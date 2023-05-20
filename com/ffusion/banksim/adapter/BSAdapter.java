/*   1:    */ package com.ffusion.banksim.adapter;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.db.DBConnection;
/*   4:    */ import com.ffusion.banksim.db.DBTransaction;
/*   5:    */ import com.ffusion.banksim.interfaces.BSConstants;
/*   6:    */ import com.ffusion.banksim.interfaces.BSDBParams;
/*   7:    */ import com.ffusion.banksim.interfaces.BSException;
/*   8:    */ import com.ffusion.beans.accounts.Account;
/*   9:    */ import com.ffusion.beans.banking.Transaction;
/*  10:    */ import com.ffusion.util.db.DBUtil;
/*  11:    */ import java.sql.PreparedStatement;
/*  12:    */ import java.sql.ResultSet;
/*  13:    */ import java.util.HashMap;
/*  14:    */ 
/*  15:    */ public class BSAdapter
/*  16:    */ {
/*  17: 33 */   private static String jdField_do = null;
/*  18: 35 */   public static String CONNECTIONTYPE = null;
/*  19: 37 */   public static BSDBParams dbProps = null;
/*  20:    */   private static final String a = "SELECT AccountID FROM BS_Account WHERE AccountNumber=? AND RoutingNum=?";
/*  21:    */   private static final String jdField_if = "SELECT LockboxID FROM BS_Lockbox WHERE AccountID=? AND LockboxNumber=?";
/*  22:    */   public static final int LIVE_DATA = 1;
/*  23:    */   public static final int BAI_DATA = 2;
/*  24:    */   
/*  25:    */   public static String getAccountID(DBConnection paramDBConnection, String paramString1, String paramString2)
/*  26:    */     throws Exception
/*  27:    */   {
/*  28: 57 */     PreparedStatement localPreparedStatement = null;
/*  29:    */     try
/*  30:    */     {
/*  31: 60 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT AccountID FROM BS_Account WHERE AccountNumber=? AND RoutingNum=?");
/*  32: 61 */       localPreparedStatement.setString(1, paramString1);
/*  33: 62 */       localPreparedStatement.setString(2, paramString2);
/*  34: 63 */       ResultSet localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT AccountID FROM BS_Account WHERE AccountNumber=? AND RoutingNum=?");
/*  35: 64 */       String str1 = "-1";
/*  36: 66 */       if (localResultSet.next()) {
/*  37: 67 */         str1 = localResultSet.getString(BSConstants.ACCOUNTID);
/*  38:    */       }
/*  39: 69 */       DBUtil.closeResultSet(localResultSet);
/*  40: 70 */       return str1;
/*  41:    */     }
/*  42:    */     catch (Exception localException)
/*  43:    */     {
/*  44: 73 */       throw new BSException(1, "Error occurred while trying to get AccountID.");
/*  45:    */     }
/*  46:    */     finally
/*  47:    */     {
/*  48: 75 */       if (localPreparedStatement != null) {
/*  49: 76 */         DBConnection.closeStatement(localPreparedStatement);
/*  50:    */       }
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static int getLockboxID(DBConnection paramDBConnection, String paramString1, String paramString2)
/*  55:    */     throws Exception
/*  56:    */   {
/*  57: 86 */     PreparedStatement localPreparedStatement = null;
/*  58:    */     try
/*  59:    */     {
/*  60: 89 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT LockboxID FROM BS_Lockbox WHERE AccountID=? AND LockboxNumber=?");
/*  61: 90 */       localPreparedStatement.setString(1, paramString1);
/*  62: 91 */       localPreparedStatement.setString(2, paramString2);
/*  63: 92 */       ResultSet localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT LockboxID FROM BS_Lockbox WHERE AccountID=? AND LockboxNumber=?");
/*  64: 93 */       int i = -1;
/*  65: 95 */       if (localResultSet.next()) {
/*  66: 96 */         i = localResultSet.getInt(BSConstants.LOCKBOXID);
/*  67:    */       }
/*  68: 98 */       DBUtil.closeResultSet(localResultSet);
/*  69: 99 */       return i;
/*  70:    */     }
/*  71:    */     catch (Exception localException)
/*  72:    */     {
/*  73:102 */       throw new BSException(1, "Error occurred while trying to get LockboxID.");
/*  74:    */     }
/*  75:    */     finally
/*  76:    */     {
/*  77:104 */       if (localPreparedStatement != null) {
/*  78:105 */         DBConnection.closeStatement(localPreparedStatement);
/*  79:    */       }
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static void addTransaction(Account paramAccount, Transaction paramTransaction, HashMap paramHashMap)
/*  84:    */     throws Exception
/*  85:    */   {
/*  86:118 */     DBTransaction.addTransaction(paramAccount, paramTransaction, paramHashMap);
/*  87:    */   }
/*  88:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.adapter.BSAdapter
 * JD-Core Version:    0.7.0.1
 */