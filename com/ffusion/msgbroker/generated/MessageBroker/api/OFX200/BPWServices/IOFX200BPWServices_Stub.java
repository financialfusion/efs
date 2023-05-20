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
/*   36:     */ import com.ffusion.ffs.interfaces.FFSExceptionHelper;
/*   37:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserData;
/*   38:     */ import com.ffusion.ffs.ofx.interfaces.TypeUserDataHelper;
/*   39:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1;
/*   40:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1Helper;
/*   41:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1;
/*   42:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1Helper;
/*   43:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1SeqHelper;
/*   44:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1;
/*   45:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1Helper;
/*   46:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1;
/*   47:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1Helper;
/*   48:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1;
/*   49:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1Helper;
/*   50:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1;
/*   51:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1Helper;
/*   52:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1;
/*   53:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1Helper;
/*   54:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1;
/*   55:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1Helper;
/*   56:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRqV1;
/*   57:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRqV1Helper;
/*   58:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRsV1;
/*   59:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInqTrnRsV1Helper;
/*   60:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1;
/*   61:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1Helper;
/*   62:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1;
/*   63:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1Helper;
/*   64:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1SeqHelper;
/*   65:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1;
/*   66:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1Helper;
/*   67:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
/*   68:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Helper;
/*   69:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1;
/*   70:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1Helper;
/*   71:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1;
/*   72:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1Helper;
/*   73:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1;
/*   74:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1Helper;
/*   75:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1;
/*   76:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1Helper;
/*   77:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1;
/*   78:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1Helper;
/*   79:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1;
/*   80:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1Helper;
/*   81:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1;
/*   82:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1Helper;
/*   83:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1;
/*   84:     */ import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1Helper;
/*   85:     */ import com.sybase.CORBA.LocalFrame;
/*   86:     */ import com.sybase.CORBA.LocalStack;
/*   87:     */ import com.sybase.CORBA.ObjectRef;
/*   88:     */ import com.sybase.CORBA.ObjectVal;
/*   89:     */ import com.sybase.CORBA.UserException;
/*   90:     */ import com.sybase.CORBA._Request;
/*   91:     */ import com.sybase.ejb.EJBObject;
/*   92:     */ import com.sybase.ejb.cts.StringSeqHelper;
/*   93:     */ import java.util.ArrayList;
/*   94:     */ import org.omg.CORBA.SystemException;
/*   95:     */ import org.omg.CORBA.TRANSIENT;
/*   96:     */ import org.omg.CORBA.portable.IDLEntity;
/*   97:     */ import org.omg.CORBA.portable.InputStream;
/*   98:     */ import org.omg.CORBA.portable.OutputStream;
/*   99:     */ 
/*  100:     */ public class IOFX200BPWServices_Stub
/*  101:     */   extends EJBObject
/*  102:     */   implements IOFX200BPWServices, IDLEntity
/*  103:     */ {
/*  104:     */   public IOFX200BPWServices_Stub(ObjectRef paramObjectRef)
/*  105:     */   {
/*  106:  21 */     super("RMI:com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.IOFX200BPWServices:0000000000000000", paramObjectRef);
/*  107:     */   }
/*  108:     */   
/*  109:     */   public int addCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
/*  110:     */     throws java.rmi.RemoteException
/*  111:     */   {
/*  112:  28 */     for (int i = 1;; i++)
/*  113:     */     {
/*  114:  30 */       _Request local_Request = null;
/*  115:  31 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  116:  32 */       boolean bool1 = false;
/*  117:  33 */       LocalFrame localLocalFrame = null;
/*  118:  34 */       boolean bool2 = false;
/*  119:     */       try
/*  120:     */       {
/*  121:  37 */         local_Request = __request("addCustomers");
/*  122:  38 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  123:  39 */         bool2 = localLocalStack.isArgsOnLocal();
/*  124:  40 */         localLocalStack.setArgsOnLocal(bool1);
/*  125:  42 */         if (bool1)
/*  126:     */         {
/*  127:  44 */           localLocalFrame = new LocalFrame(1);
/*  128:  45 */           localLocalStack.push(localLocalFrame);
/*  129:     */         }
/*  130:     */         OutputStream localOutputStream;
/*  131:  47 */         if (!bool1)
/*  132:     */         {
/*  133:  49 */           localOutputStream = local_Request.getOutputStream();
/*  134:  50 */           if (local_Request.isRMI()) {
/*  135:  50 */             local_Request.write_value(paramArrayOfCustomerInfo, new CustomerInfo[0].getClass());
/*  136:     */           } else {
/*  137:  50 */             CustomerInfoSeqHelper.write(localOutputStream, paramArrayOfCustomerInfo);
/*  138:     */           }
/*  139:     */         }
/*  140:     */         else
/*  141:     */         {
/*  142:  54 */           localOutputStream = local_Request.getOutputStream();
/*  143:  55 */           localLocalFrame.add(paramArrayOfCustomerInfo);
/*  144:     */         }
/*  145:  57 */         local_Request.invoke();
/*  146:     */         int j;
/*  147:  58 */         if (bool1)
/*  148:     */         {
/*  149:  61 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  150:  62 */           return k;
/*  151:     */         }
/*  152:  64 */         InputStream localInputStream = local_Request.getInputStream();
/*  153:     */         
/*  154:  66 */         int m = localInputStream.read_long();
/*  155:  67 */         return m;
/*  156:     */       }
/*  157:     */       catch (TRANSIENT localTRANSIENT)
/*  158:     */       {
/*  159:  71 */         if (i == 10) {
/*  160:  73 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  161:     */         }
/*  162:     */       }
/*  163:     */       catch (UserException localUserException)
/*  164:     */       {
/*  165:  78 */         local_Request.isRMI();
/*  166:     */         
/*  167:     */ 
/*  168:  81 */         throw new java.rmi.RemoteException(localUserException.type);
/*  169:     */       }
/*  170:     */       catch (SystemException localSystemException)
/*  171:     */       {
/*  172:  85 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  173:     */       }
/*  174:     */       finally
/*  175:     */       {
/*  176:  89 */         if (local_Request != null) {
/*  177:  91 */           local_Request.close();
/*  178:     */         }
/*  179:  93 */         if (bool1) {
/*  180:  94 */           localLocalStack.pop(localLocalFrame);
/*  181:     */         }
/*  182:  95 */         localLocalStack.setArgsOnLocal(bool2);
/*  183:     */       }
/*  184:     */     }
/*  185:     */   }
/*  186:     */   
/*  187:     */   public int modifyCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
/*  188:     */     throws java.rmi.RemoteException
/*  189:     */   {
/*  190: 104 */     for (int i = 1;; i++)
/*  191:     */     {
/*  192: 106 */       _Request local_Request = null;
/*  193: 107 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  194: 108 */       boolean bool1 = false;
/*  195: 109 */       LocalFrame localLocalFrame = null;
/*  196: 110 */       boolean bool2 = false;
/*  197:     */       try
/*  198:     */       {
/*  199: 113 */         local_Request = __request("modifyCustomers");
/*  200: 114 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  201: 115 */         bool2 = localLocalStack.isArgsOnLocal();
/*  202: 116 */         localLocalStack.setArgsOnLocal(bool1);
/*  203: 118 */         if (bool1)
/*  204:     */         {
/*  205: 120 */           localLocalFrame = new LocalFrame(1);
/*  206: 121 */           localLocalStack.push(localLocalFrame);
/*  207:     */         }
/*  208:     */         OutputStream localOutputStream;
/*  209: 123 */         if (!bool1)
/*  210:     */         {
/*  211: 125 */           localOutputStream = local_Request.getOutputStream();
/*  212: 126 */           if (local_Request.isRMI()) {
/*  213: 126 */             local_Request.write_value(paramArrayOfCustomerInfo, new CustomerInfo[0].getClass());
/*  214:     */           } else {
/*  215: 126 */             CustomerInfoSeqHelper.write(localOutputStream, paramArrayOfCustomerInfo);
/*  216:     */           }
/*  217:     */         }
/*  218:     */         else
/*  219:     */         {
/*  220: 130 */           localOutputStream = local_Request.getOutputStream();
/*  221: 131 */           localLocalFrame.add(paramArrayOfCustomerInfo);
/*  222:     */         }
/*  223: 133 */         local_Request.invoke();
/*  224:     */         int j;
/*  225: 134 */         if (bool1)
/*  226:     */         {
/*  227: 137 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  228: 138 */           return k;
/*  229:     */         }
/*  230: 140 */         InputStream localInputStream = local_Request.getInputStream();
/*  231:     */         
/*  232: 142 */         int m = localInputStream.read_long();
/*  233: 143 */         return m;
/*  234:     */       }
/*  235:     */       catch (TRANSIENT localTRANSIENT)
/*  236:     */       {
/*  237: 147 */         if (i == 10) {
/*  238: 149 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  239:     */         }
/*  240:     */       }
/*  241:     */       catch (UserException localUserException)
/*  242:     */       {
/*  243: 154 */         local_Request.isRMI();
/*  244:     */         
/*  245:     */ 
/*  246: 157 */         throw new java.rmi.RemoteException(localUserException.type);
/*  247:     */       }
/*  248:     */       catch (SystemException localSystemException)
/*  249:     */       {
/*  250: 161 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  251:     */       }
/*  252:     */       finally
/*  253:     */       {
/*  254: 165 */         if (local_Request != null) {
/*  255: 167 */           local_Request.close();
/*  256:     */         }
/*  257: 169 */         if (bool1) {
/*  258: 170 */           localLocalStack.pop(localLocalFrame);
/*  259:     */         }
/*  260: 171 */         localLocalStack.setArgsOnLocal(bool2);
/*  261:     */       }
/*  262:     */     }
/*  263:     */   }
/*  264:     */   
/*  265:     */   public int deleteCustomers(String[] paramArrayOfString)
/*  266:     */     throws java.rmi.RemoteException
/*  267:     */   {
/*  268: 180 */     for (int i = 1;; i++)
/*  269:     */     {
/*  270: 182 */       _Request local_Request = null;
/*  271: 183 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  272: 184 */       boolean bool1 = false;
/*  273: 185 */       LocalFrame localLocalFrame = null;
/*  274: 186 */       boolean bool2 = false;
/*  275:     */       try
/*  276:     */       {
/*  277: 189 */         local_Request = __request("deleteCustomers__StringSeq", "deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue");
/*  278: 190 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  279: 191 */         bool2 = localLocalStack.isArgsOnLocal();
/*  280: 192 */         localLocalStack.setArgsOnLocal(bool1);
/*  281: 194 */         if (bool1)
/*  282:     */         {
/*  283: 196 */           localLocalFrame = new LocalFrame(1);
/*  284: 197 */           localLocalStack.push(localLocalFrame);
/*  285:     */         }
/*  286:     */         OutputStream localOutputStream;
/*  287: 199 */         if (!bool1)
/*  288:     */         {
/*  289: 201 */           localOutputStream = local_Request.getOutputStream();
/*  290: 202 */           if (local_Request.isRMI()) {
/*  291: 202 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*  292:     */           } else {
/*  293: 202 */             StringSeqHelper.write(localOutputStream, paramArrayOfString);
/*  294:     */           }
/*  295:     */         }
/*  296:     */         else
/*  297:     */         {
/*  298: 206 */           localOutputStream = local_Request.getOutputStream();
/*  299: 207 */           localLocalFrame.add(paramArrayOfString);
/*  300:     */         }
/*  301: 209 */         local_Request.invoke();
/*  302:     */         int j;
/*  303: 210 */         if (bool1)
/*  304:     */         {
/*  305: 213 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  306: 214 */           return k;
/*  307:     */         }
/*  308: 216 */         InputStream localInputStream = local_Request.getInputStream();
/*  309:     */         
/*  310: 218 */         int m = localInputStream.read_long();
/*  311: 219 */         return m;
/*  312:     */       }
/*  313:     */       catch (TRANSIENT localTRANSIENT)
/*  314:     */       {
/*  315: 223 */         if (i == 10) {
/*  316: 225 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  317:     */         }
/*  318:     */       }
/*  319:     */       catch (UserException localUserException)
/*  320:     */       {
/*  321: 230 */         local_Request.isRMI();
/*  322:     */         
/*  323:     */ 
/*  324: 233 */         throw new java.rmi.RemoteException(localUserException.type);
/*  325:     */       }
/*  326:     */       catch (SystemException localSystemException)
/*  327:     */       {
/*  328: 237 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  329:     */       }
/*  330:     */       finally
/*  331:     */       {
/*  332: 241 */         if (local_Request != null) {
/*  333: 243 */           local_Request.close();
/*  334:     */         }
/*  335: 245 */         if (bool1) {
/*  336: 246 */           localLocalStack.pop(localLocalFrame);
/*  337:     */         }
/*  338: 247 */         localLocalStack.setArgsOnLocal(bool2);
/*  339:     */       }
/*  340:     */     }
/*  341:     */   }
/*  342:     */   
/*  343:     */   public int deleteCustomers(String[] paramArrayOfString, int paramInt)
/*  344:     */     throws java.rmi.RemoteException
/*  345:     */   {
/*  346: 257 */     for (int i = 1;; i++)
/*  347:     */     {
/*  348: 259 */       _Request local_Request = null;
/*  349: 260 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  350: 261 */       boolean bool1 = false;
/*  351: 262 */       LocalFrame localLocalFrame = null;
/*  352: 263 */       boolean bool2 = false;
/*  353:     */       try
/*  354:     */       {
/*  355: 266 */         local_Request = __request("deleteCustomers__StringSeq__long", "deleteCustomers__org_omg_boxedRMI_CORBA_seq1_WStringValue__long");
/*  356: 267 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  357: 268 */         bool2 = localLocalStack.isArgsOnLocal();
/*  358: 269 */         localLocalStack.setArgsOnLocal(bool1);
/*  359: 271 */         if (bool1)
/*  360:     */         {
/*  361: 273 */           localLocalFrame = new LocalFrame(2);
/*  362: 274 */           localLocalStack.push(localLocalFrame);
/*  363:     */         }
/*  364:     */         OutputStream localOutputStream;
/*  365: 276 */         if (!bool1)
/*  366:     */         {
/*  367: 278 */           localOutputStream = local_Request.getOutputStream();
/*  368: 279 */           if (local_Request.isRMI()) {
/*  369: 279 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*  370:     */           } else {
/*  371: 279 */             StringSeqHelper.write(localOutputStream, paramArrayOfString);
/*  372:     */           }
/*  373: 280 */           localOutputStream.write_long(paramInt);
/*  374:     */         }
/*  375:     */         else
/*  376:     */         {
/*  377: 284 */           localOutputStream = local_Request.getOutputStream();
/*  378: 285 */           localLocalFrame.add(paramArrayOfString);
/*  379: 286 */           localLocalFrame.add(paramInt);
/*  380:     */         }
/*  381: 288 */         local_Request.invoke();
/*  382:     */         int j;
/*  383: 289 */         if (bool1)
/*  384:     */         {
/*  385: 292 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  386: 293 */           return k;
/*  387:     */         }
/*  388: 295 */         InputStream localInputStream = local_Request.getInputStream();
/*  389:     */         
/*  390: 297 */         int m = localInputStream.read_long();
/*  391: 298 */         return m;
/*  392:     */       }
/*  393:     */       catch (TRANSIENT localTRANSIENT)
/*  394:     */       {
/*  395: 302 */         if (i == 10) {
/*  396: 304 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  397:     */         }
/*  398:     */       }
/*  399:     */       catch (UserException localUserException)
/*  400:     */       {
/*  401: 309 */         local_Request.isRMI();
/*  402:     */         
/*  403:     */ 
/*  404: 312 */         throw new java.rmi.RemoteException(localUserException.type);
/*  405:     */       }
/*  406:     */       catch (SystemException localSystemException)
/*  407:     */       {
/*  408: 316 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  409:     */       }
/*  410:     */       finally
/*  411:     */       {
/*  412: 320 */         if (local_Request != null) {
/*  413: 322 */           local_Request.close();
/*  414:     */         }
/*  415: 324 */         if (bool1) {
/*  416: 325 */           localLocalStack.pop(localLocalFrame);
/*  417:     */         }
/*  418: 326 */         localLocalStack.setArgsOnLocal(bool2);
/*  419:     */       }
/*  420:     */     }
/*  421:     */   }
/*  422:     */   
/*  423:     */   public int deactivateCustomers(String[] paramArrayOfString)
/*  424:     */     throws java.rmi.RemoteException
/*  425:     */   {
/*  426: 335 */     for (int i = 1;; i++)
/*  427:     */     {
/*  428: 337 */       _Request local_Request = null;
/*  429: 338 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  430: 339 */       boolean bool1 = false;
/*  431: 340 */       LocalFrame localLocalFrame = null;
/*  432: 341 */       boolean bool2 = false;
/*  433:     */       try
/*  434:     */       {
/*  435: 344 */         local_Request = __request("deactivateCustomers");
/*  436: 345 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  437: 346 */         bool2 = localLocalStack.isArgsOnLocal();
/*  438: 347 */         localLocalStack.setArgsOnLocal(bool1);
/*  439: 349 */         if (bool1)
/*  440:     */         {
/*  441: 351 */           localLocalFrame = new LocalFrame(1);
/*  442: 352 */           localLocalStack.push(localLocalFrame);
/*  443:     */         }
/*  444:     */         OutputStream localOutputStream;
/*  445: 354 */         if (!bool1)
/*  446:     */         {
/*  447: 356 */           localOutputStream = local_Request.getOutputStream();
/*  448: 357 */           if (local_Request.isRMI()) {
/*  449: 357 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*  450:     */           } else {
/*  451: 357 */             StringSeqHelper.write(localOutputStream, paramArrayOfString);
/*  452:     */           }
/*  453:     */         }
/*  454:     */         else
/*  455:     */         {
/*  456: 361 */           localOutputStream = local_Request.getOutputStream();
/*  457: 362 */           localLocalFrame.add(paramArrayOfString);
/*  458:     */         }
/*  459: 364 */         local_Request.invoke();
/*  460:     */         int j;
/*  461: 365 */         if (bool1)
/*  462:     */         {
/*  463: 368 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  464: 369 */           return k;
/*  465:     */         }
/*  466: 371 */         InputStream localInputStream = local_Request.getInputStream();
/*  467:     */         
/*  468: 373 */         int m = localInputStream.read_long();
/*  469: 374 */         return m;
/*  470:     */       }
/*  471:     */       catch (TRANSIENT localTRANSIENT)
/*  472:     */       {
/*  473: 378 */         if (i == 10) {
/*  474: 380 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  475:     */         }
/*  476:     */       }
/*  477:     */       catch (UserException localUserException)
/*  478:     */       {
/*  479: 385 */         local_Request.isRMI();
/*  480:     */         
/*  481:     */ 
/*  482: 388 */         throw new java.rmi.RemoteException(localUserException.type);
/*  483:     */       }
/*  484:     */       catch (SystemException localSystemException)
/*  485:     */       {
/*  486: 392 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  487:     */       }
/*  488:     */       finally
/*  489:     */       {
/*  490: 396 */         if (local_Request != null) {
/*  491: 398 */           local_Request.close();
/*  492:     */         }
/*  493: 400 */         if (bool1) {
/*  494: 401 */           localLocalStack.pop(localLocalFrame);
/*  495:     */         }
/*  496: 402 */         localLocalStack.setArgsOnLocal(bool2);
/*  497:     */       }
/*  498:     */     }
/*  499:     */   }
/*  500:     */   
/*  501:     */   public int activateCustomers(String[] paramArrayOfString)
/*  502:     */     throws java.rmi.RemoteException
/*  503:     */   {
/*  504: 411 */     for (int i = 1;; i++)
/*  505:     */     {
/*  506: 413 */       _Request local_Request = null;
/*  507: 414 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  508: 415 */       boolean bool1 = false;
/*  509: 416 */       LocalFrame localLocalFrame = null;
/*  510: 417 */       boolean bool2 = false;
/*  511:     */       try
/*  512:     */       {
/*  513: 420 */         local_Request = __request("activateCustomers");
/*  514: 421 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  515: 422 */         bool2 = localLocalStack.isArgsOnLocal();
/*  516: 423 */         localLocalStack.setArgsOnLocal(bool1);
/*  517: 425 */         if (bool1)
/*  518:     */         {
/*  519: 427 */           localLocalFrame = new LocalFrame(1);
/*  520: 428 */           localLocalStack.push(localLocalFrame);
/*  521:     */         }
/*  522:     */         OutputStream localOutputStream;
/*  523: 430 */         if (!bool1)
/*  524:     */         {
/*  525: 432 */           localOutputStream = local_Request.getOutputStream();
/*  526: 433 */           if (local_Request.isRMI()) {
/*  527: 433 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*  528:     */           } else {
/*  529: 433 */             StringSeqHelper.write(localOutputStream, paramArrayOfString);
/*  530:     */           }
/*  531:     */         }
/*  532:     */         else
/*  533:     */         {
/*  534: 437 */           localOutputStream = local_Request.getOutputStream();
/*  535: 438 */           localLocalFrame.add(paramArrayOfString);
/*  536:     */         }
/*  537: 440 */         local_Request.invoke();
/*  538:     */         int j;
/*  539: 441 */         if (bool1)
/*  540:     */         {
/*  541: 444 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/*  542: 445 */           return k;
/*  543:     */         }
/*  544: 447 */         InputStream localInputStream = local_Request.getInputStream();
/*  545:     */         
/*  546: 449 */         int m = localInputStream.read_long();
/*  547: 450 */         return m;
/*  548:     */       }
/*  549:     */       catch (TRANSIENT localTRANSIENT)
/*  550:     */       {
/*  551: 454 */         if (i == 10) {
/*  552: 456 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  553:     */         }
/*  554:     */       }
/*  555:     */       catch (UserException localUserException)
/*  556:     */       {
/*  557: 461 */         local_Request.isRMI();
/*  558:     */         
/*  559:     */ 
/*  560: 464 */         throw new java.rmi.RemoteException(localUserException.type);
/*  561:     */       }
/*  562:     */       catch (SystemException localSystemException)
/*  563:     */       {
/*  564: 468 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  565:     */       }
/*  566:     */       finally
/*  567:     */       {
/*  568: 472 */         if (local_Request != null) {
/*  569: 474 */           local_Request.close();
/*  570:     */         }
/*  571: 476 */         if (bool1) {
/*  572: 477 */           localLocalStack.pop(localLocalFrame);
/*  573:     */         }
/*  574: 478 */         localLocalStack.setArgsOnLocal(bool2);
/*  575:     */       }
/*  576:     */     }
/*  577:     */   }
/*  578:     */   
/*  579:     */   public CustomerInfo[] getCustomersInfo(String[] paramArrayOfString)
/*  580:     */     throws java.rmi.RemoteException
/*  581:     */   {
/*  582: 487 */     for (int i = 1;; i++)
/*  583:     */     {
/*  584: 489 */       _Request local_Request = null;
/*  585: 490 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  586: 491 */       boolean bool1 = false;
/*  587: 492 */       LocalFrame localLocalFrame = null;
/*  588: 493 */       boolean bool2 = false;
/*  589:     */       try
/*  590:     */       {
/*  591: 496 */         local_Request = __request("getCustomersInfo");
/*  592: 497 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  593: 498 */         bool2 = localLocalStack.isArgsOnLocal();
/*  594: 499 */         localLocalStack.setArgsOnLocal(bool1);
/*  595: 501 */         if (bool1)
/*  596:     */         {
/*  597: 503 */           localLocalFrame = new LocalFrame(1);
/*  598: 504 */           localLocalStack.push(localLocalFrame);
/*  599:     */         }
/*  600: 506 */         if (!bool1)
/*  601:     */         {
/*  602: 508 */           localObject4 = local_Request.getOutputStream();
/*  603: 509 */           if (local_Request.isRMI()) {
/*  604: 509 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/*  605:     */           } else {
/*  606: 509 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/*  607:     */           }
/*  608:     */         }
/*  609:     */         else
/*  610:     */         {
/*  611: 513 */           localObject4 = local_Request.getOutputStream();
/*  612: 514 */           localLocalFrame.add(paramArrayOfString);
/*  613:     */         }
/*  614: 516 */         local_Request.invoke();
/*  615:     */         Object localObject5;
/*  616:     */         Object localObject1;
/*  617: 517 */         if (bool1)
/*  618:     */         {
/*  619: 519 */           localObject4 = null;
/*  620: 520 */           localObject5 = localLocalFrame.getResult();
/*  621: 521 */           if (localObject5 != null) {
/*  622: 523 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*  623:     */           }
/*  624: 525 */           return localObject4;
/*  625:     */         }
/*  626: 527 */         Object localObject4 = local_Request.getInputStream();
/*  627: 529 */         if (local_Request.isRMI()) {
/*  628: 529 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*  629:     */         } else {
/*  630: 529 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*  631:     */         }
/*  632: 530 */         return localObject5;
/*  633:     */       }
/*  634:     */       catch (TRANSIENT localTRANSIENT)
/*  635:     */       {
/*  636: 534 */         if (i == 10) {
/*  637: 536 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  638:     */         }
/*  639:     */       }
/*  640:     */       catch (UserException localUserException)
/*  641:     */       {
/*  642: 541 */         local_Request.isRMI();
/*  643:     */         
/*  644:     */ 
/*  645: 544 */         throw new java.rmi.RemoteException(localUserException.type);
/*  646:     */       }
/*  647:     */       catch (SystemException localSystemException)
/*  648:     */       {
/*  649: 548 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  650:     */       }
/*  651:     */       finally
/*  652:     */       {
/*  653: 552 */         if (local_Request != null) {
/*  654: 554 */           local_Request.close();
/*  655:     */         }
/*  656: 556 */         if (bool1) {
/*  657: 557 */           localLocalStack.pop(localLocalFrame);
/*  658:     */         }
/*  659: 558 */         localLocalStack.setArgsOnLocal(bool2);
/*  660:     */       }
/*  661:     */     }
/*  662:     */   }
/*  663:     */   
/*  664:     */   public CustomerInfo[] getCustomerByType(String paramString)
/*  665:     */     throws java.rmi.RemoteException
/*  666:     */   {
/*  667: 567 */     for (int i = 1;; i++)
/*  668:     */     {
/*  669: 569 */       _Request local_Request = null;
/*  670: 570 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  671: 571 */       boolean bool1 = false;
/*  672: 572 */       LocalFrame localLocalFrame = null;
/*  673: 573 */       boolean bool2 = false;
/*  674:     */       try
/*  675:     */       {
/*  676: 576 */         local_Request = __request("getCustomerByType");
/*  677: 577 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  678: 578 */         bool2 = localLocalStack.isArgsOnLocal();
/*  679: 579 */         localLocalStack.setArgsOnLocal(bool1);
/*  680: 581 */         if (bool1)
/*  681:     */         {
/*  682: 583 */           localLocalFrame = new LocalFrame(1);
/*  683: 584 */           localLocalStack.push(localLocalFrame);
/*  684:     */         }
/*  685: 586 */         if (!bool1)
/*  686:     */         {
/*  687: 588 */           localObject4 = local_Request.getOutputStream();
/*  688: 589 */           local_Request.write_string(paramString);
/*  689:     */         }
/*  690:     */         else
/*  691:     */         {
/*  692: 593 */           localObject4 = local_Request.getOutputStream();
/*  693: 594 */           localLocalFrame.add(paramString);
/*  694:     */         }
/*  695: 596 */         local_Request.invoke();
/*  696:     */         Object localObject5;
/*  697:     */         Object localObject1;
/*  698: 597 */         if (bool1)
/*  699:     */         {
/*  700: 599 */           localObject4 = null;
/*  701: 600 */           localObject5 = localLocalFrame.getResult();
/*  702: 601 */           if (localObject5 != null) {
/*  703: 603 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*  704:     */           }
/*  705: 605 */           return localObject4;
/*  706:     */         }
/*  707: 607 */         Object localObject4 = local_Request.getInputStream();
/*  708: 609 */         if (local_Request.isRMI()) {
/*  709: 609 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*  710:     */         } else {
/*  711: 609 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*  712:     */         }
/*  713: 610 */         return localObject5;
/*  714:     */       }
/*  715:     */       catch (TRANSIENT localTRANSIENT)
/*  716:     */       {
/*  717: 614 */         if (i == 10) {
/*  718: 616 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  719:     */         }
/*  720:     */       }
/*  721:     */       catch (UserException localUserException)
/*  722:     */       {
/*  723: 621 */         local_Request.isRMI();
/*  724:     */         
/*  725:     */ 
/*  726: 624 */         throw new java.rmi.RemoteException(localUserException.type);
/*  727:     */       }
/*  728:     */       catch (SystemException localSystemException)
/*  729:     */       {
/*  730: 628 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  731:     */       }
/*  732:     */       finally
/*  733:     */       {
/*  734: 632 */         if (local_Request != null) {
/*  735: 634 */           local_Request.close();
/*  736:     */         }
/*  737: 636 */         if (bool1) {
/*  738: 637 */           localLocalStack.pop(localLocalFrame);
/*  739:     */         }
/*  740: 638 */         localLocalStack.setArgsOnLocal(bool2);
/*  741:     */       }
/*  742:     */     }
/*  743:     */   }
/*  744:     */   
/*  745:     */   public CustomerInfo[] getCustomerByFI(String paramString)
/*  746:     */     throws java.rmi.RemoteException
/*  747:     */   {
/*  748: 647 */     for (int i = 1;; i++)
/*  749:     */     {
/*  750: 649 */       _Request local_Request = null;
/*  751: 650 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  752: 651 */       boolean bool1 = false;
/*  753: 652 */       LocalFrame localLocalFrame = null;
/*  754: 653 */       boolean bool2 = false;
/*  755:     */       try
/*  756:     */       {
/*  757: 656 */         local_Request = __request("getCustomerByFI");
/*  758: 657 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  759: 658 */         bool2 = localLocalStack.isArgsOnLocal();
/*  760: 659 */         localLocalStack.setArgsOnLocal(bool1);
/*  761: 661 */         if (bool1)
/*  762:     */         {
/*  763: 663 */           localLocalFrame = new LocalFrame(1);
/*  764: 664 */           localLocalStack.push(localLocalFrame);
/*  765:     */         }
/*  766: 666 */         if (!bool1)
/*  767:     */         {
/*  768: 668 */           localObject4 = local_Request.getOutputStream();
/*  769: 669 */           local_Request.write_string(paramString);
/*  770:     */         }
/*  771:     */         else
/*  772:     */         {
/*  773: 673 */           localObject4 = local_Request.getOutputStream();
/*  774: 674 */           localLocalFrame.add(paramString);
/*  775:     */         }
/*  776: 676 */         local_Request.invoke();
/*  777:     */         Object localObject5;
/*  778:     */         Object localObject1;
/*  779: 677 */         if (bool1)
/*  780:     */         {
/*  781: 679 */           localObject4 = null;
/*  782: 680 */           localObject5 = localLocalFrame.getResult();
/*  783: 681 */           if (localObject5 != null) {
/*  784: 683 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*  785:     */           }
/*  786: 685 */           return localObject4;
/*  787:     */         }
/*  788: 687 */         Object localObject4 = local_Request.getInputStream();
/*  789: 689 */         if (local_Request.isRMI()) {
/*  790: 689 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*  791:     */         } else {
/*  792: 689 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*  793:     */         }
/*  794: 690 */         return localObject5;
/*  795:     */       }
/*  796:     */       catch (TRANSIENT localTRANSIENT)
/*  797:     */       {
/*  798: 694 */         if (i == 10) {
/*  799: 696 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  800:     */         }
/*  801:     */       }
/*  802:     */       catch (UserException localUserException)
/*  803:     */       {
/*  804: 701 */         local_Request.isRMI();
/*  805:     */         
/*  806:     */ 
/*  807: 704 */         throw new java.rmi.RemoteException(localUserException.type);
/*  808:     */       }
/*  809:     */       catch (SystemException localSystemException)
/*  810:     */       {
/*  811: 708 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  812:     */       }
/*  813:     */       finally
/*  814:     */       {
/*  815: 712 */         if (local_Request != null) {
/*  816: 714 */           local_Request.close();
/*  817:     */         }
/*  818: 716 */         if (bool1) {
/*  819: 717 */           localLocalStack.pop(localLocalFrame);
/*  820:     */         }
/*  821: 718 */         localLocalStack.setArgsOnLocal(bool2);
/*  822:     */       }
/*  823:     */     }
/*  824:     */   }
/*  825:     */   
/*  826:     */   public CustomerInfo[] getCustomerByCategory(String paramString)
/*  827:     */     throws java.rmi.RemoteException
/*  828:     */   {
/*  829: 727 */     for (int i = 1;; i++)
/*  830:     */     {
/*  831: 729 */       _Request local_Request = null;
/*  832: 730 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  833: 731 */       boolean bool1 = false;
/*  834: 732 */       LocalFrame localLocalFrame = null;
/*  835: 733 */       boolean bool2 = false;
/*  836:     */       try
/*  837:     */       {
/*  838: 736 */         local_Request = __request("getCustomerByCategory");
/*  839: 737 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  840: 738 */         bool2 = localLocalStack.isArgsOnLocal();
/*  841: 739 */         localLocalStack.setArgsOnLocal(bool1);
/*  842: 741 */         if (bool1)
/*  843:     */         {
/*  844: 743 */           localLocalFrame = new LocalFrame(1);
/*  845: 744 */           localLocalStack.push(localLocalFrame);
/*  846:     */         }
/*  847: 746 */         if (!bool1)
/*  848:     */         {
/*  849: 748 */           localObject4 = local_Request.getOutputStream();
/*  850: 749 */           local_Request.write_string(paramString);
/*  851:     */         }
/*  852:     */         else
/*  853:     */         {
/*  854: 753 */           localObject4 = local_Request.getOutputStream();
/*  855: 754 */           localLocalFrame.add(paramString);
/*  856:     */         }
/*  857: 756 */         local_Request.invoke();
/*  858:     */         Object localObject5;
/*  859:     */         Object localObject1;
/*  860: 757 */         if (bool1)
/*  861:     */         {
/*  862: 759 */           localObject4 = null;
/*  863: 760 */           localObject5 = localLocalFrame.getResult();
/*  864: 761 */           if (localObject5 != null) {
/*  865: 763 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*  866:     */           }
/*  867: 765 */           return localObject4;
/*  868:     */         }
/*  869: 767 */         Object localObject4 = local_Request.getInputStream();
/*  870: 769 */         if (local_Request.isRMI()) {
/*  871: 769 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*  872:     */         } else {
/*  873: 769 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*  874:     */         }
/*  875: 770 */         return localObject5;
/*  876:     */       }
/*  877:     */       catch (TRANSIENT localTRANSIENT)
/*  878:     */       {
/*  879: 774 */         if (i == 10) {
/*  880: 776 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  881:     */         }
/*  882:     */       }
/*  883:     */       catch (UserException localUserException)
/*  884:     */       {
/*  885: 781 */         local_Request.isRMI();
/*  886:     */         
/*  887:     */ 
/*  888: 784 */         throw new java.rmi.RemoteException(localUserException.type);
/*  889:     */       }
/*  890:     */       catch (SystemException localSystemException)
/*  891:     */       {
/*  892: 788 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  893:     */       }
/*  894:     */       finally
/*  895:     */       {
/*  896: 792 */         if (local_Request != null) {
/*  897: 794 */           local_Request.close();
/*  898:     */         }
/*  899: 796 */         if (bool1) {
/*  900: 797 */           localLocalStack.pop(localLocalFrame);
/*  901:     */         }
/*  902: 798 */         localLocalStack.setArgsOnLocal(bool2);
/*  903:     */       }
/*  904:     */     }
/*  905:     */   }
/*  906:     */   
/*  907:     */   public CustomerInfo[] getCustomerByGroup(String paramString)
/*  908:     */     throws java.rmi.RemoteException
/*  909:     */   {
/*  910: 807 */     for (int i = 1;; i++)
/*  911:     */     {
/*  912: 809 */       _Request local_Request = null;
/*  913: 810 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  914: 811 */       boolean bool1 = false;
/*  915: 812 */       LocalFrame localLocalFrame = null;
/*  916: 813 */       boolean bool2 = false;
/*  917:     */       try
/*  918:     */       {
/*  919: 816 */         local_Request = __request("getCustomerByGroup");
/*  920: 817 */         bool1 = ObjectRef._isLocalJava(local_Request);
/*  921: 818 */         bool2 = localLocalStack.isArgsOnLocal();
/*  922: 819 */         localLocalStack.setArgsOnLocal(bool1);
/*  923: 821 */         if (bool1)
/*  924:     */         {
/*  925: 823 */           localLocalFrame = new LocalFrame(1);
/*  926: 824 */           localLocalStack.push(localLocalFrame);
/*  927:     */         }
/*  928: 826 */         if (!bool1)
/*  929:     */         {
/*  930: 828 */           localObject4 = local_Request.getOutputStream();
/*  931: 829 */           local_Request.write_string(paramString);
/*  932:     */         }
/*  933:     */         else
/*  934:     */         {
/*  935: 833 */           localObject4 = local_Request.getOutputStream();
/*  936: 834 */           localLocalFrame.add(paramString);
/*  937:     */         }
/*  938: 836 */         local_Request.invoke();
/*  939:     */         Object localObject5;
/*  940:     */         Object localObject1;
/*  941: 837 */         if (bool1)
/*  942:     */         {
/*  943: 839 */           localObject4 = null;
/*  944: 840 */           localObject5 = localLocalFrame.getResult();
/*  945: 841 */           if (localObject5 != null) {
/*  946: 843 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/*  947:     */           }
/*  948: 845 */           return localObject4;
/*  949:     */         }
/*  950: 847 */         Object localObject4 = local_Request.getInputStream();
/*  951: 849 */         if (local_Request.isRMI()) {
/*  952: 849 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/*  953:     */         } else {
/*  954: 849 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/*  955:     */         }
/*  956: 850 */         return localObject5;
/*  957:     */       }
/*  958:     */       catch (TRANSIENT localTRANSIENT)
/*  959:     */       {
/*  960: 854 */         if (i == 10) {
/*  961: 856 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/*  962:     */         }
/*  963:     */       }
/*  964:     */       catch (UserException localUserException)
/*  965:     */       {
/*  966: 861 */         local_Request.isRMI();
/*  967:     */         
/*  968:     */ 
/*  969: 864 */         throw new java.rmi.RemoteException(localUserException.type);
/*  970:     */       }
/*  971:     */       catch (SystemException localSystemException)
/*  972:     */       {
/*  973: 868 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/*  974:     */       }
/*  975:     */       finally
/*  976:     */       {
/*  977: 872 */         if (local_Request != null) {
/*  978: 874 */           local_Request.close();
/*  979:     */         }
/*  980: 876 */         if (bool1) {
/*  981: 877 */           localLocalStack.pop(localLocalFrame);
/*  982:     */         }
/*  983: 878 */         localLocalStack.setArgsOnLocal(bool2);
/*  984:     */       }
/*  985:     */     }
/*  986:     */   }
/*  987:     */   
/*  988:     */   public CustomerInfo[] getCustomerByTypeAndFI(String paramString1, String paramString2)
/*  989:     */     throws java.rmi.RemoteException
/*  990:     */   {
/*  991: 888 */     for (int i = 1;; i++)
/*  992:     */     {
/*  993: 890 */       _Request local_Request = null;
/*  994: 891 */       LocalStack localLocalStack = LocalStack.getCurrent();
/*  995: 892 */       boolean bool1 = false;
/*  996: 893 */       LocalFrame localLocalFrame = null;
/*  997: 894 */       boolean bool2 = false;
/*  998:     */       try
/*  999:     */       {
/* 1000: 897 */         local_Request = __request("getCustomerByTypeAndFI");
/* 1001: 898 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1002: 899 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1003: 900 */         localLocalStack.setArgsOnLocal(bool1);
/* 1004: 902 */         if (bool1)
/* 1005:     */         {
/* 1006: 904 */           localLocalFrame = new LocalFrame(2);
/* 1007: 905 */           localLocalStack.push(localLocalFrame);
/* 1008:     */         }
/* 1009: 907 */         if (!bool1)
/* 1010:     */         {
/* 1011: 909 */           localObject4 = local_Request.getOutputStream();
/* 1012: 910 */           local_Request.write_string(paramString1);
/* 1013: 911 */           local_Request.write_string(paramString2);
/* 1014:     */         }
/* 1015:     */         else
/* 1016:     */         {
/* 1017: 915 */           localObject4 = local_Request.getOutputStream();
/* 1018: 916 */           localLocalFrame.add(paramString1);
/* 1019: 917 */           localLocalFrame.add(paramString2);
/* 1020:     */         }
/* 1021: 919 */         local_Request.invoke();
/* 1022:     */         Object localObject5;
/* 1023:     */         Object localObject1;
/* 1024: 920 */         if (bool1)
/* 1025:     */         {
/* 1026: 922 */           localObject4 = null;
/* 1027: 923 */           localObject5 = localLocalFrame.getResult();
/* 1028: 924 */           if (localObject5 != null) {
/* 1029: 926 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/* 1030:     */           }
/* 1031: 928 */           return localObject4;
/* 1032:     */         }
/* 1033: 930 */         Object localObject4 = local_Request.getInputStream();
/* 1034: 932 */         if (local_Request.isRMI()) {
/* 1035: 932 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/* 1036:     */         } else {
/* 1037: 932 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/* 1038:     */         }
/* 1039: 933 */         return localObject5;
/* 1040:     */       }
/* 1041:     */       catch (TRANSIENT localTRANSIENT)
/* 1042:     */       {
/* 1043: 937 */         if (i == 10) {
/* 1044: 939 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1045:     */         }
/* 1046:     */       }
/* 1047:     */       catch (UserException localUserException)
/* 1048:     */       {
/* 1049: 944 */         local_Request.isRMI();
/* 1050:     */         
/* 1051:     */ 
/* 1052: 947 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1053:     */       }
/* 1054:     */       catch (SystemException localSystemException)
/* 1055:     */       {
/* 1056: 951 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1057:     */       }
/* 1058:     */       finally
/* 1059:     */       {
/* 1060: 955 */         if (local_Request != null) {
/* 1061: 957 */           local_Request.close();
/* 1062:     */         }
/* 1063: 959 */         if (bool1) {
/* 1064: 960 */           localLocalStack.pop(localLocalFrame);
/* 1065:     */         }
/* 1066: 961 */         localLocalStack.setArgsOnLocal(bool2);
/* 1067:     */       }
/* 1068:     */     }
/* 1069:     */   }
/* 1070:     */   
/* 1071:     */   public CustomerInfo[] getCustomerByCategoryAndFI(String paramString1, String paramString2)
/* 1072:     */     throws java.rmi.RemoteException
/* 1073:     */   {
/* 1074: 971 */     for (int i = 1;; i++)
/* 1075:     */     {
/* 1076: 973 */       _Request local_Request = null;
/* 1077: 974 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1078: 975 */       boolean bool1 = false;
/* 1079: 976 */       LocalFrame localLocalFrame = null;
/* 1080: 977 */       boolean bool2 = false;
/* 1081:     */       try
/* 1082:     */       {
/* 1083: 980 */         local_Request = __request("getCustomerByCategoryAndFI");
/* 1084: 981 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1085: 982 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1086: 983 */         localLocalStack.setArgsOnLocal(bool1);
/* 1087: 985 */         if (bool1)
/* 1088:     */         {
/* 1089: 987 */           localLocalFrame = new LocalFrame(2);
/* 1090: 988 */           localLocalStack.push(localLocalFrame);
/* 1091:     */         }
/* 1092: 990 */         if (!bool1)
/* 1093:     */         {
/* 1094: 992 */           localObject4 = local_Request.getOutputStream();
/* 1095: 993 */           local_Request.write_string(paramString1);
/* 1096: 994 */           local_Request.write_string(paramString2);
/* 1097:     */         }
/* 1098:     */         else
/* 1099:     */         {
/* 1100: 998 */           localObject4 = local_Request.getOutputStream();
/* 1101: 999 */           localLocalFrame.add(paramString1);
/* 1102:1000 */           localLocalFrame.add(paramString2);
/* 1103:     */         }
/* 1104:1002 */         local_Request.invoke();
/* 1105:     */         Object localObject5;
/* 1106:     */         Object localObject1;
/* 1107:1003 */         if (bool1)
/* 1108:     */         {
/* 1109:1005 */           localObject4 = null;
/* 1110:1006 */           localObject5 = localLocalFrame.getResult();
/* 1111:1007 */           if (localObject5 != null) {
/* 1112:1009 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/* 1113:     */           }
/* 1114:1011 */           return localObject4;
/* 1115:     */         }
/* 1116:1013 */         Object localObject4 = local_Request.getInputStream();
/* 1117:1015 */         if (local_Request.isRMI()) {
/* 1118:1015 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/* 1119:     */         } else {
/* 1120:1015 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/* 1121:     */         }
/* 1122:1016 */         return localObject5;
/* 1123:     */       }
/* 1124:     */       catch (TRANSIENT localTRANSIENT)
/* 1125:     */       {
/* 1126:1020 */         if (i == 10) {
/* 1127:1022 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1128:     */         }
/* 1129:     */       }
/* 1130:     */       catch (UserException localUserException)
/* 1131:     */       {
/* 1132:1027 */         local_Request.isRMI();
/* 1133:     */         
/* 1134:     */ 
/* 1135:1030 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1136:     */       }
/* 1137:     */       catch (SystemException localSystemException)
/* 1138:     */       {
/* 1139:1034 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1140:     */       }
/* 1141:     */       finally
/* 1142:     */       {
/* 1143:1038 */         if (local_Request != null) {
/* 1144:1040 */           local_Request.close();
/* 1145:     */         }
/* 1146:1042 */         if (bool1) {
/* 1147:1043 */           localLocalStack.pop(localLocalFrame);
/* 1148:     */         }
/* 1149:1044 */         localLocalStack.setArgsOnLocal(bool2);
/* 1150:     */       }
/* 1151:     */     }
/* 1152:     */   }
/* 1153:     */   
/* 1154:     */   public CustomerInfo[] getCustomerByGroupAndFI(String paramString1, String paramString2)
/* 1155:     */     throws java.rmi.RemoteException
/* 1156:     */   {
/* 1157:1054 */     for (int i = 1;; i++)
/* 1158:     */     {
/* 1159:1056 */       _Request local_Request = null;
/* 1160:1057 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1161:1058 */       boolean bool1 = false;
/* 1162:1059 */       LocalFrame localLocalFrame = null;
/* 1163:1060 */       boolean bool2 = false;
/* 1164:     */       try
/* 1165:     */       {
/* 1166:1063 */         local_Request = __request("getCustomerByGroupAndFI");
/* 1167:1064 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1168:1065 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1169:1066 */         localLocalStack.setArgsOnLocal(bool1);
/* 1170:1068 */         if (bool1)
/* 1171:     */         {
/* 1172:1070 */           localLocalFrame = new LocalFrame(2);
/* 1173:1071 */           localLocalStack.push(localLocalFrame);
/* 1174:     */         }
/* 1175:1073 */         if (!bool1)
/* 1176:     */         {
/* 1177:1075 */           localObject4 = local_Request.getOutputStream();
/* 1178:1076 */           local_Request.write_string(paramString1);
/* 1179:1077 */           local_Request.write_string(paramString2);
/* 1180:     */         }
/* 1181:     */         else
/* 1182:     */         {
/* 1183:1081 */           localObject4 = local_Request.getOutputStream();
/* 1184:1082 */           localLocalFrame.add(paramString1);
/* 1185:1083 */           localLocalFrame.add(paramString2);
/* 1186:     */         }
/* 1187:1085 */         local_Request.invoke();
/* 1188:     */         Object localObject5;
/* 1189:     */         Object localObject1;
/* 1190:1086 */         if (bool1)
/* 1191:     */         {
/* 1192:1088 */           localObject4 = null;
/* 1193:1089 */           localObject5 = localLocalFrame.getResult();
/* 1194:1090 */           if (localObject5 != null) {
/* 1195:1092 */             localObject4 = (CustomerInfo[])ObjectVal.clone(localObject5);
/* 1196:     */           }
/* 1197:1094 */           return localObject4;
/* 1198:     */         }
/* 1199:1096 */         Object localObject4 = local_Request.getInputStream();
/* 1200:1098 */         if (local_Request.isRMI()) {
/* 1201:1098 */           localObject5 = (CustomerInfo[])local_Request.read_value(new CustomerInfo[0].getClass());
/* 1202:     */         } else {
/* 1203:1098 */           localObject5 = CustomerInfoSeqHelper.read((InputStream)localObject4);
/* 1204:     */         }
/* 1205:1099 */         return localObject5;
/* 1206:     */       }
/* 1207:     */       catch (TRANSIENT localTRANSIENT)
/* 1208:     */       {
/* 1209:1103 */         if (i == 10) {
/* 1210:1105 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1211:     */         }
/* 1212:     */       }
/* 1213:     */       catch (UserException localUserException)
/* 1214:     */       {
/* 1215:1110 */         local_Request.isRMI();
/* 1216:     */         
/* 1217:     */ 
/* 1218:1113 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1219:     */       }
/* 1220:     */       catch (SystemException localSystemException)
/* 1221:     */       {
/* 1222:1117 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1223:     */       }
/* 1224:     */       finally
/* 1225:     */       {
/* 1226:1121 */         if (local_Request != null) {
/* 1227:1123 */           local_Request.close();
/* 1228:     */         }
/* 1229:1125 */         if (bool1) {
/* 1230:1126 */           localLocalStack.pop(localLocalFrame);
/* 1231:     */         }
/* 1232:1127 */         localLocalStack.setArgsOnLocal(bool2);
/* 1233:     */       }
/* 1234:     */     }
/* 1235:     */   }
/* 1236:     */   
/* 1237:     */   public PayeeInfo[] getLinkedPayees()
/* 1238:     */     throws java.rmi.RemoteException
/* 1239:     */   {
/* 1240:1135 */     for (int i = 1;; i++)
/* 1241:     */     {
/* 1242:1137 */       _Request local_Request = null;
/* 1243:1138 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1244:1139 */       boolean bool1 = false;
/* 1245:1140 */       LocalFrame localLocalFrame = null;
/* 1246:1141 */       boolean bool2 = false;
/* 1247:     */       try
/* 1248:     */       {
/* 1249:1144 */         local_Request = __request("getLinkedPayees", "_get_linkedPayees");
/* 1250:1145 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1251:1146 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1252:1147 */         localLocalStack.setArgsOnLocal(bool1);
/* 1253:1149 */         if (bool1)
/* 1254:     */         {
/* 1255:1151 */           localLocalFrame = new LocalFrame(0);
/* 1256:1152 */           localLocalStack.push(localLocalFrame);
/* 1257:     */         }
/* 1258:1160 */         local_Request.invoke();
/* 1259:     */         Object localObject5;
/* 1260:     */         Object localObject1;
/* 1261:1161 */         if (bool1)
/* 1262:     */         {
/* 1263:1163 */           localObject4 = null;
/* 1264:1164 */           localObject5 = localLocalFrame.getResult();
/* 1265:1165 */           if (localObject5 != null) {
/* 1266:1167 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 1267:     */           }
/* 1268:1169 */           return localObject4;
/* 1269:     */         }
/* 1270:1171 */         Object localObject4 = local_Request.getInputStream();
/* 1271:1173 */         if (local_Request.isRMI()) {
/* 1272:1173 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 1273:     */         } else {
/* 1274:1173 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 1275:     */         }
/* 1276:1174 */         return localObject5;
/* 1277:     */       }
/* 1278:     */       catch (TRANSIENT localTRANSIENT)
/* 1279:     */       {
/* 1280:1178 */         if (i == 10) {
/* 1281:1180 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1282:     */         }
/* 1283:     */       }
/* 1284:     */       catch (UserException localUserException)
/* 1285:     */       {
/* 1286:1185 */         local_Request.isRMI();
/* 1287:     */         
/* 1288:     */ 
/* 1289:1188 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1290:     */       }
/* 1291:     */       catch (SystemException localSystemException)
/* 1292:     */       {
/* 1293:1192 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1294:     */       }
/* 1295:     */       finally
/* 1296:     */       {
/* 1297:1196 */         if (local_Request != null) {
/* 1298:1198 */           local_Request.close();
/* 1299:     */         }
/* 1300:1200 */         if (bool1) {
/* 1301:1201 */           localLocalStack.pop(localLocalFrame);
/* 1302:     */         }
/* 1303:1202 */         localLocalStack.setArgsOnLocal(bool2);
/* 1304:     */       }
/* 1305:     */     }
/* 1306:     */   }
/* 1307:     */   
/* 1308:     */   public PayeeInfo[] getMostUsedPayees(int paramInt)
/* 1309:     */     throws java.rmi.RemoteException
/* 1310:     */   {
/* 1311:1211 */     for (int i = 1;; i++)
/* 1312:     */     {
/* 1313:1213 */       _Request local_Request = null;
/* 1314:1214 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1315:1215 */       boolean bool1 = false;
/* 1316:1216 */       LocalFrame localLocalFrame = null;
/* 1317:1217 */       boolean bool2 = false;
/* 1318:     */       try
/* 1319:     */       {
/* 1320:1220 */         local_Request = __request("getMostUsedPayees");
/* 1321:1221 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1322:1222 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1323:1223 */         localLocalStack.setArgsOnLocal(bool1);
/* 1324:1225 */         if (bool1)
/* 1325:     */         {
/* 1326:1227 */           localLocalFrame = new LocalFrame(1);
/* 1327:1228 */           localLocalStack.push(localLocalFrame);
/* 1328:     */         }
/* 1329:1230 */         if (!bool1)
/* 1330:     */         {
/* 1331:1232 */           localObject4 = local_Request.getOutputStream();
/* 1332:1233 */           ((OutputStream)localObject4).write_long(paramInt);
/* 1333:     */         }
/* 1334:     */         else
/* 1335:     */         {
/* 1336:1237 */           localObject4 = local_Request.getOutputStream();
/* 1337:1238 */           localLocalFrame.add(paramInt);
/* 1338:     */         }
/* 1339:1240 */         local_Request.invoke();
/* 1340:     */         Object localObject5;
/* 1341:     */         Object localObject1;
/* 1342:1241 */         if (bool1)
/* 1343:     */         {
/* 1344:1243 */           localObject4 = null;
/* 1345:1244 */           localObject5 = localLocalFrame.getResult();
/* 1346:1245 */           if (localObject5 != null) {
/* 1347:1247 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 1348:     */           }
/* 1349:1249 */           return localObject4;
/* 1350:     */         }
/* 1351:1251 */         Object localObject4 = local_Request.getInputStream();
/* 1352:1253 */         if (local_Request.isRMI()) {
/* 1353:1253 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 1354:     */         } else {
/* 1355:1253 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 1356:     */         }
/* 1357:1254 */         return localObject5;
/* 1358:     */       }
/* 1359:     */       catch (TRANSIENT localTRANSIENT)
/* 1360:     */       {
/* 1361:1258 */         if (i == 10) {
/* 1362:1260 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1363:     */         }
/* 1364:     */       }
/* 1365:     */       catch (UserException localUserException)
/* 1366:     */       {
/* 1367:1265 */         local_Request.isRMI();
/* 1368:     */         
/* 1369:     */ 
/* 1370:1268 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1371:     */       }
/* 1372:     */       catch (SystemException localSystemException)
/* 1373:     */       {
/* 1374:1272 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1375:     */       }
/* 1376:     */       finally
/* 1377:     */       {
/* 1378:1276 */         if (local_Request != null) {
/* 1379:1278 */           local_Request.close();
/* 1380:     */         }
/* 1381:1280 */         if (bool1) {
/* 1382:1281 */           localLocalStack.pop(localLocalFrame);
/* 1383:     */         }
/* 1384:1282 */         localLocalStack.setArgsOnLocal(bool2);
/* 1385:     */       }
/* 1386:     */     }
/* 1387:     */   }
/* 1388:     */   
/* 1389:     */   public PayeeInfo[] getPreferredPayees(String paramString)
/* 1390:     */     throws java.rmi.RemoteException
/* 1391:     */   {
/* 1392:1291 */     for (int i = 1;; i++)
/* 1393:     */     {
/* 1394:1293 */       _Request local_Request = null;
/* 1395:1294 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1396:1295 */       boolean bool1 = false;
/* 1397:1296 */       LocalFrame localLocalFrame = null;
/* 1398:1297 */       boolean bool2 = false;
/* 1399:     */       try
/* 1400:     */       {
/* 1401:1300 */         local_Request = __request("getPreferredPayees");
/* 1402:1301 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1403:1302 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1404:1303 */         localLocalStack.setArgsOnLocal(bool1);
/* 1405:1305 */         if (bool1)
/* 1406:     */         {
/* 1407:1307 */           localLocalFrame = new LocalFrame(1);
/* 1408:1308 */           localLocalStack.push(localLocalFrame);
/* 1409:     */         }
/* 1410:1310 */         if (!bool1)
/* 1411:     */         {
/* 1412:1312 */           localObject4 = local_Request.getOutputStream();
/* 1413:1313 */           local_Request.write_string(paramString);
/* 1414:     */         }
/* 1415:     */         else
/* 1416:     */         {
/* 1417:1317 */           localObject4 = local_Request.getOutputStream();
/* 1418:1318 */           localLocalFrame.add(paramString);
/* 1419:     */         }
/* 1420:1320 */         local_Request.invoke();
/* 1421:     */         Object localObject5;
/* 1422:     */         Object localObject1;
/* 1423:1321 */         if (bool1)
/* 1424:     */         {
/* 1425:1323 */           localObject4 = null;
/* 1426:1324 */           localObject5 = localLocalFrame.getResult();
/* 1427:1325 */           if (localObject5 != null) {
/* 1428:1327 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 1429:     */           }
/* 1430:1329 */           return localObject4;
/* 1431:     */         }
/* 1432:1331 */         Object localObject4 = local_Request.getInputStream();
/* 1433:1333 */         if (local_Request.isRMI()) {
/* 1434:1333 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 1435:     */         } else {
/* 1436:1333 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 1437:     */         }
/* 1438:1334 */         return localObject5;
/* 1439:     */       }
/* 1440:     */       catch (TRANSIENT localTRANSIENT)
/* 1441:     */       {
/* 1442:1338 */         if (i == 10) {
/* 1443:1340 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1444:     */         }
/* 1445:     */       }
/* 1446:     */       catch (UserException localUserException)
/* 1447:     */       {
/* 1448:1345 */         local_Request.isRMI();
/* 1449:     */         
/* 1450:     */ 
/* 1451:1348 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1452:     */       }
/* 1453:     */       catch (SystemException localSystemException)
/* 1454:     */       {
/* 1455:1352 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1456:     */       }
/* 1457:     */       finally
/* 1458:     */       {
/* 1459:1356 */         if (local_Request != null) {
/* 1460:1358 */           local_Request.close();
/* 1461:     */         }
/* 1462:1360 */         if (bool1) {
/* 1463:1361 */           localLocalStack.pop(localLocalFrame);
/* 1464:     */         }
/* 1465:1362 */         localLocalStack.setArgsOnLocal(bool2);
/* 1466:     */       }
/* 1467:     */     }
/* 1468:     */   }
/* 1469:     */   
/* 1470:     */   public TypePmtSyncRsV1[] getPendingPmtsByCustomerID(String paramString, int paramInt)
/* 1471:     */     throws java.rmi.RemoteException
/* 1472:     */   {
/* 1473:1372 */     for (int i = 1;; i++)
/* 1474:     */     {
/* 1475:1374 */       _Request local_Request = null;
/* 1476:1375 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1477:1376 */       boolean bool1 = false;
/* 1478:1377 */       LocalFrame localLocalFrame = null;
/* 1479:1378 */       boolean bool2 = false;
/* 1480:     */       try
/* 1481:     */       {
/* 1482:1381 */         local_Request = __request("getPendingPmtsByCustomerID");
/* 1483:1382 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1484:1383 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1485:1384 */         localLocalStack.setArgsOnLocal(bool1);
/* 1486:1386 */         if (bool1)
/* 1487:     */         {
/* 1488:1388 */           localLocalFrame = new LocalFrame(2);
/* 1489:1389 */           localLocalStack.push(localLocalFrame);
/* 1490:     */         }
/* 1491:1391 */         if (!bool1)
/* 1492:     */         {
/* 1493:1393 */           localObject4 = local_Request.getOutputStream();
/* 1494:1394 */           local_Request.write_string(paramString);
/* 1495:1395 */           ((OutputStream)localObject4).write_long(paramInt);
/* 1496:     */         }
/* 1497:     */         else
/* 1498:     */         {
/* 1499:1399 */           localObject4 = local_Request.getOutputStream();
/* 1500:1400 */           localLocalFrame.add(paramString);
/* 1501:1401 */           localLocalFrame.add(paramInt);
/* 1502:     */         }
/* 1503:1403 */         local_Request.invoke();
/* 1504:     */         Object localObject5;
/* 1505:     */         Object localObject1;
/* 1506:1404 */         if (bool1)
/* 1507:     */         {
/* 1508:1406 */           localObject4 = null;
/* 1509:1407 */           localObject5 = localLocalFrame.getResult();
/* 1510:1408 */           if (localObject5 != null) {
/* 1511:1410 */             localObject4 = (TypePmtSyncRsV1[])ObjectVal.clone(localObject5);
/* 1512:     */           }
/* 1513:1412 */           return localObject4;
/* 1514:     */         }
/* 1515:1414 */         Object localObject4 = local_Request.getInputStream();
/* 1516:1416 */         if (local_Request.isRMI()) {
/* 1517:1416 */           localObject5 = (TypePmtSyncRsV1[])local_Request.read_value(new TypePmtSyncRsV1[0].getClass());
/* 1518:     */         } else {
/* 1519:1416 */           localObject5 = TypePmtSyncRsV1SeqHelper.read((InputStream)localObject4);
/* 1520:     */         }
/* 1521:1417 */         return localObject5;
/* 1522:     */       }
/* 1523:     */       catch (TRANSIENT localTRANSIENT)
/* 1524:     */       {
/* 1525:1421 */         if (i == 10) {
/* 1526:1423 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1527:     */         }
/* 1528:     */       }
/* 1529:     */       catch (UserException localUserException)
/* 1530:     */       {
/* 1531:1428 */         local_Request.isRMI();
/* 1532:     */         
/* 1533:     */ 
/* 1534:1431 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1535:     */       }
/* 1536:     */       catch (SystemException localSystemException)
/* 1537:     */       {
/* 1538:1435 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1539:     */       }
/* 1540:     */       finally
/* 1541:     */       {
/* 1542:1439 */         if (local_Request != null) {
/* 1543:1441 */           local_Request.close();
/* 1544:     */         }
/* 1545:1443 */         if (bool1) {
/* 1546:1444 */           localLocalStack.pop(localLocalFrame);
/* 1547:     */         }
/* 1548:1445 */         localLocalStack.setArgsOnLocal(bool2);
/* 1549:     */       }
/* 1550:     */     }
/* 1551:     */   }
/* 1552:     */   
/* 1553:     */   public TypePmtSyncRsV1[] getPendingPmtsAndHistoryByCustomerID(String paramString, int paramInt1, int paramInt2)
/* 1554:     */     throws java.rmi.RemoteException
/* 1555:     */   {
/* 1556:1456 */     for (int i = 1;; i++)
/* 1557:     */     {
/* 1558:1458 */       _Request local_Request = null;
/* 1559:1459 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1560:1460 */       boolean bool1 = false;
/* 1561:1461 */       LocalFrame localLocalFrame = null;
/* 1562:1462 */       boolean bool2 = false;
/* 1563:     */       try
/* 1564:     */       {
/* 1565:1465 */         local_Request = __request("getPendingPmtsAndHistoryByCustomerID");
/* 1566:1466 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1567:1467 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1568:1468 */         localLocalStack.setArgsOnLocal(bool1);
/* 1569:1470 */         if (bool1)
/* 1570:     */         {
/* 1571:1472 */           localLocalFrame = new LocalFrame(3);
/* 1572:1473 */           localLocalStack.push(localLocalFrame);
/* 1573:     */         }
/* 1574:1475 */         if (!bool1)
/* 1575:     */         {
/* 1576:1477 */           localObject4 = local_Request.getOutputStream();
/* 1577:1478 */           local_Request.write_string(paramString);
/* 1578:1479 */           ((OutputStream)localObject4).write_long(paramInt1);
/* 1579:1480 */           ((OutputStream)localObject4).write_long(paramInt2);
/* 1580:     */         }
/* 1581:     */         else
/* 1582:     */         {
/* 1583:1484 */           localObject4 = local_Request.getOutputStream();
/* 1584:1485 */           localLocalFrame.add(paramString);
/* 1585:1486 */           localLocalFrame.add(paramInt1);
/* 1586:1487 */           localLocalFrame.add(paramInt2);
/* 1587:     */         }
/* 1588:1489 */         local_Request.invoke();
/* 1589:     */         Object localObject5;
/* 1590:     */         Object localObject1;
/* 1591:1490 */         if (bool1)
/* 1592:     */         {
/* 1593:1492 */           localObject4 = null;
/* 1594:1493 */           localObject5 = localLocalFrame.getResult();
/* 1595:1494 */           if (localObject5 != null) {
/* 1596:1496 */             localObject4 = (TypePmtSyncRsV1[])ObjectVal.clone(localObject5);
/* 1597:     */           }
/* 1598:1498 */           return localObject4;
/* 1599:     */         }
/* 1600:1500 */         Object localObject4 = local_Request.getInputStream();
/* 1601:1502 */         if (local_Request.isRMI()) {
/* 1602:1502 */           localObject5 = (TypePmtSyncRsV1[])local_Request.read_value(new TypePmtSyncRsV1[0].getClass());
/* 1603:     */         } else {
/* 1604:1502 */           localObject5 = TypePmtSyncRsV1SeqHelper.read((InputStream)localObject4);
/* 1605:     */         }
/* 1606:1503 */         return localObject5;
/* 1607:     */       }
/* 1608:     */       catch (TRANSIENT localTRANSIENT)
/* 1609:     */       {
/* 1610:1507 */         if (i == 10) {
/* 1611:1509 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1612:     */         }
/* 1613:     */       }
/* 1614:     */       catch (UserException localUserException)
/* 1615:     */       {
/* 1616:1514 */         local_Request.isRMI();
/* 1617:     */         
/* 1618:     */ 
/* 1619:1517 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1620:     */       }
/* 1621:     */       catch (SystemException localSystemException)
/* 1622:     */       {
/* 1623:1521 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1624:     */       }
/* 1625:     */       finally
/* 1626:     */       {
/* 1627:1525 */         if (local_Request != null) {
/* 1628:1527 */           local_Request.close();
/* 1629:     */         }
/* 1630:1529 */         if (bool1) {
/* 1631:1530 */           localLocalStack.pop(localLocalFrame);
/* 1632:     */         }
/* 1633:1531 */         localLocalStack.setArgsOnLocal(bool2);
/* 1634:     */       }
/* 1635:     */     }
/* 1636:     */   }
/* 1637:     */   
/* 1638:     */   public TypeIntraSyncRsV1[] getPendingIntrasByCustomerID(String paramString, int paramInt)
/* 1639:     */     throws java.rmi.RemoteException
/* 1640:     */   {
/* 1641:1541 */     for (int i = 1;; i++)
/* 1642:     */     {
/* 1643:1543 */       _Request local_Request = null;
/* 1644:1544 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1645:1545 */       boolean bool1 = false;
/* 1646:1546 */       LocalFrame localLocalFrame = null;
/* 1647:1547 */       boolean bool2 = false;
/* 1648:     */       try
/* 1649:     */       {
/* 1650:1550 */         local_Request = __request("getPendingIntrasByCustomerID");
/* 1651:1551 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1652:1552 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1653:1553 */         localLocalStack.setArgsOnLocal(bool1);
/* 1654:1555 */         if (bool1)
/* 1655:     */         {
/* 1656:1557 */           localLocalFrame = new LocalFrame(2);
/* 1657:1558 */           localLocalStack.push(localLocalFrame);
/* 1658:     */         }
/* 1659:1560 */         if (!bool1)
/* 1660:     */         {
/* 1661:1562 */           localObject4 = local_Request.getOutputStream();
/* 1662:1563 */           local_Request.write_string(paramString);
/* 1663:1564 */           ((OutputStream)localObject4).write_long(paramInt);
/* 1664:     */         }
/* 1665:     */         else
/* 1666:     */         {
/* 1667:1568 */           localObject4 = local_Request.getOutputStream();
/* 1668:1569 */           localLocalFrame.add(paramString);
/* 1669:1570 */           localLocalFrame.add(paramInt);
/* 1670:     */         }
/* 1671:1572 */         local_Request.invoke();
/* 1672:     */         Object localObject5;
/* 1673:     */         Object localObject1;
/* 1674:1573 */         if (bool1)
/* 1675:     */         {
/* 1676:1575 */           localObject4 = null;
/* 1677:1576 */           localObject5 = localLocalFrame.getResult();
/* 1678:1577 */           if (localObject5 != null) {
/* 1679:1579 */             localObject4 = (TypeIntraSyncRsV1[])ObjectVal.clone(localObject5);
/* 1680:     */           }
/* 1681:1581 */           return localObject4;
/* 1682:     */         }
/* 1683:1583 */         Object localObject4 = local_Request.getInputStream();
/* 1684:1585 */         if (local_Request.isRMI()) {
/* 1685:1585 */           localObject5 = (TypeIntraSyncRsV1[])local_Request.read_value(new TypeIntraSyncRsV1[0].getClass());
/* 1686:     */         } else {
/* 1687:1585 */           localObject5 = TypeIntraSyncRsV1SeqHelper.read((InputStream)localObject4);
/* 1688:     */         }
/* 1689:1586 */         return localObject5;
/* 1690:     */       }
/* 1691:     */       catch (TRANSIENT localTRANSIENT)
/* 1692:     */       {
/* 1693:1590 */         if (i == 10) {
/* 1694:1592 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1695:     */         }
/* 1696:     */       }
/* 1697:     */       catch (UserException localUserException)
/* 1698:     */       {
/* 1699:1597 */         local_Request.isRMI();
/* 1700:     */         
/* 1701:     */ 
/* 1702:1600 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1703:     */       }
/* 1704:     */       catch (SystemException localSystemException)
/* 1705:     */       {
/* 1706:1604 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1707:     */       }
/* 1708:     */       finally
/* 1709:     */       {
/* 1710:1608 */         if (local_Request != null) {
/* 1711:1610 */           local_Request.close();
/* 1712:     */         }
/* 1713:1612 */         if (bool1) {
/* 1714:1613 */           localLocalStack.pop(localLocalFrame);
/* 1715:     */         }
/* 1716:1614 */         localLocalStack.setArgsOnLocal(bool2);
/* 1717:     */       }
/* 1718:     */     }
/* 1719:     */   }
/* 1720:     */   
/* 1721:     */   public TypeIntraSyncRsV1[] getPendingIntrasAndHistoryByCustomerID(String paramString, int paramInt1, int paramInt2)
/* 1722:     */     throws java.rmi.RemoteException
/* 1723:     */   {
/* 1724:1625 */     for (int i = 1;; i++)
/* 1725:     */     {
/* 1726:1627 */       _Request local_Request = null;
/* 1727:1628 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1728:1629 */       boolean bool1 = false;
/* 1729:1630 */       LocalFrame localLocalFrame = null;
/* 1730:1631 */       boolean bool2 = false;
/* 1731:     */       try
/* 1732:     */       {
/* 1733:1634 */         local_Request = __request("getPendingIntrasAndHistoryByCustomerID");
/* 1734:1635 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1735:1636 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1736:1637 */         localLocalStack.setArgsOnLocal(bool1);
/* 1737:1639 */         if (bool1)
/* 1738:     */         {
/* 1739:1641 */           localLocalFrame = new LocalFrame(3);
/* 1740:1642 */           localLocalStack.push(localLocalFrame);
/* 1741:     */         }
/* 1742:1644 */         if (!bool1)
/* 1743:     */         {
/* 1744:1646 */           localObject4 = local_Request.getOutputStream();
/* 1745:1647 */           local_Request.write_string(paramString);
/* 1746:1648 */           ((OutputStream)localObject4).write_long(paramInt1);
/* 1747:1649 */           ((OutputStream)localObject4).write_long(paramInt2);
/* 1748:     */         }
/* 1749:     */         else
/* 1750:     */         {
/* 1751:1653 */           localObject4 = local_Request.getOutputStream();
/* 1752:1654 */           localLocalFrame.add(paramString);
/* 1753:1655 */           localLocalFrame.add(paramInt1);
/* 1754:1656 */           localLocalFrame.add(paramInt2);
/* 1755:     */         }
/* 1756:1658 */         local_Request.invoke();
/* 1757:     */         Object localObject5;
/* 1758:     */         Object localObject1;
/* 1759:1659 */         if (bool1)
/* 1760:     */         {
/* 1761:1661 */           localObject4 = null;
/* 1762:1662 */           localObject5 = localLocalFrame.getResult();
/* 1763:1663 */           if (localObject5 != null) {
/* 1764:1665 */             localObject4 = (TypeIntraSyncRsV1[])ObjectVal.clone(localObject5);
/* 1765:     */           }
/* 1766:1667 */           return localObject4;
/* 1767:     */         }
/* 1768:1669 */         Object localObject4 = local_Request.getInputStream();
/* 1769:1671 */         if (local_Request.isRMI()) {
/* 1770:1671 */           localObject5 = (TypeIntraSyncRsV1[])local_Request.read_value(new TypeIntraSyncRsV1[0].getClass());
/* 1771:     */         } else {
/* 1772:1671 */           localObject5 = TypeIntraSyncRsV1SeqHelper.read((InputStream)localObject4);
/* 1773:     */         }
/* 1774:1672 */         return localObject5;
/* 1775:     */       }
/* 1776:     */       catch (TRANSIENT localTRANSIENT)
/* 1777:     */       {
/* 1778:1676 */         if (i == 10) {
/* 1779:1678 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1780:     */         }
/* 1781:     */       }
/* 1782:     */       catch (UserException localUserException)
/* 1783:     */       {
/* 1784:1683 */         local_Request.isRMI();
/* 1785:     */         
/* 1786:     */ 
/* 1787:1686 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1788:     */       }
/* 1789:     */       catch (SystemException localSystemException)
/* 1790:     */       {
/* 1791:1690 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1792:     */       }
/* 1793:     */       finally
/* 1794:     */       {
/* 1795:1694 */         if (local_Request != null) {
/* 1796:1696 */           local_Request.close();
/* 1797:     */         }
/* 1798:1698 */         if (bool1) {
/* 1799:1699 */           localLocalStack.pop(localLocalFrame);
/* 1800:     */         }
/* 1801:1700 */         localLocalStack.setArgsOnLocal(bool2);
/* 1802:     */       }
/* 1803:     */     }
/* 1804:     */   }
/* 1805:     */   
/* 1806:     */   public TypePmtSyncRsV1 getPendingPmts(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
/* 1807:     */     throws java.rmi.RemoteException
/* 1808:     */   {
/* 1809:1711 */     for (int i = 1;; i++)
/* 1810:     */     {
/* 1811:1713 */       _Request local_Request = null;
/* 1812:1714 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1813:1715 */       boolean bool1 = false;
/* 1814:1716 */       LocalFrame localLocalFrame = null;
/* 1815:1717 */       boolean bool2 = false;
/* 1816:     */       try
/* 1817:     */       {
/* 1818:1720 */         local_Request = __request("getPendingPmts");
/* 1819:1721 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1820:1722 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1821:1723 */         localLocalStack.setArgsOnLocal(bool1);
/* 1822:1725 */         if (bool1)
/* 1823:     */         {
/* 1824:1727 */           localLocalFrame = new LocalFrame(3);
/* 1825:1728 */           localLocalStack.push(localLocalFrame);
/* 1826:     */         }
/* 1827:1730 */         if (!bool1)
/* 1828:     */         {
/* 1829:1732 */           localObject4 = local_Request.getOutputStream();
/* 1830:1733 */           if (local_Request.isRMI()) {
/* 1831:1733 */             local_Request.write_value(paramTypePmtSyncRqV1, TypePmtSyncRqV1.class);
/* 1832:     */           } else {
/* 1833:1733 */             TypePmtSyncRqV1Helper.write((OutputStream)localObject4, paramTypePmtSyncRqV1);
/* 1834:     */           }
/* 1835:1734 */           if (local_Request.isRMI()) {
/* 1836:1734 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 1837:     */           } else {
/* 1838:1734 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 1839:     */           }
/* 1840:1735 */           ((OutputStream)localObject4).write_long(paramInt);
/* 1841:     */         }
/* 1842:     */         else
/* 1843:     */         {
/* 1844:1739 */           localObject4 = local_Request.getOutputStream();
/* 1845:1740 */           localLocalFrame.add(paramTypePmtSyncRqV1);
/* 1846:1741 */           localLocalFrame.add(paramTypeUserData);
/* 1847:1742 */           localLocalFrame.add(paramInt);
/* 1848:     */         }
/* 1849:1744 */         local_Request.invoke();
/* 1850:     */         Object localObject5;
/* 1851:     */         Object localObject1;
/* 1852:1745 */         if (bool1)
/* 1853:     */         {
/* 1854:1747 */           localObject4 = null;
/* 1855:1748 */           localObject5 = localLocalFrame.getResult();
/* 1856:1749 */           if (localObject5 != null) {
/* 1857:1751 */             localObject4 = (TypePmtSyncRsV1)ObjectVal.clone(localObject5);
/* 1858:     */           }
/* 1859:1753 */           return localObject4;
/* 1860:     */         }
/* 1861:1755 */         Object localObject4 = local_Request.getInputStream();
/* 1862:1757 */         if (local_Request.isRMI()) {
/* 1863:1757 */           localObject5 = (TypePmtSyncRsV1)local_Request.read_value(TypePmtSyncRsV1.class);
/* 1864:     */         } else {
/* 1865:1757 */           localObject5 = TypePmtSyncRsV1Helper.read((InputStream)localObject4);
/* 1866:     */         }
/* 1867:1758 */         return localObject5;
/* 1868:     */       }
/* 1869:     */       catch (TRANSIENT localTRANSIENT)
/* 1870:     */       {
/* 1871:1762 */         if (i == 10) {
/* 1872:1764 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1873:     */         }
/* 1874:     */       }
/* 1875:     */       catch (UserException localUserException)
/* 1876:     */       {
/* 1877:1769 */         local_Request.isRMI();
/* 1878:     */         
/* 1879:     */ 
/* 1880:1772 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1881:     */       }
/* 1882:     */       catch (SystemException localSystemException)
/* 1883:     */       {
/* 1884:1776 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1885:     */       }
/* 1886:     */       finally
/* 1887:     */       {
/* 1888:1780 */         if (local_Request != null) {
/* 1889:1782 */           local_Request.close();
/* 1890:     */         }
/* 1891:1784 */         if (bool1) {
/* 1892:1785 */           localLocalStack.pop(localLocalFrame);
/* 1893:     */         }
/* 1894:1786 */         localLocalStack.setArgsOnLocal(bool2);
/* 1895:     */       }
/* 1896:     */     }
/* 1897:     */   }
/* 1898:     */   
/* 1899:     */   public TypeIntraSyncRsV1 getPendingIntras(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
/* 1900:     */     throws java.rmi.RemoteException
/* 1901:     */   {
/* 1902:1797 */     for (int i = 1;; i++)
/* 1903:     */     {
/* 1904:1799 */       _Request local_Request = null;
/* 1905:1800 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1906:1801 */       boolean bool1 = false;
/* 1907:1802 */       LocalFrame localLocalFrame = null;
/* 1908:1803 */       boolean bool2 = false;
/* 1909:     */       try
/* 1910:     */       {
/* 1911:1806 */         local_Request = __request("getPendingIntras");
/* 1912:1807 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 1913:1808 */         bool2 = localLocalStack.isArgsOnLocal();
/* 1914:1809 */         localLocalStack.setArgsOnLocal(bool1);
/* 1915:1811 */         if (bool1)
/* 1916:     */         {
/* 1917:1813 */           localLocalFrame = new LocalFrame(3);
/* 1918:1814 */           localLocalStack.push(localLocalFrame);
/* 1919:     */         }
/* 1920:1816 */         if (!bool1)
/* 1921:     */         {
/* 1922:1818 */           localObject4 = local_Request.getOutputStream();
/* 1923:1819 */           if (local_Request.isRMI()) {
/* 1924:1819 */             local_Request.write_value(paramTypeIntraSyncRqV1, TypeIntraSyncRqV1.class);
/* 1925:     */           } else {
/* 1926:1819 */             TypeIntraSyncRqV1Helper.write((OutputStream)localObject4, paramTypeIntraSyncRqV1);
/* 1927:     */           }
/* 1928:1820 */           if (local_Request.isRMI()) {
/* 1929:1820 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 1930:     */           } else {
/* 1931:1820 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 1932:     */           }
/* 1933:1821 */           ((OutputStream)localObject4).write_long(paramInt);
/* 1934:     */         }
/* 1935:     */         else
/* 1936:     */         {
/* 1937:1825 */           localObject4 = local_Request.getOutputStream();
/* 1938:1826 */           localLocalFrame.add(paramTypeIntraSyncRqV1);
/* 1939:1827 */           localLocalFrame.add(paramTypeUserData);
/* 1940:1828 */           localLocalFrame.add(paramInt);
/* 1941:     */         }
/* 1942:1830 */         local_Request.invoke();
/* 1943:     */         Object localObject5;
/* 1944:     */         Object localObject1;
/* 1945:1831 */         if (bool1)
/* 1946:     */         {
/* 1947:1833 */           localObject4 = null;
/* 1948:1834 */           localObject5 = localLocalFrame.getResult();
/* 1949:1835 */           if (localObject5 != null) {
/* 1950:1837 */             localObject4 = (TypeIntraSyncRsV1)ObjectVal.clone(localObject5);
/* 1951:     */           }
/* 1952:1839 */           return localObject4;
/* 1953:     */         }
/* 1954:1841 */         Object localObject4 = local_Request.getInputStream();
/* 1955:1843 */         if (local_Request.isRMI()) {
/* 1956:1843 */           localObject5 = (TypeIntraSyncRsV1)local_Request.read_value(TypeIntraSyncRsV1.class);
/* 1957:     */         } else {
/* 1958:1843 */           localObject5 = TypeIntraSyncRsV1Helper.read((InputStream)localObject4);
/* 1959:     */         }
/* 1960:1844 */         return localObject5;
/* 1961:     */       }
/* 1962:     */       catch (TRANSIENT localTRANSIENT)
/* 1963:     */       {
/* 1964:1848 */         if (i == 10) {
/* 1965:1850 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 1966:     */         }
/* 1967:     */       }
/* 1968:     */       catch (UserException localUserException)
/* 1969:     */       {
/* 1970:1855 */         local_Request.isRMI();
/* 1971:     */         
/* 1972:     */ 
/* 1973:1858 */         throw new java.rmi.RemoteException(localUserException.type);
/* 1974:     */       }
/* 1975:     */       catch (SystemException localSystemException)
/* 1976:     */       {
/* 1977:1862 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 1978:     */       }
/* 1979:     */       finally
/* 1980:     */       {
/* 1981:1866 */         if (local_Request != null) {
/* 1982:1868 */           local_Request.close();
/* 1983:     */         }
/* 1984:1870 */         if (bool1) {
/* 1985:1871 */           localLocalStack.pop(localLocalFrame);
/* 1986:     */         }
/* 1987:1872 */         localLocalStack.setArgsOnLocal(bool2);
/* 1988:     */       }
/* 1989:     */     }
/* 1990:     */   }
/* 1991:     */   
/* 1992:     */   public String getPmtStatus(String paramString)
/* 1993:     */     throws java.rmi.RemoteException
/* 1994:     */   {
/* 1995:1881 */     for (int i = 1;; i++)
/* 1996:     */     {
/* 1997:1883 */       _Request local_Request = null;
/* 1998:1884 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 1999:1885 */       boolean bool1 = false;
/* 2000:1886 */       LocalFrame localLocalFrame = null;
/* 2001:1887 */       boolean bool2 = false;
/* 2002:     */       try
/* 2003:     */       {
/* 2004:1890 */         local_Request = __request("getPmtStatus");
/* 2005:1891 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2006:1892 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2007:1893 */         localLocalStack.setArgsOnLocal(bool1);
/* 2008:1895 */         if (bool1)
/* 2009:     */         {
/* 2010:1897 */           localLocalFrame = new LocalFrame(1);
/* 2011:1898 */           localLocalStack.push(localLocalFrame);
/* 2012:     */         }
/* 2013:1900 */         if (!bool1)
/* 2014:     */         {
/* 2015:1902 */           localObject4 = local_Request.getOutputStream();
/* 2016:1903 */           local_Request.write_string(paramString);
/* 2017:     */         }
/* 2018:     */         else
/* 2019:     */         {
/* 2020:1907 */           localObject4 = local_Request.getOutputStream();
/* 2021:1908 */           localLocalFrame.add(paramString);
/* 2022:     */         }
/* 2023:1910 */         local_Request.invoke();
/* 2024:     */         Object localObject1;
/* 2025:1911 */         if (bool1)
/* 2026:     */         {
/* 2027:1913 */           localObject4 = null;
/* 2028:1914 */           localObject4 = (String)localLocalFrame.getResult();
/* 2029:1915 */           return localObject4;
/* 2030:     */         }
/* 2031:1917 */         Object localObject4 = local_Request.getInputStream();
/* 2032:     */         
/* 2033:1919 */         String str = local_Request.read_string();
/* 2034:1920 */         return str;
/* 2035:     */       }
/* 2036:     */       catch (TRANSIENT localTRANSIENT)
/* 2037:     */       {
/* 2038:1924 */         if (i == 10) {
/* 2039:1926 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2040:     */         }
/* 2041:     */       }
/* 2042:     */       catch (UserException localUserException)
/* 2043:     */       {
/* 2044:1931 */         local_Request.isRMI();
/* 2045:     */         
/* 2046:     */ 
/* 2047:1934 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2048:     */       }
/* 2049:     */       catch (SystemException localSystemException)
/* 2050:     */       {
/* 2051:1938 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2052:     */       }
/* 2053:     */       finally
/* 2054:     */       {
/* 2055:1942 */         if (local_Request != null) {
/* 2056:1944 */           local_Request.close();
/* 2057:     */         }
/* 2058:1946 */         if (bool1) {
/* 2059:1947 */           localLocalStack.pop(localLocalFrame);
/* 2060:     */         }
/* 2061:1948 */         localLocalStack.setArgsOnLocal(bool2);
/* 2062:     */       }
/* 2063:     */     }
/* 2064:     */   }
/* 2065:     */   
/* 2066:     */   public boolean checkPayeeEditMask(String paramString1, String paramString2)
/* 2067:     */     throws java.rmi.RemoteException
/* 2068:     */   {
/* 2069:1958 */     for (int i = 1;; i++)
/* 2070:     */     {
/* 2071:1960 */       _Request local_Request = null;
/* 2072:1961 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2073:1962 */       boolean bool1 = false;
/* 2074:1963 */       LocalFrame localLocalFrame = null;
/* 2075:1964 */       boolean bool2 = false;
/* 2076:     */       try
/* 2077:     */       {
/* 2078:1967 */         local_Request = __request("checkPayeeEditMask");
/* 2079:1968 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2080:1969 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2081:1970 */         localLocalStack.setArgsOnLocal(bool1);
/* 2082:1972 */         if (bool1)
/* 2083:     */         {
/* 2084:1974 */           localLocalFrame = new LocalFrame(2);
/* 2085:1975 */           localLocalStack.push(localLocalFrame);
/* 2086:     */         }
/* 2087:     */         OutputStream localOutputStream;
/* 2088:1977 */         if (!bool1)
/* 2089:     */         {
/* 2090:1979 */           localOutputStream = local_Request.getOutputStream();
/* 2091:1980 */           local_Request.write_string(paramString1);
/* 2092:1981 */           local_Request.write_string(paramString2);
/* 2093:     */         }
/* 2094:     */         else
/* 2095:     */         {
/* 2096:1985 */           localOutputStream = local_Request.getOutputStream();
/* 2097:1986 */           localLocalFrame.add(paramString1);
/* 2098:1987 */           localLocalFrame.add(paramString2);
/* 2099:     */         }
/* 2100:1989 */         local_Request.invoke();
/* 2101:     */         boolean bool3;
/* 2102:1990 */         if (bool1)
/* 2103:     */         {
/* 2104:1993 */           boolean bool4 = ((Boolean)localLocalFrame.getResult()).booleanValue();
/* 2105:1994 */           return bool4;
/* 2106:     */         }
/* 2107:1996 */         InputStream localInputStream = local_Request.getInputStream();
/* 2108:     */         
/* 2109:1998 */         boolean bool5 = localInputStream.read_boolean();
/* 2110:1999 */         return bool5;
/* 2111:     */       }
/* 2112:     */       catch (TRANSIENT localTRANSIENT)
/* 2113:     */       {
/* 2114:2003 */         if (i == 10) {
/* 2115:2005 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2116:     */         }
/* 2117:     */       }
/* 2118:     */       catch (UserException localUserException)
/* 2119:     */       {
/* 2120:2010 */         local_Request.isRMI();
/* 2121:     */         
/* 2122:     */ 
/* 2123:2013 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2124:     */       }
/* 2125:     */       catch (SystemException localSystemException)
/* 2126:     */       {
/* 2127:2017 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2128:     */       }
/* 2129:     */       finally
/* 2130:     */       {
/* 2131:2021 */         if (local_Request != null) {
/* 2132:2023 */           local_Request.close();
/* 2133:     */         }
/* 2134:2025 */         if (bool1) {
/* 2135:2026 */           localLocalStack.pop(localLocalFrame);
/* 2136:     */         }
/* 2137:2027 */         localLocalStack.setArgsOnLocal(bool2);
/* 2138:     */       }
/* 2139:     */     }
/* 2140:     */   }
/* 2141:     */   
/* 2142:     */   public void processIntraTrnRslt(IntraTrnRslt[] paramArrayOfIntraTrnRslt)
/* 2143:     */     throws java.rmi.RemoteException
/* 2144:     */   {
/* 2145:2036 */     for (int i = 1;; i++)
/* 2146:     */     {
/* 2147:2038 */       _Request local_Request = null;
/* 2148:2039 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2149:2040 */       boolean bool1 = false;
/* 2150:2041 */       LocalFrame localLocalFrame = null;
/* 2151:2042 */       boolean bool2 = false;
/* 2152:     */       try
/* 2153:     */       {
/* 2154:2045 */         local_Request = __request("processIntraTrnRslt");
/* 2155:2046 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2156:2047 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2157:2048 */         localLocalStack.setArgsOnLocal(bool1);
/* 2158:2050 */         if (bool1)
/* 2159:     */         {
/* 2160:2052 */           localLocalFrame = new LocalFrame(1);
/* 2161:2053 */           localLocalStack.push(localLocalFrame);
/* 2162:     */         }
/* 2163:     */         OutputStream localOutputStream;
/* 2164:2055 */         if (!bool1)
/* 2165:     */         {
/* 2166:2057 */           localOutputStream = local_Request.getOutputStream();
/* 2167:2058 */           if (local_Request.isRMI()) {
/* 2168:2058 */             local_Request.write_value(paramArrayOfIntraTrnRslt, new IntraTrnRslt[0].getClass());
/* 2169:     */           } else {
/* 2170:2058 */             IntraTrnRsltSeqHelper.write(localOutputStream, paramArrayOfIntraTrnRslt);
/* 2171:     */           }
/* 2172:     */         }
/* 2173:     */         else
/* 2174:     */         {
/* 2175:2062 */           localOutputStream = local_Request.getOutputStream();
/* 2176:2063 */           localLocalFrame.add(paramArrayOfIntraTrnRslt);
/* 2177:     */         }
/* 2178:2065 */         local_Request.invoke();
/* 2179:2066 */         return;
/* 2180:     */       }
/* 2181:     */       catch (TRANSIENT localTRANSIENT)
/* 2182:     */       {
/* 2183:2070 */         if (i == 10) {
/* 2184:2072 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2185:     */         }
/* 2186:     */       }
/* 2187:     */       catch (UserException localUserException)
/* 2188:     */       {
/* 2189:2077 */         local_Request.isRMI();
/* 2190:     */         
/* 2191:     */ 
/* 2192:2080 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2193:     */       }
/* 2194:     */       catch (SystemException localSystemException)
/* 2195:     */       {
/* 2196:2084 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2197:     */       }
/* 2198:     */       finally
/* 2199:     */       {
/* 2200:2088 */         if (local_Request != null) {
/* 2201:2090 */           local_Request.close();
/* 2202:     */         }
/* 2203:2092 */         if (bool1) {
/* 2204:2093 */           localLocalStack.pop(localLocalFrame);
/* 2205:     */         }
/* 2206:2094 */         localLocalStack.setArgsOnLocal(bool2);
/* 2207:     */       }
/* 2208:     */     }
/* 2209:     */   }
/* 2210:     */   
/* 2211:     */   public void processPmtTrnRslt(PmtTrnRslt[] paramArrayOfPmtTrnRslt)
/* 2212:     */     throws java.rmi.RemoteException
/* 2213:     */   {
/* 2214:2103 */     for (int i = 1;; i++)
/* 2215:     */     {
/* 2216:2105 */       _Request local_Request = null;
/* 2217:2106 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2218:2107 */       boolean bool1 = false;
/* 2219:2108 */       LocalFrame localLocalFrame = null;
/* 2220:2109 */       boolean bool2 = false;
/* 2221:     */       try
/* 2222:     */       {
/* 2223:2112 */         local_Request = __request("processPmtTrnRslt");
/* 2224:2113 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2225:2114 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2226:2115 */         localLocalStack.setArgsOnLocal(bool1);
/* 2227:2117 */         if (bool1)
/* 2228:     */         {
/* 2229:2119 */           localLocalFrame = new LocalFrame(1);
/* 2230:2120 */           localLocalStack.push(localLocalFrame);
/* 2231:     */         }
/* 2232:     */         OutputStream localOutputStream;
/* 2233:2122 */         if (!bool1)
/* 2234:     */         {
/* 2235:2124 */           localOutputStream = local_Request.getOutputStream();
/* 2236:2125 */           if (local_Request.isRMI()) {
/* 2237:2125 */             local_Request.write_value(paramArrayOfPmtTrnRslt, new PmtTrnRslt[0].getClass());
/* 2238:     */           } else {
/* 2239:2125 */             PmtTrnRsltSeqHelper.write(localOutputStream, paramArrayOfPmtTrnRslt);
/* 2240:     */           }
/* 2241:     */         }
/* 2242:     */         else
/* 2243:     */         {
/* 2244:2129 */           localOutputStream = local_Request.getOutputStream();
/* 2245:2130 */           localLocalFrame.add(paramArrayOfPmtTrnRslt);
/* 2246:     */         }
/* 2247:2132 */         local_Request.invoke();
/* 2248:2133 */         return;
/* 2249:     */       }
/* 2250:     */       catch (TRANSIENT localTRANSIENT)
/* 2251:     */       {
/* 2252:2137 */         if (i == 10) {
/* 2253:2139 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2254:     */         }
/* 2255:     */       }
/* 2256:     */       catch (UserException localUserException)
/* 2257:     */       {
/* 2258:2144 */         local_Request.isRMI();
/* 2259:     */         
/* 2260:     */ 
/* 2261:2147 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2262:     */       }
/* 2263:     */       catch (SystemException localSystemException)
/* 2264:     */       {
/* 2265:2151 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2266:     */       }
/* 2267:     */       finally
/* 2268:     */       {
/* 2269:2155 */         if (local_Request != null) {
/* 2270:2157 */           local_Request.close();
/* 2271:     */         }
/* 2272:2159 */         if (bool1) {
/* 2273:2160 */           localLocalStack.pop(localLocalFrame);
/* 2274:     */         }
/* 2275:2161 */         localLocalStack.setArgsOnLocal(bool2);
/* 2276:     */       }
/* 2277:     */     }
/* 2278:     */   }
/* 2279:     */   
/* 2280:     */   public void processCustPayeeRslt(CustPayeeRslt[] paramArrayOfCustPayeeRslt)
/* 2281:     */     throws java.rmi.RemoteException
/* 2282:     */   {
/* 2283:2170 */     for (int i = 1;; i++)
/* 2284:     */     {
/* 2285:2172 */       _Request local_Request = null;
/* 2286:2173 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2287:2174 */       boolean bool1 = false;
/* 2288:2175 */       LocalFrame localLocalFrame = null;
/* 2289:2176 */       boolean bool2 = false;
/* 2290:     */       try
/* 2291:     */       {
/* 2292:2179 */         local_Request = __request("processCustPayeeRslt");
/* 2293:2180 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2294:2181 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2295:2182 */         localLocalStack.setArgsOnLocal(bool1);
/* 2296:2184 */         if (bool1)
/* 2297:     */         {
/* 2298:2186 */           localLocalFrame = new LocalFrame(1);
/* 2299:2187 */           localLocalStack.push(localLocalFrame);
/* 2300:     */         }
/* 2301:     */         OutputStream localOutputStream;
/* 2302:2189 */         if (!bool1)
/* 2303:     */         {
/* 2304:2191 */           localOutputStream = local_Request.getOutputStream();
/* 2305:2192 */           if (local_Request.isRMI()) {
/* 2306:2192 */             local_Request.write_value(paramArrayOfCustPayeeRslt, new CustPayeeRslt[0].getClass());
/* 2307:     */           } else {
/* 2308:2192 */             CustPayeeRsltSeqHelper.write(localOutputStream, paramArrayOfCustPayeeRslt);
/* 2309:     */           }
/* 2310:     */         }
/* 2311:     */         else
/* 2312:     */         {
/* 2313:2196 */           localOutputStream = local_Request.getOutputStream();
/* 2314:2197 */           localLocalFrame.add(paramArrayOfCustPayeeRslt);
/* 2315:     */         }
/* 2316:2199 */         local_Request.invoke();
/* 2317:2200 */         return;
/* 2318:     */       }
/* 2319:     */       catch (TRANSIENT localTRANSIENT)
/* 2320:     */       {
/* 2321:2204 */         if (i == 10) {
/* 2322:2206 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2323:     */         }
/* 2324:     */       }
/* 2325:     */       catch (UserException localUserException)
/* 2326:     */       {
/* 2327:2211 */         local_Request.isRMI();
/* 2328:     */         
/* 2329:     */ 
/* 2330:2214 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2331:     */       }
/* 2332:     */       catch (SystemException localSystemException)
/* 2333:     */       {
/* 2334:2218 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2335:     */       }
/* 2336:     */       finally
/* 2337:     */       {
/* 2338:2222 */         if (local_Request != null) {
/* 2339:2224 */           local_Request.close();
/* 2340:     */         }
/* 2341:2226 */         if (bool1) {
/* 2342:2227 */           localLocalStack.pop(localLocalFrame);
/* 2343:     */         }
/* 2344:2228 */         localLocalStack.setArgsOnLocal(bool2);
/* 2345:     */       }
/* 2346:     */     }
/* 2347:     */   }
/* 2348:     */   
/* 2349:     */   public void processFundAllocRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
/* 2350:     */     throws java.rmi.RemoteException
/* 2351:     */   {
/* 2352:2237 */     for (int i = 1;; i++)
/* 2353:     */     {
/* 2354:2239 */       _Request local_Request = null;
/* 2355:2240 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2356:2241 */       boolean bool1 = false;
/* 2357:2242 */       LocalFrame localLocalFrame = null;
/* 2358:2243 */       boolean bool2 = false;
/* 2359:     */       try
/* 2360:     */       {
/* 2361:2246 */         local_Request = __request("processFundAllocRslt");
/* 2362:2247 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2363:2248 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2364:2249 */         localLocalStack.setArgsOnLocal(bool1);
/* 2365:2251 */         if (bool1)
/* 2366:     */         {
/* 2367:2253 */           localLocalFrame = new LocalFrame(1);
/* 2368:2254 */           localLocalStack.push(localLocalFrame);
/* 2369:     */         }
/* 2370:     */         OutputStream localOutputStream;
/* 2371:2256 */         if (!bool1)
/* 2372:     */         {
/* 2373:2258 */           localOutputStream = local_Request.getOutputStream();
/* 2374:2259 */           if (local_Request.isRMI()) {
/* 2375:2259 */             local_Request.write_value(paramArrayOfFundsAllocRslt, new FundsAllocRslt[0].getClass());
/* 2376:     */           } else {
/* 2377:2259 */             FundsAllocRsltSeqHelper.write(localOutputStream, paramArrayOfFundsAllocRslt);
/* 2378:     */           }
/* 2379:     */         }
/* 2380:     */         else
/* 2381:     */         {
/* 2382:2263 */           localOutputStream = local_Request.getOutputStream();
/* 2383:2264 */           localLocalFrame.add(paramArrayOfFundsAllocRslt);
/* 2384:     */         }
/* 2385:2266 */         local_Request.invoke();
/* 2386:2267 */         return;
/* 2387:     */       }
/* 2388:     */       catch (TRANSIENT localTRANSIENT)
/* 2389:     */       {
/* 2390:2271 */         if (i == 10) {
/* 2391:2273 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2392:     */         }
/* 2393:     */       }
/* 2394:     */       catch (UserException localUserException)
/* 2395:     */       {
/* 2396:2278 */         local_Request.isRMI();
/* 2397:     */         
/* 2398:     */ 
/* 2399:2281 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2400:     */       }
/* 2401:     */       catch (SystemException localSystemException)
/* 2402:     */       {
/* 2403:2285 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2404:     */       }
/* 2405:     */       finally
/* 2406:     */       {
/* 2407:2289 */         if (local_Request != null) {
/* 2408:2291 */           local_Request.close();
/* 2409:     */         }
/* 2410:2293 */         if (bool1) {
/* 2411:2294 */           localLocalStack.pop(localLocalFrame);
/* 2412:     */         }
/* 2413:2295 */         localLocalStack.setArgsOnLocal(bool2);
/* 2414:     */       }
/* 2415:     */     }
/* 2416:     */   }
/* 2417:     */   
/* 2418:     */   public void processFundRevertRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
/* 2419:     */     throws java.rmi.RemoteException
/* 2420:     */   {
/* 2421:2304 */     for (int i = 1;; i++)
/* 2422:     */     {
/* 2423:2306 */       _Request local_Request = null;
/* 2424:2307 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2425:2308 */       boolean bool1 = false;
/* 2426:2309 */       LocalFrame localLocalFrame = null;
/* 2427:2310 */       boolean bool2 = false;
/* 2428:     */       try
/* 2429:     */       {
/* 2430:2313 */         local_Request = __request("processFundRevertRslt");
/* 2431:2314 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2432:2315 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2433:2316 */         localLocalStack.setArgsOnLocal(bool1);
/* 2434:2318 */         if (bool1)
/* 2435:     */         {
/* 2436:2320 */           localLocalFrame = new LocalFrame(1);
/* 2437:2321 */           localLocalStack.push(localLocalFrame);
/* 2438:     */         }
/* 2439:     */         OutputStream localOutputStream;
/* 2440:2323 */         if (!bool1)
/* 2441:     */         {
/* 2442:2325 */           localOutputStream = local_Request.getOutputStream();
/* 2443:2326 */           if (local_Request.isRMI()) {
/* 2444:2326 */             local_Request.write_value(paramArrayOfFundsAllocRslt, new FundsAllocRslt[0].getClass());
/* 2445:     */           } else {
/* 2446:2326 */             FundsAllocRsltSeqHelper.write(localOutputStream, paramArrayOfFundsAllocRslt);
/* 2447:     */           }
/* 2448:     */         }
/* 2449:     */         else
/* 2450:     */         {
/* 2451:2330 */           localOutputStream = local_Request.getOutputStream();
/* 2452:2331 */           localLocalFrame.add(paramArrayOfFundsAllocRslt);
/* 2453:     */         }
/* 2454:2333 */         local_Request.invoke();
/* 2455:2334 */         return;
/* 2456:     */       }
/* 2457:     */       catch (TRANSIENT localTRANSIENT)
/* 2458:     */       {
/* 2459:2338 */         if (i == 10) {
/* 2460:2340 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2461:     */         }
/* 2462:     */       }
/* 2463:     */       catch (UserException localUserException)
/* 2464:     */       {
/* 2465:2345 */         local_Request.isRMI();
/* 2466:     */         
/* 2467:     */ 
/* 2468:2348 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2469:     */       }
/* 2470:     */       catch (SystemException localSystemException)
/* 2471:     */       {
/* 2472:2352 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2473:     */       }
/* 2474:     */       finally
/* 2475:     */       {
/* 2476:2356 */         if (local_Request != null) {
/* 2477:2358 */           local_Request.close();
/* 2478:     */         }
/* 2479:2360 */         if (bool1) {
/* 2480:2361 */           localLocalStack.pop(localLocalFrame);
/* 2481:     */         }
/* 2482:2362 */         localLocalStack.setArgsOnLocal(bool2);
/* 2483:     */       }
/* 2484:     */     }
/* 2485:     */   }
/* 2486:     */   
/* 2487:     */   public void processPayeeRslt(PayeeRslt[] paramArrayOfPayeeRslt)
/* 2488:     */     throws java.rmi.RemoteException
/* 2489:     */   {
/* 2490:2371 */     for (int i = 1;; i++)
/* 2491:     */     {
/* 2492:2373 */       _Request local_Request = null;
/* 2493:2374 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2494:2375 */       boolean bool1 = false;
/* 2495:2376 */       LocalFrame localLocalFrame = null;
/* 2496:2377 */       boolean bool2 = false;
/* 2497:     */       try
/* 2498:     */       {
/* 2499:2380 */         local_Request = __request("processPayeeRslt");
/* 2500:2381 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2501:2382 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2502:2383 */         localLocalStack.setArgsOnLocal(bool1);
/* 2503:2385 */         if (bool1)
/* 2504:     */         {
/* 2505:2387 */           localLocalFrame = new LocalFrame(1);
/* 2506:2388 */           localLocalStack.push(localLocalFrame);
/* 2507:     */         }
/* 2508:     */         OutputStream localOutputStream;
/* 2509:2390 */         if (!bool1)
/* 2510:     */         {
/* 2511:2392 */           localOutputStream = local_Request.getOutputStream();
/* 2512:2393 */           if (local_Request.isRMI()) {
/* 2513:2393 */             local_Request.write_value(paramArrayOfPayeeRslt, new PayeeRslt[0].getClass());
/* 2514:     */           } else {
/* 2515:2393 */             PayeeRsltSeqHelper.write(localOutputStream, paramArrayOfPayeeRslt);
/* 2516:     */           }
/* 2517:     */         }
/* 2518:     */         else
/* 2519:     */         {
/* 2520:2397 */           localOutputStream = local_Request.getOutputStream();
/* 2521:2398 */           localLocalFrame.add(paramArrayOfPayeeRslt);
/* 2522:     */         }
/* 2523:2400 */         local_Request.invoke();
/* 2524:2401 */         return;
/* 2525:     */       }
/* 2526:     */       catch (TRANSIENT localTRANSIENT)
/* 2527:     */       {
/* 2528:2405 */         if (i == 10) {
/* 2529:2407 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2530:     */         }
/* 2531:     */       }
/* 2532:     */       catch (UserException localUserException)
/* 2533:     */       {
/* 2534:2412 */         local_Request.isRMI();
/* 2535:     */         
/* 2536:     */ 
/* 2537:2415 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2538:     */       }
/* 2539:     */       catch (SystemException localSystemException)
/* 2540:     */       {
/* 2541:2419 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2542:     */       }
/* 2543:     */       finally
/* 2544:     */       {
/* 2545:2423 */         if (local_Request != null) {
/* 2546:2425 */           local_Request.close();
/* 2547:     */         }
/* 2548:2427 */         if (bool1) {
/* 2549:2428 */           localLocalStack.pop(localLocalFrame);
/* 2550:     */         }
/* 2551:2429 */         localLocalStack.setArgsOnLocal(bool2);
/* 2552:     */       }
/* 2553:     */     }
/* 2554:     */   }
/* 2555:     */   
/* 2556:     */   public String addPayeeFromBackend(PayeeInfo paramPayeeInfo)
/* 2557:     */     throws java.rmi.RemoteException
/* 2558:     */   {
/* 2559:2438 */     for (int i = 1;; i++)
/* 2560:     */     {
/* 2561:2440 */       _Request local_Request = null;
/* 2562:2441 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2563:2442 */       boolean bool1 = false;
/* 2564:2443 */       LocalFrame localLocalFrame = null;
/* 2565:2444 */       boolean bool2 = false;
/* 2566:     */       try
/* 2567:     */       {
/* 2568:2447 */         local_Request = __request("addPayeeFromBackend");
/* 2569:2448 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2570:2449 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2571:2450 */         localLocalStack.setArgsOnLocal(bool1);
/* 2572:2452 */         if (bool1)
/* 2573:     */         {
/* 2574:2454 */           localLocalFrame = new LocalFrame(1);
/* 2575:2455 */           localLocalStack.push(localLocalFrame);
/* 2576:     */         }
/* 2577:2457 */         if (!bool1)
/* 2578:     */         {
/* 2579:2459 */           localObject4 = local_Request.getOutputStream();
/* 2580:2460 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 2581:     */         }
/* 2582:     */         else
/* 2583:     */         {
/* 2584:2464 */           localObject4 = local_Request.getOutputStream();
/* 2585:2465 */           localLocalFrame.add(paramPayeeInfo);
/* 2586:     */         }
/* 2587:2467 */         local_Request.invoke();
/* 2588:     */         Object localObject1;
/* 2589:2468 */         if (bool1)
/* 2590:     */         {
/* 2591:2470 */           localObject4 = null;
/* 2592:2471 */           localObject4 = (String)localLocalFrame.getResult();
/* 2593:2472 */           return localObject4;
/* 2594:     */         }
/* 2595:2474 */         Object localObject4 = local_Request.getInputStream();
/* 2596:     */         
/* 2597:2476 */         String str = local_Request.read_string();
/* 2598:2477 */         return str;
/* 2599:     */       }
/* 2600:     */       catch (TRANSIENT localTRANSIENT)
/* 2601:     */       {
/* 2602:2481 */         if (i == 10) {
/* 2603:2483 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2604:     */         }
/* 2605:     */       }
/* 2606:     */       catch (UserException localUserException)
/* 2607:     */       {
/* 2608:2488 */         local_Request.isRMI();
/* 2609:     */         
/* 2610:     */ 
/* 2611:2491 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2612:     */       }
/* 2613:     */       catch (SystemException localSystemException)
/* 2614:     */       {
/* 2615:2495 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2616:     */       }
/* 2617:     */       finally
/* 2618:     */       {
/* 2619:2499 */         if (local_Request != null) {
/* 2620:2501 */           local_Request.close();
/* 2621:     */         }
/* 2622:2503 */         if (bool1) {
/* 2623:2504 */           localLocalStack.pop(localLocalFrame);
/* 2624:     */         }
/* 2625:2505 */         localLocalStack.setArgsOnLocal(bool2);
/* 2626:     */       }
/* 2627:     */     }
/* 2628:     */   }
/* 2629:     */   
/* 2630:     */   public PayeeInfo[] updatePayeeFromBackend(PayeeInfo paramPayeeInfo)
/* 2631:     */     throws java.rmi.RemoteException
/* 2632:     */   {
/* 2633:2514 */     for (int i = 1;; i++)
/* 2634:     */     {
/* 2635:2516 */       _Request local_Request = null;
/* 2636:2517 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2637:2518 */       boolean bool1 = false;
/* 2638:2519 */       LocalFrame localLocalFrame = null;
/* 2639:2520 */       boolean bool2 = false;
/* 2640:     */       try
/* 2641:     */       {
/* 2642:2523 */         local_Request = __request("updatePayeeFromBackend");
/* 2643:2524 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2644:2525 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2645:2526 */         localLocalStack.setArgsOnLocal(bool1);
/* 2646:2528 */         if (bool1)
/* 2647:     */         {
/* 2648:2530 */           localLocalFrame = new LocalFrame(1);
/* 2649:2531 */           localLocalStack.push(localLocalFrame);
/* 2650:     */         }
/* 2651:2533 */         if (!bool1)
/* 2652:     */         {
/* 2653:2535 */           localObject4 = local_Request.getOutputStream();
/* 2654:2536 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 2655:     */         }
/* 2656:     */         else
/* 2657:     */         {
/* 2658:2540 */           localObject4 = local_Request.getOutputStream();
/* 2659:2541 */           localLocalFrame.add(paramPayeeInfo);
/* 2660:     */         }
/* 2661:2543 */         local_Request.invoke();
/* 2662:     */         Object localObject5;
/* 2663:     */         Object localObject1;
/* 2664:2544 */         if (bool1)
/* 2665:     */         {
/* 2666:2546 */           localObject4 = null;
/* 2667:2547 */           localObject5 = localLocalFrame.getResult();
/* 2668:2548 */           if (localObject5 != null) {
/* 2669:2550 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 2670:     */           }
/* 2671:2552 */           return localObject4;
/* 2672:     */         }
/* 2673:2554 */         Object localObject4 = local_Request.getInputStream();
/* 2674:2556 */         if (local_Request.isRMI()) {
/* 2675:2556 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 2676:     */         } else {
/* 2677:2556 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 2678:     */         }
/* 2679:2557 */         return localObject5;
/* 2680:     */       }
/* 2681:     */       catch (TRANSIENT localTRANSIENT)
/* 2682:     */       {
/* 2683:2561 */         if (i == 10) {
/* 2684:2563 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2685:     */         }
/* 2686:     */       }
/* 2687:     */       catch (UserException localUserException)
/* 2688:     */       {
/* 2689:2568 */         local_Request.isRMI();
/* 2690:     */         
/* 2691:     */ 
/* 2692:2571 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2693:     */       }
/* 2694:     */       catch (SystemException localSystemException)
/* 2695:     */       {
/* 2696:2575 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2697:     */       }
/* 2698:     */       finally
/* 2699:     */       {
/* 2700:2579 */         if (local_Request != null) {
/* 2701:2581 */           local_Request.close();
/* 2702:     */         }
/* 2703:2583 */         if (bool1) {
/* 2704:2584 */           localLocalStack.pop(localLocalFrame);
/* 2705:     */         }
/* 2706:2585 */         localLocalStack.setArgsOnLocal(bool2);
/* 2707:     */       }
/* 2708:     */     }
/* 2709:     */   }
/* 2710:     */   
/* 2711:     */   public void addPayeeRouteInfo(PayeeRouteInfo paramPayeeRouteInfo)
/* 2712:     */     throws java.rmi.RemoteException
/* 2713:     */   {
/* 2714:2594 */     for (int i = 1;; i++)
/* 2715:     */     {
/* 2716:2596 */       _Request local_Request = null;
/* 2717:2597 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2718:2598 */       boolean bool1 = false;
/* 2719:2599 */       LocalFrame localLocalFrame = null;
/* 2720:2600 */       boolean bool2 = false;
/* 2721:     */       try
/* 2722:     */       {
/* 2723:2603 */         local_Request = __request("addPayeeRouteInfo");
/* 2724:2604 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2725:2605 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2726:2606 */         localLocalStack.setArgsOnLocal(bool1);
/* 2727:2608 */         if (bool1)
/* 2728:     */         {
/* 2729:2610 */           localLocalFrame = new LocalFrame(1);
/* 2730:2611 */           localLocalStack.push(localLocalFrame);
/* 2731:     */         }
/* 2732:     */         OutputStream localOutputStream;
/* 2733:2613 */         if (!bool1)
/* 2734:     */         {
/* 2735:2615 */           localOutputStream = local_Request.getOutputStream();
/* 2736:2616 */           local_Request.write_value(paramPayeeRouteInfo, PayeeRouteInfo.class);
/* 2737:     */         }
/* 2738:     */         else
/* 2739:     */         {
/* 2740:2620 */           localOutputStream = local_Request.getOutputStream();
/* 2741:2621 */           localLocalFrame.add(paramPayeeRouteInfo);
/* 2742:     */         }
/* 2743:2623 */         local_Request.invoke();
/* 2744:2624 */         return;
/* 2745:     */       }
/* 2746:     */       catch (TRANSIENT localTRANSIENT)
/* 2747:     */       {
/* 2748:2628 */         if (i == 10) {
/* 2749:2630 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2750:     */         }
/* 2751:     */       }
/* 2752:     */       catch (UserException localUserException)
/* 2753:     */       {
/* 2754:2635 */         local_Request.isRMI();
/* 2755:     */         
/* 2756:     */ 
/* 2757:2638 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2758:     */       }
/* 2759:     */       catch (SystemException localSystemException)
/* 2760:     */       {
/* 2761:2642 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2762:     */       }
/* 2763:     */       finally
/* 2764:     */       {
/* 2765:2646 */         if (local_Request != null) {
/* 2766:2648 */           local_Request.close();
/* 2767:     */         }
/* 2768:2650 */         if (bool1) {
/* 2769:2651 */           localLocalStack.pop(localLocalFrame);
/* 2770:     */         }
/* 2771:2652 */         localLocalStack.setArgsOnLocal(bool2);
/* 2772:     */       }
/* 2773:     */     }
/* 2774:     */   }
/* 2775:     */   
/* 2776:     */   public TypeIntraSyncRsV1 processIntraSyncRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData)
/* 2777:     */     throws java.rmi.RemoteException
/* 2778:     */   {
/* 2779:2662 */     for (int i = 1;; i++)
/* 2780:     */     {
/* 2781:2664 */       _Request local_Request = null;
/* 2782:2665 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2783:2666 */       boolean bool1 = false;
/* 2784:2667 */       LocalFrame localLocalFrame = null;
/* 2785:2668 */       boolean bool2 = false;
/* 2786:     */       try
/* 2787:     */       {
/* 2788:2671 */         local_Request = __request("processIntraSyncRqV1");
/* 2789:2672 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2790:2673 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2791:2674 */         localLocalStack.setArgsOnLocal(bool1);
/* 2792:2676 */         if (bool1)
/* 2793:     */         {
/* 2794:2678 */           localLocalFrame = new LocalFrame(2);
/* 2795:2679 */           localLocalStack.push(localLocalFrame);
/* 2796:     */         }
/* 2797:2681 */         if (!bool1)
/* 2798:     */         {
/* 2799:2683 */           localObject4 = local_Request.getOutputStream();
/* 2800:2684 */           if (local_Request.isRMI()) {
/* 2801:2684 */             local_Request.write_value(paramTypeIntraSyncRqV1, TypeIntraSyncRqV1.class);
/* 2802:     */           } else {
/* 2803:2684 */             TypeIntraSyncRqV1Helper.write((OutputStream)localObject4, paramTypeIntraSyncRqV1);
/* 2804:     */           }
/* 2805:2685 */           if (local_Request.isRMI()) {
/* 2806:2685 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 2807:     */           } else {
/* 2808:2685 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 2809:     */           }
/* 2810:     */         }
/* 2811:     */         else
/* 2812:     */         {
/* 2813:2689 */           localObject4 = local_Request.getOutputStream();
/* 2814:2690 */           localLocalFrame.add(paramTypeIntraSyncRqV1);
/* 2815:2691 */           localLocalFrame.add(paramTypeUserData);
/* 2816:     */         }
/* 2817:2693 */         local_Request.invoke();
/* 2818:     */         Object localObject5;
/* 2819:     */         Object localObject1;
/* 2820:2694 */         if (bool1)
/* 2821:     */         {
/* 2822:2696 */           localObject4 = null;
/* 2823:2697 */           localObject5 = localLocalFrame.getResult();
/* 2824:2698 */           if (localObject5 != null) {
/* 2825:2700 */             localObject4 = (TypeIntraSyncRsV1)ObjectVal.clone(localObject5);
/* 2826:     */           }
/* 2827:2702 */           return localObject4;
/* 2828:     */         }
/* 2829:2704 */         Object localObject4 = local_Request.getInputStream();
/* 2830:2706 */         if (local_Request.isRMI()) {
/* 2831:2706 */           localObject5 = (TypeIntraSyncRsV1)local_Request.read_value(TypeIntraSyncRsV1.class);
/* 2832:     */         } else {
/* 2833:2706 */           localObject5 = TypeIntraSyncRsV1Helper.read((InputStream)localObject4);
/* 2834:     */         }
/* 2835:2707 */         return localObject5;
/* 2836:     */       }
/* 2837:     */       catch (TRANSIENT localTRANSIENT)
/* 2838:     */       {
/* 2839:2711 */         if (i == 10) {
/* 2840:2713 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2841:     */         }
/* 2842:     */       }
/* 2843:     */       catch (UserException localUserException)
/* 2844:     */       {
/* 2845:2718 */         local_Request.isRMI();
/* 2846:     */         
/* 2847:     */ 
/* 2848:2721 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2849:     */       }
/* 2850:     */       catch (SystemException localSystemException)
/* 2851:     */       {
/* 2852:2725 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2853:     */       }
/* 2854:     */       finally
/* 2855:     */       {
/* 2856:2729 */         if (local_Request != null) {
/* 2857:2731 */           local_Request.close();
/* 2858:     */         }
/* 2859:2733 */         if (bool1) {
/* 2860:2734 */           localLocalStack.pop(localLocalFrame);
/* 2861:     */         }
/* 2862:2735 */         localLocalStack.setArgsOnLocal(bool2);
/* 2863:     */       }
/* 2864:     */     }
/* 2865:     */   }
/* 2866:     */   
/* 2867:     */   public TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData)
/* 2868:     */     throws java.rmi.RemoteException
/* 2869:     */   {
/* 2870:2745 */     for (int i = 1;; i++)
/* 2871:     */     {
/* 2872:2747 */       _Request local_Request = null;
/* 2873:2748 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2874:2749 */       boolean bool1 = false;
/* 2875:2750 */       LocalFrame localLocalFrame = null;
/* 2876:2751 */       boolean bool2 = false;
/* 2877:     */       try
/* 2878:     */       {
/* 2879:2754 */         local_Request = __request("processIntraTrnRqV1");
/* 2880:2755 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2881:2756 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2882:2757 */         localLocalStack.setArgsOnLocal(bool1);
/* 2883:2759 */         if (bool1)
/* 2884:     */         {
/* 2885:2761 */           localLocalFrame = new LocalFrame(2);
/* 2886:2762 */           localLocalStack.push(localLocalFrame);
/* 2887:     */         }
/* 2888:2764 */         if (!bool1)
/* 2889:     */         {
/* 2890:2766 */           localObject4 = local_Request.getOutputStream();
/* 2891:2767 */           if (local_Request.isRMI()) {
/* 2892:2767 */             local_Request.write_value(paramTypeIntraTrnRqV1, TypeIntraTrnRqV1.class);
/* 2893:     */           } else {
/* 2894:2767 */             TypeIntraTrnRqV1Helper.write((OutputStream)localObject4, paramTypeIntraTrnRqV1);
/* 2895:     */           }
/* 2896:2768 */           if (local_Request.isRMI()) {
/* 2897:2768 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 2898:     */           } else {
/* 2899:2768 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 2900:     */           }
/* 2901:     */         }
/* 2902:     */         else
/* 2903:     */         {
/* 2904:2772 */           localObject4 = local_Request.getOutputStream();
/* 2905:2773 */           localLocalFrame.add(paramTypeIntraTrnRqV1);
/* 2906:2774 */           localLocalFrame.add(paramTypeUserData);
/* 2907:     */         }
/* 2908:2776 */         local_Request.invoke();
/* 2909:     */         Object localObject5;
/* 2910:     */         Object localObject1;
/* 2911:2777 */         if (bool1)
/* 2912:     */         {
/* 2913:2779 */           localObject4 = null;
/* 2914:2780 */           localObject5 = localLocalFrame.getResult();
/* 2915:2781 */           if (localObject5 != null) {
/* 2916:2783 */             localObject4 = (TypeIntraTrnRsV1)ObjectVal.clone(localObject5);
/* 2917:     */           }
/* 2918:2785 */           return localObject4;
/* 2919:     */         }
/* 2920:2787 */         Object localObject4 = local_Request.getInputStream();
/* 2921:2789 */         if (local_Request.isRMI()) {
/* 2922:2789 */           localObject5 = (TypeIntraTrnRsV1)local_Request.read_value(TypeIntraTrnRsV1.class);
/* 2923:     */         } else {
/* 2924:2789 */           localObject5 = TypeIntraTrnRsV1Helper.read((InputStream)localObject4);
/* 2925:     */         }
/* 2926:2790 */         return localObject5;
/* 2927:     */       }
/* 2928:     */       catch (TRANSIENT localTRANSIENT)
/* 2929:     */       {
/* 2930:2794 */         if (i == 10) {
/* 2931:2796 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 2932:     */         }
/* 2933:     */       }
/* 2934:     */       catch (UserException localUserException)
/* 2935:     */       {
/* 2936:2801 */         local_Request.isRMI();
/* 2937:     */         
/* 2938:     */ 
/* 2939:2804 */         throw new java.rmi.RemoteException(localUserException.type);
/* 2940:     */       }
/* 2941:     */       catch (SystemException localSystemException)
/* 2942:     */       {
/* 2943:2808 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 2944:     */       }
/* 2945:     */       finally
/* 2946:     */       {
/* 2947:2812 */         if (local_Request != null) {
/* 2948:2814 */           local_Request.close();
/* 2949:     */         }
/* 2950:2816 */         if (bool1) {
/* 2951:2817 */           localLocalStack.pop(localLocalFrame);
/* 2952:     */         }
/* 2953:2818 */         localLocalStack.setArgsOnLocal(bool2);
/* 2954:     */       }
/* 2955:     */     }
/* 2956:     */   }
/* 2957:     */   
/* 2958:     */   public TypePayeeSyncRsV1 processPayeeSyncRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData)
/* 2959:     */     throws java.rmi.RemoteException
/* 2960:     */   {
/* 2961:2828 */     for (int i = 1;; i++)
/* 2962:     */     {
/* 2963:2830 */       _Request local_Request = null;
/* 2964:2831 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 2965:2832 */       boolean bool1 = false;
/* 2966:2833 */       LocalFrame localLocalFrame = null;
/* 2967:2834 */       boolean bool2 = false;
/* 2968:     */       try
/* 2969:     */       {
/* 2970:2837 */         local_Request = __request("processPayeeSyncRqV1");
/* 2971:2838 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 2972:2839 */         bool2 = localLocalStack.isArgsOnLocal();
/* 2973:2840 */         localLocalStack.setArgsOnLocal(bool1);
/* 2974:2842 */         if (bool1)
/* 2975:     */         {
/* 2976:2844 */           localLocalFrame = new LocalFrame(2);
/* 2977:2845 */           localLocalStack.push(localLocalFrame);
/* 2978:     */         }
/* 2979:2847 */         if (!bool1)
/* 2980:     */         {
/* 2981:2849 */           localObject4 = local_Request.getOutputStream();
/* 2982:2850 */           if (local_Request.isRMI()) {
/* 2983:2850 */             local_Request.write_value(paramTypePayeeSyncRqV1, TypePayeeSyncRqV1.class);
/* 2984:     */           } else {
/* 2985:2850 */             TypePayeeSyncRqV1Helper.write((OutputStream)localObject4, paramTypePayeeSyncRqV1);
/* 2986:     */           }
/* 2987:2851 */           if (local_Request.isRMI()) {
/* 2988:2851 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 2989:     */           } else {
/* 2990:2851 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 2991:     */           }
/* 2992:     */         }
/* 2993:     */         else
/* 2994:     */         {
/* 2995:2855 */           localObject4 = local_Request.getOutputStream();
/* 2996:2856 */           localLocalFrame.add(paramTypePayeeSyncRqV1);
/* 2997:2857 */           localLocalFrame.add(paramTypeUserData);
/* 2998:     */         }
/* 2999:2859 */         local_Request.invoke();
/* 3000:     */         Object localObject5;
/* 3001:     */         Object localObject1;
/* 3002:2860 */         if (bool1)
/* 3003:     */         {
/* 3004:2862 */           localObject4 = null;
/* 3005:2863 */           localObject5 = localLocalFrame.getResult();
/* 3006:2864 */           if (localObject5 != null) {
/* 3007:2866 */             localObject4 = (TypePayeeSyncRsV1)ObjectVal.clone(localObject5);
/* 3008:     */           }
/* 3009:2868 */           return localObject4;
/* 3010:     */         }
/* 3011:2870 */         Object localObject4 = local_Request.getInputStream();
/* 3012:2872 */         if (local_Request.isRMI()) {
/* 3013:2872 */           localObject5 = (TypePayeeSyncRsV1)local_Request.read_value(TypePayeeSyncRsV1.class);
/* 3014:     */         } else {
/* 3015:2872 */           localObject5 = TypePayeeSyncRsV1Helper.read((InputStream)localObject4);
/* 3016:     */         }
/* 3017:2873 */         return localObject5;
/* 3018:     */       }
/* 3019:     */       catch (TRANSIENT localTRANSIENT)
/* 3020:     */       {
/* 3021:2877 */         if (i == 10) {
/* 3022:2879 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3023:     */         }
/* 3024:     */       }
/* 3025:     */       catch (UserException localUserException)
/* 3026:     */       {
/* 3027:2884 */         local_Request.isRMI();
/* 3028:     */         
/* 3029:     */ 
/* 3030:2887 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3031:     */       }
/* 3032:     */       catch (SystemException localSystemException)
/* 3033:     */       {
/* 3034:2891 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3035:     */       }
/* 3036:     */       finally
/* 3037:     */       {
/* 3038:2895 */         if (local_Request != null) {
/* 3039:2897 */           local_Request.close();
/* 3040:     */         }
/* 3041:2899 */         if (bool1) {
/* 3042:2900 */           localLocalStack.pop(localLocalFrame);
/* 3043:     */         }
/* 3044:2901 */         localLocalStack.setArgsOnLocal(bool2);
/* 3045:     */       }
/* 3046:     */     }
/* 3047:     */   }
/* 3048:     */   
/* 3049:     */   public TypePayeeTrnRsV1 processPayeeTrnRqV1(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1, TypeUserData paramTypeUserData)
/* 3050:     */     throws java.rmi.RemoteException
/* 3051:     */   {
/* 3052:2911 */     for (int i = 1;; i++)
/* 3053:     */     {
/* 3054:2913 */       _Request local_Request = null;
/* 3055:2914 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3056:2915 */       boolean bool1 = false;
/* 3057:2916 */       LocalFrame localLocalFrame = null;
/* 3058:2917 */       boolean bool2 = false;
/* 3059:     */       try
/* 3060:     */       {
/* 3061:2920 */         local_Request = __request("processPayeeTrnRqV1");
/* 3062:2921 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3063:2922 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3064:2923 */         localLocalStack.setArgsOnLocal(bool1);
/* 3065:2925 */         if (bool1)
/* 3066:     */         {
/* 3067:2927 */           localLocalFrame = new LocalFrame(2);
/* 3068:2928 */           localLocalStack.push(localLocalFrame);
/* 3069:     */         }
/* 3070:2930 */         if (!bool1)
/* 3071:     */         {
/* 3072:2932 */           localObject4 = local_Request.getOutputStream();
/* 3073:2933 */           if (local_Request.isRMI()) {
/* 3074:2933 */             local_Request.write_value(paramTypePayeeTrnRqV1, TypePayeeTrnRqV1.class);
/* 3075:     */           } else {
/* 3076:2933 */             TypePayeeTrnRqV1Helper.write((OutputStream)localObject4, paramTypePayeeTrnRqV1);
/* 3077:     */           }
/* 3078:2934 */           if (local_Request.isRMI()) {
/* 3079:2934 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3080:     */           } else {
/* 3081:2934 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3082:     */           }
/* 3083:     */         }
/* 3084:     */         else
/* 3085:     */         {
/* 3086:2938 */           localObject4 = local_Request.getOutputStream();
/* 3087:2939 */           localLocalFrame.add(paramTypePayeeTrnRqV1);
/* 3088:2940 */           localLocalFrame.add(paramTypeUserData);
/* 3089:     */         }
/* 3090:2942 */         local_Request.invoke();
/* 3091:     */         Object localObject5;
/* 3092:     */         Object localObject1;
/* 3093:2943 */         if (bool1)
/* 3094:     */         {
/* 3095:2945 */           localObject4 = null;
/* 3096:2946 */           localObject5 = localLocalFrame.getResult();
/* 3097:2947 */           if (localObject5 != null) {
/* 3098:2949 */             localObject4 = (TypePayeeTrnRsV1)ObjectVal.clone(localObject5);
/* 3099:     */           }
/* 3100:2951 */           return localObject4;
/* 3101:     */         }
/* 3102:2953 */         Object localObject4 = local_Request.getInputStream();
/* 3103:2955 */         if (local_Request.isRMI()) {
/* 3104:2955 */           localObject5 = (TypePayeeTrnRsV1)local_Request.read_value(TypePayeeTrnRsV1.class);
/* 3105:     */         } else {
/* 3106:2955 */           localObject5 = TypePayeeTrnRsV1Helper.read((InputStream)localObject4);
/* 3107:     */         }
/* 3108:2956 */         return localObject5;
/* 3109:     */       }
/* 3110:     */       catch (TRANSIENT localTRANSIENT)
/* 3111:     */       {
/* 3112:2960 */         if (i == 10) {
/* 3113:2962 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3114:     */         }
/* 3115:     */       }
/* 3116:     */       catch (UserException localUserException)
/* 3117:     */       {
/* 3118:2967 */         local_Request.isRMI();
/* 3119:     */         
/* 3120:     */ 
/* 3121:2970 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3122:     */       }
/* 3123:     */       catch (SystemException localSystemException)
/* 3124:     */       {
/* 3125:2974 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3126:     */       }
/* 3127:     */       finally
/* 3128:     */       {
/* 3129:2978 */         if (local_Request != null) {
/* 3130:2980 */           local_Request.close();
/* 3131:     */         }
/* 3132:2982 */         if (bool1) {
/* 3133:2983 */           localLocalStack.pop(localLocalFrame);
/* 3134:     */         }
/* 3135:2984 */         localLocalStack.setArgsOnLocal(bool2);
/* 3136:     */       }
/* 3137:     */     }
/* 3138:     */   }
/* 3139:     */   
/* 3140:     */   public TypePmtInqTrnRsV1 processPmtInqTrnRqV1(TypePmtInqTrnRqV1 paramTypePmtInqTrnRqV1, TypeUserData paramTypeUserData)
/* 3141:     */     throws java.rmi.RemoteException
/* 3142:     */   {
/* 3143:2994 */     for (int i = 1;; i++)
/* 3144:     */     {
/* 3145:2996 */       _Request local_Request = null;
/* 3146:2997 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3147:2998 */       boolean bool1 = false;
/* 3148:2999 */       LocalFrame localLocalFrame = null;
/* 3149:3000 */       boolean bool2 = false;
/* 3150:     */       try
/* 3151:     */       {
/* 3152:3003 */         local_Request = __request("processPmtInqTrnRqV1");
/* 3153:3004 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3154:3005 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3155:3006 */         localLocalStack.setArgsOnLocal(bool1);
/* 3156:3008 */         if (bool1)
/* 3157:     */         {
/* 3158:3010 */           localLocalFrame = new LocalFrame(2);
/* 3159:3011 */           localLocalStack.push(localLocalFrame);
/* 3160:     */         }
/* 3161:3013 */         if (!bool1)
/* 3162:     */         {
/* 3163:3015 */           localObject4 = local_Request.getOutputStream();
/* 3164:3016 */           if (local_Request.isRMI()) {
/* 3165:3016 */             local_Request.write_value(paramTypePmtInqTrnRqV1, TypePmtInqTrnRqV1.class);
/* 3166:     */           } else {
/* 3167:3016 */             TypePmtInqTrnRqV1Helper.write((OutputStream)localObject4, paramTypePmtInqTrnRqV1);
/* 3168:     */           }
/* 3169:3017 */           if (local_Request.isRMI()) {
/* 3170:3017 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3171:     */           } else {
/* 3172:3017 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3173:     */           }
/* 3174:     */         }
/* 3175:     */         else
/* 3176:     */         {
/* 3177:3021 */           localObject4 = local_Request.getOutputStream();
/* 3178:3022 */           localLocalFrame.add(paramTypePmtInqTrnRqV1);
/* 3179:3023 */           localLocalFrame.add(paramTypeUserData);
/* 3180:     */         }
/* 3181:3025 */         local_Request.invoke();
/* 3182:     */         Object localObject5;
/* 3183:     */         Object localObject1;
/* 3184:3026 */         if (bool1)
/* 3185:     */         {
/* 3186:3028 */           localObject4 = null;
/* 3187:3029 */           localObject5 = localLocalFrame.getResult();
/* 3188:3030 */           if (localObject5 != null) {
/* 3189:3032 */             localObject4 = (TypePmtInqTrnRsV1)ObjectVal.clone(localObject5);
/* 3190:     */           }
/* 3191:3034 */           return localObject4;
/* 3192:     */         }
/* 3193:3036 */         Object localObject4 = local_Request.getInputStream();
/* 3194:3038 */         if (local_Request.isRMI()) {
/* 3195:3038 */           localObject5 = (TypePmtInqTrnRsV1)local_Request.read_value(TypePmtInqTrnRsV1.class);
/* 3196:     */         } else {
/* 3197:3038 */           localObject5 = TypePmtInqTrnRsV1Helper.read((InputStream)localObject4);
/* 3198:     */         }
/* 3199:3039 */         return localObject5;
/* 3200:     */       }
/* 3201:     */       catch (TRANSIENT localTRANSIENT)
/* 3202:     */       {
/* 3203:3043 */         if (i == 10) {
/* 3204:3045 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3205:     */         }
/* 3206:     */       }
/* 3207:     */       catch (UserException localUserException)
/* 3208:     */       {
/* 3209:3050 */         local_Request.isRMI();
/* 3210:     */         
/* 3211:     */ 
/* 3212:3053 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3213:     */       }
/* 3214:     */       catch (SystemException localSystemException)
/* 3215:     */       {
/* 3216:3057 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3217:     */       }
/* 3218:     */       finally
/* 3219:     */       {
/* 3220:3061 */         if (local_Request != null) {
/* 3221:3063 */           local_Request.close();
/* 3222:     */         }
/* 3223:3065 */         if (bool1) {
/* 3224:3066 */           localLocalStack.pop(localLocalFrame);
/* 3225:     */         }
/* 3226:3067 */         localLocalStack.setArgsOnLocal(bool2);
/* 3227:     */       }
/* 3228:     */     }
/* 3229:     */   }
/* 3230:     */   
/* 3231:     */   public TypePmtSyncRsV1 processPmtSyncRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData)
/* 3232:     */     throws java.rmi.RemoteException
/* 3233:     */   {
/* 3234:3077 */     for (int i = 1;; i++)
/* 3235:     */     {
/* 3236:3079 */       _Request local_Request = null;
/* 3237:3080 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3238:3081 */       boolean bool1 = false;
/* 3239:3082 */       LocalFrame localLocalFrame = null;
/* 3240:3083 */       boolean bool2 = false;
/* 3241:     */       try
/* 3242:     */       {
/* 3243:3086 */         local_Request = __request("processPmtSyncRqV1");
/* 3244:3087 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3245:3088 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3246:3089 */         localLocalStack.setArgsOnLocal(bool1);
/* 3247:3091 */         if (bool1)
/* 3248:     */         {
/* 3249:3093 */           localLocalFrame = new LocalFrame(2);
/* 3250:3094 */           localLocalStack.push(localLocalFrame);
/* 3251:     */         }
/* 3252:3096 */         if (!bool1)
/* 3253:     */         {
/* 3254:3098 */           localObject4 = local_Request.getOutputStream();
/* 3255:3099 */           if (local_Request.isRMI()) {
/* 3256:3099 */             local_Request.write_value(paramTypePmtSyncRqV1, TypePmtSyncRqV1.class);
/* 3257:     */           } else {
/* 3258:3099 */             TypePmtSyncRqV1Helper.write((OutputStream)localObject4, paramTypePmtSyncRqV1);
/* 3259:     */           }
/* 3260:3100 */           if (local_Request.isRMI()) {
/* 3261:3100 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3262:     */           } else {
/* 3263:3100 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3264:     */           }
/* 3265:     */         }
/* 3266:     */         else
/* 3267:     */         {
/* 3268:3104 */           localObject4 = local_Request.getOutputStream();
/* 3269:3105 */           localLocalFrame.add(paramTypePmtSyncRqV1);
/* 3270:3106 */           localLocalFrame.add(paramTypeUserData);
/* 3271:     */         }
/* 3272:3108 */         local_Request.invoke();
/* 3273:     */         Object localObject5;
/* 3274:     */         Object localObject1;
/* 3275:3109 */         if (bool1)
/* 3276:     */         {
/* 3277:3111 */           localObject4 = null;
/* 3278:3112 */           localObject5 = localLocalFrame.getResult();
/* 3279:3113 */           if (localObject5 != null) {
/* 3280:3115 */             localObject4 = (TypePmtSyncRsV1)ObjectVal.clone(localObject5);
/* 3281:     */           }
/* 3282:3117 */           return localObject4;
/* 3283:     */         }
/* 3284:3119 */         Object localObject4 = local_Request.getInputStream();
/* 3285:3121 */         if (local_Request.isRMI()) {
/* 3286:3121 */           localObject5 = (TypePmtSyncRsV1)local_Request.read_value(TypePmtSyncRsV1.class);
/* 3287:     */         } else {
/* 3288:3121 */           localObject5 = TypePmtSyncRsV1Helper.read((InputStream)localObject4);
/* 3289:     */         }
/* 3290:3122 */         return localObject5;
/* 3291:     */       }
/* 3292:     */       catch (TRANSIENT localTRANSIENT)
/* 3293:     */       {
/* 3294:3126 */         if (i == 10) {
/* 3295:3128 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3296:     */         }
/* 3297:     */       }
/* 3298:     */       catch (UserException localUserException)
/* 3299:     */       {
/* 3300:3133 */         local_Request.isRMI();
/* 3301:     */         
/* 3302:     */ 
/* 3303:3136 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3304:     */       }
/* 3305:     */       catch (SystemException localSystemException)
/* 3306:     */       {
/* 3307:3140 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3308:     */       }
/* 3309:     */       finally
/* 3310:     */       {
/* 3311:3144 */         if (local_Request != null) {
/* 3312:3146 */           local_Request.close();
/* 3313:     */         }
/* 3314:3148 */         if (bool1) {
/* 3315:3149 */           localLocalStack.pop(localLocalFrame);
/* 3316:     */         }
/* 3317:3150 */         localLocalStack.setArgsOnLocal(bool2);
/* 3318:     */       }
/* 3319:     */     }
/* 3320:     */   }
/* 3321:     */   
/* 3322:     */   public TypePmtTrnRsV1 processPmtTrnRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
/* 3323:     */     throws java.rmi.RemoteException
/* 3324:     */   {
/* 3325:3160 */     for (int i = 1;; i++)
/* 3326:     */     {
/* 3327:3162 */       _Request local_Request = null;
/* 3328:3163 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3329:3164 */       boolean bool1 = false;
/* 3330:3165 */       LocalFrame localLocalFrame = null;
/* 3331:3166 */       boolean bool2 = false;
/* 3332:     */       try
/* 3333:     */       {
/* 3334:3169 */         local_Request = __request("processPmtTrnRqV1");
/* 3335:3170 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3336:3171 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3337:3172 */         localLocalStack.setArgsOnLocal(bool1);
/* 3338:3174 */         if (bool1)
/* 3339:     */         {
/* 3340:3176 */           localLocalFrame = new LocalFrame(2);
/* 3341:3177 */           localLocalStack.push(localLocalFrame);
/* 3342:     */         }
/* 3343:3179 */         if (!bool1)
/* 3344:     */         {
/* 3345:3181 */           localObject4 = local_Request.getOutputStream();
/* 3346:3182 */           if (local_Request.isRMI()) {
/* 3347:3182 */             local_Request.write_value(paramTypePmtTrnRqV1, TypePmtTrnRqV1.class);
/* 3348:     */           } else {
/* 3349:3182 */             TypePmtTrnRqV1Helper.write((OutputStream)localObject4, paramTypePmtTrnRqV1);
/* 3350:     */           }
/* 3351:3183 */           if (local_Request.isRMI()) {
/* 3352:3183 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3353:     */           } else {
/* 3354:3183 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3355:     */           }
/* 3356:     */         }
/* 3357:     */         else
/* 3358:     */         {
/* 3359:3187 */           localObject4 = local_Request.getOutputStream();
/* 3360:3188 */           localLocalFrame.add(paramTypePmtTrnRqV1);
/* 3361:3189 */           localLocalFrame.add(paramTypeUserData);
/* 3362:     */         }
/* 3363:3191 */         local_Request.invoke();
/* 3364:     */         Object localObject5;
/* 3365:     */         Object localObject1;
/* 3366:3192 */         if (bool1)
/* 3367:     */         {
/* 3368:3194 */           localObject4 = null;
/* 3369:3195 */           localObject5 = localLocalFrame.getResult();
/* 3370:3196 */           if (localObject5 != null) {
/* 3371:3198 */             localObject4 = (TypePmtTrnRsV1)ObjectVal.clone(localObject5);
/* 3372:     */           }
/* 3373:3200 */           return localObject4;
/* 3374:     */         }
/* 3375:3202 */         Object localObject4 = local_Request.getInputStream();
/* 3376:3204 */         if (local_Request.isRMI()) {
/* 3377:3204 */           localObject5 = (TypePmtTrnRsV1)local_Request.read_value(TypePmtTrnRsV1.class);
/* 3378:     */         } else {
/* 3379:3204 */           localObject5 = TypePmtTrnRsV1Helper.read((InputStream)localObject4);
/* 3380:     */         }
/* 3381:3205 */         return localObject5;
/* 3382:     */       }
/* 3383:     */       catch (TRANSIENT localTRANSIENT)
/* 3384:     */       {
/* 3385:3209 */         if (i == 10) {
/* 3386:3211 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3387:     */         }
/* 3388:     */       }
/* 3389:     */       catch (UserException localUserException)
/* 3390:     */       {
/* 3391:3216 */         local_Request.isRMI();
/* 3392:     */         
/* 3393:     */ 
/* 3394:3219 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3395:     */       }
/* 3396:     */       catch (SystemException localSystemException)
/* 3397:     */       {
/* 3398:3223 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3399:     */       }
/* 3400:     */       finally
/* 3401:     */       {
/* 3402:3227 */         if (local_Request != null) {
/* 3403:3229 */           local_Request.close();
/* 3404:     */         }
/* 3405:3231 */         if (bool1) {
/* 3406:3232 */           localLocalStack.pop(localLocalFrame);
/* 3407:     */         }
/* 3408:3233 */         localLocalStack.setArgsOnLocal(bool2);
/* 3409:     */       }
/* 3410:     */     }
/* 3411:     */   }
/* 3412:     */   
/* 3413:     */   public TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData)
/* 3414:     */     throws java.rmi.RemoteException
/* 3415:     */   {
/* 3416:3243 */     for (int i = 1;; i++)
/* 3417:     */     {
/* 3418:3245 */       _Request local_Request = null;
/* 3419:3246 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3420:3247 */       boolean bool1 = false;
/* 3421:3248 */       LocalFrame localLocalFrame = null;
/* 3422:3249 */       boolean bool2 = false;
/* 3423:     */       try
/* 3424:     */       {
/* 3425:3252 */         local_Request = __request("processRecIntraSyncRqV1");
/* 3426:3253 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3427:3254 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3428:3255 */         localLocalStack.setArgsOnLocal(bool1);
/* 3429:3257 */         if (bool1)
/* 3430:     */         {
/* 3431:3259 */           localLocalFrame = new LocalFrame(2);
/* 3432:3260 */           localLocalStack.push(localLocalFrame);
/* 3433:     */         }
/* 3434:3262 */         if (!bool1)
/* 3435:     */         {
/* 3436:3264 */           localObject4 = local_Request.getOutputStream();
/* 3437:3265 */           if (local_Request.isRMI()) {
/* 3438:3265 */             local_Request.write_value(paramTypeRecIntraSyncRqV1, TypeRecIntraSyncRqV1.class);
/* 3439:     */           } else {
/* 3440:3265 */             TypeRecIntraSyncRqV1Helper.write((OutputStream)localObject4, paramTypeRecIntraSyncRqV1);
/* 3441:     */           }
/* 3442:3266 */           if (local_Request.isRMI()) {
/* 3443:3266 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3444:     */           } else {
/* 3445:3266 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3446:     */           }
/* 3447:     */         }
/* 3448:     */         else
/* 3449:     */         {
/* 3450:3270 */           localObject4 = local_Request.getOutputStream();
/* 3451:3271 */           localLocalFrame.add(paramTypeRecIntraSyncRqV1);
/* 3452:3272 */           localLocalFrame.add(paramTypeUserData);
/* 3453:     */         }
/* 3454:3274 */         local_Request.invoke();
/* 3455:     */         Object localObject5;
/* 3456:     */         Object localObject1;
/* 3457:3275 */         if (bool1)
/* 3458:     */         {
/* 3459:3277 */           localObject4 = null;
/* 3460:3278 */           localObject5 = localLocalFrame.getResult();
/* 3461:3279 */           if (localObject5 != null) {
/* 3462:3281 */             localObject4 = (TypeRecIntraSyncRsV1)ObjectVal.clone(localObject5);
/* 3463:     */           }
/* 3464:3283 */           return localObject4;
/* 3465:     */         }
/* 3466:3285 */         Object localObject4 = local_Request.getInputStream();
/* 3467:3287 */         if (local_Request.isRMI()) {
/* 3468:3287 */           localObject5 = (TypeRecIntraSyncRsV1)local_Request.read_value(TypeRecIntraSyncRsV1.class);
/* 3469:     */         } else {
/* 3470:3287 */           localObject5 = TypeRecIntraSyncRsV1Helper.read((InputStream)localObject4);
/* 3471:     */         }
/* 3472:3288 */         return localObject5;
/* 3473:     */       }
/* 3474:     */       catch (TRANSIENT localTRANSIENT)
/* 3475:     */       {
/* 3476:3292 */         if (i == 10) {
/* 3477:3294 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3478:     */         }
/* 3479:     */       }
/* 3480:     */       catch (UserException localUserException)
/* 3481:     */       {
/* 3482:3299 */         local_Request.isRMI();
/* 3483:     */         
/* 3484:     */ 
/* 3485:3302 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3486:     */       }
/* 3487:     */       catch (SystemException localSystemException)
/* 3488:     */       {
/* 3489:3306 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3490:     */       }
/* 3491:     */       finally
/* 3492:     */       {
/* 3493:3310 */         if (local_Request != null) {
/* 3494:3312 */           local_Request.close();
/* 3495:     */         }
/* 3496:3314 */         if (bool1) {
/* 3497:3315 */           localLocalStack.pop(localLocalFrame);
/* 3498:     */         }
/* 3499:3316 */         localLocalStack.setArgsOnLocal(bool2);
/* 3500:     */       }
/* 3501:     */     }
/* 3502:     */   }
/* 3503:     */   
/* 3504:     */   public TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData)
/* 3505:     */     throws java.rmi.RemoteException
/* 3506:     */   {
/* 3507:3326 */     for (int i = 1;; i++)
/* 3508:     */     {
/* 3509:3328 */       _Request local_Request = null;
/* 3510:3329 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3511:3330 */       boolean bool1 = false;
/* 3512:3331 */       LocalFrame localLocalFrame = null;
/* 3513:3332 */       boolean bool2 = false;
/* 3514:     */       try
/* 3515:     */       {
/* 3516:3335 */         local_Request = __request("processRecIntraTrnRqV1");
/* 3517:3336 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3518:3337 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3519:3338 */         localLocalStack.setArgsOnLocal(bool1);
/* 3520:3340 */         if (bool1)
/* 3521:     */         {
/* 3522:3342 */           localLocalFrame = new LocalFrame(2);
/* 3523:3343 */           localLocalStack.push(localLocalFrame);
/* 3524:     */         }
/* 3525:3345 */         if (!bool1)
/* 3526:     */         {
/* 3527:3347 */           localObject4 = local_Request.getOutputStream();
/* 3528:3348 */           if (local_Request.isRMI()) {
/* 3529:3348 */             local_Request.write_value(paramTypeRecIntraTrnRqV1, TypeRecIntraTrnRqV1.class);
/* 3530:     */           } else {
/* 3531:3348 */             TypeRecIntraTrnRqV1Helper.write((OutputStream)localObject4, paramTypeRecIntraTrnRqV1);
/* 3532:     */           }
/* 3533:3349 */           if (local_Request.isRMI()) {
/* 3534:3349 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3535:     */           } else {
/* 3536:3349 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3537:     */           }
/* 3538:     */         }
/* 3539:     */         else
/* 3540:     */         {
/* 3541:3353 */           localObject4 = local_Request.getOutputStream();
/* 3542:3354 */           localLocalFrame.add(paramTypeRecIntraTrnRqV1);
/* 3543:3355 */           localLocalFrame.add(paramTypeUserData);
/* 3544:     */         }
/* 3545:3357 */         local_Request.invoke();
/* 3546:     */         Object localObject5;
/* 3547:     */         Object localObject1;
/* 3548:3358 */         if (bool1)
/* 3549:     */         {
/* 3550:3360 */           localObject4 = null;
/* 3551:3361 */           localObject5 = localLocalFrame.getResult();
/* 3552:3362 */           if (localObject5 != null) {
/* 3553:3364 */             localObject4 = (TypeRecIntraTrnRsV1)ObjectVal.clone(localObject5);
/* 3554:     */           }
/* 3555:3366 */           return localObject4;
/* 3556:     */         }
/* 3557:3368 */         Object localObject4 = local_Request.getInputStream();
/* 3558:3370 */         if (local_Request.isRMI()) {
/* 3559:3370 */           localObject5 = (TypeRecIntraTrnRsV1)local_Request.read_value(TypeRecIntraTrnRsV1.class);
/* 3560:     */         } else {
/* 3561:3370 */           localObject5 = TypeRecIntraTrnRsV1Helper.read((InputStream)localObject4);
/* 3562:     */         }
/* 3563:3371 */         return localObject5;
/* 3564:     */       }
/* 3565:     */       catch (TRANSIENT localTRANSIENT)
/* 3566:     */       {
/* 3567:3375 */         if (i == 10) {
/* 3568:3377 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3569:     */         }
/* 3570:     */       }
/* 3571:     */       catch (UserException localUserException)
/* 3572:     */       {
/* 3573:3382 */         local_Request.isRMI();
/* 3574:     */         
/* 3575:     */ 
/* 3576:3385 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3577:     */       }
/* 3578:     */       catch (SystemException localSystemException)
/* 3579:     */       {
/* 3580:3389 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3581:     */       }
/* 3582:     */       finally
/* 3583:     */       {
/* 3584:3393 */         if (local_Request != null) {
/* 3585:3395 */           local_Request.close();
/* 3586:     */         }
/* 3587:3397 */         if (bool1) {
/* 3588:3398 */           localLocalStack.pop(localLocalFrame);
/* 3589:     */         }
/* 3590:3399 */         localLocalStack.setArgsOnLocal(bool2);
/* 3591:     */       }
/* 3592:     */     }
/* 3593:     */   }
/* 3594:     */   
/* 3595:     */   public TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData)
/* 3596:     */     throws java.rmi.RemoteException
/* 3597:     */   {
/* 3598:3409 */     for (int i = 1;; i++)
/* 3599:     */     {
/* 3600:3411 */       _Request local_Request = null;
/* 3601:3412 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3602:3413 */       boolean bool1 = false;
/* 3603:3414 */       LocalFrame localLocalFrame = null;
/* 3604:3415 */       boolean bool2 = false;
/* 3605:     */       try
/* 3606:     */       {
/* 3607:3418 */         local_Request = __request("processRecPmtSyncRqV1");
/* 3608:3419 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3609:3420 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3610:3421 */         localLocalStack.setArgsOnLocal(bool1);
/* 3611:3423 */         if (bool1)
/* 3612:     */         {
/* 3613:3425 */           localLocalFrame = new LocalFrame(2);
/* 3614:3426 */           localLocalStack.push(localLocalFrame);
/* 3615:     */         }
/* 3616:3428 */         if (!bool1)
/* 3617:     */         {
/* 3618:3430 */           localObject4 = local_Request.getOutputStream();
/* 3619:3431 */           if (local_Request.isRMI()) {
/* 3620:3431 */             local_Request.write_value(paramTypeRecPmtSyncRqV1, TypeRecPmtSyncRqV1.class);
/* 3621:     */           } else {
/* 3622:3431 */             TypeRecPmtSyncRqV1Helper.write((OutputStream)localObject4, paramTypeRecPmtSyncRqV1);
/* 3623:     */           }
/* 3624:3432 */           if (local_Request.isRMI()) {
/* 3625:3432 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3626:     */           } else {
/* 3627:3432 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3628:     */           }
/* 3629:     */         }
/* 3630:     */         else
/* 3631:     */         {
/* 3632:3436 */           localObject4 = local_Request.getOutputStream();
/* 3633:3437 */           localLocalFrame.add(paramTypeRecPmtSyncRqV1);
/* 3634:3438 */           localLocalFrame.add(paramTypeUserData);
/* 3635:     */         }
/* 3636:3440 */         local_Request.invoke();
/* 3637:     */         Object localObject5;
/* 3638:     */         Object localObject1;
/* 3639:3441 */         if (bool1)
/* 3640:     */         {
/* 3641:3443 */           localObject4 = null;
/* 3642:3444 */           localObject5 = localLocalFrame.getResult();
/* 3643:3445 */           if (localObject5 != null) {
/* 3644:3447 */             localObject4 = (TypeRecPmtSyncRsV1)ObjectVal.clone(localObject5);
/* 3645:     */           }
/* 3646:3449 */           return localObject4;
/* 3647:     */         }
/* 3648:3451 */         Object localObject4 = local_Request.getInputStream();
/* 3649:3453 */         if (local_Request.isRMI()) {
/* 3650:3453 */           localObject5 = (TypeRecPmtSyncRsV1)local_Request.read_value(TypeRecPmtSyncRsV1.class);
/* 3651:     */         } else {
/* 3652:3453 */           localObject5 = TypeRecPmtSyncRsV1Helper.read((InputStream)localObject4);
/* 3653:     */         }
/* 3654:3454 */         return localObject5;
/* 3655:     */       }
/* 3656:     */       catch (TRANSIENT localTRANSIENT)
/* 3657:     */       {
/* 3658:3458 */         if (i == 10) {
/* 3659:3460 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3660:     */         }
/* 3661:     */       }
/* 3662:     */       catch (UserException localUserException)
/* 3663:     */       {
/* 3664:3465 */         local_Request.isRMI();
/* 3665:     */         
/* 3666:     */ 
/* 3667:3468 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3668:     */       }
/* 3669:     */       catch (SystemException localSystemException)
/* 3670:     */       {
/* 3671:3472 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3672:     */       }
/* 3673:     */       finally
/* 3674:     */       {
/* 3675:3476 */         if (local_Request != null) {
/* 3676:3478 */           local_Request.close();
/* 3677:     */         }
/* 3678:3480 */         if (bool1) {
/* 3679:3481 */           localLocalStack.pop(localLocalFrame);
/* 3680:     */         }
/* 3681:3482 */         localLocalStack.setArgsOnLocal(bool2);
/* 3682:     */       }
/* 3683:     */     }
/* 3684:     */   }
/* 3685:     */   
/* 3686:     */   public TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
/* 3687:     */     throws java.rmi.RemoteException
/* 3688:     */   {
/* 3689:3492 */     for (int i = 1;; i++)
/* 3690:     */     {
/* 3691:3494 */       _Request local_Request = null;
/* 3692:3495 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3693:3496 */       boolean bool1 = false;
/* 3694:3497 */       LocalFrame localLocalFrame = null;
/* 3695:3498 */       boolean bool2 = false;
/* 3696:     */       try
/* 3697:     */       {
/* 3698:3501 */         local_Request = __request("processRecPmtTrnRqV1");
/* 3699:3502 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3700:3503 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3701:3504 */         localLocalStack.setArgsOnLocal(bool1);
/* 3702:3506 */         if (bool1)
/* 3703:     */         {
/* 3704:3508 */           localLocalFrame = new LocalFrame(2);
/* 3705:3509 */           localLocalStack.push(localLocalFrame);
/* 3706:     */         }
/* 3707:3511 */         if (!bool1)
/* 3708:     */         {
/* 3709:3513 */           localObject4 = local_Request.getOutputStream();
/* 3710:3514 */           if (local_Request.isRMI()) {
/* 3711:3514 */             local_Request.write_value(paramTypeRecPmtTrnRqV1, TypeRecPmtTrnRqV1.class);
/* 3712:     */           } else {
/* 3713:3514 */             TypeRecPmtTrnRqV1Helper.write((OutputStream)localObject4, paramTypeRecPmtTrnRqV1);
/* 3714:     */           }
/* 3715:3515 */           if (local_Request.isRMI()) {
/* 3716:3515 */             local_Request.write_value(paramTypeUserData, TypeUserData.class);
/* 3717:     */           } else {
/* 3718:3515 */             TypeUserDataHelper.write((OutputStream)localObject4, paramTypeUserData);
/* 3719:     */           }
/* 3720:     */         }
/* 3721:     */         else
/* 3722:     */         {
/* 3723:3519 */           localObject4 = local_Request.getOutputStream();
/* 3724:3520 */           localLocalFrame.add(paramTypeRecPmtTrnRqV1);
/* 3725:3521 */           localLocalFrame.add(paramTypeUserData);
/* 3726:     */         }
/* 3727:3523 */         local_Request.invoke();
/* 3728:     */         Object localObject5;
/* 3729:     */         Object localObject1;
/* 3730:3524 */         if (bool1)
/* 3731:     */         {
/* 3732:3526 */           localObject4 = null;
/* 3733:3527 */           localObject5 = localLocalFrame.getResult();
/* 3734:3528 */           if (localObject5 != null) {
/* 3735:3530 */             localObject4 = (TypeRecPmtTrnRsV1)ObjectVal.clone(localObject5);
/* 3736:     */           }
/* 3737:3532 */           return localObject4;
/* 3738:     */         }
/* 3739:3534 */         Object localObject4 = local_Request.getInputStream();
/* 3740:3536 */         if (local_Request.isRMI()) {
/* 3741:3536 */           localObject5 = (TypeRecPmtTrnRsV1)local_Request.read_value(TypeRecPmtTrnRsV1.class);
/* 3742:     */         } else {
/* 3743:3536 */           localObject5 = TypeRecPmtTrnRsV1Helper.read((InputStream)localObject4);
/* 3744:     */         }
/* 3745:3537 */         return localObject5;
/* 3746:     */       }
/* 3747:     */       catch (TRANSIENT localTRANSIENT)
/* 3748:     */       {
/* 3749:3541 */         if (i == 10) {
/* 3750:3543 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3751:     */         }
/* 3752:     */       }
/* 3753:     */       catch (UserException localUserException)
/* 3754:     */       {
/* 3755:3548 */         local_Request.isRMI();
/* 3756:     */         
/* 3757:     */ 
/* 3758:3551 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3759:     */       }
/* 3760:     */       catch (SystemException localSystemException)
/* 3761:     */       {
/* 3762:3555 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3763:     */       }
/* 3764:     */       finally
/* 3765:     */       {
/* 3766:3559 */         if (local_Request != null) {
/* 3767:3561 */           local_Request.close();
/* 3768:     */         }
/* 3769:3563 */         if (bool1) {
/* 3770:3564 */           localLocalStack.pop(localLocalFrame);
/* 3771:     */         }
/* 3772:3565 */         localLocalStack.setArgsOnLocal(bool2);
/* 3773:     */       }
/* 3774:     */     }
/* 3775:     */   }
/* 3776:     */   
/* 3777:     */   public String[] getPayeeNames(String paramString, int paramInt)
/* 3778:     */     throws java.rmi.RemoteException
/* 3779:     */   {
/* 3780:3575 */     for (int i = 1;; i++)
/* 3781:     */     {
/* 3782:3577 */       _Request local_Request = null;
/* 3783:3578 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3784:3579 */       boolean bool1 = false;
/* 3785:3580 */       LocalFrame localLocalFrame = null;
/* 3786:3581 */       boolean bool2 = false;
/* 3787:     */       try
/* 3788:     */       {
/* 3789:3584 */         local_Request = __request("getPayeeNames__string__long", "getPayeeNames__CORBA_WStringValue__long");
/* 3790:3585 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3791:3586 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3792:3587 */         localLocalStack.setArgsOnLocal(bool1);
/* 3793:3589 */         if (bool1)
/* 3794:     */         {
/* 3795:3591 */           localLocalFrame = new LocalFrame(2);
/* 3796:3592 */           localLocalStack.push(localLocalFrame);
/* 3797:     */         }
/* 3798:3594 */         if (!bool1)
/* 3799:     */         {
/* 3800:3596 */           localObject4 = local_Request.getOutputStream();
/* 3801:3597 */           local_Request.write_string(paramString);
/* 3802:3598 */           ((OutputStream)localObject4).write_long(paramInt);
/* 3803:     */         }
/* 3804:     */         else
/* 3805:     */         {
/* 3806:3602 */           localObject4 = local_Request.getOutputStream();
/* 3807:3603 */           localLocalFrame.add(paramString);
/* 3808:3604 */           localLocalFrame.add(paramInt);
/* 3809:     */         }
/* 3810:3606 */         local_Request.invoke();
/* 3811:     */         Object localObject5;
/* 3812:     */         Object localObject1;
/* 3813:3607 */         if (bool1)
/* 3814:     */         {
/* 3815:3609 */           localObject4 = null;
/* 3816:3610 */           localObject5 = localLocalFrame.getResult();
/* 3817:3611 */           if (localObject5 != null) {
/* 3818:3613 */             localObject4 = (String[])ObjectVal.clone(localObject5);
/* 3819:     */           }
/* 3820:3615 */           return localObject4;
/* 3821:     */         }
/* 3822:3617 */         Object localObject4 = local_Request.getInputStream();
/* 3823:3619 */         if (local_Request.isRMI()) {
/* 3824:3619 */           localObject5 = (String[])local_Request.read_value(new String[0].getClass());
/* 3825:     */         } else {
/* 3826:3619 */           localObject5 = StringSeqHelper.read((InputStream)localObject4);
/* 3827:     */         }
/* 3828:3620 */         return localObject5;
/* 3829:     */       }
/* 3830:     */       catch (TRANSIENT localTRANSIENT)
/* 3831:     */       {
/* 3832:3624 */         if (i == 10) {
/* 3833:3626 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3834:     */         }
/* 3835:     */       }
/* 3836:     */       catch (UserException localUserException)
/* 3837:     */       {
/* 3838:3631 */         local_Request.isRMI();
/* 3839:     */         
/* 3840:     */ 
/* 3841:3634 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3842:     */       }
/* 3843:     */       catch (SystemException localSystemException)
/* 3844:     */       {
/* 3845:3638 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3846:     */       }
/* 3847:     */       finally
/* 3848:     */       {
/* 3849:3642 */         if (local_Request != null) {
/* 3850:3644 */           local_Request.close();
/* 3851:     */         }
/* 3852:3646 */         if (bool1) {
/* 3853:3647 */           localLocalStack.pop(localLocalFrame);
/* 3854:     */         }
/* 3855:3648 */         localLocalStack.setArgsOnLocal(bool2);
/* 3856:     */       }
/* 3857:     */     }
/* 3858:     */   }
/* 3859:     */   
/* 3860:     */   public String[] getPayeeNames(String paramString)
/* 3861:     */     throws java.rmi.RemoteException
/* 3862:     */   {
/* 3863:3657 */     for (int i = 1;; i++)
/* 3864:     */     {
/* 3865:3659 */       _Request local_Request = null;
/* 3866:3660 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3867:3661 */       boolean bool1 = false;
/* 3868:3662 */       LocalFrame localLocalFrame = null;
/* 3869:3663 */       boolean bool2 = false;
/* 3870:     */       try
/* 3871:     */       {
/* 3872:3666 */         local_Request = __request("getPayeeNames__string", "getPayeeNames__CORBA_WStringValue");
/* 3873:3667 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3874:3668 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3875:3669 */         localLocalStack.setArgsOnLocal(bool1);
/* 3876:3671 */         if (bool1)
/* 3877:     */         {
/* 3878:3673 */           localLocalFrame = new LocalFrame(1);
/* 3879:3674 */           localLocalStack.push(localLocalFrame);
/* 3880:     */         }
/* 3881:3676 */         if (!bool1)
/* 3882:     */         {
/* 3883:3678 */           localObject4 = local_Request.getOutputStream();
/* 3884:3679 */           local_Request.write_string(paramString);
/* 3885:     */         }
/* 3886:     */         else
/* 3887:     */         {
/* 3888:3683 */           localObject4 = local_Request.getOutputStream();
/* 3889:3684 */           localLocalFrame.add(paramString);
/* 3890:     */         }
/* 3891:3686 */         local_Request.invoke();
/* 3892:     */         Object localObject5;
/* 3893:     */         Object localObject1;
/* 3894:3687 */         if (bool1)
/* 3895:     */         {
/* 3896:3689 */           localObject4 = null;
/* 3897:3690 */           localObject5 = localLocalFrame.getResult();
/* 3898:3691 */           if (localObject5 != null) {
/* 3899:3693 */             localObject4 = (String[])ObjectVal.clone(localObject5);
/* 3900:     */           }
/* 3901:3695 */           return localObject4;
/* 3902:     */         }
/* 3903:3697 */         Object localObject4 = local_Request.getInputStream();
/* 3904:3699 */         if (local_Request.isRMI()) {
/* 3905:3699 */           localObject5 = (String[])local_Request.read_value(new String[0].getClass());
/* 3906:     */         } else {
/* 3907:3699 */           localObject5 = StringSeqHelper.read((InputStream)localObject4);
/* 3908:     */         }
/* 3909:3700 */         return localObject5;
/* 3910:     */       }
/* 3911:     */       catch (TRANSIENT localTRANSIENT)
/* 3912:     */       {
/* 3913:3704 */         if (i == 10) {
/* 3914:3706 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3915:     */         }
/* 3916:     */       }
/* 3917:     */       catch (UserException localUserException)
/* 3918:     */       {
/* 3919:3711 */         local_Request.isRMI();
/* 3920:     */         
/* 3921:     */ 
/* 3922:3714 */         throw new java.rmi.RemoteException(localUserException.type);
/* 3923:     */       }
/* 3924:     */       catch (SystemException localSystemException)
/* 3925:     */       {
/* 3926:3718 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 3927:     */       }
/* 3928:     */       finally
/* 3929:     */       {
/* 3930:3722 */         if (local_Request != null) {
/* 3931:3724 */           local_Request.close();
/* 3932:     */         }
/* 3933:3726 */         if (bool1) {
/* 3934:3727 */           localLocalStack.pop(localLocalFrame);
/* 3935:     */         }
/* 3936:3728 */         localLocalStack.setArgsOnLocal(bool2);
/* 3937:     */       }
/* 3938:     */     }
/* 3939:     */   }
/* 3940:     */   
/* 3941:     */   public String[] getPayeeIDs(String paramString)
/* 3942:     */     throws java.rmi.RemoteException
/* 3943:     */   {
/* 3944:3737 */     for (int i = 1;; i++)
/* 3945:     */     {
/* 3946:3739 */       _Request local_Request = null;
/* 3947:3740 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 3948:3741 */       boolean bool1 = false;
/* 3949:3742 */       LocalFrame localLocalFrame = null;
/* 3950:3743 */       boolean bool2 = false;
/* 3951:     */       try
/* 3952:     */       {
/* 3953:3746 */         local_Request = __request("getPayeeIDs");
/* 3954:3747 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 3955:3748 */         bool2 = localLocalStack.isArgsOnLocal();
/* 3956:3749 */         localLocalStack.setArgsOnLocal(bool1);
/* 3957:3751 */         if (bool1)
/* 3958:     */         {
/* 3959:3753 */           localLocalFrame = new LocalFrame(1);
/* 3960:3754 */           localLocalStack.push(localLocalFrame);
/* 3961:     */         }
/* 3962:3756 */         if (!bool1)
/* 3963:     */         {
/* 3964:3758 */           localObject4 = local_Request.getOutputStream();
/* 3965:3759 */           local_Request.write_string(paramString);
/* 3966:     */         }
/* 3967:     */         else
/* 3968:     */         {
/* 3969:3763 */           localObject4 = local_Request.getOutputStream();
/* 3970:3764 */           localLocalFrame.add(paramString);
/* 3971:     */         }
/* 3972:3766 */         local_Request.invoke();
/* 3973:     */         Object localObject5;
/* 3974:     */         Object localObject1;
/* 3975:3767 */         if (bool1)
/* 3976:     */         {
/* 3977:3769 */           localObject4 = null;
/* 3978:3770 */           localObject5 = localLocalFrame.getResult();
/* 3979:3771 */           if (localObject5 != null) {
/* 3980:3773 */             localObject4 = (String[])ObjectVal.clone(localObject5);
/* 3981:     */           }
/* 3982:3775 */           return localObject4;
/* 3983:     */         }
/* 3984:3777 */         Object localObject4 = local_Request.getInputStream();
/* 3985:3779 */         if (local_Request.isRMI()) {
/* 3986:3779 */           localObject5 = (String[])local_Request.read_value(new String[0].getClass());
/* 3987:     */         } else {
/* 3988:3779 */           localObject5 = StringSeqHelper.read((InputStream)localObject4);
/* 3989:     */         }
/* 3990:3780 */         return localObject5;
/* 3991:     */       }
/* 3992:     */       catch (TRANSIENT localTRANSIENT)
/* 3993:     */       {
/* 3994:3784 */         if (i == 10) {
/* 3995:3786 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 3996:     */         }
/* 3997:     */       }
/* 3998:     */       catch (UserException localUserException)
/* 3999:     */       {
/* 4000:3791 */         local_Request.isRMI();
/* 4001:     */         
/* 4002:     */ 
/* 4003:3794 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4004:     */       }
/* 4005:     */       catch (SystemException localSystemException)
/* 4006:     */       {
/* 4007:3798 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4008:     */       }
/* 4009:     */       finally
/* 4010:     */       {
/* 4011:3802 */         if (local_Request != null) {
/* 4012:3804 */           local_Request.close();
/* 4013:     */         }
/* 4014:3806 */         if (bool1) {
/* 4015:3807 */           localLocalStack.pop(localLocalFrame);
/* 4016:     */         }
/* 4017:3808 */         localLocalStack.setArgsOnLocal(bool2);
/* 4018:     */       }
/* 4019:     */     }
/* 4020:     */   }
/* 4021:     */   
/* 4022:     */   public PayeeInfo[] getPayees(String paramString, int paramInt)
/* 4023:     */     throws java.rmi.RemoteException
/* 4024:     */   {
/* 4025:3818 */     for (int i = 1;; i++)
/* 4026:     */     {
/* 4027:3820 */       _Request local_Request = null;
/* 4028:3821 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4029:3822 */       boolean bool1 = false;
/* 4030:3823 */       LocalFrame localLocalFrame = null;
/* 4031:3824 */       boolean bool2 = false;
/* 4032:     */       try
/* 4033:     */       {
/* 4034:3827 */         local_Request = __request("getPayees__string__long", "getPayees__CORBA_WStringValue__long");
/* 4035:3828 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4036:3829 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4037:3830 */         localLocalStack.setArgsOnLocal(bool1);
/* 4038:3832 */         if (bool1)
/* 4039:     */         {
/* 4040:3834 */           localLocalFrame = new LocalFrame(2);
/* 4041:3835 */           localLocalStack.push(localLocalFrame);
/* 4042:     */         }
/* 4043:3837 */         if (!bool1)
/* 4044:     */         {
/* 4045:3839 */           localObject4 = local_Request.getOutputStream();
/* 4046:3840 */           local_Request.write_string(paramString);
/* 4047:3841 */           ((OutputStream)localObject4).write_long(paramInt);
/* 4048:     */         }
/* 4049:     */         else
/* 4050:     */         {
/* 4051:3845 */           localObject4 = local_Request.getOutputStream();
/* 4052:3846 */           localLocalFrame.add(paramString);
/* 4053:3847 */           localLocalFrame.add(paramInt);
/* 4054:     */         }
/* 4055:3849 */         local_Request.invoke();
/* 4056:     */         Object localObject5;
/* 4057:     */         Object localObject1;
/* 4058:3850 */         if (bool1)
/* 4059:     */         {
/* 4060:3852 */           localObject4 = null;
/* 4061:3853 */           localObject5 = localLocalFrame.getResult();
/* 4062:3854 */           if (localObject5 != null) {
/* 4063:3856 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 4064:     */           }
/* 4065:3858 */           return localObject4;
/* 4066:     */         }
/* 4067:3860 */         Object localObject4 = local_Request.getInputStream();
/* 4068:3862 */         if (local_Request.isRMI()) {
/* 4069:3862 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 4070:     */         } else {
/* 4071:3862 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 4072:     */         }
/* 4073:3863 */         return localObject5;
/* 4074:     */       }
/* 4075:     */       catch (TRANSIENT localTRANSIENT)
/* 4076:     */       {
/* 4077:3867 */         if (i == 10) {
/* 4078:3869 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4079:     */         }
/* 4080:     */       }
/* 4081:     */       catch (UserException localUserException)
/* 4082:     */       {
/* 4083:3874 */         local_Request.isRMI();
/* 4084:     */         
/* 4085:     */ 
/* 4086:3877 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4087:     */       }
/* 4088:     */       catch (SystemException localSystemException)
/* 4089:     */       {
/* 4090:3881 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4091:     */       }
/* 4092:     */       finally
/* 4093:     */       {
/* 4094:3885 */         if (local_Request != null) {
/* 4095:3887 */           local_Request.close();
/* 4096:     */         }
/* 4097:3889 */         if (bool1) {
/* 4098:3890 */           localLocalStack.pop(localLocalFrame);
/* 4099:     */         }
/* 4100:3891 */         localLocalStack.setArgsOnLocal(bool2);
/* 4101:     */       }
/* 4102:     */     }
/* 4103:     */   }
/* 4104:     */   
/* 4105:     */   public PayeeInfo[] getPayees(String paramString)
/* 4106:     */     throws java.rmi.RemoteException
/* 4107:     */   {
/* 4108:3900 */     for (int i = 1;; i++)
/* 4109:     */     {
/* 4110:3902 */       _Request local_Request = null;
/* 4111:3903 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4112:3904 */       boolean bool1 = false;
/* 4113:3905 */       LocalFrame localLocalFrame = null;
/* 4114:3906 */       boolean bool2 = false;
/* 4115:     */       try
/* 4116:     */       {
/* 4117:3909 */         local_Request = __request("getPayees__string", "getPayees__CORBA_WStringValue");
/* 4118:3910 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4119:3911 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4120:3912 */         localLocalStack.setArgsOnLocal(bool1);
/* 4121:3914 */         if (bool1)
/* 4122:     */         {
/* 4123:3916 */           localLocalFrame = new LocalFrame(1);
/* 4124:3917 */           localLocalStack.push(localLocalFrame);
/* 4125:     */         }
/* 4126:3919 */         if (!bool1)
/* 4127:     */         {
/* 4128:3921 */           localObject4 = local_Request.getOutputStream();
/* 4129:3922 */           local_Request.write_string(paramString);
/* 4130:     */         }
/* 4131:     */         else
/* 4132:     */         {
/* 4133:3926 */           localObject4 = local_Request.getOutputStream();
/* 4134:3927 */           localLocalFrame.add(paramString);
/* 4135:     */         }
/* 4136:3929 */         local_Request.invoke();
/* 4137:     */         Object localObject5;
/* 4138:     */         Object localObject1;
/* 4139:3930 */         if (bool1)
/* 4140:     */         {
/* 4141:3932 */           localObject4 = null;
/* 4142:3933 */           localObject5 = localLocalFrame.getResult();
/* 4143:3934 */           if (localObject5 != null) {
/* 4144:3936 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 4145:     */           }
/* 4146:3938 */           return localObject4;
/* 4147:     */         }
/* 4148:3940 */         Object localObject4 = local_Request.getInputStream();
/* 4149:3942 */         if (local_Request.isRMI()) {
/* 4150:3942 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 4151:     */         } else {
/* 4152:3942 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 4153:     */         }
/* 4154:3943 */         return localObject5;
/* 4155:     */       }
/* 4156:     */       catch (TRANSIENT localTRANSIENT)
/* 4157:     */       {
/* 4158:3947 */         if (i == 10) {
/* 4159:3949 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4160:     */         }
/* 4161:     */       }
/* 4162:     */       catch (UserException localUserException)
/* 4163:     */       {
/* 4164:3954 */         local_Request.isRMI();
/* 4165:     */         
/* 4166:     */ 
/* 4167:3957 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4168:     */       }
/* 4169:     */       catch (SystemException localSystemException)
/* 4170:     */       {
/* 4171:3961 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4172:     */       }
/* 4173:     */       finally
/* 4174:     */       {
/* 4175:3965 */         if (local_Request != null) {
/* 4176:3967 */           local_Request.close();
/* 4177:     */         }
/* 4178:3969 */         if (bool1) {
/* 4179:3970 */           localLocalStack.pop(localLocalFrame);
/* 4180:     */         }
/* 4181:3971 */         localLocalStack.setArgsOnLocal(bool2);
/* 4182:     */       }
/* 4183:     */     }
/* 4184:     */   }
/* 4185:     */   
/* 4186:     */   public PayeeInfo[] searchGlobalPayees(String paramString)
/* 4187:     */     throws java.rmi.RemoteException
/* 4188:     */   {
/* 4189:3980 */     for (int i = 1;; i++)
/* 4190:     */     {
/* 4191:3982 */       _Request local_Request = null;
/* 4192:3983 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4193:3984 */       boolean bool1 = false;
/* 4194:3985 */       LocalFrame localLocalFrame = null;
/* 4195:3986 */       boolean bool2 = false;
/* 4196:     */       try
/* 4197:     */       {
/* 4198:3989 */         local_Request = __request("searchGlobalPayees");
/* 4199:3990 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4200:3991 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4201:3992 */         localLocalStack.setArgsOnLocal(bool1);
/* 4202:3994 */         if (bool1)
/* 4203:     */         {
/* 4204:3996 */           localLocalFrame = new LocalFrame(1);
/* 4205:3997 */           localLocalStack.push(localLocalFrame);
/* 4206:     */         }
/* 4207:3999 */         if (!bool1)
/* 4208:     */         {
/* 4209:4001 */           localObject4 = local_Request.getOutputStream();
/* 4210:4002 */           local_Request.write_string(paramString);
/* 4211:     */         }
/* 4212:     */         else
/* 4213:     */         {
/* 4214:4006 */           localObject4 = local_Request.getOutputStream();
/* 4215:4007 */           localLocalFrame.add(paramString);
/* 4216:     */         }
/* 4217:4009 */         local_Request.invoke();
/* 4218:     */         Object localObject5;
/* 4219:     */         Object localObject1;
/* 4220:4010 */         if (bool1)
/* 4221:     */         {
/* 4222:4012 */           localObject4 = null;
/* 4223:4013 */           localObject5 = localLocalFrame.getResult();
/* 4224:4014 */           if (localObject5 != null) {
/* 4225:4016 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 4226:     */           }
/* 4227:4018 */           return localObject4;
/* 4228:     */         }
/* 4229:4020 */         Object localObject4 = local_Request.getInputStream();
/* 4230:4022 */         if (local_Request.isRMI()) {
/* 4231:4022 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 4232:     */         } else {
/* 4233:4022 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 4234:     */         }
/* 4235:4023 */         return localObject5;
/* 4236:     */       }
/* 4237:     */       catch (TRANSIENT localTRANSIENT)
/* 4238:     */       {
/* 4239:4027 */         if (i == 10) {
/* 4240:4029 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4241:     */         }
/* 4242:     */       }
/* 4243:     */       catch (UserException localUserException)
/* 4244:     */       {
/* 4245:4034 */         local_Request.isRMI();
/* 4246:     */         
/* 4247:     */ 
/* 4248:4037 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4249:     */       }
/* 4250:     */       catch (SystemException localSystemException)
/* 4251:     */       {
/* 4252:4041 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4253:     */       }
/* 4254:     */       finally
/* 4255:     */       {
/* 4256:4045 */         if (local_Request != null) {
/* 4257:4047 */           local_Request.close();
/* 4258:     */         }
/* 4259:4049 */         if (bool1) {
/* 4260:4050 */           localLocalStack.pop(localLocalFrame);
/* 4261:     */         }
/* 4262:4051 */         localLocalStack.setArgsOnLocal(bool2);
/* 4263:     */       }
/* 4264:     */     }
/* 4265:     */   }
/* 4266:     */   
/* 4267:     */   public PayeeInfo[] updatePayee(PayeeInfo paramPayeeInfo, int paramInt)
/* 4268:     */     throws java.rmi.RemoteException
/* 4269:     */   {
/* 4270:4061 */     for (int i = 1;; i++)
/* 4271:     */     {
/* 4272:4063 */       _Request local_Request = null;
/* 4273:4064 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4274:4065 */       boolean bool1 = false;
/* 4275:4066 */       LocalFrame localLocalFrame = null;
/* 4276:4067 */       boolean bool2 = false;
/* 4277:     */       try
/* 4278:     */       {
/* 4279:4070 */         local_Request = __request("updatePayee__PayeeInfo__long", "updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__long");
/* 4280:4071 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4281:4072 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4282:4073 */         localLocalStack.setArgsOnLocal(bool1);
/* 4283:4075 */         if (bool1)
/* 4284:     */         {
/* 4285:4077 */           localLocalFrame = new LocalFrame(2);
/* 4286:4078 */           localLocalStack.push(localLocalFrame);
/* 4287:     */         }
/* 4288:4080 */         if (!bool1)
/* 4289:     */         {
/* 4290:4082 */           localObject4 = local_Request.getOutputStream();
/* 4291:4083 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 4292:4084 */           ((OutputStream)localObject4).write_long(paramInt);
/* 4293:     */         }
/* 4294:     */         else
/* 4295:     */         {
/* 4296:4088 */           localObject4 = local_Request.getOutputStream();
/* 4297:4089 */           localLocalFrame.add(paramPayeeInfo);
/* 4298:4090 */           localLocalFrame.add(paramInt);
/* 4299:     */         }
/* 4300:4092 */         local_Request.invoke();
/* 4301:     */         Object localObject5;
/* 4302:     */         Object localObject1;
/* 4303:4093 */         if (bool1)
/* 4304:     */         {
/* 4305:4095 */           localObject4 = null;
/* 4306:4096 */           localObject5 = localLocalFrame.getResult();
/* 4307:4097 */           if (localObject5 != null) {
/* 4308:4099 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 4309:     */           }
/* 4310:4101 */           return localObject4;
/* 4311:     */         }
/* 4312:4103 */         Object localObject4 = local_Request.getInputStream();
/* 4313:4105 */         if (local_Request.isRMI()) {
/* 4314:4105 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 4315:     */         } else {
/* 4316:4105 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 4317:     */         }
/* 4318:4106 */         return localObject5;
/* 4319:     */       }
/* 4320:     */       catch (TRANSIENT localTRANSIENT)
/* 4321:     */       {
/* 4322:4110 */         if (i == 10) {
/* 4323:4112 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4324:     */         }
/* 4325:     */       }
/* 4326:     */       catch (UserException localUserException)
/* 4327:     */       {
/* 4328:4117 */         local_Request.isRMI();
/* 4329:     */         
/* 4330:     */ 
/* 4331:4120 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4332:     */       }
/* 4333:     */       catch (SystemException localSystemException)
/* 4334:     */       {
/* 4335:4124 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4336:     */       }
/* 4337:     */       finally
/* 4338:     */       {
/* 4339:4128 */         if (local_Request != null) {
/* 4340:4130 */           local_Request.close();
/* 4341:     */         }
/* 4342:4132 */         if (bool1) {
/* 4343:4133 */           localLocalStack.pop(localLocalFrame);
/* 4344:     */         }
/* 4345:4134 */         localLocalStack.setArgsOnLocal(bool2);
/* 4346:     */       }
/* 4347:     */     }
/* 4348:     */   }
/* 4349:     */   
/* 4350:     */   public void updatePayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
/* 4351:     */     throws java.rmi.RemoteException
/* 4352:     */   {
/* 4353:4144 */     for (int i = 1;; i++)
/* 4354:     */     {
/* 4355:4146 */       _Request local_Request = null;
/* 4356:4147 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4357:4148 */       boolean bool1 = false;
/* 4358:4149 */       LocalFrame localLocalFrame = null;
/* 4359:4150 */       boolean bool2 = false;
/* 4360:     */       try
/* 4361:     */       {
/* 4362:4153 */         local_Request = __request("updatePayee__PayeeInfo__PayeeRouteInfo", "updatePayee__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeInfo__org_omg_boxedIDL_com_ffusion_ffs_bpw_interfaces_PayeeRouteInfo");
/* 4363:4154 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4364:4155 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4365:4156 */         localLocalStack.setArgsOnLocal(bool1);
/* 4366:4158 */         if (bool1)
/* 4367:     */         {
/* 4368:4160 */           localLocalFrame = new LocalFrame(2);
/* 4369:4161 */           localLocalStack.push(localLocalFrame);
/* 4370:     */         }
/* 4371:     */         OutputStream localOutputStream;
/* 4372:4163 */         if (!bool1)
/* 4373:     */         {
/* 4374:4165 */           localOutputStream = local_Request.getOutputStream();
/* 4375:4166 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 4376:4167 */           local_Request.write_value(paramPayeeRouteInfo, PayeeRouteInfo.class);
/* 4377:     */         }
/* 4378:     */         else
/* 4379:     */         {
/* 4380:4171 */           localOutputStream = local_Request.getOutputStream();
/* 4381:4172 */           localLocalFrame.add(paramPayeeInfo);
/* 4382:4173 */           localLocalFrame.add(paramPayeeRouteInfo);
/* 4383:     */         }
/* 4384:4175 */         local_Request.invoke();
/* 4385:4176 */         return;
/* 4386:     */       }
/* 4387:     */       catch (TRANSIENT localTRANSIENT)
/* 4388:     */       {
/* 4389:4180 */         if (i == 10) {
/* 4390:4182 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4391:     */         }
/* 4392:     */       }
/* 4393:     */       catch (UserException localUserException)
/* 4394:     */       {
/* 4395:4187 */         local_Request.isRMI();
/* 4396:     */         
/* 4397:     */ 
/* 4398:4190 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4399:     */       }
/* 4400:     */       catch (SystemException localSystemException)
/* 4401:     */       {
/* 4402:4194 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4403:     */       }
/* 4404:     */       finally
/* 4405:     */       {
/* 4406:4198 */         if (local_Request != null) {
/* 4407:4200 */           local_Request.close();
/* 4408:     */         }
/* 4409:4202 */         if (bool1) {
/* 4410:4203 */           localLocalStack.pop(localLocalFrame);
/* 4411:     */         }
/* 4412:4204 */         localLocalStack.setArgsOnLocal(bool2);
/* 4413:     */       }
/* 4414:     */     }
/* 4415:     */   }
/* 4416:     */   
/* 4417:     */   public void deletePayee(String paramString)
/* 4418:     */     throws java.rmi.RemoteException
/* 4419:     */   {
/* 4420:4213 */     for (int i = 1;; i++)
/* 4421:     */     {
/* 4422:4215 */       _Request local_Request = null;
/* 4423:4216 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4424:4217 */       boolean bool1 = false;
/* 4425:4218 */       LocalFrame localLocalFrame = null;
/* 4426:4219 */       boolean bool2 = false;
/* 4427:     */       try
/* 4428:     */       {
/* 4429:4222 */         local_Request = __request("deletePayee");
/* 4430:4223 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4431:4224 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4432:4225 */         localLocalStack.setArgsOnLocal(bool1);
/* 4433:4227 */         if (bool1)
/* 4434:     */         {
/* 4435:4229 */           localLocalFrame = new LocalFrame(1);
/* 4436:4230 */           localLocalStack.push(localLocalFrame);
/* 4437:     */         }
/* 4438:     */         OutputStream localOutputStream;
/* 4439:4232 */         if (!bool1)
/* 4440:     */         {
/* 4441:4234 */           localOutputStream = local_Request.getOutputStream();
/* 4442:4235 */           local_Request.write_string(paramString);
/* 4443:     */         }
/* 4444:     */         else
/* 4445:     */         {
/* 4446:4239 */           localOutputStream = local_Request.getOutputStream();
/* 4447:4240 */           localLocalFrame.add(paramString);
/* 4448:     */         }
/* 4449:4242 */         local_Request.invoke();
/* 4450:4243 */         return;
/* 4451:     */       }
/* 4452:     */       catch (TRANSIENT localTRANSIENT)
/* 4453:     */       {
/* 4454:4247 */         if (i == 10) {
/* 4455:4249 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4456:     */         }
/* 4457:     */       }
/* 4458:     */       catch (UserException localUserException)
/* 4459:     */       {
/* 4460:4254 */         local_Request.isRMI();
/* 4461:     */         
/* 4462:     */ 
/* 4463:4257 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4464:     */       }
/* 4465:     */       catch (SystemException localSystemException)
/* 4466:     */       {
/* 4467:4261 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4468:     */       }
/* 4469:     */       finally
/* 4470:     */       {
/* 4471:4265 */         if (local_Request != null) {
/* 4472:4267 */           local_Request.close();
/* 4473:     */         }
/* 4474:4269 */         if (bool1) {
/* 4475:4270 */           localLocalStack.pop(localLocalFrame);
/* 4476:     */         }
/* 4477:4271 */         localLocalStack.setArgsOnLocal(bool2);
/* 4478:     */       }
/* 4479:     */     }
/* 4480:     */   }
/* 4481:     */   
/* 4482:     */   public void deletePayees(String[] paramArrayOfString)
/* 4483:     */     throws java.rmi.RemoteException
/* 4484:     */   {
/* 4485:4280 */     for (int i = 1;; i++)
/* 4486:     */     {
/* 4487:4282 */       _Request local_Request = null;
/* 4488:4283 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4489:4284 */       boolean bool1 = false;
/* 4490:4285 */       LocalFrame localLocalFrame = null;
/* 4491:4286 */       boolean bool2 = false;
/* 4492:     */       try
/* 4493:     */       {
/* 4494:4289 */         local_Request = __request("deletePayees");
/* 4495:4290 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4496:4291 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4497:4292 */         localLocalStack.setArgsOnLocal(bool1);
/* 4498:4294 */         if (bool1)
/* 4499:     */         {
/* 4500:4296 */           localLocalFrame = new LocalFrame(1);
/* 4501:4297 */           localLocalStack.push(localLocalFrame);
/* 4502:     */         }
/* 4503:     */         OutputStream localOutputStream;
/* 4504:4299 */         if (!bool1)
/* 4505:     */         {
/* 4506:4301 */           localOutputStream = local_Request.getOutputStream();
/* 4507:4302 */           if (local_Request.isRMI()) {
/* 4508:4302 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 4509:     */           } else {
/* 4510:4302 */             StringSeqHelper.write(localOutputStream, paramArrayOfString);
/* 4511:     */           }
/* 4512:     */         }
/* 4513:     */         else
/* 4514:     */         {
/* 4515:4306 */           localOutputStream = local_Request.getOutputStream();
/* 4516:4307 */           localLocalFrame.add(paramArrayOfString);
/* 4517:     */         }
/* 4518:4309 */         local_Request.invoke();
/* 4519:4310 */         return;
/* 4520:     */       }
/* 4521:     */       catch (TRANSIENT localTRANSIENT)
/* 4522:     */       {
/* 4523:4314 */         if (i == 10) {
/* 4524:4316 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4525:     */         }
/* 4526:     */       }
/* 4527:     */       catch (UserException localUserException)
/* 4528:     */       {
/* 4529:4321 */         local_Request.isRMI();
/* 4530:     */         
/* 4531:     */ 
/* 4532:4324 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4533:     */       }
/* 4534:     */       catch (SystemException localSystemException)
/* 4535:     */       {
/* 4536:4328 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4537:     */       }
/* 4538:     */       finally
/* 4539:     */       {
/* 4540:4332 */         if (local_Request != null) {
/* 4541:4334 */           local_Request.close();
/* 4542:     */         }
/* 4543:4336 */         if (bool1) {
/* 4544:4337 */           localLocalStack.pop(localLocalFrame);
/* 4545:     */         }
/* 4546:4338 */         localLocalStack.setArgsOnLocal(bool2);
/* 4547:     */       }
/* 4548:     */     }
/* 4549:     */   }
/* 4550:     */   
/* 4551:     */   public PayeeInfo findPayeeByID(String paramString)
/* 4552:     */     throws java.rmi.RemoteException
/* 4553:     */   {
/* 4554:4347 */     for (int i = 1;; i++)
/* 4555:     */     {
/* 4556:4349 */       _Request local_Request = null;
/* 4557:4350 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4558:4351 */       boolean bool1 = false;
/* 4559:4352 */       LocalFrame localLocalFrame = null;
/* 4560:4353 */       boolean bool2 = false;
/* 4561:     */       try
/* 4562:     */       {
/* 4563:4356 */         local_Request = __request("findPayeeByID");
/* 4564:4357 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4565:4358 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4566:4359 */         localLocalStack.setArgsOnLocal(bool1);
/* 4567:4361 */         if (bool1)
/* 4568:     */         {
/* 4569:4363 */           localLocalFrame = new LocalFrame(1);
/* 4570:4364 */           localLocalStack.push(localLocalFrame);
/* 4571:     */         }
/* 4572:4366 */         if (!bool1)
/* 4573:     */         {
/* 4574:4368 */           localObject4 = local_Request.getOutputStream();
/* 4575:4369 */           local_Request.write_string(paramString);
/* 4576:     */         }
/* 4577:     */         else
/* 4578:     */         {
/* 4579:4373 */           localObject4 = local_Request.getOutputStream();
/* 4580:4374 */           localLocalFrame.add(paramString);
/* 4581:     */         }
/* 4582:4376 */         local_Request.invoke();
/* 4583:     */         Object localObject1;
/* 4584:4377 */         if (bool1)
/* 4585:     */         {
/* 4586:4379 */           localObject4 = null;
/* 4587:4380 */           localObject5 = localLocalFrame.getResult();
/* 4588:4381 */           if (localObject5 != null) {
/* 4589:4383 */             localObject4 = (PayeeInfo)ObjectVal.clone(localObject5);
/* 4590:     */           }
/* 4591:4385 */           return localObject4;
/* 4592:     */         }
/* 4593:4387 */         Object localObject4 = local_Request.getInputStream();
/* 4594:     */         
/* 4595:4389 */         Object localObject5 = (PayeeInfo)local_Request.read_value(PayeeInfo.class);
/* 4596:4390 */         return localObject5;
/* 4597:     */       }
/* 4598:     */       catch (TRANSIENT localTRANSIENT)
/* 4599:     */       {
/* 4600:4394 */         if (i == 10) {
/* 4601:4396 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4602:     */         }
/* 4603:     */       }
/* 4604:     */       catch (UserException localUserException)
/* 4605:     */       {
/* 4606:4401 */         local_Request.isRMI();
/* 4607:     */         
/* 4608:     */ 
/* 4609:4404 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4610:     */       }
/* 4611:     */       catch (SystemException localSystemException)
/* 4612:     */       {
/* 4613:4408 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4614:     */       }
/* 4615:     */       finally
/* 4616:     */       {
/* 4617:4412 */         if (local_Request != null) {
/* 4618:4414 */           local_Request.close();
/* 4619:     */         }
/* 4620:4416 */         if (bool1) {
/* 4621:4417 */           localLocalStack.pop(localLocalFrame);
/* 4622:     */         }
/* 4623:4418 */         localLocalStack.setArgsOnLocal(bool2);
/* 4624:     */       }
/* 4625:     */     }
/* 4626:     */   }
/* 4627:     */   
/* 4628:     */   public void setPayeeStatus(String paramString1, String paramString2)
/* 4629:     */     throws java.rmi.RemoteException
/* 4630:     */   {
/* 4631:4428 */     for (int i = 1;; i++)
/* 4632:     */     {
/* 4633:4430 */       _Request local_Request = null;
/* 4634:4431 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4635:4432 */       boolean bool1 = false;
/* 4636:4433 */       LocalFrame localLocalFrame = null;
/* 4637:4434 */       boolean bool2 = false;
/* 4638:     */       try
/* 4639:     */       {
/* 4640:4437 */         local_Request = __request("setPayeeStatus");
/* 4641:4438 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4642:4439 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4643:4440 */         localLocalStack.setArgsOnLocal(bool1);
/* 4644:4442 */         if (bool1)
/* 4645:     */         {
/* 4646:4444 */           localLocalFrame = new LocalFrame(2);
/* 4647:4445 */           localLocalStack.push(localLocalFrame);
/* 4648:     */         }
/* 4649:     */         OutputStream localOutputStream;
/* 4650:4447 */         if (!bool1)
/* 4651:     */         {
/* 4652:4449 */           localOutputStream = local_Request.getOutputStream();
/* 4653:4450 */           local_Request.write_string(paramString1);
/* 4654:4451 */           local_Request.write_string(paramString2);
/* 4655:     */         }
/* 4656:     */         else
/* 4657:     */         {
/* 4658:4455 */           localOutputStream = local_Request.getOutputStream();
/* 4659:4456 */           localLocalFrame.add(paramString1);
/* 4660:4457 */           localLocalFrame.add(paramString2);
/* 4661:     */         }
/* 4662:4459 */         local_Request.invoke();
/* 4663:4460 */         return;
/* 4664:     */       }
/* 4665:     */       catch (TRANSIENT localTRANSIENT)
/* 4666:     */       {
/* 4667:4464 */         if (i == 10) {
/* 4668:4466 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4669:     */         }
/* 4670:     */       }
/* 4671:     */       catch (UserException localUserException)
/* 4672:     */       {
/* 4673:4471 */         local_Request.isRMI();
/* 4674:     */         
/* 4675:     */ 
/* 4676:4474 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4677:     */       }
/* 4678:     */       catch (SystemException localSystemException)
/* 4679:     */       {
/* 4680:4478 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4681:     */       }
/* 4682:     */       finally
/* 4683:     */       {
/* 4684:4482 */         if (local_Request != null) {
/* 4685:4484 */           local_Request.close();
/* 4686:     */         }
/* 4687:4486 */         if (bool1) {
/* 4688:4487 */           localLocalStack.pop(localLocalFrame);
/* 4689:     */         }
/* 4690:4488 */         localLocalStack.setArgsOnLocal(bool2);
/* 4691:     */       }
/* 4692:     */     }
/* 4693:     */   }
/* 4694:     */   
/* 4695:     */   public int getSmartDate(int paramInt)
/* 4696:     */     throws java.rmi.RemoteException
/* 4697:     */   {
/* 4698:4497 */     for (int i = 1;; i++)
/* 4699:     */     {
/* 4700:4499 */       _Request local_Request = null;
/* 4701:4500 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4702:4501 */       boolean bool = false;
/* 4703:     */       try
/* 4704:     */       {
/* 4705:4504 */         local_Request = __request("getSmartDate");
/* 4706:4505 */         bool = localLocalStack.isArgsOnLocal();
/* 4707:4506 */         localLocalStack.setArgsOnLocal(false);
/* 4708:4507 */         OutputStream localOutputStream = local_Request.getOutputStream();
/* 4709:4508 */         localOutputStream.write_long(paramInt);
/* 4710:4509 */         local_Request.invoke();
/* 4711:4510 */         InputStream localInputStream = local_Request.getInputStream();
/* 4712:     */         
/* 4713:4512 */         int k = localInputStream.read_long();
/* 4714:4513 */         return k;
/* 4715:     */       }
/* 4716:     */       catch (TRANSIENT localTRANSIENT)
/* 4717:     */       {
/* 4718:4517 */         if (i == 10) {
/* 4719:4519 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4720:     */         }
/* 4721:     */       }
/* 4722:     */       catch (UserException localUserException)
/* 4723:     */       {
/* 4724:4524 */         local_Request.isRMI();
/* 4725:     */         
/* 4726:     */ 
/* 4727:4527 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4728:     */       }
/* 4729:     */       catch (SystemException localSystemException)
/* 4730:     */       {
/* 4731:4531 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4732:     */       }
/* 4733:     */       finally
/* 4734:     */       {
/* 4735:4535 */         if (local_Request != null) {
/* 4736:4537 */           local_Request.close();
/* 4737:     */         }
/* 4738:4539 */         localLocalStack.setArgsOnLocal(bool);
/* 4739:     */       }
/* 4740:     */     }
/* 4741:     */   }
/* 4742:     */   
/* 4743:     */   public PayeeInfo[] getGlobalPayees(String paramString, int paramInt)
/* 4744:     */     throws java.rmi.RemoteException
/* 4745:     */   {
/* 4746:4549 */     for (int i = 1;; i++)
/* 4747:     */     {
/* 4748:4551 */       _Request local_Request = null;
/* 4749:4552 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4750:4553 */       boolean bool1 = false;
/* 4751:4554 */       LocalFrame localLocalFrame = null;
/* 4752:4555 */       boolean bool2 = false;
/* 4753:     */       try
/* 4754:     */       {
/* 4755:4558 */         local_Request = __request("getGlobalPayees");
/* 4756:4559 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4757:4560 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4758:4561 */         localLocalStack.setArgsOnLocal(bool1);
/* 4759:4563 */         if (bool1)
/* 4760:     */         {
/* 4761:4565 */           localLocalFrame = new LocalFrame(2);
/* 4762:4566 */           localLocalStack.push(localLocalFrame);
/* 4763:     */         }
/* 4764:4568 */         if (!bool1)
/* 4765:     */         {
/* 4766:4570 */           localObject4 = local_Request.getOutputStream();
/* 4767:4571 */           local_Request.write_string(paramString);
/* 4768:4572 */           ((OutputStream)localObject4).write_long(paramInt);
/* 4769:     */         }
/* 4770:     */         else
/* 4771:     */         {
/* 4772:4576 */           localObject4 = local_Request.getOutputStream();
/* 4773:4577 */           localLocalFrame.add(paramString);
/* 4774:4578 */           localLocalFrame.add(paramInt);
/* 4775:     */         }
/* 4776:4580 */         local_Request.invoke();
/* 4777:     */         Object localObject5;
/* 4778:     */         Object localObject1;
/* 4779:4581 */         if (bool1)
/* 4780:     */         {
/* 4781:4583 */           localObject4 = null;
/* 4782:4584 */           localObject5 = localLocalFrame.getResult();
/* 4783:4585 */           if (localObject5 != null) {
/* 4784:4587 */             localObject4 = (PayeeInfo[])ObjectVal.clone(localObject5);
/* 4785:     */           }
/* 4786:4589 */           return localObject4;
/* 4787:     */         }
/* 4788:4591 */         Object localObject4 = local_Request.getInputStream();
/* 4789:4593 */         if (local_Request.isRMI()) {
/* 4790:4593 */           localObject5 = (PayeeInfo[])local_Request.read_value(new PayeeInfo[0].getClass());
/* 4791:     */         } else {
/* 4792:4593 */           localObject5 = PayeeInfoSeqHelper.read((InputStream)localObject4);
/* 4793:     */         }
/* 4794:4594 */         return localObject5;
/* 4795:     */       }
/* 4796:     */       catch (TRANSIENT localTRANSIENT)
/* 4797:     */       {
/* 4798:4598 */         if (i == 10) {
/* 4799:4600 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4800:     */         }
/* 4801:     */       }
/* 4802:     */       catch (UserException localUserException)
/* 4803:     */       {
/* 4804:4605 */         local_Request.isRMI();
/* 4805:     */         
/* 4806:     */ 
/* 4807:4608 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4808:     */       }
/* 4809:     */       catch (SystemException localSystemException)
/* 4810:     */       {
/* 4811:4612 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4812:     */       }
/* 4813:     */       finally
/* 4814:     */       {
/* 4815:4616 */         if (local_Request != null) {
/* 4816:4618 */           local_Request.close();
/* 4817:     */         }
/* 4818:4620 */         if (bool1) {
/* 4819:4621 */           localLocalStack.pop(localLocalFrame);
/* 4820:     */         }
/* 4821:4622 */         localLocalStack.setArgsOnLocal(bool2);
/* 4822:     */       }
/* 4823:     */     }
/* 4824:     */   }
/* 4825:     */   
/* 4826:     */   public String addPayee(PayeeInfo paramPayeeInfo, int paramInt)
/* 4827:     */     throws java.rmi.RemoteException
/* 4828:     */   {
/* 4829:4632 */     for (int i = 1;; i++)
/* 4830:     */     {
/* 4831:4634 */       _Request local_Request = null;
/* 4832:4635 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4833:4636 */       boolean bool1 = false;
/* 4834:4637 */       LocalFrame localLocalFrame = null;
/* 4835:4638 */       boolean bool2 = false;
/* 4836:     */       try
/* 4837:     */       {
/* 4838:4641 */         local_Request = __request("addPayee");
/* 4839:4642 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4840:4643 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4841:4644 */         localLocalStack.setArgsOnLocal(bool1);
/* 4842:4646 */         if (bool1)
/* 4843:     */         {
/* 4844:4648 */           localLocalFrame = new LocalFrame(2);
/* 4845:4649 */           localLocalStack.push(localLocalFrame);
/* 4846:     */         }
/* 4847:4651 */         if (!bool1)
/* 4848:     */         {
/* 4849:4653 */           localObject4 = local_Request.getOutputStream();
/* 4850:4654 */           local_Request.write_value(paramPayeeInfo, PayeeInfo.class);
/* 4851:4655 */           ((OutputStream)localObject4).write_long(paramInt);
/* 4852:     */         }
/* 4853:     */         else
/* 4854:     */         {
/* 4855:4659 */           localObject4 = local_Request.getOutputStream();
/* 4856:4660 */           localLocalFrame.add(paramPayeeInfo);
/* 4857:4661 */           localLocalFrame.add(paramInt);
/* 4858:     */         }
/* 4859:4663 */         local_Request.invoke();
/* 4860:     */         Object localObject1;
/* 4861:4664 */         if (bool1)
/* 4862:     */         {
/* 4863:4666 */           localObject4 = null;
/* 4864:4667 */           localObject4 = (String)localLocalFrame.getResult();
/* 4865:4668 */           return localObject4;
/* 4866:     */         }
/* 4867:4670 */         Object localObject4 = local_Request.getInputStream();
/* 4868:     */         
/* 4869:4672 */         String str = local_Request.read_string();
/* 4870:4673 */         return str;
/* 4871:     */       }
/* 4872:     */       catch (TRANSIENT localTRANSIENT)
/* 4873:     */       {
/* 4874:4677 */         if (i == 10) {
/* 4875:4679 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4876:     */         }
/* 4877:     */       }
/* 4878:     */       catch (UserException localUserException)
/* 4879:     */       {
/* 4880:4684 */         local_Request.isRMI();
/* 4881:     */         
/* 4882:     */ 
/* 4883:4687 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4884:     */       }
/* 4885:     */       catch (SystemException localSystemException)
/* 4886:     */       {
/* 4887:4691 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4888:     */       }
/* 4889:     */       finally
/* 4890:     */       {
/* 4891:4695 */         if (local_Request != null) {
/* 4892:4697 */           local_Request.close();
/* 4893:     */         }
/* 4894:4699 */         if (bool1) {
/* 4895:4700 */           localLocalStack.pop(localLocalFrame);
/* 4896:     */         }
/* 4897:4701 */         localLocalStack.setArgsOnLocal(bool2);
/* 4898:     */       }
/* 4899:     */     }
/* 4900:     */   }
/* 4901:     */   
/* 4902:     */   public int addConsumerCrossRef(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
/* 4903:     */     throws java.rmi.RemoteException
/* 4904:     */   {
/* 4905:4710 */     for (int i = 1;; i++)
/* 4906:     */     {
/* 4907:4712 */       _Request local_Request = null;
/* 4908:4713 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4909:4714 */       boolean bool1 = false;
/* 4910:4715 */       LocalFrame localLocalFrame = null;
/* 4911:4716 */       boolean bool2 = false;
/* 4912:     */       try
/* 4913:     */       {
/* 4914:4719 */         local_Request = __request("addConsumerCrossRef");
/* 4915:4720 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4916:4721 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4917:4722 */         localLocalStack.setArgsOnLocal(bool1);
/* 4918:4724 */         if (bool1)
/* 4919:     */         {
/* 4920:4726 */           localLocalFrame = new LocalFrame(1);
/* 4921:4727 */           localLocalStack.push(localLocalFrame);
/* 4922:     */         }
/* 4923:     */         OutputStream localOutputStream;
/* 4924:4729 */         if (!bool1)
/* 4925:     */         {
/* 4926:4731 */           localOutputStream = local_Request.getOutputStream();
/* 4927:4732 */           if (local_Request.isRMI()) {
/* 4928:4732 */             local_Request.write_value(paramArrayOfConsumerCrossRefInfo, new ConsumerCrossRefInfo[0].getClass());
/* 4929:     */           } else {
/* 4930:4732 */             ConsumerCrossRefInfoSeqHelper.write(localOutputStream, paramArrayOfConsumerCrossRefInfo);
/* 4931:     */           }
/* 4932:     */         }
/* 4933:     */         else
/* 4934:     */         {
/* 4935:4736 */           localOutputStream = local_Request.getOutputStream();
/* 4936:4737 */           localLocalFrame.add(paramArrayOfConsumerCrossRefInfo);
/* 4937:     */         }
/* 4938:4739 */         local_Request.invoke();
/* 4939:     */         int j;
/* 4940:4740 */         if (bool1)
/* 4941:     */         {
/* 4942:4743 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/* 4943:4744 */           return k;
/* 4944:     */         }
/* 4945:4746 */         InputStream localInputStream = local_Request.getInputStream();
/* 4946:     */         
/* 4947:4748 */         int m = localInputStream.read_long();
/* 4948:4749 */         return m;
/* 4949:     */       }
/* 4950:     */       catch (TRANSIENT localTRANSIENT)
/* 4951:     */       {
/* 4952:4753 */         if (i == 10) {
/* 4953:4755 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 4954:     */         }
/* 4955:     */       }
/* 4956:     */       catch (UserException localUserException)
/* 4957:     */       {
/* 4958:4760 */         local_Request.isRMI();
/* 4959:     */         
/* 4960:     */ 
/* 4961:4763 */         throw new java.rmi.RemoteException(localUserException.type);
/* 4962:     */       }
/* 4963:     */       catch (SystemException localSystemException)
/* 4964:     */       {
/* 4965:4767 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 4966:     */       }
/* 4967:     */       finally
/* 4968:     */       {
/* 4969:4771 */         if (local_Request != null) {
/* 4970:4773 */           local_Request.close();
/* 4971:     */         }
/* 4972:4775 */         if (bool1) {
/* 4973:4776 */           localLocalStack.pop(localLocalFrame);
/* 4974:     */         }
/* 4975:4777 */         localLocalStack.setArgsOnLocal(bool2);
/* 4976:     */       }
/* 4977:     */     }
/* 4978:     */   }
/* 4979:     */   
/* 4980:     */   public int deleteConsumerCrossRef(ConsumerCrossRefInfo[] paramArrayOfConsumerCrossRefInfo)
/* 4981:     */     throws java.rmi.RemoteException
/* 4982:     */   {
/* 4983:4786 */     for (int i = 1;; i++)
/* 4984:     */     {
/* 4985:4788 */       _Request local_Request = null;
/* 4986:4789 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 4987:4790 */       boolean bool1 = false;
/* 4988:4791 */       LocalFrame localLocalFrame = null;
/* 4989:4792 */       boolean bool2 = false;
/* 4990:     */       try
/* 4991:     */       {
/* 4992:4795 */         local_Request = __request("deleteConsumerCrossRef");
/* 4993:4796 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 4994:4797 */         bool2 = localLocalStack.isArgsOnLocal();
/* 4995:4798 */         localLocalStack.setArgsOnLocal(bool1);
/* 4996:4800 */         if (bool1)
/* 4997:     */         {
/* 4998:4802 */           localLocalFrame = new LocalFrame(1);
/* 4999:4803 */           localLocalStack.push(localLocalFrame);
/* 5000:     */         }
/* 5001:     */         OutputStream localOutputStream;
/* 5002:4805 */         if (!bool1)
/* 5003:     */         {
/* 5004:4807 */           localOutputStream = local_Request.getOutputStream();
/* 5005:4808 */           if (local_Request.isRMI()) {
/* 5006:4808 */             local_Request.write_value(paramArrayOfConsumerCrossRefInfo, new ConsumerCrossRefInfo[0].getClass());
/* 5007:     */           } else {
/* 5008:4808 */             ConsumerCrossRefInfoSeqHelper.write(localOutputStream, paramArrayOfConsumerCrossRefInfo);
/* 5009:     */           }
/* 5010:     */         }
/* 5011:     */         else
/* 5012:     */         {
/* 5013:4812 */           localOutputStream = local_Request.getOutputStream();
/* 5014:4813 */           localLocalFrame.add(paramArrayOfConsumerCrossRefInfo);
/* 5015:     */         }
/* 5016:4815 */         local_Request.invoke();
/* 5017:     */         int j;
/* 5018:4816 */         if (bool1)
/* 5019:     */         {
/* 5020:4819 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/* 5021:4820 */           return k;
/* 5022:     */         }
/* 5023:4822 */         InputStream localInputStream = local_Request.getInputStream();
/* 5024:     */         
/* 5025:4824 */         int m = localInputStream.read_long();
/* 5026:4825 */         return m;
/* 5027:     */       }
/* 5028:     */       catch (TRANSIENT localTRANSIENT)
/* 5029:     */       {
/* 5030:4829 */         if (i == 10) {
/* 5031:4831 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5032:     */         }
/* 5033:     */       }
/* 5034:     */       catch (UserException localUserException)
/* 5035:     */       {
/* 5036:4836 */         local_Request.isRMI();
/* 5037:     */         
/* 5038:     */ 
/* 5039:4839 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5040:     */       }
/* 5041:     */       catch (SystemException localSystemException)
/* 5042:     */       {
/* 5043:4843 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5044:     */       }
/* 5045:     */       finally
/* 5046:     */       {
/* 5047:4847 */         if (local_Request != null) {
/* 5048:4849 */           local_Request.close();
/* 5049:     */         }
/* 5050:4851 */         if (bool1) {
/* 5051:4852 */           localLocalStack.pop(localLocalFrame);
/* 5052:     */         }
/* 5053:4853 */         localLocalStack.setArgsOnLocal(bool2);
/* 5054:     */       }
/* 5055:     */     }
/* 5056:     */   }
/* 5057:     */   
/* 5058:     */   public ConsumerCrossRefInfo[] getConsumerCrossRef(String[] paramArrayOfString)
/* 5059:     */     throws java.rmi.RemoteException
/* 5060:     */   {
/* 5061:4862 */     for (int i = 1;; i++)
/* 5062:     */     {
/* 5063:4864 */       _Request local_Request = null;
/* 5064:4865 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5065:4866 */       boolean bool1 = false;
/* 5066:4867 */       LocalFrame localLocalFrame = null;
/* 5067:4868 */       boolean bool2 = false;
/* 5068:     */       try
/* 5069:     */       {
/* 5070:4871 */         local_Request = __request("getConsumerCrossRef");
/* 5071:4872 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5072:4873 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5073:4874 */         localLocalStack.setArgsOnLocal(bool1);
/* 5074:4876 */         if (bool1)
/* 5075:     */         {
/* 5076:4878 */           localLocalFrame = new LocalFrame(1);
/* 5077:4879 */           localLocalStack.push(localLocalFrame);
/* 5078:     */         }
/* 5079:4881 */         if (!bool1)
/* 5080:     */         {
/* 5081:4883 */           localObject4 = local_Request.getOutputStream();
/* 5082:4884 */           if (local_Request.isRMI()) {
/* 5083:4884 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 5084:     */           } else {
/* 5085:4884 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 5086:     */           }
/* 5087:     */         }
/* 5088:     */         else
/* 5089:     */         {
/* 5090:4888 */           localObject4 = local_Request.getOutputStream();
/* 5091:4889 */           localLocalFrame.add(paramArrayOfString);
/* 5092:     */         }
/* 5093:4891 */         local_Request.invoke();
/* 5094:     */         Object localObject5;
/* 5095:     */         Object localObject1;
/* 5096:4892 */         if (bool1)
/* 5097:     */         {
/* 5098:4894 */           localObject4 = null;
/* 5099:4895 */           localObject5 = localLocalFrame.getResult();
/* 5100:4896 */           if (localObject5 != null) {
/* 5101:4898 */             localObject4 = (ConsumerCrossRefInfo[])ObjectVal.clone(localObject5);
/* 5102:     */           }
/* 5103:4900 */           return localObject4;
/* 5104:     */         }
/* 5105:4902 */         Object localObject4 = local_Request.getInputStream();
/* 5106:4904 */         if (local_Request.isRMI()) {
/* 5107:4904 */           localObject5 = (ConsumerCrossRefInfo[])local_Request.read_value(new ConsumerCrossRefInfo[0].getClass());
/* 5108:     */         } else {
/* 5109:4904 */           localObject5 = ConsumerCrossRefInfoSeqHelper.read((InputStream)localObject4);
/* 5110:     */         }
/* 5111:4905 */         return localObject5;
/* 5112:     */       }
/* 5113:     */       catch (TRANSIENT localTRANSIENT)
/* 5114:     */       {
/* 5115:4909 */         if (i == 10) {
/* 5116:4911 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5117:     */         }
/* 5118:     */       }
/* 5119:     */       catch (UserException localUserException)
/* 5120:     */       {
/* 5121:4916 */         local_Request.isRMI();
/* 5122:     */         
/* 5123:     */ 
/* 5124:4919 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5125:     */       }
/* 5126:     */       catch (SystemException localSystemException)
/* 5127:     */       {
/* 5128:4923 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5129:     */       }
/* 5130:     */       finally
/* 5131:     */       {
/* 5132:4927 */         if (local_Request != null) {
/* 5133:4929 */           local_Request.close();
/* 5134:     */         }
/* 5135:4931 */         if (bool1) {
/* 5136:4932 */           localLocalStack.pop(localLocalFrame);
/* 5137:     */         }
/* 5138:4933 */         localLocalStack.setArgsOnLocal(bool2);
/* 5139:     */       }
/* 5140:     */     }
/* 5141:     */   }
/* 5142:     */   
/* 5143:     */   public int addCustomerBankInfo(CustomerBankInfo[] paramArrayOfCustomerBankInfo)
/* 5144:     */     throws java.rmi.RemoteException
/* 5145:     */   {
/* 5146:4942 */     for (int i = 1;; i++)
/* 5147:     */     {
/* 5148:4944 */       _Request local_Request = null;
/* 5149:4945 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5150:4946 */       boolean bool1 = false;
/* 5151:4947 */       LocalFrame localLocalFrame = null;
/* 5152:4948 */       boolean bool2 = false;
/* 5153:     */       try
/* 5154:     */       {
/* 5155:4951 */         local_Request = __request("addCustomerBankInfo");
/* 5156:4952 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5157:4953 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5158:4954 */         localLocalStack.setArgsOnLocal(bool1);
/* 5159:4956 */         if (bool1)
/* 5160:     */         {
/* 5161:4958 */           localLocalFrame = new LocalFrame(1);
/* 5162:4959 */           localLocalStack.push(localLocalFrame);
/* 5163:     */         }
/* 5164:     */         OutputStream localOutputStream;
/* 5165:4961 */         if (!bool1)
/* 5166:     */         {
/* 5167:4963 */           localOutputStream = local_Request.getOutputStream();
/* 5168:4964 */           if (local_Request.isRMI()) {
/* 5169:4964 */             local_Request.write_value(paramArrayOfCustomerBankInfo, new CustomerBankInfo[0].getClass());
/* 5170:     */           } else {
/* 5171:4964 */             CustomerBankInfoSeqHelper.write(localOutputStream, paramArrayOfCustomerBankInfo);
/* 5172:     */           }
/* 5173:     */         }
/* 5174:     */         else
/* 5175:     */         {
/* 5176:4968 */           localOutputStream = local_Request.getOutputStream();
/* 5177:4969 */           localLocalFrame.add(paramArrayOfCustomerBankInfo);
/* 5178:     */         }
/* 5179:4971 */         local_Request.invoke();
/* 5180:     */         int j;
/* 5181:4972 */         if (bool1)
/* 5182:     */         {
/* 5183:4975 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/* 5184:4976 */           return k;
/* 5185:     */         }
/* 5186:4978 */         InputStream localInputStream = local_Request.getInputStream();
/* 5187:     */         
/* 5188:4980 */         int m = localInputStream.read_long();
/* 5189:4981 */         return m;
/* 5190:     */       }
/* 5191:     */       catch (TRANSIENT localTRANSIENT)
/* 5192:     */       {
/* 5193:4985 */         if (i == 10) {
/* 5194:4987 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5195:     */         }
/* 5196:     */       }
/* 5197:     */       catch (UserException localUserException)
/* 5198:     */       {
/* 5199:4992 */         local_Request.isRMI();
/* 5200:     */         
/* 5201:     */ 
/* 5202:4995 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5203:     */       }
/* 5204:     */       catch (SystemException localSystemException)
/* 5205:     */       {
/* 5206:4999 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5207:     */       }
/* 5208:     */       finally
/* 5209:     */       {
/* 5210:5003 */         if (local_Request != null) {
/* 5211:5005 */           local_Request.close();
/* 5212:     */         }
/* 5213:5007 */         if (bool1) {
/* 5214:5008 */           localLocalStack.pop(localLocalFrame);
/* 5215:     */         }
/* 5216:5009 */         localLocalStack.setArgsOnLocal(bool2);
/* 5217:     */       }
/* 5218:     */     }
/* 5219:     */   }
/* 5220:     */   
/* 5221:     */   public int deleteCustomerBankInfo(CustomerBankInfo[] paramArrayOfCustomerBankInfo)
/* 5222:     */     throws java.rmi.RemoteException
/* 5223:     */   {
/* 5224:5018 */     for (int i = 1;; i++)
/* 5225:     */     {
/* 5226:5020 */       _Request local_Request = null;
/* 5227:5021 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5228:5022 */       boolean bool1 = false;
/* 5229:5023 */       LocalFrame localLocalFrame = null;
/* 5230:5024 */       boolean bool2 = false;
/* 5231:     */       try
/* 5232:     */       {
/* 5233:5027 */         local_Request = __request("deleteCustomerBankInfo");
/* 5234:5028 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5235:5029 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5236:5030 */         localLocalStack.setArgsOnLocal(bool1);
/* 5237:5032 */         if (bool1)
/* 5238:     */         {
/* 5239:5034 */           localLocalFrame = new LocalFrame(1);
/* 5240:5035 */           localLocalStack.push(localLocalFrame);
/* 5241:     */         }
/* 5242:     */         OutputStream localOutputStream;
/* 5243:5037 */         if (!bool1)
/* 5244:     */         {
/* 5245:5039 */           localOutputStream = local_Request.getOutputStream();
/* 5246:5040 */           if (local_Request.isRMI()) {
/* 5247:5040 */             local_Request.write_value(paramArrayOfCustomerBankInfo, new CustomerBankInfo[0].getClass());
/* 5248:     */           } else {
/* 5249:5040 */             CustomerBankInfoSeqHelper.write(localOutputStream, paramArrayOfCustomerBankInfo);
/* 5250:     */           }
/* 5251:     */         }
/* 5252:     */         else
/* 5253:     */         {
/* 5254:5044 */           localOutputStream = local_Request.getOutputStream();
/* 5255:5045 */           localLocalFrame.add(paramArrayOfCustomerBankInfo);
/* 5256:     */         }
/* 5257:5047 */         local_Request.invoke();
/* 5258:     */         int j;
/* 5259:5048 */         if (bool1)
/* 5260:     */         {
/* 5261:5051 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/* 5262:5052 */           return k;
/* 5263:     */         }
/* 5264:5054 */         InputStream localInputStream = local_Request.getInputStream();
/* 5265:     */         
/* 5266:5056 */         int m = localInputStream.read_long();
/* 5267:5057 */         return m;
/* 5268:     */       }
/* 5269:     */       catch (TRANSIENT localTRANSIENT)
/* 5270:     */       {
/* 5271:5061 */         if (i == 10) {
/* 5272:5063 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5273:     */         }
/* 5274:     */       }
/* 5275:     */       catch (UserException localUserException)
/* 5276:     */       {
/* 5277:5068 */         local_Request.isRMI();
/* 5278:     */         
/* 5279:     */ 
/* 5280:5071 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5281:     */       }
/* 5282:     */       catch (SystemException localSystemException)
/* 5283:     */       {
/* 5284:5075 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5285:     */       }
/* 5286:     */       finally
/* 5287:     */       {
/* 5288:5079 */         if (local_Request != null) {
/* 5289:5081 */           local_Request.close();
/* 5290:     */         }
/* 5291:5083 */         if (bool1) {
/* 5292:5084 */           localLocalStack.pop(localLocalFrame);
/* 5293:     */         }
/* 5294:5085 */         localLocalStack.setArgsOnLocal(bool2);
/* 5295:     */       }
/* 5296:     */     }
/* 5297:     */   }
/* 5298:     */   
/* 5299:     */   public CustomerBankInfo[] getCustomerBankInfo(String[] paramArrayOfString)
/* 5300:     */     throws java.rmi.RemoteException
/* 5301:     */   {
/* 5302:5094 */     for (int i = 1;; i++)
/* 5303:     */     {
/* 5304:5096 */       _Request local_Request = null;
/* 5305:5097 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5306:5098 */       boolean bool1 = false;
/* 5307:5099 */       LocalFrame localLocalFrame = null;
/* 5308:5100 */       boolean bool2 = false;
/* 5309:     */       try
/* 5310:     */       {
/* 5311:5103 */         local_Request = __request("getCustomerBankInfo");
/* 5312:5104 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5313:5105 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5314:5106 */         localLocalStack.setArgsOnLocal(bool1);
/* 5315:5108 */         if (bool1)
/* 5316:     */         {
/* 5317:5110 */           localLocalFrame = new LocalFrame(1);
/* 5318:5111 */           localLocalStack.push(localLocalFrame);
/* 5319:     */         }
/* 5320:5113 */         if (!bool1)
/* 5321:     */         {
/* 5322:5115 */           localObject4 = local_Request.getOutputStream();
/* 5323:5116 */           if (local_Request.isRMI()) {
/* 5324:5116 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 5325:     */           } else {
/* 5326:5116 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 5327:     */           }
/* 5328:     */         }
/* 5329:     */         else
/* 5330:     */         {
/* 5331:5120 */           localObject4 = local_Request.getOutputStream();
/* 5332:5121 */           localLocalFrame.add(paramArrayOfString);
/* 5333:     */         }
/* 5334:5123 */         local_Request.invoke();
/* 5335:     */         Object localObject5;
/* 5336:     */         Object localObject1;
/* 5337:5124 */         if (bool1)
/* 5338:     */         {
/* 5339:5126 */           localObject4 = null;
/* 5340:5127 */           localObject5 = localLocalFrame.getResult();
/* 5341:5128 */           if (localObject5 != null) {
/* 5342:5130 */             localObject4 = (CustomerBankInfo[])ObjectVal.clone(localObject5);
/* 5343:     */           }
/* 5344:5132 */           return localObject4;
/* 5345:     */         }
/* 5346:5134 */         Object localObject4 = local_Request.getInputStream();
/* 5347:5136 */         if (local_Request.isRMI()) {
/* 5348:5136 */           localObject5 = (CustomerBankInfo[])local_Request.read_value(new CustomerBankInfo[0].getClass());
/* 5349:     */         } else {
/* 5350:5136 */           localObject5 = CustomerBankInfoSeqHelper.read((InputStream)localObject4);
/* 5351:     */         }
/* 5352:5137 */         return localObject5;
/* 5353:     */       }
/* 5354:     */       catch (TRANSIENT localTRANSIENT)
/* 5355:     */       {
/* 5356:5141 */         if (i == 10) {
/* 5357:5143 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5358:     */         }
/* 5359:     */       }
/* 5360:     */       catch (UserException localUserException)
/* 5361:     */       {
/* 5362:5148 */         local_Request.isRMI();
/* 5363:     */         
/* 5364:     */ 
/* 5365:5151 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5366:     */       }
/* 5367:     */       catch (SystemException localSystemException)
/* 5368:     */       {
/* 5369:5155 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5370:     */       }
/* 5371:     */       finally
/* 5372:     */       {
/* 5373:5159 */         if (local_Request != null) {
/* 5374:5161 */           local_Request.close();
/* 5375:     */         }
/* 5376:5163 */         if (bool1) {
/* 5377:5164 */           localLocalStack.pop(localLocalFrame);
/* 5378:     */         }
/* 5379:5165 */         localLocalStack.setArgsOnLocal(bool2);
/* 5380:     */       }
/* 5381:     */     }
/* 5382:     */   }
/* 5383:     */   
/* 5384:     */   public int addCustomerProductAccessInfo(CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
/* 5385:     */     throws java.rmi.RemoteException
/* 5386:     */   {
/* 5387:5174 */     for (int i = 1;; i++)
/* 5388:     */     {
/* 5389:5176 */       _Request local_Request = null;
/* 5390:5177 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5391:5178 */       boolean bool1 = false;
/* 5392:5179 */       LocalFrame localLocalFrame = null;
/* 5393:5180 */       boolean bool2 = false;
/* 5394:     */       try
/* 5395:     */       {
/* 5396:5183 */         local_Request = __request("addCustomerProductAccessInfo");
/* 5397:5184 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5398:5185 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5399:5186 */         localLocalStack.setArgsOnLocal(bool1);
/* 5400:5188 */         if (bool1)
/* 5401:     */         {
/* 5402:5190 */           localLocalFrame = new LocalFrame(1);
/* 5403:5191 */           localLocalStack.push(localLocalFrame);
/* 5404:     */         }
/* 5405:     */         OutputStream localOutputStream;
/* 5406:5193 */         if (!bool1)
/* 5407:     */         {
/* 5408:5195 */           localOutputStream = local_Request.getOutputStream();
/* 5409:5196 */           if (local_Request.isRMI()) {
/* 5410:5196 */             local_Request.write_value(paramArrayOfCustomerProductAccessInfo, new CustomerProductAccessInfo[0].getClass());
/* 5411:     */           } else {
/* 5412:5196 */             CustomerProductAccessInfoSeqHelper.write(localOutputStream, paramArrayOfCustomerProductAccessInfo);
/* 5413:     */           }
/* 5414:     */         }
/* 5415:     */         else
/* 5416:     */         {
/* 5417:5200 */           localOutputStream = local_Request.getOutputStream();
/* 5418:5201 */           localLocalFrame.add(paramArrayOfCustomerProductAccessInfo);
/* 5419:     */         }
/* 5420:5203 */         local_Request.invoke();
/* 5421:     */         int j;
/* 5422:5204 */         if (bool1)
/* 5423:     */         {
/* 5424:5207 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/* 5425:5208 */           return k;
/* 5426:     */         }
/* 5427:5210 */         InputStream localInputStream = local_Request.getInputStream();
/* 5428:     */         
/* 5429:5212 */         int m = localInputStream.read_long();
/* 5430:5213 */         return m;
/* 5431:     */       }
/* 5432:     */       catch (TRANSIENT localTRANSIENT)
/* 5433:     */       {
/* 5434:5217 */         if (i == 10) {
/* 5435:5219 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5436:     */         }
/* 5437:     */       }
/* 5438:     */       catch (UserException localUserException)
/* 5439:     */       {
/* 5440:5224 */         local_Request.isRMI();
/* 5441:     */         
/* 5442:     */ 
/* 5443:5227 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5444:     */       }
/* 5445:     */       catch (SystemException localSystemException)
/* 5446:     */       {
/* 5447:5231 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5448:     */       }
/* 5449:     */       finally
/* 5450:     */       {
/* 5451:5235 */         if (local_Request != null) {
/* 5452:5237 */           local_Request.close();
/* 5453:     */         }
/* 5454:5239 */         if (bool1) {
/* 5455:5240 */           localLocalStack.pop(localLocalFrame);
/* 5456:     */         }
/* 5457:5241 */         localLocalStack.setArgsOnLocal(bool2);
/* 5458:     */       }
/* 5459:     */     }
/* 5460:     */   }
/* 5461:     */   
/* 5462:     */   public int deleteCustomerProductAccessInfo(CustomerProductAccessInfo[] paramArrayOfCustomerProductAccessInfo)
/* 5463:     */     throws java.rmi.RemoteException
/* 5464:     */   {
/* 5465:5250 */     for (int i = 1;; i++)
/* 5466:     */     {
/* 5467:5252 */       _Request local_Request = null;
/* 5468:5253 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5469:5254 */       boolean bool1 = false;
/* 5470:5255 */       LocalFrame localLocalFrame = null;
/* 5471:5256 */       boolean bool2 = false;
/* 5472:     */       try
/* 5473:     */       {
/* 5474:5259 */         local_Request = __request("deleteCustomerProductAccessInfo");
/* 5475:5260 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5476:5261 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5477:5262 */         localLocalStack.setArgsOnLocal(bool1);
/* 5478:5264 */         if (bool1)
/* 5479:     */         {
/* 5480:5266 */           localLocalFrame = new LocalFrame(1);
/* 5481:5267 */           localLocalStack.push(localLocalFrame);
/* 5482:     */         }
/* 5483:     */         OutputStream localOutputStream;
/* 5484:5269 */         if (!bool1)
/* 5485:     */         {
/* 5486:5271 */           localOutputStream = local_Request.getOutputStream();
/* 5487:5272 */           if (local_Request.isRMI()) {
/* 5488:5272 */             local_Request.write_value(paramArrayOfCustomerProductAccessInfo, new CustomerProductAccessInfo[0].getClass());
/* 5489:     */           } else {
/* 5490:5272 */             CustomerProductAccessInfoSeqHelper.write(localOutputStream, paramArrayOfCustomerProductAccessInfo);
/* 5491:     */           }
/* 5492:     */         }
/* 5493:     */         else
/* 5494:     */         {
/* 5495:5276 */           localOutputStream = local_Request.getOutputStream();
/* 5496:5277 */           localLocalFrame.add(paramArrayOfCustomerProductAccessInfo);
/* 5497:     */         }
/* 5498:5279 */         local_Request.invoke();
/* 5499:     */         int j;
/* 5500:5280 */         if (bool1)
/* 5501:     */         {
/* 5502:5283 */           int k = ((Integer)localLocalFrame.getResult()).intValue();
/* 5503:5284 */           return k;
/* 5504:     */         }
/* 5505:5286 */         InputStream localInputStream = local_Request.getInputStream();
/* 5506:     */         
/* 5507:5288 */         int m = localInputStream.read_long();
/* 5508:5289 */         return m;
/* 5509:     */       }
/* 5510:     */       catch (TRANSIENT localTRANSIENT)
/* 5511:     */       {
/* 5512:5293 */         if (i == 10) {
/* 5513:5295 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5514:     */         }
/* 5515:     */       }
/* 5516:     */       catch (UserException localUserException)
/* 5517:     */       {
/* 5518:5300 */         local_Request.isRMI();
/* 5519:     */         
/* 5520:     */ 
/* 5521:5303 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5522:     */       }
/* 5523:     */       catch (SystemException localSystemException)
/* 5524:     */       {
/* 5525:5307 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5526:     */       }
/* 5527:     */       finally
/* 5528:     */       {
/* 5529:5311 */         if (local_Request != null) {
/* 5530:5313 */           local_Request.close();
/* 5531:     */         }
/* 5532:5315 */         if (bool1) {
/* 5533:5316 */           localLocalStack.pop(localLocalFrame);
/* 5534:     */         }
/* 5535:5317 */         localLocalStack.setArgsOnLocal(bool2);
/* 5536:     */       }
/* 5537:     */     }
/* 5538:     */   }
/* 5539:     */   
/* 5540:     */   public CustomerProductAccessInfo[] getCustomerProductAccessInfo(String[] paramArrayOfString)
/* 5541:     */     throws java.rmi.RemoteException
/* 5542:     */   {
/* 5543:5326 */     for (int i = 1;; i++)
/* 5544:     */     {
/* 5545:5328 */       _Request local_Request = null;
/* 5546:5329 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5547:5330 */       boolean bool1 = false;
/* 5548:5331 */       LocalFrame localLocalFrame = null;
/* 5549:5332 */       boolean bool2 = false;
/* 5550:     */       try
/* 5551:     */       {
/* 5552:5335 */         local_Request = __request("getCustomerProductAccessInfo");
/* 5553:5336 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5554:5337 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5555:5338 */         localLocalStack.setArgsOnLocal(bool1);
/* 5556:5340 */         if (bool1)
/* 5557:     */         {
/* 5558:5342 */           localLocalFrame = new LocalFrame(1);
/* 5559:5343 */           localLocalStack.push(localLocalFrame);
/* 5560:     */         }
/* 5561:5345 */         if (!bool1)
/* 5562:     */         {
/* 5563:5347 */           localObject4 = local_Request.getOutputStream();
/* 5564:5348 */           if (local_Request.isRMI()) {
/* 5565:5348 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 5566:     */           } else {
/* 5567:5348 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 5568:     */           }
/* 5569:     */         }
/* 5570:     */         else
/* 5571:     */         {
/* 5572:5352 */           localObject4 = local_Request.getOutputStream();
/* 5573:5353 */           localLocalFrame.add(paramArrayOfString);
/* 5574:     */         }
/* 5575:5355 */         local_Request.invoke();
/* 5576:     */         Object localObject5;
/* 5577:     */         Object localObject1;
/* 5578:5356 */         if (bool1)
/* 5579:     */         {
/* 5580:5358 */           localObject4 = null;
/* 5581:5359 */           localObject5 = localLocalFrame.getResult();
/* 5582:5360 */           if (localObject5 != null) {
/* 5583:5362 */             localObject4 = (CustomerProductAccessInfo[])ObjectVal.clone(localObject5);
/* 5584:     */           }
/* 5585:5364 */           return localObject4;
/* 5586:     */         }
/* 5587:5366 */         Object localObject4 = local_Request.getInputStream();
/* 5588:5368 */         if (local_Request.isRMI()) {
/* 5589:5368 */           localObject5 = (CustomerProductAccessInfo[])local_Request.read_value(new CustomerProductAccessInfo[0].getClass());
/* 5590:     */         } else {
/* 5591:5368 */           localObject5 = CustomerProductAccessInfoSeqHelper.read((InputStream)localObject4);
/* 5592:     */         }
/* 5593:5369 */         return localObject5;
/* 5594:     */       }
/* 5595:     */       catch (TRANSIENT localTRANSIENT)
/* 5596:     */       {
/* 5597:5373 */         if (i == 10) {
/* 5598:5375 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5599:     */         }
/* 5600:     */       }
/* 5601:     */       catch (UserException localUserException)
/* 5602:     */       {
/* 5603:5380 */         local_Request.isRMI();
/* 5604:     */         
/* 5605:     */ 
/* 5606:5383 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5607:     */       }
/* 5608:     */       catch (SystemException localSystemException)
/* 5609:     */       {
/* 5610:5387 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5611:     */       }
/* 5612:     */       finally
/* 5613:     */       {
/* 5614:5391 */         if (local_Request != null) {
/* 5615:5393 */           local_Request.close();
/* 5616:     */         }
/* 5617:5395 */         if (bool1) {
/* 5618:5396 */           localLocalStack.pop(localLocalFrame);
/* 5619:     */         }
/* 5620:5397 */         localLocalStack.setArgsOnLocal(bool2);
/* 5621:     */       }
/* 5622:     */     }
/* 5623:     */   }
/* 5624:     */   
/* 5625:     */   public boolean validateMetavanteCustAcctByConsumerID(String paramString1, String paramString2)
/* 5626:     */     throws java.rmi.RemoteException
/* 5627:     */   {
/* 5628:5407 */     for (int i = 1;; i++)
/* 5629:     */     {
/* 5630:5409 */       _Request local_Request = null;
/* 5631:5410 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5632:5411 */       boolean bool1 = false;
/* 5633:5412 */       LocalFrame localLocalFrame = null;
/* 5634:5413 */       boolean bool2 = false;
/* 5635:     */       try
/* 5636:     */       {
/* 5637:5416 */         local_Request = __request("validateMetavanteCustAcctByConsumerID");
/* 5638:5417 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5639:5418 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5640:5419 */         localLocalStack.setArgsOnLocal(bool1);
/* 5641:5421 */         if (bool1)
/* 5642:     */         {
/* 5643:5423 */           localLocalFrame = new LocalFrame(2);
/* 5644:5424 */           localLocalStack.push(localLocalFrame);
/* 5645:     */         }
/* 5646:     */         OutputStream localOutputStream;
/* 5647:5426 */         if (!bool1)
/* 5648:     */         {
/* 5649:5428 */           localOutputStream = local_Request.getOutputStream();
/* 5650:5429 */           local_Request.write_string(paramString1);
/* 5651:5430 */           local_Request.write_string(paramString2);
/* 5652:     */         }
/* 5653:     */         else
/* 5654:     */         {
/* 5655:5434 */           localOutputStream = local_Request.getOutputStream();
/* 5656:5435 */           localLocalFrame.add(paramString1);
/* 5657:5436 */           localLocalFrame.add(paramString2);
/* 5658:     */         }
/* 5659:5438 */         local_Request.invoke();
/* 5660:     */         boolean bool3;
/* 5661:5439 */         if (bool1)
/* 5662:     */         {
/* 5663:5442 */           boolean bool4 = ((Boolean)localLocalFrame.getResult()).booleanValue();
/* 5664:5443 */           return bool4;
/* 5665:     */         }
/* 5666:5445 */         InputStream localInputStream = local_Request.getInputStream();
/* 5667:     */         
/* 5668:5447 */         boolean bool5 = localInputStream.read_boolean();
/* 5669:5448 */         return bool5;
/* 5670:     */       }
/* 5671:     */       catch (TRANSIENT localTRANSIENT)
/* 5672:     */       {
/* 5673:5452 */         if (i == 10) {
/* 5674:5454 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5675:     */         }
/* 5676:     */       }
/* 5677:     */       catch (UserException localUserException)
/* 5678:     */       {
/* 5679:5459 */         local_Request.isRMI();
/* 5680:     */         
/* 5681:     */ 
/* 5682:5462 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5683:     */       }
/* 5684:     */       catch (SystemException localSystemException)
/* 5685:     */       {
/* 5686:5466 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5687:     */       }
/* 5688:     */       finally
/* 5689:     */       {
/* 5690:5470 */         if (local_Request != null) {
/* 5691:5472 */           local_Request.close();
/* 5692:     */         }
/* 5693:5474 */         if (bool1) {
/* 5694:5475 */           localLocalStack.pop(localLocalFrame);
/* 5695:     */         }
/* 5696:5476 */         localLocalStack.setArgsOnLocal(bool2);
/* 5697:     */       }
/* 5698:     */     }
/* 5699:     */   }
/* 5700:     */   
/* 5701:     */   public boolean validateMetavanteCustAcctByCustomerID(String paramString1, String paramString2)
/* 5702:     */     throws java.rmi.RemoteException
/* 5703:     */   {
/* 5704:5486 */     for (int i = 1;; i++)
/* 5705:     */     {
/* 5706:5488 */       _Request local_Request = null;
/* 5707:5489 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5708:5490 */       boolean bool1 = false;
/* 5709:5491 */       LocalFrame localLocalFrame = null;
/* 5710:5492 */       boolean bool2 = false;
/* 5711:     */       try
/* 5712:     */       {
/* 5713:5495 */         local_Request = __request("validateMetavanteCustAcctByCustomerID");
/* 5714:5496 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5715:5497 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5716:5498 */         localLocalStack.setArgsOnLocal(bool1);
/* 5717:5500 */         if (bool1)
/* 5718:     */         {
/* 5719:5502 */           localLocalFrame = new LocalFrame(2);
/* 5720:5503 */           localLocalStack.push(localLocalFrame);
/* 5721:     */         }
/* 5722:     */         OutputStream localOutputStream;
/* 5723:5505 */         if (!bool1)
/* 5724:     */         {
/* 5725:5507 */           localOutputStream = local_Request.getOutputStream();
/* 5726:5508 */           local_Request.write_string(paramString1);
/* 5727:5509 */           local_Request.write_string(paramString2);
/* 5728:     */         }
/* 5729:     */         else
/* 5730:     */         {
/* 5731:5513 */           localOutputStream = local_Request.getOutputStream();
/* 5732:5514 */           localLocalFrame.add(paramString1);
/* 5733:5515 */           localLocalFrame.add(paramString2);
/* 5734:     */         }
/* 5735:5517 */         local_Request.invoke();
/* 5736:     */         boolean bool3;
/* 5737:5518 */         if (bool1)
/* 5738:     */         {
/* 5739:5521 */           boolean bool4 = ((Boolean)localLocalFrame.getResult()).booleanValue();
/* 5740:5522 */           return bool4;
/* 5741:     */         }
/* 5742:5524 */         InputStream localInputStream = local_Request.getInputStream();
/* 5743:     */         
/* 5744:5526 */         boolean bool5 = localInputStream.read_boolean();
/* 5745:5527 */         return bool5;
/* 5746:     */       }
/* 5747:     */       catch (TRANSIENT localTRANSIENT)
/* 5748:     */       {
/* 5749:5531 */         if (i == 10) {
/* 5750:5533 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5751:     */         }
/* 5752:     */       }
/* 5753:     */       catch (UserException localUserException)
/* 5754:     */       {
/* 5755:5538 */         local_Request.isRMI();
/* 5756:     */         
/* 5757:     */ 
/* 5758:5541 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5759:     */       }
/* 5760:     */       catch (SystemException localSystemException)
/* 5761:     */       {
/* 5762:5545 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5763:     */       }
/* 5764:     */       finally
/* 5765:     */       {
/* 5766:5549 */         if (local_Request != null) {
/* 5767:5551 */           local_Request.close();
/* 5768:     */         }
/* 5769:5553 */         if (bool1) {
/* 5770:5554 */           localLocalStack.pop(localLocalFrame);
/* 5771:     */         }
/* 5772:5555 */         localLocalStack.setArgsOnLocal(bool2);
/* 5773:     */       }
/* 5774:     */     }
/* 5775:     */   }
/* 5776:     */   
/* 5777:     */   public BPWHist getPmtHistory(BPWHist paramBPWHist)
/* 5778:     */     throws java.rmi.RemoteException
/* 5779:     */   {
/* 5780:5564 */     for (int i = 1;; i++)
/* 5781:     */     {
/* 5782:5566 */       _Request local_Request = null;
/* 5783:5567 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5784:5568 */       boolean bool1 = false;
/* 5785:5569 */       LocalFrame localLocalFrame = null;
/* 5786:5570 */       boolean bool2 = false;
/* 5787:     */       try
/* 5788:     */       {
/* 5789:5573 */         local_Request = __request("getPmtHistory");
/* 5790:5574 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5791:5575 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5792:5576 */         localLocalStack.setArgsOnLocal(bool1);
/* 5793:5578 */         if (bool1)
/* 5794:     */         {
/* 5795:5580 */           localLocalFrame = new LocalFrame(1);
/* 5796:5581 */           localLocalStack.push(localLocalFrame);
/* 5797:     */         }
/* 5798:5583 */         if (!bool1)
/* 5799:     */         {
/* 5800:5585 */           localObject4 = local_Request.getOutputStream();
/* 5801:5586 */           if (local_Request.isRMI()) {
/* 5802:5586 */             local_Request.write_value(paramBPWHist, BPWHist.class);
/* 5803:     */           } else {
/* 5804:5586 */             BPWHistHelper.write((OutputStream)localObject4, paramBPWHist);
/* 5805:     */           }
/* 5806:     */         }
/* 5807:     */         else
/* 5808:     */         {
/* 5809:5590 */           localObject4 = local_Request.getOutputStream();
/* 5810:5591 */           localLocalFrame.add(paramBPWHist);
/* 5811:     */         }
/* 5812:5593 */         local_Request.invoke();
/* 5813:     */         Object localObject5;
/* 5814:     */         Object localObject1;
/* 5815:5594 */         if (bool1)
/* 5816:     */         {
/* 5817:5596 */           localObject4 = null;
/* 5818:5597 */           localObject5 = localLocalFrame.getResult();
/* 5819:5598 */           if (localObject5 != null) {
/* 5820:5600 */             localObject4 = (BPWHist)ObjectVal.clone(localObject5);
/* 5821:     */           }
/* 5822:5602 */           return localObject4;
/* 5823:     */         }
/* 5824:5604 */         Object localObject4 = local_Request.getInputStream();
/* 5825:5606 */         if (local_Request.isRMI()) {
/* 5826:5606 */           localObject5 = (BPWHist)local_Request.read_value(BPWHist.class);
/* 5827:     */         } else {
/* 5828:5606 */           localObject5 = BPWHistHelper.read((InputStream)localObject4);
/* 5829:     */         }
/* 5830:5607 */         return localObject5;
/* 5831:     */       }
/* 5832:     */       catch (TRANSIENT localTRANSIENT)
/* 5833:     */       {
/* 5834:5611 */         if (i == 10) {
/* 5835:5613 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5836:     */         }
/* 5837:     */       }
/* 5838:     */       catch (UserException localUserException)
/* 5839:     */       {
/* 5840:5618 */         local_Request.isRMI();
/* 5841:     */         
/* 5842:     */ 
/* 5843:5621 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5844:     */       }
/* 5845:     */       catch (SystemException localSystemException)
/* 5846:     */       {
/* 5847:5625 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5848:     */       }
/* 5849:     */       finally
/* 5850:     */       {
/* 5851:5629 */         if (local_Request != null) {
/* 5852:5631 */           local_Request.close();
/* 5853:     */         }
/* 5854:5633 */         if (bool1) {
/* 5855:5634 */           localLocalStack.pop(localLocalFrame);
/* 5856:     */         }
/* 5857:5635 */         localLocalStack.setArgsOnLocal(bool2);
/* 5858:     */       }
/* 5859:     */     }
/* 5860:     */   }
/* 5861:     */   
/* 5862:     */   public BPWHist getIntraHistory(BPWHist paramBPWHist)
/* 5863:     */     throws java.rmi.RemoteException
/* 5864:     */   {
/* 5865:5644 */     for (int i = 1;; i++)
/* 5866:     */     {
/* 5867:5646 */       _Request local_Request = null;
/* 5868:5647 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5869:5648 */       boolean bool1 = false;
/* 5870:5649 */       LocalFrame localLocalFrame = null;
/* 5871:5650 */       boolean bool2 = false;
/* 5872:     */       try
/* 5873:     */       {
/* 5874:5653 */         local_Request = __request("getIntraHistory");
/* 5875:5654 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5876:5655 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5877:5656 */         localLocalStack.setArgsOnLocal(bool1);
/* 5878:5658 */         if (bool1)
/* 5879:     */         {
/* 5880:5660 */           localLocalFrame = new LocalFrame(1);
/* 5881:5661 */           localLocalStack.push(localLocalFrame);
/* 5882:     */         }
/* 5883:5663 */         if (!bool1)
/* 5884:     */         {
/* 5885:5665 */           localObject4 = local_Request.getOutputStream();
/* 5886:5666 */           if (local_Request.isRMI()) {
/* 5887:5666 */             local_Request.write_value(paramBPWHist, BPWHist.class);
/* 5888:     */           } else {
/* 5889:5666 */             BPWHistHelper.write((OutputStream)localObject4, paramBPWHist);
/* 5890:     */           }
/* 5891:     */         }
/* 5892:     */         else
/* 5893:     */         {
/* 5894:5670 */           localObject4 = local_Request.getOutputStream();
/* 5895:5671 */           localLocalFrame.add(paramBPWHist);
/* 5896:     */         }
/* 5897:5673 */         local_Request.invoke();
/* 5898:     */         Object localObject5;
/* 5899:     */         Object localObject1;
/* 5900:5674 */         if (bool1)
/* 5901:     */         {
/* 5902:5676 */           localObject4 = null;
/* 5903:5677 */           localObject5 = localLocalFrame.getResult();
/* 5904:5678 */           if (localObject5 != null) {
/* 5905:5680 */             localObject4 = (BPWHist)ObjectVal.clone(localObject5);
/* 5906:     */           }
/* 5907:5682 */           return localObject4;
/* 5908:     */         }
/* 5909:5684 */         Object localObject4 = local_Request.getInputStream();
/* 5910:5686 */         if (local_Request.isRMI()) {
/* 5911:5686 */           localObject5 = (BPWHist)local_Request.read_value(BPWHist.class);
/* 5912:     */         } else {
/* 5913:5686 */           localObject5 = BPWHistHelper.read((InputStream)localObject4);
/* 5914:     */         }
/* 5915:5687 */         return localObject5;
/* 5916:     */       }
/* 5917:     */       catch (TRANSIENT localTRANSIENT)
/* 5918:     */       {
/* 5919:5691 */         if (i == 10) {
/* 5920:5693 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5921:     */         }
/* 5922:     */       }
/* 5923:     */       catch (UserException localUserException)
/* 5924:     */       {
/* 5925:5698 */         local_Request.isRMI();
/* 5926:     */         
/* 5927:     */ 
/* 5928:5701 */         throw new java.rmi.RemoteException(localUserException.type);
/* 5929:     */       }
/* 5930:     */       catch (SystemException localSystemException)
/* 5931:     */       {
/* 5932:5705 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 5933:     */       }
/* 5934:     */       finally
/* 5935:     */       {
/* 5936:5709 */         if (local_Request != null) {
/* 5937:5711 */           local_Request.close();
/* 5938:     */         }
/* 5939:5713 */         if (bool1) {
/* 5940:5714 */           localLocalStack.pop(localLocalFrame);
/* 5941:     */         }
/* 5942:5715 */         localLocalStack.setArgsOnLocal(bool2);
/* 5943:     */       }
/* 5944:     */     }
/* 5945:     */   }
/* 5946:     */   
/* 5947:     */   public IntraTrnInfo getIntraById(String paramString)
/* 5948:     */     throws java.rmi.RemoteException
/* 5949:     */   {
/* 5950:5724 */     for (int i = 1;; i++)
/* 5951:     */     {
/* 5952:5726 */       _Request local_Request = null;
/* 5953:5727 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 5954:5728 */       boolean bool1 = false;
/* 5955:5729 */       LocalFrame localLocalFrame = null;
/* 5956:5730 */       boolean bool2 = false;
/* 5957:     */       try
/* 5958:     */       {
/* 5959:5733 */         local_Request = __request("getIntraById__string", "getIntraById__CORBA_WStringValue");
/* 5960:5734 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 5961:5735 */         bool2 = localLocalStack.isArgsOnLocal();
/* 5962:5736 */         localLocalStack.setArgsOnLocal(bool1);
/* 5963:5738 */         if (bool1)
/* 5964:     */         {
/* 5965:5740 */           localLocalFrame = new LocalFrame(1);
/* 5966:5741 */           localLocalStack.push(localLocalFrame);
/* 5967:     */         }
/* 5968:5743 */         if (!bool1)
/* 5969:     */         {
/* 5970:5745 */           localObject4 = local_Request.getOutputStream();
/* 5971:5746 */           local_Request.write_string(paramString);
/* 5972:     */         }
/* 5973:     */         else
/* 5974:     */         {
/* 5975:5750 */           localObject4 = local_Request.getOutputStream();
/* 5976:5751 */           localLocalFrame.add(paramString);
/* 5977:     */         }
/* 5978:5753 */         local_Request.invoke();
/* 5979:     */         Object localObject1;
/* 5980:5754 */         if (bool1)
/* 5981:     */         {
/* 5982:5756 */           localObject4 = null;
/* 5983:5757 */           localObject5 = localLocalFrame.getResult();
/* 5984:5758 */           if (localObject5 != null) {
/* 5985:5760 */             localObject4 = (IntraTrnInfo)ObjectVal.clone(localObject5);
/* 5986:     */           }
/* 5987:5762 */           return localObject4;
/* 5988:     */         }
/* 5989:5764 */         Object localObject4 = local_Request.getInputStream();
/* 5990:     */         
/* 5991:5766 */         Object localObject5 = (IntraTrnInfo)local_Request.read_value(IntraTrnInfo.class);
/* 5992:5767 */         return localObject5;
/* 5993:     */       }
/* 5994:     */       catch (TRANSIENT localTRANSIENT)
/* 5995:     */       {
/* 5996:5771 */         if (i == 10) {
/* 5997:5773 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 5998:     */         }
/* 5999:     */       }
/* 6000:     */       catch (UserException localUserException)
/* 6001:     */       {
/* 6002:5778 */         local_Request.isRMI();
/* 6003:     */         
/* 6004:     */ 
/* 6005:5781 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6006:     */       }
/* 6007:     */       catch (SystemException localSystemException)
/* 6008:     */       {
/* 6009:5785 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6010:     */       }
/* 6011:     */       finally
/* 6012:     */       {
/* 6013:5789 */         if (local_Request != null) {
/* 6014:5791 */           local_Request.close();
/* 6015:     */         }
/* 6016:5793 */         if (bool1) {
/* 6017:5794 */           localLocalStack.pop(localLocalFrame);
/* 6018:     */         }
/* 6019:5795 */         localLocalStack.setArgsOnLocal(bool2);
/* 6020:     */       }
/* 6021:     */     }
/* 6022:     */   }
/* 6023:     */   
/* 6024:     */   public IntraTrnInfo[] getIntraById(String[] paramArrayOfString)
/* 6025:     */     throws java.rmi.RemoteException
/* 6026:     */   {
/* 6027:5804 */     for (int i = 1;; i++)
/* 6028:     */     {
/* 6029:5806 */       _Request local_Request = null;
/* 6030:5807 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6031:5808 */       boolean bool1 = false;
/* 6032:5809 */       LocalFrame localLocalFrame = null;
/* 6033:5810 */       boolean bool2 = false;
/* 6034:     */       try
/* 6035:     */       {
/* 6036:5813 */         local_Request = __request("getIntraById__StringSeq", "getIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue");
/* 6037:5814 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6038:5815 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6039:5816 */         localLocalStack.setArgsOnLocal(bool1);
/* 6040:5818 */         if (bool1)
/* 6041:     */         {
/* 6042:5820 */           localLocalFrame = new LocalFrame(1);
/* 6043:5821 */           localLocalStack.push(localLocalFrame);
/* 6044:     */         }
/* 6045:5823 */         if (!bool1)
/* 6046:     */         {
/* 6047:5825 */           localObject4 = local_Request.getOutputStream();
/* 6048:5826 */           if (local_Request.isRMI()) {
/* 6049:5826 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 6050:     */           } else {
/* 6051:5826 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 6052:     */           }
/* 6053:     */         }
/* 6054:     */         else
/* 6055:     */         {
/* 6056:5830 */           localObject4 = local_Request.getOutputStream();
/* 6057:5831 */           localLocalFrame.add(paramArrayOfString);
/* 6058:     */         }
/* 6059:5833 */         local_Request.invoke();
/* 6060:     */         Object localObject5;
/* 6061:     */         Object localObject1;
/* 6062:5834 */         if (bool1)
/* 6063:     */         {
/* 6064:5836 */           localObject4 = null;
/* 6065:5837 */           localObject5 = localLocalFrame.getResult();
/* 6066:5838 */           if (localObject5 != null) {
/* 6067:5840 */             localObject4 = (IntraTrnInfo[])ObjectVal.clone(localObject5);
/* 6068:     */           }
/* 6069:5842 */           return localObject4;
/* 6070:     */         }
/* 6071:5844 */         Object localObject4 = local_Request.getInputStream();
/* 6072:5846 */         if (local_Request.isRMI()) {
/* 6073:5846 */           localObject5 = (IntraTrnInfo[])local_Request.read_value(new IntraTrnInfo[0].getClass());
/* 6074:     */         } else {
/* 6075:5846 */           localObject5 = IntraTrnInfoSeqHelper.read((InputStream)localObject4);
/* 6076:     */         }
/* 6077:5847 */         return localObject5;
/* 6078:     */       }
/* 6079:     */       catch (TRANSIENT localTRANSIENT)
/* 6080:     */       {
/* 6081:5851 */         if (i == 10) {
/* 6082:5853 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6083:     */         }
/* 6084:     */       }
/* 6085:     */       catch (UserException localUserException)
/* 6086:     */       {
/* 6087:5858 */         local_Request.isRMI();
/* 6088:     */         
/* 6089:     */ 
/* 6090:5861 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6091:     */       }
/* 6092:     */       catch (SystemException localSystemException)
/* 6093:     */       {
/* 6094:5865 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6095:     */       }
/* 6096:     */       finally
/* 6097:     */       {
/* 6098:5869 */         if (local_Request != null) {
/* 6099:5871 */           local_Request.close();
/* 6100:     */         }
/* 6101:5873 */         if (bool1) {
/* 6102:5874 */           localLocalStack.pop(localLocalFrame);
/* 6103:     */         }
/* 6104:5875 */         localLocalStack.setArgsOnLocal(bool2);
/* 6105:     */       }
/* 6106:     */     }
/* 6107:     */   }
/* 6108:     */   
/* 6109:     */   public IntraTrnInfo[] getIntraByRecSrvrTId(String[] paramArrayOfString, boolean paramBoolean)
/* 6110:     */     throws java.rmi.RemoteException
/* 6111:     */   {
/* 6112:5885 */     for (int i = 1;; i++)
/* 6113:     */     {
/* 6114:5887 */       _Request local_Request = null;
/* 6115:5888 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6116:5889 */       boolean bool1 = false;
/* 6117:5890 */       LocalFrame localLocalFrame = null;
/* 6118:5891 */       boolean bool2 = false;
/* 6119:     */       try
/* 6120:     */       {
/* 6121:5894 */         local_Request = __request("getIntraByRecSrvrTId");
/* 6122:5895 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6123:5896 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6124:5897 */         localLocalStack.setArgsOnLocal(bool1);
/* 6125:5899 */         if (bool1)
/* 6126:     */         {
/* 6127:5901 */           localLocalFrame = new LocalFrame(2);
/* 6128:5902 */           localLocalStack.push(localLocalFrame);
/* 6129:     */         }
/* 6130:5904 */         if (!bool1)
/* 6131:     */         {
/* 6132:5906 */           localObject4 = local_Request.getOutputStream();
/* 6133:5907 */           if (local_Request.isRMI()) {
/* 6134:5907 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 6135:     */           } else {
/* 6136:5907 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 6137:     */           }
/* 6138:5908 */           ((OutputStream)localObject4).write_boolean(paramBoolean);
/* 6139:     */         }
/* 6140:     */         else
/* 6141:     */         {
/* 6142:5912 */           localObject4 = local_Request.getOutputStream();
/* 6143:5913 */           localLocalFrame.add(paramArrayOfString);
/* 6144:5914 */           localLocalFrame.add(paramBoolean);
/* 6145:     */         }
/* 6146:5916 */         local_Request.invoke();
/* 6147:     */         Object localObject5;
/* 6148:     */         Object localObject1;
/* 6149:5917 */         if (bool1)
/* 6150:     */         {
/* 6151:5919 */           localObject4 = null;
/* 6152:5920 */           localObject5 = localLocalFrame.getResult();
/* 6153:5921 */           if (localObject5 != null) {
/* 6154:5923 */             localObject4 = (IntraTrnInfo[])ObjectVal.clone(localObject5);
/* 6155:     */           }
/* 6156:5925 */           return localObject4;
/* 6157:     */         }
/* 6158:5927 */         Object localObject4 = local_Request.getInputStream();
/* 6159:5929 */         if (local_Request.isRMI()) {
/* 6160:5929 */           localObject5 = (IntraTrnInfo[])local_Request.read_value(new IntraTrnInfo[0].getClass());
/* 6161:     */         } else {
/* 6162:5929 */           localObject5 = IntraTrnInfoSeqHelper.read((InputStream)localObject4);
/* 6163:     */         }
/* 6164:5930 */         return localObject5;
/* 6165:     */       }
/* 6166:     */       catch (TRANSIENT localTRANSIENT)
/* 6167:     */       {
/* 6168:5934 */         if (i == 10) {
/* 6169:5936 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6170:     */         }
/* 6171:     */       }
/* 6172:     */       catch (UserException localUserException)
/* 6173:     */       {
/* 6174:5941 */         local_Request.isRMI();
/* 6175:     */         
/* 6176:     */ 
/* 6177:5944 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6178:     */       }
/* 6179:     */       catch (SystemException localSystemException)
/* 6180:     */       {
/* 6181:5948 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6182:     */       }
/* 6183:     */       finally
/* 6184:     */       {
/* 6185:5952 */         if (local_Request != null) {
/* 6186:5954 */           local_Request.close();
/* 6187:     */         }
/* 6188:5956 */         if (bool1) {
/* 6189:5957 */           localLocalStack.pop(localLocalFrame);
/* 6190:     */         }
/* 6191:5958 */         localLocalStack.setArgsOnLocal(bool2);
/* 6192:     */       }
/* 6193:     */     }
/* 6194:     */   }
/* 6195:     */   
/* 6196:     */   public PmtInfo[] getPmtById(String[] paramArrayOfString)
/* 6197:     */     throws java.rmi.RemoteException
/* 6198:     */   {
/* 6199:5967 */     for (int i = 1;; i++)
/* 6200:     */     {
/* 6201:5969 */       _Request local_Request = null;
/* 6202:5970 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6203:5971 */       boolean bool1 = false;
/* 6204:5972 */       LocalFrame localLocalFrame = null;
/* 6205:5973 */       boolean bool2 = false;
/* 6206:     */       try
/* 6207:     */       {
/* 6208:5976 */         local_Request = __request("getPmtById__StringSeq", "getPmtById__org_omg_boxedRMI_CORBA_seq1_WStringValue");
/* 6209:5977 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6210:5978 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6211:5979 */         localLocalStack.setArgsOnLocal(bool1);
/* 6212:5981 */         if (bool1)
/* 6213:     */         {
/* 6214:5983 */           localLocalFrame = new LocalFrame(1);
/* 6215:5984 */           localLocalStack.push(localLocalFrame);
/* 6216:     */         }
/* 6217:5986 */         if (!bool1)
/* 6218:     */         {
/* 6219:5988 */           localObject4 = local_Request.getOutputStream();
/* 6220:5989 */           if (local_Request.isRMI()) {
/* 6221:5989 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 6222:     */           } else {
/* 6223:5989 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 6224:     */           }
/* 6225:     */         }
/* 6226:     */         else
/* 6227:     */         {
/* 6228:5993 */           localObject4 = local_Request.getOutputStream();
/* 6229:5994 */           localLocalFrame.add(paramArrayOfString);
/* 6230:     */         }
/* 6231:5996 */         local_Request.invoke();
/* 6232:     */         Object localObject5;
/* 6233:     */         Object localObject1;
/* 6234:5997 */         if (bool1)
/* 6235:     */         {
/* 6236:5999 */           localObject4 = null;
/* 6237:6000 */           localObject5 = localLocalFrame.getResult();
/* 6238:6001 */           if (localObject5 != null) {
/* 6239:6003 */             localObject4 = (PmtInfo[])ObjectVal.clone(localObject5);
/* 6240:     */           }
/* 6241:6005 */           return localObject4;
/* 6242:     */         }
/* 6243:6007 */         Object localObject4 = local_Request.getInputStream();
/* 6244:6009 */         if (local_Request.isRMI()) {
/* 6245:6009 */           localObject5 = (PmtInfo[])local_Request.read_value(new PmtInfo[0].getClass());
/* 6246:     */         } else {
/* 6247:6009 */           localObject5 = PmtInfoSeqHelper.read((InputStream)localObject4);
/* 6248:     */         }
/* 6249:6010 */         return localObject5;
/* 6250:     */       }
/* 6251:     */       catch (TRANSIENT localTRANSIENT)
/* 6252:     */       {
/* 6253:6014 */         if (i == 10) {
/* 6254:6016 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6255:     */         }
/* 6256:     */       }
/* 6257:     */       catch (UserException localUserException)
/* 6258:     */       {
/* 6259:6021 */         local_Request.isRMI();
/* 6260:     */         
/* 6261:     */ 
/* 6262:6024 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6263:     */       }
/* 6264:     */       catch (SystemException localSystemException)
/* 6265:     */       {
/* 6266:6028 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6267:     */       }
/* 6268:     */       finally
/* 6269:     */       {
/* 6270:6032 */         if (local_Request != null) {
/* 6271:6034 */           local_Request.close();
/* 6272:     */         }
/* 6273:6036 */         if (bool1) {
/* 6274:6037 */           localLocalStack.pop(localLocalFrame);
/* 6275:     */         }
/* 6276:6038 */         localLocalStack.setArgsOnLocal(bool2);
/* 6277:     */       }
/* 6278:     */     }
/* 6279:     */   }
/* 6280:     */   
/* 6281:     */   public PmtInfo getPmtById(String paramString)
/* 6282:     */     throws java.rmi.RemoteException
/* 6283:     */   {
/* 6284:6047 */     for (int i = 1;; i++)
/* 6285:     */     {
/* 6286:6049 */       _Request local_Request = null;
/* 6287:6050 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6288:6051 */       boolean bool1 = false;
/* 6289:6052 */       LocalFrame localLocalFrame = null;
/* 6290:6053 */       boolean bool2 = false;
/* 6291:     */       try
/* 6292:     */       {
/* 6293:6056 */         local_Request = __request("getPmtById__string", "getPmtById__CORBA_WStringValue");
/* 6294:6057 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6295:6058 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6296:6059 */         localLocalStack.setArgsOnLocal(bool1);
/* 6297:6061 */         if (bool1)
/* 6298:     */         {
/* 6299:6063 */           localLocalFrame = new LocalFrame(1);
/* 6300:6064 */           localLocalStack.push(localLocalFrame);
/* 6301:     */         }
/* 6302:6066 */         if (!bool1)
/* 6303:     */         {
/* 6304:6068 */           localObject4 = local_Request.getOutputStream();
/* 6305:6069 */           local_Request.write_string(paramString);
/* 6306:     */         }
/* 6307:     */         else
/* 6308:     */         {
/* 6309:6073 */           localObject4 = local_Request.getOutputStream();
/* 6310:6074 */           localLocalFrame.add(paramString);
/* 6311:     */         }
/* 6312:6076 */         local_Request.invoke();
/* 6313:     */         Object localObject1;
/* 6314:6077 */         if (bool1)
/* 6315:     */         {
/* 6316:6079 */           localObject4 = null;
/* 6317:6080 */           localObject5 = localLocalFrame.getResult();
/* 6318:6081 */           if (localObject5 != null) {
/* 6319:6083 */             localObject4 = (PmtInfo)ObjectVal.clone(localObject5);
/* 6320:     */           }
/* 6321:6085 */           return localObject4;
/* 6322:     */         }
/* 6323:6087 */         Object localObject4 = local_Request.getInputStream();
/* 6324:     */         
/* 6325:6089 */         Object localObject5 = (PmtInfo)local_Request.read_value(PmtInfo.class);
/* 6326:6090 */         return localObject5;
/* 6327:     */       }
/* 6328:     */       catch (TRANSIENT localTRANSIENT)
/* 6329:     */       {
/* 6330:6094 */         if (i == 10) {
/* 6331:6096 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6332:     */         }
/* 6333:     */       }
/* 6334:     */       catch (UserException localUserException)
/* 6335:     */       {
/* 6336:6101 */         local_Request.isRMI();
/* 6337:     */         
/* 6338:     */ 
/* 6339:6104 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6340:     */       }
/* 6341:     */       catch (SystemException localSystemException)
/* 6342:     */       {
/* 6343:6108 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6344:     */       }
/* 6345:     */       finally
/* 6346:     */       {
/* 6347:6112 */         if (local_Request != null) {
/* 6348:6114 */           local_Request.close();
/* 6349:     */         }
/* 6350:6116 */         if (bool1) {
/* 6351:6117 */           localLocalStack.pop(localLocalFrame);
/* 6352:     */         }
/* 6353:6118 */         localLocalStack.setArgsOnLocal(bool2);
/* 6354:     */       }
/* 6355:     */     }
/* 6356:     */   }
/* 6357:     */   
/* 6358:     */   public RecPmtInfo[] getRecPmtById(String[] paramArrayOfString)
/* 6359:     */     throws java.rmi.RemoteException
/* 6360:     */   {
/* 6361:6127 */     for (int i = 1;; i++)
/* 6362:     */     {
/* 6363:6129 */       _Request local_Request = null;
/* 6364:6130 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6365:6131 */       boolean bool1 = false;
/* 6366:6132 */       LocalFrame localLocalFrame = null;
/* 6367:6133 */       boolean bool2 = false;
/* 6368:     */       try
/* 6369:     */       {
/* 6370:6136 */         local_Request = __request("getRecPmtById");
/* 6371:6137 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6372:6138 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6373:6139 */         localLocalStack.setArgsOnLocal(bool1);
/* 6374:6141 */         if (bool1)
/* 6375:     */         {
/* 6376:6143 */           localLocalFrame = new LocalFrame(1);
/* 6377:6144 */           localLocalStack.push(localLocalFrame);
/* 6378:     */         }
/* 6379:6146 */         if (!bool1)
/* 6380:     */         {
/* 6381:6148 */           localObject4 = local_Request.getOutputStream();
/* 6382:6149 */           if (local_Request.isRMI()) {
/* 6383:6149 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 6384:     */           } else {
/* 6385:6149 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 6386:     */           }
/* 6387:     */         }
/* 6388:     */         else
/* 6389:     */         {
/* 6390:6153 */           localObject4 = local_Request.getOutputStream();
/* 6391:6154 */           localLocalFrame.add(paramArrayOfString);
/* 6392:     */         }
/* 6393:6156 */         local_Request.invoke();
/* 6394:     */         Object localObject5;
/* 6395:     */         Object localObject1;
/* 6396:6157 */         if (bool1)
/* 6397:     */         {
/* 6398:6159 */           localObject4 = null;
/* 6399:6160 */           localObject5 = localLocalFrame.getResult();
/* 6400:6161 */           if (localObject5 != null) {
/* 6401:6163 */             localObject4 = (RecPmtInfo[])ObjectVal.clone(localObject5);
/* 6402:     */           }
/* 6403:6165 */           return localObject4;
/* 6404:     */         }
/* 6405:6167 */         Object localObject4 = local_Request.getInputStream();
/* 6406:6169 */         if (local_Request.isRMI()) {
/* 6407:6169 */           localObject5 = (RecPmtInfo[])local_Request.read_value(new RecPmtInfo[0].getClass());
/* 6408:     */         } else {
/* 6409:6169 */           localObject5 = RecPmtInfoSeqHelper.read((InputStream)localObject4);
/* 6410:     */         }
/* 6411:6170 */         return localObject5;
/* 6412:     */       }
/* 6413:     */       catch (TRANSIENT localTRANSIENT)
/* 6414:     */       {
/* 6415:6174 */         if (i == 10) {
/* 6416:6176 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6417:     */         }
/* 6418:     */       }
/* 6419:     */       catch (UserException localUserException)
/* 6420:     */       {
/* 6421:6181 */         local_Request.isRMI();
/* 6422:     */         
/* 6423:     */ 
/* 6424:6184 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6425:     */       }
/* 6426:     */       catch (SystemException localSystemException)
/* 6427:     */       {
/* 6428:6188 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6429:     */       }
/* 6430:     */       finally
/* 6431:     */       {
/* 6432:6192 */         if (local_Request != null) {
/* 6433:6194 */           local_Request.close();
/* 6434:     */         }
/* 6435:6196 */         if (bool1) {
/* 6436:6197 */           localLocalStack.pop(localLocalFrame);
/* 6437:     */         }
/* 6438:6198 */         localLocalStack.setArgsOnLocal(bool2);
/* 6439:     */       }
/* 6440:     */     }
/* 6441:     */   }
/* 6442:     */   
/* 6443:     */   public RecIntraTrnInfo[] getRecIntraById(String[] paramArrayOfString)
/* 6444:     */     throws java.rmi.RemoteException
/* 6445:     */   {
/* 6446:6207 */     for (int i = 1;; i++)
/* 6447:     */     {
/* 6448:6209 */       _Request local_Request = null;
/* 6449:6210 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6450:6211 */       boolean bool1 = false;
/* 6451:6212 */       LocalFrame localLocalFrame = null;
/* 6452:6213 */       boolean bool2 = false;
/* 6453:     */       try
/* 6454:     */       {
/* 6455:6216 */         local_Request = __request("getRecIntraById__StringSeq", "getRecIntraById__org_omg_boxedRMI_CORBA_seq1_WStringValue");
/* 6456:6217 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6457:6218 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6458:6219 */         localLocalStack.setArgsOnLocal(bool1);
/* 6459:6221 */         if (bool1)
/* 6460:     */         {
/* 6461:6223 */           localLocalFrame = new LocalFrame(1);
/* 6462:6224 */           localLocalStack.push(localLocalFrame);
/* 6463:     */         }
/* 6464:6226 */         if (!bool1)
/* 6465:     */         {
/* 6466:6228 */           localObject4 = local_Request.getOutputStream();
/* 6467:6229 */           if (local_Request.isRMI()) {
/* 6468:6229 */             local_Request.write_value(paramArrayOfString, new String[0].getClass());
/* 6469:     */           } else {
/* 6470:6229 */             StringSeqHelper.write((OutputStream)localObject4, paramArrayOfString);
/* 6471:     */           }
/* 6472:     */         }
/* 6473:     */         else
/* 6474:     */         {
/* 6475:6233 */           localObject4 = local_Request.getOutputStream();
/* 6476:6234 */           localLocalFrame.add(paramArrayOfString);
/* 6477:     */         }
/* 6478:6236 */         local_Request.invoke();
/* 6479:     */         Object localObject5;
/* 6480:     */         Object localObject1;
/* 6481:6237 */         if (bool1)
/* 6482:     */         {
/* 6483:6239 */           localObject4 = null;
/* 6484:6240 */           localObject5 = localLocalFrame.getResult();
/* 6485:6241 */           if (localObject5 != null) {
/* 6486:6243 */             localObject4 = (RecIntraTrnInfo[])ObjectVal.clone(localObject5);
/* 6487:     */           }
/* 6488:6245 */           return localObject4;
/* 6489:     */         }
/* 6490:6247 */         Object localObject4 = local_Request.getInputStream();
/* 6491:6249 */         if (local_Request.isRMI()) {
/* 6492:6249 */           localObject5 = (RecIntraTrnInfo[])local_Request.read_value(new RecIntraTrnInfo[0].getClass());
/* 6493:     */         } else {
/* 6494:6249 */           localObject5 = RecIntraTrnInfoSeqHelper.read((InputStream)localObject4);
/* 6495:     */         }
/* 6496:6250 */         return localObject5;
/* 6497:     */       }
/* 6498:     */       catch (TRANSIENT localTRANSIENT)
/* 6499:     */       {
/* 6500:6254 */         if (i == 10) {
/* 6501:6256 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6502:     */         }
/* 6503:     */       }
/* 6504:     */       catch (UserException localUserException)
/* 6505:     */       {
/* 6506:6261 */         local_Request.isRMI();
/* 6507:     */         
/* 6508:     */ 
/* 6509:6264 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6510:     */       }
/* 6511:     */       catch (SystemException localSystemException)
/* 6512:     */       {
/* 6513:6268 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6514:     */       }
/* 6515:     */       finally
/* 6516:     */       {
/* 6517:6272 */         if (local_Request != null) {
/* 6518:6274 */           local_Request.close();
/* 6519:     */         }
/* 6520:6276 */         if (bool1) {
/* 6521:6277 */           localLocalStack.pop(localLocalFrame);
/* 6522:     */         }
/* 6523:6278 */         localLocalStack.setArgsOnLocal(bool2);
/* 6524:     */       }
/* 6525:     */     }
/* 6526:     */   }
/* 6527:     */   
/* 6528:     */   public RecIntraTrnInfo getRecIntraById(String paramString)
/* 6529:     */     throws java.rmi.RemoteException
/* 6530:     */   {
/* 6531:6287 */     for (int i = 1;; i++)
/* 6532:     */     {
/* 6533:6289 */       _Request local_Request = null;
/* 6534:6290 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6535:6291 */       boolean bool1 = false;
/* 6536:6292 */       LocalFrame localLocalFrame = null;
/* 6537:6293 */       boolean bool2 = false;
/* 6538:     */       try
/* 6539:     */       {
/* 6540:6296 */         local_Request = __request("getRecIntraById__string", "getRecIntraById__CORBA_WStringValue");
/* 6541:6297 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6542:6298 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6543:6299 */         localLocalStack.setArgsOnLocal(bool1);
/* 6544:6301 */         if (bool1)
/* 6545:     */         {
/* 6546:6303 */           localLocalFrame = new LocalFrame(1);
/* 6547:6304 */           localLocalStack.push(localLocalFrame);
/* 6548:     */         }
/* 6549:6306 */         if (!bool1)
/* 6550:     */         {
/* 6551:6308 */           localObject4 = local_Request.getOutputStream();
/* 6552:6309 */           local_Request.write_string(paramString);
/* 6553:     */         }
/* 6554:     */         else
/* 6555:     */         {
/* 6556:6313 */           localObject4 = local_Request.getOutputStream();
/* 6557:6314 */           localLocalFrame.add(paramString);
/* 6558:     */         }
/* 6559:6316 */         local_Request.invoke();
/* 6560:     */         Object localObject1;
/* 6561:6317 */         if (bool1)
/* 6562:     */         {
/* 6563:6319 */           localObject4 = null;
/* 6564:6320 */           localObject5 = localLocalFrame.getResult();
/* 6565:6321 */           if (localObject5 != null) {
/* 6566:6323 */             localObject4 = (RecIntraTrnInfo)ObjectVal.clone(localObject5);
/* 6567:     */           }
/* 6568:6325 */           return localObject4;
/* 6569:     */         }
/* 6570:6327 */         Object localObject4 = local_Request.getInputStream();
/* 6571:     */         
/* 6572:6329 */         Object localObject5 = (RecIntraTrnInfo)local_Request.read_value(RecIntraTrnInfo.class);
/* 6573:6330 */         return localObject5;
/* 6574:     */       }
/* 6575:     */       catch (TRANSIENT localTRANSIENT)
/* 6576:     */       {
/* 6577:6334 */         if (i == 10) {
/* 6578:6336 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6579:     */         }
/* 6580:     */       }
/* 6581:     */       catch (UserException localUserException)
/* 6582:     */       {
/* 6583:6341 */         local_Request.isRMI();
/* 6584:     */         
/* 6585:     */ 
/* 6586:6344 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6587:     */       }
/* 6588:     */       catch (SystemException localSystemException)
/* 6589:     */       {
/* 6590:6348 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6591:     */       }
/* 6592:     */       finally
/* 6593:     */       {
/* 6594:6352 */         if (local_Request != null) {
/* 6595:6354 */           local_Request.close();
/* 6596:     */         }
/* 6597:6356 */         if (bool1) {
/* 6598:6357 */           localLocalStack.pop(localLocalFrame);
/* 6599:     */         }
/* 6600:6358 */         localLocalStack.setArgsOnLocal(bool2);
/* 6601:     */       }
/* 6602:     */     }
/* 6603:     */   }
/* 6604:     */   
/* 6605:     */   public PayeeInfo getPayeeByListId(String paramString1, String paramString2)
/* 6606:     */     throws java.rmi.RemoteException, FFSException
/* 6607:     */   {
/* 6608:6368 */     for (int i = 1;; i++)
/* 6609:     */     {
/* 6610:6370 */       _Request local_Request = null;
/* 6611:6371 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6612:6372 */       boolean bool1 = false;
/* 6613:6373 */       LocalFrame localLocalFrame = null;
/* 6614:6374 */       boolean bool2 = false;
/* 6615:     */       try
/* 6616:     */       {
/* 6617:6377 */         local_Request = __request("getPayeeByListId");
/* 6618:6378 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6619:6379 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6620:6380 */         localLocalStack.setArgsOnLocal(bool1);
/* 6621:6382 */         if (bool1)
/* 6622:     */         {
/* 6623:6384 */           localLocalFrame = new LocalFrame(2);
/* 6624:6385 */           localLocalStack.push(localLocalFrame);
/* 6625:     */         }
/* 6626:6387 */         if (!bool1)
/* 6627:     */         {
/* 6628:6389 */           localObject4 = local_Request.getOutputStream();
/* 6629:6390 */           local_Request.write_string(paramString1);
/* 6630:6391 */           local_Request.write_string(paramString2);
/* 6631:     */         }
/* 6632:     */         else
/* 6633:     */         {
/* 6634:6395 */           localObject4 = local_Request.getOutputStream();
/* 6635:6396 */           localLocalFrame.add(paramString1);
/* 6636:6397 */           localLocalFrame.add(paramString2);
/* 6637:     */         }
/* 6638:6399 */         local_Request.invoke();
/* 6639:     */         Object localObject1;
/* 6640:6400 */         if (bool1)
/* 6641:     */         {
/* 6642:6402 */           localObject4 = null;
/* 6643:6403 */           localObject5 = localLocalFrame.getResult();
/* 6644:6404 */           if (localObject5 != null) {
/* 6645:6406 */             localObject4 = (PayeeInfo)ObjectVal.clone(localObject5);
/* 6646:     */           }
/* 6647:6408 */           return localObject4;
/* 6648:     */         }
/* 6649:6410 */         Object localObject4 = local_Request.getInputStream();
/* 6650:     */         
/* 6651:6412 */         localObject5 = (PayeeInfo)local_Request.read_value(PayeeInfo.class);
/* 6652:6413 */         return localObject5;
/* 6653:     */       }
/* 6654:     */       catch (TRANSIENT localTRANSIENT)
/* 6655:     */       {
/* 6656:6417 */         if (i == 10) {
/* 6657:6419 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6658:     */         }
/* 6659:     */       }
/* 6660:     */       catch (UserException localUserException)
/* 6661:     */       {
/* 6662:     */         Object localObject5;
/* 6663:6424 */         if (local_Request.isRMI())
/* 6664:     */         {
/* 6665:6426 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 6666:6428 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6667:     */           }
/* 6668:     */         }
/* 6669:     */         else
/* 6670:     */         {
/* 6671:6433 */           localObject5 = null;
/* 6672:6434 */           if (bool1)
/* 6673:     */           {
/* 6674:6436 */             localObject5 = localLocalFrame.getException();
/* 6675:6437 */             if (localObject5 != null) {
/* 6676:6439 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 6677:     */             }
/* 6678:     */           }
/* 6679:6442 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 6680:     */           {
/* 6681:6444 */             if (local_Request.isRMI()) {
/* 6682:6446 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6683:     */             }
/* 6684:6450 */             if (bool1)
/* 6685:     */             {
/* 6686:6452 */               if (localObject5 != null) {
/* 6687:6452 */                 throw ((FFSException)localObject5);
/* 6688:     */               }
/* 6689:     */             }
/* 6690:     */             else {
/* 6691:6456 */               throw FFSExceptionHelper.read(localUserException.input);
/* 6692:     */             }
/* 6693:     */           }
/* 6694:     */         }
/* 6695:6461 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6696:     */       }
/* 6697:     */       catch (SystemException localSystemException)
/* 6698:     */       {
/* 6699:6465 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6700:     */       }
/* 6701:     */       finally
/* 6702:     */       {
/* 6703:6469 */         if (local_Request != null) {
/* 6704:6471 */           local_Request.close();
/* 6705:     */         }
/* 6706:6473 */         if (bool1) {
/* 6707:6474 */           localLocalStack.pop(localLocalFrame);
/* 6708:     */         }
/* 6709:6475 */         localLocalStack.setArgsOnLocal(bool2);
/* 6710:     */       }
/* 6711:     */     }
/* 6712:     */   }
/* 6713:     */   
/* 6714:     */   public AccountTypesMap getAccountTypesMap()
/* 6715:     */     throws java.rmi.RemoteException, FFSException
/* 6716:     */   {
/* 6717:6483 */     for (int i = 1;; i++)
/* 6718:     */     {
/* 6719:6485 */       _Request local_Request = null;
/* 6720:6486 */       LocalStack localLocalStack = LocalStack.getCurrent();
/* 6721:6487 */       boolean bool1 = false;
/* 6722:6488 */       LocalFrame localLocalFrame = null;
/* 6723:6489 */       boolean bool2 = false;
/* 6724:     */       try
/* 6725:     */       {
/* 6726:6492 */         local_Request = __request("getAccountTypesMap");
/* 6727:6493 */         bool1 = ObjectRef._isLocalJava(local_Request);
/* 6728:6494 */         bool2 = localLocalStack.isArgsOnLocal();
/* 6729:6495 */         localLocalStack.setArgsOnLocal(bool1);
/* 6730:6497 */         if (bool1)
/* 6731:     */         {
/* 6732:6499 */           localLocalFrame = new LocalFrame(0);
/* 6733:6500 */           localLocalStack.push(localLocalFrame);
/* 6734:     */         }
/* 6735:6508 */         local_Request.invoke();
/* 6736:     */         Object localObject1;
/* 6737:6509 */         if (bool1)
/* 6738:     */         {
/* 6739:6511 */           localObject4 = null;
/* 6740:6512 */           localObject5 = localLocalFrame.getResult();
/* 6741:6513 */           if (localObject5 != null) {
/* 6742:6515 */             localObject4 = (AccountTypesMap)ObjectVal.clone(localObject5);
/* 6743:     */           }
/* 6744:6517 */           return localObject4;
/* 6745:     */         }
/* 6746:6519 */         Object localObject4 = local_Request.getInputStream();
/* 6747:     */         
/* 6748:6521 */         localObject5 = (AccountTypesMap)local_Request.read_value(AccountTypesMap.class);
/* 6749:6522 */         return localObject5;
/* 6750:     */       }
/* 6751:     */       catch (TRANSIENT localTRANSIENT)
/* 6752:     */       {
/* 6753:6526 */         if (i == 10) {
/* 6754:6528 */           throw new java.rmi.RemoteException(localTRANSIENT.toString());
/* 6755:     */         }
/* 6756:     */       }
/* 6757:     */       catch (UserException localUserException)
/* 6758:     */       {
/* 6759:     */         Object localObject5;
/* 6760:6533 */         if (local_Request.isRMI())
/* 6761:     */         {
/* 6762:6535 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSEx:1.0")) {
/* 6763:6537 */             throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6764:     */           }
/* 6765:     */         }
/* 6766:     */         else
/* 6767:     */         {
/* 6768:6542 */           localObject5 = null;
/* 6769:6543 */           if (bool1)
/* 6770:     */           {
/* 6771:6545 */             localObject5 = localLocalFrame.getException();
/* 6772:6546 */             if (localObject5 != null) {
/* 6773:6548 */               localObject5 = (Throwable)ObjectVal.clone(localObject5);
/* 6774:     */             }
/* 6775:     */           }
/* 6776:6551 */           if (localUserException.type.equals("IDL:com/ffusion/ffs/interfaces/FFSException:1.0"))
/* 6777:     */           {
/* 6778:6553 */             if (local_Request.isRMI()) {
/* 6779:6555 */               throw ((FFSException)local_Request.read_value(FFSException.class));
/* 6780:     */             }
/* 6781:6559 */             if (bool1)
/* 6782:     */             {
/* 6783:6561 */               if (localObject5 != null) {
/* 6784:6561 */                 throw ((FFSException)localObject5);
/* 6785:     */               }
/* 6786:     */             }
/* 6787:     */             else {
/* 6788:6565 */               throw FFSExceptionHelper.read(localUserException.input);
/* 6789:     */             }
/* 6790:     */           }
/* 6791:     */         }
/* 6792:6570 */         throw new java.rmi.RemoteException(localUserException.type);
/* 6793:     */       }
/* 6794:     */       catch (SystemException localSystemException)
/* 6795:     */       {
/* 6796:6574 */         throw com.sybase.ejb.RemoteException._throw(localSystemException);
/* 6797:     */       }
/* 6798:     */       finally
/* 6799:     */       {
/* 6800:6578 */         if (local_Request != null) {
/* 6801:6580 */           local_Request.close();
/* 6802:     */         }
/* 6803:6582 */         if (bool1) {
/* 6804:6583 */           localLocalStack.pop(localLocalFrame);
/* 6805:     */         }
/* 6806:6584 */         localLocalStack.setArgsOnLocal(bool2);
/* 6807:     */       }
/* 6808:     */     }
/* 6809:     */   }
/* 6810:     */   
/* 6811:     */   public IOFX200BPWServices_Stub() {}
/* 6812:     */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.IOFX200BPWServices_Stub
 * JD-Core Version:    0.7.0.1
 */