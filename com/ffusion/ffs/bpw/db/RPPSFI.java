package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.RPPSFIInfo;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;

public class RPPSFI
  implements DBConsts, FFSConst, ACHConsts
{
  public static RPPSFIInfo createRPPSFIInfo(FFSConnectionHolder paramFFSConnectionHolder, RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    String str1 = "RPPSFIInfo.createRPPSFIInfo: ";
    String str2 = null;
    String str3 = null;
    String str4 = null;
    int i = 0;
    FFSDebug.log(str1 + "start...", 6);
    if (paramRPPSFIInfo == null)
    {
      localObject = "*** " + str1 + "failed: " + "Null FI object passed";
      FFSDebug.log((String)localObject, 0);
      paramRPPSFIInfo = new RPPSFIInfo();
      paramRPPSFIInfo.setStatusCode(16000);
      paramRPPSFIInfo.setStatusMsg("RPPSFIInfo  is null");
      return paramRPPSFIInfo;
    }
    if (!a(paramFFSConnectionHolder, paramRPPSFIInfo)) {
      return paramRPPSFIInfo;
    }
    str2 = paramRPPSFIInfo.getFiId();
    str3 = paramRPPSFIInfo.getFiRPPSId();
    str4 = "SELECT FIId, FIRTN, FIType, FIName, NickName, FIStatus, FIDesc, FIGroup, FIRank, AddrLine1, AddrLine2, AddrLine3, City, State, PostalCode, Country, DayPhone, Extension, SubmitDate, ContactName , ActivationDate, AmtLimit, SWIFTRTN, CHIPSRTN, OtherRTN, LogId, CurrencyCode, ACHTransWarehouse, ACHMaxNoFutureDays, ACHSameDayEffDate, ETFDepositAcct, ETFDepositAcctType, ETFMinDepositAmount, ETFMaxDepositAmount FROM BPW_FIInfo WHERE FIId = ?  AND FIStatus != 'CLOSED'";
    Object localObject = jdMethod_byte(paramFFSConnectionHolder, str3, false);
    if ((localObject != null) && (((RPPSFIInfo)localObject).getStatusCode() == 0))
    {
      paramRPPSFIInfo.setStatusCode(23370);
      paramRPPSFIInfo.setStatusMsg("RPPS FI already exists ");
    }
    else
    {
      localObject = jdMethod_try(paramFFSConnectionHolder, str2, false);
      if ((localObject != null) && (((RPPSFIInfo)localObject).getStatusCode() == 0))
      {
        paramRPPSFIInfo.setStatusCode(23371);
        paramRPPSFIInfo.setStatusMsg("FI already has RPPS Info");
      }
      else
      {
        try
        {
          paramRPPSFIInfo.setRPPSFIId(DBUtil.getNextIndexStringWithPadding("RPPSFIID", 4, '0'));
          paramRPPSFIInfo.setFiStatus("ACTIVE");
          paramRPPSFIInfo.setSubmitDate(FFSUtil.getDateString());
          paramRPPSFIInfo.setActivationDate(FFSUtil.getDateString());
          str4 = "INSERT INTO RPPS_FIInfo (RPPSFIId, FIRPPSId, FIID, FIRPPSName, RPPSId, RPPSName, SubmitDate, ActivationDate, FIStatus, LogId, Memo, CreditCap) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
          Object[] arrayOfObject = { paramRPPSFIInfo.getRPPSFIId(), paramRPPSFIInfo.getFiRPPSId(), paramRPPSFIInfo.getFiId(), paramRPPSFIInfo.getFiRPPSName(), paramRPPSFIInfo.getRppsId(), paramRPPSFIInfo.getRppsName(), paramRPPSFIInfo.getSubmitDate(), paramRPPSFIInfo.getActivationDate(), paramRPPSFIInfo.getFiStatus(), paramRPPSFIInfo.getLogId(), FFSUtil.hashtableToString(paramRPPSFIInfo.getMemo()), paramRPPSFIInfo.getCreditCap() };
          i = DBUtil.executeStatement(paramFFSConnectionHolder, str4, arrayOfObject);
          paramRPPSFIInfo.setStatusCode(0);
          paramRPPSFIInfo.setStatusMsg("Success");
        }
        catch (Exception localException)
        {
          String str5 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException);
          FFSDebug.log(str5, 0);
          throw new FFSException(localException, str5);
        }
      }
    }
    FFSDebug.log(str1 + "done.", 6);
    return paramRPPSFIInfo;
  }
  
  public static RPPSFIInfo modRPPSFIInfo(FFSConnectionHolder paramFFSConnectionHolder, RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    String str1 = "RPPSFI.modRPPSFIInfo: ";
    String str2 = null;
    int i = 0;
    FFSDebug.log(str1 + "start...", 6);
    if (paramRPPSFIInfo == null)
    {
      localObject1 = "***" + str1 + "failed: " + "Null FI object passed";
      localObject2 = new RPPSFIInfo();
      FFSDebug.log((String)localObject1, 0);
      ((RPPSFIInfo)localObject2).setStatusCode(16000);
      ((RPPSFIInfo)localObject2).setStatusMsg("RPPSFIInfo  is null");
      return localObject2;
    }
    if ((paramRPPSFIInfo.getRPPSFIId() == null) || (paramRPPSFIInfo.getRPPSFIId().trim().length() == 0))
    {
      paramRPPSFIInfo.setStatusCode(16010);
      localObject1 = "RPPSFIId  contains null ";
      paramRPPSFIInfo.setStatusMsg((String)localObject1);
      FFSDebug.log("RPPSFI.modRPPSFIInfo, " + (String)localObject1, 0);
      return paramRPPSFIInfo;
    }
    if (!a(paramFFSConnectionHolder, paramRPPSFIInfo)) {
      return paramRPPSFIInfo;
    }
    Object localObject1 = jdMethod_byte(paramFFSConnectionHolder, paramRPPSFIInfo.getFiRPPSId(), false);
    if ((((RPPSFIInfo)localObject1).getStatusCode() == 0) && (((RPPSFIInfo)localObject1).getRPPSFIId() != null) && (((RPPSFIInfo)localObject1).getRPPSFIId().equals(paramRPPSFIInfo.getRPPSFIId()) != true))
    {
      paramRPPSFIInfo.setStatusCode(16150);
      paramRPPSFIInfo.setStatusMsg("This FIRPPSID is being used by another FI ");
      return paramRPPSFIInfo;
    }
    str2 = "UPDATE RPPS_FIInfo SET FIRPPSId = ?, FIRPPSName = ?, RPPSId = ?, RPPSName = ?, Memo = ?, FIStatus = ? WHERE RPPSFIId = ? AND FIStatus != 'CLOSED'";
    Object localObject2 = { paramRPPSFIInfo.getFiRPPSId(), paramRPPSFIInfo.getFiRPPSName(), paramRPPSFIInfo.getRppsId(), paramRPPSFIInfo.getRppsName(), FFSUtil.hashtableToString(paramRPPSFIInfo.getMemo()), paramRPPSFIInfo.getRPPSFIId() };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject2);
      FFSDebug.log(str1 + "Number of records updated (RPPS_FIInfo): " + i, 6);
      if (i == 0)
      {
        paramRPPSFIInfo.setStatusCode(16020);
        paramRPPSFIInfo.setStatusMsg("RPPSFIInfo  record not found");
      }
      else
      {
        paramRPPSFIInfo.setStatusCode(0);
        paramRPPSFIInfo.setStatusMsg("Success");
      }
    }
    catch (Exception localException)
    {
      String str3 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "done.", 6);
    return paramRPPSFIInfo;
  }
  
  public static RPPSFIInfo canRPPSFIInfo(FFSConnectionHolder paramFFSConnectionHolder, RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    String str1 = "RPPSFI.canRPPSFIInfo:";
    String str2 = null;
    String str3 = null;
    int i = 0;
    FFSDebug.log(str1 + "start...", 6);
    try
    {
      if (paramRPPSFIInfo == null)
      {
        localObject = "***" + str1 + "failed: " + "Null FI object passed";
        FFSDebug.log((String)localObject, 0);
        paramRPPSFIInfo = new RPPSFIInfo();
        paramRPPSFIInfo.setStatusCode(16000);
        paramRPPSFIInfo.setStatusMsg("RPPSFIInfo  is null");
        return paramRPPSFIInfo;
      }
      str2 = paramRPPSFIInfo.getFiRPPSId();
      if (str2 == null)
      {
        localObject = "***" + str1 + "failed: " + "FIRPPSId is null";
        FFSDebug.log((String)localObject, 0);
        paramRPPSFIInfo.setStatusCode(16010);
        paramRPPSFIInfo.setStatusMsg("RPPSFIInfo  contains null  FIRPPSId");
        return paramRPPSFIInfo;
      }
      str3 = "UPDATE RPPS_FIInfo SET FIStatus = 'CLOSED' WHERE RPPSFIId = ? AND FIStatus != 'CLOSED' ";
      Object localObject = { str2 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, (Object[])localObject);
      FFSDebug.log(str1 + "Number of RPPSFI records close: " + i, 6);
      if (i == 0)
      {
        paramRPPSFIInfo.setStatusCode(16020);
        paramRPPSFIInfo.setStatusMsg("RPPSFIInfo  record not found");
      }
      else
      {
        paramRPPSFIInfo.setFiStatus("CLOSED");
        paramRPPSFIInfo.setStatusCode(0);
        paramRPPSFIInfo.setStatusMsg("Success");
      }
    }
    catch (Exception localException)
    {
      String str4 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, str4);
    }
    FFSDebug.log(str1 + "done.", 6);
    return paramRPPSFIInfo;
  }
  
  public static RPPSFIInfo activateRPPSFIInfo(FFSConnectionHolder paramFFSConnectionHolder, RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    String str1 = "RPPSFI.activateRPPSFIInfo: ";
    String str2 = FFSUtil.getDateString();
    String str3 = null;
    String str4 = null;
    String str5 = null;
    int i = 0;
    FFSDebug.log(str1 + "start...", 6);
    try
    {
      if (paramRPPSFIInfo == null)
      {
        localObject1 = "***" + str1 + "failed: " + "Null FI object passed";
        FFSDebug.log((String)localObject1, 0);
        paramRPPSFIInfo = new RPPSFIInfo();
        paramRPPSFIInfo.setStatusCode(16000);
        paramRPPSFIInfo.setStatusMsg("RPPSFIInfo  is null");
        return paramRPPSFIInfo;
      }
      str3 = paramRPPSFIInfo.getFiRPPSId();
      if (str3 == null)
      {
        localObject1 = "***" + str1 + "failed: " + "FIRPPSId is null";
        FFSDebug.log((String)localObject1, 0);
        paramRPPSFIInfo.setStatusCode(16010);
        paramRPPSFIInfo.setStatusMsg("RPPSFIInfo  contains null  FIRPPSId");
        return paramRPPSFIInfo;
      }
      str4 = paramRPPSFIInfo.getRPPSFIId();
      if (str4 == null)
      {
        localObject1 = "***" + str1 + "failed: " + "RPPSFIId is null";
        FFSDebug.log((String)localObject1, 0);
        paramRPPSFIInfo.setStatusCode(16010);
        paramRPPSFIInfo.setStatusMsg("RPPSFIInfo  contains null  RPPSFIId");
        return paramRPPSFIInfo;
      }
      Object localObject1 = jdMethod_byte(paramFFSConnectionHolder, str3, false);
      if (((RPPSFIInfo)localObject1).getStatusCode() == 0)
      {
        paramRPPSFIInfo.setStatusCode(16150);
        paramRPPSFIInfo.setStatusMsg("This FIRPPSID is being used by another FI ");
        return paramRPPSFIInfo;
      }
      str5 = "UPDATE RPPS_FIInfo SET FIStatus = 'ACTIVE', ActivationDate = ? WHERE RPPSFIId = ? AND FIStatus= 'CLOSED' ";
      localObject2 = new Object[] { str2, str4 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str5, (Object[])localObject2);
      FFSDebug.log(str1 + "Number of RPPSFI records activated: " + i, 6);
      if (i == 0)
      {
        paramRPPSFIInfo.setStatusCode(16020);
        paramRPPSFIInfo.setStatusMsg("RPPSFIInfo  record not found");
      }
      else
      {
        paramRPPSFIInfo.setFiStatus("ACTIVE");
        paramRPPSFIInfo.setStatusCode(0);
        paramRPPSFIInfo.setStatusMsg("Success");
      }
    }
    catch (Exception localException)
    {
      Object localObject2 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localException, (String)localObject2);
    }
    FFSDebug.log(str1 + "done.", 6);
    return paramRPPSFIInfo;
  }
  
  public static RPPSFIInfo getRPPSFIInfoByFIRPPSId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    return jdMethod_byte(paramFFSConnectionHolder, paramString, false);
  }
  
  private static RPPSFIInfo jdMethod_try(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "RPPSFI.getRPPSFIInfoByFISId: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    RPPSFIInfo localRPPSFIInfo = new RPPSFIInfo();
    FFSDebug.log(str1 + "start...", 6);
    if (paramString == null)
    {
      localObject1 = "***" + str1 + "failed: " + "FIId is null";
      FFSDebug.log((String)localObject1, 0);
      localRPPSFIInfo.setStatusCode(16000);
      localRPPSFIInfo.setStatusMsg("FIId  is null");
      return localRPPSFIInfo;
    }
    str2 = "SELECT RPPSFIId, FIRPPSId, FIID, FIRPPSName, RPPSId, RPPSName, SubmitDate, ActivationDate, FIStatus, CreditCap, LogId, Memo FROM RPPS_FIInfo WHERE FIID = ?";
    if (!paramBoolean) {
      str2 = str2 + " AND FIStatus != 'CLOSED'";
    }
    Object localObject1 = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        localRPPSFIInfo = a(localFFSResultSet);
      }
      else
      {
        localRPPSFIInfo.setStatusCode(16020);
        localRPPSFIInfo.setStatusMsg("RPPSFIInfo  record not found");
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
          FFSDebug.log("****" + str1 + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "done.", 6);
    return localRPPSFIInfo;
  }
  
  private static RPPSFIInfo jdMethod_byte(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "RPPSFI.getRPPSFIInfoByFIRPPSId: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    RPPSFIInfo localRPPSFIInfo = new RPPSFIInfo();
    FFSDebug.log(str1 + "start...", 6);
    if (paramString == null)
    {
      localObject1 = "***" + str1 + "failed: " + "FIRPPSId is null";
      FFSDebug.log((String)localObject1, 0);
      localRPPSFIInfo.setStatusCode(16000);
      localRPPSFIInfo.setStatusMsg("FIRPPSId  is null");
      return localRPPSFIInfo;
    }
    str2 = "SELECT RPPSFIId, FIRPPSId, FIID, FIRPPSName, RPPSId, RPPSName, SubmitDate, ActivationDate, FIStatus, CreditCap, LogId, Memo FROM RPPS_FIInfo WHERE FIRPPSId = ?";
    if (!paramBoolean) {
      str2 = str2 + " AND FIStatus != 'CLOSED'";
    }
    Object localObject1 = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        localRPPSFIInfo = a(localFFSResultSet);
      }
      else
      {
        localRPPSFIInfo.setStatusCode(16020);
        localRPPSFIInfo.setStatusMsg("RPPSFIInfo  record not found");
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
          FFSDebug.log("****" + str1 + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "done.", 6);
    return localRPPSFIInfo;
  }
  
  public static RPPSFIInfo getRPPSFIInfoByFIId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    return jdMethod_try(paramFFSConnectionHolder, paramString, false);
  }
  
  public static RPPSFIInfo getRPPSFIInfoByRPPSFIId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "RPPSFI.getRPPSFIInfoByRPPSFIId: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    RPPSFIInfo localRPPSFIInfo = new RPPSFIInfo();
    FFSDebug.log(str1 + "start...", 6);
    if (paramString == null)
    {
      localObject1 = "***" + str1 + "failed: " + "RPPSFIId is null";
      FFSDebug.log((String)localObject1, 0);
      localRPPSFIInfo.setStatusCode(16000);
      localRPPSFIInfo.setStatusMsg("RPPSFIId  is null");
      return localRPPSFIInfo;
    }
    str2 = "SELECT RPPSFIId, FIRPPSId, FIID, FIRPPSName, RPPSId, RPPSName, SubmitDate, ActivationDate, FIStatus, CreditCap, LogId, Memo FROM RPPS_FIInfo WHERE RPPSFIId = ? AND FIStatus != 'CLOSED'";
    Object localObject1 = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        localRPPSFIInfo = a(localFFSResultSet);
      }
      else
      {
        localRPPSFIInfo.setStatusCode(16020);
        localRPPSFIInfo.setStatusMsg("RPPSFIInfo 16020");
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
          FFSDebug.log("RPPSFI.getRPPSFIInfoByRPPSFIId: failed " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "done.", 6);
    return localRPPSFIInfo;
  }
  
  public static RPPSFIInfo canRPPSFIByFIId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str = "RPPSFI.canRPPSFIByFIId: ";
    Object localObject1 = null;
    Object localObject2 = null;
    RPPSFIInfo localRPPSFIInfo = null;
    FFSDebug.log(str + "start...", 6);
    Object localObject3;
    Object localObject4;
    if (paramString == null)
    {
      localObject3 = "***" + str + "failed: " + "FIId is null";
      FFSDebug.log((String)localObject3, 0);
      localObject4 = new RPPSFIInfo();
      ((RPPSFIInfo)localObject4).setStatusCode(16000);
      ((RPPSFIInfo)localObject4).setStatusMsg("FIId  is null");
      return localObject4;
    }
    try
    {
      localRPPSFIInfo = getRPPSFIInfoByFIId(paramFFSConnectionHolder, paramString);
      if (localRPPSFIInfo.getFiRPPSId() != null)
      {
        canRPPSFIInfo(paramFFSConnectionHolder, localRPPSFIInfo);
      }
      else
      {
        localObject3 = localRPPSFIInfo;
        return localObject3;
      }
    }
    catch (Exception localException1)
    {
      localObject4 = "***" + str + "failed: " + FFSDebug.stackTrace(localException1);
      FFSDebug.log((String)localObject4, 0);
      throw new FFSException(localException1, (String)localObject4);
    }
    finally
    {
      if (localObject2 != null) {
        try
        {
          localObject2.close();
          localObject2 = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log("RPPSFI.getRPPSFIInfoByFIId: failed " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str + "done.", 6);
    return localRPPSFIInfo;
  }
  
  private static boolean a(FFSConnectionHolder paramFFSConnectionHolder, RPPSFIInfo paramRPPSFIInfo)
    throws FFSException
  {
    if ((paramRPPSFIInfo.getFiRPPSId() == null) || (paramRPPSFIInfo.getFiRPPSId().trim().length() == 0))
    {
      paramRPPSFIInfo.setStatusCode(16010);
      localObject = "FIRPPSId  contains null ";
      paramRPPSFIInfo.setStatusMsg((String)localObject);
      FFSDebug.log("RPPSFI.vaildateRPPSFIInfo, " + (String)localObject, 0);
      return false;
    }
    if ((paramRPPSFIInfo.getFiId() == null) || (paramRPPSFIInfo.getFiId().trim().length() == 0))
    {
      paramRPPSFIInfo.setStatusCode(16010);
      localObject = "FIId  contains null ";
      paramRPPSFIInfo.setStatusMsg((String)localObject);
      FFSDebug.log("RPPSFI.vaildateRPPSFIInfo, " + (String)localObject, 0);
      return false;
    }
    if ((paramRPPSFIInfo.getRppsId() == null) || (paramRPPSFIInfo.getRppsId().trim().length() == 0))
    {
      paramRPPSFIInfo.setStatusCode(16010);
      localObject = "RPPSId  contains null ";
      paramRPPSFIInfo.setStatusMsg((String)localObject);
      FFSDebug.log("RPPSFI.vaildateRPPSFIInfo, " + (String)localObject, 0);
      return false;
    }
    if ((paramRPPSFIInfo.getRppsName() == null) || (paramRPPSFIInfo.getRppsName().trim().length() == 0))
    {
      paramRPPSFIInfo.setStatusCode(16010);
      localObject = "RPPSName  contains null ";
      paramRPPSFIInfo.setStatusMsg((String)localObject);
      FFSDebug.log("RPPSFI.vaildateRPPSFIInfo, " + (String)localObject, 0);
      return false;
    }
    if ((paramRPPSFIInfo.getCreditCap() == null) || (paramRPPSFIInfo.getCreditCap().trim().length() == 0))
    {
      paramRPPSFIInfo.setStatusCode(16010);
      localObject = "CreditCap  contains null ";
      paramRPPSFIInfo.setStatusMsg((String)localObject);
      FFSDebug.log("RPPSFI.vaildateRPPSFIInfo, " + (String)localObject, 0);
      return false;
    }
    Object localObject = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, paramRPPSFIInfo.getFiId());
    if ((localObject == null) || (((BPWFIInfo)localObject).getStatusCode() != 0) || (!((BPWFIInfo)localObject).getFIStatus().equals("ACTIVE")))
    {
      paramRPPSFIInfo.setStatusCode(23230);
      paramRPPSFIInfo.setStatusMsg("BPWFIID does not exist : with FI id = " + paramRPPSFIInfo.getFiId() + " in the " + "BPW_FIInfo table");
      return false;
    }
    return true;
  }
  
  private static RPPSFIInfo a(FFSResultSet paramFFSResultSet)
    throws FFSException
  {
    RPPSFIInfo localRPPSFIInfo = new RPPSFIInfo();
    localRPPSFIInfo.setRPPSFIId(paramFFSResultSet.getColumnString("RPPSFIId"));
    localRPPSFIInfo.setFiRPPSId(paramFFSResultSet.getColumnString("FIRPPSId"));
    localRPPSFIInfo.setFiId(paramFFSResultSet.getColumnString("FIID"));
    localRPPSFIInfo.setFiRPPSName(paramFFSResultSet.getColumnString("FIRPPSName"));
    localRPPSFIInfo.setRppsId(paramFFSResultSet.getColumnString("RPPSId"));
    localRPPSFIInfo.setRppsName(paramFFSResultSet.getColumnString("RPPSName"));
    localRPPSFIInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
    localRPPSFIInfo.setActivationDate(paramFFSResultSet.getColumnString("ActivationDate"));
    localRPPSFIInfo.setFiStatus(paramFFSResultSet.getColumnString("FIStatus"));
    localRPPSFIInfo.setCreditCap(paramFFSResultSet.getColumnString("CreditCap"));
    localRPPSFIInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    localRPPSFIInfo.setMemo(FFSUtil.stringToHashtable(paramFFSResultSet.getColumnString("Memo")));
    localRPPSFIInfo.setStatusCode(0);
    localRPPSFIInfo.setStatusMsg("Success");
    return localRPPSFIInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.RPPSFI
 * JD-Core Version:    0.7.0.1
 */