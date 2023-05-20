package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.BillPayTmpHist;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.db.RecPmtInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.ffs.bpw.interfaces.SortCriterion;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.beans.PagingContext;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class PagedBillPay
  extends PagedTmpTable
{
  private static final String bY = "PmtClause";
  private static final String b1 = "PmtArgList";
  private static final String bS = "RecPmtClause";
  private static final String bV = "RecPmtArgList";
  private static final String b2 = "PmtTemplateClause";
  private static final String bT = "PmtTemplateArgList";
  private static final String bR = "RecPmtTemplateClause";
  private static final String b0 = "RecPmtTemplateArgList";
  private static final String bQ = "PmtBatchClause";
  private static final String bW = "PmtBatchArgList";
  private static final String b3 = "RecModelClause";
  private static final String bZ = "RecModelArgList";
  private final String bU = "SELECT instTable.SrvrTID, instTable.StartDate, instTable.AcctDebitID, instTable.AcctDebitType, instTable.Status, instTable.Amount, instTable.PaymentType, instTable.RecSrvrTID, instTable.PaymentName, payeeTable.PayeeName FROM BPW_PmtInstruction instTable, BPW_Payee payeeTable";
  private final String bP = "SELECT instTable.RecSrvrTID, instTable.AcctDebitID, instTable.AcctDebitType, instTable.Status, instTable.Amount, instTable.Frequency, instTable.FIID, instTable.FinalAmt, instTable.PaymentName, payeeTable.PayeeName, instTable.StartDate, instTable.EndDate FROM BPW_RecPmtInstruction instTable, BPW_Payee payeeTable";
  private final String bX = "SELECT instTable.BatchID, instTable.TotalAmount, instTable.BatchName, instTable.BatchStatus FROM BPW_PaymentBatch instTable";
  private PropertyConfig bO = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
  
  public String getTmpTableName()
    throws FFSException
  {
    return "BPW_BillPayTmpHist";
  }
  
  protected PagingInfo jdMethod_case(PagingInfo paramPagingInfo)
    throws FFSException
  {
    paramPagingInfo = super.jdMethod_case(paramPagingInfo);
    if (paramPagingInfo.getStatusCode() != 0) {
      return paramPagingInfo;
    }
    paramPagingInfo = jdMethod_long(paramPagingInfo);
    if (paramPagingInfo.getStatusCode() != 0) {
      return paramPagingInfo;
    }
    paramPagingInfo = jdMethod_void(paramPagingInfo);
    if (paramPagingInfo.getStatusCode() != 0) {
      return paramPagingInfo;
    }
    return paramPagingInfo;
  }
  
  private PagingInfo jdMethod_long(PagingInfo paramPagingInfo)
  {
    String str1 = "PagedBillPay.validateSearchParams";
    FFSDebug.log(str1 + ": Validating search parameters.", 6);
    try
    {
      PagingContext localPagingContext = paramPagingInfo.getPagingContext();
      HashMap localHashMap1 = localPagingContext.getMap();
      HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
      String str2 = (String)localHashMap2.get("CustomerId");
      if ((str2 == null) || (str2.trim().length() == 0))
      {
        paramPagingInfo.setStatusCode(24090);
        paramPagingInfo.setStatusMsg("Customer ID is null");
        return paramPagingInfo;
      }
      Calendar localCalendar1 = localPagingContext.getStartDate();
      if (localCalendar1 == null)
      {
        localCalendar1 = Calendar.getInstance();
        localCalendar1.add(2, -1);
        localPagingContext.setStartDate(localCalendar1);
        FFSDebug.log(str1 + ": Null Beginning Date." + " Defaulting to one month back from today.", 6);
      }
      Calendar localCalendar2 = localPagingContext.getEndDate();
      if (localCalendar2 == null)
      {
        localCalendar2 = Calendar.getInstance();
        localCalendar2.add(2, 1);
        localPagingContext.setEndDate(localCalendar2);
        FFSDebug.log(str1 + ": Null Ending Date." + " Defaulting to one month from today.", 6);
      }
      if (localCalendar1.after(localCalendar2) == true)
      {
        paramPagingInfo.setStatusCode(17080);
        paramPagingInfo.setStatusMsg("Start date cannot be after end date");
        return paramPagingInfo;
      }
    }
    catch (Throwable localThrowable)
    {
      paramPagingInfo.setStatusCode(28500);
      try
      {
        paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(28500, null, "PAYMENT_MESSAGE"));
      }
      catch (Exception localException) {}
      return paramPagingInfo;
    }
    return paramPagingInfo;
  }
  
  private PagingInfo jdMethod_void(PagingInfo paramPagingInfo)
  {
    String str1 = "PagedBillPay.validateSortParams";
    FFSDebug.log(str1 + ": Validating sorting parameters.", 6);
    try
    {
      PagingContext localPagingContext = paramPagingInfo.getPagingContext();
      ArrayList localArrayList = localPagingContext.getSortCriteriaList();
      SortCriterion localSortCriterion = null;
      if ((localArrayList == null) || (localArrayList.size() == 0))
      {
        localSortCriterion = new SortCriterion();
        localSortCriterion.setName("DateToPost");
        localSortCriterion.setAscending();
        if (localArrayList == null)
        {
          localArrayList = new ArrayList();
          localPagingContext.setSortCriteriaList(localArrayList);
        }
        localArrayList.add(localSortCriterion);
        FFSDebug.log(str1 + ": SortCriteria not found." + " Using default values.", 6);
      }
      else
      {
        localSortCriterion = (SortCriterion)localArrayList.get(0);
      }
      FFSDebug.log(str1 + ": SortCriteria" + " name = " + localSortCriterion.getName() + ", ascending = " + localSortCriterion.isAscending(), 6);
      String str2 = localSortCriterion.getName();
      if ((str2.equals("DateToPost") != true) && (str2.equals("PayeeName") != true) && (str2.equals("AcctDebitId") != true) && (str2.equals("Frequency") != true) && (str2.equals("Status") != true) && (str2.equals("Amount") != true) && (str2.equals("CursorId") != true) && (str2.equals("TemplateName") != true) && (str2.equals("PaymentType") != true))
      {
        paramPagingInfo.setStatusCode(28503);
        paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(28503, null, "PAYMENT_MESSAGE"));
        return paramPagingInfo;
      }
    }
    catch (Throwable localThrowable)
    {
      paramPagingInfo.setStatusCode(28500);
      try
      {
        paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(28500, null, "PAYMENT_MESSAGE"));
      }
      catch (Exception localException) {}
      return paramPagingInfo;
    }
    return paramPagingInfo;
  }
  
  private void jdMethod_try(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = (String)paramHashMap.get("PmtClause");
    ArrayList localArrayList = (ArrayList)((ArrayList)paramHashMap.get("PmtArgList")).clone();
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    StringBuffer localStringBuffer = new StringBuffer(str1);
    String str2 = "instTable.";
    DBUtil.appendStringToCondition(localStringBuffer, localArrayList, str2 + "PaymentType", "Current");
    a(paramFFSConnectionHolder, paramPagingInfo, localStringBuffer.toString(), localArrayList);
  }
  
  private void jdMethod_new(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = (String)paramHashMap.get("PmtClause");
    ArrayList localArrayList = (ArrayList)((ArrayList)paramHashMap.get("PmtArgList")).clone();
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    StringBuffer localStringBuffer = new StringBuffer(str1);
    String str2 = "instTable.";
    DBUtil.appendStringToCondition(localStringBuffer, localArrayList, str2 + "PaymentType", "Recurring");
    a(paramFFSConnectionHolder, paramPagingInfo, localStringBuffer.toString(), localArrayList);
  }
  
  private void jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = (String)paramHashMap.get("PmtTemplateClause");
    ArrayList localArrayList = (ArrayList)((ArrayList)paramHashMap.get("PmtTemplateArgList")).clone();
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    StringBuffer localStringBuffer = new StringBuffer(str1);
    String str2 = "instTable.";
    DBUtil.appendStringToCondition(localStringBuffer, localArrayList, str2 + "PaymentType", "TEMPLATE");
    String[] arrayOfString = (String[])localHashMap2.get("SubmittedBys");
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      if (arrayOfString.length == 1)
      {
        localStringBuffer.append(" AND SubmittedBy = ? ");
        localArrayList.add(arrayOfString[0]);
      }
      else
      {
        localStringBuffer.append(" AND SubmittedBy IN (?");
        localArrayList.add(arrayOfString[0]);
        for (int i = 1; i < arrayOfString.length; i++)
        {
          localStringBuffer.append(", ?");
          localArrayList.add(arrayOfString[i]);
        }
        localStringBuffer.append(")");
      }
    }
    localStringBuffer.append(" and BatchID is null");
    a(paramFFSConnectionHolder, paramPagingInfo, localStringBuffer.toString(), localArrayList);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    BillPayTmpHist localBillPayTmpHist = new BillPayTmpHist();
    int i = paramPagingInfo.getTotalTrans();
    localBillPayTmpHist.setCursorId(i);
    String str1 = DBUtil.getCurrentLogDate();
    localBillPayTmpHist.setSubmitDate(str1);
    String str2 = paramPagingInfo.getPagingContext().getSessionId();
    if ((str2 == null) || (str2.trim().length() == 0))
    {
      str2 = DBUtil.getNextIndexString("SessionID");
      paramPagingInfo.getPagingContext().setSessionId(str2);
    }
    a(paramFFSConnectionHolder, paramPagingInfo, paramString, paramArrayList, localBillPayTmpHist);
    i = localBillPayTmpHist.getCursorId();
    paramPagingInfo.setTotalTrans(i);
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = (String)paramHashMap.get("RecPmtClause");
    ArrayList localArrayList = (ArrayList)((ArrayList)paramHashMap.get("RecPmtArgList")).clone();
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    StringBuffer localStringBuffer = new StringBuffer(str1);
    BillPayTmpHist localBillPayTmpHist = new BillPayTmpHist();
    int i = paramPagingInfo.getTotalTrans();
    localBillPayTmpHist.setCursorId(i);
    String str2 = DBUtil.getCurrentLogDate();
    localBillPayTmpHist.setSubmitDate(str2);
    String str3 = paramPagingInfo.getPagingContext().getSessionId();
    if ((str3 == null) || (str3.trim().length() == 0))
    {
      str3 = DBUtil.getNextIndexString("SessionID");
      paramPagingInfo.getPagingContext().setSessionId(str3);
    }
    jdMethod_if(paramFFSConnectionHolder, paramPagingInfo, localStringBuffer.toString(), localArrayList, localBillPayTmpHist);
    i = localBillPayTmpHist.getCursorId();
    paramPagingInfo.setTotalTrans(i);
  }
  
  private void jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "PagedBillPay.createSessionDataRecTemplatePayment";
    FFSDebug.log(str1 + ": start.", 6);
    String str2 = "SELECT instTable.RecSrvrTID, instTable.AcctDebitID, instTable.AcctDebitType, instTable.Status, instTable.Amount, instTable.Frequency, instTable.FIID, instTable.FinalAmt, instTable.PaymentName, payeeTable.PayeeName, instTable.StartDate, instTable.EndDate FROM BPW_RecPmtInstruction instTable, BPW_Payee payeeTable" + (String)paramHashMap.get("RecPmtTemplateClause");
    ArrayList localArrayList = (ArrayList)((ArrayList)paramHashMap.get("RecPmtTemplateArgList")).clone();
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    StringBuffer localStringBuffer = new StringBuffer(str2);
    String str3 = "instTable.";
    DBUtil.appendStringArrayToCondition(localStringBuffer, localArrayList, str3 + "PaymentType", "RECTEMPLATE");
    String[] arrayOfString = (String[])localHashMap2.get("SubmittedBys");
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      if (arrayOfString.length == 1)
      {
        localStringBuffer.append(" AND SubmittedBy = ? ");
        localArrayList.add(arrayOfString[0]);
      }
      else
      {
        localStringBuffer.append(" AND SubmittedBy IN (?");
        localArrayList.add(arrayOfString[0]);
        for (i = 1; i < arrayOfString.length; i++)
        {
          localStringBuffer.append(", ?");
          localArrayList.add(arrayOfString[i]);
        }
        localStringBuffer.append(")");
      }
    }
    int i = paramPagingInfo.getTotalTrans();
    String str4 = DBUtil.getCurrentLogDate();
    String str5 = paramPagingInfo.getPagingContext().getSessionId();
    if ((str5 == null) || (str5.trim().length() == 0))
    {
      str5 = DBUtil.getNextIndexString("SessionID");
      paramPagingInfo.getPagingContext().setSessionId(str5);
    }
    String str6 = "RECTEMPLATE";
    FFSResultSet localFFSResultSet = null;
    try
    {
      int j = this.bO.getBatchSize();
      int k = 0;
      String str7 = (String)localHashMap2.get("StatusList");
      boolean bool = true;
      if (str7 != null) {
        bool = DBUtil.checkStatusQuery(str7, "ACTIVE");
      }
      if (bool)
      {
        Object[] arrayOfObject = (Object[])localArrayList.toArray(new Object[0]);
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
        while (localFFSResultSet.getNextRow() == true)
        {
          int m = 0;
          String str8 = localFFSResultSet.getColumnString("AcctDebitID");
          String str9 = localFFSResultSet.getColumnString("AcctDebitType");
          String str10 = localFFSResultSet.getColumnString("Status");
          String str11 = localFFSResultSet.getColumnString("Amount");
          String str12 = localFFSResultSet.getColumnString("RecSrvrTID");
          String str13 = localFFSResultSet.getColumnString("PaymentName");
          String str14 = localFFSResultSet.getColumnString("PayeeName");
          int n = localFFSResultSet.getColumnInt("Frequency");
          String str15 = CommonProcessor.mapSchFreqToString(n);
          String str16 = BPWUtil.getAccountIDWithAccountType(str8, str9);
          i++;
          BillPayTmpHist.insertBillPayTmpHist(paramFFSConnectionHolder, str5, i, str4, str12, str6, m, str14, str16, str15, n, str10, str11, str13);
          k++;
          if (k % j == 0) {
            paramFFSConnectionHolder.conn.commit();
          }
        }
        FFSDebug.log(str1 + ": Recurring Payment Template records found = " + k, 6);
      }
      paramPagingInfo.setTotalTrans(i);
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      try
      {
        paramFFSConnectionHolder.conn.rollback();
      }
      catch (Throwable localThrowable1) {}
      throw new FFSException(localException.toString() + FFSDebug.stackTrace(localException));
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
      catch (Throwable localThrowable2) {}
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "PagedBillPay.createSessionDataRecPaymentModel";
    FFSDebug.log(str1 + ": start.", 6);
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    Calendar localCalendar1 = localPagingContext.getStartDate();
    Calendar localCalendar2 = localPagingContext.getEndDate();
    Calendar localCalendar3 = Calendar.getInstance();
    localCalendar3.set(11, 0);
    localCalendar3.set(12, 0);
    localCalendar3.set(13, 0);
    localCalendar3.set(14, 0);
    if (localCalendar2.before(localCalendar3) == true) {
      return;
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    String str2 = localSimpleDateFormat.format(localCalendar1.getTime()) + "00";
    int i = Integer.parseInt(str2);
    String str3 = localSimpleDateFormat.format(localCalendar2.getTime()) + "99";
    int j = Integer.parseInt(str3);
    String str4 = "SELECT instTable.RecSrvrTID, instTable.AcctDebitID, instTable.AcctDebitType, instTable.Status, instTable.Amount, instTable.Frequency, instTable.FIID, instTable.FinalAmt, instTable.PaymentName, payeeTable.PayeeName, instTable.StartDate, instTable.EndDate FROM BPW_RecPmtInstruction instTable, BPW_Payee payeeTable" + (String)paramHashMap.get("RecModelClause");
    ArrayList localArrayList = (ArrayList)((ArrayList)paramHashMap.get("RecModelArgList")).clone();
    StringBuffer localStringBuffer = new StringBuffer(str4);
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    int k = paramPagingInfo.getTotalTrans();
    String str5 = DBUtil.getCurrentLogDate();
    String str6 = paramPagingInfo.getPagingContext().getSessionId();
    if ((str6 == null) || (str6.trim().length() == 0))
    {
      str6 = DBUtil.getNextIndexString("SessionID");
      paramPagingInfo.getPagingContext().setSessionId(str6);
    }
    String str7 = "RECMODEL";
    FFSResultSet localFFSResultSet = null;
    try
    {
      int m = this.bO.getBatchSize();
      int n = 0;
      String str8 = (String)localHashMap2.get("StatusList");
      boolean bool = true;
      if (str8 != null) {
        bool = DBUtil.checkStatusQuery(str8, "WILLPROCESSON");
      }
      if (bool)
      {
        Object[] arrayOfObject = (Object[])localArrayList.toArray(new Object[0]);
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
        while (localFFSResultSet.getNextRow() == true)
        {
          String str9 = localFFSResultSet.getColumnString("RecSrvrTID");
          String str10 = localFFSResultSet.getColumnString("AcctDebitID");
          String str11 = localFFSResultSet.getColumnString("AcctDebitType");
          String str12 = localFFSResultSet.getColumnString("Status");
          String str13 = localFFSResultSet.getColumnString("Amount");
          int i1 = localFFSResultSet.getColumnInt("Frequency");
          String str14 = localFFSResultSet.getColumnString("FIID");
          String str15 = localFFSResultSet.getColumnString("PaymentName");
          String str16 = localFFSResultSet.getColumnString("PayeeName");
          int i2 = localFFSResultSet.getColumnInt("EndDate");
          String str17 = CommonProcessor.mapSchFreqToString(i1);
          String str18 = BPWUtil.getAccountIDWithAccountType(str10, str11);
          int i3 = 0;
          PmtInfo localPmtInfo = PmtInstruction.getRecModelMostRecentInstance(paramFFSConnectionHolder, str9);
          if ((localPmtInfo != null) && (localPmtInfo.StartDate > i)) {
            i3 = localPmtInfo.StartDate;
          }
          if (i3 == 0)
          {
            ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(str14, "RECPMTTRN", str9, paramFFSConnectionHolder);
            if (localScheduleInfo != null)
            {
              String[] arrayOfString = ScheduleInfo.getPendingDatesByStartAndEndDate(localScheduleInfo, i, i2);
              if (arrayOfString.length > 0) {
                i3 = Integer.parseInt(arrayOfString[0].substring(0, 10));
              }
            }
          }
          FFSDebug.log(str1 + ": rec model start date: " + i3, 6);
          if (i3 != 0)
          {
            k++;
            BillPayTmpHist.insertBillPayTmpHist(paramFFSConnectionHolder, str6, k, str5, str9, str7, i3, str16, str18, str17, i1, str12, str13, str15);
            n++;
            if (n % m == 0) {
              paramFFSConnectionHolder.conn.commit();
            }
          }
        }
        FFSDebug.log(str1 + ": Recurring Payment Model records found = " + n, 6);
      }
      paramPagingInfo.setTotalTrans(k);
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      try
      {
        paramFFSConnectionHolder.conn.rollback();
      }
      catch (Throwable localThrowable1) {}
      throw new FFSException(localException.toString() + FFSDebug.stackTrace(localException));
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
      catch (Throwable localThrowable2) {}
    }
  }
  
  private void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "PagedBillPay.createSessionDataTemplatePaymentBatch";
    FFSDebug.log(str1 + ": start.", 6);
    String str2 = "SELECT instTable.BatchID, instTable.TotalAmount, instTable.BatchName, instTable.BatchStatus FROM BPW_PaymentBatch instTable" + (String)paramHashMap.get("PmtBatchClause");
    ArrayList localArrayList = (ArrayList)((ArrayList)paramHashMap.get("PmtBatchArgList")).clone();
    StringBuffer localStringBuffer = new StringBuffer(str2);
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    String str3 = "instTable.";
    String str4 = new String("BATCHTEMPLATE");
    DBUtil.appendStringArrayToCondition(localStringBuffer, localArrayList, str3 + "BatchType", "TEMPLATE");
    String[] arrayOfString = (String[])localHashMap2.get("SubmittedBys");
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      if (arrayOfString.length == 1)
      {
        localStringBuffer.append(" AND SubmittedBy = ? ");
        localArrayList.add(arrayOfString[0]);
      }
      else
      {
        localStringBuffer.append(" AND SubmittedBy IN (?");
        localArrayList.add(arrayOfString[0]);
        for (i = 1; i < arrayOfString.length; i++)
        {
          localStringBuffer.append(", ?");
          localArrayList.add(arrayOfString[i]);
        }
        localStringBuffer.append(")");
      }
    }
    int i = paramPagingInfo.getTotalTrans();
    String str5 = DBUtil.getCurrentLogDate();
    String str6 = paramPagingInfo.getPagingContext().getSessionId();
    if ((str6 == null) || (str6.trim().length() == 0))
    {
      str6 = DBUtil.getNextIndexString("SessionID");
      paramPagingInfo.getPagingContext().setSessionId(str6);
    }
    String str7 = "PMTBATCH";
    FFSResultSet localFFSResultSet = null;
    try
    {
      int j = this.bO.getBatchSize();
      int k = 0;
      String str8 = (String)localHashMap2.get("StatusList");
      boolean bool = true;
      if (str8 != null) {
        bool = DBUtil.checkStatusQuery(str8, "ACTIVE");
      }
      if (bool)
      {
        Object[] arrayOfObject = (Object[])localArrayList.toArray(new Object[0]);
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
        while (localFFSResultSet.getNextRow() == true)
        {
          String str9 = localFFSResultSet.getColumnString("BatchID");
          String str10 = localFFSResultSet.getColumnString("BatchName");
          String str11 = localFFSResultSet.getColumnString("TotalAmount");
          String str12 = localFFSResultSet.getColumnString("BatchStatus");
          PaymentProcessor localPaymentProcessor = new PaymentProcessor();
          PaymentBatchInfo localPaymentBatchInfo = localPaymentProcessor.getPaymentBatchById(str9);
          if (localPaymentBatchInfo != null)
          {
            PmtInfo[] arrayOfPmtInfo = localPaymentBatchInfo.getPayments();
            if ((arrayOfPmtInfo != null) && (arrayOfPmtInfo.length > 0) && (arrayOfPmtInfo[0] != null)) {
              str4 = arrayOfPmtInfo[0].getAcctDebitID();
            }
          }
          i++;
          BillPayTmpHist.insertBillPayTmpHist(paramFFSConnectionHolder, str6, i, str5, str9, str7, 0, new String("BATCHTEMPLATE"), str4, null, 0, str12, str11, str10);
          k++;
          if (k % j == 0) {
            paramFFSConnectionHolder.conn.commit();
          }
        }
        FFSDebug.log(str1 + ": Payment Batch Template records found = " + k, 6);
      }
      paramPagingInfo.setTotalTrans(i);
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      try
      {
        paramFFSConnectionHolder.conn.rollback();
      }
      catch (Throwable localThrowable1) {}
      throw new FFSException(localException.toString() + FFSDebug.stackTrace(localException));
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
      catch (Throwable localThrowable2) {}
    }
  }
  
  protected void jdMethod_try(PagingInfo paramPagingInfo)
  {
    String str1 = "PagedBillPay.createSessionData";
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
      HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
      HashMap localHashMap3 = jdMethod_null(paramPagingInfo);
      String[] arrayOfString = (String[])localHashMap2.get("PmtType");
      if ((arrayOfString == null) || (arrayOfString.length == 0))
      {
        jdMethod_try(localFFSConnectionHolder, paramPagingInfo, localHashMap3);
        jdMethod_new(localFFSConnectionHolder, paramPagingInfo, localHashMap3);
        jdMethod_if(localFFSConnectionHolder, paramPagingInfo, localHashMap3);
        jdMethod_int(localFFSConnectionHolder, paramPagingInfo, localHashMap3);
        jdMethod_for(localFFSConnectionHolder, paramPagingInfo, localHashMap3);
      }
      else
      {
        if (containsInArray(arrayOfString, "Current")) {
          jdMethod_try(localFFSConnectionHolder, paramPagingInfo, localHashMap3);
        }
        if (containsInArray(arrayOfString, "Recurring")) {
          jdMethod_new(localFFSConnectionHolder, paramPagingInfo, localHashMap3);
        }
        if (containsInArray(arrayOfString, "TEMPLATE")) {
          jdMethod_int(localFFSConnectionHolder, paramPagingInfo, localHashMap3);
        }
        if (containsInArray(arrayOfString, "RECTEMPLATE")) {
          jdMethod_for(localFFSConnectionHolder, paramPagingInfo, localHashMap3);
        }
        if (containsInArray(arrayOfString, "BATCHTEMPLATE")) {
          jdMethod_do(localFFSConnectionHolder, paramPagingInfo, localHashMap3);
        }
        if (containsInArray(arrayOfString, "Recmodel")) {
          a(localFFSConnectionHolder, paramPagingInfo, localHashMap3);
        }
        if (containsInArray(arrayOfString, "Repetitive")) {
          jdMethod_if(localFFSConnectionHolder, paramPagingInfo, localHashMap3);
        }
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable1)
    {
      try
      {
        localFFSConnectionHolder.conn.rollback();
      }
      catch (Throwable localThrowable2) {}
      String str2 = str1 + " failed";
      FFSDebug.log(localThrowable1, str2);
      paramPagingInfo.setStatusCode(28050);
      try
      {
        paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(28050, null, "PAYMENT_MESSAGE"));
      }
      catch (Exception localException) {}
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private HashMap jdMethod_null(PagingInfo paramPagingInfo)
  {
    String str1 = "PagedBillPay.createSQLWhereClause";
    StringBuffer localStringBuffer1 = new StringBuffer();
    ArrayList localArrayList1 = new ArrayList();
    StringBuffer localStringBuffer2 = new StringBuffer();
    ArrayList localArrayList2 = new ArrayList();
    StringBuffer localStringBuffer3 = new StringBuffer();
    ArrayList localArrayList3 = new ArrayList();
    StringBuffer localStringBuffer4 = new StringBuffer();
    ArrayList localArrayList4 = new ArrayList();
    StringBuffer localStringBuffer5 = new StringBuffer();
    ArrayList localArrayList5 = new ArrayList();
    StringBuffer localStringBuffer6 = new StringBuffer();
    ArrayList localArrayList6 = new ArrayList();
    try
    {
      PagingContext localPagingContext = paramPagingInfo.getPagingContext();
      HashMap localHashMap2 = localPagingContext.getMap();
      localObject1 = (HashMap)localHashMap2.get("SEARCH_CRITERIA");
      String str3 = "instTable.";
      SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
      Object localObject2 = (String)((HashMap)localObject1).get("CustomerId");
      localStringBuffer1.append(" WHERE " + str3 + "CustomerID" + " = ?");
      localArrayList1.add(((String)localObject2).trim());
      localStringBuffer2.append(" WHERE " + str3 + "CustomerID" + " = ?");
      localArrayList2.add(((String)localObject2).trim());
      localStringBuffer3.append(" WHERE " + str3 + "CustomerID" + " = ?");
      localArrayList3.add(((String)localObject2).trim());
      localStringBuffer4.append(" WHERE " + str3 + "CustomerID" + " = ?");
      localArrayList4.add(((String)localObject2).trim());
      localStringBuffer5.append(" WHERE " + str3 + "CustomerID" + " = ?");
      localArrayList5.add(((String)localObject2).trim());
      localStringBuffer6.append(" WHERE " + str3 + "CustomerID" + " = ?");
      localArrayList6.add(((String)localObject2).trim());
      localObject2 = localPagingContext.getStartDate();
      Object localObject4 = localSimpleDateFormat1.format(((Calendar)localObject2).getTime()) + "00";
      localStringBuffer1.append(" AND " + str3 + "StartDate" + " >= ?");
      localArrayList1.add(new Integer((String)localObject4));
      localStringBuffer2.append(" AND (" + str3 + "EndDate" + " >= ?" + " OR " + str3 + "EndDate" + " = -1)");
      localArrayList2.add(new Integer((String)localObject4));
      localStringBuffer6.append(" AND (" + str3 + "EndDate" + " >= ?" + " OR " + str3 + "EndDate" + " = -1)");
      localArrayList6.add(new Integer((String)localObject4));
      localObject2 = localPagingContext.getEndDate();
      localObject4 = localSimpleDateFormat1.format(((Calendar)localObject2).getTime()) + "99";
      localStringBuffer1.append(" AND " + str3 + "StartDate" + " <= ?");
      localArrayList1.add(new Integer((String)localObject4));
      localStringBuffer2.append(" AND " + str3 + "StartDate" + " <= ?");
      localArrayList2.add(new Integer((String)localObject4));
      Calendar localCalendar = (Calendar)((Calendar)localObject2).clone();
      localCalendar.set(11, 23);
      localCalendar.set(12, 59);
      localCalendar.set(13, 59);
      localCalendar.set(14, 999);
      SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
      String str5 = localSimpleDateFormat2.format(localCalendar.getTime());
      localStringBuffer6.append(" AND " + str3 + "DateCreate" + " <= ?");
      localArrayList6.add(str5);
      localObject2 = (String)((HashMap)localObject1).get("FIID");
      DBUtil.appendStringToCondition(localStringBuffer1, localArrayList1, str3 + "FIID", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer2, localArrayList2, str3 + "FIID", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer3, localArrayList3, str3 + "FIID", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer4, localArrayList4, str3 + "FIID", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer5, localArrayList5, str3 + "FIID", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer6, localArrayList6, str3 + "FIID", (String)localObject2);
      localObject2 = (String)((HashMap)localObject1).get("AcctDebitNumber");
      DBUtil.appendStringToCondition(localStringBuffer1, localArrayList1, str3 + "AcctDebitID", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer2, localArrayList2, str3 + "AcctDebitID", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer3, localArrayList3, str3 + "AcctDebitID", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer4, localArrayList4, str3 + "AcctDebitID", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer6, localArrayList6, str3 + "AcctDebitID", (String)localObject2);
      localObject2 = (String)((HashMap)localObject1).get("AcctDebitType");
      DBUtil.appendStringToCondition(localStringBuffer1, localArrayList1, str3 + "AcctDebitType", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer2, localArrayList2, str3 + "AcctDebitType", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer3, localArrayList3, str3 + "AcctDebitType", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer4, localArrayList4, str3 + "AcctDebitType", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer6, localArrayList6, str3 + "AcctDebitType", (String)localObject2);
      localObject2 = (String)((HashMap)localObject1).get("BankID");
      DBUtil.appendStringToCondition(localStringBuffer1, localArrayList1, str3 + "BankID", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer2, localArrayList2, str3 + "BankID", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer3, localArrayList3, str3 + "BankID", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer4, localArrayList4, str3 + "BankID", (String)localObject2);
      DBUtil.appendStringToCondition(localStringBuffer6, localArrayList6, str3 + "BankID", (String)localObject2);
      localObject2 = (String)((HashMap)localObject1).get("StatusList");
      DBUtil.appendStringArrayToCondition(localStringBuffer1, localArrayList1, str3 + "Status", (String)localObject2);
      DBUtil.appendStringArrayToCondition(localStringBuffer2, localArrayList2, str3 + "Status", (String)localObject2);
      DBUtil.appendStringArrayToCondition(localStringBuffer3, localArrayList3, str3 + "Status", (String)localObject2);
      DBUtil.appendStringArrayToCondition(localStringBuffer4, localArrayList4, str3 + "Status", (String)localObject2);
      DBUtil.appendStringArrayToCondition(localStringBuffer5, localArrayList5, str3 + "BatchStatus", (String)localObject2);
      DBUtil.appendStringArrayToCondition(localStringBuffer6, localArrayList6, str3 + "Status", (String)localObject2);
      localObject2 = (String)((HashMap)localObject1).get("RecSrvrTIDList");
      DBUtil.appendStringArrayToCondition(localStringBuffer1, localArrayList1, str3 + "RecSrvrTID", (String)localObject2);
      DBUtil.appendStringArrayToCondition(localStringBuffer2, localArrayList2, str3 + "RecSrvrTID", (String)localObject2);
      DBUtil.appendStringArrayToCondition(localStringBuffer3, localArrayList3, str3 + "RecSrvrTID", (String)localObject2);
      DBUtil.appendStringArrayToCondition(localStringBuffer4, localArrayList4, str3 + "RecSrvrTID", (String)localObject2);
      DBUtil.appendStringArrayToCondition(localStringBuffer6, localArrayList6, str3 + "RecSrvrTID", (String)localObject2);
      localObject2 = (String)((HashMap)localObject1).get("PayeeIdList");
      DBUtil.appendStringArrayToCondition(localStringBuffer1, localArrayList1, str3 + "PayeeID", (String)localObject2);
      DBUtil.appendStringArrayToCondition(localStringBuffer2, localArrayList2, str3 + "PayeeID", (String)localObject2);
      DBUtil.appendStringArrayToCondition(localStringBuffer3, localArrayList3, str3 + "PayeeID", (String)localObject2);
      DBUtil.appendStringArrayToCondition(localStringBuffer4, localArrayList4, str3 + "PayeeID", (String)localObject2);
      DBUtil.appendStringArrayToCondition(localStringBuffer6, localArrayList6, str3 + "PayeeID", (String)localObject2);
      try
      {
        localObject2 = (String)((HashMap)localObject1).get("PayeeListIdList");
        DBUtil.appendIntArrayToCondition(localStringBuffer1, localArrayList1, str3 + "PayeeListID", (String)localObject2);
        DBUtil.appendIntArrayToCondition(localStringBuffer2, localArrayList2, str3 + "PayeeListID", (String)localObject2);
        DBUtil.appendIntArrayToCondition(localStringBuffer3, localArrayList3, str3 + "PayeeListID", (String)localObject2);
        DBUtil.appendIntArrayToCondition(localStringBuffer4, localArrayList4, str3 + "PayeeListID", (String)localObject2);
        DBUtil.appendIntArrayToCondition(localStringBuffer6, localArrayList6, str3 + "PayeeListID", (String)localObject2);
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        paramPagingInfo.setStatusCode(28501);
        paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(28501, null, "PAYMENT_MESSAGE"));
        return null;
      }
      Object localObject3 = (String)((HashMap)localObject1).get("BillingAcctList");
      DBUtil.appendStringArrayToCondition(localStringBuffer1, localArrayList1, str3 + "PayAcct", (String)localObject3);
      DBUtil.appendStringArrayToCondition(localStringBuffer2, localArrayList2, str3 + "PayAcct", (String)localObject3);
      DBUtil.appendStringArrayToCondition(localStringBuffer3, localArrayList3, str3 + "PayAcct", (String)localObject3);
      DBUtil.appendStringArrayToCondition(localStringBuffer4, localArrayList4, str3 + "PayAcct", (String)localObject3);
      DBUtil.appendStringArrayToCondition(localStringBuffer6, localArrayList6, str3 + "PayAcct", (String)localObject3);
      localObject3 = (String)((HashMap)localObject1).get("Amount");
      if ((localObject3 != null) && (((String)localObject3).trim().length() != 0)) {
        try
        {
          localObject4 = NumberFormat.getNumberInstance();
          ((NumberFormat)localObject4).setMinimumFractionDigits(4);
          ((NumberFormat)localObject4).setGroupingUsed(false);
          float f = Float.parseFloat((String)localObject3);
          localObject3 = ((NumberFormat)localObject4).format(f);
          DBUtil.appendStringToCondition(localStringBuffer1, localArrayList1, str3 + "Amount", (String)localObject3);
          DBUtil.appendStringToCondition(localStringBuffer2, localArrayList2, str3 + "Amount", (String)localObject3);
          DBUtil.appendStringToCondition(localStringBuffer3, localArrayList3, str3 + "Amount", (String)localObject3);
          DBUtil.appendStringToCondition(localStringBuffer4, localArrayList4, str3 + "Amount", (String)localObject3);
          DBUtil.appendStringToCondition(localStringBuffer5, localArrayList5, str3 + "TotalAmount", (String)localObject3);
          DBUtil.appendStringToCondition(localStringBuffer6, localArrayList6, str3 + "Amount", (String)localObject3);
        }
        catch (NumberFormatException localNumberFormatException2) {}
      }
      try
      {
        localObject3 = (HashMap)((HashMap)localObject1).get("ExtraInfoMap");
        a(localStringBuffer1, localArrayList1, (HashMap)localObject3, str3 + "SrvrTID");
        a(localStringBuffer2, localArrayList2, (HashMap)localObject3, str3 + "RecSrvrTID");
        a(localStringBuffer3, localArrayList3, (HashMap)localObject3, str3 + "SrvrTID");
        a(localStringBuffer4, localArrayList4, (HashMap)localObject3, str3 + "RecSrvrTID");
        a(localStringBuffer6, localArrayList6, (HashMap)localObject3, str3 + "RecSrvrTID");
      }
      catch (Throwable localThrowable2)
      {
        paramPagingInfo.setStatusCode(28502);
        paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(28502, null, "PAYMENT_MESSAGE"));
        return null;
      }
      String str4 = " AND " + str3 + "PayeeID = payeeTable.PayeeID";
      localStringBuffer1.append(str4);
      localStringBuffer2.append(str4);
      localStringBuffer3.append(str4);
      localStringBuffer4.append(str4);
      localStringBuffer6.append(str4);
    }
    catch (Throwable localThrowable1)
    {
      paramPagingInfo.setStatusCode(28500);
      try
      {
        paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(28500, null, "PAYMENT_MESSAGE"));
      }
      catch (Exception localException) {}
      return null;
    }
    HashMap localHashMap1 = new HashMap();
    String str2 = localStringBuffer1.toString();
    Object localObject1 = (Object[])localArrayList1.toArray(new Object[0]);
    localHashMap1.put("PmtClause", str2);
    localHashMap1.put("PmtArgList", localArrayList1);
    int i;
    if (FFSDebug.checkLogLevel(6) == true)
    {
      FFSDebug.log(str1 + ": Pmt clause: " + str2, 6);
      for (i = 0; i < localObject1.length; i++) {
        FFSDebug.log("Arg[" + i + "] = " + localObject1[i].toString(), 6);
      }
    }
    str2 = localStringBuffer2.toString();
    localObject1 = (Object[])localArrayList2.toArray(new Object[0]);
    localHashMap1.put("RecPmtClause", str2);
    localHashMap1.put("RecPmtArgList", localArrayList2);
    if (FFSDebug.checkLogLevel(6) == true)
    {
      FFSDebug.log(str1 + ": RecPmt clause: " + str2, 6);
      for (i = 0; i < localObject1.length; i++) {
        FFSDebug.log("Arg[" + i + "] = " + localObject1[i].toString(), 6);
      }
    }
    str2 = localStringBuffer3.toString();
    localObject1 = (Object[])localArrayList3.toArray(new Object[0]);
    localHashMap1.put("PmtTemplateClause", str2);
    localHashMap1.put("PmtTemplateArgList", localArrayList3);
    if (FFSDebug.checkLogLevel(6) == true)
    {
      FFSDebug.log(str1 + ": pmtTemplate clause: " + str2, 6);
      for (i = 0; i < localObject1.length; i++) {
        FFSDebug.log("Arg[" + i + "] = " + localObject1[i].toString(), 6);
      }
    }
    str2 = localStringBuffer4.toString();
    localObject1 = (Object[])localArrayList4.toArray(new Object[0]);
    localHashMap1.put("RecPmtTemplateClause", str2);
    localHashMap1.put("RecPmtTemplateArgList", localArrayList4);
    if (FFSDebug.checkLogLevel(6) == true)
    {
      FFSDebug.log(str1 + ": RecPmtTemplate clause: " + str2, 6);
      for (i = 0; i < localObject1.length; i++) {
        FFSDebug.log("Arg[" + i + "] = " + localObject1[i].toString(), 6);
      }
    }
    str2 = localStringBuffer5.toString();
    localObject1 = (Object[])localArrayList5.toArray(new Object[0]);
    localHashMap1.put("PmtBatchClause", str2);
    localHashMap1.put("PmtBatchArgList", localArrayList5);
    if (FFSDebug.checkLogLevel(6) == true)
    {
      FFSDebug.log(str1 + ": PmtBatch clause: " + str2, 6);
      for (i = 0; i < localObject1.length; i++) {
        FFSDebug.log("Arg[" + i + "] = " + localObject1[i].toString(), 6);
      }
    }
    str2 = localStringBuffer6.toString();
    localObject1 = (Object[])localArrayList6.toArray(new Object[0]);
    localHashMap1.put("RecModelClause", str2);
    localHashMap1.put("RecModelArgList", localArrayList6);
    if (FFSDebug.checkLogLevel(6) == true)
    {
      FFSDebug.log(str1 + ": RecModel clause: " + str2, 6);
      for (i = 0; i < localObject1.length; i++) {
        FFSDebug.log("Arg[" + i + "] = " + localObject1[i].toString(), 6);
      }
    }
    return localHashMap1;
  }
  
  private void a(StringBuffer paramStringBuffer, ArrayList paramArrayList, HashMap paramHashMap, String paramString)
    throws BPWException
  {
    if ((paramHashMap == null) || (paramHashMap.size() == 0)) {
      return;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList1 = new ArrayList();
    int i = 1;
    Iterator localIterator = paramHashMap.keySet().iterator();
    String str1;
    while (localIterator.hasNext() == true)
    {
      str1 = (String)localIterator.next();
      if (str1 == null) {
        throw new BPWException("Null key value found in the ExtraInfo HashMap.");
      }
      ArrayList localArrayList2 = (ArrayList)paramHashMap.get(str1);
      String str2;
      if (localArrayList2 == null)
      {
        str2 = "Null value found in the ExtraInfo HashMap. Key  = " + str1 + ".";
        throw new BPWException(str2);
      }
      if (localArrayList2.size() == 0)
      {
        str2 = "No match values found in the ExtraInfo HashMap. Key  = " + str1 + ".";
        throw new BPWException(str2);
      }
      if (i == 1) {
        i = 0;
      } else {
        localStringBuffer.append(" AND");
      }
      localStringBuffer.append(" (");
      for (int j = 0; j < localArrayList2.size(); j++)
      {
        String str3 = (String)localArrayList2.get(j);
        if (str3 == null)
        {
          String str4 = "Null value found as a search parameter in the ExtraInfo HashMap. Key  = " + str1 + ".";
          throw new BPWException(str4);
        }
        if (j == 0) {
          localStringBuffer.append("(Name = ? AND Value = ?)");
        } else {
          localStringBuffer.append(" OR (Name = ? AND Value = ?)");
        }
        localArrayList1.add(str1);
        localArrayList1.add(str3);
      }
      localStringBuffer.append(")");
    }
    if (localStringBuffer.length() != 0)
    {
      str1 = " AND " + paramString + " IN " + "(SELECT RecordId FROM BPW_ExtraInfo" + " WHERE (RecordType IN ('" + "IFXPMT" + "','" + "IFXRECPMT" + "')" + " AND";
      paramStringBuffer.append(str1);
      paramStringBuffer.append(localStringBuffer.toString());
      paramStringBuffer.append(" )");
      paramStringBuffer.append(" )");
      paramArrayList.addAll(localArrayList1);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList, BillPayTmpHist paramBillPayTmpHist)
    throws FFSException
  {
    String str1 = "PagedBillPay.createTempHistUsingPmtInstruction";
    FFSDebug.log(str1 + ": start.", 6);
    int i = paramBillPayTmpHist.getCursorId();
    String str2 = paramBillPayTmpHist.getSubmitDate();
    String str3 = paramPagingInfo.getPagingContext().getSessionId();
    String str4 = "PMT";
    FFSResultSet localFFSResultSet = null;
    try
    {
      int j = this.bO.getBatchSize();
      int k = 0;
      String str5 = "SELECT instTable.SrvrTID, instTable.StartDate, instTable.AcctDebitID, instTable.AcctDebitType, instTable.Status, instTable.Amount, instTable.PaymentType, instTable.RecSrvrTID, instTable.PaymentName, payeeTable.PayeeName FROM BPW_PmtInstruction instTable, BPW_Payee payeeTable" + paramString;
      ArrayList localArrayList = paramArrayList;
      Object[] arrayOfObject = (Object[])localArrayList.toArray(new Object[0]);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str5, arrayOfObject);
      while (localFFSResultSet.getNextRow() == true)
      {
        String str6 = localFFSResultSet.getColumnString("SrvrTID");
        int m = localFFSResultSet.getColumnInt("StartDate");
        String str7 = localFFSResultSet.getColumnString("AcctDebitID");
        String str8 = localFFSResultSet.getColumnString("AcctDebitType");
        String str9 = localFFSResultSet.getColumnString("Status");
        String str10 = localFFSResultSet.getColumnString("Amount");
        String str11 = localFFSResultSet.getColumnString("PaymentType");
        String str12 = localFFSResultSet.getColumnString("RecSrvrTID");
        String str13 = localFFSResultSet.getColumnString("PaymentName");
        String str14 = localFFSResultSet.getColumnString("PayeeName");
        int n = -1;
        if (str11.equals("Recurring") == true)
        {
          localObject1 = null;
          try
          {
            localObject1 = RecPmtInstruction.getRecPmtInfo(str12, paramFFSConnectionHolder);
            n = ((RecPmtInfo)localObject1).getRecFrequencyValue();
          }
          catch (Throwable localThrowable2) {}
          if (localObject1 == null) {
            n = 10;
          }
        }
        else
        {
          n = 10;
        }
        if (str11.equals("TEMPLATE")) {
          str4 = "TEMPLATE";
        }
        Object localObject1 = CommonProcessor.mapSchFreqToString(n);
        String str15 = BPWUtil.getAccountIDWithAccountType(str7, str8);
        i++;
        BillPayTmpHist.insertBillPayTmpHist(paramFFSConnectionHolder, str3, i, str2, str6, str4, m, str14, str15, (String)localObject1, n, str9, str10, str13);
        k++;
        if (k % j == 0) {
          paramFFSConnectionHolder.conn.commit();
        }
      }
      FFSDebug.log(str1 + ": Single Pmt records found = " + k, 6);
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      try
      {
        paramFFSConnectionHolder.conn.rollback();
      }
      catch (Throwable localThrowable1) {}
      throw new FFSException(localException.toString() + FFSDebug.stackTrace(localException));
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
      catch (Throwable localThrowable3) {}
    }
    paramBillPayTmpHist.setCursorId(i);
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList, BillPayTmpHist paramBillPayTmpHist)
    throws FFSException
  {
    String str1 = "PagedBillPay.createTempHistUsingRecPmtInstruction";
    FFSDebug.log(str1 + ": start.", 6);
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    Calendar localCalendar1 = localPagingContext.getStartDate();
    Calendar localCalendar2 = localPagingContext.getEndDate();
    Calendar localCalendar3 = Calendar.getInstance();
    localCalendar3.set(11, 0);
    localCalendar3.set(12, 0);
    localCalendar3.set(13, 0);
    localCalendar3.set(14, 0);
    if (localCalendar2.before(localCalendar3) == true) {
      return;
    }
    HashMap localHashMap1 = localPagingContext.getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    String str2 = (String)localHashMap2.get("StatusList");
    boolean bool;
    if (str2 != null) {
      bool = DBUtil.checkStatusQuery(str2, "WILLPROCESSON");
    } else {
      bool = true;
    }
    if (!bool) {
      return;
    }
    int i = paramBillPayTmpHist.getCursorId();
    String str3 = paramBillPayTmpHist.getSubmitDate();
    String str4 = paramPagingInfo.getPagingContext().getSessionId();
    String str5 = "RECPMT";
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    String str6 = localSimpleDateFormat.format(localCalendar1.getTime()) + "00";
    int j = Integer.parseInt(str6);
    String str7 = localSimpleDateFormat.format(localCalendar2.getTime()) + "99";
    int k = Integer.parseInt(str7);
    FFSResultSet localFFSResultSet = null;
    try
    {
      int m = this.bO.getBatchSize();
      int n = 0;
      String str8 = "SELECT instTable.RecSrvrTID, instTable.AcctDebitID, instTable.AcctDebitType, instTable.Status, instTable.Amount, instTable.Frequency, instTable.FIID, instTable.FinalAmt, instTable.PaymentName, payeeTable.PayeeName, instTable.StartDate, instTable.EndDate FROM BPW_RecPmtInstruction instTable, BPW_Payee payeeTable" + paramString;
      ArrayList localArrayList = paramArrayList;
      Object[] arrayOfObject = (Object[])localArrayList.toArray(new Object[0]);
      str8 = str8 + " AND instTable.Status = 'WILLPROCESSON' ";
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str8, arrayOfObject);
      while (localFFSResultSet.getNextRow() == true)
      {
        String str9 = localFFSResultSet.getColumnString("RecSrvrTID");
        String str10 = localFFSResultSet.getColumnString("AcctDebitID");
        String str11 = localFFSResultSet.getColumnString("AcctDebitType");
        String str12 = localFFSResultSet.getColumnString("Status");
        String str13 = localFFSResultSet.getColumnString("Amount");
        int i1 = localFFSResultSet.getColumnInt("Frequency");
        String str14 = localFFSResultSet.getColumnString("FIID");
        String str15 = localFFSResultSet.getColumnString("FinalAmt");
        String str16 = localFFSResultSet.getColumnString("PaymentName");
        String str17 = localFFSResultSet.getColumnString("PayeeName");
        String str18 = CommonProcessor.mapSchFreqToString(i1);
        ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(str14, "RECPMTTRN", str9, paramFFSConnectionHolder);
        if (localScheduleInfo != null)
        {
          localScheduleInfo.InstanceCount -= localScheduleInfo.CurInstanceNum - 1;
          localScheduleInfo.CurInstanceNum = 1;
          String[] arrayOfString = ScheduleInfo.getPendingDatesByStartAndEndDate(localScheduleInfo, j, k);
          localScheduleInfo.CurInstanceNum = localScheduleInfo.InstanceCount;
          ScheduleInfo.computeNextInstanceDate(localScheduleInfo);
          int i2 = localScheduleInfo.NextInstanceDate;
          for (int i3 = 0; i3 < arrayOfString.length; i3++)
          {
            int i4 = Integer.parseInt(arrayOfString[i3].substring(0, 10));
            String str19 = str13;
            if (i4 == i2) {
              str19 = str15;
            }
            String str20 = BPWUtil.getAccountIDWithAccountType(str10, str11);
            i++;
            BillPayTmpHist.insertBillPayTmpHist(paramFFSConnectionHolder, str4, i, str3, str9, str5, i4, str17, str20, str18, i1, str12, str19, str16);
            n++;
            if (n % m == 0) {
              paramFFSConnectionHolder.conn.commit();
            }
          }
        }
      }
      FFSDebug.log(str1 + ": Rec Pmt records found = " + n, 6);
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      try
      {
        paramFFSConnectionHolder.conn.rollback();
      }
      catch (Throwable localThrowable1) {}
      throw new FFSException(localException.toString() + FFSDebug.stackTrace(localException));
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
      catch (Throwable localThrowable2) {}
    }
    paramBillPayTmpHist.setCursorId(i);
  }
  
  protected PagingInfo jdMethod_byte(PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "PagedBillPay.getSessionPage";
    String str2 = paramPagingInfo.getPagingContext().getSessionId();
    if ((str2 == null) || (str2.trim().length() == 0))
    {
      paramPagingInfo.setStatusCode(16000);
      paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "SessionId " }, "WIRE_MESSAGE"));
      FFSDebug.log(str1 + ": Null sessionId passed", 0);
      return paramPagingInfo;
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramPagingInfo = BillPayTmpHist.getSessionPage(localFFSConnectionHolder, paramPagingInfo);
      BillPayTmpHist.setSessionBoundaryValues(localFFSConnectionHolder, paramPagingInfo);
      int i = BillPayTmpHist.getTotalTransactionCount(localFFSConnectionHolder, paramPagingInfo);
      paramPagingInfo.setTotalTrans(i);
      if (paramPagingInfo.getStatusCode() != 0)
      {
        PagingInfo localPagingInfo = paramPagingInfo;
        return localPagingInfo;
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable1)
    {
      try
      {
        localFFSConnectionHolder.conn.rollback();
      }
      catch (Throwable localThrowable2) {}
      String str3 = str1 + " failed";
      FFSDebug.log(localThrowable1, str3, 0);
      paramPagingInfo.setStatusCode(28060);
      try
      {
        paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(28060, null, "PAYMENT_MESSAGE"));
      }
      catch (Exception localException) {}
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return paramPagingInfo;
  }
  
  public boolean containsInArray(String[] paramArrayOfString, String paramString)
  {
    String str = "PagedBillPay.containsInArray: ";
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
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.PagedBillPay
 * JD-Core Version:    0.7.0.1
 */