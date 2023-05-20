/*    1:     */ package com.ffusion.ffs.bpw.adminEJB;
/*    2:     */ 
/*    3:     */ import CtsComponents.ObjectContextHelper;
/*    4:     */ import com.ffusion.ffs.bpw.interfaces.BPWStatistics;
/*    5:     */ import com.ffusion.ffs.bpw.interfaces.BPWStatisticsHelper;
/*    6:     */ import com.ffusion.ffs.bpw.interfaces.CutOffActivityInfoList;
/*    7:     */ import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
/*    8:     */ import com.ffusion.ffs.bpw.interfaces.CutOffInfoList;
/*    9:     */ import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
/*   10:     */ import com.ffusion.ffs.bpw.interfaces.FulfillmentInfoSeqHelper;
/*   11:     */ import com.ffusion.ffs.bpw.interfaces.InstructionType;
/*   12:     */ import com.ffusion.ffs.bpw.interfaces.InstructionTypeSeqHelper;
/*   13:     */ import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
/*   14:     */ import com.ffusion.ffs.bpw.interfaces.PayeeInfoSeqHelper;
/*   15:     */ import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
/*   16:     */ import com.ffusion.ffs.bpw.interfaces.ProcessingWindowInfo;
/*   17:     */ import com.ffusion.ffs.bpw.interfaces.ProcessingWindowList;
/*   18:     */ import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
/*   19:     */ import com.ffusion.ffs.bpw.interfaces.PropertyConfigHelper;
/*   20:     */ import com.ffusion.ffs.bpw.interfaces.ScheduleActivityList;
/*   21:     */ import com.ffusion.ffs.bpw.interfaces.ScheduleCategoryInfo;
/*   22:     */ import com.ffusion.ffs.bpw.interfaces.ScheduleHist;
/*   23:     */ import com.ffusion.ffs.bpw.interfaces.ScheduleHistHelper;
/*   24:     */ import com.ffusion.ffs.bpw.interfaces.ScheduleHistSeqHelper;
/*   25:     */ import com.ffusion.ffs.bpw.interfaces.SchedulerInfo;
/*   26:     */ import com.ffusion.ffs.bpw.interfaces.SchedulerInfoSeqHelper;
/*   27:     */ import com.ffusion.ffs.bpw.interfaces.SmartCalendarFile;
/*   28:     */ import com.ffusion.ffs.db.FFSDBProperties;
/*   29:     */ import com.ffusion.ffs.db.FFSDBPropertiesHelper;
/*   30:     */ import com.ffusion.ffs.interfaces.FFSException;
/*   31:     */ import com.ffusion.ffs.interfaces.FFSExceptionHelper;
/*   32:     */ import com.ffusion.ffs.util.FFSProperties;
/*   33:     */ import com.sybase.CORBA.LocalFrame;
/*   34:     */ import com.sybase.CORBA.LocalStack;
/*   35:     */ import com.sybase.CORBA.ObjectVal;
/*   36:     */ import com.sybase.CORBA.UserException;
/*   37:     */ import com.sybase.CORBA._ServerRequest;
/*   38:     */ import com.sybase.CORBA.iiop.Connection;
/*   39:     */ import com.sybase.ejb.SessionContext;
/*   40:     */ import com.sybase.ejb.cts.StringSeqHelper;
/*   41:     */ import com.sybase.jaguar.server.Jaguar;
/*   42:     */ import java.util.ArrayList;
/*   43:     */ import java.util.HashMap;
/*   44:     */ import org.omg.CORBA.portable.InputStream;
/*   45:     */ import org.omg.CORBA.portable.OutputStream;
/*   46:     */ 
/*   47:     */ public abstract class _sk_BPWAdmin_BPWAdminBean
/*   48:     */ {
/*   49:  17 */   private static HashMap _methods = new HashMap(154);
/*   50:     */   private static HashMap _localMethods;
/*   51:     */   private static HashMap _localMethods2;
/*   52:     */   private static final String _RESET = "org.omg.CORBA.BAD_OPERATION";
/*   53:     */   
/*   54:     */   static
/*   55:     */   {
/*   56:  18 */     _methods.put("setSessionContext", new Integer(0));
/*   57:  19 */     _methods.put("ejbCreate", new Integer(1));
/*   58:  20 */     _methods.put("ejbActivate", new Integer(2));
/*   59:  21 */     _methods.put("ejbPassivate", new Integer(3));
/*   60:  22 */     _methods.put("ejbRemove", new Integer(4));
/*   61:  23 */     _methods.put("start__PropertyConfig__InstructionTypeSeq", new Integer(5));
/*   62:  24 */     _methods.put("start__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType", new Integer(5));
/*   63:  25 */     _methods.put("start__FFSProperties__PropertyConfig__InstructionTypeSeq", new Integer(6));
/*   64:  26 */     _methods.put("start__org_omg_boxedIDL_com_ffusion_ffs_util_FFSProperties__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType", new Integer(6));
/*   65:  27 */     _methods.put("stop", new Integer(7));
/*   66:  28 */     _methods.put("cleanup__string__ArrayList__ArrayList__HashMap", new Integer(8));
/*   67:  29 */     _methods.put("cleanup__CORBA_WStringValue__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_HashMap", new Integer(8));
/*   68:  30 */     _methods.put("cleanup__ArrayList__ArrayList__ArrayList__HashMap", new Integer(9));
/*   69:  31 */     _methods.put("cleanup__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_HashMap", new Integer(9));
/*   70:  32 */     _methods.put("cleanup__string__string__long__HashMap", new Integer(10));
/*   71:  33 */     _methods.put("cleanup__CORBA_WStringValue__CORBA_WStringValue__long__org_omg_boxedIDL_java_util_HashMap", new Integer(10));
/*   72:  34 */     _methods.put("refresh__FFSProperties__PropertyConfig__InstructionTypeSeq", new Integer(11));
/*   73:  35 */     _methods.put("refresh__org_omg_boxedIDL_com_ffusion_ffs_util_FFSProperties__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType", new Integer(11));
/*   74:  36 */     _methods.put("refresh__PropertyConfig__InstructionTypeSeq", new Integer(12));
/*   75:  37 */     _methods.put("refresh__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType", new Integer(12));
/*   76:  38 */     _methods.put("ping", new Integer(13));
/*   77:  39 */     _methods.put("getStatistics", new Integer(14));
/*   78:  40 */     _methods.put("refreshSmartCalendar", new Integer(15));
/*   79:  41 */     _methods.put("getFreeMem", new Integer(16));
/*   80:  42 */     _methods.put("getTotalMem", new Integer(17));
/*   81:  43 */     _methods.put("getHeapUsage", new Integer(18));
/*   82:  44 */     _methods.put("resubmitEvent__string__string__string", new Integer(19));
/*   83:  45 */     _methods.put("resubmitEvent__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue", new Integer(19));
/*   84:  46 */     _methods.put("resubmitEvent__string__string", new Integer(20));
/*   85:  47 */     _methods.put("resubmitEvent__CORBA_WStringValue__CORBA_WStringValue", new Integer(20));
/*   86:  48 */     _methods.put("resubmitEvent__string__string__string__string", new Integer(21));
/*   87:  49 */     _methods.put("resubmitEvent__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue", new Integer(21));
/*   88:  50 */     _methods.put("startScheduler", new Integer(22));
/*   89:  51 */     _methods.put("stopScheduler", new Integer(23));
/*   90:  52 */     _methods.put("refreshScheduler", new Integer(24));
/*   91:  53 */     _methods.put("registerPropertyConfig", new Integer(25));
/*   92:  54 */     _methods.put("startEngine", new Integer(26));
/*   93:  55 */     _methods.put("stopEngine", new Integer(27));
/*   94:  56 */     _methods.put("stopLimitCheckApprovalProcessor", new Integer(28));
/*   95:  57 */     _methods.put("startLimitCheckApprovalProcessor", new Integer(29));
/*   96:  58 */     _methods.put("runBatchProcess", new Integer(30));
/*   97:  59 */     _methods.put("updateScheduleRunTimeConfig", new Integer(31));
/*   98:  60 */     _methods.put("updateScheduleProcessingConfig", new Integer(32));
/*   99:  61 */     _methods.put("updateScheduleConfig", new Integer(33));
/*  100:  62 */     _methods.put("getScheduleConfig__string__string", new Integer(34));
/*  101:  63 */     _methods.put("getScheduleConfig__CORBA_WStringValue__CORBA_WStringValue", new Integer(34));
/*  102:  64 */     _methods.put("getScheduleConfig__", new Integer(35));
/*  103:  65 */     _methods.put("getScheduleConfig__", new Integer(35));
/*  104:  66 */     _methods.put("getSchedulerInfo__string__string", new Integer(36));
/*  105:  67 */     _methods.put("getSchedulerInfo__CORBA_WStringValue__CORBA_WStringValue", new Integer(36));
/*  106:  68 */     _methods.put("getSchedulerInfo__", new Integer(37));
/*  107:  69 */     _methods.put("getSchedulerInfo__", new Integer(37));
/*  108:  70 */     _methods.put("getScheduleHist", new Integer(38));
/*  109:  71 */     _methods.put("searchGlobalPayees__PayeeInfo__long", new Integer(39));
/*  110:  72 */     _methods.put("searchGlobalPayees__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__long", new Integer(39));
/*  111:  73 */     _methods.put("searchGlobalPayees__string", new Integer(40));
/*  112:  74 */     _methods.put("searchGlobalPayees__CORBA_WStringValue", new Integer(40));
/*  113:  75 */     _methods.put("getGlobalPayee", new Integer(41));
/*  114:  76 */     _methods.put("updateGlobalPayee", new Integer(42));
/*  115:  77 */     _methods.put("addPayee", new Integer(43));
/*  116:  78 */     _methods.put("updatePayee", new Integer(44));
/*  117:  79 */     _methods.put("deletePayee", new Integer(45));
/*  118:  80 */     _methods.put("getPayeeRoute", new Integer(46));
/*  119:  81 */     _methods.put("findPayeeByID", new Integer(47));
/*  120:  82 */     _methods.put("getAllFulfillmentInfo", new Integer(48));
/*  121:  83 */     _methods.put("getFulfillmentSystems", new Integer(49));
/*  122:  84 */     _methods.put("getGlobalPayeeGroups", new Integer(50));
/*  123:  85 */     _methods.put("addFulfillmentInfo", new Integer(51));
/*  124:  86 */     _methods.put("updateFulfillmentInfo", new Integer(52));
/*  125:  87 */     _methods.put("deleteFulfillmentInfo", new Integer(53));
/*  126:  88 */     _methods.put("setDebugLevel", new Integer(54));
/*  127:  89 */     _methods.put("addProcessingWindow", new Integer(55));
/*  128:  90 */     _methods.put("modProcessingWindow", new Integer(56));
/*  129:  91 */     _methods.put("delProcessingWindow", new Integer(57));
/*  130:  92 */     _methods.put("getProcessingWindows", new Integer(58));
/*  131:  93 */     _methods.put("getScheduleConfigByCategory", new Integer(59));
/*  132:  94 */     _methods.put("addScheduleConfig", new Integer(60));
/*  133:  95 */     _methods.put("deleteScheduleConfig", new Integer(61));
/*  134:  96 */     _methods.put("deleteCutOff", new Integer(62));
/*  135:  97 */     _methods.put("addCutOff", new Integer(63));
/*  136:  98 */     _methods.put("modCutOff", new Integer(64));
/*  137:  99 */     _methods.put("getCutOff", new Integer(65));
/*  138: 100 */     _methods.put("getCutOffList", new Integer(66));
/*  139: 101 */     _methods.put("getScheduleCategoryInfo", new Integer(67));
/*  140: 102 */     _methods.put("modScheduleCategoryInfo", new Integer(68));
/*  141: 103 */     _methods.put("getCutOffActivityList", new Integer(69));
/*  142: 104 */     _methods.put("getScheduleActivityList", new Integer(70));
/*  143: 105 */     _methods.put("rerunCutOff", new Integer(71));
/*  144: 106 */     _methods.put("getGeneratedFileName", new Integer(72));
/*  145: 107 */     _methods.put("importCalendar", new Integer(73));
/*  146: 108 */     _methods.put("exportCalendar", new Integer(74));
/*  147: 109 */     _methods.put("addGlobalPayee", new Integer(75));
/*  148: 110 */     _methods.put("deleteGlobalPayee", new Integer(76));
/*  149:     */     
/*  150:     */ 
/*  151:     */ 
/*  152:     */ 
/*  153:     */ 
/*  154:     */ 
/*  155:     */ 
/*  156:     */ 
/*  157: 119 */     _localMethods = new HashMap(154);
/*  158: 120 */     _localMethods2 = new HashMap(154);
/*  159: 121 */     _localMethods.put("#ejbCreate", new Integer(0));
/*  160: 122 */     _localMethods2.put("ejbCreate", new Integer(0));
/*  161: 123 */     _localMethods.put("#ejbRemove", new Integer(1));
/*  162: 124 */     _localMethods2.put("ejbRemove", new Integer(1));
/*  163: 125 */     _localMethods.put("#start__PropertyConfig__InstructionTypeSeq", new Integer(2));
/*  164: 126 */     _localMethods2.put("start__PropertyConfig__InstructionTypeSeq", new Integer(2));
/*  165: 127 */     _localMethods.put("#start__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType", new Integer(2));
/*  166: 128 */     _localMethods2.put("start__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType", new Integer(2));
/*  167: 129 */     _localMethods.put("#start__FFSProperties__PropertyConfig__InstructionTypeSeq", new Integer(3));
/*  168: 130 */     _localMethods2.put("start__FFSProperties__PropertyConfig__InstructionTypeSeq", new Integer(3));
/*  169: 131 */     _localMethods.put("#start__org_omg_boxedIDL_com_ffusion_ffs_util_FFSProperties__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType", new Integer(3));
/*  170: 132 */     _localMethods2.put("start__org_omg_boxedIDL_com_ffusion_ffs_util_FFSProperties__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType", new Integer(3));
/*  171: 133 */     _localMethods.put("#stop", new Integer(4));
/*  172: 134 */     _localMethods2.put("stop", new Integer(4));
/*  173: 135 */     _localMethods.put("#cleanup__string__ArrayList__ArrayList__HashMap", new Integer(5));
/*  174: 136 */     _localMethods2.put("cleanup__string__ArrayList__ArrayList__HashMap", new Integer(5));
/*  175: 137 */     _localMethods.put("#cleanup__CORBA_WStringValue__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_HashMap", new Integer(5));
/*  176: 138 */     _localMethods2.put("cleanup__CORBA_WStringValue__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_HashMap", new Integer(5));
/*  177: 139 */     _localMethods.put("#cleanup__ArrayList__ArrayList__ArrayList__HashMap", new Integer(6));
/*  178: 140 */     _localMethods2.put("cleanup__ArrayList__ArrayList__ArrayList__HashMap", new Integer(6));
/*  179: 141 */     _localMethods.put("#cleanup__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_HashMap", new Integer(6));
/*  180: 142 */     _localMethods2.put("cleanup__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_ArrayList__org_omg_boxedIDL_java_util_HashMap", new Integer(6));
/*  181: 143 */     _localMethods.put("#cleanup__string__string__long__HashMap", new Integer(7));
/*  182: 144 */     _localMethods2.put("cleanup__string__string__long__HashMap", new Integer(7));
/*  183: 145 */     _localMethods.put("#cleanup__CORBA_WStringValue__CORBA_WStringValue__long__org_omg_boxedIDL_java_util_HashMap", new Integer(7));
/*  184: 146 */     _localMethods2.put("cleanup__CORBA_WStringValue__CORBA_WStringValue__long__org_omg_boxedIDL_java_util_HashMap", new Integer(7));
/*  185: 147 */     _localMethods.put("#refresh__FFSProperties__PropertyConfig__InstructionTypeSeq", new Integer(8));
/*  186: 148 */     _localMethods2.put("refresh__FFSProperties__PropertyConfig__InstructionTypeSeq", new Integer(8));
/*  187: 149 */     _localMethods.put("#refresh__org_omg_boxedIDL_com_ffusion_ffs_util_FFSProperties__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType", new Integer(8));
/*  188: 150 */     _localMethods2.put("refresh__org_omg_boxedIDL_com_ffusion_ffs_util_FFSProperties__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType", new Integer(8));
/*  189: 151 */     _localMethods.put("#refresh__PropertyConfig__InstructionTypeSeq", new Integer(9));
/*  190: 152 */     _localMethods2.put("refresh__PropertyConfig__InstructionTypeSeq", new Integer(9));
/*  191: 153 */     _localMethods.put("#refresh__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType", new Integer(9));
/*  192: 154 */     _localMethods2.put("refresh__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType", new Integer(9));
/*  193: 155 */     _localMethods.put("#ping", new Integer(10));
/*  194: 156 */     _localMethods2.put("ping", new Integer(10));
/*  195: 157 */     _localMethods.put("#getStatistics", new Integer(11));
/*  196: 158 */     _localMethods2.put("getStatistics", new Integer(11));
/*  197: 159 */     _localMethods.put("#refreshSmartCalendar", new Integer(12));
/*  198: 160 */     _localMethods2.put("refreshSmartCalendar", new Integer(12));
/*  199: 161 */     _localMethods.put("#getFreeMem", new Integer(13));
/*  200: 162 */     _localMethods2.put("getFreeMem", new Integer(13));
/*  201: 163 */     _localMethods.put("#getTotalMem", new Integer(14));
/*  202: 164 */     _localMethods2.put("getTotalMem", new Integer(14));
/*  203: 165 */     _localMethods.put("#getHeapUsage", new Integer(15));
/*  204: 166 */     _localMethods2.put("getHeapUsage", new Integer(15));
/*  205: 167 */     _localMethods.put("#resubmitEvent__string__string__string", new Integer(16));
/*  206: 168 */     _localMethods2.put("resubmitEvent__string__string__string", new Integer(16));
/*  207: 169 */     _localMethods.put("#resubmitEvent__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue", new Integer(16));
/*  208: 170 */     _localMethods2.put("resubmitEvent__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue", new Integer(16));
/*  209: 171 */     _localMethods.put("#resubmitEvent__string__string", new Integer(17));
/*  210: 172 */     _localMethods2.put("resubmitEvent__string__string", new Integer(17));
/*  211: 173 */     _localMethods.put("#resubmitEvent__CORBA_WStringValue__CORBA_WStringValue", new Integer(17));
/*  212: 174 */     _localMethods2.put("resubmitEvent__CORBA_WStringValue__CORBA_WStringValue", new Integer(17));
/*  213: 175 */     _localMethods.put("#resubmitEvent__string__string__string__string", new Integer(18));
/*  214: 176 */     _localMethods2.put("resubmitEvent__string__string__string__string", new Integer(18));
/*  215: 177 */     _localMethods.put("#resubmitEvent__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue", new Integer(18));
/*  216: 178 */     _localMethods2.put("resubmitEvent__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue", new Integer(18));
/*  217: 179 */     _localMethods.put("#startScheduler", new Integer(19));
/*  218: 180 */     _localMethods2.put("startScheduler", new Integer(19));
/*  219: 181 */     _localMethods.put("#stopScheduler", new Integer(20));
/*  220: 182 */     _localMethods2.put("stopScheduler", new Integer(20));
/*  221: 183 */     _localMethods.put("#refreshScheduler", new Integer(21));
/*  222: 184 */     _localMethods2.put("refreshScheduler", new Integer(21));
/*  223: 185 */     _localMethods.put("#registerPropertyConfig", new Integer(22));
/*  224: 186 */     _localMethods2.put("registerPropertyConfig", new Integer(22));
/*  225: 187 */     _localMethods.put("#startEngine", new Integer(23));
/*  226: 188 */     _localMethods2.put("startEngine", new Integer(23));
/*  227: 189 */     _localMethods.put("#stopEngine", new Integer(24));
/*  228: 190 */     _localMethods2.put("stopEngine", new Integer(24));
/*  229: 191 */     _localMethods.put("#stopLimitCheckApprovalProcessor", new Integer(25));
/*  230: 192 */     _localMethods2.put("stopLimitCheckApprovalProcessor", new Integer(25));
/*  231: 193 */     _localMethods.put("#startLimitCheckApprovalProcessor", new Integer(26));
/*  232: 194 */     _localMethods2.put("startLimitCheckApprovalProcessor", new Integer(26));
/*  233: 195 */     _localMethods.put("#runBatchProcess", new Integer(27));
/*  234: 196 */     _localMethods2.put("runBatchProcess", new Integer(27));
/*  235: 197 */     _localMethods.put("#updateScheduleRunTimeConfig", new Integer(28));
/*  236: 198 */     _localMethods2.put("updateScheduleRunTimeConfig", new Integer(28));
/*  237: 199 */     _localMethods.put("#updateScheduleProcessingConfig", new Integer(29));
/*  238: 200 */     _localMethods2.put("updateScheduleProcessingConfig", new Integer(29));
/*  239: 201 */     _localMethods.put("#updateScheduleConfig", new Integer(30));
/*  240: 202 */     _localMethods2.put("updateScheduleConfig", new Integer(30));
/*  241: 203 */     _localMethods.put("#getScheduleConfig__string__string", new Integer(31));
/*  242: 204 */     _localMethods2.put("getScheduleConfig__string__string", new Integer(31));
/*  243: 205 */     _localMethods.put("#getScheduleConfig__CORBA_WStringValue__CORBA_WStringValue", new Integer(31));
/*  244: 206 */     _localMethods2.put("getScheduleConfig__CORBA_WStringValue__CORBA_WStringValue", new Integer(31));
/*  245: 207 */     _localMethods.put("#getScheduleConfig__", new Integer(32));
/*  246: 208 */     _localMethods2.put("getScheduleConfig__", new Integer(32));
/*  247: 209 */     _localMethods.put("#getScheduleConfig__", new Integer(32));
/*  248: 210 */     _localMethods2.put("getScheduleConfig__", new Integer(32));
/*  249: 211 */     _localMethods.put("#getSchedulerInfo__string__string", new Integer(33));
/*  250: 212 */     _localMethods2.put("getSchedulerInfo__string__string", new Integer(33));
/*  251: 213 */     _localMethods.put("#getSchedulerInfo__CORBA_WStringValue__CORBA_WStringValue", new Integer(33));
/*  252: 214 */     _localMethods2.put("getSchedulerInfo__CORBA_WStringValue__CORBA_WStringValue", new Integer(33));
/*  253: 215 */     _localMethods.put("#getSchedulerInfo__", new Integer(34));
/*  254: 216 */     _localMethods2.put("getSchedulerInfo__", new Integer(34));
/*  255: 217 */     _localMethods.put("#getSchedulerInfo__", new Integer(34));
/*  256: 218 */     _localMethods2.put("getSchedulerInfo__", new Integer(34));
/*  257: 219 */     _localMethods.put("#getScheduleHist", new Integer(35));
/*  258: 220 */     _localMethods2.put("getScheduleHist", new Integer(35));
/*  259: 221 */     _localMethods.put("#searchGlobalPayees__PayeeInfo__long", new Integer(36));
/*  260: 222 */     _localMethods2.put("searchGlobalPayees__PayeeInfo__long", new Integer(36));
/*  261: 223 */     _localMethods.put("#searchGlobalPayees__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__long", new Integer(36));
/*  262: 224 */     _localMethods2.put("searchGlobalPayees__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__long", new Integer(36));
/*  263: 225 */     _localMethods.put("#searchGlobalPayees__string", new Integer(37));
/*  264: 226 */     _localMethods2.put("searchGlobalPayees__string", new Integer(37));
/*  265: 227 */     _localMethods.put("#searchGlobalPayees__CORBA_WStringValue", new Integer(37));
/*  266: 228 */     _localMethods2.put("searchGlobalPayees__CORBA_WStringValue", new Integer(37));
/*  267: 229 */     _localMethods.put("#getGlobalPayee", new Integer(38));
/*  268: 230 */     _localMethods2.put("getGlobalPayee", new Integer(38));
/*  269: 231 */     _localMethods.put("#updateGlobalPayee", new Integer(39));
/*  270: 232 */     _localMethods2.put("updateGlobalPayee", new Integer(39));
/*  271: 233 */     _localMethods.put("#addPayee", new Integer(40));
/*  272: 234 */     _localMethods2.put("addPayee", new Integer(40));
/*  273: 235 */     _localMethods.put("#updatePayee", new Integer(41));
/*  274: 236 */     _localMethods2.put("updatePayee", new Integer(41));
/*  275: 237 */     _localMethods.put("#deletePayee", new Integer(42));
/*  276: 238 */     _localMethods2.put("deletePayee", new Integer(42));
/*  277: 239 */     _localMethods.put("#getPayeeRoute", new Integer(43));
/*  278: 240 */     _localMethods2.put("getPayeeRoute", new Integer(43));
/*  279: 241 */     _localMethods.put("#findPayeeByID", new Integer(44));
/*  280: 242 */     _localMethods2.put("findPayeeByID", new Integer(44));
/*  281: 243 */     _localMethods.put("#getAllFulfillmentInfo", new Integer(45));
/*  282: 244 */     _localMethods2.put("getAllFulfillmentInfo", new Integer(45));
/*  283: 245 */     _localMethods.put("#getFulfillmentSystems", new Integer(46));
/*  284: 246 */     _localMethods2.put("getFulfillmentSystems", new Integer(46));
/*  285: 247 */     _localMethods.put("#getGlobalPayeeGroups", new Integer(47));
/*  286: 248 */     _localMethods2.put("getGlobalPayeeGroups", new Integer(47));
/*  287: 249 */     _localMethods.put("#addFulfillmentInfo", new Integer(48));
/*  288: 250 */     _localMethods2.put("addFulfillmentInfo", new Integer(48));
/*  289: 251 */     _localMethods.put("#updateFulfillmentInfo", new Integer(49));
/*  290: 252 */     _localMethods2.put("updateFulfillmentInfo", new Integer(49));
/*  291: 253 */     _localMethods.put("#deleteFulfillmentInfo", new Integer(50));
/*  292: 254 */     _localMethods2.put("deleteFulfillmentInfo", new Integer(50));
/*  293: 255 */     _localMethods.put("#setDebugLevel", new Integer(51));
/*  294: 256 */     _localMethods2.put("setDebugLevel", new Integer(51));
/*  295: 257 */     _localMethods.put("#addProcessingWindow", new Integer(52));
/*  296: 258 */     _localMethods2.put("addProcessingWindow", new Integer(52));
/*  297: 259 */     _localMethods.put("#modProcessingWindow", new Integer(53));
/*  298: 260 */     _localMethods2.put("modProcessingWindow", new Integer(53));
/*  299: 261 */     _localMethods.put("#delProcessingWindow", new Integer(54));
/*  300: 262 */     _localMethods2.put("delProcessingWindow", new Integer(54));
/*  301: 263 */     _localMethods.put("#getProcessingWindows", new Integer(55));
/*  302: 264 */     _localMethods2.put("getProcessingWindows", new Integer(55));
/*  303: 265 */     _localMethods.put("#getScheduleConfigByCategory", new Integer(56));
/*  304: 266 */     _localMethods2.put("getScheduleConfigByCategory", new Integer(56));
/*  305: 267 */     _localMethods.put("#addScheduleConfig", new Integer(57));
/*  306: 268 */     _localMethods2.put("addScheduleConfig", new Integer(57));
/*  307: 269 */     _localMethods.put("#deleteScheduleConfig", new Integer(58));
/*  308: 270 */     _localMethods2.put("deleteScheduleConfig", new Integer(58));
/*  309: 271 */     _localMethods.put("#deleteCutOff", new Integer(59));
/*  310: 272 */     _localMethods2.put("deleteCutOff", new Integer(59));
/*  311: 273 */     _localMethods.put("#addCutOff", new Integer(60));
/*  312: 274 */     _localMethods2.put("addCutOff", new Integer(60));
/*  313: 275 */     _localMethods.put("#modCutOff", new Integer(61));
/*  314: 276 */     _localMethods2.put("modCutOff", new Integer(61));
/*  315: 277 */     _localMethods.put("#getCutOff", new Integer(62));
/*  316: 278 */     _localMethods2.put("getCutOff", new Integer(62));
/*  317: 279 */     _localMethods.put("#getCutOffList", new Integer(63));
/*  318: 280 */     _localMethods2.put("getCutOffList", new Integer(63));
/*  319: 281 */     _localMethods.put("#getScheduleCategoryInfo", new Integer(64));
/*  320: 282 */     _localMethods2.put("getScheduleCategoryInfo", new Integer(64));
/*  321: 283 */     _localMethods.put("#modScheduleCategoryInfo", new Integer(65));
/*  322: 284 */     _localMethods2.put("modScheduleCategoryInfo", new Integer(65));
/*  323: 285 */     _localMethods.put("#getCutOffActivityList", new Integer(66));
/*  324: 286 */     _localMethods2.put("getCutOffActivityList", new Integer(66));
/*  325: 287 */     _localMethods.put("#getScheduleActivityList", new Integer(67));
/*  326: 288 */     _localMethods2.put("getScheduleActivityList", new Integer(67));
/*  327: 289 */     _localMethods.put("#rerunCutOff", new Integer(68));
/*  328: 290 */     _localMethods2.put("rerunCutOff", new Integer(68));
/*  329: 291 */     _localMethods.put("#getGeneratedFileName", new Integer(69));
/*  330: 292 */     _localMethods2.put("getGeneratedFileName", new Integer(69));
/*  331: 293 */     _localMethods.put("#importCalendar", new Integer(70));
/*  332: 294 */     _localMethods2.put("importCalendar", new Integer(70));
/*  333: 295 */     _localMethods.put("#exportCalendar", new Integer(71));
/*  334: 296 */     _localMethods2.put("exportCalendar", new Integer(71));
/*  335: 297 */     _localMethods.put("#addGlobalPayee", new Integer(72));
/*  336: 298 */     _localMethods2.put("addGlobalPayee", new Integer(72));
/*  337: 299 */     _localMethods.put("#deleteGlobalPayee", new Integer(73));
/*  338: 300 */     _localMethods2.put("deleteGlobalPayee", new Integer(73));
/*  339:     */   }
/*  340:     */   
/*  341:     */   public static Object create()
/*  342:     */     throws Exception
/*  343:     */   {
/*  344: 308 */     return new BPWAdminBean();
/*  345:     */   }
/*  346:     */   
/*  347:     */   public static String invoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream)
/*  348:     */   {
/*  349: 317 */     return invoke(paramObject, paramString, paramInputStream, paramOutputStream, 0);
/*  350:     */   }
/*  351:     */   
/*  352:     */   public static String invoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/*  353:     */   {
/*  354: 327 */     if ((paramString.startsWith("#")) || (LocalStack.getCurrent().isArgsOnLocal())) {
/*  355: 329 */       return localInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/*  356:     */     }
/*  357: 333 */     return remoteInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/*  358:     */   }
/*  359:     */   
/*  360:     */   public static String remoteInvoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/*  361:     */   {
/*  362: 344 */     _ServerRequest local_ServerRequest = new _ServerRequest(paramInputStream, paramOutputStream, (paramInt & 0x1) != 0);
/*  363: 345 */     BPWAdminBean localBPWAdminBean = (BPWAdminBean)paramObject;
/*  364: 346 */     Integer localInteger = (Integer)_methods.get(paramString);
/*  365: 347 */     if (localInteger == null) {
/*  366: 349 */       return "org.omg.CORBA.BAD_OPERATION";
/*  367:     */     }
/*  368:     */     Object localObject1;
/*  369:     */     Object localObject3;
/*  370:     */     Object localObject5;
/*  371:     */     Object localObject4;
/*  372:     */     Object localObject2;
/*  373: 351 */     switch (localInteger.intValue())
/*  374:     */     {
/*  375:     */     case 0: 
/*  376:     */       try
/*  377:     */       {
/*  378: 358 */         SessionContext localSessionContext = new SessionContext(ObjectContextHelper.read(paramInputStream));
/*  379: 359 */         localBPWAdminBean
/*  380: 360 */           .setSessionContext(
/*  381: 361 */           localSessionContext);
/*  382:     */       }
/*  383:     */       catch (Throwable localThrowable1)
/*  384:     */       {
/*  385: 366 */         localThrowable1.printStackTrace(Jaguar.log);
/*  386: 367 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/*  387: 368 */         return localThrowable1.getClass().getName();
/*  388:     */       }
/*  389:     */     case 1: 
/*  390:     */       try
/*  391:     */       {
/*  392: 377 */         localBPWAdminBean.ejbCreate();
/*  393:     */       }
/*  394:     */       catch (Throwable localThrowable2)
/*  395:     */       {
/*  396: 382 */         localThrowable2.printStackTrace(Jaguar.log);
/*  397: 383 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/*  398: 384 */         return localThrowable2.getClass().getName();
/*  399:     */       }
/*  400:     */     case 2: 
/*  401:     */       try
/*  402:     */       {
/*  403: 393 */         localBPWAdminBean.ejbActivate();
/*  404:     */       }
/*  405:     */       catch (Throwable localThrowable3)
/*  406:     */       {
/*  407: 398 */         localThrowable3.printStackTrace(Jaguar.log);
/*  408: 399 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/*  409: 400 */         return localThrowable3.getClass().getName();
/*  410:     */       }
/*  411:     */     case 3: 
/*  412:     */       try
/*  413:     */       {
/*  414: 409 */         localBPWAdminBean.ejbPassivate();
/*  415:     */       }
/*  416:     */       catch (Throwable localThrowable4)
/*  417:     */       {
/*  418: 414 */         localThrowable4.printStackTrace(Jaguar.log);
/*  419: 415 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/*  420: 416 */         return localThrowable4.getClass().getName();
/*  421:     */       }
/*  422:     */     case 4: 
/*  423:     */       try
/*  424:     */       {
/*  425: 425 */         localBPWAdminBean.ejbRemove();
/*  426:     */       }
/*  427:     */       catch (Throwable localThrowable5)
/*  428:     */       {
/*  429: 430 */         localThrowable5.printStackTrace(Jaguar.log);
/*  430: 431 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/*  431: 432 */         return localThrowable5.getClass().getName();
/*  432:     */       }
/*  433:     */     case 5: 
/*  434:     */       try
/*  435:     */       {
/*  436:     */         PropertyConfig localPropertyConfig1;
/*  437: 441 */         if (local_ServerRequest.isRMI()) {
/*  438: 441 */           localPropertyConfig1 = (PropertyConfig)local_ServerRequest.read_value(PropertyConfig.class);
/*  439:     */         } else {
/*  440: 441 */           localPropertyConfig1 = PropertyConfigHelper.read(paramInputStream);
/*  441:     */         }
/*  442: 443 */         if (local_ServerRequest.isRMI()) {
/*  443: 443 */           localObject1 = (InstructionType[])local_ServerRequest.read_value(new InstructionType[0].getClass());
/*  444:     */         } else {
/*  445: 443 */           localObject1 = InstructionTypeSeqHelper.read(paramInputStream);
/*  446:     */         }
/*  447: 445 */         localBPWAdminBean.start(
/*  448: 446 */           localPropertyConfig1, 
/*  449: 447 */           (InstructionType[])localObject1);
/*  450:     */       }
/*  451:     */       catch (Throwable localThrowable6)
/*  452:     */       {
/*  453: 452 */         if ((localThrowable6 instanceof FFSException))
/*  454:     */         {
/*  455: 454 */           if (UserException.ok(paramOutputStream)) {
/*  456: 456 */             if (local_ServerRequest.isRMI())
/*  457:     */             {
/*  458: 458 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  459: 459 */               local_ServerRequest.write_value((FFSException)localThrowable6, FFSException.class);
/*  460:     */             }
/*  461:     */             else
/*  462:     */             {
/*  463: 463 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  464: 464 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable6);
/*  465:     */             }
/*  466:     */           }
/*  467: 467 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable6);
/*  468:     */         }
/*  469: 469 */         localThrowable6.printStackTrace(Jaguar.log);
/*  470: 470 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/*  471: 471 */         return localThrowable6.getClass().getName();
/*  472:     */       }
/*  473:     */     case 6: 
/*  474:     */       try
/*  475:     */       {
/*  476: 480 */         FFSProperties localFFSProperties1 = (FFSProperties)local_ServerRequest.read_value(FFSProperties.class);
/*  477: 482 */         if (local_ServerRequest.isRMI()) {
/*  478: 482 */           localObject1 = (PropertyConfig)local_ServerRequest.read_value(PropertyConfig.class);
/*  479:     */         } else {
/*  480: 482 */           localObject1 = PropertyConfigHelper.read(paramInputStream);
/*  481:     */         }
/*  482: 484 */         if (local_ServerRequest.isRMI()) {
/*  483: 484 */           localObject3 = (InstructionType[])local_ServerRequest.read_value(new InstructionType[0].getClass());
/*  484:     */         } else {
/*  485: 484 */           localObject3 = InstructionTypeSeqHelper.read(paramInputStream);
/*  486:     */         }
/*  487: 486 */         localBPWAdminBean.start(
/*  488: 487 */           localFFSProperties1, 
/*  489: 488 */           (PropertyConfig)localObject1, 
/*  490: 489 */           (InstructionType[])localObject3);
/*  491:     */       }
/*  492:     */       catch (Throwable localThrowable7)
/*  493:     */       {
/*  494: 494 */         if ((localThrowable7 instanceof FFSException))
/*  495:     */         {
/*  496: 496 */           if (UserException.ok(paramOutputStream)) {
/*  497: 498 */             if (local_ServerRequest.isRMI())
/*  498:     */             {
/*  499: 500 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  500: 501 */               local_ServerRequest.write_value((FFSException)localThrowable7, FFSException.class);
/*  501:     */             }
/*  502:     */             else
/*  503:     */             {
/*  504: 505 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  505: 506 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable7);
/*  506:     */             }
/*  507:     */           }
/*  508: 509 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable7);
/*  509:     */         }
/*  510: 511 */         localThrowable7.printStackTrace(Jaguar.log);
/*  511: 512 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/*  512: 513 */         return localThrowable7.getClass().getName();
/*  513:     */       }
/*  514:     */     case 7: 
/*  515:     */       try
/*  516:     */       {
/*  517: 522 */         localBPWAdminBean.stop();
/*  518:     */       }
/*  519:     */       catch (Throwable localThrowable8)
/*  520:     */       {
/*  521: 527 */         if ((localThrowable8 instanceof FFSException))
/*  522:     */         {
/*  523: 529 */           if (UserException.ok(paramOutputStream)) {
/*  524: 531 */             if (local_ServerRequest.isRMI())
/*  525:     */             {
/*  526: 533 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  527: 534 */               local_ServerRequest.write_value((FFSException)localThrowable8, FFSException.class);
/*  528:     */             }
/*  529:     */             else
/*  530:     */             {
/*  531: 538 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  532: 539 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable8);
/*  533:     */             }
/*  534:     */           }
/*  535: 542 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable8);
/*  536:     */         }
/*  537: 544 */         localThrowable8.printStackTrace(Jaguar.log);
/*  538: 545 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/*  539: 546 */         return localThrowable8.getClass().getName();
/*  540:     */       }
/*  541:     */     case 8: 
/*  542:     */       try
/*  543:     */       {
/*  544: 555 */         String str1 = local_ServerRequest.read_string();
/*  545:     */         
/*  546: 557 */         localObject1 = (ArrayList)local_ServerRequest.read_value(ArrayList.class);
/*  547:     */         
/*  548: 559 */         localObject3 = (ArrayList)local_ServerRequest.read_value(ArrayList.class);
/*  549:     */         
/*  550: 561 */         localObject5 = (HashMap)local_ServerRequest.read_value(HashMap.class);
/*  551: 562 */         localBPWAdminBean
/*  552: 563 */           .cleanup(
/*  553: 564 */           str1, 
/*  554: 565 */           (ArrayList)localObject1, 
/*  555: 566 */           (ArrayList)localObject3, 
/*  556: 567 */           (HashMap)localObject5);
/*  557:     */       }
/*  558:     */       catch (Throwable localThrowable9)
/*  559:     */       {
/*  560: 572 */         localThrowable9.printStackTrace(Jaguar.log);
/*  561: 573 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/*  562: 574 */         return localThrowable9.getClass().getName();
/*  563:     */       }
/*  564:     */     case 9: 
/*  565:     */       try
/*  566:     */       {
/*  567: 583 */         ArrayList localArrayList = (ArrayList)local_ServerRequest.read_value(ArrayList.class);
/*  568:     */         
/*  569: 585 */         localObject1 = (ArrayList)local_ServerRequest.read_value(ArrayList.class);
/*  570:     */         
/*  571: 587 */         localObject3 = (ArrayList)local_ServerRequest.read_value(ArrayList.class);
/*  572:     */         
/*  573: 589 */         localObject5 = (HashMap)local_ServerRequest.read_value(HashMap.class);
/*  574: 590 */         localBPWAdminBean
/*  575: 591 */           .cleanup(
/*  576: 592 */           localArrayList, 
/*  577: 593 */           (ArrayList)localObject1, 
/*  578: 594 */           (ArrayList)localObject3, 
/*  579: 595 */           (HashMap)localObject5);
/*  580:     */       }
/*  581:     */       catch (Throwable localThrowable10)
/*  582:     */       {
/*  583: 600 */         localThrowable10.printStackTrace(Jaguar.log);
/*  584: 601 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/*  585: 602 */         return localThrowable10.getClass().getName();
/*  586:     */       }
/*  587:     */     case 10: 
/*  588:     */       try
/*  589:     */       {
/*  590: 611 */         String str2 = local_ServerRequest.read_string();
/*  591:     */         
/*  592: 613 */         localObject1 = local_ServerRequest.read_string();
/*  593:     */         
/*  594: 615 */         int j = paramInputStream.read_long();
/*  595:     */         
/*  596: 617 */         localObject5 = (HashMap)local_ServerRequest.read_value(HashMap.class);
/*  597: 618 */         localBPWAdminBean
/*  598: 619 */           .cleanup(
/*  599: 620 */           str2, 
/*  600: 621 */           (String)localObject1, 
/*  601: 622 */           j, 
/*  602: 623 */           (HashMap)localObject5);
/*  603:     */       }
/*  604:     */       catch (Throwable localThrowable11)
/*  605:     */       {
/*  606: 628 */         localThrowable11.printStackTrace(Jaguar.log);
/*  607: 629 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/*  608: 630 */         return localThrowable11.getClass().getName();
/*  609:     */       }
/*  610:     */     case 11: 
/*  611:     */       try
/*  612:     */       {
/*  613: 639 */         FFSProperties localFFSProperties2 = (FFSProperties)local_ServerRequest.read_value(FFSProperties.class);
/*  614: 641 */         if (local_ServerRequest.isRMI()) {
/*  615: 641 */           localObject1 = (PropertyConfig)local_ServerRequest.read_value(PropertyConfig.class);
/*  616:     */         } else {
/*  617: 641 */           localObject1 = PropertyConfigHelper.read(paramInputStream);
/*  618:     */         }
/*  619: 643 */         if (local_ServerRequest.isRMI()) {
/*  620: 643 */           localObject4 = (InstructionType[])local_ServerRequest.read_value(new InstructionType[0].getClass());
/*  621:     */         } else {
/*  622: 643 */           localObject4 = InstructionTypeSeqHelper.read(paramInputStream);
/*  623:     */         }
/*  624: 645 */         localBPWAdminBean.refresh(
/*  625: 646 */           localFFSProperties2, 
/*  626: 647 */           (PropertyConfig)localObject1, 
/*  627: 648 */           (InstructionType[])localObject4);
/*  628:     */       }
/*  629:     */       catch (Throwable localThrowable12)
/*  630:     */       {
/*  631: 653 */         if ((localThrowable12 instanceof FFSException))
/*  632:     */         {
/*  633: 655 */           if (UserException.ok(paramOutputStream)) {
/*  634: 657 */             if (local_ServerRequest.isRMI())
/*  635:     */             {
/*  636: 659 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  637: 660 */               local_ServerRequest.write_value((FFSException)localThrowable12, FFSException.class);
/*  638:     */             }
/*  639:     */             else
/*  640:     */             {
/*  641: 664 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  642: 665 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable12);
/*  643:     */             }
/*  644:     */           }
/*  645: 668 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable12);
/*  646:     */         }
/*  647: 670 */         localThrowable12.printStackTrace(Jaguar.log);
/*  648: 671 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/*  649: 672 */         return localThrowable12.getClass().getName();
/*  650:     */       }
/*  651:     */     case 12: 
/*  652:     */       try
/*  653:     */       {
/*  654:     */         PropertyConfig localPropertyConfig2;
/*  655: 681 */         if (local_ServerRequest.isRMI()) {
/*  656: 681 */           localPropertyConfig2 = (PropertyConfig)local_ServerRequest.read_value(PropertyConfig.class);
/*  657:     */         } else {
/*  658: 681 */           localPropertyConfig2 = PropertyConfigHelper.read(paramInputStream);
/*  659:     */         }
/*  660: 683 */         if (local_ServerRequest.isRMI()) {
/*  661: 683 */           localObject1 = (InstructionType[])local_ServerRequest.read_value(new InstructionType[0].getClass());
/*  662:     */         } else {
/*  663: 683 */           localObject1 = InstructionTypeSeqHelper.read(paramInputStream);
/*  664:     */         }
/*  665: 685 */         localBPWAdminBean.refresh(
/*  666: 686 */           localPropertyConfig2, 
/*  667: 687 */           (InstructionType[])localObject1);
/*  668:     */       }
/*  669:     */       catch (Throwable localThrowable13)
/*  670:     */       {
/*  671: 692 */         if ((localThrowable13 instanceof FFSException))
/*  672:     */         {
/*  673: 694 */           if (UserException.ok(paramOutputStream)) {
/*  674: 696 */             if (local_ServerRequest.isRMI())
/*  675:     */             {
/*  676: 698 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  677: 699 */               local_ServerRequest.write_value((FFSException)localThrowable13, FFSException.class);
/*  678:     */             }
/*  679:     */             else
/*  680:     */             {
/*  681: 703 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  682: 704 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable13);
/*  683:     */             }
/*  684:     */           }
/*  685: 707 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable13);
/*  686:     */         }
/*  687: 709 */         localThrowable13.printStackTrace(Jaguar.log);
/*  688: 710 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/*  689: 711 */         return localThrowable13.getClass().getName();
/*  690:     */       }
/*  691:     */     case 13: 
/*  692:     */       try
/*  693:     */       {
/*  694: 719 */         boolean bool = localBPWAdminBean
/*  695: 720 */           .ping();
/*  696:     */         
/*  697: 722 */         paramOutputStream.write_boolean(bool);
/*  698:     */       }
/*  699:     */       catch (Throwable localThrowable14)
/*  700:     */       {
/*  701: 726 */         if ((localThrowable14 instanceof FFSException))
/*  702:     */         {
/*  703: 728 */           if (UserException.ok(paramOutputStream)) {
/*  704: 730 */             if (local_ServerRequest.isRMI())
/*  705:     */             {
/*  706: 732 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  707: 733 */               local_ServerRequest.write_value((FFSException)localThrowable14, FFSException.class);
/*  708:     */             }
/*  709:     */             else
/*  710:     */             {
/*  711: 737 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  712: 738 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable14);
/*  713:     */             }
/*  714:     */           }
/*  715: 741 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable14);
/*  716:     */         }
/*  717: 743 */         localThrowable14.printStackTrace(Jaguar.log);
/*  718: 744 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/*  719: 745 */         return localThrowable14.getClass().getName();
/*  720:     */       }
/*  721:     */     case 14: 
/*  722:     */       try
/*  723:     */       {
/*  724: 753 */         BPWStatistics localBPWStatistics = localBPWAdminBean
/*  725: 754 */           .getStatistics();
/*  726: 756 */         if (local_ServerRequest.isRMI()) {
/*  727: 756 */           local_ServerRequest.write_value(localBPWStatistics, BPWStatistics.class);
/*  728:     */         } else {
/*  729: 756 */           BPWStatisticsHelper.write(paramOutputStream, localBPWStatistics);
/*  730:     */         }
/*  731:     */       }
/*  732:     */       catch (Throwable localThrowable15)
/*  733:     */       {
/*  734: 760 */         if ((localThrowable15 instanceof FFSException))
/*  735:     */         {
/*  736: 762 */           if (UserException.ok(paramOutputStream)) {
/*  737: 764 */             if (local_ServerRequest.isRMI())
/*  738:     */             {
/*  739: 766 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  740: 767 */               local_ServerRequest.write_value((FFSException)localThrowable15, FFSException.class);
/*  741:     */             }
/*  742:     */             else
/*  743:     */             {
/*  744: 771 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  745: 772 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable15);
/*  746:     */             }
/*  747:     */           }
/*  748: 775 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable15);
/*  749:     */         }
/*  750: 777 */         localThrowable15.printStackTrace(Jaguar.log);
/*  751: 778 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/*  752: 779 */         return localThrowable15.getClass().getName();
/*  753:     */       }
/*  754:     */     case 15: 
/*  755:     */       try
/*  756:     */       {
/*  757: 788 */         localBPWAdminBean.refreshSmartCalendar();
/*  758:     */       }
/*  759:     */       catch (Throwable localThrowable16)
/*  760:     */       {
/*  761: 793 */         if ((localThrowable16 instanceof FFSException))
/*  762:     */         {
/*  763: 795 */           if (UserException.ok(paramOutputStream)) {
/*  764: 797 */             if (local_ServerRequest.isRMI())
/*  765:     */             {
/*  766: 799 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  767: 800 */               local_ServerRequest.write_value((FFSException)localThrowable16, FFSException.class);
/*  768:     */             }
/*  769:     */             else
/*  770:     */             {
/*  771: 804 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  772: 805 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable16);
/*  773:     */             }
/*  774:     */           }
/*  775: 808 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable16);
/*  776:     */         }
/*  777: 810 */         localThrowable16.printStackTrace(Jaguar.log);
/*  778: 811 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/*  779: 812 */         return localThrowable16.getClass().getName();
/*  780:     */       }
/*  781:     */     case 16: 
/*  782:     */       try
/*  783:     */       {
/*  784: 820 */         long l1 = localBPWAdminBean
/*  785: 821 */           .getFreeMem();
/*  786:     */         
/*  787: 823 */         paramOutputStream.write_longlong(l1);
/*  788:     */       }
/*  789:     */       catch (Throwable localThrowable17)
/*  790:     */       {
/*  791: 827 */         if ((localThrowable17 instanceof FFSException))
/*  792:     */         {
/*  793: 829 */           if (UserException.ok(paramOutputStream)) {
/*  794: 831 */             if (local_ServerRequest.isRMI())
/*  795:     */             {
/*  796: 833 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  797: 834 */               local_ServerRequest.write_value((FFSException)localThrowable17, FFSException.class);
/*  798:     */             }
/*  799:     */             else
/*  800:     */             {
/*  801: 838 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  802: 839 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable17);
/*  803:     */             }
/*  804:     */           }
/*  805: 842 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable17);
/*  806:     */         }
/*  807: 844 */         localThrowable17.printStackTrace(Jaguar.log);
/*  808: 845 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/*  809: 846 */         return localThrowable17.getClass().getName();
/*  810:     */       }
/*  811:     */     case 17: 
/*  812:     */       try
/*  813:     */       {
/*  814: 854 */         long l2 = localBPWAdminBean
/*  815: 855 */           .getTotalMem();
/*  816:     */         
/*  817: 857 */         paramOutputStream.write_longlong(l2);
/*  818:     */       }
/*  819:     */       catch (Throwable localThrowable18)
/*  820:     */       {
/*  821: 861 */         if ((localThrowable18 instanceof FFSException))
/*  822:     */         {
/*  823: 863 */           if (UserException.ok(paramOutputStream)) {
/*  824: 865 */             if (local_ServerRequest.isRMI())
/*  825:     */             {
/*  826: 867 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  827: 868 */               local_ServerRequest.write_value((FFSException)localThrowable18, FFSException.class);
/*  828:     */             }
/*  829:     */             else
/*  830:     */             {
/*  831: 872 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  832: 873 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable18);
/*  833:     */             }
/*  834:     */           }
/*  835: 876 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable18);
/*  836:     */         }
/*  837: 878 */         localThrowable18.printStackTrace(Jaguar.log);
/*  838: 879 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/*  839: 880 */         return localThrowable18.getClass().getName();
/*  840:     */       }
/*  841:     */     case 18: 
/*  842:     */       try
/*  843:     */       {
/*  844: 888 */         double d = localBPWAdminBean
/*  845: 889 */           .getHeapUsage();
/*  846:     */         
/*  847: 891 */         paramOutputStream.write_double(d);
/*  848:     */       }
/*  849:     */       catch (Throwable localThrowable19)
/*  850:     */       {
/*  851: 895 */         if ((localThrowable19 instanceof FFSException))
/*  852:     */         {
/*  853: 897 */           if (UserException.ok(paramOutputStream)) {
/*  854: 899 */             if (local_ServerRequest.isRMI())
/*  855:     */             {
/*  856: 901 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  857: 902 */               local_ServerRequest.write_value((FFSException)localThrowable19, FFSException.class);
/*  858:     */             }
/*  859:     */             else
/*  860:     */             {
/*  861: 906 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  862: 907 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable19);
/*  863:     */             }
/*  864:     */           }
/*  865: 910 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable19);
/*  866:     */         }
/*  867: 912 */         localThrowable19.printStackTrace(Jaguar.log);
/*  868: 913 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/*  869: 914 */         return localThrowable19.getClass().getName();
/*  870:     */       }
/*  871:     */     case 19: 
/*  872:     */       try
/*  873:     */       {
/*  874: 923 */         String str3 = local_ServerRequest.read_string();
/*  875:     */         
/*  876: 925 */         localObject1 = local_ServerRequest.read_string();
/*  877:     */         
/*  878: 927 */         localObject4 = local_ServerRequest.read_string();
/*  879: 928 */         localBPWAdminBean
/*  880: 929 */           .resubmitEvent(
/*  881: 930 */           str3, 
/*  882: 931 */           (String)localObject1, 
/*  883: 932 */           (String)localObject4);
/*  884:     */       }
/*  885:     */       catch (Throwable localThrowable20)
/*  886:     */       {
/*  887: 937 */         if ((localThrowable20 instanceof FFSException))
/*  888:     */         {
/*  889: 939 */           if (UserException.ok(paramOutputStream)) {
/*  890: 941 */             if (local_ServerRequest.isRMI())
/*  891:     */             {
/*  892: 943 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  893: 944 */               local_ServerRequest.write_value((FFSException)localThrowable20, FFSException.class);
/*  894:     */             }
/*  895:     */             else
/*  896:     */             {
/*  897: 948 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  898: 949 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable20);
/*  899:     */             }
/*  900:     */           }
/*  901: 952 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable20);
/*  902:     */         }
/*  903: 954 */         localThrowable20.printStackTrace(Jaguar.log);
/*  904: 955 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/*  905: 956 */         return localThrowable20.getClass().getName();
/*  906:     */       }
/*  907:     */     case 20: 
/*  908:     */       try
/*  909:     */       {
/*  910: 965 */         String str4 = local_ServerRequest.read_string();
/*  911:     */         
/*  912: 967 */         localObject1 = local_ServerRequest.read_string();
/*  913: 968 */         localBPWAdminBean
/*  914: 969 */           .resubmitEvent(
/*  915: 970 */           str4, 
/*  916: 971 */           (String)localObject1);
/*  917:     */       }
/*  918:     */       catch (Throwable localThrowable21)
/*  919:     */       {
/*  920: 976 */         if ((localThrowable21 instanceof FFSException))
/*  921:     */         {
/*  922: 978 */           if (UserException.ok(paramOutputStream)) {
/*  923: 980 */             if (local_ServerRequest.isRMI())
/*  924:     */             {
/*  925: 982 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  926: 983 */               local_ServerRequest.write_value((FFSException)localThrowable21, FFSException.class);
/*  927:     */             }
/*  928:     */             else
/*  929:     */             {
/*  930: 987 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  931: 988 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable21);
/*  932:     */             }
/*  933:     */           }
/*  934: 991 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable21);
/*  935:     */         }
/*  936: 993 */         localThrowable21.printStackTrace(Jaguar.log);
/*  937: 994 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/*  938: 995 */         return localThrowable21.getClass().getName();
/*  939:     */       }
/*  940:     */     case 21: 
/*  941:     */       try
/*  942:     */       {
/*  943:1004 */         String str5 = local_ServerRequest.read_string();
/*  944:     */         
/*  945:1006 */         localObject1 = local_ServerRequest.read_string();
/*  946:     */         
/*  947:1008 */         localObject4 = local_ServerRequest.read_string();
/*  948:     */         
/*  949:1010 */         localObject5 = local_ServerRequest.read_string();
/*  950:1011 */         localBPWAdminBean
/*  951:1012 */           .resubmitEvent(
/*  952:1013 */           str5, 
/*  953:1014 */           (String)localObject1, 
/*  954:1015 */           (String)localObject4, 
/*  955:1016 */           (String)localObject5);
/*  956:     */       }
/*  957:     */       catch (Throwable localThrowable22)
/*  958:     */       {
/*  959:1021 */         if ((localThrowable22 instanceof FFSException))
/*  960:     */         {
/*  961:1023 */           if (UserException.ok(paramOutputStream)) {
/*  962:1025 */             if (local_ServerRequest.isRMI())
/*  963:     */             {
/*  964:1027 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  965:1028 */               local_ServerRequest.write_value((FFSException)localThrowable22, FFSException.class);
/*  966:     */             }
/*  967:     */             else
/*  968:     */             {
/*  969:1032 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/*  970:1033 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable22);
/*  971:     */             }
/*  972:     */           }
/*  973:1036 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable22);
/*  974:     */         }
/*  975:1038 */         localThrowable22.printStackTrace(Jaguar.log);
/*  976:1039 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/*  977:1040 */         return localThrowable22.getClass().getName();
/*  978:     */       }
/*  979:     */     case 22: 
/*  980:     */       try
/*  981:     */       {
/*  982:1048 */         String str6 = localBPWAdminBean
/*  983:1049 */           .startScheduler();
/*  984:     */         
/*  985:1051 */         local_ServerRequest.write_string(str6);
/*  986:     */       }
/*  987:     */       catch (Throwable localThrowable23)
/*  988:     */       {
/*  989:1055 */         if ((localThrowable23 instanceof FFSException))
/*  990:     */         {
/*  991:1057 */           if (UserException.ok(paramOutputStream)) {
/*  992:1059 */             if (local_ServerRequest.isRMI())
/*  993:     */             {
/*  994:1061 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/*  995:1062 */               local_ServerRequest.write_value((FFSException)localThrowable23, FFSException.class);
/*  996:     */             }
/*  997:     */             else
/*  998:     */             {
/*  999:1066 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1000:1067 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable23);
/* 1001:     */             }
/* 1002:     */           }
/* 1003:1070 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable23);
/* 1004:     */         }
/* 1005:1072 */         localThrowable23.printStackTrace(Jaguar.log);
/* 1006:1073 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/* 1007:1074 */         return localThrowable23.getClass().getName();
/* 1008:     */       }
/* 1009:     */     case 23: 
/* 1010:     */       try
/* 1011:     */       {
/* 1012:1082 */         String str7 = localBPWAdminBean
/* 1013:1083 */           .stopScheduler();
/* 1014:     */         
/* 1015:1085 */         local_ServerRequest.write_string(str7);
/* 1016:     */       }
/* 1017:     */       catch (Throwable localThrowable24)
/* 1018:     */       {
/* 1019:1089 */         if ((localThrowable24 instanceof FFSException))
/* 1020:     */         {
/* 1021:1091 */           if (UserException.ok(paramOutputStream)) {
/* 1022:1093 */             if (local_ServerRequest.isRMI())
/* 1023:     */             {
/* 1024:1095 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1025:1096 */               local_ServerRequest.write_value((FFSException)localThrowable24, FFSException.class);
/* 1026:     */             }
/* 1027:     */             else
/* 1028:     */             {
/* 1029:1100 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1030:1101 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable24);
/* 1031:     */             }
/* 1032:     */           }
/* 1033:1104 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable24);
/* 1034:     */         }
/* 1035:1106 */         localThrowable24.printStackTrace(Jaguar.log);
/* 1036:1107 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/* 1037:1108 */         return localThrowable24.getClass().getName();
/* 1038:     */       }
/* 1039:     */     case 24: 
/* 1040:     */       try
/* 1041:     */       {
/* 1042:1116 */         String str8 = localBPWAdminBean
/* 1043:1117 */           .refreshScheduler();
/* 1044:     */         
/* 1045:1119 */         local_ServerRequest.write_string(str8);
/* 1046:     */       }
/* 1047:     */       catch (Throwable localThrowable25)
/* 1048:     */       {
/* 1049:1123 */         if ((localThrowable25 instanceof FFSException))
/* 1050:     */         {
/* 1051:1125 */           if (UserException.ok(paramOutputStream)) {
/* 1052:1127 */             if (local_ServerRequest.isRMI())
/* 1053:     */             {
/* 1054:1129 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1055:1130 */               local_ServerRequest.write_value((FFSException)localThrowable25, FFSException.class);
/* 1056:     */             }
/* 1057:     */             else
/* 1058:     */             {
/* 1059:1134 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1060:1135 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable25);
/* 1061:     */             }
/* 1062:     */           }
/* 1063:1138 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable25);
/* 1064:     */         }
/* 1065:1140 */         localThrowable25.printStackTrace(Jaguar.log);
/* 1066:1141 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/* 1067:1142 */         return localThrowable25.getClass().getName();
/* 1068:     */       }
/* 1069:     */     case 25: 
/* 1070:     */       try
/* 1071:     */       {
/* 1072:     */         PropertyConfig localPropertyConfig3;
/* 1073:1151 */         if (local_ServerRequest.isRMI()) {
/* 1074:1151 */           localPropertyConfig3 = (PropertyConfig)local_ServerRequest.read_value(PropertyConfig.class);
/* 1075:     */         } else {
/* 1076:1151 */           localPropertyConfig3 = PropertyConfigHelper.read(paramInputStream);
/* 1077:     */         }
/* 1078:1153 */         localBPWAdminBean.registerPropertyConfig(
/* 1079:1154 */           localPropertyConfig3);
/* 1080:     */       }
/* 1081:     */       catch (Throwable localThrowable26)
/* 1082:     */       {
/* 1083:1159 */         if ((localThrowable26 instanceof FFSException))
/* 1084:     */         {
/* 1085:1161 */           if (UserException.ok(paramOutputStream)) {
/* 1086:1163 */             if (local_ServerRequest.isRMI())
/* 1087:     */             {
/* 1088:1165 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1089:1166 */               local_ServerRequest.write_value((FFSException)localThrowable26, FFSException.class);
/* 1090:     */             }
/* 1091:     */             else
/* 1092:     */             {
/* 1093:1170 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1094:1171 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable26);
/* 1095:     */             }
/* 1096:     */           }
/* 1097:1174 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable26);
/* 1098:     */         }
/* 1099:1176 */         localThrowable26.printStackTrace(Jaguar.log);
/* 1100:1177 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/* 1101:1178 */         return localThrowable26.getClass().getName();
/* 1102:     */       }
/* 1103:     */     case 26: 
/* 1104:     */       try
/* 1105:     */       {
/* 1106:1187 */         String str9 = local_ServerRequest.read_string();
/* 1107:1188 */         localBPWAdminBean
/* 1108:1189 */           .startEngine(
/* 1109:1190 */           str9);
/* 1110:     */       }
/* 1111:     */       catch (Throwable localThrowable27)
/* 1112:     */       {
/* 1113:1195 */         if ((localThrowable27 instanceof FFSException))
/* 1114:     */         {
/* 1115:1197 */           if (UserException.ok(paramOutputStream)) {
/* 1116:1199 */             if (local_ServerRequest.isRMI())
/* 1117:     */             {
/* 1118:1201 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1119:1202 */               local_ServerRequest.write_value((FFSException)localThrowable27, FFSException.class);
/* 1120:     */             }
/* 1121:     */             else
/* 1122:     */             {
/* 1123:1206 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1124:1207 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable27);
/* 1125:     */             }
/* 1126:     */           }
/* 1127:1210 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable27);
/* 1128:     */         }
/* 1129:1212 */         localThrowable27.printStackTrace(Jaguar.log);
/* 1130:1213 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/* 1131:1214 */         return localThrowable27.getClass().getName();
/* 1132:     */       }
/* 1133:     */     case 27: 
/* 1134:     */       try
/* 1135:     */       {
/* 1136:1223 */         String str10 = local_ServerRequest.read_string();
/* 1137:1224 */         localBPWAdminBean
/* 1138:1225 */           .stopEngine(
/* 1139:1226 */           str10);
/* 1140:     */       }
/* 1141:     */       catch (Throwable localThrowable28)
/* 1142:     */       {
/* 1143:1231 */         if ((localThrowable28 instanceof FFSException))
/* 1144:     */         {
/* 1145:1233 */           if (UserException.ok(paramOutputStream)) {
/* 1146:1235 */             if (local_ServerRequest.isRMI())
/* 1147:     */             {
/* 1148:1237 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1149:1238 */               local_ServerRequest.write_value((FFSException)localThrowable28, FFSException.class);
/* 1150:     */             }
/* 1151:     */             else
/* 1152:     */             {
/* 1153:1242 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1154:1243 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable28);
/* 1155:     */             }
/* 1156:     */           }
/* 1157:1246 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable28);
/* 1158:     */         }
/* 1159:1248 */         localThrowable28.printStackTrace(Jaguar.log);
/* 1160:1249 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/* 1161:1250 */         return localThrowable28.getClass().getName();
/* 1162:     */       }
/* 1163:     */     case 28: 
/* 1164:     */       try
/* 1165:     */       {
/* 1166:1259 */         String str11 = local_ServerRequest.read_string();
/* 1167:1260 */         localBPWAdminBean
/* 1168:1261 */           .stopLimitCheckApprovalProcessor(
/* 1169:1262 */           str11);
/* 1170:     */       }
/* 1171:     */       catch (Throwable localThrowable29)
/* 1172:     */       {
/* 1173:1267 */         if ((localThrowable29 instanceof FFSException))
/* 1174:     */         {
/* 1175:1269 */           if (UserException.ok(paramOutputStream)) {
/* 1176:1271 */             if (local_ServerRequest.isRMI())
/* 1177:     */             {
/* 1178:1273 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1179:1274 */               local_ServerRequest.write_value((FFSException)localThrowable29, FFSException.class);
/* 1180:     */             }
/* 1181:     */             else
/* 1182:     */             {
/* 1183:1278 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1184:1279 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable29);
/* 1185:     */             }
/* 1186:     */           }
/* 1187:1282 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable29);
/* 1188:     */         }
/* 1189:1284 */         localThrowable29.printStackTrace(Jaguar.log);
/* 1190:1285 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/* 1191:1286 */         return localThrowable29.getClass().getName();
/* 1192:     */       }
/* 1193:     */     case 29: 
/* 1194:     */       try
/* 1195:     */       {
/* 1196:1295 */         String str12 = local_ServerRequest.read_string();
/* 1197:1296 */         localBPWAdminBean
/* 1198:1297 */           .startLimitCheckApprovalProcessor(
/* 1199:1298 */           str12);
/* 1200:     */       }
/* 1201:     */       catch (Throwable localThrowable30)
/* 1202:     */       {
/* 1203:1303 */         if ((localThrowable30 instanceof FFSException))
/* 1204:     */         {
/* 1205:1305 */           if (UserException.ok(paramOutputStream)) {
/* 1206:1307 */             if (local_ServerRequest.isRMI())
/* 1207:     */             {
/* 1208:1309 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1209:1310 */               local_ServerRequest.write_value((FFSException)localThrowable30, FFSException.class);
/* 1210:     */             }
/* 1211:     */             else
/* 1212:     */             {
/* 1213:1314 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1214:1315 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable30);
/* 1215:     */             }
/* 1216:     */           }
/* 1217:1318 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable30);
/* 1218:     */         }
/* 1219:1320 */         localThrowable30.printStackTrace(Jaguar.log);
/* 1220:1321 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/* 1221:1322 */         return localThrowable30.getClass().getName();
/* 1222:     */       }
/* 1223:     */     case 30: 
/* 1224:     */       try
/* 1225:     */       {
/* 1226:1331 */         String str13 = local_ServerRequest.read_string();
/* 1227:     */         
/* 1228:1333 */         localObject1 = local_ServerRequest.read_string();
/* 1229:1334 */         localBPWAdminBean
/* 1230:1335 */           .runBatchProcess(
/* 1231:1336 */           str13, 
/* 1232:1337 */           (String)localObject1);
/* 1233:     */       }
/* 1234:     */       catch (Throwable localThrowable31)
/* 1235:     */       {
/* 1236:1342 */         if ((localThrowable31 instanceof FFSException))
/* 1237:     */         {
/* 1238:1344 */           if (UserException.ok(paramOutputStream)) {
/* 1239:1346 */             if (local_ServerRequest.isRMI())
/* 1240:     */             {
/* 1241:1348 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1242:1349 */               local_ServerRequest.write_value((FFSException)localThrowable31, FFSException.class);
/* 1243:     */             }
/* 1244:     */             else
/* 1245:     */             {
/* 1246:1353 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1247:1354 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable31);
/* 1248:     */             }
/* 1249:     */           }
/* 1250:1357 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable31);
/* 1251:     */         }
/* 1252:1359 */         localThrowable31.printStackTrace(Jaguar.log);
/* 1253:1360 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/* 1254:1361 */         return localThrowable31.getClass().getName();
/* 1255:     */       }
/* 1256:     */     case 31: 
/* 1257:     */       try
/* 1258:     */       {
/* 1259:1370 */         InstructionType localInstructionType1 = (InstructionType)local_ServerRequest.read_value(InstructionType.class);
/* 1260:1371 */         localBPWAdminBean
/* 1261:1372 */           .updateScheduleRunTimeConfig(
/* 1262:1373 */           localInstructionType1);
/* 1263:     */       }
/* 1264:     */       catch (Throwable localThrowable32)
/* 1265:     */       {
/* 1266:1378 */         if ((localThrowable32 instanceof FFSException))
/* 1267:     */         {
/* 1268:1380 */           if (UserException.ok(paramOutputStream)) {
/* 1269:1382 */             if (local_ServerRequest.isRMI())
/* 1270:     */             {
/* 1271:1384 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1272:1385 */               local_ServerRequest.write_value((FFSException)localThrowable32, FFSException.class);
/* 1273:     */             }
/* 1274:     */             else
/* 1275:     */             {
/* 1276:1389 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1277:1390 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable32);
/* 1278:     */             }
/* 1279:     */           }
/* 1280:1393 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable32);
/* 1281:     */         }
/* 1282:1395 */         localThrowable32.printStackTrace(Jaguar.log);
/* 1283:1396 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/* 1284:1397 */         return localThrowable32.getClass().getName();
/* 1285:     */       }
/* 1286:     */     case 32: 
/* 1287:     */       try
/* 1288:     */       {
/* 1289:1406 */         InstructionType localInstructionType2 = (InstructionType)local_ServerRequest.read_value(InstructionType.class);
/* 1290:1407 */         localBPWAdminBean
/* 1291:1408 */           .updateScheduleProcessingConfig(
/* 1292:1409 */           localInstructionType2);
/* 1293:     */       }
/* 1294:     */       catch (Throwable localThrowable33)
/* 1295:     */       {
/* 1296:1414 */         if ((localThrowable33 instanceof FFSException))
/* 1297:     */         {
/* 1298:1416 */           if (UserException.ok(paramOutputStream)) {
/* 1299:1418 */             if (local_ServerRequest.isRMI())
/* 1300:     */             {
/* 1301:1420 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1302:1421 */               local_ServerRequest.write_value((FFSException)localThrowable33, FFSException.class);
/* 1303:     */             }
/* 1304:     */             else
/* 1305:     */             {
/* 1306:1425 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1307:1426 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable33);
/* 1308:     */             }
/* 1309:     */           }
/* 1310:1429 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable33);
/* 1311:     */         }
/* 1312:1431 */         localThrowable33.printStackTrace(Jaguar.log);
/* 1313:1432 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/* 1314:1433 */         return localThrowable33.getClass().getName();
/* 1315:     */       }
/* 1316:     */     case 33: 
/* 1317:     */       try
/* 1318:     */       {
/* 1319:1442 */         InstructionType localInstructionType3 = (InstructionType)local_ServerRequest.read_value(InstructionType.class);
/* 1320:1443 */         localBPWAdminBean
/* 1321:1444 */           .updateScheduleConfig(
/* 1322:1445 */           localInstructionType3);
/* 1323:     */       }
/* 1324:     */       catch (Throwable localThrowable34)
/* 1325:     */       {
/* 1326:1450 */         if ((localThrowable34 instanceof FFSException))
/* 1327:     */         {
/* 1328:1452 */           if (UserException.ok(paramOutputStream)) {
/* 1329:1454 */             if (local_ServerRequest.isRMI())
/* 1330:     */             {
/* 1331:1456 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1332:1457 */               local_ServerRequest.write_value((FFSException)localThrowable34, FFSException.class);
/* 1333:     */             }
/* 1334:     */             else
/* 1335:     */             {
/* 1336:1461 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1337:1462 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable34);
/* 1338:     */             }
/* 1339:     */           }
/* 1340:1465 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable34);
/* 1341:     */         }
/* 1342:1467 */         localThrowable34.printStackTrace(Jaguar.log);
/* 1343:1468 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/* 1344:1469 */         return localThrowable34.getClass().getName();
/* 1345:     */       }
/* 1346:     */     case 34: 
/* 1347:     */       try
/* 1348:     */       {
/* 1349:1478 */         String str14 = local_ServerRequest.read_string();
/* 1350:     */         
/* 1351:1480 */         localObject1 = local_ServerRequest.read_string();
/* 1352:1481 */         localObject4 = localBPWAdminBean
/* 1353:1482 */           .getScheduleConfig(
/* 1354:1483 */           str14, 
/* 1355:1484 */           (String)localObject1);
/* 1356:     */         
/* 1357:1486 */         local_ServerRequest.write_value(localObject4, InstructionType.class);
/* 1358:     */       }
/* 1359:     */       catch (Throwable localThrowable35)
/* 1360:     */       {
/* 1361:1490 */         if ((localThrowable35 instanceof FFSException))
/* 1362:     */         {
/* 1363:1492 */           if (UserException.ok(paramOutputStream)) {
/* 1364:1494 */             if (local_ServerRequest.isRMI())
/* 1365:     */             {
/* 1366:1496 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1367:1497 */               local_ServerRequest.write_value((FFSException)localThrowable35, FFSException.class);
/* 1368:     */             }
/* 1369:     */             else
/* 1370:     */             {
/* 1371:1501 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1372:1502 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable35);
/* 1373:     */             }
/* 1374:     */           }
/* 1375:1505 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable35);
/* 1376:     */         }
/* 1377:1507 */         localThrowable35.printStackTrace(Jaguar.log);
/* 1378:1508 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/* 1379:1509 */         return localThrowable35.getClass().getName();
/* 1380:     */       }
/* 1381:     */     case 35: 
/* 1382:     */       try
/* 1383:     */       {
/* 1384:1517 */         InstructionType[] arrayOfInstructionType = localBPWAdminBean
/* 1385:1518 */           .getScheduleConfig();
/* 1386:1520 */         if (local_ServerRequest.isRMI()) {
/* 1387:1520 */           local_ServerRequest.write_value(arrayOfInstructionType, new InstructionType[0].getClass());
/* 1388:     */         } else {
/* 1389:1520 */           InstructionTypeSeqHelper.write(paramOutputStream, arrayOfInstructionType);
/* 1390:     */         }
/* 1391:     */       }
/* 1392:     */       catch (Throwable localThrowable36)
/* 1393:     */       {
/* 1394:1524 */         if ((localThrowable36 instanceof FFSException))
/* 1395:     */         {
/* 1396:1526 */           if (UserException.ok(paramOutputStream)) {
/* 1397:1528 */             if (local_ServerRequest.isRMI())
/* 1398:     */             {
/* 1399:1530 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1400:1531 */               local_ServerRequest.write_value((FFSException)localThrowable36, FFSException.class);
/* 1401:     */             }
/* 1402:     */             else
/* 1403:     */             {
/* 1404:1535 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1405:1536 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable36);
/* 1406:     */             }
/* 1407:     */           }
/* 1408:1539 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable36);
/* 1409:     */         }
/* 1410:1541 */         localThrowable36.printStackTrace(Jaguar.log);
/* 1411:1542 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/* 1412:1543 */         return localThrowable36.getClass().getName();
/* 1413:     */       }
/* 1414:     */     case 36: 
/* 1415:     */       try
/* 1416:     */       {
/* 1417:1552 */         String str15 = local_ServerRequest.read_string();
/* 1418:     */         
/* 1419:1554 */         localObject1 = local_ServerRequest.read_string();
/* 1420:1555 */         localObject4 = localBPWAdminBean
/* 1421:1556 */           .getSchedulerInfo(
/* 1422:1557 */           str15, 
/* 1423:1558 */           (String)localObject1);
/* 1424:     */         
/* 1425:1560 */         local_ServerRequest.write_value(localObject4, SchedulerInfo.class);
/* 1426:     */       }
/* 1427:     */       catch (Throwable localThrowable37)
/* 1428:     */       {
/* 1429:1564 */         if ((localThrowable37 instanceof FFSException))
/* 1430:     */         {
/* 1431:1566 */           if (UserException.ok(paramOutputStream)) {
/* 1432:1568 */             if (local_ServerRequest.isRMI())
/* 1433:     */             {
/* 1434:1570 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1435:1571 */               local_ServerRequest.write_value((FFSException)localThrowable37, FFSException.class);
/* 1436:     */             }
/* 1437:     */             else
/* 1438:     */             {
/* 1439:1575 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1440:1576 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable37);
/* 1441:     */             }
/* 1442:     */           }
/* 1443:1579 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable37);
/* 1444:     */         }
/* 1445:1581 */         localThrowable37.printStackTrace(Jaguar.log);
/* 1446:1582 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable37, true);
/* 1447:1583 */         return localThrowable37.getClass().getName();
/* 1448:     */       }
/* 1449:     */     case 37: 
/* 1450:     */       try
/* 1451:     */       {
/* 1452:1591 */         SchedulerInfo[] arrayOfSchedulerInfo = localBPWAdminBean
/* 1453:1592 */           .getSchedulerInfo();
/* 1454:1594 */         if (local_ServerRequest.isRMI()) {
/* 1455:1594 */           local_ServerRequest.write_value(arrayOfSchedulerInfo, new SchedulerInfo[0].getClass());
/* 1456:     */         } else {
/* 1457:1594 */           SchedulerInfoSeqHelper.write(paramOutputStream, arrayOfSchedulerInfo);
/* 1458:     */         }
/* 1459:     */       }
/* 1460:     */       catch (Throwable localThrowable38)
/* 1461:     */       {
/* 1462:1598 */         if ((localThrowable38 instanceof FFSException))
/* 1463:     */         {
/* 1464:1600 */           if (UserException.ok(paramOutputStream)) {
/* 1465:1602 */             if (local_ServerRequest.isRMI())
/* 1466:     */             {
/* 1467:1604 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1468:1605 */               local_ServerRequest.write_value((FFSException)localThrowable38, FFSException.class);
/* 1469:     */             }
/* 1470:     */             else
/* 1471:     */             {
/* 1472:1609 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1473:1610 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable38);
/* 1474:     */             }
/* 1475:     */           }
/* 1476:1613 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable38);
/* 1477:     */         }
/* 1478:1615 */         localThrowable38.printStackTrace(Jaguar.log);
/* 1479:1616 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable38, true);
/* 1480:1617 */         return localThrowable38.getClass().getName();
/* 1481:     */       }
/* 1482:     */     case 38: 
/* 1483:     */       try
/* 1484:     */       {
/* 1485:1626 */         String str16 = local_ServerRequest.read_string();
/* 1486:     */         
/* 1487:1628 */         localObject1 = local_ServerRequest.read_string();
/* 1488:1630 */         if (local_ServerRequest.isRMI()) {
/* 1489:1630 */           localObject4 = (ScheduleHist)local_ServerRequest.read_value(ScheduleHist.class);
/* 1490:     */         } else {
/* 1491:1630 */           localObject4 = ScheduleHistHelper.read(paramInputStream);
/* 1492:     */         }
/* 1493:1631 */         localObject5 = 
/* 1494:1632 */           localBPWAdminBean.getScheduleHist(
/* 1495:1633 */           str16, 
/* 1496:1634 */           (String)localObject1, 
/* 1497:1635 */           (ScheduleHist)localObject4);
/* 1498:1637 */         if (local_ServerRequest.isRMI()) {
/* 1499:1637 */           local_ServerRequest.write_value(localObject5, new ScheduleHist[0].getClass());
/* 1500:     */         } else {
/* 1501:1637 */           ScheduleHistSeqHelper.write(paramOutputStream, (ScheduleHist[])localObject5);
/* 1502:     */         }
/* 1503:     */       }
/* 1504:     */       catch (Throwable localThrowable39)
/* 1505:     */       {
/* 1506:1641 */         if ((localThrowable39 instanceof FFSException))
/* 1507:     */         {
/* 1508:1643 */           if (UserException.ok(paramOutputStream)) {
/* 1509:1645 */             if (local_ServerRequest.isRMI())
/* 1510:     */             {
/* 1511:1647 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1512:1648 */               local_ServerRequest.write_value((FFSException)localThrowable39, FFSException.class);
/* 1513:     */             }
/* 1514:     */             else
/* 1515:     */             {
/* 1516:1652 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1517:1653 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable39);
/* 1518:     */             }
/* 1519:     */           }
/* 1520:1656 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable39);
/* 1521:     */         }
/* 1522:1658 */         localThrowable39.printStackTrace(Jaguar.log);
/* 1523:1659 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable39, true);
/* 1524:1660 */         return localThrowable39.getClass().getName();
/* 1525:     */       }
/* 1526:     */     case 39: 
/* 1527:     */       try
/* 1528:     */       {
/* 1529:1669 */         PayeeInfo localPayeeInfo1 = (PayeeInfo)local_ServerRequest.read_value(PayeeInfo.class);
/* 1530:     */         
/* 1531:1671 */         int i = paramInputStream.read_long();
/* 1532:1672 */         localObject4 = localBPWAdminBean
/* 1533:1673 */           .searchGlobalPayees(
/* 1534:1674 */           localPayeeInfo1, 
/* 1535:1675 */           i);
/* 1536:1677 */         if (local_ServerRequest.isRMI()) {
/* 1537:1677 */           local_ServerRequest.write_value(localObject4, new PayeeInfo[0].getClass());
/* 1538:     */         } else {
/* 1539:1677 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject4);
/* 1540:     */         }
/* 1541:     */       }
/* 1542:     */       catch (Throwable localThrowable40)
/* 1543:     */       {
/* 1544:1681 */         if ((localThrowable40 instanceof FFSException))
/* 1545:     */         {
/* 1546:1683 */           if (UserException.ok(paramOutputStream)) {
/* 1547:1685 */             if (local_ServerRequest.isRMI())
/* 1548:     */             {
/* 1549:1687 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1550:1688 */               local_ServerRequest.write_value((FFSException)localThrowable40, FFSException.class);
/* 1551:     */             }
/* 1552:     */             else
/* 1553:     */             {
/* 1554:1692 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1555:1693 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable40);
/* 1556:     */             }
/* 1557:     */           }
/* 1558:1696 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable40);
/* 1559:     */         }
/* 1560:1698 */         localThrowable40.printStackTrace(Jaguar.log);
/* 1561:1699 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable40, true);
/* 1562:1700 */         return localThrowable40.getClass().getName();
/* 1563:     */       }
/* 1564:     */     case 40: 
/* 1565:     */       try
/* 1566:     */       {
/* 1567:1709 */         String str17 = local_ServerRequest.read_string();
/* 1568:1710 */         localObject2 = localBPWAdminBean
/* 1569:1711 */           .searchGlobalPayees(
/* 1570:1712 */           str17);
/* 1571:1714 */         if (local_ServerRequest.isRMI()) {
/* 1572:1714 */           local_ServerRequest.write_value(localObject2, new PayeeInfo[0].getClass());
/* 1573:     */         } else {
/* 1574:1714 */           PayeeInfoSeqHelper.write(paramOutputStream, (PayeeInfo[])localObject2);
/* 1575:     */         }
/* 1576:     */       }
/* 1577:     */       catch (Throwable localThrowable41)
/* 1578:     */       {
/* 1579:1718 */         if ((localThrowable41 instanceof FFSException))
/* 1580:     */         {
/* 1581:1720 */           if (UserException.ok(paramOutputStream)) {
/* 1582:1722 */             if (local_ServerRequest.isRMI())
/* 1583:     */             {
/* 1584:1724 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1585:1725 */               local_ServerRequest.write_value((FFSException)localThrowable41, FFSException.class);
/* 1586:     */             }
/* 1587:     */             else
/* 1588:     */             {
/* 1589:1729 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1590:1730 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable41);
/* 1591:     */             }
/* 1592:     */           }
/* 1593:1733 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable41);
/* 1594:     */         }
/* 1595:1735 */         localThrowable41.printStackTrace(Jaguar.log);
/* 1596:1736 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable41, true);
/* 1597:1737 */         return localThrowable41.getClass().getName();
/* 1598:     */       }
/* 1599:     */     case 41: 
/* 1600:     */       try
/* 1601:     */       {
/* 1602:1746 */         String str18 = local_ServerRequest.read_string();
/* 1603:1747 */         localObject2 = localBPWAdminBean
/* 1604:1748 */           .getGlobalPayee(
/* 1605:1749 */           str18);
/* 1606:     */         
/* 1607:1751 */         local_ServerRequest.write_value(localObject2, PayeeInfo.class);
/* 1608:     */       }
/* 1609:     */       catch (Throwable localThrowable42)
/* 1610:     */       {
/* 1611:1755 */         if ((localThrowable42 instanceof FFSException))
/* 1612:     */         {
/* 1613:1757 */           if (UserException.ok(paramOutputStream)) {
/* 1614:1759 */             if (local_ServerRequest.isRMI())
/* 1615:     */             {
/* 1616:1761 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1617:1762 */               local_ServerRequest.write_value((FFSException)localThrowable42, FFSException.class);
/* 1618:     */             }
/* 1619:     */             else
/* 1620:     */             {
/* 1621:1766 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1622:1767 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable42);
/* 1623:     */             }
/* 1624:     */           }
/* 1625:1770 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable42);
/* 1626:     */         }
/* 1627:1772 */         localThrowable42.printStackTrace(Jaguar.log);
/* 1628:1773 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable42, true);
/* 1629:1774 */         return localThrowable42.getClass().getName();
/* 1630:     */       }
/* 1631:     */     case 42: 
/* 1632:     */       try
/* 1633:     */       {
/* 1634:1783 */         PayeeInfo localPayeeInfo2 = (PayeeInfo)local_ServerRequest.read_value(PayeeInfo.class);
/* 1635:1784 */         localObject2 = localBPWAdminBean
/* 1636:1785 */           .updateGlobalPayee(
/* 1637:1786 */           localPayeeInfo2);
/* 1638:     */         
/* 1639:1788 */         local_ServerRequest.write_value(localObject2, PayeeInfo.class);
/* 1640:     */       }
/* 1641:     */       catch (Throwable localThrowable43)
/* 1642:     */       {
/* 1643:1792 */         if ((localThrowable43 instanceof FFSException))
/* 1644:     */         {
/* 1645:1794 */           if (UserException.ok(paramOutputStream)) {
/* 1646:1796 */             if (local_ServerRequest.isRMI())
/* 1647:     */             {
/* 1648:1798 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1649:1799 */               local_ServerRequest.write_value((FFSException)localThrowable43, FFSException.class);
/* 1650:     */             }
/* 1651:     */             else
/* 1652:     */             {
/* 1653:1803 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1654:1804 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable43);
/* 1655:     */             }
/* 1656:     */           }
/* 1657:1807 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable43);
/* 1658:     */         }
/* 1659:1809 */         localThrowable43.printStackTrace(Jaguar.log);
/* 1660:1810 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable43, true);
/* 1661:1811 */         return localThrowable43.getClass().getName();
/* 1662:     */       }
/* 1663:     */     case 43: 
/* 1664:     */       try
/* 1665:     */       {
/* 1666:     */         FFSDBProperties localFFSDBProperties1;
/* 1667:1820 */         if (local_ServerRequest.isRMI()) {
/* 1668:1820 */           localFFSDBProperties1 = (FFSDBProperties)local_ServerRequest.read_value(FFSDBProperties.class);
/* 1669:     */         } else {
/* 1670:1820 */           localFFSDBProperties1 = FFSDBPropertiesHelper.read(paramInputStream);
/* 1671:     */         }
/* 1672:1822 */         localObject2 = (PayeeInfo)local_ServerRequest.read_value(PayeeInfo.class);
/* 1673:     */         
/* 1674:1824 */         localObject4 = (PayeeRouteInfo)local_ServerRequest.read_value(PayeeRouteInfo.class);
/* 1675:1825 */         localObject5 = localBPWAdminBean
/* 1676:1826 */           .addPayee(
/* 1677:1827 */           localFFSDBProperties1, 
/* 1678:1828 */           (PayeeInfo)localObject2, 
/* 1679:1829 */           (PayeeRouteInfo)localObject4);
/* 1680:     */         
/* 1681:1831 */         local_ServerRequest.write_string((String)localObject5);
/* 1682:     */       }
/* 1683:     */       catch (Throwable localThrowable44)
/* 1684:     */       {
/* 1685:1835 */         if ((localThrowable44 instanceof FFSException))
/* 1686:     */         {
/* 1687:1837 */           if (UserException.ok(paramOutputStream)) {
/* 1688:1839 */             if (local_ServerRequest.isRMI())
/* 1689:     */             {
/* 1690:1841 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1691:1842 */               local_ServerRequest.write_value((FFSException)localThrowable44, FFSException.class);
/* 1692:     */             }
/* 1693:     */             else
/* 1694:     */             {
/* 1695:1846 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1696:1847 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable44);
/* 1697:     */             }
/* 1698:     */           }
/* 1699:1850 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable44);
/* 1700:     */         }
/* 1701:1852 */         localThrowable44.printStackTrace(Jaguar.log);
/* 1702:1853 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable44, true);
/* 1703:1854 */         return localThrowable44.getClass().getName();
/* 1704:     */       }
/* 1705:     */     case 44: 
/* 1706:     */       try
/* 1707:     */       {
/* 1708:     */         FFSDBProperties localFFSDBProperties2;
/* 1709:1863 */         if (local_ServerRequest.isRMI()) {
/* 1710:1863 */           localFFSDBProperties2 = (FFSDBProperties)local_ServerRequest.read_value(FFSDBProperties.class);
/* 1711:     */         } else {
/* 1712:1863 */           localFFSDBProperties2 = FFSDBPropertiesHelper.read(paramInputStream);
/* 1713:     */         }
/* 1714:1865 */         localObject2 = (PayeeInfo)local_ServerRequest.read_value(PayeeInfo.class);
/* 1715:     */         
/* 1716:1867 */         localObject4 = (PayeeRouteInfo)local_ServerRequest.read_value(PayeeRouteInfo.class);
/* 1717:1868 */         localBPWAdminBean
/* 1718:1869 */           .updatePayee(
/* 1719:1870 */           localFFSDBProperties2, 
/* 1720:1871 */           (PayeeInfo)localObject2, 
/* 1721:1872 */           (PayeeRouteInfo)localObject4);
/* 1722:     */       }
/* 1723:     */       catch (Throwable localThrowable45)
/* 1724:     */       {
/* 1725:1877 */         if ((localThrowable45 instanceof FFSException))
/* 1726:     */         {
/* 1727:1879 */           if (UserException.ok(paramOutputStream)) {
/* 1728:1881 */             if (local_ServerRequest.isRMI())
/* 1729:     */             {
/* 1730:1883 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1731:1884 */               local_ServerRequest.write_value((FFSException)localThrowable45, FFSException.class);
/* 1732:     */             }
/* 1733:     */             else
/* 1734:     */             {
/* 1735:1888 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1736:1889 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable45);
/* 1737:     */             }
/* 1738:     */           }
/* 1739:1892 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable45);
/* 1740:     */         }
/* 1741:1894 */         localThrowable45.printStackTrace(Jaguar.log);
/* 1742:1895 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable45, true);
/* 1743:1896 */         return localThrowable45.getClass().getName();
/* 1744:     */       }
/* 1745:     */     case 45: 
/* 1746:     */       try
/* 1747:     */       {
/* 1748:     */         FFSDBProperties localFFSDBProperties3;
/* 1749:1905 */         if (local_ServerRequest.isRMI()) {
/* 1750:1905 */           localFFSDBProperties3 = (FFSDBProperties)local_ServerRequest.read_value(FFSDBProperties.class);
/* 1751:     */         } else {
/* 1752:1905 */           localFFSDBProperties3 = FFSDBPropertiesHelper.read(paramInputStream);
/* 1753:     */         }
/* 1754:1907 */         localObject2 = local_ServerRequest.read_string();
/* 1755:1908 */         localBPWAdminBean
/* 1756:1909 */           .deletePayee(
/* 1757:1910 */           localFFSDBProperties3, 
/* 1758:1911 */           (String)localObject2);
/* 1759:     */       }
/* 1760:     */       catch (Throwable localThrowable46)
/* 1761:     */       {
/* 1762:1916 */         if ((localThrowable46 instanceof FFSException))
/* 1763:     */         {
/* 1764:1918 */           if (UserException.ok(paramOutputStream)) {
/* 1765:1920 */             if (local_ServerRequest.isRMI())
/* 1766:     */             {
/* 1767:1922 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1768:1923 */               local_ServerRequest.write_value((FFSException)localThrowable46, FFSException.class);
/* 1769:     */             }
/* 1770:     */             else
/* 1771:     */             {
/* 1772:1927 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1773:1928 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable46);
/* 1774:     */             }
/* 1775:     */           }
/* 1776:1931 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable46);
/* 1777:     */         }
/* 1778:1933 */         localThrowable46.printStackTrace(Jaguar.log);
/* 1779:1934 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable46, true);
/* 1780:1935 */         return localThrowable46.getClass().getName();
/* 1781:     */       }
/* 1782:     */     case 46: 
/* 1783:     */       try
/* 1784:     */       {
/* 1785:     */         FFSDBProperties localFFSDBProperties4;
/* 1786:1944 */         if (local_ServerRequest.isRMI()) {
/* 1787:1944 */           localFFSDBProperties4 = (FFSDBProperties)local_ServerRequest.read_value(FFSDBProperties.class);
/* 1788:     */         } else {
/* 1789:1944 */           localFFSDBProperties4 = FFSDBPropertiesHelper.read(paramInputStream);
/* 1790:     */         }
/* 1791:1946 */         localObject2 = local_ServerRequest.read_string();
/* 1792:     */         
/* 1793:1948 */         int k = paramInputStream.read_long();
/* 1794:1949 */         localObject5 = localBPWAdminBean
/* 1795:1950 */           .getPayeeRoute(
/* 1796:1951 */           localFFSDBProperties4, 
/* 1797:1952 */           (String)localObject2, 
/* 1798:1953 */           k);
/* 1799:     */         
/* 1800:1955 */         local_ServerRequest.write_value(localObject5, PayeeRouteInfo.class);
/* 1801:     */       }
/* 1802:     */       catch (Throwable localThrowable47)
/* 1803:     */       {
/* 1804:1959 */         if ((localThrowable47 instanceof FFSException))
/* 1805:     */         {
/* 1806:1961 */           if (UserException.ok(paramOutputStream)) {
/* 1807:1963 */             if (local_ServerRequest.isRMI())
/* 1808:     */             {
/* 1809:1965 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1810:1966 */               local_ServerRequest.write_value((FFSException)localThrowable47, FFSException.class);
/* 1811:     */             }
/* 1812:     */             else
/* 1813:     */             {
/* 1814:1970 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1815:1971 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable47);
/* 1816:     */             }
/* 1817:     */           }
/* 1818:1974 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable47);
/* 1819:     */         }
/* 1820:1976 */         localThrowable47.printStackTrace(Jaguar.log);
/* 1821:1977 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable47, true);
/* 1822:1978 */         return localThrowable47.getClass().getName();
/* 1823:     */       }
/* 1824:     */     case 47: 
/* 1825:     */       try
/* 1826:     */       {
/* 1827:     */         FFSDBProperties localFFSDBProperties5;
/* 1828:1987 */         if (local_ServerRequest.isRMI()) {
/* 1829:1987 */           localFFSDBProperties5 = (FFSDBProperties)local_ServerRequest.read_value(FFSDBProperties.class);
/* 1830:     */         } else {
/* 1831:1987 */           localFFSDBProperties5 = FFSDBPropertiesHelper.read(paramInputStream);
/* 1832:     */         }
/* 1833:1989 */         localObject2 = local_ServerRequest.read_string();
/* 1834:1990 */         PayeeInfo localPayeeInfo3 = localBPWAdminBean
/* 1835:1991 */           .findPayeeByID(
/* 1836:1992 */           localFFSDBProperties5, 
/* 1837:1993 */           (String)localObject2);
/* 1838:     */         
/* 1839:1995 */         local_ServerRequest.write_value(localPayeeInfo3, PayeeInfo.class);
/* 1840:     */       }
/* 1841:     */       catch (Throwable localThrowable48)
/* 1842:     */       {
/* 1843:1999 */         if ((localThrowable48 instanceof FFSException))
/* 1844:     */         {
/* 1845:2001 */           if (UserException.ok(paramOutputStream)) {
/* 1846:2003 */             if (local_ServerRequest.isRMI())
/* 1847:     */             {
/* 1848:2005 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1849:2006 */               local_ServerRequest.write_value((FFSException)localThrowable48, FFSException.class);
/* 1850:     */             }
/* 1851:     */             else
/* 1852:     */             {
/* 1853:2010 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1854:2011 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable48);
/* 1855:     */             }
/* 1856:     */           }
/* 1857:2014 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable48);
/* 1858:     */         }
/* 1859:2016 */         localThrowable48.printStackTrace(Jaguar.log);
/* 1860:2017 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable48, true);
/* 1861:2018 */         return localThrowable48.getClass().getName();
/* 1862:     */       }
/* 1863:     */     case 48: 
/* 1864:     */       try
/* 1865:     */       {
/* 1866:     */         FFSDBProperties localFFSDBProperties6;
/* 1867:2027 */         if (local_ServerRequest.isRMI()) {
/* 1868:2027 */           localFFSDBProperties6 = (FFSDBProperties)local_ServerRequest.read_value(FFSDBProperties.class);
/* 1869:     */         } else {
/* 1870:2027 */           localFFSDBProperties6 = FFSDBPropertiesHelper.read(paramInputStream);
/* 1871:     */         }
/* 1872:2028 */         localObject2 = 
/* 1873:2029 */           localBPWAdminBean.getAllFulfillmentInfo(
/* 1874:2030 */           localFFSDBProperties6);
/* 1875:2032 */         if (local_ServerRequest.isRMI()) {
/* 1876:2032 */           local_ServerRequest.write_value(localObject2, new FulfillmentInfo[0].getClass());
/* 1877:     */         } else {
/* 1878:2032 */           FulfillmentInfoSeqHelper.write(paramOutputStream, (FulfillmentInfo[])localObject2);
/* 1879:     */         }
/* 1880:     */       }
/* 1881:     */       catch (Throwable localThrowable49)
/* 1882:     */       {
/* 1883:2036 */         if ((localThrowable49 instanceof FFSException))
/* 1884:     */         {
/* 1885:2038 */           if (UserException.ok(paramOutputStream)) {
/* 1886:2040 */             if (local_ServerRequest.isRMI())
/* 1887:     */             {
/* 1888:2042 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1889:2043 */               local_ServerRequest.write_value((FFSException)localThrowable49, FFSException.class);
/* 1890:     */             }
/* 1891:     */             else
/* 1892:     */             {
/* 1893:2047 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1894:2048 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable49);
/* 1895:     */             }
/* 1896:     */           }
/* 1897:2051 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable49);
/* 1898:     */         }
/* 1899:2053 */         localThrowable49.printStackTrace(Jaguar.log);
/* 1900:2054 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable49, true);
/* 1901:2055 */         return localThrowable49.getClass().getName();
/* 1902:     */       }
/* 1903:     */     case 49: 
/* 1904:     */       try
/* 1905:     */       {
/* 1906:2063 */         FulfillmentInfo[] arrayOfFulfillmentInfo = localBPWAdminBean
/* 1907:2064 */           .getFulfillmentSystems();
/* 1908:2066 */         if (local_ServerRequest.isRMI()) {
/* 1909:2066 */           local_ServerRequest.write_value(arrayOfFulfillmentInfo, new FulfillmentInfo[0].getClass());
/* 1910:     */         } else {
/* 1911:2066 */           FulfillmentInfoSeqHelper.write(paramOutputStream, arrayOfFulfillmentInfo);
/* 1912:     */         }
/* 1913:     */       }
/* 1914:     */       catch (Throwable localThrowable50)
/* 1915:     */       {
/* 1916:2070 */         if ((localThrowable50 instanceof FFSException))
/* 1917:     */         {
/* 1918:2072 */           if (UserException.ok(paramOutputStream)) {
/* 1919:2074 */             if (local_ServerRequest.isRMI())
/* 1920:     */             {
/* 1921:2076 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1922:2077 */               local_ServerRequest.write_value((FFSException)localThrowable50, FFSException.class);
/* 1923:     */             }
/* 1924:     */             else
/* 1925:     */             {
/* 1926:2081 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1927:2082 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable50);
/* 1928:     */             }
/* 1929:     */           }
/* 1930:2085 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable50);
/* 1931:     */         }
/* 1932:2087 */         localThrowable50.printStackTrace(Jaguar.log);
/* 1933:2088 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable50, true);
/* 1934:2089 */         return localThrowable50.getClass().getName();
/* 1935:     */       }
/* 1936:     */     case 50: 
/* 1937:     */       try
/* 1938:     */       {
/* 1939:2097 */         String[] arrayOfString = localBPWAdminBean
/* 1940:2098 */           .getGlobalPayeeGroups();
/* 1941:2100 */         if (local_ServerRequest.isRMI()) {
/* 1942:2100 */           local_ServerRequest.write_value(arrayOfString, new String[0].getClass());
/* 1943:     */         } else {
/* 1944:2100 */           StringSeqHelper.write(paramOutputStream, arrayOfString);
/* 1945:     */         }
/* 1946:     */       }
/* 1947:     */       catch (Throwable localThrowable51)
/* 1948:     */       {
/* 1949:2104 */         if ((localThrowable51 instanceof FFSException))
/* 1950:     */         {
/* 1951:2106 */           if (UserException.ok(paramOutputStream)) {
/* 1952:2108 */             if (local_ServerRequest.isRMI())
/* 1953:     */             {
/* 1954:2110 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 1955:2111 */               local_ServerRequest.write_value((FFSException)localThrowable51, FFSException.class);
/* 1956:     */             }
/* 1957:     */             else
/* 1958:     */             {
/* 1959:2115 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 1960:2116 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable51);
/* 1961:     */             }
/* 1962:     */           }
/* 1963:2119 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable51);
/* 1964:     */         }
/* 1965:2121 */         localThrowable51.printStackTrace(Jaguar.log);
/* 1966:2122 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable51, true);
/* 1967:2123 */         return localThrowable51.getClass().getName();
/* 1968:     */       }
/* 1969:     */     default: 
/* 1970:2129 */       return 
/* 1971:2130 */         invoke1(
/* 1972:2131 */         local_ServerRequest, 
/* 1973:2132 */         paramInputStream, 
/* 1974:2133 */         paramOutputStream, 
/* 1975:2134 */         localBPWAdminBean, 
/* 1976:2135 */         localInteger);
/* 1977:     */     }
/* 1978:2139 */     return null;
/* 1979:     */   }
/* 1980:     */   
/* 1981:     */   private static String invoke1(_ServerRequest param_ServerRequest, InputStream paramInputStream, OutputStream paramOutputStream, BPWAdminBean paramBPWAdminBean, Integer paramInteger)
/* 1982:     */   {
/* 1983:     */     FulfillmentInfo localFulfillmentInfo;
/* 1984:     */     Object localObject1;
/* 1985:     */     Object localObject2;
/* 1986:     */     String str4;
/* 1987:2149 */     switch (paramInteger.intValue())
/* 1988:     */     {
/* 1989:     */     case 51: 
/* 1990:     */       try
/* 1991:     */       {
/* 1992:     */         FFSDBProperties localFFSDBProperties1;
/* 1993:2156 */         if (param_ServerRequest.isRMI()) {
/* 1994:2156 */           localFFSDBProperties1 = (FFSDBProperties)param_ServerRequest.read_value(FFSDBProperties.class);
/* 1995:     */         } else {
/* 1996:2156 */           localFFSDBProperties1 = FFSDBPropertiesHelper.read(paramInputStream);
/* 1997:     */         }
/* 1998:2158 */         localFulfillmentInfo = (FulfillmentInfo)param_ServerRequest.read_value(FulfillmentInfo.class);
/* 1999:2159 */         paramBPWAdminBean
/* 2000:2160 */           .addFulfillmentInfo(
/* 2001:2161 */           localFFSDBProperties1, 
/* 2002:2162 */           localFulfillmentInfo);
/* 2003:     */       }
/* 2004:     */       catch (Throwable localThrowable1)
/* 2005:     */       {
/* 2006:2167 */         if ((localThrowable1 instanceof FFSException))
/* 2007:     */         {
/* 2008:2169 */           if (UserException.ok(paramOutputStream)) {
/* 2009:2171 */             if (param_ServerRequest.isRMI())
/* 2010:     */             {
/* 2011:2173 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2012:2174 */               param_ServerRequest.write_value((FFSException)localThrowable1, FFSException.class);
/* 2013:     */             }
/* 2014:     */             else
/* 2015:     */             {
/* 2016:2178 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2017:2179 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable1);
/* 2018:     */             }
/* 2019:     */           }
/* 2020:2182 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable1);
/* 2021:     */         }
/* 2022:2184 */         localThrowable1.printStackTrace(Jaguar.log);
/* 2023:2185 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/* 2024:2186 */         return localThrowable1.getClass().getName();
/* 2025:     */       }
/* 2026:     */     case 52: 
/* 2027:     */       try
/* 2028:     */       {
/* 2029:     */         FFSDBProperties localFFSDBProperties2;
/* 2030:2195 */         if (param_ServerRequest.isRMI()) {
/* 2031:2195 */           localFFSDBProperties2 = (FFSDBProperties)param_ServerRequest.read_value(FFSDBProperties.class);
/* 2032:     */         } else {
/* 2033:2195 */           localFFSDBProperties2 = FFSDBPropertiesHelper.read(paramInputStream);
/* 2034:     */         }
/* 2035:2197 */         localFulfillmentInfo = (FulfillmentInfo)param_ServerRequest.read_value(FulfillmentInfo.class);
/* 2036:2198 */         paramBPWAdminBean
/* 2037:2199 */           .updateFulfillmentInfo(
/* 2038:2200 */           localFFSDBProperties2, 
/* 2039:2201 */           localFulfillmentInfo);
/* 2040:     */       }
/* 2041:     */       catch (Throwable localThrowable2)
/* 2042:     */       {
/* 2043:2206 */         if ((localThrowable2 instanceof FFSException))
/* 2044:     */         {
/* 2045:2208 */           if (UserException.ok(paramOutputStream)) {
/* 2046:2210 */             if (param_ServerRequest.isRMI())
/* 2047:     */             {
/* 2048:2212 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2049:2213 */               param_ServerRequest.write_value((FFSException)localThrowable2, FFSException.class);
/* 2050:     */             }
/* 2051:     */             else
/* 2052:     */             {
/* 2053:2217 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2054:2218 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable2);
/* 2055:     */             }
/* 2056:     */           }
/* 2057:2221 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable2);
/* 2058:     */         }
/* 2059:2223 */         localThrowable2.printStackTrace(Jaguar.log);
/* 2060:2224 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/* 2061:2225 */         return localThrowable2.getClass().getName();
/* 2062:     */       }
/* 2063:     */     case 53: 
/* 2064:     */       try
/* 2065:     */       {
/* 2066:     */         FFSDBProperties localFFSDBProperties3;
/* 2067:2234 */         if (param_ServerRequest.isRMI()) {
/* 2068:2234 */           localFFSDBProperties3 = (FFSDBProperties)param_ServerRequest.read_value(FFSDBProperties.class);
/* 2069:     */         } else {
/* 2070:2234 */           localFFSDBProperties3 = FFSDBPropertiesHelper.read(paramInputStream);
/* 2071:     */         }
/* 2072:2236 */         int j = paramInputStream.read_long();
/* 2073:2237 */         paramBPWAdminBean
/* 2074:2238 */           .deleteFulfillmentInfo(
/* 2075:2239 */           localFFSDBProperties3, 
/* 2076:2240 */           j);
/* 2077:     */       }
/* 2078:     */       catch (Throwable localThrowable3)
/* 2079:     */       {
/* 2080:2245 */         if ((localThrowable3 instanceof FFSException))
/* 2081:     */         {
/* 2082:2247 */           if (UserException.ok(paramOutputStream)) {
/* 2083:2249 */             if (param_ServerRequest.isRMI())
/* 2084:     */             {
/* 2085:2251 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2086:2252 */               param_ServerRequest.write_value((FFSException)localThrowable3, FFSException.class);
/* 2087:     */             }
/* 2088:     */             else
/* 2089:     */             {
/* 2090:2256 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2091:2257 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable3);
/* 2092:     */             }
/* 2093:     */           }
/* 2094:2260 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable3);
/* 2095:     */         }
/* 2096:2262 */         localThrowable3.printStackTrace(Jaguar.log);
/* 2097:2263 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/* 2098:2264 */         return localThrowable3.getClass().getName();
/* 2099:     */       }
/* 2100:     */     case 54: 
/* 2101:     */       try
/* 2102:     */       {
/* 2103:2273 */         int i = paramInputStream.read_long();
/* 2104:2274 */         paramBPWAdminBean
/* 2105:2275 */           .setDebugLevel(
/* 2106:2276 */           i);
/* 2107:     */       }
/* 2108:     */       catch (Throwable localThrowable4)
/* 2109:     */       {
/* 2110:2281 */         if ((localThrowable4 instanceof FFSException))
/* 2111:     */         {
/* 2112:2283 */           if (UserException.ok(paramOutputStream)) {
/* 2113:2285 */             if (param_ServerRequest.isRMI())
/* 2114:     */             {
/* 2115:2287 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2116:2288 */               param_ServerRequest.write_value((FFSException)localThrowable4, FFSException.class);
/* 2117:     */             }
/* 2118:     */             else
/* 2119:     */             {
/* 2120:2292 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2121:2293 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable4);
/* 2122:     */             }
/* 2123:     */           }
/* 2124:2296 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable4);
/* 2125:     */         }
/* 2126:2298 */         localThrowable4.printStackTrace(Jaguar.log);
/* 2127:2299 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/* 2128:2300 */         return localThrowable4.getClass().getName();
/* 2129:     */       }
/* 2130:     */     case 55: 
/* 2131:     */       try
/* 2132:     */       {
/* 2133:2309 */         ProcessingWindowInfo localProcessingWindowInfo1 = (ProcessingWindowInfo)param_ServerRequest.read_value(ProcessingWindowInfo.class);
/* 2134:2310 */         localObject1 = paramBPWAdminBean
/* 2135:2311 */           .addProcessingWindow(
/* 2136:2312 */           localProcessingWindowInfo1);
/* 2137:     */         
/* 2138:2314 */         param_ServerRequest.write_value(localObject1, ProcessingWindowInfo.class);
/* 2139:     */       }
/* 2140:     */       catch (Throwable localThrowable5)
/* 2141:     */       {
/* 2142:2318 */         if ((localThrowable5 instanceof FFSException))
/* 2143:     */         {
/* 2144:2320 */           if (UserException.ok(paramOutputStream)) {
/* 2145:2322 */             if (param_ServerRequest.isRMI())
/* 2146:     */             {
/* 2147:2324 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2148:2325 */               param_ServerRequest.write_value((FFSException)localThrowable5, FFSException.class);
/* 2149:     */             }
/* 2150:     */             else
/* 2151:     */             {
/* 2152:2329 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2153:2330 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable5);
/* 2154:     */             }
/* 2155:     */           }
/* 2156:2333 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable5);
/* 2157:     */         }
/* 2158:2335 */         localThrowable5.printStackTrace(Jaguar.log);
/* 2159:2336 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/* 2160:2337 */         return localThrowable5.getClass().getName();
/* 2161:     */       }
/* 2162:     */     case 56: 
/* 2163:     */       try
/* 2164:     */       {
/* 2165:2346 */         ProcessingWindowInfo localProcessingWindowInfo2 = (ProcessingWindowInfo)param_ServerRequest.read_value(ProcessingWindowInfo.class);
/* 2166:2347 */         localObject1 = paramBPWAdminBean
/* 2167:2348 */           .modProcessingWindow(
/* 2168:2349 */           localProcessingWindowInfo2);
/* 2169:     */         
/* 2170:2351 */         param_ServerRequest.write_value(localObject1, ProcessingWindowInfo.class);
/* 2171:     */       }
/* 2172:     */       catch (Throwable localThrowable6)
/* 2173:     */       {
/* 2174:2355 */         if ((localThrowable6 instanceof FFSException))
/* 2175:     */         {
/* 2176:2357 */           if (UserException.ok(paramOutputStream)) {
/* 2177:2359 */             if (param_ServerRequest.isRMI())
/* 2178:     */             {
/* 2179:2361 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2180:2362 */               param_ServerRequest.write_value((FFSException)localThrowable6, FFSException.class);
/* 2181:     */             }
/* 2182:     */             else
/* 2183:     */             {
/* 2184:2366 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2185:2367 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable6);
/* 2186:     */             }
/* 2187:     */           }
/* 2188:2370 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable6);
/* 2189:     */         }
/* 2190:2372 */         localThrowable6.printStackTrace(Jaguar.log);
/* 2191:2373 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/* 2192:2374 */         return localThrowable6.getClass().getName();
/* 2193:     */       }
/* 2194:     */     case 57: 
/* 2195:     */       try
/* 2196:     */       {
/* 2197:2383 */         ProcessingWindowInfo localProcessingWindowInfo3 = (ProcessingWindowInfo)param_ServerRequest.read_value(ProcessingWindowInfo.class);
/* 2198:2384 */         localObject1 = paramBPWAdminBean
/* 2199:2385 */           .delProcessingWindow(
/* 2200:2386 */           localProcessingWindowInfo3);
/* 2201:     */         
/* 2202:2388 */         param_ServerRequest.write_value(localObject1, ProcessingWindowInfo.class);
/* 2203:     */       }
/* 2204:     */       catch (Throwable localThrowable7)
/* 2205:     */       {
/* 2206:2392 */         if ((localThrowable7 instanceof FFSException))
/* 2207:     */         {
/* 2208:2394 */           if (UserException.ok(paramOutputStream)) {
/* 2209:2396 */             if (param_ServerRequest.isRMI())
/* 2210:     */             {
/* 2211:2398 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2212:2399 */               param_ServerRequest.write_value((FFSException)localThrowable7, FFSException.class);
/* 2213:     */             }
/* 2214:     */             else
/* 2215:     */             {
/* 2216:2403 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2217:2404 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable7);
/* 2218:     */             }
/* 2219:     */           }
/* 2220:2407 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable7);
/* 2221:     */         }
/* 2222:2409 */         localThrowable7.printStackTrace(Jaguar.log);
/* 2223:2410 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/* 2224:2411 */         return localThrowable7.getClass().getName();
/* 2225:     */       }
/* 2226:     */     case 58: 
/* 2227:     */       try
/* 2228:     */       {
/* 2229:2420 */         ProcessingWindowList localProcessingWindowList = (ProcessingWindowList)param_ServerRequest.read_value(ProcessingWindowList.class);
/* 2230:2421 */         localObject1 = paramBPWAdminBean
/* 2231:2422 */           .getProcessingWindows(
/* 2232:2423 */           localProcessingWindowList);
/* 2233:     */         
/* 2234:2425 */         param_ServerRequest.write_value(localObject1, ProcessingWindowList.class);
/* 2235:     */       }
/* 2236:     */       catch (Throwable localThrowable8)
/* 2237:     */       {
/* 2238:2429 */         if ((localThrowable8 instanceof FFSException))
/* 2239:     */         {
/* 2240:2431 */           if (UserException.ok(paramOutputStream)) {
/* 2241:2433 */             if (param_ServerRequest.isRMI())
/* 2242:     */             {
/* 2243:2435 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2244:2436 */               param_ServerRequest.write_value((FFSException)localThrowable8, FFSException.class);
/* 2245:     */             }
/* 2246:     */             else
/* 2247:     */             {
/* 2248:2440 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2249:2441 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable8);
/* 2250:     */             }
/* 2251:     */           }
/* 2252:2444 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable8);
/* 2253:     */         }
/* 2254:2446 */         localThrowable8.printStackTrace(Jaguar.log);
/* 2255:2447 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/* 2256:2448 */         return localThrowable8.getClass().getName();
/* 2257:     */       }
/* 2258:     */     case 59: 
/* 2259:     */       try
/* 2260:     */       {
/* 2261:2457 */         InstructionType localInstructionType1 = (InstructionType)param_ServerRequest.read_value(InstructionType.class);
/* 2262:2458 */         localObject1 = paramBPWAdminBean
/* 2263:2459 */           .getScheduleConfigByCategory(
/* 2264:2460 */           localInstructionType1);
/* 2265:2462 */         if (param_ServerRequest.isRMI()) {
/* 2266:2462 */           param_ServerRequest.write_value(localObject1, new InstructionType[0].getClass());
/* 2267:     */         } else {
/* 2268:2462 */           InstructionTypeSeqHelper.write(paramOutputStream, (InstructionType[])localObject1);
/* 2269:     */         }
/* 2270:     */       }
/* 2271:     */       catch (Throwable localThrowable9)
/* 2272:     */       {
/* 2273:2466 */         if ((localThrowable9 instanceof FFSException))
/* 2274:     */         {
/* 2275:2468 */           if (UserException.ok(paramOutputStream)) {
/* 2276:2470 */             if (param_ServerRequest.isRMI())
/* 2277:     */             {
/* 2278:2472 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2279:2473 */               param_ServerRequest.write_value((FFSException)localThrowable9, FFSException.class);
/* 2280:     */             }
/* 2281:     */             else
/* 2282:     */             {
/* 2283:2477 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2284:2478 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable9);
/* 2285:     */             }
/* 2286:     */           }
/* 2287:2481 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable9);
/* 2288:     */         }
/* 2289:2483 */         localThrowable9.printStackTrace(Jaguar.log);
/* 2290:2484 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/* 2291:2485 */         return localThrowable9.getClass().getName();
/* 2292:     */       }
/* 2293:     */     case 60: 
/* 2294:     */       try
/* 2295:     */       {
/* 2296:2494 */         InstructionType localInstructionType2 = (InstructionType)param_ServerRequest.read_value(InstructionType.class);
/* 2297:2495 */         paramBPWAdminBean
/* 2298:2496 */           .addScheduleConfig(
/* 2299:2497 */           localInstructionType2);
/* 2300:     */       }
/* 2301:     */       catch (Throwable localThrowable10)
/* 2302:     */       {
/* 2303:2502 */         if ((localThrowable10 instanceof FFSException))
/* 2304:     */         {
/* 2305:2504 */           if (UserException.ok(paramOutputStream)) {
/* 2306:2506 */             if (param_ServerRequest.isRMI())
/* 2307:     */             {
/* 2308:2508 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2309:2509 */               param_ServerRequest.write_value((FFSException)localThrowable10, FFSException.class);
/* 2310:     */             }
/* 2311:     */             else
/* 2312:     */             {
/* 2313:2513 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2314:2514 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable10);
/* 2315:     */             }
/* 2316:     */           }
/* 2317:2517 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable10);
/* 2318:     */         }
/* 2319:2519 */         localThrowable10.printStackTrace(Jaguar.log);
/* 2320:2520 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/* 2321:2521 */         return localThrowable10.getClass().getName();
/* 2322:     */       }
/* 2323:     */     case 61: 
/* 2324:     */       try
/* 2325:     */       {
/* 2326:2530 */         InstructionType localInstructionType3 = (InstructionType)param_ServerRequest.read_value(InstructionType.class);
/* 2327:2531 */         paramBPWAdminBean
/* 2328:2532 */           .deleteScheduleConfig(
/* 2329:2533 */           localInstructionType3);
/* 2330:     */       }
/* 2331:     */       catch (Throwable localThrowable11)
/* 2332:     */       {
/* 2333:2538 */         if ((localThrowable11 instanceof FFSException))
/* 2334:     */         {
/* 2335:2540 */           if (UserException.ok(paramOutputStream)) {
/* 2336:2542 */             if (param_ServerRequest.isRMI())
/* 2337:     */             {
/* 2338:2544 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2339:2545 */               param_ServerRequest.write_value((FFSException)localThrowable11, FFSException.class);
/* 2340:     */             }
/* 2341:     */             else
/* 2342:     */             {
/* 2343:2549 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2344:2550 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable11);
/* 2345:     */             }
/* 2346:     */           }
/* 2347:2553 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable11);
/* 2348:     */         }
/* 2349:2555 */         localThrowable11.printStackTrace(Jaguar.log);
/* 2350:2556 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/* 2351:2557 */         return localThrowable11.getClass().getName();
/* 2352:     */       }
/* 2353:     */     case 62: 
/* 2354:     */       try
/* 2355:     */       {
/* 2356:2566 */         CutOffInfo localCutOffInfo1 = (CutOffInfo)param_ServerRequest.read_value(CutOffInfo.class);
/* 2357:2567 */         localObject1 = paramBPWAdminBean
/* 2358:2568 */           .deleteCutOff(
/* 2359:2569 */           localCutOffInfo1);
/* 2360:     */         
/* 2361:2571 */         param_ServerRequest.write_value(localObject1, CutOffInfo.class);
/* 2362:     */       }
/* 2363:     */       catch (Throwable localThrowable12)
/* 2364:     */       {
/* 2365:2575 */         if ((localThrowable12 instanceof FFSException))
/* 2366:     */         {
/* 2367:2577 */           if (UserException.ok(paramOutputStream)) {
/* 2368:2579 */             if (param_ServerRequest.isRMI())
/* 2369:     */             {
/* 2370:2581 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2371:2582 */               param_ServerRequest.write_value((FFSException)localThrowable12, FFSException.class);
/* 2372:     */             }
/* 2373:     */             else
/* 2374:     */             {
/* 2375:2586 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2376:2587 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable12);
/* 2377:     */             }
/* 2378:     */           }
/* 2379:2590 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable12);
/* 2380:     */         }
/* 2381:2592 */         localThrowable12.printStackTrace(Jaguar.log);
/* 2382:2593 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/* 2383:2594 */         return localThrowable12.getClass().getName();
/* 2384:     */       }
/* 2385:     */     case 63: 
/* 2386:     */       try
/* 2387:     */       {
/* 2388:2603 */         CutOffInfo localCutOffInfo2 = (CutOffInfo)param_ServerRequest.read_value(CutOffInfo.class);
/* 2389:2604 */         localObject1 = paramBPWAdminBean
/* 2390:2605 */           .addCutOff(
/* 2391:2606 */           localCutOffInfo2);
/* 2392:     */         
/* 2393:2608 */         param_ServerRequest.write_value(localObject1, CutOffInfo.class);
/* 2394:     */       }
/* 2395:     */       catch (Throwable localThrowable13)
/* 2396:     */       {
/* 2397:2612 */         if ((localThrowable13 instanceof FFSException))
/* 2398:     */         {
/* 2399:2614 */           if (UserException.ok(paramOutputStream)) {
/* 2400:2616 */             if (param_ServerRequest.isRMI())
/* 2401:     */             {
/* 2402:2618 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2403:2619 */               param_ServerRequest.write_value((FFSException)localThrowable13, FFSException.class);
/* 2404:     */             }
/* 2405:     */             else
/* 2406:     */             {
/* 2407:2623 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2408:2624 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable13);
/* 2409:     */             }
/* 2410:     */           }
/* 2411:2627 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable13);
/* 2412:     */         }
/* 2413:2629 */         localThrowable13.printStackTrace(Jaguar.log);
/* 2414:2630 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/* 2415:2631 */         return localThrowable13.getClass().getName();
/* 2416:     */       }
/* 2417:     */     case 64: 
/* 2418:     */       try
/* 2419:     */       {
/* 2420:2640 */         CutOffInfo localCutOffInfo3 = (CutOffInfo)param_ServerRequest.read_value(CutOffInfo.class);
/* 2421:2641 */         localObject1 = paramBPWAdminBean
/* 2422:2642 */           .modCutOff(
/* 2423:2643 */           localCutOffInfo3);
/* 2424:     */         
/* 2425:2645 */         param_ServerRequest.write_value(localObject1, CutOffInfo.class);
/* 2426:     */       }
/* 2427:     */       catch (Throwable localThrowable14)
/* 2428:     */       {
/* 2429:2649 */         if ((localThrowable14 instanceof FFSException))
/* 2430:     */         {
/* 2431:2651 */           if (UserException.ok(paramOutputStream)) {
/* 2432:2653 */             if (param_ServerRequest.isRMI())
/* 2433:     */             {
/* 2434:2655 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2435:2656 */               param_ServerRequest.write_value((FFSException)localThrowable14, FFSException.class);
/* 2436:     */             }
/* 2437:     */             else
/* 2438:     */             {
/* 2439:2660 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2440:2661 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable14);
/* 2441:     */             }
/* 2442:     */           }
/* 2443:2664 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable14);
/* 2444:     */         }
/* 2445:2666 */         localThrowable14.printStackTrace(Jaguar.log);
/* 2446:2667 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/* 2447:2668 */         return localThrowable14.getClass().getName();
/* 2448:     */       }
/* 2449:     */     case 65: 
/* 2450:     */       try
/* 2451:     */       {
/* 2452:2677 */         CutOffInfo localCutOffInfo4 = (CutOffInfo)param_ServerRequest.read_value(CutOffInfo.class);
/* 2453:2678 */         localObject1 = paramBPWAdminBean
/* 2454:2679 */           .getCutOff(
/* 2455:2680 */           localCutOffInfo4);
/* 2456:     */         
/* 2457:2682 */         param_ServerRequest.write_value(localObject1, CutOffInfo.class);
/* 2458:     */       }
/* 2459:     */       catch (Throwable localThrowable15)
/* 2460:     */       {
/* 2461:2686 */         if ((localThrowable15 instanceof FFSException))
/* 2462:     */         {
/* 2463:2688 */           if (UserException.ok(paramOutputStream)) {
/* 2464:2690 */             if (param_ServerRequest.isRMI())
/* 2465:     */             {
/* 2466:2692 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2467:2693 */               param_ServerRequest.write_value((FFSException)localThrowable15, FFSException.class);
/* 2468:     */             }
/* 2469:     */             else
/* 2470:     */             {
/* 2471:2697 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2472:2698 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable15);
/* 2473:     */             }
/* 2474:     */           }
/* 2475:2701 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable15);
/* 2476:     */         }
/* 2477:2703 */         localThrowable15.printStackTrace(Jaguar.log);
/* 2478:2704 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/* 2479:2705 */         return localThrowable15.getClass().getName();
/* 2480:     */       }
/* 2481:     */     case 66: 
/* 2482:     */       try
/* 2483:     */       {
/* 2484:2714 */         CutOffInfoList localCutOffInfoList = (CutOffInfoList)param_ServerRequest.read_value(CutOffInfoList.class);
/* 2485:2715 */         localObject1 = paramBPWAdminBean
/* 2486:2716 */           .getCutOffList(
/* 2487:2717 */           localCutOffInfoList);
/* 2488:     */         
/* 2489:2719 */         param_ServerRequest.write_value(localObject1, CutOffInfoList.class);
/* 2490:     */       }
/* 2491:     */       catch (Throwable localThrowable16)
/* 2492:     */       {
/* 2493:2723 */         if ((localThrowable16 instanceof FFSException))
/* 2494:     */         {
/* 2495:2725 */           if (UserException.ok(paramOutputStream)) {
/* 2496:2727 */             if (param_ServerRequest.isRMI())
/* 2497:     */             {
/* 2498:2729 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2499:2730 */               param_ServerRequest.write_value((FFSException)localThrowable16, FFSException.class);
/* 2500:     */             }
/* 2501:     */             else
/* 2502:     */             {
/* 2503:2734 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2504:2735 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable16);
/* 2505:     */             }
/* 2506:     */           }
/* 2507:2738 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable16);
/* 2508:     */         }
/* 2509:2740 */         localThrowable16.printStackTrace(Jaguar.log);
/* 2510:2741 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/* 2511:2742 */         return localThrowable16.getClass().getName();
/* 2512:     */       }
/* 2513:     */     case 67: 
/* 2514:     */       try
/* 2515:     */       {
/* 2516:2751 */         String str1 = param_ServerRequest.read_string();
/* 2517:     */         
/* 2518:2753 */         localObject1 = param_ServerRequest.read_string();
/* 2519:2754 */         localObject2 = paramBPWAdminBean
/* 2520:2755 */           .getScheduleCategoryInfo(
/* 2521:2756 */           str1, 
/* 2522:2757 */           (String)localObject1);
/* 2523:     */         
/* 2524:2759 */         param_ServerRequest.write_value(localObject2, ScheduleCategoryInfo.class);
/* 2525:     */       }
/* 2526:     */       catch (Throwable localThrowable17)
/* 2527:     */       {
/* 2528:2763 */         if ((localThrowable17 instanceof FFSException))
/* 2529:     */         {
/* 2530:2765 */           if (UserException.ok(paramOutputStream)) {
/* 2531:2767 */             if (param_ServerRequest.isRMI())
/* 2532:     */             {
/* 2533:2769 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2534:2770 */               param_ServerRequest.write_value((FFSException)localThrowable17, FFSException.class);
/* 2535:     */             }
/* 2536:     */             else
/* 2537:     */             {
/* 2538:2774 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2539:2775 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable17);
/* 2540:     */             }
/* 2541:     */           }
/* 2542:2778 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable17);
/* 2543:     */         }
/* 2544:2780 */         localThrowable17.printStackTrace(Jaguar.log);
/* 2545:2781 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/* 2546:2782 */         return localThrowable17.getClass().getName();
/* 2547:     */       }
/* 2548:     */     case 68: 
/* 2549:     */       try
/* 2550:     */       {
/* 2551:2791 */         ScheduleCategoryInfo localScheduleCategoryInfo = (ScheduleCategoryInfo)param_ServerRequest.read_value(ScheduleCategoryInfo.class);
/* 2552:2792 */         localObject1 = paramBPWAdminBean
/* 2553:2793 */           .modScheduleCategoryInfo(
/* 2554:2794 */           localScheduleCategoryInfo);
/* 2555:     */         
/* 2556:2796 */         param_ServerRequest.write_value(localObject1, ScheduleCategoryInfo.class);
/* 2557:     */       }
/* 2558:     */       catch (Throwable localThrowable18)
/* 2559:     */       {
/* 2560:2800 */         if ((localThrowable18 instanceof FFSException))
/* 2561:     */         {
/* 2562:2802 */           if (UserException.ok(paramOutputStream)) {
/* 2563:2804 */             if (param_ServerRequest.isRMI())
/* 2564:     */             {
/* 2565:2806 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2566:2807 */               param_ServerRequest.write_value((FFSException)localThrowable18, FFSException.class);
/* 2567:     */             }
/* 2568:     */             else
/* 2569:     */             {
/* 2570:2811 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2571:2812 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable18);
/* 2572:     */             }
/* 2573:     */           }
/* 2574:2815 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable18);
/* 2575:     */         }
/* 2576:2817 */         localThrowable18.printStackTrace(Jaguar.log);
/* 2577:2818 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/* 2578:2819 */         return localThrowable18.getClass().getName();
/* 2579:     */       }
/* 2580:     */     case 69: 
/* 2581:     */       try
/* 2582:     */       {
/* 2583:2828 */         CutOffActivityInfoList localCutOffActivityInfoList = (CutOffActivityInfoList)param_ServerRequest.read_value(CutOffActivityInfoList.class);
/* 2584:2829 */         localObject1 = paramBPWAdminBean
/* 2585:2830 */           .getCutOffActivityList(
/* 2586:2831 */           localCutOffActivityInfoList);
/* 2587:     */         
/* 2588:2833 */         param_ServerRequest.write_value(localObject1, CutOffActivityInfoList.class);
/* 2589:     */       }
/* 2590:     */       catch (Throwable localThrowable19)
/* 2591:     */       {
/* 2592:2837 */         if ((localThrowable19 instanceof FFSException))
/* 2593:     */         {
/* 2594:2839 */           if (UserException.ok(paramOutputStream)) {
/* 2595:2841 */             if (param_ServerRequest.isRMI())
/* 2596:     */             {
/* 2597:2843 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2598:2844 */               param_ServerRequest.write_value((FFSException)localThrowable19, FFSException.class);
/* 2599:     */             }
/* 2600:     */             else
/* 2601:     */             {
/* 2602:2848 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2603:2849 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable19);
/* 2604:     */             }
/* 2605:     */           }
/* 2606:2852 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable19);
/* 2607:     */         }
/* 2608:2854 */         localThrowable19.printStackTrace(Jaguar.log);
/* 2609:2855 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/* 2610:2856 */         return localThrowable19.getClass().getName();
/* 2611:     */       }
/* 2612:     */     case 70: 
/* 2613:     */       try
/* 2614:     */       {
/* 2615:2865 */         ScheduleActivityList localScheduleActivityList = (ScheduleActivityList)param_ServerRequest.read_value(ScheduleActivityList.class);
/* 2616:2866 */         localObject1 = paramBPWAdminBean
/* 2617:2867 */           .getScheduleActivityList(
/* 2618:2868 */           localScheduleActivityList);
/* 2619:     */         
/* 2620:2870 */         param_ServerRequest.write_value(localObject1, ScheduleActivityList.class);
/* 2621:     */       }
/* 2622:     */       catch (Throwable localThrowable20)
/* 2623:     */       {
/* 2624:2874 */         if ((localThrowable20 instanceof FFSException))
/* 2625:     */         {
/* 2626:2876 */           if (UserException.ok(paramOutputStream)) {
/* 2627:2878 */             if (param_ServerRequest.isRMI())
/* 2628:     */             {
/* 2629:2880 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2630:2881 */               param_ServerRequest.write_value((FFSException)localThrowable20, FFSException.class);
/* 2631:     */             }
/* 2632:     */             else
/* 2633:     */             {
/* 2634:2885 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2635:2886 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable20);
/* 2636:     */             }
/* 2637:     */           }
/* 2638:2889 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable20);
/* 2639:     */         }
/* 2640:2891 */         localThrowable20.printStackTrace(Jaguar.log);
/* 2641:2892 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/* 2642:2893 */         return localThrowable20.getClass().getName();
/* 2643:     */       }
/* 2644:     */     case 71: 
/* 2645:     */       try
/* 2646:     */       {
/* 2647:2902 */         String str2 = param_ServerRequest.read_string();
/* 2648:     */         
/* 2649:2904 */         localObject1 = param_ServerRequest.read_string();
/* 2650:     */         
/* 2651:2906 */         localObject2 = param_ServerRequest.read_string();
/* 2652:     */         
/* 2653:2908 */         str4 = param_ServerRequest.read_string();
/* 2654:2909 */         paramBPWAdminBean
/* 2655:2910 */           .rerunCutOff(
/* 2656:2911 */           str2, 
/* 2657:2912 */           (String)localObject1, 
/* 2658:2913 */           (String)localObject2, 
/* 2659:2914 */           str4);
/* 2660:     */       }
/* 2661:     */       catch (Throwable localThrowable21)
/* 2662:     */       {
/* 2663:2919 */         if ((localThrowable21 instanceof FFSException))
/* 2664:     */         {
/* 2665:2921 */           if (UserException.ok(paramOutputStream)) {
/* 2666:2923 */             if (param_ServerRequest.isRMI())
/* 2667:     */             {
/* 2668:2925 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2669:2926 */               param_ServerRequest.write_value((FFSException)localThrowable21, FFSException.class);
/* 2670:     */             }
/* 2671:     */             else
/* 2672:     */             {
/* 2673:2930 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2674:2931 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable21);
/* 2675:     */             }
/* 2676:     */           }
/* 2677:2934 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable21);
/* 2678:     */         }
/* 2679:2936 */         localThrowable21.printStackTrace(Jaguar.log);
/* 2680:2937 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/* 2681:2938 */         return localThrowable21.getClass().getName();
/* 2682:     */       }
/* 2683:     */     case 72: 
/* 2684:     */       try
/* 2685:     */       {
/* 2686:2947 */         String str3 = param_ServerRequest.read_string();
/* 2687:     */         
/* 2688:2949 */         localObject1 = param_ServerRequest.read_string();
/* 2689:     */         
/* 2690:2951 */         localObject2 = param_ServerRequest.read_string();
/* 2691:2952 */         str4 = paramBPWAdminBean
/* 2692:2953 */           .getGeneratedFileName(
/* 2693:2954 */           str3, 
/* 2694:2955 */           (String)localObject1, 
/* 2695:2956 */           (String)localObject2);
/* 2696:     */         
/* 2697:2958 */         param_ServerRequest.write_string(str4);
/* 2698:     */       }
/* 2699:     */       catch (Throwable localThrowable22)
/* 2700:     */       {
/* 2701:2962 */         if ((localThrowable22 instanceof FFSException))
/* 2702:     */         {
/* 2703:2964 */           if (UserException.ok(paramOutputStream)) {
/* 2704:2966 */             if (param_ServerRequest.isRMI())
/* 2705:     */             {
/* 2706:2968 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2707:2969 */               param_ServerRequest.write_value((FFSException)localThrowable22, FFSException.class);
/* 2708:     */             }
/* 2709:     */             else
/* 2710:     */             {
/* 2711:2973 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2712:2974 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable22);
/* 2713:     */             }
/* 2714:     */           }
/* 2715:2977 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable22);
/* 2716:     */         }
/* 2717:2979 */         localThrowable22.printStackTrace(Jaguar.log);
/* 2718:2980 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/* 2719:2981 */         return localThrowable22.getClass().getName();
/* 2720:     */       }
/* 2721:     */     case 73: 
/* 2722:     */       try
/* 2723:     */       {
/* 2724:2990 */         SmartCalendarFile localSmartCalendarFile1 = (SmartCalendarFile)param_ServerRequest.read_value(SmartCalendarFile.class);
/* 2725:2991 */         localObject1 = paramBPWAdminBean
/* 2726:2992 */           .importCalendar(
/* 2727:2993 */           localSmartCalendarFile1);
/* 2728:     */         
/* 2729:2995 */         param_ServerRequest.write_value(localObject1, SmartCalendarFile.class);
/* 2730:     */       }
/* 2731:     */       catch (Throwable localThrowable23)
/* 2732:     */       {
/* 2733:2999 */         if ((localThrowable23 instanceof FFSException))
/* 2734:     */         {
/* 2735:3001 */           if (UserException.ok(paramOutputStream)) {
/* 2736:3003 */             if (param_ServerRequest.isRMI())
/* 2737:     */             {
/* 2738:3005 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2739:3006 */               param_ServerRequest.write_value((FFSException)localThrowable23, FFSException.class);
/* 2740:     */             }
/* 2741:     */             else
/* 2742:     */             {
/* 2743:3010 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2744:3011 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable23);
/* 2745:     */             }
/* 2746:     */           }
/* 2747:3014 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable23);
/* 2748:     */         }
/* 2749:3016 */         localThrowable23.printStackTrace(Jaguar.log);
/* 2750:3017 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/* 2751:3018 */         return localThrowable23.getClass().getName();
/* 2752:     */       }
/* 2753:     */     case 74: 
/* 2754:     */       try
/* 2755:     */       {
/* 2756:3027 */         SmartCalendarFile localSmartCalendarFile2 = (SmartCalendarFile)param_ServerRequest.read_value(SmartCalendarFile.class);
/* 2757:3028 */         localObject1 = paramBPWAdminBean
/* 2758:3029 */           .exportCalendar(
/* 2759:3030 */           localSmartCalendarFile2);
/* 2760:     */         
/* 2761:3032 */         param_ServerRequest.write_value(localObject1, SmartCalendarFile.class);
/* 2762:     */       }
/* 2763:     */       catch (Throwable localThrowable24)
/* 2764:     */       {
/* 2765:3036 */         if ((localThrowable24 instanceof FFSException))
/* 2766:     */         {
/* 2767:3038 */           if (UserException.ok(paramOutputStream)) {
/* 2768:3040 */             if (param_ServerRequest.isRMI())
/* 2769:     */             {
/* 2770:3042 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2771:3043 */               param_ServerRequest.write_value((FFSException)localThrowable24, FFSException.class);
/* 2772:     */             }
/* 2773:     */             else
/* 2774:     */             {
/* 2775:3047 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2776:3048 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable24);
/* 2777:     */             }
/* 2778:     */           }
/* 2779:3051 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable24);
/* 2780:     */         }
/* 2781:3053 */         localThrowable24.printStackTrace(Jaguar.log);
/* 2782:3054 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/* 2783:3055 */         return localThrowable24.getClass().getName();
/* 2784:     */       }
/* 2785:     */     case 75: 
/* 2786:     */       try
/* 2787:     */       {
/* 2788:3064 */         PayeeInfo localPayeeInfo1 = (PayeeInfo)param_ServerRequest.read_value(PayeeInfo.class);
/* 2789:3065 */         localObject1 = paramBPWAdminBean
/* 2790:3066 */           .addGlobalPayee(
/* 2791:3067 */           localPayeeInfo1);
/* 2792:     */         
/* 2793:3069 */         param_ServerRequest.write_value(localObject1, PayeeInfo.class);
/* 2794:     */       }
/* 2795:     */       catch (Throwable localThrowable25)
/* 2796:     */       {
/* 2797:3073 */         if ((localThrowable25 instanceof FFSException))
/* 2798:     */         {
/* 2799:3075 */           if (UserException.ok(paramOutputStream)) {
/* 2800:3077 */             if (param_ServerRequest.isRMI())
/* 2801:     */             {
/* 2802:3079 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2803:3080 */               param_ServerRequest.write_value((FFSException)localThrowable25, FFSException.class);
/* 2804:     */             }
/* 2805:     */             else
/* 2806:     */             {
/* 2807:3084 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2808:3085 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable25);
/* 2809:     */             }
/* 2810:     */           }
/* 2811:3088 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable25);
/* 2812:     */         }
/* 2813:3090 */         localThrowable25.printStackTrace(Jaguar.log);
/* 2814:3091 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/* 2815:3092 */         return localThrowable25.getClass().getName();
/* 2816:     */       }
/* 2817:     */     case 76: 
/* 2818:     */       try
/* 2819:     */       {
/* 2820:3101 */         PayeeInfo localPayeeInfo2 = (PayeeInfo)param_ServerRequest.read_value(PayeeInfo.class);
/* 2821:3102 */         localObject1 = paramBPWAdminBean
/* 2822:3103 */           .deleteGlobalPayee(
/* 2823:3104 */           localPayeeInfo2);
/* 2824:     */         
/* 2825:3106 */         param_ServerRequest.write_value(localObject1, PayeeInfo.class);
/* 2826:     */       }
/* 2827:     */       catch (Throwable localThrowable26)
/* 2828:     */       {
/* 2829:3110 */         if ((localThrowable26 instanceof FFSException))
/* 2830:     */         {
/* 2831:3112 */           if (UserException.ok(paramOutputStream)) {
/* 2832:3114 */             if (param_ServerRequest.isRMI())
/* 2833:     */             {
/* 2834:3116 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0");
/* 2835:3117 */               param_ServerRequest.write_value((FFSException)localThrowable26, FFSException.class);
/* 2836:     */             }
/* 2837:     */             else
/* 2838:     */             {
/* 2839:3121 */               paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2840:3122 */               FFSExceptionHelper.write(paramOutputStream, (FFSException)localThrowable26);
/* 2841:     */             }
/* 2842:     */           }
/* 2843:3125 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable26);
/* 2844:     */         }
/* 2845:3127 */         localThrowable26.printStackTrace(Jaguar.log);
/* 2846:3128 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/* 2847:3129 */         return localThrowable26.getClass().getName();
/* 2848:     */       }
/* 2849:     */     }
/* 2850:3134 */     return null;
/* 2851:     */   }
/* 2852:     */   
/* 2853:     */   public static String localInvoke(Object paramObject, String paramString, InputStream paramInputStream, OutputStream paramOutputStream, int paramInt)
/* 2854:     */   {
/* 2855:3144 */     _ServerRequest local_ServerRequest = new _ServerRequest(paramInputStream, paramOutputStream, (paramInt & 0x1) != 0);
/* 2856:3145 */     BPWAdminBean localBPWAdminBean = (BPWAdminBean)paramObject;
/* 2857:3146 */     Integer localInteger = null;
/* 2858:3147 */     boolean bool1 = false;
/* 2859:3148 */     if (!paramString.startsWith("#"))
/* 2860:     */     {
/* 2861:3150 */       localInteger = (Integer)_localMethods2.get(paramString);
/* 2862:3151 */       if (localInteger != null) {
/* 2863:3152 */         bool1 = true;
/* 2864:     */       }
/* 2865:     */     }
/* 2866:     */     else
/* 2867:     */     {
/* 2868:3156 */       localInteger = (Integer)_localMethods.get(paramString);
/* 2869:     */     }
/* 2870:3158 */     if (localInteger == null) {
/* 2871:3160 */       return remoteInvoke(paramObject, paramString, paramInputStream, paramOutputStream, paramInt);
/* 2872:     */     }
/* 2873:3162 */     LocalFrame localLocalFrame = LocalStack.getCurrent().top();
/* 2874:     */     Object localObject1;
/* 2875:     */     Object localObject3;
/* 2876:     */     Object localObject6;
/* 2877:     */     Object localObject7;
/* 2878:     */     Object localObject4;
/* 2879:     */     Object localObject2;
/* 2880:     */     Object localObject5;
/* 2881:3163 */     switch (localInteger.intValue())
/* 2882:     */     {
/* 2883:     */     case 0: 
/* 2884:     */       try
/* 2885:     */       {
/* 2886:3170 */         localBPWAdminBean.ejbCreate();
/* 2887:     */       }
/* 2888:     */       catch (Throwable localThrowable1)
/* 2889:     */       {
/* 2890:3175 */         localThrowable1.printStackTrace(Jaguar.log);
/* 2891:3176 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/* 2892:3177 */         return localThrowable1.getClass().getName();
/* 2893:     */       }
/* 2894:     */     case 1: 
/* 2895:     */       try
/* 2896:     */       {
/* 2897:3186 */         localBPWAdminBean.ejbRemove();
/* 2898:     */       }
/* 2899:     */       catch (Throwable localThrowable2)
/* 2900:     */       {
/* 2901:3191 */         localThrowable2.printStackTrace(Jaguar.log);
/* 2902:3192 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/* 2903:3193 */         return localThrowable2.getClass().getName();
/* 2904:     */       }
/* 2905:     */     case 2: 
/* 2906:     */       try
/* 2907:     */       {
/* 2908:     */         PropertyConfig localPropertyConfig1;
/* 2909:3202 */         if (!bool1)
/* 2910:     */         {
/* 2911:3204 */           localPropertyConfig1 = (PropertyConfig)localLocalFrame.get(0);
/* 2912:     */         }
/* 2913:     */         else
/* 2914:     */         {
/* 2915:3208 */           localObject1 = localLocalFrame.get(0);
/* 2916:3209 */           localPropertyConfig1 = (PropertyConfig)ObjectVal.clone(localObject1);
/* 2917:     */         }
/* 2918:3212 */         if (!bool1)
/* 2919:     */         {
/* 2920:3214 */           localObject1 = (InstructionType[])localLocalFrame.get(1);
/* 2921:     */         }
/* 2922:     */         else
/* 2923:     */         {
/* 2924:3218 */           localObject3 = localLocalFrame.get(1);
/* 2925:3219 */           localObject1 = (InstructionType[])ObjectVal.clone(localObject3);
/* 2926:     */         }
/* 2927:3222 */         localBPWAdminBean.start(
/* 2928:3223 */           localPropertyConfig1, 
/* 2929:3224 */           (InstructionType[])localObject1);
/* 2930:     */       }
/* 2931:     */       catch (Throwable localThrowable3)
/* 2932:     */       {
/* 2933:3229 */         if ((localThrowable3 instanceof FFSException))
/* 2934:     */         {
/* 2935:3231 */           localLocalFrame.setException(localThrowable3);
/* 2936:3232 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2937:3233 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable3);
/* 2938:     */         }
/* 2939:3235 */         localThrowable3.printStackTrace(Jaguar.log);
/* 2940:3236 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/* 2941:3237 */         return localThrowable3.getClass().getName();
/* 2942:     */       }
/* 2943:     */     case 3: 
/* 2944:     */       try
/* 2945:     */       {
/* 2946:     */         FFSProperties localFFSProperties1;
/* 2947:3246 */         if (!bool1)
/* 2948:     */         {
/* 2949:3248 */           localFFSProperties1 = (FFSProperties)localLocalFrame.get(0);
/* 2950:     */         }
/* 2951:     */         else
/* 2952:     */         {
/* 2953:3252 */           localObject1 = localLocalFrame.get(0);
/* 2954:3253 */           localFFSProperties1 = (FFSProperties)ObjectVal.clone(localObject1);
/* 2955:     */         }
/* 2956:3256 */         if (!bool1)
/* 2957:     */         {
/* 2958:3258 */           localObject1 = (PropertyConfig)localLocalFrame.get(1);
/* 2959:     */         }
/* 2960:     */         else
/* 2961:     */         {
/* 2962:3262 */           localObject3 = localLocalFrame.get(1);
/* 2963:3263 */           localObject1 = (PropertyConfig)ObjectVal.clone(localObject3);
/* 2964:     */         }
/* 2965:3266 */         if (!bool1)
/* 2966:     */         {
/* 2967:3268 */           localObject3 = (InstructionType[])localLocalFrame.get(2);
/* 2968:     */         }
/* 2969:     */         else
/* 2970:     */         {
/* 2971:3272 */           localObject6 = localLocalFrame.get(2);
/* 2972:3273 */           localObject3 = (InstructionType[])ObjectVal.clone(localObject6);
/* 2973:     */         }
/* 2974:3276 */         localBPWAdminBean.start(
/* 2975:3277 */           localFFSProperties1, 
/* 2976:3278 */           (PropertyConfig)localObject1, 
/* 2977:3279 */           (InstructionType[])localObject3);
/* 2978:     */       }
/* 2979:     */       catch (Throwable localThrowable4)
/* 2980:     */       {
/* 2981:3284 */         if ((localThrowable4 instanceof FFSException))
/* 2982:     */         {
/* 2983:3286 */           localLocalFrame.setException(localThrowable4);
/* 2984:3287 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 2985:3288 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable4);
/* 2986:     */         }
/* 2987:3290 */         localThrowable4.printStackTrace(Jaguar.log);
/* 2988:3291 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/* 2989:3292 */         return localThrowable4.getClass().getName();
/* 2990:     */       }
/* 2991:     */     case 4: 
/* 2992:     */       try
/* 2993:     */       {
/* 2994:3301 */         localBPWAdminBean.stop();
/* 2995:     */       }
/* 2996:     */       catch (Throwable localThrowable5)
/* 2997:     */       {
/* 2998:3306 */         if ((localThrowable5 instanceof FFSException))
/* 2999:     */         {
/* 3000:3308 */           localLocalFrame.setException(localThrowable5);
/* 3001:3309 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3002:3310 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable5);
/* 3003:     */         }
/* 3004:3312 */         localThrowable5.printStackTrace(Jaguar.log);
/* 3005:3313 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/* 3006:3314 */         return localThrowable5.getClass().getName();
/* 3007:     */       }
/* 3008:     */     case 5: 
/* 3009:     */       try
/* 3010:     */       {
/* 3011:3323 */         String str1 = (String)localLocalFrame.get(0);
/* 3012:3325 */         if (!bool1)
/* 3013:     */         {
/* 3014:3327 */           localObject1 = (ArrayList)localLocalFrame.get(1);
/* 3015:     */         }
/* 3016:     */         else
/* 3017:     */         {
/* 3018:3331 */           localObject3 = localLocalFrame.get(1);
/* 3019:3332 */           localObject1 = (ArrayList)ObjectVal.clone(localObject3);
/* 3020:     */         }
/* 3021:3335 */         if (!bool1)
/* 3022:     */         {
/* 3023:3337 */           localObject3 = (ArrayList)localLocalFrame.get(2);
/* 3024:     */         }
/* 3025:     */         else
/* 3026:     */         {
/* 3027:3341 */           localObject6 = localLocalFrame.get(2);
/* 3028:3342 */           localObject3 = (ArrayList)ObjectVal.clone(localObject6);
/* 3029:     */         }
/* 3030:3345 */         if (!bool1)
/* 3031:     */         {
/* 3032:3347 */           localObject6 = (HashMap)localLocalFrame.get(3);
/* 3033:     */         }
/* 3034:     */         else
/* 3035:     */         {
/* 3036:3351 */           localObject7 = localLocalFrame.get(3);
/* 3037:3352 */           localObject6 = (HashMap)ObjectVal.clone(localObject7);
/* 3038:     */         }
/* 3039:3355 */         localBPWAdminBean.cleanup(
/* 3040:3356 */           str1, 
/* 3041:3357 */           (ArrayList)localObject1, 
/* 3042:3358 */           (ArrayList)localObject3, 
/* 3043:3359 */           (HashMap)localObject6);
/* 3044:     */       }
/* 3045:     */       catch (Throwable localThrowable6)
/* 3046:     */       {
/* 3047:3364 */         localThrowable6.printStackTrace(Jaguar.log);
/* 3048:3365 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/* 3049:3366 */         return localThrowable6.getClass().getName();
/* 3050:     */       }
/* 3051:     */     case 6: 
/* 3052:     */       try
/* 3053:     */       {
/* 3054:     */         ArrayList localArrayList;
/* 3055:3375 */         if (!bool1)
/* 3056:     */         {
/* 3057:3377 */           localArrayList = (ArrayList)localLocalFrame.get(0);
/* 3058:     */         }
/* 3059:     */         else
/* 3060:     */         {
/* 3061:3381 */           localObject1 = localLocalFrame.get(0);
/* 3062:3382 */           localArrayList = (ArrayList)ObjectVal.clone(localObject1);
/* 3063:     */         }
/* 3064:3385 */         if (!bool1)
/* 3065:     */         {
/* 3066:3387 */           localObject1 = (ArrayList)localLocalFrame.get(1);
/* 3067:     */         }
/* 3068:     */         else
/* 3069:     */         {
/* 3070:3391 */           localObject3 = localLocalFrame.get(1);
/* 3071:3392 */           localObject1 = (ArrayList)ObjectVal.clone(localObject3);
/* 3072:     */         }
/* 3073:3395 */         if (!bool1)
/* 3074:     */         {
/* 3075:3397 */           localObject3 = (ArrayList)localLocalFrame.get(2);
/* 3076:     */         }
/* 3077:     */         else
/* 3078:     */         {
/* 3079:3401 */           localObject6 = localLocalFrame.get(2);
/* 3080:3402 */           localObject3 = (ArrayList)ObjectVal.clone(localObject6);
/* 3081:     */         }
/* 3082:3405 */         if (!bool1)
/* 3083:     */         {
/* 3084:3407 */           localObject6 = (HashMap)localLocalFrame.get(3);
/* 3085:     */         }
/* 3086:     */         else
/* 3087:     */         {
/* 3088:3411 */           localObject7 = localLocalFrame.get(3);
/* 3089:3412 */           localObject6 = (HashMap)ObjectVal.clone(localObject7);
/* 3090:     */         }
/* 3091:3415 */         localBPWAdminBean.cleanup(
/* 3092:3416 */           localArrayList, 
/* 3093:3417 */           (ArrayList)localObject1, 
/* 3094:3418 */           (ArrayList)localObject3, 
/* 3095:3419 */           (HashMap)localObject6);
/* 3096:     */       }
/* 3097:     */       catch (Throwable localThrowable7)
/* 3098:     */       {
/* 3099:3424 */         localThrowable7.printStackTrace(Jaguar.log);
/* 3100:3425 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/* 3101:3426 */         return localThrowable7.getClass().getName();
/* 3102:     */       }
/* 3103:     */     case 7: 
/* 3104:     */       try
/* 3105:     */       {
/* 3106:3435 */         String str2 = (String)localLocalFrame.get(0);
/* 3107:     */         
/* 3108:3437 */         localObject1 = (String)localLocalFrame.get(1);
/* 3109:     */         
/* 3110:3439 */         int k = ((Integer)localLocalFrame.get(2)).intValue();
/* 3111:3441 */         if (!bool1)
/* 3112:     */         {
/* 3113:3443 */           localObject6 = (HashMap)localLocalFrame.get(3);
/* 3114:     */         }
/* 3115:     */         else
/* 3116:     */         {
/* 3117:3447 */           localObject7 = localLocalFrame.get(3);
/* 3118:3448 */           localObject6 = (HashMap)ObjectVal.clone(localObject7);
/* 3119:     */         }
/* 3120:3451 */         localBPWAdminBean.cleanup(
/* 3121:3452 */           str2, 
/* 3122:3453 */           (String)localObject1, 
/* 3123:3454 */           k, 
/* 3124:3455 */           (HashMap)localObject6);
/* 3125:     */       }
/* 3126:     */       catch (Throwable localThrowable8)
/* 3127:     */       {
/* 3128:3460 */         localThrowable8.printStackTrace(Jaguar.log);
/* 3129:3461 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/* 3130:3462 */         return localThrowable8.getClass().getName();
/* 3131:     */       }
/* 3132:     */     case 8: 
/* 3133:     */       try
/* 3134:     */       {
/* 3135:     */         FFSProperties localFFSProperties2;
/* 3136:3471 */         if (!bool1)
/* 3137:     */         {
/* 3138:3473 */           localFFSProperties2 = (FFSProperties)localLocalFrame.get(0);
/* 3139:     */         }
/* 3140:     */         else
/* 3141:     */         {
/* 3142:3477 */           localObject1 = localLocalFrame.get(0);
/* 3143:3478 */           localFFSProperties2 = (FFSProperties)ObjectVal.clone(localObject1);
/* 3144:     */         }
/* 3145:3481 */         if (!bool1)
/* 3146:     */         {
/* 3147:3483 */           localObject1 = (PropertyConfig)localLocalFrame.get(1);
/* 3148:     */         }
/* 3149:     */         else
/* 3150:     */         {
/* 3151:3487 */           localObject4 = localLocalFrame.get(1);
/* 3152:3488 */           localObject1 = (PropertyConfig)ObjectVal.clone(localObject4);
/* 3153:     */         }
/* 3154:3491 */         if (!bool1)
/* 3155:     */         {
/* 3156:3493 */           localObject4 = (InstructionType[])localLocalFrame.get(2);
/* 3157:     */         }
/* 3158:     */         else
/* 3159:     */         {
/* 3160:3497 */           localObject6 = localLocalFrame.get(2);
/* 3161:3498 */           localObject4 = (InstructionType[])ObjectVal.clone(localObject6);
/* 3162:     */         }
/* 3163:3501 */         localBPWAdminBean.refresh(
/* 3164:3502 */           localFFSProperties2, 
/* 3165:3503 */           (PropertyConfig)localObject1, 
/* 3166:3504 */           (InstructionType[])localObject4);
/* 3167:     */       }
/* 3168:     */       catch (Throwable localThrowable9)
/* 3169:     */       {
/* 3170:3509 */         if ((localThrowable9 instanceof FFSException))
/* 3171:     */         {
/* 3172:3511 */           localLocalFrame.setException(localThrowable9);
/* 3173:3512 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3174:3513 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable9);
/* 3175:     */         }
/* 3176:3515 */         localThrowable9.printStackTrace(Jaguar.log);
/* 3177:3516 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/* 3178:3517 */         return localThrowable9.getClass().getName();
/* 3179:     */       }
/* 3180:     */     case 9: 
/* 3181:     */       try
/* 3182:     */       {
/* 3183:     */         PropertyConfig localPropertyConfig2;
/* 3184:3526 */         if (!bool1)
/* 3185:     */         {
/* 3186:3528 */           localPropertyConfig2 = (PropertyConfig)localLocalFrame.get(0);
/* 3187:     */         }
/* 3188:     */         else
/* 3189:     */         {
/* 3190:3532 */           localObject1 = localLocalFrame.get(0);
/* 3191:3533 */           localPropertyConfig2 = (PropertyConfig)ObjectVal.clone(localObject1);
/* 3192:     */         }
/* 3193:3536 */         if (!bool1)
/* 3194:     */         {
/* 3195:3538 */           localObject1 = (InstructionType[])localLocalFrame.get(1);
/* 3196:     */         }
/* 3197:     */         else
/* 3198:     */         {
/* 3199:3542 */           localObject4 = localLocalFrame.get(1);
/* 3200:3543 */           localObject1 = (InstructionType[])ObjectVal.clone(localObject4);
/* 3201:     */         }
/* 3202:3546 */         localBPWAdminBean.refresh(
/* 3203:3547 */           localPropertyConfig2, 
/* 3204:3548 */           (InstructionType[])localObject1);
/* 3205:     */       }
/* 3206:     */       catch (Throwable localThrowable10)
/* 3207:     */       {
/* 3208:3553 */         if ((localThrowable10 instanceof FFSException))
/* 3209:     */         {
/* 3210:3555 */           localLocalFrame.setException(localThrowable10);
/* 3211:3556 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3212:3557 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable10);
/* 3213:     */         }
/* 3214:3559 */         localThrowable10.printStackTrace(Jaguar.log);
/* 3215:3560 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/* 3216:3561 */         return localThrowable10.getClass().getName();
/* 3217:     */       }
/* 3218:     */     case 10: 
/* 3219:     */       try
/* 3220:     */       {
/* 3221:3569 */         boolean bool2 = localBPWAdminBean
/* 3222:3570 */           .ping();
/* 3223:     */         
/* 3224:3572 */         localLocalFrame.setResult(bool2);
/* 3225:     */       }
/* 3226:     */       catch (Throwable localThrowable11)
/* 3227:     */       {
/* 3228:3576 */         if ((localThrowable11 instanceof FFSException))
/* 3229:     */         {
/* 3230:3578 */           localLocalFrame.setException(localThrowable11);
/* 3231:3579 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3232:3580 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable11);
/* 3233:     */         }
/* 3234:3582 */         localThrowable11.printStackTrace(Jaguar.log);
/* 3235:3583 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/* 3236:3584 */         return localThrowable11.getClass().getName();
/* 3237:     */       }
/* 3238:     */     case 11: 
/* 3239:     */       try
/* 3240:     */       {
/* 3241:3592 */         BPWStatistics localBPWStatistics = localBPWAdminBean
/* 3242:3593 */           .getStatistics();
/* 3243:     */         
/* 3244:3595 */         localLocalFrame.setResult(localBPWStatistics);
/* 3245:     */       }
/* 3246:     */       catch (Throwable localThrowable12)
/* 3247:     */       {
/* 3248:3599 */         if ((localThrowable12 instanceof FFSException))
/* 3249:     */         {
/* 3250:3601 */           localLocalFrame.setException(localThrowable12);
/* 3251:3602 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3252:3603 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable12);
/* 3253:     */         }
/* 3254:3605 */         localThrowable12.printStackTrace(Jaguar.log);
/* 3255:3606 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/* 3256:3607 */         return localThrowable12.getClass().getName();
/* 3257:     */       }
/* 3258:     */     case 12: 
/* 3259:     */       try
/* 3260:     */       {
/* 3261:3616 */         localBPWAdminBean.refreshSmartCalendar();
/* 3262:     */       }
/* 3263:     */       catch (Throwable localThrowable13)
/* 3264:     */       {
/* 3265:3621 */         if ((localThrowable13 instanceof FFSException))
/* 3266:     */         {
/* 3267:3623 */           localLocalFrame.setException(localThrowable13);
/* 3268:3624 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3269:3625 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable13);
/* 3270:     */         }
/* 3271:3627 */         localThrowable13.printStackTrace(Jaguar.log);
/* 3272:3628 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/* 3273:3629 */         return localThrowable13.getClass().getName();
/* 3274:     */       }
/* 3275:     */     case 13: 
/* 3276:     */       try
/* 3277:     */       {
/* 3278:3637 */         long l1 = localBPWAdminBean
/* 3279:3638 */           .getFreeMem();
/* 3280:     */         
/* 3281:3640 */         localLocalFrame.setResult(l1);
/* 3282:     */       }
/* 3283:     */       catch (Throwable localThrowable14)
/* 3284:     */       {
/* 3285:3644 */         if ((localThrowable14 instanceof FFSException))
/* 3286:     */         {
/* 3287:3646 */           localLocalFrame.setException(localThrowable14);
/* 3288:3647 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3289:3648 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable14);
/* 3290:     */         }
/* 3291:3650 */         localThrowable14.printStackTrace(Jaguar.log);
/* 3292:3651 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/* 3293:3652 */         return localThrowable14.getClass().getName();
/* 3294:     */       }
/* 3295:     */     case 14: 
/* 3296:     */       try
/* 3297:     */       {
/* 3298:3660 */         long l2 = localBPWAdminBean
/* 3299:3661 */           .getTotalMem();
/* 3300:     */         
/* 3301:3663 */         localLocalFrame.setResult(l2);
/* 3302:     */       }
/* 3303:     */       catch (Throwable localThrowable15)
/* 3304:     */       {
/* 3305:3667 */         if ((localThrowable15 instanceof FFSException))
/* 3306:     */         {
/* 3307:3669 */           localLocalFrame.setException(localThrowable15);
/* 3308:3670 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3309:3671 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable15);
/* 3310:     */         }
/* 3311:3673 */         localThrowable15.printStackTrace(Jaguar.log);
/* 3312:3674 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/* 3313:3675 */         return localThrowable15.getClass().getName();
/* 3314:     */       }
/* 3315:     */     case 15: 
/* 3316:     */       try
/* 3317:     */       {
/* 3318:3683 */         double d = localBPWAdminBean
/* 3319:3684 */           .getHeapUsage();
/* 3320:     */         
/* 3321:3686 */         localLocalFrame.setResult(d);
/* 3322:     */       }
/* 3323:     */       catch (Throwable localThrowable16)
/* 3324:     */       {
/* 3325:3690 */         if ((localThrowable16 instanceof FFSException))
/* 3326:     */         {
/* 3327:3692 */           localLocalFrame.setException(localThrowable16);
/* 3328:3693 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3329:3694 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable16);
/* 3330:     */         }
/* 3331:3696 */         localThrowable16.printStackTrace(Jaguar.log);
/* 3332:3697 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/* 3333:3698 */         return localThrowable16.getClass().getName();
/* 3334:     */       }
/* 3335:     */     case 16: 
/* 3336:     */       try
/* 3337:     */       {
/* 3338:3707 */         String str3 = (String)localLocalFrame.get(0);
/* 3339:     */         
/* 3340:3709 */         localObject1 = (String)localLocalFrame.get(1);
/* 3341:     */         
/* 3342:3711 */         localObject4 = (String)localLocalFrame.get(2);
/* 3343:3712 */         localBPWAdminBean
/* 3344:3713 */           .resubmitEvent(
/* 3345:3714 */           str3, 
/* 3346:3715 */           (String)localObject1, 
/* 3347:3716 */           (String)localObject4);
/* 3348:     */       }
/* 3349:     */       catch (Throwable localThrowable17)
/* 3350:     */       {
/* 3351:3721 */         if ((localThrowable17 instanceof FFSException))
/* 3352:     */         {
/* 3353:3723 */           localLocalFrame.setException(localThrowable17);
/* 3354:3724 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3355:3725 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable17);
/* 3356:     */         }
/* 3357:3727 */         localThrowable17.printStackTrace(Jaguar.log);
/* 3358:3728 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/* 3359:3729 */         return localThrowable17.getClass().getName();
/* 3360:     */       }
/* 3361:     */     case 17: 
/* 3362:     */       try
/* 3363:     */       {
/* 3364:3738 */         String str4 = (String)localLocalFrame.get(0);
/* 3365:     */         
/* 3366:3740 */         localObject1 = (String)localLocalFrame.get(1);
/* 3367:3741 */         localBPWAdminBean
/* 3368:3742 */           .resubmitEvent(
/* 3369:3743 */           str4, 
/* 3370:3744 */           (String)localObject1);
/* 3371:     */       }
/* 3372:     */       catch (Throwable localThrowable18)
/* 3373:     */       {
/* 3374:3749 */         if ((localThrowable18 instanceof FFSException))
/* 3375:     */         {
/* 3376:3751 */           localLocalFrame.setException(localThrowable18);
/* 3377:3752 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3378:3753 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable18);
/* 3379:     */         }
/* 3380:3755 */         localThrowable18.printStackTrace(Jaguar.log);
/* 3381:3756 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/* 3382:3757 */         return localThrowable18.getClass().getName();
/* 3383:     */       }
/* 3384:     */     case 18: 
/* 3385:     */       try
/* 3386:     */       {
/* 3387:3766 */         String str5 = (String)localLocalFrame.get(0);
/* 3388:     */         
/* 3389:3768 */         localObject1 = (String)localLocalFrame.get(1);
/* 3390:     */         
/* 3391:3770 */         localObject4 = (String)localLocalFrame.get(2);
/* 3392:     */         
/* 3393:3772 */         localObject6 = (String)localLocalFrame.get(3);
/* 3394:3773 */         localBPWAdminBean
/* 3395:3774 */           .resubmitEvent(
/* 3396:3775 */           str5, 
/* 3397:3776 */           (String)localObject1, 
/* 3398:3777 */           (String)localObject4, 
/* 3399:3778 */           (String)localObject6);
/* 3400:     */       }
/* 3401:     */       catch (Throwable localThrowable19)
/* 3402:     */       {
/* 3403:3783 */         if ((localThrowable19 instanceof FFSException))
/* 3404:     */         {
/* 3405:3785 */           localLocalFrame.setException(localThrowable19);
/* 3406:3786 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3407:3787 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable19);
/* 3408:     */         }
/* 3409:3789 */         localThrowable19.printStackTrace(Jaguar.log);
/* 3410:3790 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/* 3411:3791 */         return localThrowable19.getClass().getName();
/* 3412:     */       }
/* 3413:     */     case 19: 
/* 3414:     */       try
/* 3415:     */       {
/* 3416:3799 */         String str6 = localBPWAdminBean
/* 3417:3800 */           .startScheduler();
/* 3418:     */         
/* 3419:3802 */         localLocalFrame.setResult(str6);
/* 3420:     */       }
/* 3421:     */       catch (Throwable localThrowable20)
/* 3422:     */       {
/* 3423:3806 */         if ((localThrowable20 instanceof FFSException))
/* 3424:     */         {
/* 3425:3808 */           localLocalFrame.setException(localThrowable20);
/* 3426:3809 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3427:3810 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable20);
/* 3428:     */         }
/* 3429:3812 */         localThrowable20.printStackTrace(Jaguar.log);
/* 3430:3813 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/* 3431:3814 */         return localThrowable20.getClass().getName();
/* 3432:     */       }
/* 3433:     */     case 20: 
/* 3434:     */       try
/* 3435:     */       {
/* 3436:3822 */         String str7 = localBPWAdminBean
/* 3437:3823 */           .stopScheduler();
/* 3438:     */         
/* 3439:3825 */         localLocalFrame.setResult(str7);
/* 3440:     */       }
/* 3441:     */       catch (Throwable localThrowable21)
/* 3442:     */       {
/* 3443:3829 */         if ((localThrowable21 instanceof FFSException))
/* 3444:     */         {
/* 3445:3831 */           localLocalFrame.setException(localThrowable21);
/* 3446:3832 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3447:3833 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable21);
/* 3448:     */         }
/* 3449:3835 */         localThrowable21.printStackTrace(Jaguar.log);
/* 3450:3836 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/* 3451:3837 */         return localThrowable21.getClass().getName();
/* 3452:     */       }
/* 3453:     */     case 21: 
/* 3454:     */       try
/* 3455:     */       {
/* 3456:3845 */         String str8 = localBPWAdminBean
/* 3457:3846 */           .refreshScheduler();
/* 3458:     */         
/* 3459:3848 */         localLocalFrame.setResult(str8);
/* 3460:     */       }
/* 3461:     */       catch (Throwable localThrowable22)
/* 3462:     */       {
/* 3463:3852 */         if ((localThrowable22 instanceof FFSException))
/* 3464:     */         {
/* 3465:3854 */           localLocalFrame.setException(localThrowable22);
/* 3466:3855 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3467:3856 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable22);
/* 3468:     */         }
/* 3469:3858 */         localThrowable22.printStackTrace(Jaguar.log);
/* 3470:3859 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/* 3471:3860 */         return localThrowable22.getClass().getName();
/* 3472:     */       }
/* 3473:     */     case 22: 
/* 3474:     */       try
/* 3475:     */       {
/* 3476:     */         PropertyConfig localPropertyConfig3;
/* 3477:3869 */         if (!bool1)
/* 3478:     */         {
/* 3479:3871 */           localPropertyConfig3 = (PropertyConfig)localLocalFrame.get(0);
/* 3480:     */         }
/* 3481:     */         else
/* 3482:     */         {
/* 3483:3875 */           localObject1 = localLocalFrame.get(0);
/* 3484:3876 */           localPropertyConfig3 = (PropertyConfig)ObjectVal.clone(localObject1);
/* 3485:     */         }
/* 3486:3879 */         localBPWAdminBean.registerPropertyConfig(
/* 3487:3880 */           localPropertyConfig3);
/* 3488:     */       }
/* 3489:     */       catch (Throwable localThrowable23)
/* 3490:     */       {
/* 3491:3885 */         if ((localThrowable23 instanceof FFSException))
/* 3492:     */         {
/* 3493:3887 */           localLocalFrame.setException(localThrowable23);
/* 3494:3888 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3495:3889 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable23);
/* 3496:     */         }
/* 3497:3891 */         localThrowable23.printStackTrace(Jaguar.log);
/* 3498:3892 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/* 3499:3893 */         return localThrowable23.getClass().getName();
/* 3500:     */       }
/* 3501:     */     case 23: 
/* 3502:     */       try
/* 3503:     */       {
/* 3504:3902 */         String str9 = (String)localLocalFrame.get(0);
/* 3505:3903 */         localBPWAdminBean
/* 3506:3904 */           .startEngine(
/* 3507:3905 */           str9);
/* 3508:     */       }
/* 3509:     */       catch (Throwable localThrowable24)
/* 3510:     */       {
/* 3511:3910 */         if ((localThrowable24 instanceof FFSException))
/* 3512:     */         {
/* 3513:3912 */           localLocalFrame.setException(localThrowable24);
/* 3514:3913 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3515:3914 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable24);
/* 3516:     */         }
/* 3517:3916 */         localThrowable24.printStackTrace(Jaguar.log);
/* 3518:3917 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable24, true);
/* 3519:3918 */         return localThrowable24.getClass().getName();
/* 3520:     */       }
/* 3521:     */     case 24: 
/* 3522:     */       try
/* 3523:     */       {
/* 3524:3927 */         String str10 = (String)localLocalFrame.get(0);
/* 3525:3928 */         localBPWAdminBean
/* 3526:3929 */           .stopEngine(
/* 3527:3930 */           str10);
/* 3528:     */       }
/* 3529:     */       catch (Throwable localThrowable25)
/* 3530:     */       {
/* 3531:3935 */         if ((localThrowable25 instanceof FFSException))
/* 3532:     */         {
/* 3533:3937 */           localLocalFrame.setException(localThrowable25);
/* 3534:3938 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3535:3939 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable25);
/* 3536:     */         }
/* 3537:3941 */         localThrowable25.printStackTrace(Jaguar.log);
/* 3538:3942 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable25, true);
/* 3539:3943 */         return localThrowable25.getClass().getName();
/* 3540:     */       }
/* 3541:     */     case 25: 
/* 3542:     */       try
/* 3543:     */       {
/* 3544:3952 */         String str11 = (String)localLocalFrame.get(0);
/* 3545:3953 */         localBPWAdminBean
/* 3546:3954 */           .stopLimitCheckApprovalProcessor(
/* 3547:3955 */           str11);
/* 3548:     */       }
/* 3549:     */       catch (Throwable localThrowable26)
/* 3550:     */       {
/* 3551:3960 */         if ((localThrowable26 instanceof FFSException))
/* 3552:     */         {
/* 3553:3962 */           localLocalFrame.setException(localThrowable26);
/* 3554:3963 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3555:3964 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable26);
/* 3556:     */         }
/* 3557:3966 */         localThrowable26.printStackTrace(Jaguar.log);
/* 3558:3967 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable26, true);
/* 3559:3968 */         return localThrowable26.getClass().getName();
/* 3560:     */       }
/* 3561:     */     case 26: 
/* 3562:     */       try
/* 3563:     */       {
/* 3564:3977 */         String str12 = (String)localLocalFrame.get(0);
/* 3565:3978 */         localBPWAdminBean
/* 3566:3979 */           .startLimitCheckApprovalProcessor(
/* 3567:3980 */           str12);
/* 3568:     */       }
/* 3569:     */       catch (Throwable localThrowable27)
/* 3570:     */       {
/* 3571:3985 */         if ((localThrowable27 instanceof FFSException))
/* 3572:     */         {
/* 3573:3987 */           localLocalFrame.setException(localThrowable27);
/* 3574:3988 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3575:3989 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable27);
/* 3576:     */         }
/* 3577:3991 */         localThrowable27.printStackTrace(Jaguar.log);
/* 3578:3992 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable27, true);
/* 3579:3993 */         return localThrowable27.getClass().getName();
/* 3580:     */       }
/* 3581:     */     case 27: 
/* 3582:     */       try
/* 3583:     */       {
/* 3584:4002 */         String str13 = (String)localLocalFrame.get(0);
/* 3585:     */         
/* 3586:4004 */         localObject1 = (String)localLocalFrame.get(1);
/* 3587:4005 */         localBPWAdminBean
/* 3588:4006 */           .runBatchProcess(
/* 3589:4007 */           str13, 
/* 3590:4008 */           (String)localObject1);
/* 3591:     */       }
/* 3592:     */       catch (Throwable localThrowable28)
/* 3593:     */       {
/* 3594:4013 */         if ((localThrowable28 instanceof FFSException))
/* 3595:     */         {
/* 3596:4015 */           localLocalFrame.setException(localThrowable28);
/* 3597:4016 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3598:4017 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable28);
/* 3599:     */         }
/* 3600:4019 */         localThrowable28.printStackTrace(Jaguar.log);
/* 3601:4020 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable28, true);
/* 3602:4021 */         return localThrowable28.getClass().getName();
/* 3603:     */       }
/* 3604:     */     case 28: 
/* 3605:     */       try
/* 3606:     */       {
/* 3607:     */         InstructionType localInstructionType1;
/* 3608:4030 */         if (!bool1)
/* 3609:     */         {
/* 3610:4032 */           localInstructionType1 = (InstructionType)localLocalFrame.get(0);
/* 3611:     */         }
/* 3612:     */         else
/* 3613:     */         {
/* 3614:4036 */           localObject1 = localLocalFrame.get(0);
/* 3615:4037 */           localInstructionType1 = (InstructionType)ObjectVal.clone(localObject1);
/* 3616:     */         }
/* 3617:4040 */         localBPWAdminBean.updateScheduleRunTimeConfig(
/* 3618:4041 */           localInstructionType1);
/* 3619:     */       }
/* 3620:     */       catch (Throwable localThrowable29)
/* 3621:     */       {
/* 3622:4046 */         if ((localThrowable29 instanceof FFSException))
/* 3623:     */         {
/* 3624:4048 */           localLocalFrame.setException(localThrowable29);
/* 3625:4049 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3626:4050 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable29);
/* 3627:     */         }
/* 3628:4052 */         localThrowable29.printStackTrace(Jaguar.log);
/* 3629:4053 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable29, true);
/* 3630:4054 */         return localThrowable29.getClass().getName();
/* 3631:     */       }
/* 3632:     */     case 29: 
/* 3633:     */       try
/* 3634:     */       {
/* 3635:     */         InstructionType localInstructionType2;
/* 3636:4063 */         if (!bool1)
/* 3637:     */         {
/* 3638:4065 */           localInstructionType2 = (InstructionType)localLocalFrame.get(0);
/* 3639:     */         }
/* 3640:     */         else
/* 3641:     */         {
/* 3642:4069 */           localObject1 = localLocalFrame.get(0);
/* 3643:4070 */           localInstructionType2 = (InstructionType)ObjectVal.clone(localObject1);
/* 3644:     */         }
/* 3645:4073 */         localBPWAdminBean.updateScheduleProcessingConfig(
/* 3646:4074 */           localInstructionType2);
/* 3647:     */       }
/* 3648:     */       catch (Throwable localThrowable30)
/* 3649:     */       {
/* 3650:4079 */         if ((localThrowable30 instanceof FFSException))
/* 3651:     */         {
/* 3652:4081 */           localLocalFrame.setException(localThrowable30);
/* 3653:4082 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3654:4083 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable30);
/* 3655:     */         }
/* 3656:4085 */         localThrowable30.printStackTrace(Jaguar.log);
/* 3657:4086 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable30, true);
/* 3658:4087 */         return localThrowable30.getClass().getName();
/* 3659:     */       }
/* 3660:     */     case 30: 
/* 3661:     */       try
/* 3662:     */       {
/* 3663:     */         InstructionType localInstructionType3;
/* 3664:4096 */         if (!bool1)
/* 3665:     */         {
/* 3666:4098 */           localInstructionType3 = (InstructionType)localLocalFrame.get(0);
/* 3667:     */         }
/* 3668:     */         else
/* 3669:     */         {
/* 3670:4102 */           localObject1 = localLocalFrame.get(0);
/* 3671:4103 */           localInstructionType3 = (InstructionType)ObjectVal.clone(localObject1);
/* 3672:     */         }
/* 3673:4106 */         localBPWAdminBean.updateScheduleConfig(
/* 3674:4107 */           localInstructionType3);
/* 3675:     */       }
/* 3676:     */       catch (Throwable localThrowable31)
/* 3677:     */       {
/* 3678:4112 */         if ((localThrowable31 instanceof FFSException))
/* 3679:     */         {
/* 3680:4114 */           localLocalFrame.setException(localThrowable31);
/* 3681:4115 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3682:4116 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable31);
/* 3683:     */         }
/* 3684:4118 */         localThrowable31.printStackTrace(Jaguar.log);
/* 3685:4119 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable31, true);
/* 3686:4120 */         return localThrowable31.getClass().getName();
/* 3687:     */       }
/* 3688:     */     case 31: 
/* 3689:     */       try
/* 3690:     */       {
/* 3691:4129 */         String str14 = (String)localLocalFrame.get(0);
/* 3692:     */         
/* 3693:4131 */         localObject1 = (String)localLocalFrame.get(1);
/* 3694:4132 */         localObject4 = localBPWAdminBean
/* 3695:4133 */           .getScheduleConfig(
/* 3696:4134 */           str14, 
/* 3697:4135 */           (String)localObject1);
/* 3698:     */         
/* 3699:4137 */         localLocalFrame.setResult(localObject4);
/* 3700:     */       }
/* 3701:     */       catch (Throwable localThrowable32)
/* 3702:     */       {
/* 3703:4141 */         if ((localThrowable32 instanceof FFSException))
/* 3704:     */         {
/* 3705:4143 */           localLocalFrame.setException(localThrowable32);
/* 3706:4144 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3707:4145 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable32);
/* 3708:     */         }
/* 3709:4147 */         localThrowable32.printStackTrace(Jaguar.log);
/* 3710:4148 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable32, true);
/* 3711:4149 */         return localThrowable32.getClass().getName();
/* 3712:     */       }
/* 3713:     */     case 32: 
/* 3714:     */       try
/* 3715:     */       {
/* 3716:4157 */         InstructionType[] arrayOfInstructionType = localBPWAdminBean
/* 3717:4158 */           .getScheduleConfig();
/* 3718:     */         
/* 3719:4160 */         localLocalFrame.setResult(arrayOfInstructionType);
/* 3720:     */       }
/* 3721:     */       catch (Throwable localThrowable33)
/* 3722:     */       {
/* 3723:4164 */         if ((localThrowable33 instanceof FFSException))
/* 3724:     */         {
/* 3725:4166 */           localLocalFrame.setException(localThrowable33);
/* 3726:4167 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3727:4168 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable33);
/* 3728:     */         }
/* 3729:4170 */         localThrowable33.printStackTrace(Jaguar.log);
/* 3730:4171 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable33, true);
/* 3731:4172 */         return localThrowable33.getClass().getName();
/* 3732:     */       }
/* 3733:     */     case 33: 
/* 3734:     */       try
/* 3735:     */       {
/* 3736:4181 */         String str15 = (String)localLocalFrame.get(0);
/* 3737:     */         
/* 3738:4183 */         localObject1 = (String)localLocalFrame.get(1);
/* 3739:4184 */         localObject4 = localBPWAdminBean
/* 3740:4185 */           .getSchedulerInfo(
/* 3741:4186 */           str15, 
/* 3742:4187 */           (String)localObject1);
/* 3743:     */         
/* 3744:4189 */         localLocalFrame.setResult(localObject4);
/* 3745:     */       }
/* 3746:     */       catch (Throwable localThrowable34)
/* 3747:     */       {
/* 3748:4193 */         if ((localThrowable34 instanceof FFSException))
/* 3749:     */         {
/* 3750:4195 */           localLocalFrame.setException(localThrowable34);
/* 3751:4196 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3752:4197 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable34);
/* 3753:     */         }
/* 3754:4199 */         localThrowable34.printStackTrace(Jaguar.log);
/* 3755:4200 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable34, true);
/* 3756:4201 */         return localThrowable34.getClass().getName();
/* 3757:     */       }
/* 3758:     */     case 34: 
/* 3759:     */       try
/* 3760:     */       {
/* 3761:4209 */         SchedulerInfo[] arrayOfSchedulerInfo = localBPWAdminBean
/* 3762:4210 */           .getSchedulerInfo();
/* 3763:     */         
/* 3764:4212 */         localLocalFrame.setResult(arrayOfSchedulerInfo);
/* 3765:     */       }
/* 3766:     */       catch (Throwable localThrowable35)
/* 3767:     */       {
/* 3768:4216 */         if ((localThrowable35 instanceof FFSException))
/* 3769:     */         {
/* 3770:4218 */           localLocalFrame.setException(localThrowable35);
/* 3771:4219 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3772:4220 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable35);
/* 3773:     */         }
/* 3774:4222 */         localThrowable35.printStackTrace(Jaguar.log);
/* 3775:4223 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable35, true);
/* 3776:4224 */         return localThrowable35.getClass().getName();
/* 3777:     */       }
/* 3778:     */     case 35: 
/* 3779:     */       try
/* 3780:     */       {
/* 3781:4233 */         String str16 = (String)localLocalFrame.get(0);
/* 3782:     */         
/* 3783:4235 */         localObject1 = (String)localLocalFrame.get(1);
/* 3784:4237 */         if (!bool1)
/* 3785:     */         {
/* 3786:4239 */           localObject4 = (ScheduleHist)localLocalFrame.get(2);
/* 3787:     */         }
/* 3788:     */         else
/* 3789:     */         {
/* 3790:4243 */           localObject6 = localLocalFrame.get(2);
/* 3791:4244 */           localObject4 = (ScheduleHist)ObjectVal.clone(localObject6);
/* 3792:     */         }
/* 3793:4246 */         localObject6 = 
/* 3794:4247 */           localBPWAdminBean.getScheduleHist(
/* 3795:4248 */           str16, 
/* 3796:4249 */           (String)localObject1, 
/* 3797:4250 */           (ScheduleHist)localObject4);
/* 3798:     */         
/* 3799:4252 */         localLocalFrame.setResult(localObject6);
/* 3800:     */       }
/* 3801:     */       catch (Throwable localThrowable36)
/* 3802:     */       {
/* 3803:4256 */         if ((localThrowable36 instanceof FFSException))
/* 3804:     */         {
/* 3805:4258 */           localLocalFrame.setException(localThrowable36);
/* 3806:4259 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3807:4260 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable36);
/* 3808:     */         }
/* 3809:4262 */         localThrowable36.printStackTrace(Jaguar.log);
/* 3810:4263 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable36, true);
/* 3811:4264 */         return localThrowable36.getClass().getName();
/* 3812:     */       }
/* 3813:     */     case 36: 
/* 3814:     */       try
/* 3815:     */       {
/* 3816:     */         PayeeInfo localPayeeInfo1;
/* 3817:4273 */         if (!bool1)
/* 3818:     */         {
/* 3819:4275 */           localPayeeInfo1 = (PayeeInfo)localLocalFrame.get(0);
/* 3820:     */         }
/* 3821:     */         else
/* 3822:     */         {
/* 3823:4279 */           localObject1 = localLocalFrame.get(0);
/* 3824:4280 */           localPayeeInfo1 = (PayeeInfo)ObjectVal.clone(localObject1);
/* 3825:     */         }
/* 3826:4283 */         int i = ((Integer)localLocalFrame.get(1)).intValue();
/* 3827:4284 */         localObject4 = localBPWAdminBean
/* 3828:4285 */           .searchGlobalPayees(
/* 3829:4286 */           localPayeeInfo1, 
/* 3830:4287 */           i);
/* 3831:     */         
/* 3832:4289 */         localLocalFrame.setResult(localObject4);
/* 3833:     */       }
/* 3834:     */       catch (Throwable localThrowable37)
/* 3835:     */       {
/* 3836:4293 */         if ((localThrowable37 instanceof FFSException))
/* 3837:     */         {
/* 3838:4295 */           localLocalFrame.setException(localThrowable37);
/* 3839:4296 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3840:4297 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable37);
/* 3841:     */         }
/* 3842:4299 */         localThrowable37.printStackTrace(Jaguar.log);
/* 3843:4300 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable37, true);
/* 3844:4301 */         return localThrowable37.getClass().getName();
/* 3845:     */       }
/* 3846:     */     case 37: 
/* 3847:     */       try
/* 3848:     */       {
/* 3849:4310 */         String str17 = (String)localLocalFrame.get(0);
/* 3850:4311 */         localObject2 = localBPWAdminBean
/* 3851:4312 */           .searchGlobalPayees(
/* 3852:4313 */           str17);
/* 3853:     */         
/* 3854:4315 */         localLocalFrame.setResult(localObject2);
/* 3855:     */       }
/* 3856:     */       catch (Throwable localThrowable38)
/* 3857:     */       {
/* 3858:4319 */         if ((localThrowable38 instanceof FFSException))
/* 3859:     */         {
/* 3860:4321 */           localLocalFrame.setException(localThrowable38);
/* 3861:4322 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3862:4323 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable38);
/* 3863:     */         }
/* 3864:4325 */         localThrowable38.printStackTrace(Jaguar.log);
/* 3865:4326 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable38, true);
/* 3866:4327 */         return localThrowable38.getClass().getName();
/* 3867:     */       }
/* 3868:     */     case 38: 
/* 3869:     */       try
/* 3870:     */       {
/* 3871:4336 */         String str18 = (String)localLocalFrame.get(0);
/* 3872:4337 */         localObject2 = localBPWAdminBean
/* 3873:4338 */           .getGlobalPayee(
/* 3874:4339 */           str18);
/* 3875:     */         
/* 3876:4341 */         localLocalFrame.setResult(localObject2);
/* 3877:     */       }
/* 3878:     */       catch (Throwable localThrowable39)
/* 3879:     */       {
/* 3880:4345 */         if ((localThrowable39 instanceof FFSException))
/* 3881:     */         {
/* 3882:4347 */           localLocalFrame.setException(localThrowable39);
/* 3883:4348 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3884:4349 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable39);
/* 3885:     */         }
/* 3886:4351 */         localThrowable39.printStackTrace(Jaguar.log);
/* 3887:4352 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable39, true);
/* 3888:4353 */         return localThrowable39.getClass().getName();
/* 3889:     */       }
/* 3890:     */     case 39: 
/* 3891:     */       try
/* 3892:     */       {
/* 3893:     */         PayeeInfo localPayeeInfo2;
/* 3894:4362 */         if (!bool1)
/* 3895:     */         {
/* 3896:4364 */           localPayeeInfo2 = (PayeeInfo)localLocalFrame.get(0);
/* 3897:     */         }
/* 3898:     */         else
/* 3899:     */         {
/* 3900:4368 */           localObject2 = localLocalFrame.get(0);
/* 3901:4369 */           localPayeeInfo2 = (PayeeInfo)ObjectVal.clone(localObject2);
/* 3902:     */         }
/* 3903:4371 */         localObject2 = 
/* 3904:4372 */           localBPWAdminBean.updateGlobalPayee(
/* 3905:4373 */           localPayeeInfo2);
/* 3906:     */         
/* 3907:4375 */         localLocalFrame.setResult(localObject2);
/* 3908:     */       }
/* 3909:     */       catch (Throwable localThrowable40)
/* 3910:     */       {
/* 3911:4379 */         if ((localThrowable40 instanceof FFSException))
/* 3912:     */         {
/* 3913:4381 */           localLocalFrame.setException(localThrowable40);
/* 3914:4382 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3915:4383 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable40);
/* 3916:     */         }
/* 3917:4385 */         localThrowable40.printStackTrace(Jaguar.log);
/* 3918:4386 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable40, true);
/* 3919:4387 */         return localThrowable40.getClass().getName();
/* 3920:     */       }
/* 3921:     */     case 40: 
/* 3922:     */       try
/* 3923:     */       {
/* 3924:     */         FFSDBProperties localFFSDBProperties1;
/* 3925:4396 */         if (!bool1)
/* 3926:     */         {
/* 3927:4398 */           localFFSDBProperties1 = (FFSDBProperties)localLocalFrame.get(0);
/* 3928:     */         }
/* 3929:     */         else
/* 3930:     */         {
/* 3931:4402 */           localObject2 = localLocalFrame.get(0);
/* 3932:4403 */           localFFSDBProperties1 = (FFSDBProperties)ObjectVal.clone(localObject2);
/* 3933:     */         }
/* 3934:4406 */         if (!bool1)
/* 3935:     */         {
/* 3936:4408 */           localObject2 = (PayeeInfo)localLocalFrame.get(1);
/* 3937:     */         }
/* 3938:     */         else
/* 3939:     */         {
/* 3940:4412 */           localObject4 = localLocalFrame.get(1);
/* 3941:4413 */           localObject2 = (PayeeInfo)ObjectVal.clone(localObject4);
/* 3942:     */         }
/* 3943:4416 */         if (!bool1)
/* 3944:     */         {
/* 3945:4418 */           localObject4 = (PayeeRouteInfo)localLocalFrame.get(2);
/* 3946:     */         }
/* 3947:     */         else
/* 3948:     */         {
/* 3949:4422 */           localObject6 = localLocalFrame.get(2);
/* 3950:4423 */           localObject4 = (PayeeRouteInfo)ObjectVal.clone(localObject6);
/* 3951:     */         }
/* 3952:4425 */         localObject6 = 
/* 3953:4426 */           localBPWAdminBean.addPayee(
/* 3954:4427 */           localFFSDBProperties1, 
/* 3955:4428 */           (PayeeInfo)localObject2, 
/* 3956:4429 */           (PayeeRouteInfo)localObject4);
/* 3957:     */         
/* 3958:4431 */         localLocalFrame.setResult(localObject6);
/* 3959:     */       }
/* 3960:     */       catch (Throwable localThrowable41)
/* 3961:     */       {
/* 3962:4435 */         if ((localThrowable41 instanceof FFSException))
/* 3963:     */         {
/* 3964:4437 */           localLocalFrame.setException(localThrowable41);
/* 3965:4438 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 3966:4439 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable41);
/* 3967:     */         }
/* 3968:4441 */         localThrowable41.printStackTrace(Jaguar.log);
/* 3969:4442 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable41, true);
/* 3970:4443 */         return localThrowable41.getClass().getName();
/* 3971:     */       }
/* 3972:     */     case 41: 
/* 3973:     */       try
/* 3974:     */       {
/* 3975:     */         FFSDBProperties localFFSDBProperties2;
/* 3976:4452 */         if (!bool1)
/* 3977:     */         {
/* 3978:4454 */           localFFSDBProperties2 = (FFSDBProperties)localLocalFrame.get(0);
/* 3979:     */         }
/* 3980:     */         else
/* 3981:     */         {
/* 3982:4458 */           localObject2 = localLocalFrame.get(0);
/* 3983:4459 */           localFFSDBProperties2 = (FFSDBProperties)ObjectVal.clone(localObject2);
/* 3984:     */         }
/* 3985:4462 */         if (!bool1)
/* 3986:     */         {
/* 3987:4464 */           localObject2 = (PayeeInfo)localLocalFrame.get(1);
/* 3988:     */         }
/* 3989:     */         else
/* 3990:     */         {
/* 3991:4468 */           localObject4 = localLocalFrame.get(1);
/* 3992:4469 */           localObject2 = (PayeeInfo)ObjectVal.clone(localObject4);
/* 3993:     */         }
/* 3994:4472 */         if (!bool1)
/* 3995:     */         {
/* 3996:4474 */           localObject4 = (PayeeRouteInfo)localLocalFrame.get(2);
/* 3997:     */         }
/* 3998:     */         else
/* 3999:     */         {
/* 4000:4478 */           localObject6 = localLocalFrame.get(2);
/* 4001:4479 */           localObject4 = (PayeeRouteInfo)ObjectVal.clone(localObject6);
/* 4002:     */         }
/* 4003:4482 */         localBPWAdminBean.updatePayee(
/* 4004:4483 */           localFFSDBProperties2, 
/* 4005:4484 */           (PayeeInfo)localObject2, 
/* 4006:4485 */           (PayeeRouteInfo)localObject4);
/* 4007:     */       }
/* 4008:     */       catch (Throwable localThrowable42)
/* 4009:     */       {
/* 4010:4490 */         if ((localThrowable42 instanceof FFSException))
/* 4011:     */         {
/* 4012:4492 */           localLocalFrame.setException(localThrowable42);
/* 4013:4493 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4014:4494 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable42);
/* 4015:     */         }
/* 4016:4496 */         localThrowable42.printStackTrace(Jaguar.log);
/* 4017:4497 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable42, true);
/* 4018:4498 */         return localThrowable42.getClass().getName();
/* 4019:     */       }
/* 4020:     */     case 42: 
/* 4021:     */       try
/* 4022:     */       {
/* 4023:     */         FFSDBProperties localFFSDBProperties3;
/* 4024:4507 */         if (!bool1)
/* 4025:     */         {
/* 4026:4509 */           localFFSDBProperties3 = (FFSDBProperties)localLocalFrame.get(0);
/* 4027:     */         }
/* 4028:     */         else
/* 4029:     */         {
/* 4030:4513 */           localObject2 = localLocalFrame.get(0);
/* 4031:4514 */           localFFSDBProperties3 = (FFSDBProperties)ObjectVal.clone(localObject2);
/* 4032:     */         }
/* 4033:4517 */         localObject2 = (String)localLocalFrame.get(1);
/* 4034:4518 */         localBPWAdminBean
/* 4035:4519 */           .deletePayee(
/* 4036:4520 */           localFFSDBProperties3, 
/* 4037:4521 */           (String)localObject2);
/* 4038:     */       }
/* 4039:     */       catch (Throwable localThrowable43)
/* 4040:     */       {
/* 4041:4526 */         if ((localThrowable43 instanceof FFSException))
/* 4042:     */         {
/* 4043:4528 */           localLocalFrame.setException(localThrowable43);
/* 4044:4529 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4045:4530 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable43);
/* 4046:     */         }
/* 4047:4532 */         localThrowable43.printStackTrace(Jaguar.log);
/* 4048:4533 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable43, true);
/* 4049:4534 */         return localThrowable43.getClass().getName();
/* 4050:     */       }
/* 4051:     */     case 43: 
/* 4052:     */       try
/* 4053:     */       {
/* 4054:     */         FFSDBProperties localFFSDBProperties4;
/* 4055:4543 */         if (!bool1)
/* 4056:     */         {
/* 4057:4545 */           localFFSDBProperties4 = (FFSDBProperties)localLocalFrame.get(0);
/* 4058:     */         }
/* 4059:     */         else
/* 4060:     */         {
/* 4061:4549 */           localObject2 = localLocalFrame.get(0);
/* 4062:4550 */           localFFSDBProperties4 = (FFSDBProperties)ObjectVal.clone(localObject2);
/* 4063:     */         }
/* 4064:4553 */         localObject2 = (String)localLocalFrame.get(1);
/* 4065:     */         
/* 4066:4555 */         int m = ((Integer)localLocalFrame.get(2)).intValue();
/* 4067:4556 */         localObject6 = localBPWAdminBean
/* 4068:4557 */           .getPayeeRoute(
/* 4069:4558 */           localFFSDBProperties4, 
/* 4070:4559 */           (String)localObject2, 
/* 4071:4560 */           m);
/* 4072:     */         
/* 4073:4562 */         localLocalFrame.setResult(localObject6);
/* 4074:     */       }
/* 4075:     */       catch (Throwable localThrowable44)
/* 4076:     */       {
/* 4077:4566 */         if ((localThrowable44 instanceof FFSException))
/* 4078:     */         {
/* 4079:4568 */           localLocalFrame.setException(localThrowable44);
/* 4080:4569 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4081:4570 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable44);
/* 4082:     */         }
/* 4083:4572 */         localThrowable44.printStackTrace(Jaguar.log);
/* 4084:4573 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable44, true);
/* 4085:4574 */         return localThrowable44.getClass().getName();
/* 4086:     */       }
/* 4087:     */     case 44: 
/* 4088:     */       try
/* 4089:     */       {
/* 4090:     */         FFSDBProperties localFFSDBProperties5;
/* 4091:4583 */         if (!bool1)
/* 4092:     */         {
/* 4093:4585 */           localFFSDBProperties5 = (FFSDBProperties)localLocalFrame.get(0);
/* 4094:     */         }
/* 4095:     */         else
/* 4096:     */         {
/* 4097:4589 */           localObject2 = localLocalFrame.get(0);
/* 4098:4590 */           localFFSDBProperties5 = (FFSDBProperties)ObjectVal.clone(localObject2);
/* 4099:     */         }
/* 4100:4593 */         localObject2 = (String)localLocalFrame.get(1);
/* 4101:4594 */         localObject5 = localBPWAdminBean
/* 4102:4595 */           .findPayeeByID(
/* 4103:4596 */           localFFSDBProperties5, 
/* 4104:4597 */           (String)localObject2);
/* 4105:     */         
/* 4106:4599 */         localLocalFrame.setResult(localObject5);
/* 4107:     */       }
/* 4108:     */       catch (Throwable localThrowable45)
/* 4109:     */       {
/* 4110:4603 */         if ((localThrowable45 instanceof FFSException))
/* 4111:     */         {
/* 4112:4605 */           localLocalFrame.setException(localThrowable45);
/* 4113:4606 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4114:4607 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable45);
/* 4115:     */         }
/* 4116:4609 */         localThrowable45.printStackTrace(Jaguar.log);
/* 4117:4610 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable45, true);
/* 4118:4611 */         return localThrowable45.getClass().getName();
/* 4119:     */       }
/* 4120:     */     case 45: 
/* 4121:     */       try
/* 4122:     */       {
/* 4123:     */         FFSDBProperties localFFSDBProperties6;
/* 4124:4620 */         if (!bool1)
/* 4125:     */         {
/* 4126:4622 */           localFFSDBProperties6 = (FFSDBProperties)localLocalFrame.get(0);
/* 4127:     */         }
/* 4128:     */         else
/* 4129:     */         {
/* 4130:4626 */           localObject2 = localLocalFrame.get(0);
/* 4131:4627 */           localFFSDBProperties6 = (FFSDBProperties)ObjectVal.clone(localObject2);
/* 4132:     */         }
/* 4133:4629 */         localObject2 = 
/* 4134:4630 */           localBPWAdminBean.getAllFulfillmentInfo(
/* 4135:4631 */           localFFSDBProperties6);
/* 4136:     */         
/* 4137:4633 */         localLocalFrame.setResult(localObject2);
/* 4138:     */       }
/* 4139:     */       catch (Throwable localThrowable46)
/* 4140:     */       {
/* 4141:4637 */         if ((localThrowable46 instanceof FFSException))
/* 4142:     */         {
/* 4143:4639 */           localLocalFrame.setException(localThrowable46);
/* 4144:4640 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4145:4641 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable46);
/* 4146:     */         }
/* 4147:4643 */         localThrowable46.printStackTrace(Jaguar.log);
/* 4148:4644 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable46, true);
/* 4149:4645 */         return localThrowable46.getClass().getName();
/* 4150:     */       }
/* 4151:     */     case 46: 
/* 4152:     */       try
/* 4153:     */       {
/* 4154:4653 */         FulfillmentInfo[] arrayOfFulfillmentInfo = localBPWAdminBean
/* 4155:4654 */           .getFulfillmentSystems();
/* 4156:     */         
/* 4157:4656 */         localLocalFrame.setResult(arrayOfFulfillmentInfo);
/* 4158:     */       }
/* 4159:     */       catch (Throwable localThrowable47)
/* 4160:     */       {
/* 4161:4660 */         if ((localThrowable47 instanceof FFSException))
/* 4162:     */         {
/* 4163:4662 */           localLocalFrame.setException(localThrowable47);
/* 4164:4663 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4165:4664 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable47);
/* 4166:     */         }
/* 4167:4666 */         localThrowable47.printStackTrace(Jaguar.log);
/* 4168:4667 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable47, true);
/* 4169:4668 */         return localThrowable47.getClass().getName();
/* 4170:     */       }
/* 4171:     */     case 47: 
/* 4172:     */       try
/* 4173:     */       {
/* 4174:4676 */         String[] arrayOfString = localBPWAdminBean
/* 4175:4677 */           .getGlobalPayeeGroups();
/* 4176:     */         
/* 4177:4679 */         localLocalFrame.setResult(arrayOfString);
/* 4178:     */       }
/* 4179:     */       catch (Throwable localThrowable48)
/* 4180:     */       {
/* 4181:4683 */         if ((localThrowable48 instanceof FFSException))
/* 4182:     */         {
/* 4183:4685 */           localLocalFrame.setException(localThrowable48);
/* 4184:4686 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4185:4687 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable48);
/* 4186:     */         }
/* 4187:4689 */         localThrowable48.printStackTrace(Jaguar.log);
/* 4188:4690 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable48, true);
/* 4189:4691 */         return localThrowable48.getClass().getName();
/* 4190:     */       }
/* 4191:     */     case 48: 
/* 4192:     */       try
/* 4193:     */       {
/* 4194:     */         FFSDBProperties localFFSDBProperties7;
/* 4195:4700 */         if (!bool1)
/* 4196:     */         {
/* 4197:4702 */           localFFSDBProperties7 = (FFSDBProperties)localLocalFrame.get(0);
/* 4198:     */         }
/* 4199:     */         else
/* 4200:     */         {
/* 4201:4706 */           localObject2 = localLocalFrame.get(0);
/* 4202:4707 */           localFFSDBProperties7 = (FFSDBProperties)ObjectVal.clone(localObject2);
/* 4203:     */         }
/* 4204:4710 */         if (!bool1)
/* 4205:     */         {
/* 4206:4712 */           localObject2 = (FulfillmentInfo)localLocalFrame.get(1);
/* 4207:     */         }
/* 4208:     */         else
/* 4209:     */         {
/* 4210:4716 */           localObject5 = localLocalFrame.get(1);
/* 4211:4717 */           localObject2 = (FulfillmentInfo)ObjectVal.clone(localObject5);
/* 4212:     */         }
/* 4213:4720 */         localBPWAdminBean.addFulfillmentInfo(
/* 4214:4721 */           localFFSDBProperties7, 
/* 4215:4722 */           (FulfillmentInfo)localObject2);
/* 4216:     */       }
/* 4217:     */       catch (Throwable localThrowable49)
/* 4218:     */       {
/* 4219:4727 */         if ((localThrowable49 instanceof FFSException))
/* 4220:     */         {
/* 4221:4729 */           localLocalFrame.setException(localThrowable49);
/* 4222:4730 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4223:4731 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable49);
/* 4224:     */         }
/* 4225:4733 */         localThrowable49.printStackTrace(Jaguar.log);
/* 4226:4734 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable49, true);
/* 4227:4735 */         return localThrowable49.getClass().getName();
/* 4228:     */       }
/* 4229:     */     case 49: 
/* 4230:     */       try
/* 4231:     */       {
/* 4232:     */         FFSDBProperties localFFSDBProperties8;
/* 4233:4744 */         if (!bool1)
/* 4234:     */         {
/* 4235:4746 */           localFFSDBProperties8 = (FFSDBProperties)localLocalFrame.get(0);
/* 4236:     */         }
/* 4237:     */         else
/* 4238:     */         {
/* 4239:4750 */           localObject2 = localLocalFrame.get(0);
/* 4240:4751 */           localFFSDBProperties8 = (FFSDBProperties)ObjectVal.clone(localObject2);
/* 4241:     */         }
/* 4242:4754 */         if (!bool1)
/* 4243:     */         {
/* 4244:4756 */           localObject2 = (FulfillmentInfo)localLocalFrame.get(1);
/* 4245:     */         }
/* 4246:     */         else
/* 4247:     */         {
/* 4248:4760 */           localObject5 = localLocalFrame.get(1);
/* 4249:4761 */           localObject2 = (FulfillmentInfo)ObjectVal.clone(localObject5);
/* 4250:     */         }
/* 4251:4764 */         localBPWAdminBean.updateFulfillmentInfo(
/* 4252:4765 */           localFFSDBProperties8, 
/* 4253:4766 */           (FulfillmentInfo)localObject2);
/* 4254:     */       }
/* 4255:     */       catch (Throwable localThrowable50)
/* 4256:     */       {
/* 4257:4771 */         if ((localThrowable50 instanceof FFSException))
/* 4258:     */         {
/* 4259:4773 */           localLocalFrame.setException(localThrowable50);
/* 4260:4774 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4261:4775 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable50);
/* 4262:     */         }
/* 4263:4777 */         localThrowable50.printStackTrace(Jaguar.log);
/* 4264:4778 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable50, true);
/* 4265:4779 */         return localThrowable50.getClass().getName();
/* 4266:     */       }
/* 4267:     */     case 50: 
/* 4268:     */       try
/* 4269:     */       {
/* 4270:     */         FFSDBProperties localFFSDBProperties9;
/* 4271:4788 */         if (!bool1)
/* 4272:     */         {
/* 4273:4790 */           localFFSDBProperties9 = (FFSDBProperties)localLocalFrame.get(0);
/* 4274:     */         }
/* 4275:     */         else
/* 4276:     */         {
/* 4277:4794 */           localObject2 = localLocalFrame.get(0);
/* 4278:4795 */           localFFSDBProperties9 = (FFSDBProperties)ObjectVal.clone(localObject2);
/* 4279:     */         }
/* 4280:4798 */         int j = ((Integer)localLocalFrame.get(1)).intValue();
/* 4281:4799 */         localBPWAdminBean
/* 4282:4800 */           .deleteFulfillmentInfo(
/* 4283:4801 */           localFFSDBProperties9, 
/* 4284:4802 */           j);
/* 4285:     */       }
/* 4286:     */       catch (Throwable localThrowable51)
/* 4287:     */       {
/* 4288:4807 */         if ((localThrowable51 instanceof FFSException))
/* 4289:     */         {
/* 4290:4809 */           localLocalFrame.setException(localThrowable51);
/* 4291:4810 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4292:4811 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable51);
/* 4293:     */         }
/* 4294:4813 */         localThrowable51.printStackTrace(Jaguar.log);
/* 4295:4814 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable51, true);
/* 4296:4815 */         return localThrowable51.getClass().getName();
/* 4297:     */       }
/* 4298:     */     default: 
/* 4299:4821 */       return 
/* 4300:4822 */         localInvoke1(
/* 4301:4823 */         local_ServerRequest, 
/* 4302:4824 */         paramInputStream, 
/* 4303:4825 */         paramOutputStream, 
/* 4304:4826 */         localBPWAdminBean, 
/* 4305:4827 */         localLocalFrame, 
/* 4306:4828 */         localInteger, 
/* 4307:4829 */         bool1);
/* 4308:     */     }
/* 4309:4833 */     return null;
/* 4310:     */   }
/* 4311:     */   
/* 4312:     */   private static String localInvoke1(_ServerRequest param_ServerRequest, InputStream paramInputStream, OutputStream paramOutputStream, BPWAdminBean paramBPWAdminBean, LocalFrame paramLocalFrame, Integer paramInteger, boolean paramBoolean)
/* 4313:     */   {
/* 4314:     */     Object localObject1;
/* 4315:     */     Object localObject2;
/* 4316:     */     String str4;
/* 4317:4845 */     switch (paramInteger.intValue())
/* 4318:     */     {
/* 4319:     */     case 51: 
/* 4320:     */       try
/* 4321:     */       {
/* 4322:4852 */         int i = ((Integer)paramLocalFrame.get(0)).intValue();
/* 4323:4853 */         paramBPWAdminBean
/* 4324:4854 */           .setDebugLevel(
/* 4325:4855 */           i);
/* 4326:     */       }
/* 4327:     */       catch (Throwable localThrowable1)
/* 4328:     */       {
/* 4329:4860 */         if ((localThrowable1 instanceof FFSException))
/* 4330:     */         {
/* 4331:4862 */           paramLocalFrame.setException(localThrowable1);
/* 4332:4863 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4333:4864 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable1);
/* 4334:     */         }
/* 4335:4866 */         localThrowable1.printStackTrace(Jaguar.log);
/* 4336:4867 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable1, true);
/* 4337:4868 */         return localThrowable1.getClass().getName();
/* 4338:     */       }
/* 4339:     */     case 52: 
/* 4340:     */       try
/* 4341:     */       {
/* 4342:     */         ProcessingWindowInfo localProcessingWindowInfo1;
/* 4343:4877 */         if (!paramBoolean)
/* 4344:     */         {
/* 4345:4879 */           localProcessingWindowInfo1 = (ProcessingWindowInfo)paramLocalFrame.get(0);
/* 4346:     */         }
/* 4347:     */         else
/* 4348:     */         {
/* 4349:4883 */           localObject1 = paramLocalFrame.get(0);
/* 4350:4884 */           localProcessingWindowInfo1 = (ProcessingWindowInfo)ObjectVal.clone(localObject1);
/* 4351:     */         }
/* 4352:4886 */         localObject1 = 
/* 4353:4887 */           paramBPWAdminBean.addProcessingWindow(
/* 4354:4888 */           localProcessingWindowInfo1);
/* 4355:     */         
/* 4356:4890 */         paramLocalFrame.setResult(localObject1);
/* 4357:     */       }
/* 4358:     */       catch (Throwable localThrowable2)
/* 4359:     */       {
/* 4360:4894 */         if ((localThrowable2 instanceof FFSException))
/* 4361:     */         {
/* 4362:4896 */           paramLocalFrame.setException(localThrowable2);
/* 4363:4897 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4364:4898 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable2);
/* 4365:     */         }
/* 4366:4900 */         localThrowable2.printStackTrace(Jaguar.log);
/* 4367:4901 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable2, true);
/* 4368:4902 */         return localThrowable2.getClass().getName();
/* 4369:     */       }
/* 4370:     */     case 53: 
/* 4371:     */       try
/* 4372:     */       {
/* 4373:     */         ProcessingWindowInfo localProcessingWindowInfo2;
/* 4374:4911 */         if (!paramBoolean)
/* 4375:     */         {
/* 4376:4913 */           localProcessingWindowInfo2 = (ProcessingWindowInfo)paramLocalFrame.get(0);
/* 4377:     */         }
/* 4378:     */         else
/* 4379:     */         {
/* 4380:4917 */           localObject1 = paramLocalFrame.get(0);
/* 4381:4918 */           localProcessingWindowInfo2 = (ProcessingWindowInfo)ObjectVal.clone(localObject1);
/* 4382:     */         }
/* 4383:4920 */         localObject1 = 
/* 4384:4921 */           paramBPWAdminBean.modProcessingWindow(
/* 4385:4922 */           localProcessingWindowInfo2);
/* 4386:     */         
/* 4387:4924 */         paramLocalFrame.setResult(localObject1);
/* 4388:     */       }
/* 4389:     */       catch (Throwable localThrowable3)
/* 4390:     */       {
/* 4391:4928 */         if ((localThrowable3 instanceof FFSException))
/* 4392:     */         {
/* 4393:4930 */           paramLocalFrame.setException(localThrowable3);
/* 4394:4931 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4395:4932 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable3);
/* 4396:     */         }
/* 4397:4934 */         localThrowable3.printStackTrace(Jaguar.log);
/* 4398:4935 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable3, true);
/* 4399:4936 */         return localThrowable3.getClass().getName();
/* 4400:     */       }
/* 4401:     */     case 54: 
/* 4402:     */       try
/* 4403:     */       {
/* 4404:     */         ProcessingWindowInfo localProcessingWindowInfo3;
/* 4405:4945 */         if (!paramBoolean)
/* 4406:     */         {
/* 4407:4947 */           localProcessingWindowInfo3 = (ProcessingWindowInfo)paramLocalFrame.get(0);
/* 4408:     */         }
/* 4409:     */         else
/* 4410:     */         {
/* 4411:4951 */           localObject1 = paramLocalFrame.get(0);
/* 4412:4952 */           localProcessingWindowInfo3 = (ProcessingWindowInfo)ObjectVal.clone(localObject1);
/* 4413:     */         }
/* 4414:4954 */         localObject1 = 
/* 4415:4955 */           paramBPWAdminBean.delProcessingWindow(
/* 4416:4956 */           localProcessingWindowInfo3);
/* 4417:     */         
/* 4418:4958 */         paramLocalFrame.setResult(localObject1);
/* 4419:     */       }
/* 4420:     */       catch (Throwable localThrowable4)
/* 4421:     */       {
/* 4422:4962 */         if ((localThrowable4 instanceof FFSException))
/* 4423:     */         {
/* 4424:4964 */           paramLocalFrame.setException(localThrowable4);
/* 4425:4965 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4426:4966 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable4);
/* 4427:     */         }
/* 4428:4968 */         localThrowable4.printStackTrace(Jaguar.log);
/* 4429:4969 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable4, true);
/* 4430:4970 */         return localThrowable4.getClass().getName();
/* 4431:     */       }
/* 4432:     */     case 55: 
/* 4433:     */       try
/* 4434:     */       {
/* 4435:     */         ProcessingWindowList localProcessingWindowList;
/* 4436:4979 */         if (!paramBoolean)
/* 4437:     */         {
/* 4438:4981 */           localProcessingWindowList = (ProcessingWindowList)paramLocalFrame.get(0);
/* 4439:     */         }
/* 4440:     */         else
/* 4441:     */         {
/* 4442:4985 */           localObject1 = paramLocalFrame.get(0);
/* 4443:4986 */           localProcessingWindowList = (ProcessingWindowList)ObjectVal.clone(localObject1);
/* 4444:     */         }
/* 4445:4988 */         localObject1 = 
/* 4446:4989 */           paramBPWAdminBean.getProcessingWindows(
/* 4447:4990 */           localProcessingWindowList);
/* 4448:     */         
/* 4449:4992 */         paramLocalFrame.setResult(localObject1);
/* 4450:     */       }
/* 4451:     */       catch (Throwable localThrowable5)
/* 4452:     */       {
/* 4453:4996 */         if ((localThrowable5 instanceof FFSException))
/* 4454:     */         {
/* 4455:4998 */           paramLocalFrame.setException(localThrowable5);
/* 4456:4999 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4457:5000 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable5);
/* 4458:     */         }
/* 4459:5002 */         localThrowable5.printStackTrace(Jaguar.log);
/* 4460:5003 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable5, true);
/* 4461:5004 */         return localThrowable5.getClass().getName();
/* 4462:     */       }
/* 4463:     */     case 56: 
/* 4464:     */       try
/* 4465:     */       {
/* 4466:     */         InstructionType localInstructionType1;
/* 4467:5013 */         if (!paramBoolean)
/* 4468:     */         {
/* 4469:5015 */           localInstructionType1 = (InstructionType)paramLocalFrame.get(0);
/* 4470:     */         }
/* 4471:     */         else
/* 4472:     */         {
/* 4473:5019 */           localObject1 = paramLocalFrame.get(0);
/* 4474:5020 */           localInstructionType1 = (InstructionType)ObjectVal.clone(localObject1);
/* 4475:     */         }
/* 4476:5022 */         localObject1 = 
/* 4477:5023 */           paramBPWAdminBean.getScheduleConfigByCategory(
/* 4478:5024 */           localInstructionType1);
/* 4479:     */         
/* 4480:5026 */         paramLocalFrame.setResult(localObject1);
/* 4481:     */       }
/* 4482:     */       catch (Throwable localThrowable6)
/* 4483:     */       {
/* 4484:5030 */         if ((localThrowable6 instanceof FFSException))
/* 4485:     */         {
/* 4486:5032 */           paramLocalFrame.setException(localThrowable6);
/* 4487:5033 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4488:5034 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable6);
/* 4489:     */         }
/* 4490:5036 */         localThrowable6.printStackTrace(Jaguar.log);
/* 4491:5037 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable6, true);
/* 4492:5038 */         return localThrowable6.getClass().getName();
/* 4493:     */       }
/* 4494:     */     case 57: 
/* 4495:     */       try
/* 4496:     */       {
/* 4497:     */         InstructionType localInstructionType2;
/* 4498:5047 */         if (!paramBoolean)
/* 4499:     */         {
/* 4500:5049 */           localInstructionType2 = (InstructionType)paramLocalFrame.get(0);
/* 4501:     */         }
/* 4502:     */         else
/* 4503:     */         {
/* 4504:5053 */           localObject1 = paramLocalFrame.get(0);
/* 4505:5054 */           localInstructionType2 = (InstructionType)ObjectVal.clone(localObject1);
/* 4506:     */         }
/* 4507:5057 */         paramBPWAdminBean.addScheduleConfig(
/* 4508:5058 */           localInstructionType2);
/* 4509:     */       }
/* 4510:     */       catch (Throwable localThrowable7)
/* 4511:     */       {
/* 4512:5063 */         if ((localThrowable7 instanceof FFSException))
/* 4513:     */         {
/* 4514:5065 */           paramLocalFrame.setException(localThrowable7);
/* 4515:5066 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4516:5067 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable7);
/* 4517:     */         }
/* 4518:5069 */         localThrowable7.printStackTrace(Jaguar.log);
/* 4519:5070 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable7, true);
/* 4520:5071 */         return localThrowable7.getClass().getName();
/* 4521:     */       }
/* 4522:     */     case 58: 
/* 4523:     */       try
/* 4524:     */       {
/* 4525:     */         InstructionType localInstructionType3;
/* 4526:5080 */         if (!paramBoolean)
/* 4527:     */         {
/* 4528:5082 */           localInstructionType3 = (InstructionType)paramLocalFrame.get(0);
/* 4529:     */         }
/* 4530:     */         else
/* 4531:     */         {
/* 4532:5086 */           localObject1 = paramLocalFrame.get(0);
/* 4533:5087 */           localInstructionType3 = (InstructionType)ObjectVal.clone(localObject1);
/* 4534:     */         }
/* 4535:5090 */         paramBPWAdminBean.deleteScheduleConfig(
/* 4536:5091 */           localInstructionType3);
/* 4537:     */       }
/* 4538:     */       catch (Throwable localThrowable8)
/* 4539:     */       {
/* 4540:5096 */         if ((localThrowable8 instanceof FFSException))
/* 4541:     */         {
/* 4542:5098 */           paramLocalFrame.setException(localThrowable8);
/* 4543:5099 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4544:5100 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable8);
/* 4545:     */         }
/* 4546:5102 */         localThrowable8.printStackTrace(Jaguar.log);
/* 4547:5103 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable8, true);
/* 4548:5104 */         return localThrowable8.getClass().getName();
/* 4549:     */       }
/* 4550:     */     case 59: 
/* 4551:     */       try
/* 4552:     */       {
/* 4553:     */         CutOffInfo localCutOffInfo1;
/* 4554:5113 */         if (!paramBoolean)
/* 4555:     */         {
/* 4556:5115 */           localCutOffInfo1 = (CutOffInfo)paramLocalFrame.get(0);
/* 4557:     */         }
/* 4558:     */         else
/* 4559:     */         {
/* 4560:5119 */           localObject1 = paramLocalFrame.get(0);
/* 4561:5120 */           localCutOffInfo1 = (CutOffInfo)ObjectVal.clone(localObject1);
/* 4562:     */         }
/* 4563:5122 */         localObject1 = 
/* 4564:5123 */           paramBPWAdminBean.deleteCutOff(
/* 4565:5124 */           localCutOffInfo1);
/* 4566:     */         
/* 4567:5126 */         paramLocalFrame.setResult(localObject1);
/* 4568:     */       }
/* 4569:     */       catch (Throwable localThrowable9)
/* 4570:     */       {
/* 4571:5130 */         if ((localThrowable9 instanceof FFSException))
/* 4572:     */         {
/* 4573:5132 */           paramLocalFrame.setException(localThrowable9);
/* 4574:5133 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4575:5134 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable9);
/* 4576:     */         }
/* 4577:5136 */         localThrowable9.printStackTrace(Jaguar.log);
/* 4578:5137 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable9, true);
/* 4579:5138 */         return localThrowable9.getClass().getName();
/* 4580:     */       }
/* 4581:     */     case 60: 
/* 4582:     */       try
/* 4583:     */       {
/* 4584:     */         CutOffInfo localCutOffInfo2;
/* 4585:5147 */         if (!paramBoolean)
/* 4586:     */         {
/* 4587:5149 */           localCutOffInfo2 = (CutOffInfo)paramLocalFrame.get(0);
/* 4588:     */         }
/* 4589:     */         else
/* 4590:     */         {
/* 4591:5153 */           localObject1 = paramLocalFrame.get(0);
/* 4592:5154 */           localCutOffInfo2 = (CutOffInfo)ObjectVal.clone(localObject1);
/* 4593:     */         }
/* 4594:5156 */         localObject1 = 
/* 4595:5157 */           paramBPWAdminBean.addCutOff(
/* 4596:5158 */           localCutOffInfo2);
/* 4597:     */         
/* 4598:5160 */         paramLocalFrame.setResult(localObject1);
/* 4599:     */       }
/* 4600:     */       catch (Throwable localThrowable10)
/* 4601:     */       {
/* 4602:5164 */         if ((localThrowable10 instanceof FFSException))
/* 4603:     */         {
/* 4604:5166 */           paramLocalFrame.setException(localThrowable10);
/* 4605:5167 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4606:5168 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable10);
/* 4607:     */         }
/* 4608:5170 */         localThrowable10.printStackTrace(Jaguar.log);
/* 4609:5171 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable10, true);
/* 4610:5172 */         return localThrowable10.getClass().getName();
/* 4611:     */       }
/* 4612:     */     case 61: 
/* 4613:     */       try
/* 4614:     */       {
/* 4615:     */         CutOffInfo localCutOffInfo3;
/* 4616:5181 */         if (!paramBoolean)
/* 4617:     */         {
/* 4618:5183 */           localCutOffInfo3 = (CutOffInfo)paramLocalFrame.get(0);
/* 4619:     */         }
/* 4620:     */         else
/* 4621:     */         {
/* 4622:5187 */           localObject1 = paramLocalFrame.get(0);
/* 4623:5188 */           localCutOffInfo3 = (CutOffInfo)ObjectVal.clone(localObject1);
/* 4624:     */         }
/* 4625:5190 */         localObject1 = 
/* 4626:5191 */           paramBPWAdminBean.modCutOff(
/* 4627:5192 */           localCutOffInfo3);
/* 4628:     */         
/* 4629:5194 */         paramLocalFrame.setResult(localObject1);
/* 4630:     */       }
/* 4631:     */       catch (Throwable localThrowable11)
/* 4632:     */       {
/* 4633:5198 */         if ((localThrowable11 instanceof FFSException))
/* 4634:     */         {
/* 4635:5200 */           paramLocalFrame.setException(localThrowable11);
/* 4636:5201 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4637:5202 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable11);
/* 4638:     */         }
/* 4639:5204 */         localThrowable11.printStackTrace(Jaguar.log);
/* 4640:5205 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable11, true);
/* 4641:5206 */         return localThrowable11.getClass().getName();
/* 4642:     */       }
/* 4643:     */     case 62: 
/* 4644:     */       try
/* 4645:     */       {
/* 4646:     */         CutOffInfo localCutOffInfo4;
/* 4647:5215 */         if (!paramBoolean)
/* 4648:     */         {
/* 4649:5217 */           localCutOffInfo4 = (CutOffInfo)paramLocalFrame.get(0);
/* 4650:     */         }
/* 4651:     */         else
/* 4652:     */         {
/* 4653:5221 */           localObject1 = paramLocalFrame.get(0);
/* 4654:5222 */           localCutOffInfo4 = (CutOffInfo)ObjectVal.clone(localObject1);
/* 4655:     */         }
/* 4656:5224 */         localObject1 = 
/* 4657:5225 */           paramBPWAdminBean.getCutOff(
/* 4658:5226 */           localCutOffInfo4);
/* 4659:     */         
/* 4660:5228 */         paramLocalFrame.setResult(localObject1);
/* 4661:     */       }
/* 4662:     */       catch (Throwable localThrowable12)
/* 4663:     */       {
/* 4664:5232 */         if ((localThrowable12 instanceof FFSException))
/* 4665:     */         {
/* 4666:5234 */           paramLocalFrame.setException(localThrowable12);
/* 4667:5235 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4668:5236 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable12);
/* 4669:     */         }
/* 4670:5238 */         localThrowable12.printStackTrace(Jaguar.log);
/* 4671:5239 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable12, true);
/* 4672:5240 */         return localThrowable12.getClass().getName();
/* 4673:     */       }
/* 4674:     */     case 63: 
/* 4675:     */       try
/* 4676:     */       {
/* 4677:     */         CutOffInfoList localCutOffInfoList;
/* 4678:5249 */         if (!paramBoolean)
/* 4679:     */         {
/* 4680:5251 */           localCutOffInfoList = (CutOffInfoList)paramLocalFrame.get(0);
/* 4681:     */         }
/* 4682:     */         else
/* 4683:     */         {
/* 4684:5255 */           localObject1 = paramLocalFrame.get(0);
/* 4685:5256 */           localCutOffInfoList = (CutOffInfoList)ObjectVal.clone(localObject1);
/* 4686:     */         }
/* 4687:5258 */         localObject1 = 
/* 4688:5259 */           paramBPWAdminBean.getCutOffList(
/* 4689:5260 */           localCutOffInfoList);
/* 4690:     */         
/* 4691:5262 */         paramLocalFrame.setResult(localObject1);
/* 4692:     */       }
/* 4693:     */       catch (Throwable localThrowable13)
/* 4694:     */       {
/* 4695:5266 */         if ((localThrowable13 instanceof FFSException))
/* 4696:     */         {
/* 4697:5268 */           paramLocalFrame.setException(localThrowable13);
/* 4698:5269 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4699:5270 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable13);
/* 4700:     */         }
/* 4701:5272 */         localThrowable13.printStackTrace(Jaguar.log);
/* 4702:5273 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable13, true);
/* 4703:5274 */         return localThrowable13.getClass().getName();
/* 4704:     */       }
/* 4705:     */     case 64: 
/* 4706:     */       try
/* 4707:     */       {
/* 4708:5283 */         String str1 = (String)paramLocalFrame.get(0);
/* 4709:     */         
/* 4710:5285 */         localObject1 = (String)paramLocalFrame.get(1);
/* 4711:5286 */         localObject2 = paramBPWAdminBean
/* 4712:5287 */           .getScheduleCategoryInfo(
/* 4713:5288 */           str1, 
/* 4714:5289 */           (String)localObject1);
/* 4715:     */         
/* 4716:5291 */         paramLocalFrame.setResult(localObject2);
/* 4717:     */       }
/* 4718:     */       catch (Throwable localThrowable14)
/* 4719:     */       {
/* 4720:5295 */         if ((localThrowable14 instanceof FFSException))
/* 4721:     */         {
/* 4722:5297 */           paramLocalFrame.setException(localThrowable14);
/* 4723:5298 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4724:5299 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable14);
/* 4725:     */         }
/* 4726:5301 */         localThrowable14.printStackTrace(Jaguar.log);
/* 4727:5302 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable14, true);
/* 4728:5303 */         return localThrowable14.getClass().getName();
/* 4729:     */       }
/* 4730:     */     case 65: 
/* 4731:     */       try
/* 4732:     */       {
/* 4733:     */         ScheduleCategoryInfo localScheduleCategoryInfo;
/* 4734:5312 */         if (!paramBoolean)
/* 4735:     */         {
/* 4736:5314 */           localScheduleCategoryInfo = (ScheduleCategoryInfo)paramLocalFrame.get(0);
/* 4737:     */         }
/* 4738:     */         else
/* 4739:     */         {
/* 4740:5318 */           localObject1 = paramLocalFrame.get(0);
/* 4741:5319 */           localScheduleCategoryInfo = (ScheduleCategoryInfo)ObjectVal.clone(localObject1);
/* 4742:     */         }
/* 4743:5321 */         localObject1 = 
/* 4744:5322 */           paramBPWAdminBean.modScheduleCategoryInfo(
/* 4745:5323 */           localScheduleCategoryInfo);
/* 4746:     */         
/* 4747:5325 */         paramLocalFrame.setResult(localObject1);
/* 4748:     */       }
/* 4749:     */       catch (Throwable localThrowable15)
/* 4750:     */       {
/* 4751:5329 */         if ((localThrowable15 instanceof FFSException))
/* 4752:     */         {
/* 4753:5331 */           paramLocalFrame.setException(localThrowable15);
/* 4754:5332 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4755:5333 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable15);
/* 4756:     */         }
/* 4757:5335 */         localThrowable15.printStackTrace(Jaguar.log);
/* 4758:5336 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable15, true);
/* 4759:5337 */         return localThrowable15.getClass().getName();
/* 4760:     */       }
/* 4761:     */     case 66: 
/* 4762:     */       try
/* 4763:     */       {
/* 4764:     */         CutOffActivityInfoList localCutOffActivityInfoList;
/* 4765:5346 */         if (!paramBoolean)
/* 4766:     */         {
/* 4767:5348 */           localCutOffActivityInfoList = (CutOffActivityInfoList)paramLocalFrame.get(0);
/* 4768:     */         }
/* 4769:     */         else
/* 4770:     */         {
/* 4771:5352 */           localObject1 = paramLocalFrame.get(0);
/* 4772:5353 */           localCutOffActivityInfoList = (CutOffActivityInfoList)ObjectVal.clone(localObject1);
/* 4773:     */         }
/* 4774:5355 */         localObject1 = 
/* 4775:5356 */           paramBPWAdminBean.getCutOffActivityList(
/* 4776:5357 */           localCutOffActivityInfoList);
/* 4777:     */         
/* 4778:5359 */         paramLocalFrame.setResult(localObject1);
/* 4779:     */       }
/* 4780:     */       catch (Throwable localThrowable16)
/* 4781:     */       {
/* 4782:5363 */         if ((localThrowable16 instanceof FFSException))
/* 4783:     */         {
/* 4784:5365 */           paramLocalFrame.setException(localThrowable16);
/* 4785:5366 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4786:5367 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable16);
/* 4787:     */         }
/* 4788:5369 */         localThrowable16.printStackTrace(Jaguar.log);
/* 4789:5370 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable16, true);
/* 4790:5371 */         return localThrowable16.getClass().getName();
/* 4791:     */       }
/* 4792:     */     case 67: 
/* 4793:     */       try
/* 4794:     */       {
/* 4795:     */         ScheduleActivityList localScheduleActivityList;
/* 4796:5380 */         if (!paramBoolean)
/* 4797:     */         {
/* 4798:5382 */           localScheduleActivityList = (ScheduleActivityList)paramLocalFrame.get(0);
/* 4799:     */         }
/* 4800:     */         else
/* 4801:     */         {
/* 4802:5386 */           localObject1 = paramLocalFrame.get(0);
/* 4803:5387 */           localScheduleActivityList = (ScheduleActivityList)ObjectVal.clone(localObject1);
/* 4804:     */         }
/* 4805:5389 */         localObject1 = 
/* 4806:5390 */           paramBPWAdminBean.getScheduleActivityList(
/* 4807:5391 */           localScheduleActivityList);
/* 4808:     */         
/* 4809:5393 */         paramLocalFrame.setResult(localObject1);
/* 4810:     */       }
/* 4811:     */       catch (Throwable localThrowable17)
/* 4812:     */       {
/* 4813:5397 */         if ((localThrowable17 instanceof FFSException))
/* 4814:     */         {
/* 4815:5399 */           paramLocalFrame.setException(localThrowable17);
/* 4816:5400 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4817:5401 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable17);
/* 4818:     */         }
/* 4819:5403 */         localThrowable17.printStackTrace(Jaguar.log);
/* 4820:5404 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable17, true);
/* 4821:5405 */         return localThrowable17.getClass().getName();
/* 4822:     */       }
/* 4823:     */     case 68: 
/* 4824:     */       try
/* 4825:     */       {
/* 4826:5414 */         String str2 = (String)paramLocalFrame.get(0);
/* 4827:     */         
/* 4828:5416 */         localObject1 = (String)paramLocalFrame.get(1);
/* 4829:     */         
/* 4830:5418 */         localObject2 = (String)paramLocalFrame.get(2);
/* 4831:     */         
/* 4832:5420 */         str4 = (String)paramLocalFrame.get(3);
/* 4833:5421 */         paramBPWAdminBean
/* 4834:5422 */           .rerunCutOff(
/* 4835:5423 */           str2, 
/* 4836:5424 */           (String)localObject1, 
/* 4837:5425 */           (String)localObject2, 
/* 4838:5426 */           str4);
/* 4839:     */       }
/* 4840:     */       catch (Throwable localThrowable18)
/* 4841:     */       {
/* 4842:5431 */         if ((localThrowable18 instanceof FFSException))
/* 4843:     */         {
/* 4844:5433 */           paramLocalFrame.setException(localThrowable18);
/* 4845:5434 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4846:5435 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable18);
/* 4847:     */         }
/* 4848:5437 */         localThrowable18.printStackTrace(Jaguar.log);
/* 4849:5438 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable18, true);
/* 4850:5439 */         return localThrowable18.getClass().getName();
/* 4851:     */       }
/* 4852:     */     case 69: 
/* 4853:     */       try
/* 4854:     */       {
/* 4855:5448 */         String str3 = (String)paramLocalFrame.get(0);
/* 4856:     */         
/* 4857:5450 */         localObject1 = (String)paramLocalFrame.get(1);
/* 4858:     */         
/* 4859:5452 */         localObject2 = (String)paramLocalFrame.get(2);
/* 4860:5453 */         str4 = paramBPWAdminBean
/* 4861:5454 */           .getGeneratedFileName(
/* 4862:5455 */           str3, 
/* 4863:5456 */           (String)localObject1, 
/* 4864:5457 */           (String)localObject2);
/* 4865:     */         
/* 4866:5459 */         paramLocalFrame.setResult(str4);
/* 4867:     */       }
/* 4868:     */       catch (Throwable localThrowable19)
/* 4869:     */       {
/* 4870:5463 */         if ((localThrowable19 instanceof FFSException))
/* 4871:     */         {
/* 4872:5465 */           paramLocalFrame.setException(localThrowable19);
/* 4873:5466 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4874:5467 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable19);
/* 4875:     */         }
/* 4876:5469 */         localThrowable19.printStackTrace(Jaguar.log);
/* 4877:5470 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable19, true);
/* 4878:5471 */         return localThrowable19.getClass().getName();
/* 4879:     */       }
/* 4880:     */     case 70: 
/* 4881:     */       try
/* 4882:     */       {
/* 4883:     */         SmartCalendarFile localSmartCalendarFile1;
/* 4884:5480 */         if (!paramBoolean)
/* 4885:     */         {
/* 4886:5482 */           localSmartCalendarFile1 = (SmartCalendarFile)paramLocalFrame.get(0);
/* 4887:     */         }
/* 4888:     */         else
/* 4889:     */         {
/* 4890:5486 */           localObject1 = paramLocalFrame.get(0);
/* 4891:5487 */           localSmartCalendarFile1 = (SmartCalendarFile)ObjectVal.clone(localObject1);
/* 4892:     */         }
/* 4893:5489 */         localObject1 = 
/* 4894:5490 */           paramBPWAdminBean.importCalendar(
/* 4895:5491 */           localSmartCalendarFile1);
/* 4896:     */         
/* 4897:5493 */         paramLocalFrame.setResult(localObject1);
/* 4898:     */       }
/* 4899:     */       catch (Throwable localThrowable20)
/* 4900:     */       {
/* 4901:5497 */         if ((localThrowable20 instanceof FFSException))
/* 4902:     */         {
/* 4903:5499 */           paramLocalFrame.setException(localThrowable20);
/* 4904:5500 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4905:5501 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable20);
/* 4906:     */         }
/* 4907:5503 */         localThrowable20.printStackTrace(Jaguar.log);
/* 4908:5504 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable20, true);
/* 4909:5505 */         return localThrowable20.getClass().getName();
/* 4910:     */       }
/* 4911:     */     case 71: 
/* 4912:     */       try
/* 4913:     */       {
/* 4914:     */         SmartCalendarFile localSmartCalendarFile2;
/* 4915:5514 */         if (!paramBoolean)
/* 4916:     */         {
/* 4917:5516 */           localSmartCalendarFile2 = (SmartCalendarFile)paramLocalFrame.get(0);
/* 4918:     */         }
/* 4919:     */         else
/* 4920:     */         {
/* 4921:5520 */           localObject1 = paramLocalFrame.get(0);
/* 4922:5521 */           localSmartCalendarFile2 = (SmartCalendarFile)ObjectVal.clone(localObject1);
/* 4923:     */         }
/* 4924:5523 */         localObject1 = 
/* 4925:5524 */           paramBPWAdminBean.exportCalendar(
/* 4926:5525 */           localSmartCalendarFile2);
/* 4927:     */         
/* 4928:5527 */         paramLocalFrame.setResult(localObject1);
/* 4929:     */       }
/* 4930:     */       catch (Throwable localThrowable21)
/* 4931:     */       {
/* 4932:5531 */         if ((localThrowable21 instanceof FFSException))
/* 4933:     */         {
/* 4934:5533 */           paramLocalFrame.setException(localThrowable21);
/* 4935:5534 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4936:5535 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable21);
/* 4937:     */         }
/* 4938:5537 */         localThrowable21.printStackTrace(Jaguar.log);
/* 4939:5538 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable21, true);
/* 4940:5539 */         return localThrowable21.getClass().getName();
/* 4941:     */       }
/* 4942:     */     case 72: 
/* 4943:     */       try
/* 4944:     */       {
/* 4945:     */         PayeeInfo localPayeeInfo1;
/* 4946:5548 */         if (!paramBoolean)
/* 4947:     */         {
/* 4948:5550 */           localPayeeInfo1 = (PayeeInfo)paramLocalFrame.get(0);
/* 4949:     */         }
/* 4950:     */         else
/* 4951:     */         {
/* 4952:5554 */           localObject1 = paramLocalFrame.get(0);
/* 4953:5555 */           localPayeeInfo1 = (PayeeInfo)ObjectVal.clone(localObject1);
/* 4954:     */         }
/* 4955:5557 */         localObject1 = 
/* 4956:5558 */           paramBPWAdminBean.addGlobalPayee(
/* 4957:5559 */           localPayeeInfo1);
/* 4958:     */         
/* 4959:5561 */         paramLocalFrame.setResult(localObject1);
/* 4960:     */       }
/* 4961:     */       catch (Throwable localThrowable22)
/* 4962:     */       {
/* 4963:5565 */         if ((localThrowable22 instanceof FFSException))
/* 4964:     */         {
/* 4965:5567 */           paramLocalFrame.setException(localThrowable22);
/* 4966:5568 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4967:5569 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable22);
/* 4968:     */         }
/* 4969:5571 */         localThrowable22.printStackTrace(Jaguar.log);
/* 4970:5572 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable22, true);
/* 4971:5573 */         return localThrowable22.getClass().getName();
/* 4972:     */       }
/* 4973:     */     case 73: 
/* 4974:     */       try
/* 4975:     */       {
/* 4976:     */         PayeeInfo localPayeeInfo2;
/* 4977:5582 */         if (!paramBoolean)
/* 4978:     */         {
/* 4979:5584 */           localPayeeInfo2 = (PayeeInfo)paramLocalFrame.get(0);
/* 4980:     */         }
/* 4981:     */         else
/* 4982:     */         {
/* 4983:5588 */           localObject1 = paramLocalFrame.get(0);
/* 4984:5589 */           localPayeeInfo2 = (PayeeInfo)ObjectVal.clone(localObject1);
/* 4985:     */         }
/* 4986:5591 */         localObject1 = 
/* 4987:5592 */           paramBPWAdminBean.deleteGlobalPayee(
/* 4988:5593 */           localPayeeInfo2);
/* 4989:     */         
/* 4990:5595 */         paramLocalFrame.setResult(localObject1);
/* 4991:     */       }
/* 4992:     */       catch (Throwable localThrowable23)
/* 4993:     */       {
/* 4994:5599 */         if ((localThrowable23 instanceof FFSException))
/* 4995:     */         {
/* 4996:5601 */           paramLocalFrame.setException(localThrowable23);
/* 4997:5602 */           paramOutputStream.write_string("IDL:com/ffusion/ffs/interfaces/FFSException:1.0");
/* 4998:5603 */           return UserException.toString("IDL:com/ffusion/ffs/interfaces/FFSException:1.0", localThrowable23);
/* 4999:     */         }
/* 5000:5605 */         localThrowable23.printStackTrace(Jaguar.log);
/* 5001:5606 */         Connection.insertExToServiceContext(paramOutputStream, localThrowable23, true);
/* 5002:5607 */         return localThrowable23.getClass().getName();
/* 5003:     */       }
/* 5004:     */     }
/* 5005:5612 */     return null;
/* 5006:     */   }
/* 5007:     */ }


/* Location:           D:\drops\jd\jars\BPWAdmin.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB._sk_BPWAdmin_BPWAdminBean
 * JD-Core Version:    0.7.0.1
 */