/*   1:    */ package com.ffusion.ffs.bpw.ACHServices;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.rmi.Remote;
/*   5:    */ import javax.ejb.CreateException;
/*   6:    */ import javax.ejb.EJBMetaData;
/*   7:    */ import javax.ejb.Handle;
/*   8:    */ import javax.ejb.HomeHandle;
/*   9:    */ import javax.ejb.RemoveException;
/*  10:    */ import javax.rmi.CORBA.Tie;
/*  11:    */ import javax.rmi.CORBA.Util;
/*  12:    */ import org.omg.CORBA.BAD_OPERATION;
/*  13:    */ import org.omg.CORBA.ORB;
/*  14:    */ import org.omg.CORBA.SystemException;
/*  15:    */ import org.omg.CORBA.portable.Delegate;
/*  16:    */ import org.omg.CORBA.portable.ResponseHandler;
/*  17:    */ import org.omg.CORBA.portable.UnknownException;
/*  18:    */ 
/*  19:    */ public class _EJSRemoteStatelessACHBPWServicesHome_Tie
/*  20:    */   extends org.omg.CORBA_2_3.portable.ObjectImpl
/*  21:    */   implements Tie
/*  22:    */ {
/*  23: 29 */   private EJSRemoteStatelessACHBPWServicesHome target = null;
/*  24: 30 */   private ORB orb = null;
/*  25: 32 */   private static final String[] _type_ids = {
/*  26: 33 */     "RMI:com.ffusion.ffs.bpw.ACHServices.ACHBPWServicesHome:0000000000000000", 
/*  27: 34 */     "RMI:javax.ejb.EJBHome:0000000000000000", 
/*  28: 35 */     "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
/*  29: 36 */     "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000", 
/*  30: 37 */     "RMI:javax.ejb.EJBObject:0000000000000000" };
/*  31:    */   
/*  32:    */   public void setTarget(Remote paramRemote)
/*  33:    */   {
/*  34: 41 */     this.target = ((EJSRemoteStatelessACHBPWServicesHome)paramRemote);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Remote getTarget()
/*  38:    */   {
/*  39: 45 */     return this.target;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public org.omg.CORBA.Object thisObject()
/*  43:    */   {
/*  44: 49 */     return this;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void deactivate()
/*  48:    */   {
/*  49: 53 */     if (this.orb != null)
/*  50:    */     {
/*  51: 54 */       this.orb.disconnect(this);
/*  52: 55 */       _set_delegate(null);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public ORB orb()
/*  57:    */   {
/*  58: 60 */     return _orb();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void orb(ORB paramORB)
/*  62:    */   {
/*  63: 64 */     paramORB.connect(this);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void _set_delegate(Delegate paramDelegate)
/*  67:    */   {
/*  68: 68 */     super._set_delegate(paramDelegate);
/*  69: 69 */     if (paramDelegate != null) {
/*  70: 70 */       this.orb = _orb();
/*  71:    */     } else {
/*  72: 72 */       this.orb = null;
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String[] _ids()
/*  77:    */   {
/*  78: 76 */     return _type_ids;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public org.omg.CORBA.portable.OutputStream _invoke(String paramString, org.omg.CORBA.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  82:    */     throws SystemException
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86: 81 */       org.omg.CORBA_2_3.portable.InputStream localInputStream = 
/*  87: 82 */         (org.omg.CORBA_2_3.portable.InputStream)paramInputStream;
/*  88: 83 */       switch (paramString.length())
/*  89:    */       {
/*  90:    */       case 6: 
/*  91: 85 */         if (paramString.equals("create")) {
/*  92: 86 */           return create(localInputStream, paramResponseHandler);
/*  93:    */         }
/*  94:    */       case 15: 
/*  95: 89 */         if (paramString.equals("_get_homeHandle")) {
/*  96: 90 */           return _get_homeHandle(localInputStream, paramResponseHandler);
/*  97:    */         }
/*  98:    */       case 16: 
/*  99: 93 */         if (paramString.equals("_get_EJBMetaData")) {
/* 100: 94 */           return _get_EJBMetaData(localInputStream, paramResponseHandler);
/* 101:    */         }
/* 102:    */       case 24: 
/* 103: 97 */         if (paramString.equals("remove__java_lang_Object")) {
/* 104: 98 */           return remove__java_lang_Object(localInputStream, paramResponseHandler);
/* 105:    */         }
/* 106: 99 */         if (paramString.equals("remove__javax_ejb_Handle")) {
/* 107:100 */           return remove__javax_ejb_Handle(localInputStream, paramResponseHandler);
/* 108:    */         }
/* 109:    */         break;
/* 110:    */       }
/* 111:103 */       throw new BAD_OPERATION();
/* 112:    */     }
/* 113:    */     catch (SystemException localSystemException)
/* 114:    */     {
/* 115:105 */       throw localSystemException;
/* 116:    */     }
/* 117:    */     catch (Throwable localThrowable)
/* 118:    */     {
/* 119:107 */       throw new UnknownException(localThrowable);
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   private org.omg.CORBA.portable.OutputStream remove__javax_ejb_Handle(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 124:    */     throws Throwable
/* 125:    */   {
/* 126:112 */     Handle localHandle = (Handle)paramInputStream.read_abstract_interface(Handle.class);
/* 127:    */     try
/* 128:    */     {
/* 129:114 */       this.target.remove(localHandle);
/* 130:    */     }
/* 131:    */     catch (RemoveException localRemoveException)
/* 132:    */     {
/* 133:116 */       String str = "IDL:javax/ejb/RemoveEx:1.0";
/* 134:117 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 135:118 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 136:119 */       localOutputStream1.write_string(str);
/* 137:120 */       localOutputStream1.write_value(localRemoveException, RemoveException.class);
/* 138:121 */       return localOutputStream1;
/* 139:    */     }
/* 140:123 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 141:124 */     return localOutputStream;
/* 142:    */   }
/* 143:    */   
/* 144:    */   private org.omg.CORBA.portable.OutputStream remove__java_lang_Object(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 145:    */     throws Throwable
/* 146:    */   {
/* 147:128 */     java.lang.Object localObject = Util.readAny(paramInputStream);
/* 148:    */     try
/* 149:    */     {
/* 150:130 */       this.target.remove(localObject);
/* 151:    */     }
/* 152:    */     catch (RemoveException localRemoveException)
/* 153:    */     {
/* 154:132 */       String str = "IDL:javax/ejb/RemoveEx:1.0";
/* 155:133 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 156:134 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 157:135 */       localOutputStream1.write_string(str);
/* 158:136 */       localOutputStream1.write_value(localRemoveException, RemoveException.class);
/* 159:137 */       return localOutputStream1;
/* 160:    */     }
/* 161:139 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 162:140 */     return localOutputStream;
/* 163:    */   }
/* 164:    */   
/* 165:    */   private org.omg.CORBA.portable.OutputStream _get_EJBMetaData(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 166:    */     throws Throwable
/* 167:    */   {
/* 168:144 */     EJBMetaData localEJBMetaData = this.target.getEJBMetaData();
/* 169:145 */     org.omg.CORBA_2_3.portable.OutputStream localOutputStream = 
/* 170:146 */       (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createReply();
/* 171:147 */     localOutputStream.write_value((Serializable)localEJBMetaData, EJBMetaData.class);
/* 172:148 */     return localOutputStream;
/* 173:    */   }
/* 174:    */   
/* 175:    */   private org.omg.CORBA.portable.OutputStream _get_homeHandle(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 176:    */     throws Throwable
/* 177:    */   {
/* 178:152 */     HomeHandle localHomeHandle = this.target.getHomeHandle();
/* 179:153 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 180:154 */     Util.writeAbstractObject(localOutputStream, localHomeHandle);
/* 181:155 */     return localOutputStream;
/* 182:    */   }
/* 183:    */   
/* 184:    */   private org.omg.CORBA.portable.OutputStream create(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 185:    */     throws Throwable
/* 186:    */   {
/* 187:    */     ACHBPWServices localACHBPWServices;
/* 188:    */     try
/* 189:    */     {
/* 190:161 */       localACHBPWServices = this.target.create();
/* 191:    */     }
/* 192:    */     catch (CreateException localCreateException)
/* 193:    */     {
/* 194:163 */       String str = "IDL:javax/ejb/CreateEx:1.0";
/* 195:164 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 196:165 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 197:166 */       localOutputStream1.write_string(str);
/* 198:167 */       localOutputStream1.write_value(localCreateException, CreateException.class);
/* 199:168 */       return localOutputStream1;
/* 200:    */     }
/* 201:170 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 202:171 */     Util.writeRemoteObject(localOutputStream, localACHBPWServices);
/* 203:172 */     return localOutputStream;
/* 204:    */   }
/* 205:    */ }


/* Location:           D:\drops\jd\jars\Deployed_ACHBPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.ACHServices._EJSRemoteStatelessACHBPWServicesHome_Tie
 * JD-Core Version:    0.7.0.1
 */