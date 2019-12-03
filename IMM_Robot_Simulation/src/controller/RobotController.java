package controller;


import open62Wrap.*;
import roboticArm.RoboticArm;
import roboticArm.ArmTrigger;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * This is the controller class for the RoboticArm where the logic shall be
 * implemented also the OPCUA client/server implementations. The class extends
 * the the RoboticArm model class, to implement the logic at each abstract state
 * in the model also wire up the connections between states as the logic drives.
 * 
 * @author Michael Bishara
 */


public class RobotController extends RoboticArm {
	ServerAPIBase serverAPI;
	ClientAPIBase clientAPI;
	SWIGTYPE_p_UA_Server server;
	SWIGTYPE_p_UA_Client client;
	UA_NodeId statusNodeID;
	UA_NodeId robotStatusNodeID;
	Robot_OPCUA_Client client_opcua;
	static RobotController robotController;
	String serverUrl = "opc.tcp://localhost:4840";
	int subId;

	/**
	 * Extending for the Client API Base to implement the callback methods form the
	 * c libraries , enabling callback s from the c library to the java code.
	 */
	class Robot_OPCUA_Client extends ClientAPIBase {

		int i = 0; // calls counter

		/**
		 * Receiving interval calls from the c library when the client is connected
		 * successfully to a server. This helps to set the monitored items at the
		 * connected server after connection is successfully made. The interval is set
		 * with this function call the c library UA_Client_run_iterate(client, 5000);
		 */
		@Override
		public void client_connected(ClientAPIBase clientAPIBase, SWIGTYPE_p_UA_Client client, String serverUrl) {
			System.out.println("Connected");

			UA_NodeId objNodeId = new UA_NodeId();
			Identifier objNode_ideIdentifier = new Identifier();
			objNode_ideIdentifier.setNumeric(85);
			objNodeId.setNamespaceIndex(0);
			objNodeId.setIdentifier(objNode_ideIdentifier);

			UA_NodeId methodNodeId = new UA_NodeId();
			Identifier method_ideIdentifier = new Identifier();
			method_ideIdentifier.setNumeric(2004);
			methodNodeId.setNamespaceIndex(1);
			methodNodeId.setIdentifier(method_ideIdentifier);

			// Calling a method on the server
			System.out.println("Calling a method on the server:  "
					+ ClientAPIBase.CallMethod(serverUrl, objNodeId, methodNodeId, "Testt"));
			
		
					
		 // read the
			
			
			
			if (i < 1) {
				i++;
				statusNodeID = ClientAPIBase.GetNodeByName(client, "Status"); // get Node id at the server by name
				subId = ClientAPIBase.ClientSubtoNode(clientAPIBase, client, statusNodeID); // subscribe to changes at the
		
				
				UA_NodeId readNode2 = ClientAPIBase.GetNodeByName(client, "ManufacturerName");
				UA_NodeId readNode = new UA_NodeId();
				Identifier readNodeObjNode_ideIdentifier = new Identifier();
				readNodeObjNode_ideIdentifier.setNumeric(open62541.UA_NS0ID_OBJECTSFOLDER);
				readNode.setIdentifier(readNodeObjNode_ideIdentifier);
				readNodeObjNode_ideIdentifier.setNumeric(11);
				readNode.setNamespaceIndex(1);
				
				UA_Variant variant = ClientAPIBase.ClientReadValue(client, readNode2);
			//	System.out.println("111 : " + ClientAPIBase.Get_UA_Type(5) );
				//System.out.println("111 : " + variant.getType() );
				Object example= variant.getData();
				System.out.println("THE READ VALUE: " + variant.getData() );
				System.out.println("THE READ VALUE 2 : " + example );

			//	if(variant.getType() == ClientAPIBase.Get_UA_Type(open62541.UA_TYPES_INT32)) {
			//		System.out.println("THE READ VALUE: " +variant.getData() );
			//	}
				
				//	System.out.println("THE READ VALUE:" + ClientAPIBase.clientReadValue(client, statusNodeID)); // read the
																												// value
																												// for
																												// the
																												// node
																												// at
																												// the
																												// server
																												// by id
				// ClientAPIBase.clientWriteValue(client, statusId, 991);

			//	if (ClientAPIBase.clientReadValue(client, statusNodeID) == MoldingTrigger.ROBOT.ordinal()) { // *(For
																												// testing)*
																												// if
																												// the
					// node
					// value
					// is
					// waiting
					// for
					// robot
					// ->
					// move
					// in
					// moveIn();
			//	}
			}

		}

		/**
		 * Receiving calls from the c library on monitored item(s) changed. can be
		 * further filtered by Node id. As a first step considering the monitored item
		 * value is int, but later could be changed to more generic variant.
		 * 
		 * @param nodeId the node id triggered the change
		 * @param value  the value of the node triggered the change
		 */
		@Override
		public void monitored_itemChanged(UA_NodeId nodeId, int value) {
			System.out.println(nodeId.toString()+nodeId.getIdentifier().getNumeric()+"IMM FROM CLIENT Status monitored_itemChanged() invoked." + value);
			if (value == 3) {
				 moveIn();

			}

		}

	}

	/**
	 * Extending for the Server API Base to implement the callback methods form the
	 * c libraries , enabling callback s from the c library to the java code.
	 */

	class Robot_OPCUA extends ServerAPIBase {
		/**
		 * Extending for the Server API Base to implement the callback methods form the
		 * c libraries , enabling callback s from the c library to the java code.
		 * 
		 * @param nodeId the node id triggered the change
		 * @param value  the value of the node triggered the change
		 */
		@Override
		public void monitored_itemChanged(UA_NodeId nodeId, int value) {
			System.out.println("Robot Status monitored_itemChanged() invoked." + value);

		}
	}

	/**
	 * This the Robot controller constructor
	 */
	public RobotController() {
		System.out.println("Start");
		//serverAPI = new ServerAPIBase();
		server = ServerAPIBase.CreateServer("localhost", 4050);
		robotStatusNodeID = ServerAPIBase.ManuallyDefineRobot(server);
		//serverAPI.addMonitoredItem(server, robotStatusNodeID, new Robot_OPCUA());

	//	clientAPI = new ClientAPIBase();
		client = ClientAPIBase.InitClient();
		client_opcua = new Robot_OPCUA_Client();

	}

	/**
	 * This method is called from the Main.java main method to init and start the
	 * opcua server/client.
	 */
	public static void startRobot() {
		// RobotController.robotController = new RobotController();
		// new RobotSimpleThread(robotController.serverAPI,
		// robotController.server).start();
		// new RobotClientThread(robotController.clientAPI,
		// robotController.client).start();

		// robotController.ready();
	}

	/**
	 * main method for testing purposes. calling the constructor then running the
	 * server in a separate thread.
	 */
	public static void main(String[] args) {

		RobotController.robotController = new RobotController();

		new Thread(new Runnable() {
			@Override
			public void run() {
				ServerAPIBase.RunServer(robotController.server);
			}
		}).start();

		ClientAPIBase.ClientConnect(robotController.client_opcua, robotController.client, "opc.tcp://localhost:4840");
		

	}

	/**
	 * Overriding the model abstract method moveIn in RoboticArm super class to
	 * implement the logic needed
	 */
	@Override
	public void moveIn() {
		super.moveIn();
		System.out.println("moveIn in Mold" + robotController.getCurrentState());
		ServerAPIBase.WriteVariable(server, robotStatusNodeID, robotController.getCurrentState());

		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
		exec.schedule(new Runnable() {
			public void run() {
				fireTrigger(ArmTrigger.GRAB);

			}
		}, 5, TimeUnit.SECONDS);

	}

	/**
	 * Overriding the model abstract method moveOut in RoboticArm super class to
	 * implement the logic needed
	 */
	@Override
	public void moveOut() {
		super.moveOut();
		System.out.println("moveOut  Mold " + robotController.getCurrentState());
		ServerAPIBase.WriteVariable(server, robotStatusNodeID, robotController.getCurrentState());

		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

		exec.schedule(new Runnable() {
			public void run() {
				fireTrigger(ArmTrigger.RELEASE);

			}
		}, 5, TimeUnit.SECONDS);
		// ClientAPIBase.clientWriteValue(client, statusNodeID, 1);

	}

	/**
	 * Overriding the model abstract method grab in RoboticArm super class to
	 * implement the logic needed
	 */
	@Override
	public void grab() {
		super.grab();
		System.out.println("grab in Mold " + robotController.getCurrentState());
		ServerAPIBase.WriteVariable(server, robotStatusNodeID, robotController.getCurrentState());
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
		exec.schedule(new Runnable() {
			public void run() {
				fireTrigger(ArmTrigger.MOVE_OUT);

			}
		}, 5, TimeUnit.SECONDS);

	}

	/**
	 * Overriding the model abstract method release in RoboticArm super class to
	 * implement the logic needed
	 */
	@Override
	public void release() {
		super.release();
		System.out.println("release out Mold " + robotController.getCurrentState());
		ServerAPIBase.WriteVariable(server, robotStatusNodeID, robotController.getCurrentState());
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

		exec.schedule(new Runnable() {
			public void run() {
				fireTrigger(ArmTrigger.READY);

			}
		}, 5, TimeUnit.SECONDS);

	}

	/**
	 * Overriding the model abstract method ready in RoboticArm super class to
	 * implement the logic needed
	 */
	@Override
	public void ready() {
		super.ready();
		
		ServerAPIBase.WriteVariable(server, robotStatusNodeID, robotController.getCurrentState());
		ClientAPIBase.ClientWriteValue("opc.tcp://localhost:4840", statusNodeID, 0);
	
		// fireTrigger(ArmTrigger.GRAB);
	}

}
