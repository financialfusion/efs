package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHHistInfo;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CCEntryInfo;
import com.ffusion.ffs.bpw.interfaces.CCLocationInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
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

public class CashConTempHist
  implements FFSConst, DBConsts, BPWResource
{
  public static int createTempHist(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt, ACHHistInfo paramACHHistInfo)
    throws FFSException
  {
    String str1 = "CashConTempHist.createTempHist ";
    FFSDebug.log(str1 + " : start", 6);
    paramACHHistInfo.setStatusCode(0);
    paramACHHistInfo.setStatusMsg("Success");
    if (paramACHHistInfo == null)
    {
      paramACHHistInfo.setStatusCode(16000);
      paramACHHistInfo.setStatusMsg("ACHHistInfo is null");
      return 0;
    }
    CCEntryInfo localCCEntryInfo = (CCEntryInfo)paramACHHistInfo.getObjInfo();
    if (localCCEntryInfo == null)
    {
      paramACHHistInfo.setStatusCode(16000);
      paramACHHistInfo.setStatusMsg("ACHHistInfo.ObjInfo is null");
      return 0;
    }
    String str2 = null;
    CCLocationInfo localCCLocationInfo = new CCLocationInfo();
    localCCLocationInfo.setLocationId(localCCEntryInfo.getLocationId());
    localCCLocationInfo = CashCon.getCCLocation(paramFFSConnectionHolder, localCCLocationInfo);
    if (localCCLocationInfo.getStatusCode() != 0)
    {
      FFSDebug.log(str1 + "Error, Cannot find location !" + " Reason =" + localCCLocationInfo.getStatusMsg(), 0);
      return 0;
    }
    str2 = localCCLocationInfo.getLocationName();
    FFSDebug.log(str1 + " , sessionId = " + paramString + ", cursorId = " + paramInt, 6);
    Object[] arrayOfObject = { paramString, new Integer(paramInt), localCCEntryInfo.getEntryId(), "CASHCON", "" + new Integer(BPWUtil.getDateDBFormat(localCCEntryInfo.getDueDate())), "Division", str2, localCCEntryInfo.getTransactionType(), localCCEntryInfo.getStatus(), new BigDecimal(localCCEntryInfo.getAmount()), DBUtil.getCurrentLogDate() };
    FFSDebug.log(str1 + "args " + Arrays.asList(arrayOfObject), 6);
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_CashConTempHist (SessionId, CursorId, TransactionId, TransactionType,DueDate, Division, Location, Type, Status, Amount, SubmitDate) VALUES(?,?,?,?,?,?,?,?,?,?,?)", arrayOfObject);
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
    String str1 = "CashConTempHist.getBounds";
    FFSDebug.log(str1 + " : start", 6);
    FFSResultSet localFFSResultSet = null;
    PagingContext localPagingContext = paramPagingInfo.getPagingContext();
    try
    {
      SortCriterion localSortCriterion = h(paramPagingInfo);
      str2 = localSortCriterion.getName();
      int i = 1;
      String str3 = "SELECT CursorId, " + str2 + " FROM BPW_CashConTempHist " + " WHERE SessionId = ? ORDER BY " + str2 + " ASC, CursorId ASC";
      Object[] arrayOfObject = { paramPagingInfo.getPagingContext().getSessionId() };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      String str4;
      String str5;
      if (localFFSResultSet.getNextRow())
      {
        str4 = a(localSortCriterion.getName(), localFFSResultSet);
        str5 = a("CursorId", localFFSResultSet);
        localPagingContext.getMap().put("LOWER_BOUND_" + str2, str4);
        localPagingContext.getMap().put("LOWER_BOUND_TransactionIndex", str5);
      }
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
      str3 = "SELECT CursorId, " + str2 + " FROM BPW_CashConTempHist " + " WHERE SessionId = ?  ORDER BY " + str2 + " DESC, CursorId DESC";
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        str4 = a(localSortCriterion.getName(), localFFSResultSet);
        str5 = a("CursorId", localFFSResultSet);
        localPagingContext.getMap().put("UPPER_BOUND_" + str2, str4);
        localPagingContext.getMap().put("UPPER_BOUND_TransactionIndex", str5);
      }
      paramPagingInfo.setStatusCode(0);
      paramPagingInfo.setStatusMsg("Success");
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
  
  public static ArrayList getSessionPage(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "CashConTempHist.getSessionPage";
    FFSDebug.log(str1 + " : start. Direction: " + paramPagingInfo.getPagingContext().getDirection(), 6);
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
      Object localObject2 = h(paramPagingInfo);
      boolean bool1 = ((SortCriterion)localObject2).isAscending();
      String str4 = paramPagingInfo.getPagingContext().getDirection();
      if (i >= 1)
      {
        localObject3 = new ArrayList();
        int k = 0;
        ArrayList localArrayList = new ArrayList();
        boolean bool2 = false;
        String str5 = jdMethod_do(paramPagingInfo, localArrayList, bool2);
        Object[] arrayOfObject = localArrayList.toArray();
        FFSDebug.log(str1 + " sql stmt = " + str5, 6);
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str5, arrayOfObject);
        int m = 0;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        int n = 0;
        while (localFFSResultSet.getNextRow())
        {
          n = 1;
          localACHHistInfo = jdMethod_int(paramFFSConnectionHolder, localFFSResultSet);
          if (bool1)
          {
            str6 = a(((SortCriterion)localObject2).getName(), localFFSResultSet);
            str9 = localACHHistInfo.getCursorId();
          }
          else
          {
            str7 = a(((SortCriterion)localObject2).getName(), localFFSResultSet);
            str8 = localACHHistInfo.getCursorId();
          }
          if (localACHHistInfo.getStatusCode() == 0)
          {
            ((ArrayList)localObject3).add(localACHHistInfo);
            k++;
            if (k >= i) {
              break;
            }
          }
          if (m == 0) {
            if (bool1)
            {
              str7 = a(((SortCriterion)localObject2).getName(), localFFSResultSet);
              str8 = localACHHistInfo.getCursorId();
            }
            else
            {
              str6 = a(((SortCriterion)localObject2).getName(), localFFSResultSet);
              str9 = localACHHistInfo.getCursorId();
            }
          }
          m++;
        }
        if (n != 0)
        {
          paramPagingInfo.getPagingContext().getMap().put("SORT_VALUE_MIN_" + ((SortCriterion)localObject2).getName(), str7);
          paramPagingInfo.getPagingContext().getMap().put("SORT_VALUE_MIN_TransactionIndex", str8);
          paramPagingInfo.getPagingContext().getMap().put("SORT_VALUE_MAX_" + ((SortCriterion)localObject2).getName(), str6);
          paramPagingInfo.getPagingContext().getMap().put("SORT_VALUE_MAX_TransactionIndex", str9);
        }
        localObject1 = localObject3;
        if (str4.equals("PREVIOUS"))
        {
          localObject1 = new ArrayList();
          while (!((ArrayList)localObject3).isEmpty()) {
            ((ArrayList)localObject1).add(0, (ACHHistInfo)((ArrayList)localObject3).remove(0));
          }
          if (n != 0)
          {
            paramPagingInfo.getPagingContext().getMap().put("SORT_VALUE_MAX_" + ((SortCriterion)localObject2).getName(), str7);
            paramPagingInfo.getPagingContext().getMap().put("SORT_VALUE_MAX_TransactionIndex", str8);
            paramPagingInfo.getPagingContext().getMap().put("SORT_VALUE_MIN_" + ((SortCriterion)localObject2).getName(), str6);
            paramPagingInfo.getPagingContext().getMap().put("SORT_VALUE_MIN_TransactionIndex", str9);
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
      Object localObject3 = localObject1;
      return localObject3;
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
  
  protected static ACHHistInfo jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, FFSResultSet paramFFSResultSet)
    throws FFSException
  {
    String str1 = "CashConTempHist.getACHHistInfo";
    String str2 = paramFFSResultSet.getColumnString("TransactionId");
    String str3 = paramFFSResultSet.getColumnString("TransactionType");
    String str4 = paramFFSResultSet.getColumnString("CursorId");
    CCEntryInfo localCCEntryInfo = new CCEntryInfo();
    localCCEntryInfo.setEntryId(str2);
    localCCEntryInfo = CashCon.getCCEntry(paramFFSConnectionHolder, localCCEntryInfo);
    ACHHistInfo localACHHistInfo = new ACHHistInfo();
    localACHHistInfo.setCursorId(str4);
    localACHHistInfo.setObjInfo(localCCEntryInfo);
    localACHHistInfo.setObjType(1);
    localACHHistInfo.setStatusCode(localCCEntryInfo.getStatusCode());
    localACHHistInfo.setStatusMsg(localCCEntryInfo.getStatusMsg());
    return localACHHistInfo;
  }
  
  protected static String jdMethod_do(PagingInfo paramPagingInfo, ArrayList paramArrayList, boolean paramBoolean)
  {
    String str1 = "BPW_CashConTempHist";
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
    String str2 = localSortCriterion.getName();
    String str3 = localPagingContext.getDirection();
    String str4 = null;
    String str5 = null;
    if (str3.equals("NEXT"))
    {
      if (bool)
      {
        str4 = (String)localHashMap.get("SORT_VALUE_MAX_" + str2);
        str5 = (String)localHashMap.get("SORT_VALUE_MAX_TransactionIndex");
      }
      else
      {
        str4 = (String)localHashMap.get("SORT_VALUE_MIN_" + str2);
        str5 = (String)localHashMap.get("SORT_VALUE_MIN_TransactionIndex");
      }
    }
    else if (str3.equals("PREVIOUS")) {
      if (bool)
      {
        str4 = (String)localHashMap.get("SORT_VALUE_MIN_" + str2);
        str5 = (String)localHashMap.get("SORT_VALUE_MIN_TransactionIndex");
      }
      else
      {
        str4 = (String)localHashMap.get("SORT_VALUE_MAX_" + str2);
        str5 = (String)localHashMap.get("SORT_VALUE_MAX_TransactionIndex");
      }
    }
    FFSDebug.log("ColumnValue: " + str4);
    FFSDebug.log("CursorId: " + str5);
    String str6 = "<";
    if ((bool) && (str3.equals("NEXT"))) {
      str6 = ">";
    } else if ((!bool) && (str3.equals("PREVIOUS"))) {
      str6 = ">";
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT * FROM ");
    localStringBuffer.append(str1);
    localStringBuffer.append(" WHERE ");
    localStringBuffer.append(" SessionId = ? ");
    paramArrayList.add(localPagingContext.getSessionId());
    if ((str4 != null) && (str5 != null) && (!paramBoolean))
    {
      localStringBuffer.append(" AND (");
      localStringBuffer.append(str2);
      localStringBuffer.append(str6);
      localStringBuffer.append("?");
      localStringBuffer.append(" OR (");
      localStringBuffer.append(str2);
      localStringBuffer.append("=");
      localStringBuffer.append("? AND CursorId");
      localStringBuffer.append(str6);
      localStringBuffer.append("?))");
      if (str2.equalsIgnoreCase("Amount"))
      {
        paramArrayList.add(new BigDecimal(str4));
        paramArrayList.add(new BigDecimal(str4));
      }
      else if (str2.equalsIgnoreCase("DueDate"))
      {
        paramArrayList.add(String.valueOf(BPWUtil.getDateDBFormat(str4)));
        paramArrayList.add(String.valueOf(BPWUtil.getDateDBFormat(str4)));
      }
      else
      {
        paramArrayList.add(str4);
        paramArrayList.add(str4);
      }
      paramArrayList.add(new Integer(str5));
    }
    localStringBuffer.append(" ORDER BY ");
    localStringBuffer.append(str2);
    if ((paramBoolean) || (str3.equals("FIRST")))
    {
      if (bool) {
        localStringBuffer.append(" ASC, CursorId ASC");
      } else {
        localStringBuffer.append(" DESC, CursorId DESC");
      }
    }
    else if (str6.equals(">")) {
      localStringBuffer.append(" ASC, CursorId ASC");
    } else {
      localStringBuffer.append(" DESC, CursorId DESC");
    }
    String str7 = localStringBuffer.toString();
    return str7;
  }
  
  private static SortCriterion h(PagingInfo paramPagingInfo)
  {
    ArrayList localArrayList = paramPagingInfo.getPagingContext().getSortCriteriaList();
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
    return localSortCriterion;
  }
  
  private static String a(String paramString, FFSResultSet paramFFSResultSet)
    throws FFSException
  {
    String str1 = "CashConTempHist.getValue ";
    FFSDebug.log(str1 + "start, columnName =" + paramString, 6);
    String str2 = null;
    if (paramString == null)
    {
      FFSDebug.log(str1 + "error, columnName is null !", 6);
      return null;
    }
    if (paramString.equalsIgnoreCase("CursorId")) {
      str2 = Integer.toString(paramFFSResultSet.getColumnInt(paramString));
    } else if (paramString.equalsIgnoreCase("TransactionId")) {
      str2 = paramFFSResultSet.getColumnString(paramString);
    } else if (paramString.equalsIgnoreCase("TransactionType")) {
      str2 = "CASHCON";
    } else if (paramString.equalsIgnoreCase("DueDate")) {
      str2 = paramFFSResultSet.getColumnString(paramString);
    } else if (paramString.equalsIgnoreCase("Division")) {
      str2 = "Division";
    } else if (paramString.equalsIgnoreCase("Location")) {
      str2 = paramFFSResultSet.getColumnString(paramString);
    } else if (paramString.equalsIgnoreCase("Type")) {
      str2 = paramFFSResultSet.getColumnString(paramString);
    } else if (paramString.equalsIgnoreCase("Status")) {
      str2 = paramFFSResultSet.getColumnString(paramString);
    } else if (paramString.equalsIgnoreCase("Amount")) {
      str2 = ((BigDecimal)paramFFSResultSet.getColumnObject(paramString)).toString();
    }
    FFSDebug.log(str1 + "end, value =" + str2, 6);
    return str2;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.CashConTempHist
 * JD-Core Version:    0.7.0.1
 */