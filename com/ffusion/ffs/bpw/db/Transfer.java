package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.AccountTransactions;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ETFACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ETFACHEntryInfo;
import com.ffusion.ffs.bpw.interfaces.ETFACHFileInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecTransferInfo;
import com.ffusion.ffs.bpw.interfaces.TransferBatchInfo;
import com.ffusion.ffs.bpw.interfaces.TransferHistInfo;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.ffs.bpw.master.XferProcessor;
import com.ffusion.ffs.bpw.master.XferSyncProcessor;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfoLog;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.enums.UserAssignedAmount;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

public class Transfer
  implements FFSConst, ACHConsts, DBConsts, BPWResource
{
  private static final String j6 = " ORDER BY SrvrTId";
  private static final String j7 = " ORDER BY RecSrvrTId";
  private static final String j5 = " ORDER BY DateToPost";
  private static int j4 = 0;
  public static final int TRANSFER_SINGLE = 0;
  public static final int TRANSFER_RECURRING_INSTANCE = 1;
  public static final int TRANSFER_RECURRING = 2;
  
  private static int ac()
    throws FFSException
  {
    if (j4 == 0)
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      String str = localPropertyConfig.otherProperties.getProperty("bpw.paging.maximum.sessionsize", "1000");
      j4 = Integer.parseInt(str);
    }
    return j4;
  }
  
  private static void jdMethod_if(PagingInfo paramPagingInfo, StringBuffer paramStringBuffer, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Transfer.createPagingSearchCriteria";
    FFSDebug.log(str1 + " : start ", 6);
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    String str2 = null;
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    String[] arrayOfString3 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    if (paramStringBuffer == null) {
      paramStringBuffer = new StringBuffer();
    }
    if (paramArrayList == null) {
      paramArrayList = new ArrayList();
    }
    Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramPagingInfo.getStartDate()));
    Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramPagingInfo.getEndDate()));
    paramArrayList.add(localInteger1);
    paramArrayList.add(localInteger2);
    str2 = (String)localHashMap2.get("CustomerId");
    DBUtil.appendStringToCondition(paramStringBuffer, paramArrayList, "a.CustomerId", str2);
    arrayOfString1 = (String[])localHashMap2.get("SubmittedBys");
    DBUtil.appendStringArrayToCondition(paramStringBuffer, paramArrayList, "a.SubmittedBy", arrayOfString1);
    arrayOfString2 = (String[])localHashMap2.get("UserIds");
    DBUtil.appendStringArrayToCondition(paramStringBuffer, paramArrayList, "a.OriginatingUserId", arrayOfString2);
    arrayOfString3 = (String[])localHashMap2.get("Dests");
    DBUtil.appendStringArrayToCondition(paramStringBuffer, paramArrayList, "a.TransferDest", arrayOfString3);
    str3 = (String)localHashMap2.get("TemplateId");
    DBUtil.appendStringToCondition(paramStringBuffer, paramArrayList, "SourceTemplateId", str3);
    str4 = (String)localHashMap2.get("MinAmount");
    if ((str4 != null) && (str4.trim().length() != 0))
    {
      paramStringBuffer.append(" AND cast (Amount as decimal) >= cast (? as decimal) ");
      paramArrayList.add(new BigDecimal(str4));
    }
    str5 = (String)localHashMap2.get("MaxAmount");
    if ((str5 != null) && (str5.trim().length() != 0))
    {
      paramStringBuffer.append(" AND cast (Amount as decimal) <= cast (? as decimal) ");
      paramArrayList.add(new BigDecimal(str5));
    }
    str6 = (String)localHashMap2.get("CategoryList");
    DBUtil.appendStringToCondition(paramStringBuffer, paramArrayList, "a.TransferCategory", str6);
    ExtTransferAcctInfo localExtTransferAcctInfo1 = (ExtTransferAcctInfo)localHashMap2.get("FromAcct");
    if (localExtTransferAcctInfo1 != null) {
      if (localExtTransferAcctInfo1.getAcctId() != null) {
        DBUtil.appendStringToCondition(paramStringBuffer, paramArrayList, "a.AccountFromId", localExtTransferAcctInfo1.getAcctId());
      } else if (localExtTransferAcctInfo1.getAcctNum() != null) {
        DBUtil.appendStringToCondition(paramStringBuffer, paramArrayList, "a.AccountFromNum", localExtTransferAcctInfo1.getAcctNum());
      }
    }
    ExtTransferAcctInfo localExtTransferAcctInfo2 = (ExtTransferAcctInfo)localHashMap2.get("ToAcct");
    if (localExtTransferAcctInfo2 != null) {
      if (localExtTransferAcctInfo2.getAcctId() != null)
      {
        DBUtil.appendStringToCondition(paramStringBuffer, paramArrayList, "a.ExternalAcctId", localExtTransferAcctInfo2.getAcctId());
      }
      else if (localExtTransferAcctInfo2.getAcctNum() != null)
      {
        paramStringBuffer.append(" AND (a.ExternalAcctId = (select AcctId from ETF_ExternalAcct ext where ext.AcctId = a.ExternalAcctId and ext.AcctNum = ?)) ");
        paramArrayList.add(localExtTransferAcctInfo2.getAcctNum());
      }
    }
    String str7 = (String)localHashMap2.get("Amount");
    if ((str7 != null) && (str7.trim().length() != 0)) {
      try
      {
        NumberFormat localNumberFormat = NumberFormat.getNumberInstance();
        localNumberFormat.setGroupingUsed(false);
        localNumberFormat.setMinimumFractionDigits(2);
        float f = Float.parseFloat(str7);
        str7 = localNumberFormat.format(f);
        paramStringBuffer.append(" AND (a.Amount = ? or a.ToAmount = ?) ");
        paramArrayList.add(str7);
        paramArrayList.add(str7);
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
    String str8 = (String)localHashMap2.get("Currency");
    if ((str8 != null) && (str8.trim().length() != 0)) {
      DBUtil.appendStringToCondition(paramStringBuffer, paramArrayList, "a.AmountCurrency", str8);
    }
    FFSDebug.log(str1 + " : where clause as " + paramStringBuffer.toString(), 6);
    FFSDebug.log(str1 + " : end. ", 6);
  }
  
  private static void jdMethod_do(PagingInfo paramPagingInfo, StringBuffer paramStringBuffer, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Transfer.createBatchSearchCriteria";
    FFSDebug.log(str1 + " : start ", 6);
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    String str2 = null;
    String[] arrayOfString = null;
    if (paramStringBuffer == null) {
      paramStringBuffer = new StringBuffer();
    }
    if (paramArrayList == null) {
      paramArrayList = new ArrayList();
    }
    int i = 0;
    str2 = (String)localHashMap2.get("CustomerId");
    if ((str2 != null) && (str2.trim().length() != 0))
    {
      paramStringBuffer.append(" CustomerID = ? ");
      paramArrayList.add(str2);
      i = 1;
    }
    arrayOfString = (String[])localHashMap2.get("SubmittedBys");
    if ((arrayOfString != null) && (arrayOfString.length > 0))
    {
      if (i != 0) {
        paramStringBuffer.append("  AND ");
      }
      FFSDebug.log(str1 + " submittedBy.length:" + arrayOfString.length, 6);
      if (arrayOfString.length == 1)
      {
        paramStringBuffer.append("  SubmittedBy = ? ");
        paramArrayList.add(arrayOfString[0]);
      }
      else
      {
        paramStringBuffer.append(" SubmittedBy IN (?");
        paramArrayList.add(arrayOfString[0]);
        for (int j = 1; j < arrayOfString.length; j++)
        {
          paramStringBuffer.append(", ?");
          paramArrayList.add(arrayOfString[j]);
        }
        paramStringBuffer.append(")");
      }
    }
    FFSDebug.log(str1 + " : where clause as " + paramStringBuffer.toString(), 6);
    FFSDebug.log(str1 + " : end. ", 6);
  }
  
  public static void createSessionData(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    jdMethod_if(paramPagingInfo, localStringBuffer, localArrayList);
    String str = localStringBuffer.toString();
    String[] arrayOfString = (String[])localHashMap2.get("TransType");
    if ((arrayOfString == null) || (arrayOfString.length == 0))
    {
      jdMethod_try(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      jdMethod_byte(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      jdMethod_for(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      jdMethod_new(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      jdMethod_int(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
    }
    else
    {
      if (containsInArray(arrayOfString, "Current")) {
        jdMethod_try(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      }
      if (containsInArray(arrayOfString, "Recurring")) {
        jdMethod_byte(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      }
      if (containsInArray(arrayOfString, "TEMPLATE")) {
        jdMethod_new(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      }
      if (containsInArray(arrayOfString, "RECTEMPLATE")) {
        jdMethod_int(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      }
      if (containsInArray(arrayOfString, "BATCHTEMPLATE")) {
        a(paramFFSConnectionHolder, paramPagingInfo);
      }
      if (containsInArray(arrayOfString, "Recmodel")) {
        jdMethod_do(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      }
      if (containsInArray(arrayOfString, "Repetitive")) {
        jdMethod_for(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      }
    }
  }
  
  private static void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Transfer.createSessionDataTransfer: ";
    FFSDebug.log(str1 + "Starts ...", 6);
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = localPropertyConfig.getBatchSize();
      int j = paramPagingInfo.getTotalTrans();
      Object localObject1 = null;
      String str3 = paramPagingInfo.getPagingContext().getSessionId();
      if ((str3 == null) || (str3.trim().length() == 0))
      {
        str3 = DBUtil.getNextIndexString("SessionID");
        paramPagingInfo.getPagingContext().setSessionId(str3);
      }
      StringBuffer localStringBuffer = new StringBuffer("SELECT * FROM BPW_Transfer a  WHERE DateToPost >= ? AND DateToPost <= ?");
      if (paramString != null) {
        localStringBuffer.append(paramString);
      }
      String[] arrayOfString = (String[])localHashMap2.get("StatusList");
      if ((arrayOfString != null) && (arrayOfString.length > 0))
      {
        localStringBuffer.append(" AND Status IN (?");
        paramArrayList.add(arrayOfString[0]);
        for (k = 1; k < arrayOfString.length; k++)
        {
          localStringBuffer.append(", ?");
          paramArrayList.add(arrayOfString[k]);
        }
        localStringBuffer.append(")");
      }
      else
      {
        localStringBuffer.append(" AND Status NOT IN (?, ?)");
        paramArrayList.add("CANCELEDON");
        paramArrayList.add("TEMPLATE");
      }
      localStringBuffer.append(" ORDER BY SrvrTId");
      FFSDebug.log(str1 + "Sql Statement: " + localStringBuffer.toString(), 6);
      for (int k = 0; k < paramArrayList.size(); k++) {
        FFSDebug.log(str1 + " Sql Param:" + k + " :" + String.valueOf(paramArrayList.get(k)), 6);
      }
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        localObject2 = String.valueOf(localIterator.next());
        localArrayList.add(localObject2);
      }
      Object localObject2 = localArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject2);
      while (localFFSResultSet.getNextRow())
      {
        boolean bool1 = false;
        String str4 = localFFSResultSet.getColumnString("SourceRecSrvrTId");
        FFSDebug.log(str1 + ": recSrvrTid: " + str4, 6);
        if ((str4 != null) && (str4.trim().length() > 0)) {
          bool1 = true;
        }
        j++;
        if (j > ac())
        {
          paramPagingInfo.setStatusCode(28020);
          paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
          break;
        }
        if (bool1) {
          localObject1 = new RecTransferInfo();
        } else {
          localObject1 = new TransferInfo();
        }
        boolean bool2 = false;
        a((TransferInfo)localObject1, localFFSResultSet, bool2);
        jdMethod_do((TransferInfo)localObject1, paramFFSConnectionHolder, str1);
        FFSDebug.log(str1, "populated TransferInfo = " + localObject1, 6);
        if (bool1) {
          try
          {
            Object localObject3 = new RecTransferInfo();
            ((TransferInfo)localObject3).setSrvrTId(str4);
            localObject3 = getTransferInfo(paramFFSConnectionHolder, (TransferInfo)localObject3, bool1, false);
            if ((localObject3 == null) || (((TransferInfo)localObject3).getStatusCode() != 0))
            {
              FFSDebug.log(str1 + "Failed to retrieve recurring model for a single instance." + localObject1, 0);
            }
            else
            {
              FFSDebug.log(str1 + "RecTransferInfo = " + localObject3, 6);
              bool2 = true;
              str5 = ((RecTransferInfo)localObject3).getFrequency();
              FFSDebug.log(str1 + "freqIntStr = " + str5, 6);
              try
              {
                int m = Integer.parseInt(str5);
                str5 = FFSUtil.getFreqString(m);
              }
              catch (Throwable localThrowable3)
              {
                FFSDebug.log(str1 + " getFreqString failed :" + localThrowable3.toString(), 0);
              }
              ((RecTransferInfo)localObject1).setFrequency(str5);
              ((RecTransferInfo)localObject1).setPmtsCount(((RecTransferInfo)localObject3).getPmtsCount());
            }
          }
          catch (Throwable localThrowable2)
          {
            String str5 = "*** " + str1 + " failed: ";
            String str6 = FFSDebug.stackTrace(localThrowable2);
            FFSDebug.log(str5, str6, 0);
            throw new FFSException(localThrowable2, str5);
          }
        }
        TransferTempHist.createTempHist(paramFFSConnectionHolder, str3, j, (TransferInfo)localObject1, bool2);
        if (j % i == 0) {
          paramFFSConnectionHolder.conn.commit();
        }
      }
      paramPagingInfo.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      paramPagingInfo.setStatusCode(0);
      paramPagingInfo.setStatusMsg("Success");
      FFSDebug.log(str1 + "Done", 6);
    }
    catch (Throwable localThrowable1)
    {
      String str2 = str1 + "failed: ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable1), 0);
      throw new FFSException(localThrowable1, str2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + "failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  private static void jdMethod_try(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str = "Transfer.createSessionDataSingleTransfer";
    FFSDebug.log(str + " : start ", 6);
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    localStringBuffer.append(" AND a.TransferType = ? ");
    paramArrayList.add("Current");
    jdMethod_if(paramFFSConnectionHolder, paramPagingInfo, localStringBuffer.toString(), (ArrayList)paramArrayList.clone());
  }
  
  private static void jdMethod_byte(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str = "Transfer.createSessionDataPhyInstanceOfRecTransfer";
    FFSDebug.log(str + " : start ", 6);
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    localStringBuffer.append(" AND a.TransferType = ? ");
    paramArrayList.add("Recurring");
    jdMethod_if(paramFFSConnectionHolder, paramPagingInfo, localStringBuffer.toString(), (ArrayList)paramArrayList.clone());
  }
  
  private static void jdMethod_new(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str = "Transfer.createSessionDataTemplate";
    FFSDebug.log(str + " : start ", 6);
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    localStringBuffer.append(" AND a.TransferType = ? ");
    paramArrayList.add("TEMPLATE");
    jdMethod_if(paramFFSConnectionHolder, paramPagingInfo, localStringBuffer.toString(), (ArrayList)paramArrayList.clone());
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "Transfer.createSessionDataTemplateBatch: ";
    FFSDebug.log(str1 + "Starts ...", 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      StringBuffer localStringBuffer1 = new StringBuffer();
      localObject1 = new ArrayList();
      jdMethod_do(paramPagingInfo, localStringBuffer1, (ArrayList)localObject1);
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = localPropertyConfig.getBatchSize();
      int j = paramPagingInfo.getTotalTrans();
      String str2 = paramPagingInfo.getPagingContext().getSessionId();
      if ((str2 == null) || (str2.trim().length() == 0))
      {
        str2 = DBUtil.getNextIndexString("SessionID");
        paramPagingInfo.getPagingContext().setSessionId(str2);
      }
      StringBuffer localStringBuffer2 = new StringBuffer("SELECT BatchID, FIID, CustomerID,  BatchName, BatchType, TotalAmount, AmountCurrency, TransferCount,  SubmittedBy, SubmitDate, BatchStatus, Memo, LogID  FROM BPW_TransferBatch ");
      localStringBuffer2.append(" WHERE ");
      localStringBuffer2.append(localStringBuffer1);
      localStringBuffer2.append(" AND BatchStatus <> ?");
      ((ArrayList)localObject1).add("CANCELEDON");
      localStringBuffer2.append(" AND BatchType =  ?");
      ((ArrayList)localObject1).add("TEMPLATE");
      localStringBuffer2.append(" ORDER BY BatchName");
      FFSDebug.log(str1 + "Sql Statement: " + localStringBuffer2.toString(), 6);
      for (int k = 0; k < ((ArrayList)localObject1).size(); k++) {
        FFSDebug.log(str1 + " Sql Param:" + k + " :" + String.valueOf(((ArrayList)localObject1).get(k)), 6);
      }
      Object[] arrayOfObject = ((ArrayList)localObject1).toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer2.toString(), arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        TransferBatchInfo localTransferBatchInfo = TransferBatch.populateTransferBatchInfo(localFFSResultSet);
        FFSDebug.log(str1, "populated TransferInfo = " + localTransferBatchInfo, 6);
        j++;
        TransferTempHist.createTempHist(paramFFSConnectionHolder, str2, j, localTransferBatchInfo);
        if (j % i == 0) {
          paramFFSConnectionHolder.conn.commit();
        }
      }
      paramPagingInfo.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      paramPagingInfo.setStatusCode(0);
      paramPagingInfo.setStatusMsg("Success");
      FFSDebug.log(str1 + "Done", 6);
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = str1 + "failed: ";
      FFSDebug.log((String)localObject1 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, (String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + "failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  public static TransferInfo getRecurringModel(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException
  {
    String str1 = "Transfer.getRecurringModel: ";
    FFSDebug.log(str1 + "Starts ...", 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      String str2 = paramTransferInfo.getSrvrTId();
      paramTransferInfo = getTransferInfo(paramFFSConnectionHolder, paramTransferInfo, true);
      localObject1 = (RecTransferInfo)paramTransferInfo;
      StringBuffer localStringBuffer = new StringBuffer("SELECT transfer.SrvrTId, transfer.CustomerId, transfer.TransferType, transfer.TransferDest, transfer.TransferGroup, transfer.TransferCategory, transfer.BankFromRtn, transfer.AccountFromNum, transfer.AccountFromType, transfer.ExternalAcctId, transfer.Amount, transfer.AmountCurrency, transfer.OrigAmount, transfer.OrigCurrency, transfer.DateCreate, transfer.DateDue, transfer.DateToPost, transfer.DatePosted, transfer.LastProcessId, transfer.Memo, transfer.TemplateScope, transfer.TemplateNickName, transfer.SourceTemplateId, transfer.SourceRecSrvrTId, transfer.Status, transfer.SubmittedBy, transfer.LogId , transfer.ProcessLeadNumber, transfer.FIId, transfer.AccountFromId, transfer.ProcessDate, transfer.OriginatingUserId, transfer.ConfirmMsg, transfer.ConfirmNum, transfer.FundsProcessing, transfer.ProcessType, transfer.TypeDetail, transfer.LastChangeDate, transfer.Action, transfer.FundsRetry, transfer.BankFromRtnType, transfer.ProcessNumber, transfer.ToAmount, transfer.ToAmountCurrency, transfer.UserAssignedAmount FROM BPW_Transfer transfer  where transfer.SourceRecSrvrTId = ?");
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(str2);
      localStringBuffer.append(" AND Status ='WILLPROCESSON'");
      localStringBuffer.append(" ORDER BY DateToPost");
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), localArrayList.toArray());
      int i = 2147483647;
      ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(paramTransferInfo.getFIId(), "RECETFTRN", str2, paramFFSConnectionHolder);
      if (localFFSResultSet.getNextRow())
      {
        i = BPWUtil.getDateDBFormat(localFFSResultSet.getColumnString("DateDue"));
      }
      else if (localScheduleInfo != null)
      {
        localScheduleInfo.NextInstanceDate = ScheduleInfo.computeFutureDate(localScheduleInfo.NextInstanceDate, localScheduleInfo.Frequency, 1);
        i = localScheduleInfo.NextInstanceDate;
        localScheduleInfo.CurInstanceNum += 1;
      }
      else
      {
        throw new FFSException(str1 + " Unable to determine the next instance date");
      }
      String[] arrayOfString = null;
      Integer localInteger1 = new Integer(i);
      Integer localInteger2 = new Integer(2147483647);
      if (localScheduleInfo.Perpetual != 1)
      {
        arrayOfString = ScheduleInfo.getPendingDatesByStartAndEndDate(localScheduleInfo, localInteger1.intValue(), localInteger2.intValue());
        ((RecTransferInfo)localObject1).setPmtsCount(arrayOfString.length + 1);
      }
      ((RecTransferInfo)localObject1).setDateToPost(String.valueOf(i));
      ((RecTransferInfo)localObject1).setStatusCode(0);
      ((RecTransferInfo)localObject1).setStatusMsg("Success");
      FFSDebug.log(str1 + "Done.", 6);
      Object localObject2 = localObject1;
      return localObject2;
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = str1 + " failed : ";
      FFSDebug.log((String)localObject1 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, (String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + " failed to close Recurring ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  private static void jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Transfer.createSessionDataRecTransfer: ";
    FFSDebug.log(str1 + "Starts ...", 6);
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    try
    {
      StringBuffer localStringBuffer1 = new StringBuffer(paramString);
      localStringBuffer1.append(" AND a.TransferType = ? ");
      paramArrayList.add("Repetitive");
      localObject1 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = ((PropertyConfig)localObject1).getBatchSize();
      int j = paramPagingInfo.getTotalTrans();
      RecTransferInfo localRecTransferInfo = null;
      String str2 = paramPagingInfo.getPagingContext().getSessionId();
      if ((str2 == null) || (str2.trim().length() == 0))
      {
        str2 = DBUtil.getNextIndexString("SessionID");
        paramPagingInfo.getPagingContext().setSessionId(str2);
      }
      boolean bool1 = true;
      String[] arrayOfString = (String[])localHashMap2.get("StatusList");
      if ((arrayOfString != null) && (arrayOfString.length > 0)) {
        bool1 = containsInArray(arrayOfString, "WILLPROCESSON");
      }
      if (bool1)
      {
        int k = DBUtil.getCurrentStartDate();
        int m = BPWUtil.getDateDBFormat(paramPagingInfo.getEndDate());
        int n = (m - k) / 10000;
        if (n >= 0)
        {
          StringBuffer localStringBuffer2 = new StringBuffer("SELECT a.RecSrvrTId, a.CustomerId, a.TransferType, a.TransferDest, a.TransferGroup, a.TransferCategory, a.BankFromRtn, a.AccountFromNum, a.AccountFromType, a.ExternalAcctId, a.Amount, a.AmountCurrency, a.OrigAmount, a.OrigCurrency, a.DateCreate, a.StartDate, a.EndDate, a.Frequency, a.InstanceCount, a.LastProcessId, a.Memo, a.TemplateScope, a.TemplateNickName, a.SourceTemplateId, a.Status, a.SubmittedBy, a.LogId, a.ProcessLeadNumber, a.FIId, a.AccountFromId, a.OriginatingUserId, a.ConfirmMsg, a.ConfirmNum, a.FundsProcessing, a.ProcessType, a.TypeDetail, a.LastChangeDate, a.BankFromRtnType, a.ToAmount, a.ToAmountCurrency, a.UserAssignedAmount  FROM BPW_RecTransfer a  WHERE a.EndDate > ? AND a.StartDate < ? ");
          if (localStringBuffer1 != null) {
            localStringBuffer2.append(localStringBuffer1);
          }
          localStringBuffer2.append(" AND Status ='WILLPROCESSON' ");
          localStringBuffer2.append(" ORDER BY RecSrvrTId");
          FFSDebug.log(str1 + "Sql Statement = " + localStringBuffer2.toString(), 6);
          for (int i1 = 0; i1 < paramArrayList.size(); i1++) {
            FFSDebug.log(str1 + " Sql Param = " + i1 + " :" + String.valueOf(paramArrayList.get(i1)), 6);
          }
          Object[] arrayOfObject = paramArrayList.toArray();
          localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer2.toString(), arrayOfObject);
          ScheduleInfo localScheduleInfo = null;
          String str3 = null;
          String str4 = null;
          String str5 = null;
          label882:
          while (localFFSResultSet.getNextRow())
          {
            localRecTransferInfo = new RecTransferInfo();
            boolean bool2 = true;
            a(localRecTransferInfo, localFFSResultSet, bool2);
            jdMethod_do(localRecTransferInfo, paramFFSConnectionHolder, str1);
            FFSDebug.log(str1, "populated RecTransferInfo = " + localRecTransferInfo, 6);
            str3 = localRecTransferInfo.getSrvrTId();
            str4 = localRecTransferInfo.getCustomerId();
            str5 = localRecTransferInfo.getFIId();
            Object localObject2;
            if (aj(str5) == true) {
              try
              {
                CustomerInfo localCustomerInfo = Customer.getCustomerByID(str4, paramFFSConnectionHolder);
                if (localCustomerInfo == null)
                {
                  str7 = BPWLocaleUtil.getMessage(19130, new String[] { str4 }, "TRANSFER_MESSAGE");
                  paramPagingInfo.setStatusCode(19130);
                  paramPagingInfo.setStatusMsg(str7);
                  FFSDebug.log(str1 + "failed, ", str7, 0);
                  return;
                }
                str5 = localCustomerInfo.fIID;
                localRecTransferInfo.setFIId(str5);
              }
              catch (Throwable localThrowable2)
              {
                String str7 = "*** " + str1 + "failed: ";
                localObject2 = FFSDebug.stackTrace(localThrowable2);
                FFSDebug.log(str7, (String)localObject2, 0);
                throw new FFSException(localThrowable2, str7);
              }
            }
            localScheduleInfo = ScheduleInfo.getScheduleInfo(str5, "RECETFTRN", str3, paramFFSConnectionHolder);
            if (localScheduleInfo != null)
            {
              String str6 = localFFSResultSet.getColumnString("StartDate");
              int i2 = Integer.parseInt(str6);
              localScheduleInfo.NextInstanceDate = ScheduleInfo.computeFutureDate(i2, localScheduleInfo.Frequency, localScheduleInfo.CurInstanceNum - 1);
              localScheduleInfo.InstanceCount -= localScheduleInfo.CurInstanceNum - 1;
              localScheduleInfo.CurInstanceNum = 1;
              localObject2 = null;
              Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramPagingInfo.getStartDate()));
              Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramPagingInfo.getEndDate()));
              localObject2 = ScheduleInfo.getPendingDatesByStartAndEndDate(localScheduleInfo, localInteger1.intValue(), localInteger2.intValue());
              for (int i3 = 0;; i3++)
              {
                if (i3 >= localObject2.length) {
                  break label882;
                }
                j++;
                if (j > ac())
                {
                  paramPagingInfo.setStatusCode(28020);
                  paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
                  break;
                }
                localRecTransferInfo.setRecordCursor(j);
                String str8 = localObject2[i3];
                if ((str8 != null) && (str8.length() > 8)) {
                  str8 = str8.substring(0, 8);
                }
                localRecTransferInfo.setDateDue(str8);
                getValidTransferDueDate(paramFFSConnectionHolder, localRecTransferInfo);
                TransferTempHist.createTempHist(paramFFSConnectionHolder, str2, j, localRecTransferInfo, true);
                if (j % i == 0) {
                  paramFFSConnectionHolder.conn.commit();
                }
              }
            }
          }
        }
      }
      paramPagingInfo.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      paramPagingInfo.setStatusCode(0);
      paramPagingInfo.setStatusMsg("Success");
      FFSDebug.log(str1 + "Done.", 6);
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = str1 + " failed : ";
      FFSDebug.log((String)localObject1 + FFSDebug.stackTrace(localThrowable1), 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + " failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  private static void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Transfer.createSessionDataRecTransferModel: ";
    FFSDebug.log(str1 + "Starts ...", 6);
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    try
    {
      StringBuffer localStringBuffer1 = new StringBuffer(paramString);
      localStringBuffer1.append(" AND a.TransferType = ? ");
      paramArrayList.add("Repetitive");
      localObject1 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = ((PropertyConfig)localObject1).getBatchSize();
      int j = paramPagingInfo.getTotalTrans();
      RecTransferInfo localRecTransferInfo = null;
      String str2 = paramPagingInfo.getPagingContext().getSessionId();
      if ((str2 == null) || (str2.trim().length() == 0))
      {
        str2 = DBUtil.getNextIndexString("SessionID");
        paramPagingInfo.getPagingContext().setSessionId(str2);
      }
      boolean bool = true;
      String[] arrayOfString = (String[])localHashMap2.get("StatusList");
      if ((arrayOfString != null) && (arrayOfString.length > 0)) {
        bool = containsInArray(arrayOfString, "WILLPROCESSON");
      }
      if (bool)
      {
        StringBuffer localStringBuffer2 = new StringBuffer("SELECT a.RecSrvrTId, a.CustomerId, a.TransferType, a.TransferDest, a.TransferGroup, a.TransferCategory, a.BankFromRtn, a.AccountFromNum, a.AccountFromType, a.ExternalAcctId, a.Amount, a.AmountCurrency, a.OrigAmount, a.OrigCurrency, a.DateCreate, a.StartDate, a.EndDate, a.Frequency, a.InstanceCount, a.LastProcessId, a.Memo, a.TemplateScope, a.TemplateNickName, a.SourceTemplateId, a.Status, a.SubmittedBy, a.LogId, a.ProcessLeadNumber, a.FIId, a.AccountFromId, a.OriginatingUserId, a.ConfirmMsg, a.ConfirmNum, a.FundsProcessing, a.ProcessType, a.TypeDetail, a.LastChangeDate, a.BankFromRtnType, a.ToAmount, a.ToAmountCurrency, a.UserAssignedAmount  FROM BPW_RecTransfer a  WHERE a.EndDate > ? AND a.StartDate < ? ");
        if (localStringBuffer1 != null) {
          localStringBuffer2.append(localStringBuffer1);
        }
        localStringBuffer2.append(" AND Status ='WILLPROCESSON' ");
        localStringBuffer2.append(" ORDER BY RecSrvrTId");
        FFSDebug.log(str1 + "Sql Statement = " + localStringBuffer2.toString(), 6);
        for (int k = 0; k < paramArrayList.size(); k++) {
          FFSDebug.log(str1 + " Sql Param = " + k + " :" + String.valueOf(paramArrayList.get(k)), 6);
        }
        Object[] arrayOfObject = paramArrayList.toArray();
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer2.toString(), arrayOfObject);
        String str3 = null;
        String str4 = null;
        while (localFFSResultSet.getNextRow())
        {
          localRecTransferInfo = new RecTransferInfo();
          a(localRecTransferInfo, localFFSResultSet, true);
          ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(localRecTransferInfo.getFIId(), "RECETFTRN", localRecTransferInfo.getSrvrTId(), paramFFSConnectionHolder);
          Object localObject2;
          if (localScheduleInfo != null)
          {
            localObject2 = localScheduleInfo.getFirstInstanceDateInRange(Integer.parseInt(paramPagingInfo.getStartDate()), Integer.parseInt(paramPagingInfo.getEndDate()));
            if (localObject2 != null) {
              localRecTransferInfo.setDateToPost((String)localObject2);
            }
          }
          else
          {
            jdMethod_do(localRecTransferInfo, paramFFSConnectionHolder, str1);
            FFSDebug.log(str1, "populated RecTransferInfo = " + localRecTransferInfo, 6);
            str3 = localRecTransferInfo.getCustomerId();
            str4 = localRecTransferInfo.getFIId();
            if (aj(str4) == true) {
              try
              {
                localObject2 = Customer.getCustomerByID(str3, paramFFSConnectionHolder);
                if (localObject2 == null)
                {
                  str5 = BPWLocaleUtil.getMessage(19130, new String[] { str3 }, "TRANSFER_MESSAGE");
                  paramPagingInfo.setStatusCode(19130);
                  paramPagingInfo.setStatusMsg(str5);
                  FFSDebug.log(str1 + "failed, ", str5, 0);
                  return;
                }
                str4 = ((CustomerInfo)localObject2).fIID;
                localRecTransferInfo.setFIId(str4);
              }
              catch (Throwable localThrowable2)
              {
                String str5 = "*** " + str1 + "failed: ";
                String str6 = FFSDebug.stackTrace(localThrowable2);
                FFSDebug.log(str5, str6, 0);
                throw new FFSException(localThrowable2, str5);
              }
            }
            j++;
            if (j > ac())
            {
              paramPagingInfo.setStatusCode(28020);
              paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
              break;
            }
            localRecTransferInfo.setRecordCursor(j);
            localRecTransferInfo.setTransferType("Recmodel");
            TransferTempHist.createTempHist(paramFFSConnectionHolder, str2, j, localRecTransferInfo, true);
            if (j % i == 0) {
              paramFFSConnectionHolder.conn.commit();
            }
          }
        }
      }
      paramPagingInfo.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      paramPagingInfo.setStatusCode(0);
      paramPagingInfo.setStatusMsg("Success");
      FFSDebug.log(str1 + "Done.", 6);
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = str1 + " failed : ";
      FFSDebug.log((String)localObject1 + FFSDebug.stackTrace(localThrowable1), 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + " failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  private static void jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Transfer.createSessionDataRecTemplate: ";
    FFSDebug.log(str1 + "Starts ...", 6);
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    try
    {
      StringBuffer localStringBuffer1 = new StringBuffer(paramString);
      localStringBuffer1.append(" AND a.TransferType = ? ");
      paramArrayList.add("RECTEMPLATE");
      localObject1 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = ((PropertyConfig)localObject1).getBatchSize();
      int j = paramPagingInfo.getTotalTrans();
      RecTransferInfo localRecTransferInfo = null;
      String str2 = paramPagingInfo.getPagingContext().getSessionId();
      if ((str2 == null) || (str2.trim().length() == 0))
      {
        str2 = DBUtil.getNextIndexString("SessionID");
        paramPagingInfo.getPagingContext().setSessionId(str2);
      }
      boolean bool1 = false;
      String[] arrayOfString = (String[])localHashMap2.get("StatusList");
      if ((arrayOfString != null) && (arrayOfString.length > 0)) {
        bool1 = containsInArray(arrayOfString, "RECTEMPLATE");
      }
      if (bool1)
      {
        StringBuffer localStringBuffer2 = new StringBuffer("SELECT * FROM BPW_RecTransfer a  WHERE StartDate >= ? AND StartDate <= ?");
        if ((localStringBuffer1 != null) && (localStringBuffer1.length() > 0)) {
          localStringBuffer2.append(localStringBuffer1);
        }
        localStringBuffer2.append(" AND Status = ? ");
        paramArrayList.add("RECTEMPLATE");
        localStringBuffer2.append(" ORDER BY RecSrvrTId");
        Object[] arrayOfObject = paramArrayList.toArray();
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer2.toString(), arrayOfObject);
        while (localFFSResultSet.getNextRow())
        {
          j++;
          if (j > ac())
          {
            paramPagingInfo.setStatusCode(28020);
            paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
            break;
          }
          localRecTransferInfo = new RecTransferInfo();
          boolean bool2 = true;
          a(localRecTransferInfo, localFFSResultSet, bool2);
          jdMethod_do(localRecTransferInfo, paramFFSConnectionHolder, str1);
          TransferTempHist.createTempHist(paramFFSConnectionHolder, str2, j, localRecTransferInfo, true);
          if (j % i == 0) {
            paramFFSConnectionHolder.conn.commit();
          }
        }
      }
      paramPagingInfo.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      paramPagingInfo.setStatusCode(0);
      paramPagingInfo.setStatusMsg("Success");
      FFSDebug.log(str1 + "Done", 6);
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = str1 + "failed: ";
      FFSDebug.log((String)localObject1 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, (String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + " failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  public static PagingInfo getSessionPage(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "Transfer.getSessionPage";
    FFSDebug.log(str1 + " : start", 6);
    String str2 = paramPagingInfo.getPagingContext().getSessionId();
    if ((str2 == null) || (str2.trim().length() == 0))
    {
      paramPagingInfo.setStatusCode(16000);
      paramPagingInfo.setStatusMsg(" is null");
      FFSDebug.log(str1 + " : " + "Null sessionId passed", 0);
      return paramPagingInfo;
    }
    try
    {
      ArrayList localArrayList = TransferTempHist.getSessionPage(paramFFSConnectionHolder, paramPagingInfo);
      paramPagingInfo.setPagingResult(localArrayList);
      TransferTempHist.getBounds(paramFFSConnectionHolder, paramPagingInfo);
      paramPagingInfo.setPagingResult(jdMethod_do(paramPagingInfo.getPagingResult()));
      paramPagingInfo.setStatusCode(0);
      return paramPagingInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed : ";
      FFSDebug.log(str3 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  private static ArrayList jdMethod_do(ArrayList paramArrayList)
  {
    ArrayList localArrayList = new ArrayList();
    TransferHistInfo localTransferHistInfo = null;
    if (paramArrayList != null)
    {
      ListIterator localListIterator = paramArrayList.listIterator();
      while (localListIterator.hasNext())
      {
        localTransferHistInfo = (TransferHistInfo)localListIterator.next();
        if ((localTransferHistInfo instanceof TransferInfo)) {
          localArrayList.add((TransferInfo)localTransferHistInfo);
        } else if ((localTransferHistInfo instanceof TransferBatchInfo)) {
          localArrayList.add((TransferBatchInfo)localTransferHistInfo);
        }
      }
    }
    return localArrayList;
  }
  
  public static TransferInfo addTransfer(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean, BPWFIInfo paramBPWFIInfo, CustomerInfo paramCustomerInfo)
    throws FFSException
  {
    String str1 = "Transfer.addTransfer: ";
    String str2 = null;
    Object[] arrayOfObject = null;
    String str3 = null;
    FFSDebug.log(str1 + "Starts ...", 6);
    FFSDebug.log(str1 + "Recurring = " + paramBoolean, 6);
    if (paramFFSConnectionHolder == null)
    {
      paramTransferInfo.setStatusCode(16000);
      String str4 = "FFSConnectionHolder object  is null";
      paramTransferInfo.setStatusMsg(str4);
      FFSDebug.log(str1, str4, 0);
      return paramTransferInfo;
    }
    validateCustomerETFCompany(paramFFSConnectionHolder, paramTransferInfo);
    if (paramTransferInfo.getStatusCode() != 0) {
      return paramTransferInfo;
    }
    try
    {
      str3 = paramTransferInfo.getTransferType().trim();
      if (paramBoolean)
      {
        str2 = "INSERT INTO BPW_RecTransfer (RecSrvrTId, FIId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, StartDate, EndDate, Frequency, InstanceCount, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, Status, SubmittedBy, LogId, ProcessLeadNumber, AccountFromId, OriginatingUserId, FundsProcessing, ProcessType, TypeDetail, BankFromRtnType, LastChangeDate, ToAmount, ToAmountCurrency, UserAssignedAmount)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        if (str3.equalsIgnoreCase("RECTEMPLATE")) {
          paramTransferInfo.setPrcStatus("RECTEMPLATE");
        } else {
          paramTransferInfo.setPrcStatus("WILLPROCESSON");
        }
      }
      else
      {
        str2 = "INSERT INTO BPW_Transfer (SrvrTId, FIId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, DateDue, DateToPost, DatePosted, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, SourceRecSrvrTId, Status, SubmittedBy, LogId, ProcessLeadNumber, ProcessDate, AccountFromId, OriginatingUserId, FundsProcessing, ProcessType, Action, FundsRetry, TypeDetail, BankFromRtnType, LastChangeDate, BatchId, ToAmount, ToAmountCurrency, UserAssignedAmount) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        if (str3.equalsIgnoreCase("TEMPLATE")) {
          paramTransferInfo.setPrcStatus("TEMPLATE");
        }
        paramTransferInfo.setDatePosted(paramTransferInfo.getDateToPost());
      }
      if ((str3.equalsIgnoreCase("TEMPLATE")) || (str3.equalsIgnoreCase("RECTEMPLATE"))) {
        paramTransferInfo.setSourceTemplateId(paramTransferInfo.getSrvrTId());
      }
      arrayOfObject = jdMethod_long(paramTransferInfo, paramBoolean);
      for (int i = 0; i < arrayOfObject.length; i++) {
        if ((arrayOfObject[i] instanceof String)) {
          FFSDebug.log("*****", str1, "argsIns[" + i + "]:" + (String)arrayOfObject[i], 6);
        } else if ((arrayOfObject[i] instanceof Integer)) {
          FFSDebug.log("*****", str1, "argsIns[" + i + "]:" + ((Integer)arrayOfObject[i]).intValue(), 6);
        } else {
          FFSDebug.log("*****", str1, "argsIns[" + i + "]:" + arrayOfObject[i], 6);
        }
      }
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (paramTransferInfo.getExtInfo() != null)
      {
        i = BPWExtraInfo.processXtraInfo(paramFFSConnectionHolder, paramTransferInfo.getFIId(), paramTransferInfo.getSrvrTId(), str3 + "_" + "TRANSFER", paramTransferInfo.getExtInfo());
        if (i != 0)
        {
          paramTransferInfo.setStatusCode(19270);
          paramTransferInfo.setStatusMsg("Failed to add/update/delete Extra info.");
        }
        else
        {
          paramTransferInfo.setStatusCode(0);
          paramTransferInfo.setStatusMsg("Success");
        }
      }
      else
      {
        paramTransferInfo.setStatusCode(0);
        paramTransferInfo.setStatusMsg("Success");
      }
    }
    catch (Throwable localThrowable)
    {
      String str5 = str1 + " failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5, str6, 0);
      throw new FFSException(localThrowable, str5);
    }
    FFSDebug.log(str1 + "Done.", 6);
    return paramTransferInfo;
  }
  
  public static TransferInfo addTransferFromAdapter(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean, BPWFIInfo paramBPWFIInfo, CustomerInfo paramCustomerInfo)
    throws FFSException
  {
    String str1 = "Transfer.addTransferFromAdapter: ";
    String str2 = null;
    FFSDebug.log(str1 + "Starts. Recurring: " + paramBoolean, 6);
    str2 = DBUtil.getNewTransId("SrvrTID", 18);
    paramTransferInfo.setSrvrTId(str2);
    paramTransferInfo = addTransfer(paramFFSConnectionHolder, paramTransferInfo, paramBoolean, paramBPWFIInfo, paramCustomerInfo);
    FFSDebug.log(str1 + "info: " + paramTransferInfo, 6);
    FFSDebug.log(str1 + "Done", 6);
    return paramTransferInfo;
  }
  
  private static Object[] jdMethod_long(TransferInfo paramTransferInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.getInsertSQLParametersForTransfer: ";
    FFSDebug.log("*****", str1, "starts, isRecurring:" + paramBoolean, 6);
    String str2 = paramTransferInfo.getAmount();
    if (paramTransferInfo.getIsAmountEstimated()) {
      str2 = "0.00";
    }
    String str3 = paramTransferInfo.getToAmount();
    if (paramTransferInfo.getIsToAmountEstimated()) {
      str3 = "0.00";
    }
    if (paramBoolean)
    {
      localObject = (RecTransferInfo)paramTransferInfo;
      Object[] arrayOfObject = { ((RecTransferInfo)localObject).getSrvrTId(), ((RecTransferInfo)localObject).getFIId(), ((RecTransferInfo)localObject).getCustomerId(), ((RecTransferInfo)localObject).getTransferType(), ((RecTransferInfo)localObject).getTransferDest(), ((RecTransferInfo)localObject).getTransferGroup(), ((RecTransferInfo)localObject).getTransferCategory(), ((RecTransferInfo)localObject).getBankFromRtn(), ((RecTransferInfo)localObject).getAccountFromNum(), ((RecTransferInfo)localObject).getAccountFromType(), ((RecTransferInfo)localObject).getAccountToId(), str2, ((RecTransferInfo)localObject).getAmountCurrency(), ((RecTransferInfo)localObject).getOrigAmount(), ((RecTransferInfo)localObject).getOrigCurrency(), DBUtil.getCurrentLogDate(), new Integer(BPWUtil.getDateDBFormat(((RecTransferInfo)localObject).getStartDate())), new Integer(BPWUtil.getDateDBFormat(((RecTransferInfo)localObject).getEndDate())), new Integer(FFSUtil.getFreqInt(((RecTransferInfo)localObject).getFrequency())), new Integer(((RecTransferInfo)localObject).getPmtsCount()), ((RecTransferInfo)localObject).getLastProcessId(), ((RecTransferInfo)localObject).getMemo(), ((RecTransferInfo)localObject).getTemplateScope(), ((RecTransferInfo)localObject).getTemplateNickName(), ((RecTransferInfo)localObject).getSourceTemplateId(), ((RecTransferInfo)localObject).getPrcStatus(), paramTransferInfo.getSubmittedBy(), paramTransferInfo.getLogId(), new Integer(((RecTransferInfo)localObject).getProcessLeadNumber()), ((RecTransferInfo)localObject).getAccountFromId(), ((RecTransferInfo)localObject).getOriginatingUserId(), new Integer(((RecTransferInfo)localObject).getFundsProcessing()), new Integer(((RecTransferInfo)localObject).getProcessType()), ((RecTransferInfo)localObject).getTypeDetail(), ((RecTransferInfo)localObject).getBankFromRtnType(), DBUtil.getCurrentLogDate(), str3, ((RecTransferInfo)localObject).getToAmountCurrency(), new Integer(((RecTransferInfo)localObject).getUserAssignedAmount().getValue()) };
      return arrayOfObject;
    }
    Object localObject = { paramTransferInfo.getSrvrTId(), paramTransferInfo.getFIId(), paramTransferInfo.getCustomerId(), paramTransferInfo.getTransferType(), paramTransferInfo.getTransferDest(), paramTransferInfo.getTransferGroup(), paramTransferInfo.getTransferCategory(), paramTransferInfo.getBankFromRtn(), paramTransferInfo.getAccountFromNum(), paramTransferInfo.getAccountFromType(), paramTransferInfo.getAccountToId(), str2, paramTransferInfo.getAmountCurrency(), paramTransferInfo.getOrigAmount(), paramTransferInfo.getOrigCurrency(), DBUtil.getCurrentLogDate(), String.valueOf(BPWUtil.getDateDBFormat(paramTransferInfo.getDateDue())), String.valueOf(BPWUtil.getDateDBFormat(paramTransferInfo.getDateToPost())), String.valueOf(BPWUtil.getDateDBFormat(paramTransferInfo.getDatePosted())), paramTransferInfo.getLastProcessId(), paramTransferInfo.getMemo(), paramTransferInfo.getTemplateScope(), paramTransferInfo.getTemplateNickName(), paramTransferInfo.getSourceTemplateId(), paramTransferInfo.getSourceRecSrvrTId(), paramTransferInfo.getPrcStatus(), paramTransferInfo.getSubmittedBy(), paramTransferInfo.getLogId(), new Integer(paramTransferInfo.getProcessLeadNumber()), String.valueOf(BPWUtil.getDateDBFormat(paramTransferInfo.getProcessDate())), paramTransferInfo.getAccountFromId(), paramTransferInfo.getOriginatingUserId(), new Integer(paramTransferInfo.getFundsProcessing()), new Integer(paramTransferInfo.getProcessType()), paramTransferInfo.getAction(), new Integer(paramTransferInfo.getFundsRetry()), paramTransferInfo.getTypeDetail(), paramTransferInfo.getBankFromRtnType(), DBUtil.getCurrentLogDate(), paramTransferInfo.getBatchId(), str3, paramTransferInfo.getToAmountCurrency(), new Integer(paramTransferInfo.getUserAssignedAmount().getValue()) };
    return localObject;
  }
  
  public static void validateCustomerETFCompany(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException
  {
    String str1 = "Transfer.validateCustomerETFCompany: ";
    String str2 = paramTransferInfo.getCustomerId();
    String str3 = paramTransferInfo.getFIId();
    FFSDebug.log(str1 + "CustomerID = ", str2, 6);
    FFSDebug.log(str1 + "FIID = ", str3, 6);
    try
    {
      if (!paramTransferInfo.getTransferDest().equals("ITOI"))
      {
        if (ad() == true)
        {
          localObject = Customer.getCustomerInfo(str2, paramFFSConnectionHolder, paramTransferInfo);
          if ((localObject == null) || (((CustomerInfo)localObject).getStatusCode() == 16020))
          {
            str4 = BPWLocaleUtil.getMessage(19130, new String[] { str2 }, "TRANSFER_MESSAGE");
            paramTransferInfo.setStatusCode(19130);
            paramTransferInfo.setStatusMsg(str4);
            FFSDebug.log(str1 + "failed, ", str4, 0);
            return;
          }
        }
        Object localObject = ExternalTransferCompany.getETFCompanyByFIIDAndCustomerId(paramFFSConnectionHolder, str3, str2);
        if ((localObject == null) || (localObject.length == 0))
        {
          localObject = ExternalTransferCompany.getETFCompanyByFIIDAndCustomerId(paramFFSConnectionHolder, str3, null);
          if ((localObject == null) || (localObject.length == 0))
          {
            str4 = BPWLocaleUtil.getMessage(21160, new String[] { str2 }, "TRANSFER_MESSAGE");
            paramTransferInfo.setStatusCode(21160);
            paramTransferInfo.setStatusMsg(str4);
            FFSDebug.log(str1 + "failed, ", str4, 0);
            return;
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** " + str1 + "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable, str4);
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
  }
  
  private static boolean ad()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = localPropertyConfig.otherProperties.getProperty("bpw.transfer.manageCustomers", "BPW");
    return !str.equalsIgnoreCase("EXTERNAL");
  }
  
  public static TransferInfo generateSingleTransferFromRecTransfer(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, BPWFIInfo paramBPWFIInfo, CustomerInfo paramCustomerInfo)
    throws FFSException
  {
    String str1 = "Transfer.generateSingleTransferFromRecTransfer: ";
    String str2 = null;
    FFSDebug.log(str1, "starts, recSrvrTid=", paramString1, 6);
    Object localObject1 = new RecTransferInfo();
    ((TransferInfo)localObject1).setSrvrTId(paramString1);
    localObject1 = (RecTransferInfo)getTransferInfo(paramFFSConnectionHolder, (TransferInfo)localObject1, true);
    if (((TransferInfo)localObject1).getStatusCode() != 0) {
      return localObject1;
    }
    if (((TransferInfo)localObject1).getExtInfo() != null)
    {
      localObject2 = ((TransferInfo)localObject1).getExtInfo().values().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        ValueInfo localValueInfo = (ValueInfo)((Iterator)localObject2).next();
        localValueInfo.setAction("add");
      }
    }
    ((TransferInfo)localObject1).setSourceRecSrvrTId(paramString1);
    FFSDebug.log(str1, "DueDate=", ((TransferInfo)localObject1).getDateDue(), 6);
    str2 = paramBPWFIInfo.getFIId();
    ((TransferInfo)localObject1).setFIId(str2);
    ((TransferInfo)localObject1).setDateDue(paramString2);
    getValidTransferDueDate(paramFFSConnectionHolder, (TransferInfo)localObject1);
    ((TransferInfo)localObject1).setTransferType("Recurring");
    FFSDebug.log(str1, "DueDate=", ((TransferInfo)localObject1).getDateDue(), " DtToPost=", ((TransferInfo)localObject1).getDateToPost(), 6);
    ((TransferInfo)localObject1).setPrcStatus("WILLPROCESSON");
    ((TransferInfo)localObject1).setLogId(BPWTrackingIDGenerator.getNextId());
    Object localObject2 = DBUtil.getNewTransId("SrvrTID", 18);
    ((TransferInfo)localObject1).setSrvrTId((String)localObject2);
    ((TransferInfo)localObject1).setAction("Add");
    localObject1 = addTransfer(paramFFSConnectionHolder, (TransferInfo)localObject1, false, paramBPWFIInfo, paramCustomerInfo);
    FFSDebug.log(str1, "Done", 6);
    ((TransferInfo)localObject1).setCustomerInfo(paramCustomerInfo);
    return localObject1;
  }
  
  public static TransferInfo getTransferInfo(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean)
    throws FFSException
  {
    return getTransferInfo(paramFFSConnectionHolder, paramTransferInfo, paramBoolean, true);
  }
  
  public static TransferInfo getTransferInfo(TransferInfo paramTransferInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "TransferProcessor.getTransferInfo";
    TransferInfo localTransferInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        String str2 = str1 + "Can not get DB Connection.";
        FFSDebug.log(str2, 0);
        throw new FFSException(str2);
      }
      localTransferInfo = getTransferInfo(localFFSConnectionHolder, paramTransferInfo, paramBoolean1, paramBoolean2);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str3 = str1 + "failed: ";
      FFSDebug.log(str3 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTransferInfo;
  }
  
  public static TransferInfo getTransferInfo(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "Transfer.getTransferInfo: ";
    FFSDebug.log(str1, "start, srvrTID=", paramTransferInfo.getSrvrTId(), 6);
    String str2 = null;
    Object[] arrayOfObject = { paramTransferInfo.getSrvrTId() };
    Object localObject1 = null;
    FFSResultSet localFFSResultSet = null;
    if (paramBoolean1)
    {
      str2 = "SELECT RecSrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, StartDate, EndDate, Frequency, InstanceCount, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, BankFromRtnType, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_RecTransfer  WHERE RecSrvrTId = ? ";
      localObject1 = new RecTransferInfo();
    }
    else
    {
      str2 = "SELECT SrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, DateDue, DateToPost, DatePosted, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, SourceRecSrvrTId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, ProcessDate, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, Action, FundsRetry, BankFromRtnType, ProcessNumber, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_Transfer  WHERE SrvrTId = ? ";
      localObject1 = new TransferInfo();
    }
    if (paramBoolean2) {
      str2 = str2 + " AND Status != 'CANCELEDON'";
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        a((TransferInfo)localObject1, localFFSResultSet, paramBoolean1);
        jdMethod_do((TransferInfo)localObject1, paramFFSConnectionHolder, str1);
      }
      else
      {
        ((TransferInfo)localObject1).setSrvrTId(paramTransferInfo.getSrvrTId());
        ((TransferInfo)localObject1).setStatusCode(16020);
        ((TransferInfo)localObject1).setStatusMsg(" record not found");
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + "failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
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
        String str5 = str1 + "failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1, "Done", 6);
    return localObject1;
  }
  
  public static TransferInfo[] getTransfersByRecSrvrTId(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    return getTransfersByRecSrvrTId(paramFFSConnectionHolder, paramString, paramBoolean, null);
  }
  
  public static TransferInfo[] getTransfersByRecSrvrTId(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean, String[] paramArrayOfString)
    throws FFSException
  {
    String str1 = "Transfer.getTransfersByRecSrvrTId:";
    FFSDebug.log(str1, "Start", 6);
    TransferInfo[] arrayOfTransferInfo = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      ArrayList localArrayList = new ArrayList();
      localObject1 = new ArrayList();
      ((ArrayList)localObject1).add(paramString);
      str2 = "SELECT transfer.SrvrTId, transfer.CustomerId, transfer.TransferType, transfer.TransferDest, transfer.TransferGroup, transfer.TransferCategory, transfer.BankFromRtn, transfer.AccountFromNum, transfer.AccountFromType, transfer.ExternalAcctId, transfer.Amount, transfer.AmountCurrency, transfer.OrigAmount, transfer.OrigCurrency, transfer.DateCreate, transfer.DateDue, transfer.DateToPost, transfer.DatePosted, transfer.LastProcessId, transfer.Memo, transfer.TemplateScope, transfer.TemplateNickName, transfer.SourceTemplateId, transfer.SourceRecSrvrTId, transfer.Status, transfer.SubmittedBy, transfer.LogId , transfer.ProcessLeadNumber, transfer.FIId, transfer.AccountFromId, transfer.ProcessDate, transfer.OriginatingUserId, transfer.ConfirmMsg, transfer.ConfirmNum, transfer.FundsProcessing, transfer.ProcessType, transfer.TypeDetail, transfer.LastChangeDate, transfer.Action, transfer.FundsRetry, transfer.BankFromRtnType, transfer.ProcessNumber, transfer.ToAmount, transfer.ToAmountCurrency, transfer.UserAssignedAmount FROM BPW_Transfer transfer  where transfer.SourceRecSrvrTId = ?";
      if (paramBoolean) {
        str2 = "SELECT transfer.SrvrTId, transfer.CustomerId, transfer.TransferType, transfer.TransferDest, transfer.TransferGroup, transfer.TransferCategory, transfer.BankFromRtn, transfer.AccountFromNum, transfer.AccountFromType, transfer.ExternalAcctId, transfer.Amount, transfer.AmountCurrency, transfer.OrigAmount, transfer.OrigCurrency, transfer.DateCreate, transfer.DateDue, transfer.DateToPost, transfer.DatePosted, transfer.LastProcessId, transfer.Memo, transfer.TemplateScope, transfer.TemplateNickName, transfer.SourceTemplateId, transfer.SourceRecSrvrTId, transfer.Status, transfer.SubmittedBy, transfer.LogId , transfer.ProcessLeadNumber, transfer.FIId, transfer.AccountFromId, transfer.ProcessDate, transfer.OriginatingUserId, transfer.ConfirmMsg, transfer.ConfirmNum, transfer.FundsProcessing, transfer.ProcessType, transfer.TypeDetail, transfer.LastChangeDate, transfer.Action, transfer.FundsRetry, transfer.BankFromRtnType, transfer.ProcessNumber, transfer.ToAmount, transfer.ToAmountCurrency, transfer.UserAssignedAmount FROM BPW_Transfer transfer  where transfer.SourceRecSrvrTId = ? and transfer.Status != 'CANCELEDON'";
      }
      Object localObject2;
      if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
      {
        localObject2 = new StringBuffer(str2);
        String str3 = "WILLPROCESSON, APPROVAL_REJECTED,APPROVAL_REJECTED,FUNDSPROCESSED";
        DBUtil.appendStringArrayToCondition((StringBuffer)localObject2, (ArrayList)localObject1, "Status", str3);
        str2 = ((StringBuffer)localObject2).toString();
      }
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, ((ArrayList)localObject1).toArray());
      while (localFFSResultSet.getNextRow())
      {
        localObject2 = new TransferInfo();
        a((TransferInfo)localObject2, localFFSResultSet, false);
        jdMethod_do((TransferInfo)localObject2, paramFFSConnectionHolder, str1);
        localArrayList.add(localObject2);
      }
      int i = localArrayList.size();
      if (i > 0)
      {
        arrayOfTransferInfo = new TransferInfo[i];
        arrayOfTransferInfo = (TransferInfo[])localArrayList.toArray(arrayOfTransferInfo);
      }
      else
      {
        arrayOfTransferInfo = new TransferInfo[1];
        arrayOfTransferInfo[0] = new TransferInfo();
        arrayOfTransferInfo[0].setStatusCode(16020);
        arrayOfTransferInfo[0].setStatusMsg(" record not found");
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = str1 + "failed: ";
      String str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1, str2, 0);
      throw new FFSException(localThrowable, (String)localObject1);
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
        String str4 = str1 + "failed: ";
        String str5 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str4, str5, 0);
      }
    }
    FFSDebug.log(str1, "Done", 6);
    return arrayOfTransferInfo;
  }
  
  public static TransferInfo[] getTransfersByRecSrvrTId(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.getTransfersByRecSrvrTId:";
    FFSDebug.log(str1, "Start", 6);
    TransferInfo[] arrayOfTransferInfo = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = paramArrayOfString.length;
      if (i > 500)
      {
        arrayOfTransferInfo = new TransferInfo[1];
        arrayOfTransferInfo[0] = new TransferInfo();
        arrayOfTransferInfo[0].setStatusCode(21540);
        arrayOfTransferInfo[0].setStatusMsg(BPWLocaleUtil.getMessage(21540, null, "TRANSFER_MESSAGE"));
        localObject1 = arrayOfTransferInfo;
        return localObject1;
      }
      localObject1 = new StringBuffer("SELECT transfer.SrvrTId, transfer.CustomerId, transfer.TransferType, transfer.TransferDest, transfer.TransferGroup, transfer.TransferCategory, transfer.BankFromRtn, transfer.AccountFromNum, transfer.AccountFromType, transfer.ExternalAcctId, transfer.Amount, transfer.AmountCurrency, transfer.OrigAmount, transfer.OrigCurrency, transfer.DateCreate, transfer.DateDue, transfer.DateToPost, transfer.DatePosted, transfer.LastProcessId, transfer.Memo, transfer.TemplateScope, transfer.TemplateNickName, transfer.SourceTemplateId, transfer.SourceRecSrvrTId, transfer.Status, transfer.SubmittedBy, transfer.LogId , transfer.ProcessLeadNumber, transfer.FIId, transfer.AccountFromId, transfer.ProcessDate, transfer.OriginatingUserId, transfer.ConfirmMsg, transfer.ConfirmNum, transfer.FundsProcessing, transfer.ProcessType, transfer.TypeDetail, transfer.LastChangeDate, transfer.Action, transfer.FundsRetry, transfer.BankFromRtnType, transfer.ProcessNumber, transfer.ToAmount, transfer.ToAmountCurrency, transfer.UserAssignedAmount FROM BPW_Transfer transfer ");
      ((StringBuffer)localObject1).append(" where transfer.SourceRecSrvrTId in ( ");
      for (int j = 0; j < i; j++) {
        ((StringBuffer)localObject1).append(" ?,");
      }
      ((StringBuffer)localObject1).deleteCharAt(((StringBuffer)localObject1).length() - 1);
      ((StringBuffer)localObject1).append(" )");
      if (paramBoolean) {
        ((StringBuffer)localObject1).append(" and transfer.Status !='").append("CANCELEDON").append("'");
      }
      ((StringBuffer)localObject1).append(" order by transfer.SourceRecSrvrTId");
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, ((StringBuffer)localObject1).toString(), paramArrayOfString);
      localObject2 = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        TransferInfo localTransferInfo = new TransferInfo();
        a(localTransferInfo, localFFSResultSet, false);
        jdMethod_do(localTransferInfo, paramFFSConnectionHolder, str1);
        ((ArrayList)localObject2).add(localTransferInfo);
      }
      int k = ((ArrayList)localObject2).size();
      if (k > 0)
      {
        arrayOfTransferInfo = new TransferInfo[k];
        arrayOfTransferInfo = (TransferInfo[])((ArrayList)localObject2).toArray(arrayOfTransferInfo);
      }
      else
      {
        arrayOfTransferInfo = new TransferInfo[1];
        arrayOfTransferInfo[0] = new TransferInfo();
        arrayOfTransferInfo[0].setStatusCode(16020);
        arrayOfTransferInfo[0].setStatusMsg(" record not found");
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = str1 + "failed: ";
      Object localObject2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1, (String)localObject2, 0);
      throw new FFSException(localThrowable, (String)localObject1);
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
        String str2 = str1 + "failed: ";
        String str3 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str2, str3, 0);
      }
    }
    FFSDebug.log(str1, "Done", 6);
    return arrayOfTransferInfo;
  }
  
  private static void jdMethod_do(TransferInfo paramTransferInfo, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    ExtTransferAcctInfo localExtTransferAcctInfo = null;
    Hashtable localHashtable = null;
    try
    {
      String str1 = paramTransferInfo.getAccountFromId();
      localExtTransferAcctInfo = new ExtTransferAcctInfo();
      if (!aj(str1))
      {
        localExtTransferAcctInfo.setAcctId(str1);
        localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo, false, false);
        if (localExtTransferAcctInfo.getStatusCode() != 0)
        {
          paramTransferInfo.setStatusCode(localExtTransferAcctInfo.getStatusCode());
          paramTransferInfo.setStatusMsg(localExtTransferAcctInfo.getStatusMsg());
          return;
        }
        paramTransferInfo.setAccountFromNum(localExtTransferAcctInfo.getAcctNum());
        paramTransferInfo.setAccountFromType(localExtTransferAcctInfo.getAcctType());
        paramTransferInfo.setBankFromRtn(localExtTransferAcctInfo.getAcctBankRtn());
        paramTransferInfo.setAccountFromInfo(localExtTransferAcctInfo);
      }
      else
      {
        localExtTransferAcctInfo.setAcctBankRtn(paramTransferInfo.getBankFromRtn());
        localExtTransferAcctInfo.setBankRtnType(paramTransferInfo.getBankFromRtnType());
        localExtTransferAcctInfo.setAcctNum(paramTransferInfo.getAccountFromNum());
        localExtTransferAcctInfo.setAcctType(paramTransferInfo.getAccountFromType());
        paramTransferInfo.setAccountFromInfo(localExtTransferAcctInfo);
      }
      str2 = paramTransferInfo.getAccountToId();
      if (!aj(str2))
      {
        localExtTransferAcctInfo = new ExtTransferAcctInfo();
        localExtTransferAcctInfo.setAcctId(str2);
        localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo, false, false);
        if (localExtTransferAcctInfo.getStatusCode() != 0)
        {
          paramTransferInfo.setStatusCode(localExtTransferAcctInfo.getStatusCode());
          paramTransferInfo.setStatusMsg(localExtTransferAcctInfo.getStatusMsg());
          return;
        }
        paramTransferInfo.setAccountToNum(localExtTransferAcctInfo.getAcctNum());
        paramTransferInfo.setAccountToType(localExtTransferAcctInfo.getAcctType());
        paramTransferInfo.setBankToRtn(localExtTransferAcctInfo.getAcctBankRtn());
        paramTransferInfo.setAccountToInfo(localExtTransferAcctInfo);
      }
      localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, paramTransferInfo.getFIId(), paramTransferInfo.getSrvrTId(), paramTransferInfo.getTransferType().trim() + "_" + "TRANSFER");
      paramTransferInfo.setExtInfo(localHashtable);
      FFSDebug.log(paramString, "populated XtraInfo = " + localHashtable, 6);
      paramTransferInfo.setStatusCode(0);
      paramTransferInfo.setStatusMsg("Success");
    }
    catch (Throwable localThrowable)
    {
      String str2 = paramString + "failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public static TransferInfo getTransferInfoByTrackingId(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "Transfer.getTransferInfoByTrackingId: ";
    FFSDebug.log(str1, "start, TrackingId=", paramTransferInfo.getLogId(), 6);
    String str2 = null;
    Object[] arrayOfObject = { paramTransferInfo.getLogId() };
    Object localObject1 = null;
    FFSResultSet localFFSResultSet = null;
    if (paramBoolean1)
    {
      str2 = "SELECT RecSrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, StartDate, EndDate, Frequency, InstanceCount, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, BankFromRtnType, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_RecTransfer  WHERE LogId = ? ";
      localObject1 = new RecTransferInfo();
    }
    else
    {
      str2 = "SELECT SrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, DateDue, DateToPost, DatePosted, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, SourceRecSrvrTId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, ProcessDate, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, Action, FundsRetry, BankFromRtnType, ProcessNumber, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_Transfer  WHERE LogId = ? ";
      localObject1 = new TransferInfo();
    }
    if (paramBoolean2) {
      str2 = str2 + " AND Status != 'CANCELEDON'";
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        a((TransferInfo)localObject1, localFFSResultSet, paramBoolean1);
        jdMethod_do((TransferInfo)localObject1, paramFFSConnectionHolder, str1);
      }
      else
      {
        ((TransferInfo)localObject1).setStatusCode(16020);
        ((TransferInfo)localObject1).setStatusMsg(" record not found");
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
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
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1, "done", 6);
    return localObject1;
  }
  
  private static void a(TransferInfo paramTransferInfo, FFSResultSet paramFFSResultSet, boolean paramBoolean)
    throws Exception
  {
    if (paramBoolean == true)
    {
      RecTransferInfo localRecTransferInfo = (RecTransferInfo)paramTransferInfo;
      localRecTransferInfo.setSrvrTId(paramFFSResultSet.getColumnString("RecSrvrTId"));
      localRecTransferInfo.setCustomerId(paramFFSResultSet.getColumnString("CustomerId"));
      localRecTransferInfo.setTransferType(paramFFSResultSet.getColumnString("TransferType"));
      localRecTransferInfo.setTransferDest(paramFFSResultSet.getColumnString("TransferDest"));
      localRecTransferInfo.setTransferGroup(paramFFSResultSet.getColumnString("TransferGroup"));
      localRecTransferInfo.setTransferCategory(paramFFSResultSet.getColumnString("TransferCategory"));
      localRecTransferInfo.setBankFromRtn(paramFFSResultSet.getColumnString("BankFromRtn"));
      localRecTransferInfo.setAccountFromNum(paramFFSResultSet.getColumnString("AccountFromNum"));
      localRecTransferInfo.setAccountFromType(paramFFSResultSet.getColumnString("AccountFromType"));
      localRecTransferInfo.setAccountToId(paramFFSResultSet.getColumnString("ExternalAcctId"));
      localRecTransferInfo.setAmount(paramFFSResultSet.getColumnString("Amount"));
      localRecTransferInfo.setAmountCurrency(paramFFSResultSet.getColumnString("AmountCurrency"));
      localRecTransferInfo.setOrigAmount(paramFFSResultSet.getColumnString("OrigAmount"));
      localRecTransferInfo.setOrigCurrency(paramFFSResultSet.getColumnString("OrigCurrency"));
      localRecTransferInfo.setDateCreate(paramFFSResultSet.getColumnString("DateCreate"));
      localRecTransferInfo.setLastProcessId(paramFFSResultSet.getColumnString("LastProcessId"));
      localRecTransferInfo.setPrcStatus(paramFFSResultSet.getColumnString("Status"));
      localRecTransferInfo.setMemo(paramFFSResultSet.getColumnString("Memo"));
      localRecTransferInfo.setTemplateScope(paramFFSResultSet.getColumnString("TemplateScope"));
      localRecTransferInfo.setTemplateNickName(paramFFSResultSet.getColumnString("TemplateNickName"));
      localRecTransferInfo.setSourceTemplateId(paramFFSResultSet.getColumnString("SourceTemplateId"));
      localRecTransferInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
      localRecTransferInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
      localRecTransferInfo.setStartDate(BPWUtil.getDateBeanFormat(Integer.parseInt(paramFFSResultSet.getColumnString("StartDate"))));
      localRecTransferInfo.setEndDate(BPWUtil.getDateBeanFormat(Integer.parseInt(paramFFSResultSet.getColumnString("EndDate"))));
      localRecTransferInfo.setFrequency(FFSUtil.getFreqString(paramFFSResultSet.getColumnInt("Frequency")));
      localRecTransferInfo.setPmtsCount(paramFFSResultSet.getColumnInt("InstanceCount"));
      localRecTransferInfo.setProcessLeadNumber(paramFFSResultSet.getColumnInt("ProcessLeadNumber"));
      localRecTransferInfo.setFIId(paramFFSResultSet.getColumnString("FIId"));
      localRecTransferInfo.setAccountFromId(paramFFSResultSet.getColumnString("AccountFromId"));
      localRecTransferInfo.setOriginatingUserId(paramFFSResultSet.getColumnString("OriginatingUserId"));
      localRecTransferInfo.setConfirmMsg(paramFFSResultSet.getColumnString("ConfirmMsg"));
      localRecTransferInfo.setConfirmNum(paramFFSResultSet.getColumnString("ConfirmNum"));
      localRecTransferInfo.setFundsProcessing(paramFFSResultSet.getColumnInt("FundsProcessing"));
      localRecTransferInfo.setProcessType(paramFFSResultSet.getColumnInt("ProcessType"));
      localRecTransferInfo.setTypeDetail(paramFFSResultSet.getColumnString("TypeDetail"));
      localRecTransferInfo.setLastChangeDate(paramFFSResultSet.getColumnString("LastChangeDate"));
      localRecTransferInfo.setBankFromRtnType(paramFFSResultSet.getColumnString("BankFromRtnType"));
      localRecTransferInfo.setToAmount(paramFFSResultSet.getColumnString("ToAmount"));
      localRecTransferInfo.setToAmountCurrency(paramFFSResultSet.getColumnString("ToAmountCurrency"));
      localRecTransferInfo.setUserAssignedAmount(UserAssignedAmount.getEnum(paramFFSResultSet.getColumnInt("UserAssignedAmount")));
    }
    else
    {
      paramTransferInfo.setSrvrTId(paramFFSResultSet.getColumnString("SrvrTId"));
      paramTransferInfo.setCustomerId(paramFFSResultSet.getColumnString("CustomerId"));
      paramTransferInfo.setTransferType(paramFFSResultSet.getColumnString("TransferType"));
      paramTransferInfo.setTransferDest(paramFFSResultSet.getColumnString("TransferDest"));
      paramTransferInfo.setTransferGroup(paramFFSResultSet.getColumnString("TransferGroup"));
      paramTransferInfo.setTransferCategory(paramFFSResultSet.getColumnString("TransferCategory"));
      paramTransferInfo.setBankFromRtn(paramFFSResultSet.getColumnString("BankFromRtn"));
      paramTransferInfo.setAccountFromNum(paramFFSResultSet.getColumnString("AccountFromNum"));
      paramTransferInfo.setAccountFromType(paramFFSResultSet.getColumnString("AccountFromType"));
      paramTransferInfo.setAccountToId(paramFFSResultSet.getColumnString("ExternalAcctId"));
      paramTransferInfo.setAmount(paramFFSResultSet.getColumnString("Amount"));
      paramTransferInfo.setAmountCurrency(paramFFSResultSet.getColumnString("AmountCurrency"));
      paramTransferInfo.setOrigAmount(paramFFSResultSet.getColumnString("OrigAmount"));
      paramTransferInfo.setOrigCurrency(paramFFSResultSet.getColumnString("OrigCurrency"));
      paramTransferInfo.setDateCreate(paramFFSResultSet.getColumnString("DateCreate"));
      paramTransferInfo.setDateDue(paramFFSResultSet.getColumnString("DateDue"));
      paramTransferInfo.setDateToPost(paramFFSResultSet.getColumnString("DateToPost"));
      paramTransferInfo.setDatePosted(paramFFSResultSet.getColumnString("DatePosted"));
      paramTransferInfo.setLastProcessId(paramFFSResultSet.getColumnString("LastProcessId"));
      paramTransferInfo.setPrcStatus(paramFFSResultSet.getColumnString("Status"));
      paramTransferInfo.setMemo(paramFFSResultSet.getColumnString("Memo"));
      paramTransferInfo.setTemplateScope(paramFFSResultSet.getColumnString("TemplateScope"));
      paramTransferInfo.setTemplateNickName(paramFFSResultSet.getColumnString("TemplateNickName"));
      paramTransferInfo.setSourceTemplateId(paramFFSResultSet.getColumnString("SourceTemplateId"));
      paramTransferInfo.setSourceRecSrvrTId(paramFFSResultSet.getColumnString("SourceRecSrvrTId"));
      paramTransferInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
      paramTransferInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
      paramTransferInfo.setProcessLeadNumber(paramFFSResultSet.getColumnInt("ProcessLeadNumber"));
      paramTransferInfo.setFIId(paramFFSResultSet.getColumnString("FIId"));
      paramTransferInfo.setAccountFromId(paramFFSResultSet.getColumnString("AccountFromId"));
      paramTransferInfo.setProcessDate(paramFFSResultSet.getColumnString("ProcessDate"));
      paramTransferInfo.setOriginatingUserId(paramFFSResultSet.getColumnString("OriginatingUserId"));
      paramTransferInfo.setConfirmMsg(paramFFSResultSet.getColumnString("ConfirmMsg"));
      paramTransferInfo.setConfirmNum(paramFFSResultSet.getColumnString("ConfirmNum"));
      paramTransferInfo.setFundsProcessing(paramFFSResultSet.getColumnInt("FundsProcessing"));
      paramTransferInfo.setProcessType(paramFFSResultSet.getColumnInt("ProcessType"));
      paramTransferInfo.setTypeDetail(paramFFSResultSet.getColumnString("TypeDetail"));
      paramTransferInfo.setLastChangeDate(paramFFSResultSet.getColumnString("LastChangeDate"));
      paramTransferInfo.setAction(paramFFSResultSet.getColumnString("Action"));
      paramTransferInfo.setFundsRetry(paramFFSResultSet.getColumnInt("FundsRetry"));
      paramTransferInfo.setBankFromRtnType(paramFFSResultSet.getColumnString("BankFromRtnType"));
      paramTransferInfo.setProcessNumber(paramFFSResultSet.getColumnInt("ProcessNumber"));
      paramTransferInfo.setToAmount(paramFFSResultSet.getColumnString("ToAmount"));
      paramTransferInfo.setToAmountCurrency(paramFFSResultSet.getColumnString("ToAmountCurrency"));
      paramTransferInfo.setUserAssignedAmount(UserAssignedAmount.getEnum(paramFFSResultSet.getColumnInt("UserAssignedAmount")));
    }
  }
  
  public static TransferInfo updateTransfer(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean, BPWFIInfo paramBPWFIInfo, CustomerInfo paramCustomerInfo)
    throws FFSException
  {
    String str1 = "Transfer.updateTransfer: ";
    StringBuffer localStringBuffer = null;
    Object[] arrayOfObject = null;
    FFSDebug.log(str1 + "Starts ...", 6);
    FFSDebug.log(str1 + "Recurring = " + paramBoolean, 6);
    if (paramFFSConnectionHolder == null)
    {
      paramTransferInfo.setStatusCode(16000);
      str2 = "FFSConnectionHolder object  is null";
      paramTransferInfo.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return paramTransferInfo;
    }
    validateCustomerETFCompany(paramFFSConnectionHolder, paramTransferInfo);
    if (paramTransferInfo.getStatusCode() != 0) {
      return paramTransferInfo;
    }
    String str2 = paramTransferInfo.getTransferType();
    try
    {
      localStringBuffer = new StringBuffer();
      if (paramBoolean)
      {
        localStringBuffer.append("UPDATE BPW_RecTransfer set TransferDest = ?, TransferGroup = ?, TransferCategory = ?, BankFromRtn = ?, AccountFromNum = ?, AccountFromType = ?, ExternalAcctId = ?, Amount = ?, AmountCurrency = ?, OrigAmount = ?, OrigCurrency = ?, StartDate = ?, EndDate = ?, Frequency = ?, InstanceCount = ?, LastProcessId = ?, Memo = ?, TemplateScope = ?, TemplateNickName = ?, SourceTemplateId = ?, SubmittedBy = ?, ProcessLeadNumber = ?, AccountFromId = ?, FundsProcessing = ?, ProcessType = ?, ConfirmMsg = ?, ConfirmNum = ?, TypeDetail = ?, LastChangeDate = ?, BankFromRtnType = ?, ToAmount = ?, ToAmountCurrency = ?, UserAssignedAmount = ? WHERE RecSrvrTId = ? ");
        if ((str2 != null) && (str2.trim().equalsIgnoreCase("RECTEMPLATE")))
        {
          localStringBuffer.append(" AND Status = '").append("RECTEMPLATE").append("' ");
        }
        else
        {
          localStringBuffer.append(" AND Status in ('");
          localStringBuffer.append("WILLPROCESSON");
          localStringBuffer.append("', '");
          localStringBuffer.append("APPROVAL_PENDING");
          localStringBuffer.append("', '");
          localStringBuffer.append("APPROVAL_REJECTED");
          localStringBuffer.append("') ");
        }
      }
      else
      {
        localStringBuffer.append("UPDATE BPW_Transfer set TransferDest = ?, TransferGroup = ?, TransferCategory = ?, BankFromRtn = ?, AccountFromNum = ?, AccountFromType = ?, ExternalAcctId = ?, Amount = ?, AmountCurrency = ?, OrigAmount = ?, OrigCurrency = ?, DateDue = ?, DateToPost = ?, DatePosted = ?, LastProcessId = ?, Memo = ?, TemplateScope = ?, TemplateNickName = ?, SourceTemplateId = ?, SubmittedBy = ?, ProcessLeadNumber = ?, ProcessDate = ? , AccountFromId = ?, FundsProcessing = ?, ProcessType = ?, Action = ?, FundsRetry = ?, ConfirmMsg = ?, ConfirmNum = ?, TypeDetail = ?, LastChangeDate = ?, BankFromRtnType = ?, ToAmount = ?, ToAmountCurrency = ?, UserAssignedAmount = ? WHERE SrvrTId = ? ");
        if ((str2 != null) && (str2.trim().equalsIgnoreCase("TEMPLATE")))
        {
          localStringBuffer.append(" AND Status = '").append("TEMPLATE").append("' ");
        }
        else
        {
          localStringBuffer.append(" AND Status in ('");
          localStringBuffer.append("WILLPROCESSON");
          localStringBuffer.append("', '");
          localStringBuffer.append("APPROVAL_PENDING");
          localStringBuffer.append("', '");
          localStringBuffer.append("APPROVAL_REJECTED");
          localStringBuffer.append("', '");
          localStringBuffer.append("FUNDSPROCESSED");
          localStringBuffer.append("', '");
          localStringBuffer.append("NOFUNDS");
          localStringBuffer.append("', '");
          localStringBuffer.append("IMMED_INPROCESS");
          localStringBuffer.append("', '");
          localStringBuffer.append("FUNDSREVERTED");
          localStringBuffer.append("')");
        }
        paramTransferInfo.setDatePosted(paramTransferInfo.getDateToPost());
      }
      arrayOfObject = jdMethod_null(paramTransferInfo, paramBoolean);
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      if (i == 0)
      {
        paramTransferInfo.setStatusCode(16020);
        paramTransferInfo.setStatusMsg(" record not found");
      }
      else if (paramTransferInfo.getExtInfo() != null)
      {
        int j = BPWExtraInfo.processXtraInfo(paramFFSConnectionHolder, paramTransferInfo.getFIId(), paramTransferInfo.getSrvrTId(), str2 + "_" + "TRANSFER", paramTransferInfo.getExtInfo());
        if (j != 0)
        {
          paramTransferInfo.setStatusCode(19270);
          paramTransferInfo.setStatusMsg("Failed to add/update/delete Extra info.");
        }
        else
        {
          paramTransferInfo.setStatusCode(0);
          paramTransferInfo.setStatusMsg("Success");
        }
      }
      else
      {
        paramTransferInfo.setStatusCode(0);
        paramTransferInfo.setStatusMsg("Success");
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    FFSDebug.log(str1 + "Done.", 6);
    return paramTransferInfo;
  }
  
  public static TransferInfo updateTransferForFundsRslt(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException
  {
    String str1 = "Transfer.updateTransferForFundsRslt";
    FFSDebug.log(str1, ": Starts ", 6);
    Object localObject1;
    if (paramFFSConnectionHolder == null)
    {
      paramTransferInfo.setStatusCode(16000);
      localObject1 = "FFSConnectionHolder object  is null";
      paramTransferInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1, (String)localObject1, 0);
      return paramTransferInfo;
    }
    try
    {
      if (paramTransferInfo.getPrcStatus() == null) {
        return paramTransferInfo;
      }
      localObject1 = new StringBuffer();
      ((StringBuffer)localObject1).append("Update ").append("BPW_Transfer").append(" set ");
      localObject2 = new ArrayList();
      int i = 0;
      if (paramTransferInfo.getPrcStatus() != null)
      {
        if (i != 0) {
          ((StringBuffer)localObject1).append(" , ");
        }
        i = 1;
        ((StringBuffer)localObject1).append(" Status = ? ");
        ((ArrayList)localObject2).add(paramTransferInfo.getPrcStatus());
      }
      if (paramTransferInfo.getFundsRetry() != 0)
      {
        if (i != 0) {
          ((StringBuffer)localObject1).append(" , ");
        }
        i = 1;
        ((StringBuffer)localObject1).append(" FundsRetry = ?");
        ((ArrayList)localObject2).add(new Integer(paramTransferInfo.getFundsRetry()));
      }
      if (paramTransferInfo.getAmount() != null)
      {
        if (i != 0) {
          ((StringBuffer)localObject1).append(" , ");
        }
        i = 1;
        ((StringBuffer)localObject1).append(" Amount = ? ");
        ((ArrayList)localObject2).add(paramTransferInfo.getAmount());
      }
      if (paramTransferInfo.getAmountCurrency() != null)
      {
        if (i != 0) {
          ((StringBuffer)localObject1).append(" , ");
        }
        i = 1;
        ((StringBuffer)localObject1).append(" AmountCurrency = ? ");
        ((ArrayList)localObject2).add(paramTransferInfo.getAmountCurrency());
      }
      if (paramTransferInfo.getToAmount() != null)
      {
        if (i != 0) {
          ((StringBuffer)localObject1).append(" , ");
        }
        i = 1;
        ((StringBuffer)localObject1).append(" ToAmount = ? ");
        ((ArrayList)localObject2).add(paramTransferInfo.getToAmount());
      }
      if (paramTransferInfo.getToAmountCurrency() != null)
      {
        if (i != 0) {
          ((StringBuffer)localObject1).append(" , ");
        }
        i = 1;
        ((StringBuffer)localObject1).append(" ToAmountCurrency = ? ");
        ((ArrayList)localObject2).add(paramTransferInfo.getToAmountCurrency());
      }
      if (paramTransferInfo.getOrigAmount() != null)
      {
        if (i != 0) {
          ((StringBuffer)localObject1).append(" , ");
        }
        i = 1;
        ((StringBuffer)localObject1).append(" OrigAmount = ? ");
        ((ArrayList)localObject2).add(paramTransferInfo.getOrigAmount());
      }
      if (paramTransferInfo.getOrigCurrency() != null)
      {
        if (i != 0) {
          ((StringBuffer)localObject1).append(" , ");
        }
        i = 1;
        ((StringBuffer)localObject1).append(" OrigCurrency = ? ");
        ((ArrayList)localObject2).add(paramTransferInfo.getOrigCurrency());
      }
      if (paramTransferInfo.getAction() != null)
      {
        if (i != 0) {
          ((StringBuffer)localObject1).append(" , ");
        }
        i = 1;
        ((StringBuffer)localObject1).append(" Action = ? ");
        ((ArrayList)localObject2).add(paramTransferInfo.getAction());
      }
      if (paramTransferInfo.getProcessDate() != null)
      {
        if (i != 0) {
          ((StringBuffer)localObject1).append(" , ");
        }
        i = 1;
        ((StringBuffer)localObject1).append(" ProcessDate = ? ");
        ((ArrayList)localObject2).add(paramTransferInfo.getProcessDate());
      }
      if (i == 0) {
        return paramTransferInfo;
      }
      ((StringBuffer)localObject1).append(" where SrvrTId = ? ");
      ((ArrayList)localObject2).add(paramTransferInfo.getSrvrTId());
      Object[] arrayOfObject = new Object[((ArrayList)localObject2).size()];
      arrayOfObject = ((ArrayList)localObject2).toArray(arrayOfObject);
      int j = DBUtil.executeStatement(paramFFSConnectionHolder, ((StringBuffer)localObject1).toString(), arrayOfObject);
      if (j == 0)
      {
        paramTransferInfo.setStatusCode(16020);
        paramTransferInfo.setStatusMsg(" record not found");
      }
      else if (paramTransferInfo.getExtInfo() != null)
      {
        int k = BPWExtraInfo.processXtraInfo(paramFFSConnectionHolder, paramTransferInfo.getFIId().trim(), paramTransferInfo.getSrvrTId().trim(), paramTransferInfo.getTransferType().trim() + "_" + "TRANSFER", paramTransferInfo.getExtInfo());
        if (k != 0)
        {
          paramTransferInfo.setStatusCode(19270);
          paramTransferInfo.setStatusMsg(BPWLocaleUtil.getMessage(19270, null, "TRANSFER_MESSAGE"));
        }
        else
        {
          paramTransferInfo.setStatusCode(0);
          paramTransferInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "TRANSFER_MESSAGE"));
        }
      }
      else
      {
        paramTransferInfo.setStatusCode(0);
        paramTransferInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "TRANSFER_MESSAGE"));
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject2 = str1 + " failed: ";
      String str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2, str2, 0);
      throw new FFSException(localThrowable, (String)localObject2);
    }
    FFSDebug.log(str1, ": Ends ", 6);
    return paramTransferInfo;
  }
  
  private static Object[] jdMethod_null(TransferInfo paramTransferInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = paramTransferInfo.getAmount();
    if (paramTransferInfo.getIsAmountEstimated()) {
      str1 = "0.00";
    }
    String str2 = paramTransferInfo.getToAmount();
    if (paramTransferInfo.getIsToAmountEstimated()) {
      str2 = "0.00";
    }
    if (paramBoolean)
    {
      localObject = (RecTransferInfo)paramTransferInfo;
      Object[] arrayOfObject = { ((RecTransferInfo)localObject).getTransferDest(), ((RecTransferInfo)localObject).getTransferGroup(), ((RecTransferInfo)localObject).getTransferCategory(), ((RecTransferInfo)localObject).getBankFromRtn(), ((RecTransferInfo)localObject).getAccountFromNum(), ((RecTransferInfo)localObject).getAccountFromType(), ((RecTransferInfo)localObject).getAccountToId(), str1, ((RecTransferInfo)localObject).getAmountCurrency(), ((RecTransferInfo)localObject).getOrigAmount(), ((RecTransferInfo)localObject).getOrigCurrency(), new Integer(BPWUtil.getDateDBFormat(((RecTransferInfo)localObject).getStartDate())), new Integer(BPWUtil.getDateDBFormat(((RecTransferInfo)localObject).getEndDate())), new Integer(FFSUtil.getFreqInt(((RecTransferInfo)localObject).getFrequency())), new Integer(((RecTransferInfo)localObject).getPmtsCount()), ((RecTransferInfo)localObject).getLastProcessId(), ((RecTransferInfo)localObject).getMemo(), ((RecTransferInfo)localObject).getTemplateScope(), ((RecTransferInfo)localObject).getTemplateNickName(), ((RecTransferInfo)localObject).getSourceTemplateId(), ((RecTransferInfo)localObject).getSubmittedBy(), new Integer(((RecTransferInfo)localObject).getProcessLeadNumber()), ((RecTransferInfo)localObject).getAccountFromId(), new Integer(((RecTransferInfo)localObject).getFundsProcessing()), new Integer(((RecTransferInfo)localObject).getProcessType()), ((RecTransferInfo)localObject).getConfirmMsg(), ((RecTransferInfo)localObject).getConfirmNum(), ((RecTransferInfo)localObject).getTypeDetail(), DBUtil.getCurrentLogDate(), ((RecTransferInfo)localObject).getBankFromRtnType(), str2, ((RecTransferInfo)localObject).getToAmountCurrency(), new Integer(((RecTransferInfo)localObject).getUserAssignedAmount().getValue()), ((RecTransferInfo)localObject).getSrvrTId() };
      return arrayOfObject;
    }
    Object localObject = { paramTransferInfo.getTransferDest(), paramTransferInfo.getTransferGroup(), paramTransferInfo.getTransferCategory(), paramTransferInfo.getBankFromRtn(), paramTransferInfo.getAccountFromNum(), paramTransferInfo.getAccountFromType(), paramTransferInfo.getAccountToId(), str1, paramTransferInfo.getAmountCurrency(), paramTransferInfo.getOrigAmount(), paramTransferInfo.getOrigCurrency(), String.valueOf(BPWUtil.getDateDBFormat(paramTransferInfo.getDateDue())), String.valueOf(BPWUtil.getDateDBFormat(paramTransferInfo.getDateToPost())), String.valueOf(BPWUtil.getDateDBFormat(paramTransferInfo.getDatePosted())), paramTransferInfo.getLastProcessId(), paramTransferInfo.getMemo(), paramTransferInfo.getTemplateScope(), paramTransferInfo.getTemplateNickName(), paramTransferInfo.getSourceTemplateId(), paramTransferInfo.getSubmittedBy(), new Integer(paramTransferInfo.getProcessLeadNumber()), String.valueOf(BPWUtil.getDateDBFormat(paramTransferInfo.getProcessDate())), paramTransferInfo.getAccountFromId(), new Integer(paramTransferInfo.getFundsProcessing()), new Integer(paramTransferInfo.getProcessType()), paramTransferInfo.getAction(), new Integer(paramTransferInfo.getFundsRetry()), paramTransferInfo.getConfirmMsg(), paramTransferInfo.getConfirmNum(), paramTransferInfo.getTypeDetail(), DBUtil.getCurrentLogDate(), paramTransferInfo.getBankFromRtnType(), str2, paramTransferInfo.getToAmountCurrency(), new Integer(paramTransferInfo.getUserAssignedAmount().getValue()), paramTransferInfo.getSrvrTId() };
    return localObject;
  }
  
  public static String[] getSrvrTIDsForRecSrvrTID(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "Transfer.getSrvrTIDsForRecSrvrTID: ";
    FFSDebug.log(str1, "start, recSrvrTid=", paramString, 6);
    String[] arrayOfString = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    StringBuffer localStringBuffer = new StringBuffer("SELECT SrvrTId FROM BPW_Transfer WHERE SourceRecSrvrTId = ?");
    localStringBuffer.append(" AND Status IN ('");
    localStringBuffer.append("WILLPROCESSON");
    localStringBuffer.append("', '");
    localStringBuffer.append("APPROVAL_PENDING");
    localStringBuffer.append("', '");
    localStringBuffer.append("APPROVAL_REJECTED");
    localStringBuffer.append("', '");
    localStringBuffer.append("FUNDSPROCESSED");
    localStringBuffer.append("', '");
    localStringBuffer.append("NOFUNDS");
    localStringBuffer.append("', '");
    localStringBuffer.append("IMMED_INPROCESS");
    localStringBuffer.append("')");
    Object[] arrayOfObject = { paramString };
    try
    {
      FFSDebug.log(str1, " stmt = " + localStringBuffer.toString(), 6);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localArrayList.add(localFFSResultSet.getColumnString("SrvrTId"));
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
    arrayOfString = (String[])localArrayList.toArray(new String[0]);
    FFSDebug.log(str1, "done", 6);
    return arrayOfString;
  }
  
  public static RecTransferInfo updateSingleForRecurring(FFSConnectionHolder paramFFSConnectionHolder, RecTransferInfo paramRecTransferInfo, BPWFIInfo paramBPWFIInfo, CustomerInfo paramCustomerInfo)
    throws FFSException
  {
    String str1 = "Transfer.updateSingleForRecurring: ";
    FFSDebug.log(str1, "Start", 6);
    String str2 = null;
    String str3 = paramRecTransferInfo.getSrvrTId();
    String[] arrayOfString = paramRecTransferInfo.getSingleIds();
    if ((arrayOfString == null) || (arrayOfString.length == 0))
    {
      FFSDebug.log(str1, "Single IDs null", 6);
      return paramRecTransferInfo;
    }
    str2 = paramRecTransferInfo.getFIId();
    if ((str2 == null) || (str2.trim().length() == 0)) {
      str2 = paramBPWFIInfo.getFIId();
    }
    String str4 = paramRecTransferInfo.getCustomerId();
    if ((str4 == null) || (str4.trim().length() == 0)) {
      str4 = paramCustomerInfo.customerID;
    }
    for (int i = 0; i < arrayOfString.length; i++)
    {
      paramRecTransferInfo.setSrvrTId(arrayOfString[i]);
      paramRecTransferInfo.setTransferType("Recurring");
      String str5 = paramRecTransferInfo.getStartDate();
      paramRecTransferInfo.setDateDue(str5);
      getValidTransferDueDate(paramFFSConnectionHolder, paramRecTransferInfo);
      updateTransfer(paramFFSConnectionHolder, paramRecTransferInfo, false, paramBPWFIInfo, paramCustomerInfo);
      if (paramRecTransferInfo.getStatusCode() != 0)
      {
        paramRecTransferInfo.setSrvrTId(str3);
        break;
      }
    }
    paramRecTransferInfo.setSrvrTId(str3);
    FFSDebug.log(str1, "End", 6);
    return paramRecTransferInfo;
  }
  
  public static String getStatus(String paramString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("Transfer.getStatus: start, srvrTID=" + paramString, 6);
    String str1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str2;
    String str3;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Exception localException)
    {
      str2 = "*** Transfer.getStatus failed: ";
      str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localException, str2);
    }
    try
    {
      str1 = getStatus(localFFSConnectionHolder, paramString, paramBoolean);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "*** Transfer.getStatus failed: ";
      str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("Transfer.getStatus: done, status=" + str1, 6);
    return str1;
  }
  
  public static String getStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("Transfer.getStatus: start, srvrTID=" + paramString, 6);
    String str1 = null;
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString };
    if (paramBoolean) {
      str1 = "SELECT Status FROM BPW_RecTransfer WHERE RecSrvrTId = ?";
    } else {
      str1 = "SELECT Status FROM BPW_Transfer WHERE SrvrTId = ?";
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        str2 = localFFSResultSet.getColumnString("Status");
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = "*** Transfer.getStatus failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3 + str4, 0);
      throw new FFSException(localThrowable, str3);
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
        String str5 = "*** Transfer.getStatus  failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5 + str6, 0);
      }
    }
    FFSDebug.log("Transfer.getStatus: done, status=" + str2, 6);
    return str2;
  }
  
  public static TransferInfo cancelTransfer(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.cancelTransfer: ";
    FFSDebug.log(str1, "Starts ...", 6);
    FFSDebug.log(str1, "SrvrTid = ", paramTransferInfo.getSrvrTId(), 6);
    String str2 = null;
    StringBuffer localStringBuffer = null;
    Object localObject1 = null;
    int i = 0;
    int j = 0;
    if (paramFFSConnectionHolder == null)
    {
      paramTransferInfo.setStatusCode(16000);
      str3 = "FFSConnectionHolder object  is null";
      paramTransferInfo.setStatusMsg(str3);
      FFSDebug.log(str1, str3, 0);
      return paramTransferInfo;
    }
    paramTransferInfo.setPrcStatus("CANCELEDON");
    String str3 = paramTransferInfo.getTransferType();
    if ((str3 != null) && (str3.trim().equalsIgnoreCase("TEMPLATE")))
    {
      FFSDebug.log(str1, " Deleting a template", 6);
      i = 1;
    }
    else if ((str3 != null) && (str3.trim().equalsIgnoreCase("RECTEMPLATE")))
    {
      j = 1;
      FFSDebug.log(str1, " Deleting a rectemplate", 6);
    }
    localStringBuffer = new StringBuffer();
    if (paramBoolean)
    {
      if (j != 0)
      {
        localStringBuffer.append("Delete From BPW_RecTransfer  WHERE RecSrvrTId = ? AND TransferType = 'RECTEMPLATE' ");
        localStringBuffer.append(" AND Status = '");
        localStringBuffer.append("RECTEMPLATE");
        localStringBuffer.append("' ");
      }
      else
      {
        localStringBuffer.append("UPDATE BPW_RecTransfer SET Status=?, SubmittedBy = ?, LastChangeDate = ? WHERE RecSrvrTId=?");
        localStringBuffer.append(" AND Status in ('");
        localStringBuffer.append("WILLPROCESSON");
        localStringBuffer.append("', '");
        localStringBuffer.append("APPROVAL_PENDING");
        localStringBuffer.append("', '");
        localStringBuffer.append("APPROVAL_REJECTED");
        localStringBuffer.append("') ");
      }
    }
    else if (i != 0)
    {
      localStringBuffer.append("Delete From BPW_Transfer  WHERE SrvrTId = ? AND TransferType = 'TEMPLATE'");
      localStringBuffer.append(" AND Status = '");
      localStringBuffer.append("TEMPLATE");
      localStringBuffer.append("' ");
    }
    else
    {
      localStringBuffer.append("UPDATE BPW_Transfer SET Status=?, SubmittedBy = ?, LastChangeDate = ?, Action = ?, FundsRetry = ? WHERE SrvrTId=?");
      localStringBuffer.append(" AND Status in ('");
      localStringBuffer.append("WILLPROCESSON");
      localStringBuffer.append("', '");
      localStringBuffer.append("APPROVAL_PENDING");
      localStringBuffer.append("', '");
      localStringBuffer.append("APPROVAL_REJECTED");
      localStringBuffer.append("', '");
      localStringBuffer.append("FUNDSPROCESSED");
      localStringBuffer.append("', '");
      localStringBuffer.append("NOFUNDS");
      localStringBuffer.append("', '");
      localStringBuffer.append("IMMED_INPROCESS");
      localStringBuffer.append("', '");
      localStringBuffer.append("FUNDSREVERTED");
      localStringBuffer.append("') ");
    }
    if ((i != 0) || (j != 0)) {
      localObject1 = new String[] { paramTransferInfo.getSrvrTId() };
    } else if (paramBoolean == true) {
      localObject1 = new Object[] { paramTransferInfo.getPrcStatus(), paramTransferInfo.getSubmittedBy(), DBUtil.getCurrentLogDate(), paramTransferInfo.getSrvrTId() };
    } else {
      localObject1 = new Object[] { paramTransferInfo.getPrcStatus(), paramTransferInfo.getSubmittedBy(), DBUtil.getCurrentLogDate(), paramTransferInfo.getAction(), new Integer(paramTransferInfo.getFundsRetry()), paramTransferInfo.getSrvrTId() };
    }
    try
    {
      str2 = localStringBuffer.toString();
      FFSDebug.log(str1, " stmt:", str2, 6);
      int k = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (k == 0)
      {
        localObject2 = new StringBuffer();
        ((StringBuffer)localObject2).append("***").append(str1);
        ((StringBuffer)localObject2).append("failed, No record is found in DB with");
        if (paramBoolean) {
          ((StringBuffer)localObject2).append(" RecSrvrTId: ");
        } else {
          ((StringBuffer)localObject2).append(" SrvrTId: ");
        }
        ((StringBuffer)localObject2).append(paramTransferInfo.getSrvrTId());
        ((StringBuffer)localObject2).append(" and CustomerID: ").append(paramTransferInfo.getCustomerId());
        FFSDebug.log(((StringBuffer)localObject2).toString(), 0);
        paramTransferInfo.setStatusCode(19280);
        paramTransferInfo.setStatusMsg("Record does not exist in DB or has already been processed or canceled.");
      }
      else
      {
        paramTransferInfo.setStatusCode(0);
        paramTransferInfo.setStatusMsg("Success");
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject2 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2 + str4, 0);
      throw new FFSException(localThrowable, (String)localObject2);
    }
    FFSDebug.log(str1, "Done", 6);
    return paramTransferInfo;
  }
  
  public static RecTransferInfo canSingleForRecurring(FFSConnectionHolder paramFFSConnectionHolder, RecTransferInfo paramRecTransferInfo)
    throws FFSException
  {
    String str = paramRecTransferInfo.getSrvrTId();
    String[] arrayOfString = paramRecTransferInfo.getSingleIds();
    if ((arrayOfString == null) || (arrayOfString.length == 0))
    {
      FFSDebug.log("Transfer.canSingleForRecurring: ", "No single transfer is found for the recurring model, ", "RecSrvrTid:", str, 6);
      return paramRecTransferInfo;
    }
    for (int i = 0; i < arrayOfString.length; i++)
    {
      paramRecTransferInfo.setSrvrTId(arrayOfString[i]);
      cancelTransfer(paramFFSConnectionHolder, paramRecTransferInfo, false);
      if (paramRecTransferInfo.getStatusCode() != 0) {
        break;
      }
    }
    paramRecTransferInfo.setSrvrTId(str);
    return paramRecTransferInfo;
  }
  
  public static BPWHist getTransferHistory(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("Transfer.getTransferHistory : start", 6);
    paramBPWHist = a(paramBPWHist, "getTransferHistory", true);
    FFSDebug.log("Transfer.getTransferHistory : transferHist:" + paramBPWHist, 6);
    if (paramBPWHist.getStatusCode() != 0) {
      return paramBPWHist;
    }
    FFSDebug.log("Transfer.getTransferHistory : calling getTransferHistoryCommon", 6);
    return a(paramFFSConnectionHolder, paramBPWHist, null, paramBoolean);
  }
  
  public static BPWHist getRecTransfers(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("Transfer.getRecTransfers:", "start", 6);
    paramBPWHist = a(paramBPWHist, "Transfer.getRecTransfers:", false);
    FFSDebug.log("Transfer.getRecTransfers:", "transferHist:" + paramBPWHist, 6);
    if (paramBPWHist.getStatusCode() != 0) {
      return paramBPWHist;
    }
    if (aj(paramBPWHist.fiId))
    {
      paramBPWHist.setStatusCode(21590);
      paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(21590, null, "TRANSFER_MESSAGE"));
    }
    if (aj(paramBPWHist.custId))
    {
      paramBPWHist.setStatusCode(21600);
      paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(21600, null, "TRANSFER_MESSAGE"));
    }
    StringBuffer localStringBuffer = new StringBuffer("SELECT RecSrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, StartDate, EndDate, Frequency, InstanceCount, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, BankFromRtnType, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_RecTransfer  where FIId = ? and CustomerId = ?");
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(paramBPWHist.fiId);
    localArrayList1.add(paramBPWHist.custId);
    if (!aj(paramBPWHist.startDate))
    {
      localStringBuffer.append(" and StartDate >= ?");
      localArrayList1.add(new Integer(BPWUtil.getDateDBFormat(paramBPWHist.startDate)));
    }
    if (!aj(paramBPWHist.endDate))
    {
      localStringBuffer.append(" and EndDate <= ?");
      localArrayList1.add(new Integer(BPWUtil.getDateDBFormat(paramBPWHist.endDate)));
    }
    int i;
    int j;
    if (paramBPWHist.submittedBy != null)
    {
      i = paramBPWHist.submittedBy.length;
      if (i > 0) {
        localStringBuffer.append(" and SubmittedBy in (");
      }
      for (j = 0; j < i; j++)
      {
        localStringBuffer.append("?");
        localArrayList1.add(paramBPWHist.submittedBy[j]);
        if (j != i - 1) {
          localStringBuffer.append(",");
        }
      }
      if (i > 0) {
        localStringBuffer.append(")");
      }
    }
    if (paramBPWHist.statusList != null)
    {
      i = paramBPWHist.statusList.length;
      if (i > 0) {
        localStringBuffer.append(" and Status in (");
      }
      for (j = 0; j < i; j++)
      {
        localStringBuffer.append("?");
        localArrayList1.add(paramBPWHist.statusList[j]);
        if (j != i - 1) {
          localStringBuffer.append(",");
        }
      }
      if (i > 0) {
        localStringBuffer.append(")");
      }
    }
    if (paramBPWHist.dest != null)
    {
      i = paramBPWHist.dest.length;
      if (i > 0) {
        localStringBuffer.append(" and TransferDest in (");
      }
      for (j = 0; j < i; j++)
      {
        localStringBuffer.append("?");
        localArrayList1.add(paramBPWHist.dest[j]);
        if (j != i - 1) {
          localStringBuffer.append(",");
        }
      }
      if (i > 0) {
        localStringBuffer.append(")");
      }
    }
    localStringBuffer.append(" order by FIId, CustomerId, Status, StartDate");
    Object[] arrayOfObject = new Object[localArrayList1.size()];
    arrayOfObject = (Object[])localArrayList1.toArray(arrayOfObject);
    FFSResultSet localFFSResultSet = null;
    try
    {
      ArrayList localArrayList2 = new ArrayList();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      for (int k = 0; (localFFSResultSet.getNextRow()) && (k <= 500); k++)
      {
        RecTransferInfo localRecTransferInfo = new RecTransferInfo();
        a(localRecTransferInfo, localFFSResultSet, true);
        jdMethod_do(localRecTransferInfo, paramFFSConnectionHolder, "Transfer.getRecTransfers:");
        localArrayList2.add(localRecTransferInfo);
      }
      int m = 0;
      if (k > 500)
      {
        localArrayList2.remove(localArrayList2.get(localArrayList2.size() - 1));
        m = 1;
      }
      if (!localArrayList2.isEmpty())
      {
        RecTransferInfo[] arrayOfRecTransferInfo = new RecTransferInfo[localArrayList2.size()];
        arrayOfRecTransferInfo = (RecTransferInfo[])localArrayList2.toArray(arrayOfRecTransferInfo);
        paramBPWHist.setTrans(arrayOfRecTransferInfo);
        if (m != 0)
        {
          paramBPWHist.setStatusCode(21680);
          paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(21680, null, "TRANSFER_MESSAGE"));
        }
        else
        {
          paramBPWHist.setStatusCode(0);
          paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "TRANSFER_MESSAGE"));
        }
      }
      else
      {
        paramBPWHist.setStatusCode(16020);
        paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(16020, null, "TRANSFER_MESSAGE"));
      }
    }
    catch (Throwable localThrowable)
    {
      String str1 = "Transfer.getRecTransfers: failed: ";
      String str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1, str2, 0);
      throw new FFSException(localThrowable, str1);
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
        String str3 = "Transfer.getRecTransfers: failed: ";
        String str4 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str3, str4, 0);
      }
    }
    FFSDebug.log("Transfer.getRecTransfers:", "end", 6);
    return paramBPWHist;
  }
  
  private static BPWHist a(BPWHist paramBPWHist, String paramString, boolean paramBoolean)
  {
    if (paramBPWHist == null)
    {
      paramBPWHist = new BPWHist();
      paramBPWHist.setStatusCode(16000);
      paramBPWHist.setStatusMsg("BPWHist  is null");
      FFSDebug.log("Transfer." + paramString + " : " + "Null BPWHist passed", 0);
      return paramBPWHist;
    }
    String[] arrayOfString = paramBPWHist.getStatusList();
    if ((arrayOfString != null) && (arrayOfString.length > 0))
    {
      int i = 0;
      int j = 0;
      for (int k = 0; k < arrayOfString.length; k++) {
        if (("TEMPLATE".equalsIgnoreCase(arrayOfString[k])) || ("RECTEMPLATE".equalsIgnoreCase(arrayOfString[k])))
        {
          i = 1;
          if (j != 0)
          {
            paramBPWHist.setStatusCode(19340);
            paramBPWHist.setStatusMsg("Status list cannot contain status TEMPLATE or RECTEMPLATE with any other status");
            FFSDebug.log("Transfer.", paramString, " : ", "Status list cannot contain status TEMPLATE or RECTEMPLATE with any other status", 0);
            return paramBPWHist;
          }
        }
        else
        {
          j = 1;
          if (i != 0)
          {
            paramBPWHist.setStatusCode(19340);
            paramBPWHist.setStatusMsg("Status list cannot contain status TEMPLATE or RECTEMPLATE with any other status");
            FFSDebug.log("Transfer.", paramString, " : ", "Status list cannot contain status TEMPLATE or RECTEMPLATE with any other status", 0);
            return paramBPWHist;
          }
        }
      }
    }
    String str1 = paramBPWHist.getTransType();
    if ((str1 != null) && (str1.trim().length() != 0) && (!str1.equalsIgnoreCase("Current")) && (!str1.equalsIgnoreCase("TEMPLATE")) && (!str1.equalsIgnoreCase("Repetitive")) && (!str1.equalsIgnoreCase("RECTEMPLATE")))
    {
      paramBPWHist.setStatusCode(19390);
      paramBPWHist.setStatusMsg("Invalid trans type in BPWHist:" + str1);
      FFSDebug.log("Transfer.", paramString, " : ", "Invalid trans type in BPWHist:", str1, 0);
      return paramBPWHist;
    }
    if (paramBoolean) {
      a(paramBPWHist);
    }
    String str2;
    if ((paramBPWHist.getStartDate() != null) && (!BPWUtil.checkFrontEndDateFormat(paramBPWHist.getStartDate())))
    {
      paramBPWHist.setStatusCode(17205);
      str2 = paramString + ":" + "Invalid start date value for getting histories." + ": " + paramBPWHist.getStartDate();
      paramBPWHist.setStatusMsg(str2);
      FFSDebug.log(str2, 0);
      return paramBPWHist;
    }
    if ((paramBPWHist.getEndDate() != null) && (!BPWUtil.checkFrontEndDateFormat(paramBPWHist.getEndDate())))
    {
      paramBPWHist.setStatusCode(17206);
      str2 = paramString + ":" + "Invalid end date value for getting histories." + ": " + paramBPWHist.getEndDate();
      paramBPWHist.setStatusMsg(str2);
      FFSDebug.log(str2, 0);
      return paramBPWHist;
    }
    FFSDebug.log("Transfer." + paramString + " : StartDate: ", paramBPWHist.getStartDate(), ", EndDate: ", paramBPWHist.getEndDate(), 6);
    if ((paramBPWHist.getStartDate() != null) && (paramBPWHist.getEndDate() != null) && (Integer.parseInt(paramBPWHist.getStartDate()) > Integer.parseInt(paramBPWHist.getEndDate())))
    {
      paramBPWHist.setStatusCode(17080);
      paramBPWHist.setStatusMsg("Start date cannot be after end date");
      FFSDebug.log("Transfer." + paramString + " : " + " Start Date is later than End Date", 0);
      return paramBPWHist;
    }
    paramBPWHist.setStatusCode(0);
    paramBPWHist.setStatusMsg("Success");
    return paramBPWHist;
  }
  
  private static void a(BPWHist paramBPWHist)
  {
    if ((paramBPWHist.getStartDate() == null) || (paramBPWHist.getStartDate().trim().length() == 0)) {
      paramBPWHist.setStartDate(String.valueOf(FFSUtil.getInstanceDate(-30)));
    } else if (paramBPWHist.getStartDate().trim().length() == 8) {
      paramBPWHist.setStartDate(paramBPWHist.getStartDate().trim() + "00");
    }
    if ((paramBPWHist.getEndDate() == null) || (paramBPWHist.getEndDate().trim().length() == 0)) {
      paramBPWHist.setEndDate(String.valueOf(FFSUtil.getInstanceDate(30)));
    } else if (paramBPWHist.getEndDate().trim().length() == 8) {
      paramBPWHist.setEndDate(paramBPWHist.getEndDate().trim() + "00");
    }
    FFSDebug.log("Transfer.fixHistDates: StartDate=", paramBPWHist.getStartDate(), " EndDate=", paramBPWHist.getEndDate(), 6);
  }
  
  public static boolean containsInArray(String[] paramArrayOfString, String paramString)
  {
    String str = "Transfer.containsInArray: ";
    FFSDebug.log(str + "Starts ...", 6);
    FFSDebug.log(str + "list[] = " + paramArrayOfString, 6);
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
      return false;
    }
    FFSDebug.log(str + "value = ", paramString, 6);
    if (paramString == null) {
      return false;
    }
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if (paramString.equalsIgnoreCase(paramArrayOfString[i]))
      {
        FFSDebug.log(str + "value FOUND", 6);
        FFSDebug.log(str + "End", 6);
        return true;
      }
    }
    FFSDebug.log(str + "value not FOUND", 6);
    FFSDebug.log(str + "End", 6);
    return false;
  }
  
  private static BPWHist a(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.getTransferHistoryCommon : ";
    FFSDebug.log(str1, "start", 6);
    FFSDebug.log(str1, "Param Values", 6);
    FFSDebug.log("StartDate : " + paramBPWHist.getStartDate() + "\nEndDate : " + paramBPWHist.getEndDate(), 6);
    StringBuffer localStringBuffer = new StringBuffer();
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String[] arrayOfString = null;
    ArrayList localArrayList = new ArrayList();
    if (paramString != null) {
      localStringBuffer.append(paramString);
    }
    str2 = paramBPWHist.getTransType();
    if ((str2 != null) && (str2.trim().length() != 0)) {
      if (str2.equalsIgnoreCase("TEMPLATE"))
      {
        localStringBuffer.append(" AND ( (TransferType = 'TEMPLATE') OR (TransferType = 'RECTEMPLATE') ) ");
      }
      else
      {
        localStringBuffer.append(" AND TransferType = ? ");
        localArrayList.add(str2);
      }
    }
    arrayOfString = paramBPWHist.getDest();
    if ((arrayOfString != null) && (arrayOfString.length > 0))
    {
      FFSDebug.log(str1, "dests.length:" + arrayOfString.length, 6);
      if (arrayOfString.length == 1)
      {
        localStringBuffer.append(" AND a.TransferDest = ? ");
        localArrayList.add(arrayOfString[0]);
      }
      else
      {
        localStringBuffer.append(" AND a.TransferDest IN (?");
        localArrayList.add(arrayOfString[0]);
        for (int i = 1; i < arrayOfString.length; i++)
        {
          localStringBuffer.append(", ?");
          localArrayList.add(arrayOfString[i]);
        }
        localStringBuffer.append(")");
      }
    }
    str3 = paramBPWHist.getTemplateId();
    if ((str3 != null) && (str3.trim().length() != 0))
    {
      localStringBuffer.append(" AND SourceTemplateId = ? ");
      localArrayList.add(str3);
    }
    str4 = paramBPWHist.getMinAmount();
    if ((str4 != null) && (str4.trim().length() != 0))
    {
      localStringBuffer.append(" AND cast (Amount as decimal) >= cast (? as decimal) ");
      localArrayList.add(new Integer(str4));
    }
    str5 = paramBPWHist.getMaxAmount();
    if ((str5 != null) && (str5.trim().length() != 0))
    {
      localStringBuffer.append(" AND cast (Amount as decimal) <= cast (? as decimal) ");
      localArrayList.add(new Integer(str5));
    }
    str6 = paramBPWHist.getCustId();
    if ((str6 != null) && (str6.trim().length() != 0))
    {
      localStringBuffer.append(" AND a.CustomerId = ? ");
      localArrayList.add(paramBPWHist.getCustId());
    }
    str7 = paramBPWHist.getCategory();
    if ((str7 != null) && (str7.trim().length() != 0))
    {
      localStringBuffer.append(" AND a.TransferCategory = ? ");
      localArrayList.add(str7);
    }
    if ((paramBPWHist.getHistId() == null) || (paramBPWHist.getHistId().trim().length() == 0))
    {
      if (paramBoolean) {
        jdMethod_if(paramFFSConnectionHolder, paramBPWHist, localStringBuffer.toString(), localArrayList);
      } else {
        a(paramFFSConnectionHolder, paramBPWHist, localStringBuffer.toString(), localArrayList);
      }
      paramBPWHist.setCursorId("0");
    }
    a(paramFFSConnectionHolder, paramBPWHist, paramBoolean);
    return paramBPWHist;
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Transfer.makeHistory: ";
    FFSDebug.log(str1, "start", 6);
    FFSResultSet localFFSResultSet1 = null;
    FFSResultSet localFFSResultSet2 = null;
    try
    {
      int i = 0;
      str2 = null;
      int j = 0;
      String str3 = null;
      String str4 = null;
      String str5 = null;
      String str6 = null;
      String str7 = null;
      Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramBPWHist.getStartDate()));
      Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramBPWHist.getEndDate()));
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int k = localPropertyConfig.getBatchSize();
      str6 = paramBPWHist.getStartDate();
      if (str6.endsWith("00")) {
        str6 = str6.substring(0, 8);
      }
      str7 = paramBPWHist.getEndDate();
      if (str7.endsWith("00")) {
        str7 = str7.substring(0, 8);
      }
      XferSyncProcessor localXferSyncProcessor = new XferSyncProcessor(new XferProcessor());
      str3 = localXferSyncProcessor.makeHistory(paramBPWHist.getCustId(), paramBPWHist.getAcctId(), str6, str7, paramBPWHist.getPageSize(), paramBPWHist);
      FFSDebug.log(str1, "histId:", str3, 6);
      i = TempHist.getCount(paramFFSConnectionHolder, str3);
      StringBuffer localStringBuffer = new StringBuffer("SELECT SrvrTId, DateToPost, SourceRecSrvrTId FROM BPW_Transfer a  WHERE DateToPost >= ? AND DateToPost <= ?");
      String[] arrayOfString = paramBPWHist.getStatusList();
      if ((arrayOfString != null) && (arrayOfString.length > 0))
      {
        localStringBuffer.append(" AND Status IN (?");
        paramArrayList.add(0, arrayOfString[0]);
        FFSDebug.log(str1 + " Status: " + arrayOfString[0]);
        for (int m = 1; m < arrayOfString.length; m++)
        {
          localStringBuffer.append(", ?");
          paramArrayList.add(m, arrayOfString[m]);
          FFSDebug.log(str1 + " Status: " + arrayOfString[m]);
        }
        localStringBuffer.append(")");
      }
      else
      {
        localStringBuffer.append(" AND Status NOT IN (?, ?)");
        paramArrayList.add(0, "CANCELEDON");
        paramArrayList.add(1, "TEMPLATE");
      }
      if (paramString != null) {
        localStringBuffer.append(paramString);
      }
      localStringBuffer.append(" ORDER BY SrvrTId");
      if ((str3 == null) || (str3.trim().length() == 0)) {
        str3 = DBUtil.getNextIndexString("HistID");
      }
      paramBPWHist.setHistId(str3);
      paramArrayList.add(0, localInteger2.toString());
      paramArrayList.add(0, localInteger1.toString());
      Object[] arrayOfObject1 = paramArrayList.toArray();
      for (int n = 0; n < paramArrayList.size(); n++) {
        FFSDebug.log(str1, "Sql Param:" + n + " :" + String.valueOf(paramArrayList.get(n)), 6);
      }
      localFFSResultSet1 = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject1);
      while (localFFSResultSet1.getNextRow())
      {
        str4 = localFFSResultSet1.getColumnString("SourceRecSrvrTId");
        if ((str4 != null) && (str4.trim().length() > 0))
        {
          str5 = getStatus(str4.trim(), true);
          if ("CANCELEDON".equalsIgnoreCase(str5)) {}
        }
        else
        {
          j = Integer.parseInt(localFFSResultSet1.getColumnString("DateToPost"));
          i++;
          str2 = DBUtil.getCursor(j, i);
          TempHist.create(paramFFSConnectionHolder, str3, str2, localFFSResultSet1.getColumnString("SrvrTId"), "TRANSFER", j, null);
          if (i % k == 0) {
            paramFFSConnectionHolder.conn.commit();
          }
        }
      }
      paramFFSConnectionHolder.conn.commit();
      paramArrayList.remove(0);
      paramArrayList.remove(0);
      if ((arrayOfString != null) && (arrayOfString.length > 0)) {
        n = 0;
      }
      while (n < arrayOfString.length)
      {
        paramArrayList.remove(0);
        n++;
        continue;
        paramArrayList.remove(0);
        paramArrayList.remove(0);
      }
      n = 1;
      boolean bool1;
      if ((arrayOfString != null) && (arrayOfString.length > 0)) {
        bool1 = containsInArray(arrayOfString, "WILLPROCESSON");
      }
      if (bool1)
      {
        i1 = DBUtil.getCurrentStartDate();
        int i2 = BPWUtil.getDateDBFormat(paramBPWHist.getEndDate());
        int i3 = (i2 - i1) / 10000;
        if (i3 >= 0)
        {
          localStringBuffer = new StringBuffer("SELECT a.RecSrvrTId, a.Frequency, a.StartDate, a.EndDate,  a.InstanceCount, a.CustomerId  FROM BPW_RecTransfer a  WHERE a.StartDate <= ? AND a.EndDate >= ?");
          localStringBuffer.append(" AND Status ='WILLPROCESSON' ");
          if (paramString != null) {
            localStringBuffer.append(paramString);
          }
          localStringBuffer.append(" ORDER BY RecSrvrTId");
          paramArrayList.add(0, localInteger1);
          paramArrayList.add(0, localInteger2);
          Object[] arrayOfObject2 = paramArrayList.toArray();
          localFFSResultSet2 = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject2);
          ScheduleInfo localScheduleInfo = null;
          String str8 = null;
          String str9 = null;
          String str10 = null;
          while (localFFSResultSet2.getNextRow())
          {
            str8 = localFFSResultSet2.getColumnString("RecSrvrTId");
            str9 = localFFSResultSet2.getColumnString("CustomerId");
            Object localObject1;
            try
            {
              CustomerInfo localCustomerInfo = Customer.getCustomerByID(str9, paramFFSConnectionHolder);
              if (localCustomerInfo == null)
              {
                str12 = BPWLocaleUtil.getMessage(19130, new String[] { str9 }, "TRANSFER_MESSAGE");
                paramBPWHist.setStatusCode(19130);
                paramBPWHist.setStatusMsg(str12);
                FFSDebug.log(str1 + "failed, ", str12, 0);
                return;
              }
              str10 = localCustomerInfo.fIID;
            }
            catch (Throwable localThrowable2)
            {
              String str12 = "*** " + str1 + " failed: ";
              localObject1 = FFSDebug.stackTrace(localThrowable2);
              FFSDebug.log(str12, (String)localObject1, 0);
              throw new FFSException(localThrowable2, str12);
            }
            localScheduleInfo = ScheduleInfo.getScheduleInfo(str10, "RECETFTRN", str8, paramFFSConnectionHolder);
            if (localScheduleInfo != null)
            {
              String str11 = localFFSResultSet2.getColumnString("StartDate");
              int i4 = Integer.parseInt(str11);
              localScheduleInfo.NextInstanceDate = ScheduleInfo.computeFutureDate(i4, localScheduleInfo.Frequency, localScheduleInfo.CurInstanceNum - 1);
              localScheduleInfo.InstanceCount -= localScheduleInfo.CurInstanceNum - 1;
              localScheduleInfo.CurInstanceNum = 1;
              localObject1 = null;
              localObject1 = ScheduleInfo.getPendingDatesByStartAndEndDate(localScheduleInfo, localInteger1.intValue(), localInteger2.intValue());
              for (int i5 = 0; i5 < localObject1.length; i5++)
              {
                j = Integer.parseInt(localObject1[i5].substring(0, 10));
                i++;
                str2 = DBUtil.getCursor(j, i);
                TempHist.create(paramFFSConnectionHolder, str3, str2, str8, "RECTRANSFER", j, null);
                if (i % k == 0) {
                  paramFFSConnectionHolder.conn.commit();
                }
              }
            }
          }
        }
        paramArrayList.remove(0);
        paramArrayList.remove(0);
      }
      paramBPWHist.setTotalTrans(i);
      paramFFSConnectionHolder.conn.commit();
      int i1 = 0;
      boolean bool2;
      if ((arrayOfString != null) && (arrayOfString.length > 0)) {
        bool2 = containsInArray(arrayOfString, "RECTEMPLATE");
      }
      if (bool2) {
        jdMethod_do(paramFFSConnectionHolder, paramBPWHist, paramString, paramArrayList);
      }
      FFSDebug.log(str1, "end", 6);
    }
    catch (Throwable localThrowable1)
    {
      String str2 = str1 + "failed : ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable1), 0);
      throw new FFSException(localThrowable1, str2);
    }
    finally
    {
      if (localFFSResultSet1 != null) {
        try
        {
          localFFSResultSet1.close();
          localFFSResultSet1 = null;
        }
        catch (Exception localException1)
        {
          FFSDebug.log(str1, "failed to close ResultSet " + FFSDebug.stackTrace(localException1), 0);
        }
      }
      if (localFFSResultSet2 != null) {
        try
        {
          localFFSResultSet2.close();
          localFFSResultSet2 = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log(str1, "failed to close ResultSet " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
  }
  
  private static void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Transfer.makeRecTransferHistory: ";
    FFSDebug.log(str1, "start", 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = 0;
      int j = 0;
      long l = paramBPWHist.getPageSize();
      String str3 = null;
      int k = 0;
      StringBuffer localStringBuffer = new StringBuffer("SELECT RecSrvrTId, StartDate FROM BPW_RecTransfer a  WHERE StartDate >= ? AND StartDate <= ?");
      String[] arrayOfString = paramBPWHist.getStatusList();
      if ((arrayOfString != null) && (arrayOfString.length > 0))
      {
        localStringBuffer.append(" AND Status IN (?");
        paramArrayList.add(0, arrayOfString[0]);
        for (int m = 1; m < arrayOfString.length; m++)
        {
          localStringBuffer.append(", ?");
          paramArrayList.add(m, arrayOfString[m]);
        }
        localStringBuffer.append(")");
      }
      else
      {
        localStringBuffer.append(" AND Status NOT IN (?, ?)");
        paramArrayList.add(0, "CANCELEDON");
        paramArrayList.add(1, "RECTEMPLATE");
      }
      if ((paramString != null) && (paramString.length() > 0)) {
        localStringBuffer.append(paramString);
      }
      localStringBuffer.append(" ORDER BY RecSrvrTId");
      paramBPWHist.setHistId(DBUtil.getNextIndexString("HistID"));
      Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramBPWHist.getStartDate()));
      Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramBPWHist.getEndDate()));
      paramArrayList.add(0, localInteger2);
      paramArrayList.add(0, localInteger1);
      Object[] arrayOfObject = paramArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        k = Integer.parseInt(localFFSResultSet.getColumnString("StartDate"));
        i++;
        j++;
        str3 = DBUtil.getCursor(k, j);
        TempHist.create(paramFFSConnectionHolder, paramBPWHist.getHistId(), str3, localFFSResultSet.getColumnString("RecSrvrTId"), "RECTRANSFER", k, null);
        if ((l != 0L) && (i == l))
        {
          i = 0;
          paramFFSConnectionHolder.conn.commit();
        }
      }
      paramBPWHist.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1, "end", 6);
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed : ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1, "failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  private static void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Transfer.makeRecTemplateHistory: ";
    FFSDebug.log(str1, "start", 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = 0;
      int j = (int)paramBPWHist.getTotalTrans();
      long l = paramBPWHist.getPageSize();
      String str3 = null;
      int k = 0;
      StringBuffer localStringBuffer = new StringBuffer("SELECT RecSrvrTId, StartDate FROM BPW_RecTransfer a  WHERE StartDate >= ? AND StartDate <= ?");
      localStringBuffer.append(" AND Status = '");
      localStringBuffer.append("RECTEMPLATE").append("' ");
      if ((paramString != null) && (paramString.length() > 0)) {
        localStringBuffer.append(paramString);
      }
      localStringBuffer.append(" ORDER BY RecSrvrTId");
      Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramBPWHist.getStartDate()));
      Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramBPWHist.getEndDate()));
      paramArrayList.add(0, localInteger2);
      paramArrayList.add(0, localInteger1);
      for (int m = 0; m < paramArrayList.size(); m++) {
        FFSDebug.log(str1, "Sql Param:" + m + " :" + String.valueOf(paramArrayList.get(m)), 6);
      }
      Object[] arrayOfObject = paramArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        k = Integer.parseInt(localFFSResultSet.getColumnString("StartDate"));
        i++;
        j++;
        str3 = DBUtil.getCursor(k, j);
        TempHist.create(paramFFSConnectionHolder, paramBPWHist.getHistId(), str3, localFFSResultSet.getColumnString("RecSrvrTId"), "RECTRANSFER", k, null);
        if ((l != 0L) && (i == l))
        {
          i = 0;
          paramFFSConnectionHolder.conn.commit();
        }
      }
      paramBPWHist.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1, "end", 6);
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed : ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1, "failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.getHistory: ";
    FFSDebug.log(str1, "start", 6);
    FFSDebug.log(str1, "Parameter values \nHistId : " + paramBPWHist.getHistId() + "\nCursorId : " + paramBPWHist.getCursorId() + "\nPage Size : " + paramBPWHist.getPageSize(), 6);
    ArrayList localArrayList = new ArrayList();
    Object localObject1 = null;
    try
    {
      TempHist[] arrayOfTempHist = TempHist.get(paramFFSConnectionHolder, paramBPWHist.getHistId(), paramBPWHist.getCursorId(), paramBPWHist.getPageSize());
      int i = arrayOfTempHist.length;
      for (int j = 0; j < i; j++)
      {
        FFSDebug.log("Transfer.getHistory : RecordType=", arrayOfTempHist[j].RecordType, 6);
        if (arrayOfTempHist[j].RecordType.equals("TRANSFER"))
        {
          localObject1 = new TransferInfo();
          ((TransferInfo)localObject1).setSrvrTId(arrayOfTempHist[j].RecordExtId);
          localObject1 = getTransferInfo(paramFFSConnectionHolder, (TransferInfo)localObject1, false, false);
          if (((TransferInfo)localObject1).getTransferType().equals("Recurring")) {
            ((TransferInfo)localObject1).setDateToPost(BPWUtil.getDateBeanFormat(arrayOfTempHist[j].DueDate));
          }
          if ((((TransferInfo)localObject1).getTransferDest() == null) || (((TransferInfo)localObject1).getTransferDest().trim().length() == 0)) {
            ((TransferInfo)localObject1).setTransferDest("ITOE");
          }
          ((TransferInfo)localObject1).setRecordCursor(Long.parseLong(arrayOfTempHist[j].CursorId));
        }
        else if (arrayOfTempHist[j].RecordType.equals("RECTRANSFER"))
        {
          localObject1 = new RecTransferInfo();
          ((TransferInfo)localObject1).setSrvrTId(arrayOfTempHist[j].RecordExtId);
          localObject1 = getTransferInfo(paramFFSConnectionHolder, (TransferInfo)localObject1, true, false);
          ((TransferInfo)localObject1).setSourceRecSrvrTId(arrayOfTempHist[j].RecordExtId);
          if ((((TransferInfo)localObject1).getTransferDest() == null) || (((TransferInfo)localObject1).getTransferDest().trim().length() == 0)) {
            ((TransferInfo)localObject1).setTransferDest("ITOE");
          }
          ((TransferInfo)localObject1).setTransferType("Recurring");
          ((TransferInfo)localObject1).setDateDue(BPWUtil.getDateBeanFormat(arrayOfTempHist[j].DueDate));
          try
          {
            getValidTransferDueDate(paramFFSConnectionHolder, (TransferInfo)localObject1);
          }
          catch (Exception localException)
          {
            ((TransferInfo)localObject1).setDateToPost(((TransferInfo)localObject1).getDateDue());
            ((TransferInfo)localObject1).setProcessDate(((TransferInfo)localObject1).getDateDue());
          }
          ((TransferInfo)localObject1).setRecordCursor(Long.parseLong(arrayOfTempHist[j].CursorId));
        }
        else
        {
          String str3;
          Object localObject2;
          if (arrayOfTempHist[j].RecordType.equals("INTRA"))
          {
            str3 = arrayOfTempHist[j].RecordExtId;
            localObject2 = XferInstruction.getXferInstruction(paramFFSConnectionHolder, str3);
            if (localObject2 != null)
            {
              localObject1 = new TransferInfo(str3, ((XferInstruction)localObject2).FIID, ((XferInstruction)localObject2).CustomerID, ((XferInstruction)localObject2).BankID, ((XferInstruction)localObject2).BranchID, ((XferInstruction)localObject2).AcctDebitID, ((XferInstruction)localObject2).AcctDebitType, ((XferInstruction)localObject2).AcctCreditID, ((XferInstruction)localObject2).AcctCreditType, ((XferInstruction)localObject2).Amount, ((XferInstruction)localObject2).CurDef, String.valueOf(((XferInstruction)localObject2).ToAmount), ((XferInstruction)localObject2).ToAmtCurrency, ((XferInstruction)localObject2).userAssignedAmount, false, ((XferInstruction)localObject2).DateToPost, ((XferInstruction)localObject2).DateToPost, ((XferInstruction)localObject2).RecSrvrTID, ((XferInstruction)localObject2).LogID, ((XferInstruction)localObject2).Status, "Current", ((XferInstruction)localObject2).SubmittedBy, "INTERNAL");
              if (((XferInstruction)localObject2).extraFields != null)
              {
                ((TransferInfo)localObject1).setMemo(buildMemoFromXtraInfo((HashMap)((XferInstruction)localObject2).extraFields));
                ((TransferInfo)localObject1).extraFields = ((HashMap)((XferInstruction)localObject2).extraFields);
              }
              ((TransferInfo)localObject1).setDateCreate(((XferInstruction)localObject2).DateCreate);
            }
          }
          else if (arrayOfTempHist[j].RecordType.equals("RECINTRA"))
          {
            str3 = arrayOfTempHist[j].RecordExtId;
            localObject2 = RecXferInstruction.getRecXferInstruction(paramFFSConnectionHolder, str3);
            if (localObject2 != null)
            {
              localObject1 = new RecTransferInfo(str3, ((RecXferInstruction)localObject2).FIID, ((RecXferInstruction)localObject2).CustomerID, ((RecXferInstruction)localObject2).BankID, ((RecXferInstruction)localObject2).BranchID, ((RecXferInstruction)localObject2).AcctDebitID, ((RecXferInstruction)localObject2).AcctDebitType, ((RecXferInstruction)localObject2).AcctCreditID, ((RecXferInstruction)localObject2).AcctCreditType, String.valueOf(((RecXferInstruction)localObject2).Amount), ((RecXferInstruction)localObject2).CurDef, String.valueOf(((RecXferInstruction)localObject2).ToAmount), ((RecXferInstruction)localObject2).ToAmtCurrency, ((RecXferInstruction)localObject2).userAssignedAmount, false, BPWUtil.getDateBeanFormat(arrayOfTempHist[j].DueDate), BPWUtil.getDateBeanFormat(arrayOfTempHist[j].DueDate), str3, ((RecXferInstruction)localObject2).LogID, "WILLPROCESSON", "Recurring", ((RecXferInstruction)localObject2).SubmittedBy, "INTERNAL");
              if (((RecXferInstruction)localObject2).extraFields != null) {
                ((TransferInfo)localObject1).extraFields = ((HashMap)((RecXferInstruction)localObject2).extraFields);
              }
            }
            ((TransferInfo)localObject1).setDateCreate(((RecXferInstruction)localObject2).DateCreate);
          }
          else
          {
            localObject1 = new TransferInfo();
            ((TransferInfo)localObject1).setStatusCode(23190);
            ((TransferInfo)localObject1).setStatusMsg("Invalid History Record Type");
            FFSDebug.log("Transfer.getHistory : Invalid History Record Type : RecordExtId = " + arrayOfTempHist[j].RecordExtId + "RecordType = " + arrayOfTempHist[j].RecordType, 0);
          }
        }
        if (localObject1 != null) {
          localArrayList.add(localObject1);
        }
      }
      if (paramBoolean) {
        paramBPWHist.setTrans(localArrayList.toArray(new RecTransferInfo[0]));
      } else {
        paramBPWHist.setTrans(localArrayList.toArray(new TransferInfo[0]));
      }
      if (paramBPWHist.getTotalTrans() == 0L) {
        paramBPWHist.setTotalTrans(TempHist.getCount(paramFFSConnectionHolder, paramBPWHist.getHistId()));
      }
      if (localArrayList.size() == 0)
      {
        FFSDebug.log(str1, "No history record found", 6);
        paramBPWHist.setStatusCode(16020);
        paramBPWHist.setStatusMsg("Transfer History  record not found");
      }
      else
      {
        paramBPWHist.setCursorId(arrayOfTempHist[(arrayOfTempHist.length - 1)].CursorId);
        paramBPWHist.setStatusCode(0);
        paramBPWHist.setStatusMsg("Success");
      }
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed:  " + localThrowable.getMessage();
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public static TransferInfo updateStatus(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.updateStatus";
    FFSDebug.log(str1, "start", 6);
    String str2 = null;
    String str3 = paramTransferInfo.getSrvrTId();
    if (paramFFSConnectionHolder == null)
    {
      paramTransferInfo.setStatusCode(16000);
      localObject = "FFSConnectionHolder object  is null";
      paramTransferInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      return paramTransferInfo;
    }
    Object localObject = { paramTransferInfo.getPrcStatus(), str3 };
    if (paramBoolean) {
      str2 = "UPDATE BPW_RecTransfer SET Status=? WHERE RecSrvrTId=?";
    } else {
      str2 = "UPDATE BPW_Transfer SET Status=? WHERE SrvrTId=?";
    }
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** Transfer.updateStatus failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4 + str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
    FFSDebug.log(str1, "done", 6);
    return paramTransferInfo;
  }
  
  public static TransferInfo updateStatusFromAdapter(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException
  {
    String str1 = "Transfer.updateStatusFromAdapter";
    FFSDebug.log(str1, "start", 6);
    String str2 = null;
    String str3 = paramTransferInfo.getSrvrTId();
    if (paramFFSConnectionHolder == null)
    {
      paramTransferInfo.setStatusCode(16000);
      localObject = "FFSConnectionHolder object  is null";
      paramTransferInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      return paramTransferInfo;
    }
    Object localObject = { paramTransferInfo.getPrcStatus(), paramTransferInfo.getDatePosted(), paramTransferInfo.getConfirmNum(), paramTransferInfo.getConfirmMsg(), paramTransferInfo.getLastProcessId(), str3 };
    str2 = "UPDATE BPW_Transfer SET Status=?, DatePosted=?, ConfirmNum = ?, ConfirmMsg = ?,  LastProcessId = ? WHERE SrvrTId=?";
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4 + str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
    FFSDebug.log(str1, "done", 6);
    return paramTransferInfo;
  }
  
  public static TransferInfo updateStatusAndAmountFromAdapter(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException
  {
    FFSDebug.log("Transfer.updateStatusAndToAmountFromAdapter", " start", 6);
    if (paramFFSConnectionHolder == null)
    {
      paramTransferInfo.setStatusCode(16000);
      str1 = "FFSConnectionHolder object  is null";
      paramTransferInfo.setStatusMsg(str1);
      FFSDebug.log("Transfer.updateStatusAndToAmountFromAdapter", str1, 0);
      return paramTransferInfo;
    }
    String str1 = "UPDATE BPW_Transfer SET Status=?, DatePosted=?, ConfirmNum=?, ConfirmMsg=?, LastProcessId=?, Amount=?, ToAmount=? WHERE SrvrTId=?";
    Object[] arrayOfObject = { paramTransferInfo.getPrcStatus(), paramTransferInfo.getDatePosted(), paramTransferInfo.getConfirmNum(), paramTransferInfo.getConfirmMsg(), paramTransferInfo.getLastProcessId(), paramTransferInfo.getAmount(), paramTransferInfo.getToAmount(), paramTransferInfo.getSrvrTId() };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** Transfer.updateStatusAndToAmountFromAdapter failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
    FFSDebug.log("Transfer.updateStatusAndToAmountFromAdapter", " done", 6);
    return paramTransferInfo;
  }
  
  public static String buildMemoFromXtraInfo(HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    String str1 = null;
    Iterator localIterator = null;
    String str2 = null;
    if (paramHashMap == null) {
      return null;
    }
    localStringBuffer = new StringBuffer();
    localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      str2 = (String)localIterator.next();
      localStringBuffer.append(str2).append("=");
      localStringBuffer.append((String)paramHashMap.get(str2));
      localStringBuffer.append(",");
    }
    str1 = localStringBuffer.toString();
    str1 = str1.substring(0, str1.length() - 1);
    return str1;
  }
  
  public static String[] getActiveTransfersByCustomer(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.getActiveTransfersByCustomer";
    FFSDebug.log(str1, ": start, custId=", paramString, 6);
    String str2 = null;
    Object[] arrayOfObject = { paramString, "CANCELEDON", "POSTEDON" };
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    if (paramBoolean) {
      str2 = "SELECT RecSrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, StartDate, EndDate, Frequency, InstanceCount, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, BankFromRtnType, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_RecTransfer  WHERE CustomerId = ?  AND Status NOT IN (?, ?) ";
    } else {
      str2 = "SELECT SrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, DateDue, DateToPost, DatePosted, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, SourceRecSrvrTId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, ProcessDate, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, Action, FundsRetry, BankFromRtnType, ProcessNumber, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_Transfer  WHERE CustomerId = ?  AND Status NOT IN (?, ?) ";
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        String str3 = localFFSResultSet.getColumnString(1);
        localArrayList.add(str3);
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localThrowable, str4);
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
        String str6 = str1 + " failed: ";
        String str7 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str6, str7, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  public static String[] getActiveTransfersByExtAcctId(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.getActiveTransfersByExtAcctId";
    FFSDebug.log(str1, ": start, extAcctId=", paramString, 6);
    String str2 = null;
    Object[] arrayOfObject = { paramString, "CANCELEDON", "POSTEDON" };
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    if (paramBoolean) {
      str2 = "SELECT RecSrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, StartDate, EndDate, Frequency, InstanceCount, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, BankFromRtnType, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_RecTransfer  WHERE ExternalAcctId = ?  AND Status NOT IN (?, ?) ";
    } else {
      str2 = "SELECT SrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, DateDue, DateToPost, DatePosted, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, SourceRecSrvrTId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, ProcessDate, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, Action, FundsRetry, BankFromRtnType, ProcessNumber, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_Transfer  WHERE ExternalAcctId = ?  AND Status NOT IN (?, ?) ";
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        String str3 = localFFSResultSet.getColumnString(1);
        localArrayList.add(str3);
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localThrowable, str4);
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
        String str6 = str1 + " failed: ";
        String str7 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str6, str7, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  public static int cleanup(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt1, int paramInt2)
    throws FFSException
  {
    String str1 = "Transfer.cleanup ";
    FFSDebug.log(str1 + " start...", 6);
    int i = 0;
    int j = 1;
    if ((paramString != null) && (paramString.trim().length() != 0)) {
      j = 0;
    }
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt1);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd00");
    String str2 = localSimpleDateFormat.format(localCalendar.getTime());
    int k = Integer.parseInt(str2);
    FFSResultSet localFFSResultSet = null;
    Object localObject1 = null;
    int m = 1;
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    int n = Purge.getPageSize();
    String str3 = null;
    try
    {
      while (m <= 2)
      {
        if (j != 0) {
          localFFSResultSet = jdMethod_if(paramFFSConnectionHolder, k, paramInt2);
        } else {
          localFFSResultSet = a(paramFFSConnectionHolder, k, paramString, paramInt2);
        }
        try
        {
          for (;;)
          {
            if (localFFSResultSet.getNextRow()) {
              try
              {
                localArrayList1.add(localFFSResultSet.getColumnString(1));
                if (paramInt2 != 1)
                {
                  str3 = localFFSResultSet.getColumnString(2);
                  if (str3 != null) {
                    localArrayList2.add(str3);
                  }
                  str3 = localFFSResultSet.getColumnString(3);
                  if (str3 != null) {
                    localArrayList2.add(str3);
                  }
                }
                if (localArrayList1.size() >= n)
                {
                  i += a(paramFFSConnectionHolder, localArrayList1, localArrayList2, paramInt2);
                  localArrayList1.clear();
                  localArrayList2.clear();
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
            if (localArrayList1.size() > 0) {
              a(paramFFSConnectionHolder, localArrayList1, localArrayList2, paramInt2);
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
          FFSDebug.log(str1 + " Attempting retry # " + ++m);
        }
      }
      FFSDebug.log("----> Done deleteing External Transfers", 3);
      if (localObject1 != null) {
        throw localObject1;
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException4)
    {
      String str4 = "***  " + str1 + ": failed:";
      String str5 = FFSDebug.stackTrace(localException4);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException4, str4);
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
  
  private static int a(FFSConnectionHolder paramFFSConnectionHolder, ArrayList paramArrayList1, ArrayList paramArrayList2, int paramInt)
    throws FFSException
  {
    int i = 0;
    String str = "ETFTRN";
    boolean bool = false;
    if (paramInt == 2)
    {
      str = "RECETFTRN";
      bool = true;
    }
    String[] arrayOfString = (String[])paramArrayList1.toArray(new String[0]);
    EventInfoLog.deleteBatchByInstructionIdsAndType(paramFFSConnectionHolder, arrayOfString, str);
    BPWExtraInfo.deleteBatch(paramFFSConnectionHolder, arrayOfString);
    if (paramInt != 1) {
      ExternalTransferAccount.deleteExternalAccounts(paramFFSConnectionHolder, (String[])paramArrayList2.toArray(new String[0]));
    }
    i += deleteBatch(paramFFSConnectionHolder, arrayOfString, bool);
    paramFFSConnectionHolder.conn.commit();
    FFSDebug.log("----> Committed External Transfer Delete " + i, 3);
    return i;
  }
  
  private static FFSResultSet a(FFSConnectionHolder paramFFSConnectionHolder, int paramInt1, String paramString, int paramInt2)
    throws FFSException
  {
    String str1 = "Transfer.getOldSrvrTIDByCustId: ";
    ArrayList localArrayList = new ArrayList();
    Object localObject = null;
    FFSDebug.log(str1, "start ageDay=" + paramInt1, "custId=" + paramString, 6);
    String str2 = "SELECT SrvrTId, AccountFromId, ExternalAcctId FROM BPW_Transfer WHERE DateToPost <= ?  AND CustomerId = ? AND SourceRecSrvrTId is NULL AND Status IN ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    localObject = new Integer(paramInt1).toString();
    if (paramInt2 == 2)
    {
      str2 = "SELECT RecSrvrTId, AccountFromId, ExternalAcctId FROM BPW_RecTransfer WHERE StartDate <= ? AND CustomerId = ? AND Status IN ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) AND (SELECT DISTINCT SourceRecSrvrTId FROM BPW_Transfer WHERE SourceRecSrvrTId = BPW_RecTransfer.RecSrvrTId) IS NULL";
      localObject = new Integer(paramInt1);
    }
    else if (paramInt2 == 1)
    {
      str2 = "SELECT SrvrTId from BPW_Transfer WHERE DateToPost <= ? AND CustomerId = ? AND SourceRecSrvrTId is not NULL AND Status IN ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }
    Object[] arrayOfObject = { localObject, paramString, "NOFUNDSON_NOTIF", "NOFUNDSON", "NOFUNDS", "FAILEDON_NOTIF", "FAILEDON", "FUNDSFAILEDON_NOTIF", "FUNDSFAILEDON", "FUNDSFAILED", "FUNDSREVERTED_NOTIF", "FUNDSREVERTED", "FUNDSREVERTFAILED_NOTIF", "FUNDSREVERTFAILED", "PROCESSEDON", "CANCELEDON", "POSTEDON", "BACKENDFAILED", "BACKENDFAILED_NOTIF", "BACKENDREJECTED", "BACKENDREJECTED_NOTIF" };
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
    FFSDebug.log(str1, " rows=" + localArrayList.size(), 6);
    return localFFSResultSet;
  }
  
  private static FFSResultSet jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, int paramInt1, int paramInt2)
    throws FFSException
  {
    String str1 = "Transfer.getOldSrvrTID: ";
    Object localObject = null;
    FFSDebug.log(str1, "start ageDay=" + paramInt1, 6);
    String str2 = "SELECT SrvrTId, AccountFromId, ExternalAcctId FROM BPW_Transfer WHERE DateToPost <= ?  AND SourceRecSrvrTId is NULL and Status IN ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    localObject = new Integer(paramInt1).toString();
    if (paramInt2 == 2)
    {
      str2 = "SELECT RecSrvrTId, AccountFromId, ExternalAcctId FROM BPW_RecTransfer WHERE StartDate <= ? AND Status IN ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) AND (SELECT DISTINCT SourceRecSrvrTId FROM BPW_Transfer WHERE SourceRecSrvrTId = BPW_RecTransfer.RecSrvrTId) IS NULL";
      localObject = new Integer(paramInt1);
    }
    else if (paramInt2 == 1)
    {
      str2 = "SELECT SrvrTId from BPW_Transfer WHERE DateToPost <= ? AND SourceRecSrvrTId is not NULL AND Status IN ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }
    Object[] arrayOfObject = { localObject, "NOFUNDSON_NOTIF", "NOFUNDSON", "NOFUNDS", "FAILEDON_NOTIF", "FAILEDON", "FUNDSFAILEDON_NOTIF", "FUNDSFAILEDON", "FUNDSFAILED", "FUNDSREVERTED_NOTIF", "FUNDSREVERTED", "FUNDSREVERTFAILED_NOTIF", "FUNDSREVERTFAILED", "PROCESSEDON", "CANCELEDON", "POSTEDON", "BACKENDFAILED", "BACKENDFAILED_NOTIF", "BACKENDREJECTED", "BACKENDREJECTED_NOTIF" };
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
  
  public static int delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("Transfer.deleteBatch start.", 6);
    String str1 = "Delete From BPW_Transfer WHERE SrvrTId = ?";
    if (paramBoolean) {
      str1 = "Delete From BPW_RecTransfer WHERE RecSrvrTId = ?";
    }
    Object[] arrayOfObject = { paramString };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** Transfer.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("Transfer.delete done. Rows: " + i, 6);
    return i;
  }
  
  public static int deleteBatch(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("Transfer.deleteBatch start.", 6);
    String str1 = "Delete From BPW_Transfer WHERE SrvrTId = ?";
    if (paramBoolean) {
      str1 = "Delete From BPW_RecTransfer WHERE RecSrvrTId = ?";
    }
    try
    {
      DBUtil.executeStatementBatch(paramFFSConnectionHolder, str1, DBUtil.arrayStringToArrayList(paramArrayOfString));
    }
    catch (Exception localException)
    {
      String str2 = "*** Transfer.deleteBatch failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("Transfer.deleteBatch done. Rows: " + paramArrayOfString.length, 6);
    return paramArrayOfString.length;
  }
  
  public static TransferInfo[] getUnprocessedTransfersForAcct(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    String str1 = "Transfer.getUnprocessedTransfersForAcct: ";
    FFSDebug.log(str1 + "start. ProcessTime = " + paramString3 + ". acctId = " + paramString1, 6);
    String str2 = "SELECT transfer.SrvrTId, transfer.CustomerId, transfer.TransferType, transfer.TransferDest, transfer.TransferGroup, transfer.TransferCategory, transfer.BankFromRtn, transfer.AccountFromNum, transfer.AccountFromType, transfer.ExternalAcctId, transfer.Amount, transfer.AmountCurrency, transfer.OrigAmount, transfer.OrigCurrency, transfer.DateCreate, transfer.DateDue, transfer.DateToPost, transfer.DatePosted, transfer.LastProcessId, transfer.Memo, transfer.TemplateScope, transfer.TemplateNickName, transfer.SourceTemplateId, transfer.SourceRecSrvrTId, transfer.Status, transfer.SubmittedBy, transfer.LogId , transfer.ProcessLeadNumber, transfer.FIId, transfer.AccountFromId, transfer.ProcessDate, transfer.OriginatingUserId, transfer.ConfirmMsg, transfer.ConfirmNum, transfer.FundsProcessing, transfer.ProcessType, transfer.TypeDetail, transfer.LastChangeDate, transfer.Action, transfer.FundsRetry, transfer.BankFromRtnType, transfer.ProcessNumber, transfer.ToAmount, transfer.ToAmountCurrency, transfer.UserAssignedAmount FROM BPW_Transfer transfer  WHERE  transfer.ExternalAcctId = ? AND transfer.Status = 'WILLPROCESSON' AND transfer.DateToPost <= ? ";
    Object[] arrayOfObject = { paramString1, paramString3 };
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        TransferInfo localTransferInfo = new TransferInfo();
        a(localTransferInfo, localFFSResultSet, false);
        jdMethod_do(localTransferInfo, paramFFSConnectionHolder, str1);
        localArrayList.add(localTransferInfo);
        i++;
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
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
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1 + "done. Count = " + i, 6);
    return (TransferInfo[])localArrayList.toArray(new TransferInfo[0]);
  }
  
  public static TransferInfo[] getProcessedTransfersForAccount(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "Transfer.getProcessedTransfersForAccount: ";
    FFSDebug.log(str1 + "start. AccountId = " + paramString1 + ". ProcessId = " + paramString2, 6);
    String str2 = "SELECT transfer.SrvrTId, transfer.CustomerId, transfer.TransferType, transfer.TransferDest, transfer.TransferGroup, transfer.TransferCategory, transfer.BankFromRtn, transfer.AccountFromNum, transfer.AccountFromType, transfer.ExternalAcctId, transfer.Amount, transfer.AmountCurrency, transfer.OrigAmount, transfer.OrigCurrency, transfer.DateCreate, transfer.DateDue, transfer.DateToPost, transfer.DatePosted, transfer.LastProcessId, transfer.Memo, transfer.TemplateScope, transfer.TemplateNickName, transfer.SourceTemplateId, transfer.SourceRecSrvrTId, transfer.Status, transfer.SubmittedBy, transfer.LogId , transfer.ProcessLeadNumber, transfer.FIId, transfer.AccountFromId, transfer.ProcessDate, transfer.OriginatingUserId, transfer.ConfirmMsg, transfer.ConfirmNum, transfer.FundsProcessing, transfer.ProcessType, transfer.TypeDetail, transfer.LastChangeDate, transfer.Action, transfer.FundsRetry, transfer.BankFromRtnType, transfer.ProcessNumber, transfer.ToAmount, transfer.ToAmountCurrency, transfer.UserAssignedAmount FROM BPW_Transfer transfer  WHERE transfer.Status = 'POSTEDON' AND transfer.ExternalAcctId = ? AND transfer.LastProcessId = ?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        TransferInfo localTransferInfo = new TransferInfo();
        a(localTransferInfo, localFFSResultSet, false);
        jdMethod_do(localTransferInfo, paramFFSConnectionHolder, str1);
        localArrayList.add(localTransferInfo);
        i++;
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
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
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1 + "done. Count = " + i, 6);
    return (TransferInfo[])localArrayList.toArray(new TransferInfo[0]);
  }
  
  public static TransferInfo getLastestPrenoteTransferByExtAcctIdAndLastProcessId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "Transfer.getLastestPrenoteTransferByExtAcctIdAndLastProcessId: ";
    FFSDebug.log(str1 + "start. ExtAcctId = " + paramString1, 6);
    String str2 = "SELECT SrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, DateDue, DateToPost, DatePosted, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, SourceRecSrvrTId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, ProcessDate, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, Action, FundsRetry, BankFromRtnType, ProcessNumber, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_Transfer  WHERE ExternalAcctId = ? AND LastProcessId = ? AND TransferCategory = 'PRENOTE_ENTRY' AND Status = 'POSTEDON' ORDER BY DateCreate DESC";
    Object[] arrayOfObject = { paramString1, paramString2 };
    FFSResultSet localFFSResultSet = null;
    TransferInfo localTransferInfo = new TransferInfo();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        a(localTransferInfo, localFFSResultSet, false);
        jdMethod_do(localTransferInfo, paramFFSConnectionHolder, str1);
      }
      else
      {
        localTransferInfo.setStatusCode(16020);
        localTransferInfo.setStatusMsg(" record not found for prenote of external account " + paramString1);
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
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
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1 + "done", 6);
    return localTransferInfo;
  }
  
  public static ETFACHEntryInfo addETFACHEntry(FFSConnectionHolder paramFFSConnectionHolder, ETFACHEntryInfo paramETFACHEntryInfo)
    throws FFSException
  {
    String str1 = "Transfer.addETFACHEntry";
    String str2 = null;
    Object[] arrayOfObject = null;
    String str3 = null;
    FFSDebug.log(str1 + ": Starts", 6);
    if (paramFFSConnectionHolder == null)
    {
      paramETFACHEntryInfo.setStatusCode(16000);
      String str4 = "FFSConnectionHolder object  is null";
      paramETFACHEntryInfo.setStatusMsg(str4);
      FFSDebug.log(str1, str4, 0);
      return paramETFACHEntryInfo;
    }
    try
    {
      str2 = "INSERT INTO ETF_ACHEntry (EntryId, BatchId, TransactionCode, RecvDFIIdentification, DFIAccountNumber, RcvCompIndvName, IdentificationNumber, Amount, TraceNumber, LogId) VALUES (?,?,?,?,?,?,?,?,?,?)";
      str3 = DBUtil.getNewTransId("RecordID", 32);
      paramETFACHEntryInfo.setEntryId(str3);
      arrayOfObject = a(paramETFACHEntryInfo);
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      paramETFACHEntryInfo.setStatusCode(0);
      paramETFACHEntryInfo.setStatusMsg("Success");
    }
    catch (Throwable localThrowable)
    {
      String str5 = str1 + " failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5, str6, 0);
      throw new FFSException(localThrowable, str5);
    }
    return paramETFACHEntryInfo;
  }
  
  private static Object[] a(ETFACHEntryInfo paramETFACHEntryInfo)
    throws FFSException
  {
    String str = "Transfer.getInsertSQLParametersForETFACHEntry: ";
    FFSDebug.log("*****", str, "starts", 6);
    Object[] arrayOfObject = { paramETFACHEntryInfo.getEntryId(), paramETFACHEntryInfo.getBatchId(), paramETFACHEntryInfo.getTransactionCode(), paramETFACHEntryInfo.getRecvDFIIdentification(), paramETFACHEntryInfo.getDFIAccountNumber(), paramETFACHEntryInfo.getRcvCompIndvName(), paramETFACHEntryInfo.getIdentificationNumber(), paramETFACHEntryInfo.getAmount(), paramETFACHEntryInfo.getTraceNumber(), paramETFACHEntryInfo.getLogId() };
    return arrayOfObject;
  }
  
  public static ETFACHBatchInfo addETFACHBatch(FFSConnectionHolder paramFFSConnectionHolder, ETFACHBatchInfo paramETFACHBatchInfo)
    throws FFSException
  {
    String str1 = "Transfer.addETFACHBatch";
    String str2 = null;
    Object[] arrayOfObject = null;
    String str3 = null;
    FFSDebug.log(str1 + ": Starts", 6);
    if (paramFFSConnectionHolder == null)
    {
      paramETFACHBatchInfo.setStatusCode(16000);
      String str4 = "FFSConnectionHolder object  is null";
      paramETFACHBatchInfo.setStatusMsg(str4);
      FFSDebug.log(str1, str4, 0);
      return paramETFACHBatchInfo;
    }
    try
    {
      str2 = "INSERT INTO ETF_ACHBatch (BatchId, FileId, CompanyName, CompanyIdentification, StdEntryClassCode, EffectiveEntryDate, EntryAddendaCount, TotalDebits, NumberOfDebits, TotalCredits, NumberOfCredits, BatchNumber, CustomerId, CustomerIdInt) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      str3 = DBUtil.getNewTransId("BatchID", 32);
      paramETFACHBatchInfo.setBatchId(str3);
      arrayOfObject = a(paramETFACHBatchInfo);
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      paramETFACHBatchInfo.setStatusCode(0);
      paramETFACHBatchInfo.setStatusMsg("Success");
    }
    catch (Throwable localThrowable)
    {
      String str5 = str1 + " failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5, str6, 0);
      throw new FFSException(localThrowable, str5);
    }
    return paramETFACHBatchInfo;
  }
  
  private static Object[] a(ETFACHBatchInfo paramETFACHBatchInfo)
    throws FFSException
  {
    String str = "Transfer.getInsertSQLParametersForETFACHBatch: ";
    FFSDebug.log("*****", str, "starts", 6);
    Object[] arrayOfObject = { paramETFACHBatchInfo.getBatchId(), paramETFACHBatchInfo.getFileId(), paramETFACHBatchInfo.getCompanyName(), paramETFACHBatchInfo.getCompanyIdentification(), paramETFACHBatchInfo.getStdEntryClassCode(), paramETFACHBatchInfo.getEffectiveEntryDate(), paramETFACHBatchInfo.getEntryAddendaCount() != null ? new Integer(paramETFACHBatchInfo.getEntryAddendaCount()) : new Integer(0), paramETFACHBatchInfo.getTotalDebits() != null ? new Integer(paramETFACHBatchInfo.getTotalDebits()) : new Integer(0), paramETFACHBatchInfo.getNumberOfDebits() != null ? new Integer(paramETFACHBatchInfo.getNumberOfDebits()) : new Integer(0), paramETFACHBatchInfo.getTotalCredits() != null ? new Integer(paramETFACHBatchInfo.getTotalCredits()) : new Integer(0), paramETFACHBatchInfo.getNumberOfCredits() != null ? new Integer(paramETFACHBatchInfo.getNumberOfCredits()) : new Integer(0), paramETFACHBatchInfo.getBatchNumber() != null ? new Integer(paramETFACHBatchInfo.getBatchNumber()) : new Integer(0), paramETFACHBatchInfo.getCustomerId(), paramETFACHBatchInfo.getCustomerId() != null ? new Integer(paramETFACHBatchInfo.getCustomerId()) : new Integer(0) };
    return arrayOfObject;
  }
  
  public static ETFACHFileInfo addETFACHFile(FFSConnectionHolder paramFFSConnectionHolder, ETFACHFileInfo paramETFACHFileInfo)
    throws FFSException
  {
    String str1 = "Transfer.addETFACHFile";
    String str2 = null;
    Object[] arrayOfObject = null;
    String str3 = null;
    FFSDebug.log(str1 + ": Starts", 6);
    if (paramFFSConnectionHolder == null)
    {
      paramETFACHFileInfo.setStatusCode(16000);
      String str4 = "FFSConnectionHolder object  is null";
      paramETFACHFileInfo.setStatusMsg(str4);
      FFSDebug.log(str1, str4, 0);
      return paramETFACHFileInfo;
    }
    try
    {
      str2 = "INSERT INTO ETF_ACHFile (FileId, CreateDate, FileStatus, BatchCount, TotalDebit, NumberOfDebits, TotalCredit, NumberOfCredits,  ProcessId, ExportFileName, RecordCount) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      str3 = DBUtil.getNewTransId("FileID", 32);
      paramETFACHFileInfo.setFileId(str3);
      arrayOfObject = jdMethod_if(paramETFACHFileInfo);
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      paramETFACHFileInfo.setStatusCode(0);
      paramETFACHFileInfo.setStatusMsg("Success");
    }
    catch (Throwable localThrowable)
    {
      String str5 = str1 + " failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5, str6, 0);
      throw new FFSException(localThrowable, str5);
    }
    return paramETFACHFileInfo;
  }
  
  private static Object[] jdMethod_if(ETFACHFileInfo paramETFACHFileInfo)
    throws FFSException
  {
    String str = "Transfer.getInsertSQLParametersForETFACHFile: ";
    FFSDebug.log("*****", str, "starts", 6);
    Object[] arrayOfObject = { paramETFACHFileInfo.getFileId(), paramETFACHFileInfo.getCreateDate(), paramETFACHFileInfo.getFileStatus(), paramETFACHFileInfo.getBatchCount() != null ? new Integer(paramETFACHFileInfo.getBatchCount()) : new Integer(0), paramETFACHFileInfo.getTotalDebits(), paramETFACHFileInfo.getNumberOfDebits() != null ? new Integer(paramETFACHFileInfo.getNumberOfDebits()) : new Integer(0), paramETFACHFileInfo.getTotalCredits(), paramETFACHFileInfo.getNumberOfCredits() != null ? new Integer(paramETFACHFileInfo.getNumberOfCredits()) : new Integer(0), paramETFACHFileInfo.getProcessId(), paramETFACHFileInfo.getExportFileName(), paramETFACHFileInfo.getRecordCount() != null ? new Integer(paramETFACHFileInfo.getRecordCount()) : new Integer(0) };
    return arrayOfObject;
  }
  
  public static ETFACHFileInfo updateETFACHFileWithCtrlInfo(FFSConnectionHolder paramFFSConnectionHolder, ETFACHFileInfo paramETFACHFileInfo)
    throws FFSException
  {
    String str1 = "Transfer.updateETFACHFileWithCtrlInfo";
    String str2 = null;
    Object[] arrayOfObject = null;
    FFSDebug.log(str1, ": Starts.", 6);
    if (paramFFSConnectionHolder == null)
    {
      paramETFACHFileInfo.setStatusCode(16000);
      String str3 = "FFSConnectionHolder object  is null";
      paramETFACHFileInfo.setStatusMsg(str3);
      FFSDebug.log(str1, str3, 0);
      return paramETFACHFileInfo;
    }
    try
    {
      str2 = "UPDATE ETF_ACHFile set FileStatus = ?, BatchCount = ?, RecordCount = ?, TotalDebit = ?, NumberOfDebits = ?, TotalCredit = ?, NumberOfCredits = ? WHERE FileId = ?";
      arrayOfObject = a(paramETFACHFileInfo);
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (i == 0)
      {
        paramETFACHFileInfo.setStatusCode(16020);
        paramETFACHFileInfo.setStatusMsg(" record not found");
      }
      else
      {
        paramETFACHFileInfo.setStatusCode(0);
        paramETFACHFileInfo.setStatusMsg("Success");
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    return paramETFACHFileInfo;
  }
  
  private static Object[] a(ETFACHFileInfo paramETFACHFileInfo)
    throws FFSException
  {
    String str = "Transfer.getUpdateSQLParametersForTransfer: ";
    FFSDebug.log("*****", str, "starts", 6);
    Object[] arrayOfObject = { paramETFACHFileInfo.getFileStatus(), paramETFACHFileInfo.getBatchCount() != null ? new Integer(paramETFACHFileInfo.getBatchCount()) : new Integer(0), paramETFACHFileInfo.getRecordCount() != null ? new Integer(paramETFACHFileInfo.getRecordCount()) : new Integer(0), paramETFACHFileInfo.getTotalDebits(), paramETFACHFileInfo.getNumberOfDebits() != null ? new Integer(paramETFACHFileInfo.getNumberOfDebits()) : new Integer(0), paramETFACHFileInfo.getTotalCredits(), paramETFACHFileInfo.getNumberOfCredits() != null ? new Integer(paramETFACHFileInfo.getNumberOfCredits()) : new Integer(0), paramETFACHFileInfo.getFileId() };
    return arrayOfObject;
  }
  
  public static ETFACHBatchInfo updateETFACHBatchWithCtrlInfo(FFSConnectionHolder paramFFSConnectionHolder, ETFACHBatchInfo paramETFACHBatchInfo)
    throws FFSException
  {
    String str1 = "Transfer.updateETFACHBatchWithCtrlInfo";
    String str2 = null;
    Object[] arrayOfObject = null;
    FFSDebug.log(str1 + ": Starts", 6);
    if (paramFFSConnectionHolder == null)
    {
      paramETFACHBatchInfo.setStatusCode(16000);
      String str3 = "FFSConnectionHolder object  is null";
      paramETFACHBatchInfo.setStatusMsg(str3);
      FFSDebug.log(str1, str3, 0);
      return paramETFACHBatchInfo;
    }
    try
    {
      str2 = "UPDATE ETF_ACHBatch set EntryAddendaCount = ?, TotalDebits = ?, NumberOfDebits = ?, TotalCredits = ?, NumberOfCredits = ?, EffectiveEntryDate = ? WHERE BatchId = ?";
      arrayOfObject = jdMethod_if(paramETFACHBatchInfo);
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (i == 0)
      {
        paramETFACHBatchInfo.setStatusCode(16020);
        paramETFACHBatchInfo.setStatusMsg(" record not found");
      }
      else
      {
        paramETFACHBatchInfo.setStatusCode(0);
        paramETFACHBatchInfo.setStatusMsg("Success");
      }
      paramETFACHBatchInfo.setStatusCode(0);
      paramETFACHBatchInfo.setStatusMsg("Success");
    }
    catch (Throwable localThrowable)
    {
      String str4 = str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    return paramETFACHBatchInfo;
  }
  
  private static Object[] jdMethod_if(ETFACHBatchInfo paramETFACHBatchInfo)
    throws FFSException
  {
    String str = "Transfer.getUpdateSQLParametersForETFACHBatch: ";
    FFSDebug.log("*****", str, "starts", 6);
    Object[] arrayOfObject = { paramETFACHBatchInfo.getEntryAddendaCount() != null ? new Integer(paramETFACHBatchInfo.getEntryAddendaCount()) : new Integer(0), paramETFACHBatchInfo.getTotalDebits() != null ? new Integer(paramETFACHBatchInfo.getTotalDebits()) : new Integer(0), paramETFACHBatchInfo.getNumberOfDebits() != null ? new Integer(paramETFACHBatchInfo.getNumberOfDebits()) : new Integer(0), paramETFACHBatchInfo.getTotalCredits() != null ? new Integer(paramETFACHBatchInfo.getTotalCredits()) : new Integer(0), paramETFACHBatchInfo.getNumberOfCredits() != null ? new Integer(paramETFACHBatchInfo.getNumberOfCredits()) : new Integer(0), paramETFACHBatchInfo.getEffectiveEntryDate(), paramETFACHBatchInfo.getBatchId() };
    return arrayOfObject;
  }
  
  public static ETFACHFileInfo deleteETFACHFile(FFSConnectionHolder paramFFSConnectionHolder, ETFACHFileInfo paramETFACHFileInfo)
    throws FFSException
  {
    String str1 = "Transfer.deleteETFACHFile";
    String str2 = null;
    FFSDebug.log(str1, ": Starts.", 6);
    if (paramFFSConnectionHolder == null)
    {
      paramETFACHFileInfo.setStatusCode(16000);
      localObject = "FFSConnectionHolder object  is null";
      paramETFACHFileInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      return paramETFACHFileInfo;
    }
    Object localObject = { paramETFACHFileInfo.getFileId() };
    try
    {
      str2 = "DELETE From ETF_ACHFile WHERE FileId = ?";
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      if (i == 0)
      {
        paramETFACHFileInfo.setStatusCode(16020);
        paramETFACHFileInfo.setStatusMsg(" record not found");
      }
      else
      {
        paramETFACHFileInfo.setStatusCode(0);
        paramETFACHFileInfo.setStatusMsg("Success");
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    return paramETFACHFileInfo;
  }
  
  public static int deleteETFACHFileByProcessId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "Transfer.deleteETFACHFileByProcessId";
    String str2 = null;
    FFSDebug.log(str1, ": Starts.", 6);
    if (paramFFSConnectionHolder == null)
    {
      localObject = "FFSConnectionHolder object  is null";
      FFSDebug.log(str1, (String)localObject, 0);
      return 16000;
    }
    Object localObject = { paramString };
    try
    {
      str2 = "DELETE From ETF_ACHFile WHERE ProcessId = ?";
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    return 0;
  }
  
  public static int getNextInstanceDateInBPWWarehouse(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, BPWFIInfo paramBPWFIInfo, String paramString2)
    throws FFSException
  {
    String str1 = "Transfer.getNextInstanceDateInBPWWarehouse: ";
    FFSDebug.log(str1 + " start. Date: " + paramString1, 6);
    int i = Integer.parseInt(paramString1);
    try
    {
      int j = DBUtil.getTransferScheduleInterval();
      localObject1 = Calendar.getInstance();
      ((Calendar)localObject1).add(13, j);
      localObject2 = Calendar.getInstance();
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.add(13, j);
      String str2 = FFSUtil.getDateString("yyyyMMdd");
      int k = Integer.parseInt(str2);
      if (k >= i)
      {
        ((Calendar)localObject2).add(13, j);
      }
      else
      {
        localObject2 = jdMethod_int(i);
        ((Calendar)localObject2).set(11, 23);
        ((Calendar)localObject2).set(12, 59);
        localObject1 = (Calendar)((Calendar)localObject2).clone();
        ((Calendar)localObject1).set(11, 0);
        ((Calendar)localObject1).set(12, 0);
      }
      String[] arrayOfString1 = null;
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = "RECETFTRN";
      Calendar[] arrayOfCalendar = DBUtil.getScheduleRunDates(paramFFSConnectionHolder, (Calendar)localObject1, (Calendar)localObject2, paramBPWFIInfo.getFIId(), arrayOfString1);
      if ((arrayOfCalendar != null) && (arrayOfCalendar.length > 0)) {
        i = a(arrayOfCalendar[0]);
      }
    }
    catch (Exception localException)
    {
      Object localObject1 = "*** " + str1 + "failed: ";
      Object localObject2 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localException, (String)localObject1);
    }
    i *= 100;
    FFSDebug.log(str1 + "done. NextInstanceDate: " + i, 6);
    return i;
  }
  
  public static ArrayList getProcessedTransfersForFIId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt1, int paramInt2)
    throws FFSException
  {
    String str1 = "Transfer.getTransfersToBeProcessedToday";
    FFSDebug.log(str1, ": start", 6);
    String str2 = "SELECT SrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, DateDue, DateToPost, DatePosted, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, SourceRecSrvrTId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, ProcessDate, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, Action, FundsRetry, BankFromRtnType, ProcessNumber, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_Transfer  WHERE FIId = ? AND  (LastProcessId = ? AND Status = 'POSTEDON' AND ProcessNumber < ?)  ORDER BY TransferDest";
    Object[] arrayOfObject = { paramString2, paramString1, new Integer(paramInt1) };
    TransferInfo localTransferInfo = null;
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        i++;
        localTransferInfo = new TransferInfo();
        a(localTransferInfo, localFFSResultSet, false);
        jdMethod_do(localTransferInfo, paramFFSConnectionHolder, str1);
        ExtTransferCompanyInfo localExtTransferCompanyInfo = null;
        localObject1 = ExternalTransferCompany.getExternalTransferCompany(paramFFSConnectionHolder, localTransferInfo.getCustomerId(), localTransferInfo.getFIId());
        if ((localObject1 != null) && (localObject1.length > 0))
        {
          localExtTransferCompanyInfo = localObject1[0];
        }
        else
        {
          localExtTransferCompanyInfo = new ExtTransferCompanyInfo();
          localExtTransferCompanyInfo.setStatusCode(16020);
          localExtTransferCompanyInfo.setStatusMsg(" record not found");
        }
        localTransferInfo.setExtTransferCompanyInfo(localExtTransferCompanyInfo);
        localArrayList.add(localTransferInfo);
        if (i == paramInt2) {
          break;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1, str3, 0);
      throw new FFSException(localThrowable, (String)localObject1);
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
    FFSDebug.log(str1, ": done, rows fetched =" + localArrayList.size(), 6);
    return localArrayList;
  }
  
  public static ArrayList getUnProcessedTransfersForFIId(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "Transfer.getUnProcessedTransfersForFIId ";
    FFSDebug.log(str1, ": start", 6);
    String str2 = "SELECT SrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, DateDue, DateToPost, DatePosted, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, SourceRecSrvrTId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, ProcessDate, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, Action, FundsRetry, BankFromRtnType, ProcessNumber, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_Transfer  WHERE FIId = ? AND  ( ((DateToPost >= ? AND ProcessDate <= ?) OR DateToPost < ?) AND ((Status = 'WILLPROCESSON' AND LastProcessId is null AND FundsProcessing = 1) OR Status = 'FUNDSPROCESSED') AND (ProcessNumber = 0) )  ORDER BY TransferDest";
    String str3 = "" + DBUtil.getCurrentStartDate();
    Object[] arrayOfObject = { paramString, str3, str3, str3 };
    TransferInfo localTransferInfo = null;
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        i++;
        localTransferInfo = new TransferInfo();
        a(localTransferInfo, localFFSResultSet, false);
        jdMethod_do(localTransferInfo, paramFFSConnectionHolder, str1);
        ExtTransferCompanyInfo localExtTransferCompanyInfo = null;
        localObject1 = ExternalTransferCompany.getExternalTransferCompany(paramFFSConnectionHolder, localTransferInfo.getCustomerId(), localTransferInfo.getFIId());
        if ((localObject1 != null) && (localObject1.length > 0))
        {
          localExtTransferCompanyInfo = localObject1[0];
        }
        else
        {
          localExtTransferCompanyInfo = new ExtTransferCompanyInfo();
          localExtTransferCompanyInfo.setStatusCode(16020);
          localExtTransferCompanyInfo.setStatusMsg(" record not found");
        }
        localTransferInfo.setExtTransferCompanyInfo(localExtTransferCompanyInfo);
        localArrayList.add(localTransferInfo);
        if (i == paramInt) {
          break;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1, str4, 0);
      throw new FFSException(localThrowable, (String)localObject1);
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
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1, ": done, rows fetched =" + localArrayList.size(), 6);
    return localArrayList;
  }
  
  public static TransferInfo[] getTransferInfosByExtAcctId(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.getTransferInfosByExtAcctId";
    FFSDebug.log(str1, ": start, extAcctId=", paramString, 6);
    String str2 = null;
    Object[] arrayOfObject = { paramString, "CANCELEDON", "POSTEDON" };
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    if (paramBoolean) {
      str2 = "SELECT RecSrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, StartDate, EndDate, Frequency, InstanceCount, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, BankFromRtnType, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_RecTransfer  WHERE ExternalAcctId = ?  AND Status NOT IN (?, ?) ";
    } else {
      str2 = "SELECT SrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, DateDue, DateToPost, DatePosted, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, SourceRecSrvrTId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, ProcessDate, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, Action, FundsRetry, BankFromRtnType, ProcessNumber, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_Transfer  WHERE ExternalAcctId = ?  AND Status NOT IN (?, ?) ";
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        TransferInfo localTransferInfo = new TransferInfo();
        a(localTransferInfo, localFFSResultSet, paramBoolean);
        jdMethod_do(localTransferInfo, paramFFSConnectionHolder, str1);
        localArrayList.add(localTransferInfo);
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
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
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return (TransferInfo[])localArrayList.toArray(new TransferInfo[0]);
  }
  
  public static int getBatchLeadDays(CustomerInfo paramCustomerInfo)
  {
    if (paramCustomerInfo == null) {
      return 2;
    }
    return paramCustomerInfo.ACHCreditLeadDays > paramCustomerInfo.ACHDebitLeadDays ? paramCustomerInfo.ACHCreditLeadDays : paramCustomerInfo.ACHDebitLeadDays;
  }
  
  public static int getInternalCustomerACHLeadDays(CustomerInfo paramCustomerInfo, String paramString)
  {
    if (paramString != null)
    {
      paramString = paramString.trim();
      if (paramString.equals("ETOI")) {
        return paramCustomerInfo.ACHDebitLeadDays;
      }
    }
    return paramCustomerInfo.ACHCreditLeadDays;
  }
  
  public static int getCustomerLeadDays(CustomerInfo paramCustomerInfo, String paramString)
  {
    if ((paramCustomerInfo == null) || (paramCustomerInfo.getStatusCode() != 0)) {
      return ai(paramString);
    }
    return getInternalCustomerACHLeadDays(paramCustomerInfo, paramString);
  }
  
  public static int getValidTransferDueDate(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException
  {
    String str1 = "Transfer.getValidTransferDueDate: ";
    FFSDebug.log(str1 + " In DueDate = " + paramTransferInfo.getDateDue(), 6);
    String str2 = paramTransferInfo.getFIId();
    String str3 = paramTransferInfo.getDateDue();
    if (!BPWUtil.validateDate(str3, "yyyyMMdd"))
    {
      FFSDebug.log(str1 + "Invalid due date value for batch." + str3, 6);
      throw new FFSException(17203, "Invalid due date value for batch." + str3);
    }
    if (str3.length() > 8) {
      str3 = str3.substring(0, 8);
    }
    int i = Integer.parseInt(str3);
    try
    {
      i = getTransferPayday(paramTransferInfo.getFIId(), i);
    }
    catch (Exception localException1) {}
    try
    {
      int j = DBUtil.getTransferScheduleInterval();
      localObject1 = Calendar.getInstance();
      ((Calendar)localObject1).add(13, j);
      localObject2 = Calendar.getInstance();
      Calendar localCalendar1 = Calendar.getInstance();
      localCalendar1.add(13, j);
      CustomerInfo localCustomerInfo = Customer.getCustomerInfo(paramTransferInfo.getCustomerId(), paramFFSConnectionHolder, paramTransferInfo);
      int k = getCustomerLeadDays(localCustomerInfo, paramTransferInfo.getTransferDest());
      String str4 = FFSUtil.getDateString("yyyyMMdd");
      int m = Integer.parseInt(str4);
      if (m >= i)
      {
        ((Calendar)localObject2).add(13, j);
      }
      else
      {
        localObject2 = jdMethod_int(i);
        ((Calendar)localObject2).set(11, 23);
        ((Calendar)localObject2).set(12, 59);
        int n = i;
        try
        {
          for (int i1 = 0; i1 < k; i1++) {
            n = getTransferBusinessDay(str2, n, false);
          }
        }
        catch (Exception localException2) {}
        localObject1 = jdMethod_int(n);
        ((Calendar)localObject1).set(11, 0);
        ((Calendar)localObject1).set(12, 0);
      }
      Object localObject3 = null;
      String[] arrayOfString1 = new String[1];
      arrayOfString1[0] = "ETFTRN";
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = "ETFFUNDTRN";
      arrayOfString2[1] = "ETFTRN";
      if ((paramTransferInfo.getFundsProcessing() == 2) && (paramTransferInfo.getProcessType() == 1)) {
        localObject3 = arrayOfString2;
      } else {
        localObject3 = arrayOfString1;
      }
      Calendar[] arrayOfCalendar1 = DBUtil.getScheduleRunDates(paramFFSConnectionHolder, (Calendar)localObject1, (Calendar)localObject2, str2, localObject3);
      Calendar localCalendar2 = arrayOfCalendar1[(arrayOfCalendar1.length - 1)];
      Calendar localCalendar3 = arrayOfCalendar1[0];
      Calendar localCalendar4 = null;
      if (isPrenoteRequired(paramFFSConnectionHolder, paramTransferInfo, localCalendar4))
      {
        if (localCalendar4 == null)
        {
          Calendar[] arrayOfCalendar2 = DBUtil.getScheduleRunDates(paramFFSConnectionHolder, localCalendar1, localCalendar2, str2, arrayOfString1);
          localCalendar4 = arrayOfCalendar2[0];
        }
        i2 = a(localCalendar4);
        for (i3 = 0; i3 < 6; i3++) {
          i2 = getTransferBusinessDay(str2, i2, true);
        }
        FFSDebug.log(str1 + " Prenote matured date: " + i2, 6);
        i3 = a(localCalendar2);
        if (i3 < i2)
        {
          Calendar localCalendar5 = Calendar.getInstance();
          localCalendar5 = jdMethod_int(i2);
          localCalendar5.set(11, 0);
          localCalendar5.set(12, 0);
          localObject2 = (Calendar)localCalendar5.clone();
          ((Calendar)localObject2).set(11, 23);
          ((Calendar)localObject2).set(12, 59);
          Calendar[] arrayOfCalendar3 = DBUtil.getScheduleRunDates(paramFFSConnectionHolder, localCalendar5, (Calendar)localObject2, str2, localObject3);
          if (arrayOfCalendar3.length > 1) {
            localCalendar2 = (Calendar)arrayOfCalendar3[1].clone();
          } else {
            localCalendar2 = (Calendar)arrayOfCalendar3[0].clone();
          }
          localCalendar3 = (Calendar)arrayOfCalendar3[0].clone();
        }
      }
      int i2 = a(localCalendar3);
      int i3 = i;
      if (i <= i2)
      {
        if ((paramTransferInfo.getProcessLeadNumber() != 0) && (k != 0)) {
          i = getTransferBusinessDay(str2, i2, true);
        } else {
          i = i2;
        }
        i3 = i2;
      }
      else if ((paramTransferInfo.getProcessLeadNumber() != 0) && (k != 0))
      {
        i3 = getTransferBusinessDay(str2, i, false);
      }
      paramTransferInfo.setDateToPost(String.valueOf(i3));
      paramTransferInfo.setProcessDate(String.valueOf(i2));
      FFSDebug.log(str1 + " DateToPost: " + i, 6);
      FFSDebug.log(str1 + " ProcessDate: " + paramTransferInfo.getProcessDate(), 6);
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = str1 + "failed: ";
      Object localObject2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1 + (String)localObject2, 0);
      throw new FFSException(localThrowable, (String)localObject1);
    }
    FFSDebug.log(str1 + " Out DueDate = " + i, 6);
    return i;
  }
  
  private static Calendar jdMethod_int(int paramInt)
    throws FFSException
  {
    try
    {
      return BPWUtil.dateIntToCalendar(paramInt, "yyyyMMdd");
    }
    catch (Exception localException)
    {
      String str = "Date format is not correct " + paramInt;
      FFSDebug.log(str, 6);
      throw new FFSException(localException, str);
    }
  }
  
  private static int a(Calendar paramCalendar)
  {
    return paramCalendar.get(1) * 10000 + (paramCalendar.get(2) + 1) * 100 + paramCalendar.get(5);
  }
  
  public static boolean isPrenoteRequired(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, Calendar paramCalendar)
    throws FFSException
  {
    String str1 = "Transfer.isPrenoteRequired:";
    FFSDebug.log(str1 + " start", 6);
    boolean bool = false;
    String str2 = paramTransferInfo.getTransferDest();
    if (str2 != null)
    {
      ExtTransferAcctInfo localExtTransferAcctInfo = null;
      String str3 = null;
      if (str2.compareTo("ITOE") == 0)
      {
        localExtTransferAcctInfo = paramTransferInfo.getAccountToInfo();
        str3 = paramTransferInfo.getAccountToId();
      }
      else if (str2.compareTo("ETOI") == 0)
      {
        localExtTransferAcctInfo = paramTransferInfo.getAccountFromInfo();
        str3 = paramTransferInfo.getAccountFromId();
      }
      if (localExtTransferAcctInfo == null)
      {
        localExtTransferAcctInfo = new ExtTransferAcctInfo();
        localExtTransferAcctInfo.setAcctId(str3);
        localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo);
      }
      String str4 = localExtTransferAcctInfo.getPrenote();
      if ((str4 != null) && (str4.equalsIgnoreCase("Y")))
      {
        bool = true;
        String str5 = localExtTransferAcctInfo.getPrenoteSubDate();
        if (str5 != null)
        {
          SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
          Date localDate = localSimpleDateFormat.parse(str5, new ParsePosition(0));
          paramCalendar = Calendar.getInstance();
          paramCalendar.setTime(localDate);
        }
      }
      else
      {
        bool = false;
      }
    }
    FFSDebug.log(str1 + bool + ". Prenote submitted date: " + DBUtil.calendarToString(paramCalendar), 6);
    return bool;
  }
  
  private static int ai(String paramString)
  {
    String str1 = "Transfer.getTransferExternalCustomerLeadDays: ";
    String str2 = "bpw.ach.batch.leadtime.credit";
    String str3 = "2";
    if (paramString != null)
    {
      paramString = paramString.trim();
      FFSDebug.log(str1 + "transferDest = " + paramString, 6);
      if (paramString.equals("ETOI") == true)
      {
        str2 = "bpw.ach.batch.leadtime.debit";
        str3 = "1";
      }
    }
    else
    {
      FFSDebug.log(str1 + "transferDest is null", 6);
    }
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str4 = localPropertyConfig.otherProperties.getProperty(str2, str3);
    return Integer.parseInt(str4);
  }
  
  public static String getTransferDestBySrvrTId(String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.getTransferDestBySrvrTId: ";
    FFSDebug.log(str1 + "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    String str3 = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        localObject1 = str1 + "Can not get DB Connection.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      if (paramBoolean) {
        str2 = "SELECT TransferDest FROM BPW_RecTransfer WHERE RecSrvrTId = ?";
      } else {
        str2 = "SELECT TransferDest FROM BPW_Transfer WHERE SrvrTId = ?";
      }
      Object localObject1 = { paramString };
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str2, (Object[])localObject1);
      while (localFFSResultSet.getNextRow()) {
        str3 = localFFSResultSet.getColumnString("TransferDest");
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str4 = str1 + "failed: ";
      FFSDebug.log(str4 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str4);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + "failed to close ResultSet: " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
    FFSDebug.log(str1 + "done", 6);
    return str3;
  }
  
  private static boolean aj(String paramString)
  {
    return (paramString == null) || (paramString.trim().length() == 0);
  }
  
  public static boolean isDuplicateTemplate(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.isDuplicateTemplate";
    FFSDebug.log(str1, ": start", 6);
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      StringBuffer localStringBuffer = null;
      localObject1 = null;
      int i = 0;
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("SELECT BatchID, FIID, CustomerID,  BatchName, BatchType, TotalAmount, AmountCurrency, TransferCount,  SubmittedBy, SubmitDate, BatchStatus, Memo, LogID  FROM BPW_TransferBatch ");
      localObject1 = new ArrayList();
      localStringBuffer.append(" WHERE BatchName = ? ");
      ((ArrayList)localObject1).add(paramString1);
      localStringBuffer.append(" AND SubmittedBy = ? ");
      ((ArrayList)localObject1).add(paramString2);
      localStringBuffer.append(" AND BatchType = 'TEMPLATE' ");
      localStringBuffer.append(" AND BatchStatus <> 'CANCELEDON' ");
      i = ((ArrayList)localObject1).size();
      arrayOfObject = new Object[i];
      arrayOfObject = ((ArrayList)localObject1).toArray();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      boolean bool4;
      if (localFFSResultSet.getNextRow()) {
        if (paramBoolean)
        {
          String str3 = localFFSResultSet.getColumnString("BatchID");
          if ((str3 != null) && (!str3.equals(paramString3)))
          {
            bool4 = true;
            return bool4;
          }
        }
        else
        {
          boolean bool1 = true;
          return bool1;
        }
      }
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("SELECT SrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, DateDue, DateToPost, DatePosted, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, SourceRecSrvrTId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, ProcessDate, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, Action, FundsRetry, BankFromRtnType, ProcessNumber, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_Transfer ");
      localObject1 = new ArrayList();
      localStringBuffer.append(" WHERE TemplateNickName = ? ");
      ((ArrayList)localObject1).add(paramString1);
      localStringBuffer.append(" AND SubmittedBy = ? ");
      ((ArrayList)localObject1).add(paramString2);
      localStringBuffer.append(" AND TransferType = 'TEMPLATE' ");
      localStringBuffer.append(" AND Status <> 'CANCELEDON' ");
      i = ((ArrayList)localObject1).size();
      arrayOfObject = new Object[i];
      arrayOfObject = ((ArrayList)localObject1).toArray();
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        if (paramBoolean)
        {
          String str4 = localFFSResultSet.getColumnString("SrvrTId");
          if ((str4 != null) && (!str4.equals(paramString3)))
          {
            bool4 = true;
            return bool4;
          }
        }
        else
        {
          boolean bool2 = true;
          return bool2;
        }
      }
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("SELECT RecSrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, StartDate, EndDate, Frequency, InstanceCount, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, BankFromRtnType, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_RecTransfer ");
      localObject1 = new ArrayList();
      localStringBuffer.append(" WHERE TemplateNickName = ? ");
      ((ArrayList)localObject1).add(paramString1);
      localStringBuffer.append(" AND SubmittedBy = ? ");
      ((ArrayList)localObject1).add(paramString2);
      localStringBuffer.append(" AND TransferType = 'TEMPLATE' ");
      localStringBuffer.append(" AND Status <> 'CANCELEDON' ");
      i = ((ArrayList)localObject1).size();
      arrayOfObject = new Object[i];
      arrayOfObject = ((ArrayList)localObject1).toArray();
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        if (paramBoolean)
        {
          String str5 = localFFSResultSet.getColumnString("RecSrvrTId");
          if ((str5 != null) && (!str5.equals(paramString3)))
          {
            bool4 = true;
            return bool4;
          }
        }
        else
        {
          boolean bool3 = true;
          return bool3;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject1 = str1 + " failed: ";
      String str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1, str2, 0);
      throw new FFSException(localThrowable, (String)localObject1);
    }
    finally
    {
      try
      {
        localFFSConnectionHolder.conn.commit();
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Exception localException)
      {
        String str6 = str1 + " failed: ";
        String str7 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str6, str7, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return false;
  }
  
  public static TransferInfo updateStatusAction(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.updateStatusAction";
    FFSDebug.log(str1, "start", 6);
    String str2 = null;
    String str3 = paramTransferInfo.getSrvrTId();
    if (paramFFSConnectionHolder == null)
    {
      paramTransferInfo.setStatusCode(16000);
      localObject = "FFSConnectionHolder object  is null";
      paramTransferInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      return paramTransferInfo;
    }
    Object localObject = { paramTransferInfo.getPrcStatus(), paramTransferInfo.getAction(), str3 };
    if (paramBoolean) {
      str2 = "UPDATE BPW_RecTransfer SET Status=? , Action=? WHERE RecSrvrTId=?";
    } else {
      str2 = "UPDATE BPW_Transfer SET Status=? , Action=? WHERE SrvrTId=?";
    }
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** Transfer.updateStatusAction failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4 + str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
    FFSDebug.log(str1, "done", 6);
    return paramTransferInfo;
  }
  
  public static ArrayList getAllTransfersForBackendByFIId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt1, int paramInt2)
    throws FFSException
  {
    String str1 = "Transfer.getAllTransfersForBackendByFIId: ";
    FFSDebug.log(str1, ": start", 6);
    String str2 = "SELECT SrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, DateDue, DateToPost, DatePosted, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, SourceRecSrvrTId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, ProcessDate, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, Action, FundsRetry, BankFromRtnType, ProcessNumber, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_Transfer  WHERE FIId = ? AND ( (LastProcessId = ? AND (Status = 'POSTEDON' OR Status = 'INPROCESS') AND ProcessNumber < ?)  OR  ( ((DateToPost >= ? AND ProcessDate <= ?) OR DateToPost < ?) AND ((Status = 'WILLPROCESSON' AND LastProcessId is null AND FundsProcessing = 1) OR Status = 'FUNDSPROCESSED') AND (ProcessNumber = 0) ) ) ORDER BY TransferDest";
    String str3 = "" + DBUtil.getCurrentStartDate();
    Object[] arrayOfObject = { paramString2, paramString1, new Integer(paramInt1), str3, str3, str3 };
    TransferInfo localTransferInfo = null;
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        i++;
        localTransferInfo = new TransferInfo();
        a(localTransferInfo, localFFSResultSet, false);
        jdMethod_do(localTransferInfo, paramFFSConnectionHolder, str1);
        ExtTransferCompanyInfo localExtTransferCompanyInfo = null;
        localObject1 = ExternalTransferCompany.getExternalTransferCompany(paramFFSConnectionHolder, localTransferInfo.getCustomerId(), localTransferInfo.getFIId());
        if ((localObject1 != null) && (localObject1.length > 0))
        {
          localExtTransferCompanyInfo = localObject1[0];
        }
        else
        {
          localExtTransferCompanyInfo = new ExtTransferCompanyInfo();
          localExtTransferCompanyInfo.setStatusCode(16020);
          localExtTransferCompanyInfo.setStatusMsg(" record not found");
        }
        localTransferInfo.setExtTransferCompanyInfo(localExtTransferCompanyInfo);
        localArrayList.add(localTransferInfo);
        if (i == paramInt2) {
          break;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1, str4, 0);
      throw new FFSException(localThrowable, (String)localObject1);
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
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1, ": done, rows fetched =" + localArrayList.size(), 6);
    return localArrayList;
  }
  
  public static TransferInfo updateProcessInfo(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException
  {
    String str1 = "Transfer.updateProcessInfo: ";
    FFSDebug.log(str1, "start", 6);
    String str2 = "UPDATE BPW_Transfer SET LastProcessId = ?, ProcessNumber = ? WHERE SrvrTId = ?";
    String str3 = paramTransferInfo.getSrvrTId();
    if (paramFFSConnectionHolder == null)
    {
      paramTransferInfo.setStatusCode(16000);
      localObject = "FFSConnectionHolder object  is null";
      paramTransferInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      return paramTransferInfo;
    }
    Object localObject = { paramTransferInfo.getLastProcessId(), new Integer(paramTransferInfo.getProcessNumber()), str3 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** Transfer.updateProcessInfo failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4 + str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
    FFSDebug.log(str1, "done", 6);
    return paramTransferInfo;
  }
  
  public static TransferInfo[] getPrenoteTransferByProcessId(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "Transfer.getPrenoteTransferByProcessId";
    FFSDebug.log(str1, ": start, processId=", paramString + ", processNumber = " + paramInt, 6);
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    TransferInfo localTransferInfo = null;
    String str2 = "SELECT transfer.SrvrTId, transfer.CustomerId, transfer.TransferType, transfer.TransferDest, transfer.TransferGroup, transfer.TransferCategory, transfer.BankFromRtn, transfer.AccountFromNum, transfer.AccountFromType, transfer.ExternalAcctId, transfer.Amount, transfer.AmountCurrency, transfer.OrigAmount, transfer.OrigCurrency, transfer.DateCreate, transfer.DateDue, transfer.DateToPost, transfer.DatePosted, transfer.LastProcessId, transfer.Memo, transfer.TemplateScope, transfer.TemplateNickName, transfer.SourceTemplateId, transfer.SourceRecSrvrTId, transfer.Status, transfer.SubmittedBy, transfer.LogId , transfer.ProcessLeadNumber, transfer.FIId, transfer.AccountFromId, transfer.ProcessDate, transfer.OriginatingUserId, transfer.ConfirmMsg, transfer.ConfirmNum, transfer.FundsProcessing, transfer.ProcessType, transfer.TypeDetail, transfer.LastChangeDate, transfer.Action, transfer.FundsRetry, transfer.BankFromRtnType, transfer.ProcessNumber, transfer.ToAmount, transfer.ToAmountCurrency, transfer.UserAssignedAmount FROM BPW_Transfer transfer  Where transfer.LastProcessId = ? AND transfer.TransferCategory = 'PRENOTE_ENTRY' AND ProcessNumber < ? AND Status != 'CANCELEDON'";
    Object[] arrayOfObject = { paramString, new Integer(paramInt) };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localTransferInfo = new TransferInfo();
        a(localTransferInfo, localFFSResultSet, false);
        jdMethod_do(localTransferInfo, paramFFSConnectionHolder, str1);
        localArrayList.add(localTransferInfo);
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
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
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return (TransferInfo[])localArrayList.toArray(new TransferInfo[0]);
  }
  
  public static int getMaxProcessNumberByProcessId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "Transfer.getMaxProcessNumberByProcessId: ";
    FFSDebug.log(str1 + "start. ProcessId = " + paramString, 6);
    String str2 = "SELECT MAX(ProcessNumber) FROM BPW_Transfer WHERE LastProcessId = ?";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString };
    int i = 0;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
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
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1 + " done. Max process number = " + i, 6);
    return i;
  }
  
  public static ETFACHBatchInfo deleteETFACHBatch(FFSConnectionHolder paramFFSConnectionHolder, ETFACHBatchInfo paramETFACHBatchInfo)
    throws FFSException
  {
    String str1 = "Transfer.deleteETFACHBatch";
    String str2 = null;
    FFSDebug.log(str1, ": Starts. BatchId = " + paramETFACHBatchInfo.getBatchId(), 6);
    if (paramFFSConnectionHolder == null)
    {
      paramETFACHBatchInfo.setStatusCode(16000);
      localObject = "FFSConnectionHolder object  is null";
      paramETFACHBatchInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      return paramETFACHBatchInfo;
    }
    Object localObject = { paramETFACHBatchInfo.getBatchId() };
    try
    {
      str2 = "DELETE From ETF_ACHBatch WHERE BatchId = ?";
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      if (i == 0)
      {
        paramETFACHBatchInfo.setStatusCode(16020);
        paramETFACHBatchInfo.setStatusMsg(" record not found");
      }
      else
      {
        paramETFACHBatchInfo.setStatusCode(0);
        paramETFACHBatchInfo.setStatusMsg("Success");
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    return paramETFACHBatchInfo;
  }
  
  public static int getTransferPayday(String paramString, int paramInt)
    throws Exception
  {
    if (DBUtil.getTransferIgnoreWeekendRule()) {
      return SmartCalendar.getPayday(paramString, paramInt);
    }
    return SmartCalendar.getACHPayday(paramString, paramInt);
  }
  
  public static int getTransferBusinessDay(String paramString, int paramInt, boolean paramBoolean)
    throws Exception
  {
    if (DBUtil.getTransferIgnoreWeekendRule()) {
      return SmartCalendar.getBusinessDay(paramString, paramInt, paramBoolean);
    }
    return SmartCalendar.getACHBusinessDay(paramString, paramInt, paramBoolean);
  }
  
  public static void checkTransferCustFI(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException
  {
    String str1 = "Transfesr.checkTransferCustFI ";
    String str2 = paramTransferInfo.getCustomerId();
    String str3 = paramTransferInfo.getFIId();
    BPWFIInfo localBPWFIInfo = null;
    CustomerInfo localCustomerInfo = null;
    String str4 = null;
    FFSDebug.log(str1 + ": CustID:", str2, " FIID:", str3, 6);
    try
    {
      str4 = paramTransferInfo.getTransferType();
      String str5;
      if ((str4 == null) || (!str4.trim().equalsIgnoreCase("TEMPLATE")) || ((str3 != null) && (str3.trim().length() != 0)))
      {
        localBPWFIInfo = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, str3);
        if (localBPWFIInfo.getFIId() == null)
        {
          str5 = BPWLocaleUtil.getMessage(23170, new String[] { str3 }, "TRANSFER_MESSAGE");
          paramTransferInfo.setStatusCode(23170);
          paramTransferInfo.setStatusMsg(str5);
          FFSDebug.log(str1 + "failed, ", str5, 0);
          return;
        }
      }
      localCustomerInfo = Customer.getCustomerInfo(str2, paramFFSConnectionHolder, paramTransferInfo);
      if (localCustomerInfo == null)
      {
        str5 = BPWLocaleUtil.getMessage(19130, new String[] { str2 }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusCode(19130);
        paramTransferInfo.setStatusMsg(str5);
        FFSDebug.log(str1 + "failed, ", str5, 0);
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      String str6 = "***Transfesr.checkTransferCustFI failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str6, 0);
      throw new FFSException(localThrowable, str6);
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "TRANSFER_MESSAGE"));
  }
  
  public static ArrayList getTransfersByBatchId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "Transfer.getTransfersByBatchId";
    FFSDebug.log(str1, ": start, batchId=", paramString, 6);
    String str2 = null;
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    str2 = "SELECT SrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, DateDue, DateToPost, DatePosted, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, SourceRecSrvrTId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, ProcessDate, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, Action, FundsRetry, BankFromRtnType, ProcessNumber, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_Transfer  WHERE BatchId = ? ";
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        TransferInfo localTransferInfo = new TransferInfo();
        a(localTransferInfo, localFFSResultSet, false);
        jdMethod_do(localTransferInfo, paramFFSConnectionHolder, str1);
        localArrayList.add(localTransferInfo);
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
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
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return localArrayList;
  }
  
  private static String a(AccountTransactions paramAccountTransactions, String paramString, ArrayList paramArrayList, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "getSQLFromCriteria";
    FFSDebug.log(str1, ": done", 6);
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    try
    {
      ExtTransferAcctInfo localExtTransferAcctInfo = paramAccountTransactions.getSearchForAccount();
      localStringBuffer.append(" WHERE ");
      if (paramAccountTransactions.getAccountScope().equals("FromAccount"))
      {
        if (!aj(localExtTransferAcctInfo.getAcctId()))
        {
          localStringBuffer.append(" AccountFromId = ? ");
          paramArrayList.add(localExtTransferAcctInfo.getAcctId());
        }
        else
        {
          localStringBuffer.append(" CustomerId = ?");
          paramArrayList.add(localExtTransferAcctInfo.getCustomerId());
          localStringBuffer.append(" AND AccountFromNum = ?");
          paramArrayList.add(localExtTransferAcctInfo.getAcctNum());
          localStringBuffer.append(" AND AccountFromType = ?");
          paramArrayList.add(localExtTransferAcctInfo.getAcctType());
          localStringBuffer.append(" AND BankFromRtn = ?");
          paramArrayList.add(localExtTransferAcctInfo.getAcctBankRtn());
        }
      }
      else if (paramAccountTransactions.getAccountScope().equals("ToAccount"))
      {
        if (!aj(localExtTransferAcctInfo.getAcctId()))
        {
          localStringBuffer.append(" ExternalAcctId = ? ");
          paramArrayList.add(localExtTransferAcctInfo.getAcctId());
        }
      }
      else if (paramAccountTransactions.getAccountScope().equals("FromAccountAndToAccount")) {
        if (!aj(localExtTransferAcctInfo.getAcctId()))
        {
          localStringBuffer.append("( AccountFromId = ? ");
          paramArrayList.add(localExtTransferAcctInfo.getAcctId());
          localStringBuffer.append("  OR ExternalAcctId = ? ) ");
          paramArrayList.add(localExtTransferAcctInfo.getAcctId());
        }
        else
        {
          localStringBuffer.append(" ( CustomerId = ?");
          paramArrayList.add(localExtTransferAcctInfo.getCustomerId());
          localStringBuffer.append(" AND AccountFromNum = ?");
          paramArrayList.add(localExtTransferAcctInfo.getAcctNum());
          localStringBuffer.append(" AND AccountFromType = ?");
          paramArrayList.add(localExtTransferAcctInfo.getAcctType());
          localStringBuffer.append(" AND BankFromRtn = ? ) ");
          paramArrayList.add(localExtTransferAcctInfo.getAcctBankRtn());
        }
      }
      if (paramAccountTransactions.getTransactionType().equals("TRANSFER"))
      {
        if (paramBoolean)
        {
          localStringBuffer.append(" AND TransferType = ?");
          paramArrayList.add("Repetitive");
        }
        else
        {
          localStringBuffer.append(" AND TransferType IN (?, ?)");
          paramArrayList.add("Current");
          paramArrayList.add("Recurring");
        }
        localStringBuffer.append(" AND Status IN (?, ?, ?, ?, ?, ?, ?)");
        paramArrayList.add("WILLPROCESSON");
        paramArrayList.add("BATCH_INPROCESS");
        paramArrayList.add("IMMED_INPROCESS");
        paramArrayList.add("INPROCESS");
        paramArrayList.add("FUNDSPROCESSED");
        paramArrayList.add("APPROVAL_PENDING");
        paramArrayList.add("APPROVAL_REJECTED");
      }
      else if (paramAccountTransactions.getTransactionType().equals("TEMPLATE"))
      {
        localStringBuffer.append(" AND TransferType = ?");
        localStringBuffer.append(" AND Status = ?");
        if (paramBoolean)
        {
          paramArrayList.add("RECTEMPLATE");
          paramArrayList.add("RECTEMPLATE");
        }
        else
        {
          paramArrayList.add("TEMPLATE");
          paramArrayList.add("TEMPLATE");
        }
      }
      else if (paramAccountTransactions.getTransactionType().equals("TRANSFERANDTEMPLATE"))
      {
        if (paramBoolean)
        {
          localStringBuffer.append(" AND TransferType IN ( ?, ? )");
          paramArrayList.add("Repetitive");
          paramArrayList.add("RECTEMPLATE");
        }
        else
        {
          localStringBuffer.append(" AND TransferType IN ( ?, ?, ? )");
          paramArrayList.add("Current");
          paramArrayList.add("Recurring");
          paramArrayList.add("TEMPLATE");
        }
        localStringBuffer.append(" AND Status IN (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        paramArrayList.add("WILLPROCESSON");
        paramArrayList.add("BATCH_INPROCESS");
        paramArrayList.add("IMMED_INPROCESS");
        paramArrayList.add("INPROCESS");
        paramArrayList.add("FUNDSPROCESSED");
        paramArrayList.add("APPROVAL_PENDING");
        paramArrayList.add("APPROVAL_REJECTED");
        paramArrayList.add("TEMPLATE");
        paramArrayList.add("RECTEMPLATE");
      }
      FFSDebug.log(str1, "getSQLFromCriteria:" + paramString, 6);
      return localStringBuffer.toString();
    }
    catch (Exception localException)
    {
      String str2 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localException, str2);
    }
  }
  
  public static ArrayList getPendingTransfersByAccount(AccountTransactions paramAccountTransactions, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.getTransfersByAccount";
    FFSDebug.log(str1, ": start", 6);
    String str2 = null;
    ArrayList localArrayList1 = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList2 = new ArrayList();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      if (paramBoolean) {
        str2 = "SELECT RecSrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, StartDate, EndDate, Frequency, InstanceCount, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, BankFromRtnType, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_RecTransfer ";
      } else {
        str2 = "SELECT SrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, DateDue, DateToPost, DatePosted, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, SourceRecSrvrTId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, ProcessDate, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, Action, FundsRetry, BankFromRtnType, ProcessNumber, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_Transfer ";
      }
      str2 = a(paramAccountTransactions, str2, localArrayList1, paramBoolean);
      Object[] arrayOfObject = localArrayList1.toArray();
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        if (paramBoolean) {
          localObject1 = new RecTransferInfo();
        } else {
          localObject1 = new TransferInfo();
        }
        a((TransferInfo)localObject1, localFFSResultSet, paramBoolean);
        jdMethod_do((TransferInfo)localObject1, localFFSConnectionHolder, str1);
        localArrayList2.add(localObject1);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, str3, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
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
        try
        {
          if (localFFSConnectionHolder != null) {
            DBUtil.freeConnection(localFFSConnectionHolder.conn);
          }
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log(str1, " Failed to free connection", 6);
        }
      }
      catch (Exception localException)
      {
        String str4 = str1 + " failed: ";
        String str5 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str4, str5, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return localArrayList2;
  }
  
  public static long getPendingTransfersCountByAccount(AccountTransactions paramAccountTransactions, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Transfer.getTransfersCountByAccount";
    FFSDebug.log(str1, ": start", 6);
    String str2 = null;
    ArrayList localArrayList = new ArrayList();
    long l = 0L;
    FFSResultSet localFFSResultSet = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      if (paramBoolean) {
        str2 = "SELECT COUNT(*) AS TRANSFERCOUNT FROM BPW_RecTransfer ";
      } else {
        str2 = "SELECT COUNT(*) AS TRANSFERCOUNT FROM BPW_Transfer ";
      }
      str2 = a(paramAccountTransactions, str2, localArrayList, paramBoolean);
      Object[] arrayOfObject = localArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        l = localFFSResultSet.getColumnLong("TRANSFERCOUNT");
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable1)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable1, str3);
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
        try
        {
          if (localFFSConnectionHolder != null) {
            DBUtil.freeConnection(localFFSConnectionHolder.conn);
          }
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log(str1, " Failed to free connection", 6);
        }
      }
      catch (Exception localException)
      {
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return l;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.Transfer
 * JD-Core Version:    0.7.0.1
 */