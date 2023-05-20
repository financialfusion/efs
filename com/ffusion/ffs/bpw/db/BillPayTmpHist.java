package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.ffs.bpw.interfaces.SortCriterion;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.beans.PagingContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class BillPayTmpHist
  implements FFSConst, DBConsts, BPWResource
{
  private String x1;
  private int x9;
  private String x0;
  private String x4;
  private String x6;
  private int x2;
  private String x3;
  private String xZ;
  private String x8;
  private int x5;
  private String x7;
  private String ya;
  private String yb;
  
  public String getSessionId()
  {
    return this.x1;
  }
  
  public void setSessionId(String paramString)
  {
    this.x1 = paramString;
  }
  
  public int getCursorId()
  {
    return this.x9;
  }
  
  public void setCursorId(int paramInt)
  {
    this.x9 = paramInt;
  }
  
  public String getSubmitDate()
  {
    return this.x0;
  }
  
  public void setSubmitDate(String paramString)
  {
    this.x0 = paramString;
  }
  
  public String getTransactionID()
  {
    return this.x4;
  }
  
  public void setTransactionID(String paramString)
  {
    this.x4 = paramString;
  }
  
  public String getTransactionType()
  {
    return this.x6;
  }
  
  public void setTransactionType(String paramString)
  {
    this.x6 = paramString;
  }
  
  public int getDateToPost()
  {
    return this.x2;
  }
  
  public void setDateToPost(int paramInt)
  {
    this.x2 = paramInt;
  }
  
  public String getPayeeName()
  {
    return this.x3;
  }
  
  public void setPayeeName(String paramString)
  {
    this.x3 = paramString;
  }
  
  public String getAcctDebitID()
  {
    return this.xZ;
  }
  
  public void setAcctDebitID(String paramString)
  {
    this.xZ = paramString;
  }
  
  public String getFrequency()
  {
    return this.x8;
  }
  
  public void setFrequency(String paramString)
  {
    this.x8 = paramString;
  }
  
  public int getBpwFreq()
  {
    return this.x5;
  }
  
  public void setBpwFreq(int paramInt)
  {
    this.x5 = paramInt;
  }
  
  public String getStatus()
  {
    return this.x7;
  }
  
  public void setStatus(String paramString)
  {
    this.x7 = paramString;
  }
  
  public String getAmount()
  {
    return this.ya;
  }
  
  public void setAmount(String paramString)
  {
    this.ya = paramString;
  }
  
  public String getTemplateName()
  {
    return this.yb;
  }
  
  public void setTemplateName(String paramString)
  {
    this.yb = paramString;
  }
  
  public static void insertBillPayTmpHist(FFSConnectionHolder paramFFSConnectionHolder, BillPayTmpHist paramBillPayTmpHist)
    throws FFSException
  {
    insertBillPayTmpHist(paramFFSConnectionHolder, paramBillPayTmpHist.getSessionId(), paramBillPayTmpHist.getCursorId(), paramBillPayTmpHist.getSubmitDate(), paramBillPayTmpHist.getTransactionID(), paramBillPayTmpHist.getTransactionType(), paramBillPayTmpHist.getDateToPost(), paramBillPayTmpHist.getPayeeName(), paramBillPayTmpHist.getAcctDebitID(), paramBillPayTmpHist.getFrequency(), paramBillPayTmpHist.getBpwFreq(), paramBillPayTmpHist.getStatus(), paramBillPayTmpHist.getAmount(), paramBillPayTmpHist.getTemplateName());
  }
  
  public static void insertBillPayTmpHist(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, int paramInt2, String paramString5, String paramString6, String paramString7, int paramInt3, String paramString8, String paramString9, String paramString10)
    throws FFSException
  {
    String str1 = "BillPayTmpHist.createTempHist";
    FFSDebug.log(str1 + ": start.", 1000000);
    FFSDebug.log(str1 + ": sessionId = " + paramString1 + ", cursorId = " + paramInt1 + ", transactionId = " + paramString3, 1000000);
    Object[] arrayOfObject = { paramString1, new Integer(paramInt1), paramString2, paramString3, paramString4, new Integer(paramInt2), paramString5, paramString6, paramString7, new Integer(paramInt3), paramString8, new BigDecimal(paramString9), paramString10 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_BillPayTmpHist ( SessionId, CursorId, SubmitDate, TransactionId, TransactionType, DateToPost, PayeeName, AcctDebitID, Frequency, FreqIntVal, Status, Amount,TemplateName) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)", arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = str1 + " failed";
      FFSDebug.log(localException, str2, 0);
      throw new BPWException(localException.toString());
    }
    FFSDebug.log(str1 + ": done.", 1000000);
  }
  
  public static BillPayTmpHist createInstanceFromResultSet(FFSResultSet paramFFSResultSet)
    throws Exception
  {
    BillPayTmpHist localBillPayTmpHist = new BillPayTmpHist();
    localBillPayTmpHist.setSessionId(paramFFSResultSet.getColumnString("SessionId"));
    localBillPayTmpHist.setCursorId(paramFFSResultSet.getColumnInt("CursorId"));
    localBillPayTmpHist.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
    localBillPayTmpHist.setTransactionID(paramFFSResultSet.getColumnString("TransactionId"));
    localBillPayTmpHist.setTransactionType(paramFFSResultSet.getColumnString("TransactionType"));
    localBillPayTmpHist.setDateToPost(paramFFSResultSet.getColumnInt("DateToPost"));
    localBillPayTmpHist.setPayeeName(paramFFSResultSet.getColumnString("PayeeName"));
    localBillPayTmpHist.setAcctDebitID(paramFFSResultSet.getColumnString("AcctDebitID"));
    localBillPayTmpHist.setFrequency(paramFFSResultSet.getColumnString("Frequency"));
    localBillPayTmpHist.setBpwFreq(paramFFSResultSet.getColumnInt("FreqIntVal"));
    localBillPayTmpHist.setStatus(paramFFSResultSet.getColumnString("Status"));
    BigDecimal localBigDecimal = paramFFSResultSet.getColumnBigDecimal("Amount");
    localBillPayTmpHist.setAmount(localBigDecimal.toString());
    localBillPayTmpHist.setTemplateName(paramFFSResultSet.getColumnString("TemplateName"));
    return localBillPayTmpHist;
  }
  
  public static PagingInfo getSessionPage(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "BillPayTmpHist.getSessionPage";
    FFSDebug.log(str1 + ": start.", 6);
    int i = 0;
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    String str2 = (String)localPagingContext.getMap().get("PAGE_SIZE");
    int j = Integer.parseInt(str2.trim());
    Object localObject1;
    if (j == 0)
    {
      try
      {
        PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
        localObject1 = localPropertyConfig.otherProperties.getProperty("bpw.paging.maximum.pagesize", "250");
        j = Integer.parseInt((String)localObject1);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        j = 250;
      }
      i = 1;
    }
    FFSResultSet localFFSResultSet = null;
    try
    {
      localObject1 = null;
      localObject2 = null;
      if (localPagingContext.getDirection().equals("LAST"))
      {
        long l1 = getTotalTransactionCount(paramFFSConnectionHolder, paramPagingInfo);
        long l2 = l1 / j * j;
        int k = new Long(l1 - l2).intValue();
        if (k <= 0) {
          k = j;
        } else {
          j = k;
        }
      }
      ArrayList localArrayList1 = new ArrayList();
      StringBuffer localStringBuffer = new StringBuffer();
      ArrayList localArrayList2 = new ArrayList();
      jdMethod_int(paramPagingInfo, localStringBuffer, localArrayList2);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), localArrayList2.toArray());
      Object localObject3;
      while ((localFFSResultSet.getNextRow() == true) && (localArrayList1.size() < j))
      {
        localObject3 = createInstanceFromResultSet(localFFSResultSet);
        localObject4 = null;
        RecPmtInfo localRecPmtInfo1 = null;
        PaymentBatchInfo localPaymentBatchInfo = null;
        String str3 = ((BillPayTmpHist)localObject3).getTransactionType();
        String str4;
        Object localObject5;
        if ((str3.equals("PMT")) || (str3.equals("TEMPLATE")))
        {
          str4 = ((BillPayTmpHist)localObject3).getTransactionID();
          localObject5 = PmtInstruction.getPmtInstr(str4, paramFFSConnectionHolder);
          if (localObject5 == null) {
            continue;
          }
          localObject4 = ((PmtInstruction)localObject5).getPmtInfo();
        }
        else if (str3.equals("RECPMT") == true)
        {
          str4 = ((BillPayTmpHist)localObject3).getTransactionID();
          localObject5 = RecPmtInstruction.getRecPmtInstr(str4, paramFFSConnectionHolder);
          if (localObject5 == null) {
            continue;
          }
          RecPmtInfo localRecPmtInfo2 = ((RecPmtInstruction)localObject5).getRecPmtInfo();
          localObject4 = new PmtInfo("", localRecPmtInfo2.CustomerID, localRecPmtInfo2.FIID, localRecPmtInfo2.PayeeID, localRecPmtInfo2.PayAcct, localRecPmtInfo2.PayeeListID, ((BillPayTmpHist)localObject3).getAmount(), localRecPmtInfo2.BankID, localRecPmtInfo2.AcctDebitID, localRecPmtInfo2.AcctDebitType, localRecPmtInfo2.Memo, localRecPmtInfo2.ExtdPmtInfo, localRecPmtInfo2.DateCreate, localRecPmtInfo2.CurDef, "WILLPROCESSON", ((BillPayTmpHist)localObject3).getDateToPost(), "Recurring", str4, null, null, localRecPmtInfo2.submittedBy);
          RecSrvrTrans localRecSrvrTrans = RecSrvrTrans.getRecSrvrTransById(str4, paramFFSConnectionHolder);
          ((PmtInfo)localObject4).StatusDt = localRecSrvrTrans.getSubmitdate();
          int m = SmartCalendar.getPayday(localRecPmtInfo2.FIID, ((BillPayTmpHist)localObject3).getDateToPost() / 100);
          ((PmtInfo)localObject4).AdjustedDueDt = String.valueOf(m);
        }
        else if ((str3.equals("RECMODEL")) || (str3.equals("RECTEMPLATE")))
        {
          str4 = ((BillPayTmpHist)localObject3).getTransactionID();
          localObject5 = RecPmtInstruction.getRecPmtInstr(str4, paramFFSConnectionHolder);
          if (localObject5 == null) {
            continue;
          }
          localRecPmtInfo1 = ((RecPmtInstruction)localObject5).getRecPmtInfo();
          localRecPmtInfo1.StartDate = ((BillPayTmpHist)localObject3).getDateToPost();
          a(localRecPmtInfo1, paramFFSConnectionHolder);
        }
        else if (str3.equals("PMTBATCH"))
        {
          str4 = ((BillPayTmpHist)localObject3).getTransactionID();
          localPaymentBatchInfo = PaymentBatch.getPaymentBatch(paramFFSConnectionHolder, str4);
          if (localPaymentBatchInfo != null) {}
        }
        else
        {
          FFSDebug.log(str1 + ": Unknown TransactionType for cursorID = " + ((BillPayTmpHist)localObject3).getCursorId(), 6);
        }
        if (localObject4 != null)
        {
          ((PmtInfo)localObject4).payeeInfo = Payee.findPayeeByID(((PmtInfo)localObject4).PayeeID, paramFFSConnectionHolder);
          if (((PmtInfo)localObject4).payeeInfo.PayeeType == 0) {
            ((PmtInfo)localObject4).payeeInfo = Payee.getGlobalPayee(paramFFSConnectionHolder, ((PmtInfo)localObject4).payeeInfo.PayeeID);
          }
          ((PmtInfo)localObject4).cursorID = ((BillPayTmpHist)localObject3).getCursorId();
          ((PmtInfo)localObject4).setRecFrequency(((BillPayTmpHist)localObject3).getFrequency());
          localArrayList1.add(localObject4);
        }
        else if (localRecPmtInfo1 != null)
        {
          localRecPmtInfo1.cursorID = ((BillPayTmpHist)localObject3).getCursorId();
          localArrayList1.add(localRecPmtInfo1);
        }
        else if (localPaymentBatchInfo != null)
        {
          localArrayList1.add(localPaymentBatchInfo);
        }
        if ((localObject4 != null) || (localRecPmtInfo1 != null) || (localPaymentBatchInfo != null))
        {
          if (localArrayList1.size() == 1) {
            localObject1 = localObject3;
          }
          localObject2 = localObject3;
        }
      }
      Object localObject4 = localPagingContext.getDirection();
      if (((((String)localObject4).equals("PREVIOUS") == true) || (((String)localObject4).equals("LAST") == true)) && (localArrayList1.size() > 0)) {
        localObject3 = new ArrayList();
      }
      while (!localArrayList1.isEmpty())
      {
        ((ArrayList)localObject3).add(0, localArrayList1.remove(0));
        continue;
        localObject3 = localArrayList1;
      }
      FFSDebug.log(str1 + ": Pmts retrieved = " + ((ArrayList)localObject3).size(), 6);
      paramPagingInfo.setPagingResult((ArrayList)localObject3);
      setPageBoundaryValues(paramPagingInfo, (BillPayTmpHist)localObject1, (BillPayTmpHist)localObject2);
      if ((i == 1) && (((ArrayList)localObject3).size() >= j) && (localFFSResultSet.getNextRow() == true))
      {
        paramPagingInfo.setStatusCode(28010);
        paramPagingInfo.setStatusMsg("Server can't return all records requested because the number of records server found is more than the server maximum page size.");
      }
    }
    catch (Exception localException)
    {
      Object localObject2 = str1 + " failed";
      FFSDebug.log(localException, (String)localObject2, 0);
      throw new FFSException(localException, (String)localObject2);
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
      catch (Throwable localThrowable) {}
    }
    FFSDebug.log(str1 + ": done.", 6);
    return paramPagingInfo;
  }
  
  public static int getTotalTransactionCount(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws BPWException
  {
    String str1 = "BillPayTmpHist.getTotalTransactionCount";
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    String str2 = localPagingContext.getSessionId();
    FFSDebug.log(str1 + ": start. sessionId = " + str2, 6);
    int i = 0;
    FFSResultSet localFFSResultSet = null;
    try
    {
      String str3 = "SELECT COUNT(*) FROM BPW_BillPayTmpHist WHERE SessionId = ?";
      Object[] arrayOfObject = { str2 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      if (localFFSResultSet.getNextRow() == true) {
        i = localFFSResultSet.getColumnInt(1);
      }
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
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
      catch (Throwable localThrowable) {}
    }
    FFSDebug.log(str1 + ": end. rowCount = " + i, 6);
    return i;
  }
  
  public static void setPageBoundaryValues(PagingInfo paramPagingInfo, BillPayTmpHist paramBillPayTmpHist1, BillPayTmpHist paramBillPayTmpHist2)
  {
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    ArrayList localArrayList = localPagingContext.getSortCriteriaList();
    SortCriterion localSortCriterion = (SortCriterion)localArrayList.get(0);
    String str1 = localSortCriterion.getName();
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    if ((paramBillPayTmpHist1 != null) && (paramBillPayTmpHist2 != null))
    {
      str2 = a(str1, paramBillPayTmpHist1);
      str3 = String.valueOf(paramBillPayTmpHist1.getCursorId());
      str4 = a(str1, paramBillPayTmpHist2);
      str5 = String.valueOf(paramBillPayTmpHist2.getCursorId());
    }
    String str6 = localPagingContext.getDirection();
    boolean bool = localSortCriterion.isAscending();
    if (((bool == true) && (str6.equals("FIRST"))) || ((bool == true) && (str6.equals("NEXT"))) || ((!bool) && (str6.equals("PREVIOUS"))) || ((!bool) && (str6.equals("LAST"))))
    {
      a(paramPagingInfo, "SORT_VALUE_MIN_", str1, str2, str3);
      a(paramPagingInfo, "SORT_VALUE_MAX_", str1, str4, str5);
    }
    else if (((!bool) && (str6.equals("FIRST"))) || ((!bool) && (str6.equals("NEXT"))) || ((bool == true) && (str6.equals("PREVIOUS"))) || ((bool == true) && (str6.equals("LAST"))))
    {
      a(paramPagingInfo, "SORT_VALUE_MIN_", str1, str4, str5);
      a(paramPagingInfo, "SORT_VALUE_MAX_", str1, str2, str3);
    }
  }
  
  public static void setSessionBoundaryValues(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "BillPayTmpHist.getBoundaryValues";
    FFSDebug.log(str1 + ": start.", 6);
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    ArrayList localArrayList1 = localPagingContext.getSortCriteriaList();
    SortCriterion localSortCriterion = (SortCriterion)localArrayList1.get(0);
    String str2 = localSortCriterion.getName();
    FFSResultSet localFFSResultSet = null;
    String str5;
    StringBuffer localStringBuffer;
    ArrayList localArrayList2;
    boolean bool;
    BillPayTmpHist localBillPayTmpHist;
    try
    {
      String str3 = null;
      str5 = null;
      localStringBuffer = new StringBuffer();
      localArrayList2 = new ArrayList();
      bool = true;
      jdMethod_if(paramPagingInfo, localStringBuffer, localArrayList2, bool);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), localArrayList2.toArray());
      if (localFFSResultSet.getNextRow() == true)
      {
        localBillPayTmpHist = createInstanceFromResultSet(localFFSResultSet);
        str3 = a(str2, localBillPayTmpHist);
        str5 = String.valueOf(localBillPayTmpHist.getCursorId());
      }
      a(paramPagingInfo, "LOWER_BOUND_", str2, str3, str5);
    }
    catch (Exception localException1)
    {
      str5 = str1 + " failed";
      FFSDebug.log(localException1, str5, 0);
      throw new FFSException(localException1, str5);
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
      catch (Throwable localThrowable1) {}
    }
    localFFSResultSet = null;
    try
    {
      String str4 = null;
      str5 = null;
      localStringBuffer = new StringBuffer();
      localArrayList2 = new ArrayList();
      bool = false;
      jdMethod_if(paramPagingInfo, localStringBuffer, localArrayList2, bool);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), localArrayList2.toArray());
      if (localFFSResultSet.getNextRow() == true)
      {
        localBillPayTmpHist = createInstanceFromResultSet(localFFSResultSet);
        str4 = a(str2, localBillPayTmpHist);
        str5 = String.valueOf(localBillPayTmpHist.getCursorId());
      }
      a(paramPagingInfo, "UPPER_BOUND_", str2, str4, str5);
    }
    catch (Exception localException2)
    {
      str5 = str1 + " failed";
      FFSDebug.log(localException2, str5, 0);
      throw new FFSException(localException2, str5);
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
    FFSDebug.log(str1 + ": end.", 6);
  }
  
  private static void a(PagingInfo paramPagingInfo, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    String str = "BillPayTmpHist.setBoundary";
    FFSDebug.log(str + ": " + paramString1 + paramString2 + " = " + paramString3, 6);
    FFSDebug.log(str + ": " + paramString1 + "TransactionIndex" + " = " + paramString4, 6);
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    HashMap localHashMap = localPagingContext.getMap();
    localHashMap.put(paramString1 + paramString2, paramString3);
    localHashMap.put(paramString1 + "TransactionIndex", paramString4);
  }
  
  private static void jdMethod_int(PagingInfo paramPagingInfo, StringBuffer paramStringBuffer, ArrayList paramArrayList)
  {
    String str1 = "BillPayTmpHist.createPageRetrievalSql";
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    ArrayList localArrayList = localPagingContext.getSortCriteriaList();
    SortCriterion localSortCriterion = (SortCriterion)localArrayList.get(0);
    String str2 = localSortCriterion.getName();
    String str3 = aB(str2);
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    HashMap localHashMap = localPagingContext.getMap();
    String str8 = localPagingContext.getDirection();
    boolean bool = localSortCriterion.isAscending();
    if (str8.equals("FIRST") == true)
    {
      str4 = null;
      str5 = null;
      str6 = null;
      if (bool == true) {
        str7 = "ASC";
      } else {
        str7 = "DESC";
      }
    }
    else if (str8.equals("LAST") == true)
    {
      str4 = null;
      str5 = null;
      str6 = null;
      if (bool == true) {
        str7 = "DESC";
      } else {
        str7 = "ASC";
      }
    }
    else if (((bool == true) && (str8.equals("PREVIOUS") == true)) || ((!bool) && (str8.equals("NEXT") == true)))
    {
      str4 = (String)localHashMap.get("SORT_VALUE_MIN_" + str2);
      str5 = (String)localHashMap.get("SORT_VALUE_MIN_TransactionIndex");
      str6 = "<";
      str7 = "DESC";
    }
    else
    {
      str4 = (String)localHashMap.get("SORT_VALUE_MAX_" + str2);
      str5 = (String)localHashMap.get("SORT_VALUE_MAX_TransactionIndex");
      str6 = ">";
      str7 = "ASC";
    }
    paramStringBuffer.append("SELECT * FROM BPW_BillPayTmpHist");
    paramStringBuffer.append(" WHERE SessionId = ?");
    paramArrayList.add(localPagingContext.getSessionId());
    if ((str4 != null) && (str5 != null))
    {
      paramStringBuffer.append(" AND");
      paramStringBuffer.append(" (");
      paramStringBuffer.append(" (" + str3 + " " + str6 + " ?)");
      a(paramArrayList, str3, str4);
      paramStringBuffer.append(" OR");
      paramStringBuffer.append(" (");
      paramStringBuffer.append(" (" + str3 + " = ?)");
      a(paramArrayList, str3, str4);
      paramStringBuffer.append(" AND");
      paramStringBuffer.append(" (CursorId " + str6 + " ?)");
      a(paramArrayList, "CursorId", str5);
      paramStringBuffer.append(" )");
      paramStringBuffer.append(" )");
    }
    paramStringBuffer.append(" ORDER BY " + str3 + " " + str7);
    paramStringBuffer.append(", CursorId " + str7);
    if (FFSDebug.checkLogLevel(6) == true)
    {
      FFSDebug.log(str1 + ": Resulting statement: " + paramStringBuffer.toString(), 6);
      for (int i = 0; i < paramArrayList.size(); i++) {
        FFSDebug.log("Arg[" + i + "] = " + paramArrayList.get(i).toString(), 6);
      }
    }
  }
  
  private static void jdMethod_if(PagingInfo paramPagingInfo, StringBuffer paramStringBuffer, ArrayList paramArrayList, boolean paramBoolean)
  {
    String str1 = "BillPayTmpHist.createSessionDataRetrievalSql";
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    ArrayList localArrayList = localPagingContext.getSortCriteriaList();
    SortCriterion localSortCriterion = (SortCriterion)localArrayList.get(0);
    String str2 = localSortCriterion.getName();
    String str3 = aB(str2);
    paramStringBuffer.append("SELECT * FROM BPW_BillPayTmpHist");
    paramStringBuffer.append(" WHERE SessionId = ? ");
    paramArrayList.add(localPagingContext.getSessionId());
    String str4 = null;
    if (paramBoolean == true) {
      str4 = "ASC";
    } else {
      str4 = "DESC";
    }
    paramStringBuffer.append(" ORDER BY " + str3 + " " + str4);
    paramStringBuffer.append(", CursorId " + str4);
    if (FFSDebug.checkLogLevel(6) == true)
    {
      FFSDebug.log(str1 + ": Resulting statement: " + paramStringBuffer.toString(), 6);
      for (int i = 0; i < paramArrayList.size(); i++) {
        FFSDebug.log("Arg[" + i + "] = " + paramArrayList.get(i).toString(), 6);
      }
    }
  }
  
  private static void a(ArrayList paramArrayList, String paramString1, String paramString2)
  {
    if ((paramString1.equalsIgnoreCase("CursorId") == true) || (paramString1.equalsIgnoreCase("DateToPost") == true) || (paramString1.equalsIgnoreCase("FreqIntVal") == true)) {
      paramArrayList.add(new Integer(paramString2));
    } else if (paramString1.equalsIgnoreCase("Amount") == true) {
      paramArrayList.add(new BigDecimal(paramString2));
    } else {
      paramArrayList.add(paramString2);
    }
  }
  
  private static String aB(String paramString)
  {
    String str = null;
    if (paramString.equalsIgnoreCase("DateToPost") == true) {
      str = "DateToPost";
    } else if (paramString.equalsIgnoreCase("PayeeName") == true) {
      str = "PayeeName";
    } else if (paramString.equalsIgnoreCase("AcctDebitId") == true) {
      str = "AcctDebitID";
    } else if (paramString.equalsIgnoreCase("Frequency") == true) {
      str = "Frequency";
    } else if (paramString.equalsIgnoreCase("Status") == true) {
      str = "Status";
    } else if (paramString.equalsIgnoreCase("Amount") == true) {
      str = "Amount";
    } else if (paramString.equalsIgnoreCase("CursorId") == true) {
      str = "CursorId";
    } else if (paramString.equalsIgnoreCase("TemplateName") == true) {
      str = "TemplateName";
    } else if (paramString.equalsIgnoreCase("PaymentType") == true) {
      str = "TransactionType";
    }
    return str;
  }
  
  private static String a(String paramString, BillPayTmpHist paramBillPayTmpHist)
  {
    String str = null;
    if (paramString.equalsIgnoreCase("DateToPost") == true)
    {
      str = String.valueOf(paramBillPayTmpHist.getDateToPost());
    }
    else if (paramString.equalsIgnoreCase("PayeeName") == true)
    {
      str = paramBillPayTmpHist.getPayeeName();
    }
    else if (paramString.equalsIgnoreCase("AcctDebitId") == true)
    {
      str = paramBillPayTmpHist.getAcctDebitID();
    }
    else if (paramString.equalsIgnoreCase("Frequency") == true)
    {
      str = paramBillPayTmpHist.getFrequency();
    }
    else if (paramString.equalsIgnoreCase("Status") == true)
    {
      str = paramBillPayTmpHist.getStatus();
    }
    else if (paramString.equalsIgnoreCase("Amount") == true)
    {
      BigDecimal localBigDecimal1 = new BigDecimal(paramBillPayTmpHist.getAmount());
      BigDecimal localBigDecimal2 = localBigDecimal1.setScale(2, 5);
      str = localBigDecimal2.toString();
    }
    else if (paramString.equalsIgnoreCase("CursorId") == true)
    {
      str = String.valueOf(paramBillPayTmpHist.getCursorId());
    }
    else if (paramString.equalsIgnoreCase("TemplateName") == true)
    {
      str = paramBillPayTmpHist.getTemplateName();
    }
    else if (paramString.equalsIgnoreCase("PaymentType") == true)
    {
      str = paramBillPayTmpHist.getTransactionType();
    }
    return str;
  }
  
  private static void a(RecPmtInfo paramRecPmtInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "RECPMTTRN";
    ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(paramRecPmtInfo.FIID, str1, paramRecPmtInfo.RecSrvrTID, paramFFSConnectionHolder);
    if (localScheduleInfo != null)
    {
      int i = localScheduleInfo.CurInstanceNum;
      int j = localScheduleInfo.InstanceCount;
      String str2 = Integer.toString(paramRecPmtInfo.StartDate);
      if (str2.length() < 10) {
        str2 = str2 + "00";
      }
      int k = Integer.parseInt(str2);
      int m = localScheduleInfo.NextInstanceDate;
      while (((i < j) || (localScheduleInfo.Perpetual == 1)) && (m < k))
      {
        m = ScheduleInfo.computeFutureDate(localScheduleInfo.NextInstanceDate, localScheduleInfo.Frequency, i);
        i++;
      }
      paramRecPmtInfo.InstanceCount = (j - i + 1);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.BillPayTmpHist
 * JD-Core Version:    0.7.0.1
 */