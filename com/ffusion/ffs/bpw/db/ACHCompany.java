package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyList;
import com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryList;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class ACHCompany
  implements FFSConst, DBConsts, SQLConsts, ACHConsts
{
  public static ACHCompanyInfo create(ACHCompanyInfo paramACHCompanyInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHCompany.create: start", 6);
    if (!jdMethod_if(paramACHCompanyInfo)) {
      return paramACHCompanyInfo;
    }
    ACHFIInfo localACHFIInfo = ACHFI.getACHFIInfo(paramFFSConnectionHolder, paramACHCompanyInfo.getODFIACHId());
    if (localACHFIInfo.getStatusCode() != 0)
    {
      paramACHCompanyInfo.setStatusCode(23220);
      paramACHCompanyInfo.setStatusMsg("ODFIACHID does not exist :");
      return paramACHCompanyInfo;
    }
    String str1 = localACHFIInfo.getACHFIId();
    String str2 = "Insert into ACH_Company (CustomerId,CompId,ACHFIId,CompName,NickName,CompACHId,BankAcctId,BankId,BankAcctType,CompStatus,CompDesc,CompType,CompGroup,CompRank,AddrLine1,AddrLine2,AddrLine3,City,State,PostalCode,Country,DayPhone,Extension,ContactName,SubmitDate,ActivationDate,LogId,Memo,SubmittedBy,BatchType) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String str3;
    try
    {
      str3 = DBUtil.getNextIndexStringWithPadding("CompID", 10, '0');
    }
    catch (Exception localException1)
    {
      localObject = "*** ACHCompany.create failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException1, (String)localObject);
    }
    paramACHCompanyInfo.setCompId(str3);
    String str4 = FFSUtil.getDateString();
    paramACHCompanyInfo.setSubmitDate(str4);
    paramACHCompanyInfo.setActivationDate(str4);
    paramACHCompanyInfo.setCompStatus("ACTIVE");
    Object localObject = { paramACHCompanyInfo.getCustomerId(), paramACHCompanyInfo.getCompId(), str1, paramACHCompanyInfo.getCompName(), paramACHCompanyInfo.getNickName(), paramACHCompanyInfo.getCompACHId(), paramACHCompanyInfo.getBankAcctId(), paramACHCompanyInfo.getBankId(), paramACHCompanyInfo.getBankAcctType(), paramACHCompanyInfo.getCompStatus(), paramACHCompanyInfo.getCompDesc(), paramACHCompanyInfo.getCompType(), paramACHCompanyInfo.getCompGroup(), paramACHCompanyInfo.getCompRank(), paramACHCompanyInfo.getAddr1(), paramACHCompanyInfo.getAddr2(), paramACHCompanyInfo.getAddr3(), paramACHCompanyInfo.getCity(), paramACHCompanyInfo.getState(), paramACHCompanyInfo.getPostalCode(), paramACHCompanyInfo.getCountry(), paramACHCompanyInfo.getPhone(), paramACHCompanyInfo.getExtension(), paramACHCompanyInfo.getContactName(), paramACHCompanyInfo.getSubmitDate(), paramACHCompanyInfo.getActivationDate(), paramACHCompanyInfo.getLogId(), FFSUtil.hashtableToString(paramACHCompanyInfo.getMemo()), paramACHCompanyInfo.getSubmittedBy(), paramACHCompanyInfo.getBatchType() };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
    }
    catch (Exception localException2)
    {
      String str6 = "*** ACHCompany.create: failed: ";
      String str7 = FFSDebug.stackTrace(localException2);
      FFSDebug.log(str7, 0);
      throw new FFSException(localException2, str6);
    }
    paramACHCompanyInfo.setStatusCode(0);
    paramACHCompanyInfo.setStatusMsg("Success");
    FFSDebug.log("ACHCompany.create: done, copmID=" + str3, 6);
    return paramACHCompanyInfo;
  }
  
  public static String getFiIdbyCompId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ACHCompany.getFiIdByCompId: ";
    FFSDebug.log(str1 + "compId =" + paramString, 6);
    FFSResultSet localFFSResultSet = null;
    String str2 = "Select b.FIId  From ACH_Company a, ACH_FIInfo b  Where a.CompStatus !=' CLOSED'AND a.ACHFIId = b.ACHFIId AND a.CompId = ? ";
    Object[] arrayOfObject = { paramString };
    String str3 = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        str3 = localFFSResultSet.getColumnString("FIId");
      }
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + "failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException1, str4);
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
        String str6 = "*** " + str1 + "failed: ";
        FFSDebug.log(str6 + FFSDebug.stackTrace(localException2), 0);
      }
    }
    FFSDebug.log(str1 + "fiId =" + str3, 6);
    return str3;
  }
  
  public static ACHCompanyInfo getCompByACHId(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHCompany.getCompByACHId: start, achId =" + paramString, 6);
    ACHCompanyInfo localACHCompanyInfo = null;
    if ((paramString == null) || (paramString.trim().length() < 1))
    {
      FFSDebug.log("ACHCompany.getCompByACHId: Invalid ACH FI Id value, achId =" + paramString, 0);
      localACHCompanyInfo = new ACHCompanyInfo();
      localACHCompanyInfo.setStatusCode(16020);
      localACHCompanyInfo.setStatusMsg("ACHCompany  record not found");
      return localACHCompanyInfo;
    }
    FFSResultSet localFFSResultSet = null;
    String str1 = "Select a.CompId, a.ACHFIId, a.CompName, a.NickName, a.CompACHId,a.BankAcctId,a.BankId,a.CustomerId,a.BankAcctType,a.CompStatus,a.CompDesc,a.CompType,a.CompGroup,a.CompRank,a.AddrLine1,a.AddrLine2,a.AddrLine3,a.City,a.State,a.PostalCode,a.Country,a.DayPhone,a.Extension,a.ContactName,a.SubmitDate,a.ActivationDate,a.LogId, a.Memo, a.SubmittedBy, a.BatchType , b.ODFIACHId From ACH_Company a,  ACH_FIInfo b Where b.ACHFIId = a.ACHFIId AND a.CompACHId = ? AND a.CompStatus != 'CLOSED'";
    Object[] arrayOfObject = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localACHCompanyInfo = new ACHCompanyInfo();
        a(paramFFSConnectionHolder, localFFSResultSet, localACHCompanyInfo);
      }
      if (localACHCompanyInfo == null)
      {
        localACHCompanyInfo = new ACHCompanyInfo();
        localACHCompanyInfo.setStatusCode(16020);
        localACHCompanyInfo.setStatusMsg(" record not found");
      }
      else
      {
        localACHCompanyInfo.setStatusCode(0);
        localACHCompanyInfo.setStatusMsg("Success");
      }
    }
    catch (Exception localException1)
    {
      String str2 = "*** ACHCompany.getCompByACHId failed: ";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
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
        FFSDebug.log("*** ACHCompany.getCompByACHId failed: " + FFSDebug.stackTrace(localException2), 0);
      }
    }
    return localACHCompanyInfo;
  }
  
  public static ACHCompanyInfo getACHCompanyByACHId(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHCompany.getACHCompanyByACHId: start, achId =" + paramString, 6);
    ACHCompanyInfo localACHCompanyInfo = null;
    if ((paramString == null) || (paramString.trim().length() < 1))
    {
      FFSDebug.log("ACHCompany.getACHCompanyByACHId: Invalid ACH FI Id value, achId =" + paramString, 0);
      localACHCompanyInfo = new ACHCompanyInfo();
      localACHCompanyInfo.setStatusCode(16020);
      localACHCompanyInfo.setStatusMsg("ACHCompany  record not found");
      return localACHCompanyInfo;
    }
    localACHCompanyInfo = getCompByACHId(paramString, paramFFSConnectionHolder);
    if (localACHCompanyInfo.getStatusCode() != 0)
    {
      String str = "ACHCompany.getACHCompanyByACHId: Failed to get ACH Company information, for company, achId = " + paramString + ", Error Code: " + localACHCompanyInfo.getStatusCode() + ", Error Message: " + localACHCompanyInfo.getStatusMsg();
      FFSDebug.log(str, 0);
      return localACHCompanyInfo;
    }
    if ("CLOSED".equalsIgnoreCase(localACHCompanyInfo.getCompStatus()))
    {
      localACHCompanyInfo = null;
      localACHCompanyInfo = new ACHCompanyInfo();
      localACHCompanyInfo.setStatusCode(16020);
      localACHCompanyInfo.setStatusMsg(" record not found");
    }
    else
    {
      localACHCompanyInfo.setStatusCode(0);
      localACHCompanyInfo.setStatusMsg("Success");
    }
    return localACHCompanyInfo;
  }
  
  public static ACHCompanyInfo updateACHCompany(ACHCompanyInfo paramACHCompanyInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHCompany.updateACHCompany: start, compId=" + paramACHCompanyInfo.getCompId(), 6);
    if ((!jdMethod_for(paramACHCompanyInfo)) || (!jdMethod_do(paramACHCompanyInfo)) || (!jdMethod_new(paramACHCompanyInfo))) {
      return paramACHCompanyInfo;
    }
    String str1 = "Update ACH_Company set CompName = ?, NickName = ?, BankAcctId = ?,BankId = ?, BankAcctType = ?, CompDesc = ?,CompType = ?, CompGroup = ?, CompRank = ?, AddrLine1 = ?,AddrLine2 = ?, AddrLine3 = ?, City = ?, State = ?,PostalCode = ?, Country = ?, DayPhone = ?, Extension = ?, ContactName = ?, Memo = ?, BatchType = ?  Where CompId = ? AND CompStatus != 'CLOSED'";
    int i = 0;
    Object[] arrayOfObject = { paramACHCompanyInfo.getCompName(), paramACHCompanyInfo.getNickName(), paramACHCompanyInfo.getBankAcctId(), paramACHCompanyInfo.getBankId(), paramACHCompanyInfo.getBankAcctType(), paramACHCompanyInfo.getCompDesc(), paramACHCompanyInfo.getCompType(), paramACHCompanyInfo.getCompGroup(), paramACHCompanyInfo.getCompRank(), paramACHCompanyInfo.getAddr1(), paramACHCompanyInfo.getAddr2(), paramACHCompanyInfo.getAddr3(), paramACHCompanyInfo.getCity(), paramACHCompanyInfo.getState(), paramACHCompanyInfo.getPostalCode(), paramACHCompanyInfo.getCountry(), paramACHCompanyInfo.getPhone(), paramACHCompanyInfo.getExtension(), paramACHCompanyInfo.getContactName(), FFSUtil.hashtableToString(paramACHCompanyInfo.getMemo()), paramACHCompanyInfo.getBatchType(), paramACHCompanyInfo.getCompId() };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** ACHCompany.updateACHCompany failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, str3);
    }
    if (i <= 0)
    {
      String str2 = " record not found, compID =" + paramACHCompanyInfo.getCompId() + ", does not exist";
      paramACHCompanyInfo.setStatusCode(16020);
      paramACHCompanyInfo.setStatusMsg("ACHCompanyProcessor.modACHCompany " + str2);
      FFSDebug.log(str2, 0);
    }
    else
    {
      paramACHCompanyInfo.setStatusCode(0);
      paramACHCompanyInfo.setStatusMsg("Success");
    }
    FFSDebug.log("ACHCompany.updateACHCompany: done", 6);
    return paramACHCompanyInfo;
  }
  
  public static ACHCompanyInfo updateStatusForACHCompany(ACHCompanyInfo paramACHCompanyInfo, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "ACHCompany.updateStatusForACHCompany: ";
    if (paramACHCompanyInfo == null)
    {
      paramACHCompanyInfo = new ACHCompanyInfo();
      paramACHCompanyInfo.setStatusCode(16000);
      str2 = "ACHCompanyInfo object  is null";
      paramACHCompanyInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + str2, 0);
      return paramACHCompanyInfo;
    }
    String str2 = paramACHCompanyInfo.getCompId();
    if ((str2 == null) || (str2.trim().equals("")))
    {
      paramACHCompanyInfo.setStatusCode(16000);
      str3 = "compInfo  is null compId";
      paramACHCompanyInfo.setStatusMsg(str1 + str3);
      FFSDebug.log(str3, 0);
      return paramACHCompanyInfo;
    }
    if (!jdMethod_do(paramACHCompanyInfo))
    {
      str3 = str1 + " failed to check auditLog Info: " + paramACHCompanyInfo.getStatusMsg();
      FFSDebug.log(str3, 0);
      return paramACHCompanyInfo;
    }
    FFSDebug.log(str1 + " start, compId=" + paramACHCompanyInfo.getCompId(), 6);
    String str3 = "Update ACH_Company set CompStatus = ?  Where CompId = ?";
    int i = 0;
    Object[] arrayOfObject = { paramString, str2 };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException, str4);
    }
    if ((i > 0) && (paramString.equalsIgnoreCase("CLOSED"))) {}
    paramACHCompanyInfo.setCompStatus(paramString);
    if (i == 0)
    {
      paramACHCompanyInfo.setStatusCode(16020);
      paramACHCompanyInfo.setStatusMsg(" record not found, compId = " + str2 + " does not exist");
    }
    else
    {
      paramACHCompanyInfo.setStatusCode(0);
      paramACHCompanyInfo.setStatusMsg("Success");
    }
    FFSDebug.log(str1 + " done", 6);
    return paramACHCompanyInfo;
  }
  
  public static ACHCompanyInfo getCompanyInfo(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHCompany.getCompanyInfo: start, compId=" + paramString, 6);
    String str1 = "Select a.CompId, a.ACHFIId, a.CompName, a.NickName, a.CompACHId,a.BankAcctId,a.BankId,a.CustomerId,a.BankAcctType,a.CompStatus,a.CompDesc,a.CompType,a.CompGroup,a.CompRank,a.AddrLine1,a.AddrLine2,a.AddrLine3,a.City,a.State,a.PostalCode,a.Country,a.DayPhone,a.Extension,a.ContactName,a.SubmitDate,a.ActivationDate,a.LogId, a.Memo, a.SubmittedBy, a.BatchType , b.ODFIACHId From ACH_Company a,  ACH_FIInfo b Where b.ACHFIId = a.ACHFIId AND a.CompId = ? ";
    str1 = str1 + " AND CompStatus != 'CLOSED'";
    FFSResultSet localFFSResultSet = null;
    ACHCompanyInfo localACHCompanyInfo = null;
    Object[] arrayOfObject = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      localACHCompanyInfo = new ACHCompanyInfo();
      if (localFFSResultSet.getNextRow())
      {
        a(paramFFSConnectionHolder, localFFSResultSet, localACHCompanyInfo);
        localACHCompanyInfo.setStatusCode(0);
        localACHCompanyInfo.setStatusMsg("Success");
      }
      else
      {
        localACHCompanyInfo.setStatusCode(16020);
        localACHCompanyInfo.setStatusMsg("ACHCompanyInfo  record not found");
      }
    }
    catch (Exception localException1)
    {
      String str2 = "*** ACHCompany.getCompanyInfo failed: ";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
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
        FFSDebug.log("*** ACHCompany.getCompanyInfo  failed: " + FFSDebug.stackTrace(localException2), 0);
      }
    }
    FFSDebug.log("ACHCompany.getCompanyInfo: done", 6);
    return localACHCompanyInfo;
  }
  
  public static ACHCompanyList getACHCompanyList(ACHCompanyList paramACHCompanyList, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "ACHComany.getACHCompanyList: ";
    FFSDebug.log(str1 + "start... ", 6);
    StringBuffer localStringBuffer = new StringBuffer("Select a.CompId, a.ACHFIId, a.CompName, a.NickName, a.CompACHId,a.BankAcctId,a.BankId,a.CustomerId,a.BankAcctType,a.CompStatus,a.CompDesc,a.CompType,a.CompGroup,a.CompRank,a.AddrLine1,a.AddrLine2,a.AddrLine3,a.City,a.State,a.PostalCode,a.Country,a.DayPhone,a.Extension,a.ContactName,a.SubmitDate,a.ActivationDate,a.LogId, a.Memo, a.SubmittedBy, a.BatchType , b.ODFIACHId From ACH_Company a,  ACH_FIInfo b WHERE b.ACHFIId = a.ACHFIId AND a.CompStatus != 'CLOSED'");
    ArrayList localArrayList1 = new ArrayList();
    DBUtil.appendToCondition(localStringBuffer, paramACHCompanyList.getFiId(), " AND a.ACHFIId in ( SELECT ACHFIId from ACH_FIInfo where FIId=? AND FIStatus != 'CLOSED')", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramACHCompanyList.getOdfiAchId(), " AND a.ACHFIId in  ( Select ACHFIId from ACH_FIInfo where ODFIACHId = ?  AND FIStatus != 'CLOSED')", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramACHCompanyList.getCustomerId(), " AND CustomerId =? ", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramACHCompanyList.getCompAchId(), " AND CompACHId =? ", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramACHCompanyList.getCompGroup(), " AND CompGroup =? ", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramACHCompanyList.getCompType(), " AND CompType =? ", localArrayList1);
    DBUtil.appendArrayToCondition(localStringBuffer, paramACHCompanyList.getCompIdList(), " AND CompId IN (", localArrayList1);
    DBUtil.appendArrayToCondition(localStringBuffer, paramACHCompanyList.getCompStatusList(), " AND CompStatus IN (", localArrayList1);
    Object[] arrayOfObject = (Object[])localArrayList1.toArray(new Object[0]);
    localStringBuffer.append(" ORDER BY CompId");
    String str2 = localStringBuffer.toString();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      ArrayList localArrayList2 = new ArrayList();
      localObject1 = null;
      while (localFFSResultSet.getNextRow())
      {
        localObject1 = new ACHCompanyInfo();
        a(paramFFSConnectionHolder, localFFSResultSet, (ACHCompanyInfo)localObject1);
        ((ACHCompanyInfo)localObject1).setStatusCode(0);
        ((ACHCompanyInfo)localObject1).setStatusMsg("Success");
        localArrayList2.add(localObject1);
      }
      localObject2 = (ACHCompanyInfo[])localArrayList2.toArray(new ACHCompanyInfo[0]);
      paramACHCompanyList.setACHCompanyInfoList((ACHCompanyInfo[])localObject2);
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Object localObject1 = str1 + " failed: ";
      Object localObject2 = FFSDebug.stackTrace(localException1);
      FFSDebug.log((String)localObject2, 0);
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
        FFSDebug.log(str1 + " failed: " + FFSDebug.stackTrace(localException2), 0);
      }
    }
    return paramACHCompanyList;
  }
  
  public static int updateStatusByODFIACHId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("ACHCompany.updateStatusByODFIACHId: start, oDFIACHID =" + paramString1 + ", status =" + paramString2, 6);
    int i = 0;
    Object[] arrayOfObject = { paramString1 };
    try
    {
      FFSResultSet localFFSResultSet = null;
      str = "SELECT CompId  FROM ACH_Company  WHERE ACHFIId =  (Select ACHFIId from ACH_FIInfo where ODFIACHId = ?  AND FIStatus != 'CLOSED')  AND CompStatus != 'CLOSED'";
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      localObject = null;
      while (localFFSResultSet.getNextRow())
      {
        localObject = new ACHCompanyInfo();
        ((ACHCompanyInfo)localObject).setCompId(localFFSResultSet.getColumnString(1));
        updateStatusForACHCompany((ACHCompanyInfo)localObject, "CLOSED", paramFFSConnectionHolder);
      }
    }
    catch (Exception localException)
    {
      String str = "*** ACHCompany.updateStatusByODFIACHId failed: ";
      Object localObject = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject, 0);
      throw new FFSException(localException, str);
    }
    FFSDebug.log("ACHCompany.updateStatusByODFIACHId: done", 6);
    return i;
  }
  
  public static String getCompanyStatus(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHCompany.getCompanyStatus: start, compId=" + paramString, 6);
    if (paramString == null) {
      return null;
    }
    FFSResultSet localFFSResultSet = null;
    String str1 = "Select CompStatus From ACH_Company Where CompId = ?";
    Object[] arrayOfObject = { paramString };
    String str2 = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str2 = localFFSResultSet.getColumnString(1);
      }
    }
    catch (Exception localException1)
    {
      String str3 = "*** ACHCompany.getCompanyStatus failed: ";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, str3);
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
        FFSDebug.log("*** ACHCompany.getCompanyStatus failed: " + FFSDebug.stackTrace(localException2), 0);
      }
    }
    FFSDebug.log("ACHCompany.getCompanyStatus: done", 6);
    return str2;
  }
  
  public static boolean isCompanyClosed(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHCompany.isCompanyClosed: start, compId=" + paramString, 6);
    boolean bool = false;
    String str = getCompanyStatus(paramString, paramFFSConnectionHolder);
    if ((str == null) || (str.equals("CLOSED"))) {
      bool = true;
    }
    FFSDebug.log("ACHCompany.isCompanyClosed: done. Value" + bool, 6);
    return bool;
  }
  
  public static ACHCompanyInfo activateCompany(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHCompany.activateCompany: start, compId=" + paramString, 6);
    ACHCompanyInfo localACHCompanyInfo = null;
    if (paramString == null)
    {
      localACHCompanyInfo = new ACHCompanyInfo();
      localACHCompanyInfo.setStatusCode(16000);
      localACHCompanyInfo.setStatusMsg("CompId  is null");
      return localACHCompanyInfo;
    }
    String str1 = "Select a.CompId, a.ACHFIId, a.CompName, a.NickName, a.CompACHId,a.BankAcctId,a.BankId,a.CustomerId,a.BankAcctType,a.CompStatus,a.CompDesc,a.CompType,a.CompGroup,a.CompRank,a.AddrLine1,a.AddrLine2,a.AddrLine3,a.City,a.State,a.PostalCode,a.Country,a.DayPhone,a.Extension,a.ContactName,a.SubmitDate,a.ActivationDate,a.LogId, a.Memo, a.SubmittedBy, a.BatchType , b.ODFIACHId From ACH_Company a,  ACH_FIInfo b Where b.ACHFIId = a.ACHFIId AND a.CompId = ? ";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      localACHCompanyInfo = new ACHCompanyInfo();
      if (localFFSResultSet.getNextRow())
      {
        a(paramFFSConnectionHolder, localFFSResultSet, localACHCompanyInfo);
        if (!localACHCompanyInfo.getCompStatus().equals("CLOSED"))
        {
          localACHCompanyInfo.setStatusCode(23250);
          localACHCompanyInfo.setStatusMsg("Company is already ACTIVE , CompId " + paramString);
          localObject1 = localACHCompanyInfo;
          return localObject1;
        }
        if (!checkACHFIAndCustomerStatus(localACHCompanyInfo, paramFFSConnectionHolder))
        {
          localObject1 = localACHCompanyInfo;
          return localObject1;
        }
        str1 = "Update ACH_Company set CompStatus = ?, ActivationDate = ?  Where CompId = ?";
        localACHCompanyInfo.setActivationDate(FFSUtil.getDateString());
        localACHCompanyInfo.setCompStatus("ACTIVE");
        Object localObject1 = { localACHCompanyInfo.getCompStatus(), localACHCompanyInfo.getActivationDate(), localACHCompanyInfo.getCompId() };
        try
        {
          DBUtil.executeStatement(paramFFSConnectionHolder, str1, (Object[])localObject1);
        }
        catch (Exception localException2)
        {
          str3 = "*** ACHCompany.activateCompany failed: ";
          String str4 = FFSDebug.stackTrace(localException2);
          FFSDebug.log(str4, 0);
          throw new FFSException(localException2, str3);
        }
        localACHCompanyInfo.setStatusCode(0);
        localACHCompanyInfo.setStatusMsg("Success");
      }
      else
      {
        localACHCompanyInfo.setStatusCode(16020);
        localACHCompanyInfo.setStatusMsg("ACHCompany  record not found");
      }
    }
    catch (Exception localException1)
    {
      String str2 = "*** ACHCompany.activateCompany failed: ";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
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
      catch (Exception localException3)
      {
        FFSDebug.log("*** ACHCompany.activateCompany  failed: " + FFSDebug.stackTrace(localException3), 0);
      }
    }
    FFSDebug.log("ACHCompany.activateCompany: done", 6);
    return localACHCompanyInfo;
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, FFSResultSet paramFFSResultSet, ACHCompanyInfo paramACHCompanyInfo)
    throws FFSException
  {
    paramACHCompanyInfo.setCompId(paramFFSResultSet.getColumnString("CompId"));
    paramACHCompanyInfo.setCompName(paramFFSResultSet.getColumnString("CompName"));
    paramACHCompanyInfo.setNickName(paramFFSResultSet.getColumnString("NickName"));
    paramACHCompanyInfo.setCompACHId(paramFFSResultSet.getColumnString("CompACHId"));
    paramACHCompanyInfo.setBankAcctId(paramFFSResultSet.getColumnString("BankAcctId"));
    paramACHCompanyInfo.setBankId(paramFFSResultSet.getColumnString("BankId"));
    paramACHCompanyInfo.setCustomerId(paramFFSResultSet.getColumnString("CustomerId"));
    paramACHCompanyInfo.setBankAcctType(paramFFSResultSet.getColumnString("BankAcctType"));
    paramACHCompanyInfo.setCompStatus(paramFFSResultSet.getColumnString("CompStatus"));
    paramACHCompanyInfo.setCompDesc(paramFFSResultSet.getColumnString("CompDesc"));
    paramACHCompanyInfo.setCompType(paramFFSResultSet.getColumnString("CompType"));
    paramACHCompanyInfo.setCompGroup(paramFFSResultSet.getColumnString("CompGroup"));
    paramACHCompanyInfo.setCompRank(paramFFSResultSet.getColumnString("CompRank"));
    paramACHCompanyInfo.setAddr1(paramFFSResultSet.getColumnString("AddrLine1"));
    paramACHCompanyInfo.setAddr2(paramFFSResultSet.getColumnString("AddrLine2"));
    paramACHCompanyInfo.setAddr3(paramFFSResultSet.getColumnString("AddrLine3"));
    paramACHCompanyInfo.setCity(paramFFSResultSet.getColumnString("City"));
    paramACHCompanyInfo.setState(paramFFSResultSet.getColumnString("State"));
    paramACHCompanyInfo.setPostalCode(paramFFSResultSet.getColumnString("PostalCode"));
    paramACHCompanyInfo.setCountry(paramFFSResultSet.getColumnString("Country"));
    paramACHCompanyInfo.setPhone(paramFFSResultSet.getColumnString("DayPhone"));
    paramACHCompanyInfo.setExtension(paramFFSResultSet.getColumnString("Extension"));
    paramACHCompanyInfo.setContactName(paramFFSResultSet.getColumnString("ContactName"));
    paramACHCompanyInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
    paramACHCompanyInfo.setActivationDate(paramFFSResultSet.getColumnString("ActivationDate"));
    paramACHCompanyInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    paramACHCompanyInfo.setMemo(FFSUtil.stringToHashtable(paramFFSResultSet.getColumnString("Memo")));
    paramACHCompanyInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    paramACHCompanyInfo.setBatchType(paramFFSResultSet.getColumnString("BatchType"));
    paramACHCompanyInfo.setODFIACHId(paramFFSResultSet.getColumnString("ODFIACHId"));
  }
  
  public static ACHCompanySummaryList getACHCompanySummaries(ACHCompanySummaryList paramACHCompanySummaryList, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str = "ACHCompany.getACHCompanySummaries: ";
    FFSDebug.log(str + " start", 6);
    HashMap localHashMap = new HashMap();
    ACHCompanySummaryList localACHCompanySummaryList = getACHCompanySummaries(paramACHCompanySummaryList, paramFFSConnectionHolder, false);
    if (localACHCompanySummaryList.getStatusCode() == 0)
    {
      localObject1 = localACHCompanySummaryList.getACHCompanySummaryInfoList();
      if (localObject1 != null) {
        for (int i = 0; i < localObject1.length; i++) {
          localHashMap.put(localObject1[i].getCompanyId() + localObject1[i].getBatchCategory(), localObject1[i]);
        }
      }
    }
    else if (localACHCompanySummaryList.getStatusCode() != 16020)
    {
      paramACHCompanySummaryList.setStatusCode(localACHCompanySummaryList.getStatusCode());
      paramACHCompanySummaryList.setStatusMsg(localACHCompanySummaryList.getStatusMsg());
      return paramACHCompanySummaryList;
    }
    Object localObject1 = getACHCompanySummaries(paramACHCompanySummaryList, paramFFSConnectionHolder, true);
    if (((ACHCompanySummaryList)localObject1).getStatusCode() == 0)
    {
      localObject2 = ((ACHCompanySummaryList)localObject1).getACHCompanySummaryInfoList();
      if (localObject2 != null) {
        for (int j = 0; j < localObject2.length; j++)
        {
          ACHCompanySummaryInfo localACHCompanySummaryInfo = (ACHCompanySummaryInfo)localHashMap.get(localObject2[j].getCompanyId() + localObject2[j].getBatchCategory());
          if (localACHCompanySummaryInfo == null) {
            localHashMap.put(localObject2[j].getCompanyId() + localObject2[j].getBatchCategory(), localObject2[j]);
          } else {
            localACHCompanySummaryInfo.add(localObject2[j]);
          }
        }
      }
    }
    else if (((ACHCompanySummaryList)localObject1).getStatusCode() != 16020)
    {
      paramACHCompanySummaryList.setStatusCode(((ACHCompanySummaryList)localObject1).getStatusCode());
      paramACHCompanySummaryList.setStatusMsg(((ACHCompanySummaryList)localObject1).getStatusMsg());
      return paramACHCompanySummaryList;
    }
    Object localObject2 = new ArrayList(localHashMap.values());
    paramACHCompanySummaryList.setACHCompanySummaryInfoList((ACHCompanySummaryInfo[])((ArrayList)localObject2).toArray(new ACHCompanySummaryInfo[0]));
    paramACHCompanySummaryList.setStatusCode(0);
    paramACHCompanySummaryList.setStatusMsg("Success");
    return paramACHCompanySummaryList;
  }
  
  public static ACHCompanySummaryList getACHCompanySummaries(ACHCompanySummaryList paramACHCompanySummaryList, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHCompany.getACHCompanySummaries: ";
    FFSDebug.log(str1 + " start", 6);
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramBoolean == true) {
      localStringBuffer.append("SELECT AC.CompId, AC.CompName, AC.CustomerId, AB.BatchCategory, AB.RecordCount, AB.TotalCredit, AB.TotalDebit From ACH_Company AC, ACH_RecBatch AB where  AC.CompStatus != 'CLOSED' AND AB.BatchStatus != 'CANCELEDON' AND AB.BatchStatus != 'ACHTEMPORARY' AND AC.CompId = AB.CompId");
    } else {
      localStringBuffer.append("SELECT AC.CompId, AC.CompName, AC.CustomerId, AB.BatchCategory, AB.RecordCount, AB.TotalCredit, AB.TotalDebit From ACH_Company AC, ACH_Batch AB where  AC.CompStatus != 'CLOSED' AND AB.BatchStatus != 'CANCELEDON' AND AB.BatchStatus != 'ACHTEMPORARY' AND AB.BatchType = 'Current' AND AC.CompId = AB.CompId");
    }
    ArrayList localArrayList1 = new ArrayList();
    DBUtil.appendToCondition(localStringBuffer, paramACHCompanySummaryList.getCustomerId(), " AND AC.CustomerId = ?", localArrayList1);
    DBUtil.appendArrayToCondition(localStringBuffer, paramACHCompanySummaryList.getCompanyIdList(), " AND AC.CompId IN (", localArrayList1);
    DBUtil.appendArrayToCondition(localStringBuffer, paramACHCompanySummaryList.getBatchCategoryList(), " AND AB.BatchCategory IN (", localArrayList1);
    Object[] arrayOfObject = (Object[])localArrayList1.toArray(new Object[0]);
    localStringBuffer.append(" ORDER BY AC.CompId, AB.BatchCategory");
    String str2 = localStringBuffer.toString();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      ArrayList localArrayList2 = new ArrayList();
      long l1 = 0L;
      long l2 = 0L;
      long l3 = 0L;
      long l4 = 0L;
      long l5 = 0L;
      Object localObject1 = "";
      Object localObject2 = "";
      ACHCompanySummaryInfo localACHCompanySummaryInfo = new ACHCompanySummaryInfo();
      Object localObject3;
      while (localFFSResultSet.getNextRow())
      {
        localObject3 = localFFSResultSet.getColumnString("CompId");
        String str5 = localFFSResultSet.getColumnString("BatchCategory");
        if ((!((String)localObject1).equals(localObject3)) || ((str5 != null) && (!str5.equals(localObject2))) || ((str5 == null) && (localObject2 != null)))
        {
          if (l1 > 0L)
          {
            localACHCompanySummaryInfo.setNumberOfBatches(l2);
            localACHCompanySummaryInfo.setNumberOfBatchEntries(l3);
            localACHCompanySummaryInfo.setTotalCredit("" + l4);
            localACHCompanySummaryInfo.setTotalDebit("" + l5);
            localACHCompanySummaryInfo.setGrandTotal("" + (l5 - l4));
            localACHCompanySummaryInfo.setStatusCode(0);
            localACHCompanySummaryInfo.setStatusMsg("Success");
            localArrayList2.add(localACHCompanySummaryInfo);
            localACHCompanySummaryInfo = new ACHCompanySummaryInfo();
          }
          localObject1 = localObject3;
          localObject2 = str5;
          localACHCompanySummaryInfo.setCompanyId((String)localObject1);
          localACHCompanySummaryInfo.setBatchCategory((String)localObject2);
          localACHCompanySummaryInfo.setCompanyName(localFFSResultSet.getColumnString("CompName"));
          localACHCompanySummaryInfo.setCustomerId(localFFSResultSet.getColumnString("CustomerId"));
          l1 += 1L;
          l2 = 1L;
          l3 = 0L;
          l4 = 0L;
          l5 = 0L;
        }
        else
        {
          l2 += 1L;
        }
        l3 += localFFSResultSet.getColumnLong("RecordCount");
        l4 += Long.parseLong(localFFSResultSet.getColumnString("TotalCredit"));
        l5 += Long.parseLong(localFFSResultSet.getColumnString("TotalDebit"));
      }
      if (l1 == 0L)
      {
        paramACHCompanySummaryList.setStatusCode(16020);
        paramACHCompanySummaryList.setStatusMsg(" record not found");
      }
      else
      {
        localACHCompanySummaryInfo.setNumberOfBatches(l2);
        localACHCompanySummaryInfo.setNumberOfBatchEntries(l3);
        localACHCompanySummaryInfo.setTotalCredit("" + l4);
        localACHCompanySummaryInfo.setTotalDebit("" + l5);
        localACHCompanySummaryInfo.setGrandTotal("" + (l5 - l4));
        localACHCompanySummaryInfo.setStatusCode(0);
        localACHCompanySummaryInfo.setStatusMsg("Success");
        localArrayList2.add(localACHCompanySummaryInfo);
        localObject3 = (ACHCompanySummaryInfo[])localArrayList2.toArray(new ACHCompanySummaryInfo[0]);
        paramACHCompanySummaryList.setACHCompanySummaryInfoList((ACHCompanySummaryInfo[])localObject3);
        paramACHCompanySummaryList.setStatusCode(0);
        paramACHCompanySummaryList.setStatusMsg("Success");
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, str3);
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
        FFSDebug.log(str1 + " failed: " + FFSDebug.stackTrace(localException2), 0);
      }
    }
    return paramACHCompanySummaryList;
  }
  
  public static boolean checkACHFIAndCustomerStatus(ACHCompanyInfo paramACHCompanyInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    ACHFIInfo localACHFIInfo = ACHFI.getACHFIInfo(paramFFSConnectionHolder, paramACHCompanyInfo.getODFIACHId());
    if ((localACHFIInfo == null) || (localACHFIInfo.getStatusCode() == 16020))
    {
      paramACHCompanyInfo.setStatusCode(23220);
      paramACHCompanyInfo.setStatusMsg("ODFIACHID does not exist :ODFIACHID = " + paramACHCompanyInfo.getODFIACHId());
      return false;
    }
    String str1 = localACHFIInfo.getFIStatus();
    if ((str1 == null) || (str1.equals("CLOSED")))
    {
      paramACHCompanyInfo.setStatusCode(23350);
      paramACHCompanyInfo.setStatusMsg("ACHFI Exists but Status is Closed  ODFIACHId =" + paramACHCompanyInfo.getODFIACHId());
      return false;
    }
    CustomerInfo localCustomerInfo = Customer.getCustomerInfo(paramACHCompanyInfo.getCustomerId(), paramFFSConnectionHolder, paramACHCompanyInfo);
    if (localCustomerInfo == null)
    {
      paramACHCompanyInfo.setStatusCode(19130);
      String str2 = BPWLocaleUtil.getMessage(19130, new String[] { paramACHCompanyInfo.getCustomerId() }, "TRANSFER_MESSAGE");
      paramACHCompanyInfo.setStatusMsg(str2);
      return false;
    }
    str1 = localCustomerInfo.status;
    if ((str1 == null) || ((!str1.equals("ACTIVE")) && (!str1.equals("NEW"))))
    {
      paramACHCompanyInfo.setStatusCode(23400);
      paramACHCompanyInfo.setStatusMsg("Customer exists but the status is not ACTIVE or NEW  CustomerId = " + paramACHCompanyInfo.getCustomerId() + ", Status = " + str1);
      return false;
    }
    if (localCustomerInfo.fIID.compareTo(localACHFIInfo.getFIId()) != 0)
    {
      paramACHCompanyInfo.setStatusCode(23390);
      paramACHCompanyInfo.setStatusMsg("ACHFI and Customer have different FIID, ACHFI.FIID = " + localACHFIInfo.getFIId() + " and Customer.FIID = " + localCustomerInfo.fIID);
      return false;
    }
    return true;
  }
  
  private static boolean jdMethod_if(ACHCompanyInfo paramACHCompanyInfo)
  {
    return (jdMethod_for(paramACHCompanyInfo)) && (jdMethod_int(paramACHCompanyInfo)) && (jdMethod_do(paramACHCompanyInfo)) && (jdMethod_new(paramACHCompanyInfo));
  }
  
  private static boolean jdMethod_for(ACHCompanyInfo paramACHCompanyInfo)
  {
    boolean bool = true;
    bool = BPWUtil.validateCompName(paramACHCompanyInfo.getCompName());
    if (!bool)
    {
      paramACHCompanyInfo.setStatusCode(23740);
      paramACHCompanyInfo.setStatusMsg("Company Name length has exceeded the maximum value = 16");
    }
    return bool;
  }
  
  private static boolean jdMethod_new(ACHCompanyInfo paramACHCompanyInfo)
  {
    boolean bool = true;
    String str = paramACHCompanyInfo.getBatchType();
    if (str == null) {
      return true;
    }
    if ((str.compareTo("UnbalancedBatch") != 0) && (str.compareTo("BatchBalancedBatch") != 0) && (str.compareTo("EntryBalancedBatch") != 0))
    {
      paramACHCompanyInfo.setStatusCode(24250);
      paramACHCompanyInfo.setStatusMsg("Invalid Batch Type. Valid Batch Types are :UnbalancedBatch, BatchBalancedBatch, EntryBalancedBatch. Received batchType =" + str);
      bool = false;
    }
    return bool;
  }
  
  private static boolean jdMethod_int(ACHCompanyInfo paramACHCompanyInfo)
  {
    boolean bool = true;
    bool = BPWUtil.validateCompACHId(paramACHCompanyInfo.getCompACHId());
    if (!bool)
    {
      paramACHCompanyInfo.setStatusCode(23750);
      paramACHCompanyInfo.setStatusMsg("Company ACH Id length has exceeded the maximum value = 10");
    }
    return bool;
  }
  
  private static boolean jdMethod_do(ACHCompanyInfo paramACHCompanyInfo)
  {
    String str1 = paramACHCompanyInfo.getLogId();
    if (str1 == null)
    {
      paramACHCompanyInfo.setStatusCode(23870);
      paramACHCompanyInfo.setStatusMsg("LogID field is null");
      return false;
    }
    if (str1.length() > 32)
    {
      paramACHCompanyInfo.setStatusCode(23880);
      paramACHCompanyInfo.setStatusMsg("LogID field is too long: " + str1);
      return false;
    }
    String str2 = paramACHCompanyInfo.getSubmittedBy();
    if (str2 == null)
    {
      paramACHCompanyInfo.setStatusCode(23890);
      paramACHCompanyInfo.setStatusMsg("SubmittedBy field is null");
      return false;
    }
    if (str2.length() > 32)
    {
      paramACHCompanyInfo.setStatusCode(23900);
      paramACHCompanyInfo.setStatusMsg("SubmittedBy field is too long: " + str2);
      return false;
    }
    return true;
  }
  
  public static void doTransAuditLog(FFSConnectionHolder paramFFSConnectionHolder, ACHCompanyInfo paramACHCompanyInfo, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    paramString2 = paramString2 + " CustomerID=" + paramACHCompanyInfo.getCustomerId() + ", CompACHID=" + paramACHCompanyInfo.getCompACHId();
    BigDecimal localBigDecimal = new BigDecimal(0.0D);
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramString1, paramACHCompanyInfo.getAgentId(), paramACHCompanyInfo.getAgentType(), paramString2, paramACHCompanyInfo.getLogId(), paramInt, Integer.parseInt(paramACHCompanyInfo.getCustomerId()), localBigDecimal, null, paramACHCompanyInfo.getCompId(), paramACHCompanyInfo.getCompStatus(), null, null, null, null, 0);
    TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ACHCompany
 * JD-Core Version:    0.7.0.1
 */