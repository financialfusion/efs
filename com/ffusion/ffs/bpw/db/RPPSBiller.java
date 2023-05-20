package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.RPPSBillerInfo;
import com.ffusion.ffs.bpw.interfaces.RPPSFIInfo;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.util.ArrayList;

public class RPPSBiller
  implements DBConsts, FFSConst
{
  public static RPPSBillerInfo createRPPSBillerInfo(FFSConnectionHolder paramFFSConnectionHolder, RPPSBillerInfo paramRPPSBillerInfo)
    throws FFSException
  {
    String str1 = null;
    String str2 = null;
    String str3 = "RPPSBillerInfo.createRPPSBillerInfo: ";
    FFSDebug.log(str3 + "start...", 6);
    Object localObject1;
    if (paramRPPSBillerInfo == null)
    {
      localObject1 = "***" + str3 + "failed: " + "Null Biller object passed";
      FFSDebug.log((String)localObject1, 0);
      paramRPPSBillerInfo = new RPPSBillerInfo();
      paramRPPSBillerInfo.setStatusCode(16000);
      paramRPPSBillerInfo.setStatusMsg("RPPSBillerInfo  is null");
      return paramRPPSBillerInfo;
    }
    if (!a(paramRPPSBillerInfo)) {
      return paramRPPSBillerInfo;
    }
    str1 = paramRPPSBillerInfo.getBillerRPPSId();
    try
    {
      localObject1 = getRPPSBillerInfoByFIAndBillerRPPSId(paramFFSConnectionHolder, paramRPPSBillerInfo.getFiRPPSId(), str1);
      if (((RPPSBillerInfo)localObject1).getStatusCode() == 0)
      {
        paramRPPSBillerInfo.setStatusCode(23380);
        paramRPPSBillerInfo.setStatusMsg("RPPS Biller already exists ");
        return paramRPPSBillerInfo;
      }
      localObject2 = RPPSFI.getRPPSFIInfoByFIRPPSId(paramFFSConnectionHolder, paramRPPSBillerInfo.getFiRPPSId());
      String str4 = ((RPPSFIInfo)localObject2).getRPPSFIId();
      paramRPPSBillerInfo.setSubmitDate(FFSUtil.getDateString());
      str2 = "INSERT INTO RPPS_PayeeExt (BillerRPPSId, BillerAliasId, RPPSFIId, BillerName, BillerClass, BillerType, TrnABA, Prenotes, GuarPayOnly, DmpPrenote, DmpPayOnly, OldName, PrivateFlag, BillerStatus, LogId, Memo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      Object[] arrayOfObject = { paramRPPSBillerInfo.getBillerRPPSId(), paramRPPSBillerInfo.getBillerAliasId(), str4, paramRPPSBillerInfo.getBillerName(), paramRPPSBillerInfo.getBillerClass(), paramRPPSBillerInfo.getBillerType(), paramRPPSBillerInfo.getTrnABA(), BPWUtil.booleanToString10(paramRPPSBillerInfo.isPrenotes()), BPWUtil.booleanToString10(paramRPPSBillerInfo.isGuarPayOnly()), BPWUtil.booleanToString10(paramRPPSBillerInfo.isDmpPrenotes()), BPWUtil.booleanToString10(paramRPPSBillerInfo.isDmpPayOnly()), paramRPPSBillerInfo.getOldName(), BPWUtil.booleanToString10(paramRPPSBillerInfo.isPrivateFlag()), paramRPPSBillerInfo.getBillerStatus(), paramRPPSBillerInfo.getLogId(), FFSUtil.hashtableToString(paramRPPSBillerInfo.getMemo()) };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      paramRPPSBillerInfo.setStatusCode(0);
      paramRPPSBillerInfo.setStatusMsg("Success");
      FFSDebug.log(str3 + "end", 6);
      return paramRPPSBillerInfo;
    }
    catch (Exception localException)
    {
      Object localObject2 = str3 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localException, (String)localObject2);
    }
  }
  
  public static RPPSBillerInfo modRPPSBillerInfo(FFSConnectionHolder paramFFSConnectionHolder, RPPSBillerInfo paramRPPSBillerInfo)
    throws FFSException
  {
    String str1 = "RPPSBiller.modRPPSBillerInfo: ";
    FFSDebug.log(str1 + "start...", 6);
    if (paramRPPSBillerInfo == null)
    {
      localObject = "***" + str1 + "failed: " + "Null Biller object passed";
      paramRPPSBillerInfo = new RPPSBillerInfo();
      FFSDebug.log((String)localObject, 0);
      paramRPPSBillerInfo.setStatusCode(16000);
      paramRPPSBillerInfo.setStatusMsg("RPPSBillerInfo  is null");
      return paramRPPSBillerInfo;
    }
    if (!a(paramRPPSBillerInfo)) {
      return paramRPPSBillerInfo;
    }
    Object localObject = RPPSFI.getRPPSFIInfoByFIRPPSId(paramFFSConnectionHolder, paramRPPSBillerInfo.getFiRPPSId());
    if (((RPPSFIInfo)localObject).getStatusCode() != 0)
    {
      paramRPPSBillerInfo.setStatusCode(16160);
      paramRPPSBillerInfo.setStatusMsg("No RPPSFI exists with the specified FIRPPSID  =" + paramRPPSBillerInfo.getFiRPPSId());
      return paramRPPSBillerInfo;
    }
    String str2 = ((RPPSFIInfo)localObject).getRPPSFIId();
    String str3 = "UPDATE RPPS_PayeeExt SET BillerAliasId = ?, BillerName = ?, BillerClass = ?, BillerType = ?, TrnABA = ?, Prenotes = ?, GuarPayOnly = ?, DmpPrenote = ?, DmpPayOnly = ?, OldName = ?, PrivateFlag = ?, BillerStatus = ?, memo = ? WHERE RPPSFIId=? and BillerRPPSId = ? ";
    Object[] arrayOfObject = { paramRPPSBillerInfo.getBillerAliasId(), paramRPPSBillerInfo.getBillerName(), paramRPPSBillerInfo.getBillerClass(), paramRPPSBillerInfo.getBillerType(), paramRPPSBillerInfo.getTrnABA(), BPWUtil.booleanToString10(paramRPPSBillerInfo.isPrenotes()), BPWUtil.booleanToString10(paramRPPSBillerInfo.isGuarPayOnly()), BPWUtil.booleanToString10(paramRPPSBillerInfo.isDmpPrenotes()), BPWUtil.booleanToString10(paramRPPSBillerInfo.isDmpPayOnly()), paramRPPSBillerInfo.getOldName(), BPWUtil.booleanToString10(paramRPPSBillerInfo.isPrivateFlag()), paramRPPSBillerInfo.getBillerStatus(), FFSUtil.hashtableToString(paramRPPSBillerInfo.getMemo()), str2, paramRPPSBillerInfo.getBillerRPPSId() };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      FFSDebug.log(str1 + "Number of records updated (RPPS_Biller): " + i, 6);
      if (i == 0)
      {
        paramRPPSBillerInfo.setStatusCode(16020);
        paramRPPSBillerInfo.setStatusMsg("RPPSBillerInfo  record not found");
      }
      else
      {
        paramRPPSBillerInfo.setStatusCode(0);
        paramRPPSBillerInfo.setStatusMsg("Success");
      }
    }
    catch (Exception localException)
    {
      String str4 = "*** " + str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, str4);
    }
    FFSDebug.log(str1 + "end", 6);
    return paramRPPSBillerInfo;
  }
  
  public static RPPSBillerInfo deleteRPPSBillerInfo(FFSConnectionHolder paramFFSConnectionHolder, RPPSBillerInfo paramRPPSBillerInfo)
    throws FFSException
  {
    String str1 = "RPPSBiller.deleteRPPSBillerInfo: ";
    String str2 = null;
    String str3 = null;
    String str4 = null;
    int i = 0;
    FFSDebug.log(str1 + "start...", 6);
    try
    {
      if (paramRPPSBillerInfo == null)
      {
        localObject1 = "***" + str1 + "failed: " + "Null Biller object passed";
        FFSDebug.log((String)localObject1, 0);
        paramRPPSBillerInfo = new RPPSBillerInfo();
        paramRPPSBillerInfo.setStatusCode(16000);
        paramRPPSBillerInfo.setStatusMsg("RPPSBillerInfo  is null");
        return paramRPPSBillerInfo;
      }
      Object localObject1 = RPPSFI.getRPPSFIInfoByFIRPPSId(paramFFSConnectionHolder, paramRPPSBillerInfo.getFiRPPSId());
      if (((RPPSFIInfo)localObject1).getStatusCode() != 0)
      {
        paramRPPSBillerInfo.setStatusCode(16160);
        paramRPPSBillerInfo.setStatusMsg("No RPPSFI exists with the specified FIRPPSID  =" + str3);
        return paramRPPSBillerInfo;
      }
      str5 = ((RPPSFIInfo)localObject1).getRPPSFIId();
      str2 = paramRPPSBillerInfo.getBillerRPPSId();
      if ((str2 == null) || (str3 == null))
      {
        localObject2 = "***" + str1 + "failed: " + "FIRPPSId or BillerRPPSId is null";
        FFSDebug.log((String)localObject2, 0);
        paramRPPSBillerInfo.setStatusCode(16010);
        paramRPPSBillerInfo.setStatusMsg("RPPSBillerInfo  contains null FIRPPSId or BillerRPPSId");
        return paramRPPSBillerInfo;
      }
      str4 = "DELETE FROM RPPS_PayeeExt WHERE RPPSFIId=? and BillerRPPSId = ? ";
      Object localObject2 = { str5, str2 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str4, (Object[])localObject2);
      FFSDebug.log(str1 + "Number of RPPSBiller records deleted: " + i, 6);
      if (i == 0)
      {
        paramRPPSBillerInfo.setStatusCode(16020);
        paramRPPSBillerInfo.setStatusMsg("RPPSBillerInfo  record not found");
      }
      else
      {
        paramRPPSBillerInfo.setStatusCode(0);
        paramRPPSBillerInfo.setStatusMsg("Success");
      }
    }
    catch (Exception localException)
    {
      String str5 = "*** " + str1 + "failed: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException, str5);
    }
    FFSDebug.log(str1 + "Done.", 6);
    return paramRPPSBillerInfo;
  }
  
  public static RPPSBillerInfo[] getRPPSBillerInfoByFIRPPSId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "RPPSBiller.getRPPSBillerInfoByFIRPPSId: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    RPPSBillerInfo localRPPSBillerInfo = new RPPSBillerInfo();
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    FFSDebug.log(str1 + "start...", 6);
    if (paramString == null)
    {
      localObject1 = "***" + str1 + "failed: " + "FIRPPSId is null";
      FFSDebug.log((String)localObject1, 0);
      localRPPSBillerInfo.setStatusCode(16000);
      localRPPSBillerInfo.setStatusMsg("FIRPPSId  is null");
      localArrayList.add(localRPPSBillerInfo);
      return toBillerArray(localArrayList);
    }
    Object localObject1 = RPPSFI.getRPPSFIInfoByFIRPPSId(paramFFSConnectionHolder, paramString);
    if (((RPPSFIInfo)localObject1).getStatusCode() != 0)
    {
      localRPPSBillerInfo.setStatusCode(16160);
      localRPPSBillerInfo.setStatusMsg("No RPPSFI exists with the specified FIRPPSID  =" + paramString);
      localArrayList.add(localRPPSBillerInfo);
      return toBillerArray(localArrayList);
    }
    String str3 = ((RPPSFIInfo)localObject1).getRPPSFIId();
    str2 = "SELECT BillerRPPSId, BillerAliasId, RPPSFIId, BillerName, BillerClass, BillerType, TrnABA, Prenotes, GuarPayOnly, DmpPrenote, DmpPayOnly, OldName, PrivateFlag, BillerStatus, Memo, SubmitDate, LogId FROM RPPS_PayeeExt WHERE RPPSFIId = ?";
    Object[] arrayOfObject = { str3 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localRPPSBillerInfo = jdMethod_for(paramFFSConnectionHolder, localFFSResultSet);
        localArrayList.add(localRPPSBillerInfo);
        i++;
      }
      if (i == 0)
      {
        localRPPSBillerInfo.setStatusCode(16020);
        localRPPSBillerInfo.setStatusMsg("RPPSBillerInfo  record not found");
        localArrayList.add(localRPPSBillerInfo);
      }
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + "failed: " + FFSDebug.stackTrace(localException1);
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
          FFSDebug.log("***" + str1 + "Failed to close result set." + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end", 6);
    return toBillerArray(localArrayList);
  }
  
  public static RPPSBillerInfo getRPPSBillerInfoByFIAndBillerRPPSId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "RPPSBiller.getRPPSBillerInfoByFIAndBillerRPPSId: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    RPPSBillerInfo localRPPSBillerInfo = new RPPSBillerInfo();
    FFSDebug.log(str1 + "start...", 6);
    if (paramString2 == null)
    {
      localObject1 = "***" + str1 + "failed: " + "BillerRPPSId is null";
      FFSDebug.log((String)localObject1, 0);
      localRPPSBillerInfo.setStatusCode(16000);
      localRPPSBillerInfo.setStatusMsg("BillerRPPSId  is null");
      return localRPPSBillerInfo;
    }
    Object localObject1 = RPPSFI.getRPPSFIInfoByFIRPPSId(paramFFSConnectionHolder, paramString1);
    if (((RPPSFIInfo)localObject1).getStatusCode() != 0)
    {
      localRPPSBillerInfo.setStatusCode(16160);
      localRPPSBillerInfo.setStatusMsg("No RPPSFI exists with the specified FIRPPSID  =" + paramString1);
      return localRPPSBillerInfo;
    }
    String str3 = ((RPPSFIInfo)localObject1).getRPPSFIId();
    str2 = "SELECT BillerRPPSId, BillerAliasId, RPPSFIId, BillerName, BillerClass, BillerType, TrnABA, Prenotes, GuarPayOnly, DmpPrenote, DmpPayOnly, OldName, PrivateFlag, BillerStatus, Memo, SubmitDate, LogId FROM RPPS_PayeeExt WHERE RPPSFIId=? and BillerRPPSId = ? ";
    Object[] arrayOfObject = { str3, paramString2 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localRPPSBillerInfo = jdMethod_for(paramFFSConnectionHolder, localFFSResultSet);
      }
      else
      {
        localRPPSBillerInfo.setStatusCode(16020);
        localRPPSBillerInfo.setStatusMsg("RPPSBillerInfo  record not found");
      }
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + "failed: " + FFSDebug.stackTrace(localException1);
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
          FFSDebug.log("***" + str1 + "Failed to close result set." + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end", 6);
    return localRPPSBillerInfo;
  }
  
  public static RPPSBillerInfo getRPPSBillerInfoByFIRPPSIdPayeeId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "RPPSBiller.getRPPSBillerInfoByFIRPPSIdPayeeId: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    RPPSBillerInfo localRPPSBillerInfo = new RPPSBillerInfo();
    FFSDebug.log(str1 + "start...", 6);
    if (paramString2 == null)
    {
      localObject1 = "***" + str1 + "failed: " + "PayeeId is null";
      FFSDebug.log((String)localObject1, 0);
      localRPPSBillerInfo.setStatusCode(16000);
      localRPPSBillerInfo.setStatusMsg("PayeeId  is null");
      return localRPPSBillerInfo;
    }
    if (paramString1 == null)
    {
      localObject1 = "***" + str1 + "failed: " + "fiRPPSId is null";
      FFSDebug.log((String)localObject1, 0);
      localRPPSBillerInfo.setStatusCode(16000);
      localRPPSBillerInfo.setStatusMsg("fiRPPSId  is null");
      return localRPPSBillerInfo;
    }
    Object localObject1 = RPPSFI.getRPPSFIInfoByFIRPPSId(paramFFSConnectionHolder, paramString1);
    if (((RPPSFIInfo)localObject1).getStatusCode() != 0)
    {
      localRPPSBillerInfo.setStatusCode(16160);
      localRPPSBillerInfo.setStatusMsg("No RPPSFI exists with the specified FIRPPSID  =" + paramString1);
      return localRPPSBillerInfo;
    }
    String str3 = ((RPPSFIInfo)localObject1).getRPPSFIId();
    str2 = "SELECT RPPS_PayeeExt.BillerRPPSId, RPPS_PayeeExt.BillerAliasId, RPPS_PayeeExt.RPPSFIId, RPPS_PayeeExt.BillerName, RPPS_PayeeExt.BillerClass, RPPS_PayeeExt.BillerType, RPPS_PayeeExt.TrnABA, RPPS_PayeeExt.Prenotes, RPPS_PayeeExt.GuarPayOnly, RPPS_PayeeExt.DmpPrenote, RPPS_PayeeExt.DmpPayOnly, RPPS_PayeeExt.OldName, RPPS_PayeeExt.PrivateFlag, RPPS_PayeeExt.BillerStatus, RPPS_PayeeExt.Memo, RPPS_PayeeExt.SubmitDate, RPPS_PayeeExt.LogId FROM RPPS_PayeeExt, BPW_Payee WHERE RPPS_PayeeExt.RPPSFIId=?  and BPW_Payee.PayeeID = ? and RPPS_PayeeExt.BillerRPPSId = BPW_Payee.ExtdPayeeID";
    Object[] arrayOfObject = { str3, paramString2 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localRPPSBillerInfo = jdMethod_for(paramFFSConnectionHolder, localFFSResultSet);
      }
      else
      {
        localRPPSBillerInfo.setStatusCode(16020);
        localRPPSBillerInfo.setStatusMsg("RPPSBillerInfo  record not found");
      }
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + "failed: " + FFSDebug.stackTrace(localException1);
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
          FFSDebug.log("***" + str1 + "Failed to close result set." + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end", 6);
    return localRPPSBillerInfo;
  }
  
  private static boolean a(RPPSBillerInfo paramRPPSBillerInfo)
    throws FFSException
  {
    String str;
    if ((paramRPPSBillerInfo.getBillerRPPSId() == null) || (paramRPPSBillerInfo.getBillerRPPSId().trim().length() == 0))
    {
      paramRPPSBillerInfo.setStatusCode(16010);
      str = "BillerRPPSId  contains null ";
      paramRPPSBillerInfo.setStatusMsg(str);
      FFSDebug.log("RPPSBiller.vaildateRPPSBillerInfo, " + str, 0);
      return false;
    }
    if ((paramRPPSBillerInfo.getFiRPPSId() == null) || (paramRPPSBillerInfo.getFiRPPSId().trim().length() == 0))
    {
      paramRPPSBillerInfo.setStatusCode(16010);
      str = "FIRPPSId  contains null ";
      paramRPPSBillerInfo.setStatusMsg(str);
      FFSDebug.log("RPPSBiller.vaildateRPPSBillerInfo, " + str, 0);
      return false;
    }
    if ((paramRPPSBillerInfo.getBillerName() == null) || (paramRPPSBillerInfo.getBillerName().trim().length() == 0))
    {
      paramRPPSBillerInfo.setStatusCode(16010);
      str = "BillerName  contains null ";
      paramRPPSBillerInfo.setStatusMsg(str);
      FFSDebug.log("RPPSBiller.vaildateRPPSBillerInfo, " + str, 0);
      return false;
    }
    if ((paramRPPSBillerInfo.getBillerClass() == null) || (paramRPPSBillerInfo.getBillerClass().trim().length() == 0))
    {
      paramRPPSBillerInfo.setStatusCode(16010);
      str = "BillerClass  contains null ";
      paramRPPSBillerInfo.setStatusMsg(str);
      FFSDebug.log("RPPSBiller.vaildateRPPSBillerInfo, " + str, 0);
      return false;
    }
    if ((paramRPPSBillerInfo.getBillerType() == null) || (paramRPPSBillerInfo.getBillerType().trim().length() == 0))
    {
      paramRPPSBillerInfo.setStatusCode(16010);
      str = "BillerType  contains null ";
      paramRPPSBillerInfo.setStatusMsg(str);
      FFSDebug.log("RPPSBiller.vaildateRPPSBillerInfo, " + str, 0);
      return false;
    }
    if ((paramRPPSBillerInfo.getBillerStatus() == null) || (paramRPPSBillerInfo.getBillerStatus().trim().length() == 0))
    {
      paramRPPSBillerInfo.setStatusCode(16010);
      str = "BillerStatus  contains null ";
      paramRPPSBillerInfo.setStatusMsg(str);
      FFSDebug.log("RPPSBiller.vaildateRPPSBillerInfo, " + str, 0);
      return false;
    }
    return true;
  }
  
  private static RPPSBillerInfo jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, FFSResultSet paramFFSResultSet)
    throws FFSException
  {
    RPPSBillerInfo localRPPSBillerInfo = new RPPSBillerInfo();
    localRPPSBillerInfo.setBillerRPPSId(paramFFSResultSet.getColumnString("BillerRPPSId"));
    localRPPSBillerInfo.setBillerAliasId(paramFFSResultSet.getColumnString("BillerAliasId"));
    RPPSFIInfo localRPPSFIInfo = RPPSFI.getRPPSFIInfoByRPPSFIId(paramFFSConnectionHolder, paramFFSResultSet.getColumnString("RPPSFIId"));
    localRPPSBillerInfo.setFiRPPSId(localRPPSFIInfo.getFiRPPSId());
    localRPPSFIInfo = null;
    localRPPSBillerInfo.setBillerName(paramFFSResultSet.getColumnString("BillerName"));
    localRPPSBillerInfo.setBillerClass(paramFFSResultSet.getColumnString("BillerClass"));
    localRPPSBillerInfo.setBillerType(paramFFSResultSet.getColumnString("BillerType"));
    localRPPSBillerInfo.setTrnABA(paramFFSResultSet.getColumnString("TrnABA"));
    localRPPSBillerInfo.setPrenotes(BPWUtil.string10toBoolean(paramFFSResultSet.getColumnString("Prenotes")));
    localRPPSBillerInfo.setGuarPayOnly(BPWUtil.string10toBoolean(paramFFSResultSet.getColumnString("GuarPayOnly")));
    localRPPSBillerInfo.setDmpPrenotes(BPWUtil.string10toBoolean(paramFFSResultSet.getColumnString("DmpPrenote")));
    localRPPSBillerInfo.setDmpPayOnly(BPWUtil.string10toBoolean(paramFFSResultSet.getColumnString("DmpPayOnly")));
    localRPPSBillerInfo.setOldName(paramFFSResultSet.getColumnString("OldName"));
    localRPPSBillerInfo.setPrivateFlag(BPWUtil.string10toBoolean(paramFFSResultSet.getColumnString("PrivateFlag")));
    localRPPSBillerInfo.setBillerStatus(paramFFSResultSet.getColumnString("BillerStatus"));
    localRPPSBillerInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
    localRPPSBillerInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    localRPPSBillerInfo.setMemo(FFSUtil.stringToHashtable(paramFFSResultSet.getColumnString("Memo")));
    localRPPSBillerInfo.setStatusCode(0);
    localRPPSBillerInfo.setStatusMsg("Success");
    return localRPPSBillerInfo;
  }
  
  public static RPPSBillerInfo[] toBillerArray(ArrayList paramArrayList)
  {
    return (RPPSBillerInfo[])paramArrayList.toArray(new RPPSBillerInfo[0]);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.RPPSBiller
 * JD-Core Version:    0.7.0.1
 */