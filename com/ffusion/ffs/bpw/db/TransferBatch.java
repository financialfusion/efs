package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.TransferBatchInfo;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TransferBatch
  implements FFSConst, ACHConsts, DBConsts, BPWResource
{
  public static TransferBatchInfo addTransferBatch(FFSConnectionHolder paramFFSConnectionHolder, TransferBatchInfo paramTransferBatchInfo)
    throws FFSException
  {
    String str1 = "Transfer.addTransferBatch";
    String str2 = null;
    String str3 = null;
    Object[] arrayOfObject = null;
    FFSDebug.log(str1 + ": Starts: ", 6);
    Object localObject;
    if (paramFFSConnectionHolder == null)
    {
      paramTransferBatchInfo.setStatusCode(16000);
      localObject = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "TRANSFER_MESSAGE");
      paramTransferBatchInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      return paramTransferBatchInfo;
    }
    try
    {
      localObject = new TransferInfo();
      ((TransferInfo)localObject).setFIId(paramTransferBatchInfo.getFIID());
      ((TransferInfo)localObject).setCustomerId(paramTransferBatchInfo.getCustomerId());
      ((TransferInfo)localObject).setTransferType(paramTransferBatchInfo.getBatchType());
      Transfer.checkTransferCustFI(paramFFSConnectionHolder, (TransferInfo)localObject);
      if (((TransferInfo)localObject).getStatusCode() != 0)
      {
        str4 = "failed " + ((TransferInfo)localObject).getStatusMsg();
        FFSDebug.log(str1, str4, 0);
        paramTransferBatchInfo.setStatusCode(((TransferInfo)localObject).getStatusCode());
        paramTransferBatchInfo.setStatusMsg(((TransferInfo)localObject).getStatusMsg());
        return paramTransferBatchInfo;
      }
      localObject = null;
      str2 = DBUtil.getNewTransId("SrvrTID", 18);
      str3 = "INSERT INTO BPW_TransferBatch ( BatchID, FIID, CustomerID,  BatchName, BatchType, TotalAmount, AmountCurrency, TransferCount,  SubmittedBy, SubmitDate, BatchStatus, Memo, LogID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      paramTransferBatchInfo.setBatchID(str2);
      paramTransferBatchInfo.setBatchStatus("CREATED");
      arrayOfObject = jdMethod_do(paramTransferBatchInfo);
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      paramTransferBatchInfo.setStatusCode(0);
      paramTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "TRANSFER_MESSAGE"));
    }
    catch (Throwable localThrowable)
    {
      String str4 = str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    return paramTransferBatchInfo;
  }
  
  private static Object[] jdMethod_do(TransferBatchInfo paramTransferBatchInfo)
    throws FFSException
  {
    Object[] arrayOfObject = { paramTransferBatchInfo.getBatchID(), paramTransferBatchInfo.getFIID(), paramTransferBatchInfo.getCustomerId(), paramTransferBatchInfo.getBatchName(), paramTransferBatchInfo.getBatchType(), paramTransferBatchInfo.getTotalAmount(), paramTransferBatchInfo.getAmountCurrency(), new Integer(paramTransferBatchInfo.getTransferCount()), paramTransferBatchInfo.getSubmittedBy(), new Timestamp(Calendar.getInstance().getTimeInMillis()), paramTransferBatchInfo.getBatchStatus(), paramTransferBatchInfo.getMemo(), paramTransferBatchInfo.getLogId() };
    return arrayOfObject;
  }
  
  private static Object[] jdMethod_if(TransferBatchInfo paramTransferBatchInfo)
    throws FFSException
  {
    Object[] arrayOfObject = { paramTransferBatchInfo.getBatchName(), paramTransferBatchInfo.getBatchType(), paramTransferBatchInfo.getTotalAmount(), new Integer(paramTransferBatchInfo.getTransferCount()), paramTransferBatchInfo.getMemo(), paramTransferBatchInfo.getAmountCurrency(), paramTransferBatchInfo.getBatchStatus(), paramTransferBatchInfo.getBatchID(), paramTransferBatchInfo.getFIID(), paramTransferBatchInfo.getCustomerId() };
    return arrayOfObject;
  }
  
  public static TransferBatchInfo populateTransferBatchInfo(FFSResultSet paramFFSResultSet)
    throws Exception
  {
    TransferBatchInfo localTransferBatchInfo = new TransferBatchInfo();
    localTransferBatchInfo.setBatchID(paramFFSResultSet.getColumnString("BatchID"));
    localTransferBatchInfo.setFIID(paramFFSResultSet.getColumnString("FIID"));
    localTransferBatchInfo.setCustomerId(paramFFSResultSet.getColumnString("CustomerID"));
    localTransferBatchInfo.setBatchName(paramFFSResultSet.getColumnString("BatchName"));
    localTransferBatchInfo.setBatchType(paramFFSResultSet.getColumnString("BatchType"));
    localTransferBatchInfo.setTotalAmount(paramFFSResultSet.getColumnString("TotalAmount"));
    localTransferBatchInfo.setAmountCurrency(paramFFSResultSet.getColumnString("AmountCurrency"));
    localTransferBatchInfo.setTransferCount(paramFFSResultSet.getColumnString("TransferCount"));
    localTransferBatchInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    localTransferBatchInfo.setSubmitDate(new SimpleDateFormat("yyyyMMdd").format(paramFFSResultSet.getColumnTimestamp("SubmitDate")));
    localTransferBatchInfo.setBatchStatus(paramFFSResultSet.getColumnString("BatchStatus"));
    localTransferBatchInfo.setMemo(paramFFSResultSet.getColumnString("Memo"));
    localTransferBatchInfo.setLogId(paramFFSResultSet.getColumnString("LogID"));
    return localTransferBatchInfo;
  }
  
  public static TransferBatchInfo updateTransferBatch(FFSConnectionHolder paramFFSConnectionHolder, TransferBatchInfo paramTransferBatchInfo)
    throws FFSException
  {
    String str1 = "Transfer.updateTransferBatch";
    Object localObject1 = null;
    String str2 = null;
    Object[] arrayOfObject = null;
    FFSDebug.log(str1 + ": Starts: ", 6);
    Object localObject2;
    if (paramFFSConnectionHolder == null)
    {
      paramTransferBatchInfo.setStatusCode(16000);
      localObject2 = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "TRANSFER_MESSAGE");
      paramTransferBatchInfo.setStatusMsg((String)localObject2);
      FFSDebug.log(str1, (String)localObject2, 0);
      return paramTransferBatchInfo;
    }
    try
    {
      localObject2 = new TransferInfo();
      ((TransferInfo)localObject2).setFIId(paramTransferBatchInfo.getFIID());
      ((TransferInfo)localObject2).setCustomerId(paramTransferBatchInfo.getCustomerId());
      ((TransferInfo)localObject2).setTransferType(paramTransferBatchInfo.getBatchType());
      Transfer.checkTransferCustFI(paramFFSConnectionHolder, (TransferInfo)localObject2);
      if (((TransferInfo)localObject2).getStatusCode() != 0)
      {
        String str3 = "failed " + ((TransferInfo)localObject2).getStatusMsg();
        FFSDebug.log(str1, str3, 0);
        paramTransferBatchInfo.setStatusCode(((TransferInfo)localObject2).getStatusCode());
        paramTransferBatchInfo.setStatusMsg(((TransferInfo)localObject2).getStatusMsg());
        return paramTransferBatchInfo;
      }
      localObject2 = null;
      str2 = "UPDATE BPW_TransferBatch set BatchName=?,  BatchType=?, TotalAmount=?,  TransferCount=?, Memo=?, AmountCurrency=?, BatchStatus = ?  WHERE BatchID=?  AND FIID=?  AND CustomerID=? ";
      arrayOfObject = jdMethod_if(paramTransferBatchInfo);
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (i == 0)
      {
        localObject3 = new StringBuffer();
        ((StringBuffer)localObject3).append(" record not found");
        ((StringBuffer)localObject3).append(" for batchId:").append(paramTransferBatchInfo.getBatchID());
        ((StringBuffer)localObject3).append(", FIID:").append(paramTransferBatchInfo.getFIID());
        ((StringBuffer)localObject3).append(" and CustomerID:");
        ((StringBuffer)localObject3).append(paramTransferBatchInfo.getCustomerId());
        paramTransferBatchInfo.setStatusCode(16020);
        paramTransferBatchInfo.setStatusMsg(((StringBuffer)localObject3).toString());
        paramTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(((TransferInfo)localObject2).getStatusCode(), new String[] { " BatchId: " + paramTransferBatchInfo.getBatchID() + " FIID: " + paramTransferBatchInfo.getFIID() + " CustomerID:" + paramTransferBatchInfo.getCustomerId() }, "TRANSFER_MESSAGE"));
        return paramTransferBatchInfo;
      }
      paramTransferBatchInfo.setStatusCode(0);
      paramTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "TRANSFER_MESSAGE"));
    }
    catch (Throwable localThrowable)
    {
      String str4 = str1 + " failed: ";
      Object localObject3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, (String)localObject3, 0);
      throw new FFSException(localThrowable, str4);
    }
    return paramTransferBatchInfo;
  }
  
  public static TransferBatchInfo getTransferBatchById(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.getTransferBatch";
    FFSDebug.log(str1, ": start", 6);
    StringBuffer localStringBuffer = null;
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    int i = 0;
    TransferBatchInfo localTransferBatchInfo = new TransferBatchInfo();
    FFSResultSet localFFSResultSet = null;
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      localObject4 = " BatchId all are null or not specified.";
      FFSDebug.log("Transfer.getTransferBatch: Failed, ", (String)localObject4, 0);
      localTransferBatchInfo.setStatusCode(16000);
      localTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BatchId" }, "TRANSFER_MESSAGE"));
      return localTransferBatchInfo;
    }
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT BatchID, FIID, CustomerID,  BatchName, BatchType, TotalAmount, AmountCurrency, TransferCount,  SubmittedBy, SubmitDate, BatchStatus, Memo, LogID  FROM BPW_TransferBatch ");
    localStringBuffer.append(" WHERE BatchID = ? AND BatchType = 'TEMPLATE'");
    Object localObject4 = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject4);
      Object localObject5;
      if (localFFSResultSet.getNextRow())
      {
        localTransferBatchInfo = populateTransferBatchInfo(localFFSResultSet);
        if (paramBoolean)
        {
          localObject5 = Transfer.getTransfersByBatchId(paramFFSConnectionHolder, paramString);
          localTransferBatchInfo.setTransfers((TransferInfo[])((ArrayList)localObject5).toArray(new TransferInfo[((ArrayList)localObject5).size()]));
        }
      }
      else
      {
        localTransferBatchInfo.setStatusCode(16020);
        localTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "TransferBatchInfo" }, "TRANSFER_MESSAGE"));
        localObject5 = localTransferBatchInfo;
        return localObject5;
      }
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localThrowable, str2);
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
      catch (Exception localException)
      {
        String str4 = str1 + " failed: ";
        String str5 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str4, str5, 0);
      }
    }
    localTransferBatchInfo.setStatusCode(0);
    localTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "TRANSFER_MESSAGE"));
    FFSDebug.log(str1, ": done", 6);
    return localTransferBatchInfo;
  }
  
  public static int cleanup(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "TransferBatch.cleanup: ";
    FFSDebug.log(str1 + "starts. CustomerId: " + paramString + ". Age days: " + paramInt, 6);
    int i = 0;
    ArrayList localArrayList = null;
    String str2 = "DELETE * FROM BPW_TransferBatch WHERE BatchID = ? ";
    try
    {
      for (;;)
      {
        localArrayList = getTransferBatchIdsByCustomerIdAndStatus(paramFFSConnectionHolder, paramString, "CANCELEDON");
        if (localArrayList == null) {
          break;
        }
        int[] arrayOfInt = DBUtil.executeStatementBatch(paramFFSConnectionHolder, str2, localArrayList);
        i += arrayOfInt.length;
        if (arrayOfInt.length < Purge.getPageSize()) {
          break;
        }
        paramFFSConnectionHolder.conn.commit();
      }
      paramFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "***  " + str1 + ": failed:";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "end, No of rows deleted = " + i, 6);
    return i;
  }
  
  public static ArrayList getTransferBatchIdsByCustomerIdAndStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "TransferBatch.getCanceledTransferBatchIdsByCustomerId: ";
    FFSDebug.log(str1 + "CustomerId: " + paramString1, 6);
    ArrayList localArrayList = new ArrayList();
    String str2 = null;
    String[] arrayOfString = null;
    FFSResultSet localFFSResultSet = null;
    if (paramString1 == null)
    {
      str2 = "SELECT BatchID FROM BPW_TransferBatch WHERE BatchStatus = ?";
      arrayOfString = new String[1];
      arrayOfString[0] = paramString2;
    }
    else
    {
      str2 = "SELECT BatchID FROM BPW_TransferBatch WHERE CustomerID = ? AND BatchStatus = ?";
      arrayOfString = new String[2];
      arrayOfString[0] = paramString1;
      arrayOfString[1] = paramString2;
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfString);
      while ((localFFSResultSet.getNextRow()) && (localArrayList.size() < Purge.getPageSize()))
      {
        String str3 = localFFSResultSet.getColumnString("BatchID");
        localArrayList.add(str3);
      }
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed:";
      FFSDebug.log(localException1, str4, 0);
      throw new FFSException(localException1, str4);
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
      catch (Exception localException2)
      {
        FFSDebug.log("*** ", str1, " failed:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log(str1, " rows=" + localArrayList.size(), 6);
    if (localArrayList.isEmpty()) {
      return null;
    }
    return localArrayList;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.TransferBatch
 * JD-Core Version:    0.7.0.1
 */