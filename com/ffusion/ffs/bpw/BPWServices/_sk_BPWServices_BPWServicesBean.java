/*     1:      */ package com.ffusion.ffs.bpw.BPWServices;
/*     2:      */ 
/*     3:      */ import CtsComponents.ObjectContextHelper;
/*     4:      */ import com.ffusion.ffs.bpw.interfaces.AccountTransactions;
/*     5:      */ import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
/*     6:      */ import com.ffusion.ffs.bpw.interfaces.BPWBankInfoSeqHelper;
/*     7:      */ import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
/*     8:      */ import com.ffusion.ffs.bpw.interfaces.BPWFIInfoSeqHelper;
/*     9:      */ import com.ffusion.ffs.bpw.interfaces.BPWHist;
/*    10:      */ import com.ffusion.ffs.bpw.interfaces.BPWHistHelper;
/*    11:      */ import com.ffusion.ffs.bpw.interfaces.BPWPayeeList;
/*    12:      */ import com.ffusion.ffs.bpw.interfaces.BankingDays;
/*    13:      */ import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo;
/*    14:      */ import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfoList;
/*    15:      */ import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo;
/*    16:      */ import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfoList;
/*    17:      */ import com.ffusion.ffs.bpw.interfaces.CCCompanyInfo;
/*    18:      */ import com.ffusion.ffs.bpw.interfaces.CCCompanyInfoList;
/*    19:      */ import com.ffusion.ffs.bpw.interfaces.CCEntryHistInfo;
/*    20:      */ import com.ffusion.ffs.bpw.interfaces.CCEntryInfo;
/*    21:      */ import com.ffusion.ffs.bpw.interfaces.CCEntrySummaryInfoList;
/*    22:      */ import com.ffusion.ffs.bpw.interfaces.CCLocationInfo;
/*    23:      */ import com.ffusion.ffs.bpw.interfaces.CCLocationInfoList;
/*    24:      */ import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
/*    25:      */ import com.ffusion.ffs.bpw.interfaces.CustomerInfoSeqHelper;
/*    26:      */ import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
/*    27:      */ import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfoSeqHelper;
/*    28:      */ import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
/*    29:      */ import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
/*    30:      */ import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList;
/*    31:      */ import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
/*    32:      */ import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList;
/*    33:      */ import com.ffusion.ffs.bpw.interfaces.LastPaymentInfo;
/*    34:      */ import com.ffusion.ffs.bpw.interfaces.NonBusinessDay;
/*    35:      */ import com.ffusion.ffs.bpw.interfaces.NonBusinessDaySeqHelper;
/*    36:      */ import com.ffusion.ffs.bpw.interfaces.PagingInfo;
/*    37:      */ import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
/*    38:      */ import com.ffusion.ffs.bpw.interfaces.PayeeInfoSeqHelper;
/*    39:      */ import com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo;
/*    40:      */ import com.ffusion.ffs.bpw.interfaces.PmtInfo;
/*    41:      */ import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
/*    42:      */ import com.ffusion.ffs.bpw.interfaces.PmtTrnRsltSeqHelper;
/*    43:      */ import com.ffusion.ffs.bpw.interfaces.RPPSBillerInfo;
/*    44:      */ import com.ffusion.ffs.bpw.interfaces.RPPSBillerInfoSeqHelper;
/*    45:      */ import com.ffusion.ffs.bpw.interfaces.RPPSFIInfo;
/*    46:      */ import com.ffusion.ffs.bpw.interfaces.RecTransferInfo;
/*    47:      */ import com.ffusion.ffs.bpw.interfaces.RecTransferInfoSeqHelper;
/*    48:      */ import com.ffusion.ffs.bpw.interfaces.RecWireInfo;
/*    49:      */ import com.ffusion.ffs.bpw.interfaces.RecWireInfoSeqHelper;
/*    50:      */ import com.ffusion.ffs.bpw.interfaces.TransferBatchInfo;
/*    51:      */ import com.ffusion.ffs.bpw.interfaces.TransferInfo;
/*    52:      */ import com.ffusion.ffs.bpw.interfaces.TransferInfoSeqHelper;
/*    53:      */ import com.ffusion.ffs.bpw.interfaces.WireBatchInfo;
/*    54:      */ import com.ffusion.ffs.bpw.interfaces.WireBatchInfoSeqHelper;
/*    55:      */ import com.ffusion.ffs.bpw.interfaces.WireInfo;
/*    56:      */ import com.ffusion.ffs.bpw.interfaces.WireInfoSeqHelper;
/*    57:      */ import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
/*    58:      */ import com.ffusion.ffs.bpw.interfaces.WirePayeeInfoSeqHelper;
/*    59:      */ import com.ffusion.ffs.bpw.interfaces.WireReleaseInfo;
/*    60:      */ import com.ffusion.ffs.bpw.util.AccountTypesMap;
/*    61:      */ import com.ffusion.ffs.interfaces.FFSException;
/*    62:      */ import com.ffusion.ffs.interfaces.FFSExceptionHelper;
/*    63:      */ import com.sybase.CORBA.LocalFrame;
/*    64:      */ import com.sybase.CORBA.LocalStack;
/*    65:      */ import com.sybase.CORBA.ObjectVal;
/*    66:      */ import com.sybase.CORBA.UserException;
/*    67:      */ import com.sybase.CORBA._ServerRequest;
/*    68:      */ import com.sybase.CORBA.iiop.Connection;
/*    69:      */ import com.sybase.ejb.SessionContext;
/*    70:      */ import com.sybase.ejb.cts.StringSeqHelper;
/*    71:      */ import com.sybase.jaguar.server.Jaguar;
/*    72:      */ import java.util.ArrayList;
/*    73:      */ import java.util.HashMap;
/*    74:      */ import org.omg.CORBA.portable.InputStream;
/*    75:      */ import org.omg.CORBA.portable.OutputStream;
/*    76:      */ 
/*    77:      */ public abstract class _sk_BPWServices_BPWServicesBean
/*    78:      */ {
/*    79:   17 */   private static HashMap _methods = new HashMap(380);
/*    80:      */   private static HashMap _localMethods;
/*    81:      */   private static HashMap _localMethods2;
/*    82:      */   private static final String _RESET = "org.omg.CORBA.BAD_OPERATION";
/*    83:      */   
/*    84:      */   static
/*    85:      */   {
/*    86:   18 */     _methods.put("setSessionContext", new Integer(0));
/*    87:   19 */     _methods.put("ejbCreate", new Integer(1));
/*    88:   20 */     _methods.put("ejbActivate", new Integer(2));
/*    89:   21 */     _methods.put("ejbPassivate", new Integer(3));
/*    90:   22 */     _methods.put("ejbRemove", new Integer(4));
/*    91:   23 */     _methods.put("disconnect", new Integer(5));
/*    92:   24 */     _methods.put("addCustomers", new Integer(6));
/*    93:   25 */     _methods.put("modifyCustomers", new Integer(7));
/*    94:   26 */     _methods.put("deleteCustomers__StringSeq", new Integer(8));
/*    95:   27 */     _methods.put("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(8));
/*    96:   28 */     _methods.put("deleteCustomers__StringSeq__long", new Integer(9));
/*    97:   29 */     _methods.put("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long", new Integer(9));
/*    98:   30 */     _methods.put("deactivateCustomers", new Integer(10));
/*    99:   31 */     _methods.put("activateCustomers", new Integer(11));
/*   100:   32 */     _methods.put("getCustomersInfo", new Integer(12));
/*   101:   33 */     _methods.put("getCustomerByType", new Integer(13));
/*   102:   34 */     _methods.put("getCustomerByFI", new Integer(14));
/*   103:   35 */     _methods.put("getCustomerByCategory", new Integer(15));
/*   104:   36 */     _methods.put("getCustomerByGroup", new Integer(16));
/*   105:   37 */     _methods.put("getCustomerByTypeAndFI", new Integer(17));
/*   106:   38 */     _methods.put("getCustomerByCategoryAndFI", new Integer(18));
/*   107:   39 */     _methods.put("getCustomerByGroupAndFI", new Integer(19));
/*   108:   40 */     _methods.put("getRPPSBillerInfoByFIRPPSId", new Integer(20));
/*   109:   41 */     _methods.put("getRPPSBillerInfoByFIAndBillerRPPSId", new Integer(21));
/*   110:   42 */     _methods.put("addWireTransfer", new Integer(22));
/*   111:   43 */     _methods.put("modWireTransfer", new Integer(23));
/*   112:   44 */     _methods.put("cancWireTransfer", new Integer(24));
/*   113:   45 */     _methods.put("getWireTransferById", new Integer(25));
/*   114:   46 */     _methods.put("getWireTransfer", new Integer(26));
/*   115:   47 */     _methods.put("getWireTransfers", new Integer(27));
/*   116:   48 */     _methods.put("getDuplicateWires", new Integer(28));
/*   117:   49 */     _methods.put("getBatchWires", new Integer(29));
/*   118:   50 */     _methods.put("getWireHistory", new Integer(30));
/*   119:   51 */     _methods.put("getWireHistoryByCustomer", new Integer(31));
/*   120:   52 */     _methods.put("getCombinedWireHistory", new Integer(32));
/*   121:   53 */     _methods.put("getAuditWireTransfer", new Integer(33));
/*   122:   54 */     _methods.put("getAuditWireTransferByExtId", new Integer(34));
/*   123:   55 */     _methods.put("getWireReleaseCount", new Integer(35));
/*   124:   56 */     _methods.put("addWireTransfers", new Integer(36));
/*   125:   57 */     _methods.put("releaseWireTransfer", new Integer(37));
/*   126:   58 */     _methods.put("addRecWireTransfer", new Integer(38));
/*   127:   59 */     _methods.put("modRecWireTransfer", new Integer(39));
/*   128:   60 */     _methods.put("cancRecWireTransfer", new Integer(40));
/*   129:   61 */     _methods.put("getRecWireTransferById__string__boolean", new Integer(41));
/*   130:   62 */     _methods.put("getRecWireTransferById__CORBA_WStringValue__boolean", new Integer(41));
/*   131:   63 */     _methods.put("getRecWireTransferById__string", new Integer(42));
/*   132:   64 */     _methods.put("getRecWireTransferById__CORBA_WStringValue", new Integer(42));
/*   133:   65 */     _methods.put("getRecWireTransfer", new Integer(43));
/*   134:   66 */     _methods.put("getRecWireTransfers", new Integer(44));
/*   135:   67 */     _methods.put("getRecWireHistory", new Integer(45));
/*   136:   68 */     _methods.put("getRecWireHistoryByCustomer", new Integer(46));
/*   137:   69 */     _methods.put("addRecWireTransfers", new Integer(47));
/*   138:   70 */     _methods.put("getWiresConfiguration", new Integer(48));
/*   139:   71 */     _methods.put("_get_wiresConfiguration", new Integer(48));
/*   140:   72 */     _methods.put("addWireTransferBatch", new Integer(49));
/*   141:   73 */     _methods.put("modWireTransferBatch", new Integer(50));
/*   142:   74 */     _methods.put("canWireTransferBatch", new Integer(51));
/*   143:   75 */     _methods.put("getWireTransferBatch", new Integer(52));
/*   144:   76 */     _methods.put("getWireBatchHistory", new Integer(53));
/*   145:   77 */     _methods.put("isWireBatchDeleteable", new Integer(54));
/*   146:   78 */     _methods.put("addWirePayee__WirePayeeInfoSeq", new Integer(55));
/*   147:   79 */     _methods.put("addWirePayee__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_WirePayeeInfo", new Integer(55));
/*   148:   80 */     _methods.put("addWirePayee__WirePayeeInfo", new Integer(56));
/*   149:   81 */     _methods.put("addWirePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_WirePayeeInfo", new Integer(56));
/*   150:   82 */     _methods.put("modWirePayee", new Integer(57));
/*   151:   83 */     _methods.put("canWirePayee", new Integer(58));
/*   152:   84 */     _methods.put("getWirePayee__WirePayeeInfo", new Integer(59));
/*   153:   85 */     _methods.put("getWirePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_WirePayeeInfo", new Integer(59));
/*   154:   86 */     _methods.put("getWirePayee__string", new Integer(60));
/*   155:   87 */     _methods.put("getWirePayee__CORBA_WStringValue", new Integer(60));
/*   156:   88 */     _methods.put("getWirePayeeByType", new Integer(61));
/*   157:   89 */     _methods.put("getWirePayeeByStatus", new Integer(62));
/*   158:   90 */     _methods.put("getWirePayeeByGroup", new Integer(63));
/*   159:   91 */     _methods.put("getWirePayeeByCustomer", new Integer(64));
/*   160:   92 */     _methods.put("addIntermediaryBanksToBeneficiary", new Integer(65));
/*   161:   93 */     _methods.put("delIntermediaryBanksFromBeneficiary", new Integer(66));
/*   162:   94 */     _methods.put("addWireBanks", new Integer(67));
/*   163:   95 */     _methods.put("modWireBanks", new Integer(68));
/*   164:   96 */     _methods.put("delWireBanks", new Integer(69));
/*   165:   97 */     _methods.put("getWireBanks", new Integer(70));
/*   166:   98 */     _methods.put("getWireBanksByRTN", new Integer(71));
/*   167:   99 */     _methods.put("getWireBanksByID", new Integer(72));
/*   168:  100 */     _methods.put("processWireApprovalRslt", new Integer(73));
/*   169:  101 */     _methods.put("processWireBackendlRslt", new Integer(74));
/*   170:  102 */     _methods.put("processWireApprovalRevertRslt", new Integer(75));
/*   171:  103 */     _methods.put("addBPWFIInfo", new Integer(76));
/*   172:  104 */     _methods.put("modBPWFIInfo", new Integer(77));
/*   173:  105 */     _methods.put("canBPWFIInfo", new Integer(78));
/*   174:  106 */     _methods.put("activateBPWFI", new Integer(79));
/*   175:  107 */     _methods.put("getBPWFIInfo__string", new Integer(80));
/*   176:  108 */     _methods.put("getBPWFIInfo__CORBA_WStringValue", new Integer(80));
/*   177:  109 */     _methods.put("getBPWFIInfo__StringSeq", new Integer(81));
/*   178:  110 */     _methods.put("getBPWFIInfo__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(81));
/*   179:  111 */     _methods.put("getBPWFIInfoByType", new Integer(82));
/*   180:  112 */     _methods.put("getBPWFIInfoByStatus", new Integer(83));
/*   181:  113 */     _methods.put("getBPWFIInfoByGroup", new Integer(84));
/*   182:  114 */     _methods.put("getBPWFIInfoByACHId", new Integer(85));
/*   183:  115 */     _methods.put("getBPWFIInfoByRTN", new Integer(86));
/*   184:  116 */     _methods.put("addRPPSFIInfo", new Integer(87));
/*   185:  117 */     _methods.put("getRPPSFIInfoByFIId", new Integer(88));
/*   186:  118 */     _methods.put("getRPPSFIInfoByFIRPPSId", new Integer(89));
/*   187:  119 */     _methods.put("canRPPSFIInfo", new Integer(90));
/*   188:  120 */     _methods.put("activateRPPSFI", new Integer(91));
/*   189:  121 */     _methods.put("modRPPSFIInfo", new Integer(92));
/*   190:  122 */     _methods.put("getSmartDate", new Integer(93));
/*   191:  123 */     _methods.put("processApprovalResult", new Integer(94));
/*   192:  124 */     _methods.put("addCCCompany", new Integer(95));
/*   193:  125 */     _methods.put("cancelCCCompany", new Integer(96));
/*   194:  126 */     _methods.put("modCCCompany", new Integer(97));
/*   195:  127 */     _methods.put("getCCCompany", new Integer(98));
/*   196:  128 */     _methods.put("getCCCompanyList", new Integer(99));
/*   197:  129 */     _methods.put("getNextCashConCutOff", new Integer(100));
/*   198:  130 */     _methods.put("addCCCompanyAcct", new Integer(101));
/*   199:  131 */     _methods.put("cancelCCCompanyAcct", new Integer(102));
/*   200:  132 */     _methods.put("modCCCompanyAcct", new Integer(103));
/*   201:  133 */     _methods.put("getCCCompanyAcct", new Integer(104));
/*   202:  134 */     _methods.put("getCCCompanyAcctList", new Integer(105));
/*   203:  135 */     _methods.put("addCCCompanyCutOff", new Integer(106));
/*   204:  136 */     _methods.put("cancelCCCompanyCutOff", new Integer(107));
/*   205:  137 */     _methods.put("getCCCompanyCutOff", new Integer(108));
/*   206:  138 */     _methods.put("getCCCompanyCutOffList", new Integer(109));
/*   207:  139 */     _methods.put("addCCLocation", new Integer(110));
/*   208:  140 */     _methods.put("cancelCCLocation", new Integer(111));
/*   209:  141 */     _methods.put("modCCLocation", new Integer(112));
/*   210:  142 */     _methods.put("getCCLocation", new Integer(113));
/*   211:  143 */     _methods.put("getCCLocationList", new Integer(114));
/*   212:  144 */     _methods.put("addCCEntry", new Integer(115));
/*   213:  145 */     _methods.put("cancelCCEntry", new Integer(116));
/*   214:  146 */     _methods.put("modCCEntry", new Integer(117));
/*   215:  147 */     _methods.put("getCCEntry", new Integer(118));
/*   216:  148 */     _methods.put("getCCEntryHist", new Integer(119));
/*   217:  149 */     _methods.put("getCCEntrySummaryList", new Integer(120));
/*   218:  150 */     _methods.put("addTransfer", new Integer(121));
/*   219:  151 */     _methods.put("modTransfer", new Integer(122));
/*   220:  152 */     _methods.put("cancTransfer", new Integer(123));
/*   221:  153 */     _methods.put("getTransferBySrvrTId__string__string", new Integer(124));
/*   222:  154 */     _methods.put("getTransferBySrvrTId__CORBA_WStringValue__CORBA_WStringValue", new Integer(124));
/*   223:  155 */     _methods.put("getTransferBySrvrTId__string", new Integer(125));
/*   224:  156 */     _methods.put("getTransferBySrvrTId__CORBA_WStringValue", new Integer(125));
/*   225:  157 */     _methods.put("getTransferByTrackingId", new Integer(126));
/*   226:  158 */     _methods.put("getTransfersBySrvrTId", new Integer(127));
/*   227:  159 */     _methods.put("getTransfersByRecSrvrTId__StringSeq__boolean", new Integer(128));
/*   228:  160 */     _methods.put("getTransfersByRecSrvrTId__org_omg_boxedRMI_CORBA_seq1_WStringValue__boolean", new Integer(128));
/*   229:  161 */     _methods.put("getTransfersByRecSrvrTId__string__boolean", new Integer(129));
/*   230:  162 */     _methods.put("getTransfersByRecSrvrTId__CORBA_WStringValue__boolean", new Integer(129));
/*   231:  163 */     _methods.put("getTransfersByTrackingId", new Integer(130));
/*   232:  164 */     _methods.put("getTransferHistory", new Integer(131));
/*   233:  165 */     _methods.put("addTransfers", new Integer(132));
/*   234:  166 */     _methods.put("addRecTransfer", new Integer(133));
/*   235:  167 */     _methods.put("modRecTransfer", new Integer(134));
/*   236:  168 */     _methods.put("cancRecTransfer", new Integer(135));
/*   237:  169 */     _methods.put("getRecTransferBySrvrTId__string__string", new Integer(136));
/*   238:  170 */     _methods.put("getRecTransferBySrvrTId__CORBA_WStringValue__CORBA_WStringValue", new Integer(136));
/*   239:  171 */     _methods.put("getRecTransferBySrvrTId__string", new Integer(137));
/*   240:  172 */     _methods.put("getRecTransferBySrvrTId__CORBA_WStringValue", new Integer(137));
/*   241:  173 */     _methods.put("getRecTransferByTrackingId", new Integer(138));
/*   242:  174 */     _methods.put("getRecTransfersBySrvrTId", new Integer(139));
/*   243:  175 */     _methods.put("getRecTransfers", new Integer(140));
/*   244:  176 */     _methods.put("getRecTransfersByTrackingId", new Integer(141));
/*   245:  177 */     _methods.put("getRecTransferHistory", new Integer(142));
/*   246:  178 */     _methods.put("addExtTransferCompany", new Integer(143));
/*   247:  179 */     _methods.put("canExtTransferCompany", new Integer(144));
/*   248:  180 */     _methods.put("modExtTransferCompany", new Integer(145));
/*   249:  181 */     _methods.put("getExtTransferCompany", new Integer(146));
/*   250:  182 */     _methods.put("getExtTransferCompanyList", new Integer(147));
/*   251:  183 */     _methods.put("addExtTransferAccount", new Integer(148));
/*   252:  184 */     _methods.put("canExtTransferAccount", new Integer(149));
/*   253:  185 */     _methods.put("modExtTransferAccount", new Integer(150));
/*   254:  186 */     _methods.put("getExtTransferAccount", new Integer(151));
/*   255:  187 */     _methods.put("verifyExtTransferAccount", new Integer(152));
/*   256:  188 */     _methods.put("depositAmountsForVerify", new Integer(153));
/*   257:  189 */     _methods.put("activateExtTransferAcct", new Integer(154));
/*   258:  190 */     _methods.put("inactivateExtTransferAcct", new Integer(155));
/*   259:  191 */     _methods.put("getExtTransferAcctList", new Integer(156));
/*   260:  192 */     _methods.put("getNonBusinessDays", new Integer(157));
/*   261:  193 */     _methods.put("getNonBusinessDaysFromFile", new Integer(158));
/*   262:  194 */     _methods.put("getPagedWire", new Integer(159));
/*   263:  195 */     _methods.put("getPagedTransfer", new Integer(160));
/*   264:  196 */     _methods.put("getPagedBillPayments", new Integer(161));
/*   265:  197 */     _methods.put("getValidTransferDateDue", new Integer(162));
/*   266:  198 */     _methods.put("getPagedCashCon", new Integer(163));
/*   267:  199 */     _methods.put("getPayeeByListId", new Integer(164));
/*   268:  200 */     _methods.put("getAccountTypesMap", new Integer(165));
/*   269:  201 */     _methods.put("modExtTransferAccountPrenoteStatus", new Integer(166));
/*   270:  202 */     _methods.put("getBPWProperty", new Integer(167));
/*   271:  203 */     _methods.put("addTransferBatch", new Integer(168));
/*   272:  204 */     _methods.put("modifyTransferBatch", new Integer(169));
/*   273:  205 */     _methods.put("cancelTransferBatch", new Integer(170));
/*   274:  206 */     _methods.put("getTransferBatchById", new Integer(171));
/*   275:  207 */     _methods.put("accountHasPendingTransfers", new Integer(172));
/*   276:  208 */     _methods.put("addBillPayment", new Integer(173));
/*   277:  209 */     _methods.put("modifyBillPayment", new Integer(174));
/*   278:  210 */     _methods.put("deleteBillPayment", new Integer(175));
/*   279:  211 */     _methods.put("getBillPaymentById", new Integer(176));
/*   280:  212 */     _methods.put("addPaymentBatch", new Integer(177));
/*   281:  213 */     _methods.put("modifyPaymentBatch", new Integer(178));
/*   282:  214 */     _methods.put("deletePaymentBatch", new Integer(179));
/*   283:  215 */     _methods.put("getPaymentBatchById", new Integer(180));
/*   284:  216 */     _methods.put("getLastPayments", new Integer(181));
/*   285:  217 */     _methods.put("getBankingDaysInRange", new Integer(182));
/*   286:  218 */     _methods.put("addCustomerPayee", new Integer(183));
/*   287:  219 */     _methods.put("deleteCustomerPayee", new Integer(184));
/*   288:  220 */     _methods.put("updateCustomerPayee", new Integer(185));
/*   289:  221 */     _methods.put("getCustomerPayees", new Integer(186));
/*   290:  222 */     _methods.put("searchGlobalPayees", new Integer(187));
/*   291:  223 */     _methods.put("getGlobalPayee", new Integer(188));
/*   292:  224 */     _methods.put("processPmtTrnRslt", new Integer(189));
/*   293:      */     
/*   294:      */ 
/*   295:      */ 
/*   296:      */ 
/*   297:      */ 
/*   298:      */ 
/*   299:      */ 
/*   300:      */ 
/*   301:  233 */     _localMethods = new HashMap(380);
/*   302:  234 */     _localMethods2 = new HashMap(380);
/*   303:  235 */     _localMethods.put("#ejbCreate", new Integer(0));
/*   304:  236 */     _localMethods2.put("ejbCreate", new Integer(0));
/*   305:  237 */     _localMethods.put("#ejbRemove", new Integer(1));
/*   306:  238 */     _localMethods2.put("ejbRemove", new Integer(1));
/*   307:  239 */     _localMethods.put("#disconnect", new Integer(2));
/*   308:  240 */     _localMethods2.put("disconnect", new Integer(2));
/*   309:  241 */     _localMethods.put("#addCustomers", new Integer(3));
/*   310:  242 */     _localMethods2.put("addCustomers", new Integer(3));
/*   311:  243 */     _localMethods.put("#modifyCustomers", new Integer(4));
/*   312:  244 */     _localMethods2.put("modifyCustomers", new Integer(4));
/*   313:  245 */     _localMethods.put("#deleteCustomers__StringSeq", new Integer(5));
/*   314:  246 */     _localMethods2.put("deleteCustomers__StringSeq", new Integer(5));
/*   315:  247 */     _localMethods.put("#deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(5));
/*   316:  248 */     _localMethods2.put("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(5));
/*   317:  249 */     _localMethods.put("#deleteCustomers__StringSeq__long", new Integer(6));
/*   318:  250 */     _localMethods2.put("deleteCustomers__StringSeq__long", new Integer(6));
/*   319:  251 */     _localMethods.put("#deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long", new Integer(6));
/*   320:  252 */     _localMethods2.put("deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long", new Integer(6));
/*   321:  253 */     _localMethods.put("#deactivateCustomers", new Integer(7));
/*   322:  254 */     _localMethods2.put("deactivateCustomers", new Integer(7));
/*   323:  255 */     _localMethods.put("#activateCustomers", new Integer(8));
/*   324:  256 */     _localMethods2.put("activateCustomers", new Integer(8));
/*   325:  257 */     _localMethods.put("#getCustomersInfo", new Integer(9));
/*   326:  258 */     _localMethods2.put("getCustomersInfo", new Integer(9));
/*   327:  259 */     _localMethods.put("#getCustomerByType", new Integer(10));
/*   328:  260 */     _localMethods2.put("getCustomerByType", new Integer(10));
/*   329:  261 */     _localMethods.put("#getCustomerByFI", new Integer(11));
/*   330:  262 */     _localMethods2.put("getCustomerByFI", new Integer(11));
/*   331:  263 */     _localMethods.put("#getCustomerByCategory", new Integer(12));
/*   332:  264 */     _localMethods2.put("getCustomerByCategory", new Integer(12));
/*   333:  265 */     _localMethods.put("#getCustomerByGroup", new Integer(13));
/*   334:  266 */     _localMethods2.put("getCustomerByGroup", new Integer(13));
/*   335:  267 */     _localMethods.put("#getCustomerByTypeAndFI", new Integer(14));
/*   336:  268 */     _localMethods2.put("getCustomerByTypeAndFI", new Integer(14));
/*   337:  269 */     _localMethods.put("#getCustomerByCategoryAndFI", new Integer(15));
/*   338:  270 */     _localMethods2.put("getCustomerByCategoryAndFI", new Integer(15));
/*   339:  271 */     _localMethods.put("#getCustomerByGroupAndFI", new Integer(16));
/*   340:  272 */     _localMethods2.put("getCustomerByGroupAndFI", new Integer(16));
/*   341:  273 */     _localMethods.put("#getRPPSBillerInfoByFIRPPSId", new Integer(17));
/*   342:  274 */     _localMethods2.put("getRPPSBillerInfoByFIRPPSId", new Integer(17));
/*   343:  275 */     _localMethods.put("#getRPPSBillerInfoByFIAndBillerRPPSId", new Integer(18));
/*   344:  276 */     _localMethods2.put("getRPPSBillerInfoByFIAndBillerRPPSId", new Integer(18));
/*   345:  277 */     _localMethods.put("#addWireTransfer", new Integer(19));
/*   346:  278 */     _localMethods2.put("addWireTransfer", new Integer(19));
/*   347:  279 */     _localMethods.put("#modWireTransfer", new Integer(20));
/*   348:  280 */     _localMethods2.put("modWireTransfer", new Integer(20));
/*   349:  281 */     _localMethods.put("#cancWireTransfer", new Integer(21));
/*   350:  282 */     _localMethods2.put("cancWireTransfer", new Integer(21));
/*   351:  283 */     _localMethods.put("#getWireTransferById", new Integer(22));
/*   352:  284 */     _localMethods2.put("getWireTransferById", new Integer(22));
/*   353:  285 */     _localMethods.put("#getWireTransfer", new Integer(23));
/*   354:  286 */     _localMethods2.put("getWireTransfer", new Integer(23));
/*   355:  287 */     _localMethods.put("#getWireTransfers", new Integer(24));
/*   356:  288 */     _localMethods2.put("getWireTransfers", new Integer(24));
/*   357:  289 */     _localMethods.put("#getDuplicateWires", new Integer(25));
/*   358:  290 */     _localMethods2.put("getDuplicateWires", new Integer(25));
/*   359:  291 */     _localMethods.put("#getBatchWires", new Integer(26));
/*   360:  292 */     _localMethods2.put("getBatchWires", new Integer(26));
/*   361:  293 */     _localMethods.put("#getWireHistory", new Integer(27));
/*   362:  294 */     _localMethods2.put("getWireHistory", new Integer(27));
/*   363:  295 */     _localMethods.put("#getWireHistoryByCustomer", new Integer(28));
/*   364:  296 */     _localMethods2.put("getWireHistoryByCustomer", new Integer(28));
/*   365:  297 */     _localMethods.put("#getCombinedWireHistory", new Integer(29));
/*   366:  298 */     _localMethods2.put("getCombinedWireHistory", new Integer(29));
/*   367:  299 */     _localMethods.put("#getAuditWireTransfer", new Integer(30));
/*   368:  300 */     _localMethods2.put("getAuditWireTransfer", new Integer(30));
/*   369:  301 */     _localMethods.put("#getAuditWireTransferByExtId", new Integer(31));
/*   370:  302 */     _localMethods2.put("getAuditWireTransferByExtId", new Integer(31));
/*   371:  303 */     _localMethods.put("#getWireReleaseCount", new Integer(32));
/*   372:  304 */     _localMethods2.put("getWireReleaseCount", new Integer(32));
/*   373:  305 */     _localMethods.put("#addWireTransfers", new Integer(33));
/*   374:  306 */     _localMethods2.put("addWireTransfers", new Integer(33));
/*   375:  307 */     _localMethods.put("#releaseWireTransfer", new Integer(34));
/*   376:  308 */     _localMethods2.put("releaseWireTransfer", new Integer(34));
/*   377:  309 */     _localMethods.put("#addRecWireTransfer", new Integer(35));
/*   378:  310 */     _localMethods2.put("addRecWireTransfer", new Integer(35));
/*   379:  311 */     _localMethods.put("#modRecWireTransfer", new Integer(36));
/*   380:  312 */     _localMethods2.put("modRecWireTransfer", new Integer(36));
/*   381:  313 */     _localMethods.put("#cancRecWireTransfer", new Integer(37));
/*   382:  314 */     _localMethods2.put("cancRecWireTransfer", new Integer(37));
/*   383:  315 */     _localMethods.put("#getRecWireTransferById__string__boolean", new Integer(38));
/*   384:  316 */     _localMethods2.put("getRecWireTransferById__string__boolean", new Integer(38));
/*   385:  317 */     _localMethods.put("#getRecWireTransferById__CORBA_WStringValue__boolean", new Integer(38));
/*   386:  318 */     _localMethods2.put("getRecWireTransferById__CORBA_WStringValue__boolean", new Integer(38));
/*   387:  319 */     _localMethods.put("#getRecWireTransferById__string", new Integer(39));
/*   388:  320 */     _localMethods2.put("getRecWireTransferById__string", new Integer(39));
/*   389:  321 */     _localMethods.put("#getRecWireTransferById__CORBA_WStringValue", new Integer(39));
/*   390:  322 */     _localMethods2.put("getRecWireTransferById__CORBA_WStringValue", new Integer(39));
/*   391:  323 */     _localMethods.put("#getRecWireTransfer", new Integer(40));
/*   392:  324 */     _localMethods2.put("getRecWireTransfer", new Integer(40));
/*   393:  325 */     _localMethods.put("#getRecWireTransfers", new Integer(41));
/*   394:  326 */     _localMethods2.put("getRecWireTransfers", new Integer(41));
/*   395:  327 */     _localMethods.put("#getRecWireHistory", new Integer(42));
/*   396:  328 */     _localMethods2.put("getRecWireHistory", new Integer(42));
/*   397:  329 */     _localMethods.put("#getRecWireHistoryByCustomer", new Integer(43));
/*   398:  330 */     _localMethods2.put("getRecWireHistoryByCustomer", new Integer(43));
/*   399:  331 */     _localMethods.put("#addRecWireTransfers", new Integer(44));
/*   400:  332 */     _localMethods2.put("addRecWireTransfers", new Integer(44));
/*   401:  333 */     _localMethods.put("#getWiresConfiguration", new Integer(45));
/*   402:  334 */     _localMethods2.put("getWiresConfiguration", new Integer(45));
/*   403:  335 */     _localMethods.put("#_get_wiresConfiguration", new Integer(45));
/*   404:  336 */     _localMethods2.put("_get_wiresConfiguration", new Integer(45));
/*   405:  337 */     _localMethods.put("#addWireTransferBatch", new Integer(46));
/*   406:  338 */     _localMethods2.put("addWireTransferBatch", new Integer(46));
/*   407:  339 */     _localMethods.put("#modWireTransferBatch", new Integer(47));
/*   408:  340 */     _localMethods2.put("modWireTransferBatch", new Integer(47));
/*   409:  341 */     _localMethods.put("#canWireTransferBatch", new Integer(48));
/*   410:  342 */     _localMethods2.put("canWireTransferBatch", new Integer(48));
/*   411:  343 */     _localMethods.put("#getWireTransferBatch", new Integer(49));
/*   412:  344 */     _localMethods2.put("getWireTransferBatch", new Integer(49));
/*   413:  345 */     _localMethods.put("#getWireBatchHistory", new Integer(50));
/*   414:  346 */     _localMethods2.put("getWireBatchHistory", new Integer(50));
/*   415:  347 */     _localMethods.put("#isWireBatchDeleteable", new Integer(51));
/*   416:  348 */     _localMethods2.put("isWireBatchDeleteable", new Integer(51));
/*   417:  349 */     _localMethods.put("#addWirePayee__WirePayeeInfoSeq", new Integer(52));
/*   418:  350 */     _localMethods2.put("addWirePayee__WirePayeeInfoSeq", new Integer(52));
/*   419:  351 */     _localMethods.put("#addWirePayee__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_WirePayeeInfo", new Integer(52));
/*   420:  352 */     _localMethods2.put("addWirePayee__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_WirePayeeInfo", new Integer(52));
/*   421:  353 */     _localMethods.put("#addWirePayee__WirePayeeInfo", new Integer(53));
/*   422:  354 */     _localMethods2.put("addWirePayee__WirePayeeInfo", new Integer(53));
/*   423:  355 */     _localMethods.put("#addWirePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_WirePayeeInfo", new Integer(53));
/*   424:  356 */     _localMethods2.put("addWirePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_WirePayeeInfo", new Integer(53));
/*   425:  357 */     _localMethods.put("#modWirePayee", new Integer(54));
/*   426:  358 */     _localMethods2.put("modWirePayee", new Integer(54));
/*   427:  359 */     _localMethods.put("#canWirePayee", new Integer(55));
/*   428:  360 */     _localMethods2.put("canWirePayee", new Integer(55));
/*   429:  361 */     _localMethods.put("#getWirePayee__WirePayeeInfo", new Integer(56));
/*   430:  362 */     _localMethods2.put("getWirePayee__WirePayeeInfo", new Integer(56));
/*   431:  363 */     _localMethods.put("#getWirePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_WirePayeeInfo", new Integer(56));
/*   432:  364 */     _localMethods2.put("getWirePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_WirePayeeInfo", new Integer(56));
/*   433:  365 */     _localMethods.put("#getWirePayee__string", new Integer(57));
/*   434:  366 */     _localMethods2.put("getWirePayee__string", new Integer(57));
/*   435:  367 */     _localMethods.put("#getWirePayee__CORBA_WStringValue", new Integer(57));
/*   436:  368 */     _localMethods2.put("getWirePayee__CORBA_WStringValue", new Integer(57));
/*   437:  369 */     _localMethods.put("#getWirePayeeByType", new Integer(58));
/*   438:  370 */     _localMethods2.put("getWirePayeeByType", new Integer(58));
/*   439:  371 */     _localMethods.put("#getWirePayeeByStatus", new Integer(59));
/*   440:  372 */     _localMethods2.put("getWirePayeeByStatus", new Integer(59));
/*   441:  373 */     _localMethods.put("#getWirePayeeByGroup", new Integer(60));
/*   442:  374 */     _localMethods2.put("getWirePayeeByGroup", new Integer(60));
/*   443:  375 */     _localMethods.put("#getWirePayeeByCustomer", new Integer(61));
/*   444:  376 */     _localMethods2.put("getWirePayeeByCustomer", new Integer(61));
/*   445:  377 */     _localMethods.put("#addIntermediaryBanksToBeneficiary", new Integer(62));
/*   446:  378 */     _localMethods2.put("addIntermediaryBanksToBeneficiary", new Integer(62));
/*   447:  379 */     _localMethods.put("#delIntermediaryBanksFromBeneficiary", new Integer(63));
/*   448:  380 */     _localMethods2.put("delIntermediaryBanksFromBeneficiary", new Integer(63));
/*   449:  381 */     _localMethods.put("#addWireBanks", new Integer(64));
/*   450:  382 */     _localMethods2.put("addWireBanks", new Integer(64));
/*   451:  383 */     _localMethods.put("#modWireBanks", new Integer(65));
/*   452:  384 */     _localMethods2.put("modWireBanks", new Integer(65));
/*   453:  385 */     _localMethods.put("#delWireBanks", new Integer(66));
/*   454:  386 */     _localMethods2.put("delWireBanks", new Integer(66));
/*   455:  387 */     _localMethods.put("#getWireBanks", new Integer(67));
/*   456:  388 */     _localMethods2.put("getWireBanks", new Integer(67));
/*   457:  389 */     _localMethods.put("#getWireBanksByRTN", new Integer(68));
/*   458:  390 */     _localMethods2.put("getWireBanksByRTN", new Integer(68));
/*   459:  391 */     _localMethods.put("#getWireBanksByID", new Integer(69));
/*   460:  392 */     _localMethods2.put("getWireBanksByID", new Integer(69));
/*   461:  393 */     _localMethods.put("#processWireApprovalRslt", new Integer(70));
/*   462:  394 */     _localMethods2.put("processWireApprovalRslt", new Integer(70));
/*   463:  395 */     _localMethods.put("#processWireBackendlRslt", new Integer(71));
/*   464:  396 */     _localMethods2.put("processWireBackendlRslt", new Integer(71));
/*   465:  397 */     _localMethods.put("#processWireApprovalRevertRslt", new Integer(72));
/*   466:  398 */     _localMethods2.put("processWireApprovalRevertRslt", new Integer(72));
/*   467:  399 */     _localMethods.put("#addBPWFIInfo", new Integer(73));
/*   468:  400 */     _localMethods2.put("addBPWFIInfo", new Integer(73));
/*   469:  401 */     _localMethods.put("#modBPWFIInfo", new Integer(74));
/*   470:  402 */     _localMethods2.put("modBPWFIInfo", new Integer(74));
/*   471:  403 */     _localMethods.put("#canBPWFIInfo", new Integer(75));
/*   472:  404 */     _localMethods2.put("canBPWFIInfo", new Integer(75));
/*   473:  405 */     _localMethods.put("#activateBPWFI", new Integer(76));
/*   474:  406 */     _localMethods2.put("activateBPWFI", new Integer(76));
/*   475:  407 */     _localMethods.put("#getBPWFIInfo__string", new Integer(77));
/*   476:  408 */     _localMethods2.put("getBPWFIInfo__string", new Integer(77));
/*   477:  409 */     _localMethods.put("#getBPWFIInfo__CORBA_WStringValue", new Integer(77));
/*   478:  410 */     _localMethods2.put("getBPWFIInfo__CORBA_WStringValue", new Integer(77));
/*   479:  411 */     _localMethods.put("#getBPWFIInfo__StringSeq", new Integer(78));
/*   480:  412 */     _localMethods2.put("getBPWFIInfo__StringSeq", new Integer(78));
/*   481:  413 */     _localMethods.put("#getBPWFIInfo__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(78));
/*   482:  414 */     _localMethods2.put("getBPWFIInfo__org_omg_boxedRMI_CORBA_seq1_WStringValue", new Integer(78));
/*   483:  415 */     _localMethods.put("#getBPWFIInfoByType", new Integer(79));
/*   484:  416 */     _localMethods2.put("getBPWFIInfoByType", new Integer(79));
/*   485:  417 */     _localMethods.put("#getBPWFIInfoByStatus", new Integer(80));
/*   486:  418 */     _localMethods2.put("getBPWFIInfoByStatus", new Integer(80));
/*   487:  419 */     _localMethods.put("#getBPWFIInfoByGroup", new Integer(81));
/*   488:  420 */     _localMethods2.put("getBPWFIInfoByGroup", new Integer(81));
/*   489:  421 */     _localMethods.put("#getBPWFIInfoByACHId", new Integer(82));
/*   490:  422 */     _localMethods2.put("getBPWFIInfoByACHId", new Integer(82));
/*   491:  423 */     _localMethods.put("#getBPWFIInfoByRTN", new Integer(83));
/*   492:  424 */     _localMethods2.put("getBPWFIInfoByRTN", new Integer(83));
/*   493:  425 */     _localMethods.put("#addRPPSFIInfo", new Integer(84));
/*   494:  426 */     _localMethods2.put("addRPPSFIInfo", new Integer(84));
/*   495:  427 */     _localMethods.put("#getRPPSFIInfoByFIId", new Integer(85));
/*   496:  428 */     _localMethods2.put("getRPPSFIInfoByFIId", new Integer(85));
/*   497:  429 */     _localMethods.put("#getRPPSFIInfoByFIRPPSId", new Integer(86));
/*   498:  430 */     _localMethods2.put("getRPPSFIInfoByFIRPPSId", new Integer(86));
/*   499:  431 */     _localMethods.put("#canRPPSFIInfo", new Integer(87));
/*   500:  432 */     _localMethods2.put("canRPPSFIInfo", new Integer(87));
/*   501:  433 */     _localMethods.put("#activateRPPSFI", new Integer(88));
/*   502:  434 */     _localMethods2.put("activateRPPSFI", new Integer(88));
/*   503:  435 */     _localMethods.put("#modRPPSFIInfo", new Integer(89));
/*   504:  436 */     _localMethods2.put("modRPPSFIInfo", new Integer(89));
/*   505:  437 */     _localMethods.put("#getSmartDate", new Integer(90));
/*   506:  438 */     _localMethods2.put("getSmartDate", new Integer(90));
/*   507:  439 */     _localMethods.put("#processApprovalResult", new Integer(91));
/*   508:  440 */     _localMethods2.put("processApprovalResult", new Integer(91));
/*   509:  441 */     _localMethods.put("#addCCCompany", new Integer(92));
/*   510:  442 */     _localMethods2.put("addCCCompany", new Integer(92));
/*   511:  443 */     _localMethods.put("#cancelCCCompany", new Integer(93));
/*   512:  444 */     _localMethods2.put("cancelCCCompany", new Integer(93));
/*   513:  445 */     _localMethods.put("#modCCCompany", new Integer(94));
/*   514:  446 */     _localMethods2.put("modCCCompany", new Integer(94));
/*   515:  447 */     _localMethods.put("#getCCCompany", new Integer(95));
/*   516:  448 */     _localMethods2.put("getCCCompany", new Integer(95));
/*   517:  449 */     _localMethods.put("#getCCCompanyList", new Integer(96));
/*   518:  450 */     _localMethods2.put("getCCCompanyList", new Integer(96));
/*   519:  451 */     _localMethods.put("#getNextCashConCutOff", new Integer(97));
/*   520:  452 */     _localMethods2.put("getNextCashConCutOff", new Integer(97));
/*   521:  453 */     _localMethods.put("#addCCCompanyAcct", new Integer(98));
/*   522:  454 */     _localMethods2.put("addCCCompanyAcct", new Integer(98));
/*   523:  455 */     _localMethods.put("#cancelCCCompanyAcct", new Integer(99));
/*   524:  456 */     _localMethods2.put("cancelCCCompanyAcct", new Integer(99));
/*   525:  457 */     _localMethods.put("#modCCCompanyAcct", new Integer(100));
/*   526:  458 */     _localMethods2.put("modCCCompanyAcct", new Integer(100));
/*   527:  459 */     _localMethods.put("#getCCCompanyAcct", new Integer(101));
/*   528:  460 */     _localMethods2.put("getCCCompanyAcct", new Integer(101));
/*   529:  461 */     _localMethods.put("#getCCCompanyAcctList", new Integer(102));
/*   530:  462 */     _localMethods2.put("getCCCompanyAcctList", new Integer(102));
/*   531:  463 */     _localMethods.put("#addCCCompanyCutOff", new Integer(103));
/*   532:  464 */     _localMethods2.put("addCCCompanyCutOff", new Integer(103));
/*   533:  465 */     _localMethods.put("#cancelCCCompanyCutOff", new Integer(104));
/*   534:  466 */     _localMethods2.put("cancelCCCompanyCutOff", new Integer(104));
/*   535:  467 */     _localMethods.put("#getCCCompanyCutOff", new Integer(105));
/*   536:  468 */     _localMethods2.put("getCCCompanyCutOff", new Integer(105));
/*   537:  469 */     _localMethods.put("#getCCCompanyCutOffList", new Integer(106));
/*   538:  470 */     _localMethods2.put("getCCCompanyCutOffList", new Integer(106));
/*   539:  471 */     _localMethods.put("#addCCLocation", new Integer(107));
/*   540:  472 */     _localMethods2.put("addCCLocation", new Integer(107));
/*   541:  473 */     _localMethods.put("#cancelCCLocation", new Integer(108));
/*   542:  474 */     _localMethods2.put("cancelCCLocation", new Integer(108));
/*   543:  475 */     _localMethods.put("#modCCLocation", new Integer(109));
/*   544:  476 */     _localMethods2.put("modCCLocation", new Integer(109));
/*   545:  477 */     _localMethods.put("#getCCLocation", new Integer(110));
/*   546:  478 */     _localMethods2.put("getCCLocation", new Integer(110));
/*   547:  479 */     _localMethods.put("#getCCLocationList", new Integer(111));
/*   548:  480 */     _localMethods2.put("getCCLocationList", new Integer(111));
/*   549:  481 */     _localMethods.put("#addCCEntry", new Integer(112));
/*   550:  482 */     _localMethods2.put("addCCEntry", new Integer(112));
/*   551:  483 */     _localMethods.put("#cancelCCEntry", new Integer(113));
/*   552:  484 */     _localMethods2.put("cancelCCEntry", new Integer(113));
/*   553:  485 */     _localMethods.put("#modCCEntry", new Integer(114));
/*   554:  486 */     _localMethods2.put("modCCEntry", new Integer(114));
/*   555:  487 */     _localMethods.put("#getCCEntry", new Integer(115));
/*   556:  488 */     _localMethods2.put("getCCEntry", new Integer(115));
/*   557:  489 */     _localMethods.put("#getCCEntryHist", new Integer(116));
/*   558:  490 */     _localMethods2.put("getCCEntryHist", new Integer(116));
/*   559:  491 */     _localMethods.put("#getCCEntrySummaryList", new Integer(117));
/*   560:  492 */     _localMethods2.put("getCCEntrySummaryList", new Integer(117));
/*   561:  493 */     _localMethods.put("#addTransfer", new Integer(118));
/*   562:  494 */     _localMethods2.put("addTransfer", new Integer(118));
/*   563:  495 */     _localMethods.put("#modTransfer", new Integer(119));
/*   564:  496 */     _localMethods2.put("modTransfer", new Integer(119));
/*   565:  497 */     _localMethods.put("#cancTransfer", new Integer(120));
/*   566:  498 */     _localMethods2.put("cancTransfer", new Integer(120));
/*   567:  499 */     _localMethods.put("#getTransferBySrvrTId__string__string", new Integer(121));
/*   568:  500 */     _localMethods2.put("getTransferBySrvrTId__string__string", new Integer(121));
/*   569:  501 */     _localMethods.put("#getTransferBySrvrTId__CORBA_WStringValue__CORBA_WStringValue", new Integer(121));
/*   570:  502 */     _localMethods2.put("getTransferBySrvrTId__CORBA_WStringValue__CORBA_WStringValue", new Integer(121));
/*   571:  503 */     _localMethods.put("#getTransferBySrvrTId__string", new Integer(122));
/*   572:  504 */     _localMethods2.put("getTransferBySrvrTId__string", new Integer(122));
/*   573:  505 */     _localMethods.put("#getTransferBySrvrTId__CORBA_WStringValue", new Integer(122));
/*   574:  506 */     _localMethods2.put("getTransferBySrvrTId__CORBA_WStringValue", new Integer(122));
/*   575:  507 */     _localMethods.put("#getTransferByTrackingId", new Integer(123));
/*   576:  508 */     _localMethods2.put("getTransferByTrackingId", new Integer(123));
/*   577:  509 */     _localMethods.put("#getTransfersBySrvrTId", new Integer(124));
/*   578:  510 */     _localMethods2.put("getTransfersBySrvrTId", new Integer(124));
/*   579:  511 */     _localMethods.put("#getTransfersByRecSrvrTId__StringSeq__boolean", new Integer(125));
/*   580:  512 */     _localMethods2.put("getTransfersByRecSrvrTId__StringSeq__boolean", new Integer(125));
/*   581:  513 */     _localMethods.put("#getTransfersByRecSrvrTId__org_omg_boxedRMI_CORBA_seq1_WStringValue__boolean", new Integer(125));
/*   582:  514 */     _localMethods2.put("getTransfersByRecSrvrTId__org_omg_boxedRMI_CORBA_seq1_WStringValue__boolean", new Integer(125));
/*   583:  515 */     _localMethods.put("#getTransfersByRecSrvrTId__string__boolean", new Integer(126));
/*   584:  516 */     _localMethods2.put("getTransfersByRecSrvrTId__string__boolean", new Integer(126));
/*   585:  517 */     _localMethods.put("#getTransfersByRecSrvrTId__CORBA_WStringValue__boolean", new Integer(126));
/*   586:  518 */     _localMethods2.put("getTransfersByRecSrvrTId__CORBA_WStringValue__boolean", new Integer(126));
/*   587:  519 */     _localMethods.put("#getTransfersByTrackingId", new Integer(127));
/*   588:  520 */     _localMethods2.put("getTransfersByTrackingId", new Integer(127));
/*   589:  521 */     _localMethods.put("#getTransferHistory", new Integer(128));
/*   590:  522 */     _localMethods2.put("getTransferHistory", new Integer(128));
/*   591:  523 */     _localMethods.put("#addTransfers", new Integer(129));
/*   592:  524 */     _localMethods2.put("addTransfers", new Integer(129));
/*   593:  525 */     _localMethods.put("#addRecTransfer", new Integer(130));
/*   594:  526 */     _localMethods2.put("addRecTransfer", new Integer(130));
/*   595:  527 */     _localMethods.put("#modRecTransfer", new Integer(131));
/*   596:  528 */     _localMethods2.put("modRecTransfer", new Integer(131));
/*   597:  529 */     _localMethods.put("#cancRecTransfer", new Integer(132));
/*   598:  530 */     _localMethods2.put("cancRecTransfer", new Integer(132));
/*   599:  531 */     _localMethods.put("#getRecTransferBySrvrTId__string__string", new Integer(133));
/*   600:  532 */     _localMethods2.put("getRecTransferBySrvrTId__string__string", new Integer(133));
/*   601:  533 */     _localMethods.put("#getRecTransferBySrvrTId__CORBA_WStringValue__CORBA_WStringValue", new Integer(133));
/*   602:  534 */     _localMethods2.put("getRecTransferBySrvrTId__CORBA_WStringValue__CORBA_WStringValue", new Integer(133));
/*   603:  535 */     _localMethods.put("#getRecTransferBySrvrTId__string", new Integer(134));
/*   604:  536 */     _localMethods2.put("getRecTransferBySrvrTId__string", new Integer(134));
/*   605:  537 */     _localMethods.put("#getRecTransferBySrvrTId__CORBA_WStringValue", new Integer(134));
/*   606:  538 */     _localMethods2.put("getRecTransferBySrvrTId__CORBA_WStringValue", new Integer(134));
/*   607:  539 */     _localMethods.put("#getRecTransferByTrackingId", new Integer(135));
/*   608:  540 */     _localMethods2.put("getRecTransferByTrackingId", new Integer(135));
/*   609:  541 */     _localMethods.put("#getRecTransfersBySrvrTId", new Integer(136));
/*   610:  542 */     _localMethods2.put("getRecTransfersBySrvrTId", new Integer(136));
/*   611:  543 */     _localMethods.put("#getRecTransfers", new Integer(137));
/*   612:  544 */     _localMethods2.put("getRecTransfers", new Integer(137));
/*   613:  545 */     _localMethods.put("#getRecTransfersByTrackingId", new Integer(138));
/*   614:  546 */     _localMethods2.put("getRecTransfersByTrackingId", new Integer(138));
/*   615:  547 */     _localMethods.put("#getRecTransferHistory", new Integer(139));
/*   616:  548 */     _localMethods2.put("getRecTransferHistory", new Integer(139));
/*   617:  549 */     _localMethods.put("#addExtTransferCompany", new Integer(140));
/*   618:  550 */     _localMethods2.put("addExtTransferCompany", new Integer(140));
/*   619:  551 */     _localMethods.put("#canExtTransferCompany", new Integer(141));
/*   620:  552 */     _localMethods2.put("canExtTransferCompany", new Integer(141));
/*   621:  553 */     _localMethods.put("#modExtTransferCompany", new Integer(142));
/*   622:  554 */     _localMethods2.put("modExtTransferCompany", new Integer(142));
/*   623:  555 */     _localMethods.put("#getExtTransferCompany", new Integer(143));
/*   624:  556 */     _localMethods2.put("getExtTransferCompany", new Integer(143));
/*   625:  557 */     _localMethods.put("#getExtTransferCompanyList", new Integer(144));
/*   626:  558 */     _localMethods2.put("getExtTransferCompanyList", new Integer(144));
/*   627:  559 */     _localMethods.put("#addExtTransferAccount", new Integer(145));
/*   628:  560 */     _localMethods2.put("addExtTransferAccount", new Integer(145));
/*   629:  561 */     _localMethods.put("#canExtTransferAccount", new Integer(146));
/*   630:  562 */     _localMethods2.put("canExtTransferAccount", new Integer(146));
/*   631:  563 */     _localMethods.put("#modExtTransferAccount", new Integer(147));
/*   632:  564 */     _localMethods2.put("modExtTransferAccount", new Integer(147));
/*   633:  565 */     _localMethods.put("#getExtTransferAccount", new Integer(148));
/*   634:  566 */     _localMethods2.put("getExtTransferAccount", new Integer(148));
/*   635:  567 */     _localMethods.put("#verifyExtTransferAccount", new Integer(149));
/*   636:  568 */     _localMethods2.put("verifyExtTransferAccount", new Integer(149));
/*   637:  569 */     _localMethods.put("#depositAmountsForVerify", new Integer(150));
/*   638:  570 */     _localMethods2.put("depositAmountsForVerify", new Integer(150));
/*   639:  571 */     _localMethods.put("#activateExtTransferAcct", new Integer(151));
/*   640:  572 */     _localMethods2.put("activateExtTransferAcct", new Integer(151));
/*   641:  573 */     _localMethods.put("#inactivateExtTransferAcct", new Integer(152));
/*   642:  574 */     _localMethods2.put("inactivateExtTransferAcct", new Integer(152));
/*   643:  575 */     _localMethods.put("#getExtTransferAcctList", new Integer(153));
/*   644:  576 */     _localMethods2.put("getExtTransferAcctList", new Integer(153));
/*   645:  577 */     _localMethods.put("#getNonBusinessDays", new Integer(154));
/*   646:  578 */     _localMethods2.put("getNonBusinessDays", new Integer(154));
/*   647:  579 */     _localMethods.put("#getNonBusinessDaysFromFile", new Integer(155));
/*   648:  580 */     _localMethods2.put("getNonBusinessDaysFromFile", new Integer(155));
/*   649:  581 */     _localMethods.put("#getPagedWire", new Integer(156));
/*   650:  582 */     _localMethods2.put("getPagedWire", new Integer(156));
/*   651:  583 */     _localMethods.put("#getPagedTransfer", new Integer(157));
/*   652:  584 */     _localMethods2.put("getPagedTransfer", new Integer(157));
/*   653:  585 */     _localMethods.put("#getPagedBillPayments", new Integer(158));
/*   654:  586 */     _localMethods2.put("getPagedBillPayments", new Integer(158));
/*   655:  587 */     _localMethods.put("#getValidTransferDateDue", new Integer(159));
/*   656:  588 */     _localMethods2.put("getValidTransferDateDue", new Integer(159));
/*   657:  589 */     _localMethods.put("#getPagedCashCon", new Integer(160));
/*   658:  590 */     _localMethods2.put("getPagedCashCon", new Integer(160));
/*   659:  591 */     _localMethods.put("#getPayeeByListId", new Integer(161));
/*   660:  592 */     _localMethods2.put("getPayeeByListId", new Integer(161));
/*   661:  593 */     _localMethods.put("#getAccountTypesMap", new Integer(162));
/*   662:  594 */     _localMethods2.put("getAccountTypesMap", new Integer(162));
/*   663:  595 */     _localMethods.put("#modExtTransferAccountPrenoteStatus", new Integer(163));
/*   664:  596 */     _localMethods2.put("modExtTransferAccountPrenoteStatus", new Integer(163));
/*   665:  597 */     _localMethods.put("#getBPWProperty", new Integer(164));
/*   666:  598 */     _localMethods2.put("getBPWProperty", new Integer(164));
/*   667:  599 */     _localMethods.put("#addTransferBatch", new Integer(165));
/*   668:  600 */     _localMethods2.put("addTransferBatch", new Integer(165));
/*   669:  601 */     _localMethods.put("#modifyTransferBatch", new Integer(166));
/*   670:  602 */     _localMethods2.put("modifyTransferBatch", new Integer(166));
/*   671:  603 */     _localMethods.put("#cancelTransferBatch", new Integer(167));
/*   672:  604 */     _localMethods2.put("cancelTransferBatch", new Integer(167));
/*   673:  605 */     _localMethods.put("#getTransferBatchById", new Integer(168));
/*   674:  606 */     _localMethods2.put("getTransferBatchById", new Integer(168));
/*   675:  607 */     _localMethods.put("#accountHasPendingTransfers", new Integer(169));
/*   676:  608 */     _localMethods2.put("accountHasPendingTransfers", new Integer(169));
/*   677:  609 */     _localMethods.put("#addBillPayment", new Integer(170));
/*   678:  610 */     _localMethods2.put("addBillPayment", new Integer(170));
/*   679:  611 */     _localMethods.put("#modifyBillPayment", new Integer(171));
/*   680:  612 */     _localMethods2.put("modifyBillPayment", new Integer(171));
/*   681:  613 */     _localMethods.put("#deleteBillPayment", new Integer(172));
/*   682:  614 */     _localMethods2.put("deleteBillPayment", new Integer(172));
/*   683:  615 */     _localMethods.put("#getBillPaymentById", new Integer(173));
/*   684:  616 */     _localMethods2.put("getBillPaymentById", new Integer(173));
/*   685:  617 */     _localMethods.put("#addPaymentBatch", new Integer(174));
/*   686:  618 */     _localMethods2.put("addPaymentBatch", new Integer(174));
/*   687:  619 */     _localMethods.put("#modifyPaymentBatch", new Integer(175));
/*   688:  620 */     _localMethods2.put("modifyPaymentBatch", new Integer(175));
/*   689:  621 */     _localMethods.put("#deletePaymentBatch", new Integer(176));
/*   690:  622 */     _localMethods2.put("deletePaymentBatch", new Integer(176));
/*   691:  623 */     _localMethods.put("#getPaymentBatchById", new Integer(177));
/*   692:  624 */     _localMethods2.put("getPaymentBatchById", new Integer(177));
/*   693:  625 */     _localMethods.put("#getLastPayments", new Integer(178));
/*   694:  626 */     _localMethods2.put("getLastPayments", new Integer(178));
/*   695:  627 */     _localMethods.put("#getBankingDaysInRange", new Integer(179));
/*   696:  628 */     _localMethods2.put("getBankingDaysInRange", new Integer(179));
/*   697:  629 */     _localMethods.put("#addCustomerPayee", new Integer(180));
/*   698:  630 */     _localMethods2.put("addCustomerPayee", new Integer(180));
/*   699:  631 */     _localMethods.put("#deleteCustomerPayee", new Integer(181));
/*   700:  632 */     _localMethods2.put("deleteCustomerPayee", new Integer(181));
/*   701:  633 */     _localMethods.put("#updateCustomerPayee", new Integer(182));
/*   702:  634 */     _localMethods2.put("updateCustomerPayee", new Integer(182));
/*   703:  635 */     _localMethods.put("#getCustomerPayees", new Integer(183));
/*   704:  636 */     _localMethods2.put("getCustomerPayees", new Integer(183));
/*   705:  637 */     _localMethods.put("#searchGlobalPayees", new Integer(184));
/*   706:  638 */     _localMethods2.put("searchGlobalPayees", new Integer(184));
/*   707:  639 */     _localMethods.put("#getGlobalPayee", new Integer(185));
/*   708:  640 */     _localMethods2.put("getGlobalPayee", new Integer(185));
/*   709:  641 */     _localMethods.put("#processPmtTrnRslt", new Integer(186));
/*   710:  642 */     _localMethods2.put("processPmtTrnRslt", new Integer(186));
/*   711:      */   }
/*   712:      */   
/*   713:      */   public static Object create()
/*   714:      */     throws Exception
/*   715:      */   {
/*   716:  650 */     return new BPWServicesBean();
/*   717:      */   }
/*   718:      */   
/*   719:      */   public static String invoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream)
/*   720:      */   {
/*   721:  659 */     return invoke(paramObject, paramString, paramInputStream, paramOutputStream, 0);
/*   722:      */   }
/*   723:      */   
/*   724:      */   public static String invoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/*   725:      */   {
/*   726:  669 */     if ((paramString.startsWith("#")) || (LocalStack.getCurrent().isArgsOnLocal())) {
/*   727:  671 */       return localInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/*   728:      */     }
/*   729:  675 */     return remoteInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/*   730:      */   }
/*   731:      */   
/*   732:      */   public static String remoteInvoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/*   733:      */   {
/*   734:  686 */     _ServerRequest local_ServerRequest = new _ServerRequest(paramInputStream, paramOutputStream, (paramInt & 0x1) != 0);
/*   735:  687 */     BPWServicesBean localBPWServicesBean = (BPWServicesBean)paramObject;
/*   736:  688 */     Integer localInteger = (Integer)_methods.get(paramString);
/*   737:  689 */     if (localInteger == null) {
/*   738:  691 */       return "org.omg.CORBA.BAD_OPERATION";
/*   739:      */     }
/*   740:      */     int i;
/*   741:      */     Object localObject1;
/*   742:      */     Object localObject3;
/*   743:      */     Object localObject2;
/*   744:  693 */     switch (localInteger.intValue())
/*   745:      */     {
/*   746:      */     case 0: 
/*   747:      */       try
/*   748:      */       {
/*   749:  700 */         SessionContext localSessionContext = new SessionContext(ObjectContextHelper.read(paramInputStream));
/*   750:  701 */         localBPWServicesBean
/*   751:  702 */           .setSessionContext(
/*   752:  703 */           localSessionContext);
/*   753:      */       }
/*   754:      */       catch (Throwable localThrowable1)
/*   755:      */       {
/*   756:  708 */         localThrowable1.printStackTrace(Jaguar.log);
/*   757:  709 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/*   758:  710 */         return localThrowable1.getClass().getName();
/*   759:      */       }
/*   760:      */     case 1: 
/*   761:      */       try
/*   762:      */       {
/*   763:  719 */         localBPWServicesBean.ejbCreate();
/*   764:      */       }
/*   765:      */       catch (Throwable localThrowable2)
/*   766:      */       {
/*   767:  724 */         localThrowable2.printStackTrace(Jaguar.log);
/*   768:  725 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/*   769:  726 */         return localThrowable2.getClass().getName();
/*   770:      */       }
/*   771:      */     case 2: 
/*   772:      */       try
/*   773:      */       {
/*   774:  735 */         localBPWServicesBean.ejbActivate();
/*   775:      */       }
/*   776:      */       catch (Throwable localThrowable3)
/*   777:      */       {
/*   778:  740 */         localThrowable3.printStackTrace(Jaguar.log);
/*   779:  741 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/*   780:  742 */         return localThrowable3.getClass().getName();
/*   781:      */       }
/*   782:      */     case 3: 
/*   783:      */       try
/*   784:      */       {
/*   785:  751 */         localBPWServicesBean.ejbPassivate();
/*   786:      */       }
/*   787:      */       catch (Throwable localThrowable4)
/*   788:      */       {
/*   789:  756 */         localThrowable4.printStackTrace(Jaguar.log);
/*   790:  757 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/*   791:  758 */         return localThrowable4.getClass().getName();
/*   792:      */       }
/*   793:      */     case 4: 
/*   794:      */       try
/*   795:      */       {
/*   796:  767 */         localBPWServicesBean.ejbRemove();
/*   797:      */       }
/*   798:      */       catch (Throwable localThrowable5)
/*   799:      */       {
/*   800:  772 */         localThrowable5.printStackTrace(Jaguar.log);
/*   801:  773 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/*   802:  774 */         return localThrowable5.getClass().getName();
/*   803:      */       }
/*   804:      */     case 5: 
/*   805:      */       try
/*   806:      */       {
/*   807:  783 */         localBPWServicesBean.disconnect();
/*   808:      */       }
/*   809:      */       catch (Throwable localThrowable6)
/*   810:      */       {
/*   811:  788 */         localThrowable6.printStackTrace(Jaguar.log);
/*   812:  789 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/*   813:  790 */         return localThrowable6.getClass().getName();
/*   814:      */       }
/*   815:      */     case 6: 
/*   816:      */       try
/*   817:      */       {
/*   818:      */         CustomerInfo[] arrayOfCustomerInfo1;
/*   819:  799 */         if (local_ServerRequest.isRMI()) {
/*   820:  799 */           arrayOfCustomerInfo1 = (CustomerInfo[])local_ServerRequest.read_value(new CustomerInfo[0].getClass());
/*   821:      */         } else {
/*   822:  799 */           arrayOfCustomerInfo1 = CustomerInfoSeqHelper.read(paramInputStream);
/*   823:      */         }
/*   824:  800 */         i = 
/*   825:  801 */           localBPWServicesBean.addCustomers(
/*   826:  802 */           arrayOfCustomerInfo1);
/*   827:      */         
/*   828:  804 */         paramOutputStream.write_long(i);
/*   829:      */       }
/*   830:      */       catch (Throwable localThrowable7)
/*   831:      */       {
/*   832:  808 */         localThrowable7.printStackTrace(Jaguar.log);
/*   833:  809 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/*   834:  810 */         return localThrowable7.getClass().getName();
/*   835:      */       }
/*   836:      */     case 7: 
/*   837:      */       try
/*   838:      */       {
/*   839:      */         CustomerInfo[] arrayOfCustomerInfo2;
/*   840:  819 */         if (local_ServerRequest.isRMI()) {
/*   841:  819 */           arrayOfCustomerInfo2 = (CustomerInfo[])local_ServerRequest.read_value(new CustomerInfo[0].getClass());
/*   842:      */         } else {
/*   843:  819 */           arrayOfCustomerInfo2 = CustomerInfoSeqHelper.read(paramInputStream);
/*   844:      */         }
/*   845:  820 */         i = 
/*   846:  821 */           localBPWServicesBean.modifyCustomers(
/*   847:  822 */           arrayOfCustomerInfo2);
/*   848:      */         
/*   849:  824 */         paramOutputStream.write_long(i);
/*   850:      */       }
/*   851:      */       catch (Throwable localThrowable8)
/*   852:      */       {
/*   853:  828 */         localThrowable8.printStackTrace(Jaguar.log);
/*   854:  829 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/*   855:  830 */         return localThrowable8.getClass().getName();
/*   856:      */       }
/*   857:      */     case 8: 
/*   858:      */       try
/*   859:      */       {
/*   860:      */         String[] arrayOfString1;
/*   861:  839 */         if (local_ServerRequest.isRMI()) {
/*   862:  839 */           arrayOfString1 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*   863:      */         } else {
/*   864:  839 */           arrayOfString1 = StringSeqHelper.read(paramInputStream);
/*   865:      */         }
/*   866:  840 */         i = 
/*   867:  841 */           localBPWServicesBean.deleteCustomers(
/*   868:  842 */           arrayOfString1);
/*   869:      */         
/*   870:  844 */         paramOutputStream.write_long(i);
/*   871:      */       }
/*   872:      */       catch (Throwable localThrowable9)
/*   873:      */       {
/*   874:  848 */         localThrowable9.printStackTrace(Jaguar.log);
/*   875:  849 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/*   876:  850 */         return localThrowable9.getClass().getName();
/*   877:      */       }
/*   878:      */     case 9: 
/*   879:      */       try
/*   880:      */       {
/*   881:      */         String[] arrayOfString2;
/*   882:  859 */         if (local_ServerRequest.isRMI()) {
/*   883:  859 */           arrayOfString2 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*   884:      */         } else {
/*   885:  859 */           arrayOfString2 = StringSeqHelper.read(paramInputStream);
/*   886:      */         }
/*   887:  861 */         i = paramInputStream.read_long();
/*   888:  862 */         int j = localBPWServicesBean
/*   889:  863 */           .deleteCustomers(
/*   890:  864 */           arrayOfString2, 
/*   891:  865 */           i);
/*   892:      */         
/*   893:  867 */         paramOutputStream.write_long(j);
/*   894:      */       }
/*   895:      */       catch (Throwable localThrowable10)
/*   896:      */       {
/*   897:  871 */         localThrowable10.printStackTrace(Jaguar.log);
/*   898:  872 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/*   899:  873 */         return localThrowable10.getClass().getName();
/*   900:      */       }
/*   901:      */     case 10: 
/*   902:      */       try
/*   903:      */       {
/*   904:      */         String[] arrayOfString3;
/*   905:  882 */         if (local_ServerRequest.isRMI()) {
/*   906:  882 */           arrayOfString3 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*   907:      */         } else {
/*   908:  882 */           arrayOfString3 = StringSeqHelper.read(paramInputStream);
/*   909:      */         }
/*   910:  883 */         i = 
/*   911:  884 */           localBPWServicesBean.deactivateCustomers(
/*   912:  885 */           arrayOfString3);
/*   913:      */         
/*   914:  887 */         paramOutputStream.write_long(i);
/*   915:      */       }
/*   916:      */       catch (Throwable localThrowable11)
/*   917:      */       {
/*   918:  891 */         localThrowable11.printStackTrace(Jaguar.log);
/*   919:  892 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/*   920:  893 */         return localThrowable11.getClass().getName();
/*   921:      */       }
/*   922:      */     case 11: 
/*   923:      */       try
/*   924:      */       {
/*   925:      */         String[] arrayOfString4;
/*   926:  902 */         if (local_ServerRequest.isRMI()) {
/*   927:  902 */           arrayOfString4 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*   928:      */         } else {
/*   929:  902 */           arrayOfString4 = StringSeqHelper.read(paramInputStream);
/*   930:      */         }
/*   931:  903 */         i = 
/*   932:  904 */           localBPWServicesBean.activateCustomers(
/*   933:  905 */           arrayOfString4);
/*   934:      */         
/*   935:  907 */         paramOutputStream.write_long(i);
/*   936:      */       }
/*   937:      */       catch (Throwable localThrowable12)
/*   938:      */       {
/*   939:  911 */         localThrowable12.printStackTrace(Jaguar.log);
/*   940:  912 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/*   941:  913 */         return localThrowable12.getClass().getName();
/*   942:      */       }
/*   943:      */     case 12: 
/*   944:      */       try
/*   945:      */       {
/*   946:      */         String[] arrayOfString5;
/*   947:  922 */         if (local_ServerRequest.isRMI()) {
/*   948:  922 */           arrayOfString5 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*   949:      */         } else {
/*   950:  922 */           arrayOfString5 = StringSeqHelper.read(paramInputStream);
/*   951:      */         }
/*   952:  923 */         localObject1 = 
/*   953:  924 */           localBPWServicesBean.getCustomersInfo(
/*   954:  925 */           arrayOfString5);
/*   955:  927 */         if (local_ServerRequest.isRMI()) {
/*   956:  927 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*   957:      */         } else {
/*   958:  927 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*   959:      */         }
/*   960:      */       }
/*   961:      */       catch (Throwable localThrowable13)
/*   962:      */       {
/*   963:  931 */         localThrowable13.printStackTrace(Jaguar.log);
/*   964:  932 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/*   965:  933 */         return localThrowable13.getClass().getName();
/*   966:      */       }
/*   967:      */     case 13: 
/*   968:      */       try
/*   969:      */       {
/*   970:  942 */         String str1 = local_ServerRequest.read_string();
/*   971:  943 */         localObject1 = localBPWServicesBean
/*   972:  944 */           .getCustomerByType(
/*   973:  945 */           str1);
/*   974:  947 */         if (local_ServerRequest.isRMI()) {
/*   975:  947 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*   976:      */         } else {
/*   977:  947 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*   978:      */         }
/*   979:      */       }
/*   980:      */       catch (Throwable localThrowable14)
/*   981:      */       {
/*   982:  951 */         localThrowable14.printStackTrace(Jaguar.log);
/*   983:  952 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/*   984:  953 */         return localThrowable14.getClass().getName();
/*   985:      */       }
/*   986:      */     case 14: 
/*   987:      */       try
/*   988:      */       {
/*   989:  962 */         String str2 = local_ServerRequest.read_string();
/*   990:  963 */         localObject1 = localBPWServicesBean
/*   991:  964 */           .getCustomerByFI(
/*   992:  965 */           str2);
/*   993:  967 */         if (local_ServerRequest.isRMI()) {
/*   994:  967 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*   995:      */         } else {
/*   996:  967 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*   997:      */         }
/*   998:      */       }
/*   999:      */       catch (Throwable localThrowable15)
/*  1000:      */       {
/*  1001:  971 */         localThrowable15.printStackTrace(Jaguar.log);
/*  1002:  972 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/*  1003:  973 */         return localThrowable15.getClass().getName();
/*  1004:      */       }
/*  1005:      */     case 15: 
/*  1006:      */       try
/*  1007:      */       {
/*  1008:  982 */         String str3 = local_ServerRequest.read_string();
/*  1009:  983 */         localObject1 = localBPWServicesBean
/*  1010:  984 */           .getCustomerByCategory(
/*  1011:  985 */           str3);
/*  1012:  987 */         if (local_ServerRequest.isRMI()) {
/*  1013:  987 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*  1014:      */         } else {
/*  1015:  987 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*  1016:      */         }
/*  1017:      */       }
/*  1018:      */       catch (Throwable localThrowable16)
/*  1019:      */       {
/*  1020:  991 */         localThrowable16.printStackTrace(Jaguar.log);
/*  1021:  992 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/*  1022:  993 */         return localThrowable16.getClass().getName();
/*  1023:      */       }
/*  1024:      */     case 16: 
/*  1025:      */       try
/*  1026:      */       {
/*  1027: 1002 */         String str4 = local_ServerRequest.read_string();
/*  1028: 1003 */         localObject1 = localBPWServicesBean
/*  1029: 1004 */           .getCustomerByGroup(
/*  1030: 1005 */           str4);
/*  1031: 1007 */         if (local_ServerRequest.isRMI()) {
/*  1032: 1007 */           local_ServerRequest.write_value(localObject1, new CustomerInfo[0].getClass());
/*  1033:      */         } else {
/*  1034: 1007 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject1);
/*  1035:      */         }
/*  1036:      */       }
/*  1037:      */       catch (Throwable localThrowable17)
/*  1038:      */       {
/*  1039: 1011 */         localThrowable17.printStackTrace(Jaguar.log);
/*  1040: 1012 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/*  1041: 1013 */         return localThrowable17.getClass().getName();
/*  1042:      */       }
/*  1043:      */     case 17: 
/*  1044:      */       try
/*  1045:      */       {
/*  1046: 1022 */         String str5 = local_ServerRequest.read_string();
/*  1047:      */         
/*  1048: 1024 */         localObject1 = local_ServerRequest.read_string();
/*  1049: 1025 */         localObject3 = localBPWServicesBean
/*  1050: 1026 */           .getCustomerByTypeAndFI(
/*  1051: 1027 */           str5, 
/*  1052: 1028 */           (String)localObject1);
/*  1053: 1030 */         if (local_ServerRequest.isRMI()) {
/*  1054: 1030 */           local_ServerRequest.write_value(localObject3, new CustomerInfo[0].getClass());
/*  1055:      */         } else {
/*  1056: 1030 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject3);
/*  1057:      */         }
/*  1058:      */       }
/*  1059:      */       catch (Throwable localThrowable18)
/*  1060:      */       {
/*  1061: 1034 */         localThrowable18.printStackTrace(Jaguar.log);
/*  1062: 1035 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/*  1063: 1036 */         return localThrowable18.getClass().getName();
/*  1064:      */       }
/*  1065:      */     case 18: 
/*  1066:      */       try
/*  1067:      */       {
/*  1068: 1045 */         String str6 = local_ServerRequest.read_string();
/*  1069:      */         
/*  1070: 1047 */         localObject1 = local_ServerRequest.read_string();
/*  1071: 1048 */         localObject3 = localBPWServicesBean
/*  1072: 1049 */           .getCustomerByCategoryAndFI(
/*  1073: 1050 */           str6, 
/*  1074: 1051 */           (String)localObject1);
/*  1075: 1053 */         if (local_ServerRequest.isRMI()) {
/*  1076: 1053 */           local_ServerRequest.write_value(localObject3, new CustomerInfo[0].getClass());
/*  1077:      */         } else {
/*  1078: 1053 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject3);
/*  1079:      */         }
/*  1080:      */       }
/*  1081:      */       catch (Throwable localThrowable19)
/*  1082:      */       {
/*  1083: 1057 */         localThrowable19.printStackTrace(Jaguar.log);
/*  1084: 1058 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/*  1085: 1059 */         return localThrowable19.getClass().getName();
/*  1086:      */       }
/*  1087:      */     case 19: 
/*  1088:      */       try
/*  1089:      */       {
/*  1090: 1068 */         String str7 = local_ServerRequest.read_string();
/*  1091:      */         
/*  1092: 1070 */         localObject1 = local_ServerRequest.read_string();
/*  1093: 1071 */         localObject3 = localBPWServicesBean
/*  1094: 1072 */           .getCustomerByGroupAndFI(
/*  1095: 1073 */           str7, 
/*  1096: 1074 */           (String)localObject1);
/*  1097: 1076 */         if (local_ServerRequest.isRMI()) {
/*  1098: 1076 */           local_ServerRequest.write_value(localObject3, new CustomerInfo[0].getClass());
/*  1099:      */         } else {
/*  1100: 1076 */           CustomerInfoSeqHelper.write(paramOutputStream, (CustomerInfo[])localObject3);
/*  1101:      */         }
/*  1102:      */       }
/*  1103:      */       catch (Throwable localThrowable20)
/*  1104:      */       {
/*  1105: 1080 */         localThrowable20.printStackTrace(Jaguar.log);
/*  1106: 1081 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/*  1107: 1082 */         return localThrowable20.getClass().getName();
/*  1108:      */       }
/*  1109:      */     case 20: 
/*  1110:      */       try
/*  1111:      */       {
/*  1112: 1091 */         String str8 = local_ServerRequest.read_string();
/*  1113: 1092 */         localObject1 = localBPWServicesBean
/*  1114: 1093 */           .getRPPSBillerInfoByFIRPPSId(
/*  1115: 1094 */           str8);
/*  1116: 1096 */         if (local_ServerRequest.isRMI()) {
/*  1117: 1096 */           local_ServerRequest.write_value(localObject1, new RPPSBillerInfo[0].getClass());
/*  1118:      */         } else {
/*  1119: 1096 */           RPPSBillerInfoSeqHelper.write(paramOutputStream, (RPPSBillerInfo[])localObject1);
/*  1120:      */         }
/*  1121:      */       }
/*  1122:      */       catch (Throwable localThrowable21)
/*  1123:      */       {
/*  1124: 1100 */         if ((localThrowable21 instanceof FFSException))
/*  1125:      */         {
/*  1126: 1102 */           if (UserException.ok(paramOutputStream)) {
/*  1127: 1104 */             if (local_ServerRequest.isRMI())
/*  1128:      */             {
/*  1129: 1106 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  1130: 1107 */               local_ServerRequest.write_value((FFSException)localThrowable21, FFSException.class);
/*  1131:      */             }
/*  1132:      */             else
/*  1133:      */             {
/*  1134: 1111 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  1135: 1112 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable21);
/*  1136:      */             }
/*  1137:      */           }
/*  1138: 1115 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable21);
/*  1139:      */         }
/*  1140: 1117 */         localThrowable21.printStackTrace(Jaguar.log);
/*  1141: 1118 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/*  1142: 1119 */         return localThrowable21.getClass().getName();
/*  1143:      */       }
/*  1144:      */     case 21: 
/*  1145:      */       try
/*  1146:      */       {
/*  1147: 1128 */         String str9 = local_ServerRequest.read_string();
/*  1148:      */         
/*  1149: 1130 */         localObject1 = local_ServerRequest.read_string();
/*  1150: 1131 */         localObject3 = localBPWServicesBean
/*  1151: 1132 */           .getRPPSBillerInfoByFIAndBillerRPPSId(
/*  1152: 1133 */           str9, 
/*  1153: 1134 */           (String)localObject1);
/*  1154:      */         
/*  1155: 1136 */         local_ServerRequest.write_value(localObject3, RPPSBillerInfo.class);
/*  1156:      */       }
/*  1157:      */       catch (Throwable localThrowable22)
/*  1158:      */       {
/*  1159: 1140 */         if ((localThrowable22 instanceof FFSException))
/*  1160:      */         {
/*  1161: 1142 */           if (UserException.ok(paramOutputStream)) {
/*  1162: 1144 */             if (local_ServerRequest.isRMI())
/*  1163:      */             {
/*  1164: 1146 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  1165: 1147 */               local_ServerRequest.write_value((FFSException)localThrowable22, FFSException.class);
/*  1166:      */             }
/*  1167:      */             else
/*  1168:      */             {
/*  1169: 1151 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  1170: 1152 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable22);
/*  1171:      */             }
/*  1172:      */           }
/*  1173: 1155 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable22);
/*  1174:      */         }
/*  1175: 1157 */         localThrowable22.printStackTrace(Jaguar.log);
/*  1176: 1158 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/*  1177: 1159 */         return localThrowable22.getClass().getName();
/*  1178:      */       }
/*  1179:      */     case 22: 
/*  1180:      */       try
/*  1181:      */       {
/*  1182: 1168 */         WireInfo localWireInfo1 = (WireInfo)local_ServerRequest.read_value(WireInfo.class);
/*  1183: 1169 */         localObject1 = localBPWServicesBean
/*  1184: 1170 */           .addWireTransfer(
/*  1185: 1171 */           localWireInfo1);
/*  1186:      */         
/*  1187: 1173 */         local_ServerRequest.write_value(localObject1, WireInfo.class);
/*  1188:      */       }
/*  1189:      */       catch (Throwable localThrowable23)
/*  1190:      */       {
/*  1191: 1177 */         localThrowable23.printStackTrace(Jaguar.log);
/*  1192: 1178 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/*  1193: 1179 */         return localThrowable23.getClass().getName();
/*  1194:      */       }
/*  1195:      */     case 23: 
/*  1196:      */       try
/*  1197:      */       {
/*  1198: 1188 */         WireInfo localWireInfo2 = (WireInfo)local_ServerRequest.read_value(WireInfo.class);
/*  1199: 1189 */         localObject1 = localBPWServicesBean
/*  1200: 1190 */           .modWireTransfer(
/*  1201: 1191 */           localWireInfo2);
/*  1202:      */         
/*  1203: 1193 */         local_ServerRequest.write_value(localObject1, WireInfo.class);
/*  1204:      */       }
/*  1205:      */       catch (Throwable localThrowable24)
/*  1206:      */       {
/*  1207: 1197 */         localThrowable24.printStackTrace(Jaguar.log);
/*  1208: 1198 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/*  1209: 1199 */         return localThrowable24.getClass().getName();
/*  1210:      */       }
/*  1211:      */     case 24: 
/*  1212:      */       try
/*  1213:      */       {
/*  1214: 1208 */         WireInfo localWireInfo3 = (WireInfo)local_ServerRequest.read_value(WireInfo.class);
/*  1215: 1209 */         localObject1 = localBPWServicesBean
/*  1216: 1210 */           .cancWireTransfer(
/*  1217: 1211 */           localWireInfo3);
/*  1218:      */         
/*  1219: 1213 */         local_ServerRequest.write_value(localObject1, WireInfo.class);
/*  1220:      */       }
/*  1221:      */       catch (Throwable localThrowable25)
/*  1222:      */       {
/*  1223: 1217 */         localThrowable25.printStackTrace(Jaguar.log);
/*  1224: 1218 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/*  1225: 1219 */         return localThrowable25.getClass().getName();
/*  1226:      */       }
/*  1227:      */     case 25: 
/*  1228:      */       try
/*  1229:      */       {
/*  1230: 1228 */         String str10 = local_ServerRequest.read_string();
/*  1231: 1229 */         localObject1 = localBPWServicesBean
/*  1232: 1230 */           .getWireTransferById(
/*  1233: 1231 */           str10);
/*  1234:      */         
/*  1235: 1233 */         local_ServerRequest.write_value(localObject1, WireInfo.class);
/*  1236:      */       }
/*  1237:      */       catch (Throwable localThrowable26)
/*  1238:      */       {
/*  1239: 1237 */         localThrowable26.printStackTrace(Jaguar.log);
/*  1240: 1238 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/*  1241: 1239 */         return localThrowable26.getClass().getName();
/*  1242:      */       }
/*  1243:      */     case 26: 
/*  1244:      */       try
/*  1245:      */       {
/*  1246: 1248 */         String str11 = local_ServerRequest.read_string();
/*  1247: 1249 */         localObject1 = localBPWServicesBean
/*  1248: 1250 */           .getWireTransfer(
/*  1249: 1251 */           str11);
/*  1250:      */         
/*  1251: 1253 */         local_ServerRequest.write_value(localObject1, WireInfo.class);
/*  1252:      */       }
/*  1253:      */       catch (Throwable localThrowable27)
/*  1254:      */       {
/*  1255: 1257 */         localThrowable27.printStackTrace(Jaguar.log);
/*  1256: 1258 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/*  1257: 1259 */         return localThrowable27.getClass().getName();
/*  1258:      */       }
/*  1259:      */     case 27: 
/*  1260:      */       try
/*  1261:      */       {
/*  1262:      */         String[] arrayOfString6;
/*  1263: 1268 */         if (local_ServerRequest.isRMI()) {
/*  1264: 1268 */           arrayOfString6 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*  1265:      */         } else {
/*  1266: 1268 */           arrayOfString6 = StringSeqHelper.read(paramInputStream);
/*  1267:      */         }
/*  1268: 1269 */         localObject1 = 
/*  1269: 1270 */           localBPWServicesBean.getWireTransfers(
/*  1270: 1271 */           arrayOfString6);
/*  1271: 1273 */         if (local_ServerRequest.isRMI()) {
/*  1272: 1273 */           local_ServerRequest.write_value(localObject1, new WireInfo[0].getClass());
/*  1273:      */         } else {
/*  1274: 1273 */           WireInfoSeqHelper.write(paramOutputStream, (WireInfo[])localObject1);
/*  1275:      */         }
/*  1276:      */       }
/*  1277:      */       catch (Throwable localThrowable28)
/*  1278:      */       {
/*  1279: 1277 */         localThrowable28.printStackTrace(Jaguar.log);
/*  1280: 1278 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/*  1281: 1279 */         return localThrowable28.getClass().getName();
/*  1282:      */       }
/*  1283:      */     case 28: 
/*  1284:      */       try
/*  1285:      */       {
/*  1286: 1288 */         WireInfo localWireInfo4 = (WireInfo)local_ServerRequest.read_value(WireInfo.class);
/*  1287: 1289 */         localObject1 = localBPWServicesBean
/*  1288: 1290 */           .getDuplicateWires(
/*  1289: 1291 */           localWireInfo4);
/*  1290: 1293 */         if (local_ServerRequest.isRMI()) {
/*  1291: 1293 */           local_ServerRequest.write_value(localObject1, BPWHist.class);
/*  1292:      */         } else {
/*  1293: 1293 */           BPWHistHelper.write(paramOutputStream, (BPWHist)localObject1);
/*  1294:      */         }
/*  1295:      */       }
/*  1296:      */       catch (Throwable localThrowable29)
/*  1297:      */       {
/*  1298: 1297 */         localThrowable29.printStackTrace(Jaguar.log);
/*  1299: 1298 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/*  1300: 1299 */         return localThrowable29.getClass().getName();
/*  1301:      */       }
/*  1302:      */     case 29: 
/*  1303:      */       try
/*  1304:      */       {
/*  1305: 1308 */         String str12 = local_ServerRequest.read_string();
/*  1306: 1309 */         localObject1 = localBPWServicesBean
/*  1307: 1310 */           .getBatchWires(
/*  1308: 1311 */           str12);
/*  1309: 1313 */         if (local_ServerRequest.isRMI()) {
/*  1310: 1313 */           local_ServerRequest.write_value(localObject1, new WireInfo[0].getClass());
/*  1311:      */         } else {
/*  1312: 1313 */           WireInfoSeqHelper.write(paramOutputStream, (WireInfo[])localObject1);
/*  1313:      */         }
/*  1314:      */       }
/*  1315:      */       catch (Throwable localThrowable30)
/*  1316:      */       {
/*  1317: 1317 */         localThrowable30.printStackTrace(Jaguar.log);
/*  1318: 1318 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/*  1319: 1319 */         return localThrowable30.getClass().getName();
/*  1320:      */       }
/*  1321:      */     case 30: 
/*  1322:      */       try
/*  1323:      */       {
/*  1324:      */         BPWHist localBPWHist1;
/*  1325: 1328 */         if (local_ServerRequest.isRMI()) {
/*  1326: 1328 */           localBPWHist1 = (BPWHist)local_ServerRequest.read_value(BPWHist.class);
/*  1327:      */         } else {
/*  1328: 1328 */           localBPWHist1 = BPWHistHelper.read(paramInputStream);
/*  1329:      */         }
/*  1330: 1329 */         localObject1 = 
/*  1331: 1330 */           localBPWServicesBean.getWireHistory(
/*  1332: 1331 */           localBPWHist1);
/*  1333: 1333 */         if (local_ServerRequest.isRMI()) {
/*  1334: 1333 */           local_ServerRequest.write_value(localObject1, BPWHist.class);
/*  1335:      */         } else {
/*  1336: 1333 */           BPWHistHelper.write(paramOutputStream, (BPWHist)localObject1);
/*  1337:      */         }
/*  1338:      */       }
/*  1339:      */       catch (Throwable localThrowable31)
/*  1340:      */       {
/*  1341: 1337 */         localThrowable31.printStackTrace(Jaguar.log);
/*  1342: 1338 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/*  1343: 1339 */         return localThrowable31.getClass().getName();
/*  1344:      */       }
/*  1345:      */     case 31: 
/*  1346:      */       try
/*  1347:      */       {
/*  1348:      */         BPWHist localBPWHist2;
/*  1349: 1348 */         if (local_ServerRequest.isRMI()) {
/*  1350: 1348 */           localBPWHist2 = (BPWHist)local_ServerRequest.read_value(BPWHist.class);
/*  1351:      */         } else {
/*  1352: 1348 */           localBPWHist2 = BPWHistHelper.read(paramInputStream);
/*  1353:      */         }
/*  1354: 1349 */         localObject1 = 
/*  1355: 1350 */           localBPWServicesBean.getWireHistoryByCustomer(
/*  1356: 1351 */           localBPWHist2);
/*  1357: 1353 */         if (local_ServerRequest.isRMI()) {
/*  1358: 1353 */           local_ServerRequest.write_value(localObject1, BPWHist.class);
/*  1359:      */         } else {
/*  1360: 1353 */           BPWHistHelper.write(paramOutputStream, (BPWHist)localObject1);
/*  1361:      */         }
/*  1362:      */       }
/*  1363:      */       catch (Throwable localThrowable32)
/*  1364:      */       {
/*  1365: 1357 */         localThrowable32.printStackTrace(Jaguar.log);
/*  1366: 1358 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/*  1367: 1359 */         return localThrowable32.getClass().getName();
/*  1368:      */       }
/*  1369:      */     case 32: 
/*  1370:      */       try
/*  1371:      */       {
/*  1372:      */         BPWHist localBPWHist3;
/*  1373: 1368 */         if (local_ServerRequest.isRMI()) {
/*  1374: 1368 */           localBPWHist3 = (BPWHist)local_ServerRequest.read_value(BPWHist.class);
/*  1375:      */         } else {
/*  1376: 1368 */           localBPWHist3 = BPWHistHelper.read(paramInputStream);
/*  1377:      */         }
/*  1378: 1369 */         localObject1 = 
/*  1379: 1370 */           localBPWServicesBean.getCombinedWireHistory(
/*  1380: 1371 */           localBPWHist3);
/*  1381: 1373 */         if (local_ServerRequest.isRMI()) {
/*  1382: 1373 */           local_ServerRequest.write_value(localObject1, BPWHist.class);
/*  1383:      */         } else {
/*  1384: 1373 */           BPWHistHelper.write(paramOutputStream, (BPWHist)localObject1);
/*  1385:      */         }
/*  1386:      */       }
/*  1387:      */       catch (Throwable localThrowable33)
/*  1388:      */       {
/*  1389: 1377 */         localThrowable33.printStackTrace(Jaguar.log);
/*  1390: 1378 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/*  1391: 1379 */         return localThrowable33.getClass().getName();
/*  1392:      */       }
/*  1393:      */     case 33: 
/*  1394:      */       try
/*  1395:      */       {
/*  1396: 1388 */         String str13 = local_ServerRequest.read_string();
/*  1397: 1389 */         localObject1 = localBPWServicesBean
/*  1398: 1390 */           .getAuditWireTransfer(
/*  1399: 1391 */           str13);
/*  1400: 1393 */         if (local_ServerRequest.isRMI()) {
/*  1401: 1393 */           local_ServerRequest.write_value(localObject1, new WireInfo[0].getClass());
/*  1402:      */         } else {
/*  1403: 1393 */           WireInfoSeqHelper.write(paramOutputStream, (WireInfo[])localObject1);
/*  1404:      */         }
/*  1405:      */       }
/*  1406:      */       catch (Throwable localThrowable34)
/*  1407:      */       {
/*  1408: 1397 */         localThrowable34.printStackTrace(Jaguar.log);
/*  1409: 1398 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/*  1410: 1399 */         return localThrowable34.getClass().getName();
/*  1411:      */       }
/*  1412:      */     case 34: 
/*  1413:      */       try
/*  1414:      */       {
/*  1415: 1408 */         String str14 = local_ServerRequest.read_string();
/*  1416: 1409 */         localObject1 = localBPWServicesBean
/*  1417: 1410 */           .getAuditWireTransferByExtId(
/*  1418: 1411 */           str14);
/*  1419: 1413 */         if (local_ServerRequest.isRMI()) {
/*  1420: 1413 */           local_ServerRequest.write_value(localObject1, new WireInfo[0].getClass());
/*  1421:      */         } else {
/*  1422: 1413 */           WireInfoSeqHelper.write(paramOutputStream, (WireInfo[])localObject1);
/*  1423:      */         }
/*  1424:      */       }
/*  1425:      */       catch (Throwable localThrowable35)
/*  1426:      */       {
/*  1427: 1417 */         localThrowable35.printStackTrace(Jaguar.log);
/*  1428: 1418 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/*  1429: 1419 */         return localThrowable35.getClass().getName();
/*  1430:      */       }
/*  1431:      */     case 35: 
/*  1432:      */       try
/*  1433:      */       {
/*  1434: 1428 */         WireReleaseInfo localWireReleaseInfo = (WireReleaseInfo)local_ServerRequest.read_value(WireReleaseInfo.class);
/*  1435: 1429 */         localObject1 = localBPWServicesBean
/*  1436: 1430 */           .getWireReleaseCount(
/*  1437: 1431 */           localWireReleaseInfo);
/*  1438:      */         
/*  1439: 1433 */         local_ServerRequest.write_value(localObject1, WireReleaseInfo.class);
/*  1440:      */       }
/*  1441:      */       catch (Throwable localThrowable36)
/*  1442:      */       {
/*  1443: 1437 */         localThrowable36.printStackTrace(Jaguar.log);
/*  1444: 1438 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/*  1445: 1439 */         return localThrowable36.getClass().getName();
/*  1446:      */       }
/*  1447:      */     case 36: 
/*  1448:      */       try
/*  1449:      */       {
/*  1450:      */         WireInfo[] arrayOfWireInfo1;
/*  1451: 1448 */         if (local_ServerRequest.isRMI()) {
/*  1452: 1448 */           arrayOfWireInfo1 = (WireInfo[])local_ServerRequest.read_value(new WireInfo[0].getClass());
/*  1453:      */         } else {
/*  1454: 1448 */           arrayOfWireInfo1 = WireInfoSeqHelper.read(paramInputStream);
/*  1455:      */         }
/*  1456: 1449 */         localObject1 = 
/*  1457: 1450 */           localBPWServicesBean.addWireTransfers(
/*  1458: 1451 */           arrayOfWireInfo1);
/*  1459: 1453 */         if (local_ServerRequest.isRMI()) {
/*  1460: 1453 */           local_ServerRequest.write_value(localObject1, new WireInfo[0].getClass());
/*  1461:      */         } else {
/*  1462: 1453 */           WireInfoSeqHelper.write(paramOutputStream, (WireInfo[])localObject1);
/*  1463:      */         }
/*  1464:      */       }
/*  1465:      */       catch (Throwable localThrowable37)
/*  1466:      */       {
/*  1467: 1457 */         localThrowable37.printStackTrace(Jaguar.log);
/*  1468: 1458 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable37, true);
/*  1469: 1459 */         return localThrowable37.getClass().getName();
/*  1470:      */       }
/*  1471:      */     case 37: 
/*  1472:      */       try
/*  1473:      */       {
/*  1474:      */         WireInfo[] arrayOfWireInfo2;
/*  1475: 1468 */         if (local_ServerRequest.isRMI()) {
/*  1476: 1468 */           arrayOfWireInfo2 = (WireInfo[])local_ServerRequest.read_value(new WireInfo[0].getClass());
/*  1477:      */         } else {
/*  1478: 1468 */           arrayOfWireInfo2 = WireInfoSeqHelper.read(paramInputStream);
/*  1479:      */         }
/*  1480: 1469 */         localObject1 = 
/*  1481: 1470 */           localBPWServicesBean.releaseWireTransfer(
/*  1482: 1471 */           arrayOfWireInfo2);
/*  1483: 1473 */         if (local_ServerRequest.isRMI()) {
/*  1484: 1473 */           local_ServerRequest.write_value(localObject1, new WireInfo[0].getClass());
/*  1485:      */         } else {
/*  1486: 1473 */           WireInfoSeqHelper.write(paramOutputStream, (WireInfo[])localObject1);
/*  1487:      */         }
/*  1488:      */       }
/*  1489:      */       catch (Throwable localThrowable38)
/*  1490:      */       {
/*  1491: 1477 */         localThrowable38.printStackTrace(Jaguar.log);
/*  1492: 1478 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable38, true);
/*  1493: 1479 */         return localThrowable38.getClass().getName();
/*  1494:      */       }
/*  1495:      */     case 38: 
/*  1496:      */       try
/*  1497:      */       {
/*  1498: 1488 */         RecWireInfo localRecWireInfo1 = (RecWireInfo)local_ServerRequest.read_value(RecWireInfo.class);
/*  1499: 1489 */         localObject1 = localBPWServicesBean
/*  1500: 1490 */           .addRecWireTransfer(
/*  1501: 1491 */           localRecWireInfo1);
/*  1502:      */         
/*  1503: 1493 */         local_ServerRequest.write_value(localObject1, RecWireInfo.class);
/*  1504:      */       }
/*  1505:      */       catch (Throwable localThrowable39)
/*  1506:      */       {
/*  1507: 1497 */         localThrowable39.printStackTrace(Jaguar.log);
/*  1508: 1498 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable39, true);
/*  1509: 1499 */         return localThrowable39.getClass().getName();
/*  1510:      */       }
/*  1511:      */     case 39: 
/*  1512:      */       try
/*  1513:      */       {
/*  1514: 1508 */         RecWireInfo localRecWireInfo2 = (RecWireInfo)local_ServerRequest.read_value(RecWireInfo.class);
/*  1515: 1509 */         localObject1 = localBPWServicesBean
/*  1516: 1510 */           .modRecWireTransfer(
/*  1517: 1511 */           localRecWireInfo2);
/*  1518:      */         
/*  1519: 1513 */         local_ServerRequest.write_value(localObject1, RecWireInfo.class);
/*  1520:      */       }
/*  1521:      */       catch (Throwable localThrowable40)
/*  1522:      */       {
/*  1523: 1517 */         localThrowable40.printStackTrace(Jaguar.log);
/*  1524: 1518 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable40, true);
/*  1525: 1519 */         return localThrowable40.getClass().getName();
/*  1526:      */       }
/*  1527:      */     case 40: 
/*  1528:      */       try
/*  1529:      */       {
/*  1530: 1528 */         RecWireInfo localRecWireInfo3 = (RecWireInfo)local_ServerRequest.read_value(RecWireInfo.class);
/*  1531: 1529 */         localObject1 = localBPWServicesBean
/*  1532: 1530 */           .cancRecWireTransfer(
/*  1533: 1531 */           localRecWireInfo3);
/*  1534:      */         
/*  1535: 1533 */         local_ServerRequest.write_value(localObject1, RecWireInfo.class);
/*  1536:      */       }
/*  1537:      */       catch (Throwable localThrowable41)
/*  1538:      */       {
/*  1539: 1537 */         localThrowable41.printStackTrace(Jaguar.log);
/*  1540: 1538 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable41, true);
/*  1541: 1539 */         return localThrowable41.getClass().getName();
/*  1542:      */       }
/*  1543:      */     case 41: 
/*  1544:      */       try
/*  1545:      */       {
/*  1546: 1548 */         String str15 = local_ServerRequest.read_string();
/*  1547:      */         
/*  1548: 1550 */         boolean bool = paramInputStream.read_boolean();
/*  1549: 1551 */         localObject3 = localBPWServicesBean
/*  1550: 1552 */           .getRecWireTransferById(
/*  1551: 1553 */           str15, 
/*  1552: 1554 */           bool);
/*  1553:      */         
/*  1554: 1556 */         local_ServerRequest.write_value(localObject3, RecWireInfo.class);
/*  1555:      */       }
/*  1556:      */       catch (Throwable localThrowable42)
/*  1557:      */       {
/*  1558: 1560 */         localThrowable42.printStackTrace(Jaguar.log);
/*  1559: 1561 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable42, true);
/*  1560: 1562 */         return localThrowable42.getClass().getName();
/*  1561:      */       }
/*  1562:      */     case 42: 
/*  1563:      */       try
/*  1564:      */       {
/*  1565: 1571 */         String str16 = local_ServerRequest.read_string();
/*  1566: 1572 */         localObject2 = localBPWServicesBean
/*  1567: 1573 */           .getRecWireTransferById(
/*  1568: 1574 */           str16);
/*  1569:      */         
/*  1570: 1576 */         local_ServerRequest.write_value(localObject2, RecWireInfo.class);
/*  1571:      */       }
/*  1572:      */       catch (Throwable localThrowable43)
/*  1573:      */       {
/*  1574: 1580 */         localThrowable43.printStackTrace(Jaguar.log);
/*  1575: 1581 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable43, true);
/*  1576: 1582 */         return localThrowable43.getClass().getName();
/*  1577:      */       }
/*  1578:      */     case 43: 
/*  1579:      */       try
/*  1580:      */       {
/*  1581: 1591 */         String str17 = local_ServerRequest.read_string();
/*  1582: 1592 */         localObject2 = localBPWServicesBean
/*  1583: 1593 */           .getRecWireTransfer(
/*  1584: 1594 */           str17);
/*  1585:      */         
/*  1586: 1596 */         local_ServerRequest.write_value(localObject2, RecWireInfo.class);
/*  1587:      */       }
/*  1588:      */       catch (Throwable localThrowable44)
/*  1589:      */       {
/*  1590: 1600 */         localThrowable44.printStackTrace(Jaguar.log);
/*  1591: 1601 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable44, true);
/*  1592: 1602 */         return localThrowable44.getClass().getName();
/*  1593:      */       }
/*  1594:      */     case 44: 
/*  1595:      */       try
/*  1596:      */       {
/*  1597:      */         String[] arrayOfString7;
/*  1598: 1611 */         if (local_ServerRequest.isRMI()) {
/*  1599: 1611 */           arrayOfString7 = (String[])local_ServerRequest.read_value(new String[0].getClass());
/*  1600:      */         } else {
/*  1601: 1611 */           arrayOfString7 = StringSeqHelper.read(paramInputStream);
/*  1602:      */         }
/*  1603: 1612 */         localObject2 = 
/*  1604: 1613 */           localBPWServicesBean.getRecWireTransfers(
/*  1605: 1614 */           arrayOfString7);
/*  1606: 1616 */         if (local_ServerRequest.isRMI()) {
/*  1607: 1616 */           local_ServerRequest.write_value(localObject2, new RecWireInfo[0].getClass());
/*  1608:      */         } else {
/*  1609: 1616 */           RecWireInfoSeqHelper.write(paramOutputStream, (RecWireInfo[])localObject2);
/*  1610:      */         }
/*  1611:      */       }
/*  1612:      */       catch (Throwable localThrowable45)
/*  1613:      */       {
/*  1614: 1620 */         localThrowable45.printStackTrace(Jaguar.log);
/*  1615: 1621 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable45, true);
/*  1616: 1622 */         return localThrowable45.getClass().getName();
/*  1617:      */       }
/*  1618:      */     case 45: 
/*  1619:      */       try
/*  1620:      */       {
/*  1621:      */         BPWHist localBPWHist4;
/*  1622: 1631 */         if (local_ServerRequest.isRMI()) {
/*  1623: 1631 */           localBPWHist4 = (BPWHist)local_ServerRequest.read_value(BPWHist.class);
/*  1624:      */         } else {
/*  1625: 1631 */           localBPWHist4 = BPWHistHelper.read(paramInputStream);
/*  1626:      */         }
/*  1627: 1632 */         localObject2 = 
/*  1628: 1633 */           localBPWServicesBean.getRecWireHistory(
/*  1629: 1634 */           localBPWHist4);
/*  1630: 1636 */         if (local_ServerRequest.isRMI()) {
/*  1631: 1636 */           local_ServerRequest.write_value(localObject2, BPWHist.class);
/*  1632:      */         } else {
/*  1633: 1636 */           BPWHistHelper.write(paramOutputStream, (BPWHist)localObject2);
/*  1634:      */         }
/*  1635:      */       }
/*  1636:      */       catch (Throwable localThrowable46)
/*  1637:      */       {
/*  1638: 1640 */         localThrowable46.printStackTrace(Jaguar.log);
/*  1639: 1641 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable46, true);
/*  1640: 1642 */         return localThrowable46.getClass().getName();
/*  1641:      */       }
/*  1642:      */     case 46: 
/*  1643:      */       try
/*  1644:      */       {
/*  1645:      */         BPWHist localBPWHist5;
/*  1646: 1651 */         if (local_ServerRequest.isRMI()) {
/*  1647: 1651 */           localBPWHist5 = (BPWHist)local_ServerRequest.read_value(BPWHist.class);
/*  1648:      */         } else {
/*  1649: 1651 */           localBPWHist5 = BPWHistHelper.read(paramInputStream);
/*  1650:      */         }
/*  1651: 1652 */         localObject2 = 
/*  1652: 1653 */           localBPWServicesBean.getRecWireHistoryByCustomer(
/*  1653: 1654 */           localBPWHist5);
/*  1654: 1656 */         if (local_ServerRequest.isRMI()) {
/*  1655: 1656 */           local_ServerRequest.write_value(localObject2, BPWHist.class);
/*  1656:      */         } else {
/*  1657: 1656 */           BPWHistHelper.write(paramOutputStream, (BPWHist)localObject2);
/*  1658:      */         }
/*  1659:      */       }
/*  1660:      */       catch (Throwable localThrowable47)
/*  1661:      */       {
/*  1662: 1660 */         localThrowable47.printStackTrace(Jaguar.log);
/*  1663: 1661 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable47, true);
/*  1664: 1662 */         return localThrowable47.getClass().getName();
/*  1665:      */       }
/*  1666:      */     case 47: 
/*  1667:      */       try
/*  1668:      */       {
/*  1669:      */         RecWireInfo[] arrayOfRecWireInfo;
/*  1670: 1671 */         if (local_ServerRequest.isRMI()) {
/*  1671: 1671 */           arrayOfRecWireInfo = (RecWireInfo[])local_ServerRequest.read_value(new RecWireInfo[0].getClass());
/*  1672:      */         } else {
/*  1673: 1671 */           arrayOfRecWireInfo = RecWireInfoSeqHelper.read(paramInputStream);
/*  1674:      */         }
/*  1675: 1672 */         localObject2 = 
/*  1676: 1673 */           localBPWServicesBean.addRecWireTransfers(
/*  1677: 1674 */           arrayOfRecWireInfo);
/*  1678: 1676 */         if (local_ServerRequest.isRMI()) {
/*  1679: 1676 */           local_ServerRequest.write_value(localObject2, new RecWireInfo[0].getClass());
/*  1680:      */         } else {
/*  1681: 1676 */           RecWireInfoSeqHelper.write(paramOutputStream, (RecWireInfo[])localObject2);
/*  1682:      */         }
/*  1683:      */       }
/*  1684:      */       catch (Throwable localThrowable48)
/*  1685:      */       {
/*  1686: 1680 */         localThrowable48.printStackTrace(Jaguar.log);
/*  1687: 1681 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable48, true);
/*  1688: 1682 */         return localThrowable48.getClass().getName();
/*  1689:      */       }
/*  1690:      */     case 48: 
/*  1691:      */       try
/*  1692:      */       {
/*  1693: 1690 */         HashMap localHashMap = localBPWServicesBean
/*  1694: 1691 */           .getWiresConfiguration();
/*  1695:      */         
/*  1696: 1693 */         local_ServerRequest.write_value(localHashMap, HashMap.class);
/*  1697:      */       }
/*  1698:      */       catch (Throwable localThrowable49)
/*  1699:      */       {
/*  1700: 1697 */         localThrowable49.printStackTrace(Jaguar.log);
/*  1701: 1698 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable49, true);
/*  1702: 1699 */         return localThrowable49.getClass().getName();
/*  1703:      */       }
/*  1704:      */     case 49: 
/*  1705:      */       try
/*  1706:      */       {
/*  1707: 1708 */         WireBatchInfo localWireBatchInfo1 = (WireBatchInfo)local_ServerRequest.read_value(WireBatchInfo.class);
/*  1708: 1709 */         localObject2 = localBPWServicesBean
/*  1709: 1710 */           .addWireTransferBatch(
/*  1710: 1711 */           localWireBatchInfo1);
/*  1711:      */         
/*  1712: 1713 */         local_ServerRequest.write_value(localObject2, WireBatchInfo.class);
/*  1713:      */       }
/*  1714:      */       catch (Throwable localThrowable50)
/*  1715:      */       {
/*  1716: 1717 */         localThrowable50.printStackTrace(Jaguar.log);
/*  1717: 1718 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable50, true);
/*  1718: 1719 */         return localThrowable50.getClass().getName();
/*  1719:      */       }
/*  1720:      */     case 50: 
/*  1721:      */       try
/*  1722:      */       {
/*  1723: 1728 */         WireBatchInfo localWireBatchInfo2 = (WireBatchInfo)local_ServerRequest.read_value(WireBatchInfo.class);
/*  1724: 1729 */         localObject2 = localBPWServicesBean
/*  1725: 1730 */           .modWireTransferBatch(
/*  1726: 1731 */           localWireBatchInfo2);
/*  1727:      */         
/*  1728: 1733 */         local_ServerRequest.write_value(localObject2, WireBatchInfo.class);
/*  1729:      */       }
/*  1730:      */       catch (Throwable localThrowable51)
/*  1731:      */       {
/*  1732: 1737 */         localThrowable51.printStackTrace(Jaguar.log);
/*  1733: 1738 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable51, true);
/*  1734: 1739 */         return localThrowable51.getClass().getName();
/*  1735:      */       }
/*  1736:      */     default: 
/*  1737: 1745 */       return 
/*  1738: 1746 */         invoke1(
/*  1739: 1747 */         local_ServerRequest, 
/*  1740: 1748 */         paramInputStream, 
/*  1741: 1749 */         paramOutputStream, 
/*  1742: 1750 */         localBPWServicesBean, 
/*  1743: 1751 */         localInteger);
/*  1744:      */     }
/*  1745: 1755 */     return null;
/*  1746:      */   }
/*  1747:      */   
/*  1748:      */   private static String invoke1(_ServerRequest param_ServerRequest, InputStream paramInputStream, OutputStream paramOutputStream, BPWServicesBean paramBPWServicesBean, Integer paramInteger)
/*  1749:      */   {
/*  1750:      */     Object localObject1;
/*  1751:      */     Object localObject2;
/*  1752:      */     int j;
/*  1753:      */     Object localObject5;
/*  1754:      */     Object localObject3;
/*  1755:      */     Object localObject4;
/*  1756: 1765 */     switch (paramInteger.intValue())
/*  1757:      */     {
/*  1758:      */     case 51: 
/*  1759:      */       try
/*  1760:      */       {
/*  1761: 1772 */         WireBatchInfo localWireBatchInfo1 = (WireBatchInfo)param_ServerRequest.read_value(WireBatchInfo.class);
/*  1762: 1773 */         localObject1 = paramBPWServicesBean
/*  1763: 1774 */           .canWireTransferBatch(
/*  1764: 1775 */           localWireBatchInfo1);
/*  1765:      */         
/*  1766: 1777 */         param_ServerRequest.write_value(localObject1, WireBatchInfo.class);
/*  1767:      */       }
/*  1768:      */       catch (Throwable localThrowable1)
/*  1769:      */       {
/*  1770: 1781 */         localThrowable1.printStackTrace(Jaguar.log);
/*  1771: 1782 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/*  1772: 1783 */         return localThrowable1.getClass().getName();
/*  1773:      */       }
/*  1774:      */     case 52: 
/*  1775:      */       try
/*  1776:      */       {
/*  1777: 1792 */         WireBatchInfo localWireBatchInfo2 = (WireBatchInfo)param_ServerRequest.read_value(WireBatchInfo.class);
/*  1778: 1793 */         localObject1 = paramBPWServicesBean
/*  1779: 1794 */           .getWireTransferBatch(
/*  1780: 1795 */           localWireBatchInfo2);
/*  1781: 1797 */         if (param_ServerRequest.isRMI()) {
/*  1782: 1797 */           param_ServerRequest.write_value(localObject1, new WireBatchInfo[0].getClass());
/*  1783:      */         } else {
/*  1784: 1797 */           WireBatchInfoSeqHelper.write(paramOutputStream, (WireBatchInfo[])localObject1);
/*  1785:      */         }
/*  1786:      */       }
/*  1787:      */       catch (Throwable localThrowable2)
/*  1788:      */       {
/*  1789: 1801 */         localThrowable2.printStackTrace(Jaguar.log);
/*  1790: 1802 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/*  1791: 1803 */         return localThrowable2.getClass().getName();
/*  1792:      */       }
/*  1793:      */     case 53: 
/*  1794:      */       try
/*  1795:      */       {
/*  1796:      */         BPWHist localBPWHist;
/*  1797: 1812 */         if (param_ServerRequest.isRMI()) {
/*  1798: 1812 */           localBPWHist = (BPWHist)param_ServerRequest.read_value(BPWHist.class);
/*  1799:      */         } else {
/*  1800: 1812 */           localBPWHist = BPWHistHelper.read(paramInputStream);
/*  1801:      */         }
/*  1802: 1813 */         localObject1 = 
/*  1803: 1814 */           paramBPWServicesBean.getWireBatchHistory(
/*  1804: 1815 */           localBPWHist);
/*  1805: 1817 */         if (param_ServerRequest.isRMI()) {
/*  1806: 1817 */           param_ServerRequest.write_value(localObject1, BPWHist.class);
/*  1807:      */         } else {
/*  1808: 1817 */           BPWHistHelper.write(paramOutputStream, (BPWHist)localObject1);
/*  1809:      */         }
/*  1810:      */       }
/*  1811:      */       catch (Throwable localThrowable3)
/*  1812:      */       {
/*  1813: 1821 */         localThrowable3.printStackTrace(Jaguar.log);
/*  1814: 1822 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/*  1815: 1823 */         return localThrowable3.getClass().getName();
/*  1816:      */       }
/*  1817:      */     case 54: 
/*  1818:      */       try
/*  1819:      */       {
/*  1820: 1832 */         String str1 = param_ServerRequest.read_string();
/*  1821: 1833 */         boolean bool = paramBPWServicesBean
/*  1822: 1834 */           .isWireBatchDeleteable(
/*  1823: 1835 */           str1);
/*  1824:      */         
/*  1825: 1837 */         paramOutputStream.write_boolean(bool);
/*  1826:      */       }
/*  1827:      */       catch (Throwable localThrowable4)
/*  1828:      */       {
/*  1829: 1841 */         localThrowable4.printStackTrace(Jaguar.log);
/*  1830: 1842 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/*  1831: 1843 */         return localThrowable4.getClass().getName();
/*  1832:      */       }
/*  1833:      */     case 55: 
/*  1834:      */       try
/*  1835:      */       {
/*  1836:      */         WirePayeeInfo[] arrayOfWirePayeeInfo;
/*  1837: 1852 */         if (param_ServerRequest.isRMI()) {
/*  1838: 1852 */           arrayOfWirePayeeInfo = (WirePayeeInfo[])param_ServerRequest.read_value(new WirePayeeInfo[0].getClass());
/*  1839:      */         } else {
/*  1840: 1852 */           arrayOfWirePayeeInfo = WirePayeeInfoSeqHelper.read(paramInputStream);
/*  1841:      */         }
/*  1842: 1853 */         localObject2 = 
/*  1843: 1854 */           paramBPWServicesBean.addWirePayee(
/*  1844: 1855 */           arrayOfWirePayeeInfo);
/*  1845: 1857 */         if (param_ServerRequest.isRMI()) {
/*  1846: 1857 */           param_ServerRequest.write_value(localObject2, new WirePayeeInfo[0].getClass());
/*  1847:      */         } else {
/*  1848: 1857 */           WirePayeeInfoSeqHelper.write(paramOutputStream, (WirePayeeInfo[])localObject2);
/*  1849:      */         }
/*  1850:      */       }
/*  1851:      */       catch (Throwable localThrowable5)
/*  1852:      */       {
/*  1853: 1861 */         localThrowable5.printStackTrace(Jaguar.log);
/*  1854: 1862 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/*  1855: 1863 */         return localThrowable5.getClass().getName();
/*  1856:      */       }
/*  1857:      */     case 56: 
/*  1858:      */       try
/*  1859:      */       {
/*  1860: 1872 */         WirePayeeInfo localWirePayeeInfo1 = (WirePayeeInfo)param_ServerRequest.read_value(WirePayeeInfo.class);
/*  1861: 1873 */         localObject2 = paramBPWServicesBean
/*  1862: 1874 */           .addWirePayee(
/*  1863: 1875 */           localWirePayeeInfo1);
/*  1864:      */         
/*  1865: 1877 */         param_ServerRequest.write_value(localObject2, WirePayeeInfo.class);
/*  1866:      */       }
/*  1867:      */       catch (Throwable localThrowable6)
/*  1868:      */       {
/*  1869: 1881 */         localThrowable6.printStackTrace(Jaguar.log);
/*  1870: 1882 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/*  1871: 1883 */         return localThrowable6.getClass().getName();
/*  1872:      */       }
/*  1873:      */     case 57: 
/*  1874:      */       try
/*  1875:      */       {
/*  1876: 1892 */         WirePayeeInfo localWirePayeeInfo2 = (WirePayeeInfo)param_ServerRequest.read_value(WirePayeeInfo.class);
/*  1877: 1893 */         localObject2 = paramBPWServicesBean
/*  1878: 1894 */           .modWirePayee(
/*  1879: 1895 */           localWirePayeeInfo2);
/*  1880:      */         
/*  1881: 1897 */         param_ServerRequest.write_value(localObject2, WirePayeeInfo.class);
/*  1882:      */       }
/*  1883:      */       catch (Throwable localThrowable7)
/*  1884:      */       {
/*  1885: 1901 */         localThrowable7.printStackTrace(Jaguar.log);
/*  1886: 1902 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/*  1887: 1903 */         return localThrowable7.getClass().getName();
/*  1888:      */       }
/*  1889:      */     case 58: 
/*  1890:      */       try
/*  1891:      */       {
/*  1892: 1912 */         WirePayeeInfo localWirePayeeInfo3 = (WirePayeeInfo)param_ServerRequest.read_value(WirePayeeInfo.class);
/*  1893: 1913 */         localObject2 = paramBPWServicesBean
/*  1894: 1914 */           .canWirePayee(
/*  1895: 1915 */           localWirePayeeInfo3);
/*  1896:      */         
/*  1897: 1917 */         param_ServerRequest.write_value(localObject2, WirePayeeInfo.class);
/*  1898:      */       }
/*  1899:      */       catch (Throwable localThrowable8)
/*  1900:      */       {
/*  1901: 1921 */         localThrowable8.printStackTrace(Jaguar.log);
/*  1902: 1922 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/*  1903: 1923 */         return localThrowable8.getClass().getName();
/*  1904:      */       }
/*  1905:      */     case 59: 
/*  1906:      */       try
/*  1907:      */       {
/*  1908: 1932 */         WirePayeeInfo localWirePayeeInfo4 = (WirePayeeInfo)param_ServerRequest.read_value(WirePayeeInfo.class);
/*  1909: 1933 */         localObject2 = paramBPWServicesBean
/*  1910: 1934 */           .getWirePayee(
/*  1911: 1935 */           localWirePayeeInfo4);
/*  1912:      */         
/*  1913: 1937 */         param_ServerRequest.write_value(localObject2, WirePayeeInfo.class);
/*  1914:      */       }
/*  1915:      */       catch (Throwable localThrowable9)
/*  1916:      */       {
/*  1917: 1941 */         localThrowable9.printStackTrace(Jaguar.log);
/*  1918: 1942 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/*  1919: 1943 */         return localThrowable9.getClass().getName();
/*  1920:      */       }
/*  1921:      */     case 60: 
/*  1922:      */       try
/*  1923:      */       {
/*  1924: 1952 */         String str2 = param_ServerRequest.read_string();
/*  1925: 1953 */         localObject2 = paramBPWServicesBean
/*  1926: 1954 */           .getWirePayee(
/*  1927: 1955 */           str2);
/*  1928:      */         
/*  1929: 1957 */         param_ServerRequest.write_value(localObject2, WirePayeeInfo.class);
/*  1930:      */       }
/*  1931:      */       catch (Throwable localThrowable10)
/*  1932:      */       {
/*  1933: 1961 */         localThrowable10.printStackTrace(Jaguar.log);
/*  1934: 1962 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/*  1935: 1963 */         return localThrowable10.getClass().getName();
/*  1936:      */       }
/*  1937:      */     case 61: 
/*  1938:      */       try
/*  1939:      */       {
/*  1940: 1972 */         BPWPayeeList localBPWPayeeList1 = (BPWPayeeList)param_ServerRequest.read_value(BPWPayeeList.class);
/*  1941: 1973 */         localObject2 = paramBPWServicesBean
/*  1942: 1974 */           .getWirePayeeByType(
/*  1943: 1975 */           localBPWPayeeList1);
/*  1944:      */         
/*  1945: 1977 */         param_ServerRequest.write_value(localObject2, BPWPayeeList.class);
/*  1946:      */       }
/*  1947:      */       catch (Throwable localThrowable11)
/*  1948:      */       {
/*  1949: 1981 */         localThrowable11.printStackTrace(Jaguar.log);
/*  1950: 1982 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/*  1951: 1983 */         return localThrowable11.getClass().getName();
/*  1952:      */       }
/*  1953:      */     case 62: 
/*  1954:      */       try
/*  1955:      */       {
/*  1956: 1992 */         BPWPayeeList localBPWPayeeList2 = (BPWPayeeList)param_ServerRequest.read_value(BPWPayeeList.class);
/*  1957: 1993 */         localObject2 = paramBPWServicesBean
/*  1958: 1994 */           .getWirePayeeByStatus(
/*  1959: 1995 */           localBPWPayeeList2);
/*  1960:      */         
/*  1961: 1997 */         param_ServerRequest.write_value(localObject2, BPWPayeeList.class);
/*  1962:      */       }
/*  1963:      */       catch (Throwable localThrowable12)
/*  1964:      */       {
/*  1965: 2001 */         localThrowable12.printStackTrace(Jaguar.log);
/*  1966: 2002 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/*  1967: 2003 */         return localThrowable12.getClass().getName();
/*  1968:      */       }
/*  1969:      */     case 63: 
/*  1970:      */       try
/*  1971:      */       {
/*  1972: 2012 */         BPWPayeeList localBPWPayeeList3 = (BPWPayeeList)param_ServerRequest.read_value(BPWPayeeList.class);
/*  1973: 2013 */         localObject2 = paramBPWServicesBean
/*  1974: 2014 */           .getWirePayeeByGroup(
/*  1975: 2015 */           localBPWPayeeList3);
/*  1976:      */         
/*  1977: 2017 */         param_ServerRequest.write_value(localObject2, BPWPayeeList.class);
/*  1978:      */       }
/*  1979:      */       catch (Throwable localThrowable13)
/*  1980:      */       {
/*  1981: 2021 */         localThrowable13.printStackTrace(Jaguar.log);
/*  1982: 2022 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/*  1983: 2023 */         return localThrowable13.getClass().getName();
/*  1984:      */       }
/*  1985:      */     case 64: 
/*  1986:      */       try
/*  1987:      */       {
/*  1988: 2032 */         BPWPayeeList localBPWPayeeList4 = (BPWPayeeList)param_ServerRequest.read_value(BPWPayeeList.class);
/*  1989: 2033 */         localObject2 = paramBPWServicesBean
/*  1990: 2034 */           .getWirePayeeByCustomer(
/*  1991: 2035 */           localBPWPayeeList4);
/*  1992:      */         
/*  1993: 2037 */         param_ServerRequest.write_value(localObject2, BPWPayeeList.class);
/*  1994:      */       }
/*  1995:      */       catch (Throwable localThrowable14)
/*  1996:      */       {
/*  1997: 2041 */         localThrowable14.printStackTrace(Jaguar.log);
/*  1998: 2042 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/*  1999: 2043 */         return localThrowable14.getClass().getName();
/*  2000:      */       }
/*  2001:      */     case 65: 
/*  2002:      */       try
/*  2003:      */       {
/*  2004: 2052 */         String str3 = param_ServerRequest.read_string();
/*  2005: 2054 */         if (param_ServerRequest.isRMI()) {
/*  2006: 2054 */           localObject2 = (BPWBankInfo[])param_ServerRequest.read_value(new BPWBankInfo[0].getClass());
/*  2007:      */         } else {
/*  2008: 2054 */           localObject2 = BPWBankInfoSeqHelper.read(paramInputStream);
/*  2009:      */         }
/*  2010: 2055 */         j = 
/*  2011: 2056 */           paramBPWServicesBean.addIntermediaryBanksToBeneficiary(
/*  2012: 2057 */           str3, 
/*  2013: 2058 */           (BPWBankInfo[])localObject2);
/*  2014:      */         
/*  2015: 2060 */         paramOutputStream.write_long(j);
/*  2016:      */       }
/*  2017:      */       catch (Throwable localThrowable15)
/*  2018:      */       {
/*  2019: 2064 */         localThrowable15.printStackTrace(Jaguar.log);
/*  2020: 2065 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/*  2021: 2066 */         return localThrowable15.getClass().getName();
/*  2022:      */       }
/*  2023:      */     case 66: 
/*  2024:      */       try
/*  2025:      */       {
/*  2026: 2075 */         String str4 = param_ServerRequest.read_string();
/*  2027: 2077 */         if (param_ServerRequest.isRMI()) {
/*  2028: 2077 */           localObject2 = (BPWBankInfo[])param_ServerRequest.read_value(new BPWBankInfo[0].getClass());
/*  2029:      */         } else {
/*  2030: 2077 */           localObject2 = BPWBankInfoSeqHelper.read(paramInputStream);
/*  2031:      */         }
/*  2032: 2078 */         j = 
/*  2033: 2079 */           paramBPWServicesBean.delIntermediaryBanksFromBeneficiary(
/*  2034: 2080 */           str4, 
/*  2035: 2081 */           (BPWBankInfo[])localObject2);
/*  2036:      */         
/*  2037: 2083 */         paramOutputStream.write_long(j);
/*  2038:      */       }
/*  2039:      */       catch (Throwable localThrowable16)
/*  2040:      */       {
/*  2041: 2087 */         localThrowable16.printStackTrace(Jaguar.log);
/*  2042: 2088 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/*  2043: 2089 */         return localThrowable16.getClass().getName();
/*  2044:      */       }
/*  2045:      */     case 67: 
/*  2046:      */       try
/*  2047:      */       {
/*  2048:      */         BPWBankInfo[] arrayOfBPWBankInfo1;
/*  2049: 2098 */         if (param_ServerRequest.isRMI()) {
/*  2050: 2098 */           arrayOfBPWBankInfo1 = (BPWBankInfo[])param_ServerRequest.read_value(new BPWBankInfo[0].getClass());
/*  2051:      */         } else {
/*  2052: 2098 */           arrayOfBPWBankInfo1 = BPWBankInfoSeqHelper.read(paramInputStream);
/*  2053:      */         }
/*  2054: 2099 */         localObject2 = 
/*  2055: 2100 */           paramBPWServicesBean.addWireBanks(
/*  2056: 2101 */           arrayOfBPWBankInfo1);
/*  2057: 2103 */         if (param_ServerRequest.isRMI()) {
/*  2058: 2103 */           param_ServerRequest.write_value(localObject2, new BPWBankInfo[0].getClass());
/*  2059:      */         } else {
/*  2060: 2103 */           BPWBankInfoSeqHelper.write(paramOutputStream, (BPWBankInfo[])localObject2);
/*  2061:      */         }
/*  2062:      */       }
/*  2063:      */       catch (Throwable localThrowable17)
/*  2064:      */       {
/*  2065: 2107 */         localThrowable17.printStackTrace(Jaguar.log);
/*  2066: 2108 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/*  2067: 2109 */         return localThrowable17.getClass().getName();
/*  2068:      */       }
/*  2069:      */     case 68: 
/*  2070:      */       try
/*  2071:      */       {
/*  2072:      */         BPWBankInfo[] arrayOfBPWBankInfo2;
/*  2073: 2118 */         if (param_ServerRequest.isRMI()) {
/*  2074: 2118 */           arrayOfBPWBankInfo2 = (BPWBankInfo[])param_ServerRequest.read_value(new BPWBankInfo[0].getClass());
/*  2075:      */         } else {
/*  2076: 2118 */           arrayOfBPWBankInfo2 = BPWBankInfoSeqHelper.read(paramInputStream);
/*  2077:      */         }
/*  2078: 2119 */         localObject2 = 
/*  2079: 2120 */           paramBPWServicesBean.modWireBanks(
/*  2080: 2121 */           arrayOfBPWBankInfo2);
/*  2081: 2123 */         if (param_ServerRequest.isRMI()) {
/*  2082: 2123 */           param_ServerRequest.write_value(localObject2, new BPWBankInfo[0].getClass());
/*  2083:      */         } else {
/*  2084: 2123 */           BPWBankInfoSeqHelper.write(paramOutputStream, (BPWBankInfo[])localObject2);
/*  2085:      */         }
/*  2086:      */       }
/*  2087:      */       catch (Throwable localThrowable18)
/*  2088:      */       {
/*  2089: 2127 */         localThrowable18.printStackTrace(Jaguar.log);
/*  2090: 2128 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/*  2091: 2129 */         return localThrowable18.getClass().getName();
/*  2092:      */       }
/*  2093:      */     case 69: 
/*  2094:      */       try
/*  2095:      */       {
/*  2096:      */         BPWBankInfo[] arrayOfBPWBankInfo3;
/*  2097: 2138 */         if (param_ServerRequest.isRMI()) {
/*  2098: 2138 */           arrayOfBPWBankInfo3 = (BPWBankInfo[])param_ServerRequest.read_value(new BPWBankInfo[0].getClass());
/*  2099:      */         } else {
/*  2100: 2138 */           arrayOfBPWBankInfo3 = BPWBankInfoSeqHelper.read(paramInputStream);
/*  2101:      */         }
/*  2102: 2139 */         localObject2 = 
/*  2103: 2140 */           paramBPWServicesBean.delWireBanks(
/*  2104: 2141 */           arrayOfBPWBankInfo3);
/*  2105: 2143 */         if (param_ServerRequest.isRMI()) {
/*  2106: 2143 */           param_ServerRequest.write_value(localObject2, new BPWBankInfo[0].getClass());
/*  2107:      */         } else {
/*  2108: 2143 */           BPWBankInfoSeqHelper.write(paramOutputStream, (BPWBankInfo[])localObject2);
/*  2109:      */         }
/*  2110:      */       }
/*  2111:      */       catch (Throwable localThrowable19)
/*  2112:      */       {
/*  2113: 2147 */         localThrowable19.printStackTrace(Jaguar.log);
/*  2114: 2148 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/*  2115: 2149 */         return localThrowable19.getClass().getName();
/*  2116:      */       }
/*  2117:      */     case 70: 
/*  2118:      */       try
/*  2119:      */       {
/*  2120: 2158 */         String str5 = param_ServerRequest.read_string();
/*  2121:      */         
/*  2122: 2160 */         localObject2 = param_ServerRequest.read_string();
/*  2123:      */         
/*  2124: 2162 */         String str20 = param_ServerRequest.read_string();
/*  2125:      */         
/*  2126: 2164 */         localObject5 = param_ServerRequest.read_string();
/*  2127: 2165 */         BPWBankInfo[] arrayOfBPWBankInfo4 = paramBPWServicesBean
/*  2128: 2166 */           .getWireBanks(
/*  2129: 2167 */           str5, 
/*  2130: 2168 */           (String)localObject2, 
/*  2131: 2169 */           str20, 
/*  2132: 2170 */           (String)localObject5);
/*  2133: 2172 */         if (param_ServerRequest.isRMI()) {
/*  2134: 2172 */           param_ServerRequest.write_value(arrayOfBPWBankInfo4, new BPWBankInfo[0].getClass());
/*  2135:      */         } else {
/*  2136: 2172 */           BPWBankInfoSeqHelper.write(paramOutputStream, arrayOfBPWBankInfo4);
/*  2137:      */         }
/*  2138:      */       }
/*  2139:      */       catch (Throwable localThrowable20)
/*  2140:      */       {
/*  2141: 2176 */         localThrowable20.printStackTrace(Jaguar.log);
/*  2142: 2177 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/*  2143: 2178 */         return localThrowable20.getClass().getName();
/*  2144:      */       }
/*  2145:      */     case 71: 
/*  2146:      */       try
/*  2147:      */       {
/*  2148: 2187 */         String str6 = param_ServerRequest.read_string();
/*  2149: 2188 */         localObject2 = paramBPWServicesBean
/*  2150: 2189 */           .getWireBanksByRTN(
/*  2151: 2190 */           str6);
/*  2152: 2192 */         if (param_ServerRequest.isRMI()) {
/*  2153: 2192 */           param_ServerRequest.write_value(localObject2, new BPWBankInfo[0].getClass());
/*  2154:      */         } else {
/*  2155: 2192 */           BPWBankInfoSeqHelper.write(paramOutputStream, (BPWBankInfo[])localObject2);
/*  2156:      */         }
/*  2157:      */       }
/*  2158:      */       catch (Throwable localThrowable21)
/*  2159:      */       {
/*  2160: 2196 */         localThrowable21.printStackTrace(Jaguar.log);
/*  2161: 2197 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/*  2162: 2198 */         return localThrowable21.getClass().getName();
/*  2163:      */       }
/*  2164:      */     case 72: 
/*  2165:      */       try
/*  2166:      */       {
/*  2167: 2207 */         String str7 = param_ServerRequest.read_string();
/*  2168: 2208 */         localObject2 = paramBPWServicesBean
/*  2169: 2209 */           .getWireBanksByID(
/*  2170: 2210 */           str7);
/*  2171:      */         
/*  2172: 2212 */         param_ServerRequest.write_value(localObject2, BPWBankInfo.class);
/*  2173:      */       }
/*  2174:      */       catch (Throwable localThrowable22)
/*  2175:      */       {
/*  2176: 2216 */         localThrowable22.printStackTrace(Jaguar.log);
/*  2177: 2217 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/*  2178: 2218 */         return localThrowable22.getClass().getName();
/*  2179:      */       }
/*  2180:      */     case 73: 
/*  2181:      */       try
/*  2182:      */       {
/*  2183:      */         WireInfo[] arrayOfWireInfo1;
/*  2184: 2227 */         if (param_ServerRequest.isRMI()) {
/*  2185: 2227 */           arrayOfWireInfo1 = (WireInfo[])param_ServerRequest.read_value(new WireInfo[0].getClass());
/*  2186:      */         } else {
/*  2187: 2227 */           arrayOfWireInfo1 = WireInfoSeqHelper.read(paramInputStream);
/*  2188:      */         }
/*  2189: 2229 */         paramBPWServicesBean.processWireApprovalRslt(
/*  2190: 2230 */           arrayOfWireInfo1);
/*  2191:      */       }
/*  2192:      */       catch (Throwable localThrowable23)
/*  2193:      */       {
/*  2194: 2235 */         localThrowable23.printStackTrace(Jaguar.log);
/*  2195: 2236 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/*  2196: 2237 */         return localThrowable23.getClass().getName();
/*  2197:      */       }
/*  2198:      */     case 74: 
/*  2199:      */       try
/*  2200:      */       {
/*  2201:      */         WireInfo[] arrayOfWireInfo2;
/*  2202: 2246 */         if (param_ServerRequest.isRMI()) {
/*  2203: 2246 */           arrayOfWireInfo2 = (WireInfo[])param_ServerRequest.read_value(new WireInfo[0].getClass());
/*  2204:      */         } else {
/*  2205: 2246 */           arrayOfWireInfo2 = WireInfoSeqHelper.read(paramInputStream);
/*  2206:      */         }
/*  2207: 2248 */         paramBPWServicesBean.processWireBackendlRslt(
/*  2208: 2249 */           arrayOfWireInfo2);
/*  2209:      */       }
/*  2210:      */       catch (Throwable localThrowable24)
/*  2211:      */       {
/*  2212: 2254 */         localThrowable24.printStackTrace(Jaguar.log);
/*  2213: 2255 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/*  2214: 2256 */         return localThrowable24.getClass().getName();
/*  2215:      */       }
/*  2216:      */     case 75: 
/*  2217:      */       try
/*  2218:      */       {
/*  2219:      */         WireInfo[] arrayOfWireInfo3;
/*  2220: 2265 */         if (param_ServerRequest.isRMI()) {
/*  2221: 2265 */           arrayOfWireInfo3 = (WireInfo[])param_ServerRequest.read_value(new WireInfo[0].getClass());
/*  2222:      */         } else {
/*  2223: 2265 */           arrayOfWireInfo3 = WireInfoSeqHelper.read(paramInputStream);
/*  2224:      */         }
/*  2225: 2267 */         paramBPWServicesBean.processWireApprovalRevertRslt(
/*  2226: 2268 */           arrayOfWireInfo3);
/*  2227:      */       }
/*  2228:      */       catch (Throwable localThrowable25)
/*  2229:      */       {
/*  2230: 2273 */         localThrowable25.printStackTrace(Jaguar.log);
/*  2231: 2274 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/*  2232: 2275 */         return localThrowable25.getClass().getName();
/*  2233:      */       }
/*  2234:      */     case 76: 
/*  2235:      */       try
/*  2236:      */       {
/*  2237: 2284 */         BPWFIInfo localBPWFIInfo1 = (BPWFIInfo)param_ServerRequest.read_value(BPWFIInfo.class);
/*  2238: 2285 */         localObject2 = paramBPWServicesBean
/*  2239: 2286 */           .addBPWFIInfo(
/*  2240: 2287 */           localBPWFIInfo1);
/*  2241:      */         
/*  2242: 2289 */         param_ServerRequest.write_value(localObject2, BPWFIInfo.class);
/*  2243:      */       }
/*  2244:      */       catch (Throwable localThrowable26)
/*  2245:      */       {
/*  2246: 2293 */         localThrowable26.printStackTrace(Jaguar.log);
/*  2247: 2294 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/*  2248: 2295 */         return localThrowable26.getClass().getName();
/*  2249:      */       }
/*  2250:      */     case 77: 
/*  2251:      */       try
/*  2252:      */       {
/*  2253: 2304 */         BPWFIInfo localBPWFIInfo2 = (BPWFIInfo)param_ServerRequest.read_value(BPWFIInfo.class);
/*  2254: 2305 */         localObject2 = paramBPWServicesBean
/*  2255: 2306 */           .modBPWFIInfo(
/*  2256: 2307 */           localBPWFIInfo2);
/*  2257:      */         
/*  2258: 2309 */         param_ServerRequest.write_value(localObject2, BPWFIInfo.class);
/*  2259:      */       }
/*  2260:      */       catch (Throwable localThrowable27)
/*  2261:      */       {
/*  2262: 2313 */         localThrowable27.printStackTrace(Jaguar.log);
/*  2263: 2314 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/*  2264: 2315 */         return localThrowable27.getClass().getName();
/*  2265:      */       }
/*  2266:      */     case 78: 
/*  2267:      */       try
/*  2268:      */       {
/*  2269: 2324 */         BPWFIInfo localBPWFIInfo3 = (BPWFIInfo)param_ServerRequest.read_value(BPWFIInfo.class);
/*  2270: 2325 */         localObject2 = paramBPWServicesBean
/*  2271: 2326 */           .canBPWFIInfo(
/*  2272: 2327 */           localBPWFIInfo3);
/*  2273:      */         
/*  2274: 2329 */         param_ServerRequest.write_value(localObject2, BPWFIInfo.class);
/*  2275:      */       }
/*  2276:      */       catch (Throwable localThrowable28)
/*  2277:      */       {
/*  2278: 2333 */         localThrowable28.printStackTrace(Jaguar.log);
/*  2279: 2334 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/*  2280: 2335 */         return localThrowable28.getClass().getName();
/*  2281:      */       }
/*  2282:      */     case 79: 
/*  2283:      */       try
/*  2284:      */       {
/*  2285: 2344 */         String str8 = param_ServerRequest.read_string();
/*  2286: 2345 */         localObject2 = paramBPWServicesBean
/*  2287: 2346 */           .activateBPWFI(
/*  2288: 2347 */           str8);
/*  2289:      */         
/*  2290: 2349 */         param_ServerRequest.write_value(localObject2, BPWFIInfo.class);
/*  2291:      */       }
/*  2292:      */       catch (Throwable localThrowable29)
/*  2293:      */       {
/*  2294: 2353 */         localThrowable29.printStackTrace(Jaguar.log);
/*  2295: 2354 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/*  2296: 2355 */         return localThrowable29.getClass().getName();
/*  2297:      */       }
/*  2298:      */     case 80: 
/*  2299:      */       try
/*  2300:      */       {
/*  2301: 2364 */         String str9 = param_ServerRequest.read_string();
/*  2302: 2365 */         localObject2 = paramBPWServicesBean
/*  2303: 2366 */           .getBPWFIInfo(
/*  2304: 2367 */           str9);
/*  2305:      */         
/*  2306: 2369 */         param_ServerRequest.write_value(localObject2, BPWFIInfo.class);
/*  2307:      */       }
/*  2308:      */       catch (Throwable localThrowable30)
/*  2309:      */       {
/*  2310: 2373 */         localThrowable30.printStackTrace(Jaguar.log);
/*  2311: 2374 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/*  2312: 2375 */         return localThrowable30.getClass().getName();
/*  2313:      */       }
/*  2314:      */     case 81: 
/*  2315:      */       try
/*  2316:      */       {
/*  2317:      */         String[] arrayOfString;
/*  2318: 2384 */         if (param_ServerRequest.isRMI()) {
/*  2319: 2384 */           arrayOfString = (String[])param_ServerRequest.read_value(new String[0].getClass());
/*  2320:      */         } else {
/*  2321: 2384 */           arrayOfString = StringSeqHelper.read(paramInputStream);
/*  2322:      */         }
/*  2323: 2385 */         localObject2 = 
/*  2324: 2386 */           paramBPWServicesBean.getBPWFIInfo(
/*  2325: 2387 */           arrayOfString);
/*  2326: 2389 */         if (param_ServerRequest.isRMI()) {
/*  2327: 2389 */           param_ServerRequest.write_value(localObject2, new BPWFIInfo[0].getClass());
/*  2328:      */         } else {
/*  2329: 2389 */           BPWFIInfoSeqHelper.write(paramOutputStream, (BPWFIInfo[])localObject2);
/*  2330:      */         }
/*  2331:      */       }
/*  2332:      */       catch (Throwable localThrowable31)
/*  2333:      */       {
/*  2334: 2393 */         localThrowable31.printStackTrace(Jaguar.log);
/*  2335: 2394 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/*  2336: 2395 */         return localThrowable31.getClass().getName();
/*  2337:      */       }
/*  2338:      */     case 82: 
/*  2339:      */       try
/*  2340:      */       {
/*  2341: 2404 */         String str10 = param_ServerRequest.read_string();
/*  2342: 2405 */         localObject2 = paramBPWServicesBean
/*  2343: 2406 */           .getBPWFIInfoByType(
/*  2344: 2407 */           str10);
/*  2345: 2409 */         if (param_ServerRequest.isRMI()) {
/*  2346: 2409 */           param_ServerRequest.write_value(localObject2, new BPWFIInfo[0].getClass());
/*  2347:      */         } else {
/*  2348: 2409 */           BPWFIInfoSeqHelper.write(paramOutputStream, (BPWFIInfo[])localObject2);
/*  2349:      */         }
/*  2350:      */       }
/*  2351:      */       catch (Throwable localThrowable32)
/*  2352:      */       {
/*  2353: 2413 */         localThrowable32.printStackTrace(Jaguar.log);
/*  2354: 2414 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/*  2355: 2415 */         return localThrowable32.getClass().getName();
/*  2356:      */       }
/*  2357:      */     case 83: 
/*  2358:      */       try
/*  2359:      */       {
/*  2360: 2424 */         String str11 = param_ServerRequest.read_string();
/*  2361: 2425 */         localObject2 = paramBPWServicesBean
/*  2362: 2426 */           .getBPWFIInfoByStatus(
/*  2363: 2427 */           str11);
/*  2364: 2429 */         if (param_ServerRequest.isRMI()) {
/*  2365: 2429 */           param_ServerRequest.write_value(localObject2, new BPWFIInfo[0].getClass());
/*  2366:      */         } else {
/*  2367: 2429 */           BPWFIInfoSeqHelper.write(paramOutputStream, (BPWFIInfo[])localObject2);
/*  2368:      */         }
/*  2369:      */       }
/*  2370:      */       catch (Throwable localThrowable33)
/*  2371:      */       {
/*  2372: 2433 */         localThrowable33.printStackTrace(Jaguar.log);
/*  2373: 2434 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/*  2374: 2435 */         return localThrowable33.getClass().getName();
/*  2375:      */       }
/*  2376:      */     case 84: 
/*  2377:      */       try
/*  2378:      */       {
/*  2379: 2444 */         String str12 = param_ServerRequest.read_string();
/*  2380: 2445 */         localObject2 = paramBPWServicesBean
/*  2381: 2446 */           .getBPWFIInfoByGroup(
/*  2382: 2447 */           str12);
/*  2383: 2449 */         if (param_ServerRequest.isRMI()) {
/*  2384: 2449 */           param_ServerRequest.write_value(localObject2, new BPWFIInfo[0].getClass());
/*  2385:      */         } else {
/*  2386: 2449 */           BPWFIInfoSeqHelper.write(paramOutputStream, (BPWFIInfo[])localObject2);
/*  2387:      */         }
/*  2388:      */       }
/*  2389:      */       catch (Throwable localThrowable34)
/*  2390:      */       {
/*  2391: 2453 */         localThrowable34.printStackTrace(Jaguar.log);
/*  2392: 2454 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/*  2393: 2455 */         return localThrowable34.getClass().getName();
/*  2394:      */       }
/*  2395:      */     case 85: 
/*  2396:      */       try
/*  2397:      */       {
/*  2398: 2464 */         String str13 = param_ServerRequest.read_string();
/*  2399: 2465 */         localObject2 = paramBPWServicesBean
/*  2400: 2466 */           .getBPWFIInfoByACHId(
/*  2401: 2467 */           str13);
/*  2402:      */         
/*  2403: 2469 */         param_ServerRequest.write_value(localObject2, BPWFIInfo.class);
/*  2404:      */       }
/*  2405:      */       catch (Throwable localThrowable35)
/*  2406:      */       {
/*  2407: 2473 */         localThrowable35.printStackTrace(Jaguar.log);
/*  2408: 2474 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/*  2409: 2475 */         return localThrowable35.getClass().getName();
/*  2410:      */       }
/*  2411:      */     case 86: 
/*  2412:      */       try
/*  2413:      */       {
/*  2414: 2484 */         String str14 = param_ServerRequest.read_string();
/*  2415: 2485 */         localObject2 = paramBPWServicesBean
/*  2416: 2486 */           .getBPWFIInfoByRTN(
/*  2417: 2487 */           str14);
/*  2418:      */         
/*  2419: 2489 */         param_ServerRequest.write_value(localObject2, BPWFIInfo.class);
/*  2420:      */       }
/*  2421:      */       catch (Throwable localThrowable36)
/*  2422:      */       {
/*  2423: 2493 */         localThrowable36.printStackTrace(Jaguar.log);
/*  2424: 2494 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/*  2425: 2495 */         return localThrowable36.getClass().getName();
/*  2426:      */       }
/*  2427:      */     case 87: 
/*  2428:      */       try
/*  2429:      */       {
/*  2430: 2504 */         RPPSFIInfo localRPPSFIInfo1 = (RPPSFIInfo)param_ServerRequest.read_value(RPPSFIInfo.class);
/*  2431: 2505 */         localObject2 = paramBPWServicesBean
/*  2432: 2506 */           .addRPPSFIInfo(
/*  2433: 2507 */           localRPPSFIInfo1);
/*  2434:      */         
/*  2435: 2509 */         param_ServerRequest.write_value(localObject2, RPPSFIInfo.class);
/*  2436:      */       }
/*  2437:      */       catch (Throwable localThrowable37)
/*  2438:      */       {
/*  2439: 2513 */         if ((localThrowable37 instanceof FFSException))
/*  2440:      */         {
/*  2441: 2515 */           if (UserException.ok(paramOutputStream)) {
/*  2442: 2517 */             if (param_ServerRequest.isRMI())
/*  2443:      */             {
/*  2444: 2519 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2445: 2520 */               param_ServerRequest.write_value((FFSException)localThrowable37, FFSException.class);
/*  2446:      */             }
/*  2447:      */             else
/*  2448:      */             {
/*  2449: 2524 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2450: 2525 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable37);
/*  2451:      */             }
/*  2452:      */           }
/*  2453: 2528 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable37);
/*  2454:      */         }
/*  2455: 2530 */         localThrowable37.printStackTrace(Jaguar.log);
/*  2456: 2531 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable37, true);
/*  2457: 2532 */         return localThrowable37.getClass().getName();
/*  2458:      */       }
/*  2459:      */     case 88: 
/*  2460:      */       try
/*  2461:      */       {
/*  2462: 2541 */         String str15 = param_ServerRequest.read_string();
/*  2463: 2542 */         localObject2 = paramBPWServicesBean
/*  2464: 2543 */           .getRPPSFIInfoByFIId(
/*  2465: 2544 */           str15);
/*  2466:      */         
/*  2467: 2546 */         param_ServerRequest.write_value(localObject2, RPPSFIInfo.class);
/*  2468:      */       }
/*  2469:      */       catch (Throwable localThrowable38)
/*  2470:      */       {
/*  2471: 2550 */         if ((localThrowable38 instanceof FFSException))
/*  2472:      */         {
/*  2473: 2552 */           if (UserException.ok(paramOutputStream)) {
/*  2474: 2554 */             if (param_ServerRequest.isRMI())
/*  2475:      */             {
/*  2476: 2556 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2477: 2557 */               param_ServerRequest.write_value((FFSException)localThrowable38, FFSException.class);
/*  2478:      */             }
/*  2479:      */             else
/*  2480:      */             {
/*  2481: 2561 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2482: 2562 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable38);
/*  2483:      */             }
/*  2484:      */           }
/*  2485: 2565 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable38);
/*  2486:      */         }
/*  2487: 2567 */         localThrowable38.printStackTrace(Jaguar.log);
/*  2488: 2568 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable38, true);
/*  2489: 2569 */         return localThrowable38.getClass().getName();
/*  2490:      */       }
/*  2491:      */     case 89: 
/*  2492:      */       try
/*  2493:      */       {
/*  2494: 2578 */         String str16 = param_ServerRequest.read_string();
/*  2495: 2579 */         localObject2 = paramBPWServicesBean
/*  2496: 2580 */           .getRPPSFIInfoByFIRPPSId(
/*  2497: 2581 */           str16);
/*  2498:      */         
/*  2499: 2583 */         param_ServerRequest.write_value(localObject2, RPPSFIInfo.class);
/*  2500:      */       }
/*  2501:      */       catch (Throwable localThrowable39)
/*  2502:      */       {
/*  2503: 2587 */         if ((localThrowable39 instanceof FFSException))
/*  2504:      */         {
/*  2505: 2589 */           if (UserException.ok(paramOutputStream)) {
/*  2506: 2591 */             if (param_ServerRequest.isRMI())
/*  2507:      */             {
/*  2508: 2593 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2509: 2594 */               param_ServerRequest.write_value((FFSException)localThrowable39, FFSException.class);
/*  2510:      */             }
/*  2511:      */             else
/*  2512:      */             {
/*  2513: 2598 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2514: 2599 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable39);
/*  2515:      */             }
/*  2516:      */           }
/*  2517: 2602 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable39);
/*  2518:      */         }
/*  2519: 2604 */         localThrowable39.printStackTrace(Jaguar.log);
/*  2520: 2605 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable39, true);
/*  2521: 2606 */         return localThrowable39.getClass().getName();
/*  2522:      */       }
/*  2523:      */     case 90: 
/*  2524:      */       try
/*  2525:      */       {
/*  2526: 2615 */         RPPSFIInfo localRPPSFIInfo2 = (RPPSFIInfo)param_ServerRequest.read_value(RPPSFIInfo.class);
/*  2527: 2616 */         localObject2 = paramBPWServicesBean
/*  2528: 2617 */           .canRPPSFIInfo(
/*  2529: 2618 */           localRPPSFIInfo2);
/*  2530:      */         
/*  2531: 2620 */         param_ServerRequest.write_value(localObject2, RPPSFIInfo.class);
/*  2532:      */       }
/*  2533:      */       catch (Throwable localThrowable40)
/*  2534:      */       {
/*  2535: 2624 */         if ((localThrowable40 instanceof FFSException))
/*  2536:      */         {
/*  2537: 2626 */           if (UserException.ok(paramOutputStream)) {
/*  2538: 2628 */             if (param_ServerRequest.isRMI())
/*  2539:      */             {
/*  2540: 2630 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2541: 2631 */               param_ServerRequest.write_value((FFSException)localThrowable40, FFSException.class);
/*  2542:      */             }
/*  2543:      */             else
/*  2544:      */             {
/*  2545: 2635 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2546: 2636 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable40);
/*  2547:      */             }
/*  2548:      */           }
/*  2549: 2639 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable40);
/*  2550:      */         }
/*  2551: 2641 */         localThrowable40.printStackTrace(Jaguar.log);
/*  2552: 2642 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable40, true);
/*  2553: 2643 */         return localThrowable40.getClass().getName();
/*  2554:      */       }
/*  2555:      */     case 91: 
/*  2556:      */       try
/*  2557:      */       {
/*  2558: 2652 */         RPPSFIInfo localRPPSFIInfo3 = (RPPSFIInfo)param_ServerRequest.read_value(RPPSFIInfo.class);
/*  2559: 2653 */         localObject2 = paramBPWServicesBean
/*  2560: 2654 */           .activateRPPSFI(
/*  2561: 2655 */           localRPPSFIInfo3);
/*  2562:      */         
/*  2563: 2657 */         param_ServerRequest.write_value(localObject2, RPPSFIInfo.class);
/*  2564:      */       }
/*  2565:      */       catch (Throwable localThrowable41)
/*  2566:      */       {
/*  2567: 2661 */         if ((localThrowable41 instanceof FFSException))
/*  2568:      */         {
/*  2569: 2663 */           if (UserException.ok(paramOutputStream)) {
/*  2570: 2665 */             if (param_ServerRequest.isRMI())
/*  2571:      */             {
/*  2572: 2667 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2573: 2668 */               param_ServerRequest.write_value((FFSException)localThrowable41, FFSException.class);
/*  2574:      */             }
/*  2575:      */             else
/*  2576:      */             {
/*  2577: 2672 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2578: 2673 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable41);
/*  2579:      */             }
/*  2580:      */           }
/*  2581: 2676 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable41);
/*  2582:      */         }
/*  2583: 2678 */         localThrowable41.printStackTrace(Jaguar.log);
/*  2584: 2679 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable41, true);
/*  2585: 2680 */         return localThrowable41.getClass().getName();
/*  2586:      */       }
/*  2587:      */     case 92: 
/*  2588:      */       try
/*  2589:      */       {
/*  2590: 2689 */         RPPSFIInfo localRPPSFIInfo4 = (RPPSFIInfo)param_ServerRequest.read_value(RPPSFIInfo.class);
/*  2591: 2690 */         localObject2 = paramBPWServicesBean
/*  2592: 2691 */           .modRPPSFIInfo(
/*  2593: 2692 */           localRPPSFIInfo4);
/*  2594:      */         
/*  2595: 2694 */         param_ServerRequest.write_value(localObject2, RPPSFIInfo.class);
/*  2596:      */       }
/*  2597:      */       catch (Throwable localThrowable42)
/*  2598:      */       {
/*  2599: 2698 */         if ((localThrowable42 instanceof FFSException))
/*  2600:      */         {
/*  2601: 2700 */           if (UserException.ok(paramOutputStream)) {
/*  2602: 2702 */             if (param_ServerRequest.isRMI())
/*  2603:      */             {
/*  2604: 2704 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2605: 2705 */               param_ServerRequest.write_value((FFSException)localThrowable42, FFSException.class);
/*  2606:      */             }
/*  2607:      */             else
/*  2608:      */             {
/*  2609: 2709 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2610: 2710 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable42);
/*  2611:      */             }
/*  2612:      */           }
/*  2613: 2713 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable42);
/*  2614:      */         }
/*  2615: 2715 */         localThrowable42.printStackTrace(Jaguar.log);
/*  2616: 2716 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable42, true);
/*  2617: 2717 */         return localThrowable42.getClass().getName();
/*  2618:      */       }
/*  2619:      */     case 93: 
/*  2620:      */       try
/*  2621:      */       {
/*  2622: 2726 */         String str17 = param_ServerRequest.read_string();
/*  2623:      */         
/*  2624: 2728 */         int i = paramInputStream.read_long();
/*  2625: 2729 */         int k = paramBPWServicesBean
/*  2626: 2730 */           .getSmartDate(
/*  2627: 2731 */           str17, 
/*  2628: 2732 */           i);
/*  2629:      */         
/*  2630: 2734 */         paramOutputStream.write_long(k);
/*  2631:      */       }
/*  2632:      */       catch (Throwable localThrowable43)
/*  2633:      */       {
/*  2634: 2738 */         localThrowable43.printStackTrace(Jaguar.log);
/*  2635: 2739 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable43, true);
/*  2636: 2740 */         return localThrowable43.getClass().getName();
/*  2637:      */       }
/*  2638:      */     case 94: 
/*  2639:      */       try
/*  2640:      */       {
/*  2641: 2749 */         String str18 = param_ServerRequest.read_string();
/*  2642:      */         
/*  2643: 2751 */         localObject3 = param_ServerRequest.read_string();
/*  2644:      */         
/*  2645: 2753 */         localObject4 = param_ServerRequest.read_string();
/*  2646:      */         
/*  2647: 2755 */         localObject5 = (HashMap)param_ServerRequest.read_value(HashMap.class);
/*  2648: 2756 */         paramBPWServicesBean
/*  2649: 2757 */           .processApprovalResult(
/*  2650: 2758 */           str18, 
/*  2651: 2759 */           (String)localObject3, 
/*  2652: 2760 */           (String)localObject4, 
/*  2653: 2761 */           (HashMap)localObject5);
/*  2654:      */       }
/*  2655:      */       catch (Throwable localThrowable44)
/*  2656:      */       {
/*  2657: 2766 */         if ((localThrowable44 instanceof FFSException))
/*  2658:      */         {
/*  2659: 2768 */           if (UserException.ok(paramOutputStream)) {
/*  2660: 2770 */             if (param_ServerRequest.isRMI())
/*  2661:      */             {
/*  2662: 2772 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2663: 2773 */               param_ServerRequest.write_value((FFSException)localThrowable44, FFSException.class);
/*  2664:      */             }
/*  2665:      */             else
/*  2666:      */             {
/*  2667: 2777 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2668: 2778 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable44);
/*  2669:      */             }
/*  2670:      */           }
/*  2671: 2781 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable44);
/*  2672:      */         }
/*  2673: 2783 */         localThrowable44.printStackTrace(Jaguar.log);
/*  2674: 2784 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable44, true);
/*  2675: 2785 */         return localThrowable44.getClass().getName();
/*  2676:      */       }
/*  2677:      */     case 95: 
/*  2678:      */       try
/*  2679:      */       {
/*  2680: 2794 */         CCCompanyInfo localCCCompanyInfo1 = (CCCompanyInfo)param_ServerRequest.read_value(CCCompanyInfo.class);
/*  2681: 2795 */         localObject3 = paramBPWServicesBean
/*  2682: 2796 */           .addCCCompany(
/*  2683: 2797 */           localCCCompanyInfo1);
/*  2684:      */         
/*  2685: 2799 */         param_ServerRequest.write_value(localObject3, CCCompanyInfo.class);
/*  2686:      */       }
/*  2687:      */       catch (Throwable localThrowable45)
/*  2688:      */       {
/*  2689: 2803 */         if ((localThrowable45 instanceof FFSException))
/*  2690:      */         {
/*  2691: 2805 */           if (UserException.ok(paramOutputStream)) {
/*  2692: 2807 */             if (param_ServerRequest.isRMI())
/*  2693:      */             {
/*  2694: 2809 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2695: 2810 */               param_ServerRequest.write_value((FFSException)localThrowable45, FFSException.class);
/*  2696:      */             }
/*  2697:      */             else
/*  2698:      */             {
/*  2699: 2814 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2700: 2815 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable45);
/*  2701:      */             }
/*  2702:      */           }
/*  2703: 2818 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable45);
/*  2704:      */         }
/*  2705: 2820 */         localThrowable45.printStackTrace(Jaguar.log);
/*  2706: 2821 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable45, true);
/*  2707: 2822 */         return localThrowable45.getClass().getName();
/*  2708:      */       }
/*  2709:      */     case 96: 
/*  2710:      */       try
/*  2711:      */       {
/*  2712: 2831 */         CCCompanyInfo localCCCompanyInfo2 = (CCCompanyInfo)param_ServerRequest.read_value(CCCompanyInfo.class);
/*  2713: 2832 */         localObject3 = paramBPWServicesBean
/*  2714: 2833 */           .cancelCCCompany(
/*  2715: 2834 */           localCCCompanyInfo2);
/*  2716:      */         
/*  2717: 2836 */         param_ServerRequest.write_value(localObject3, CCCompanyInfo.class);
/*  2718:      */       }
/*  2719:      */       catch (Throwable localThrowable46)
/*  2720:      */       {
/*  2721: 2840 */         if ((localThrowable46 instanceof FFSException))
/*  2722:      */         {
/*  2723: 2842 */           if (UserException.ok(paramOutputStream)) {
/*  2724: 2844 */             if (param_ServerRequest.isRMI())
/*  2725:      */             {
/*  2726: 2846 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2727: 2847 */               param_ServerRequest.write_value((FFSException)localThrowable46, FFSException.class);
/*  2728:      */             }
/*  2729:      */             else
/*  2730:      */             {
/*  2731: 2851 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2732: 2852 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable46);
/*  2733:      */             }
/*  2734:      */           }
/*  2735: 2855 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable46);
/*  2736:      */         }
/*  2737: 2857 */         localThrowable46.printStackTrace(Jaguar.log);
/*  2738: 2858 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable46, true);
/*  2739: 2859 */         return localThrowable46.getClass().getName();
/*  2740:      */       }
/*  2741:      */     case 97: 
/*  2742:      */       try
/*  2743:      */       {
/*  2744: 2868 */         CCCompanyInfo localCCCompanyInfo3 = (CCCompanyInfo)param_ServerRequest.read_value(CCCompanyInfo.class);
/*  2745: 2869 */         localObject3 = paramBPWServicesBean
/*  2746: 2870 */           .modCCCompany(
/*  2747: 2871 */           localCCCompanyInfo3);
/*  2748:      */         
/*  2749: 2873 */         param_ServerRequest.write_value(localObject3, CCCompanyInfo.class);
/*  2750:      */       }
/*  2751:      */       catch (Throwable localThrowable47)
/*  2752:      */       {
/*  2753: 2877 */         if ((localThrowable47 instanceof FFSException))
/*  2754:      */         {
/*  2755: 2879 */           if (UserException.ok(paramOutputStream)) {
/*  2756: 2881 */             if (param_ServerRequest.isRMI())
/*  2757:      */             {
/*  2758: 2883 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2759: 2884 */               param_ServerRequest.write_value((FFSException)localThrowable47, FFSException.class);
/*  2760:      */             }
/*  2761:      */             else
/*  2762:      */             {
/*  2763: 2888 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2764: 2889 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable47);
/*  2765:      */             }
/*  2766:      */           }
/*  2767: 2892 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable47);
/*  2768:      */         }
/*  2769: 2894 */         localThrowable47.printStackTrace(Jaguar.log);
/*  2770: 2895 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable47, true);
/*  2771: 2896 */         return localThrowable47.getClass().getName();
/*  2772:      */       }
/*  2773:      */     case 98: 
/*  2774:      */       try
/*  2775:      */       {
/*  2776: 2905 */         CCCompanyInfo localCCCompanyInfo4 = (CCCompanyInfo)param_ServerRequest.read_value(CCCompanyInfo.class);
/*  2777: 2906 */         localObject3 = paramBPWServicesBean
/*  2778: 2907 */           .getCCCompany(
/*  2779: 2908 */           localCCCompanyInfo4);
/*  2780:      */         
/*  2781: 2910 */         param_ServerRequest.write_value(localObject3, CCCompanyInfo.class);
/*  2782:      */       }
/*  2783:      */       catch (Throwable localThrowable48)
/*  2784:      */       {
/*  2785: 2914 */         if ((localThrowable48 instanceof FFSException))
/*  2786:      */         {
/*  2787: 2916 */           if (UserException.ok(paramOutputStream)) {
/*  2788: 2918 */             if (param_ServerRequest.isRMI())
/*  2789:      */             {
/*  2790: 2920 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2791: 2921 */               param_ServerRequest.write_value((FFSException)localThrowable48, FFSException.class);
/*  2792:      */             }
/*  2793:      */             else
/*  2794:      */             {
/*  2795: 2925 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2796: 2926 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable48);
/*  2797:      */             }
/*  2798:      */           }
/*  2799: 2929 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable48);
/*  2800:      */         }
/*  2801: 2931 */         localThrowable48.printStackTrace(Jaguar.log);
/*  2802: 2932 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable48, true);
/*  2803: 2933 */         return localThrowable48.getClass().getName();
/*  2804:      */       }
/*  2805:      */     case 99: 
/*  2806:      */       try
/*  2807:      */       {
/*  2808: 2942 */         CCCompanyInfoList localCCCompanyInfoList = (CCCompanyInfoList)param_ServerRequest.read_value(CCCompanyInfoList.class);
/*  2809: 2943 */         localObject3 = paramBPWServicesBean
/*  2810: 2944 */           .getCCCompanyList(
/*  2811: 2945 */           localCCCompanyInfoList);
/*  2812:      */         
/*  2813: 2947 */         param_ServerRequest.write_value(localObject3, CCCompanyInfoList.class);
/*  2814:      */       }
/*  2815:      */       catch (Throwable localThrowable49)
/*  2816:      */       {
/*  2817: 2951 */         if ((localThrowable49 instanceof FFSException))
/*  2818:      */         {
/*  2819: 2953 */           if (UserException.ok(paramOutputStream)) {
/*  2820: 2955 */             if (param_ServerRequest.isRMI())
/*  2821:      */             {
/*  2822: 2957 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2823: 2958 */               param_ServerRequest.write_value((FFSException)localThrowable49, FFSException.class);
/*  2824:      */             }
/*  2825:      */             else
/*  2826:      */             {
/*  2827: 2962 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2828: 2963 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable49);
/*  2829:      */             }
/*  2830:      */           }
/*  2831: 2966 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable49);
/*  2832:      */         }
/*  2833: 2968 */         localThrowable49.printStackTrace(Jaguar.log);
/*  2834: 2969 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable49, true);
/*  2835: 2970 */         return localThrowable49.getClass().getName();
/*  2836:      */       }
/*  2837:      */     case 100: 
/*  2838:      */       try
/*  2839:      */       {
/*  2840: 2979 */         String str19 = param_ServerRequest.read_string();
/*  2841:      */         
/*  2842: 2981 */         localObject3 = param_ServerRequest.read_string();
/*  2843: 2982 */         localObject4 = paramBPWServicesBean
/*  2844: 2983 */           .getNextCashConCutOff(
/*  2845: 2984 */           str19, 
/*  2846: 2985 */           (String)localObject3);
/*  2847:      */         
/*  2848: 2987 */         param_ServerRequest.write_value(localObject4, CutOffInfo.class);
/*  2849:      */       }
/*  2850:      */       catch (Throwable localThrowable50)
/*  2851:      */       {
/*  2852: 2991 */         if ((localThrowable50 instanceof FFSException))
/*  2853:      */         {
/*  2854: 2993 */           if (UserException.ok(paramOutputStream)) {
/*  2855: 2995 */             if (param_ServerRequest.isRMI())
/*  2856:      */             {
/*  2857: 2997 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2858: 2998 */               param_ServerRequest.write_value((FFSException)localThrowable50, FFSException.class);
/*  2859:      */             }
/*  2860:      */             else
/*  2861:      */             {
/*  2862: 3002 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2863: 3003 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable50);
/*  2864:      */             }
/*  2865:      */           }
/*  2866: 3006 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable50);
/*  2867:      */         }
/*  2868: 3008 */         localThrowable50.printStackTrace(Jaguar.log);
/*  2869: 3009 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable50, true);
/*  2870: 3010 */         return localThrowable50.getClass().getName();
/*  2871:      */       }
/*  2872:      */     default: 
/*  2873: 3016 */       return 
/*  2874: 3017 */         invoke2(
/*  2875: 3018 */         param_ServerRequest, 
/*  2876: 3019 */         paramInputStream, 
/*  2877: 3020 */         paramOutputStream, 
/*  2878: 3021 */         paramBPWServicesBean, 
/*  2879: 3022 */         paramInteger);
/*  2880:      */     }
/*  2881: 3026 */     return null;
/*  2882:      */   }
/*  2883:      */   
/*  2884:      */   private static String invoke2(_ServerRequest param_ServerRequest, InputStream paramInputStream, OutputStream paramOutputStream, BPWServicesBean paramBPWServicesBean, Integer paramInteger)
/*  2885:      */   {
/*  2886:      */     Object localObject1;
/*  2887:      */     Object localObject3;
/*  2888:      */     boolean bool;
/*  2889:      */     Object localObject2;
/*  2890: 3036 */     switch (paramInteger.intValue())
/*  2891:      */     {
/*  2892:      */     case 101: 
/*  2893:      */       try
/*  2894:      */       {
/*  2895: 3043 */         CCCompanyAcctInfo localCCCompanyAcctInfo1 = (CCCompanyAcctInfo)param_ServerRequest.read_value(CCCompanyAcctInfo.class);
/*  2896: 3044 */         localObject1 = paramBPWServicesBean
/*  2897: 3045 */           .addCCCompanyAcct(
/*  2898: 3046 */           localCCCompanyAcctInfo1);
/*  2899:      */         
/*  2900: 3048 */         param_ServerRequest.write_value(localObject1, CCCompanyAcctInfo.class);
/*  2901:      */       }
/*  2902:      */       catch (Throwable localThrowable1)
/*  2903:      */       {
/*  2904: 3052 */         if ((localThrowable1 instanceof FFSException))
/*  2905:      */         {
/*  2906: 3054 */           if (UserException.ok(paramOutputStream)) {
/*  2907: 3056 */             if (param_ServerRequest.isRMI())
/*  2908:      */             {
/*  2909: 3058 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2910: 3059 */               param_ServerRequest.write_value((FFSException)localThrowable1, FFSException.class);
/*  2911:      */             }
/*  2912:      */             else
/*  2913:      */             {
/*  2914: 3063 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2915: 3064 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable1);
/*  2916:      */             }
/*  2917:      */           }
/*  2918: 3067 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable1);
/*  2919:      */         }
/*  2920: 3069 */         localThrowable1.printStackTrace(Jaguar.log);
/*  2921: 3070 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/*  2922: 3071 */         return localThrowable1.getClass().getName();
/*  2923:      */       }
/*  2924:      */     case 102: 
/*  2925:      */       try
/*  2926:      */       {
/*  2927: 3080 */         CCCompanyAcctInfo localCCCompanyAcctInfo2 = (CCCompanyAcctInfo)param_ServerRequest.read_value(CCCompanyAcctInfo.class);
/*  2928: 3081 */         localObject1 = paramBPWServicesBean
/*  2929: 3082 */           .cancelCCCompanyAcct(
/*  2930: 3083 */           localCCCompanyAcctInfo2);
/*  2931:      */         
/*  2932: 3085 */         param_ServerRequest.write_value(localObject1, CCCompanyAcctInfo.class);
/*  2933:      */       }
/*  2934:      */       catch (Throwable localThrowable2)
/*  2935:      */       {
/*  2936: 3089 */         if ((localThrowable2 instanceof FFSException))
/*  2937:      */         {
/*  2938: 3091 */           if (UserException.ok(paramOutputStream)) {
/*  2939: 3093 */             if (param_ServerRequest.isRMI())
/*  2940:      */             {
/*  2941: 3095 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2942: 3096 */               param_ServerRequest.write_value((FFSException)localThrowable2, FFSException.class);
/*  2943:      */             }
/*  2944:      */             else
/*  2945:      */             {
/*  2946: 3100 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2947: 3101 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable2);
/*  2948:      */             }
/*  2949:      */           }
/*  2950: 3104 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable2);
/*  2951:      */         }
/*  2952: 3106 */         localThrowable2.printStackTrace(Jaguar.log);
/*  2953: 3107 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/*  2954: 3108 */         return localThrowable2.getClass().getName();
/*  2955:      */       }
/*  2956:      */     case 103: 
/*  2957:      */       try
/*  2958:      */       {
/*  2959: 3117 */         CCCompanyAcctInfo localCCCompanyAcctInfo3 = (CCCompanyAcctInfo)param_ServerRequest.read_value(CCCompanyAcctInfo.class);
/*  2960: 3118 */         localObject1 = paramBPWServicesBean
/*  2961: 3119 */           .modCCCompanyAcct(
/*  2962: 3120 */           localCCCompanyAcctInfo3);
/*  2963:      */         
/*  2964: 3122 */         param_ServerRequest.write_value(localObject1, CCCompanyAcctInfo.class);
/*  2965:      */       }
/*  2966:      */       catch (Throwable localThrowable3)
/*  2967:      */       {
/*  2968: 3126 */         if ((localThrowable3 instanceof FFSException))
/*  2969:      */         {
/*  2970: 3128 */           if (UserException.ok(paramOutputStream)) {
/*  2971: 3130 */             if (param_ServerRequest.isRMI())
/*  2972:      */             {
/*  2973: 3132 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  2974: 3133 */               param_ServerRequest.write_value((FFSException)localThrowable3, FFSException.class);
/*  2975:      */             }
/*  2976:      */             else
/*  2977:      */             {
/*  2978: 3137 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  2979: 3138 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable3);
/*  2980:      */             }
/*  2981:      */           }
/*  2982: 3141 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable3);
/*  2983:      */         }
/*  2984: 3143 */         localThrowable3.printStackTrace(Jaguar.log);
/*  2985: 3144 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/*  2986: 3145 */         return localThrowable3.getClass().getName();
/*  2987:      */       }
/*  2988:      */     case 104: 
/*  2989:      */       try
/*  2990:      */       {
/*  2991: 3154 */         CCCompanyAcctInfo localCCCompanyAcctInfo4 = (CCCompanyAcctInfo)param_ServerRequest.read_value(CCCompanyAcctInfo.class);
/*  2992: 3155 */         localObject1 = paramBPWServicesBean
/*  2993: 3156 */           .getCCCompanyAcct(
/*  2994: 3157 */           localCCCompanyAcctInfo4);
/*  2995:      */         
/*  2996: 3159 */         param_ServerRequest.write_value(localObject1, CCCompanyAcctInfo.class);
/*  2997:      */       }
/*  2998:      */       catch (Throwable localThrowable4)
/*  2999:      */       {
/*  3000: 3163 */         if ((localThrowable4 instanceof FFSException))
/*  3001:      */         {
/*  3002: 3165 */           if (UserException.ok(paramOutputStream)) {
/*  3003: 3167 */             if (param_ServerRequest.isRMI())
/*  3004:      */             {
/*  3005: 3169 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3006: 3170 */               param_ServerRequest.write_value((FFSException)localThrowable4, FFSException.class);
/*  3007:      */             }
/*  3008:      */             else
/*  3009:      */             {
/*  3010: 3174 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3011: 3175 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable4);
/*  3012:      */             }
/*  3013:      */           }
/*  3014: 3178 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable4);
/*  3015:      */         }
/*  3016: 3180 */         localThrowable4.printStackTrace(Jaguar.log);
/*  3017: 3181 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/*  3018: 3182 */         return localThrowable4.getClass().getName();
/*  3019:      */       }
/*  3020:      */     case 105: 
/*  3021:      */       try
/*  3022:      */       {
/*  3023: 3191 */         CCCompanyAcctInfoList localCCCompanyAcctInfoList = (CCCompanyAcctInfoList)param_ServerRequest.read_value(CCCompanyAcctInfoList.class);
/*  3024: 3192 */         localObject1 = paramBPWServicesBean
/*  3025: 3193 */           .getCCCompanyAcctList(
/*  3026: 3194 */           localCCCompanyAcctInfoList);
/*  3027:      */         
/*  3028: 3196 */         param_ServerRequest.write_value(localObject1, CCCompanyAcctInfoList.class);
/*  3029:      */       }
/*  3030:      */       catch (Throwable localThrowable5)
/*  3031:      */       {
/*  3032: 3200 */         if ((localThrowable5 instanceof FFSException))
/*  3033:      */         {
/*  3034: 3202 */           if (UserException.ok(paramOutputStream)) {
/*  3035: 3204 */             if (param_ServerRequest.isRMI())
/*  3036:      */             {
/*  3037: 3206 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3038: 3207 */               param_ServerRequest.write_value((FFSException)localThrowable5, FFSException.class);
/*  3039:      */             }
/*  3040:      */             else
/*  3041:      */             {
/*  3042: 3211 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3043: 3212 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable5);
/*  3044:      */             }
/*  3045:      */           }
/*  3046: 3215 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable5);
/*  3047:      */         }
/*  3048: 3217 */         localThrowable5.printStackTrace(Jaguar.log);
/*  3049: 3218 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/*  3050: 3219 */         return localThrowable5.getClass().getName();
/*  3051:      */       }
/*  3052:      */     case 106: 
/*  3053:      */       try
/*  3054:      */       {
/*  3055: 3228 */         CCCompanyCutOffInfo localCCCompanyCutOffInfo1 = (CCCompanyCutOffInfo)param_ServerRequest.read_value(CCCompanyCutOffInfo.class);
/*  3056: 3229 */         localObject1 = paramBPWServicesBean
/*  3057: 3230 */           .addCCCompanyCutOff(
/*  3058: 3231 */           localCCCompanyCutOffInfo1);
/*  3059:      */         
/*  3060: 3233 */         param_ServerRequest.write_value(localObject1, CCCompanyCutOffInfo.class);
/*  3061:      */       }
/*  3062:      */       catch (Throwable localThrowable6)
/*  3063:      */       {
/*  3064: 3237 */         if ((localThrowable6 instanceof FFSException))
/*  3065:      */         {
/*  3066: 3239 */           if (UserException.ok(paramOutputStream)) {
/*  3067: 3241 */             if (param_ServerRequest.isRMI())
/*  3068:      */             {
/*  3069: 3243 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3070: 3244 */               param_ServerRequest.write_value((FFSException)localThrowable6, FFSException.class);
/*  3071:      */             }
/*  3072:      */             else
/*  3073:      */             {
/*  3074: 3248 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3075: 3249 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable6);
/*  3076:      */             }
/*  3077:      */           }
/*  3078: 3252 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable6);
/*  3079:      */         }
/*  3080: 3254 */         localThrowable6.printStackTrace(Jaguar.log);
/*  3081: 3255 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/*  3082: 3256 */         return localThrowable6.getClass().getName();
/*  3083:      */       }
/*  3084:      */     case 107: 
/*  3085:      */       try
/*  3086:      */       {
/*  3087: 3265 */         CCCompanyCutOffInfo localCCCompanyCutOffInfo2 = (CCCompanyCutOffInfo)param_ServerRequest.read_value(CCCompanyCutOffInfo.class);
/*  3088: 3266 */         localObject1 = paramBPWServicesBean
/*  3089: 3267 */           .cancelCCCompanyCutOff(
/*  3090: 3268 */           localCCCompanyCutOffInfo2);
/*  3091:      */         
/*  3092: 3270 */         param_ServerRequest.write_value(localObject1, CCCompanyCutOffInfo.class);
/*  3093:      */       }
/*  3094:      */       catch (Throwable localThrowable7)
/*  3095:      */       {
/*  3096: 3274 */         if ((localThrowable7 instanceof FFSException))
/*  3097:      */         {
/*  3098: 3276 */           if (UserException.ok(paramOutputStream)) {
/*  3099: 3278 */             if (param_ServerRequest.isRMI())
/*  3100:      */             {
/*  3101: 3280 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3102: 3281 */               param_ServerRequest.write_value((FFSException)localThrowable7, FFSException.class);
/*  3103:      */             }
/*  3104:      */             else
/*  3105:      */             {
/*  3106: 3285 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3107: 3286 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable7);
/*  3108:      */             }
/*  3109:      */           }
/*  3110: 3289 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable7);
/*  3111:      */         }
/*  3112: 3291 */         localThrowable7.printStackTrace(Jaguar.log);
/*  3113: 3292 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/*  3114: 3293 */         return localThrowable7.getClass().getName();
/*  3115:      */       }
/*  3116:      */     case 108: 
/*  3117:      */       try
/*  3118:      */       {
/*  3119: 3302 */         CCCompanyCutOffInfo localCCCompanyCutOffInfo3 = (CCCompanyCutOffInfo)param_ServerRequest.read_value(CCCompanyCutOffInfo.class);
/*  3120: 3303 */         localObject1 = paramBPWServicesBean
/*  3121: 3304 */           .getCCCompanyCutOff(
/*  3122: 3305 */           localCCCompanyCutOffInfo3);
/*  3123:      */         
/*  3124: 3307 */         param_ServerRequest.write_value(localObject1, CCCompanyCutOffInfo.class);
/*  3125:      */       }
/*  3126:      */       catch (Throwable localThrowable8)
/*  3127:      */       {
/*  3128: 3311 */         if ((localThrowable8 instanceof FFSException))
/*  3129:      */         {
/*  3130: 3313 */           if (UserException.ok(paramOutputStream)) {
/*  3131: 3315 */             if (param_ServerRequest.isRMI())
/*  3132:      */             {
/*  3133: 3317 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3134: 3318 */               param_ServerRequest.write_value((FFSException)localThrowable8, FFSException.class);
/*  3135:      */             }
/*  3136:      */             else
/*  3137:      */             {
/*  3138: 3322 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3139: 3323 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable8);
/*  3140:      */             }
/*  3141:      */           }
/*  3142: 3326 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable8);
/*  3143:      */         }
/*  3144: 3328 */         localThrowable8.printStackTrace(Jaguar.log);
/*  3145: 3329 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/*  3146: 3330 */         return localThrowable8.getClass().getName();
/*  3147:      */       }
/*  3148:      */     case 109: 
/*  3149:      */       try
/*  3150:      */       {
/*  3151: 3339 */         CCCompanyCutOffInfoList localCCCompanyCutOffInfoList = (CCCompanyCutOffInfoList)param_ServerRequest.read_value(CCCompanyCutOffInfoList.class);
/*  3152: 3340 */         localObject1 = paramBPWServicesBean
/*  3153: 3341 */           .getCCCompanyCutOffList(
/*  3154: 3342 */           localCCCompanyCutOffInfoList);
/*  3155:      */         
/*  3156: 3344 */         param_ServerRequest.write_value(localObject1, CCCompanyCutOffInfoList.class);
/*  3157:      */       }
/*  3158:      */       catch (Throwable localThrowable9)
/*  3159:      */       {
/*  3160: 3348 */         if ((localThrowable9 instanceof FFSException))
/*  3161:      */         {
/*  3162: 3350 */           if (UserException.ok(paramOutputStream)) {
/*  3163: 3352 */             if (param_ServerRequest.isRMI())
/*  3164:      */             {
/*  3165: 3354 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3166: 3355 */               param_ServerRequest.write_value((FFSException)localThrowable9, FFSException.class);
/*  3167:      */             }
/*  3168:      */             else
/*  3169:      */             {
/*  3170: 3359 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3171: 3360 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable9);
/*  3172:      */             }
/*  3173:      */           }
/*  3174: 3363 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable9);
/*  3175:      */         }
/*  3176: 3365 */         localThrowable9.printStackTrace(Jaguar.log);
/*  3177: 3366 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/*  3178: 3367 */         return localThrowable9.getClass().getName();
/*  3179:      */       }
/*  3180:      */     case 110: 
/*  3181:      */       try
/*  3182:      */       {
/*  3183: 3376 */         CCLocationInfo localCCLocationInfo1 = (CCLocationInfo)param_ServerRequest.read_value(CCLocationInfo.class);
/*  3184: 3377 */         localObject1 = paramBPWServicesBean
/*  3185: 3378 */           .addCCLocation(
/*  3186: 3379 */           localCCLocationInfo1);
/*  3187:      */         
/*  3188: 3381 */         param_ServerRequest.write_value(localObject1, CCLocationInfo.class);
/*  3189:      */       }
/*  3190:      */       catch (Throwable localThrowable10)
/*  3191:      */       {
/*  3192: 3385 */         if ((localThrowable10 instanceof FFSException))
/*  3193:      */         {
/*  3194: 3387 */           if (UserException.ok(paramOutputStream)) {
/*  3195: 3389 */             if (param_ServerRequest.isRMI())
/*  3196:      */             {
/*  3197: 3391 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3198: 3392 */               param_ServerRequest.write_value((FFSException)localThrowable10, FFSException.class);
/*  3199:      */             }
/*  3200:      */             else
/*  3201:      */             {
/*  3202: 3396 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3203: 3397 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable10);
/*  3204:      */             }
/*  3205:      */           }
/*  3206: 3400 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable10);
/*  3207:      */         }
/*  3208: 3402 */         localThrowable10.printStackTrace(Jaguar.log);
/*  3209: 3403 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/*  3210: 3404 */         return localThrowable10.getClass().getName();
/*  3211:      */       }
/*  3212:      */     case 111: 
/*  3213:      */       try
/*  3214:      */       {
/*  3215: 3413 */         CCLocationInfo localCCLocationInfo2 = (CCLocationInfo)param_ServerRequest.read_value(CCLocationInfo.class);
/*  3216: 3414 */         localObject1 = paramBPWServicesBean
/*  3217: 3415 */           .cancelCCLocation(
/*  3218: 3416 */           localCCLocationInfo2);
/*  3219:      */         
/*  3220: 3418 */         param_ServerRequest.write_value(localObject1, CCLocationInfo.class);
/*  3221:      */       }
/*  3222:      */       catch (Throwable localThrowable11)
/*  3223:      */       {
/*  3224: 3422 */         if ((localThrowable11 instanceof FFSException))
/*  3225:      */         {
/*  3226: 3424 */           if (UserException.ok(paramOutputStream)) {
/*  3227: 3426 */             if (param_ServerRequest.isRMI())
/*  3228:      */             {
/*  3229: 3428 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3230: 3429 */               param_ServerRequest.write_value((FFSException)localThrowable11, FFSException.class);
/*  3231:      */             }
/*  3232:      */             else
/*  3233:      */             {
/*  3234: 3433 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3235: 3434 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable11);
/*  3236:      */             }
/*  3237:      */           }
/*  3238: 3437 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable11);
/*  3239:      */         }
/*  3240: 3439 */         localThrowable11.printStackTrace(Jaguar.log);
/*  3241: 3440 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/*  3242: 3441 */         return localThrowable11.getClass().getName();
/*  3243:      */       }
/*  3244:      */     case 112: 
/*  3245:      */       try
/*  3246:      */       {
/*  3247: 3450 */         CCLocationInfo localCCLocationInfo3 = (CCLocationInfo)param_ServerRequest.read_value(CCLocationInfo.class);
/*  3248: 3451 */         localObject1 = paramBPWServicesBean
/*  3249: 3452 */           .modCCLocation(
/*  3250: 3453 */           localCCLocationInfo3);
/*  3251:      */         
/*  3252: 3455 */         param_ServerRequest.write_value(localObject1, CCLocationInfo.class);
/*  3253:      */       }
/*  3254:      */       catch (Throwable localThrowable12)
/*  3255:      */       {
/*  3256: 3459 */         if ((localThrowable12 instanceof FFSException))
/*  3257:      */         {
/*  3258: 3461 */           if (UserException.ok(paramOutputStream)) {
/*  3259: 3463 */             if (param_ServerRequest.isRMI())
/*  3260:      */             {
/*  3261: 3465 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3262: 3466 */               param_ServerRequest.write_value((FFSException)localThrowable12, FFSException.class);
/*  3263:      */             }
/*  3264:      */             else
/*  3265:      */             {
/*  3266: 3470 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3267: 3471 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable12);
/*  3268:      */             }
/*  3269:      */           }
/*  3270: 3474 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable12);
/*  3271:      */         }
/*  3272: 3476 */         localThrowable12.printStackTrace(Jaguar.log);
/*  3273: 3477 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/*  3274: 3478 */         return localThrowable12.getClass().getName();
/*  3275:      */       }
/*  3276:      */     case 113: 
/*  3277:      */       try
/*  3278:      */       {
/*  3279: 3487 */         CCLocationInfo localCCLocationInfo4 = (CCLocationInfo)param_ServerRequest.read_value(CCLocationInfo.class);
/*  3280: 3488 */         localObject1 = paramBPWServicesBean
/*  3281: 3489 */           .getCCLocation(
/*  3282: 3490 */           localCCLocationInfo4);
/*  3283:      */         
/*  3284: 3492 */         param_ServerRequest.write_value(localObject1, CCLocationInfo.class);
/*  3285:      */       }
/*  3286:      */       catch (Throwable localThrowable13)
/*  3287:      */       {
/*  3288: 3496 */         if ((localThrowable13 instanceof FFSException))
/*  3289:      */         {
/*  3290: 3498 */           if (UserException.ok(paramOutputStream)) {
/*  3291: 3500 */             if (param_ServerRequest.isRMI())
/*  3292:      */             {
/*  3293: 3502 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3294: 3503 */               param_ServerRequest.write_value((FFSException)localThrowable13, FFSException.class);
/*  3295:      */             }
/*  3296:      */             else
/*  3297:      */             {
/*  3298: 3507 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3299: 3508 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable13);
/*  3300:      */             }
/*  3301:      */           }
/*  3302: 3511 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable13);
/*  3303:      */         }
/*  3304: 3513 */         localThrowable13.printStackTrace(Jaguar.log);
/*  3305: 3514 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/*  3306: 3515 */         return localThrowable13.getClass().getName();
/*  3307:      */       }
/*  3308:      */     case 114: 
/*  3309:      */       try
/*  3310:      */       {
/*  3311: 3524 */         CCLocationInfoList localCCLocationInfoList = (CCLocationInfoList)param_ServerRequest.read_value(CCLocationInfoList.class);
/*  3312: 3525 */         localObject1 = paramBPWServicesBean
/*  3313: 3526 */           .getCCLocationList(
/*  3314: 3527 */           localCCLocationInfoList);
/*  3315:      */         
/*  3316: 3529 */         param_ServerRequest.write_value(localObject1, CCLocationInfoList.class);
/*  3317:      */       }
/*  3318:      */       catch (Throwable localThrowable14)
/*  3319:      */       {
/*  3320: 3533 */         if ((localThrowable14 instanceof FFSException))
/*  3321:      */         {
/*  3322: 3535 */           if (UserException.ok(paramOutputStream)) {
/*  3323: 3537 */             if (param_ServerRequest.isRMI())
/*  3324:      */             {
/*  3325: 3539 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3326: 3540 */               param_ServerRequest.write_value((FFSException)localThrowable14, FFSException.class);
/*  3327:      */             }
/*  3328:      */             else
/*  3329:      */             {
/*  3330: 3544 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3331: 3545 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable14);
/*  3332:      */             }
/*  3333:      */           }
/*  3334: 3548 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable14);
/*  3335:      */         }
/*  3336: 3550 */         localThrowable14.printStackTrace(Jaguar.log);
/*  3337: 3551 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/*  3338: 3552 */         return localThrowable14.getClass().getName();
/*  3339:      */       }
/*  3340:      */     case 115: 
/*  3341:      */       try
/*  3342:      */       {
/*  3343: 3561 */         CCEntryInfo localCCEntryInfo1 = (CCEntryInfo)param_ServerRequest.read_value(CCEntryInfo.class);
/*  3344: 3562 */         localObject1 = paramBPWServicesBean
/*  3345: 3563 */           .addCCEntry(
/*  3346: 3564 */           localCCEntryInfo1);
/*  3347:      */         
/*  3348: 3566 */         param_ServerRequest.write_value(localObject1, CCEntryInfo.class);
/*  3349:      */       }
/*  3350:      */       catch (Throwable localThrowable15)
/*  3351:      */       {
/*  3352: 3570 */         if ((localThrowable15 instanceof FFSException))
/*  3353:      */         {
/*  3354: 3572 */           if (UserException.ok(paramOutputStream)) {
/*  3355: 3574 */             if (param_ServerRequest.isRMI())
/*  3356:      */             {
/*  3357: 3576 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3358: 3577 */               param_ServerRequest.write_value((FFSException)localThrowable15, FFSException.class);
/*  3359:      */             }
/*  3360:      */             else
/*  3361:      */             {
/*  3362: 3581 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3363: 3582 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable15);
/*  3364:      */             }
/*  3365:      */           }
/*  3366: 3585 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable15);
/*  3367:      */         }
/*  3368: 3587 */         localThrowable15.printStackTrace(Jaguar.log);
/*  3369: 3588 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/*  3370: 3589 */         return localThrowable15.getClass().getName();
/*  3371:      */       }
/*  3372:      */     case 116: 
/*  3373:      */       try
/*  3374:      */       {
/*  3375: 3598 */         CCEntryInfo localCCEntryInfo2 = (CCEntryInfo)param_ServerRequest.read_value(CCEntryInfo.class);
/*  3376: 3599 */         localObject1 = paramBPWServicesBean
/*  3377: 3600 */           .cancelCCEntry(
/*  3378: 3601 */           localCCEntryInfo2);
/*  3379:      */         
/*  3380: 3603 */         param_ServerRequest.write_value(localObject1, CCEntryInfo.class);
/*  3381:      */       }
/*  3382:      */       catch (Throwable localThrowable16)
/*  3383:      */       {
/*  3384: 3607 */         if ((localThrowable16 instanceof FFSException))
/*  3385:      */         {
/*  3386: 3609 */           if (UserException.ok(paramOutputStream)) {
/*  3387: 3611 */             if (param_ServerRequest.isRMI())
/*  3388:      */             {
/*  3389: 3613 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3390: 3614 */               param_ServerRequest.write_value((FFSException)localThrowable16, FFSException.class);
/*  3391:      */             }
/*  3392:      */             else
/*  3393:      */             {
/*  3394: 3618 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3395: 3619 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable16);
/*  3396:      */             }
/*  3397:      */           }
/*  3398: 3622 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable16);
/*  3399:      */         }
/*  3400: 3624 */         localThrowable16.printStackTrace(Jaguar.log);
/*  3401: 3625 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/*  3402: 3626 */         return localThrowable16.getClass().getName();
/*  3403:      */       }
/*  3404:      */     case 117: 
/*  3405:      */       try
/*  3406:      */       {
/*  3407: 3635 */         CCEntryInfo localCCEntryInfo3 = (CCEntryInfo)param_ServerRequest.read_value(CCEntryInfo.class);
/*  3408: 3636 */         localObject1 = paramBPWServicesBean
/*  3409: 3637 */           .modCCEntry(
/*  3410: 3638 */           localCCEntryInfo3);
/*  3411:      */         
/*  3412: 3640 */         param_ServerRequest.write_value(localObject1, CCEntryInfo.class);
/*  3413:      */       }
/*  3414:      */       catch (Throwable localThrowable17)
/*  3415:      */       {
/*  3416: 3644 */         if ((localThrowable17 instanceof FFSException))
/*  3417:      */         {
/*  3418: 3646 */           if (UserException.ok(paramOutputStream)) {
/*  3419: 3648 */             if (param_ServerRequest.isRMI())
/*  3420:      */             {
/*  3421: 3650 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3422: 3651 */               param_ServerRequest.write_value((FFSException)localThrowable17, FFSException.class);
/*  3423:      */             }
/*  3424:      */             else
/*  3425:      */             {
/*  3426: 3655 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3427: 3656 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable17);
/*  3428:      */             }
/*  3429:      */           }
/*  3430: 3659 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable17);
/*  3431:      */         }
/*  3432: 3661 */         localThrowable17.printStackTrace(Jaguar.log);
/*  3433: 3662 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/*  3434: 3663 */         return localThrowable17.getClass().getName();
/*  3435:      */       }
/*  3436:      */     case 118: 
/*  3437:      */       try
/*  3438:      */       {
/*  3439: 3672 */         CCEntryInfo localCCEntryInfo4 = (CCEntryInfo)param_ServerRequest.read_value(CCEntryInfo.class);
/*  3440: 3673 */         localObject1 = paramBPWServicesBean
/*  3441: 3674 */           .getCCEntry(
/*  3442: 3675 */           localCCEntryInfo4);
/*  3443:      */         
/*  3444: 3677 */         param_ServerRequest.write_value(localObject1, CCEntryInfo.class);
/*  3445:      */       }
/*  3446:      */       catch (Throwable localThrowable18)
/*  3447:      */       {
/*  3448: 3681 */         if ((localThrowable18 instanceof FFSException))
/*  3449:      */         {
/*  3450: 3683 */           if (UserException.ok(paramOutputStream)) {
/*  3451: 3685 */             if (param_ServerRequest.isRMI())
/*  3452:      */             {
/*  3453: 3687 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3454: 3688 */               param_ServerRequest.write_value((FFSException)localThrowable18, FFSException.class);
/*  3455:      */             }
/*  3456:      */             else
/*  3457:      */             {
/*  3458: 3692 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3459: 3693 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable18);
/*  3460:      */             }
/*  3461:      */           }
/*  3462: 3696 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable18);
/*  3463:      */         }
/*  3464: 3698 */         localThrowable18.printStackTrace(Jaguar.log);
/*  3465: 3699 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/*  3466: 3700 */         return localThrowable18.getClass().getName();
/*  3467:      */       }
/*  3468:      */     case 119: 
/*  3469:      */       try
/*  3470:      */       {
/*  3471: 3709 */         CCEntryHistInfo localCCEntryHistInfo = (CCEntryHistInfo)param_ServerRequest.read_value(CCEntryHistInfo.class);
/*  3472: 3710 */         localObject1 = paramBPWServicesBean
/*  3473: 3711 */           .getCCEntryHist(
/*  3474: 3712 */           localCCEntryHistInfo);
/*  3475:      */         
/*  3476: 3714 */         param_ServerRequest.write_value(localObject1, CCEntryHistInfo.class);
/*  3477:      */       }
/*  3478:      */       catch (Throwable localThrowable19)
/*  3479:      */       {
/*  3480: 3718 */         if ((localThrowable19 instanceof FFSException))
/*  3481:      */         {
/*  3482: 3720 */           if (UserException.ok(paramOutputStream)) {
/*  3483: 3722 */             if (param_ServerRequest.isRMI())
/*  3484:      */             {
/*  3485: 3724 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3486: 3725 */               param_ServerRequest.write_value((FFSException)localThrowable19, FFSException.class);
/*  3487:      */             }
/*  3488:      */             else
/*  3489:      */             {
/*  3490: 3729 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3491: 3730 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable19);
/*  3492:      */             }
/*  3493:      */           }
/*  3494: 3733 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable19);
/*  3495:      */         }
/*  3496: 3735 */         localThrowable19.printStackTrace(Jaguar.log);
/*  3497: 3736 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/*  3498: 3737 */         return localThrowable19.getClass().getName();
/*  3499:      */       }
/*  3500:      */     case 120: 
/*  3501:      */       try
/*  3502:      */       {
/*  3503: 3746 */         CCEntrySummaryInfoList localCCEntrySummaryInfoList = (CCEntrySummaryInfoList)param_ServerRequest.read_value(CCEntrySummaryInfoList.class);
/*  3504: 3747 */         localObject1 = paramBPWServicesBean
/*  3505: 3748 */           .getCCEntrySummaryList(
/*  3506: 3749 */           localCCEntrySummaryInfoList);
/*  3507:      */         
/*  3508: 3751 */         param_ServerRequest.write_value(localObject1, CCEntrySummaryInfoList.class);
/*  3509:      */       }
/*  3510:      */       catch (Throwable localThrowable20)
/*  3511:      */       {
/*  3512: 3755 */         if ((localThrowable20 instanceof FFSException))
/*  3513:      */         {
/*  3514: 3757 */           if (UserException.ok(paramOutputStream)) {
/*  3515: 3759 */             if (param_ServerRequest.isRMI())
/*  3516:      */             {
/*  3517: 3761 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  3518: 3762 */               param_ServerRequest.write_value((FFSException)localThrowable20, FFSException.class);
/*  3519:      */             }
/*  3520:      */             else
/*  3521:      */             {
/*  3522: 3766 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  3523: 3767 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable20);
/*  3524:      */             }
/*  3525:      */           }
/*  3526: 3770 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable20);
/*  3527:      */         }
/*  3528: 3772 */         localThrowable20.printStackTrace(Jaguar.log);
/*  3529: 3773 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/*  3530: 3774 */         return localThrowable20.getClass().getName();
/*  3531:      */       }
/*  3532:      */     case 121: 
/*  3533:      */       try
/*  3534:      */       {
/*  3535: 3783 */         TransferInfo localTransferInfo1 = (TransferInfo)param_ServerRequest.read_value(TransferInfo.class);
/*  3536: 3784 */         localObject1 = paramBPWServicesBean
/*  3537: 3785 */           .addTransfer(
/*  3538: 3786 */           localTransferInfo1);
/*  3539:      */         
/*  3540: 3788 */         param_ServerRequest.write_value(localObject1, TransferInfo.class);
/*  3541:      */       }
/*  3542:      */       catch (Throwable localThrowable21)
/*  3543:      */       {
/*  3544: 3792 */         localThrowable21.printStackTrace(Jaguar.log);
/*  3545: 3793 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/*  3546: 3794 */         return localThrowable21.getClass().getName();
/*  3547:      */       }
/*  3548:      */     case 122: 
/*  3549:      */       try
/*  3550:      */       {
/*  3551: 3803 */         TransferInfo localTransferInfo2 = (TransferInfo)param_ServerRequest.read_value(TransferInfo.class);
/*  3552: 3804 */         localObject1 = paramBPWServicesBean
/*  3553: 3805 */           .modTransfer(
/*  3554: 3806 */           localTransferInfo2);
/*  3555:      */         
/*  3556: 3808 */         param_ServerRequest.write_value(localObject1, TransferInfo.class);
/*  3557:      */       }
/*  3558:      */       catch (Throwable localThrowable22)
/*  3559:      */       {
/*  3560: 3812 */         localThrowable22.printStackTrace(Jaguar.log);
/*  3561: 3813 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/*  3562: 3814 */         return localThrowable22.getClass().getName();
/*  3563:      */       }
/*  3564:      */     case 123: 
/*  3565:      */       try
/*  3566:      */       {
/*  3567: 3823 */         TransferInfo localTransferInfo3 = (TransferInfo)param_ServerRequest.read_value(TransferInfo.class);
/*  3568: 3824 */         localObject1 = paramBPWServicesBean
/*  3569: 3825 */           .cancTransfer(
/*  3570: 3826 */           localTransferInfo3);
/*  3571:      */         
/*  3572: 3828 */         param_ServerRequest.write_value(localObject1, TransferInfo.class);
/*  3573:      */       }
/*  3574:      */       catch (Throwable localThrowable23)
/*  3575:      */       {
/*  3576: 3832 */         localThrowable23.printStackTrace(Jaguar.log);
/*  3577: 3833 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/*  3578: 3834 */         return localThrowable23.getClass().getName();
/*  3579:      */       }
/*  3580:      */     case 124: 
/*  3581:      */       try
/*  3582:      */       {
/*  3583: 3843 */         String str1 = param_ServerRequest.read_string();
/*  3584:      */         
/*  3585: 3845 */         localObject1 = param_ServerRequest.read_string();
/*  3586: 3846 */         localObject3 = paramBPWServicesBean
/*  3587: 3847 */           .getTransferBySrvrTId(
/*  3588: 3848 */           str1, 
/*  3589: 3849 */           (String)localObject1);
/*  3590:      */         
/*  3591: 3851 */         param_ServerRequest.write_value(localObject3, TransferInfo.class);
/*  3592:      */       }
/*  3593:      */       catch (Throwable localThrowable24)
/*  3594:      */       {
/*  3595: 3855 */         localThrowable24.printStackTrace(Jaguar.log);
/*  3596: 3856 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/*  3597: 3857 */         return localThrowable24.getClass().getName();
/*  3598:      */       }
/*  3599:      */     case 125: 
/*  3600:      */       try
/*  3601:      */       {
/*  3602: 3866 */         String str2 = param_ServerRequest.read_string();
/*  3603: 3867 */         localObject1 = paramBPWServicesBean
/*  3604: 3868 */           .getTransferBySrvrTId(
/*  3605: 3869 */           str2);
/*  3606:      */         
/*  3607: 3871 */         param_ServerRequest.write_value(localObject1, TransferInfo.class);
/*  3608:      */       }
/*  3609:      */       catch (Throwable localThrowable25)
/*  3610:      */       {
/*  3611: 3875 */         localThrowable25.printStackTrace(Jaguar.log);
/*  3612: 3876 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/*  3613: 3877 */         return localThrowable25.getClass().getName();
/*  3614:      */       }
/*  3615:      */     case 126: 
/*  3616:      */       try
/*  3617:      */       {
/*  3618: 3886 */         String str3 = param_ServerRequest.read_string();
/*  3619: 3887 */         localObject1 = paramBPWServicesBean
/*  3620: 3888 */           .getTransferByTrackingId(
/*  3621: 3889 */           str3);
/*  3622:      */         
/*  3623: 3891 */         param_ServerRequest.write_value(localObject1, TransferInfo.class);
/*  3624:      */       }
/*  3625:      */       catch (Throwable localThrowable26)
/*  3626:      */       {
/*  3627: 3895 */         localThrowable26.printStackTrace(Jaguar.log);
/*  3628: 3896 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/*  3629: 3897 */         return localThrowable26.getClass().getName();
/*  3630:      */       }
/*  3631:      */     case 127: 
/*  3632:      */       try
/*  3633:      */       {
/*  3634:      */         String[] arrayOfString1;
/*  3635: 3906 */         if (param_ServerRequest.isRMI()) {
/*  3636: 3906 */           arrayOfString1 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/*  3637:      */         } else {
/*  3638: 3906 */           arrayOfString1 = StringSeqHelper.read(paramInputStream);
/*  3639:      */         }
/*  3640: 3907 */         localObject1 = 
/*  3641: 3908 */           paramBPWServicesBean.getTransfersBySrvrTId(
/*  3642: 3909 */           arrayOfString1);
/*  3643: 3911 */         if (param_ServerRequest.isRMI()) {
/*  3644: 3911 */           param_ServerRequest.write_value(localObject1, new TransferInfo[0].getClass());
/*  3645:      */         } else {
/*  3646: 3911 */           TransferInfoSeqHelper.write(paramOutputStream, (TransferInfo[])localObject1);
/*  3647:      */         }
/*  3648:      */       }
/*  3649:      */       catch (Throwable localThrowable27)
/*  3650:      */       {
/*  3651: 3915 */         localThrowable27.printStackTrace(Jaguar.log);
/*  3652: 3916 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/*  3653: 3917 */         return localThrowable27.getClass().getName();
/*  3654:      */       }
/*  3655:      */     case 128: 
/*  3656:      */       try
/*  3657:      */       {
/*  3658:      */         String[] arrayOfString2;
/*  3659: 3926 */         if (param_ServerRequest.isRMI()) {
/*  3660: 3926 */           arrayOfString2 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/*  3661:      */         } else {
/*  3662: 3926 */           arrayOfString2 = StringSeqHelper.read(paramInputStream);
/*  3663:      */         }
/*  3664: 3928 */         bool = paramInputStream.read_boolean();
/*  3665: 3929 */         localObject3 = paramBPWServicesBean
/*  3666: 3930 */           .getTransfersByRecSrvrTId(
/*  3667: 3931 */           arrayOfString2, 
/*  3668: 3932 */           bool);
/*  3669: 3934 */         if (param_ServerRequest.isRMI()) {
/*  3670: 3934 */           param_ServerRequest.write_value(localObject3, new TransferInfo[0].getClass());
/*  3671:      */         } else {
/*  3672: 3934 */           TransferInfoSeqHelper.write(paramOutputStream, (TransferInfo[])localObject3);
/*  3673:      */         }
/*  3674:      */       }
/*  3675:      */       catch (Throwable localThrowable28)
/*  3676:      */       {
/*  3677: 3938 */         localThrowable28.printStackTrace(Jaguar.log);
/*  3678: 3939 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/*  3679: 3940 */         return localThrowable28.getClass().getName();
/*  3680:      */       }
/*  3681:      */     case 129: 
/*  3682:      */       try
/*  3683:      */       {
/*  3684: 3949 */         String str4 = param_ServerRequest.read_string();
/*  3685:      */         
/*  3686: 3951 */         bool = paramInputStream.read_boolean();
/*  3687: 3952 */         localObject3 = paramBPWServicesBean
/*  3688: 3953 */           .getTransfersByRecSrvrTId(
/*  3689: 3954 */           str4, 
/*  3690: 3955 */           bool);
/*  3691: 3957 */         if (param_ServerRequest.isRMI()) {
/*  3692: 3957 */           param_ServerRequest.write_value(localObject3, new TransferInfo[0].getClass());
/*  3693:      */         } else {
/*  3694: 3957 */           TransferInfoSeqHelper.write(paramOutputStream, (TransferInfo[])localObject3);
/*  3695:      */         }
/*  3696:      */       }
/*  3697:      */       catch (Throwable localThrowable29)
/*  3698:      */       {
/*  3699: 3961 */         localThrowable29.printStackTrace(Jaguar.log);
/*  3700: 3962 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/*  3701: 3963 */         return localThrowable29.getClass().getName();
/*  3702:      */       }
/*  3703:      */     case 130: 
/*  3704:      */       try
/*  3705:      */       {
/*  3706:      */         String[] arrayOfString3;
/*  3707: 3972 */         if (param_ServerRequest.isRMI()) {
/*  3708: 3972 */           arrayOfString3 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/*  3709:      */         } else {
/*  3710: 3972 */           arrayOfString3 = StringSeqHelper.read(paramInputStream);
/*  3711:      */         }
/*  3712: 3973 */         localObject2 = 
/*  3713: 3974 */           paramBPWServicesBean.getTransfersByTrackingId(
/*  3714: 3975 */           arrayOfString3);
/*  3715: 3977 */         if (param_ServerRequest.isRMI()) {
/*  3716: 3977 */           param_ServerRequest.write_value(localObject2, new TransferInfo[0].getClass());
/*  3717:      */         } else {
/*  3718: 3977 */           TransferInfoSeqHelper.write(paramOutputStream, (TransferInfo[])localObject2);
/*  3719:      */         }
/*  3720:      */       }
/*  3721:      */       catch (Throwable localThrowable30)
/*  3722:      */       {
/*  3723: 3981 */         localThrowable30.printStackTrace(Jaguar.log);
/*  3724: 3982 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/*  3725: 3983 */         return localThrowable30.getClass().getName();
/*  3726:      */       }
/*  3727:      */     case 131: 
/*  3728:      */       try
/*  3729:      */       {
/*  3730:      */         BPWHist localBPWHist1;
/*  3731: 3992 */         if (param_ServerRequest.isRMI()) {
/*  3732: 3992 */           localBPWHist1 = (BPWHist)param_ServerRequest.read_value(BPWHist.class);
/*  3733:      */         } else {
/*  3734: 3992 */           localBPWHist1 = BPWHistHelper.read(paramInputStream);
/*  3735:      */         }
/*  3736: 3993 */         localObject2 = 
/*  3737: 3994 */           paramBPWServicesBean.getTransferHistory(
/*  3738: 3995 */           localBPWHist1);
/*  3739: 3997 */         if (param_ServerRequest.isRMI()) {
/*  3740: 3997 */           param_ServerRequest.write_value(localObject2, BPWHist.class);
/*  3741:      */         } else {
/*  3742: 3997 */           BPWHistHelper.write(paramOutputStream, (BPWHist)localObject2);
/*  3743:      */         }
/*  3744:      */       }
/*  3745:      */       catch (Throwable localThrowable31)
/*  3746:      */       {
/*  3747: 4001 */         localThrowable31.printStackTrace(Jaguar.log);
/*  3748: 4002 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/*  3749: 4003 */         return localThrowable31.getClass().getName();
/*  3750:      */       }
/*  3751:      */     case 132: 
/*  3752:      */       try
/*  3753:      */       {
/*  3754:      */         TransferInfo[] arrayOfTransferInfo;
/*  3755: 4012 */         if (param_ServerRequest.isRMI()) {
/*  3756: 4012 */           arrayOfTransferInfo = (TransferInfo[])param_ServerRequest.read_value(new TransferInfo[0].getClass());
/*  3757:      */         } else {
/*  3758: 4012 */           arrayOfTransferInfo = TransferInfoSeqHelper.read(paramInputStream);
/*  3759:      */         }
/*  3760: 4013 */         localObject2 = 
/*  3761: 4014 */           paramBPWServicesBean.addTransfers(
/*  3762: 4015 */           arrayOfTransferInfo);
/*  3763: 4017 */         if (param_ServerRequest.isRMI()) {
/*  3764: 4017 */           param_ServerRequest.write_value(localObject2, new TransferInfo[0].getClass());
/*  3765:      */         } else {
/*  3766: 4017 */           TransferInfoSeqHelper.write(paramOutputStream, (TransferInfo[])localObject2);
/*  3767:      */         }
/*  3768:      */       }
/*  3769:      */       catch (Throwable localThrowable32)
/*  3770:      */       {
/*  3771: 4021 */         localThrowable32.printStackTrace(Jaguar.log);
/*  3772: 4022 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/*  3773: 4023 */         return localThrowable32.getClass().getName();
/*  3774:      */       }
/*  3775:      */     case 133: 
/*  3776:      */       try
/*  3777:      */       {
/*  3778: 4032 */         RecTransferInfo localRecTransferInfo1 = (RecTransferInfo)param_ServerRequest.read_value(RecTransferInfo.class);
/*  3779: 4033 */         localObject2 = paramBPWServicesBean
/*  3780: 4034 */           .addRecTransfer(
/*  3781: 4035 */           localRecTransferInfo1);
/*  3782:      */         
/*  3783: 4037 */         param_ServerRequest.write_value(localObject2, RecTransferInfo.class);
/*  3784:      */       }
/*  3785:      */       catch (Throwable localThrowable33)
/*  3786:      */       {
/*  3787: 4041 */         localThrowable33.printStackTrace(Jaguar.log);
/*  3788: 4042 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/*  3789: 4043 */         return localThrowable33.getClass().getName();
/*  3790:      */       }
/*  3791:      */     case 134: 
/*  3792:      */       try
/*  3793:      */       {
/*  3794: 4052 */         RecTransferInfo localRecTransferInfo2 = (RecTransferInfo)param_ServerRequest.read_value(RecTransferInfo.class);
/*  3795: 4053 */         localObject2 = paramBPWServicesBean
/*  3796: 4054 */           .modRecTransfer(
/*  3797: 4055 */           localRecTransferInfo2);
/*  3798:      */         
/*  3799: 4057 */         param_ServerRequest.write_value(localObject2, RecTransferInfo.class);
/*  3800:      */       }
/*  3801:      */       catch (Throwable localThrowable34)
/*  3802:      */       {
/*  3803: 4061 */         localThrowable34.printStackTrace(Jaguar.log);
/*  3804: 4062 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/*  3805: 4063 */         return localThrowable34.getClass().getName();
/*  3806:      */       }
/*  3807:      */     case 135: 
/*  3808:      */       try
/*  3809:      */       {
/*  3810: 4072 */         RecTransferInfo localRecTransferInfo3 = (RecTransferInfo)param_ServerRequest.read_value(RecTransferInfo.class);
/*  3811: 4073 */         localObject2 = paramBPWServicesBean
/*  3812: 4074 */           .cancRecTransfer(
/*  3813: 4075 */           localRecTransferInfo3);
/*  3814:      */         
/*  3815: 4077 */         param_ServerRequest.write_value(localObject2, RecTransferInfo.class);
/*  3816:      */       }
/*  3817:      */       catch (Throwable localThrowable35)
/*  3818:      */       {
/*  3819: 4081 */         localThrowable35.printStackTrace(Jaguar.log);
/*  3820: 4082 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/*  3821: 4083 */         return localThrowable35.getClass().getName();
/*  3822:      */       }
/*  3823:      */     case 136: 
/*  3824:      */       try
/*  3825:      */       {
/*  3826: 4092 */         String str5 = param_ServerRequest.read_string();
/*  3827:      */         
/*  3828: 4094 */         localObject2 = param_ServerRequest.read_string();
/*  3829: 4095 */         localObject3 = paramBPWServicesBean
/*  3830: 4096 */           .getRecTransferBySrvrTId(
/*  3831: 4097 */           str5, 
/*  3832: 4098 */           (String)localObject2);
/*  3833:      */         
/*  3834: 4100 */         param_ServerRequest.write_value(localObject3, RecTransferInfo.class);
/*  3835:      */       }
/*  3836:      */       catch (Throwable localThrowable36)
/*  3837:      */       {
/*  3838: 4104 */         localThrowable36.printStackTrace(Jaguar.log);
/*  3839: 4105 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/*  3840: 4106 */         return localThrowable36.getClass().getName();
/*  3841:      */       }
/*  3842:      */     case 137: 
/*  3843:      */       try
/*  3844:      */       {
/*  3845: 4115 */         String str6 = param_ServerRequest.read_string();
/*  3846: 4116 */         localObject2 = paramBPWServicesBean
/*  3847: 4117 */           .getRecTransferBySrvrTId(
/*  3848: 4118 */           str6);
/*  3849:      */         
/*  3850: 4120 */         param_ServerRequest.write_value(localObject2, RecTransferInfo.class);
/*  3851:      */       }
/*  3852:      */       catch (Throwable localThrowable37)
/*  3853:      */       {
/*  3854: 4124 */         localThrowable37.printStackTrace(Jaguar.log);
/*  3855: 4125 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable37, true);
/*  3856: 4126 */         return localThrowable37.getClass().getName();
/*  3857:      */       }
/*  3858:      */     case 138: 
/*  3859:      */       try
/*  3860:      */       {
/*  3861: 4135 */         String str7 = param_ServerRequest.read_string();
/*  3862: 4136 */         localObject2 = paramBPWServicesBean
/*  3863: 4137 */           .getRecTransferByTrackingId(
/*  3864: 4138 */           str7);
/*  3865:      */         
/*  3866: 4140 */         param_ServerRequest.write_value(localObject2, RecTransferInfo.class);
/*  3867:      */       }
/*  3868:      */       catch (Throwable localThrowable38)
/*  3869:      */       {
/*  3870: 4144 */         localThrowable38.printStackTrace(Jaguar.log);
/*  3871: 4145 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable38, true);
/*  3872: 4146 */         return localThrowable38.getClass().getName();
/*  3873:      */       }
/*  3874:      */     case 139: 
/*  3875:      */       try
/*  3876:      */       {
/*  3877:      */         String[] arrayOfString4;
/*  3878: 4155 */         if (param_ServerRequest.isRMI()) {
/*  3879: 4155 */           arrayOfString4 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/*  3880:      */         } else {
/*  3881: 4155 */           arrayOfString4 = StringSeqHelper.read(paramInputStream);
/*  3882:      */         }
/*  3883: 4156 */         localObject2 = 
/*  3884: 4157 */           paramBPWServicesBean.getRecTransfersBySrvrTId(
/*  3885: 4158 */           arrayOfString4);
/*  3886: 4160 */         if (param_ServerRequest.isRMI()) {
/*  3887: 4160 */           param_ServerRequest.write_value(localObject2, new RecTransferInfo[0].getClass());
/*  3888:      */         } else {
/*  3889: 4160 */           RecTransferInfoSeqHelper.write(paramOutputStream, (RecTransferInfo[])localObject2);
/*  3890:      */         }
/*  3891:      */       }
/*  3892:      */       catch (Throwable localThrowable39)
/*  3893:      */       {
/*  3894: 4164 */         localThrowable39.printStackTrace(Jaguar.log);
/*  3895: 4165 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable39, true);
/*  3896: 4166 */         return localThrowable39.getClass().getName();
/*  3897:      */       }
/*  3898:      */     case 140: 
/*  3899:      */       try
/*  3900:      */       {
/*  3901:      */         BPWHist localBPWHist2;
/*  3902: 4175 */         if (param_ServerRequest.isRMI()) {
/*  3903: 4175 */           localBPWHist2 = (BPWHist)param_ServerRequest.read_value(BPWHist.class);
/*  3904:      */         } else {
/*  3905: 4175 */           localBPWHist2 = BPWHistHelper.read(paramInputStream);
/*  3906:      */         }
/*  3907: 4176 */         localObject2 = 
/*  3908: 4177 */           paramBPWServicesBean.getRecTransfers(
/*  3909: 4178 */           localBPWHist2);
/*  3910: 4180 */         if (param_ServerRequest.isRMI()) {
/*  3911: 4180 */           param_ServerRequest.write_value(localObject2, BPWHist.class);
/*  3912:      */         } else {
/*  3913: 4180 */           BPWHistHelper.write(paramOutputStream, (BPWHist)localObject2);
/*  3914:      */         }
/*  3915:      */       }
/*  3916:      */       catch (Throwable localThrowable40)
/*  3917:      */       {
/*  3918: 4184 */         localThrowable40.printStackTrace(Jaguar.log);
/*  3919: 4185 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable40, true);
/*  3920: 4186 */         return localThrowable40.getClass().getName();
/*  3921:      */       }
/*  3922:      */     case 141: 
/*  3923:      */       try
/*  3924:      */       {
/*  3925:      */         String[] arrayOfString5;
/*  3926: 4195 */         if (param_ServerRequest.isRMI()) {
/*  3927: 4195 */           arrayOfString5 = (String[])param_ServerRequest.read_value(new String[0].getClass());
/*  3928:      */         } else {
/*  3929: 4195 */           arrayOfString5 = StringSeqHelper.read(paramInputStream);
/*  3930:      */         }
/*  3931: 4196 */         localObject2 = 
/*  3932: 4197 */           paramBPWServicesBean.getRecTransfersByTrackingId(
/*  3933: 4198 */           arrayOfString5);
/*  3934: 4200 */         if (param_ServerRequest.isRMI()) {
/*  3935: 4200 */           param_ServerRequest.write_value(localObject2, new RecTransferInfo[0].getClass());
/*  3936:      */         } else {
/*  3937: 4200 */           RecTransferInfoSeqHelper.write(paramOutputStream, (RecTransferInfo[])localObject2);
/*  3938:      */         }
/*  3939:      */       }
/*  3940:      */       catch (Throwable localThrowable41)
/*  3941:      */       {
/*  3942: 4204 */         localThrowable41.printStackTrace(Jaguar.log);
/*  3943: 4205 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable41, true);
/*  3944: 4206 */         return localThrowable41.getClass().getName();
/*  3945:      */       }
/*  3946:      */     case 142: 
/*  3947:      */       try
/*  3948:      */       {
/*  3949:      */         BPWHist localBPWHist3;
/*  3950: 4215 */         if (param_ServerRequest.isRMI()) {
/*  3951: 4215 */           localBPWHist3 = (BPWHist)param_ServerRequest.read_value(BPWHist.class);
/*  3952:      */         } else {
/*  3953: 4215 */           localBPWHist3 = BPWHistHelper.read(paramInputStream);
/*  3954:      */         }
/*  3955: 4216 */         localObject2 = 
/*  3956: 4217 */           paramBPWServicesBean.getRecTransferHistory(
/*  3957: 4218 */           localBPWHist3);
/*  3958: 4220 */         if (param_ServerRequest.isRMI()) {
/*  3959: 4220 */           param_ServerRequest.write_value(localObject2, BPWHist.class);
/*  3960:      */         } else {
/*  3961: 4220 */           BPWHistHelper.write(paramOutputStream, (BPWHist)localObject2);
/*  3962:      */         }
/*  3963:      */       }
/*  3964:      */       catch (Throwable localThrowable42)
/*  3965:      */       {
/*  3966: 4224 */         localThrowable42.printStackTrace(Jaguar.log);
/*  3967: 4225 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable42, true);
/*  3968: 4226 */         return localThrowable42.getClass().getName();
/*  3969:      */       }
/*  3970:      */     case 143: 
/*  3971:      */       try
/*  3972:      */       {
/*  3973: 4235 */         ExtTransferCompanyInfo localExtTransferCompanyInfo1 = (ExtTransferCompanyInfo)param_ServerRequest.read_value(ExtTransferCompanyInfo.class);
/*  3974: 4236 */         localObject2 = paramBPWServicesBean
/*  3975: 4237 */           .addExtTransferCompany(
/*  3976: 4238 */           localExtTransferCompanyInfo1);
/*  3977:      */         
/*  3978: 4240 */         param_ServerRequest.write_value(localObject2, ExtTransferCompanyInfo.class);
/*  3979:      */       }
/*  3980:      */       catch (Throwable localThrowable43)
/*  3981:      */       {
/*  3982: 4244 */         localThrowable43.printStackTrace(Jaguar.log);
/*  3983: 4245 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable43, true);
/*  3984: 4246 */         return localThrowable43.getClass().getName();
/*  3985:      */       }
/*  3986:      */     case 144: 
/*  3987:      */       try
/*  3988:      */       {
/*  3989: 4255 */         ExtTransferCompanyInfo localExtTransferCompanyInfo2 = (ExtTransferCompanyInfo)param_ServerRequest.read_value(ExtTransferCompanyInfo.class);
/*  3990: 4256 */         localObject2 = paramBPWServicesBean
/*  3991: 4257 */           .canExtTransferCompany(
/*  3992: 4258 */           localExtTransferCompanyInfo2);
/*  3993:      */         
/*  3994: 4260 */         param_ServerRequest.write_value(localObject2, ExtTransferCompanyInfo.class);
/*  3995:      */       }
/*  3996:      */       catch (Throwable localThrowable44)
/*  3997:      */       {
/*  3998: 4264 */         localThrowable44.printStackTrace(Jaguar.log);
/*  3999: 4265 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable44, true);
/*  4000: 4266 */         return localThrowable44.getClass().getName();
/*  4001:      */       }
/*  4002:      */     case 145: 
/*  4003:      */       try
/*  4004:      */       {
/*  4005: 4275 */         ExtTransferCompanyInfo localExtTransferCompanyInfo3 = (ExtTransferCompanyInfo)param_ServerRequest.read_value(ExtTransferCompanyInfo.class);
/*  4006: 4276 */         localObject2 = paramBPWServicesBean
/*  4007: 4277 */           .modExtTransferCompany(
/*  4008: 4278 */           localExtTransferCompanyInfo3);
/*  4009:      */         
/*  4010: 4280 */         param_ServerRequest.write_value(localObject2, ExtTransferCompanyInfo.class);
/*  4011:      */       }
/*  4012:      */       catch (Throwable localThrowable45)
/*  4013:      */       {
/*  4014: 4284 */         localThrowable45.printStackTrace(Jaguar.log);
/*  4015: 4285 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable45, true);
/*  4016: 4286 */         return localThrowable45.getClass().getName();
/*  4017:      */       }
/*  4018:      */     case 146: 
/*  4019:      */       try
/*  4020:      */       {
/*  4021: 4295 */         ExtTransferCompanyInfo localExtTransferCompanyInfo4 = (ExtTransferCompanyInfo)param_ServerRequest.read_value(ExtTransferCompanyInfo.class);
/*  4022: 4296 */         localObject2 = paramBPWServicesBean
/*  4023: 4297 */           .getExtTransferCompany(
/*  4024: 4298 */           localExtTransferCompanyInfo4);
/*  4025:      */         
/*  4026: 4300 */         param_ServerRequest.write_value(localObject2, ExtTransferCompanyInfo.class);
/*  4027:      */       }
/*  4028:      */       catch (Throwable localThrowable46)
/*  4029:      */       {
/*  4030: 4304 */         localThrowable46.printStackTrace(Jaguar.log);
/*  4031: 4305 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable46, true);
/*  4032: 4306 */         return localThrowable46.getClass().getName();
/*  4033:      */       }
/*  4034:      */     case 147: 
/*  4035:      */       try
/*  4036:      */       {
/*  4037: 4315 */         ExtTransferCompanyList localExtTransferCompanyList = (ExtTransferCompanyList)param_ServerRequest.read_value(ExtTransferCompanyList.class);
/*  4038: 4316 */         localObject2 = paramBPWServicesBean
/*  4039: 4317 */           .getExtTransferCompanyList(
/*  4040: 4318 */           localExtTransferCompanyList);
/*  4041:      */         
/*  4042: 4320 */         param_ServerRequest.write_value(localObject2, ExtTransferCompanyList.class);
/*  4043:      */       }
/*  4044:      */       catch (Throwable localThrowable47)
/*  4045:      */       {
/*  4046: 4324 */         localThrowable47.printStackTrace(Jaguar.log);
/*  4047: 4325 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable47, true);
/*  4048: 4326 */         return localThrowable47.getClass().getName();
/*  4049:      */       }
/*  4050:      */     case 148: 
/*  4051:      */       try
/*  4052:      */       {
/*  4053: 4335 */         ExtTransferAcctInfo localExtTransferAcctInfo1 = (ExtTransferAcctInfo)param_ServerRequest.read_value(ExtTransferAcctInfo.class);
/*  4054: 4336 */         localObject2 = paramBPWServicesBean
/*  4055: 4337 */           .addExtTransferAccount(
/*  4056: 4338 */           localExtTransferAcctInfo1);
/*  4057:      */         
/*  4058: 4340 */         param_ServerRequest.write_value(localObject2, ExtTransferAcctInfo.class);
/*  4059:      */       }
/*  4060:      */       catch (Throwable localThrowable48)
/*  4061:      */       {
/*  4062: 4344 */         localThrowable48.printStackTrace(Jaguar.log);
/*  4063: 4345 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable48, true);
/*  4064: 4346 */         return localThrowable48.getClass().getName();
/*  4065:      */       }
/*  4066:      */     case 149: 
/*  4067:      */       try
/*  4068:      */       {
/*  4069: 4355 */         ExtTransferAcctInfo localExtTransferAcctInfo2 = (ExtTransferAcctInfo)param_ServerRequest.read_value(ExtTransferAcctInfo.class);
/*  4070: 4356 */         localObject2 = paramBPWServicesBean
/*  4071: 4357 */           .canExtTransferAccount(
/*  4072: 4358 */           localExtTransferAcctInfo2);
/*  4073:      */         
/*  4074: 4360 */         param_ServerRequest.write_value(localObject2, ExtTransferAcctInfo.class);
/*  4075:      */       }
/*  4076:      */       catch (Throwable localThrowable49)
/*  4077:      */       {
/*  4078: 4364 */         localThrowable49.printStackTrace(Jaguar.log);
/*  4079: 4365 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable49, true);
/*  4080: 4366 */         return localThrowable49.getClass().getName();
/*  4081:      */       }
/*  4082:      */     case 150: 
/*  4083:      */       try
/*  4084:      */       {
/*  4085: 4375 */         ExtTransferAcctInfo localExtTransferAcctInfo3 = (ExtTransferAcctInfo)param_ServerRequest.read_value(ExtTransferAcctInfo.class);
/*  4086: 4376 */         localObject2 = paramBPWServicesBean
/*  4087: 4377 */           .modExtTransferAccount(
/*  4088: 4378 */           localExtTransferAcctInfo3);
/*  4089:      */         
/*  4090: 4380 */         param_ServerRequest.write_value(localObject2, ExtTransferAcctInfo.class);
/*  4091:      */       }
/*  4092:      */       catch (Throwable localThrowable50)
/*  4093:      */       {
/*  4094: 4384 */         localThrowable50.printStackTrace(Jaguar.log);
/*  4095: 4385 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable50, true);
/*  4096: 4386 */         return localThrowable50.getClass().getName();
/*  4097:      */       }
/*  4098:      */     default: 
/*  4099: 4392 */       return 
/*  4100: 4393 */         invoke3(
/*  4101: 4394 */         param_ServerRequest, 
/*  4102: 4395 */         paramInputStream, 
/*  4103: 4396 */         paramOutputStream, 
/*  4104: 4397 */         paramBPWServicesBean, 
/*  4105: 4398 */         paramInteger);
/*  4106:      */     }
/*  4107: 4402 */     return null;
/*  4108:      */   }
/*  4109:      */   
/*  4110:      */   private static String invoke3(_ServerRequest param_ServerRequest, InputStream paramInputStream, OutputStream paramOutputStream, BPWServicesBean paramBPWServicesBean, Integer paramInteger)
/*  4111:      */   {
/*  4112:      */     Object localObject1;
/*  4113:      */     Object localObject3;
/*  4114:      */     Object localObject2;
/*  4115: 4412 */     switch (paramInteger.intValue())
/*  4116:      */     {
/*  4117:      */     case 151: 
/*  4118:      */       try
/*  4119:      */       {
/*  4120: 4419 */         ExtTransferAcctInfo localExtTransferAcctInfo1 = (ExtTransferAcctInfo)param_ServerRequest.read_value(ExtTransferAcctInfo.class);
/*  4121: 4420 */         localObject1 = paramBPWServicesBean
/*  4122: 4421 */           .getExtTransferAccount(
/*  4123: 4422 */           localExtTransferAcctInfo1);
/*  4124:      */         
/*  4125: 4424 */         param_ServerRequest.write_value(localObject1, ExtTransferAcctInfo.class);
/*  4126:      */       }
/*  4127:      */       catch (Throwable localThrowable1)
/*  4128:      */       {
/*  4129: 4428 */         localThrowable1.printStackTrace(Jaguar.log);
/*  4130: 4429 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/*  4131: 4430 */         return localThrowable1.getClass().getName();
/*  4132:      */       }
/*  4133:      */     case 152: 
/*  4134:      */       try
/*  4135:      */       {
/*  4136: 4439 */         String str1 = param_ServerRequest.read_string();
/*  4137:      */         
/*  4138: 4441 */         localObject1 = (ExtTransferAcctInfo)param_ServerRequest.read_value(ExtTransferAcctInfo.class);
/*  4139: 4443 */         if (param_ServerRequest.isRMI()) {
/*  4140: 4443 */           localObject3 = (int[])param_ServerRequest.read_value(new int[0].getClass());
/*  4141:      */         } else {
/*  4142: 4443 */           localObject3 = longSeqHelper.read(paramInputStream);
/*  4143:      */         }
/*  4144: 4444 */         ExtTransferAcctInfo localExtTransferAcctInfo5 = paramBPWServicesBean
/*  4145: 4445 */           .verifyExtTransferAccount(
/*  4146: 4446 */           str1, 
/*  4147: 4447 */           (ExtTransferAcctInfo)localObject1, 
/*  4148: 4448 */           (int[])localObject3);
/*  4149:      */         
/*  4150: 4450 */         param_ServerRequest.write_value(localExtTransferAcctInfo5, ExtTransferAcctInfo.class);
/*  4151:      */       }
/*  4152:      */       catch (Throwable localThrowable2)
/*  4153:      */       {
/*  4154: 4454 */         if ((localThrowable2 instanceof FFSException))
/*  4155:      */         {
/*  4156: 4456 */           if (UserException.ok(paramOutputStream)) {
/*  4157: 4458 */             if (param_ServerRequest.isRMI())
/*  4158:      */             {
/*  4159: 4460 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4160: 4461 */               param_ServerRequest.write_value((FFSException)localThrowable2, FFSException.class);
/*  4161:      */             }
/*  4162:      */             else
/*  4163:      */             {
/*  4164: 4465 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4165: 4466 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable2);
/*  4166:      */             }
/*  4167:      */           }
/*  4168: 4469 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable2);
/*  4169:      */         }
/*  4170: 4471 */         localThrowable2.printStackTrace(Jaguar.log);
/*  4171: 4472 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/*  4172: 4473 */         return localThrowable2.getClass().getName();
/*  4173:      */       }
/*  4174:      */     case 153: 
/*  4175:      */       try
/*  4176:      */       {
/*  4177: 4482 */         String str2 = param_ServerRequest.read_string();
/*  4178:      */         
/*  4179: 4484 */         localObject1 = (ExtTransferAcctInfo)param_ServerRequest.read_value(ExtTransferAcctInfo.class);
/*  4180: 4485 */         localObject3 = paramBPWServicesBean
/*  4181: 4486 */           .depositAmountsForVerify(
/*  4182: 4487 */           str2, 
/*  4183: 4488 */           (ExtTransferAcctInfo)localObject1);
/*  4184:      */         
/*  4185: 4490 */         param_ServerRequest.write_value(localObject3, ExtTransferAcctInfo.class);
/*  4186:      */       }
/*  4187:      */       catch (Throwable localThrowable3)
/*  4188:      */       {
/*  4189: 4494 */         if ((localThrowable3 instanceof FFSException))
/*  4190:      */         {
/*  4191: 4496 */           if (UserException.ok(paramOutputStream)) {
/*  4192: 4498 */             if (param_ServerRequest.isRMI())
/*  4193:      */             {
/*  4194: 4500 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4195: 4501 */               param_ServerRequest.write_value((FFSException)localThrowable3, FFSException.class);
/*  4196:      */             }
/*  4197:      */             else
/*  4198:      */             {
/*  4199: 4505 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4200: 4506 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable3);
/*  4201:      */             }
/*  4202:      */           }
/*  4203: 4509 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable3);
/*  4204:      */         }
/*  4205: 4511 */         localThrowable3.printStackTrace(Jaguar.log);
/*  4206: 4512 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/*  4207: 4513 */         return localThrowable3.getClass().getName();
/*  4208:      */       }
/*  4209:      */     case 154: 
/*  4210:      */       try
/*  4211:      */       {
/*  4212: 4522 */         ExtTransferAcctInfo localExtTransferAcctInfo2 = (ExtTransferAcctInfo)param_ServerRequest.read_value(ExtTransferAcctInfo.class);
/*  4213: 4523 */         localObject1 = paramBPWServicesBean
/*  4214: 4524 */           .activateExtTransferAcct(
/*  4215: 4525 */           localExtTransferAcctInfo2);
/*  4216:      */         
/*  4217: 4527 */         param_ServerRequest.write_value(localObject1, ExtTransferAcctInfo.class);
/*  4218:      */       }
/*  4219:      */       catch (Throwable localThrowable4)
/*  4220:      */       {
/*  4221: 4531 */         if ((localThrowable4 instanceof FFSException))
/*  4222:      */         {
/*  4223: 4533 */           if (UserException.ok(paramOutputStream)) {
/*  4224: 4535 */             if (param_ServerRequest.isRMI())
/*  4225:      */             {
/*  4226: 4537 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4227: 4538 */               param_ServerRequest.write_value((FFSException)localThrowable4, FFSException.class);
/*  4228:      */             }
/*  4229:      */             else
/*  4230:      */             {
/*  4231: 4542 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4232: 4543 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable4);
/*  4233:      */             }
/*  4234:      */           }
/*  4235: 4546 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable4);
/*  4236:      */         }
/*  4237: 4548 */         localThrowable4.printStackTrace(Jaguar.log);
/*  4238: 4549 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/*  4239: 4550 */         return localThrowable4.getClass().getName();
/*  4240:      */       }
/*  4241:      */     case 155: 
/*  4242:      */       try
/*  4243:      */       {
/*  4244: 4559 */         ExtTransferAcctInfo localExtTransferAcctInfo3 = (ExtTransferAcctInfo)param_ServerRequest.read_value(ExtTransferAcctInfo.class);
/*  4245: 4560 */         localObject1 = paramBPWServicesBean
/*  4246: 4561 */           .inactivateExtTransferAcct(
/*  4247: 4562 */           localExtTransferAcctInfo3);
/*  4248:      */         
/*  4249: 4564 */         param_ServerRequest.write_value(localObject1, ExtTransferAcctInfo.class);
/*  4250:      */       }
/*  4251:      */       catch (Throwable localThrowable5)
/*  4252:      */       {
/*  4253: 4568 */         if ((localThrowable5 instanceof FFSException))
/*  4254:      */         {
/*  4255: 4570 */           if (UserException.ok(paramOutputStream)) {
/*  4256: 4572 */             if (param_ServerRequest.isRMI())
/*  4257:      */             {
/*  4258: 4574 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4259: 4575 */               param_ServerRequest.write_value((FFSException)localThrowable5, FFSException.class);
/*  4260:      */             }
/*  4261:      */             else
/*  4262:      */             {
/*  4263: 4579 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4264: 4580 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable5);
/*  4265:      */             }
/*  4266:      */           }
/*  4267: 4583 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable5);
/*  4268:      */         }
/*  4269: 4585 */         localThrowable5.printStackTrace(Jaguar.log);
/*  4270: 4586 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/*  4271: 4587 */         return localThrowable5.getClass().getName();
/*  4272:      */       }
/*  4273:      */     case 156: 
/*  4274:      */       try
/*  4275:      */       {
/*  4276: 4596 */         ExtTransferAcctList localExtTransferAcctList = (ExtTransferAcctList)param_ServerRequest.read_value(ExtTransferAcctList.class);
/*  4277: 4597 */         localObject1 = paramBPWServicesBean
/*  4278: 4598 */           .getExtTransferAcctList(
/*  4279: 4599 */           localExtTransferAcctList);
/*  4280:      */         
/*  4281: 4601 */         param_ServerRequest.write_value(localObject1, ExtTransferAcctList.class);
/*  4282:      */       }
/*  4283:      */       catch (Throwable localThrowable6)
/*  4284:      */       {
/*  4285: 4605 */         localThrowable6.printStackTrace(Jaguar.log);
/*  4286: 4606 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/*  4287: 4607 */         return localThrowable6.getClass().getName();
/*  4288:      */       }
/*  4289:      */     case 157: 
/*  4290:      */       try
/*  4291:      */       {
/*  4292: 4616 */         String str3 = param_ServerRequest.read_string();
/*  4293: 4617 */         localObject1 = paramBPWServicesBean
/*  4294: 4618 */           .getNonBusinessDays(
/*  4295: 4619 */           str3);
/*  4296: 4621 */         if (param_ServerRequest.isRMI()) {
/*  4297: 4621 */           param_ServerRequest.write_value(localObject1, new NonBusinessDay[0].getClass());
/*  4298:      */         } else {
/*  4299: 4621 */           NonBusinessDaySeqHelper.write(paramOutputStream, (NonBusinessDay[])localObject1);
/*  4300:      */         }
/*  4301:      */       }
/*  4302:      */       catch (Throwable localThrowable7)
/*  4303:      */       {
/*  4304: 4625 */         if ((localThrowable7 instanceof FFSException))
/*  4305:      */         {
/*  4306: 4627 */           if (UserException.ok(paramOutputStream)) {
/*  4307: 4629 */             if (param_ServerRequest.isRMI())
/*  4308:      */             {
/*  4309: 4631 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4310: 4632 */               param_ServerRequest.write_value((FFSException)localThrowable7, FFSException.class);
/*  4311:      */             }
/*  4312:      */             else
/*  4313:      */             {
/*  4314: 4636 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4315: 4637 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable7);
/*  4316:      */             }
/*  4317:      */           }
/*  4318: 4640 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable7);
/*  4319:      */         }
/*  4320: 4642 */         localThrowable7.printStackTrace(Jaguar.log);
/*  4321: 4643 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/*  4322: 4644 */         return localThrowable7.getClass().getName();
/*  4323:      */       }
/*  4324:      */     case 158: 
/*  4325:      */       try
/*  4326:      */       {
/*  4327: 4653 */         String str4 = param_ServerRequest.read_string();
/*  4328: 4654 */         localObject1 = paramBPWServicesBean
/*  4329: 4655 */           .getNonBusinessDaysFromFile(
/*  4330: 4656 */           str4);
/*  4331: 4658 */         if (param_ServerRequest.isRMI()) {
/*  4332: 4658 */           param_ServerRequest.write_value(localObject1, new NonBusinessDay[0].getClass());
/*  4333:      */         } else {
/*  4334: 4658 */           NonBusinessDaySeqHelper.write(paramOutputStream, (NonBusinessDay[])localObject1);
/*  4335:      */         }
/*  4336:      */       }
/*  4337:      */       catch (Throwable localThrowable8)
/*  4338:      */       {
/*  4339: 4662 */         if ((localThrowable8 instanceof FFSException))
/*  4340:      */         {
/*  4341: 4664 */           if (UserException.ok(paramOutputStream)) {
/*  4342: 4666 */             if (param_ServerRequest.isRMI())
/*  4343:      */             {
/*  4344: 4668 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4345: 4669 */               param_ServerRequest.write_value((FFSException)localThrowable8, FFSException.class);
/*  4346:      */             }
/*  4347:      */             else
/*  4348:      */             {
/*  4349: 4673 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4350: 4674 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable8);
/*  4351:      */             }
/*  4352:      */           }
/*  4353: 4677 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable8);
/*  4354:      */         }
/*  4355: 4679 */         localThrowable8.printStackTrace(Jaguar.log);
/*  4356: 4680 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/*  4357: 4681 */         return localThrowable8.getClass().getName();
/*  4358:      */       }
/*  4359:      */     case 159: 
/*  4360:      */       try
/*  4361:      */       {
/*  4362: 4690 */         PagingInfo localPagingInfo1 = (PagingInfo)param_ServerRequest.read_value(PagingInfo.class);
/*  4363: 4691 */         localObject1 = paramBPWServicesBean
/*  4364: 4692 */           .getPagedWire(
/*  4365: 4693 */           localPagingInfo1);
/*  4366:      */         
/*  4367: 4695 */         param_ServerRequest.write_value(localObject1, PagingInfo.class);
/*  4368:      */       }
/*  4369:      */       catch (Throwable localThrowable9)
/*  4370:      */       {
/*  4371: 4699 */         if ((localThrowable9 instanceof FFSException))
/*  4372:      */         {
/*  4373: 4701 */           if (UserException.ok(paramOutputStream)) {
/*  4374: 4703 */             if (param_ServerRequest.isRMI())
/*  4375:      */             {
/*  4376: 4705 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4377: 4706 */               param_ServerRequest.write_value((FFSException)localThrowable9, FFSException.class);
/*  4378:      */             }
/*  4379:      */             else
/*  4380:      */             {
/*  4381: 4710 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4382: 4711 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable9);
/*  4383:      */             }
/*  4384:      */           }
/*  4385: 4714 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable9);
/*  4386:      */         }
/*  4387: 4716 */         localThrowable9.printStackTrace(Jaguar.log);
/*  4388: 4717 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/*  4389: 4718 */         return localThrowable9.getClass().getName();
/*  4390:      */       }
/*  4391:      */     case 160: 
/*  4392:      */       try
/*  4393:      */       {
/*  4394: 4727 */         PagingInfo localPagingInfo2 = (PagingInfo)param_ServerRequest.read_value(PagingInfo.class);
/*  4395: 4728 */         localObject1 = paramBPWServicesBean
/*  4396: 4729 */           .getPagedTransfer(
/*  4397: 4730 */           localPagingInfo2);
/*  4398:      */         
/*  4399: 4732 */         param_ServerRequest.write_value(localObject1, PagingInfo.class);
/*  4400:      */       }
/*  4401:      */       catch (Throwable localThrowable10)
/*  4402:      */       {
/*  4403: 4736 */         if ((localThrowable10 instanceof FFSException))
/*  4404:      */         {
/*  4405: 4738 */           if (UserException.ok(paramOutputStream)) {
/*  4406: 4740 */             if (param_ServerRequest.isRMI())
/*  4407:      */             {
/*  4408: 4742 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4409: 4743 */               param_ServerRequest.write_value((FFSException)localThrowable10, FFSException.class);
/*  4410:      */             }
/*  4411:      */             else
/*  4412:      */             {
/*  4413: 4747 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4414: 4748 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable10);
/*  4415:      */             }
/*  4416:      */           }
/*  4417: 4751 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable10);
/*  4418:      */         }
/*  4419: 4753 */         localThrowable10.printStackTrace(Jaguar.log);
/*  4420: 4754 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/*  4421: 4755 */         return localThrowable10.getClass().getName();
/*  4422:      */       }
/*  4423:      */     case 161: 
/*  4424:      */       try
/*  4425:      */       {
/*  4426: 4764 */         PagingInfo localPagingInfo3 = (PagingInfo)param_ServerRequest.read_value(PagingInfo.class);
/*  4427: 4765 */         localObject1 = paramBPWServicesBean
/*  4428: 4766 */           .getPagedBillPayments(
/*  4429: 4767 */           localPagingInfo3);
/*  4430:      */         
/*  4431: 4769 */         param_ServerRequest.write_value(localObject1, PagingInfo.class);
/*  4432:      */       }
/*  4433:      */       catch (Throwable localThrowable11)
/*  4434:      */       {
/*  4435: 4773 */         if ((localThrowable11 instanceof FFSException))
/*  4436:      */         {
/*  4437: 4775 */           if (UserException.ok(paramOutputStream)) {
/*  4438: 4777 */             if (param_ServerRequest.isRMI())
/*  4439:      */             {
/*  4440: 4779 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4441: 4780 */               param_ServerRequest.write_value((FFSException)localThrowable11, FFSException.class);
/*  4442:      */             }
/*  4443:      */             else
/*  4444:      */             {
/*  4445: 4784 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4446: 4785 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable11);
/*  4447:      */             }
/*  4448:      */           }
/*  4449: 4788 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable11);
/*  4450:      */         }
/*  4451: 4790 */         localThrowable11.printStackTrace(Jaguar.log);
/*  4452: 4791 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/*  4453: 4792 */         return localThrowable11.getClass().getName();
/*  4454:      */       }
/*  4455:      */     case 162: 
/*  4456:      */       try
/*  4457:      */       {
/*  4458: 4801 */         TransferInfo localTransferInfo = (TransferInfo)param_ServerRequest.read_value(TransferInfo.class);
/*  4459: 4802 */         int i = paramBPWServicesBean
/*  4460: 4803 */           .getValidTransferDateDue(
/*  4461: 4804 */           localTransferInfo);
/*  4462:      */         
/*  4463: 4806 */         paramOutputStream.write_long(i);
/*  4464:      */       }
/*  4465:      */       catch (Throwable localThrowable12)
/*  4466:      */       {
/*  4467: 4810 */         localThrowable12.printStackTrace(Jaguar.log);
/*  4468: 4811 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/*  4469: 4812 */         return localThrowable12.getClass().getName();
/*  4470:      */       }
/*  4471:      */     case 163: 
/*  4472:      */       try
/*  4473:      */       {
/*  4474: 4821 */         PagingInfo localPagingInfo4 = (PagingInfo)param_ServerRequest.read_value(PagingInfo.class);
/*  4475: 4822 */         localObject2 = paramBPWServicesBean
/*  4476: 4823 */           .getPagedCashCon(
/*  4477: 4824 */           localPagingInfo4);
/*  4478:      */         
/*  4479: 4826 */         param_ServerRequest.write_value(localObject2, PagingInfo.class);
/*  4480:      */       }
/*  4481:      */       catch (Throwable localThrowable13)
/*  4482:      */       {
/*  4483: 4830 */         if ((localThrowable13 instanceof FFSException))
/*  4484:      */         {
/*  4485: 4832 */           if (UserException.ok(paramOutputStream)) {
/*  4486: 4834 */             if (param_ServerRequest.isRMI())
/*  4487:      */             {
/*  4488: 4836 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4489: 4837 */               param_ServerRequest.write_value((FFSException)localThrowable13, FFSException.class);
/*  4490:      */             }
/*  4491:      */             else
/*  4492:      */             {
/*  4493: 4841 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4494: 4842 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable13);
/*  4495:      */             }
/*  4496:      */           }
/*  4497: 4845 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable13);
/*  4498:      */         }
/*  4499: 4847 */         localThrowable13.printStackTrace(Jaguar.log);
/*  4500: 4848 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/*  4501: 4849 */         return localThrowable13.getClass().getName();
/*  4502:      */       }
/*  4503:      */     case 164: 
/*  4504:      */       try
/*  4505:      */       {
/*  4506: 4858 */         String str5 = param_ServerRequest.read_string();
/*  4507:      */         
/*  4508: 4860 */         localObject2 = param_ServerRequest.read_string();
/*  4509: 4861 */         localObject3 = paramBPWServicesBean
/*  4510: 4862 */           .getPayeeByListId(
/*  4511: 4863 */           str5, 
/*  4512: 4864 */           (String)localObject2);
/*  4513:      */         
/*  4514: 4866 */         param_ServerRequest.write_value(localObject3, PayeeInfo.class);
/*  4515:      */       }
/*  4516:      */       catch (Throwable localThrowable14)
/*  4517:      */       {
/*  4518: 4870 */         if ((localThrowable14 instanceof FFSException))
/*  4519:      */         {
/*  4520: 4872 */           if (UserException.ok(paramOutputStream)) {
/*  4521: 4874 */             if (param_ServerRequest.isRMI())
/*  4522:      */             {
/*  4523: 4876 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4524: 4877 */               param_ServerRequest.write_value((FFSException)localThrowable14, FFSException.class);
/*  4525:      */             }
/*  4526:      */             else
/*  4527:      */             {
/*  4528: 4881 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4529: 4882 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable14);
/*  4530:      */             }
/*  4531:      */           }
/*  4532: 4885 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable14);
/*  4533:      */         }
/*  4534: 4887 */         localThrowable14.printStackTrace(Jaguar.log);
/*  4535: 4888 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/*  4536: 4889 */         return localThrowable14.getClass().getName();
/*  4537:      */       }
/*  4538:      */     case 165: 
/*  4539:      */       try
/*  4540:      */       {
/*  4541: 4897 */         AccountTypesMap localAccountTypesMap = paramBPWServicesBean
/*  4542: 4898 */           .getAccountTypesMap();
/*  4543:      */         
/*  4544: 4900 */         param_ServerRequest.write_value(localAccountTypesMap, AccountTypesMap.class);
/*  4545:      */       }
/*  4546:      */       catch (Throwable localThrowable15)
/*  4547:      */       {
/*  4548: 4904 */         if ((localThrowable15 instanceof FFSException))
/*  4549:      */         {
/*  4550: 4906 */           if (UserException.ok(paramOutputStream)) {
/*  4551: 4908 */             if (param_ServerRequest.isRMI())
/*  4552:      */             {
/*  4553: 4910 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4554: 4911 */               param_ServerRequest.write_value((FFSException)localThrowable15, FFSException.class);
/*  4555:      */             }
/*  4556:      */             else
/*  4557:      */             {
/*  4558: 4915 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4559: 4916 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable15);
/*  4560:      */             }
/*  4561:      */           }
/*  4562: 4919 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable15);
/*  4563:      */         }
/*  4564: 4921 */         localThrowable15.printStackTrace(Jaguar.log);
/*  4565: 4922 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/*  4566: 4923 */         return localThrowable15.getClass().getName();
/*  4567:      */       }
/*  4568:      */     case 166: 
/*  4569:      */       try
/*  4570:      */       {
/*  4571: 4932 */         ExtTransferAcctInfo localExtTransferAcctInfo4 = (ExtTransferAcctInfo)param_ServerRequest.read_value(ExtTransferAcctInfo.class);
/*  4572: 4933 */         localObject2 = paramBPWServicesBean
/*  4573: 4934 */           .modExtTransferAccountPrenoteStatus(
/*  4574: 4935 */           localExtTransferAcctInfo4);
/*  4575:      */         
/*  4576: 4937 */         param_ServerRequest.write_value(localObject2, ExtTransferAcctInfo.class);
/*  4577:      */       }
/*  4578:      */       catch (Throwable localThrowable16)
/*  4579:      */       {
/*  4580: 4941 */         localThrowable16.printStackTrace(Jaguar.log);
/*  4581: 4942 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/*  4582: 4943 */         return localThrowable16.getClass().getName();
/*  4583:      */       }
/*  4584:      */     case 167: 
/*  4585:      */       try
/*  4586:      */       {
/*  4587: 4952 */         String str6 = param_ServerRequest.read_string();
/*  4588:      */         
/*  4589: 4954 */         localObject2 = param_ServerRequest.read_string();
/*  4590: 4955 */         localObject3 = paramBPWServicesBean
/*  4591: 4956 */           .getBPWProperty(
/*  4592: 4957 */           str6, 
/*  4593: 4958 */           (String)localObject2);
/*  4594:      */         
/*  4595: 4960 */         param_ServerRequest.write_string((String)localObject3);
/*  4596:      */       }
/*  4597:      */       catch (Throwable localThrowable17)
/*  4598:      */       {
/*  4599: 4964 */         if ((localThrowable17 instanceof FFSException))
/*  4600:      */         {
/*  4601: 4966 */           if (UserException.ok(paramOutputStream)) {
/*  4602: 4968 */             if (param_ServerRequest.isRMI())
/*  4603:      */             {
/*  4604: 4970 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4605: 4971 */               param_ServerRequest.write_value((FFSException)localThrowable17, FFSException.class);
/*  4606:      */             }
/*  4607:      */             else
/*  4608:      */             {
/*  4609: 4975 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4610: 4976 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable17);
/*  4611:      */             }
/*  4612:      */           }
/*  4613: 4979 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable17);
/*  4614:      */         }
/*  4615: 4981 */         localThrowable17.printStackTrace(Jaguar.log);
/*  4616: 4982 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/*  4617: 4983 */         return localThrowable17.getClass().getName();
/*  4618:      */       }
/*  4619:      */     case 168: 
/*  4620:      */       try
/*  4621:      */       {
/*  4622: 4992 */         TransferBatchInfo localTransferBatchInfo1 = (TransferBatchInfo)param_ServerRequest.read_value(TransferBatchInfo.class);
/*  4623: 4993 */         localObject2 = paramBPWServicesBean
/*  4624: 4994 */           .addTransferBatch(
/*  4625: 4995 */           localTransferBatchInfo1);
/*  4626:      */         
/*  4627: 4997 */         param_ServerRequest.write_value(localObject2, TransferBatchInfo.class);
/*  4628:      */       }
/*  4629:      */       catch (Throwable localThrowable18)
/*  4630:      */       {
/*  4631: 5001 */         if ((localThrowable18 instanceof FFSException))
/*  4632:      */         {
/*  4633: 5003 */           if (UserException.ok(paramOutputStream)) {
/*  4634: 5005 */             if (param_ServerRequest.isRMI())
/*  4635:      */             {
/*  4636: 5007 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4637: 5008 */               param_ServerRequest.write_value((FFSException)localThrowable18, FFSException.class);
/*  4638:      */             }
/*  4639:      */             else
/*  4640:      */             {
/*  4641: 5012 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4642: 5013 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable18);
/*  4643:      */             }
/*  4644:      */           }
/*  4645: 5016 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable18);
/*  4646:      */         }
/*  4647: 5018 */         localThrowable18.printStackTrace(Jaguar.log);
/*  4648: 5019 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/*  4649: 5020 */         return localThrowable18.getClass().getName();
/*  4650:      */       }
/*  4651:      */     case 169: 
/*  4652:      */       try
/*  4653:      */       {
/*  4654: 5029 */         TransferBatchInfo localTransferBatchInfo2 = (TransferBatchInfo)param_ServerRequest.read_value(TransferBatchInfo.class);
/*  4655: 5030 */         localObject2 = paramBPWServicesBean
/*  4656: 5031 */           .modifyTransferBatch(
/*  4657: 5032 */           localTransferBatchInfo2);
/*  4658:      */         
/*  4659: 5034 */         param_ServerRequest.write_value(localObject2, TransferBatchInfo.class);
/*  4660:      */       }
/*  4661:      */       catch (Throwable localThrowable19)
/*  4662:      */       {
/*  4663: 5038 */         if ((localThrowable19 instanceof FFSException))
/*  4664:      */         {
/*  4665: 5040 */           if (UserException.ok(paramOutputStream)) {
/*  4666: 5042 */             if (param_ServerRequest.isRMI())
/*  4667:      */             {
/*  4668: 5044 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4669: 5045 */               param_ServerRequest.write_value((FFSException)localThrowable19, FFSException.class);
/*  4670:      */             }
/*  4671:      */             else
/*  4672:      */             {
/*  4673: 5049 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4674: 5050 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable19);
/*  4675:      */             }
/*  4676:      */           }
/*  4677: 5053 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable19);
/*  4678:      */         }
/*  4679: 5055 */         localThrowable19.printStackTrace(Jaguar.log);
/*  4680: 5056 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/*  4681: 5057 */         return localThrowable19.getClass().getName();
/*  4682:      */       }
/*  4683:      */     case 170: 
/*  4684:      */       try
/*  4685:      */       {
/*  4686: 5066 */         TransferBatchInfo localTransferBatchInfo3 = (TransferBatchInfo)param_ServerRequest.read_value(TransferBatchInfo.class);
/*  4687: 5067 */         localObject2 = paramBPWServicesBean
/*  4688: 5068 */           .cancelTransferBatch(
/*  4689: 5069 */           localTransferBatchInfo3);
/*  4690:      */         
/*  4691: 5071 */         param_ServerRequest.write_value(localObject2, TransferBatchInfo.class);
/*  4692:      */       }
/*  4693:      */       catch (Throwable localThrowable20)
/*  4694:      */       {
/*  4695: 5075 */         if ((localThrowable20 instanceof FFSException))
/*  4696:      */         {
/*  4697: 5077 */           if (UserException.ok(paramOutputStream)) {
/*  4698: 5079 */             if (param_ServerRequest.isRMI())
/*  4699:      */             {
/*  4700: 5081 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4701: 5082 */               param_ServerRequest.write_value((FFSException)localThrowable20, FFSException.class);
/*  4702:      */             }
/*  4703:      */             else
/*  4704:      */             {
/*  4705: 5086 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4706: 5087 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable20);
/*  4707:      */             }
/*  4708:      */           }
/*  4709: 5090 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable20);
/*  4710:      */         }
/*  4711: 5092 */         localThrowable20.printStackTrace(Jaguar.log);
/*  4712: 5093 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/*  4713: 5094 */         return localThrowable20.getClass().getName();
/*  4714:      */       }
/*  4715:      */     case 171: 
/*  4716:      */       try
/*  4717:      */       {
/*  4718: 5103 */         String str7 = param_ServerRequest.read_string();
/*  4719: 5104 */         localObject2 = paramBPWServicesBean
/*  4720: 5105 */           .getTransferBatchById(
/*  4721: 5106 */           str7);
/*  4722:      */         
/*  4723: 5108 */         param_ServerRequest.write_value(localObject2, TransferBatchInfo.class);
/*  4724:      */       }
/*  4725:      */       catch (Throwable localThrowable21)
/*  4726:      */       {
/*  4727: 5112 */         if ((localThrowable21 instanceof FFSException))
/*  4728:      */         {
/*  4729: 5114 */           if (UserException.ok(paramOutputStream)) {
/*  4730: 5116 */             if (param_ServerRequest.isRMI())
/*  4731:      */             {
/*  4732: 5118 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4733: 5119 */               param_ServerRequest.write_value((FFSException)localThrowable21, FFSException.class);
/*  4734:      */             }
/*  4735:      */             else
/*  4736:      */             {
/*  4737: 5123 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4738: 5124 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable21);
/*  4739:      */             }
/*  4740:      */           }
/*  4741: 5127 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable21);
/*  4742:      */         }
/*  4743: 5129 */         localThrowable21.printStackTrace(Jaguar.log);
/*  4744: 5130 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/*  4745: 5131 */         return localThrowable21.getClass().getName();
/*  4746:      */       }
/*  4747:      */     case 172: 
/*  4748:      */       try
/*  4749:      */       {
/*  4750: 5140 */         AccountTransactions localAccountTransactions = (AccountTransactions)param_ServerRequest.read_value(AccountTransactions.class);
/*  4751: 5141 */         localObject2 = paramBPWServicesBean
/*  4752: 5142 */           .accountHasPendingTransfers(
/*  4753: 5143 */           localAccountTransactions);
/*  4754:      */         
/*  4755: 5145 */         param_ServerRequest.write_value(localObject2, AccountTransactions.class);
/*  4756:      */       }
/*  4757:      */       catch (Throwable localThrowable22)
/*  4758:      */       {
/*  4759: 5149 */         if ((localThrowable22 instanceof FFSException))
/*  4760:      */         {
/*  4761: 5151 */           if (UserException.ok(paramOutputStream)) {
/*  4762: 5153 */             if (param_ServerRequest.isRMI())
/*  4763:      */             {
/*  4764: 5155 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4765: 5156 */               param_ServerRequest.write_value((FFSException)localThrowable22, FFSException.class);
/*  4766:      */             }
/*  4767:      */             else
/*  4768:      */             {
/*  4769: 5160 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4770: 5161 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable22);
/*  4771:      */             }
/*  4772:      */           }
/*  4773: 5164 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable22);
/*  4774:      */         }
/*  4775: 5166 */         localThrowable22.printStackTrace(Jaguar.log);
/*  4776: 5167 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/*  4777: 5168 */         return localThrowable22.getClass().getName();
/*  4778:      */       }
/*  4779:      */     case 173: 
/*  4780:      */       try
/*  4781:      */       {
/*  4782: 5177 */         PmtInfo localPmtInfo1 = (PmtInfo)param_ServerRequest.read_value(PmtInfo.class);
/*  4783: 5178 */         localObject2 = paramBPWServicesBean
/*  4784: 5179 */           .addBillPayment(
/*  4785: 5180 */           localPmtInfo1);
/*  4786:      */         
/*  4787: 5182 */         param_ServerRequest.write_value(localObject2, PmtInfo.class);
/*  4788:      */       }
/*  4789:      */       catch (Throwable localThrowable23)
/*  4790:      */       {
/*  4791: 5186 */         if ((localThrowable23 instanceof FFSException))
/*  4792:      */         {
/*  4793: 5188 */           if (UserException.ok(paramOutputStream)) {
/*  4794: 5190 */             if (param_ServerRequest.isRMI())
/*  4795:      */             {
/*  4796: 5192 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4797: 5193 */               param_ServerRequest.write_value((FFSException)localThrowable23, FFSException.class);
/*  4798:      */             }
/*  4799:      */             else
/*  4800:      */             {
/*  4801: 5197 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4802: 5198 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable23);
/*  4803:      */             }
/*  4804:      */           }
/*  4805: 5201 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable23);
/*  4806:      */         }
/*  4807: 5203 */         localThrowable23.printStackTrace(Jaguar.log);
/*  4808: 5204 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/*  4809: 5205 */         return localThrowable23.getClass().getName();
/*  4810:      */       }
/*  4811:      */     case 174: 
/*  4812:      */       try
/*  4813:      */       {
/*  4814: 5214 */         PmtInfo localPmtInfo2 = (PmtInfo)param_ServerRequest.read_value(PmtInfo.class);
/*  4815: 5215 */         localObject2 = paramBPWServicesBean
/*  4816: 5216 */           .modifyBillPayment(
/*  4817: 5217 */           localPmtInfo2);
/*  4818:      */         
/*  4819: 5219 */         param_ServerRequest.write_value(localObject2, PmtInfo.class);
/*  4820:      */       }
/*  4821:      */       catch (Throwable localThrowable24)
/*  4822:      */       {
/*  4823: 5223 */         if ((localThrowable24 instanceof FFSException))
/*  4824:      */         {
/*  4825: 5225 */           if (UserException.ok(paramOutputStream)) {
/*  4826: 5227 */             if (param_ServerRequest.isRMI())
/*  4827:      */             {
/*  4828: 5229 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4829: 5230 */               param_ServerRequest.write_value((FFSException)localThrowable24, FFSException.class);
/*  4830:      */             }
/*  4831:      */             else
/*  4832:      */             {
/*  4833: 5234 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4834: 5235 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable24);
/*  4835:      */             }
/*  4836:      */           }
/*  4837: 5238 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable24);
/*  4838:      */         }
/*  4839: 5240 */         localThrowable24.printStackTrace(Jaguar.log);
/*  4840: 5241 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/*  4841: 5242 */         return localThrowable24.getClass().getName();
/*  4842:      */       }
/*  4843:      */     case 175: 
/*  4844:      */       try
/*  4845:      */       {
/*  4846: 5251 */         PmtInfo localPmtInfo3 = (PmtInfo)param_ServerRequest.read_value(PmtInfo.class);
/*  4847: 5252 */         localObject2 = paramBPWServicesBean
/*  4848: 5253 */           .deleteBillPayment(
/*  4849: 5254 */           localPmtInfo3);
/*  4850:      */         
/*  4851: 5256 */         param_ServerRequest.write_value(localObject2, PmtInfo.class);
/*  4852:      */       }
/*  4853:      */       catch (Throwable localThrowable25)
/*  4854:      */       {
/*  4855: 5260 */         if ((localThrowable25 instanceof FFSException))
/*  4856:      */         {
/*  4857: 5262 */           if (UserException.ok(paramOutputStream)) {
/*  4858: 5264 */             if (param_ServerRequest.isRMI())
/*  4859:      */             {
/*  4860: 5266 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4861: 5267 */               param_ServerRequest.write_value((FFSException)localThrowable25, FFSException.class);
/*  4862:      */             }
/*  4863:      */             else
/*  4864:      */             {
/*  4865: 5271 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4866: 5272 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable25);
/*  4867:      */             }
/*  4868:      */           }
/*  4869: 5275 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable25);
/*  4870:      */         }
/*  4871: 5277 */         localThrowable25.printStackTrace(Jaguar.log);
/*  4872: 5278 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/*  4873: 5279 */         return localThrowable25.getClass().getName();
/*  4874:      */       }
/*  4875:      */     case 176: 
/*  4876:      */       try
/*  4877:      */       {
/*  4878: 5288 */         String str8 = param_ServerRequest.read_string();
/*  4879:      */         
/*  4880: 5290 */         localObject2 = param_ServerRequest.read_string();
/*  4881: 5291 */         localObject3 = paramBPWServicesBean
/*  4882: 5292 */           .getBillPaymentById(
/*  4883: 5293 */           str8, 
/*  4884: 5294 */           (String)localObject2);
/*  4885:      */         
/*  4886: 5296 */         param_ServerRequest.write_value(localObject3, PmtInfo.class);
/*  4887:      */       }
/*  4888:      */       catch (Throwable localThrowable26)
/*  4889:      */       {
/*  4890: 5300 */         if ((localThrowable26 instanceof FFSException))
/*  4891:      */         {
/*  4892: 5302 */           if (UserException.ok(paramOutputStream)) {
/*  4893: 5304 */             if (param_ServerRequest.isRMI())
/*  4894:      */             {
/*  4895: 5306 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4896: 5307 */               param_ServerRequest.write_value((FFSException)localThrowable26, FFSException.class);
/*  4897:      */             }
/*  4898:      */             else
/*  4899:      */             {
/*  4900: 5311 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4901: 5312 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable26);
/*  4902:      */             }
/*  4903:      */           }
/*  4904: 5315 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable26);
/*  4905:      */         }
/*  4906: 5317 */         localThrowable26.printStackTrace(Jaguar.log);
/*  4907: 5318 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/*  4908: 5319 */         return localThrowable26.getClass().getName();
/*  4909:      */       }
/*  4910:      */     case 177: 
/*  4911:      */       try
/*  4912:      */       {
/*  4913: 5328 */         PaymentBatchInfo localPaymentBatchInfo1 = (PaymentBatchInfo)param_ServerRequest.read_value(PaymentBatchInfo.class);
/*  4914: 5329 */         localObject2 = paramBPWServicesBean
/*  4915: 5330 */           .addPaymentBatch(
/*  4916: 5331 */           localPaymentBatchInfo1);
/*  4917:      */         
/*  4918: 5333 */         param_ServerRequest.write_value(localObject2, PaymentBatchInfo.class);
/*  4919:      */       }
/*  4920:      */       catch (Throwable localThrowable27)
/*  4921:      */       {
/*  4922: 5337 */         if ((localThrowable27 instanceof FFSException))
/*  4923:      */         {
/*  4924: 5339 */           if (UserException.ok(paramOutputStream)) {
/*  4925: 5341 */             if (param_ServerRequest.isRMI())
/*  4926:      */             {
/*  4927: 5343 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4928: 5344 */               param_ServerRequest.write_value((FFSException)localThrowable27, FFSException.class);
/*  4929:      */             }
/*  4930:      */             else
/*  4931:      */             {
/*  4932: 5348 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4933: 5349 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable27);
/*  4934:      */             }
/*  4935:      */           }
/*  4936: 5352 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable27);
/*  4937:      */         }
/*  4938: 5354 */         localThrowable27.printStackTrace(Jaguar.log);
/*  4939: 5355 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/*  4940: 5356 */         return localThrowable27.getClass().getName();
/*  4941:      */       }
/*  4942:      */     case 178: 
/*  4943:      */       try
/*  4944:      */       {
/*  4945: 5365 */         PaymentBatchInfo localPaymentBatchInfo2 = (PaymentBatchInfo)param_ServerRequest.read_value(PaymentBatchInfo.class);
/*  4946: 5366 */         localObject2 = paramBPWServicesBean
/*  4947: 5367 */           .modifyPaymentBatch(
/*  4948: 5368 */           localPaymentBatchInfo2);
/*  4949:      */         
/*  4950: 5370 */         param_ServerRequest.write_value(localObject2, PaymentBatchInfo.class);
/*  4951:      */       }
/*  4952:      */       catch (Throwable localThrowable28)
/*  4953:      */       {
/*  4954: 5374 */         if ((localThrowable28 instanceof FFSException))
/*  4955:      */         {
/*  4956: 5376 */           if (UserException.ok(paramOutputStream)) {
/*  4957: 5378 */             if (param_ServerRequest.isRMI())
/*  4958:      */             {
/*  4959: 5380 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4960: 5381 */               param_ServerRequest.write_value((FFSException)localThrowable28, FFSException.class);
/*  4961:      */             }
/*  4962:      */             else
/*  4963:      */             {
/*  4964: 5385 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4965: 5386 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable28);
/*  4966:      */             }
/*  4967:      */           }
/*  4968: 5389 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable28);
/*  4969:      */         }
/*  4970: 5391 */         localThrowable28.printStackTrace(Jaguar.log);
/*  4971: 5392 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/*  4972: 5393 */         return localThrowable28.getClass().getName();
/*  4973:      */       }
/*  4974:      */     case 179: 
/*  4975:      */       try
/*  4976:      */       {
/*  4977: 5402 */         PaymentBatchInfo localPaymentBatchInfo3 = (PaymentBatchInfo)param_ServerRequest.read_value(PaymentBatchInfo.class);
/*  4978: 5403 */         localObject2 = paramBPWServicesBean
/*  4979: 5404 */           .deletePaymentBatch(
/*  4980: 5405 */           localPaymentBatchInfo3);
/*  4981:      */         
/*  4982: 5407 */         param_ServerRequest.write_value(localObject2, PaymentBatchInfo.class);
/*  4983:      */       }
/*  4984:      */       catch (Throwable localThrowable29)
/*  4985:      */       {
/*  4986: 5411 */         if ((localThrowable29 instanceof FFSException))
/*  4987:      */         {
/*  4988: 5413 */           if (UserException.ok(paramOutputStream)) {
/*  4989: 5415 */             if (param_ServerRequest.isRMI())
/*  4990:      */             {
/*  4991: 5417 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  4992: 5418 */               param_ServerRequest.write_value((FFSException)localThrowable29, FFSException.class);
/*  4993:      */             }
/*  4994:      */             else
/*  4995:      */             {
/*  4996: 5422 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  4997: 5423 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable29);
/*  4998:      */             }
/*  4999:      */           }
/*  5000: 5426 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable29);
/*  5001:      */         }
/*  5002: 5428 */         localThrowable29.printStackTrace(Jaguar.log);
/*  5003: 5429 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/*  5004: 5430 */         return localThrowable29.getClass().getName();
/*  5005:      */       }
/*  5006:      */     case 180: 
/*  5007:      */       try
/*  5008:      */       {
/*  5009: 5439 */         String str9 = param_ServerRequest.read_string();
/*  5010: 5440 */         localObject2 = paramBPWServicesBean
/*  5011: 5441 */           .getPaymentBatchById(
/*  5012: 5442 */           str9);
/*  5013:      */         
/*  5014: 5444 */         param_ServerRequest.write_value(localObject2, PaymentBatchInfo.class);
/*  5015:      */       }
/*  5016:      */       catch (Throwable localThrowable30)
/*  5017:      */       {
/*  5018: 5448 */         if ((localThrowable30 instanceof FFSException))
/*  5019:      */         {
/*  5020: 5450 */           if (UserException.ok(paramOutputStream)) {
/*  5021: 5452 */             if (param_ServerRequest.isRMI())
/*  5022:      */             {
/*  5023: 5454 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  5024: 5455 */               param_ServerRequest.write_value((FFSException)localThrowable30, FFSException.class);
/*  5025:      */             }
/*  5026:      */             else
/*  5027:      */             {
/*  5028: 5459 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  5029: 5460 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable30);
/*  5030:      */             }
/*  5031:      */           }
/*  5032: 5463 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable30);
/*  5033:      */         }
/*  5034: 5465 */         localThrowable30.printStackTrace(Jaguar.log);
/*  5035: 5466 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/*  5036: 5467 */         return localThrowable30.getClass().getName();
/*  5037:      */       }
/*  5038:      */     case 181: 
/*  5039:      */       try
/*  5040:      */       {
/*  5041: 5476 */         LastPaymentInfo localLastPaymentInfo = (LastPaymentInfo)param_ServerRequest.read_value(LastPaymentInfo.class);
/*  5042: 5477 */         localObject2 = paramBPWServicesBean
/*  5043: 5478 */           .getLastPayments(
/*  5044: 5479 */           localLastPaymentInfo);
/*  5045:      */         
/*  5046: 5481 */         param_ServerRequest.write_value(localObject2, LastPaymentInfo.class);
/*  5047:      */       }
/*  5048:      */       catch (Throwable localThrowable31)
/*  5049:      */       {
/*  5050: 5485 */         if ((localThrowable31 instanceof FFSException))
/*  5051:      */         {
/*  5052: 5487 */           if (UserException.ok(paramOutputStream)) {
/*  5053: 5489 */             if (param_ServerRequest.isRMI())
/*  5054:      */             {
/*  5055: 5491 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  5056: 5492 */               param_ServerRequest.write_value((FFSException)localThrowable31, FFSException.class);
/*  5057:      */             }
/*  5058:      */             else
/*  5059:      */             {
/*  5060: 5496 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  5061: 5497 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable31);
/*  5062:      */             }
/*  5063:      */           }
/*  5064: 5500 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable31);
/*  5065:      */         }
/*  5066: 5502 */         localThrowable31.printStackTrace(Jaguar.log);
/*  5067: 5503 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/*  5068: 5504 */         return localThrowable31.getClass().getName();
/*  5069:      */       }
/*  5070:      */     case 182: 
/*  5071:      */       try
/*  5072:      */       {
/*  5073: 5513 */         BankingDays localBankingDays = (BankingDays)param_ServerRequest.read_value(BankingDays.class);
/*  5074:      */         
/*  5075: 5515 */         localObject2 = (HashMap)param_ServerRequest.read_value(HashMap.class);
/*  5076: 5516 */         localObject3 = paramBPWServicesBean
/*  5077: 5517 */           .getBankingDaysInRange(
/*  5078: 5518 */           localBankingDays, 
/*  5079: 5519 */           (HashMap)localObject2);
/*  5080:      */         
/*  5081: 5521 */         param_ServerRequest.write_value(localObject3, BankingDays.class);
/*  5082:      */       }
/*  5083:      */       catch (Throwable localThrowable32)
/*  5084:      */       {
/*  5085: 5525 */         if ((localThrowable32 instanceof FFSException))
/*  5086:      */         {
/*  5087: 5527 */           if (UserException.ok(paramOutputStream)) {
/*  5088: 5529 */             if (param_ServerRequest.isRMI())
/*  5089:      */             {
/*  5090: 5531 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  5091: 5532 */               param_ServerRequest.write_value((FFSException)localThrowable32, FFSException.class);
/*  5092:      */             }
/*  5093:      */             else
/*  5094:      */             {
/*  5095: 5536 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  5096: 5537 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable32);
/*  5097:      */             }
/*  5098:      */           }
/*  5099: 5540 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable32);
/*  5100:      */         }
/*  5101: 5542 */         localThrowable32.printStackTrace(Jaguar.log);
/*  5102: 5543 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/*  5103: 5544 */         return localThrowable32.getClass().getName();
/*  5104:      */       }
/*  5105:      */     case 183: 
/*  5106:      */       try
/*  5107:      */       {
/*  5108: 5553 */         CustomerPayeeInfo localCustomerPayeeInfo1 = (CustomerPayeeInfo)param_ServerRequest.read_value(CustomerPayeeInfo.class);
/*  5109: 5554 */         localObject2 = paramBPWServicesBean
/*  5110: 5555 */           .addCustomerPayee(
/*  5111: 5556 */           localCustomerPayeeInfo1);
/*  5112:      */         
/*  5113: 5558 */         param_ServerRequest.write_value(localObject2, CustomerPayeeInfo.class);
/*  5114:      */       }
/*  5115:      */       catch (Throwable localThrowable33)
/*  5116:      */       {
/*  5117: 5562 */         if ((localThrowable33 instanceof FFSException))
/*  5118:      */         {
/*  5119: 5564 */           if (UserException.ok(paramOutputStream)) {
/*  5120: 5566 */             if (param_ServerRequest.isRMI())
/*  5121:      */             {
/*  5122: 5568 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  5123: 5569 */               param_ServerRequest.write_value((FFSException)localThrowable33, FFSException.class);
/*  5124:      */             }
/*  5125:      */             else
/*  5126:      */             {
/*  5127: 5573 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  5128: 5574 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable33);
/*  5129:      */             }
/*  5130:      */           }
/*  5131: 5577 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable33);
/*  5132:      */         }
/*  5133: 5579 */         localThrowable33.printStackTrace(Jaguar.log);
/*  5134: 5580 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/*  5135: 5581 */         return localThrowable33.getClass().getName();
/*  5136:      */       }
/*  5137:      */     case 184: 
/*  5138:      */       try
/*  5139:      */       {
/*  5140: 5590 */         CustomerPayeeInfo localCustomerPayeeInfo2 = (CustomerPayeeInfo)param_ServerRequest.read_value(CustomerPayeeInfo.class);
/*  5141: 5591 */         localObject2 = paramBPWServicesBean
/*  5142: 5592 */           .deleteCustomerPayee(
/*  5143: 5593 */           localCustomerPayeeInfo2);
/*  5144:      */         
/*  5145: 5595 */         param_ServerRequest.write_value(localObject2, CustomerPayeeInfo.class);
/*  5146:      */       }
/*  5147:      */       catch (Throwable localThrowable34)
/*  5148:      */       {
/*  5149: 5599 */         if ((localThrowable34 instanceof FFSException))
/*  5150:      */         {
/*  5151: 5601 */           if (UserException.ok(paramOutputStream)) {
/*  5152: 5603 */             if (param_ServerRequest.isRMI())
/*  5153:      */             {
/*  5154: 5605 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  5155: 5606 */               param_ServerRequest.write_value((FFSException)localThrowable34, FFSException.class);
/*  5156:      */             }
/*  5157:      */             else
/*  5158:      */             {
/*  5159: 5610 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  5160: 5611 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable34);
/*  5161:      */             }
/*  5162:      */           }
/*  5163: 5614 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable34);
/*  5164:      */         }
/*  5165: 5616 */         localThrowable34.printStackTrace(Jaguar.log);
/*  5166: 5617 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/*  5167: 5618 */         return localThrowable34.getClass().getName();
/*  5168:      */       }
/*  5169:      */     case 185: 
/*  5170:      */       try
/*  5171:      */       {
/*  5172: 5627 */         CustomerPayeeInfo localCustomerPayeeInfo3 = (CustomerPayeeInfo)param_ServerRequest.read_value(CustomerPayeeInfo.class);
/*  5173: 5628 */         localObject2 = paramBPWServicesBean
/*  5174: 5629 */           .updateCustomerPayee(
/*  5175: 5630 */           localCustomerPayeeInfo3);
/*  5176:      */         
/*  5177: 5632 */         param_ServerRequest.write_value(localObject2, CustomerPayeeInfo.class);
/*  5178:      */       }
/*  5179:      */       catch (Throwable localThrowable35)
/*  5180:      */       {
/*  5181: 5636 */         if ((localThrowable35 instanceof FFSException))
/*  5182:      */         {
/*  5183: 5638 */           if (UserException.ok(paramOutputStream)) {
/*  5184: 5640 */             if (param_ServerRequest.isRMI())
/*  5185:      */             {
/*  5186: 5642 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  5187: 5643 */               param_ServerRequest.write_value((FFSException)localThrowable35, FFSException.class);
/*  5188:      */             }
/*  5189:      */             else
/*  5190:      */             {
/*  5191: 5647 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  5192: 5648 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable35);
/*  5193:      */             }
/*  5194:      */           }
/*  5195: 5651 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable35);
/*  5196:      */         }
/*  5197: 5653 */         localThrowable35.printStackTrace(Jaguar.log);
/*  5198: 5654 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/*  5199: 5655 */         return localThrowable35.getClass().getName();
/*  5200:      */       }
/*  5201:      */     case 186: 
/*  5202:      */       try
/*  5203:      */       {
/*  5204: 5664 */         CustomerPayeeInfo localCustomerPayeeInfo4 = (CustomerPayeeInfo)param_ServerRequest.read_value(CustomerPayeeInfo.class);
/*  5205: 5665 */         localObject2 = paramBPWServicesBean
/*  5206: 5666 */           .getCustomerPayees(
/*  5207: 5667 */           localCustomerPayeeInfo4);
/*  5208: 5669 */         if (param_ServerRequest.isRMI()) {
/*  5209: 5669 */           param_ServerRequest.write_value(localObject2, new CustomerPayeeInfo[0].getClass());
/*  5210:      */         } else {
/*  5211: 5669 */           CustomerPayeeInfoSeqHelper.write(paramOutputStream, (CustomerPayeeInfo[])localObject2);
/*  5212:      */         }
/*  5213:      */       }
/*  5214:      */       catch (Throwable localThrowable36)
/*  5215:      */       {
/*  5216: 5673 */         if ((localThrowable36 instanceof FFSException))
/*  5217:      */         {
/*  5218: 5675 */           if (UserException.ok(paramOutputStream)) {
/*  5219: 5677 */             if (param_ServerRequest.isRMI())
/*  5220:      */             {
/*  5221: 5679 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  5222: 5680 */               param_ServerRequest.write_value((FFSException)localThrowable36, FFSException.class);
/*  5223:      */             }
/*  5224:      */             else
/*  5225:      */             {
/*  5226: 5684 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  5227: 5685 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable36);
/*  5228:      */             }
/*  5229:      */           }
/*  5230: 5688 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable36);
/*  5231:      */         }
/*  5232: 5690 */         localThrowable36.printStackTrace(Jaguar.log);
/*  5233: 5691 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/*  5234: 5692 */         return localThrowable36.getClass().getName();
/*  5235:      */       }
/*  5236:      */     case 187: 
/*  5237:      */       try
/*  5238:      */       {
/*  5239: 5701 */         PayeeInfo localPayeeInfo1 = (PayeeInfo)param_ServerRequest.read_value(PayeeInfo.class);
/*  5240:      */         
/*  5241: 5703 */         int j = paramInputStream.read_long();
/*  5242: 5704 */         localObject3 = paramBPWServicesBean
/*  5243: 5705 */           .searchGlobalPayees(
/*  5244: 5706 */           localPayeeInfo1, 
/*  5245: 5707 */           j);
/*  5246: 5709 */         if (param_ServerRequest.isRMI()) {
/*  5247: 5709 */           param_ServerRequest.write_value(localObject3, new PayeeInfo[0].getClass());
/*  5248:      */         } else {
/*  5249: 5709 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject3);
/*  5250:      */         }
/*  5251:      */       }
/*  5252:      */       catch (Throwable localThrowable37)
/*  5253:      */       {
/*  5254: 5713 */         if ((localThrowable37 instanceof FFSException))
/*  5255:      */         {
/*  5256: 5715 */           if (UserException.ok(paramOutputStream)) {
/*  5257: 5717 */             if (param_ServerRequest.isRMI())
/*  5258:      */             {
/*  5259: 5719 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  5260: 5720 */               param_ServerRequest.write_value((FFSException)localThrowable37, FFSException.class);
/*  5261:      */             }
/*  5262:      */             else
/*  5263:      */             {
/*  5264: 5724 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  5265: 5725 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable37);
/*  5266:      */             }
/*  5267:      */           }
/*  5268: 5728 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable37);
/*  5269:      */         }
/*  5270: 5730 */         localThrowable37.printStackTrace(Jaguar.log);
/*  5271: 5731 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable37, true);
/*  5272: 5732 */         return localThrowable37.getClass().getName();
/*  5273:      */       }
/*  5274:      */     case 188: 
/*  5275:      */       try
/*  5276:      */       {
/*  5277: 5741 */         String str10 = param_ServerRequest.read_string();
/*  5278: 5742 */         PayeeInfo localPayeeInfo2 = paramBPWServicesBean
/*  5279: 5743 */           .getGlobalPayee(
/*  5280: 5744 */           str10);
/*  5281:      */         
/*  5282: 5746 */         param_ServerRequest.write_value(localPayeeInfo2, PayeeInfo.class);
/*  5283:      */       }
/*  5284:      */       catch (Throwable localThrowable38)
/*  5285:      */       {
/*  5286: 5750 */         if ((localThrowable38 instanceof FFSException))
/*  5287:      */         {
/*  5288: 5752 */           if (UserException.ok(paramOutputStream)) {
/*  5289: 5754 */             if (param_ServerRequest.isRMI())
/*  5290:      */             {
/*  5291: 5756 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  5292: 5757 */               param_ServerRequest.write_value((FFSException)localThrowable38, FFSException.class);
/*  5293:      */             }
/*  5294:      */             else
/*  5295:      */             {
/*  5296: 5761 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  5297: 5762 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable38);
/*  5298:      */             }
/*  5299:      */           }
/*  5300: 5765 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable38);
/*  5301:      */         }
/*  5302: 5767 */         localThrowable38.printStackTrace(Jaguar.log);
/*  5303: 5768 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable38, true);
/*  5304: 5769 */         return localThrowable38.getClass().getName();
/*  5305:      */       }
/*  5306:      */     case 189: 
/*  5307:      */       try
/*  5308:      */       {
/*  5309:      */         PmtTrnRslt[] arrayOfPmtTrnRslt;
/*  5310: 5778 */         if (param_ServerRequest.isRMI()) {
/*  5311: 5778 */           arrayOfPmtTrnRslt = (PmtTrnRslt[])param_ServerRequest.read_value(new PmtTrnRslt[0].getClass());
/*  5312:      */         } else {
/*  5313: 5778 */           arrayOfPmtTrnRslt = PmtTrnRsltSeqHelper.read(paramInputStream);
/*  5314:      */         }
/*  5315: 5780 */         paramBPWServicesBean.processPmtTrnRslt(
/*  5316: 5781 */           arrayOfPmtTrnRslt);
/*  5317:      */       }
/*  5318:      */       catch (Throwable localThrowable39)
/*  5319:      */       {
/*  5320: 5786 */         localThrowable39.printStackTrace(Jaguar.log);
/*  5321: 5787 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable39, true);
/*  5322: 5788 */         return localThrowable39.getClass().getName();
/*  5323:      */       }
/*  5324:      */     }
/*  5325: 5793 */     return null;
/*  5326:      */   }
/*  5327:      */   
/*  5328:      */   public static String localInvoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/*  5329:      */   {
/*  5330: 5803 */     _ServerRequest local_ServerRequest = new _ServerRequest(paramInputStream, paramOutputStream, (paramInt & 0x1) != 0);
/*  5331: 5804 */     BPWServicesBean localBPWServicesBean = (BPWServicesBean)paramObject;
/*  5332: 5805 */     Integer localInteger = null;
/*  5333: 5806 */     boolean bool1 = false;
/*  5334: 5807 */     if (!paramString.startsWith("#"))
/*  5335:      */     {
/*  5336: 5809 */       localInteger = (Integer)_localMethods2.get(paramString);
/*  5337: 5810 */       if (localInteger != null) {
/*  5338: 5811 */         bool1 = true;
/*  5339:      */       }
/*  5340:      */     }
/*  5341:      */     else
/*  5342:      */     {
/*  5343: 5815 */       localInteger = (Integer)_localMethods.get(paramString);
/*  5344:      */     }
/*  5345: 5817 */     if (localInteger == null) {
/*  5346: 5819 */       return remoteInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/*  5347:      */     }
/*  5348: 5821 */     LocalFrame localLocalFrame = LocalStack.getCurrent().top();
/*  5349:      */     Object localObject7;
/*  5350:      */     Object localObject9;
/*  5351:      */     Object localObject8;
/*  5352: 5822 */     switch (localInteger.intValue())
/*  5353:      */     {
/*  5354:      */     case 0: 
/*  5355:      */       try
/*  5356:      */       {
/*  5357: 5829 */         localBPWServicesBean.ejbCreate();
/*  5358:      */       }
/*  5359:      */       catch (Throwable localThrowable1)
/*  5360:      */       {
/*  5361: 5834 */         localThrowable1.printStackTrace(Jaguar.log);
/*  5362: 5835 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/*  5363: 5836 */         return localThrowable1.getClass().getName();
/*  5364:      */       }
/*  5365:      */     case 1: 
/*  5366:      */       try
/*  5367:      */       {
/*  5368: 5845 */         localBPWServicesBean.ejbRemove();
/*  5369:      */       }
/*  5370:      */       catch (Throwable localThrowable2)
/*  5371:      */       {
/*  5372: 5850 */         localThrowable2.printStackTrace(Jaguar.log);
/*  5373: 5851 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/*  5374: 5852 */         return localThrowable2.getClass().getName();
/*  5375:      */       }
/*  5376:      */     case 2: 
/*  5377:      */       try
/*  5378:      */       {
/*  5379: 5861 */         localBPWServicesBean.disconnect();
/*  5380:      */       }
/*  5381:      */       catch (Throwable localThrowable3)
/*  5382:      */       {
/*  5383: 5866 */         localThrowable3.printStackTrace(Jaguar.log);
/*  5384: 5867 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/*  5385: 5868 */         return localThrowable3.getClass().getName();
/*  5386:      */       }
/*  5387:      */     case 3: 
/*  5388:      */       try
/*  5389:      */       {
/*  5390:      */         CustomerInfo[] arrayOfCustomerInfo1;
/*  5391: 5877 */         if (!bool1)
/*  5392:      */         {
/*  5393: 5879 */           arrayOfCustomerInfo1 = (CustomerInfo[])localLocalFrame.get(0);
/*  5394:      */         }
/*  5395:      */         else
/*  5396:      */         {
/*  5397: 5883 */           Object localObject1 = localLocalFrame.get(0);
/*  5398: 5884 */           arrayOfCustomerInfo1 = (CustomerInfo[])ObjectVal.clone(localObject1);
/*  5399:      */         }
/*  5400: 5886 */         int i = localBPWServicesBean
/*  5401: 5887 */           .addCustomers(
/*  5402: 5888 */           arrayOfCustomerInfo1);
/*  5403:      */         
/*  5404: 5890 */         localLocalFrame.setResult(i);
/*  5405:      */       }
/*  5406:      */       catch (Throwable localThrowable4)
/*  5407:      */       {
/*  5408: 5894 */         localThrowable4.printStackTrace(Jaguar.log);
/*  5409: 5895 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/*  5410: 5896 */         return localThrowable4.getClass().getName();
/*  5411:      */       }
/*  5412:      */     case 4: 
/*  5413:      */       try
/*  5414:      */       {
/*  5415:      */         CustomerInfo[] arrayOfCustomerInfo2;
/*  5416: 5905 */         if (!bool1)
/*  5417:      */         {
/*  5418: 5907 */           arrayOfCustomerInfo2 = (CustomerInfo[])localLocalFrame.get(0);
/*  5419:      */         }
/*  5420:      */         else
/*  5421:      */         {
/*  5422: 5911 */           Object localObject2 = localLocalFrame.get(0);
/*  5423: 5912 */           arrayOfCustomerInfo2 = (CustomerInfo[])ObjectVal.clone(localObject2);
/*  5424:      */         }
/*  5425: 5914 */         int j = localBPWServicesBean
/*  5426: 5915 */           .modifyCustomers(
/*  5427: 5916 */           arrayOfCustomerInfo2);
/*  5428:      */         
/*  5429: 5918 */         localLocalFrame.setResult(j);
/*  5430:      */       }
/*  5431:      */       catch (Throwable localThrowable5)
/*  5432:      */       {
/*  5433: 5922 */         localThrowable5.printStackTrace(Jaguar.log);
/*  5434: 5923 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/*  5435: 5924 */         return localThrowable5.getClass().getName();
/*  5436:      */       }
/*  5437:      */     case 5: 
/*  5438:      */       try
/*  5439:      */       {
/*  5440:      */         String[] arrayOfString1;
/*  5441: 5933 */         if (!bool1)
/*  5442:      */         {
/*  5443: 5935 */           arrayOfString1 = (String[])localLocalFrame.get(0);
/*  5444:      */         }
/*  5445:      */         else
/*  5446:      */         {
/*  5447: 5939 */           Object localObject3 = localLocalFrame.get(0);
/*  5448: 5940 */           arrayOfString1 = (String[])ObjectVal.clone(localObject3);
/*  5449:      */         }
/*  5450: 5942 */         int k = localBPWServicesBean
/*  5451: 5943 */           .deleteCustomers(
/*  5452: 5944 */           arrayOfString1);
/*  5453:      */         
/*  5454: 5946 */         localLocalFrame.setResult(k);
/*  5455:      */       }
/*  5456:      */       catch (Throwable localThrowable6)
/*  5457:      */       {
/*  5458: 5950 */         localThrowable6.printStackTrace(Jaguar.log);
/*  5459: 5951 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/*  5460: 5952 */         return localThrowable6.getClass().getName();
/*  5461:      */       }
/*  5462:      */     case 6: 
/*  5463:      */       try
/*  5464:      */       {
/*  5465:      */         String[] arrayOfString2;
/*  5466: 5961 */         if (!bool1)
/*  5467:      */         {
/*  5468: 5963 */           arrayOfString2 = (String[])localLocalFrame.get(0);
/*  5469:      */         }
/*  5470:      */         else
/*  5471:      */         {
/*  5472: 5967 */           Object localObject4 = localLocalFrame.get(0);
/*  5473: 5968 */           arrayOfString2 = (String[])ObjectVal.clone(localObject4);
/*  5474:      */         }
/*  5475: 5971 */         int m = ((Integer)localLocalFrame.get(1)).intValue();
/*  5476: 5972 */         int i2 = localBPWServicesBean
/*  5477: 5973 */           .deleteCustomers(
/*  5478: 5974 */           arrayOfString2, 
/*  5479: 5975 */           m);
/*  5480:      */         
/*  5481: 5977 */         localLocalFrame.setResult(i2);
/*  5482:      */       }
/*  5483:      */       catch (Throwable localThrowable7)
/*  5484:      */       {
/*  5485: 5981 */         localThrowable7.printStackTrace(Jaguar.log);
/*  5486: 5982 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/*  5487: 5983 */         return localThrowable7.getClass().getName();
/*  5488:      */       }
/*  5489:      */     case 7: 
/*  5490:      */       try
/*  5491:      */       {
/*  5492:      */         String[] arrayOfString3;
/*  5493: 5992 */         if (!bool1)
/*  5494:      */         {
/*  5495: 5994 */           arrayOfString3 = (String[])localLocalFrame.get(0);
/*  5496:      */         }
/*  5497:      */         else
/*  5498:      */         {
/*  5499: 5998 */           Object localObject5 = localLocalFrame.get(0);
/*  5500: 5999 */           arrayOfString3 = (String[])ObjectVal.clone(localObject5);
/*  5501:      */         }
/*  5502: 6001 */         int n = localBPWServicesBean
/*  5503: 6002 */           .deactivateCustomers(
/*  5504: 6003 */           arrayOfString3);
/*  5505:      */         
/*  5506: 6005 */         localLocalFrame.setResult(n);
/*  5507:      */       }
/*  5508:      */       catch (Throwable localThrowable8)
/*  5509:      */       {
/*  5510: 6009 */         localThrowable8.printStackTrace(Jaguar.log);
/*  5511: 6010 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/*  5512: 6011 */         return localThrowable8.getClass().getName();
/*  5513:      */       }
/*  5514:      */     case 8: 
/*  5515:      */       try
/*  5516:      */       {
/*  5517:      */         String[] arrayOfString4;
/*  5518: 6020 */         if (!bool1)
/*  5519:      */         {
/*  5520: 6022 */           arrayOfString4 = (String[])localLocalFrame.get(0);
/*  5521:      */         }
/*  5522:      */         else
/*  5523:      */         {
/*  5524: 6026 */           Object localObject6 = localLocalFrame.get(0);
/*  5525: 6027 */           arrayOfString4 = (String[])ObjectVal.clone(localObject6);
/*  5526:      */         }
/*  5527: 6029 */         int i1 = localBPWServicesBean
/*  5528: 6030 */           .activateCustomers(
/*  5529: 6031 */           arrayOfString4);
/*  5530:      */         
/*  5531: 6033 */         localLocalFrame.setResult(i1);
/*  5532:      */       }
/*  5533:      */       catch (Throwable localThrowable9)
/*  5534:      */       {
/*  5535: 6037 */         localThrowable9.printStackTrace(Jaguar.log);
/*  5536: 6038 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/*  5537: 6039 */         return localThrowable9.getClass().getName();
/*  5538:      */       }
/*  5539:      */     case 9: 
/*  5540:      */       try
/*  5541:      */       {
/*  5542:      */         String[] arrayOfString5;
/*  5543: 6048 */         if (!bool1)
/*  5544:      */         {
/*  5545: 6050 */           arrayOfString5 = (String[])localLocalFrame.get(0);
/*  5546:      */         }
/*  5547:      */         else
/*  5548:      */         {
/*  5549: 6054 */           localObject7 = localLocalFrame.get(0);
/*  5550: 6055 */           arrayOfString5 = (String[])ObjectVal.clone(localObject7);
/*  5551:      */         }
/*  5552: 6057 */         localObject7 = 
/*  5553: 6058 */           localBPWServicesBean.getCustomersInfo(
/*  5554: 6059 */           arrayOfString5);
/*  5555:      */         
/*  5556: 6061 */         localLocalFrame.setResult(localObject7);
/*  5557:      */       }
/*  5558:      */       catch (Throwable localThrowable10)
/*  5559:      */       {
/*  5560: 6065 */         localThrowable10.printStackTrace(Jaguar.log);
/*  5561: 6066 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/*  5562: 6067 */         return localThrowable10.getClass().getName();
/*  5563:      */       }
/*  5564:      */     case 10: 
/*  5565:      */       try
/*  5566:      */       {
/*  5567: 6076 */         String str1 = (String)localLocalFrame.get(0);
/*  5568: 6077 */         localObject7 = localBPWServicesBean
/*  5569: 6078 */           .getCustomerByType(
/*  5570: 6079 */           str1);
/*  5571:      */         
/*  5572: 6081 */         localLocalFrame.setResult(localObject7);
/*  5573:      */       }
/*  5574:      */       catch (Throwable localThrowable11)
/*  5575:      */       {
/*  5576: 6085 */         localThrowable11.printStackTrace(Jaguar.log);
/*  5577: 6086 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/*  5578: 6087 */         return localThrowable11.getClass().getName();
/*  5579:      */       }
/*  5580:      */     case 11: 
/*  5581:      */       try
/*  5582:      */       {
/*  5583: 6096 */         String str2 = (String)localLocalFrame.get(0);
/*  5584: 6097 */         localObject7 = localBPWServicesBean
/*  5585: 6098 */           .getCustomerByFI(
/*  5586: 6099 */           str2);
/*  5587:      */         
/*  5588: 6101 */         localLocalFrame.setResult(localObject7);
/*  5589:      */       }
/*  5590:      */       catch (Throwable localThrowable12)
/*  5591:      */       {
/*  5592: 6105 */         localThrowable12.printStackTrace(Jaguar.log);
/*  5593: 6106 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/*  5594: 6107 */         return localThrowable12.getClass().getName();
/*  5595:      */       }
/*  5596:      */     case 12: 
/*  5597:      */       try
/*  5598:      */       {
/*  5599: 6116 */         String str3 = (String)localLocalFrame.get(0);
/*  5600: 6117 */         localObject7 = localBPWServicesBean
/*  5601: 6118 */           .getCustomerByCategory(
/*  5602: 6119 */           str3);
/*  5603:      */         
/*  5604: 6121 */         localLocalFrame.setResult(localObject7);
/*  5605:      */       }
/*  5606:      */       catch (Throwable localThrowable13)
/*  5607:      */       {
/*  5608: 6125 */         localThrowable13.printStackTrace(Jaguar.log);
/*  5609: 6126 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/*  5610: 6127 */         return localThrowable13.getClass().getName();
/*  5611:      */       }
/*  5612:      */     case 13: 
/*  5613:      */       try
/*  5614:      */       {
/*  5615: 6136 */         String str4 = (String)localLocalFrame.get(0);
/*  5616: 6137 */         localObject7 = localBPWServicesBean
/*  5617: 6138 */           .getCustomerByGroup(
/*  5618: 6139 */           str4);
/*  5619:      */         
/*  5620: 6141 */         localLocalFrame.setResult(localObject7);
/*  5621:      */       }
/*  5622:      */       catch (Throwable localThrowable14)
/*  5623:      */       {
/*  5624: 6145 */         localThrowable14.printStackTrace(Jaguar.log);
/*  5625: 6146 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/*  5626: 6147 */         return localThrowable14.getClass().getName();
/*  5627:      */       }
/*  5628:      */     case 14: 
/*  5629:      */       try
/*  5630:      */       {
/*  5631: 6156 */         String str5 = (String)localLocalFrame.get(0);
/*  5632:      */         
/*  5633: 6158 */         localObject7 = (String)localLocalFrame.get(1);
/*  5634: 6159 */         localObject9 = localBPWServicesBean
/*  5635: 6160 */           .getCustomerByTypeAndFI(
/*  5636: 6161 */           str5, 
/*  5637: 6162 */           (String)localObject7);
/*  5638:      */         
/*  5639: 6164 */         localLocalFrame.setResult(localObject9);
/*  5640:      */       }
/*  5641:      */       catch (Throwable localThrowable15)
/*  5642:      */       {
/*  5643: 6168 */         localThrowable15.printStackTrace(Jaguar.log);
/*  5644: 6169 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/*  5645: 6170 */         return localThrowable15.getClass().getName();
/*  5646:      */       }
/*  5647:      */     case 15: 
/*  5648:      */       try
/*  5649:      */       {
/*  5650: 6179 */         String str6 = (String)localLocalFrame.get(0);
/*  5651:      */         
/*  5652: 6181 */         localObject7 = (String)localLocalFrame.get(1);
/*  5653: 6182 */         localObject9 = localBPWServicesBean
/*  5654: 6183 */           .getCustomerByCategoryAndFI(
/*  5655: 6184 */           str6, 
/*  5656: 6185 */           (String)localObject7);
/*  5657:      */         
/*  5658: 6187 */         localLocalFrame.setResult(localObject9);
/*  5659:      */       }
/*  5660:      */       catch (Throwable localThrowable16)
/*  5661:      */       {
/*  5662: 6191 */         localThrowable16.printStackTrace(Jaguar.log);
/*  5663: 6192 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/*  5664: 6193 */         return localThrowable16.getClass().getName();
/*  5665:      */       }
/*  5666:      */     case 16: 
/*  5667:      */       try
/*  5668:      */       {
/*  5669: 6202 */         String str7 = (String)localLocalFrame.get(0);
/*  5670:      */         
/*  5671: 6204 */         localObject7 = (String)localLocalFrame.get(1);
/*  5672: 6205 */         localObject9 = localBPWServicesBean
/*  5673: 6206 */           .getCustomerByGroupAndFI(
/*  5674: 6207 */           str7, 
/*  5675: 6208 */           (String)localObject7);
/*  5676:      */         
/*  5677: 6210 */         localLocalFrame.setResult(localObject9);
/*  5678:      */       }
/*  5679:      */       catch (Throwable localThrowable17)
/*  5680:      */       {
/*  5681: 6214 */         localThrowable17.printStackTrace(Jaguar.log);
/*  5682: 6215 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/*  5683: 6216 */         return localThrowable17.getClass().getName();
/*  5684:      */       }
/*  5685:      */     case 17: 
/*  5686:      */       try
/*  5687:      */       {
/*  5688: 6225 */         String str8 = (String)localLocalFrame.get(0);
/*  5689: 6226 */         localObject7 = localBPWServicesBean
/*  5690: 6227 */           .getRPPSBillerInfoByFIRPPSId(
/*  5691: 6228 */           str8);
/*  5692:      */         
/*  5693: 6230 */         localLocalFrame.setResult(localObject7);
/*  5694:      */       }
/*  5695:      */       catch (Throwable localThrowable18)
/*  5696:      */       {
/*  5697: 6234 */         if ((localThrowable18 instanceof FFSException))
/*  5698:      */         {
/*  5699: 6236 */           localLocalFrame.setException(localThrowable18);
/*  5700: 6237 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  5701: 6238 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable18);
/*  5702:      */         }
/*  5703: 6240 */         localThrowable18.printStackTrace(Jaguar.log);
/*  5704: 6241 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/*  5705: 6242 */         return localThrowable18.getClass().getName();
/*  5706:      */       }
/*  5707:      */     case 18: 
/*  5708:      */       try
/*  5709:      */       {
/*  5710: 6251 */         String str9 = (String)localLocalFrame.get(0);
/*  5711:      */         
/*  5712: 6253 */         localObject7 = (String)localLocalFrame.get(1);
/*  5713: 6254 */         localObject9 = localBPWServicesBean
/*  5714: 6255 */           .getRPPSBillerInfoByFIAndBillerRPPSId(
/*  5715: 6256 */           str9, 
/*  5716: 6257 */           (String)localObject7);
/*  5717:      */         
/*  5718: 6259 */         localLocalFrame.setResult(localObject9);
/*  5719:      */       }
/*  5720:      */       catch (Throwable localThrowable19)
/*  5721:      */       {
/*  5722: 6263 */         if ((localThrowable19 instanceof FFSException))
/*  5723:      */         {
/*  5724: 6265 */           localLocalFrame.setException(localThrowable19);
/*  5725: 6266 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  5726: 6267 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable19);
/*  5727:      */         }
/*  5728: 6269 */         localThrowable19.printStackTrace(Jaguar.log);
/*  5729: 6270 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/*  5730: 6271 */         return localThrowable19.getClass().getName();
/*  5731:      */       }
/*  5732:      */     case 19: 
/*  5733:      */       try
/*  5734:      */       {
/*  5735:      */         WireInfo localWireInfo1;
/*  5736: 6280 */         if (!bool1)
/*  5737:      */         {
/*  5738: 6282 */           localWireInfo1 = (WireInfo)localLocalFrame.get(0);
/*  5739:      */         }
/*  5740:      */         else
/*  5741:      */         {
/*  5742: 6286 */           localObject7 = localLocalFrame.get(0);
/*  5743: 6287 */           localWireInfo1 = (WireInfo)ObjectVal.clone(localObject7);
/*  5744:      */         }
/*  5745: 6289 */         localObject7 = 
/*  5746: 6290 */           localBPWServicesBean.addWireTransfer(
/*  5747: 6291 */           localWireInfo1);
/*  5748:      */         
/*  5749: 6293 */         localLocalFrame.setResult(localObject7);
/*  5750:      */       }
/*  5751:      */       catch (Throwable localThrowable20)
/*  5752:      */       {
/*  5753: 6297 */         localThrowable20.printStackTrace(Jaguar.log);
/*  5754: 6298 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/*  5755: 6299 */         return localThrowable20.getClass().getName();
/*  5756:      */       }
/*  5757:      */     case 20: 
/*  5758:      */       try
/*  5759:      */       {
/*  5760:      */         WireInfo localWireInfo2;
/*  5761: 6308 */         if (!bool1)
/*  5762:      */         {
/*  5763: 6310 */           localWireInfo2 = (WireInfo)localLocalFrame.get(0);
/*  5764:      */         }
/*  5765:      */         else
/*  5766:      */         {
/*  5767: 6314 */           localObject7 = localLocalFrame.get(0);
/*  5768: 6315 */           localWireInfo2 = (WireInfo)ObjectVal.clone(localObject7);
/*  5769:      */         }
/*  5770: 6317 */         localObject7 = 
/*  5771: 6318 */           localBPWServicesBean.modWireTransfer(
/*  5772: 6319 */           localWireInfo2);
/*  5773:      */         
/*  5774: 6321 */         localLocalFrame.setResult(localObject7);
/*  5775:      */       }
/*  5776:      */       catch (Throwable localThrowable21)
/*  5777:      */       {
/*  5778: 6325 */         localThrowable21.printStackTrace(Jaguar.log);
/*  5779: 6326 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/*  5780: 6327 */         return localThrowable21.getClass().getName();
/*  5781:      */       }
/*  5782:      */     case 21: 
/*  5783:      */       try
/*  5784:      */       {
/*  5785:      */         WireInfo localWireInfo3;
/*  5786: 6336 */         if (!bool1)
/*  5787:      */         {
/*  5788: 6338 */           localWireInfo3 = (WireInfo)localLocalFrame.get(0);
/*  5789:      */         }
/*  5790:      */         else
/*  5791:      */         {
/*  5792: 6342 */           localObject7 = localLocalFrame.get(0);
/*  5793: 6343 */           localWireInfo3 = (WireInfo)ObjectVal.clone(localObject7);
/*  5794:      */         }
/*  5795: 6345 */         localObject7 = 
/*  5796: 6346 */           localBPWServicesBean.cancWireTransfer(
/*  5797: 6347 */           localWireInfo3);
/*  5798:      */         
/*  5799: 6349 */         localLocalFrame.setResult(localObject7);
/*  5800:      */       }
/*  5801:      */       catch (Throwable localThrowable22)
/*  5802:      */       {
/*  5803: 6353 */         localThrowable22.printStackTrace(Jaguar.log);
/*  5804: 6354 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/*  5805: 6355 */         return localThrowable22.getClass().getName();
/*  5806:      */       }
/*  5807:      */     case 22: 
/*  5808:      */       try
/*  5809:      */       {
/*  5810: 6364 */         String str10 = (String)localLocalFrame.get(0);
/*  5811: 6365 */         localObject7 = localBPWServicesBean
/*  5812: 6366 */           .getWireTransferById(
/*  5813: 6367 */           str10);
/*  5814:      */         
/*  5815: 6369 */         localLocalFrame.setResult(localObject7);
/*  5816:      */       }
/*  5817:      */       catch (Throwable localThrowable23)
/*  5818:      */       {
/*  5819: 6373 */         localThrowable23.printStackTrace(Jaguar.log);
/*  5820: 6374 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/*  5821: 6375 */         return localThrowable23.getClass().getName();
/*  5822:      */       }
/*  5823:      */     case 23: 
/*  5824:      */       try
/*  5825:      */       {
/*  5826: 6384 */         String str11 = (String)localLocalFrame.get(0);
/*  5827: 6385 */         localObject7 = localBPWServicesBean
/*  5828: 6386 */           .getWireTransfer(
/*  5829: 6387 */           str11);
/*  5830:      */         
/*  5831: 6389 */         localLocalFrame.setResult(localObject7);
/*  5832:      */       }
/*  5833:      */       catch (Throwable localThrowable24)
/*  5834:      */       {
/*  5835: 6393 */         localThrowable24.printStackTrace(Jaguar.log);
/*  5836: 6394 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/*  5837: 6395 */         return localThrowable24.getClass().getName();
/*  5838:      */       }
/*  5839:      */     case 24: 
/*  5840:      */       try
/*  5841:      */       {
/*  5842:      */         String[] arrayOfString6;
/*  5843: 6404 */         if (!bool1)
/*  5844:      */         {
/*  5845: 6406 */           arrayOfString6 = (String[])localLocalFrame.get(0);
/*  5846:      */         }
/*  5847:      */         else
/*  5848:      */         {
/*  5849: 6410 */           localObject7 = localLocalFrame.get(0);
/*  5850: 6411 */           arrayOfString6 = (String[])ObjectVal.clone(localObject7);
/*  5851:      */         }
/*  5852: 6413 */         localObject7 = 
/*  5853: 6414 */           localBPWServicesBean.getWireTransfers(
/*  5854: 6415 */           arrayOfString6);
/*  5855:      */         
/*  5856: 6417 */         localLocalFrame.setResult(localObject7);
/*  5857:      */       }
/*  5858:      */       catch (Throwable localThrowable25)
/*  5859:      */       {
/*  5860: 6421 */         localThrowable25.printStackTrace(Jaguar.log);
/*  5861: 6422 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/*  5862: 6423 */         return localThrowable25.getClass().getName();
/*  5863:      */       }
/*  5864:      */     case 25: 
/*  5865:      */       try
/*  5866:      */       {
/*  5867:      */         WireInfo localWireInfo4;
/*  5868: 6432 */         if (!bool1)
/*  5869:      */         {
/*  5870: 6434 */           localWireInfo4 = (WireInfo)localLocalFrame.get(0);
/*  5871:      */         }
/*  5872:      */         else
/*  5873:      */         {
/*  5874: 6438 */           localObject7 = localLocalFrame.get(0);
/*  5875: 6439 */           localWireInfo4 = (WireInfo)ObjectVal.clone(localObject7);
/*  5876:      */         }
/*  5877: 6441 */         localObject7 = 
/*  5878: 6442 */           localBPWServicesBean.getDuplicateWires(
/*  5879: 6443 */           localWireInfo4);
/*  5880:      */         
/*  5881: 6445 */         localLocalFrame.setResult(localObject7);
/*  5882:      */       }
/*  5883:      */       catch (Throwable localThrowable26)
/*  5884:      */       {
/*  5885: 6449 */         localThrowable26.printStackTrace(Jaguar.log);
/*  5886: 6450 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/*  5887: 6451 */         return localThrowable26.getClass().getName();
/*  5888:      */       }
/*  5889:      */     case 26: 
/*  5890:      */       try
/*  5891:      */       {
/*  5892: 6460 */         String str12 = (String)localLocalFrame.get(0);
/*  5893: 6461 */         localObject7 = localBPWServicesBean
/*  5894: 6462 */           .getBatchWires(
/*  5895: 6463 */           str12);
/*  5896:      */         
/*  5897: 6465 */         localLocalFrame.setResult(localObject7);
/*  5898:      */       }
/*  5899:      */       catch (Throwable localThrowable27)
/*  5900:      */       {
/*  5901: 6469 */         localThrowable27.printStackTrace(Jaguar.log);
/*  5902: 6470 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/*  5903: 6471 */         return localThrowable27.getClass().getName();
/*  5904:      */       }
/*  5905:      */     case 27: 
/*  5906:      */       try
/*  5907:      */       {
/*  5908:      */         BPWHist localBPWHist1;
/*  5909: 6480 */         if (!bool1)
/*  5910:      */         {
/*  5911: 6482 */           localBPWHist1 = (BPWHist)localLocalFrame.get(0);
/*  5912:      */         }
/*  5913:      */         else
/*  5914:      */         {
/*  5915: 6486 */           localObject7 = localLocalFrame.get(0);
/*  5916: 6487 */           localBPWHist1 = (BPWHist)ObjectVal.clone(localObject7);
/*  5917:      */         }
/*  5918: 6489 */         localObject7 = 
/*  5919: 6490 */           localBPWServicesBean.getWireHistory(
/*  5920: 6491 */           localBPWHist1);
/*  5921:      */         
/*  5922: 6493 */         localLocalFrame.setResult(localObject7);
/*  5923:      */       }
/*  5924:      */       catch (Throwable localThrowable28)
/*  5925:      */       {
/*  5926: 6497 */         localThrowable28.printStackTrace(Jaguar.log);
/*  5927: 6498 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/*  5928: 6499 */         return localThrowable28.getClass().getName();
/*  5929:      */       }
/*  5930:      */     case 28: 
/*  5931:      */       try
/*  5932:      */       {
/*  5933:      */         BPWHist localBPWHist2;
/*  5934: 6508 */         if (!bool1)
/*  5935:      */         {
/*  5936: 6510 */           localBPWHist2 = (BPWHist)localLocalFrame.get(0);
/*  5937:      */         }
/*  5938:      */         else
/*  5939:      */         {
/*  5940: 6514 */           localObject7 = localLocalFrame.get(0);
/*  5941: 6515 */           localBPWHist2 = (BPWHist)ObjectVal.clone(localObject7);
/*  5942:      */         }
/*  5943: 6517 */         localObject7 = 
/*  5944: 6518 */           localBPWServicesBean.getWireHistoryByCustomer(
/*  5945: 6519 */           localBPWHist2);
/*  5946:      */         
/*  5947: 6521 */         localLocalFrame.setResult(localObject7);
/*  5948:      */       }
/*  5949:      */       catch (Throwable localThrowable29)
/*  5950:      */       {
/*  5951: 6525 */         localThrowable29.printStackTrace(Jaguar.log);
/*  5952: 6526 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/*  5953: 6527 */         return localThrowable29.getClass().getName();
/*  5954:      */       }
/*  5955:      */     case 29: 
/*  5956:      */       try
/*  5957:      */       {
/*  5958:      */         BPWHist localBPWHist3;
/*  5959: 6536 */         if (!bool1)
/*  5960:      */         {
/*  5961: 6538 */           localBPWHist3 = (BPWHist)localLocalFrame.get(0);
/*  5962:      */         }
/*  5963:      */         else
/*  5964:      */         {
/*  5965: 6542 */           localObject7 = localLocalFrame.get(0);
/*  5966: 6543 */           localBPWHist3 = (BPWHist)ObjectVal.clone(localObject7);
/*  5967:      */         }
/*  5968: 6545 */         localObject7 = 
/*  5969: 6546 */           localBPWServicesBean.getCombinedWireHistory(
/*  5970: 6547 */           localBPWHist3);
/*  5971:      */         
/*  5972: 6549 */         localLocalFrame.setResult(localObject7);
/*  5973:      */       }
/*  5974:      */       catch (Throwable localThrowable30)
/*  5975:      */       {
/*  5976: 6553 */         localThrowable30.printStackTrace(Jaguar.log);
/*  5977: 6554 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/*  5978: 6555 */         return localThrowable30.getClass().getName();
/*  5979:      */       }
/*  5980:      */     case 30: 
/*  5981:      */       try
/*  5982:      */       {
/*  5983: 6564 */         String str13 = (String)localLocalFrame.get(0);
/*  5984: 6565 */         localObject7 = localBPWServicesBean
/*  5985: 6566 */           .getAuditWireTransfer(
/*  5986: 6567 */           str13);
/*  5987:      */         
/*  5988: 6569 */         localLocalFrame.setResult(localObject7);
/*  5989:      */       }
/*  5990:      */       catch (Throwable localThrowable31)
/*  5991:      */       {
/*  5992: 6573 */         localThrowable31.printStackTrace(Jaguar.log);
/*  5993: 6574 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/*  5994: 6575 */         return localThrowable31.getClass().getName();
/*  5995:      */       }
/*  5996:      */     case 31: 
/*  5997:      */       try
/*  5998:      */       {
/*  5999: 6584 */         String str14 = (String)localLocalFrame.get(0);
/*  6000: 6585 */         localObject7 = localBPWServicesBean
/*  6001: 6586 */           .getAuditWireTransferByExtId(
/*  6002: 6587 */           str14);
/*  6003:      */         
/*  6004: 6589 */         localLocalFrame.setResult(localObject7);
/*  6005:      */       }
/*  6006:      */       catch (Throwable localThrowable32)
/*  6007:      */       {
/*  6008: 6593 */         localThrowable32.printStackTrace(Jaguar.log);
/*  6009: 6594 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/*  6010: 6595 */         return localThrowable32.getClass().getName();
/*  6011:      */       }
/*  6012:      */     case 32: 
/*  6013:      */       try
/*  6014:      */       {
/*  6015:      */         WireReleaseInfo localWireReleaseInfo;
/*  6016: 6604 */         if (!bool1)
/*  6017:      */         {
/*  6018: 6606 */           localWireReleaseInfo = (WireReleaseInfo)localLocalFrame.get(0);
/*  6019:      */         }
/*  6020:      */         else
/*  6021:      */         {
/*  6022: 6610 */           localObject7 = localLocalFrame.get(0);
/*  6023: 6611 */           localWireReleaseInfo = (WireReleaseInfo)ObjectVal.clone(localObject7);
/*  6024:      */         }
/*  6025: 6613 */         localObject7 = 
/*  6026: 6614 */           localBPWServicesBean.getWireReleaseCount(
/*  6027: 6615 */           localWireReleaseInfo);
/*  6028:      */         
/*  6029: 6617 */         localLocalFrame.setResult(localObject7);
/*  6030:      */       }
/*  6031:      */       catch (Throwable localThrowable33)
/*  6032:      */       {
/*  6033: 6621 */         localThrowable33.printStackTrace(Jaguar.log);
/*  6034: 6622 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/*  6035: 6623 */         return localThrowable33.getClass().getName();
/*  6036:      */       }
/*  6037:      */     case 33: 
/*  6038:      */       try
/*  6039:      */       {
/*  6040:      */         WireInfo[] arrayOfWireInfo1;
/*  6041: 6632 */         if (!bool1)
/*  6042:      */         {
/*  6043: 6634 */           arrayOfWireInfo1 = (WireInfo[])localLocalFrame.get(0);
/*  6044:      */         }
/*  6045:      */         else
/*  6046:      */         {
/*  6047: 6638 */           localObject7 = localLocalFrame.get(0);
/*  6048: 6639 */           arrayOfWireInfo1 = (WireInfo[])ObjectVal.clone(localObject7);
/*  6049:      */         }
/*  6050: 6641 */         localObject7 = 
/*  6051: 6642 */           localBPWServicesBean.addWireTransfers(
/*  6052: 6643 */           arrayOfWireInfo1);
/*  6053:      */         
/*  6054: 6645 */         localLocalFrame.setResult(localObject7);
/*  6055:      */       }
/*  6056:      */       catch (Throwable localThrowable34)
/*  6057:      */       {
/*  6058: 6649 */         localThrowable34.printStackTrace(Jaguar.log);
/*  6059: 6650 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/*  6060: 6651 */         return localThrowable34.getClass().getName();
/*  6061:      */       }
/*  6062:      */     case 34: 
/*  6063:      */       try
/*  6064:      */       {
/*  6065:      */         WireInfo[] arrayOfWireInfo2;
/*  6066: 6660 */         if (!bool1)
/*  6067:      */         {
/*  6068: 6662 */           arrayOfWireInfo2 = (WireInfo[])localLocalFrame.get(0);
/*  6069:      */         }
/*  6070:      */         else
/*  6071:      */         {
/*  6072: 6666 */           localObject7 = localLocalFrame.get(0);
/*  6073: 6667 */           arrayOfWireInfo2 = (WireInfo[])ObjectVal.clone(localObject7);
/*  6074:      */         }
/*  6075: 6669 */         localObject7 = 
/*  6076: 6670 */           localBPWServicesBean.releaseWireTransfer(
/*  6077: 6671 */           arrayOfWireInfo2);
/*  6078:      */         
/*  6079: 6673 */         localLocalFrame.setResult(localObject7);
/*  6080:      */       }
/*  6081:      */       catch (Throwable localThrowable35)
/*  6082:      */       {
/*  6083: 6677 */         localThrowable35.printStackTrace(Jaguar.log);
/*  6084: 6678 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/*  6085: 6679 */         return localThrowable35.getClass().getName();
/*  6086:      */       }
/*  6087:      */     case 35: 
/*  6088:      */       try
/*  6089:      */       {
/*  6090:      */         RecWireInfo localRecWireInfo1;
/*  6091: 6688 */         if (!bool1)
/*  6092:      */         {
/*  6093: 6690 */           localRecWireInfo1 = (RecWireInfo)localLocalFrame.get(0);
/*  6094:      */         }
/*  6095:      */         else
/*  6096:      */         {
/*  6097: 6694 */           localObject7 = localLocalFrame.get(0);
/*  6098: 6695 */           localRecWireInfo1 = (RecWireInfo)ObjectVal.clone(localObject7);
/*  6099:      */         }
/*  6100: 6697 */         localObject7 = 
/*  6101: 6698 */           localBPWServicesBean.addRecWireTransfer(
/*  6102: 6699 */           localRecWireInfo1);
/*  6103:      */         
/*  6104: 6701 */         localLocalFrame.setResult(localObject7);
/*  6105:      */       }
/*  6106:      */       catch (Throwable localThrowable36)
/*  6107:      */       {
/*  6108: 6705 */         localThrowable36.printStackTrace(Jaguar.log);
/*  6109: 6706 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/*  6110: 6707 */         return localThrowable36.getClass().getName();
/*  6111:      */       }
/*  6112:      */     case 36: 
/*  6113:      */       try
/*  6114:      */       {
/*  6115:      */         RecWireInfo localRecWireInfo2;
/*  6116: 6716 */         if (!bool1)
/*  6117:      */         {
/*  6118: 6718 */           localRecWireInfo2 = (RecWireInfo)localLocalFrame.get(0);
/*  6119:      */         }
/*  6120:      */         else
/*  6121:      */         {
/*  6122: 6722 */           localObject7 = localLocalFrame.get(0);
/*  6123: 6723 */           localRecWireInfo2 = (RecWireInfo)ObjectVal.clone(localObject7);
/*  6124:      */         }
/*  6125: 6725 */         localObject7 = 
/*  6126: 6726 */           localBPWServicesBean.modRecWireTransfer(
/*  6127: 6727 */           localRecWireInfo2);
/*  6128:      */         
/*  6129: 6729 */         localLocalFrame.setResult(localObject7);
/*  6130:      */       }
/*  6131:      */       catch (Throwable localThrowable37)
/*  6132:      */       {
/*  6133: 6733 */         localThrowable37.printStackTrace(Jaguar.log);
/*  6134: 6734 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable37, true);
/*  6135: 6735 */         return localThrowable37.getClass().getName();
/*  6136:      */       }
/*  6137:      */     case 37: 
/*  6138:      */       try
/*  6139:      */       {
/*  6140:      */         RecWireInfo localRecWireInfo3;
/*  6141: 6744 */         if (!bool1)
/*  6142:      */         {
/*  6143: 6746 */           localRecWireInfo3 = (RecWireInfo)localLocalFrame.get(0);
/*  6144:      */         }
/*  6145:      */         else
/*  6146:      */         {
/*  6147: 6750 */           localObject7 = localLocalFrame.get(0);
/*  6148: 6751 */           localRecWireInfo3 = (RecWireInfo)ObjectVal.clone(localObject7);
/*  6149:      */         }
/*  6150: 6753 */         localObject7 = 
/*  6151: 6754 */           localBPWServicesBean.cancRecWireTransfer(
/*  6152: 6755 */           localRecWireInfo3);
/*  6153:      */         
/*  6154: 6757 */         localLocalFrame.setResult(localObject7);
/*  6155:      */       }
/*  6156:      */       catch (Throwable localThrowable38)
/*  6157:      */       {
/*  6158: 6761 */         localThrowable38.printStackTrace(Jaguar.log);
/*  6159: 6762 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable38, true);
/*  6160: 6763 */         return localThrowable38.getClass().getName();
/*  6161:      */       }
/*  6162:      */     case 38: 
/*  6163:      */       try
/*  6164:      */       {
/*  6165: 6772 */         String str15 = (String)localLocalFrame.get(0);
/*  6166:      */         
/*  6167: 6774 */         boolean bool2 = ((Boolean)localLocalFrame.get(1)).booleanValue();
/*  6168: 6775 */         localObject9 = localBPWServicesBean
/*  6169: 6776 */           .getRecWireTransferById(
/*  6170: 6777 */           str15, 
/*  6171: 6778 */           bool2);
/*  6172:      */         
/*  6173: 6780 */         localLocalFrame.setResult(localObject9);
/*  6174:      */       }
/*  6175:      */       catch (Throwable localThrowable39)
/*  6176:      */       {
/*  6177: 6784 */         localThrowable39.printStackTrace(Jaguar.log);
/*  6178: 6785 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable39, true);
/*  6179: 6786 */         return localThrowable39.getClass().getName();
/*  6180:      */       }
/*  6181:      */     case 39: 
/*  6182:      */       try
/*  6183:      */       {
/*  6184: 6795 */         String str16 = (String)localLocalFrame.get(0);
/*  6185: 6796 */         localObject8 = localBPWServicesBean
/*  6186: 6797 */           .getRecWireTransferById(
/*  6187: 6798 */           str16);
/*  6188:      */         
/*  6189: 6800 */         localLocalFrame.setResult(localObject8);
/*  6190:      */       }
/*  6191:      */       catch (Throwable localThrowable40)
/*  6192:      */       {
/*  6193: 6804 */         localThrowable40.printStackTrace(Jaguar.log);
/*  6194: 6805 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable40, true);
/*  6195: 6806 */         return localThrowable40.getClass().getName();
/*  6196:      */       }
/*  6197:      */     case 40: 
/*  6198:      */       try
/*  6199:      */       {
/*  6200: 6815 */         String str17 = (String)localLocalFrame.get(0);
/*  6201: 6816 */         localObject8 = localBPWServicesBean
/*  6202: 6817 */           .getRecWireTransfer(
/*  6203: 6818 */           str17);
/*  6204:      */         
/*  6205: 6820 */         localLocalFrame.setResult(localObject8);
/*  6206:      */       }
/*  6207:      */       catch (Throwable localThrowable41)
/*  6208:      */       {
/*  6209: 6824 */         localThrowable41.printStackTrace(Jaguar.log);
/*  6210: 6825 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable41, true);
/*  6211: 6826 */         return localThrowable41.getClass().getName();
/*  6212:      */       }
/*  6213:      */     case 41: 
/*  6214:      */       try
/*  6215:      */       {
/*  6216:      */         String[] arrayOfString7;
/*  6217: 6835 */         if (!bool1)
/*  6218:      */         {
/*  6219: 6837 */           arrayOfString7 = (String[])localLocalFrame.get(0);
/*  6220:      */         }
/*  6221:      */         else
/*  6222:      */         {
/*  6223: 6841 */           localObject8 = localLocalFrame.get(0);
/*  6224: 6842 */           arrayOfString7 = (String[])ObjectVal.clone(localObject8);
/*  6225:      */         }
/*  6226: 6844 */         localObject8 = 
/*  6227: 6845 */           localBPWServicesBean.getRecWireTransfers(
/*  6228: 6846 */           arrayOfString7);
/*  6229:      */         
/*  6230: 6848 */         localLocalFrame.setResult(localObject8);
/*  6231:      */       }
/*  6232:      */       catch (Throwable localThrowable42)
/*  6233:      */       {
/*  6234: 6852 */         localThrowable42.printStackTrace(Jaguar.log);
/*  6235: 6853 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable42, true);
/*  6236: 6854 */         return localThrowable42.getClass().getName();
/*  6237:      */       }
/*  6238:      */     case 42: 
/*  6239:      */       try
/*  6240:      */       {
/*  6241:      */         BPWHist localBPWHist4;
/*  6242: 6863 */         if (!bool1)
/*  6243:      */         {
/*  6244: 6865 */           localBPWHist4 = (BPWHist)localLocalFrame.get(0);
/*  6245:      */         }
/*  6246:      */         else
/*  6247:      */         {
/*  6248: 6869 */           localObject8 = localLocalFrame.get(0);
/*  6249: 6870 */           localBPWHist4 = (BPWHist)ObjectVal.clone(localObject8);
/*  6250:      */         }
/*  6251: 6872 */         localObject8 = 
/*  6252: 6873 */           localBPWServicesBean.getRecWireHistory(
/*  6253: 6874 */           localBPWHist4);
/*  6254:      */         
/*  6255: 6876 */         localLocalFrame.setResult(localObject8);
/*  6256:      */       }
/*  6257:      */       catch (Throwable localThrowable43)
/*  6258:      */       {
/*  6259: 6880 */         localThrowable43.printStackTrace(Jaguar.log);
/*  6260: 6881 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable43, true);
/*  6261: 6882 */         return localThrowable43.getClass().getName();
/*  6262:      */       }
/*  6263:      */     case 43: 
/*  6264:      */       try
/*  6265:      */       {
/*  6266:      */         BPWHist localBPWHist5;
/*  6267: 6891 */         if (!bool1)
/*  6268:      */         {
/*  6269: 6893 */           localBPWHist5 = (BPWHist)localLocalFrame.get(0);
/*  6270:      */         }
/*  6271:      */         else
/*  6272:      */         {
/*  6273: 6897 */           localObject8 = localLocalFrame.get(0);
/*  6274: 6898 */           localBPWHist5 = (BPWHist)ObjectVal.clone(localObject8);
/*  6275:      */         }
/*  6276: 6900 */         localObject8 = 
/*  6277: 6901 */           localBPWServicesBean.getRecWireHistoryByCustomer(
/*  6278: 6902 */           localBPWHist5);
/*  6279:      */         
/*  6280: 6904 */         localLocalFrame.setResult(localObject8);
/*  6281:      */       }
/*  6282:      */       catch (Throwable localThrowable44)
/*  6283:      */       {
/*  6284: 6908 */         localThrowable44.printStackTrace(Jaguar.log);
/*  6285: 6909 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable44, true);
/*  6286: 6910 */         return localThrowable44.getClass().getName();
/*  6287:      */       }
/*  6288:      */     case 44: 
/*  6289:      */       try
/*  6290:      */       {
/*  6291:      */         RecWireInfo[] arrayOfRecWireInfo;
/*  6292: 6919 */         if (!bool1)
/*  6293:      */         {
/*  6294: 6921 */           arrayOfRecWireInfo = (RecWireInfo[])localLocalFrame.get(0);
/*  6295:      */         }
/*  6296:      */         else
/*  6297:      */         {
/*  6298: 6925 */           localObject8 = localLocalFrame.get(0);
/*  6299: 6926 */           arrayOfRecWireInfo = (RecWireInfo[])ObjectVal.clone(localObject8);
/*  6300:      */         }
/*  6301: 6928 */         localObject8 = 
/*  6302: 6929 */           localBPWServicesBean.addRecWireTransfers(
/*  6303: 6930 */           arrayOfRecWireInfo);
/*  6304:      */         
/*  6305: 6932 */         localLocalFrame.setResult(localObject8);
/*  6306:      */       }
/*  6307:      */       catch (Throwable localThrowable45)
/*  6308:      */       {
/*  6309: 6936 */         localThrowable45.printStackTrace(Jaguar.log);
/*  6310: 6937 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable45, true);
/*  6311: 6938 */         return localThrowable45.getClass().getName();
/*  6312:      */       }
/*  6313:      */     case 45: 
/*  6314:      */       try
/*  6315:      */       {
/*  6316: 6946 */         HashMap localHashMap = localBPWServicesBean
/*  6317: 6947 */           .getWiresConfiguration();
/*  6318:      */         
/*  6319: 6949 */         localLocalFrame.setResult(localHashMap);
/*  6320:      */       }
/*  6321:      */       catch (Throwable localThrowable46)
/*  6322:      */       {
/*  6323: 6953 */         localThrowable46.printStackTrace(Jaguar.log);
/*  6324: 6954 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable46, true);
/*  6325: 6955 */         return localThrowable46.getClass().getName();
/*  6326:      */       }
/*  6327:      */     case 46: 
/*  6328:      */       try
/*  6329:      */       {
/*  6330:      */         WireBatchInfo localWireBatchInfo1;
/*  6331: 6964 */         if (!bool1)
/*  6332:      */         {
/*  6333: 6966 */           localWireBatchInfo1 = (WireBatchInfo)localLocalFrame.get(0);
/*  6334:      */         }
/*  6335:      */         else
/*  6336:      */         {
/*  6337: 6970 */           localObject8 = localLocalFrame.get(0);
/*  6338: 6971 */           localWireBatchInfo1 = (WireBatchInfo)ObjectVal.clone(localObject8);
/*  6339:      */         }
/*  6340: 6973 */         localObject8 = 
/*  6341: 6974 */           localBPWServicesBean.addWireTransferBatch(
/*  6342: 6975 */           localWireBatchInfo1);
/*  6343:      */         
/*  6344: 6977 */         localLocalFrame.setResult(localObject8);
/*  6345:      */       }
/*  6346:      */       catch (Throwable localThrowable47)
/*  6347:      */       {
/*  6348: 6981 */         localThrowable47.printStackTrace(Jaguar.log);
/*  6349: 6982 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable47, true);
/*  6350: 6983 */         return localThrowable47.getClass().getName();
/*  6351:      */       }
/*  6352:      */     case 47: 
/*  6353:      */       try
/*  6354:      */       {
/*  6355:      */         WireBatchInfo localWireBatchInfo2;
/*  6356: 6992 */         if (!bool1)
/*  6357:      */         {
/*  6358: 6994 */           localWireBatchInfo2 = (WireBatchInfo)localLocalFrame.get(0);
/*  6359:      */         }
/*  6360:      */         else
/*  6361:      */         {
/*  6362: 6998 */           localObject8 = localLocalFrame.get(0);
/*  6363: 6999 */           localWireBatchInfo2 = (WireBatchInfo)ObjectVal.clone(localObject8);
/*  6364:      */         }
/*  6365: 7001 */         localObject8 = 
/*  6366: 7002 */           localBPWServicesBean.modWireTransferBatch(
/*  6367: 7003 */           localWireBatchInfo2);
/*  6368:      */         
/*  6369: 7005 */         localLocalFrame.setResult(localObject8);
/*  6370:      */       }
/*  6371:      */       catch (Throwable localThrowable48)
/*  6372:      */       {
/*  6373: 7009 */         localThrowable48.printStackTrace(Jaguar.log);
/*  6374: 7010 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable48, true);
/*  6375: 7011 */         return localThrowable48.getClass().getName();
/*  6376:      */       }
/*  6377:      */     case 48: 
/*  6378:      */       try
/*  6379:      */       {
/*  6380:      */         WireBatchInfo localWireBatchInfo3;
/*  6381: 7020 */         if (!bool1)
/*  6382:      */         {
/*  6383: 7022 */           localWireBatchInfo3 = (WireBatchInfo)localLocalFrame.get(0);
/*  6384:      */         }
/*  6385:      */         else
/*  6386:      */         {
/*  6387: 7026 */           localObject8 = localLocalFrame.get(0);
/*  6388: 7027 */           localWireBatchInfo3 = (WireBatchInfo)ObjectVal.clone(localObject8);
/*  6389:      */         }
/*  6390: 7029 */         localObject8 = 
/*  6391: 7030 */           localBPWServicesBean.canWireTransferBatch(
/*  6392: 7031 */           localWireBatchInfo3);
/*  6393:      */         
/*  6394: 7033 */         localLocalFrame.setResult(localObject8);
/*  6395:      */       }
/*  6396:      */       catch (Throwable localThrowable49)
/*  6397:      */       {
/*  6398: 7037 */         localThrowable49.printStackTrace(Jaguar.log);
/*  6399: 7038 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable49, true);
/*  6400: 7039 */         return localThrowable49.getClass().getName();
/*  6401:      */       }
/*  6402:      */     case 49: 
/*  6403:      */       try
/*  6404:      */       {
/*  6405:      */         WireBatchInfo localWireBatchInfo4;
/*  6406: 7048 */         if (!bool1)
/*  6407:      */         {
/*  6408: 7050 */           localWireBatchInfo4 = (WireBatchInfo)localLocalFrame.get(0);
/*  6409:      */         }
/*  6410:      */         else
/*  6411:      */         {
/*  6412: 7054 */           localObject8 = localLocalFrame.get(0);
/*  6413: 7055 */           localWireBatchInfo4 = (WireBatchInfo)ObjectVal.clone(localObject8);
/*  6414:      */         }
/*  6415: 7057 */         localObject8 = 
/*  6416: 7058 */           localBPWServicesBean.getWireTransferBatch(
/*  6417: 7059 */           localWireBatchInfo4);
/*  6418:      */         
/*  6419: 7061 */         localLocalFrame.setResult(localObject8);
/*  6420:      */       }
/*  6421:      */       catch (Throwable localThrowable50)
/*  6422:      */       {
/*  6423: 7065 */         localThrowable50.printStackTrace(Jaguar.log);
/*  6424: 7066 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable50, true);
/*  6425: 7067 */         return localThrowable50.getClass().getName();
/*  6426:      */       }
/*  6427:      */     case 50: 
/*  6428:      */       try
/*  6429:      */       {
/*  6430:      */         BPWHist localBPWHist6;
/*  6431: 7076 */         if (!bool1)
/*  6432:      */         {
/*  6433: 7078 */           localBPWHist6 = (BPWHist)localLocalFrame.get(0);
/*  6434:      */         }
/*  6435:      */         else
/*  6436:      */         {
/*  6437: 7082 */           localObject8 = localLocalFrame.get(0);
/*  6438: 7083 */           localBPWHist6 = (BPWHist)ObjectVal.clone(localObject8);
/*  6439:      */         }
/*  6440: 7085 */         localObject8 = 
/*  6441: 7086 */           localBPWServicesBean.getWireBatchHistory(
/*  6442: 7087 */           localBPWHist6);
/*  6443:      */         
/*  6444: 7089 */         localLocalFrame.setResult(localObject8);
/*  6445:      */       }
/*  6446:      */       catch (Throwable localThrowable51)
/*  6447:      */       {
/*  6448: 7093 */         localThrowable51.printStackTrace(Jaguar.log);
/*  6449: 7094 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable51, true);
/*  6450: 7095 */         return localThrowable51.getClass().getName();
/*  6451:      */       }
/*  6452:      */     default: 
/*  6453: 7101 */       return 
/*  6454: 7102 */         localInvoke1(
/*  6455: 7103 */         local_ServerRequest, 
/*  6456: 7104 */         paramInputStream, 
/*  6457: 7105 */         paramOutputStream, 
/*  6458: 7106 */         localBPWServicesBean, 
/*  6459: 7107 */         localLocalFrame, 
/*  6460: 7108 */         localInteger, 
/*  6461: 7109 */         bool1);
/*  6462:      */     }
/*  6463: 7113 */     return null;
/*  6464:      */   }
/*  6465:      */   
/*  6466:      */   private static String localInvoke1(_ServerRequest param_ServerRequest, InputStream paramInputStream, OutputStream paramOutputStream, BPWServicesBean paramBPWServicesBean, LocalFrame paramLocalFrame, Integer paramInteger, boolean paramBoolean)
/*  6467:      */   {
/*  6468:      */     Object localObject1;
/*  6469:      */     Object localObject6;
/*  6470:      */     Object localObject7;
/*  6471:      */     Object localObject2;
/*  6472:      */     Object localObject5;
/*  6473: 7125 */     switch (paramInteger.intValue())
/*  6474:      */     {
/*  6475:      */     case 51: 
/*  6476:      */       try
/*  6477:      */       {
/*  6478: 7132 */         String str1 = (String)paramLocalFrame.get(0);
/*  6479: 7133 */         boolean bool = paramBPWServicesBean
/*  6480: 7134 */           .isWireBatchDeleteable(
/*  6481: 7135 */           str1);
/*  6482:      */         
/*  6483: 7137 */         paramLocalFrame.setResult(bool);
/*  6484:      */       }
/*  6485:      */       catch (Throwable localThrowable1)
/*  6486:      */       {
/*  6487: 7141 */         localThrowable1.printStackTrace(Jaguar.log);
/*  6488: 7142 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/*  6489: 7143 */         return localThrowable1.getClass().getName();
/*  6490:      */       }
/*  6491:      */     case 52: 
/*  6492:      */       try
/*  6493:      */       {
/*  6494:      */         WirePayeeInfo[] arrayOfWirePayeeInfo;
/*  6495: 7152 */         if (!paramBoolean)
/*  6496:      */         {
/*  6497: 7154 */           arrayOfWirePayeeInfo = (WirePayeeInfo[])paramLocalFrame.get(0);
/*  6498:      */         }
/*  6499:      */         else
/*  6500:      */         {
/*  6501: 7158 */           localObject1 = paramLocalFrame.get(0);
/*  6502: 7159 */           arrayOfWirePayeeInfo = (WirePayeeInfo[])ObjectVal.clone(localObject1);
/*  6503:      */         }
/*  6504: 7161 */         localObject1 = 
/*  6505: 7162 */           paramBPWServicesBean.addWirePayee(
/*  6506: 7163 */           arrayOfWirePayeeInfo);
/*  6507:      */         
/*  6508: 7165 */         paramLocalFrame.setResult(localObject1);
/*  6509:      */       }
/*  6510:      */       catch (Throwable localThrowable2)
/*  6511:      */       {
/*  6512: 7169 */         localThrowable2.printStackTrace(Jaguar.log);
/*  6513: 7170 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/*  6514: 7171 */         return localThrowable2.getClass().getName();
/*  6515:      */       }
/*  6516:      */     case 53: 
/*  6517:      */       try
/*  6518:      */       {
/*  6519:      */         WirePayeeInfo localWirePayeeInfo1;
/*  6520: 7180 */         if (!paramBoolean)
/*  6521:      */         {
/*  6522: 7182 */           localWirePayeeInfo1 = (WirePayeeInfo)paramLocalFrame.get(0);
/*  6523:      */         }
/*  6524:      */         else
/*  6525:      */         {
/*  6526: 7186 */           localObject1 = paramLocalFrame.get(0);
/*  6527: 7187 */           localWirePayeeInfo1 = (WirePayeeInfo)ObjectVal.clone(localObject1);
/*  6528:      */         }
/*  6529: 7189 */         localObject1 = 
/*  6530: 7190 */           paramBPWServicesBean.addWirePayee(
/*  6531: 7191 */           localWirePayeeInfo1);
/*  6532:      */         
/*  6533: 7193 */         paramLocalFrame.setResult(localObject1);
/*  6534:      */       }
/*  6535:      */       catch (Throwable localThrowable3)
/*  6536:      */       {
/*  6537: 7197 */         localThrowable3.printStackTrace(Jaguar.log);
/*  6538: 7198 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/*  6539: 7199 */         return localThrowable3.getClass().getName();
/*  6540:      */       }
/*  6541:      */     case 54: 
/*  6542:      */       try
/*  6543:      */       {
/*  6544:      */         WirePayeeInfo localWirePayeeInfo2;
/*  6545: 7208 */         if (!paramBoolean)
/*  6546:      */         {
/*  6547: 7210 */           localWirePayeeInfo2 = (WirePayeeInfo)paramLocalFrame.get(0);
/*  6548:      */         }
/*  6549:      */         else
/*  6550:      */         {
/*  6551: 7214 */           localObject1 = paramLocalFrame.get(0);
/*  6552: 7215 */           localWirePayeeInfo2 = (WirePayeeInfo)ObjectVal.clone(localObject1);
/*  6553:      */         }
/*  6554: 7217 */         localObject1 = 
/*  6555: 7218 */           paramBPWServicesBean.modWirePayee(
/*  6556: 7219 */           localWirePayeeInfo2);
/*  6557:      */         
/*  6558: 7221 */         paramLocalFrame.setResult(localObject1);
/*  6559:      */       }
/*  6560:      */       catch (Throwable localThrowable4)
/*  6561:      */       {
/*  6562: 7225 */         localThrowable4.printStackTrace(Jaguar.log);
/*  6563: 7226 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/*  6564: 7227 */         return localThrowable4.getClass().getName();
/*  6565:      */       }
/*  6566:      */     case 55: 
/*  6567:      */       try
/*  6568:      */       {
/*  6569:      */         WirePayeeInfo localWirePayeeInfo3;
/*  6570: 7236 */         if (!paramBoolean)
/*  6571:      */         {
/*  6572: 7238 */           localWirePayeeInfo3 = (WirePayeeInfo)paramLocalFrame.get(0);
/*  6573:      */         }
/*  6574:      */         else
/*  6575:      */         {
/*  6576: 7242 */           localObject1 = paramLocalFrame.get(0);
/*  6577: 7243 */           localWirePayeeInfo3 = (WirePayeeInfo)ObjectVal.clone(localObject1);
/*  6578:      */         }
/*  6579: 7245 */         localObject1 = 
/*  6580: 7246 */           paramBPWServicesBean.canWirePayee(
/*  6581: 7247 */           localWirePayeeInfo3);
/*  6582:      */         
/*  6583: 7249 */         paramLocalFrame.setResult(localObject1);
/*  6584:      */       }
/*  6585:      */       catch (Throwable localThrowable5)
/*  6586:      */       {
/*  6587: 7253 */         localThrowable5.printStackTrace(Jaguar.log);
/*  6588: 7254 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/*  6589: 7255 */         return localThrowable5.getClass().getName();
/*  6590:      */       }
/*  6591:      */     case 56: 
/*  6592:      */       try
/*  6593:      */       {
/*  6594:      */         WirePayeeInfo localWirePayeeInfo4;
/*  6595: 7264 */         if (!paramBoolean)
/*  6596:      */         {
/*  6597: 7266 */           localWirePayeeInfo4 = (WirePayeeInfo)paramLocalFrame.get(0);
/*  6598:      */         }
/*  6599:      */         else
/*  6600:      */         {
/*  6601: 7270 */           localObject1 = paramLocalFrame.get(0);
/*  6602: 7271 */           localWirePayeeInfo4 = (WirePayeeInfo)ObjectVal.clone(localObject1);
/*  6603:      */         }
/*  6604: 7273 */         localObject1 = 
/*  6605: 7274 */           paramBPWServicesBean.getWirePayee(
/*  6606: 7275 */           localWirePayeeInfo4);
/*  6607:      */         
/*  6608: 7277 */         paramLocalFrame.setResult(localObject1);
/*  6609:      */       }
/*  6610:      */       catch (Throwable localThrowable6)
/*  6611:      */       {
/*  6612: 7281 */         localThrowable6.printStackTrace(Jaguar.log);
/*  6613: 7282 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/*  6614: 7283 */         return localThrowable6.getClass().getName();
/*  6615:      */       }
/*  6616:      */     case 57: 
/*  6617:      */       try
/*  6618:      */       {
/*  6619: 7292 */         String str2 = (String)paramLocalFrame.get(0);
/*  6620: 7293 */         localObject1 = paramBPWServicesBean
/*  6621: 7294 */           .getWirePayee(
/*  6622: 7295 */           str2);
/*  6623:      */         
/*  6624: 7297 */         paramLocalFrame.setResult(localObject1);
/*  6625:      */       }
/*  6626:      */       catch (Throwable localThrowable7)
/*  6627:      */       {
/*  6628: 7301 */         localThrowable7.printStackTrace(Jaguar.log);
/*  6629: 7302 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/*  6630: 7303 */         return localThrowable7.getClass().getName();
/*  6631:      */       }
/*  6632:      */     case 58: 
/*  6633:      */       try
/*  6634:      */       {
/*  6635:      */         BPWPayeeList localBPWPayeeList1;
/*  6636: 7312 */         if (!paramBoolean)
/*  6637:      */         {
/*  6638: 7314 */           localBPWPayeeList1 = (BPWPayeeList)paramLocalFrame.get(0);
/*  6639:      */         }
/*  6640:      */         else
/*  6641:      */         {
/*  6642: 7318 */           localObject1 = paramLocalFrame.get(0);
/*  6643: 7319 */           localBPWPayeeList1 = (BPWPayeeList)ObjectVal.clone(localObject1);
/*  6644:      */         }
/*  6645: 7321 */         localObject1 = 
/*  6646: 7322 */           paramBPWServicesBean.getWirePayeeByType(
/*  6647: 7323 */           localBPWPayeeList1);
/*  6648:      */         
/*  6649: 7325 */         paramLocalFrame.setResult(localObject1);
/*  6650:      */       }
/*  6651:      */       catch (Throwable localThrowable8)
/*  6652:      */       {
/*  6653: 7329 */         localThrowable8.printStackTrace(Jaguar.log);
/*  6654: 7330 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/*  6655: 7331 */         return localThrowable8.getClass().getName();
/*  6656:      */       }
/*  6657:      */     case 59: 
/*  6658:      */       try
/*  6659:      */       {
/*  6660:      */         BPWPayeeList localBPWPayeeList2;
/*  6661: 7340 */         if (!paramBoolean)
/*  6662:      */         {
/*  6663: 7342 */           localBPWPayeeList2 = (BPWPayeeList)paramLocalFrame.get(0);
/*  6664:      */         }
/*  6665:      */         else
/*  6666:      */         {
/*  6667: 7346 */           localObject1 = paramLocalFrame.get(0);
/*  6668: 7347 */           localBPWPayeeList2 = (BPWPayeeList)ObjectVal.clone(localObject1);
/*  6669:      */         }
/*  6670: 7349 */         localObject1 = 
/*  6671: 7350 */           paramBPWServicesBean.getWirePayeeByStatus(
/*  6672: 7351 */           localBPWPayeeList2);
/*  6673:      */         
/*  6674: 7353 */         paramLocalFrame.setResult(localObject1);
/*  6675:      */       }
/*  6676:      */       catch (Throwable localThrowable9)
/*  6677:      */       {
/*  6678: 7357 */         localThrowable9.printStackTrace(Jaguar.log);
/*  6679: 7358 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/*  6680: 7359 */         return localThrowable9.getClass().getName();
/*  6681:      */       }
/*  6682:      */     case 60: 
/*  6683:      */       try
/*  6684:      */       {
/*  6685:      */         BPWPayeeList localBPWPayeeList3;
/*  6686: 7368 */         if (!paramBoolean)
/*  6687:      */         {
/*  6688: 7370 */           localBPWPayeeList3 = (BPWPayeeList)paramLocalFrame.get(0);
/*  6689:      */         }
/*  6690:      */         else
/*  6691:      */         {
/*  6692: 7374 */           localObject1 = paramLocalFrame.get(0);
/*  6693: 7375 */           localBPWPayeeList3 = (BPWPayeeList)ObjectVal.clone(localObject1);
/*  6694:      */         }
/*  6695: 7377 */         localObject1 = 
/*  6696: 7378 */           paramBPWServicesBean.getWirePayeeByGroup(
/*  6697: 7379 */           localBPWPayeeList3);
/*  6698:      */         
/*  6699: 7381 */         paramLocalFrame.setResult(localObject1);
/*  6700:      */       }
/*  6701:      */       catch (Throwable localThrowable10)
/*  6702:      */       {
/*  6703: 7385 */         localThrowable10.printStackTrace(Jaguar.log);
/*  6704: 7386 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/*  6705: 7387 */         return localThrowable10.getClass().getName();
/*  6706:      */       }
/*  6707:      */     case 61: 
/*  6708:      */       try
/*  6709:      */       {
/*  6710:      */         BPWPayeeList localBPWPayeeList4;
/*  6711: 7396 */         if (!paramBoolean)
/*  6712:      */         {
/*  6713: 7398 */           localBPWPayeeList4 = (BPWPayeeList)paramLocalFrame.get(0);
/*  6714:      */         }
/*  6715:      */         else
/*  6716:      */         {
/*  6717: 7402 */           localObject1 = paramLocalFrame.get(0);
/*  6718: 7403 */           localBPWPayeeList4 = (BPWPayeeList)ObjectVal.clone(localObject1);
/*  6719:      */         }
/*  6720: 7405 */         localObject1 = 
/*  6721: 7406 */           paramBPWServicesBean.getWirePayeeByCustomer(
/*  6722: 7407 */           localBPWPayeeList4);
/*  6723:      */         
/*  6724: 7409 */         paramLocalFrame.setResult(localObject1);
/*  6725:      */       }
/*  6726:      */       catch (Throwable localThrowable11)
/*  6727:      */       {
/*  6728: 7413 */         localThrowable11.printStackTrace(Jaguar.log);
/*  6729: 7414 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/*  6730: 7415 */         return localThrowable11.getClass().getName();
/*  6731:      */       }
/*  6732:      */     case 62: 
/*  6733:      */       try
/*  6734:      */       {
/*  6735: 7424 */         String str3 = (String)paramLocalFrame.get(0);
/*  6736: 7426 */         if (!paramBoolean)
/*  6737:      */         {
/*  6738: 7428 */           localObject1 = (BPWBankInfo[])paramLocalFrame.get(1);
/*  6739:      */         }
/*  6740:      */         else
/*  6741:      */         {
/*  6742: 7432 */           Object localObject3 = paramLocalFrame.get(1);
/*  6743: 7433 */           localObject1 = (BPWBankInfo[])ObjectVal.clone(localObject3);
/*  6744:      */         }
/*  6745: 7435 */         int j = paramBPWServicesBean
/*  6746: 7436 */           .addIntermediaryBanksToBeneficiary(
/*  6747: 7437 */           str3, 
/*  6748: 7438 */           (BPWBankInfo[])localObject1);
/*  6749:      */         
/*  6750: 7440 */         paramLocalFrame.setResult(j);
/*  6751:      */       }
/*  6752:      */       catch (Throwable localThrowable12)
/*  6753:      */       {
/*  6754: 7444 */         localThrowable12.printStackTrace(Jaguar.log);
/*  6755: 7445 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/*  6756: 7446 */         return localThrowable12.getClass().getName();
/*  6757:      */       }
/*  6758:      */     case 63: 
/*  6759:      */       try
/*  6760:      */       {
/*  6761: 7455 */         String str4 = (String)paramLocalFrame.get(0);
/*  6762: 7457 */         if (!paramBoolean)
/*  6763:      */         {
/*  6764: 7459 */           localObject1 = (BPWBankInfo[])paramLocalFrame.get(1);
/*  6765:      */         }
/*  6766:      */         else
/*  6767:      */         {
/*  6768: 7463 */           Object localObject4 = paramLocalFrame.get(1);
/*  6769: 7464 */           localObject1 = (BPWBankInfo[])ObjectVal.clone(localObject4);
/*  6770:      */         }
/*  6771: 7466 */         int k = paramBPWServicesBean
/*  6772: 7467 */           .delIntermediaryBanksFromBeneficiary(
/*  6773: 7468 */           str4, 
/*  6774: 7469 */           (BPWBankInfo[])localObject1);
/*  6775:      */         
/*  6776: 7471 */         paramLocalFrame.setResult(k);
/*  6777:      */       }
/*  6778:      */       catch (Throwable localThrowable13)
/*  6779:      */       {
/*  6780: 7475 */         localThrowable13.printStackTrace(Jaguar.log);
/*  6781: 7476 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/*  6782: 7477 */         return localThrowable13.getClass().getName();
/*  6783:      */       }
/*  6784:      */     case 64: 
/*  6785:      */       try
/*  6786:      */       {
/*  6787:      */         BPWBankInfo[] arrayOfBPWBankInfo1;
/*  6788: 7486 */         if (!paramBoolean)
/*  6789:      */         {
/*  6790: 7488 */           arrayOfBPWBankInfo1 = (BPWBankInfo[])paramLocalFrame.get(0);
/*  6791:      */         }
/*  6792:      */         else
/*  6793:      */         {
/*  6794: 7492 */           localObject1 = paramLocalFrame.get(0);
/*  6795: 7493 */           arrayOfBPWBankInfo1 = (BPWBankInfo[])ObjectVal.clone(localObject1);
/*  6796:      */         }
/*  6797: 7495 */         localObject1 = 
/*  6798: 7496 */           paramBPWServicesBean.addWireBanks(
/*  6799: 7497 */           arrayOfBPWBankInfo1);
/*  6800:      */         
/*  6801: 7499 */         paramLocalFrame.setResult(localObject1);
/*  6802:      */       }
/*  6803:      */       catch (Throwable localThrowable14)
/*  6804:      */       {
/*  6805: 7503 */         localThrowable14.printStackTrace(Jaguar.log);
/*  6806: 7504 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/*  6807: 7505 */         return localThrowable14.getClass().getName();
/*  6808:      */       }
/*  6809:      */     case 65: 
/*  6810:      */       try
/*  6811:      */       {
/*  6812:      */         BPWBankInfo[] arrayOfBPWBankInfo2;
/*  6813: 7514 */         if (!paramBoolean)
/*  6814:      */         {
/*  6815: 7516 */           arrayOfBPWBankInfo2 = (BPWBankInfo[])paramLocalFrame.get(0);
/*  6816:      */         }
/*  6817:      */         else
/*  6818:      */         {
/*  6819: 7520 */           localObject1 = paramLocalFrame.get(0);
/*  6820: 7521 */           arrayOfBPWBankInfo2 = (BPWBankInfo[])ObjectVal.clone(localObject1);
/*  6821:      */         }
/*  6822: 7523 */         localObject1 = 
/*  6823: 7524 */           paramBPWServicesBean.modWireBanks(
/*  6824: 7525 */           arrayOfBPWBankInfo2);
/*  6825:      */         
/*  6826: 7527 */         paramLocalFrame.setResult(localObject1);
/*  6827:      */       }
/*  6828:      */       catch (Throwable localThrowable15)
/*  6829:      */       {
/*  6830: 7531 */         localThrowable15.printStackTrace(Jaguar.log);
/*  6831: 7532 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/*  6832: 7533 */         return localThrowable15.getClass().getName();
/*  6833:      */       }
/*  6834:      */     case 66: 
/*  6835:      */       try
/*  6836:      */       {
/*  6837:      */         BPWBankInfo[] arrayOfBPWBankInfo3;
/*  6838: 7542 */         if (!paramBoolean)
/*  6839:      */         {
/*  6840: 7544 */           arrayOfBPWBankInfo3 = (BPWBankInfo[])paramLocalFrame.get(0);
/*  6841:      */         }
/*  6842:      */         else
/*  6843:      */         {
/*  6844: 7548 */           localObject1 = paramLocalFrame.get(0);
/*  6845: 7549 */           arrayOfBPWBankInfo3 = (BPWBankInfo[])ObjectVal.clone(localObject1);
/*  6846:      */         }
/*  6847: 7551 */         localObject1 = 
/*  6848: 7552 */           paramBPWServicesBean.delWireBanks(
/*  6849: 7553 */           arrayOfBPWBankInfo3);
/*  6850:      */         
/*  6851: 7555 */         paramLocalFrame.setResult(localObject1);
/*  6852:      */       }
/*  6853:      */       catch (Throwable localThrowable16)
/*  6854:      */       {
/*  6855: 7559 */         localThrowable16.printStackTrace(Jaguar.log);
/*  6856: 7560 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/*  6857: 7561 */         return localThrowable16.getClass().getName();
/*  6858:      */       }
/*  6859:      */     case 67: 
/*  6860:      */       try
/*  6861:      */       {
/*  6862: 7570 */         String str5 = (String)paramLocalFrame.get(0);
/*  6863:      */         
/*  6864: 7572 */         localObject1 = (String)paramLocalFrame.get(1);
/*  6865:      */         
/*  6866: 7574 */         String str20 = (String)paramLocalFrame.get(2);
/*  6867:      */         
/*  6868: 7576 */         localObject6 = (String)paramLocalFrame.get(3);
/*  6869: 7577 */         localObject7 = paramBPWServicesBean
/*  6870: 7578 */           .getWireBanks(
/*  6871: 7579 */           str5, 
/*  6872: 7580 */           (String)localObject1, 
/*  6873: 7581 */           str20, 
/*  6874: 7582 */           (String)localObject6);
/*  6875:      */         
/*  6876: 7584 */         paramLocalFrame.setResult(localObject7);
/*  6877:      */       }
/*  6878:      */       catch (Throwable localThrowable17)
/*  6879:      */       {
/*  6880: 7588 */         localThrowable17.printStackTrace(Jaguar.log);
/*  6881: 7589 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/*  6882: 7590 */         return localThrowable17.getClass().getName();
/*  6883:      */       }
/*  6884:      */     case 68: 
/*  6885:      */       try
/*  6886:      */       {
/*  6887: 7599 */         String str6 = (String)paramLocalFrame.get(0);
/*  6888: 7600 */         localObject1 = paramBPWServicesBean
/*  6889: 7601 */           .getWireBanksByRTN(
/*  6890: 7602 */           str6);
/*  6891:      */         
/*  6892: 7604 */         paramLocalFrame.setResult(localObject1);
/*  6893:      */       }
/*  6894:      */       catch (Throwable localThrowable18)
/*  6895:      */       {
/*  6896: 7608 */         localThrowable18.printStackTrace(Jaguar.log);
/*  6897: 7609 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/*  6898: 7610 */         return localThrowable18.getClass().getName();
/*  6899:      */       }
/*  6900:      */     case 69: 
/*  6901:      */       try
/*  6902:      */       {
/*  6903: 7619 */         String str7 = (String)paramLocalFrame.get(0);
/*  6904: 7620 */         localObject1 = paramBPWServicesBean
/*  6905: 7621 */           .getWireBanksByID(
/*  6906: 7622 */           str7);
/*  6907:      */         
/*  6908: 7624 */         paramLocalFrame.setResult(localObject1);
/*  6909:      */       }
/*  6910:      */       catch (Throwable localThrowable19)
/*  6911:      */       {
/*  6912: 7628 */         localThrowable19.printStackTrace(Jaguar.log);
/*  6913: 7629 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/*  6914: 7630 */         return localThrowable19.getClass().getName();
/*  6915:      */       }
/*  6916:      */     case 70: 
/*  6917:      */       try
/*  6918:      */       {
/*  6919:      */         WireInfo[] arrayOfWireInfo1;
/*  6920: 7639 */         if (!paramBoolean)
/*  6921:      */         {
/*  6922: 7641 */           arrayOfWireInfo1 = (WireInfo[])paramLocalFrame.get(0);
/*  6923:      */         }
/*  6924:      */         else
/*  6925:      */         {
/*  6926: 7645 */           localObject1 = paramLocalFrame.get(0);
/*  6927: 7646 */           arrayOfWireInfo1 = (WireInfo[])ObjectVal.clone(localObject1);
/*  6928:      */         }
/*  6929: 7649 */         paramBPWServicesBean.processWireApprovalRslt(
/*  6930: 7650 */           arrayOfWireInfo1);
/*  6931:      */       }
/*  6932:      */       catch (Throwable localThrowable20)
/*  6933:      */       {
/*  6934: 7655 */         localThrowable20.printStackTrace(Jaguar.log);
/*  6935: 7656 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/*  6936: 7657 */         return localThrowable20.getClass().getName();
/*  6937:      */       }
/*  6938:      */     case 71: 
/*  6939:      */       try
/*  6940:      */       {
/*  6941:      */         WireInfo[] arrayOfWireInfo2;
/*  6942: 7666 */         if (!paramBoolean)
/*  6943:      */         {
/*  6944: 7668 */           arrayOfWireInfo2 = (WireInfo[])paramLocalFrame.get(0);
/*  6945:      */         }
/*  6946:      */         else
/*  6947:      */         {
/*  6948: 7672 */           localObject1 = paramLocalFrame.get(0);
/*  6949: 7673 */           arrayOfWireInfo2 = (WireInfo[])ObjectVal.clone(localObject1);
/*  6950:      */         }
/*  6951: 7676 */         paramBPWServicesBean.processWireBackendlRslt(
/*  6952: 7677 */           arrayOfWireInfo2);
/*  6953:      */       }
/*  6954:      */       catch (Throwable localThrowable21)
/*  6955:      */       {
/*  6956: 7682 */         localThrowable21.printStackTrace(Jaguar.log);
/*  6957: 7683 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/*  6958: 7684 */         return localThrowable21.getClass().getName();
/*  6959:      */       }
/*  6960:      */     case 72: 
/*  6961:      */       try
/*  6962:      */       {
/*  6963:      */         WireInfo[] arrayOfWireInfo3;
/*  6964: 7693 */         if (!paramBoolean)
/*  6965:      */         {
/*  6966: 7695 */           arrayOfWireInfo3 = (WireInfo[])paramLocalFrame.get(0);
/*  6967:      */         }
/*  6968:      */         else
/*  6969:      */         {
/*  6970: 7699 */           localObject1 = paramLocalFrame.get(0);
/*  6971: 7700 */           arrayOfWireInfo3 = (WireInfo[])ObjectVal.clone(localObject1);
/*  6972:      */         }
/*  6973: 7703 */         paramBPWServicesBean.processWireApprovalRevertRslt(
/*  6974: 7704 */           arrayOfWireInfo3);
/*  6975:      */       }
/*  6976:      */       catch (Throwable localThrowable22)
/*  6977:      */       {
/*  6978: 7709 */         localThrowable22.printStackTrace(Jaguar.log);
/*  6979: 7710 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/*  6980: 7711 */         return localThrowable22.getClass().getName();
/*  6981:      */       }
/*  6982:      */     case 73: 
/*  6983:      */       try
/*  6984:      */       {
/*  6985:      */         BPWFIInfo localBPWFIInfo1;
/*  6986: 7720 */         if (!paramBoolean)
/*  6987:      */         {
/*  6988: 7722 */           localBPWFIInfo1 = (BPWFIInfo)paramLocalFrame.get(0);
/*  6989:      */         }
/*  6990:      */         else
/*  6991:      */         {
/*  6992: 7726 */           localObject1 = paramLocalFrame.get(0);
/*  6993: 7727 */           localBPWFIInfo1 = (BPWFIInfo)ObjectVal.clone(localObject1);
/*  6994:      */         }
/*  6995: 7729 */         localObject1 = 
/*  6996: 7730 */           paramBPWServicesBean.addBPWFIInfo(
/*  6997: 7731 */           localBPWFIInfo1);
/*  6998:      */         
/*  6999: 7733 */         paramLocalFrame.setResult(localObject1);
/*  7000:      */       }
/*  7001:      */       catch (Throwable localThrowable23)
/*  7002:      */       {
/*  7003: 7737 */         localThrowable23.printStackTrace(Jaguar.log);
/*  7004: 7738 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/*  7005: 7739 */         return localThrowable23.getClass().getName();
/*  7006:      */       }
/*  7007:      */     case 74: 
/*  7008:      */       try
/*  7009:      */       {
/*  7010:      */         BPWFIInfo localBPWFIInfo2;
/*  7011: 7748 */         if (!paramBoolean)
/*  7012:      */         {
/*  7013: 7750 */           localBPWFIInfo2 = (BPWFIInfo)paramLocalFrame.get(0);
/*  7014:      */         }
/*  7015:      */         else
/*  7016:      */         {
/*  7017: 7754 */           localObject1 = paramLocalFrame.get(0);
/*  7018: 7755 */           localBPWFIInfo2 = (BPWFIInfo)ObjectVal.clone(localObject1);
/*  7019:      */         }
/*  7020: 7757 */         localObject1 = 
/*  7021: 7758 */           paramBPWServicesBean.modBPWFIInfo(
/*  7022: 7759 */           localBPWFIInfo2);
/*  7023:      */         
/*  7024: 7761 */         paramLocalFrame.setResult(localObject1);
/*  7025:      */       }
/*  7026:      */       catch (Throwable localThrowable24)
/*  7027:      */       {
/*  7028: 7765 */         localThrowable24.printStackTrace(Jaguar.log);
/*  7029: 7766 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/*  7030: 7767 */         return localThrowable24.getClass().getName();
/*  7031:      */       }
/*  7032:      */     case 75: 
/*  7033:      */       try
/*  7034:      */       {
/*  7035:      */         BPWFIInfo localBPWFIInfo3;
/*  7036: 7776 */         if (!paramBoolean)
/*  7037:      */         {
/*  7038: 7778 */           localBPWFIInfo3 = (BPWFIInfo)paramLocalFrame.get(0);
/*  7039:      */         }
/*  7040:      */         else
/*  7041:      */         {
/*  7042: 7782 */           localObject1 = paramLocalFrame.get(0);
/*  7043: 7783 */           localBPWFIInfo3 = (BPWFIInfo)ObjectVal.clone(localObject1);
/*  7044:      */         }
/*  7045: 7785 */         localObject1 = 
/*  7046: 7786 */           paramBPWServicesBean.canBPWFIInfo(
/*  7047: 7787 */           localBPWFIInfo3);
/*  7048:      */         
/*  7049: 7789 */         paramLocalFrame.setResult(localObject1);
/*  7050:      */       }
/*  7051:      */       catch (Throwable localThrowable25)
/*  7052:      */       {
/*  7053: 7793 */         localThrowable25.printStackTrace(Jaguar.log);
/*  7054: 7794 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/*  7055: 7795 */         return localThrowable25.getClass().getName();
/*  7056:      */       }
/*  7057:      */     case 76: 
/*  7058:      */       try
/*  7059:      */       {
/*  7060: 7804 */         String str8 = (String)paramLocalFrame.get(0);
/*  7061: 7805 */         localObject1 = paramBPWServicesBean
/*  7062: 7806 */           .activateBPWFI(
/*  7063: 7807 */           str8);
/*  7064:      */         
/*  7065: 7809 */         paramLocalFrame.setResult(localObject1);
/*  7066:      */       }
/*  7067:      */       catch (Throwable localThrowable26)
/*  7068:      */       {
/*  7069: 7813 */         localThrowable26.printStackTrace(Jaguar.log);
/*  7070: 7814 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/*  7071: 7815 */         return localThrowable26.getClass().getName();
/*  7072:      */       }
/*  7073:      */     case 77: 
/*  7074:      */       try
/*  7075:      */       {
/*  7076: 7824 */         String str9 = (String)paramLocalFrame.get(0);
/*  7077: 7825 */         localObject1 = paramBPWServicesBean
/*  7078: 7826 */           .getBPWFIInfo(
/*  7079: 7827 */           str9);
/*  7080:      */         
/*  7081: 7829 */         paramLocalFrame.setResult(localObject1);
/*  7082:      */       }
/*  7083:      */       catch (Throwable localThrowable27)
/*  7084:      */       {
/*  7085: 7833 */         localThrowable27.printStackTrace(Jaguar.log);
/*  7086: 7834 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/*  7087: 7835 */         return localThrowable27.getClass().getName();
/*  7088:      */       }
/*  7089:      */     case 78: 
/*  7090:      */       try
/*  7091:      */       {
/*  7092:      */         String[] arrayOfString;
/*  7093: 7844 */         if (!paramBoolean)
/*  7094:      */         {
/*  7095: 7846 */           arrayOfString = (String[])paramLocalFrame.get(0);
/*  7096:      */         }
/*  7097:      */         else
/*  7098:      */         {
/*  7099: 7850 */           localObject1 = paramLocalFrame.get(0);
/*  7100: 7851 */           arrayOfString = (String[])ObjectVal.clone(localObject1);
/*  7101:      */         }
/*  7102: 7853 */         localObject1 = 
/*  7103: 7854 */           paramBPWServicesBean.getBPWFIInfo(
/*  7104: 7855 */           arrayOfString);
/*  7105:      */         
/*  7106: 7857 */         paramLocalFrame.setResult(localObject1);
/*  7107:      */       }
/*  7108:      */       catch (Throwable localThrowable28)
/*  7109:      */       {
/*  7110: 7861 */         localThrowable28.printStackTrace(Jaguar.log);
/*  7111: 7862 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/*  7112: 7863 */         return localThrowable28.getClass().getName();
/*  7113:      */       }
/*  7114:      */     case 79: 
/*  7115:      */       try
/*  7116:      */       {
/*  7117: 7872 */         String str10 = (String)paramLocalFrame.get(0);
/*  7118: 7873 */         localObject1 = paramBPWServicesBean
/*  7119: 7874 */           .getBPWFIInfoByType(
/*  7120: 7875 */           str10);
/*  7121:      */         
/*  7122: 7877 */         paramLocalFrame.setResult(localObject1);
/*  7123:      */       }
/*  7124:      */       catch (Throwable localThrowable29)
/*  7125:      */       {
/*  7126: 7881 */         localThrowable29.printStackTrace(Jaguar.log);
/*  7127: 7882 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/*  7128: 7883 */         return localThrowable29.getClass().getName();
/*  7129:      */       }
/*  7130:      */     case 80: 
/*  7131:      */       try
/*  7132:      */       {
/*  7133: 7892 */         String str11 = (String)paramLocalFrame.get(0);
/*  7134: 7893 */         localObject1 = paramBPWServicesBean
/*  7135: 7894 */           .getBPWFIInfoByStatus(
/*  7136: 7895 */           str11);
/*  7137:      */         
/*  7138: 7897 */         paramLocalFrame.setResult(localObject1);
/*  7139:      */       }
/*  7140:      */       catch (Throwable localThrowable30)
/*  7141:      */       {
/*  7142: 7901 */         localThrowable30.printStackTrace(Jaguar.log);
/*  7143: 7902 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/*  7144: 7903 */         return localThrowable30.getClass().getName();
/*  7145:      */       }
/*  7146:      */     case 81: 
/*  7147:      */       try
/*  7148:      */       {
/*  7149: 7912 */         String str12 = (String)paramLocalFrame.get(0);
/*  7150: 7913 */         localObject1 = paramBPWServicesBean
/*  7151: 7914 */           .getBPWFIInfoByGroup(
/*  7152: 7915 */           str12);
/*  7153:      */         
/*  7154: 7917 */         paramLocalFrame.setResult(localObject1);
/*  7155:      */       }
/*  7156:      */       catch (Throwable localThrowable31)
/*  7157:      */       {
/*  7158: 7921 */         localThrowable31.printStackTrace(Jaguar.log);
/*  7159: 7922 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/*  7160: 7923 */         return localThrowable31.getClass().getName();
/*  7161:      */       }
/*  7162:      */     case 82: 
/*  7163:      */       try
/*  7164:      */       {
/*  7165: 7932 */         String str13 = (String)paramLocalFrame.get(0);
/*  7166: 7933 */         localObject1 = paramBPWServicesBean
/*  7167: 7934 */           .getBPWFIInfoByACHId(
/*  7168: 7935 */           str13);
/*  7169:      */         
/*  7170: 7937 */         paramLocalFrame.setResult(localObject1);
/*  7171:      */       }
/*  7172:      */       catch (Throwable localThrowable32)
/*  7173:      */       {
/*  7174: 7941 */         localThrowable32.printStackTrace(Jaguar.log);
/*  7175: 7942 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/*  7176: 7943 */         return localThrowable32.getClass().getName();
/*  7177:      */       }
/*  7178:      */     case 83: 
/*  7179:      */       try
/*  7180:      */       {
/*  7181: 7952 */         String str14 = (String)paramLocalFrame.get(0);
/*  7182: 7953 */         localObject1 = paramBPWServicesBean
/*  7183: 7954 */           .getBPWFIInfoByRTN(
/*  7184: 7955 */           str14);
/*  7185:      */         
/*  7186: 7957 */         paramLocalFrame.setResult(localObject1);
/*  7187:      */       }
/*  7188:      */       catch (Throwable localThrowable33)
/*  7189:      */       {
/*  7190: 7961 */         localThrowable33.printStackTrace(Jaguar.log);
/*  7191: 7962 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/*  7192: 7963 */         return localThrowable33.getClass().getName();
/*  7193:      */       }
/*  7194:      */     case 84: 
/*  7195:      */       try
/*  7196:      */       {
/*  7197:      */         RPPSFIInfo localRPPSFIInfo1;
/*  7198: 7972 */         if (!paramBoolean)
/*  7199:      */         {
/*  7200: 7974 */           localRPPSFIInfo1 = (RPPSFIInfo)paramLocalFrame.get(0);
/*  7201:      */         }
/*  7202:      */         else
/*  7203:      */         {
/*  7204: 7978 */           localObject1 = paramLocalFrame.get(0);
/*  7205: 7979 */           localRPPSFIInfo1 = (RPPSFIInfo)ObjectVal.clone(localObject1);
/*  7206:      */         }
/*  7207: 7981 */         localObject1 = 
/*  7208: 7982 */           paramBPWServicesBean.addRPPSFIInfo(
/*  7209: 7983 */           localRPPSFIInfo1);
/*  7210:      */         
/*  7211: 7985 */         paramLocalFrame.setResult(localObject1);
/*  7212:      */       }
/*  7213:      */       catch (Throwable localThrowable34)
/*  7214:      */       {
/*  7215: 7989 */         if ((localThrowable34 instanceof FFSException))
/*  7216:      */         {
/*  7217: 7991 */           paramLocalFrame.setException(localThrowable34);
/*  7218: 7992 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7219: 7993 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable34);
/*  7220:      */         }
/*  7221: 7995 */         localThrowable34.printStackTrace(Jaguar.log);
/*  7222: 7996 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/*  7223: 7997 */         return localThrowable34.getClass().getName();
/*  7224:      */       }
/*  7225:      */     case 85: 
/*  7226:      */       try
/*  7227:      */       {
/*  7228: 8006 */         String str15 = (String)paramLocalFrame.get(0);
/*  7229: 8007 */         localObject1 = paramBPWServicesBean
/*  7230: 8008 */           .getRPPSFIInfoByFIId(
/*  7231: 8009 */           str15);
/*  7232:      */         
/*  7233: 8011 */         paramLocalFrame.setResult(localObject1);
/*  7234:      */       }
/*  7235:      */       catch (Throwable localThrowable35)
/*  7236:      */       {
/*  7237: 8015 */         if ((localThrowable35 instanceof FFSException))
/*  7238:      */         {
/*  7239: 8017 */           paramLocalFrame.setException(localThrowable35);
/*  7240: 8018 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7241: 8019 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable35);
/*  7242:      */         }
/*  7243: 8021 */         localThrowable35.printStackTrace(Jaguar.log);
/*  7244: 8022 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/*  7245: 8023 */         return localThrowable35.getClass().getName();
/*  7246:      */       }
/*  7247:      */     case 86: 
/*  7248:      */       try
/*  7249:      */       {
/*  7250: 8032 */         String str16 = (String)paramLocalFrame.get(0);
/*  7251: 8033 */         localObject1 = paramBPWServicesBean
/*  7252: 8034 */           .getRPPSFIInfoByFIRPPSId(
/*  7253: 8035 */           str16);
/*  7254:      */         
/*  7255: 8037 */         paramLocalFrame.setResult(localObject1);
/*  7256:      */       }
/*  7257:      */       catch (Throwable localThrowable36)
/*  7258:      */       {
/*  7259: 8041 */         if ((localThrowable36 instanceof FFSException))
/*  7260:      */         {
/*  7261: 8043 */           paramLocalFrame.setException(localThrowable36);
/*  7262: 8044 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7263: 8045 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable36);
/*  7264:      */         }
/*  7265: 8047 */         localThrowable36.printStackTrace(Jaguar.log);
/*  7266: 8048 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/*  7267: 8049 */         return localThrowable36.getClass().getName();
/*  7268:      */       }
/*  7269:      */     case 87: 
/*  7270:      */       try
/*  7271:      */       {
/*  7272:      */         RPPSFIInfo localRPPSFIInfo2;
/*  7273: 8058 */         if (!paramBoolean)
/*  7274:      */         {
/*  7275: 8060 */           localRPPSFIInfo2 = (RPPSFIInfo)paramLocalFrame.get(0);
/*  7276:      */         }
/*  7277:      */         else
/*  7278:      */         {
/*  7279: 8064 */           localObject1 = paramLocalFrame.get(0);
/*  7280: 8065 */           localRPPSFIInfo2 = (RPPSFIInfo)ObjectVal.clone(localObject1);
/*  7281:      */         }
/*  7282: 8067 */         localObject1 = 
/*  7283: 8068 */           paramBPWServicesBean.canRPPSFIInfo(
/*  7284: 8069 */           localRPPSFIInfo2);
/*  7285:      */         
/*  7286: 8071 */         paramLocalFrame.setResult(localObject1);
/*  7287:      */       }
/*  7288:      */       catch (Throwable localThrowable37)
/*  7289:      */       {
/*  7290: 8075 */         if ((localThrowable37 instanceof FFSException))
/*  7291:      */         {
/*  7292: 8077 */           paramLocalFrame.setException(localThrowable37);
/*  7293: 8078 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7294: 8079 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable37);
/*  7295:      */         }
/*  7296: 8081 */         localThrowable37.printStackTrace(Jaguar.log);
/*  7297: 8082 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable37, true);
/*  7298: 8083 */         return localThrowable37.getClass().getName();
/*  7299:      */       }
/*  7300:      */     case 88: 
/*  7301:      */       try
/*  7302:      */       {
/*  7303:      */         RPPSFIInfo localRPPSFIInfo3;
/*  7304: 8092 */         if (!paramBoolean)
/*  7305:      */         {
/*  7306: 8094 */           localRPPSFIInfo3 = (RPPSFIInfo)paramLocalFrame.get(0);
/*  7307:      */         }
/*  7308:      */         else
/*  7309:      */         {
/*  7310: 8098 */           localObject1 = paramLocalFrame.get(0);
/*  7311: 8099 */           localRPPSFIInfo3 = (RPPSFIInfo)ObjectVal.clone(localObject1);
/*  7312:      */         }
/*  7313: 8101 */         localObject1 = 
/*  7314: 8102 */           paramBPWServicesBean.activateRPPSFI(
/*  7315: 8103 */           localRPPSFIInfo3);
/*  7316:      */         
/*  7317: 8105 */         paramLocalFrame.setResult(localObject1);
/*  7318:      */       }
/*  7319:      */       catch (Throwable localThrowable38)
/*  7320:      */       {
/*  7321: 8109 */         if ((localThrowable38 instanceof FFSException))
/*  7322:      */         {
/*  7323: 8111 */           paramLocalFrame.setException(localThrowable38);
/*  7324: 8112 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7325: 8113 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable38);
/*  7326:      */         }
/*  7327: 8115 */         localThrowable38.printStackTrace(Jaguar.log);
/*  7328: 8116 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable38, true);
/*  7329: 8117 */         return localThrowable38.getClass().getName();
/*  7330:      */       }
/*  7331:      */     case 89: 
/*  7332:      */       try
/*  7333:      */       {
/*  7334:      */         RPPSFIInfo localRPPSFIInfo4;
/*  7335: 8126 */         if (!paramBoolean)
/*  7336:      */         {
/*  7337: 8128 */           localRPPSFIInfo4 = (RPPSFIInfo)paramLocalFrame.get(0);
/*  7338:      */         }
/*  7339:      */         else
/*  7340:      */         {
/*  7341: 8132 */           localObject1 = paramLocalFrame.get(0);
/*  7342: 8133 */           localRPPSFIInfo4 = (RPPSFIInfo)ObjectVal.clone(localObject1);
/*  7343:      */         }
/*  7344: 8135 */         localObject1 = 
/*  7345: 8136 */           paramBPWServicesBean.modRPPSFIInfo(
/*  7346: 8137 */           localRPPSFIInfo4);
/*  7347:      */         
/*  7348: 8139 */         paramLocalFrame.setResult(localObject1);
/*  7349:      */       }
/*  7350:      */       catch (Throwable localThrowable39)
/*  7351:      */       {
/*  7352: 8143 */         if ((localThrowable39 instanceof FFSException))
/*  7353:      */         {
/*  7354: 8145 */           paramLocalFrame.setException(localThrowable39);
/*  7355: 8146 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7356: 8147 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable39);
/*  7357:      */         }
/*  7358: 8149 */         localThrowable39.printStackTrace(Jaguar.log);
/*  7359: 8150 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable39, true);
/*  7360: 8151 */         return localThrowable39.getClass().getName();
/*  7361:      */       }
/*  7362:      */     case 90: 
/*  7363:      */       try
/*  7364:      */       {
/*  7365: 8160 */         String str17 = (String)paramLocalFrame.get(0);
/*  7366:      */         
/*  7367: 8162 */         int i = ((Integer)paramLocalFrame.get(1)).intValue();
/*  7368: 8163 */         int m = paramBPWServicesBean
/*  7369: 8164 */           .getSmartDate(
/*  7370: 8165 */           str17, 
/*  7371: 8166 */           i);
/*  7372:      */         
/*  7373: 8168 */         paramLocalFrame.setResult(m);
/*  7374:      */       }
/*  7375:      */       catch (Throwable localThrowable40)
/*  7376:      */       {
/*  7377: 8172 */         localThrowable40.printStackTrace(Jaguar.log);
/*  7378: 8173 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable40, true);
/*  7379: 8174 */         return localThrowable40.getClass().getName();
/*  7380:      */       }
/*  7381:      */     case 91: 
/*  7382:      */       try
/*  7383:      */       {
/*  7384: 8183 */         String str18 = (String)paramLocalFrame.get(0);
/*  7385:      */         
/*  7386: 8185 */         localObject2 = (String)paramLocalFrame.get(1);
/*  7387:      */         
/*  7388: 8187 */         localObject5 = (String)paramLocalFrame.get(2);
/*  7389: 8189 */         if (!paramBoolean)
/*  7390:      */         {
/*  7391: 8191 */           localObject6 = (HashMap)paramLocalFrame.get(3);
/*  7392:      */         }
/*  7393:      */         else
/*  7394:      */         {
/*  7395: 8195 */           localObject7 = paramLocalFrame.get(3);
/*  7396: 8196 */           localObject6 = (HashMap)ObjectVal.clone(localObject7);
/*  7397:      */         }
/*  7398: 8199 */         paramBPWServicesBean.processApprovalResult(
/*  7399: 8200 */           str18, 
/*  7400: 8201 */           (String)localObject2, 
/*  7401: 8202 */           (String)localObject5, 
/*  7402: 8203 */           (HashMap)localObject6);
/*  7403:      */       }
/*  7404:      */       catch (Throwable localThrowable41)
/*  7405:      */       {
/*  7406: 8208 */         if ((localThrowable41 instanceof FFSException))
/*  7407:      */         {
/*  7408: 8210 */           paramLocalFrame.setException(localThrowable41);
/*  7409: 8211 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7410: 8212 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable41);
/*  7411:      */         }
/*  7412: 8214 */         localThrowable41.printStackTrace(Jaguar.log);
/*  7413: 8215 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable41, true);
/*  7414: 8216 */         return localThrowable41.getClass().getName();
/*  7415:      */       }
/*  7416:      */     case 92: 
/*  7417:      */       try
/*  7418:      */       {
/*  7419:      */         CCCompanyInfo localCCCompanyInfo1;
/*  7420: 8225 */         if (!paramBoolean)
/*  7421:      */         {
/*  7422: 8227 */           localCCCompanyInfo1 = (CCCompanyInfo)paramLocalFrame.get(0);
/*  7423:      */         }
/*  7424:      */         else
/*  7425:      */         {
/*  7426: 8231 */           localObject2 = paramLocalFrame.get(0);
/*  7427: 8232 */           localCCCompanyInfo1 = (CCCompanyInfo)ObjectVal.clone(localObject2);
/*  7428:      */         }
/*  7429: 8234 */         localObject2 = 
/*  7430: 8235 */           paramBPWServicesBean.addCCCompany(
/*  7431: 8236 */           localCCCompanyInfo1);
/*  7432:      */         
/*  7433: 8238 */         paramLocalFrame.setResult(localObject2);
/*  7434:      */       }
/*  7435:      */       catch (Throwable localThrowable42)
/*  7436:      */       {
/*  7437: 8242 */         if ((localThrowable42 instanceof FFSException))
/*  7438:      */         {
/*  7439: 8244 */           paramLocalFrame.setException(localThrowable42);
/*  7440: 8245 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7441: 8246 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable42);
/*  7442:      */         }
/*  7443: 8248 */         localThrowable42.printStackTrace(Jaguar.log);
/*  7444: 8249 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable42, true);
/*  7445: 8250 */         return localThrowable42.getClass().getName();
/*  7446:      */       }
/*  7447:      */     case 93: 
/*  7448:      */       try
/*  7449:      */       {
/*  7450:      */         CCCompanyInfo localCCCompanyInfo2;
/*  7451: 8259 */         if (!paramBoolean)
/*  7452:      */         {
/*  7453: 8261 */           localCCCompanyInfo2 = (CCCompanyInfo)paramLocalFrame.get(0);
/*  7454:      */         }
/*  7455:      */         else
/*  7456:      */         {
/*  7457: 8265 */           localObject2 = paramLocalFrame.get(0);
/*  7458: 8266 */           localCCCompanyInfo2 = (CCCompanyInfo)ObjectVal.clone(localObject2);
/*  7459:      */         }
/*  7460: 8268 */         localObject2 = 
/*  7461: 8269 */           paramBPWServicesBean.cancelCCCompany(
/*  7462: 8270 */           localCCCompanyInfo2);
/*  7463:      */         
/*  7464: 8272 */         paramLocalFrame.setResult(localObject2);
/*  7465:      */       }
/*  7466:      */       catch (Throwable localThrowable43)
/*  7467:      */       {
/*  7468: 8276 */         if ((localThrowable43 instanceof FFSException))
/*  7469:      */         {
/*  7470: 8278 */           paramLocalFrame.setException(localThrowable43);
/*  7471: 8279 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7472: 8280 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable43);
/*  7473:      */         }
/*  7474: 8282 */         localThrowable43.printStackTrace(Jaguar.log);
/*  7475: 8283 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable43, true);
/*  7476: 8284 */         return localThrowable43.getClass().getName();
/*  7477:      */       }
/*  7478:      */     case 94: 
/*  7479:      */       try
/*  7480:      */       {
/*  7481:      */         CCCompanyInfo localCCCompanyInfo3;
/*  7482: 8293 */         if (!paramBoolean)
/*  7483:      */         {
/*  7484: 8295 */           localCCCompanyInfo3 = (CCCompanyInfo)paramLocalFrame.get(0);
/*  7485:      */         }
/*  7486:      */         else
/*  7487:      */         {
/*  7488: 8299 */           localObject2 = paramLocalFrame.get(0);
/*  7489: 8300 */           localCCCompanyInfo3 = (CCCompanyInfo)ObjectVal.clone(localObject2);
/*  7490:      */         }
/*  7491: 8302 */         localObject2 = 
/*  7492: 8303 */           paramBPWServicesBean.modCCCompany(
/*  7493: 8304 */           localCCCompanyInfo3);
/*  7494:      */         
/*  7495: 8306 */         paramLocalFrame.setResult(localObject2);
/*  7496:      */       }
/*  7497:      */       catch (Throwable localThrowable44)
/*  7498:      */       {
/*  7499: 8310 */         if ((localThrowable44 instanceof FFSException))
/*  7500:      */         {
/*  7501: 8312 */           paramLocalFrame.setException(localThrowable44);
/*  7502: 8313 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7503: 8314 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable44);
/*  7504:      */         }
/*  7505: 8316 */         localThrowable44.printStackTrace(Jaguar.log);
/*  7506: 8317 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable44, true);
/*  7507: 8318 */         return localThrowable44.getClass().getName();
/*  7508:      */       }
/*  7509:      */     case 95: 
/*  7510:      */       try
/*  7511:      */       {
/*  7512:      */         CCCompanyInfo localCCCompanyInfo4;
/*  7513: 8327 */         if (!paramBoolean)
/*  7514:      */         {
/*  7515: 8329 */           localCCCompanyInfo4 = (CCCompanyInfo)paramLocalFrame.get(0);
/*  7516:      */         }
/*  7517:      */         else
/*  7518:      */         {
/*  7519: 8333 */           localObject2 = paramLocalFrame.get(0);
/*  7520: 8334 */           localCCCompanyInfo4 = (CCCompanyInfo)ObjectVal.clone(localObject2);
/*  7521:      */         }
/*  7522: 8336 */         localObject2 = 
/*  7523: 8337 */           paramBPWServicesBean.getCCCompany(
/*  7524: 8338 */           localCCCompanyInfo4);
/*  7525:      */         
/*  7526: 8340 */         paramLocalFrame.setResult(localObject2);
/*  7527:      */       }
/*  7528:      */       catch (Throwable localThrowable45)
/*  7529:      */       {
/*  7530: 8344 */         if ((localThrowable45 instanceof FFSException))
/*  7531:      */         {
/*  7532: 8346 */           paramLocalFrame.setException(localThrowable45);
/*  7533: 8347 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7534: 8348 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable45);
/*  7535:      */         }
/*  7536: 8350 */         localThrowable45.printStackTrace(Jaguar.log);
/*  7537: 8351 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable45, true);
/*  7538: 8352 */         return localThrowable45.getClass().getName();
/*  7539:      */       }
/*  7540:      */     case 96: 
/*  7541:      */       try
/*  7542:      */       {
/*  7543:      */         CCCompanyInfoList localCCCompanyInfoList;
/*  7544: 8361 */         if (!paramBoolean)
/*  7545:      */         {
/*  7546: 8363 */           localCCCompanyInfoList = (CCCompanyInfoList)paramLocalFrame.get(0);
/*  7547:      */         }
/*  7548:      */         else
/*  7549:      */         {
/*  7550: 8367 */           localObject2 = paramLocalFrame.get(0);
/*  7551: 8368 */           localCCCompanyInfoList = (CCCompanyInfoList)ObjectVal.clone(localObject2);
/*  7552:      */         }
/*  7553: 8370 */         localObject2 = 
/*  7554: 8371 */           paramBPWServicesBean.getCCCompanyList(
/*  7555: 8372 */           localCCCompanyInfoList);
/*  7556:      */         
/*  7557: 8374 */         paramLocalFrame.setResult(localObject2);
/*  7558:      */       }
/*  7559:      */       catch (Throwable localThrowable46)
/*  7560:      */       {
/*  7561: 8378 */         if ((localThrowable46 instanceof FFSException))
/*  7562:      */         {
/*  7563: 8380 */           paramLocalFrame.setException(localThrowable46);
/*  7564: 8381 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7565: 8382 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable46);
/*  7566:      */         }
/*  7567: 8384 */         localThrowable46.printStackTrace(Jaguar.log);
/*  7568: 8385 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable46, true);
/*  7569: 8386 */         return localThrowable46.getClass().getName();
/*  7570:      */       }
/*  7571:      */     case 97: 
/*  7572:      */       try
/*  7573:      */       {
/*  7574: 8395 */         String str19 = (String)paramLocalFrame.get(0);
/*  7575:      */         
/*  7576: 8397 */         localObject2 = (String)paramLocalFrame.get(1);
/*  7577: 8398 */         localObject5 = paramBPWServicesBean
/*  7578: 8399 */           .getNextCashConCutOff(
/*  7579: 8400 */           str19, 
/*  7580: 8401 */           (String)localObject2);
/*  7581:      */         
/*  7582: 8403 */         paramLocalFrame.setResult(localObject5);
/*  7583:      */       }
/*  7584:      */       catch (Throwable localThrowable47)
/*  7585:      */       {
/*  7586: 8407 */         if ((localThrowable47 instanceof FFSException))
/*  7587:      */         {
/*  7588: 8409 */           paramLocalFrame.setException(localThrowable47);
/*  7589: 8410 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7590: 8411 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable47);
/*  7591:      */         }
/*  7592: 8413 */         localThrowable47.printStackTrace(Jaguar.log);
/*  7593: 8414 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable47, true);
/*  7594: 8415 */         return localThrowable47.getClass().getName();
/*  7595:      */       }
/*  7596:      */     case 98: 
/*  7597:      */       try
/*  7598:      */       {
/*  7599:      */         CCCompanyAcctInfo localCCCompanyAcctInfo1;
/*  7600: 8424 */         if (!paramBoolean)
/*  7601:      */         {
/*  7602: 8426 */           localCCCompanyAcctInfo1 = (CCCompanyAcctInfo)paramLocalFrame.get(0);
/*  7603:      */         }
/*  7604:      */         else
/*  7605:      */         {
/*  7606: 8430 */           localObject2 = paramLocalFrame.get(0);
/*  7607: 8431 */           localCCCompanyAcctInfo1 = (CCCompanyAcctInfo)ObjectVal.clone(localObject2);
/*  7608:      */         }
/*  7609: 8433 */         localObject2 = 
/*  7610: 8434 */           paramBPWServicesBean.addCCCompanyAcct(
/*  7611: 8435 */           localCCCompanyAcctInfo1);
/*  7612:      */         
/*  7613: 8437 */         paramLocalFrame.setResult(localObject2);
/*  7614:      */       }
/*  7615:      */       catch (Throwable localThrowable48)
/*  7616:      */       {
/*  7617: 8441 */         if ((localThrowable48 instanceof FFSException))
/*  7618:      */         {
/*  7619: 8443 */           paramLocalFrame.setException(localThrowable48);
/*  7620: 8444 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7621: 8445 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable48);
/*  7622:      */         }
/*  7623: 8447 */         localThrowable48.printStackTrace(Jaguar.log);
/*  7624: 8448 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable48, true);
/*  7625: 8449 */         return localThrowable48.getClass().getName();
/*  7626:      */       }
/*  7627:      */     case 99: 
/*  7628:      */       try
/*  7629:      */       {
/*  7630:      */         CCCompanyAcctInfo localCCCompanyAcctInfo2;
/*  7631: 8458 */         if (!paramBoolean)
/*  7632:      */         {
/*  7633: 8460 */           localCCCompanyAcctInfo2 = (CCCompanyAcctInfo)paramLocalFrame.get(0);
/*  7634:      */         }
/*  7635:      */         else
/*  7636:      */         {
/*  7637: 8464 */           localObject2 = paramLocalFrame.get(0);
/*  7638: 8465 */           localCCCompanyAcctInfo2 = (CCCompanyAcctInfo)ObjectVal.clone(localObject2);
/*  7639:      */         }
/*  7640: 8467 */         localObject2 = 
/*  7641: 8468 */           paramBPWServicesBean.cancelCCCompanyAcct(
/*  7642: 8469 */           localCCCompanyAcctInfo2);
/*  7643:      */         
/*  7644: 8471 */         paramLocalFrame.setResult(localObject2);
/*  7645:      */       }
/*  7646:      */       catch (Throwable localThrowable49)
/*  7647:      */       {
/*  7648: 8475 */         if ((localThrowable49 instanceof FFSException))
/*  7649:      */         {
/*  7650: 8477 */           paramLocalFrame.setException(localThrowable49);
/*  7651: 8478 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7652: 8479 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable49);
/*  7653:      */         }
/*  7654: 8481 */         localThrowable49.printStackTrace(Jaguar.log);
/*  7655: 8482 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable49, true);
/*  7656: 8483 */         return localThrowable49.getClass().getName();
/*  7657:      */       }
/*  7658:      */     case 100: 
/*  7659:      */       try
/*  7660:      */       {
/*  7661:      */         CCCompanyAcctInfo localCCCompanyAcctInfo3;
/*  7662: 8492 */         if (!paramBoolean)
/*  7663:      */         {
/*  7664: 8494 */           localCCCompanyAcctInfo3 = (CCCompanyAcctInfo)paramLocalFrame.get(0);
/*  7665:      */         }
/*  7666:      */         else
/*  7667:      */         {
/*  7668: 8498 */           localObject2 = paramLocalFrame.get(0);
/*  7669: 8499 */           localCCCompanyAcctInfo3 = (CCCompanyAcctInfo)ObjectVal.clone(localObject2);
/*  7670:      */         }
/*  7671: 8501 */         localObject2 = 
/*  7672: 8502 */           paramBPWServicesBean.modCCCompanyAcct(
/*  7673: 8503 */           localCCCompanyAcctInfo3);
/*  7674:      */         
/*  7675: 8505 */         paramLocalFrame.setResult(localObject2);
/*  7676:      */       }
/*  7677:      */       catch (Throwable localThrowable50)
/*  7678:      */       {
/*  7679: 8509 */         if ((localThrowable50 instanceof FFSException))
/*  7680:      */         {
/*  7681: 8511 */           paramLocalFrame.setException(localThrowable50);
/*  7682: 8512 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7683: 8513 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable50);
/*  7684:      */         }
/*  7685: 8515 */         localThrowable50.printStackTrace(Jaguar.log);
/*  7686: 8516 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable50, true);
/*  7687: 8517 */         return localThrowable50.getClass().getName();
/*  7688:      */       }
/*  7689:      */     default: 
/*  7690: 8523 */       return 
/*  7691: 8524 */         localInvoke2(
/*  7692: 8525 */         param_ServerRequest, 
/*  7693: 8526 */         paramInputStream, 
/*  7694: 8527 */         paramOutputStream, 
/*  7695: 8528 */         paramBPWServicesBean, 
/*  7696: 8529 */         paramLocalFrame, 
/*  7697: 8530 */         paramInteger, 
/*  7698: 8531 */         paramBoolean);
/*  7699:      */     }
/*  7700: 8535 */     return null;
/*  7701:      */   }
/*  7702:      */   
/*  7703:      */   private static String localInvoke2(_ServerRequest param_ServerRequest, InputStream paramInputStream, OutputStream paramOutputStream, BPWServicesBean paramBPWServicesBean, LocalFrame paramLocalFrame, Integer paramInteger, boolean paramBoolean)
/*  7704:      */   {
/*  7705:      */     Object localObject1;
/*  7706:      */     Object localObject3;
/*  7707:      */     boolean bool;
/*  7708:      */     Object localObject2;
/*  7709: 8547 */     switch (paramInteger.intValue())
/*  7710:      */     {
/*  7711:      */     case 101: 
/*  7712:      */       try
/*  7713:      */       {
/*  7714:      */         CCCompanyAcctInfo localCCCompanyAcctInfo;
/*  7715: 8554 */         if (!paramBoolean)
/*  7716:      */         {
/*  7717: 8556 */           localCCCompanyAcctInfo = (CCCompanyAcctInfo)paramLocalFrame.get(0);
/*  7718:      */         }
/*  7719:      */         else
/*  7720:      */         {
/*  7721: 8560 */           localObject1 = paramLocalFrame.get(0);
/*  7722: 8561 */           localCCCompanyAcctInfo = (CCCompanyAcctInfo)ObjectVal.clone(localObject1);
/*  7723:      */         }
/*  7724: 8563 */         localObject1 = 
/*  7725: 8564 */           paramBPWServicesBean.getCCCompanyAcct(
/*  7726: 8565 */           localCCCompanyAcctInfo);
/*  7727:      */         
/*  7728: 8567 */         paramLocalFrame.setResult(localObject1);
/*  7729:      */       }
/*  7730:      */       catch (Throwable localThrowable1)
/*  7731:      */       {
/*  7732: 8571 */         if ((localThrowable1 instanceof FFSException))
/*  7733:      */         {
/*  7734: 8573 */           paramLocalFrame.setException(localThrowable1);
/*  7735: 8574 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7736: 8575 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable1);
/*  7737:      */         }
/*  7738: 8577 */         localThrowable1.printStackTrace(Jaguar.log);
/*  7739: 8578 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/*  7740: 8579 */         return localThrowable1.getClass().getName();
/*  7741:      */       }
/*  7742:      */     case 102: 
/*  7743:      */       try
/*  7744:      */       {
/*  7745:      */         CCCompanyAcctInfoList localCCCompanyAcctInfoList;
/*  7746: 8588 */         if (!paramBoolean)
/*  7747:      */         {
/*  7748: 8590 */           localCCCompanyAcctInfoList = (CCCompanyAcctInfoList)paramLocalFrame.get(0);
/*  7749:      */         }
/*  7750:      */         else
/*  7751:      */         {
/*  7752: 8594 */           localObject1 = paramLocalFrame.get(0);
/*  7753: 8595 */           localCCCompanyAcctInfoList = (CCCompanyAcctInfoList)ObjectVal.clone(localObject1);
/*  7754:      */         }
/*  7755: 8597 */         localObject1 = 
/*  7756: 8598 */           paramBPWServicesBean.getCCCompanyAcctList(
/*  7757: 8599 */           localCCCompanyAcctInfoList);
/*  7758:      */         
/*  7759: 8601 */         paramLocalFrame.setResult(localObject1);
/*  7760:      */       }
/*  7761:      */       catch (Throwable localThrowable2)
/*  7762:      */       {
/*  7763: 8605 */         if ((localThrowable2 instanceof FFSException))
/*  7764:      */         {
/*  7765: 8607 */           paramLocalFrame.setException(localThrowable2);
/*  7766: 8608 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7767: 8609 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable2);
/*  7768:      */         }
/*  7769: 8611 */         localThrowable2.printStackTrace(Jaguar.log);
/*  7770: 8612 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/*  7771: 8613 */         return localThrowable2.getClass().getName();
/*  7772:      */       }
/*  7773:      */     case 103: 
/*  7774:      */       try
/*  7775:      */       {
/*  7776:      */         CCCompanyCutOffInfo localCCCompanyCutOffInfo1;
/*  7777: 8622 */         if (!paramBoolean)
/*  7778:      */         {
/*  7779: 8624 */           localCCCompanyCutOffInfo1 = (CCCompanyCutOffInfo)paramLocalFrame.get(0);
/*  7780:      */         }
/*  7781:      */         else
/*  7782:      */         {
/*  7783: 8628 */           localObject1 = paramLocalFrame.get(0);
/*  7784: 8629 */           localCCCompanyCutOffInfo1 = (CCCompanyCutOffInfo)ObjectVal.clone(localObject1);
/*  7785:      */         }
/*  7786: 8631 */         localObject1 = 
/*  7787: 8632 */           paramBPWServicesBean.addCCCompanyCutOff(
/*  7788: 8633 */           localCCCompanyCutOffInfo1);
/*  7789:      */         
/*  7790: 8635 */         paramLocalFrame.setResult(localObject1);
/*  7791:      */       }
/*  7792:      */       catch (Throwable localThrowable3)
/*  7793:      */       {
/*  7794: 8639 */         if ((localThrowable3 instanceof FFSException))
/*  7795:      */         {
/*  7796: 8641 */           paramLocalFrame.setException(localThrowable3);
/*  7797: 8642 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7798: 8643 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable3);
/*  7799:      */         }
/*  7800: 8645 */         localThrowable3.printStackTrace(Jaguar.log);
/*  7801: 8646 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/*  7802: 8647 */         return localThrowable3.getClass().getName();
/*  7803:      */       }
/*  7804:      */     case 104: 
/*  7805:      */       try
/*  7806:      */       {
/*  7807:      */         CCCompanyCutOffInfo localCCCompanyCutOffInfo2;
/*  7808: 8656 */         if (!paramBoolean)
/*  7809:      */         {
/*  7810: 8658 */           localCCCompanyCutOffInfo2 = (CCCompanyCutOffInfo)paramLocalFrame.get(0);
/*  7811:      */         }
/*  7812:      */         else
/*  7813:      */         {
/*  7814: 8662 */           localObject1 = paramLocalFrame.get(0);
/*  7815: 8663 */           localCCCompanyCutOffInfo2 = (CCCompanyCutOffInfo)ObjectVal.clone(localObject1);
/*  7816:      */         }
/*  7817: 8665 */         localObject1 = 
/*  7818: 8666 */           paramBPWServicesBean.cancelCCCompanyCutOff(
/*  7819: 8667 */           localCCCompanyCutOffInfo2);
/*  7820:      */         
/*  7821: 8669 */         paramLocalFrame.setResult(localObject1);
/*  7822:      */       }
/*  7823:      */       catch (Throwable localThrowable4)
/*  7824:      */       {
/*  7825: 8673 */         if ((localThrowable4 instanceof FFSException))
/*  7826:      */         {
/*  7827: 8675 */           paramLocalFrame.setException(localThrowable4);
/*  7828: 8676 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7829: 8677 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable4);
/*  7830:      */         }
/*  7831: 8679 */         localThrowable4.printStackTrace(Jaguar.log);
/*  7832: 8680 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/*  7833: 8681 */         return localThrowable4.getClass().getName();
/*  7834:      */       }
/*  7835:      */     case 105: 
/*  7836:      */       try
/*  7837:      */       {
/*  7838:      */         CCCompanyCutOffInfo localCCCompanyCutOffInfo3;
/*  7839: 8690 */         if (!paramBoolean)
/*  7840:      */         {
/*  7841: 8692 */           localCCCompanyCutOffInfo3 = (CCCompanyCutOffInfo)paramLocalFrame.get(0);
/*  7842:      */         }
/*  7843:      */         else
/*  7844:      */         {
/*  7845: 8696 */           localObject1 = paramLocalFrame.get(0);
/*  7846: 8697 */           localCCCompanyCutOffInfo3 = (CCCompanyCutOffInfo)ObjectVal.clone(localObject1);
/*  7847:      */         }
/*  7848: 8699 */         localObject1 = 
/*  7849: 8700 */           paramBPWServicesBean.getCCCompanyCutOff(
/*  7850: 8701 */           localCCCompanyCutOffInfo3);
/*  7851:      */         
/*  7852: 8703 */         paramLocalFrame.setResult(localObject1);
/*  7853:      */       }
/*  7854:      */       catch (Throwable localThrowable5)
/*  7855:      */       {
/*  7856: 8707 */         if ((localThrowable5 instanceof FFSException))
/*  7857:      */         {
/*  7858: 8709 */           paramLocalFrame.setException(localThrowable5);
/*  7859: 8710 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7860: 8711 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable5);
/*  7861:      */         }
/*  7862: 8713 */         localThrowable5.printStackTrace(Jaguar.log);
/*  7863: 8714 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/*  7864: 8715 */         return localThrowable5.getClass().getName();
/*  7865:      */       }
/*  7866:      */     case 106: 
/*  7867:      */       try
/*  7868:      */       {
/*  7869:      */         CCCompanyCutOffInfoList localCCCompanyCutOffInfoList;
/*  7870: 8724 */         if (!paramBoolean)
/*  7871:      */         {
/*  7872: 8726 */           localCCCompanyCutOffInfoList = (CCCompanyCutOffInfoList)paramLocalFrame.get(0);
/*  7873:      */         }
/*  7874:      */         else
/*  7875:      */         {
/*  7876: 8730 */           localObject1 = paramLocalFrame.get(0);
/*  7877: 8731 */           localCCCompanyCutOffInfoList = (CCCompanyCutOffInfoList)ObjectVal.clone(localObject1);
/*  7878:      */         }
/*  7879: 8733 */         localObject1 = 
/*  7880: 8734 */           paramBPWServicesBean.getCCCompanyCutOffList(
/*  7881: 8735 */           localCCCompanyCutOffInfoList);
/*  7882:      */         
/*  7883: 8737 */         paramLocalFrame.setResult(localObject1);
/*  7884:      */       }
/*  7885:      */       catch (Throwable localThrowable6)
/*  7886:      */       {
/*  7887: 8741 */         if ((localThrowable6 instanceof FFSException))
/*  7888:      */         {
/*  7889: 8743 */           paramLocalFrame.setException(localThrowable6);
/*  7890: 8744 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7891: 8745 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable6);
/*  7892:      */         }
/*  7893: 8747 */         localThrowable6.printStackTrace(Jaguar.log);
/*  7894: 8748 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/*  7895: 8749 */         return localThrowable6.getClass().getName();
/*  7896:      */       }
/*  7897:      */     case 107: 
/*  7898:      */       try
/*  7899:      */       {
/*  7900:      */         CCLocationInfo localCCLocationInfo1;
/*  7901: 8758 */         if (!paramBoolean)
/*  7902:      */         {
/*  7903: 8760 */           localCCLocationInfo1 = (CCLocationInfo)paramLocalFrame.get(0);
/*  7904:      */         }
/*  7905:      */         else
/*  7906:      */         {
/*  7907: 8764 */           localObject1 = paramLocalFrame.get(0);
/*  7908: 8765 */           localCCLocationInfo1 = (CCLocationInfo)ObjectVal.clone(localObject1);
/*  7909:      */         }
/*  7910: 8767 */         localObject1 = 
/*  7911: 8768 */           paramBPWServicesBean.addCCLocation(
/*  7912: 8769 */           localCCLocationInfo1);
/*  7913:      */         
/*  7914: 8771 */         paramLocalFrame.setResult(localObject1);
/*  7915:      */       }
/*  7916:      */       catch (Throwable localThrowable7)
/*  7917:      */       {
/*  7918: 8775 */         if ((localThrowable7 instanceof FFSException))
/*  7919:      */         {
/*  7920: 8777 */           paramLocalFrame.setException(localThrowable7);
/*  7921: 8778 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7922: 8779 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable7);
/*  7923:      */         }
/*  7924: 8781 */         localThrowable7.printStackTrace(Jaguar.log);
/*  7925: 8782 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/*  7926: 8783 */         return localThrowable7.getClass().getName();
/*  7927:      */       }
/*  7928:      */     case 108: 
/*  7929:      */       try
/*  7930:      */       {
/*  7931:      */         CCLocationInfo localCCLocationInfo2;
/*  7932: 8792 */         if (!paramBoolean)
/*  7933:      */         {
/*  7934: 8794 */           localCCLocationInfo2 = (CCLocationInfo)paramLocalFrame.get(0);
/*  7935:      */         }
/*  7936:      */         else
/*  7937:      */         {
/*  7938: 8798 */           localObject1 = paramLocalFrame.get(0);
/*  7939: 8799 */           localCCLocationInfo2 = (CCLocationInfo)ObjectVal.clone(localObject1);
/*  7940:      */         }
/*  7941: 8801 */         localObject1 = 
/*  7942: 8802 */           paramBPWServicesBean.cancelCCLocation(
/*  7943: 8803 */           localCCLocationInfo2);
/*  7944:      */         
/*  7945: 8805 */         paramLocalFrame.setResult(localObject1);
/*  7946:      */       }
/*  7947:      */       catch (Throwable localThrowable8)
/*  7948:      */       {
/*  7949: 8809 */         if ((localThrowable8 instanceof FFSException))
/*  7950:      */         {
/*  7951: 8811 */           paramLocalFrame.setException(localThrowable8);
/*  7952: 8812 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7953: 8813 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable8);
/*  7954:      */         }
/*  7955: 8815 */         localThrowable8.printStackTrace(Jaguar.log);
/*  7956: 8816 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/*  7957: 8817 */         return localThrowable8.getClass().getName();
/*  7958:      */       }
/*  7959:      */     case 109: 
/*  7960:      */       try
/*  7961:      */       {
/*  7962:      */         CCLocationInfo localCCLocationInfo3;
/*  7963: 8826 */         if (!paramBoolean)
/*  7964:      */         {
/*  7965: 8828 */           localCCLocationInfo3 = (CCLocationInfo)paramLocalFrame.get(0);
/*  7966:      */         }
/*  7967:      */         else
/*  7968:      */         {
/*  7969: 8832 */           localObject1 = paramLocalFrame.get(0);
/*  7970: 8833 */           localCCLocationInfo3 = (CCLocationInfo)ObjectVal.clone(localObject1);
/*  7971:      */         }
/*  7972: 8835 */         localObject1 = 
/*  7973: 8836 */           paramBPWServicesBean.modCCLocation(
/*  7974: 8837 */           localCCLocationInfo3);
/*  7975:      */         
/*  7976: 8839 */         paramLocalFrame.setResult(localObject1);
/*  7977:      */       }
/*  7978:      */       catch (Throwable localThrowable9)
/*  7979:      */       {
/*  7980: 8843 */         if ((localThrowable9 instanceof FFSException))
/*  7981:      */         {
/*  7982: 8845 */           paramLocalFrame.setException(localThrowable9);
/*  7983: 8846 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  7984: 8847 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable9);
/*  7985:      */         }
/*  7986: 8849 */         localThrowable9.printStackTrace(Jaguar.log);
/*  7987: 8850 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/*  7988: 8851 */         return localThrowable9.getClass().getName();
/*  7989:      */       }
/*  7990:      */     case 110: 
/*  7991:      */       try
/*  7992:      */       {
/*  7993:      */         CCLocationInfo localCCLocationInfo4;
/*  7994: 8860 */         if (!paramBoolean)
/*  7995:      */         {
/*  7996: 8862 */           localCCLocationInfo4 = (CCLocationInfo)paramLocalFrame.get(0);
/*  7997:      */         }
/*  7998:      */         else
/*  7999:      */         {
/*  8000: 8866 */           localObject1 = paramLocalFrame.get(0);
/*  8001: 8867 */           localCCLocationInfo4 = (CCLocationInfo)ObjectVal.clone(localObject1);
/*  8002:      */         }
/*  8003: 8869 */         localObject1 = 
/*  8004: 8870 */           paramBPWServicesBean.getCCLocation(
/*  8005: 8871 */           localCCLocationInfo4);
/*  8006:      */         
/*  8007: 8873 */         paramLocalFrame.setResult(localObject1);
/*  8008:      */       }
/*  8009:      */       catch (Throwable localThrowable10)
/*  8010:      */       {
/*  8011: 8877 */         if ((localThrowable10 instanceof FFSException))
/*  8012:      */         {
/*  8013: 8879 */           paramLocalFrame.setException(localThrowable10);
/*  8014: 8880 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  8015: 8881 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable10);
/*  8016:      */         }
/*  8017: 8883 */         localThrowable10.printStackTrace(Jaguar.log);
/*  8018: 8884 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/*  8019: 8885 */         return localThrowable10.getClass().getName();
/*  8020:      */       }
/*  8021:      */     case 111: 
/*  8022:      */       try
/*  8023:      */       {
/*  8024:      */         CCLocationInfoList localCCLocationInfoList;
/*  8025: 8894 */         if (!paramBoolean)
/*  8026:      */         {
/*  8027: 8896 */           localCCLocationInfoList = (CCLocationInfoList)paramLocalFrame.get(0);
/*  8028:      */         }
/*  8029:      */         else
/*  8030:      */         {
/*  8031: 8900 */           localObject1 = paramLocalFrame.get(0);
/*  8032: 8901 */           localCCLocationInfoList = (CCLocationInfoList)ObjectVal.clone(localObject1);
/*  8033:      */         }
/*  8034: 8903 */         localObject1 = 
/*  8035: 8904 */           paramBPWServicesBean.getCCLocationList(
/*  8036: 8905 */           localCCLocationInfoList);
/*  8037:      */         
/*  8038: 8907 */         paramLocalFrame.setResult(localObject1);
/*  8039:      */       }
/*  8040:      */       catch (Throwable localThrowable11)
/*  8041:      */       {
/*  8042: 8911 */         if ((localThrowable11 instanceof FFSException))
/*  8043:      */         {
/*  8044: 8913 */           paramLocalFrame.setException(localThrowable11);
/*  8045: 8914 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  8046: 8915 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable11);
/*  8047:      */         }
/*  8048: 8917 */         localThrowable11.printStackTrace(Jaguar.log);
/*  8049: 8918 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/*  8050: 8919 */         return localThrowable11.getClass().getName();
/*  8051:      */       }
/*  8052:      */     case 112: 
/*  8053:      */       try
/*  8054:      */       {
/*  8055:      */         CCEntryInfo localCCEntryInfo1;
/*  8056: 8928 */         if (!paramBoolean)
/*  8057:      */         {
/*  8058: 8930 */           localCCEntryInfo1 = (CCEntryInfo)paramLocalFrame.get(0);
/*  8059:      */         }
/*  8060:      */         else
/*  8061:      */         {
/*  8062: 8934 */           localObject1 = paramLocalFrame.get(0);
/*  8063: 8935 */           localCCEntryInfo1 = (CCEntryInfo)ObjectVal.clone(localObject1);
/*  8064:      */         }
/*  8065: 8937 */         localObject1 = 
/*  8066: 8938 */           paramBPWServicesBean.addCCEntry(
/*  8067: 8939 */           localCCEntryInfo1);
/*  8068:      */         
/*  8069: 8941 */         paramLocalFrame.setResult(localObject1);
/*  8070:      */       }
/*  8071:      */       catch (Throwable localThrowable12)
/*  8072:      */       {
/*  8073: 8945 */         if ((localThrowable12 instanceof FFSException))
/*  8074:      */         {
/*  8075: 8947 */           paramLocalFrame.setException(localThrowable12);
/*  8076: 8948 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  8077: 8949 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable12);
/*  8078:      */         }
/*  8079: 8951 */         localThrowable12.printStackTrace(Jaguar.log);
/*  8080: 8952 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/*  8081: 8953 */         return localThrowable12.getClass().getName();
/*  8082:      */       }
/*  8083:      */     case 113: 
/*  8084:      */       try
/*  8085:      */       {
/*  8086:      */         CCEntryInfo localCCEntryInfo2;
/*  8087: 8962 */         if (!paramBoolean)
/*  8088:      */         {
/*  8089: 8964 */           localCCEntryInfo2 = (CCEntryInfo)paramLocalFrame.get(0);
/*  8090:      */         }
/*  8091:      */         else
/*  8092:      */         {
/*  8093: 8968 */           localObject1 = paramLocalFrame.get(0);
/*  8094: 8969 */           localCCEntryInfo2 = (CCEntryInfo)ObjectVal.clone(localObject1);
/*  8095:      */         }
/*  8096: 8971 */         localObject1 = 
/*  8097: 8972 */           paramBPWServicesBean.cancelCCEntry(
/*  8098: 8973 */           localCCEntryInfo2);
/*  8099:      */         
/*  8100: 8975 */         paramLocalFrame.setResult(localObject1);
/*  8101:      */       }
/*  8102:      */       catch (Throwable localThrowable13)
/*  8103:      */       {
/*  8104: 8979 */         if ((localThrowable13 instanceof FFSException))
/*  8105:      */         {
/*  8106: 8981 */           paramLocalFrame.setException(localThrowable13);
/*  8107: 8982 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  8108: 8983 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable13);
/*  8109:      */         }
/*  8110: 8985 */         localThrowable13.printStackTrace(Jaguar.log);
/*  8111: 8986 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/*  8112: 8987 */         return localThrowable13.getClass().getName();
/*  8113:      */       }
/*  8114:      */     case 114: 
/*  8115:      */       try
/*  8116:      */       {
/*  8117:      */         CCEntryInfo localCCEntryInfo3;
/*  8118: 8996 */         if (!paramBoolean)
/*  8119:      */         {
/*  8120: 8998 */           localCCEntryInfo3 = (CCEntryInfo)paramLocalFrame.get(0);
/*  8121:      */         }
/*  8122:      */         else
/*  8123:      */         {
/*  8124: 9002 */           localObject1 = paramLocalFrame.get(0);
/*  8125: 9003 */           localCCEntryInfo3 = (CCEntryInfo)ObjectVal.clone(localObject1);
/*  8126:      */         }
/*  8127: 9005 */         localObject1 = 
/*  8128: 9006 */           paramBPWServicesBean.modCCEntry(
/*  8129: 9007 */           localCCEntryInfo3);
/*  8130:      */         
/*  8131: 9009 */         paramLocalFrame.setResult(localObject1);
/*  8132:      */       }
/*  8133:      */       catch (Throwable localThrowable14)
/*  8134:      */       {
/*  8135: 9013 */         if ((localThrowable14 instanceof FFSException))
/*  8136:      */         {
/*  8137: 9015 */           paramLocalFrame.setException(localThrowable14);
/*  8138: 9016 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  8139: 9017 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable14);
/*  8140:      */         }
/*  8141: 9019 */         localThrowable14.printStackTrace(Jaguar.log);
/*  8142: 9020 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/*  8143: 9021 */         return localThrowable14.getClass().getName();
/*  8144:      */       }
/*  8145:      */     case 115: 
/*  8146:      */       try
/*  8147:      */       {
/*  8148:      */         CCEntryInfo localCCEntryInfo4;
/*  8149: 9030 */         if (!paramBoolean)
/*  8150:      */         {
/*  8151: 9032 */           localCCEntryInfo4 = (CCEntryInfo)paramLocalFrame.get(0);
/*  8152:      */         }
/*  8153:      */         else
/*  8154:      */         {
/*  8155: 9036 */           localObject1 = paramLocalFrame.get(0);
/*  8156: 9037 */           localCCEntryInfo4 = (CCEntryInfo)ObjectVal.clone(localObject1);
/*  8157:      */         }
/*  8158: 9039 */         localObject1 = 
/*  8159: 9040 */           paramBPWServicesBean.getCCEntry(
/*  8160: 9041 */           localCCEntryInfo4);
/*  8161:      */         
/*  8162: 9043 */         paramLocalFrame.setResult(localObject1);
/*  8163:      */       }
/*  8164:      */       catch (Throwable localThrowable15)
/*  8165:      */       {
/*  8166: 9047 */         if ((localThrowable15 instanceof FFSException))
/*  8167:      */         {
/*  8168: 9049 */           paramLocalFrame.setException(localThrowable15);
/*  8169: 9050 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  8170: 9051 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable15);
/*  8171:      */         }
/*  8172: 9053 */         localThrowable15.printStackTrace(Jaguar.log);
/*  8173: 9054 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/*  8174: 9055 */         return localThrowable15.getClass().getName();
/*  8175:      */       }
/*  8176:      */     case 116: 
/*  8177:      */       try
/*  8178:      */       {
/*  8179:      */         CCEntryHistInfo localCCEntryHistInfo;
/*  8180: 9064 */         if (!paramBoolean)
/*  8181:      */         {
/*  8182: 9066 */           localCCEntryHistInfo = (CCEntryHistInfo)paramLocalFrame.get(0);
/*  8183:      */         }
/*  8184:      */         else
/*  8185:      */         {
/*  8186: 9070 */           localObject1 = paramLocalFrame.get(0);
/*  8187: 9071 */           localCCEntryHistInfo = (CCEntryHistInfo)ObjectVal.clone(localObject1);
/*  8188:      */         }
/*  8189: 9073 */         localObject1 = 
/*  8190: 9074 */           paramBPWServicesBean.getCCEntryHist(
/*  8191: 9075 */           localCCEntryHistInfo);
/*  8192:      */         
/*  8193: 9077 */         paramLocalFrame.setResult(localObject1);
/*  8194:      */       }
/*  8195:      */       catch (Throwable localThrowable16)
/*  8196:      */       {
/*  8197: 9081 */         if ((localThrowable16 instanceof FFSException))
/*  8198:      */         {
/*  8199: 9083 */           paramLocalFrame.setException(localThrowable16);
/*  8200: 9084 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  8201: 9085 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable16);
/*  8202:      */         }
/*  8203: 9087 */         localThrowable16.printStackTrace(Jaguar.log);
/*  8204: 9088 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/*  8205: 9089 */         return localThrowable16.getClass().getName();
/*  8206:      */       }
/*  8207:      */     case 117: 
/*  8208:      */       try
/*  8209:      */       {
/*  8210:      */         CCEntrySummaryInfoList localCCEntrySummaryInfoList;
/*  8211: 9098 */         if (!paramBoolean)
/*  8212:      */         {
/*  8213: 9100 */           localCCEntrySummaryInfoList = (CCEntrySummaryInfoList)paramLocalFrame.get(0);
/*  8214:      */         }
/*  8215:      */         else
/*  8216:      */         {
/*  8217: 9104 */           localObject1 = paramLocalFrame.get(0);
/*  8218: 9105 */           localCCEntrySummaryInfoList = (CCEntrySummaryInfoList)ObjectVal.clone(localObject1);
/*  8219:      */         }
/*  8220: 9107 */         localObject1 = 
/*  8221: 9108 */           paramBPWServicesBean.getCCEntrySummaryList(
/*  8222: 9109 */           localCCEntrySummaryInfoList);
/*  8223:      */         
/*  8224: 9111 */         paramLocalFrame.setResult(localObject1);
/*  8225:      */       }
/*  8226:      */       catch (Throwable localThrowable17)
/*  8227:      */       {
/*  8228: 9115 */         if ((localThrowable17 instanceof FFSException))
/*  8229:      */         {
/*  8230: 9117 */           paramLocalFrame.setException(localThrowable17);
/*  8231: 9118 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  8232: 9119 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable17);
/*  8233:      */         }
/*  8234: 9121 */         localThrowable17.printStackTrace(Jaguar.log);
/*  8235: 9122 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/*  8236: 9123 */         return localThrowable17.getClass().getName();
/*  8237:      */       }
/*  8238:      */     case 118: 
/*  8239:      */       try
/*  8240:      */       {
/*  8241:      */         TransferInfo localTransferInfo1;
/*  8242: 9132 */         if (!paramBoolean)
/*  8243:      */         {
/*  8244: 9134 */           localTransferInfo1 = (TransferInfo)paramLocalFrame.get(0);
/*  8245:      */         }
/*  8246:      */         else
/*  8247:      */         {
/*  8248: 9138 */           localObject1 = paramLocalFrame.get(0);
/*  8249: 9139 */           localTransferInfo1 = (TransferInfo)ObjectVal.clone(localObject1);
/*  8250:      */         }
/*  8251: 9141 */         localObject1 = 
/*  8252: 9142 */           paramBPWServicesBean.addTransfer(
/*  8253: 9143 */           localTransferInfo1);
/*  8254:      */         
/*  8255: 9145 */         paramLocalFrame.setResult(localObject1);
/*  8256:      */       }
/*  8257:      */       catch (Throwable localThrowable18)
/*  8258:      */       {
/*  8259: 9149 */         localThrowable18.printStackTrace(Jaguar.log);
/*  8260: 9150 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/*  8261: 9151 */         return localThrowable18.getClass().getName();
/*  8262:      */       }
/*  8263:      */     case 119: 
/*  8264:      */       try
/*  8265:      */       {
/*  8266:      */         TransferInfo localTransferInfo2;
/*  8267: 9160 */         if (!paramBoolean)
/*  8268:      */         {
/*  8269: 9162 */           localTransferInfo2 = (TransferInfo)paramLocalFrame.get(0);
/*  8270:      */         }
/*  8271:      */         else
/*  8272:      */         {
/*  8273: 9166 */           localObject1 = paramLocalFrame.get(0);
/*  8274: 9167 */           localTransferInfo2 = (TransferInfo)ObjectVal.clone(localObject1);
/*  8275:      */         }
/*  8276: 9169 */         localObject1 = 
/*  8277: 9170 */           paramBPWServicesBean.modTransfer(
/*  8278: 9171 */           localTransferInfo2);
/*  8279:      */         
/*  8280: 9173 */         paramLocalFrame.setResult(localObject1);
/*  8281:      */       }
/*  8282:      */       catch (Throwable localThrowable19)
/*  8283:      */       {
/*  8284: 9177 */         localThrowable19.printStackTrace(Jaguar.log);
/*  8285: 9178 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/*  8286: 9179 */         return localThrowable19.getClass().getName();
/*  8287:      */       }
/*  8288:      */     case 120: 
/*  8289:      */       try
/*  8290:      */       {
/*  8291:      */         TransferInfo localTransferInfo3;
/*  8292: 9188 */         if (!paramBoolean)
/*  8293:      */         {
/*  8294: 9190 */           localTransferInfo3 = (TransferInfo)paramLocalFrame.get(0);
/*  8295:      */         }
/*  8296:      */         else
/*  8297:      */         {
/*  8298: 9194 */           localObject1 = paramLocalFrame.get(0);
/*  8299: 9195 */           localTransferInfo3 = (TransferInfo)ObjectVal.clone(localObject1);
/*  8300:      */         }
/*  8301: 9197 */         localObject1 = 
/*  8302: 9198 */           paramBPWServicesBean.cancTransfer(
/*  8303: 9199 */           localTransferInfo3);
/*  8304:      */         
/*  8305: 9201 */         paramLocalFrame.setResult(localObject1);
/*  8306:      */       }
/*  8307:      */       catch (Throwable localThrowable20)
/*  8308:      */       {
/*  8309: 9205 */         localThrowable20.printStackTrace(Jaguar.log);
/*  8310: 9206 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/*  8311: 9207 */         return localThrowable20.getClass().getName();
/*  8312:      */       }
/*  8313:      */     case 121: 
/*  8314:      */       try
/*  8315:      */       {
/*  8316: 9216 */         String str1 = (String)paramLocalFrame.get(0);
/*  8317:      */         
/*  8318: 9218 */         localObject1 = (String)paramLocalFrame.get(1);
/*  8319: 9219 */         localObject3 = paramBPWServicesBean
/*  8320: 9220 */           .getTransferBySrvrTId(
/*  8321: 9221 */           str1, 
/*  8322: 9222 */           (String)localObject1);
/*  8323:      */         
/*  8324: 9224 */         paramLocalFrame.setResult(localObject3);
/*  8325:      */       }
/*  8326:      */       catch (Throwable localThrowable21)
/*  8327:      */       {
/*  8328: 9228 */         localThrowable21.printStackTrace(Jaguar.log);
/*  8329: 9229 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/*  8330: 9230 */         return localThrowable21.getClass().getName();
/*  8331:      */       }
/*  8332:      */     case 122: 
/*  8333:      */       try
/*  8334:      */       {
/*  8335: 9239 */         String str2 = (String)paramLocalFrame.get(0);
/*  8336: 9240 */         localObject1 = paramBPWServicesBean
/*  8337: 9241 */           .getTransferBySrvrTId(
/*  8338: 9242 */           str2);
/*  8339:      */         
/*  8340: 9244 */         paramLocalFrame.setResult(localObject1);
/*  8341:      */       }
/*  8342:      */       catch (Throwable localThrowable22)
/*  8343:      */       {
/*  8344: 9248 */         localThrowable22.printStackTrace(Jaguar.log);
/*  8345: 9249 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/*  8346: 9250 */         return localThrowable22.getClass().getName();
/*  8347:      */       }
/*  8348:      */     case 123: 
/*  8349:      */       try
/*  8350:      */       {
/*  8351: 9259 */         String str3 = (String)paramLocalFrame.get(0);
/*  8352: 9260 */         localObject1 = paramBPWServicesBean
/*  8353: 9261 */           .getTransferByTrackingId(
/*  8354: 9262 */           str3);
/*  8355:      */         
/*  8356: 9264 */         paramLocalFrame.setResult(localObject1);
/*  8357:      */       }
/*  8358:      */       catch (Throwable localThrowable23)
/*  8359:      */       {
/*  8360: 9268 */         localThrowable23.printStackTrace(Jaguar.log);
/*  8361: 9269 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/*  8362: 9270 */         return localThrowable23.getClass().getName();
/*  8363:      */       }
/*  8364:      */     case 124: 
/*  8365:      */       try
/*  8366:      */       {
/*  8367:      */         String[] arrayOfString1;
/*  8368: 9279 */         if (!paramBoolean)
/*  8369:      */         {
/*  8370: 9281 */           arrayOfString1 = (String[])paramLocalFrame.get(0);
/*  8371:      */         }
/*  8372:      */         else
/*  8373:      */         {
/*  8374: 9285 */           localObject1 = paramLocalFrame.get(0);
/*  8375: 9286 */           arrayOfString1 = (String[])ObjectVal.clone(localObject1);
/*  8376:      */         }
/*  8377: 9288 */         localObject1 = 
/*  8378: 9289 */           paramBPWServicesBean.getTransfersBySrvrTId(
/*  8379: 9290 */           arrayOfString1);
/*  8380:      */         
/*  8381: 9292 */         paramLocalFrame.setResult(localObject1);
/*  8382:      */       }
/*  8383:      */       catch (Throwable localThrowable24)
/*  8384:      */       {
/*  8385: 9296 */         localThrowable24.printStackTrace(Jaguar.log);
/*  8386: 9297 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/*  8387: 9298 */         return localThrowable24.getClass().getName();
/*  8388:      */       }
/*  8389:      */     case 125: 
/*  8390:      */       try
/*  8391:      */       {
/*  8392:      */         String[] arrayOfString2;
/*  8393: 9307 */         if (!paramBoolean)
/*  8394:      */         {
/*  8395: 9309 */           arrayOfString2 = (String[])paramLocalFrame.get(0);
/*  8396:      */         }
/*  8397:      */         else
/*  8398:      */         {
/*  8399: 9313 */           localObject1 = paramLocalFrame.get(0);
/*  8400: 9314 */           arrayOfString2 = (String[])ObjectVal.clone(localObject1);
/*  8401:      */         }
/*  8402: 9317 */         bool = ((Boolean)paramLocalFrame.get(1)).booleanValue();
/*  8403: 9318 */         localObject3 = paramBPWServicesBean
/*  8404: 9319 */           .getTransfersByRecSrvrTId(
/*  8405: 9320 */           arrayOfString2, 
/*  8406: 9321 */           bool);
/*  8407:      */         
/*  8408: 9323 */         paramLocalFrame.setResult(localObject3);
/*  8409:      */       }
/*  8410:      */       catch (Throwable localThrowable25)
/*  8411:      */       {
/*  8412: 9327 */         localThrowable25.printStackTrace(Jaguar.log);
/*  8413: 9328 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/*  8414: 9329 */         return localThrowable25.getClass().getName();
/*  8415:      */       }
/*  8416:      */     case 126: 
/*  8417:      */       try
/*  8418:      */       {
/*  8419: 9338 */         String str4 = (String)paramLocalFrame.get(0);
/*  8420:      */         
/*  8421: 9340 */         bool = ((Boolean)paramLocalFrame.get(1)).booleanValue();
/*  8422: 9341 */         localObject3 = paramBPWServicesBean
/*  8423: 9342 */           .getTransfersByRecSrvrTId(
/*  8424: 9343 */           str4, 
/*  8425: 9344 */           bool);
/*  8426:      */         
/*  8427: 9346 */         paramLocalFrame.setResult(localObject3);
/*  8428:      */       }
/*  8429:      */       catch (Throwable localThrowable26)
/*  8430:      */       {
/*  8431: 9350 */         localThrowable26.printStackTrace(Jaguar.log);
/*  8432: 9351 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/*  8433: 9352 */         return localThrowable26.getClass().getName();
/*  8434:      */       }
/*  8435:      */     case 127: 
/*  8436:      */       try
/*  8437:      */       {
/*  8438:      */         String[] arrayOfString3;
/*  8439: 9361 */         if (!paramBoolean)
/*  8440:      */         {
/*  8441: 9363 */           arrayOfString3 = (String[])paramLocalFrame.get(0);
/*  8442:      */         }
/*  8443:      */         else
/*  8444:      */         {
/*  8445: 9367 */           localObject2 = paramLocalFrame.get(0);
/*  8446: 9368 */           arrayOfString3 = (String[])ObjectVal.clone(localObject2);
/*  8447:      */         }
/*  8448: 9370 */         localObject2 = 
/*  8449: 9371 */           paramBPWServicesBean.getTransfersByTrackingId(
/*  8450: 9372 */           arrayOfString3);
/*  8451:      */         
/*  8452: 9374 */         paramLocalFrame.setResult(localObject2);
/*  8453:      */       }
/*  8454:      */       catch (Throwable localThrowable27)
/*  8455:      */       {
/*  8456: 9378 */         localThrowable27.printStackTrace(Jaguar.log);
/*  8457: 9379 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/*  8458: 9380 */         return localThrowable27.getClass().getName();
/*  8459:      */       }
/*  8460:      */     case 128: 
/*  8461:      */       try
/*  8462:      */       {
/*  8463:      */         BPWHist localBPWHist1;
/*  8464: 9389 */         if (!paramBoolean)
/*  8465:      */         {
/*  8466: 9391 */           localBPWHist1 = (BPWHist)paramLocalFrame.get(0);
/*  8467:      */         }
/*  8468:      */         else
/*  8469:      */         {
/*  8470: 9395 */           localObject2 = paramLocalFrame.get(0);
/*  8471: 9396 */           localBPWHist1 = (BPWHist)ObjectVal.clone(localObject2);
/*  8472:      */         }
/*  8473: 9398 */         localObject2 = 
/*  8474: 9399 */           paramBPWServicesBean.getTransferHistory(
/*  8475: 9400 */           localBPWHist1);
/*  8476:      */         
/*  8477: 9402 */         paramLocalFrame.setResult(localObject2);
/*  8478:      */       }
/*  8479:      */       catch (Throwable localThrowable28)
/*  8480:      */       {
/*  8481: 9406 */         localThrowable28.printStackTrace(Jaguar.log);
/*  8482: 9407 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/*  8483: 9408 */         return localThrowable28.getClass().getName();
/*  8484:      */       }
/*  8485:      */     case 129: 
/*  8486:      */       try
/*  8487:      */       {
/*  8488:      */         TransferInfo[] arrayOfTransferInfo;
/*  8489: 9417 */         if (!paramBoolean)
/*  8490:      */         {
/*  8491: 9419 */           arrayOfTransferInfo = (TransferInfo[])paramLocalFrame.get(0);
/*  8492:      */         }
/*  8493:      */         else
/*  8494:      */         {
/*  8495: 9423 */           localObject2 = paramLocalFrame.get(0);
/*  8496: 9424 */           arrayOfTransferInfo = (TransferInfo[])ObjectVal.clone(localObject2);
/*  8497:      */         }
/*  8498: 9426 */         localObject2 = 
/*  8499: 9427 */           paramBPWServicesBean.addTransfers(
/*  8500: 9428 */           arrayOfTransferInfo);
/*  8501:      */         
/*  8502: 9430 */         paramLocalFrame.setResult(localObject2);
/*  8503:      */       }
/*  8504:      */       catch (Throwable localThrowable29)
/*  8505:      */       {
/*  8506: 9434 */         localThrowable29.printStackTrace(Jaguar.log);
/*  8507: 9435 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/*  8508: 9436 */         return localThrowable29.getClass().getName();
/*  8509:      */       }
/*  8510:      */     case 130: 
/*  8511:      */       try
/*  8512:      */       {
/*  8513:      */         RecTransferInfo localRecTransferInfo1;
/*  8514: 9445 */         if (!paramBoolean)
/*  8515:      */         {
/*  8516: 9447 */           localRecTransferInfo1 = (RecTransferInfo)paramLocalFrame.get(0);
/*  8517:      */         }
/*  8518:      */         else
/*  8519:      */         {
/*  8520: 9451 */           localObject2 = paramLocalFrame.get(0);
/*  8521: 9452 */           localRecTransferInfo1 = (RecTransferInfo)ObjectVal.clone(localObject2);
/*  8522:      */         }
/*  8523: 9454 */         localObject2 = 
/*  8524: 9455 */           paramBPWServicesBean.addRecTransfer(
/*  8525: 9456 */           localRecTransferInfo1);
/*  8526:      */         
/*  8527: 9458 */         paramLocalFrame.setResult(localObject2);
/*  8528:      */       }
/*  8529:      */       catch (Throwable localThrowable30)
/*  8530:      */       {
/*  8531: 9462 */         localThrowable30.printStackTrace(Jaguar.log);
/*  8532: 9463 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/*  8533: 9464 */         return localThrowable30.getClass().getName();
/*  8534:      */       }
/*  8535:      */     case 131: 
/*  8536:      */       try
/*  8537:      */       {
/*  8538:      */         RecTransferInfo localRecTransferInfo2;
/*  8539: 9473 */         if (!paramBoolean)
/*  8540:      */         {
/*  8541: 9475 */           localRecTransferInfo2 = (RecTransferInfo)paramLocalFrame.get(0);
/*  8542:      */         }
/*  8543:      */         else
/*  8544:      */         {
/*  8545: 9479 */           localObject2 = paramLocalFrame.get(0);
/*  8546: 9480 */           localRecTransferInfo2 = (RecTransferInfo)ObjectVal.clone(localObject2);
/*  8547:      */         }
/*  8548: 9482 */         localObject2 = 
/*  8549: 9483 */           paramBPWServicesBean.modRecTransfer(
/*  8550: 9484 */           localRecTransferInfo2);
/*  8551:      */         
/*  8552: 9486 */         paramLocalFrame.setResult(localObject2);
/*  8553:      */       }
/*  8554:      */       catch (Throwable localThrowable31)
/*  8555:      */       {
/*  8556: 9490 */         localThrowable31.printStackTrace(Jaguar.log);
/*  8557: 9491 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/*  8558: 9492 */         return localThrowable31.getClass().getName();
/*  8559:      */       }
/*  8560:      */     case 132: 
/*  8561:      */       try
/*  8562:      */       {
/*  8563:      */         RecTransferInfo localRecTransferInfo3;
/*  8564: 9501 */         if (!paramBoolean)
/*  8565:      */         {
/*  8566: 9503 */           localRecTransferInfo3 = (RecTransferInfo)paramLocalFrame.get(0);
/*  8567:      */         }
/*  8568:      */         else
/*  8569:      */         {
/*  8570: 9507 */           localObject2 = paramLocalFrame.get(0);
/*  8571: 9508 */           localRecTransferInfo3 = (RecTransferInfo)ObjectVal.clone(localObject2);
/*  8572:      */         }
/*  8573: 9510 */         localObject2 = 
/*  8574: 9511 */           paramBPWServicesBean.cancRecTransfer(
/*  8575: 9512 */           localRecTransferInfo3);
/*  8576:      */         
/*  8577: 9514 */         paramLocalFrame.setResult(localObject2);
/*  8578:      */       }
/*  8579:      */       catch (Throwable localThrowable32)
/*  8580:      */       {
/*  8581: 9518 */         localThrowable32.printStackTrace(Jaguar.log);
/*  8582: 9519 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/*  8583: 9520 */         return localThrowable32.getClass().getName();
/*  8584:      */       }
/*  8585:      */     case 133: 
/*  8586:      */       try
/*  8587:      */       {
/*  8588: 9529 */         String str5 = (String)paramLocalFrame.get(0);
/*  8589:      */         
/*  8590: 9531 */         localObject2 = (String)paramLocalFrame.get(1);
/*  8591: 9532 */         localObject3 = paramBPWServicesBean
/*  8592: 9533 */           .getRecTransferBySrvrTId(
/*  8593: 9534 */           str5, 
/*  8594: 9535 */           (String)localObject2);
/*  8595:      */         
/*  8596: 9537 */         paramLocalFrame.setResult(localObject3);
/*  8597:      */       }
/*  8598:      */       catch (Throwable localThrowable33)
/*  8599:      */       {
/*  8600: 9541 */         localThrowable33.printStackTrace(Jaguar.log);
/*  8601: 9542 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/*  8602: 9543 */         return localThrowable33.getClass().getName();
/*  8603:      */       }
/*  8604:      */     case 134: 
/*  8605:      */       try
/*  8606:      */       {
/*  8607: 9552 */         String str6 = (String)paramLocalFrame.get(0);
/*  8608: 9553 */         localObject2 = paramBPWServicesBean
/*  8609: 9554 */           .getRecTransferBySrvrTId(
/*  8610: 9555 */           str6);
/*  8611:      */         
/*  8612: 9557 */         paramLocalFrame.setResult(localObject2);
/*  8613:      */       }
/*  8614:      */       catch (Throwable localThrowable34)
/*  8615:      */       {
/*  8616: 9561 */         localThrowable34.printStackTrace(Jaguar.log);
/*  8617: 9562 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/*  8618: 9563 */         return localThrowable34.getClass().getName();
/*  8619:      */       }
/*  8620:      */     case 135: 
/*  8621:      */       try
/*  8622:      */       {
/*  8623: 9572 */         String str7 = (String)paramLocalFrame.get(0);
/*  8624: 9573 */         localObject2 = paramBPWServicesBean
/*  8625: 9574 */           .getRecTransferByTrackingId(
/*  8626: 9575 */           str7);
/*  8627:      */         
/*  8628: 9577 */         paramLocalFrame.setResult(localObject2);
/*  8629:      */       }
/*  8630:      */       catch (Throwable localThrowable35)
/*  8631:      */       {
/*  8632: 9581 */         localThrowable35.printStackTrace(Jaguar.log);
/*  8633: 9582 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/*  8634: 9583 */         return localThrowable35.getClass().getName();
/*  8635:      */       }
/*  8636:      */     case 136: 
/*  8637:      */       try
/*  8638:      */       {
/*  8639:      */         String[] arrayOfString4;
/*  8640: 9592 */         if (!paramBoolean)
/*  8641:      */         {
/*  8642: 9594 */           arrayOfString4 = (String[])paramLocalFrame.get(0);
/*  8643:      */         }
/*  8644:      */         else
/*  8645:      */         {
/*  8646: 9598 */           localObject2 = paramLocalFrame.get(0);
/*  8647: 9599 */           arrayOfString4 = (String[])ObjectVal.clone(localObject2);
/*  8648:      */         }
/*  8649: 9601 */         localObject2 = 
/*  8650: 9602 */           paramBPWServicesBean.getRecTransfersBySrvrTId(
/*  8651: 9603 */           arrayOfString4);
/*  8652:      */         
/*  8653: 9605 */         paramLocalFrame.setResult(localObject2);
/*  8654:      */       }
/*  8655:      */       catch (Throwable localThrowable36)
/*  8656:      */       {
/*  8657: 9609 */         localThrowable36.printStackTrace(Jaguar.log);
/*  8658: 9610 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/*  8659: 9611 */         return localThrowable36.getClass().getName();
/*  8660:      */       }
/*  8661:      */     case 137: 
/*  8662:      */       try
/*  8663:      */       {
/*  8664:      */         BPWHist localBPWHist2;
/*  8665: 9620 */         if (!paramBoolean)
/*  8666:      */         {
/*  8667: 9622 */           localBPWHist2 = (BPWHist)paramLocalFrame.get(0);
/*  8668:      */         }
/*  8669:      */         else
/*  8670:      */         {
/*  8671: 9626 */           localObject2 = paramLocalFrame.get(0);
/*  8672: 9627 */           localBPWHist2 = (BPWHist)ObjectVal.clone(localObject2);
/*  8673:      */         }
/*  8674: 9629 */         localObject2 = 
/*  8675: 9630 */           paramBPWServicesBean.getRecTransfers(
/*  8676: 9631 */           localBPWHist2);
/*  8677:      */         
/*  8678: 9633 */         paramLocalFrame.setResult(localObject2);
/*  8679:      */       }
/*  8680:      */       catch (Throwable localThrowable37)
/*  8681:      */       {
/*  8682: 9637 */         localThrowable37.printStackTrace(Jaguar.log);
/*  8683: 9638 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable37, true);
/*  8684: 9639 */         return localThrowable37.getClass().getName();
/*  8685:      */       }
/*  8686:      */     case 138: 
/*  8687:      */       try
/*  8688:      */       {
/*  8689:      */         String[] arrayOfString5;
/*  8690: 9648 */         if (!paramBoolean)
/*  8691:      */         {
/*  8692: 9650 */           arrayOfString5 = (String[])paramLocalFrame.get(0);
/*  8693:      */         }
/*  8694:      */         else
/*  8695:      */         {
/*  8696: 9654 */           localObject2 = paramLocalFrame.get(0);
/*  8697: 9655 */           arrayOfString5 = (String[])ObjectVal.clone(localObject2);
/*  8698:      */         }
/*  8699: 9657 */         localObject2 = 
/*  8700: 9658 */           paramBPWServicesBean.getRecTransfersByTrackingId(
/*  8701: 9659 */           arrayOfString5);
/*  8702:      */         
/*  8703: 9661 */         paramLocalFrame.setResult(localObject2);
/*  8704:      */       }
/*  8705:      */       catch (Throwable localThrowable38)
/*  8706:      */       {
/*  8707: 9665 */         localThrowable38.printStackTrace(Jaguar.log);
/*  8708: 9666 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable38, true);
/*  8709: 9667 */         return localThrowable38.getClass().getName();
/*  8710:      */       }
/*  8711:      */     case 139: 
/*  8712:      */       try
/*  8713:      */       {
/*  8714:      */         BPWHist localBPWHist3;
/*  8715: 9676 */         if (!paramBoolean)
/*  8716:      */         {
/*  8717: 9678 */           localBPWHist3 = (BPWHist)paramLocalFrame.get(0);
/*  8718:      */         }
/*  8719:      */         else
/*  8720:      */         {
/*  8721: 9682 */           localObject2 = paramLocalFrame.get(0);
/*  8722: 9683 */           localBPWHist3 = (BPWHist)ObjectVal.clone(localObject2);
/*  8723:      */         }
/*  8724: 9685 */         localObject2 = 
/*  8725: 9686 */           paramBPWServicesBean.getRecTransferHistory(
/*  8726: 9687 */           localBPWHist3);
/*  8727:      */         
/*  8728: 9689 */         paramLocalFrame.setResult(localObject2);
/*  8729:      */       }
/*  8730:      */       catch (Throwable localThrowable39)
/*  8731:      */       {
/*  8732: 9693 */         localThrowable39.printStackTrace(Jaguar.log);
/*  8733: 9694 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable39, true);
/*  8734: 9695 */         return localThrowable39.getClass().getName();
/*  8735:      */       }
/*  8736:      */     case 140: 
/*  8737:      */       try
/*  8738:      */       {
/*  8739:      */         ExtTransferCompanyInfo localExtTransferCompanyInfo1;
/*  8740: 9704 */         if (!paramBoolean)
/*  8741:      */         {
/*  8742: 9706 */           localExtTransferCompanyInfo1 = (ExtTransferCompanyInfo)paramLocalFrame.get(0);
/*  8743:      */         }
/*  8744:      */         else
/*  8745:      */         {
/*  8746: 9710 */           localObject2 = paramLocalFrame.get(0);
/*  8747: 9711 */           localExtTransferCompanyInfo1 = (ExtTransferCompanyInfo)ObjectVal.clone(localObject2);
/*  8748:      */         }
/*  8749: 9713 */         localObject2 = 
/*  8750: 9714 */           paramBPWServicesBean.addExtTransferCompany(
/*  8751: 9715 */           localExtTransferCompanyInfo1);
/*  8752:      */         
/*  8753: 9717 */         paramLocalFrame.setResult(localObject2);
/*  8754:      */       }
/*  8755:      */       catch (Throwable localThrowable40)
/*  8756:      */       {
/*  8757: 9721 */         localThrowable40.printStackTrace(Jaguar.log);
/*  8758: 9722 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable40, true);
/*  8759: 9723 */         return localThrowable40.getClass().getName();
/*  8760:      */       }
/*  8761:      */     case 141: 
/*  8762:      */       try
/*  8763:      */       {
/*  8764:      */         ExtTransferCompanyInfo localExtTransferCompanyInfo2;
/*  8765: 9732 */         if (!paramBoolean)
/*  8766:      */         {
/*  8767: 9734 */           localExtTransferCompanyInfo2 = (ExtTransferCompanyInfo)paramLocalFrame.get(0);
/*  8768:      */         }
/*  8769:      */         else
/*  8770:      */         {
/*  8771: 9738 */           localObject2 = paramLocalFrame.get(0);
/*  8772: 9739 */           localExtTransferCompanyInfo2 = (ExtTransferCompanyInfo)ObjectVal.clone(localObject2);
/*  8773:      */         }
/*  8774: 9741 */         localObject2 = 
/*  8775: 9742 */           paramBPWServicesBean.canExtTransferCompany(
/*  8776: 9743 */           localExtTransferCompanyInfo2);
/*  8777:      */         
/*  8778: 9745 */         paramLocalFrame.setResult(localObject2);
/*  8779:      */       }
/*  8780:      */       catch (Throwable localThrowable41)
/*  8781:      */       {
/*  8782: 9749 */         localThrowable41.printStackTrace(Jaguar.log);
/*  8783: 9750 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable41, true);
/*  8784: 9751 */         return localThrowable41.getClass().getName();
/*  8785:      */       }
/*  8786:      */     case 142: 
/*  8787:      */       try
/*  8788:      */       {
/*  8789:      */         ExtTransferCompanyInfo localExtTransferCompanyInfo3;
/*  8790: 9760 */         if (!paramBoolean)
/*  8791:      */         {
/*  8792: 9762 */           localExtTransferCompanyInfo3 = (ExtTransferCompanyInfo)paramLocalFrame.get(0);
/*  8793:      */         }
/*  8794:      */         else
/*  8795:      */         {
/*  8796: 9766 */           localObject2 = paramLocalFrame.get(0);
/*  8797: 9767 */           localExtTransferCompanyInfo3 = (ExtTransferCompanyInfo)ObjectVal.clone(localObject2);
/*  8798:      */         }
/*  8799: 9769 */         localObject2 = 
/*  8800: 9770 */           paramBPWServicesBean.modExtTransferCompany(
/*  8801: 9771 */           localExtTransferCompanyInfo3);
/*  8802:      */         
/*  8803: 9773 */         paramLocalFrame.setResult(localObject2);
/*  8804:      */       }
/*  8805:      */       catch (Throwable localThrowable42)
/*  8806:      */       {
/*  8807: 9777 */         localThrowable42.printStackTrace(Jaguar.log);
/*  8808: 9778 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable42, true);
/*  8809: 9779 */         return localThrowable42.getClass().getName();
/*  8810:      */       }
/*  8811:      */     case 143: 
/*  8812:      */       try
/*  8813:      */       {
/*  8814:      */         ExtTransferCompanyInfo localExtTransferCompanyInfo4;
/*  8815: 9788 */         if (!paramBoolean)
/*  8816:      */         {
/*  8817: 9790 */           localExtTransferCompanyInfo4 = (ExtTransferCompanyInfo)paramLocalFrame.get(0);
/*  8818:      */         }
/*  8819:      */         else
/*  8820:      */         {
/*  8821: 9794 */           localObject2 = paramLocalFrame.get(0);
/*  8822: 9795 */           localExtTransferCompanyInfo4 = (ExtTransferCompanyInfo)ObjectVal.clone(localObject2);
/*  8823:      */         }
/*  8824: 9797 */         localObject2 = 
/*  8825: 9798 */           paramBPWServicesBean.getExtTransferCompany(
/*  8826: 9799 */           localExtTransferCompanyInfo4);
/*  8827:      */         
/*  8828: 9801 */         paramLocalFrame.setResult(localObject2);
/*  8829:      */       }
/*  8830:      */       catch (Throwable localThrowable43)
/*  8831:      */       {
/*  8832: 9805 */         localThrowable43.printStackTrace(Jaguar.log);
/*  8833: 9806 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable43, true);
/*  8834: 9807 */         return localThrowable43.getClass().getName();
/*  8835:      */       }
/*  8836:      */     case 144: 
/*  8837:      */       try
/*  8838:      */       {
/*  8839:      */         ExtTransferCompanyList localExtTransferCompanyList;
/*  8840: 9816 */         if (!paramBoolean)
/*  8841:      */         {
/*  8842: 9818 */           localExtTransferCompanyList = (ExtTransferCompanyList)paramLocalFrame.get(0);
/*  8843:      */         }
/*  8844:      */         else
/*  8845:      */         {
/*  8846: 9822 */           localObject2 = paramLocalFrame.get(0);
/*  8847: 9823 */           localExtTransferCompanyList = (ExtTransferCompanyList)ObjectVal.clone(localObject2);
/*  8848:      */         }
/*  8849: 9825 */         localObject2 = 
/*  8850: 9826 */           paramBPWServicesBean.getExtTransferCompanyList(
/*  8851: 9827 */           localExtTransferCompanyList);
/*  8852:      */         
/*  8853: 9829 */         paramLocalFrame.setResult(localObject2);
/*  8854:      */       }
/*  8855:      */       catch (Throwable localThrowable44)
/*  8856:      */       {
/*  8857: 9833 */         localThrowable44.printStackTrace(Jaguar.log);
/*  8858: 9834 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable44, true);
/*  8859: 9835 */         return localThrowable44.getClass().getName();
/*  8860:      */       }
/*  8861:      */     case 145: 
/*  8862:      */       try
/*  8863:      */       {
/*  8864:      */         ExtTransferAcctInfo localExtTransferAcctInfo1;
/*  8865: 9844 */         if (!paramBoolean)
/*  8866:      */         {
/*  8867: 9846 */           localExtTransferAcctInfo1 = (ExtTransferAcctInfo)paramLocalFrame.get(0);
/*  8868:      */         }
/*  8869:      */         else
/*  8870:      */         {
/*  8871: 9850 */           localObject2 = paramLocalFrame.get(0);
/*  8872: 9851 */           localExtTransferAcctInfo1 = (ExtTransferAcctInfo)ObjectVal.clone(localObject2);
/*  8873:      */         }
/*  8874: 9853 */         localObject2 = 
/*  8875: 9854 */           paramBPWServicesBean.addExtTransferAccount(
/*  8876: 9855 */           localExtTransferAcctInfo1);
/*  8877:      */         
/*  8878: 9857 */         paramLocalFrame.setResult(localObject2);
/*  8879:      */       }
/*  8880:      */       catch (Throwable localThrowable45)
/*  8881:      */       {
/*  8882: 9861 */         localThrowable45.printStackTrace(Jaguar.log);
/*  8883: 9862 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable45, true);
/*  8884: 9863 */         return localThrowable45.getClass().getName();
/*  8885:      */       }
/*  8886:      */     case 146: 
/*  8887:      */       try
/*  8888:      */       {
/*  8889:      */         ExtTransferAcctInfo localExtTransferAcctInfo2;
/*  8890: 9872 */         if (!paramBoolean)
/*  8891:      */         {
/*  8892: 9874 */           localExtTransferAcctInfo2 = (ExtTransferAcctInfo)paramLocalFrame.get(0);
/*  8893:      */         }
/*  8894:      */         else
/*  8895:      */         {
/*  8896: 9878 */           localObject2 = paramLocalFrame.get(0);
/*  8897: 9879 */           localExtTransferAcctInfo2 = (ExtTransferAcctInfo)ObjectVal.clone(localObject2);
/*  8898:      */         }
/*  8899: 9881 */         localObject2 = 
/*  8900: 9882 */           paramBPWServicesBean.canExtTransferAccount(
/*  8901: 9883 */           localExtTransferAcctInfo2);
/*  8902:      */         
/*  8903: 9885 */         paramLocalFrame.setResult(localObject2);
/*  8904:      */       }
/*  8905:      */       catch (Throwable localThrowable46)
/*  8906:      */       {
/*  8907: 9889 */         localThrowable46.printStackTrace(Jaguar.log);
/*  8908: 9890 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable46, true);
/*  8909: 9891 */         return localThrowable46.getClass().getName();
/*  8910:      */       }
/*  8911:      */     case 147: 
/*  8912:      */       try
/*  8913:      */       {
/*  8914:      */         ExtTransferAcctInfo localExtTransferAcctInfo3;
/*  8915: 9900 */         if (!paramBoolean)
/*  8916:      */         {
/*  8917: 9902 */           localExtTransferAcctInfo3 = (ExtTransferAcctInfo)paramLocalFrame.get(0);
/*  8918:      */         }
/*  8919:      */         else
/*  8920:      */         {
/*  8921: 9906 */           localObject2 = paramLocalFrame.get(0);
/*  8922: 9907 */           localExtTransferAcctInfo3 = (ExtTransferAcctInfo)ObjectVal.clone(localObject2);
/*  8923:      */         }
/*  8924: 9909 */         localObject2 = 
/*  8925: 9910 */           paramBPWServicesBean.modExtTransferAccount(
/*  8926: 9911 */           localExtTransferAcctInfo3);
/*  8927:      */         
/*  8928: 9913 */         paramLocalFrame.setResult(localObject2);
/*  8929:      */       }
/*  8930:      */       catch (Throwable localThrowable47)
/*  8931:      */       {
/*  8932: 9917 */         localThrowable47.printStackTrace(Jaguar.log);
/*  8933: 9918 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable47, true);
/*  8934: 9919 */         return localThrowable47.getClass().getName();
/*  8935:      */       }
/*  8936:      */     case 148: 
/*  8937:      */       try
/*  8938:      */       {
/*  8939:      */         ExtTransferAcctInfo localExtTransferAcctInfo4;
/*  8940: 9928 */         if (!paramBoolean)
/*  8941:      */         {
/*  8942: 9930 */           localExtTransferAcctInfo4 = (ExtTransferAcctInfo)paramLocalFrame.get(0);
/*  8943:      */         }
/*  8944:      */         else
/*  8945:      */         {
/*  8946: 9934 */           localObject2 = paramLocalFrame.get(0);
/*  8947: 9935 */           localExtTransferAcctInfo4 = (ExtTransferAcctInfo)ObjectVal.clone(localObject2);
/*  8948:      */         }
/*  8949: 9937 */         localObject2 = 
/*  8950: 9938 */           paramBPWServicesBean.getExtTransferAccount(
/*  8951: 9939 */           localExtTransferAcctInfo4);
/*  8952:      */         
/*  8953: 9941 */         paramLocalFrame.setResult(localObject2);
/*  8954:      */       }
/*  8955:      */       catch (Throwable localThrowable48)
/*  8956:      */       {
/*  8957: 9945 */         localThrowable48.printStackTrace(Jaguar.log);
/*  8958: 9946 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable48, true);
/*  8959: 9947 */         return localThrowable48.getClass().getName();
/*  8960:      */       }
/*  8961:      */     case 149: 
/*  8962:      */       try
/*  8963:      */       {
/*  8964: 9956 */         String str8 = (String)paramLocalFrame.get(0);
/*  8965: 9958 */         if (!paramBoolean)
/*  8966:      */         {
/*  8967: 9960 */           localObject2 = (ExtTransferAcctInfo)paramLocalFrame.get(1);
/*  8968:      */         }
/*  8969:      */         else
/*  8970:      */         {
/*  8971: 9964 */           localObject3 = paramLocalFrame.get(1);
/*  8972: 9965 */           localObject2 = (ExtTransferAcctInfo)ObjectVal.clone(localObject3);
/*  8973:      */         }
/*  8974: 9968 */         if (!paramBoolean)
/*  8975:      */         {
/*  8976: 9970 */           localObject3 = (int[])paramLocalFrame.get(2);
/*  8977:      */         }
/*  8978:      */         else
/*  8979:      */         {
/*  8980: 9974 */           localObject4 = paramLocalFrame.get(2);
/*  8981: 9975 */           localObject3 = (int[])ObjectVal.clone(localObject4);
/*  8982:      */         }
/*  8983: 9977 */         Object localObject4 = paramBPWServicesBean
/*  8984: 9978 */           .verifyExtTransferAccount(
/*  8985: 9979 */           str8, 
/*  8986: 9980 */           (ExtTransferAcctInfo)localObject2, 
/*  8987: 9981 */           (int[])localObject3);
/*  8988:      */         
/*  8989: 9983 */         paramLocalFrame.setResult(localObject4);
/*  8990:      */       }
/*  8991:      */       catch (Throwable localThrowable49)
/*  8992:      */       {
/*  8993: 9987 */         if ((localThrowable49 instanceof FFSException))
/*  8994:      */         {
/*  8995: 9989 */           paramLocalFrame.setException(localThrowable49);
/*  8996: 9990 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  8997: 9991 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable49);
/*  8998:      */         }
/*  8999: 9993 */         localThrowable49.printStackTrace(Jaguar.log);
/*  9000: 9994 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable49, true);
/*  9001: 9995 */         return localThrowable49.getClass().getName();
/*  9002:      */       }
/*  9003:      */     case 150: 
/*  9004:      */       try
/*  9005:      */       {
/*  9006:10004 */         String str9 = (String)paramLocalFrame.get(0);
/*  9007:10006 */         if (!paramBoolean)
/*  9008:      */         {
/*  9009:10008 */           localObject2 = (ExtTransferAcctInfo)paramLocalFrame.get(1);
/*  9010:      */         }
/*  9011:      */         else
/*  9012:      */         {
/*  9013:10012 */           localObject3 = paramLocalFrame.get(1);
/*  9014:10013 */           localObject2 = (ExtTransferAcctInfo)ObjectVal.clone(localObject3);
/*  9015:      */         }
/*  9016:10015 */         localObject3 = 
/*  9017:10016 */           paramBPWServicesBean.depositAmountsForVerify(
/*  9018:10017 */           str9, 
/*  9019:10018 */           (ExtTransferAcctInfo)localObject2);
/*  9020:      */         
/*  9021:10020 */         paramLocalFrame.setResult(localObject3);
/*  9022:      */       }
/*  9023:      */       catch (Throwable localThrowable50)
/*  9024:      */       {
/*  9025:10024 */         if ((localThrowable50 instanceof FFSException))
/*  9026:      */         {
/*  9027:10026 */           paramLocalFrame.setException(localThrowable50);
/*  9028:10027 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9029:10028 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable50);
/*  9030:      */         }
/*  9031:10030 */         localThrowable50.printStackTrace(Jaguar.log);
/*  9032:10031 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable50, true);
/*  9033:10032 */         return localThrowable50.getClass().getName();
/*  9034:      */       }
/*  9035:      */     default: 
/*  9036:10038 */       return 
/*  9037:10039 */         localInvoke3(
/*  9038:10040 */         param_ServerRequest, 
/*  9039:10041 */         paramInputStream, 
/*  9040:10042 */         paramOutputStream, 
/*  9041:10043 */         paramBPWServicesBean, 
/*  9042:10044 */         paramLocalFrame, 
/*  9043:10045 */         paramInteger, 
/*  9044:10046 */         paramBoolean);
/*  9045:      */     }
/*  9046:10050 */     return null;
/*  9047:      */   }
/*  9048:      */   
/*  9049:      */   private static String localInvoke3(_ServerRequest param_ServerRequest, InputStream paramInputStream, OutputStream paramOutputStream, BPWServicesBean paramBPWServicesBean, LocalFrame paramLocalFrame, Integer paramInteger, boolean paramBoolean)
/*  9050:      */   {
/*  9051:      */     Object localObject1;
/*  9052:      */     Object localObject2;
/*  9053:      */     Object localObject4;
/*  9054:      */     Object localObject3;
/*  9055:10062 */     switch (paramInteger.intValue())
/*  9056:      */     {
/*  9057:      */     case 151: 
/*  9058:      */       try
/*  9059:      */       {
/*  9060:      */         ExtTransferAcctInfo localExtTransferAcctInfo1;
/*  9061:10069 */         if (!paramBoolean)
/*  9062:      */         {
/*  9063:10071 */           localExtTransferAcctInfo1 = (ExtTransferAcctInfo)paramLocalFrame.get(0);
/*  9064:      */         }
/*  9065:      */         else
/*  9066:      */         {
/*  9067:10075 */           localObject1 = paramLocalFrame.get(0);
/*  9068:10076 */           localExtTransferAcctInfo1 = (ExtTransferAcctInfo)ObjectVal.clone(localObject1);
/*  9069:      */         }
/*  9070:10078 */         localObject1 = 
/*  9071:10079 */           paramBPWServicesBean.activateExtTransferAcct(
/*  9072:10080 */           localExtTransferAcctInfo1);
/*  9073:      */         
/*  9074:10082 */         paramLocalFrame.setResult(localObject1);
/*  9075:      */       }
/*  9076:      */       catch (Throwable localThrowable1)
/*  9077:      */       {
/*  9078:10086 */         if ((localThrowable1 instanceof FFSException))
/*  9079:      */         {
/*  9080:10088 */           paramLocalFrame.setException(localThrowable1);
/*  9081:10089 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9082:10090 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable1);
/*  9083:      */         }
/*  9084:10092 */         localThrowable1.printStackTrace(Jaguar.log);
/*  9085:10093 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/*  9086:10094 */         return localThrowable1.getClass().getName();
/*  9087:      */       }
/*  9088:      */     case 152: 
/*  9089:      */       try
/*  9090:      */       {
/*  9091:      */         ExtTransferAcctInfo localExtTransferAcctInfo2;
/*  9092:10103 */         if (!paramBoolean)
/*  9093:      */         {
/*  9094:10105 */           localExtTransferAcctInfo2 = (ExtTransferAcctInfo)paramLocalFrame.get(0);
/*  9095:      */         }
/*  9096:      */         else
/*  9097:      */         {
/*  9098:10109 */           localObject1 = paramLocalFrame.get(0);
/*  9099:10110 */           localExtTransferAcctInfo2 = (ExtTransferAcctInfo)ObjectVal.clone(localObject1);
/*  9100:      */         }
/*  9101:10112 */         localObject1 = 
/*  9102:10113 */           paramBPWServicesBean.inactivateExtTransferAcct(
/*  9103:10114 */           localExtTransferAcctInfo2);
/*  9104:      */         
/*  9105:10116 */         paramLocalFrame.setResult(localObject1);
/*  9106:      */       }
/*  9107:      */       catch (Throwable localThrowable2)
/*  9108:      */       {
/*  9109:10120 */         if ((localThrowable2 instanceof FFSException))
/*  9110:      */         {
/*  9111:10122 */           paramLocalFrame.setException(localThrowable2);
/*  9112:10123 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9113:10124 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable2);
/*  9114:      */         }
/*  9115:10126 */         localThrowable2.printStackTrace(Jaguar.log);
/*  9116:10127 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/*  9117:10128 */         return localThrowable2.getClass().getName();
/*  9118:      */       }
/*  9119:      */     case 153: 
/*  9120:      */       try
/*  9121:      */       {
/*  9122:      */         ExtTransferAcctList localExtTransferAcctList;
/*  9123:10137 */         if (!paramBoolean)
/*  9124:      */         {
/*  9125:10139 */           localExtTransferAcctList = (ExtTransferAcctList)paramLocalFrame.get(0);
/*  9126:      */         }
/*  9127:      */         else
/*  9128:      */         {
/*  9129:10143 */           localObject1 = paramLocalFrame.get(0);
/*  9130:10144 */           localExtTransferAcctList = (ExtTransferAcctList)ObjectVal.clone(localObject1);
/*  9131:      */         }
/*  9132:10146 */         localObject1 = 
/*  9133:10147 */           paramBPWServicesBean.getExtTransferAcctList(
/*  9134:10148 */           localExtTransferAcctList);
/*  9135:      */         
/*  9136:10150 */         paramLocalFrame.setResult(localObject1);
/*  9137:      */       }
/*  9138:      */       catch (Throwable localThrowable3)
/*  9139:      */       {
/*  9140:10154 */         localThrowable3.printStackTrace(Jaguar.log);
/*  9141:10155 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/*  9142:10156 */         return localThrowable3.getClass().getName();
/*  9143:      */       }
/*  9144:      */     case 154: 
/*  9145:      */       try
/*  9146:      */       {
/*  9147:10165 */         String str1 = (String)paramLocalFrame.get(0);
/*  9148:10166 */         localObject1 = paramBPWServicesBean
/*  9149:10167 */           .getNonBusinessDays(
/*  9150:10168 */           str1);
/*  9151:      */         
/*  9152:10170 */         paramLocalFrame.setResult(localObject1);
/*  9153:      */       }
/*  9154:      */       catch (Throwable localThrowable4)
/*  9155:      */       {
/*  9156:10174 */         if ((localThrowable4 instanceof FFSException))
/*  9157:      */         {
/*  9158:10176 */           paramLocalFrame.setException(localThrowable4);
/*  9159:10177 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9160:10178 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable4);
/*  9161:      */         }
/*  9162:10180 */         localThrowable4.printStackTrace(Jaguar.log);
/*  9163:10181 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/*  9164:10182 */         return localThrowable4.getClass().getName();
/*  9165:      */       }
/*  9166:      */     case 155: 
/*  9167:      */       try
/*  9168:      */       {
/*  9169:10191 */         String str2 = (String)paramLocalFrame.get(0);
/*  9170:10192 */         localObject1 = paramBPWServicesBean
/*  9171:10193 */           .getNonBusinessDaysFromFile(
/*  9172:10194 */           str2);
/*  9173:      */         
/*  9174:10196 */         paramLocalFrame.setResult(localObject1);
/*  9175:      */       }
/*  9176:      */       catch (Throwable localThrowable5)
/*  9177:      */       {
/*  9178:10200 */         if ((localThrowable5 instanceof FFSException))
/*  9179:      */         {
/*  9180:10202 */           paramLocalFrame.setException(localThrowable5);
/*  9181:10203 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9182:10204 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable5);
/*  9183:      */         }
/*  9184:10206 */         localThrowable5.printStackTrace(Jaguar.log);
/*  9185:10207 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/*  9186:10208 */         return localThrowable5.getClass().getName();
/*  9187:      */       }
/*  9188:      */     case 156: 
/*  9189:      */       try
/*  9190:      */       {
/*  9191:      */         PagingInfo localPagingInfo1;
/*  9192:10217 */         if (!paramBoolean)
/*  9193:      */         {
/*  9194:10219 */           localPagingInfo1 = (PagingInfo)paramLocalFrame.get(0);
/*  9195:      */         }
/*  9196:      */         else
/*  9197:      */         {
/*  9198:10223 */           localObject1 = paramLocalFrame.get(0);
/*  9199:10224 */           localPagingInfo1 = (PagingInfo)ObjectVal.clone(localObject1);
/*  9200:      */         }
/*  9201:10226 */         localObject1 = 
/*  9202:10227 */           paramBPWServicesBean.getPagedWire(
/*  9203:10228 */           localPagingInfo1);
/*  9204:      */         
/*  9205:10230 */         paramLocalFrame.setResult(localObject1);
/*  9206:      */       }
/*  9207:      */       catch (Throwable localThrowable6)
/*  9208:      */       {
/*  9209:10234 */         if ((localThrowable6 instanceof FFSException))
/*  9210:      */         {
/*  9211:10236 */           paramLocalFrame.setException(localThrowable6);
/*  9212:10237 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9213:10238 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable6);
/*  9214:      */         }
/*  9215:10240 */         localThrowable6.printStackTrace(Jaguar.log);
/*  9216:10241 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/*  9217:10242 */         return localThrowable6.getClass().getName();
/*  9218:      */       }
/*  9219:      */     case 157: 
/*  9220:      */       try
/*  9221:      */       {
/*  9222:      */         PagingInfo localPagingInfo2;
/*  9223:10251 */         if (!paramBoolean)
/*  9224:      */         {
/*  9225:10253 */           localPagingInfo2 = (PagingInfo)paramLocalFrame.get(0);
/*  9226:      */         }
/*  9227:      */         else
/*  9228:      */         {
/*  9229:10257 */           localObject1 = paramLocalFrame.get(0);
/*  9230:10258 */           localPagingInfo2 = (PagingInfo)ObjectVal.clone(localObject1);
/*  9231:      */         }
/*  9232:10260 */         localObject1 = 
/*  9233:10261 */           paramBPWServicesBean.getPagedTransfer(
/*  9234:10262 */           localPagingInfo2);
/*  9235:      */         
/*  9236:10264 */         paramLocalFrame.setResult(localObject1);
/*  9237:      */       }
/*  9238:      */       catch (Throwable localThrowable7)
/*  9239:      */       {
/*  9240:10268 */         if ((localThrowable7 instanceof FFSException))
/*  9241:      */         {
/*  9242:10270 */           paramLocalFrame.setException(localThrowable7);
/*  9243:10271 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9244:10272 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable7);
/*  9245:      */         }
/*  9246:10274 */         localThrowable7.printStackTrace(Jaguar.log);
/*  9247:10275 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/*  9248:10276 */         return localThrowable7.getClass().getName();
/*  9249:      */       }
/*  9250:      */     case 158: 
/*  9251:      */       try
/*  9252:      */       {
/*  9253:      */         PagingInfo localPagingInfo3;
/*  9254:10285 */         if (!paramBoolean)
/*  9255:      */         {
/*  9256:10287 */           localPagingInfo3 = (PagingInfo)paramLocalFrame.get(0);
/*  9257:      */         }
/*  9258:      */         else
/*  9259:      */         {
/*  9260:10291 */           localObject1 = paramLocalFrame.get(0);
/*  9261:10292 */           localPagingInfo3 = (PagingInfo)ObjectVal.clone(localObject1);
/*  9262:      */         }
/*  9263:10294 */         localObject1 = 
/*  9264:10295 */           paramBPWServicesBean.getPagedBillPayments(
/*  9265:10296 */           localPagingInfo3);
/*  9266:      */         
/*  9267:10298 */         paramLocalFrame.setResult(localObject1);
/*  9268:      */       }
/*  9269:      */       catch (Throwable localThrowable8)
/*  9270:      */       {
/*  9271:10302 */         if ((localThrowable8 instanceof FFSException))
/*  9272:      */         {
/*  9273:10304 */           paramLocalFrame.setException(localThrowable8);
/*  9274:10305 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9275:10306 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable8);
/*  9276:      */         }
/*  9277:10308 */         localThrowable8.printStackTrace(Jaguar.log);
/*  9278:10309 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/*  9279:10310 */         return localThrowable8.getClass().getName();
/*  9280:      */       }
/*  9281:      */     case 159: 
/*  9282:      */       try
/*  9283:      */       {
/*  9284:      */         TransferInfo localTransferInfo;
/*  9285:10319 */         if (!paramBoolean)
/*  9286:      */         {
/*  9287:10321 */           localTransferInfo = (TransferInfo)paramLocalFrame.get(0);
/*  9288:      */         }
/*  9289:      */         else
/*  9290:      */         {
/*  9291:10325 */           localObject1 = paramLocalFrame.get(0);
/*  9292:10326 */           localTransferInfo = (TransferInfo)ObjectVal.clone(localObject1);
/*  9293:      */         }
/*  9294:10328 */         int i = paramBPWServicesBean
/*  9295:10329 */           .getValidTransferDateDue(
/*  9296:10330 */           localTransferInfo);
/*  9297:      */         
/*  9298:10332 */         paramLocalFrame.setResult(i);
/*  9299:      */       }
/*  9300:      */       catch (Throwable localThrowable9)
/*  9301:      */       {
/*  9302:10336 */         localThrowable9.printStackTrace(Jaguar.log);
/*  9303:10337 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/*  9304:10338 */         return localThrowable9.getClass().getName();
/*  9305:      */       }
/*  9306:      */     case 160: 
/*  9307:      */       try
/*  9308:      */       {
/*  9309:      */         PagingInfo localPagingInfo4;
/*  9310:10347 */         if (!paramBoolean)
/*  9311:      */         {
/*  9312:10349 */           localPagingInfo4 = (PagingInfo)paramLocalFrame.get(0);
/*  9313:      */         }
/*  9314:      */         else
/*  9315:      */         {
/*  9316:10353 */           localObject2 = paramLocalFrame.get(0);
/*  9317:10354 */           localPagingInfo4 = (PagingInfo)ObjectVal.clone(localObject2);
/*  9318:      */         }
/*  9319:10356 */         localObject2 = 
/*  9320:10357 */           paramBPWServicesBean.getPagedCashCon(
/*  9321:10358 */           localPagingInfo4);
/*  9322:      */         
/*  9323:10360 */         paramLocalFrame.setResult(localObject2);
/*  9324:      */       }
/*  9325:      */       catch (Throwable localThrowable10)
/*  9326:      */       {
/*  9327:10364 */         if ((localThrowable10 instanceof FFSException))
/*  9328:      */         {
/*  9329:10366 */           paramLocalFrame.setException(localThrowable10);
/*  9330:10367 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9331:10368 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable10);
/*  9332:      */         }
/*  9333:10370 */         localThrowable10.printStackTrace(Jaguar.log);
/*  9334:10371 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/*  9335:10372 */         return localThrowable10.getClass().getName();
/*  9336:      */       }
/*  9337:      */     case 161: 
/*  9338:      */       try
/*  9339:      */       {
/*  9340:10381 */         String str3 = (String)paramLocalFrame.get(0);
/*  9341:      */         
/*  9342:10383 */         localObject2 = (String)paramLocalFrame.get(1);
/*  9343:10384 */         localObject4 = paramBPWServicesBean
/*  9344:10385 */           .getPayeeByListId(
/*  9345:10386 */           str3, 
/*  9346:10387 */           (String)localObject2);
/*  9347:      */         
/*  9348:10389 */         paramLocalFrame.setResult(localObject4);
/*  9349:      */       }
/*  9350:      */       catch (Throwable localThrowable11)
/*  9351:      */       {
/*  9352:10393 */         if ((localThrowable11 instanceof FFSException))
/*  9353:      */         {
/*  9354:10395 */           paramLocalFrame.setException(localThrowable11);
/*  9355:10396 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9356:10397 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable11);
/*  9357:      */         }
/*  9358:10399 */         localThrowable11.printStackTrace(Jaguar.log);
/*  9359:10400 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/*  9360:10401 */         return localThrowable11.getClass().getName();
/*  9361:      */       }
/*  9362:      */     case 162: 
/*  9363:      */       try
/*  9364:      */       {
/*  9365:10409 */         AccountTypesMap localAccountTypesMap = paramBPWServicesBean
/*  9366:10410 */           .getAccountTypesMap();
/*  9367:      */         
/*  9368:10412 */         paramLocalFrame.setResult(localAccountTypesMap);
/*  9369:      */       }
/*  9370:      */       catch (Throwable localThrowable12)
/*  9371:      */       {
/*  9372:10416 */         if ((localThrowable12 instanceof FFSException))
/*  9373:      */         {
/*  9374:10418 */           paramLocalFrame.setException(localThrowable12);
/*  9375:10419 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9376:10420 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable12);
/*  9377:      */         }
/*  9378:10422 */         localThrowable12.printStackTrace(Jaguar.log);
/*  9379:10423 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/*  9380:10424 */         return localThrowable12.getClass().getName();
/*  9381:      */       }
/*  9382:      */     case 163: 
/*  9383:      */       try
/*  9384:      */       {
/*  9385:      */         ExtTransferAcctInfo localExtTransferAcctInfo3;
/*  9386:10433 */         if (!paramBoolean)
/*  9387:      */         {
/*  9388:10435 */           localExtTransferAcctInfo3 = (ExtTransferAcctInfo)paramLocalFrame.get(0);
/*  9389:      */         }
/*  9390:      */         else
/*  9391:      */         {
/*  9392:10439 */           localObject2 = paramLocalFrame.get(0);
/*  9393:10440 */           localExtTransferAcctInfo3 = (ExtTransferAcctInfo)ObjectVal.clone(localObject2);
/*  9394:      */         }
/*  9395:10442 */         localObject2 = 
/*  9396:10443 */           paramBPWServicesBean.modExtTransferAccountPrenoteStatus(
/*  9397:10444 */           localExtTransferAcctInfo3);
/*  9398:      */         
/*  9399:10446 */         paramLocalFrame.setResult(localObject2);
/*  9400:      */       }
/*  9401:      */       catch (Throwable localThrowable13)
/*  9402:      */       {
/*  9403:10450 */         localThrowable13.printStackTrace(Jaguar.log);
/*  9404:10451 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/*  9405:10452 */         return localThrowable13.getClass().getName();
/*  9406:      */       }
/*  9407:      */     case 164: 
/*  9408:      */       try
/*  9409:      */       {
/*  9410:10461 */         String str4 = (String)paramLocalFrame.get(0);
/*  9411:      */         
/*  9412:10463 */         localObject2 = (String)paramLocalFrame.get(1);
/*  9413:10464 */         localObject4 = paramBPWServicesBean
/*  9414:10465 */           .getBPWProperty(
/*  9415:10466 */           str4, 
/*  9416:10467 */           (String)localObject2);
/*  9417:      */         
/*  9418:10469 */         paramLocalFrame.setResult(localObject4);
/*  9419:      */       }
/*  9420:      */       catch (Throwable localThrowable14)
/*  9421:      */       {
/*  9422:10473 */         if ((localThrowable14 instanceof FFSException))
/*  9423:      */         {
/*  9424:10475 */           paramLocalFrame.setException(localThrowable14);
/*  9425:10476 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9426:10477 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable14);
/*  9427:      */         }
/*  9428:10479 */         localThrowable14.printStackTrace(Jaguar.log);
/*  9429:10480 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/*  9430:10481 */         return localThrowable14.getClass().getName();
/*  9431:      */       }
/*  9432:      */     case 165: 
/*  9433:      */       try
/*  9434:      */       {
/*  9435:      */         TransferBatchInfo localTransferBatchInfo1;
/*  9436:10490 */         if (!paramBoolean)
/*  9437:      */         {
/*  9438:10492 */           localTransferBatchInfo1 = (TransferBatchInfo)paramLocalFrame.get(0);
/*  9439:      */         }
/*  9440:      */         else
/*  9441:      */         {
/*  9442:10496 */           localObject2 = paramLocalFrame.get(0);
/*  9443:10497 */           localTransferBatchInfo1 = (TransferBatchInfo)ObjectVal.clone(localObject2);
/*  9444:      */         }
/*  9445:10499 */         localObject2 = 
/*  9446:10500 */           paramBPWServicesBean.addTransferBatch(
/*  9447:10501 */           localTransferBatchInfo1);
/*  9448:      */         
/*  9449:10503 */         paramLocalFrame.setResult(localObject2);
/*  9450:      */       }
/*  9451:      */       catch (Throwable localThrowable15)
/*  9452:      */       {
/*  9453:10507 */         if ((localThrowable15 instanceof FFSException))
/*  9454:      */         {
/*  9455:10509 */           paramLocalFrame.setException(localThrowable15);
/*  9456:10510 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9457:10511 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable15);
/*  9458:      */         }
/*  9459:10513 */         localThrowable15.printStackTrace(Jaguar.log);
/*  9460:10514 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/*  9461:10515 */         return localThrowable15.getClass().getName();
/*  9462:      */       }
/*  9463:      */     case 166: 
/*  9464:      */       try
/*  9465:      */       {
/*  9466:      */         TransferBatchInfo localTransferBatchInfo2;
/*  9467:10524 */         if (!paramBoolean)
/*  9468:      */         {
/*  9469:10526 */           localTransferBatchInfo2 = (TransferBatchInfo)paramLocalFrame.get(0);
/*  9470:      */         }
/*  9471:      */         else
/*  9472:      */         {
/*  9473:10530 */           localObject2 = paramLocalFrame.get(0);
/*  9474:10531 */           localTransferBatchInfo2 = (TransferBatchInfo)ObjectVal.clone(localObject2);
/*  9475:      */         }
/*  9476:10533 */         localObject2 = 
/*  9477:10534 */           paramBPWServicesBean.modifyTransferBatch(
/*  9478:10535 */           localTransferBatchInfo2);
/*  9479:      */         
/*  9480:10537 */         paramLocalFrame.setResult(localObject2);
/*  9481:      */       }
/*  9482:      */       catch (Throwable localThrowable16)
/*  9483:      */       {
/*  9484:10541 */         if ((localThrowable16 instanceof FFSException))
/*  9485:      */         {
/*  9486:10543 */           paramLocalFrame.setException(localThrowable16);
/*  9487:10544 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9488:10545 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable16);
/*  9489:      */         }
/*  9490:10547 */         localThrowable16.printStackTrace(Jaguar.log);
/*  9491:10548 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/*  9492:10549 */         return localThrowable16.getClass().getName();
/*  9493:      */       }
/*  9494:      */     case 167: 
/*  9495:      */       try
/*  9496:      */       {
/*  9497:      */         TransferBatchInfo localTransferBatchInfo3;
/*  9498:10558 */         if (!paramBoolean)
/*  9499:      */         {
/*  9500:10560 */           localTransferBatchInfo3 = (TransferBatchInfo)paramLocalFrame.get(0);
/*  9501:      */         }
/*  9502:      */         else
/*  9503:      */         {
/*  9504:10564 */           localObject2 = paramLocalFrame.get(0);
/*  9505:10565 */           localTransferBatchInfo3 = (TransferBatchInfo)ObjectVal.clone(localObject2);
/*  9506:      */         }
/*  9507:10567 */         localObject2 = 
/*  9508:10568 */           paramBPWServicesBean.cancelTransferBatch(
/*  9509:10569 */           localTransferBatchInfo3);
/*  9510:      */         
/*  9511:10571 */         paramLocalFrame.setResult(localObject2);
/*  9512:      */       }
/*  9513:      */       catch (Throwable localThrowable17)
/*  9514:      */       {
/*  9515:10575 */         if ((localThrowable17 instanceof FFSException))
/*  9516:      */         {
/*  9517:10577 */           paramLocalFrame.setException(localThrowable17);
/*  9518:10578 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9519:10579 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable17);
/*  9520:      */         }
/*  9521:10581 */         localThrowable17.printStackTrace(Jaguar.log);
/*  9522:10582 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/*  9523:10583 */         return localThrowable17.getClass().getName();
/*  9524:      */       }
/*  9525:      */     case 168: 
/*  9526:      */       try
/*  9527:      */       {
/*  9528:10592 */         String str5 = (String)paramLocalFrame.get(0);
/*  9529:10593 */         localObject2 = paramBPWServicesBean
/*  9530:10594 */           .getTransferBatchById(
/*  9531:10595 */           str5);
/*  9532:      */         
/*  9533:10597 */         paramLocalFrame.setResult(localObject2);
/*  9534:      */       }
/*  9535:      */       catch (Throwable localThrowable18)
/*  9536:      */       {
/*  9537:10601 */         if ((localThrowable18 instanceof FFSException))
/*  9538:      */         {
/*  9539:10603 */           paramLocalFrame.setException(localThrowable18);
/*  9540:10604 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9541:10605 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable18);
/*  9542:      */         }
/*  9543:10607 */         localThrowable18.printStackTrace(Jaguar.log);
/*  9544:10608 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/*  9545:10609 */         return localThrowable18.getClass().getName();
/*  9546:      */       }
/*  9547:      */     case 169: 
/*  9548:      */       try
/*  9549:      */       {
/*  9550:      */         AccountTransactions localAccountTransactions;
/*  9551:10618 */         if (!paramBoolean)
/*  9552:      */         {
/*  9553:10620 */           localAccountTransactions = (AccountTransactions)paramLocalFrame.get(0);
/*  9554:      */         }
/*  9555:      */         else
/*  9556:      */         {
/*  9557:10624 */           localObject2 = paramLocalFrame.get(0);
/*  9558:10625 */           localAccountTransactions = (AccountTransactions)ObjectVal.clone(localObject2);
/*  9559:      */         }
/*  9560:10627 */         localObject2 = 
/*  9561:10628 */           paramBPWServicesBean.accountHasPendingTransfers(
/*  9562:10629 */           localAccountTransactions);
/*  9563:      */         
/*  9564:10631 */         paramLocalFrame.setResult(localObject2);
/*  9565:      */       }
/*  9566:      */       catch (Throwable localThrowable19)
/*  9567:      */       {
/*  9568:10635 */         if ((localThrowable19 instanceof FFSException))
/*  9569:      */         {
/*  9570:10637 */           paramLocalFrame.setException(localThrowable19);
/*  9571:10638 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9572:10639 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable19);
/*  9573:      */         }
/*  9574:10641 */         localThrowable19.printStackTrace(Jaguar.log);
/*  9575:10642 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/*  9576:10643 */         return localThrowable19.getClass().getName();
/*  9577:      */       }
/*  9578:      */     case 170: 
/*  9579:      */       try
/*  9580:      */       {
/*  9581:      */         PmtInfo localPmtInfo1;
/*  9582:10652 */         if (!paramBoolean)
/*  9583:      */         {
/*  9584:10654 */           localPmtInfo1 = (PmtInfo)paramLocalFrame.get(0);
/*  9585:      */         }
/*  9586:      */         else
/*  9587:      */         {
/*  9588:10658 */           localObject2 = paramLocalFrame.get(0);
/*  9589:10659 */           localPmtInfo1 = (PmtInfo)ObjectVal.clone(localObject2);
/*  9590:      */         }
/*  9591:10661 */         localObject2 = 
/*  9592:10662 */           paramBPWServicesBean.addBillPayment(
/*  9593:10663 */           localPmtInfo1);
/*  9594:      */         
/*  9595:10665 */         paramLocalFrame.setResult(localObject2);
/*  9596:      */       }
/*  9597:      */       catch (Throwable localThrowable20)
/*  9598:      */       {
/*  9599:10669 */         if ((localThrowable20 instanceof FFSException))
/*  9600:      */         {
/*  9601:10671 */           paramLocalFrame.setException(localThrowable20);
/*  9602:10672 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9603:10673 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable20);
/*  9604:      */         }
/*  9605:10675 */         localThrowable20.printStackTrace(Jaguar.log);
/*  9606:10676 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/*  9607:10677 */         return localThrowable20.getClass().getName();
/*  9608:      */       }
/*  9609:      */     case 171: 
/*  9610:      */       try
/*  9611:      */       {
/*  9612:      */         PmtInfo localPmtInfo2;
/*  9613:10686 */         if (!paramBoolean)
/*  9614:      */         {
/*  9615:10688 */           localPmtInfo2 = (PmtInfo)paramLocalFrame.get(0);
/*  9616:      */         }
/*  9617:      */         else
/*  9618:      */         {
/*  9619:10692 */           localObject2 = paramLocalFrame.get(0);
/*  9620:10693 */           localPmtInfo2 = (PmtInfo)ObjectVal.clone(localObject2);
/*  9621:      */         }
/*  9622:10695 */         localObject2 = 
/*  9623:10696 */           paramBPWServicesBean.modifyBillPayment(
/*  9624:10697 */           localPmtInfo2);
/*  9625:      */         
/*  9626:10699 */         paramLocalFrame.setResult(localObject2);
/*  9627:      */       }
/*  9628:      */       catch (Throwable localThrowable21)
/*  9629:      */       {
/*  9630:10703 */         if ((localThrowable21 instanceof FFSException))
/*  9631:      */         {
/*  9632:10705 */           paramLocalFrame.setException(localThrowable21);
/*  9633:10706 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9634:10707 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable21);
/*  9635:      */         }
/*  9636:10709 */         localThrowable21.printStackTrace(Jaguar.log);
/*  9637:10710 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/*  9638:10711 */         return localThrowable21.getClass().getName();
/*  9639:      */       }
/*  9640:      */     case 172: 
/*  9641:      */       try
/*  9642:      */       {
/*  9643:      */         PmtInfo localPmtInfo3;
/*  9644:10720 */         if (!paramBoolean)
/*  9645:      */         {
/*  9646:10722 */           localPmtInfo3 = (PmtInfo)paramLocalFrame.get(0);
/*  9647:      */         }
/*  9648:      */         else
/*  9649:      */         {
/*  9650:10726 */           localObject2 = paramLocalFrame.get(0);
/*  9651:10727 */           localPmtInfo3 = (PmtInfo)ObjectVal.clone(localObject2);
/*  9652:      */         }
/*  9653:10729 */         localObject2 = 
/*  9654:10730 */           paramBPWServicesBean.deleteBillPayment(
/*  9655:10731 */           localPmtInfo3);
/*  9656:      */         
/*  9657:10733 */         paramLocalFrame.setResult(localObject2);
/*  9658:      */       }
/*  9659:      */       catch (Throwable localThrowable22)
/*  9660:      */       {
/*  9661:10737 */         if ((localThrowable22 instanceof FFSException))
/*  9662:      */         {
/*  9663:10739 */           paramLocalFrame.setException(localThrowable22);
/*  9664:10740 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9665:10741 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable22);
/*  9666:      */         }
/*  9667:10743 */         localThrowable22.printStackTrace(Jaguar.log);
/*  9668:10744 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/*  9669:10745 */         return localThrowable22.getClass().getName();
/*  9670:      */       }
/*  9671:      */     case 173: 
/*  9672:      */       try
/*  9673:      */       {
/*  9674:10754 */         String str6 = (String)paramLocalFrame.get(0);
/*  9675:      */         
/*  9676:10756 */         localObject2 = (String)paramLocalFrame.get(1);
/*  9677:10757 */         localObject4 = paramBPWServicesBean
/*  9678:10758 */           .getBillPaymentById(
/*  9679:10759 */           str6, 
/*  9680:10760 */           (String)localObject2);
/*  9681:      */         
/*  9682:10762 */         paramLocalFrame.setResult(localObject4);
/*  9683:      */       }
/*  9684:      */       catch (Throwable localThrowable23)
/*  9685:      */       {
/*  9686:10766 */         if ((localThrowable23 instanceof FFSException))
/*  9687:      */         {
/*  9688:10768 */           paramLocalFrame.setException(localThrowable23);
/*  9689:10769 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9690:10770 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable23);
/*  9691:      */         }
/*  9692:10772 */         localThrowable23.printStackTrace(Jaguar.log);
/*  9693:10773 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/*  9694:10774 */         return localThrowable23.getClass().getName();
/*  9695:      */       }
/*  9696:      */     case 174: 
/*  9697:      */       try
/*  9698:      */       {
/*  9699:      */         PaymentBatchInfo localPaymentBatchInfo1;
/*  9700:10783 */         if (!paramBoolean)
/*  9701:      */         {
/*  9702:10785 */           localPaymentBatchInfo1 = (PaymentBatchInfo)paramLocalFrame.get(0);
/*  9703:      */         }
/*  9704:      */         else
/*  9705:      */         {
/*  9706:10789 */           localObject2 = paramLocalFrame.get(0);
/*  9707:10790 */           localPaymentBatchInfo1 = (PaymentBatchInfo)ObjectVal.clone(localObject2);
/*  9708:      */         }
/*  9709:10792 */         localObject2 = 
/*  9710:10793 */           paramBPWServicesBean.addPaymentBatch(
/*  9711:10794 */           localPaymentBatchInfo1);
/*  9712:      */         
/*  9713:10796 */         paramLocalFrame.setResult(localObject2);
/*  9714:      */       }
/*  9715:      */       catch (Throwable localThrowable24)
/*  9716:      */       {
/*  9717:10800 */         if ((localThrowable24 instanceof FFSException))
/*  9718:      */         {
/*  9719:10802 */           paramLocalFrame.setException(localThrowable24);
/*  9720:10803 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9721:10804 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable24);
/*  9722:      */         }
/*  9723:10806 */         localThrowable24.printStackTrace(Jaguar.log);
/*  9724:10807 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/*  9725:10808 */         return localThrowable24.getClass().getName();
/*  9726:      */       }
/*  9727:      */     case 175: 
/*  9728:      */       try
/*  9729:      */       {
/*  9730:      */         PaymentBatchInfo localPaymentBatchInfo2;
/*  9731:10817 */         if (!paramBoolean)
/*  9732:      */         {
/*  9733:10819 */           localPaymentBatchInfo2 = (PaymentBatchInfo)paramLocalFrame.get(0);
/*  9734:      */         }
/*  9735:      */         else
/*  9736:      */         {
/*  9737:10823 */           localObject2 = paramLocalFrame.get(0);
/*  9738:10824 */           localPaymentBatchInfo2 = (PaymentBatchInfo)ObjectVal.clone(localObject2);
/*  9739:      */         }
/*  9740:10826 */         localObject2 = 
/*  9741:10827 */           paramBPWServicesBean.modifyPaymentBatch(
/*  9742:10828 */           localPaymentBatchInfo2);
/*  9743:      */         
/*  9744:10830 */         paramLocalFrame.setResult(localObject2);
/*  9745:      */       }
/*  9746:      */       catch (Throwable localThrowable25)
/*  9747:      */       {
/*  9748:10834 */         if ((localThrowable25 instanceof FFSException))
/*  9749:      */         {
/*  9750:10836 */           paramLocalFrame.setException(localThrowable25);
/*  9751:10837 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9752:10838 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable25);
/*  9753:      */         }
/*  9754:10840 */         localThrowable25.printStackTrace(Jaguar.log);
/*  9755:10841 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/*  9756:10842 */         return localThrowable25.getClass().getName();
/*  9757:      */       }
/*  9758:      */     case 176: 
/*  9759:      */       try
/*  9760:      */       {
/*  9761:      */         PaymentBatchInfo localPaymentBatchInfo3;
/*  9762:10851 */         if (!paramBoolean)
/*  9763:      */         {
/*  9764:10853 */           localPaymentBatchInfo3 = (PaymentBatchInfo)paramLocalFrame.get(0);
/*  9765:      */         }
/*  9766:      */         else
/*  9767:      */         {
/*  9768:10857 */           localObject2 = paramLocalFrame.get(0);
/*  9769:10858 */           localPaymentBatchInfo3 = (PaymentBatchInfo)ObjectVal.clone(localObject2);
/*  9770:      */         }
/*  9771:10860 */         localObject2 = 
/*  9772:10861 */           paramBPWServicesBean.deletePaymentBatch(
/*  9773:10862 */           localPaymentBatchInfo3);
/*  9774:      */         
/*  9775:10864 */         paramLocalFrame.setResult(localObject2);
/*  9776:      */       }
/*  9777:      */       catch (Throwable localThrowable26)
/*  9778:      */       {
/*  9779:10868 */         if ((localThrowable26 instanceof FFSException))
/*  9780:      */         {
/*  9781:10870 */           paramLocalFrame.setException(localThrowable26);
/*  9782:10871 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9783:10872 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable26);
/*  9784:      */         }
/*  9785:10874 */         localThrowable26.printStackTrace(Jaguar.log);
/*  9786:10875 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/*  9787:10876 */         return localThrowable26.getClass().getName();
/*  9788:      */       }
/*  9789:      */     case 177: 
/*  9790:      */       try
/*  9791:      */       {
/*  9792:10885 */         String str7 = (String)paramLocalFrame.get(0);
/*  9793:10886 */         localObject2 = paramBPWServicesBean
/*  9794:10887 */           .getPaymentBatchById(
/*  9795:10888 */           str7);
/*  9796:      */         
/*  9797:10890 */         paramLocalFrame.setResult(localObject2);
/*  9798:      */       }
/*  9799:      */       catch (Throwable localThrowable27)
/*  9800:      */       {
/*  9801:10894 */         if ((localThrowable27 instanceof FFSException))
/*  9802:      */         {
/*  9803:10896 */           paramLocalFrame.setException(localThrowable27);
/*  9804:10897 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9805:10898 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable27);
/*  9806:      */         }
/*  9807:10900 */         localThrowable27.printStackTrace(Jaguar.log);
/*  9808:10901 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/*  9809:10902 */         return localThrowable27.getClass().getName();
/*  9810:      */       }
/*  9811:      */     case 178: 
/*  9812:      */       try
/*  9813:      */       {
/*  9814:      */         LastPaymentInfo localLastPaymentInfo;
/*  9815:10911 */         if (!paramBoolean)
/*  9816:      */         {
/*  9817:10913 */           localLastPaymentInfo = (LastPaymentInfo)paramLocalFrame.get(0);
/*  9818:      */         }
/*  9819:      */         else
/*  9820:      */         {
/*  9821:10917 */           localObject2 = paramLocalFrame.get(0);
/*  9822:10918 */           localLastPaymentInfo = (LastPaymentInfo)ObjectVal.clone(localObject2);
/*  9823:      */         }
/*  9824:10920 */         localObject2 = 
/*  9825:10921 */           paramBPWServicesBean.getLastPayments(
/*  9826:10922 */           localLastPaymentInfo);
/*  9827:      */         
/*  9828:10924 */         paramLocalFrame.setResult(localObject2);
/*  9829:      */       }
/*  9830:      */       catch (Throwable localThrowable28)
/*  9831:      */       {
/*  9832:10928 */         if ((localThrowable28 instanceof FFSException))
/*  9833:      */         {
/*  9834:10930 */           paramLocalFrame.setException(localThrowable28);
/*  9835:10931 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9836:10932 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable28);
/*  9837:      */         }
/*  9838:10934 */         localThrowable28.printStackTrace(Jaguar.log);
/*  9839:10935 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/*  9840:10936 */         return localThrowable28.getClass().getName();
/*  9841:      */       }
/*  9842:      */     case 179: 
/*  9843:      */       try
/*  9844:      */       {
/*  9845:      */         BankingDays localBankingDays;
/*  9846:10945 */         if (!paramBoolean)
/*  9847:      */         {
/*  9848:10947 */           localBankingDays = (BankingDays)paramLocalFrame.get(0);
/*  9849:      */         }
/*  9850:      */         else
/*  9851:      */         {
/*  9852:10951 */           localObject2 = paramLocalFrame.get(0);
/*  9853:10952 */           localBankingDays = (BankingDays)ObjectVal.clone(localObject2);
/*  9854:      */         }
/*  9855:10955 */         if (!paramBoolean)
/*  9856:      */         {
/*  9857:10957 */           localObject2 = (HashMap)paramLocalFrame.get(1);
/*  9858:      */         }
/*  9859:      */         else
/*  9860:      */         {
/*  9861:10961 */           localObject4 = paramLocalFrame.get(1);
/*  9862:10962 */           localObject2 = (HashMap)ObjectVal.clone(localObject4);
/*  9863:      */         }
/*  9864:10964 */         localObject4 = 
/*  9865:10965 */           paramBPWServicesBean.getBankingDaysInRange(
/*  9866:10966 */           localBankingDays, 
/*  9867:10967 */           (HashMap)localObject2);
/*  9868:      */         
/*  9869:10969 */         paramLocalFrame.setResult(localObject4);
/*  9870:      */       }
/*  9871:      */       catch (Throwable localThrowable29)
/*  9872:      */       {
/*  9873:10973 */         if ((localThrowable29 instanceof FFSException))
/*  9874:      */         {
/*  9875:10975 */           paramLocalFrame.setException(localThrowable29);
/*  9876:10976 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9877:10977 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable29);
/*  9878:      */         }
/*  9879:10979 */         localThrowable29.printStackTrace(Jaguar.log);
/*  9880:10980 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/*  9881:10981 */         return localThrowable29.getClass().getName();
/*  9882:      */       }
/*  9883:      */     case 180: 
/*  9884:      */       try
/*  9885:      */       {
/*  9886:      */         CustomerPayeeInfo localCustomerPayeeInfo1;
/*  9887:10990 */         if (!paramBoolean)
/*  9888:      */         {
/*  9889:10992 */           localCustomerPayeeInfo1 = (CustomerPayeeInfo)paramLocalFrame.get(0);
/*  9890:      */         }
/*  9891:      */         else
/*  9892:      */         {
/*  9893:10996 */           localObject2 = paramLocalFrame.get(0);
/*  9894:10997 */           localCustomerPayeeInfo1 = (CustomerPayeeInfo)ObjectVal.clone(localObject2);
/*  9895:      */         }
/*  9896:10999 */         localObject2 = 
/*  9897:11000 */           paramBPWServicesBean.addCustomerPayee(
/*  9898:11001 */           localCustomerPayeeInfo1);
/*  9899:      */         
/*  9900:11003 */         paramLocalFrame.setResult(localObject2);
/*  9901:      */       }
/*  9902:      */       catch (Throwable localThrowable30)
/*  9903:      */       {
/*  9904:11007 */         if ((localThrowable30 instanceof FFSException))
/*  9905:      */         {
/*  9906:11009 */           paramLocalFrame.setException(localThrowable30);
/*  9907:11010 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9908:11011 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable30);
/*  9909:      */         }
/*  9910:11013 */         localThrowable30.printStackTrace(Jaguar.log);
/*  9911:11014 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/*  9912:11015 */         return localThrowable30.getClass().getName();
/*  9913:      */       }
/*  9914:      */     case 181: 
/*  9915:      */       try
/*  9916:      */       {
/*  9917:      */         CustomerPayeeInfo localCustomerPayeeInfo2;
/*  9918:11024 */         if (!paramBoolean)
/*  9919:      */         {
/*  9920:11026 */           localCustomerPayeeInfo2 = (CustomerPayeeInfo)paramLocalFrame.get(0);
/*  9921:      */         }
/*  9922:      */         else
/*  9923:      */         {
/*  9924:11030 */           localObject2 = paramLocalFrame.get(0);
/*  9925:11031 */           localCustomerPayeeInfo2 = (CustomerPayeeInfo)ObjectVal.clone(localObject2);
/*  9926:      */         }
/*  9927:11033 */         localObject2 = 
/*  9928:11034 */           paramBPWServicesBean.deleteCustomerPayee(
/*  9929:11035 */           localCustomerPayeeInfo2);
/*  9930:      */         
/*  9931:11037 */         paramLocalFrame.setResult(localObject2);
/*  9932:      */       }
/*  9933:      */       catch (Throwable localThrowable31)
/*  9934:      */       {
/*  9935:11041 */         if ((localThrowable31 instanceof FFSException))
/*  9936:      */         {
/*  9937:11043 */           paramLocalFrame.setException(localThrowable31);
/*  9938:11044 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9939:11045 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable31);
/*  9940:      */         }
/*  9941:11047 */         localThrowable31.printStackTrace(Jaguar.log);
/*  9942:11048 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/*  9943:11049 */         return localThrowable31.getClass().getName();
/*  9944:      */       }
/*  9945:      */     case 182: 
/*  9946:      */       try
/*  9947:      */       {
/*  9948:      */         CustomerPayeeInfo localCustomerPayeeInfo3;
/*  9949:11058 */         if (!paramBoolean)
/*  9950:      */         {
/*  9951:11060 */           localCustomerPayeeInfo3 = (CustomerPayeeInfo)paramLocalFrame.get(0);
/*  9952:      */         }
/*  9953:      */         else
/*  9954:      */         {
/*  9955:11064 */           localObject2 = paramLocalFrame.get(0);
/*  9956:11065 */           localCustomerPayeeInfo3 = (CustomerPayeeInfo)ObjectVal.clone(localObject2);
/*  9957:      */         }
/*  9958:11067 */         localObject2 = 
/*  9959:11068 */           paramBPWServicesBean.updateCustomerPayee(
/*  9960:11069 */           localCustomerPayeeInfo3);
/*  9961:      */         
/*  9962:11071 */         paramLocalFrame.setResult(localObject2);
/*  9963:      */       }
/*  9964:      */       catch (Throwable localThrowable32)
/*  9965:      */       {
/*  9966:11075 */         if ((localThrowable32 instanceof FFSException))
/*  9967:      */         {
/*  9968:11077 */           paramLocalFrame.setException(localThrowable32);
/*  9969:11078 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  9970:11079 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable32);
/*  9971:      */         }
/*  9972:11081 */         localThrowable32.printStackTrace(Jaguar.log);
/*  9973:11082 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/*  9974:11083 */         return localThrowable32.getClass().getName();
/*  9975:      */       }
/*  9976:      */     case 183: 
/*  9977:      */       try
/*  9978:      */       {
/*  9979:      */         CustomerPayeeInfo localCustomerPayeeInfo4;
/*  9980:11092 */         if (!paramBoolean)
/*  9981:      */         {
/*  9982:11094 */           localCustomerPayeeInfo4 = (CustomerPayeeInfo)paramLocalFrame.get(0);
/*  9983:      */         }
/*  9984:      */         else
/*  9985:      */         {
/*  9986:11098 */           localObject2 = paramLocalFrame.get(0);
/*  9987:11099 */           localCustomerPayeeInfo4 = (CustomerPayeeInfo)ObjectVal.clone(localObject2);
/*  9988:      */         }
/*  9989:11101 */         localObject2 = 
/*  9990:11102 */           paramBPWServicesBean.getCustomerPayees(
/*  9991:11103 */           localCustomerPayeeInfo4);
/*  9992:      */         
/*  9993:11105 */         paramLocalFrame.setResult(localObject2);
/*  9994:      */       }
/*  9995:      */       catch (Throwable localThrowable33)
/*  9996:      */       {
/*  9997:11109 */         if ((localThrowable33 instanceof FFSException))
/*  9998:      */         {
/*  9999:11111 */           paramLocalFrame.setException(localThrowable33);
/* 10000:11112 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 10001:11113 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable33);
/* 10002:      */         }
/* 10003:11115 */         localThrowable33.printStackTrace(Jaguar.log);
/* 10004:11116 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/* 10005:11117 */         return localThrowable33.getClass().getName();
/* 10006:      */       }
/* 10007:      */     case 184: 
/* 10008:      */       try
/* 10009:      */       {
/* 10010:      */         PayeeInfo localPayeeInfo;
/* 10011:11126 */         if (!paramBoolean)
/* 10012:      */         {
/* 10013:11128 */           localPayeeInfo = (PayeeInfo)paramLocalFrame.get(0);
/* 10014:      */         }
/* 10015:      */         else
/* 10016:      */         {
/* 10017:11132 */           localObject2 = paramLocalFrame.get(0);
/* 10018:11133 */           localPayeeInfo = (PayeeInfo)ObjectVal.clone(localObject2);
/* 10019:      */         }
/* 10020:11136 */         int j = ((Integer)paramLocalFrame.get(1)).intValue();
/* 10021:11137 */         localObject4 = paramBPWServicesBean
/* 10022:11138 */           .searchGlobalPayees(
/* 10023:11139 */           localPayeeInfo, 
/* 10024:11140 */           j);
/* 10025:      */         
/* 10026:11142 */         paramLocalFrame.setResult(localObject4);
/* 10027:      */       }
/* 10028:      */       catch (Throwable localThrowable34)
/* 10029:      */       {
/* 10030:11146 */         if ((localThrowable34 instanceof FFSException))
/* 10031:      */         {
/* 10032:11148 */           paramLocalFrame.setException(localThrowable34);
/* 10033:11149 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 10034:11150 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable34);
/* 10035:      */         }
/* 10036:11152 */         localThrowable34.printStackTrace(Jaguar.log);
/* 10037:11153 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/* 10038:11154 */         return localThrowable34.getClass().getName();
/* 10039:      */       }
/* 10040:      */     case 185: 
/* 10041:      */       try
/* 10042:      */       {
/* 10043:11163 */         String str8 = (String)paramLocalFrame.get(0);
/* 10044:11164 */         localObject3 = paramBPWServicesBean
/* 10045:11165 */           .getGlobalPayee(
/* 10046:11166 */           str8);
/* 10047:      */         
/* 10048:11168 */         paramLocalFrame.setResult(localObject3);
/* 10049:      */       }
/* 10050:      */       catch (Throwable localThrowable35)
/* 10051:      */       {
/* 10052:11172 */         if ((localThrowable35 instanceof FFSException))
/* 10053:      */         {
/* 10054:11174 */           paramLocalFrame.setException(localThrowable35);
/* 10055:11175 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 10056:11176 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable35);
/* 10057:      */         }
/* 10058:11178 */         localThrowable35.printStackTrace(Jaguar.log);
/* 10059:11179 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/* 10060:11180 */         return localThrowable35.getClass().getName();
/* 10061:      */       }
/* 10062:      */     case 186: 
/* 10063:      */       try
/* 10064:      */       {
/* 10065:      */         PmtTrnRslt[] arrayOfPmtTrnRslt;
/* 10066:11189 */         if (!paramBoolean)
/* 10067:      */         {
/* 10068:11191 */           arrayOfPmtTrnRslt = (PmtTrnRslt[])paramLocalFrame.get(0);
/* 10069:      */         }
/* 10070:      */         else
/* 10071:      */         {
/* 10072:11195 */           localObject3 = paramLocalFrame.get(0);
/* 10073:11196 */           arrayOfPmtTrnRslt = (PmtTrnRslt[])ObjectVal.clone(localObject3);
/* 10074:      */         }
/* 10075:11199 */         paramBPWServicesBean.processPmtTrnRslt(
/* 10076:11200 */           arrayOfPmtTrnRslt);
/* 10077:      */       }
/* 10078:      */       catch (Throwable localThrowable36)
/* 10079:      */       {
/* 10080:11205 */         localThrowable36.printStackTrace(Jaguar.log);
/* 10081:11206 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/* 10082:11207 */         return localThrowable36.getClass().getName();
/* 10083:      */       }
/* 10084:      */     }
/* 10085:11212 */     return null;
/* 10086:      */   }
/* 10087:      */ }


/* Location:           D:\drops\jd\jars\BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWServices._sk_BPWServices_BPWServicesBean
 * JD-Core Version:    0.7.0.1
 */