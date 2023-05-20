package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWExtdHist;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ACHTotals
  implements FFSConst, DBConsts, SQLConsts, ACHConsts
{
  public static final int SQL_TYPE_ACH_PASSTHRU_FILE = 1;
  public static final int SQL_TYPE_ACH_PASSTHRU_BATCH = 2;
  public static final int SQL_TYPE_ACH_BPWGEN_FILE = 3;
  public static final int SQL_TYPE_ACH_BPWGEN_BATCH = 4;
  public static final int SQL_TYPE_ACH_PASSTHRU_PARTICIPANT = 5;
  public static final int SQL_TYPE_ACH_BPWGEN_PARTICIPANT = 6;
  public static final int SQL_TYPE_EXT_TRANSFERS = 7;
  public static final int SQL_TYPE_TAX_PAYMENT = 8;
  public static final int SQL_TYPE_CASH_CON = 9;
  
  public static BPWExtdHist getACHTotals(FFSConnectionHolder paramFFSConnectionHolder, BPWExtdHist paramBPWExtdHist)
    throws FFSException
  {
    String str1 = "ACHTotals.getResultTotals: ";
    FFSDebug.log(str1 + " start", 6);
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    String str2 = paramBPWExtdHist.getTransType();
    if (str2.equals("ACHBATCHTRN"))
    {
      a(paramBPWExtdHist, localStringBuffer, 2, localArrayList);
      a(paramFFSConnectionHolder, paramBPWExtdHist, localStringBuffer.toString(), 2, localArrayList.toArray());
      a(localStringBuffer, localArrayList);
      a(paramBPWExtdHist, localStringBuffer, 4, localArrayList);
      a(paramFFSConnectionHolder, paramBPWExtdHist, localStringBuffer.toString(), 4, localArrayList.toArray());
    }
    else if (str2.equals("ACHFILETRN"))
    {
      a(paramBPWExtdHist, localStringBuffer, 1, localArrayList);
      a(paramFFSConnectionHolder, paramBPWExtdHist, localStringBuffer.toString(), 1, localArrayList.toArray());
      a(localStringBuffer, localArrayList);
      a(paramBPWExtdHist, localStringBuffer, 3, localArrayList);
      a(paramFFSConnectionHolder, paramBPWExtdHist, localStringBuffer.toString(), 3, localArrayList.toArray());
    }
    else if (str2.equals("ETFTRN"))
    {
      a(paramBPWExtdHist, localStringBuffer, 7, localArrayList);
      a(paramFFSConnectionHolder, paramBPWExtdHist, localStringBuffer.toString(), 7, localArrayList.toArray());
    }
    else if (str2.equals("TAXTRN"))
    {
      a(paramBPWExtdHist, localStringBuffer, 8, localArrayList);
      a(paramFFSConnectionHolder, paramBPWExtdHist, localStringBuffer.toString(), 8, localArrayList.toArray());
    }
    else if (str2.equals("CASHCONTRN"))
    {
      a(paramBPWExtdHist, localStringBuffer, 9, localArrayList);
      a(paramFFSConnectionHolder, paramBPWExtdHist, localStringBuffer.toString(), 9, localArrayList.toArray());
    }
    FFSDebug.log(str1 + "end", 6);
    return paramBPWExtdHist;
  }
  
  public static BPWExtdHist getACHParticipantTotals(FFSConnectionHolder paramFFSConnectionHolder, BPWExtdHist paramBPWExtdHist)
    throws FFSException
  {
    String str = "ACHTotals.getACHParticipantTotals: ";
    FFSDebug.log(str + " start", 6);
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    a(paramBPWExtdHist, localStringBuffer, 5, localArrayList);
    a(paramFFSConnectionHolder, paramBPWExtdHist, localStringBuffer.toString(), 5, localArrayList.toArray());
    a(localStringBuffer, localArrayList);
    a(paramBPWExtdHist, localStringBuffer, 6, localArrayList);
    a(paramFFSConnectionHolder, paramBPWExtdHist, localStringBuffer.toString(), 6, localArrayList.toArray());
    FFSDebug.log(str + "end", 6);
    return paramBPWExtdHist;
  }
  
  private static BPWExtdHist a(FFSConnectionHolder paramFFSConnectionHolder, BPWExtdHist paramBPWExtdHist, String paramString, int paramInt, Object[] paramArrayOfObject)
    throws FFSException
  {
    String str1 = "ACHTotals.getResultTotals: ";
    FFSDebug.log(str1 + " start", 6);
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap1 = new HashMap();
    if (paramBPWExtdHist.getCustId() != null) {
      localHashMap1.put(paramBPWExtdHist.getCustId(), new HashMap());
    }
    String str2 = null;
    String str3 = null;
    if (paramInt == 3)
    {
      str2 = paramBPWExtdHist.getStartDate();
      if (str2 == null) {
        str2 = ap("yyMMdd");
      } else {
        str2 = str2.substring(2);
      }
      str3 = paramBPWExtdHist.getEndDate();
      if (str3 != null) {
        str3 = str3.substring(2);
      }
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString, paramArrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        String str4 = localFFSResultSet.getColumnString(1);
        localObject1 = (HashMap)localHashMap1.get(str4);
        if (localObject1 == null)
        {
          localObject1 = new HashMap();
          localHashMap1.put(str4, localObject1);
        }
        Object localObject2;
        if ((paramInt == 1) || (paramInt == 2))
        {
          localObject2 = new HashMap();
          ((HashMap)localObject2).put("ACH_PASSTHRU_TOTAL", localFFSResultSet.getColumnString(3));
          ((HashMap)localObject1).put(localFFSResultSet.getColumnString(2), localObject2);
        }
        else if (paramInt == 4)
        {
          localObject2 = new HashMap();
          ((HashMap)localObject2).put("ACH_BPWGEN_TOTAL", localFFSResultSet.getColumnString(3));
          ((HashMap)localObject1).put(localFFSResultSet.getColumnString(2), localObject2);
        }
        else if (paramInt == 3)
        {
          localObject2 = localFFSResultSet.getColumnString("exportfilename");
          if (localObject2 != null)
          {
            int i = ((String)localObject2).indexOf('.');
            int j = ((String)localObject2).indexOf('.', i + 1);
            String str5 = ((String)localObject2).substring(i + 1, j);
            if ((str5.compareTo(str2) >= 0) && ((str3 == null) || (str5.compareTo(str3) <= 0)))
            {
              HashMap localHashMap2 = new HashMap();
              localHashMap2.put("ACH_BPWGEN_TOTAL", localFFSResultSet.getColumnString(3));
              ((HashMap)localObject1).put(localFFSResultSet.getColumnString(2), localHashMap2);
            }
          }
        }
        else
        {
          ((HashMap)localObject1).put(localFFSResultSet.getColumnString(2), localFFSResultSet.getColumnString(3));
        }
      }
      a(paramBPWExtdHist.getStatusList(), paramBPWExtdHist.getTransType(), localHashMap1, paramInt);
      paramBPWExtdHist.setExtraInfo(localHashMap1);
      FFSDebug.log(str1 + "end", 6);
    }
    catch (Exception localException1)
    {
      Object localObject1 = "****" + str1 + "failed : ";
      FFSDebug.log((String)localObject1 + FFSDebug.stackTrace(localException1), 0);
      throw new FFSException(localException1, (String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log("****" + str1 + "failed to close ResultSet " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    return paramBPWExtdHist;
  }
  
  private static void a(BPWExtdHist paramBPWExtdHist, StringBuffer paramStringBuffer, int paramInt, ArrayList paramArrayList)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    int i = 0;
    switch (paramInt)
    {
    case 1: 
      paramStringBuffer.append("select CustomerId, FileStatus, count(*) from ACH_File");
      str1 = " where DtProcessed >= ?";
      str2 = " and DtProcessed <= ?";
      str3 = " and CustomerId = ?";
      str4 = " and FileStatus in (";
      str5 = " group by CustomerId,FileStatus";
      str6 = " order by CustomerId,FileStatus";
      break;
    case 2: 
      paramStringBuffer.append("select CustomerId, FileStatus, count(*) from ACH_File f, ACH_FileBatch fb where f.FileId = fb.FileId");
      str1 = " and f.DtProcessed >= ?";
      str2 = " and f.DtProcessed <= ?";
      str3 = " and f.CustomerId = ?";
      str4 = " and f.filestatus in (";
      str5 = " group by f.FileStatus,f.FileStatus";
      str6 = " order by f.CustomerId,f.FileStatus";
      break;
    case 3: 
      paramStringBuffer.append("select CustomerId, BatchStatus, count(*) from ACH_Company c, ACH_Batch b where c.CompId = b.CompId and ExportFileName is not null and BatchCategory not in ('ACH_BATCH_TAX','ACH_BATCH_CHILD_SUPPORT')");
      str3 = " and c.CustomerId = ?";
      str4 = " and b.BatchStatus in (";
      str5 = " group by c.CustomerId,b.BatchStatus,b.ExportFileName";
      str6 = " order by c.CustomerId,b.BatchStatus,b.ExportFileName";
      i = 1;
      break;
    case 4: 
      paramStringBuffer.append("select CustomerId, BatchStatus, count(*) from ACH_Company c, ACH_Batch b where c.CompId = b.CompId and BatchCategory not in ('ACH_BATCH_TAX','ACH_BATCH_CHILD_SUPPORT')");
      str1 = " and b.DtProcessed >= ?";
      str2 = " and b.DtProcessed <= ?";
      str3 = " and c.CustomerId = ?";
      str4 = " and b.BatchStatus in (";
      str5 = " group by c.CustomerId,b.BatchStatus";
      str6 = " order by c.CustomerId,b.BatchStatus";
      break;
    case 5: 
      paramStringBuffer.append("select CustomerId, FileStatus, count(*) from ACH_File f, ACH_FileBatch fb, ACH_FileEntry fe where f.FileId = fb.FileId and fb.BatchId = fe.BatchId");
      str1 = " and f.DtProcessed >= ?";
      str2 = " and f.DtProcessed <= ?";
      str3 = " and f.CustomerId = ?";
      str4 = " and f.FileStatus in (";
      str5 = " group by f.CustomerId,f.FileStatus";
      str6 = " order by f.CustomerId,f.FileStatus";
      break;
    case 6: 
      paramStringBuffer.append("select CustomerId, BatchStatus, count(*) from ACH_Company c, ACH_Batch b, ACH_Record r, ACH_Payee p where c.CompId = b.CompId and b.BatchId = r.BatchId and r.PayeeId = p.PayeeId and BatchCategory not in ('ACH_BATCH_TAX','ACH_BATCH_CHILD_SUPPORT')");
      str1 = " and b.DtProcessed >= ?";
      str2 = " and b.DtProcessed <= ?";
      str3 = " and c.CustomerId = ?";
      str4 = " and b.BatchStatus in (";
      str5 = " group by c.CustomerId,b.BatchStatus";
      str6 = " order by c.CustomerId,b.BatchStatus";
      break;
    case 7: 
      paramStringBuffer.append("select CustomerId, Status, count(*) from BPW_Transfer");
      str1 = " where DatePosted >= ?";
      str2 = " and DatePosted <= ?";
      str3 = " and CustomerId = ?";
      str4 = " and Status in (";
      str5 = " group by CustomerId,Status";
      str6 = " order by CustomerId,Status";
      i = 1;
      break;
    case 8: 
      paramStringBuffer.append("select CustomerId, BatchStatus, count(*) from ACH_Company c, ACH_Batch b where c.CompId = b.CompId and BatchCategory in ('ACH_BATCH_TAX')");
      str1 = " and b.DtProcessed >= ?";
      str2 = " and b.DtProcessed <= ?";
      str3 = " and c.CustomerId = ?";
      str4 = " and b.BatchStatus in (";
      str5 = " group by c.CustomerId,b.BatchStatus";
      str6 = " order by c.CustomerId,b.BatchStatus";
      break;
    case 9: 
      paramStringBuffer.append("select CustomerId, e.Status, count(*) from CC_Company c, CC_Location l, CC_Entry e where c.CompId=l.CompId and l.LocationId=e.LocationId");
      str1 = " and e.ProcessedTime >= ?";
      str2 = " and e.ProcessedTime <= ?";
      str3 = " and c.CustomerId = ?";
      str4 = " and e.Status in (";
      str5 = " group by c.CustomerId,e.Status";
      str6 = " order by c.CustomerId,e.Status";
      i = 1;
    }
    if (paramInt != 3)
    {
      String str7 = paramBPWExtdHist.getStartDate();
      if (str7 == null) {
        str7 = ap("yyyyMMdd");
      }
      if (i != 0)
      {
        DBUtil.appendToCondition(paramStringBuffer, str7, str1, paramArrayList);
        DBUtil.appendToCondition(paramStringBuffer, paramBPWExtdHist.getEndDate(), str2, paramArrayList);
      }
      else
      {
        try
        {
          DBUtil.appendToCondition(paramStringBuffer, new Integer(str7), str1, paramArrayList);
        }
        catch (NumberFormatException localNumberFormatException1) {}
        try
        {
          DBUtil.appendToCondition(paramStringBuffer, new Integer(paramBPWExtdHist.getEndDate()), str2, paramArrayList);
        }
        catch (NumberFormatException localNumberFormatException2) {}
      }
    }
    DBUtil.appendToCondition(paramStringBuffer, paramBPWExtdHist.getCustId(), str3, paramArrayList);
    DBUtil.appendArrayToCondition(paramStringBuffer, paramBPWExtdHist.getStatusList(), str4, paramArrayList);
    paramStringBuffer.append(str5);
    paramStringBuffer.append(str6);
  }
  
  private static String ap(String paramString)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(2, -1);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
    return localSimpleDateFormat.format(localCalendar.getTime());
  }
  
  private static void a(String[] paramArrayOfString, String paramString, HashMap paramHashMap, int paramInt)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      HashMap localHashMap1 = (HashMap)paramHashMap.get(str);
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        Object localObject = localHashMap1.get(paramArrayOfString[i]);
        HashMap localHashMap2;
        if (localObject == null)
        {
          if ((("ACHBATCHTRN".equals(paramString)) || ("ACHFILETRN".equals(paramString))) && (paramInt != 5) && (paramInt != 6))
          {
            localHashMap2 = new HashMap();
            localHashMap2.put("ACH_PASSTHRU_TOTAL", "0");
            localHashMap2.put("ACH_BPWGEN_TOTAL", "0");
            localHashMap1.put(paramArrayOfString[i], localHashMap2);
          }
          else
          {
            localHashMap1.put(paramArrayOfString[i], "0");
          }
        }
        else if ((("ACHBATCHTRN".equals(paramString)) || ("ACHFILETRN".equals(paramString))) && (paramInt != 5) && (paramInt != 6))
        {
          localHashMap2 = (HashMap)localObject;
          if (localHashMap2.get("ACH_PASSTHRU_TOTAL") == null) {
            localHashMap2.put("ACH_PASSTHRU_TOTAL", "0");
          }
          if (localHashMap2.get("ACH_BPWGEN_TOTAL") == null) {
            localHashMap2.put("ACH_BPWGEN_TOTAL", "0");
          }
        }
      }
    }
  }
  
  private static void a(StringBuffer paramStringBuffer, ArrayList paramArrayList)
  {
    paramStringBuffer.setLength(0);
    paramArrayList.clear();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ACHTotals
 * JD-Core Version:    0.7.0.1
 */