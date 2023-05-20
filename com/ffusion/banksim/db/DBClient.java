/*  1:   */ package com.ffusion.banksim.db;
/*  2:   */ 
/*  3:   */ import com.ffusion.banksim.interfaces.BSException;
/*  4:   */ import com.ffusion.beans.user.User;
/*  5:   */ import java.sql.SQLException;
/*  6:   */ 
/*  7:   */ public class DBClient
/*  8:   */ {
/*  9:   */   private static final String a = "Select count(*) from BS_Customer where UserID = ?";
/* 10:   */   private static final String jdField_if = "Select CustomerID, UserID, Password, FirstName, MiddleName, LastName, Address1, Address2, City, State, PostalCode, Country, DayPhone, EveningPhone, EMailAddress from BS_Customer where UserID = ?";
/* 11:   */   
/* 12:   */   public static final User signOn(String paramString1, String paramString2, DBConnection paramDBConnection)
/* 13:   */     throws BSException
/* 14:   */   {
/* 15:39 */     User localUser = null;
/* 16:40 */     DBResultSet localDBResultSet = null;
/* 17:   */     try
/* 18:   */     {
/* 19:42 */       Object[] arrayOfObject = { paramString1 };
/* 20:   */       
/* 21:   */ 
/* 22:   */ 
/* 23:   */ 
/* 24:47 */       localDBResultSet = paramDBConnection.prepareQuery("Select count(*) from BS_Customer where UserID = ?");
/* 25:48 */       localDBResultSet.open(arrayOfObject);
/* 26:49 */       int i = 0;
/* 27:50 */       if (localDBResultSet.getNextRow()) {
/* 28:51 */         i = localDBResultSet.getColumnInt(1);
/* 29:   */       }
/* 30:53 */       localDBResultSet.close();
/* 31:54 */       int j = 0;
/* 32:55 */       if (i > 0)
/* 33:   */       {
/* 34:56 */         localDBResultSet = paramDBConnection.prepareQuery("Select CustomerID, UserID, Password, FirstName, MiddleName, LastName, Address1, Address2, City, State, PostalCode, Country, DayPhone, EveningPhone, EMailAddress from BS_Customer where UserID = ?");
/* 35:57 */         localDBResultSet.open(arrayOfObject);
/* 36:59 */         while (localDBResultSet.getNextRow())
/* 37:   */         {
/* 38:60 */           localUser = new User();
/* 39:61 */           if (i > 1)
/* 40:   */           {
/* 41:62 */             String str = localDBResultSet.getColumnString(3);
/* 42:63 */             if (!str.equals(paramString2)) {
/* 43:   */               break;
/* 44:   */             }
/* 45:   */           }
/* 46:   */           else
/* 47:   */           {
/* 48:66 */             j = 1;
/* 49:67 */             localUser.setId(localDBResultSet.getColumnString(1));
/* 50:68 */             localUser.setUserName(localDBResultSet.getColumnString(2));
/* 51:69 */             localUser.setPassword(localDBResultSet.getColumnString(3));
/* 52:70 */             localUser.setFirstName(localDBResultSet.getColumnString(4));
/* 53:71 */             localUser.setMiddleName(localDBResultSet.getColumnString(5));
/* 54:72 */             localUser.setLastName(localDBResultSet.getColumnString(6));
/* 55:73 */             localUser.setStreet(localDBResultSet.getColumnString(7));
/* 56:74 */             localUser.setStreet2(localDBResultSet.getColumnString(8));
/* 57:75 */             localUser.setCity(localDBResultSet.getColumnString(9));
/* 58:76 */             localUser.setState(localDBResultSet.getColumnString(10));
/* 59:77 */             localUser.setZipCode(localDBResultSet.getColumnString(11));
/* 60:78 */             localUser.setCountry(localDBResultSet.getColumnString(12));
/* 61:79 */             localUser.setPhone(localDBResultSet.getColumnString(13));
/* 62:80 */             localUser.setPhone2(localDBResultSet.getColumnString(14));
/* 63:81 */             localUser.setEmail(localDBResultSet.getColumnString(15));
/* 64:   */           }
/* 65:   */         }
/* 66:84 */         localDBResultSet.close();
/* 67:   */       }
/* 68:88 */       if (j == 0)
/* 69:   */       {
/* 70:89 */         localDBResultSet.close();
/* 71:90 */         throw new BSException(1008, MessageText.getMessage("ERR_USERID_NOT_EXISTS"));
/* 72:   */       }
/* 73:   */     }
/* 74:   */     catch (SQLException localSQLException)
/* 75:   */     {
/* 76:94 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException));
/* 77:   */     }
/* 78:97 */     return localUser;
/* 79:   */   }
/* 80:   */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.db.DBClient
 * JD-Core Version:    0.7.0.1
 */