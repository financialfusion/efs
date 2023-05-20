/*   1:    */ package com.ffusion.banksim.adapter;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.db.DBConnection;
/*   4:    */ import com.ffusion.banksim.interfaces.BSException;
/*   5:    */ import java.sql.PreparedStatement;
/*   6:    */ import java.sql.ResultSet;
/*   7:    */ 
/*   8:    */ public class BSExtendABeanXML
/*   9:    */ {
/*  10:    */   private static final String a = "INSERT INTO BS_ExtendABeanXML( ExtendABeanID, XMLSegmentNumber, XMLSegment ) VALUES( ?, ?, ?)";
/*  11:    */   private static final String jdField_do = "DELETE FROM BS_ExtendABeanXML WHERE ExtendABeanID=?";
/*  12:    */   private static final String jdField_if = "SELECT XMLSegment FROM BS_ExtendABeanXML WHERE ExtendABeanID=? ORDER BY XMLSegmentNumber ASC";
/*  13:    */   
/*  14:    */   public static long addExtendABeanXML(DBConnection paramDBConnection, String paramString)
/*  15:    */     throws BSException
/*  16:    */   {
/*  17: 36 */     if ((paramString == null) || (paramString.length() == 0)) {
/*  18: 37 */       return 0L;
/*  19:    */     }
/*  20: 40 */     PreparedStatement localPreparedStatement = null;
/*  21:    */     try
/*  22:    */     {
/*  23: 43 */       long l1 = BSRecordCounter.getNextIndex(paramDBConnection, 3, String.valueOf(0), "ExtendABeanIndex");
/*  24:    */       
/*  25: 45 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "INSERT INTO BS_ExtendABeanXML( ExtendABeanID, XMLSegmentNumber, XMLSegment ) VALUES( ?, ?, ?)");
/*  26:    */       
/*  27: 47 */       int i = 1;
/*  28: 48 */       String str = null;
/*  29: 49 */       int j = 1;
/*  30: 50 */       while (j != 0)
/*  31:    */       {
/*  32: 51 */         if (paramString.length() > 2000)
/*  33:    */         {
/*  34: 52 */           str = paramString.substring(0, 2000);
/*  35: 53 */           paramString = paramString.substring(2000, paramString.length());
/*  36:    */         }
/*  37:    */         else
/*  38:    */         {
/*  39: 55 */           str = paramString;
/*  40: 56 */           j = 0;
/*  41:    */         }
/*  42: 58 */         localPreparedStatement.setLong(1, l1);
/*  43: 59 */         localPreparedStatement.setInt(2, i);
/*  44: 60 */         localPreparedStatement.setString(3, str);
/*  45: 61 */         DBConnection.executeUpdate(localPreparedStatement, "INSERT INTO BS_ExtendABeanXML( ExtendABeanID, XMLSegmentNumber, XMLSegment ) VALUES( ?, ?, ?)");
/*  46: 62 */         i++;
/*  47:    */       }
/*  48: 65 */       return l1;
/*  49:    */     }
/*  50:    */     catch (Exception localException)
/*  51:    */     {
/*  52: 68 */       throw new BSException(1, localException.getMessage());
/*  53:    */     }
/*  54:    */     finally
/*  55:    */     {
/*  56: 70 */       if (localPreparedStatement != null) {
/*  57: 71 */         DBConnection.closeStatement(localPreparedStatement);
/*  58:    */       }
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static void deleteExtendABeanXML(DBConnection paramDBConnection, long paramLong)
/*  63:    */     throws BSException
/*  64:    */   {
/*  65: 83 */     PreparedStatement localPreparedStatement = null;
/*  66:    */     try
/*  67:    */     {
/*  68: 85 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "DELETE FROM BS_ExtendABeanXML WHERE ExtendABeanID=?");
/*  69: 86 */       localPreparedStatement.setLong(1, paramLong);
/*  70: 87 */       DBConnection.executeUpdate(localPreparedStatement, "DELETE FROM BS_ExtendABeanXML WHERE ExtendABeanID=?");
/*  71:    */     }
/*  72:    */     catch (Exception localException)
/*  73:    */     {
/*  74: 90 */       throw new BSException(1, localException.getMessage());
/*  75:    */     }
/*  76:    */     finally
/*  77:    */     {
/*  78: 92 */       if (localPreparedStatement != null) {
/*  79: 93 */         DBConnection.closeStatement(localPreparedStatement);
/*  80:    */       }
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static String getExtendABeanXML(long paramLong, DBConnection paramDBConnection)
/*  85:    */     throws BSException
/*  86:    */   {
/*  87:108 */     if (paramLong == 0L) {
/*  88:109 */       return null;
/*  89:    */     }
/*  90:112 */     PreparedStatement localPreparedStatement = null;
/*  91:    */     try
/*  92:    */     {
/*  93:115 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT XMLSegment FROM BS_ExtendABeanXML WHERE ExtendABeanID=? ORDER BY XMLSegmentNumber ASC");
/*  94:116 */       localPreparedStatement.setLong(1, paramLong);
/*  95:117 */       ResultSet localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT XMLSegment FROM BS_ExtendABeanXML WHERE ExtendABeanID=? ORDER BY XMLSegmentNumber ASC");
/*  96:    */       
/*  97:119 */       StringBuffer localStringBuffer = new StringBuffer();
/*  98:120 */       while (localResultSet.next()) {
/*  99:122 */         localStringBuffer.append(localResultSet.getString(1));
/* 100:    */       }
/* 101:124 */       return localStringBuffer.toString();
/* 102:    */     }
/* 103:    */     catch (Exception localException)
/* 104:    */     {
/* 105:127 */       throw new BSException(1, localException.getMessage());
/* 106:    */     }
/* 107:    */     finally
/* 108:    */     {
/* 109:130 */       if (localPreparedStatement != null) {
/* 110:131 */         DBConnection.closeStatement(localPreparedStatement);
/* 111:    */       }
/* 112:134 */       if (paramDBConnection != null) {
/* 113:135 */         paramDBConnection.close();
/* 114:    */       }
/* 115:    */     }
/* 116:    */   }
/* 117:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.adapter.BSExtendABeanXML
 * JD-Core Version:    0.7.0.1
 */