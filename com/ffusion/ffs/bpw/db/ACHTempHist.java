package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHHistInfo;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.SortCriterion;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.beans.PagingContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;

public class ACHTempHist
  implements FFSConst, DBConsts, BPWResource
{
  public static int createTempHist(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, int paramInt, String paramString2, ACHHistInfo paramACHHistInfo)
    throws FFSException
  {
    String str1 = "ACHTempHist.createTempHist ";
    FFSDebug.log(str1 + " : start", 6);
    paramACHHistInfo.setStatusCode(0);
    paramACHHistInfo.setStatusMsg("Success");
    if (paramACHHistInfo == null)
    {
      paramACHHistInfo.setStatusCode(16000);
      paramACHHistInfo.setStatusMsg("ACHHistInfo is null");
      return 0;
    }
    ACHBatchInfo localACHBatchInfo = (ACHBatchInfo)paramACHHistInfo.getObjInfo();
    if (localACHBatchInfo == null)
    {
      paramACHHistInfo.setStatusCode(16000);
      paramACHHistInfo.setStatusMsg("ACHHistInfo.ObjInfo is null");
      return 0;
    }
    FFSDebug.log(str1 + " , sessionId = " + paramString1 + ", cursorId = " + paramInt, 6);
    String str2 = null;
    if (localACHBatchInfo.getMemo() != null) {
      str2 = (String)localACHBatchInfo.getMemo().get("NAME");
    }
    if (str2 == null) {
      str2 = localACHBatchInfo.getBatchId();
    }
    String str3 = null;
    String str4 = localACHBatchInfo.getDueDate().substring(0, 8);
    if (localACHBatchInfo.getBatchType().compareTo("Current") == 0)
    {
      str3 = "NONE";
    }
    else if (localACHBatchInfo.getBatchType().compareTo("Repetitive") == 0)
    {
      str3 = ((RecACHBatchInfo)localACHBatchInfo).getFrequency();
    }
    else
    {
      localObject = localACHBatchInfo.getRecBatchId();
      if (localObject == null) {
        localObject = localACHBatchInfo.getBatchId();
      }
      RecACHBatchInfo localRecACHBatchInfo = new RecACHBatchInfo();
      localRecACHBatchInfo.setBatchId((String)localObject);
      localRecACHBatchInfo = (RecACHBatchInfo)ACHBatch.getACHBatch(localRecACHBatchInfo, paramFFSConnectionHolder, true, false, false, false, false);
      str3 = localRecACHBatchInfo.getFrequency();
      FFSDebug.log(str1 + "Frequency: " + str3, 6);
    }
    Object localObject = { paramString1, new Integer(paramInt), localACHBatchInfo.getBatchId(), paramString2, new String(String.valueOf(new Integer(str4))), str2, localACHBatchInfo.getCompAchId(), new Integer(localACHBatchInfo.getNonOffBatchEntryCount()), str3, localACHBatchInfo.getBatchStatus(), new BigDecimal(localACHBatchInfo.getNonOffBatchCreditSum()), new BigDecimal(localACHBatchInfo.getNonOffBatchDebitSum()), DBUtil.getCurrentLogDate() };
    FFSDebug.log(str1 + "args " + Arrays.asList((Object[])localObject), 6);
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_ACHTempHist (SessionId, CursorId, TransactionId, TransactionType,DueDate, BatchName, CompACHId, NumberOfEntries, Frequency, Status, TotalCredit,TotalDebit, SubmitDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)", (Object[])localObject);
    }
    catch (Exception localException)
    {
      String str5 = str1 + " failed:";
      FFSDebug.log(str5 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log(str1 + " done", 6);
    return i;
  }
  
  public static void getBounds(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "ACHTempHist.getBounds";
    FFSDebug.log(str1 + " : start", 6);
    getBound(paramPagingInfo, "SORT_VALUE_MAX_", null);
    getBound(paramPagingInfo, "SORT_VALUE_MIN_", null);
    FFSResultSet localFFSResultSet = null;
    ACHHistInfo localACHHistInfo = null;
    try
    {
      ArrayList localArrayList1 = paramPagingInfo.getPagingContext().getSortCriteriaList();
      localObject1 = null;
      if ((localArrayList1 == null) || (localArrayList1.size() == 0))
      {
        localObject1 = new SortCriterion();
        ((SortCriterion)localObject1).setName("DueDate");
        ((SortCriterion)localObject1).setAscending();
      }
      else
      {
        localObject1 = (SortCriterion)localArrayList1.get(0);
      }
      boolean bool1 = ((SortCriterion)localObject1).isAscending();
      ArrayList localArrayList2 = new ArrayList();
      ((SortCriterion)localObject1).setAscending();
      boolean bool2 = true;
      String str2 = a(paramPagingInfo, localArrayList2, bool2);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, localArrayList2.toArray());
      if (localFFSResultSet.getNextRow())
      {
        localACHHistInfo = jdMethod_if(paramFFSConnectionHolder, localFFSResultSet);
        getBound(paramPagingInfo, "LOWER_BOUND_", localACHHistInfo);
      }
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
      localArrayList2 = new ArrayList();
      ((SortCriterion)localObject1).setDescending();
      str2 = a(paramPagingInfo, localArrayList2, bool2);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, localArrayList2.toArray());
      if (localFFSResultSet.getNextRow())
      {
        localACHHistInfo = jdMethod_if(paramFFSConnectionHolder, localFFSResultSet);
        getBound(paramPagingInfo, "UPPER_BOUND_", localACHHistInfo);
      }
      if (bool1) {
        ((SortCriterion)localObject1).setAscending();
      } else {
        ((SortCriterion)localObject1).setDescending();
      }
      paramPagingInfo.setStatusCode(0);
      paramPagingInfo.setStatusMsg("Success");
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
  
  public static ArrayList getSessionPage(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "ACHTempHist.getSessionPage";
    FFSDebug.log(str1 + " : start", 6);
    FFSResultSet localFFSResultSet = null;
    Object localObject1 = null;
    ACHHistInfo localACHHistInfo = null;
    try
    {
      int i = 0;
      int j = 0;
      String str3 = (String)paramPagingInfo.getPagingContext().getMap().get("PAGE_SIZE");
      if ((str3 != null) && (str3.length() != 0)) {
        j = Integer.parseInt(str3);
      }
      i = j;
      if (j == 0)
      {
        localObject2 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
        str3 = ((PropertyConfig)localObject2).otherProperties.getProperty("bpw.paging.maximum.pagesize", "250");
        i = Integer.parseInt(str3);
      }
      if (i >= 1)
      {
        localObject2 = new ArrayList();
        int k = 0;
        ArrayList localArrayList = new ArrayList();
        boolean bool = false;
        String str4 = a(paramPagingInfo, localArrayList, bool);
        Object[] arrayOfObject = localArrayList.toArray();
        FFSDebug.log(str1 + " sql stmt = " + str4, 6);
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str4, arrayOfObject);
        while (localFFSResultSet.getNextRow())
        {
          localACHHistInfo = jdMethod_if(paramFFSConnectionHolder, localFFSResultSet);
          if (localACHHistInfo.getStatusCode() == 0)
          {
            ((ArrayList)localObject2).add(localACHHistInfo);
            k++;
            if (k >= i) {
              break;
            }
          }
        }
        localObject1 = localObject2;
        String str5 = paramPagingInfo.getPagingContext().getDirection();
        if (str5.equals("PREVIOUS"))
        {
          localObject1 = new ArrayList();
          while (!((ArrayList)localObject2).isEmpty()) {
            ((ArrayList)localObject1).add(0, (ACHHistInfo)((ArrayList)localObject2).remove(0));
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
  
  protected static ACHHistInfo jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, FFSResultSet paramFFSResultSet)
    throws FFSException
  {
    String str1 = "ACHTempHist.getACHHistInfo";
    String str2 = paramFFSResultSet.getColumnString("TransactionId");
    String str3 = paramFFSResultSet.getColumnString("TransactionType");
    String str4 = paramFFSResultSet.getColumnString("CursorId");
    int i = paramFFSResultSet.getColumnInt("DueDate");
    String str5 = paramFFSResultSet.getColumnString("Frequency");
    boolean bool = false;
    if (str3.equals("ACH"))
    {
      bool = false;
    }
    else if (str3.equals("RECACH"))
    {
      bool = true;
    }
    else
    {
      localObject1 = str1 + " : Invalid History " + "Transaction Type : " + "TransactionId = " + str2 + "TransactionType = " + str3;
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException((String)localObject1);
    }
    Object localObject1 = ACHBatch.getACHBatchById(paramFFSConnectionHolder, str2, bool);
    String str6 = ((ACHBatchInfo)localObject1).getBatchStatus();
    String str7 = ((ACHBatchInfo)localObject1).getLogId();
    long l1 = ((ACHBatchInfo)localObject1).getNonOffBatchCreditSum();
    long l2 = ((ACHBatchInfo)localObject1).getNonOffBatchDebitSum();
    int j = ((ACHBatchInfo)localObject1).getNonOffBatchEntryCount();
    ACHRecordInfo localACHRecordInfo1 = ((ACHBatchInfo)localObject1).getBatchHeader();
    ACHRecordInfo localACHRecordInfo2 = ((ACHBatchInfo)localObject1).getBatchControl();
    ACHRecordInfo[] arrayOfACHRecordInfo = ((ACHBatchInfo)localObject1).getRecords();
    Hashtable localHashtable = ((ACHBatchInfo)localObject1).getMemo();
    int k = ((ACHBatchInfo)localObject1).getBatchType().compareTo("Recurring") == 0 ? 1 : 0;
    if (k == 1)
    {
      localObject2 = ((ACHBatchInfo)localObject1).getRecBatchId();
      RecACHBatchInfo localRecACHBatchInfo = new RecACHBatchInfo();
      localRecACHBatchInfo.setBatchId((String)localObject2);
      localObject1 = ACHBatch.getACHBatch(localRecACHBatchInfo, paramFFSConnectionHolder, true, false, false, false, false);
      ((ACHBatchInfo)localObject1).setBatchStatus(str6);
      ((ACHBatchInfo)localObject1).setLogId(str7);
      if ((str6.compareTo("POSTEDON") == 0) || (str6.compareTo("PROCESSEDON") == 0) || (str6.compareTo("LIMIT_CHECK_FAILED") == 0) || (str6.compareTo("LIMIT_REVERT_FAILED") == 0) || (str6.compareTo("APPROVAL_FAILED") == 0) || (str6.compareTo("APPROVAL_NOT_ALLOWED") == 0) || (str6.compareTo("FAILEDON") == 0))
      {
        ((ACHBatchInfo)localObject1).setBatchId(str2);
        ((ACHBatchInfo)localObject1).setNonOffBatchCreditSum(l1);
        ((ACHBatchInfo)localObject1).setNonOffBatchDebitSum(l2);
        ((ACHBatchInfo)localObject1).setNonOffBatchEntryCount(j);
        ((ACHBatchInfo)localObject1).setBatchHeader(localACHRecordInfo1);
        ((ACHBatchInfo)localObject1).setBatchControl(localACHRecordInfo2);
        ((ACHBatchInfo)localObject1).setRecords(arrayOfACHRecordInfo);
        ((ACHBatchInfo)localObject1).setMemo(localHashtable);
      }
    }
    if ((bool == true) || (k != 0)) {
      ((ACHBatchInfo)localObject1).setDueDate("" + BPWUtil.getDateDBFormat(new StringBuffer().append("").append(i).toString()));
    }
    Object localObject2 = new ACHHistInfo();
    ((ACHHistInfo)localObject2).setCursorId(str4);
    ((ACHHistInfo)localObject2).setObjInfo(localObject1);
    ((ACHHistInfo)localObject2).setObjType(0);
    ((ACHHistInfo)localObject2).setStatusCode(((ACHBatchInfo)localObject1).getStatusCode());
    ((ACHHistInfo)localObject2).setStatusMsg(((ACHBatchInfo)localObject1).getStatusMsg());
    return localObject2;
  }
  
  protected static String a(PagingInfo paramPagingInfo, ArrayList paramArrayList, boolean paramBoolean)
  {
    String str1 = "ACHTempHist.createSortingSql: ";
    FFSDebug.log(str1 + " start", 6);
    String str2 = "BPW_ACHTempHist";
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    HashMap localHashMap = localPagingContext.getMap();
    ArrayList localArrayList = localPagingContext.getSortCriteriaList();
    SortCriterion localSortCriterion = null;
    if ((localArrayList == null) || (localArrayList.size() == 0))
    {
      localSortCriterion = new SortCriterion();
      localSortCriterion.setName("DueDate");
      localSortCriterion.setAscending();
    }
    else
    {
      localSortCriterion = (SortCriterion)localArrayList.get(0);
    }
    boolean bool = localSortCriterion.isAscending();
    String str3 = localSortCriterion.getName();
    String str4 = localPagingContext.getDirection();
    String str5 = null;
    String str6 = null;
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
    else if (str4.equals("PREVIOUS")) {
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
    FFSDebug.log(str1 + "Sorting criteria: " + str3 + ", " + str4 + ", " + str5, 6);
    String str7 = "<";
    if ((bool) && (str4.equals("NEXT"))) {
      str7 = ">";
    } else if ((!bool) && (str4.equals("PREVIOUS"))) {
      str7 = ">";
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT * FROM ");
    localStringBuffer.append(str2);
    localStringBuffer.append(" WHERE ");
    localStringBuffer.append(" SessionId = ? ");
    paramArrayList.add(localPagingContext.getSessionId());
    if ((str5 != null) && (str6 != null) && (!paramBoolean))
    {
      localStringBuffer.append(" AND (");
      localStringBuffer.append(str3);
      localStringBuffer.append(str7);
      localStringBuffer.append("?");
      localStringBuffer.append(" OR (");
      localStringBuffer.append(str3);
      localStringBuffer.append("=");
      localStringBuffer.append("? AND CursorId");
      localStringBuffer.append(str7);
      localStringBuffer.append("?))");
      if (str3.equalsIgnoreCase("TotalCredit"))
      {
        paramArrayList.add(new BigDecimal(str5));
        paramArrayList.add(new BigDecimal(str5));
      }
      else if (str3.equalsIgnoreCase("TotalDebit"))
      {
        paramArrayList.add(new BigDecimal(str5));
        paramArrayList.add(new BigDecimal(str5));
      }
      else if (str3.equalsIgnoreCase("NumberOfEntries"))
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
    return str8;
  }
  
  private static String a(String paramString, ACHHistInfo paramACHHistInfo)
  {
    String str1 = "ACHTempHist.getColumnValue ";
    FFSDebug.log(str1 + "start, columnName =" + paramString, 6);
    String str2 = null;
    if (paramString == null)
    {
      FFSDebug.log(str1 + "error, columnName is null !", 6);
      return null;
    }
    ACHBatchInfo localACHBatchInfo = (ACHBatchInfo)paramACHHistInfo.getObjInfo();
    int i = 0;
    if ((localACHBatchInfo instanceof RecACHBatchInfo)) {
      i = 1;
    }
    if (paramString.equalsIgnoreCase("CursorId")) {
      str2 = paramACHHistInfo.getCursorId();
    } else if (paramString.equalsIgnoreCase("TransactionId")) {
      str2 = localACHBatchInfo.getBatchId();
    } else if (paramString.equalsIgnoreCase("TransactionType"))
    {
      if (i == 1) {
        str2 = "RECACH";
      } else {
        str2 = "ACH";
      }
    }
    else if (paramString.equalsIgnoreCase("DueDate")) {
      str2 = localACHBatchInfo.getDueDate().substring(0, 8);
    } else if (paramString.equalsIgnoreCase("BatchName"))
    {
      if (localACHBatchInfo.getMemo() != null) {
        str2 = (String)localACHBatchInfo.getMemo().get("NAME");
      }
    }
    else if (paramString.equalsIgnoreCase("CompACHId")) {
      str2 = localACHBatchInfo.getCompAchId();
    } else if (paramString.equalsIgnoreCase("NumberOfEntries")) {
      str2 = "" + localACHBatchInfo.getNonOffBatchEntryCount();
    } else if (paramString.equalsIgnoreCase("Frequency"))
    {
      if (i == 1) {
        str2 = ((RecACHBatchInfo)localACHBatchInfo).getFrequency();
      } else {
        str2 = "NONE";
      }
    }
    else if (paramString.equalsIgnoreCase("Status")) {
      str2 = localACHBatchInfo.getBatchStatus();
    } else if (paramString.equalsIgnoreCase("TotalCredit")) {
      str2 = "" + localACHBatchInfo.getNonOffBatchCreditSum();
    } else if (paramString.equalsIgnoreCase("TotalDebit")) {
      str2 = "" + localACHBatchInfo.getNonOffBatchDebitSum();
    }
    FFSDebug.log(str1 + "end, value =" + str2, 6);
    return str2;
  }
  
  public static void getBound(PagingInfo paramPagingInfo, String paramString, ACHHistInfo paramACHHistInfo)
    throws FFSException
  {
    String str1 = "ACHTempHist.getBound";
    FFSDebug.log(str1 + " : start", 6);
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    ArrayList localArrayList1 = paramPagingInfo.getPagingResult();
    ArrayList localArrayList2 = paramPagingInfo.getPagingContext().getSortCriteriaList();
    SortCriterion localSortCriterion = null;
    if ((localArrayList2 == null) || (localArrayList2.size() == 0))
    {
      localSortCriterion = new SortCriterion();
      localSortCriterion.setName("DueDate");
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
          paramACHHistInfo = (ACHHistInfo)localArrayList1.get(0);
        } else {
          paramACHHistInfo = (ACHHistInfo)localArrayList1.get(localArrayList1.size() - 1);
        }
      }
      else if (paramString.equals("SORT_VALUE_MAX_")) {
        if (bool) {
          paramACHHistInfo = (ACHHistInfo)localArrayList1.get(localArrayList1.size() - 1);
        } else {
          paramACHHistInfo = (ACHHistInfo)localArrayList1.get(0);
        }
      }
    }
    if (paramACHHistInfo != null)
    {
      String str3 = a(str2, paramACHHistInfo);
      localPagingContext.getMap().put(paramString + str2, str3);
      localPagingContext.getMap().put(paramString + "TransactionIndex", paramACHHistInfo.getCursorId());
      FFSDebug.log(str1 + "done. Key: " + paramString + ", name: " + str2 + ", value: " + str3, 6);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ACHTempHist
 * JD-Core Version:    0.7.0.1
 */