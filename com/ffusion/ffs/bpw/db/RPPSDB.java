package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RPPSPmtFileInfo;
import com.ffusion.ffs.bpw.interfaces.RPPSPmtInfo;
import com.ffusion.ffs.bpw.interfaces.RPPSRecordInfo;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import java.util.ArrayList;

public class RPPSDB
  implements DBConsts, FFSConst
{
  public static RPPSRecordInfo addRPPSEntryTmp(FFSConnectionHolder paramFFSConnectionHolder, RPPSRecordInfo paramRPPSRecordInfo)
    throws FFSException
  {
    String str1 = "RPPSDB.addRPPSEntryTmp: ";
    String str2 = null;
    int i = 0;
    int j = 0;
    FFSDebug.log(str1 + "start...", 6);
    Object localObject;
    if (paramRPPSRecordInfo == null)
    {
      localObject = "***" + str1 + "failed: " + "Null record object passed";
      FFSDebug.log((String)localObject, 0);
      paramRPPSRecordInfo = new RPPSRecordInfo();
      paramRPPSRecordInfo.setStatusCode(16000);
      paramRPPSRecordInfo.setStatusMsg("RPPSDB  is null");
      return paramRPPSRecordInfo;
    }
    if (!a(paramRPPSRecordInfo)) {
      return paramRPPSRecordInfo;
    }
    try
    {
      j = DBUtil.getNextIndex("RPPSENTRYID");
      paramRPPSRecordInfo.setEntryId(j);
      str2 = "INSERT INTO RPPS_EntryTmp (EntryId, SrvrTID, FIID, PayeeID, TXCode) VALUES (?,?,?,?,?)";
      localObject = new Object[] { new Integer(paramRPPSRecordInfo.getEntryId()), paramRPPSRecordInfo.getSrvrTId(), paramRPPSRecordInfo.getFiId(), paramRPPSRecordInfo.getPayeeId(), new Integer(paramRPPSRecordInfo.getTxCode()) };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      paramRPPSRecordInfo.setStatusCode(0);
      paramRPPSRecordInfo.setStatusMsg("Success");
      return paramRPPSRecordInfo;
    }
    catch (Exception localException)
    {
      String str3 = "***RPPSDB.addRPPSRecord failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str3);
    }
  }
  
  public static RPPSRecordInfo[] getRPPSEntryTmps(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt1, int paramInt2)
    throws FFSException
  {
    String str1 = "RPPSDB.getRPPSEntryTmps: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    RPPSRecordInfo localRPPSRecordInfo = new RPPSRecordInfo();
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    FFSDebug.log(str1 + "start...", 6);
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    int j = localPropertyConfig.getBatchSize();
    try
    {
      str2 = "SELECT EntryId, SrvrTID, FIID, PayeeID, TXCode FROM RPPS_EntryTmp WHERE FIID = ? and PayeeId = ? and TXCode = ? and EntryId >= ? ORDER BY EntryId";
      Object[] arrayOfObject = { paramString1, paramString2, new Integer(paramInt1), new Integer(paramInt2) };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localRPPSRecordInfo = jdMethod_if(localFFSResultSet);
        localArrayList.add(localRPPSRecordInfo);
        i++;
        if (i >= j) {
          break;
        }
      }
    }
    catch (Exception localException1)
    {
      String str3 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, str3);
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
          FFSDebug.log("***" + str1 + "Error closing resultset: " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "Done.", 6);
    return toEntryTmpArray(localArrayList);
  }
  
  public static RPPSRecordInfo removeAllRPPSEntryTmps(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "RPPSDB.removeAllRPPSRecords: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    RPPSRecordInfo localRPPSRecordInfo = new RPPSRecordInfo();
    int j = 0;
    int k = 0;
    int m = 0;
    FFSDebug.log(str1 + "start...", 6);
    Object localObject1;
    if (paramString == null)
    {
      localObject1 = "***" + str1 + "failed: " + "Null FIId passed";
      FFSDebug.log((String)localObject1, 0);
      localRPPSRecordInfo.setStatusCode(16000);
      localRPPSRecordInfo.setStatusMsg("RPPSDB  is null");
      return localRPPSRecordInfo;
    }
    try
    {
      str2 = "SELECT MAX(EntryId) AS RPPSEntryTmpMaxEntryId, MIN(EntryId) AS RPPSEntryTmpMinEntryId FROM RPPS_EntryTmp WHERE FIID = ?";
      localObject1 = new Object[] { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        j = localFFSResultSet.getColumnInt("RPPSEntryTmpMinEntryId");
        localObject2 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
        m = ((PropertyConfig)localObject2).getBatchSize();
        k = j + m;
        str2 = "DELETE FROM RPPS_EntryTmp WHERE FIID = ? AND EntryId <= ?";
        for (;;)
        {
          Object[] arrayOfObject = { paramString, new Integer(k) };
          i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
          String str3 = str1 + "records removed: " + Integer.toString(i);
          FFSDebug.log(str3, 6);
          paramFFSConnectionHolder.conn.commit();
          if (i == 0) {
            break;
          }
          k += m;
        }
      }
      localRPPSRecordInfo.setStatusCode(0);
      localRPPSRecordInfo.setStatusMsg("Success");
    }
    catch (Exception localException1)
    {
      Object localObject2 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException1);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localException1, (String)localObject2);
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
          FFSDebug.log(str1 + ": failed " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "Done.", 6);
    return localRPPSRecordInfo;
  }
  
  public static RPPSPmtFileInfo addRPPSPmtFileMap(FFSConnectionHolder paramFFSConnectionHolder, RPPSPmtFileInfo paramRPPSPmtFileInfo)
    throws FFSException
  {
    String str1 = "RPPSDB.addRPPSPmtFileMap: ";
    String str2 = null;
    int i = 0;
    FFSDebug.log(str1 + "start...", 6);
    if (paramRPPSPmtFileInfo == null)
    {
      String str3 = "***" + str1 + "failed: " + "Null pmt. file object passed";
      FFSDebug.log(str3, 0);
      paramRPPSPmtFileInfo = new RPPSPmtFileInfo();
      paramRPPSPmtFileInfo.setStatusCode(16000);
      paramRPPSPmtFileInfo.setStatusMsg("RPPSDB  is null");
      return paramRPPSPmtFileInfo;
    }
    if (!a(paramRPPSPmtFileInfo)) {
      return paramRPPSPmtFileInfo;
    }
    try
    {
      int j = DBUtil.getNextIndex("RPPSPMTFILE");
      paramRPPSPmtFileInfo.setFileId(j);
      str2 = "INSERT INTO RPPS_PmtFileMap (FileId, TransDate, TransTime, FileIdModifier, TotalEntryCount, TotalDebit, TotalCredit, FileName, Confirmed, Completed, FIID) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      localObject = new Object[] { new Integer(paramRPPSPmtFileInfo.getFileId()), paramRPPSPmtFileInfo.getTransDate(), paramRPPSPmtFileInfo.getTransTime(), paramRPPSPmtFileInfo.getFileIdModifier(), new Integer(paramRPPSPmtFileInfo.getTotalEntryCount()), paramRPPSPmtFileInfo.getTotalDebit(), paramRPPSPmtFileInfo.getTotalCredit(), paramRPPSPmtFileInfo.getFileName(), paramRPPSPmtFileInfo.getConfirmed(), paramRPPSPmtFileInfo.getCompleted(), paramRPPSPmtFileInfo.getFiId() };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      paramRPPSPmtFileInfo.setStatusCode(0);
      paramRPPSPmtFileInfo.setStatusMsg("Success");
    }
    catch (Exception localException)
    {
      Object localObject = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject, 0);
      throw new FFSException(localException, (String)localObject);
    }
    FFSDebug.log(str1 + "done.", 6);
    return paramRPPSPmtFileInfo;
  }
  
  public static RPPSPmtFileInfo updateRPPSPmtFileMap(FFSConnectionHolder paramFFSConnectionHolder, RPPSPmtFileInfo paramRPPSPmtFileInfo)
    throws FFSException
  {
    String str1 = "RPPSDB.updateRPPSPmtFileMap: ";
    String str2 = "UPDATE RPPS_PmtFileMap SET TransDate = ?, TransTime = ?, FileIdModifier = ?, TotalEntryCount = ?, TotalDebit = ?, TotalCredit = ?, FileName = ?, Confirmed = ?, Completed = ?, FIID = ? WHERE FileId = ?";
    int i = 0;
    FFSDebug.log(str1 + "start...", 6);
    if (paramRPPSPmtFileInfo == null)
    {
      localObject = str1 + "failed: " + "Null PmtFileInfo object passed";
      FFSDebug.log((String)localObject, 0);
      paramRPPSPmtFileInfo = new RPPSPmtFileInfo();
      paramRPPSPmtFileInfo.setStatusCode(16000);
      paramRPPSPmtFileInfo.setStatusMsg("RPPSPmtFileInfo  is null");
      return paramRPPSPmtFileInfo;
    }
    if (!a(paramRPPSPmtFileInfo)) {
      return paramRPPSPmtFileInfo;
    }
    Object localObject = { paramRPPSPmtFileInfo.getTransDate(), paramRPPSPmtFileInfo.getTransTime(), paramRPPSPmtFileInfo.getFileIdModifier(), new Integer(paramRPPSPmtFileInfo.getTotalEntryCount()), paramRPPSPmtFileInfo.getTotalDebit(), paramRPPSPmtFileInfo.getTotalCredit(), paramRPPSPmtFileInfo.getFileName(), paramRPPSPmtFileInfo.getConfirmed(), paramRPPSPmtFileInfo.getCompleted(), paramRPPSPmtFileInfo.getFiId(), new Integer(paramRPPSPmtFileInfo.getFileId()) };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      FFSDebug.log(str1 + "Number of records updated (RPPS_PmtFileMap): " + i, 6);
      FFSDebug.log(str1 + "end", 6);
      if (i == 0)
      {
        paramRPPSPmtFileInfo.setStatusCode(16020);
        paramRPPSPmtFileInfo.setStatusMsg("RPPS_PmtFileMap  record not found");
      }
      else
      {
        paramRPPSPmtFileInfo.setStatusCode(0);
        paramRPPSPmtFileInfo.setStatusMsg("Success");
      }
      return paramRPPSPmtFileInfo;
    }
    catch (Exception localException)
    {
      String str3 = str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str3);
    }
  }
  
  public static RPPSPmtFileInfo getRPPSPmtFileMapByFileId(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws FFSException
  {
    String str1 = "RPPSDB.getRPPSPmtFileMapByFileId: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    RPPSPmtFileInfo localRPPSPmtFileInfo = new RPPSPmtFileInfo();
    FFSDebug.log(str1 + "start...", 6);
    try
    {
      str2 = "SELECT FileId, TransDate, TransTime, FileIdModifier, TotalEntryCount, TotalDebit, TotalCredit, FileName, Confirmed, Completed, FIID FROM RPPS_PmtFileMap WHERE FileId = ? ORDER BY FileId";
      Object[] arrayOfObject = { new Integer(paramInt) };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localRPPSPmtFileInfo = jdMethod_for(localFFSResultSet);
      }
      else
      {
        localRPPSPmtFileInfo.setStatusCode(16020);
        localRPPSPmtFileInfo.setStatusMsg(" record not found");
      }
    }
    catch (Exception localException1)
    {
      String str3 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, str3);
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
          FFSDebug.log("***" + str1 + "Error closing resultset: " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "Done.", 6);
    return localRPPSPmtFileInfo;
  }
  
  public static RPPSPmtInfo addRPPSPmtEntryMap(FFSConnectionHolder paramFFSConnectionHolder, RPPSPmtInfo paramRPPSPmtInfo)
    throws FFSException
  {
    String str1 = "RPPSDB.addRPPSPmtEntryMap: ";
    String str2 = null;
    int i = 0;
    FFSDebug.log(str1 + "start...", 6);
    Object localObject;
    if (paramRPPSPmtInfo == null)
    {
      localObject = "***" + str1 + "failed: " + "Null pmt. info object passed";
      FFSDebug.log((String)localObject, 0);
      paramRPPSPmtInfo = new RPPSPmtInfo();
      paramRPPSPmtInfo.setStatusCode(16000);
      paramRPPSPmtInfo.setStatusMsg("RPPSDB  is null");
      return paramRPPSPmtInfo;
    }
    if (!a(paramRPPSPmtInfo)) {
      return paramRPPSPmtInfo;
    }
    try
    {
      str2 = "INSERT INTO RPPS_PmtEntryMap (SrvrTID, SubmitDate, BatchNum, TraceNum, PayAccount, FileId, ConsumerName) VALUES (?,?,?,?,?,?,?)";
      localObject = new Object[] { paramRPPSPmtInfo.getSrvrTId(), paramRPPSPmtInfo.getSubmitDate(), new Integer(paramRPPSPmtInfo.getBatchNum()), new Integer(paramRPPSPmtInfo.getTraceNum()), paramRPPSPmtInfo.getPayAccount(), new Integer(paramRPPSPmtInfo.getFileId()), paramRPPSPmtInfo.getConsumerName() };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      paramRPPSPmtInfo.setStatusCode(0);
      paramRPPSPmtInfo.setStatusMsg("Success");
    }
    catch (Exception localException)
    {
      String str3 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "Done.", 6);
    return paramRPPSPmtInfo;
  }
  
  public static RPPSPmtInfo updateRPPSPmtEntryMap(FFSConnectionHolder paramFFSConnectionHolder, RPPSPmtInfo paramRPPSPmtInfo)
    throws FFSException
  {
    String str1 = "RPPSDB.updateRPPSPmtEntryMap: ";
    String str2 = "UPDATE RPPS_PmtEntryMap SET SubmitDate = ?,  BatchNum = ? , TraceNum = ? , PayAccount = ? ,  FileId = ?,  ConsumerName = ? WHERE SrvrTID = ?";
    int i = 0;
    FFSDebug.log(str1 + "start...", 6);
    if (!a(paramRPPSPmtInfo)) {
      return paramRPPSPmtInfo;
    }
    try
    {
      Object[] arrayOfObject = { FFSUtil.getDateString(), new Integer(paramRPPSPmtInfo.getBatchNum()), new Integer(paramRPPSPmtInfo.getTraceNum()), paramRPPSPmtInfo.getPayAccount(), new Integer(paramRPPSPmtInfo.getFileId()), paramRPPSPmtInfo.getConsumerName(), paramRPPSPmtInfo.getSrvrTId() };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      FFSDebug.log(str1 + "Number of records updated (RPPS_Biller): " + i, 6);
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "end", 6);
    return paramRPPSPmtInfo;
  }
  
  public static RPPSPmtInfo getRPPSPmtEntryMap(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "RPPSDB.getRPPSPmtEntryMap: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    RPPSPmtInfo localRPPSPmtInfo = new RPPSPmtInfo();
    FFSDebug.log(str1 + "start...", 6);
    try
    {
      str2 = "SELECT SrvrTID, SubmitDate, BatchNum, TraceNum, PayAccount, FileId, ConsumerName FROM RPPS_PmtEntryMap WHERE SrvrTID = ?";
      Object[] arrayOfObject = { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localRPPSPmtInfo = jdMethod_do(localFFSResultSet);
      }
      else
      {
        localRPPSPmtInfo.setStatusCode(16020);
        localRPPSPmtInfo.setStatusMsg(" record not found");
      }
    }
    catch (Exception localException1)
    {
      String str3 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, str3);
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
          FFSDebug.log("***" + str1 + "Error closing resultset: " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "Done.", 6);
    return localRPPSPmtInfo;
  }
  
  public static String getRPPSPmtEntryMapSrvrTID(FFSConnectionHolder paramFFSConnectionHolder, int paramInt1, int paramInt2, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "RPPSDB.getRPPSPmtEntryMapSrvrTID: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    String str3 = null;
    FFSDebug.log(str1 + "start...", 6);
    try
    {
      str2 = "SELECT SrvrTID FROM RPPS_PmtEntryMap WHERE BatchNum = ? AND TraceNum = ? AND PayAccount = ? AND ConsumerName = ? ORDER BY SubmitDate DESC";
      Object[] arrayOfObject = { new Integer(paramInt1), new Integer(paramInt2), paramString1, paramString2 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str3 = localFFSResultSet.getColumnString("SrvrTID");
      } else {
        FFSDebug.log(str1 + "no records found", 6);
      }
    }
    catch (Exception localException1)
    {
      String str4 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, str4);
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
          FFSDebug.log("***" + str1 + "Error closing resultset: " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "Done.", 6);
    return str3;
  }
  
  public static String getRPPSPmtEntryMapSrvrTID(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws FFSException
  {
    String str1 = "RPPSDB.getRPPSPmtEntryMapSrvrTID: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    String str3 = null;
    FFSDebug.log(str1 + "start...", 6);
    try
    {
      str2 = "SELECT SrvrTID FROM RPPS_PmtEntryMap WHERE TraceNum = ? ORDER BY SubmitDate DESC";
      Object[] arrayOfObject = { new Integer(paramInt) };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str3 = localFFSResultSet.getColumnString("SrvrTID");
      } else {
        FFSDebug.log(str1 + "no records found", 6);
      }
    }
    catch (Exception localException1)
    {
      String str4 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, str4);
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
          FFSDebug.log("***Error closing resultset: " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "Done.", 6);
    return str3;
  }
  
  public static RPPSPmtInfo[] getRPPSPmtEntryMapByFileIdAndTraceNum(FFSConnectionHolder paramFFSConnectionHolder, int paramInt1, int paramInt2)
    throws FFSException
  {
    String str1 = "RPPSDB.getRPPSPmtEntryMapByFileIdAndTraceNum: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    RPPSPmtInfo localRPPSPmtInfo = null;
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    FFSDebug.log(str1 + "start...", 6);
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    int j = localPropertyConfig.getBatchSize();
    try
    {
      str2 = "SELECT SrvrTID, SubmitDate, BatchNum, TraceNum, PayAccount, FileId, ConsumerName FROM RPPS_PmtEntryMap WHERE FileId = ? and TraceNum >= ?";
      Object[] arrayOfObject = { new Integer(paramInt1), new Integer(paramInt2) };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localRPPSPmtInfo = jdMethod_do(localFFSResultSet);
        localArrayList.add(localRPPSPmtInfo);
        i++;
        if (i >= j) {
          break;
        }
      }
    }
    catch (Exception localException1)
    {
      String str3 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, str3);
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
          FFSDebug.log("***" + str1 + "Error closing resultset: " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "Done.", 6);
    return toEntryMapArray(localArrayList);
  }
  
  public static RPPSPmtFileInfo getRPPSPmtFileMap(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt, long paramLong1, long paramLong2)
    throws FFSException
  {
    String str1 = "RPPSDB.getRPPSPmtFileMap: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    RPPSPmtFileInfo localRPPSPmtFileInfo = new RPPSPmtFileInfo();
    FFSDebug.log(str1 + "start...", 6);
    try
    {
      str2 = "SELECT FileId, TransDate, TransTime, FileIdModifier, TotalEntryCount, TotalDebit, TotalCredit, FileName, Confirmed, Completed, FIID FROM RPPS_PmtFileMap WHERE FIId =? AND TransDate = ? AND TotalEntryCount = ? AND TotalDebit = ? AND TotalCredit = ? ORDER BY FileId DESC";
      Object[] arrayOfObject = { paramString1, paramString2, new Integer(paramInt), new Long(paramLong1).toString(), new Long(paramLong2).toString() };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localRPPSPmtFileInfo = jdMethod_for(localFFSResultSet);
      }
      else
      {
        localRPPSPmtFileInfo.setStatusCode(16020);
        localRPPSPmtFileInfo.setStatusMsg(" record not found");
      }
    }
    catch (Exception localException1)
    {
      String str3 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, str3);
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
          FFSDebug.log("***Error closing resultset: " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "Done.", 6);
    return localRPPSPmtFileInfo;
  }
  
  private static boolean a(RPPSRecordInfo paramRPPSRecordInfo)
    throws FFSException
  {
    if ((paramRPPSRecordInfo.getSrvrTId() == null) || (paramRPPSRecordInfo.getSrvrTId().trim().length() == 0))
    {
      paramRPPSRecordInfo.setStatusCode(16010);
      String str = "SrvrTID  contains null ";
      paramRPPSRecordInfo.setStatusMsg(str);
      FFSDebug.log("RPPSDB.vaildateRPPSEntryTmpInfo, " + str, 0);
      return false;
    }
    return true;
  }
  
  private static boolean a(RPPSPmtFileInfo paramRPPSPmtFileInfo)
    throws FFSException
  {
    return true;
  }
  
  private static boolean a(RPPSPmtInfo paramRPPSPmtInfo)
    throws FFSException
  {
    if ((paramRPPSPmtInfo.getSrvrTId() == null) || (paramRPPSPmtInfo.getSrvrTId().trim().length() == 0))
    {
      paramRPPSPmtInfo.setStatusCode(16010);
      String str = "SrvrTID  contains null ";
      paramRPPSPmtInfo.setStatusMsg(str);
      FFSDebug.log("RPPSDB.vaildateRPPSRecordInfo, " + str, 0);
      return false;
    }
    return true;
  }
  
  private static RPPSRecordInfo jdMethod_if(FFSResultSet paramFFSResultSet)
    throws FFSException
  {
    RPPSRecordInfo localRPPSRecordInfo = new RPPSRecordInfo();
    localRPPSRecordInfo.setEntryId(paramFFSResultSet.getColumnInt("EntryId"));
    localRPPSRecordInfo.setFiId(paramFFSResultSet.getColumnString("FIID"));
    localRPPSRecordInfo.setSrvrTId(paramFFSResultSet.getColumnString("SrvrTID"));
    localRPPSRecordInfo.setPayeeId(paramFFSResultSet.getColumnString("PayeeID"));
    localRPPSRecordInfo.setTxCode(paramFFSResultSet.getColumnInt("TXCode"));
    localRPPSRecordInfo.setStatusCode(0);
    localRPPSRecordInfo.setStatusMsg("Success");
    return localRPPSRecordInfo;
  }
  
  private static RPPSPmtFileInfo jdMethod_for(FFSResultSet paramFFSResultSet)
    throws FFSException
  {
    RPPSPmtFileInfo localRPPSPmtFileInfo = new RPPSPmtFileInfo();
    localRPPSPmtFileInfo.setFileId(paramFFSResultSet.getColumnInt("FileId"));
    localRPPSPmtFileInfo.setTransDate(paramFFSResultSet.getColumnString("TransDate"));
    localRPPSPmtFileInfo.setTransTime(paramFFSResultSet.getColumnString("TransTime"));
    localRPPSPmtFileInfo.setFileIdModifier(paramFFSResultSet.getColumnString("FileIdModifier"));
    localRPPSPmtFileInfo.setTotalEntryCount(paramFFSResultSet.getColumnInt("TotalEntryCount"));
    localRPPSPmtFileInfo.setTotalDebit(paramFFSResultSet.getColumnString("TotalDebit"));
    localRPPSPmtFileInfo.setTotalCredit(paramFFSResultSet.getColumnString("TotalCredit"));
    localRPPSPmtFileInfo.setFileName(paramFFSResultSet.getColumnString("FileName"));
    localRPPSPmtFileInfo.setConfirmed(paramFFSResultSet.getColumnString("Confirmed"));
    localRPPSPmtFileInfo.setCompleted(paramFFSResultSet.getColumnString("Completed"));
    localRPPSPmtFileInfo.setFiId(paramFFSResultSet.getColumnString("FIID"));
    localRPPSPmtFileInfo.setStatusCode(0);
    localRPPSPmtFileInfo.setStatusMsg("Success");
    return localRPPSPmtFileInfo;
  }
  
  private static RPPSPmtInfo jdMethod_do(FFSResultSet paramFFSResultSet)
    throws FFSException
  {
    RPPSPmtInfo localRPPSPmtInfo = new RPPSPmtInfo();
    localRPPSPmtInfo.setSrvrTId(paramFFSResultSet.getColumnString("SrvrTID"));
    localRPPSPmtInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
    localRPPSPmtInfo.setBatchNum(paramFFSResultSet.getColumnInt("BatchNum"));
    localRPPSPmtInfo.setTraceNum(paramFFSResultSet.getColumnInt("TraceNum"));
    localRPPSPmtInfo.setPayAccount(paramFFSResultSet.getColumnString("PayAccount"));
    localRPPSPmtInfo.setFileId(paramFFSResultSet.getColumnInt("FileId"));
    localRPPSPmtInfo.setConsumerName(paramFFSResultSet.getColumnString("ConsumerName"));
    localRPPSPmtInfo.setStatusCode(0);
    localRPPSPmtInfo.setStatusMsg("Success");
    return localRPPSPmtInfo;
  }
  
  public static RPPSRecordInfo[] toEntryTmpArray(ArrayList paramArrayList)
  {
    return (RPPSRecordInfo[])paramArrayList.toArray(new RPPSRecordInfo[0]);
  }
  
  public static RPPSPmtFileInfo[] toPmtFileMapArray(ArrayList paramArrayList)
  {
    return (RPPSPmtFileInfo[])paramArrayList.toArray(new RPPSPmtFileInfo[0]);
  }
  
  public static RPPSPmtInfo[] toEntryMapArray(ArrayList paramArrayList)
  {
    return (RPPSPmtInfo[])paramArrayList.toArray(new RPPSPmtInfo[0]);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.RPPSDB
 * JD-Core Version:    0.7.0.1
 */