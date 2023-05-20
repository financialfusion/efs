package com.ffusion.tw.db;

import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.tw.TWTransactions;
import com.ffusion.tw.TransactionWarehouse;
import com.ffusion.tw.interfaces.TWException;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLable;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class Transaction
{
  private static final int i = 2000;
  private static final int jdField_byte = 254;
  private static final int j = 1000000;
  private static final String jdField_case = "TW";
  private static final String jdField_goto = "TW_Transaction";
  private static final String b = "twID, trackingID, userID, transactionType, dtSubmit,amount, userName, password, transDetailXML";
  private static final String jdField_null = "twID, ordinal, transDetailXML";
  private static final String c = "INSERT INTO TW_Transaction ( twID, trackingID, userID, transactionType, dtSubmit,amount, userName, password, transDetailXML ) VALUES (?,?,?,?,?,?,?,?,?)";
  private static final String e = "INSERT INTO TW_Transaction ( twID, trackingID, userID, transactionType, dtSubmit,amount, userName, password, transDetailXML ) VALUES (?,?,?,?,?,?,null,null,?)";
  private static final String h = "INSERT INTO TW_TransDetail ( twID, ordinal, transDetailXML ) VALUES (?,?,?)";
  private static final String jdField_long = "UPDATE TW_Transaction SET amount = ?, transDetailXML = ? WHERE twID = ?";
  private static final String jdField_if = "UPDATE TW_Transaction SET userid = ?, amount = ?, transDetailXML = ? WHERE twID = ?";
  private static final String d = "SELECT twID, trackingID, userID, transactionType, dtSubmit,amount, userName, password, transDetailXML FROM TW_Transaction WHERE twID = ?";
  private static final String g = "SELECT transDetailXML FROM TW_TransDetail WHERE twID = ? ORDER BY ordinal";
  private static final String jdField_else = "SELECT twID FROM TW_Transaction WHERE transactionType = ? AND trackingID = ? ORDER BY dtSubmit DESC ";
  private static final String jdField_for = "SELECT twID FROM TW_Transaction WHERE trackingID = ? ORDER BY dtSubmit DESC ";
  private static final String jdField_new = "SELECT twID FROM TW_Transaction WHERE transactionType = ? order by twID";
  private static final String a = "SELECT twID FROM TW_Transaction WHERE userID = ? order by twID";
  private static final String jdField_try = "SELECT twID FROM TW_Transaction WHERE userID = ? AND transactionType = ? order by twID";
  private static final String f = "UPDATE TW_TransDetail set twID=twID where twID = ?";
  private static final String jdField_char = "DELETE FROM TW_Transaction WHERE twID = ?";
  private static final String jdField_int = "DELETE FROM TW_TransDetail WHERE twID = ?";
  private static final String jdField_do = "AND transactionType NOT IN (1001,1007,1005,1003)";
  private static final String jdField_void = "DELETE FROM TW_Transaction WHERE dtSubmit <= ? AND transactionType NOT IN (1001,1007,1005,1003)";
  
  public static TWTransaction addTransaction(int paramInt, IApprovable paramIApprovable, String paramString1, String paramString2)
    throws TWException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    TWTransaction localTWTransaction = null;
    try
    {
      if (paramIApprovable == null) {
        throw new TWException(108, " The transaction could not be added. ");
      }
      int k = paramIApprovable.getApprovalSubType();
      localObject1 = a(paramIApprovable, k);
      String str1 = paramIApprovable.getApprovalAmount().getString();
      String str2 = jdField_if(paramIApprovable, k);
      int m = 0;
      String str3 = str2;
      int n = str3.length();
      if (str2.length() >= 2000)
      {
        n = Strings.getLastNonSpaceCharIndex(str2, 0, 2000);
        str3 = str2.substring(0, n);
        m = 1;
      }
      localConnection = DBUtil.getConnection(paramString1, true, 2);
      DBUtil.beginTransaction(localConnection);
      int i1 = DBUtil.getNextId(localConnection, paramString2, "TW");
      Timestamp localTimestamp = new Timestamp(System.currentTimeMillis());
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "INSERT INTO TW_Transaction ( twID, trackingID, userID, transactionType, dtSubmit,amount, userName, password, transDetailXML ) VALUES (?,?,?,?,?,?,null,null,?)");
      localPreparedStatement.setInt(1, i1);
      localPreparedStatement.setString(2, (String)localObject1);
      localPreparedStatement.setInt(3, paramInt);
      localPreparedStatement.setInt(4, k);
      localPreparedStatement.setTimestamp(5, localTimestamp);
      localPreparedStatement.setString(6, str1);
      localPreparedStatement.setString(7, str3);
      DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO TW_Transaction ( twID, trackingID, userID, transactionType, dtSubmit,amount, userName, password, transDetailXML ) VALUES (?,?,?,?,?,?,null,null,?)");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      if (m != 0) {
        a(str2, i1, localConnection, n);
      }
      DBUtil.commit(localConnection);
      localTWTransaction = a(i1, (String)localObject1, paramInt, k, localTimestamp, str1, null, null, paramIApprovable);
      str2 = null;
      str3 = null;
    }
    catch (Throwable localThrowable)
    {
      Object localObject1;
      DBUtil.rollback(localConnection);
      if ((localThrowable instanceof SQLException)) {
        throw new TWException(localThrowable, 1, " The transaction could not be added. ");
      }
      if ((localThrowable instanceof TWException))
      {
        localObject1 = (TWException)localThrowable;
        if (((TWException)localObject1).getErrorMessage() == null) {
          ((TWException)localObject1).setErrorMessage(" The transaction could not be added. ");
        }
        throw ((Throwable)localObject1);
      }
      throw new TWException(localThrowable, 0, " The transaction could not be added. ");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.returnConnection(paramString1, localConnection);
    }
    return localTWTransaction;
  }
  
  public static TWTransaction addTransaction(int paramInt, String paramString1, String paramString2, IApprovable paramIApprovable, String paramString3, String paramString4)
    throws TWException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    TWTransaction localTWTransaction = null;
    try
    {
      if (paramIApprovable == null) {
        throw new TWException(108, " The transaction could not be added. ");
      }
      int k = paramIApprovable.getApprovalSubType();
      localObject1 = a(paramIApprovable, k);
      String str1 = paramIApprovable.getApprovalAmount().getString();
      String str2 = jdField_if(paramIApprovable, k);
      int m = 0;
      String str3 = str2;
      int n = str3.length();
      if (str2.length() >= 2000)
      {
        n = Strings.getLastNonSpaceCharIndex(str2, 0, 2000);
        str3 = str2.substring(0, n);
        m = 1;
      }
      localConnection = DBUtil.getConnection(paramString3, true, 2);
      DBUtil.beginTransaction(localConnection);
      int i1 = DBUtil.getNextId(localConnection, paramString4, "TW");
      Timestamp localTimestamp = new Timestamp(System.currentTimeMillis());
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "INSERT INTO TW_Transaction ( twID, trackingID, userID, transactionType, dtSubmit,amount, userName, password, transDetailXML ) VALUES (?,?,?,?,?,?,?,?,?)");
      localPreparedStatement.setInt(1, i1);
      localPreparedStatement.setString(2, (String)localObject1);
      localPreparedStatement.setInt(3, paramInt);
      localPreparedStatement.setInt(4, k);
      localPreparedStatement.setTimestamp(5, localTimestamp);
      localPreparedStatement.setString(6, str1);
      localPreparedStatement.setString(7, paramString1);
      localPreparedStatement.setString(8, paramString2);
      localPreparedStatement.setString(9, str3);
      DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO TW_Transaction ( twID, trackingID, userID, transactionType, dtSubmit,amount, userName, password, transDetailXML ) VALUES (?,?,?,?,?,?,?,?,?)");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      if (m != 0) {
        a(str2, i1, localConnection, n);
      }
      DBUtil.commit(localConnection);
      localTWTransaction = a(i1, (String)localObject1, paramInt, k, localTimestamp, str1, paramString1, paramString2, paramIApprovable);
      str3 = null;
      str2 = null;
    }
    catch (Throwable localThrowable)
    {
      Object localObject1;
      DBUtil.rollback(localConnection);
      if ((localThrowable instanceof SQLException)) {
        throw new TWException(localThrowable, 1, " The transaction could not be added. ");
      }
      if ((localThrowable instanceof TWException))
      {
        localObject1 = (TWException)localThrowable;
        if (((TWException)localObject1).getErrorMessage() == null) {
          ((TWException)localObject1).setErrorMessage(" The transaction could not be added. ");
        }
        throw ((Throwable)localObject1);
      }
      throw new TWException(localThrowable, 0, " The transaction could not be added. ");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.returnConnection(paramString3, localConnection);
    }
    return localTWTransaction;
  }
  
  public static void deleteTransaction(int paramInt, String paramString)
    throws TWException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(paramString, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "DELETE FROM TW_TransDetail WHERE twID = ?");
      localPreparedStatement.setInt(1, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM TW_TransDetail WHERE twID = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "DELETE FROM TW_Transaction WHERE twID = ?");
      localPreparedStatement.setInt(1, paramInt);
      int k = DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM TW_Transaction WHERE twID = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      if (k != 1) {
        throw new TWException(101, " The transaction could not be deleted. ");
      }
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      if ((localThrowable instanceof SQLException)) {
        throw new TWException(localThrowable, 1, " The transaction could not be deleted. ");
      }
      if ((localThrowable instanceof TWException))
      {
        TWException localTWException = (TWException)localThrowable;
        if (localTWException.getErrorMessage() == null) {
          localTWException.setErrorMessage(" The transaction could not be deleted. ");
        }
        throw localTWException;
      }
      throw new TWException(localThrowable, 0, " The transaction could not be deleted. ");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.returnConnection(paramString, localConnection);
    }
  }
  
  public static void deleteTransactions(int[] paramArrayOfInt, String paramString)
    throws TWException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    try
    {
      if (paramArrayOfInt == null) {
        return;
      }
      Arrays.sort(paramArrayOfInt);
      int k = paramArrayOfInt.length;
      localConnection = DBUtil.getConnection(paramString, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "DELETE FROM TW_TransDetail WHERE twID = ?");
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "DELETE FROM TW_Transaction WHERE twID = ?");
      int m = 0;
      for (int n = 0; n < k; n++)
      {
        localPreparedStatement1.clearParameters();
        localPreparedStatement1.setInt(1, paramArrayOfInt[n]);
        localPreparedStatement1.executeUpdate();
        localPreparedStatement2.clearParameters();
        localPreparedStatement2.setInt(1, paramArrayOfInt[n]);
        m = localPreparedStatement2.executeUpdate();
        if (m != 1) {
          throw new TWException(101, " The transactions could not be deleted. ");
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      if ((localThrowable instanceof SQLException)) {
        throw new TWException(localThrowable, 1, " The transactions could not be deleted. ");
      }
      if ((localThrowable instanceof TWException))
      {
        TWException localTWException = (TWException)localThrowable;
        if (localTWException.getErrorMessage() == null) {
          localTWException.setErrorMessage(" The transactions could not be deleted. ");
        }
        throw localTWException;
      }
      throw new TWException(localThrowable, 0, " The transactions could not be deleted. ");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement1);
      localPreparedStatement1 = null;
      DBUtil.closeStatement(localPreparedStatement2);
      localPreparedStatement2 = null;
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.returnConnection(paramString, localConnection);
    }
  }
  
  public static void cleanup(int paramInt, String paramString)
    throws TWException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      long l1 = paramInt * 24L * 60L * 60L * 1000L;
      long l2 = System.currentTimeMillis();
      long l3 = l2 - l1;
      Timestamp localTimestamp = new Timestamp(l3);
      localConnection = DBUtil.getConnection(paramString, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "DELETE FROM TW_Transaction WHERE dtSubmit <= ? AND transactionType NOT IN (1001,1007,1005,1003)");
      localPreparedStatement.setTimestamp(1, localTimestamp);
      DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM TW_Transaction WHERE dtSubmit <= ? AND transactionType NOT IN (1001,1007,1005,1003)");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof SQLException)) {
        throw new TWException(localThrowable, 1, " The transactions could not be purged. ");
      }
      if ((localThrowable instanceof TWException))
      {
        TWException localTWException = (TWException)localThrowable;
        if (localTWException.getErrorMessage() == null) {
          localTWException.setErrorMessage(" The transactions could not be purged. ");
        }
        throw localTWException;
      }
      throw new TWException(localThrowable, 0, " The transactions could not be purged. ");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      DBUtil.returnConnection(paramString, localConnection);
    }
  }
  
  public static TWTransaction modifyTransaction(int paramInt, IApprovable paramIApprovable, String paramString)
    throws TWException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    TWTransaction localTWTransaction = null;
    try
    {
      if (paramIApprovable == null) {
        throw new TWException(108, " The transaction could not be modified. ");
      }
      int k = paramIApprovable.getApprovalSubType();
      localObject1 = paramIApprovable.getApprovalAmount();
      String str1 = ((Currency)localObject1).getString();
      String str2 = a(paramIApprovable, k);
      String str3 = jdField_if(paramIApprovable, k);
      int m = 0;
      String str4 = str3;
      int n = str4.length();
      if (str3.length() >= 2000)
      {
        n = Strings.getLastNonSpaceCharIndex(str3, 0, 2000);
        str4 = str3.substring(0, n);
        m = 1;
      }
      localTWTransaction = a(paramInt, paramString, false);
      if (localTWTransaction == null) {
        throw new TWException(101, " The transaction could not be modified. ");
      }
      if (localTWTransaction.getTransactionType() != k) {
        throw new TWException(110, " The transaction could not be modified. ");
      }
      if (localTWTransaction.getTrackingID().compareTo(str2) != 0) {
        throw new TWException(109, " The transaction could not be modified. ");
      }
      localConnection = DBUtil.getConnection(paramString, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "DELETE FROM TW_TransDetail WHERE twID = ?");
      localPreparedStatement.setInt(1, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM TW_TransDetail WHERE twID = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "UPDATE TW_Transaction SET amount = ?, transDetailXML = ? WHERE twID = ?");
      localPreparedStatement.setString(1, str1);
      localPreparedStatement.setString(2, str4);
      localPreparedStatement.setInt(3, paramInt);
      int i1 = DBUtil.executeUpdate(localPreparedStatement, "UPDATE TW_Transaction SET amount = ?, transDetailXML = ? WHERE twID = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      if (i1 == 0) {
        throw new TWException(101, " The transaction could not be modified. ");
      }
      if (m != 0) {
        a(str3, paramInt, localConnection, n);
      }
      DBUtil.commit(localConnection);
      localTWTransaction.setAmount((Currency)localObject1);
      localTWTransaction.setTransaction(paramIApprovable);
      str4 = null;
      str3 = null;
    }
    catch (Throwable localThrowable)
    {
      Object localObject1;
      DBUtil.rollback(localConnection);
      if ((localThrowable instanceof SQLException)) {
        throw new TWException(localThrowable, 1, " The transaction could not be modified. ");
      }
      if ((localThrowable instanceof TWException))
      {
        localObject1 = (TWException)localThrowable;
        if (((TWException)localObject1).getErrorMessage() == null) {
          ((TWException)localObject1).setErrorMessage(" The transaction could not be modified. ");
        }
        throw ((Throwable)localObject1);
      }
      throw new TWException(localThrowable, 0, " The transaction could not be modified. ");
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      localResultSet = null;
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.returnConnection(paramString, localConnection);
    }
    return localTWTransaction;
  }
  
  public static TWTransaction getTransaction(int paramInt, String paramString)
    throws TWException
  {
    return a(paramInt, paramString, false);
  }
  
  private static TWTransaction a(int paramInt, String paramString, boolean paramBoolean)
    throws TWException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    TWTransaction localTWTransaction = null;
    try
    {
      localConnection = DBUtil.getConnection(paramString, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "UPDATE TW_TransDetail set twID=twID where twID = ?");
      localPreparedStatement1.setInt(1, paramInt);
      int k = DBUtil.executeUpdate(localPreparedStatement1, "UPDATE TW_TransDetail set twID=twID where twID = ?");
      DBUtil.closeStatement(localPreparedStatement1);
      localPreparedStatement1 = null;
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "SELECT twID, trackingID, userID, transactionType, dtSubmit,amount, userName, password, transDetailXML FROM TW_Transaction WHERE twID = ?");
      localPreparedStatement1.setInt(1, paramInt);
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "SELECT twID, trackingID, userID, transactionType, dtSubmit,amount, userName, password, transDetailXML FROM TW_Transaction WHERE twID = ?");
      if (localResultSet1.next())
      {
        localObject1 = localResultSet1.getString(2);
        int m = localResultSet1.getInt(3);
        int n = localResultSet1.getInt(4);
        Timestamp localTimestamp = localResultSet1.getTimestamp(5);
        String str1 = localResultSet1.getString(6);
        String str2 = localResultSet1.getString(7);
        String str3 = localResultSet1.getString(8);
        String str4 = localResultSet1.getString(9);
        StringBuffer localStringBuffer = new StringBuffer(str4);
        if (k > 0)
        {
          localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "SELECT transDetailXML FROM TW_TransDetail WHERE twID = ? ORDER BY ordinal");
          localPreparedStatement2.setInt(1, paramInt);
          localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "SELECT transDetailXML FROM TW_TransDetail WHERE twID = ? ORDER BY ordinal");
          while (localResultSet2.next())
          {
            localObject2 = localResultSet2.getString(1);
            localStringBuffer.append((String)localObject2);
          }
          DBUtil.closeAll(localPreparedStatement2, localResultSet2);
          localResultSet2 = null;
          localPreparedStatement2 = null;
        }
        Object localObject2 = TransactionWarehouse.getApprovable(n, localStringBuffer.toString(), null);
        localTWTransaction = a(paramInt, (String)localObject1, m, n, localTimestamp, str1, str2, str3, (IApprovable)localObject2);
        str4 = null;
        localStringBuffer = null;
      }
      else
      {
        if (paramBoolean)
        {
          localObject1 = null;
          return localObject1;
        }
        throw new TWException(101, " The transaction could not be obtained. ");
      }
      DBUtil.closeAll(localPreparedStatement1, localResultSet1);
      localResultSet1 = null;
      localPreparedStatement1 = null;
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      Object localObject1;
      DBUtil.rollback(localConnection);
      if ((localThrowable instanceof SQLException)) {
        throw new TWException(localThrowable, 1, " The transaction could not be obtained. ");
      }
      if ((localThrowable instanceof TWException))
      {
        localObject1 = (TWException)localThrowable;
        if (((TWException)localObject1).getErrorMessage() == null) {
          ((TWException)localObject1).setErrorMessage(" The transaction could not be obtained. ");
        }
        throw ((Throwable)localObject1);
      }
      throw new TWException(localThrowable, 0, " The transaction could not be obtained. ");
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement2, localResultSet2);
      localResultSet2 = null;
      localPreparedStatement2 = null;
      DBUtil.closeAll(localPreparedStatement1, localResultSet1);
      localResultSet1 = null;
      localPreparedStatement1 = null;
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.returnConnection(paramString, localConnection);
    }
    return localTWTransaction;
  }
  
  public static TWTransaction getTransaction(int paramInt, String paramString1, String paramString2)
    throws TWException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    TWTransaction localTWTransaction = null;
    try
    {
      localConnection = DBUtil.getConnection(paramString2, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT twID FROM TW_Transaction WHERE transactionType = ? AND trackingID = ? ORDER BY dtSubmit DESC ");
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT twID FROM TW_Transaction WHERE transactionType = ? AND trackingID = ? ORDER BY dtSubmit DESC ");
      if (localResultSet.next())
      {
        int k = localResultSet.getInt(1);
        localTWTransaction = a(k, paramString2, true);
      }
      DBUtil.closeResultSet(localResultSet);
      localResultSet = null;
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof SQLException)) {
        throw new TWException(localThrowable, 1, " The transaction could not be obtained. ");
      }
      if ((localThrowable instanceof TWException))
      {
        TWException localTWException = (TWException)localThrowable;
        if (localTWException.getErrorMessage() == null) {
          localTWException.setErrorMessage(" The transaction could not be obtained. ");
        }
        throw localTWException;
      }
      throw new TWException(localThrowable, 0, " The transaction could not be obtained. ");
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      localResultSet = null;
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      DBUtil.returnConnection(paramString2, localConnection);
    }
    return localTWTransaction;
  }
  
  public static TWTransaction getTransaction(String paramString1, String paramString2)
    throws TWException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    TWTransaction localTWTransaction = null;
    try
    {
      localConnection = DBUtil.getConnection(paramString2, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT twID FROM TW_Transaction WHERE trackingID = ? ORDER BY dtSubmit DESC ");
      localPreparedStatement.setString(1, paramString1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT twID FROM TW_Transaction WHERE trackingID = ? ORDER BY dtSubmit DESC ");
      if (localResultSet.next())
      {
        int k = localResultSet.getInt(1);
        localTWTransaction = a(k, paramString2, true);
      }
      DBUtil.closeResultSet(localResultSet);
      localResultSet = null;
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof SQLException)) {
        throw new TWException(localThrowable, 1, " The transaction could not be obtained. ");
      }
      if ((localThrowable instanceof TWException))
      {
        TWException localTWException = (TWException)localThrowable;
        if (localTWException.getErrorMessage() == null) {
          localTWException.setErrorMessage(" The transaction could not be obtained. ");
        }
        throw localTWException;
      }
      throw new TWException(localThrowable, 0, " The transaction could not be obtained. ");
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      localResultSet = null;
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      DBUtil.returnConnection(paramString2, localConnection);
    }
    return localTWTransaction;
  }
  
  public static TWTransactions getTransactions(int paramInt1, int paramInt2, String paramString)
    throws TWException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Object localObject1 = null;
    try
    {
      localConnection = DBUtil.getConnection(paramString, true, 2);
      if (paramInt1 > 0)
      {
        if (paramInt2 >= 0)
        {
          localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT twID FROM TW_Transaction WHERE userID = ? AND transactionType = ? order by twID");
          localPreparedStatement.setInt(1, paramInt2);
          localPreparedStatement.setInt(2, paramInt1);
          localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT twID FROM TW_Transaction WHERE userID = ? AND transactionType = ? order by twID");
        }
        else
        {
          localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT twID FROM TW_Transaction WHERE transactionType = ? order by twID");
          localPreparedStatement.setInt(1, paramInt1);
          localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT twID FROM TW_Transaction WHERE transactionType = ? order by twID");
        }
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT twID FROM TW_Transaction WHERE userID = ? order by twID");
        localPreparedStatement.setInt(1, paramInt2);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT twID FROM TW_Transaction WHERE userID = ? order by twID");
      }
      TWTransactions localTWTransactions = new TWTransactions(Locale.getDefault());
      while (localResultSet.next())
      {
        int k = localResultSet.getInt(1);
        TWTransaction localTWTransaction = a(k, paramString, true);
        if (localTWTransaction != null) {
          localTWTransactions.add(localTWTransaction);
        }
      }
      if (localTWTransactions.size() > 0) {
        localObject1 = localTWTransactions;
      }
      DBUtil.closeResultSet(localResultSet);
      localResultSet = null;
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof SQLException)) {
        throw new TWException(localThrowable, 1, " The transactions could not be obtained. ");
      }
      if ((localThrowable instanceof TWException))
      {
        TWException localTWException = (TWException)localThrowable;
        if (localTWException.getErrorMessage() == null) {
          localTWException.setErrorMessage(" The transactions could not be obtained. ");
        }
        throw localTWException;
      }
      throw new TWException(localThrowable, 0, " The transactions could not be obtained. ");
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      localResultSet = null;
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      DBUtil.returnConnection(paramString, localConnection);
    }
    return localObject1;
  }
  
  public static TWTransactions getTransactions(int[] paramArrayOfInt, String paramString)
    throws TWException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Object localObject1 = null;
    try
    {
      if (paramArrayOfInt == null)
      {
        TWTransactions localTWTransactions = null;
        return localTWTransactions;
      }
      Arrays.sort(paramArrayOfInt);
      int k = paramArrayOfInt.length;
      localObject2 = new TWTransactions(Locale.getDefault());
      for (int m = 0; m < k; m++)
      {
        TWTransaction localTWTransaction = a(paramArrayOfInt[m], paramString, false);
        ((TWTransactions)localObject2).add(localTWTransaction);
      }
      if (((TWTransactions)localObject2).size() > 0) {
        localObject1 = localObject2;
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject2;
      DBUtil.rollback(localConnection);
      if ((localThrowable instanceof SQLException)) {
        throw new TWException(localThrowable, 1, " The transactions could not be obtained. ");
      }
      if ((localThrowable instanceof TWException))
      {
        localObject2 = (TWException)localThrowable;
        if (((TWException)localObject2).getErrorMessage() == null) {
          ((TWException)localObject2).setErrorMessage(" The transactions could not be obtained. ");
        }
        throw ((Throwable)localObject2);
      }
      throw new TWException(localThrowable, 0, " The transactions could not be obtained. ");
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      localResultSet = null;
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.returnConnection(paramString, localConnection);
    }
    return localObject1;
  }
  
  private static TWTransaction a(int paramInt1, String paramString1, int paramInt2, int paramInt3, Timestamp paramTimestamp, String paramString2, String paramString3, String paramString4, IApprovable paramIApprovable)
  {
    TWTransaction localTWTransaction = new TWTransaction(null);
    DateTime localDateTime = new DateTime();
    long l = paramTimestamp.getTime() + paramTimestamp.getNanos() / 1000000;
    localDateTime.setTime(new Date(l));
    localTWTransaction.setID(paramInt1);
    localTWTransaction.setTrackingID(paramString1);
    localTWTransaction.setUserID(paramInt2);
    localTWTransaction.setTransactionType(paramInt3);
    localTWTransaction.setSubmissionDateTime(localDateTime);
    String str = null;
    Currency localCurrency = paramIApprovable.getApprovalAmount();
    if (localCurrency != null) {
      str = localCurrency.getCurrencyCode();
    }
    localTWTransaction.setAmount(new Currency(paramString2, str, Locale.getDefault()));
    localTWTransaction.setUserName(paramString3);
    localTWTransaction.setPassword(paramString4);
    localTWTransaction.setTransaction(paramIApprovable);
    return localTWTransaction;
  }
  
  private static void a(String paramString, int paramInt1, Connection paramConnection, int paramInt2)
    throws TWException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "INSERT INTO TW_TransDetail ( twID, ordinal, transDetailXML ) VALUES (?,?,?)");
      int k = paramInt2;
      int m = k;
      int n = paramString.length();
      for (int i1 = 1; m != n; i1++)
      {
        m += 2000;
        if (m > n) {
          m = n;
        }
        if (m != n) {
          m = Strings.getLastNonSpaceCharIndex(paramString, k, m);
        }
        String str = paramString.substring(k, m);
        localPreparedStatement.setInt(1, paramInt1);
        localPreparedStatement.setInt(2, i1);
        localPreparedStatement.setString(3, str);
        DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO TW_TransDetail ( twID, ordinal, transDetailXML ) VALUES (?,?,?)");
        localPreparedStatement.clearParameters();
        k = m;
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof SQLException)) {
        throw new TWException(localThrowable, 1);
      }
      if ((localThrowable instanceof TWException)) {
        throw ((TWException)localThrowable);
      }
      throw new TWException(localThrowable, 0);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
  }
  
  private static String jdField_if(XMLable paramXMLable, int paramInt)
    throws TWException
  {
    String str = null;
    try
    {
      if ((paramInt > 0) && (paramInt < 999)) {
        str = ((FundsTransaction)paramXMLable).toXML();
      } else if ((paramInt > 1000) && (paramInt < 1999)) {
        str = ((FundsTransactionTemplate)paramXMLable).toXML();
      }
    }
    catch (Throwable localThrowable)
    {
      throw new TWException(localThrowable, 0);
    }
    if (str == null) {
      throw new TWException(105);
    }
    return str;
  }
  
  private static String a(XMLable paramXMLable, int paramInt)
    throws TWException
  {
    String str = null;
    try
    {
      if ((paramInt > 0) && (paramInt < 999)) {
        str = ((FundsTransaction)paramXMLable).getTrackingID();
      } else if ((paramInt > 1000) && (paramInt < 1999)) {
        str = ((FundsTransactionTemplate)paramXMLable).getFundsTransaction().getTrackingID();
      }
    }
    catch (Throwable localThrowable)
    {
      throw new TWException(localThrowable, 0);
    }
    if (str == null) {
      throw new TWException(104);
    }
    return str;
  }
  
  public static TWTransaction modifyTransaction(int paramInt1, int paramInt2, IApprovable paramIApprovable, String paramString)
    throws TWException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    TWTransaction localTWTransaction = null;
    try
    {
      if (paramIApprovable == null) {
        throw new TWException(108, " The transaction could not be modified. ");
      }
      int k = paramIApprovable.getApprovalSubType();
      localObject1 = paramIApprovable.getApprovalAmount();
      String str1 = ((Currency)localObject1).getString();
      String str2 = a(paramIApprovable, k);
      String str3 = jdField_if(paramIApprovable, k);
      int m = 0;
      String str4 = str3;
      int n = str4.length();
      if (str3.length() >= 2000)
      {
        n = Strings.getLastNonSpaceCharIndex(str3, 0, 2000);
        str4 = str3.substring(0, n);
        m = 1;
      }
      localTWTransaction = a(paramInt2, paramString, false);
      if (localTWTransaction == null) {
        throw new TWException(101, " The transaction could not be modified. ");
      }
      if (localTWTransaction.getTransactionType() != k) {
        throw new TWException(110, " The transaction could not be modified. ");
      }
      if (localTWTransaction.getTrackingID().compareTo(str2) != 0) {
        throw new TWException(109, " The transaction could not be modified. ");
      }
      localConnection = DBUtil.getConnection(paramString, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "DELETE FROM TW_TransDetail WHERE twID = ?");
      localPreparedStatement.setInt(1, paramInt2);
      DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM TW_TransDetail WHERE twID = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "UPDATE TW_Transaction SET userid = ?, amount = ?, transDetailXML = ? WHERE twID = ?");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setString(1, str1);
      localPreparedStatement.setString(2, str4);
      localPreparedStatement.setInt(3, paramInt2);
      int i1 = DBUtil.executeUpdate(localPreparedStatement, "UPDATE TW_Transaction SET userid = ?, amount = ?, transDetailXML = ? WHERE twID = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      if (i1 == 0) {
        throw new TWException(101, " The transaction could not be modified. ");
      }
      if (m != 0) {
        a(str3, paramInt2, localConnection, n);
      }
      DBUtil.commit(localConnection);
      localTWTransaction.setAmount((Currency)localObject1);
      localTWTransaction.setTransaction(paramIApprovable);
      str4 = null;
      str3 = null;
    }
    catch (Throwable localThrowable)
    {
      Object localObject1;
      DBUtil.rollback(localConnection);
      if ((localThrowable instanceof SQLException)) {
        throw new TWException(localThrowable, 1, " The transaction could not be modified. ");
      }
      if ((localThrowable instanceof TWException))
      {
        localObject1 = (TWException)localThrowable;
        if (((TWException)localObject1).getErrorMessage() == null) {
          ((TWException)localObject1).setErrorMessage(" The transaction could not be modified. ");
        }
        throw ((Throwable)localObject1);
      }
      throw new TWException(localThrowable, 0, " The transaction could not be modified. ");
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      localResultSet = null;
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.returnConnection(paramString, localConnection);
    }
    return localTWTransaction;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tw.db.Transaction
 * JD-Core Version:    0.7.0.1
 */