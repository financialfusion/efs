package com.ffusion.ffs.bpw.db;

import com.ffusion.beans.Currency;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWInfoBase;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecTransferInfo;
import com.ffusion.ffs.bpw.interfaces.SortCriterion;
import com.ffusion.ffs.bpw.interfaces.TransferBatchInfo;
import com.ffusion.ffs.bpw.interfaces.TransferHistInfo;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.fx.FXException;
import com.ffusion.fx.FXUtil;
import com.ffusion.services.fx.FXService;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.enums.UserAssignedAmount;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class TransferTempHist
  implements FFSConst, DBConsts, BPWResource
{
  public static int createTempHist(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt, TransferInfo paramTransferInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferTempHist.createTempHist";
    FFSDebug.log(str1 + " : start", 6);
    FFSDebug.log(str1 + " , sessionId = " + paramString + ", cursorId = " + paramInt + "TransferInfo id = " + paramTransferInfo.getSrvrTId(), 6);
    int i = 0;
    try
    {
      String str2 = null;
      str3 = null;
      if (paramTransferInfo.getTransferDest().equals("INTERNAL")) {
        str3 = paramTransferInfo.getDateToPost();
      } else {
        str3 = paramTransferInfo.getDateDue();
      }
      if (str3 == null) {
        str3 = FFSUtil.getDateString("yyyyMMdd");
      }
      FFSDebug.log(str1 + " isRecurring: " + paramBoolean, 6);
      if (paramBoolean)
      {
        localObject = (RecTransferInfo)paramTransferInfo;
        str2 = ((RecTransferInfo)localObject).getFrequency();
      }
      else
      {
        str2 = "NONE";
      }
      FFSDebug.log(str1 + " frequency: " + str2, 6);
      FFSDebug.log(str1 + " tInfo: " + paramTransferInfo, 6);
      jdMethod_void(paramTransferInfo);
      localObject = new Object[] { paramString, new Integer(paramInt), paramTransferInfo.getSrvrTId(), paramTransferInfo.getTransferType(), new Integer(BPWUtil.getDateDBFormat(str3)), paramTransferInfo.buildFromAcctId(), paramTransferInfo.buildToAcctId(), paramTransferInfo.getTransferDest(), str2, paramTransferInfo.getPrcStatus(), new BigDecimal(paramTransferInfo.getAmount()), paramTransferInfo.getTemplateNickName(), DBUtil.getCurrentLogDate(), paramTransferInfo.getAmountCurrency(), new BigDecimal(paramTransferInfo.getToAmount()), paramTransferInfo.getToAmountCurrency(), new Integer(paramTransferInfo.getUserAssignedAmount().getValue()), FFSUtil.getYNFromBoolean(paramTransferInfo.getEstimatedAmount()) };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_TransfrTmpHist (SessionId, CursorId, TransactionId, TransactionType, DateToPost, FromAcctId, ToAcctId, Destination, Frequency, Status, Amount,  TemplateName, SubmitDate, AmountCurrency, ToAmount, ToAmountCurrency, UserAssignedAmount, EstimatedAmount) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", (Object[])localObject);
    }
    catch (Exception localException)
    {
      String str3 = str1 + " failed: ";
      Object localObject = new StringWriter();
      localException.printStackTrace(new PrintWriter((Writer)localObject));
      FFSDebug.log(str3 + ((StringWriter)localObject).toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log(str1 + " done", 6);
    return i;
  }
  
  public static int createTempHist(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt, TransferBatchInfo paramTransferBatchInfo)
    throws FFSException
  {
    String str1 = "TransferTempHist.createTempHist";
    FFSDebug.log(str1 + " : start", 6);
    FFSDebug.log(str1 + " , sessionId = " + paramString + ", cursorId = " + paramInt + "TransferBatchInfo id = " + paramTransferBatchInfo.getBatchID(), 6);
    int i = 0;
    try
    {
      String str2 = null;
      str3 = paramTransferBatchInfo.getSubmitDate();
      if (str3 == null) {
        str3 = FFSUtil.getDateString("yyyyMMdd");
      }
      FFSDebug.log(str1 + " frequency: " + str2, 6);
      FFSDebug.log(str1 + " tInfo: " + paramTransferBatchInfo, 6);
      Object[] arrayOfObject = { paramString, new Integer(paramInt), paramTransferBatchInfo.getBatchID(), "BATCHTEMPLATE", new Integer(BPWUtil.getDateDBFormat(str3)), "NONE", "NONE", "NONE", str2, paramTransferBatchInfo.getBatchStatus(), new BigDecimal(paramTransferBatchInfo.getTotalAmount()), paramTransferBatchInfo.getBatchName(), str3, paramTransferBatchInfo.getAmountCurrency(), new BigDecimal(0.0D), null, new Integer(0), "N" };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_TransfrTmpHist (SessionId, CursorId, TransactionId, TransactionType, DateToPost, FromAcctId, ToAcctId, Destination, Frequency, Status, Amount,  TemplateName, SubmitDate, AmountCurrency, ToAmount, ToAmountCurrency, UserAssignedAmount, EstimatedAmount) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = str1 + " failed:";
      FFSDebug.log(str3 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log(str1 + " done", 6);
    return i;
  }
  
  public static void getBounds(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "TransferTempHist.getBounds";
    FFSDebug.log(str1 + " : start", 6);
    getBound(paramPagingInfo, "SORT_VALUE_MAX_", null, paramFFSConnectionHolder);
    getBound(paramPagingInfo, "SORT_VALUE_MIN_", null, paramFFSConnectionHolder);
    FFSResultSet localFFSResultSet = null;
    Object localObject1 = null;
    try
    {
      ArrayList localArrayList1 = paramPagingInfo.getPagingContext().getSortCriteriaList();
      localObject2 = null;
      if ((localArrayList1 == null) || (localArrayList1.size() == 0))
      {
        localObject2 = new SortCriterion();
        ((SortCriterion)localObject2).setName("DateToPost");
        ((SortCriterion)localObject2).setAscending();
      }
      else
      {
        localObject2 = (SortCriterion)localArrayList1.get(0);
      }
      boolean bool1 = ((SortCriterion)localObject2).isAscending();
      ArrayList localArrayList2 = new ArrayList();
      boolean bool2 = true;
      ((SortCriterion)localObject2).setAscending();
      String str2 = jdMethod_for(paramPagingInfo, localArrayList2, bool2);
      FFSDebug.log(str1 + " params = " + localArrayList2, 6);
      Object[] arrayOfObject = localArrayList2.toArray();
      FFSDebug.log(str1 + " sql stmt = " + str2, 6);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        FFSDebug.log(str1 + " tInfo = " + localObject1, 6);
        localObject1 = jdMethod_try(paramFFSConnectionHolder, localFFSResultSet);
        getBound(paramPagingInfo, "LOWER_BOUND_", (TransferHistInfo)localObject1, paramFFSConnectionHolder);
      }
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
      localArrayList2 = new ArrayList();
      ((SortCriterion)localObject2).setDescending();
      str2 = jdMethod_for(paramPagingInfo, localArrayList2, bool2);
      arrayOfObject = localArrayList2.toArray();
      FFSDebug.log(str1 + " sql stmt = " + str2, 6);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localObject1 = jdMethod_try(paramFFSConnectionHolder, localFFSResultSet);
        getBound(paramPagingInfo, "UPPER_BOUND_", (TransferHistInfo)localObject1, paramFFSConnectionHolder);
      }
      if (bool1) {
        ((SortCriterion)localObject2).setAscending();
      } else {
        ((SortCriterion)localObject2).setDescending();
      }
      paramPagingInfo.setStatusCode(0);
      paramPagingInfo.setStatusMsg("Success");
    }
    catch (Exception localException1)
    {
      Object localObject2 = str1 + " failed : ";
      FFSDebug.log((String)localObject2 + FFSDebug.stackTrace(localException1), 0);
      throw new FFSException(localException1, (String)localObject2);
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
        FFSDebug.log(str1 + " failed to close ResultSet " + FFSDebug.stackTrace(localException2), 0);
      }
    }
  }
  
  public static void getBound(PagingInfo paramPagingInfo, String paramString, TransferHistInfo paramTransferHistInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "TransferTempHist.getBound";
    FFSDebug.log(str1 + " : start", 6);
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    ArrayList localArrayList1 = paramPagingInfo.getPagingResult();
    ArrayList localArrayList2 = paramPagingInfo.getPagingContext().getSortCriteriaList();
    SortCriterion localSortCriterion = null;
    if ((localArrayList2 == null) || (localArrayList2.size() == 0))
    {
      localSortCriterion = new SortCriterion();
      localSortCriterion.setName("DateToPost");
      localSortCriterion.setAscending();
    }
    else
    {
      localSortCriterion = (SortCriterion)localArrayList2.get(0);
    }
    String str2 = localSortCriterion.getName();
    boolean bool = localSortCriterion.isAscending();
    if ((localArrayList1 != null) && (localArrayList1.size() > 0)) {
      if (paramString.equals("SORT_VALUE_MIN_"))
      {
        if (bool) {
          paramTransferHistInfo = (TransferHistInfo)localArrayList1.get(0);
        } else {
          paramTransferHistInfo = (TransferHistInfo)localArrayList1.get(localArrayList1.size() - 1);
        }
      }
      else if (paramString.equals("SORT_VALUE_MAX_")) {
        if (bool) {
          paramTransferHistInfo = (TransferHistInfo)localArrayList1.get(localArrayList1.size() - 1);
        } else {
          paramTransferHistInfo = (TransferHistInfo)localArrayList1.get(0);
        }
      }
    }
    if (paramTransferHistInfo != null)
    {
      String str3 = a(str2, paramTransferHistInfo, paramFFSConnectionHolder);
      localPagingContext.getMap().put(paramString + str2, str3);
      localPagingContext.getMap().put(paramString + "TransactionIndex", Long.toString(paramTransferHistInfo.getRecordCursor()));
    }
  }
  
  public static ArrayList getSessionPage(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "TransferTempHist.getSessionPage";
    FFSDebug.log(str1 + " : start", 6);
    FFSResultSet localFFSResultSet = null;
    Object localObject1 = null;
    TransferHistInfo localTransferHistInfo = null;
    try
    {
      int i = 0;
      int j = 0;
      String str3 = (String)paramPagingInfo.getPagingContext().getMap().get("PAGE_SIZE");
      if ((str3 != null) && (str3.length() != 0)) {
        j = Integer.parseInt(str3);
      }
      if (j == 0)
      {
        localObject2 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
        str3 = ((PropertyConfig)localObject2).otherProperties.getProperty("bpw.paging.maximum.pagesize", "250");
        i = Integer.parseInt(str3);
        paramPagingInfo.getPagingContext().getMap().put("PAGE_SIZE", str3);
      }
      else
      {
        i = j;
      }
      if (i >= 1)
      {
        localObject2 = new ArrayList();
        int k = 0;
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        boolean bool = false;
        long l1 = 0L;
        int m = 0;
        String str4 = paramPagingInfo.getPagingContext().getDirection();
        if (str4.equals("LAST"))
        {
          l1 = a(paramPagingInfo, localArrayList2, paramFFSConnectionHolder);
          long l2 = l1 / i * i;
          m = new Long(l1 - l2).intValue();
          if (m <= 0) {
            m = i;
          } else {
            i = m;
          }
        }
        String str5 = jdMethod_for(paramPagingInfo, localArrayList1, bool);
        FFSDebug.log(str1 + " params = " + localArrayList1, 6);
        Object[] arrayOfObject = localArrayList1.toArray();
        FFSDebug.log(str1 + " sql stmt = " + str5, 6);
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str5, arrayOfObject);
        int n = 1;
        while (localFFSResultSet.getNextRow())
        {
          FFSDebug.log(str1 + " counter = " + n++, 6);
          localTransferHistInfo = jdMethod_try(paramFFSConnectionHolder, localFFSResultSet);
          FFSDebug.log(str1 + " tInfo = " + localTransferHistInfo, 6);
          BPWInfoBase localBPWInfoBase = (BPWInfoBase)localTransferHistInfo;
          if (localBPWInfoBase.getStatusCode() == 0)
          {
            ((ArrayList)localObject2).add(localTransferHistInfo);
            FFSDebug.log(str1 + " rows = " + k, 6);
            FFSDebug.log(str1 + " pageSize = " + i, 6);
            k++;
            if (k >= i) {
              break;
            }
          }
        }
        localObject1 = localObject2;
        if ((str4.equals("PREVIOUS")) || (str4.equals("LAST")))
        {
          localObject1 = new ArrayList();
          while (!((ArrayList)localObject2).isEmpty()) {
            ((ArrayList)localObject1).add(0, (TransferHistInfo)((ArrayList)localObject2).remove(0));
          }
        }
        if ((j == 0) && (((ArrayList)localObject1).size() >= i) && (localFFSResultSet.getNextRow()))
        {
          paramPagingInfo.setStatusCode(28010);
          paramPagingInfo.setStatusMsg("Server can't return all records requested because the number of records server found is more than the server maximum page size.");
        }
        else
        {
          paramPagingInfo.setStatusCode(0);
          paramPagingInfo.setStatusMsg("Success");
        }
      }
      Object localObject2 = localObject1;
      return localObject2;
    }
    catch (Exception localException1)
    {
      String str2 = str1 + " failed : ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localException1), 0);
      throw new FFSException(localException1, str2);
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
        FFSDebug.log(str1 + " failed to close ResultSet " + FFSDebug.stackTrace(localException2), 0);
      }
    }
  }
  
  private static long a(PagingInfo paramPagingInfo, ArrayList paramArrayList, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "TransferTempHist.getRowCount";
    FFSDebug.log(str1 + " : start", 6);
    FFSResultSet localFFSResultSet = null;
    long l1 = 0L;
    try
    {
      String str2 = "BPW_TransfrTmpHist";
      localObject1 = paramPagingInfo.getPagingContext();
      if ((paramPagingInfo == null) || (paramPagingInfo.getPagingContext() == null) || (paramPagingInfo.getPagingContext().getMap() == null))
      {
        localObject2 = str1 + " Invalid Map inside paging context";
        FFSDebug.log((String)localObject2, 0);
        throw new FFSException((String)localObject2);
      }
      Object localObject2 = new StringBuffer();
      ((StringBuffer)localObject2).append("SELECT COUNT(*) AS ROWCOUNTS FROM ");
      ((StringBuffer)localObject2).append(str2);
      ((StringBuffer)localObject2).append(" WHERE ");
      ((StringBuffer)localObject2).append(" SessionId IN ( ? )");
      paramArrayList.add(((PagingContext)localObject1).getSessionId());
      Object[] arrayOfObject = paramArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, ((StringBuffer)localObject2).toString(), arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        l1 = localFFSResultSet.getColumnInt("ROWCOUNTS");
      }
      long l2 = l1;
      return l2;
    }
    catch (Exception localException1)
    {
      Object localObject1 = str1 + " failed : ";
      FFSDebug.log((String)localObject1 + FFSDebug.stackTrace(localException1), 0);
      throw new FFSException(localException1, (String)localObject1);
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
        FFSDebug.log(str1 + " failed to close ResultSet " + FFSDebug.stackTrace(localException2), 0);
      }
    }
  }
  
  private static TransferBatchInfo jdMethod_byte(FFSConnectionHolder paramFFSConnectionHolder, FFSResultSet paramFFSResultSet)
    throws FFSException
  {
    String str1 = "TransferTempHist.getTransferBatchInfo";
    TransferBatchInfo localTransferBatchInfo = new TransferBatchInfo();
    try
    {
      String str2 = paramFFSResultSet.getColumnString("TransactionId");
      localTransferBatchInfo = TransferBatch.getTransferBatchById(paramFFSConnectionHolder, str2, true);
      localTransferBatchInfo.setRecordCursor(paramFFSResultSet.getColumnInt("CursorId"));
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed : ";
      FFSDebug.log(str3 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str3);
    }
    FFSDebug.log(str1 + "Done. transferInfo: " + localTransferBatchInfo, 6);
    return localTransferBatchInfo;
  }
  
  private static TransferHistInfo jdMethod_try(FFSConnectionHolder paramFFSConnectionHolder, FFSResultSet paramFFSResultSet)
    throws FFSException
  {
    Object localObject = null;
    String str = paramFFSResultSet.getColumnString("TransactionType");
    if ((str != null) && (str.equals("BATCHTEMPLATE"))) {
      localObject = jdMethod_byte(paramFFSConnectionHolder, paramFFSResultSet);
    } else {
      localObject = jdMethod_new(paramFFSConnectionHolder, paramFFSResultSet);
    }
    return localObject;
  }
  
  private static TransferInfo jdMethod_new(FFSConnectionHolder paramFFSConnectionHolder, FFSResultSet paramFFSResultSet)
    throws FFSException
  {
    String str1 = "TransferTempHist.getTransferInfo";
    Object localObject1 = null;
    try
    {
      String str2 = paramFFSResultSet.getColumnString("TransactionId");
      str3 = paramFFSResultSet.getColumnString("TransactionType");
      String str4 = Integer.toString(paramFFSResultSet.getColumnInt("CursorId"));
      String str5 = paramFFSResultSet.getColumnString("Destination");
      int i = paramFFSResultSet.getColumnInt("DateToPost");
      boolean bool = FFSUtil.getBooleanFromYN(paramFFSResultSet.getColumnString("EstimatedAmount"));
      FFSDebug.log(str1 + " transtype: ", str3, 6);
      FFSDebug.log(str1 + " dest: ", str5, 6);
      FFSDebug.log(str1 + " transId: ", str2, 6);
      if (((str5.equalsIgnoreCase("ITOE")) || (str5.equalsIgnoreCase("EXTERNAL")) || (str5.equalsIgnoreCase("ETOI"))) && ((str3.equalsIgnoreCase("Current")) || (str3.equalsIgnoreCase("TEMPLATE")) || (str3.equalsIgnoreCase("Recurring"))))
      {
        localObject1 = new TransferInfo();
        ((TransferInfo)localObject1).setSrvrTId(str2);
        localObject1 = Transfer.getTransferInfo(paramFFSConnectionHolder, (TransferInfo)localObject1, false, false);
        if ((((TransferInfo)localObject1).getTransferDest() == null) || (((TransferInfo)localObject1).getTransferDest().trim().length() == 0)) {
          ((TransferInfo)localObject1).setTransferDest("ITOE");
        }
        ((TransferInfo)localObject1).setRecordCursor(Long.parseLong(str4));
        if (bool) {
          if (((TransferInfo)localObject1).getUserAssignedAmount() == UserAssignedAmount.FROM) {
            ((TransferInfo)localObject1).setToAmount(paramFFSResultSet.getColumnString("ToAmount"));
          } else if (((TransferInfo)localObject1).getUserAssignedAmount() == UserAssignedAmount.TO) {
            ((TransferInfo)localObject1).setAmount(paramFFSResultSet.getColumnString("Amount"));
          }
        }
        ((TransferInfo)localObject1).setEstimatedAmount(bool);
      }
      else if (((str5.equalsIgnoreCase("INTERNAL")) || (str5.equalsIgnoreCase("ITOI"))) && (str3.equalsIgnoreCase("TEMPLATE")))
      {
        localObject1 = new TransferInfo();
        ((TransferInfo)localObject1).setSrvrTId(str2);
        localObject1 = Transfer.getTransferInfo(paramFFSConnectionHolder, (TransferInfo)localObject1, false, false);
        if ((((TransferInfo)localObject1).getTransferDest() == null) || (((TransferInfo)localObject1).getTransferDest().trim().length() == 0)) {
          ((TransferInfo)localObject1).setTransferDest("ITOI");
        }
        ((TransferInfo)localObject1).setRecordCursor(Long.parseLong(str4));
        if (bool) {
          if (((TransferInfo)localObject1).getUserAssignedAmount() == UserAssignedAmount.FROM) {
            ((TransferInfo)localObject1).setToAmount(paramFFSResultSet.getColumnString("ToAmount"));
          } else if (((TransferInfo)localObject1).getUserAssignedAmount() == UserAssignedAmount.TO) {
            ((TransferInfo)localObject1).setAmount(paramFFSResultSet.getColumnString("Amount"));
          }
        }
        ((TransferInfo)localObject1).setEstimatedAmount(bool);
      }
      else if (((str5.equalsIgnoreCase("INTERNAL")) || (str5.equalsIgnoreCase("ITOI"))) && (str3.equalsIgnoreCase("RECTEMPLATE")))
      {
        localObject1 = new RecTransferInfo();
        ((TransferInfo)localObject1).setSrvrTId(str2);
        localObject1 = Transfer.getTransferInfo(paramFFSConnectionHolder, (TransferInfo)localObject1, true, true);
        ((TransferInfo)localObject1).setSourceRecSrvrTId(str2);
        if ((((TransferInfo)localObject1).getTransferDest() == null) || (((TransferInfo)localObject1).getTransferDest().trim().length() == 0)) {
          ((TransferInfo)localObject1).setTransferDest("ITOI");
        }
        ((TransferInfo)localObject1).setRecordCursor(Long.parseLong(str4));
        if (bool) {
          if (((TransferInfo)localObject1).getUserAssignedAmount() == UserAssignedAmount.FROM) {
            ((TransferInfo)localObject1).setToAmount(paramFFSResultSet.getColumnString("ToAmount"));
          } else if (((TransferInfo)localObject1).getUserAssignedAmount() == UserAssignedAmount.TO) {
            ((TransferInfo)localObject1).setAmount(paramFFSResultSet.getColumnString("Amount"));
          }
        }
        ((TransferInfo)localObject1).setEstimatedAmount(bool);
      }
      else if (((str5.equalsIgnoreCase("ITOE")) || (str5.equalsIgnoreCase("EXTERNAL")) || (str5.equalsIgnoreCase("ETOI"))) && ((str3.equalsIgnoreCase("Repetitive")) || (str3.equalsIgnoreCase("RECTEMPLATE"))))
      {
        localObject1 = new RecTransferInfo();
        ((TransferInfo)localObject1).setSrvrTId(str2);
        localObject1 = Transfer.getTransferInfo(paramFFSConnectionHolder, (TransferInfo)localObject1, true, true);
        ((TransferInfo)localObject1).setSourceRecSrvrTId(str2);
        if ((((TransferInfo)localObject1).getTransferDest() == null) || (((TransferInfo)localObject1).getTransferDest().trim().length() == 0)) {
          ((TransferInfo)localObject1).setTransferDest("ITOE");
        }
        if (str3.equalsIgnoreCase("Repetitive"))
        {
          ((TransferInfo)localObject1).setTransferType("Recurring");
          ((TransferInfo)localObject1).setDateDue(Integer.toString(i));
          ((TransferInfo)localObject1).setDateToPost(DBUtil.getPayday(((TransferInfo)localObject1).getFIId(), ((TransferInfo)localObject1).getDateDue()));
        }
        ((TransferInfo)localObject1).setRecordCursor(Long.parseLong(str4));
        if (bool) {
          if (((TransferInfo)localObject1).getUserAssignedAmount() == UserAssignedAmount.FROM) {
            ((TransferInfo)localObject1).setToAmount(paramFFSResultSet.getColumnString("ToAmount"));
          } else if (((TransferInfo)localObject1).getUserAssignedAmount() == UserAssignedAmount.TO) {
            ((TransferInfo)localObject1).setAmount(paramFFSResultSet.getColumnString("Amount"));
          }
        }
        ((TransferInfo)localObject1).setEstimatedAmount(bool);
      }
      else if (((str5.equalsIgnoreCase("ITOE")) || (str5.equalsIgnoreCase("EXTERNAL")) || (str5.equalsIgnoreCase("ETOI"))) && (str3.equalsIgnoreCase("Recmodel")))
      {
        localObject1 = new RecTransferInfo();
        ((TransferInfo)localObject1).setSrvrTId(str2);
        localObject1 = Transfer.getRecurringModel(paramFFSConnectionHolder, (TransferInfo)localObject1);
        ((TransferInfo)localObject1).setSourceRecSrvrTId(str2);
        if ((((TransferInfo)localObject1).getTransferDest() == null) || (((TransferInfo)localObject1).getTransferDest().trim().length() == 0)) {
          ((TransferInfo)localObject1).setTransferDest("ITOE");
        }
        if ((str3.equalsIgnoreCase("Repetitive")) || (str3.equalsIgnoreCase("Recmodel")))
        {
          ((TransferInfo)localObject1).setDateDue(Integer.toString(i));
          ((TransferInfo)localObject1).setDateToPost(DBUtil.getPayday(((TransferInfo)localObject1).getFIId(), ((TransferInfo)localObject1).getDateDue()));
          if ((localObject1 instanceof RecTransferInfo)) {
            a((RecTransferInfo)localObject1, true, paramFFSConnectionHolder);
          }
        }
        ((TransferInfo)localObject1).setRecordCursor(Long.parseLong(str4));
        if (bool) {
          if (((TransferInfo)localObject1).getUserAssignedAmount() == UserAssignedAmount.FROM) {
            ((TransferInfo)localObject1).setToAmount(paramFFSResultSet.getColumnString("ToAmount"));
          } else if (((TransferInfo)localObject1).getUserAssignedAmount() == UserAssignedAmount.TO) {
            ((TransferInfo)localObject1).setAmount(paramFFSResultSet.getColumnString("Amount"));
          }
        }
        ((TransferInfo)localObject1).setEstimatedAmount(bool);
      }
      else
      {
        Object localObject2;
        Object localObject3;
        if (((str5.equalsIgnoreCase("INTERNAL")) || (str5.equalsIgnoreCase("ITOI"))) && ((str3.equalsIgnoreCase("Current")) || (str3.equalsIgnoreCase("Recurring"))))
        {
          localObject2 = XferInstruction.getXferInstruction(paramFFSConnectionHolder, str2);
          if (localObject2 != null)
          {
            FFSDebug.log(str1 + "********* xinst.RecSrvrTID: ", ((XferInstruction)localObject2).RecSrvrTID, 6);
            localObject1 = new TransferInfo(str2, ((XferInstruction)localObject2).FIID, ((XferInstruction)localObject2).CustomerID, ((XferInstruction)localObject2).BankID, ((XferInstruction)localObject2).BranchID, ((XferInstruction)localObject2).AcctDebitID, ((XferInstruction)localObject2).AcctDebitType, ((XferInstruction)localObject2).AcctCreditID, ((XferInstruction)localObject2).AcctCreditType, ((XferInstruction)localObject2).Amount, ((XferInstruction)localObject2).CurDef, ((XferInstruction)localObject2).ToAmount, ((XferInstruction)localObject2).ToAmtCurrency, ((XferInstruction)localObject2).userAssignedAmount, bool, ((XferInstruction)localObject2).DateToPost, ((XferInstruction)localObject2).DateToPost, ((XferInstruction)localObject2).RecSrvrTID, ((XferInstruction)localObject2).LogID, ((XferInstruction)localObject2).Status, str3, ((XferInstruction)localObject2).SubmittedBy, "INTERNAL");
            ((TransferInfo)localObject1).setRecordCursor(Long.parseLong(str4));
            ((TransferInfo)localObject1).setDateCreate(((XferInstruction)localObject2).DateCreate);
            if (bool) {
              if (((XferInstruction)localObject2).userAssignedAmount == UserAssignedAmount.FROM) {
                ((TransferInfo)localObject1).setToAmount(paramFFSResultSet.getColumnString("ToAmount"));
              } else if (((XferInstruction)localObject2).userAssignedAmount == UserAssignedAmount.TO) {
                ((TransferInfo)localObject1).setAmount(paramFFSResultSet.getColumnString("Amount"));
              }
            }
            localObject3 = new ExtTransferAcctInfo();
            ((ExtTransferAcctInfo)localObject3).setAcctBankRtn(((XferInstruction)localObject2).BankID);
            ((ExtTransferAcctInfo)localObject3).setBankRtnType("FEDABA");
            ((ExtTransferAcctInfo)localObject3).setAcctNum(((XferInstruction)localObject2).AcctDebitID);
            ((ExtTransferAcctInfo)localObject3).setAcctType(((XferInstruction)localObject2).AcctDebitType);
            ((TransferInfo)localObject1).setAccountFromInfo((ExtTransferAcctInfo)localObject3);
            localObject3 = new ExtTransferAcctInfo();
            ((ExtTransferAcctInfo)localObject3).setAcctBankRtn(((XferInstruction)localObject2).BankID);
            ((ExtTransferAcctInfo)localObject3).setBankRtnType("FEDABA");
            ((ExtTransferAcctInfo)localObject3).setAcctNum(((XferInstruction)localObject2).AcctCreditID);
            ((ExtTransferAcctInfo)localObject3).setAcctType(((XferInstruction)localObject2).AcctCreditType);
            ((TransferInfo)localObject1).setAccountToInfo((ExtTransferAcctInfo)localObject3);
            if (((XferInstruction)localObject2).extraFields != null)
            {
              ((TransferInfo)localObject1).setMemo(Transfer.buildMemoFromXtraInfo((HashMap)((XferInstruction)localObject2).extraFields));
              ((TransferInfo)localObject1).extraFields = ((XferInstruction)localObject2).extraFields;
            }
          }
          else
          {
            localObject1 = new TransferInfo();
            ((TransferInfo)localObject1).setStatusCode(16020);
            ((TransferInfo)localObject1).setStatusMsg(" record not found");
          }
        }
        else if (((str5.equalsIgnoreCase("INTERNAL")) || (str5.equalsIgnoreCase("ITOI"))) && (str3.equalsIgnoreCase("Repetitive")))
        {
          localObject2 = RecXferInstruction.getRecXferInstruction(paramFFSConnectionHolder, str2);
          if (localObject2 != null)
          {
            localObject1 = new RecTransferInfo(str2, ((RecXferInstruction)localObject2).FIID, ((RecXferInstruction)localObject2).CustomerID, ((RecXferInstruction)localObject2).BankID, ((RecXferInstruction)localObject2).BranchID, ((RecXferInstruction)localObject2).AcctDebitID, ((RecXferInstruction)localObject2).AcctDebitType, ((RecXferInstruction)localObject2).AcctCreditID, ((RecXferInstruction)localObject2).AcctCreditType, ((RecXferInstruction)localObject2).Amount, ((RecXferInstruction)localObject2).CurDef, ((RecXferInstruction)localObject2).ToAmount, ((RecXferInstruction)localObject2).ToAmtCurrency, ((RecXferInstruction)localObject2).userAssignedAmount, bool, Integer.toString(i), Integer.toString(i), str2, ((RecXferInstruction)localObject2).LogID, "WILLPROCESSON", "Recurring", ((RecXferInstruction)localObject2).SubmittedBy, "INTERNAL");
            ((TransferInfo)localObject1).setRecordCursor(Long.parseLong(str4));
            ((TransferInfo)localObject1).setDateCreate(((RecXferInstruction)localObject2).DateCreate);
            if (bool) {
              if (((RecXferInstruction)localObject2).userAssignedAmount == UserAssignedAmount.FROM) {
                ((TransferInfo)localObject1).setToAmount(paramFFSResultSet.getColumnString("ToAmount"));
              } else if (((RecXferInstruction)localObject2).userAssignedAmount == UserAssignedAmount.TO) {
                ((TransferInfo)localObject1).setAmount(paramFFSResultSet.getColumnString("Amount"));
              }
            }
            localObject3 = new ExtTransferAcctInfo();
            ((ExtTransferAcctInfo)localObject3).setAcctBankRtn(((RecXferInstruction)localObject2).BankID);
            ((ExtTransferAcctInfo)localObject3).setBankRtnType("FEDABA");
            ((ExtTransferAcctInfo)localObject3).setAcctNum(((RecXferInstruction)localObject2).AcctDebitID);
            ((ExtTransferAcctInfo)localObject3).setAcctType(((RecXferInstruction)localObject2).AcctDebitType);
            ((TransferInfo)localObject1).setAccountFromInfo((ExtTransferAcctInfo)localObject3);
            localObject3 = new ExtTransferAcctInfo();
            ((ExtTransferAcctInfo)localObject3).setAcctBankRtn(((RecXferInstruction)localObject2).BankID);
            ((ExtTransferAcctInfo)localObject3).setBankRtnType("FEDABA");
            ((ExtTransferAcctInfo)localObject3).setAcctNum(((RecXferInstruction)localObject2).AcctCreditID);
            ((ExtTransferAcctInfo)localObject3).setAcctType(((RecXferInstruction)localObject2).AcctCreditType);
            ((TransferInfo)localObject1).setAccountToInfo((ExtTransferAcctInfo)localObject3);
            ((RecTransferInfo)localObject1).setFrequency(FFSUtil.getFreqString(((RecXferInstruction)localObject2).Frequency));
            ((RecTransferInfo)localObject1).setPmtsCount(((RecXferInstruction)localObject2).InstanceCount);
            if (((RecXferInstruction)localObject2).extraFields != null)
            {
              ((TransferInfo)localObject1).setMemo(Transfer.buildMemoFromXtraInfo((HashMap)((RecXferInstruction)localObject2).extraFields));
              ((TransferInfo)localObject1).extraFields = ((RecXferInstruction)localObject2).extraFields;
            }
          }
          else
          {
            localObject1 = new RecTransferInfo();
            ((TransferInfo)localObject1).setStatusCode(16020);
            ((TransferInfo)localObject1).setStatusMsg(" record not found");
          }
        }
        else if (((str5.equalsIgnoreCase("INTERNAL")) || (str5.equalsIgnoreCase("ITOI"))) && (str3.equalsIgnoreCase("Recmodel")))
        {
          localObject2 = RecXferInstruction.getRecXferModel(paramFFSConnectionHolder, str2);
          if (localObject2 != null)
          {
            localObject3 = new RecTransferInfo(str2, ((RecXferInstruction)localObject2).FIID, ((RecXferInstruction)localObject2).CustomerID, ((RecXferInstruction)localObject2).BankID, ((RecXferInstruction)localObject2).BranchID, ((RecXferInstruction)localObject2).AcctDebitID, ((RecXferInstruction)localObject2).AcctDebitType, ((RecXferInstruction)localObject2).AcctCreditID, ((RecXferInstruction)localObject2).AcctCreditType, ((RecXferInstruction)localObject2).Amount, ((RecXferInstruction)localObject2).CurDef, ((RecXferInstruction)localObject2).ToAmount, ((RecXferInstruction)localObject2).ToAmtCurrency, ((RecXferInstruction)localObject2).userAssignedAmount, bool, Integer.toString(i), Integer.toString(i), str2, ((RecXferInstruction)localObject2).LogID, "WILLPROCESSON", "Recmodel", ((RecXferInstruction)localObject2).SubmittedBy, "INTERNAL");
            ((RecTransferInfo)localObject3).setPmtsCount(((RecXferInstruction)localObject2).InstanceCount);
            a((RecTransferInfo)localObject3, false, paramFFSConnectionHolder);
            ((RecTransferInfo)localObject3).setFrequency(FFSUtil.getFreqString(((RecXferInstruction)localObject2).Frequency));
            ((RecTransferInfo)localObject3).setStartDate(String.valueOf(((RecXferInstruction)localObject2).StartDate));
            ((RecTransferInfo)localObject3).setEndDate(String.valueOf(((RecXferInstruction)localObject2).EndDate));
            localObject1 = localObject3;
            ((TransferInfo)localObject1).setRecordCursor(Long.parseLong(str4));
            ((TransferInfo)localObject1).setDateCreate(((RecXferInstruction)localObject2).DateCreate);
            if (bool) {
              if (((RecXferInstruction)localObject2).userAssignedAmount == UserAssignedAmount.FROM) {
                ((RecTransferInfo)localObject3).setToAmount(paramFFSResultSet.getColumnString("ToAmount"));
              } else if (((RecXferInstruction)localObject2).userAssignedAmount == UserAssignedAmount.TO) {
                ((RecTransferInfo)localObject3).setAmount(paramFFSResultSet.getColumnString("Amount"));
              }
            }
            ExtTransferAcctInfo localExtTransferAcctInfo = new ExtTransferAcctInfo();
            localExtTransferAcctInfo.setAcctBankRtn(((RecXferInstruction)localObject2).BankID);
            localExtTransferAcctInfo.setBankRtnType("FEDABA");
            localExtTransferAcctInfo.setAcctNum(((RecXferInstruction)localObject2).AcctDebitID);
            localExtTransferAcctInfo.setAcctType(((RecXferInstruction)localObject2).AcctDebitType);
            ((TransferInfo)localObject1).setAccountFromInfo(localExtTransferAcctInfo);
            localExtTransferAcctInfo = new ExtTransferAcctInfo();
            localExtTransferAcctInfo.setAcctBankRtn(((RecXferInstruction)localObject2).BankID);
            localExtTransferAcctInfo.setBankRtnType("FEDABA");
            localExtTransferAcctInfo.setAcctNum(((RecXferInstruction)localObject2).AcctCreditID);
            localExtTransferAcctInfo.setAcctType(((RecXferInstruction)localObject2).AcctCreditType);
            ((TransferInfo)localObject1).setAccountToInfo(localExtTransferAcctInfo);
            if (((RecXferInstruction)localObject2).extraFields != null) {
              ((TransferInfo)localObject1).setMemo(Transfer.buildMemoFromXtraInfo((HashMap)((RecXferInstruction)localObject2).extraFields));
            }
          }
          else
          {
            localObject1 = new RecTransferInfo();
            ((TransferInfo)localObject1).setStatusCode(16020);
            ((TransferInfo)localObject1).setStatusMsg(" record not found");
          }
        }
        else
        {
          localObject1 = new TransferInfo();
          ((TransferInfo)localObject1).setStatusCode(23190);
          ((TransferInfo)localObject1).setStatusMsg("Invalid History Record Type");
          FFSDebug.log(str1 + " : Invalid History " + "Transaction Type : transId = " + str2 + " transType = " + str3, 0);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed : ";
      FFSDebug.log(str3 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str3);
    }
    FFSDebug.log(str1 + "Done. transferInfo: " + localObject1, 6);
    return localObject1;
  }
  
  private static void a(RecTransferInfo paramRecTransferInfo, boolean paramBoolean, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "RECINTRATRN";
    if (paramBoolean) {
      str1 = "RECETFTRN";
    }
    ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(paramRecTransferInfo.getFIId(), str1, paramRecTransferInfo.getSrvrTId(), paramFFSConnectionHolder);
    if (localScheduleInfo != null)
    {
      int i = localScheduleInfo.CurInstanceNum;
      int j = localScheduleInfo.InstanceCount;
      String str2 = paramRecTransferInfo.getDateToPost();
      if (str2.length() < 10) {
        str2 = str2 + "00";
      }
      int k = Integer.parseInt(str2);
      int m = localScheduleInfo.NextInstanceDate;
      while (((i <= j) || (localScheduleInfo.Perpetual == 1)) && (m < k))
      {
        m = ScheduleInfo.computeFutureDate(m, localScheduleInfo.Frequency, 1);
        i++;
      }
      paramRecTransferInfo.setPmtsCount(j - i + 1);
    }
  }
  
  protected static String jdMethod_for(PagingInfo paramPagingInfo, ArrayList paramArrayList, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferTempHist.createSortingSql";
    FFSDebug.log(str1 + " : start", 6);
    String str2 = "BPW_TransfrTmpHist";
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    HashMap localHashMap = null;
    if ((paramPagingInfo == null) || (paramPagingInfo.getPagingContext() == null) || (paramPagingInfo.getPagingContext().getMap() == null))
    {
      localObject = str1 + " Invalid Map inside paging context";
      FFSDebug.log((String)localObject, 0);
      throw new FFSException((String)localObject);
    }
    localHashMap = paramPagingInfo.getPagingContext().getMap();
    Object localObject = localPagingContext.getSortCriteriaList();
    SortCriterion localSortCriterion = null;
    if ((localObject == null) || (((ArrayList)localObject).size() == 0))
    {
      localSortCriterion = new SortCriterion();
      localSortCriterion.setName("DateToPost");
      localSortCriterion.setAscending();
    }
    else
    {
      localSortCriterion = (SortCriterion)((ArrayList)localObject).get(0);
    }
    boolean bool = localSortCriterion.isAscending();
    String str3 = localSortCriterion.getName();
    String str4 = localPagingContext.getDirection();
    String str5 = null;
    String str6 = null;
    int i = 0;
    if (str4.equals("NEXT"))
    {
      if (bool)
      {
        str5 = (String)localHashMap.get("SORT_VALUE_MAX_" + str3);
        str6 = (String)localHashMap.get("SORT_VALUE_MAX_TransactionIndex");
      }
      else
      {
        str5 = (String)localHashMap.get("SORT_VALUE_MIN_" + str3);
        str6 = (String)localHashMap.get("SORT_VALUE_MIN_TransactionIndex");
      }
    }
    else if (str4.equals("PREVIOUS"))
    {
      if (bool)
      {
        str5 = (String)localHashMap.get("SORT_VALUE_MIN_" + str3);
        str6 = (String)localHashMap.get("SORT_VALUE_MIN_TransactionIndex");
      }
      else
      {
        str5 = (String)localHashMap.get("SORT_VALUE_MAX_" + str3);
        str6 = (String)localHashMap.get("SORT_VALUE_MAX_TransactionIndex");
      }
    }
    else if (str4.equals("LAST")) {
      i = 1;
    }
    String str7 = "<";
    if ((bool) && (str4.equals("NEXT"))) {
      str7 = ">";
    } else if ((!bool) && (str4.equals("PREVIOUS"))) {
      str7 = ">";
    } else if ((!bool) && (str4.equals("LAST"))) {
      str7 = ">";
    } else if ((bool) && (str4.equals("LAST"))) {
      str7 = "<";
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT * FROM ");
    localStringBuffer.append(str2);
    localStringBuffer.append(" WHERE ");
    localStringBuffer.append(" SessionId = ? ");
    paramArrayList.add(localPagingContext.getSessionId());
    if ((str5 != null) && (str6 != null) && (!paramBoolean) && (i == 0))
    {
      localStringBuffer.append(" AND ((");
      localStringBuffer.append(str3);
      localStringBuffer.append(str7);
      localStringBuffer.append("?");
      localStringBuffer.append(") OR (");
      localStringBuffer.append(str3);
      localStringBuffer.append("=");
      localStringBuffer.append("? AND CursorId");
      localStringBuffer.append(str7);
      localStringBuffer.append("?))");
      if ((str3.equalsIgnoreCase("Amount")) || (str3.equalsIgnoreCase("ToAmount")))
      {
        paramArrayList.add(new BigDecimal(str5));
        paramArrayList.add(new BigDecimal(str5));
      }
      else if (str3.equalsIgnoreCase("DateToPost"))
      {
        paramArrayList.add(new Integer(str5));
        paramArrayList.add(new Integer(str5));
      }
      else if (str3.equalsIgnoreCase("CursorId"))
      {
        paramArrayList.add(new Integer(str5));
        paramArrayList.add(new Integer(str5));
      }
      else
      {
        paramArrayList.add(str5);
        paramArrayList.add(str5);
      }
      paramArrayList.add(new Integer(str6));
    }
    localStringBuffer.append(" ORDER BY ");
    localStringBuffer.append(str3);
    if ((paramBoolean) || (str4.equals("FIRST")))
    {
      if (bool) {
        localStringBuffer.append(" ASC, CursorId ASC");
      } else {
        localStringBuffer.append(" DESC, CursorId DESC");
      }
    }
    else if (str7.equals(">")) {
      localStringBuffer.append(" ASC, CursorId ASC");
    } else {
      localStringBuffer.append(" DESC, CursorId DESC");
    }
    String str8 = localStringBuffer.toString();
    FFSDebug.log(str1 + " sql stmt = " + str8, 6);
    for (int j = 0; j < paramArrayList.size(); j++) {
      FFSDebug.log(str1 + " Sql Param:" + j + " :" + String.valueOf(paramArrayList.get(j)), 6);
    }
    FFSDebug.log(str1 + " : end", 6);
    return str8;
  }
  
  private static String a(String paramString, TransferHistInfo paramTransferHistInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "TransferTempHist.getColumnValue";
    String str2 = null;
    try
    {
      Object localObject1;
      if (((paramTransferHistInfo instanceof TransferInfo)) || ((paramTransferHistInfo instanceof RecTransferInfo)))
      {
        localObject1 = (TransferInfo)paramTransferHistInfo;
        if (paramString.equalsIgnoreCase("CursorId"))
        {
          str2 = Long.toString(((TransferInfo)localObject1).getRecordCursor());
        }
        else if (paramString.equalsIgnoreCase("TransactionId"))
        {
          str2 = ((TransferInfo)localObject1).getSrvrTId();
        }
        else if (paramString.equalsIgnoreCase("TransactionType"))
        {
          str2 = ((TransferInfo)localObject1).getTransferType();
        }
        else if (paramString.equalsIgnoreCase("DateToPost"))
        {
          FFSDebug.log(str1 + ": dateToPost= " + ((TransferInfo)localObject1).getDateToPost(), 6);
          int i = 0;
          if (((TransferInfo)localObject1).getTransferDest().equals("INTERNAL")) {
            i = BPWUtil.getDateDBFormat(((TransferInfo)localObject1).getDateToPost());
          } else {
            i = BPWUtil.getDateDBFormat(((TransferInfo)localObject1).getDateDue());
          }
          str2 = "" + i;
        }
        else
        {
          Object localObject2;
          if (paramString.equalsIgnoreCase("Frequency"))
          {
            FFSDebug.log(str1 + ": tInfo= " + localObject1, 6);
            FFSDebug.log(str1 + ": tInfo class " + localObject1.getClass().getName(), 6);
            FFSDebug.log(str1 + ": tInfo.getSourceRecSrvrTId() " + ((TransferInfo)localObject1).getSourceRecSrvrTId(), 6);
            if ((localObject1 instanceof RecTransferInfo))
            {
              localObject2 = (RecTransferInfo)localObject1;
              str2 = ((RecTransferInfo)localObject2).getFrequency();
            }
            else if ((((TransferInfo)localObject1).getSourceRecSrvrTId() != null) && (((TransferInfo)localObject1).getSourceRecSrvrTId().trim().length() != 0))
            {
              localObject2 = ((TransferInfo)localObject1).getTransferDest();
              FFSDebug.log(str1 + ": transferDest " + (String)localObject2, 6);
              str2 = jdMethod_for(((TransferInfo)localObject1).getSourceRecSrvrTId(), (String)localObject2, paramFFSConnectionHolder);
            }
            else
            {
              str2 = "NONE";
            }
            FFSDebug.log(str1 + ": Frequency value " + str2, 6);
            FFSDebug.log(str1 + ": getSourceRecSrvrTId value " + ((TransferInfo)localObject1).getSourceRecSrvrTId(), 6);
          }
          else if (paramString.equalsIgnoreCase("FromAcctId"))
          {
            str2 = ((TransferInfo)localObject1).buildFromAcctId();
          }
          else if (paramString.equalsIgnoreCase("ToAcctId"))
          {
            str2 = ((TransferInfo)localObject1).buildToAcctId();
          }
          else if (paramString.equalsIgnoreCase("Destination"))
          {
            str2 = ((TransferInfo)localObject1).getTransferDest();
          }
          else if (paramString.equalsIgnoreCase("Status"))
          {
            str2 = ((TransferInfo)localObject1).getPrcStatus();
          }
          else if (paramString.equalsIgnoreCase("Amount"))
          {
            FFSDebug.log(str1 + "tInfo.getAmount(): " + ((TransferInfo)localObject1).getAmount(), 6);
            localObject2 = new BigDecimal(((TransferInfo)localObject1).getAmount()).setScale(2, 5);
            str2 = ((BigDecimal)localObject2).toString();
            FFSDebug.log(str1 + "amountValue.toString(): " + str2, 6);
          }
          else if (paramString.equalsIgnoreCase("TemplateName"))
          {
            str2 = ((TransferInfo)localObject1).getTemplateNickName();
          }
          else if (paramString.equalsIgnoreCase("ToAmount"))
          {
            FFSDebug.log(str1 + "tInfo.getToAmount(): " + ((TransferInfo)localObject1).getToAmount(), 6);
            localObject2 = new BigDecimal(((TransferInfo)localObject1).getToAmount()).setScale(2, 5);
            str2 = ((BigDecimal)localObject2).toString();
            FFSDebug.log(str1 + "amountValue.toString(): " + str2, 6);
          }
          else if (paramString.equalsIgnoreCase("AmountCurrency"))
          {
            str2 = ((TransferInfo)localObject1).getAmountCurrency();
          }
          else if (paramString.equalsIgnoreCase("ToAmountCurrency"))
          {
            str2 = ((TransferInfo)localObject1).getToAmountCurrency();
          }
        }
      }
      else if ((paramTransferHistInfo instanceof TransferBatchInfo))
      {
        localObject1 = (TransferBatchInfo)paramTransferHistInfo;
        if (paramString.equalsIgnoreCase("DateToPost"))
        {
          int j = BPWUtil.getDateDBFormat(((TransferBatchInfo)localObject1).getSubmitDate());
          str2 = "" + j;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
    return str2;
  }
  
  private static String jdMethod_for(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "TransferTempHist.getFrequency";
    FFSDebug.log(str1 + ": Entering ... recSrvrTid: " + paramString1, 6);
    FFSDebug.log(str1 + ": Entering ... transferDest: " + paramString2, 6);
    String str2 = null;
    if ((paramString2 == null) || (paramString1 == null)) {
      return str2;
    }
    if ("INTERNAL".equalsIgnoreCase(paramString2)) {
      try
      {
        RecXferInstruction localRecXferInstruction = RecXferInstruction.getRecXferInstruction(paramFFSConnectionHolder, paramString1);
        FFSDebug.log(str1 + ": rti ... " + localRecXferInstruction, 6);
        if (localRecXferInstruction != null)
        {
          try
          {
            str2 = FFSUtil.getFreqString(localRecXferInstruction.Frequency);
          }
          catch (Throwable localThrowable3)
          {
            localThrowable3.printStackTrace();
          }
          FFSDebug.log(str1 + " freqIntStr :" + str2, 6);
        }
      }
      catch (Throwable localThrowable1)
      {
        String str3 = "*** " + str1 + " failed: ";
        String str5 = FFSDebug.stackTrace(localThrowable1);
        FFSDebug.log(str3, str5, 0);
        throw new FFSException(localThrowable1, str3);
      }
    } else {
      try
      {
        Object localObject = new RecTransferInfo();
        ((TransferInfo)localObject).setSrvrTId(paramString1);
        boolean bool1 = true;
        FFSDebug.log(str1 + ": recTrans: " + localObject, 6);
        boolean bool2 = true;
        localObject = Transfer.getTransferInfo(paramFFSConnectionHolder, (TransferInfo)localObject, bool2, bool1);
        FFSDebug.log(str1 + ": recTrans: " + localObject, 6);
        if ((localObject == null) || (((TransferInfo)localObject).getStatusCode() != 0))
        {
          FFSDebug.log(str1 + "Failed to retrieve recurring model for a single instance: " + paramString1, 0);
          return null;
        }
        str2 = ((RecTransferInfo)localObject).getFrequency();
        FFSDebug.log(str1 + "freqIntStr: " + str2, 6);
        try
        {
          int i = Integer.parseInt(str2);
          str2 = FFSUtil.getFreqString(i);
        }
        catch (Throwable localThrowable4) {}
        FFSDebug.log(str1 + " freqIntStr :" + str2, 6);
      }
      catch (Throwable localThrowable2)
      {
        String str4 = "*** " + str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localThrowable2);
        FFSDebug.log(str4, str6, 0);
        throw new FFSException(localThrowable2, str4);
      }
    }
    return str2;
  }
  
  private static void jdMethod_void(TransferInfo paramTransferInfo)
  {
    String str1 = paramTransferInfo.getSubmittedBy();
    if (paramTransferInfo.getToAmount() == null) {
      paramTransferInfo.setToAmount("0.00");
    }
    String str2;
    if (((paramTransferInfo.getAmount().equals("0.0")) || (paramTransferInfo.getAmount().equals("0.00"))) && (paramTransferInfo.getUserAssignedAmount() == UserAssignedAmount.TO))
    {
      str2 = a(str1, paramTransferInfo.getToAmount(), paramTransferInfo.getToAmountCurrency(), paramTransferInfo.getAmountCurrency());
      paramTransferInfo.setAmount(str2);
      paramTransferInfo.setEstimatedAmount(true);
    }
    else if ((paramTransferInfo.getToAmount().equals("0.00")) && (paramTransferInfo.getUserAssignedAmount() == UserAssignedAmount.FROM))
    {
      str2 = a(str1, paramTransferInfo.getAmount(), paramTransferInfo.getAmountCurrency(), paramTransferInfo.getToAmountCurrency());
      paramTransferInfo.setToAmount(str2);
      paramTransferInfo.setEstimatedAmount(true);
    }
  }
  
  private static String a(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(0.0D);
    try
    {
      FXService localFXService = (FXService)FFSRegistry.lookup("FXSERVICE");
      BigDecimal localBigDecimal2 = new BigDecimal(paramString2);
      HashMap localHashMap = new HashMap();
      FXRate localFXRate = localFXService.getFXRate(Integer.parseInt(paramString1), paramString3, paramString4, 4, paramString1, localHashMap);
      if (localFXRate != null)
      {
        localBigDecimal1 = localBigDecimal2.multiply(localFXRate.getBuyPrice().getAmountValue());
        try
        {
          int i = FXUtil.getNumDecimals(paramString4, localHashMap);
          localBigDecimal1 = localBigDecimal1.setScale(i, 5);
        }
        catch (FXException localFXException2)
        {
          DebugLog.log(Level.WARNING, "Unable to get FX rate: " + localFXException2);
        }
      }
      else
      {
        DebugLog.log(Level.WARNING, "FX rate not availaible for " + paramString3 + " to " + paramString4);
      }
    }
    catch (FXException localFXException1)
    {
      DebugLog.log(Level.WARNING, "Unable to get FX rate: " + localFXException1);
    }
    return localBigDecimal1.toString();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.TransferTempHist
 * JD-Core Version:    0.7.0.1
 */