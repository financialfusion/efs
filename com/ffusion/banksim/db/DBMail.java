/*   1:    */ package com.ffusion.banksim.db;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.interfaces.BSException;
/*   4:    */ import com.ffusion.beans.DateTime;
/*   5:    */ import com.ffusion.beans.accounts.Account;
/*   6:    */ import com.ffusion.beans.accounts.Accounts;
/*   7:    */ import com.ffusion.beans.messages.Message;
/*   8:    */ import com.ffusion.beans.messages.Messages;
/*   9:    */ import com.ffusion.beans.user.User;
/*  10:    */ import java.sql.SQLException;
/*  11:    */ import java.util.Enumeration;
/*  12:    */ import java.util.Vector;
/*  13:    */ 
/*  14:    */ public class DBMail
/*  15:    */ {
/*  16:    */   private static final String jdField_if = "insert into BS_Mail( {||MessageID,|MessageID,|||MessageID,} CustomerID, SentDate, SentFrom, SentTo, Subject, Message, AccountNumber ) values( {||NEXTVAL FOR BS_MessageIDSequence,|BS_MessageIDSequence.NEXTVAL,|||NEXTVAL FOR BS_MessageIDSequence,} ?,?,?,?,?,?,?)";
/*  17:    */   private static final String a = "Select MessageID, SentDate, SentFrom, SentTo, Subject, Message, AccountNumber FROM BS_Mail WHERE CustomerID in (Select CustomerID from BS_Customer where UserID = ?)";
/*  18:    */   
/*  19:    */   public static final void addMailMessage(User paramUser, Message paramMessage, DBConnection paramDBConnection)
/*  20:    */     throws BSException
/*  21:    */   {
/*  22: 51 */     boolean bool = false;
/*  23:    */     try
/*  24:    */     {
/*  25: 56 */       bool = paramDBConnection.isAutoCommit();
/*  26: 57 */       if (bool) {
/*  27: 57 */         paramDBConnection.setAutoCommit(false);
/*  28:    */       }
/*  29: 60 */       if (!DBCustomer.doesCustomerExist(paramUser.getUserName(), paramDBConnection)) {
/*  30: 61 */         throw new BSException(1008, MessageText.getMessage("ERR_CUSTOMER_NOT_EXISTS"));
/*  31:    */       }
/*  32: 65 */       String str = null;
/*  33: 66 */       if (paramMessage.containsKey("BS_ACCOUNT_NUMBER"))
/*  34:    */       {
/*  35: 67 */         str = (String)paramMessage.get("BS_ACCOUNT_NUMBER");
/*  36:    */         
/*  37: 69 */         localObject1 = new Accounts();
/*  38:    */         
/*  39: 71 */         Account localAccount = ((Accounts)localObject1).create(str, 0);
/*  40: 76 */         if (!DBAccount.a(localAccount, paramDBConnection)) {
/*  41: 77 */           throw new BSException(1011, MessageText.getMessage("ERR_ACCOUNT_NOT_EXISTS"));
/*  42:    */         }
/*  43:    */       }
/*  44: 82 */       Object localObject1 = { paramUser.getId(), new Long(System.currentTimeMillis()), paramMessage.getFrom(), paramMessage.getTo(), paramMessage.getSubject(), paramMessage.getMemo(), str };
/*  45:    */       
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54: 92 */       paramDBConnection.executeUpdate("insert into BS_Mail( {||MessageID,|MessageID,|||MessageID,} CustomerID, SentDate, SentFrom, SentTo, Subject, Message, AccountNumber ) values( {||NEXTVAL FOR BS_MessageIDSequence,|BS_MessageIDSequence.NEXTVAL,|||NEXTVAL FOR BS_MessageIDSequence,} ?,?,?,?,?,?,?)", (Object[])localObject1);
/*  55:    */       
/*  56: 94 */       paramDBConnection.commit();
/*  57:    */     }
/*  58:    */     catch (SQLException localSQLException1)
/*  59:    */     {
/*  60:    */       try
/*  61:    */       {
/*  62: 99 */         paramDBConnection.rollback();
/*  63:    */       }
/*  64:    */       catch (SQLException localSQLException2) {}
/*  65:104 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException1));
/*  66:    */     }
/*  67:    */     finally
/*  68:    */     {
/*  69:    */       try
/*  70:    */       {
/*  71:109 */         if (bool) {
/*  72:109 */           paramDBConnection.setAutoCommit(true);
/*  73:    */         }
/*  74:    */       }
/*  75:    */       catch (SQLException localSQLException3)
/*  76:    */       {
/*  77:111 */         throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException3));
/*  78:    */       }
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static final Enumeration getMailMessages(User paramUser, DBConnection paramDBConnection)
/*  83:    */     throws BSException
/*  84:    */   {
/*  85:129 */     Enumeration localEnumeration = null;
/*  86:    */     try
/*  87:    */     {
/*  88:132 */       if (!DBCustomer.doesCustomerExist(paramUser.getUserName(), paramDBConnection)) {
/*  89:133 */         throw new BSException(1008, MessageText.getMessage("ERR_CUSTOMER_NOT_EXISTS"));
/*  90:    */       }
/*  91:137 */       DBResultSet localDBResultSet = paramDBConnection.prepareQuery("Select MessageID, SentDate, SentFrom, SentTo, Subject, Message, AccountNumber FROM BS_Mail WHERE CustomerID in (Select CustomerID from BS_Customer where UserID = ?)");
/*  92:138 */       Object[] arrayOfObject = { paramUser.getUserName() };
/*  93:139 */       localDBResultSet.open(arrayOfObject);
/*  94:    */       
/*  95:141 */       Vector localVector = new Vector();
/*  96:    */       
/*  97:143 */       Messages localMessages = new Messages();
/*  98:144 */       Message localMessage = null;
/*  99:145 */       DateTime localDateTime = null;
/* 100:146 */       while (localDBResultSet.getNextRow())
/* 101:    */       {
/* 102:147 */         localMessage = localMessages.create();
/* 103:148 */         localMessage.setID(localDBResultSet.getColumnString(1));
/* 104:    */         
/* 105:150 */         localDateTime = new DateTime();
/* 106:151 */         localDateTime.setDate(localDBResultSet.getColumnString(2));
/* 107:152 */         localMessage.setDate(localDateTime);
/* 108:    */         
/* 109:154 */         localMessage.setFrom(localDBResultSet.getColumnString(3));
/* 110:155 */         localMessage.setTo(localDBResultSet.getColumnString(4));
/* 111:156 */         localMessage.setSubject(localDBResultSet.getColumnString(5));
/* 112:157 */         localMessage.setMemo(localDBResultSet.getColumnString(6));
/* 113:160 */         if (localDBResultSet.getColumnString(7) != null) {
/* 114:161 */           localMessage.put("BS_ACCOUNT_NUMBER", localDBResultSet.getColumnString(7));
/* 115:    */         }
/* 116:164 */         localVector.add(localMessage);
/* 117:    */       }
/* 118:166 */       localDBResultSet.close();
/* 119:167 */       localEnumeration = localVector.elements();
/* 120:    */     }
/* 121:    */     catch (SQLException localSQLException)
/* 122:    */     {
/* 123:169 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException));
/* 124:    */     }
/* 125:172 */     return localEnumeration;
/* 126:    */   }
/* 127:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.db.DBMail
 * JD-Core Version:    0.7.0.1
 */