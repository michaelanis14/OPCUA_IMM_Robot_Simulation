/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.0
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package open62Wrap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

public class open62541JNI {

    /* static {
       try {
           System.loadLibrary("opcua_java_api");
       } catch (UnsatisfiedLinkError e) {
         System.err.println("opcua_java_api failed to load. \n" + e);
         System.exit(1);
       }
     }*/
    /*
     * Loads the native libraries using a workaround as the EV3 currently has troubles with finding them.
     * Uncomment this and comment the loadLib from open62Wrap/open62541JNI if using EV3
     */

    static {
        try {
            System.out.println("Looking for native lib");
            loadNativeLib();    //change the library in this method depending on your platform
            System.out.println("Found native lib");
        } catch (IOException e) {
            System.out.println("Cannot find native lib");
            e.printStackTrace();
        }
    }

    private static void loadNativeLib() throws IOException {
        String libName;
        if (System.getProperty("os.name").startsWith("Windows")) {
            libName = "opcua_java_api.dll"; //use this on windows (needs 32 bit java)
        } else {
            libName = "libOpcua-Java-API_hf.so"; //use this on BrickPi, use the one w/o _hf suffix on ev3
        }
        URL url = open62541JNI.class.getResource("/" + libName);
        File tmpDir = Files.createTempDirectory("my-native-lib").toFile();
        tmpDir.deleteOnExit();
        File nativeLibTmpFile = new File(tmpDir, libName);
        nativeLibTmpFile.deleteOnExit();
        try (InputStream in = url.openStream()) {
            Files.copy(in, nativeLibTmpFile.toPath());
        } catch (Exception e) {
            System.out.println("Error in loadNativeLib");
            e.printStackTrace();
        }
        System.load(nativeLibTmpFile.getAbsolutePath());
    }

    public final static native void ServerAPIBase_running_set(long jarg1, ServerAPIBase jarg1_, boolean jarg2);

    public final static native boolean ServerAPIBase_running_get(long jarg1, ServerAPIBase jarg1_);

    public final static native long ServerAPIBase_Get();

    public final static native void ServerAPIBase_stopHandler(int jarg1);

    public final static native long ServerAPIBase_CreateServerDefaultConfig();

    public final static native long ServerAPIBase_CreateServer(String jarg1, int jarg2);

    public final static native int ServerAPIBase_RunServer(long jarg1);

    public final static native void ServerAPIBase_AddMonitoredItem(long jarg1, ServerAPIBase jarg1_, long jarg2, long jarg3, UA_NodeId jarg3_);

    public final static native long ServerAPIBase_AddObject__SWIG_0(long jarg1, long jarg2, UA_NodeId jarg2_, String jarg3);

    public final static native long ServerAPIBase_AddObject__SWIG_1(long jarg1, long jarg2, UA_NodeId jarg2_, long jarg3, UA_NodeId jarg3_, String jarg4);

    public final static native long ServerAPIBase_AddVariableNode(long jarg1, long jarg2, UA_NodeId jarg2_, long jarg3, UA_NodeId jarg3_, String jarg4, int jarg5, int jarg6);

    public final static native long ServerAPIBase_ManuallyDefineIMM(long jarg1);

    public final static native long ServerAPIBase_ManuallyDefineRobot(long jarg1);

    public final static native int ServerAPIBase_WriteVariable__SWIG_0(long jarg1, long jarg2, UA_NodeId jarg2_, int jarg3);

    public final static native int ServerAPIBase_WriteVariable__SWIG_1(long jarg1, long jarg2, UA_NodeId jarg2_, String jarg3);

    public final static native int ServerAPIBase_WriteVariable__SWIG_2(long jarg1, long jarg2, UA_NodeId jarg2_, double jarg3);

    public final static native long ServerAPIBase_GetDataTypeNode(int jarg1);

    public final static native long ServerAPIBase_AddMethod(long jarg1, ServerAPIBase jarg1_, long jarg2, long jarg3, UA_NodeId jarg3_, long jarg4, UA_NodeId jarg4_, long jarg5, UA_Argument jarg5_, long jarg6, UA_Argument jarg6_, long jarg7, UA_MethodAttributes jarg7_);

    public final static native long ServerAPIBase_AddArrayMethod(long jarg1, ServerAPIBase jarg1_, long jarg2, long jarg3, UA_NodeId jarg3_, long jarg4, UA_NodeId jarg4_, long jarg5, UA_Argument jarg5_, long jarg6, UA_MethodAttributes jarg6_, String jarg7, String jarg8, int jarg9, int jarg10);

    public final static native void ServerAPIBase_SetMethodOutput(long jarg1, UA_NodeId jarg1_, String jarg2);

    public final static native long ServerAPIBase_CreateStringNodeId(int jarg1, String jarg2);

    public final static native void ServerAPIBase_monitored_itemChanged(long jarg1, ServerAPIBase jarg1_, long jarg2, UA_NodeId jarg2_, int jarg3);

    public final static native void ServerAPIBase_monitored_itemChangedSwigExplicitServerAPIBase(long jarg1, ServerAPIBase jarg1_, long jarg2, UA_NodeId jarg2_, int jarg3);

    public final static native void ServerAPIBase_methods_callback(long jarg1, ServerAPIBase jarg1_, long jarg2, UA_NodeId jarg2_, long jarg3, UA_NodeId jarg3_, String jarg4, String jarg5, long jarg6, ServerAPIBase jarg6_);

    public final static native void ServerAPIBase_methods_callbackSwigExplicitServerAPIBase(long jarg1, ServerAPIBase jarg1_, long jarg2, UA_NodeId jarg2_, long jarg3, UA_NodeId jarg3_, String jarg4, String jarg5, long jarg6, ServerAPIBase jarg6_);

    public final static native void delete_ServerAPIBase(long jarg1);

    public final static native long new_ServerAPIBase();

    public final static native void ServerAPIBase_director_connect(ServerAPIBase obj, long cptr, boolean mem_own, boolean weak_global);

    public final static native void ServerAPIBase_change_ownership(ServerAPIBase obj, long cptr, boolean take_or_release);

    public final static native void ClientAPIBase_methodInputs_set(long jarg1, ClientAPIBase jarg1_, int[] jarg2);

    public final static native int[] ClientAPIBase_methodInputs_get(long jarg1, ClientAPIBase jarg1_);

    public final static native void ClientAPIBase_running_set(long jarg1, ClientAPIBase jarg1_, boolean jarg2);

    public final static native boolean ClientAPIBase_running_get(long jarg1, ClientAPIBase jarg1_);

    public final static native long ClientAPIBase_Get();

    public final static native void ClientAPIBase_stopHandler(int jarg1);

    public final static native void ClientAPIBase_inactivityCallback(long jarg1);

    public final static native long ClientAPIBase_InitClient();

    public final static native int ClientAPIBase_ClientConnect(long jarg1, ClientAPIBase jarg1_, long jarg2, String jarg3);

    public final static native long ClientAPIBase_NodeIter(long jarg1, UA_NodeId jarg1_, long jarg2, String jarg3);

    public final static native long ClientAPIBase_GetNodeByName(long jarg1, String jarg2);

    public final static native int ClientAPIBase_ClientSubtoNode__SWIG_0(long jarg1, ClientAPIBase jarg1_, long jarg2, long jarg3, UA_NodeId jarg3_);

    public final static native int ClientAPIBase_ClientSubtoNode__SWIG_1(long jarg1, ClientAPIBase jarg1_, String jarg2, long jarg3, UA_NodeId jarg3_);

    public final static native long ClientAPIBase_SetGetVariant(long jarg1, UA_Variant jarg1_);

    public final static native void ClientAPIBase_ClientRemoveSub(long jarg1, int jarg2);

    public final static native long ClientAPIBase_ClientReadValue(long jarg1, long jarg2, UA_NodeId jarg2_);

    public final static native int ClientAPIBase_ClientReadIntValue(long jarg1, long jarg2, UA_NodeId jarg2_);

    public final static native int ClientAPIBase_ClientWriteValue(String jarg1, long jarg2, UA_NodeId jarg2_, int jarg3);

    public final static native String ClientAPIBase_GetMethodOutput();

    public final static native String ClientAPIBase_CallMethod(String jarg1, long jarg2, UA_NodeId jarg2_, long jarg3, UA_NodeId jarg3_, String jarg4);

    public final static native String ClientAPIBase_CallArrayMethod(String jarg1, long jarg2, UA_NodeId jarg2_, long jarg3, UA_NodeId jarg3_, int[] jarg4, int jarg5, long jarg6, UA_Variant jarg6_);

    public final static native void ClientAPIBase_monitored_itemChanged(long jarg1, ClientAPIBase jarg1_, long jarg2, UA_NodeId jarg2_, int jarg3);

    public final static native void ClientAPIBase_monitored_itemChangedSwigExplicitClientAPIBase(long jarg1, ClientAPIBase jarg1_, long jarg2, UA_NodeId jarg2_, int jarg3);

    public final static native void ClientAPIBase_client_connected(long jarg1, ClientAPIBase jarg1_, long jarg2, ClientAPIBase jarg2_, long jarg3, String jarg4);

    public final static native void ClientAPIBase_client_connectedSwigExplicitClientAPIBase(long jarg1, ClientAPIBase jarg1_, long jarg2, ClientAPIBase jarg2_, long jarg3, String jarg4);

    public final static native void ClientAPIBase_methods_callback(long jarg1, ClientAPIBase jarg1_, long jarg2, UA_NodeId jarg2_, long jarg3, UA_NodeId jarg3_, String jarg4, String jarg5, long jarg6, ClientAPIBase jarg6_);

    public final static native void ClientAPIBase_methods_callbackSwigExplicitClientAPIBase(long jarg1, ClientAPIBase jarg1_, long jarg2, UA_NodeId jarg2_, long jarg3, UA_NodeId jarg3_, String jarg4, String jarg5, long jarg6, ClientAPIBase jarg6_);

    public final static native void delete_ClientAPIBase(long jarg1);

    public final static native long new_ClientAPIBase();

    public final static native void ClientAPIBase_director_connect(ClientAPIBase obj, long cptr, boolean mem_own, boolean weak_global);

    public final static native void ClientAPIBase_change_ownership(ClientAPIBase obj, long cptr, boolean take_or_release);

    public final static native int UA_NODEIDTYPE_NUMERIC_get();

    public final static native int UA_NODEIDTYPE_STRING_get();

    public final static native int UA_NODEIDTYPE_GUID_get();

    public final static native int UA_NODEIDTYPE_BYTESTRING_get();

    public final static native void Identifier_numeric_set(long jarg1, Identifier jarg1_, int jarg2);

    public final static native int Identifier_numeric_get(long jarg1, Identifier jarg1_);

    public final static native void Identifier_string_set(long jarg1, Identifier jarg1_, String jarg2);

    public final static native String Identifier_string_get(long jarg1, Identifier jarg1_);

    public final static native void Identifier_guid_set(long jarg1, Identifier jarg1_, long jarg2, UA_Guid jarg2_);

    public final static native long Identifier_guid_get(long jarg1, Identifier jarg1_);

    public final static native void Identifier_byteString_set(long jarg1, Identifier jarg1_, String jarg2);

    public final static native String Identifier_byteString_get(long jarg1, Identifier jarg1_);

    public final static native long new_Identifier();

    public final static native void delete_Identifier(long jarg1);

    public final static native void UA_NodeId_namespaceIndex_set(long jarg1, UA_NodeId jarg1_, int jarg2);

    public final static native int UA_NodeId_namespaceIndex_get(long jarg1, UA_NodeId jarg1_);

    public final static native void UA_NodeId_identifierType_set(long jarg1, UA_NodeId jarg1_, int jarg2);

    public final static native int UA_NodeId_identifierType_get(long jarg1, UA_NodeId jarg1_);

    public final static native void UA_NodeId_identifier_set(long jarg1, UA_NodeId jarg1_, long jarg2, Identifier jarg2_);

    public final static native long UA_NodeId_identifier_get(long jarg1, UA_NodeId jarg1_);

    public final static native long new_UA_NodeId();

    public final static native void delete_UA_NodeId(long jarg1);

    public final static native void UA_Argument_name_set(long jarg1, UA_Argument jarg1_, String jarg2);

    public final static native String UA_Argument_name_get(long jarg1, UA_Argument jarg1_);

    public final static native void UA_Argument_dataType_set(long jarg1, UA_Argument jarg1_, long jarg2, UA_NodeId jarg2_);

    public final static native long UA_Argument_dataType_get(long jarg1, UA_Argument jarg1_);

    public final static native void UA_Argument_valueRank_set(long jarg1, UA_Argument jarg1_, int jarg2);

    public final static native int UA_Argument_valueRank_get(long jarg1, UA_Argument jarg1_);

    public final static native void UA_Argument_arrayDimensionsSize_set(long jarg1, UA_Argument jarg1_, long jarg2);

    public final static native long UA_Argument_arrayDimensionsSize_get(long jarg1, UA_Argument jarg1_);

    public final static native void UA_Argument_arrayDimensions_set(long jarg1, UA_Argument jarg1_, long jarg2);

    public final static native long UA_Argument_arrayDimensions_get(long jarg1, UA_Argument jarg1_);

    public final static native void UA_Argument_description_set(long jarg1, UA_Argument jarg1_, long jarg2, UA_LocalizedText jarg2_);

    public final static native long UA_Argument_description_get(long jarg1, UA_Argument jarg1_);

    public final static native long new_UA_Argument();

    public final static native void delete_UA_Argument(long jarg1);

    public final static native void UA_LocalizedText_locale_set(long jarg1, UA_LocalizedText jarg1_, String jarg2);

    public final static native String UA_LocalizedText_locale_get(long jarg1, UA_LocalizedText jarg1_);

    public final static native void UA_LocalizedText_text_set(long jarg1, UA_LocalizedText jarg1_, String jarg2);

    public final static native String UA_LocalizedText_text_get(long jarg1, UA_LocalizedText jarg1_);

    public final static native long new_UA_LocalizedText();

    public final static native void delete_UA_LocalizedText(long jarg1);

    public final static native void UA_MethodAttributes_specifiedAttributes_set(long jarg1, UA_MethodAttributes jarg1_, int jarg2);

    public final static native int UA_MethodAttributes_specifiedAttributes_get(long jarg1, UA_MethodAttributes jarg1_);

    public final static native void UA_MethodAttributes_displayName_set(long jarg1, UA_MethodAttributes jarg1_, long jarg2, UA_LocalizedText jarg2_);

    public final static native long UA_MethodAttributes_displayName_get(long jarg1, UA_MethodAttributes jarg1_);

    public final static native void UA_MethodAttributes_description_set(long jarg1, UA_MethodAttributes jarg1_, long jarg2, UA_LocalizedText jarg2_);

    public final static native long UA_MethodAttributes_description_get(long jarg1, UA_MethodAttributes jarg1_);

    public final static native void UA_MethodAttributes_writeMask_set(long jarg1, UA_MethodAttributes jarg1_, int jarg2);

    public final static native int UA_MethodAttributes_writeMask_get(long jarg1, UA_MethodAttributes jarg1_);

    public final static native void UA_MethodAttributes_userWriteMask_set(long jarg1, UA_MethodAttributes jarg1_, int jarg2);

    public final static native int UA_MethodAttributes_userWriteMask_get(long jarg1, UA_MethodAttributes jarg1_);

    public final static native void UA_MethodAttributes_executable_set(long jarg1, UA_MethodAttributes jarg1_, boolean jarg2);

    public final static native boolean UA_MethodAttributes_executable_get(long jarg1, UA_MethodAttributes jarg1_);

    public final static native void UA_MethodAttributes_userExecutable_set(long jarg1, UA_MethodAttributes jarg1_, boolean jarg2);

    public final static native boolean UA_MethodAttributes_userExecutable_get(long jarg1, UA_MethodAttributes jarg1_);

    public final static native long new_UA_MethodAttributes();

    public final static native void delete_UA_MethodAttributes(long jarg1);

    public final static native int UA_ACCESSLEVELMASK_READ_get();

    public final static native int UA_ACCESSLEVELMASK_WRITE_get();

    public final static native int UA_ACCESSLEVELMASK_HISTORYREAD_get();

    public final static native int UA_ACCESSLEVELMASK_HISTORYWRITE_get();

    public final static native int UA_ACCESSLEVELMASK_SEMANTICCHANGE_get();

    public final static native int UA_ACCESSLEVELMASK_STATUSWRITE_get();

    public final static native int UA_ACCESSLEVELMASK_TIMESTAMPWRITE_get();

    public final static native int UA_TYPES_BOOLEAN_get();

    public final static native int UA_TYPES_SBYTE_get();

    public final static native int UA_TYPES_BYTE_get();

    public final static native int UA_TYPES_INT16_get();

    public final static native int UA_TYPES_UINT16_get();

    public final static native int UA_TYPES_INT32_get();

    public final static native int UA_TYPES_UINT32_get();

    public final static native int UA_TYPES_INT64_get();

    public final static native int UA_TYPES_UINT64_get();

    public final static native int UA_TYPES_FLOAT_get();

    public final static native int UA_TYPES_DOUBLE_get();

    public final static native int UA_TYPES_STRING_get();

    public final static native int UA_TYPES_DATETIME_get();

    public final static native int UA_TYPES_GUID_get();

    public final static native int UA_TYPES_BYTESTRING_get();

    public final static native int UA_TYPES_XMLELEMENT_get();

    public final static native int UA_TYPES_NODEID_get();

    public final static native int UA_TYPES_EXPANDEDNODEID_get();

    public final static native int UA_TYPES_STATUSCODE_get();

    public final static native int UA_TYPES_QUALIFIEDNAME_get();

    public final static native int UA_TYPES_LOCALIZEDTEXT_get();

    public final static native int UA_TYPES_EXTENSIONOBJECT_get();

    public final static native int UA_TYPES_DATAVALUE_get();

    public final static native int UA_TYPES_VARIANT_get();

    public final static native int UA_TYPES_DIAGNOSTICINFO_get();

    public final static native int UA_TYPES_KEYVALUEPAIR_get();

    public final static native int UA_NS0ID_MODELLINGRULE_MANDATORY_get();

    public final static native int UA_NS0ID_MODELLINGRULE_MANDATORYSHARED_get();

    public final static native int UA_NS0ID_MODELLINGRULE_OPTIONAL_get();

    public final static native int UA_NS0ID_MODELLINGRULE_EXPOSESITSARRAY_get();

    public final static native int UA_NS0ID_ROOTFOLDER_get();

    public final static native int UA_NS0ID_OBJECTSFOLDER_get();

    public final static native int UA_NS0ID_TYPESFOLDER_get();

    public final static native int UA_NS0ID_VIEWSFOLDER_get();

    public final static native int UA_NS0ID_OBJECTTYPESFOLDER_get();

    public final static native int UA_NS0ID_VARIABLETYPESFOLDER_get();

    public final static native int UA_NS0ID_DATATYPESFOLDER_get();

    public final static native int UA_NS0ID_REFERENCETYPESFOLDER_get();

    public final static native int UA_NS0ID_XMLSCHEMA_TYPESYSTEM_get();

    public final static native int UA_NS0ID_OPCBINARYSCHEMA_TYPESYSTEM_get();

    public final static native int UA_VALUERANK_SCALAR_OR_ONE_DIMENSION_get();

    public final static native int UA_VALUERANK_ANY_get();

    public final static native int UA_VALUERANK_SCALAR_get();

    public final static native int UA_VALUERANK_ONE_OR_MORE_DIMENSIONS_get();

    public final static native int UA_VALUERANK_ONE_DIMENSION_get();

    public final static native int UA_VALUERANK_TWO_DIMENSIONS_get();

    public final static native int UA_VALUERANK_THREE_DIMENSIONS_get();

    public final static native long UA_NODEID_NUMERIC(int jarg1, int jarg2);

    public final static native void UA_Variant_type_set(long jarg1, UA_Variant jarg1_, long jarg2, UA_DataType jarg2_);

    public final static native long UA_Variant_type_get(long jarg1, UA_Variant jarg1_);

    public final static native void UA_Variant_storageType_set(long jarg1, UA_Variant jarg1_, int jarg2);

    public final static native int UA_Variant_storageType_get(long jarg1, UA_Variant jarg1_);

    public final static native void UA_Variant_arrayLength_set(long jarg1, UA_Variant jarg1_, long jarg2);

    public final static native long UA_Variant_arrayLength_get(long jarg1, UA_Variant jarg1_);

    public final static native void UA_Variant_data_set(long jarg1, UA_Variant jarg1_, long jarg2);

    public final static native long UA_Variant_data_get(long jarg1, UA_Variant jarg1_);

    public final static native void UA_Variant_arrayDimensionsSize_set(long jarg1, UA_Variant jarg1_, long jarg2);

    public final static native long UA_Variant_arrayDimensionsSize_get(long jarg1, UA_Variant jarg1_);

    public final static native void UA_Variant_arrayDimensions_set(long jarg1, UA_Variant jarg1_, long jarg2);

    public final static native long UA_Variant_arrayDimensions_get(long jarg1, UA_Variant jarg1_);

    public final static native long new_UA_Variant();

    public final static native void delete_UA_Variant(long jarg1);

    public final static native void UA_DataType_typeId_set(long jarg1, UA_DataType jarg1_, long jarg2, UA_NodeId jarg2_);

    public final static native long UA_DataType_typeId_get(long jarg1, UA_DataType jarg1_);

    public final static native void UA_DataType_memSize_set(long jarg1, UA_DataType jarg1_, int jarg2);

    public final static native int UA_DataType_memSize_get(long jarg1, UA_DataType jarg1_);

    public final static native void UA_DataType_typeIndex_set(long jarg1, UA_DataType jarg1_, int jarg2);

    public final static native int UA_DataType_typeIndex_get(long jarg1, UA_DataType jarg1_);

    public final static native void UA_DataType_typeKind_set(long jarg1, UA_DataType jarg1_, int jarg2);

    public final static native int UA_DataType_typeKind_get(long jarg1, UA_DataType jarg1_);

    public final static native void UA_DataType_pointerFree_set(long jarg1, UA_DataType jarg1_, int jarg2);

    public final static native int UA_DataType_pointerFree_get(long jarg1, UA_DataType jarg1_);

    public final static native void UA_DataType_overlayable_set(long jarg1, UA_DataType jarg1_, int jarg2);

    public final static native int UA_DataType_overlayable_get(long jarg1, UA_DataType jarg1_);

    public final static native void UA_DataType_membersSize_set(long jarg1, UA_DataType jarg1_, int jarg2);

    public final static native int UA_DataType_membersSize_get(long jarg1, UA_DataType jarg1_);

    public final static native void UA_DataType_binaryEncodingId_set(long jarg1, UA_DataType jarg1_, int jarg2);

    public final static native int UA_DataType_binaryEncodingId_get(long jarg1, UA_DataType jarg1_);

    public final static native void UA_DataType_members_set(long jarg1, UA_DataType jarg1_, long jarg2, UA_DataTypeMember jarg2_);

    public final static native long UA_DataType_members_get(long jarg1, UA_DataType jarg1_);

    public final static native long new_UA_DataType();

    public final static native void delete_UA_DataType(long jarg1);

    public final static native void UA_DataTypeMember_memberTypeIndex_set(long jarg1, UA_DataTypeMember jarg1_, int jarg2);

    public final static native int UA_DataTypeMember_memberTypeIndex_get(long jarg1, UA_DataTypeMember jarg1_);

    public final static native void UA_DataTypeMember_padding_set(long jarg1, UA_DataTypeMember jarg1_, short jarg2);

    public final static native short UA_DataTypeMember_padding_get(long jarg1, UA_DataTypeMember jarg1_);

    public final static native void UA_DataTypeMember_namespaceZero_set(long jarg1, UA_DataTypeMember jarg1_, boolean jarg2);

    public final static native boolean UA_DataTypeMember_namespaceZero_get(long jarg1, UA_DataTypeMember jarg1_);

    public final static native void UA_DataTypeMember_isArray_set(long jarg1, UA_DataTypeMember jarg1_, boolean jarg2);

    public final static native boolean UA_DataTypeMember_isArray_get(long jarg1, UA_DataTypeMember jarg1_);

    public final static native long new_UA_DataTypeMember();

    public final static native void delete_UA_DataTypeMember(long jarg1);

    public final static native void UA_Guid_data1_set(long jarg1, UA_Guid jarg1_, int jarg2);

    public final static native int UA_Guid_data1_get(long jarg1, UA_Guid jarg1_);

    public final static native void UA_Guid_data2_set(long jarg1, UA_Guid jarg1_, int jarg2);

    public final static native int UA_Guid_data2_get(long jarg1, UA_Guid jarg1_);

    public final static native void UA_Guid_data3_set(long jarg1, UA_Guid jarg1_, int jarg2);

    public final static native int UA_Guid_data3_get(long jarg1, UA_Guid jarg1_);

    public final static native void UA_Guid_data4_set(long jarg1, UA_Guid jarg1_, short[] jarg2);

    public final static native short[] UA_Guid_data4_get(long jarg1, UA_Guid jarg1_);

    public final static native long new_UA_Guid();

    public final static native void delete_UA_Guid(long jarg1);

    public final static native int void2int(long jarg1);

    public final static native String void2str(long jarg1);

    public final static native boolean IsVariantType_Int(long jarg1, UA_Variant jarg1_);

    public final static native boolean IsVariantType_String(long jarg1, UA_Variant jarg1_);

    public final static native long new_intp();

    public final static native long copy_intp(int jarg1);

    public final static native void delete_intp(long jarg1);

    public final static native void intp_assign(long jarg1, int jarg2);

    public final static native int intp_value(long jarg1);

    public static void SwigDirector_ServerAPIBase_monitored_itemChanged(ServerAPIBase jself, long nodeId, int value) {
        jself.monitored_itemChanged((nodeId == 0) ? null : new UA_NodeId(nodeId, false), value);
    }

    public static void SwigDirector_ServerAPIBase_methods_callback(ServerAPIBase jself, long methodId, long objectId, String input, String output, long jAPIBase) {
        jself.methods_callback((methodId == 0) ? null : new UA_NodeId(methodId, false), (objectId == 0) ? null : new UA_NodeId(objectId, false), input, output, (jAPIBase == 0) ? null : new ServerAPIBase(jAPIBase, false));
    }

    public static void SwigDirector_ClientAPIBase_monitored_itemChanged(ClientAPIBase jself, long nodeId, int value) {
        jself.monitored_itemChanged(new UA_NodeId(nodeId, true), value);
    }

    public static void SwigDirector_ClientAPIBase_client_connected(ClientAPIBase jself, long jClientAPIBase, long client, String serverUrl) {
        jself.client_connected((jClientAPIBase == 0) ? null : new ClientAPIBase(jClientAPIBase, false), (client == 0) ? null : new SWIGTYPE_p_UA_Client(client, false), serverUrl);
    }

    public static void SwigDirector_ClientAPIBase_methods_callback(ClientAPIBase jself, long objectId, long methodId, String input, String output, long jAPIBase) {
        jself.methods_callback(new UA_NodeId(objectId, true), new UA_NodeId(methodId, true), input, output, (jAPIBase == 0) ? null : new ClientAPIBase(jAPIBase, false));
    }

    private final static native void swig_module_init();

    static {
        swig_module_init();
    }
}
