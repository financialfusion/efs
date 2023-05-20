package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.util.ArrayList;

public class ACHFI
  implements DBConsts, FFSConst, ACHConsts
{
  public static ACHFIInfo create(FFSConnectionHolder paramFFSConnectionHolder, ACHFIInfo paramACHFIInfo)
    throws FFSException
  {
    String str1 = "ACHFI.create()";
    FFSDebug.log(str1 + " start ...", 6);
    if (!jdMethod_goto(paramACHFIInfo)) {
      return paramACHFIInfo;
    }
    String str2 = paramACHFIInfo.getFIId();
    String str3 = paramACHFIInfo.getODFIACHId();
    try
    {
      BPWFIInfo localBPWFIInfo = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, str2);
      if ((localBPWFIInfo == null) || (localBPWFIInfo.getStatusCode() != 0))
      {
        paramACHFIInfo.setStatusCode(23230);
        paramACHFIInfo.setStatusMsg("BPWFIID does not exist : with FIId = " + str2 + " in the " + "BPW_FIInfo table");
        return paramACHFIInfo;
      }
      localObject = getACHFIInfo(paramFFSConnectionHolder, str3, true);
      if (((ACHFIInfo)localObject).getStatusCode() == 0)
      {
        paramACHFIInfo.setStatusCode(23340);
        paramACHFIInfo.setStatusMsg("ACHFI already Exists");
        return paramACHFIInfo;
      }
      paramACHFIInfo.setACHFIId(DBUtil.getNextIndexStringWithPadding("ACHFIID", 4, '0'));
      paramACHFIInfo.setFIStatus("ACTIVE");
      paramACHFIInfo.setSubmitDate(FFSUtil.getDateString());
      paramACHFIInfo.setActivationDate(FFSUtil.getDateString());
      String str4 = "INSERT INTO ACH_FIInfo (ACHFIId, ODFIACHId, FIId, ODFIName, RDFIACHId, RDFIName, SubmitDate, ActivationDate, FIStatus, LogId, Memo, CashConDFI)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      Object[] arrayOfObject = { paramACHFIInfo.getACHFIId(), paramACHFIInfo.getODFIACHId(), paramACHFIInfo.getFIId(), paramACHFIInfo.getODFIName(), paramACHFIInfo.getRDFIACHId(), paramACHFIInfo.getRDFIName(), paramACHFIInfo.getSubmitDate(), paramACHFIInfo.getActivationDate(), paramACHFIInfo.getFIStatus(), paramACHFIInfo.getLogId(), FFSUtil.hashtableToString(paramACHFIInfo.getMemo()), paramACHFIInfo.getCashConDFI() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str4, arrayOfObject);
      paramACHFIInfo.setStatusCode(0);
      paramACHFIInfo.setStatusMsg("Success");
      FFSDebug.log(str1 + " end", 6);
      return paramACHFIInfo;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = str1 + " failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject, 0);
      throw new FFSException(localThrowable, (String)localObject);
    }
  }
  
  public static ACHFIInfo modACHFIInfo(FFSConnectionHolder paramFFSConnectionHolder, ACHFIInfo paramACHFIInfo)
    throws FFSException
  {
    String str1 = "ACHFI.modACHFIInfo";
    FFSDebug.log(str1 + " start ... ", 6);
    if (!jdMethod_goto(paramACHFIInfo)) {
      return paramACHFIInfo;
    }
    if (jdMethod_char(paramACHFIInfo)) {
      return paramACHFIInfo;
    }
    ACHFIInfo localACHFIInfo = getACHFIInfo(paramFFSConnectionHolder, paramACHFIInfo.getODFIACHId(), true);
    if ((localACHFIInfo.getStatusCode() == 0) && (localACHFIInfo.getACHFIId() != null) && (localACHFIInfo.getACHFIId().equals(paramACHFIInfo.getACHFIId()) != true))
    {
      paramACHFIInfo.setStatusCode(16140);
      paramACHFIInfo.setStatusMsg("This ODFIACHID is being used by another FI ");
      return paramACHFIInfo;
    }
    FFSDebug.log("New ACHFIInfo has: ODFIName = " + paramACHFIInfo.getODFIName() + ", RDFIACHID = " + paramACHFIInfo.getRDFIACHId() + ", Memo = " + FFSUtil.hashtableToString(paramACHFIInfo.getMemo()) + ", RDFIName = " + paramACHFIInfo.getRDFIName(), 6);
    String str2 = "UPDATE ACH_FIInfo SET ODFIACHId = ?, ODFIName = ?, RDFIACHId = ?, RDFIName = ?,  Memo = ?  WHERE ACHFIId = ? AND FIStatus != 'CLOSED' ";
    Object[] arrayOfObject = { paramACHFIInfo.getODFIACHId(), paramACHFIInfo.getODFIName(), paramACHFIInfo.getRDFIACHId(), paramACHFIInfo.getRDFIName(), FFSUtil.hashtableToString(paramACHFIInfo.getMemo()), paramACHFIInfo.getACHFIId() };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      FFSDebug.log(str1 + ": Number of records updated (ACH_FIInfo) " + i, 6);
      if (i == 0)
      {
        paramACHFIInfo.setStatusCode(16020);
        paramACHFIInfo.setStatusMsg("ACHFIInfo  record not found");
      }
      else
      {
        paramACHFIInfo.setStatusCode(0);
        paramACHFIInfo.setStatusMsg("Success");
      }
      localObject1 = paramACHFIInfo;
      return localObject1;
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = str1 + " failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable, (String)localObject1);
    }
    finally
    {
      FFSDebug.log(str1 + " end", 6);
    }
  }
  
  public static ACHFIInfo getACHFIInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    int i = 1;
    return getACHFIInfo(paramFFSConnectionHolder, paramString, true);
  }
  
  public static ACHFIInfo getACHFIInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("ACHFI.getACHFIInfo start ...achId =" + paramString + ", filterStatus =" + paramBoolean, 6);
    if (paramString == null)
    {
      str1 = "***ACHFI.getACHFIInfo failed: ODFIACHId is null";
      FFSDebug.log(str1, 0);
      localObject1 = new ACHFIInfo();
      ((ACHFIInfo)localObject1).setStatusCode(16000);
      ((ACHFIInfo)localObject1).setStatusMsg("ODFIACHId  is null");
      return localObject1;
    }
    String str1 = "SELECT ACHFIId, ODFIACHId, FIId, ODFIName, RDFIACHId, RDFIName,  SubmitDate, ActivationDate, FIStatus, LogId, Memo, CashConDFI  FROM ACH_FIInfo WHERE ODFIACHId = ?";
    if (paramBoolean) {
      str1 = str1 + " AND FIStatus != 'CLOSED'";
    }
    Object localObject1 = null;
    ACHFIInfo localACHFIInfo = null;
    Object[] arrayOfObject = { paramString };
    try
    {
      localACHFIInfo = new ACHFIInfo();
      localObject1 = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (((FFSResultSet)localObject1).getNextRow())
      {
        localACHFIInfo = a((FFSResultSet)localObject1, localACHFIInfo);
      }
      else
      {
        localACHFIInfo.setStatusCode(16020);
        localACHFIInfo.setStatusMsg("ACHFIInfo with ODFIACHId " + paramString + ": " + " record not found");
      }
    }
    catch (Throwable localThrowable1)
    {
      String str2 = "*** ACHFI.getACHFIInfo failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    finally
    {
      if (localObject1 != null) {
        try
        {
          ((FFSResultSet)localObject1).close();
          localObject1 = null;
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log("ACHFI.getACHFIInfo: failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
    FFSDebug.log("ACHFI.getACHFIInfo end", 6);
    return localACHFIInfo;
  }
  
  public static ACHFIInfo[] getFIInfoByRTN(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHFI.getFIInfoByRTN start ...", 6);
    FFSDebug.log("ACHFI.getFIInfoByRTN: ODFIRTN is " + paramString, 6);
    ArrayList localArrayList = new ArrayList();
    ACHFIInfo localACHFIInfo = null;
    String str = "SELECT a.ACHFIId, a.ODFIACHId, a.FIId, a.ODFIName, a.RDFIACHId, a.RDFIName,  a.SubmitDate, a.ActivationDate, a.FIStatus, a.LogId, a.Memo, a.CashConDFI  FROM ACH_FIInfo a, BPW_FIInfo b WHERE  b.FIRTN = ? AND a.FIStatus != 'CLOSED' AND a.FIId = b.FIId";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localACHFIInfo = new ACHFIInfo();
        a(localFFSResultSet, localACHFIInfo);
        localArrayList.add(localACHFIInfo);
      }
      if (localACHFIInfo == null)
      {
        FFSDebug.log("***ACHFI.getFIInfoByRTN no records found", 6);
        localACHFIInfo = new ACHFIInfo();
        localACHFIInfo.setStatusCode(16020);
        localACHFIInfo.setStatusMsg("ACHFIInfo record not found");
        localArrayList.add(localACHFIInfo);
      }
      ACHFIInfo[] arrayOfACHFIInfo = (ACHFIInfo[])localArrayList.toArray(new ACHFIInfo[0]);
      FFSDebug.log("ACHFI.getFIInfoByRTN end", 6);
      localObject1 = arrayOfACHFIInfo;
      return localObject1;
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = "*** ACHFI.getFIInfoByRTN failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
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
        catch (Throwable localThrowable2)
        {
          FFSDebug.log("ACHFI.getACHFIInfoByRTN: failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
  }
  
  public static ACHFIInfo canACHFIInfo(FFSConnectionHolder paramFFSConnectionHolder, ACHFIInfo paramACHFIInfo)
    throws FFSException
  {
    FFSDebug.log("ACHFI.delete start ...", 6);
    try
    {
      if ((jdMethod_new(paramACHFIInfo)) || (jdMethod_else(paramACHFIInfo))) {
        return paramACHFIInfo;
      }
      String str1 = paramACHFIInfo.getODFIACHId();
      str2 = "UPDATE ACH_FIInfo SET FIStatus = 'CLOSED' WHERE ODFIACHId = ? AND FIStatus != 'CLOSED' ";
      Object[] arrayOfObject = { str1 };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      FFSDebug.log("ACHFI.cancelACHFI: Number of ACHFI records updated " + i, 6);
      if (i == 0)
      {
        paramACHFIInfo.setStatusCode(16020);
        paramACHFIInfo.setStatusMsg("ACHFIInfo  record not found");
      }
      else if (i > 1)
      {
        paramACHFIInfo.setStatusCode(23270);
        paramACHFIInfo.setStatusMsg("More than One Record found");
      }
      else
      {
        ACHBatch.updateStatusByFIACHId(paramFFSConnectionHolder, str1, "CANCELEDON");
        ACHBatch.updateRecBatchStatusByFIACHId(paramFFSConnectionHolder, str1, "CANCELEDON");
        ACHCompany.updateStatusByODFIACHId(paramFFSConnectionHolder, str1, "CLOSED");
        ACHFile.updateStatusByODFIACHId(paramFFSConnectionHolder, str1, "CANCELEDON");
        ACHBatchTemplate.deleteTemplateByODFIACHId(paramFFSConnectionHolder, str1);
        paramACHFIInfo.setFIStatus("CLOSED");
        paramACHFIInfo.setStatusCode(0);
        paramACHFIInfo.setStatusMsg("Success");
      }
      return paramACHFIInfo;
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** ACHFI.delete failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public static String getACHFIStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHFI.getACHFIStatus start ...", 6);
    String str1 = "SELECT FIStatus FROM ACH_FIInfo WHERE ODFIACHId = ?";
    String str2 = null;
    if (paramString == null) {
      return null;
    }
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str2 = localFFSResultSet.getColumnString(1);
      }
      String str3 = str2;
      return str3;
    }
    catch (Throwable localThrowable1)
    {
      String str4 = "*** ACHFI.getACHFIStatus failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable1, str4);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log("ACHFI.getACHFIStatus: failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
  }
  
  public static int updateStatusForACHFI(ACHFIInfo paramACHFIInfo, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    if (paramACHFIInfo == null)
    {
      paramACHFIInfo = new ACHFIInfo();
      paramACHFIInfo.setStatusCode(16000);
      str1 = "ACHFIInfo object  is null";
      paramACHFIInfo.setStatusMsg(str1);
      FFSDebug.log("ACHFI.updateStatusForACHFI, " + str1);
      return -1;
    }
    if (paramString == null)
    {
      paramACHFIInfo.setStatusCode(16000);
      str1 = "status  is null";
      paramACHFIInfo.setStatusMsg(str1);
      FFSDebug.log("ACHFI.updateStatusForACHFI, " + str1);
      return -1;
    }
    FFSDebug.log("ACHFI.updateStatusForACHFI: start, oDFIACHId=" + paramACHFIInfo.getODFIACHId(), 6);
    String str1 = null;
    Object[] arrayOfObject = { paramString, paramACHFIInfo.getODFIACHId() };
    if (paramString.equals("ACTIVE"))
    {
      paramACHFIInfo.setActivationDate(FFSUtil.getDateString());
      str1 = "Update ACH_FIInfo set FIStatus = ?, ActivationDate = ?  Where ODFIACHId = ?";
      arrayOfObject = new Object[3];
      arrayOfObject[0] = paramString;
      arrayOfObject[1] = paramACHFIInfo.getActivationDate();
      arrayOfObject[2] = paramACHFIInfo.getODFIACHId();
    }
    else
    {
      str1 = "UPDATE ACH_FIInfo SET FIStatus = ?  WHERE ODFIACHId = ? ";
    }
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ACHFI.updateStatusForACHFI failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    FFSDebug.log("ACHFI.updateStatusForACHFI: done", 6);
    return i;
  }
  
  public static ACHFIInfo activateACHFI(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHFI.activateACHFI: start, oDFIACHId=" + paramString, 6);
    ACHFIInfo localACHFIInfo = null;
    if (paramString == null)
    {
      localACHFIInfo = new ACHFIInfo();
      localACHFIInfo.setStatusCode(16000);
      localACHFIInfo.setStatusMsg("ODFIACHId  is null");
      return localACHFIInfo;
    }
    try
    {
      boolean bool = false;
      localACHFIInfo = getACHFIInfo(paramFFSConnectionHolder, paramString, bool);
      if ((localACHFIInfo == null) || (localACHFIInfo.getStatusCode() != 0))
      {
        localACHFIInfo.setStatusCode(16020);
        localACHFIInfo.setStatusMsg("ACHFI with ODFIACHID = " + paramString + " " + " record not found");
        return localACHFIInfo;
      }
      if (!localACHFIInfo.getFIStatus().equals("CLOSED"))
      {
        localACHFIInfo.setStatusCode(23280);
        localACHFIInfo.setStatusMsg("ACH FI is already ACTIVE , ODFIACHId " + paramString);
        return localACHFIInfo;
      }
      str1 = BPWFI.getBPWFIStatus(paramFFSConnectionHolder, localACHFIInfo.getFIId());
      if (str1 == null)
      {
        localACHFIInfo.setStatusCode(23240);
        localACHFIInfo.setStatusMsg("Activation Failed : BFWFI =" + localACHFIInfo.getFIId() + " does not exist");
        return localACHFIInfo;
      }
      if (str1.equals("CLOSED"))
      {
        localACHFIInfo.setStatusCode(23240);
        localACHFIInfo.setStatusMsg("Activation Failed : BFWFI =" + localACHFIInfo.getFIId() + " exists but the" + " status is CLOSED ");
        return localACHFIInfo;
      }
      localACHFIInfo.setFIStatus("ACTIVE");
      updateStatusForACHFI(localACHFIInfo, "ACTIVE", paramFFSConnectionHolder);
      localACHFIInfo.setStatusCode(0);
      localACHFIInfo.setStatusMsg("Success");
    }
    catch (Exception localException)
    {
      String str1 = "*** ACHFI.activateACHFI failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, str1);
    }
    FFSDebug.log("ACHFI.activateACHFI: done", 6);
    return localACHFIInfo;
  }
  
  public static boolean isACHFIClosed(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHACHFI.isACHFIClosed: start, compId=" + paramString, 6);
    boolean bool = false;
    String str = getACHFIStatus(paramFFSConnectionHolder, paramString);
    if ((str == null) || (str.equals("CLOSED"))) {
      bool = true;
    }
    FFSDebug.log("ACHACHFI.isACHFIClosed: done. Value" + bool, 6);
    return bool;
  }
  
  public static void canACHFIInfoByFIId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHFI.canACHFIInfoByFIId start, fIId =" + paramString, 6);
    if (paramString == null) {
      FFSDebug.log("***ACHFI.canACHFIInfoByFIId failed : fIId is null", 0);
    }
    FFSResultSet localFFSResultSet = null;
    try
    {
      String str = "SELECT ODFIACHId, FIId, ODFIName, RDFIACHId, RDFIName,  SubmitDate, ActivationDate, FIStatus, LogId, CashConDFI  FROM ACH_FIInfo WHERE FIId = ? AND FIStatus != 'CLOSED'";
      localObject1 = new Object[] { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, (Object[])localObject1);
      ACHFIInfo localACHFIInfo = null;
      while (localFFSResultSet.getNextRow())
      {
        localACHFIInfo = new ACHFIInfo();
        localACHFIInfo.setODFIACHId(localFFSResultSet.getColumnString(1));
        canACHFIInfo(paramFFSConnectionHolder, localACHFIInfo);
      }
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = "*** ACHFI.canACHFIInfoByFIId failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
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
        catch (Throwable localThrowable2)
        {
          FFSDebug.log("ACHFI.canACHFIInfoByFIId: failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
  }
  
  public static ACHFIInfo getACHFIInfoByACHFIId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHFI.getACHFIInfoByACHFIId start ...achFIId =" + paramString, 6);
    if (paramString == null)
    {
      str1 = "***ACHFI.getACHFIInfoByACHFIId failed: achFIId is null";
      FFSDebug.log(str1, 0);
      localObject1 = new ACHFIInfo();
      ((ACHFIInfo)localObject1).setStatusCode(16000);
      ((ACHFIInfo)localObject1).setStatusMsg("ACHFIId  is null");
      return localObject1;
    }
    String str1 = "SELECT ACHFIId, ODFIACHId, FIId, ODFIName, RDFIACHId, RDFIName,  SubmitDate, ActivationDate, FIStatus, LogId, Memo, CashConDFI  FROM ACH_FIInfo WHERE ACHFIId = ? AND FIStatus != 'CLOSED'";
    Object localObject1 = null;
    ACHFIInfo localACHFIInfo = null;
    Object[] arrayOfObject = { paramString };
    try
    {
      localACHFIInfo = new ACHFIInfo();
      localObject1 = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (((FFSResultSet)localObject1).getNextRow())
      {
        localACHFIInfo = a((FFSResultSet)localObject1, localACHFIInfo);
      }
      else
      {
        localACHFIInfo.setStatusCode(16020);
        localACHFIInfo.setStatusMsg("ACHFIInfo with achFIId " + paramString + ": " + " record not found");
      }
    }
    catch (Throwable localThrowable1)
    {
      String str2 = "*** ACHFI.getACHFIInfoByACHFIId failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable1, str2);
    }
    finally
    {
      if (localObject1 != null) {
        try
        {
          ((FFSResultSet)localObject1).close();
          localObject1 = null;
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log("ACHFI.getACHFIInfoByACHFIId: failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
    FFSDebug.log("ACHFI.getACHFIInfoByACHFIId end", 6);
    return localACHFIInfo;
  }
  
  private static ACHFIInfo a(FFSResultSet paramFFSResultSet, ACHFIInfo paramACHFIInfo)
    throws FFSException
  {
    paramACHFIInfo.setACHFIId(paramFFSResultSet.getColumnString("ACHFIID"));
    paramACHFIInfo.setODFIACHId(paramFFSResultSet.getColumnString("ODFIACHId"));
    paramACHFIInfo.setFIId(paramFFSResultSet.getColumnString("FIId"));
    paramACHFIInfo.setODFIName(paramFFSResultSet.getColumnString("ODFIName"));
    paramACHFIInfo.setRDFIACHId(paramFFSResultSet.getColumnString("RDFIACHId"));
    paramACHFIInfo.setRDFIName(paramFFSResultSet.getColumnString("RDFIName"));
    paramACHFIInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
    paramACHFIInfo.setActivationDate(paramFFSResultSet.getColumnString("ActivationDate"));
    paramACHFIInfo.setFIStatus(paramFFSResultSet.getColumnString("FIStatus"));
    paramACHFIInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    paramACHFIInfo.setMemo(FFSUtil.stringToHashtable(paramFFSResultSet.getColumnString("Memo")));
    paramACHFIInfo.setCashConDFI(paramFFSResultSet.getColumnString("CashConDFI"));
    paramACHFIInfo.setStatusCode(0);
    paramACHFIInfo.setStatusMsg("Success");
    return paramACHFIInfo;
  }
  
  private static boolean jdMethod_goto(ACHFIInfo paramACHFIInfo)
    throws FFSException
  {
    return (!jdMethod_new(paramACHFIInfo)) && (!jdMethod_try(paramACHFIInfo)) && (!jdMethod_byte(paramACHFIInfo)) && (!jdMethod_case(paramACHFIInfo)) && (!jdMethod_else(paramACHFIInfo)) && (jdMethod_int(paramACHFIInfo)) && (jdMethod_long(paramACHFIInfo)) && (jdMethod_for(paramACHFIInfo)) && (jdMethod_do(paramACHFIInfo));
  }
  
  private static boolean jdMethod_int(ACHFIInfo paramACHFIInfo)
  {
    boolean bool = true;
    try
    {
      bool = BPWUtil.validateABA(paramACHFIInfo.getODFIACHId());
      if (!bool)
      {
        paramACHFIInfo.setStatusCode(23630);
        paramACHFIInfo.setStatusMsg("Invalid ODFIACHId : " + paramACHFIInfo.getODFIACHId());
      }
    }
    catch (FFSException localFFSException)
    {
      paramACHFIInfo.setStatusCode(23640);
      paramACHFIInfo.setStatusMsg("Failed to validate ODFIACHId ODFIACHID = " + paramACHFIInfo.getODFIACHId() + ". Exception: " + localFFSException.toString());
      bool = false;
    }
    return bool;
  }
  
  private static boolean jdMethod_long(ACHFIInfo paramACHFIInfo)
  {
    boolean bool = true;
    try
    {
      bool = BPWUtil.validateABA(paramACHFIInfo.getRDFIACHId());
      if (!bool)
      {
        paramACHFIInfo.setStatusCode(23650);
        paramACHFIInfo.setStatusMsg("Invalid RDFIACHId : " + paramACHFIInfo.getRDFIACHId());
      }
    }
    catch (FFSException localFFSException)
    {
      paramACHFIInfo.setStatusCode(23660);
      paramACHFIInfo.setStatusMsg("Failed to validate RDFIACHId RDFIACHID = " + paramACHFIInfo.getRDFIACHId() + ". Exception: " + localFFSException.toString());
      bool = false;
    }
    return bool;
  }
  
  private static boolean jdMethod_new(ACHFIInfo paramACHFIInfo)
  {
    if (paramACHFIInfo == null)
    {
      String str = " Null ACHFIInfo Object passed";
      FFSDebug.log(str, 0);
      paramACHFIInfo = new ACHFIInfo();
      paramACHFIInfo.setStatusCode(16000);
      paramACHFIInfo.setStatusMsg("ACHFIInfo  is null");
      return true;
    }
    return false;
  }
  
  private static boolean jdMethod_try(ACHFIInfo paramACHFIInfo)
  {
    if ((paramACHFIInfo.getFIId() == null) || (paramACHFIInfo.getFIId().trim().length() == 0))
    {
      paramACHFIInfo.setStatusCode(16010);
      String str = "ACHFIInfo.FIId   contains null ";
      paramACHFIInfo.setStatusMsg(str);
      FFSDebug.log(str, 0);
      return true;
    }
    return false;
  }
  
  private static boolean jdMethod_byte(ACHFIInfo paramACHFIInfo)
  {
    if ((paramACHFIInfo.getRDFIACHId() == null) || (paramACHFIInfo.getRDFIACHId().trim().length() == 0))
    {
      paramACHFIInfo.setStatusCode(16010);
      String str = "ACHFIInfo.RDFIACHId   contains null ";
      paramACHFIInfo.setStatusMsg(str);
      FFSDebug.log(str, 0);
      return true;
    }
    return false;
  }
  
  private static boolean jdMethod_case(ACHFIInfo paramACHFIInfo)
  {
    if ((paramACHFIInfo.getRDFIName() == null) || (paramACHFIInfo.getRDFIName().trim().length() == 0))
    {
      paramACHFIInfo.setStatusCode(16010);
      String str = "ACHFIInfo.RDFINAME   contains null ";
      paramACHFIInfo.setStatusMsg(str);
      FFSDebug.log(str, 0);
      return true;
    }
    return false;
  }
  
  private static boolean jdMethod_else(ACHFIInfo paramACHFIInfo)
  {
    if (paramACHFIInfo.getODFIACHId() == null)
    {
      String str = "ACHFIInfo.ODFIACHID  contains null ";
      FFSDebug.log(str, 0);
      paramACHFIInfo.setStatusCode(16010);
      paramACHFIInfo.setStatusMsg("ACHFIInfo  contains null  ODFIACHId");
      return true;
    }
    return false;
  }
  
  private static boolean jdMethod_char(ACHFIInfo paramACHFIInfo)
  {
    if (paramACHFIInfo.getACHFIId() == null)
    {
      String str = "ACHFIInfo.ACHFIID  contains null ";
      FFSDebug.log(str, 0);
      paramACHFIInfo.setStatusCode(16010);
      paramACHFIInfo.setStatusMsg("ACHFIInfo  contains null  ACHFIID");
      return true;
    }
    return false;
  }
  
  private static boolean jdMethod_do(ACHFIInfo paramACHFIInfo)
  {
    boolean bool = false;
    bool = BPWUtil.validateODFIName(paramACHFIInfo.getODFIName());
    if (!bool)
    {
      paramACHFIInfo.setStatusCode(23720);
      paramACHFIInfo.setStatusMsg("ODFIName length has exceeded the maximum value = 23");
    }
    return bool;
  }
  
  private static boolean jdMethod_for(ACHFIInfo paramACHFIInfo)
  {
    boolean bool = false;
    bool = BPWUtil.validateRDFIName(paramACHFIInfo.getRDFIName());
    if (!bool)
    {
      paramACHFIInfo.setStatusCode(23730);
      paramACHFIInfo.setStatusMsg("RDFIName length has exceeded the maximum value = 23");
    }
    return bool;
  }
  
  public static ACHFIInfo[] getACHFIInfosByFIID(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ACHFI.getACHFIInfoByFIID: ";
    FFSDebug.log(str1 + "start ...", 6);
    FFSDebug.log(str1 + " FIID is " + paramString, 6);
    ArrayList localArrayList = new ArrayList();
    ACHFIInfo localACHFIInfo = null;
    String str2 = "SELECT ACHFIId, ODFIACHId, FIId, ODFIName, RDFIACHId, RDFIName,  SubmitDate, ActivationDate, FIStatus, LogId, Memo, CashConDFI  FROM ACH_FIInfo WHERE FIId = ? AND FIStatus != 'CLOSED'";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localACHFIInfo = new ACHFIInfo();
        a(localFFSResultSet, localACHFIInfo);
        localArrayList.add(localACHFIInfo);
      }
      if (localArrayList.size() > 0)
      {
        arrayOfACHFIInfo = (ACHFIInfo[])localArrayList.toArray(new ACHFIInfo[0]);
        FFSDebug.log(str1 + " end", 6);
        localObject1 = arrayOfACHFIInfo;
        return localObject1;
      }
      ACHFIInfo[] arrayOfACHFIInfo = null;
      return arrayOfACHFIInfo;
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = str1 + "*** failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
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
        catch (Throwable localThrowable2)
        {
          FFSDebug.log(str1 + "failed " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
    }
  }
  
  public static ACHFIInfo getCutOffACHFIInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ACHFI.getCutOffACHFIInfo :";
    FFSDebug.log(str1 + "start, FIId =" + paramString, 6);
    String str2 = "SELECT ACHFIId, ODFIACHId, FIId, ODFIName, RDFIACHId, RDFIName,  SubmitDate, ActivationDate, FIStatus, LogId, Memo, CashConDFI  FROM ACH_FIInfo WHERE FIId = ? AND (CashConDFI is null OR CashConDFI != 'N') Order By CashConDFI";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString };
    ACHFIInfo localACHFIInfo = new ACHFIInfo();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localACHFIInfo = a(localFFSResultSet, localACHFIInfo);
      }
      else
      {
        localACHFIInfo.setStatusCode(16020);
        localACHFIInfo.setStatusMsg(" record not found");
      }
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, str4, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end, status =" + localACHFIInfo.getStatusMsg(), 6);
    return localACHFIInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ACHFI
 * JD-Core Version:    0.7.0.1
 */