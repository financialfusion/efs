/*    1:     */ package com.ffusion.ffs.bpw.adminEJB;
/*    2:     */ 
/*    3:     */ import com.ffusion.ffs.bpw.interfaces.BPWStatistics;
/*    4:     */ import com.ffusion.ffs.bpw.interfaces.CutOffActivityInfoList;
/*    5:     */ import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
/*    6:     */ import com.ffusion.ffs.bpw.interfaces.CutOffInfoList;
/*    7:     */ import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
/*    8:     */ import com.ffusion.ffs.bpw.interfaces.InstructionType;
/*    9:     */ import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
/*   10:     */ import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
/*   11:     */ import com.ffusion.ffs.bpw.interfaces.ProcessingWindowInfo;
/*   12:     */ import com.ffusion.ffs.bpw.interfaces.ProcessingWindowList;
/*   13:     */ import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
/*   14:     */ import com.ffusion.ffs.bpw.interfaces.ScheduleActivityList;
/*   15:     */ import com.ffusion.ffs.bpw.interfaces.ScheduleCategoryInfo;
/*   16:     */ import com.ffusion.ffs.bpw.interfaces.ScheduleHist;
/*   17:     */ import com.ffusion.ffs.bpw.interfaces.SchedulerInfo;
/*   18:     */ import com.ffusion.ffs.bpw.interfaces.SmartCalendarFile;
/*   19:     */ import com.ffusion.ffs.db.FFSDBProperties;
/*   20:     */ import com.ffusion.ffs.interfaces.FFSException;
/*   21:     */ import com.ffusion.ffs.util.FFSProperties;
/*   22:     */ import com.ibm.ejs.container.EJSWrapper;
/*   23:     */ import java.io.Serializable;
/*   24:     */ import java.rmi.Remote;
/*   25:     */ import java.util.ArrayList;
/*   26:     */ import java.util.HashMap;
/*   27:     */ import javax.ejb.EJBHome;
/*   28:     */ import javax.ejb.EJBObject;
/*   29:     */ import javax.ejb.Handle;
/*   30:     */ import javax.ejb.RemoveException;
/*   31:     */ import javax.rmi.CORBA.Tie;
/*   32:     */ import javax.rmi.CORBA.Util;
/*   33:     */ import org.omg.CORBA.BAD_OPERATION;
/*   34:     */ import org.omg.CORBA.ORB;
/*   35:     */ import org.omg.CORBA.SystemException;
/*   36:     */ import org.omg.CORBA.portable.Delegate;
/*   37:     */ import org.omg.CORBA.portable.ResponseHandler;
/*   38:     */ import org.omg.CORBA.portable.UnknownException;
/*   39:     */ 
/*   40:     */ public class _EJSRemoteStatelessIBPWAdmin_Tie
/*   41:     */   extends org.omg.CORBA_2_3.portable.ObjectImpl
/*   42:     */   implements Tie
/*   43:     */ {
/*   44:  49 */   private EJSRemoteStatelessIBPWAdmin target = null;
/*   45:  50 */   private ORB orb = null;
/*   46:  52 */   private static final String[] _type_ids = {
/*   47:  53 */     "RMI:com.ffusion.ffs.bpw.adminEJB.IBPWAdmin:0000000000000000", 
/*   48:  54 */     "RMI:javax.ejb.EJBObject:0000000000000000", 
/*   49:  55 */     "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
/*   50:  56 */     "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000" };
/*   51:     */   
/*   52:     */   public void setTarget(Remote paramRemote)
/*   53:     */   {
/*   54:  60 */     this.target = ((EJSRemoteStatelessIBPWAdmin)paramRemote);
/*   55:     */   }
/*   56:     */   
/*   57:     */   public Remote getTarget()
/*   58:     */   {
/*   59:  64 */     return this.target;
/*   60:     */   }
/*   61:     */   
/*   62:     */   public org.omg.CORBA.Object thisObject()
/*   63:     */   {
/*   64:  68 */     return this;
/*   65:     */   }
/*   66:     */   
/*   67:     */   public void deactivate()
/*   68:     */   {
/*   69:  72 */     if (this.orb != null)
/*   70:     */     {
/*   71:  73 */       this.orb.disconnect(this);
/*   72:  74 */       _set_delegate(null);
/*   73:     */     }
/*   74:     */   }
/*   75:     */   
/*   76:     */   public ORB orb()
/*   77:     */   {
/*   78:  79 */     return _orb();
/*   79:     */   }
/*   80:     */   
/*   81:     */   public void orb(ORB paramORB)
/*   82:     */   {
/*   83:  83 */     paramORB.connect(this);
/*   84:     */   }
/*   85:     */   
/*   86:     */   public void _set_delegate(Delegate paramDelegate)
/*   87:     */   {
/*   88:  87 */     super._set_delegate(paramDelegate);
/*   89:  88 */     if (paramDelegate != null) {
/*   90:  89 */       this.orb = _orb();
/*   91:     */     } else {
/*   92:  91 */       this.orb = null;
/*   93:     */     }
/*   94:     */   }
/*   95:     */   
/*   96:     */   public String[] _ids()
/*   97:     */   {
/*   98:  95 */     return _type_ids;
/*   99:     */   }
/*  100:     */   
/*  101:     */   public org.omg.CORBA.portable.OutputStream _invoke(String paramString, org.omg.CORBA.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  102:     */     throws SystemException
/*  103:     */   {
/*  104:     */     try
/*  105:     */     {
/*  106: 100 */       org.omg.CORBA_2_3.portable.InputStream localInputStream = 
/*  107: 101 */         (org.omg.CORBA_2_3.portable.InputStream)paramInputStream;
/*  108: 102 */       switch (paramString.hashCode())
/*  109:     */       {
/*  110:     */       case -2108228870: 
/*  111: 104 */         if (paramString.equals("registerPropertyConfig")) {
/*  112: 105 */           return registerPropertyConfig(localInputStream, paramResponseHandler);
/*  113:     */         }
/*  114:     */       case -2102723869: 
/*  115: 108 */         if (paramString.equals("getGlobalPayeeGroups")) {
/*  116: 109 */           return getGlobalPayeeGroups(localInputStream, paramResponseHandler);
/*  117:     */         }
/*  118:     */       case -2004821191: 
/*  119: 112 */         if (paramString.equals("searchGlobalPayees__CORBA_WStringValue")) {
/*  120: 113 */           return searchGlobalPayees__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  121:     */         }
/*  122:     */       case -1995615012: 
/*  123: 116 */         if (paramString.equals("updateGlobalPayee")) {
/*  124: 117 */           return updateGlobalPayee(localInputStream, paramResponseHandler);
/*  125:     */         }
/*  126:     */       case -1989100345: 
/*  127: 120 */         if (paramString.equals("cleanup__CORBA_WStringValue__java_util_ArrayList__java_util_ArrayList__java_util_HashMap")) {
/*  128: 121 */           return cleanup__CORBA_WStringValue__java_util_ArrayList__java_util_ArrayList__java_util_HashMap(localInputStream, paramResponseHandler);
/*  129:     */         }
/*  130:     */       case -1964120606: 
/*  131: 124 */         if (paramString.equals("updateScheduleConfig")) {
/*  132: 125 */           return updateScheduleConfig(localInputStream, paramResponseHandler);
/*  133:     */         }
/*  134:     */       case -1797812928: 
/*  135: 128 */         if (paramString.equals("startLimitCheckApprovalProcessor")) {
/*  136: 129 */           return startLimitCheckApprovalProcessor(localInputStream, paramResponseHandler);
/*  137:     */         }
/*  138:     */       case -1755749148: 
/*  139: 132 */         if (paramString.equals("stopEngine")) {
/*  140: 133 */           return stopEngine(localInputStream, paramResponseHandler);
/*  141:     */         }
/*  142:     */       case -1751355634: 
/*  143: 136 */         if (paramString.equals("delProcessingWindow")) {
/*  144: 137 */           return delProcessingWindow(localInputStream, paramResponseHandler);
/*  145:     */         }
/*  146:     */       case -1661176564: 
/*  147: 140 */         if (paramString.equals("refreshSmartCalendar")) {
/*  148: 141 */           return refreshSmartCalendar(localInputStream, paramResponseHandler);
/*  149:     */         }
/*  150:     */       case -1550521068: 
/*  151: 144 */         if (paramString.equals("_get_EJBHome")) {
/*  152: 145 */           return _get_EJBHome(localInputStream, paramResponseHandler);
/*  153:     */         }
/*  154:     */       case -1546763590: 
/*  155: 148 */         if (paramString.equals("updateScheduleRunTimeConfig")) {
/*  156: 149 */           return updateScheduleRunTimeConfig(localInputStream, paramResponseHandler);
/*  157:     */         }
/*  158:     */       case -1515908137: 
/*  159: 152 */         if (paramString.equals("getPayeeRoute")) {
/*  160: 153 */           return getPayeeRoute(localInputStream, paramResponseHandler);
/*  161:     */         }
/*  162:     */       case -1506650929: 
/*  163: 156 */         if (paramString.equals("getScheduleHist")) {
/*  164: 157 */           return getScheduleHist(localInputStream, paramResponseHandler);
/*  165:     */         }
/*  166:     */       case -1496165329: 
/*  167: 160 */         if (paramString.equals("getGlobalPayee")) {
/*  168: 161 */           return getGlobalPayee(localInputStream, paramResponseHandler);
/*  169:     */         }
/*  170:     */       case -1486987399: 
/*  171: 164 */         if (paramString.equals("getScheduleCategoryInfo")) {
/*  172: 165 */           return getScheduleCategoryInfo(localInputStream, paramResponseHandler);
/*  173:     */         }
/*  174:     */       case -1395539968: 
/*  175: 168 */         if (paramString.equals("runBatchProcess")) {
/*  176: 169 */           return runBatchProcess(localInputStream, paramResponseHandler);
/*  177:     */         }
/*  178:     */       case -1381820685: 
/*  179: 172 */         if (paramString.equals("setDebugLevel")) {
/*  180: 173 */           return setDebugLevel(localInputStream, paramResponseHandler);
/*  181:     */         }
/*  182:     */       case -1295821792: 
/*  183: 176 */         if (paramString.equals("refreshScheduler")) {
/*  184: 177 */           return refreshScheduler(localInputStream, paramResponseHandler);
/*  185:     */         }
/*  186:     */       case -1250228441: 
/*  187: 180 */         if (paramString.equals("addPayee")) {
/*  188: 181 */           return addPayee(localInputStream, paramResponseHandler);
/*  189:     */         }
/*  190:     */       case -1217152680: 
/*  191: 184 */         if (paramString.equals("deleteCutOff")) {
/*  192: 185 */           return deleteCutOff(localInputStream, paramResponseHandler);
/*  193:     */         }
/*  194:     */       case -1173160124: 
/*  195: 188 */         if (paramString.equals("deleteScheduleConfig")) {
/*  196: 189 */           return deleteScheduleConfig(localInputStream, paramResponseHandler);
/*  197:     */         }
/*  198:     */       case -1143218267: 
/*  199: 192 */         if (paramString.equals("refresh__com_ffusion_ffs_util_FFSProperties__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType")) {
/*  200: 193 */           return refresh__com_ffusion_ffs_util_FFSProperties__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType(localInputStream, paramResponseHandler);
/*  201:     */         }
/*  202:     */       case -1136226211: 
/*  203: 196 */         if (paramString.equals("deletePayee")) {
/*  204: 197 */           return deletePayee(localInputStream, paramResponseHandler);
/*  205:     */         }
/*  206:     */       case -1109191813: 
/*  207: 200 */         if (paramString.equals("cleanup__java_util_ArrayList__java_util_ArrayList__java_util_ArrayList__java_util_HashMap")) {
/*  208: 201 */           return cleanup__java_util_ArrayList__java_util_ArrayList__java_util_ArrayList__java_util_HashMap(localInputStream, paramResponseHandler);
/*  209:     */         }
/*  210:     */       case -1011244123: 
/*  211: 204 */         if (paramString.equals("_get_primaryKey")) {
/*  212: 205 */           return _get_primaryKey(localInputStream, paramResponseHandler);
/*  213:     */         }
/*  214:     */       case -975628863: 
/*  215: 208 */         if (paramString.equals("getAllFulfillmentInfo")) {
/*  216: 209 */           return getAllFulfillmentInfo(localInputStream, paramResponseHandler);
/*  217:     */         }
/*  218:     */       case -943882478: 
/*  219: 212 */         if (paramString.equals("exportCalendar")) {
/*  220: 213 */           return exportCalendar(localInputStream, paramResponseHandler);
/*  221:     */         }
/*  222:     */       case -934610812: 
/*  223: 216 */         if (paramString.equals("remove")) {
/*  224: 217 */           return remove(localInputStream, paramResponseHandler);
/*  225:     */         }
/*  226:     */       case -893383848: 
/*  227: 220 */         if (paramString.equals("searchGlobalPayees__com_ffusion_ffs_bpw_interfaces_PayeeInfo__long")) {
/*  228: 221 */           return searchGlobalPayees__com_ffusion_ffs_bpw_interfaces_PayeeInfo__long(localInputStream, paramResponseHandler);
/*  229:     */         }
/*  230:     */       case -781704107: 
/*  231: 224 */         if (paramString.equals("resubmitEvent__CORBA_WStringValue__CORBA_WStringValue")) {
/*  232: 225 */           return resubmitEvent__CORBA_WStringValue__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  233:     */         }
/*  234:     */       case -757889350: 
/*  235: 228 */         if (paramString.equals("deleteGlobalPayee")) {
/*  236: 229 */           return deleteGlobalPayee(localInputStream, paramResponseHandler);
/*  237:     */         }
/*  238:     */       case -600708827: 
/*  239: 232 */         if (paramString.equals("modScheduleCategoryInfo")) {
/*  240: 233 */           return modScheduleCategoryInfo(localInputStream, paramResponseHandler);
/*  241:     */         }
/*  242:     */       case -587849729: 
/*  243: 236 */         if (paramString.equals("updatePayee")) {
/*  244: 237 */           return updatePayee(localInputStream, paramResponseHandler);
/*  245:     */         }
/*  246:     */       case -515052935: 
/*  247: 240 */         if (paramString.equals("startScheduler")) {
/*  248: 241 */           return startScheduler(localInputStream, paramResponseHandler);
/*  249:     */         }
/*  250:     */       case -457410652: 
/*  251: 244 */         if (paramString.equals("addProcessingWindow")) {
/*  252: 245 */           return addProcessingWindow(localInputStream, paramResponseHandler);
/*  253:     */         }
/*  254:     */       case -456254514: 
/*  255: 248 */         if (paramString.equals("addCutOff")) {
/*  256: 249 */           return addCutOff(localInputStream, paramResponseHandler);
/*  257:     */         }
/*  258:     */       case -392337127: 
/*  259: 252 */         if (paramString.equals("getStatistics")) {
/*  260: 253 */           return getStatistics(localInputStream, paramResponseHandler);
/*  261:     */         }
/*  262:     */       case -370820120: 
/*  263: 256 */         if (paramString.equals("cleanup__CORBA_WStringValue__CORBA_WStringValue__long__java_util_HashMap")) {
/*  264: 257 */           return cleanup__CORBA_WStringValue__CORBA_WStringValue__long__java_util_HashMap(localInputStream, paramResponseHandler);
/*  265:     */         }
/*  266:     */       case -284331782: 
/*  267: 260 */         if (paramString.equals("getScheduleActivityList")) {
/*  268: 261 */           return getScheduleActivityList(localInputStream, paramResponseHandler);
/*  269:     */         }
/*  270:     */       case -237936939: 
/*  271: 264 */         if (paramString.equals("updateScheduleProcessingConfig")) {
/*  272: 265 */           return updateScheduleProcessingConfig(localInputStream, paramResponseHandler);
/*  273:     */         }
/*  274:     */       case -235224205: 
/*  275: 268 */         if (paramString.equals("getSchedulerInfo__")) {
/*  276: 269 */           return getSchedulerInfo__(localInputStream, paramResponseHandler);
/*  277:     */         }
/*  278:     */       case -234734076: 
/*  279: 272 */         if (paramString.equals("addGlobalPayee")) {
/*  280: 273 */           return addGlobalPayee(localInputStream, paramResponseHandler);
/*  281:     */         }
/*  282:     */       case -123137026: 
/*  283: 276 */         if (paramString.equals("start__com_ffusion_ffs_util_FFSProperties__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType")) {
/*  284: 277 */           return start__com_ffusion_ffs_util_FFSProperties__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType(localInputStream, paramResponseHandler);
/*  285:     */         }
/*  286:     */       case -84032507: 
/*  287: 280 */         if (paramString.equals("modProcessingWindow")) {
/*  288: 281 */           return modProcessingWindow(localInputStream, paramResponseHandler);
/*  289:     */         }
/*  290:     */       case -74077855: 
/*  291: 284 */         if (paramString.equals("findPayeeByID")) {
/*  292: 285 */           return findPayeeByID(localInputStream, paramResponseHandler);
/*  293:     */         }
/*  294:     */       case 3441010: 
/*  295: 288 */         if (paramString.equals("ping")) {
/*  296: 289 */           return ping(localInputStream, paramResponseHandler);
/*  297:     */         }
/*  298:     */       case 3540994: 
/*  299: 292 */         if (paramString.equals("stop")) {
/*  300: 293 */           return stop(localInputStream, paramResponseHandler);
/*  301:     */         }
/*  302:     */       case 105907295: 
/*  303: 296 */         if (paramString.equals("getHeapUsage")) {
/*  304: 297 */           return getHeapUsage(localInputStream, paramResponseHandler);
/*  305:     */         }
/*  306:     */       case 117675355: 
/*  307: 300 */         if (paramString.equals("resubmitEvent__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue")) {
/*  308: 301 */           return resubmitEvent__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  309:     */         }
/*  310:     */       case 242314246: 
/*  311: 304 */         if (paramString.equals("start__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType")) {
/*  312: 305 */           return start__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType(localInputStream, paramResponseHandler);
/*  313:     */         }
/*  314:     */       case 261316099: 
/*  315: 308 */         if (paramString.equals("importCalendar")) {
/*  316: 309 */           return importCalendar(localInputStream, paramResponseHandler);
/*  317:     */         }
/*  318:     */       case 346920643: 
/*  319: 312 */         if (paramString.equals("getCutOff")) {
/*  320: 313 */           return getCutOff(localInputStream, paramResponseHandler);
/*  321:     */         }
/*  322:     */       case 355507663: 
/*  323: 316 */         if (paramString.equals("getScheduleConfig__")) {
/*  324: 317 */           return getScheduleConfig__(localInputStream, paramResponseHandler);
/*  325:     */         }
/*  326:     */       case 433040275: 
/*  327: 320 */         if (paramString.equals("getFreeMem")) {
/*  328: 321 */           return getFreeMem(localInputStream, paramResponseHandler);
/*  329:     */         }
/*  330:     */       case 534709664: 
/*  331: 324 */         if (paramString.equals("stopLimitCheckApprovalProcessor")) {
/*  332: 325 */           return stopLimitCheckApprovalProcessor(localInputStream, paramResponseHandler);
/*  333:     */         }
/*  334:     */       case 547280075: 
/*  335: 328 */         if (paramString.equals("addFulfillmentInfo")) {
/*  336: 329 */           return addFulfillmentInfo(localInputStream, paramResponseHandler);
/*  337:     */         }
/*  338:     */       case 702578477: 
/*  339: 332 */         if (paramString.equals("refresh__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType")) {
/*  340: 333 */           return refresh__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType(localInputStream, paramResponseHandler);
/*  341:     */         }
/*  342:     */       case 706213252: 
/*  343: 336 */         if (paramString.equals("getScheduleConfigByCategory")) {
/*  344: 337 */           return getScheduleConfigByCategory(localInputStream, paramResponseHandler);
/*  345:     */         }
/*  346:     */       case 831574657: 
/*  347: 340 */         if (paramString.equals("deleteFulfillmentInfo")) {
/*  348: 341 */           return deleteFulfillmentInfo(localInputStream, paramResponseHandler);
/*  349:     */         }
/*  350:     */       case 929433402: 
/*  351: 344 */         if (paramString.equals("getProcessingWindows")) {
/*  352: 345 */           return getProcessingWindows(localInputStream, paramResponseHandler);
/*  353:     */         }
/*  354:     */       case 987778080: 
/*  355: 348 */         if (paramString.equals("getGeneratedFileName")) {
/*  356: 349 */           return getGeneratedFileName(localInputStream, paramResponseHandler);
/*  357:     */         }
/*  358:     */       case 1121100289: 
/*  359: 352 */         if (paramString.equals("getCutOffList")) {
/*  360: 353 */           return getCutOffList(localInputStream, paramResponseHandler);
/*  361:     */         }
/*  362:     */       case 1169770076: 
/*  363: 356 */         if (paramString.equals("getFulfillmentSystems")) {
/*  364: 357 */           return getFulfillmentSystems(localInputStream, paramResponseHandler);
/*  365:     */         }
/*  366:     */       case 1227397977: 
/*  367: 360 */         if (paramString.equals("getSchedulerInfo__CORBA_WStringValue__CORBA_WStringValue")) {
/*  368: 361 */           return getSchedulerInfo__CORBA_WStringValue__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  369:     */         }
/*  370:     */       case 1227987303: 
/*  371: 364 */         if (paramString.equals("getTotalMem")) {
/*  372: 365 */           return getTotalMem(localInputStream, paramResponseHandler);
/*  373:     */         }
/*  374:     */       case 1242545304: 
/*  375: 368 */         if (paramString.equals("resubmitEvent__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue")) {
/*  376: 369 */           return resubmitEvent__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  377:     */         }
/*  378:     */       case 1264756395: 
/*  379: 372 */         if (paramString.equals("isIdentical")) {
/*  380: 373 */           return isIdentical(localInputStream, paramResponseHandler);
/*  381:     */         }
/*  382:     */       case 1320172911: 
/*  383: 376 */         if (paramString.equals("modCutOff")) {
/*  384: 377 */           return modCutOff(localInputStream, paramResponseHandler);
/*  385:     */         }
/*  386:     */       case 1470617264: 
/*  387: 380 */         if (paramString.equals("getCutOffActivityList")) {
/*  388: 381 */           return getCutOffActivityList(localInputStream, paramResponseHandler);
/*  389:     */         }
/*  390:     */       case 1662845285: 
/*  391: 384 */         if (paramString.equals("rerunCutOff")) {
/*  392: 385 */           return rerunCutOff(localInputStream, paramResponseHandler);
/*  393:     */         }
/*  394:     */       case 1803800757: 
/*  395: 388 */         if (paramString.equals("getScheduleConfig__CORBA_WStringValue__CORBA_WStringValue")) {
/*  396: 389 */           return getScheduleConfig__CORBA_WStringValue__CORBA_WStringValue(localInputStream, paramResponseHandler);
/*  397:     */         }
/*  398:     */       case 1944413392: 
/*  399: 392 */         if (paramString.equals("_get_handle")) {
/*  400: 393 */           return _get_handle(localInputStream, paramResponseHandler);
/*  401:     */         }
/*  402:     */       case 2004257722: 
/*  403: 396 */         if (paramString.equals("addScheduleConfig")) {
/*  404: 397 */           return addScheduleConfig(localInputStream, paramResponseHandler);
/*  405:     */         }
/*  406:     */       case 2022136132: 
/*  407: 400 */         if (paramString.equals("startEngine")) {
/*  408: 401 */           return startEngine(localInputStream, paramResponseHandler);
/*  409:     */         }
/*  410:     */       case 2081603491: 
/*  411: 404 */         if (paramString.equals("updateFulfillmentInfo")) {
/*  412: 405 */           return updateFulfillmentInfo(localInputStream, paramResponseHandler);
/*  413:     */         }
/*  414:     */       case 2122562265: 
/*  415: 408 */         if (paramString.equals("stopScheduler")) {
/*  416: 409 */           return stopScheduler(localInputStream, paramResponseHandler);
/*  417:     */         }
/*  418:     */         break;
/*  419:     */       }
/*  420: 412 */       throw new BAD_OPERATION();
/*  421:     */     }
/*  422:     */     catch (SystemException localSystemException)
/*  423:     */     {
/*  424: 414 */       throw localSystemException;
/*  425:     */     }
/*  426:     */     catch (Throwable localThrowable)
/*  427:     */     {
/*  428: 416 */       throw new UnknownException(localThrowable);
/*  429:     */     }
/*  430:     */   }
/*  431:     */   
/*  432:     */   private org.omg.CORBA.portable.OutputStream _get_EJBHome(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  433:     */     throws Throwable
/*  434:     */   {
/*  435: 421 */     EJBHome localEJBHome = this.target.getEJBHome();
/*  436: 422 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  437: 423 */     Util.writeRemoteObject(localOutputStream, localEJBHome);
/*  438: 424 */     return localOutputStream;
/*  439:     */   }
/*  440:     */   
/*  441:     */   private org.omg.CORBA.portable.OutputStream _get_primaryKey(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  442:     */     throws Throwable
/*  443:     */   {
/*  444: 428 */     java.lang.Object localObject = this.target.getPrimaryKey();
/*  445: 429 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  446: 430 */     Util.writeAny(localOutputStream, localObject);
/*  447: 431 */     return localOutputStream;
/*  448:     */   }
/*  449:     */   
/*  450:     */   private org.omg.CORBA.portable.OutputStream remove(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  451:     */     throws Throwable
/*  452:     */   {
/*  453:     */     try
/*  454:     */     {
/*  455: 436 */       this.target.remove();
/*  456:     */     }
/*  457:     */     catch (RemoveException localRemoveException)
/*  458:     */     {
/*  459: 438 */       String str = "IDL:javax/ejb/RemoveEx:1.0";
/*  460: 439 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  461: 440 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  462: 441 */       localOutputStream1.write_string(str);
/*  463: 442 */       localOutputStream1.write_value(localRemoveException, RemoveException.class);
/*  464: 443 */       return localOutputStream1;
/*  465:     */     }
/*  466: 445 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  467: 446 */     return localOutputStream;
/*  468:     */   }
/*  469:     */   
/*  470:     */   private org.omg.CORBA.portable.OutputStream _get_handle(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  471:     */     throws Throwable
/*  472:     */   {
/*  473: 450 */     Handle localHandle = this.target.getHandle();
/*  474: 451 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  475: 452 */     Util.writeAbstractObject(localOutputStream, localHandle);
/*  476: 453 */     return localOutputStream;
/*  477:     */   }
/*  478:     */   
/*  479:     */   private org.omg.CORBA.portable.OutputStream isIdentical(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  480:     */     throws Throwable
/*  481:     */   {
/*  482: 457 */     EJBObject localEJBObject = (EJBObject)paramInputStream.read_Object(EJBObject.class);
/*  483: 458 */     boolean bool = this.target.isIdentical(localEJBObject);
/*  484: 459 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  485: 460 */     localOutputStream.write_boolean(bool);
/*  486: 461 */     return localOutputStream;
/*  487:     */   }
/*  488:     */   
/*  489:     */   private org.omg.CORBA.portable.OutputStream start__com_ffusion_ffs_util_FFSProperties__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  490:     */     throws Throwable
/*  491:     */   {
/*  492: 465 */     FFSProperties localFFSProperties = (FFSProperties)paramInputStream.read_value(FFSProperties.class);
/*  493: 466 */     PropertyConfig localPropertyConfig = (PropertyConfig)paramInputStream.read_value(PropertyConfig.class);
/*  494: 467 */     InstructionType[] arrayOfInstructionType = (InstructionType[])paramInputStream.read_value(new InstructionType[0].getClass());
/*  495:     */     try
/*  496:     */     {
/*  497: 469 */       this.target.start(localFFSProperties, localPropertyConfig, arrayOfInstructionType);
/*  498:     */     }
/*  499:     */     catch (FFSException localFFSException)
/*  500:     */     {
/*  501: 471 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  502: 472 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  503: 473 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  504: 474 */       localOutputStream1.write_string(str);
/*  505: 475 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  506: 476 */       return localOutputStream1;
/*  507:     */     }
/*  508: 478 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  509: 479 */     return localOutputStream;
/*  510:     */   }
/*  511:     */   
/*  512:     */   private org.omg.CORBA.portable.OutputStream start__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  513:     */     throws Throwable
/*  514:     */   {
/*  515: 483 */     PropertyConfig localPropertyConfig = (PropertyConfig)paramInputStream.read_value(PropertyConfig.class);
/*  516: 484 */     InstructionType[] arrayOfInstructionType = (InstructionType[])paramInputStream.read_value(new InstructionType[0].getClass());
/*  517:     */     try
/*  518:     */     {
/*  519: 486 */       this.target.start(localPropertyConfig, arrayOfInstructionType);
/*  520:     */     }
/*  521:     */     catch (FFSException localFFSException)
/*  522:     */     {
/*  523: 488 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  524: 489 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  525: 490 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  526: 491 */       localOutputStream1.write_string(str);
/*  527: 492 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  528: 493 */       return localOutputStream1;
/*  529:     */     }
/*  530: 495 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  531: 496 */     return localOutputStream;
/*  532:     */   }
/*  533:     */   
/*  534:     */   private org.omg.CORBA.portable.OutputStream refresh__com_ffusion_ffs_util_FFSProperties__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  535:     */     throws Throwable
/*  536:     */   {
/*  537: 500 */     FFSProperties localFFSProperties = (FFSProperties)paramInputStream.read_value(FFSProperties.class);
/*  538: 501 */     PropertyConfig localPropertyConfig = (PropertyConfig)paramInputStream.read_value(PropertyConfig.class);
/*  539: 502 */     InstructionType[] arrayOfInstructionType = (InstructionType[])paramInputStream.read_value(new InstructionType[0].getClass());
/*  540:     */     try
/*  541:     */     {
/*  542: 504 */       this.target.refresh(localFFSProperties, localPropertyConfig, arrayOfInstructionType);
/*  543:     */     }
/*  544:     */     catch (FFSException localFFSException)
/*  545:     */     {
/*  546: 506 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  547: 507 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  548: 508 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  549: 509 */       localOutputStream1.write_string(str);
/*  550: 510 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  551: 511 */       return localOutputStream1;
/*  552:     */     }
/*  553: 513 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  554: 514 */     return localOutputStream;
/*  555:     */   }
/*  556:     */   
/*  557:     */   private org.omg.CORBA.portable.OutputStream refresh__com_ffusion_ffs_bpw_interfaces_PropertyConfig__org_omg_boxedRMI_com_ffusion_ffs_bpw_interfaces_seq1_InstructionType(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  558:     */     throws Throwable
/*  559:     */   {
/*  560: 518 */     PropertyConfig localPropertyConfig = (PropertyConfig)paramInputStream.read_value(PropertyConfig.class);
/*  561: 519 */     InstructionType[] arrayOfInstructionType = (InstructionType[])paramInputStream.read_value(new InstructionType[0].getClass());
/*  562:     */     try
/*  563:     */     {
/*  564: 521 */       this.target.refresh(localPropertyConfig, arrayOfInstructionType);
/*  565:     */     }
/*  566:     */     catch (FFSException localFFSException)
/*  567:     */     {
/*  568: 523 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  569: 524 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  570: 525 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  571: 526 */       localOutputStream1.write_string(str);
/*  572: 527 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  573: 528 */       return localOutputStream1;
/*  574:     */     }
/*  575: 530 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  576: 531 */     return localOutputStream;
/*  577:     */   }
/*  578:     */   
/*  579:     */   private org.omg.CORBA.portable.OutputStream stop(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  580:     */     throws Throwable
/*  581:     */   {
/*  582:     */     try
/*  583:     */     {
/*  584: 536 */       this.target.stop();
/*  585:     */     }
/*  586:     */     catch (FFSException localFFSException)
/*  587:     */     {
/*  588: 538 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  589: 539 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  590: 540 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  591: 541 */       localOutputStream1.write_string(str);
/*  592: 542 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  593: 543 */       return localOutputStream1;
/*  594:     */     }
/*  595: 545 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  596: 546 */     return localOutputStream;
/*  597:     */   }
/*  598:     */   
/*  599:     */   private org.omg.CORBA.portable.OutputStream refreshSmartCalendar(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  600:     */     throws Throwable
/*  601:     */   {
/*  602:     */     try
/*  603:     */     {
/*  604: 551 */       this.target.refreshSmartCalendar();
/*  605:     */     }
/*  606:     */     catch (FFSException localFFSException)
/*  607:     */     {
/*  608: 553 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  609: 554 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  610: 555 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  611: 556 */       localOutputStream1.write_string(str);
/*  612: 557 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  613: 558 */       return localOutputStream1;
/*  614:     */     }
/*  615: 560 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  616: 561 */     return localOutputStream;
/*  617:     */   }
/*  618:     */   
/*  619:     */   private org.omg.CORBA.portable.OutputStream getStatistics(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  620:     */     throws Throwable
/*  621:     */   {
/*  622:     */     BPWStatistics localBPWStatistics;
/*  623:     */     try
/*  624:     */     {
/*  625: 567 */       localBPWStatistics = this.target.getStatistics();
/*  626:     */     }
/*  627:     */     catch (FFSException localFFSException)
/*  628:     */     {
/*  629: 569 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  630: 570 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/*  631: 571 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  632: 572 */       localOutputStream2.write_string(str);
/*  633: 573 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/*  634: 574 */       return localOutputStream2;
/*  635:     */     }
/*  636: 576 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  637: 577 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  638: 578 */     localOutputStream1.write_value(localBPWStatistics, BPWStatistics.class);
/*  639: 579 */     return localOutputStream1;
/*  640:     */   }
/*  641:     */   
/*  642:     */   private org.omg.CORBA.portable.OutputStream getFreeMem(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  643:     */     throws Throwable
/*  644:     */   {
/*  645:     */     long l;
/*  646:     */     try
/*  647:     */     {
/*  648: 585 */       l = this.target.getFreeMem();
/*  649:     */     }
/*  650:     */     catch (FFSException localFFSException)
/*  651:     */     {
/*  652: 587 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  653: 588 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  654: 589 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  655: 590 */       localOutputStream1.write_string(str);
/*  656: 591 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  657: 592 */       return localOutputStream1;
/*  658:     */     }
/*  659: 594 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  660: 595 */     localOutputStream.write_longlong(l);
/*  661: 596 */     return localOutputStream;
/*  662:     */   }
/*  663:     */   
/*  664:     */   private org.omg.CORBA.portable.OutputStream getTotalMem(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  665:     */     throws Throwable
/*  666:     */   {
/*  667:     */     long l;
/*  668:     */     try
/*  669:     */     {
/*  670: 602 */       l = this.target.getTotalMem();
/*  671:     */     }
/*  672:     */     catch (FFSException localFFSException)
/*  673:     */     {
/*  674: 604 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  675: 605 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  676: 606 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  677: 607 */       localOutputStream1.write_string(str);
/*  678: 608 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  679: 609 */       return localOutputStream1;
/*  680:     */     }
/*  681: 611 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  682: 612 */     localOutputStream.write_longlong(l);
/*  683: 613 */     return localOutputStream;
/*  684:     */   }
/*  685:     */   
/*  686:     */   private org.omg.CORBA.portable.OutputStream getHeapUsage(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  687:     */     throws Throwable
/*  688:     */   {
/*  689:     */     double d;
/*  690:     */     try
/*  691:     */     {
/*  692: 619 */       d = this.target.getHeapUsage();
/*  693:     */     }
/*  694:     */     catch (FFSException localFFSException)
/*  695:     */     {
/*  696: 621 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  697: 622 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  698: 623 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  699: 624 */       localOutputStream1.write_string(str);
/*  700: 625 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  701: 626 */       return localOutputStream1;
/*  702:     */     }
/*  703: 628 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  704: 629 */     localOutputStream.write_double(d);
/*  705: 630 */     return localOutputStream;
/*  706:     */   }
/*  707:     */   
/*  708:     */   private org.omg.CORBA.portable.OutputStream ping(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  709:     */     throws Throwable
/*  710:     */   {
/*  711:     */     boolean bool;
/*  712:     */     try
/*  713:     */     {
/*  714: 636 */       bool = this.target.ping();
/*  715:     */     }
/*  716:     */     catch (FFSException localFFSException)
/*  717:     */     {
/*  718: 638 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  719: 639 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  720: 640 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  721: 641 */       localOutputStream1.write_string(str);
/*  722: 642 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  723: 643 */       return localOutputStream1;
/*  724:     */     }
/*  725: 645 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  726: 646 */     localOutputStream.write_boolean(bool);
/*  727: 647 */     return localOutputStream;
/*  728:     */   }
/*  729:     */   
/*  730:     */   private org.omg.CORBA.portable.OutputStream resubmitEvent__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  731:     */     throws Throwable
/*  732:     */   {
/*  733: 651 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  734: 652 */     String str2 = (String)paramInputStream.read_value(String.class);
/*  735: 653 */     String str3 = (String)paramInputStream.read_value(String.class);
/*  736: 654 */     String str4 = (String)paramInputStream.read_value(String.class);
/*  737:     */     try
/*  738:     */     {
/*  739: 656 */       this.target.resubmitEvent(str1, str2, str3, str4);
/*  740:     */     }
/*  741:     */     catch (FFSException localFFSException)
/*  742:     */     {
/*  743: 658 */       String str5 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  744: 659 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  745: 660 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  746: 661 */       localOutputStream1.write_string(str5);
/*  747: 662 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  748: 663 */       return localOutputStream1;
/*  749:     */     }
/*  750: 665 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  751: 666 */     return localOutputStream;
/*  752:     */   }
/*  753:     */   
/*  754:     */   private org.omg.CORBA.portable.OutputStream resubmitEvent__CORBA_WStringValue__CORBA_WStringValue__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  755:     */     throws Throwable
/*  756:     */   {
/*  757: 670 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  758: 671 */     String str2 = (String)paramInputStream.read_value(String.class);
/*  759: 672 */     String str3 = (String)paramInputStream.read_value(String.class);
/*  760:     */     try
/*  761:     */     {
/*  762: 674 */       this.target.resubmitEvent(str1, str2, str3);
/*  763:     */     }
/*  764:     */     catch (FFSException localFFSException)
/*  765:     */     {
/*  766: 676 */       String str4 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  767: 677 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  768: 678 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  769: 679 */       localOutputStream1.write_string(str4);
/*  770: 680 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  771: 681 */       return localOutputStream1;
/*  772:     */     }
/*  773: 683 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  774: 684 */     return localOutputStream;
/*  775:     */   }
/*  776:     */   
/*  777:     */   private org.omg.CORBA.portable.OutputStream resubmitEvent__CORBA_WStringValue__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  778:     */     throws Throwable
/*  779:     */   {
/*  780: 688 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  781: 689 */     String str2 = (String)paramInputStream.read_value(String.class);
/*  782:     */     try
/*  783:     */     {
/*  784: 691 */       this.target.resubmitEvent(str1, str2);
/*  785:     */     }
/*  786:     */     catch (FFSException localFFSException)
/*  787:     */     {
/*  788: 693 */       String str3 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  789: 694 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  790: 695 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  791: 696 */       localOutputStream1.write_string(str3);
/*  792: 697 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  793: 698 */       return localOutputStream1;
/*  794:     */     }
/*  795: 700 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  796: 701 */     return localOutputStream;
/*  797:     */   }
/*  798:     */   
/*  799:     */   private org.omg.CORBA.portable.OutputStream startScheduler(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  800:     */     throws Throwable
/*  801:     */   {
/*  802:     */     String str1;
/*  803:     */     try
/*  804:     */     {
/*  805: 707 */       str1 = this.target.startScheduler();
/*  806:     */     }
/*  807:     */     catch (FFSException localFFSException)
/*  808:     */     {
/*  809: 709 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  810: 710 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/*  811: 711 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  812: 712 */       localOutputStream2.write_string(str2);
/*  813: 713 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/*  814: 714 */       return localOutputStream2;
/*  815:     */     }
/*  816: 716 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  817: 717 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  818: 718 */     localOutputStream1.write_value(str1, String.class);
/*  819: 719 */     return localOutputStream1;
/*  820:     */   }
/*  821:     */   
/*  822:     */   private org.omg.CORBA.portable.OutputStream stopScheduler(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  823:     */     throws Throwable
/*  824:     */   {
/*  825:     */     String str1;
/*  826:     */     try
/*  827:     */     {
/*  828: 725 */       str1 = this.target.stopScheduler();
/*  829:     */     }
/*  830:     */     catch (FFSException localFFSException)
/*  831:     */     {
/*  832: 727 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  833: 728 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/*  834: 729 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  835: 730 */       localOutputStream2.write_string(str2);
/*  836: 731 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/*  837: 732 */       return localOutputStream2;
/*  838:     */     }
/*  839: 734 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  840: 735 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  841: 736 */     localOutputStream1.write_value(str1, String.class);
/*  842: 737 */     return localOutputStream1;
/*  843:     */   }
/*  844:     */   
/*  845:     */   private org.omg.CORBA.portable.OutputStream refreshScheduler(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  846:     */     throws Throwable
/*  847:     */   {
/*  848:     */     String str1;
/*  849:     */     try
/*  850:     */     {
/*  851: 743 */       str1 = this.target.refreshScheduler();
/*  852:     */     }
/*  853:     */     catch (FFSException localFFSException)
/*  854:     */     {
/*  855: 745 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  856: 746 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/*  857: 747 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  858: 748 */       localOutputStream2.write_string(str2);
/*  859: 749 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/*  860: 750 */       return localOutputStream2;
/*  861:     */     }
/*  862: 752 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  863: 753 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/*  864: 754 */     localOutputStream1.write_value(str1, String.class);
/*  865: 755 */     return localOutputStream1;
/*  866:     */   }
/*  867:     */   
/*  868:     */   private org.omg.CORBA.portable.OutputStream registerPropertyConfig(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  869:     */     throws Throwable
/*  870:     */   {
/*  871: 759 */     PropertyConfig localPropertyConfig = (PropertyConfig)paramInputStream.read_value(PropertyConfig.class);
/*  872:     */     try
/*  873:     */     {
/*  874: 761 */       this.target.registerPropertyConfig(localPropertyConfig);
/*  875:     */     }
/*  876:     */     catch (FFSException localFFSException)
/*  877:     */     {
/*  878: 763 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  879: 764 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  880: 765 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  881: 766 */       localOutputStream1.write_string(str);
/*  882: 767 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  883: 768 */       return localOutputStream1;
/*  884:     */     }
/*  885: 770 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  886: 771 */     return localOutputStream;
/*  887:     */   }
/*  888:     */   
/*  889:     */   private org.omg.CORBA.portable.OutputStream startEngine(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  890:     */     throws Throwable
/*  891:     */   {
/*  892: 775 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  893:     */     try
/*  894:     */     {
/*  895: 777 */       this.target.startEngine(str1);
/*  896:     */     }
/*  897:     */     catch (FFSException localFFSException)
/*  898:     */     {
/*  899: 779 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  900: 780 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  901: 781 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  902: 782 */       localOutputStream1.write_string(str2);
/*  903: 783 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  904: 784 */       return localOutputStream1;
/*  905:     */     }
/*  906: 786 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  907: 787 */     return localOutputStream;
/*  908:     */   }
/*  909:     */   
/*  910:     */   private org.omg.CORBA.portable.OutputStream stopEngine(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  911:     */     throws Throwable
/*  912:     */   {
/*  913: 791 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  914:     */     try
/*  915:     */     {
/*  916: 793 */       this.target.stopEngine(str1);
/*  917:     */     }
/*  918:     */     catch (FFSException localFFSException)
/*  919:     */     {
/*  920: 795 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  921: 796 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  922: 797 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  923: 798 */       localOutputStream1.write_string(str2);
/*  924: 799 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  925: 800 */       return localOutputStream1;
/*  926:     */     }
/*  927: 802 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  928: 803 */     return localOutputStream;
/*  929:     */   }
/*  930:     */   
/*  931:     */   private org.omg.CORBA.portable.OutputStream stopLimitCheckApprovalProcessor(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  932:     */     throws Throwable
/*  933:     */   {
/*  934: 807 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  935:     */     try
/*  936:     */     {
/*  937: 809 */       this.target.stopLimitCheckApprovalProcessor(str1);
/*  938:     */     }
/*  939:     */     catch (FFSException localFFSException)
/*  940:     */     {
/*  941: 811 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  942: 812 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  943: 813 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  944: 814 */       localOutputStream1.write_string(str2);
/*  945: 815 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  946: 816 */       return localOutputStream1;
/*  947:     */     }
/*  948: 818 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  949: 819 */     return localOutputStream;
/*  950:     */   }
/*  951:     */   
/*  952:     */   private org.omg.CORBA.portable.OutputStream startLimitCheckApprovalProcessor(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  953:     */     throws Throwable
/*  954:     */   {
/*  955: 823 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  956:     */     try
/*  957:     */     {
/*  958: 825 */       this.target.startLimitCheckApprovalProcessor(str1);
/*  959:     */     }
/*  960:     */     catch (FFSException localFFSException)
/*  961:     */     {
/*  962: 827 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  963: 828 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  964: 829 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  965: 830 */       localOutputStream1.write_string(str2);
/*  966: 831 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  967: 832 */       return localOutputStream1;
/*  968:     */     }
/*  969: 834 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  970: 835 */     return localOutputStream;
/*  971:     */   }
/*  972:     */   
/*  973:     */   private org.omg.CORBA.portable.OutputStream runBatchProcess(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  974:     */     throws Throwable
/*  975:     */   {
/*  976: 839 */     String str1 = (String)paramInputStream.read_value(String.class);
/*  977: 840 */     String str2 = (String)paramInputStream.read_value(String.class);
/*  978:     */     try
/*  979:     */     {
/*  980: 842 */       this.target.runBatchProcess(str1, str2);
/*  981:     */     }
/*  982:     */     catch (FFSException localFFSException)
/*  983:     */     {
/*  984: 844 */       String str3 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/*  985: 845 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/*  986: 846 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/*  987: 847 */       localOutputStream1.write_string(str3);
/*  988: 848 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/*  989: 849 */       return localOutputStream1;
/*  990:     */     }
/*  991: 851 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/*  992: 852 */     return localOutputStream;
/*  993:     */   }
/*  994:     */   
/*  995:     */   private org.omg.CORBA.portable.OutputStream updateScheduleRunTimeConfig(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  996:     */     throws Throwable
/*  997:     */   {
/*  998: 856 */     InstructionType localInstructionType = (InstructionType)paramInputStream.read_value(InstructionType.class);
/*  999:     */     try
/* 1000:     */     {
/* 1001: 858 */       this.target.updateScheduleRunTimeConfig(localInstructionType);
/* 1002:     */     }
/* 1003:     */     catch (FFSException localFFSException)
/* 1004:     */     {
/* 1005: 860 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1006: 861 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1007: 862 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1008: 863 */       localOutputStream1.write_string(str);
/* 1009: 864 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/* 1010: 865 */       return localOutputStream1;
/* 1011:     */     }
/* 1012: 867 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1013: 868 */     return localOutputStream;
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   private org.omg.CORBA.portable.OutputStream updateScheduleProcessingConfig(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1017:     */     throws Throwable
/* 1018:     */   {
/* 1019: 872 */     InstructionType localInstructionType = (InstructionType)paramInputStream.read_value(InstructionType.class);
/* 1020:     */     try
/* 1021:     */     {
/* 1022: 874 */       this.target.updateScheduleProcessingConfig(localInstructionType);
/* 1023:     */     }
/* 1024:     */     catch (FFSException localFFSException)
/* 1025:     */     {
/* 1026: 876 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1027: 877 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1028: 878 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1029: 879 */       localOutputStream1.write_string(str);
/* 1030: 880 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/* 1031: 881 */       return localOutputStream1;
/* 1032:     */     }
/* 1033: 883 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1034: 884 */     return localOutputStream;
/* 1035:     */   }
/* 1036:     */   
/* 1037:     */   private org.omg.CORBA.portable.OutputStream updateScheduleConfig(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1038:     */     throws Throwable
/* 1039:     */   {
/* 1040: 888 */     InstructionType localInstructionType = (InstructionType)paramInputStream.read_value(InstructionType.class);
/* 1041:     */     try
/* 1042:     */     {
/* 1043: 890 */       this.target.updateScheduleConfig(localInstructionType);
/* 1044:     */     }
/* 1045:     */     catch (FFSException localFFSException)
/* 1046:     */     {
/* 1047: 892 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1048: 893 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1049: 894 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1050: 895 */       localOutputStream1.write_string(str);
/* 1051: 896 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/* 1052: 897 */       return localOutputStream1;
/* 1053:     */     }
/* 1054: 899 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1055: 900 */     return localOutputStream;
/* 1056:     */   }
/* 1057:     */   
/* 1058:     */   private org.omg.CORBA.portable.OutputStream getScheduleConfig__CORBA_WStringValue__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1059:     */     throws Throwable
/* 1060:     */   {
/* 1061: 904 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1062: 905 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1063:     */     InstructionType localInstructionType;
/* 1064:     */     try
/* 1065:     */     {
/* 1066: 908 */       localInstructionType = this.target.getScheduleConfig(str1, str2);
/* 1067:     */     }
/* 1068:     */     catch (FFSException localFFSException)
/* 1069:     */     {
/* 1070: 910 */       String str3 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1071: 911 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1072: 912 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1073: 913 */       localOutputStream2.write_string(str3);
/* 1074: 914 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1075: 915 */       return localOutputStream2;
/* 1076:     */     }
/* 1077: 917 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1078: 918 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1079: 919 */     localOutputStream1.write_value(localInstructionType, InstructionType.class);
/* 1080: 920 */     return localOutputStream1;
/* 1081:     */   }
/* 1082:     */   
/* 1083:     */   private org.omg.CORBA.portable.OutputStream getScheduleConfig__(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1084:     */     throws Throwable
/* 1085:     */   {
/* 1086:     */     InstructionType[] arrayOfInstructionType;
/* 1087:     */     try
/* 1088:     */     {
/* 1089: 926 */       arrayOfInstructionType = this.target.getScheduleConfig();
/* 1090:     */     }
/* 1091:     */     catch (FFSException localFFSException)
/* 1092:     */     {
/* 1093: 928 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1094: 929 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1095: 930 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1096: 931 */       localOutputStream2.write_string(str);
/* 1097: 932 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1098: 933 */       return localOutputStream2;
/* 1099:     */     }
/* 1100: 935 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1101: 936 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1102: 937 */     localOutputStream1.write_value(cast_array(arrayOfInstructionType), new InstructionType[0].getClass());
/* 1103: 938 */     return localOutputStream1;
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   private org.omg.CORBA.portable.OutputStream getSchedulerInfo__CORBA_WStringValue__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1107:     */     throws Throwable
/* 1108:     */   {
/* 1109: 942 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1110: 943 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1111:     */     SchedulerInfo localSchedulerInfo;
/* 1112:     */     try
/* 1113:     */     {
/* 1114: 946 */       localSchedulerInfo = this.target.getSchedulerInfo(str1, str2);
/* 1115:     */     }
/* 1116:     */     catch (FFSException localFFSException)
/* 1117:     */     {
/* 1118: 948 */       String str3 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1119: 949 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1120: 950 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1121: 951 */       localOutputStream2.write_string(str3);
/* 1122: 952 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1123: 953 */       return localOutputStream2;
/* 1124:     */     }
/* 1125: 955 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1126: 956 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1127: 957 */     localOutputStream1.write_value(localSchedulerInfo, SchedulerInfo.class);
/* 1128: 958 */     return localOutputStream1;
/* 1129:     */   }
/* 1130:     */   
/* 1131:     */   private org.omg.CORBA.portable.OutputStream getSchedulerInfo__(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1132:     */     throws Throwable
/* 1133:     */   {
/* 1134:     */     SchedulerInfo[] arrayOfSchedulerInfo;
/* 1135:     */     try
/* 1136:     */     {
/* 1137: 964 */       arrayOfSchedulerInfo = this.target.getSchedulerInfo();
/* 1138:     */     }
/* 1139:     */     catch (FFSException localFFSException)
/* 1140:     */     {
/* 1141: 966 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1142: 967 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1143: 968 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1144: 969 */       localOutputStream2.write_string(str);
/* 1145: 970 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1146: 971 */       return localOutputStream2;
/* 1147:     */     }
/* 1148: 973 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1149: 974 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1150: 975 */     localOutputStream1.write_value(cast_array(arrayOfSchedulerInfo), new SchedulerInfo[0].getClass());
/* 1151: 976 */     return localOutputStream1;
/* 1152:     */   }
/* 1153:     */   
/* 1154:     */   private org.omg.CORBA.portable.OutputStream getScheduleHist(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1155:     */     throws Throwable
/* 1156:     */   {
/* 1157: 980 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1158: 981 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1159: 982 */     ScheduleHist localScheduleHist = (ScheduleHist)paramInputStream.read_value(ScheduleHist.class);
/* 1160:     */     ScheduleHist[] arrayOfScheduleHist;
/* 1161:     */     try
/* 1162:     */     {
/* 1163: 985 */       arrayOfScheduleHist = this.target.getScheduleHist(str1, str2, localScheduleHist);
/* 1164:     */     }
/* 1165:     */     catch (FFSException localFFSException)
/* 1166:     */     {
/* 1167: 987 */       String str3 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1168: 988 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1169: 989 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1170: 990 */       localOutputStream2.write_string(str3);
/* 1171: 991 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1172: 992 */       return localOutputStream2;
/* 1173:     */     }
/* 1174: 994 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1175: 995 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1176: 996 */     localOutputStream1.write_value(cast_array(arrayOfScheduleHist), new ScheduleHist[0].getClass());
/* 1177: 997 */     return localOutputStream1;
/* 1178:     */   }
/* 1179:     */   
/* 1180:     */   private org.omg.CORBA.portable.OutputStream searchGlobalPayees__CORBA_WStringValue(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1181:     */     throws Throwable
/* 1182:     */   {
/* 1183:1001 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1184:     */     PayeeInfo[] arrayOfPayeeInfo;
/* 1185:     */     try
/* 1186:     */     {
/* 1187:1004 */       arrayOfPayeeInfo = this.target.searchGlobalPayees(str1);
/* 1188:     */     }
/* 1189:     */     catch (FFSException localFFSException)
/* 1190:     */     {
/* 1191:1006 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1192:1007 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1193:1008 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1194:1009 */       localOutputStream2.write_string(str2);
/* 1195:1010 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1196:1011 */       return localOutputStream2;
/* 1197:     */     }
/* 1198:1013 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1199:1014 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1200:1015 */     localOutputStream1.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/* 1201:1016 */     return localOutputStream1;
/* 1202:     */   }
/* 1203:     */   
/* 1204:     */   private org.omg.CORBA.portable.OutputStream searchGlobalPayees__com_ffusion_ffs_bpw_interfaces_PayeeInfo__long(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1205:     */     throws Throwable
/* 1206:     */   {
/* 1207:1020 */     PayeeInfo localPayeeInfo = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/* 1208:1021 */     int i = paramInputStream.read_long();
/* 1209:     */     PayeeInfo[] arrayOfPayeeInfo;
/* 1210:     */     try
/* 1211:     */     {
/* 1212:1024 */       arrayOfPayeeInfo = this.target.searchGlobalPayees(localPayeeInfo, i);
/* 1213:     */     }
/* 1214:     */     catch (FFSException localFFSException)
/* 1215:     */     {
/* 1216:1026 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1217:1027 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1218:1028 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1219:1029 */       localOutputStream2.write_string(str);
/* 1220:1030 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1221:1031 */       return localOutputStream2;
/* 1222:     */     }
/* 1223:1033 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1224:1034 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1225:1035 */     localOutputStream1.write_value(cast_array(arrayOfPayeeInfo), new PayeeInfo[0].getClass());
/* 1226:1036 */     return localOutputStream1;
/* 1227:     */   }
/* 1228:     */   
/* 1229:     */   private org.omg.CORBA.portable.OutputStream getGlobalPayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1230:     */     throws Throwable
/* 1231:     */   {
/* 1232:1040 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1233:     */     PayeeInfo localPayeeInfo;
/* 1234:     */     try
/* 1235:     */     {
/* 1236:1043 */       localPayeeInfo = this.target.getGlobalPayee(str1);
/* 1237:     */     }
/* 1238:     */     catch (FFSException localFFSException)
/* 1239:     */     {
/* 1240:1045 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1241:1046 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1242:1047 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1243:1048 */       localOutputStream2.write_string(str2);
/* 1244:1049 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1245:1050 */       return localOutputStream2;
/* 1246:     */     }
/* 1247:1052 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1248:1053 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1249:1054 */     localOutputStream1.write_value(localPayeeInfo, PayeeInfo.class);
/* 1250:1055 */     return localOutputStream1;
/* 1251:     */   }
/* 1252:     */   
/* 1253:     */   private org.omg.CORBA.portable.OutputStream updateGlobalPayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1254:     */     throws Throwable
/* 1255:     */   {
/* 1256:1059 */     PayeeInfo localPayeeInfo1 = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/* 1257:     */     PayeeInfo localPayeeInfo2;
/* 1258:     */     try
/* 1259:     */     {
/* 1260:1062 */       localPayeeInfo2 = this.target.updateGlobalPayee(localPayeeInfo1);
/* 1261:     */     }
/* 1262:     */     catch (FFSException localFFSException)
/* 1263:     */     {
/* 1264:1064 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1265:1065 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1266:1066 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1267:1067 */       localOutputStream2.write_string(str);
/* 1268:1068 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1269:1069 */       return localOutputStream2;
/* 1270:     */     }
/* 1271:1071 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1272:1072 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1273:1073 */     localOutputStream1.write_value(localPayeeInfo2, PayeeInfo.class);
/* 1274:1074 */     return localOutputStream1;
/* 1275:     */   }
/* 1276:     */   
/* 1277:     */   private org.omg.CORBA.portable.OutputStream addPayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1278:     */     throws Throwable
/* 1279:     */   {
/* 1280:1078 */     FFSDBProperties localFFSDBProperties = (FFSDBProperties)paramInputStream.read_value(FFSDBProperties.class);
/* 1281:1079 */     PayeeInfo localPayeeInfo = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/* 1282:1080 */     PayeeRouteInfo localPayeeRouteInfo = (PayeeRouteInfo)paramInputStream.read_value(PayeeRouteInfo.class);
/* 1283:     */     String str1;
/* 1284:     */     try
/* 1285:     */     {
/* 1286:1083 */       str1 = this.target.addPayee(localFFSDBProperties, localPayeeInfo, localPayeeRouteInfo);
/* 1287:     */     }
/* 1288:     */     catch (FFSException localFFSException)
/* 1289:     */     {
/* 1290:1085 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1291:1086 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1292:1087 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1293:1088 */       localOutputStream2.write_string(str2);
/* 1294:1089 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1295:1090 */       return localOutputStream2;
/* 1296:     */     }
/* 1297:1092 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1298:1093 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1299:1094 */     localOutputStream1.write_value(str1, String.class);
/* 1300:1095 */     return localOutputStream1;
/* 1301:     */   }
/* 1302:     */   
/* 1303:     */   private org.omg.CORBA.portable.OutputStream updatePayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1304:     */     throws Throwable
/* 1305:     */   {
/* 1306:1099 */     FFSDBProperties localFFSDBProperties = (FFSDBProperties)paramInputStream.read_value(FFSDBProperties.class);
/* 1307:1100 */     PayeeInfo localPayeeInfo = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/* 1308:1101 */     PayeeRouteInfo localPayeeRouteInfo = (PayeeRouteInfo)paramInputStream.read_value(PayeeRouteInfo.class);
/* 1309:     */     try
/* 1310:     */     {
/* 1311:1103 */       this.target.updatePayee(localFFSDBProperties, localPayeeInfo, localPayeeRouteInfo);
/* 1312:     */     }
/* 1313:     */     catch (FFSException localFFSException)
/* 1314:     */     {
/* 1315:1105 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1316:1106 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1317:1107 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1318:1108 */       localOutputStream1.write_string(str);
/* 1319:1109 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/* 1320:1110 */       return localOutputStream1;
/* 1321:     */     }
/* 1322:1112 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1323:1113 */     return localOutputStream;
/* 1324:     */   }
/* 1325:     */   
/* 1326:     */   private org.omg.CORBA.portable.OutputStream deletePayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1327:     */     throws Throwable
/* 1328:     */   {
/* 1329:1117 */     FFSDBProperties localFFSDBProperties = (FFSDBProperties)paramInputStream.read_value(FFSDBProperties.class);
/* 1330:1118 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1331:     */     try
/* 1332:     */     {
/* 1333:1120 */       this.target.deletePayee(localFFSDBProperties, str1);
/* 1334:     */     }
/* 1335:     */     catch (FFSException localFFSException)
/* 1336:     */     {
/* 1337:1122 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1338:1123 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1339:1124 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1340:1125 */       localOutputStream1.write_string(str2);
/* 1341:1126 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/* 1342:1127 */       return localOutputStream1;
/* 1343:     */     }
/* 1344:1129 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1345:1130 */     return localOutputStream;
/* 1346:     */   }
/* 1347:     */   
/* 1348:     */   private org.omg.CORBA.portable.OutputStream getPayeeRoute(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1349:     */     throws Throwable
/* 1350:     */   {
/* 1351:1134 */     FFSDBProperties localFFSDBProperties = (FFSDBProperties)paramInputStream.read_value(FFSDBProperties.class);
/* 1352:1135 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1353:1136 */     int i = paramInputStream.read_long();
/* 1354:     */     PayeeRouteInfo localPayeeRouteInfo;
/* 1355:     */     try
/* 1356:     */     {
/* 1357:1139 */       localPayeeRouteInfo = this.target.getPayeeRoute(localFFSDBProperties, str1, i);
/* 1358:     */     }
/* 1359:     */     catch (FFSException localFFSException)
/* 1360:     */     {
/* 1361:1141 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1362:1142 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1363:1143 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1364:1144 */       localOutputStream2.write_string(str2);
/* 1365:1145 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1366:1146 */       return localOutputStream2;
/* 1367:     */     }
/* 1368:1148 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1369:1149 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1370:1150 */     localOutputStream1.write_value(localPayeeRouteInfo, PayeeRouteInfo.class);
/* 1371:1151 */     return localOutputStream1;
/* 1372:     */   }
/* 1373:     */   
/* 1374:     */   private org.omg.CORBA.portable.OutputStream findPayeeByID(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1375:     */     throws Throwable
/* 1376:     */   {
/* 1377:1155 */     FFSDBProperties localFFSDBProperties = (FFSDBProperties)paramInputStream.read_value(FFSDBProperties.class);
/* 1378:1156 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1379:     */     PayeeInfo localPayeeInfo;
/* 1380:     */     try
/* 1381:     */     {
/* 1382:1159 */       localPayeeInfo = this.target.findPayeeByID(localFFSDBProperties, str1);
/* 1383:     */     }
/* 1384:     */     catch (FFSException localFFSException)
/* 1385:     */     {
/* 1386:1161 */       String str2 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1387:1162 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1388:1163 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1389:1164 */       localOutputStream2.write_string(str2);
/* 1390:1165 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1391:1166 */       return localOutputStream2;
/* 1392:     */     }
/* 1393:1168 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1394:1169 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1395:1170 */     localOutputStream1.write_value(localPayeeInfo, PayeeInfo.class);
/* 1396:1171 */     return localOutputStream1;
/* 1397:     */   }
/* 1398:     */   
/* 1399:     */   private org.omg.CORBA.portable.OutputStream getAllFulfillmentInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1400:     */     throws Throwable
/* 1401:     */   {
/* 1402:1175 */     FFSDBProperties localFFSDBProperties = (FFSDBProperties)paramInputStream.read_value(FFSDBProperties.class);
/* 1403:     */     FulfillmentInfo[] arrayOfFulfillmentInfo;
/* 1404:     */     try
/* 1405:     */     {
/* 1406:1178 */       arrayOfFulfillmentInfo = this.target.getAllFulfillmentInfo(localFFSDBProperties);
/* 1407:     */     }
/* 1408:     */     catch (FFSException localFFSException)
/* 1409:     */     {
/* 1410:1180 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1411:1181 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1412:1182 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1413:1183 */       localOutputStream2.write_string(str);
/* 1414:1184 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1415:1185 */       return localOutputStream2;
/* 1416:     */     }
/* 1417:1187 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1418:1188 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1419:1189 */     localOutputStream1.write_value(cast_array(arrayOfFulfillmentInfo), new FulfillmentInfo[0].getClass());
/* 1420:1190 */     return localOutputStream1;
/* 1421:     */   }
/* 1422:     */   
/* 1423:     */   private org.omg.CORBA.portable.OutputStream getFulfillmentSystems(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1424:     */     throws Throwable
/* 1425:     */   {
/* 1426:     */     FulfillmentInfo[] arrayOfFulfillmentInfo;
/* 1427:     */     try
/* 1428:     */     {
/* 1429:1196 */       arrayOfFulfillmentInfo = this.target.getFulfillmentSystems();
/* 1430:     */     }
/* 1431:     */     catch (FFSException localFFSException)
/* 1432:     */     {
/* 1433:1198 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1434:1199 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1435:1200 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1436:1201 */       localOutputStream2.write_string(str);
/* 1437:1202 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1438:1203 */       return localOutputStream2;
/* 1439:     */     }
/* 1440:1205 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1441:1206 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1442:1207 */     localOutputStream1.write_value(cast_array(arrayOfFulfillmentInfo), new FulfillmentInfo[0].getClass());
/* 1443:1208 */     return localOutputStream1;
/* 1444:     */   }
/* 1445:     */   
/* 1446:     */   private org.omg.CORBA.portable.OutputStream getGlobalPayeeGroups(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1447:     */     throws Throwable
/* 1448:     */   {
/* 1449:     */     String[] arrayOfString;
/* 1450:     */     try
/* 1451:     */     {
/* 1452:1214 */       arrayOfString = this.target.getGlobalPayeeGroups();
/* 1453:     */     }
/* 1454:     */     catch (FFSException localFFSException)
/* 1455:     */     {
/* 1456:1216 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1457:1217 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1458:1218 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1459:1219 */       localOutputStream2.write_string(str);
/* 1460:1220 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1461:1221 */       return localOutputStream2;
/* 1462:     */     }
/* 1463:1223 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1464:1224 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1465:1225 */     localOutputStream1.write_value(cast_array(arrayOfString), new String[0].getClass());
/* 1466:1226 */     return localOutputStream1;
/* 1467:     */   }
/* 1468:     */   
/* 1469:     */   private org.omg.CORBA.portable.OutputStream addFulfillmentInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1470:     */     throws Throwable
/* 1471:     */   {
/* 1472:1230 */     FFSDBProperties localFFSDBProperties = (FFSDBProperties)paramInputStream.read_value(FFSDBProperties.class);
/* 1473:1231 */     FulfillmentInfo localFulfillmentInfo = (FulfillmentInfo)paramInputStream.read_value(FulfillmentInfo.class);
/* 1474:     */     try
/* 1475:     */     {
/* 1476:1233 */       this.target.addFulfillmentInfo(localFFSDBProperties, localFulfillmentInfo);
/* 1477:     */     }
/* 1478:     */     catch (FFSException localFFSException)
/* 1479:     */     {
/* 1480:1235 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1481:1236 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1482:1237 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1483:1238 */       localOutputStream1.write_string(str);
/* 1484:1239 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/* 1485:1240 */       return localOutputStream1;
/* 1486:     */     }
/* 1487:1242 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1488:1243 */     return localOutputStream;
/* 1489:     */   }
/* 1490:     */   
/* 1491:     */   private org.omg.CORBA.portable.OutputStream updateFulfillmentInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1492:     */     throws Throwable
/* 1493:     */   {
/* 1494:1247 */     FFSDBProperties localFFSDBProperties = (FFSDBProperties)paramInputStream.read_value(FFSDBProperties.class);
/* 1495:1248 */     FulfillmentInfo localFulfillmentInfo = (FulfillmentInfo)paramInputStream.read_value(FulfillmentInfo.class);
/* 1496:     */     try
/* 1497:     */     {
/* 1498:1250 */       this.target.updateFulfillmentInfo(localFFSDBProperties, localFulfillmentInfo);
/* 1499:     */     }
/* 1500:     */     catch (FFSException localFFSException)
/* 1501:     */     {
/* 1502:1252 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1503:1253 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1504:1254 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1505:1255 */       localOutputStream1.write_string(str);
/* 1506:1256 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/* 1507:1257 */       return localOutputStream1;
/* 1508:     */     }
/* 1509:1259 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1510:1260 */     return localOutputStream;
/* 1511:     */   }
/* 1512:     */   
/* 1513:     */   private org.omg.CORBA.portable.OutputStream deleteFulfillmentInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1514:     */     throws Throwable
/* 1515:     */   {
/* 1516:1264 */     FFSDBProperties localFFSDBProperties = (FFSDBProperties)paramInputStream.read_value(FFSDBProperties.class);
/* 1517:1265 */     int i = paramInputStream.read_long();
/* 1518:     */     try
/* 1519:     */     {
/* 1520:1267 */       this.target.deleteFulfillmentInfo(localFFSDBProperties, i);
/* 1521:     */     }
/* 1522:     */     catch (FFSException localFFSException)
/* 1523:     */     {
/* 1524:1269 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1525:1270 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1526:1271 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1527:1272 */       localOutputStream1.write_string(str);
/* 1528:1273 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/* 1529:1274 */       return localOutputStream1;
/* 1530:     */     }
/* 1531:1276 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1532:1277 */     return localOutputStream;
/* 1533:     */   }
/* 1534:     */   
/* 1535:     */   private org.omg.CORBA.portable.OutputStream setDebugLevel(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1536:     */     throws Throwable
/* 1537:     */   {
/* 1538:1281 */     int i = paramInputStream.read_long();
/* 1539:     */     try
/* 1540:     */     {
/* 1541:1283 */       this.target.setDebugLevel(i);
/* 1542:     */     }
/* 1543:     */     catch (FFSException localFFSException)
/* 1544:     */     {
/* 1545:1285 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1546:1286 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1547:1287 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1548:1288 */       localOutputStream1.write_string(str);
/* 1549:1289 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/* 1550:1290 */       return localOutputStream1;
/* 1551:     */     }
/* 1552:1292 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1553:1293 */     return localOutputStream;
/* 1554:     */   }
/* 1555:     */   
/* 1556:     */   private org.omg.CORBA.portable.OutputStream addProcessingWindow(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1557:     */     throws Throwable
/* 1558:     */   {
/* 1559:1297 */     ProcessingWindowInfo localProcessingWindowInfo1 = (ProcessingWindowInfo)paramInputStream.read_value(ProcessingWindowInfo.class);
/* 1560:     */     ProcessingWindowInfo localProcessingWindowInfo2;
/* 1561:     */     try
/* 1562:     */     {
/* 1563:1300 */       localProcessingWindowInfo2 = this.target.addProcessingWindow(localProcessingWindowInfo1);
/* 1564:     */     }
/* 1565:     */     catch (FFSException localFFSException)
/* 1566:     */     {
/* 1567:1302 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1568:1303 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1569:1304 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1570:1305 */       localOutputStream2.write_string(str);
/* 1571:1306 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1572:1307 */       return localOutputStream2;
/* 1573:     */     }
/* 1574:1309 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1575:1310 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1576:1311 */     localOutputStream1.write_value(localProcessingWindowInfo2, ProcessingWindowInfo.class);
/* 1577:1312 */     return localOutputStream1;
/* 1578:     */   }
/* 1579:     */   
/* 1580:     */   private org.omg.CORBA.portable.OutputStream modProcessingWindow(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1581:     */     throws Throwable
/* 1582:     */   {
/* 1583:1316 */     ProcessingWindowInfo localProcessingWindowInfo1 = (ProcessingWindowInfo)paramInputStream.read_value(ProcessingWindowInfo.class);
/* 1584:     */     ProcessingWindowInfo localProcessingWindowInfo2;
/* 1585:     */     try
/* 1586:     */     {
/* 1587:1319 */       localProcessingWindowInfo2 = this.target.modProcessingWindow(localProcessingWindowInfo1);
/* 1588:     */     }
/* 1589:     */     catch (FFSException localFFSException)
/* 1590:     */     {
/* 1591:1321 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1592:1322 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1593:1323 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1594:1324 */       localOutputStream2.write_string(str);
/* 1595:1325 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1596:1326 */       return localOutputStream2;
/* 1597:     */     }
/* 1598:1328 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1599:1329 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1600:1330 */     localOutputStream1.write_value(localProcessingWindowInfo2, ProcessingWindowInfo.class);
/* 1601:1331 */     return localOutputStream1;
/* 1602:     */   }
/* 1603:     */   
/* 1604:     */   private org.omg.CORBA.portable.OutputStream delProcessingWindow(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1605:     */     throws Throwable
/* 1606:     */   {
/* 1607:1335 */     ProcessingWindowInfo localProcessingWindowInfo1 = (ProcessingWindowInfo)paramInputStream.read_value(ProcessingWindowInfo.class);
/* 1608:     */     ProcessingWindowInfo localProcessingWindowInfo2;
/* 1609:     */     try
/* 1610:     */     {
/* 1611:1338 */       localProcessingWindowInfo2 = this.target.delProcessingWindow(localProcessingWindowInfo1);
/* 1612:     */     }
/* 1613:     */     catch (FFSException localFFSException)
/* 1614:     */     {
/* 1615:1340 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1616:1341 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1617:1342 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1618:1343 */       localOutputStream2.write_string(str);
/* 1619:1344 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1620:1345 */       return localOutputStream2;
/* 1621:     */     }
/* 1622:1347 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1623:1348 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1624:1349 */     localOutputStream1.write_value(localProcessingWindowInfo2, ProcessingWindowInfo.class);
/* 1625:1350 */     return localOutputStream1;
/* 1626:     */   }
/* 1627:     */   
/* 1628:     */   private org.omg.CORBA.portable.OutputStream getProcessingWindows(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1629:     */     throws Throwable
/* 1630:     */   {
/* 1631:1354 */     ProcessingWindowList localProcessingWindowList1 = (ProcessingWindowList)paramInputStream.read_value(ProcessingWindowList.class);
/* 1632:     */     ProcessingWindowList localProcessingWindowList2;
/* 1633:     */     try
/* 1634:     */     {
/* 1635:1357 */       localProcessingWindowList2 = this.target.getProcessingWindows(localProcessingWindowList1);
/* 1636:     */     }
/* 1637:     */     catch (FFSException localFFSException)
/* 1638:     */     {
/* 1639:1359 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1640:1360 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1641:1361 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1642:1362 */       localOutputStream2.write_string(str);
/* 1643:1363 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1644:1364 */       return localOutputStream2;
/* 1645:     */     }
/* 1646:1366 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1647:1367 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1648:1368 */     localOutputStream1.write_value(localProcessingWindowList2, ProcessingWindowList.class);
/* 1649:1369 */     return localOutputStream1;
/* 1650:     */   }
/* 1651:     */   
/* 1652:     */   private org.omg.CORBA.portable.OutputStream getScheduleConfigByCategory(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1653:     */     throws Throwable
/* 1654:     */   {
/* 1655:1373 */     InstructionType localInstructionType = (InstructionType)paramInputStream.read_value(InstructionType.class);
/* 1656:     */     InstructionType[] arrayOfInstructionType;
/* 1657:     */     try
/* 1658:     */     {
/* 1659:1376 */       arrayOfInstructionType = this.target.getScheduleConfigByCategory(localInstructionType);
/* 1660:     */     }
/* 1661:     */     catch (FFSException localFFSException)
/* 1662:     */     {
/* 1663:1378 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1664:1379 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1665:1380 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1666:1381 */       localOutputStream2.write_string(str);
/* 1667:1382 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1668:1383 */       return localOutputStream2;
/* 1669:     */     }
/* 1670:1385 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1671:1386 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1672:1387 */     localOutputStream1.write_value(cast_array(arrayOfInstructionType), new InstructionType[0].getClass());
/* 1673:1388 */     return localOutputStream1;
/* 1674:     */   }
/* 1675:     */   
/* 1676:     */   private org.omg.CORBA.portable.OutputStream addScheduleConfig(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1677:     */     throws Throwable
/* 1678:     */   {
/* 1679:1392 */     InstructionType localInstructionType = (InstructionType)paramInputStream.read_value(InstructionType.class);
/* 1680:     */     try
/* 1681:     */     {
/* 1682:1394 */       this.target.addScheduleConfig(localInstructionType);
/* 1683:     */     }
/* 1684:     */     catch (FFSException localFFSException)
/* 1685:     */     {
/* 1686:1396 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1687:1397 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1688:1398 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1689:1399 */       localOutputStream1.write_string(str);
/* 1690:1400 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/* 1691:1401 */       return localOutputStream1;
/* 1692:     */     }
/* 1693:1403 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1694:1404 */     return localOutputStream;
/* 1695:     */   }
/* 1696:     */   
/* 1697:     */   private org.omg.CORBA.portable.OutputStream deleteScheduleConfig(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1698:     */     throws Throwable
/* 1699:     */   {
/* 1700:1408 */     InstructionType localInstructionType = (InstructionType)paramInputStream.read_value(InstructionType.class);
/* 1701:     */     try
/* 1702:     */     {
/* 1703:1410 */       this.target.deleteScheduleConfig(localInstructionType);
/* 1704:     */     }
/* 1705:     */     catch (FFSException localFFSException)
/* 1706:     */     {
/* 1707:1412 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1708:1413 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1709:1414 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1710:1415 */       localOutputStream1.write_string(str);
/* 1711:1416 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/* 1712:1417 */       return localOutputStream1;
/* 1713:     */     }
/* 1714:1419 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1715:1420 */     return localOutputStream;
/* 1716:     */   }
/* 1717:     */   
/* 1718:     */   private org.omg.CORBA.portable.OutputStream deleteCutOff(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1719:     */     throws Throwable
/* 1720:     */   {
/* 1721:1424 */     CutOffInfo localCutOffInfo1 = (CutOffInfo)paramInputStream.read_value(CutOffInfo.class);
/* 1722:     */     CutOffInfo localCutOffInfo2;
/* 1723:     */     try
/* 1724:     */     {
/* 1725:1427 */       localCutOffInfo2 = this.target.deleteCutOff(localCutOffInfo1);
/* 1726:     */     }
/* 1727:     */     catch (FFSException localFFSException)
/* 1728:     */     {
/* 1729:1429 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1730:1430 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1731:1431 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1732:1432 */       localOutputStream2.write_string(str);
/* 1733:1433 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1734:1434 */       return localOutputStream2;
/* 1735:     */     }
/* 1736:1436 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1737:1437 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1738:1438 */     localOutputStream1.write_value(localCutOffInfo2, CutOffInfo.class);
/* 1739:1439 */     return localOutputStream1;
/* 1740:     */   }
/* 1741:     */   
/* 1742:     */   private org.omg.CORBA.portable.OutputStream addCutOff(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1743:     */     throws Throwable
/* 1744:     */   {
/* 1745:1443 */     CutOffInfo localCutOffInfo1 = (CutOffInfo)paramInputStream.read_value(CutOffInfo.class);
/* 1746:     */     CutOffInfo localCutOffInfo2;
/* 1747:     */     try
/* 1748:     */     {
/* 1749:1446 */       localCutOffInfo2 = this.target.addCutOff(localCutOffInfo1);
/* 1750:     */     }
/* 1751:     */     catch (FFSException localFFSException)
/* 1752:     */     {
/* 1753:1448 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1754:1449 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1755:1450 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1756:1451 */       localOutputStream2.write_string(str);
/* 1757:1452 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1758:1453 */       return localOutputStream2;
/* 1759:     */     }
/* 1760:1455 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1761:1456 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1762:1457 */     localOutputStream1.write_value(localCutOffInfo2, CutOffInfo.class);
/* 1763:1458 */     return localOutputStream1;
/* 1764:     */   }
/* 1765:     */   
/* 1766:     */   private org.omg.CORBA.portable.OutputStream modCutOff(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1767:     */     throws Throwable
/* 1768:     */   {
/* 1769:1462 */     CutOffInfo localCutOffInfo1 = (CutOffInfo)paramInputStream.read_value(CutOffInfo.class);
/* 1770:     */     CutOffInfo localCutOffInfo2;
/* 1771:     */     try
/* 1772:     */     {
/* 1773:1465 */       localCutOffInfo2 = this.target.modCutOff(localCutOffInfo1);
/* 1774:     */     }
/* 1775:     */     catch (FFSException localFFSException)
/* 1776:     */     {
/* 1777:1467 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1778:1468 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1779:1469 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1780:1470 */       localOutputStream2.write_string(str);
/* 1781:1471 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1782:1472 */       return localOutputStream2;
/* 1783:     */     }
/* 1784:1474 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1785:1475 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1786:1476 */     localOutputStream1.write_value(localCutOffInfo2, CutOffInfo.class);
/* 1787:1477 */     return localOutputStream1;
/* 1788:     */   }
/* 1789:     */   
/* 1790:     */   private org.omg.CORBA.portable.OutputStream getCutOff(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1791:     */     throws Throwable
/* 1792:     */   {
/* 1793:1481 */     CutOffInfo localCutOffInfo1 = (CutOffInfo)paramInputStream.read_value(CutOffInfo.class);
/* 1794:     */     CutOffInfo localCutOffInfo2;
/* 1795:     */     try
/* 1796:     */     {
/* 1797:1484 */       localCutOffInfo2 = this.target.getCutOff(localCutOffInfo1);
/* 1798:     */     }
/* 1799:     */     catch (FFSException localFFSException)
/* 1800:     */     {
/* 1801:1486 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1802:1487 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1803:1488 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1804:1489 */       localOutputStream2.write_string(str);
/* 1805:1490 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1806:1491 */       return localOutputStream2;
/* 1807:     */     }
/* 1808:1493 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1809:1494 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1810:1495 */     localOutputStream1.write_value(localCutOffInfo2, CutOffInfo.class);
/* 1811:1496 */     return localOutputStream1;
/* 1812:     */   }
/* 1813:     */   
/* 1814:     */   private org.omg.CORBA.portable.OutputStream getCutOffList(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1815:     */     throws Throwable
/* 1816:     */   {
/* 1817:1500 */     CutOffInfoList localCutOffInfoList1 = (CutOffInfoList)paramInputStream.read_value(CutOffInfoList.class);
/* 1818:     */     CutOffInfoList localCutOffInfoList2;
/* 1819:     */     try
/* 1820:     */     {
/* 1821:1503 */       localCutOffInfoList2 = this.target.getCutOffList(localCutOffInfoList1);
/* 1822:     */     }
/* 1823:     */     catch (FFSException localFFSException)
/* 1824:     */     {
/* 1825:1505 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1826:1506 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1827:1507 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1828:1508 */       localOutputStream2.write_string(str);
/* 1829:1509 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1830:1510 */       return localOutputStream2;
/* 1831:     */     }
/* 1832:1512 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1833:1513 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1834:1514 */     localOutputStream1.write_value(localCutOffInfoList2, CutOffInfoList.class);
/* 1835:1515 */     return localOutputStream1;
/* 1836:     */   }
/* 1837:     */   
/* 1838:     */   private org.omg.CORBA.portable.OutputStream getScheduleCategoryInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1839:     */     throws Throwable
/* 1840:     */   {
/* 1841:1519 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1842:1520 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1843:     */     ScheduleCategoryInfo localScheduleCategoryInfo;
/* 1844:     */     try
/* 1845:     */     {
/* 1846:1523 */       localScheduleCategoryInfo = this.target.getScheduleCategoryInfo(str1, str2);
/* 1847:     */     }
/* 1848:     */     catch (FFSException localFFSException)
/* 1849:     */     {
/* 1850:1525 */       String str3 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1851:1526 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1852:1527 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1853:1528 */       localOutputStream2.write_string(str3);
/* 1854:1529 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1855:1530 */       return localOutputStream2;
/* 1856:     */     }
/* 1857:1532 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1858:1533 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1859:1534 */     localOutputStream1.write_value(localScheduleCategoryInfo, ScheduleCategoryInfo.class);
/* 1860:1535 */     return localOutputStream1;
/* 1861:     */   }
/* 1862:     */   
/* 1863:     */   private org.omg.CORBA.portable.OutputStream modScheduleCategoryInfo(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1864:     */     throws Throwable
/* 1865:     */   {
/* 1866:1539 */     ScheduleCategoryInfo localScheduleCategoryInfo1 = (ScheduleCategoryInfo)paramInputStream.read_value(ScheduleCategoryInfo.class);
/* 1867:     */     ScheduleCategoryInfo localScheduleCategoryInfo2;
/* 1868:     */     try
/* 1869:     */     {
/* 1870:1542 */       localScheduleCategoryInfo2 = this.target.modScheduleCategoryInfo(localScheduleCategoryInfo1);
/* 1871:     */     }
/* 1872:     */     catch (FFSException localFFSException)
/* 1873:     */     {
/* 1874:1544 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1875:1545 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1876:1546 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1877:1547 */       localOutputStream2.write_string(str);
/* 1878:1548 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1879:1549 */       return localOutputStream2;
/* 1880:     */     }
/* 1881:1551 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1882:1552 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1883:1553 */     localOutputStream1.write_value(localScheduleCategoryInfo2, ScheduleCategoryInfo.class);
/* 1884:1554 */     return localOutputStream1;
/* 1885:     */   }
/* 1886:     */   
/* 1887:     */   private org.omg.CORBA.portable.OutputStream cleanup__CORBA_WStringValue__CORBA_WStringValue__long__java_util_HashMap(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1888:     */     throws Throwable
/* 1889:     */   {
/* 1890:1558 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1891:1559 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1892:1560 */     int i = paramInputStream.read_long();
/* 1893:1561 */     HashMap localHashMap = (HashMap)paramInputStream.read_value(HashMap.class);
/* 1894:1562 */     this.target.cleanup(str1, str2, i, localHashMap);
/* 1895:1563 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1896:1564 */     return localOutputStream;
/* 1897:     */   }
/* 1898:     */   
/* 1899:     */   private org.omg.CORBA.portable.OutputStream cleanup__CORBA_WStringValue__java_util_ArrayList__java_util_ArrayList__java_util_HashMap(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1900:     */     throws Throwable
/* 1901:     */   {
/* 1902:1568 */     String str = (String)paramInputStream.read_value(String.class);
/* 1903:1569 */     ArrayList localArrayList1 = (ArrayList)paramInputStream.read_value(ArrayList.class);
/* 1904:1570 */     ArrayList localArrayList2 = (ArrayList)paramInputStream.read_value(ArrayList.class);
/* 1905:1571 */     HashMap localHashMap = (HashMap)paramInputStream.read_value(HashMap.class);
/* 1906:1572 */     this.target.cleanup(str, localArrayList1, localArrayList2, localHashMap);
/* 1907:1573 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1908:1574 */     return localOutputStream;
/* 1909:     */   }
/* 1910:     */   
/* 1911:     */   private org.omg.CORBA.portable.OutputStream cleanup__java_util_ArrayList__java_util_ArrayList__java_util_ArrayList__java_util_HashMap(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1912:     */     throws Throwable
/* 1913:     */   {
/* 1914:1578 */     ArrayList localArrayList1 = (ArrayList)paramInputStream.read_value(ArrayList.class);
/* 1915:1579 */     ArrayList localArrayList2 = (ArrayList)paramInputStream.read_value(ArrayList.class);
/* 1916:1580 */     ArrayList localArrayList3 = (ArrayList)paramInputStream.read_value(ArrayList.class);
/* 1917:1581 */     HashMap localHashMap = (HashMap)paramInputStream.read_value(HashMap.class);
/* 1918:1582 */     this.target.cleanup(localArrayList1, localArrayList2, localArrayList3, localHashMap);
/* 1919:1583 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1920:1584 */     return localOutputStream;
/* 1921:     */   }
/* 1922:     */   
/* 1923:     */   private org.omg.CORBA.portable.OutputStream getCutOffActivityList(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1924:     */     throws Throwable
/* 1925:     */   {
/* 1926:1588 */     CutOffActivityInfoList localCutOffActivityInfoList1 = (CutOffActivityInfoList)paramInputStream.read_value(CutOffActivityInfoList.class);
/* 1927:     */     CutOffActivityInfoList localCutOffActivityInfoList2;
/* 1928:     */     try
/* 1929:     */     {
/* 1930:1591 */       localCutOffActivityInfoList2 = this.target.getCutOffActivityList(localCutOffActivityInfoList1);
/* 1931:     */     }
/* 1932:     */     catch (FFSException localFFSException)
/* 1933:     */     {
/* 1934:1593 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1935:1594 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1936:1595 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1937:1596 */       localOutputStream2.write_string(str);
/* 1938:1597 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1939:1598 */       return localOutputStream2;
/* 1940:     */     }
/* 1941:1600 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1942:1601 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1943:1602 */     localOutputStream1.write_value(localCutOffActivityInfoList2, CutOffActivityInfoList.class);
/* 1944:1603 */     return localOutputStream1;
/* 1945:     */   }
/* 1946:     */   
/* 1947:     */   private org.omg.CORBA.portable.OutputStream getScheduleActivityList(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1948:     */     throws Throwable
/* 1949:     */   {
/* 1950:1607 */     ScheduleActivityList localScheduleActivityList1 = (ScheduleActivityList)paramInputStream.read_value(ScheduleActivityList.class);
/* 1951:     */     ScheduleActivityList localScheduleActivityList2;
/* 1952:     */     try
/* 1953:     */     {
/* 1954:1610 */       localScheduleActivityList2 = this.target.getScheduleActivityList(localScheduleActivityList1);
/* 1955:     */     }
/* 1956:     */     catch (FFSException localFFSException)
/* 1957:     */     {
/* 1958:1612 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1959:1613 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 1960:1614 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1961:1615 */       localOutputStream2.write_string(str);
/* 1962:1616 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 1963:1617 */       return localOutputStream2;
/* 1964:     */     }
/* 1965:1619 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1966:1620 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 1967:1621 */     localOutputStream1.write_value(localScheduleActivityList2, ScheduleActivityList.class);
/* 1968:1622 */     return localOutputStream1;
/* 1969:     */   }
/* 1970:     */   
/* 1971:     */   private org.omg.CORBA.portable.OutputStream rerunCutOff(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1972:     */     throws Throwable
/* 1973:     */   {
/* 1974:1626 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1975:1627 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 1976:1628 */     String str3 = (String)paramInputStream.read_value(String.class);
/* 1977:1629 */     String str4 = (String)paramInputStream.read_value(String.class);
/* 1978:     */     try
/* 1979:     */     {
/* 1980:1631 */       this.target.rerunCutOff(str1, str2, str3, str4);
/* 1981:     */     }
/* 1982:     */     catch (FFSException localFFSException)
/* 1983:     */     {
/* 1984:1633 */       String str5 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 1985:1634 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 1986:1635 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 1987:1636 */       localOutputStream1.write_string(str5);
/* 1988:1637 */       localOutputStream1.write_value(localFFSException, FFSException.class);
/* 1989:1638 */       return localOutputStream1;
/* 1990:     */     }
/* 1991:1640 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 1992:1641 */     return localOutputStream;
/* 1993:     */   }
/* 1994:     */   
/* 1995:     */   private org.omg.CORBA.portable.OutputStream getGeneratedFileName(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 1996:     */     throws Throwable
/* 1997:     */   {
/* 1998:1645 */     String str1 = (String)paramInputStream.read_value(String.class);
/* 1999:1646 */     String str2 = (String)paramInputStream.read_value(String.class);
/* 2000:1647 */     String str3 = (String)paramInputStream.read_value(String.class);
/* 2001:     */     String str4;
/* 2002:     */     try
/* 2003:     */     {
/* 2004:1650 */       str4 = this.target.getGeneratedFileName(str1, str2, str3);
/* 2005:     */     }
/* 2006:     */     catch (FFSException localFFSException)
/* 2007:     */     {
/* 2008:1652 */       String str5 = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2009:1653 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2010:1654 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2011:1655 */       localOutputStream2.write_string(str5);
/* 2012:1656 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2013:1657 */       return localOutputStream2;
/* 2014:     */     }
/* 2015:1659 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2016:1660 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2017:1661 */     localOutputStream1.write_value(str4, String.class);
/* 2018:1662 */     return localOutputStream1;
/* 2019:     */   }
/* 2020:     */   
/* 2021:     */   private org.omg.CORBA.portable.OutputStream importCalendar(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2022:     */     throws Throwable
/* 2023:     */   {
/* 2024:1666 */     SmartCalendarFile localSmartCalendarFile1 = (SmartCalendarFile)paramInputStream.read_value(SmartCalendarFile.class);
/* 2025:     */     SmartCalendarFile localSmartCalendarFile2;
/* 2026:     */     try
/* 2027:     */     {
/* 2028:1669 */       localSmartCalendarFile2 = this.target.importCalendar(localSmartCalendarFile1);
/* 2029:     */     }
/* 2030:     */     catch (FFSException localFFSException)
/* 2031:     */     {
/* 2032:1671 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2033:1672 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2034:1673 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2035:1674 */       localOutputStream2.write_string(str);
/* 2036:1675 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2037:1676 */       return localOutputStream2;
/* 2038:     */     }
/* 2039:1678 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2040:1679 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2041:1680 */     localOutputStream1.write_value(localSmartCalendarFile2, SmartCalendarFile.class);
/* 2042:1681 */     return localOutputStream1;
/* 2043:     */   }
/* 2044:     */   
/* 2045:     */   private org.omg.CORBA.portable.OutputStream exportCalendar(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2046:     */     throws Throwable
/* 2047:     */   {
/* 2048:1685 */     SmartCalendarFile localSmartCalendarFile1 = (SmartCalendarFile)paramInputStream.read_value(SmartCalendarFile.class);
/* 2049:     */     SmartCalendarFile localSmartCalendarFile2;
/* 2050:     */     try
/* 2051:     */     {
/* 2052:1688 */       localSmartCalendarFile2 = this.target.exportCalendar(localSmartCalendarFile1);
/* 2053:     */     }
/* 2054:     */     catch (FFSException localFFSException)
/* 2055:     */     {
/* 2056:1690 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2057:1691 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2058:1692 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2059:1693 */       localOutputStream2.write_string(str);
/* 2060:1694 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2061:1695 */       return localOutputStream2;
/* 2062:     */     }
/* 2063:1697 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2064:1698 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2065:1699 */     localOutputStream1.write_value(localSmartCalendarFile2, SmartCalendarFile.class);
/* 2066:1700 */     return localOutputStream1;
/* 2067:     */   }
/* 2068:     */   
/* 2069:     */   private org.omg.CORBA.portable.OutputStream addGlobalPayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2070:     */     throws Throwable
/* 2071:     */   {
/* 2072:1704 */     PayeeInfo localPayeeInfo1 = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/* 2073:     */     PayeeInfo localPayeeInfo2;
/* 2074:     */     try
/* 2075:     */     {
/* 2076:1707 */       localPayeeInfo2 = this.target.addGlobalPayee(localPayeeInfo1);
/* 2077:     */     }
/* 2078:     */     catch (FFSException localFFSException)
/* 2079:     */     {
/* 2080:1709 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2081:1710 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2082:1711 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2083:1712 */       localOutputStream2.write_string(str);
/* 2084:1713 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2085:1714 */       return localOutputStream2;
/* 2086:     */     }
/* 2087:1716 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2088:1717 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2089:1718 */     localOutputStream1.write_value(localPayeeInfo2, PayeeInfo.class);
/* 2090:1719 */     return localOutputStream1;
/* 2091:     */   }
/* 2092:     */   
/* 2093:     */   private org.omg.CORBA.portable.OutputStream deleteGlobalPayee(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 2094:     */     throws Throwable
/* 2095:     */   {
/* 2096:1723 */     PayeeInfo localPayeeInfo1 = (PayeeInfo)paramInputStream.read_value(PayeeInfo.class);
/* 2097:     */     PayeeInfo localPayeeInfo2;
/* 2098:     */     try
/* 2099:     */     {
/* 2100:1726 */       localPayeeInfo2 = this.target.deleteGlobalPayee(localPayeeInfo1);
/* 2101:     */     }
/* 2102:     */     catch (FFSException localFFSException)
/* 2103:     */     {
/* 2104:1728 */       String str = "IDL:com/ffusion/ffs/interfaces/FFSEx:1.0";
/* 2105:1729 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream2 = 
/* 2106:1730 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 2107:1731 */       localOutputStream2.write_string(str);
/* 2108:1732 */       localOutputStream2.write_value(localFFSException, FFSException.class);
/* 2109:1733 */       return localOutputStream2;
/* 2110:     */     }
/* 2111:1735 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 2112:1736 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 2113:1737 */     localOutputStream1.write_value(localPayeeInfo2, PayeeInfo.class);
/* 2114:1738 */     return localOutputStream1;
/* 2115:     */   }
/* 2116:     */   
/* 2117:     */   private Serializable cast_array(java.lang.Object paramObject)
/* 2118:     */   {
/* 2119:1745 */     return (Serializable)paramObject;
/* 2120:     */   }
/* 2121:     */ }


/* Location:           D:\drops\jd\jars\Deployed_BPWAdmin.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB._EJSRemoteStatelessIBPWAdmin_Tie
 * JD-Core Version:    0.7.0.1
 */