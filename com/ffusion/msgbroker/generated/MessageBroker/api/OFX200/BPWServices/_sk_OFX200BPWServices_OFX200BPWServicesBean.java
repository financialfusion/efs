/*    1:     */ package com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices;
/*    2:     */ 
/*    3:     */ import CtsComponents.ObjectContextHelper;
/*    4:     */ import com.ffusion.ffs.bpw.interfaces.BPWHist;
/*    5:     */ import com.ffusion.ffs.bpw.interfaces.BPWHistHelper;
/*    6:     */ import com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfo;
/*    7:     */ import com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfoSeqHelper;
/*    8:     */ import com.ffusion.ffs.bpw.interfaces.CustPayeeRslt;
/*    9:     */ import com.ffusion.ffs.bpw.interfaces.CustPayeeRsltSeqHelper;
/*   10:     */ import com.ffusion.ffs.bpw.interfaces.CustomerBankInfo;
/*   11:     */ import com.ffusion.ffs.bpw.interfaces.CustomerBankInfoSeqHelper;
/*   12:     */ import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
/*   13:     */ import com.ffusion.ffs.bpw.interfaces.CustomerInfoSeqHelper;
/*   14:     */ import com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfo;
/*   15:     */ import com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfoSeqHelper;
/*   16:     */ import com.ffusion.ffs.bpw.interfaces.FundsAllocRslt;
/*   17:     */ import com.ffusion.ffs.bpw.interfaces.FundsAllocRsltSeqHelper;
/*   18:     */ import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
/*   19:     */ import com.ffusion.ffs.bpw.interfaces.IntraTrnInfoSeqHelper;
/*   20:     */ import com.ffusion.ffs.bpw.interfaces.IntraTrnRslt;
/*   21:     */ import com.ffusion.ffs.bpw.interfaces.IntraTrnRsltSeqHelper;
/*   22:     */ import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
/*   23:     */ import com.ffusion.ffs.bpw.interfaces.PayeeInfoSeqHelper;
/*   24:     */ import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
/*   25:     */ import com.ffusion.ffs.bpw.interfaces.PayeeRslt;
/*   26:     */ import com.ffusion.ffs.bpw.interfaces.PayeeRsltSeqHelper;
/*   27:     */ import com.ffusion.ffs.bpw.interfaces.PmtInfo;
/*   28:     */ import com.ffusion.ffs.bpw.interfaces.PmtInfoSeqHelper;
/*   29:     */ import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
/*   30:     */ import com.ffusion.ffs.bpw.interfaces.PmtTrnRsltSeqHelper;
/*   31:     */ import com.ffusion.ffs.bpw.interfaces.RecIntraTrnInfo;
/*   32:     */ import com.ffusion.ffs.bpw.interfaces.RecIntraTrnInfoSeqHelper;
/*   33:     */ import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
/*   34:     */ import com.ffusion.ffs.bpw.interfaces.RecPmtInfoSeqHelper;
/*   35:     */ import com.ffusion.ffs.bpw.util.AccountTypesMap;
/*   36:     */ import com.ffusion.ffs.interfaces.FFSException;
/*   37:     */ import com.ffusion.ffs.interfaces.FFSExceptionHelper;
/*   38:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserData;
/*   39:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserDataHelper;
/*   40:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1;
/*   41:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1Helper;
/*   42:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1;
/*   43:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1Helper;
/*   44:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1SeqHelper;
/*   45:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1;
/*   46:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1Helper;
/*   47:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1;
/*   48:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1Helper;
/*   49:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1;
/*   50:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1Helper;
/*   51:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1;
/*   52:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1Helper;
/*   53:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1;
/*   54:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1Helper;
/*   55:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1;
/*   56:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1Helper;
/*   57:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRqV1;
/*   58:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRqV1Helper;
/*   59:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRsV1;
/*   60:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRsV1Helper;
/*   61:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1;
/*   62:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1Helper;
/*   63:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1;
/*   64:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1Helper;
/*   65:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1SeqHelper;
/*   66:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1;
/*   67:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1Helper;
/*   68:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
/*   69:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Helper;
/*   70:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1;
/*   71:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1Helper;
/*   72:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1;
/*   73:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1Helper;
/*   74:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1;
/*   75:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1Helper;
/*   76:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1;
/*   77:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1Helper;
/*   78:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1;
/*   79:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1Helper;
/*   80:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1;
/*   81:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1Helper;
/*   82:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1;
/*   83:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1Helper;
/*   84:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1;
/*   85:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1Helper;
/*   86:     */ import com.sybase.CORBA.LocalFrame;
/*   87:     */ import com.sybase.CORBA.LocalStack;
/*   88:     */ import com.sybase.CORBA.ObjectVal;
/*   89:     */ import com.sybase.CORBA.UserException;
/*   90:     */ import com.sybase.CORBA._ServerRequest;
/*   91:     */ import com.sybase.CORBA.iiop.Connection;
/*   92:     */ import com.sybase.ejb.SessionContext;
/*   93:     */ import com.sybase.ejb.cts.StringSeqHelper;
/*   94:     */ import com.sybase.jaguar.server.Jaguar;
/*   95:     */ import java.util.ArrayList;
/*   96:     */ import java.util.HashMap;
/*   97:     */ import org.omg.CORBA.portable.InputStream;
/*   98:     */ import org.omg.CORBA.portable.OutputStream;
/*   99:     */ 
/*  100:     */ public abstract class _sk_OFX200BPWServices_OFX200BPWServicesBean
/*  101:     */ {
/*  102:  17 */   private static HashMap _methods = new HashMap(176);
/*  103:     */   private static HashMap _localMethods;
/*  104:     */   private static HashMap _localMethods2;
/*  105:     */   private static final String _RESET = "org.omg.CORBA.BAD_OPERATION";
/*  106:     */   
/*  107:     */   static
/*  108:     */   {
/*  109:  18 */     _methods.put("setSessionContext", new Integer(0));
/*  110:  19 */     _methods.put("ejbCreate", new Integer(1));
/*  111:  20 */     _methods.put("ejbActivate", new Integer(2));
/*  112:  21 */     _methods.put("ejbPassivate", new Integer(3));
/*  113:  22 */     _methods.put("ejbRemove", new Integer(4));
/*  114:  23 */     _methods.put("addCustomers", new Integer(5));
/*  115:  24 */     _methods.put("modifyCustomers", new Integer(6));
/*  116:  25 */     _methods.put("deleteCustomers__StringSeq", new Integer(7));
/*  117:  26 */     _methods.put("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(7));
/*  118:  27 */     _methods.put("deleteCustomers__StringSeq__long", new Integer(8));
/*  119:  28 */     _methods.put("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long", new Integer(8));
/*  120:  29 */     _methods.put("deactivateCustomers", new Integer(9));
/*  121:  30 */     _methods.put("activateCustomers", new Integer(10));
/*  122:  31 */     _methods.put("getCustomersInfo", new Integer(11));
/*  123:  32 */     _methods.put("getCustomerByType", new Integer(12));
/*  124:  33 */     _methods.put("getCustomerByFI", new Integer(13));
/*  125:  34 */     _methods.put("getCustomerByCategory", new Integer(14));
/*  126:  35 */     _methods.put("getCustomerByGroup", new Integer(15));
/*  127:  36 */     _methods.put("getCustomerByTypeAndFI", new Integer(16));
/*  128:  37 */     _methods.put("getCustomerByCategoryAndFI", new Integer(17));
/*  129:  38 */     _methods.put("getCustomerByGroupAndFI", new Integer(18));
/*  130:  39 */     _methods.put("getLinkedPayees", new Integer(19));
/*  131:  40 */     _methods.put("_get_linkedPayees", new Integer(19));
/*  132:  41 */     _methods.put("getMostUsedPayees", new Integer(20));
/*  133:  42 */     _methods.put("getPreferredPayees", new Integer(21));
/*  134:  43 */     _methods.put("getPendingPmtsByCustomerID", new Integer(22));
/*  135:  44 */     _methods.put("getPendingPmtsAndHistoryByCustomerID", new Integer(23));
/*  136:  45 */     _methods.put("getPendingIntrasByCustomerID", new Integer(24));
/*  137:  46 */     _methods.put("getPendingIntrasAndHistoryByCustomerID", new Integer(25));
/*  138:  47 */     _methods.put("getPendingPmts", new Integer(26));
/*  139:  48 */     _methods.put("getPendingIntras", new Integer(27));
/*  140:  49 */     _methods.put("getPmtStatus", new Integer(28));
/*  141:  50 */     _methods.put("checkPayeeEditMask", new Integer(29));
/*  142:  51 */     _methods.put("processIntraTrnRslt", new Integer(30));
/*  143:  52 */     _methods.put("processPmtTrnRslt", new Integer(31));
/*  144:  53 */     _methods.put("processCustPayeeRslt", new Integer(32));
/*  145:  54 */     _methods.put("processFundAllocRslt", new Integer(33));
/*  146:  55 */     _methods.put("processFundRevertRslt", new Integer(34));
/*  147:  56 */     _methods.put("processPayeeRslt", new Integer(35));
/*  148:  57 */     _methods.put("addPayeeFromBackend", new Integer(36));
/*  149:  58 */     _methods.put("updatePayeeFromBackend", new Integer(37));
/*  150:  59 */     _methods.put("addPayeeRouteInfo", new Integer(38));
/*  151:  60 */     _methods.put("processIntraSyncRqV1", new Integer(39));
/*  152:  61 */     _methods.put("processIntraTrnRqV1", new Integer(40));
/*  153:  62 */     _methods.put("processPayeeSyncRqV1", new Integer(41));
/*  154:  63 */     _methods.put("processPayeeTrnRqV1", new Integer(42));
/*  155:  64 */     _methods.put("processPmtInqTrnRqV1", new Integer(43));
/*  156:  65 */     _methods.put("processPmtSyncRqV1", new Integer(44));
/*  157:  66 */     _methods.put("processPmtTrnRqV1", new Integer(45));
/*  158:  67 */     _methods.put("processRecIntraSyncRqV1", new Integer(46));
/*  159:  68 */     _methods.put("processRecIntraTrnRqV1", new Integer(47));
/*  160:  69 */     _methods.put("processRecPmtSyncRqV1", new Integer(48));
/*  161:  70 */     _methods.put("processRecPmtTrnRqV1", new Integer(49));
/*  162:  71 */     _methods.put("getPayeeNames__string__long", new Integer(50));
/*  163:  72 */     _methods.put("getPayeeNames__CORBA_WStringValue__long", new Integer(50));
/*  164:  73 */     _methods.put("getPayeeNames__string", new Integer(51));
/*  165:  74 */     _methods.put("getPayeeNames__CORBA_WStringValue", new Integer(51));
/*  166:  75 */     _methods.put("getPayeeIDs", new Integer(52));
/*  167:  76 */     _methods.put("getPayees__string__long", new Integer(53));
/*  168:  77 */     _methods.put("getPayees__CORBA_WStringValue__long", new Integer(53));
/*  169:  78 */     _methods.put("getPayees__string", new Integer(54));
/*  170:  79 */     _methods.put("getPayees__CORBA_WStringValue", new Integer(54));
/*  171:  80 */     _methods.put("searchGlobalPayees", new Integer(55));
/*  172:  81 */     _methods.put("updatePayee__PayeeInfo__long", new Integer(56));
/*  173:  82 */     _methods.put("updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__long", new Integer(56));
/*  174:  83 */     _methods.put("updatePayee__PayeeInfo__PayeeRouteInfo", new Integer(57));
/*  175:  84 */     _methods.put("updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeRouteInfo", new Integer(57));
/*  176:  85 */     _methods.put("deletePayee", new Integer(58));
/*  177:  86 */     _methods.put("deletePayees", new Integer(59));
/*  178:  87 */     _methods.put("findPayeeByID", new Integer(60));
/*  179:  88 */     _methods.put("setPayeeStatus", new Integer(61));
/*  180:  89 */     _methods.put("getSmartDate", new Integer(62));
/*  181:  90 */     _methods.put("getGlobalPayees", new Integer(63));
/*  182:  91 */     _methods.put("addPayee", new Integer(64));
/*  183:  92 */     _methods.put("addConsumerCrossRef", new Integer(65));
/*  184:  93 */     _methods.put("deleteConsumerCrossRef", new Integer(66));
/*  185:  94 */     _methods.put("getConsumerCrossRef", new Integer(67));
/*  186:  95 */     _methods.put("addCustomerBankInfo", new Integer(68));
/*  187:  96 */     _methods.put("deleteCustomerBankInfo", new Integer(69));
/*  188:  97 */     _methods.put("getCustomerBankInfo", new Integer(70));
/*  189:  98 */     _methods.put("addCustomerProductAccessInfo", new Integer(71));
/*  190:  99 */     _methods.put("deleteCustomerProductAccessInfo", new Integer(72));
/*  191: 100 */     _methods.put("getCustomerProductAccessInfo", new Integer(73));
/*  192: 101 */     _methods.put("validateMetavanteCustAcctByConsumerID", new Integer(74));
/*  193: 102 */     _methods.put("validateMetavanteCustAcctByCustomerID", new Integer(75));
/*  194: 103 */     _methods.put("getPmtHistory", new Integer(76));
/*  195: 104 */     _methods.put("getIntraHistory", new Integer(77));
/*  196: 105 */     _methods.put("getIntraById__string", new Integer(78));
/*  197: 106 */     _methods.put("getIntraById__CORBA_WStringValue", new Integer(78));
/*  198: 107 */     _methods.put("getIntraById__StringSeq", new Integer(79));
/*  199: 108 */     _methods.put("getIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(79));
/*  200: 109 */     _methods.put("getIntraByRecSrvrTId", new Integer(80));
/*  201: 110 */     _methods.put("getPmtById__StringSeq", new Integer(81));
/*  202: 111 */     _methods.put("getPmtById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(81));
/*  203: 112 */     _methods.put("getPmtById__string", new Integer(82));
/*  204: 113 */     _methods.put("getPmtById__CORBA_WStringValue", new Integer(82));
/*  205: 114 */     _methods.put("getRecPmtById", new Integer(83));
/*  206: 115 */     _methods.put("getRecIntraById__StringSeq", new Integer(84));
/*  207: 116 */     _methods.put("getRecIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(84));
/*  208: 117 */     _methods.put("getRecIntraById__string", new Integer(85));
/*  209: 118 */     _methods.put("getRecIntraById__CORBA_WStringValue", new Integer(85));
/*  210: 119 */     _methods.put("getPayeeByListId", new Integer(86));
/*  211: 120 */     _methods.put("getAccountTypesMap", new Integer(87));
/*  212:     */     
/*  213:     */ 
/*  214:     */ 
/*  215:     */ 
/*  216:     */ 
/*  217:     */ 
/*  218:     */ 
/*  219:     */ 
/*  220: 129 */     _localMethods = new HashMap(176);
/*  221: 130 */     _localMethods2 = new HashMap(176);
/*  222: 131 */     _localMethods.put("#ejbCreate", new Integer(0));
/*  223: 132 */     _localMethods2.put("ejbCreate", new Integer(0));
/*  224: 133 */     _localMethods.put("#ejbRemove", new Integer(1));
/*  225: 134 */     _localMethods2.put("ejbRemove", new Integer(1));
/*  226: 135 */     _localMethods.put("#addCustomers", new Integer(2));
/*  227: 136 */     _localMethods2.put("addCustomers", new Integer(2));
/*  228: 137 */     _localMethods.put("#modifyCustomers", new Integer(3));
/*  229: 138 */     _localMethods2.put("modifyCustomers", new Integer(3));
/*  230: 139 */     _localMethods.put("#deleteCustomers__StringSeq", new Integer(4));
/*  231: 140 */     _localMethods2.put("deleteCustomers__StringSeq", new Integer(4));
/*  232: 141 */     _localMethods.put("#deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(4));
/*  233: 142 */     _localMethods2.put("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(4));
/*  234: 143 */     _localMethods.put("#deleteCustomers__StringSeq__long", new Integer(5));
/*  235: 144 */     _localMethods2.put("deleteCustomers__StringSeq__long", new Integer(5));
/*  236: 145 */     _localMethods.put("#deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long", new Integer(5));
/*  237: 146 */     _localMethods2.put("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long", new Integer(5));
/*  238: 147 */     _localMethods.put("#deactivateCustomers", new Integer(6));
/*  239: 148 */     _localMethods2.put("deactivateCustomers", new Integer(6));
/*  240: 149 */     _localMethods.put("#activateCustomers", new Integer(7));
/*  241: 150 */     _localMethods2.put("activateCustomers", new Integer(7));
/*  242: 151 */     _localMethods.put("#getCustomersInfo", new Integer(8));
/*  243: 152 */     _localMethods2.put("getCustomersInfo", new Integer(8));
/*  244: 153 */     _localMethods.put("#getCustomerByType", new Integer(9));
/*  245: 154 */     _localMethods2.put("getCustomerByType", new Integer(9));
/*  246: 155 */     _localMethods.put("#getCustomerByFI", new Integer(10));
/*  247: 156 */     _localMethods2.put("getCustomerByFI", new Integer(10));
/*  248: 157 */     _localMethods.put("#getCustomerByCategory", new Integer(11));
/*  249: 158 */     _localMethods2.put("getCustomerByCategory", new Integer(11));
/*  250: 159 */     _localMethods.put("#getCustomerByGroup", new Integer(12));
/*  251: 160 */     _localMethods2.put("getCustomerByGroup", new Integer(12));
/*  252: 161 */     _localMethods.put("#getCustomerByTypeAndFI", new Integer(13));
/*  253: 162 */     _localMethods2.put("getCustomerByTypeAndFI", new Integer(13));
/*  254: 163 */     _localMethods.put("#getCustomerByCategoryAndFI", new Integer(14));
/*  255: 164 */     _localMethods2.put("getCustomerByCategoryAndFI", new Integer(14));
/*  256: 165 */     _localMethods.put("#getCustomerByGroupAndFI", new Integer(15));
/*  257: 166 */     _localMethods2.put("getCustomerByGroupAndFI", new Integer(15));
/*  258: 167 */     _localMethods.put("#getLinkedPayees", new Integer(16));
/*  259: 168 */     _localMethods2.put("getLinkedPayees", new Integer(16));
/*  260: 169 */     _localMethods.put("#_get_linkedPayees", new Integer(16));
/*  261: 170 */     _localMethods2.put("_get_linkedPayees", new Integer(16));
/*  262: 171 */     _localMethods.put("#getMostUsedPayees", new Integer(17));
/*  263: 172 */     _localMethods2.put("getMostUsedPayees", new Integer(17));
/*  264: 173 */     _localMethods.put("#getPreferredPayees", new Integer(18));
/*  265: 174 */     _localMethods2.put("getPreferredPayees", new Integer(18));
/*  266: 175 */     _localMethods.put("#getPendingPmtsByCustomerID", new Integer(19));
/*  267: 176 */     _localMethods2.put("getPendingPmtsByCustomerID", new Integer(19));
/*  268: 177 */     _localMethods.put("#getPendingPmtsAndHistoryByCustomerID", new Integer(20));
/*  269: 178 */     _localMethods2.put("getPendingPmtsAndHistoryByCustomerID", new Integer(20));
/*  270: 179 */     _localMethods.put("#getPendingIntrasByCustomerID", new Integer(21));
/*  271: 180 */     _localMethods2.put("getPendingIntrasByCustomerID", new Integer(21));
/*  272: 181 */     _localMethods.put("#getPendingIntrasAndHistoryByCustomerID", new Integer(22));
/*  273: 182 */     _localMethods2.put("getPendingIntrasAndHistoryByCustomerID", new Integer(22));
/*  274: 183 */     _localMethods.put("#getPendingPmts", new Integer(23));
/*  275: 184 */     _localMethods2.put("getPendingPmts", new Integer(23));
/*  276: 185 */     _localMethods.put("#getPendingIntras", new Integer(24));
/*  277: 186 */     _localMethods2.put("getPendingIntras", new Integer(24));
/*  278: 187 */     _localMethods.put("#getPmtStatus", new Integer(25));
/*  279: 188 */     _localMethods2.put("getPmtStatus", new Integer(25));
/*  280: 189 */     _localMethods.put("#checkPayeeEditMask", new Integer(26));
/*  281: 190 */     _localMethods2.put("checkPayeeEditMask", new Integer(26));
/*  282: 191 */     _localMethods.put("#processIntraTrnRslt", new Integer(27));
/*  283: 192 */     _localMethods2.put("processIntraTrnRslt", new Integer(27));
/*  284: 193 */     _localMethods.put("#processPmtTrnRslt", new Integer(28));
/*  285: 194 */     _localMethods2.put("processPmtTrnRslt", new Integer(28));
/*  286: 195 */     _localMethods.put("#processCustPayeeRslt", new Integer(29));
/*  287: 196 */     _localMethods2.put("processCustPayeeRslt", new Integer(29));
/*  288: 197 */     _localMethods.put("#processFundAllocRslt", new Integer(30));
/*  289: 198 */     _localMethods2.put("processFundAllocRslt", new Integer(30));
/*  290: 199 */     _localMethods.put("#processFundRevertRslt", new Integer(31));
/*  291: 200 */     _localMethods2.put("processFundRevertRslt", new Integer(31));
/*  292: 201 */     _localMethods.put("#processPayeeRslt", new Integer(32));
/*  293: 202 */     _localMethods2.put("processPayeeRslt", new Integer(32));
/*  294: 203 */     _localMethods.put("#addPayeeFromBackend", new Integer(33));
/*  295: 204 */     _localMethods2.put("addPayeeFromBackend", new Integer(33));
/*  296: 205 */     _localMethods.put("#updatePayeeFromBackend", new Integer(34));
/*  297: 206 */     _localMethods2.put("updatePayeeFromBackend", new Integer(34));
/*  298: 207 */     _localMethods.put("#addPayeeRouteInfo", new Integer(35));
/*  299: 208 */     _localMethods2.put("addPayeeRouteInfo", new Integer(35));
/*  300: 209 */     _localMethods.put("#processIntraSyncRqV1", new Integer(36));
/*  301: 210 */     _localMethods2.put("processIntraSyncRqV1", new Integer(36));
/*  302: 211 */     _localMethods.put("#processIntraTrnRqV1", new Integer(37));
/*  303: 212 */     _localMethods2.put("processIntraTrnRqV1", new Integer(37));
/*  304: 213 */     _localMethods.put("#processPayeeSyncRqV1", new Integer(38));
/*  305: 214 */     _localMethods2.put("processPayeeSyncRqV1", new Integer(38));
/*  306: 215 */     _localMethods.put("#processPayeeTrnRqV1", new Integer(39));
/*  307: 216 */     _localMethods2.put("processPayeeTrnRqV1", new Integer(39));
/*  308: 217 */     _localMethods.put("#processPmtInqTrnRqV1", new Integer(40));
/*  309: 218 */     _localMethods2.put("processPmtInqTrnRqV1", new Integer(40));
/*  310: 219 */     _localMethods.put("#processPmtSyncRqV1", new Integer(41));
/*  311: 220 */     _localMethods2.put("processPmtSyncRqV1", new Integer(41));
/*  312: 221 */     _localMethods.put("#processPmtTrnRqV1", new Integer(42));
/*  313: 222 */     _localMethods2.put("processPmtTrnRqV1", new Integer(42));
/*  314: 223 */     _localMethods.put("#processRecIntraSyncRqV1", new Integer(43));
/*  315: 224 */     _localMethods2.put("processRecIntraSyncRqV1", new Integer(43));
/*  316: 225 */     _localMethods.put("#processRecIntraTrnRqV1", new Integer(44));
/*  317: 226 */     _localMethods2.put("processRecIntraTrnRqV1", new Integer(44));
/*  318: 227 */     _localMethods.put("#processRecPmtSyncRqV1", new Integer(45));
/*  319: 228 */     _localMethods2.put("processRecPmtSyncRqV1", new Integer(45));
/*  320: 229 */     _localMethods.put("#processRecPmtTrnRqV1", new Integer(46));
/*  321: 230 */     _localMethods2.put("processRecPmtTrnRqV1", new Integer(46));
/*  322: 231 */     _localMethods.put("#getPayeeNames__string__long", new Integer(47));
/*  323: 232 */     _localMethods2.put("getPayeeNames__string__long", new Integer(47));
/*  324: 233 */     _localMethods.put("#getPayeeNames__CORBA_WStringValue__long", new Integer(47));
/*  325: 234 */     _localMethods2.put("getPayeeNames__CORBA_WStringValue__long", new Integer(47));
/*  326: 235 */     _localMethods.put("#getPayeeNames__string", new Integer(48));
/*  327: 236 */     _localMethods2.put("getPayeeNames__string", new Integer(48));
/*  328: 237 */     _localMethods.put("#getPayeeNames__CORBA_WStringValue", new Integer(48));
/*  329: 238 */     _localMethods2.put("getPayeeNames__CORBA_WStringValue", new Integer(48));
/*  330: 239 */     _localMethods.put("#getPayeeIDs", new Integer(49));
/*  331: 240 */     _localMethods2.put("getPayeeIDs", new Integer(49));
/*  332: 241 */     _localMethods.put("#getPayees__string__long", new Integer(50));
/*  333: 242 */     _localMethods2.put("getPayees__string__long", new Integer(50));
/*  334: 243 */     _localMethods.put("#getPayees__CORBA_WStringValue__long", new Integer(50));
/*  335: 244 */     _localMethods2.put("getPayees__CORBA_WStringValue__long", new Integer(50));
/*  336: 245 */     _localMethods.put("#getPayees__string", new Integer(51));
/*  337: 246 */     _localMethods2.put("getPayees__string", new Integer(51));
/*  338: 247 */     _localMethods.put("#getPayees__CORBA_WStringValue", new Integer(51));
/*  339: 248 */     _localMethods2.put("getPayees__CORBA_WStringValue", new Integer(51));
/*  340: 249 */     _localMethods.put("#searchGlobalPayees", new Integer(52));
/*  341: 250 */     _localMethods2.put("searchGlobalPayees", new Integer(52));
/*  342: 251 */     _localMethods.put("#updatePayee__PayeeInfo__long", new Integer(53));
/*  343: 252 */     _localMethods2.put("updatePayee__PayeeInfo__long", new Integer(53));
/*  344: 253 */     _localMethods.put("#updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__long", new Integer(53));
/*  345: 254 */     _localMethods2.put("updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__long", new Integer(53));
/*  346: 255 */     _localMethods.put("#updatePayee__PayeeInfo__PayeeRouteInfo", new Integer(54));
/*  347: 256 */     _localMethods2.put("updatePayee__PayeeInfo__PayeeRouteInfo", new Integer(54));
/*  348: 257 */     _localMethods.put("#updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeRouteInfo", new Integer(54));
/*  349: 258 */     _localMethods2.put("updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeRouteInfo", new Integer(54));
/*  350: 259 */     _localMethods.put("#deletePayee", new Integer(55));
/*  351: 260 */     _localMethods2.put("deletePayee", new Integer(55));
/*  352: 261 */     _localMethods.put("#deletePayees", new Integer(56));
/*  353: 262 */     _localMethods2.put("deletePayees", new Integer(56));
/*  354: 263 */     _localMethods.put("#findPayeeByID", new Integer(57));
/*  355: 264 */     _localMethods2.put("findPayeeByID", new Integer(57));
/*  356: 265 */     _localMethods.put("#setPayeeStatus", new Integer(58));
/*  357: 266 */     _localMethods2.put("setPayeeStatus", new Integer(58));
/*  358: 267 */     _localMethods.put("#getSmartDate", new Integer(59));
/*  359: 268 */     _localMethods2.put("getSmartDate", new Integer(59));
/*  360: 269 */     _localMethods.put("#getGlobalPayees", new Integer(60));
/*  361: 270 */     _localMethods2.put("getGlobalPayees", new Integer(60));
/*  362: 271 */     _localMethods.put("#addPayee", new Integer(61));
/*  363: 272 */     _localMethods2.put("addPayee", new Integer(61));
/*  364: 273 */     _localMethods.put("#addConsumerCrossRef", new Integer(62));
/*  365: 274 */     _localMethods2.put("addConsumerCrossRef", new Integer(62));
/*  366: 275 */     _localMethods.put("#deleteConsumerCrossRef", new Integer(63));
/*  367: 276 */     _localMethods2.put("deleteConsumerCrossRef", new Integer(63));
/*  368: 277 */     _localMethods.put("#getConsumerCrossRef", new Integer(64));
/*  369: 278 */     _localMethods2.put("getConsumerCrossRef", new Integer(64));
/*  370: 279 */     _localMethods.put("#addCustomerBankInfo", new Integer(65));
/*  371: 280 */     _localMethods2.put("addCustomerBankInfo", new Integer(65));
/*  372: 281 */     _localMethods.put("#deleteCustomerBankInfo", new Integer(66));
/*  373: 282 */     _localMethods2.put("deleteCustomerBankInfo", new Integer(66));
/*  374: 283 */     _localMethods.put("#getCustomerBankInfo", new Integer(67));
/*  375: 284 */     _localMethods2.put("getCustomerBankInfo", new Integer(67));
/*  376: 285 */     _localMethods.put("#addCustomerProductAccessInfo", new Integer(68));
/*  377: 286 */     _localMethods2.put("addCustomerProductAccessInfo", new Integer(68));
/*  378: 287 */     _localMethods.put("#deleteCustomerProductAccessInfo", new Integer(69));
/*  379: 288 */     _localMethods2.put("deleteCustomerProductAccessInfo", new Integer(69));
/*  380: 289 */     _localMethods.put("#getCustomerProductAccessInfo", new Integer(70));
/*  381: 290 */     _localMethods2.put("getCustomerProductAccessInfo", new Integer(70));
/*  382: 291 */     _localMethods.put("#validateMetavanteCustAcctByConsumerID", new Integer(71));
/*  383: 292 */     _localMethods2.put("validateMetavanteCustAcctByConsumerID", new Integer(71));
/*  384: 293 */     _localMethods.put("#validateMetavanteCustAcctByCustomerID", new Integer(72));
/*  385: 294 */     _localMethods2.put("validateMetavanteCustAcctByCustomerID", new Integer(72));
/*  386: 295 */     _localMethods.put("#getPmtHistory", new Integer(73));
/*  387: 296 */     _localMethods2.put("getPmtHistory", new Integer(73));
/*  388: 297 */     _localMethods.put("#getIntraHistory", new Integer(74));
/*  389: 298 */     _localMethods2.put("getIntraHistory", new Integer(74));
/*  390: 299 */     _localMethods.put("#getIntraById__string", new Integer(75));
/*  391: 300 */     _localMethods2.put("getIntraById__string", new Integer(75));
/*  392: 301 */     _localMethods.put("#getIntraById__CORBA_WStringValue", new Integer(75));
/*  393: 302 */     _localMethods2.put("getIntraById__CORBA_WStringValue", new Integer(75));
/*  394: 303 */     _localMethods.put("#getIntraById__StringSeq", new Integer(76));
/*  395: 304 */     _localMethods2.put("getIntraById__StringSeq", new Integer(76));
/*  396: 305 */     _localMethods.put("#getIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(76));
/*  397: 306 */     _localMethods2.put("getIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(76));
/*  398: 307 */     _localMethods.put("#getIntraByRecSrvrTId", new Integer(77));
/*  399: 308 */     _localMethods2.put("getIntraByRecSrvrTId", new Integer(77));
/*  400: 309 */     _localMethods.put("#getPmtById__StringSeq", new Integer(78));
/*  401: 310 */     _localMethods2.put("getPmtById__StringSeq", new Integer(78));
/*  402: 311 */     _localMethods.put("#getPmtById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(78));
/*  403: 312 */     _localMethods2.put("getPmtById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(78));
/*  404: 313 */     _localMethods.put("#getPmtById__string", new Integer(79));
/*  405: 314 */     _localMethods2.put("getPmtById__string", new Integer(79));
/*  406: 315 */     _localMethods.put("#getPmtById__CORBA_WStringValue", new Integer(79));
/*  407: 316 */     _localMethods2.put("getPmtById__CORBA_WStringValue", new Integer(79));
/*  408: 317 */     _localMethods.put("#getRecPmtById", new Integer(80));
/*  409: 318 */     _localMethods2.put("getRecPmtById", new Integer(80));
/*  410: 319 */     _localMethods.put("#getRecIntraById__StringSeq", new Integer(81));
/*  411: 320 */     _localMethods2.put("getRecIntraById__StringSeq", new Integer(81));
/*  412: 321 */     _localMethods.put("#getRecIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(81));
/*  413: 322 */     _localMethods2.put("getRecIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(81));
/*  414: 323 */     _localMethods.put("#getRecIntraById__string", new Integer(82));
/*  415: 324 */     _localMethods2.put("getRecIntraById__string", new Integer(82));
/*  416: 325 */     _localMethods.put("#getRecIntraById__CORBA_WStringValue", new Integer(82));
/*  417: 326 */     _localMethods2.put("getRecIntraById__CORBA_WStringValue", new Integer(82));
/*  418: 327 */     _localMethods.put("#getPayeeByListId", new Integer(83));
/*  419: 328 */     _localMethods2.put("getPayeeByListId", new Integer(83));
/*  420: 329 */     _localMethods.put("#getAccountTypesMap", new Integer(84));
/*  421: 330 */     _localMethods2.put("getAccountTypesMap", new Integer(84));
/*  422:     */   }
/*  423:     */   
/*  424:     */   public static Object create()
/*  425:     */     throws Exception
/*  426:     */   {
/*  427: 338 */     return new OFX200BPWServicesBean();
/*  428:     */   }
/*  429:     */   
/*  430:     */   public static String invoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream)
/*  431:     */   {
/*  432: 347 */     return invoke(paramObject, paramString, paramInputStream, paramOutputStream, 0);
/*  433:     */   }
/*  434:     */   
/*  435:     */   public static String invoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/*  436:     */   {
/*  437: 357 */     if ((paramString.startsWith("#")) || (LocalStack.getCurrent().isArgsOnLocal())) {
/*  438: 359 */       return localInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/*  439:     */     }
/*  440: 363 */     return remoteInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/*  441:     */   }
/*  442:     */   
/*  443:     */   public static String remoteInvoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/*  444:     */   {
/*  445: 374 */     _ServerRequest local_ServerRequest = new _ServerRequest(paramInputStream, paramOutputStream, (paramInt & 0x1) != 0);
/*  446: 375 */     OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)paramObject;
/*  447: 376 */     Integer localInteger = (Integer)_methods.get(paramString);
/*  448: 377 */     if (localInteger == null) {
/*  449: 379 */       return "org.omg.CORBA.BAD_OPERATION";
/*  450:     */     }
/*  451:     */     int j;
/*  452:     */     Object localObject1;
/*  453:     */     Object localObject3;
/*  454:     */     int k;
/*  455:     */     Object localObject5;
/*  456:     */     int i2;
/*  457:     */     Object localObject2;
/*  458:     */     Object localObject4;
/*  459: 381 */     switch (localInteger.intValue())
/*  460:     */     {
/*  461:     */     case 0: 
/*  462:     */       try
/*  463:     */       {
/*  464: 388 */         SessionContext localSessionContext = new SessionContext(ObjectContextHelper.read(paramInputStream));
/*  465: 389 */         localOFX200BPWServicesBean
/*  466: 390 */           .setSessionContext(
/*  467: 391 */           localSessionContext);
/*  468:     */       }
/*  469:     */       catch (Throwable localThrowable1)
/*  470:     */       {
/*  471: 396 */         localThrowable1.printStackTrace(Jaguar.log);
/*  472: 397 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/*  473: 398 */         return localThrowable1.getClass().getName();
/*  474:     */       }
/*  475:     */     case 1: 
/*  476:     */       try
/*  477:     */       {
/*  478: 407 */         localOFX200BPWServicesBean.ejbCreate();
/*  479:     */       }
/*  480:     */       catch (Throwable localThrowable2)
/*  481:     */       {
/*  482: 412 */         localThrowable2.printStackTrace(Jaguar.log);
/*  483: 413 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/*  484: 414 */         return localThrowable2.getClass().getName();
/*  485:     */       }
/*  486:     */     case 2: 
/*  487:     */       try
/*  488:     */       {
/*  489: 423 */         localOFX200BPWServicesBean.ejbActivate();
/*  490:     */       }
/*  491:     */       catch (Throwable localThrowable3)
/*  492:     */       {
/*  493: 428 */         localThrowable3.printStackTrace(Jaguar.log);
/*  494: 429 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/*  495: 430 */         return localThrowable3.getClass().getName();
/*  496:     */       }
/*  497:     */     case 3: 
/*  498:     */       try
/*  499:     */       {
/*  500: 439 */         localOFX200BPWServicesBean.ejbPassivate();
/*  501:     */       }
/*  502:     */       catch (Throwable localThrowable4)
/*  503:     */       {
/*  504: 444 */         localThrowable4.printStackTrace(Jaguar.log);
/*  505: 445 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/*  506: 446 */         return localThrowable4.getClass().getName();
/*  507:     */       }
/*  508:     */     case 4: 
/*  509:     */       try
/*  510:     */       {
/*  511: 455 */         localOFX200BPWServicesBean.ejbRemove();
/*  512:     */       }
/*  513:     */       catch (Throwable localThrowable5)
/*  514:     */       {
/*  515: 460 */         localThrowable5.printStackTrace(Jaguar.log);
/*  516: 461 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/*  517: 462 */         return localThrowable5.getClass().getName();
/*  518:     */       }
/*  519:     */     case 5: 
/*  520:     */       try
/*  521:     */       {
/*  522:     */         CustomerInfo[] arrayOfCustomerInfo1;
/*  523: 471 */         if (local_ServerRequest.isRMI()) {
/*  524: 471 */           arrayOfCustomerInfo1 = (CustomerInfo[])local_ServerRequest.read_value(new CustomerInfo[0].getClass());
/*  525:     */         } else {
/*  526: 471 */           arrayOfCustomerInfo1 = CustomerInfoSeqHelper.read(paramInputStream);
/*  527:     */         }
/*  528: 472 */         j = 
/*  529: 473 */           localOFX200BPWServicesBean.addCustomers(
/*  530: 474 */           arrayOfCustomerInfo1);
/*  531:     */         
/*  532: 476 */         paramOutputStream.write_long(j);
/*  533:     */       }
/*  534:     */       catch (Throwable localThrowable6)
/*  535:     */       {
/*  536: 480 */         localThrowable6.printStackTrace(Jaguar.log);
/*  537: 481 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/*  538: 482 */         return localThrowable6.getClass().getName();
/*  539:     */       }
/*  540:     */     case 6: 
/*  541:     */       try
/*  542:     */       {
/*  543:     */         CustomerInfo[] arrayOfCustomerInfo2;
/*  544: 491 */         if (local_ServerRequest.isRMI()) {
/*  545: 491 */           arrayOfCustomerInfo2 = (CustomerInfo[])local_ServerRequest.read_value(new CustomerInfo[0].getClass());
/*  546:     */         } else {
/*  547: 491 */           arrayOfCustomerInfo2 = CustomerInfoSeqHelper.read(paramInputStream);
/*  548:     */         }
/*  549: 492 */         j = 
/*  550: 493 */           localOFX200BPWServicesBean.modifyCustomers(
/*  551: 494 */           arrayOfCustomerInfo2);
/*  552:     */         
/*  553: 496 */         paramOutputStream.write_long(j);
/*  554:     */       }
/*  555:     */       catch (Throwable localThrowable7)
/*  556:     */       {
/*  557: 500 */         localThrowable7.printStackTrace(Jaguar.log);
/*  558: 501 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/*  559: 502 */         return localThrowable7.getClass().getName();
/*  560:     */       }
/*  561:     */     case 7: 
/*  562:     */       try
/*  563:     */       {
/*  564:     */         String[] arrayOfString1;
/*  565: 511 */         if (local_ServerRequest.isRMI()) {
/*  566: 511 */           arrayOfString1 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*  567:     */         } else {
/*  568: 511 */           arrayOfString1 = StringSeqHelper.read(paramInputStream);
/*  569:     */         }
/*  570: 512 */         j = 
/*  571: 513 */           localOFX200BPWServicesBean.deleteCustomers(
/*  572: 514 */           arrayOfString1);
/*  573:     */         
/*  574: 516 */         paramOutputStream.write_long(j);
/*  575:     */       }
/*  576:     */       catch (Throwable localThrowable8)
/*  577:     */       {
/*  578: 520 */         localThrowable8.printStackTrace(Jaguar.log);
/*  579: 521 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/*  580: 522 */         return localThrowable8.getClass().getName();
/*  581:     */       }
/*  582:     */     case 8: 
/*  583:     */       try
/*  584:     */       {
/*  585:     */         String[] arrayOfString2;
/*  586: 531 */         if (local_ServerRequest.isRMI()) {
/*  587: 531 */           arrayOfString2 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*  588:     */         } else {
/*  589: 531 */           arrayOfString2 = StringSeqHelper.read(paramInputStream);
/*  590:     */         }
/*  591: 533 */         j = paramInputStream.read_long();
/*  592: 534 */         int n = localOFX200BPWServicesBean
/*  593: 535 */           .deleteCustomers(
/*  594: 536 */           arrayOfString2, 
/*  595: 537 */           j);
/*  596:     */         
/*  597: 539 */         paramOutputStream.write_long(n);
/*  598:     */       }
/*  599:     */       catch (Throwable localThrowable9)
/*  600:     */       {
/*  601: 543 */         localThrowable9.printStackTrace(Jaguar.log);
/*  602: 544 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/*  603: 545 */         return localThrowable9.getClass().getName();
/*  604:     */       }
/*  605:     */     case 9: 
/*  606:     */       try
/*  607:     */       {
/*  608:     */         String[] arrayOfString3;
/*  609: 554 */         if (local_ServerRequest.isRMI()) {
/*  610: 554 */           arrayOfString3 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*  611:     */         } else {
/*  612: 554 */           arrayOfString3 = StringSeqHelper.read(paramInputStream);
/*  613:     */         }
/*  614: 555 */         j = 
/*  615: 556 */           localOFX200BPWServicesBean.deactivateCustomers(
/*  616: 557 */           arrayOfString3);
/*  617:     */         
/*  618: 559 */         paramOutputStream.write_long(j);
/*  619:     */       }
/*  620:     */       catch (Throwable localThrowable10)
/*  621:     */       {
/*  622: 563 */         localThrowable10.printStackTrace(Jaguar.log);
/*  623: 564 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/*  624: 565 */         return localThrowable10.getClass().getName();
/*  625:     */       }
/*  626:     */     case 10: 
/*  627:     */       try
/*  628:     */       {
/*  629:     */         String[] arrayOfString4;
/*  630: 574 */         if (local_ServerRequest.isRMI()) {
/*  631: 574 */           arrayOfString4 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*  632:     */         } else {
/*  633: 574 */           arrayOfString4 = StringSeqHelper.read(paramInputStream);
/*  634:     */         }
/*  635: 575 */         j = 
/*  636: 576 */           localOFX200BPWServicesBean.activateCustomers(
/*  637: 577 */           arrayOfString4);
/*  638:     */         
/*  639: 579 */         paramOutputStream.write_long(j);
/*  640:     */       }
/*  641:     */       catch (Throwable localThrowable11)
/*  642:     */       {
/*  643: 583 */         localThrowable11.printStackTrace(Jaguar.log);
/*  644: 584 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/*  645: 585 */         return localThrowable11.getClass().getName();
/*  646:     */       }
/*  647:     */     case 11: 
/*  648:     */       try
/*  649:     */       {
/*  650:     */         String[] arrayOfString5;
/*  651: 594 */         if (local_ServerRequest.isRMI()) {
/*  652: 594 */           arrayOfString5 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*  653:     */         } else {
/*  654: 594 */           arrayOfString5 = StringSeqHelper.read(paramInputStream);
/*  655:     */         }
/*  656: 595 */         localObject1 = 
/*  657: 596 */           localOFX200BPWServicesBean.getCustomersInfo(
/*  658: 597 */           arrayOfString5);
/*  659: 599 */         if (local_ServerRequest.isRMI()) {
/*  660: 599 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*  661:     */         } else {
/*  662: 599 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*  663:     */         }
/*  664:     */       }
/*  665:     */       catch (Throwable localThrowable12)
/*  666:     */       {
/*  667: 603 */         localThrowable12.printStackTrace(Jaguar.log);
/*  668: 604 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/*  669: 605 */         return localThrowable12.getClass().getName();
/*  670:     */       }
/*  671:     */     case 12: 
/*  672:     */       try
/*  673:     */       {
/*  674: 614 */         String str1 = local_ServerRequest.read_string();
/*  675: 615 */         localObject1 = localOFX200BPWServicesBean
/*  676: 616 */           .getCustomerByType(
/*  677: 617 */           str1);
/*  678: 619 */         if (local_ServerRequest.isRMI()) {
/*  679: 619 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*  680:     */         } else {
/*  681: 619 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*  682:     */         }
/*  683:     */       }
/*  684:     */       catch (Throwable localThrowable13)
/*  685:     */       {
/*  686: 623 */         localThrowable13.printStackTrace(Jaguar.log);
/*  687: 624 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/*  688: 625 */         return localThrowable13.getClass().getName();
/*  689:     */       }
/*  690:     */     case 13: 
/*  691:     */       try
/*  692:     */       {
/*  693: 634 */         String str2 = local_ServerRequest.read_string();
/*  694: 635 */         localObject1 = localOFX200BPWServicesBean
/*  695: 636 */           .getCustomerByFI(
/*  696: 637 */           str2);
/*  697: 639 */         if (local_ServerRequest.isRMI()) {
/*  698: 639 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*  699:     */         } else {
/*  700: 639 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*  701:     */         }
/*  702:     */       }
/*  703:     */       catch (Throwable localThrowable14)
/*  704:     */       {
/*  705: 643 */         localThrowable14.printStackTrace(Jaguar.log);
/*  706: 644 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/*  707: 645 */         return localThrowable14.getClass().getName();
/*  708:     */       }
/*  709:     */     case 14: 
/*  710:     */       try
/*  711:     */       {
/*  712: 654 */         String str3 = local_ServerRequest.read_string();
/*  713: 655 */         localObject1 = localOFX200BPWServicesBean
/*  714: 656 */           .getCustomerByCategory(
/*  715: 657 */           str3);
/*  716: 659 */         if (local_ServerRequest.isRMI()) {
/*  717: 659 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*  718:     */         } else {
/*  719: 659 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*  720:     */         }
/*  721:     */       }
/*  722:     */       catch (Throwable localThrowable15)
/*  723:     */       {
/*  724: 663 */         localThrowable15.printStackTrace(Jaguar.log);
/*  725: 664 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/*  726: 665 */         return localThrowable15.getClass().getName();
/*  727:     */       }
/*  728:     */     case 15: 
/*  729:     */       try
/*  730:     */       {
/*  731: 674 */         String str4 = local_ServerRequest.read_string();
/*  732: 675 */         localObject1 = localOFX200BPWServicesBean
/*  733: 676 */           .getCustomerByGroup(
/*  734: 677 */           str4);
/*  735: 679 */         if (local_ServerRequest.isRMI()) {
/*  736: 679 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*  737:     */         } else {
/*  738: 679 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*  739:     */         }
/*  740:     */       }
/*  741:     */       catch (Throwable localThrowable16)
/*  742:     */       {
/*  743: 683 */         localThrowable16.printStackTrace(Jaguar.log);
/*  744: 684 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/*  745: 685 */         return localThrowable16.getClass().getName();
/*  746:     */       }
/*  747:     */     case 16: 
/*  748:     */       try
/*  749:     */       {
/*  750: 694 */         String str5 = local_ServerRequest.read_string();
/*  751:     */         
/*  752: 696 */         localObject1 = local_ServerRequest.read_string();
/*  753: 697 */         localObject3 = localOFX200BPWServicesBean
/*  754: 698 */           .getCustomerByTypeAndFI(
/*  755: 699 */           str5, 
/*  756: 700 */           (String)localObject1);
/*  757: 702 */         if (local_ServerRequest.isRMI()) {
/*  758: 702 */           local_ServerRequest.write_value(localObject3, new CustomerInfo[0].getClass());
/*  759:     */         } else {
/*  760: 702 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject3);
/*  761:     */         }
/*  762:     */       }
/*  763:     */       catch (Throwable localThrowable17)
/*  764:     */       {
/*  765: 706 */         localThrowable17.printStackTrace(Jaguar.log);
/*  766: 707 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/*  767: 708 */         return localThrowable17.getClass().getName();
/*  768:     */       }
/*  769:     */     case 17: 
/*  770:     */       try
/*  771:     */       {
/*  772: 717 */         String str6 = local_ServerRequest.read_string();
/*  773:     */         
/*  774: 719 */         localObject1 = local_ServerRequest.read_string();
/*  775: 720 */         localObject3 = localOFX200BPWServicesBean
/*  776: 721 */           .getCustomerByCategoryAndFI(
/*  777: 722 */           str6, 
/*  778: 723 */           (String)localObject1);
/*  779: 725 */         if (local_ServerRequest.isRMI()) {
/*  780: 725 */           local_ServerRequest.write_value(localObject3, new CustomerInfo[0].getClass());
/*  781:     */         } else {
/*  782: 725 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject3);
/*  783:     */         }
/*  784:     */       }
/*  785:     */       catch (Throwable localThrowable18)
/*  786:     */       {
/*  787: 729 */         localThrowable18.printStackTrace(Jaguar.log);
/*  788: 730 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/*  789: 731 */         return localThrowable18.getClass().getName();
/*  790:     */       }
/*  791:     */     case 18: 
/*  792:     */       try
/*  793:     */       {
/*  794: 740 */         String str7 = local_ServerRequest.read_string();
/*  795:     */         
/*  796: 742 */         localObject1 = local_ServerRequest.read_string();
/*  797: 743 */         localObject3 = localOFX200BPWServicesBean
/*  798: 744 */           .getCustomerByGroupAndFI(
/*  799: 745 */           str7, 
/*  800: 746 */           (String)localObject1);
/*  801: 748 */         if (local_ServerRequest.isRMI()) {
/*  802: 748 */           local_ServerRequest.write_value(localObject3, new CustomerInfo[0].getClass());
/*  803:     */         } else {
/*  804: 748 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject3);
/*  805:     */         }
/*  806:     */       }
/*  807:     */       catch (Throwable localThrowable19)
/*  808:     */       {
/*  809: 752 */         localThrowable19.printStackTrace(Jaguar.log);
/*  810: 753 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/*  811: 754 */         return localThrowable19.getClass().getName();
/*  812:     */       }
/*  813:     */     case 19: 
/*  814:     */       try
/*  815:     */       {
/*  816: 762 */         PayeeInfo[] arrayOfPayeeInfo = localOFX200BPWServicesBean
/*  817: 763 */           .getLinkedPayees();
/*  818: 765 */         if (local_ServerRequest.isRMI()) {
/*  819: 765 */           local_ServerRequest.write_value(arrayOfPayeeInfo, new PayeeInfo[0].getClass());
/*  820:     */         } else {
/*  821: 765 */           PayeeInfoSeqHelper.write(paramOutputStream, arrayOfPayeeInfo);
/*  822:     */         }
/*  823:     */       }
/*  824:     */       catch (Throwable localThrowable20)
/*  825:     */       {
/*  826: 769 */         localThrowable20.printStackTrace(Jaguar.log);
/*  827: 770 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/*  828: 771 */         return localThrowable20.getClass().getName();
/*  829:     */       }
/*  830:     */     case 20: 
/*  831:     */       try
/*  832:     */       {
/*  833: 780 */         int i = paramInputStream.read_long();
/*  834: 781 */         localObject1 = localOFX200BPWServicesBean
/*  835: 782 */           .getMostUsedPayees(
/*  836: 783 */           i);
/*  837: 785 */         if (local_ServerRequest.isRMI()) {
/*  838: 785 */           local_ServerRequest.write_value(localObject1, new PayeeInfo[0].getClass());
/*  839:     */         } else {
/*  840: 785 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject1);
/*  841:     */         }
/*  842:     */       }
/*  843:     */       catch (Throwable localThrowable21)
/*  844:     */       {
/*  845: 789 */         localThrowable21.printStackTrace(Jaguar.log);
/*  846: 790 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/*  847: 791 */         return localThrowable21.getClass().getName();
/*  848:     */       }
/*  849:     */     case 21: 
/*  850:     */       try
/*  851:     */       {
/*  852: 800 */         String str8 = local_ServerRequest.read_string();
/*  853: 801 */         localObject1 = localOFX200BPWServicesBean
/*  854: 802 */           .getPreferredPayees(
/*  855: 803 */           str8);
/*  856: 805 */         if (local_ServerRequest.isRMI()) {
/*  857: 805 */           local_ServerRequest.write_value(localObject1, new PayeeInfo[0].getClass());
/*  858:     */         } else {
/*  859: 805 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject1);
/*  860:     */         }
/*  861:     */       }
/*  862:     */       catch (Throwable localThrowable22)
/*  863:     */       {
/*  864: 809 */         localThrowable22.printStackTrace(Jaguar.log);
/*  865: 810 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/*  866: 811 */         return localThrowable22.getClass().getName();
/*  867:     */       }
/*  868:     */     case 22: 
/*  869:     */       try
/*  870:     */       {
/*  871: 820 */         String str9 = local_ServerRequest.read_string();
/*  872:     */         
/*  873: 822 */         k = paramInputStream.read_long();
/*  874: 823 */         localObject3 = localOFX200BPWServicesBean
/*  875: 824 */           .getPendingPmtsByCustomerID(
/*  876: 825 */           str9, 
/*  877: 826 */           k);
/*  878: 828 */         if (local_ServerRequest.isRMI()) {
/*  879: 828 */           local_ServerRequest.write_value(localObject3, new TypePmtSyncRsV1[0].getClass());
/*  880:     */         } else {
/*  881: 828 */           TypePmtSyncRsV1SeqHelper.write(paramOutputStream, (TypePmtSyncRsV1[])localObject3);
/*  882:     */         }
/*  883:     */       }
/*  884:     */       catch (Throwable localThrowable23)
/*  885:     */       {
/*  886: 832 */         localThrowable23.printStackTrace(Jaguar.log);
/*  887: 833 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/*  888: 834 */         return localThrowable23.getClass().getName();
/*  889:     */       }
/*  890:     */     case 23: 
/*  891:     */       try
/*  892:     */       {
/*  893: 843 */         String str10 = local_ServerRequest.read_string();
/*  894:     */         
/*  895: 845 */         k = paramInputStream.read_long();
/*  896:     */         
/*  897: 847 */         int i1 = paramInputStream.read_long();
/*  898: 848 */         localObject5 = localOFX200BPWServicesBean
/*  899: 849 */           .getPendingPmtsAndHistoryByCustomerID(
/*  900: 850 */           str10, 
/*  901: 851 */           k, 
/*  902: 852 */           i1);
/*  903: 854 */         if (local_ServerRequest.isRMI()) {
/*  904: 854 */           local_ServerRequest.write_value(localObject5, new TypePmtSyncRsV1[0].getClass());
/*  905:     */         } else {
/*  906: 854 */           TypePmtSyncRsV1SeqHelper.write(paramOutputStream, (TypePmtSyncRsV1[])localObject5);
/*  907:     */         }
/*  908:     */       }
/*  909:     */       catch (Throwable localThrowable24)
/*  910:     */       {
/*  911: 858 */         localThrowable24.printStackTrace(Jaguar.log);
/*  912: 859 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/*  913: 860 */         return localThrowable24.getClass().getName();
/*  914:     */       }
/*  915:     */     case 24: 
/*  916:     */       try
/*  917:     */       {
/*  918: 869 */         String str11 = local_ServerRequest.read_string();
/*  919:     */         
/*  920: 871 */         k = paramInputStream.read_long();
/*  921: 872 */         TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV1 = localOFX200BPWServicesBean
/*  922: 873 */           .getPendingIntrasByCustomerID(
/*  923: 874 */           str11, 
/*  924: 875 */           k);
/*  925: 877 */         if (local_ServerRequest.isRMI()) {
/*  926: 877 */           local_ServerRequest.write_value(arrayOfTypeIntraSyncRsV1, new TypeIntraSyncRsV1[0].getClass());
/*  927:     */         } else {
/*  928: 877 */           TypeIntraSyncRsV1SeqHelper.write(paramOutputStream, arrayOfTypeIntraSyncRsV1);
/*  929:     */         }
/*  930:     */       }
/*  931:     */       catch (Throwable localThrowable25)
/*  932:     */       {
/*  933: 881 */         localThrowable25.printStackTrace(Jaguar.log);
/*  934: 882 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/*  935: 883 */         return localThrowable25.getClass().getName();
/*  936:     */       }
/*  937:     */     case 25: 
/*  938:     */       try
/*  939:     */       {
/*  940: 892 */         String str12 = local_ServerRequest.read_string();
/*  941:     */         
/*  942: 894 */         k = paramInputStream.read_long();
/*  943:     */         
/*  944: 896 */         i2 = paramInputStream.read_long();
/*  945: 897 */         localObject5 = localOFX200BPWServicesBean
/*  946: 898 */           .getPendingIntrasAndHistoryByCustomerID(
/*  947: 899 */           str12, 
/*  948: 900 */           k, 
/*  949: 901 */           i2);
/*  950: 903 */         if (local_ServerRequest.isRMI()) {
/*  951: 903 */           local_ServerRequest.write_value(localObject5, new TypeIntraSyncRsV1[0].getClass());
/*  952:     */         } else {
/*  953: 903 */           TypeIntraSyncRsV1SeqHelper.write(paramOutputStream, (TypeIntraSyncRsV1[])localObject5);
/*  954:     */         }
/*  955:     */       }
/*  956:     */       catch (Throwable localThrowable26)
/*  957:     */       {
/*  958: 907 */         localThrowable26.printStackTrace(Jaguar.log);
/*  959: 908 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/*  960: 909 */         return localThrowable26.getClass().getName();
/*  961:     */       }
/*  962:     */     case 26: 
/*  963:     */       try
/*  964:     */       {
/*  965:     */         TypePmtSyncRqV1 localTypePmtSyncRqV11;
/*  966: 918 */         if (local_ServerRequest.isRMI()) {
/*  967: 918 */           localTypePmtSyncRqV11 = (TypePmtSyncRqV1)local_ServerRequest.read_value(TypePmtSyncRqV1.class);
/*  968:     */         } else {
/*  969: 918 */           localTypePmtSyncRqV11 = TypePmtSyncRqV1Helper.read(paramInputStream);
/*  970:     */         }
/*  971: 920 */         if (local_ServerRequest.isRMI()) {
/*  972: 920 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/*  973:     */         } else {
/*  974: 920 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/*  975:     */         }
/*  976: 922 */         i2 = paramInputStream.read_long();
/*  977: 923 */         localObject5 = localOFX200BPWServicesBean
/*  978: 924 */           .getPendingPmts(
/*  979: 925 */           localTypePmtSyncRqV11, 
/*  980: 926 */           (TypeUserData)localObject2, 
/*  981: 927 */           i2);
/*  982: 929 */         if (local_ServerRequest.isRMI()) {
/*  983: 929 */           local_ServerRequest.write_value(localObject5, TypePmtSyncRsV1.class);
/*  984:     */         } else {
/*  985: 929 */           TypePmtSyncRsV1Helper.write(paramOutputStream, (TypePmtSyncRsV1)localObject5);
/*  986:     */         }
/*  987:     */       }
/*  988:     */       catch (Throwable localThrowable27)
/*  989:     */       {
/*  990: 933 */         localThrowable27.printStackTrace(Jaguar.log);
/*  991: 934 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/*  992: 935 */         return localThrowable27.getClass().getName();
/*  993:     */       }
/*  994:     */     case 27: 
/*  995:     */       try
/*  996:     */       {
/*  997:     */         TypeIntraSyncRqV1 localTypeIntraSyncRqV11;
/*  998: 944 */         if (local_ServerRequest.isRMI()) {
/*  999: 944 */           localTypeIntraSyncRqV11 = (TypeIntraSyncRqV1)local_ServerRequest.read_value(TypeIntraSyncRqV1.class);
/* 1000:     */         } else {
/* 1001: 944 */           localTypeIntraSyncRqV11 = TypeIntraSyncRqV1Helper.read(paramInputStream);
/* 1002:     */         }
/* 1003: 946 */         if (local_ServerRequest.isRMI()) {
/* 1004: 946 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1005:     */         } else {
/* 1006: 946 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1007:     */         }
/* 1008: 948 */         i2 = paramInputStream.read_long();
/* 1009: 949 */         localObject5 = localOFX200BPWServicesBean
/* 1010: 950 */           .getPendingIntras(
/* 1011: 951 */           localTypeIntraSyncRqV11, 
/* 1012: 952 */           (TypeUserData)localObject2, 
/* 1013: 953 */           i2);
/* 1014: 955 */         if (local_ServerRequest.isRMI()) {
/* 1015: 955 */           local_ServerRequest.write_value(localObject5, TypeIntraSyncRsV1.class);
/* 1016:     */         } else {
/* 1017: 955 */           TypeIntraSyncRsV1Helper.write(paramOutputStream, (TypeIntraSyncRsV1)localObject5);
/* 1018:     */         }
/* 1019:     */       }
/* 1020:     */       catch (Throwable localThrowable28)
/* 1021:     */       {
/* 1022: 959 */         localThrowable28.printStackTrace(Jaguar.log);
/* 1023: 960 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/* 1024: 961 */         return localThrowable28.getClass().getName();
/* 1025:     */       }
/* 1026:     */     case 28: 
/* 1027:     */       try
/* 1028:     */       {
/* 1029: 970 */         String str13 = local_ServerRequest.read_string();
/* 1030: 971 */         localObject2 = localOFX200BPWServicesBean
/* 1031: 972 */           .getPmtStatus(
/* 1032: 973 */           str13);
/* 1033:     */         
/* 1034: 975 */         local_ServerRequest.write_string((String)localObject2);
/* 1035:     */       }
/* 1036:     */       catch (Throwable localThrowable29)
/* 1037:     */       {
/* 1038: 979 */         localThrowable29.printStackTrace(Jaguar.log);
/* 1039: 980 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/* 1040: 981 */         return localThrowable29.getClass().getName();
/* 1041:     */       }
/* 1042:     */     case 29: 
/* 1043:     */       try
/* 1044:     */       {
/* 1045: 990 */         String str14 = local_ServerRequest.read_string();
/* 1046:     */         
/* 1047: 992 */         localObject2 = local_ServerRequest.read_string();
/* 1048: 993 */         boolean bool = localOFX200BPWServicesBean
/* 1049: 994 */           .checkPayeeEditMask(
/* 1050: 995 */           str14, 
/* 1051: 996 */           (String)localObject2);
/* 1052:     */         
/* 1053: 998 */         paramOutputStream.write_boolean(bool);
/* 1054:     */       }
/* 1055:     */       catch (Throwable localThrowable30)
/* 1056:     */       {
/* 1057:1002 */         localThrowable30.printStackTrace(Jaguar.log);
/* 1058:1003 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/* 1059:1004 */         return localThrowable30.getClass().getName();
/* 1060:     */       }
/* 1061:     */     case 30: 
/* 1062:     */       try
/* 1063:     */       {
/* 1064:     */         IntraTrnRslt[] arrayOfIntraTrnRslt;
/* 1065:1013 */         if (local_ServerRequest.isRMI()) {
/* 1066:1013 */           arrayOfIntraTrnRslt = (IntraTrnRslt[])local_ServerRequest.read_value(new IntraTrnRslt[0].getClass());
/* 1067:     */         } else {
/* 1068:1013 */           arrayOfIntraTrnRslt = IntraTrnRsltSeqHelper.read(paramInputStream);
/* 1069:     */         }
/* 1070:1015 */         localOFX200BPWServicesBean.processIntraTrnRslt(
/* 1071:1016 */           arrayOfIntraTrnRslt);
/* 1072:     */       }
/* 1073:     */       catch (Throwable localThrowable31)
/* 1074:     */       {
/* 1075:1021 */         localThrowable31.printStackTrace(Jaguar.log);
/* 1076:1022 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/* 1077:1023 */         return localThrowable31.getClass().getName();
/* 1078:     */       }
/* 1079:     */     case 31: 
/* 1080:     */       try
/* 1081:     */       {
/* 1082:     */         PmtTrnRslt[] arrayOfPmtTrnRslt;
/* 1083:1032 */         if (local_ServerRequest.isRMI()) {
/* 1084:1032 */           arrayOfPmtTrnRslt = (PmtTrnRslt[])local_ServerRequest.read_value(new PmtTrnRslt[0].getClass());
/* 1085:     */         } else {
/* 1086:1032 */           arrayOfPmtTrnRslt = PmtTrnRsltSeqHelper.read(paramInputStream);
/* 1087:     */         }
/* 1088:1034 */         localOFX200BPWServicesBean.processPmtTrnRslt(
/* 1089:1035 */           arrayOfPmtTrnRslt);
/* 1090:     */       }
/* 1091:     */       catch (Throwable localThrowable32)
/* 1092:     */       {
/* 1093:1040 */         localThrowable32.printStackTrace(Jaguar.log);
/* 1094:1041 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/* 1095:1042 */         return localThrowable32.getClass().getName();
/* 1096:     */       }
/* 1097:     */     case 32: 
/* 1098:     */       try
/* 1099:     */       {
/* 1100:     */         CustPayeeRslt[] arrayOfCustPayeeRslt;
/* 1101:1051 */         if (local_ServerRequest.isRMI()) {
/* 1102:1051 */           arrayOfCustPayeeRslt = (CustPayeeRslt[])local_ServerRequest.read_value(new CustPayeeRslt[0].getClass());
/* 1103:     */         } else {
/* 1104:1051 */           arrayOfCustPayeeRslt = CustPayeeRsltSeqHelper.read(paramInputStream);
/* 1105:     */         }
/* 1106:1053 */         localOFX200BPWServicesBean.processCustPayeeRslt(
/* 1107:1054 */           arrayOfCustPayeeRslt);
/* 1108:     */       }
/* 1109:     */       catch (Throwable localThrowable33)
/* 1110:     */       {
/* 1111:1059 */         localThrowable33.printStackTrace(Jaguar.log);
/* 1112:1060 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/* 1113:1061 */         return localThrowable33.getClass().getName();
/* 1114:     */       }
/* 1115:     */     case 33: 
/* 1116:     */       try
/* 1117:     */       {
/* 1118:     */         FundsAllocRslt[] arrayOfFundsAllocRslt1;
/* 1119:1070 */         if (local_ServerRequest.isRMI()) {
/* 1120:1070 */           arrayOfFundsAllocRslt1 = (FundsAllocRslt[])local_ServerRequest.read_value(new FundsAllocRslt[0].getClass());
/* 1121:     */         } else {
/* 1122:1070 */           arrayOfFundsAllocRslt1 = FundsAllocRsltSeqHelper.read(paramInputStream);
/* 1123:     */         }
/* 1124:1072 */         localOFX200BPWServicesBean.processFundAllocRslt(
/* 1125:1073 */           arrayOfFundsAllocRslt1);
/* 1126:     */       }
/* 1127:     */       catch (Throwable localThrowable34)
/* 1128:     */       {
/* 1129:1078 */         localThrowable34.printStackTrace(Jaguar.log);
/* 1130:1079 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/* 1131:1080 */         return localThrowable34.getClass().getName();
/* 1132:     */       }
/* 1133:     */     case 34: 
/* 1134:     */       try
/* 1135:     */       {
/* 1136:     */         FundsAllocRslt[] arrayOfFundsAllocRslt2;
/* 1137:1089 */         if (local_ServerRequest.isRMI()) {
/* 1138:1089 */           arrayOfFundsAllocRslt2 = (FundsAllocRslt[])local_ServerRequest.read_value(new FundsAllocRslt[0].getClass());
/* 1139:     */         } else {
/* 1140:1089 */           arrayOfFundsAllocRslt2 = FundsAllocRsltSeqHelper.read(paramInputStream);
/* 1141:     */         }
/* 1142:1091 */         localOFX200BPWServicesBean.processFundRevertRslt(
/* 1143:1092 */           arrayOfFundsAllocRslt2);
/* 1144:     */       }
/* 1145:     */       catch (Throwable localThrowable35)
/* 1146:     */       {
/* 1147:1097 */         localThrowable35.printStackTrace(Jaguar.log);
/* 1148:1098 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/* 1149:1099 */         return localThrowable35.getClass().getName();
/* 1150:     */       }
/* 1151:     */     case 35: 
/* 1152:     */       try
/* 1153:     */       {
/* 1154:     */         PayeeRslt[] arrayOfPayeeRslt;
/* 1155:1108 */         if (local_ServerRequest.isRMI()) {
/* 1156:1108 */           arrayOfPayeeRslt = (PayeeRslt[])local_ServerRequest.read_value(new PayeeRslt[0].getClass());
/* 1157:     */         } else {
/* 1158:1108 */           arrayOfPayeeRslt = PayeeRsltSeqHelper.read(paramInputStream);
/* 1159:     */         }
/* 1160:1110 */         localOFX200BPWServicesBean.processPayeeRslt(
/* 1161:1111 */           arrayOfPayeeRslt);
/* 1162:     */       }
/* 1163:     */       catch (Throwable localThrowable36)
/* 1164:     */       {
/* 1165:1116 */         localThrowable36.printStackTrace(Jaguar.log);
/* 1166:1117 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/* 1167:1118 */         return localThrowable36.getClass().getName();
/* 1168:     */       }
/* 1169:     */     case 36: 
/* 1170:     */       try
/* 1171:     */       {
/* 1172:1127 */         PayeeInfo localPayeeInfo1 = (PayeeInfo)local_ServerRequest.read_value(PayeeInfo.class);
/* 1173:1128 */         localObject2 = localOFX200BPWServicesBean
/* 1174:1129 */           .addPayeeFromBackend(
/* 1175:1130 */           localPayeeInfo1);
/* 1176:     */         
/* 1177:1132 */         local_ServerRequest.write_string((String)localObject2);
/* 1178:     */       }
/* 1179:     */       catch (Throwable localThrowable37)
/* 1180:     */       {
/* 1181:1136 */         localThrowable37.printStackTrace(Jaguar.log);
/* 1182:1137 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable37, true);
/* 1183:1138 */         return localThrowable37.getClass().getName();
/* 1184:     */       }
/* 1185:     */     case 37: 
/* 1186:     */       try
/* 1187:     */       {
/* 1188:1147 */         PayeeInfo localPayeeInfo2 = (PayeeInfo)local_ServerRequest.read_value(PayeeInfo.class);
/* 1189:1148 */         localObject2 = localOFX200BPWServicesBean
/* 1190:1149 */           .updatePayeeFromBackend(
/* 1191:1150 */           localPayeeInfo2);
/* 1192:1152 */         if (local_ServerRequest.isRMI()) {
/* 1193:1152 */           local_ServerRequest.write_value(localObject2, new PayeeInfo[0].getClass());
/* 1194:     */         } else {
/* 1195:1152 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject2);
/* 1196:     */         }
/* 1197:     */       }
/* 1198:     */       catch (Throwable localThrowable38)
/* 1199:     */       {
/* 1200:1156 */         localThrowable38.printStackTrace(Jaguar.log);
/* 1201:1157 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable38, true);
/* 1202:1158 */         return localThrowable38.getClass().getName();
/* 1203:     */       }
/* 1204:     */     case 38: 
/* 1205:     */       try
/* 1206:     */       {
/* 1207:1167 */         PayeeRouteInfo localPayeeRouteInfo = (PayeeRouteInfo)local_ServerRequest.read_value(PayeeRouteInfo.class);
/* 1208:1168 */         localOFX200BPWServicesBean
/* 1209:1169 */           .addPayeeRouteInfo(
/* 1210:1170 */           localPayeeRouteInfo);
/* 1211:     */       }
/* 1212:     */       catch (Throwable localThrowable39)
/* 1213:     */       {
/* 1214:1175 */         localThrowable39.printStackTrace(Jaguar.log);
/* 1215:1176 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable39, true);
/* 1216:1177 */         return localThrowable39.getClass().getName();
/* 1217:     */       }
/* 1218:     */     case 39: 
/* 1219:     */       try
/* 1220:     */       {
/* 1221:     */         TypeIntraSyncRqV1 localTypeIntraSyncRqV12;
/* 1222:1186 */         if (local_ServerRequest.isRMI()) {
/* 1223:1186 */           localTypeIntraSyncRqV12 = (TypeIntraSyncRqV1)local_ServerRequest.read_value(TypeIntraSyncRqV1.class);
/* 1224:     */         } else {
/* 1225:1186 */           localTypeIntraSyncRqV12 = TypeIntraSyncRqV1Helper.read(paramInputStream);
/* 1226:     */         }
/* 1227:1188 */         if (local_ServerRequest.isRMI()) {
/* 1228:1188 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1229:     */         } else {
/* 1230:1188 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1231:     */         }
/* 1232:1189 */         localObject4 = 
/* 1233:1190 */           localOFX200BPWServicesBean.processIntraSyncRqV1(
/* 1234:1191 */           localTypeIntraSyncRqV12, 
/* 1235:1192 */           (TypeUserData)localObject2);
/* 1236:1194 */         if (local_ServerRequest.isRMI()) {
/* 1237:1194 */           local_ServerRequest.write_value(localObject4, TypeIntraSyncRsV1.class);
/* 1238:     */         } else {
/* 1239:1194 */           TypeIntraSyncRsV1Helper.write(paramOutputStream, (TypeIntraSyncRsV1)localObject4);
/* 1240:     */         }
/* 1241:     */       }
/* 1242:     */       catch (Throwable localThrowable40)
/* 1243:     */       {
/* 1244:1198 */         localThrowable40.printStackTrace(Jaguar.log);
/* 1245:1199 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable40, true);
/* 1246:1200 */         return localThrowable40.getClass().getName();
/* 1247:     */       }
/* 1248:     */     case 40: 
/* 1249:     */       try
/* 1250:     */       {
/* 1251:     */         TypeIntraTrnRqV1 localTypeIntraTrnRqV1;
/* 1252:1209 */         if (local_ServerRequest.isRMI()) {
/* 1253:1209 */           localTypeIntraTrnRqV1 = (TypeIntraTrnRqV1)local_ServerRequest.read_value(TypeIntraTrnRqV1.class);
/* 1254:     */         } else {
/* 1255:1209 */           localTypeIntraTrnRqV1 = TypeIntraTrnRqV1Helper.read(paramInputStream);
/* 1256:     */         }
/* 1257:1211 */         if (local_ServerRequest.isRMI()) {
/* 1258:1211 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1259:     */         } else {
/* 1260:1211 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1261:     */         }
/* 1262:1212 */         localObject4 = 
/* 1263:1213 */           localOFX200BPWServicesBean.processIntraTrnRqV1(
/* 1264:1214 */           localTypeIntraTrnRqV1, 
/* 1265:1215 */           (TypeUserData)localObject2);
/* 1266:1217 */         if (local_ServerRequest.isRMI()) {
/* 1267:1217 */           local_ServerRequest.write_value(localObject4, TypeIntraTrnRsV1.class);
/* 1268:     */         } else {
/* 1269:1217 */           TypeIntraTrnRsV1Helper.write(paramOutputStream, (TypeIntraTrnRsV1)localObject4);
/* 1270:     */         }
/* 1271:     */       }
/* 1272:     */       catch (Throwable localThrowable41)
/* 1273:     */       {
/* 1274:1221 */         localThrowable41.printStackTrace(Jaguar.log);
/* 1275:1222 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable41, true);
/* 1276:1223 */         return localThrowable41.getClass().getName();
/* 1277:     */       }
/* 1278:     */     case 41: 
/* 1279:     */       try
/* 1280:     */       {
/* 1281:     */         TypePayeeSyncRqV1 localTypePayeeSyncRqV1;
/* 1282:1232 */         if (local_ServerRequest.isRMI()) {
/* 1283:1232 */           localTypePayeeSyncRqV1 = (TypePayeeSyncRqV1)local_ServerRequest.read_value(TypePayeeSyncRqV1.class);
/* 1284:     */         } else {
/* 1285:1232 */           localTypePayeeSyncRqV1 = TypePayeeSyncRqV1Helper.read(paramInputStream);
/* 1286:     */         }
/* 1287:1234 */         if (local_ServerRequest.isRMI()) {
/* 1288:1234 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1289:     */         } else {
/* 1290:1234 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1291:     */         }
/* 1292:1235 */         localObject4 = 
/* 1293:1236 */           localOFX200BPWServicesBean.processPayeeSyncRqV1(
/* 1294:1237 */           localTypePayeeSyncRqV1, 
/* 1295:1238 */           (TypeUserData)localObject2);
/* 1296:1240 */         if (local_ServerRequest.isRMI()) {
/* 1297:1240 */           local_ServerRequest.write_value(localObject4, TypePayeeSyncRsV1.class);
/* 1298:     */         } else {
/* 1299:1240 */           TypePayeeSyncRsV1Helper.write(paramOutputStream, (TypePayeeSyncRsV1)localObject4);
/* 1300:     */         }
/* 1301:     */       }
/* 1302:     */       catch (Throwable localThrowable42)
/* 1303:     */       {
/* 1304:1244 */         localThrowable42.printStackTrace(Jaguar.log);
/* 1305:1245 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable42, true);
/* 1306:1246 */         return localThrowable42.getClass().getName();
/* 1307:     */       }
/* 1308:     */     case 42: 
/* 1309:     */       try
/* 1310:     */       {
/* 1311:     */         TypePayeeTrnRqV1 localTypePayeeTrnRqV1;
/* 1312:1255 */         if (local_ServerRequest.isRMI()) {
/* 1313:1255 */           localTypePayeeTrnRqV1 = (TypePayeeTrnRqV1)local_ServerRequest.read_value(TypePayeeTrnRqV1.class);
/* 1314:     */         } else {
/* 1315:1255 */           localTypePayeeTrnRqV1 = TypePayeeTrnRqV1Helper.read(paramInputStream);
/* 1316:     */         }
/* 1317:1257 */         if (local_ServerRequest.isRMI()) {
/* 1318:1257 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1319:     */         } else {
/* 1320:1257 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1321:     */         }
/* 1322:1258 */         localObject4 = 
/* 1323:1259 */           localOFX200BPWServicesBean.processPayeeTrnRqV1(
/* 1324:1260 */           localTypePayeeTrnRqV1, 
/* 1325:1261 */           (TypeUserData)localObject2);
/* 1326:1263 */         if (local_ServerRequest.isRMI()) {
/* 1327:1263 */           local_ServerRequest.write_value(localObject4, TypePayeeTrnRsV1.class);
/* 1328:     */         } else {
/* 1329:1263 */           TypePayeeTrnRsV1Helper.write(paramOutputStream, (TypePayeeTrnRsV1)localObject4);
/* 1330:     */         }
/* 1331:     */       }
/* 1332:     */       catch (Throwable localThrowable43)
/* 1333:     */       {
/* 1334:1267 */         localThrowable43.printStackTrace(Jaguar.log);
/* 1335:1268 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable43, true);
/* 1336:1269 */         return localThrowable43.getClass().getName();
/* 1337:     */       }
/* 1338:     */     case 43: 
/* 1339:     */       try
/* 1340:     */       {
/* 1341:     */         TypePmtInqTrnRqV1 localTypePmtInqTrnRqV1;
/* 1342:1278 */         if (local_ServerRequest.isRMI()) {
/* 1343:1278 */           localTypePmtInqTrnRqV1 = (TypePmtInqTrnRqV1)local_ServerRequest.read_value(TypePmtInqTrnRqV1.class);
/* 1344:     */         } else {
/* 1345:1278 */           localTypePmtInqTrnRqV1 = TypePmtInqTrnRqV1Helper.read(paramInputStream);
/* 1346:     */         }
/* 1347:1280 */         if (local_ServerRequest.isRMI()) {
/* 1348:1280 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1349:     */         } else {
/* 1350:1280 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1351:     */         }
/* 1352:1281 */         localObject4 = 
/* 1353:1282 */           localOFX200BPWServicesBean.processPmtInqTrnRqV1(
/* 1354:1283 */           localTypePmtInqTrnRqV1, 
/* 1355:1284 */           (TypeUserData)localObject2);
/* 1356:1286 */         if (local_ServerRequest.isRMI()) {
/* 1357:1286 */           local_ServerRequest.write_value(localObject4, TypePmtInqTrnRsV1.class);
/* 1358:     */         } else {
/* 1359:1286 */           TypePmtInqTrnRsV1Helper.write(paramOutputStream, (TypePmtInqTrnRsV1)localObject4);
/* 1360:     */         }
/* 1361:     */       }
/* 1362:     */       catch (Throwable localThrowable44)
/* 1363:     */       {
/* 1364:1290 */         localThrowable44.printStackTrace(Jaguar.log);
/* 1365:1291 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable44, true);
/* 1366:1292 */         return localThrowable44.getClass().getName();
/* 1367:     */       }
/* 1368:     */     case 44: 
/* 1369:     */       try
/* 1370:     */       {
/* 1371:     */         TypePmtSyncRqV1 localTypePmtSyncRqV12;
/* 1372:1301 */         if (local_ServerRequest.isRMI()) {
/* 1373:1301 */           localTypePmtSyncRqV12 = (TypePmtSyncRqV1)local_ServerRequest.read_value(TypePmtSyncRqV1.class);
/* 1374:     */         } else {
/* 1375:1301 */           localTypePmtSyncRqV12 = TypePmtSyncRqV1Helper.read(paramInputStream);
/* 1376:     */         }
/* 1377:1303 */         if (local_ServerRequest.isRMI()) {
/* 1378:1303 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1379:     */         } else {
/* 1380:1303 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1381:     */         }
/* 1382:1304 */         localObject4 = 
/* 1383:1305 */           localOFX200BPWServicesBean.processPmtSyncRqV1(
/* 1384:1306 */           localTypePmtSyncRqV12, 
/* 1385:1307 */           (TypeUserData)localObject2);
/* 1386:1309 */         if (local_ServerRequest.isRMI()) {
/* 1387:1309 */           local_ServerRequest.write_value(localObject4, TypePmtSyncRsV1.class);
/* 1388:     */         } else {
/* 1389:1309 */           TypePmtSyncRsV1Helper.write(paramOutputStream, (TypePmtSyncRsV1)localObject4);
/* 1390:     */         }
/* 1391:     */       }
/* 1392:     */       catch (Throwable localThrowable45)
/* 1393:     */       {
/* 1394:1313 */         localThrowable45.printStackTrace(Jaguar.log);
/* 1395:1314 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable45, true);
/* 1396:1315 */         return localThrowable45.getClass().getName();
/* 1397:     */       }
/* 1398:     */     case 45: 
/* 1399:     */       try
/* 1400:     */       {
/* 1401:     */         TypePmtTrnRqV1 localTypePmtTrnRqV1;
/* 1402:1324 */         if (local_ServerRequest.isRMI()) {
/* 1403:1324 */           localTypePmtTrnRqV1 = (TypePmtTrnRqV1)local_ServerRequest.read_value(TypePmtTrnRqV1.class);
/* 1404:     */         } else {
/* 1405:1324 */           localTypePmtTrnRqV1 = TypePmtTrnRqV1Helper.read(paramInputStream);
/* 1406:     */         }
/* 1407:1326 */         if (local_ServerRequest.isRMI()) {
/* 1408:1326 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1409:     */         } else {
/* 1410:1326 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1411:     */         }
/* 1412:1327 */         localObject4 = 
/* 1413:1328 */           localOFX200BPWServicesBean.processPmtTrnRqV1(
/* 1414:1329 */           localTypePmtTrnRqV1, 
/* 1415:1330 */           (TypeUserData)localObject2);
/* 1416:1332 */         if (local_ServerRequest.isRMI()) {
/* 1417:1332 */           local_ServerRequest.write_value(localObject4, TypePmtTrnRsV1.class);
/* 1418:     */         } else {
/* 1419:1332 */           TypePmtTrnRsV1Helper.write(paramOutputStream, (TypePmtTrnRsV1)localObject4);
/* 1420:     */         }
/* 1421:     */       }
/* 1422:     */       catch (Throwable localThrowable46)
/* 1423:     */       {
/* 1424:1336 */         localThrowable46.printStackTrace(Jaguar.log);
/* 1425:1337 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable46, true);
/* 1426:1338 */         return localThrowable46.getClass().getName();
/* 1427:     */       }
/* 1428:     */     case 46: 
/* 1429:     */       try
/* 1430:     */       {
/* 1431:     */         TypeRecIntraSyncRqV1 localTypeRecIntraSyncRqV1;
/* 1432:1347 */         if (local_ServerRequest.isRMI()) {
/* 1433:1347 */           localTypeRecIntraSyncRqV1 = (TypeRecIntraSyncRqV1)local_ServerRequest.read_value(TypeRecIntraSyncRqV1.class);
/* 1434:     */         } else {
/* 1435:1347 */           localTypeRecIntraSyncRqV1 = TypeRecIntraSyncRqV1Helper.read(paramInputStream);
/* 1436:     */         }
/* 1437:1349 */         if (local_ServerRequest.isRMI()) {
/* 1438:1349 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1439:     */         } else {
/* 1440:1349 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1441:     */         }
/* 1442:1350 */         localObject4 = 
/* 1443:1351 */           localOFX200BPWServicesBean.processRecIntraSyncRqV1(
/* 1444:1352 */           localTypeRecIntraSyncRqV1, 
/* 1445:1353 */           (TypeUserData)localObject2);
/* 1446:1355 */         if (local_ServerRequest.isRMI()) {
/* 1447:1355 */           local_ServerRequest.write_value(localObject4, TypeRecIntraSyncRsV1.class);
/* 1448:     */         } else {
/* 1449:1355 */           TypeRecIntraSyncRsV1Helper.write(paramOutputStream, (TypeRecIntraSyncRsV1)localObject4);
/* 1450:     */         }
/* 1451:     */       }
/* 1452:     */       catch (Throwable localThrowable47)
/* 1453:     */       {
/* 1454:1359 */         localThrowable47.printStackTrace(Jaguar.log);
/* 1455:1360 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable47, true);
/* 1456:1361 */         return localThrowable47.getClass().getName();
/* 1457:     */       }
/* 1458:     */     case 47: 
/* 1459:     */       try
/* 1460:     */       {
/* 1461:     */         TypeRecIntraTrnRqV1 localTypeRecIntraTrnRqV1;
/* 1462:1370 */         if (local_ServerRequest.isRMI()) {
/* 1463:1370 */           localTypeRecIntraTrnRqV1 = (TypeRecIntraTrnRqV1)local_ServerRequest.read_value(TypeRecIntraTrnRqV1.class);
/* 1464:     */         } else {
/* 1465:1370 */           localTypeRecIntraTrnRqV1 = TypeRecIntraTrnRqV1Helper.read(paramInputStream);
/* 1466:     */         }
/* 1467:1372 */         if (local_ServerRequest.isRMI()) {
/* 1468:1372 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1469:     */         } else {
/* 1470:1372 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1471:     */         }
/* 1472:1373 */         localObject4 = 
/* 1473:1374 */           localOFX200BPWServicesBean.processRecIntraTrnRqV1(
/* 1474:1375 */           localTypeRecIntraTrnRqV1, 
/* 1475:1376 */           (TypeUserData)localObject2);
/* 1476:1378 */         if (local_ServerRequest.isRMI()) {
/* 1477:1378 */           local_ServerRequest.write_value(localObject4, TypeRecIntraTrnRsV1.class);
/* 1478:     */         } else {
/* 1479:1378 */           TypeRecIntraTrnRsV1Helper.write(paramOutputStream, (TypeRecIntraTrnRsV1)localObject4);
/* 1480:     */         }
/* 1481:     */       }
/* 1482:     */       catch (Throwable localThrowable48)
/* 1483:     */       {
/* 1484:1382 */         localThrowable48.printStackTrace(Jaguar.log);
/* 1485:1383 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable48, true);
/* 1486:1384 */         return localThrowable48.getClass().getName();
/* 1487:     */       }
/* 1488:     */     case 48: 
/* 1489:     */       try
/* 1490:     */       {
/* 1491:     */         TypeRecPmtSyncRqV1 localTypeRecPmtSyncRqV1;
/* 1492:1393 */         if (local_ServerRequest.isRMI()) {
/* 1493:1393 */           localTypeRecPmtSyncRqV1 = (TypeRecPmtSyncRqV1)local_ServerRequest.read_value(TypeRecPmtSyncRqV1.class);
/* 1494:     */         } else {
/* 1495:1393 */           localTypeRecPmtSyncRqV1 = TypeRecPmtSyncRqV1Helper.read(paramInputStream);
/* 1496:     */         }
/* 1497:1395 */         if (local_ServerRequest.isRMI()) {
/* 1498:1395 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1499:     */         } else {
/* 1500:1395 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1501:     */         }
/* 1502:1396 */         localObject4 = 
/* 1503:1397 */           localOFX200BPWServicesBean.processRecPmtSyncRqV1(
/* 1504:1398 */           localTypeRecPmtSyncRqV1, 
/* 1505:1399 */           (TypeUserData)localObject2);
/* 1506:1401 */         if (local_ServerRequest.isRMI()) {
/* 1507:1401 */           local_ServerRequest.write_value(localObject4, TypeRecPmtSyncRsV1.class);
/* 1508:     */         } else {
/* 1509:1401 */           TypeRecPmtSyncRsV1Helper.write(paramOutputStream, (TypeRecPmtSyncRsV1)localObject4);
/* 1510:     */         }
/* 1511:     */       }
/* 1512:     */       catch (Throwable localThrowable49)
/* 1513:     */       {
/* 1514:1405 */         localThrowable49.printStackTrace(Jaguar.log);
/* 1515:1406 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable49, true);
/* 1516:1407 */         return localThrowable49.getClass().getName();
/* 1517:     */       }
/* 1518:     */     case 49: 
/* 1519:     */       try
/* 1520:     */       {
/* 1521:     */         TypeRecPmtTrnRqV1 localTypeRecPmtTrnRqV1;
/* 1522:1416 */         if (local_ServerRequest.isRMI()) {
/* 1523:1416 */           localTypeRecPmtTrnRqV1 = (TypeRecPmtTrnRqV1)local_ServerRequest.read_value(TypeRecPmtTrnRqV1.class);
/* 1524:     */         } else {
/* 1525:1416 */           localTypeRecPmtTrnRqV1 = TypeRecPmtTrnRqV1Helper.read(paramInputStream);
/* 1526:     */         }
/* 1527:1418 */         if (local_ServerRequest.isRMI()) {
/* 1528:1418 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1529:     */         } else {
/* 1530:1418 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1531:     */         }
/* 1532:1419 */         localObject4 = 
/* 1533:1420 */           localOFX200BPWServicesBean.processRecPmtTrnRqV1(
/* 1534:1421 */           localTypeRecPmtTrnRqV1, 
/* 1535:1422 */           (TypeUserData)localObject2);
/* 1536:1424 */         if (local_ServerRequest.isRMI()) {
/* 1537:1424 */           local_ServerRequest.write_value(localObject4, TypeRecPmtTrnRsV1.class);
/* 1538:     */         } else {
/* 1539:1424 */           TypeRecPmtTrnRsV1Helper.write(paramOutputStream, (TypeRecPmtTrnRsV1)localObject4);
/* 1540:     */         }
/* 1541:     */       }
/* 1542:     */       catch (Throwable localThrowable50)
/* 1543:     */       {
/* 1544:1428 */         localThrowable50.printStackTrace(Jaguar.log);
/* 1545:1429 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable50, true);
/* 1546:1430 */         return localThrowable50.getClass().getName();
/* 1547:     */       }
/* 1548:     */     case 50: 
/* 1549:     */       try
/* 1550:     */       {
/* 1551:1439 */         String str15 = local_ServerRequest.read_string();
/* 1552:     */         
/* 1553:1441 */         int m = paramInputStream.read_long();
/* 1554:1442 */         localObject4 = localOFX200BPWServicesBean
/* 1555:1443 */           .getPayeeNames(
/* 1556:1444 */           str15, 
/* 1557:1445 */           m);
/* 1558:1447 */         if (local_ServerRequest.isRMI()) {
/* 1559:1447 */           local_ServerRequest.write_value(localObject4, new String[0].getClass());
/* 1560:     */         } else {
/* 1561:1447 */           StringSeqHelper.write(paramOutputStream, (String[])localObject4);
/* 1562:     */         }
/* 1563:     */       }
/* 1564:     */       catch (Throwable localThrowable51)
/* 1565:     */       {
/* 1566:1451 */         localThrowable51.printStackTrace(Jaguar.log);
/* 1567:1452 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable51, true);
/* 1568:1453 */         return localThrowable51.getClass().getName();
/* 1569:     */       }
/* 1570:     */     default: 
/* 1571:1459 */       return 
/* 1572:1460 */         invoke1(
/* 1573:1461 */         local_ServerRequest, 
/* 1574:1462 */         paramInputStream, 
/* 1575:1463 */         paramOutputStream, 
/* 1576:1464 */         localOFX200BPWServicesBean, 
/* 1577:1465 */         localInteger);
/* 1578:     */     }
/* 1579:1469 */     return null;
/* 1580:     */   }
/* 1581:     */   
/* 1582:     */   private static String invoke1(_ServerRequest param_ServerRequest, InputStream paramInputStream, OutputStream paramOutputStream, OFX200BPWServicesBean paramOFX200BPWServicesBean, Integer paramInteger)
/* 1583:     */   {
/* 1584:     */     String[] arrayOfString10;
/* 1585:     */     Object localObject4;
/* 1586:     */     PayeeInfo[] arrayOfPayeeInfo;
/* 1587:     */     Object localObject1;
/* 1588:     */     int m;
/* 1589:     */     int n;
/* 1590:     */     int i1;
/* 1591:     */     Object localObject2;
/* 1592:     */     boolean bool2;
/* 1593:     */     Object localObject5;
/* 1594:     */     Object localObject3;
/* 1595:1479 */     switch (paramInteger.intValue())
/* 1596:     */     {
/* 1597:     */     case 51: 
/* 1598:     */       try
/* 1599:     */       {
/* 1600:1486 */         String str1 = param_ServerRequest.read_string();
/* 1601:1487 */         arrayOfString10 = paramOFX200BPWServicesBean
/* 1602:1488 */           .getPayeeNames(
/* 1603:1489 */           str1);
/* 1604:1491 */         if (param_ServerRequest.isRMI()) {
/* 1605:1491 */           param_ServerRequest.write_value(arrayOfString10, new String[0].getClass());
/* 1606:     */         } else {
/* 1607:1491 */           StringSeqHelper.write(paramOutputStream, arrayOfString10);
/* 1608:     */         }
/* 1609:     */       }
/* 1610:     */       catch (Throwable localThrowable1)
/* 1611:     */       {
/* 1612:1495 */         localThrowable1.printStackTrace(Jaguar.log);
/* 1613:1496 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/* 1614:1497 */         return localThrowable1.getClass().getName();
/* 1615:     */       }
/* 1616:     */     case 52: 
/* 1617:     */       try
/* 1618:     */       {
/* 1619:1506 */         String str2 = param_ServerRequest.read_string();
/* 1620:1507 */         arrayOfString10 = paramOFX200BPWServicesBean
/* 1621:1508 */           .getPayeeIDs(
/* 1622:1509 */           str2);
/* 1623:1511 */         if (param_ServerRequest.isRMI()) {
/* 1624:1511 */           param_ServerRequest.write_value(arrayOfString10, new String[0].getClass());
/* 1625:     */         } else {
/* 1626:1511 */           StringSeqHelper.write(paramOutputStream, arrayOfString10);
/* 1627:     */         }
/* 1628:     */       }
/* 1629:     */       catch (Throwable localThrowable2)
/* 1630:     */       {
/* 1631:1515 */         localThrowable2.printStackTrace(Jaguar.log);
/* 1632:1516 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/* 1633:1517 */         return localThrowable2.getClass().getName();
/* 1634:     */       }
/* 1635:     */     case 53: 
/* 1636:     */       try
/* 1637:     */       {
/* 1638:1526 */         String str3 = param_ServerRequest.read_string();
/* 1639:     */         
/* 1640:1528 */         int j = paramInputStream.read_long();
/* 1641:1529 */         localObject4 = paramOFX200BPWServicesBean
/* 1642:1530 */           .getPayees(
/* 1643:1531 */           str3, 
/* 1644:1532 */           j);
/* 1645:1534 */         if (param_ServerRequest.isRMI()) {
/* 1646:1534 */           param_ServerRequest.write_value(localObject4, new PayeeInfo[0].getClass());
/* 1647:     */         } else {
/* 1648:1534 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject4);
/* 1649:     */         }
/* 1650:     */       }
/* 1651:     */       catch (Throwable localThrowable3)
/* 1652:     */       {
/* 1653:1538 */         localThrowable3.printStackTrace(Jaguar.log);
/* 1654:1539 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/* 1655:1540 */         return localThrowable3.getClass().getName();
/* 1656:     */       }
/* 1657:     */     case 54: 
/* 1658:     */       try
/* 1659:     */       {
/* 1660:1549 */         String str4 = param_ServerRequest.read_string();
/* 1661:1550 */         arrayOfPayeeInfo = paramOFX200BPWServicesBean
/* 1662:1551 */           .getPayees(
/* 1663:1552 */           str4);
/* 1664:1554 */         if (param_ServerRequest.isRMI()) {
/* 1665:1554 */           param_ServerRequest.write_value(arrayOfPayeeInfo, new PayeeInfo[0].getClass());
/* 1666:     */         } else {
/* 1667:1554 */           PayeeInfoSeqHelper.write(paramOutputStream, arrayOfPayeeInfo);
/* 1668:     */         }
/* 1669:     */       }
/* 1670:     */       catch (Throwable localThrowable4)
/* 1671:     */       {
/* 1672:1558 */         localThrowable4.printStackTrace(Jaguar.log);
/* 1673:1559 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/* 1674:1560 */         return localThrowable4.getClass().getName();
/* 1675:     */       }
/* 1676:     */     case 55: 
/* 1677:     */       try
/* 1678:     */       {
/* 1679:1569 */         String str5 = param_ServerRequest.read_string();
/* 1680:1570 */         arrayOfPayeeInfo = paramOFX200BPWServicesBean
/* 1681:1571 */           .searchGlobalPayees(
/* 1682:1572 */           str5);
/* 1683:1574 */         if (param_ServerRequest.isRMI()) {
/* 1684:1574 */           param_ServerRequest.write_value(arrayOfPayeeInfo, new PayeeInfo[0].getClass());
/* 1685:     */         } else {
/* 1686:1574 */           PayeeInfoSeqHelper.write(paramOutputStream, arrayOfPayeeInfo);
/* 1687:     */         }
/* 1688:     */       }
/* 1689:     */       catch (Throwable localThrowable5)
/* 1690:     */       {
/* 1691:1578 */         localThrowable5.printStackTrace(Jaguar.log);
/* 1692:1579 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/* 1693:1580 */         return localThrowable5.getClass().getName();
/* 1694:     */       }
/* 1695:     */     case 56: 
/* 1696:     */       try
/* 1697:     */       {
/* 1698:1589 */         PayeeInfo localPayeeInfo1 = (PayeeInfo)param_ServerRequest.read_value(PayeeInfo.class);
/* 1699:     */         
/* 1700:1591 */         int k = paramInputStream.read_long();
/* 1701:1592 */         localObject4 = paramOFX200BPWServicesBean
/* 1702:1593 */           .updatePayee(
/* 1703:1594 */           localPayeeInfo1, 
/* 1704:1595 */           k);
/* 1705:1597 */         if (param_ServerRequest.isRMI()) {
/* 1706:1597 */           param_ServerRequest.write_value(localObject4, new PayeeInfo[0].getClass());
/* 1707:     */         } else {
/* 1708:1597 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject4);
/* 1709:     */         }
/* 1710:     */       }
/* 1711:     */       catch (Throwable localThrowable6)
/* 1712:     */       {
/* 1713:1601 */         localThrowable6.printStackTrace(Jaguar.log);
/* 1714:1602 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/* 1715:1603 */         return localThrowable6.getClass().getName();
/* 1716:     */       }
/* 1717:     */     case 57: 
/* 1718:     */       try
/* 1719:     */       {
/* 1720:1612 */         PayeeInfo localPayeeInfo2 = (PayeeInfo)param_ServerRequest.read_value(PayeeInfo.class);
/* 1721:     */         
/* 1722:1614 */         localObject1 = (PayeeRouteInfo)param_ServerRequest.read_value(PayeeRouteInfo.class);
/* 1723:1615 */         paramOFX200BPWServicesBean
/* 1724:1616 */           .updatePayee(
/* 1725:1617 */           localPayeeInfo2, 
/* 1726:1618 */           (PayeeRouteInfo)localObject1);
/* 1727:     */       }
/* 1728:     */       catch (Throwable localThrowable7)
/* 1729:     */       {
/* 1730:1623 */         localThrowable7.printStackTrace(Jaguar.log);
/* 1731:1624 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/* 1732:1625 */         return localThrowable7.getClass().getName();
/* 1733:     */       }
/* 1734:     */     case 58: 
/* 1735:     */       try
/* 1736:     */       {
/* 1737:1634 */         String str6 = param_ServerRequest.read_string();
/* 1738:1635 */         paramOFX200BPWServicesBean
/* 1739:1636 */           .deletePayee(
/* 1740:1637 */           str6);
/* 1741:     */       }
/* 1742:     */       catch (Throwable localThrowable8)
/* 1743:     */       {
/* 1744:1642 */         localThrowable8.printStackTrace(Jaguar.log);
/* 1745:1643 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/* 1746:1644 */         return localThrowable8.getClass().getName();
/* 1747:     */       }
/* 1748:     */     case 59: 
/* 1749:     */       try
/* 1750:     */       {
/* 1751:     */         String[] arrayOfString1;
/* 1752:1653 */         if (param_ServerRequest.isRMI()) {
/* 1753:1653 */           arrayOfString1 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 1754:     */         } else {
/* 1755:1653 */           arrayOfString1 = StringSeqHelper.read(paramInputStream);
/* 1756:     */         }
/* 1757:1655 */         paramOFX200BPWServicesBean.deletePayees(
/* 1758:1656 */           arrayOfString1);
/* 1759:     */       }
/* 1760:     */       catch (Throwable localThrowable9)
/* 1761:     */       {
/* 1762:1661 */         localThrowable9.printStackTrace(Jaguar.log);
/* 1763:1662 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/* 1764:1663 */         return localThrowable9.getClass().getName();
/* 1765:     */       }
/* 1766:     */     case 60: 
/* 1767:     */       try
/* 1768:     */       {
/* 1769:1672 */         String str7 = param_ServerRequest.read_string();
/* 1770:1673 */         localObject1 = paramOFX200BPWServicesBean
/* 1771:1674 */           .findPayeeByID(
/* 1772:1675 */           str7);
/* 1773:     */         
/* 1774:1677 */         param_ServerRequest.write_value(localObject1, PayeeInfo.class);
/* 1775:     */       }
/* 1776:     */       catch (Throwable localThrowable10)
/* 1777:     */       {
/* 1778:1681 */         localThrowable10.printStackTrace(Jaguar.log);
/* 1779:1682 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/* 1780:1683 */         return localThrowable10.getClass().getName();
/* 1781:     */       }
/* 1782:     */     case 61: 
/* 1783:     */       try
/* 1784:     */       {
/* 1785:1692 */         String str8 = param_ServerRequest.read_string();
/* 1786:     */         
/* 1787:1694 */         localObject1 = param_ServerRequest.read_string();
/* 1788:1695 */         paramOFX200BPWServicesBean
/* 1789:1696 */           .setPayeeStatus(
/* 1790:1697 */           str8, 
/* 1791:1698 */           (String)localObject1);
/* 1792:     */       }
/* 1793:     */       catch (Throwable localThrowable11)
/* 1794:     */       {
/* 1795:1703 */         localThrowable11.printStackTrace(Jaguar.log);
/* 1796:1704 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/* 1797:1705 */         return localThrowable11.getClass().getName();
/* 1798:     */       }
/* 1799:     */     case 62: 
/* 1800:     */       try
/* 1801:     */       {
/* 1802:1714 */         int i = paramInputStream.read_long();
/* 1803:1715 */         m = paramOFX200BPWServicesBean
/* 1804:1716 */           .getSmartDate(
/* 1805:1717 */           i);
/* 1806:     */         
/* 1807:1719 */         paramOutputStream.write_long(m);
/* 1808:     */       }
/* 1809:     */       catch (Throwable localThrowable12)
/* 1810:     */       {
/* 1811:1723 */         localThrowable12.printStackTrace(Jaguar.log);
/* 1812:1724 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/* 1813:1725 */         return localThrowable12.getClass().getName();
/* 1814:     */       }
/* 1815:     */     case 63: 
/* 1816:     */       try
/* 1817:     */       {
/* 1818:1734 */         String str9 = param_ServerRequest.read_string();
/* 1819:     */         
/* 1820:1736 */         m = paramInputStream.read_long();
/* 1821:1737 */         localObject4 = paramOFX200BPWServicesBean
/* 1822:1738 */           .getGlobalPayees(
/* 1823:1739 */           str9, 
/* 1824:1740 */           m);
/* 1825:1742 */         if (param_ServerRequest.isRMI()) {
/* 1826:1742 */           param_ServerRequest.write_value(localObject4, new PayeeInfo[0].getClass());
/* 1827:     */         } else {
/* 1828:1742 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject4);
/* 1829:     */         }
/* 1830:     */       }
/* 1831:     */       catch (Throwable localThrowable13)
/* 1832:     */       {
/* 1833:1746 */         localThrowable13.printStackTrace(Jaguar.log);
/* 1834:1747 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/* 1835:1748 */         return localThrowable13.getClass().getName();
/* 1836:     */       }
/* 1837:     */     case 64: 
/* 1838:     */       try
/* 1839:     */       {
/* 1840:1757 */         PayeeInfo localPayeeInfo3 = (PayeeInfo)param_ServerRequest.read_value(PayeeInfo.class);
/* 1841:     */         
/* 1842:1759 */         m = paramInputStream.read_long();
/* 1843:1760 */         localObject4 = paramOFX200BPWServicesBean
/* 1844:1761 */           .addPayee(
/* 1845:1762 */           localPayeeInfo3, 
/* 1846:1763 */           m);
/* 1847:     */         
/* 1848:1765 */         param_ServerRequest.write_string((String)localObject4);
/* 1849:     */       }
/* 1850:     */       catch (Throwable localThrowable14)
/* 1851:     */       {
/* 1852:1769 */         localThrowable14.printStackTrace(Jaguar.log);
/* 1853:1770 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/* 1854:1771 */         return localThrowable14.getClass().getName();
/* 1855:     */       }
/* 1856:     */     case 65: 
/* 1857:     */       try
/* 1858:     */       {
/* 1859:     */         ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo1;
/* 1860:1780 */         if (param_ServerRequest.isRMI()) {
/* 1861:1780 */           arrayOfConsumerCrossRefInfo1 = (ConsumerCrossRefInfo[])param_ServerRequest.read_value(new ConsumerCrossRefInfo[0].getClass());
/* 1862:     */         } else {
/* 1863:1780 */           arrayOfConsumerCrossRefInfo1 = ConsumerCrossRefInfoSeqHelper.read(paramInputStream);
/* 1864:     */         }
/* 1865:1781 */         m = 
/* 1866:1782 */           paramOFX200BPWServicesBean.addConsumerCrossRef(
/* 1867:1783 */           arrayOfConsumerCrossRefInfo1);
/* 1868:     */         
/* 1869:1785 */         paramOutputStream.write_long(m);
/* 1870:     */       }
/* 1871:     */       catch (Throwable localThrowable15)
/* 1872:     */       {
/* 1873:1789 */         localThrowable15.printStackTrace(Jaguar.log);
/* 1874:1790 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/* 1875:1791 */         return localThrowable15.getClass().getName();
/* 1876:     */       }
/* 1877:     */     case 66: 
/* 1878:     */       try
/* 1879:     */       {
/* 1880:     */         ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo2;
/* 1881:1800 */         if (param_ServerRequest.isRMI()) {
/* 1882:1800 */           arrayOfConsumerCrossRefInfo2 = (ConsumerCrossRefInfo[])param_ServerRequest.read_value(new ConsumerCrossRefInfo[0].getClass());
/* 1883:     */         } else {
/* 1884:1800 */           arrayOfConsumerCrossRefInfo2 = ConsumerCrossRefInfoSeqHelper.read(paramInputStream);
/* 1885:     */         }
/* 1886:1801 */         m = 
/* 1887:1802 */           paramOFX200BPWServicesBean.deleteConsumerCrossRef(
/* 1888:1803 */           arrayOfConsumerCrossRefInfo2);
/* 1889:     */         
/* 1890:1805 */         paramOutputStream.write_long(m);
/* 1891:     */       }
/* 1892:     */       catch (Throwable localThrowable16)
/* 1893:     */       {
/* 1894:1809 */         localThrowable16.printStackTrace(Jaguar.log);
/* 1895:1810 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/* 1896:1811 */         return localThrowable16.getClass().getName();
/* 1897:     */       }
/* 1898:     */     case 67: 
/* 1899:     */       try
/* 1900:     */       {
/* 1901:     */         String[] arrayOfString2;
/* 1902:1820 */         if (param_ServerRequest.isRMI()) {
/* 1903:1820 */           arrayOfString2 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 1904:     */         } else {
/* 1905:1820 */           arrayOfString2 = StringSeqHelper.read(paramInputStream);
/* 1906:     */         }
/* 1907:1821 */         ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo3 = paramOFX200BPWServicesBean
/* 1908:1822 */           .getConsumerCrossRef(
/* 1909:1823 */           arrayOfString2);
/* 1910:1825 */         if (param_ServerRequest.isRMI()) {
/* 1911:1825 */           param_ServerRequest.write_value(arrayOfConsumerCrossRefInfo3, new ConsumerCrossRefInfo[0].getClass());
/* 1912:     */         } else {
/* 1913:1825 */           ConsumerCrossRefInfoSeqHelper.write(paramOutputStream, arrayOfConsumerCrossRefInfo3);
/* 1914:     */         }
/* 1915:     */       }
/* 1916:     */       catch (Throwable localThrowable17)
/* 1917:     */       {
/* 1918:1829 */         localThrowable17.printStackTrace(Jaguar.log);
/* 1919:1830 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/* 1920:1831 */         return localThrowable17.getClass().getName();
/* 1921:     */       }
/* 1922:     */     case 68: 
/* 1923:     */       try
/* 1924:     */       {
/* 1925:     */         CustomerBankInfo[] arrayOfCustomerBankInfo1;
/* 1926:1840 */         if (param_ServerRequest.isRMI()) {
/* 1927:1840 */           arrayOfCustomerBankInfo1 = (CustomerBankInfo[])param_ServerRequest.read_value(new CustomerBankInfo[0].getClass());
/* 1928:     */         } else {
/* 1929:1840 */           arrayOfCustomerBankInfo1 = CustomerBankInfoSeqHelper.read(paramInputStream);
/* 1930:     */         }
/* 1931:1841 */         n = 
/* 1932:1842 */           paramOFX200BPWServicesBean.addCustomerBankInfo(
/* 1933:1843 */           arrayOfCustomerBankInfo1);
/* 1934:     */         
/* 1935:1845 */         paramOutputStream.write_long(n);
/* 1936:     */       }
/* 1937:     */       catch (Throwable localThrowable18)
/* 1938:     */       {
/* 1939:1849 */         localThrowable18.printStackTrace(Jaguar.log);
/* 1940:1850 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/* 1941:1851 */         return localThrowable18.getClass().getName();
/* 1942:     */       }
/* 1943:     */     case 69: 
/* 1944:     */       try
/* 1945:     */       {
/* 1946:     */         CustomerBankInfo[] arrayOfCustomerBankInfo2;
/* 1947:1860 */         if (param_ServerRequest.isRMI()) {
/* 1948:1860 */           arrayOfCustomerBankInfo2 = (CustomerBankInfo[])param_ServerRequest.read_value(new CustomerBankInfo[0].getClass());
/* 1949:     */         } else {
/* 1950:1860 */           arrayOfCustomerBankInfo2 = CustomerBankInfoSeqHelper.read(paramInputStream);
/* 1951:     */         }
/* 1952:1861 */         n = 
/* 1953:1862 */           paramOFX200BPWServicesBean.deleteCustomerBankInfo(
/* 1954:1863 */           arrayOfCustomerBankInfo2);
/* 1955:     */         
/* 1956:1865 */         paramOutputStream.write_long(n);
/* 1957:     */       }
/* 1958:     */       catch (Throwable localThrowable19)
/* 1959:     */       {
/* 1960:1869 */         localThrowable19.printStackTrace(Jaguar.log);
/* 1961:1870 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/* 1962:1871 */         return localThrowable19.getClass().getName();
/* 1963:     */       }
/* 1964:     */     case 70: 
/* 1965:     */       try
/* 1966:     */       {
/* 1967:     */         String[] arrayOfString3;
/* 1968:1880 */         if (param_ServerRequest.isRMI()) {
/* 1969:1880 */           arrayOfString3 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 1970:     */         } else {
/* 1971:1880 */           arrayOfString3 = StringSeqHelper.read(paramInputStream);
/* 1972:     */         }
/* 1973:1881 */         CustomerBankInfo[] arrayOfCustomerBankInfo3 = paramOFX200BPWServicesBean
/* 1974:1882 */           .getCustomerBankInfo(
/* 1975:1883 */           arrayOfString3);
/* 1976:1885 */         if (param_ServerRequest.isRMI()) {
/* 1977:1885 */           param_ServerRequest.write_value(arrayOfCustomerBankInfo3, new CustomerBankInfo[0].getClass());
/* 1978:     */         } else {
/* 1979:1885 */           CustomerBankInfoSeqHelper.write(paramOutputStream, arrayOfCustomerBankInfo3);
/* 1980:     */         }
/* 1981:     */       }
/* 1982:     */       catch (Throwable localThrowable20)
/* 1983:     */       {
/* 1984:1889 */         localThrowable20.printStackTrace(Jaguar.log);
/* 1985:1890 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/* 1986:1891 */         return localThrowable20.getClass().getName();
/* 1987:     */       }
/* 1988:     */     case 71: 
/* 1989:     */       try
/* 1990:     */       {
/* 1991:     */         CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo1;
/* 1992:1900 */         if (param_ServerRequest.isRMI()) {
/* 1993:1900 */           arrayOfCustomerProductAccessInfo1 = (CustomerProductAccessInfo[])param_ServerRequest.read_value(new CustomerProductAccessInfo[0].getClass());
/* 1994:     */         } else {
/* 1995:1900 */           arrayOfCustomerProductAccessInfo1 = CustomerProductAccessInfoSeqHelper.read(paramInputStream);
/* 1996:     */         }
/* 1997:1901 */         i1 = 
/* 1998:1902 */           paramOFX200BPWServicesBean.addCustomerProductAccessInfo(
/* 1999:1903 */           arrayOfCustomerProductAccessInfo1);
/* 2000:     */         
/* 2001:1905 */         paramOutputStream.write_long(i1);
/* 2002:     */       }
/* 2003:     */       catch (Throwable localThrowable21)
/* 2004:     */       {
/* 2005:1909 */         localThrowable21.printStackTrace(Jaguar.log);
/* 2006:1910 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/* 2007:1911 */         return localThrowable21.getClass().getName();
/* 2008:     */       }
/* 2009:     */     case 72: 
/* 2010:     */       try
/* 2011:     */       {
/* 2012:     */         CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo2;
/* 2013:1920 */         if (param_ServerRequest.isRMI()) {
/* 2014:1920 */           arrayOfCustomerProductAccessInfo2 = (CustomerProductAccessInfo[])param_ServerRequest.read_value(new CustomerProductAccessInfo[0].getClass());
/* 2015:     */         } else {
/* 2016:1920 */           arrayOfCustomerProductAccessInfo2 = CustomerProductAccessInfoSeqHelper.read(paramInputStream);
/* 2017:     */         }
/* 2018:1921 */         i1 = 
/* 2019:1922 */           paramOFX200BPWServicesBean.deleteCustomerProductAccessInfo(
/* 2020:1923 */           arrayOfCustomerProductAccessInfo2);
/* 2021:     */         
/* 2022:1925 */         paramOutputStream.write_long(i1);
/* 2023:     */       }
/* 2024:     */       catch (Throwable localThrowable22)
/* 2025:     */       {
/* 2026:1929 */         localThrowable22.printStackTrace(Jaguar.log);
/* 2027:1930 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/* 2028:1931 */         return localThrowable22.getClass().getName();
/* 2029:     */       }
/* 2030:     */     case 73: 
/* 2031:     */       try
/* 2032:     */       {
/* 2033:     */         String[] arrayOfString4;
/* 2034:1940 */         if (param_ServerRequest.isRMI()) {
/* 2035:1940 */           arrayOfString4 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 2036:     */         } else {
/* 2037:1940 */           arrayOfString4 = StringSeqHelper.read(paramInputStream);
/* 2038:     */         }
/* 2039:1941 */         localObject2 = 
/* 2040:1942 */           paramOFX200BPWServicesBean.getCustomerProductAccessInfo(
/* 2041:1943 */           arrayOfString4);
/* 2042:1945 */         if (param_ServerRequest.isRMI()) {
/* 2043:1945 */           param_ServerRequest.write_value(localObject2, new CustomerProductAccessInfo[0].getClass());
/* 2044:     */         } else {
/* 2045:1945 */           CustomerProductAccessInfoSeqHelper.write(paramOutputStream, (CustomerProductAccessInfo[])localObject2);
/* 2046:     */         }
/* 2047:     */       }
/* 2048:     */       catch (Throwable localThrowable23)
/* 2049:     */       {
/* 2050:1949 */         localThrowable23.printStackTrace(Jaguar.log);
/* 2051:1950 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/* 2052:1951 */         return localThrowable23.getClass().getName();
/* 2053:     */       }
/* 2054:     */     case 74: 
/* 2055:     */       try
/* 2056:     */       {
/* 2057:1960 */         String str10 = param_ServerRequest.read_string();
/* 2058:     */         
/* 2059:1962 */         localObject2 = param_ServerRequest.read_string();
/* 2060:1963 */         bool2 = paramOFX200BPWServicesBean
/* 2061:1964 */           .validateMetavanteCustAcctByConsumerID(
/* 2062:1965 */           str10, 
/* 2063:1966 */           (String)localObject2);
/* 2064:     */         
/* 2065:1968 */         paramOutputStream.write_boolean(bool2);
/* 2066:     */       }
/* 2067:     */       catch (Throwable localThrowable24)
/* 2068:     */       {
/* 2069:1972 */         localThrowable24.printStackTrace(Jaguar.log);
/* 2070:1973 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/* 2071:1974 */         return localThrowable24.getClass().getName();
/* 2072:     */       }
/* 2073:     */     case 75: 
/* 2074:     */       try
/* 2075:     */       {
/* 2076:1983 */         String str11 = param_ServerRequest.read_string();
/* 2077:     */         
/* 2078:1985 */         localObject2 = param_ServerRequest.read_string();
/* 2079:1986 */         bool2 = paramOFX200BPWServicesBean
/* 2080:1987 */           .validateMetavanteCustAcctByCustomerID(
/* 2081:1988 */           str11, 
/* 2082:1989 */           (String)localObject2);
/* 2083:     */         
/* 2084:1991 */         paramOutputStream.write_boolean(bool2);
/* 2085:     */       }
/* 2086:     */       catch (Throwable localThrowable25)
/* 2087:     */       {
/* 2088:1995 */         localThrowable25.printStackTrace(Jaguar.log);
/* 2089:1996 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/* 2090:1997 */         return localThrowable25.getClass().getName();
/* 2091:     */       }
/* 2092:     */     case 76: 
/* 2093:     */       try
/* 2094:     */       {
/* 2095:     */         BPWHist localBPWHist1;
/* 2096:2006 */         if (param_ServerRequest.isRMI()) {
/* 2097:2006 */           localBPWHist1 = (BPWHist)param_ServerRequest.read_value(BPWHist.class);
/* 2098:     */         } else {
/* 2099:2006 */           localBPWHist1 = BPWHistHelper.read(paramInputStream);
/* 2100:     */         }
/* 2101:2007 */         localObject2 = 
/* 2102:2008 */           paramOFX200BPWServicesBean.getPmtHistory(
/* 2103:2009 */           localBPWHist1);
/* 2104:2011 */         if (param_ServerRequest.isRMI()) {
/* 2105:2011 */           param_ServerRequest.write_value(localObject2, BPWHist.class);
/* 2106:     */         } else {
/* 2107:2011 */           BPWHistHelper.write(paramOutputStream, (BPWHist)localObject2);
/* 2108:     */         }
/* 2109:     */       }
/* 2110:     */       catch (Throwable localThrowable26)
/* 2111:     */       {
/* 2112:2015 */         localThrowable26.printStackTrace(Jaguar.log);
/* 2113:2016 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/* 2114:2017 */         return localThrowable26.getClass().getName();
/* 2115:     */       }
/* 2116:     */     case 77: 
/* 2117:     */       try
/* 2118:     */       {
/* 2119:     */         BPWHist localBPWHist2;
/* 2120:2026 */         if (param_ServerRequest.isRMI()) {
/* 2121:2026 */           localBPWHist2 = (BPWHist)param_ServerRequest.read_value(BPWHist.class);
/* 2122:     */         } else {
/* 2123:2026 */           localBPWHist2 = BPWHistHelper.read(paramInputStream);
/* 2124:     */         }
/* 2125:2027 */         localObject2 = 
/* 2126:2028 */           paramOFX200BPWServicesBean.getIntraHistory(
/* 2127:2029 */           localBPWHist2);
/* 2128:2031 */         if (param_ServerRequest.isRMI()) {
/* 2129:2031 */           param_ServerRequest.write_value(localObject2, BPWHist.class);
/* 2130:     */         } else {
/* 2131:2031 */           BPWHistHelper.write(paramOutputStream, (BPWHist)localObject2);
/* 2132:     */         }
/* 2133:     */       }
/* 2134:     */       catch (Throwable localThrowable27)
/* 2135:     */       {
/* 2136:2035 */         localThrowable27.printStackTrace(Jaguar.log);
/* 2137:2036 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/* 2138:2037 */         return localThrowable27.getClass().getName();
/* 2139:     */       }
/* 2140:     */     case 78: 
/* 2141:     */       try
/* 2142:     */       {
/* 2143:2046 */         String str12 = param_ServerRequest.read_string();
/* 2144:2047 */         localObject2 = paramOFX200BPWServicesBean
/* 2145:2048 */           .getIntraById(
/* 2146:2049 */           str12);
/* 2147:     */         
/* 2148:2051 */         param_ServerRequest.write_value(localObject2, IntraTrnInfo.class);
/* 2149:     */       }
/* 2150:     */       catch (Throwable localThrowable28)
/* 2151:     */       {
/* 2152:2055 */         localThrowable28.printStackTrace(Jaguar.log);
/* 2153:2056 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/* 2154:2057 */         return localThrowable28.getClass().getName();
/* 2155:     */       }
/* 2156:     */     case 79: 
/* 2157:     */       try
/* 2158:     */       {
/* 2159:     */         String[] arrayOfString5;
/* 2160:2066 */         if (param_ServerRequest.isRMI()) {
/* 2161:2066 */           arrayOfString5 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 2162:     */         } else {
/* 2163:2066 */           arrayOfString5 = StringSeqHelper.read(paramInputStream);
/* 2164:     */         }
/* 2165:2067 */         localObject2 = 
/* 2166:2068 */           paramOFX200BPWServicesBean.getIntraById(
/* 2167:2069 */           arrayOfString5);
/* 2168:2071 */         if (param_ServerRequest.isRMI()) {
/* 2169:2071 */           param_ServerRequest.write_value(localObject2, new IntraTrnInfo[0].getClass());
/* 2170:     */         } else {
/* 2171:2071 */           IntraTrnInfoSeqHelper.write(paramOutputStream, (IntraTrnInfo[])localObject2);
/* 2172:     */         }
/* 2173:     */       }
/* 2174:     */       catch (Throwable localThrowable29)
/* 2175:     */       {
/* 2176:2075 */         localThrowable29.printStackTrace(Jaguar.log);
/* 2177:2076 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/* 2178:2077 */         return localThrowable29.getClass().getName();
/* 2179:     */       }
/* 2180:     */     case 80: 
/* 2181:     */       try
/* 2182:     */       {
/* 2183:     */         String[] arrayOfString6;
/* 2184:2086 */         if (param_ServerRequest.isRMI()) {
/* 2185:2086 */           arrayOfString6 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 2186:     */         } else {
/* 2187:2086 */           arrayOfString6 = StringSeqHelper.read(paramInputStream);
/* 2188:     */         }
/* 2189:2088 */         boolean bool1 = paramInputStream.read_boolean();
/* 2190:2089 */         localObject5 = paramOFX200BPWServicesBean
/* 2191:2090 */           .getIntraByRecSrvrTId(
/* 2192:2091 */           arrayOfString6, 
/* 2193:2092 */           bool1);
/* 2194:2094 */         if (param_ServerRequest.isRMI()) {
/* 2195:2094 */           param_ServerRequest.write_value(localObject5, new IntraTrnInfo[0].getClass());
/* 2196:     */         } else {
/* 2197:2094 */           IntraTrnInfoSeqHelper.write(paramOutputStream, (IntraTrnInfo[])localObject5);
/* 2198:     */         }
/* 2199:     */       }
/* 2200:     */       catch (Throwable localThrowable30)
/* 2201:     */       {
/* 2202:2098 */         localThrowable30.printStackTrace(Jaguar.log);
/* 2203:2099 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/* 2204:2100 */         return localThrowable30.getClass().getName();
/* 2205:     */       }
/* 2206:     */     case 81: 
/* 2207:     */       try
/* 2208:     */       {
/* 2209:     */         String[] arrayOfString7;
/* 2210:2109 */         if (param_ServerRequest.isRMI()) {
/* 2211:2109 */           arrayOfString7 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 2212:     */         } else {
/* 2213:2109 */           arrayOfString7 = StringSeqHelper.read(paramInputStream);
/* 2214:     */         }
/* 2215:2110 */         localObject3 = 
/* 2216:2111 */           paramOFX200BPWServicesBean.getPmtById(
/* 2217:2112 */           arrayOfString7);
/* 2218:2114 */         if (param_ServerRequest.isRMI()) {
/* 2219:2114 */           param_ServerRequest.write_value(localObject3, new PmtInfo[0].getClass());
/* 2220:     */         } else {
/* 2221:2114 */           PmtInfoSeqHelper.write(paramOutputStream, (PmtInfo[])localObject3);
/* 2222:     */         }
/* 2223:     */       }
/* 2224:     */       catch (Throwable localThrowable31)
/* 2225:     */       {
/* 2226:2118 */         localThrowable31.printStackTrace(Jaguar.log);
/* 2227:2119 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/* 2228:2120 */         return localThrowable31.getClass().getName();
/* 2229:     */       }
/* 2230:     */     case 82: 
/* 2231:     */       try
/* 2232:     */       {
/* 2233:2129 */         String str13 = param_ServerRequest.read_string();
/* 2234:2130 */         localObject3 = paramOFX200BPWServicesBean
/* 2235:2131 */           .getPmtById(
/* 2236:2132 */           str13);
/* 2237:     */         
/* 2238:2134 */         param_ServerRequest.write_value(localObject3, PmtInfo.class);
/* 2239:     */       }
/* 2240:     */       catch (Throwable localThrowable32)
/* 2241:     */       {
/* 2242:2138 */         localThrowable32.printStackTrace(Jaguar.log);
/* 2243:2139 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/* 2244:2140 */         return localThrowable32.getClass().getName();
/* 2245:     */       }
/* 2246:     */     case 83: 
/* 2247:     */       try
/* 2248:     */       {
/* 2249:     */         String[] arrayOfString8;
/* 2250:2149 */         if (param_ServerRequest.isRMI()) {
/* 2251:2149 */           arrayOfString8 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 2252:     */         } else {
/* 2253:2149 */           arrayOfString8 = StringSeqHelper.read(paramInputStream);
/* 2254:     */         }
/* 2255:2150 */         localObject3 = 
/* 2256:2151 */           paramOFX200BPWServicesBean.getRecPmtById(
/* 2257:2152 */           arrayOfString8);
/* 2258:2154 */         if (param_ServerRequest.isRMI()) {
/* 2259:2154 */           param_ServerRequest.write_value(localObject3, new RecPmtInfo[0].getClass());
/* 2260:     */         } else {
/* 2261:2154 */           RecPmtInfoSeqHelper.write(paramOutputStream, (RecPmtInfo[])localObject3);
/* 2262:     */         }
/* 2263:     */       }
/* 2264:     */       catch (Throwable localThrowable33)
/* 2265:     */       {
/* 2266:2158 */         localThrowable33.printStackTrace(Jaguar.log);
/* 2267:2159 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/* 2268:2160 */         return localThrowable33.getClass().getName();
/* 2269:     */       }
/* 2270:     */     case 84: 
/* 2271:     */       try
/* 2272:     */       {
/* 2273:     */         String[] arrayOfString9;
/* 2274:2169 */         if (param_ServerRequest.isRMI()) {
/* 2275:2169 */           arrayOfString9 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 2276:     */         } else {
/* 2277:2169 */           arrayOfString9 = StringSeqHelper.read(paramInputStream);
/* 2278:     */         }
/* 2279:2170 */         localObject3 = 
/* 2280:2171 */           paramOFX200BPWServicesBean.getRecIntraById(
/* 2281:2172 */           arrayOfString9);
/* 2282:2174 */         if (param_ServerRequest.isRMI()) {
/* 2283:2174 */           param_ServerRequest.write_value(localObject3, new RecIntraTrnInfo[0].getClass());
/* 2284:     */         } else {
/* 2285:2174 */           RecIntraTrnInfoSeqHelper.write(paramOutputStream, (RecIntraTrnInfo[])localObject3);
/* 2286:     */         }
/* 2287:     */       }
/* 2288:     */       catch (Throwable localThrowable34)
/* 2289:     */       {
/* 2290:2178 */         localThrowable34.printStackTrace(Jaguar.log);
/* 2291:2179 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/* 2292:2180 */         return localThrowable34.getClass().getName();
/* 2293:     */       }
/* 2294:     */     case 85: 
/* 2295:     */       try
/* 2296:     */       {
/* 2297:2189 */         String str14 = param_ServerRequest.read_string();
/* 2298:2190 */         localObject3 = paramOFX200BPWServicesBean
/* 2299:2191 */           .getRecIntraById(
/* 2300:2192 */           str14);
/* 2301:     */         
/* 2302:2194 */         param_ServerRequest.write_value(localObject3, RecIntraTrnInfo.class);
/* 2303:     */       }
/* 2304:     */       catch (Throwable localThrowable35)
/* 2305:     */       {
/* 2306:2198 */         localThrowable35.printStackTrace(Jaguar.log);
/* 2307:2199 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/* 2308:2200 */         return localThrowable35.getClass().getName();
/* 2309:     */       }
/* 2310:     */     case 86: 
/* 2311:     */       try
/* 2312:     */       {
/* 2313:2209 */         String str15 = param_ServerRequest.read_string();
/* 2314:     */         
/* 2315:2211 */         localObject3 = param_ServerRequest.read_string();
/* 2316:2212 */         localObject5 = paramOFX200BPWServicesBean
/* 2317:2213 */           .getPayeeByListId(
/* 2318:2214 */           str15, 
/* 2319:2215 */           (String)localObject3);
/* 2320:     */         
/* 2321:2217 */         param_ServerRequest.write_value(localObject5, PayeeInfo.class);
/* 2322:     */       }
/* 2323:     */       catch (Throwable localThrowable36)
/* 2324:     */       {
/* 2325:2221 */         if ((localThrowable36 instanceof FFSException))
/* 2326:     */         {
/* 2327:2223 */           if (UserException.ok(paramOutputStream)) {
/* 2328:2225 */             if (param_ServerRequest.isRMI())
/* 2329:     */             {
/* 2330:2227 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2331:2228 */               param_ServerRequest.write_value((FFSException)localThrowable36, FFSException.class);
/* 2332:     */             }
/* 2333:     */             else
/* 2334:     */             {
/* 2335:2232 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2336:2233 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable36);
/* 2337:     */             }
/* 2338:     */           }
/* 2339:2236 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable36);
/* 2340:     */         }
/* 2341:2238 */         localThrowable36.printStackTrace(Jaguar.log);
/* 2342:2239 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/* 2343:2240 */         return localThrowable36.getClass().getName();
/* 2344:     */       }
/* 2345:     */     case 87: 
/* 2346:     */       try
/* 2347:     */       {
/* 2348:2248 */         AccountTypesMap localAccountTypesMap = paramOFX200BPWServicesBean
/* 2349:2249 */           .getAccountTypesMap();
/* 2350:     */         
/* 2351:2251 */         param_ServerRequest.write_value(localAccountTypesMap, AccountTypesMap.class);
/* 2352:     */       }
/* 2353:     */       catch (Throwable localThrowable37)
/* 2354:     */       {
/* 2355:2255 */         if ((localThrowable37 instanceof FFSException))
/* 2356:     */         {
/* 2357:2257 */           if (UserException.ok(paramOutputStream)) {
/* 2358:2259 */             if (param_ServerRequest.isRMI())
/* 2359:     */             {
/* 2360:2261 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2361:2262 */               param_ServerRequest.write_value((FFSException)localThrowable37, FFSException.class);
/* 2362:     */             }
/* 2363:     */             else
/* 2364:     */             {
/* 2365:2266 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2366:2267 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable37);
/* 2367:     */             }
/* 2368:     */           }
/* 2369:2270 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable37);
/* 2370:     */         }
/* 2371:2272 */         localThrowable37.printStackTrace(Jaguar.log);
/* 2372:2273 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable37, true);
/* 2373:2274 */         return localThrowable37.getClass().getName();
/* 2374:     */       }
/* 2375:     */     }
/* 2376:2279 */     return null;
/* 2377:     */   }
/* 2378:     */   
/* 2379:     */   public static String localInvoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/* 2380:     */   {
/* 2381:2289 */     _ServerRequest local_ServerRequest = new _ServerRequest(paramInputStream, paramOutputStream, (paramInt & 0x1) != 0);
/* 2382:2290 */     OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)paramObject;
/* 2383:2291 */     Integer localInteger = null;
/* 2384:2292 */     boolean bool1 = false;
/* 2385:2293 */     if (!paramString.startsWith("#"))
/* 2386:     */     {
/* 2387:2295 */       localInteger = (Integer)_localMethods2.get(paramString);
/* 2388:2296 */       if (localInteger != null) {
/* 2389:2297 */         bool1 = true;
/* 2390:     */       }
/* 2391:     */     }
/* 2392:     */     else
/* 2393:     */     {
/* 2394:2301 */       localInteger = (Integer)_localMethods.get(paramString);
/* 2395:     */     }
/* 2396:2303 */     if (localInteger == null) {
/* 2397:2305 */       return remoteInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/* 2398:     */     }
/* 2399:2307 */     LocalFrame localLocalFrame = LocalStack.getCurrent().top();
/* 2400:     */     Object localObject7;
/* 2401:     */     Object localObject9;
/* 2402:     */     int i3;
/* 2403:     */     Object localObject13;
/* 2404:     */     Object localObject8;
/* 2405:     */     Object localObject12;
/* 2406:     */     String[] arrayOfString6;
/* 2407:2308 */     switch (localInteger.intValue())
/* 2408:     */     {
/* 2409:     */     case 0: 
/* 2410:     */       try
/* 2411:     */       {
/* 2412:2315 */         localOFX200BPWServicesBean.ejbCreate();
/* 2413:     */       }
/* 2414:     */       catch (Throwable localThrowable1)
/* 2415:     */       {
/* 2416:2320 */         localThrowable1.printStackTrace(Jaguar.log);
/* 2417:2321 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/* 2418:2322 */         return localThrowable1.getClass().getName();
/* 2419:     */       }
/* 2420:     */     case 1: 
/* 2421:     */       try
/* 2422:     */       {
/* 2423:2331 */         localOFX200BPWServicesBean.ejbRemove();
/* 2424:     */       }
/* 2425:     */       catch (Throwable localThrowable2)
/* 2426:     */       {
/* 2427:2336 */         localThrowable2.printStackTrace(Jaguar.log);
/* 2428:2337 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/* 2429:2338 */         return localThrowable2.getClass().getName();
/* 2430:     */       }
/* 2431:     */     case 2: 
/* 2432:     */       try
/* 2433:     */       {
/* 2434:     */         CustomerInfo[] arrayOfCustomerInfo1;
/* 2435:2347 */         if (!bool1)
/* 2436:     */         {
/* 2437:2349 */           arrayOfCustomerInfo1 = (CustomerInfo[])localLocalFrame.get(0);
/* 2438:     */         }
/* 2439:     */         else
/* 2440:     */         {
/* 2441:2353 */           Object localObject1 = localLocalFrame.get(0);
/* 2442:2354 */           arrayOfCustomerInfo1 = (CustomerInfo[])ObjectVal.clone(localObject1);
/* 2443:     */         }
/* 2444:2356 */         int j = localOFX200BPWServicesBean
/* 2445:2357 */           .addCustomers(
/* 2446:2358 */           arrayOfCustomerInfo1);
/* 2447:     */         
/* 2448:2360 */         localLocalFrame.setResult(j);
/* 2449:     */       }
/* 2450:     */       catch (Throwable localThrowable3)
/* 2451:     */       {
/* 2452:2364 */         localThrowable3.printStackTrace(Jaguar.log);
/* 2453:2365 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/* 2454:2366 */         return localThrowable3.getClass().getName();
/* 2455:     */       }
/* 2456:     */     case 3: 
/* 2457:     */       try
/* 2458:     */       {
/* 2459:     */         CustomerInfo[] arrayOfCustomerInfo2;
/* 2460:2375 */         if (!bool1)
/* 2461:     */         {
/* 2462:2377 */           arrayOfCustomerInfo2 = (CustomerInfo[])localLocalFrame.get(0);
/* 2463:     */         }
/* 2464:     */         else
/* 2465:     */         {
/* 2466:2381 */           Object localObject2 = localLocalFrame.get(0);
/* 2467:2382 */           arrayOfCustomerInfo2 = (CustomerInfo[])ObjectVal.clone(localObject2);
/* 2468:     */         }
/* 2469:2384 */         int k = localOFX200BPWServicesBean
/* 2470:2385 */           .modifyCustomers(
/* 2471:2386 */           arrayOfCustomerInfo2);
/* 2472:     */         
/* 2473:2388 */         localLocalFrame.setResult(k);
/* 2474:     */       }
/* 2475:     */       catch (Throwable localThrowable4)
/* 2476:     */       {
/* 2477:2392 */         localThrowable4.printStackTrace(Jaguar.log);
/* 2478:2393 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/* 2479:2394 */         return localThrowable4.getClass().getName();
/* 2480:     */       }
/* 2481:     */     case 4: 
/* 2482:     */       try
/* 2483:     */       {
/* 2484:     */         String[] arrayOfString1;
/* 2485:2403 */         if (!bool1)
/* 2486:     */         {
/* 2487:2405 */           arrayOfString1 = (String[])localLocalFrame.get(0);
/* 2488:     */         }
/* 2489:     */         else
/* 2490:     */         {
/* 2491:2409 */           Object localObject3 = localLocalFrame.get(0);
/* 2492:2410 */           arrayOfString1 = (String[])ObjectVal.clone(localObject3);
/* 2493:     */         }
/* 2494:2412 */         int m = localOFX200BPWServicesBean
/* 2495:2413 */           .deleteCustomers(
/* 2496:2414 */           arrayOfString1);
/* 2497:     */         
/* 2498:2416 */         localLocalFrame.setResult(m);
/* 2499:     */       }
/* 2500:     */       catch (Throwable localThrowable5)
/* 2501:     */       {
/* 2502:2420 */         localThrowable5.printStackTrace(Jaguar.log);
/* 2503:2421 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/* 2504:2422 */         return localThrowable5.getClass().getName();
/* 2505:     */       }
/* 2506:     */     case 5: 
/* 2507:     */       try
/* 2508:     */       {
/* 2509:     */         String[] arrayOfString2;
/* 2510:2431 */         if (!bool1)
/* 2511:     */         {
/* 2512:2433 */           arrayOfString2 = (String[])localLocalFrame.get(0);
/* 2513:     */         }
/* 2514:     */         else
/* 2515:     */         {
/* 2516:2437 */           Object localObject4 = localLocalFrame.get(0);
/* 2517:2438 */           arrayOfString2 = (String[])ObjectVal.clone(localObject4);
/* 2518:     */         }
/* 2519:2441 */         int n = ((Integer)localLocalFrame.get(1)).intValue();
/* 2520:2442 */         int i6 = localOFX200BPWServicesBean
/* 2521:2443 */           .deleteCustomers(
/* 2522:2444 */           arrayOfString2, 
/* 2523:2445 */           n);
/* 2524:     */         
/* 2525:2447 */         localLocalFrame.setResult(i6);
/* 2526:     */       }
/* 2527:     */       catch (Throwable localThrowable6)
/* 2528:     */       {
/* 2529:2451 */         localThrowable6.printStackTrace(Jaguar.log);
/* 2530:2452 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/* 2531:2453 */         return localThrowable6.getClass().getName();
/* 2532:     */       }
/* 2533:     */     case 6: 
/* 2534:     */       try
/* 2535:     */       {
/* 2536:     */         String[] arrayOfString3;
/* 2537:2462 */         if (!bool1)
/* 2538:     */         {
/* 2539:2464 */           arrayOfString3 = (String[])localLocalFrame.get(0);
/* 2540:     */         }
/* 2541:     */         else
/* 2542:     */         {
/* 2543:2468 */           Object localObject5 = localLocalFrame.get(0);
/* 2544:2469 */           arrayOfString3 = (String[])ObjectVal.clone(localObject5);
/* 2545:     */         }
/* 2546:2471 */         int i1 = localOFX200BPWServicesBean
/* 2547:2472 */           .deactivateCustomers(
/* 2548:2473 */           arrayOfString3);
/* 2549:     */         
/* 2550:2475 */         localLocalFrame.setResult(i1);
/* 2551:     */       }
/* 2552:     */       catch (Throwable localThrowable7)
/* 2553:     */       {
/* 2554:2479 */         localThrowable7.printStackTrace(Jaguar.log);
/* 2555:2480 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/* 2556:2481 */         return localThrowable7.getClass().getName();
/* 2557:     */       }
/* 2558:     */     case 7: 
/* 2559:     */       try
/* 2560:     */       {
/* 2561:     */         String[] arrayOfString4;
/* 2562:2490 */         if (!bool1)
/* 2563:     */         {
/* 2564:2492 */           arrayOfString4 = (String[])localLocalFrame.get(0);
/* 2565:     */         }
/* 2566:     */         else
/* 2567:     */         {
/* 2568:2496 */           Object localObject6 = localLocalFrame.get(0);
/* 2569:2497 */           arrayOfString4 = (String[])ObjectVal.clone(localObject6);
/* 2570:     */         }
/* 2571:2499 */         int i2 = localOFX200BPWServicesBean
/* 2572:2500 */           .activateCustomers(
/* 2573:2501 */           arrayOfString4);
/* 2574:     */         
/* 2575:2503 */         localLocalFrame.setResult(i2);
/* 2576:     */       }
/* 2577:     */       catch (Throwable localThrowable8)
/* 2578:     */       {
/* 2579:2507 */         localThrowable8.printStackTrace(Jaguar.log);
/* 2580:2508 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/* 2581:2509 */         return localThrowable8.getClass().getName();
/* 2582:     */       }
/* 2583:     */     case 8: 
/* 2584:     */       try
/* 2585:     */       {
/* 2586:     */         String[] arrayOfString5;
/* 2587:2518 */         if (!bool1)
/* 2588:     */         {
/* 2589:2520 */           arrayOfString5 = (String[])localLocalFrame.get(0);
/* 2590:     */         }
/* 2591:     */         else
/* 2592:     */         {
/* 2593:2524 */           localObject7 = localLocalFrame.get(0);
/* 2594:2525 */           arrayOfString5 = (String[])ObjectVal.clone(localObject7);
/* 2595:     */         }
/* 2596:2527 */         localObject7 = 
/* 2597:2528 */           localOFX200BPWServicesBean.getCustomersInfo(
/* 2598:2529 */           arrayOfString5);
/* 2599:     */         
/* 2600:2531 */         localLocalFrame.setResult(localObject7);
/* 2601:     */       }
/* 2602:     */       catch (Throwable localThrowable9)
/* 2603:     */       {
/* 2604:2535 */         localThrowable9.printStackTrace(Jaguar.log);
/* 2605:2536 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/* 2606:2537 */         return localThrowable9.getClass().getName();
/* 2607:     */       }
/* 2608:     */     case 9: 
/* 2609:     */       try
/* 2610:     */       {
/* 2611:2546 */         String str1 = (String)localLocalFrame.get(0);
/* 2612:2547 */         localObject7 = localOFX200BPWServicesBean
/* 2613:2548 */           .getCustomerByType(
/* 2614:2549 */           str1);
/* 2615:     */         
/* 2616:2551 */         localLocalFrame.setResult(localObject7);
/* 2617:     */       }
/* 2618:     */       catch (Throwable localThrowable10)
/* 2619:     */       {
/* 2620:2555 */         localThrowable10.printStackTrace(Jaguar.log);
/* 2621:2556 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/* 2622:2557 */         return localThrowable10.getClass().getName();
/* 2623:     */       }
/* 2624:     */     case 10: 
/* 2625:     */       try
/* 2626:     */       {
/* 2627:2566 */         String str2 = (String)localLocalFrame.get(0);
/* 2628:2567 */         localObject7 = localOFX200BPWServicesBean
/* 2629:2568 */           .getCustomerByFI(
/* 2630:2569 */           str2);
/* 2631:     */         
/* 2632:2571 */         localLocalFrame.setResult(localObject7);
/* 2633:     */       }
/* 2634:     */       catch (Throwable localThrowable11)
/* 2635:     */       {
/* 2636:2575 */         localThrowable11.printStackTrace(Jaguar.log);
/* 2637:2576 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/* 2638:2577 */         return localThrowable11.getClass().getName();
/* 2639:     */       }
/* 2640:     */     case 11: 
/* 2641:     */       try
/* 2642:     */       {
/* 2643:2586 */         String str3 = (String)localLocalFrame.get(0);
/* 2644:2587 */         localObject7 = localOFX200BPWServicesBean
/* 2645:2588 */           .getCustomerByCategory(
/* 2646:2589 */           str3);
/* 2647:     */         
/* 2648:2591 */         localLocalFrame.setResult(localObject7);
/* 2649:     */       }
/* 2650:     */       catch (Throwable localThrowable12)
/* 2651:     */       {
/* 2652:2595 */         localThrowable12.printStackTrace(Jaguar.log);
/* 2653:2596 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/* 2654:2597 */         return localThrowable12.getClass().getName();
/* 2655:     */       }
/* 2656:     */     case 12: 
/* 2657:     */       try
/* 2658:     */       {
/* 2659:2606 */         String str4 = (String)localLocalFrame.get(0);
/* 2660:2607 */         localObject7 = localOFX200BPWServicesBean
/* 2661:2608 */           .getCustomerByGroup(
/* 2662:2609 */           str4);
/* 2663:     */         
/* 2664:2611 */         localLocalFrame.setResult(localObject7);
/* 2665:     */       }
/* 2666:     */       catch (Throwable localThrowable13)
/* 2667:     */       {
/* 2668:2615 */         localThrowable13.printStackTrace(Jaguar.log);
/* 2669:2616 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/* 2670:2617 */         return localThrowable13.getClass().getName();
/* 2671:     */       }
/* 2672:     */     case 13: 
/* 2673:     */       try
/* 2674:     */       {
/* 2675:2626 */         String str5 = (String)localLocalFrame.get(0);
/* 2676:     */         
/* 2677:2628 */         localObject7 = (String)localLocalFrame.get(1);
/* 2678:2629 */         localObject9 = localOFX200BPWServicesBean
/* 2679:2630 */           .getCustomerByTypeAndFI(
/* 2680:2631 */           str5, 
/* 2681:2632 */           (String)localObject7);
/* 2682:     */         
/* 2683:2634 */         localLocalFrame.setResult(localObject9);
/* 2684:     */       }
/* 2685:     */       catch (Throwable localThrowable14)
/* 2686:     */       {
/* 2687:2638 */         localThrowable14.printStackTrace(Jaguar.log);
/* 2688:2639 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/* 2689:2640 */         return localThrowable14.getClass().getName();
/* 2690:     */       }
/* 2691:     */     case 14: 
/* 2692:     */       try
/* 2693:     */       {
/* 2694:2649 */         String str6 = (String)localLocalFrame.get(0);
/* 2695:     */         
/* 2696:2651 */         localObject7 = (String)localLocalFrame.get(1);
/* 2697:2652 */         localObject9 = localOFX200BPWServicesBean
/* 2698:2653 */           .getCustomerByCategoryAndFI(
/* 2699:2654 */           str6, 
/* 2700:2655 */           (String)localObject7);
/* 2701:     */         
/* 2702:2657 */         localLocalFrame.setResult(localObject9);
/* 2703:     */       }
/* 2704:     */       catch (Throwable localThrowable15)
/* 2705:     */       {
/* 2706:2661 */         localThrowable15.printStackTrace(Jaguar.log);
/* 2707:2662 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/* 2708:2663 */         return localThrowable15.getClass().getName();
/* 2709:     */       }
/* 2710:     */     case 15: 
/* 2711:     */       try
/* 2712:     */       {
/* 2713:2672 */         String str7 = (String)localLocalFrame.get(0);
/* 2714:     */         
/* 2715:2674 */         localObject7 = (String)localLocalFrame.get(1);
/* 2716:2675 */         localObject9 = localOFX200BPWServicesBean
/* 2717:2676 */           .getCustomerByGroupAndFI(
/* 2718:2677 */           str7, 
/* 2719:2678 */           (String)localObject7);
/* 2720:     */         
/* 2721:2680 */         localLocalFrame.setResult(localObject9);
/* 2722:     */       }
/* 2723:     */       catch (Throwable localThrowable16)
/* 2724:     */       {
/* 2725:2684 */         localThrowable16.printStackTrace(Jaguar.log);
/* 2726:2685 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/* 2727:2686 */         return localThrowable16.getClass().getName();
/* 2728:     */       }
/* 2729:     */     case 16: 
/* 2730:     */       try
/* 2731:     */       {
/* 2732:2694 */         PayeeInfo[] arrayOfPayeeInfo = localOFX200BPWServicesBean
/* 2733:2695 */           .getLinkedPayees();
/* 2734:     */         
/* 2735:2697 */         localLocalFrame.setResult(arrayOfPayeeInfo);
/* 2736:     */       }
/* 2737:     */       catch (Throwable localThrowable17)
/* 2738:     */       {
/* 2739:2701 */         localThrowable17.printStackTrace(Jaguar.log);
/* 2740:2702 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/* 2741:2703 */         return localThrowable17.getClass().getName();
/* 2742:     */       }
/* 2743:     */     case 17: 
/* 2744:     */       try
/* 2745:     */       {
/* 2746:2712 */         int i = ((Integer)localLocalFrame.get(0)).intValue();
/* 2747:2713 */         localObject7 = localOFX200BPWServicesBean
/* 2748:2714 */           .getMostUsedPayees(
/* 2749:2715 */           i);
/* 2750:     */         
/* 2751:2717 */         localLocalFrame.setResult(localObject7);
/* 2752:     */       }
/* 2753:     */       catch (Throwable localThrowable18)
/* 2754:     */       {
/* 2755:2721 */         localThrowable18.printStackTrace(Jaguar.log);
/* 2756:2722 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/* 2757:2723 */         return localThrowable18.getClass().getName();
/* 2758:     */       }
/* 2759:     */     case 18: 
/* 2760:     */       try
/* 2761:     */       {
/* 2762:2732 */         String str8 = (String)localLocalFrame.get(0);
/* 2763:2733 */         localObject7 = localOFX200BPWServicesBean
/* 2764:2734 */           .getPreferredPayees(
/* 2765:2735 */           str8);
/* 2766:     */         
/* 2767:2737 */         localLocalFrame.setResult(localObject7);
/* 2768:     */       }
/* 2769:     */       catch (Throwable localThrowable19)
/* 2770:     */       {
/* 2771:2741 */         localThrowable19.printStackTrace(Jaguar.log);
/* 2772:2742 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/* 2773:2743 */         return localThrowable19.getClass().getName();
/* 2774:     */       }
/* 2775:     */     case 19: 
/* 2776:     */       try
/* 2777:     */       {
/* 2778:2752 */         String str9 = (String)localLocalFrame.get(0);
/* 2779:     */         
/* 2780:2754 */         i3 = ((Integer)localLocalFrame.get(1)).intValue();
/* 2781:2755 */         localObject9 = localOFX200BPWServicesBean
/* 2782:2756 */           .getPendingPmtsByCustomerID(
/* 2783:2757 */           str9, 
/* 2784:2758 */           i3);
/* 2785:     */         
/* 2786:2760 */         localLocalFrame.setResult(localObject9);
/* 2787:     */       }
/* 2788:     */       catch (Throwable localThrowable20)
/* 2789:     */       {
/* 2790:2764 */         localThrowable20.printStackTrace(Jaguar.log);
/* 2791:2765 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/* 2792:2766 */         return localThrowable20.getClass().getName();
/* 2793:     */       }
/* 2794:     */     case 20: 
/* 2795:     */       try
/* 2796:     */       {
/* 2797:2775 */         String str10 = (String)localLocalFrame.get(0);
/* 2798:     */         
/* 2799:2777 */         i3 = ((Integer)localLocalFrame.get(1)).intValue();
/* 2800:     */         
/* 2801:2779 */         int i7 = ((Integer)localLocalFrame.get(2)).intValue();
/* 2802:2780 */         localObject13 = localOFX200BPWServicesBean
/* 2803:2781 */           .getPendingPmtsAndHistoryByCustomerID(
/* 2804:2782 */           str10, 
/* 2805:2783 */           i3, 
/* 2806:2784 */           i7);
/* 2807:     */         
/* 2808:2786 */         localLocalFrame.setResult(localObject13);
/* 2809:     */       }
/* 2810:     */       catch (Throwable localThrowable21)
/* 2811:     */       {
/* 2812:2790 */         localThrowable21.printStackTrace(Jaguar.log);
/* 2813:2791 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/* 2814:2792 */         return localThrowable21.getClass().getName();
/* 2815:     */       }
/* 2816:     */     case 21: 
/* 2817:     */       try
/* 2818:     */       {
/* 2819:2801 */         String str11 = (String)localLocalFrame.get(0);
/* 2820:     */         
/* 2821:2803 */         i3 = ((Integer)localLocalFrame.get(1)).intValue();
/* 2822:2804 */         TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV1 = localOFX200BPWServicesBean
/* 2823:2805 */           .getPendingIntrasByCustomerID(
/* 2824:2806 */           str11, 
/* 2825:2807 */           i3);
/* 2826:     */         
/* 2827:2809 */         localLocalFrame.setResult(arrayOfTypeIntraSyncRsV1);
/* 2828:     */       }
/* 2829:     */       catch (Throwable localThrowable22)
/* 2830:     */       {
/* 2831:2813 */         localThrowable22.printStackTrace(Jaguar.log);
/* 2832:2814 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/* 2833:2815 */         return localThrowable22.getClass().getName();
/* 2834:     */       }
/* 2835:     */     case 22: 
/* 2836:     */       try
/* 2837:     */       {
/* 2838:2824 */         String str12 = (String)localLocalFrame.get(0);
/* 2839:     */         
/* 2840:2826 */         i3 = ((Integer)localLocalFrame.get(1)).intValue();
/* 2841:     */         
/* 2842:2828 */         int i8 = ((Integer)localLocalFrame.get(2)).intValue();
/* 2843:2829 */         localObject13 = localOFX200BPWServicesBean
/* 2844:2830 */           .getPendingIntrasAndHistoryByCustomerID(
/* 2845:2831 */           str12, 
/* 2846:2832 */           i3, 
/* 2847:2833 */           i8);
/* 2848:     */         
/* 2849:2835 */         localLocalFrame.setResult(localObject13);
/* 2850:     */       }
/* 2851:     */       catch (Throwable localThrowable23)
/* 2852:     */       {
/* 2853:2839 */         localThrowable23.printStackTrace(Jaguar.log);
/* 2854:2840 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/* 2855:2841 */         return localThrowable23.getClass().getName();
/* 2856:     */       }
/* 2857:     */     case 23: 
/* 2858:     */       try
/* 2859:     */       {
/* 2860:     */         TypePmtSyncRqV1 localTypePmtSyncRqV11;
/* 2861:2850 */         if (!bool1)
/* 2862:     */         {
/* 2863:2852 */           localTypePmtSyncRqV11 = (TypePmtSyncRqV1)localLocalFrame.get(0);
/* 2864:     */         }
/* 2865:     */         else
/* 2866:     */         {
/* 2867:2856 */           localObject8 = localLocalFrame.get(0);
/* 2868:2857 */           localTypePmtSyncRqV11 = (TypePmtSyncRqV1)ObjectVal.clone(localObject8);
/* 2869:     */         }
/* 2870:2860 */         if (!bool1)
/* 2871:     */         {
/* 2872:2862 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 2873:     */         }
/* 2874:     */         else
/* 2875:     */         {
/* 2876:2866 */           Object localObject10 = localLocalFrame.get(1);
/* 2877:2867 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject10);
/* 2878:     */         }
/* 2879:2870 */         int i9 = ((Integer)localLocalFrame.get(2)).intValue();
/* 2880:2871 */         localObject13 = localOFX200BPWServicesBean
/* 2881:2872 */           .getPendingPmts(
/* 2882:2873 */           localTypePmtSyncRqV11, 
/* 2883:2874 */           (TypeUserData)localObject8, 
/* 2884:2875 */           i9);
/* 2885:     */         
/* 2886:2877 */         localLocalFrame.setResult(localObject13);
/* 2887:     */       }
/* 2888:     */       catch (Throwable localThrowable24)
/* 2889:     */       {
/* 2890:2881 */         localThrowable24.printStackTrace(Jaguar.log);
/* 2891:2882 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/* 2892:2883 */         return localThrowable24.getClass().getName();
/* 2893:     */       }
/* 2894:     */     case 24: 
/* 2895:     */       try
/* 2896:     */       {
/* 2897:     */         TypeIntraSyncRqV1 localTypeIntraSyncRqV11;
/* 2898:2892 */         if (!bool1)
/* 2899:     */         {
/* 2900:2894 */           localTypeIntraSyncRqV11 = (TypeIntraSyncRqV1)localLocalFrame.get(0);
/* 2901:     */         }
/* 2902:     */         else
/* 2903:     */         {
/* 2904:2898 */           localObject8 = localLocalFrame.get(0);
/* 2905:2899 */           localTypeIntraSyncRqV11 = (TypeIntraSyncRqV1)ObjectVal.clone(localObject8);
/* 2906:     */         }
/* 2907:2902 */         if (!bool1)
/* 2908:     */         {
/* 2909:2904 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 2910:     */         }
/* 2911:     */         else
/* 2912:     */         {
/* 2913:2908 */           Object localObject11 = localLocalFrame.get(1);
/* 2914:2909 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject11);
/* 2915:     */         }
/* 2916:2912 */         int i10 = ((Integer)localLocalFrame.get(2)).intValue();
/* 2917:2913 */         localObject13 = localOFX200BPWServicesBean
/* 2918:2914 */           .getPendingIntras(
/* 2919:2915 */           localTypeIntraSyncRqV11, 
/* 2920:2916 */           (TypeUserData)localObject8, 
/* 2921:2917 */           i10);
/* 2922:     */         
/* 2923:2919 */         localLocalFrame.setResult(localObject13);
/* 2924:     */       }
/* 2925:     */       catch (Throwable localThrowable25)
/* 2926:     */       {
/* 2927:2923 */         localThrowable25.printStackTrace(Jaguar.log);
/* 2928:2924 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/* 2929:2925 */         return localThrowable25.getClass().getName();
/* 2930:     */       }
/* 2931:     */     case 25: 
/* 2932:     */       try
/* 2933:     */       {
/* 2934:2934 */         String str13 = (String)localLocalFrame.get(0);
/* 2935:2935 */         localObject8 = localOFX200BPWServicesBean
/* 2936:2936 */           .getPmtStatus(
/* 2937:2937 */           str13);
/* 2938:     */         
/* 2939:2939 */         localLocalFrame.setResult(localObject8);
/* 2940:     */       }
/* 2941:     */       catch (Throwable localThrowable26)
/* 2942:     */       {
/* 2943:2943 */         localThrowable26.printStackTrace(Jaguar.log);
/* 2944:2944 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/* 2945:2945 */         return localThrowable26.getClass().getName();
/* 2946:     */       }
/* 2947:     */     case 26: 
/* 2948:     */       try
/* 2949:     */       {
/* 2950:2954 */         String str14 = (String)localLocalFrame.get(0);
/* 2951:     */         
/* 2952:2956 */         localObject8 = (String)localLocalFrame.get(1);
/* 2953:2957 */         boolean bool2 = localOFX200BPWServicesBean
/* 2954:2958 */           .checkPayeeEditMask(
/* 2955:2959 */           str14, 
/* 2956:2960 */           (String)localObject8);
/* 2957:     */         
/* 2958:2962 */         localLocalFrame.setResult(bool2);
/* 2959:     */       }
/* 2960:     */       catch (Throwable localThrowable27)
/* 2961:     */       {
/* 2962:2966 */         localThrowable27.printStackTrace(Jaguar.log);
/* 2963:2967 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/* 2964:2968 */         return localThrowable27.getClass().getName();
/* 2965:     */       }
/* 2966:     */     case 27: 
/* 2967:     */       try
/* 2968:     */       {
/* 2969:     */         IntraTrnRslt[] arrayOfIntraTrnRslt;
/* 2970:2977 */         if (!bool1)
/* 2971:     */         {
/* 2972:2979 */           arrayOfIntraTrnRslt = (IntraTrnRslt[])localLocalFrame.get(0);
/* 2973:     */         }
/* 2974:     */         else
/* 2975:     */         {
/* 2976:2983 */           localObject8 = localLocalFrame.get(0);
/* 2977:2984 */           arrayOfIntraTrnRslt = (IntraTrnRslt[])ObjectVal.clone(localObject8);
/* 2978:     */         }
/* 2979:2987 */         localOFX200BPWServicesBean.processIntraTrnRslt(
/* 2980:2988 */           arrayOfIntraTrnRslt);
/* 2981:     */       }
/* 2982:     */       catch (Throwable localThrowable28)
/* 2983:     */       {
/* 2984:2993 */         localThrowable28.printStackTrace(Jaguar.log);
/* 2985:2994 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/* 2986:2995 */         return localThrowable28.getClass().getName();
/* 2987:     */       }
/* 2988:     */     case 28: 
/* 2989:     */       try
/* 2990:     */       {
/* 2991:     */         PmtTrnRslt[] arrayOfPmtTrnRslt;
/* 2992:3004 */         if (!bool1)
/* 2993:     */         {
/* 2994:3006 */           arrayOfPmtTrnRslt = (PmtTrnRslt[])localLocalFrame.get(0);
/* 2995:     */         }
/* 2996:     */         else
/* 2997:     */         {
/* 2998:3010 */           localObject8 = localLocalFrame.get(0);
/* 2999:3011 */           arrayOfPmtTrnRslt = (PmtTrnRslt[])ObjectVal.clone(localObject8);
/* 3000:     */         }
/* 3001:3014 */         localOFX200BPWServicesBean.processPmtTrnRslt(
/* 3002:3015 */           arrayOfPmtTrnRslt);
/* 3003:     */       }
/* 3004:     */       catch (Throwable localThrowable29)
/* 3005:     */       {
/* 3006:3020 */         localThrowable29.printStackTrace(Jaguar.log);
/* 3007:3021 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/* 3008:3022 */         return localThrowable29.getClass().getName();
/* 3009:     */       }
/* 3010:     */     case 29: 
/* 3011:     */       try
/* 3012:     */       {
/* 3013:     */         CustPayeeRslt[] arrayOfCustPayeeRslt;
/* 3014:3031 */         if (!bool1)
/* 3015:     */         {
/* 3016:3033 */           arrayOfCustPayeeRslt = (CustPayeeRslt[])localLocalFrame.get(0);
/* 3017:     */         }
/* 3018:     */         else
/* 3019:     */         {
/* 3020:3037 */           localObject8 = localLocalFrame.get(0);
/* 3021:3038 */           arrayOfCustPayeeRslt = (CustPayeeRslt[])ObjectVal.clone(localObject8);
/* 3022:     */         }
/* 3023:3041 */         localOFX200BPWServicesBean.processCustPayeeRslt(
/* 3024:3042 */           arrayOfCustPayeeRslt);
/* 3025:     */       }
/* 3026:     */       catch (Throwable localThrowable30)
/* 3027:     */       {
/* 3028:3047 */         localThrowable30.printStackTrace(Jaguar.log);
/* 3029:3048 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/* 3030:3049 */         return localThrowable30.getClass().getName();
/* 3031:     */       }
/* 3032:     */     case 30: 
/* 3033:     */       try
/* 3034:     */       {
/* 3035:     */         FundsAllocRslt[] arrayOfFundsAllocRslt1;
/* 3036:3058 */         if (!bool1)
/* 3037:     */         {
/* 3038:3060 */           arrayOfFundsAllocRslt1 = (FundsAllocRslt[])localLocalFrame.get(0);
/* 3039:     */         }
/* 3040:     */         else
/* 3041:     */         {
/* 3042:3064 */           localObject8 = localLocalFrame.get(0);
/* 3043:3065 */           arrayOfFundsAllocRslt1 = (FundsAllocRslt[])ObjectVal.clone(localObject8);
/* 3044:     */         }
/* 3045:3068 */         localOFX200BPWServicesBean.processFundAllocRslt(
/* 3046:3069 */           arrayOfFundsAllocRslt1);
/* 3047:     */       }
/* 3048:     */       catch (Throwable localThrowable31)
/* 3049:     */       {
/* 3050:3074 */         localThrowable31.printStackTrace(Jaguar.log);
/* 3051:3075 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/* 3052:3076 */         return localThrowable31.getClass().getName();
/* 3053:     */       }
/* 3054:     */     case 31: 
/* 3055:     */       try
/* 3056:     */       {
/* 3057:     */         FundsAllocRslt[] arrayOfFundsAllocRslt2;
/* 3058:3085 */         if (!bool1)
/* 3059:     */         {
/* 3060:3087 */           arrayOfFundsAllocRslt2 = (FundsAllocRslt[])localLocalFrame.get(0);
/* 3061:     */         }
/* 3062:     */         else
/* 3063:     */         {
/* 3064:3091 */           localObject8 = localLocalFrame.get(0);
/* 3065:3092 */           arrayOfFundsAllocRslt2 = (FundsAllocRslt[])ObjectVal.clone(localObject8);
/* 3066:     */         }
/* 3067:3095 */         localOFX200BPWServicesBean.processFundRevertRslt(
/* 3068:3096 */           arrayOfFundsAllocRslt2);
/* 3069:     */       }
/* 3070:     */       catch (Throwable localThrowable32)
/* 3071:     */       {
/* 3072:3101 */         localThrowable32.printStackTrace(Jaguar.log);
/* 3073:3102 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/* 3074:3103 */         return localThrowable32.getClass().getName();
/* 3075:     */       }
/* 3076:     */     case 32: 
/* 3077:     */       try
/* 3078:     */       {
/* 3079:     */         PayeeRslt[] arrayOfPayeeRslt;
/* 3080:3112 */         if (!bool1)
/* 3081:     */         {
/* 3082:3114 */           arrayOfPayeeRslt = (PayeeRslt[])localLocalFrame.get(0);
/* 3083:     */         }
/* 3084:     */         else
/* 3085:     */         {
/* 3086:3118 */           localObject8 = localLocalFrame.get(0);
/* 3087:3119 */           arrayOfPayeeRslt = (PayeeRslt[])ObjectVal.clone(localObject8);
/* 3088:     */         }
/* 3089:3122 */         localOFX200BPWServicesBean.processPayeeRslt(
/* 3090:3123 */           arrayOfPayeeRslt);
/* 3091:     */       }
/* 3092:     */       catch (Throwable localThrowable33)
/* 3093:     */       {
/* 3094:3128 */         localThrowable33.printStackTrace(Jaguar.log);
/* 3095:3129 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/* 3096:3130 */         return localThrowable33.getClass().getName();
/* 3097:     */       }
/* 3098:     */     case 33: 
/* 3099:     */       try
/* 3100:     */       {
/* 3101:     */         PayeeInfo localPayeeInfo1;
/* 3102:3139 */         if (!bool1)
/* 3103:     */         {
/* 3104:3141 */           localPayeeInfo1 = (PayeeInfo)localLocalFrame.get(0);
/* 3105:     */         }
/* 3106:     */         else
/* 3107:     */         {
/* 3108:3145 */           localObject8 = localLocalFrame.get(0);
/* 3109:3146 */           localPayeeInfo1 = (PayeeInfo)ObjectVal.clone(localObject8);
/* 3110:     */         }
/* 3111:3148 */         localObject8 = 
/* 3112:3149 */           localOFX200BPWServicesBean.addPayeeFromBackend(
/* 3113:3150 */           localPayeeInfo1);
/* 3114:     */         
/* 3115:3152 */         localLocalFrame.setResult(localObject8);
/* 3116:     */       }
/* 3117:     */       catch (Throwable localThrowable34)
/* 3118:     */       {
/* 3119:3156 */         localThrowable34.printStackTrace(Jaguar.log);
/* 3120:3157 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/* 3121:3158 */         return localThrowable34.getClass().getName();
/* 3122:     */       }
/* 3123:     */     case 34: 
/* 3124:     */       try
/* 3125:     */       {
/* 3126:     */         PayeeInfo localPayeeInfo2;
/* 3127:3167 */         if (!bool1)
/* 3128:     */         {
/* 3129:3169 */           localPayeeInfo2 = (PayeeInfo)localLocalFrame.get(0);
/* 3130:     */         }
/* 3131:     */         else
/* 3132:     */         {
/* 3133:3173 */           localObject8 = localLocalFrame.get(0);
/* 3134:3174 */           localPayeeInfo2 = (PayeeInfo)ObjectVal.clone(localObject8);
/* 3135:     */         }
/* 3136:3176 */         localObject8 = 
/* 3137:3177 */           localOFX200BPWServicesBean.updatePayeeFromBackend(
/* 3138:3178 */           localPayeeInfo2);
/* 3139:     */         
/* 3140:3180 */         localLocalFrame.setResult(localObject8);
/* 3141:     */       }
/* 3142:     */       catch (Throwable localThrowable35)
/* 3143:     */       {
/* 3144:3184 */         localThrowable35.printStackTrace(Jaguar.log);
/* 3145:3185 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/* 3146:3186 */         return localThrowable35.getClass().getName();
/* 3147:     */       }
/* 3148:     */     case 35: 
/* 3149:     */       try
/* 3150:     */       {
/* 3151:     */         PayeeRouteInfo localPayeeRouteInfo;
/* 3152:3195 */         if (!bool1)
/* 3153:     */         {
/* 3154:3197 */           localPayeeRouteInfo = (PayeeRouteInfo)localLocalFrame.get(0);
/* 3155:     */         }
/* 3156:     */         else
/* 3157:     */         {
/* 3158:3201 */           localObject8 = localLocalFrame.get(0);
/* 3159:3202 */           localPayeeRouteInfo = (PayeeRouteInfo)ObjectVal.clone(localObject8);
/* 3160:     */         }
/* 3161:3205 */         localOFX200BPWServicesBean.addPayeeRouteInfo(
/* 3162:3206 */           localPayeeRouteInfo);
/* 3163:     */       }
/* 3164:     */       catch (Throwable localThrowable36)
/* 3165:     */       {
/* 3166:3211 */         localThrowable36.printStackTrace(Jaguar.log);
/* 3167:3212 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/* 3168:3213 */         return localThrowable36.getClass().getName();
/* 3169:     */       }
/* 3170:     */     case 36: 
/* 3171:     */       try
/* 3172:     */       {
/* 3173:     */         TypeIntraSyncRqV1 localTypeIntraSyncRqV12;
/* 3174:3222 */         if (!bool1)
/* 3175:     */         {
/* 3176:3224 */           localTypeIntraSyncRqV12 = (TypeIntraSyncRqV1)localLocalFrame.get(0);
/* 3177:     */         }
/* 3178:     */         else
/* 3179:     */         {
/* 3180:3228 */           localObject8 = localLocalFrame.get(0);
/* 3181:3229 */           localTypeIntraSyncRqV12 = (TypeIntraSyncRqV1)ObjectVal.clone(localObject8);
/* 3182:     */         }
/* 3183:3232 */         if (!bool1)
/* 3184:     */         {
/* 3185:3234 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3186:     */         }
/* 3187:     */         else
/* 3188:     */         {
/* 3189:3238 */           localObject12 = localLocalFrame.get(1);
/* 3190:3239 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3191:     */         }
/* 3192:3241 */         localObject12 = 
/* 3193:3242 */           localOFX200BPWServicesBean.processIntraSyncRqV1(
/* 3194:3243 */           localTypeIntraSyncRqV12, 
/* 3195:3244 */           (TypeUserData)localObject8);
/* 3196:     */         
/* 3197:3246 */         localLocalFrame.setResult(localObject12);
/* 3198:     */       }
/* 3199:     */       catch (Throwable localThrowable37)
/* 3200:     */       {
/* 3201:3250 */         localThrowable37.printStackTrace(Jaguar.log);
/* 3202:3251 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable37, true);
/* 3203:3252 */         return localThrowable37.getClass().getName();
/* 3204:     */       }
/* 3205:     */     case 37: 
/* 3206:     */       try
/* 3207:     */       {
/* 3208:     */         TypeIntraTrnRqV1 localTypeIntraTrnRqV1;
/* 3209:3261 */         if (!bool1)
/* 3210:     */         {
/* 3211:3263 */           localTypeIntraTrnRqV1 = (TypeIntraTrnRqV1)localLocalFrame.get(0);
/* 3212:     */         }
/* 3213:     */         else
/* 3214:     */         {
/* 3215:3267 */           localObject8 = localLocalFrame.get(0);
/* 3216:3268 */           localTypeIntraTrnRqV1 = (TypeIntraTrnRqV1)ObjectVal.clone(localObject8);
/* 3217:     */         }
/* 3218:3271 */         if (!bool1)
/* 3219:     */         {
/* 3220:3273 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3221:     */         }
/* 3222:     */         else
/* 3223:     */         {
/* 3224:3277 */           localObject12 = localLocalFrame.get(1);
/* 3225:3278 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3226:     */         }
/* 3227:3280 */         localObject12 = 
/* 3228:3281 */           localOFX200BPWServicesBean.processIntraTrnRqV1(
/* 3229:3282 */           localTypeIntraTrnRqV1, 
/* 3230:3283 */           (TypeUserData)localObject8);
/* 3231:     */         
/* 3232:3285 */         localLocalFrame.setResult(localObject12);
/* 3233:     */       }
/* 3234:     */       catch (Throwable localThrowable38)
/* 3235:     */       {
/* 3236:3289 */         localThrowable38.printStackTrace(Jaguar.log);
/* 3237:3290 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable38, true);
/* 3238:3291 */         return localThrowable38.getClass().getName();
/* 3239:     */       }
/* 3240:     */     case 38: 
/* 3241:     */       try
/* 3242:     */       {
/* 3243:     */         TypePayeeSyncRqV1 localTypePayeeSyncRqV1;
/* 3244:3300 */         if (!bool1)
/* 3245:     */         {
/* 3246:3302 */           localTypePayeeSyncRqV1 = (TypePayeeSyncRqV1)localLocalFrame.get(0);
/* 3247:     */         }
/* 3248:     */         else
/* 3249:     */         {
/* 3250:3306 */           localObject8 = localLocalFrame.get(0);
/* 3251:3307 */           localTypePayeeSyncRqV1 = (TypePayeeSyncRqV1)ObjectVal.clone(localObject8);
/* 3252:     */         }
/* 3253:3310 */         if (!bool1)
/* 3254:     */         {
/* 3255:3312 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3256:     */         }
/* 3257:     */         else
/* 3258:     */         {
/* 3259:3316 */           localObject12 = localLocalFrame.get(1);
/* 3260:3317 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3261:     */         }
/* 3262:3319 */         localObject12 = 
/* 3263:3320 */           localOFX200BPWServicesBean.processPayeeSyncRqV1(
/* 3264:3321 */           localTypePayeeSyncRqV1, 
/* 3265:3322 */           (TypeUserData)localObject8);
/* 3266:     */         
/* 3267:3324 */         localLocalFrame.setResult(localObject12);
/* 3268:     */       }
/* 3269:     */       catch (Throwable localThrowable39)
/* 3270:     */       {
/* 3271:3328 */         localThrowable39.printStackTrace(Jaguar.log);
/* 3272:3329 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable39, true);
/* 3273:3330 */         return localThrowable39.getClass().getName();
/* 3274:     */       }
/* 3275:     */     case 39: 
/* 3276:     */       try
/* 3277:     */       {
/* 3278:     */         TypePayeeTrnRqV1 localTypePayeeTrnRqV1;
/* 3279:3339 */         if (!bool1)
/* 3280:     */         {
/* 3281:3341 */           localTypePayeeTrnRqV1 = (TypePayeeTrnRqV1)localLocalFrame.get(0);
/* 3282:     */         }
/* 3283:     */         else
/* 3284:     */         {
/* 3285:3345 */           localObject8 = localLocalFrame.get(0);
/* 3286:3346 */           localTypePayeeTrnRqV1 = (TypePayeeTrnRqV1)ObjectVal.clone(localObject8);
/* 3287:     */         }
/* 3288:3349 */         if (!bool1)
/* 3289:     */         {
/* 3290:3351 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3291:     */         }
/* 3292:     */         else
/* 3293:     */         {
/* 3294:3355 */           localObject12 = localLocalFrame.get(1);
/* 3295:3356 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3296:     */         }
/* 3297:3358 */         localObject12 = 
/* 3298:3359 */           localOFX200BPWServicesBean.processPayeeTrnRqV1(
/* 3299:3360 */           localTypePayeeTrnRqV1, 
/* 3300:3361 */           (TypeUserData)localObject8);
/* 3301:     */         
/* 3302:3363 */         localLocalFrame.setResult(localObject12);
/* 3303:     */       }
/* 3304:     */       catch (Throwable localThrowable40)
/* 3305:     */       {
/* 3306:3367 */         localThrowable40.printStackTrace(Jaguar.log);
/* 3307:3368 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable40, true);
/* 3308:3369 */         return localThrowable40.getClass().getName();
/* 3309:     */       }
/* 3310:     */     case 40: 
/* 3311:     */       try
/* 3312:     */       {
/* 3313:     */         TypePmtInqTrnRqV1 localTypePmtInqTrnRqV1;
/* 3314:3378 */         if (!bool1)
/* 3315:     */         {
/* 3316:3380 */           localTypePmtInqTrnRqV1 = (TypePmtInqTrnRqV1)localLocalFrame.get(0);
/* 3317:     */         }
/* 3318:     */         else
/* 3319:     */         {
/* 3320:3384 */           localObject8 = localLocalFrame.get(0);
/* 3321:3385 */           localTypePmtInqTrnRqV1 = (TypePmtInqTrnRqV1)ObjectVal.clone(localObject8);
/* 3322:     */         }
/* 3323:3388 */         if (!bool1)
/* 3324:     */         {
/* 3325:3390 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3326:     */         }
/* 3327:     */         else
/* 3328:     */         {
/* 3329:3394 */           localObject12 = localLocalFrame.get(1);
/* 3330:3395 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3331:     */         }
/* 3332:3397 */         localObject12 = 
/* 3333:3398 */           localOFX200BPWServicesBean.processPmtInqTrnRqV1(
/* 3334:3399 */           localTypePmtInqTrnRqV1, 
/* 3335:3400 */           (TypeUserData)localObject8);
/* 3336:     */         
/* 3337:3402 */         localLocalFrame.setResult(localObject12);
/* 3338:     */       }
/* 3339:     */       catch (Throwable localThrowable41)
/* 3340:     */       {
/* 3341:3406 */         localThrowable41.printStackTrace(Jaguar.log);
/* 3342:3407 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable41, true);
/* 3343:3408 */         return localThrowable41.getClass().getName();
/* 3344:     */       }
/* 3345:     */     case 41: 
/* 3346:     */       try
/* 3347:     */       {
/* 3348:     */         TypePmtSyncRqV1 localTypePmtSyncRqV12;
/* 3349:3417 */         if (!bool1)
/* 3350:     */         {
/* 3351:3419 */           localTypePmtSyncRqV12 = (TypePmtSyncRqV1)localLocalFrame.get(0);
/* 3352:     */         }
/* 3353:     */         else
/* 3354:     */         {
/* 3355:3423 */           localObject8 = localLocalFrame.get(0);
/* 3356:3424 */           localTypePmtSyncRqV12 = (TypePmtSyncRqV1)ObjectVal.clone(localObject8);
/* 3357:     */         }
/* 3358:3427 */         if (!bool1)
/* 3359:     */         {
/* 3360:3429 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3361:     */         }
/* 3362:     */         else
/* 3363:     */         {
/* 3364:3433 */           localObject12 = localLocalFrame.get(1);
/* 3365:3434 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3366:     */         }
/* 3367:3436 */         localObject12 = 
/* 3368:3437 */           localOFX200BPWServicesBean.processPmtSyncRqV1(
/* 3369:3438 */           localTypePmtSyncRqV12, 
/* 3370:3439 */           (TypeUserData)localObject8);
/* 3371:     */         
/* 3372:3441 */         localLocalFrame.setResult(localObject12);
/* 3373:     */       }
/* 3374:     */       catch (Throwable localThrowable42)
/* 3375:     */       {
/* 3376:3445 */         localThrowable42.printStackTrace(Jaguar.log);
/* 3377:3446 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable42, true);
/* 3378:3447 */         return localThrowable42.getClass().getName();
/* 3379:     */       }
/* 3380:     */     case 42: 
/* 3381:     */       try
/* 3382:     */       {
/* 3383:     */         TypePmtTrnRqV1 localTypePmtTrnRqV1;
/* 3384:3456 */         if (!bool1)
/* 3385:     */         {
/* 3386:3458 */           localTypePmtTrnRqV1 = (TypePmtTrnRqV1)localLocalFrame.get(0);
/* 3387:     */         }
/* 3388:     */         else
/* 3389:     */         {
/* 3390:3462 */           localObject8 = localLocalFrame.get(0);
/* 3391:3463 */           localTypePmtTrnRqV1 = (TypePmtTrnRqV1)ObjectVal.clone(localObject8);
/* 3392:     */         }
/* 3393:3466 */         if (!bool1)
/* 3394:     */         {
/* 3395:3468 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3396:     */         }
/* 3397:     */         else
/* 3398:     */         {
/* 3399:3472 */           localObject12 = localLocalFrame.get(1);
/* 3400:3473 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3401:     */         }
/* 3402:3475 */         localObject12 = 
/* 3403:3476 */           localOFX200BPWServicesBean.processPmtTrnRqV1(
/* 3404:3477 */           localTypePmtTrnRqV1, 
/* 3405:3478 */           (TypeUserData)localObject8);
/* 3406:     */         
/* 3407:3480 */         localLocalFrame.setResult(localObject12);
/* 3408:     */       }
/* 3409:     */       catch (Throwable localThrowable43)
/* 3410:     */       {
/* 3411:3484 */         localThrowable43.printStackTrace(Jaguar.log);
/* 3412:3485 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable43, true);
/* 3413:3486 */         return localThrowable43.getClass().getName();
/* 3414:     */       }
/* 3415:     */     case 43: 
/* 3416:     */       try
/* 3417:     */       {
/* 3418:     */         TypeRecIntraSyncRqV1 localTypeRecIntraSyncRqV1;
/* 3419:3495 */         if (!bool1)
/* 3420:     */         {
/* 3421:3497 */           localTypeRecIntraSyncRqV1 = (TypeRecIntraSyncRqV1)localLocalFrame.get(0);
/* 3422:     */         }
/* 3423:     */         else
/* 3424:     */         {
/* 3425:3501 */           localObject8 = localLocalFrame.get(0);
/* 3426:3502 */           localTypeRecIntraSyncRqV1 = (TypeRecIntraSyncRqV1)ObjectVal.clone(localObject8);
/* 3427:     */         }
/* 3428:3505 */         if (!bool1)
/* 3429:     */         {
/* 3430:3507 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3431:     */         }
/* 3432:     */         else
/* 3433:     */         {
/* 3434:3511 */           localObject12 = localLocalFrame.get(1);
/* 3435:3512 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3436:     */         }
/* 3437:3514 */         localObject12 = 
/* 3438:3515 */           localOFX200BPWServicesBean.processRecIntraSyncRqV1(
/* 3439:3516 */           localTypeRecIntraSyncRqV1, 
/* 3440:3517 */           (TypeUserData)localObject8);
/* 3441:     */         
/* 3442:3519 */         localLocalFrame.setResult(localObject12);
/* 3443:     */       }
/* 3444:     */       catch (Throwable localThrowable44)
/* 3445:     */       {
/* 3446:3523 */         localThrowable44.printStackTrace(Jaguar.log);
/* 3447:3524 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable44, true);
/* 3448:3525 */         return localThrowable44.getClass().getName();
/* 3449:     */       }
/* 3450:     */     case 44: 
/* 3451:     */       try
/* 3452:     */       {
/* 3453:     */         TypeRecIntraTrnRqV1 localTypeRecIntraTrnRqV1;
/* 3454:3534 */         if (!bool1)
/* 3455:     */         {
/* 3456:3536 */           localTypeRecIntraTrnRqV1 = (TypeRecIntraTrnRqV1)localLocalFrame.get(0);
/* 3457:     */         }
/* 3458:     */         else
/* 3459:     */         {
/* 3460:3540 */           localObject8 = localLocalFrame.get(0);
/* 3461:3541 */           localTypeRecIntraTrnRqV1 = (TypeRecIntraTrnRqV1)ObjectVal.clone(localObject8);
/* 3462:     */         }
/* 3463:3544 */         if (!bool1)
/* 3464:     */         {
/* 3465:3546 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3466:     */         }
/* 3467:     */         else
/* 3468:     */         {
/* 3469:3550 */           localObject12 = localLocalFrame.get(1);
/* 3470:3551 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3471:     */         }
/* 3472:3553 */         localObject12 = 
/* 3473:3554 */           localOFX200BPWServicesBean.processRecIntraTrnRqV1(
/* 3474:3555 */           localTypeRecIntraTrnRqV1, 
/* 3475:3556 */           (TypeUserData)localObject8);
/* 3476:     */         
/* 3477:3558 */         localLocalFrame.setResult(localObject12);
/* 3478:     */       }
/* 3479:     */       catch (Throwable localThrowable45)
/* 3480:     */       {
/* 3481:3562 */         localThrowable45.printStackTrace(Jaguar.log);
/* 3482:3563 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable45, true);
/* 3483:3564 */         return localThrowable45.getClass().getName();
/* 3484:     */       }
/* 3485:     */     case 45: 
/* 3486:     */       try
/* 3487:     */       {
/* 3488:     */         TypeRecPmtSyncRqV1 localTypeRecPmtSyncRqV1;
/* 3489:3573 */         if (!bool1)
/* 3490:     */         {
/* 3491:3575 */           localTypeRecPmtSyncRqV1 = (TypeRecPmtSyncRqV1)localLocalFrame.get(0);
/* 3492:     */         }
/* 3493:     */         else
/* 3494:     */         {
/* 3495:3579 */           localObject8 = localLocalFrame.get(0);
/* 3496:3580 */           localTypeRecPmtSyncRqV1 = (TypeRecPmtSyncRqV1)ObjectVal.clone(localObject8);
/* 3497:     */         }
/* 3498:3583 */         if (!bool1)
/* 3499:     */         {
/* 3500:3585 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3501:     */         }
/* 3502:     */         else
/* 3503:     */         {
/* 3504:3589 */           localObject12 = localLocalFrame.get(1);
/* 3505:3590 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3506:     */         }
/* 3507:3592 */         localObject12 = 
/* 3508:3593 */           localOFX200BPWServicesBean.processRecPmtSyncRqV1(
/* 3509:3594 */           localTypeRecPmtSyncRqV1, 
/* 3510:3595 */           (TypeUserData)localObject8);
/* 3511:     */         
/* 3512:3597 */         localLocalFrame.setResult(localObject12);
/* 3513:     */       }
/* 3514:     */       catch (Throwable localThrowable46)
/* 3515:     */       {
/* 3516:3601 */         localThrowable46.printStackTrace(Jaguar.log);
/* 3517:3602 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable46, true);
/* 3518:3603 */         return localThrowable46.getClass().getName();
/* 3519:     */       }
/* 3520:     */     case 46: 
/* 3521:     */       try
/* 3522:     */       {
/* 3523:     */         TypeRecPmtTrnRqV1 localTypeRecPmtTrnRqV1;
/* 3524:3612 */         if (!bool1)
/* 3525:     */         {
/* 3526:3614 */           localTypeRecPmtTrnRqV1 = (TypeRecPmtTrnRqV1)localLocalFrame.get(0);
/* 3527:     */         }
/* 3528:     */         else
/* 3529:     */         {
/* 3530:3618 */           localObject8 = localLocalFrame.get(0);
/* 3531:3619 */           localTypeRecPmtTrnRqV1 = (TypeRecPmtTrnRqV1)ObjectVal.clone(localObject8);
/* 3532:     */         }
/* 3533:3622 */         if (!bool1)
/* 3534:     */         {
/* 3535:3624 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3536:     */         }
/* 3537:     */         else
/* 3538:     */         {
/* 3539:3628 */           localObject12 = localLocalFrame.get(1);
/* 3540:3629 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3541:     */         }
/* 3542:3631 */         localObject12 = 
/* 3543:3632 */           localOFX200BPWServicesBean.processRecPmtTrnRqV1(
/* 3544:3633 */           localTypeRecPmtTrnRqV1, 
/* 3545:3634 */           (TypeUserData)localObject8);
/* 3546:     */         
/* 3547:3636 */         localLocalFrame.setResult(localObject12);
/* 3548:     */       }
/* 3549:     */       catch (Throwable localThrowable47)
/* 3550:     */       {
/* 3551:3640 */         localThrowable47.printStackTrace(Jaguar.log);
/* 3552:3641 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable47, true);
/* 3553:3642 */         return localThrowable47.getClass().getName();
/* 3554:     */       }
/* 3555:     */     case 47: 
/* 3556:     */       try
/* 3557:     */       {
/* 3558:3651 */         String str15 = (String)localLocalFrame.get(0);
/* 3559:     */         
/* 3560:3653 */         int i4 = ((Integer)localLocalFrame.get(1)).intValue();
/* 3561:3654 */         localObject12 = localOFX200BPWServicesBean
/* 3562:3655 */           .getPayeeNames(
/* 3563:3656 */           str15, 
/* 3564:3657 */           i4);
/* 3565:     */         
/* 3566:3659 */         localLocalFrame.setResult(localObject12);
/* 3567:     */       }
/* 3568:     */       catch (Throwable localThrowable48)
/* 3569:     */       {
/* 3570:3663 */         localThrowable48.printStackTrace(Jaguar.log);
/* 3571:3664 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable48, true);
/* 3572:3665 */         return localThrowable48.getClass().getName();
/* 3573:     */       }
/* 3574:     */     case 48: 
/* 3575:     */       try
/* 3576:     */       {
/* 3577:3674 */         String str16 = (String)localLocalFrame.get(0);
/* 3578:3675 */         arrayOfString6 = localOFX200BPWServicesBean
/* 3579:3676 */           .getPayeeNames(
/* 3580:3677 */           str16);
/* 3581:     */         
/* 3582:3679 */         localLocalFrame.setResult(arrayOfString6);
/* 3583:     */       }
/* 3584:     */       catch (Throwable localThrowable49)
/* 3585:     */       {
/* 3586:3683 */         localThrowable49.printStackTrace(Jaguar.log);
/* 3587:3684 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable49, true);
/* 3588:3685 */         return localThrowable49.getClass().getName();
/* 3589:     */       }
/* 3590:     */     case 49: 
/* 3591:     */       try
/* 3592:     */       {
/* 3593:3694 */         String str17 = (String)localLocalFrame.get(0);
/* 3594:3695 */         arrayOfString6 = localOFX200BPWServicesBean
/* 3595:3696 */           .getPayeeIDs(
/* 3596:3697 */           str17);
/* 3597:     */         
/* 3598:3699 */         localLocalFrame.setResult(arrayOfString6);
/* 3599:     */       }
/* 3600:     */       catch (Throwable localThrowable50)
/* 3601:     */       {
/* 3602:3703 */         localThrowable50.printStackTrace(Jaguar.log);
/* 3603:3704 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable50, true);
/* 3604:3705 */         return localThrowable50.getClass().getName();
/* 3605:     */       }
/* 3606:     */     case 50: 
/* 3607:     */       try
/* 3608:     */       {
/* 3609:3714 */         String str18 = (String)localLocalFrame.get(0);
/* 3610:     */         
/* 3611:3716 */         int i5 = ((Integer)localLocalFrame.get(1)).intValue();
/* 3612:3717 */         localObject12 = localOFX200BPWServicesBean
/* 3613:3718 */           .getPayees(
/* 3614:3719 */           str18, 
/* 3615:3720 */           i5);
/* 3616:     */         
/* 3617:3722 */         localLocalFrame.setResult(localObject12);
/* 3618:     */       }
/* 3619:     */       catch (Throwable localThrowable51)
/* 3620:     */       {
/* 3621:3726 */         localThrowable51.printStackTrace(Jaguar.log);
/* 3622:3727 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable51, true);
/* 3623:3728 */         return localThrowable51.getClass().getName();
/* 3624:     */       }
/* 3625:     */     default: 
/* 3626:3734 */       return 
/* 3627:3735 */         localInvoke1(
/* 3628:3736 */         local_ServerRequest, 
/* 3629:3737 */         paramInputStream, 
/* 3630:3738 */         paramOutputStream, 
/* 3631:3739 */         localOFX200BPWServicesBean, 
/* 3632:3740 */         localLocalFrame, 
/* 3633:3741 */         localInteger, 
/* 3634:3742 */         bool1);
/* 3635:     */     }
/* 3636:3746 */     return null;
/* 3637:     */   }
/* 3638:     */   
/* 3639:     */   private static String localInvoke1(_ServerRequest param_ServerRequest, InputStream paramInputStream, OutputStream paramOutputStream, OFX200BPWServicesBean paramOFX200BPWServicesBean, LocalFrame paramLocalFrame, Integer paramInteger, boolean paramBoolean)
/* 3640:     */   {
/* 3641:     */     Object localObject1;
/* 3642:     */     Object localObject12;
/* 3643:     */     Object localObject2;
/* 3644:     */     int k;
/* 3645:     */     Object localObject6;
/* 3646:     */     Object localObject8;
/* 3647:     */     Object localObject10;
/* 3648:     */     boolean bool2;
/* 3649:     */     Object localObject13;
/* 3650:     */     Object localObject11;
/* 3651:3758 */     switch (paramInteger.intValue())
/* 3652:     */     {
/* 3653:     */     case 51: 
/* 3654:     */       try
/* 3655:     */       {
/* 3656:3765 */         String str1 = (String)paramLocalFrame.get(0);
/* 3657:3766 */         localObject1 = paramOFX200BPWServicesBean
/* 3658:3767 */           .getPayees(
/* 3659:3768 */           str1);
/* 3660:     */         
/* 3661:3770 */         paramLocalFrame.setResult(localObject1);
/* 3662:     */       }
/* 3663:     */       catch (Throwable localThrowable1)
/* 3664:     */       {
/* 3665:3774 */         localThrowable1.printStackTrace(Jaguar.log);
/* 3666:3775 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/* 3667:3776 */         return localThrowable1.getClass().getName();
/* 3668:     */       }
/* 3669:     */     case 52: 
/* 3670:     */       try
/* 3671:     */       {
/* 3672:3785 */         String str2 = (String)paramLocalFrame.get(0);
/* 3673:3786 */         localObject1 = paramOFX200BPWServicesBean
/* 3674:3787 */           .searchGlobalPayees(
/* 3675:3788 */           str2);
/* 3676:     */         
/* 3677:3790 */         paramLocalFrame.setResult(localObject1);
/* 3678:     */       }
/* 3679:     */       catch (Throwable localThrowable2)
/* 3680:     */       {
/* 3681:3794 */         localThrowable2.printStackTrace(Jaguar.log);
/* 3682:3795 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/* 3683:3796 */         return localThrowable2.getClass().getName();
/* 3684:     */       }
/* 3685:     */     case 53: 
/* 3686:     */       try
/* 3687:     */       {
/* 3688:     */         PayeeInfo localPayeeInfo1;
/* 3689:3805 */         if (!paramBoolean)
/* 3690:     */         {
/* 3691:3807 */           localPayeeInfo1 = (PayeeInfo)paramLocalFrame.get(0);
/* 3692:     */         }
/* 3693:     */         else
/* 3694:     */         {
/* 3695:3811 */           localObject1 = paramLocalFrame.get(0);
/* 3696:3812 */           localPayeeInfo1 = (PayeeInfo)ObjectVal.clone(localObject1);
/* 3697:     */         }
/* 3698:3815 */         int j = ((Integer)paramLocalFrame.get(1)).intValue();
/* 3699:3816 */         localObject12 = paramOFX200BPWServicesBean
/* 3700:3817 */           .updatePayee(
/* 3701:3818 */           localPayeeInfo1, 
/* 3702:3819 */           j);
/* 3703:     */         
/* 3704:3821 */         paramLocalFrame.setResult(localObject12);
/* 3705:     */       }
/* 3706:     */       catch (Throwable localThrowable3)
/* 3707:     */       {
/* 3708:3825 */         localThrowable3.printStackTrace(Jaguar.log);
/* 3709:3826 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/* 3710:3827 */         return localThrowable3.getClass().getName();
/* 3711:     */       }
/* 3712:     */     case 54: 
/* 3713:     */       try
/* 3714:     */       {
/* 3715:     */         PayeeInfo localPayeeInfo2;
/* 3716:3836 */         if (!paramBoolean)
/* 3717:     */         {
/* 3718:3838 */           localPayeeInfo2 = (PayeeInfo)paramLocalFrame.get(0);
/* 3719:     */         }
/* 3720:     */         else
/* 3721:     */         {
/* 3722:3842 */           localObject2 = paramLocalFrame.get(0);
/* 3723:3843 */           localPayeeInfo2 = (PayeeInfo)ObjectVal.clone(localObject2);
/* 3724:     */         }
/* 3725:3846 */         if (!paramBoolean)
/* 3726:     */         {
/* 3727:3848 */           localObject2 = (PayeeRouteInfo)paramLocalFrame.get(1);
/* 3728:     */         }
/* 3729:     */         else
/* 3730:     */         {
/* 3731:3852 */           localObject12 = paramLocalFrame.get(1);
/* 3732:3853 */           localObject2 = (PayeeRouteInfo)ObjectVal.clone(localObject12);
/* 3733:     */         }
/* 3734:3856 */         paramOFX200BPWServicesBean.updatePayee(
/* 3735:3857 */           localPayeeInfo2, 
/* 3736:3858 */           (PayeeRouteInfo)localObject2);
/* 3737:     */       }
/* 3738:     */       catch (Throwable localThrowable4)
/* 3739:     */       {
/* 3740:3863 */         localThrowable4.printStackTrace(Jaguar.log);
/* 3741:3864 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/* 3742:3865 */         return localThrowable4.getClass().getName();
/* 3743:     */       }
/* 3744:     */     case 55: 
/* 3745:     */       try
/* 3746:     */       {
/* 3747:3874 */         String str3 = (String)paramLocalFrame.get(0);
/* 3748:3875 */         paramOFX200BPWServicesBean
/* 3749:3876 */           .deletePayee(
/* 3750:3877 */           str3);
/* 3751:     */       }
/* 3752:     */       catch (Throwable localThrowable5)
/* 3753:     */       {
/* 3754:3882 */         localThrowable5.printStackTrace(Jaguar.log);
/* 3755:3883 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/* 3756:3884 */         return localThrowable5.getClass().getName();
/* 3757:     */       }
/* 3758:     */     case 56: 
/* 3759:     */       try
/* 3760:     */       {
/* 3761:     */         String[] arrayOfString1;
/* 3762:3893 */         if (!paramBoolean)
/* 3763:     */         {
/* 3764:3895 */           arrayOfString1 = (String[])paramLocalFrame.get(0);
/* 3765:     */         }
/* 3766:     */         else
/* 3767:     */         {
/* 3768:3899 */           localObject2 = paramLocalFrame.get(0);
/* 3769:3900 */           arrayOfString1 = (String[])ObjectVal.clone(localObject2);
/* 3770:     */         }
/* 3771:3903 */         paramOFX200BPWServicesBean.deletePayees(
/* 3772:3904 */           arrayOfString1);
/* 3773:     */       }
/* 3774:     */       catch (Throwable localThrowable6)
/* 3775:     */       {
/* 3776:3909 */         localThrowable6.printStackTrace(Jaguar.log);
/* 3777:3910 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/* 3778:3911 */         return localThrowable6.getClass().getName();
/* 3779:     */       }
/* 3780:     */     case 57: 
/* 3781:     */       try
/* 3782:     */       {
/* 3783:3920 */         String str4 = (String)paramLocalFrame.get(0);
/* 3784:3921 */         localObject2 = paramOFX200BPWServicesBean
/* 3785:3922 */           .findPayeeByID(
/* 3786:3923 */           str4);
/* 3787:     */         
/* 3788:3925 */         paramLocalFrame.setResult(localObject2);
/* 3789:     */       }
/* 3790:     */       catch (Throwable localThrowable7)
/* 3791:     */       {
/* 3792:3929 */         localThrowable7.printStackTrace(Jaguar.log);
/* 3793:3930 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/* 3794:3931 */         return localThrowable7.getClass().getName();
/* 3795:     */       }
/* 3796:     */     case 58: 
/* 3797:     */       try
/* 3798:     */       {
/* 3799:3940 */         String str5 = (String)paramLocalFrame.get(0);
/* 3800:     */         
/* 3801:3942 */         localObject2 = (String)paramLocalFrame.get(1);
/* 3802:3943 */         paramOFX200BPWServicesBean
/* 3803:3944 */           .setPayeeStatus(
/* 3804:3945 */           str5, 
/* 3805:3946 */           (String)localObject2);
/* 3806:     */       }
/* 3807:     */       catch (Throwable localThrowable8)
/* 3808:     */       {
/* 3809:3951 */         localThrowable8.printStackTrace(Jaguar.log);
/* 3810:3952 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/* 3811:3953 */         return localThrowable8.getClass().getName();
/* 3812:     */       }
/* 3813:     */     case 59: 
/* 3814:     */       try
/* 3815:     */       {
/* 3816:3962 */         int i = ((Integer)paramLocalFrame.get(0)).intValue();
/* 3817:3963 */         k = paramOFX200BPWServicesBean
/* 3818:3964 */           .getSmartDate(
/* 3819:3965 */           i);
/* 3820:     */         
/* 3821:3967 */         paramLocalFrame.setResult(k);
/* 3822:     */       }
/* 3823:     */       catch (Throwable localThrowable9)
/* 3824:     */       {
/* 3825:3971 */         localThrowable9.printStackTrace(Jaguar.log);
/* 3826:3972 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/* 3827:3973 */         return localThrowable9.getClass().getName();
/* 3828:     */       }
/* 3829:     */     case 60: 
/* 3830:     */       try
/* 3831:     */       {
/* 3832:3982 */         String str6 = (String)paramLocalFrame.get(0);
/* 3833:     */         
/* 3834:3984 */         k = ((Integer)paramLocalFrame.get(1)).intValue();
/* 3835:3985 */         localObject12 = paramOFX200BPWServicesBean
/* 3836:3986 */           .getGlobalPayees(
/* 3837:3987 */           str6, 
/* 3838:3988 */           k);
/* 3839:     */         
/* 3840:3990 */         paramLocalFrame.setResult(localObject12);
/* 3841:     */       }
/* 3842:     */       catch (Throwable localThrowable10)
/* 3843:     */       {
/* 3844:3994 */         localThrowable10.printStackTrace(Jaguar.log);
/* 3845:3995 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/* 3846:3996 */         return localThrowable10.getClass().getName();
/* 3847:     */       }
/* 3848:     */     case 61: 
/* 3849:     */       try
/* 3850:     */       {
/* 3851:     */         PayeeInfo localPayeeInfo3;
/* 3852:4005 */         if (!paramBoolean)
/* 3853:     */         {
/* 3854:4007 */           localPayeeInfo3 = (PayeeInfo)paramLocalFrame.get(0);
/* 3855:     */         }
/* 3856:     */         else
/* 3857:     */         {
/* 3858:4011 */           Object localObject3 = paramLocalFrame.get(0);
/* 3859:4012 */           localPayeeInfo3 = (PayeeInfo)ObjectVal.clone(localObject3);
/* 3860:     */         }
/* 3861:4015 */         int m = ((Integer)paramLocalFrame.get(1)).intValue();
/* 3862:4016 */         localObject12 = paramOFX200BPWServicesBean
/* 3863:4017 */           .addPayee(
/* 3864:4018 */           localPayeeInfo3, 
/* 3865:4019 */           m);
/* 3866:     */         
/* 3867:4021 */         paramLocalFrame.setResult(localObject12);
/* 3868:     */       }
/* 3869:     */       catch (Throwable localThrowable11)
/* 3870:     */       {
/* 3871:4025 */         localThrowable11.printStackTrace(Jaguar.log);
/* 3872:4026 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/* 3873:4027 */         return localThrowable11.getClass().getName();
/* 3874:     */       }
/* 3875:     */     case 62: 
/* 3876:     */       try
/* 3877:     */       {
/* 3878:     */         ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo1;
/* 3879:4036 */         if (!paramBoolean)
/* 3880:     */         {
/* 3881:4038 */           arrayOfConsumerCrossRefInfo1 = (ConsumerCrossRefInfo[])paramLocalFrame.get(0);
/* 3882:     */         }
/* 3883:     */         else
/* 3884:     */         {
/* 3885:4042 */           Object localObject4 = paramLocalFrame.get(0);
/* 3886:4043 */           arrayOfConsumerCrossRefInfo1 = (ConsumerCrossRefInfo[])ObjectVal.clone(localObject4);
/* 3887:     */         }
/* 3888:4045 */         int n = paramOFX200BPWServicesBean
/* 3889:4046 */           .addConsumerCrossRef(
/* 3890:4047 */           arrayOfConsumerCrossRefInfo1);
/* 3891:     */         
/* 3892:4049 */         paramLocalFrame.setResult(n);
/* 3893:     */       }
/* 3894:     */       catch (Throwable localThrowable12)
/* 3895:     */       {
/* 3896:4053 */         localThrowable12.printStackTrace(Jaguar.log);
/* 3897:4054 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/* 3898:4055 */         return localThrowable12.getClass().getName();
/* 3899:     */       }
/* 3900:     */     case 63: 
/* 3901:     */       try
/* 3902:     */       {
/* 3903:     */         ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo2;
/* 3904:4064 */         if (!paramBoolean)
/* 3905:     */         {
/* 3906:4066 */           arrayOfConsumerCrossRefInfo2 = (ConsumerCrossRefInfo[])paramLocalFrame.get(0);
/* 3907:     */         }
/* 3908:     */         else
/* 3909:     */         {
/* 3910:4070 */           Object localObject5 = paramLocalFrame.get(0);
/* 3911:4071 */           arrayOfConsumerCrossRefInfo2 = (ConsumerCrossRefInfo[])ObjectVal.clone(localObject5);
/* 3912:     */         }
/* 3913:4073 */         int i1 = paramOFX200BPWServicesBean
/* 3914:4074 */           .deleteConsumerCrossRef(
/* 3915:4075 */           arrayOfConsumerCrossRefInfo2);
/* 3916:     */         
/* 3917:4077 */         paramLocalFrame.setResult(i1);
/* 3918:     */       }
/* 3919:     */       catch (Throwable localThrowable13)
/* 3920:     */       {
/* 3921:4081 */         localThrowable13.printStackTrace(Jaguar.log);
/* 3922:4082 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/* 3923:4083 */         return localThrowable13.getClass().getName();
/* 3924:     */       }
/* 3925:     */     case 64: 
/* 3926:     */       try
/* 3927:     */       {
/* 3928:     */         String[] arrayOfString2;
/* 3929:4092 */         if (!paramBoolean)
/* 3930:     */         {
/* 3931:4094 */           arrayOfString2 = (String[])paramLocalFrame.get(0);
/* 3932:     */         }
/* 3933:     */         else
/* 3934:     */         {
/* 3935:4098 */           localObject6 = paramLocalFrame.get(0);
/* 3936:4099 */           arrayOfString2 = (String[])ObjectVal.clone(localObject6);
/* 3937:     */         }
/* 3938:4101 */         localObject6 = 
/* 3939:4102 */           paramOFX200BPWServicesBean.getConsumerCrossRef(
/* 3940:4103 */           arrayOfString2);
/* 3941:     */         
/* 3942:4105 */         paramLocalFrame.setResult(localObject6);
/* 3943:     */       }
/* 3944:     */       catch (Throwable localThrowable14)
/* 3945:     */       {
/* 3946:4109 */         localThrowable14.printStackTrace(Jaguar.log);
/* 3947:4110 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/* 3948:4111 */         return localThrowable14.getClass().getName();
/* 3949:     */       }
/* 3950:     */     case 65: 
/* 3951:     */       try
/* 3952:     */       {
/* 3953:     */         CustomerBankInfo[] arrayOfCustomerBankInfo1;
/* 3954:4120 */         if (!paramBoolean)
/* 3955:     */         {
/* 3956:4122 */           arrayOfCustomerBankInfo1 = (CustomerBankInfo[])paramLocalFrame.get(0);
/* 3957:     */         }
/* 3958:     */         else
/* 3959:     */         {
/* 3960:4126 */           localObject6 = paramLocalFrame.get(0);
/* 3961:4127 */           arrayOfCustomerBankInfo1 = (CustomerBankInfo[])ObjectVal.clone(localObject6);
/* 3962:     */         }
/* 3963:4129 */         int i2 = paramOFX200BPWServicesBean
/* 3964:4130 */           .addCustomerBankInfo(
/* 3965:4131 */           arrayOfCustomerBankInfo1);
/* 3966:     */         
/* 3967:4133 */         paramLocalFrame.setResult(i2);
/* 3968:     */       }
/* 3969:     */       catch (Throwable localThrowable15)
/* 3970:     */       {
/* 3971:4137 */         localThrowable15.printStackTrace(Jaguar.log);
/* 3972:4138 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/* 3973:4139 */         return localThrowable15.getClass().getName();
/* 3974:     */       }
/* 3975:     */     case 66: 
/* 3976:     */       try
/* 3977:     */       {
/* 3978:     */         CustomerBankInfo[] arrayOfCustomerBankInfo2;
/* 3979:4148 */         if (!paramBoolean)
/* 3980:     */         {
/* 3981:4150 */           arrayOfCustomerBankInfo2 = (CustomerBankInfo[])paramLocalFrame.get(0);
/* 3982:     */         }
/* 3983:     */         else
/* 3984:     */         {
/* 3985:4154 */           Object localObject7 = paramLocalFrame.get(0);
/* 3986:4155 */           arrayOfCustomerBankInfo2 = (CustomerBankInfo[])ObjectVal.clone(localObject7);
/* 3987:     */         }
/* 3988:4157 */         int i3 = paramOFX200BPWServicesBean
/* 3989:4158 */           .deleteCustomerBankInfo(
/* 3990:4159 */           arrayOfCustomerBankInfo2);
/* 3991:     */         
/* 3992:4161 */         paramLocalFrame.setResult(i3);
/* 3993:     */       }
/* 3994:     */       catch (Throwable localThrowable16)
/* 3995:     */       {
/* 3996:4165 */         localThrowable16.printStackTrace(Jaguar.log);
/* 3997:4166 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/* 3998:4167 */         return localThrowable16.getClass().getName();
/* 3999:     */       }
/* 4000:     */     case 67: 
/* 4001:     */       try
/* 4002:     */       {
/* 4003:     */         String[] arrayOfString3;
/* 4004:4176 */         if (!paramBoolean)
/* 4005:     */         {
/* 4006:4178 */           arrayOfString3 = (String[])paramLocalFrame.get(0);
/* 4007:     */         }
/* 4008:     */         else
/* 4009:     */         {
/* 4010:4182 */           localObject8 = paramLocalFrame.get(0);
/* 4011:4183 */           arrayOfString3 = (String[])ObjectVal.clone(localObject8);
/* 4012:     */         }
/* 4013:4185 */         localObject8 = 
/* 4014:4186 */           paramOFX200BPWServicesBean.getCustomerBankInfo(
/* 4015:4187 */           arrayOfString3);
/* 4016:     */         
/* 4017:4189 */         paramLocalFrame.setResult(localObject8);
/* 4018:     */       }
/* 4019:     */       catch (Throwable localThrowable17)
/* 4020:     */       {
/* 4021:4193 */         localThrowable17.printStackTrace(Jaguar.log);
/* 4022:4194 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/* 4023:4195 */         return localThrowable17.getClass().getName();
/* 4024:     */       }
/* 4025:     */     case 68: 
/* 4026:     */       try
/* 4027:     */       {
/* 4028:     */         CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo1;
/* 4029:4204 */         if (!paramBoolean)
/* 4030:     */         {
/* 4031:4206 */           arrayOfCustomerProductAccessInfo1 = (CustomerProductAccessInfo[])paramLocalFrame.get(0);
/* 4032:     */         }
/* 4033:     */         else
/* 4034:     */         {
/* 4035:4210 */           localObject8 = paramLocalFrame.get(0);
/* 4036:4211 */           arrayOfCustomerProductAccessInfo1 = (CustomerProductAccessInfo[])ObjectVal.clone(localObject8);
/* 4037:     */         }
/* 4038:4213 */         int i4 = paramOFX200BPWServicesBean
/* 4039:4214 */           .addCustomerProductAccessInfo(
/* 4040:4215 */           arrayOfCustomerProductAccessInfo1);
/* 4041:     */         
/* 4042:4217 */         paramLocalFrame.setResult(i4);
/* 4043:     */       }
/* 4044:     */       catch (Throwable localThrowable18)
/* 4045:     */       {
/* 4046:4221 */         localThrowable18.printStackTrace(Jaguar.log);
/* 4047:4222 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/* 4048:4223 */         return localThrowable18.getClass().getName();
/* 4049:     */       }
/* 4050:     */     case 69: 
/* 4051:     */       try
/* 4052:     */       {
/* 4053:     */         CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo2;
/* 4054:4232 */         if (!paramBoolean)
/* 4055:     */         {
/* 4056:4234 */           arrayOfCustomerProductAccessInfo2 = (CustomerProductAccessInfo[])paramLocalFrame.get(0);
/* 4057:     */         }
/* 4058:     */         else
/* 4059:     */         {
/* 4060:4238 */           Object localObject9 = paramLocalFrame.get(0);
/* 4061:4239 */           arrayOfCustomerProductAccessInfo2 = (CustomerProductAccessInfo[])ObjectVal.clone(localObject9);
/* 4062:     */         }
/* 4063:4241 */         int i5 = paramOFX200BPWServicesBean
/* 4064:4242 */           .deleteCustomerProductAccessInfo(
/* 4065:4243 */           arrayOfCustomerProductAccessInfo2);
/* 4066:     */         
/* 4067:4245 */         paramLocalFrame.setResult(i5);
/* 4068:     */       }
/* 4069:     */       catch (Throwable localThrowable19)
/* 4070:     */       {
/* 4071:4249 */         localThrowable19.printStackTrace(Jaguar.log);
/* 4072:4250 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/* 4073:4251 */         return localThrowable19.getClass().getName();
/* 4074:     */       }
/* 4075:     */     case 70: 
/* 4076:     */       try
/* 4077:     */       {
/* 4078:     */         String[] arrayOfString4;
/* 4079:4260 */         if (!paramBoolean)
/* 4080:     */         {
/* 4081:4262 */           arrayOfString4 = (String[])paramLocalFrame.get(0);
/* 4082:     */         }
/* 4083:     */         else
/* 4084:     */         {
/* 4085:4266 */           localObject10 = paramLocalFrame.get(0);
/* 4086:4267 */           arrayOfString4 = (String[])ObjectVal.clone(localObject10);
/* 4087:     */         }
/* 4088:4269 */         localObject10 = 
/* 4089:4270 */           paramOFX200BPWServicesBean.getCustomerProductAccessInfo(
/* 4090:4271 */           arrayOfString4);
/* 4091:     */         
/* 4092:4273 */         paramLocalFrame.setResult(localObject10);
/* 4093:     */       }
/* 4094:     */       catch (Throwable localThrowable20)
/* 4095:     */       {
/* 4096:4277 */         localThrowable20.printStackTrace(Jaguar.log);
/* 4097:4278 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/* 4098:4279 */         return localThrowable20.getClass().getName();
/* 4099:     */       }
/* 4100:     */     case 71: 
/* 4101:     */       try
/* 4102:     */       {
/* 4103:4288 */         String str7 = (String)paramLocalFrame.get(0);
/* 4104:     */         
/* 4105:4290 */         localObject10 = (String)paramLocalFrame.get(1);
/* 4106:4291 */         bool2 = paramOFX200BPWServicesBean
/* 4107:4292 */           .validateMetavanteCustAcctByConsumerID(
/* 4108:4293 */           str7, 
/* 4109:4294 */           (String)localObject10);
/* 4110:     */         
/* 4111:4296 */         paramLocalFrame.setResult(bool2);
/* 4112:     */       }
/* 4113:     */       catch (Throwable localThrowable21)
/* 4114:     */       {
/* 4115:4300 */         localThrowable21.printStackTrace(Jaguar.log);
/* 4116:4301 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/* 4117:4302 */         return localThrowable21.getClass().getName();
/* 4118:     */       }
/* 4119:     */     case 72: 
/* 4120:     */       try
/* 4121:     */       {
/* 4122:4311 */         String str8 = (String)paramLocalFrame.get(0);
/* 4123:     */         
/* 4124:4313 */         localObject10 = (String)paramLocalFrame.get(1);
/* 4125:4314 */         bool2 = paramOFX200BPWServicesBean
/* 4126:4315 */           .validateMetavanteCustAcctByCustomerID(
/* 4127:4316 */           str8, 
/* 4128:4317 */           (String)localObject10);
/* 4129:     */         
/* 4130:4319 */         paramLocalFrame.setResult(bool2);
/* 4131:     */       }
/* 4132:     */       catch (Throwable localThrowable22)
/* 4133:     */       {
/* 4134:4323 */         localThrowable22.printStackTrace(Jaguar.log);
/* 4135:4324 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/* 4136:4325 */         return localThrowable22.getClass().getName();
/* 4137:     */       }
/* 4138:     */     case 73: 
/* 4139:     */       try
/* 4140:     */       {
/* 4141:     */         BPWHist localBPWHist1;
/* 4142:4334 */         if (!paramBoolean)
/* 4143:     */         {
/* 4144:4336 */           localBPWHist1 = (BPWHist)paramLocalFrame.get(0);
/* 4145:     */         }
/* 4146:     */         else
/* 4147:     */         {
/* 4148:4340 */           localObject10 = paramLocalFrame.get(0);
/* 4149:4341 */           localBPWHist1 = (BPWHist)ObjectVal.clone(localObject10);
/* 4150:     */         }
/* 4151:4343 */         localObject10 = 
/* 4152:4344 */           paramOFX200BPWServicesBean.getPmtHistory(
/* 4153:4345 */           localBPWHist1);
/* 4154:     */         
/* 4155:4347 */         paramLocalFrame.setResult(localObject10);
/* 4156:     */       }
/* 4157:     */       catch (Throwable localThrowable23)
/* 4158:     */       {
/* 4159:4351 */         localThrowable23.printStackTrace(Jaguar.log);
/* 4160:4352 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/* 4161:4353 */         return localThrowable23.getClass().getName();
/* 4162:     */       }
/* 4163:     */     case 74: 
/* 4164:     */       try
/* 4165:     */       {
/* 4166:     */         BPWHist localBPWHist2;
/* 4167:4362 */         if (!paramBoolean)
/* 4168:     */         {
/* 4169:4364 */           localBPWHist2 = (BPWHist)paramLocalFrame.get(0);
/* 4170:     */         }
/* 4171:     */         else
/* 4172:     */         {
/* 4173:4368 */           localObject10 = paramLocalFrame.get(0);
/* 4174:4369 */           localBPWHist2 = (BPWHist)ObjectVal.clone(localObject10);
/* 4175:     */         }
/* 4176:4371 */         localObject10 = 
/* 4177:4372 */           paramOFX200BPWServicesBean.getIntraHistory(
/* 4178:4373 */           localBPWHist2);
/* 4179:     */         
/* 4180:4375 */         paramLocalFrame.setResult(localObject10);
/* 4181:     */       }
/* 4182:     */       catch (Throwable localThrowable24)
/* 4183:     */       {
/* 4184:4379 */         localThrowable24.printStackTrace(Jaguar.log);
/* 4185:4380 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/* 4186:4381 */         return localThrowable24.getClass().getName();
/* 4187:     */       }
/* 4188:     */     case 75: 
/* 4189:     */       try
/* 4190:     */       {
/* 4191:4390 */         String str9 = (String)paramLocalFrame.get(0);
/* 4192:4391 */         localObject10 = paramOFX200BPWServicesBean
/* 4193:4392 */           .getIntraById(
/* 4194:4393 */           str9);
/* 4195:     */         
/* 4196:4395 */         paramLocalFrame.setResult(localObject10);
/* 4197:     */       }
/* 4198:     */       catch (Throwable localThrowable25)
/* 4199:     */       {
/* 4200:4399 */         localThrowable25.printStackTrace(Jaguar.log);
/* 4201:4400 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/* 4202:4401 */         return localThrowable25.getClass().getName();
/* 4203:     */       }
/* 4204:     */     case 76: 
/* 4205:     */       try
/* 4206:     */       {
/* 4207:     */         String[] arrayOfString5;
/* 4208:4410 */         if (!paramBoolean)
/* 4209:     */         {
/* 4210:4412 */           arrayOfString5 = (String[])paramLocalFrame.get(0);
/* 4211:     */         }
/* 4212:     */         else
/* 4213:     */         {
/* 4214:4416 */           localObject10 = paramLocalFrame.get(0);
/* 4215:4417 */           arrayOfString5 = (String[])ObjectVal.clone(localObject10);
/* 4216:     */         }
/* 4217:4419 */         localObject10 = 
/* 4218:4420 */           paramOFX200BPWServicesBean.getIntraById(
/* 4219:4421 */           arrayOfString5);
/* 4220:     */         
/* 4221:4423 */         paramLocalFrame.setResult(localObject10);
/* 4222:     */       }
/* 4223:     */       catch (Throwable localThrowable26)
/* 4224:     */       {
/* 4225:4427 */         localThrowable26.printStackTrace(Jaguar.log);
/* 4226:4428 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/* 4227:4429 */         return localThrowable26.getClass().getName();
/* 4228:     */       }
/* 4229:     */     case 77: 
/* 4230:     */       try
/* 4231:     */       {
/* 4232:     */         String[] arrayOfString6;
/* 4233:4438 */         if (!paramBoolean)
/* 4234:     */         {
/* 4235:4440 */           arrayOfString6 = (String[])paramLocalFrame.get(0);
/* 4236:     */         }
/* 4237:     */         else
/* 4238:     */         {
/* 4239:4444 */           localObject10 = paramLocalFrame.get(0);
/* 4240:4445 */           arrayOfString6 = (String[])ObjectVal.clone(localObject10);
/* 4241:     */         }
/* 4242:4448 */         boolean bool1 = ((Boolean)paramLocalFrame.get(1)).booleanValue();
/* 4243:4449 */         localObject13 = paramOFX200BPWServicesBean
/* 4244:4450 */           .getIntraByRecSrvrTId(
/* 4245:4451 */           arrayOfString6, 
/* 4246:4452 */           bool1);
/* 4247:     */         
/* 4248:4454 */         paramLocalFrame.setResult(localObject13);
/* 4249:     */       }
/* 4250:     */       catch (Throwable localThrowable27)
/* 4251:     */       {
/* 4252:4458 */         localThrowable27.printStackTrace(Jaguar.log);
/* 4253:4459 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/* 4254:4460 */         return localThrowable27.getClass().getName();
/* 4255:     */       }
/* 4256:     */     case 78: 
/* 4257:     */       try
/* 4258:     */       {
/* 4259:     */         String[] arrayOfString7;
/* 4260:4469 */         if (!paramBoolean)
/* 4261:     */         {
/* 4262:4471 */           arrayOfString7 = (String[])paramLocalFrame.get(0);
/* 4263:     */         }
/* 4264:     */         else
/* 4265:     */         {
/* 4266:4475 */           localObject11 = paramLocalFrame.get(0);
/* 4267:4476 */           arrayOfString7 = (String[])ObjectVal.clone(localObject11);
/* 4268:     */         }
/* 4269:4478 */         localObject11 = 
/* 4270:4479 */           paramOFX200BPWServicesBean.getPmtById(
/* 4271:4480 */           arrayOfString7);
/* 4272:     */         
/* 4273:4482 */         paramLocalFrame.setResult(localObject11);
/* 4274:     */       }
/* 4275:     */       catch (Throwable localThrowable28)
/* 4276:     */       {
/* 4277:4486 */         localThrowable28.printStackTrace(Jaguar.log);
/* 4278:4487 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/* 4279:4488 */         return localThrowable28.getClass().getName();
/* 4280:     */       }
/* 4281:     */     case 79: 
/* 4282:     */       try
/* 4283:     */       {
/* 4284:4497 */         String str10 = (String)paramLocalFrame.get(0);
/* 4285:4498 */         localObject11 = paramOFX200BPWServicesBean
/* 4286:4499 */           .getPmtById(
/* 4287:4500 */           str10);
/* 4288:     */         
/* 4289:4502 */         paramLocalFrame.setResult(localObject11);
/* 4290:     */       }
/* 4291:     */       catch (Throwable localThrowable29)
/* 4292:     */       {
/* 4293:4506 */         localThrowable29.printStackTrace(Jaguar.log);
/* 4294:4507 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/* 4295:4508 */         return localThrowable29.getClass().getName();
/* 4296:     */       }
/* 4297:     */     case 80: 
/* 4298:     */       try
/* 4299:     */       {
/* 4300:     */         String[] arrayOfString8;
/* 4301:4517 */         if (!paramBoolean)
/* 4302:     */         {
/* 4303:4519 */           arrayOfString8 = (String[])paramLocalFrame.get(0);
/* 4304:     */         }
/* 4305:     */         else
/* 4306:     */         {
/* 4307:4523 */           localObject11 = paramLocalFrame.get(0);
/* 4308:4524 */           arrayOfString8 = (String[])ObjectVal.clone(localObject11);
/* 4309:     */         }
/* 4310:4526 */         localObject11 = 
/* 4311:4527 */           paramOFX200BPWServicesBean.getRecPmtById(
/* 4312:4528 */           arrayOfString8);
/* 4313:     */         
/* 4314:4530 */         paramLocalFrame.setResult(localObject11);
/* 4315:     */       }
/* 4316:     */       catch (Throwable localThrowable30)
/* 4317:     */       {
/* 4318:4534 */         localThrowable30.printStackTrace(Jaguar.log);
/* 4319:4535 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/* 4320:4536 */         return localThrowable30.getClass().getName();
/* 4321:     */       }
/* 4322:     */     case 81: 
/* 4323:     */       try
/* 4324:     */       {
/* 4325:     */         String[] arrayOfString9;
/* 4326:4545 */         if (!paramBoolean)
/* 4327:     */         {
/* 4328:4547 */           arrayOfString9 = (String[])paramLocalFrame.get(0);
/* 4329:     */         }
/* 4330:     */         else
/* 4331:     */         {
/* 4332:4551 */           localObject11 = paramLocalFrame.get(0);
/* 4333:4552 */           arrayOfString9 = (String[])ObjectVal.clone(localObject11);
/* 4334:     */         }
/* 4335:4554 */         localObject11 = 
/* 4336:4555 */           paramOFX200BPWServicesBean.getRecIntraById(
/* 4337:4556 */           arrayOfString9);
/* 4338:     */         
/* 4339:4558 */         paramLocalFrame.setResult(localObject11);
/* 4340:     */       }
/* 4341:     */       catch (Throwable localThrowable31)
/* 4342:     */       {
/* 4343:4562 */         localThrowable31.printStackTrace(Jaguar.log);
/* 4344:4563 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/* 4345:4564 */         return localThrowable31.getClass().getName();
/* 4346:     */       }
/* 4347:     */     case 82: 
/* 4348:     */       try
/* 4349:     */       {
/* 4350:4573 */         String str11 = (String)paramLocalFrame.get(0);
/* 4351:4574 */         localObject11 = paramOFX200BPWServicesBean
/* 4352:4575 */           .getRecIntraById(
/* 4353:4576 */           str11);
/* 4354:     */         
/* 4355:4578 */         paramLocalFrame.setResult(localObject11);
/* 4356:     */       }
/* 4357:     */       catch (Throwable localThrowable32)
/* 4358:     */       {
/* 4359:4582 */         localThrowable32.printStackTrace(Jaguar.log);
/* 4360:4583 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/* 4361:4584 */         return localThrowable32.getClass().getName();
/* 4362:     */       }
/* 4363:     */     case 83: 
/* 4364:     */       try
/* 4365:     */       {
/* 4366:4593 */         String str12 = (String)paramLocalFrame.get(0);
/* 4367:     */         
/* 4368:4595 */         localObject11 = (String)paramLocalFrame.get(1);
/* 4369:4596 */         localObject13 = paramOFX200BPWServicesBean
/* 4370:4597 */           .getPayeeByListId(
/* 4371:4598 */           str12, 
/* 4372:4599 */           (String)localObject11);
/* 4373:     */         
/* 4374:4601 */         paramLocalFrame.setResult(localObject13);
/* 4375:     */       }
/* 4376:     */       catch (Throwable localThrowable33)
/* 4377:     */       {
/* 4378:4605 */         if ((localThrowable33 instanceof FFSException))
/* 4379:     */         {
/* 4380:4607 */           paramLocalFrame.setException(localThrowable33);
/* 4381:4608 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4382:4609 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable33);
/* 4383:     */         }
/* 4384:4611 */         localThrowable33.printStackTrace(Jaguar.log);
/* 4385:4612 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/* 4386:4613 */         return localThrowable33.getClass().getName();
/* 4387:     */       }
/* 4388:     */     case 84: 
/* 4389:     */       try
/* 4390:     */       {
/* 4391:4621 */         AccountTypesMap localAccountTypesMap = paramOFX200BPWServicesBean
/* 4392:4622 */           .getAccountTypesMap();
/* 4393:     */         
/* 4394:4624 */         paramLocalFrame.setResult(localAccountTypesMap);
/* 4395:     */       }
/* 4396:     */       catch (Throwable localThrowable34)
/* 4397:     */       {
/* 4398:4628 */         if ((localThrowable34 instanceof FFSException))
/* 4399:     */         {
/* 4400:4630 */           paramLocalFrame.setException(localThrowable34);
/* 4401:4631 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4402:4632 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable34);
/* 4403:     */         }
/* 4404:4634 */         localThrowable34.printStackTrace(Jaguar.log);
/* 4405:4635 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/* 4406:4636 */         return localThrowable34.getClass().getName();
/* 4407:     */       }
/* 4408:     */     }
/* 4409:4641 */     return null;
/* 4410:     */   }
/* 4411:     */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices._sk_OFX200BPWServices_OFX200BPWServicesBean
 * JD-Core Version:    0.7.0.1
 */