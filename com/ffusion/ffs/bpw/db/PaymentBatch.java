package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class PaymentBatch
{
  public static PaymentBatchInfo addPaymentBatch(FFSConnectionHolder paramFFSConnectionHolder, PaymentBatchInfo paramPaymentBatchInfo)
    throws FFSException
  {
    String str1 = "PaymentBatch.addPaymentBatch: ";
    FFSDebug.log(str1, "start.", 6);
    String str2 = null;
    paramPaymentBatchInfo.setSubmitDate(new Timestamp(Calendar.getInstance().getTimeInMillis()).toString());
    paramPaymentBatchInfo.setBatchStatus("ACTIVE");
    try
    {
      String str3 = "INSERT INTO BPW_PaymentBatch ( BatchID, FIID, CustomerID,  BatchName, BatchType, TotalAmount, AmountCurrency, PaymentCount,  SubmittedBy, SubmitDate, BatchStatus, LogID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      Object[] arrayOfObject = { paramPaymentBatchInfo.getBatchID(), paramPaymentBatchInfo.getFIID(), paramPaymentBatchInfo.getCustomerId(), paramPaymentBatchInfo.getBatchName(), paramPaymentBatchInfo.getBatchType(), paramPaymentBatchInfo.getTotalAmount(), paramPaymentBatchInfo.getAmountCurrency(), new Integer(paramPaymentBatchInfo.getPaymentCount()), paramPaymentBatchInfo.getSubmittedBy(), new Timestamp(Calendar.getInstance().getTimeInMillis()), paramPaymentBatchInfo.getBatchStatus(), paramPaymentBatchInfo.getLogId() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      str2 = "*** " + str1 + " failed:";
      FFSDebug.log(localException, str2, 0);
      throw new FFSException(localException, str2);
    }
    str2 = BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE");
    paramPaymentBatchInfo.setStatusCode(0);
    paramPaymentBatchInfo.setStatusMsg(str2);
    FFSDebug.log(str1 + " end.", 6);
    return paramPaymentBatchInfo;
  }
  
  public static PaymentBatchInfo modifyPaymentBatch(FFSConnectionHolder paramFFSConnectionHolder, PaymentBatchInfo paramPaymentBatchInfo)
    throws FFSException
  {
    String str1 = "PaymentBatch.modifyPaymentBatch: ";
    FFSDebug.log(str1, "start.", 6);
    String str2 = null;
    try
    {
      String str3 = "UPDATE BPW_PaymentBatch SET BatchName=?, TotalAmount=?, PaymentCount=?, LogID=?, AmountCurrency=?, SubmittedBy=? WHERE BatchID=? AND FIID=? AND CustomerID=?";
      Object[] arrayOfObject = { paramPaymentBatchInfo.getBatchName(), paramPaymentBatchInfo.getTotalAmount(), new Integer(paramPaymentBatchInfo.getPaymentCount()), paramPaymentBatchInfo.getLogId(), paramPaymentBatchInfo.getAmountCurrency(), paramPaymentBatchInfo.getSubmittedBy(), paramPaymentBatchInfo.getBatchID(), paramPaymentBatchInfo.getFIID(), paramPaymentBatchInfo.getCustomerId() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      if (i == 0)
      {
        str2 = BPWLocaleUtil.getMessage(16020, new String[] { "PaymentBatchInfo" }, "PAYMENT_MESSAGE");
        paramPaymentBatchInfo.setStatusCode(16020);
        paramPaymentBatchInfo.setStatusMsg(str2);
        return paramPaymentBatchInfo;
      }
    }
    catch (Exception localException)
    {
      str2 = "*** " + str1 + " failed:";
      FFSDebug.log(localException, str2, 0);
      throw new FFSException(localException, str2);
    }
    str2 = BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE");
    paramPaymentBatchInfo.setStatusCode(0);
    paramPaymentBatchInfo.setStatusMsg(str2);
    FFSDebug.log(str1 + " end.", 6);
    return paramPaymentBatchInfo;
  }
  
  public static PaymentBatchInfo cancelPaymentBatch(FFSConnectionHolder paramFFSConnectionHolder, PaymentBatchInfo paramPaymentBatchInfo)
    throws FFSException
  {
    String str1 = "PaymentBatch.cancelPaymentBatch: ";
    FFSDebug.log(str1, "start.", 6);
    String str2 = null;
    paramPaymentBatchInfo.setBatchStatus("CANCELEDON");
    try
    {
      String str3 = "UPDATE BPW_PaymentBatch SET BatchStatus=? WHERE BatchID = ?";
      Object[] arrayOfObject = { paramPaymentBatchInfo.getBatchStatus(), paramPaymentBatchInfo.getBatchID() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      if (i == 0)
      {
        str2 = BPWLocaleUtil.getMessage(16020, new String[] { "PaymentBatchInfo" }, "PAYMENT_MESSAGE");
        paramPaymentBatchInfo.setStatusCode(16020);
        paramPaymentBatchInfo.setStatusMsg(str2);
        return paramPaymentBatchInfo;
      }
    }
    catch (Exception localException)
    {
      str2 = "*** " + str1 + " failed:";
      FFSDebug.log(localException, str2, 0);
      throw new FFSException(localException, str2);
    }
    str2 = BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE");
    paramPaymentBatchInfo.setStatusCode(0);
    paramPaymentBatchInfo.setStatusMsg(str2);
    FFSDebug.log(str1 + " end.", 6);
    return paramPaymentBatchInfo;
  }
  
  private static FFSResultSet a(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "PaymentBatch.getCancelledBatchId: ";
    FFSDebug.log(str1, "start.", 6);
    String str2 = "SELECT a.BatchID FROM BPW_PaymentBatch a WHERE a.BatchStatus IN (?) AND (SELECT COUNT(*) from BPW_PmtInstruction b WHERE b.PaymentType = ? AND b.BatchID = a.BatchID) = 0";
    Object[] arrayOfObject = { "CANCELEDON", "TEMPLATE" };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed:";
      FFSDebug.log(localException, str3, 0);
      throw new FFSException(localException, str3);
    }
    return localFFSResultSet;
  }
  
  private static FFSResultSet a(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "PaymentBatch.getCancelledBatchIdByCustomerId: ";
    FFSDebug.log(str1, "start.", 6);
    String str2 = "SELECT a.BatchID FROM BPW_PaymentBatch a WHERE a.BatchStatus IN (?) AND a.CustomerID = ? AND (SELECT COUNT(*) from BPW_PmtInstruction b WHERE b.PaymentType = ? AND b.BatchID = a.BatchID) = 0";
    Object[] arrayOfObject = { "CANCELEDON", paramString, "TEMPLATE" };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed:";
      FFSDebug.log(localException, str3, 0);
      throw new FFSException(localException, str3);
    }
    return localFFSResultSet;
  }
  
  public static PaymentBatchInfo getPaymentBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    String str1 = "PaymentBatch.getPaymentBatch: ";
    FFSDebug.log(str1 + "start, BatchId=" + paramString, 6);
    PaymentBatchInfo localPaymentBatchInfo = null;
    String str2 = "SELECT FIID, CustomerID, BatchName, BatchType,  TotalAmount, AmountCurrency, PaymentCount,  SubmittedBy, SubmitDate, BatchStatus, LogID  FROM BPW_PaymentBatch WHERE BatchID = ? ";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
    if (localFFSResultSet.getNextRow())
    {
      localPaymentBatchInfo = new PaymentBatchInfo();
      localPaymentBatchInfo.setFIID(localFFSResultSet.getColumnString(1));
      localPaymentBatchInfo.setCustomerId(localFFSResultSet.getColumnString(2));
      localPaymentBatchInfo.setBatchName(localFFSResultSet.getColumnString(3));
      localPaymentBatchInfo.setBatchType(localFFSResultSet.getColumnString(4));
      localPaymentBatchInfo.setTotalAmount(localFFSResultSet.getColumnString(5));
      localPaymentBatchInfo.setAmountCurrency(localFFSResultSet.getColumnString(6));
      localPaymentBatchInfo.setPaymentCount(localFFSResultSet.getColumnString(7));
      localPaymentBatchInfo.setSubmittedBy(localFFSResultSet.getColumnString(8));
      localPaymentBatchInfo.setSubmitDate(localFFSResultSet.getColumnString(9));
      localPaymentBatchInfo.setBatchStatus(localFFSResultSet.getColumnString(10));
      localPaymentBatchInfo.setLogId(localFFSResultSet.getColumnString(11));
      localPaymentBatchInfo.setBatchID(paramString);
    }
    return localPaymentBatchInfo;
  }
  
  public static int cleanup(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "PaymentBatch.cleanup ";
    FFSDebug.log(str1 + " start.", 6);
    int i = 0;
    int j = 1;
    FFSResultSet localFFSResultSet = null;
    Object localObject1 = null;
    int k = 1;
    int m = Purge.getPageSize();
    ArrayList localArrayList = new ArrayList();
    if ((paramString != null) && (paramString.trim().length() != 0)) {
      j = 0;
    }
    try
    {
      while (k <= 2)
      {
        if (j != 0) {
          localFFSResultSet = a(paramFFSConnectionHolder);
        } else {
          localFFSResultSet = a(paramFFSConnectionHolder, paramString);
        }
        try
        {
          for (;;)
          {
            if (localFFSResultSet.getNextRow()) {
              try
              {
                localArrayList.add(localFFSResultSet.getColumnString(1));
                if (localArrayList.size() >= m)
                {
                  i += deleteBatch(paramFFSConnectionHolder, (String[])localArrayList.toArray(new String[0]));
                  localArrayList.clear();
                }
              }
              catch (Exception localException1)
              {
                localObject1 = localException1;
              }
            }
          }
          try
          {
            if (localArrayList.size() > 0) {
              i += deleteBatch(paramFFSConnectionHolder, (String[])localArrayList.toArray(new String[0]));
            }
          }
          catch (Exception localException2)
          {
            localObject1 = localException2;
          }
        }
        catch (Exception localException3)
        {
          FFSDebug.log(str1 + " SQLException: " + localException3.getMessage());
          FFSDebug.log(str1 + " Attempting retry # " + ++k);
        }
      }
      FFSDebug.log("----> Done deleting payment batches", 3);
      if (localObject1 != null) {
        throw localObject1;
      }
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** " + str1 + " failed: ";
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    catch (Exception localException4)
    {
      String str2 = "***  " + str1 + ": failed:";
      String str3 = FFSDebug.stackTrace(localException4);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException4, str2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException5) {}
    }
    FFSDebug.log(str1 + "end, No of rows deleted = " + i, 6);
    return i;
  }
  
  public static int delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("PaymentBatch.delete start, batchId=" + paramString, 6);
    String str1 = "DELETE FROM BPW_PaymentBatch WHERE BatchID = ?";
    Object[] arrayOfObject = { paramString };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** PaymentBatch.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PaymentBatch.delete done, batchId=" + paramString, 6);
    return i;
  }
  
  public static int deleteBatch(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("PaymentBatch.deleteBatch start.", 6);
    String str1 = "DELETE FROM BPW_PaymentBatch WHERE BatchID = ?";
    try
    {
      DBUtil.executeStatementBatch(paramFFSConnectionHolder, str1, DBUtil.arrayStringToArrayList(paramArrayOfString));
    }
    catch (Exception localException)
    {
      String str2 = "*** PaymentBatch.deleteBatch failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PaymentBatch.deleteBatch done. Deleted " + paramArrayOfString.length + " payment batches", 6);
    return paramArrayOfString.length;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.PaymentBatch
 * JD-Core Version:    0.7.0.1
 */