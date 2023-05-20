/*    1:     */ package com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices;
/*    2:     */ 
/*    3:     */ import com.ffusion.ffs.bpw.interfaces.BPWHist;
/*    4:     */ import com.ffusion.ffs.bpw.interfaces.BPWHistHelper;
/*    5:     */ import com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfo;
/*    6:     */ import com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfoSeqHelper;
/*    7:     */ import com.ffusion.ffs.bpw.interfaces.CustPayeeRslt;
/*    8:     */ import com.ffusion.ffs.bpw.interfaces.CustPayeeRsltSeqHelper;
/*    9:     */ import com.ffusion.ffs.bpw.interfaces.CustomerBankInfo;
/*   10:     */ import com.ffusion.ffs.bpw.interfaces.CustomerBankInfoSeqHelper;
/*   11:     */ import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
/*   12:     */ import com.ffusion.ffs.bpw.interfaces.CustomerInfoSeqHelper;
/*   13:     */ import com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfo;
/*   14:     */ import com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfoSeqHelper;
/*   15:     */ import com.ffusion.ffs.bpw.interfaces.FundsAllocRslt;
/*   16:     */ import com.ffusion.ffs.bpw.interfaces.FundsAllocRsltSeqHelper;
/*   17:     */ import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
/*   18:     */ import com.ffusion.ffs.bpw.interfaces.IntraTrnInfoSeqHelper;
/*   19:     */ import com.ffusion.ffs.bpw.interfaces.IntraTrnRslt;
/*   20:     */ import com.ffusion.ffs.bpw.interfaces.IntraTrnRsltSeqHelper;
/*   21:     */ import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
/*   22:     */ import com.ffusion.ffs.bpw.interfaces.PayeeInfoSeqHelper;
/*   23:     */ import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
/*   24:     */ import com.ffusion.ffs.bpw.interfaces.PayeeRslt;
/*   25:     */ import com.ffusion.ffs.bpw.interfaces.PayeeRsltSeqHelper;
/*   26:     */ import com.ffusion.ffs.bpw.interfaces.PmtInfo;
/*   27:     */ import com.ffusion.ffs.bpw.interfaces.PmtInfoSeqHelper;
/*   28:     */ import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
/*   29:     */ import com.ffusion.ffs.bpw.interfaces.PmtTrnRsltSeqHelper;
/*   30:     */ import com.ffusion.ffs.bpw.interfaces.RecIntraTrnInfo;
/*   31:     */ import com.ffusion.ffs.bpw.interfaces.RecIntraTrnInfoSeqHelper;
/*   32:     */ import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
/*   33:     */ import com.ffusion.ffs.bpw.interfaces.RecPmtInfoSeqHelper;
/*   34:     */ import com.ffusion.ffs.bpw.util.AccountTypesMap;
/*   35:     */ import com.ffusion.ffs.interfaces.FFSException;
/*   36:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserData;
/*   37:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserDataHelper;
/*   38:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1;
/*   39:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1Helper;
/*   40:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1;
/*   41:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1Helper;
/*   42:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1SeqHelper;
/*   43:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1;
/*   44:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1Helper;
/*   45:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1;
/*   46:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1Helper;
/*   47:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1;
/*   48:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1Helper;
/*   49:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1;
/*   50:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1Helper;
/*   51:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1;
/*   52:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1Helper;
/*   53:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1;
/*   54:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1Helper;
/*   55:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRqV1;
/*   56:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRqV1Helper;
/*   57:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRsV1;
/*   58:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRsV1Helper;
/*   59:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1;
/*   60:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1Helper;
/*   61:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1;
/*   62:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1Helper;
/*   63:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1SeqHelper;
/*   64:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1;
/*   65:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1Helper;
/*   66:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
/*   67:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Helper;
/*   68:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1;
/*   69:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1Helper;
/*   70:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1;
/*   71:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1Helper;
/*   72:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1;
/*   73:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1Helper;
/*   74:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1;
/*   75:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1Helper;
/*   76:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1;
/*   77:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1Helper;
/*   78:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1;
/*   79:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1Helper;
/*   80:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1;
/*   81:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1Helper;
/*   82:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1;
/*   83:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1Helper;
/*   84:     */ import com.sybase.ejb.cts.StringSeqHelper;
/*   85:     */ import com.sybase.ejb.lwc.EJBContext;
/*   86:     */ import com.sybase.ejb.lwc.EJBObject;
/*   87:     */ import com.sybase.ejb.lwc.JavaComponent;
/*   88:     */ import com.sybase.ejb.lwc.Stub;
/*   89:     */ import com.sybase.jaguar.logger.Logger;
/*   90:     */ import java.rmi.RemoteException;
/*   91:     */ import javax.ejb.EJBException;
/*   92:     */ 
/*   93:     */ public class _lwc_rs_OFX200BPWServices_OFX200BPWServicesBean
/*   94:     */   extends EJBObject
/*   95:     */   implements IOFX200BPWServices
/*   96:     */ {
/*   97:  14 */   private static JavaComponent __component = JavaComponent.get("OFX200BPWServices/OFX200BPWServicesBean");
/*   98:     */   
/*   99:     */   public _lwc_rs_OFX200BPWServices_OFX200BPWServicesBean(Object paramObject)
/*  100:     */   {
/*  101:  19 */     super(__component);
/*  102:  20 */     this.key = paramObject;
/*  103:     */   }
/*  104:     */   
/*  105:     */   private EJBContext _create()
/*  106:     */     throws Exception
/*  107:     */   {
/*  108:  26 */     EJBContext localEJBContext = this._comp.getInstance();
/*  109:  27 */     OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  110:  28 */     if (localEJBContext.debug) {
/*  111:  30 */       localEJBContext.logger.debug(localEJBContext.debugMsg("ejbCreate"));
/*  112:     */     }
/*  113:  32 */     localOFX200BPWServicesBean.ejbCreate();
/*  114:  33 */     return localEJBContext;
/*  115:     */   }
/*  116:     */   
/*  117:     */   public int addCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
/*  118:     */     throws RemoteException
/*  119:     */   {
/*  120:  40 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  121:  41 */     EJBContext localEJBContext = null;
/*  122:     */     try
/*  123:     */     {
/*  124:  44 */       localEJBContext = this._comp.getPooledInstance();
/*  125:  45 */       if (localEJBContext == null) {
/*  126:  47 */         localEJBContext = _create();
/*  127:     */       }
/*  128:  49 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  129:  50 */       if (localEJBContext.debug) {
/*  130:  52 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addCustomers"));
/*  131:     */       }
/*  132:  54 */       int j = localOFX200BPWServicesBean
/*  133:  55 */         .addCustomers(
/*  134:  56 */         CustomerInfoSeqHelper.clone(paramArrayOfCustomerInfo));
/*  135:     */       
/*  136:  58 */       localEJBContext.returnToPool();
/*  137:  59 */       return j;
/*  138:     */     }
/*  139:     */     catch (Exception localException)
/*  140:     */     {
/*  141:  63 */       if (localEJBContext != null) {
/*  142:  65 */         localEJBContext.throwRemote(localException);
/*  143:     */       }
/*  144:  67 */       throw new EJBException(localException);
/*  145:     */     }
/*  146:     */     finally
/*  147:     */     {
/*  148:  71 */       this._comp.setCurrent(localJavaComponent);
/*  149:     */     }
/*  150:     */   }
/*  151:     */   
/*  152:     */   public int modifyCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
/*  153:     */     throws RemoteException
/*  154:     */   {
/*  155:  79 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  156:  80 */     EJBContext localEJBContext = null;
/*  157:     */     try
/*  158:     */     {
/*  159:  83 */       localEJBContext = this._comp.getPooledInstance();
/*  160:  84 */       if (localEJBContext == null) {
/*  161:  86 */         localEJBContext = _create();
/*  162:     */       }
/*  163:  88 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  164:  89 */       if (localEJBContext.debug) {
/*  165:  91 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modifyCustomers"));
/*  166:     */       }
/*  167:  93 */       int j = localOFX200BPWServicesBean
/*  168:  94 */         .modifyCustomers(
/*  169:  95 */         CustomerInfoSeqHelper.clone(paramArrayOfCustomerInfo));
/*  170:     */       
/*  171:  97 */       localEJBContext.returnToPool();
/*  172:  98 */       return j;
/*  173:     */     }
/*  174:     */     catch (Exception localException)
/*  175:     */     {
/*  176: 102 */       if (localEJBContext != null) {
/*  177: 104 */         localEJBContext.throwRemote(localException);
/*  178:     */       }
/*  179: 106 */       throw new EJBException(localException);
/*  180:     */     }
/*  181:     */     finally
/*  182:     */     {
/*  183: 110 */       this._comp.setCurrent(localJavaComponent);
/*  184:     */     }
/*  185:     */   }
/*  186:     */   
/*  187:     */   public int deleteCustomers(String[] paramArrayOfString)
/*  188:     */     throws RemoteException
/*  189:     */   {
/*  190: 118 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  191: 119 */     EJBContext localEJBContext = null;
/*  192:     */     try
/*  193:     */     {
/*  194: 122 */       localEJBContext = this._comp.getPooledInstance();
/*  195: 123 */       if (localEJBContext == null) {
/*  196: 125 */         localEJBContext = _create();
/*  197:     */       }
/*  198: 127 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  199: 128 */       if (localEJBContext.debug) {
/*  200: 130 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteCustomers"));
/*  201:     */       }
/*  202: 132 */       int j = localOFX200BPWServicesBean
/*  203: 133 */         .deleteCustomers(
/*  204: 134 */         StringSeqHelper.clone(paramArrayOfString));
/*  205:     */       
/*  206: 136 */       localEJBContext.returnToPool();
/*  207: 137 */       return j;
/*  208:     */     }
/*  209:     */     catch (Exception localException)
/*  210:     */     {
/*  211: 141 */       if (localEJBContext != null) {
/*  212: 143 */         localEJBContext.throwRemote(localException);
/*  213:     */       }
/*  214: 145 */       throw new EJBException(localException);
/*  215:     */     }
/*  216:     */     finally
/*  217:     */     {
/*  218: 149 */       this._comp.setCurrent(localJavaComponent);
/*  219:     */     }
/*  220:     */   }
/*  221:     */   
/*  222:     */   public int deleteCustomers(String[] paramArrayOfString, int paramInt)
/*  223:     */     throws RemoteException
/*  224:     */   {
/*  225: 158 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  226: 159 */     EJBContext localEJBContext = null;
/*  227:     */     try
/*  228:     */     {
/*  229: 162 */       localEJBContext = this._comp.getPooledInstance();
/*  230: 163 */       if (localEJBContext == null) {
/*  231: 165 */         localEJBContext = _create();
/*  232:     */       }
/*  233: 167 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  234: 168 */       if (localEJBContext.debug) {
/*  235: 170 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteCustomers"));
/*  236:     */       }
/*  237: 172 */       int j = localOFX200BPWServicesBean
/*  238: 173 */         .deleteCustomers(
/*  239: 174 */         StringSeqHelper.clone(paramArrayOfString), 
/*  240: 175 */         paramInt);
/*  241:     */       
/*  242: 177 */       localEJBContext.returnToPool();
/*  243: 178 */       return j;
/*  244:     */     }
/*  245:     */     catch (Exception localException)
/*  246:     */     {
/*  247: 182 */       if (localEJBContext != null) {
/*  248: 184 */         localEJBContext.throwRemote(localException);
/*  249:     */       }
/*  250: 186 */       throw new EJBException(localException);
/*  251:     */     }
/*  252:     */     finally
/*  253:     */     {
/*  254: 190 */       this._comp.setCurrent(localJavaComponent);
/*  255:     */     }
/*  256:     */   }
/*  257:     */   
/*  258:     */   public int deactivateCustomers(String[] paramArrayOfString)
/*  259:     */     throws RemoteException
/*  260:     */   {
/*  261: 198 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  262: 199 */     EJBContext localEJBContext = null;
/*  263:     */     try
/*  264:     */     {
/*  265: 202 */       localEJBContext = this._comp.getPooledInstance();
/*  266: 203 */       if (localEJBContext == null) {
/*  267: 205 */         localEJBContext = _create();
/*  268:     */       }
/*  269: 207 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  270: 208 */       if (localEJBContext.debug) {
/*  271: 210 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deactivateCustomers"));
/*  272:     */       }
/*  273: 212 */       int j = localOFX200BPWServicesBean
/*  274: 213 */         .deactivateCustomers(
/*  275: 214 */         StringSeqHelper.clone(paramArrayOfString));
/*  276:     */       
/*  277: 216 */       localEJBContext.returnToPool();
/*  278: 217 */       return j;
/*  279:     */     }
/*  280:     */     catch (Exception localException)
/*  281:     */     {
/*  282: 221 */       if (localEJBContext != null) {
/*  283: 223 */         localEJBContext.throwRemote(localException);
/*  284:     */       }
/*  285: 225 */       throw new EJBException(localException);
/*  286:     */     }
/*  287:     */     finally
/*  288:     */     {
/*  289: 229 */       this._comp.setCurrent(localJavaComponent);
/*  290:     */     }
/*  291:     */   }
/*  292:     */   
/*  293:     */   public int activateCustomers(String[] paramArrayOfString)
/*  294:     */     throws RemoteException
/*  295:     */   {
/*  296: 237 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  297: 238 */     EJBContext localEJBContext = null;
/*  298:     */     try
/*  299:     */     {
/*  300: 241 */       localEJBContext = this._comp.getPooledInstance();
/*  301: 242 */       if (localEJBContext == null) {
/*  302: 244 */         localEJBContext = _create();
/*  303:     */       }
/*  304: 246 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  305: 247 */       if (localEJBContext.debug) {
/*  306: 249 */         localEJBContext.logger.debug(localEJBContext.debugMsg("activateCustomers"));
/*  307:     */       }
/*  308: 251 */       int j = localOFX200BPWServicesBean
/*  309: 252 */         .activateCustomers(
/*  310: 253 */         StringSeqHelper.clone(paramArrayOfString));
/*  311:     */       
/*  312: 255 */       localEJBContext.returnToPool();
/*  313: 256 */       return j;
/*  314:     */     }
/*  315:     */     catch (Exception localException)
/*  316:     */     {
/*  317: 260 */       if (localEJBContext != null) {
/*  318: 262 */         localEJBContext.throwRemote(localException);
/*  319:     */       }
/*  320: 264 */       throw new EJBException(localException);
/*  321:     */     }
/*  322:     */     finally
/*  323:     */     {
/*  324: 268 */       this._comp.setCurrent(localJavaComponent);
/*  325:     */     }
/*  326:     */   }
/*  327:     */   
/*  328:     */   public CustomerInfo[] getCustomersInfo(String[] paramArrayOfString)
/*  329:     */     throws RemoteException
/*  330:     */   {
/*  331: 276 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  332: 277 */     EJBContext localEJBContext = null;
/*  333:     */     try
/*  334:     */     {
/*  335: 280 */       localEJBContext = this._comp.getPooledInstance();
/*  336: 281 */       if (localEJBContext == null) {
/*  337: 283 */         localEJBContext = _create();
/*  338:     */       }
/*  339: 285 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  340: 286 */       if (localEJBContext.debug) {
/*  341: 288 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomersInfo"));
/*  342:     */       }
/*  343: 290 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX200BPWServicesBean
/*  344: 291 */         .getCustomersInfo(
/*  345: 292 */         StringSeqHelper.clone(paramArrayOfString));
/*  346:     */       
/*  347: 294 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  348: 295 */       localEJBContext.returnToPool();
/*  349: 296 */       return arrayOfCustomerInfo2;
/*  350:     */     }
/*  351:     */     catch (Exception localException)
/*  352:     */     {
/*  353: 300 */       if (localEJBContext != null) {
/*  354: 302 */         localEJBContext.throwRemote(localException);
/*  355:     */       }
/*  356: 304 */       throw new EJBException(localException);
/*  357:     */     }
/*  358:     */     finally
/*  359:     */     {
/*  360: 308 */       this._comp.setCurrent(localJavaComponent);
/*  361:     */     }
/*  362:     */   }
/*  363:     */   
/*  364:     */   public CustomerInfo[] getCustomerByType(String paramString)
/*  365:     */     throws RemoteException
/*  366:     */   {
/*  367: 316 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  368: 317 */     EJBContext localEJBContext = null;
/*  369:     */     try
/*  370:     */     {
/*  371: 320 */       localEJBContext = this._comp.getPooledInstance();
/*  372: 321 */       if (localEJBContext == null) {
/*  373: 323 */         localEJBContext = _create();
/*  374:     */       }
/*  375: 325 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  376: 326 */       if (localEJBContext.debug) {
/*  377: 328 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByType"));
/*  378:     */       }
/*  379: 330 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX200BPWServicesBean
/*  380: 331 */         .getCustomerByType(
/*  381: 332 */         paramString);
/*  382:     */       
/*  383: 334 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  384: 335 */       localEJBContext.returnToPool();
/*  385: 336 */       return arrayOfCustomerInfo2;
/*  386:     */     }
/*  387:     */     catch (Exception localException)
/*  388:     */     {
/*  389: 340 */       if (localEJBContext != null) {
/*  390: 342 */         localEJBContext.throwRemote(localException);
/*  391:     */       }
/*  392: 344 */       throw new EJBException(localException);
/*  393:     */     }
/*  394:     */     finally
/*  395:     */     {
/*  396: 348 */       this._comp.setCurrent(localJavaComponent);
/*  397:     */     }
/*  398:     */   }
/*  399:     */   
/*  400:     */   public CustomerInfo[] getCustomerByFI(String paramString)
/*  401:     */     throws RemoteException
/*  402:     */   {
/*  403: 356 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  404: 357 */     EJBContext localEJBContext = null;
/*  405:     */     try
/*  406:     */     {
/*  407: 360 */       localEJBContext = this._comp.getPooledInstance();
/*  408: 361 */       if (localEJBContext == null) {
/*  409: 363 */         localEJBContext = _create();
/*  410:     */       }
/*  411: 365 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  412: 366 */       if (localEJBContext.debug) {
/*  413: 368 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByFI"));
/*  414:     */       }
/*  415: 370 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX200BPWServicesBean
/*  416: 371 */         .getCustomerByFI(
/*  417: 372 */         paramString);
/*  418:     */       
/*  419: 374 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  420: 375 */       localEJBContext.returnToPool();
/*  421: 376 */       return arrayOfCustomerInfo2;
/*  422:     */     }
/*  423:     */     catch (Exception localException)
/*  424:     */     {
/*  425: 380 */       if (localEJBContext != null) {
/*  426: 382 */         localEJBContext.throwRemote(localException);
/*  427:     */       }
/*  428: 384 */       throw new EJBException(localException);
/*  429:     */     }
/*  430:     */     finally
/*  431:     */     {
/*  432: 388 */       this._comp.setCurrent(localJavaComponent);
/*  433:     */     }
/*  434:     */   }
/*  435:     */   
/*  436:     */   public CustomerInfo[] getCustomerByCategory(String paramString)
/*  437:     */     throws RemoteException
/*  438:     */   {
/*  439: 396 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  440: 397 */     EJBContext localEJBContext = null;
/*  441:     */     try
/*  442:     */     {
/*  443: 400 */       localEJBContext = this._comp.getPooledInstance();
/*  444: 401 */       if (localEJBContext == null) {
/*  445: 403 */         localEJBContext = _create();
/*  446:     */       }
/*  447: 405 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  448: 406 */       if (localEJBContext.debug) {
/*  449: 408 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByCategory"));
/*  450:     */       }
/*  451: 410 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX200BPWServicesBean
/*  452: 411 */         .getCustomerByCategory(
/*  453: 412 */         paramString);
/*  454:     */       
/*  455: 414 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  456: 415 */       localEJBContext.returnToPool();
/*  457: 416 */       return arrayOfCustomerInfo2;
/*  458:     */     }
/*  459:     */     catch (Exception localException)
/*  460:     */     {
/*  461: 420 */       if (localEJBContext != null) {
/*  462: 422 */         localEJBContext.throwRemote(localException);
/*  463:     */       }
/*  464: 424 */       throw new EJBException(localException);
/*  465:     */     }
/*  466:     */     finally
/*  467:     */     {
/*  468: 428 */       this._comp.setCurrent(localJavaComponent);
/*  469:     */     }
/*  470:     */   }
/*  471:     */   
/*  472:     */   public CustomerInfo[] getCustomerByGroup(String paramString)
/*  473:     */     throws RemoteException
/*  474:     */   {
/*  475: 436 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  476: 437 */     EJBContext localEJBContext = null;
/*  477:     */     try
/*  478:     */     {
/*  479: 440 */       localEJBContext = this._comp.getPooledInstance();
/*  480: 441 */       if (localEJBContext == null) {
/*  481: 443 */         localEJBContext = _create();
/*  482:     */       }
/*  483: 445 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  484: 446 */       if (localEJBContext.debug) {
/*  485: 448 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByGroup"));
/*  486:     */       }
/*  487: 450 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX200BPWServicesBean
/*  488: 451 */         .getCustomerByGroup(
/*  489: 452 */         paramString);
/*  490:     */       
/*  491: 454 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  492: 455 */       localEJBContext.returnToPool();
/*  493: 456 */       return arrayOfCustomerInfo2;
/*  494:     */     }
/*  495:     */     catch (Exception localException)
/*  496:     */     {
/*  497: 460 */       if (localEJBContext != null) {
/*  498: 462 */         localEJBContext.throwRemote(localException);
/*  499:     */       }
/*  500: 464 */       throw new EJBException(localException);
/*  501:     */     }
/*  502:     */     finally
/*  503:     */     {
/*  504: 468 */       this._comp.setCurrent(localJavaComponent);
/*  505:     */     }
/*  506:     */   }
/*  507:     */   
/*  508:     */   public CustomerInfo[] getCustomerByTypeAndFI(String paramString1, String paramString2)
/*  509:     */     throws RemoteException
/*  510:     */   {
/*  511: 477 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  512: 478 */     EJBContext localEJBContext = null;
/*  513:     */     try
/*  514:     */     {
/*  515: 481 */       localEJBContext = this._comp.getPooledInstance();
/*  516: 482 */       if (localEJBContext == null) {
/*  517: 484 */         localEJBContext = _create();
/*  518:     */       }
/*  519: 486 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  520: 487 */       if (localEJBContext.debug) {
/*  521: 489 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByTypeAndFI"));
/*  522:     */       }
/*  523: 491 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX200BPWServicesBean
/*  524: 492 */         .getCustomerByTypeAndFI(
/*  525: 493 */         paramString1, 
/*  526: 494 */         paramString2);
/*  527:     */       
/*  528: 496 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  529: 497 */       localEJBContext.returnToPool();
/*  530: 498 */       return arrayOfCustomerInfo2;
/*  531:     */     }
/*  532:     */     catch (Exception localException)
/*  533:     */     {
/*  534: 502 */       if (localEJBContext != null) {
/*  535: 504 */         localEJBContext.throwRemote(localException);
/*  536:     */       }
/*  537: 506 */       throw new EJBException(localException);
/*  538:     */     }
/*  539:     */     finally
/*  540:     */     {
/*  541: 510 */       this._comp.setCurrent(localJavaComponent);
/*  542:     */     }
/*  543:     */   }
/*  544:     */   
/*  545:     */   public CustomerInfo[] getCustomerByCategoryAndFI(String paramString1, String paramString2)
/*  546:     */     throws RemoteException
/*  547:     */   {
/*  548: 519 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  549: 520 */     EJBContext localEJBContext = null;
/*  550:     */     try
/*  551:     */     {
/*  552: 523 */       localEJBContext = this._comp.getPooledInstance();
/*  553: 524 */       if (localEJBContext == null) {
/*  554: 526 */         localEJBContext = _create();
/*  555:     */       }
/*  556: 528 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  557: 529 */       if (localEJBContext.debug) {
/*  558: 531 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByCategoryAndFI"));
/*  559:     */       }
/*  560: 533 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX200BPWServicesBean
/*  561: 534 */         .getCustomerByCategoryAndFI(
/*  562: 535 */         paramString1, 
/*  563: 536 */         paramString2);
/*  564:     */       
/*  565: 538 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  566: 539 */       localEJBContext.returnToPool();
/*  567: 540 */       return arrayOfCustomerInfo2;
/*  568:     */     }
/*  569:     */     catch (Exception localException)
/*  570:     */     {
/*  571: 544 */       if (localEJBContext != null) {
/*  572: 546 */         localEJBContext.throwRemote(localException);
/*  573:     */       }
/*  574: 548 */       throw new EJBException(localException);
/*  575:     */     }
/*  576:     */     finally
/*  577:     */     {
/*  578: 552 */       this._comp.setCurrent(localJavaComponent);
/*  579:     */     }
/*  580:     */   }
/*  581:     */   
/*  582:     */   public CustomerInfo[] getCustomerByGroupAndFI(String paramString1, String paramString2)
/*  583:     */     throws RemoteException
/*  584:     */   {
/*  585: 561 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  586: 562 */     EJBContext localEJBContext = null;
/*  587:     */     try
/*  588:     */     {
/*  589: 565 */       localEJBContext = this._comp.getPooledInstance();
/*  590: 566 */       if (localEJBContext == null) {
/*  591: 568 */         localEJBContext = _create();
/*  592:     */       }
/*  593: 570 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  594: 571 */       if (localEJBContext.debug) {
/*  595: 573 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByGroupAndFI"));
/*  596:     */       }
/*  597: 575 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX200BPWServicesBean
/*  598: 576 */         .getCustomerByGroupAndFI(
/*  599: 577 */         paramString1, 
/*  600: 578 */         paramString2);
/*  601:     */       
/*  602: 580 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  603: 581 */       localEJBContext.returnToPool();
/*  604: 582 */       return arrayOfCustomerInfo2;
/*  605:     */     }
/*  606:     */     catch (Exception localException)
/*  607:     */     {
/*  608: 586 */       if (localEJBContext != null) {
/*  609: 588 */         localEJBContext.throwRemote(localException);
/*  610:     */       }
/*  611: 590 */       throw new EJBException(localException);
/*  612:     */     }
/*  613:     */     finally
/*  614:     */     {
/*  615: 594 */       this._comp.setCurrent(localJavaComponent);
/*  616:     */     }
/*  617:     */   }
/*  618:     */   
/*  619:     */   public PayeeInfo[] getLinkedPayees()
/*  620:     */     throws RemoteException
/*  621:     */   {
/*  622: 601 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  623: 602 */     EJBContext localEJBContext = null;
/*  624:     */     try
/*  625:     */     {
/*  626: 605 */       localEJBContext = this._comp.getPooledInstance();
/*  627: 606 */       if (localEJBContext == null) {
/*  628: 608 */         localEJBContext = _create();
/*  629:     */       }
/*  630: 610 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  631: 611 */       if (localEJBContext.debug) {
/*  632: 613 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getLinkedPayees"));
/*  633:     */       }
/*  634: 615 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX200BPWServicesBean
/*  635: 616 */         .getLinkedPayees();
/*  636:     */       
/*  637: 618 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/*  638: 619 */       localEJBContext.returnToPool();
/*  639: 620 */       return arrayOfPayeeInfo2;
/*  640:     */     }
/*  641:     */     catch (Exception localException)
/*  642:     */     {
/*  643: 624 */       if (localEJBContext != null) {
/*  644: 626 */         localEJBContext.throwRemote(localException);
/*  645:     */       }
/*  646: 628 */       throw new EJBException(localException);
/*  647:     */     }
/*  648:     */     finally
/*  649:     */     {
/*  650: 632 */       this._comp.setCurrent(localJavaComponent);
/*  651:     */     }
/*  652:     */   }
/*  653:     */   
/*  654:     */   public PayeeInfo[] getMostUsedPayees(int paramInt)
/*  655:     */     throws RemoteException
/*  656:     */   {
/*  657: 640 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  658: 641 */     EJBContext localEJBContext = null;
/*  659:     */     try
/*  660:     */     {
/*  661: 644 */       localEJBContext = this._comp.getPooledInstance();
/*  662: 645 */       if (localEJBContext == null) {
/*  663: 647 */         localEJBContext = _create();
/*  664:     */       }
/*  665: 649 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  666: 650 */       if (localEJBContext.debug) {
/*  667: 652 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getMostUsedPayees"));
/*  668:     */       }
/*  669: 654 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX200BPWServicesBean
/*  670: 655 */         .getMostUsedPayees(
/*  671: 656 */         paramInt);
/*  672:     */       
/*  673: 658 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/*  674: 659 */       localEJBContext.returnToPool();
/*  675: 660 */       return arrayOfPayeeInfo2;
/*  676:     */     }
/*  677:     */     catch (Exception localException)
/*  678:     */     {
/*  679: 664 */       if (localEJBContext != null) {
/*  680: 666 */         localEJBContext.throwRemote(localException);
/*  681:     */       }
/*  682: 668 */       throw new EJBException(localException);
/*  683:     */     }
/*  684:     */     finally
/*  685:     */     {
/*  686: 672 */       this._comp.setCurrent(localJavaComponent);
/*  687:     */     }
/*  688:     */   }
/*  689:     */   
/*  690:     */   public PayeeInfo[] getPreferredPayees(String paramString)
/*  691:     */     throws RemoteException
/*  692:     */   {
/*  693: 680 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  694: 681 */     EJBContext localEJBContext = null;
/*  695:     */     try
/*  696:     */     {
/*  697: 684 */       localEJBContext = this._comp.getPooledInstance();
/*  698: 685 */       if (localEJBContext == null) {
/*  699: 687 */         localEJBContext = _create();
/*  700:     */       }
/*  701: 689 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  702: 690 */       if (localEJBContext.debug) {
/*  703: 692 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPreferredPayees"));
/*  704:     */       }
/*  705: 694 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX200BPWServicesBean
/*  706: 695 */         .getPreferredPayees(
/*  707: 696 */         paramString);
/*  708:     */       
/*  709: 698 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/*  710: 699 */       localEJBContext.returnToPool();
/*  711: 700 */       return arrayOfPayeeInfo2;
/*  712:     */     }
/*  713:     */     catch (Exception localException)
/*  714:     */     {
/*  715: 704 */       if (localEJBContext != null) {
/*  716: 706 */         localEJBContext.throwRemote(localException);
/*  717:     */       }
/*  718: 708 */       throw new EJBException(localException);
/*  719:     */     }
/*  720:     */     finally
/*  721:     */     {
/*  722: 712 */       this._comp.setCurrent(localJavaComponent);
/*  723:     */     }
/*  724:     */   }
/*  725:     */   
/*  726:     */   public TypePmtSyncRsV1[] getPendingPmtsByCustomerID(String paramString, int paramInt)
/*  727:     */     throws RemoteException
/*  728:     */   {
/*  729: 721 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  730: 722 */     EJBContext localEJBContext = null;
/*  731:     */     try
/*  732:     */     {
/*  733: 725 */       localEJBContext = this._comp.getPooledInstance();
/*  734: 726 */       if (localEJBContext == null) {
/*  735: 728 */         localEJBContext = _create();
/*  736:     */       }
/*  737: 730 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  738: 731 */       if (localEJBContext.debug) {
/*  739: 733 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPendingPmtsByCustomerID"));
/*  740:     */       }
/*  741: 735 */       TypePmtSyncRsV1[] arrayOfTypePmtSyncRsV12 = localOFX200BPWServicesBean
/*  742: 736 */         .getPendingPmtsByCustomerID(
/*  743: 737 */         paramString, 
/*  744: 738 */         paramInt);
/*  745:     */       
/*  746: 740 */       arrayOfTypePmtSyncRsV12 = TypePmtSyncRsV1SeqHelper.clone(arrayOfTypePmtSyncRsV12);
/*  747: 741 */       localEJBContext.returnToPool();
/*  748: 742 */       return arrayOfTypePmtSyncRsV12;
/*  749:     */     }
/*  750:     */     catch (Exception localException)
/*  751:     */     {
/*  752: 746 */       if (localEJBContext != null) {
/*  753: 748 */         localEJBContext.throwRemote(localException);
/*  754:     */       }
/*  755: 750 */       throw new EJBException(localException);
/*  756:     */     }
/*  757:     */     finally
/*  758:     */     {
/*  759: 754 */       this._comp.setCurrent(localJavaComponent);
/*  760:     */     }
/*  761:     */   }
/*  762:     */   
/*  763:     */   public TypePmtSyncRsV1[] getPendingPmtsAndHistoryByCustomerID(String paramString, int paramInt1, int paramInt2)
/*  764:     */     throws RemoteException
/*  765:     */   {
/*  766: 764 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  767: 765 */     EJBContext localEJBContext = null;
/*  768:     */     try
/*  769:     */     {
/*  770: 768 */       localEJBContext = this._comp.getPooledInstance();
/*  771: 769 */       if (localEJBContext == null) {
/*  772: 771 */         localEJBContext = _create();
/*  773:     */       }
/*  774: 773 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  775: 774 */       if (localEJBContext.debug) {
/*  776: 776 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPendingPmtsAndHistoryByCustomerID"));
/*  777:     */       }
/*  778: 778 */       TypePmtSyncRsV1[] arrayOfTypePmtSyncRsV12 = localOFX200BPWServicesBean
/*  779: 779 */         .getPendingPmtsAndHistoryByCustomerID(
/*  780: 780 */         paramString, 
/*  781: 781 */         paramInt1, 
/*  782: 782 */         paramInt2);
/*  783:     */       
/*  784: 784 */       arrayOfTypePmtSyncRsV12 = TypePmtSyncRsV1SeqHelper.clone(arrayOfTypePmtSyncRsV12);
/*  785: 785 */       localEJBContext.returnToPool();
/*  786: 786 */       return arrayOfTypePmtSyncRsV12;
/*  787:     */     }
/*  788:     */     catch (Exception localException)
/*  789:     */     {
/*  790: 790 */       if (localEJBContext != null) {
/*  791: 792 */         localEJBContext.throwRemote(localException);
/*  792:     */       }
/*  793: 794 */       throw new EJBException(localException);
/*  794:     */     }
/*  795:     */     finally
/*  796:     */     {
/*  797: 798 */       this._comp.setCurrent(localJavaComponent);
/*  798:     */     }
/*  799:     */   }
/*  800:     */   
/*  801:     */   public TypeIntraSyncRsV1[] getPendingIntrasByCustomerID(String paramString, int paramInt)
/*  802:     */     throws RemoteException
/*  803:     */   {
/*  804: 807 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  805: 808 */     EJBContext localEJBContext = null;
/*  806:     */     try
/*  807:     */     {
/*  808: 811 */       localEJBContext = this._comp.getPooledInstance();
/*  809: 812 */       if (localEJBContext == null) {
/*  810: 814 */         localEJBContext = _create();
/*  811:     */       }
/*  812: 816 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  813: 817 */       if (localEJBContext.debug) {
/*  814: 819 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPendingIntrasByCustomerID"));
/*  815:     */       }
/*  816: 821 */       TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV12 = localOFX200BPWServicesBean
/*  817: 822 */         .getPendingIntrasByCustomerID(
/*  818: 823 */         paramString, 
/*  819: 824 */         paramInt);
/*  820:     */       
/*  821: 826 */       arrayOfTypeIntraSyncRsV12 = TypeIntraSyncRsV1SeqHelper.clone(arrayOfTypeIntraSyncRsV12);
/*  822: 827 */       localEJBContext.returnToPool();
/*  823: 828 */       return arrayOfTypeIntraSyncRsV12;
/*  824:     */     }
/*  825:     */     catch (Exception localException)
/*  826:     */     {
/*  827: 832 */       if (localEJBContext != null) {
/*  828: 834 */         localEJBContext.throwRemote(localException);
/*  829:     */       }
/*  830: 836 */       throw new EJBException(localException);
/*  831:     */     }
/*  832:     */     finally
/*  833:     */     {
/*  834: 840 */       this._comp.setCurrent(localJavaComponent);
/*  835:     */     }
/*  836:     */   }
/*  837:     */   
/*  838:     */   public TypeIntraSyncRsV1[] getPendingIntrasAndHistoryByCustomerID(String paramString, int paramInt1, int paramInt2)
/*  839:     */     throws RemoteException
/*  840:     */   {
/*  841: 850 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  842: 851 */     EJBContext localEJBContext = null;
/*  843:     */     try
/*  844:     */     {
/*  845: 854 */       localEJBContext = this._comp.getPooledInstance();
/*  846: 855 */       if (localEJBContext == null) {
/*  847: 857 */         localEJBContext = _create();
/*  848:     */       }
/*  849: 859 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  850: 860 */       if (localEJBContext.debug) {
/*  851: 862 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPendingIntrasAndHistoryByCustomerID"));
/*  852:     */       }
/*  853: 864 */       TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV12 = localOFX200BPWServicesBean
/*  854: 865 */         .getPendingIntrasAndHistoryByCustomerID(
/*  855: 866 */         paramString, 
/*  856: 867 */         paramInt1, 
/*  857: 868 */         paramInt2);
/*  858:     */       
/*  859: 870 */       arrayOfTypeIntraSyncRsV12 = TypeIntraSyncRsV1SeqHelper.clone(arrayOfTypeIntraSyncRsV12);
/*  860: 871 */       localEJBContext.returnToPool();
/*  861: 872 */       return arrayOfTypeIntraSyncRsV12;
/*  862:     */     }
/*  863:     */     catch (Exception localException)
/*  864:     */     {
/*  865: 876 */       if (localEJBContext != null) {
/*  866: 878 */         localEJBContext.throwRemote(localException);
/*  867:     */       }
/*  868: 880 */       throw new EJBException(localException);
/*  869:     */     }
/*  870:     */     finally
/*  871:     */     {
/*  872: 884 */       this._comp.setCurrent(localJavaComponent);
/*  873:     */     }
/*  874:     */   }
/*  875:     */   
/*  876:     */   public TypePmtSyncRsV1 getPendingPmts(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
/*  877:     */     throws RemoteException
/*  878:     */   {
/*  879: 894 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  880: 895 */     EJBContext localEJBContext = null;
/*  881:     */     try
/*  882:     */     {
/*  883: 898 */       localEJBContext = this._comp.getPooledInstance();
/*  884: 899 */       if (localEJBContext == null) {
/*  885: 901 */         localEJBContext = _create();
/*  886:     */       }
/*  887: 903 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  888: 904 */       if (localEJBContext.debug) {
/*  889: 906 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPendingPmts"));
/*  890:     */       }
/*  891: 908 */       TypePmtSyncRsV1 localTypePmtSyncRsV12 = localOFX200BPWServicesBean
/*  892: 909 */         .getPendingPmts(
/*  893: 910 */         TypePmtSyncRqV1Helper.clone(paramTypePmtSyncRqV1), 
/*  894: 911 */         TypeUserDataHelper.clone(paramTypeUserData), 
/*  895: 912 */         paramInt);
/*  896:     */       
/*  897: 914 */       localTypePmtSyncRsV12 = TypePmtSyncRsV1Helper.clone(localTypePmtSyncRsV12);
/*  898: 915 */       localEJBContext.returnToPool();
/*  899: 916 */       return localTypePmtSyncRsV12;
/*  900:     */     }
/*  901:     */     catch (Exception localException)
/*  902:     */     {
/*  903: 920 */       if (localEJBContext != null) {
/*  904: 922 */         localEJBContext.throwRemote(localException);
/*  905:     */       }
/*  906: 924 */       throw new EJBException(localException);
/*  907:     */     }
/*  908:     */     finally
/*  909:     */     {
/*  910: 928 */       this._comp.setCurrent(localJavaComponent);
/*  911:     */     }
/*  912:     */   }
/*  913:     */   
/*  914:     */   public TypeIntraSyncRsV1 getPendingIntras(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
/*  915:     */     throws RemoteException
/*  916:     */   {
/*  917: 938 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  918: 939 */     EJBContext localEJBContext = null;
/*  919:     */     try
/*  920:     */     {
/*  921: 942 */       localEJBContext = this._comp.getPooledInstance();
/*  922: 943 */       if (localEJBContext == null) {
/*  923: 945 */         localEJBContext = _create();
/*  924:     */       }
/*  925: 947 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  926: 948 */       if (localEJBContext.debug) {
/*  927: 950 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPendingIntras"));
/*  928:     */       }
/*  929: 952 */       TypeIntraSyncRsV1 localTypeIntraSyncRsV12 = localOFX200BPWServicesBean
/*  930: 953 */         .getPendingIntras(
/*  931: 954 */         TypeIntraSyncRqV1Helper.clone(paramTypeIntraSyncRqV1), 
/*  932: 955 */         TypeUserDataHelper.clone(paramTypeUserData), 
/*  933: 956 */         paramInt);
/*  934:     */       
/*  935: 958 */       localTypeIntraSyncRsV12 = TypeIntraSyncRsV1Helper.clone(localTypeIntraSyncRsV12);
/*  936: 959 */       localEJBContext.returnToPool();
/*  937: 960 */       return localTypeIntraSyncRsV12;
/*  938:     */     }
/*  939:     */     catch (Exception localException)
/*  940:     */     {
/*  941: 964 */       if (localEJBContext != null) {
/*  942: 966 */         localEJBContext.throwRemote(localException);
/*  943:     */       }
/*  944: 968 */       throw new EJBException(localException);
/*  945:     */     }
/*  946:     */     finally
/*  947:     */     {
/*  948: 972 */       this._comp.setCurrent(localJavaComponent);
/*  949:     */     }
/*  950:     */   }
/*  951:     */   
/*  952:     */   public String getPmtStatus(String paramString)
/*  953:     */     throws RemoteException
/*  954:     */   {
/*  955: 980 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  956: 981 */     EJBContext localEJBContext = null;
/*  957:     */     try
/*  958:     */     {
/*  959: 984 */       localEJBContext = this._comp.getPooledInstance();
/*  960: 985 */       if (localEJBContext == null) {
/*  961: 987 */         localEJBContext = _create();
/*  962:     */       }
/*  963: 989 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  964: 990 */       if (localEJBContext.debug) {
/*  965: 992 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPmtStatus"));
/*  966:     */       }
/*  967: 994 */       String str2 = localOFX200BPWServicesBean
/*  968: 995 */         .getPmtStatus(
/*  969: 996 */         paramString);
/*  970:     */       
/*  971: 998 */       localEJBContext.returnToPool();
/*  972: 999 */       return str2;
/*  973:     */     }
/*  974:     */     catch (Exception localException)
/*  975:     */     {
/*  976:1003 */       if (localEJBContext != null) {
/*  977:1005 */         localEJBContext.throwRemote(localException);
/*  978:     */       }
/*  979:1007 */       throw new EJBException(localException);
/*  980:     */     }
/*  981:     */     finally
/*  982:     */     {
/*  983:1011 */       this._comp.setCurrent(localJavaComponent);
/*  984:     */     }
/*  985:     */   }
/*  986:     */   
/*  987:     */   public boolean checkPayeeEditMask(String paramString1, String paramString2)
/*  988:     */     throws RemoteException
/*  989:     */   {
/*  990:1020 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  991:1021 */     EJBContext localEJBContext = null;
/*  992:     */     try
/*  993:     */     {
/*  994:1024 */       localEJBContext = this._comp.getPooledInstance();
/*  995:1025 */       if (localEJBContext == null) {
/*  996:1027 */         localEJBContext = _create();
/*  997:     */       }
/*  998:1029 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/*  999:1030 */       if (localEJBContext.debug) {
/* 1000:1032 */         localEJBContext.logger.debug(localEJBContext.debugMsg("checkPayeeEditMask"));
/* 1001:     */       }
/* 1002:1034 */       boolean bool2 = localOFX200BPWServicesBean
/* 1003:1035 */         .checkPayeeEditMask(
/* 1004:1036 */         paramString1, 
/* 1005:1037 */         paramString2);
/* 1006:     */       
/* 1007:1039 */       localEJBContext.returnToPool();
/* 1008:1040 */       return bool2;
/* 1009:     */     }
/* 1010:     */     catch (Exception localException)
/* 1011:     */     {
/* 1012:1044 */       if (localEJBContext != null) {
/* 1013:1046 */         localEJBContext.throwRemote(localException);
/* 1014:     */       }
/* 1015:1048 */       throw new EJBException(localException);
/* 1016:     */     }
/* 1017:     */     finally
/* 1018:     */     {
/* 1019:1052 */       this._comp.setCurrent(localJavaComponent);
/* 1020:     */     }
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   public void processIntraTrnRslt(IntraTrnRslt[] paramArrayOfIntraTrnRslt)
/* 1024:     */     throws RemoteException
/* 1025:     */   {
/* 1026:1060 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1027:1061 */     EJBContext localEJBContext = null;
/* 1028:     */     try
/* 1029:     */     {
/* 1030:1064 */       localEJBContext = this._comp.getPooledInstance();
/* 1031:1065 */       if (localEJBContext == null) {
/* 1032:1067 */         localEJBContext = _create();
/* 1033:     */       }
/* 1034:1069 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1035:1070 */       if (localEJBContext.debug) {
/* 1036:1072 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processIntraTrnRslt"));
/* 1037:     */       }
/* 1038:1075 */       localOFX200BPWServicesBean.processIntraTrnRslt(
/* 1039:1076 */         IntraTrnRsltSeqHelper.clone(paramArrayOfIntraTrnRslt));
/* 1040:     */       
/* 1041:1078 */       localEJBContext.returnToPool();
/* 1042:     */     }
/* 1043:     */     catch (Exception localException)
/* 1044:     */     {
/* 1045:1082 */       if (localEJBContext != null) {
/* 1046:1084 */         localEJBContext.throwRemote(localException);
/* 1047:     */       }
/* 1048:1086 */       throw new EJBException(localException);
/* 1049:     */     }
/* 1050:     */     finally
/* 1051:     */     {
/* 1052:1090 */       this._comp.setCurrent(localJavaComponent);
/* 1053:     */     }
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public void processPmtTrnRslt(PmtTrnRslt[] paramArrayOfPmtTrnRslt)
/* 1057:     */     throws RemoteException
/* 1058:     */   {
/* 1059:1098 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1060:1099 */     EJBContext localEJBContext = null;
/* 1061:     */     try
/* 1062:     */     {
/* 1063:1102 */       localEJBContext = this._comp.getPooledInstance();
/* 1064:1103 */       if (localEJBContext == null) {
/* 1065:1105 */         localEJBContext = _create();
/* 1066:     */       }
/* 1067:1107 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1068:1108 */       if (localEJBContext.debug) {
/* 1069:1110 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPmtTrnRslt"));
/* 1070:     */       }
/* 1071:1113 */       localOFX200BPWServicesBean.processPmtTrnRslt(
/* 1072:1114 */         PmtTrnRsltSeqHelper.clone(paramArrayOfPmtTrnRslt));
/* 1073:     */       
/* 1074:1116 */       localEJBContext.returnToPool();
/* 1075:     */     }
/* 1076:     */     catch (Exception localException)
/* 1077:     */     {
/* 1078:1120 */       if (localEJBContext != null) {
/* 1079:1122 */         localEJBContext.throwRemote(localException);
/* 1080:     */       }
/* 1081:1124 */       throw new EJBException(localException);
/* 1082:     */     }
/* 1083:     */     finally
/* 1084:     */     {
/* 1085:1128 */       this._comp.setCurrent(localJavaComponent);
/* 1086:     */     }
/* 1087:     */   }
/* 1088:     */   
/* 1089:     */   public void processCustPayeeRslt(CustPayeeRslt[] paramArrayOfCustPayeeRslt)
/* 1090:     */     throws RemoteException
/* 1091:     */   {
/* 1092:1136 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1093:1137 */     EJBContext localEJBContext = null;
/* 1094:     */     try
/* 1095:     */     {
/* 1096:1140 */       localEJBContext = this._comp.getPooledInstance();
/* 1097:1141 */       if (localEJBContext == null) {
/* 1098:1143 */         localEJBContext = _create();
/* 1099:     */       }
/* 1100:1145 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1101:1146 */       if (localEJBContext.debug) {
/* 1102:1148 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processCustPayeeRslt"));
/* 1103:     */       }
/* 1104:1151 */       localOFX200BPWServicesBean.processCustPayeeRslt(
/* 1105:1152 */         CustPayeeRsltSeqHelper.clone(paramArrayOfCustPayeeRslt));
/* 1106:     */       
/* 1107:1154 */       localEJBContext.returnToPool();
/* 1108:     */     }
/* 1109:     */     catch (Exception localException)
/* 1110:     */     {
/* 1111:1158 */       if (localEJBContext != null) {
/* 1112:1160 */         localEJBContext.throwRemote(localException);
/* 1113:     */       }
/* 1114:1162 */       throw new EJBException(localException);
/* 1115:     */     }
/* 1116:     */     finally
/* 1117:     */     {
/* 1118:1166 */       this._comp.setCurrent(localJavaComponent);
/* 1119:     */     }
/* 1120:     */   }
/* 1121:     */   
/* 1122:     */   public void processFundAllocRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
/* 1123:     */     throws RemoteException
/* 1124:     */   {
/* 1125:1174 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1126:1175 */     EJBContext localEJBContext = null;
/* 1127:     */     try
/* 1128:     */     {
/* 1129:1178 */       localEJBContext = this._comp.getPooledInstance();
/* 1130:1179 */       if (localEJBContext == null) {
/* 1131:1181 */         localEJBContext = _create();
/* 1132:     */       }
/* 1133:1183 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1134:1184 */       if (localEJBContext.debug) {
/* 1135:1186 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processFundAllocRslt"));
/* 1136:     */       }
/* 1137:1189 */       localOFX200BPWServicesBean.processFundAllocRslt(
/* 1138:1190 */         FundsAllocRsltSeqHelper.clone(paramArrayOfFundsAllocRslt));
/* 1139:     */       
/* 1140:1192 */       localEJBContext.returnToPool();
/* 1141:     */     }
/* 1142:     */     catch (Exception localException)
/* 1143:     */     {
/* 1144:1196 */       if (localEJBContext != null) {
/* 1145:1198 */         localEJBContext.throwRemote(localException);
/* 1146:     */       }
/* 1147:1200 */       throw new EJBException(localException);
/* 1148:     */     }
/* 1149:     */     finally
/* 1150:     */     {
/* 1151:1204 */       this._comp.setCurrent(localJavaComponent);
/* 1152:     */     }
/* 1153:     */   }
/* 1154:     */   
/* 1155:     */   public void processFundRevertRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
/* 1156:     */     throws RemoteException
/* 1157:     */   {
/* 1158:1212 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1159:1213 */     EJBContext localEJBContext = null;
/* 1160:     */     try
/* 1161:     */     {
/* 1162:1216 */       localEJBContext = this._comp.getPooledInstance();
/* 1163:1217 */       if (localEJBContext == null) {
/* 1164:1219 */         localEJBContext = _create();
/* 1165:     */       }
/* 1166:1221 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1167:1222 */       if (localEJBContext.debug) {
/* 1168:1224 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processFundRevertRslt"));
/* 1169:     */       }
/* 1170:1227 */       localOFX200BPWServicesBean.processFundRevertRslt(
/* 1171:1228 */         FundsAllocRsltSeqHelper.clone(paramArrayOfFundsAllocRslt));
/* 1172:     */       
/* 1173:1230 */       localEJBContext.returnToPool();
/* 1174:     */     }
/* 1175:     */     catch (Exception localException)
/* 1176:     */     {
/* 1177:1234 */       if (localEJBContext != null) {
/* 1178:1236 */         localEJBContext.throwRemote(localException);
/* 1179:     */       }
/* 1180:1238 */       throw new EJBException(localException);
/* 1181:     */     }
/* 1182:     */     finally
/* 1183:     */     {
/* 1184:1242 */       this._comp.setCurrent(localJavaComponent);
/* 1185:     */     }
/* 1186:     */   }
/* 1187:     */   
/* 1188:     */   public void processPayeeRslt(PayeeRslt[] paramArrayOfPayeeRslt)
/* 1189:     */     throws RemoteException
/* 1190:     */   {
/* 1191:1250 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1192:1251 */     EJBContext localEJBContext = null;
/* 1193:     */     try
/* 1194:     */     {
/* 1195:1254 */       localEJBContext = this._comp.getPooledInstance();
/* 1196:1255 */       if (localEJBContext == null) {
/* 1197:1257 */         localEJBContext = _create();
/* 1198:     */       }
/* 1199:1259 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1200:1260 */       if (localEJBContext.debug) {
/* 1201:1262 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPayeeRslt"));
/* 1202:     */       }
/* 1203:1265 */       localOFX200BPWServicesBean.processPayeeRslt(
/* 1204:1266 */         PayeeRsltSeqHelper.clone(paramArrayOfPayeeRslt));
/* 1205:     */       
/* 1206:1268 */       localEJBContext.returnToPool();
/* 1207:     */     }
/* 1208:     */     catch (Exception localException)
/* 1209:     */     {
/* 1210:1272 */       if (localEJBContext != null) {
/* 1211:1274 */         localEJBContext.throwRemote(localException);
/* 1212:     */       }
/* 1213:1276 */       throw new EJBException(localException);
/* 1214:     */     }
/* 1215:     */     finally
/* 1216:     */     {
/* 1217:1280 */       this._comp.setCurrent(localJavaComponent);
/* 1218:     */     }
/* 1219:     */   }
/* 1220:     */   
/* 1221:     */   public String addPayeeFromBackend(PayeeInfo paramPayeeInfo)
/* 1222:     */     throws RemoteException
/* 1223:     */   {
/* 1224:1288 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1225:1289 */     EJBContext localEJBContext = null;
/* 1226:     */     try
/* 1227:     */     {
/* 1228:1292 */       localEJBContext = this._comp.getPooledInstance();
/* 1229:1293 */       if (localEJBContext == null) {
/* 1230:1295 */         localEJBContext = _create();
/* 1231:     */       }
/* 1232:1297 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1233:1298 */       if (localEJBContext.debug) {
/* 1234:1300 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addPayeeFromBackend"));
/* 1235:     */       }
/* 1236:1302 */       String str2 = localOFX200BPWServicesBean
/* 1237:1303 */         .addPayeeFromBackend(
/* 1238:1304 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo));
/* 1239:     */       
/* 1240:1306 */       localEJBContext.returnToPool();
/* 1241:1307 */       return str2;
/* 1242:     */     }
/* 1243:     */     catch (Exception localException)
/* 1244:     */     {
/* 1245:1311 */       if (localEJBContext != null) {
/* 1246:1313 */         localEJBContext.throwRemote(localException);
/* 1247:     */       }
/* 1248:1315 */       throw new EJBException(localException);
/* 1249:     */     }
/* 1250:     */     finally
/* 1251:     */     {
/* 1252:1319 */       this._comp.setCurrent(localJavaComponent);
/* 1253:     */     }
/* 1254:     */   }
/* 1255:     */   
/* 1256:     */   public PayeeInfo[] updatePayeeFromBackend(PayeeInfo paramPayeeInfo)
/* 1257:     */     throws RemoteException
/* 1258:     */   {
/* 1259:1327 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1260:1328 */     EJBContext localEJBContext = null;
/* 1261:     */     try
/* 1262:     */     {
/* 1263:1331 */       localEJBContext = this._comp.getPooledInstance();
/* 1264:1332 */       if (localEJBContext == null) {
/* 1265:1334 */         localEJBContext = _create();
/* 1266:     */       }
/* 1267:1336 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1268:1337 */       if (localEJBContext.debug) {
/* 1269:1339 */         localEJBContext.logger.debug(localEJBContext.debugMsg("updatePayeeFromBackend"));
/* 1270:     */       }
/* 1271:1341 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX200BPWServicesBean
/* 1272:1342 */         .updatePayeeFromBackend(
/* 1273:1343 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo));
/* 1274:     */       
/* 1275:1345 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 1276:1346 */       localEJBContext.returnToPool();
/* 1277:1347 */       return arrayOfPayeeInfo2;
/* 1278:     */     }
/* 1279:     */     catch (Exception localException)
/* 1280:     */     {
/* 1281:1351 */       if (localEJBContext != null) {
/* 1282:1353 */         localEJBContext.throwRemote(localException);
/* 1283:     */       }
/* 1284:1355 */       throw new EJBException(localException);
/* 1285:     */     }
/* 1286:     */     finally
/* 1287:     */     {
/* 1288:1359 */       this._comp.setCurrent(localJavaComponent);
/* 1289:     */     }
/* 1290:     */   }
/* 1291:     */   
/* 1292:     */   public void addPayeeRouteInfo(PayeeRouteInfo paramPayeeRouteInfo)
/* 1293:     */     throws RemoteException
/* 1294:     */   {
/* 1295:1367 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1296:1368 */     EJBContext localEJBContext = null;
/* 1297:     */     try
/* 1298:     */     {
/* 1299:1371 */       localEJBContext = this._comp.getPooledInstance();
/* 1300:1372 */       if (localEJBContext == null) {
/* 1301:1374 */         localEJBContext = _create();
/* 1302:     */       }
/* 1303:1376 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1304:1377 */       if (localEJBContext.debug) {
/* 1305:1379 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addPayeeRouteInfo"));
/* 1306:     */       }
/* 1307:1382 */       localOFX200BPWServicesBean.addPayeeRouteInfo(
/* 1308:1383 */         (PayeeRouteInfo)EJBContext.clone(paramPayeeRouteInfo));
/* 1309:     */       
/* 1310:1385 */       localEJBContext.returnToPool();
/* 1311:     */     }
/* 1312:     */     catch (Exception localException)
/* 1313:     */     {
/* 1314:1389 */       if (localEJBContext != null) {
/* 1315:1391 */         localEJBContext.throwRemote(localException);
/* 1316:     */       }
/* 1317:1393 */       throw new EJBException(localException);
/* 1318:     */     }
/* 1319:     */     finally
/* 1320:     */     {
/* 1321:1397 */       this._comp.setCurrent(localJavaComponent);
/* 1322:     */     }
/* 1323:     */   }
/* 1324:     */   
/* 1325:     */   public TypeIntraSyncRsV1 processIntraSyncRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData)
/* 1326:     */     throws RemoteException
/* 1327:     */   {
/* 1328:1406 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1329:1407 */     EJBContext localEJBContext = null;
/* 1330:     */     try
/* 1331:     */     {
/* 1332:1410 */       localEJBContext = this._comp.getPooledInstance();
/* 1333:1411 */       if (localEJBContext == null) {
/* 1334:1413 */         localEJBContext = _create();
/* 1335:     */       }
/* 1336:1415 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1337:1416 */       if (localEJBContext.debug) {
/* 1338:1418 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processIntraSyncRqV1"));
/* 1339:     */       }
/* 1340:1420 */       TypeIntraSyncRsV1 localTypeIntraSyncRsV12 = localOFX200BPWServicesBean
/* 1341:1421 */         .processIntraSyncRqV1(
/* 1342:1422 */         TypeIntraSyncRqV1Helper.clone(paramTypeIntraSyncRqV1), 
/* 1343:1423 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1344:     */       
/* 1345:1425 */       localTypeIntraSyncRsV12 = TypeIntraSyncRsV1Helper.clone(localTypeIntraSyncRsV12);
/* 1346:1426 */       localEJBContext.returnToPool();
/* 1347:1427 */       return localTypeIntraSyncRsV12;
/* 1348:     */     }
/* 1349:     */     catch (Exception localException)
/* 1350:     */     {
/* 1351:1431 */       if (localEJBContext != null) {
/* 1352:1433 */         localEJBContext.throwRemote(localException);
/* 1353:     */       }
/* 1354:1435 */       throw new EJBException(localException);
/* 1355:     */     }
/* 1356:     */     finally
/* 1357:     */     {
/* 1358:1439 */       this._comp.setCurrent(localJavaComponent);
/* 1359:     */     }
/* 1360:     */   }
/* 1361:     */   
/* 1362:     */   public TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData)
/* 1363:     */     throws RemoteException
/* 1364:     */   {
/* 1365:1448 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1366:1449 */     EJBContext localEJBContext = null;
/* 1367:     */     try
/* 1368:     */     {
/* 1369:1452 */       localEJBContext = this._comp.getPooledInstance();
/* 1370:1453 */       if (localEJBContext == null) {
/* 1371:1455 */         localEJBContext = _create();
/* 1372:     */       }
/* 1373:1457 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1374:1458 */       if (localEJBContext.debug) {
/* 1375:1460 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processIntraTrnRqV1"));
/* 1376:     */       }
/* 1377:1462 */       TypeIntraTrnRsV1 localTypeIntraTrnRsV12 = localOFX200BPWServicesBean
/* 1378:1463 */         .processIntraTrnRqV1(
/* 1379:1464 */         TypeIntraTrnRqV1Helper.clone(paramTypeIntraTrnRqV1), 
/* 1380:1465 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1381:     */       
/* 1382:1467 */       localTypeIntraTrnRsV12 = TypeIntraTrnRsV1Helper.clone(localTypeIntraTrnRsV12);
/* 1383:1468 */       localEJBContext.returnToPool();
/* 1384:1469 */       return localTypeIntraTrnRsV12;
/* 1385:     */     }
/* 1386:     */     catch (Exception localException)
/* 1387:     */     {
/* 1388:1473 */       if (localEJBContext != null) {
/* 1389:1475 */         localEJBContext.throwRemote(localException);
/* 1390:     */       }
/* 1391:1477 */       throw new EJBException(localException);
/* 1392:     */     }
/* 1393:     */     finally
/* 1394:     */     {
/* 1395:1481 */       this._comp.setCurrent(localJavaComponent);
/* 1396:     */     }
/* 1397:     */   }
/* 1398:     */   
/* 1399:     */   public TypePayeeSyncRsV1 processPayeeSyncRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData)
/* 1400:     */     throws RemoteException
/* 1401:     */   {
/* 1402:1490 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1403:1491 */     EJBContext localEJBContext = null;
/* 1404:     */     try
/* 1405:     */     {
/* 1406:1494 */       localEJBContext = this._comp.getPooledInstance();
/* 1407:1495 */       if (localEJBContext == null) {
/* 1408:1497 */         localEJBContext = _create();
/* 1409:     */       }
/* 1410:1499 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1411:1500 */       if (localEJBContext.debug) {
/* 1412:1502 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPayeeSyncRqV1"));
/* 1413:     */       }
/* 1414:1504 */       TypePayeeSyncRsV1 localTypePayeeSyncRsV12 = localOFX200BPWServicesBean
/* 1415:1505 */         .processPayeeSyncRqV1(
/* 1416:1506 */         TypePayeeSyncRqV1Helper.clone(paramTypePayeeSyncRqV1), 
/* 1417:1507 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1418:     */       
/* 1419:1509 */       localTypePayeeSyncRsV12 = TypePayeeSyncRsV1Helper.clone(localTypePayeeSyncRsV12);
/* 1420:1510 */       localEJBContext.returnToPool();
/* 1421:1511 */       return localTypePayeeSyncRsV12;
/* 1422:     */     }
/* 1423:     */     catch (Exception localException)
/* 1424:     */     {
/* 1425:1515 */       if (localEJBContext != null) {
/* 1426:1517 */         localEJBContext.throwRemote(localException);
/* 1427:     */       }
/* 1428:1519 */       throw new EJBException(localException);
/* 1429:     */     }
/* 1430:     */     finally
/* 1431:     */     {
/* 1432:1523 */       this._comp.setCurrent(localJavaComponent);
/* 1433:     */     }
/* 1434:     */   }
/* 1435:     */   
/* 1436:     */   public TypePayeeTrnRsV1 processPayeeTrnRqV1(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData)
/* 1437:     */     throws RemoteException
/* 1438:     */   {
/* 1439:1532 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1440:1533 */     EJBContext localEJBContext = null;
/* 1441:     */     try
/* 1442:     */     {
/* 1443:1536 */       localEJBContext = this._comp.getPooledInstance();
/* 1444:1537 */       if (localEJBContext == null) {
/* 1445:1539 */         localEJBContext = _create();
/* 1446:     */       }
/* 1447:1541 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1448:1542 */       if (localEJBContext.debug) {
/* 1449:1544 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPayeeTrnRqV1"));
/* 1450:     */       }
/* 1451:1546 */       TypePayeeTrnRsV1 localTypePayeeTrnRsV12 = localOFX200BPWServicesBean
/* 1452:1547 */         .processPayeeTrnRqV1(
/* 1453:1548 */         TypePayeeTrnRqV1Helper.clone(paramTypePayeeTrnRqV1), 
/* 1454:1549 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1455:     */       
/* 1456:1551 */       localTypePayeeTrnRsV12 = TypePayeeTrnRsV1Helper.clone(localTypePayeeTrnRsV12);
/* 1457:1552 */       localEJBContext.returnToPool();
/* 1458:1553 */       return localTypePayeeTrnRsV12;
/* 1459:     */     }
/* 1460:     */     catch (Exception localException)
/* 1461:     */     {
/* 1462:1557 */       if (localEJBContext != null) {
/* 1463:1559 */         localEJBContext.throwRemote(localException);
/* 1464:     */       }
/* 1465:1561 */       throw new EJBException(localException);
/* 1466:     */     }
/* 1467:     */     finally
/* 1468:     */     {
/* 1469:1565 */       this._comp.setCurrent(localJavaComponent);
/* 1470:     */     }
/* 1471:     */   }
/* 1472:     */   
/* 1473:     */   public TypePmtInqTrnRsV1 processPmtInqTrnRqV1(TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, TypeUserData paramTypeUserData)
/* 1474:     */     throws RemoteException
/* 1475:     */   {
/* 1476:1574 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1477:1575 */     EJBContext localEJBContext = null;
/* 1478:     */     try
/* 1479:     */     {
/* 1480:1578 */       localEJBContext = this._comp.getPooledInstance();
/* 1481:1579 */       if (localEJBContext == null) {
/* 1482:1581 */         localEJBContext = _create();
/* 1483:     */       }
/* 1484:1583 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1485:1584 */       if (localEJBContext.debug) {
/* 1486:1586 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPmtInqTrnRqV1"));
/* 1487:     */       }
/* 1488:1588 */       TypePmtInqTrnRsV1 localTypePmtInqTrnRsV12 = localOFX200BPWServicesBean
/* 1489:1589 */         .processPmtInqTrnRqV1(
/* 1490:1590 */         TypePmtInqTrnRqV1Helper.clone(paramTypePmtInqTrnRqV1), 
/* 1491:1591 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1492:     */       
/* 1493:1593 */       localTypePmtInqTrnRsV12 = TypePmtInqTrnRsV1Helper.clone(localTypePmtInqTrnRsV12);
/* 1494:1594 */       localEJBContext.returnToPool();
/* 1495:1595 */       return localTypePmtInqTrnRsV12;
/* 1496:     */     }
/* 1497:     */     catch (Exception localException)
/* 1498:     */     {
/* 1499:1599 */       if (localEJBContext != null) {
/* 1500:1601 */         localEJBContext.throwRemote(localException);
/* 1501:     */       }
/* 1502:1603 */       throw new EJBException(localException);
/* 1503:     */     }
/* 1504:     */     finally
/* 1505:     */     {
/* 1506:1607 */       this._comp.setCurrent(localJavaComponent);
/* 1507:     */     }
/* 1508:     */   }
/* 1509:     */   
/* 1510:     */   public TypePmtSyncRsV1 processPmtSyncRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData)
/* 1511:     */     throws RemoteException
/* 1512:     */   {
/* 1513:1616 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1514:1617 */     EJBContext localEJBContext = null;
/* 1515:     */     try
/* 1516:     */     {
/* 1517:1620 */       localEJBContext = this._comp.getPooledInstance();
/* 1518:1621 */       if (localEJBContext == null) {
/* 1519:1623 */         localEJBContext = _create();
/* 1520:     */       }
/* 1521:1625 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1522:1626 */       if (localEJBContext.debug) {
/* 1523:1628 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPmtSyncRqV1"));
/* 1524:     */       }
/* 1525:1630 */       TypePmtSyncRsV1 localTypePmtSyncRsV12 = localOFX200BPWServicesBean
/* 1526:1631 */         .processPmtSyncRqV1(
/* 1527:1632 */         TypePmtSyncRqV1Helper.clone(paramTypePmtSyncRqV1), 
/* 1528:1633 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1529:     */       
/* 1530:1635 */       localTypePmtSyncRsV12 = TypePmtSyncRsV1Helper.clone(localTypePmtSyncRsV12);
/* 1531:1636 */       localEJBContext.returnToPool();
/* 1532:1637 */       return localTypePmtSyncRsV12;
/* 1533:     */     }
/* 1534:     */     catch (Exception localException)
/* 1535:     */     {
/* 1536:1641 */       if (localEJBContext != null) {
/* 1537:1643 */         localEJBContext.throwRemote(localException);
/* 1538:     */       }
/* 1539:1645 */       throw new EJBException(localException);
/* 1540:     */     }
/* 1541:     */     finally
/* 1542:     */     {
/* 1543:1649 */       this._comp.setCurrent(localJavaComponent);
/* 1544:     */     }
/* 1545:     */   }
/* 1546:     */   
/* 1547:     */   public TypePmtTrnRsV1 processPmtTrnRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
/* 1548:     */     throws RemoteException
/* 1549:     */   {
/* 1550:1658 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1551:1659 */     EJBContext localEJBContext = null;
/* 1552:     */     try
/* 1553:     */     {
/* 1554:1662 */       localEJBContext = this._comp.getPooledInstance();
/* 1555:1663 */       if (localEJBContext == null) {
/* 1556:1665 */         localEJBContext = _create();
/* 1557:     */       }
/* 1558:1667 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1559:1668 */       if (localEJBContext.debug) {
/* 1560:1670 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPmtTrnRqV1"));
/* 1561:     */       }
/* 1562:1672 */       TypePmtTrnRsV1 localTypePmtTrnRsV12 = localOFX200BPWServicesBean
/* 1563:1673 */         .processPmtTrnRqV1(
/* 1564:1674 */         TypePmtTrnRqV1Helper.clone(paramTypePmtTrnRqV1), 
/* 1565:1675 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1566:     */       
/* 1567:1677 */       localTypePmtTrnRsV12 = TypePmtTrnRsV1Helper.clone(localTypePmtTrnRsV12);
/* 1568:1678 */       localEJBContext.returnToPool();
/* 1569:1679 */       return localTypePmtTrnRsV12;
/* 1570:     */     }
/* 1571:     */     catch (Exception localException)
/* 1572:     */     {
/* 1573:1683 */       if (localEJBContext != null) {
/* 1574:1685 */         localEJBContext.throwRemote(localException);
/* 1575:     */       }
/* 1576:1687 */       throw new EJBException(localException);
/* 1577:     */     }
/* 1578:     */     finally
/* 1579:     */     {
/* 1580:1691 */       this._comp.setCurrent(localJavaComponent);
/* 1581:     */     }
/* 1582:     */   }
/* 1583:     */   
/* 1584:     */   public TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData)
/* 1585:     */     throws RemoteException
/* 1586:     */   {
/* 1587:1700 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1588:1701 */     EJBContext localEJBContext = null;
/* 1589:     */     try
/* 1590:     */     {
/* 1591:1704 */       localEJBContext = this._comp.getPooledInstance();
/* 1592:1705 */       if (localEJBContext == null) {
/* 1593:1707 */         localEJBContext = _create();
/* 1594:     */       }
/* 1595:1709 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1596:1710 */       if (localEJBContext.debug) {
/* 1597:1712 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processRecIntraSyncRqV1"));
/* 1598:     */       }
/* 1599:1714 */       TypeRecIntraSyncRsV1 localTypeRecIntraSyncRsV12 = localOFX200BPWServicesBean
/* 1600:1715 */         .processRecIntraSyncRqV1(
/* 1601:1716 */         TypeRecIntraSyncRqV1Helper.clone(paramTypeRecIntraSyncRqV1), 
/* 1602:1717 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1603:     */       
/* 1604:1719 */       localTypeRecIntraSyncRsV12 = TypeRecIntraSyncRsV1Helper.clone(localTypeRecIntraSyncRsV12);
/* 1605:1720 */       localEJBContext.returnToPool();
/* 1606:1721 */       return localTypeRecIntraSyncRsV12;
/* 1607:     */     }
/* 1608:     */     catch (Exception localException)
/* 1609:     */     {
/* 1610:1725 */       if (localEJBContext != null) {
/* 1611:1727 */         localEJBContext.throwRemote(localException);
/* 1612:     */       }
/* 1613:1729 */       throw new EJBException(localException);
/* 1614:     */     }
/* 1615:     */     finally
/* 1616:     */     {
/* 1617:1733 */       this._comp.setCurrent(localJavaComponent);
/* 1618:     */     }
/* 1619:     */   }
/* 1620:     */   
/* 1621:     */   public TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData)
/* 1622:     */     throws RemoteException
/* 1623:     */   {
/* 1624:1742 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1625:1743 */     EJBContext localEJBContext = null;
/* 1626:     */     try
/* 1627:     */     {
/* 1628:1746 */       localEJBContext = this._comp.getPooledInstance();
/* 1629:1747 */       if (localEJBContext == null) {
/* 1630:1749 */         localEJBContext = _create();
/* 1631:     */       }
/* 1632:1751 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1633:1752 */       if (localEJBContext.debug) {
/* 1634:1754 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processRecIntraTrnRqV1"));
/* 1635:     */       }
/* 1636:1756 */       TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV12 = localOFX200BPWServicesBean
/* 1637:1757 */         .processRecIntraTrnRqV1(
/* 1638:1758 */         TypeRecIntraTrnRqV1Helper.clone(paramTypeRecIntraTrnRqV1), 
/* 1639:1759 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1640:     */       
/* 1641:1761 */       localTypeRecIntraTrnRsV12 = TypeRecIntraTrnRsV1Helper.clone(localTypeRecIntraTrnRsV12);
/* 1642:1762 */       localEJBContext.returnToPool();
/* 1643:1763 */       return localTypeRecIntraTrnRsV12;
/* 1644:     */     }
/* 1645:     */     catch (Exception localException)
/* 1646:     */     {
/* 1647:1767 */       if (localEJBContext != null) {
/* 1648:1769 */         localEJBContext.throwRemote(localException);
/* 1649:     */       }
/* 1650:1771 */       throw new EJBException(localException);
/* 1651:     */     }
/* 1652:     */     finally
/* 1653:     */     {
/* 1654:1775 */       this._comp.setCurrent(localJavaComponent);
/* 1655:     */     }
/* 1656:     */   }
/* 1657:     */   
/* 1658:     */   public TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData)
/* 1659:     */     throws RemoteException
/* 1660:     */   {
/* 1661:1784 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1662:1785 */     EJBContext localEJBContext = null;
/* 1663:     */     try
/* 1664:     */     {
/* 1665:1788 */       localEJBContext = this._comp.getPooledInstance();
/* 1666:1789 */       if (localEJBContext == null) {
/* 1667:1791 */         localEJBContext = _create();
/* 1668:     */       }
/* 1669:1793 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1670:1794 */       if (localEJBContext.debug) {
/* 1671:1796 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processRecPmtSyncRqV1"));
/* 1672:     */       }
/* 1673:1798 */       TypeRecPmtSyncRsV1 localTypeRecPmtSyncRsV12 = localOFX200BPWServicesBean
/* 1674:1799 */         .processRecPmtSyncRqV1(
/* 1675:1800 */         TypeRecPmtSyncRqV1Helper.clone(paramTypeRecPmtSyncRqV1), 
/* 1676:1801 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1677:     */       
/* 1678:1803 */       localTypeRecPmtSyncRsV12 = TypeRecPmtSyncRsV1Helper.clone(localTypeRecPmtSyncRsV12);
/* 1679:1804 */       localEJBContext.returnToPool();
/* 1680:1805 */       return localTypeRecPmtSyncRsV12;
/* 1681:     */     }
/* 1682:     */     catch (Exception localException)
/* 1683:     */     {
/* 1684:1809 */       if (localEJBContext != null) {
/* 1685:1811 */         localEJBContext.throwRemote(localException);
/* 1686:     */       }
/* 1687:1813 */       throw new EJBException(localException);
/* 1688:     */     }
/* 1689:     */     finally
/* 1690:     */     {
/* 1691:1817 */       this._comp.setCurrent(localJavaComponent);
/* 1692:     */     }
/* 1693:     */   }
/* 1694:     */   
/* 1695:     */   public TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
/* 1696:     */     throws RemoteException
/* 1697:     */   {
/* 1698:1826 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1699:1827 */     EJBContext localEJBContext = null;
/* 1700:     */     try
/* 1701:     */     {
/* 1702:1830 */       localEJBContext = this._comp.getPooledInstance();
/* 1703:1831 */       if (localEJBContext == null) {
/* 1704:1833 */         localEJBContext = _create();
/* 1705:     */       }
/* 1706:1835 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1707:1836 */       if (localEJBContext.debug) {
/* 1708:1838 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processRecPmtTrnRqV1"));
/* 1709:     */       }
/* 1710:1840 */       TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV12 = localOFX200BPWServicesBean
/* 1711:1841 */         .processRecPmtTrnRqV1(
/* 1712:1842 */         TypeRecPmtTrnRqV1Helper.clone(paramTypeRecPmtTrnRqV1), 
/* 1713:1843 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1714:     */       
/* 1715:1845 */       localTypeRecPmtTrnRsV12 = TypeRecPmtTrnRsV1Helper.clone(localTypeRecPmtTrnRsV12);
/* 1716:1846 */       localEJBContext.returnToPool();
/* 1717:1847 */       return localTypeRecPmtTrnRsV12;
/* 1718:     */     }
/* 1719:     */     catch (Exception localException)
/* 1720:     */     {
/* 1721:1851 */       if (localEJBContext != null) {
/* 1722:1853 */         localEJBContext.throwRemote(localException);
/* 1723:     */       }
/* 1724:1855 */       throw new EJBException(localException);
/* 1725:     */     }
/* 1726:     */     finally
/* 1727:     */     {
/* 1728:1859 */       this._comp.setCurrent(localJavaComponent);
/* 1729:     */     }
/* 1730:     */   }
/* 1731:     */   
/* 1732:     */   public String[] getPayeeNames(String paramString, int paramInt)
/* 1733:     */     throws RemoteException
/* 1734:     */   {
/* 1735:1868 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1736:1869 */     EJBContext localEJBContext = null;
/* 1737:     */     try
/* 1738:     */     {
/* 1739:1872 */       localEJBContext = this._comp.getPooledInstance();
/* 1740:1873 */       if (localEJBContext == null) {
/* 1741:1875 */         localEJBContext = _create();
/* 1742:     */       }
/* 1743:1877 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1744:1878 */       if (localEJBContext.debug) {
/* 1745:1880 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPayeeNames"));
/* 1746:     */       }
/* 1747:1882 */       String[] arrayOfString2 = localOFX200BPWServicesBean
/* 1748:1883 */         .getPayeeNames(
/* 1749:1884 */         paramString, 
/* 1750:1885 */         paramInt);
/* 1751:     */       
/* 1752:1887 */       arrayOfString2 = StringSeqHelper.clone(arrayOfString2);
/* 1753:1888 */       localEJBContext.returnToPool();
/* 1754:1889 */       return arrayOfString2;
/* 1755:     */     }
/* 1756:     */     catch (Exception localException)
/* 1757:     */     {
/* 1758:1893 */       if (localEJBContext != null) {
/* 1759:1895 */         localEJBContext.throwRemote(localException);
/* 1760:     */       }
/* 1761:1897 */       throw new EJBException(localException);
/* 1762:     */     }
/* 1763:     */     finally
/* 1764:     */     {
/* 1765:1901 */       this._comp.setCurrent(localJavaComponent);
/* 1766:     */     }
/* 1767:     */   }
/* 1768:     */   
/* 1769:     */   public String[] getPayeeNames(String paramString)
/* 1770:     */     throws RemoteException
/* 1771:     */   {
/* 1772:1909 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1773:1910 */     EJBContext localEJBContext = null;
/* 1774:     */     try
/* 1775:     */     {
/* 1776:1913 */       localEJBContext = this._comp.getPooledInstance();
/* 1777:1914 */       if (localEJBContext == null) {
/* 1778:1916 */         localEJBContext = _create();
/* 1779:     */       }
/* 1780:1918 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1781:1919 */       if (localEJBContext.debug) {
/* 1782:1921 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPayeeNames"));
/* 1783:     */       }
/* 1784:1923 */       String[] arrayOfString2 = localOFX200BPWServicesBean
/* 1785:1924 */         .getPayeeNames(
/* 1786:1925 */         paramString);
/* 1787:     */       
/* 1788:1927 */       arrayOfString2 = StringSeqHelper.clone(arrayOfString2);
/* 1789:1928 */       localEJBContext.returnToPool();
/* 1790:1929 */       return arrayOfString2;
/* 1791:     */     }
/* 1792:     */     catch (Exception localException)
/* 1793:     */     {
/* 1794:1933 */       if (localEJBContext != null) {
/* 1795:1935 */         localEJBContext.throwRemote(localException);
/* 1796:     */       }
/* 1797:1937 */       throw new EJBException(localException);
/* 1798:     */     }
/* 1799:     */     finally
/* 1800:     */     {
/* 1801:1941 */       this._comp.setCurrent(localJavaComponent);
/* 1802:     */     }
/* 1803:     */   }
/* 1804:     */   
/* 1805:     */   public String[] getPayeeIDs(String paramString)
/* 1806:     */     throws RemoteException
/* 1807:     */   {
/* 1808:1949 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1809:1950 */     EJBContext localEJBContext = null;
/* 1810:     */     try
/* 1811:     */     {
/* 1812:1953 */       localEJBContext = this._comp.getPooledInstance();
/* 1813:1954 */       if (localEJBContext == null) {
/* 1814:1956 */         localEJBContext = _create();
/* 1815:     */       }
/* 1816:1958 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1817:1959 */       if (localEJBContext.debug) {
/* 1818:1961 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPayeeIDs"));
/* 1819:     */       }
/* 1820:1963 */       String[] arrayOfString2 = localOFX200BPWServicesBean
/* 1821:1964 */         .getPayeeIDs(
/* 1822:1965 */         paramString);
/* 1823:     */       
/* 1824:1967 */       arrayOfString2 = StringSeqHelper.clone(arrayOfString2);
/* 1825:1968 */       localEJBContext.returnToPool();
/* 1826:1969 */       return arrayOfString2;
/* 1827:     */     }
/* 1828:     */     catch (Exception localException)
/* 1829:     */     {
/* 1830:1973 */       if (localEJBContext != null) {
/* 1831:1975 */         localEJBContext.throwRemote(localException);
/* 1832:     */       }
/* 1833:1977 */       throw new EJBException(localException);
/* 1834:     */     }
/* 1835:     */     finally
/* 1836:     */     {
/* 1837:1981 */       this._comp.setCurrent(localJavaComponent);
/* 1838:     */     }
/* 1839:     */   }
/* 1840:     */   
/* 1841:     */   public PayeeInfo[] getPayees(String paramString, int paramInt)
/* 1842:     */     throws RemoteException
/* 1843:     */   {
/* 1844:1990 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1845:1991 */     EJBContext localEJBContext = null;
/* 1846:     */     try
/* 1847:     */     {
/* 1848:1994 */       localEJBContext = this._comp.getPooledInstance();
/* 1849:1995 */       if (localEJBContext == null) {
/* 1850:1997 */         localEJBContext = _create();
/* 1851:     */       }
/* 1852:1999 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1853:2000 */       if (localEJBContext.debug) {
/* 1854:2002 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPayees"));
/* 1855:     */       }
/* 1856:2004 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX200BPWServicesBean
/* 1857:2005 */         .getPayees(
/* 1858:2006 */         paramString, 
/* 1859:2007 */         paramInt);
/* 1860:     */       
/* 1861:2009 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 1862:2010 */       localEJBContext.returnToPool();
/* 1863:2011 */       return arrayOfPayeeInfo2;
/* 1864:     */     }
/* 1865:     */     catch (Exception localException)
/* 1866:     */     {
/* 1867:2015 */       if (localEJBContext != null) {
/* 1868:2017 */         localEJBContext.throwRemote(localException);
/* 1869:     */       }
/* 1870:2019 */       throw new EJBException(localException);
/* 1871:     */     }
/* 1872:     */     finally
/* 1873:     */     {
/* 1874:2023 */       this._comp.setCurrent(localJavaComponent);
/* 1875:     */     }
/* 1876:     */   }
/* 1877:     */   
/* 1878:     */   public PayeeInfo[] getPayees(String paramString)
/* 1879:     */     throws RemoteException
/* 1880:     */   {
/* 1881:2031 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1882:2032 */     EJBContext localEJBContext = null;
/* 1883:     */     try
/* 1884:     */     {
/* 1885:2035 */       localEJBContext = this._comp.getPooledInstance();
/* 1886:2036 */       if (localEJBContext == null) {
/* 1887:2038 */         localEJBContext = _create();
/* 1888:     */       }
/* 1889:2040 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1890:2041 */       if (localEJBContext.debug) {
/* 1891:2043 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPayees"));
/* 1892:     */       }
/* 1893:2045 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX200BPWServicesBean
/* 1894:2046 */         .getPayees(
/* 1895:2047 */         paramString);
/* 1896:     */       
/* 1897:2049 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 1898:2050 */       localEJBContext.returnToPool();
/* 1899:2051 */       return arrayOfPayeeInfo2;
/* 1900:     */     }
/* 1901:     */     catch (Exception localException)
/* 1902:     */     {
/* 1903:2055 */       if (localEJBContext != null) {
/* 1904:2057 */         localEJBContext.throwRemote(localException);
/* 1905:     */       }
/* 1906:2059 */       throw new EJBException(localException);
/* 1907:     */     }
/* 1908:     */     finally
/* 1909:     */     {
/* 1910:2063 */       this._comp.setCurrent(localJavaComponent);
/* 1911:     */     }
/* 1912:     */   }
/* 1913:     */   
/* 1914:     */   public PayeeInfo[] searchGlobalPayees(String paramString)
/* 1915:     */     throws RemoteException
/* 1916:     */   {
/* 1917:2071 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1918:2072 */     EJBContext localEJBContext = null;
/* 1919:     */     try
/* 1920:     */     {
/* 1921:2075 */       localEJBContext = this._comp.getPooledInstance();
/* 1922:2076 */       if (localEJBContext == null) {
/* 1923:2078 */         localEJBContext = _create();
/* 1924:     */       }
/* 1925:2080 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1926:2081 */       if (localEJBContext.debug) {
/* 1927:2083 */         localEJBContext.logger.debug(localEJBContext.debugMsg("searchGlobalPayees"));
/* 1928:     */       }
/* 1929:2085 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX200BPWServicesBean
/* 1930:2086 */         .searchGlobalPayees(
/* 1931:2087 */         paramString);
/* 1932:     */       
/* 1933:2089 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 1934:2090 */       localEJBContext.returnToPool();
/* 1935:2091 */       return arrayOfPayeeInfo2;
/* 1936:     */     }
/* 1937:     */     catch (Exception localException)
/* 1938:     */     {
/* 1939:2095 */       if (localEJBContext != null) {
/* 1940:2097 */         localEJBContext.throwRemote(localException);
/* 1941:     */       }
/* 1942:2099 */       throw new EJBException(localException);
/* 1943:     */     }
/* 1944:     */     finally
/* 1945:     */     {
/* 1946:2103 */       this._comp.setCurrent(localJavaComponent);
/* 1947:     */     }
/* 1948:     */   }
/* 1949:     */   
/* 1950:     */   public PayeeInfo[] updatePayee(PayeeInfo paramPayeeInfo, int paramInt)
/* 1951:     */     throws RemoteException
/* 1952:     */   {
/* 1953:2112 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1954:2113 */     EJBContext localEJBContext = null;
/* 1955:     */     try
/* 1956:     */     {
/* 1957:2116 */       localEJBContext = this._comp.getPooledInstance();
/* 1958:2117 */       if (localEJBContext == null) {
/* 1959:2119 */         localEJBContext = _create();
/* 1960:     */       }
/* 1961:2121 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1962:2122 */       if (localEJBContext.debug) {
/* 1963:2124 */         localEJBContext.logger.debug(localEJBContext.debugMsg("updatePayee"));
/* 1964:     */       }
/* 1965:2126 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX200BPWServicesBean
/* 1966:2127 */         .updatePayee(
/* 1967:2128 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo), 
/* 1968:2129 */         paramInt);
/* 1969:     */       
/* 1970:2131 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 1971:2132 */       localEJBContext.returnToPool();
/* 1972:2133 */       return arrayOfPayeeInfo2;
/* 1973:     */     }
/* 1974:     */     catch (Exception localException)
/* 1975:     */     {
/* 1976:2137 */       if (localEJBContext != null) {
/* 1977:2139 */         localEJBContext.throwRemote(localException);
/* 1978:     */       }
/* 1979:2141 */       throw new EJBException(localException);
/* 1980:     */     }
/* 1981:     */     finally
/* 1982:     */     {
/* 1983:2145 */       this._comp.setCurrent(localJavaComponent);
/* 1984:     */     }
/* 1985:     */   }
/* 1986:     */   
/* 1987:     */   public void updatePayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
/* 1988:     */     throws RemoteException
/* 1989:     */   {
/* 1990:2154 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1991:2155 */     EJBContext localEJBContext = null;
/* 1992:     */     try
/* 1993:     */     {
/* 1994:2158 */       localEJBContext = this._comp.getPooledInstance();
/* 1995:2159 */       if (localEJBContext == null) {
/* 1996:2161 */         localEJBContext = _create();
/* 1997:     */       }
/* 1998:2163 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 1999:2164 */       if (localEJBContext.debug) {
/* 2000:2166 */         localEJBContext.logger.debug(localEJBContext.debugMsg("updatePayee"));
/* 2001:     */       }
/* 2002:2169 */       localOFX200BPWServicesBean.updatePayee(
/* 2003:2170 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo), 
/* 2004:2171 */         (PayeeRouteInfo)EJBContext.clone(paramPayeeRouteInfo));
/* 2005:     */       
/* 2006:2173 */       localEJBContext.returnToPool();
/* 2007:     */     }
/* 2008:     */     catch (Exception localException)
/* 2009:     */     {
/* 2010:2177 */       if (localEJBContext != null) {
/* 2011:2179 */         localEJBContext.throwRemote(localException);
/* 2012:     */       }
/* 2013:2181 */       throw new EJBException(localException);
/* 2014:     */     }
/* 2015:     */     finally
/* 2016:     */     {
/* 2017:2185 */       this._comp.setCurrent(localJavaComponent);
/* 2018:     */     }
/* 2019:     */   }
/* 2020:     */   
/* 2021:     */   public void deletePayee(String paramString)
/* 2022:     */     throws RemoteException
/* 2023:     */   {
/* 2024:2193 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2025:2194 */     EJBContext localEJBContext = null;
/* 2026:     */     try
/* 2027:     */     {
/* 2028:2197 */       localEJBContext = this._comp.getPooledInstance();
/* 2029:2198 */       if (localEJBContext == null) {
/* 2030:2200 */         localEJBContext = _create();
/* 2031:     */       }
/* 2032:2202 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2033:2203 */       if (localEJBContext.debug) {
/* 2034:2205 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deletePayee"));
/* 2035:     */       }
/* 2036:2208 */       localOFX200BPWServicesBean.deletePayee(
/* 2037:2209 */         paramString);
/* 2038:     */       
/* 2039:2211 */       localEJBContext.returnToPool();
/* 2040:     */     }
/* 2041:     */     catch (Exception localException)
/* 2042:     */     {
/* 2043:2215 */       if (localEJBContext != null) {
/* 2044:2217 */         localEJBContext.throwRemote(localException);
/* 2045:     */       }
/* 2046:2219 */       throw new EJBException(localException);
/* 2047:     */     }
/* 2048:     */     finally
/* 2049:     */     {
/* 2050:2223 */       this._comp.setCurrent(localJavaComponent);
/* 2051:     */     }
/* 2052:     */   }
/* 2053:     */   
/* 2054:     */   public void deletePayees(String[] paramArrayOfString)
/* 2055:     */     throws RemoteException
/* 2056:     */   {
/* 2057:2231 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2058:2232 */     EJBContext localEJBContext = null;
/* 2059:     */     try
/* 2060:     */     {
/* 2061:2235 */       localEJBContext = this._comp.getPooledInstance();
/* 2062:2236 */       if (localEJBContext == null) {
/* 2063:2238 */         localEJBContext = _create();
/* 2064:     */       }
/* 2065:2240 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2066:2241 */       if (localEJBContext.debug) {
/* 2067:2243 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deletePayees"));
/* 2068:     */       }
/* 2069:2246 */       localOFX200BPWServicesBean.deletePayees(
/* 2070:2247 */         StringSeqHelper.clone(paramArrayOfString));
/* 2071:     */       
/* 2072:2249 */       localEJBContext.returnToPool();
/* 2073:     */     }
/* 2074:     */     catch (Exception localException)
/* 2075:     */     {
/* 2076:2253 */       if (localEJBContext != null) {
/* 2077:2255 */         localEJBContext.throwRemote(localException);
/* 2078:     */       }
/* 2079:2257 */       throw new EJBException(localException);
/* 2080:     */     }
/* 2081:     */     finally
/* 2082:     */     {
/* 2083:2261 */       this._comp.setCurrent(localJavaComponent);
/* 2084:     */     }
/* 2085:     */   }
/* 2086:     */   
/* 2087:     */   public PayeeInfo findPayeeByID(String paramString)
/* 2088:     */     throws RemoteException
/* 2089:     */   {
/* 2090:2269 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2091:2270 */     EJBContext localEJBContext = null;
/* 2092:     */     try
/* 2093:     */     {
/* 2094:2273 */       localEJBContext = this._comp.getPooledInstance();
/* 2095:2274 */       if (localEJBContext == null) {
/* 2096:2276 */         localEJBContext = _create();
/* 2097:     */       }
/* 2098:2278 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2099:2279 */       if (localEJBContext.debug) {
/* 2100:2281 */         localEJBContext.logger.debug(localEJBContext.debugMsg("findPayeeByID"));
/* 2101:     */       }
/* 2102:2283 */       PayeeInfo localPayeeInfo2 = localOFX200BPWServicesBean
/* 2103:2284 */         .findPayeeByID(
/* 2104:2285 */         paramString);
/* 2105:     */       
/* 2106:2287 */       localPayeeInfo2 = (PayeeInfo)EJBContext.clone(localPayeeInfo2);
/* 2107:2288 */       localEJBContext.returnToPool();
/* 2108:2289 */       return localPayeeInfo2;
/* 2109:     */     }
/* 2110:     */     catch (Exception localException)
/* 2111:     */     {
/* 2112:2293 */       if (localEJBContext != null) {
/* 2113:2295 */         localEJBContext.throwRemote(localException);
/* 2114:     */       }
/* 2115:2297 */       throw new EJBException(localException);
/* 2116:     */     }
/* 2117:     */     finally
/* 2118:     */     {
/* 2119:2301 */       this._comp.setCurrent(localJavaComponent);
/* 2120:     */     }
/* 2121:     */   }
/* 2122:     */   
/* 2123:     */   public void setPayeeStatus(String paramString1, String paramString2)
/* 2124:     */     throws RemoteException
/* 2125:     */   {
/* 2126:2310 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2127:2311 */     EJBContext localEJBContext = null;
/* 2128:     */     try
/* 2129:     */     {
/* 2130:2314 */       localEJBContext = this._comp.getPooledInstance();
/* 2131:2315 */       if (localEJBContext == null) {
/* 2132:2317 */         localEJBContext = _create();
/* 2133:     */       }
/* 2134:2319 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2135:2320 */       if (localEJBContext.debug) {
/* 2136:2322 */         localEJBContext.logger.debug(localEJBContext.debugMsg("setPayeeStatus"));
/* 2137:     */       }
/* 2138:2325 */       localOFX200BPWServicesBean.setPayeeStatus(
/* 2139:2326 */         paramString1, 
/* 2140:2327 */         paramString2);
/* 2141:     */       
/* 2142:2329 */       localEJBContext.returnToPool();
/* 2143:     */     }
/* 2144:     */     catch (Exception localException)
/* 2145:     */     {
/* 2146:2333 */       if (localEJBContext != null) {
/* 2147:2335 */         localEJBContext.throwRemote(localException);
/* 2148:     */       }
/* 2149:2337 */       throw new EJBException(localException);
/* 2150:     */     }
/* 2151:     */     finally
/* 2152:     */     {
/* 2153:2341 */       this._comp.setCurrent(localJavaComponent);
/* 2154:     */     }
/* 2155:     */   }
/* 2156:     */   
/* 2157:     */   public int getSmartDate(int paramInt)
/* 2158:     */     throws RemoteException
/* 2159:     */   {
/* 2160:2349 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2161:2350 */     EJBContext localEJBContext = null;
/* 2162:     */     try
/* 2163:     */     {
/* 2164:2353 */       localEJBContext = this._comp.getPooledInstance();
/* 2165:2354 */       if (localEJBContext == null) {
/* 2166:2356 */         localEJBContext = _create();
/* 2167:     */       }
/* 2168:2358 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2169:2359 */       if (localEJBContext.debug) {
/* 2170:2361 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getSmartDate"));
/* 2171:     */       }
/* 2172:2363 */       int j = localOFX200BPWServicesBean
/* 2173:2364 */         .getSmartDate(
/* 2174:2365 */         paramInt);
/* 2175:     */       
/* 2176:2367 */       localEJBContext.returnToPool();
/* 2177:2368 */       return j;
/* 2178:     */     }
/* 2179:     */     catch (Exception localException)
/* 2180:     */     {
/* 2181:2372 */       if (localEJBContext != null) {
/* 2182:2374 */         localEJBContext.throwRemote(localException);
/* 2183:     */       }
/* 2184:2376 */       throw new EJBException(localException);
/* 2185:     */     }
/* 2186:     */     finally
/* 2187:     */     {
/* 2188:2380 */       this._comp.setCurrent(localJavaComponent);
/* 2189:     */     }
/* 2190:     */   }
/* 2191:     */   
/* 2192:     */   public PayeeInfo[] getGlobalPayees(String paramString, int paramInt)
/* 2193:     */     throws RemoteException
/* 2194:     */   {
/* 2195:2389 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2196:2390 */     EJBContext localEJBContext = null;
/* 2197:     */     try
/* 2198:     */     {
/* 2199:2393 */       localEJBContext = this._comp.getPooledInstance();
/* 2200:2394 */       if (localEJBContext == null) {
/* 2201:2396 */         localEJBContext = _create();
/* 2202:     */       }
/* 2203:2398 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2204:2399 */       if (localEJBContext.debug) {
/* 2205:2401 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getGlobalPayees"));
/* 2206:     */       }
/* 2207:2403 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX200BPWServicesBean
/* 2208:2404 */         .getGlobalPayees(
/* 2209:2405 */         paramString, 
/* 2210:2406 */         paramInt);
/* 2211:     */       
/* 2212:2408 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 2213:2409 */       localEJBContext.returnToPool();
/* 2214:2410 */       return arrayOfPayeeInfo2;
/* 2215:     */     }
/* 2216:     */     catch (Exception localException)
/* 2217:     */     {
/* 2218:2414 */       if (localEJBContext != null) {
/* 2219:2416 */         localEJBContext.throwRemote(localException);
/* 2220:     */       }
/* 2221:2418 */       throw new EJBException(localException);
/* 2222:     */     }
/* 2223:     */     finally
/* 2224:     */     {
/* 2225:2422 */       this._comp.setCurrent(localJavaComponent);
/* 2226:     */     }
/* 2227:     */   }
/* 2228:     */   
/* 2229:     */   public String addPayee(PayeeInfo paramPayeeInfo, int paramInt)
/* 2230:     */     throws RemoteException
/* 2231:     */   {
/* 2232:2431 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2233:2432 */     EJBContext localEJBContext = null;
/* 2234:     */     try
/* 2235:     */     {
/* 2236:2435 */       localEJBContext = this._comp.getPooledInstance();
/* 2237:2436 */       if (localEJBContext == null) {
/* 2238:2438 */         localEJBContext = _create();
/* 2239:     */       }
/* 2240:2440 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2241:2441 */       if (localEJBContext.debug) {
/* 2242:2443 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addPayee"));
/* 2243:     */       }
/* 2244:2445 */       String str2 = localOFX200BPWServicesBean
/* 2245:2446 */         .addPayee(
/* 2246:2447 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo), 
/* 2247:2448 */         paramInt);
/* 2248:     */       
/* 2249:2450 */       localEJBContext.returnToPool();
/* 2250:2451 */       return str2;
/* 2251:     */     }
/* 2252:     */     catch (Exception localException)
/* 2253:     */     {
/* 2254:2455 */       if (localEJBContext != null) {
/* 2255:2457 */         localEJBContext.throwRemote(localException);
/* 2256:     */       }
/* 2257:2459 */       throw new EJBException(localException);
/* 2258:     */     }
/* 2259:     */     finally
/* 2260:     */     {
/* 2261:2463 */       this._comp.setCurrent(localJavaComponent);
/* 2262:     */     }
/* 2263:     */   }
/* 2264:     */   
/* 2265:     */   public int addConsumerCrossRef(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
/* 2266:     */     throws RemoteException
/* 2267:     */   {
/* 2268:2471 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2269:2472 */     EJBContext localEJBContext = null;
/* 2270:     */     try
/* 2271:     */     {
/* 2272:2475 */       localEJBContext = this._comp.getPooledInstance();
/* 2273:2476 */       if (localEJBContext == null) {
/* 2274:2478 */         localEJBContext = _create();
/* 2275:     */       }
/* 2276:2480 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2277:2481 */       if (localEJBContext.debug) {
/* 2278:2483 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addConsumerCrossRef"));
/* 2279:     */       }
/* 2280:2485 */       int j = localOFX200BPWServicesBean
/* 2281:2486 */         .addConsumerCrossRef(
/* 2282:2487 */         ConsumerCrossRefInfoSeqHelper.clone(paramArrayOfConsumerCrossRefInfo));
/* 2283:     */       
/* 2284:2489 */       localEJBContext.returnToPool();
/* 2285:2490 */       return j;
/* 2286:     */     }
/* 2287:     */     catch (Exception localException)
/* 2288:     */     {
/* 2289:2494 */       if (localEJBContext != null) {
/* 2290:2496 */         localEJBContext.throwRemote(localException);
/* 2291:     */       }
/* 2292:2498 */       throw new EJBException(localException);
/* 2293:     */     }
/* 2294:     */     finally
/* 2295:     */     {
/* 2296:2502 */       this._comp.setCurrent(localJavaComponent);
/* 2297:     */     }
/* 2298:     */   }
/* 2299:     */   
/* 2300:     */   public int deleteConsumerCrossRef(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
/* 2301:     */     throws RemoteException
/* 2302:     */   {
/* 2303:2510 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2304:2511 */     EJBContext localEJBContext = null;
/* 2305:     */     try
/* 2306:     */     {
/* 2307:2514 */       localEJBContext = this._comp.getPooledInstance();
/* 2308:2515 */       if (localEJBContext == null) {
/* 2309:2517 */         localEJBContext = _create();
/* 2310:     */       }
/* 2311:2519 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2312:2520 */       if (localEJBContext.debug) {
/* 2313:2522 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteConsumerCrossRef"));
/* 2314:     */       }
/* 2315:2524 */       int j = localOFX200BPWServicesBean
/* 2316:2525 */         .deleteConsumerCrossRef(
/* 2317:2526 */         ConsumerCrossRefInfoSeqHelper.clone(paramArrayOfConsumerCrossRefInfo));
/* 2318:     */       
/* 2319:2528 */       localEJBContext.returnToPool();
/* 2320:2529 */       return j;
/* 2321:     */     }
/* 2322:     */     catch (Exception localException)
/* 2323:     */     {
/* 2324:2533 */       if (localEJBContext != null) {
/* 2325:2535 */         localEJBContext.throwRemote(localException);
/* 2326:     */       }
/* 2327:2537 */       throw new EJBException(localException);
/* 2328:     */     }
/* 2329:     */     finally
/* 2330:     */     {
/* 2331:2541 */       this._comp.setCurrent(localJavaComponent);
/* 2332:     */     }
/* 2333:     */   }
/* 2334:     */   
/* 2335:     */   public ConsumerCrossRefInfo[] getConsumerCrossRef(String[] paramArrayOfString)
/* 2336:     */     throws RemoteException
/* 2337:     */   {
/* 2338:2549 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2339:2550 */     EJBContext localEJBContext = null;
/* 2340:     */     try
/* 2341:     */     {
/* 2342:2553 */       localEJBContext = this._comp.getPooledInstance();
/* 2343:2554 */       if (localEJBContext == null) {
/* 2344:2556 */         localEJBContext = _create();
/* 2345:     */       }
/* 2346:2558 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2347:2559 */       if (localEJBContext.debug) {
/* 2348:2561 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getConsumerCrossRef"));
/* 2349:     */       }
/* 2350:2563 */       ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo2 = localOFX200BPWServicesBean
/* 2351:2564 */         .getConsumerCrossRef(
/* 2352:2565 */         StringSeqHelper.clone(paramArrayOfString));
/* 2353:     */       
/* 2354:2567 */       arrayOfConsumerCrossRefInfo2 = ConsumerCrossRefInfoSeqHelper.clone(arrayOfConsumerCrossRefInfo2);
/* 2355:2568 */       localEJBContext.returnToPool();
/* 2356:2569 */       return arrayOfConsumerCrossRefInfo2;
/* 2357:     */     }
/* 2358:     */     catch (Exception localException)
/* 2359:     */     {
/* 2360:2573 */       if (localEJBContext != null) {
/* 2361:2575 */         localEJBContext.throwRemote(localException);
/* 2362:     */       }
/* 2363:2577 */       throw new EJBException(localException);
/* 2364:     */     }
/* 2365:     */     finally
/* 2366:     */     {
/* 2367:2581 */       this._comp.setCurrent(localJavaComponent);
/* 2368:     */     }
/* 2369:     */   }
/* 2370:     */   
/* 2371:     */   public int addCustomerBankInfo(CustomerBankInfo[] paramArrayOfCustomerBankInfo)
/* 2372:     */     throws RemoteException
/* 2373:     */   {
/* 2374:2589 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2375:2590 */     EJBContext localEJBContext = null;
/* 2376:     */     try
/* 2377:     */     {
/* 2378:2593 */       localEJBContext = this._comp.getPooledInstance();
/* 2379:2594 */       if (localEJBContext == null) {
/* 2380:2596 */         localEJBContext = _create();
/* 2381:     */       }
/* 2382:2598 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2383:2599 */       if (localEJBContext.debug) {
/* 2384:2601 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addCustomerBankInfo"));
/* 2385:     */       }
/* 2386:2603 */       int j = localOFX200BPWServicesBean
/* 2387:2604 */         .addCustomerBankInfo(
/* 2388:2605 */         CustomerBankInfoSeqHelper.clone(paramArrayOfCustomerBankInfo));
/* 2389:     */       
/* 2390:2607 */       localEJBContext.returnToPool();
/* 2391:2608 */       return j;
/* 2392:     */     }
/* 2393:     */     catch (Exception localException)
/* 2394:     */     {
/* 2395:2612 */       if (localEJBContext != null) {
/* 2396:2614 */         localEJBContext.throwRemote(localException);
/* 2397:     */       }
/* 2398:2616 */       throw new EJBException(localException);
/* 2399:     */     }
/* 2400:     */     finally
/* 2401:     */     {
/* 2402:2620 */       this._comp.setCurrent(localJavaComponent);
/* 2403:     */     }
/* 2404:     */   }
/* 2405:     */   
/* 2406:     */   public int deleteCustomerBankInfo(CustomerBankInfo[] paramArrayOfCustomerBankInfo)
/* 2407:     */     throws RemoteException
/* 2408:     */   {
/* 2409:2628 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2410:2629 */     EJBContext localEJBContext = null;
/* 2411:     */     try
/* 2412:     */     {
/* 2413:2632 */       localEJBContext = this._comp.getPooledInstance();
/* 2414:2633 */       if (localEJBContext == null) {
/* 2415:2635 */         localEJBContext = _create();
/* 2416:     */       }
/* 2417:2637 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2418:2638 */       if (localEJBContext.debug) {
/* 2419:2640 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteCustomerBankInfo"));
/* 2420:     */       }
/* 2421:2642 */       int j = localOFX200BPWServicesBean
/* 2422:2643 */         .deleteCustomerBankInfo(
/* 2423:2644 */         CustomerBankInfoSeqHelper.clone(paramArrayOfCustomerBankInfo));
/* 2424:     */       
/* 2425:2646 */       localEJBContext.returnToPool();
/* 2426:2647 */       return j;
/* 2427:     */     }
/* 2428:     */     catch (Exception localException)
/* 2429:     */     {
/* 2430:2651 */       if (localEJBContext != null) {
/* 2431:2653 */         localEJBContext.throwRemote(localException);
/* 2432:     */       }
/* 2433:2655 */       throw new EJBException(localException);
/* 2434:     */     }
/* 2435:     */     finally
/* 2436:     */     {
/* 2437:2659 */       this._comp.setCurrent(localJavaComponent);
/* 2438:     */     }
/* 2439:     */   }
/* 2440:     */   
/* 2441:     */   public CustomerBankInfo[] getCustomerBankInfo(String[] paramArrayOfString)
/* 2442:     */     throws RemoteException
/* 2443:     */   {
/* 2444:2667 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2445:2668 */     EJBContext localEJBContext = null;
/* 2446:     */     try
/* 2447:     */     {
/* 2448:2671 */       localEJBContext = this._comp.getPooledInstance();
/* 2449:2672 */       if (localEJBContext == null) {
/* 2450:2674 */         localEJBContext = _create();
/* 2451:     */       }
/* 2452:2676 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2453:2677 */       if (localEJBContext.debug) {
/* 2454:2679 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerBankInfo"));
/* 2455:     */       }
/* 2456:2681 */       CustomerBankInfo[] arrayOfCustomerBankInfo2 = localOFX200BPWServicesBean
/* 2457:2682 */         .getCustomerBankInfo(
/* 2458:2683 */         StringSeqHelper.clone(paramArrayOfString));
/* 2459:     */       
/* 2460:2685 */       arrayOfCustomerBankInfo2 = CustomerBankInfoSeqHelper.clone(arrayOfCustomerBankInfo2);
/* 2461:2686 */       localEJBContext.returnToPool();
/* 2462:2687 */       return arrayOfCustomerBankInfo2;
/* 2463:     */     }
/* 2464:     */     catch (Exception localException)
/* 2465:     */     {
/* 2466:2691 */       if (localEJBContext != null) {
/* 2467:2693 */         localEJBContext.throwRemote(localException);
/* 2468:     */       }
/* 2469:2695 */       throw new EJBException(localException);
/* 2470:     */     }
/* 2471:     */     finally
/* 2472:     */     {
/* 2473:2699 */       this._comp.setCurrent(localJavaComponent);
/* 2474:     */     }
/* 2475:     */   }
/* 2476:     */   
/* 2477:     */   public int addCustomerProductAccessInfo(CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
/* 2478:     */     throws RemoteException
/* 2479:     */   {
/* 2480:2707 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2481:2708 */     EJBContext localEJBContext = null;
/* 2482:     */     try
/* 2483:     */     {
/* 2484:2711 */       localEJBContext = this._comp.getPooledInstance();
/* 2485:2712 */       if (localEJBContext == null) {
/* 2486:2714 */         localEJBContext = _create();
/* 2487:     */       }
/* 2488:2716 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2489:2717 */       if (localEJBContext.debug) {
/* 2490:2719 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addCustomerProductAccessInfo"));
/* 2491:     */       }
/* 2492:2721 */       int j = localOFX200BPWServicesBean
/* 2493:2722 */         .addCustomerProductAccessInfo(
/* 2494:2723 */         CustomerProductAccessInfoSeqHelper.clone(paramArrayOfCustomerProductAccessInfo));
/* 2495:     */       
/* 2496:2725 */       localEJBContext.returnToPool();
/* 2497:2726 */       return j;
/* 2498:     */     }
/* 2499:     */     catch (Exception localException)
/* 2500:     */     {
/* 2501:2730 */       if (localEJBContext != null) {
/* 2502:2732 */         localEJBContext.throwRemote(localException);
/* 2503:     */       }
/* 2504:2734 */       throw new EJBException(localException);
/* 2505:     */     }
/* 2506:     */     finally
/* 2507:     */     {
/* 2508:2738 */       this._comp.setCurrent(localJavaComponent);
/* 2509:     */     }
/* 2510:     */   }
/* 2511:     */   
/* 2512:     */   public int deleteCustomerProductAccessInfo(CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
/* 2513:     */     throws RemoteException
/* 2514:     */   {
/* 2515:2746 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2516:2747 */     EJBContext localEJBContext = null;
/* 2517:     */     try
/* 2518:     */     {
/* 2519:2750 */       localEJBContext = this._comp.getPooledInstance();
/* 2520:2751 */       if (localEJBContext == null) {
/* 2521:2753 */         localEJBContext = _create();
/* 2522:     */       }
/* 2523:2755 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2524:2756 */       if (localEJBContext.debug) {
/* 2525:2758 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteCustomerProductAccessInfo"));
/* 2526:     */       }
/* 2527:2760 */       int j = localOFX200BPWServicesBean
/* 2528:2761 */         .deleteCustomerProductAccessInfo(
/* 2529:2762 */         CustomerProductAccessInfoSeqHelper.clone(paramArrayOfCustomerProductAccessInfo));
/* 2530:     */       
/* 2531:2764 */       localEJBContext.returnToPool();
/* 2532:2765 */       return j;
/* 2533:     */     }
/* 2534:     */     catch (Exception localException)
/* 2535:     */     {
/* 2536:2769 */       if (localEJBContext != null) {
/* 2537:2771 */         localEJBContext.throwRemote(localException);
/* 2538:     */       }
/* 2539:2773 */       throw new EJBException(localException);
/* 2540:     */     }
/* 2541:     */     finally
/* 2542:     */     {
/* 2543:2777 */       this._comp.setCurrent(localJavaComponent);
/* 2544:     */     }
/* 2545:     */   }
/* 2546:     */   
/* 2547:     */   public CustomerProductAccessInfo[] getCustomerProductAccessInfo(String[] paramArrayOfString)
/* 2548:     */     throws RemoteException
/* 2549:     */   {
/* 2550:2785 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2551:2786 */     EJBContext localEJBContext = null;
/* 2552:     */     try
/* 2553:     */     {
/* 2554:2789 */       localEJBContext = this._comp.getPooledInstance();
/* 2555:2790 */       if (localEJBContext == null) {
/* 2556:2792 */         localEJBContext = _create();
/* 2557:     */       }
/* 2558:2794 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2559:2795 */       if (localEJBContext.debug) {
/* 2560:2797 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerProductAccessInfo"));
/* 2561:     */       }
/* 2562:2799 */       CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo2 = localOFX200BPWServicesBean
/* 2563:2800 */         .getCustomerProductAccessInfo(
/* 2564:2801 */         StringSeqHelper.clone(paramArrayOfString));
/* 2565:     */       
/* 2566:2803 */       arrayOfCustomerProductAccessInfo2 = CustomerProductAccessInfoSeqHelper.clone(arrayOfCustomerProductAccessInfo2);
/* 2567:2804 */       localEJBContext.returnToPool();
/* 2568:2805 */       return arrayOfCustomerProductAccessInfo2;
/* 2569:     */     }
/* 2570:     */     catch (Exception localException)
/* 2571:     */     {
/* 2572:2809 */       if (localEJBContext != null) {
/* 2573:2811 */         localEJBContext.throwRemote(localException);
/* 2574:     */       }
/* 2575:2813 */       throw new EJBException(localException);
/* 2576:     */     }
/* 2577:     */     finally
/* 2578:     */     {
/* 2579:2817 */       this._comp.setCurrent(localJavaComponent);
/* 2580:     */     }
/* 2581:     */   }
/* 2582:     */   
/* 2583:     */   public boolean validateMetavanteCustAcctByConsumerID(String paramString1, String paramString2)
/* 2584:     */     throws RemoteException
/* 2585:     */   {
/* 2586:2826 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2587:2827 */     EJBContext localEJBContext = null;
/* 2588:     */     try
/* 2589:     */     {
/* 2590:2830 */       localEJBContext = this._comp.getPooledInstance();
/* 2591:2831 */       if (localEJBContext == null) {
/* 2592:2833 */         localEJBContext = _create();
/* 2593:     */       }
/* 2594:2835 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2595:2836 */       if (localEJBContext.debug) {
/* 2596:2838 */         localEJBContext.logger.debug(localEJBContext.debugMsg("validateMetavanteCustAcctByConsumerID"));
/* 2597:     */       }
/* 2598:2840 */       boolean bool2 = localOFX200BPWServicesBean
/* 2599:2841 */         .validateMetavanteCustAcctByConsumerID(
/* 2600:2842 */         paramString1, 
/* 2601:2843 */         paramString2);
/* 2602:     */       
/* 2603:2845 */       localEJBContext.returnToPool();
/* 2604:2846 */       return bool2;
/* 2605:     */     }
/* 2606:     */     catch (Exception localException)
/* 2607:     */     {
/* 2608:2850 */       if (localEJBContext != null) {
/* 2609:2852 */         localEJBContext.throwRemote(localException);
/* 2610:     */       }
/* 2611:2854 */       throw new EJBException(localException);
/* 2612:     */     }
/* 2613:     */     finally
/* 2614:     */     {
/* 2615:2858 */       this._comp.setCurrent(localJavaComponent);
/* 2616:     */     }
/* 2617:     */   }
/* 2618:     */   
/* 2619:     */   public boolean validateMetavanteCustAcctByCustomerID(String paramString1, String paramString2)
/* 2620:     */     throws RemoteException
/* 2621:     */   {
/* 2622:2867 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2623:2868 */     EJBContext localEJBContext = null;
/* 2624:     */     try
/* 2625:     */     {
/* 2626:2871 */       localEJBContext = this._comp.getPooledInstance();
/* 2627:2872 */       if (localEJBContext == null) {
/* 2628:2874 */         localEJBContext = _create();
/* 2629:     */       }
/* 2630:2876 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2631:2877 */       if (localEJBContext.debug) {
/* 2632:2879 */         localEJBContext.logger.debug(localEJBContext.debugMsg("validateMetavanteCustAcctByCustomerID"));
/* 2633:     */       }
/* 2634:2881 */       boolean bool2 = localOFX200BPWServicesBean
/* 2635:2882 */         .validateMetavanteCustAcctByCustomerID(
/* 2636:2883 */         paramString1, 
/* 2637:2884 */         paramString2);
/* 2638:     */       
/* 2639:2886 */       localEJBContext.returnToPool();
/* 2640:2887 */       return bool2;
/* 2641:     */     }
/* 2642:     */     catch (Exception localException)
/* 2643:     */     {
/* 2644:2891 */       if (localEJBContext != null) {
/* 2645:2893 */         localEJBContext.throwRemote(localException);
/* 2646:     */       }
/* 2647:2895 */       throw new EJBException(localException);
/* 2648:     */     }
/* 2649:     */     finally
/* 2650:     */     {
/* 2651:2899 */       this._comp.setCurrent(localJavaComponent);
/* 2652:     */     }
/* 2653:     */   }
/* 2654:     */   
/* 2655:     */   public BPWHist getPmtHistory(BPWHist paramBPWHist)
/* 2656:     */     throws RemoteException
/* 2657:     */   {
/* 2658:2907 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2659:2908 */     EJBContext localEJBContext = null;
/* 2660:     */     try
/* 2661:     */     {
/* 2662:2911 */       localEJBContext = this._comp.getPooledInstance();
/* 2663:2912 */       if (localEJBContext == null) {
/* 2664:2914 */         localEJBContext = _create();
/* 2665:     */       }
/* 2666:2916 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2667:2917 */       if (localEJBContext.debug) {
/* 2668:2919 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPmtHistory"));
/* 2669:     */       }
/* 2670:2921 */       BPWHist localBPWHist2 = localOFX200BPWServicesBean
/* 2671:2922 */         .getPmtHistory(
/* 2672:2923 */         BPWHistHelper.clone(paramBPWHist));
/* 2673:     */       
/* 2674:2925 */       localBPWHist2 = BPWHistHelper.clone(localBPWHist2);
/* 2675:2926 */       localEJBContext.returnToPool();
/* 2676:2927 */       return localBPWHist2;
/* 2677:     */     }
/* 2678:     */     catch (Exception localException)
/* 2679:     */     {
/* 2680:2931 */       if (localEJBContext != null) {
/* 2681:2933 */         localEJBContext.throwRemote(localException);
/* 2682:     */       }
/* 2683:2935 */       throw new EJBException(localException);
/* 2684:     */     }
/* 2685:     */     finally
/* 2686:     */     {
/* 2687:2939 */       this._comp.setCurrent(localJavaComponent);
/* 2688:     */     }
/* 2689:     */   }
/* 2690:     */   
/* 2691:     */   public BPWHist getIntraHistory(BPWHist paramBPWHist)
/* 2692:     */     throws RemoteException
/* 2693:     */   {
/* 2694:2947 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2695:2948 */     EJBContext localEJBContext = null;
/* 2696:     */     try
/* 2697:     */     {
/* 2698:2951 */       localEJBContext = this._comp.getPooledInstance();
/* 2699:2952 */       if (localEJBContext == null) {
/* 2700:2954 */         localEJBContext = _create();
/* 2701:     */       }
/* 2702:2956 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2703:2957 */       if (localEJBContext.debug) {
/* 2704:2959 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getIntraHistory"));
/* 2705:     */       }
/* 2706:2961 */       BPWHist localBPWHist2 = localOFX200BPWServicesBean
/* 2707:2962 */         .getIntraHistory(
/* 2708:2963 */         BPWHistHelper.clone(paramBPWHist));
/* 2709:     */       
/* 2710:2965 */       localBPWHist2 = BPWHistHelper.clone(localBPWHist2);
/* 2711:2966 */       localEJBContext.returnToPool();
/* 2712:2967 */       return localBPWHist2;
/* 2713:     */     }
/* 2714:     */     catch (Exception localException)
/* 2715:     */     {
/* 2716:2971 */       if (localEJBContext != null) {
/* 2717:2973 */         localEJBContext.throwRemote(localException);
/* 2718:     */       }
/* 2719:2975 */       throw new EJBException(localException);
/* 2720:     */     }
/* 2721:     */     finally
/* 2722:     */     {
/* 2723:2979 */       this._comp.setCurrent(localJavaComponent);
/* 2724:     */     }
/* 2725:     */   }
/* 2726:     */   
/* 2727:     */   public IntraTrnInfo getIntraById(String paramString)
/* 2728:     */     throws RemoteException
/* 2729:     */   {
/* 2730:2987 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2731:2988 */     EJBContext localEJBContext = null;
/* 2732:     */     try
/* 2733:     */     {
/* 2734:2991 */       localEJBContext = this._comp.getPooledInstance();
/* 2735:2992 */       if (localEJBContext == null) {
/* 2736:2994 */         localEJBContext = _create();
/* 2737:     */       }
/* 2738:2996 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2739:2997 */       if (localEJBContext.debug) {
/* 2740:2999 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getIntraById"));
/* 2741:     */       }
/* 2742:3001 */       IntraTrnInfo localIntraTrnInfo2 = localOFX200BPWServicesBean
/* 2743:3002 */         .getIntraById(
/* 2744:3003 */         paramString);
/* 2745:     */       
/* 2746:3005 */       localIntraTrnInfo2 = (IntraTrnInfo)EJBContext.clone(localIntraTrnInfo2);
/* 2747:3006 */       localEJBContext.returnToPool();
/* 2748:3007 */       return localIntraTrnInfo2;
/* 2749:     */     }
/* 2750:     */     catch (Exception localException)
/* 2751:     */     {
/* 2752:3011 */       if (localEJBContext != null) {
/* 2753:3013 */         localEJBContext.throwRemote(localException);
/* 2754:     */       }
/* 2755:3015 */       throw new EJBException(localException);
/* 2756:     */     }
/* 2757:     */     finally
/* 2758:     */     {
/* 2759:3019 */       this._comp.setCurrent(localJavaComponent);
/* 2760:     */     }
/* 2761:     */   }
/* 2762:     */   
/* 2763:     */   public IntraTrnInfo[] getIntraById(String[] paramArrayOfString)
/* 2764:     */     throws RemoteException
/* 2765:     */   {
/* 2766:3027 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2767:3028 */     EJBContext localEJBContext = null;
/* 2768:     */     try
/* 2769:     */     {
/* 2770:3031 */       localEJBContext = this._comp.getPooledInstance();
/* 2771:3032 */       if (localEJBContext == null) {
/* 2772:3034 */         localEJBContext = _create();
/* 2773:     */       }
/* 2774:3036 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2775:3037 */       if (localEJBContext.debug) {
/* 2776:3039 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getIntraById"));
/* 2777:     */       }
/* 2778:3041 */       IntraTrnInfo[] arrayOfIntraTrnInfo2 = localOFX200BPWServicesBean
/* 2779:3042 */         .getIntraById(
/* 2780:3043 */         StringSeqHelper.clone(paramArrayOfString));
/* 2781:     */       
/* 2782:3045 */       arrayOfIntraTrnInfo2 = IntraTrnInfoSeqHelper.clone(arrayOfIntraTrnInfo2);
/* 2783:3046 */       localEJBContext.returnToPool();
/* 2784:3047 */       return arrayOfIntraTrnInfo2;
/* 2785:     */     }
/* 2786:     */     catch (Exception localException)
/* 2787:     */     {
/* 2788:3051 */       if (localEJBContext != null) {
/* 2789:3053 */         localEJBContext.throwRemote(localException);
/* 2790:     */       }
/* 2791:3055 */       throw new EJBException(localException);
/* 2792:     */     }
/* 2793:     */     finally
/* 2794:     */     {
/* 2795:3059 */       this._comp.setCurrent(localJavaComponent);
/* 2796:     */     }
/* 2797:     */   }
/* 2798:     */   
/* 2799:     */   public IntraTrnInfo[] getIntraByRecSrvrTId(String[] paramArrayOfString, boolean paramBoolean)
/* 2800:     */     throws RemoteException
/* 2801:     */   {
/* 2802:3068 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2803:3069 */     EJBContext localEJBContext = null;
/* 2804:     */     try
/* 2805:     */     {
/* 2806:3072 */       localEJBContext = this._comp.getPooledInstance();
/* 2807:3073 */       if (localEJBContext == null) {
/* 2808:3075 */         localEJBContext = _create();
/* 2809:     */       }
/* 2810:3077 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2811:3078 */       if (localEJBContext.debug) {
/* 2812:3080 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getIntraByRecSrvrTId"));
/* 2813:     */       }
/* 2814:3082 */       IntraTrnInfo[] arrayOfIntraTrnInfo2 = localOFX200BPWServicesBean
/* 2815:3083 */         .getIntraByRecSrvrTId(
/* 2816:3084 */         StringSeqHelper.clone(paramArrayOfString), 
/* 2817:3085 */         paramBoolean);
/* 2818:     */       
/* 2819:3087 */       arrayOfIntraTrnInfo2 = IntraTrnInfoSeqHelper.clone(arrayOfIntraTrnInfo2);
/* 2820:3088 */       localEJBContext.returnToPool();
/* 2821:3089 */       return arrayOfIntraTrnInfo2;
/* 2822:     */     }
/* 2823:     */     catch (Exception localException)
/* 2824:     */     {
/* 2825:3093 */       if (localEJBContext != null) {
/* 2826:3095 */         localEJBContext.throwRemote(localException);
/* 2827:     */       }
/* 2828:3097 */       throw new EJBException(localException);
/* 2829:     */     }
/* 2830:     */     finally
/* 2831:     */     {
/* 2832:3101 */       this._comp.setCurrent(localJavaComponent);
/* 2833:     */     }
/* 2834:     */   }
/* 2835:     */   
/* 2836:     */   public PmtInfo[] getPmtById(String[] paramArrayOfString)
/* 2837:     */     throws RemoteException
/* 2838:     */   {
/* 2839:3109 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2840:3110 */     EJBContext localEJBContext = null;
/* 2841:     */     try
/* 2842:     */     {
/* 2843:3113 */       localEJBContext = this._comp.getPooledInstance();
/* 2844:3114 */       if (localEJBContext == null) {
/* 2845:3116 */         localEJBContext = _create();
/* 2846:     */       }
/* 2847:3118 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2848:3119 */       if (localEJBContext.debug) {
/* 2849:3121 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPmtById"));
/* 2850:     */       }
/* 2851:3123 */       PmtInfo[] arrayOfPmtInfo2 = localOFX200BPWServicesBean
/* 2852:3124 */         .getPmtById(
/* 2853:3125 */         StringSeqHelper.clone(paramArrayOfString));
/* 2854:     */       
/* 2855:3127 */       arrayOfPmtInfo2 = PmtInfoSeqHelper.clone(arrayOfPmtInfo2);
/* 2856:3128 */       localEJBContext.returnToPool();
/* 2857:3129 */       return arrayOfPmtInfo2;
/* 2858:     */     }
/* 2859:     */     catch (Exception localException)
/* 2860:     */     {
/* 2861:3133 */       if (localEJBContext != null) {
/* 2862:3135 */         localEJBContext.throwRemote(localException);
/* 2863:     */       }
/* 2864:3137 */       throw new EJBException(localException);
/* 2865:     */     }
/* 2866:     */     finally
/* 2867:     */     {
/* 2868:3141 */       this._comp.setCurrent(localJavaComponent);
/* 2869:     */     }
/* 2870:     */   }
/* 2871:     */   
/* 2872:     */   public PmtInfo getPmtById(String paramString)
/* 2873:     */     throws RemoteException
/* 2874:     */   {
/* 2875:3149 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2876:3150 */     EJBContext localEJBContext = null;
/* 2877:     */     try
/* 2878:     */     {
/* 2879:3153 */       localEJBContext = this._comp.getPooledInstance();
/* 2880:3154 */       if (localEJBContext == null) {
/* 2881:3156 */         localEJBContext = _create();
/* 2882:     */       }
/* 2883:3158 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2884:3159 */       if (localEJBContext.debug) {
/* 2885:3161 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPmtById"));
/* 2886:     */       }
/* 2887:3163 */       PmtInfo localPmtInfo2 = localOFX200BPWServicesBean
/* 2888:3164 */         .getPmtById(
/* 2889:3165 */         paramString);
/* 2890:     */       
/* 2891:3167 */       localPmtInfo2 = (PmtInfo)EJBContext.clone(localPmtInfo2);
/* 2892:3168 */       localEJBContext.returnToPool();
/* 2893:3169 */       return localPmtInfo2;
/* 2894:     */     }
/* 2895:     */     catch (Exception localException)
/* 2896:     */     {
/* 2897:3173 */       if (localEJBContext != null) {
/* 2898:3175 */         localEJBContext.throwRemote(localException);
/* 2899:     */       }
/* 2900:3177 */       throw new EJBException(localException);
/* 2901:     */     }
/* 2902:     */     finally
/* 2903:     */     {
/* 2904:3181 */       this._comp.setCurrent(localJavaComponent);
/* 2905:     */     }
/* 2906:     */   }
/* 2907:     */   
/* 2908:     */   public RecPmtInfo[] getRecPmtById(String[] paramArrayOfString)
/* 2909:     */     throws RemoteException
/* 2910:     */   {
/* 2911:3189 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2912:3190 */     EJBContext localEJBContext = null;
/* 2913:     */     try
/* 2914:     */     {
/* 2915:3193 */       localEJBContext = this._comp.getPooledInstance();
/* 2916:3194 */       if (localEJBContext == null) {
/* 2917:3196 */         localEJBContext = _create();
/* 2918:     */       }
/* 2919:3198 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2920:3199 */       if (localEJBContext.debug) {
/* 2921:3201 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecPmtById"));
/* 2922:     */       }
/* 2923:3203 */       RecPmtInfo[] arrayOfRecPmtInfo2 = localOFX200BPWServicesBean
/* 2924:3204 */         .getRecPmtById(
/* 2925:3205 */         StringSeqHelper.clone(paramArrayOfString));
/* 2926:     */       
/* 2927:3207 */       arrayOfRecPmtInfo2 = RecPmtInfoSeqHelper.clone(arrayOfRecPmtInfo2);
/* 2928:3208 */       localEJBContext.returnToPool();
/* 2929:3209 */       return arrayOfRecPmtInfo2;
/* 2930:     */     }
/* 2931:     */     catch (Exception localException)
/* 2932:     */     {
/* 2933:3213 */       if (localEJBContext != null) {
/* 2934:3215 */         localEJBContext.throwRemote(localException);
/* 2935:     */       }
/* 2936:3217 */       throw new EJBException(localException);
/* 2937:     */     }
/* 2938:     */     finally
/* 2939:     */     {
/* 2940:3221 */       this._comp.setCurrent(localJavaComponent);
/* 2941:     */     }
/* 2942:     */   }
/* 2943:     */   
/* 2944:     */   public RecIntraTrnInfo[] getRecIntraById(String[] paramArrayOfString)
/* 2945:     */     throws RemoteException
/* 2946:     */   {
/* 2947:3229 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2948:3230 */     EJBContext localEJBContext = null;
/* 2949:     */     try
/* 2950:     */     {
/* 2951:3233 */       localEJBContext = this._comp.getPooledInstance();
/* 2952:3234 */       if (localEJBContext == null) {
/* 2953:3236 */         localEJBContext = _create();
/* 2954:     */       }
/* 2955:3238 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2956:3239 */       if (localEJBContext.debug) {
/* 2957:3241 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecIntraById"));
/* 2958:     */       }
/* 2959:3243 */       RecIntraTrnInfo[] arrayOfRecIntraTrnInfo2 = localOFX200BPWServicesBean
/* 2960:3244 */         .getRecIntraById(
/* 2961:3245 */         StringSeqHelper.clone(paramArrayOfString));
/* 2962:     */       
/* 2963:3247 */       arrayOfRecIntraTrnInfo2 = RecIntraTrnInfoSeqHelper.clone(arrayOfRecIntraTrnInfo2);
/* 2964:3248 */       localEJBContext.returnToPool();
/* 2965:3249 */       return arrayOfRecIntraTrnInfo2;
/* 2966:     */     }
/* 2967:     */     catch (Exception localException)
/* 2968:     */     {
/* 2969:3253 */       if (localEJBContext != null) {
/* 2970:3255 */         localEJBContext.throwRemote(localException);
/* 2971:     */       }
/* 2972:3257 */       throw new EJBException(localException);
/* 2973:     */     }
/* 2974:     */     finally
/* 2975:     */     {
/* 2976:3261 */       this._comp.setCurrent(localJavaComponent);
/* 2977:     */     }
/* 2978:     */   }
/* 2979:     */   
/* 2980:     */   public RecIntraTrnInfo getRecIntraById(String paramString)
/* 2981:     */     throws RemoteException
/* 2982:     */   {
/* 2983:3269 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2984:3270 */     EJBContext localEJBContext = null;
/* 2985:     */     try
/* 2986:     */     {
/* 2987:3273 */       localEJBContext = this._comp.getPooledInstance();
/* 2988:3274 */       if (localEJBContext == null) {
/* 2989:3276 */         localEJBContext = _create();
/* 2990:     */       }
/* 2991:3278 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 2992:3279 */       if (localEJBContext.debug) {
/* 2993:3281 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecIntraById"));
/* 2994:     */       }
/* 2995:3283 */       RecIntraTrnInfo localRecIntraTrnInfo2 = localOFX200BPWServicesBean
/* 2996:3284 */         .getRecIntraById(
/* 2997:3285 */         paramString);
/* 2998:     */       
/* 2999:3287 */       localRecIntraTrnInfo2 = (RecIntraTrnInfo)EJBContext.clone(localRecIntraTrnInfo2);
/* 3000:3288 */       localEJBContext.returnToPool();
/* 3001:3289 */       return localRecIntraTrnInfo2;
/* 3002:     */     }
/* 3003:     */     catch (Exception localException)
/* 3004:     */     {
/* 3005:3293 */       if (localEJBContext != null) {
/* 3006:3295 */         localEJBContext.throwRemote(localException);
/* 3007:     */       }
/* 3008:3297 */       throw new EJBException(localException);
/* 3009:     */     }
/* 3010:     */     finally
/* 3011:     */     {
/* 3012:3301 */       this._comp.setCurrent(localJavaComponent);
/* 3013:     */     }
/* 3014:     */   }
/* 3015:     */   
/* 3016:     */   public PayeeInfo getPayeeByListId(String paramString1, String paramString2)
/* 3017:     */     throws RemoteException, FFSException
/* 3018:     */   {
/* 3019:3310 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3020:3311 */     EJBContext localEJBContext = null;
/* 3021:     */     try
/* 3022:     */     {
/* 3023:3314 */       localEJBContext = this._comp.getPooledInstance();
/* 3024:3315 */       if (localEJBContext == null) {
/* 3025:3317 */         localEJBContext = _create();
/* 3026:     */       }
/* 3027:3319 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 3028:3320 */       if (localEJBContext.debug) {
/* 3029:3322 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPayeeByListId"));
/* 3030:     */       }
/* 3031:3324 */       PayeeInfo localPayeeInfo2 = localOFX200BPWServicesBean
/* 3032:3325 */         .getPayeeByListId(
/* 3033:3326 */         paramString1, 
/* 3034:3327 */         paramString2);
/* 3035:     */       
/* 3036:3329 */       localPayeeInfo2 = (PayeeInfo)EJBContext.clone(localPayeeInfo2);
/* 3037:3330 */       localEJBContext.returnToPool();
/* 3038:3331 */       return localPayeeInfo2;
/* 3039:     */     }
/* 3040:     */     catch (Exception localException)
/* 3041:     */     {
/* 3042:3335 */       if (localEJBContext != null)
/* 3043:     */       {
/* 3044:3337 */         if ((localException instanceof FFSException)) {
/* 3045:3339 */           throw ((FFSException)EJBContext.clone(localException));
/* 3046:     */         }
/* 3047:3341 */         localEJBContext.throwRemote(localException);
/* 3048:     */       }
/* 3049:3343 */       throw new EJBException(localException);
/* 3050:     */     }
/* 3051:     */     finally
/* 3052:     */     {
/* 3053:3347 */       this._comp.setCurrent(localJavaComponent);
/* 3054:     */     }
/* 3055:     */   }
/* 3056:     */   
/* 3057:     */   public AccountTypesMap getAccountTypesMap()
/* 3058:     */     throws RemoteException, FFSException
/* 3059:     */   {
/* 3060:3354 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 3061:3355 */     EJBContext localEJBContext = null;
/* 3062:     */     try
/* 3063:     */     {
/* 3064:3358 */       localEJBContext = this._comp.getPooledInstance();
/* 3065:3359 */       if (localEJBContext == null) {
/* 3066:3361 */         localEJBContext = _create();
/* 3067:     */       }
/* 3068:3363 */       OFX200BPWServicesBean localOFX200BPWServicesBean = (OFX200BPWServicesBean)localEJBContext.getBean();
/* 3069:3364 */       if (localEJBContext.debug) {
/* 3070:3366 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getAccountTypesMap"));
/* 3071:     */       }
/* 3072:3368 */       AccountTypesMap localAccountTypesMap2 = localOFX200BPWServicesBean
/* 3073:3369 */         .getAccountTypesMap();
/* 3074:     */       
/* 3075:3371 */       localAccountTypesMap2 = (AccountTypesMap)EJBContext.clone(localAccountTypesMap2);
/* 3076:3372 */       localEJBContext.returnToPool();
/* 3077:3373 */       return localAccountTypesMap2;
/* 3078:     */     }
/* 3079:     */     catch (Exception localException)
/* 3080:     */     {
/* 3081:3377 */       if (localEJBContext != null)
/* 3082:     */       {
/* 3083:3379 */         if ((localException instanceof FFSException)) {
/* 3084:3381 */           throw ((FFSException)EJBContext.clone(localException));
/* 3085:     */         }
/* 3086:3383 */         localEJBContext.throwRemote(localException);
/* 3087:     */       }
/* 3088:3385 */       throw new EJBException(localException);
/* 3089:     */     }
/* 3090:     */     finally
/* 3091:     */     {
/* 3092:3389 */       this._comp.setCurrent(localJavaComponent);
/* 3093:     */     }
/* 3094:     */   }
/* 3095:     */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices._lwc_rs_OFX200BPWServices_OFX200BPWServicesBean
 * JD-Core Version:    0.7.0.1
 */