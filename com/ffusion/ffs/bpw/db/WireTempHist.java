package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.SortCriterion;
import com.ffusion.ffs.bpw.interfaces.WireHistoryInfo;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.beans.PagingContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class WireTempHist
  implements FFSConst, DBConsts, BPWResource
{
  public static int createTempHist(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt, WireHistoryInfo paramWireHistoryInfo)
    throws FFSException
  {
    String str1 = "WireTempHist.createTempHist";
    FFSDebug.log(str1 + " : start", 6);
    FFSDebug.log(str1 + " , sessionId = " + paramString + ", cursorId = " + paramInt + "WireHistoryInfo id = " + paramWireHistoryInfo.getId(), 6);
    String str2 = null;
    if (paramWireHistoryInfo == null)
    {
      localObject = str1 + " failed: Invalid WireHistoryInfo object = null: ";
      FFSDebug.log((String)localObject, 0);
      throw new BPWException((String)localObject);
    }
    FFSDebug.log(str1 + " wHistInfo.getPayeeInfo(): " + paramWireHistoryInfo.getPayeeInfo(), 6);
    FFSDebug.log(str1 + " wHistInfo.getPayeeId() : " + paramWireHistoryInfo.getPayeeId(), 6);
    if (paramWireHistoryInfo.getPayeeInfo() != null)
    {
      str2 = paramWireHistoryInfo.getPayeeInfo().getBeneficiaryName();
    }
    else if ((paramWireHistoryInfo.getPayeeId() != null) && (paramWireHistoryInfo.getPayeeId().trim().length() > 0))
    {
      localObject = WirePayee.getWirePayeeInfo(paramFFSConnectionHolder, paramWireHistoryInfo.getPayeeId(), false);
      if (((WirePayeeInfo)localObject).getStatusCode() != 0)
      {
        str3 = str1 + " Failed to get Beneficiary Information. Error: " + ((WirePayeeInfo)localObject).getStatusMsg();
        FFSDebug.log(str3, 0);
        throw new BPWException(str3);
      }
      paramWireHistoryInfo.setPayeeInfo((WirePayeeInfo)localObject);
      str2 = paramWireHistoryInfo.getPayeeInfo().getBeneficiaryName();
    }
    Object localObject = paramWireHistoryInfo.getFromAcctNum();
    FFSDebug.log(str1 + " Source: " + paramWireHistoryInfo.getSource(), 6);
    FFSDebug.log(str1 + " Destination: " + paramWireHistoryInfo.getDestination(), 6);
    FFSDebug.log(str1 + " TransType: " + paramWireHistoryInfo.getTransType(), 6);
    FFSDebug.log(str1 + " wHistInfo.getPayeeInfo(): " + paramWireHistoryInfo.getPayeeInfo(), 6);
    if ((paramWireHistoryInfo.getSource() != null) && (paramWireHistoryInfo.getSource().equalsIgnoreCase("HOST")))
    {
      str2 = "HOST";
      localObject = "HOST";
    }
    if ((paramWireHistoryInfo.getTransType() != null) && (paramWireHistoryInfo.getTransType().equalsIgnoreCase("BATCH")))
    {
      str2 = "BATCH";
      localObject = "BATCH";
    }
    String str3 = paramWireHistoryInfo.getDate();
    if (str3 == null)
    {
      str3 = FFSUtil.getDateString("yyyyMMdd");
      str3 = str3 + "00";
    }
    Object[] arrayOfObject = { paramString, new Integer(paramInt), paramWireHistoryInfo.getId(), paramWireHistoryInfo.getTransType(), new Integer(Integer.parseInt(str3)), str2, localObject, paramWireHistoryInfo.getDestination(), paramWireHistoryInfo.getStatus(), new BigDecimal(paramWireHistoryInfo.getAmount()), paramWireHistoryInfo.getWireName(), DBUtil.getCurrentLogDate() };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_WireTempHist (SessionId, CursorId, TransactionId, TransactionType, DateToPost, BeneficiaryName, AccountNum, Destination, Status, Amount, WireName, SubmitDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)", arrayOfObject);
    }
    catch (Exception localException)
    {
      String str4 = str1 + " failed:";
      FFSDebug.log(str4 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log(str1 + " done", 6);
    return i;
  }
  
  public static void getBounds(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "WireTempHist.getBounds";
    FFSDebug.log(str1 + " : start", 6);
    getBound(paramPagingInfo, "SORT_VALUE_MAX_", null);
    getBound(paramPagingInfo, "SORT_VALUE_MIN_", null);
    FFSResultSet localFFSResultSet = null;
    WireHistoryInfo localWireHistoryInfo = null;
    try
    {
      ArrayList localArrayList1 = paramPagingInfo.getPagingContext().getSortCriteriaList();
      localObject1 = null;
      if ((localArrayList1 == null) || (localArrayList1.size() == 0))
      {
        localObject1 = new SortCriterion();
        ((SortCriterion)localObject1).setName("DateToPost");
        ((SortCriterion)localObject1).setAscending();
      }
      else
      {
        localObject1 = (SortCriterion)localArrayList1.get(0);
      }
      boolean bool1 = ((SortCriterion)localObject1).isAscending();
      ArrayList localArrayList2 = new ArrayList();
      boolean bool2 = true;
      ((SortCriterion)localObject1).setAscending();
      String str2 = jdMethod_if(paramPagingInfo, localArrayList2, bool2);
      Object[] arrayOfObject = localArrayList2.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localWireHistoryInfo = jdMethod_do(paramFFSConnectionHolder, localFFSResultSet);
        getBound(paramPagingInfo, "LOWER_BOUND_", localWireHistoryInfo);
      }
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
      localArrayList2 = new ArrayList();
      ((SortCriterion)localObject1).setDescending();
      str2 = jdMethod_if(paramPagingInfo, localArrayList2, bool2);
      arrayOfObject = localArrayList2.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localWireHistoryInfo = jdMethod_do(paramFFSConnectionHolder, localFFSResultSet);
        getBound(paramPagingInfo, "UPPER_BOUND_", localWireHistoryInfo);
      }
      if (bool1) {
        ((SortCriterion)localObject1).setAscending();
      } else {
        ((SortCriterion)localObject1).setDescending();
      }
      paramPagingInfo.setStatusCode(0);
      paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
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
  
  public static void getBound(PagingInfo paramPagingInfo, String paramString, WireHistoryInfo paramWireHistoryInfo)
    throws FFSException
  {
    String str1 = "WireTempHist.getBound";
    FFSDebug.log(str1 + " : start", 6);
    FFSDebug.log(str1 + " : Key = " + paramString, 6);
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
          paramWireHistoryInfo = (WireHistoryInfo)localArrayList1.get(0);
        } else {
          paramWireHistoryInfo = (WireHistoryInfo)localArrayList1.get(localArrayList1.size() - 1);
        }
      }
      else if (paramString.equals("SORT_VALUE_MAX_")) {
        if (bool) {
          paramWireHistoryInfo = (WireHistoryInfo)localArrayList1.get(localArrayList1.size() - 1);
        } else {
          paramWireHistoryInfo = (WireHistoryInfo)localArrayList1.get(0);
        }
      }
    }
    if (paramWireHistoryInfo != null)
    {
      String str3 = a(str2, paramWireHistoryInfo);
      FFSDebug.log(str1 + " : columnName = " + str2, 6);
      FFSDebug.log(str1 + " : columnValue = " + str3, 6);
      localPagingContext.getMap().put(paramString + str2, str3);
      localPagingContext.getMap().put(paramString + "TransactionIndex", paramWireHistoryInfo.getCursorId());
    }
    FFSDebug.log(str1 + " : end", 6);
  }
  
  public static ArrayList getSessionPage(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "WireTempHist.getSessionPage";
    FFSDebug.log(str1 + " : start", 6);
    FFSResultSet localFFSResultSet = null;
    Object localObject1 = null;
    WireHistoryInfo localWireHistoryInfo = null;
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
      }
      else
      {
        i = j;
      }
      FFSDebug.log(str1 + " : pagesize" + i, 6);
      if (i >= 1)
      {
        localObject2 = new ArrayList();
        int k = 0;
        ArrayList localArrayList = new ArrayList();
        boolean bool = false;
        String str4 = jdMethod_if(paramPagingInfo, localArrayList, bool);
        FFSDebug.log(str1 + " : stmt = createSortingSql" + str4, 6);
        Object[] arrayOfObject = localArrayList.toArray();
        FFSDebug.log(str1 + " : params" + localArrayList, 6);
        FFSDebug.log(str1 + " : params.size()" + localArrayList.size(), 6);
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str4, arrayOfObject);
        while (localFFSResultSet.getNextRow())
        {
          FFSDebug.log(str1 + " start to getWireHistoryInfo, rows = " + k, 6);
          localWireHistoryInfo = jdMethod_do(paramFFSConnectionHolder, localFFSResultSet);
          FFSDebug.log(str1 + " : wInfo" + localWireHistoryInfo, 6);
          FFSDebug.log(str1 + " wInfo.getStatusCode() = " + localWireHistoryInfo.getStatusCode(), 6);
          FFSDebug.log(str1 + " wInfo.getStatusMsg() = " + localWireHistoryInfo.getStatusMsg(), 6);
          if (localWireHistoryInfo.getStatusCode() == 0)
          {
            ((ArrayList)localObject2).add(localWireHistoryInfo);
            k++;
            if (k >= i) {
              break;
            }
          }
        }
        FFSDebug.log(str1 + " the number of record found " + ((ArrayList)localObject2).size(), 6);
        localObject1 = localObject2;
        String str5 = paramPagingInfo.getPagingContext().getDirection();
        if (str5.equals("PREVIOUS"))
        {
          localObject1 = new ArrayList();
          while (!((ArrayList)localObject2).isEmpty()) {
            ((ArrayList)localObject1).add(0, (WireHistoryInfo)((ArrayList)localObject2).remove(0));
          }
          FFSDebug.log(str1 + " invert result list, size= " + ((ArrayList)localObject1).size(), 6);
        }
        FFSDebug.log("**** reqPageSize = " + j, 6);
        FFSDebug.log("**** rows = " + k, 6);
        if ((j == 0) && (k >= i) && (localFFSResultSet.getNextRow()))
        {
          paramPagingInfo.setStatusCode(28010);
          paramPagingInfo.setStatusMsg("Server can't return all records requested because the number of records server found is more than the server maximum page size.");
        }
        else
        {
          paramPagingInfo.setStatusCode(0);
          paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        }
      }
      FFSDebug.log(str1 + " : end", 6);
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
  
  protected static WireHistoryInfo jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, FFSResultSet paramFFSResultSet)
    throws FFSException
  {
    String str1 = "WireTempHist.getWireHistoryInfo";
    WireHistoryInfo localWireHistoryInfo = new WireHistoryInfo();
    String str2 = paramFFSResultSet.getColumnString("TransactionId");
    String str3 = paramFFSResultSet.getColumnString("TransactionType");
    String str4 = Integer.toString(paramFFSResultSet.getColumnInt("CursorId"));
    String str5 = paramFFSResultSet.getColumnString("DateToPost");
    FFSDebug.log(str1 + " date: " + str5, 6);
    localWireHistoryInfo.setId(str2);
    localWireHistoryInfo.setTransType(str3);
    localWireHistoryInfo.setCursorId(str4);
    localWireHistoryInfo.setDate(str5);
    localWireHistoryInfo.setStatus("CREATED");
    localWireHistoryInfo = Wire.getWireHistoryInfoWithWireObject(paramFFSConnectionHolder, localWireHistoryInfo);
    FFSDebug.log(str1 + " wHistInfo.getPayeeInfo: " + localWireHistoryInfo.getPayeeInfo(), 6);
    FFSDebug.log(str1 + " date: " + str5, 6);
    return localWireHistoryInfo;
  }
  
  protected static String jdMethod_if(PagingInfo paramPagingInfo, ArrayList paramArrayList, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "WireTempHist.createSortingSql";
    FFSDebug.log(str1 + " : start", 6);
    String str2 = "BPW_WireTempHist";
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
    if (str4.equals("NEXT"))
    {
      FFSDebug.log(str1 + " : hm: " + localHashMap, 6);
      FFSDebug.log(str1 + " : columnName: " + str3, 6);
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
      if (str3.equalsIgnoreCase("Amount"))
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
    for (int i = 0; i < paramArrayList.size(); i++) {
      FFSDebug.log(str1 + " Sql Param:" + i + " :" + String.valueOf(paramArrayList.get(i)), 6);
    }
    FFSDebug.log(str1 + " : end", 6);
    return str8;
  }
  
  private static String a(String paramString, WireHistoryInfo paramWireHistoryInfo)
  {
    String str = null;
    if (paramString.equalsIgnoreCase("CursorId")) {
      str = paramWireHistoryInfo.getCursorId();
    } else if (paramString.equalsIgnoreCase("TransactionId")) {
      str = paramWireHistoryInfo.getId();
    } else if (paramString.equalsIgnoreCase("TransactionType")) {
      str = paramWireHistoryInfo.getTransType();
    } else if (paramString.equalsIgnoreCase("DateToPost")) {
      str = paramWireHistoryInfo.getDate();
    } else if (paramString.equalsIgnoreCase("BeneficiaryName"))
    {
      if (paramWireHistoryInfo.getPayeeInfo() != null) {
        str = paramWireHistoryInfo.getPayeeInfo().getBeneficiaryName();
      }
    }
    else if (paramString.equalsIgnoreCase("AccountNum")) {
      str = paramWireHistoryInfo.getFromAcctNum();
    } else if (paramString.equalsIgnoreCase("Destination")) {
      str = paramWireHistoryInfo.getDestination();
    } else if (paramString.equalsIgnoreCase("Status")) {
      str = paramWireHistoryInfo.getStatus();
    } else if (paramString.equalsIgnoreCase("Amount")) {
      str = paramWireHistoryInfo.getAmount();
    } else if (paramString.equalsIgnoreCase("WireName")) {
      str = paramWireHistoryInfo.getWireName();
    }
    return str;
  }
  
  public static int cleanup(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    String str = "WireTempHist.clean";
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = DBUtil.getCurrentLogDate();
    }
    FFSDebug.log(str + " start, oldDate=" + paramString, 6);
    Object[] arrayOfObject = { paramString };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_WireTempHist WHERE SubmitDate < ?", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log(str + " failed: " + localException.toString() + FFSDebug.stackTrace(localException));
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
    FFSDebug.log(str + " done, oldDate=" + paramString, 6);
    return i;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.WireTempHist
 * JD-Core Version:    0.7.0.1
 */