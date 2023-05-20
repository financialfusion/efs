/*    1:     */ package com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices;
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
/*   31:     */ import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
/*   32:     */ import com.ffusion.ffs.bpw.interfaces.RecPmtInfoSeqHelper;
/*   33:     */ import com.ffusion.ffs.bpw.util.AccountTypesMap;
/*   34:     */ import com.ffusion.ffs.interfaces.FFSException;
/*   35:     */ import com.ffusion.ffs.interfaces.FFSExceptionHelper;
/*   36:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserData;
/*   37:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserDataHelper;
/*   38:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1;
/*   39:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1Helper;
/*   40:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1;
/*   41:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1Helper;
/*   42:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1SeqHelper;
/*   43:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1;
/*   44:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1Helper;
/*   45:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1;
/*   46:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Helper;
/*   47:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1;
/*   48:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1Helper;
/*   49:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1;
/*   50:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1Helper;
/*   51:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1;
/*   52:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1Helper;
/*   53:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1;
/*   54:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1Helper;
/*   55:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1;
/*   56:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1Helper;
/*   57:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1;
/*   58:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1Helper;
/*   59:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1;
/*   60:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1Helper;
/*   61:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1;
/*   62:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1Helper;
/*   63:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1SeqHelper;
/*   64:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1;
/*   65:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1Helper;
/*   66:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1;
/*   67:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1Helper;
/*   68:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1;
/*   69:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1Helper;
/*   70:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1;
/*   71:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1Helper;
/*   72:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1;
/*   73:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1Helper;
/*   74:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1;
/*   75:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1Helper;
/*   76:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1;
/*   77:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1Helper;
/*   78:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1;
/*   79:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1Helper;
/*   80:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1;
/*   81:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1Helper;
/*   82:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1;
/*   83:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Helper;
/*   84:     */ import com.sybase.CORBA.LocalFrame;
/*   85:     */ import com.sybase.CORBA.LocalStack;
/*   86:     */ import com.sybase.CORBA.ObjectVal;
/*   87:     */ import com.sybase.CORBA.UserException;
/*   88:     */ import com.sybase.CORBA._ServerRequest;
/*   89:     */ import com.sybase.CORBA.iiop.Connection;
/*   90:     */ import com.sybase.ejb.SessionContext;
/*   91:     */ import com.sybase.ejb.cts.StringSeqHelper;
/*   92:     */ import com.sybase.jaguar.server.Jaguar;
/*   93:     */ import java.util.ArrayList;
/*   94:     */ import java.util.HashMap;
/*   95:     */ import org.omg.CORBA.portable.InputStream;
/*   96:     */ import org.omg.CORBA.portable.OutputStream;
/*   97:     */ 
/*   98:     */ public abstract class _sk_OFX151BPWServices_OFX151BPWServicesBean
/*   99:     */ {
/*  100:  17 */   private static HashMap _methods = new HashMap(172);
/*  101:     */   private static HashMap _localMethods;
/*  102:     */   private static HashMap _localMethods2;
/*  103:     */   private static final String _RESET = "org.omg.CORBA.BAD_OPERATION";
/*  104:     */   
/*  105:     */   static
/*  106:     */   {
/*  107:  18 */     _methods.put("setSessionContext", new Integer(0));
/*  108:  19 */     _methods.put("ejbCreate", new Integer(1));
/*  109:  20 */     _methods.put("ejbActivate", new Integer(2));
/*  110:  21 */     _methods.put("ejbPassivate", new Integer(3));
/*  111:  22 */     _methods.put("ejbRemove", new Integer(4));
/*  112:  23 */     _methods.put("disconnect", new Integer(5));
/*  113:  24 */     _methods.put("addCustomers", new Integer(6));
/*  114:  25 */     _methods.put("modifyCustomers", new Integer(7));
/*  115:  26 */     _methods.put("deleteCustomers__StringSeq", new Integer(8));
/*  116:  27 */     _methods.put("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(8));
/*  117:  28 */     _methods.put("deleteCustomers__StringSeq__long", new Integer(9));
/*  118:  29 */     _methods.put("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long", new Integer(9));
/*  119:  30 */     _methods.put("deactivateCustomers", new Integer(10));
/*  120:  31 */     _methods.put("activateCustomers", new Integer(11));
/*  121:  32 */     _methods.put("getCustomersInfo", new Integer(12));
/*  122:  33 */     _methods.put("getCustomerByType", new Integer(13));
/*  123:  34 */     _methods.put("getCustomerByFI", new Integer(14));
/*  124:  35 */     _methods.put("getCustomerByCategory", new Integer(15));
/*  125:  36 */     _methods.put("getCustomerByGroup", new Integer(16));
/*  126:  37 */     _methods.put("getCustomerByTypeAndFI", new Integer(17));
/*  127:  38 */     _methods.put("getCustomerByCategoryAndFI", new Integer(18));
/*  128:  39 */     _methods.put("getCustomerByGroupAndFI", new Integer(19));
/*  129:  40 */     _methods.put("getLinkedPayees", new Integer(20));
/*  130:  41 */     _methods.put("_get_linkedPayees", new Integer(20));
/*  131:  42 */     _methods.put("getMostUsedPayees", new Integer(21));
/*  132:  43 */     _methods.put("getPreferredPayees", new Integer(22));
/*  133:  44 */     _methods.put("getPendingPmtsByCustomerID", new Integer(23));
/*  134:  45 */     _methods.put("getPendingPmtsAndHistoryByCustomerID", new Integer(24));
/*  135:  46 */     _methods.put("getPendingIntrasByCustomerID", new Integer(25));
/*  136:  47 */     _methods.put("getPendingIntrasAndHistoryByCustomerID", new Integer(26));
/*  137:  48 */     _methods.put("getPendingPmts", new Integer(27));
/*  138:  49 */     _methods.put("getPendingIntras", new Integer(28));
/*  139:  50 */     _methods.put("getPmtStatus", new Integer(29));
/*  140:  51 */     _methods.put("checkPayeeEditMask", new Integer(30));
/*  141:  52 */     _methods.put("processIntraTrnRslt", new Integer(31));
/*  142:  53 */     _methods.put("processPmtTrnRslt", new Integer(32));
/*  143:  54 */     _methods.put("processCustPayeeRslt", new Integer(33));
/*  144:  55 */     _methods.put("processFundAllocRslt", new Integer(34));
/*  145:  56 */     _methods.put("processFundRevertRslt", new Integer(35));
/*  146:  57 */     _methods.put("processPayeeRslt", new Integer(36));
/*  147:  58 */     _methods.put("addPayeeFromBackend", new Integer(37));
/*  148:  59 */     _methods.put("updatePayeeFromBackend", new Integer(38));
/*  149:  60 */     _methods.put("addPayeeRouteInfo", new Integer(39));
/*  150:  61 */     _methods.put("processIntraSyncRqV1", new Integer(40));
/*  151:  62 */     _methods.put("processIntraTrnRqV1", new Integer(41));
/*  152:  63 */     _methods.put("processPayeeSyncRqV1", new Integer(42));
/*  153:  64 */     _methods.put("processPayeeTrnRqV1", new Integer(43));
/*  154:  65 */     _methods.put("processPmtInqTrnRqV1", new Integer(44));
/*  155:  66 */     _methods.put("processPmtSyncRqV1", new Integer(45));
/*  156:  67 */     _methods.put("processPmtTrnRqV1", new Integer(46));
/*  157:  68 */     _methods.put("processRecIntraSyncRqV1", new Integer(47));
/*  158:  69 */     _methods.put("processRecIntraTrnRqV1", new Integer(48));
/*  159:  70 */     _methods.put("processRecPmtSyncRqV1", new Integer(49));
/*  160:  71 */     _methods.put("processRecPmtTrnRqV1", new Integer(50));
/*  161:  72 */     _methods.put("getPayeeNames__string__long", new Integer(51));
/*  162:  73 */     _methods.put("getPayeeNames__CORBA_WStringValue__long", new Integer(51));
/*  163:  74 */     _methods.put("getPayeeNames__string", new Integer(52));
/*  164:  75 */     _methods.put("getPayeeNames__CORBA_WStringValue", new Integer(52));
/*  165:  76 */     _methods.put("getPayeeIDs", new Integer(53));
/*  166:  77 */     _methods.put("getPayees__string__long", new Integer(54));
/*  167:  78 */     _methods.put("getPayees__CORBA_WStringValue__long", new Integer(54));
/*  168:  79 */     _methods.put("getPayees__string", new Integer(55));
/*  169:  80 */     _methods.put("getPayees__CORBA_WStringValue", new Integer(55));
/*  170:  81 */     _methods.put("searchGlobalPayees", new Integer(56));
/*  171:  82 */     _methods.put("updatePayee__PayeeInfo__long", new Integer(57));
/*  172:  83 */     _methods.put("updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__long", new Integer(57));
/*  173:  84 */     _methods.put("updatePayee__PayeeInfo__PayeeRouteInfo", new Integer(58));
/*  174:  85 */     _methods.put("updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeRouteInfo", new Integer(58));
/*  175:  86 */     _methods.put("deletePayee", new Integer(59));
/*  176:  87 */     _methods.put("deletePayees", new Integer(60));
/*  177:  88 */     _methods.put("findPayeeByID", new Integer(61));
/*  178:  89 */     _methods.put("setPayeeStatus", new Integer(62));
/*  179:  90 */     _methods.put("getSmartDate", new Integer(63));
/*  180:  91 */     _methods.put("getGlobalPayees", new Integer(64));
/*  181:  92 */     _methods.put("addPayee", new Integer(65));
/*  182:  93 */     _methods.put("addConsumerCrossRef", new Integer(66));
/*  183:  94 */     _methods.put("deleteConsumerCrossRef", new Integer(67));
/*  184:  95 */     _methods.put("getConsumerCrossRef", new Integer(68));
/*  185:  96 */     _methods.put("addCustomerBankInfo", new Integer(69));
/*  186:  97 */     _methods.put("deleteCustomerBankInfo", new Integer(70));
/*  187:  98 */     _methods.put("getCustomerBankInfo", new Integer(71));
/*  188:  99 */     _methods.put("addCustomerProductAccessInfo", new Integer(72));
/*  189: 100 */     _methods.put("deleteCustomerProductAccessInfo", new Integer(73));
/*  190: 101 */     _methods.put("getCustomerProductAccessInfo", new Integer(74));
/*  191: 102 */     _methods.put("validateMetavanteCustAcctByConsumerID", new Integer(75));
/*  192: 103 */     _methods.put("validateMetavanteCustAcctByCustomerID", new Integer(76));
/*  193: 104 */     _methods.put("getPmtHistory", new Integer(77));
/*  194: 105 */     _methods.put("getIntraHistory", new Integer(78));
/*  195: 106 */     _methods.put("getIntraById__StringSeq", new Integer(79));
/*  196: 107 */     _methods.put("getIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(79));
/*  197: 108 */     _methods.put("getIntraById__string", new Integer(80));
/*  198: 109 */     _methods.put("getIntraById__CORBA_WStringValue", new Integer(80));
/*  199: 110 */     _methods.put("getPmtById__string", new Integer(81));
/*  200: 111 */     _methods.put("getPmtById__CORBA_WStringValue", new Integer(81));
/*  201: 112 */     _methods.put("getPmtById__StringSeq", new Integer(82));
/*  202: 113 */     _methods.put("getPmtById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(82));
/*  203: 114 */     _methods.put("getRecPmtById", new Integer(83));
/*  204: 115 */     _methods.put("getPayeeByListId", new Integer(84));
/*  205: 116 */     _methods.put("getAccountTypesMap", new Integer(85));
/*  206:     */     
/*  207:     */ 
/*  208:     */ 
/*  209:     */ 
/*  210:     */ 
/*  211:     */ 
/*  212:     */ 
/*  213:     */ 
/*  214: 125 */     _localMethods = new HashMap(172);
/*  215: 126 */     _localMethods2 = new HashMap(172);
/*  216: 127 */     _localMethods.put("#ejbCreate", new Integer(0));
/*  217: 128 */     _localMethods2.put("ejbCreate", new Integer(0));
/*  218: 129 */     _localMethods.put("#ejbRemove", new Integer(1));
/*  219: 130 */     _localMethods2.put("ejbRemove", new Integer(1));
/*  220: 131 */     _localMethods.put("#disconnect", new Integer(2));
/*  221: 132 */     _localMethods2.put("disconnect", new Integer(2));
/*  222: 133 */     _localMethods.put("#addCustomers", new Integer(3));
/*  223: 134 */     _localMethods2.put("addCustomers", new Integer(3));
/*  224: 135 */     _localMethods.put("#modifyCustomers", new Integer(4));
/*  225: 136 */     _localMethods2.put("modifyCustomers", new Integer(4));
/*  226: 137 */     _localMethods.put("#deleteCustomers__StringSeq", new Integer(5));
/*  227: 138 */     _localMethods2.put("deleteCustomers__StringSeq", new Integer(5));
/*  228: 139 */     _localMethods.put("#deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(5));
/*  229: 140 */     _localMethods2.put("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(5));
/*  230: 141 */     _localMethods.put("#deleteCustomers__StringSeq__long", new Integer(6));
/*  231: 142 */     _localMethods2.put("deleteCustomers__StringSeq__long", new Integer(6));
/*  232: 143 */     _localMethods.put("#deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long", new Integer(6));
/*  233: 144 */     _localMethods2.put("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long", new Integer(6));
/*  234: 145 */     _localMethods.put("#deactivateCustomers", new Integer(7));
/*  235: 146 */     _localMethods2.put("deactivateCustomers", new Integer(7));
/*  236: 147 */     _localMethods.put("#activateCustomers", new Integer(8));
/*  237: 148 */     _localMethods2.put("activateCustomers", new Integer(8));
/*  238: 149 */     _localMethods.put("#getCustomersInfo", new Integer(9));
/*  239: 150 */     _localMethods2.put("getCustomersInfo", new Integer(9));
/*  240: 151 */     _localMethods.put("#getCustomerByType", new Integer(10));
/*  241: 152 */     _localMethods2.put("getCustomerByType", new Integer(10));
/*  242: 153 */     _localMethods.put("#getCustomerByFI", new Integer(11));
/*  243: 154 */     _localMethods2.put("getCustomerByFI", new Integer(11));
/*  244: 155 */     _localMethods.put("#getCustomerByCategory", new Integer(12));
/*  245: 156 */     _localMethods2.put("getCustomerByCategory", new Integer(12));
/*  246: 157 */     _localMethods.put("#getCustomerByGroup", new Integer(13));
/*  247: 158 */     _localMethods2.put("getCustomerByGroup", new Integer(13));
/*  248: 159 */     _localMethods.put("#getCustomerByTypeAndFI", new Integer(14));
/*  249: 160 */     _localMethods2.put("getCustomerByTypeAndFI", new Integer(14));
/*  250: 161 */     _localMethods.put("#getCustomerByCategoryAndFI", new Integer(15));
/*  251: 162 */     _localMethods2.put("getCustomerByCategoryAndFI", new Integer(15));
/*  252: 163 */     _localMethods.put("#getCustomerByGroupAndFI", new Integer(16));
/*  253: 164 */     _localMethods2.put("getCustomerByGroupAndFI", new Integer(16));
/*  254: 165 */     _localMethods.put("#getLinkedPayees", new Integer(17));
/*  255: 166 */     _localMethods2.put("getLinkedPayees", new Integer(17));
/*  256: 167 */     _localMethods.put("#_get_linkedPayees", new Integer(17));
/*  257: 168 */     _localMethods2.put("_get_linkedPayees", new Integer(17));
/*  258: 169 */     _localMethods.put("#getMostUsedPayees", new Integer(18));
/*  259: 170 */     _localMethods2.put("getMostUsedPayees", new Integer(18));
/*  260: 171 */     _localMethods.put("#getPreferredPayees", new Integer(19));
/*  261: 172 */     _localMethods2.put("getPreferredPayees", new Integer(19));
/*  262: 173 */     _localMethods.put("#getPendingPmtsByCustomerID", new Integer(20));
/*  263: 174 */     _localMethods2.put("getPendingPmtsByCustomerID", new Integer(20));
/*  264: 175 */     _localMethods.put("#getPendingPmtsAndHistoryByCustomerID", new Integer(21));
/*  265: 176 */     _localMethods2.put("getPendingPmtsAndHistoryByCustomerID", new Integer(21));
/*  266: 177 */     _localMethods.put("#getPendingIntrasByCustomerID", new Integer(22));
/*  267: 178 */     _localMethods2.put("getPendingIntrasByCustomerID", new Integer(22));
/*  268: 179 */     _localMethods.put("#getPendingIntrasAndHistoryByCustomerID", new Integer(23));
/*  269: 180 */     _localMethods2.put("getPendingIntrasAndHistoryByCustomerID", new Integer(23));
/*  270: 181 */     _localMethods.put("#getPendingPmts", new Integer(24));
/*  271: 182 */     _localMethods2.put("getPendingPmts", new Integer(24));
/*  272: 183 */     _localMethods.put("#getPendingIntras", new Integer(25));
/*  273: 184 */     _localMethods2.put("getPendingIntras", new Integer(25));
/*  274: 185 */     _localMethods.put("#getPmtStatus", new Integer(26));
/*  275: 186 */     _localMethods2.put("getPmtStatus", new Integer(26));
/*  276: 187 */     _localMethods.put("#checkPayeeEditMask", new Integer(27));
/*  277: 188 */     _localMethods2.put("checkPayeeEditMask", new Integer(27));
/*  278: 189 */     _localMethods.put("#processIntraTrnRslt", new Integer(28));
/*  279: 190 */     _localMethods2.put("processIntraTrnRslt", new Integer(28));
/*  280: 191 */     _localMethods.put("#processPmtTrnRslt", new Integer(29));
/*  281: 192 */     _localMethods2.put("processPmtTrnRslt", new Integer(29));
/*  282: 193 */     _localMethods.put("#processCustPayeeRslt", new Integer(30));
/*  283: 194 */     _localMethods2.put("processCustPayeeRslt", new Integer(30));
/*  284: 195 */     _localMethods.put("#processFundAllocRslt", new Integer(31));
/*  285: 196 */     _localMethods2.put("processFundAllocRslt", new Integer(31));
/*  286: 197 */     _localMethods.put("#processFundRevertRslt", new Integer(32));
/*  287: 198 */     _localMethods2.put("processFundRevertRslt", new Integer(32));
/*  288: 199 */     _localMethods.put("#processPayeeRslt", new Integer(33));
/*  289: 200 */     _localMethods2.put("processPayeeRslt", new Integer(33));
/*  290: 201 */     _localMethods.put("#addPayeeFromBackend", new Integer(34));
/*  291: 202 */     _localMethods2.put("addPayeeFromBackend", new Integer(34));
/*  292: 203 */     _localMethods.put("#updatePayeeFromBackend", new Integer(35));
/*  293: 204 */     _localMethods2.put("updatePayeeFromBackend", new Integer(35));
/*  294: 205 */     _localMethods.put("#addPayeeRouteInfo", new Integer(36));
/*  295: 206 */     _localMethods2.put("addPayeeRouteInfo", new Integer(36));
/*  296: 207 */     _localMethods.put("#processIntraSyncRqV1", new Integer(37));
/*  297: 208 */     _localMethods2.put("processIntraSyncRqV1", new Integer(37));
/*  298: 209 */     _localMethods.put("#processIntraTrnRqV1", new Integer(38));
/*  299: 210 */     _localMethods2.put("processIntraTrnRqV1", new Integer(38));
/*  300: 211 */     _localMethods.put("#processPayeeSyncRqV1", new Integer(39));
/*  301: 212 */     _localMethods2.put("processPayeeSyncRqV1", new Integer(39));
/*  302: 213 */     _localMethods.put("#processPayeeTrnRqV1", new Integer(40));
/*  303: 214 */     _localMethods2.put("processPayeeTrnRqV1", new Integer(40));
/*  304: 215 */     _localMethods.put("#processPmtInqTrnRqV1", new Integer(41));
/*  305: 216 */     _localMethods2.put("processPmtInqTrnRqV1", new Integer(41));
/*  306: 217 */     _localMethods.put("#processPmtSyncRqV1", new Integer(42));
/*  307: 218 */     _localMethods2.put("processPmtSyncRqV1", new Integer(42));
/*  308: 219 */     _localMethods.put("#processPmtTrnRqV1", new Integer(43));
/*  309: 220 */     _localMethods2.put("processPmtTrnRqV1", new Integer(43));
/*  310: 221 */     _localMethods.put("#processRecIntraSyncRqV1", new Integer(44));
/*  311: 222 */     _localMethods2.put("processRecIntraSyncRqV1", new Integer(44));
/*  312: 223 */     _localMethods.put("#processRecIntraTrnRqV1", new Integer(45));
/*  313: 224 */     _localMethods2.put("processRecIntraTrnRqV1", new Integer(45));
/*  314: 225 */     _localMethods.put("#processRecPmtSyncRqV1", new Integer(46));
/*  315: 226 */     _localMethods2.put("processRecPmtSyncRqV1", new Integer(46));
/*  316: 227 */     _localMethods.put("#processRecPmtTrnRqV1", new Integer(47));
/*  317: 228 */     _localMethods2.put("processRecPmtTrnRqV1", new Integer(47));
/*  318: 229 */     _localMethods.put("#getPayeeNames__string__long", new Integer(48));
/*  319: 230 */     _localMethods2.put("getPayeeNames__string__long", new Integer(48));
/*  320: 231 */     _localMethods.put("#getPayeeNames__CORBA_WStringValue__long", new Integer(48));
/*  321: 232 */     _localMethods2.put("getPayeeNames__CORBA_WStringValue__long", new Integer(48));
/*  322: 233 */     _localMethods.put("#getPayeeNames__string", new Integer(49));
/*  323: 234 */     _localMethods2.put("getPayeeNames__string", new Integer(49));
/*  324: 235 */     _localMethods.put("#getPayeeNames__CORBA_WStringValue", new Integer(49));
/*  325: 236 */     _localMethods2.put("getPayeeNames__CORBA_WStringValue", new Integer(49));
/*  326: 237 */     _localMethods.put("#getPayeeIDs", new Integer(50));
/*  327: 238 */     _localMethods2.put("getPayeeIDs", new Integer(50));
/*  328: 239 */     _localMethods.put("#getPayees__string__long", new Integer(51));
/*  329: 240 */     _localMethods2.put("getPayees__string__long", new Integer(51));
/*  330: 241 */     _localMethods.put("#getPayees__CORBA_WStringValue__long", new Integer(51));
/*  331: 242 */     _localMethods2.put("getPayees__CORBA_WStringValue__long", new Integer(51));
/*  332: 243 */     _localMethods.put("#getPayees__string", new Integer(52));
/*  333: 244 */     _localMethods2.put("getPayees__string", new Integer(52));
/*  334: 245 */     _localMethods.put("#getPayees__CORBA_WStringValue", new Integer(52));
/*  335: 246 */     _localMethods2.put("getPayees__CORBA_WStringValue", new Integer(52));
/*  336: 247 */     _localMethods.put("#searchGlobalPayees", new Integer(53));
/*  337: 248 */     _localMethods2.put("searchGlobalPayees", new Integer(53));
/*  338: 249 */     _localMethods.put("#updatePayee__PayeeInfo__long", new Integer(54));
/*  339: 250 */     _localMethods2.put("updatePayee__PayeeInfo__long", new Integer(54));
/*  340: 251 */     _localMethods.put("#updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__long", new Integer(54));
/*  341: 252 */     _localMethods2.put("updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__long", new Integer(54));
/*  342: 253 */     _localMethods.put("#updatePayee__PayeeInfo__PayeeRouteInfo", new Integer(55));
/*  343: 254 */     _localMethods2.put("updatePayee__PayeeInfo__PayeeRouteInfo", new Integer(55));
/*  344: 255 */     _localMethods.put("#updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeRouteInfo", new Integer(55));
/*  345: 256 */     _localMethods2.put("updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeRouteInfo", new Integer(55));
/*  346: 257 */     _localMethods.put("#deletePayee", new Integer(56));
/*  347: 258 */     _localMethods2.put("deletePayee", new Integer(56));
/*  348: 259 */     _localMethods.put("#deletePayees", new Integer(57));
/*  349: 260 */     _localMethods2.put("deletePayees", new Integer(57));
/*  350: 261 */     _localMethods.put("#findPayeeByID", new Integer(58));
/*  351: 262 */     _localMethods2.put("findPayeeByID", new Integer(58));
/*  352: 263 */     _localMethods.put("#setPayeeStatus", new Integer(59));
/*  353: 264 */     _localMethods2.put("setPayeeStatus", new Integer(59));
/*  354: 265 */     _localMethods.put("#getSmartDate", new Integer(60));
/*  355: 266 */     _localMethods2.put("getSmartDate", new Integer(60));
/*  356: 267 */     _localMethods.put("#getGlobalPayees", new Integer(61));
/*  357: 268 */     _localMethods2.put("getGlobalPayees", new Integer(61));
/*  358: 269 */     _localMethods.put("#addPayee", new Integer(62));
/*  359: 270 */     _localMethods2.put("addPayee", new Integer(62));
/*  360: 271 */     _localMethods.put("#addConsumerCrossRef", new Integer(63));
/*  361: 272 */     _localMethods2.put("addConsumerCrossRef", new Integer(63));
/*  362: 273 */     _localMethods.put("#deleteConsumerCrossRef", new Integer(64));
/*  363: 274 */     _localMethods2.put("deleteConsumerCrossRef", new Integer(64));
/*  364: 275 */     _localMethods.put("#getConsumerCrossRef", new Integer(65));
/*  365: 276 */     _localMethods2.put("getConsumerCrossRef", new Integer(65));
/*  366: 277 */     _localMethods.put("#addCustomerBankInfo", new Integer(66));
/*  367: 278 */     _localMethods2.put("addCustomerBankInfo", new Integer(66));
/*  368: 279 */     _localMethods.put("#deleteCustomerBankInfo", new Integer(67));
/*  369: 280 */     _localMethods2.put("deleteCustomerBankInfo", new Integer(67));
/*  370: 281 */     _localMethods.put("#getCustomerBankInfo", new Integer(68));
/*  371: 282 */     _localMethods2.put("getCustomerBankInfo", new Integer(68));
/*  372: 283 */     _localMethods.put("#addCustomerProductAccessInfo", new Integer(69));
/*  373: 284 */     _localMethods2.put("addCustomerProductAccessInfo", new Integer(69));
/*  374: 285 */     _localMethods.put("#deleteCustomerProductAccessInfo", new Integer(70));
/*  375: 286 */     _localMethods2.put("deleteCustomerProductAccessInfo", new Integer(70));
/*  376: 287 */     _localMethods.put("#getCustomerProductAccessInfo", new Integer(71));
/*  377: 288 */     _localMethods2.put("getCustomerProductAccessInfo", new Integer(71));
/*  378: 289 */     _localMethods.put("#validateMetavanteCustAcctByConsumerID", new Integer(72));
/*  379: 290 */     _localMethods2.put("validateMetavanteCustAcctByConsumerID", new Integer(72));
/*  380: 291 */     _localMethods.put("#validateMetavanteCustAcctByCustomerID", new Integer(73));
/*  381: 292 */     _localMethods2.put("validateMetavanteCustAcctByCustomerID", new Integer(73));
/*  382: 293 */     _localMethods.put("#getPmtHistory", new Integer(74));
/*  383: 294 */     _localMethods2.put("getPmtHistory", new Integer(74));
/*  384: 295 */     _localMethods.put("#getIntraHistory", new Integer(75));
/*  385: 296 */     _localMethods2.put("getIntraHistory", new Integer(75));
/*  386: 297 */     _localMethods.put("#getIntraById__StringSeq", new Integer(76));
/*  387: 298 */     _localMethods2.put("getIntraById__StringSeq", new Integer(76));
/*  388: 299 */     _localMethods.put("#getIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(76));
/*  389: 300 */     _localMethods2.put("getIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(76));
/*  390: 301 */     _localMethods.put("#getIntraById__string", new Integer(77));
/*  391: 302 */     _localMethods2.put("getIntraById__string", new Integer(77));
/*  392: 303 */     _localMethods.put("#getIntraById__CORBA_WStringValue", new Integer(77));
/*  393: 304 */     _localMethods2.put("getIntraById__CORBA_WStringValue", new Integer(77));
/*  394: 305 */     _localMethods.put("#getPmtById__string", new Integer(78));
/*  395: 306 */     _localMethods2.put("getPmtById__string", new Integer(78));
/*  396: 307 */     _localMethods.put("#getPmtById__CORBA_WStringValue", new Integer(78));
/*  397: 308 */     _localMethods2.put("getPmtById__CORBA_WStringValue", new Integer(78));
/*  398: 309 */     _localMethods.put("#getPmtById__StringSeq", new Integer(79));
/*  399: 310 */     _localMethods2.put("getPmtById__StringSeq", new Integer(79));
/*  400: 311 */     _localMethods.put("#getPmtById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(79));
/*  401: 312 */     _localMethods2.put("getPmtById__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(79));
/*  402: 313 */     _localMethods.put("#getRecPmtById", new Integer(80));
/*  403: 314 */     _localMethods2.put("getRecPmtById", new Integer(80));
/*  404: 315 */     _localMethods.put("#getPayeeByListId", new Integer(81));
/*  405: 316 */     _localMethods2.put("getPayeeByListId", new Integer(81));
/*  406: 317 */     _localMethods.put("#getAccountTypesMap", new Integer(82));
/*  407: 318 */     _localMethods2.put("getAccountTypesMap", new Integer(82));
/*  408:     */   }
/*  409:     */   
/*  410:     */   public static Object create()
/*  411:     */     throws Exception
/*  412:     */   {
/*  413: 326 */     return new OFX151BPWServicesBean();
/*  414:     */   }
/*  415:     */   
/*  416:     */   public static String invoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream)
/*  417:     */   {
/*  418: 335 */     return invoke(paramObject, paramString, paramInputStream, paramOutputStream, 0);
/*  419:     */   }
/*  420:     */   
/*  421:     */   public static String invoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/*  422:     */   {
/*  423: 345 */     if ((paramString.startsWith("#")) || (LocalStack.getCurrent().isArgsOnLocal())) {
/*  424: 347 */       return localInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/*  425:     */     }
/*  426: 351 */     return remoteInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/*  427:     */   }
/*  428:     */   
/*  429:     */   public static String remoteInvoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/*  430:     */   {
/*  431: 362 */     _ServerRequest local_ServerRequest = new _ServerRequest(paramInputStream, paramOutputStream, (paramInt & 0x1) != 0);
/*  432: 363 */     OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)paramObject;
/*  433: 364 */     Integer localInteger = (Integer)_methods.get(paramString);
/*  434: 365 */     if (localInteger == null) {
/*  435: 367 */       return "org.omg.CORBA.BAD_OPERATION";
/*  436:     */     }
/*  437:     */     int j;
/*  438:     */     Object localObject1;
/*  439:     */     Object localObject3;
/*  440:     */     int k;
/*  441:     */     Object localObject5;
/*  442:     */     int i1;
/*  443:     */     Object localObject2;
/*  444:     */     Object localObject4;
/*  445: 369 */     switch (localInteger.intValue())
/*  446:     */     {
/*  447:     */     case 0: 
/*  448:     */       try
/*  449:     */       {
/*  450: 376 */         SessionContext localSessionContext = new SessionContext(ObjectContextHelper.read(paramInputStream));
/*  451: 377 */         localOFX151BPWServicesBean
/*  452: 378 */           .setSessionContext(
/*  453: 379 */           localSessionContext);
/*  454:     */       }
/*  455:     */       catch (Throwable localThrowable1)
/*  456:     */       {
/*  457: 384 */         localThrowable1.printStackTrace(Jaguar.log);
/*  458: 385 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/*  459: 386 */         return localThrowable1.getClass().getName();
/*  460:     */       }
/*  461:     */     case 1: 
/*  462:     */       try
/*  463:     */       {
/*  464: 395 */         localOFX151BPWServicesBean.ejbCreate();
/*  465:     */       }
/*  466:     */       catch (Throwable localThrowable2)
/*  467:     */       {
/*  468: 400 */         localThrowable2.printStackTrace(Jaguar.log);
/*  469: 401 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/*  470: 402 */         return localThrowable2.getClass().getName();
/*  471:     */       }
/*  472:     */     case 2: 
/*  473:     */       try
/*  474:     */       {
/*  475: 411 */         localOFX151BPWServicesBean.ejbActivate();
/*  476:     */       }
/*  477:     */       catch (Throwable localThrowable3)
/*  478:     */       {
/*  479: 416 */         localThrowable3.printStackTrace(Jaguar.log);
/*  480: 417 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/*  481: 418 */         return localThrowable3.getClass().getName();
/*  482:     */       }
/*  483:     */     case 3: 
/*  484:     */       try
/*  485:     */       {
/*  486: 427 */         localOFX151BPWServicesBean.ejbPassivate();
/*  487:     */       }
/*  488:     */       catch (Throwable localThrowable4)
/*  489:     */       {
/*  490: 432 */         localThrowable4.printStackTrace(Jaguar.log);
/*  491: 433 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/*  492: 434 */         return localThrowable4.getClass().getName();
/*  493:     */       }
/*  494:     */     case 4: 
/*  495:     */       try
/*  496:     */       {
/*  497: 443 */         localOFX151BPWServicesBean.ejbRemove();
/*  498:     */       }
/*  499:     */       catch (Throwable localThrowable5)
/*  500:     */       {
/*  501: 448 */         localThrowable5.printStackTrace(Jaguar.log);
/*  502: 449 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/*  503: 450 */         return localThrowable5.getClass().getName();
/*  504:     */       }
/*  505:     */     case 5: 
/*  506:     */       try
/*  507:     */       {
/*  508: 459 */         localOFX151BPWServicesBean.disconnect();
/*  509:     */       }
/*  510:     */       catch (Throwable localThrowable6)
/*  511:     */       {
/*  512: 464 */         localThrowable6.printStackTrace(Jaguar.log);
/*  513: 465 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/*  514: 466 */         return localThrowable6.getClass().getName();
/*  515:     */       }
/*  516:     */     case 6: 
/*  517:     */       try
/*  518:     */       {
/*  519:     */         CustomerInfo[] arrayOfCustomerInfo1;
/*  520: 475 */         if (local_ServerRequest.isRMI()) {
/*  521: 475 */           arrayOfCustomerInfo1 = (CustomerInfo[])local_ServerRequest.read_value(new CustomerInfo[0].getClass());
/*  522:     */         } else {
/*  523: 475 */           arrayOfCustomerInfo1 = CustomerInfoSeqHelper.read(paramInputStream);
/*  524:     */         }
/*  525: 476 */         j = 
/*  526: 477 */           localOFX151BPWServicesBean.addCustomers(
/*  527: 478 */           arrayOfCustomerInfo1);
/*  528:     */         
/*  529: 480 */         paramOutputStream.write_long(j);
/*  530:     */       }
/*  531:     */       catch (Throwable localThrowable7)
/*  532:     */       {
/*  533: 484 */         localThrowable7.printStackTrace(Jaguar.log);
/*  534: 485 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/*  535: 486 */         return localThrowable7.getClass().getName();
/*  536:     */       }
/*  537:     */     case 7: 
/*  538:     */       try
/*  539:     */       {
/*  540:     */         CustomerInfo[] arrayOfCustomerInfo2;
/*  541: 495 */         if (local_ServerRequest.isRMI()) {
/*  542: 495 */           arrayOfCustomerInfo2 = (CustomerInfo[])local_ServerRequest.read_value(new CustomerInfo[0].getClass());
/*  543:     */         } else {
/*  544: 495 */           arrayOfCustomerInfo2 = CustomerInfoSeqHelper.read(paramInputStream);
/*  545:     */         }
/*  546: 496 */         j = 
/*  547: 497 */           localOFX151BPWServicesBean.modifyCustomers(
/*  548: 498 */           arrayOfCustomerInfo2);
/*  549:     */         
/*  550: 500 */         paramOutputStream.write_long(j);
/*  551:     */       }
/*  552:     */       catch (Throwable localThrowable8)
/*  553:     */       {
/*  554: 504 */         localThrowable8.printStackTrace(Jaguar.log);
/*  555: 505 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/*  556: 506 */         return localThrowable8.getClass().getName();
/*  557:     */       }
/*  558:     */     case 8: 
/*  559:     */       try
/*  560:     */       {
/*  561:     */         String[] arrayOfString1;
/*  562: 515 */         if (local_ServerRequest.isRMI()) {
/*  563: 515 */           arrayOfString1 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*  564:     */         } else {
/*  565: 515 */           arrayOfString1 = StringSeqHelper.read(paramInputStream);
/*  566:     */         }
/*  567: 516 */         j = 
/*  568: 517 */           localOFX151BPWServicesBean.deleteCustomers(
/*  569: 518 */           arrayOfString1);
/*  570:     */         
/*  571: 520 */         paramOutputStream.write_long(j);
/*  572:     */       }
/*  573:     */       catch (Throwable localThrowable9)
/*  574:     */       {
/*  575: 524 */         localThrowable9.printStackTrace(Jaguar.log);
/*  576: 525 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/*  577: 526 */         return localThrowable9.getClass().getName();
/*  578:     */       }
/*  579:     */     case 9: 
/*  580:     */       try
/*  581:     */       {
/*  582:     */         String[] arrayOfString2;
/*  583: 535 */         if (local_ServerRequest.isRMI()) {
/*  584: 535 */           arrayOfString2 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*  585:     */         } else {
/*  586: 535 */           arrayOfString2 = StringSeqHelper.read(paramInputStream);
/*  587:     */         }
/*  588: 537 */         j = paramInputStream.read_long();
/*  589: 538 */         int m = localOFX151BPWServicesBean
/*  590: 539 */           .deleteCustomers(
/*  591: 540 */           arrayOfString2, 
/*  592: 541 */           j);
/*  593:     */         
/*  594: 543 */         paramOutputStream.write_long(m);
/*  595:     */       }
/*  596:     */       catch (Throwable localThrowable10)
/*  597:     */       {
/*  598: 547 */         localThrowable10.printStackTrace(Jaguar.log);
/*  599: 548 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/*  600: 549 */         return localThrowable10.getClass().getName();
/*  601:     */       }
/*  602:     */     case 10: 
/*  603:     */       try
/*  604:     */       {
/*  605:     */         String[] arrayOfString3;
/*  606: 558 */         if (local_ServerRequest.isRMI()) {
/*  607: 558 */           arrayOfString3 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*  608:     */         } else {
/*  609: 558 */           arrayOfString3 = StringSeqHelper.read(paramInputStream);
/*  610:     */         }
/*  611: 559 */         j = 
/*  612: 560 */           localOFX151BPWServicesBean.deactivateCustomers(
/*  613: 561 */           arrayOfString3);
/*  614:     */         
/*  615: 563 */         paramOutputStream.write_long(j);
/*  616:     */       }
/*  617:     */       catch (Throwable localThrowable11)
/*  618:     */       {
/*  619: 567 */         localThrowable11.printStackTrace(Jaguar.log);
/*  620: 568 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/*  621: 569 */         return localThrowable11.getClass().getName();
/*  622:     */       }
/*  623:     */     case 11: 
/*  624:     */       try
/*  625:     */       {
/*  626:     */         String[] arrayOfString4;
/*  627: 578 */         if (local_ServerRequest.isRMI()) {
/*  628: 578 */           arrayOfString4 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*  629:     */         } else {
/*  630: 578 */           arrayOfString4 = StringSeqHelper.read(paramInputStream);
/*  631:     */         }
/*  632: 579 */         j = 
/*  633: 580 */           localOFX151BPWServicesBean.activateCustomers(
/*  634: 581 */           arrayOfString4);
/*  635:     */         
/*  636: 583 */         paramOutputStream.write_long(j);
/*  637:     */       }
/*  638:     */       catch (Throwable localThrowable12)
/*  639:     */       {
/*  640: 587 */         localThrowable12.printStackTrace(Jaguar.log);
/*  641: 588 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/*  642: 589 */         return localThrowable12.getClass().getName();
/*  643:     */       }
/*  644:     */     case 12: 
/*  645:     */       try
/*  646:     */       {
/*  647:     */         String[] arrayOfString5;
/*  648: 598 */         if (local_ServerRequest.isRMI()) {
/*  649: 598 */           arrayOfString5 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*  650:     */         } else {
/*  651: 598 */           arrayOfString5 = StringSeqHelper.read(paramInputStream);
/*  652:     */         }
/*  653: 599 */         localObject1 = 
/*  654: 600 */           localOFX151BPWServicesBean.getCustomersInfo(
/*  655: 601 */           arrayOfString5);
/*  656: 603 */         if (local_ServerRequest.isRMI()) {
/*  657: 603 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*  658:     */         } else {
/*  659: 603 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*  660:     */         }
/*  661:     */       }
/*  662:     */       catch (Throwable localThrowable13)
/*  663:     */       {
/*  664: 607 */         localThrowable13.printStackTrace(Jaguar.log);
/*  665: 608 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/*  666: 609 */         return localThrowable13.getClass().getName();
/*  667:     */       }
/*  668:     */     case 13: 
/*  669:     */       try
/*  670:     */       {
/*  671: 618 */         String str1 = local_ServerRequest.read_string();
/*  672: 619 */         localObject1 = localOFX151BPWServicesBean
/*  673: 620 */           .getCustomerByType(
/*  674: 621 */           str1);
/*  675: 623 */         if (local_ServerRequest.isRMI()) {
/*  676: 623 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*  677:     */         } else {
/*  678: 623 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*  679:     */         }
/*  680:     */       }
/*  681:     */       catch (Throwable localThrowable14)
/*  682:     */       {
/*  683: 627 */         localThrowable14.printStackTrace(Jaguar.log);
/*  684: 628 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/*  685: 629 */         return localThrowable14.getClass().getName();
/*  686:     */       }
/*  687:     */     case 14: 
/*  688:     */       try
/*  689:     */       {
/*  690: 638 */         String str2 = local_ServerRequest.read_string();
/*  691: 639 */         localObject1 = localOFX151BPWServicesBean
/*  692: 640 */           .getCustomerByFI(
/*  693: 641 */           str2);
/*  694: 643 */         if (local_ServerRequest.isRMI()) {
/*  695: 643 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*  696:     */         } else {
/*  697: 643 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*  698:     */         }
/*  699:     */       }
/*  700:     */       catch (Throwable localThrowable15)
/*  701:     */       {
/*  702: 647 */         localThrowable15.printStackTrace(Jaguar.log);
/*  703: 648 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/*  704: 649 */         return localThrowable15.getClass().getName();
/*  705:     */       }
/*  706:     */     case 15: 
/*  707:     */       try
/*  708:     */       {
/*  709: 658 */         String str3 = local_ServerRequest.read_string();
/*  710: 659 */         localObject1 = localOFX151BPWServicesBean
/*  711: 660 */           .getCustomerByCategory(
/*  712: 661 */           str3);
/*  713: 663 */         if (local_ServerRequest.isRMI()) {
/*  714: 663 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*  715:     */         } else {
/*  716: 663 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*  717:     */         }
/*  718:     */       }
/*  719:     */       catch (Throwable localThrowable16)
/*  720:     */       {
/*  721: 667 */         localThrowable16.printStackTrace(Jaguar.log);
/*  722: 668 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/*  723: 669 */         return localThrowable16.getClass().getName();
/*  724:     */       }
/*  725:     */     case 16: 
/*  726:     */       try
/*  727:     */       {
/*  728: 678 */         String str4 = local_ServerRequest.read_string();
/*  729: 679 */         localObject1 = localOFX151BPWServicesBean
/*  730: 680 */           .getCustomerByGroup(
/*  731: 681 */           str4);
/*  732: 683 */         if (local_ServerRequest.isRMI()) {
/*  733: 683 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*  734:     */         } else {
/*  735: 683 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*  736:     */         }
/*  737:     */       }
/*  738:     */       catch (Throwable localThrowable17)
/*  739:     */       {
/*  740: 687 */         localThrowable17.printStackTrace(Jaguar.log);
/*  741: 688 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/*  742: 689 */         return localThrowable17.getClass().getName();
/*  743:     */       }
/*  744:     */     case 17: 
/*  745:     */       try
/*  746:     */       {
/*  747: 698 */         String str5 = local_ServerRequest.read_string();
/*  748:     */         
/*  749: 700 */         localObject1 = local_ServerRequest.read_string();
/*  750: 701 */         localObject3 = localOFX151BPWServicesBean
/*  751: 702 */           .getCustomerByTypeAndFI(
/*  752: 703 */           str5, 
/*  753: 704 */           (String)localObject1);
/*  754: 706 */         if (local_ServerRequest.isRMI()) {
/*  755: 706 */           local_ServerRequest.write_value(localObject3, new CustomerInfo[0].getClass());
/*  756:     */         } else {
/*  757: 706 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject3);
/*  758:     */         }
/*  759:     */       }
/*  760:     */       catch (Throwable localThrowable18)
/*  761:     */       {
/*  762: 710 */         localThrowable18.printStackTrace(Jaguar.log);
/*  763: 711 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/*  764: 712 */         return localThrowable18.getClass().getName();
/*  765:     */       }
/*  766:     */     case 18: 
/*  767:     */       try
/*  768:     */       {
/*  769: 721 */         String str6 = local_ServerRequest.read_string();
/*  770:     */         
/*  771: 723 */         localObject1 = local_ServerRequest.read_string();
/*  772: 724 */         localObject3 = localOFX151BPWServicesBean
/*  773: 725 */           .getCustomerByCategoryAndFI(
/*  774: 726 */           str6, 
/*  775: 727 */           (String)localObject1);
/*  776: 729 */         if (local_ServerRequest.isRMI()) {
/*  777: 729 */           local_ServerRequest.write_value(localObject3, new CustomerInfo[0].getClass());
/*  778:     */         } else {
/*  779: 729 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject3);
/*  780:     */         }
/*  781:     */       }
/*  782:     */       catch (Throwable localThrowable19)
/*  783:     */       {
/*  784: 733 */         localThrowable19.printStackTrace(Jaguar.log);
/*  785: 734 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/*  786: 735 */         return localThrowable19.getClass().getName();
/*  787:     */       }
/*  788:     */     case 19: 
/*  789:     */       try
/*  790:     */       {
/*  791: 744 */         String str7 = local_ServerRequest.read_string();
/*  792:     */         
/*  793: 746 */         localObject1 = local_ServerRequest.read_string();
/*  794: 747 */         localObject3 = localOFX151BPWServicesBean
/*  795: 748 */           .getCustomerByGroupAndFI(
/*  796: 749 */           str7, 
/*  797: 750 */           (String)localObject1);
/*  798: 752 */         if (local_ServerRequest.isRMI()) {
/*  799: 752 */           local_ServerRequest.write_value(localObject3, new CustomerInfo[0].getClass());
/*  800:     */         } else {
/*  801: 752 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject3);
/*  802:     */         }
/*  803:     */       }
/*  804:     */       catch (Throwable localThrowable20)
/*  805:     */       {
/*  806: 756 */         localThrowable20.printStackTrace(Jaguar.log);
/*  807: 757 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/*  808: 758 */         return localThrowable20.getClass().getName();
/*  809:     */       }
/*  810:     */     case 20: 
/*  811:     */       try
/*  812:     */       {
/*  813: 766 */         PayeeInfo[] arrayOfPayeeInfo = localOFX151BPWServicesBean
/*  814: 767 */           .getLinkedPayees();
/*  815: 769 */         if (local_ServerRequest.isRMI()) {
/*  816: 769 */           local_ServerRequest.write_value(arrayOfPayeeInfo, new PayeeInfo[0].getClass());
/*  817:     */         } else {
/*  818: 769 */           PayeeInfoSeqHelper.write(paramOutputStream, arrayOfPayeeInfo);
/*  819:     */         }
/*  820:     */       }
/*  821:     */       catch (Throwable localThrowable21)
/*  822:     */       {
/*  823: 773 */         localThrowable21.printStackTrace(Jaguar.log);
/*  824: 774 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/*  825: 775 */         return localThrowable21.getClass().getName();
/*  826:     */       }
/*  827:     */     case 21: 
/*  828:     */       try
/*  829:     */       {
/*  830: 784 */         int i = paramInputStream.read_long();
/*  831: 785 */         localObject1 = localOFX151BPWServicesBean
/*  832: 786 */           .getMostUsedPayees(
/*  833: 787 */           i);
/*  834: 789 */         if (local_ServerRequest.isRMI()) {
/*  835: 789 */           local_ServerRequest.write_value(localObject1, new PayeeInfo[0].getClass());
/*  836:     */         } else {
/*  837: 789 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject1);
/*  838:     */         }
/*  839:     */       }
/*  840:     */       catch (Throwable localThrowable22)
/*  841:     */       {
/*  842: 793 */         localThrowable22.printStackTrace(Jaguar.log);
/*  843: 794 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/*  844: 795 */         return localThrowable22.getClass().getName();
/*  845:     */       }
/*  846:     */     case 22: 
/*  847:     */       try
/*  848:     */       {
/*  849: 804 */         String str8 = local_ServerRequest.read_string();
/*  850: 805 */         localObject1 = localOFX151BPWServicesBean
/*  851: 806 */           .getPreferredPayees(
/*  852: 807 */           str8);
/*  853: 809 */         if (local_ServerRequest.isRMI()) {
/*  854: 809 */           local_ServerRequest.write_value(localObject1, new PayeeInfo[0].getClass());
/*  855:     */         } else {
/*  856: 809 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject1);
/*  857:     */         }
/*  858:     */       }
/*  859:     */       catch (Throwable localThrowable23)
/*  860:     */       {
/*  861: 813 */         localThrowable23.printStackTrace(Jaguar.log);
/*  862: 814 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/*  863: 815 */         return localThrowable23.getClass().getName();
/*  864:     */       }
/*  865:     */     case 23: 
/*  866:     */       try
/*  867:     */       {
/*  868: 824 */         String str9 = local_ServerRequest.read_string();
/*  869:     */         
/*  870: 826 */         k = paramInputStream.read_long();
/*  871: 827 */         localObject3 = localOFX151BPWServicesBean
/*  872: 828 */           .getPendingPmtsByCustomerID(
/*  873: 829 */           str9, 
/*  874: 830 */           k);
/*  875: 832 */         if (local_ServerRequest.isRMI()) {
/*  876: 832 */           local_ServerRequest.write_value(localObject3, new TypePmtSyncRsV1[0].getClass());
/*  877:     */         } else {
/*  878: 832 */           TypePmtSyncRsV1SeqHelper.write(paramOutputStream, (TypePmtSyncRsV1[])localObject3);
/*  879:     */         }
/*  880:     */       }
/*  881:     */       catch (Throwable localThrowable24)
/*  882:     */       {
/*  883: 836 */         localThrowable24.printStackTrace(Jaguar.log);
/*  884: 837 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/*  885: 838 */         return localThrowable24.getClass().getName();
/*  886:     */       }
/*  887:     */     case 24: 
/*  888:     */       try
/*  889:     */       {
/*  890: 847 */         String str10 = local_ServerRequest.read_string();
/*  891:     */         
/*  892: 849 */         k = paramInputStream.read_long();
/*  893:     */         
/*  894: 851 */         int n = paramInputStream.read_long();
/*  895: 852 */         localObject5 = localOFX151BPWServicesBean
/*  896: 853 */           .getPendingPmtsAndHistoryByCustomerID(
/*  897: 854 */           str10, 
/*  898: 855 */           k, 
/*  899: 856 */           n);
/*  900: 858 */         if (local_ServerRequest.isRMI()) {
/*  901: 858 */           local_ServerRequest.write_value(localObject5, new TypePmtSyncRsV1[0].getClass());
/*  902:     */         } else {
/*  903: 858 */           TypePmtSyncRsV1SeqHelper.write(paramOutputStream, (TypePmtSyncRsV1[])localObject5);
/*  904:     */         }
/*  905:     */       }
/*  906:     */       catch (Throwable localThrowable25)
/*  907:     */       {
/*  908: 862 */         localThrowable25.printStackTrace(Jaguar.log);
/*  909: 863 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/*  910: 864 */         return localThrowable25.getClass().getName();
/*  911:     */       }
/*  912:     */     case 25: 
/*  913:     */       try
/*  914:     */       {
/*  915: 873 */         String str11 = local_ServerRequest.read_string();
/*  916:     */         
/*  917: 875 */         k = paramInputStream.read_long();
/*  918: 876 */         TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV1 = localOFX151BPWServicesBean
/*  919: 877 */           .getPendingIntrasByCustomerID(
/*  920: 878 */           str11, 
/*  921: 879 */           k);
/*  922: 881 */         if (local_ServerRequest.isRMI()) {
/*  923: 881 */           local_ServerRequest.write_value(arrayOfTypeIntraSyncRsV1, new TypeIntraSyncRsV1[0].getClass());
/*  924:     */         } else {
/*  925: 881 */           TypeIntraSyncRsV1SeqHelper.write(paramOutputStream, arrayOfTypeIntraSyncRsV1);
/*  926:     */         }
/*  927:     */       }
/*  928:     */       catch (Throwable localThrowable26)
/*  929:     */       {
/*  930: 885 */         localThrowable26.printStackTrace(Jaguar.log);
/*  931: 886 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/*  932: 887 */         return localThrowable26.getClass().getName();
/*  933:     */       }
/*  934:     */     case 26: 
/*  935:     */       try
/*  936:     */       {
/*  937: 896 */         String str12 = local_ServerRequest.read_string();
/*  938:     */         
/*  939: 898 */         k = paramInputStream.read_long();
/*  940:     */         
/*  941: 900 */         i1 = paramInputStream.read_long();
/*  942: 901 */         localObject5 = localOFX151BPWServicesBean
/*  943: 902 */           .getPendingIntrasAndHistoryByCustomerID(
/*  944: 903 */           str12, 
/*  945: 904 */           k, 
/*  946: 905 */           i1);
/*  947: 907 */         if (local_ServerRequest.isRMI()) {
/*  948: 907 */           local_ServerRequest.write_value(localObject5, new TypeIntraSyncRsV1[0].getClass());
/*  949:     */         } else {
/*  950: 907 */           TypeIntraSyncRsV1SeqHelper.write(paramOutputStream, (TypeIntraSyncRsV1[])localObject5);
/*  951:     */         }
/*  952:     */       }
/*  953:     */       catch (Throwable localThrowable27)
/*  954:     */       {
/*  955: 911 */         localThrowable27.printStackTrace(Jaguar.log);
/*  956: 912 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/*  957: 913 */         return localThrowable27.getClass().getName();
/*  958:     */       }
/*  959:     */     case 27: 
/*  960:     */       try
/*  961:     */       {
/*  962:     */         TypePmtSyncRqV1 localTypePmtSyncRqV11;
/*  963: 922 */         if (local_ServerRequest.isRMI()) {
/*  964: 922 */           localTypePmtSyncRqV11 = (TypePmtSyncRqV1)local_ServerRequest.read_value(TypePmtSyncRqV1.class);
/*  965:     */         } else {
/*  966: 922 */           localTypePmtSyncRqV11 = TypePmtSyncRqV1Helper.read(paramInputStream);
/*  967:     */         }
/*  968: 924 */         if (local_ServerRequest.isRMI()) {
/*  969: 924 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/*  970:     */         } else {
/*  971: 924 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/*  972:     */         }
/*  973: 926 */         i1 = paramInputStream.read_long();
/*  974: 927 */         localObject5 = localOFX151BPWServicesBean
/*  975: 928 */           .getPendingPmts(
/*  976: 929 */           localTypePmtSyncRqV11, 
/*  977: 930 */           (TypeUserData)localObject2, 
/*  978: 931 */           i1);
/*  979: 933 */         if (local_ServerRequest.isRMI()) {
/*  980: 933 */           local_ServerRequest.write_value(localObject5, TypePmtSyncRsV1.class);
/*  981:     */         } else {
/*  982: 933 */           TypePmtSyncRsV1Helper.write(paramOutputStream, (TypePmtSyncRsV1)localObject5);
/*  983:     */         }
/*  984:     */       }
/*  985:     */       catch (Throwable localThrowable28)
/*  986:     */       {
/*  987: 937 */         localThrowable28.printStackTrace(Jaguar.log);
/*  988: 938 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/*  989: 939 */         return localThrowable28.getClass().getName();
/*  990:     */       }
/*  991:     */     case 28: 
/*  992:     */       try
/*  993:     */       {
/*  994:     */         TypeIntraSyncRqV1 localTypeIntraSyncRqV11;
/*  995: 948 */         if (local_ServerRequest.isRMI()) {
/*  996: 948 */           localTypeIntraSyncRqV11 = (TypeIntraSyncRqV1)local_ServerRequest.read_value(TypeIntraSyncRqV1.class);
/*  997:     */         } else {
/*  998: 948 */           localTypeIntraSyncRqV11 = TypeIntraSyncRqV1Helper.read(paramInputStream);
/*  999:     */         }
/* 1000: 950 */         if (local_ServerRequest.isRMI()) {
/* 1001: 950 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1002:     */         } else {
/* 1003: 950 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1004:     */         }
/* 1005: 952 */         i1 = paramInputStream.read_long();
/* 1006: 953 */         localObject5 = localOFX151BPWServicesBean
/* 1007: 954 */           .getPendingIntras(
/* 1008: 955 */           localTypeIntraSyncRqV11, 
/* 1009: 956 */           (TypeUserData)localObject2, 
/* 1010: 957 */           i1);
/* 1011: 959 */         if (local_ServerRequest.isRMI()) {
/* 1012: 959 */           local_ServerRequest.write_value(localObject5, TypeIntraSyncRsV1.class);
/* 1013:     */         } else {
/* 1014: 959 */           TypeIntraSyncRsV1Helper.write(paramOutputStream, (TypeIntraSyncRsV1)localObject5);
/* 1015:     */         }
/* 1016:     */       }
/* 1017:     */       catch (Throwable localThrowable29)
/* 1018:     */       {
/* 1019: 963 */         localThrowable29.printStackTrace(Jaguar.log);
/* 1020: 964 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/* 1021: 965 */         return localThrowable29.getClass().getName();
/* 1022:     */       }
/* 1023:     */     case 29: 
/* 1024:     */       try
/* 1025:     */       {
/* 1026: 974 */         String str13 = local_ServerRequest.read_string();
/* 1027: 975 */         localObject2 = localOFX151BPWServicesBean
/* 1028: 976 */           .getPmtStatus(
/* 1029: 977 */           str13);
/* 1030:     */         
/* 1031: 979 */         local_ServerRequest.write_string((String)localObject2);
/* 1032:     */       }
/* 1033:     */       catch (Throwable localThrowable30)
/* 1034:     */       {
/* 1035: 983 */         localThrowable30.printStackTrace(Jaguar.log);
/* 1036: 984 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/* 1037: 985 */         return localThrowable30.getClass().getName();
/* 1038:     */       }
/* 1039:     */     case 30: 
/* 1040:     */       try
/* 1041:     */       {
/* 1042: 994 */         String str14 = local_ServerRequest.read_string();
/* 1043:     */         
/* 1044: 996 */         localObject2 = local_ServerRequest.read_string();
/* 1045: 997 */         boolean bool = localOFX151BPWServicesBean
/* 1046: 998 */           .checkPayeeEditMask(
/* 1047: 999 */           str14, 
/* 1048:1000 */           (String)localObject2);
/* 1049:     */         
/* 1050:1002 */         paramOutputStream.write_boolean(bool);
/* 1051:     */       }
/* 1052:     */       catch (Throwable localThrowable31)
/* 1053:     */       {
/* 1054:1006 */         localThrowable31.printStackTrace(Jaguar.log);
/* 1055:1007 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/* 1056:1008 */         return localThrowable31.getClass().getName();
/* 1057:     */       }
/* 1058:     */     case 31: 
/* 1059:     */       try
/* 1060:     */       {
/* 1061:     */         IntraTrnRslt[] arrayOfIntraTrnRslt;
/* 1062:1017 */         if (local_ServerRequest.isRMI()) {
/* 1063:1017 */           arrayOfIntraTrnRslt = (IntraTrnRslt[])local_ServerRequest.read_value(new IntraTrnRslt[0].getClass());
/* 1064:     */         } else {
/* 1065:1017 */           arrayOfIntraTrnRslt = IntraTrnRsltSeqHelper.read(paramInputStream);
/* 1066:     */         }
/* 1067:1019 */         localOFX151BPWServicesBean.processIntraTrnRslt(
/* 1068:1020 */           arrayOfIntraTrnRslt);
/* 1069:     */       }
/* 1070:     */       catch (Throwable localThrowable32)
/* 1071:     */       {
/* 1072:1025 */         localThrowable32.printStackTrace(Jaguar.log);
/* 1073:1026 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/* 1074:1027 */         return localThrowable32.getClass().getName();
/* 1075:     */       }
/* 1076:     */     case 32: 
/* 1077:     */       try
/* 1078:     */       {
/* 1079:     */         PmtTrnRslt[] arrayOfPmtTrnRslt;
/* 1080:1036 */         if (local_ServerRequest.isRMI()) {
/* 1081:1036 */           arrayOfPmtTrnRslt = (PmtTrnRslt[])local_ServerRequest.read_value(new PmtTrnRslt[0].getClass());
/* 1082:     */         } else {
/* 1083:1036 */           arrayOfPmtTrnRslt = PmtTrnRsltSeqHelper.read(paramInputStream);
/* 1084:     */         }
/* 1085:1038 */         localOFX151BPWServicesBean.processPmtTrnRslt(
/* 1086:1039 */           arrayOfPmtTrnRslt);
/* 1087:     */       }
/* 1088:     */       catch (Throwable localThrowable33)
/* 1089:     */       {
/* 1090:1044 */         localThrowable33.printStackTrace(Jaguar.log);
/* 1091:1045 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/* 1092:1046 */         return localThrowable33.getClass().getName();
/* 1093:     */       }
/* 1094:     */     case 33: 
/* 1095:     */       try
/* 1096:     */       {
/* 1097:     */         CustPayeeRslt[] arrayOfCustPayeeRslt;
/* 1098:1055 */         if (local_ServerRequest.isRMI()) {
/* 1099:1055 */           arrayOfCustPayeeRslt = (CustPayeeRslt[])local_ServerRequest.read_value(new CustPayeeRslt[0].getClass());
/* 1100:     */         } else {
/* 1101:1055 */           arrayOfCustPayeeRslt = CustPayeeRsltSeqHelper.read(paramInputStream);
/* 1102:     */         }
/* 1103:1057 */         localOFX151BPWServicesBean.processCustPayeeRslt(
/* 1104:1058 */           arrayOfCustPayeeRslt);
/* 1105:     */       }
/* 1106:     */       catch (Throwable localThrowable34)
/* 1107:     */       {
/* 1108:1063 */         localThrowable34.printStackTrace(Jaguar.log);
/* 1109:1064 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/* 1110:1065 */         return localThrowable34.getClass().getName();
/* 1111:     */       }
/* 1112:     */     case 34: 
/* 1113:     */       try
/* 1114:     */       {
/* 1115:     */         FundsAllocRslt[] arrayOfFundsAllocRslt1;
/* 1116:1074 */         if (local_ServerRequest.isRMI()) {
/* 1117:1074 */           arrayOfFundsAllocRslt1 = (FundsAllocRslt[])local_ServerRequest.read_value(new FundsAllocRslt[0].getClass());
/* 1118:     */         } else {
/* 1119:1074 */           arrayOfFundsAllocRslt1 = FundsAllocRsltSeqHelper.read(paramInputStream);
/* 1120:     */         }
/* 1121:1076 */         localOFX151BPWServicesBean.processFundAllocRslt(
/* 1122:1077 */           arrayOfFundsAllocRslt1);
/* 1123:     */       }
/* 1124:     */       catch (Throwable localThrowable35)
/* 1125:     */       {
/* 1126:1082 */         localThrowable35.printStackTrace(Jaguar.log);
/* 1127:1083 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/* 1128:1084 */         return localThrowable35.getClass().getName();
/* 1129:     */       }
/* 1130:     */     case 35: 
/* 1131:     */       try
/* 1132:     */       {
/* 1133:     */         FundsAllocRslt[] arrayOfFundsAllocRslt2;
/* 1134:1093 */         if (local_ServerRequest.isRMI()) {
/* 1135:1093 */           arrayOfFundsAllocRslt2 = (FundsAllocRslt[])local_ServerRequest.read_value(new FundsAllocRslt[0].getClass());
/* 1136:     */         } else {
/* 1137:1093 */           arrayOfFundsAllocRslt2 = FundsAllocRsltSeqHelper.read(paramInputStream);
/* 1138:     */         }
/* 1139:1095 */         localOFX151BPWServicesBean.processFundRevertRslt(
/* 1140:1096 */           arrayOfFundsAllocRslt2);
/* 1141:     */       }
/* 1142:     */       catch (Throwable localThrowable36)
/* 1143:     */       {
/* 1144:1101 */         localThrowable36.printStackTrace(Jaguar.log);
/* 1145:1102 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/* 1146:1103 */         return localThrowable36.getClass().getName();
/* 1147:     */       }
/* 1148:     */     case 36: 
/* 1149:     */       try
/* 1150:     */       {
/* 1151:     */         PayeeRslt[] arrayOfPayeeRslt;
/* 1152:1112 */         if (local_ServerRequest.isRMI()) {
/* 1153:1112 */           arrayOfPayeeRslt = (PayeeRslt[])local_ServerRequest.read_value(new PayeeRslt[0].getClass());
/* 1154:     */         } else {
/* 1155:1112 */           arrayOfPayeeRslt = PayeeRsltSeqHelper.read(paramInputStream);
/* 1156:     */         }
/* 1157:1114 */         localOFX151BPWServicesBean.processPayeeRslt(
/* 1158:1115 */           arrayOfPayeeRslt);
/* 1159:     */       }
/* 1160:     */       catch (Throwable localThrowable37)
/* 1161:     */       {
/* 1162:1120 */         localThrowable37.printStackTrace(Jaguar.log);
/* 1163:1121 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable37, true);
/* 1164:1122 */         return localThrowable37.getClass().getName();
/* 1165:     */       }
/* 1166:     */     case 37: 
/* 1167:     */       try
/* 1168:     */       {
/* 1169:1131 */         PayeeInfo localPayeeInfo1 = (PayeeInfo)local_ServerRequest.read_value(PayeeInfo.class);
/* 1170:1132 */         localObject2 = localOFX151BPWServicesBean
/* 1171:1133 */           .addPayeeFromBackend(
/* 1172:1134 */           localPayeeInfo1);
/* 1173:     */         
/* 1174:1136 */         local_ServerRequest.write_string((String)localObject2);
/* 1175:     */       }
/* 1176:     */       catch (Throwable localThrowable38)
/* 1177:     */       {
/* 1178:1140 */         localThrowable38.printStackTrace(Jaguar.log);
/* 1179:1141 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable38, true);
/* 1180:1142 */         return localThrowable38.getClass().getName();
/* 1181:     */       }
/* 1182:     */     case 38: 
/* 1183:     */       try
/* 1184:     */       {
/* 1185:1151 */         PayeeInfo localPayeeInfo2 = (PayeeInfo)local_ServerRequest.read_value(PayeeInfo.class);
/* 1186:1152 */         localObject2 = localOFX151BPWServicesBean
/* 1187:1153 */           .updatePayeeFromBackend(
/* 1188:1154 */           localPayeeInfo2);
/* 1189:1156 */         if (local_ServerRequest.isRMI()) {
/* 1190:1156 */           local_ServerRequest.write_value(localObject2, new PayeeInfo[0].getClass());
/* 1191:     */         } else {
/* 1192:1156 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject2);
/* 1193:     */         }
/* 1194:     */       }
/* 1195:     */       catch (Throwable localThrowable39)
/* 1196:     */       {
/* 1197:1160 */         localThrowable39.printStackTrace(Jaguar.log);
/* 1198:1161 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable39, true);
/* 1199:1162 */         return localThrowable39.getClass().getName();
/* 1200:     */       }
/* 1201:     */     case 39: 
/* 1202:     */       try
/* 1203:     */       {
/* 1204:1171 */         PayeeRouteInfo localPayeeRouteInfo = (PayeeRouteInfo)local_ServerRequest.read_value(PayeeRouteInfo.class);
/* 1205:1172 */         localOFX151BPWServicesBean
/* 1206:1173 */           .addPayeeRouteInfo(
/* 1207:1174 */           localPayeeRouteInfo);
/* 1208:     */       }
/* 1209:     */       catch (Throwable localThrowable40)
/* 1210:     */       {
/* 1211:1179 */         localThrowable40.printStackTrace(Jaguar.log);
/* 1212:1180 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable40, true);
/* 1213:1181 */         return localThrowable40.getClass().getName();
/* 1214:     */       }
/* 1215:     */     case 40: 
/* 1216:     */       try
/* 1217:     */       {
/* 1218:     */         TypeIntraSyncRqV1 localTypeIntraSyncRqV12;
/* 1219:1190 */         if (local_ServerRequest.isRMI()) {
/* 1220:1190 */           localTypeIntraSyncRqV12 = (TypeIntraSyncRqV1)local_ServerRequest.read_value(TypeIntraSyncRqV1.class);
/* 1221:     */         } else {
/* 1222:1190 */           localTypeIntraSyncRqV12 = TypeIntraSyncRqV1Helper.read(paramInputStream);
/* 1223:     */         }
/* 1224:1192 */         if (local_ServerRequest.isRMI()) {
/* 1225:1192 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1226:     */         } else {
/* 1227:1192 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1228:     */         }
/* 1229:1193 */         localObject4 = 
/* 1230:1194 */           localOFX151BPWServicesBean.processIntraSyncRqV1(
/* 1231:1195 */           localTypeIntraSyncRqV12, 
/* 1232:1196 */           (TypeUserData)localObject2);
/* 1233:1198 */         if (local_ServerRequest.isRMI()) {
/* 1234:1198 */           local_ServerRequest.write_value(localObject4, TypeIntraSyncRsV1.class);
/* 1235:     */         } else {
/* 1236:1198 */           TypeIntraSyncRsV1Helper.write(paramOutputStream, (TypeIntraSyncRsV1)localObject4);
/* 1237:     */         }
/* 1238:     */       }
/* 1239:     */       catch (Throwable localThrowable41)
/* 1240:     */       {
/* 1241:1202 */         localThrowable41.printStackTrace(Jaguar.log);
/* 1242:1203 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable41, true);
/* 1243:1204 */         return localThrowable41.getClass().getName();
/* 1244:     */       }
/* 1245:     */     case 41: 
/* 1246:     */       try
/* 1247:     */       {
/* 1248:     */         TypeIntraTrnRqV1 localTypeIntraTrnRqV1;
/* 1249:1213 */         if (local_ServerRequest.isRMI()) {
/* 1250:1213 */           localTypeIntraTrnRqV1 = (TypeIntraTrnRqV1)local_ServerRequest.read_value(TypeIntraTrnRqV1.class);
/* 1251:     */         } else {
/* 1252:1213 */           localTypeIntraTrnRqV1 = TypeIntraTrnRqV1Helper.read(paramInputStream);
/* 1253:     */         }
/* 1254:1215 */         if (local_ServerRequest.isRMI()) {
/* 1255:1215 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1256:     */         } else {
/* 1257:1215 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1258:     */         }
/* 1259:1216 */         localObject4 = 
/* 1260:1217 */           localOFX151BPWServicesBean.processIntraTrnRqV1(
/* 1261:1218 */           localTypeIntraTrnRqV1, 
/* 1262:1219 */           (TypeUserData)localObject2);
/* 1263:1221 */         if (local_ServerRequest.isRMI()) {
/* 1264:1221 */           local_ServerRequest.write_value(localObject4, TypeIntraTrnRsV1.class);
/* 1265:     */         } else {
/* 1266:1221 */           TypeIntraTrnRsV1Helper.write(paramOutputStream, (TypeIntraTrnRsV1)localObject4);
/* 1267:     */         }
/* 1268:     */       }
/* 1269:     */       catch (Throwable localThrowable42)
/* 1270:     */       {
/* 1271:1225 */         localThrowable42.printStackTrace(Jaguar.log);
/* 1272:1226 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable42, true);
/* 1273:1227 */         return localThrowable42.getClass().getName();
/* 1274:     */       }
/* 1275:     */     case 42: 
/* 1276:     */       try
/* 1277:     */       {
/* 1278:     */         TypePayeeSyncRqV1 localTypePayeeSyncRqV1;
/* 1279:1236 */         if (local_ServerRequest.isRMI()) {
/* 1280:1236 */           localTypePayeeSyncRqV1 = (TypePayeeSyncRqV1)local_ServerRequest.read_value(TypePayeeSyncRqV1.class);
/* 1281:     */         } else {
/* 1282:1236 */           localTypePayeeSyncRqV1 = TypePayeeSyncRqV1Helper.read(paramInputStream);
/* 1283:     */         }
/* 1284:1238 */         if (local_ServerRequest.isRMI()) {
/* 1285:1238 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1286:     */         } else {
/* 1287:1238 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1288:     */         }
/* 1289:1239 */         localObject4 = 
/* 1290:1240 */           localOFX151BPWServicesBean.processPayeeSyncRqV1(
/* 1291:1241 */           localTypePayeeSyncRqV1, 
/* 1292:1242 */           (TypeUserData)localObject2);
/* 1293:1244 */         if (local_ServerRequest.isRMI()) {
/* 1294:1244 */           local_ServerRequest.write_value(localObject4, TypePayeeSyncRsV1.class);
/* 1295:     */         } else {
/* 1296:1244 */           TypePayeeSyncRsV1Helper.write(paramOutputStream, (TypePayeeSyncRsV1)localObject4);
/* 1297:     */         }
/* 1298:     */       }
/* 1299:     */       catch (Throwable localThrowable43)
/* 1300:     */       {
/* 1301:1248 */         localThrowable43.printStackTrace(Jaguar.log);
/* 1302:1249 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable43, true);
/* 1303:1250 */         return localThrowable43.getClass().getName();
/* 1304:     */       }
/* 1305:     */     case 43: 
/* 1306:     */       try
/* 1307:     */       {
/* 1308:     */         TypePayeeTrnRqV1 localTypePayeeTrnRqV1;
/* 1309:1259 */         if (local_ServerRequest.isRMI()) {
/* 1310:1259 */           localTypePayeeTrnRqV1 = (TypePayeeTrnRqV1)local_ServerRequest.read_value(TypePayeeTrnRqV1.class);
/* 1311:     */         } else {
/* 1312:1259 */           localTypePayeeTrnRqV1 = TypePayeeTrnRqV1Helper.read(paramInputStream);
/* 1313:     */         }
/* 1314:1261 */         if (local_ServerRequest.isRMI()) {
/* 1315:1261 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1316:     */         } else {
/* 1317:1261 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1318:     */         }
/* 1319:1262 */         localObject4 = 
/* 1320:1263 */           localOFX151BPWServicesBean.processPayeeTrnRqV1(
/* 1321:1264 */           localTypePayeeTrnRqV1, 
/* 1322:1265 */           (TypeUserData)localObject2);
/* 1323:1267 */         if (local_ServerRequest.isRMI()) {
/* 1324:1267 */           local_ServerRequest.write_value(localObject4, TypePayeeTrnRsV1.class);
/* 1325:     */         } else {
/* 1326:1267 */           TypePayeeTrnRsV1Helper.write(paramOutputStream, (TypePayeeTrnRsV1)localObject4);
/* 1327:     */         }
/* 1328:     */       }
/* 1329:     */       catch (Throwable localThrowable44)
/* 1330:     */       {
/* 1331:1271 */         localThrowable44.printStackTrace(Jaguar.log);
/* 1332:1272 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable44, true);
/* 1333:1273 */         return localThrowable44.getClass().getName();
/* 1334:     */       }
/* 1335:     */     case 44: 
/* 1336:     */       try
/* 1337:     */       {
/* 1338:     */         TypePmtInqTrnRqV1 localTypePmtInqTrnRqV1;
/* 1339:1282 */         if (local_ServerRequest.isRMI()) {
/* 1340:1282 */           localTypePmtInqTrnRqV1 = (TypePmtInqTrnRqV1)local_ServerRequest.read_value(TypePmtInqTrnRqV1.class);
/* 1341:     */         } else {
/* 1342:1282 */           localTypePmtInqTrnRqV1 = TypePmtInqTrnRqV1Helper.read(paramInputStream);
/* 1343:     */         }
/* 1344:1284 */         if (local_ServerRequest.isRMI()) {
/* 1345:1284 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1346:     */         } else {
/* 1347:1284 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1348:     */         }
/* 1349:1285 */         localObject4 = 
/* 1350:1286 */           localOFX151BPWServicesBean.processPmtInqTrnRqV1(
/* 1351:1287 */           localTypePmtInqTrnRqV1, 
/* 1352:1288 */           (TypeUserData)localObject2);
/* 1353:1290 */         if (local_ServerRequest.isRMI()) {
/* 1354:1290 */           local_ServerRequest.write_value(localObject4, TypePmtInqTrnRsV1.class);
/* 1355:     */         } else {
/* 1356:1290 */           TypePmtInqTrnRsV1Helper.write(paramOutputStream, (TypePmtInqTrnRsV1)localObject4);
/* 1357:     */         }
/* 1358:     */       }
/* 1359:     */       catch (Throwable localThrowable45)
/* 1360:     */       {
/* 1361:1294 */         localThrowable45.printStackTrace(Jaguar.log);
/* 1362:1295 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable45, true);
/* 1363:1296 */         return localThrowable45.getClass().getName();
/* 1364:     */       }
/* 1365:     */     case 45: 
/* 1366:     */       try
/* 1367:     */       {
/* 1368:     */         TypePmtSyncRqV1 localTypePmtSyncRqV12;
/* 1369:1305 */         if (local_ServerRequest.isRMI()) {
/* 1370:1305 */           localTypePmtSyncRqV12 = (TypePmtSyncRqV1)local_ServerRequest.read_value(TypePmtSyncRqV1.class);
/* 1371:     */         } else {
/* 1372:1305 */           localTypePmtSyncRqV12 = TypePmtSyncRqV1Helper.read(paramInputStream);
/* 1373:     */         }
/* 1374:1307 */         if (local_ServerRequest.isRMI()) {
/* 1375:1307 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1376:     */         } else {
/* 1377:1307 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1378:     */         }
/* 1379:1308 */         localObject4 = 
/* 1380:1309 */           localOFX151BPWServicesBean.processPmtSyncRqV1(
/* 1381:1310 */           localTypePmtSyncRqV12, 
/* 1382:1311 */           (TypeUserData)localObject2);
/* 1383:1313 */         if (local_ServerRequest.isRMI()) {
/* 1384:1313 */           local_ServerRequest.write_value(localObject4, TypePmtSyncRsV1.class);
/* 1385:     */         } else {
/* 1386:1313 */           TypePmtSyncRsV1Helper.write(paramOutputStream, (TypePmtSyncRsV1)localObject4);
/* 1387:     */         }
/* 1388:     */       }
/* 1389:     */       catch (Throwable localThrowable46)
/* 1390:     */       {
/* 1391:1317 */         localThrowable46.printStackTrace(Jaguar.log);
/* 1392:1318 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable46, true);
/* 1393:1319 */         return localThrowable46.getClass().getName();
/* 1394:     */       }
/* 1395:     */     case 46: 
/* 1396:     */       try
/* 1397:     */       {
/* 1398:     */         TypePmtTrnRqV1 localTypePmtTrnRqV1;
/* 1399:1328 */         if (local_ServerRequest.isRMI()) {
/* 1400:1328 */           localTypePmtTrnRqV1 = (TypePmtTrnRqV1)local_ServerRequest.read_value(TypePmtTrnRqV1.class);
/* 1401:     */         } else {
/* 1402:1328 */           localTypePmtTrnRqV1 = TypePmtTrnRqV1Helper.read(paramInputStream);
/* 1403:     */         }
/* 1404:1330 */         if (local_ServerRequest.isRMI()) {
/* 1405:1330 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1406:     */         } else {
/* 1407:1330 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1408:     */         }
/* 1409:1331 */         localObject4 = 
/* 1410:1332 */           localOFX151BPWServicesBean.processPmtTrnRqV1(
/* 1411:1333 */           localTypePmtTrnRqV1, 
/* 1412:1334 */           (TypeUserData)localObject2);
/* 1413:1336 */         if (local_ServerRequest.isRMI()) {
/* 1414:1336 */           local_ServerRequest.write_value(localObject4, TypePmtTrnRsV1.class);
/* 1415:     */         } else {
/* 1416:1336 */           TypePmtTrnRsV1Helper.write(paramOutputStream, (TypePmtTrnRsV1)localObject4);
/* 1417:     */         }
/* 1418:     */       }
/* 1419:     */       catch (Throwable localThrowable47)
/* 1420:     */       {
/* 1421:1340 */         localThrowable47.printStackTrace(Jaguar.log);
/* 1422:1341 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable47, true);
/* 1423:1342 */         return localThrowable47.getClass().getName();
/* 1424:     */       }
/* 1425:     */     case 47: 
/* 1426:     */       try
/* 1427:     */       {
/* 1428:     */         TypeRecIntraSyncRqV1 localTypeRecIntraSyncRqV1;
/* 1429:1351 */         if (local_ServerRequest.isRMI()) {
/* 1430:1351 */           localTypeRecIntraSyncRqV1 = (TypeRecIntraSyncRqV1)local_ServerRequest.read_value(TypeRecIntraSyncRqV1.class);
/* 1431:     */         } else {
/* 1432:1351 */           localTypeRecIntraSyncRqV1 = TypeRecIntraSyncRqV1Helper.read(paramInputStream);
/* 1433:     */         }
/* 1434:1353 */         if (local_ServerRequest.isRMI()) {
/* 1435:1353 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1436:     */         } else {
/* 1437:1353 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1438:     */         }
/* 1439:1354 */         localObject4 = 
/* 1440:1355 */           localOFX151BPWServicesBean.processRecIntraSyncRqV1(
/* 1441:1356 */           localTypeRecIntraSyncRqV1, 
/* 1442:1357 */           (TypeUserData)localObject2);
/* 1443:1359 */         if (local_ServerRequest.isRMI()) {
/* 1444:1359 */           local_ServerRequest.write_value(localObject4, TypeRecIntraSyncRsV1.class);
/* 1445:     */         } else {
/* 1446:1359 */           TypeRecIntraSyncRsV1Helper.write(paramOutputStream, (TypeRecIntraSyncRsV1)localObject4);
/* 1447:     */         }
/* 1448:     */       }
/* 1449:     */       catch (Throwable localThrowable48)
/* 1450:     */       {
/* 1451:1363 */         localThrowable48.printStackTrace(Jaguar.log);
/* 1452:1364 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable48, true);
/* 1453:1365 */         return localThrowable48.getClass().getName();
/* 1454:     */       }
/* 1455:     */     case 48: 
/* 1456:     */       try
/* 1457:     */       {
/* 1458:     */         TypeRecIntraTrnRqV1 localTypeRecIntraTrnRqV1;
/* 1459:1374 */         if (local_ServerRequest.isRMI()) {
/* 1460:1374 */           localTypeRecIntraTrnRqV1 = (TypeRecIntraTrnRqV1)local_ServerRequest.read_value(TypeRecIntraTrnRqV1.class);
/* 1461:     */         } else {
/* 1462:1374 */           localTypeRecIntraTrnRqV1 = TypeRecIntraTrnRqV1Helper.read(paramInputStream);
/* 1463:     */         }
/* 1464:1376 */         if (local_ServerRequest.isRMI()) {
/* 1465:1376 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1466:     */         } else {
/* 1467:1376 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1468:     */         }
/* 1469:1377 */         localObject4 = 
/* 1470:1378 */           localOFX151BPWServicesBean.processRecIntraTrnRqV1(
/* 1471:1379 */           localTypeRecIntraTrnRqV1, 
/* 1472:1380 */           (TypeUserData)localObject2);
/* 1473:1382 */         if (local_ServerRequest.isRMI()) {
/* 1474:1382 */           local_ServerRequest.write_value(localObject4, TypeRecIntraTrnRsV1.class);
/* 1475:     */         } else {
/* 1476:1382 */           TypeRecIntraTrnRsV1Helper.write(paramOutputStream, (TypeRecIntraTrnRsV1)localObject4);
/* 1477:     */         }
/* 1478:     */       }
/* 1479:     */       catch (Throwable localThrowable49)
/* 1480:     */       {
/* 1481:1386 */         localThrowable49.printStackTrace(Jaguar.log);
/* 1482:1387 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable49, true);
/* 1483:1388 */         return localThrowable49.getClass().getName();
/* 1484:     */       }
/* 1485:     */     case 49: 
/* 1486:     */       try
/* 1487:     */       {
/* 1488:     */         TypeRecPmtSyncRqV1 localTypeRecPmtSyncRqV1;
/* 1489:1397 */         if (local_ServerRequest.isRMI()) {
/* 1490:1397 */           localTypeRecPmtSyncRqV1 = (TypeRecPmtSyncRqV1)local_ServerRequest.read_value(TypeRecPmtSyncRqV1.class);
/* 1491:     */         } else {
/* 1492:1397 */           localTypeRecPmtSyncRqV1 = TypeRecPmtSyncRqV1Helper.read(paramInputStream);
/* 1493:     */         }
/* 1494:1399 */         if (local_ServerRequest.isRMI()) {
/* 1495:1399 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1496:     */         } else {
/* 1497:1399 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1498:     */         }
/* 1499:1400 */         localObject4 = 
/* 1500:1401 */           localOFX151BPWServicesBean.processRecPmtSyncRqV1(
/* 1501:1402 */           localTypeRecPmtSyncRqV1, 
/* 1502:1403 */           (TypeUserData)localObject2);
/* 1503:1405 */         if (local_ServerRequest.isRMI()) {
/* 1504:1405 */           local_ServerRequest.write_value(localObject4, TypeRecPmtSyncRsV1.class);
/* 1505:     */         } else {
/* 1506:1405 */           TypeRecPmtSyncRsV1Helper.write(paramOutputStream, (TypeRecPmtSyncRsV1)localObject4);
/* 1507:     */         }
/* 1508:     */       }
/* 1509:     */       catch (Throwable localThrowable50)
/* 1510:     */       {
/* 1511:1409 */         localThrowable50.printStackTrace(Jaguar.log);
/* 1512:1410 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable50, true);
/* 1513:1411 */         return localThrowable50.getClass().getName();
/* 1514:     */       }
/* 1515:     */     case 50: 
/* 1516:     */       try
/* 1517:     */       {
/* 1518:     */         TypeRecPmtTrnRqV1 localTypeRecPmtTrnRqV1;
/* 1519:1420 */         if (local_ServerRequest.isRMI()) {
/* 1520:1420 */           localTypeRecPmtTrnRqV1 = (TypeRecPmtTrnRqV1)local_ServerRequest.read_value(TypeRecPmtTrnRqV1.class);
/* 1521:     */         } else {
/* 1522:1420 */           localTypeRecPmtTrnRqV1 = TypeRecPmtTrnRqV1Helper.read(paramInputStream);
/* 1523:     */         }
/* 1524:1422 */         if (local_ServerRequest.isRMI()) {
/* 1525:1422 */           localObject2 = (TypeUserData)local_ServerRequest.read_value(TypeUserData.class);
/* 1526:     */         } else {
/* 1527:1422 */           localObject2 = TypeUserDataHelper.read(paramInputStream);
/* 1528:     */         }
/* 1529:1423 */         localObject4 = 
/* 1530:1424 */           localOFX151BPWServicesBean.processRecPmtTrnRqV1(
/* 1531:1425 */           localTypeRecPmtTrnRqV1, 
/* 1532:1426 */           (TypeUserData)localObject2);
/* 1533:1428 */         if (local_ServerRequest.isRMI()) {
/* 1534:1428 */           local_ServerRequest.write_value(localObject4, TypeRecPmtTrnRsV1.class);
/* 1535:     */         } else {
/* 1536:1428 */           TypeRecPmtTrnRsV1Helper.write(paramOutputStream, (TypeRecPmtTrnRsV1)localObject4);
/* 1537:     */         }
/* 1538:     */       }
/* 1539:     */       catch (Throwable localThrowable51)
/* 1540:     */       {
/* 1541:1432 */         localThrowable51.printStackTrace(Jaguar.log);
/* 1542:1433 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable51, true);
/* 1543:1434 */         return localThrowable51.getClass().getName();
/* 1544:     */       }
/* 1545:     */     default: 
/* 1546:1440 */       return 
/* 1547:1441 */         invoke1(
/* 1548:1442 */         local_ServerRequest, 
/* 1549:1443 */         paramInputStream, 
/* 1550:1444 */         paramOutputStream, 
/* 1551:1445 */         localOFX151BPWServicesBean, 
/* 1552:1446 */         localInteger);
/* 1553:     */     }
/* 1554:1450 */     return null;
/* 1555:     */   }
/* 1556:     */   
/* 1557:     */   private static String invoke1(_ServerRequest param_ServerRequest, InputStream paramInputStream, OutputStream paramOutputStream, OFX151BPWServicesBean paramOFX151BPWServicesBean, Integer paramInteger)
/* 1558:     */   {
/* 1559:     */     Object localObject3;
/* 1560:     */     String[] arrayOfString8;
/* 1561:     */     PayeeInfo[] arrayOfPayeeInfo;
/* 1562:     */     Object localObject1;
/* 1563:     */     int n;
/* 1564:     */     int i1;
/* 1565:     */     int i2;
/* 1566:     */     Object localObject2;
/* 1567:     */     boolean bool;
/* 1568:1460 */     switch (paramInteger.intValue())
/* 1569:     */     {
/* 1570:     */     case 51: 
/* 1571:     */       try
/* 1572:     */       {
/* 1573:1467 */         String str1 = param_ServerRequest.read_string();
/* 1574:     */         
/* 1575:1469 */         int j = paramInputStream.read_long();
/* 1576:1470 */         localObject3 = paramOFX151BPWServicesBean
/* 1577:1471 */           .getPayeeNames(
/* 1578:1472 */           str1, 
/* 1579:1473 */           j);
/* 1580:1475 */         if (param_ServerRequest.isRMI()) {
/* 1581:1475 */           param_ServerRequest.write_value(localObject3, new String[0].getClass());
/* 1582:     */         } else {
/* 1583:1475 */           StringSeqHelper.write(paramOutputStream, (String[])localObject3);
/* 1584:     */         }
/* 1585:     */       }
/* 1586:     */       catch (Throwable localThrowable1)
/* 1587:     */       {
/* 1588:1479 */         localThrowable1.printStackTrace(Jaguar.log);
/* 1589:1480 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/* 1590:1481 */         return localThrowable1.getClass().getName();
/* 1591:     */       }
/* 1592:     */     case 52: 
/* 1593:     */       try
/* 1594:     */       {
/* 1595:1490 */         String str2 = param_ServerRequest.read_string();
/* 1596:1491 */         arrayOfString8 = paramOFX151BPWServicesBean
/* 1597:1492 */           .getPayeeNames(
/* 1598:1493 */           str2);
/* 1599:1495 */         if (param_ServerRequest.isRMI()) {
/* 1600:1495 */           param_ServerRequest.write_value(arrayOfString8, new String[0].getClass());
/* 1601:     */         } else {
/* 1602:1495 */           StringSeqHelper.write(paramOutputStream, arrayOfString8);
/* 1603:     */         }
/* 1604:     */       }
/* 1605:     */       catch (Throwable localThrowable2)
/* 1606:     */       {
/* 1607:1499 */         localThrowable2.printStackTrace(Jaguar.log);
/* 1608:1500 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/* 1609:1501 */         return localThrowable2.getClass().getName();
/* 1610:     */       }
/* 1611:     */     case 53: 
/* 1612:     */       try
/* 1613:     */       {
/* 1614:1510 */         String str3 = param_ServerRequest.read_string();
/* 1615:1511 */         arrayOfString8 = paramOFX151BPWServicesBean
/* 1616:1512 */           .getPayeeIDs(
/* 1617:1513 */           str3);
/* 1618:1515 */         if (param_ServerRequest.isRMI()) {
/* 1619:1515 */           param_ServerRequest.write_value(arrayOfString8, new String[0].getClass());
/* 1620:     */         } else {
/* 1621:1515 */           StringSeqHelper.write(paramOutputStream, arrayOfString8);
/* 1622:     */         }
/* 1623:     */       }
/* 1624:     */       catch (Throwable localThrowable3)
/* 1625:     */       {
/* 1626:1519 */         localThrowable3.printStackTrace(Jaguar.log);
/* 1627:1520 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/* 1628:1521 */         return localThrowable3.getClass().getName();
/* 1629:     */       }
/* 1630:     */     case 54: 
/* 1631:     */       try
/* 1632:     */       {
/* 1633:1530 */         String str4 = param_ServerRequest.read_string();
/* 1634:     */         
/* 1635:1532 */         int k = paramInputStream.read_long();
/* 1636:1533 */         localObject3 = paramOFX151BPWServicesBean
/* 1637:1534 */           .getPayees(
/* 1638:1535 */           str4, 
/* 1639:1536 */           k);
/* 1640:1538 */         if (param_ServerRequest.isRMI()) {
/* 1641:1538 */           param_ServerRequest.write_value(localObject3, new PayeeInfo[0].getClass());
/* 1642:     */         } else {
/* 1643:1538 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject3);
/* 1644:     */         }
/* 1645:     */       }
/* 1646:     */       catch (Throwable localThrowable4)
/* 1647:     */       {
/* 1648:1542 */         localThrowable4.printStackTrace(Jaguar.log);
/* 1649:1543 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/* 1650:1544 */         return localThrowable4.getClass().getName();
/* 1651:     */       }
/* 1652:     */     case 55: 
/* 1653:     */       try
/* 1654:     */       {
/* 1655:1553 */         String str5 = param_ServerRequest.read_string();
/* 1656:1554 */         arrayOfPayeeInfo = paramOFX151BPWServicesBean
/* 1657:1555 */           .getPayees(
/* 1658:1556 */           str5);
/* 1659:1558 */         if (param_ServerRequest.isRMI()) {
/* 1660:1558 */           param_ServerRequest.write_value(arrayOfPayeeInfo, new PayeeInfo[0].getClass());
/* 1661:     */         } else {
/* 1662:1558 */           PayeeInfoSeqHelper.write(paramOutputStream, arrayOfPayeeInfo);
/* 1663:     */         }
/* 1664:     */       }
/* 1665:     */       catch (Throwable localThrowable5)
/* 1666:     */       {
/* 1667:1562 */         localThrowable5.printStackTrace(Jaguar.log);
/* 1668:1563 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/* 1669:1564 */         return localThrowable5.getClass().getName();
/* 1670:     */       }
/* 1671:     */     case 56: 
/* 1672:     */       try
/* 1673:     */       {
/* 1674:1573 */         String str6 = param_ServerRequest.read_string();
/* 1675:1574 */         arrayOfPayeeInfo = paramOFX151BPWServicesBean
/* 1676:1575 */           .searchGlobalPayees(
/* 1677:1576 */           str6);
/* 1678:1578 */         if (param_ServerRequest.isRMI()) {
/* 1679:1578 */           param_ServerRequest.write_value(arrayOfPayeeInfo, new PayeeInfo[0].getClass());
/* 1680:     */         } else {
/* 1681:1578 */           PayeeInfoSeqHelper.write(paramOutputStream, arrayOfPayeeInfo);
/* 1682:     */         }
/* 1683:     */       }
/* 1684:     */       catch (Throwable localThrowable6)
/* 1685:     */       {
/* 1686:1582 */         localThrowable6.printStackTrace(Jaguar.log);
/* 1687:1583 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/* 1688:1584 */         return localThrowable6.getClass().getName();
/* 1689:     */       }
/* 1690:     */     case 57: 
/* 1691:     */       try
/* 1692:     */       {
/* 1693:1593 */         PayeeInfo localPayeeInfo1 = (PayeeInfo)param_ServerRequest.read_value(PayeeInfo.class);
/* 1694:     */         
/* 1695:1595 */         int m = paramInputStream.read_long();
/* 1696:1596 */         localObject3 = paramOFX151BPWServicesBean
/* 1697:1597 */           .updatePayee(
/* 1698:1598 */           localPayeeInfo1, 
/* 1699:1599 */           m);
/* 1700:1601 */         if (param_ServerRequest.isRMI()) {
/* 1701:1601 */           param_ServerRequest.write_value(localObject3, new PayeeInfo[0].getClass());
/* 1702:     */         } else {
/* 1703:1601 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject3);
/* 1704:     */         }
/* 1705:     */       }
/* 1706:     */       catch (Throwable localThrowable7)
/* 1707:     */       {
/* 1708:1605 */         localThrowable7.printStackTrace(Jaguar.log);
/* 1709:1606 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/* 1710:1607 */         return localThrowable7.getClass().getName();
/* 1711:     */       }
/* 1712:     */     case 58: 
/* 1713:     */       try
/* 1714:     */       {
/* 1715:1616 */         PayeeInfo localPayeeInfo2 = (PayeeInfo)param_ServerRequest.read_value(PayeeInfo.class);
/* 1716:     */         
/* 1717:1618 */         localObject1 = (PayeeRouteInfo)param_ServerRequest.read_value(PayeeRouteInfo.class);
/* 1718:1619 */         paramOFX151BPWServicesBean
/* 1719:1620 */           .updatePayee(
/* 1720:1621 */           localPayeeInfo2, 
/* 1721:1622 */           (PayeeRouteInfo)localObject1);
/* 1722:     */       }
/* 1723:     */       catch (Throwable localThrowable8)
/* 1724:     */       {
/* 1725:1627 */         localThrowable8.printStackTrace(Jaguar.log);
/* 1726:1628 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/* 1727:1629 */         return localThrowable8.getClass().getName();
/* 1728:     */       }
/* 1729:     */     case 59: 
/* 1730:     */       try
/* 1731:     */       {
/* 1732:1638 */         String str7 = param_ServerRequest.read_string();
/* 1733:1639 */         paramOFX151BPWServicesBean
/* 1734:1640 */           .deletePayee(
/* 1735:1641 */           str7);
/* 1736:     */       }
/* 1737:     */       catch (Throwable localThrowable9)
/* 1738:     */       {
/* 1739:1646 */         localThrowable9.printStackTrace(Jaguar.log);
/* 1740:1647 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/* 1741:1648 */         return localThrowable9.getClass().getName();
/* 1742:     */       }
/* 1743:     */     case 60: 
/* 1744:     */       try
/* 1745:     */       {
/* 1746:     */         String[] arrayOfString1;
/* 1747:1657 */         if (param_ServerRequest.isRMI()) {
/* 1748:1657 */           arrayOfString1 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 1749:     */         } else {
/* 1750:1657 */           arrayOfString1 = StringSeqHelper.read(paramInputStream);
/* 1751:     */         }
/* 1752:1659 */         paramOFX151BPWServicesBean.deletePayees(
/* 1753:1660 */           arrayOfString1);
/* 1754:     */       }
/* 1755:     */       catch (Throwable localThrowable10)
/* 1756:     */       {
/* 1757:1665 */         localThrowable10.printStackTrace(Jaguar.log);
/* 1758:1666 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/* 1759:1667 */         return localThrowable10.getClass().getName();
/* 1760:     */       }
/* 1761:     */     case 61: 
/* 1762:     */       try
/* 1763:     */       {
/* 1764:1676 */         String str8 = param_ServerRequest.read_string();
/* 1765:1677 */         localObject1 = paramOFX151BPWServicesBean
/* 1766:1678 */           .findPayeeByID(
/* 1767:1679 */           str8);
/* 1768:     */         
/* 1769:1681 */         param_ServerRequest.write_value(localObject1, PayeeInfo.class);
/* 1770:     */       }
/* 1771:     */       catch (Throwable localThrowable11)
/* 1772:     */       {
/* 1773:1685 */         localThrowable11.printStackTrace(Jaguar.log);
/* 1774:1686 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/* 1775:1687 */         return localThrowable11.getClass().getName();
/* 1776:     */       }
/* 1777:     */     case 62: 
/* 1778:     */       try
/* 1779:     */       {
/* 1780:1696 */         String str9 = param_ServerRequest.read_string();
/* 1781:     */         
/* 1782:1698 */         localObject1 = param_ServerRequest.read_string();
/* 1783:1699 */         paramOFX151BPWServicesBean
/* 1784:1700 */           .setPayeeStatus(
/* 1785:1701 */           str9, 
/* 1786:1702 */           (String)localObject1);
/* 1787:     */       }
/* 1788:     */       catch (Throwable localThrowable12)
/* 1789:     */       {
/* 1790:1707 */         localThrowable12.printStackTrace(Jaguar.log);
/* 1791:1708 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/* 1792:1709 */         return localThrowable12.getClass().getName();
/* 1793:     */       }
/* 1794:     */     case 63: 
/* 1795:     */       try
/* 1796:     */       {
/* 1797:1718 */         int i = paramInputStream.read_long();
/* 1798:1719 */         n = paramOFX151BPWServicesBean
/* 1799:1720 */           .getSmartDate(
/* 1800:1721 */           i);
/* 1801:     */         
/* 1802:1723 */         paramOutputStream.write_long(n);
/* 1803:     */       }
/* 1804:     */       catch (Throwable localThrowable13)
/* 1805:     */       {
/* 1806:1727 */         localThrowable13.printStackTrace(Jaguar.log);
/* 1807:1728 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/* 1808:1729 */         return localThrowable13.getClass().getName();
/* 1809:     */       }
/* 1810:     */     case 64: 
/* 1811:     */       try
/* 1812:     */       {
/* 1813:1738 */         String str10 = param_ServerRequest.read_string();
/* 1814:     */         
/* 1815:1740 */         n = paramInputStream.read_long();
/* 1816:1741 */         localObject3 = paramOFX151BPWServicesBean
/* 1817:1742 */           .getGlobalPayees(
/* 1818:1743 */           str10, 
/* 1819:1744 */           n);
/* 1820:1746 */         if (param_ServerRequest.isRMI()) {
/* 1821:1746 */           param_ServerRequest.write_value(localObject3, new PayeeInfo[0].getClass());
/* 1822:     */         } else {
/* 1823:1746 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject3);
/* 1824:     */         }
/* 1825:     */       }
/* 1826:     */       catch (Throwable localThrowable14)
/* 1827:     */       {
/* 1828:1750 */         localThrowable14.printStackTrace(Jaguar.log);
/* 1829:1751 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/* 1830:1752 */         return localThrowable14.getClass().getName();
/* 1831:     */       }
/* 1832:     */     case 65: 
/* 1833:     */       try
/* 1834:     */       {
/* 1835:1761 */         PayeeInfo localPayeeInfo3 = (PayeeInfo)param_ServerRequest.read_value(PayeeInfo.class);
/* 1836:     */         
/* 1837:1763 */         n = paramInputStream.read_long();
/* 1838:1764 */         localObject3 = paramOFX151BPWServicesBean
/* 1839:1765 */           .addPayee(
/* 1840:1766 */           localPayeeInfo3, 
/* 1841:1767 */           n);
/* 1842:     */         
/* 1843:1769 */         param_ServerRequest.write_string((String)localObject3);
/* 1844:     */       }
/* 1845:     */       catch (Throwable localThrowable15)
/* 1846:     */       {
/* 1847:1773 */         localThrowable15.printStackTrace(Jaguar.log);
/* 1848:1774 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/* 1849:1775 */         return localThrowable15.getClass().getName();
/* 1850:     */       }
/* 1851:     */     case 66: 
/* 1852:     */       try
/* 1853:     */       {
/* 1854:     */         ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo1;
/* 1855:1784 */         if (param_ServerRequest.isRMI()) {
/* 1856:1784 */           arrayOfConsumerCrossRefInfo1 = (ConsumerCrossRefInfo[])param_ServerRequest.read_value(new ConsumerCrossRefInfo[0].getClass());
/* 1857:     */         } else {
/* 1858:1784 */           arrayOfConsumerCrossRefInfo1 = ConsumerCrossRefInfoSeqHelper.read(paramInputStream);
/* 1859:     */         }
/* 1860:1785 */         n = 
/* 1861:1786 */           paramOFX151BPWServicesBean.addConsumerCrossRef(
/* 1862:1787 */           arrayOfConsumerCrossRefInfo1);
/* 1863:     */         
/* 1864:1789 */         paramOutputStream.write_long(n);
/* 1865:     */       }
/* 1866:     */       catch (Throwable localThrowable16)
/* 1867:     */       {
/* 1868:1793 */         localThrowable16.printStackTrace(Jaguar.log);
/* 1869:1794 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/* 1870:1795 */         return localThrowable16.getClass().getName();
/* 1871:     */       }
/* 1872:     */     case 67: 
/* 1873:     */       try
/* 1874:     */       {
/* 1875:     */         ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo2;
/* 1876:1804 */         if (param_ServerRequest.isRMI()) {
/* 1877:1804 */           arrayOfConsumerCrossRefInfo2 = (ConsumerCrossRefInfo[])param_ServerRequest.read_value(new ConsumerCrossRefInfo[0].getClass());
/* 1878:     */         } else {
/* 1879:1804 */           arrayOfConsumerCrossRefInfo2 = ConsumerCrossRefInfoSeqHelper.read(paramInputStream);
/* 1880:     */         }
/* 1881:1805 */         n = 
/* 1882:1806 */           paramOFX151BPWServicesBean.deleteConsumerCrossRef(
/* 1883:1807 */           arrayOfConsumerCrossRefInfo2);
/* 1884:     */         
/* 1885:1809 */         paramOutputStream.write_long(n);
/* 1886:     */       }
/* 1887:     */       catch (Throwable localThrowable17)
/* 1888:     */       {
/* 1889:1813 */         localThrowable17.printStackTrace(Jaguar.log);
/* 1890:1814 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/* 1891:1815 */         return localThrowable17.getClass().getName();
/* 1892:     */       }
/* 1893:     */     case 68: 
/* 1894:     */       try
/* 1895:     */       {
/* 1896:     */         String[] arrayOfString2;
/* 1897:1824 */         if (param_ServerRequest.isRMI()) {
/* 1898:1824 */           arrayOfString2 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 1899:     */         } else {
/* 1900:1824 */           arrayOfString2 = StringSeqHelper.read(paramInputStream);
/* 1901:     */         }
/* 1902:1825 */         ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo3 = paramOFX151BPWServicesBean
/* 1903:1826 */           .getConsumerCrossRef(
/* 1904:1827 */           arrayOfString2);
/* 1905:1829 */         if (param_ServerRequest.isRMI()) {
/* 1906:1829 */           param_ServerRequest.write_value(arrayOfConsumerCrossRefInfo3, new ConsumerCrossRefInfo[0].getClass());
/* 1907:     */         } else {
/* 1908:1829 */           ConsumerCrossRefInfoSeqHelper.write(paramOutputStream, arrayOfConsumerCrossRefInfo3);
/* 1909:     */         }
/* 1910:     */       }
/* 1911:     */       catch (Throwable localThrowable18)
/* 1912:     */       {
/* 1913:1833 */         localThrowable18.printStackTrace(Jaguar.log);
/* 1914:1834 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/* 1915:1835 */         return localThrowable18.getClass().getName();
/* 1916:     */       }
/* 1917:     */     case 69: 
/* 1918:     */       try
/* 1919:     */       {
/* 1920:     */         CustomerBankInfo[] arrayOfCustomerBankInfo1;
/* 1921:1844 */         if (param_ServerRequest.isRMI()) {
/* 1922:1844 */           arrayOfCustomerBankInfo1 = (CustomerBankInfo[])param_ServerRequest.read_value(new CustomerBankInfo[0].getClass());
/* 1923:     */         } else {
/* 1924:1844 */           arrayOfCustomerBankInfo1 = CustomerBankInfoSeqHelper.read(paramInputStream);
/* 1925:     */         }
/* 1926:1845 */         i1 = 
/* 1927:1846 */           paramOFX151BPWServicesBean.addCustomerBankInfo(
/* 1928:1847 */           arrayOfCustomerBankInfo1);
/* 1929:     */         
/* 1930:1849 */         paramOutputStream.write_long(i1);
/* 1931:     */       }
/* 1932:     */       catch (Throwable localThrowable19)
/* 1933:     */       {
/* 1934:1853 */         localThrowable19.printStackTrace(Jaguar.log);
/* 1935:1854 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/* 1936:1855 */         return localThrowable19.getClass().getName();
/* 1937:     */       }
/* 1938:     */     case 70: 
/* 1939:     */       try
/* 1940:     */       {
/* 1941:     */         CustomerBankInfo[] arrayOfCustomerBankInfo2;
/* 1942:1864 */         if (param_ServerRequest.isRMI()) {
/* 1943:1864 */           arrayOfCustomerBankInfo2 = (CustomerBankInfo[])param_ServerRequest.read_value(new CustomerBankInfo[0].getClass());
/* 1944:     */         } else {
/* 1945:1864 */           arrayOfCustomerBankInfo2 = CustomerBankInfoSeqHelper.read(paramInputStream);
/* 1946:     */         }
/* 1947:1865 */         i1 = 
/* 1948:1866 */           paramOFX151BPWServicesBean.deleteCustomerBankInfo(
/* 1949:1867 */           arrayOfCustomerBankInfo2);
/* 1950:     */         
/* 1951:1869 */         paramOutputStream.write_long(i1);
/* 1952:     */       }
/* 1953:     */       catch (Throwable localThrowable20)
/* 1954:     */       {
/* 1955:1873 */         localThrowable20.printStackTrace(Jaguar.log);
/* 1956:1874 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/* 1957:1875 */         return localThrowable20.getClass().getName();
/* 1958:     */       }
/* 1959:     */     case 71: 
/* 1960:     */       try
/* 1961:     */       {
/* 1962:     */         String[] arrayOfString3;
/* 1963:1884 */         if (param_ServerRequest.isRMI()) {
/* 1964:1884 */           arrayOfString3 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 1965:     */         } else {
/* 1966:1884 */           arrayOfString3 = StringSeqHelper.read(paramInputStream);
/* 1967:     */         }
/* 1968:1885 */         CustomerBankInfo[] arrayOfCustomerBankInfo3 = paramOFX151BPWServicesBean
/* 1969:1886 */           .getCustomerBankInfo(
/* 1970:1887 */           arrayOfString3);
/* 1971:1889 */         if (param_ServerRequest.isRMI()) {
/* 1972:1889 */           param_ServerRequest.write_value(arrayOfCustomerBankInfo3, new CustomerBankInfo[0].getClass());
/* 1973:     */         } else {
/* 1974:1889 */           CustomerBankInfoSeqHelper.write(paramOutputStream, arrayOfCustomerBankInfo3);
/* 1975:     */         }
/* 1976:     */       }
/* 1977:     */       catch (Throwable localThrowable21)
/* 1978:     */       {
/* 1979:1893 */         localThrowable21.printStackTrace(Jaguar.log);
/* 1980:1894 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/* 1981:1895 */         return localThrowable21.getClass().getName();
/* 1982:     */       }
/* 1983:     */     case 72: 
/* 1984:     */       try
/* 1985:     */       {
/* 1986:     */         CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo1;
/* 1987:1904 */         if (param_ServerRequest.isRMI()) {
/* 1988:1904 */           arrayOfCustomerProductAccessInfo1 = (CustomerProductAccessInfo[])param_ServerRequest.read_value(new CustomerProductAccessInfo[0].getClass());
/* 1989:     */         } else {
/* 1990:1904 */           arrayOfCustomerProductAccessInfo1 = CustomerProductAccessInfoSeqHelper.read(paramInputStream);
/* 1991:     */         }
/* 1992:1905 */         i2 = 
/* 1993:1906 */           paramOFX151BPWServicesBean.addCustomerProductAccessInfo(
/* 1994:1907 */           arrayOfCustomerProductAccessInfo1);
/* 1995:     */         
/* 1996:1909 */         paramOutputStream.write_long(i2);
/* 1997:     */       }
/* 1998:     */       catch (Throwable localThrowable22)
/* 1999:     */       {
/* 2000:1913 */         localThrowable22.printStackTrace(Jaguar.log);
/* 2001:1914 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/* 2002:1915 */         return localThrowable22.getClass().getName();
/* 2003:     */       }
/* 2004:     */     case 73: 
/* 2005:     */       try
/* 2006:     */       {
/* 2007:     */         CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo2;
/* 2008:1924 */         if (param_ServerRequest.isRMI()) {
/* 2009:1924 */           arrayOfCustomerProductAccessInfo2 = (CustomerProductAccessInfo[])param_ServerRequest.read_value(new CustomerProductAccessInfo[0].getClass());
/* 2010:     */         } else {
/* 2011:1924 */           arrayOfCustomerProductAccessInfo2 = CustomerProductAccessInfoSeqHelper.read(paramInputStream);
/* 2012:     */         }
/* 2013:1925 */         i2 = 
/* 2014:1926 */           paramOFX151BPWServicesBean.deleteCustomerProductAccessInfo(
/* 2015:1927 */           arrayOfCustomerProductAccessInfo2);
/* 2016:     */         
/* 2017:1929 */         paramOutputStream.write_long(i2);
/* 2018:     */       }
/* 2019:     */       catch (Throwable localThrowable23)
/* 2020:     */       {
/* 2021:1933 */         localThrowable23.printStackTrace(Jaguar.log);
/* 2022:1934 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/* 2023:1935 */         return localThrowable23.getClass().getName();
/* 2024:     */       }
/* 2025:     */     case 74: 
/* 2026:     */       try
/* 2027:     */       {
/* 2028:     */         String[] arrayOfString4;
/* 2029:1944 */         if (param_ServerRequest.isRMI()) {
/* 2030:1944 */           arrayOfString4 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 2031:     */         } else {
/* 2032:1944 */           arrayOfString4 = StringSeqHelper.read(paramInputStream);
/* 2033:     */         }
/* 2034:1945 */         localObject2 = 
/* 2035:1946 */           paramOFX151BPWServicesBean.getCustomerProductAccessInfo(
/* 2036:1947 */           arrayOfString4);
/* 2037:1949 */         if (param_ServerRequest.isRMI()) {
/* 2038:1949 */           param_ServerRequest.write_value(localObject2, new CustomerProductAccessInfo[0].getClass());
/* 2039:     */         } else {
/* 2040:1949 */           CustomerProductAccessInfoSeqHelper.write(paramOutputStream, (CustomerProductAccessInfo[])localObject2);
/* 2041:     */         }
/* 2042:     */       }
/* 2043:     */       catch (Throwable localThrowable24)
/* 2044:     */       {
/* 2045:1953 */         localThrowable24.printStackTrace(Jaguar.log);
/* 2046:1954 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/* 2047:1955 */         return localThrowable24.getClass().getName();
/* 2048:     */       }
/* 2049:     */     case 75: 
/* 2050:     */       try
/* 2051:     */       {
/* 2052:1964 */         String str11 = param_ServerRequest.read_string();
/* 2053:     */         
/* 2054:1966 */         localObject2 = param_ServerRequest.read_string();
/* 2055:1967 */         bool = paramOFX151BPWServicesBean
/* 2056:1968 */           .validateMetavanteCustAcctByConsumerID(
/* 2057:1969 */           str11, 
/* 2058:1970 */           (String)localObject2);
/* 2059:     */         
/* 2060:1972 */         paramOutputStream.write_boolean(bool);
/* 2061:     */       }
/* 2062:     */       catch (Throwable localThrowable25)
/* 2063:     */       {
/* 2064:1976 */         localThrowable25.printStackTrace(Jaguar.log);
/* 2065:1977 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/* 2066:1978 */         return localThrowable25.getClass().getName();
/* 2067:     */       }
/* 2068:     */     case 76: 
/* 2069:     */       try
/* 2070:     */       {
/* 2071:1987 */         String str12 = param_ServerRequest.read_string();
/* 2072:     */         
/* 2073:1989 */         localObject2 = param_ServerRequest.read_string();
/* 2074:1990 */         bool = paramOFX151BPWServicesBean
/* 2075:1991 */           .validateMetavanteCustAcctByCustomerID(
/* 2076:1992 */           str12, 
/* 2077:1993 */           (String)localObject2);
/* 2078:     */         
/* 2079:1995 */         paramOutputStream.write_boolean(bool);
/* 2080:     */       }
/* 2081:     */       catch (Throwable localThrowable26)
/* 2082:     */       {
/* 2083:1999 */         localThrowable26.printStackTrace(Jaguar.log);
/* 2084:2000 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/* 2085:2001 */         return localThrowable26.getClass().getName();
/* 2086:     */       }
/* 2087:     */     case 77: 
/* 2088:     */       try
/* 2089:     */       {
/* 2090:     */         BPWHist localBPWHist1;
/* 2091:2010 */         if (param_ServerRequest.isRMI()) {
/* 2092:2010 */           localBPWHist1 = (BPWHist)param_ServerRequest.read_value(BPWHist.class);
/* 2093:     */         } else {
/* 2094:2010 */           localBPWHist1 = BPWHistHelper.read(paramInputStream);
/* 2095:     */         }
/* 2096:2011 */         localObject2 = 
/* 2097:2012 */           paramOFX151BPWServicesBean.getPmtHistory(
/* 2098:2013 */           localBPWHist1);
/* 2099:2015 */         if (param_ServerRequest.isRMI()) {
/* 2100:2015 */           param_ServerRequest.write_value(localObject2, BPWHist.class);
/* 2101:     */         } else {
/* 2102:2015 */           BPWHistHelper.write(paramOutputStream, (BPWHist)localObject2);
/* 2103:     */         }
/* 2104:     */       }
/* 2105:     */       catch (Throwable localThrowable27)
/* 2106:     */       {
/* 2107:2019 */         localThrowable27.printStackTrace(Jaguar.log);
/* 2108:2020 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/* 2109:2021 */         return localThrowable27.getClass().getName();
/* 2110:     */       }
/* 2111:     */     case 78: 
/* 2112:     */       try
/* 2113:     */       {
/* 2114:     */         BPWHist localBPWHist2;
/* 2115:2030 */         if (param_ServerRequest.isRMI()) {
/* 2116:2030 */           localBPWHist2 = (BPWHist)param_ServerRequest.read_value(BPWHist.class);
/* 2117:     */         } else {
/* 2118:2030 */           localBPWHist2 = BPWHistHelper.read(paramInputStream);
/* 2119:     */         }
/* 2120:2031 */         localObject2 = 
/* 2121:2032 */           paramOFX151BPWServicesBean.getIntraHistory(
/* 2122:2033 */           localBPWHist2);
/* 2123:2035 */         if (param_ServerRequest.isRMI()) {
/* 2124:2035 */           param_ServerRequest.write_value(localObject2, BPWHist.class);
/* 2125:     */         } else {
/* 2126:2035 */           BPWHistHelper.write(paramOutputStream, (BPWHist)localObject2);
/* 2127:     */         }
/* 2128:     */       }
/* 2129:     */       catch (Throwable localThrowable28)
/* 2130:     */       {
/* 2131:2039 */         localThrowable28.printStackTrace(Jaguar.log);
/* 2132:2040 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/* 2133:2041 */         return localThrowable28.getClass().getName();
/* 2134:     */       }
/* 2135:     */     case 79: 
/* 2136:     */       try
/* 2137:     */       {
/* 2138:     */         String[] arrayOfString5;
/* 2139:2050 */         if (param_ServerRequest.isRMI()) {
/* 2140:2050 */           arrayOfString5 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 2141:     */         } else {
/* 2142:2050 */           arrayOfString5 = StringSeqHelper.read(paramInputStream);
/* 2143:     */         }
/* 2144:2051 */         localObject2 = 
/* 2145:2052 */           paramOFX151BPWServicesBean.getIntraById(
/* 2146:2053 */           arrayOfString5);
/* 2147:2055 */         if (param_ServerRequest.isRMI()) {
/* 2148:2055 */           param_ServerRequest.write_value(localObject2, new IntraTrnInfo[0].getClass());
/* 2149:     */         } else {
/* 2150:2055 */           IntraTrnInfoSeqHelper.write(paramOutputStream, (IntraTrnInfo[])localObject2);
/* 2151:     */         }
/* 2152:     */       }
/* 2153:     */       catch (Throwable localThrowable29)
/* 2154:     */       {
/* 2155:2059 */         localThrowable29.printStackTrace(Jaguar.log);
/* 2156:2060 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/* 2157:2061 */         return localThrowable29.getClass().getName();
/* 2158:     */       }
/* 2159:     */     case 80: 
/* 2160:     */       try
/* 2161:     */       {
/* 2162:2070 */         String str13 = param_ServerRequest.read_string();
/* 2163:2071 */         localObject2 = paramOFX151BPWServicesBean
/* 2164:2072 */           .getIntraById(
/* 2165:2073 */           str13);
/* 2166:     */         
/* 2167:2075 */         param_ServerRequest.write_value(localObject2, IntraTrnInfo.class);
/* 2168:     */       }
/* 2169:     */       catch (Throwable localThrowable30)
/* 2170:     */       {
/* 2171:2079 */         localThrowable30.printStackTrace(Jaguar.log);
/* 2172:2080 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/* 2173:2081 */         return localThrowable30.getClass().getName();
/* 2174:     */       }
/* 2175:     */     case 81: 
/* 2176:     */       try
/* 2177:     */       {
/* 2178:2090 */         String str14 = param_ServerRequest.read_string();
/* 2179:2091 */         localObject2 = paramOFX151BPWServicesBean
/* 2180:2092 */           .getPmtById(
/* 2181:2093 */           str14);
/* 2182:     */         
/* 2183:2095 */         param_ServerRequest.write_value(localObject2, PmtInfo.class);
/* 2184:     */       }
/* 2185:     */       catch (Throwable localThrowable31)
/* 2186:     */       {
/* 2187:2099 */         localThrowable31.printStackTrace(Jaguar.log);
/* 2188:2100 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/* 2189:2101 */         return localThrowable31.getClass().getName();
/* 2190:     */       }
/* 2191:     */     case 82: 
/* 2192:     */       try
/* 2193:     */       {
/* 2194:     */         String[] arrayOfString6;
/* 2195:2110 */         if (param_ServerRequest.isRMI()) {
/* 2196:2110 */           arrayOfString6 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 2197:     */         } else {
/* 2198:2110 */           arrayOfString6 = StringSeqHelper.read(paramInputStream);
/* 2199:     */         }
/* 2200:2111 */         localObject2 = 
/* 2201:2112 */           paramOFX151BPWServicesBean.getPmtById(
/* 2202:2113 */           arrayOfString6);
/* 2203:2115 */         if (param_ServerRequest.isRMI()) {
/* 2204:2115 */           param_ServerRequest.write_value(localObject2, new PmtInfo[0].getClass());
/* 2205:     */         } else {
/* 2206:2115 */           PmtInfoSeqHelper.write(paramOutputStream, (PmtInfo[])localObject2);
/* 2207:     */         }
/* 2208:     */       }
/* 2209:     */       catch (Throwable localThrowable32)
/* 2210:     */       {
/* 2211:2119 */         localThrowable32.printStackTrace(Jaguar.log);
/* 2212:2120 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/* 2213:2121 */         return localThrowable32.getClass().getName();
/* 2214:     */       }
/* 2215:     */     case 83: 
/* 2216:     */       try
/* 2217:     */       {
/* 2218:     */         String[] arrayOfString7;
/* 2219:2130 */         if (param_ServerRequest.isRMI()) {
/* 2220:2130 */           arrayOfString7 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/* 2221:     */         } else {
/* 2222:2130 */           arrayOfString7 = StringSeqHelper.read(paramInputStream);
/* 2223:     */         }
/* 2224:2131 */         localObject2 = 
/* 2225:2132 */           paramOFX151BPWServicesBean.getRecPmtById(
/* 2226:2133 */           arrayOfString7);
/* 2227:2135 */         if (param_ServerRequest.isRMI()) {
/* 2228:2135 */           param_ServerRequest.write_value(localObject2, new RecPmtInfo[0].getClass());
/* 2229:     */         } else {
/* 2230:2135 */           RecPmtInfoSeqHelper.write(paramOutputStream, (RecPmtInfo[])localObject2);
/* 2231:     */         }
/* 2232:     */       }
/* 2233:     */       catch (Throwable localThrowable33)
/* 2234:     */       {
/* 2235:2139 */         localThrowable33.printStackTrace(Jaguar.log);
/* 2236:2140 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/* 2237:2141 */         return localThrowable33.getClass().getName();
/* 2238:     */       }
/* 2239:     */     case 84: 
/* 2240:     */       try
/* 2241:     */       {
/* 2242:2150 */         String str15 = param_ServerRequest.read_string();
/* 2243:     */         
/* 2244:2152 */         localObject2 = param_ServerRequest.read_string();
/* 2245:2153 */         PayeeInfo localPayeeInfo4 = paramOFX151BPWServicesBean
/* 2246:2154 */           .getPayeeByListId(
/* 2247:2155 */           str15, 
/* 2248:2156 */           (String)localObject2);
/* 2249:     */         
/* 2250:2158 */         param_ServerRequest.write_value(localPayeeInfo4, PayeeInfo.class);
/* 2251:     */       }
/* 2252:     */       catch (Throwable localThrowable34)
/* 2253:     */       {
/* 2254:2162 */         if ((localThrowable34 instanceof FFSException))
/* 2255:     */         {
/* 2256:2164 */           if (UserException.ok(paramOutputStream)) {
/* 2257:2166 */             if (param_ServerRequest.isRMI())
/* 2258:     */             {
/* 2259:2168 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2260:2169 */               param_ServerRequest.write_value((FFSException)localThrowable34, FFSException.class);
/* 2261:     */             }
/* 2262:     */             else
/* 2263:     */             {
/* 2264:2173 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2265:2174 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable34);
/* 2266:     */             }
/* 2267:     */           }
/* 2268:2177 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable34);
/* 2269:     */         }
/* 2270:2179 */         localThrowable34.printStackTrace(Jaguar.log);
/* 2271:2180 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/* 2272:2181 */         return localThrowable34.getClass().getName();
/* 2273:     */       }
/* 2274:     */     case 85: 
/* 2275:     */       try
/* 2276:     */       {
/* 2277:2189 */         AccountTypesMap localAccountTypesMap = paramOFX151BPWServicesBean
/* 2278:2190 */           .getAccountTypesMap();
/* 2279:     */         
/* 2280:2192 */         param_ServerRequest.write_value(localAccountTypesMap, AccountTypesMap.class);
/* 2281:     */       }
/* 2282:     */       catch (Throwable localThrowable35)
/* 2283:     */       {
/* 2284:2196 */         if ((localThrowable35 instanceof FFSException))
/* 2285:     */         {
/* 2286:2198 */           if (UserException.ok(paramOutputStream)) {
/* 2287:2200 */             if (param_ServerRequest.isRMI())
/* 2288:     */             {
/* 2289:2202 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2290:2203 */               param_ServerRequest.write_value((FFSException)localThrowable35, FFSException.class);
/* 2291:     */             }
/* 2292:     */             else
/* 2293:     */             {
/* 2294:2207 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2295:2208 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable35);
/* 2296:     */             }
/* 2297:     */           }
/* 2298:2211 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable35);
/* 2299:     */         }
/* 2300:2213 */         localThrowable35.printStackTrace(Jaguar.log);
/* 2301:2214 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/* 2302:2215 */         return localThrowable35.getClass().getName();
/* 2303:     */       }
/* 2304:     */     }
/* 2305:2220 */     return null;
/* 2306:     */   }
/* 2307:     */   
/* 2308:     */   public static String localInvoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/* 2309:     */   {
/* 2310:2230 */     _ServerRequest local_ServerRequest = new _ServerRequest(paramInputStream, paramOutputStream, (paramInt & 0x1) != 0);
/* 2311:2231 */     OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)paramObject;
/* 2312:2232 */     Integer localInteger = null;
/* 2313:2233 */     boolean bool1 = false;
/* 2314:2234 */     if (!paramString.startsWith("#"))
/* 2315:     */     {
/* 2316:2236 */       localInteger = (Integer)_localMethods2.get(paramString);
/* 2317:2237 */       if (localInteger != null) {
/* 2318:2238 */         bool1 = true;
/* 2319:     */       }
/* 2320:     */     }
/* 2321:     */     else
/* 2322:     */     {
/* 2323:2242 */       localInteger = (Integer)_localMethods.get(paramString);
/* 2324:     */     }
/* 2325:2244 */     if (localInteger == null) {
/* 2326:2246 */       return remoteInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/* 2327:     */     }
/* 2328:2248 */     LocalFrame localLocalFrame = LocalStack.getCurrent().top();
/* 2329:     */     Object localObject7;
/* 2330:     */     Object localObject9;
/* 2331:     */     int i3;
/* 2332:     */     Object localObject13;
/* 2333:     */     Object localObject8;
/* 2334:     */     Object localObject12;
/* 2335:     */     String[] arrayOfString6;
/* 2336:2249 */     switch (localInteger.intValue())
/* 2337:     */     {
/* 2338:     */     case 0: 
/* 2339:     */       try
/* 2340:     */       {
/* 2341:2256 */         localOFX151BPWServicesBean.ejbCreate();
/* 2342:     */       }
/* 2343:     */       catch (Throwable localThrowable1)
/* 2344:     */       {
/* 2345:2261 */         localThrowable1.printStackTrace(Jaguar.log);
/* 2346:2262 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/* 2347:2263 */         return localThrowable1.getClass().getName();
/* 2348:     */       }
/* 2349:     */     case 1: 
/* 2350:     */       try
/* 2351:     */       {
/* 2352:2272 */         localOFX151BPWServicesBean.ejbRemove();
/* 2353:     */       }
/* 2354:     */       catch (Throwable localThrowable2)
/* 2355:     */       {
/* 2356:2277 */         localThrowable2.printStackTrace(Jaguar.log);
/* 2357:2278 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/* 2358:2279 */         return localThrowable2.getClass().getName();
/* 2359:     */       }
/* 2360:     */     case 2: 
/* 2361:     */       try
/* 2362:     */       {
/* 2363:2288 */         localOFX151BPWServicesBean.disconnect();
/* 2364:     */       }
/* 2365:     */       catch (Throwable localThrowable3)
/* 2366:     */       {
/* 2367:2293 */         localThrowable3.printStackTrace(Jaguar.log);
/* 2368:2294 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/* 2369:2295 */         return localThrowable3.getClass().getName();
/* 2370:     */       }
/* 2371:     */     case 3: 
/* 2372:     */       try
/* 2373:     */       {
/* 2374:     */         CustomerInfo[] arrayOfCustomerInfo1;
/* 2375:2304 */         if (!bool1)
/* 2376:     */         {
/* 2377:2306 */           arrayOfCustomerInfo1 = (CustomerInfo[])localLocalFrame.get(0);
/* 2378:     */         }
/* 2379:     */         else
/* 2380:     */         {
/* 2381:2310 */           Object localObject1 = localLocalFrame.get(0);
/* 2382:2311 */           arrayOfCustomerInfo1 = (CustomerInfo[])ObjectVal.clone(localObject1);
/* 2383:     */         }
/* 2384:2313 */         int j = localOFX151BPWServicesBean
/* 2385:2314 */           .addCustomers(
/* 2386:2315 */           arrayOfCustomerInfo1);
/* 2387:     */         
/* 2388:2317 */         localLocalFrame.setResult(j);
/* 2389:     */       }
/* 2390:     */       catch (Throwable localThrowable4)
/* 2391:     */       {
/* 2392:2321 */         localThrowable4.printStackTrace(Jaguar.log);
/* 2393:2322 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/* 2394:2323 */         return localThrowable4.getClass().getName();
/* 2395:     */       }
/* 2396:     */     case 4: 
/* 2397:     */       try
/* 2398:     */       {
/* 2399:     */         CustomerInfo[] arrayOfCustomerInfo2;
/* 2400:2332 */         if (!bool1)
/* 2401:     */         {
/* 2402:2334 */           arrayOfCustomerInfo2 = (CustomerInfo[])localLocalFrame.get(0);
/* 2403:     */         }
/* 2404:     */         else
/* 2405:     */         {
/* 2406:2338 */           Object localObject2 = localLocalFrame.get(0);
/* 2407:2339 */           arrayOfCustomerInfo2 = (CustomerInfo[])ObjectVal.clone(localObject2);
/* 2408:     */         }
/* 2409:2341 */         int k = localOFX151BPWServicesBean
/* 2410:2342 */           .modifyCustomers(
/* 2411:2343 */           arrayOfCustomerInfo2);
/* 2412:     */         
/* 2413:2345 */         localLocalFrame.setResult(k);
/* 2414:     */       }
/* 2415:     */       catch (Throwable localThrowable5)
/* 2416:     */       {
/* 2417:2349 */         localThrowable5.printStackTrace(Jaguar.log);
/* 2418:2350 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/* 2419:2351 */         return localThrowable5.getClass().getName();
/* 2420:     */       }
/* 2421:     */     case 5: 
/* 2422:     */       try
/* 2423:     */       {
/* 2424:     */         String[] arrayOfString1;
/* 2425:2360 */         if (!bool1)
/* 2426:     */         {
/* 2427:2362 */           arrayOfString1 = (String[])localLocalFrame.get(0);
/* 2428:     */         }
/* 2429:     */         else
/* 2430:     */         {
/* 2431:2366 */           Object localObject3 = localLocalFrame.get(0);
/* 2432:2367 */           arrayOfString1 = (String[])ObjectVal.clone(localObject3);
/* 2433:     */         }
/* 2434:2369 */         int m = localOFX151BPWServicesBean
/* 2435:2370 */           .deleteCustomers(
/* 2436:2371 */           arrayOfString1);
/* 2437:     */         
/* 2438:2373 */         localLocalFrame.setResult(m);
/* 2439:     */       }
/* 2440:     */       catch (Throwable localThrowable6)
/* 2441:     */       {
/* 2442:2377 */         localThrowable6.printStackTrace(Jaguar.log);
/* 2443:2378 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/* 2444:2379 */         return localThrowable6.getClass().getName();
/* 2445:     */       }
/* 2446:     */     case 6: 
/* 2447:     */       try
/* 2448:     */       {
/* 2449:     */         String[] arrayOfString2;
/* 2450:2388 */         if (!bool1)
/* 2451:     */         {
/* 2452:2390 */           arrayOfString2 = (String[])localLocalFrame.get(0);
/* 2453:     */         }
/* 2454:     */         else
/* 2455:     */         {
/* 2456:2394 */           Object localObject4 = localLocalFrame.get(0);
/* 2457:2395 */           arrayOfString2 = (String[])ObjectVal.clone(localObject4);
/* 2458:     */         }
/* 2459:2398 */         int n = ((Integer)localLocalFrame.get(1)).intValue();
/* 2460:2399 */         int i5 = localOFX151BPWServicesBean
/* 2461:2400 */           .deleteCustomers(
/* 2462:2401 */           arrayOfString2, 
/* 2463:2402 */           n);
/* 2464:     */         
/* 2465:2404 */         localLocalFrame.setResult(i5);
/* 2466:     */       }
/* 2467:     */       catch (Throwable localThrowable7)
/* 2468:     */       {
/* 2469:2408 */         localThrowable7.printStackTrace(Jaguar.log);
/* 2470:2409 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/* 2471:2410 */         return localThrowable7.getClass().getName();
/* 2472:     */       }
/* 2473:     */     case 7: 
/* 2474:     */       try
/* 2475:     */       {
/* 2476:     */         String[] arrayOfString3;
/* 2477:2419 */         if (!bool1)
/* 2478:     */         {
/* 2479:2421 */           arrayOfString3 = (String[])localLocalFrame.get(0);
/* 2480:     */         }
/* 2481:     */         else
/* 2482:     */         {
/* 2483:2425 */           Object localObject5 = localLocalFrame.get(0);
/* 2484:2426 */           arrayOfString3 = (String[])ObjectVal.clone(localObject5);
/* 2485:     */         }
/* 2486:2428 */         int i1 = localOFX151BPWServicesBean
/* 2487:2429 */           .deactivateCustomers(
/* 2488:2430 */           arrayOfString3);
/* 2489:     */         
/* 2490:2432 */         localLocalFrame.setResult(i1);
/* 2491:     */       }
/* 2492:     */       catch (Throwable localThrowable8)
/* 2493:     */       {
/* 2494:2436 */         localThrowable8.printStackTrace(Jaguar.log);
/* 2495:2437 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/* 2496:2438 */         return localThrowable8.getClass().getName();
/* 2497:     */       }
/* 2498:     */     case 8: 
/* 2499:     */       try
/* 2500:     */       {
/* 2501:     */         String[] arrayOfString4;
/* 2502:2447 */         if (!bool1)
/* 2503:     */         {
/* 2504:2449 */           arrayOfString4 = (String[])localLocalFrame.get(0);
/* 2505:     */         }
/* 2506:     */         else
/* 2507:     */         {
/* 2508:2453 */           Object localObject6 = localLocalFrame.get(0);
/* 2509:2454 */           arrayOfString4 = (String[])ObjectVal.clone(localObject6);
/* 2510:     */         }
/* 2511:2456 */         int i2 = localOFX151BPWServicesBean
/* 2512:2457 */           .activateCustomers(
/* 2513:2458 */           arrayOfString4);
/* 2514:     */         
/* 2515:2460 */         localLocalFrame.setResult(i2);
/* 2516:     */       }
/* 2517:     */       catch (Throwable localThrowable9)
/* 2518:     */       {
/* 2519:2464 */         localThrowable9.printStackTrace(Jaguar.log);
/* 2520:2465 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/* 2521:2466 */         return localThrowable9.getClass().getName();
/* 2522:     */       }
/* 2523:     */     case 9: 
/* 2524:     */       try
/* 2525:     */       {
/* 2526:     */         String[] arrayOfString5;
/* 2527:2475 */         if (!bool1)
/* 2528:     */         {
/* 2529:2477 */           arrayOfString5 = (String[])localLocalFrame.get(0);
/* 2530:     */         }
/* 2531:     */         else
/* 2532:     */         {
/* 2533:2481 */           localObject7 = localLocalFrame.get(0);
/* 2534:2482 */           arrayOfString5 = (String[])ObjectVal.clone(localObject7);
/* 2535:     */         }
/* 2536:2484 */         localObject7 = 
/* 2537:2485 */           localOFX151BPWServicesBean.getCustomersInfo(
/* 2538:2486 */           arrayOfString5);
/* 2539:     */         
/* 2540:2488 */         localLocalFrame.setResult(localObject7);
/* 2541:     */       }
/* 2542:     */       catch (Throwable localThrowable10)
/* 2543:     */       {
/* 2544:2492 */         localThrowable10.printStackTrace(Jaguar.log);
/* 2545:2493 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/* 2546:2494 */         return localThrowable10.getClass().getName();
/* 2547:     */       }
/* 2548:     */     case 10: 
/* 2549:     */       try
/* 2550:     */       {
/* 2551:2503 */         String str1 = (String)localLocalFrame.get(0);
/* 2552:2504 */         localObject7 = localOFX151BPWServicesBean
/* 2553:2505 */           .getCustomerByType(
/* 2554:2506 */           str1);
/* 2555:     */         
/* 2556:2508 */         localLocalFrame.setResult(localObject7);
/* 2557:     */       }
/* 2558:     */       catch (Throwable localThrowable11)
/* 2559:     */       {
/* 2560:2512 */         localThrowable11.printStackTrace(Jaguar.log);
/* 2561:2513 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/* 2562:2514 */         return localThrowable11.getClass().getName();
/* 2563:     */       }
/* 2564:     */     case 11: 
/* 2565:     */       try
/* 2566:     */       {
/* 2567:2523 */         String str2 = (String)localLocalFrame.get(0);
/* 2568:2524 */         localObject7 = localOFX151BPWServicesBean
/* 2569:2525 */           .getCustomerByFI(
/* 2570:2526 */           str2);
/* 2571:     */         
/* 2572:2528 */         localLocalFrame.setResult(localObject7);
/* 2573:     */       }
/* 2574:     */       catch (Throwable localThrowable12)
/* 2575:     */       {
/* 2576:2532 */         localThrowable12.printStackTrace(Jaguar.log);
/* 2577:2533 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/* 2578:2534 */         return localThrowable12.getClass().getName();
/* 2579:     */       }
/* 2580:     */     case 12: 
/* 2581:     */       try
/* 2582:     */       {
/* 2583:2543 */         String str3 = (String)localLocalFrame.get(0);
/* 2584:2544 */         localObject7 = localOFX151BPWServicesBean
/* 2585:2545 */           .getCustomerByCategory(
/* 2586:2546 */           str3);
/* 2587:     */         
/* 2588:2548 */         localLocalFrame.setResult(localObject7);
/* 2589:     */       }
/* 2590:     */       catch (Throwable localThrowable13)
/* 2591:     */       {
/* 2592:2552 */         localThrowable13.printStackTrace(Jaguar.log);
/* 2593:2553 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/* 2594:2554 */         return localThrowable13.getClass().getName();
/* 2595:     */       }
/* 2596:     */     case 13: 
/* 2597:     */       try
/* 2598:     */       {
/* 2599:2563 */         String str4 = (String)localLocalFrame.get(0);
/* 2600:2564 */         localObject7 = localOFX151BPWServicesBean
/* 2601:2565 */           .getCustomerByGroup(
/* 2602:2566 */           str4);
/* 2603:     */         
/* 2604:2568 */         localLocalFrame.setResult(localObject7);
/* 2605:     */       }
/* 2606:     */       catch (Throwable localThrowable14)
/* 2607:     */       {
/* 2608:2572 */         localThrowable14.printStackTrace(Jaguar.log);
/* 2609:2573 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/* 2610:2574 */         return localThrowable14.getClass().getName();
/* 2611:     */       }
/* 2612:     */     case 14: 
/* 2613:     */       try
/* 2614:     */       {
/* 2615:2583 */         String str5 = (String)localLocalFrame.get(0);
/* 2616:     */         
/* 2617:2585 */         localObject7 = (String)localLocalFrame.get(1);
/* 2618:2586 */         localObject9 = localOFX151BPWServicesBean
/* 2619:2587 */           .getCustomerByTypeAndFI(
/* 2620:2588 */           str5, 
/* 2621:2589 */           (String)localObject7);
/* 2622:     */         
/* 2623:2591 */         localLocalFrame.setResult(localObject9);
/* 2624:     */       }
/* 2625:     */       catch (Throwable localThrowable15)
/* 2626:     */       {
/* 2627:2595 */         localThrowable15.printStackTrace(Jaguar.log);
/* 2628:2596 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/* 2629:2597 */         return localThrowable15.getClass().getName();
/* 2630:     */       }
/* 2631:     */     case 15: 
/* 2632:     */       try
/* 2633:     */       {
/* 2634:2606 */         String str6 = (String)localLocalFrame.get(0);
/* 2635:     */         
/* 2636:2608 */         localObject7 = (String)localLocalFrame.get(1);
/* 2637:2609 */         localObject9 = localOFX151BPWServicesBean
/* 2638:2610 */           .getCustomerByCategoryAndFI(
/* 2639:2611 */           str6, 
/* 2640:2612 */           (String)localObject7);
/* 2641:     */         
/* 2642:2614 */         localLocalFrame.setResult(localObject9);
/* 2643:     */       }
/* 2644:     */       catch (Throwable localThrowable16)
/* 2645:     */       {
/* 2646:2618 */         localThrowable16.printStackTrace(Jaguar.log);
/* 2647:2619 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/* 2648:2620 */         return localThrowable16.getClass().getName();
/* 2649:     */       }
/* 2650:     */     case 16: 
/* 2651:     */       try
/* 2652:     */       {
/* 2653:2629 */         String str7 = (String)localLocalFrame.get(0);
/* 2654:     */         
/* 2655:2631 */         localObject7 = (String)localLocalFrame.get(1);
/* 2656:2632 */         localObject9 = localOFX151BPWServicesBean
/* 2657:2633 */           .getCustomerByGroupAndFI(
/* 2658:2634 */           str7, 
/* 2659:2635 */           (String)localObject7);
/* 2660:     */         
/* 2661:2637 */         localLocalFrame.setResult(localObject9);
/* 2662:     */       }
/* 2663:     */       catch (Throwable localThrowable17)
/* 2664:     */       {
/* 2665:2641 */         localThrowable17.printStackTrace(Jaguar.log);
/* 2666:2642 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/* 2667:2643 */         return localThrowable17.getClass().getName();
/* 2668:     */       }
/* 2669:     */     case 17: 
/* 2670:     */       try
/* 2671:     */       {
/* 2672:2651 */         PayeeInfo[] arrayOfPayeeInfo = localOFX151BPWServicesBean
/* 2673:2652 */           .getLinkedPayees();
/* 2674:     */         
/* 2675:2654 */         localLocalFrame.setResult(arrayOfPayeeInfo);
/* 2676:     */       }
/* 2677:     */       catch (Throwable localThrowable18)
/* 2678:     */       {
/* 2679:2658 */         localThrowable18.printStackTrace(Jaguar.log);
/* 2680:2659 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/* 2681:2660 */         return localThrowable18.getClass().getName();
/* 2682:     */       }
/* 2683:     */     case 18: 
/* 2684:     */       try
/* 2685:     */       {
/* 2686:2669 */         int i = ((Integer)localLocalFrame.get(0)).intValue();
/* 2687:2670 */         localObject7 = localOFX151BPWServicesBean
/* 2688:2671 */           .getMostUsedPayees(
/* 2689:2672 */           i);
/* 2690:     */         
/* 2691:2674 */         localLocalFrame.setResult(localObject7);
/* 2692:     */       }
/* 2693:     */       catch (Throwable localThrowable19)
/* 2694:     */       {
/* 2695:2678 */         localThrowable19.printStackTrace(Jaguar.log);
/* 2696:2679 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/* 2697:2680 */         return localThrowable19.getClass().getName();
/* 2698:     */       }
/* 2699:     */     case 19: 
/* 2700:     */       try
/* 2701:     */       {
/* 2702:2689 */         String str8 = (String)localLocalFrame.get(0);
/* 2703:2690 */         localObject7 = localOFX151BPWServicesBean
/* 2704:2691 */           .getPreferredPayees(
/* 2705:2692 */           str8);
/* 2706:     */         
/* 2707:2694 */         localLocalFrame.setResult(localObject7);
/* 2708:     */       }
/* 2709:     */       catch (Throwable localThrowable20)
/* 2710:     */       {
/* 2711:2698 */         localThrowable20.printStackTrace(Jaguar.log);
/* 2712:2699 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/* 2713:2700 */         return localThrowable20.getClass().getName();
/* 2714:     */       }
/* 2715:     */     case 20: 
/* 2716:     */       try
/* 2717:     */       {
/* 2718:2709 */         String str9 = (String)localLocalFrame.get(0);
/* 2719:     */         
/* 2720:2711 */         i3 = ((Integer)localLocalFrame.get(1)).intValue();
/* 2721:2712 */         localObject9 = localOFX151BPWServicesBean
/* 2722:2713 */           .getPendingPmtsByCustomerID(
/* 2723:2714 */           str9, 
/* 2724:2715 */           i3);
/* 2725:     */         
/* 2726:2717 */         localLocalFrame.setResult(localObject9);
/* 2727:     */       }
/* 2728:     */       catch (Throwable localThrowable21)
/* 2729:     */       {
/* 2730:2721 */         localThrowable21.printStackTrace(Jaguar.log);
/* 2731:2722 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/* 2732:2723 */         return localThrowable21.getClass().getName();
/* 2733:     */       }
/* 2734:     */     case 21: 
/* 2735:     */       try
/* 2736:     */       {
/* 2737:2732 */         String str10 = (String)localLocalFrame.get(0);
/* 2738:     */         
/* 2739:2734 */         i3 = ((Integer)localLocalFrame.get(1)).intValue();
/* 2740:     */         
/* 2741:2736 */         int i6 = ((Integer)localLocalFrame.get(2)).intValue();
/* 2742:2737 */         localObject13 = localOFX151BPWServicesBean
/* 2743:2738 */           .getPendingPmtsAndHistoryByCustomerID(
/* 2744:2739 */           str10, 
/* 2745:2740 */           i3, 
/* 2746:2741 */           i6);
/* 2747:     */         
/* 2748:2743 */         localLocalFrame.setResult(localObject13);
/* 2749:     */       }
/* 2750:     */       catch (Throwable localThrowable22)
/* 2751:     */       {
/* 2752:2747 */         localThrowable22.printStackTrace(Jaguar.log);
/* 2753:2748 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/* 2754:2749 */         return localThrowable22.getClass().getName();
/* 2755:     */       }
/* 2756:     */     case 22: 
/* 2757:     */       try
/* 2758:     */       {
/* 2759:2758 */         String str11 = (String)localLocalFrame.get(0);
/* 2760:     */         
/* 2761:2760 */         i3 = ((Integer)localLocalFrame.get(1)).intValue();
/* 2762:2761 */         TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV1 = localOFX151BPWServicesBean
/* 2763:2762 */           .getPendingIntrasByCustomerID(
/* 2764:2763 */           str11, 
/* 2765:2764 */           i3);
/* 2766:     */         
/* 2767:2766 */         localLocalFrame.setResult(arrayOfTypeIntraSyncRsV1);
/* 2768:     */       }
/* 2769:     */       catch (Throwable localThrowable23)
/* 2770:     */       {
/* 2771:2770 */         localThrowable23.printStackTrace(Jaguar.log);
/* 2772:2771 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/* 2773:2772 */         return localThrowable23.getClass().getName();
/* 2774:     */       }
/* 2775:     */     case 23: 
/* 2776:     */       try
/* 2777:     */       {
/* 2778:2781 */         String str12 = (String)localLocalFrame.get(0);
/* 2779:     */         
/* 2780:2783 */         i3 = ((Integer)localLocalFrame.get(1)).intValue();
/* 2781:     */         
/* 2782:2785 */         int i7 = ((Integer)localLocalFrame.get(2)).intValue();
/* 2783:2786 */         localObject13 = localOFX151BPWServicesBean
/* 2784:2787 */           .getPendingIntrasAndHistoryByCustomerID(
/* 2785:2788 */           str12, 
/* 2786:2789 */           i3, 
/* 2787:2790 */           i7);
/* 2788:     */         
/* 2789:2792 */         localLocalFrame.setResult(localObject13);
/* 2790:     */       }
/* 2791:     */       catch (Throwable localThrowable24)
/* 2792:     */       {
/* 2793:2796 */         localThrowable24.printStackTrace(Jaguar.log);
/* 2794:2797 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/* 2795:2798 */         return localThrowable24.getClass().getName();
/* 2796:     */       }
/* 2797:     */     case 24: 
/* 2798:     */       try
/* 2799:     */       {
/* 2800:     */         TypePmtSyncRqV1 localTypePmtSyncRqV11;
/* 2801:2807 */         if (!bool1)
/* 2802:     */         {
/* 2803:2809 */           localTypePmtSyncRqV11 = (TypePmtSyncRqV1)localLocalFrame.get(0);
/* 2804:     */         }
/* 2805:     */         else
/* 2806:     */         {
/* 2807:2813 */           localObject8 = localLocalFrame.get(0);
/* 2808:2814 */           localTypePmtSyncRqV11 = (TypePmtSyncRqV1)ObjectVal.clone(localObject8);
/* 2809:     */         }
/* 2810:2817 */         if (!bool1)
/* 2811:     */         {
/* 2812:2819 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 2813:     */         }
/* 2814:     */         else
/* 2815:     */         {
/* 2816:2823 */           Object localObject10 = localLocalFrame.get(1);
/* 2817:2824 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject10);
/* 2818:     */         }
/* 2819:2827 */         int i8 = ((Integer)localLocalFrame.get(2)).intValue();
/* 2820:2828 */         localObject13 = localOFX151BPWServicesBean
/* 2821:2829 */           .getPendingPmts(
/* 2822:2830 */           localTypePmtSyncRqV11, 
/* 2823:2831 */           (TypeUserData)localObject8, 
/* 2824:2832 */           i8);
/* 2825:     */         
/* 2826:2834 */         localLocalFrame.setResult(localObject13);
/* 2827:     */       }
/* 2828:     */       catch (Throwable localThrowable25)
/* 2829:     */       {
/* 2830:2838 */         localThrowable25.printStackTrace(Jaguar.log);
/* 2831:2839 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/* 2832:2840 */         return localThrowable25.getClass().getName();
/* 2833:     */       }
/* 2834:     */     case 25: 
/* 2835:     */       try
/* 2836:     */       {
/* 2837:     */         TypeIntraSyncRqV1 localTypeIntraSyncRqV11;
/* 2838:2849 */         if (!bool1)
/* 2839:     */         {
/* 2840:2851 */           localTypeIntraSyncRqV11 = (TypeIntraSyncRqV1)localLocalFrame.get(0);
/* 2841:     */         }
/* 2842:     */         else
/* 2843:     */         {
/* 2844:2855 */           localObject8 = localLocalFrame.get(0);
/* 2845:2856 */           localTypeIntraSyncRqV11 = (TypeIntraSyncRqV1)ObjectVal.clone(localObject8);
/* 2846:     */         }
/* 2847:2859 */         if (!bool1)
/* 2848:     */         {
/* 2849:2861 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 2850:     */         }
/* 2851:     */         else
/* 2852:     */         {
/* 2853:2865 */           Object localObject11 = localLocalFrame.get(1);
/* 2854:2866 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject11);
/* 2855:     */         }
/* 2856:2869 */         int i9 = ((Integer)localLocalFrame.get(2)).intValue();
/* 2857:2870 */         localObject13 = localOFX151BPWServicesBean
/* 2858:2871 */           .getPendingIntras(
/* 2859:2872 */           localTypeIntraSyncRqV11, 
/* 2860:2873 */           (TypeUserData)localObject8, 
/* 2861:2874 */           i9);
/* 2862:     */         
/* 2863:2876 */         localLocalFrame.setResult(localObject13);
/* 2864:     */       }
/* 2865:     */       catch (Throwable localThrowable26)
/* 2866:     */       {
/* 2867:2880 */         localThrowable26.printStackTrace(Jaguar.log);
/* 2868:2881 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/* 2869:2882 */         return localThrowable26.getClass().getName();
/* 2870:     */       }
/* 2871:     */     case 26: 
/* 2872:     */       try
/* 2873:     */       {
/* 2874:2891 */         String str13 = (String)localLocalFrame.get(0);
/* 2875:2892 */         localObject8 = localOFX151BPWServicesBean
/* 2876:2893 */           .getPmtStatus(
/* 2877:2894 */           str13);
/* 2878:     */         
/* 2879:2896 */         localLocalFrame.setResult(localObject8);
/* 2880:     */       }
/* 2881:     */       catch (Throwable localThrowable27)
/* 2882:     */       {
/* 2883:2900 */         localThrowable27.printStackTrace(Jaguar.log);
/* 2884:2901 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/* 2885:2902 */         return localThrowable27.getClass().getName();
/* 2886:     */       }
/* 2887:     */     case 27: 
/* 2888:     */       try
/* 2889:     */       {
/* 2890:2911 */         String str14 = (String)localLocalFrame.get(0);
/* 2891:     */         
/* 2892:2913 */         localObject8 = (String)localLocalFrame.get(1);
/* 2893:2914 */         boolean bool2 = localOFX151BPWServicesBean
/* 2894:2915 */           .checkPayeeEditMask(
/* 2895:2916 */           str14, 
/* 2896:2917 */           (String)localObject8);
/* 2897:     */         
/* 2898:2919 */         localLocalFrame.setResult(bool2);
/* 2899:     */       }
/* 2900:     */       catch (Throwable localThrowable28)
/* 2901:     */       {
/* 2902:2923 */         localThrowable28.printStackTrace(Jaguar.log);
/* 2903:2924 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/* 2904:2925 */         return localThrowable28.getClass().getName();
/* 2905:     */       }
/* 2906:     */     case 28: 
/* 2907:     */       try
/* 2908:     */       {
/* 2909:     */         IntraTrnRslt[] arrayOfIntraTrnRslt;
/* 2910:2934 */         if (!bool1)
/* 2911:     */         {
/* 2912:2936 */           arrayOfIntraTrnRslt = (IntraTrnRslt[])localLocalFrame.get(0);
/* 2913:     */         }
/* 2914:     */         else
/* 2915:     */         {
/* 2916:2940 */           localObject8 = localLocalFrame.get(0);
/* 2917:2941 */           arrayOfIntraTrnRslt = (IntraTrnRslt[])ObjectVal.clone(localObject8);
/* 2918:     */         }
/* 2919:2944 */         localOFX151BPWServicesBean.processIntraTrnRslt(
/* 2920:2945 */           arrayOfIntraTrnRslt);
/* 2921:     */       }
/* 2922:     */       catch (Throwable localThrowable29)
/* 2923:     */       {
/* 2924:2950 */         localThrowable29.printStackTrace(Jaguar.log);
/* 2925:2951 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/* 2926:2952 */         return localThrowable29.getClass().getName();
/* 2927:     */       }
/* 2928:     */     case 29: 
/* 2929:     */       try
/* 2930:     */       {
/* 2931:     */         PmtTrnRslt[] arrayOfPmtTrnRslt;
/* 2932:2961 */         if (!bool1)
/* 2933:     */         {
/* 2934:2963 */           arrayOfPmtTrnRslt = (PmtTrnRslt[])localLocalFrame.get(0);
/* 2935:     */         }
/* 2936:     */         else
/* 2937:     */         {
/* 2938:2967 */           localObject8 = localLocalFrame.get(0);
/* 2939:2968 */           arrayOfPmtTrnRslt = (PmtTrnRslt[])ObjectVal.clone(localObject8);
/* 2940:     */         }
/* 2941:2971 */         localOFX151BPWServicesBean.processPmtTrnRslt(
/* 2942:2972 */           arrayOfPmtTrnRslt);
/* 2943:     */       }
/* 2944:     */       catch (Throwable localThrowable30)
/* 2945:     */       {
/* 2946:2977 */         localThrowable30.printStackTrace(Jaguar.log);
/* 2947:2978 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/* 2948:2979 */         return localThrowable30.getClass().getName();
/* 2949:     */       }
/* 2950:     */     case 30: 
/* 2951:     */       try
/* 2952:     */       {
/* 2953:     */         CustPayeeRslt[] arrayOfCustPayeeRslt;
/* 2954:2988 */         if (!bool1)
/* 2955:     */         {
/* 2956:2990 */           arrayOfCustPayeeRslt = (CustPayeeRslt[])localLocalFrame.get(0);
/* 2957:     */         }
/* 2958:     */         else
/* 2959:     */         {
/* 2960:2994 */           localObject8 = localLocalFrame.get(0);
/* 2961:2995 */           arrayOfCustPayeeRslt = (CustPayeeRslt[])ObjectVal.clone(localObject8);
/* 2962:     */         }
/* 2963:2998 */         localOFX151BPWServicesBean.processCustPayeeRslt(
/* 2964:2999 */           arrayOfCustPayeeRslt);
/* 2965:     */       }
/* 2966:     */       catch (Throwable localThrowable31)
/* 2967:     */       {
/* 2968:3004 */         localThrowable31.printStackTrace(Jaguar.log);
/* 2969:3005 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/* 2970:3006 */         return localThrowable31.getClass().getName();
/* 2971:     */       }
/* 2972:     */     case 31: 
/* 2973:     */       try
/* 2974:     */       {
/* 2975:     */         FundsAllocRslt[] arrayOfFundsAllocRslt1;
/* 2976:3015 */         if (!bool1)
/* 2977:     */         {
/* 2978:3017 */           arrayOfFundsAllocRslt1 = (FundsAllocRslt[])localLocalFrame.get(0);
/* 2979:     */         }
/* 2980:     */         else
/* 2981:     */         {
/* 2982:3021 */           localObject8 = localLocalFrame.get(0);
/* 2983:3022 */           arrayOfFundsAllocRslt1 = (FundsAllocRslt[])ObjectVal.clone(localObject8);
/* 2984:     */         }
/* 2985:3025 */         localOFX151BPWServicesBean.processFundAllocRslt(
/* 2986:3026 */           arrayOfFundsAllocRslt1);
/* 2987:     */       }
/* 2988:     */       catch (Throwable localThrowable32)
/* 2989:     */       {
/* 2990:3031 */         localThrowable32.printStackTrace(Jaguar.log);
/* 2991:3032 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/* 2992:3033 */         return localThrowable32.getClass().getName();
/* 2993:     */       }
/* 2994:     */     case 32: 
/* 2995:     */       try
/* 2996:     */       {
/* 2997:     */         FundsAllocRslt[] arrayOfFundsAllocRslt2;
/* 2998:3042 */         if (!bool1)
/* 2999:     */         {
/* 3000:3044 */           arrayOfFundsAllocRslt2 = (FundsAllocRslt[])localLocalFrame.get(0);
/* 3001:     */         }
/* 3002:     */         else
/* 3003:     */         {
/* 3004:3048 */           localObject8 = localLocalFrame.get(0);
/* 3005:3049 */           arrayOfFundsAllocRslt2 = (FundsAllocRslt[])ObjectVal.clone(localObject8);
/* 3006:     */         }
/* 3007:3052 */         localOFX151BPWServicesBean.processFundRevertRslt(
/* 3008:3053 */           arrayOfFundsAllocRslt2);
/* 3009:     */       }
/* 3010:     */       catch (Throwable localThrowable33)
/* 3011:     */       {
/* 3012:3058 */         localThrowable33.printStackTrace(Jaguar.log);
/* 3013:3059 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/* 3014:3060 */         return localThrowable33.getClass().getName();
/* 3015:     */       }
/* 3016:     */     case 33: 
/* 3017:     */       try
/* 3018:     */       {
/* 3019:     */         PayeeRslt[] arrayOfPayeeRslt;
/* 3020:3069 */         if (!bool1)
/* 3021:     */         {
/* 3022:3071 */           arrayOfPayeeRslt = (PayeeRslt[])localLocalFrame.get(0);
/* 3023:     */         }
/* 3024:     */         else
/* 3025:     */         {
/* 3026:3075 */           localObject8 = localLocalFrame.get(0);
/* 3027:3076 */           arrayOfPayeeRslt = (PayeeRslt[])ObjectVal.clone(localObject8);
/* 3028:     */         }
/* 3029:3079 */         localOFX151BPWServicesBean.processPayeeRslt(
/* 3030:3080 */           arrayOfPayeeRslt);
/* 3031:     */       }
/* 3032:     */       catch (Throwable localThrowable34)
/* 3033:     */       {
/* 3034:3085 */         localThrowable34.printStackTrace(Jaguar.log);
/* 3035:3086 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/* 3036:3087 */         return localThrowable34.getClass().getName();
/* 3037:     */       }
/* 3038:     */     case 34: 
/* 3039:     */       try
/* 3040:     */       {
/* 3041:     */         PayeeInfo localPayeeInfo1;
/* 3042:3096 */         if (!bool1)
/* 3043:     */         {
/* 3044:3098 */           localPayeeInfo1 = (PayeeInfo)localLocalFrame.get(0);
/* 3045:     */         }
/* 3046:     */         else
/* 3047:     */         {
/* 3048:3102 */           localObject8 = localLocalFrame.get(0);
/* 3049:3103 */           localPayeeInfo1 = (PayeeInfo)ObjectVal.clone(localObject8);
/* 3050:     */         }
/* 3051:3105 */         localObject8 = 
/* 3052:3106 */           localOFX151BPWServicesBean.addPayeeFromBackend(
/* 3053:3107 */           localPayeeInfo1);
/* 3054:     */         
/* 3055:3109 */         localLocalFrame.setResult(localObject8);
/* 3056:     */       }
/* 3057:     */       catch (Throwable localThrowable35)
/* 3058:     */       {
/* 3059:3113 */         localThrowable35.printStackTrace(Jaguar.log);
/* 3060:3114 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/* 3061:3115 */         return localThrowable35.getClass().getName();
/* 3062:     */       }
/* 3063:     */     case 35: 
/* 3064:     */       try
/* 3065:     */       {
/* 3066:     */         PayeeInfo localPayeeInfo2;
/* 3067:3124 */         if (!bool1)
/* 3068:     */         {
/* 3069:3126 */           localPayeeInfo2 = (PayeeInfo)localLocalFrame.get(0);
/* 3070:     */         }
/* 3071:     */         else
/* 3072:     */         {
/* 3073:3130 */           localObject8 = localLocalFrame.get(0);
/* 3074:3131 */           localPayeeInfo2 = (PayeeInfo)ObjectVal.clone(localObject8);
/* 3075:     */         }
/* 3076:3133 */         localObject8 = 
/* 3077:3134 */           localOFX151BPWServicesBean.updatePayeeFromBackend(
/* 3078:3135 */           localPayeeInfo2);
/* 3079:     */         
/* 3080:3137 */         localLocalFrame.setResult(localObject8);
/* 3081:     */       }
/* 3082:     */       catch (Throwable localThrowable36)
/* 3083:     */       {
/* 3084:3141 */         localThrowable36.printStackTrace(Jaguar.log);
/* 3085:3142 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/* 3086:3143 */         return localThrowable36.getClass().getName();
/* 3087:     */       }
/* 3088:     */     case 36: 
/* 3089:     */       try
/* 3090:     */       {
/* 3091:     */         PayeeRouteInfo localPayeeRouteInfo;
/* 3092:3152 */         if (!bool1)
/* 3093:     */         {
/* 3094:3154 */           localPayeeRouteInfo = (PayeeRouteInfo)localLocalFrame.get(0);
/* 3095:     */         }
/* 3096:     */         else
/* 3097:     */         {
/* 3098:3158 */           localObject8 = localLocalFrame.get(0);
/* 3099:3159 */           localPayeeRouteInfo = (PayeeRouteInfo)ObjectVal.clone(localObject8);
/* 3100:     */         }
/* 3101:3162 */         localOFX151BPWServicesBean.addPayeeRouteInfo(
/* 3102:3163 */           localPayeeRouteInfo);
/* 3103:     */       }
/* 3104:     */       catch (Throwable localThrowable37)
/* 3105:     */       {
/* 3106:3168 */         localThrowable37.printStackTrace(Jaguar.log);
/* 3107:3169 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable37, true);
/* 3108:3170 */         return localThrowable37.getClass().getName();
/* 3109:     */       }
/* 3110:     */     case 37: 
/* 3111:     */       try
/* 3112:     */       {
/* 3113:     */         TypeIntraSyncRqV1 localTypeIntraSyncRqV12;
/* 3114:3179 */         if (!bool1)
/* 3115:     */         {
/* 3116:3181 */           localTypeIntraSyncRqV12 = (TypeIntraSyncRqV1)localLocalFrame.get(0);
/* 3117:     */         }
/* 3118:     */         else
/* 3119:     */         {
/* 3120:3185 */           localObject8 = localLocalFrame.get(0);
/* 3121:3186 */           localTypeIntraSyncRqV12 = (TypeIntraSyncRqV1)ObjectVal.clone(localObject8);
/* 3122:     */         }
/* 3123:3189 */         if (!bool1)
/* 3124:     */         {
/* 3125:3191 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3126:     */         }
/* 3127:     */         else
/* 3128:     */         {
/* 3129:3195 */           localObject12 = localLocalFrame.get(1);
/* 3130:3196 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3131:     */         }
/* 3132:3198 */         localObject12 = 
/* 3133:3199 */           localOFX151BPWServicesBean.processIntraSyncRqV1(
/* 3134:3200 */           localTypeIntraSyncRqV12, 
/* 3135:3201 */           (TypeUserData)localObject8);
/* 3136:     */         
/* 3137:3203 */         localLocalFrame.setResult(localObject12);
/* 3138:     */       }
/* 3139:     */       catch (Throwable localThrowable38)
/* 3140:     */       {
/* 3141:3207 */         localThrowable38.printStackTrace(Jaguar.log);
/* 3142:3208 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable38, true);
/* 3143:3209 */         return localThrowable38.getClass().getName();
/* 3144:     */       }
/* 3145:     */     case 38: 
/* 3146:     */       try
/* 3147:     */       {
/* 3148:     */         TypeIntraTrnRqV1 localTypeIntraTrnRqV1;
/* 3149:3218 */         if (!bool1)
/* 3150:     */         {
/* 3151:3220 */           localTypeIntraTrnRqV1 = (TypeIntraTrnRqV1)localLocalFrame.get(0);
/* 3152:     */         }
/* 3153:     */         else
/* 3154:     */         {
/* 3155:3224 */           localObject8 = localLocalFrame.get(0);
/* 3156:3225 */           localTypeIntraTrnRqV1 = (TypeIntraTrnRqV1)ObjectVal.clone(localObject8);
/* 3157:     */         }
/* 3158:3228 */         if (!bool1)
/* 3159:     */         {
/* 3160:3230 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3161:     */         }
/* 3162:     */         else
/* 3163:     */         {
/* 3164:3234 */           localObject12 = localLocalFrame.get(1);
/* 3165:3235 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3166:     */         }
/* 3167:3237 */         localObject12 = 
/* 3168:3238 */           localOFX151BPWServicesBean.processIntraTrnRqV1(
/* 3169:3239 */           localTypeIntraTrnRqV1, 
/* 3170:3240 */           (TypeUserData)localObject8);
/* 3171:     */         
/* 3172:3242 */         localLocalFrame.setResult(localObject12);
/* 3173:     */       }
/* 3174:     */       catch (Throwable localThrowable39)
/* 3175:     */       {
/* 3176:3246 */         localThrowable39.printStackTrace(Jaguar.log);
/* 3177:3247 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable39, true);
/* 3178:3248 */         return localThrowable39.getClass().getName();
/* 3179:     */       }
/* 3180:     */     case 39: 
/* 3181:     */       try
/* 3182:     */       {
/* 3183:     */         TypePayeeSyncRqV1 localTypePayeeSyncRqV1;
/* 3184:3257 */         if (!bool1)
/* 3185:     */         {
/* 3186:3259 */           localTypePayeeSyncRqV1 = (TypePayeeSyncRqV1)localLocalFrame.get(0);
/* 3187:     */         }
/* 3188:     */         else
/* 3189:     */         {
/* 3190:3263 */           localObject8 = localLocalFrame.get(0);
/* 3191:3264 */           localTypePayeeSyncRqV1 = (TypePayeeSyncRqV1)ObjectVal.clone(localObject8);
/* 3192:     */         }
/* 3193:3267 */         if (!bool1)
/* 3194:     */         {
/* 3195:3269 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3196:     */         }
/* 3197:     */         else
/* 3198:     */         {
/* 3199:3273 */           localObject12 = localLocalFrame.get(1);
/* 3200:3274 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3201:     */         }
/* 3202:3276 */         localObject12 = 
/* 3203:3277 */           localOFX151BPWServicesBean.processPayeeSyncRqV1(
/* 3204:3278 */           localTypePayeeSyncRqV1, 
/* 3205:3279 */           (TypeUserData)localObject8);
/* 3206:     */         
/* 3207:3281 */         localLocalFrame.setResult(localObject12);
/* 3208:     */       }
/* 3209:     */       catch (Throwable localThrowable40)
/* 3210:     */       {
/* 3211:3285 */         localThrowable40.printStackTrace(Jaguar.log);
/* 3212:3286 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable40, true);
/* 3213:3287 */         return localThrowable40.getClass().getName();
/* 3214:     */       }
/* 3215:     */     case 40: 
/* 3216:     */       try
/* 3217:     */       {
/* 3218:     */         TypePayeeTrnRqV1 localTypePayeeTrnRqV1;
/* 3219:3296 */         if (!bool1)
/* 3220:     */         {
/* 3221:3298 */           localTypePayeeTrnRqV1 = (TypePayeeTrnRqV1)localLocalFrame.get(0);
/* 3222:     */         }
/* 3223:     */         else
/* 3224:     */         {
/* 3225:3302 */           localObject8 = localLocalFrame.get(0);
/* 3226:3303 */           localTypePayeeTrnRqV1 = (TypePayeeTrnRqV1)ObjectVal.clone(localObject8);
/* 3227:     */         }
/* 3228:3306 */         if (!bool1)
/* 3229:     */         {
/* 3230:3308 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3231:     */         }
/* 3232:     */         else
/* 3233:     */         {
/* 3234:3312 */           localObject12 = localLocalFrame.get(1);
/* 3235:3313 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3236:     */         }
/* 3237:3315 */         localObject12 = 
/* 3238:3316 */           localOFX151BPWServicesBean.processPayeeTrnRqV1(
/* 3239:3317 */           localTypePayeeTrnRqV1, 
/* 3240:3318 */           (TypeUserData)localObject8);
/* 3241:     */         
/* 3242:3320 */         localLocalFrame.setResult(localObject12);
/* 3243:     */       }
/* 3244:     */       catch (Throwable localThrowable41)
/* 3245:     */       {
/* 3246:3324 */         localThrowable41.printStackTrace(Jaguar.log);
/* 3247:3325 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable41, true);
/* 3248:3326 */         return localThrowable41.getClass().getName();
/* 3249:     */       }
/* 3250:     */     case 41: 
/* 3251:     */       try
/* 3252:     */       {
/* 3253:     */         TypePmtInqTrnRqV1 localTypePmtInqTrnRqV1;
/* 3254:3335 */         if (!bool1)
/* 3255:     */         {
/* 3256:3337 */           localTypePmtInqTrnRqV1 = (TypePmtInqTrnRqV1)localLocalFrame.get(0);
/* 3257:     */         }
/* 3258:     */         else
/* 3259:     */         {
/* 3260:3341 */           localObject8 = localLocalFrame.get(0);
/* 3261:3342 */           localTypePmtInqTrnRqV1 = (TypePmtInqTrnRqV1)ObjectVal.clone(localObject8);
/* 3262:     */         }
/* 3263:3345 */         if (!bool1)
/* 3264:     */         {
/* 3265:3347 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3266:     */         }
/* 3267:     */         else
/* 3268:     */         {
/* 3269:3351 */           localObject12 = localLocalFrame.get(1);
/* 3270:3352 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3271:     */         }
/* 3272:3354 */         localObject12 = 
/* 3273:3355 */           localOFX151BPWServicesBean.processPmtInqTrnRqV1(
/* 3274:3356 */           localTypePmtInqTrnRqV1, 
/* 3275:3357 */           (TypeUserData)localObject8);
/* 3276:     */         
/* 3277:3359 */         localLocalFrame.setResult(localObject12);
/* 3278:     */       }
/* 3279:     */       catch (Throwable localThrowable42)
/* 3280:     */       {
/* 3281:3363 */         localThrowable42.printStackTrace(Jaguar.log);
/* 3282:3364 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable42, true);
/* 3283:3365 */         return localThrowable42.getClass().getName();
/* 3284:     */       }
/* 3285:     */     case 42: 
/* 3286:     */       try
/* 3287:     */       {
/* 3288:     */         TypePmtSyncRqV1 localTypePmtSyncRqV12;
/* 3289:3374 */         if (!bool1)
/* 3290:     */         {
/* 3291:3376 */           localTypePmtSyncRqV12 = (TypePmtSyncRqV1)localLocalFrame.get(0);
/* 3292:     */         }
/* 3293:     */         else
/* 3294:     */         {
/* 3295:3380 */           localObject8 = localLocalFrame.get(0);
/* 3296:3381 */           localTypePmtSyncRqV12 = (TypePmtSyncRqV1)ObjectVal.clone(localObject8);
/* 3297:     */         }
/* 3298:3384 */         if (!bool1)
/* 3299:     */         {
/* 3300:3386 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3301:     */         }
/* 3302:     */         else
/* 3303:     */         {
/* 3304:3390 */           localObject12 = localLocalFrame.get(1);
/* 3305:3391 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3306:     */         }
/* 3307:3393 */         localObject12 = 
/* 3308:3394 */           localOFX151BPWServicesBean.processPmtSyncRqV1(
/* 3309:3395 */           localTypePmtSyncRqV12, 
/* 3310:3396 */           (TypeUserData)localObject8);
/* 3311:     */         
/* 3312:3398 */         localLocalFrame.setResult(localObject12);
/* 3313:     */       }
/* 3314:     */       catch (Throwable localThrowable43)
/* 3315:     */       {
/* 3316:3402 */         localThrowable43.printStackTrace(Jaguar.log);
/* 3317:3403 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable43, true);
/* 3318:3404 */         return localThrowable43.getClass().getName();
/* 3319:     */       }
/* 3320:     */     case 43: 
/* 3321:     */       try
/* 3322:     */       {
/* 3323:     */         TypePmtTrnRqV1 localTypePmtTrnRqV1;
/* 3324:3413 */         if (!bool1)
/* 3325:     */         {
/* 3326:3415 */           localTypePmtTrnRqV1 = (TypePmtTrnRqV1)localLocalFrame.get(0);
/* 3327:     */         }
/* 3328:     */         else
/* 3329:     */         {
/* 3330:3419 */           localObject8 = localLocalFrame.get(0);
/* 3331:3420 */           localTypePmtTrnRqV1 = (TypePmtTrnRqV1)ObjectVal.clone(localObject8);
/* 3332:     */         }
/* 3333:3423 */         if (!bool1)
/* 3334:     */         {
/* 3335:3425 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3336:     */         }
/* 3337:     */         else
/* 3338:     */         {
/* 3339:3429 */           localObject12 = localLocalFrame.get(1);
/* 3340:3430 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3341:     */         }
/* 3342:3432 */         localObject12 = 
/* 3343:3433 */           localOFX151BPWServicesBean.processPmtTrnRqV1(
/* 3344:3434 */           localTypePmtTrnRqV1, 
/* 3345:3435 */           (TypeUserData)localObject8);
/* 3346:     */         
/* 3347:3437 */         localLocalFrame.setResult(localObject12);
/* 3348:     */       }
/* 3349:     */       catch (Throwable localThrowable44)
/* 3350:     */       {
/* 3351:3441 */         localThrowable44.printStackTrace(Jaguar.log);
/* 3352:3442 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable44, true);
/* 3353:3443 */         return localThrowable44.getClass().getName();
/* 3354:     */       }
/* 3355:     */     case 44: 
/* 3356:     */       try
/* 3357:     */       {
/* 3358:     */         TypeRecIntraSyncRqV1 localTypeRecIntraSyncRqV1;
/* 3359:3452 */         if (!bool1)
/* 3360:     */         {
/* 3361:3454 */           localTypeRecIntraSyncRqV1 = (TypeRecIntraSyncRqV1)localLocalFrame.get(0);
/* 3362:     */         }
/* 3363:     */         else
/* 3364:     */         {
/* 3365:3458 */           localObject8 = localLocalFrame.get(0);
/* 3366:3459 */           localTypeRecIntraSyncRqV1 = (TypeRecIntraSyncRqV1)ObjectVal.clone(localObject8);
/* 3367:     */         }
/* 3368:3462 */         if (!bool1)
/* 3369:     */         {
/* 3370:3464 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3371:     */         }
/* 3372:     */         else
/* 3373:     */         {
/* 3374:3468 */           localObject12 = localLocalFrame.get(1);
/* 3375:3469 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3376:     */         }
/* 3377:3471 */         localObject12 = 
/* 3378:3472 */           localOFX151BPWServicesBean.processRecIntraSyncRqV1(
/* 3379:3473 */           localTypeRecIntraSyncRqV1, 
/* 3380:3474 */           (TypeUserData)localObject8);
/* 3381:     */         
/* 3382:3476 */         localLocalFrame.setResult(localObject12);
/* 3383:     */       }
/* 3384:     */       catch (Throwable localThrowable45)
/* 3385:     */       {
/* 3386:3480 */         localThrowable45.printStackTrace(Jaguar.log);
/* 3387:3481 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable45, true);
/* 3388:3482 */         return localThrowable45.getClass().getName();
/* 3389:     */       }
/* 3390:     */     case 45: 
/* 3391:     */       try
/* 3392:     */       {
/* 3393:     */         TypeRecIntraTrnRqV1 localTypeRecIntraTrnRqV1;
/* 3394:3491 */         if (!bool1)
/* 3395:     */         {
/* 3396:3493 */           localTypeRecIntraTrnRqV1 = (TypeRecIntraTrnRqV1)localLocalFrame.get(0);
/* 3397:     */         }
/* 3398:     */         else
/* 3399:     */         {
/* 3400:3497 */           localObject8 = localLocalFrame.get(0);
/* 3401:3498 */           localTypeRecIntraTrnRqV1 = (TypeRecIntraTrnRqV1)ObjectVal.clone(localObject8);
/* 3402:     */         }
/* 3403:3501 */         if (!bool1)
/* 3404:     */         {
/* 3405:3503 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3406:     */         }
/* 3407:     */         else
/* 3408:     */         {
/* 3409:3507 */           localObject12 = localLocalFrame.get(1);
/* 3410:3508 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3411:     */         }
/* 3412:3510 */         localObject12 = 
/* 3413:3511 */           localOFX151BPWServicesBean.processRecIntraTrnRqV1(
/* 3414:3512 */           localTypeRecIntraTrnRqV1, 
/* 3415:3513 */           (TypeUserData)localObject8);
/* 3416:     */         
/* 3417:3515 */         localLocalFrame.setResult(localObject12);
/* 3418:     */       }
/* 3419:     */       catch (Throwable localThrowable46)
/* 3420:     */       {
/* 3421:3519 */         localThrowable46.printStackTrace(Jaguar.log);
/* 3422:3520 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable46, true);
/* 3423:3521 */         return localThrowable46.getClass().getName();
/* 3424:     */       }
/* 3425:     */     case 46: 
/* 3426:     */       try
/* 3427:     */       {
/* 3428:     */         TypeRecPmtSyncRqV1 localTypeRecPmtSyncRqV1;
/* 3429:3530 */         if (!bool1)
/* 3430:     */         {
/* 3431:3532 */           localTypeRecPmtSyncRqV1 = (TypeRecPmtSyncRqV1)localLocalFrame.get(0);
/* 3432:     */         }
/* 3433:     */         else
/* 3434:     */         {
/* 3435:3536 */           localObject8 = localLocalFrame.get(0);
/* 3436:3537 */           localTypeRecPmtSyncRqV1 = (TypeRecPmtSyncRqV1)ObjectVal.clone(localObject8);
/* 3437:     */         }
/* 3438:3540 */         if (!bool1)
/* 3439:     */         {
/* 3440:3542 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3441:     */         }
/* 3442:     */         else
/* 3443:     */         {
/* 3444:3546 */           localObject12 = localLocalFrame.get(1);
/* 3445:3547 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3446:     */         }
/* 3447:3549 */         localObject12 = 
/* 3448:3550 */           localOFX151BPWServicesBean.processRecPmtSyncRqV1(
/* 3449:3551 */           localTypeRecPmtSyncRqV1, 
/* 3450:3552 */           (TypeUserData)localObject8);
/* 3451:     */         
/* 3452:3554 */         localLocalFrame.setResult(localObject12);
/* 3453:     */       }
/* 3454:     */       catch (Throwable localThrowable47)
/* 3455:     */       {
/* 3456:3558 */         localThrowable47.printStackTrace(Jaguar.log);
/* 3457:3559 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable47, true);
/* 3458:3560 */         return localThrowable47.getClass().getName();
/* 3459:     */       }
/* 3460:     */     case 47: 
/* 3461:     */       try
/* 3462:     */       {
/* 3463:     */         TypeRecPmtTrnRqV1 localTypeRecPmtTrnRqV1;
/* 3464:3569 */         if (!bool1)
/* 3465:     */         {
/* 3466:3571 */           localTypeRecPmtTrnRqV1 = (TypeRecPmtTrnRqV1)localLocalFrame.get(0);
/* 3467:     */         }
/* 3468:     */         else
/* 3469:     */         {
/* 3470:3575 */           localObject8 = localLocalFrame.get(0);
/* 3471:3576 */           localTypeRecPmtTrnRqV1 = (TypeRecPmtTrnRqV1)ObjectVal.clone(localObject8);
/* 3472:     */         }
/* 3473:3579 */         if (!bool1)
/* 3474:     */         {
/* 3475:3581 */           localObject8 = (TypeUserData)localLocalFrame.get(1);
/* 3476:     */         }
/* 3477:     */         else
/* 3478:     */         {
/* 3479:3585 */           localObject12 = localLocalFrame.get(1);
/* 3480:3586 */           localObject8 = (TypeUserData)ObjectVal.clone(localObject12);
/* 3481:     */         }
/* 3482:3588 */         localObject12 = 
/* 3483:3589 */           localOFX151BPWServicesBean.processRecPmtTrnRqV1(
/* 3484:3590 */           localTypeRecPmtTrnRqV1, 
/* 3485:3591 */           (TypeUserData)localObject8);
/* 3486:     */         
/* 3487:3593 */         localLocalFrame.setResult(localObject12);
/* 3488:     */       }
/* 3489:     */       catch (Throwable localThrowable48)
/* 3490:     */       {
/* 3491:3597 */         localThrowable48.printStackTrace(Jaguar.log);
/* 3492:3598 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable48, true);
/* 3493:3599 */         return localThrowable48.getClass().getName();
/* 3494:     */       }
/* 3495:     */     case 48: 
/* 3496:     */       try
/* 3497:     */       {
/* 3498:3608 */         String str15 = (String)localLocalFrame.get(0);
/* 3499:     */         
/* 3500:3610 */         int i4 = ((Integer)localLocalFrame.get(1)).intValue();
/* 3501:3611 */         localObject12 = localOFX151BPWServicesBean
/* 3502:3612 */           .getPayeeNames(
/* 3503:3613 */           str15, 
/* 3504:3614 */           i4);
/* 3505:     */         
/* 3506:3616 */         localLocalFrame.setResult(localObject12);
/* 3507:     */       }
/* 3508:     */       catch (Throwable localThrowable49)
/* 3509:     */       {
/* 3510:3620 */         localThrowable49.printStackTrace(Jaguar.log);
/* 3511:3621 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable49, true);
/* 3512:3622 */         return localThrowable49.getClass().getName();
/* 3513:     */       }
/* 3514:     */     case 49: 
/* 3515:     */       try
/* 3516:     */       {
/* 3517:3631 */         String str16 = (String)localLocalFrame.get(0);
/* 3518:3632 */         arrayOfString6 = localOFX151BPWServicesBean
/* 3519:3633 */           .getPayeeNames(
/* 3520:3634 */           str16);
/* 3521:     */         
/* 3522:3636 */         localLocalFrame.setResult(arrayOfString6);
/* 3523:     */       }
/* 3524:     */       catch (Throwable localThrowable50)
/* 3525:     */       {
/* 3526:3640 */         localThrowable50.printStackTrace(Jaguar.log);
/* 3527:3641 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable50, true);
/* 3528:3642 */         return localThrowable50.getClass().getName();
/* 3529:     */       }
/* 3530:     */     case 50: 
/* 3531:     */       try
/* 3532:     */       {
/* 3533:3651 */         String str17 = (String)localLocalFrame.get(0);
/* 3534:3652 */         arrayOfString6 = localOFX151BPWServicesBean
/* 3535:3653 */           .getPayeeIDs(
/* 3536:3654 */           str17);
/* 3537:     */         
/* 3538:3656 */         localLocalFrame.setResult(arrayOfString6);
/* 3539:     */       }
/* 3540:     */       catch (Throwable localThrowable51)
/* 3541:     */       {
/* 3542:3660 */         localThrowable51.printStackTrace(Jaguar.log);
/* 3543:3661 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable51, true);
/* 3544:3662 */         return localThrowable51.getClass().getName();
/* 3545:     */       }
/* 3546:     */     default: 
/* 3547:3668 */       return 
/* 3548:3669 */         localInvoke1(
/* 3549:3670 */         local_ServerRequest, 
/* 3550:3671 */         paramInputStream, 
/* 3551:3672 */         paramOutputStream, 
/* 3552:3673 */         localOFX151BPWServicesBean, 
/* 3553:3674 */         localLocalFrame, 
/* 3554:3675 */         localInteger, 
/* 3555:3676 */         bool1);
/* 3556:     */     }
/* 3557:3680 */     return null;
/* 3558:     */   }
/* 3559:     */   
/* 3560:     */   private static String localInvoke1(_ServerRequest param_ServerRequest, InputStream paramInputStream, OutputStream paramOutputStream, OFX151BPWServicesBean paramOFX151BPWServicesBean, LocalFrame paramLocalFrame, Integer paramInteger, boolean paramBoolean)
/* 3561:     */   {
/* 3562:     */     Object localObject11;
/* 3563:     */     Object localObject1;
/* 3564:     */     Object localObject2;
/* 3565:     */     int m;
/* 3566:     */     Object localObject6;
/* 3567:     */     Object localObject8;
/* 3568:     */     Object localObject10;
/* 3569:     */     boolean bool;
/* 3570:3692 */     switch (paramInteger.intValue())
/* 3571:     */     {
/* 3572:     */     case 51: 
/* 3573:     */       try
/* 3574:     */       {
/* 3575:3699 */         String str1 = (String)paramLocalFrame.get(0);
/* 3576:     */         
/* 3577:3701 */         int j = ((Integer)paramLocalFrame.get(1)).intValue();
/* 3578:3702 */         localObject11 = paramOFX151BPWServicesBean
/* 3579:3703 */           .getPayees(
/* 3580:3704 */           str1, 
/* 3581:3705 */           j);
/* 3582:     */         
/* 3583:3707 */         paramLocalFrame.setResult(localObject11);
/* 3584:     */       }
/* 3585:     */       catch (Throwable localThrowable1)
/* 3586:     */       {
/* 3587:3711 */         localThrowable1.printStackTrace(Jaguar.log);
/* 3588:3712 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/* 3589:3713 */         return localThrowable1.getClass().getName();
/* 3590:     */       }
/* 3591:     */     case 52: 
/* 3592:     */       try
/* 3593:     */       {
/* 3594:3722 */         String str2 = (String)paramLocalFrame.get(0);
/* 3595:3723 */         localObject1 = paramOFX151BPWServicesBean
/* 3596:3724 */           .getPayees(
/* 3597:3725 */           str2);
/* 3598:     */         
/* 3599:3727 */         paramLocalFrame.setResult(localObject1);
/* 3600:     */       }
/* 3601:     */       catch (Throwable localThrowable2)
/* 3602:     */       {
/* 3603:3731 */         localThrowable2.printStackTrace(Jaguar.log);
/* 3604:3732 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/* 3605:3733 */         return localThrowable2.getClass().getName();
/* 3606:     */       }
/* 3607:     */     case 53: 
/* 3608:     */       try
/* 3609:     */       {
/* 3610:3742 */         String str3 = (String)paramLocalFrame.get(0);
/* 3611:3743 */         localObject1 = paramOFX151BPWServicesBean
/* 3612:3744 */           .searchGlobalPayees(
/* 3613:3745 */           str3);
/* 3614:     */         
/* 3615:3747 */         paramLocalFrame.setResult(localObject1);
/* 3616:     */       }
/* 3617:     */       catch (Throwable localThrowable3)
/* 3618:     */       {
/* 3619:3751 */         localThrowable3.printStackTrace(Jaguar.log);
/* 3620:3752 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/* 3621:3753 */         return localThrowable3.getClass().getName();
/* 3622:     */       }
/* 3623:     */     case 54: 
/* 3624:     */       try
/* 3625:     */       {
/* 3626:     */         PayeeInfo localPayeeInfo1;
/* 3627:3762 */         if (!paramBoolean)
/* 3628:     */         {
/* 3629:3764 */           localPayeeInfo1 = (PayeeInfo)paramLocalFrame.get(0);
/* 3630:     */         }
/* 3631:     */         else
/* 3632:     */         {
/* 3633:3768 */           localObject1 = paramLocalFrame.get(0);
/* 3634:3769 */           localPayeeInfo1 = (PayeeInfo)ObjectVal.clone(localObject1);
/* 3635:     */         }
/* 3636:3772 */         int k = ((Integer)paramLocalFrame.get(1)).intValue();
/* 3637:3773 */         localObject11 = paramOFX151BPWServicesBean
/* 3638:3774 */           .updatePayee(
/* 3639:3775 */           localPayeeInfo1, 
/* 3640:3776 */           k);
/* 3641:     */         
/* 3642:3778 */         paramLocalFrame.setResult(localObject11);
/* 3643:     */       }
/* 3644:     */       catch (Throwable localThrowable4)
/* 3645:     */       {
/* 3646:3782 */         localThrowable4.printStackTrace(Jaguar.log);
/* 3647:3783 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/* 3648:3784 */         return localThrowable4.getClass().getName();
/* 3649:     */       }
/* 3650:     */     case 55: 
/* 3651:     */       try
/* 3652:     */       {
/* 3653:     */         PayeeInfo localPayeeInfo2;
/* 3654:3793 */         if (!paramBoolean)
/* 3655:     */         {
/* 3656:3795 */           localPayeeInfo2 = (PayeeInfo)paramLocalFrame.get(0);
/* 3657:     */         }
/* 3658:     */         else
/* 3659:     */         {
/* 3660:3799 */           localObject2 = paramLocalFrame.get(0);
/* 3661:3800 */           localPayeeInfo2 = (PayeeInfo)ObjectVal.clone(localObject2);
/* 3662:     */         }
/* 3663:3803 */         if (!paramBoolean)
/* 3664:     */         {
/* 3665:3805 */           localObject2 = (PayeeRouteInfo)paramLocalFrame.get(1);
/* 3666:     */         }
/* 3667:     */         else
/* 3668:     */         {
/* 3669:3809 */           localObject11 = paramLocalFrame.get(1);
/* 3670:3810 */           localObject2 = (PayeeRouteInfo)ObjectVal.clone(localObject11);
/* 3671:     */         }
/* 3672:3813 */         paramOFX151BPWServicesBean.updatePayee(
/* 3673:3814 */           localPayeeInfo2, 
/* 3674:3815 */           (PayeeRouteInfo)localObject2);
/* 3675:     */       }
/* 3676:     */       catch (Throwable localThrowable5)
/* 3677:     */       {
/* 3678:3820 */         localThrowable5.printStackTrace(Jaguar.log);
/* 3679:3821 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/* 3680:3822 */         return localThrowable5.getClass().getName();
/* 3681:     */       }
/* 3682:     */     case 56: 
/* 3683:     */       try
/* 3684:     */       {
/* 3685:3831 */         String str4 = (String)paramLocalFrame.get(0);
/* 3686:3832 */         paramOFX151BPWServicesBean
/* 3687:3833 */           .deletePayee(
/* 3688:3834 */           str4);
/* 3689:     */       }
/* 3690:     */       catch (Throwable localThrowable6)
/* 3691:     */       {
/* 3692:3839 */         localThrowable6.printStackTrace(Jaguar.log);
/* 3693:3840 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/* 3694:3841 */         return localThrowable6.getClass().getName();
/* 3695:     */       }
/* 3696:     */     case 57: 
/* 3697:     */       try
/* 3698:     */       {
/* 3699:     */         String[] arrayOfString1;
/* 3700:3850 */         if (!paramBoolean)
/* 3701:     */         {
/* 3702:3852 */           arrayOfString1 = (String[])paramLocalFrame.get(0);
/* 3703:     */         }
/* 3704:     */         else
/* 3705:     */         {
/* 3706:3856 */           localObject2 = paramLocalFrame.get(0);
/* 3707:3857 */           arrayOfString1 = (String[])ObjectVal.clone(localObject2);
/* 3708:     */         }
/* 3709:3860 */         paramOFX151BPWServicesBean.deletePayees(
/* 3710:3861 */           arrayOfString1);
/* 3711:     */       }
/* 3712:     */       catch (Throwable localThrowable7)
/* 3713:     */       {
/* 3714:3866 */         localThrowable7.printStackTrace(Jaguar.log);
/* 3715:3867 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/* 3716:3868 */         return localThrowable7.getClass().getName();
/* 3717:     */       }
/* 3718:     */     case 58: 
/* 3719:     */       try
/* 3720:     */       {
/* 3721:3877 */         String str5 = (String)paramLocalFrame.get(0);
/* 3722:3878 */         localObject2 = paramOFX151BPWServicesBean
/* 3723:3879 */           .findPayeeByID(
/* 3724:3880 */           str5);
/* 3725:     */         
/* 3726:3882 */         paramLocalFrame.setResult(localObject2);
/* 3727:     */       }
/* 3728:     */       catch (Throwable localThrowable8)
/* 3729:     */       {
/* 3730:3886 */         localThrowable8.printStackTrace(Jaguar.log);
/* 3731:3887 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/* 3732:3888 */         return localThrowable8.getClass().getName();
/* 3733:     */       }
/* 3734:     */     case 59: 
/* 3735:     */       try
/* 3736:     */       {
/* 3737:3897 */         String str6 = (String)paramLocalFrame.get(0);
/* 3738:     */         
/* 3739:3899 */         localObject2 = (String)paramLocalFrame.get(1);
/* 3740:3900 */         paramOFX151BPWServicesBean
/* 3741:3901 */           .setPayeeStatus(
/* 3742:3902 */           str6, 
/* 3743:3903 */           (String)localObject2);
/* 3744:     */       }
/* 3745:     */       catch (Throwable localThrowable9)
/* 3746:     */       {
/* 3747:3908 */         localThrowable9.printStackTrace(Jaguar.log);
/* 3748:3909 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/* 3749:3910 */         return localThrowable9.getClass().getName();
/* 3750:     */       }
/* 3751:     */     case 60: 
/* 3752:     */       try
/* 3753:     */       {
/* 3754:3919 */         int i = ((Integer)paramLocalFrame.get(0)).intValue();
/* 3755:3920 */         m = paramOFX151BPWServicesBean
/* 3756:3921 */           .getSmartDate(
/* 3757:3922 */           i);
/* 3758:     */         
/* 3759:3924 */         paramLocalFrame.setResult(m);
/* 3760:     */       }
/* 3761:     */       catch (Throwable localThrowable10)
/* 3762:     */       {
/* 3763:3928 */         localThrowable10.printStackTrace(Jaguar.log);
/* 3764:3929 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/* 3765:3930 */         return localThrowable10.getClass().getName();
/* 3766:     */       }
/* 3767:     */     case 61: 
/* 3768:     */       try
/* 3769:     */       {
/* 3770:3939 */         String str7 = (String)paramLocalFrame.get(0);
/* 3771:     */         
/* 3772:3941 */         m = ((Integer)paramLocalFrame.get(1)).intValue();
/* 3773:3942 */         localObject11 = paramOFX151BPWServicesBean
/* 3774:3943 */           .getGlobalPayees(
/* 3775:3944 */           str7, 
/* 3776:3945 */           m);
/* 3777:     */         
/* 3778:3947 */         paramLocalFrame.setResult(localObject11);
/* 3779:     */       }
/* 3780:     */       catch (Throwable localThrowable11)
/* 3781:     */       {
/* 3782:3951 */         localThrowable11.printStackTrace(Jaguar.log);
/* 3783:3952 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/* 3784:3953 */         return localThrowable11.getClass().getName();
/* 3785:     */       }
/* 3786:     */     case 62: 
/* 3787:     */       try
/* 3788:     */       {
/* 3789:     */         PayeeInfo localPayeeInfo3;
/* 3790:3962 */         if (!paramBoolean)
/* 3791:     */         {
/* 3792:3964 */           localPayeeInfo3 = (PayeeInfo)paramLocalFrame.get(0);
/* 3793:     */         }
/* 3794:     */         else
/* 3795:     */         {
/* 3796:3968 */           Object localObject3 = paramLocalFrame.get(0);
/* 3797:3969 */           localPayeeInfo3 = (PayeeInfo)ObjectVal.clone(localObject3);
/* 3798:     */         }
/* 3799:3972 */         int n = ((Integer)paramLocalFrame.get(1)).intValue();
/* 3800:3973 */         localObject11 = paramOFX151BPWServicesBean
/* 3801:3974 */           .addPayee(
/* 3802:3975 */           localPayeeInfo3, 
/* 3803:3976 */           n);
/* 3804:     */         
/* 3805:3978 */         paramLocalFrame.setResult(localObject11);
/* 3806:     */       }
/* 3807:     */       catch (Throwable localThrowable12)
/* 3808:     */       {
/* 3809:3982 */         localThrowable12.printStackTrace(Jaguar.log);
/* 3810:3983 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/* 3811:3984 */         return localThrowable12.getClass().getName();
/* 3812:     */       }
/* 3813:     */     case 63: 
/* 3814:     */       try
/* 3815:     */       {
/* 3816:     */         ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo1;
/* 3817:3993 */         if (!paramBoolean)
/* 3818:     */         {
/* 3819:3995 */           arrayOfConsumerCrossRefInfo1 = (ConsumerCrossRefInfo[])paramLocalFrame.get(0);
/* 3820:     */         }
/* 3821:     */         else
/* 3822:     */         {
/* 3823:3999 */           Object localObject4 = paramLocalFrame.get(0);
/* 3824:4000 */           arrayOfConsumerCrossRefInfo1 = (ConsumerCrossRefInfo[])ObjectVal.clone(localObject4);
/* 3825:     */         }
/* 3826:4002 */         int i1 = paramOFX151BPWServicesBean
/* 3827:4003 */           .addConsumerCrossRef(
/* 3828:4004 */           arrayOfConsumerCrossRefInfo1);
/* 3829:     */         
/* 3830:4006 */         paramLocalFrame.setResult(i1);
/* 3831:     */       }
/* 3832:     */       catch (Throwable localThrowable13)
/* 3833:     */       {
/* 3834:4010 */         localThrowable13.printStackTrace(Jaguar.log);
/* 3835:4011 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/* 3836:4012 */         return localThrowable13.getClass().getName();
/* 3837:     */       }
/* 3838:     */     case 64: 
/* 3839:     */       try
/* 3840:     */       {
/* 3841:     */         ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo2;
/* 3842:4021 */         if (!paramBoolean)
/* 3843:     */         {
/* 3844:4023 */           arrayOfConsumerCrossRefInfo2 = (ConsumerCrossRefInfo[])paramLocalFrame.get(0);
/* 3845:     */         }
/* 3846:     */         else
/* 3847:     */         {
/* 3848:4027 */           Object localObject5 = paramLocalFrame.get(0);
/* 3849:4028 */           arrayOfConsumerCrossRefInfo2 = (ConsumerCrossRefInfo[])ObjectVal.clone(localObject5);
/* 3850:     */         }
/* 3851:4030 */         int i2 = paramOFX151BPWServicesBean
/* 3852:4031 */           .deleteConsumerCrossRef(
/* 3853:4032 */           arrayOfConsumerCrossRefInfo2);
/* 3854:     */         
/* 3855:4034 */         paramLocalFrame.setResult(i2);
/* 3856:     */       }
/* 3857:     */       catch (Throwable localThrowable14)
/* 3858:     */       {
/* 3859:4038 */         localThrowable14.printStackTrace(Jaguar.log);
/* 3860:4039 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/* 3861:4040 */         return localThrowable14.getClass().getName();
/* 3862:     */       }
/* 3863:     */     case 65: 
/* 3864:     */       try
/* 3865:     */       {
/* 3866:     */         String[] arrayOfString2;
/* 3867:4049 */         if (!paramBoolean)
/* 3868:     */         {
/* 3869:4051 */           arrayOfString2 = (String[])paramLocalFrame.get(0);
/* 3870:     */         }
/* 3871:     */         else
/* 3872:     */         {
/* 3873:4055 */           localObject6 = paramLocalFrame.get(0);
/* 3874:4056 */           arrayOfString2 = (String[])ObjectVal.clone(localObject6);
/* 3875:     */         }
/* 3876:4058 */         localObject6 = 
/* 3877:4059 */           paramOFX151BPWServicesBean.getConsumerCrossRef(
/* 3878:4060 */           arrayOfString2);
/* 3879:     */         
/* 3880:4062 */         paramLocalFrame.setResult(localObject6);
/* 3881:     */       }
/* 3882:     */       catch (Throwable localThrowable15)
/* 3883:     */       {
/* 3884:4066 */         localThrowable15.printStackTrace(Jaguar.log);
/* 3885:4067 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/* 3886:4068 */         return localThrowable15.getClass().getName();
/* 3887:     */       }
/* 3888:     */     case 66: 
/* 3889:     */       try
/* 3890:     */       {
/* 3891:     */         CustomerBankInfo[] arrayOfCustomerBankInfo1;
/* 3892:4077 */         if (!paramBoolean)
/* 3893:     */         {
/* 3894:4079 */           arrayOfCustomerBankInfo1 = (CustomerBankInfo[])paramLocalFrame.get(0);
/* 3895:     */         }
/* 3896:     */         else
/* 3897:     */         {
/* 3898:4083 */           localObject6 = paramLocalFrame.get(0);
/* 3899:4084 */           arrayOfCustomerBankInfo1 = (CustomerBankInfo[])ObjectVal.clone(localObject6);
/* 3900:     */         }
/* 3901:4086 */         int i3 = paramOFX151BPWServicesBean
/* 3902:4087 */           .addCustomerBankInfo(
/* 3903:4088 */           arrayOfCustomerBankInfo1);
/* 3904:     */         
/* 3905:4090 */         paramLocalFrame.setResult(i3);
/* 3906:     */       }
/* 3907:     */       catch (Throwable localThrowable16)
/* 3908:     */       {
/* 3909:4094 */         localThrowable16.printStackTrace(Jaguar.log);
/* 3910:4095 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/* 3911:4096 */         return localThrowable16.getClass().getName();
/* 3912:     */       }
/* 3913:     */     case 67: 
/* 3914:     */       try
/* 3915:     */       {
/* 3916:     */         CustomerBankInfo[] arrayOfCustomerBankInfo2;
/* 3917:4105 */         if (!paramBoolean)
/* 3918:     */         {
/* 3919:4107 */           arrayOfCustomerBankInfo2 = (CustomerBankInfo[])paramLocalFrame.get(0);
/* 3920:     */         }
/* 3921:     */         else
/* 3922:     */         {
/* 3923:4111 */           Object localObject7 = paramLocalFrame.get(0);
/* 3924:4112 */           arrayOfCustomerBankInfo2 = (CustomerBankInfo[])ObjectVal.clone(localObject7);
/* 3925:     */         }
/* 3926:4114 */         int i4 = paramOFX151BPWServicesBean
/* 3927:4115 */           .deleteCustomerBankInfo(
/* 3928:4116 */           arrayOfCustomerBankInfo2);
/* 3929:     */         
/* 3930:4118 */         paramLocalFrame.setResult(i4);
/* 3931:     */       }
/* 3932:     */       catch (Throwable localThrowable17)
/* 3933:     */       {
/* 3934:4122 */         localThrowable17.printStackTrace(Jaguar.log);
/* 3935:4123 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/* 3936:4124 */         return localThrowable17.getClass().getName();
/* 3937:     */       }
/* 3938:     */     case 68: 
/* 3939:     */       try
/* 3940:     */       {
/* 3941:     */         String[] arrayOfString3;
/* 3942:4133 */         if (!paramBoolean)
/* 3943:     */         {
/* 3944:4135 */           arrayOfString3 = (String[])paramLocalFrame.get(0);
/* 3945:     */         }
/* 3946:     */         else
/* 3947:     */         {
/* 3948:4139 */           localObject8 = paramLocalFrame.get(0);
/* 3949:4140 */           arrayOfString3 = (String[])ObjectVal.clone(localObject8);
/* 3950:     */         }
/* 3951:4142 */         localObject8 = 
/* 3952:4143 */           paramOFX151BPWServicesBean.getCustomerBankInfo(
/* 3953:4144 */           arrayOfString3);
/* 3954:     */         
/* 3955:4146 */         paramLocalFrame.setResult(localObject8);
/* 3956:     */       }
/* 3957:     */       catch (Throwable localThrowable18)
/* 3958:     */       {
/* 3959:4150 */         localThrowable18.printStackTrace(Jaguar.log);
/* 3960:4151 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/* 3961:4152 */         return localThrowable18.getClass().getName();
/* 3962:     */       }
/* 3963:     */     case 69: 
/* 3964:     */       try
/* 3965:     */       {
/* 3966:     */         CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo1;
/* 3967:4161 */         if (!paramBoolean)
/* 3968:     */         {
/* 3969:4163 */           arrayOfCustomerProductAccessInfo1 = (CustomerProductAccessInfo[])paramLocalFrame.get(0);
/* 3970:     */         }
/* 3971:     */         else
/* 3972:     */         {
/* 3973:4167 */           localObject8 = paramLocalFrame.get(0);
/* 3974:4168 */           arrayOfCustomerProductAccessInfo1 = (CustomerProductAccessInfo[])ObjectVal.clone(localObject8);
/* 3975:     */         }
/* 3976:4170 */         int i5 = paramOFX151BPWServicesBean
/* 3977:4171 */           .addCustomerProductAccessInfo(
/* 3978:4172 */           arrayOfCustomerProductAccessInfo1);
/* 3979:     */         
/* 3980:4174 */         paramLocalFrame.setResult(i5);
/* 3981:     */       }
/* 3982:     */       catch (Throwable localThrowable19)
/* 3983:     */       {
/* 3984:4178 */         localThrowable19.printStackTrace(Jaguar.log);
/* 3985:4179 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/* 3986:4180 */         return localThrowable19.getClass().getName();
/* 3987:     */       }
/* 3988:     */     case 70: 
/* 3989:     */       try
/* 3990:     */       {
/* 3991:     */         CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo2;
/* 3992:4189 */         if (!paramBoolean)
/* 3993:     */         {
/* 3994:4191 */           arrayOfCustomerProductAccessInfo2 = (CustomerProductAccessInfo[])paramLocalFrame.get(0);
/* 3995:     */         }
/* 3996:     */         else
/* 3997:     */         {
/* 3998:4195 */           Object localObject9 = paramLocalFrame.get(0);
/* 3999:4196 */           arrayOfCustomerProductAccessInfo2 = (CustomerProductAccessInfo[])ObjectVal.clone(localObject9);
/* 4000:     */         }
/* 4001:4198 */         int i6 = paramOFX151BPWServicesBean
/* 4002:4199 */           .deleteCustomerProductAccessInfo(
/* 4003:4200 */           arrayOfCustomerProductAccessInfo2);
/* 4004:     */         
/* 4005:4202 */         paramLocalFrame.setResult(i6);
/* 4006:     */       }
/* 4007:     */       catch (Throwable localThrowable20)
/* 4008:     */       {
/* 4009:4206 */         localThrowable20.printStackTrace(Jaguar.log);
/* 4010:4207 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/* 4011:4208 */         return localThrowable20.getClass().getName();
/* 4012:     */       }
/* 4013:     */     case 71: 
/* 4014:     */       try
/* 4015:     */       {
/* 4016:     */         String[] arrayOfString4;
/* 4017:4217 */         if (!paramBoolean)
/* 4018:     */         {
/* 4019:4219 */           arrayOfString4 = (String[])paramLocalFrame.get(0);
/* 4020:     */         }
/* 4021:     */         else
/* 4022:     */         {
/* 4023:4223 */           localObject10 = paramLocalFrame.get(0);
/* 4024:4224 */           arrayOfString4 = (String[])ObjectVal.clone(localObject10);
/* 4025:     */         }
/* 4026:4226 */         localObject10 = 
/* 4027:4227 */           paramOFX151BPWServicesBean.getCustomerProductAccessInfo(
/* 4028:4228 */           arrayOfString4);
/* 4029:     */         
/* 4030:4230 */         paramLocalFrame.setResult(localObject10);
/* 4031:     */       }
/* 4032:     */       catch (Throwable localThrowable21)
/* 4033:     */       {
/* 4034:4234 */         localThrowable21.printStackTrace(Jaguar.log);
/* 4035:4235 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/* 4036:4236 */         return localThrowable21.getClass().getName();
/* 4037:     */       }
/* 4038:     */     case 72: 
/* 4039:     */       try
/* 4040:     */       {
/* 4041:4245 */         String str8 = (String)paramLocalFrame.get(0);
/* 4042:     */         
/* 4043:4247 */         localObject10 = (String)paramLocalFrame.get(1);
/* 4044:4248 */         bool = paramOFX151BPWServicesBean
/* 4045:4249 */           .validateMetavanteCustAcctByConsumerID(
/* 4046:4250 */           str8, 
/* 4047:4251 */           (String)localObject10);
/* 4048:     */         
/* 4049:4253 */         paramLocalFrame.setResult(bool);
/* 4050:     */       }
/* 4051:     */       catch (Throwable localThrowable22)
/* 4052:     */       {
/* 4053:4257 */         localThrowable22.printStackTrace(Jaguar.log);
/* 4054:4258 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/* 4055:4259 */         return localThrowable22.getClass().getName();
/* 4056:     */       }
/* 4057:     */     case 73: 
/* 4058:     */       try
/* 4059:     */       {
/* 4060:4268 */         String str9 = (String)paramLocalFrame.get(0);
/* 4061:     */         
/* 4062:4270 */         localObject10 = (String)paramLocalFrame.get(1);
/* 4063:4271 */         bool = paramOFX151BPWServicesBean
/* 4064:4272 */           .validateMetavanteCustAcctByCustomerID(
/* 4065:4273 */           str9, 
/* 4066:4274 */           (String)localObject10);
/* 4067:     */         
/* 4068:4276 */         paramLocalFrame.setResult(bool);
/* 4069:     */       }
/* 4070:     */       catch (Throwable localThrowable23)
/* 4071:     */       {
/* 4072:4280 */         localThrowable23.printStackTrace(Jaguar.log);
/* 4073:4281 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/* 4074:4282 */         return localThrowable23.getClass().getName();
/* 4075:     */       }
/* 4076:     */     case 74: 
/* 4077:     */       try
/* 4078:     */       {
/* 4079:     */         BPWHist localBPWHist1;
/* 4080:4291 */         if (!paramBoolean)
/* 4081:     */         {
/* 4082:4293 */           localBPWHist1 = (BPWHist)paramLocalFrame.get(0);
/* 4083:     */         }
/* 4084:     */         else
/* 4085:     */         {
/* 4086:4297 */           localObject10 = paramLocalFrame.get(0);
/* 4087:4298 */           localBPWHist1 = (BPWHist)ObjectVal.clone(localObject10);
/* 4088:     */         }
/* 4089:4300 */         localObject10 = 
/* 4090:4301 */           paramOFX151BPWServicesBean.getPmtHistory(
/* 4091:4302 */           localBPWHist1);
/* 4092:     */         
/* 4093:4304 */         paramLocalFrame.setResult(localObject10);
/* 4094:     */       }
/* 4095:     */       catch (Throwable localThrowable24)
/* 4096:     */       {
/* 4097:4308 */         localThrowable24.printStackTrace(Jaguar.log);
/* 4098:4309 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/* 4099:4310 */         return localThrowable24.getClass().getName();
/* 4100:     */       }
/* 4101:     */     case 75: 
/* 4102:     */       try
/* 4103:     */       {
/* 4104:     */         BPWHist localBPWHist2;
/* 4105:4319 */         if (!paramBoolean)
/* 4106:     */         {
/* 4107:4321 */           localBPWHist2 = (BPWHist)paramLocalFrame.get(0);
/* 4108:     */         }
/* 4109:     */         else
/* 4110:     */         {
/* 4111:4325 */           localObject10 = paramLocalFrame.get(0);
/* 4112:4326 */           localBPWHist2 = (BPWHist)ObjectVal.clone(localObject10);
/* 4113:     */         }
/* 4114:4328 */         localObject10 = 
/* 4115:4329 */           paramOFX151BPWServicesBean.getIntraHistory(
/* 4116:4330 */           localBPWHist2);
/* 4117:     */         
/* 4118:4332 */         paramLocalFrame.setResult(localObject10);
/* 4119:     */       }
/* 4120:     */       catch (Throwable localThrowable25)
/* 4121:     */       {
/* 4122:4336 */         localThrowable25.printStackTrace(Jaguar.log);
/* 4123:4337 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/* 4124:4338 */         return localThrowable25.getClass().getName();
/* 4125:     */       }
/* 4126:     */     case 76: 
/* 4127:     */       try
/* 4128:     */       {
/* 4129:     */         String[] arrayOfString5;
/* 4130:4347 */         if (!paramBoolean)
/* 4131:     */         {
/* 4132:4349 */           arrayOfString5 = (String[])paramLocalFrame.get(0);
/* 4133:     */         }
/* 4134:     */         else
/* 4135:     */         {
/* 4136:4353 */           localObject10 = paramLocalFrame.get(0);
/* 4137:4354 */           arrayOfString5 = (String[])ObjectVal.clone(localObject10);
/* 4138:     */         }
/* 4139:4356 */         localObject10 = 
/* 4140:4357 */           paramOFX151BPWServicesBean.getIntraById(
/* 4141:4358 */           arrayOfString5);
/* 4142:     */         
/* 4143:4360 */         paramLocalFrame.setResult(localObject10);
/* 4144:     */       }
/* 4145:     */       catch (Throwable localThrowable26)
/* 4146:     */       {
/* 4147:4364 */         localThrowable26.printStackTrace(Jaguar.log);
/* 4148:4365 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/* 4149:4366 */         return localThrowable26.getClass().getName();
/* 4150:     */       }
/* 4151:     */     case 77: 
/* 4152:     */       try
/* 4153:     */       {
/* 4154:4375 */         String str10 = (String)paramLocalFrame.get(0);
/* 4155:4376 */         localObject10 = paramOFX151BPWServicesBean
/* 4156:4377 */           .getIntraById(
/* 4157:4378 */           str10);
/* 4158:     */         
/* 4159:4380 */         paramLocalFrame.setResult(localObject10);
/* 4160:     */       }
/* 4161:     */       catch (Throwable localThrowable27)
/* 4162:     */       {
/* 4163:4384 */         localThrowable27.printStackTrace(Jaguar.log);
/* 4164:4385 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/* 4165:4386 */         return localThrowable27.getClass().getName();
/* 4166:     */       }
/* 4167:     */     case 78: 
/* 4168:     */       try
/* 4169:     */       {
/* 4170:4395 */         String str11 = (String)paramLocalFrame.get(0);
/* 4171:4396 */         localObject10 = paramOFX151BPWServicesBean
/* 4172:4397 */           .getPmtById(
/* 4173:4398 */           str11);
/* 4174:     */         
/* 4175:4400 */         paramLocalFrame.setResult(localObject10);
/* 4176:     */       }
/* 4177:     */       catch (Throwable localThrowable28)
/* 4178:     */       {
/* 4179:4404 */         localThrowable28.printStackTrace(Jaguar.log);
/* 4180:4405 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/* 4181:4406 */         return localThrowable28.getClass().getName();
/* 4182:     */       }
/* 4183:     */     case 79: 
/* 4184:     */       try
/* 4185:     */       {
/* 4186:     */         String[] arrayOfString6;
/* 4187:4415 */         if (!paramBoolean)
/* 4188:     */         {
/* 4189:4417 */           arrayOfString6 = (String[])paramLocalFrame.get(0);
/* 4190:     */         }
/* 4191:     */         else
/* 4192:     */         {
/* 4193:4421 */           localObject10 = paramLocalFrame.get(0);
/* 4194:4422 */           arrayOfString6 = (String[])ObjectVal.clone(localObject10);
/* 4195:     */         }
/* 4196:4424 */         localObject10 = 
/* 4197:4425 */           paramOFX151BPWServicesBean.getPmtById(
/* 4198:4426 */           arrayOfString6);
/* 4199:     */         
/* 4200:4428 */         paramLocalFrame.setResult(localObject10);
/* 4201:     */       }
/* 4202:     */       catch (Throwable localThrowable29)
/* 4203:     */       {
/* 4204:4432 */         localThrowable29.printStackTrace(Jaguar.log);
/* 4205:4433 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/* 4206:4434 */         return localThrowable29.getClass().getName();
/* 4207:     */       }
/* 4208:     */     case 80: 
/* 4209:     */       try
/* 4210:     */       {
/* 4211:     */         String[] arrayOfString7;
/* 4212:4443 */         if (!paramBoolean)
/* 4213:     */         {
/* 4214:4445 */           arrayOfString7 = (String[])paramLocalFrame.get(0);
/* 4215:     */         }
/* 4216:     */         else
/* 4217:     */         {
/* 4218:4449 */           localObject10 = paramLocalFrame.get(0);
/* 4219:4450 */           arrayOfString7 = (String[])ObjectVal.clone(localObject10);
/* 4220:     */         }
/* 4221:4452 */         localObject10 = 
/* 4222:4453 */           paramOFX151BPWServicesBean.getRecPmtById(
/* 4223:4454 */           arrayOfString7);
/* 4224:     */         
/* 4225:4456 */         paramLocalFrame.setResult(localObject10);
/* 4226:     */       }
/* 4227:     */       catch (Throwable localThrowable30)
/* 4228:     */       {
/* 4229:4460 */         localThrowable30.printStackTrace(Jaguar.log);
/* 4230:4461 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/* 4231:4462 */         return localThrowable30.getClass().getName();
/* 4232:     */       }
/* 4233:     */     case 81: 
/* 4234:     */       try
/* 4235:     */       {
/* 4236:4471 */         String str12 = (String)paramLocalFrame.get(0);
/* 4237:     */         
/* 4238:4473 */         localObject10 = (String)paramLocalFrame.get(1);
/* 4239:4474 */         PayeeInfo localPayeeInfo4 = paramOFX151BPWServicesBean
/* 4240:4475 */           .getPayeeByListId(
/* 4241:4476 */           str12, 
/* 4242:4477 */           (String)localObject10);
/* 4243:     */         
/* 4244:4479 */         paramLocalFrame.setResult(localPayeeInfo4);
/* 4245:     */       }
/* 4246:     */       catch (Throwable localThrowable31)
/* 4247:     */       {
/* 4248:4483 */         if ((localThrowable31 instanceof FFSException))
/* 4249:     */         {
/* 4250:4485 */           paramLocalFrame.setException(localThrowable31);
/* 4251:4486 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4252:4487 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable31);
/* 4253:     */         }
/* 4254:4489 */         localThrowable31.printStackTrace(Jaguar.log);
/* 4255:4490 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/* 4256:4491 */         return localThrowable31.getClass().getName();
/* 4257:     */       }
/* 4258:     */     case 82: 
/* 4259:     */       try
/* 4260:     */       {
/* 4261:4499 */         AccountTypesMap localAccountTypesMap = paramOFX151BPWServicesBean
/* 4262:4500 */           .getAccountTypesMap();
/* 4263:     */         
/* 4264:4502 */         paramLocalFrame.setResult(localAccountTypesMap);
/* 4265:     */       }
/* 4266:     */       catch (Throwable localThrowable32)
/* 4267:     */       {
/* 4268:4506 */         if ((localThrowable32 instanceof FFSException))
/* 4269:     */         {
/* 4270:4508 */           paramLocalFrame.setException(localThrowable32);
/* 4271:4509 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4272:4510 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable32);
/* 4273:     */         }
/* 4274:4512 */         localThrowable32.printStackTrace(Jaguar.log);
/* 4275:4513 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/* 4276:4514 */         return localThrowable32.getClass().getName();
/* 4277:     */       }
/* 4278:     */     }
/* 4279:4519 */     return null;
/* 4280:     */   }
/* 4281:     */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices._sk_OFX151BPWServices_OFX151BPWServicesBean
 * JD-Core Version:    0.7.0.1
 */