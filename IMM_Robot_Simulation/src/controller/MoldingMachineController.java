package controller;

import open62Wrap.*;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import communication.Communication;
import communication.utils.RequestedNodePair;
import molding.MoldingMachine;
import molding.MoldingTrigger;

/**
 * This is the controller class for the MoldingMachine where the logic shall be
 * implemented also the OPCUA client/server implementations. The class extends
 * the the MoldingMachine model class, to implement the logic at each abstract
 * state in the model also wire up the connections between states as the logic
 * drives.
 * 
 * @author Michael Bishara
 */
public class MoldingMachineController extends MoldingMachine {
	//ServerAPIBaseBase ServerAPIBase;
	SWIGTYPE_p_UA_Server server;
	UA_NodeId statusNodeID;
	static MoldingMachineController mMController;
	// static MoldingMachine_OPCUA mMachine_OPCUA;

	/**
	 * Extending for the Client API Base to implement the callback methods form the
	 * c libraries , enabling callback s from the c library to the java code.
	 */
	static class MoldingMachine_OPCUA extends ServerAPIBase {

		public MoldingMachine_OPCUA() {
			new ServerAPIBase();
		}

		/**
		 * Receiving calls from the c library on monitored item(s) changed. can be
		 * further filtered by Node id. As a first step considering the monitored item
		 * value is int, but later could be changed to more generic variant.
		 * 
		 * @param nodeId the node id triggered the change
		 * @param value  the value of the node triggered the change
		 */
		public void monitored_itemChanged(UA_NodeId nodeId, int value) {
			System.out.println("iiiiii FROM JAVA 1:  monitored_itemChanged::monitored_itemChanged() invoked. " + value+" \n " +
					 nodeId.getIdentifierType() +" \n " +
					 nodeId.getIdentifier().getNumeric() +" \n " +
					 nodeId.getIdentifier().getString() +" \n " 
					);
			if ( nodeId.getIdentifierType() == UA_NodeIdType.UA_NODEIDTYPE_NUMERIC &&value == MoldingTrigger.CLOSING.ordinal()) {
				System.out.println("The Robot is OUT lets close for the new mold");
				mMController.closeMold();
			}
		}

		@Override
		public void methods_callback(UA_NodeId methodId, UA_NodeId objectId, String input, String output,
				ServerAPIBase jAPIBase) {
			System.out.println(" iiiii Got a methods_callback With input 1: " + input);
			jAPIBase.SetMethodOutput(methodId, "FROM JAVA 1:" + input + " " + methodId.getIdentifier().getNumeric());
			// System.out.println(" iiiii Got a methods_callback setting the output " +
			// jAPIBase.getData());

		}

	}

	static class MoldingMachine_OPCUA2 extends ServerAPIBase {
		public MoldingMachine_OPCUA2() {
			super();
		}

		/**
		 * Receiving calls from the c library on monitored item(s) changed. can be
		 * further filtered by Node id. As a first step considering the monitored item
		 * value is int, but later could be changed to more generic variant.
		 * 
		 * @param nodeId the node id triggered the change
		 * @param value  the value of the node triggered the change
		 */
		public void monitored_itemChanged(UA_NodeId nodeId, int value) {
			System.out.println("iiiiii FROM JAVA 2: monitored_itemChanged::monitored_itemChanged() invoked." + value
					+ nodeId.getIdentifierType() + nodeId.getIdentifier().getString());
			if ( nodeId.getIdentifierType() == UA_NodeIdType.UA_NODEIDTYPE_NUMERIC && value == MoldingTrigger.CLOSING.ordinal()) {
				System.out.println("The Robot is OUT lets close for the new mold");
				mMController.closeMold();
			}
		}

		@Override
		public void methods_callback(UA_NodeId methodId, UA_NodeId objectId, String input, String output,
				ServerAPIBase jAPIBase) {
			System.out.println(" iiiii Got a methods_callback With input 2:" + input);
			jAPIBase.SetMethodOutput(methodId, "FROM JAVA 2: " + input + " " + methodId.getIdentifier().getNumeric());
			// System.out.println(" iiiii Got a methods_callback setting the output " +
			// jAPIBase.getData());

		}

	}

	/**
	 * This the MoldingMachine controller constructor
	 */
	public MoldingMachineController() {

		MoldingMachine_OPCUA opcuaM = new MoldingMachine_OPCUA();

		MoldingMachine_OPCUA2 opcuaM2 = new MoldingMachine_OPCUA2();

		System.out.println("Start");
		//ServerAPIBase = new ServerAPIBaseBase();

		server = ServerAPIBase.CreateServer("localhost", 4840);

		UA_NodeId type = new UA_NodeId();
		type.setIdentifierType(UA_NodeIdType.UA_NODEIDTYPE_NUMERIC);

		UA_NodeId object = ServerAPIBase.AddObject(server, open62541.UA_NODEID_NUMERIC(1, 20), "OPCUA Object");

		int accessRights = open62541.UA_ACCESSLEVELMASK_WRITE | open62541.UA_ACCESSLEVELMASK_READ;
		ServerAPIBase.AddVariableNode(server, object, ServerAPIBase.CreateStringNodeId(1, "stringID"), "Hello Variable from Java", open62541.UA_TYPES_STRING,
				accessRights);

		statusNodeID = ServerAPIBase.ManuallyDefineIMM(server);
		
		UA_NodeId typestatusNodeID = ServerAPIBase.AddVariableNode(server,object, open62541.UA_NODEID_NUMERIC(1, 33), "VariableName", open62541.UA_TYPES_STRING, accessRights);
		
		
		ServerAPIBase.AddMonitoredItem(opcuaM,server, statusNodeID);
		//ServerAPIBase.addMonitoredItem(opcuaM,server, statusNodeID, opcuaM2);

		UA_LocalizedText locale = new UA_LocalizedText();
		locale.setLocale("en-US");
		locale.setText("A String");

		UA_Argument input = new UA_Argument();
		input.setDescription(locale);
		input.setName("MyInput");
		input.setDataType(ServerAPIBase.GetDataTypeNode(open62541.UA_TYPES_STRING));
		input.setValueRank(open62541.UA_VALUERANK_SCALAR);

		UA_Argument output = new UA_Argument();
		output.setDescription(locale);
		output.setName("MyOutput");
		output.setDataType(ServerAPIBase.GetDataTypeNode(open62541.UA_TYPES_STRING));
		output.setValueRank(open62541.UA_VALUERANK_SCALAR);

		UA_LocalizedText methodLocale = new UA_LocalizedText();
		methodLocale.setLocale("en-US");
		methodLocale.setText("SayHelloWorld");

		UA_MethodAttributes methodAttr = new UA_MethodAttributes();
		methodAttr.setDescription(methodLocale);
		methodAttr.setDisplayName(methodLocale);
		methodAttr.setExecutable(true);
		methodAttr.setUserExecutable(true);

		UA_NodeId objNodeId = new UA_NodeId();
		Identifier objNode_ideIdentifier = new Identifier();
		objNode_ideIdentifier.setNumeric(open62541.UA_NS0ID_OBJECTSFOLDER);
		objNodeId.setNamespaceIndex(0);
		objNodeId.setIdentifier(objNode_ideIdentifier);

		ServerAPIBase.AddMethod(opcuaM,server, objNodeId, open62541.UA_NODEID_NUMERIC(1, 2004), input, output, methodAttr);

		// add Method inside IMM object passing the object node id
		objNode_ideIdentifier.setNumeric(20);
		objNodeId.setNamespaceIndex(1);
		objNodeId.setIdentifier(objNode_ideIdentifier);
		ServerAPIBase.AddMethod(opcuaM2,server, objNodeId, open62541.UA_NODEID_NUMERIC(1, 2005), input, output, methodAttr);
		
		
		Communication communication = new Communication();
		communication.getServerCommunication().addStringMethod(communication.getServerCommunication(), server, objNodeId, new RequestedNodePair<>(1, communication.getServerCommunication().getUnique_id()), "OPEN_MOLD",
                opcuaMethodInput -> {
                    return openMold();
                });
		//Object example = "HELLO";
		//ServerAPIBase.setData(example);
		//System.out.println(ServerAPIBase.getData());
		

	}

	/**
	 * This method is called from the Main.java main method to init and start the
	 * opcua server/client.
	 */
	public static void startMolding() {

		// MoldingMachineController.mMController = new MoldingMachineController();

		// new SimpleThread().start();

		// mMController.closeMold();

	}

	/**
	 * main method for testing purposes. calling the constructor then running the
	 * server in a separate thread.
	 */
	public static void main(String[] args) {
		// mMachine_OPCUA = ;
		mMController = new MoldingMachineController();

		new Thread(new Runnable() {
			@Override
			public void run() {
				ServerAPIBase.RunServer(MoldingMachineController.mMController.server);
			}
		}).start();

		mMController.closeMold();

	}

	/**
	 * Overriding the model abstract method injectMold in MoldingMachine super class
	 * to implement the logic needed
	 */
	@Override
	public String injectMold() {
		super.injectMold();
		ServerAPIBase.WriteVariable(server, statusNodeID, mMController.getCurrentState());
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

		exec.schedule(new Runnable() {
			public void run() {
				fireTrigger(MoldingTrigger.OPENING);

			}
		}, 2, TimeUnit.SECONDS);
		return "injectMold";
	}

	/**
	 * Overriding the model abstract method openMold in MoldingMachine super class
	 * to implement the logic needed
	 */
	@Override
	public String openMold() {
		super.openMold();

		ServerAPIBase.WriteVariable(server, statusNodeID, mMController.getCurrentState());
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

		exec.schedule(new Runnable() {
			public void run() {
				fireTrigger(MoldingTrigger.ROBOT);

			}
		}, 2, TimeUnit.SECONDS);
		return "openMold";

	}

	/**
	 * Overriding the model abstract method closeMold in MoldingMachine super class
	 * to implement the logic needed
	 */
	@Override
	public String closeMold() {
		super.closeMold();
		ServerAPIBase.WriteVariable(server, statusNodeID, mMController.getCurrentState());

		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

		exec.schedule(new Runnable() {
			public void run() {
				fireTrigger(MoldingTrigger.MOLDING);

			}
		}, 2, TimeUnit.SECONDS);
		return "closeMold";

	}

	/**
	 * Overriding the model abstract method waitForRobot in MoldingMachine super
	 * class to implement the logic needed
	 */
	@Override
	public String waitForRobot() {
		super.waitForRobot();
		ServerAPIBase.WriteVariable(server, statusNodeID, mMController.getCurrentState()); // fireTrigger(MoldingTrigger.CLOSING);
		return "waitForRobot";
	}

}
