/*   1:    */ package com.ffusion.ffs.db;
/*   2:    */ 
/*   3:    */ import com.ffusion.ffs.config.ConnPoolInfo;
/*   4:    */ import com.ffusion.ffs.config.DBConnInfo;
/*   5:    */ import com.ffusion.ffs.interfaces.FFSException;
/*   6:    */ import com.ffusion.ffs.util.FFSConst;
/*   7:    */ import com.ffusion.ffs.util.FFSProperties;
/*   8:    */ import com.ibm.wsspi.security.auth.callback.WSMappingCallbackHandlerFactory;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.Properties;
/*  11:    */ import java.util.Set;
/*  12:    */ import java.util.Vector;
/*  13:    */ import javax.resource.spi.security.PasswordCredential;
/*  14:    */ import javax.security.auth.Subject;
/*  15:    */ import javax.security.auth.callback.CallbackHandler;
/*  16:    */ import javax.security.auth.login.LoginContext;
/*  17:    */ 
/*  18:    */ public class ConnectionFactory
/*  19:    */   implements FFSDBConst, FFSConst
/*  20:    */ {
/*  21:    */   public static FFSConnection getNewConnection(FFSDBProperties paramFFSDBProperties)
/*  22:    */     throws FFSException
/*  23:    */   {
/*  24: 20 */     String str1 = null;
/*  25: 21 */     String str2 = null;
/*  26: 22 */     FFSConnection localFFSConnection = null;
/*  27: 23 */     if (paramFFSDBProperties != null)
/*  28:    */     {
/*  29: 25 */       str1 = paramFFSDBProperties._connInfo._dbConnInfo._connType;
/*  30: 26 */       str2 = paramFFSDBProperties._connInfo._dbConnInfo._databaseName;
/*  31:    */       
/*  32: 28 */       String dbuser = paramFFSDBProperties._connInfo._dbConnInfo._user;
/*  33: 29 */       System.out.println("+++ ConnectionFactory: paramFFSDBProperties._connInfo._dbConnInfo._user: " + dbuser);
/*  34: 31 */       if ((dbuser != null) && (dbuser.contains("/")))
/*  35:    */       {
/*  36: 33 */         Vector<String> authData = getDataSourceJ2CData(dbuser);
/*  37: 35 */         if (authData != null)
/*  38:    */         {
/*  39: 37 */           System.out.println("+++ ConnectionFactory: authData.size: " + authData.size());
/*  40:    */           
/*  41:    */ 
/*  42: 40 */           paramFFSDBProperties._connInfo._dbConnInfo._user = ((String)authData.get(0));
/*  43: 41 */           paramFFSDBProperties._connInfo._dbConnInfo._password = ((String)authData.get(1));
/*  44:    */           
/*  45: 43 */           paramFFSDBProperties._pureProps.setProperty("user", paramFFSDBProperties._connInfo._dbConnInfo._user);
/*  46: 44 */           paramFFSDBProperties._pureProps.setProperty("password", paramFFSDBProperties._connInfo._dbConnInfo._password);
/*  47:    */         }
/*  48:    */       }
/*  49:    */     }
/*  50:    */     else
/*  51:    */     {
/*  52: 56 */       throw new FFSException("Invalid connection properties?!");
/*  53:    */     }
/*  54: 60 */     if (str1 == null) {
/*  55: 61 */       throw new FFSException("Invalid connection type");
/*  56:    */     }
/*  57:    */     try
/*  58:    */     {
/*  59: 64 */       if (str1.equalsIgnoreCase("DRIVER_MANAGER")) {
/*  60: 65 */         localFFSConnection = DriverConnection.getNewConnection(paramFFSDBProperties);
/*  61: 66 */       } else if (str1.equalsIgnoreCase("DATA_SOURCE")) {
/*  62: 67 */         localFFSConnection = DataSourceConnection.getNewConnection(paramFFSDBProperties);
/*  63:    */       } else {
/*  64: 69 */         throw new FFSException("Invalid connection type");
/*  65:    */       }
/*  66: 70 */       System.out.println("+++ ConnectionFactory: new connection created...");
/*  67:    */     }
/*  68:    */     catch (Exception e)
/*  69:    */     {
/*  70: 72 */       e.printStackTrace();
/*  71:    */     }
/*  72: 74 */     return localFFSConnection;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static FFSConnection getNewConnection(FFSProperties paramFFSProperties)
/*  76:    */     throws FFSException
/*  77:    */   {
/*  78: 80 */     FFSDBProperties localFFSDBProperties = new FFSDBProperties(paramFFSProperties);
/*  79: 81 */     return getNewConnection(localFFSDBProperties);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static FFSConnection getNewConnection(String paramString)
/*  83:    */     throws FFSException
/*  84:    */   {
/*  85: 87 */     FFSProperties localFFSProperties = new FFSProperties(paramString);
/*  86: 88 */     FFSDBProperties localFFSDBProperties = new FFSDBProperties(localFFSProperties);
/*  87: 89 */     return getNewConnection(localFFSDBProperties);
/*  88:    */   }
/*  89:    */   
/*  90:    */   private static Vector<String> getDataSourceJ2CData(String authDataAlias)
/*  91:    */   {
/*  92: 94 */     Vector j2cData = new Vector();
/*  93:    */     try
/*  94:    */     {
/*  95: 96 */       Properties properties = new Properties();
/*  96: 97 */       properties.put("com.ibm.mapping.authDataAlias", authDataAlias);
/*  97: 98 */       CallbackHandler callbackHandler = null;
/*  98: 99 */       callbackHandler = WSMappingCallbackHandlerFactory.getInstance().getCallbackHandler(properties, null);
/*  99:100 */       LoginContext lc = new LoginContext("DefaultPrincipalMapping", callbackHandler);
/* 100:101 */       lc.login();
/* 101:102 */       Subject subject = lc.getSubject();
/* 102:103 */       Set creds = subject.getPrivateCredentials();
/* 103:104 */       PasswordCredential result = (PasswordCredential)creds.toArray()[0];
/* 104:105 */       j2cData.add(result.getUserName());
/* 105:106 */       j2cData.add(new String(result.getPassword()));
/* 106:    */     }
/* 107:    */     catch (Exception exception)
/* 108:    */     {
/* 109:109 */       exception.printStackTrace();
/* 110:    */     }
/* 111:111 */     return j2cData;
/* 112:    */   }
/* 113:    */ }


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.db.ConnectionFactory
 * JD-Core Version:    0.7.0.1
 */