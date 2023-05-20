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
/*   34:     */ import com.ffusion.ffs.interfaces.FFSExceptionHelper;
/*   35:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserData;
/*   36:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserDataHelper;
/*   37:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1;
/*   38:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1Helper;
/*   39:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1;
/*   40:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1Helper;
/*   41:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1SeqHelper;
/*   42:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1;
/*   43:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1Helper;
/*   44:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1;
/*   45:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Helper;
/*   46:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1;
/*   47:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1Helper;
/*   48:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1;
/*   49:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1Helper;
/*   50:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1;
/*   51:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1Helper;
/*   52:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1;
/*   53:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1Helper;
/*   54:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1;
/*   55:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRqV1Helper;
/*   56:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1;
/*   57:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInqTrnRsV1Helper;
/*   58:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1;
/*   59:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1Helper;
/*   60:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1;
/*   61:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1Helper;
/*   62:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1SeqHelper;
/*   63:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1;
/*   64:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1Helper;
/*   65:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1;
/*   66:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1Helper;
/*   67:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1;
/*   68:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1Helper;
/*   69:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1;
/*   70:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1Helper;
/*   71:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1;
/*   72:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1Helper;
/*   73:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1;
/*   74:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1Helper;
/*   75:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1;
/*   76:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1Helper;
/*   77:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1;
/*   78:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1Helper;
/*   79:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1;
/*   80:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1Helper;
/*   81:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1;
/*   82:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Helper;
/*   83:     */ import com.sybase.CORBA.LocalFrame;
/*   84:     */ import com.sybase.CORBA.LocalStack;
/*   85:     */ import com.sybase.CORBA.ObjectRef;
/*   86:     */ import com.sybase.CORBA.ObjectVal;
/*   87:     */ import com.sybase.CORBA.UserException;
/*   88:     */ import com.sybase.CORBA._Request;
/*   89:     */ import com.sybase.ejb.EJBObject;
/*   90:     */ import com.sybase.ejb.cts.StringSeqHelper;
/*   91:     */ import java.util.ArrayList;
/*   92:     */ import org.omg.CORBA.SystemException;
/*   93:     */ import org.omg.CORBA.TRANSIENT;
/*   94:     */ import org.omg.CORBA.portable.IDLEntity;
/*   95:     */ import org.omg.CORBA.portable.InputStream;
/*   96:     */ import org.omg.CORBA.portable.OutputStream;
/*   97:     */ 
/*   98:     */ public class IOFX151BPWServices_Stub
/*   99:     */   extends EJBObject
/*  100:     */   implements IOFX151BPWServices, IDLEntity
/*  101:     */ {
/*  102:     */   public IOFX151BPWServices_Stub(ObjectRef paramObjectRef)
/*  103:     */   {
/*  104:  21 */     super("RMI:com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.IOFX151BPWServices:0000000000000000", paramObjectRef);
/*  105:     */   }
/*  106:     */   
/*  107:     */   public void disconnect()
/*  108:     */     throws java.rmi.RemoteException
/*  109:     */   {
/*  110:  27 */     for (int i = 1;; i++)
/*  111:     */     {
/*  112:  29 */       _Request local_Request = null;
/*  113:  30 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  114:  31 */       boolean bool = false;
/*  115:     */       try
/*  116:     */       {
/*  117:  34 */         local_Request = __request("disconnect");
/*  118:  35 */         bool = localLocalStack.isArgsOnLocal();
/*  119:  36 */         localLocalStack.setArgsOnLocal(false);
/*  120:  37 */         local_Request.invoke();
/*  121:  38 */         return;
/*  122:     */       }
/*  123:     */       catch (TRANSIENT localTRANSIENT)
/*  124:     */       {
/*  125:  42 */         if (i == 10) {
/*  126:  44 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  127:     */         }
/*  128:     */       }
/*  129:     */       catch (UserException localUserException)
/*  130:     */       {
/*  131:  49 */         local_Request.isRMI();
/*  132:     */         
/*  133:     */ 
/*  134:  52 */         throw new java.rmi.RemoteException(localUserException.type);
/*  135:     */       }
/*  136:     */       catch (SystemException localSystemException)
/*  137:     */       {
/*  138:  56 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  139:     */       }
/*  140:     */       finally
/*  141:     */       {
/*  142:  60 */         if (local_Request != null) {
/*  143:  62 */           local_Request.close();
/*  144:     */         }
/*  145:  64 */         localLocalStack.setArgsOnLocal(bool);
/*  146:     */       }
/*  147:     */     }
/*  148:     */   }
/*  149:     */   
/*  150:     */   public int addCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
/*  151:     */     throws java.rmi.RemoteException
/*  152:     */   {
/*  153:  73 */     for (int i = 1;; i++)
/*  154:     */     {
/*  155:  75 */       _Request local_Request = null;
/*  156:  76 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  157:  77 */       boolean bool1 = false;
/*  158:  78 */       LocalFrame localLocalFrame = null;
/*  159:  79 */       boolean bool2 = false;
/*  160:     */       try
/*  161:     */       {
/*  162:  82 */         local_Request = __request("addCustomers");
/*  163:  83 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  164:  84 */         bool2 = localLocalStack.isArgsOnLocal();
/*  165:  85 */         localLocalStack.setArgsOnLocal(bool1);
/*  166:  87 */         if (bool1)
/*  167:     */         {
/*  168:  89 */           localLocalFrame = new LocalFrame(1);
/*  169:  90 */           localLocalStack.push(localLocalFrame);
/*  170:     */         }
/*  171:     */         OutputStream localOutputStream;
/*  172:  92 */         if (!bool1)
/*  173:     */         {
/*  174:  94 */           localOutputStream = local_Request.getOutputStream();
/*  175:  95 */           if (local_Request.isRMI()) {
/*  176:  95 */             local_Request.write_value(paramArrayOfCustomerInfo, new CustomerInfo[0].getClass());
/*  177:     */           } else {
/*  178:  95 */             CustomerInfoSeqHelper.write(localOutputStream, paramArrayOfCustomerInfo);
/*  179:     */           }
/*  180:     */         }
/*  181:     */         else
/*  182:     */         {
/*  183:  99 */           localOutputStream = local_Request.getOutputStream();
/*  184: 100 */           localLocalFrame.add(paramArrayOfCustomerInfo);
/*  185:     */         }
/*  186: 102 */         local_Request.invoke();
/*  187:     */         int j;
/*  188: 103 */         if (bool1)
/*  189:     */         {
/*  190: 106 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  191: 107 */           return k;
/*  192:     */         }
/*  193: 109 */         InputStream localInputStream = local_Request.getInputStream();
/*  194:     */         
/*  195: 111 */         int m = localInputStream.read_long();
/*  196: 112 */         return m;
/*  197:     */       }
/*  198:     */       catch (TRANSIENT localTRANSIENT)
/*  199:     */       {
/*  200: 116 */         if (i == 10) {
/*  201: 118 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  202:     */         }
/*  203:     */       }
/*  204:     */       catch (UserException localUserException)
/*  205:     */       {
/*  206: 123 */         local_Request.isRMI();
/*  207:     */         
/*  208:     */ 
/*  209: 126 */         throw new java.rmi.RemoteException(localUserException.type);
/*  210:     */       }
/*  211:     */       catch (SystemException localSystemException)
/*  212:     */       {
/*  213: 130 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  214:     */       }
/*  215:     */       finally
/*  216:     */       {
/*  217: 134 */         if (local_Request != null) {
/*  218: 136 */           local_Request.close();
/*  219:     */         }
/*  220: 138 */         if (bool1) {
/*  221: 139 */           localLocalStack.pop(localLocalFrame);
/*  222:     */         }
/*  223: 140 */         localLocalStack.setArgsOnLocal(bool2);
/*  224:     */       }
/*  225:     */     }
/*  226:     */   }
/*  227:     */   
/*  228:     */   public int modifyCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
/*  229:     */     throws java.rmi.RemoteException
/*  230:     */   {
/*  231: 149 */     for (int i = 1;; i++)
/*  232:     */     {
/*  233: 151 */       _Request local_Request = null;
/*  234: 152 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  235: 153 */       boolean bool1 = false;
/*  236: 154 */       LocalFrame localLocalFrame = null;
/*  237: 155 */       boolean bool2 = false;
/*  238:     */       try
/*  239:     */       {
/*  240: 158 */         local_Request = __request("modifyCustomers");
/*  241: 159 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  242: 160 */         bool2 = localLocalStack.isArgsOnLocal();
/*  243: 161 */         localLocalStack.setArgsOnLocal(bool1);
/*  244: 163 */         if (bool1)
/*  245:     */         {
/*  246: 165 */           localLocalFrame = new LocalFrame(1);
/*  247: 166 */           localLocalStack.push(localLocalFrame);
/*  248:     */         }
/*  249:     */         OutputStream localOutputStream;
/*  250: 168 */         if (!bool1)
/*  251:     */         {
/*  252: 170 */           localOutputStream = local_Request.getOutputStream();
/*  253: 171 */           if (local_Request.isRMI()) {
/*  254: 171 */             local_Request.write_value(paramArrayOfCustomerInfo, new CustomerInfo[0].getClass());
/*  255:     */           } else {
/*  256: 171 */             CustomerInfoSeqHelper.write(localOutputStream, paramArrayOfCustomerInfo);
/*  257:     */           }
/*  258:     */         }
/*  259:     */         else
/*  260:     */         {
/*  261: 175 */           localOutputStream = local_Request.getOutputStream();
/*  262: 176 */           localLocalFrame.add(paramArrayOfCustomerInfo);
/*  263:     */         }
/*  264: 178 */         local_Request.invoke();
/*  265:     */         int j;
/*  266: 179 */         if (bool1)
/*  267:     */         {
/*  268: 182 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  269: 183 */           return k;
/*  270:     */         }
/*  271: 185 */         InputStream localInputStream = local_Request.getInputStream();
/*  272:     */         
/*  273: 187 */         int m = localInputStream.read_long();
/*  274: 188 */         return m;
/*  275:     */       }
/*  276:     */       catch (TRANSIENT localTRANSIENT)
/*  277:     */       {
/*  278: 192 */         if (i == 10) {
/*  279: 194 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  280:     */         }
/*  281:     */       }
/*  282:     */       catch (UserException localUserException)
/*  283:     */       {
/*  284: 199 */         local_Request.isRMI();
/*  285:     */         
/*  286:     */ 
/*  287: 202 */         throw new java.rmi.RemoteException(localUserException.type);
/*  288:     */       }
/*  289:     */       catch (SystemException localSystemException)
/*  290:     */       {
/*  291: 206 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  292:     */       }
/*  293:     */       finally
/*  294:     */       {
/*  295: 210 */         if (local_Request != null) {
/*  296: 212 */           local_Request.close();
/*  297:     */         }
/*  298: 214 */         if (bool1) {
/*  299: 215 */           localLocalStack.pop(localLocalFrame);
/*  300:     */         }
/*  301: 216 */         localLocalStack.setArgsOnLocal(bool2);
/*  302:     */       }
/*  303:     */     }
/*  304:     */   }
/*  305:     */   
/*  306:     */   public int deleteCustomers(String[] paramArrayOfString)
/*  307:     */     throws java.rmi.RemoteException
/*  308:     */   {
/*  309: 225 */     for (int i = 1;; i++)
/*  310:     */     {
/*  311: 227 */       _Request local_Request = null;
/*  312: 228 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  313: 229 */       boolean bool1 = false;
/*  314: 230 */       LocalFrame localLocalFrame = null;
/*  315: 231 */       boolean bool2 = false;
/*  316:     */       try
/*  317:     */       {
/*  318: 234 */         local_Request = __request("deleteCustomers__StringSeq", "deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue");
/*  319: 235 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  320: 236 */         bool2 = localLocalStack.isArgsOnLocal();
/*  321: 237 */         localLocalStack.setArgsOnLocal(bool1);
/*  322: 239 */         if (bool1)
/*  323:     */         {
/*  324: 241 */           localLocalFrame = new LocalFrame(1);
/*  325: 242 */           localLocalStack.push(localLocalFrame);
/*  326:     */         }
/*  327:     */         OutputStream localOutputStream;
/*  328: 244 */         if (!bool1)
/*  329:     */         {
/*  330: 246 */           localOutputStream = local_Request.getOutputStream();
/*  331: 247 */           if (local_Request.isRMI()) {
/*  332: 247 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*  333:     */           } else {
/*  334: 247 */             StringSeqHelper.write(localOutputStream, paramArrayOfString);
/*  335:     */           }
/*  336:     */         }
/*  337:     */         else
/*  338:     */         {
/*  339: 251 */           localOutputStream = local_Request.getOutputStream();
/*  340: 252 */           localLocalFrame.add(paramArrayOfString);
/*  341:     */         }
/*  342: 254 */         local_Request.invoke();
/*  343:     */         int j;
/*  344: 255 */         if (bool1)
/*  345:     */         {
/*  346: 258 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  347: 259 */           return k;
/*  348:     */         }
/*  349: 261 */         InputStream localInputStream = local_Request.getInputStream();
/*  350:     */         
/*  351: 263 */         int m = localInputStream.read_long();
/*  352: 264 */         return m;
/*  353:     */       }
/*  354:     */       catch (TRANSIENT localTRANSIENT)
/*  355:     */       {
/*  356: 268 */         if (i == 10) {
/*  357: 270 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  358:     */         }
/*  359:     */       }
/*  360:     */       catch (UserException localUserException)
/*  361:     */       {
/*  362: 275 */         local_Request.isRMI();
/*  363:     */         
/*  364:     */ 
/*  365: 278 */         throw new java.rmi.RemoteException(localUserException.type);
/*  366:     */       }
/*  367:     */       catch (SystemException localSystemException)
/*  368:     */       {
/*  369: 282 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  370:     */       }
/*  371:     */       finally
/*  372:     */       {
/*  373: 286 */         if (local_Request != null) {
/*  374: 288 */           local_Request.close();
/*  375:     */         }
/*  376: 290 */         if (bool1) {
/*  377: 291 */           localLocalStack.pop(localLocalFrame);
/*  378:     */         }
/*  379: 292 */         localLocalStack.setArgsOnLocal(bool2);
/*  380:     */       }
/*  381:     */     }
/*  382:     */   }
/*  383:     */   
/*  384:     */   public int deleteCustomers(String[] paramArrayOfString, int paramInt)
/*  385:     */     throws java.rmi.RemoteException
/*  386:     */   {
/*  387: 302 */     for (int i = 1;; i++)
/*  388:     */     {
/*  389: 304 */       _Request local_Request = null;
/*  390: 305 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  391: 306 */       boolean bool1 = false;
/*  392: 307 */       LocalFrame localLocalFrame = null;
/*  393: 308 */       boolean bool2 = false;
/*  394:     */       try
/*  395:     */       {
/*  396: 311 */         local_Request = __request("deleteCustomers__StringSeq__long", "deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long");
/*  397: 312 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  398: 313 */         bool2 = localLocalStack.isArgsOnLocal();
/*  399: 314 */         localLocalStack.setArgsOnLocal(bool1);
/*  400: 316 */         if (bool1)
/*  401:     */         {
/*  402: 318 */           localLocalFrame = new LocalFrame(2);
/*  403: 319 */           localLocalStack.push(localLocalFrame);
/*  404:     */         }
/*  405:     */         OutputStream localOutputStream;
/*  406: 321 */         if (!bool1)
/*  407:     */         {
/*  408: 323 */           localOutputStream = local_Request.getOutputStream();
/*  409: 324 */           if (local_Request.isRMI()) {
/*  410: 324 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*  411:     */           } else {
/*  412: 324 */             StringSeqHelper.write(localOutputStream, paramArrayOfString);
/*  413:     */           }
/*  414: 325 */           localOutputStream.write_long(paramInt);
/*  415:     */         }
/*  416:     */         else
/*  417:     */         {
/*  418: 329 */           localOutputStream = local_Request.getOutputStream();
/*  419: 330 */           localLocalFrame.add(paramArrayOfString);
/*  420: 331 */           localLocalFrame.add(paramInt);
/*  421:     */         }
/*  422: 333 */         local_Request.invoke();
/*  423:     */         int j;
/*  424: 334 */         if (bool1)
/*  425:     */         {
/*  426: 337 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  427: 338 */           return k;
/*  428:     */         }
/*  429: 340 */         InputStream localInputStream = local_Request.getInputStream();
/*  430:     */         
/*  431: 342 */         int m = localInputStream.read_long();
/*  432: 343 */         return m;
/*  433:     */       }
/*  434:     */       catch (TRANSIENT localTRANSIENT)
/*  435:     */       {
/*  436: 347 */         if (i == 10) {
/*  437: 349 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  438:     */         }
/*  439:     */       }
/*  440:     */       catch (UserException localUserException)
/*  441:     */       {
/*  442: 354 */         local_Request.isRMI();
/*  443:     */         
/*  444:     */ 
/*  445: 357 */         throw new java.rmi.RemoteException(localUserException.type);
/*  446:     */       }
/*  447:     */       catch (SystemException localSystemException)
/*  448:     */       {
/*  449: 361 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  450:     */       }
/*  451:     */       finally
/*  452:     */       {
/*  453: 365 */         if (local_Request != null) {
/*  454: 367 */           local_Request.close();
/*  455:     */         }
/*  456: 369 */         if (bool1) {
/*  457: 370 */           localLocalStack.pop(localLocalFrame);
/*  458:     */         }
/*  459: 371 */         localLocalStack.setArgsOnLocal(bool2);
/*  460:     */       }
/*  461:     */     }
/*  462:     */   }
/*  463:     */   
/*  464:     */   public int deactivateCustomers(String[] paramArrayOfString)
/*  465:     */     throws java.rmi.RemoteException
/*  466:     */   {
/*  467: 380 */     for (int i = 1;; i++)
/*  468:     */     {
/*  469: 382 */       _Request local_Request = null;
/*  470: 383 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  471: 384 */       boolean bool1 = false;
/*  472: 385 */       LocalFrame localLocalFrame = null;
/*  473: 386 */       boolean bool2 = false;
/*  474:     */       try
/*  475:     */       {
/*  476: 389 */         local_Request = __request("deactivateCustomers");
/*  477: 390 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  478: 391 */         bool2 = localLocalStack.isArgsOnLocal();
/*  479: 392 */         localLocalStack.setArgsOnLocal(bool1);
/*  480: 394 */         if (bool1)
/*  481:     */         {
/*  482: 396 */           localLocalFrame = new LocalFrame(1);
/*  483: 397 */           localLocalStack.push(localLocalFrame);
/*  484:     */         }
/*  485:     */         OutputStream localOutputStream;
/*  486: 399 */         if (!bool1)
/*  487:     */         {
/*  488: 401 */           localOutputStream = local_Request.getOutputStream();
/*  489: 402 */           if (local_Request.isRMI()) {
/*  490: 402 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*  491:     */           } else {
/*  492: 402 */             StringSeqHelper.write(localOutputStream, paramArrayOfString);
/*  493:     */           }
/*  494:     */         }
/*  495:     */         else
/*  496:     */         {
/*  497: 406 */           localOutputStream = local_Request.getOutputStream();
/*  498: 407 */           localLocalFrame.add(paramArrayOfString);
/*  499:     */         }
/*  500: 409 */         local_Request.invoke();
/*  501:     */         int j;
/*  502: 410 */         if (bool1)
/*  503:     */         {
/*  504: 413 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  505: 414 */           return k;
/*  506:     */         }
/*  507: 416 */         InputStream localInputStream = local_Request.getInputStream();
/*  508:     */         
/*  509: 418 */         int m = localInputStream.read_long();
/*  510: 419 */         return m;
/*  511:     */       }
/*  512:     */       catch (TRANSIENT localTRANSIENT)
/*  513:     */       {
/*  514: 423 */         if (i == 10) {
/*  515: 425 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  516:     */         }
/*  517:     */       }
/*  518:     */       catch (UserException localUserException)
/*  519:     */       {
/*  520: 430 */         local_Request.isRMI();
/*  521:     */         
/*  522:     */ 
/*  523: 433 */         throw new java.rmi.RemoteException(localUserException.type);
/*  524:     */       }
/*  525:     */       catch (SystemException localSystemException)
/*  526:     */       {
/*  527: 437 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  528:     */       }
/*  529:     */       finally
/*  530:     */       {
/*  531: 441 */         if (local_Request != null) {
/*  532: 443 */           local_Request.close();
/*  533:     */         }
/*  534: 445 */         if (bool1) {
/*  535: 446 */           localLocalStack.pop(localLocalFrame);
/*  536:     */         }
/*  537: 447 */         localLocalStack.setArgsOnLocal(bool2);
/*  538:     */       }
/*  539:     */     }
/*  540:     */   }
/*  541:     */   
/*  542:     */   public int activateCustomers(String[] paramArrayOfString)
/*  543:     */     throws java.rmi.RemoteException
/*  544:     */   {
/*  545: 456 */     for (int i = 1;; i++)
/*  546:     */     {
/*  547: 458 */       _Request local_Request = null;
/*  548: 459 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  549: 460 */       boolean bool1 = false;
/*  550: 461 */       LocalFrame localLocalFrame = null;
/*  551: 462 */       boolean bool2 = false;
/*  552:     */       try
/*  553:     */       {
/*  554: 465 */         local_Request = __request("activateCustomers");
/*  555: 466 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  556: 467 */         bool2 = localLocalStack.isArgsOnLocal();
/*  557: 468 */         localLocalStack.setArgsOnLocal(bool1);
/*  558: 470 */         if (bool1)
/*  559:     */         {
/*  560: 472 */           localLocalFrame = new LocalFrame(1);
/*  561: 473 */           localLocalStack.push(localLocalFrame);
/*  562:     */         }
/*  563:     */         OutputStream localOutputStream;
/*  564: 475 */         if (!bool1)
/*  565:     */         {
/*  566: 477 */           localOutputStream = local_Request.getOutputStream();
/*  567: 478 */           if (local_Request.isRMI()) {
/*  568: 478 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*  569:     */           } else {
/*  570: 478 */             StringSeqHelper.write(localOutputStream, paramArrayOfString);
/*  571:     */           }
/*  572:     */         }
/*  573:     */         else
/*  574:     */         {
/*  575: 482 */           localOutputStream = local_Request.getOutputStream();
/*  576: 483 */           localLocalFrame.add(paramArrayOfString);
/*  577:     */         }
/*  578: 485 */         local_Request.invoke();
/*  579:     */         int j;
/*  580: 486 */         if (bool1)
/*  581:     */         {
/*  582: 489 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  583: 490 */           return k;
/*  584:     */         }
/*  585: 492 */         InputStream localInputStream = local_Request.getInputStream();
/*  586:     */         
/*  587: 494 */         int m = localInputStream.read_long();
/*  588: 495 */         return m;
/*  589:     */       }
/*  590:     */       catch (TRANSIENT localTRANSIENT)
/*  591:     */       {
/*  592: 499 */         if (i == 10) {
/*  593: 501 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  594:     */         }
/*  595:     */       }
/*  596:     */       catch (UserException localUserException)
/*  597:     */       {
/*  598: 506 */         local_Request.isRMI();
/*  599:     */         
/*  600:     */ 
/*  601: 509 */         throw new java.rmi.RemoteException(localUserException.type);
/*  602:     */       }
/*  603:     */       catch (SystemException localSystemException)
/*  604:     */       {
/*  605: 513 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  606:     */       }
/*  607:     */       finally
/*  608:     */       {
/*  609: 517 */         if (local_Request != null) {
/*  610: 519 */           local_Request.close();
/*  611:     */         }
/*  612: 521 */         if (bool1) {
/*  613: 522 */           localLocalStack.pop(localLocalFrame);
/*  614:     */         }
/*  615: 523 */         localLocalStack.setArgsOnLocal(bool2);
/*  616:     */       }
/*  617:     */     }
/*  618:     */   }
/*  619:     */   
/*  620:     */   public CustomerInfo[] getCustomersInfo(String[] paramArrayOfString)
/*  621:     */     throws java.rmi.RemoteException
/*  622:     */   {
/*  623: 532 */     for (int i = 1;; i++)
/*  624:     */     {
/*  625: 534 */       _Request local_Request = null;
/*  626: 535 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  627: 536 */       boolean bool1 = false;
/*  628: 537 */       LocalFrame localLocalFrame = null;
/*  629: 538 */       boolean bool2 = false;
/*  630:     */       try
/*  631:     */       {
/*  632: 541 */         local_Request = __request("getCustomersInfo");
/*  633: 542 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  634: 543 */         bool2 = localLocalStack.isArgsOnLocal();
/*  635: 544 */         localLocalStack.setArgsOnLocal(bool1);
/*  636: 546 */         if (bool1)
/*  637:     */         {
/*  638: 548 */           localLocalFrame = new LocalFrame(1);
/*  639: 549 */           localLocalStack.push(localLocalFrame);
/*  640:     */         }
/*  641: 551 */         if (!bool1)
/*  642:     */         {
/*  643: 553 */           localObject4 = local_Request.getOutputStream();
/*  644: 554 */           if (local_Request.isRMI()) {
/*  645: 554 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*  646:     */           } else {
/*  647: 554 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/*  648:     */           }
/*  649:     */         }
/*  650:     */         else
/*  651:     */         {
/*  652: 558 */           localObject4 = local_Request.getOutputStream();
/*  653: 559 */           localLocalFrame.add(paramArrayOfString);
/*  654:     */         }
/*  655: 561 */         local_Request.invoke();
/*  656:     */         Object localObject5;
/*  657:     */         Object localObject1;
/*  658: 562 */         if (bool1)
/*  659:     */         {
/*  660: 564 */           localObject4 = null;
/*  661: 565 */           localObject5 = localLocalFrame.getResult();
/*  662: 566 */           if (localObject5 != null) {
/*  663: 568 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*  664:     */           }
/*  665: 570 */           return localObject4;
/*  666:     */         }
/*  667: 572 */         Object localObject4 = local_Request.getInputStream();
/*  668: 574 */         if (local_Request.isRMI()) {
/*  669: 574 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*  670:     */         } else {
/*  671: 574 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*  672:     */         }
/*  673: 575 */         return localObject5;
/*  674:     */       }
/*  675:     */       catch (TRANSIENT localTRANSIENT)
/*  676:     */       {
/*  677: 579 */         if (i == 10) {
/*  678: 581 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  679:     */         }
/*  680:     */       }
/*  681:     */       catch (UserException localUserException)
/*  682:     */       {
/*  683: 586 */         local_Request.isRMI();
/*  684:     */         
/*  685:     */ 
/*  686: 589 */         throw new java.rmi.RemoteException(localUserException.type);
/*  687:     */       }
/*  688:     */       catch (SystemException localSystemException)
/*  689:     */       {
/*  690: 593 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  691:     */       }
/*  692:     */       finally
/*  693:     */       {
/*  694: 597 */         if (local_Request != null) {
/*  695: 599 */           local_Request.close();
/*  696:     */         }
/*  697: 601 */         if (bool1) {
/*  698: 602 */           localLocalStack.pop(localLocalFrame);
/*  699:     */         }
/*  700: 603 */         localLocalStack.setArgsOnLocal(bool2);
/*  701:     */       }
/*  702:     */     }
/*  703:     */   }
/*  704:     */   
/*  705:     */   public CustomerInfo[] getCustomerByType(String paramString)
/*  706:     */     throws java.rmi.RemoteException
/*  707:     */   {
/*  708: 612 */     for (int i = 1;; i++)
/*  709:     */     {
/*  710: 614 */       _Request local_Request = null;
/*  711: 615 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  712: 616 */       boolean bool1 = false;
/*  713: 617 */       LocalFrame localLocalFrame = null;
/*  714: 618 */       boolean bool2 = false;
/*  715:     */       try
/*  716:     */       {
/*  717: 621 */         local_Request = __request("getCustomerByType");
/*  718: 622 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  719: 623 */         bool2 = localLocalStack.isArgsOnLocal();
/*  720: 624 */         localLocalStack.setArgsOnLocal(bool1);
/*  721: 626 */         if (bool1)
/*  722:     */         {
/*  723: 628 */           localLocalFrame = new LocalFrame(1);
/*  724: 629 */           localLocalStack.push(localLocalFrame);
/*  725:     */         }
/*  726: 631 */         if (!bool1)
/*  727:     */         {
/*  728: 633 */           localObject4 = local_Request.getOutputStream();
/*  729: 634 */           local_Request.write_string(paramString);
/*  730:     */         }
/*  731:     */         else
/*  732:     */         {
/*  733: 638 */           localObject4 = local_Request.getOutputStream();
/*  734: 639 */           localLocalFrame.add(paramString);
/*  735:     */         }
/*  736: 641 */         local_Request.invoke();
/*  737:     */         Object localObject5;
/*  738:     */         Object localObject1;
/*  739: 642 */         if (bool1)
/*  740:     */         {
/*  741: 644 */           localObject4 = null;
/*  742: 645 */           localObject5 = localLocalFrame.getResult();
/*  743: 646 */           if (localObject5 != null) {
/*  744: 648 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*  745:     */           }
/*  746: 650 */           return localObject4;
/*  747:     */         }
/*  748: 652 */         Object localObject4 = local_Request.getInputStream();
/*  749: 654 */         if (local_Request.isRMI()) {
/*  750: 654 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*  751:     */         } else {
/*  752: 654 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*  753:     */         }
/*  754: 655 */         return localObject5;
/*  755:     */       }
/*  756:     */       catch (TRANSIENT localTRANSIENT)
/*  757:     */       {
/*  758: 659 */         if (i == 10) {
/*  759: 661 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  760:     */         }
/*  761:     */       }
/*  762:     */       catch (UserException localUserException)
/*  763:     */       {
/*  764: 666 */         local_Request.isRMI();
/*  765:     */         
/*  766:     */ 
/*  767: 669 */         throw new java.rmi.RemoteException(localUserException.type);
/*  768:     */       }
/*  769:     */       catch (SystemException localSystemException)
/*  770:     */       {
/*  771: 673 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  772:     */       }
/*  773:     */       finally
/*  774:     */       {
/*  775: 677 */         if (local_Request != null) {
/*  776: 679 */           local_Request.close();
/*  777:     */         }
/*  778: 681 */         if (bool1) {
/*  779: 682 */           localLocalStack.pop(localLocalFrame);
/*  780:     */         }
/*  781: 683 */         localLocalStack.setArgsOnLocal(bool2);
/*  782:     */       }
/*  783:     */     }
/*  784:     */   }
/*  785:     */   
/*  786:     */   public CustomerInfo[] getCustomerByFI(String paramString)
/*  787:     */     throws java.rmi.RemoteException
/*  788:     */   {
/*  789: 692 */     for (int i = 1;; i++)
/*  790:     */     {
/*  791: 694 */       _Request local_Request = null;
/*  792: 695 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  793: 696 */       boolean bool1 = false;
/*  794: 697 */       LocalFrame localLocalFrame = null;
/*  795: 698 */       boolean bool2 = false;
/*  796:     */       try
/*  797:     */       {
/*  798: 701 */         local_Request = __request("getCustomerByFI");
/*  799: 702 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  800: 703 */         bool2 = localLocalStack.isArgsOnLocal();
/*  801: 704 */         localLocalStack.setArgsOnLocal(bool1);
/*  802: 706 */         if (bool1)
/*  803:     */         {
/*  804: 708 */           localLocalFrame = new LocalFrame(1);
/*  805: 709 */           localLocalStack.push(localLocalFrame);
/*  806:     */         }
/*  807: 711 */         if (!bool1)
/*  808:     */         {
/*  809: 713 */           localObject4 = local_Request.getOutputStream();
/*  810: 714 */           local_Request.write_string(paramString);
/*  811:     */         }
/*  812:     */         else
/*  813:     */         {
/*  814: 718 */           localObject4 = local_Request.getOutputStream();
/*  815: 719 */           localLocalFrame.add(paramString);
/*  816:     */         }
/*  817: 721 */         local_Request.invoke();
/*  818:     */         Object localObject5;
/*  819:     */         Object localObject1;
/*  820: 722 */         if (bool1)
/*  821:     */         {
/*  822: 724 */           localObject4 = null;
/*  823: 725 */           localObject5 = localLocalFrame.getResult();
/*  824: 726 */           if (localObject5 != null) {
/*  825: 728 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*  826:     */           }
/*  827: 730 */           return localObject4;
/*  828:     */         }
/*  829: 732 */         Object localObject4 = local_Request.getInputStream();
/*  830: 734 */         if (local_Request.isRMI()) {
/*  831: 734 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*  832:     */         } else {
/*  833: 734 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*  834:     */         }
/*  835: 735 */         return localObject5;
/*  836:     */       }
/*  837:     */       catch (TRANSIENT localTRANSIENT)
/*  838:     */       {
/*  839: 739 */         if (i == 10) {
/*  840: 741 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  841:     */         }
/*  842:     */       }
/*  843:     */       catch (UserException localUserException)
/*  844:     */       {
/*  845: 746 */         local_Request.isRMI();
/*  846:     */         
/*  847:     */ 
/*  848: 749 */         throw new java.rmi.RemoteException(localUserException.type);
/*  849:     */       }
/*  850:     */       catch (SystemException localSystemException)
/*  851:     */       {
/*  852: 753 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  853:     */       }
/*  854:     */       finally
/*  855:     */       {
/*  856: 757 */         if (local_Request != null) {
/*  857: 759 */           local_Request.close();
/*  858:     */         }
/*  859: 761 */         if (bool1) {
/*  860: 762 */           localLocalStack.pop(localLocalFrame);
/*  861:     */         }
/*  862: 763 */         localLocalStack.setArgsOnLocal(bool2);
/*  863:     */       }
/*  864:     */     }
/*  865:     */   }
/*  866:     */   
/*  867:     */   public CustomerInfo[] getCustomerByCategory(String paramString)
/*  868:     */     throws java.rmi.RemoteException
/*  869:     */   {
/*  870: 772 */     for (int i = 1;; i++)
/*  871:     */     {
/*  872: 774 */       _Request local_Request = null;
/*  873: 775 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  874: 776 */       boolean bool1 = false;
/*  875: 777 */       LocalFrame localLocalFrame = null;
/*  876: 778 */       boolean bool2 = false;
/*  877:     */       try
/*  878:     */       {
/*  879: 781 */         local_Request = __request("getCustomerByCategory");
/*  880: 782 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  881: 783 */         bool2 = localLocalStack.isArgsOnLocal();
/*  882: 784 */         localLocalStack.setArgsOnLocal(bool1);
/*  883: 786 */         if (bool1)
/*  884:     */         {
/*  885: 788 */           localLocalFrame = new LocalFrame(1);
/*  886: 789 */           localLocalStack.push(localLocalFrame);
/*  887:     */         }
/*  888: 791 */         if (!bool1)
/*  889:     */         {
/*  890: 793 */           localObject4 = local_Request.getOutputStream();
/*  891: 794 */           local_Request.write_string(paramString);
/*  892:     */         }
/*  893:     */         else
/*  894:     */         {
/*  895: 798 */           localObject4 = local_Request.getOutputStream();
/*  896: 799 */           localLocalFrame.add(paramString);
/*  897:     */         }
/*  898: 801 */         local_Request.invoke();
/*  899:     */         Object localObject5;
/*  900:     */         Object localObject1;
/*  901: 802 */         if (bool1)
/*  902:     */         {
/*  903: 804 */           localObject4 = null;
/*  904: 805 */           localObject5 = localLocalFrame.getResult();
/*  905: 806 */           if (localObject5 != null) {
/*  906: 808 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*  907:     */           }
/*  908: 810 */           return localObject4;
/*  909:     */         }
/*  910: 812 */         Object localObject4 = local_Request.getInputStream();
/*  911: 814 */         if (local_Request.isRMI()) {
/*  912: 814 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*  913:     */         } else {
/*  914: 814 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*  915:     */         }
/*  916: 815 */         return localObject5;
/*  917:     */       }
/*  918:     */       catch (TRANSIENT localTRANSIENT)
/*  919:     */       {
/*  920: 819 */         if (i == 10) {
/*  921: 821 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  922:     */         }
/*  923:     */       }
/*  924:     */       catch (UserException localUserException)
/*  925:     */       {
/*  926: 826 */         local_Request.isRMI();
/*  927:     */         
/*  928:     */ 
/*  929: 829 */         throw new java.rmi.RemoteException(localUserException.type);
/*  930:     */       }
/*  931:     */       catch (SystemException localSystemException)
/*  932:     */       {
/*  933: 833 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  934:     */       }
/*  935:     */       finally
/*  936:     */       {
/*  937: 837 */         if (local_Request != null) {
/*  938: 839 */           local_Request.close();
/*  939:     */         }
/*  940: 841 */         if (bool1) {
/*  941: 842 */           localLocalStack.pop(localLocalFrame);
/*  942:     */         }
/*  943: 843 */         localLocalStack.setArgsOnLocal(bool2);
/*  944:     */       }
/*  945:     */     }
/*  946:     */   }
/*  947:     */   
/*  948:     */   public CustomerInfo[] getCustomerByGroup(String paramString)
/*  949:     */     throws java.rmi.RemoteException
/*  950:     */   {
/*  951: 852 */     for (int i = 1;; i++)
/*  952:     */     {
/*  953: 854 */       _Request local_Request = null;
/*  954: 855 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  955: 856 */       boolean bool1 = false;
/*  956: 857 */       LocalFrame localLocalFrame = null;
/*  957: 858 */       boolean bool2 = false;
/*  958:     */       try
/*  959:     */       {
/*  960: 861 */         local_Request = __request("getCustomerByGroup");
/*  961: 862 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  962: 863 */         bool2 = localLocalStack.isArgsOnLocal();
/*  963: 864 */         localLocalStack.setArgsOnLocal(bool1);
/*  964: 866 */         if (bool1)
/*  965:     */         {
/*  966: 868 */           localLocalFrame = new LocalFrame(1);
/*  967: 869 */           localLocalStack.push(localLocalFrame);
/*  968:     */         }
/*  969: 871 */         if (!bool1)
/*  970:     */         {
/*  971: 873 */           localObject4 = local_Request.getOutputStream();
/*  972: 874 */           local_Request.write_string(paramString);
/*  973:     */         }
/*  974:     */         else
/*  975:     */         {
/*  976: 878 */           localObject4 = local_Request.getOutputStream();
/*  977: 879 */           localLocalFrame.add(paramString);
/*  978:     */         }
/*  979: 881 */         local_Request.invoke();
/*  980:     */         Object localObject5;
/*  981:     */         Object localObject1;
/*  982: 882 */         if (bool1)
/*  983:     */         {
/*  984: 884 */           localObject4 = null;
/*  985: 885 */           localObject5 = localLocalFrame.getResult();
/*  986: 886 */           if (localObject5 != null) {
/*  987: 888 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*  988:     */           }
/*  989: 890 */           return localObject4;
/*  990:     */         }
/*  991: 892 */         Object localObject4 = local_Request.getInputStream();
/*  992: 894 */         if (local_Request.isRMI()) {
/*  993: 894 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*  994:     */         } else {
/*  995: 894 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*  996:     */         }
/*  997: 895 */         return localObject5;
/*  998:     */       }
/*  999:     */       catch (TRANSIENT localTRANSIENT)
/* 1000:     */       {
/* 1001: 899 */         if (i == 10) {
/* 1002: 901 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1003:     */         }
/* 1004:     */       }
/* 1005:     */       catch (UserException localUserException)
/* 1006:     */       {
/* 1007: 906 */         local_Request.isRMI();
/* 1008:     */         
/* 1009:     */ 
/* 1010: 909 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1011:     */       }
/* 1012:     */       catch (SystemException localSystemException)
/* 1013:     */       {
/* 1014: 913 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1015:     */       }
/* 1016:     */       finally
/* 1017:     */       {
/* 1018: 917 */         if (local_Request != null) {
/* 1019: 919 */           local_Request.close();
/* 1020:     */         }
/* 1021: 921 */         if (bool1) {
/* 1022: 922 */           localLocalStack.pop(localLocalFrame);
/* 1023:     */         }
/* 1024: 923 */         localLocalStack.setArgsOnLocal(bool2);
/* 1025:     */       }
/* 1026:     */     }
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   public CustomerInfo[] getCustomerByTypeAndFI(String paramString1, String paramString2)
/* 1030:     */     throws java.rmi.RemoteException
/* 1031:     */   {
/* 1032: 933 */     for (int i = 1;; i++)
/* 1033:     */     {
/* 1034: 935 */       _Request local_Request = null;
/* 1035: 936 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1036: 937 */       boolean bool1 = false;
/* 1037: 938 */       LocalFrame localLocalFrame = null;
/* 1038: 939 */       boolean bool2 = false;
/* 1039:     */       try
/* 1040:     */       {
/* 1041: 942 */         local_Request = __request("getCustomerByTypeAndFI");
/* 1042: 943 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1043: 944 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1044: 945 */         localLocalStack.setArgsOnLocal(bool1);
/* 1045: 947 */         if (bool1)
/* 1046:     */         {
/* 1047: 949 */           localLocalFrame = new LocalFrame(2);
/* 1048: 950 */           localLocalStack.push(localLocalFrame);
/* 1049:     */         }
/* 1050: 952 */         if (!bool1)
/* 1051:     */         {
/* 1052: 954 */           localObject4 = local_Request.getOutputStream();
/* 1053: 955 */           local_Request.write_string(paramString1);
/* 1054: 956 */           local_Request.write_string(paramString2);
/* 1055:     */         }
/* 1056:     */         else
/* 1057:     */         {
/* 1058: 960 */           localObject4 = local_Request.getOutputStream();
/* 1059: 961 */           localLocalFrame.add(paramString1);
/* 1060: 962 */           localLocalFrame.add(paramString2);
/* 1061:     */         }
/* 1062: 964 */         local_Request.invoke();
/* 1063:     */         Object localObject5;
/* 1064:     */         Object localObject1;
/* 1065: 965 */         if (bool1)
/* 1066:     */         {
/* 1067: 967 */           localObject4 = null;
/* 1068: 968 */           localObject5 = localLocalFrame.getResult();
/* 1069: 969 */           if (localObject5 != null) {
/* 1070: 971 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/* 1071:     */           }
/* 1072: 973 */           return localObject4;
/* 1073:     */         }
/* 1074: 975 */         Object localObject4 = local_Request.getInputStream();
/* 1075: 977 */         if (local_Request.isRMI()) {
/* 1076: 977 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/* 1077:     */         } else {
/* 1078: 977 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/* 1079:     */         }
/* 1080: 978 */         return localObject5;
/* 1081:     */       }
/* 1082:     */       catch (TRANSIENT localTRANSIENT)
/* 1083:     */       {
/* 1084: 982 */         if (i == 10) {
/* 1085: 984 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1086:     */         }
/* 1087:     */       }
/* 1088:     */       catch (UserException localUserException)
/* 1089:     */       {
/* 1090: 989 */         local_Request.isRMI();
/* 1091:     */         
/* 1092:     */ 
/* 1093: 992 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1094:     */       }
/* 1095:     */       catch (SystemException localSystemException)
/* 1096:     */       {
/* 1097: 996 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1098:     */       }
/* 1099:     */       finally
/* 1100:     */       {
/* 1101:1000 */         if (local_Request != null) {
/* 1102:1002 */           local_Request.close();
/* 1103:     */         }
/* 1104:1004 */         if (bool1) {
/* 1105:1005 */           localLocalStack.pop(localLocalFrame);
/* 1106:     */         }
/* 1107:1006 */         localLocalStack.setArgsOnLocal(bool2);
/* 1108:     */       }
/* 1109:     */     }
/* 1110:     */   }
/* 1111:     */   
/* 1112:     */   public CustomerInfo[] getCustomerByCategoryAndFI(String paramString1, String paramString2)
/* 1113:     */     throws java.rmi.RemoteException
/* 1114:     */   {
/* 1115:1016 */     for (int i = 1;; i++)
/* 1116:     */     {
/* 1117:1018 */       _Request local_Request = null;
/* 1118:1019 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1119:1020 */       boolean bool1 = false;
/* 1120:1021 */       LocalFrame localLocalFrame = null;
/* 1121:1022 */       boolean bool2 = false;
/* 1122:     */       try
/* 1123:     */       {
/* 1124:1025 */         local_Request = __request("getCustomerByCategoryAndFI");
/* 1125:1026 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1126:1027 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1127:1028 */         localLocalStack.setArgsOnLocal(bool1);
/* 1128:1030 */         if (bool1)
/* 1129:     */         {
/* 1130:1032 */           localLocalFrame = new LocalFrame(2);
/* 1131:1033 */           localLocalStack.push(localLocalFrame);
/* 1132:     */         }
/* 1133:1035 */         if (!bool1)
/* 1134:     */         {
/* 1135:1037 */           localObject4 = local_Request.getOutputStream();
/* 1136:1038 */           local_Request.write_string(paramString1);
/* 1137:1039 */           local_Request.write_string(paramString2);
/* 1138:     */         }
/* 1139:     */         else
/* 1140:     */         {
/* 1141:1043 */           localObject4 = local_Request.getOutputStream();
/* 1142:1044 */           localLocalFrame.add(paramString1);
/* 1143:1045 */           localLocalFrame.add(paramString2);
/* 1144:     */         }
/* 1145:1047 */         local_Request.invoke();
/* 1146:     */         Object localObject5;
/* 1147:     */         Object localObject1;
/* 1148:1048 */         if (bool1)
/* 1149:     */         {
/* 1150:1050 */           localObject4 = null;
/* 1151:1051 */           localObject5 = localLocalFrame.getResult();
/* 1152:1052 */           if (localObject5 != null) {
/* 1153:1054 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/* 1154:     */           }
/* 1155:1056 */           return localObject4;
/* 1156:     */         }
/* 1157:1058 */         Object localObject4 = local_Request.getInputStream();
/* 1158:1060 */         if (local_Request.isRMI()) {
/* 1159:1060 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/* 1160:     */         } else {
/* 1161:1060 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/* 1162:     */         }
/* 1163:1061 */         return localObject5;
/* 1164:     */       }
/* 1165:     */       catch (TRANSIENT localTRANSIENT)
/* 1166:     */       {
/* 1167:1065 */         if (i == 10) {
/* 1168:1067 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1169:     */         }
/* 1170:     */       }
/* 1171:     */       catch (UserException localUserException)
/* 1172:     */       {
/* 1173:1072 */         local_Request.isRMI();
/* 1174:     */         
/* 1175:     */ 
/* 1176:1075 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1177:     */       }
/* 1178:     */       catch (SystemException localSystemException)
/* 1179:     */       {
/* 1180:1079 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1181:     */       }
/* 1182:     */       finally
/* 1183:     */       {
/* 1184:1083 */         if (local_Request != null) {
/* 1185:1085 */           local_Request.close();
/* 1186:     */         }
/* 1187:1087 */         if (bool1) {
/* 1188:1088 */           localLocalStack.pop(localLocalFrame);
/* 1189:     */         }
/* 1190:1089 */         localLocalStack.setArgsOnLocal(bool2);
/* 1191:     */       }
/* 1192:     */     }
/* 1193:     */   }
/* 1194:     */   
/* 1195:     */   public CustomerInfo[] getCustomerByGroupAndFI(String paramString1, String paramString2)
/* 1196:     */     throws java.rmi.RemoteException
/* 1197:     */   {
/* 1198:1099 */     for (int i = 1;; i++)
/* 1199:     */     {
/* 1200:1101 */       _Request local_Request = null;
/* 1201:1102 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1202:1103 */       boolean bool1 = false;
/* 1203:1104 */       LocalFrame localLocalFrame = null;
/* 1204:1105 */       boolean bool2 = false;
/* 1205:     */       try
/* 1206:     */       {
/* 1207:1108 */         local_Request = __request("getCustomerByGroupAndFI");
/* 1208:1109 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1209:1110 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1210:1111 */         localLocalStack.setArgsOnLocal(bool1);
/* 1211:1113 */         if (bool1)
/* 1212:     */         {
/* 1213:1115 */           localLocalFrame = new LocalFrame(2);
/* 1214:1116 */           localLocalStack.push(localLocalFrame);
/* 1215:     */         }
/* 1216:1118 */         if (!bool1)
/* 1217:     */         {
/* 1218:1120 */           localObject4 = local_Request.getOutputStream();
/* 1219:1121 */           local_Request.write_string(paramString1);
/* 1220:1122 */           local_Request.write_string(paramString2);
/* 1221:     */         }
/* 1222:     */         else
/* 1223:     */         {
/* 1224:1126 */           localObject4 = local_Request.getOutputStream();
/* 1225:1127 */           localLocalFrame.add(paramString1);
/* 1226:1128 */           localLocalFrame.add(paramString2);
/* 1227:     */         }
/* 1228:1130 */         local_Request.invoke();
/* 1229:     */         Object localObject5;
/* 1230:     */         Object localObject1;
/* 1231:1131 */         if (bool1)
/* 1232:     */         {
/* 1233:1133 */           localObject4 = null;
/* 1234:1134 */           localObject5 = localLocalFrame.getResult();
/* 1235:1135 */           if (localObject5 != null) {
/* 1236:1137 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/* 1237:     */           }
/* 1238:1139 */           return localObject4;
/* 1239:     */         }
/* 1240:1141 */         Object localObject4 = local_Request.getInputStream();
/* 1241:1143 */         if (local_Request.isRMI()) {
/* 1242:1143 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/* 1243:     */         } else {
/* 1244:1143 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/* 1245:     */         }
/* 1246:1144 */         return localObject5;
/* 1247:     */       }
/* 1248:     */       catch (TRANSIENT localTRANSIENT)
/* 1249:     */       {
/* 1250:1148 */         if (i == 10) {
/* 1251:1150 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1252:     */         }
/* 1253:     */       }
/* 1254:     */       catch (UserException localUserException)
/* 1255:     */       {
/* 1256:1155 */         local_Request.isRMI();
/* 1257:     */         
/* 1258:     */ 
/* 1259:1158 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1260:     */       }
/* 1261:     */       catch (SystemException localSystemException)
/* 1262:     */       {
/* 1263:1162 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1264:     */       }
/* 1265:     */       finally
/* 1266:     */       {
/* 1267:1166 */         if (local_Request != null) {
/* 1268:1168 */           local_Request.close();
/* 1269:     */         }
/* 1270:1170 */         if (bool1) {
/* 1271:1171 */           localLocalStack.pop(localLocalFrame);
/* 1272:     */         }
/* 1273:1172 */         localLocalStack.setArgsOnLocal(bool2);
/* 1274:     */       }
/* 1275:     */     }
/* 1276:     */   }
/* 1277:     */   
/* 1278:     */   public PayeeInfo[] getLinkedPayees()
/* 1279:     */     throws java.rmi.RemoteException
/* 1280:     */   {
/* 1281:1180 */     for (int i = 1;; i++)
/* 1282:     */     {
/* 1283:1182 */       _Request local_Request = null;
/* 1284:1183 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1285:1184 */       boolean bool1 = false;
/* 1286:1185 */       LocalFrame localLocalFrame = null;
/* 1287:1186 */       boolean bool2 = false;
/* 1288:     */       try
/* 1289:     */       {
/* 1290:1189 */         local_Request = __request("getLinkedPayees", "_get_linkedPayees");
/* 1291:1190 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1292:1191 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1293:1192 */         localLocalStack.setArgsOnLocal(bool1);
/* 1294:1194 */         if (bool1)
/* 1295:     */         {
/* 1296:1196 */           localLocalFrame = new LocalFrame(0);
/* 1297:1197 */           localLocalStack.push(localLocalFrame);
/* 1298:     */         }
/* 1299:1205 */         local_Request.invoke();
/* 1300:     */         Object localObject5;
/* 1301:     */         Object localObject1;
/* 1302:1206 */         if (bool1)
/* 1303:     */         {
/* 1304:1208 */           localObject4 = null;
/* 1305:1209 */           localObject5 = localLocalFrame.getResult();
/* 1306:1210 */           if (localObject5 != null) {
/* 1307:1212 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 1308:     */           }
/* 1309:1214 */           return localObject4;
/* 1310:     */         }
/* 1311:1216 */         Object localObject4 = local_Request.getInputStream();
/* 1312:1218 */         if (local_Request.isRMI()) {
/* 1313:1218 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 1314:     */         } else {
/* 1315:1218 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 1316:     */         }
/* 1317:1219 */         return localObject5;
/* 1318:     */       }
/* 1319:     */       catch (TRANSIENT localTRANSIENT)
/* 1320:     */       {
/* 1321:1223 */         if (i == 10) {
/* 1322:1225 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1323:     */         }
/* 1324:     */       }
/* 1325:     */       catch (UserException localUserException)
/* 1326:     */       {
/* 1327:1230 */         local_Request.isRMI();
/* 1328:     */         
/* 1329:     */ 
/* 1330:1233 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1331:     */       }
/* 1332:     */       catch (SystemException localSystemException)
/* 1333:     */       {
/* 1334:1237 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1335:     */       }
/* 1336:     */       finally
/* 1337:     */       {
/* 1338:1241 */         if (local_Request != null) {
/* 1339:1243 */           local_Request.close();
/* 1340:     */         }
/* 1341:1245 */         if (bool1) {
/* 1342:1246 */           localLocalStack.pop(localLocalFrame);
/* 1343:     */         }
/* 1344:1247 */         localLocalStack.setArgsOnLocal(bool2);
/* 1345:     */       }
/* 1346:     */     }
/* 1347:     */   }
/* 1348:     */   
/* 1349:     */   public PayeeInfo[] getMostUsedPayees(int paramInt)
/* 1350:     */     throws java.rmi.RemoteException
/* 1351:     */   {
/* 1352:1256 */     for (int i = 1;; i++)
/* 1353:     */     {
/* 1354:1258 */       _Request local_Request = null;
/* 1355:1259 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1356:1260 */       boolean bool1 = false;
/* 1357:1261 */       LocalFrame localLocalFrame = null;
/* 1358:1262 */       boolean bool2 = false;
/* 1359:     */       try
/* 1360:     */       {
/* 1361:1265 */         local_Request = __request("getMostUsedPayees");
/* 1362:1266 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1363:1267 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1364:1268 */         localLocalStack.setArgsOnLocal(bool1);
/* 1365:1270 */         if (bool1)
/* 1366:     */         {
/* 1367:1272 */           localLocalFrame = new LocalFrame(1);
/* 1368:1273 */           localLocalStack.push(localLocalFrame);
/* 1369:     */         }
/* 1370:1275 */         if (!bool1)
/* 1371:     */         {
/* 1372:1277 */           localObject4 = local_Request.getOutputStream();
/* 1373:1278 */           ((OutputStream)localObject4).write_long(paramInt);
/* 1374:     */         }
/* 1375:     */         else
/* 1376:     */         {
/* 1377:1282 */           localObject4 = local_Request.getOutputStream();
/* 1378:1283 */           localLocalFrame.add(paramInt);
/* 1379:     */         }
/* 1380:1285 */         local_Request.invoke();
/* 1381:     */         Object localObject5;
/* 1382:     */         Object localObject1;
/* 1383:1286 */         if (bool1)
/* 1384:     */         {
/* 1385:1288 */           localObject4 = null;
/* 1386:1289 */           localObject5 = localLocalFrame.getResult();
/* 1387:1290 */           if (localObject5 != null) {
/* 1388:1292 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 1389:     */           }
/* 1390:1294 */           return localObject4;
/* 1391:     */         }
/* 1392:1296 */         Object localObject4 = local_Request.getInputStream();
/* 1393:1298 */         if (local_Request.isRMI()) {
/* 1394:1298 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 1395:     */         } else {
/* 1396:1298 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 1397:     */         }
/* 1398:1299 */         return localObject5;
/* 1399:     */       }
/* 1400:     */       catch (TRANSIENT localTRANSIENT)
/* 1401:     */       {
/* 1402:1303 */         if (i == 10) {
/* 1403:1305 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1404:     */         }
/* 1405:     */       }
/* 1406:     */       catch (UserException localUserException)
/* 1407:     */       {
/* 1408:1310 */         local_Request.isRMI();
/* 1409:     */         
/* 1410:     */ 
/* 1411:1313 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1412:     */       }
/* 1413:     */       catch (SystemException localSystemException)
/* 1414:     */       {
/* 1415:1317 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1416:     */       }
/* 1417:     */       finally
/* 1418:     */       {
/* 1419:1321 */         if (local_Request != null) {
/* 1420:1323 */           local_Request.close();
/* 1421:     */         }
/* 1422:1325 */         if (bool1) {
/* 1423:1326 */           localLocalStack.pop(localLocalFrame);
/* 1424:     */         }
/* 1425:1327 */         localLocalStack.setArgsOnLocal(bool2);
/* 1426:     */       }
/* 1427:     */     }
/* 1428:     */   }
/* 1429:     */   
/* 1430:     */   public PayeeInfo[] getPreferredPayees(String paramString)
/* 1431:     */     throws java.rmi.RemoteException
/* 1432:     */   {
/* 1433:1336 */     for (int i = 1;; i++)
/* 1434:     */     {
/* 1435:1338 */       _Request local_Request = null;
/* 1436:1339 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1437:1340 */       boolean bool1 = false;
/* 1438:1341 */       LocalFrame localLocalFrame = null;
/* 1439:1342 */       boolean bool2 = false;
/* 1440:     */       try
/* 1441:     */       {
/* 1442:1345 */         local_Request = __request("getPreferredPayees");
/* 1443:1346 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1444:1347 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1445:1348 */         localLocalStack.setArgsOnLocal(bool1);
/* 1446:1350 */         if (bool1)
/* 1447:     */         {
/* 1448:1352 */           localLocalFrame = new LocalFrame(1);
/* 1449:1353 */           localLocalStack.push(localLocalFrame);
/* 1450:     */         }
/* 1451:1355 */         if (!bool1)
/* 1452:     */         {
/* 1453:1357 */           localObject4 = local_Request.getOutputStream();
/* 1454:1358 */           local_Request.write_string(paramString);
/* 1455:     */         }
/* 1456:     */         else
/* 1457:     */         {
/* 1458:1362 */           localObject4 = local_Request.getOutputStream();
/* 1459:1363 */           localLocalFrame.add(paramString);
/* 1460:     */         }
/* 1461:1365 */         local_Request.invoke();
/* 1462:     */         Object localObject5;
/* 1463:     */         Object localObject1;
/* 1464:1366 */         if (bool1)
/* 1465:     */         {
/* 1466:1368 */           localObject4 = null;
/* 1467:1369 */           localObject5 = localLocalFrame.getResult();
/* 1468:1370 */           if (localObject5 != null) {
/* 1469:1372 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 1470:     */           }
/* 1471:1374 */           return localObject4;
/* 1472:     */         }
/* 1473:1376 */         Object localObject4 = local_Request.getInputStream();
/* 1474:1378 */         if (local_Request.isRMI()) {
/* 1475:1378 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 1476:     */         } else {
/* 1477:1378 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 1478:     */         }
/* 1479:1379 */         return localObject5;
/* 1480:     */       }
/* 1481:     */       catch (TRANSIENT localTRANSIENT)
/* 1482:     */       {
/* 1483:1383 */         if (i == 10) {
/* 1484:1385 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1485:     */         }
/* 1486:     */       }
/* 1487:     */       catch (UserException localUserException)
/* 1488:     */       {
/* 1489:1390 */         local_Request.isRMI();
/* 1490:     */         
/* 1491:     */ 
/* 1492:1393 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1493:     */       }
/* 1494:     */       catch (SystemException localSystemException)
/* 1495:     */       {
/* 1496:1397 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1497:     */       }
/* 1498:     */       finally
/* 1499:     */       {
/* 1500:1401 */         if (local_Request != null) {
/* 1501:1403 */           local_Request.close();
/* 1502:     */         }
/* 1503:1405 */         if (bool1) {
/* 1504:1406 */           localLocalStack.pop(localLocalFrame);
/* 1505:     */         }
/* 1506:1407 */         localLocalStack.setArgsOnLocal(bool2);
/* 1507:     */       }
/* 1508:     */     }
/* 1509:     */   }
/* 1510:     */   
/* 1511:     */   public TypePmtSyncRsV1[] getPendingPmtsByCustomerID(String paramString, int paramInt)
/* 1512:     */     throws java.rmi.RemoteException
/* 1513:     */   {
/* 1514:1417 */     for (int i = 1;; i++)
/* 1515:     */     {
/* 1516:1419 */       _Request local_Request = null;
/* 1517:1420 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1518:1421 */       boolean bool1 = false;
/* 1519:1422 */       LocalFrame localLocalFrame = null;
/* 1520:1423 */       boolean bool2 = false;
/* 1521:     */       try
/* 1522:     */       {
/* 1523:1426 */         local_Request = __request("getPendingPmtsByCustomerID");
/* 1524:1427 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1525:1428 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1526:1429 */         localLocalStack.setArgsOnLocal(bool1);
/* 1527:1431 */         if (bool1)
/* 1528:     */         {
/* 1529:1433 */           localLocalFrame = new LocalFrame(2);
/* 1530:1434 */           localLocalStack.push(localLocalFrame);
/* 1531:     */         }
/* 1532:1436 */         if (!bool1)
/* 1533:     */         {
/* 1534:1438 */           localObject4 = local_Request.getOutputStream();
/* 1535:1439 */           local_Request.write_string(paramString);
/* 1536:1440 */           ((OutputStream)localObject4).write_long(paramInt);
/* 1537:     */         }
/* 1538:     */         else
/* 1539:     */         {
/* 1540:1444 */           localObject4 = local_Request.getOutputStream();
/* 1541:1445 */           localLocalFrame.add(paramString);
/* 1542:1446 */           localLocalFrame.add(paramInt);
/* 1543:     */         }
/* 1544:1448 */         local_Request.invoke();
/* 1545:     */         Object localObject5;
/* 1546:     */         Object localObject1;
/* 1547:1449 */         if (bool1)
/* 1548:     */         {
/* 1549:1451 */           localObject4 = null;
/* 1550:1452 */           localObject5 = localLocalFrame.getResult();
/* 1551:1453 */           if (localObject5 != null) {
/* 1552:1455 */             localObject4 = (TypePmtSyncRsV1[])ObjectVal.clone(localObject5);
/* 1553:     */           }
/* 1554:1457 */           return localObject4;
/* 1555:     */         }
/* 1556:1459 */         Object localObject4 = local_Request.getInputStream();
/* 1557:1461 */         if (local_Request.isRMI()) {
/* 1558:1461 */           localObject5 = (TypePmtSyncRsV1[])local_Request.read_value(new TypePmtSyncRsV1[0].getClass());
/* 1559:     */         } else {
/* 1560:1461 */           localObject5 = TypePmtSyncRsV1SeqHelper.read((InputStream)localObject4);
/* 1561:     */         }
/* 1562:1462 */         return localObject5;
/* 1563:     */       }
/* 1564:     */       catch (TRANSIENT localTRANSIENT)
/* 1565:     */       {
/* 1566:1466 */         if (i == 10) {
/* 1567:1468 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1568:     */         }
/* 1569:     */       }
/* 1570:     */       catch (UserException localUserException)
/* 1571:     */       {
/* 1572:1473 */         local_Request.isRMI();
/* 1573:     */         
/* 1574:     */ 
/* 1575:1476 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1576:     */       }
/* 1577:     */       catch (SystemException localSystemException)
/* 1578:     */       {
/* 1579:1480 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1580:     */       }
/* 1581:     */       finally
/* 1582:     */       {
/* 1583:1484 */         if (local_Request != null) {
/* 1584:1486 */           local_Request.close();
/* 1585:     */         }
/* 1586:1488 */         if (bool1) {
/* 1587:1489 */           localLocalStack.pop(localLocalFrame);
/* 1588:     */         }
/* 1589:1490 */         localLocalStack.setArgsOnLocal(bool2);
/* 1590:     */       }
/* 1591:     */     }
/* 1592:     */   }
/* 1593:     */   
/* 1594:     */   public TypePmtSyncRsV1[] getPendingPmtsAndHistoryByCustomerID(String paramString, int paramInt1, int paramInt2)
/* 1595:     */     throws java.rmi.RemoteException
/* 1596:     */   {
/* 1597:1501 */     for (int i = 1;; i++)
/* 1598:     */     {
/* 1599:1503 */       _Request local_Request = null;
/* 1600:1504 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1601:1505 */       boolean bool1 = false;
/* 1602:1506 */       LocalFrame localLocalFrame = null;
/* 1603:1507 */       boolean bool2 = false;
/* 1604:     */       try
/* 1605:     */       {
/* 1606:1510 */         local_Request = __request("getPendingPmtsAndHistoryByCustomerID");
/* 1607:1511 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1608:1512 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1609:1513 */         localLocalStack.setArgsOnLocal(bool1);
/* 1610:1515 */         if (bool1)
/* 1611:     */         {
/* 1612:1517 */           localLocalFrame = new LocalFrame(3);
/* 1613:1518 */           localLocalStack.push(localLocalFrame);
/* 1614:     */         }
/* 1615:1520 */         if (!bool1)
/* 1616:     */         {
/* 1617:1522 */           localObject4 = local_Request.getOutputStream();
/* 1618:1523 */           local_Request.write_string(paramString);
/* 1619:1524 */           ((OutputStream)localObject4).write_long(paramInt1);
/* 1620:1525 */           ((OutputStream)localObject4).write_long(paramInt2);
/* 1621:     */         }
/* 1622:     */         else
/* 1623:     */         {
/* 1624:1529 */           localObject4 = local_Request.getOutputStream();
/* 1625:1530 */           localLocalFrame.add(paramString);
/* 1626:1531 */           localLocalFrame.add(paramInt1);
/* 1627:1532 */           localLocalFrame.add(paramInt2);
/* 1628:     */         }
/* 1629:1534 */         local_Request.invoke();
/* 1630:     */         Object localObject5;
/* 1631:     */         Object localObject1;
/* 1632:1535 */         if (bool1)
/* 1633:     */         {
/* 1634:1537 */           localObject4 = null;
/* 1635:1538 */           localObject5 = localLocalFrame.getResult();
/* 1636:1539 */           if (localObject5 != null) {
/* 1637:1541 */             localObject4 = (TypePmtSyncRsV1[])ObjectVal.clone(localObject5);
/* 1638:     */           }
/* 1639:1543 */           return localObject4;
/* 1640:     */         }
/* 1641:1545 */         Object localObject4 = local_Request.getInputStream();
/* 1642:1547 */         if (local_Request.isRMI()) {
/* 1643:1547 */           localObject5 = (TypePmtSyncRsV1[])local_Request.read_value(new TypePmtSyncRsV1[0].getClass());
/* 1644:     */         } else {
/* 1645:1547 */           localObject5 = TypePmtSyncRsV1SeqHelper.read((InputStream)localObject4);
/* 1646:     */         }
/* 1647:1548 */         return localObject5;
/* 1648:     */       }
/* 1649:     */       catch (TRANSIENT localTRANSIENT)
/* 1650:     */       {
/* 1651:1552 */         if (i == 10) {
/* 1652:1554 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1653:     */         }
/* 1654:     */       }
/* 1655:     */       catch (UserException localUserException)
/* 1656:     */       {
/* 1657:1559 */         local_Request.isRMI();
/* 1658:     */         
/* 1659:     */ 
/* 1660:1562 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1661:     */       }
/* 1662:     */       catch (SystemException localSystemException)
/* 1663:     */       {
/* 1664:1566 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1665:     */       }
/* 1666:     */       finally
/* 1667:     */       {
/* 1668:1570 */         if (local_Request != null) {
/* 1669:1572 */           local_Request.close();
/* 1670:     */         }
/* 1671:1574 */         if (bool1) {
/* 1672:1575 */           localLocalStack.pop(localLocalFrame);
/* 1673:     */         }
/* 1674:1576 */         localLocalStack.setArgsOnLocal(bool2);
/* 1675:     */       }
/* 1676:     */     }
/* 1677:     */   }
/* 1678:     */   
/* 1679:     */   public TypeIntraSyncRsV1[] getPendingIntrasByCustomerID(String paramString, int paramInt)
/* 1680:     */     throws java.rmi.RemoteException
/* 1681:     */   {
/* 1682:1586 */     for (int i = 1;; i++)
/* 1683:     */     {
/* 1684:1588 */       _Request local_Request = null;
/* 1685:1589 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1686:1590 */       boolean bool1 = false;
/* 1687:1591 */       LocalFrame localLocalFrame = null;
/* 1688:1592 */       boolean bool2 = false;
/* 1689:     */       try
/* 1690:     */       {
/* 1691:1595 */         local_Request = __request("getPendingIntrasByCustomerID");
/* 1692:1596 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1693:1597 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1694:1598 */         localLocalStack.setArgsOnLocal(bool1);
/* 1695:1600 */         if (bool1)
/* 1696:     */         {
/* 1697:1602 */           localLocalFrame = new LocalFrame(2);
/* 1698:1603 */           localLocalStack.push(localLocalFrame);
/* 1699:     */         }
/* 1700:1605 */         if (!bool1)
/* 1701:     */         {
/* 1702:1607 */           localObject4 = local_Request.getOutputStream();
/* 1703:1608 */           local_Request.write_string(paramString);
/* 1704:1609 */           ((OutputStream)localObject4).write_long(paramInt);
/* 1705:     */         }
/* 1706:     */         else
/* 1707:     */         {
/* 1708:1613 */           localObject4 = local_Request.getOutputStream();
/* 1709:1614 */           localLocalFrame.add(paramString);
/* 1710:1615 */           localLocalFrame.add(paramInt);
/* 1711:     */         }
/* 1712:1617 */         local_Request.invoke();
/* 1713:     */         Object localObject5;
/* 1714:     */         Object localObject1;
/* 1715:1618 */         if (bool1)
/* 1716:     */         {
/* 1717:1620 */           localObject4 = null;
/* 1718:1621 */           localObject5 = localLocalFrame.getResult();
/* 1719:1622 */           if (localObject5 != null) {
/* 1720:1624 */             localObject4 = (TypeIntraSyncRsV1[])ObjectVal.clone(localObject5);
/* 1721:     */           }
/* 1722:1626 */           return localObject4;
/* 1723:     */         }
/* 1724:1628 */         Object localObject4 = local_Request.getInputStream();
/* 1725:1630 */         if (local_Request.isRMI()) {
/* 1726:1630 */           localObject5 = (TypeIntraSyncRsV1[])local_Request.read_value(new TypeIntraSyncRsV1[0].getClass());
/* 1727:     */         } else {
/* 1728:1630 */           localObject5 = TypeIntraSyncRsV1SeqHelper.read((InputStream)localObject4);
/* 1729:     */         }
/* 1730:1631 */         return localObject5;
/* 1731:     */       }
/* 1732:     */       catch (TRANSIENT localTRANSIENT)
/* 1733:     */       {
/* 1734:1635 */         if (i == 10) {
/* 1735:1637 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1736:     */         }
/* 1737:     */       }
/* 1738:     */       catch (UserException localUserException)
/* 1739:     */       {
/* 1740:1642 */         local_Request.isRMI();
/* 1741:     */         
/* 1742:     */ 
/* 1743:1645 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1744:     */       }
/* 1745:     */       catch (SystemException localSystemException)
/* 1746:     */       {
/* 1747:1649 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1748:     */       }
/* 1749:     */       finally
/* 1750:     */       {
/* 1751:1653 */         if (local_Request != null) {
/* 1752:1655 */           local_Request.close();
/* 1753:     */         }
/* 1754:1657 */         if (bool1) {
/* 1755:1658 */           localLocalStack.pop(localLocalFrame);
/* 1756:     */         }
/* 1757:1659 */         localLocalStack.setArgsOnLocal(bool2);
/* 1758:     */       }
/* 1759:     */     }
/* 1760:     */   }
/* 1761:     */   
/* 1762:     */   public TypeIntraSyncRsV1[] getPendingIntrasAndHistoryByCustomerID(String paramString, int paramInt1, int paramInt2)
/* 1763:     */     throws java.rmi.RemoteException
/* 1764:     */   {
/* 1765:1670 */     for (int i = 1;; i++)
/* 1766:     */     {
/* 1767:1672 */       _Request local_Request = null;
/* 1768:1673 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1769:1674 */       boolean bool1 = false;
/* 1770:1675 */       LocalFrame localLocalFrame = null;
/* 1771:1676 */       boolean bool2 = false;
/* 1772:     */       try
/* 1773:     */       {
/* 1774:1679 */         local_Request = __request("getPendingIntrasAndHistoryByCustomerID");
/* 1775:1680 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1776:1681 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1777:1682 */         localLocalStack.setArgsOnLocal(bool1);
/* 1778:1684 */         if (bool1)
/* 1779:     */         {
/* 1780:1686 */           localLocalFrame = new LocalFrame(3);
/* 1781:1687 */           localLocalStack.push(localLocalFrame);
/* 1782:     */         }
/* 1783:1689 */         if (!bool1)
/* 1784:     */         {
/* 1785:1691 */           localObject4 = local_Request.getOutputStream();
/* 1786:1692 */           local_Request.write_string(paramString);
/* 1787:1693 */           ((OutputStream)localObject4).write_long(paramInt1);
/* 1788:1694 */           ((OutputStream)localObject4).write_long(paramInt2);
/* 1789:     */         }
/* 1790:     */         else
/* 1791:     */         {
/* 1792:1698 */           localObject4 = local_Request.getOutputStream();
/* 1793:1699 */           localLocalFrame.add(paramString);
/* 1794:1700 */           localLocalFrame.add(paramInt1);
/* 1795:1701 */           localLocalFrame.add(paramInt2);
/* 1796:     */         }
/* 1797:1703 */         local_Request.invoke();
/* 1798:     */         Object localObject5;
/* 1799:     */         Object localObject1;
/* 1800:1704 */         if (bool1)
/* 1801:     */         {
/* 1802:1706 */           localObject4 = null;
/* 1803:1707 */           localObject5 = localLocalFrame.getResult();
/* 1804:1708 */           if (localObject5 != null) {
/* 1805:1710 */             localObject4 = (TypeIntraSyncRsV1[])ObjectVal.clone(localObject5);
/* 1806:     */           }
/* 1807:1712 */           return localObject4;
/* 1808:     */         }
/* 1809:1714 */         Object localObject4 = local_Request.getInputStream();
/* 1810:1716 */         if (local_Request.isRMI()) {
/* 1811:1716 */           localObject5 = (TypeIntraSyncRsV1[])local_Request.read_value(new TypeIntraSyncRsV1[0].getClass());
/* 1812:     */         } else {
/* 1813:1716 */           localObject5 = TypeIntraSyncRsV1SeqHelper.read((InputStream)localObject4);
/* 1814:     */         }
/* 1815:1717 */         return localObject5;
/* 1816:     */       }
/* 1817:     */       catch (TRANSIENT localTRANSIENT)
/* 1818:     */       {
/* 1819:1721 */         if (i == 10) {
/* 1820:1723 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1821:     */         }
/* 1822:     */       }
/* 1823:     */       catch (UserException localUserException)
/* 1824:     */       {
/* 1825:1728 */         local_Request.isRMI();
/* 1826:     */         
/* 1827:     */ 
/* 1828:1731 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1829:     */       }
/* 1830:     */       catch (SystemException localSystemException)
/* 1831:     */       {
/* 1832:1735 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1833:     */       }
/* 1834:     */       finally
/* 1835:     */       {
/* 1836:1739 */         if (local_Request != null) {
/* 1837:1741 */           local_Request.close();
/* 1838:     */         }
/* 1839:1743 */         if (bool1) {
/* 1840:1744 */           localLocalStack.pop(localLocalFrame);
/* 1841:     */         }
/* 1842:1745 */         localLocalStack.setArgsOnLocal(bool2);
/* 1843:     */       }
/* 1844:     */     }
/* 1845:     */   }
/* 1846:     */   
/* 1847:     */   public TypePmtSyncRsV1 getPendingPmts(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
/* 1848:     */     throws java.rmi.RemoteException
/* 1849:     */   {
/* 1850:1756 */     for (int i = 1;; i++)
/* 1851:     */     {
/* 1852:1758 */       _Request local_Request = null;
/* 1853:1759 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1854:1760 */       boolean bool1 = false;
/* 1855:1761 */       LocalFrame localLocalFrame = null;
/* 1856:1762 */       boolean bool2 = false;
/* 1857:     */       try
/* 1858:     */       {
/* 1859:1765 */         local_Request = __request("getPendingPmts");
/* 1860:1766 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1861:1767 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1862:1768 */         localLocalStack.setArgsOnLocal(bool1);
/* 1863:1770 */         if (bool1)
/* 1864:     */         {
/* 1865:1772 */           localLocalFrame = new LocalFrame(3);
/* 1866:1773 */           localLocalStack.push(localLocalFrame);
/* 1867:     */         }
/* 1868:1775 */         if (!bool1)
/* 1869:     */         {
/* 1870:1777 */           localObject4 = local_Request.getOutputStream();
/* 1871:1778 */           if (local_Request.isRMI()) {
/* 1872:1778 */             local_Request.write_value(paramTypePmtSyncRqV1, TypePmtSyncRqV1.class);
/* 1873:     */           } else {
/* 1874:1778 */             TypePmtSyncRqV1Helper.write((OutputStream)localObject4, paramTypePmtSyncRqV1);
/* 1875:     */           }
/* 1876:1779 */           if (local_Request.isRMI()) {
/* 1877:1779 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 1878:     */           } else {
/* 1879:1779 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 1880:     */           }
/* 1881:1780 */           ((OutputStream)localObject4).write_long(paramInt);
/* 1882:     */         }
/* 1883:     */         else
/* 1884:     */         {
/* 1885:1784 */           localObject4 = local_Request.getOutputStream();
/* 1886:1785 */           localLocalFrame.add(paramTypePmtSyncRqV1);
/* 1887:1786 */           localLocalFrame.add(paramTypeUserData);
/* 1888:1787 */           localLocalFrame.add(paramInt);
/* 1889:     */         }
/* 1890:1789 */         local_Request.invoke();
/* 1891:     */         Object localObject5;
/* 1892:     */         Object localObject1;
/* 1893:1790 */         if (bool1)
/* 1894:     */         {
/* 1895:1792 */           localObject4 = null;
/* 1896:1793 */           localObject5 = localLocalFrame.getResult();
/* 1897:1794 */           if (localObject5 != null) {
/* 1898:1796 */             localObject4 = (TypePmtSyncRsV1)ObjectVal.clone(localObject5);
/* 1899:     */           }
/* 1900:1798 */           return localObject4;
/* 1901:     */         }
/* 1902:1800 */         Object localObject4 = local_Request.getInputStream();
/* 1903:1802 */         if (local_Request.isRMI()) {
/* 1904:1802 */           localObject5 = (TypePmtSyncRsV1)local_Request.read_value(TypePmtSyncRsV1.class);
/* 1905:     */         } else {
/* 1906:1802 */           localObject5 = TypePmtSyncRsV1Helper.read((InputStream)localObject4);
/* 1907:     */         }
/* 1908:1803 */         return localObject5;
/* 1909:     */       }
/* 1910:     */       catch (TRANSIENT localTRANSIENT)
/* 1911:     */       {
/* 1912:1807 */         if (i == 10) {
/* 1913:1809 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1914:     */         }
/* 1915:     */       }
/* 1916:     */       catch (UserException localUserException)
/* 1917:     */       {
/* 1918:1814 */         local_Request.isRMI();
/* 1919:     */         
/* 1920:     */ 
/* 1921:1817 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1922:     */       }
/* 1923:     */       catch (SystemException localSystemException)
/* 1924:     */       {
/* 1925:1821 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1926:     */       }
/* 1927:     */       finally
/* 1928:     */       {
/* 1929:1825 */         if (local_Request != null) {
/* 1930:1827 */           local_Request.close();
/* 1931:     */         }
/* 1932:1829 */         if (bool1) {
/* 1933:1830 */           localLocalStack.pop(localLocalFrame);
/* 1934:     */         }
/* 1935:1831 */         localLocalStack.setArgsOnLocal(bool2);
/* 1936:     */       }
/* 1937:     */     }
/* 1938:     */   }
/* 1939:     */   
/* 1940:     */   public TypeIntraSyncRsV1 getPendingIntras(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
/* 1941:     */     throws java.rmi.RemoteException
/* 1942:     */   {
/* 1943:1842 */     for (int i = 1;; i++)
/* 1944:     */     {
/* 1945:1844 */       _Request local_Request = null;
/* 1946:1845 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1947:1846 */       boolean bool1 = false;
/* 1948:1847 */       LocalFrame localLocalFrame = null;
/* 1949:1848 */       boolean bool2 = false;
/* 1950:     */       try
/* 1951:     */       {
/* 1952:1851 */         local_Request = __request("getPendingIntras");
/* 1953:1852 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1954:1853 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1955:1854 */         localLocalStack.setArgsOnLocal(bool1);
/* 1956:1856 */         if (bool1)
/* 1957:     */         {
/* 1958:1858 */           localLocalFrame = new LocalFrame(3);
/* 1959:1859 */           localLocalStack.push(localLocalFrame);
/* 1960:     */         }
/* 1961:1861 */         if (!bool1)
/* 1962:     */         {
/* 1963:1863 */           localObject4 = local_Request.getOutputStream();
/* 1964:1864 */           if (local_Request.isRMI()) {
/* 1965:1864 */             local_Request.write_value(paramTypeIntraSyncRqV1, TypeIntraSyncRqV1.class);
/* 1966:     */           } else {
/* 1967:1864 */             TypeIntraSyncRqV1Helper.write((OutputStream)localObject4, paramTypeIntraSyncRqV1);
/* 1968:     */           }
/* 1969:1865 */           if (local_Request.isRMI()) {
/* 1970:1865 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 1971:     */           } else {
/* 1972:1865 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 1973:     */           }
/* 1974:1866 */           ((OutputStream)localObject4).write_long(paramInt);
/* 1975:     */         }
/* 1976:     */         else
/* 1977:     */         {
/* 1978:1870 */           localObject4 = local_Request.getOutputStream();
/* 1979:1871 */           localLocalFrame.add(paramTypeIntraSyncRqV1);
/* 1980:1872 */           localLocalFrame.add(paramTypeUserData);
/* 1981:1873 */           localLocalFrame.add(paramInt);
/* 1982:     */         }
/* 1983:1875 */         local_Request.invoke();
/* 1984:     */         Object localObject5;
/* 1985:     */         Object localObject1;
/* 1986:1876 */         if (bool1)
/* 1987:     */         {
/* 1988:1878 */           localObject4 = null;
/* 1989:1879 */           localObject5 = localLocalFrame.getResult();
/* 1990:1880 */           if (localObject5 != null) {
/* 1991:1882 */             localObject4 = (TypeIntraSyncRsV1)ObjectVal.clone(localObject5);
/* 1992:     */           }
/* 1993:1884 */           return localObject4;
/* 1994:     */         }
/* 1995:1886 */         Object localObject4 = local_Request.getInputStream();
/* 1996:1888 */         if (local_Request.isRMI()) {
/* 1997:1888 */           localObject5 = (TypeIntraSyncRsV1)local_Request.read_value(TypeIntraSyncRsV1.class);
/* 1998:     */         } else {
/* 1999:1888 */           localObject5 = TypeIntraSyncRsV1Helper.read((InputStream)localObject4);
/* 2000:     */         }
/* 2001:1889 */         return localObject5;
/* 2002:     */       }
/* 2003:     */       catch (TRANSIENT localTRANSIENT)
/* 2004:     */       {
/* 2005:1893 */         if (i == 10) {
/* 2006:1895 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2007:     */         }
/* 2008:     */       }
/* 2009:     */       catch (UserException localUserException)
/* 2010:     */       {
/* 2011:1900 */         local_Request.isRMI();
/* 2012:     */         
/* 2013:     */ 
/* 2014:1903 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2015:     */       }
/* 2016:     */       catch (SystemException localSystemException)
/* 2017:     */       {
/* 2018:1907 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2019:     */       }
/* 2020:     */       finally
/* 2021:     */       {
/* 2022:1911 */         if (local_Request != null) {
/* 2023:1913 */           local_Request.close();
/* 2024:     */         }
/* 2025:1915 */         if (bool1) {
/* 2026:1916 */           localLocalStack.pop(localLocalFrame);
/* 2027:     */         }
/* 2028:1917 */         localLocalStack.setArgsOnLocal(bool2);
/* 2029:     */       }
/* 2030:     */     }
/* 2031:     */   }
/* 2032:     */   
/* 2033:     */   public String getPmtStatus(String paramString)
/* 2034:     */     throws java.rmi.RemoteException
/* 2035:     */   {
/* 2036:1926 */     for (int i = 1;; i++)
/* 2037:     */     {
/* 2038:1928 */       _Request local_Request = null;
/* 2039:1929 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2040:1930 */       boolean bool1 = false;
/* 2041:1931 */       LocalFrame localLocalFrame = null;
/* 2042:1932 */       boolean bool2 = false;
/* 2043:     */       try
/* 2044:     */       {
/* 2045:1935 */         local_Request = __request("getPmtStatus");
/* 2046:1936 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2047:1937 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2048:1938 */         localLocalStack.setArgsOnLocal(bool1);
/* 2049:1940 */         if (bool1)
/* 2050:     */         {
/* 2051:1942 */           localLocalFrame = new LocalFrame(1);
/* 2052:1943 */           localLocalStack.push(localLocalFrame);
/* 2053:     */         }
/* 2054:1945 */         if (!bool1)
/* 2055:     */         {
/* 2056:1947 */           localObject4 = local_Request.getOutputStream();
/* 2057:1948 */           local_Request.write_string(paramString);
/* 2058:     */         }
/* 2059:     */         else
/* 2060:     */         {
/* 2061:1952 */           localObject4 = local_Request.getOutputStream();
/* 2062:1953 */           localLocalFrame.add(paramString);
/* 2063:     */         }
/* 2064:1955 */         local_Request.invoke();
/* 2065:     */         Object localObject1;
/* 2066:1956 */         if (bool1)
/* 2067:     */         {
/* 2068:1958 */           localObject4 = null;
/* 2069:1959 */           localObject4 = (String)localLocalFrame.getResult();
/* 2070:1960 */           return localObject4;
/* 2071:     */         }
/* 2072:1962 */         Object localObject4 = local_Request.getInputStream();
/* 2073:     */         
/* 2074:1964 */         String str = local_Request.read_string();
/* 2075:1965 */         return str;
/* 2076:     */       }
/* 2077:     */       catch (TRANSIENT localTRANSIENT)
/* 2078:     */       {
/* 2079:1969 */         if (i == 10) {
/* 2080:1971 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2081:     */         }
/* 2082:     */       }
/* 2083:     */       catch (UserException localUserException)
/* 2084:     */       {
/* 2085:1976 */         local_Request.isRMI();
/* 2086:     */         
/* 2087:     */ 
/* 2088:1979 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2089:     */       }
/* 2090:     */       catch (SystemException localSystemException)
/* 2091:     */       {
/* 2092:1983 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2093:     */       }
/* 2094:     */       finally
/* 2095:     */       {
/* 2096:1987 */         if (local_Request != null) {
/* 2097:1989 */           local_Request.close();
/* 2098:     */         }
/* 2099:1991 */         if (bool1) {
/* 2100:1992 */           localLocalStack.pop(localLocalFrame);
/* 2101:     */         }
/* 2102:1993 */         localLocalStack.setArgsOnLocal(bool2);
/* 2103:     */       }
/* 2104:     */     }
/* 2105:     */   }
/* 2106:     */   
/* 2107:     */   public boolean checkPayeeEditMask(String paramString1, String paramString2)
/* 2108:     */     throws java.rmi.RemoteException
/* 2109:     */   {
/* 2110:2003 */     for (int i = 1;; i++)
/* 2111:     */     {
/* 2112:2005 */       _Request local_Request = null;
/* 2113:2006 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2114:2007 */       boolean bool1 = false;
/* 2115:2008 */       LocalFrame localLocalFrame = null;
/* 2116:2009 */       boolean bool2 = false;
/* 2117:     */       try
/* 2118:     */       {
/* 2119:2012 */         local_Request = __request("checkPayeeEditMask");
/* 2120:2013 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2121:2014 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2122:2015 */         localLocalStack.setArgsOnLocal(bool1);
/* 2123:2017 */         if (bool1)
/* 2124:     */         {
/* 2125:2019 */           localLocalFrame = new LocalFrame(2);
/* 2126:2020 */           localLocalStack.push(localLocalFrame);
/* 2127:     */         }
/* 2128:     */         OutputStream localOutputStream;
/* 2129:2022 */         if (!bool1)
/* 2130:     */         {
/* 2131:2024 */           localOutputStream = local_Request.getOutputStream();
/* 2132:2025 */           local_Request.write_string(paramString1);
/* 2133:2026 */           local_Request.write_string(paramString2);
/* 2134:     */         }
/* 2135:     */         else
/* 2136:     */         {
/* 2137:2030 */           localOutputStream = local_Request.getOutputStream();
/* 2138:2031 */           localLocalFrame.add(paramString1);
/* 2139:2032 */           localLocalFrame.add(paramString2);
/* 2140:     */         }
/* 2141:2034 */         local_Request.invoke();
/* 2142:     */         boolean bool3;
/* 2143:2035 */         if (bool1)
/* 2144:     */         {
/* 2145:2038 */           boolean bool4 = ((Boolean)localLocalFrame.getResult()).booleanValue();
/* 2146:2039 */           return bool4;
/* 2147:     */         }
/* 2148:2041 */         InputStream localInputStream = local_Request.getInputStream();
/* 2149:     */         
/* 2150:2043 */         boolean bool5 = localInputStream.read_boolean();
/* 2151:2044 */         return bool5;
/* 2152:     */       }
/* 2153:     */       catch (TRANSIENT localTRANSIENT)
/* 2154:     */       {
/* 2155:2048 */         if (i == 10) {
/* 2156:2050 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2157:     */         }
/* 2158:     */       }
/* 2159:     */       catch (UserException localUserException)
/* 2160:     */       {
/* 2161:2055 */         local_Request.isRMI();
/* 2162:     */         
/* 2163:     */ 
/* 2164:2058 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2165:     */       }
/* 2166:     */       catch (SystemException localSystemException)
/* 2167:     */       {
/* 2168:2062 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2169:     */       }
/* 2170:     */       finally
/* 2171:     */       {
/* 2172:2066 */         if (local_Request != null) {
/* 2173:2068 */           local_Request.close();
/* 2174:     */         }
/* 2175:2070 */         if (bool1) {
/* 2176:2071 */           localLocalStack.pop(localLocalFrame);
/* 2177:     */         }
/* 2178:2072 */         localLocalStack.setArgsOnLocal(bool2);
/* 2179:     */       }
/* 2180:     */     }
/* 2181:     */   }
/* 2182:     */   
/* 2183:     */   public void processIntraTrnRslt(IntraTrnRslt[] paramArrayOfIntraTrnRslt)
/* 2184:     */     throws java.rmi.RemoteException
/* 2185:     */   {
/* 2186:2081 */     for (int i = 1;; i++)
/* 2187:     */     {
/* 2188:2083 */       _Request local_Request = null;
/* 2189:2084 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2190:2085 */       boolean bool1 = false;
/* 2191:2086 */       LocalFrame localLocalFrame = null;
/* 2192:2087 */       boolean bool2 = false;
/* 2193:     */       try
/* 2194:     */       {
/* 2195:2090 */         local_Request = __request("processIntraTrnRslt");
/* 2196:2091 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2197:2092 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2198:2093 */         localLocalStack.setArgsOnLocal(bool1);
/* 2199:2095 */         if (bool1)
/* 2200:     */         {
/* 2201:2097 */           localLocalFrame = new LocalFrame(1);
/* 2202:2098 */           localLocalStack.push(localLocalFrame);
/* 2203:     */         }
/* 2204:     */         OutputStream localOutputStream;
/* 2205:2100 */         if (!bool1)
/* 2206:     */         {
/* 2207:2102 */           localOutputStream = local_Request.getOutputStream();
/* 2208:2103 */           if (local_Request.isRMI()) {
/* 2209:2103 */             local_Request.write_value(paramArrayOfIntraTrnRslt, new IntraTrnRslt[0].getClass());
/* 2210:     */           } else {
/* 2211:2103 */             IntraTrnRsltSeqHelper.write(localOutputStream, paramArrayOfIntraTrnRslt);
/* 2212:     */           }
/* 2213:     */         }
/* 2214:     */         else
/* 2215:     */         {
/* 2216:2107 */           localOutputStream = local_Request.getOutputStream();
/* 2217:2108 */           localLocalFrame.add(paramArrayOfIntraTrnRslt);
/* 2218:     */         }
/* 2219:2110 */         local_Request.invoke();
/* 2220:2111 */         return;
/* 2221:     */       }
/* 2222:     */       catch (TRANSIENT localTRANSIENT)
/* 2223:     */       {
/* 2224:2115 */         if (i == 10) {
/* 2225:2117 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2226:     */         }
/* 2227:     */       }
/* 2228:     */       catch (UserException localUserException)
/* 2229:     */       {
/* 2230:2122 */         local_Request.isRMI();
/* 2231:     */         
/* 2232:     */ 
/* 2233:2125 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2234:     */       }
/* 2235:     */       catch (SystemException localSystemException)
/* 2236:     */       {
/* 2237:2129 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2238:     */       }
/* 2239:     */       finally
/* 2240:     */       {
/* 2241:2133 */         if (local_Request != null) {
/* 2242:2135 */           local_Request.close();
/* 2243:     */         }
/* 2244:2137 */         if (bool1) {
/* 2245:2138 */           localLocalStack.pop(localLocalFrame);
/* 2246:     */         }
/* 2247:2139 */         localLocalStack.setArgsOnLocal(bool2);
/* 2248:     */       }
/* 2249:     */     }
/* 2250:     */   }
/* 2251:     */   
/* 2252:     */   public void processPmtTrnRslt(PmtTrnRslt[] paramArrayOfPmtTrnRslt)
/* 2253:     */     throws java.rmi.RemoteException
/* 2254:     */   {
/* 2255:2148 */     for (int i = 1;; i++)
/* 2256:     */     {
/* 2257:2150 */       _Request local_Request = null;
/* 2258:2151 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2259:2152 */       boolean bool1 = false;
/* 2260:2153 */       LocalFrame localLocalFrame = null;
/* 2261:2154 */       boolean bool2 = false;
/* 2262:     */       try
/* 2263:     */       {
/* 2264:2157 */         local_Request = __request("processPmtTrnRslt");
/* 2265:2158 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2266:2159 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2267:2160 */         localLocalStack.setArgsOnLocal(bool1);
/* 2268:2162 */         if (bool1)
/* 2269:     */         {
/* 2270:2164 */           localLocalFrame = new LocalFrame(1);
/* 2271:2165 */           localLocalStack.push(localLocalFrame);
/* 2272:     */         }
/* 2273:     */         OutputStream localOutputStream;
/* 2274:2167 */         if (!bool1)
/* 2275:     */         {
/* 2276:2169 */           localOutputStream = local_Request.getOutputStream();
/* 2277:2170 */           if (local_Request.isRMI()) {
/* 2278:2170 */             local_Request.write_value(paramArrayOfPmtTrnRslt, new PmtTrnRslt[0].getClass());
/* 2279:     */           } else {
/* 2280:2170 */             PmtTrnRsltSeqHelper.write(localOutputStream, paramArrayOfPmtTrnRslt);
/* 2281:     */           }
/* 2282:     */         }
/* 2283:     */         else
/* 2284:     */         {
/* 2285:2174 */           localOutputStream = local_Request.getOutputStream();
/* 2286:2175 */           localLocalFrame.add(paramArrayOfPmtTrnRslt);
/* 2287:     */         }
/* 2288:2177 */         local_Request.invoke();
/* 2289:2178 */         return;
/* 2290:     */       }
/* 2291:     */       catch (TRANSIENT localTRANSIENT)
/* 2292:     */       {
/* 2293:2182 */         if (i == 10) {
/* 2294:2184 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2295:     */         }
/* 2296:     */       }
/* 2297:     */       catch (UserException localUserException)
/* 2298:     */       {
/* 2299:2189 */         local_Request.isRMI();
/* 2300:     */         
/* 2301:     */ 
/* 2302:2192 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2303:     */       }
/* 2304:     */       catch (SystemException localSystemException)
/* 2305:     */       {
/* 2306:2196 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2307:     */       }
/* 2308:     */       finally
/* 2309:     */       {
/* 2310:2200 */         if (local_Request != null) {
/* 2311:2202 */           local_Request.close();
/* 2312:     */         }
/* 2313:2204 */         if (bool1) {
/* 2314:2205 */           localLocalStack.pop(localLocalFrame);
/* 2315:     */         }
/* 2316:2206 */         localLocalStack.setArgsOnLocal(bool2);
/* 2317:     */       }
/* 2318:     */     }
/* 2319:     */   }
/* 2320:     */   
/* 2321:     */   public void processCustPayeeRslt(CustPayeeRslt[] paramArrayOfCustPayeeRslt)
/* 2322:     */     throws java.rmi.RemoteException
/* 2323:     */   {
/* 2324:2215 */     for (int i = 1;; i++)
/* 2325:     */     {
/* 2326:2217 */       _Request local_Request = null;
/* 2327:2218 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2328:2219 */       boolean bool1 = false;
/* 2329:2220 */       LocalFrame localLocalFrame = null;
/* 2330:2221 */       boolean bool2 = false;
/* 2331:     */       try
/* 2332:     */       {
/* 2333:2224 */         local_Request = __request("processCustPayeeRslt");
/* 2334:2225 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2335:2226 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2336:2227 */         localLocalStack.setArgsOnLocal(bool1);
/* 2337:2229 */         if (bool1)
/* 2338:     */         {
/* 2339:2231 */           localLocalFrame = new LocalFrame(1);
/* 2340:2232 */           localLocalStack.push(localLocalFrame);
/* 2341:     */         }
/* 2342:     */         OutputStream localOutputStream;
/* 2343:2234 */         if (!bool1)
/* 2344:     */         {
/* 2345:2236 */           localOutputStream = local_Request.getOutputStream();
/* 2346:2237 */           if (local_Request.isRMI()) {
/* 2347:2237 */             local_Request.write_value(paramArrayOfCustPayeeRslt, new CustPayeeRslt[0].getClass());
/* 2348:     */           } else {
/* 2349:2237 */             CustPayeeRsltSeqHelper.write(localOutputStream, paramArrayOfCustPayeeRslt);
/* 2350:     */           }
/* 2351:     */         }
/* 2352:     */         else
/* 2353:     */         {
/* 2354:2241 */           localOutputStream = local_Request.getOutputStream();
/* 2355:2242 */           localLocalFrame.add(paramArrayOfCustPayeeRslt);
/* 2356:     */         }
/* 2357:2244 */         local_Request.invoke();
/* 2358:2245 */         return;
/* 2359:     */       }
/* 2360:     */       catch (TRANSIENT localTRANSIENT)
/* 2361:     */       {
/* 2362:2249 */         if (i == 10) {
/* 2363:2251 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2364:     */         }
/* 2365:     */       }
/* 2366:     */       catch (UserException localUserException)
/* 2367:     */       {
/* 2368:2256 */         local_Request.isRMI();
/* 2369:     */         
/* 2370:     */ 
/* 2371:2259 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2372:     */       }
/* 2373:     */       catch (SystemException localSystemException)
/* 2374:     */       {
/* 2375:2263 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2376:     */       }
/* 2377:     */       finally
/* 2378:     */       {
/* 2379:2267 */         if (local_Request != null) {
/* 2380:2269 */           local_Request.close();
/* 2381:     */         }
/* 2382:2271 */         if (bool1) {
/* 2383:2272 */           localLocalStack.pop(localLocalFrame);
/* 2384:     */         }
/* 2385:2273 */         localLocalStack.setArgsOnLocal(bool2);
/* 2386:     */       }
/* 2387:     */     }
/* 2388:     */   }
/* 2389:     */   
/* 2390:     */   public void processFundAllocRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
/* 2391:     */     throws java.rmi.RemoteException
/* 2392:     */   {
/* 2393:2282 */     for (int i = 1;; i++)
/* 2394:     */     {
/* 2395:2284 */       _Request local_Request = null;
/* 2396:2285 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2397:2286 */       boolean bool1 = false;
/* 2398:2287 */       LocalFrame localLocalFrame = null;
/* 2399:2288 */       boolean bool2 = false;
/* 2400:     */       try
/* 2401:     */       {
/* 2402:2291 */         local_Request = __request("processFundAllocRslt");
/* 2403:2292 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2404:2293 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2405:2294 */         localLocalStack.setArgsOnLocal(bool1);
/* 2406:2296 */         if (bool1)
/* 2407:     */         {
/* 2408:2298 */           localLocalFrame = new LocalFrame(1);
/* 2409:2299 */           localLocalStack.push(localLocalFrame);
/* 2410:     */         }
/* 2411:     */         OutputStream localOutputStream;
/* 2412:2301 */         if (!bool1)
/* 2413:     */         {
/* 2414:2303 */           localOutputStream = local_Request.getOutputStream();
/* 2415:2304 */           if (local_Request.isRMI()) {
/* 2416:2304 */             local_Request.write_value(paramArrayOfFundsAllocRslt, new FundsAllocRslt[0].getClass());
/* 2417:     */           } else {
/* 2418:2304 */             FundsAllocRsltSeqHelper.write(localOutputStream, paramArrayOfFundsAllocRslt);
/* 2419:     */           }
/* 2420:     */         }
/* 2421:     */         else
/* 2422:     */         {
/* 2423:2308 */           localOutputStream = local_Request.getOutputStream();
/* 2424:2309 */           localLocalFrame.add(paramArrayOfFundsAllocRslt);
/* 2425:     */         }
/* 2426:2311 */         local_Request.invoke();
/* 2427:2312 */         return;
/* 2428:     */       }
/* 2429:     */       catch (TRANSIENT localTRANSIENT)
/* 2430:     */       {
/* 2431:2316 */         if (i == 10) {
/* 2432:2318 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2433:     */         }
/* 2434:     */       }
/* 2435:     */       catch (UserException localUserException)
/* 2436:     */       {
/* 2437:2323 */         local_Request.isRMI();
/* 2438:     */         
/* 2439:     */ 
/* 2440:2326 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2441:     */       }
/* 2442:     */       catch (SystemException localSystemException)
/* 2443:     */       {
/* 2444:2330 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2445:     */       }
/* 2446:     */       finally
/* 2447:     */       {
/* 2448:2334 */         if (local_Request != null) {
/* 2449:2336 */           local_Request.close();
/* 2450:     */         }
/* 2451:2338 */         if (bool1) {
/* 2452:2339 */           localLocalStack.pop(localLocalFrame);
/* 2453:     */         }
/* 2454:2340 */         localLocalStack.setArgsOnLocal(bool2);
/* 2455:     */       }
/* 2456:     */     }
/* 2457:     */   }
/* 2458:     */   
/* 2459:     */   public void processFundRevertRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
/* 2460:     */     throws java.rmi.RemoteException
/* 2461:     */   {
/* 2462:2349 */     for (int i = 1;; i++)
/* 2463:     */     {
/* 2464:2351 */       _Request local_Request = null;
/* 2465:2352 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2466:2353 */       boolean bool1 = false;
/* 2467:2354 */       LocalFrame localLocalFrame = null;
/* 2468:2355 */       boolean bool2 = false;
/* 2469:     */       try
/* 2470:     */       {
/* 2471:2358 */         local_Request = __request("processFundRevertRslt");
/* 2472:2359 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2473:2360 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2474:2361 */         localLocalStack.setArgsOnLocal(bool1);
/* 2475:2363 */         if (bool1)
/* 2476:     */         {
/* 2477:2365 */           localLocalFrame = new LocalFrame(1);
/* 2478:2366 */           localLocalStack.push(localLocalFrame);
/* 2479:     */         }
/* 2480:     */         OutputStream localOutputStream;
/* 2481:2368 */         if (!bool1)
/* 2482:     */         {
/* 2483:2370 */           localOutputStream = local_Request.getOutputStream();
/* 2484:2371 */           if (local_Request.isRMI()) {
/* 2485:2371 */             local_Request.write_value(paramArrayOfFundsAllocRslt, new FundsAllocRslt[0].getClass());
/* 2486:     */           } else {
/* 2487:2371 */             FundsAllocRsltSeqHelper.write(localOutputStream, paramArrayOfFundsAllocRslt);
/* 2488:     */           }
/* 2489:     */         }
/* 2490:     */         else
/* 2491:     */         {
/* 2492:2375 */           localOutputStream = local_Request.getOutputStream();
/* 2493:2376 */           localLocalFrame.add(paramArrayOfFundsAllocRslt);
/* 2494:     */         }
/* 2495:2378 */         local_Request.invoke();
/* 2496:2379 */         return;
/* 2497:     */       }
/* 2498:     */       catch (TRANSIENT localTRANSIENT)
/* 2499:     */       {
/* 2500:2383 */         if (i == 10) {
/* 2501:2385 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2502:     */         }
/* 2503:     */       }
/* 2504:     */       catch (UserException localUserException)
/* 2505:     */       {
/* 2506:2390 */         local_Request.isRMI();
/* 2507:     */         
/* 2508:     */ 
/* 2509:2393 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2510:     */       }
/* 2511:     */       catch (SystemException localSystemException)
/* 2512:     */       {
/* 2513:2397 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2514:     */       }
/* 2515:     */       finally
/* 2516:     */       {
/* 2517:2401 */         if (local_Request != null) {
/* 2518:2403 */           local_Request.close();
/* 2519:     */         }
/* 2520:2405 */         if (bool1) {
/* 2521:2406 */           localLocalStack.pop(localLocalFrame);
/* 2522:     */         }
/* 2523:2407 */         localLocalStack.setArgsOnLocal(bool2);
/* 2524:     */       }
/* 2525:     */     }
/* 2526:     */   }
/* 2527:     */   
/* 2528:     */   public void processPayeeRslt(PayeeRslt[] paramArrayOfPayeeRslt)
/* 2529:     */     throws java.rmi.RemoteException
/* 2530:     */   {
/* 2531:2416 */     for (int i = 1;; i++)
/* 2532:     */     {
/* 2533:2418 */       _Request local_Request = null;
/* 2534:2419 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2535:2420 */       boolean bool1 = false;
/* 2536:2421 */       LocalFrame localLocalFrame = null;
/* 2537:2422 */       boolean bool2 = false;
/* 2538:     */       try
/* 2539:     */       {
/* 2540:2425 */         local_Request = __request("processPayeeRslt");
/* 2541:2426 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2542:2427 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2543:2428 */         localLocalStack.setArgsOnLocal(bool1);
/* 2544:2430 */         if (bool1)
/* 2545:     */         {
/* 2546:2432 */           localLocalFrame = new LocalFrame(1);
/* 2547:2433 */           localLocalStack.push(localLocalFrame);
/* 2548:     */         }
/* 2549:     */         OutputStream localOutputStream;
/* 2550:2435 */         if (!bool1)
/* 2551:     */         {
/* 2552:2437 */           localOutputStream = local_Request.getOutputStream();
/* 2553:2438 */           if (local_Request.isRMI()) {
/* 2554:2438 */             local_Request.write_value(paramArrayOfPayeeRslt, new PayeeRslt[0].getClass());
/* 2555:     */           } else {
/* 2556:2438 */             PayeeRsltSeqHelper.write(localOutputStream, paramArrayOfPayeeRslt);
/* 2557:     */           }
/* 2558:     */         }
/* 2559:     */         else
/* 2560:     */         {
/* 2561:2442 */           localOutputStream = local_Request.getOutputStream();
/* 2562:2443 */           localLocalFrame.add(paramArrayOfPayeeRslt);
/* 2563:     */         }
/* 2564:2445 */         local_Request.invoke();
/* 2565:2446 */         return;
/* 2566:     */       }
/* 2567:     */       catch (TRANSIENT localTRANSIENT)
/* 2568:     */       {
/* 2569:2450 */         if (i == 10) {
/* 2570:2452 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2571:     */         }
/* 2572:     */       }
/* 2573:     */       catch (UserException localUserException)
/* 2574:     */       {
/* 2575:2457 */         local_Request.isRMI();
/* 2576:     */         
/* 2577:     */ 
/* 2578:2460 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2579:     */       }
/* 2580:     */       catch (SystemException localSystemException)
/* 2581:     */       {
/* 2582:2464 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2583:     */       }
/* 2584:     */       finally
/* 2585:     */       {
/* 2586:2468 */         if (local_Request != null) {
/* 2587:2470 */           local_Request.close();
/* 2588:     */         }
/* 2589:2472 */         if (bool1) {
/* 2590:2473 */           localLocalStack.pop(localLocalFrame);
/* 2591:     */         }
/* 2592:2474 */         localLocalStack.setArgsOnLocal(bool2);
/* 2593:     */       }
/* 2594:     */     }
/* 2595:     */   }
/* 2596:     */   
/* 2597:     */   public String addPayeeFromBackend(PayeeInfo paramPayeeInfo)
/* 2598:     */     throws java.rmi.RemoteException
/* 2599:     */   {
/* 2600:2483 */     for (int i = 1;; i++)
/* 2601:     */     {
/* 2602:2485 */       _Request local_Request = null;
/* 2603:2486 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2604:2487 */       boolean bool1 = false;
/* 2605:2488 */       LocalFrame localLocalFrame = null;
/* 2606:2489 */       boolean bool2 = false;
/* 2607:     */       try
/* 2608:     */       {
/* 2609:2492 */         local_Request = __request("addPayeeFromBackend");
/* 2610:2493 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2611:2494 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2612:2495 */         localLocalStack.setArgsOnLocal(bool1);
/* 2613:2497 */         if (bool1)
/* 2614:     */         {
/* 2615:2499 */           localLocalFrame = new LocalFrame(1);
/* 2616:2500 */           localLocalStack.push(localLocalFrame);
/* 2617:     */         }
/* 2618:2502 */         if (!bool1)
/* 2619:     */         {
/* 2620:2504 */           localObject4 = local_Request.getOutputStream();
/* 2621:2505 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 2622:     */         }
/* 2623:     */         else
/* 2624:     */         {
/* 2625:2509 */           localObject4 = local_Request.getOutputStream();
/* 2626:2510 */           localLocalFrame.add(paramPayeeInfo);
/* 2627:     */         }
/* 2628:2512 */         local_Request.invoke();
/* 2629:     */         Object localObject1;
/* 2630:2513 */         if (bool1)
/* 2631:     */         {
/* 2632:2515 */           localObject4 = null;
/* 2633:2516 */           localObject4 = (String)localLocalFrame.getResult();
/* 2634:2517 */           return localObject4;
/* 2635:     */         }
/* 2636:2519 */         Object localObject4 = local_Request.getInputStream();
/* 2637:     */         
/* 2638:2521 */         String str = local_Request.read_string();
/* 2639:2522 */         return str;
/* 2640:     */       }
/* 2641:     */       catch (TRANSIENT localTRANSIENT)
/* 2642:     */       {
/* 2643:2526 */         if (i == 10) {
/* 2644:2528 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2645:     */         }
/* 2646:     */       }
/* 2647:     */       catch (UserException localUserException)
/* 2648:     */       {
/* 2649:2533 */         local_Request.isRMI();
/* 2650:     */         
/* 2651:     */ 
/* 2652:2536 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2653:     */       }
/* 2654:     */       catch (SystemException localSystemException)
/* 2655:     */       {
/* 2656:2540 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2657:     */       }
/* 2658:     */       finally
/* 2659:     */       {
/* 2660:2544 */         if (local_Request != null) {
/* 2661:2546 */           local_Request.close();
/* 2662:     */         }
/* 2663:2548 */         if (bool1) {
/* 2664:2549 */           localLocalStack.pop(localLocalFrame);
/* 2665:     */         }
/* 2666:2550 */         localLocalStack.setArgsOnLocal(bool2);
/* 2667:     */       }
/* 2668:     */     }
/* 2669:     */   }
/* 2670:     */   
/* 2671:     */   public PayeeInfo[] updatePayeeFromBackend(PayeeInfo paramPayeeInfo)
/* 2672:     */     throws java.rmi.RemoteException
/* 2673:     */   {
/* 2674:2559 */     for (int i = 1;; i++)
/* 2675:     */     {
/* 2676:2561 */       _Request local_Request = null;
/* 2677:2562 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2678:2563 */       boolean bool1 = false;
/* 2679:2564 */       LocalFrame localLocalFrame = null;
/* 2680:2565 */       boolean bool2 = false;
/* 2681:     */       try
/* 2682:     */       {
/* 2683:2568 */         local_Request = __request("updatePayeeFromBackend");
/* 2684:2569 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2685:2570 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2686:2571 */         localLocalStack.setArgsOnLocal(bool1);
/* 2687:2573 */         if (bool1)
/* 2688:     */         {
/* 2689:2575 */           localLocalFrame = new LocalFrame(1);
/* 2690:2576 */           localLocalStack.push(localLocalFrame);
/* 2691:     */         }
/* 2692:2578 */         if (!bool1)
/* 2693:     */         {
/* 2694:2580 */           localObject4 = local_Request.getOutputStream();
/* 2695:2581 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 2696:     */         }
/* 2697:     */         else
/* 2698:     */         {
/* 2699:2585 */           localObject4 = local_Request.getOutputStream();
/* 2700:2586 */           localLocalFrame.add(paramPayeeInfo);
/* 2701:     */         }
/* 2702:2588 */         local_Request.invoke();
/* 2703:     */         Object localObject5;
/* 2704:     */         Object localObject1;
/* 2705:2589 */         if (bool1)
/* 2706:     */         {
/* 2707:2591 */           localObject4 = null;
/* 2708:2592 */           localObject5 = localLocalFrame.getResult();
/* 2709:2593 */           if (localObject5 != null) {
/* 2710:2595 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 2711:     */           }
/* 2712:2597 */           return localObject4;
/* 2713:     */         }
/* 2714:2599 */         Object localObject4 = local_Request.getInputStream();
/* 2715:2601 */         if (local_Request.isRMI()) {
/* 2716:2601 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 2717:     */         } else {
/* 2718:2601 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 2719:     */         }
/* 2720:2602 */         return localObject5;
/* 2721:     */       }
/* 2722:     */       catch (TRANSIENT localTRANSIENT)
/* 2723:     */       {
/* 2724:2606 */         if (i == 10) {
/* 2725:2608 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2726:     */         }
/* 2727:     */       }
/* 2728:     */       catch (UserException localUserException)
/* 2729:     */       {
/* 2730:2613 */         local_Request.isRMI();
/* 2731:     */         
/* 2732:     */ 
/* 2733:2616 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2734:     */       }
/* 2735:     */       catch (SystemException localSystemException)
/* 2736:     */       {
/* 2737:2620 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2738:     */       }
/* 2739:     */       finally
/* 2740:     */       {
/* 2741:2624 */         if (local_Request != null) {
/* 2742:2626 */           local_Request.close();
/* 2743:     */         }
/* 2744:2628 */         if (bool1) {
/* 2745:2629 */           localLocalStack.pop(localLocalFrame);
/* 2746:     */         }
/* 2747:2630 */         localLocalStack.setArgsOnLocal(bool2);
/* 2748:     */       }
/* 2749:     */     }
/* 2750:     */   }
/* 2751:     */   
/* 2752:     */   public void addPayeeRouteInfo(PayeeRouteInfo paramPayeeRouteInfo)
/* 2753:     */     throws java.rmi.RemoteException
/* 2754:     */   {
/* 2755:2639 */     for (int i = 1;; i++)
/* 2756:     */     {
/* 2757:2641 */       _Request local_Request = null;
/* 2758:2642 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2759:2643 */       boolean bool1 = false;
/* 2760:2644 */       LocalFrame localLocalFrame = null;
/* 2761:2645 */       boolean bool2 = false;
/* 2762:     */       try
/* 2763:     */       {
/* 2764:2648 */         local_Request = __request("addPayeeRouteInfo");
/* 2765:2649 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2766:2650 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2767:2651 */         localLocalStack.setArgsOnLocal(bool1);
/* 2768:2653 */         if (bool1)
/* 2769:     */         {
/* 2770:2655 */           localLocalFrame = new LocalFrame(1);
/* 2771:2656 */           localLocalStack.push(localLocalFrame);
/* 2772:     */         }
/* 2773:     */         OutputStream localOutputStream;
/* 2774:2658 */         if (!bool1)
/* 2775:     */         {
/* 2776:2660 */           localOutputStream = local_Request.getOutputStream();
/* 2777:2661 */           local_Request.write_value(paramPayeeRouteInfo, PayeeRouteInfo.class);
/* 2778:     */         }
/* 2779:     */         else
/* 2780:     */         {
/* 2781:2665 */           localOutputStream = local_Request.getOutputStream();
/* 2782:2666 */           localLocalFrame.add(paramPayeeRouteInfo);
/* 2783:     */         }
/* 2784:2668 */         local_Request.invoke();
/* 2785:2669 */         return;
/* 2786:     */       }
/* 2787:     */       catch (TRANSIENT localTRANSIENT)
/* 2788:     */       {
/* 2789:2673 */         if (i == 10) {
/* 2790:2675 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2791:     */         }
/* 2792:     */       }
/* 2793:     */       catch (UserException localUserException)
/* 2794:     */       {
/* 2795:2680 */         local_Request.isRMI();
/* 2796:     */         
/* 2797:     */ 
/* 2798:2683 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2799:     */       }
/* 2800:     */       catch (SystemException localSystemException)
/* 2801:     */       {
/* 2802:2687 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2803:     */       }
/* 2804:     */       finally
/* 2805:     */       {
/* 2806:2691 */         if (local_Request != null) {
/* 2807:2693 */           local_Request.close();
/* 2808:     */         }
/* 2809:2695 */         if (bool1) {
/* 2810:2696 */           localLocalStack.pop(localLocalFrame);
/* 2811:     */         }
/* 2812:2697 */         localLocalStack.setArgsOnLocal(bool2);
/* 2813:     */       }
/* 2814:     */     }
/* 2815:     */   }
/* 2816:     */   
/* 2817:     */   public TypeIntraSyncRsV1 processIntraSyncRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData)
/* 2818:     */     throws java.rmi.RemoteException
/* 2819:     */   {
/* 2820:2707 */     for (int i = 1;; i++)
/* 2821:     */     {
/* 2822:2709 */       _Request local_Request = null;
/* 2823:2710 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2824:2711 */       boolean bool1 = false;
/* 2825:2712 */       LocalFrame localLocalFrame = null;
/* 2826:2713 */       boolean bool2 = false;
/* 2827:     */       try
/* 2828:     */       {
/* 2829:2716 */         local_Request = __request("processIntraSyncRqV1");
/* 2830:2717 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2831:2718 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2832:2719 */         localLocalStack.setArgsOnLocal(bool1);
/* 2833:2721 */         if (bool1)
/* 2834:     */         {
/* 2835:2723 */           localLocalFrame = new LocalFrame(2);
/* 2836:2724 */           localLocalStack.push(localLocalFrame);
/* 2837:     */         }
/* 2838:2726 */         if (!bool1)
/* 2839:     */         {
/* 2840:2728 */           localObject4 = local_Request.getOutputStream();
/* 2841:2729 */           if (local_Request.isRMI()) {
/* 2842:2729 */             local_Request.write_value(paramTypeIntraSyncRqV1, TypeIntraSyncRqV1.class);
/* 2843:     */           } else {
/* 2844:2729 */             TypeIntraSyncRqV1Helper.write((OutputStream)localObject4, paramTypeIntraSyncRqV1);
/* 2845:     */           }
/* 2846:2730 */           if (local_Request.isRMI()) {
/* 2847:2730 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 2848:     */           } else {
/* 2849:2730 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 2850:     */           }
/* 2851:     */         }
/* 2852:     */         else
/* 2853:     */         {
/* 2854:2734 */           localObject4 = local_Request.getOutputStream();
/* 2855:2735 */           localLocalFrame.add(paramTypeIntraSyncRqV1);
/* 2856:2736 */           localLocalFrame.add(paramTypeUserData);
/* 2857:     */         }
/* 2858:2738 */         local_Request.invoke();
/* 2859:     */         Object localObject5;
/* 2860:     */         Object localObject1;
/* 2861:2739 */         if (bool1)
/* 2862:     */         {
/* 2863:2741 */           localObject4 = null;
/* 2864:2742 */           localObject5 = localLocalFrame.getResult();
/* 2865:2743 */           if (localObject5 != null) {
/* 2866:2745 */             localObject4 = (TypeIntraSyncRsV1)ObjectVal.clone(localObject5);
/* 2867:     */           }
/* 2868:2747 */           return localObject4;
/* 2869:     */         }
/* 2870:2749 */         Object localObject4 = local_Request.getInputStream();
/* 2871:2751 */         if (local_Request.isRMI()) {
/* 2872:2751 */           localObject5 = (TypeIntraSyncRsV1)local_Request.read_value(TypeIntraSyncRsV1.class);
/* 2873:     */         } else {
/* 2874:2751 */           localObject5 = TypeIntraSyncRsV1Helper.read((InputStream)localObject4);
/* 2875:     */         }
/* 2876:2752 */         return localObject5;
/* 2877:     */       }
/* 2878:     */       catch (TRANSIENT localTRANSIENT)
/* 2879:     */       {
/* 2880:2756 */         if (i == 10) {
/* 2881:2758 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2882:     */         }
/* 2883:     */       }
/* 2884:     */       catch (UserException localUserException)
/* 2885:     */       {
/* 2886:2763 */         local_Request.isRMI();
/* 2887:     */         
/* 2888:     */ 
/* 2889:2766 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2890:     */       }
/* 2891:     */       catch (SystemException localSystemException)
/* 2892:     */       {
/* 2893:2770 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2894:     */       }
/* 2895:     */       finally
/* 2896:     */       {
/* 2897:2774 */         if (local_Request != null) {
/* 2898:2776 */           local_Request.close();
/* 2899:     */         }
/* 2900:2778 */         if (bool1) {
/* 2901:2779 */           localLocalStack.pop(localLocalFrame);
/* 2902:     */         }
/* 2903:2780 */         localLocalStack.setArgsOnLocal(bool2);
/* 2904:     */       }
/* 2905:     */     }
/* 2906:     */   }
/* 2907:     */   
/* 2908:     */   public TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData)
/* 2909:     */     throws java.rmi.RemoteException
/* 2910:     */   {
/* 2911:2790 */     for (int i = 1;; i++)
/* 2912:     */     {
/* 2913:2792 */       _Request local_Request = null;
/* 2914:2793 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2915:2794 */       boolean bool1 = false;
/* 2916:2795 */       LocalFrame localLocalFrame = null;
/* 2917:2796 */       boolean bool2 = false;
/* 2918:     */       try
/* 2919:     */       {
/* 2920:2799 */         local_Request = __request("processIntraTrnRqV1");
/* 2921:2800 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2922:2801 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2923:2802 */         localLocalStack.setArgsOnLocal(bool1);
/* 2924:2804 */         if (bool1)
/* 2925:     */         {
/* 2926:2806 */           localLocalFrame = new LocalFrame(2);
/* 2927:2807 */           localLocalStack.push(localLocalFrame);
/* 2928:     */         }
/* 2929:2809 */         if (!bool1)
/* 2930:     */         {
/* 2931:2811 */           localObject4 = local_Request.getOutputStream();
/* 2932:2812 */           if (local_Request.isRMI()) {
/* 2933:2812 */             local_Request.write_value(paramTypeIntraTrnRqV1, TypeIntraTrnRqV1.class);
/* 2934:     */           } else {
/* 2935:2812 */             TypeIntraTrnRqV1Helper.write((OutputStream)localObject4, paramTypeIntraTrnRqV1);
/* 2936:     */           }
/* 2937:2813 */           if (local_Request.isRMI()) {
/* 2938:2813 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 2939:     */           } else {
/* 2940:2813 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 2941:     */           }
/* 2942:     */         }
/* 2943:     */         else
/* 2944:     */         {
/* 2945:2817 */           localObject4 = local_Request.getOutputStream();
/* 2946:2818 */           localLocalFrame.add(paramTypeIntraTrnRqV1);
/* 2947:2819 */           localLocalFrame.add(paramTypeUserData);
/* 2948:     */         }
/* 2949:2821 */         local_Request.invoke();
/* 2950:     */         Object localObject5;
/* 2951:     */         Object localObject1;
/* 2952:2822 */         if (bool1)
/* 2953:     */         {
/* 2954:2824 */           localObject4 = null;
/* 2955:2825 */           localObject5 = localLocalFrame.getResult();
/* 2956:2826 */           if (localObject5 != null) {
/* 2957:2828 */             localObject4 = (TypeIntraTrnRsV1)ObjectVal.clone(localObject5);
/* 2958:     */           }
/* 2959:2830 */           return localObject4;
/* 2960:     */         }
/* 2961:2832 */         Object localObject4 = local_Request.getInputStream();
/* 2962:2834 */         if (local_Request.isRMI()) {
/* 2963:2834 */           localObject5 = (TypeIntraTrnRsV1)local_Request.read_value(TypeIntraTrnRsV1.class);
/* 2964:     */         } else {
/* 2965:2834 */           localObject5 = TypeIntraTrnRsV1Helper.read((InputStream)localObject4);
/* 2966:     */         }
/* 2967:2835 */         return localObject5;
/* 2968:     */       }
/* 2969:     */       catch (TRANSIENT localTRANSIENT)
/* 2970:     */       {
/* 2971:2839 */         if (i == 10) {
/* 2972:2841 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2973:     */         }
/* 2974:     */       }
/* 2975:     */       catch (UserException localUserException)
/* 2976:     */       {
/* 2977:2846 */         local_Request.isRMI();
/* 2978:     */         
/* 2979:     */ 
/* 2980:2849 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2981:     */       }
/* 2982:     */       catch (SystemException localSystemException)
/* 2983:     */       {
/* 2984:2853 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2985:     */       }
/* 2986:     */       finally
/* 2987:     */       {
/* 2988:2857 */         if (local_Request != null) {
/* 2989:2859 */           local_Request.close();
/* 2990:     */         }
/* 2991:2861 */         if (bool1) {
/* 2992:2862 */           localLocalStack.pop(localLocalFrame);
/* 2993:     */         }
/* 2994:2863 */         localLocalStack.setArgsOnLocal(bool2);
/* 2995:     */       }
/* 2996:     */     }
/* 2997:     */   }
/* 2998:     */   
/* 2999:     */   public TypePayeeSyncRsV1 processPayeeSyncRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData)
/* 3000:     */     throws java.rmi.RemoteException
/* 3001:     */   {
/* 3002:2873 */     for (int i = 1;; i++)
/* 3003:     */     {
/* 3004:2875 */       _Request local_Request = null;
/* 3005:2876 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3006:2877 */       boolean bool1 = false;
/* 3007:2878 */       LocalFrame localLocalFrame = null;
/* 3008:2879 */       boolean bool2 = false;
/* 3009:     */       try
/* 3010:     */       {
/* 3011:2882 */         local_Request = __request("processPayeeSyncRqV1");
/* 3012:2883 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3013:2884 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3014:2885 */         localLocalStack.setArgsOnLocal(bool1);
/* 3015:2887 */         if (bool1)
/* 3016:     */         {
/* 3017:2889 */           localLocalFrame = new LocalFrame(2);
/* 3018:2890 */           localLocalStack.push(localLocalFrame);
/* 3019:     */         }
/* 3020:2892 */         if (!bool1)
/* 3021:     */         {
/* 3022:2894 */           localObject4 = local_Request.getOutputStream();
/* 3023:2895 */           if (local_Request.isRMI()) {
/* 3024:2895 */             local_Request.write_value(paramTypePayeeSyncRqV1, TypePayeeSyncRqV1.class);
/* 3025:     */           } else {
/* 3026:2895 */             TypePayeeSyncRqV1Helper.write((OutputStream)localObject4, paramTypePayeeSyncRqV1);
/* 3027:     */           }
/* 3028:2896 */           if (local_Request.isRMI()) {
/* 3029:2896 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3030:     */           } else {
/* 3031:2896 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3032:     */           }
/* 3033:     */         }
/* 3034:     */         else
/* 3035:     */         {
/* 3036:2900 */           localObject4 = local_Request.getOutputStream();
/* 3037:2901 */           localLocalFrame.add(paramTypePayeeSyncRqV1);
/* 3038:2902 */           localLocalFrame.add(paramTypeUserData);
/* 3039:     */         }
/* 3040:2904 */         local_Request.invoke();
/* 3041:     */         Object localObject5;
/* 3042:     */         Object localObject1;
/* 3043:2905 */         if (bool1)
/* 3044:     */         {
/* 3045:2907 */           localObject4 = null;
/* 3046:2908 */           localObject5 = localLocalFrame.getResult();
/* 3047:2909 */           if (localObject5 != null) {
/* 3048:2911 */             localObject4 = (TypePayeeSyncRsV1)ObjectVal.clone(localObject5);
/* 3049:     */           }
/* 3050:2913 */           return localObject4;
/* 3051:     */         }
/* 3052:2915 */         Object localObject4 = local_Request.getInputStream();
/* 3053:2917 */         if (local_Request.isRMI()) {
/* 3054:2917 */           localObject5 = (TypePayeeSyncRsV1)local_Request.read_value(TypePayeeSyncRsV1.class);
/* 3055:     */         } else {
/* 3056:2917 */           localObject5 = TypePayeeSyncRsV1Helper.read((InputStream)localObject4);
/* 3057:     */         }
/* 3058:2918 */         return localObject5;
/* 3059:     */       }
/* 3060:     */       catch (TRANSIENT localTRANSIENT)
/* 3061:     */       {
/* 3062:2922 */         if (i == 10) {
/* 3063:2924 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3064:     */         }
/* 3065:     */       }
/* 3066:     */       catch (UserException localUserException)
/* 3067:     */       {
/* 3068:2929 */         local_Request.isRMI();
/* 3069:     */         
/* 3070:     */ 
/* 3071:2932 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3072:     */       }
/* 3073:     */       catch (SystemException localSystemException)
/* 3074:     */       {
/* 3075:2936 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3076:     */       }
/* 3077:     */       finally
/* 3078:     */       {
/* 3079:2940 */         if (local_Request != null) {
/* 3080:2942 */           local_Request.close();
/* 3081:     */         }
/* 3082:2944 */         if (bool1) {
/* 3083:2945 */           localLocalStack.pop(localLocalFrame);
/* 3084:     */         }
/* 3085:2946 */         localLocalStack.setArgsOnLocal(bool2);
/* 3086:     */       }
/* 3087:     */     }
/* 3088:     */   }
/* 3089:     */   
/* 3090:     */   public TypePayeeTrnRsV1 processPayeeTrnRqV1(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData)
/* 3091:     */     throws java.rmi.RemoteException
/* 3092:     */   {
/* 3093:2956 */     for (int i = 1;; i++)
/* 3094:     */     {
/* 3095:2958 */       _Request local_Request = null;
/* 3096:2959 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3097:2960 */       boolean bool1 = false;
/* 3098:2961 */       LocalFrame localLocalFrame = null;
/* 3099:2962 */       boolean bool2 = false;
/* 3100:     */       try
/* 3101:     */       {
/* 3102:2965 */         local_Request = __request("processPayeeTrnRqV1");
/* 3103:2966 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3104:2967 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3105:2968 */         localLocalStack.setArgsOnLocal(bool1);
/* 3106:2970 */         if (bool1)
/* 3107:     */         {
/* 3108:2972 */           localLocalFrame = new LocalFrame(2);
/* 3109:2973 */           localLocalStack.push(localLocalFrame);
/* 3110:     */         }
/* 3111:2975 */         if (!bool1)
/* 3112:     */         {
/* 3113:2977 */           localObject4 = local_Request.getOutputStream();
/* 3114:2978 */           if (local_Request.isRMI()) {
/* 3115:2978 */             local_Request.write_value(paramTypePayeeTrnRqV1, TypePayeeTrnRqV1.class);
/* 3116:     */           } else {
/* 3117:2978 */             TypePayeeTrnRqV1Helper.write((OutputStream)localObject4, paramTypePayeeTrnRqV1);
/* 3118:     */           }
/* 3119:2979 */           if (local_Request.isRMI()) {
/* 3120:2979 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3121:     */           } else {
/* 3122:2979 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3123:     */           }
/* 3124:     */         }
/* 3125:     */         else
/* 3126:     */         {
/* 3127:2983 */           localObject4 = local_Request.getOutputStream();
/* 3128:2984 */           localLocalFrame.add(paramTypePayeeTrnRqV1);
/* 3129:2985 */           localLocalFrame.add(paramTypeUserData);
/* 3130:     */         }
/* 3131:2987 */         local_Request.invoke();
/* 3132:     */         Object localObject5;
/* 3133:     */         Object localObject1;
/* 3134:2988 */         if (bool1)
/* 3135:     */         {
/* 3136:2990 */           localObject4 = null;
/* 3137:2991 */           localObject5 = localLocalFrame.getResult();
/* 3138:2992 */           if (localObject5 != null) {
/* 3139:2994 */             localObject4 = (TypePayeeTrnRsV1)ObjectVal.clone(localObject5);
/* 3140:     */           }
/* 3141:2996 */           return localObject4;
/* 3142:     */         }
/* 3143:2998 */         Object localObject4 = local_Request.getInputStream();
/* 3144:3000 */         if (local_Request.isRMI()) {
/* 3145:3000 */           localObject5 = (TypePayeeTrnRsV1)local_Request.read_value(TypePayeeTrnRsV1.class);
/* 3146:     */         } else {
/* 3147:3000 */           localObject5 = TypePayeeTrnRsV1Helper.read((InputStream)localObject4);
/* 3148:     */         }
/* 3149:3001 */         return localObject5;
/* 3150:     */       }
/* 3151:     */       catch (TRANSIENT localTRANSIENT)
/* 3152:     */       {
/* 3153:3005 */         if (i == 10) {
/* 3154:3007 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3155:     */         }
/* 3156:     */       }
/* 3157:     */       catch (UserException localUserException)
/* 3158:     */       {
/* 3159:3012 */         local_Request.isRMI();
/* 3160:     */         
/* 3161:     */ 
/* 3162:3015 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3163:     */       }
/* 3164:     */       catch (SystemException localSystemException)
/* 3165:     */       {
/* 3166:3019 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3167:     */       }
/* 3168:     */       finally
/* 3169:     */       {
/* 3170:3023 */         if (local_Request != null) {
/* 3171:3025 */           local_Request.close();
/* 3172:     */         }
/* 3173:3027 */         if (bool1) {
/* 3174:3028 */           localLocalStack.pop(localLocalFrame);
/* 3175:     */         }
/* 3176:3029 */         localLocalStack.setArgsOnLocal(bool2);
/* 3177:     */       }
/* 3178:     */     }
/* 3179:     */   }
/* 3180:     */   
/* 3181:     */   public TypePmtInqTrnRsV1 processPmtInqTrnRqV1(TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, TypeUserData paramTypeUserData)
/* 3182:     */     throws java.rmi.RemoteException
/* 3183:     */   {
/* 3184:3039 */     for (int i = 1;; i++)
/* 3185:     */     {
/* 3186:3041 */       _Request local_Request = null;
/* 3187:3042 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3188:3043 */       boolean bool1 = false;
/* 3189:3044 */       LocalFrame localLocalFrame = null;
/* 3190:3045 */       boolean bool2 = false;
/* 3191:     */       try
/* 3192:     */       {
/* 3193:3048 */         local_Request = __request("processPmtInqTrnRqV1");
/* 3194:3049 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3195:3050 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3196:3051 */         localLocalStack.setArgsOnLocal(bool1);
/* 3197:3053 */         if (bool1)
/* 3198:     */         {
/* 3199:3055 */           localLocalFrame = new LocalFrame(2);
/* 3200:3056 */           localLocalStack.push(localLocalFrame);
/* 3201:     */         }
/* 3202:3058 */         if (!bool1)
/* 3203:     */         {
/* 3204:3060 */           localObject4 = local_Request.getOutputStream();
/* 3205:3061 */           if (local_Request.isRMI()) {
/* 3206:3061 */             local_Request.write_value(paramTypePmtInqTrnRqV1, TypePmtInqTrnRqV1.class);
/* 3207:     */           } else {
/* 3208:3061 */             TypePmtInqTrnRqV1Helper.write((OutputStream)localObject4, paramTypePmtInqTrnRqV1);
/* 3209:     */           }
/* 3210:3062 */           if (local_Request.isRMI()) {
/* 3211:3062 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3212:     */           } else {
/* 3213:3062 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3214:     */           }
/* 3215:     */         }
/* 3216:     */         else
/* 3217:     */         {
/* 3218:3066 */           localObject4 = local_Request.getOutputStream();
/* 3219:3067 */           localLocalFrame.add(paramTypePmtInqTrnRqV1);
/* 3220:3068 */           localLocalFrame.add(paramTypeUserData);
/* 3221:     */         }
/* 3222:3070 */         local_Request.invoke();
/* 3223:     */         Object localObject5;
/* 3224:     */         Object localObject1;
/* 3225:3071 */         if (bool1)
/* 3226:     */         {
/* 3227:3073 */           localObject4 = null;
/* 3228:3074 */           localObject5 = localLocalFrame.getResult();
/* 3229:3075 */           if (localObject5 != null) {
/* 3230:3077 */             localObject4 = (TypePmtInqTrnRsV1)ObjectVal.clone(localObject5);
/* 3231:     */           }
/* 3232:3079 */           return localObject4;
/* 3233:     */         }
/* 3234:3081 */         Object localObject4 = local_Request.getInputStream();
/* 3235:3083 */         if (local_Request.isRMI()) {
/* 3236:3083 */           localObject5 = (TypePmtInqTrnRsV1)local_Request.read_value(TypePmtInqTrnRsV1.class);
/* 3237:     */         } else {
/* 3238:3083 */           localObject5 = TypePmtInqTrnRsV1Helper.read((InputStream)localObject4);
/* 3239:     */         }
/* 3240:3084 */         return localObject5;
/* 3241:     */       }
/* 3242:     */       catch (TRANSIENT localTRANSIENT)
/* 3243:     */       {
/* 3244:3088 */         if (i == 10) {
/* 3245:3090 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3246:     */         }
/* 3247:     */       }
/* 3248:     */       catch (UserException localUserException)
/* 3249:     */       {
/* 3250:3095 */         local_Request.isRMI();
/* 3251:     */         
/* 3252:     */ 
/* 3253:3098 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3254:     */       }
/* 3255:     */       catch (SystemException localSystemException)
/* 3256:     */       {
/* 3257:3102 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3258:     */       }
/* 3259:     */       finally
/* 3260:     */       {
/* 3261:3106 */         if (local_Request != null) {
/* 3262:3108 */           local_Request.close();
/* 3263:     */         }
/* 3264:3110 */         if (bool1) {
/* 3265:3111 */           localLocalStack.pop(localLocalFrame);
/* 3266:     */         }
/* 3267:3112 */         localLocalStack.setArgsOnLocal(bool2);
/* 3268:     */       }
/* 3269:     */     }
/* 3270:     */   }
/* 3271:     */   
/* 3272:     */   public TypePmtSyncRsV1 processPmtSyncRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData)
/* 3273:     */     throws java.rmi.RemoteException
/* 3274:     */   {
/* 3275:3122 */     for (int i = 1;; i++)
/* 3276:     */     {
/* 3277:3124 */       _Request local_Request = null;
/* 3278:3125 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3279:3126 */       boolean bool1 = false;
/* 3280:3127 */       LocalFrame localLocalFrame = null;
/* 3281:3128 */       boolean bool2 = false;
/* 3282:     */       try
/* 3283:     */       {
/* 3284:3131 */         local_Request = __request("processPmtSyncRqV1");
/* 3285:3132 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3286:3133 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3287:3134 */         localLocalStack.setArgsOnLocal(bool1);
/* 3288:3136 */         if (bool1)
/* 3289:     */         {
/* 3290:3138 */           localLocalFrame = new LocalFrame(2);
/* 3291:3139 */           localLocalStack.push(localLocalFrame);
/* 3292:     */         }
/* 3293:3141 */         if (!bool1)
/* 3294:     */         {
/* 3295:3143 */           localObject4 = local_Request.getOutputStream();
/* 3296:3144 */           if (local_Request.isRMI()) {
/* 3297:3144 */             local_Request.write_value(paramTypePmtSyncRqV1, TypePmtSyncRqV1.class);
/* 3298:     */           } else {
/* 3299:3144 */             TypePmtSyncRqV1Helper.write((OutputStream)localObject4, paramTypePmtSyncRqV1);
/* 3300:     */           }
/* 3301:3145 */           if (local_Request.isRMI()) {
/* 3302:3145 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3303:     */           } else {
/* 3304:3145 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3305:     */           }
/* 3306:     */         }
/* 3307:     */         else
/* 3308:     */         {
/* 3309:3149 */           localObject4 = local_Request.getOutputStream();
/* 3310:3150 */           localLocalFrame.add(paramTypePmtSyncRqV1);
/* 3311:3151 */           localLocalFrame.add(paramTypeUserData);
/* 3312:     */         }
/* 3313:3153 */         local_Request.invoke();
/* 3314:     */         Object localObject5;
/* 3315:     */         Object localObject1;
/* 3316:3154 */         if (bool1)
/* 3317:     */         {
/* 3318:3156 */           localObject4 = null;
/* 3319:3157 */           localObject5 = localLocalFrame.getResult();
/* 3320:3158 */           if (localObject5 != null) {
/* 3321:3160 */             localObject4 = (TypePmtSyncRsV1)ObjectVal.clone(localObject5);
/* 3322:     */           }
/* 3323:3162 */           return localObject4;
/* 3324:     */         }
/* 3325:3164 */         Object localObject4 = local_Request.getInputStream();
/* 3326:3166 */         if (local_Request.isRMI()) {
/* 3327:3166 */           localObject5 = (TypePmtSyncRsV1)local_Request.read_value(TypePmtSyncRsV1.class);
/* 3328:     */         } else {
/* 3329:3166 */           localObject5 = TypePmtSyncRsV1Helper.read((InputStream)localObject4);
/* 3330:     */         }
/* 3331:3167 */         return localObject5;
/* 3332:     */       }
/* 3333:     */       catch (TRANSIENT localTRANSIENT)
/* 3334:     */       {
/* 3335:3171 */         if (i == 10) {
/* 3336:3173 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3337:     */         }
/* 3338:     */       }
/* 3339:     */       catch (UserException localUserException)
/* 3340:     */       {
/* 3341:3178 */         local_Request.isRMI();
/* 3342:     */         
/* 3343:     */ 
/* 3344:3181 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3345:     */       }
/* 3346:     */       catch (SystemException localSystemException)
/* 3347:     */       {
/* 3348:3185 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3349:     */       }
/* 3350:     */       finally
/* 3351:     */       {
/* 3352:3189 */         if (local_Request != null) {
/* 3353:3191 */           local_Request.close();
/* 3354:     */         }
/* 3355:3193 */         if (bool1) {
/* 3356:3194 */           localLocalStack.pop(localLocalFrame);
/* 3357:     */         }
/* 3358:3195 */         localLocalStack.setArgsOnLocal(bool2);
/* 3359:     */       }
/* 3360:     */     }
/* 3361:     */   }
/* 3362:     */   
/* 3363:     */   public TypePmtTrnRsV1 processPmtTrnRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
/* 3364:     */     throws java.rmi.RemoteException
/* 3365:     */   {
/* 3366:3205 */     for (int i = 1;; i++)
/* 3367:     */     {
/* 3368:3207 */       _Request local_Request = null;
/* 3369:3208 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3370:3209 */       boolean bool1 = false;
/* 3371:3210 */       LocalFrame localLocalFrame = null;
/* 3372:3211 */       boolean bool2 = false;
/* 3373:     */       try
/* 3374:     */       {
/* 3375:3214 */         local_Request = __request("processPmtTrnRqV1");
/* 3376:3215 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3377:3216 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3378:3217 */         localLocalStack.setArgsOnLocal(bool1);
/* 3379:3219 */         if (bool1)
/* 3380:     */         {
/* 3381:3221 */           localLocalFrame = new LocalFrame(2);
/* 3382:3222 */           localLocalStack.push(localLocalFrame);
/* 3383:     */         }
/* 3384:3224 */         if (!bool1)
/* 3385:     */         {
/* 3386:3226 */           localObject4 = local_Request.getOutputStream();
/* 3387:3227 */           if (local_Request.isRMI()) {
/* 3388:3227 */             local_Request.write_value(paramTypePmtTrnRqV1, TypePmtTrnRqV1.class);
/* 3389:     */           } else {
/* 3390:3227 */             TypePmtTrnRqV1Helper.write((OutputStream)localObject4, paramTypePmtTrnRqV1);
/* 3391:     */           }
/* 3392:3228 */           if (local_Request.isRMI()) {
/* 3393:3228 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3394:     */           } else {
/* 3395:3228 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3396:     */           }
/* 3397:     */         }
/* 3398:     */         else
/* 3399:     */         {
/* 3400:3232 */           localObject4 = local_Request.getOutputStream();
/* 3401:3233 */           localLocalFrame.add(paramTypePmtTrnRqV1);
/* 3402:3234 */           localLocalFrame.add(paramTypeUserData);
/* 3403:     */         }
/* 3404:3236 */         local_Request.invoke();
/* 3405:     */         Object localObject5;
/* 3406:     */         Object localObject1;
/* 3407:3237 */         if (bool1)
/* 3408:     */         {
/* 3409:3239 */           localObject4 = null;
/* 3410:3240 */           localObject5 = localLocalFrame.getResult();
/* 3411:3241 */           if (localObject5 != null) {
/* 3412:3243 */             localObject4 = (TypePmtTrnRsV1)ObjectVal.clone(localObject5);
/* 3413:     */           }
/* 3414:3245 */           return localObject4;
/* 3415:     */         }
/* 3416:3247 */         Object localObject4 = local_Request.getInputStream();
/* 3417:3249 */         if (local_Request.isRMI()) {
/* 3418:3249 */           localObject5 = (TypePmtTrnRsV1)local_Request.read_value(TypePmtTrnRsV1.class);
/* 3419:     */         } else {
/* 3420:3249 */           localObject5 = TypePmtTrnRsV1Helper.read((InputStream)localObject4);
/* 3421:     */         }
/* 3422:3250 */         return localObject5;
/* 3423:     */       }
/* 3424:     */       catch (TRANSIENT localTRANSIENT)
/* 3425:     */       {
/* 3426:3254 */         if (i == 10) {
/* 3427:3256 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3428:     */         }
/* 3429:     */       }
/* 3430:     */       catch (UserException localUserException)
/* 3431:     */       {
/* 3432:3261 */         local_Request.isRMI();
/* 3433:     */         
/* 3434:     */ 
/* 3435:3264 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3436:     */       }
/* 3437:     */       catch (SystemException localSystemException)
/* 3438:     */       {
/* 3439:3268 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3440:     */       }
/* 3441:     */       finally
/* 3442:     */       {
/* 3443:3272 */         if (local_Request != null) {
/* 3444:3274 */           local_Request.close();
/* 3445:     */         }
/* 3446:3276 */         if (bool1) {
/* 3447:3277 */           localLocalStack.pop(localLocalFrame);
/* 3448:     */         }
/* 3449:3278 */         localLocalStack.setArgsOnLocal(bool2);
/* 3450:     */       }
/* 3451:     */     }
/* 3452:     */   }
/* 3453:     */   
/* 3454:     */   public TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData)
/* 3455:     */     throws java.rmi.RemoteException
/* 3456:     */   {
/* 3457:3288 */     for (int i = 1;; i++)
/* 3458:     */     {
/* 3459:3290 */       _Request local_Request = null;
/* 3460:3291 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3461:3292 */       boolean bool1 = false;
/* 3462:3293 */       LocalFrame localLocalFrame = null;
/* 3463:3294 */       boolean bool2 = false;
/* 3464:     */       try
/* 3465:     */       {
/* 3466:3297 */         local_Request = __request("processRecIntraSyncRqV1");
/* 3467:3298 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3468:3299 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3469:3300 */         localLocalStack.setArgsOnLocal(bool1);
/* 3470:3302 */         if (bool1)
/* 3471:     */         {
/* 3472:3304 */           localLocalFrame = new LocalFrame(2);
/* 3473:3305 */           localLocalStack.push(localLocalFrame);
/* 3474:     */         }
/* 3475:3307 */         if (!bool1)
/* 3476:     */         {
/* 3477:3309 */           localObject4 = local_Request.getOutputStream();
/* 3478:3310 */           if (local_Request.isRMI()) {
/* 3479:3310 */             local_Request.write_value(paramTypeRecIntraSyncRqV1, TypeRecIntraSyncRqV1.class);
/* 3480:     */           } else {
/* 3481:3310 */             TypeRecIntraSyncRqV1Helper.write((OutputStream)localObject4, paramTypeRecIntraSyncRqV1);
/* 3482:     */           }
/* 3483:3311 */           if (local_Request.isRMI()) {
/* 3484:3311 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3485:     */           } else {
/* 3486:3311 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3487:     */           }
/* 3488:     */         }
/* 3489:     */         else
/* 3490:     */         {
/* 3491:3315 */           localObject4 = local_Request.getOutputStream();
/* 3492:3316 */           localLocalFrame.add(paramTypeRecIntraSyncRqV1);
/* 3493:3317 */           localLocalFrame.add(paramTypeUserData);
/* 3494:     */         }
/* 3495:3319 */         local_Request.invoke();
/* 3496:     */         Object localObject5;
/* 3497:     */         Object localObject1;
/* 3498:3320 */         if (bool1)
/* 3499:     */         {
/* 3500:3322 */           localObject4 = null;
/* 3501:3323 */           localObject5 = localLocalFrame.getResult();
/* 3502:3324 */           if (localObject5 != null) {
/* 3503:3326 */             localObject4 = (TypeRecIntraSyncRsV1)ObjectVal.clone(localObject5);
/* 3504:     */           }
/* 3505:3328 */           return localObject4;
/* 3506:     */         }
/* 3507:3330 */         Object localObject4 = local_Request.getInputStream();
/* 3508:3332 */         if (local_Request.isRMI()) {
/* 3509:3332 */           localObject5 = (TypeRecIntraSyncRsV1)local_Request.read_value(TypeRecIntraSyncRsV1.class);
/* 3510:     */         } else {
/* 3511:3332 */           localObject5 = TypeRecIntraSyncRsV1Helper.read((InputStream)localObject4);
/* 3512:     */         }
/* 3513:3333 */         return localObject5;
/* 3514:     */       }
/* 3515:     */       catch (TRANSIENT localTRANSIENT)
/* 3516:     */       {
/* 3517:3337 */         if (i == 10) {
/* 3518:3339 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3519:     */         }
/* 3520:     */       }
/* 3521:     */       catch (UserException localUserException)
/* 3522:     */       {
/* 3523:3344 */         local_Request.isRMI();
/* 3524:     */         
/* 3525:     */ 
/* 3526:3347 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3527:     */       }
/* 3528:     */       catch (SystemException localSystemException)
/* 3529:     */       {
/* 3530:3351 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3531:     */       }
/* 3532:     */       finally
/* 3533:     */       {
/* 3534:3355 */         if (local_Request != null) {
/* 3535:3357 */           local_Request.close();
/* 3536:     */         }
/* 3537:3359 */         if (bool1) {
/* 3538:3360 */           localLocalStack.pop(localLocalFrame);
/* 3539:     */         }
/* 3540:3361 */         localLocalStack.setArgsOnLocal(bool2);
/* 3541:     */       }
/* 3542:     */     }
/* 3543:     */   }
/* 3544:     */   
/* 3545:     */   public TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData)
/* 3546:     */     throws java.rmi.RemoteException
/* 3547:     */   {
/* 3548:3371 */     for (int i = 1;; i++)
/* 3549:     */     {
/* 3550:3373 */       _Request local_Request = null;
/* 3551:3374 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3552:3375 */       boolean bool1 = false;
/* 3553:3376 */       LocalFrame localLocalFrame = null;
/* 3554:3377 */       boolean bool2 = false;
/* 3555:     */       try
/* 3556:     */       {
/* 3557:3380 */         local_Request = __request("processRecIntraTrnRqV1");
/* 3558:3381 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3559:3382 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3560:3383 */         localLocalStack.setArgsOnLocal(bool1);
/* 3561:3385 */         if (bool1)
/* 3562:     */         {
/* 3563:3387 */           localLocalFrame = new LocalFrame(2);
/* 3564:3388 */           localLocalStack.push(localLocalFrame);
/* 3565:     */         }
/* 3566:3390 */         if (!bool1)
/* 3567:     */         {
/* 3568:3392 */           localObject4 = local_Request.getOutputStream();
/* 3569:3393 */           if (local_Request.isRMI()) {
/* 3570:3393 */             local_Request.write_value(paramTypeRecIntraTrnRqV1, TypeRecIntraTrnRqV1.class);
/* 3571:     */           } else {
/* 3572:3393 */             TypeRecIntraTrnRqV1Helper.write((OutputStream)localObject4, paramTypeRecIntraTrnRqV1);
/* 3573:     */           }
/* 3574:3394 */           if (local_Request.isRMI()) {
/* 3575:3394 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3576:     */           } else {
/* 3577:3394 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3578:     */           }
/* 3579:     */         }
/* 3580:     */         else
/* 3581:     */         {
/* 3582:3398 */           localObject4 = local_Request.getOutputStream();
/* 3583:3399 */           localLocalFrame.add(paramTypeRecIntraTrnRqV1);
/* 3584:3400 */           localLocalFrame.add(paramTypeUserData);
/* 3585:     */         }
/* 3586:3402 */         local_Request.invoke();
/* 3587:     */         Object localObject5;
/* 3588:     */         Object localObject1;
/* 3589:3403 */         if (bool1)
/* 3590:     */         {
/* 3591:3405 */           localObject4 = null;
/* 3592:3406 */           localObject5 = localLocalFrame.getResult();
/* 3593:3407 */           if (localObject5 != null) {
/* 3594:3409 */             localObject4 = (TypeRecIntraTrnRsV1)ObjectVal.clone(localObject5);
/* 3595:     */           }
/* 3596:3411 */           return localObject4;
/* 3597:     */         }
/* 3598:3413 */         Object localObject4 = local_Request.getInputStream();
/* 3599:3415 */         if (local_Request.isRMI()) {
/* 3600:3415 */           localObject5 = (TypeRecIntraTrnRsV1)local_Request.read_value(TypeRecIntraTrnRsV1.class);
/* 3601:     */         } else {
/* 3602:3415 */           localObject5 = TypeRecIntraTrnRsV1Helper.read((InputStream)localObject4);
/* 3603:     */         }
/* 3604:3416 */         return localObject5;
/* 3605:     */       }
/* 3606:     */       catch (TRANSIENT localTRANSIENT)
/* 3607:     */       {
/* 3608:3420 */         if (i == 10) {
/* 3609:3422 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3610:     */         }
/* 3611:     */       }
/* 3612:     */       catch (UserException localUserException)
/* 3613:     */       {
/* 3614:3427 */         local_Request.isRMI();
/* 3615:     */         
/* 3616:     */ 
/* 3617:3430 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3618:     */       }
/* 3619:     */       catch (SystemException localSystemException)
/* 3620:     */       {
/* 3621:3434 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3622:     */       }
/* 3623:     */       finally
/* 3624:     */       {
/* 3625:3438 */         if (local_Request != null) {
/* 3626:3440 */           local_Request.close();
/* 3627:     */         }
/* 3628:3442 */         if (bool1) {
/* 3629:3443 */           localLocalStack.pop(localLocalFrame);
/* 3630:     */         }
/* 3631:3444 */         localLocalStack.setArgsOnLocal(bool2);
/* 3632:     */       }
/* 3633:     */     }
/* 3634:     */   }
/* 3635:     */   
/* 3636:     */   public TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData)
/* 3637:     */     throws java.rmi.RemoteException
/* 3638:     */   {
/* 3639:3454 */     for (int i = 1;; i++)
/* 3640:     */     {
/* 3641:3456 */       _Request local_Request = null;
/* 3642:3457 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3643:3458 */       boolean bool1 = false;
/* 3644:3459 */       LocalFrame localLocalFrame = null;
/* 3645:3460 */       boolean bool2 = false;
/* 3646:     */       try
/* 3647:     */       {
/* 3648:3463 */         local_Request = __request("processRecPmtSyncRqV1");
/* 3649:3464 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3650:3465 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3651:3466 */         localLocalStack.setArgsOnLocal(bool1);
/* 3652:3468 */         if (bool1)
/* 3653:     */         {
/* 3654:3470 */           localLocalFrame = new LocalFrame(2);
/* 3655:3471 */           localLocalStack.push(localLocalFrame);
/* 3656:     */         }
/* 3657:3473 */         if (!bool1)
/* 3658:     */         {
/* 3659:3475 */           localObject4 = local_Request.getOutputStream();
/* 3660:3476 */           if (local_Request.isRMI()) {
/* 3661:3476 */             local_Request.write_value(paramTypeRecPmtSyncRqV1, TypeRecPmtSyncRqV1.class);
/* 3662:     */           } else {
/* 3663:3476 */             TypeRecPmtSyncRqV1Helper.write((OutputStream)localObject4, paramTypeRecPmtSyncRqV1);
/* 3664:     */           }
/* 3665:3477 */           if (local_Request.isRMI()) {
/* 3666:3477 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3667:     */           } else {
/* 3668:3477 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3669:     */           }
/* 3670:     */         }
/* 3671:     */         else
/* 3672:     */         {
/* 3673:3481 */           localObject4 = local_Request.getOutputStream();
/* 3674:3482 */           localLocalFrame.add(paramTypeRecPmtSyncRqV1);
/* 3675:3483 */           localLocalFrame.add(paramTypeUserData);
/* 3676:     */         }
/* 3677:3485 */         local_Request.invoke();
/* 3678:     */         Object localObject5;
/* 3679:     */         Object localObject1;
/* 3680:3486 */         if (bool1)
/* 3681:     */         {
/* 3682:3488 */           localObject4 = null;
/* 3683:3489 */           localObject5 = localLocalFrame.getResult();
/* 3684:3490 */           if (localObject5 != null) {
/* 3685:3492 */             localObject4 = (TypeRecPmtSyncRsV1)ObjectVal.clone(localObject5);
/* 3686:     */           }
/* 3687:3494 */           return localObject4;
/* 3688:     */         }
/* 3689:3496 */         Object localObject4 = local_Request.getInputStream();
/* 3690:3498 */         if (local_Request.isRMI()) {
/* 3691:3498 */           localObject5 = (TypeRecPmtSyncRsV1)local_Request.read_value(TypeRecPmtSyncRsV1.class);
/* 3692:     */         } else {
/* 3693:3498 */           localObject5 = TypeRecPmtSyncRsV1Helper.read((InputStream)localObject4);
/* 3694:     */         }
/* 3695:3499 */         return localObject5;
/* 3696:     */       }
/* 3697:     */       catch (TRANSIENT localTRANSIENT)
/* 3698:     */       {
/* 3699:3503 */         if (i == 10) {
/* 3700:3505 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3701:     */         }
/* 3702:     */       }
/* 3703:     */       catch (UserException localUserException)
/* 3704:     */       {
/* 3705:3510 */         local_Request.isRMI();
/* 3706:     */         
/* 3707:     */ 
/* 3708:3513 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3709:     */       }
/* 3710:     */       catch (SystemException localSystemException)
/* 3711:     */       {
/* 3712:3517 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3713:     */       }
/* 3714:     */       finally
/* 3715:     */       {
/* 3716:3521 */         if (local_Request != null) {
/* 3717:3523 */           local_Request.close();
/* 3718:     */         }
/* 3719:3525 */         if (bool1) {
/* 3720:3526 */           localLocalStack.pop(localLocalFrame);
/* 3721:     */         }
/* 3722:3527 */         localLocalStack.setArgsOnLocal(bool2);
/* 3723:     */       }
/* 3724:     */     }
/* 3725:     */   }
/* 3726:     */   
/* 3727:     */   public TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
/* 3728:     */     throws java.rmi.RemoteException
/* 3729:     */   {
/* 3730:3537 */     for (int i = 1;; i++)
/* 3731:     */     {
/* 3732:3539 */       _Request local_Request = null;
/* 3733:3540 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3734:3541 */       boolean bool1 = false;
/* 3735:3542 */       LocalFrame localLocalFrame = null;
/* 3736:3543 */       boolean bool2 = false;
/* 3737:     */       try
/* 3738:     */       {
/* 3739:3546 */         local_Request = __request("processRecPmtTrnRqV1");
/* 3740:3547 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3741:3548 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3742:3549 */         localLocalStack.setArgsOnLocal(bool1);
/* 3743:3551 */         if (bool1)
/* 3744:     */         {
/* 3745:3553 */           localLocalFrame = new LocalFrame(2);
/* 3746:3554 */           localLocalStack.push(localLocalFrame);
/* 3747:     */         }
/* 3748:3556 */         if (!bool1)
/* 3749:     */         {
/* 3750:3558 */           localObject4 = local_Request.getOutputStream();
/* 3751:3559 */           if (local_Request.isRMI()) {
/* 3752:3559 */             local_Request.write_value(paramTypeRecPmtTrnRqV1, TypeRecPmtTrnRqV1.class);
/* 3753:     */           } else {
/* 3754:3559 */             TypeRecPmtTrnRqV1Helper.write((OutputStream)localObject4, paramTypeRecPmtTrnRqV1);
/* 3755:     */           }
/* 3756:3560 */           if (local_Request.isRMI()) {
/* 3757:3560 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3758:     */           } else {
/* 3759:3560 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3760:     */           }
/* 3761:     */         }
/* 3762:     */         else
/* 3763:     */         {
/* 3764:3564 */           localObject4 = local_Request.getOutputStream();
/* 3765:3565 */           localLocalFrame.add(paramTypeRecPmtTrnRqV1);
/* 3766:3566 */           localLocalFrame.add(paramTypeUserData);
/* 3767:     */         }
/* 3768:3568 */         local_Request.invoke();
/* 3769:     */         Object localObject5;
/* 3770:     */         Object localObject1;
/* 3771:3569 */         if (bool1)
/* 3772:     */         {
/* 3773:3571 */           localObject4 = null;
/* 3774:3572 */           localObject5 = localLocalFrame.getResult();
/* 3775:3573 */           if (localObject5 != null) {
/* 3776:3575 */             localObject4 = (TypeRecPmtTrnRsV1)ObjectVal.clone(localObject5);
/* 3777:     */           }
/* 3778:3577 */           return localObject4;
/* 3779:     */         }
/* 3780:3579 */         Object localObject4 = local_Request.getInputStream();
/* 3781:3581 */         if (local_Request.isRMI()) {
/* 3782:3581 */           localObject5 = (TypeRecPmtTrnRsV1)local_Request.read_value(TypeRecPmtTrnRsV1.class);
/* 3783:     */         } else {
/* 3784:3581 */           localObject5 = TypeRecPmtTrnRsV1Helper.read((InputStream)localObject4);
/* 3785:     */         }
/* 3786:3582 */         return localObject5;
/* 3787:     */       }
/* 3788:     */       catch (TRANSIENT localTRANSIENT)
/* 3789:     */       {
/* 3790:3586 */         if (i == 10) {
/* 3791:3588 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3792:     */         }
/* 3793:     */       }
/* 3794:     */       catch (UserException localUserException)
/* 3795:     */       {
/* 3796:3593 */         local_Request.isRMI();
/* 3797:     */         
/* 3798:     */ 
/* 3799:3596 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3800:     */       }
/* 3801:     */       catch (SystemException localSystemException)
/* 3802:     */       {
/* 3803:3600 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3804:     */       }
/* 3805:     */       finally
/* 3806:     */       {
/* 3807:3604 */         if (local_Request != null) {
/* 3808:3606 */           local_Request.close();
/* 3809:     */         }
/* 3810:3608 */         if (bool1) {
/* 3811:3609 */           localLocalStack.pop(localLocalFrame);
/* 3812:     */         }
/* 3813:3610 */         localLocalStack.setArgsOnLocal(bool2);
/* 3814:     */       }
/* 3815:     */     }
/* 3816:     */   }
/* 3817:     */   
/* 3818:     */   public String[] getPayeeNames(String paramString, int paramInt)
/* 3819:     */     throws java.rmi.RemoteException
/* 3820:     */   {
/* 3821:3620 */     for (int i = 1;; i++)
/* 3822:     */     {
/* 3823:3622 */       _Request local_Request = null;
/* 3824:3623 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3825:3624 */       boolean bool1 = false;
/* 3826:3625 */       LocalFrame localLocalFrame = null;
/* 3827:3626 */       boolean bool2 = false;
/* 3828:     */       try
/* 3829:     */       {
/* 3830:3629 */         local_Request = __request("getPayeeNames__string__long", "getPayeeNames__CORBA_WStringValue__long");
/* 3831:3630 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3832:3631 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3833:3632 */         localLocalStack.setArgsOnLocal(bool1);
/* 3834:3634 */         if (bool1)
/* 3835:     */         {
/* 3836:3636 */           localLocalFrame = new LocalFrame(2);
/* 3837:3637 */           localLocalStack.push(localLocalFrame);
/* 3838:     */         }
/* 3839:3639 */         if (!bool1)
/* 3840:     */         {
/* 3841:3641 */           localObject4 = local_Request.getOutputStream();
/* 3842:3642 */           local_Request.write_string(paramString);
/* 3843:3643 */           ((OutputStream)localObject4).write_long(paramInt);
/* 3844:     */         }
/* 3845:     */         else
/* 3846:     */         {
/* 3847:3647 */           localObject4 = local_Request.getOutputStream();
/* 3848:3648 */           localLocalFrame.add(paramString);
/* 3849:3649 */           localLocalFrame.add(paramInt);
/* 3850:     */         }
/* 3851:3651 */         local_Request.invoke();
/* 3852:     */         Object localObject5;
/* 3853:     */         Object localObject1;
/* 3854:3652 */         if (bool1)
/* 3855:     */         {
/* 3856:3654 */           localObject4 = null;
/* 3857:3655 */           localObject5 = localLocalFrame.getResult();
/* 3858:3656 */           if (localObject5 != null) {
/* 3859:3658 */             localObject4 = (String[])ObjectVal.clone(localObject5);
/* 3860:     */           }
/* 3861:3660 */           return localObject4;
/* 3862:     */         }
/* 3863:3662 */         Object localObject4 = local_Request.getInputStream();
/* 3864:3664 */         if (local_Request.isRMI()) {
/* 3865:3664 */           localObject5 = (String[])local_Request.read_value(new String[0].getClass());
/* 3866:     */         } else {
/* 3867:3664 */           localObject5 = StringSeqHelper.read((InputStream)localObject4);
/* 3868:     */         }
/* 3869:3665 */         return localObject5;
/* 3870:     */       }
/* 3871:     */       catch (TRANSIENT localTRANSIENT)
/* 3872:     */       {
/* 3873:3669 */         if (i == 10) {
/* 3874:3671 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3875:     */         }
/* 3876:     */       }
/* 3877:     */       catch (UserException localUserException)
/* 3878:     */       {
/* 3879:3676 */         local_Request.isRMI();
/* 3880:     */         
/* 3881:     */ 
/* 3882:3679 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3883:     */       }
/* 3884:     */       catch (SystemException localSystemException)
/* 3885:     */       {
/* 3886:3683 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3887:     */       }
/* 3888:     */       finally
/* 3889:     */       {
/* 3890:3687 */         if (local_Request != null) {
/* 3891:3689 */           local_Request.close();
/* 3892:     */         }
/* 3893:3691 */         if (bool1) {
/* 3894:3692 */           localLocalStack.pop(localLocalFrame);
/* 3895:     */         }
/* 3896:3693 */         localLocalStack.setArgsOnLocal(bool2);
/* 3897:     */       }
/* 3898:     */     }
/* 3899:     */   }
/* 3900:     */   
/* 3901:     */   public String[] getPayeeNames(String paramString)
/* 3902:     */     throws java.rmi.RemoteException
/* 3903:     */   {
/* 3904:3702 */     for (int i = 1;; i++)
/* 3905:     */     {
/* 3906:3704 */       _Request local_Request = null;
/* 3907:3705 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3908:3706 */       boolean bool1 = false;
/* 3909:3707 */       LocalFrame localLocalFrame = null;
/* 3910:3708 */       boolean bool2 = false;
/* 3911:     */       try
/* 3912:     */       {
/* 3913:3711 */         local_Request = __request("getPayeeNames__string", "getPayeeNames__CORBA_WStringValue");
/* 3914:3712 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3915:3713 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3916:3714 */         localLocalStack.setArgsOnLocal(bool1);
/* 3917:3716 */         if (bool1)
/* 3918:     */         {
/* 3919:3718 */           localLocalFrame = new LocalFrame(1);
/* 3920:3719 */           localLocalStack.push(localLocalFrame);
/* 3921:     */         }
/* 3922:3721 */         if (!bool1)
/* 3923:     */         {
/* 3924:3723 */           localObject4 = local_Request.getOutputStream();
/* 3925:3724 */           local_Request.write_string(paramString);
/* 3926:     */         }
/* 3927:     */         else
/* 3928:     */         {
/* 3929:3728 */           localObject4 = local_Request.getOutputStream();
/* 3930:3729 */           localLocalFrame.add(paramString);
/* 3931:     */         }
/* 3932:3731 */         local_Request.invoke();
/* 3933:     */         Object localObject5;
/* 3934:     */         Object localObject1;
/* 3935:3732 */         if (bool1)
/* 3936:     */         {
/* 3937:3734 */           localObject4 = null;
/* 3938:3735 */           localObject5 = localLocalFrame.getResult();
/* 3939:3736 */           if (localObject5 != null) {
/* 3940:3738 */             localObject4 = (String[])ObjectVal.clone(localObject5);
/* 3941:     */           }
/* 3942:3740 */           return localObject4;
/* 3943:     */         }
/* 3944:3742 */         Object localObject4 = local_Request.getInputStream();
/* 3945:3744 */         if (local_Request.isRMI()) {
/* 3946:3744 */           localObject5 = (String[])local_Request.read_value(new String[0].getClass());
/* 3947:     */         } else {
/* 3948:3744 */           localObject5 = StringSeqHelper.read((InputStream)localObject4);
/* 3949:     */         }
/* 3950:3745 */         return localObject5;
/* 3951:     */       }
/* 3952:     */       catch (TRANSIENT localTRANSIENT)
/* 3953:     */       {
/* 3954:3749 */         if (i == 10) {
/* 3955:3751 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3956:     */         }
/* 3957:     */       }
/* 3958:     */       catch (UserException localUserException)
/* 3959:     */       {
/* 3960:3756 */         local_Request.isRMI();
/* 3961:     */         
/* 3962:     */ 
/* 3963:3759 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3964:     */       }
/* 3965:     */       catch (SystemException localSystemException)
/* 3966:     */       {
/* 3967:3763 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3968:     */       }
/* 3969:     */       finally
/* 3970:     */       {
/* 3971:3767 */         if (local_Request != null) {
/* 3972:3769 */           local_Request.close();
/* 3973:     */         }
/* 3974:3771 */         if (bool1) {
/* 3975:3772 */           localLocalStack.pop(localLocalFrame);
/* 3976:     */         }
/* 3977:3773 */         localLocalStack.setArgsOnLocal(bool2);
/* 3978:     */       }
/* 3979:     */     }
/* 3980:     */   }
/* 3981:     */   
/* 3982:     */   public String[] getPayeeIDs(String paramString)
/* 3983:     */     throws java.rmi.RemoteException
/* 3984:     */   {
/* 3985:3782 */     for (int i = 1;; i++)
/* 3986:     */     {
/* 3987:3784 */       _Request local_Request = null;
/* 3988:3785 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3989:3786 */       boolean bool1 = false;
/* 3990:3787 */       LocalFrame localLocalFrame = null;
/* 3991:3788 */       boolean bool2 = false;
/* 3992:     */       try
/* 3993:     */       {
/* 3994:3791 */         local_Request = __request("getPayeeIDs");
/* 3995:3792 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3996:3793 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3997:3794 */         localLocalStack.setArgsOnLocal(bool1);
/* 3998:3796 */         if (bool1)
/* 3999:     */         {
/* 4000:3798 */           localLocalFrame = new LocalFrame(1);
/* 4001:3799 */           localLocalStack.push(localLocalFrame);
/* 4002:     */         }
/* 4003:3801 */         if (!bool1)
/* 4004:     */         {
/* 4005:3803 */           localObject4 = local_Request.getOutputStream();
/* 4006:3804 */           local_Request.write_string(paramString);
/* 4007:     */         }
/* 4008:     */         else
/* 4009:     */         {
/* 4010:3808 */           localObject4 = local_Request.getOutputStream();
/* 4011:3809 */           localLocalFrame.add(paramString);
/* 4012:     */         }
/* 4013:3811 */         local_Request.invoke();
/* 4014:     */         Object localObject5;
/* 4015:     */         Object localObject1;
/* 4016:3812 */         if (bool1)
/* 4017:     */         {
/* 4018:3814 */           localObject4 = null;
/* 4019:3815 */           localObject5 = localLocalFrame.getResult();
/* 4020:3816 */           if (localObject5 != null) {
/* 4021:3818 */             localObject4 = (String[])ObjectVal.clone(localObject5);
/* 4022:     */           }
/* 4023:3820 */           return localObject4;
/* 4024:     */         }
/* 4025:3822 */         Object localObject4 = local_Request.getInputStream();
/* 4026:3824 */         if (local_Request.isRMI()) {
/* 4027:3824 */           localObject5 = (String[])local_Request.read_value(new String[0].getClass());
/* 4028:     */         } else {
/* 4029:3824 */           localObject5 = StringSeqHelper.read((InputStream)localObject4);
/* 4030:     */         }
/* 4031:3825 */         return localObject5;
/* 4032:     */       }
/* 4033:     */       catch (TRANSIENT localTRANSIENT)
/* 4034:     */       {
/* 4035:3829 */         if (i == 10) {
/* 4036:3831 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4037:     */         }
/* 4038:     */       }
/* 4039:     */       catch (UserException localUserException)
/* 4040:     */       {
/* 4041:3836 */         local_Request.isRMI();
/* 4042:     */         
/* 4043:     */ 
/* 4044:3839 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4045:     */       }
/* 4046:     */       catch (SystemException localSystemException)
/* 4047:     */       {
/* 4048:3843 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4049:     */       }
/* 4050:     */       finally
/* 4051:     */       {
/* 4052:3847 */         if (local_Request != null) {
/* 4053:3849 */           local_Request.close();
/* 4054:     */         }
/* 4055:3851 */         if (bool1) {
/* 4056:3852 */           localLocalStack.pop(localLocalFrame);
/* 4057:     */         }
/* 4058:3853 */         localLocalStack.setArgsOnLocal(bool2);
/* 4059:     */       }
/* 4060:     */     }
/* 4061:     */   }
/* 4062:     */   
/* 4063:     */   public PayeeInfo[] getPayees(String paramString, int paramInt)
/* 4064:     */     throws java.rmi.RemoteException
/* 4065:     */   {
/* 4066:3863 */     for (int i = 1;; i++)
/* 4067:     */     {
/* 4068:3865 */       _Request local_Request = null;
/* 4069:3866 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4070:3867 */       boolean bool1 = false;
/* 4071:3868 */       LocalFrame localLocalFrame = null;
/* 4072:3869 */       boolean bool2 = false;
/* 4073:     */       try
/* 4074:     */       {
/* 4075:3872 */         local_Request = __request("getPayees__string__long", "getPayees__CORBA_WStringValue__long");
/* 4076:3873 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4077:3874 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4078:3875 */         localLocalStack.setArgsOnLocal(bool1);
/* 4079:3877 */         if (bool1)
/* 4080:     */         {
/* 4081:3879 */           localLocalFrame = new LocalFrame(2);
/* 4082:3880 */           localLocalStack.push(localLocalFrame);
/* 4083:     */         }
/* 4084:3882 */         if (!bool1)
/* 4085:     */         {
/* 4086:3884 */           localObject4 = local_Request.getOutputStream();
/* 4087:3885 */           local_Request.write_string(paramString);
/* 4088:3886 */           ((OutputStream)localObject4).write_long(paramInt);
/* 4089:     */         }
/* 4090:     */         else
/* 4091:     */         {
/* 4092:3890 */           localObject4 = local_Request.getOutputStream();
/* 4093:3891 */           localLocalFrame.add(paramString);
/* 4094:3892 */           localLocalFrame.add(paramInt);
/* 4095:     */         }
/* 4096:3894 */         local_Request.invoke();
/* 4097:     */         Object localObject5;
/* 4098:     */         Object localObject1;
/* 4099:3895 */         if (bool1)
/* 4100:     */         {
/* 4101:3897 */           localObject4 = null;
/* 4102:3898 */           localObject5 = localLocalFrame.getResult();
/* 4103:3899 */           if (localObject5 != null) {
/* 4104:3901 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 4105:     */           }
/* 4106:3903 */           return localObject4;
/* 4107:     */         }
/* 4108:3905 */         Object localObject4 = local_Request.getInputStream();
/* 4109:3907 */         if (local_Request.isRMI()) {
/* 4110:3907 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 4111:     */         } else {
/* 4112:3907 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 4113:     */         }
/* 4114:3908 */         return localObject5;
/* 4115:     */       }
/* 4116:     */       catch (TRANSIENT localTRANSIENT)
/* 4117:     */       {
/* 4118:3912 */         if (i == 10) {
/* 4119:3914 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4120:     */         }
/* 4121:     */       }
/* 4122:     */       catch (UserException localUserException)
/* 4123:     */       {
/* 4124:3919 */         local_Request.isRMI();
/* 4125:     */         
/* 4126:     */ 
/* 4127:3922 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4128:     */       }
/* 4129:     */       catch (SystemException localSystemException)
/* 4130:     */       {
/* 4131:3926 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4132:     */       }
/* 4133:     */       finally
/* 4134:     */       {
/* 4135:3930 */         if (local_Request != null) {
/* 4136:3932 */           local_Request.close();
/* 4137:     */         }
/* 4138:3934 */         if (bool1) {
/* 4139:3935 */           localLocalStack.pop(localLocalFrame);
/* 4140:     */         }
/* 4141:3936 */         localLocalStack.setArgsOnLocal(bool2);
/* 4142:     */       }
/* 4143:     */     }
/* 4144:     */   }
/* 4145:     */   
/* 4146:     */   public PayeeInfo[] getPayees(String paramString)
/* 4147:     */     throws java.rmi.RemoteException
/* 4148:     */   {
/* 4149:3945 */     for (int i = 1;; i++)
/* 4150:     */     {
/* 4151:3947 */       _Request local_Request = null;
/* 4152:3948 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4153:3949 */       boolean bool1 = false;
/* 4154:3950 */       LocalFrame localLocalFrame = null;
/* 4155:3951 */       boolean bool2 = false;
/* 4156:     */       try
/* 4157:     */       {
/* 4158:3954 */         local_Request = __request("getPayees__string", "getPayees__CORBA_WStringValue");
/* 4159:3955 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4160:3956 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4161:3957 */         localLocalStack.setArgsOnLocal(bool1);
/* 4162:3959 */         if (bool1)
/* 4163:     */         {
/* 4164:3961 */           localLocalFrame = new LocalFrame(1);
/* 4165:3962 */           localLocalStack.push(localLocalFrame);
/* 4166:     */         }
/* 4167:3964 */         if (!bool1)
/* 4168:     */         {
/* 4169:3966 */           localObject4 = local_Request.getOutputStream();
/* 4170:3967 */           local_Request.write_string(paramString);
/* 4171:     */         }
/* 4172:     */         else
/* 4173:     */         {
/* 4174:3971 */           localObject4 = local_Request.getOutputStream();
/* 4175:3972 */           localLocalFrame.add(paramString);
/* 4176:     */         }
/* 4177:3974 */         local_Request.invoke();
/* 4178:     */         Object localObject5;
/* 4179:     */         Object localObject1;
/* 4180:3975 */         if (bool1)
/* 4181:     */         {
/* 4182:3977 */           localObject4 = null;
/* 4183:3978 */           localObject5 = localLocalFrame.getResult();
/* 4184:3979 */           if (localObject5 != null) {
/* 4185:3981 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 4186:     */           }
/* 4187:3983 */           return localObject4;
/* 4188:     */         }
/* 4189:3985 */         Object localObject4 = local_Request.getInputStream();
/* 4190:3987 */         if (local_Request.isRMI()) {
/* 4191:3987 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 4192:     */         } else {
/* 4193:3987 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 4194:     */         }
/* 4195:3988 */         return localObject5;
/* 4196:     */       }
/* 4197:     */       catch (TRANSIENT localTRANSIENT)
/* 4198:     */       {
/* 4199:3992 */         if (i == 10) {
/* 4200:3994 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4201:     */         }
/* 4202:     */       }
/* 4203:     */       catch (UserException localUserException)
/* 4204:     */       {
/* 4205:3999 */         local_Request.isRMI();
/* 4206:     */         
/* 4207:     */ 
/* 4208:4002 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4209:     */       }
/* 4210:     */       catch (SystemException localSystemException)
/* 4211:     */       {
/* 4212:4006 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4213:     */       }
/* 4214:     */       finally
/* 4215:     */       {
/* 4216:4010 */         if (local_Request != null) {
/* 4217:4012 */           local_Request.close();
/* 4218:     */         }
/* 4219:4014 */         if (bool1) {
/* 4220:4015 */           localLocalStack.pop(localLocalFrame);
/* 4221:     */         }
/* 4222:4016 */         localLocalStack.setArgsOnLocal(bool2);
/* 4223:     */       }
/* 4224:     */     }
/* 4225:     */   }
/* 4226:     */   
/* 4227:     */   public PayeeInfo[] searchGlobalPayees(String paramString)
/* 4228:     */     throws java.rmi.RemoteException
/* 4229:     */   {
/* 4230:4025 */     for (int i = 1;; i++)
/* 4231:     */     {
/* 4232:4027 */       _Request local_Request = null;
/* 4233:4028 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4234:4029 */       boolean bool1 = false;
/* 4235:4030 */       LocalFrame localLocalFrame = null;
/* 4236:4031 */       boolean bool2 = false;
/* 4237:     */       try
/* 4238:     */       {
/* 4239:4034 */         local_Request = __request("searchGlobalPayees");
/* 4240:4035 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4241:4036 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4242:4037 */         localLocalStack.setArgsOnLocal(bool1);
/* 4243:4039 */         if (bool1)
/* 4244:     */         {
/* 4245:4041 */           localLocalFrame = new LocalFrame(1);
/* 4246:4042 */           localLocalStack.push(localLocalFrame);
/* 4247:     */         }
/* 4248:4044 */         if (!bool1)
/* 4249:     */         {
/* 4250:4046 */           localObject4 = local_Request.getOutputStream();
/* 4251:4047 */           local_Request.write_string(paramString);
/* 4252:     */         }
/* 4253:     */         else
/* 4254:     */         {
/* 4255:4051 */           localObject4 = local_Request.getOutputStream();
/* 4256:4052 */           localLocalFrame.add(paramString);
/* 4257:     */         }
/* 4258:4054 */         local_Request.invoke();
/* 4259:     */         Object localObject5;
/* 4260:     */         Object localObject1;
/* 4261:4055 */         if (bool1)
/* 4262:     */         {
/* 4263:4057 */           localObject4 = null;
/* 4264:4058 */           localObject5 = localLocalFrame.getResult();
/* 4265:4059 */           if (localObject5 != null) {
/* 4266:4061 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 4267:     */           }
/* 4268:4063 */           return localObject4;
/* 4269:     */         }
/* 4270:4065 */         Object localObject4 = local_Request.getInputStream();
/* 4271:4067 */         if (local_Request.isRMI()) {
/* 4272:4067 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 4273:     */         } else {
/* 4274:4067 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 4275:     */         }
/* 4276:4068 */         return localObject5;
/* 4277:     */       }
/* 4278:     */       catch (TRANSIENT localTRANSIENT)
/* 4279:     */       {
/* 4280:4072 */         if (i == 10) {
/* 4281:4074 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4282:     */         }
/* 4283:     */       }
/* 4284:     */       catch (UserException localUserException)
/* 4285:     */       {
/* 4286:4079 */         local_Request.isRMI();
/* 4287:     */         
/* 4288:     */ 
/* 4289:4082 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4290:     */       }
/* 4291:     */       catch (SystemException localSystemException)
/* 4292:     */       {
/* 4293:4086 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4294:     */       }
/* 4295:     */       finally
/* 4296:     */       {
/* 4297:4090 */         if (local_Request != null) {
/* 4298:4092 */           local_Request.close();
/* 4299:     */         }
/* 4300:4094 */         if (bool1) {
/* 4301:4095 */           localLocalStack.pop(localLocalFrame);
/* 4302:     */         }
/* 4303:4096 */         localLocalStack.setArgsOnLocal(bool2);
/* 4304:     */       }
/* 4305:     */     }
/* 4306:     */   }
/* 4307:     */   
/* 4308:     */   public PayeeInfo[] updatePayee(PayeeInfo paramPayeeInfo, int paramInt)
/* 4309:     */     throws java.rmi.RemoteException
/* 4310:     */   {
/* 4311:4106 */     for (int i = 1;; i++)
/* 4312:     */     {
/* 4313:4108 */       _Request local_Request = null;
/* 4314:4109 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4315:4110 */       boolean bool1 = false;
/* 4316:4111 */       LocalFrame localLocalFrame = null;
/* 4317:4112 */       boolean bool2 = false;
/* 4318:     */       try
/* 4319:     */       {
/* 4320:4115 */         local_Request = __request("updatePayee__PayeeInfo__long", "updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__long");
/* 4321:4116 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4322:4117 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4323:4118 */         localLocalStack.setArgsOnLocal(bool1);
/* 4324:4120 */         if (bool1)
/* 4325:     */         {
/* 4326:4122 */           localLocalFrame = new LocalFrame(2);
/* 4327:4123 */           localLocalStack.push(localLocalFrame);
/* 4328:     */         }
/* 4329:4125 */         if (!bool1)
/* 4330:     */         {
/* 4331:4127 */           localObject4 = local_Request.getOutputStream();
/* 4332:4128 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 4333:4129 */           ((OutputStream)localObject4).write_long(paramInt);
/* 4334:     */         }
/* 4335:     */         else
/* 4336:     */         {
/* 4337:4133 */           localObject4 = local_Request.getOutputStream();
/* 4338:4134 */           localLocalFrame.add(paramPayeeInfo);
/* 4339:4135 */           localLocalFrame.add(paramInt);
/* 4340:     */         }
/* 4341:4137 */         local_Request.invoke();
/* 4342:     */         Object localObject5;
/* 4343:     */         Object localObject1;
/* 4344:4138 */         if (bool1)
/* 4345:     */         {
/* 4346:4140 */           localObject4 = null;
/* 4347:4141 */           localObject5 = localLocalFrame.getResult();
/* 4348:4142 */           if (localObject5 != null) {
/* 4349:4144 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 4350:     */           }
/* 4351:4146 */           return localObject4;
/* 4352:     */         }
/* 4353:4148 */         Object localObject4 = local_Request.getInputStream();
/* 4354:4150 */         if (local_Request.isRMI()) {
/* 4355:4150 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 4356:     */         } else {
/* 4357:4150 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 4358:     */         }
/* 4359:4151 */         return localObject5;
/* 4360:     */       }
/* 4361:     */       catch (TRANSIENT localTRANSIENT)
/* 4362:     */       {
/* 4363:4155 */         if (i == 10) {
/* 4364:4157 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4365:     */         }
/* 4366:     */       }
/* 4367:     */       catch (UserException localUserException)
/* 4368:     */       {
/* 4369:4162 */         local_Request.isRMI();
/* 4370:     */         
/* 4371:     */ 
/* 4372:4165 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4373:     */       }
/* 4374:     */       catch (SystemException localSystemException)
/* 4375:     */       {
/* 4376:4169 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4377:     */       }
/* 4378:     */       finally
/* 4379:     */       {
/* 4380:4173 */         if (local_Request != null) {
/* 4381:4175 */           local_Request.close();
/* 4382:     */         }
/* 4383:4177 */         if (bool1) {
/* 4384:4178 */           localLocalStack.pop(localLocalFrame);
/* 4385:     */         }
/* 4386:4179 */         localLocalStack.setArgsOnLocal(bool2);
/* 4387:     */       }
/* 4388:     */     }
/* 4389:     */   }
/* 4390:     */   
/* 4391:     */   public void updatePayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
/* 4392:     */     throws java.rmi.RemoteException
/* 4393:     */   {
/* 4394:4189 */     for (int i = 1;; i++)
/* 4395:     */     {
/* 4396:4191 */       _Request local_Request = null;
/* 4397:4192 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4398:4193 */       boolean bool1 = false;
/* 4399:4194 */       LocalFrame localLocalFrame = null;
/* 4400:4195 */       boolean bool2 = false;
/* 4401:     */       try
/* 4402:     */       {
/* 4403:4198 */         local_Request = __request("updatePayee__PayeeInfo__PayeeRouteInfo", "updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeRouteInfo");
/* 4404:4199 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4405:4200 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4406:4201 */         localLocalStack.setArgsOnLocal(bool1);
/* 4407:4203 */         if (bool1)
/* 4408:     */         {
/* 4409:4205 */           localLocalFrame = new LocalFrame(2);
/* 4410:4206 */           localLocalStack.push(localLocalFrame);
/* 4411:     */         }
/* 4412:     */         OutputStream localOutputStream;
/* 4413:4208 */         if (!bool1)
/* 4414:     */         {
/* 4415:4210 */           localOutputStream = local_Request.getOutputStream();
/* 4416:4211 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 4417:4212 */           local_Request.write_value(paramPayeeRouteInfo, PayeeRouteInfo.class);
/* 4418:     */         }
/* 4419:     */         else
/* 4420:     */         {
/* 4421:4216 */           localOutputStream = local_Request.getOutputStream();
/* 4422:4217 */           localLocalFrame.add(paramPayeeInfo);
/* 4423:4218 */           localLocalFrame.add(paramPayeeRouteInfo);
/* 4424:     */         }
/* 4425:4220 */         local_Request.invoke();
/* 4426:4221 */         return;
/* 4427:     */       }
/* 4428:     */       catch (TRANSIENT localTRANSIENT)
/* 4429:     */       {
/* 4430:4225 */         if (i == 10) {
/* 4431:4227 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4432:     */         }
/* 4433:     */       }
/* 4434:     */       catch (UserException localUserException)
/* 4435:     */       {
/* 4436:4232 */         local_Request.isRMI();
/* 4437:     */         
/* 4438:     */ 
/* 4439:4235 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4440:     */       }
/* 4441:     */       catch (SystemException localSystemException)
/* 4442:     */       {
/* 4443:4239 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4444:     */       }
/* 4445:     */       finally
/* 4446:     */       {
/* 4447:4243 */         if (local_Request != null) {
/* 4448:4245 */           local_Request.close();
/* 4449:     */         }
/* 4450:4247 */         if (bool1) {
/* 4451:4248 */           localLocalStack.pop(localLocalFrame);
/* 4452:     */         }
/* 4453:4249 */         localLocalStack.setArgsOnLocal(bool2);
/* 4454:     */       }
/* 4455:     */     }
/* 4456:     */   }
/* 4457:     */   
/* 4458:     */   public void deletePayee(String paramString)
/* 4459:     */     throws java.rmi.RemoteException
/* 4460:     */   {
/* 4461:4258 */     for (int i = 1;; i++)
/* 4462:     */     {
/* 4463:4260 */       _Request local_Request = null;
/* 4464:4261 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4465:4262 */       boolean bool1 = false;
/* 4466:4263 */       LocalFrame localLocalFrame = null;
/* 4467:4264 */       boolean bool2 = false;
/* 4468:     */       try
/* 4469:     */       {
/* 4470:4267 */         local_Request = __request("deletePayee");
/* 4471:4268 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4472:4269 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4473:4270 */         localLocalStack.setArgsOnLocal(bool1);
/* 4474:4272 */         if (bool1)
/* 4475:     */         {
/* 4476:4274 */           localLocalFrame = new LocalFrame(1);
/* 4477:4275 */           localLocalStack.push(localLocalFrame);
/* 4478:     */         }
/* 4479:     */         OutputStream localOutputStream;
/* 4480:4277 */         if (!bool1)
/* 4481:     */         {
/* 4482:4279 */           localOutputStream = local_Request.getOutputStream();
/* 4483:4280 */           local_Request.write_string(paramString);
/* 4484:     */         }
/* 4485:     */         else
/* 4486:     */         {
/* 4487:4284 */           localOutputStream = local_Request.getOutputStream();
/* 4488:4285 */           localLocalFrame.add(paramString);
/* 4489:     */         }
/* 4490:4287 */         local_Request.invoke();
/* 4491:4288 */         return;
/* 4492:     */       }
/* 4493:     */       catch (TRANSIENT localTRANSIENT)
/* 4494:     */       {
/* 4495:4292 */         if (i == 10) {
/* 4496:4294 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4497:     */         }
/* 4498:     */       }
/* 4499:     */       catch (UserException localUserException)
/* 4500:     */       {
/* 4501:4299 */         local_Request.isRMI();
/* 4502:     */         
/* 4503:     */ 
/* 4504:4302 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4505:     */       }
/* 4506:     */       catch (SystemException localSystemException)
/* 4507:     */       {
/* 4508:4306 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4509:     */       }
/* 4510:     */       finally
/* 4511:     */       {
/* 4512:4310 */         if (local_Request != null) {
/* 4513:4312 */           local_Request.close();
/* 4514:     */         }
/* 4515:4314 */         if (bool1) {
/* 4516:4315 */           localLocalStack.pop(localLocalFrame);
/* 4517:     */         }
/* 4518:4316 */         localLocalStack.setArgsOnLocal(bool2);
/* 4519:     */       }
/* 4520:     */     }
/* 4521:     */   }
/* 4522:     */   
/* 4523:     */   public void deletePayees(String[] paramArrayOfString)
/* 4524:     */     throws java.rmi.RemoteException
/* 4525:     */   {
/* 4526:4325 */     for (int i = 1;; i++)
/* 4527:     */     {
/* 4528:4327 */       _Request local_Request = null;
/* 4529:4328 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4530:4329 */       boolean bool1 = false;
/* 4531:4330 */       LocalFrame localLocalFrame = null;
/* 4532:4331 */       boolean bool2 = false;
/* 4533:     */       try
/* 4534:     */       {
/* 4535:4334 */         local_Request = __request("deletePayees");
/* 4536:4335 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4537:4336 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4538:4337 */         localLocalStack.setArgsOnLocal(bool1);
/* 4539:4339 */         if (bool1)
/* 4540:     */         {
/* 4541:4341 */           localLocalFrame = new LocalFrame(1);
/* 4542:4342 */           localLocalStack.push(localLocalFrame);
/* 4543:     */         }
/* 4544:     */         OutputStream localOutputStream;
/* 4545:4344 */         if (!bool1)
/* 4546:     */         {
/* 4547:4346 */           localOutputStream = local_Request.getOutputStream();
/* 4548:4347 */           if (local_Request.isRMI()) {
/* 4549:4347 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 4550:     */           } else {
/* 4551:4347 */             StringSeqHelper.write(localOutputStream, paramArrayOfString);
/* 4552:     */           }
/* 4553:     */         }
/* 4554:     */         else
/* 4555:     */         {
/* 4556:4351 */           localOutputStream = local_Request.getOutputStream();
/* 4557:4352 */           localLocalFrame.add(paramArrayOfString);
/* 4558:     */         }
/* 4559:4354 */         local_Request.invoke();
/* 4560:4355 */         return;
/* 4561:     */       }
/* 4562:     */       catch (TRANSIENT localTRANSIENT)
/* 4563:     */       {
/* 4564:4359 */         if (i == 10) {
/* 4565:4361 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4566:     */         }
/* 4567:     */       }
/* 4568:     */       catch (UserException localUserException)
/* 4569:     */       {
/* 4570:4366 */         local_Request.isRMI();
/* 4571:     */         
/* 4572:     */ 
/* 4573:4369 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4574:     */       }
/* 4575:     */       catch (SystemException localSystemException)
/* 4576:     */       {
/* 4577:4373 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4578:     */       }
/* 4579:     */       finally
/* 4580:     */       {
/* 4581:4377 */         if (local_Request != null) {
/* 4582:4379 */           local_Request.close();
/* 4583:     */         }
/* 4584:4381 */         if (bool1) {
/* 4585:4382 */           localLocalStack.pop(localLocalFrame);
/* 4586:     */         }
/* 4587:4383 */         localLocalStack.setArgsOnLocal(bool2);
/* 4588:     */       }
/* 4589:     */     }
/* 4590:     */   }
/* 4591:     */   
/* 4592:     */   public PayeeInfo findPayeeByID(String paramString)
/* 4593:     */     throws java.rmi.RemoteException
/* 4594:     */   {
/* 4595:4392 */     for (int i = 1;; i++)
/* 4596:     */     {
/* 4597:4394 */       _Request local_Request = null;
/* 4598:4395 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4599:4396 */       boolean bool1 = false;
/* 4600:4397 */       LocalFrame localLocalFrame = null;
/* 4601:4398 */       boolean bool2 = false;
/* 4602:     */       try
/* 4603:     */       {
/* 4604:4401 */         local_Request = __request("findPayeeByID");
/* 4605:4402 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4606:4403 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4607:4404 */         localLocalStack.setArgsOnLocal(bool1);
/* 4608:4406 */         if (bool1)
/* 4609:     */         {
/* 4610:4408 */           localLocalFrame = new LocalFrame(1);
/* 4611:4409 */           localLocalStack.push(localLocalFrame);
/* 4612:     */         }
/* 4613:4411 */         if (!bool1)
/* 4614:     */         {
/* 4615:4413 */           localObject4 = local_Request.getOutputStream();
/* 4616:4414 */           local_Request.write_string(paramString);
/* 4617:     */         }
/* 4618:     */         else
/* 4619:     */         {
/* 4620:4418 */           localObject4 = local_Request.getOutputStream();
/* 4621:4419 */           localLocalFrame.add(paramString);
/* 4622:     */         }
/* 4623:4421 */         local_Request.invoke();
/* 4624:     */         Object localObject1;
/* 4625:4422 */         if (bool1)
/* 4626:     */         {
/* 4627:4424 */           localObject4 = null;
/* 4628:4425 */           localObject5 = localLocalFrame.getResult();
/* 4629:4426 */           if (localObject5 != null) {
/* 4630:4428 */             localObject4 = (PayeeInfo)ObjectVal.clone(localObject5);
/* 4631:     */           }
/* 4632:4430 */           return localObject4;
/* 4633:     */         }
/* 4634:4432 */         Object localObject4 = local_Request.getInputStream();
/* 4635:     */         
/* 4636:4434 */         Object localObject5 = (PayeeInfo)local_Request.read_value(PayeeInfo.class);
/* 4637:4435 */         return localObject5;
/* 4638:     */       }
/* 4639:     */       catch (TRANSIENT localTRANSIENT)
/* 4640:     */       {
/* 4641:4439 */         if (i == 10) {
/* 4642:4441 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4643:     */         }
/* 4644:     */       }
/* 4645:     */       catch (UserException localUserException)
/* 4646:     */       {
/* 4647:4446 */         local_Request.isRMI();
/* 4648:     */         
/* 4649:     */ 
/* 4650:4449 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4651:     */       }
/* 4652:     */       catch (SystemException localSystemException)
/* 4653:     */       {
/* 4654:4453 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4655:     */       }
/* 4656:     */       finally
/* 4657:     */       {
/* 4658:4457 */         if (local_Request != null) {
/* 4659:4459 */           local_Request.close();
/* 4660:     */         }
/* 4661:4461 */         if (bool1) {
/* 4662:4462 */           localLocalStack.pop(localLocalFrame);
/* 4663:     */         }
/* 4664:4463 */         localLocalStack.setArgsOnLocal(bool2);
/* 4665:     */       }
/* 4666:     */     }
/* 4667:     */   }
/* 4668:     */   
/* 4669:     */   public void setPayeeStatus(String paramString1, String paramString2)
/* 4670:     */     throws java.rmi.RemoteException
/* 4671:     */   {
/* 4672:4473 */     for (int i = 1;; i++)
/* 4673:     */     {
/* 4674:4475 */       _Request local_Request = null;
/* 4675:4476 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4676:4477 */       boolean bool1 = false;
/* 4677:4478 */       LocalFrame localLocalFrame = null;
/* 4678:4479 */       boolean bool2 = false;
/* 4679:     */       try
/* 4680:     */       {
/* 4681:4482 */         local_Request = __request("setPayeeStatus");
/* 4682:4483 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4683:4484 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4684:4485 */         localLocalStack.setArgsOnLocal(bool1);
/* 4685:4487 */         if (bool1)
/* 4686:     */         {
/* 4687:4489 */           localLocalFrame = new LocalFrame(2);
/* 4688:4490 */           localLocalStack.push(localLocalFrame);
/* 4689:     */         }
/* 4690:     */         OutputStream localOutputStream;
/* 4691:4492 */         if (!bool1)
/* 4692:     */         {
/* 4693:4494 */           localOutputStream = local_Request.getOutputStream();
/* 4694:4495 */           local_Request.write_string(paramString1);
/* 4695:4496 */           local_Request.write_string(paramString2);
/* 4696:     */         }
/* 4697:     */         else
/* 4698:     */         {
/* 4699:4500 */           localOutputStream = local_Request.getOutputStream();
/* 4700:4501 */           localLocalFrame.add(paramString1);
/* 4701:4502 */           localLocalFrame.add(paramString2);
/* 4702:     */         }
/* 4703:4504 */         local_Request.invoke();
/* 4704:4505 */         return;
/* 4705:     */       }
/* 4706:     */       catch (TRANSIENT localTRANSIENT)
/* 4707:     */       {
/* 4708:4509 */         if (i == 10) {
/* 4709:4511 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4710:     */         }
/* 4711:     */       }
/* 4712:     */       catch (UserException localUserException)
/* 4713:     */       {
/* 4714:4516 */         local_Request.isRMI();
/* 4715:     */         
/* 4716:     */ 
/* 4717:4519 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4718:     */       }
/* 4719:     */       catch (SystemException localSystemException)
/* 4720:     */       {
/* 4721:4523 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4722:     */       }
/* 4723:     */       finally
/* 4724:     */       {
/* 4725:4527 */         if (local_Request != null) {
/* 4726:4529 */           local_Request.close();
/* 4727:     */         }
/* 4728:4531 */         if (bool1) {
/* 4729:4532 */           localLocalStack.pop(localLocalFrame);
/* 4730:     */         }
/* 4731:4533 */         localLocalStack.setArgsOnLocal(bool2);
/* 4732:     */       }
/* 4733:     */     }
/* 4734:     */   }
/* 4735:     */   
/* 4736:     */   public int getSmartDate(int paramInt)
/* 4737:     */     throws java.rmi.RemoteException
/* 4738:     */   {
/* 4739:4542 */     for (int i = 1;; i++)
/* 4740:     */     {
/* 4741:4544 */       _Request local_Request = null;
/* 4742:4545 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4743:4546 */       boolean bool = false;
/* 4744:     */       try
/* 4745:     */       {
/* 4746:4549 */         local_Request = __request("getSmartDate");
/* 4747:4550 */         bool = localLocalStack.isArgsOnLocal();
/* 4748:4551 */         localLocalStack.setArgsOnLocal(false);
/* 4749:4552 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 4750:4553 */         localOutputStream.write_long(paramInt);
/* 4751:4554 */         local_Request.invoke();
/* 4752:4555 */         InputStream localInputStream = local_Request.getInputStream();
/* 4753:     */         
/* 4754:4557 */         int k = localInputStream.read_long();
/* 4755:4558 */         return k;
/* 4756:     */       }
/* 4757:     */       catch (TRANSIENT localTRANSIENT)
/* 4758:     */       {
/* 4759:4562 */         if (i == 10) {
/* 4760:4564 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4761:     */         }
/* 4762:     */       }
/* 4763:     */       catch (UserException localUserException)
/* 4764:     */       {
/* 4765:4569 */         local_Request.isRMI();
/* 4766:     */         
/* 4767:     */ 
/* 4768:4572 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4769:     */       }
/* 4770:     */       catch (SystemException localSystemException)
/* 4771:     */       {
/* 4772:4576 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4773:     */       }
/* 4774:     */       finally
/* 4775:     */       {
/* 4776:4580 */         if (local_Request != null) {
/* 4777:4582 */           local_Request.close();
/* 4778:     */         }
/* 4779:4584 */         localLocalStack.setArgsOnLocal(bool);
/* 4780:     */       }
/* 4781:     */     }
/* 4782:     */   }
/* 4783:     */   
/* 4784:     */   public PayeeInfo[] getGlobalPayees(String paramString, int paramInt)
/* 4785:     */     throws java.rmi.RemoteException
/* 4786:     */   {
/* 4787:4594 */     for (int i = 1;; i++)
/* 4788:     */     {
/* 4789:4596 */       _Request local_Request = null;
/* 4790:4597 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4791:4598 */       boolean bool1 = false;
/* 4792:4599 */       LocalFrame localLocalFrame = null;
/* 4793:4600 */       boolean bool2 = false;
/* 4794:     */       try
/* 4795:     */       {
/* 4796:4603 */         local_Request = __request("getGlobalPayees");
/* 4797:4604 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4798:4605 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4799:4606 */         localLocalStack.setArgsOnLocal(bool1);
/* 4800:4608 */         if (bool1)
/* 4801:     */         {
/* 4802:4610 */           localLocalFrame = new LocalFrame(2);
/* 4803:4611 */           localLocalStack.push(localLocalFrame);
/* 4804:     */         }
/* 4805:4613 */         if (!bool1)
/* 4806:     */         {
/* 4807:4615 */           localObject4 = local_Request.getOutputStream();
/* 4808:4616 */           local_Request.write_string(paramString);
/* 4809:4617 */           ((OutputStream)localObject4).write_long(paramInt);
/* 4810:     */         }
/* 4811:     */         else
/* 4812:     */         {
/* 4813:4621 */           localObject4 = local_Request.getOutputStream();
/* 4814:4622 */           localLocalFrame.add(paramString);
/* 4815:4623 */           localLocalFrame.add(paramInt);
/* 4816:     */         }
/* 4817:4625 */         local_Request.invoke();
/* 4818:     */         Object localObject5;
/* 4819:     */         Object localObject1;
/* 4820:4626 */         if (bool1)
/* 4821:     */         {
/* 4822:4628 */           localObject4 = null;
/* 4823:4629 */           localObject5 = localLocalFrame.getResult();
/* 4824:4630 */           if (localObject5 != null) {
/* 4825:4632 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 4826:     */           }
/* 4827:4634 */           return localObject4;
/* 4828:     */         }
/* 4829:4636 */         Object localObject4 = local_Request.getInputStream();
/* 4830:4638 */         if (local_Request.isRMI()) {
/* 4831:4638 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 4832:     */         } else {
/* 4833:4638 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 4834:     */         }
/* 4835:4639 */         return localObject5;
/* 4836:     */       }
/* 4837:     */       catch (TRANSIENT localTRANSIENT)
/* 4838:     */       {
/* 4839:4643 */         if (i == 10) {
/* 4840:4645 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4841:     */         }
/* 4842:     */       }
/* 4843:     */       catch (UserException localUserException)
/* 4844:     */       {
/* 4845:4650 */         local_Request.isRMI();
/* 4846:     */         
/* 4847:     */ 
/* 4848:4653 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4849:     */       }
/* 4850:     */       catch (SystemException localSystemException)
/* 4851:     */       {
/* 4852:4657 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4853:     */       }
/* 4854:     */       finally
/* 4855:     */       {
/* 4856:4661 */         if (local_Request != null) {
/* 4857:4663 */           local_Request.close();
/* 4858:     */         }
/* 4859:4665 */         if (bool1) {
/* 4860:4666 */           localLocalStack.pop(localLocalFrame);
/* 4861:     */         }
/* 4862:4667 */         localLocalStack.setArgsOnLocal(bool2);
/* 4863:     */       }
/* 4864:     */     }
/* 4865:     */   }
/* 4866:     */   
/* 4867:     */   public String addPayee(PayeeInfo paramPayeeInfo, int paramInt)
/* 4868:     */     throws java.rmi.RemoteException
/* 4869:     */   {
/* 4870:4677 */     for (int i = 1;; i++)
/* 4871:     */     {
/* 4872:4679 */       _Request local_Request = null;
/* 4873:4680 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4874:4681 */       boolean bool1 = false;
/* 4875:4682 */       LocalFrame localLocalFrame = null;
/* 4876:4683 */       boolean bool2 = false;
/* 4877:     */       try
/* 4878:     */       {
/* 4879:4686 */         local_Request = __request("addPayee");
/* 4880:4687 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4881:4688 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4882:4689 */         localLocalStack.setArgsOnLocal(bool1);
/* 4883:4691 */         if (bool1)
/* 4884:     */         {
/* 4885:4693 */           localLocalFrame = new LocalFrame(2);
/* 4886:4694 */           localLocalStack.push(localLocalFrame);
/* 4887:     */         }
/* 4888:4696 */         if (!bool1)
/* 4889:     */         {
/* 4890:4698 */           localObject4 = local_Request.getOutputStream();
/* 4891:4699 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 4892:4700 */           ((OutputStream)localObject4).write_long(paramInt);
/* 4893:     */         }
/* 4894:     */         else
/* 4895:     */         {
/* 4896:4704 */           localObject4 = local_Request.getOutputStream();
/* 4897:4705 */           localLocalFrame.add(paramPayeeInfo);
/* 4898:4706 */           localLocalFrame.add(paramInt);
/* 4899:     */         }
/* 4900:4708 */         local_Request.invoke();
/* 4901:     */         Object localObject1;
/* 4902:4709 */         if (bool1)
/* 4903:     */         {
/* 4904:4711 */           localObject4 = null;
/* 4905:4712 */           localObject4 = (String)localLocalFrame.getResult();
/* 4906:4713 */           return localObject4;
/* 4907:     */         }
/* 4908:4715 */         Object localObject4 = local_Request.getInputStream();
/* 4909:     */         
/* 4910:4717 */         String str = local_Request.read_string();
/* 4911:4718 */         return str;
/* 4912:     */       }
/* 4913:     */       catch (TRANSIENT localTRANSIENT)
/* 4914:     */       {
/* 4915:4722 */         if (i == 10) {
/* 4916:4724 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4917:     */         }
/* 4918:     */       }
/* 4919:     */       catch (UserException localUserException)
/* 4920:     */       {
/* 4921:4729 */         local_Request.isRMI();
/* 4922:     */         
/* 4923:     */ 
/* 4924:4732 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4925:     */       }
/* 4926:     */       catch (SystemException localSystemException)
/* 4927:     */       {
/* 4928:4736 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4929:     */       }
/* 4930:     */       finally
/* 4931:     */       {
/* 4932:4740 */         if (local_Request != null) {
/* 4933:4742 */           local_Request.close();
/* 4934:     */         }
/* 4935:4744 */         if (bool1) {
/* 4936:4745 */           localLocalStack.pop(localLocalFrame);
/* 4937:     */         }
/* 4938:4746 */         localLocalStack.setArgsOnLocal(bool2);
/* 4939:     */       }
/* 4940:     */     }
/* 4941:     */   }
/* 4942:     */   
/* 4943:     */   public int addConsumerCrossRef(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
/* 4944:     */     throws java.rmi.RemoteException
/* 4945:     */   {
/* 4946:4755 */     for (int i = 1;; i++)
/* 4947:     */     {
/* 4948:4757 */       _Request local_Request = null;
/* 4949:4758 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4950:4759 */       boolean bool1 = false;
/* 4951:4760 */       LocalFrame localLocalFrame = null;
/* 4952:4761 */       boolean bool2 = false;
/* 4953:     */       try
/* 4954:     */       {
/* 4955:4764 */         local_Request = __request("addConsumerCrossRef");
/* 4956:4765 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4957:4766 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4958:4767 */         localLocalStack.setArgsOnLocal(bool1);
/* 4959:4769 */         if (bool1)
/* 4960:     */         {
/* 4961:4771 */           localLocalFrame = new LocalFrame(1);
/* 4962:4772 */           localLocalStack.push(localLocalFrame);
/* 4963:     */         }
/* 4964:     */         OutputStream localOutputStream;
/* 4965:4774 */         if (!bool1)
/* 4966:     */         {
/* 4967:4776 */           localOutputStream = local_Request.getOutputStream();
/* 4968:4777 */           if (local_Request.isRMI()) {
/* 4969:4777 */             local_Request.write_value(paramArrayOfConsumerCrossRefInfo, new ConsumerCrossRefInfo[0].getClass());
/* 4970:     */           } else {
/* 4971:4777 */             ConsumerCrossRefInfoSeqHelper.write(localOutputStream, paramArrayOfConsumerCrossRefInfo);
/* 4972:     */           }
/* 4973:     */         }
/* 4974:     */         else
/* 4975:     */         {
/* 4976:4781 */           localOutputStream = local_Request.getOutputStream();
/* 4977:4782 */           localLocalFrame.add(paramArrayOfConsumerCrossRefInfo);
/* 4978:     */         }
/* 4979:4784 */         local_Request.invoke();
/* 4980:     */         int j;
/* 4981:4785 */         if (bool1)
/* 4982:     */         {
/* 4983:4788 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/* 4984:4789 */           return k;
/* 4985:     */         }
/* 4986:4791 */         InputStream localInputStream = local_Request.getInputStream();
/* 4987:     */         
/* 4988:4793 */         int m = localInputStream.read_long();
/* 4989:4794 */         return m;
/* 4990:     */       }
/* 4991:     */       catch (TRANSIENT localTRANSIENT)
/* 4992:     */       {
/* 4993:4798 */         if (i == 10) {
/* 4994:4800 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4995:     */         }
/* 4996:     */       }
/* 4997:     */       catch (UserException localUserException)
/* 4998:     */       {
/* 4999:4805 */         local_Request.isRMI();
/* 5000:     */         
/* 5001:     */ 
/* 5002:4808 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5003:     */       }
/* 5004:     */       catch (SystemException localSystemException)
/* 5005:     */       {
/* 5006:4812 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5007:     */       }
/* 5008:     */       finally
/* 5009:     */       {
/* 5010:4816 */         if (local_Request != null) {
/* 5011:4818 */           local_Request.close();
/* 5012:     */         }
/* 5013:4820 */         if (bool1) {
/* 5014:4821 */           localLocalStack.pop(localLocalFrame);
/* 5015:     */         }
/* 5016:4822 */         localLocalStack.setArgsOnLocal(bool2);
/* 5017:     */       }
/* 5018:     */     }
/* 5019:     */   }
/* 5020:     */   
/* 5021:     */   public int deleteConsumerCrossRef(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
/* 5022:     */     throws java.rmi.RemoteException
/* 5023:     */   {
/* 5024:4831 */     for (int i = 1;; i++)
/* 5025:     */     {
/* 5026:4833 */       _Request local_Request = null;
/* 5027:4834 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5028:4835 */       boolean bool1 = false;
/* 5029:4836 */       LocalFrame localLocalFrame = null;
/* 5030:4837 */       boolean bool2 = false;
/* 5031:     */       try
/* 5032:     */       {
/* 5033:4840 */         local_Request = __request("deleteConsumerCrossRef");
/* 5034:4841 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5035:4842 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5036:4843 */         localLocalStack.setArgsOnLocal(bool1);
/* 5037:4845 */         if (bool1)
/* 5038:     */         {
/* 5039:4847 */           localLocalFrame = new LocalFrame(1);
/* 5040:4848 */           localLocalStack.push(localLocalFrame);
/* 5041:     */         }
/* 5042:     */         OutputStream localOutputStream;
/* 5043:4850 */         if (!bool1)
/* 5044:     */         {
/* 5045:4852 */           localOutputStream = local_Request.getOutputStream();
/* 5046:4853 */           if (local_Request.isRMI()) {
/* 5047:4853 */             local_Request.write_value(paramArrayOfConsumerCrossRefInfo, new ConsumerCrossRefInfo[0].getClass());
/* 5048:     */           } else {
/* 5049:4853 */             ConsumerCrossRefInfoSeqHelper.write(localOutputStream, paramArrayOfConsumerCrossRefInfo);
/* 5050:     */           }
/* 5051:     */         }
/* 5052:     */         else
/* 5053:     */         {
/* 5054:4857 */           localOutputStream = local_Request.getOutputStream();
/* 5055:4858 */           localLocalFrame.add(paramArrayOfConsumerCrossRefInfo);
/* 5056:     */         }
/* 5057:4860 */         local_Request.invoke();
/* 5058:     */         int j;
/* 5059:4861 */         if (bool1)
/* 5060:     */         {
/* 5061:4864 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/* 5062:4865 */           return k;
/* 5063:     */         }
/* 5064:4867 */         InputStream localInputStream = local_Request.getInputStream();
/* 5065:     */         
/* 5066:4869 */         int m = localInputStream.read_long();
/* 5067:4870 */         return m;
/* 5068:     */       }
/* 5069:     */       catch (TRANSIENT localTRANSIENT)
/* 5070:     */       {
/* 5071:4874 */         if (i == 10) {
/* 5072:4876 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5073:     */         }
/* 5074:     */       }
/* 5075:     */       catch (UserException localUserException)
/* 5076:     */       {
/* 5077:4881 */         local_Request.isRMI();
/* 5078:     */         
/* 5079:     */ 
/* 5080:4884 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5081:     */       }
/* 5082:     */       catch (SystemException localSystemException)
/* 5083:     */       {
/* 5084:4888 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5085:     */       }
/* 5086:     */       finally
/* 5087:     */       {
/* 5088:4892 */         if (local_Request != null) {
/* 5089:4894 */           local_Request.close();
/* 5090:     */         }
/* 5091:4896 */         if (bool1) {
/* 5092:4897 */           localLocalStack.pop(localLocalFrame);
/* 5093:     */         }
/* 5094:4898 */         localLocalStack.setArgsOnLocal(bool2);
/* 5095:     */       }
/* 5096:     */     }
/* 5097:     */   }
/* 5098:     */   
/* 5099:     */   public ConsumerCrossRefInfo[] getConsumerCrossRef(String[] paramArrayOfString)
/* 5100:     */     throws java.rmi.RemoteException
/* 5101:     */   {
/* 5102:4907 */     for (int i = 1;; i++)
/* 5103:     */     {
/* 5104:4909 */       _Request local_Request = null;
/* 5105:4910 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5106:4911 */       boolean bool1 = false;
/* 5107:4912 */       LocalFrame localLocalFrame = null;
/* 5108:4913 */       boolean bool2 = false;
/* 5109:     */       try
/* 5110:     */       {
/* 5111:4916 */         local_Request = __request("getConsumerCrossRef");
/* 5112:4917 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5113:4918 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5114:4919 */         localLocalStack.setArgsOnLocal(bool1);
/* 5115:4921 */         if (bool1)
/* 5116:     */         {
/* 5117:4923 */           localLocalFrame = new LocalFrame(1);
/* 5118:4924 */           localLocalStack.push(localLocalFrame);
/* 5119:     */         }
/* 5120:4926 */         if (!bool1)
/* 5121:     */         {
/* 5122:4928 */           localObject4 = local_Request.getOutputStream();
/* 5123:4929 */           if (local_Request.isRMI()) {
/* 5124:4929 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 5125:     */           } else {
/* 5126:4929 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 5127:     */           }
/* 5128:     */         }
/* 5129:     */         else
/* 5130:     */         {
/* 5131:4933 */           localObject4 = local_Request.getOutputStream();
/* 5132:4934 */           localLocalFrame.add(paramArrayOfString);
/* 5133:     */         }
/* 5134:4936 */         local_Request.invoke();
/* 5135:     */         Object localObject5;
/* 5136:     */         Object localObject1;
/* 5137:4937 */         if (bool1)
/* 5138:     */         {
/* 5139:4939 */           localObject4 = null;
/* 5140:4940 */           localObject5 = localLocalFrame.getResult();
/* 5141:4941 */           if (localObject5 != null) {
/* 5142:4943 */             localObject4 = (ConsumerCrossRefInfo[])ObjectVal.clone(localObject5);
/* 5143:     */           }
/* 5144:4945 */           return localObject4;
/* 5145:     */         }
/* 5146:4947 */         Object localObject4 = local_Request.getInputStream();
/* 5147:4949 */         if (local_Request.isRMI()) {
/* 5148:4949 */           localObject5 = (ConsumerCrossRefInfo[])local_Request.read_value(new ConsumerCrossRefInfo[0].getClass());
/* 5149:     */         } else {
/* 5150:4949 */           localObject5 = ConsumerCrossRefInfoSeqHelper.read((InputStream)localObject4);
/* 5151:     */         }
/* 5152:4950 */         return localObject5;
/* 5153:     */       }
/* 5154:     */       catch (TRANSIENT localTRANSIENT)
/* 5155:     */       {
/* 5156:4954 */         if (i == 10) {
/* 5157:4956 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5158:     */         }
/* 5159:     */       }
/* 5160:     */       catch (UserException localUserException)
/* 5161:     */       {
/* 5162:4961 */         local_Request.isRMI();
/* 5163:     */         
/* 5164:     */ 
/* 5165:4964 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5166:     */       }
/* 5167:     */       catch (SystemException localSystemException)
/* 5168:     */       {
/* 5169:4968 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5170:     */       }
/* 5171:     */       finally
/* 5172:     */       {
/* 5173:4972 */         if (local_Request != null) {
/* 5174:4974 */           local_Request.close();
/* 5175:     */         }
/* 5176:4976 */         if (bool1) {
/* 5177:4977 */           localLocalStack.pop(localLocalFrame);
/* 5178:     */         }
/* 5179:4978 */         localLocalStack.setArgsOnLocal(bool2);
/* 5180:     */       }
/* 5181:     */     }
/* 5182:     */   }
/* 5183:     */   
/* 5184:     */   public int addCustomerBankInfo(CustomerBankInfo[] paramArrayOfCustomerBankInfo)
/* 5185:     */     throws java.rmi.RemoteException
/* 5186:     */   {
/* 5187:4987 */     for (int i = 1;; i++)
/* 5188:     */     {
/* 5189:4989 */       _Request local_Request = null;
/* 5190:4990 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5191:4991 */       boolean bool1 = false;
/* 5192:4992 */       LocalFrame localLocalFrame = null;
/* 5193:4993 */       boolean bool2 = false;
/* 5194:     */       try
/* 5195:     */       {
/* 5196:4996 */         local_Request = __request("addCustomerBankInfo");
/* 5197:4997 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5198:4998 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5199:4999 */         localLocalStack.setArgsOnLocal(bool1);
/* 5200:5001 */         if (bool1)
/* 5201:     */         {
/* 5202:5003 */           localLocalFrame = new LocalFrame(1);
/* 5203:5004 */           localLocalStack.push(localLocalFrame);
/* 5204:     */         }
/* 5205:     */         OutputStream localOutputStream;
/* 5206:5006 */         if (!bool1)
/* 5207:     */         {
/* 5208:5008 */           localOutputStream = local_Request.getOutputStream();
/* 5209:5009 */           if (local_Request.isRMI()) {
/* 5210:5009 */             local_Request.write_value(paramArrayOfCustomerBankInfo, new CustomerBankInfo[0].getClass());
/* 5211:     */           } else {
/* 5212:5009 */             CustomerBankInfoSeqHelper.write(localOutputStream, paramArrayOfCustomerBankInfo);
/* 5213:     */           }
/* 5214:     */         }
/* 5215:     */         else
/* 5216:     */         {
/* 5217:5013 */           localOutputStream = local_Request.getOutputStream();
/* 5218:5014 */           localLocalFrame.add(paramArrayOfCustomerBankInfo);
/* 5219:     */         }
/* 5220:5016 */         local_Request.invoke();
/* 5221:     */         int j;
/* 5222:5017 */         if (bool1)
/* 5223:     */         {
/* 5224:5020 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/* 5225:5021 */           return k;
/* 5226:     */         }
/* 5227:5023 */         InputStream localInputStream = local_Request.getInputStream();
/* 5228:     */         
/* 5229:5025 */         int m = localInputStream.read_long();
/* 5230:5026 */         return m;
/* 5231:     */       }
/* 5232:     */       catch (TRANSIENT localTRANSIENT)
/* 5233:     */       {
/* 5234:5030 */         if (i == 10) {
/* 5235:5032 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5236:     */         }
/* 5237:     */       }
/* 5238:     */       catch (UserException localUserException)
/* 5239:     */       {
/* 5240:5037 */         local_Request.isRMI();
/* 5241:     */         
/* 5242:     */ 
/* 5243:5040 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5244:     */       }
/* 5245:     */       catch (SystemException localSystemException)
/* 5246:     */       {
/* 5247:5044 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5248:     */       }
/* 5249:     */       finally
/* 5250:     */       {
/* 5251:5048 */         if (local_Request != null) {
/* 5252:5050 */           local_Request.close();
/* 5253:     */         }
/* 5254:5052 */         if (bool1) {
/* 5255:5053 */           localLocalStack.pop(localLocalFrame);
/* 5256:     */         }
/* 5257:5054 */         localLocalStack.setArgsOnLocal(bool2);
/* 5258:     */       }
/* 5259:     */     }
/* 5260:     */   }
/* 5261:     */   
/* 5262:     */   public int deleteCustomerBankInfo(CustomerBankInfo[] paramArrayOfCustomerBankInfo)
/* 5263:     */     throws java.rmi.RemoteException
/* 5264:     */   {
/* 5265:5063 */     for (int i = 1;; i++)
/* 5266:     */     {
/* 5267:5065 */       _Request local_Request = null;
/* 5268:5066 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5269:5067 */       boolean bool1 = false;
/* 5270:5068 */       LocalFrame localLocalFrame = null;
/* 5271:5069 */       boolean bool2 = false;
/* 5272:     */       try
/* 5273:     */       {
/* 5274:5072 */         local_Request = __request("deleteCustomerBankInfo");
/* 5275:5073 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5276:5074 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5277:5075 */         localLocalStack.setArgsOnLocal(bool1);
/* 5278:5077 */         if (bool1)
/* 5279:     */         {
/* 5280:5079 */           localLocalFrame = new LocalFrame(1);
/* 5281:5080 */           localLocalStack.push(localLocalFrame);
/* 5282:     */         }
/* 5283:     */         OutputStream localOutputStream;
/* 5284:5082 */         if (!bool1)
/* 5285:     */         {
/* 5286:5084 */           localOutputStream = local_Request.getOutputStream();
/* 5287:5085 */           if (local_Request.isRMI()) {
/* 5288:5085 */             local_Request.write_value(paramArrayOfCustomerBankInfo, new CustomerBankInfo[0].getClass());
/* 5289:     */           } else {
/* 5290:5085 */             CustomerBankInfoSeqHelper.write(localOutputStream, paramArrayOfCustomerBankInfo);
/* 5291:     */           }
/* 5292:     */         }
/* 5293:     */         else
/* 5294:     */         {
/* 5295:5089 */           localOutputStream = local_Request.getOutputStream();
/* 5296:5090 */           localLocalFrame.add(paramArrayOfCustomerBankInfo);
/* 5297:     */         }
/* 5298:5092 */         local_Request.invoke();
/* 5299:     */         int j;
/* 5300:5093 */         if (bool1)
/* 5301:     */         {
/* 5302:5096 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/* 5303:5097 */           return k;
/* 5304:     */         }
/* 5305:5099 */         InputStream localInputStream = local_Request.getInputStream();
/* 5306:     */         
/* 5307:5101 */         int m = localInputStream.read_long();
/* 5308:5102 */         return m;
/* 5309:     */       }
/* 5310:     */       catch (TRANSIENT localTRANSIENT)
/* 5311:     */       {
/* 5312:5106 */         if (i == 10) {
/* 5313:5108 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5314:     */         }
/* 5315:     */       }
/* 5316:     */       catch (UserException localUserException)
/* 5317:     */       {
/* 5318:5113 */         local_Request.isRMI();
/* 5319:     */         
/* 5320:     */ 
/* 5321:5116 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5322:     */       }
/* 5323:     */       catch (SystemException localSystemException)
/* 5324:     */       {
/* 5325:5120 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5326:     */       }
/* 5327:     */       finally
/* 5328:     */       {
/* 5329:5124 */         if (local_Request != null) {
/* 5330:5126 */           local_Request.close();
/* 5331:     */         }
/* 5332:5128 */         if (bool1) {
/* 5333:5129 */           localLocalStack.pop(localLocalFrame);
/* 5334:     */         }
/* 5335:5130 */         localLocalStack.setArgsOnLocal(bool2);
/* 5336:     */       }
/* 5337:     */     }
/* 5338:     */   }
/* 5339:     */   
/* 5340:     */   public CustomerBankInfo[] getCustomerBankInfo(String[] paramArrayOfString)
/* 5341:     */     throws java.rmi.RemoteException
/* 5342:     */   {
/* 5343:5139 */     for (int i = 1;; i++)
/* 5344:     */     {
/* 5345:5141 */       _Request local_Request = null;
/* 5346:5142 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5347:5143 */       boolean bool1 = false;
/* 5348:5144 */       LocalFrame localLocalFrame = null;
/* 5349:5145 */       boolean bool2 = false;
/* 5350:     */       try
/* 5351:     */       {
/* 5352:5148 */         local_Request = __request("getCustomerBankInfo");
/* 5353:5149 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5354:5150 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5355:5151 */         localLocalStack.setArgsOnLocal(bool1);
/* 5356:5153 */         if (bool1)
/* 5357:     */         {
/* 5358:5155 */           localLocalFrame = new LocalFrame(1);
/* 5359:5156 */           localLocalStack.push(localLocalFrame);
/* 5360:     */         }
/* 5361:5158 */         if (!bool1)
/* 5362:     */         {
/* 5363:5160 */           localObject4 = local_Request.getOutputStream();
/* 5364:5161 */           if (local_Request.isRMI()) {
/* 5365:5161 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 5366:     */           } else {
/* 5367:5161 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 5368:     */           }
/* 5369:     */         }
/* 5370:     */         else
/* 5371:     */         {
/* 5372:5165 */           localObject4 = local_Request.getOutputStream();
/* 5373:5166 */           localLocalFrame.add(paramArrayOfString);
/* 5374:     */         }
/* 5375:5168 */         local_Request.invoke();
/* 5376:     */         Object localObject5;
/* 5377:     */         Object localObject1;
/* 5378:5169 */         if (bool1)
/* 5379:     */         {
/* 5380:5171 */           localObject4 = null;
/* 5381:5172 */           localObject5 = localLocalFrame.getResult();
/* 5382:5173 */           if (localObject5 != null) {
/* 5383:5175 */             localObject4 = (CustomerBankInfo[])ObjectVal.clone(localObject5);
/* 5384:     */           }
/* 5385:5177 */           return localObject4;
/* 5386:     */         }
/* 5387:5179 */         Object localObject4 = local_Request.getInputStream();
/* 5388:5181 */         if (local_Request.isRMI()) {
/* 5389:5181 */           localObject5 = (CustomerBankInfo[])local_Request.read_value(new CustomerBankInfo[0].getClass());
/* 5390:     */         } else {
/* 5391:5181 */           localObject5 = CustomerBankInfoSeqHelper.read((InputStream)localObject4);
/* 5392:     */         }
/* 5393:5182 */         return localObject5;
/* 5394:     */       }
/* 5395:     */       catch (TRANSIENT localTRANSIENT)
/* 5396:     */       {
/* 5397:5186 */         if (i == 10) {
/* 5398:5188 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5399:     */         }
/* 5400:     */       }
/* 5401:     */       catch (UserException localUserException)
/* 5402:     */       {
/* 5403:5193 */         local_Request.isRMI();
/* 5404:     */         
/* 5405:     */ 
/* 5406:5196 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5407:     */       }
/* 5408:     */       catch (SystemException localSystemException)
/* 5409:     */       {
/* 5410:5200 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5411:     */       }
/* 5412:     */       finally
/* 5413:     */       {
/* 5414:5204 */         if (local_Request != null) {
/* 5415:5206 */           local_Request.close();
/* 5416:     */         }
/* 5417:5208 */         if (bool1) {
/* 5418:5209 */           localLocalStack.pop(localLocalFrame);
/* 5419:     */         }
/* 5420:5210 */         localLocalStack.setArgsOnLocal(bool2);
/* 5421:     */       }
/* 5422:     */     }
/* 5423:     */   }
/* 5424:     */   
/* 5425:     */   public int addCustomerProductAccessInfo(CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
/* 5426:     */     throws java.rmi.RemoteException
/* 5427:     */   {
/* 5428:5219 */     for (int i = 1;; i++)
/* 5429:     */     {
/* 5430:5221 */       _Request local_Request = null;
/* 5431:5222 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5432:5223 */       boolean bool1 = false;
/* 5433:5224 */       LocalFrame localLocalFrame = null;
/* 5434:5225 */       boolean bool2 = false;
/* 5435:     */       try
/* 5436:     */       {
/* 5437:5228 */         local_Request = __request("addCustomerProductAccessInfo");
/* 5438:5229 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5439:5230 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5440:5231 */         localLocalStack.setArgsOnLocal(bool1);
/* 5441:5233 */         if (bool1)
/* 5442:     */         {
/* 5443:5235 */           localLocalFrame = new LocalFrame(1);
/* 5444:5236 */           localLocalStack.push(localLocalFrame);
/* 5445:     */         }
/* 5446:     */         OutputStream localOutputStream;
/* 5447:5238 */         if (!bool1)
/* 5448:     */         {
/* 5449:5240 */           localOutputStream = local_Request.getOutputStream();
/* 5450:5241 */           if (local_Request.isRMI()) {
/* 5451:5241 */             local_Request.write_value(paramArrayOfCustomerProductAccessInfo, new CustomerProductAccessInfo[0].getClass());
/* 5452:     */           } else {
/* 5453:5241 */             CustomerProductAccessInfoSeqHelper.write(localOutputStream, paramArrayOfCustomerProductAccessInfo);
/* 5454:     */           }
/* 5455:     */         }
/* 5456:     */         else
/* 5457:     */         {
/* 5458:5245 */           localOutputStream = local_Request.getOutputStream();
/* 5459:5246 */           localLocalFrame.add(paramArrayOfCustomerProductAccessInfo);
/* 5460:     */         }
/* 5461:5248 */         local_Request.invoke();
/* 5462:     */         int j;
/* 5463:5249 */         if (bool1)
/* 5464:     */         {
/* 5465:5252 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/* 5466:5253 */           return k;
/* 5467:     */         }
/* 5468:5255 */         InputStream localInputStream = local_Request.getInputStream();
/* 5469:     */         
/* 5470:5257 */         int m = localInputStream.read_long();
/* 5471:5258 */         return m;
/* 5472:     */       }
/* 5473:     */       catch (TRANSIENT localTRANSIENT)
/* 5474:     */       {
/* 5475:5262 */         if (i == 10) {
/* 5476:5264 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5477:     */         }
/* 5478:     */       }
/* 5479:     */       catch (UserException localUserException)
/* 5480:     */       {
/* 5481:5269 */         local_Request.isRMI();
/* 5482:     */         
/* 5483:     */ 
/* 5484:5272 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5485:     */       }
/* 5486:     */       catch (SystemException localSystemException)
/* 5487:     */       {
/* 5488:5276 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5489:     */       }
/* 5490:     */       finally
/* 5491:     */       {
/* 5492:5280 */         if (local_Request != null) {
/* 5493:5282 */           local_Request.close();
/* 5494:     */         }
/* 5495:5284 */         if (bool1) {
/* 5496:5285 */           localLocalStack.pop(localLocalFrame);
/* 5497:     */         }
/* 5498:5286 */         localLocalStack.setArgsOnLocal(bool2);
/* 5499:     */       }
/* 5500:     */     }
/* 5501:     */   }
/* 5502:     */   
/* 5503:     */   public int deleteCustomerProductAccessInfo(CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
/* 5504:     */     throws java.rmi.RemoteException
/* 5505:     */   {
/* 5506:5295 */     for (int i = 1;; i++)
/* 5507:     */     {
/* 5508:5297 */       _Request local_Request = null;
/* 5509:5298 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5510:5299 */       boolean bool1 = false;
/* 5511:5300 */       LocalFrame localLocalFrame = null;
/* 5512:5301 */       boolean bool2 = false;
/* 5513:     */       try
/* 5514:     */       {
/* 5515:5304 */         local_Request = __request("deleteCustomerProductAccessInfo");
/* 5516:5305 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5517:5306 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5518:5307 */         localLocalStack.setArgsOnLocal(bool1);
/* 5519:5309 */         if (bool1)
/* 5520:     */         {
/* 5521:5311 */           localLocalFrame = new LocalFrame(1);
/* 5522:5312 */           localLocalStack.push(localLocalFrame);
/* 5523:     */         }
/* 5524:     */         OutputStream localOutputStream;
/* 5525:5314 */         if (!bool1)
/* 5526:     */         {
/* 5527:5316 */           localOutputStream = local_Request.getOutputStream();
/* 5528:5317 */           if (local_Request.isRMI()) {
/* 5529:5317 */             local_Request.write_value(paramArrayOfCustomerProductAccessInfo, new CustomerProductAccessInfo[0].getClass());
/* 5530:     */           } else {
/* 5531:5317 */             CustomerProductAccessInfoSeqHelper.write(localOutputStream, paramArrayOfCustomerProductAccessInfo);
/* 5532:     */           }
/* 5533:     */         }
/* 5534:     */         else
/* 5535:     */         {
/* 5536:5321 */           localOutputStream = local_Request.getOutputStream();
/* 5537:5322 */           localLocalFrame.add(paramArrayOfCustomerProductAccessInfo);
/* 5538:     */         }
/* 5539:5324 */         local_Request.invoke();
/* 5540:     */         int j;
/* 5541:5325 */         if (bool1)
/* 5542:     */         {
/* 5543:5328 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/* 5544:5329 */           return k;
/* 5545:     */         }
/* 5546:5331 */         InputStream localInputStream = local_Request.getInputStream();
/* 5547:     */         
/* 5548:5333 */         int m = localInputStream.read_long();
/* 5549:5334 */         return m;
/* 5550:     */       }
/* 5551:     */       catch (TRANSIENT localTRANSIENT)
/* 5552:     */       {
/* 5553:5338 */         if (i == 10) {
/* 5554:5340 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5555:     */         }
/* 5556:     */       }
/* 5557:     */       catch (UserException localUserException)
/* 5558:     */       {
/* 5559:5345 */         local_Request.isRMI();
/* 5560:     */         
/* 5561:     */ 
/* 5562:5348 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5563:     */       }
/* 5564:     */       catch (SystemException localSystemException)
/* 5565:     */       {
/* 5566:5352 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5567:     */       }
/* 5568:     */       finally
/* 5569:     */       {
/* 5570:5356 */         if (local_Request != null) {
/* 5571:5358 */           local_Request.close();
/* 5572:     */         }
/* 5573:5360 */         if (bool1) {
/* 5574:5361 */           localLocalStack.pop(localLocalFrame);
/* 5575:     */         }
/* 5576:5362 */         localLocalStack.setArgsOnLocal(bool2);
/* 5577:     */       }
/* 5578:     */     }
/* 5579:     */   }
/* 5580:     */   
/* 5581:     */   public CustomerProductAccessInfo[] getCustomerProductAccessInfo(String[] paramArrayOfString)
/* 5582:     */     throws java.rmi.RemoteException
/* 5583:     */   {
/* 5584:5371 */     for (int i = 1;; i++)
/* 5585:     */     {
/* 5586:5373 */       _Request local_Request = null;
/* 5587:5374 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5588:5375 */       boolean bool1 = false;
/* 5589:5376 */       LocalFrame localLocalFrame = null;
/* 5590:5377 */       boolean bool2 = false;
/* 5591:     */       try
/* 5592:     */       {
/* 5593:5380 */         local_Request = __request("getCustomerProductAccessInfo");
/* 5594:5381 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5595:5382 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5596:5383 */         localLocalStack.setArgsOnLocal(bool1);
/* 5597:5385 */         if (bool1)
/* 5598:     */         {
/* 5599:5387 */           localLocalFrame = new LocalFrame(1);
/* 5600:5388 */           localLocalStack.push(localLocalFrame);
/* 5601:     */         }
/* 5602:5390 */         if (!bool1)
/* 5603:     */         {
/* 5604:5392 */           localObject4 = local_Request.getOutputStream();
/* 5605:5393 */           if (local_Request.isRMI()) {
/* 5606:5393 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 5607:     */           } else {
/* 5608:5393 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 5609:     */           }
/* 5610:     */         }
/* 5611:     */         else
/* 5612:     */         {
/* 5613:5397 */           localObject4 = local_Request.getOutputStream();
/* 5614:5398 */           localLocalFrame.add(paramArrayOfString);
/* 5615:     */         }
/* 5616:5400 */         local_Request.invoke();
/* 5617:     */         Object localObject5;
/* 5618:     */         Object localObject1;
/* 5619:5401 */         if (bool1)
/* 5620:     */         {
/* 5621:5403 */           localObject4 = null;
/* 5622:5404 */           localObject5 = localLocalFrame.getResult();
/* 5623:5405 */           if (localObject5 != null) {
/* 5624:5407 */             localObject4 = (CustomerProductAccessInfo[])ObjectVal.clone(localObject5);
/* 5625:     */           }
/* 5626:5409 */           return localObject4;
/* 5627:     */         }
/* 5628:5411 */         Object localObject4 = local_Request.getInputStream();
/* 5629:5413 */         if (local_Request.isRMI()) {
/* 5630:5413 */           localObject5 = (CustomerProductAccessInfo[])local_Request.read_value(new CustomerProductAccessInfo[0].getClass());
/* 5631:     */         } else {
/* 5632:5413 */           localObject5 = CustomerProductAccessInfoSeqHelper.read((InputStream)localObject4);
/* 5633:     */         }
/* 5634:5414 */         return localObject5;
/* 5635:     */       }
/* 5636:     */       catch (TRANSIENT localTRANSIENT)
/* 5637:     */       {
/* 5638:5418 */         if (i == 10) {
/* 5639:5420 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5640:     */         }
/* 5641:     */       }
/* 5642:     */       catch (UserException localUserException)
/* 5643:     */       {
/* 5644:5425 */         local_Request.isRMI();
/* 5645:     */         
/* 5646:     */ 
/* 5647:5428 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5648:     */       }
/* 5649:     */       catch (SystemException localSystemException)
/* 5650:     */       {
/* 5651:5432 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5652:     */       }
/* 5653:     */       finally
/* 5654:     */       {
/* 5655:5436 */         if (local_Request != null) {
/* 5656:5438 */           local_Request.close();
/* 5657:     */         }
/* 5658:5440 */         if (bool1) {
/* 5659:5441 */           localLocalStack.pop(localLocalFrame);
/* 5660:     */         }
/* 5661:5442 */         localLocalStack.setArgsOnLocal(bool2);
/* 5662:     */       }
/* 5663:     */     }
/* 5664:     */   }
/* 5665:     */   
/* 5666:     */   public boolean validateMetavanteCustAcctByConsumerID(String paramString1, String paramString2)
/* 5667:     */     throws java.rmi.RemoteException
/* 5668:     */   {
/* 5669:5452 */     for (int i = 1;; i++)
/* 5670:     */     {
/* 5671:5454 */       _Request local_Request = null;
/* 5672:5455 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5673:5456 */       boolean bool1 = false;
/* 5674:5457 */       LocalFrame localLocalFrame = null;
/* 5675:5458 */       boolean bool2 = false;
/* 5676:     */       try
/* 5677:     */       {
/* 5678:5461 */         local_Request = __request("validateMetavanteCustAcctByConsumerID");
/* 5679:5462 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5680:5463 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5681:5464 */         localLocalStack.setArgsOnLocal(bool1);
/* 5682:5466 */         if (bool1)
/* 5683:     */         {
/* 5684:5468 */           localLocalFrame = new LocalFrame(2);
/* 5685:5469 */           localLocalStack.push(localLocalFrame);
/* 5686:     */         }
/* 5687:     */         OutputStream localOutputStream;
/* 5688:5471 */         if (!bool1)
/* 5689:     */         {
/* 5690:5473 */           localOutputStream = local_Request.getOutputStream();
/* 5691:5474 */           local_Request.write_string(paramString1);
/* 5692:5475 */           local_Request.write_string(paramString2);
/* 5693:     */         }
/* 5694:     */         else
/* 5695:     */         {
/* 5696:5479 */           localOutputStream = local_Request.getOutputStream();
/* 5697:5480 */           localLocalFrame.add(paramString1);
/* 5698:5481 */           localLocalFrame.add(paramString2);
/* 5699:     */         }
/* 5700:5483 */         local_Request.invoke();
/* 5701:     */         boolean bool3;
/* 5702:5484 */         if (bool1)
/* 5703:     */         {
/* 5704:5487 */           boolean bool4 = ((Boolean)localLocalFrame.getResult()).booleanValue();
/* 5705:5488 */           return bool4;
/* 5706:     */         }
/* 5707:5490 */         InputStream localInputStream = local_Request.getInputStream();
/* 5708:     */         
/* 5709:5492 */         boolean bool5 = localInputStream.read_boolean();
/* 5710:5493 */         return bool5;
/* 5711:     */       }
/* 5712:     */       catch (TRANSIENT localTRANSIENT)
/* 5713:     */       {
/* 5714:5497 */         if (i == 10) {
/* 5715:5499 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5716:     */         }
/* 5717:     */       }
/* 5718:     */       catch (UserException localUserException)
/* 5719:     */       {
/* 5720:5504 */         local_Request.isRMI();
/* 5721:     */         
/* 5722:     */ 
/* 5723:5507 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5724:     */       }
/* 5725:     */       catch (SystemException localSystemException)
/* 5726:     */       {
/* 5727:5511 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5728:     */       }
/* 5729:     */       finally
/* 5730:     */       {
/* 5731:5515 */         if (local_Request != null) {
/* 5732:5517 */           local_Request.close();
/* 5733:     */         }
/* 5734:5519 */         if (bool1) {
/* 5735:5520 */           localLocalStack.pop(localLocalFrame);
/* 5736:     */         }
/* 5737:5521 */         localLocalStack.setArgsOnLocal(bool2);
/* 5738:     */       }
/* 5739:     */     }
/* 5740:     */   }
/* 5741:     */   
/* 5742:     */   public boolean validateMetavanteCustAcctByCustomerID(String paramString1, String paramString2)
/* 5743:     */     throws java.rmi.RemoteException
/* 5744:     */   {
/* 5745:5531 */     for (int i = 1;; i++)
/* 5746:     */     {
/* 5747:5533 */       _Request local_Request = null;
/* 5748:5534 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5749:5535 */       boolean bool1 = false;
/* 5750:5536 */       LocalFrame localLocalFrame = null;
/* 5751:5537 */       boolean bool2 = false;
/* 5752:     */       try
/* 5753:     */       {
/* 5754:5540 */         local_Request = __request("validateMetavanteCustAcctByCustomerID");
/* 5755:5541 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5756:5542 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5757:5543 */         localLocalStack.setArgsOnLocal(bool1);
/* 5758:5545 */         if (bool1)
/* 5759:     */         {
/* 5760:5547 */           localLocalFrame = new LocalFrame(2);
/* 5761:5548 */           localLocalStack.push(localLocalFrame);
/* 5762:     */         }
/* 5763:     */         OutputStream localOutputStream;
/* 5764:5550 */         if (!bool1)
/* 5765:     */         {
/* 5766:5552 */           localOutputStream = local_Request.getOutputStream();
/* 5767:5553 */           local_Request.write_string(paramString1);
/* 5768:5554 */           local_Request.write_string(paramString2);
/* 5769:     */         }
/* 5770:     */         else
/* 5771:     */         {
/* 5772:5558 */           localOutputStream = local_Request.getOutputStream();
/* 5773:5559 */           localLocalFrame.add(paramString1);
/* 5774:5560 */           localLocalFrame.add(paramString2);
/* 5775:     */         }
/* 5776:5562 */         local_Request.invoke();
/* 5777:     */         boolean bool3;
/* 5778:5563 */         if (bool1)
/* 5779:     */         {
/* 5780:5566 */           boolean bool4 = ((Boolean)localLocalFrame.getResult()).booleanValue();
/* 5781:5567 */           return bool4;
/* 5782:     */         }
/* 5783:5569 */         InputStream localInputStream = local_Request.getInputStream();
/* 5784:     */         
/* 5785:5571 */         boolean bool5 = localInputStream.read_boolean();
/* 5786:5572 */         return bool5;
/* 5787:     */       }
/* 5788:     */       catch (TRANSIENT localTRANSIENT)
/* 5789:     */       {
/* 5790:5576 */         if (i == 10) {
/* 5791:5578 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5792:     */         }
/* 5793:     */       }
/* 5794:     */       catch (UserException localUserException)
/* 5795:     */       {
/* 5796:5583 */         local_Request.isRMI();
/* 5797:     */         
/* 5798:     */ 
/* 5799:5586 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5800:     */       }
/* 5801:     */       catch (SystemException localSystemException)
/* 5802:     */       {
/* 5803:5590 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5804:     */       }
/* 5805:     */       finally
/* 5806:     */       {
/* 5807:5594 */         if (local_Request != null) {
/* 5808:5596 */           local_Request.close();
/* 5809:     */         }
/* 5810:5598 */         if (bool1) {
/* 5811:5599 */           localLocalStack.pop(localLocalFrame);
/* 5812:     */         }
/* 5813:5600 */         localLocalStack.setArgsOnLocal(bool2);
/* 5814:     */       }
/* 5815:     */     }
/* 5816:     */   }
/* 5817:     */   
/* 5818:     */   public BPWHist getPmtHistory(BPWHist paramBPWHist)
/* 5819:     */     throws java.rmi.RemoteException
/* 5820:     */   {
/* 5821:5609 */     for (int i = 1;; i++)
/* 5822:     */     {
/* 5823:5611 */       _Request local_Request = null;
/* 5824:5612 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5825:5613 */       boolean bool1 = false;
/* 5826:5614 */       LocalFrame localLocalFrame = null;
/* 5827:5615 */       boolean bool2 = false;
/* 5828:     */       try
/* 5829:     */       {
/* 5830:5618 */         local_Request = __request("getPmtHistory");
/* 5831:5619 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5832:5620 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5833:5621 */         localLocalStack.setArgsOnLocal(bool1);
/* 5834:5623 */         if (bool1)
/* 5835:     */         {
/* 5836:5625 */           localLocalFrame = new LocalFrame(1);
/* 5837:5626 */           localLocalStack.push(localLocalFrame);
/* 5838:     */         }
/* 5839:5628 */         if (!bool1)
/* 5840:     */         {
/* 5841:5630 */           localObject4 = local_Request.getOutputStream();
/* 5842:5631 */           if (local_Request.isRMI()) {
/* 5843:5631 */             local_Request.write_value(paramBPWHist, BPWHist.class);
/* 5844:     */           } else {
/* 5845:5631 */             BPWHistHelper.write((OutputStream)localObject4, paramBPWHist);
/* 5846:     */           }
/* 5847:     */         }
/* 5848:     */         else
/* 5849:     */         {
/* 5850:5635 */           localObject4 = local_Request.getOutputStream();
/* 5851:5636 */           localLocalFrame.add(paramBPWHist);
/* 5852:     */         }
/* 5853:5638 */         local_Request.invoke();
/* 5854:     */         Object localObject5;
/* 5855:     */         Object localObject1;
/* 5856:5639 */         if (bool1)
/* 5857:     */         {
/* 5858:5641 */           localObject4 = null;
/* 5859:5642 */           localObject5 = localLocalFrame.getResult();
/* 5860:5643 */           if (localObject5 != null) {
/* 5861:5645 */             localObject4 = (BPWHist)ObjectVal.clone(localObject5);
/* 5862:     */           }
/* 5863:5647 */           return localObject4;
/* 5864:     */         }
/* 5865:5649 */         Object localObject4 = local_Request.getInputStream();
/* 5866:5651 */         if (local_Request.isRMI()) {
/* 5867:5651 */           localObject5 = (BPWHist)local_Request.read_value(BPWHist.class);
/* 5868:     */         } else {
/* 5869:5651 */           localObject5 = BPWHistHelper.read((InputStream)localObject4);
/* 5870:     */         }
/* 5871:5652 */         return localObject5;
/* 5872:     */       }
/* 5873:     */       catch (TRANSIENT localTRANSIENT)
/* 5874:     */       {
/* 5875:5656 */         if (i == 10) {
/* 5876:5658 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5877:     */         }
/* 5878:     */       }
/* 5879:     */       catch (UserException localUserException)
/* 5880:     */       {
/* 5881:5663 */         local_Request.isRMI();
/* 5882:     */         
/* 5883:     */ 
/* 5884:5666 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5885:     */       }
/* 5886:     */       catch (SystemException localSystemException)
/* 5887:     */       {
/* 5888:5670 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5889:     */       }
/* 5890:     */       finally
/* 5891:     */       {
/* 5892:5674 */         if (local_Request != null) {
/* 5893:5676 */           local_Request.close();
/* 5894:     */         }
/* 5895:5678 */         if (bool1) {
/* 5896:5679 */           localLocalStack.pop(localLocalFrame);
/* 5897:     */         }
/* 5898:5680 */         localLocalStack.setArgsOnLocal(bool2);
/* 5899:     */       }
/* 5900:     */     }
/* 5901:     */   }
/* 5902:     */   
/* 5903:     */   public BPWHist getIntraHistory(BPWHist paramBPWHist)
/* 5904:     */     throws java.rmi.RemoteException
/* 5905:     */   {
/* 5906:5689 */     for (int i = 1;; i++)
/* 5907:     */     {
/* 5908:5691 */       _Request local_Request = null;
/* 5909:5692 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5910:5693 */       boolean bool1 = false;
/* 5911:5694 */       LocalFrame localLocalFrame = null;
/* 5912:5695 */       boolean bool2 = false;
/* 5913:     */       try
/* 5914:     */       {
/* 5915:5698 */         local_Request = __request("getIntraHistory");
/* 5916:5699 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5917:5700 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5918:5701 */         localLocalStack.setArgsOnLocal(bool1);
/* 5919:5703 */         if (bool1)
/* 5920:     */         {
/* 5921:5705 */           localLocalFrame = new LocalFrame(1);
/* 5922:5706 */           localLocalStack.push(localLocalFrame);
/* 5923:     */         }
/* 5924:5708 */         if (!bool1)
/* 5925:     */         {
/* 5926:5710 */           localObject4 = local_Request.getOutputStream();
/* 5927:5711 */           if (local_Request.isRMI()) {
/* 5928:5711 */             local_Request.write_value(paramBPWHist, BPWHist.class);
/* 5929:     */           } else {
/* 5930:5711 */             BPWHistHelper.write((OutputStream)localObject4, paramBPWHist);
/* 5931:     */           }
/* 5932:     */         }
/* 5933:     */         else
/* 5934:     */         {
/* 5935:5715 */           localObject4 = local_Request.getOutputStream();
/* 5936:5716 */           localLocalFrame.add(paramBPWHist);
/* 5937:     */         }
/* 5938:5718 */         local_Request.invoke();
/* 5939:     */         Object localObject5;
/* 5940:     */         Object localObject1;
/* 5941:5719 */         if (bool1)
/* 5942:     */         {
/* 5943:5721 */           localObject4 = null;
/* 5944:5722 */           localObject5 = localLocalFrame.getResult();
/* 5945:5723 */           if (localObject5 != null) {
/* 5946:5725 */             localObject4 = (BPWHist)ObjectVal.clone(localObject5);
/* 5947:     */           }
/* 5948:5727 */           return localObject4;
/* 5949:     */         }
/* 5950:5729 */         Object localObject4 = local_Request.getInputStream();
/* 5951:5731 */         if (local_Request.isRMI()) {
/* 5952:5731 */           localObject5 = (BPWHist)local_Request.read_value(BPWHist.class);
/* 5953:     */         } else {
/* 5954:5731 */           localObject5 = BPWHistHelper.read((InputStream)localObject4);
/* 5955:     */         }
/* 5956:5732 */         return localObject5;
/* 5957:     */       }
/* 5958:     */       catch (TRANSIENT localTRANSIENT)
/* 5959:     */       {
/* 5960:5736 */         if (i == 10) {
/* 5961:5738 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5962:     */         }
/* 5963:     */       }
/* 5964:     */       catch (UserException localUserException)
/* 5965:     */       {
/* 5966:5743 */         local_Request.isRMI();
/* 5967:     */         
/* 5968:     */ 
/* 5969:5746 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5970:     */       }
/* 5971:     */       catch (SystemException localSystemException)
/* 5972:     */       {
/* 5973:5750 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5974:     */       }
/* 5975:     */       finally
/* 5976:     */       {
/* 5977:5754 */         if (local_Request != null) {
/* 5978:5756 */           local_Request.close();
/* 5979:     */         }
/* 5980:5758 */         if (bool1) {
/* 5981:5759 */           localLocalStack.pop(localLocalFrame);
/* 5982:     */         }
/* 5983:5760 */         localLocalStack.setArgsOnLocal(bool2);
/* 5984:     */       }
/* 5985:     */     }
/* 5986:     */   }
/* 5987:     */   
/* 5988:     */   public IntraTrnInfo[] getIntraById(String[] paramArrayOfString)
/* 5989:     */     throws java.rmi.RemoteException
/* 5990:     */   {
/* 5991:5769 */     for (int i = 1;; i++)
/* 5992:     */     {
/* 5993:5771 */       _Request local_Request = null;
/* 5994:5772 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5995:5773 */       boolean bool1 = false;
/* 5996:5774 */       LocalFrame localLocalFrame = null;
/* 5997:5775 */       boolean bool2 = false;
/* 5998:     */       try
/* 5999:     */       {
/* 6000:5778 */         local_Request = __request("getIntraById__StringSeq", "getIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue");
/* 6001:5779 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6002:5780 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6003:5781 */         localLocalStack.setArgsOnLocal(bool1);
/* 6004:5783 */         if (bool1)
/* 6005:     */         {
/* 6006:5785 */           localLocalFrame = new LocalFrame(1);
/* 6007:5786 */           localLocalStack.push(localLocalFrame);
/* 6008:     */         }
/* 6009:5788 */         if (!bool1)
/* 6010:     */         {
/* 6011:5790 */           localObject4 = local_Request.getOutputStream();
/* 6012:5791 */           if (local_Request.isRMI()) {
/* 6013:5791 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 6014:     */           } else {
/* 6015:5791 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 6016:     */           }
/* 6017:     */         }
/* 6018:     */         else
/* 6019:     */         {
/* 6020:5795 */           localObject4 = local_Request.getOutputStream();
/* 6021:5796 */           localLocalFrame.add(paramArrayOfString);
/* 6022:     */         }
/* 6023:5798 */         local_Request.invoke();
/* 6024:     */         Object localObject5;
/* 6025:     */         Object localObject1;
/* 6026:5799 */         if (bool1)
/* 6027:     */         {
/* 6028:5801 */           localObject4 = null;
/* 6029:5802 */           localObject5 = localLocalFrame.getResult();
/* 6030:5803 */           if (localObject5 != null) {
/* 6031:5805 */             localObject4 = (IntraTrnInfo[])ObjectVal.clone(localObject5);
/* 6032:     */           }
/* 6033:5807 */           return localObject4;
/* 6034:     */         }
/* 6035:5809 */         Object localObject4 = local_Request.getInputStream();
/* 6036:5811 */         if (local_Request.isRMI()) {
/* 6037:5811 */           localObject5 = (IntraTrnInfo[])local_Request.read_value(new IntraTrnInfo[0].getClass());
/* 6038:     */         } else {
/* 6039:5811 */           localObject5 = IntraTrnInfoSeqHelper.read((InputStream)localObject4);
/* 6040:     */         }
/* 6041:5812 */         return localObject5;
/* 6042:     */       }
/* 6043:     */       catch (TRANSIENT localTRANSIENT)
/* 6044:     */       {
/* 6045:5816 */         if (i == 10) {
/* 6046:5818 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6047:     */         }
/* 6048:     */       }
/* 6049:     */       catch (UserException localUserException)
/* 6050:     */       {
/* 6051:5823 */         local_Request.isRMI();
/* 6052:     */         
/* 6053:     */ 
/* 6054:5826 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6055:     */       }
/* 6056:     */       catch (SystemException localSystemException)
/* 6057:     */       {
/* 6058:5830 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6059:     */       }
/* 6060:     */       finally
/* 6061:     */       {
/* 6062:5834 */         if (local_Request != null) {
/* 6063:5836 */           local_Request.close();
/* 6064:     */         }
/* 6065:5838 */         if (bool1) {
/* 6066:5839 */           localLocalStack.pop(localLocalFrame);
/* 6067:     */         }
/* 6068:5840 */         localLocalStack.setArgsOnLocal(bool2);
/* 6069:     */       }
/* 6070:     */     }
/* 6071:     */   }
/* 6072:     */   
/* 6073:     */   public IntraTrnInfo getIntraById(String paramString)
/* 6074:     */     throws java.rmi.RemoteException
/* 6075:     */   {
/* 6076:5849 */     for (int i = 1;; i++)
/* 6077:     */     {
/* 6078:5851 */       _Request local_Request = null;
/* 6079:5852 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6080:5853 */       boolean bool1 = false;
/* 6081:5854 */       LocalFrame localLocalFrame = null;
/* 6082:5855 */       boolean bool2 = false;
/* 6083:     */       try
/* 6084:     */       {
/* 6085:5858 */         local_Request = __request("getIntraById__string", "getIntraById__CORBA_WStringValue");
/* 6086:5859 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6087:5860 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6088:5861 */         localLocalStack.setArgsOnLocal(bool1);
/* 6089:5863 */         if (bool1)
/* 6090:     */         {
/* 6091:5865 */           localLocalFrame = new LocalFrame(1);
/* 6092:5866 */           localLocalStack.push(localLocalFrame);
/* 6093:     */         }
/* 6094:5868 */         if (!bool1)
/* 6095:     */         {
/* 6096:5870 */           localObject4 = local_Request.getOutputStream();
/* 6097:5871 */           local_Request.write_string(paramString);
/* 6098:     */         }
/* 6099:     */         else
/* 6100:     */         {
/* 6101:5875 */           localObject4 = local_Request.getOutputStream();
/* 6102:5876 */           localLocalFrame.add(paramString);
/* 6103:     */         }
/* 6104:5878 */         local_Request.invoke();
/* 6105:     */         Object localObject1;
/* 6106:5879 */         if (bool1)
/* 6107:     */         {
/* 6108:5881 */           localObject4 = null;
/* 6109:5882 */           localObject5 = localLocalFrame.getResult();
/* 6110:5883 */           if (localObject5 != null) {
/* 6111:5885 */             localObject4 = (IntraTrnInfo)ObjectVal.clone(localObject5);
/* 6112:     */           }
/* 6113:5887 */           return localObject4;
/* 6114:     */         }
/* 6115:5889 */         Object localObject4 = local_Request.getInputStream();
/* 6116:     */         
/* 6117:5891 */         Object localObject5 = (IntraTrnInfo)local_Request.read_value(IntraTrnInfo.class);
/* 6118:5892 */         return localObject5;
/* 6119:     */       }
/* 6120:     */       catch (TRANSIENT localTRANSIENT)
/* 6121:     */       {
/* 6122:5896 */         if (i == 10) {
/* 6123:5898 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6124:     */         }
/* 6125:     */       }
/* 6126:     */       catch (UserException localUserException)
/* 6127:     */       {
/* 6128:5903 */         local_Request.isRMI();
/* 6129:     */         
/* 6130:     */ 
/* 6131:5906 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6132:     */       }
/* 6133:     */       catch (SystemException localSystemException)
/* 6134:     */       {
/* 6135:5910 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6136:     */       }
/* 6137:     */       finally
/* 6138:     */       {
/* 6139:5914 */         if (local_Request != null) {
/* 6140:5916 */           local_Request.close();
/* 6141:     */         }
/* 6142:5918 */         if (bool1) {
/* 6143:5919 */           localLocalStack.pop(localLocalFrame);
/* 6144:     */         }
/* 6145:5920 */         localLocalStack.setArgsOnLocal(bool2);
/* 6146:     */       }
/* 6147:     */     }
/* 6148:     */   }
/* 6149:     */   
/* 6150:     */   public PmtInfo getPmtById(String paramString)
/* 6151:     */     throws java.rmi.RemoteException
/* 6152:     */   {
/* 6153:5929 */     for (int i = 1;; i++)
/* 6154:     */     {
/* 6155:5931 */       _Request local_Request = null;
/* 6156:5932 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6157:5933 */       boolean bool1 = false;
/* 6158:5934 */       LocalFrame localLocalFrame = null;
/* 6159:5935 */       boolean bool2 = false;
/* 6160:     */       try
/* 6161:     */       {
/* 6162:5938 */         local_Request = __request("getPmtById__string", "getPmtById__CORBA_WStringValue");
/* 6163:5939 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6164:5940 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6165:5941 */         localLocalStack.setArgsOnLocal(bool1);
/* 6166:5943 */         if (bool1)
/* 6167:     */         {
/* 6168:5945 */           localLocalFrame = new LocalFrame(1);
/* 6169:5946 */           localLocalStack.push(localLocalFrame);
/* 6170:     */         }
/* 6171:5948 */         if (!bool1)
/* 6172:     */         {
/* 6173:5950 */           localObject4 = local_Request.getOutputStream();
/* 6174:5951 */           local_Request.write_string(paramString);
/* 6175:     */         }
/* 6176:     */         else
/* 6177:     */         {
/* 6178:5955 */           localObject4 = local_Request.getOutputStream();
/* 6179:5956 */           localLocalFrame.add(paramString);
/* 6180:     */         }
/* 6181:5958 */         local_Request.invoke();
/* 6182:     */         Object localObject1;
/* 6183:5959 */         if (bool1)
/* 6184:     */         {
/* 6185:5961 */           localObject4 = null;
/* 6186:5962 */           localObject5 = localLocalFrame.getResult();
/* 6187:5963 */           if (localObject5 != null) {
/* 6188:5965 */             localObject4 = (PmtInfo)ObjectVal.clone(localObject5);
/* 6189:     */           }
/* 6190:5967 */           return localObject4;
/* 6191:     */         }
/* 6192:5969 */         Object localObject4 = local_Request.getInputStream();
/* 6193:     */         
/* 6194:5971 */         Object localObject5 = (PmtInfo)local_Request.read_value(PmtInfo.class);
/* 6195:5972 */         return localObject5;
/* 6196:     */       }
/* 6197:     */       catch (TRANSIENT localTRANSIENT)
/* 6198:     */       {
/* 6199:5976 */         if (i == 10) {
/* 6200:5978 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6201:     */         }
/* 6202:     */       }
/* 6203:     */       catch (UserException localUserException)
/* 6204:     */       {
/* 6205:5983 */         local_Request.isRMI();
/* 6206:     */         
/* 6207:     */ 
/* 6208:5986 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6209:     */       }
/* 6210:     */       catch (SystemException localSystemException)
/* 6211:     */       {
/* 6212:5990 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6213:     */       }
/* 6214:     */       finally
/* 6215:     */       {
/* 6216:5994 */         if (local_Request != null) {
/* 6217:5996 */           local_Request.close();
/* 6218:     */         }
/* 6219:5998 */         if (bool1) {
/* 6220:5999 */           localLocalStack.pop(localLocalFrame);
/* 6221:     */         }
/* 6222:6000 */         localLocalStack.setArgsOnLocal(bool2);
/* 6223:     */       }
/* 6224:     */     }
/* 6225:     */   }
/* 6226:     */   
/* 6227:     */   public PmtInfo[] getPmtById(String[] paramArrayOfString)
/* 6228:     */     throws java.rmi.RemoteException
/* 6229:     */   {
/* 6230:6009 */     for (int i = 1;; i++)
/* 6231:     */     {
/* 6232:6011 */       _Request local_Request = null;
/* 6233:6012 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6234:6013 */       boolean bool1 = false;
/* 6235:6014 */       LocalFrame localLocalFrame = null;
/* 6236:6015 */       boolean bool2 = false;
/* 6237:     */       try
/* 6238:     */       {
/* 6239:6018 */         local_Request = __request("getPmtById__StringSeq", "getPmtById__org_omg_boxedRMI_CORBA_seq1_WStringValue");
/* 6240:6019 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6241:6020 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6242:6021 */         localLocalStack.setArgsOnLocal(bool1);
/* 6243:6023 */         if (bool1)
/* 6244:     */         {
/* 6245:6025 */           localLocalFrame = new LocalFrame(1);
/* 6246:6026 */           localLocalStack.push(localLocalFrame);
/* 6247:     */         }
/* 6248:6028 */         if (!bool1)
/* 6249:     */         {
/* 6250:6030 */           localObject4 = local_Request.getOutputStream();
/* 6251:6031 */           if (local_Request.isRMI()) {
/* 6252:6031 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 6253:     */           } else {
/* 6254:6031 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 6255:     */           }
/* 6256:     */         }
/* 6257:     */         else
/* 6258:     */         {
/* 6259:6035 */           localObject4 = local_Request.getOutputStream();
/* 6260:6036 */           localLocalFrame.add(paramArrayOfString);
/* 6261:     */         }
/* 6262:6038 */         local_Request.invoke();
/* 6263:     */         Object localObject5;
/* 6264:     */         Object localObject1;
/* 6265:6039 */         if (bool1)
/* 6266:     */         {
/* 6267:6041 */           localObject4 = null;
/* 6268:6042 */           localObject5 = localLocalFrame.getResult();
/* 6269:6043 */           if (localObject5 != null) {
/* 6270:6045 */             localObject4 = (PmtInfo[])ObjectVal.clone(localObject5);
/* 6271:     */           }
/* 6272:6047 */           return localObject4;
/* 6273:     */         }
/* 6274:6049 */         Object localObject4 = local_Request.getInputStream();
/* 6275:6051 */         if (local_Request.isRMI()) {
/* 6276:6051 */           localObject5 = (PmtInfo[])local_Request.read_value(new PmtInfo[0].getClass());
/* 6277:     */         } else {
/* 6278:6051 */           localObject5 = PmtInfoSeqHelper.read((InputStream)localObject4);
/* 6279:     */         }
/* 6280:6052 */         return localObject5;
/* 6281:     */       }
/* 6282:     */       catch (TRANSIENT localTRANSIENT)
/* 6283:     */       {
/* 6284:6056 */         if (i == 10) {
/* 6285:6058 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6286:     */         }
/* 6287:     */       }
/* 6288:     */       catch (UserException localUserException)
/* 6289:     */       {
/* 6290:6063 */         local_Request.isRMI();
/* 6291:     */         
/* 6292:     */ 
/* 6293:6066 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6294:     */       }
/* 6295:     */       catch (SystemException localSystemException)
/* 6296:     */       {
/* 6297:6070 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6298:     */       }
/* 6299:     */       finally
/* 6300:     */       {
/* 6301:6074 */         if (local_Request != null) {
/* 6302:6076 */           local_Request.close();
/* 6303:     */         }
/* 6304:6078 */         if (bool1) {
/* 6305:6079 */           localLocalStack.pop(localLocalFrame);
/* 6306:     */         }
/* 6307:6080 */         localLocalStack.setArgsOnLocal(bool2);
/* 6308:     */       }
/* 6309:     */     }
/* 6310:     */   }
/* 6311:     */   
/* 6312:     */   public RecPmtInfo[] getRecPmtById(String[] paramArrayOfString)
/* 6313:     */     throws java.rmi.RemoteException
/* 6314:     */   {
/* 6315:6089 */     for (int i = 1;; i++)
/* 6316:     */     {
/* 6317:6091 */       _Request local_Request = null;
/* 6318:6092 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6319:6093 */       boolean bool1 = false;
/* 6320:6094 */       LocalFrame localLocalFrame = null;
/* 6321:6095 */       boolean bool2 = false;
/* 6322:     */       try
/* 6323:     */       {
/* 6324:6098 */         local_Request = __request("getRecPmtById");
/* 6325:6099 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6326:6100 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6327:6101 */         localLocalStack.setArgsOnLocal(bool1);
/* 6328:6103 */         if (bool1)
/* 6329:     */         {
/* 6330:6105 */           localLocalFrame = new LocalFrame(1);
/* 6331:6106 */           localLocalStack.push(localLocalFrame);
/* 6332:     */         }
/* 6333:6108 */         if (!bool1)
/* 6334:     */         {
/* 6335:6110 */           localObject4 = local_Request.getOutputStream();
/* 6336:6111 */           if (local_Request.isRMI()) {
/* 6337:6111 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 6338:     */           } else {
/* 6339:6111 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 6340:     */           }
/* 6341:     */         }
/* 6342:     */         else
/* 6343:     */         {
/* 6344:6115 */           localObject4 = local_Request.getOutputStream();
/* 6345:6116 */           localLocalFrame.add(paramArrayOfString);
/* 6346:     */         }
/* 6347:6118 */         local_Request.invoke();
/* 6348:     */         Object localObject5;
/* 6349:     */         Object localObject1;
/* 6350:6119 */         if (bool1)
/* 6351:     */         {
/* 6352:6121 */           localObject4 = null;
/* 6353:6122 */           localObject5 = localLocalFrame.getResult();
/* 6354:6123 */           if (localObject5 != null) {
/* 6355:6125 */             localObject4 = (RecPmtInfo[])ObjectVal.clone(localObject5);
/* 6356:     */           }
/* 6357:6127 */           return localObject4;
/* 6358:     */         }
/* 6359:6129 */         Object localObject4 = local_Request.getInputStream();
/* 6360:6131 */         if (local_Request.isRMI()) {
/* 6361:6131 */           localObject5 = (RecPmtInfo[])local_Request.read_value(new RecPmtInfo[0].getClass());
/* 6362:     */         } else {
/* 6363:6131 */           localObject5 = RecPmtInfoSeqHelper.read((InputStream)localObject4);
/* 6364:     */         }
/* 6365:6132 */         return localObject5;
/* 6366:     */       }
/* 6367:     */       catch (TRANSIENT localTRANSIENT)
/* 6368:     */       {
/* 6369:6136 */         if (i == 10) {
/* 6370:6138 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6371:     */         }
/* 6372:     */       }
/* 6373:     */       catch (UserException localUserException)
/* 6374:     */       {
/* 6375:6143 */         local_Request.isRMI();
/* 6376:     */         
/* 6377:     */ 
/* 6378:6146 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6379:     */       }
/* 6380:     */       catch (SystemException localSystemException)
/* 6381:     */       {
/* 6382:6150 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6383:     */       }
/* 6384:     */       finally
/* 6385:     */       {
/* 6386:6154 */         if (local_Request != null) {
/* 6387:6156 */           local_Request.close();
/* 6388:     */         }
/* 6389:6158 */         if (bool1) {
/* 6390:6159 */           localLocalStack.pop(localLocalFrame);
/* 6391:     */         }
/* 6392:6160 */         localLocalStack.setArgsOnLocal(bool2);
/* 6393:     */       }
/* 6394:     */     }
/* 6395:     */   }
/* 6396:     */   
/* 6397:     */   public PayeeInfo getPayeeByListId(String paramString1, String paramString2)
/* 6398:     */     throws java.rmi.RemoteException, FFSException
/* 6399:     */   {
/* 6400:6170 */     for (int i = 1;; i++)
/* 6401:     */     {
/* 6402:6172 */       _Request local_Request = null;
/* 6403:6173 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6404:6174 */       boolean bool1 = false;
/* 6405:6175 */       LocalFrame localLocalFrame = null;
/* 6406:6176 */       boolean bool2 = false;
/* 6407:     */       try
/* 6408:     */       {
/* 6409:6179 */         local_Request = __request("getPayeeByListId");
/* 6410:6180 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6411:6181 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6412:6182 */         localLocalStack.setArgsOnLocal(bool1);
/* 6413:6184 */         if (bool1)
/* 6414:     */         {
/* 6415:6186 */           localLocalFrame = new LocalFrame(2);
/* 6416:6187 */           localLocalStack.push(localLocalFrame);
/* 6417:     */         }
/* 6418:6189 */         if (!bool1)
/* 6419:     */         {
/* 6420:6191 */           localObject4 = local_Request.getOutputStream();
/* 6421:6192 */           local_Request.write_string(paramString1);
/* 6422:6193 */           local_Request.write_string(paramString2);
/* 6423:     */         }
/* 6424:     */         else
/* 6425:     */         {
/* 6426:6197 */           localObject4 = local_Request.getOutputStream();
/* 6427:6198 */           localLocalFrame.add(paramString1);
/* 6428:6199 */           localLocalFrame.add(paramString2);
/* 6429:     */         }
/* 6430:6201 */         local_Request.invoke();
/* 6431:     */         Object localObject1;
/* 6432:6202 */         if (bool1)
/* 6433:     */         {
/* 6434:6204 */           localObject4 = null;
/* 6435:6205 */           localObject5 = localLocalFrame.getResult();
/* 6436:6206 */           if (localObject5 != null) {
/* 6437:6208 */             localObject4 = (PayeeInfo)ObjectVal.clone(localObject5);
/* 6438:     */           }
/* 6439:6210 */           return localObject4;
/* 6440:     */         }
/* 6441:6212 */         Object localObject4 = local_Request.getInputStream();
/* 6442:     */         
/* 6443:6214 */         localObject5 = (PayeeInfo)local_Request.read_value(PayeeInfo.class);
/* 6444:6215 */         return localObject5;
/* 6445:     */       }
/* 6446:     */       catch (TRANSIENT localTRANSIENT)
/* 6447:     */       {
/* 6448:6219 */         if (i == 10) {
/* 6449:6221 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6450:     */         }
/* 6451:     */       }
/* 6452:     */       catch (UserException localUserException)
/* 6453:     */       {
/* 6454:     */         Object localObject5;
/* 6455:6226 */         if (local_Request.isRMI())
/* 6456:     */         {
/* 6457:6228 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 6458:6230 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6459:     */           }
/* 6460:     */         }
/* 6461:     */         else
/* 6462:     */         {
/* 6463:6235 */           localObject5 = null;
/* 6464:6236 */           if (bool1)
/* 6465:     */           {
/* 6466:6238 */             localObject5 = localLocalFrame.getException();
/* 6467:6239 */             if (localObject5 != null) {
/* 6468:6241 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 6469:     */             }
/* 6470:     */           }
/* 6471:6244 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 6472:     */           {
/* 6473:6246 */             if (local_Request.isRMI()) {
/* 6474:6248 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6475:     */             }
/* 6476:6252 */             if (bool1)
/* 6477:     */             {
/* 6478:6254 */               if (localObject5 != null) {
/* 6479:6254 */                 throw ((FFSException)localObject5);
/* 6480:     */               }
/* 6481:     */             }
/* 6482:     */             else {
/* 6483:6258 */               throw FFSExceptionHelper.read(localUserException.input);
/* 6484:     */             }
/* 6485:     */           }
/* 6486:     */         }
/* 6487:6263 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6488:     */       }
/* 6489:     */       catch (SystemException localSystemException)
/* 6490:     */       {
/* 6491:6267 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6492:     */       }
/* 6493:     */       finally
/* 6494:     */       {
/* 6495:6271 */         if (local_Request != null) {
/* 6496:6273 */           local_Request.close();
/* 6497:     */         }
/* 6498:6275 */         if (bool1) {
/* 6499:6276 */           localLocalStack.pop(localLocalFrame);
/* 6500:     */         }
/* 6501:6277 */         localLocalStack.setArgsOnLocal(bool2);
/* 6502:     */       }
/* 6503:     */     }
/* 6504:     */   }
/* 6505:     */   
/* 6506:     */   public AccountTypesMap getAccountTypesMap()
/* 6507:     */     throws java.rmi.RemoteException, FFSException
/* 6508:     */   {
/* 6509:6285 */     for (int i = 1;; i++)
/* 6510:     */     {
/* 6511:6287 */       _Request local_Request = null;
/* 6512:6288 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6513:6289 */       boolean bool1 = false;
/* 6514:6290 */       LocalFrame localLocalFrame = null;
/* 6515:6291 */       boolean bool2 = false;
/* 6516:     */       try
/* 6517:     */       {
/* 6518:6294 */         local_Request = __request("getAccountTypesMap");
/* 6519:6295 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6520:6296 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6521:6297 */         localLocalStack.setArgsOnLocal(bool1);
/* 6522:6299 */         if (bool1)
/* 6523:     */         {
/* 6524:6301 */           localLocalFrame = new LocalFrame(0);
/* 6525:6302 */           localLocalStack.push(localLocalFrame);
/* 6526:     */         }
/* 6527:6310 */         local_Request.invoke();
/* 6528:     */         Object localObject1;
/* 6529:6311 */         if (bool1)
/* 6530:     */         {
/* 6531:6313 */           localObject4 = null;
/* 6532:6314 */           localObject5 = localLocalFrame.getResult();
/* 6533:6315 */           if (localObject5 != null) {
/* 6534:6317 */             localObject4 = (AccountTypesMap)ObjectVal.clone(localObject5);
/* 6535:     */           }
/* 6536:6319 */           return localObject4;
/* 6537:     */         }
/* 6538:6321 */         Object localObject4 = local_Request.getInputStream();
/* 6539:     */         
/* 6540:6323 */         localObject5 = (AccountTypesMap)local_Request.read_value(AccountTypesMap.class);
/* 6541:6324 */         return localObject5;
/* 6542:     */       }
/* 6543:     */       catch (TRANSIENT localTRANSIENT)
/* 6544:     */       {
/* 6545:6328 */         if (i == 10) {
/* 6546:6330 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6547:     */         }
/* 6548:     */       }
/* 6549:     */       catch (UserException localUserException)
/* 6550:     */       {
/* 6551:     */         Object localObject5;
/* 6552:6335 */         if (local_Request.isRMI())
/* 6553:     */         {
/* 6554:6337 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 6555:6339 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6556:     */           }
/* 6557:     */         }
/* 6558:     */         else
/* 6559:     */         {
/* 6560:6344 */           localObject5 = null;
/* 6561:6345 */           if (bool1)
/* 6562:     */           {
/* 6563:6347 */             localObject5 = localLocalFrame.getException();
/* 6564:6348 */             if (localObject5 != null) {
/* 6565:6350 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 6566:     */             }
/* 6567:     */           }
/* 6568:6353 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 6569:     */           {
/* 6570:6355 */             if (local_Request.isRMI()) {
/* 6571:6357 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6572:     */             }
/* 6573:6361 */             if (bool1)
/* 6574:     */             {
/* 6575:6363 */               if (localObject5 != null) {
/* 6576:6363 */                 throw ((FFSException)localObject5);
/* 6577:     */               }
/* 6578:     */             }
/* 6579:     */             else {
/* 6580:6367 */               throw FFSExceptionHelper.read(localUserException.input);
/* 6581:     */             }
/* 6582:     */           }
/* 6583:     */         }
/* 6584:6372 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6585:     */       }
/* 6586:     */       catch (SystemException localSystemException)
/* 6587:     */       {
/* 6588:6376 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6589:     */       }
/* 6590:     */       finally
/* 6591:     */       {
/* 6592:6380 */         if (local_Request != null) {
/* 6593:6382 */           local_Request.close();
/* 6594:     */         }
/* 6595:6384 */         if (bool1) {
/* 6596:6385 */           localLocalStack.pop(localLocalFrame);
/* 6597:     */         }
/* 6598:6386 */         localLocalStack.setArgsOnLocal(bool2);
/* 6599:     */       }
/* 6600:     */     }
/* 6601:     */   }
/* 6602:     */   
/* 6603:     */   public IOFX151BPWServices_Stub() {}
/* 6604:     */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.IOFX151BPWServices_Stub
 * JD-Core Version:    0.7.0.1
 */