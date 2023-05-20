/*   1:    */ package com.ibm.ejs.container;
/*   2:    */ 
/*   3:    */ import java.rmi.Remote;
/*   4:    */ import javax.ejb.EJBHome;
/*   5:    */ import javax.ejb.EJBObject;
/*   6:    */ import javax.ejb.Handle;
/*   7:    */ import javax.ejb.RemoveException;
/*   8:    */ import javax.rmi.CORBA.Tie;
/*   9:    */ import javax.rmi.CORBA.Util;
/*  10:    */ import org.omg.CORBA.BAD_OPERATION;
/*  11:    */ import org.omg.CORBA.ORB;
/*  12:    */ import org.omg.CORBA.SystemException;
/*  13:    */ import org.omg.CORBA.portable.Delegate;
/*  14:    */ import org.omg.CORBA.portable.ResponseHandler;
/*  15:    */ import org.omg.CORBA.portable.UnknownException;
/*  16:    */ 
/*  17:    */ public class _EJSWrapper_Tie
/*  18:    */   extends org.omg.CORBA_2_3.portable.ObjectImpl
/*  19:    */   implements Tie
/*  20:    */ {
/*  21: 27 */   private EJSWrapper target = null;
/*  22: 28 */   private ORB orb = null;
/*  23: 30 */   private static final String[] _type_ids = {
/*  24: 31 */     "RMI:com.ibm.ejs.container.EJSWrapper:0000000000000000", 
/*  25: 32 */     "RMI:com.ibm.websphere.csi.CSIServant:0000000000000000", 
/*  26: 33 */     "RMI:com.ibm.websphere.csi.TransactionalObject:0000000000000000", 
/*  27: 34 */     "RMI:javax.ejb.EJBObject:0000000000000000" };
/*  28:    */   
/*  29:    */   public void setTarget(Remote paramRemote)
/*  30:    */   {
/*  31: 38 */     this.target = ((EJSWrapper)paramRemote);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Remote getTarget()
/*  35:    */   {
/*  36: 42 */     return this.target;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public org.omg.CORBA.Object thisObject()
/*  40:    */   {
/*  41: 46 */     return this;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void deactivate()
/*  45:    */   {
/*  46: 50 */     if (this.orb != null)
/*  47:    */     {
/*  48: 51 */       this.orb.disconnect(this);
/*  49: 52 */       _set_delegate(null);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public ORB orb()
/*  54:    */   {
/*  55: 57 */     return _orb();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void orb(ORB paramORB)
/*  59:    */   {
/*  60: 61 */     paramORB.connect(this);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void _set_delegate(Delegate paramDelegate)
/*  64:    */   {
/*  65: 65 */     super._set_delegate(paramDelegate);
/*  66: 66 */     if (paramDelegate != null) {
/*  67: 67 */       this.orb = _orb();
/*  68:    */     } else {
/*  69: 69 */       this.orb = null;
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String[] _ids()
/*  74:    */   {
/*  75: 73 */     return _type_ids;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public org.omg.CORBA.portable.OutputStream _invoke(String paramString, org.omg.CORBA.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/*  79:    */     throws SystemException
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83: 78 */       org.omg.CORBA_2_3.portable.InputStream localInputStream = 
/*  84: 79 */         (org.omg.CORBA_2_3.portable.InputStream)paramInputStream;
/*  85: 80 */       switch (paramString.charAt(5))
/*  86:    */       {
/*  87:    */       case 'E': 
/*  88: 82 */         if (paramString.equals("_get_EJBHome")) {
/*  89: 83 */           return _get_EJBHome(localInputStream, paramResponseHandler);
/*  90:    */         }
/*  91:    */       case 'e': 
/*  92: 86 */         if (paramString.equals("remove")) {
/*  93: 87 */           return remove(localInputStream, paramResponseHandler);
/*  94:    */         }
/*  95:    */       case 'h': 
/*  96: 90 */         if (paramString.equals("_get_handle")) {
/*  97: 91 */           return _get_handle(localInputStream, paramResponseHandler);
/*  98:    */         }
/*  99:    */       case 'l': 
/* 100: 94 */         if (paramString.equals("wlmable")) {
/* 101: 95 */           return wlmable(localInputStream, paramResponseHandler);
/* 102:    */         }
/* 103:    */       case 'n': 
/* 104: 98 */         if (paramString.equals("isIdentical")) {
/* 105: 99 */           return isIdentical(localInputStream, paramResponseHandler);
/* 106:    */         }
/* 107:    */       case 'p': 
/* 108:102 */         if (paramString.equals("_get_primaryKey")) {
/* 109:103 */           return _get_primaryKey(localInputStream, paramResponseHandler);
/* 110:    */         }
/* 111:    */         break;
/* 112:    */       }
/* 113:106 */       throw new BAD_OPERATION();
/* 114:    */     }
/* 115:    */     catch (SystemException localSystemException)
/* 116:    */     {
/* 117:108 */       throw localSystemException;
/* 118:    */     }
/* 119:    */     catch (Throwable localThrowable)
/* 120:    */     {
/* 121:110 */       throw new UnknownException(localThrowable);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   private org.omg.CORBA.portable.OutputStream wlmable(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 126:    */     throws Throwable
/* 127:    */   {
/* 128:115 */     boolean bool = this.target.wlmable();
/* 129:116 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 130:117 */     localOutputStream.write_boolean(bool);
/* 131:118 */     return localOutputStream;
/* 132:    */   }
/* 133:    */   
/* 134:    */   private org.omg.CORBA.portable.OutputStream _get_EJBHome(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 135:    */     throws Throwable
/* 136:    */   {
/* 137:122 */     EJBHome localEJBHome = this.target.getEJBHome();
/* 138:123 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 139:124 */     Util.writeRemoteObject(localOutputStream, localEJBHome);
/* 140:125 */     return localOutputStream;
/* 141:    */   }
/* 142:    */   
/* 143:    */   private org.omg.CORBA.portable.OutputStream _get_primaryKey(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 144:    */     throws Throwable
/* 145:    */   {
/* 146:129 */     java.lang.Object localObject = this.target.getPrimaryKey();
/* 147:130 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 148:131 */     Util.writeAny(localOutputStream, localObject);
/* 149:132 */     return localOutputStream;
/* 150:    */   }
/* 151:    */   
/* 152:    */   private org.omg.CORBA.portable.OutputStream remove(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 153:    */     throws Throwable
/* 154:    */   {
/* 155:    */     try
/* 156:    */     {
/* 157:137 */       this.target.remove();
/* 158:    */     }
/* 159:    */     catch (RemoveException localRemoveException)
/* 160:    */     {
/* 161:139 */       String str = "IDL:javax/ejb/RemoveEx:1.0";
/* 162:140 */       org.omg.CORBA_2_3.portable.OutputStream localOutputStream1 = 
/* 163:141 */         (org.omg.CORBA_2_3.portable.OutputStream)paramResponseHandler.createExceptionReply();
/* 164:142 */       localOutputStream1.write_string(str);
/* 165:143 */       localOutputStream1.write_value(localRemoveException, RemoveException.class);
/* 166:144 */       return localOutputStream1;
/* 167:    */     }
/* 168:146 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 169:147 */     return localOutputStream;
/* 170:    */   }
/* 171:    */   
/* 172:    */   private org.omg.CORBA.portable.OutputStream _get_handle(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 173:    */     throws Throwable
/* 174:    */   {
/* 175:151 */     Handle localHandle = this.target.getHandle();
/* 176:152 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 177:153 */     Util.writeAbstractObject(localOutputStream, localHandle);
/* 178:154 */     return localOutputStream;
/* 179:    */   }
/* 180:    */   
/* 181:    */   private org.omg.CORBA.portable.OutputStream isIdentical(org.omg.CORBA_2_3.portable.InputStream paramInputStream, ResponseHandler paramResponseHandler)
/* 182:    */     throws Throwable
/* 183:    */   {
/* 184:158 */     EJBObject localEJBObject = (EJBObject)paramInputStream.read_Object(EJBObject.class);
/* 185:159 */     boolean bool = this.target.isIdentical(localEJBObject);
/* 186:160 */     org.omg.CORBA.portable.OutputStream localOutputStream = paramResponseHandler.createReply();
/* 187:161 */     localOutputStream.write_boolean(bool);
/* 188:162 */     return localOutputStream;
/* 189:    */   }
/* 190:    */ }


/* Location:           D:\drops\jd\jars\Deployed_OFX200BPWServices.jar
 * Qualified Name:     com.ibm.ejs.container._EJSWrapper_Tie
 * JD-Core Version:    0.7.0.1
 */