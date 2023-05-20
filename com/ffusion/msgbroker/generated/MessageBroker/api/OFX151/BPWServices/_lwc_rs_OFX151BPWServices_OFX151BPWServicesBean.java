/*    1:     */ package com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices;
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
/*   30:     */ import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
/*   31:     */ import com.ffusion.ffs.bpw.interfaces.RecPmtInfoSeqHelper;
/*   32:     */ import com.ffusion.ffs.bpw.util.AccountTypesMap;
/*   33:     */ import com.ffusion.ffs.interfaces.FFSException;
/*   34:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserData;
/*   35:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserDataHelper;
/*   36:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1;
/*   37:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1Helper;
/*   38:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1;
/*   39:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1Helper;
/*   40:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1SeqHelper;
/*   41:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1;
/*   42:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1Helper;
/*   43:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1;
/*   44:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Helper;
/*   45:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1;
/*   46:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1Helper;
/*   47:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1;
/*   48:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1Helper;
/*   49:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1;
/*   50:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1Helper;
/*   51:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1;
/*   52:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1Helper;
/*   53:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1;
/*   54:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1Helper;
/*   55:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1;
/*   56:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1Helper;
/*   57:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1;
/*   58:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1Helper;
/*   59:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1;
/*   60:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1Helper;
/*   61:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1SeqHelper;
/*   62:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1;
/*   63:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1Helper;
/*   64:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1;
/*   65:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1Helper;
/*   66:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1;
/*   67:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1Helper;
/*   68:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1;
/*   69:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1Helper;
/*   70:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1;
/*   71:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1Helper;
/*   72:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1;
/*   73:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1Helper;
/*   74:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1;
/*   75:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1Helper;
/*   76:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1;
/*   77:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1Helper;
/*   78:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1;
/*   79:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1Helper;
/*   80:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1;
/*   81:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Helper;
/*   82:     */ import com.sybase.ejb.cts.StringSeqHelper;
/*   83:     */ import com.sybase.ejb.lwc.EJBContext;
/*   84:     */ import com.sybase.ejb.lwc.EJBObject;
/*   85:     */ import com.sybase.ejb.lwc.JavaComponent;
/*   86:     */ import com.sybase.ejb.lwc.Stub;
/*   87:     */ import com.sybase.jaguar.logger.Logger;
/*   88:     */ import java.rmi.RemoteException;
/*   89:     */ import javax.ejb.EJBException;
/*   90:     */ 
/*   91:     */ public class _lwc_rs_OFX151BPWServices_OFX151BPWServicesBean
/*   92:     */   extends EJBObject
/*   93:     */   implements IOFX151BPWServices
/*   94:     */ {
/*   95:  14 */   private static JavaComponent __component = JavaComponent.get("OFX151BPWServices/OFX151BPWServicesBean");
/*   96:     */   
/*   97:     */   public _lwc_rs_OFX151BPWServices_OFX151BPWServicesBean(Object paramObject)
/*   98:     */   {
/*   99:  19 */     super(__component);
/*  100:  20 */     this.key = paramObject;
/*  101:     */   }
/*  102:     */   
/*  103:     */   private EJBContext _create()
/*  104:     */     throws Exception
/*  105:     */   {
/*  106:  26 */     EJBContext localEJBContext = this._comp.getInstance();
/*  107:  27 */     OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  108:  28 */     if (localEJBContext.debug) {
/*  109:  30 */       localEJBContext.logger.debug(localEJBContext.debugMsg("ejbCreate"));
/*  110:     */     }
/*  111:  32 */     localOFX151BPWServicesBean.ejbCreate();
/*  112:  33 */     return localEJBContext;
/*  113:     */   }
/*  114:     */   
/*  115:     */   public void disconnect()
/*  116:     */     throws RemoteException
/*  117:     */   {
/*  118:  39 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  119:  40 */     EJBContext localEJBContext = null;
/*  120:     */     try
/*  121:     */     {
/*  122:  43 */       localEJBContext = this._comp.getPooledInstance();
/*  123:  44 */       if (localEJBContext == null) {
/*  124:  46 */         localEJBContext = _create();
/*  125:     */       }
/*  126:  48 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  127:  49 */       if (localEJBContext.debug) {
/*  128:  51 */         localEJBContext.logger.debug(localEJBContext.debugMsg("disconnect"));
/*  129:     */       }
/*  130:  54 */       localOFX151BPWServicesBean.disconnect();
/*  131:     */       
/*  132:  56 */       localEJBContext.returnToPool();
/*  133:     */     }
/*  134:     */     catch (Exception localException)
/*  135:     */     {
/*  136:  60 */       if (localEJBContext != null) {
/*  137:  62 */         localEJBContext.throwRemote(localException);
/*  138:     */       }
/*  139:  64 */       throw new EJBException(localException);
/*  140:     */     }
/*  141:     */     finally
/*  142:     */     {
/*  143:  68 */       this._comp.setCurrent(localJavaComponent);
/*  144:     */     }
/*  145:     */   }
/*  146:     */   
/*  147:     */   public int addCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
/*  148:     */     throws RemoteException
/*  149:     */   {
/*  150:  76 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  151:  77 */     EJBContext localEJBContext = null;
/*  152:     */     try
/*  153:     */     {
/*  154:  80 */       localEJBContext = this._comp.getPooledInstance();
/*  155:  81 */       if (localEJBContext == null) {
/*  156:  83 */         localEJBContext = _create();
/*  157:     */       }
/*  158:  85 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  159:  86 */       if (localEJBContext.debug) {
/*  160:  88 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addCustomers"));
/*  161:     */       }
/*  162:  90 */       int j = localOFX151BPWServicesBean
/*  163:  91 */         .addCustomers(
/*  164:  92 */         CustomerInfoSeqHelper.clone(paramArrayOfCustomerInfo));
/*  165:     */       
/*  166:  94 */       localEJBContext.returnToPool();
/*  167:  95 */       return j;
/*  168:     */     }
/*  169:     */     catch (Exception localException)
/*  170:     */     {
/*  171:  99 */       if (localEJBContext != null) {
/*  172: 101 */         localEJBContext.throwRemote(localException);
/*  173:     */       }
/*  174: 103 */       throw new EJBException(localException);
/*  175:     */     }
/*  176:     */     finally
/*  177:     */     {
/*  178: 107 */       this._comp.setCurrent(localJavaComponent);
/*  179:     */     }
/*  180:     */   }
/*  181:     */   
/*  182:     */   public int modifyCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
/*  183:     */     throws RemoteException
/*  184:     */   {
/*  185: 115 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  186: 116 */     EJBContext localEJBContext = null;
/*  187:     */     try
/*  188:     */     {
/*  189: 119 */       localEJBContext = this._comp.getPooledInstance();
/*  190: 120 */       if (localEJBContext == null) {
/*  191: 122 */         localEJBContext = _create();
/*  192:     */       }
/*  193: 124 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  194: 125 */       if (localEJBContext.debug) {
/*  195: 127 */         localEJBContext.logger.debug(localEJBContext.debugMsg("modifyCustomers"));
/*  196:     */       }
/*  197: 129 */       int j = localOFX151BPWServicesBean
/*  198: 130 */         .modifyCustomers(
/*  199: 131 */         CustomerInfoSeqHelper.clone(paramArrayOfCustomerInfo));
/*  200:     */       
/*  201: 133 */       localEJBContext.returnToPool();
/*  202: 134 */       return j;
/*  203:     */     }
/*  204:     */     catch (Exception localException)
/*  205:     */     {
/*  206: 138 */       if (localEJBContext != null) {
/*  207: 140 */         localEJBContext.throwRemote(localException);
/*  208:     */       }
/*  209: 142 */       throw new EJBException(localException);
/*  210:     */     }
/*  211:     */     finally
/*  212:     */     {
/*  213: 146 */       this._comp.setCurrent(localJavaComponent);
/*  214:     */     }
/*  215:     */   }
/*  216:     */   
/*  217:     */   public int deleteCustomers(String[] paramArrayOfString)
/*  218:     */     throws RemoteException
/*  219:     */   {
/*  220: 154 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  221: 155 */     EJBContext localEJBContext = null;
/*  222:     */     try
/*  223:     */     {
/*  224: 158 */       localEJBContext = this._comp.getPooledInstance();
/*  225: 159 */       if (localEJBContext == null) {
/*  226: 161 */         localEJBContext = _create();
/*  227:     */       }
/*  228: 163 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  229: 164 */       if (localEJBContext.debug) {
/*  230: 166 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteCustomers"));
/*  231:     */       }
/*  232: 168 */       int j = localOFX151BPWServicesBean
/*  233: 169 */         .deleteCustomers(
/*  234: 170 */         StringSeqHelper.clone(paramArrayOfString));
/*  235:     */       
/*  236: 172 */       localEJBContext.returnToPool();
/*  237: 173 */       return j;
/*  238:     */     }
/*  239:     */     catch (Exception localException)
/*  240:     */     {
/*  241: 177 */       if (localEJBContext != null) {
/*  242: 179 */         localEJBContext.throwRemote(localException);
/*  243:     */       }
/*  244: 181 */       throw new EJBException(localException);
/*  245:     */     }
/*  246:     */     finally
/*  247:     */     {
/*  248: 185 */       this._comp.setCurrent(localJavaComponent);
/*  249:     */     }
/*  250:     */   }
/*  251:     */   
/*  252:     */   public int deleteCustomers(String[] paramArrayOfString, int paramInt)
/*  253:     */     throws RemoteException
/*  254:     */   {
/*  255: 194 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  256: 195 */     EJBContext localEJBContext = null;
/*  257:     */     try
/*  258:     */     {
/*  259: 198 */       localEJBContext = this._comp.getPooledInstance();
/*  260: 199 */       if (localEJBContext == null) {
/*  261: 201 */         localEJBContext = _create();
/*  262:     */       }
/*  263: 203 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  264: 204 */       if (localEJBContext.debug) {
/*  265: 206 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteCustomers"));
/*  266:     */       }
/*  267: 208 */       int j = localOFX151BPWServicesBean
/*  268: 209 */         .deleteCustomers(
/*  269: 210 */         StringSeqHelper.clone(paramArrayOfString), 
/*  270: 211 */         paramInt);
/*  271:     */       
/*  272: 213 */       localEJBContext.returnToPool();
/*  273: 214 */       return j;
/*  274:     */     }
/*  275:     */     catch (Exception localException)
/*  276:     */     {
/*  277: 218 */       if (localEJBContext != null) {
/*  278: 220 */         localEJBContext.throwRemote(localException);
/*  279:     */       }
/*  280: 222 */       throw new EJBException(localException);
/*  281:     */     }
/*  282:     */     finally
/*  283:     */     {
/*  284: 226 */       this._comp.setCurrent(localJavaComponent);
/*  285:     */     }
/*  286:     */   }
/*  287:     */   
/*  288:     */   public int deactivateCustomers(String[] paramArrayOfString)
/*  289:     */     throws RemoteException
/*  290:     */   {
/*  291: 234 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  292: 235 */     EJBContext localEJBContext = null;
/*  293:     */     try
/*  294:     */     {
/*  295: 238 */       localEJBContext = this._comp.getPooledInstance();
/*  296: 239 */       if (localEJBContext == null) {
/*  297: 241 */         localEJBContext = _create();
/*  298:     */       }
/*  299: 243 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  300: 244 */       if (localEJBContext.debug) {
/*  301: 246 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deactivateCustomers"));
/*  302:     */       }
/*  303: 248 */       int j = localOFX151BPWServicesBean
/*  304: 249 */         .deactivateCustomers(
/*  305: 250 */         StringSeqHelper.clone(paramArrayOfString));
/*  306:     */       
/*  307: 252 */       localEJBContext.returnToPool();
/*  308: 253 */       return j;
/*  309:     */     }
/*  310:     */     catch (Exception localException)
/*  311:     */     {
/*  312: 257 */       if (localEJBContext != null) {
/*  313: 259 */         localEJBContext.throwRemote(localException);
/*  314:     */       }
/*  315: 261 */       throw new EJBException(localException);
/*  316:     */     }
/*  317:     */     finally
/*  318:     */     {
/*  319: 265 */       this._comp.setCurrent(localJavaComponent);
/*  320:     */     }
/*  321:     */   }
/*  322:     */   
/*  323:     */   public int activateCustomers(String[] paramArrayOfString)
/*  324:     */     throws RemoteException
/*  325:     */   {
/*  326: 273 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  327: 274 */     EJBContext localEJBContext = null;
/*  328:     */     try
/*  329:     */     {
/*  330: 277 */       localEJBContext = this._comp.getPooledInstance();
/*  331: 278 */       if (localEJBContext == null) {
/*  332: 280 */         localEJBContext = _create();
/*  333:     */       }
/*  334: 282 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  335: 283 */       if (localEJBContext.debug) {
/*  336: 285 */         localEJBContext.logger.debug(localEJBContext.debugMsg("activateCustomers"));
/*  337:     */       }
/*  338: 287 */       int j = localOFX151BPWServicesBean
/*  339: 288 */         .activateCustomers(
/*  340: 289 */         StringSeqHelper.clone(paramArrayOfString));
/*  341:     */       
/*  342: 291 */       localEJBContext.returnToPool();
/*  343: 292 */       return j;
/*  344:     */     }
/*  345:     */     catch (Exception localException)
/*  346:     */     {
/*  347: 296 */       if (localEJBContext != null) {
/*  348: 298 */         localEJBContext.throwRemote(localException);
/*  349:     */       }
/*  350: 300 */       throw new EJBException(localException);
/*  351:     */     }
/*  352:     */     finally
/*  353:     */     {
/*  354: 304 */       this._comp.setCurrent(localJavaComponent);
/*  355:     */     }
/*  356:     */   }
/*  357:     */   
/*  358:     */   public CustomerInfo[] getCustomersInfo(String[] paramArrayOfString)
/*  359:     */     throws RemoteException
/*  360:     */   {
/*  361: 312 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  362: 313 */     EJBContext localEJBContext = null;
/*  363:     */     try
/*  364:     */     {
/*  365: 316 */       localEJBContext = this._comp.getPooledInstance();
/*  366: 317 */       if (localEJBContext == null) {
/*  367: 319 */         localEJBContext = _create();
/*  368:     */       }
/*  369: 321 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  370: 322 */       if (localEJBContext.debug) {
/*  371: 324 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomersInfo"));
/*  372:     */       }
/*  373: 326 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX151BPWServicesBean
/*  374: 327 */         .getCustomersInfo(
/*  375: 328 */         StringSeqHelper.clone(paramArrayOfString));
/*  376:     */       
/*  377: 330 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  378: 331 */       localEJBContext.returnToPool();
/*  379: 332 */       return arrayOfCustomerInfo2;
/*  380:     */     }
/*  381:     */     catch (Exception localException)
/*  382:     */     {
/*  383: 336 */       if (localEJBContext != null) {
/*  384: 338 */         localEJBContext.throwRemote(localException);
/*  385:     */       }
/*  386: 340 */       throw new EJBException(localException);
/*  387:     */     }
/*  388:     */     finally
/*  389:     */     {
/*  390: 344 */       this._comp.setCurrent(localJavaComponent);
/*  391:     */     }
/*  392:     */   }
/*  393:     */   
/*  394:     */   public CustomerInfo[] getCustomerByType(String paramString)
/*  395:     */     throws RemoteException
/*  396:     */   {
/*  397: 352 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  398: 353 */     EJBContext localEJBContext = null;
/*  399:     */     try
/*  400:     */     {
/*  401: 356 */       localEJBContext = this._comp.getPooledInstance();
/*  402: 357 */       if (localEJBContext == null) {
/*  403: 359 */         localEJBContext = _create();
/*  404:     */       }
/*  405: 361 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  406: 362 */       if (localEJBContext.debug) {
/*  407: 364 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByType"));
/*  408:     */       }
/*  409: 366 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX151BPWServicesBean
/*  410: 367 */         .getCustomerByType(
/*  411: 368 */         paramString);
/*  412:     */       
/*  413: 370 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  414: 371 */       localEJBContext.returnToPool();
/*  415: 372 */       return arrayOfCustomerInfo2;
/*  416:     */     }
/*  417:     */     catch (Exception localException)
/*  418:     */     {
/*  419: 376 */       if (localEJBContext != null) {
/*  420: 378 */         localEJBContext.throwRemote(localException);
/*  421:     */       }
/*  422: 380 */       throw new EJBException(localException);
/*  423:     */     }
/*  424:     */     finally
/*  425:     */     {
/*  426: 384 */       this._comp.setCurrent(localJavaComponent);
/*  427:     */     }
/*  428:     */   }
/*  429:     */   
/*  430:     */   public CustomerInfo[] getCustomerByFI(String paramString)
/*  431:     */     throws RemoteException
/*  432:     */   {
/*  433: 392 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  434: 393 */     EJBContext localEJBContext = null;
/*  435:     */     try
/*  436:     */     {
/*  437: 396 */       localEJBContext = this._comp.getPooledInstance();
/*  438: 397 */       if (localEJBContext == null) {
/*  439: 399 */         localEJBContext = _create();
/*  440:     */       }
/*  441: 401 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  442: 402 */       if (localEJBContext.debug) {
/*  443: 404 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByFI"));
/*  444:     */       }
/*  445: 406 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX151BPWServicesBean
/*  446: 407 */         .getCustomerByFI(
/*  447: 408 */         paramString);
/*  448:     */       
/*  449: 410 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  450: 411 */       localEJBContext.returnToPool();
/*  451: 412 */       return arrayOfCustomerInfo2;
/*  452:     */     }
/*  453:     */     catch (Exception localException)
/*  454:     */     {
/*  455: 416 */       if (localEJBContext != null) {
/*  456: 418 */         localEJBContext.throwRemote(localException);
/*  457:     */       }
/*  458: 420 */       throw new EJBException(localException);
/*  459:     */     }
/*  460:     */     finally
/*  461:     */     {
/*  462: 424 */       this._comp.setCurrent(localJavaComponent);
/*  463:     */     }
/*  464:     */   }
/*  465:     */   
/*  466:     */   public CustomerInfo[] getCustomerByCategory(String paramString)
/*  467:     */     throws RemoteException
/*  468:     */   {
/*  469: 432 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  470: 433 */     EJBContext localEJBContext = null;
/*  471:     */     try
/*  472:     */     {
/*  473: 436 */       localEJBContext = this._comp.getPooledInstance();
/*  474: 437 */       if (localEJBContext == null) {
/*  475: 439 */         localEJBContext = _create();
/*  476:     */       }
/*  477: 441 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  478: 442 */       if (localEJBContext.debug) {
/*  479: 444 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByCategory"));
/*  480:     */       }
/*  481: 446 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX151BPWServicesBean
/*  482: 447 */         .getCustomerByCategory(
/*  483: 448 */         paramString);
/*  484:     */       
/*  485: 450 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  486: 451 */       localEJBContext.returnToPool();
/*  487: 452 */       return arrayOfCustomerInfo2;
/*  488:     */     }
/*  489:     */     catch (Exception localException)
/*  490:     */     {
/*  491: 456 */       if (localEJBContext != null) {
/*  492: 458 */         localEJBContext.throwRemote(localException);
/*  493:     */       }
/*  494: 460 */       throw new EJBException(localException);
/*  495:     */     }
/*  496:     */     finally
/*  497:     */     {
/*  498: 464 */       this._comp.setCurrent(localJavaComponent);
/*  499:     */     }
/*  500:     */   }
/*  501:     */   
/*  502:     */   public CustomerInfo[] getCustomerByGroup(String paramString)
/*  503:     */     throws RemoteException
/*  504:     */   {
/*  505: 472 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  506: 473 */     EJBContext localEJBContext = null;
/*  507:     */     try
/*  508:     */     {
/*  509: 476 */       localEJBContext = this._comp.getPooledInstance();
/*  510: 477 */       if (localEJBContext == null) {
/*  511: 479 */         localEJBContext = _create();
/*  512:     */       }
/*  513: 481 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  514: 482 */       if (localEJBContext.debug) {
/*  515: 484 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByGroup"));
/*  516:     */       }
/*  517: 486 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX151BPWServicesBean
/*  518: 487 */         .getCustomerByGroup(
/*  519: 488 */         paramString);
/*  520:     */       
/*  521: 490 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  522: 491 */       localEJBContext.returnToPool();
/*  523: 492 */       return arrayOfCustomerInfo2;
/*  524:     */     }
/*  525:     */     catch (Exception localException)
/*  526:     */     {
/*  527: 496 */       if (localEJBContext != null) {
/*  528: 498 */         localEJBContext.throwRemote(localException);
/*  529:     */       }
/*  530: 500 */       throw new EJBException(localException);
/*  531:     */     }
/*  532:     */     finally
/*  533:     */     {
/*  534: 504 */       this._comp.setCurrent(localJavaComponent);
/*  535:     */     }
/*  536:     */   }
/*  537:     */   
/*  538:     */   public CustomerInfo[] getCustomerByTypeAndFI(String paramString1, String paramString2)
/*  539:     */     throws RemoteException
/*  540:     */   {
/*  541: 513 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  542: 514 */     EJBContext localEJBContext = null;
/*  543:     */     try
/*  544:     */     {
/*  545: 517 */       localEJBContext = this._comp.getPooledInstance();
/*  546: 518 */       if (localEJBContext == null) {
/*  547: 520 */         localEJBContext = _create();
/*  548:     */       }
/*  549: 522 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  550: 523 */       if (localEJBContext.debug) {
/*  551: 525 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByTypeAndFI"));
/*  552:     */       }
/*  553: 527 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX151BPWServicesBean
/*  554: 528 */         .getCustomerByTypeAndFI(
/*  555: 529 */         paramString1, 
/*  556: 530 */         paramString2);
/*  557:     */       
/*  558: 532 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  559: 533 */       localEJBContext.returnToPool();
/*  560: 534 */       return arrayOfCustomerInfo2;
/*  561:     */     }
/*  562:     */     catch (Exception localException)
/*  563:     */     {
/*  564: 538 */       if (localEJBContext != null) {
/*  565: 540 */         localEJBContext.throwRemote(localException);
/*  566:     */       }
/*  567: 542 */       throw new EJBException(localException);
/*  568:     */     }
/*  569:     */     finally
/*  570:     */     {
/*  571: 546 */       this._comp.setCurrent(localJavaComponent);
/*  572:     */     }
/*  573:     */   }
/*  574:     */   
/*  575:     */   public CustomerInfo[] getCustomerByCategoryAndFI(String paramString1, String paramString2)
/*  576:     */     throws RemoteException
/*  577:     */   {
/*  578: 555 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  579: 556 */     EJBContext localEJBContext = null;
/*  580:     */     try
/*  581:     */     {
/*  582: 559 */       localEJBContext = this._comp.getPooledInstance();
/*  583: 560 */       if (localEJBContext == null) {
/*  584: 562 */         localEJBContext = _create();
/*  585:     */       }
/*  586: 564 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  587: 565 */       if (localEJBContext.debug) {
/*  588: 567 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByCategoryAndFI"));
/*  589:     */       }
/*  590: 569 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX151BPWServicesBean
/*  591: 570 */         .getCustomerByCategoryAndFI(
/*  592: 571 */         paramString1, 
/*  593: 572 */         paramString2);
/*  594:     */       
/*  595: 574 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  596: 575 */       localEJBContext.returnToPool();
/*  597: 576 */       return arrayOfCustomerInfo2;
/*  598:     */     }
/*  599:     */     catch (Exception localException)
/*  600:     */     {
/*  601: 580 */       if (localEJBContext != null) {
/*  602: 582 */         localEJBContext.throwRemote(localException);
/*  603:     */       }
/*  604: 584 */       throw new EJBException(localException);
/*  605:     */     }
/*  606:     */     finally
/*  607:     */     {
/*  608: 588 */       this._comp.setCurrent(localJavaComponent);
/*  609:     */     }
/*  610:     */   }
/*  611:     */   
/*  612:     */   public CustomerInfo[] getCustomerByGroupAndFI(String paramString1, String paramString2)
/*  613:     */     throws RemoteException
/*  614:     */   {
/*  615: 597 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  616: 598 */     EJBContext localEJBContext = null;
/*  617:     */     try
/*  618:     */     {
/*  619: 601 */       localEJBContext = this._comp.getPooledInstance();
/*  620: 602 */       if (localEJBContext == null) {
/*  621: 604 */         localEJBContext = _create();
/*  622:     */       }
/*  623: 606 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  624: 607 */       if (localEJBContext.debug) {
/*  625: 609 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerByGroupAndFI"));
/*  626:     */       }
/*  627: 611 */       CustomerInfo[] arrayOfCustomerInfo2 = localOFX151BPWServicesBean
/*  628: 612 */         .getCustomerByGroupAndFI(
/*  629: 613 */         paramString1, 
/*  630: 614 */         paramString2);
/*  631:     */       
/*  632: 616 */       arrayOfCustomerInfo2 = CustomerInfoSeqHelper.clone(arrayOfCustomerInfo2);
/*  633: 617 */       localEJBContext.returnToPool();
/*  634: 618 */       return arrayOfCustomerInfo2;
/*  635:     */     }
/*  636:     */     catch (Exception localException)
/*  637:     */     {
/*  638: 622 */       if (localEJBContext != null) {
/*  639: 624 */         localEJBContext.throwRemote(localException);
/*  640:     */       }
/*  641: 626 */       throw new EJBException(localException);
/*  642:     */     }
/*  643:     */     finally
/*  644:     */     {
/*  645: 630 */       this._comp.setCurrent(localJavaComponent);
/*  646:     */     }
/*  647:     */   }
/*  648:     */   
/*  649:     */   public PayeeInfo[] getLinkedPayees()
/*  650:     */     throws RemoteException
/*  651:     */   {
/*  652: 637 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  653: 638 */     EJBContext localEJBContext = null;
/*  654:     */     try
/*  655:     */     {
/*  656: 641 */       localEJBContext = this._comp.getPooledInstance();
/*  657: 642 */       if (localEJBContext == null) {
/*  658: 644 */         localEJBContext = _create();
/*  659:     */       }
/*  660: 646 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  661: 647 */       if (localEJBContext.debug) {
/*  662: 649 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getLinkedPayees"));
/*  663:     */       }
/*  664: 651 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX151BPWServicesBean
/*  665: 652 */         .getLinkedPayees();
/*  666:     */       
/*  667: 654 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/*  668: 655 */       localEJBContext.returnToPool();
/*  669: 656 */       return arrayOfPayeeInfo2;
/*  670:     */     }
/*  671:     */     catch (Exception localException)
/*  672:     */     {
/*  673: 660 */       if (localEJBContext != null) {
/*  674: 662 */         localEJBContext.throwRemote(localException);
/*  675:     */       }
/*  676: 664 */       throw new EJBException(localException);
/*  677:     */     }
/*  678:     */     finally
/*  679:     */     {
/*  680: 668 */       this._comp.setCurrent(localJavaComponent);
/*  681:     */     }
/*  682:     */   }
/*  683:     */   
/*  684:     */   public PayeeInfo[] getMostUsedPayees(int paramInt)
/*  685:     */     throws RemoteException
/*  686:     */   {
/*  687: 676 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  688: 677 */     EJBContext localEJBContext = null;
/*  689:     */     try
/*  690:     */     {
/*  691: 680 */       localEJBContext = this._comp.getPooledInstance();
/*  692: 681 */       if (localEJBContext == null) {
/*  693: 683 */         localEJBContext = _create();
/*  694:     */       }
/*  695: 685 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  696: 686 */       if (localEJBContext.debug) {
/*  697: 688 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getMostUsedPayees"));
/*  698:     */       }
/*  699: 690 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX151BPWServicesBean
/*  700: 691 */         .getMostUsedPayees(
/*  701: 692 */         paramInt);
/*  702:     */       
/*  703: 694 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/*  704: 695 */       localEJBContext.returnToPool();
/*  705: 696 */       return arrayOfPayeeInfo2;
/*  706:     */     }
/*  707:     */     catch (Exception localException)
/*  708:     */     {
/*  709: 700 */       if (localEJBContext != null) {
/*  710: 702 */         localEJBContext.throwRemote(localException);
/*  711:     */       }
/*  712: 704 */       throw new EJBException(localException);
/*  713:     */     }
/*  714:     */     finally
/*  715:     */     {
/*  716: 708 */       this._comp.setCurrent(localJavaComponent);
/*  717:     */     }
/*  718:     */   }
/*  719:     */   
/*  720:     */   public PayeeInfo[] getPreferredPayees(String paramString)
/*  721:     */     throws RemoteException
/*  722:     */   {
/*  723: 716 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  724: 717 */     EJBContext localEJBContext = null;
/*  725:     */     try
/*  726:     */     {
/*  727: 720 */       localEJBContext = this._comp.getPooledInstance();
/*  728: 721 */       if (localEJBContext == null) {
/*  729: 723 */         localEJBContext = _create();
/*  730:     */       }
/*  731: 725 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  732: 726 */       if (localEJBContext.debug) {
/*  733: 728 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPreferredPayees"));
/*  734:     */       }
/*  735: 730 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX151BPWServicesBean
/*  736: 731 */         .getPreferredPayees(
/*  737: 732 */         paramString);
/*  738:     */       
/*  739: 734 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/*  740: 735 */       localEJBContext.returnToPool();
/*  741: 736 */       return arrayOfPayeeInfo2;
/*  742:     */     }
/*  743:     */     catch (Exception localException)
/*  744:     */     {
/*  745: 740 */       if (localEJBContext != null) {
/*  746: 742 */         localEJBContext.throwRemote(localException);
/*  747:     */       }
/*  748: 744 */       throw new EJBException(localException);
/*  749:     */     }
/*  750:     */     finally
/*  751:     */     {
/*  752: 748 */       this._comp.setCurrent(localJavaComponent);
/*  753:     */     }
/*  754:     */   }
/*  755:     */   
/*  756:     */   public TypePmtSyncRsV1[] getPendingPmtsByCustomerID(String paramString, int paramInt)
/*  757:     */     throws RemoteException
/*  758:     */   {
/*  759: 757 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  760: 758 */     EJBContext localEJBContext = null;
/*  761:     */     try
/*  762:     */     {
/*  763: 761 */       localEJBContext = this._comp.getPooledInstance();
/*  764: 762 */       if (localEJBContext == null) {
/*  765: 764 */         localEJBContext = _create();
/*  766:     */       }
/*  767: 766 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  768: 767 */       if (localEJBContext.debug) {
/*  769: 769 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPendingPmtsByCustomerID"));
/*  770:     */       }
/*  771: 771 */       TypePmtSyncRsV1[] arrayOfTypePmtSyncRsV12 = localOFX151BPWServicesBean
/*  772: 772 */         .getPendingPmtsByCustomerID(
/*  773: 773 */         paramString, 
/*  774: 774 */         paramInt);
/*  775:     */       
/*  776: 776 */       arrayOfTypePmtSyncRsV12 = TypePmtSyncRsV1SeqHelper.clone(arrayOfTypePmtSyncRsV12);
/*  777: 777 */       localEJBContext.returnToPool();
/*  778: 778 */       return arrayOfTypePmtSyncRsV12;
/*  779:     */     }
/*  780:     */     catch (Exception localException)
/*  781:     */     {
/*  782: 782 */       if (localEJBContext != null) {
/*  783: 784 */         localEJBContext.throwRemote(localException);
/*  784:     */       }
/*  785: 786 */       throw new EJBException(localException);
/*  786:     */     }
/*  787:     */     finally
/*  788:     */     {
/*  789: 790 */       this._comp.setCurrent(localJavaComponent);
/*  790:     */     }
/*  791:     */   }
/*  792:     */   
/*  793:     */   public TypePmtSyncRsV1[] getPendingPmtsAndHistoryByCustomerID(String paramString, int paramInt1, int paramInt2)
/*  794:     */     throws RemoteException
/*  795:     */   {
/*  796: 800 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  797: 801 */     EJBContext localEJBContext = null;
/*  798:     */     try
/*  799:     */     {
/*  800: 804 */       localEJBContext = this._comp.getPooledInstance();
/*  801: 805 */       if (localEJBContext == null) {
/*  802: 807 */         localEJBContext = _create();
/*  803:     */       }
/*  804: 809 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  805: 810 */       if (localEJBContext.debug) {
/*  806: 812 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPendingPmtsAndHistoryByCustomerID"));
/*  807:     */       }
/*  808: 814 */       TypePmtSyncRsV1[] arrayOfTypePmtSyncRsV12 = localOFX151BPWServicesBean
/*  809: 815 */         .getPendingPmtsAndHistoryByCustomerID(
/*  810: 816 */         paramString, 
/*  811: 817 */         paramInt1, 
/*  812: 818 */         paramInt2);
/*  813:     */       
/*  814: 820 */       arrayOfTypePmtSyncRsV12 = TypePmtSyncRsV1SeqHelper.clone(arrayOfTypePmtSyncRsV12);
/*  815: 821 */       localEJBContext.returnToPool();
/*  816: 822 */       return arrayOfTypePmtSyncRsV12;
/*  817:     */     }
/*  818:     */     catch (Exception localException)
/*  819:     */     {
/*  820: 826 */       if (localEJBContext != null) {
/*  821: 828 */         localEJBContext.throwRemote(localException);
/*  822:     */       }
/*  823: 830 */       throw new EJBException(localException);
/*  824:     */     }
/*  825:     */     finally
/*  826:     */     {
/*  827: 834 */       this._comp.setCurrent(localJavaComponent);
/*  828:     */     }
/*  829:     */   }
/*  830:     */   
/*  831:     */   public TypeIntraSyncRsV1[] getPendingIntrasByCustomerID(String paramString, int paramInt)
/*  832:     */     throws RemoteException
/*  833:     */   {
/*  834: 843 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  835: 844 */     EJBContext localEJBContext = null;
/*  836:     */     try
/*  837:     */     {
/*  838: 847 */       localEJBContext = this._comp.getPooledInstance();
/*  839: 848 */       if (localEJBContext == null) {
/*  840: 850 */         localEJBContext = _create();
/*  841:     */       }
/*  842: 852 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  843: 853 */       if (localEJBContext.debug) {
/*  844: 855 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPendingIntrasByCustomerID"));
/*  845:     */       }
/*  846: 857 */       TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV12 = localOFX151BPWServicesBean
/*  847: 858 */         .getPendingIntrasByCustomerID(
/*  848: 859 */         paramString, 
/*  849: 860 */         paramInt);
/*  850:     */       
/*  851: 862 */       arrayOfTypeIntraSyncRsV12 = TypeIntraSyncRsV1SeqHelper.clone(arrayOfTypeIntraSyncRsV12);
/*  852: 863 */       localEJBContext.returnToPool();
/*  853: 864 */       return arrayOfTypeIntraSyncRsV12;
/*  854:     */     }
/*  855:     */     catch (Exception localException)
/*  856:     */     {
/*  857: 868 */       if (localEJBContext != null) {
/*  858: 870 */         localEJBContext.throwRemote(localException);
/*  859:     */       }
/*  860: 872 */       throw new EJBException(localException);
/*  861:     */     }
/*  862:     */     finally
/*  863:     */     {
/*  864: 876 */       this._comp.setCurrent(localJavaComponent);
/*  865:     */     }
/*  866:     */   }
/*  867:     */   
/*  868:     */   public TypeIntraSyncRsV1[] getPendingIntrasAndHistoryByCustomerID(String paramString, int paramInt1, int paramInt2)
/*  869:     */     throws RemoteException
/*  870:     */   {
/*  871: 886 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  872: 887 */     EJBContext localEJBContext = null;
/*  873:     */     try
/*  874:     */     {
/*  875: 890 */       localEJBContext = this._comp.getPooledInstance();
/*  876: 891 */       if (localEJBContext == null) {
/*  877: 893 */         localEJBContext = _create();
/*  878:     */       }
/*  879: 895 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  880: 896 */       if (localEJBContext.debug) {
/*  881: 898 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPendingIntrasAndHistoryByCustomerID"));
/*  882:     */       }
/*  883: 900 */       TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV12 = localOFX151BPWServicesBean
/*  884: 901 */         .getPendingIntrasAndHistoryByCustomerID(
/*  885: 902 */         paramString, 
/*  886: 903 */         paramInt1, 
/*  887: 904 */         paramInt2);
/*  888:     */       
/*  889: 906 */       arrayOfTypeIntraSyncRsV12 = TypeIntraSyncRsV1SeqHelper.clone(arrayOfTypeIntraSyncRsV12);
/*  890: 907 */       localEJBContext.returnToPool();
/*  891: 908 */       return arrayOfTypeIntraSyncRsV12;
/*  892:     */     }
/*  893:     */     catch (Exception localException)
/*  894:     */     {
/*  895: 912 */       if (localEJBContext != null) {
/*  896: 914 */         localEJBContext.throwRemote(localException);
/*  897:     */       }
/*  898: 916 */       throw new EJBException(localException);
/*  899:     */     }
/*  900:     */     finally
/*  901:     */     {
/*  902: 920 */       this._comp.setCurrent(localJavaComponent);
/*  903:     */     }
/*  904:     */   }
/*  905:     */   
/*  906:     */   public TypePmtSyncRsV1 getPendingPmts(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
/*  907:     */     throws RemoteException
/*  908:     */   {
/*  909: 930 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  910: 931 */     EJBContext localEJBContext = null;
/*  911:     */     try
/*  912:     */     {
/*  913: 934 */       localEJBContext = this._comp.getPooledInstance();
/*  914: 935 */       if (localEJBContext == null) {
/*  915: 937 */         localEJBContext = _create();
/*  916:     */       }
/*  917: 939 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  918: 940 */       if (localEJBContext.debug) {
/*  919: 942 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPendingPmts"));
/*  920:     */       }
/*  921: 944 */       TypePmtSyncRsV1 localTypePmtSyncRsV12 = localOFX151BPWServicesBean
/*  922: 945 */         .getPendingPmts(
/*  923: 946 */         TypePmtSyncRqV1Helper.clone(paramTypePmtSyncRqV1), 
/*  924: 947 */         TypeUserDataHelper.clone(paramTypeUserData), 
/*  925: 948 */         paramInt);
/*  926:     */       
/*  927: 950 */       localTypePmtSyncRsV12 = TypePmtSyncRsV1Helper.clone(localTypePmtSyncRsV12);
/*  928: 951 */       localEJBContext.returnToPool();
/*  929: 952 */       return localTypePmtSyncRsV12;
/*  930:     */     }
/*  931:     */     catch (Exception localException)
/*  932:     */     {
/*  933: 956 */       if (localEJBContext != null) {
/*  934: 958 */         localEJBContext.throwRemote(localException);
/*  935:     */       }
/*  936: 960 */       throw new EJBException(localException);
/*  937:     */     }
/*  938:     */     finally
/*  939:     */     {
/*  940: 964 */       this._comp.setCurrent(localJavaComponent);
/*  941:     */     }
/*  942:     */   }
/*  943:     */   
/*  944:     */   public TypeIntraSyncRsV1 getPendingIntras(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
/*  945:     */     throws RemoteException
/*  946:     */   {
/*  947: 974 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  948: 975 */     EJBContext localEJBContext = null;
/*  949:     */     try
/*  950:     */     {
/*  951: 978 */       localEJBContext = this._comp.getPooledInstance();
/*  952: 979 */       if (localEJBContext == null) {
/*  953: 981 */         localEJBContext = _create();
/*  954:     */       }
/*  955: 983 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  956: 984 */       if (localEJBContext.debug) {
/*  957: 986 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPendingIntras"));
/*  958:     */       }
/*  959: 988 */       TypeIntraSyncRsV1 localTypeIntraSyncRsV12 = localOFX151BPWServicesBean
/*  960: 989 */         .getPendingIntras(
/*  961: 990 */         TypeIntraSyncRqV1Helper.clone(paramTypeIntraSyncRqV1), 
/*  962: 991 */         TypeUserDataHelper.clone(paramTypeUserData), 
/*  963: 992 */         paramInt);
/*  964:     */       
/*  965: 994 */       localTypeIntraSyncRsV12 = TypeIntraSyncRsV1Helper.clone(localTypeIntraSyncRsV12);
/*  966: 995 */       localEJBContext.returnToPool();
/*  967: 996 */       return localTypeIntraSyncRsV12;
/*  968:     */     }
/*  969:     */     catch (Exception localException)
/*  970:     */     {
/*  971:1000 */       if (localEJBContext != null) {
/*  972:1002 */         localEJBContext.throwRemote(localException);
/*  973:     */       }
/*  974:1004 */       throw new EJBException(localException);
/*  975:     */     }
/*  976:     */     finally
/*  977:     */     {
/*  978:1008 */       this._comp.setCurrent(localJavaComponent);
/*  979:     */     }
/*  980:     */   }
/*  981:     */   
/*  982:     */   public String getPmtStatus(String paramString)
/*  983:     */     throws RemoteException
/*  984:     */   {
/*  985:1016 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/*  986:1017 */     EJBContext localEJBContext = null;
/*  987:     */     try
/*  988:     */     {
/*  989:1020 */       localEJBContext = this._comp.getPooledInstance();
/*  990:1021 */       if (localEJBContext == null) {
/*  991:1023 */         localEJBContext = _create();
/*  992:     */       }
/*  993:1025 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/*  994:1026 */       if (localEJBContext.debug) {
/*  995:1028 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPmtStatus"));
/*  996:     */       }
/*  997:1030 */       String str2 = localOFX151BPWServicesBean
/*  998:1031 */         .getPmtStatus(
/*  999:1032 */         paramString);
/* 1000:     */       
/* 1001:1034 */       localEJBContext.returnToPool();
/* 1002:1035 */       return str2;
/* 1003:     */     }
/* 1004:     */     catch (Exception localException)
/* 1005:     */     {
/* 1006:1039 */       if (localEJBContext != null) {
/* 1007:1041 */         localEJBContext.throwRemote(localException);
/* 1008:     */       }
/* 1009:1043 */       throw new EJBException(localException);
/* 1010:     */     }
/* 1011:     */     finally
/* 1012:     */     {
/* 1013:1047 */       this._comp.setCurrent(localJavaComponent);
/* 1014:     */     }
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   public boolean checkPayeeEditMask(String paramString1, String paramString2)
/* 1018:     */     throws RemoteException
/* 1019:     */   {
/* 1020:1056 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1021:1057 */     EJBContext localEJBContext = null;
/* 1022:     */     try
/* 1023:     */     {
/* 1024:1060 */       localEJBContext = this._comp.getPooledInstance();
/* 1025:1061 */       if (localEJBContext == null) {
/* 1026:1063 */         localEJBContext = _create();
/* 1027:     */       }
/* 1028:1065 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1029:1066 */       if (localEJBContext.debug) {
/* 1030:1068 */         localEJBContext.logger.debug(localEJBContext.debugMsg("checkPayeeEditMask"));
/* 1031:     */       }
/* 1032:1070 */       boolean bool2 = localOFX151BPWServicesBean
/* 1033:1071 */         .checkPayeeEditMask(
/* 1034:1072 */         paramString1, 
/* 1035:1073 */         paramString2);
/* 1036:     */       
/* 1037:1075 */       localEJBContext.returnToPool();
/* 1038:1076 */       return bool2;
/* 1039:     */     }
/* 1040:     */     catch (Exception localException)
/* 1041:     */     {
/* 1042:1080 */       if (localEJBContext != null) {
/* 1043:1082 */         localEJBContext.throwRemote(localException);
/* 1044:     */       }
/* 1045:1084 */       throw new EJBException(localException);
/* 1046:     */     }
/* 1047:     */     finally
/* 1048:     */     {
/* 1049:1088 */       this._comp.setCurrent(localJavaComponent);
/* 1050:     */     }
/* 1051:     */   }
/* 1052:     */   
/* 1053:     */   public void processIntraTrnRslt(IntraTrnRslt[] paramArrayOfIntraTrnRslt)
/* 1054:     */     throws RemoteException
/* 1055:     */   {
/* 1056:1096 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1057:1097 */     EJBContext localEJBContext = null;
/* 1058:     */     try
/* 1059:     */     {
/* 1060:1100 */       localEJBContext = this._comp.getPooledInstance();
/* 1061:1101 */       if (localEJBContext == null) {
/* 1062:1103 */         localEJBContext = _create();
/* 1063:     */       }
/* 1064:1105 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1065:1106 */       if (localEJBContext.debug) {
/* 1066:1108 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processIntraTrnRslt"));
/* 1067:     */       }
/* 1068:1111 */       localOFX151BPWServicesBean.processIntraTrnRslt(
/* 1069:1112 */         IntraTrnRsltSeqHelper.clone(paramArrayOfIntraTrnRslt));
/* 1070:     */       
/* 1071:1114 */       localEJBContext.returnToPool();
/* 1072:     */     }
/* 1073:     */     catch (Exception localException)
/* 1074:     */     {
/* 1075:1118 */       if (localEJBContext != null) {
/* 1076:1120 */         localEJBContext.throwRemote(localException);
/* 1077:     */       }
/* 1078:1122 */       throw new EJBException(localException);
/* 1079:     */     }
/* 1080:     */     finally
/* 1081:     */     {
/* 1082:1126 */       this._comp.setCurrent(localJavaComponent);
/* 1083:     */     }
/* 1084:     */   }
/* 1085:     */   
/* 1086:     */   public void processPmtTrnRslt(PmtTrnRslt[] paramArrayOfPmtTrnRslt)
/* 1087:     */     throws RemoteException
/* 1088:     */   {
/* 1089:1134 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1090:1135 */     EJBContext localEJBContext = null;
/* 1091:     */     try
/* 1092:     */     {
/* 1093:1138 */       localEJBContext = this._comp.getPooledInstance();
/* 1094:1139 */       if (localEJBContext == null) {
/* 1095:1141 */         localEJBContext = _create();
/* 1096:     */       }
/* 1097:1143 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1098:1144 */       if (localEJBContext.debug) {
/* 1099:1146 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPmtTrnRslt"));
/* 1100:     */       }
/* 1101:1149 */       localOFX151BPWServicesBean.processPmtTrnRslt(
/* 1102:1150 */         PmtTrnRsltSeqHelper.clone(paramArrayOfPmtTrnRslt));
/* 1103:     */       
/* 1104:1152 */       localEJBContext.returnToPool();
/* 1105:     */     }
/* 1106:     */     catch (Exception localException)
/* 1107:     */     {
/* 1108:1156 */       if (localEJBContext != null) {
/* 1109:1158 */         localEJBContext.throwRemote(localException);
/* 1110:     */       }
/* 1111:1160 */       throw new EJBException(localException);
/* 1112:     */     }
/* 1113:     */     finally
/* 1114:     */     {
/* 1115:1164 */       this._comp.setCurrent(localJavaComponent);
/* 1116:     */     }
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public void processCustPayeeRslt(CustPayeeRslt[] paramArrayOfCustPayeeRslt)
/* 1120:     */     throws RemoteException
/* 1121:     */   {
/* 1122:1172 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1123:1173 */     EJBContext localEJBContext = null;
/* 1124:     */     try
/* 1125:     */     {
/* 1126:1176 */       localEJBContext = this._comp.getPooledInstance();
/* 1127:1177 */       if (localEJBContext == null) {
/* 1128:1179 */         localEJBContext = _create();
/* 1129:     */       }
/* 1130:1181 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1131:1182 */       if (localEJBContext.debug) {
/* 1132:1184 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processCustPayeeRslt"));
/* 1133:     */       }
/* 1134:1187 */       localOFX151BPWServicesBean.processCustPayeeRslt(
/* 1135:1188 */         CustPayeeRsltSeqHelper.clone(paramArrayOfCustPayeeRslt));
/* 1136:     */       
/* 1137:1190 */       localEJBContext.returnToPool();
/* 1138:     */     }
/* 1139:     */     catch (Exception localException)
/* 1140:     */     {
/* 1141:1194 */       if (localEJBContext != null) {
/* 1142:1196 */         localEJBContext.throwRemote(localException);
/* 1143:     */       }
/* 1144:1198 */       throw new EJBException(localException);
/* 1145:     */     }
/* 1146:     */     finally
/* 1147:     */     {
/* 1148:1202 */       this._comp.setCurrent(localJavaComponent);
/* 1149:     */     }
/* 1150:     */   }
/* 1151:     */   
/* 1152:     */   public void processFundAllocRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
/* 1153:     */     throws RemoteException
/* 1154:     */   {
/* 1155:1210 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1156:1211 */     EJBContext localEJBContext = null;
/* 1157:     */     try
/* 1158:     */     {
/* 1159:1214 */       localEJBContext = this._comp.getPooledInstance();
/* 1160:1215 */       if (localEJBContext == null) {
/* 1161:1217 */         localEJBContext = _create();
/* 1162:     */       }
/* 1163:1219 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1164:1220 */       if (localEJBContext.debug) {
/* 1165:1222 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processFundAllocRslt"));
/* 1166:     */       }
/* 1167:1225 */       localOFX151BPWServicesBean.processFundAllocRslt(
/* 1168:1226 */         FundsAllocRsltSeqHelper.clone(paramArrayOfFundsAllocRslt));
/* 1169:     */       
/* 1170:1228 */       localEJBContext.returnToPool();
/* 1171:     */     }
/* 1172:     */     catch (Exception localException)
/* 1173:     */     {
/* 1174:1232 */       if (localEJBContext != null) {
/* 1175:1234 */         localEJBContext.throwRemote(localException);
/* 1176:     */       }
/* 1177:1236 */       throw new EJBException(localException);
/* 1178:     */     }
/* 1179:     */     finally
/* 1180:     */     {
/* 1181:1240 */       this._comp.setCurrent(localJavaComponent);
/* 1182:     */     }
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   public void processFundRevertRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
/* 1186:     */     throws RemoteException
/* 1187:     */   {
/* 1188:1248 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1189:1249 */     EJBContext localEJBContext = null;
/* 1190:     */     try
/* 1191:     */     {
/* 1192:1252 */       localEJBContext = this._comp.getPooledInstance();
/* 1193:1253 */       if (localEJBContext == null) {
/* 1194:1255 */         localEJBContext = _create();
/* 1195:     */       }
/* 1196:1257 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1197:1258 */       if (localEJBContext.debug) {
/* 1198:1260 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processFundRevertRslt"));
/* 1199:     */       }
/* 1200:1263 */       localOFX151BPWServicesBean.processFundRevertRslt(
/* 1201:1264 */         FundsAllocRsltSeqHelper.clone(paramArrayOfFundsAllocRslt));
/* 1202:     */       
/* 1203:1266 */       localEJBContext.returnToPool();
/* 1204:     */     }
/* 1205:     */     catch (Exception localException)
/* 1206:     */     {
/* 1207:1270 */       if (localEJBContext != null) {
/* 1208:1272 */         localEJBContext.throwRemote(localException);
/* 1209:     */       }
/* 1210:1274 */       throw new EJBException(localException);
/* 1211:     */     }
/* 1212:     */     finally
/* 1213:     */     {
/* 1214:1278 */       this._comp.setCurrent(localJavaComponent);
/* 1215:     */     }
/* 1216:     */   }
/* 1217:     */   
/* 1218:     */   public void processPayeeRslt(PayeeRslt[] paramArrayOfPayeeRslt)
/* 1219:     */     throws RemoteException
/* 1220:     */   {
/* 1221:1286 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1222:1287 */     EJBContext localEJBContext = null;
/* 1223:     */     try
/* 1224:     */     {
/* 1225:1290 */       localEJBContext = this._comp.getPooledInstance();
/* 1226:1291 */       if (localEJBContext == null) {
/* 1227:1293 */         localEJBContext = _create();
/* 1228:     */       }
/* 1229:1295 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1230:1296 */       if (localEJBContext.debug) {
/* 1231:1298 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPayeeRslt"));
/* 1232:     */       }
/* 1233:1301 */       localOFX151BPWServicesBean.processPayeeRslt(
/* 1234:1302 */         PayeeRsltSeqHelper.clone(paramArrayOfPayeeRslt));
/* 1235:     */       
/* 1236:1304 */       localEJBContext.returnToPool();
/* 1237:     */     }
/* 1238:     */     catch (Exception localException)
/* 1239:     */     {
/* 1240:1308 */       if (localEJBContext != null) {
/* 1241:1310 */         localEJBContext.throwRemote(localException);
/* 1242:     */       }
/* 1243:1312 */       throw new EJBException(localException);
/* 1244:     */     }
/* 1245:     */     finally
/* 1246:     */     {
/* 1247:1316 */       this._comp.setCurrent(localJavaComponent);
/* 1248:     */     }
/* 1249:     */   }
/* 1250:     */   
/* 1251:     */   public String addPayeeFromBackend(PayeeInfo paramPayeeInfo)
/* 1252:     */     throws RemoteException
/* 1253:     */   {
/* 1254:1324 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1255:1325 */     EJBContext localEJBContext = null;
/* 1256:     */     try
/* 1257:     */     {
/* 1258:1328 */       localEJBContext = this._comp.getPooledInstance();
/* 1259:1329 */       if (localEJBContext == null) {
/* 1260:1331 */         localEJBContext = _create();
/* 1261:     */       }
/* 1262:1333 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1263:1334 */       if (localEJBContext.debug) {
/* 1264:1336 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addPayeeFromBackend"));
/* 1265:     */       }
/* 1266:1338 */       String str2 = localOFX151BPWServicesBean
/* 1267:1339 */         .addPayeeFromBackend(
/* 1268:1340 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo));
/* 1269:     */       
/* 1270:1342 */       localEJBContext.returnToPool();
/* 1271:1343 */       return str2;
/* 1272:     */     }
/* 1273:     */     catch (Exception localException)
/* 1274:     */     {
/* 1275:1347 */       if (localEJBContext != null) {
/* 1276:1349 */         localEJBContext.throwRemote(localException);
/* 1277:     */       }
/* 1278:1351 */       throw new EJBException(localException);
/* 1279:     */     }
/* 1280:     */     finally
/* 1281:     */     {
/* 1282:1355 */       this._comp.setCurrent(localJavaComponent);
/* 1283:     */     }
/* 1284:     */   }
/* 1285:     */   
/* 1286:     */   public PayeeInfo[] updatePayeeFromBackend(PayeeInfo paramPayeeInfo)
/* 1287:     */     throws RemoteException
/* 1288:     */   {
/* 1289:1363 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1290:1364 */     EJBContext localEJBContext = null;
/* 1291:     */     try
/* 1292:     */     {
/* 1293:1367 */       localEJBContext = this._comp.getPooledInstance();
/* 1294:1368 */       if (localEJBContext == null) {
/* 1295:1370 */         localEJBContext = _create();
/* 1296:     */       }
/* 1297:1372 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1298:1373 */       if (localEJBContext.debug) {
/* 1299:1375 */         localEJBContext.logger.debug(localEJBContext.debugMsg("updatePayeeFromBackend"));
/* 1300:     */       }
/* 1301:1377 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX151BPWServicesBean
/* 1302:1378 */         .updatePayeeFromBackend(
/* 1303:1379 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo));
/* 1304:     */       
/* 1305:1381 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 1306:1382 */       localEJBContext.returnToPool();
/* 1307:1383 */       return arrayOfPayeeInfo2;
/* 1308:     */     }
/* 1309:     */     catch (Exception localException)
/* 1310:     */     {
/* 1311:1387 */       if (localEJBContext != null) {
/* 1312:1389 */         localEJBContext.throwRemote(localException);
/* 1313:     */       }
/* 1314:1391 */       throw new EJBException(localException);
/* 1315:     */     }
/* 1316:     */     finally
/* 1317:     */     {
/* 1318:1395 */       this._comp.setCurrent(localJavaComponent);
/* 1319:     */     }
/* 1320:     */   }
/* 1321:     */   
/* 1322:     */   public void addPayeeRouteInfo(PayeeRouteInfo paramPayeeRouteInfo)
/* 1323:     */     throws RemoteException
/* 1324:     */   {
/* 1325:1403 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1326:1404 */     EJBContext localEJBContext = null;
/* 1327:     */     try
/* 1328:     */     {
/* 1329:1407 */       localEJBContext = this._comp.getPooledInstance();
/* 1330:1408 */       if (localEJBContext == null) {
/* 1331:1410 */         localEJBContext = _create();
/* 1332:     */       }
/* 1333:1412 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1334:1413 */       if (localEJBContext.debug) {
/* 1335:1415 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addPayeeRouteInfo"));
/* 1336:     */       }
/* 1337:1418 */       localOFX151BPWServicesBean.addPayeeRouteInfo(
/* 1338:1419 */         (PayeeRouteInfo)EJBContext.clone(paramPayeeRouteInfo));
/* 1339:     */       
/* 1340:1421 */       localEJBContext.returnToPool();
/* 1341:     */     }
/* 1342:     */     catch (Exception localException)
/* 1343:     */     {
/* 1344:1425 */       if (localEJBContext != null) {
/* 1345:1427 */         localEJBContext.throwRemote(localException);
/* 1346:     */       }
/* 1347:1429 */       throw new EJBException(localException);
/* 1348:     */     }
/* 1349:     */     finally
/* 1350:     */     {
/* 1351:1433 */       this._comp.setCurrent(localJavaComponent);
/* 1352:     */     }
/* 1353:     */   }
/* 1354:     */   
/* 1355:     */   public TypeIntraSyncRsV1 processIntraSyncRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData)
/* 1356:     */     throws RemoteException
/* 1357:     */   {
/* 1358:1442 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1359:1443 */     EJBContext localEJBContext = null;
/* 1360:     */     try
/* 1361:     */     {
/* 1362:1446 */       localEJBContext = this._comp.getPooledInstance();
/* 1363:1447 */       if (localEJBContext == null) {
/* 1364:1449 */         localEJBContext = _create();
/* 1365:     */       }
/* 1366:1451 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1367:1452 */       if (localEJBContext.debug) {
/* 1368:1454 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processIntraSyncRqV1"));
/* 1369:     */       }
/* 1370:1456 */       TypeIntraSyncRsV1 localTypeIntraSyncRsV12 = localOFX151BPWServicesBean
/* 1371:1457 */         .processIntraSyncRqV1(
/* 1372:1458 */         TypeIntraSyncRqV1Helper.clone(paramTypeIntraSyncRqV1), 
/* 1373:1459 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1374:     */       
/* 1375:1461 */       localTypeIntraSyncRsV12 = TypeIntraSyncRsV1Helper.clone(localTypeIntraSyncRsV12);
/* 1376:1462 */       localEJBContext.returnToPool();
/* 1377:1463 */       return localTypeIntraSyncRsV12;
/* 1378:     */     }
/* 1379:     */     catch (Exception localException)
/* 1380:     */     {
/* 1381:1467 */       if (localEJBContext != null) {
/* 1382:1469 */         localEJBContext.throwRemote(localException);
/* 1383:     */       }
/* 1384:1471 */       throw new EJBException(localException);
/* 1385:     */     }
/* 1386:     */     finally
/* 1387:     */     {
/* 1388:1475 */       this._comp.setCurrent(localJavaComponent);
/* 1389:     */     }
/* 1390:     */   }
/* 1391:     */   
/* 1392:     */   public TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData)
/* 1393:     */     throws RemoteException
/* 1394:     */   {
/* 1395:1484 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1396:1485 */     EJBContext localEJBContext = null;
/* 1397:     */     try
/* 1398:     */     {
/* 1399:1488 */       localEJBContext = this._comp.getPooledInstance();
/* 1400:1489 */       if (localEJBContext == null) {
/* 1401:1491 */         localEJBContext = _create();
/* 1402:     */       }
/* 1403:1493 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1404:1494 */       if (localEJBContext.debug) {
/* 1405:1496 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processIntraTrnRqV1"));
/* 1406:     */       }
/* 1407:1498 */       TypeIntraTrnRsV1 localTypeIntraTrnRsV12 = localOFX151BPWServicesBean
/* 1408:1499 */         .processIntraTrnRqV1(
/* 1409:1500 */         TypeIntraTrnRqV1Helper.clone(paramTypeIntraTrnRqV1), 
/* 1410:1501 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1411:     */       
/* 1412:1503 */       localTypeIntraTrnRsV12 = TypeIntraTrnRsV1Helper.clone(localTypeIntraTrnRsV12);
/* 1413:1504 */       localEJBContext.returnToPool();
/* 1414:1505 */       return localTypeIntraTrnRsV12;
/* 1415:     */     }
/* 1416:     */     catch (Exception localException)
/* 1417:     */     {
/* 1418:1509 */       if (localEJBContext != null) {
/* 1419:1511 */         localEJBContext.throwRemote(localException);
/* 1420:     */       }
/* 1421:1513 */       throw new EJBException(localException);
/* 1422:     */     }
/* 1423:     */     finally
/* 1424:     */     {
/* 1425:1517 */       this._comp.setCurrent(localJavaComponent);
/* 1426:     */     }
/* 1427:     */   }
/* 1428:     */   
/* 1429:     */   public TypePayeeSyncRsV1 processPayeeSyncRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData)
/* 1430:     */     throws RemoteException
/* 1431:     */   {
/* 1432:1526 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1433:1527 */     EJBContext localEJBContext = null;
/* 1434:     */     try
/* 1435:     */     {
/* 1436:1530 */       localEJBContext = this._comp.getPooledInstance();
/* 1437:1531 */       if (localEJBContext == null) {
/* 1438:1533 */         localEJBContext = _create();
/* 1439:     */       }
/* 1440:1535 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1441:1536 */       if (localEJBContext.debug) {
/* 1442:1538 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPayeeSyncRqV1"));
/* 1443:     */       }
/* 1444:1540 */       TypePayeeSyncRsV1 localTypePayeeSyncRsV12 = localOFX151BPWServicesBean
/* 1445:1541 */         .processPayeeSyncRqV1(
/* 1446:1542 */         TypePayeeSyncRqV1Helper.clone(paramTypePayeeSyncRqV1), 
/* 1447:1543 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1448:     */       
/* 1449:1545 */       localTypePayeeSyncRsV12 = TypePayeeSyncRsV1Helper.clone(localTypePayeeSyncRsV12);
/* 1450:1546 */       localEJBContext.returnToPool();
/* 1451:1547 */       return localTypePayeeSyncRsV12;
/* 1452:     */     }
/* 1453:     */     catch (Exception localException)
/* 1454:     */     {
/* 1455:1551 */       if (localEJBContext != null) {
/* 1456:1553 */         localEJBContext.throwRemote(localException);
/* 1457:     */       }
/* 1458:1555 */       throw new EJBException(localException);
/* 1459:     */     }
/* 1460:     */     finally
/* 1461:     */     {
/* 1462:1559 */       this._comp.setCurrent(localJavaComponent);
/* 1463:     */     }
/* 1464:     */   }
/* 1465:     */   
/* 1466:     */   public TypePayeeTrnRsV1 processPayeeTrnRqV1(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData)
/* 1467:     */     throws RemoteException
/* 1468:     */   {
/* 1469:1568 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1470:1569 */     EJBContext localEJBContext = null;
/* 1471:     */     try
/* 1472:     */     {
/* 1473:1572 */       localEJBContext = this._comp.getPooledInstance();
/* 1474:1573 */       if (localEJBContext == null) {
/* 1475:1575 */         localEJBContext = _create();
/* 1476:     */       }
/* 1477:1577 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1478:1578 */       if (localEJBContext.debug) {
/* 1479:1580 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPayeeTrnRqV1"));
/* 1480:     */       }
/* 1481:1582 */       TypePayeeTrnRsV1 localTypePayeeTrnRsV12 = localOFX151BPWServicesBean
/* 1482:1583 */         .processPayeeTrnRqV1(
/* 1483:1584 */         TypePayeeTrnRqV1Helper.clone(paramTypePayeeTrnRqV1), 
/* 1484:1585 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1485:     */       
/* 1486:1587 */       localTypePayeeTrnRsV12 = TypePayeeTrnRsV1Helper.clone(localTypePayeeTrnRsV12);
/* 1487:1588 */       localEJBContext.returnToPool();
/* 1488:1589 */       return localTypePayeeTrnRsV12;
/* 1489:     */     }
/* 1490:     */     catch (Exception localException)
/* 1491:     */     {
/* 1492:1593 */       if (localEJBContext != null) {
/* 1493:1595 */         localEJBContext.throwRemote(localException);
/* 1494:     */       }
/* 1495:1597 */       throw new EJBException(localException);
/* 1496:     */     }
/* 1497:     */     finally
/* 1498:     */     {
/* 1499:1601 */       this._comp.setCurrent(localJavaComponent);
/* 1500:     */     }
/* 1501:     */   }
/* 1502:     */   
/* 1503:     */   public TypePmtInqTrnRsV1 processPmtInqTrnRqV1(TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, TypeUserData paramTypeUserData)
/* 1504:     */     throws RemoteException
/* 1505:     */   {
/* 1506:1610 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1507:1611 */     EJBContext localEJBContext = null;
/* 1508:     */     try
/* 1509:     */     {
/* 1510:1614 */       localEJBContext = this._comp.getPooledInstance();
/* 1511:1615 */       if (localEJBContext == null) {
/* 1512:1617 */         localEJBContext = _create();
/* 1513:     */       }
/* 1514:1619 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1515:1620 */       if (localEJBContext.debug) {
/* 1516:1622 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPmtInqTrnRqV1"));
/* 1517:     */       }
/* 1518:1624 */       TypePmtInqTrnRsV1 localTypePmtInqTrnRsV12 = localOFX151BPWServicesBean
/* 1519:1625 */         .processPmtInqTrnRqV1(
/* 1520:1626 */         TypePmtInqTrnRqV1Helper.clone(paramTypePmtInqTrnRqV1), 
/* 1521:1627 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1522:     */       
/* 1523:1629 */       localTypePmtInqTrnRsV12 = TypePmtInqTrnRsV1Helper.clone(localTypePmtInqTrnRsV12);
/* 1524:1630 */       localEJBContext.returnToPool();
/* 1525:1631 */       return localTypePmtInqTrnRsV12;
/* 1526:     */     }
/* 1527:     */     catch (Exception localException)
/* 1528:     */     {
/* 1529:1635 */       if (localEJBContext != null) {
/* 1530:1637 */         localEJBContext.throwRemote(localException);
/* 1531:     */       }
/* 1532:1639 */       throw new EJBException(localException);
/* 1533:     */     }
/* 1534:     */     finally
/* 1535:     */     {
/* 1536:1643 */       this._comp.setCurrent(localJavaComponent);
/* 1537:     */     }
/* 1538:     */   }
/* 1539:     */   
/* 1540:     */   public TypePmtSyncRsV1 processPmtSyncRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData)
/* 1541:     */     throws RemoteException
/* 1542:     */   {
/* 1543:1652 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1544:1653 */     EJBContext localEJBContext = null;
/* 1545:     */     try
/* 1546:     */     {
/* 1547:1656 */       localEJBContext = this._comp.getPooledInstance();
/* 1548:1657 */       if (localEJBContext == null) {
/* 1549:1659 */         localEJBContext = _create();
/* 1550:     */       }
/* 1551:1661 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1552:1662 */       if (localEJBContext.debug) {
/* 1553:1664 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPmtSyncRqV1"));
/* 1554:     */       }
/* 1555:1666 */       TypePmtSyncRsV1 localTypePmtSyncRsV12 = localOFX151BPWServicesBean
/* 1556:1667 */         .processPmtSyncRqV1(
/* 1557:1668 */         TypePmtSyncRqV1Helper.clone(paramTypePmtSyncRqV1), 
/* 1558:1669 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1559:     */       
/* 1560:1671 */       localTypePmtSyncRsV12 = TypePmtSyncRsV1Helper.clone(localTypePmtSyncRsV12);
/* 1561:1672 */       localEJBContext.returnToPool();
/* 1562:1673 */       return localTypePmtSyncRsV12;
/* 1563:     */     }
/* 1564:     */     catch (Exception localException)
/* 1565:     */     {
/* 1566:1677 */       if (localEJBContext != null) {
/* 1567:1679 */         localEJBContext.throwRemote(localException);
/* 1568:     */       }
/* 1569:1681 */       throw new EJBException(localException);
/* 1570:     */     }
/* 1571:     */     finally
/* 1572:     */     {
/* 1573:1685 */       this._comp.setCurrent(localJavaComponent);
/* 1574:     */     }
/* 1575:     */   }
/* 1576:     */   
/* 1577:     */   public TypePmtTrnRsV1 processPmtTrnRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
/* 1578:     */     throws RemoteException
/* 1579:     */   {
/* 1580:1694 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1581:1695 */     EJBContext localEJBContext = null;
/* 1582:     */     try
/* 1583:     */     {
/* 1584:1698 */       localEJBContext = this._comp.getPooledInstance();
/* 1585:1699 */       if (localEJBContext == null) {
/* 1586:1701 */         localEJBContext = _create();
/* 1587:     */       }
/* 1588:1703 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1589:1704 */       if (localEJBContext.debug) {
/* 1590:1706 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processPmtTrnRqV1"));
/* 1591:     */       }
/* 1592:1708 */       TypePmtTrnRsV1 localTypePmtTrnRsV12 = localOFX151BPWServicesBean
/* 1593:1709 */         .processPmtTrnRqV1(
/* 1594:1710 */         TypePmtTrnRqV1Helper.clone(paramTypePmtTrnRqV1), 
/* 1595:1711 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1596:     */       
/* 1597:1713 */       localTypePmtTrnRsV12 = TypePmtTrnRsV1Helper.clone(localTypePmtTrnRsV12);
/* 1598:1714 */       localEJBContext.returnToPool();
/* 1599:1715 */       return localTypePmtTrnRsV12;
/* 1600:     */     }
/* 1601:     */     catch (Exception localException)
/* 1602:     */     {
/* 1603:1719 */       if (localEJBContext != null) {
/* 1604:1721 */         localEJBContext.throwRemote(localException);
/* 1605:     */       }
/* 1606:1723 */       throw new EJBException(localException);
/* 1607:     */     }
/* 1608:     */     finally
/* 1609:     */     {
/* 1610:1727 */       this._comp.setCurrent(localJavaComponent);
/* 1611:     */     }
/* 1612:     */   }
/* 1613:     */   
/* 1614:     */   public TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData)
/* 1615:     */     throws RemoteException
/* 1616:     */   {
/* 1617:1736 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1618:1737 */     EJBContext localEJBContext = null;
/* 1619:     */     try
/* 1620:     */     {
/* 1621:1740 */       localEJBContext = this._comp.getPooledInstance();
/* 1622:1741 */       if (localEJBContext == null) {
/* 1623:1743 */         localEJBContext = _create();
/* 1624:     */       }
/* 1625:1745 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1626:1746 */       if (localEJBContext.debug) {
/* 1627:1748 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processRecIntraSyncRqV1"));
/* 1628:     */       }
/* 1629:1750 */       TypeRecIntraSyncRsV1 localTypeRecIntraSyncRsV12 = localOFX151BPWServicesBean
/* 1630:1751 */         .processRecIntraSyncRqV1(
/* 1631:1752 */         TypeRecIntraSyncRqV1Helper.clone(paramTypeRecIntraSyncRqV1), 
/* 1632:1753 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1633:     */       
/* 1634:1755 */       localTypeRecIntraSyncRsV12 = TypeRecIntraSyncRsV1Helper.clone(localTypeRecIntraSyncRsV12);
/* 1635:1756 */       localEJBContext.returnToPool();
/* 1636:1757 */       return localTypeRecIntraSyncRsV12;
/* 1637:     */     }
/* 1638:     */     catch (Exception localException)
/* 1639:     */     {
/* 1640:1761 */       if (localEJBContext != null) {
/* 1641:1763 */         localEJBContext.throwRemote(localException);
/* 1642:     */       }
/* 1643:1765 */       throw new EJBException(localException);
/* 1644:     */     }
/* 1645:     */     finally
/* 1646:     */     {
/* 1647:1769 */       this._comp.setCurrent(localJavaComponent);
/* 1648:     */     }
/* 1649:     */   }
/* 1650:     */   
/* 1651:     */   public TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData)
/* 1652:     */     throws RemoteException
/* 1653:     */   {
/* 1654:1778 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1655:1779 */     EJBContext localEJBContext = null;
/* 1656:     */     try
/* 1657:     */     {
/* 1658:1782 */       localEJBContext = this._comp.getPooledInstance();
/* 1659:1783 */       if (localEJBContext == null) {
/* 1660:1785 */         localEJBContext = _create();
/* 1661:     */       }
/* 1662:1787 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1663:1788 */       if (localEJBContext.debug) {
/* 1664:1790 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processRecIntraTrnRqV1"));
/* 1665:     */       }
/* 1666:1792 */       TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV12 = localOFX151BPWServicesBean
/* 1667:1793 */         .processRecIntraTrnRqV1(
/* 1668:1794 */         TypeRecIntraTrnRqV1Helper.clone(paramTypeRecIntraTrnRqV1), 
/* 1669:1795 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1670:     */       
/* 1671:1797 */       localTypeRecIntraTrnRsV12 = TypeRecIntraTrnRsV1Helper.clone(localTypeRecIntraTrnRsV12);
/* 1672:1798 */       localEJBContext.returnToPool();
/* 1673:1799 */       return localTypeRecIntraTrnRsV12;
/* 1674:     */     }
/* 1675:     */     catch (Exception localException)
/* 1676:     */     {
/* 1677:1803 */       if (localEJBContext != null) {
/* 1678:1805 */         localEJBContext.throwRemote(localException);
/* 1679:     */       }
/* 1680:1807 */       throw new EJBException(localException);
/* 1681:     */     }
/* 1682:     */     finally
/* 1683:     */     {
/* 1684:1811 */       this._comp.setCurrent(localJavaComponent);
/* 1685:     */     }
/* 1686:     */   }
/* 1687:     */   
/* 1688:     */   public TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData)
/* 1689:     */     throws RemoteException
/* 1690:     */   {
/* 1691:1820 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1692:1821 */     EJBContext localEJBContext = null;
/* 1693:     */     try
/* 1694:     */     {
/* 1695:1824 */       localEJBContext = this._comp.getPooledInstance();
/* 1696:1825 */       if (localEJBContext == null) {
/* 1697:1827 */         localEJBContext = _create();
/* 1698:     */       }
/* 1699:1829 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1700:1830 */       if (localEJBContext.debug) {
/* 1701:1832 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processRecPmtSyncRqV1"));
/* 1702:     */       }
/* 1703:1834 */       TypeRecPmtSyncRsV1 localTypeRecPmtSyncRsV12 = localOFX151BPWServicesBean
/* 1704:1835 */         .processRecPmtSyncRqV1(
/* 1705:1836 */         TypeRecPmtSyncRqV1Helper.clone(paramTypeRecPmtSyncRqV1), 
/* 1706:1837 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1707:     */       
/* 1708:1839 */       localTypeRecPmtSyncRsV12 = TypeRecPmtSyncRsV1Helper.clone(localTypeRecPmtSyncRsV12);
/* 1709:1840 */       localEJBContext.returnToPool();
/* 1710:1841 */       return localTypeRecPmtSyncRsV12;
/* 1711:     */     }
/* 1712:     */     catch (Exception localException)
/* 1713:     */     {
/* 1714:1845 */       if (localEJBContext != null) {
/* 1715:1847 */         localEJBContext.throwRemote(localException);
/* 1716:     */       }
/* 1717:1849 */       throw new EJBException(localException);
/* 1718:     */     }
/* 1719:     */     finally
/* 1720:     */     {
/* 1721:1853 */       this._comp.setCurrent(localJavaComponent);
/* 1722:     */     }
/* 1723:     */   }
/* 1724:     */   
/* 1725:     */   public TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
/* 1726:     */     throws RemoteException
/* 1727:     */   {
/* 1728:1862 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1729:1863 */     EJBContext localEJBContext = null;
/* 1730:     */     try
/* 1731:     */     {
/* 1732:1866 */       localEJBContext = this._comp.getPooledInstance();
/* 1733:1867 */       if (localEJBContext == null) {
/* 1734:1869 */         localEJBContext = _create();
/* 1735:     */       }
/* 1736:1871 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1737:1872 */       if (localEJBContext.debug) {
/* 1738:1874 */         localEJBContext.logger.debug(localEJBContext.debugMsg("processRecPmtTrnRqV1"));
/* 1739:     */       }
/* 1740:1876 */       TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV12 = localOFX151BPWServicesBean
/* 1741:1877 */         .processRecPmtTrnRqV1(
/* 1742:1878 */         TypeRecPmtTrnRqV1Helper.clone(paramTypeRecPmtTrnRqV1), 
/* 1743:1879 */         TypeUserDataHelper.clone(paramTypeUserData));
/* 1744:     */       
/* 1745:1881 */       localTypeRecPmtTrnRsV12 = TypeRecPmtTrnRsV1Helper.clone(localTypeRecPmtTrnRsV12);
/* 1746:1882 */       localEJBContext.returnToPool();
/* 1747:1883 */       return localTypeRecPmtTrnRsV12;
/* 1748:     */     }
/* 1749:     */     catch (Exception localException)
/* 1750:     */     {
/* 1751:1887 */       if (localEJBContext != null) {
/* 1752:1889 */         localEJBContext.throwRemote(localException);
/* 1753:     */       }
/* 1754:1891 */       throw new EJBException(localException);
/* 1755:     */     }
/* 1756:     */     finally
/* 1757:     */     {
/* 1758:1895 */       this._comp.setCurrent(localJavaComponent);
/* 1759:     */     }
/* 1760:     */   }
/* 1761:     */   
/* 1762:     */   public String[] getPayeeNames(String paramString, int paramInt)
/* 1763:     */     throws RemoteException
/* 1764:     */   {
/* 1765:1904 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1766:1905 */     EJBContext localEJBContext = null;
/* 1767:     */     try
/* 1768:     */     {
/* 1769:1908 */       localEJBContext = this._comp.getPooledInstance();
/* 1770:1909 */       if (localEJBContext == null) {
/* 1771:1911 */         localEJBContext = _create();
/* 1772:     */       }
/* 1773:1913 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1774:1914 */       if (localEJBContext.debug) {
/* 1775:1916 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPayeeNames"));
/* 1776:     */       }
/* 1777:1918 */       String[] arrayOfString2 = localOFX151BPWServicesBean
/* 1778:1919 */         .getPayeeNames(
/* 1779:1920 */         paramString, 
/* 1780:1921 */         paramInt);
/* 1781:     */       
/* 1782:1923 */       arrayOfString2 = StringSeqHelper.clone(arrayOfString2);
/* 1783:1924 */       localEJBContext.returnToPool();
/* 1784:1925 */       return arrayOfString2;
/* 1785:     */     }
/* 1786:     */     catch (Exception localException)
/* 1787:     */     {
/* 1788:1929 */       if (localEJBContext != null) {
/* 1789:1931 */         localEJBContext.throwRemote(localException);
/* 1790:     */       }
/* 1791:1933 */       throw new EJBException(localException);
/* 1792:     */     }
/* 1793:     */     finally
/* 1794:     */     {
/* 1795:1937 */       this._comp.setCurrent(localJavaComponent);
/* 1796:     */     }
/* 1797:     */   }
/* 1798:     */   
/* 1799:     */   public String[] getPayeeNames(String paramString)
/* 1800:     */     throws RemoteException
/* 1801:     */   {
/* 1802:1945 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1803:1946 */     EJBContext localEJBContext = null;
/* 1804:     */     try
/* 1805:     */     {
/* 1806:1949 */       localEJBContext = this._comp.getPooledInstance();
/* 1807:1950 */       if (localEJBContext == null) {
/* 1808:1952 */         localEJBContext = _create();
/* 1809:     */       }
/* 1810:1954 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1811:1955 */       if (localEJBContext.debug) {
/* 1812:1957 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPayeeNames"));
/* 1813:     */       }
/* 1814:1959 */       String[] arrayOfString2 = localOFX151BPWServicesBean
/* 1815:1960 */         .getPayeeNames(
/* 1816:1961 */         paramString);
/* 1817:     */       
/* 1818:1963 */       arrayOfString2 = StringSeqHelper.clone(arrayOfString2);
/* 1819:1964 */       localEJBContext.returnToPool();
/* 1820:1965 */       return arrayOfString2;
/* 1821:     */     }
/* 1822:     */     catch (Exception localException)
/* 1823:     */     {
/* 1824:1969 */       if (localEJBContext != null) {
/* 1825:1971 */         localEJBContext.throwRemote(localException);
/* 1826:     */       }
/* 1827:1973 */       throw new EJBException(localException);
/* 1828:     */     }
/* 1829:     */     finally
/* 1830:     */     {
/* 1831:1977 */       this._comp.setCurrent(localJavaComponent);
/* 1832:     */     }
/* 1833:     */   }
/* 1834:     */   
/* 1835:     */   public String[] getPayeeIDs(String paramString)
/* 1836:     */     throws RemoteException
/* 1837:     */   {
/* 1838:1985 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1839:1986 */     EJBContext localEJBContext = null;
/* 1840:     */     try
/* 1841:     */     {
/* 1842:1989 */       localEJBContext = this._comp.getPooledInstance();
/* 1843:1990 */       if (localEJBContext == null) {
/* 1844:1992 */         localEJBContext = _create();
/* 1845:     */       }
/* 1846:1994 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1847:1995 */       if (localEJBContext.debug) {
/* 1848:1997 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPayeeIDs"));
/* 1849:     */       }
/* 1850:1999 */       String[] arrayOfString2 = localOFX151BPWServicesBean
/* 1851:2000 */         .getPayeeIDs(
/* 1852:2001 */         paramString);
/* 1853:     */       
/* 1854:2003 */       arrayOfString2 = StringSeqHelper.clone(arrayOfString2);
/* 1855:2004 */       localEJBContext.returnToPool();
/* 1856:2005 */       return arrayOfString2;
/* 1857:     */     }
/* 1858:     */     catch (Exception localException)
/* 1859:     */     {
/* 1860:2009 */       if (localEJBContext != null) {
/* 1861:2011 */         localEJBContext.throwRemote(localException);
/* 1862:     */       }
/* 1863:2013 */       throw new EJBException(localException);
/* 1864:     */     }
/* 1865:     */     finally
/* 1866:     */     {
/* 1867:2017 */       this._comp.setCurrent(localJavaComponent);
/* 1868:     */     }
/* 1869:     */   }
/* 1870:     */   
/* 1871:     */   public PayeeInfo[] getPayees(String paramString, int paramInt)
/* 1872:     */     throws RemoteException
/* 1873:     */   {
/* 1874:2026 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1875:2027 */     EJBContext localEJBContext = null;
/* 1876:     */     try
/* 1877:     */     {
/* 1878:2030 */       localEJBContext = this._comp.getPooledInstance();
/* 1879:2031 */       if (localEJBContext == null) {
/* 1880:2033 */         localEJBContext = _create();
/* 1881:     */       }
/* 1882:2035 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1883:2036 */       if (localEJBContext.debug) {
/* 1884:2038 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPayees"));
/* 1885:     */       }
/* 1886:2040 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX151BPWServicesBean
/* 1887:2041 */         .getPayees(
/* 1888:2042 */         paramString, 
/* 1889:2043 */         paramInt);
/* 1890:     */       
/* 1891:2045 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 1892:2046 */       localEJBContext.returnToPool();
/* 1893:2047 */       return arrayOfPayeeInfo2;
/* 1894:     */     }
/* 1895:     */     catch (Exception localException)
/* 1896:     */     {
/* 1897:2051 */       if (localEJBContext != null) {
/* 1898:2053 */         localEJBContext.throwRemote(localException);
/* 1899:     */       }
/* 1900:2055 */       throw new EJBException(localException);
/* 1901:     */     }
/* 1902:     */     finally
/* 1903:     */     {
/* 1904:2059 */       this._comp.setCurrent(localJavaComponent);
/* 1905:     */     }
/* 1906:     */   }
/* 1907:     */   
/* 1908:     */   public PayeeInfo[] getPayees(String paramString)
/* 1909:     */     throws RemoteException
/* 1910:     */   {
/* 1911:2067 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1912:2068 */     EJBContext localEJBContext = null;
/* 1913:     */     try
/* 1914:     */     {
/* 1915:2071 */       localEJBContext = this._comp.getPooledInstance();
/* 1916:2072 */       if (localEJBContext == null) {
/* 1917:2074 */         localEJBContext = _create();
/* 1918:     */       }
/* 1919:2076 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1920:2077 */       if (localEJBContext.debug) {
/* 1921:2079 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPayees"));
/* 1922:     */       }
/* 1923:2081 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX151BPWServicesBean
/* 1924:2082 */         .getPayees(
/* 1925:2083 */         paramString);
/* 1926:     */       
/* 1927:2085 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 1928:2086 */       localEJBContext.returnToPool();
/* 1929:2087 */       return arrayOfPayeeInfo2;
/* 1930:     */     }
/* 1931:     */     catch (Exception localException)
/* 1932:     */     {
/* 1933:2091 */       if (localEJBContext != null) {
/* 1934:2093 */         localEJBContext.throwRemote(localException);
/* 1935:     */       }
/* 1936:2095 */       throw new EJBException(localException);
/* 1937:     */     }
/* 1938:     */     finally
/* 1939:     */     {
/* 1940:2099 */       this._comp.setCurrent(localJavaComponent);
/* 1941:     */     }
/* 1942:     */   }
/* 1943:     */   
/* 1944:     */   public PayeeInfo[] searchGlobalPayees(String paramString)
/* 1945:     */     throws RemoteException
/* 1946:     */   {
/* 1947:2107 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1948:2108 */     EJBContext localEJBContext = null;
/* 1949:     */     try
/* 1950:     */     {
/* 1951:2111 */       localEJBContext = this._comp.getPooledInstance();
/* 1952:2112 */       if (localEJBContext == null) {
/* 1953:2114 */         localEJBContext = _create();
/* 1954:     */       }
/* 1955:2116 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1956:2117 */       if (localEJBContext.debug) {
/* 1957:2119 */         localEJBContext.logger.debug(localEJBContext.debugMsg("searchGlobalPayees"));
/* 1958:     */       }
/* 1959:2121 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX151BPWServicesBean
/* 1960:2122 */         .searchGlobalPayees(
/* 1961:2123 */         paramString);
/* 1962:     */       
/* 1963:2125 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 1964:2126 */       localEJBContext.returnToPool();
/* 1965:2127 */       return arrayOfPayeeInfo2;
/* 1966:     */     }
/* 1967:     */     catch (Exception localException)
/* 1968:     */     {
/* 1969:2131 */       if (localEJBContext != null) {
/* 1970:2133 */         localEJBContext.throwRemote(localException);
/* 1971:     */       }
/* 1972:2135 */       throw new EJBException(localException);
/* 1973:     */     }
/* 1974:     */     finally
/* 1975:     */     {
/* 1976:2139 */       this._comp.setCurrent(localJavaComponent);
/* 1977:     */     }
/* 1978:     */   }
/* 1979:     */   
/* 1980:     */   public PayeeInfo[] updatePayee(PayeeInfo paramPayeeInfo, int paramInt)
/* 1981:     */     throws RemoteException
/* 1982:     */   {
/* 1983:2148 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 1984:2149 */     EJBContext localEJBContext = null;
/* 1985:     */     try
/* 1986:     */     {
/* 1987:2152 */       localEJBContext = this._comp.getPooledInstance();
/* 1988:2153 */       if (localEJBContext == null) {
/* 1989:2155 */         localEJBContext = _create();
/* 1990:     */       }
/* 1991:2157 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 1992:2158 */       if (localEJBContext.debug) {
/* 1993:2160 */         localEJBContext.logger.debug(localEJBContext.debugMsg("updatePayee"));
/* 1994:     */       }
/* 1995:2162 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX151BPWServicesBean
/* 1996:2163 */         .updatePayee(
/* 1997:2164 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo), 
/* 1998:2165 */         paramInt);
/* 1999:     */       
/* 2000:2167 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 2001:2168 */       localEJBContext.returnToPool();
/* 2002:2169 */       return arrayOfPayeeInfo2;
/* 2003:     */     }
/* 2004:     */     catch (Exception localException)
/* 2005:     */     {
/* 2006:2173 */       if (localEJBContext != null) {
/* 2007:2175 */         localEJBContext.throwRemote(localException);
/* 2008:     */       }
/* 2009:2177 */       throw new EJBException(localException);
/* 2010:     */     }
/* 2011:     */     finally
/* 2012:     */     {
/* 2013:2181 */       this._comp.setCurrent(localJavaComponent);
/* 2014:     */     }
/* 2015:     */   }
/* 2016:     */   
/* 2017:     */   public void updatePayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
/* 2018:     */     throws RemoteException
/* 2019:     */   {
/* 2020:2190 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2021:2191 */     EJBContext localEJBContext = null;
/* 2022:     */     try
/* 2023:     */     {
/* 2024:2194 */       localEJBContext = this._comp.getPooledInstance();
/* 2025:2195 */       if (localEJBContext == null) {
/* 2026:2197 */         localEJBContext = _create();
/* 2027:     */       }
/* 2028:2199 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2029:2200 */       if (localEJBContext.debug) {
/* 2030:2202 */         localEJBContext.logger.debug(localEJBContext.debugMsg("updatePayee"));
/* 2031:     */       }
/* 2032:2205 */       localOFX151BPWServicesBean.updatePayee(
/* 2033:2206 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo), 
/* 2034:2207 */         (PayeeRouteInfo)EJBContext.clone(paramPayeeRouteInfo));
/* 2035:     */       
/* 2036:2209 */       localEJBContext.returnToPool();
/* 2037:     */     }
/* 2038:     */     catch (Exception localException)
/* 2039:     */     {
/* 2040:2213 */       if (localEJBContext != null) {
/* 2041:2215 */         localEJBContext.throwRemote(localException);
/* 2042:     */       }
/* 2043:2217 */       throw new EJBException(localException);
/* 2044:     */     }
/* 2045:     */     finally
/* 2046:     */     {
/* 2047:2221 */       this._comp.setCurrent(localJavaComponent);
/* 2048:     */     }
/* 2049:     */   }
/* 2050:     */   
/* 2051:     */   public void deletePayee(String paramString)
/* 2052:     */     throws RemoteException
/* 2053:     */   {
/* 2054:2229 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2055:2230 */     EJBContext localEJBContext = null;
/* 2056:     */     try
/* 2057:     */     {
/* 2058:2233 */       localEJBContext = this._comp.getPooledInstance();
/* 2059:2234 */       if (localEJBContext == null) {
/* 2060:2236 */         localEJBContext = _create();
/* 2061:     */       }
/* 2062:2238 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2063:2239 */       if (localEJBContext.debug) {
/* 2064:2241 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deletePayee"));
/* 2065:     */       }
/* 2066:2244 */       localOFX151BPWServicesBean.deletePayee(
/* 2067:2245 */         paramString);
/* 2068:     */       
/* 2069:2247 */       localEJBContext.returnToPool();
/* 2070:     */     }
/* 2071:     */     catch (Exception localException)
/* 2072:     */     {
/* 2073:2251 */       if (localEJBContext != null) {
/* 2074:2253 */         localEJBContext.throwRemote(localException);
/* 2075:     */       }
/* 2076:2255 */       throw new EJBException(localException);
/* 2077:     */     }
/* 2078:     */     finally
/* 2079:     */     {
/* 2080:2259 */       this._comp.setCurrent(localJavaComponent);
/* 2081:     */     }
/* 2082:     */   }
/* 2083:     */   
/* 2084:     */   public void deletePayees(String[] paramArrayOfString)
/* 2085:     */     throws RemoteException
/* 2086:     */   {
/* 2087:2267 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2088:2268 */     EJBContext localEJBContext = null;
/* 2089:     */     try
/* 2090:     */     {
/* 2091:2271 */       localEJBContext = this._comp.getPooledInstance();
/* 2092:2272 */       if (localEJBContext == null) {
/* 2093:2274 */         localEJBContext = _create();
/* 2094:     */       }
/* 2095:2276 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2096:2277 */       if (localEJBContext.debug) {
/* 2097:2279 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deletePayees"));
/* 2098:     */       }
/* 2099:2282 */       localOFX151BPWServicesBean.deletePayees(
/* 2100:2283 */         StringSeqHelper.clone(paramArrayOfString));
/* 2101:     */       
/* 2102:2285 */       localEJBContext.returnToPool();
/* 2103:     */     }
/* 2104:     */     catch (Exception localException)
/* 2105:     */     {
/* 2106:2289 */       if (localEJBContext != null) {
/* 2107:2291 */         localEJBContext.throwRemote(localException);
/* 2108:     */       }
/* 2109:2293 */       throw new EJBException(localException);
/* 2110:     */     }
/* 2111:     */     finally
/* 2112:     */     {
/* 2113:2297 */       this._comp.setCurrent(localJavaComponent);
/* 2114:     */     }
/* 2115:     */   }
/* 2116:     */   
/* 2117:     */   public PayeeInfo findPayeeByID(String paramString)
/* 2118:     */     throws RemoteException
/* 2119:     */   {
/* 2120:2305 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2121:2306 */     EJBContext localEJBContext = null;
/* 2122:     */     try
/* 2123:     */     {
/* 2124:2309 */       localEJBContext = this._comp.getPooledInstance();
/* 2125:2310 */       if (localEJBContext == null) {
/* 2126:2312 */         localEJBContext = _create();
/* 2127:     */       }
/* 2128:2314 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2129:2315 */       if (localEJBContext.debug) {
/* 2130:2317 */         localEJBContext.logger.debug(localEJBContext.debugMsg("findPayeeByID"));
/* 2131:     */       }
/* 2132:2319 */       PayeeInfo localPayeeInfo2 = localOFX151BPWServicesBean
/* 2133:2320 */         .findPayeeByID(
/* 2134:2321 */         paramString);
/* 2135:     */       
/* 2136:2323 */       localPayeeInfo2 = (PayeeInfo)EJBContext.clone(localPayeeInfo2);
/* 2137:2324 */       localEJBContext.returnToPool();
/* 2138:2325 */       return localPayeeInfo2;
/* 2139:     */     }
/* 2140:     */     catch (Exception localException)
/* 2141:     */     {
/* 2142:2329 */       if (localEJBContext != null) {
/* 2143:2331 */         localEJBContext.throwRemote(localException);
/* 2144:     */       }
/* 2145:2333 */       throw new EJBException(localException);
/* 2146:     */     }
/* 2147:     */     finally
/* 2148:     */     {
/* 2149:2337 */       this._comp.setCurrent(localJavaComponent);
/* 2150:     */     }
/* 2151:     */   }
/* 2152:     */   
/* 2153:     */   public void setPayeeStatus(String paramString1, String paramString2)
/* 2154:     */     throws RemoteException
/* 2155:     */   {
/* 2156:2346 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2157:2347 */     EJBContext localEJBContext = null;
/* 2158:     */     try
/* 2159:     */     {
/* 2160:2350 */       localEJBContext = this._comp.getPooledInstance();
/* 2161:2351 */       if (localEJBContext == null) {
/* 2162:2353 */         localEJBContext = _create();
/* 2163:     */       }
/* 2164:2355 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2165:2356 */       if (localEJBContext.debug) {
/* 2166:2358 */         localEJBContext.logger.debug(localEJBContext.debugMsg("setPayeeStatus"));
/* 2167:     */       }
/* 2168:2361 */       localOFX151BPWServicesBean.setPayeeStatus(
/* 2169:2362 */         paramString1, 
/* 2170:2363 */         paramString2);
/* 2171:     */       
/* 2172:2365 */       localEJBContext.returnToPool();
/* 2173:     */     }
/* 2174:     */     catch (Exception localException)
/* 2175:     */     {
/* 2176:2369 */       if (localEJBContext != null) {
/* 2177:2371 */         localEJBContext.throwRemote(localException);
/* 2178:     */       }
/* 2179:2373 */       throw new EJBException(localException);
/* 2180:     */     }
/* 2181:     */     finally
/* 2182:     */     {
/* 2183:2377 */       this._comp.setCurrent(localJavaComponent);
/* 2184:     */     }
/* 2185:     */   }
/* 2186:     */   
/* 2187:     */   public int getSmartDate(int paramInt)
/* 2188:     */     throws RemoteException
/* 2189:     */   {
/* 2190:2385 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2191:2386 */     EJBContext localEJBContext = null;
/* 2192:     */     try
/* 2193:     */     {
/* 2194:2389 */       localEJBContext = this._comp.getPooledInstance();
/* 2195:2390 */       if (localEJBContext == null) {
/* 2196:2392 */         localEJBContext = _create();
/* 2197:     */       }
/* 2198:2394 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2199:2395 */       if (localEJBContext.debug) {
/* 2200:2397 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getSmartDate"));
/* 2201:     */       }
/* 2202:2399 */       int j = localOFX151BPWServicesBean
/* 2203:2400 */         .getSmartDate(
/* 2204:2401 */         paramInt);
/* 2205:     */       
/* 2206:2403 */       localEJBContext.returnToPool();
/* 2207:2404 */       return j;
/* 2208:     */     }
/* 2209:     */     catch (Exception localException)
/* 2210:     */     {
/* 2211:2408 */       if (localEJBContext != null) {
/* 2212:2410 */         localEJBContext.throwRemote(localException);
/* 2213:     */       }
/* 2214:2412 */       throw new EJBException(localException);
/* 2215:     */     }
/* 2216:     */     finally
/* 2217:     */     {
/* 2218:2416 */       this._comp.setCurrent(localJavaComponent);
/* 2219:     */     }
/* 2220:     */   }
/* 2221:     */   
/* 2222:     */   public PayeeInfo[] getGlobalPayees(String paramString, int paramInt)
/* 2223:     */     throws RemoteException
/* 2224:     */   {
/* 2225:2425 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2226:2426 */     EJBContext localEJBContext = null;
/* 2227:     */     try
/* 2228:     */     {
/* 2229:2429 */       localEJBContext = this._comp.getPooledInstance();
/* 2230:2430 */       if (localEJBContext == null) {
/* 2231:2432 */         localEJBContext = _create();
/* 2232:     */       }
/* 2233:2434 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2234:2435 */       if (localEJBContext.debug) {
/* 2235:2437 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getGlobalPayees"));
/* 2236:     */       }
/* 2237:2439 */       PayeeInfo[] arrayOfPayeeInfo2 = localOFX151BPWServicesBean
/* 2238:2440 */         .getGlobalPayees(
/* 2239:2441 */         paramString, 
/* 2240:2442 */         paramInt);
/* 2241:     */       
/* 2242:2444 */       arrayOfPayeeInfo2 = PayeeInfoSeqHelper.clone(arrayOfPayeeInfo2);
/* 2243:2445 */       localEJBContext.returnToPool();
/* 2244:2446 */       return arrayOfPayeeInfo2;
/* 2245:     */     }
/* 2246:     */     catch (Exception localException)
/* 2247:     */     {
/* 2248:2450 */       if (localEJBContext != null) {
/* 2249:2452 */         localEJBContext.throwRemote(localException);
/* 2250:     */       }
/* 2251:2454 */       throw new EJBException(localException);
/* 2252:     */     }
/* 2253:     */     finally
/* 2254:     */     {
/* 2255:2458 */       this._comp.setCurrent(localJavaComponent);
/* 2256:     */     }
/* 2257:     */   }
/* 2258:     */   
/* 2259:     */   public String addPayee(PayeeInfo paramPayeeInfo, int paramInt)
/* 2260:     */     throws RemoteException
/* 2261:     */   {
/* 2262:2467 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2263:2468 */     EJBContext localEJBContext = null;
/* 2264:     */     try
/* 2265:     */     {
/* 2266:2471 */       localEJBContext = this._comp.getPooledInstance();
/* 2267:2472 */       if (localEJBContext == null) {
/* 2268:2474 */         localEJBContext = _create();
/* 2269:     */       }
/* 2270:2476 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2271:2477 */       if (localEJBContext.debug) {
/* 2272:2479 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addPayee"));
/* 2273:     */       }
/* 2274:2481 */       String str2 = localOFX151BPWServicesBean
/* 2275:2482 */         .addPayee(
/* 2276:2483 */         (PayeeInfo)EJBContext.clone(paramPayeeInfo), 
/* 2277:2484 */         paramInt);
/* 2278:     */       
/* 2279:2486 */       localEJBContext.returnToPool();
/* 2280:2487 */       return str2;
/* 2281:     */     }
/* 2282:     */     catch (Exception localException)
/* 2283:     */     {
/* 2284:2491 */       if (localEJBContext != null) {
/* 2285:2493 */         localEJBContext.throwRemote(localException);
/* 2286:     */       }
/* 2287:2495 */       throw new EJBException(localException);
/* 2288:     */     }
/* 2289:     */     finally
/* 2290:     */     {
/* 2291:2499 */       this._comp.setCurrent(localJavaComponent);
/* 2292:     */     }
/* 2293:     */   }
/* 2294:     */   
/* 2295:     */   public int addConsumerCrossRef(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
/* 2296:     */     throws RemoteException
/* 2297:     */   {
/* 2298:2507 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2299:2508 */     EJBContext localEJBContext = null;
/* 2300:     */     try
/* 2301:     */     {
/* 2302:2511 */       localEJBContext = this._comp.getPooledInstance();
/* 2303:2512 */       if (localEJBContext == null) {
/* 2304:2514 */         localEJBContext = _create();
/* 2305:     */       }
/* 2306:2516 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2307:2517 */       if (localEJBContext.debug) {
/* 2308:2519 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addConsumerCrossRef"));
/* 2309:     */       }
/* 2310:2521 */       int j = localOFX151BPWServicesBean
/* 2311:2522 */         .addConsumerCrossRef(
/* 2312:2523 */         ConsumerCrossRefInfoSeqHelper.clone(paramArrayOfConsumerCrossRefInfo));
/* 2313:     */       
/* 2314:2525 */       localEJBContext.returnToPool();
/* 2315:2526 */       return j;
/* 2316:     */     }
/* 2317:     */     catch (Exception localException)
/* 2318:     */     {
/* 2319:2530 */       if (localEJBContext != null) {
/* 2320:2532 */         localEJBContext.throwRemote(localException);
/* 2321:     */       }
/* 2322:2534 */       throw new EJBException(localException);
/* 2323:     */     }
/* 2324:     */     finally
/* 2325:     */     {
/* 2326:2538 */       this._comp.setCurrent(localJavaComponent);
/* 2327:     */     }
/* 2328:     */   }
/* 2329:     */   
/* 2330:     */   public int deleteConsumerCrossRef(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
/* 2331:     */     throws RemoteException
/* 2332:     */   {
/* 2333:2546 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2334:2547 */     EJBContext localEJBContext = null;
/* 2335:     */     try
/* 2336:     */     {
/* 2337:2550 */       localEJBContext = this._comp.getPooledInstance();
/* 2338:2551 */       if (localEJBContext == null) {
/* 2339:2553 */         localEJBContext = _create();
/* 2340:     */       }
/* 2341:2555 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2342:2556 */       if (localEJBContext.debug) {
/* 2343:2558 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteConsumerCrossRef"));
/* 2344:     */       }
/* 2345:2560 */       int j = localOFX151BPWServicesBean
/* 2346:2561 */         .deleteConsumerCrossRef(
/* 2347:2562 */         ConsumerCrossRefInfoSeqHelper.clone(paramArrayOfConsumerCrossRefInfo));
/* 2348:     */       
/* 2349:2564 */       localEJBContext.returnToPool();
/* 2350:2565 */       return j;
/* 2351:     */     }
/* 2352:     */     catch (Exception localException)
/* 2353:     */     {
/* 2354:2569 */       if (localEJBContext != null) {
/* 2355:2571 */         localEJBContext.throwRemote(localException);
/* 2356:     */       }
/* 2357:2573 */       throw new EJBException(localException);
/* 2358:     */     }
/* 2359:     */     finally
/* 2360:     */     {
/* 2361:2577 */       this._comp.setCurrent(localJavaComponent);
/* 2362:     */     }
/* 2363:     */   }
/* 2364:     */   
/* 2365:     */   public ConsumerCrossRefInfo[] getConsumerCrossRef(String[] paramArrayOfString)
/* 2366:     */     throws RemoteException
/* 2367:     */   {
/* 2368:2585 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2369:2586 */     EJBContext localEJBContext = null;
/* 2370:     */     try
/* 2371:     */     {
/* 2372:2589 */       localEJBContext = this._comp.getPooledInstance();
/* 2373:2590 */       if (localEJBContext == null) {
/* 2374:2592 */         localEJBContext = _create();
/* 2375:     */       }
/* 2376:2594 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2377:2595 */       if (localEJBContext.debug) {
/* 2378:2597 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getConsumerCrossRef"));
/* 2379:     */       }
/* 2380:2599 */       ConsumerCrossRefInfo[] arrayOfConsumerCrossRefInfo2 = localOFX151BPWServicesBean
/* 2381:2600 */         .getConsumerCrossRef(
/* 2382:2601 */         StringSeqHelper.clone(paramArrayOfString));
/* 2383:     */       
/* 2384:2603 */       arrayOfConsumerCrossRefInfo2 = ConsumerCrossRefInfoSeqHelper.clone(arrayOfConsumerCrossRefInfo2);
/* 2385:2604 */       localEJBContext.returnToPool();
/* 2386:2605 */       return arrayOfConsumerCrossRefInfo2;
/* 2387:     */     }
/* 2388:     */     catch (Exception localException)
/* 2389:     */     {
/* 2390:2609 */       if (localEJBContext != null) {
/* 2391:2611 */         localEJBContext.throwRemote(localException);
/* 2392:     */       }
/* 2393:2613 */       throw new EJBException(localException);
/* 2394:     */     }
/* 2395:     */     finally
/* 2396:     */     {
/* 2397:2617 */       this._comp.setCurrent(localJavaComponent);
/* 2398:     */     }
/* 2399:     */   }
/* 2400:     */   
/* 2401:     */   public int addCustomerBankInfo(CustomerBankInfo[] paramArrayOfCustomerBankInfo)
/* 2402:     */     throws RemoteException
/* 2403:     */   {
/* 2404:2625 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2405:2626 */     EJBContext localEJBContext = null;
/* 2406:     */     try
/* 2407:     */     {
/* 2408:2629 */       localEJBContext = this._comp.getPooledInstance();
/* 2409:2630 */       if (localEJBContext == null) {
/* 2410:2632 */         localEJBContext = _create();
/* 2411:     */       }
/* 2412:2634 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2413:2635 */       if (localEJBContext.debug) {
/* 2414:2637 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addCustomerBankInfo"));
/* 2415:     */       }
/* 2416:2639 */       int j = localOFX151BPWServicesBean
/* 2417:2640 */         .addCustomerBankInfo(
/* 2418:2641 */         CustomerBankInfoSeqHelper.clone(paramArrayOfCustomerBankInfo));
/* 2419:     */       
/* 2420:2643 */       localEJBContext.returnToPool();
/* 2421:2644 */       return j;
/* 2422:     */     }
/* 2423:     */     catch (Exception localException)
/* 2424:     */     {
/* 2425:2648 */       if (localEJBContext != null) {
/* 2426:2650 */         localEJBContext.throwRemote(localException);
/* 2427:     */       }
/* 2428:2652 */       throw new EJBException(localException);
/* 2429:     */     }
/* 2430:     */     finally
/* 2431:     */     {
/* 2432:2656 */       this._comp.setCurrent(localJavaComponent);
/* 2433:     */     }
/* 2434:     */   }
/* 2435:     */   
/* 2436:     */   public int deleteCustomerBankInfo(CustomerBankInfo[] paramArrayOfCustomerBankInfo)
/* 2437:     */     throws RemoteException
/* 2438:     */   {
/* 2439:2664 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2440:2665 */     EJBContext localEJBContext = null;
/* 2441:     */     try
/* 2442:     */     {
/* 2443:2668 */       localEJBContext = this._comp.getPooledInstance();
/* 2444:2669 */       if (localEJBContext == null) {
/* 2445:2671 */         localEJBContext = _create();
/* 2446:     */       }
/* 2447:2673 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2448:2674 */       if (localEJBContext.debug) {
/* 2449:2676 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteCustomerBankInfo"));
/* 2450:     */       }
/* 2451:2678 */       int j = localOFX151BPWServicesBean
/* 2452:2679 */         .deleteCustomerBankInfo(
/* 2453:2680 */         CustomerBankInfoSeqHelper.clone(paramArrayOfCustomerBankInfo));
/* 2454:     */       
/* 2455:2682 */       localEJBContext.returnToPool();
/* 2456:2683 */       return j;
/* 2457:     */     }
/* 2458:     */     catch (Exception localException)
/* 2459:     */     {
/* 2460:2687 */       if (localEJBContext != null) {
/* 2461:2689 */         localEJBContext.throwRemote(localException);
/* 2462:     */       }
/* 2463:2691 */       throw new EJBException(localException);
/* 2464:     */     }
/* 2465:     */     finally
/* 2466:     */     {
/* 2467:2695 */       this._comp.setCurrent(localJavaComponent);
/* 2468:     */     }
/* 2469:     */   }
/* 2470:     */   
/* 2471:     */   public CustomerBankInfo[] getCustomerBankInfo(String[] paramArrayOfString)
/* 2472:     */     throws RemoteException
/* 2473:     */   {
/* 2474:2703 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2475:2704 */     EJBContext localEJBContext = null;
/* 2476:     */     try
/* 2477:     */     {
/* 2478:2707 */       localEJBContext = this._comp.getPooledInstance();
/* 2479:2708 */       if (localEJBContext == null) {
/* 2480:2710 */         localEJBContext = _create();
/* 2481:     */       }
/* 2482:2712 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2483:2713 */       if (localEJBContext.debug) {
/* 2484:2715 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerBankInfo"));
/* 2485:     */       }
/* 2486:2717 */       CustomerBankInfo[] arrayOfCustomerBankInfo2 = localOFX151BPWServicesBean
/* 2487:2718 */         .getCustomerBankInfo(
/* 2488:2719 */         StringSeqHelper.clone(paramArrayOfString));
/* 2489:     */       
/* 2490:2721 */       arrayOfCustomerBankInfo2 = CustomerBankInfoSeqHelper.clone(arrayOfCustomerBankInfo2);
/* 2491:2722 */       localEJBContext.returnToPool();
/* 2492:2723 */       return arrayOfCustomerBankInfo2;
/* 2493:     */     }
/* 2494:     */     catch (Exception localException)
/* 2495:     */     {
/* 2496:2727 */       if (localEJBContext != null) {
/* 2497:2729 */         localEJBContext.throwRemote(localException);
/* 2498:     */       }
/* 2499:2731 */       throw new EJBException(localException);
/* 2500:     */     }
/* 2501:     */     finally
/* 2502:     */     {
/* 2503:2735 */       this._comp.setCurrent(localJavaComponent);
/* 2504:     */     }
/* 2505:     */   }
/* 2506:     */   
/* 2507:     */   public int addCustomerProductAccessInfo(CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
/* 2508:     */     throws RemoteException
/* 2509:     */   {
/* 2510:2743 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2511:2744 */     EJBContext localEJBContext = null;
/* 2512:     */     try
/* 2513:     */     {
/* 2514:2747 */       localEJBContext = this._comp.getPooledInstance();
/* 2515:2748 */       if (localEJBContext == null) {
/* 2516:2750 */         localEJBContext = _create();
/* 2517:     */       }
/* 2518:2752 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2519:2753 */       if (localEJBContext.debug) {
/* 2520:2755 */         localEJBContext.logger.debug(localEJBContext.debugMsg("addCustomerProductAccessInfo"));
/* 2521:     */       }
/* 2522:2757 */       int j = localOFX151BPWServicesBean
/* 2523:2758 */         .addCustomerProductAccessInfo(
/* 2524:2759 */         CustomerProductAccessInfoSeqHelper.clone(paramArrayOfCustomerProductAccessInfo));
/* 2525:     */       
/* 2526:2761 */       localEJBContext.returnToPool();
/* 2527:2762 */       return j;
/* 2528:     */     }
/* 2529:     */     catch (Exception localException)
/* 2530:     */     {
/* 2531:2766 */       if (localEJBContext != null) {
/* 2532:2768 */         localEJBContext.throwRemote(localException);
/* 2533:     */       }
/* 2534:2770 */       throw new EJBException(localException);
/* 2535:     */     }
/* 2536:     */     finally
/* 2537:     */     {
/* 2538:2774 */       this._comp.setCurrent(localJavaComponent);
/* 2539:     */     }
/* 2540:     */   }
/* 2541:     */   
/* 2542:     */   public int deleteCustomerProductAccessInfo(CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
/* 2543:     */     throws RemoteException
/* 2544:     */   {
/* 2545:2782 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2546:2783 */     EJBContext localEJBContext = null;
/* 2547:     */     try
/* 2548:     */     {
/* 2549:2786 */       localEJBContext = this._comp.getPooledInstance();
/* 2550:2787 */       if (localEJBContext == null) {
/* 2551:2789 */         localEJBContext = _create();
/* 2552:     */       }
/* 2553:2791 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2554:2792 */       if (localEJBContext.debug) {
/* 2555:2794 */         localEJBContext.logger.debug(localEJBContext.debugMsg("deleteCustomerProductAccessInfo"));
/* 2556:     */       }
/* 2557:2796 */       int j = localOFX151BPWServicesBean
/* 2558:2797 */         .deleteCustomerProductAccessInfo(
/* 2559:2798 */         CustomerProductAccessInfoSeqHelper.clone(paramArrayOfCustomerProductAccessInfo));
/* 2560:     */       
/* 2561:2800 */       localEJBContext.returnToPool();
/* 2562:2801 */       return j;
/* 2563:     */     }
/* 2564:     */     catch (Exception localException)
/* 2565:     */     {
/* 2566:2805 */       if (localEJBContext != null) {
/* 2567:2807 */         localEJBContext.throwRemote(localException);
/* 2568:     */       }
/* 2569:2809 */       throw new EJBException(localException);
/* 2570:     */     }
/* 2571:     */     finally
/* 2572:     */     {
/* 2573:2813 */       this._comp.setCurrent(localJavaComponent);
/* 2574:     */     }
/* 2575:     */   }
/* 2576:     */   
/* 2577:     */   public CustomerProductAccessInfo[] getCustomerProductAccessInfo(String[] paramArrayOfString)
/* 2578:     */     throws RemoteException
/* 2579:     */   {
/* 2580:2821 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2581:2822 */     EJBContext localEJBContext = null;
/* 2582:     */     try
/* 2583:     */     {
/* 2584:2825 */       localEJBContext = this._comp.getPooledInstance();
/* 2585:2826 */       if (localEJBContext == null) {
/* 2586:2828 */         localEJBContext = _create();
/* 2587:     */       }
/* 2588:2830 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2589:2831 */       if (localEJBContext.debug) {
/* 2590:2833 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getCustomerProductAccessInfo"));
/* 2591:     */       }
/* 2592:2835 */       CustomerProductAccessInfo[] arrayOfCustomerProductAccessInfo2 = localOFX151BPWServicesBean
/* 2593:2836 */         .getCustomerProductAccessInfo(
/* 2594:2837 */         StringSeqHelper.clone(paramArrayOfString));
/* 2595:     */       
/* 2596:2839 */       arrayOfCustomerProductAccessInfo2 = CustomerProductAccessInfoSeqHelper.clone(arrayOfCustomerProductAccessInfo2);
/* 2597:2840 */       localEJBContext.returnToPool();
/* 2598:2841 */       return arrayOfCustomerProductAccessInfo2;
/* 2599:     */     }
/* 2600:     */     catch (Exception localException)
/* 2601:     */     {
/* 2602:2845 */       if (localEJBContext != null) {
/* 2603:2847 */         localEJBContext.throwRemote(localException);
/* 2604:     */       }
/* 2605:2849 */       throw new EJBException(localException);
/* 2606:     */     }
/* 2607:     */     finally
/* 2608:     */     {
/* 2609:2853 */       this._comp.setCurrent(localJavaComponent);
/* 2610:     */     }
/* 2611:     */   }
/* 2612:     */   
/* 2613:     */   public boolean validateMetavanteCustAcctByConsumerID(String paramString1, String paramString2)
/* 2614:     */     throws RemoteException
/* 2615:     */   {
/* 2616:2862 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2617:2863 */     EJBContext localEJBContext = null;
/* 2618:     */     try
/* 2619:     */     {
/* 2620:2866 */       localEJBContext = this._comp.getPooledInstance();
/* 2621:2867 */       if (localEJBContext == null) {
/* 2622:2869 */         localEJBContext = _create();
/* 2623:     */       }
/* 2624:2871 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2625:2872 */       if (localEJBContext.debug) {
/* 2626:2874 */         localEJBContext.logger.debug(localEJBContext.debugMsg("validateMetavanteCustAcctByConsumerID"));
/* 2627:     */       }
/* 2628:2876 */       boolean bool2 = localOFX151BPWServicesBean
/* 2629:2877 */         .validateMetavanteCustAcctByConsumerID(
/* 2630:2878 */         paramString1, 
/* 2631:2879 */         paramString2);
/* 2632:     */       
/* 2633:2881 */       localEJBContext.returnToPool();
/* 2634:2882 */       return bool2;
/* 2635:     */     }
/* 2636:     */     catch (Exception localException)
/* 2637:     */     {
/* 2638:2886 */       if (localEJBContext != null) {
/* 2639:2888 */         localEJBContext.throwRemote(localException);
/* 2640:     */       }
/* 2641:2890 */       throw new EJBException(localException);
/* 2642:     */     }
/* 2643:     */     finally
/* 2644:     */     {
/* 2645:2894 */       this._comp.setCurrent(localJavaComponent);
/* 2646:     */     }
/* 2647:     */   }
/* 2648:     */   
/* 2649:     */   public boolean validateMetavanteCustAcctByCustomerID(String paramString1, String paramString2)
/* 2650:     */     throws RemoteException
/* 2651:     */   {
/* 2652:2903 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2653:2904 */     EJBContext localEJBContext = null;
/* 2654:     */     try
/* 2655:     */     {
/* 2656:2907 */       localEJBContext = this._comp.getPooledInstance();
/* 2657:2908 */       if (localEJBContext == null) {
/* 2658:2910 */         localEJBContext = _create();
/* 2659:     */       }
/* 2660:2912 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2661:2913 */       if (localEJBContext.debug) {
/* 2662:2915 */         localEJBContext.logger.debug(localEJBContext.debugMsg("validateMetavanteCustAcctByCustomerID"));
/* 2663:     */       }
/* 2664:2917 */       boolean bool2 = localOFX151BPWServicesBean
/* 2665:2918 */         .validateMetavanteCustAcctByCustomerID(
/* 2666:2919 */         paramString1, 
/* 2667:2920 */         paramString2);
/* 2668:     */       
/* 2669:2922 */       localEJBContext.returnToPool();
/* 2670:2923 */       return bool2;
/* 2671:     */     }
/* 2672:     */     catch (Exception localException)
/* 2673:     */     {
/* 2674:2927 */       if (localEJBContext != null) {
/* 2675:2929 */         localEJBContext.throwRemote(localException);
/* 2676:     */       }
/* 2677:2931 */       throw new EJBException(localException);
/* 2678:     */     }
/* 2679:     */     finally
/* 2680:     */     {
/* 2681:2935 */       this._comp.setCurrent(localJavaComponent);
/* 2682:     */     }
/* 2683:     */   }
/* 2684:     */   
/* 2685:     */   public BPWHist getPmtHistory(BPWHist paramBPWHist)
/* 2686:     */     throws RemoteException
/* 2687:     */   {
/* 2688:2943 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2689:2944 */     EJBContext localEJBContext = null;
/* 2690:     */     try
/* 2691:     */     {
/* 2692:2947 */       localEJBContext = this._comp.getPooledInstance();
/* 2693:2948 */       if (localEJBContext == null) {
/* 2694:2950 */         localEJBContext = _create();
/* 2695:     */       }
/* 2696:2952 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2697:2953 */       if (localEJBContext.debug) {
/* 2698:2955 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPmtHistory"));
/* 2699:     */       }
/* 2700:2957 */       BPWHist localBPWHist2 = localOFX151BPWServicesBean
/* 2701:2958 */         .getPmtHistory(
/* 2702:2959 */         BPWHistHelper.clone(paramBPWHist));
/* 2703:     */       
/* 2704:2961 */       localBPWHist2 = BPWHistHelper.clone(localBPWHist2);
/* 2705:2962 */       localEJBContext.returnToPool();
/* 2706:2963 */       return localBPWHist2;
/* 2707:     */     }
/* 2708:     */     catch (Exception localException)
/* 2709:     */     {
/* 2710:2967 */       if (localEJBContext != null) {
/* 2711:2969 */         localEJBContext.throwRemote(localException);
/* 2712:     */       }
/* 2713:2971 */       throw new EJBException(localException);
/* 2714:     */     }
/* 2715:     */     finally
/* 2716:     */     {
/* 2717:2975 */       this._comp.setCurrent(localJavaComponent);
/* 2718:     */     }
/* 2719:     */   }
/* 2720:     */   
/* 2721:     */   public BPWHist getIntraHistory(BPWHist paramBPWHist)
/* 2722:     */     throws RemoteException
/* 2723:     */   {
/* 2724:2983 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2725:2984 */     EJBContext localEJBContext = null;
/* 2726:     */     try
/* 2727:     */     {
/* 2728:2987 */       localEJBContext = this._comp.getPooledInstance();
/* 2729:2988 */       if (localEJBContext == null) {
/* 2730:2990 */         localEJBContext = _create();
/* 2731:     */       }
/* 2732:2992 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2733:2993 */       if (localEJBContext.debug) {
/* 2734:2995 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getIntraHistory"));
/* 2735:     */       }
/* 2736:2997 */       BPWHist localBPWHist2 = localOFX151BPWServicesBean
/* 2737:2998 */         .getIntraHistory(
/* 2738:2999 */         BPWHistHelper.clone(paramBPWHist));
/* 2739:     */       
/* 2740:3001 */       localBPWHist2 = BPWHistHelper.clone(localBPWHist2);
/* 2741:3002 */       localEJBContext.returnToPool();
/* 2742:3003 */       return localBPWHist2;
/* 2743:     */     }
/* 2744:     */     catch (Exception localException)
/* 2745:     */     {
/* 2746:3007 */       if (localEJBContext != null) {
/* 2747:3009 */         localEJBContext.throwRemote(localException);
/* 2748:     */       }
/* 2749:3011 */       throw new EJBException(localException);
/* 2750:     */     }
/* 2751:     */     finally
/* 2752:     */     {
/* 2753:3015 */       this._comp.setCurrent(localJavaComponent);
/* 2754:     */     }
/* 2755:     */   }
/* 2756:     */   
/* 2757:     */   public IntraTrnInfo[] getIntraById(String[] paramArrayOfString)
/* 2758:     */     throws RemoteException
/* 2759:     */   {
/* 2760:3023 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2761:3024 */     EJBContext localEJBContext = null;
/* 2762:     */     try
/* 2763:     */     {
/* 2764:3027 */       localEJBContext = this._comp.getPooledInstance();
/* 2765:3028 */       if (localEJBContext == null) {
/* 2766:3030 */         localEJBContext = _create();
/* 2767:     */       }
/* 2768:3032 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2769:3033 */       if (localEJBContext.debug) {
/* 2770:3035 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getIntraById"));
/* 2771:     */       }
/* 2772:3037 */       IntraTrnInfo[] arrayOfIntraTrnInfo2 = localOFX151BPWServicesBean
/* 2773:3038 */         .getIntraById(
/* 2774:3039 */         StringSeqHelper.clone(paramArrayOfString));
/* 2775:     */       
/* 2776:3041 */       arrayOfIntraTrnInfo2 = IntraTrnInfoSeqHelper.clone(arrayOfIntraTrnInfo2);
/* 2777:3042 */       localEJBContext.returnToPool();
/* 2778:3043 */       return arrayOfIntraTrnInfo2;
/* 2779:     */     }
/* 2780:     */     catch (Exception localException)
/* 2781:     */     {
/* 2782:3047 */       if (localEJBContext != null) {
/* 2783:3049 */         localEJBContext.throwRemote(localException);
/* 2784:     */       }
/* 2785:3051 */       throw new EJBException(localException);
/* 2786:     */     }
/* 2787:     */     finally
/* 2788:     */     {
/* 2789:3055 */       this._comp.setCurrent(localJavaComponent);
/* 2790:     */     }
/* 2791:     */   }
/* 2792:     */   
/* 2793:     */   public IntraTrnInfo getIntraById(String paramString)
/* 2794:     */     throws RemoteException
/* 2795:     */   {
/* 2796:3063 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2797:3064 */     EJBContext localEJBContext = null;
/* 2798:     */     try
/* 2799:     */     {
/* 2800:3067 */       localEJBContext = this._comp.getPooledInstance();
/* 2801:3068 */       if (localEJBContext == null) {
/* 2802:3070 */         localEJBContext = _create();
/* 2803:     */       }
/* 2804:3072 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2805:3073 */       if (localEJBContext.debug) {
/* 2806:3075 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getIntraById"));
/* 2807:     */       }
/* 2808:3077 */       IntraTrnInfo localIntraTrnInfo2 = localOFX151BPWServicesBean
/* 2809:3078 */         .getIntraById(
/* 2810:3079 */         paramString);
/* 2811:     */       
/* 2812:3081 */       localIntraTrnInfo2 = (IntraTrnInfo)EJBContext.clone(localIntraTrnInfo2);
/* 2813:3082 */       localEJBContext.returnToPool();
/* 2814:3083 */       return localIntraTrnInfo2;
/* 2815:     */     }
/* 2816:     */     catch (Exception localException)
/* 2817:     */     {
/* 2818:3087 */       if (localEJBContext != null) {
/* 2819:3089 */         localEJBContext.throwRemote(localException);
/* 2820:     */       }
/* 2821:3091 */       throw new EJBException(localException);
/* 2822:     */     }
/* 2823:     */     finally
/* 2824:     */     {
/* 2825:3095 */       this._comp.setCurrent(localJavaComponent);
/* 2826:     */     }
/* 2827:     */   }
/* 2828:     */   
/* 2829:     */   public PmtInfo getPmtById(String paramString)
/* 2830:     */     throws RemoteException
/* 2831:     */   {
/* 2832:3103 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2833:3104 */     EJBContext localEJBContext = null;
/* 2834:     */     try
/* 2835:     */     {
/* 2836:3107 */       localEJBContext = this._comp.getPooledInstance();
/* 2837:3108 */       if (localEJBContext == null) {
/* 2838:3110 */         localEJBContext = _create();
/* 2839:     */       }
/* 2840:3112 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2841:3113 */       if (localEJBContext.debug) {
/* 2842:3115 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPmtById"));
/* 2843:     */       }
/* 2844:3117 */       PmtInfo localPmtInfo2 = localOFX151BPWServicesBean
/* 2845:3118 */         .getPmtById(
/* 2846:3119 */         paramString);
/* 2847:     */       
/* 2848:3121 */       localPmtInfo2 = (PmtInfo)EJBContext.clone(localPmtInfo2);
/* 2849:3122 */       localEJBContext.returnToPool();
/* 2850:3123 */       return localPmtInfo2;
/* 2851:     */     }
/* 2852:     */     catch (Exception localException)
/* 2853:     */     {
/* 2854:3127 */       if (localEJBContext != null) {
/* 2855:3129 */         localEJBContext.throwRemote(localException);
/* 2856:     */       }
/* 2857:3131 */       throw new EJBException(localException);
/* 2858:     */     }
/* 2859:     */     finally
/* 2860:     */     {
/* 2861:3135 */       this._comp.setCurrent(localJavaComponent);
/* 2862:     */     }
/* 2863:     */   }
/* 2864:     */   
/* 2865:     */   public PmtInfo[] getPmtById(String[] paramArrayOfString)
/* 2866:     */     throws RemoteException
/* 2867:     */   {
/* 2868:3143 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2869:3144 */     EJBContext localEJBContext = null;
/* 2870:     */     try
/* 2871:     */     {
/* 2872:3147 */       localEJBContext = this._comp.getPooledInstance();
/* 2873:3148 */       if (localEJBContext == null) {
/* 2874:3150 */         localEJBContext = _create();
/* 2875:     */       }
/* 2876:3152 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2877:3153 */       if (localEJBContext.debug) {
/* 2878:3155 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPmtById"));
/* 2879:     */       }
/* 2880:3157 */       PmtInfo[] arrayOfPmtInfo2 = localOFX151BPWServicesBean
/* 2881:3158 */         .getPmtById(
/* 2882:3159 */         StringSeqHelper.clone(paramArrayOfString));
/* 2883:     */       
/* 2884:3161 */       arrayOfPmtInfo2 = PmtInfoSeqHelper.clone(arrayOfPmtInfo2);
/* 2885:3162 */       localEJBContext.returnToPool();
/* 2886:3163 */       return arrayOfPmtInfo2;
/* 2887:     */     }
/* 2888:     */     catch (Exception localException)
/* 2889:     */     {
/* 2890:3167 */       if (localEJBContext != null) {
/* 2891:3169 */         localEJBContext.throwRemote(localException);
/* 2892:     */       }
/* 2893:3171 */       throw new EJBException(localException);
/* 2894:     */     }
/* 2895:     */     finally
/* 2896:     */     {
/* 2897:3175 */       this._comp.setCurrent(localJavaComponent);
/* 2898:     */     }
/* 2899:     */   }
/* 2900:     */   
/* 2901:     */   public RecPmtInfo[] getRecPmtById(String[] paramArrayOfString)
/* 2902:     */     throws RemoteException
/* 2903:     */   {
/* 2904:3183 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2905:3184 */     EJBContext localEJBContext = null;
/* 2906:     */     try
/* 2907:     */     {
/* 2908:3187 */       localEJBContext = this._comp.getPooledInstance();
/* 2909:3188 */       if (localEJBContext == null) {
/* 2910:3190 */         localEJBContext = _create();
/* 2911:     */       }
/* 2912:3192 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2913:3193 */       if (localEJBContext.debug) {
/* 2914:3195 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getRecPmtById"));
/* 2915:     */       }
/* 2916:3197 */       RecPmtInfo[] arrayOfRecPmtInfo2 = localOFX151BPWServicesBean
/* 2917:3198 */         .getRecPmtById(
/* 2918:3199 */         StringSeqHelper.clone(paramArrayOfString));
/* 2919:     */       
/* 2920:3201 */       arrayOfRecPmtInfo2 = RecPmtInfoSeqHelper.clone(arrayOfRecPmtInfo2);
/* 2921:3202 */       localEJBContext.returnToPool();
/* 2922:3203 */       return arrayOfRecPmtInfo2;
/* 2923:     */     }
/* 2924:     */     catch (Exception localException)
/* 2925:     */     {
/* 2926:3207 */       if (localEJBContext != null) {
/* 2927:3209 */         localEJBContext.throwRemote(localException);
/* 2928:     */       }
/* 2929:3211 */       throw new EJBException(localException);
/* 2930:     */     }
/* 2931:     */     finally
/* 2932:     */     {
/* 2933:3215 */       this._comp.setCurrent(localJavaComponent);
/* 2934:     */     }
/* 2935:     */   }
/* 2936:     */   
/* 2937:     */   public PayeeInfo getPayeeByListId(String paramString1, String paramString2)
/* 2938:     */     throws RemoteException, FFSException
/* 2939:     */   {
/* 2940:3224 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2941:3225 */     EJBContext localEJBContext = null;
/* 2942:     */     try
/* 2943:     */     {
/* 2944:3228 */       localEJBContext = this._comp.getPooledInstance();
/* 2945:3229 */       if (localEJBContext == null) {
/* 2946:3231 */         localEJBContext = _create();
/* 2947:     */       }
/* 2948:3233 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2949:3234 */       if (localEJBContext.debug) {
/* 2950:3236 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getPayeeByListId"));
/* 2951:     */       }
/* 2952:3238 */       PayeeInfo localPayeeInfo2 = localOFX151BPWServicesBean
/* 2953:3239 */         .getPayeeByListId(
/* 2954:3240 */         paramString1, 
/* 2955:3241 */         paramString2);
/* 2956:     */       
/* 2957:3243 */       localPayeeInfo2 = (PayeeInfo)EJBContext.clone(localPayeeInfo2);
/* 2958:3244 */       localEJBContext.returnToPool();
/* 2959:3245 */       return localPayeeInfo2;
/* 2960:     */     }
/* 2961:     */     catch (Exception localException)
/* 2962:     */     {
/* 2963:3249 */       if (localEJBContext != null)
/* 2964:     */       {
/* 2965:3251 */         if ((localException instanceof FFSException)) {
/* 2966:3253 */           throw ((FFSException)EJBContext.clone(localException));
/* 2967:     */         }
/* 2968:3255 */         localEJBContext.throwRemote(localException);
/* 2969:     */       }
/* 2970:3257 */       throw new EJBException(localException);
/* 2971:     */     }
/* 2972:     */     finally
/* 2973:     */     {
/* 2974:3261 */       this._comp.setCurrent(localJavaComponent);
/* 2975:     */     }
/* 2976:     */   }
/* 2977:     */   
/* 2978:     */   public AccountTypesMap getAccountTypesMap()
/* 2979:     */     throws RemoteException, FFSException
/* 2980:     */   {
/* 2981:3268 */     JavaComponent localJavaComponent = this._comp.setCurrent();
/* 2982:3269 */     EJBContext localEJBContext = null;
/* 2983:     */     try
/* 2984:     */     {
/* 2985:3272 */       localEJBContext = this._comp.getPooledInstance();
/* 2986:3273 */       if (localEJBContext == null) {
/* 2987:3275 */         localEJBContext = _create();
/* 2988:     */       }
/* 2989:3277 */       OFX151BPWServicesBean localOFX151BPWServicesBean = (OFX151BPWServicesBean)localEJBContext.getBean();
/* 2990:3278 */       if (localEJBContext.debug) {
/* 2991:3280 */         localEJBContext.logger.debug(localEJBContext.debugMsg("getAccountTypesMap"));
/* 2992:     */       }
/* 2993:3282 */       AccountTypesMap localAccountTypesMap2 = localOFX151BPWServicesBean
/* 2994:3283 */         .getAccountTypesMap();
/* 2995:     */       
/* 2996:3285 */       localAccountTypesMap2 = (AccountTypesMap)EJBContext.clone(localAccountTypesMap2);
/* 2997:3286 */       localEJBContext.returnToPool();
/* 2998:3287 */       return localAccountTypesMap2;
/* 2999:     */     }
/* 3000:     */     catch (Exception localException)
/* 3001:     */     {
/* 3002:3291 */       if (localEJBContext != null)
/* 3003:     */       {
/* 3004:3293 */         if ((localException instanceof FFSException)) {
/* 3005:3295 */           throw ((FFSException)EJBContext.clone(localException));
/* 3006:     */         }
/* 3007:3297 */         localEJBContext.throwRemote(localException);
/* 3008:     */       }
/* 3009:3299 */       throw new EJBException(localException);
/* 3010:     */     }
/* 3011:     */     finally
/* 3012:     */     {
/* 3013:3303 */       this._comp.setCurrent(localJavaComponent);
/* 3014:     */     }
/* 3015:     */   }
/* 3016:     */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices._lwc_rs_OFX151BPWServices_OFX151BPWServicesBean
 * JD-Core Version:    0.7.0.1
 */