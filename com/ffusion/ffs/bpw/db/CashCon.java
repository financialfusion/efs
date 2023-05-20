package com.ffusion.ffs.bpw.db;

import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.ACHHistInfo;
import com.ffusion.ffs.bpw.interfaces.BPWBankAcctInfo;
import com.ffusion.ffs.bpw.interfaces.CCBatchHistInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfoList;
import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfoList;
import com.ffusion.ffs.bpw.interfaces.CCCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyInfoList;
import com.ffusion.ffs.bpw.interfaces.CCEntryHistInfo;
import com.ffusion.ffs.bpw.interfaces.CCEntryInfo;
import com.ffusion.ffs.bpw.interfaces.CCEntrySummaryInfo;
import com.ffusion.ffs.bpw.interfaces.CCEntrySummaryInfoList;
import com.ffusion.ffs.bpw.interfaces.CCLocationInfo;
import com.ffusion.ffs.bpw.interfaces.CCLocationInfoList;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.master.LimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.util.ACHAdapterUtil;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CashCon
  implements FFSConst, DBConsts, SQLConsts, ACHConsts
{
  private static String kd = new String(new char[] { '\001' });
  private static String kb = new String(new char[] { '\002' });
  private static final int ke = 8;
  private static final int kc = kd.length();
  
  public static CCCompanyInfo addCCCompany(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    String str1 = "CashCon.addCCCompany: ";
    FFSDebug.log(str1 + "start ...", 6);
    try
    {
      if (paramCCCompanyInfo.getCompId() != null)
      {
        paramCCCompanyInfo.setStatusCode(24080);
        paramCCCompanyInfo.setStatusMsg("Company ID must be null");
        return paramCCCompanyInfo;
      }
      paramCCCompanyInfo = jdMethod_do(paramFFSConnectionHolder, paramCCCompanyInfo);
      if (paramCCCompanyInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "failed, Reason =" + paramCCCompanyInfo.getStatusMsg(), 0);
        return paramCCCompanyInfo;
      }
      CCCompanyInfo localCCCompanyInfo = jdMethod_int(paramFFSConnectionHolder, paramCCCompanyInfo.getCCCompId(), true);
      if (localCCCompanyInfo.getStatusCode() == 0)
      {
        localCCCompanyInfo.setStatusCode(24170);
        localCCCompanyInfo.setStatusMsg("A Cash Con Company exists with the same CCCompId =" + paramCCCompanyInfo.getCCCompId());
        return localCCCompanyInfo;
      }
      localCCCompanyInfo = null;
      paramCCCompanyInfo.setCompId(DBUtil.getNextIndexStringWithPadding("CCCompId", 32, '0'));
      str2 = "INSERT INTO CC_Company (CompId, CustomerId, CCCompId, CompName, ConcentrateAcctId, DisburseAcctId, BatchType, Status, SubmittedBy, LogId) VALUES (?,?,?,?,?,?,?,?,?,?)";
      localObject = new Object[] { paramCCCompanyInfo.getCompId(), paramCCCompanyInfo.getCustomerId(), paramCCCompanyInfo.getCCCompId(), paramCCCompanyInfo.getCompName(), paramCCCompanyInfo.getConcentrateAcctId(), paramCCCompanyInfo.getDisburseAcctId(), paramCCCompanyInfo.getBatchType(), paramCCCompanyInfo.getStatus(), paramCCCompanyInfo.getSubmittedBy().trim().length() > 0 ? paramCCCompanyInfo.getSubmittedBy() : paramCCCompanyInfo.getAgentId(), paramCCCompanyInfo.getLogId() };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      paramCCCompanyInfo.setStatusCode(0);
      paramCCCompanyInfo.setStatusMsg("Success");
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** " + str1 + " failed: ";
      localObject = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, (String)localObject, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = "*** " + str1 + " failed: ";
      Object localObject = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, (String)localObject, 0);
      throw new FFSException(localException, str2);
    }
    FFSDebug.log(str1 + "end ...", 6);
    return paramCCCompanyInfo;
  }
  
  public static CCCompanyInfo cancelCCCompany(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    String str1 = "CashCon.cancelCCCompany: ";
    String str2 = paramCCCompanyInfo.getCompId();
    FFSDebug.log(str1 + "start, CompId =" + str2, 6);
    if (str2 == null)
    {
      paramCCCompanyInfo.setStatusCode(24180);
      paramCCCompanyInfo.setStatusMsg("CompId is Null");
      return paramCCCompanyInfo;
    }
    if (j(paramFFSConnectionHolder, str2) == true)
    {
      paramCCCompanyInfo.setStatusCode(24190);
      paramCCCompanyInfo.setStatusMsg("This CC Company has active location, CompId =" + str2);
      return paramCCCompanyInfo;
    }
    try
    {
      i(paramFFSConnectionHolder, str2);
    }
    catch (FFSException localFFSException)
    {
      paramCCCompanyInfo.setStatusCode(24200);
      paramCCCompanyInfo.setStatusMsg("Could not cancel the CC Company Acct. ");
      return paramCCCompanyInfo;
    }
    if (isTransactionPending(paramFFSConnectionHolder, paramCCCompanyInfo) == true)
    {
      paramCCCompanyInfo.setStatusCode(24240);
      paramCCCompanyInfo.setStatusMsg("Pending transaction(s) exists For this Company, CompId =" + paramCCCompanyInfo.getCompId());
      return paramCCCompanyInfo;
    }
    a(paramFFSConnectionHolder, paramCCCompanyInfo);
    CCCompanyInfo localCCCompanyInfo = new CCCompanyInfo();
    localCCCompanyInfo.setCompId(str2);
    localCCCompanyInfo = getCCCompany(paramFFSConnectionHolder, localCCCompanyInfo);
    if (localCCCompanyInfo.getStatusCode() != 0)
    {
      paramCCCompanyInfo.setStatusCode(localCCCompanyInfo.getStatusCode());
      paramCCCompanyInfo.setStatusMsg(localCCCompanyInfo.getStatusMsg());
      return paramCCCompanyInfo;
    }
    if ((paramCCCompanyInfo.getSubmittedBy() != null) && (!paramCCCompanyInfo.getSubmittedBy().trim().equals(""))) {
      localCCCompanyInfo.setSubmittedBy(paramCCCompanyInfo.getSubmittedBy());
    }
    localCCCompanyInfo.setAgentId(paramCCCompanyInfo.getAgentId());
    localCCCompanyInfo.setAgentType(paramCCCompanyInfo.getAgentType());
    localCCCompanyInfo.setAgentName(paramCCCompanyInfo.getAgentName());
    int i = b(paramFFSConnectionHolder, "CANCELEDON", str2);
    if (i == 0)
    {
      paramCCCompanyInfo.setStatusCode(16020);
      paramCCCompanyInfo.setStatusMsg("CCCompanyInfo with CompId =" + paramCCCompanyInfo.getCompId() + " record not found");
    }
    else
    {
      paramCCCompanyInfo.setStatus("CANCELEDON");
      localCCCompanyInfo.setStatus("CANCELEDON");
      paramCCCompanyInfo.setStatusCode(0);
      paramCCCompanyInfo.setStatusMsg("Success");
      logCCompanyTransAuditLog(paramFFSConnectionHolder, localCCCompanyInfo, "Cancel a Cash Con Company", 5302);
    }
    return paramCCCompanyInfo;
  }
  
  public static CCCompanyInfo modCCCompany(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    String str1 = "CashCon.modCCCompany: ";
    FFSDebug.log(str1 + "start, CompId =" + paramCCCompanyInfo.getCompId(), 6);
    try
    {
      if (paramCCCompanyInfo.getCompId() == null)
      {
        paramCCCompanyInfo.setStatusCode(24180);
        paramCCCompanyInfo.setStatusMsg("CompId is Null");
        FFSDebug.log(str1 + "failed, Reason =" + paramCCCompanyInfo.getStatusMsg(), 0);
        return paramCCCompanyInfo;
      }
      paramCCCompanyInfo = jdMethod_do(paramFFSConnectionHolder, paramCCCompanyInfo);
      if (paramCCCompanyInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "failed, Reason =" + paramCCCompanyInfo.getStatusMsg(), 0);
        return paramCCCompanyInfo;
      }
      CCCompanyInfo localCCCompanyInfo = jdMethod_int(paramFFSConnectionHolder, paramCCCompanyInfo.getCCCompId(), true);
      if ((localCCCompanyInfo.getStatusCode() == 0) && (paramCCCompanyInfo.getCompId().compareTo(localCCCompanyInfo.getCompId()) != 0))
      {
        localCCCompanyInfo.setStatusCode(24170);
        localCCCompanyInfo.setStatusMsg("A Cash Con Company exists with the same CCCompId =" + paramCCCompanyInfo.getCCCompId());
        return localCCCompanyInfo;
      }
      localCCCompanyInfo = null;
      str2 = "Update CC_Company set CustomerId = ?, CCCompId = ?, CompName = ?, ConcentrateAcctId = ?, DisburseAcctId = ?, BatchType = ?, Status = ?  Where CompId = ?";
      localObject = new Object[] { paramCCCompanyInfo.getCustomerId(), paramCCCompanyInfo.getCCCompId(), paramCCCompanyInfo.getCompName(), paramCCCompanyInfo.getConcentrateAcctId(), paramCCCompanyInfo.getDisburseAcctId(), paramCCCompanyInfo.getBatchType(), paramCCCompanyInfo.getStatus(), paramCCCompanyInfo.getCompId() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      if (i == 0)
      {
        paramCCCompanyInfo.setStatusCode(16020);
        paramCCCompanyInfo.setStatusMsg("CCCompanyInfo with CompId =" + paramCCCompanyInfo.getCompId() + " record not found");
      }
      else
      {
        paramCCCompanyInfo.setStatusCode(0);
        paramCCCompanyInfo.setStatusMsg("Success");
      }
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** " + str1 + " failed: ";
      localObject = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, (String)localObject, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = "*** " + str1 + " failed: ";
      Object localObject = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, (String)localObject, 0);
      throw new FFSException(localException, str2);
    }
    FFSDebug.log(str1 + "end, status =" + paramCCCompanyInfo.getStatusMsg(), 6);
    return paramCCCompanyInfo;
  }
  
  public static CCCompanyInfo getCCCompany(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    String str1 = "CashCon.getCCCompany :";
    FFSDebug.log(str1 + "start, CompId =" + paramCCCompanyInfo.getCompId(), 6);
    if (paramCCCompanyInfo.getCompId() == null)
    {
      paramCCCompanyInfo.setStatusCode(24230);
      paramCCCompanyInfo.setStatusMsg("CC Company Id cannot be Null");
      return paramCCCompanyInfo;
    }
    String str2 = "SELECT cc.CompId, cc.CustomerId, cc.CCCompId, cc.CompName, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BatchType, cc.Status, cc.SubmittedBy, cc.LogId  FROM CC_Company cc  WHERE cc.CompId = ? And cc.Status != 'CANCELEDON'";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramCCCompanyInfo.getCompId() };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        paramCCCompanyInfo = a(localFFSResultSet, paramCCCompanyInfo);
      }
      else
      {
        paramCCCompanyInfo.setStatusCode(16020);
        paramCCCompanyInfo.setStatusMsg("CCCompanyInfo with CompId =" + paramCCCompanyInfo.getCompId() + " " + " record not found");
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
    FFSDebug.log(str1 + "end, status =" + paramCCCompanyInfo.getStatusMsg(), 6);
    return paramCCCompanyInfo;
  }
  
  public static CCCompanyInfoList getCCCompanyList(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyInfoList paramCCCompanyInfoList)
    throws FFSException
  {
    String str1 = "CashCon.getCCCompanyList :";
    String str2 = "FIId =" + paramCCCompanyInfoList.getFIId() + ", CustomerId =" + paramCCCompanyInfoList.getCustomerId();
    if (paramCCCompanyInfoList.getCompIdList() != null) {
      str2 = str2 + ", CompIds =" + Arrays.asList(paramCCCompanyInfoList.getCompIdList());
    }
    FFSDebug.log(str1 + "start " + str2, 6);
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList1 = new ArrayList();
    if (paramCCCompanyInfoList.getFIId() != null)
    {
      if (paramCCCompanyInfoList.getIncludeDeletedEntries() == true) {
        localStringBuffer.append("SELECT cc.CompId, cc.CustomerId, cc.CCCompId, cc.CompName, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BatchType, cc.Status, cc.SubmittedBy, cc.LogId  FROM CC_Company cc , BPW_Customer cust  Where cust.CustomerID = cc.CustomerId ");
      } else {
        localStringBuffer.append("SELECT cc.CompId, cc.CustomerId, cc.CCCompId, cc.CompName, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BatchType, cc.Status, cc.SubmittedBy, cc.LogId  FROM CC_Company cc , BPW_Customer cust  Where cc.Status != 'CANCELEDON' AND cust.CustomerID = cc.CustomerId ");
      }
    }
    else if (paramCCCompanyInfoList.getIncludeDeletedEntries() == true) {
      localStringBuffer.append("SELECT cc.CompId, cc.CustomerId, cc.CCCompId, cc.CompName, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BatchType, cc.Status, cc.SubmittedBy, cc.LogId  FROM CC_Company cc  Where 1=1");
    } else {
      localStringBuffer.append("SELECT cc.CompId, cc.CustomerId, cc.CCCompId, cc.CompName, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BatchType, cc.Status, cc.SubmittedBy, cc.LogId  FROM CC_Company cc  Where cc.Status != 'CANCELEDON'");
    }
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyInfoList.getFIId(), " AND cust.FIID = ? ", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyInfoList.getCustomerId(), " AND cc.CustomerId = ? ", localArrayList1);
    DBUtil.appendArrayToCondition(localStringBuffer, paramCCCompanyInfoList.getCompIdList(), " AND cc.CompId IN (", localArrayList1);
    String str3 = localStringBuffer.toString();
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = (Object[])localArrayList1.toArray(new Object[0]);
    CCCompanyInfo localCCCompanyInfo = null;
    ArrayList localArrayList2 = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localCCCompanyInfo = new CCCompanyInfo();
        localCCCompanyInfo = a(localFFSResultSet, localCCCompanyInfo);
        localArrayList2.add(localCCCompanyInfo);
      }
      if (localArrayList2.size() == 0)
      {
        paramCCCompanyInfoList.setStatusCode(16020);
        paramCCCompanyInfoList.setStatusMsg("CCCompany with " + str2 + " " + " record not found");
      }
      else
      {
        paramCCCompanyInfoList.setStatusCode(0);
        paramCCCompanyInfoList.setStatusMsg("Success");
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, str5, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    paramCCCompanyInfoList.setCCCompanyInfoList((CCCompanyInfo[])localArrayList2.toArray(new CCCompanyInfo[0]));
    FFSDebug.log(str1 + "end, status =" + paramCCCompanyInfoList.getStatusMsg(), 6);
    return paramCCCompanyInfoList;
  }
  
  public static ACHFIInfo getACHFIInfoByCompId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "CashCon.getACHFIInfoByCompId :";
    FFSDebug.log(str1 + "start, compId =" + paramString, 6);
    String str2 = "SELECT a.ODFIACHId From ACH_FIInfo a, BPW_Customer b, CC_Company c  Where c.CompId = ? AND c.CustomerId = b.CustomerID  AND b.FIID = a.FIId AND (a.CashConDFI is null OR a.CashConDFI != 'N') AND a.FIStatus != 'CLOSED' Order by a.CashConDFI";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString };
    ACHFIInfo localACHFIInfo = null;
    String str3 = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        str3 = localFFSResultSet.getColumnString("ODFIACHId");
        localACHFIInfo = ACHFI.getACHFIInfo(paramFFSConnectionHolder, str3);
      }
      else
      {
        localACHFIInfo = new ACHFIInfo();
        localACHFIInfo.setStatusCode(16020);
        localACHFIInfo.setStatusMsg(" record not found");
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, str5, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end, status =" + localACHFIInfo.getStatusMsg(), 6);
    return localACHFIInfo;
  }
  
  public static CutOffInfo getNextCashConCutOff(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "CashCon.getNextCashConCutOff :";
    FFSDebug.log(str1 + "start, compId =" + paramString1 + ", transactionType =" + paramString2, 6);
    String str2 = "SELECT a.CutOffId, a.FIID, a.InstructionType, a.Frequency, a.Day, a.ProcessTime, a.Extension, a.NextProcessTime, a.LastProcessTime, a.Status, a.SubmittedBy, a.LogId, a.Memo  From SCH_CutOffs a, CC_CompanyCutOff b  Where b.CompId = ? AND b.TransactionType = ?  AND a.CutOffId = b.CutOffId AND a.NextProcessTime is not null  Order By a.NextProcessTime ASC";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString1, paramString2 };
    CutOffInfo localCutOffInfo = new CutOffInfo();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localCutOffInfo = a(localFFSResultSet, localCutOffInfo);
      }
      else
      {
        localCutOffInfo.setStatusCode(16020);
        localCutOffInfo.setStatusMsg(" record not found");
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
    FFSDebug.log(str1 + "end, status =" + localCutOffInfo.getStatusMsg(), 6);
    return localCutOffInfo;
  }
  
  public static CCCompanyAcctInfo addCCCompanyAcct(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    String str1 = "CashCon.addCCCompanyAcct: ";
    FFSDebug.log(str1 + "start ...", 6);
    try
    {
      paramCCCompanyAcctInfo = jdMethod_if(paramFFSConnectionHolder, paramCCCompanyAcctInfo);
      if (paramCCCompanyAcctInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "failed, Reason =" + paramCCCompanyAcctInfo.getStatusMsg(), 0);
        return paramCCCompanyAcctInfo;
      }
      String str2 = a(paramFFSConnectionHolder, paramCCCompanyAcctInfo.getCompId(), paramCCCompanyAcctInfo.getBankRTN(), paramCCCompanyAcctInfo.getAcctNumber(), paramCCCompanyAcctInfo.getTransactionType());
      if (str2 != null)
      {
        paramCCCompanyAcctInfo.setStatusCode(23410);
        str3 = "BPW Bank Account already exists AcctNumber: " + paramCCCompanyAcctInfo.getAcctNumber() + " BankRTN: " + paramCCCompanyAcctInfo.getBankRTN();
        paramCCCompanyAcctInfo.setStatusMsg(str3);
        FFSDebug.log(str1 + "failed. " + str3, 0);
        return paramCCCompanyAcctInfo;
      }
      paramCCCompanyAcctInfo = (CCCompanyAcctInfo)BPWBankAcct.createBPWBankAcctinfo(paramFFSConnectionHolder, paramCCCompanyAcctInfo);
      if (paramCCCompanyAcctInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "failed to create BPWBankAcct " + paramCCCompanyAcctInfo.getStatusMsg(), 0);
        return paramCCCompanyAcctInfo;
      }
      str3 = "INSERT into CC_CompanyAcct (AcctId, CompId, TransactionType)  Values (?, ?, ?)";
      localObject = new Object[] { paramCCCompanyAcctInfo.getAcctId(), paramCCCompanyAcctInfo.getCompId(), paramCCCompanyAcctInfo.getTransactionType() };
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, (Object[])localObject);
      paramCCCompanyAcctInfo.setStatusCode(0);
      paramCCCompanyAcctInfo.setStatusMsg("Success");
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      localObject = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, (String)localObject, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      Object localObject = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, (String)localObject, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "end ...", 6);
    return paramCCCompanyAcctInfo;
  }
  
  public static CCCompanyAcctInfo cancelCCCompanyAcct(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    String str = "CashCon.cancelCCCompanyAcct: ";
    FFSDebug.log(str + "start, AcctId =" + paramCCCompanyAcctInfo.getAcctId(), 6);
    if (paramCCCompanyAcctInfo.getAcctId() == null)
    {
      paramCCCompanyAcctInfo.setStatusCode(24390);
      paramCCCompanyAcctInfo.setStatusMsg("CC Company Acct Id cannot be Null");
      return paramCCCompanyAcctInfo;
    }
    if (a(paramFFSConnectionHolder, paramCCCompanyAcctInfo) == true)
    {
      paramCCCompanyAcctInfo.setStatusCode(24450);
      paramCCCompanyAcctInfo.setStatusMsg("This Company Acct has active location(s), AcctID =" + paramCCCompanyAcctInfo.getAcctId());
      return paramCCCompanyAcctInfo;
    }
    int i = h(paramFFSConnectionHolder, paramCCCompanyAcctInfo.getAcctId());
    if (i == 0)
    {
      paramCCCompanyAcctInfo.setStatusCode(16020);
      paramCCCompanyAcctInfo.setStatusMsg("CCCompAccount with AcctId =" + paramCCCompanyAcctInfo.getAcctId() + " record not found");
    }
    else
    {
      BPWBankAcctInfo localBPWBankAcctInfo = BPWBankAcct.deleteBPWBankInfoByAcctId(paramFFSConnectionHolder, paramCCCompanyAcctInfo.getAcctId());
      paramCCCompanyAcctInfo.setStatusCode(localBPWBankAcctInfo.getStatusCode());
      paramCCCompanyAcctInfo.setStatusMsg(localBPWBankAcctInfo.getStatusMsg());
    }
    FFSDebug.log(str + "end, status =" + paramCCCompanyAcctInfo.getStatusMsg(), 6);
    return paramCCCompanyAcctInfo;
  }
  
  public static CCCompanyAcctInfo modCCCompanyAcct(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    String str1 = "CashCon.modCCCompanyAcct: ";
    FFSDebug.log(str1 + "start, AcctId =" + paramCCCompanyAcctInfo.getAcctId(), 6);
    try
    {
      if (paramCCCompanyAcctInfo.getAcctId() == null)
      {
        paramCCCompanyAcctInfo.setStatusCode(24390);
        paramCCCompanyAcctInfo.setStatusMsg("CC Company Acct Id cannot be Null");
        FFSDebug.log(str1 + "failed, Reason =" + paramCCCompanyAcctInfo.getStatusMsg(), 0);
        return paramCCCompanyAcctInfo;
      }
      paramCCCompanyAcctInfo = jdMethod_if(paramFFSConnectionHolder, paramCCCompanyAcctInfo);
      if (paramCCCompanyAcctInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "failed, Reason =" + paramCCCompanyAcctInfo.getStatusMsg(), 0);
        return paramCCCompanyAcctInfo;
      }
      String str2 = a(paramFFSConnectionHolder, paramCCCompanyAcctInfo.getCompId(), paramCCCompanyAcctInfo.getBankRTN(), paramCCCompanyAcctInfo.getAcctNumber(), paramCCCompanyAcctInfo.getTransactionType());
      if ((str2 != null) && (str2.equals(paramCCCompanyAcctInfo.getAcctId())))
      {
        paramCCCompanyAcctInfo.setStatusCode(23410);
        str3 = "BPW Bank Account already exists AcctNumber: " + paramCCCompanyAcctInfo.getAcctNumber() + " BankRTN: " + paramCCCompanyAcctInfo.getBankRTN();
        paramCCCompanyAcctInfo.setStatusMsg(str3);
        FFSDebug.log(str1 + "failed. " + str3, 0);
        return paramCCCompanyAcctInfo;
      }
      str3 = "UPDATE CC_CompanyAcct set CompId = ?, TransactionType = ? Where AcctId = ?";
      localObject = new Object[] { paramCCCompanyAcctInfo.getCompId(), paramCCCompanyAcctInfo.getTransactionType(), paramCCCompanyAcctInfo.getAcctId() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, (Object[])localObject);
      if (i == 0)
      {
        paramCCCompanyAcctInfo.setStatusCode(16020);
        paramCCCompanyAcctInfo.setStatusMsg("CCCompanyAcctInfo with AcctId =" + paramCCCompanyAcctInfo.getAcctId() + " record not found");
      }
      else
      {
        paramCCCompanyAcctInfo.setStatusCode(0);
        paramCCCompanyAcctInfo.setStatusMsg("Success");
      }
      paramCCCompanyAcctInfo = (CCCompanyAcctInfo)BPWBankAcct.modBPWBankAcct(paramFFSConnectionHolder, paramCCCompanyAcctInfo);
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      localObject = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, (String)localObject, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      Object localObject = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, (String)localObject, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "end ...", 6);
    return paramCCCompanyAcctInfo;
  }
  
  public static CCCompanyAcctInfo getCCCompanyAcct(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    String str1 = "CashCon.getCCCompanyAcct :";
    String str2 = "AcctId =" + paramCCCompanyAcctInfo.getAcctId();
    if (paramCCCompanyAcctInfo.getCompId() != null) {
      str2 = str2 + " CompId =" + paramCCCompanyAcctInfo.getCompId();
    }
    if (paramCCCompanyAcctInfo.getTransactionType() != null) {
      str2 = str2 + " TransactionType =" + paramCCCompanyAcctInfo.getTransactionType();
    }
    FFSDebug.log(str1 + "start " + str2, 6);
    if (paramCCCompanyAcctInfo.getAcctId() == null)
    {
      paramCCCompanyAcctInfo.setStatusCode(24390);
      paramCCCompanyAcctInfo.setStatusMsg("CC Company Acct Id cannot be Null");
      return paramCCCompanyAcctInfo;
    }
    ArrayList localArrayList = new ArrayList();
    StringBuffer localStringBuffer = new StringBuffer();
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyAcctInfo.getAcctId(), "SELECT a.AcctId, a.CompId, a.TransactionType From CC_CompanyAcct a  Where a.AcctId = ?", localArrayList);
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyAcctInfo.getCompId(), " AND a.CompId = ?", localArrayList);
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyAcctInfo.getTransactionType(), " AND a.TransactionType = ?", localArrayList);
    String str3 = localStringBuffer.toString();
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = (Object[])localArrayList.toArray(new Object[0]);
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        paramCCCompanyAcctInfo = a(paramFFSConnectionHolder, localFFSResultSet, paramCCCompanyAcctInfo);
      }
      else
      {
        paramCCCompanyAcctInfo.setStatusCode(16020);
        paramCCCompanyAcctInfo.setStatusMsg("CCCompanyAcct with " + str2 + " record not found");
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, str5, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyAcctInfo.getStatusMsg(), 6);
    return paramCCCompanyAcctInfo;
  }
  
  public static CCCompanyAcctInfo getCCCompanyAcctByCompId(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    String str1 = "CashCon.getCCCompanyAcctByCompId :";
    String str2 = "CompId =" + paramCCCompanyAcctInfo.getCompId();
    if (paramCCCompanyAcctInfo.getTransactionType() != null) {
      str2 = str2 + ", TransactionType =" + paramCCCompanyAcctInfo.getTransactionType();
    }
    FFSDebug.log(str1 + "start " + str2, 6);
    if (paramCCCompanyAcctInfo.getCompId() == null)
    {
      paramCCCompanyAcctInfo.setStatusCode(24180);
      paramCCCompanyAcctInfo.setStatusMsg("CompId is Null");
      return paramCCCompanyAcctInfo;
    }
    ArrayList localArrayList = new ArrayList();
    StringBuffer localStringBuffer = new StringBuffer();
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyAcctInfo.getCompId(), "SELECT a.AcctId, a.CompId, a.TransactionType From CC_CompanyAcct a  Where a.CompId = ?", localArrayList);
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyAcctInfo.getTransactionType(), " AND a.TransactionType = ?", localArrayList);
    String str3 = localStringBuffer.toString();
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = (Object[])localArrayList.toArray(new Object[0]);
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        paramCCCompanyAcctInfo = a(paramFFSConnectionHolder, localFFSResultSet, paramCCCompanyAcctInfo);
      }
      else
      {
        paramCCCompanyAcctInfo.setStatusCode(16020);
        paramCCCompanyAcctInfo.setStatusMsg("CCCompanyAcct with " + str2 + " record not found");
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, str5, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end, Status =" + paramCCCompanyAcctInfo.getStatusMsg(), 6);
    return paramCCCompanyAcctInfo;
  }
  
  public static CCCompanyAcctInfoList getCCCompanyAcctList(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyAcctInfoList paramCCCompanyAcctInfoList)
    throws FFSException
  {
    String str1 = "CashCon.getCCCompanyAcctList :";
    String str2 = "CustomerId =" + paramCCCompanyAcctInfoList.getCustomerId() + ", CompId =" + paramCCCompanyAcctInfoList.getCompId() + ", TransactionType =" + paramCCCompanyAcctInfoList.getTransactionType();
    FFSDebug.log(str1 + "start " + str2, 6);
    StringBuffer localStringBuffer = new StringBuffer("SELECT a.AcctId, a.CompId, a.TransactionType From CC_CompanyAcct a , CC_Company b  Where a.CompId = b.CompId AND b.Status != 'CANCELEDON'");
    ArrayList localArrayList1 = new ArrayList();
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyAcctInfoList.getCustomerId(), " AND b.CustomerId = ?", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyAcctInfoList.getCompId(), " AND a.CompId = ?", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyAcctInfoList.getTransactionType(), " AND a.TransactionType = ?", localArrayList1);
    String str3 = localStringBuffer.toString();
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = (Object[])localArrayList1.toArray(new Object[0]);
    CCCompanyAcctInfo localCCCompanyAcctInfo = null;
    ArrayList localArrayList2 = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localCCCompanyAcctInfo = new CCCompanyAcctInfo();
        localCCCompanyAcctInfo = a(paramFFSConnectionHolder, localFFSResultSet, localCCCompanyAcctInfo);
        localArrayList2.add(localCCCompanyAcctInfo);
      }
      if (localArrayList2.size() == 0)
      {
        paramCCCompanyAcctInfoList.setStatusCode(16020);
        paramCCCompanyAcctInfoList.setStatusMsg("CCCompanyAcct with " + str2 + " " + " record not found");
      }
      else
      {
        paramCCCompanyAcctInfoList.setStatusCode(0);
        paramCCCompanyAcctInfoList.setStatusMsg("Success");
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, str5, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    paramCCCompanyAcctInfoList.setCCCompanyAcctInfoList((CCCompanyAcctInfo[])localArrayList2.toArray(new CCCompanyAcctInfo[0]));
    FFSDebug.log(str1 + "end, status =" + paramCCCompanyAcctInfoList.getStatusMsg(), 6);
    return paramCCCompanyAcctInfoList;
  }
  
  public static CCCompanyCutOffInfo addCCCompanyCutOff(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    String str1 = "CashCon.addCCCompanyCutOff: ";
    FFSDebug.log(str1 + "start ...", 6);
    try
    {
      if (paramCCCompanyCutOffInfo.getCompCutOffId() != null)
      {
        paramCCCompanyCutOffInfo.setStatusCode(24460);
        paramCCCompanyCutOffInfo.setStatusMsg("CC Company CutOff Id must be Null");
        return paramCCCompanyCutOffInfo;
      }
      paramCCCompanyCutOffInfo = a(paramFFSConnectionHolder, paramCCCompanyCutOffInfo);
      if (paramCCCompanyCutOffInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "failed, Reason =" + paramCCCompanyCutOffInfo.getStatusMsg(), 0);
        return paramCCCompanyCutOffInfo;
      }
      paramCCCompanyCutOffInfo.setCompCutOffId(DBUtil.getNextIndexStringWithPadding("CCCompCutOff", 32, '0'));
      String str2 = "INSERT into CC_CompanyCutOff (CompCutOffId, CutOffId, CompId, TransactionType) Values (?, ?, ?, ?)";
      localObject = new Object[] { paramCCCompanyCutOffInfo.getCompCutOffId(), paramCCCompanyCutOffInfo.getCutOffId(), paramCCCompanyCutOffInfo.getCompId(), paramCCCompanyCutOffInfo.getTransactionType() };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      paramCCCompanyCutOffInfo.setStatusCode(0);
      paramCCCompanyCutOffInfo.setStatusMsg("Success");
    }
    catch (FFSException localFFSException)
    {
      localObject = "*** " + str1 + " failed: ";
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log((String)localObject, str3, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      Object localObject = "*** " + str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject, str3, 0);
      throw new FFSException(localException, (String)localObject);
    }
    FFSDebug.log(str1 + "end ...", 6);
    return paramCCCompanyCutOffInfo;
  }
  
  public static CCCompanyCutOffInfo cancelCCCompanyCutOff(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    String str1 = "CashCon.cancelCCCompanyCutOff: ";
    FFSDebug.log(str1 + "start ...", 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      if (paramCCCompanyCutOffInfo.getCompCutOffId() == null)
      {
        paramCCCompanyCutOffInfo.setStatusCode(24490);
        paramCCCompanyCutOffInfo.setStatusMsg("CC Company CutOff Id cannot be Null");
        localObject1 = paramCCCompanyCutOffInfo;
        return localObject1;
      }
      Object localObject1 = "SELECT count(sch.CutOffId) FROM SCH_CutOffs sch, CC_CompanyCutOff cCutOff, CC_Entry entry, CC_Location loc  WHERE sch.Status='ACTIVE'  AND cCutOff.CutOffId = sch.CutOffId AND cCutOff.CompCutOffId = ?  AND cCutOff.CompId = loc.CompId AND entry.LocationId = loc.LocationId  AND entry.Status = 'WILLPROCESSON'";
      localObject2 = new Object[] { paramCCCompanyCutOffInfo.getCompCutOffId() };
      int i = 0;
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, (String)localObject1, (Object[])localObject2);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
      if (i > 0)
      {
        paramCCCompanyCutOffInfo.setStatusCode(24240);
        paramCCCompanyCutOffInfo.setStatusMsg("Pending transaction(s) exists  CompCutOffId =" + paramCCCompanyCutOffInfo.getCompCutOffId());
        CCCompanyCutOffInfo localCCCompanyCutOffInfo = paramCCCompanyCutOffInfo;
        return localCCCompanyCutOffInfo;
      }
      localObject1 = "Delete from CC_CompanyCutOff Where CompCutOffId = ?";
      int j = DBUtil.executeStatement(paramFFSConnectionHolder, (String)localObject1, (Object[])localObject2);
      if (j == 0)
      {
        paramCCCompanyCutOffInfo.setStatusCode(16020);
        paramCCCompanyCutOffInfo.setStatusMsg("CCCompanyCutOffInfo with CompCutOffId =" + paramCCCompanyCutOffInfo.getCompCutOffId() + " record not found");
      }
      else
      {
        paramCCCompanyCutOffInfo.setStatusCode(0);
        paramCCCompanyCutOffInfo.setStatusMsg("Success");
      }
    }
    catch (FFSException localFFSException)
    {
      localObject2 = "*** " + str1 + " failed: ";
      str2 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log((String)localObject2, str2, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      Object localObject2 = "*** " + str1 + " failed: ";
      String str2 = FFSDebug.stackTrace(localException1);
      FFSDebug.log((String)localObject2, str2, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end ...", 6);
    return paramCCCompanyCutOffInfo;
  }
  
  public static CCCompanyCutOffInfo getCCCompanyCutOff(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    String str1 = "CashCon.getCCCompanyCutOff :";
    FFSDebug.log(str1 + "start, compCutOffId =" + paramCCCompanyCutOffInfo.getCompCutOffId(), 6);
    if (paramCCCompanyCutOffInfo.getCompCutOffId() == null)
    {
      paramCCCompanyCutOffInfo.setStatusCode(24490);
      paramCCCompanyCutOffInfo.setStatusMsg("CC Company CutOff Id cannot be Null");
      return paramCCCompanyCutOffInfo;
    }
    String str2 = "SELECT a.CompCutOffId, a.CutOffId, a.CompId, a.TransactionType  From CC_CompanyCutOff a  Where a.CompCutOffId = ?";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramCCCompanyCutOffInfo.getCompCutOffId() };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        paramCCCompanyCutOffInfo = a(localFFSResultSet, paramCCCompanyCutOffInfo);
      }
      else
      {
        paramCCCompanyCutOffInfo.setStatusCode(16020);
        paramCCCompanyCutOffInfo.setStatusMsg("CCCompanyCutOffInfo with CompCutOffId =" + paramCCCompanyCutOffInfo.getCompCutOffId() + " " + " record not found");
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
    FFSDebug.log(str1 + "end, status =" + paramCCCompanyCutOffInfo.getStatusMsg(), 6);
    return paramCCCompanyCutOffInfo;
  }
  
  public static CCCompanyCutOffInfoList getCCCompanyCutOffList(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyCutOffInfoList paramCCCompanyCutOffInfoList)
    throws FFSException
  {
    String str1 = "CashCon.getCCCompanyCutOffList :";
    String str2 = "CompCutOffId =" + paramCCCompanyCutOffInfoList.getCompCutOffId() + ", CompId =" + paramCCCompanyCutOffInfoList.getCompId() + ", CutOffId =" + paramCCCompanyCutOffInfoList.getCutOffId() + ", CustomerId =" + paramCCCompanyCutOffInfoList.getCustomerId() + ", TransactionType =" + paramCCCompanyCutOffInfoList.getTransactionType();
    FFSDebug.log(str1 + "start " + str2, 6);
    StringBuffer localStringBuffer = new StringBuffer("SELECT a.CompCutOffId, a.CutOffId, a.CompId, a.TransactionType  From CC_CompanyCutOff a , CC_Company b  Where b.Status != 'CANCELEDON' AND a.CompId = b.CompId ");
    ArrayList localArrayList1 = new ArrayList();
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyCutOffInfoList.getCompCutOffId(), " AND a.CompCutOffId = ?", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyCutOffInfoList.getCompId(), " AND a.CompId = ?", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyCutOffInfoList.getCutOffId(), " AND a.CutOffId = ?", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyCutOffInfoList.getCustomerId(), " AND b.CustomerId = ?", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramCCCompanyCutOffInfoList.getTransactionType(), " AND a.TransactionType = ?", localArrayList1);
    String str3 = localStringBuffer.toString();
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = (Object[])localArrayList1.toArray(new Object[0]);
    CCCompanyCutOffInfo localCCCompanyCutOffInfo = null;
    ArrayList localArrayList2 = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localCCCompanyCutOffInfo = new CCCompanyCutOffInfo();
        localCCCompanyCutOffInfo = a(localFFSResultSet, localCCCompanyCutOffInfo);
        localArrayList2.add(localCCCompanyCutOffInfo);
      }
      if (localArrayList2.size() == 0)
      {
        paramCCCompanyCutOffInfoList.setStatusCode(16020);
        paramCCCompanyCutOffInfoList.setStatusMsg("CCCompanyCutOff with " + str2 + " " + " record not found");
      }
      else
      {
        paramCCCompanyCutOffInfoList.setStatusCode(0);
        paramCCCompanyCutOffInfoList.setStatusMsg("Success");
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, str5, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    paramCCCompanyCutOffInfoList.setCCCompanyCutOffInfoList((CCCompanyCutOffInfo[])localArrayList2.toArray(new CCCompanyCutOffInfo[0]));
    FFSDebug.log(str1 + "end, status =" + paramCCCompanyCutOffInfoList.getStatusMsg(), 6);
    return paramCCCompanyCutOffInfoList;
  }
  
  public static CCCompanyCutOffInfo[] getCCCompanyCutOffArrayByCutOffId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    String str1 = "CashCon.getCCCompanyCutOffListByCutOffId :";
    String str2 = "CutOffId =" + paramString1 + ", startCompId = " + paramString2 + ", size = " + paramInt;
    FFSDebug.log(str1 + "start " + str2, 6);
    StringBuffer localStringBuffer = new StringBuffer("SELECT a.CompCutOffId, a.CutOffId, a.CompId, a.TransactionType  From CC_CompanyCutOff a , CC_Company b  Where b.Status != 'CANCELEDON' AND a.CompId = b.CompId ");
    ArrayList localArrayList1 = new ArrayList();
    DBUtil.appendToCondition(localStringBuffer, paramString1, " AND a.CutOffId = ?", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramString2, " AND a.CompId > ?", localArrayList1);
    localStringBuffer.append(" ORDER BY a.CompId ASC");
    String str3 = localStringBuffer.toString();
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = (Object[])localArrayList1.toArray(new Object[0]);
    CCCompanyCutOffInfo localCCCompanyCutOffInfo = null;
    ArrayList localArrayList2 = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      while ((localFFSResultSet.getNextRow()) && (localArrayList2.size() <= paramInt))
      {
        localCCCompanyCutOffInfo = new CCCompanyCutOffInfo();
        localCCCompanyCutOffInfo = a(localFFSResultSet, localCCCompanyCutOffInfo);
        localArrayList2.add(localCCCompanyCutOffInfo);
      }
      if (localFFSResultSet.getNextRow())
      {
        String str4 = localFFSResultSet.getColumnString("CompID");
        if ((str4 != null) && (localCCCompanyCutOffInfo != null) && (str4.equals(localCCCompanyCutOffInfo.getCompId())))
        {
          localCCCompanyCutOffInfo = new CCCompanyCutOffInfo();
          localCCCompanyCutOffInfo = a(localFFSResultSet, localCCCompanyCutOffInfo);
          localArrayList2.add(localCCCompanyCutOffInfo);
        }
      }
    }
    catch (FFSException localFFSException)
    {
      str5 = "*** " + str1 + " failed: ";
      str6 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str5, str6, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str5 = "*** " + str1 + " failed: ";
      String str6 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str5, str6, 0);
      throw new FFSException(localException1, str5);
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
    FFSDebug.log(str1 + "end, count =" + localArrayList2.size(), 6);
    return (CCCompanyCutOffInfo[])localArrayList2.toArray(new CCCompanyCutOffInfo[0]);
  }
  
  public static CCLocationInfo addCCLocation(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    String str1 = "CashCon.addCCLocation: ";
    FFSDebug.log(str1 + "start. AgentId: " + paramCCLocationInfo.getAgentId() + ", Agent Type: " + paramCCLocationInfo.getAgentType(), 6);
    try
    {
      if (paramCCLocationInfo.getLocationId() != null)
      {
        paramCCLocationInfo.setStatusCode(24260);
        paramCCLocationInfo.setStatusMsg("Location Id must be Null");
        return paramCCLocationInfo;
      }
      paramCCLocationInfo = jdMethod_if(paramFFSConnectionHolder, paramCCLocationInfo);
      if (paramCCLocationInfo.getStatusCode() != 0) {
        return paramCCLocationInfo;
      }
      CCLocationInfo localCCLocationInfo = a(paramFFSConnectionHolder, paramCCLocationInfo.getCCLocationId(), paramCCLocationInfo.getBankRtn(), paramCCLocationInfo.getAccountNum());
      if (localCCLocationInfo.getStatusCode() == 0)
      {
        localCCLocationInfo.setStatusCode(24350);
        localCCLocationInfo.setStatusMsg("A location exists with the same CCLocationId, BankRTN, Account Number. CCLocationId =" + localCCLocationInfo.getCCLocationId() + ", BankRtn =" + localCCLocationInfo.getBankRtn() + ", AccountNum =" + localCCLocationInfo.getAccountNum());
        return localCCLocationInfo;
      }
      localCCLocationInfo = null;
      paramCCLocationInfo.setLocationId(DBUtil.getNextIndexStringWithPadding("CCLocationId", 32, '0'));
      paramCCLocationInfo.setLastRequestTime(FFSUtil.getDateString("yyyy/MM/dd HH:mm:ss"));
      str2 = "INSERT into CC_Location (LocationId, LocationName, CCLocationId, CompId, ConcentrateAcctId, DisburseAcctId, BankRtn, BankName, AccountNum, AccountType, DepositMin, DepositMax, AnticipatoryDepos, ThresholdDeposAmt, ConsolidateDepos, DepositPrenote,  DisbursePrenote, Memo, Status, LogId, LastRequestTime, SubmittedBy, AgentId, AgentType )  Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      localObject = new Object[] { paramCCLocationInfo.getLocationId(), paramCCLocationInfo.getLocationName(), paramCCLocationInfo.getCCLocationId(), paramCCLocationInfo.getCompId(), paramCCLocationInfo.getConcentrateAcctId(), paramCCLocationInfo.getDisburseAcctId(), paramCCLocationInfo.getBankRtn(), paramCCLocationInfo.getBankName(), paramCCLocationInfo.getAccountNum(), paramCCLocationInfo.getAccountType(), paramCCLocationInfo.getDepositMin(), paramCCLocationInfo.getDepositMax(), paramCCLocationInfo.getAnticipatoryDepos(), paramCCLocationInfo.getThresholdDeposAmt(), paramCCLocationInfo.getConsolidateDepos(), paramCCLocationInfo.getDepositPrenote(), paramCCLocationInfo.getDisbursePrenote(), paramCCLocationInfo.getMemo(), paramCCLocationInfo.getStatus(), paramCCLocationInfo.getLogId(), paramCCLocationInfo.getLastRequestTime(), paramCCLocationInfo.getSubmittedBy(), paramCCLocationInfo.getAgentId(), paramCCLocationInfo.getAgentType() };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      paramCCLocationInfo.setStatusCode(0);
      paramCCLocationInfo.setStatusMsg("Success");
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** " + str1 + " failed: ";
      localObject = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, (String)localObject, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = "*** " + str1 + " failed: ";
      Object localObject = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, (String)localObject, 0);
      throw new FFSException(localException, str2);
    }
    FFSDebug.log(str1 + "end ...", 6);
    return paramCCLocationInfo;
  }
  
  public static CCLocationInfo cancelCCLocation(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    String str = "CashCon.cancelCCLocation: ";
    FFSDebug.log(str + "start ...", 6);
    if (paramCCLocationInfo.getLocationId() == null)
    {
      paramCCLocationInfo.setStatusCode(24360);
      paramCCLocationInfo.setStatusMsg("Location Id cannot be Null");
      return paramCCLocationInfo;
    }
    if (isTransactionPending(paramFFSConnectionHolder, paramCCLocationInfo) == true)
    {
      paramCCLocationInfo.setStatusCode(24240);
      paramCCLocationInfo.setStatusMsg("Pending transaction(s) exists For this Location, LocationId =" + paramCCLocationInfo.getLocationId());
      return paramCCLocationInfo;
    }
    CCLocationInfo localCCLocationInfo = new CCLocationInfo();
    localCCLocationInfo.setLocationId(paramCCLocationInfo.getLocationId());
    localCCLocationInfo = getCCLocation(paramFFSConnectionHolder, localCCLocationInfo);
    if (localCCLocationInfo.getStatusCode() != 0)
    {
      paramCCLocationInfo.setStatusCode(localCCLocationInfo.getStatusCode());
      paramCCLocationInfo.setStatusMsg(localCCLocationInfo.getStatusMsg());
      return paramCCLocationInfo;
    }
    if ((paramCCLocationInfo.getSubmittedBy() != null) && (!paramCCLocationInfo.getSubmittedBy().trim().equals(""))) {
      localCCLocationInfo.setSubmittedBy(paramCCLocationInfo.getSubmittedBy());
    }
    int i = c(paramFFSConnectionHolder, "CANCELEDON", paramCCLocationInfo.getLocationId());
    if (i == 0)
    {
      paramCCLocationInfo.setStatusCode(16020);
      paramCCLocationInfo.setStatusMsg("CCLocationInfo with LocationId =" + paramCCLocationInfo.getLocationId() + " record not found");
    }
    else
    {
      paramCCLocationInfo.setStatus("CANCELEDON");
      localCCLocationInfo.setStatus("CANCELEDON");
      paramCCLocationInfo.setStatusCode(0);
      paramCCLocationInfo.setStatusMsg("Success");
      logCCLocationTransAuditLog(paramFFSConnectionHolder, localCCLocationInfo, "Cancel a Cash Con Location", 5305);
    }
    FFSDebug.log(str + "end ...", 6);
    return paramCCLocationInfo;
  }
  
  public static CCLocationInfo modCCLocation(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    String str1 = "CashCon.modCCLocation: ";
    FFSDebug.log(str1 + "start. AgentId: " + paramCCLocationInfo.getAgentId() + ", Agent Type: " + paramCCLocationInfo.getAgentType(), 6);
    try
    {
      if (paramCCLocationInfo.getLocationId() == null)
      {
        paramCCLocationInfo.setStatusCode(24360);
        paramCCLocationInfo.setStatusMsg("Location Id cannot be Null");
        return paramCCLocationInfo;
      }
      paramCCLocationInfo = jdMethod_if(paramFFSConnectionHolder, paramCCLocationInfo);
      if (paramCCLocationInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "failed, Reason =" + paramCCLocationInfo.getStatusMsg(), 0);
        return paramCCLocationInfo;
      }
      CCLocationInfo localCCLocationInfo = a(paramFFSConnectionHolder, paramCCLocationInfo.getCCLocationId(), paramCCLocationInfo.getBankRtn(), paramCCLocationInfo.getAccountNum());
      if ((localCCLocationInfo.getStatusCode() == 0) && (paramCCLocationInfo.getLocationId().compareTo(localCCLocationInfo.getLocationId()) != 0))
      {
        localCCLocationInfo.setStatusCode(24350);
        localCCLocationInfo.setStatusMsg("A location exists with the same CCLocationId, BankRTN, Account Number. CCLocationId =" + localCCLocationInfo.getCCLocationId() + ", BankRtn =" + localCCLocationInfo.getBankRtn() + ", AccountNum =" + localCCLocationInfo.getAccountNum());
        return localCCLocationInfo;
      }
      localCCLocationInfo = null;
      localObject = new CCLocationInfo();
      ((CCLocationInfo)localObject).setLocationId(paramCCLocationInfo.getLocationId());
      localObject = getCCLocation(paramFFSConnectionHolder, (CCLocationInfo)localObject);
      if (((CCLocationInfo)localObject).getStatusCode() != 0)
      {
        paramCCLocationInfo.setStatusCode(16020);
        paramCCLocationInfo.setStatusMsg("CCLocationInfo with LocationId =" + paramCCLocationInfo.getLocationId() + " record not found");
        return paramCCLocationInfo;
      }
      if ((paramCCLocationInfo.getCCLocationId().compareTo(((CCLocationInfo)localObject).getCCLocationId()) != 0) || (paramCCLocationInfo.getBankRtn().compareTo(((CCLocationInfo)localObject).getBankRtn()) != 0) || (paramCCLocationInfo.getAccountNum().compareTo(((CCLocationInfo)localObject).getAccountNum()) != 0))
      {
        paramCCLocationInfo.setDisPrenoteStatus(null);
        paramCCLocationInfo.setDepPrenoteStatus(null);
      }
      str2 = "Update CC_Location set LocationName = ? , CCLocationId = ?, CompId = ?, ConcentrateAcctId = ?, DisburseAcctId = ?, BankRtn = ?, BankName = ?, AccountNum = ?, AccountType = ?, DepositMin = ?, DepositMax = ?, AnticipatoryDepos = ?, ThresholdDeposAmt = ?, ConsolidateDepos = ?, DepositPrenote = ?, DepPrenoteStatus = ?, DisbursePrenote = ?, DisPrenoteStatus = ?, Memo = ?, Status = ?, AgentId = ?, AgentType = ?  Where LocationId = ?  AND Status != 'CANCELEDON'";
      Object[] arrayOfObject = { paramCCLocationInfo.getLocationName(), paramCCLocationInfo.getCCLocationId(), paramCCLocationInfo.getCompId(), paramCCLocationInfo.getConcentrateAcctId(), paramCCLocationInfo.getDisburseAcctId(), paramCCLocationInfo.getBankRtn(), paramCCLocationInfo.getBankName(), paramCCLocationInfo.getAccountNum(), paramCCLocationInfo.getAccountType(), paramCCLocationInfo.getDepositMin(), paramCCLocationInfo.getDepositMax(), paramCCLocationInfo.getAnticipatoryDepos(), paramCCLocationInfo.getThresholdDeposAmt(), paramCCLocationInfo.getConsolidateDepos(), paramCCLocationInfo.getDepositPrenote(), paramCCLocationInfo.getDepPrenoteStatus(), paramCCLocationInfo.getDisbursePrenote(), paramCCLocationInfo.getDisPrenoteStatus(), paramCCLocationInfo.getMemo(), paramCCLocationInfo.getStatus(), paramCCLocationInfo.getAgentId(), paramCCLocationInfo.getAgentType(), paramCCLocationInfo.getLocationId() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (i == 0)
      {
        paramCCLocationInfo.setStatusCode(16020);
        paramCCLocationInfo.setStatusMsg("CCLocationInfo with LocationId =" + paramCCLocationInfo.getLocationId() + " record not found");
      }
      else
      {
        paramCCLocationInfo.setStatusCode(0);
        paramCCLocationInfo.setStatusMsg("Success");
      }
    }
    catch (FFSException localFFSException)
    {
      localObject = "*** " + str1 + " failed: ";
      str2 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log((String)localObject, str2, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      Object localObject = "*** " + str1 + " failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject, str2, 0);
      throw new FFSException(localException, (String)localObject);
    }
    FFSDebug.log(str1 + "end ...", 6);
    return paramCCLocationInfo;
  }
  
  public static CCLocationInfo getCCLocation(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    String str1 = "CashCon.getCCLocation: ";
    FFSDebug.log(str1 + "start ...", 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      if (paramCCLocationInfo.getLocationId() == null)
      {
        paramCCLocationInfo.setStatusCode(24360);
        paramCCLocationInfo.setStatusMsg("Location Id cannot be Null");
        localObject1 = paramCCLocationInfo;
        return localObject1;
      }
      Object localObject1 = "SELECT cc.LocationId, cc.LocationName, cc.CCLocationId, cc.CompId, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BankRtn, cc.BankName, cc.AccountNum, cc.AccountType, cc.DepositMin, cc.DepositMax, cc.AnticipatoryDepos, cc.ThresholdDeposAmt, cc.ConsolidateDepos, cc.DepositPrenote, cc.DepPrenSubDate, cc.DepPrenoteStatus, cc.DisbursePrenote, cc.DisPrenSubDate, cc.DisPrenoteStatus, cc.Memo, cc.Status, cc.LogId, cc.LastRequestTime, cc.SubmittedBy, cc.AgentId, cc.AgentType  FROM CC_Location cc  Where cc.LocationId = ?  AND cc.Status !='CANCELEDON'";
      localObject2 = new Object[] { paramCCLocationInfo.getLocationId() };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, (String)localObject1, (Object[])localObject2);
      if (localFFSResultSet.getNextRow())
      {
        paramCCLocationInfo = a(localFFSResultSet, paramCCLocationInfo);
      }
      else
      {
        paramCCLocationInfo.setStatusCode(16020);
        paramCCLocationInfo.setStatusMsg("CCLocationInfo with LocationId =" + paramCCLocationInfo.getLocationId() + " record not found");
      }
    }
    catch (FFSException localFFSException)
    {
      localObject2 = "*** " + str1 + " failed: ";
      str2 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log((String)localObject2, str2, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      Object localObject2 = "*** " + str1 + " failed: ";
      String str2 = FFSDebug.stackTrace(localException1);
      FFSDebug.log((String)localObject2, str2, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end, status =" + paramCCLocationInfo.getStatusMsg(), 6);
    return paramCCLocationInfo;
  }
  
  public static CCLocationInfoList getCCLocationList(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfoList paramCCLocationInfoList)
    throws FFSException
  {
    String str1 = "CashCon.getCCLocationList :";
    String str2 = "FIId =" + paramCCLocationInfoList.getFIId() + ", CustomerId =" + paramCCLocationInfoList.getCustomerId() + ", CompId =" + paramCCLocationInfoList.getCompId() + ", ConcentrateAcctId =" + paramCCLocationInfoList.getConcentrateAcctId() + ", DisburseAcctId =" + paramCCLocationInfoList.getDisburseAcctId();
    if (paramCCLocationInfoList.getLocationIdList() != null) {
      str2 = str2 + ", LocationIds =" + Arrays.asList(paramCCLocationInfoList.getLocationIdList());
    }
    FFSDebug.log(str1 + "start " + str2, 6);
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList1 = new ArrayList();
    if (paramCCLocationInfoList.getFIId() != null) {
      localStringBuffer.append("SELECT cc.LocationId, cc.LocationName, cc.CCLocationId, cc.CompId, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BankRtn, cc.BankName, cc.AccountNum, cc.AccountType, cc.DepositMin, cc.DepositMax, cc.AnticipatoryDepos, cc.ThresholdDeposAmt, cc.ConsolidateDepos, cc.DepositPrenote, cc.DepPrenSubDate, cc.DepPrenoteStatus, cc.DisbursePrenote, cc.DisPrenSubDate, cc.DisPrenoteStatus, cc.Memo, cc.Status, cc.LogId, cc.LastRequestTime, cc.SubmittedBy, cc.AgentId, cc.AgentType  FROM CC_Location cc , CC_Company comp, BPW_Customer cust  Where cc.Status != 'CANCELEDON' AND cc.CompId = comp.CompId  AND cust.CustomerID = comp.CustomerId ");
    } else if (paramCCLocationInfoList.getCustomerId() != null) {
      localStringBuffer.append("SELECT cc.LocationId, cc.LocationName, cc.CCLocationId, cc.CompId, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BankRtn, cc.BankName, cc.AccountNum, cc.AccountType, cc.DepositMin, cc.DepositMax, cc.AnticipatoryDepos, cc.ThresholdDeposAmt, cc.ConsolidateDepos, cc.DepositPrenote, cc.DepPrenSubDate, cc.DepPrenoteStatus, cc.DisbursePrenote, cc.DisPrenSubDate, cc.DisPrenoteStatus, cc.Memo, cc.Status, cc.LogId, cc.LastRequestTime, cc.SubmittedBy, cc.AgentId, cc.AgentType  FROM CC_Location cc , CC_Company comp  Where cc.Status != 'CANCELEDON' AND comp.CompId = cc.CompId ");
    } else {
      localStringBuffer.append("SELECT cc.LocationId, cc.LocationName, cc.CCLocationId, cc.CompId, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BankRtn, cc.BankName, cc.AccountNum, cc.AccountType, cc.DepositMin, cc.DepositMax, cc.AnticipatoryDepos, cc.ThresholdDeposAmt, cc.ConsolidateDepos, cc.DepositPrenote, cc.DepPrenSubDate, cc.DepPrenoteStatus, cc.DisbursePrenote, cc.DisPrenSubDate, cc.DisPrenoteStatus, cc.Memo, cc.Status, cc.LogId, cc.LastRequestTime, cc.SubmittedBy, cc.AgentId, cc.AgentType  FROM CC_Location cc  Where cc.Status != 'CANCELEDON'");
    }
    DBUtil.appendToCondition(localStringBuffer, paramCCLocationInfoList.getFIId(), " AND cust.FIID = ? ", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramCCLocationInfoList.getCustomerId(), " AND comp.CustomerId = ? ", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramCCLocationInfoList.getCompId(), " AND cc.CompId = ? ", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramCCLocationInfoList.getConcentrateAcctId(), " AND cc.ConcentrateAcctId = ? ", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramCCLocationInfoList.getDisburseAcctId(), " AND cc.DisburseAcctId = ? ", localArrayList1);
    DBUtil.appendArrayToCondition(localStringBuffer, paramCCLocationInfoList.getLocationIdList(), " AND cc.LocationId in (", localArrayList1);
    String str3 = localStringBuffer.toString();
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = (Object[])localArrayList1.toArray(new Object[0]);
    CCLocationInfo localCCLocationInfo = null;
    ArrayList localArrayList2 = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localCCLocationInfo = new CCLocationInfo();
        localCCLocationInfo = a(localFFSResultSet, localCCLocationInfo);
        localArrayList2.add(localCCLocationInfo);
      }
      if (localArrayList2.size() == 0)
      {
        paramCCLocationInfoList.setStatusCode(16020);
        paramCCLocationInfoList.setStatusMsg("CCLocation with " + str2 + " " + " record not found");
      }
      else
      {
        paramCCLocationInfoList.setStatusCode(0);
        paramCCLocationInfoList.setStatusMsg("Success");
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, str5, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    paramCCLocationInfoList.setCCLocationInfoList((CCLocationInfo[])localArrayList2.toArray(new CCLocationInfo[0]));
    FFSDebug.log(str1 + "end, status =" + paramCCLocationInfoList.getStatusMsg(), 6);
    return paramCCLocationInfoList;
  }
  
  public static CCLocationInfo[] getCCLocationArrayByCompId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    String str1 = "CashCon.getCCLocationListByCompId :";
    String str2 = " CompId =" + paramString1 + ", Page size =" + paramInt;
    FFSDebug.log(str1 + "start " + str2, 6);
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList1 = new ArrayList();
    localStringBuffer.append("SELECT cc.LocationId, cc.LocationName, cc.CCLocationId, cc.CompId, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BankRtn, cc.BankName, cc.AccountNum, cc.AccountType, cc.DepositMin, cc.DepositMax, cc.AnticipatoryDepos, cc.ThresholdDeposAmt, cc.ConsolidateDepos, cc.DepositPrenote, cc.DepPrenSubDate, cc.DepPrenoteStatus, cc.DisbursePrenote, cc.DisPrenSubDate, cc.DisPrenoteStatus, cc.Memo, cc.Status, cc.LogId, cc.LastRequestTime, cc.SubmittedBy, cc.AgentId, cc.AgentType  FROM CC_Location cc  Where cc.Status != 'CANCELEDON'");
    DBUtil.appendToCondition(localStringBuffer, paramString1, " AND cc.CompId = ? ", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramString2, " AND cc.LocationId > ?", localArrayList1);
    localStringBuffer.append(" ORDER BY cc.LocationId ASC");
    String str3 = localStringBuffer.toString();
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = (Object[])localArrayList1.toArray(new Object[0]);
    CCLocationInfo localCCLocationInfo = null;
    ArrayList localArrayList2 = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      while ((localFFSResultSet.getNextRow()) && (localArrayList2.size() <= paramInt))
      {
        localCCLocationInfo = new CCLocationInfo();
        localCCLocationInfo = a(localFFSResultSet, localCCLocationInfo);
        localArrayList2.add(localCCLocationInfo);
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, str5, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end, count =" + localArrayList2.size(), 6);
    return (CCLocationInfo[])localArrayList2.toArray(new CCLocationInfo[0]);
  }
  
  public static CCEntryInfo addCCEntry(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    String str1 = "CashCon.addCCEntry: ";
    FFSDebug.log(str1 + "start ...", 6);
    try
    {
      String str2 = "INSERT into CC_Entry (EntryId, LocationId, Amount, DueDate, WillProcessTime, ProcessedTime, TransactionType, Memo, CreatedDate, EntryCategory, Status, SubmittedBy, LastProcessId, LogId, LastModifier ) Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      localObject = new Object[] { paramCCEntryInfo.getEntryId(), paramCCEntryInfo.getLocationId(), paramCCEntryInfo.getAmount(), paramCCEntryInfo.getDueDate(), paramCCEntryInfo.getWillProcessTime(), paramCCEntryInfo.getProcessedTime(), paramCCEntryInfo.getTransactionType(), paramCCEntryInfo.getMemo(), paramCCEntryInfo.getCreatedDate(), paramCCEntryInfo.getEntryCategory(), paramCCEntryInfo.getStatus(), paramCCEntryInfo.getSubmittedBy(), paramCCEntryInfo.getLastProcessId(), paramCCEntryInfo.getLogId(), paramCCEntryInfo.getLastModifier() };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      paramCCEntryInfo.setStatusCode(0);
      paramCCEntryInfo.setStatusMsg("Success");
    }
    catch (FFSException localFFSException)
    {
      localObject = "*** " + str1 + " failed: ";
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log((String)localObject, str3, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      Object localObject = "*** " + str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject, str3, 0);
      throw new FFSException(localException, (String)localObject);
    }
    FFSDebug.log(str1 + "end ...", 6);
    return paramCCEntryInfo;
  }
  
  public static CCEntryInfo addCCEntryFromAdapter(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    String str = "CashCon.addCCEntryFromAdapter: ";
    FFSDebug.log(str + "start ...", 6);
    paramCCEntryInfo.setEntryId(DBUtil.getNextIndexStringWithPadding("CCEntry", 32, '0'));
    paramCCEntryInfo.setCreatedDate(FFSUtil.getDateString("yyyy/MM/dd HH:mm:ss"));
    paramCCEntryInfo = addCCEntry(paramFFSConnectionHolder, paramCCEntryInfo);
    FFSDebug.log(str + "end, status =" + paramCCEntryInfo.getStatusMsg(), 6);
    return paramCCEntryInfo;
  }
  
  public static CCEntryInfo cancelCCEntry(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    String str = "CashCon.cancelCCEntry: ";
    FFSDebug.log(str + "start, EntryId =" + paramCCEntryInfo.getEntryId(), 6);
    if (paramCCEntryInfo.getEntryId() == null)
    {
      paramCCEntryInfo.setStatusCode(24610);
      paramCCEntryInfo.setStatusMsg("CC Entry Id cannot be Null");
      return paramCCEntryInfo;
    }
    paramCCEntryInfo = getCCEntry(paramFFSConnectionHolder, paramCCEntryInfo);
    if (paramCCEntryInfo.getStatusCode() != 0) {
      return paramCCEntryInfo;
    }
    if ((paramCCEntryInfo.getStatus().compareTo("WILLPROCESSON") != 0) && (paramCCEntryInfo.getStatus().compareTo("APPROVAL_REJECTED") != 0) && (paramCCEntryInfo.getStatus().compareTo("APPROVAL_PENDING") != 0))
    {
      paramCCEntryInfo.setStatusCode(24620);
      paramCCEntryInfo.setStatusMsg("This CC Entry has already been processed EntryId =" + paramCCEntryInfo.getEntryId());
      return paramCCEntryInfo;
    }
    int i = updateEntryStatus(paramFFSConnectionHolder, "CANCELEDON", paramCCEntryInfo.getEntryId());
    if (i == 0)
    {
      paramCCEntryInfo.setStatusCode(16020);
      paramCCEntryInfo.setStatusMsg("CCEntryInfo with EntryId =" + paramCCEntryInfo.getEntryId() + " record not found");
    }
    else
    {
      paramCCEntryInfo.setStatus("CANCELEDON");
      paramCCEntryInfo.setStatusCode(0);
      paramCCEntryInfo.setStatusMsg("Success");
    }
    return paramCCEntryInfo;
  }
  
  public static CCEntryInfo modCCEntry(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    String str1 = "CashCon.modCCEntry: ";
    FFSDebug.log(str1 + "start, EntryId =" + paramCCEntryInfo.getEntryId(), 6);
    try
    {
      if (paramCCEntryInfo.getEntryId() == null)
      {
        paramCCEntryInfo.setStatusCode(24610);
        paramCCEntryInfo.setStatusMsg("CC Entry Id cannot be Null");
        FFSDebug.log(str1 + "failed, Reason =" + paramCCEntryInfo.getStatusMsg(), 0);
        return paramCCEntryInfo;
      }
      CCLocationInfo localCCLocationInfo = new CCLocationInfo();
      localCCLocationInfo.setLocationId(paramCCEntryInfo.getLocationId());
      localCCLocationInfo = getCCLocation(paramFFSConnectionHolder, localCCLocationInfo);
      if ((localCCLocationInfo == null) || (localCCLocationInfo.getStatusCode() != 0))
      {
        paramCCEntryInfo.setStatusCode(localCCLocationInfo.getStatusCode());
        paramCCEntryInfo.setStatusMsg(localCCLocationInfo.getStatusMsg() + localCCLocationInfo.getLocationId());
        FFSDebug.log(str1 + paramCCEntryInfo.getStatusMsg(), 0);
        return paramCCEntryInfo;
      }
      paramCCEntryInfo.setCompId(localCCLocationInfo.getCompId());
      str2 = "UPDATE CC_Entry set LocationId = ?, Amount = ?, DueDate =?, WillProcessTime = ?, ProcessedTime = ?, TransactionType = ?, Memo =?, EntryCategory =?,  Status = ?, LastModifier = ?  Where EntryId = ?";
      localObject = new Object[] { paramCCEntryInfo.getLocationId(), paramCCEntryInfo.getAmount(), paramCCEntryInfo.getDueDate(), paramCCEntryInfo.getWillProcessTime(), paramCCEntryInfo.getProcessedTime(), paramCCEntryInfo.getTransactionType(), paramCCEntryInfo.getMemo(), paramCCEntryInfo.getEntryCategory(), paramCCEntryInfo.getStatus(), paramCCEntryInfo.getLastModifier(), paramCCEntryInfo.getEntryId() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      if (i == 0)
      {
        paramCCEntryInfo.setStatusCode(16020);
        paramCCEntryInfo.setStatusMsg("CCEntryInfo with EntryId =" + paramCCEntryInfo.getEntryId() + " record not found");
      }
      else
      {
        paramCCEntryInfo.setStatusCode(0);
        paramCCEntryInfo.setStatusMsg("Success");
      }
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** " + str1 + " failed: ";
      localObject = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, (String)localObject, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = "*** " + str1 + " failed: ";
      Object localObject = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, (String)localObject, 0);
      throw new FFSException(localException, str2);
    }
    FFSDebug.log(str1 + "end, status =" + paramCCEntryInfo.getStatusMsg(), 6);
    return paramCCEntryInfo;
  }
  
  public static CCEntryInfo getCCEntry(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    String str1 = "CashCon.getCCEntry :";
    FFSDebug.log(str1 + "start, EntryId =" + paramCCEntryInfo.getEntryId(), 6);
    if (paramCCEntryInfo.getEntryId() == null)
    {
      paramCCEntryInfo.setStatusCode(24610);
      paramCCEntryInfo.setStatusMsg("CC Entry Id cannot be Null");
      return paramCCEntryInfo;
    }
    String str2 = "SELECT a.EntryId, a.LocationId, a.Amount, a.DueDate, a.WillProcessTime,a.ProcessedTime, a.TransactionType, a.Memo, a.CreatedDate, a.EntryCategory,a.Status, a.SubmittedBy, a.LastProcessId, a.LogId, a.LastModifier, b.CompId  From CC_Entry a, CC_Location b WHERE a.LocationId = b.LocationId  AND a.EntryId = ? ";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramCCEntryInfo.getEntryId() };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        paramCCEntryInfo = a(localFFSResultSet, paramCCEntryInfo);
      }
      else
      {
        paramCCEntryInfo.setStatusCode(16020);
        paramCCEntryInfo.setStatusMsg("CCEntryInfo with EntryId =" + paramCCEntryInfo.getEntryId() + " " + " record not found");
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
    FFSDebug.log(str1 + "end, status =" + paramCCEntryInfo.getStatusMsg(), 6);
    return paramCCEntryInfo;
  }
  
  public static CCEntryHistInfo getCCEntryHist(FFSConnectionHolder paramFFSConnectionHolder, CCEntryHistInfo paramCCEntryHistInfo)
    throws FFSException
  {
    String str = "CashCon.getCCEntryHist :";
    long l = paramCCEntryHistInfo.getPageSize();
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    int i = localPropertyConfig.getBatchSize();
    if (l > i)
    {
      paramCCEntryHistInfo.setStatusCode(17030);
      paramCCEntryHistInfo.setStatusMsg("Paging size bigger than system batch size");
      return paramCCEntryHistInfo;
    }
    paramCCEntryHistInfo = jdMethod_do(paramCCEntryHistInfo);
    if (paramCCEntryHistInfo.getStatusCode() != 0) {
      return paramCCEntryHistInfo;
    }
    a(paramFFSConnectionHolder, l, paramCCEntryHistInfo);
    if (paramCCEntryHistInfo.getCCEntryInfoList().length > 0)
    {
      paramCCEntryHistInfo.setStatusCode(0);
      paramCCEntryHistInfo.setStatusMsg("Success");
    }
    else
    {
      paramCCEntryHistInfo.setStatusCode(17020);
      paramCCEntryHistInfo.setStatusMsg("No Records Found");
    }
    FFSDebug.log(str + "end, status =" + paramCCEntryHistInfo.getStatusMsg(), 6);
    return paramCCEntryHistInfo;
  }
  
  public static CCEntrySummaryInfoList getCCEntrySummaryList(FFSConnectionHolder paramFFSConnectionHolder, CCEntrySummaryInfoList paramCCEntrySummaryInfoList)
    throws FFSException
  {
    String str1 = "CashCon.getCCEntrySummaryList :";
    String str2 = "FIId =" + paramCCEntrySummaryInfoList.getFIId() + ", CustomerId =" + paramCCEntrySummaryInfoList.getCustomerId() + ", CompId =" + paramCCEntrySummaryInfoList.getCompId();
    if (paramCCEntrySummaryInfoList.getLocationIdList() != null) {
      str2 = str2 + ", Location Ids =" + Arrays.asList(paramCCEntrySummaryInfoList.getLocationIdList());
    }
    if (paramCCEntrySummaryInfoList.getTransactionTypeList() != null) {
      str2 = str2 + ", Transaction Types =" + Arrays.asList(paramCCEntrySummaryInfoList.getTransactionTypeList());
    }
    if (paramCCEntrySummaryInfoList.getCategoryTypeList() != null) {
      str2 = str2 + ", Category Types =" + Arrays.asList(paramCCEntrySummaryInfoList.getCategoryTypeList());
    }
    if (paramCCEntrySummaryInfoList.getStatusList() != null) {
      str2 = str2 + ", Status List =" + Arrays.asList(paramCCEntrySummaryInfoList.getStatusList());
    }
    FFSDebug.log(str1 + "start, " + str2, 6);
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList1 = new ArrayList();
    localStringBuffer.append("SELECT a.Amount Amount, a.TransactionType,  c.CompId, b.LocationId, d.CustomerID  FROM CC_Entry a, CC_Location b, CC_Company c, BPW_Customer d  Where a.LocationId = b.LocationId AND b.CompId = c.CompId  AND c.CustomerId = d.CustomerID  AND a.Status != 'CANCELEDON'");
    DBUtil.appendToCondition(localStringBuffer, paramCCEntrySummaryInfoList.getFIId(), " AND d.FIID = ?", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramCCEntrySummaryInfoList.getCustomerId(), " AND c.CustomerId = ?", localArrayList1);
    DBUtil.appendToCondition(localStringBuffer, paramCCEntrySummaryInfoList.getCompId(), " AND c.CompId = ?", localArrayList1);
    DBUtil.appendArrayToCondition(localStringBuffer, paramCCEntrySummaryInfoList.getLocationIdList(), " AND a.LocationId IN (", localArrayList1);
    DBUtil.appendArrayToCondition(localStringBuffer, paramCCEntrySummaryInfoList.getTransactionTypeList(), " AND a.TransactionType IN (", localArrayList1);
    DBUtil.appendArrayToCondition(localStringBuffer, paramCCEntrySummaryInfoList.getCategoryTypeList(), " AND a.EntryCategory IN (", localArrayList1);
    DBUtil.appendArrayToCondition(localStringBuffer, paramCCEntrySummaryInfoList.getStatusList(), " AND a.Status IN (", localArrayList1);
    String str3 = localStringBuffer.toString();
    str3 = str3 + " Order By d.CustomerId, c.CompId, b.LocationId, a.TransactionType ";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = (Object[])localArrayList1.toArray(new Object[0]);
    ArrayList localArrayList2 = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      Object localObject1 = "";
      str4 = "";
      localObject2 = "";
      String str5 = "";
      Object localObject3 = "";
      String str6 = "";
      String str7 = "";
      float f = 0.0F;
      CCEntrySummaryInfo localCCEntrySummaryInfo = null;
      while (localFFSResultSet.getNextRow())
      {
        str4 = localFFSResultSet.getColumnString("CompId");
        str5 = localFFSResultSet.getColumnString("CustomerId");
        str6 = localFFSResultSet.getColumnString("LocationId");
        if ((((String)localObject2).compareTo(str5) != 0) || (((String)localObject1).compareTo(str4) != 0) || (((String)localObject3).compareTo(str6) != 0))
        {
          localCCEntrySummaryInfo = new CCEntrySummaryInfo();
          localCCEntrySummaryInfo.setCustomerId(str5);
          localCCEntrySummaryInfo.setCompId(str4);
          localCCEntrySummaryInfo.setLocationId(str6);
          localArrayList2.add(localCCEntrySummaryInfo);
        }
        localObject1 = str4;
        localObject3 = str6;
        localObject2 = str5;
        str7 = localFFSResultSet.getColumnString("TransactionType");
        f = localFFSResultSet.getColumnFloat("Amount");
        if (str7.compareTo("CONCENTRATION") == 0)
        {
          localCCEntrySummaryInfo.setNumOfDepositEntries(localCCEntrySummaryInfo.getNumOfDepositEntries() + 1);
          localCCEntrySummaryInfo.setTotalDepositAmt(localCCEntrySummaryInfo.getTotalDepositAmt() + f);
        }
        if (str7.compareTo("DISBURSEMENT") == 0)
        {
          localCCEntrySummaryInfo.setNumOfDisburseEntries(localCCEntrySummaryInfo.getNumOfDisburseEntries() + 1);
          localCCEntrySummaryInfo.setTotalDisbursmentAmt(localCCEntrySummaryInfo.getTotalDisbursmentAmt() + f);
        }
      }
      CCEntrySummaryInfo[] arrayOfCCEntrySummaryInfo = (CCEntrySummaryInfo[])localArrayList2.toArray(new CCEntrySummaryInfo[0]);
      paramCCEntrySummaryInfoList.setCCEntrySummaryInfoList(arrayOfCCEntrySummaryInfo);
      if (localArrayList2.size() == 0)
      {
        paramCCEntrySummaryInfoList.setStatusCode(16020);
        paramCCEntrySummaryInfoList.setStatusMsg("CCCompany with " + str2 + " record not found");
      }
      else
      {
        paramCCEntrySummaryInfoList.setStatusCode(0);
        paramCCEntrySummaryInfoList.setStatusMsg("Success");
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      localObject2 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, (String)localObject2, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str4 = "*** " + str1 + " failed: ";
      Object localObject2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, (String)localObject2, 0);
      throw new FFSException(localException, str4);
    }
    finally
    {
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
    }
    FFSDebug.log(str1 + "end, status =" + paramCCEntrySummaryInfoList.getStatusMsg(), 6);
    return paramCCEntrySummaryInfoList;
  }
  
  public static CCCompanyInfo getCCCompanyByCCCompId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    return jdMethod_int(paramFFSConnectionHolder, paramString, true);
  }
  
  private static CCCompanyInfo jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "CashCon.getCCCompanyByCCCompId :";
    FFSDebug.log(str1 + "start, cCCompId =" + paramString, 6);
    String str2 = "SELECT cc.CompId, cc.CustomerId, cc.CCCompId, cc.CompName, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BatchType, cc.Status, cc.SubmittedBy, cc.LogId  FROM CC_Company cc  WHERE cc.CCCompId = ?";
    if (paramBoolean) {
      str2 = str2 + " AND cc.Status != 'CANCELEDON'";
    }
    FFSResultSet localFFSResultSet = null;
    CCCompanyInfo localCCCompanyInfo = null;
    Object[] arrayOfObject = { paramString };
    try
    {
      localCCCompanyInfo = new CCCompanyInfo();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localCCCompanyInfo = a(localFFSResultSet, localCCCompanyInfo);
      }
      else
      {
        localCCCompanyInfo.setStatusCode(16020);
        localCCCompanyInfo.setStatusMsg("CCCompanyInfo with cCCompId =" + paramString + " " + " record not found");
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
    FFSDebug.log(str1 + "end, Status =" + localCCCompanyInfo.getStatusMsg(), 6);
    return localCCCompanyInfo;
  }
  
  private static boolean j(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "CashCon.checkActiveLocationForCompany :";
    FFSDebug.log(str1 + "start, CompId =" + paramString, 6);
    String str2 = "SELECT count(LocationId) FROM CC_Location WHERE CompId = ?  AND Status !='CANCELEDON'";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString };
    int i = 0;
    boolean bool = false;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
      bool = i > 0;
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
    FFSDebug.log(str1 + "end, activeLocPresent =" + bool, 6);
    return bool;
  }
  
  private static int i(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "CashCon.cancelCompanyAcctByCompId :";
    FFSDebug.log(str1 + "start, compId =" + paramString, 6);
    String str2 = "Delete from CC_CompanyAcct WHERE CompId = ? ";
    int i = -1;
    Object[] arrayOfObject = { paramString };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "end, No of rows deleted =" + i, 6);
    return i;
  }
  
  private static int h(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "CashCon.cancelCompanyAcctByAcctId :";
    FFSDebug.log(str1 + "start, acctId =" + paramString, 6);
    String str2 = "Delete from CC_CompanyAcct WHERE AcctId = ? ";
    int i = -1;
    Object[] arrayOfObject = { paramString };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "end, No of rows deleted =" + i, 6);
    return i;
  }
  
  private static int b(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "CashCon.updateCompanyStatus :";
    FFSDebug.log(str1 + "start, compId =" + paramString2 + ", newStatus =" + paramString1, 6);
    String str2 = "Update CC_Company set Status = ? where CompId = ?  AND Status != 'CANCELEDON'";
    int i = -1;
    Object[] arrayOfObject = { paramString1, paramString2 };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "end, No of rows updated =" + i, 6);
    return i;
  }
  
  private static int c(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "CashCon.updateLocationStatus :";
    FFSDebug.log(str1 + "start, locationId =" + paramString2 + ", newStatus =" + paramString1, 6);
    String str2 = "Update CC_Location set Status = ? Where LocationId = ?  AND Status != 'CANCELEDON'";
    int i = -1;
    Object[] arrayOfObject = { paramString1, paramString2 };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "end, No of rows updated =" + i, 6);
    return i;
  }
  
  public static int updateEntryStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "CashCon.updateEntryStatus :";
    FFSDebug.log(str1 + "start, entryId =" + paramString2 + ", newStatus =" + paramString1, 6);
    String str2 = "UPDATE CC_Entry set Status = ? Where EntryId = ?  AND Status != 'CANCELEDON'";
    int i = -1;
    Object[] arrayOfObject = { paramString1, paramString2 };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "end, No of rows updated =" + i, 6);
    return i;
  }
  
  private static CCCompanyInfo jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyInfo paramCCCompanyInfo)
    throws Exception
  {
    if (paramCCCompanyInfo.getCustomerId() == null)
    {
      paramCCCompanyInfo.setStatusCode(24090);
      paramCCCompanyInfo.setStatusMsg("Customer ID is null");
      return paramCCCompanyInfo;
    }
    if (paramCCCompanyInfo.getCCCompId() == null)
    {
      paramCCCompanyInfo.setStatusCode(24100);
      paramCCCompanyInfo.setStatusMsg("CCCompId is null");
      return paramCCCompanyInfo;
    }
    if (paramCCCompanyInfo.getCompName() == null)
    {
      paramCCCompanyInfo.setStatusCode(24110);
      paramCCCompanyInfo.setStatusMsg("CCCompName is Null");
      return paramCCCompanyInfo;
    }
    if (paramCCCompanyInfo.getBatchType() == null)
    {
      paramCCCompanyInfo.setStatusCode(24120);
      paramCCCompanyInfo.setStatusMsg("CCBatchType is Null");
      return paramCCCompanyInfo;
    }
    if ((paramCCCompanyInfo.getSubmittedBy() == null) || (paramCCCompanyInfo.getAgentId() == null) || (paramCCCompanyInfo.getAgentId().length() == 0))
    {
      paramCCCompanyInfo.setStatusCode(24130);
      paramCCCompanyInfo.setStatusMsg("SubmittedBy is Null");
      return paramCCCompanyInfo;
    }
    if (paramCCCompanyInfo.getLogId() == null)
    {
      paramCCCompanyInfo.setStatusCode(24140);
      paramCCCompanyInfo.setStatusMsg("LogId is Null");
      return paramCCCompanyInfo;
    }
    if ((paramCCCompanyInfo.getBatchType().compareTo("UnbalancedBatch") != 0) && (paramCCCompanyInfo.getBatchType().compareTo("BatchBalancedBatch") != 0) && (paramCCCompanyInfo.getBatchType().compareTo("EntryBalancedBatch") != 0))
    {
      paramCCCompanyInfo.setStatusCode(24250);
      paramCCCompanyInfo.setStatusMsg("Invalid Batch Type. Valid Batch Types are :UnbalancedBatch, BatchBalancedBatch, EntryBalancedBatch. BatchType received =" + paramCCCompanyInfo.getBatchType());
      return paramCCCompanyInfo;
    }
    CustomerInfo localCustomerInfo = Customer.getCustomerInfo(paramCCCompanyInfo.getCustomerId(), paramFFSConnectionHolder, paramCCCompanyInfo);
    if (localCustomerInfo == null)
    {
      paramCCCompanyInfo.setStatusCode(19130);
      String str1 = BPWLocaleUtil.getMessage(19130, new String[] { paramCCCompanyInfo.getCustomerId() }, "TRANSFER_MESSAGE");
      paramCCCompanyInfo.setStatusMsg(str1);
      return paramCCCompanyInfo;
    }
    localCustomerInfo = null;
    int i = paramCCCompanyInfo.getCCCompId().length();
    if ((i < 1) || (i > 10))
    {
      paramCCCompanyInfo.setStatusCode(24150);
      paramCCCompanyInfo.setStatusMsg("Invalid CC Company Id. The length of Cash Con Company Id should be between 1 and 10, CompId =" + paramCCCompanyInfo.getCCCompId());
      return paramCCCompanyInfo;
    }
    i = paramCCCompanyInfo.getCompName().length();
    if ((i < 1) || (i > 16))
    {
      paramCCCompanyInfo.setStatusCode(24160);
      paramCCCompanyInfo.setStatusMsg("Invalid CC Company Name. The length of Cash Con Company Name should be between 1 and 16, CompName =" + paramCCCompanyInfo.getCompName());
      return paramCCCompanyInfo;
    }
    String str2 = paramCCCompanyInfo.getStatus();
    if (str2 == null)
    {
      paramCCCompanyInfo.setStatusCode(24210);
      paramCCCompanyInfo.setStatusMsg("Status is Null");
      return paramCCCompanyInfo;
    }
    if ((str2.compareTo("ACTIVE") != 0) && (str2.compareTo("INACTIVE") != 0))
    {
      paramCCCompanyInfo.setStatusCode(24220);
      paramCCCompanyInfo.setStatusMsg("Invalid status for this operation. Permitted Status are :ACTIVE, INACTIVE");
      return paramCCCompanyInfo;
    }
    paramCCCompanyInfo.setStatusCode(0);
    paramCCCompanyInfo.setStatusMsg("Success");
    return paramCCCompanyInfo;
  }
  
  private static CCLocationInfo jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    if (paramCCLocationInfo.getLocationName() == null)
    {
      paramCCLocationInfo.setStatusCode(24270);
      paramCCLocationInfo.setStatusMsg("CC Location Name is Null");
      return paramCCLocationInfo;
    }
    if (paramCCLocationInfo.getCCLocationId() == null)
    {
      paramCCLocationInfo.setStatusCode(24280);
      paramCCLocationInfo.setStatusMsg("CCLocationId is Null");
      return paramCCLocationInfo;
    }
    if (paramCCLocationInfo.getCompId() == null)
    {
      paramCCLocationInfo.setStatusCode(24180);
      paramCCLocationInfo.setStatusMsg("CompId is Null");
      return paramCCLocationInfo;
    }
    if (paramCCLocationInfo.getBankRtn() == null)
    {
      paramCCLocationInfo.setStatusCode(24290);
      paramCCLocationInfo.setStatusMsg("Bank RTN is Null");
      return paramCCLocationInfo;
    }
    if (paramCCLocationInfo.getAccountNum() == null)
    {
      paramCCLocationInfo.setStatusCode(24300);
      paramCCLocationInfo.setStatusMsg("Account Number is Null");
      return paramCCLocationInfo;
    }
    if (paramCCLocationInfo.getAccountType() == null)
    {
      paramCCLocationInfo.setStatusCode(24310);
      paramCCLocationInfo.setStatusMsg("Account Type is Null");
      return paramCCLocationInfo;
    }
    if (paramCCLocationInfo.getSubmittedBy() == null)
    {
      paramCCLocationInfo.setStatusCode(24130);
      paramCCLocationInfo.setStatusMsg("SubmittedBy is Null");
      return paramCCLocationInfo;
    }
    if (paramCCLocationInfo.getLogId() == null)
    {
      paramCCLocationInfo.setStatusCode(24140);
      paramCCLocationInfo.setStatusMsg("LogId is Null");
      return paramCCLocationInfo;
    }
    String str = paramCCLocationInfo.getStatus();
    if (str == null)
    {
      paramCCLocationInfo.setStatusCode(24210);
      paramCCLocationInfo.setStatusMsg("Status is Null");
      return paramCCLocationInfo;
    }
    if ((str.compareTo("ACTIVE") != 0) && (str.compareTo("INACTIVE") != 0))
    {
      paramCCLocationInfo.setStatusCode(24220);
      paramCCLocationInfo.setStatusMsg("Invalid status for this operation. Permitted Status are :ACTIVE, INACTIVE");
      return paramCCLocationInfo;
    }
    CCCompanyInfo localCCCompanyInfo = new CCCompanyInfo();
    localCCCompanyInfo.setCompId(paramCCLocationInfo.getCompId());
    localCCCompanyInfo = getCCCompany(paramFFSConnectionHolder, localCCCompanyInfo);
    if (localCCCompanyInfo.getStatusCode() != 0)
    {
      paramCCLocationInfo.setStatusCode(16080);
      paramCCLocationInfo.setStatusMsg("Company does not exist: CompId =" + paramCCLocationInfo.getCompId());
      return paramCCLocationInfo;
    }
    localCCCompanyInfo = null;
    CCCompanyAcctInfo localCCCompanyAcctInfo = null;
    if (paramCCLocationInfo.getConcentrateAcctId() != null)
    {
      localCCCompanyAcctInfo = new CCCompanyAcctInfo();
      localCCCompanyAcctInfo.setAcctId(paramCCLocationInfo.getConcentrateAcctId());
      localCCCompanyAcctInfo.setCompId(paramCCLocationInfo.getCompId());
      localCCCompanyAcctInfo.setTransactionType("CONCENTRATION");
      localCCCompanyAcctInfo = getCCCompanyAcct(paramFFSConnectionHolder, localCCCompanyAcctInfo);
      if (localCCCompanyAcctInfo.getStatusCode() != 0)
      {
        paramCCLocationInfo.setStatusCode(24330);
        paramCCLocationInfo.setStatusMsg("Invalid Concentration Acct Id, Concentrate AcctId =" + paramCCLocationInfo.getConcentrateAcctId());
        return paramCCLocationInfo;
      }
    }
    if (paramCCLocationInfo.getDisburseAcctId() != null)
    {
      localCCCompanyAcctInfo = new CCCompanyAcctInfo();
      localCCCompanyAcctInfo.setAcctId(paramCCLocationInfo.getDisburseAcctId());
      localCCCompanyAcctInfo.setCompId(paramCCLocationInfo.getCompId());
      localCCCompanyAcctInfo.setTransactionType("DISBURSEMENT");
      localCCCompanyAcctInfo = getCCCompanyAcct(paramFFSConnectionHolder, localCCCompanyAcctInfo);
      if (localCCCompanyAcctInfo.getStatusCode() != 0)
      {
        paramCCLocationInfo.setStatusCode(24380);
        paramCCLocationInfo.setStatusMsg("Invalid Disbursement Acct Id, Disburse AcctId =" + paramCCLocationInfo.getDisburseAcctId());
        return paramCCLocationInfo;
      }
    }
    localCCCompanyAcctInfo = null;
    int i = paramCCLocationInfo.getCCLocationId().length();
    if ((i < 1) || (i > 15))
    {
      paramCCLocationInfo.setStatusCode(24340);
      paramCCLocationInfo.setStatusMsg("Invalid CCLocationId. Length should be between 1 and 15. CCLocationId =" + paramCCLocationInfo.getCCLocationId());
      return paramCCLocationInfo;
    }
    i = paramCCLocationInfo.getLocationName().length();
    if ((i < 1) || (i > 22))
    {
      paramCCLocationInfo.setStatusCode(24340);
      paramCCLocationInfo.setStatusMsg("Invalid CCLocation Name. Length should be between 1 and 22. LocationName =" + paramCCLocationInfo.getLocationName());
      return paramCCLocationInfo;
    }
    if (!an(paramCCLocationInfo.getBankRtn()))
    {
      paramCCLocationInfo.setStatusCode(23670);
      paramCCLocationInfo.setStatusMsg("Invalid BankRTN =" + paramCCLocationInfo.getBankRtn());
      return paramCCLocationInfo;
    }
    if (!am(paramCCLocationInfo.getAccountNum()))
    {
      paramCCLocationInfo.setStatusCode(24370);
      paramCCLocationInfo.setStatusMsg("Invalid Bank Acct Num, Length should be less than 22 , Acct Num =" + paramCCLocationInfo.getAccountNum());
      return paramCCLocationInfo;
    }
    if ((paramCCLocationInfo.getDepositPrenote() == null) || (paramCCLocationInfo.getDepositPrenote().compareTo("N") == 0)) {
      paramCCLocationInfo.setDepPrenoteStatus(null);
    }
    if ((paramCCLocationInfo.getDisbursePrenote() == null) || (paramCCLocationInfo.getDisbursePrenote().compareTo("N") == 0)) {
      paramCCLocationInfo.setDisPrenoteStatus(null);
    }
    if ((paramCCLocationInfo.getAccountType().compareTo("Checking") != 0) && (paramCCLocationInfo.getAccountType().compareTo("Savings") != 0))
    {
      paramCCLocationInfo.setStatusCode(24760);
      paramCCLocationInfo.setStatusMsg("Account Type specified is not valid. Valid values are :Checking, Savings. Acct Type received =" + paramCCLocationInfo.getAccountType());
      return paramCCLocationInfo;
    }
    paramCCLocationInfo.setStatusCode(0);
    paramCCLocationInfo.setStatusMsg("Success");
    return paramCCLocationInfo;
  }
  
  private static CCCompanyAcctInfo jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    if (paramCCCompanyAcctInfo.getCompId() == null)
    {
      paramCCCompanyAcctInfo.setStatusCode(24180);
      paramCCCompanyAcctInfo.setStatusMsg("CompId is Null");
      return paramCCCompanyAcctInfo;
    }
    if (paramCCCompanyAcctInfo.getTransactionType() == null)
    {
      paramCCCompanyAcctInfo.setStatusCode(24410);
      paramCCCompanyAcctInfo.setStatusMsg("Transaction Type is Null");
      return paramCCCompanyAcctInfo;
    }
    CCCompanyInfo localCCCompanyInfo = new CCCompanyInfo();
    localCCCompanyInfo.setCompId(paramCCCompanyAcctInfo.getCompId());
    localCCCompanyInfo = getCCCompany(paramFFSConnectionHolder, localCCCompanyInfo);
    if (localCCCompanyInfo.getStatusCode() != 0)
    {
      paramCCCompanyAcctInfo.setStatusCode(24430);
      paramCCCompanyAcctInfo.setStatusMsg("This CC Company does not exist, CompId =" + paramCCCompanyAcctInfo.getCompId());
      return paramCCCompanyAcctInfo;
    }
    paramCCCompanyAcctInfo.setFIId(jdMethod_if(paramFFSConnectionHolder, localCCCompanyInfo));
    localCCCompanyInfo = null;
    if (!al(paramCCCompanyAcctInfo.getTransactionType()))
    {
      paramCCCompanyAcctInfo.setStatusCode(24440);
      paramCCCompanyAcctInfo.setStatusMsg("This transaction type is not valid. Valid types are CONCENTRATION,DISBURSEMENT. Transaction type received =" + paramCCCompanyAcctInfo.getTransactionType());
      return paramCCCompanyAcctInfo;
    }
    if ((paramCCCompanyAcctInfo.getAcctType().compareTo("Checking") != 0) && (paramCCCompanyAcctInfo.getAcctType().compareTo("Savings") != 0))
    {
      paramCCCompanyAcctInfo.setStatusCode(24760);
      paramCCCompanyAcctInfo.setStatusMsg("Account Type specified is not valid. Valid values are :Checking, Savings. Acct Type received =" + paramCCCompanyAcctInfo.getAcctType());
      return paramCCCompanyAcctInfo;
    }
    paramCCCompanyAcctInfo.setStatusCode(0);
    paramCCCompanyAcctInfo.setStatusMsg("Success");
    return paramCCCompanyAcctInfo;
  }
  
  private static CCCompanyCutOffInfo a(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    if (paramCCCompanyCutOffInfo.getCompId() == null)
    {
      paramCCCompanyCutOffInfo.setStatusCode(24180);
      paramCCCompanyCutOffInfo.setStatusMsg("CompId is Null");
      return paramCCCompanyCutOffInfo;
    }
    if (paramCCCompanyCutOffInfo.getCutOffId() == null)
    {
      paramCCCompanyCutOffInfo.setStatusCode(26004);
      paramCCCompanyCutOffInfo.setStatusMsg("CutOffId is null");
      return paramCCCompanyCutOffInfo;
    }
    if (paramCCCompanyCutOffInfo.getTransactionType() == null)
    {
      paramCCCompanyCutOffInfo.setStatusCode(24410);
      paramCCCompanyCutOffInfo.setStatusMsg("Transaction Type is Null");
      return paramCCCompanyCutOffInfo;
    }
    CCCompanyInfo localCCCompanyInfo = new CCCompanyInfo();
    localCCCompanyInfo.setCompId(paramCCCompanyCutOffInfo.getCompId());
    localCCCompanyInfo = getCCCompany(paramFFSConnectionHolder, localCCCompanyInfo);
    if (localCCCompanyInfo.getStatusCode() != 0)
    {
      paramCCCompanyCutOffInfo.setStatusCode(24430);
      paramCCCompanyCutOffInfo.setStatusMsg("This CC Company does not exist, CompId =" + paramCCCompanyCutOffInfo.getCompId());
      return paramCCCompanyCutOffInfo;
    }
    localCCCompanyInfo = null;
    CutOffInfo localCutOffInfo = new CutOffInfo();
    localCutOffInfo.setCutOffId(paramCCCompanyCutOffInfo.getCutOffId());
    localCutOffInfo = DBInstructionType.getCutOffById(paramFFSConnectionHolder, localCutOffInfo);
    if (localCutOffInfo.getStatusCode() != 0)
    {
      paramCCCompanyCutOffInfo.setStatusCode(24470);
      paramCCCompanyCutOffInfo.setStatusMsg("CC Company CutOff Id does not exist, CutOffId =" + paramCCCompanyCutOffInfo.getCutOffId());
      return paramCCCompanyCutOffInfo;
    }
    localCutOffInfo = null;
    if (!al(paramCCCompanyCutOffInfo.getTransactionType()))
    {
      paramCCCompanyCutOffInfo.setStatusCode(24480);
      paramCCCompanyCutOffInfo.setStatusMsg("This transaction type is not valid. Valid types are CONCENTRATION,DISBURSEMENT, Transaction type received =" + paramCCCompanyCutOffInfo.getTransactionType());
      return paramCCCompanyCutOffInfo;
    }
    paramCCCompanyCutOffInfo = jdMethod_if(paramFFSConnectionHolder, paramCCCompanyCutOffInfo);
    if (paramCCCompanyCutOffInfo.getStatusCode() == 0)
    {
      paramCCCompanyCutOffInfo.setStatusCode(24750);
      paramCCCompanyCutOffInfo.setStatusMsg("A CutOff exists for this Company With CompId =" + paramCCCompanyCutOffInfo.getCompId() + ", CutOffId =" + paramCCCompanyCutOffInfo.getCutOffId() + ", TransactionType =" + paramCCCompanyCutOffInfo.getTransactionType());
      return paramCCCompanyCutOffInfo;
    }
    paramCCCompanyCutOffInfo.setStatusCode(0);
    paramCCCompanyCutOffInfo.setStatusMsg("Success");
    return paramCCCompanyCutOffInfo;
  }
  
  public static CCEntryInfo validateCCEntryInfo(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    if (paramCCEntryInfo.getLocationId() == null)
    {
      paramCCEntryInfo.setStatusCode(24510);
      paramCCEntryInfo.setStatusMsg("Location Id is Null");
      return paramCCEntryInfo;
    }
    if (paramCCEntryInfo.getAmount() == null)
    {
      paramCCEntryInfo.setStatusCode(24520);
      paramCCEntryInfo.setStatusMsg("Amount is Null");
      return paramCCEntryInfo;
    }
    if (paramCCEntryInfo.getDueDate() == null)
    {
      paramCCEntryInfo.setStatusCode(24530);
      paramCCEntryInfo.setStatusMsg("DueDate is Null");
      return paramCCEntryInfo;
    }
    if (paramCCEntryInfo.getTransactionType() == null)
    {
      paramCCEntryInfo.setStatusCode(24410);
      paramCCEntryInfo.setStatusMsg("Transaction Type is Null");
      return paramCCEntryInfo;
    }
    if (paramCCEntryInfo.getSubmittedBy() == null)
    {
      paramCCEntryInfo.setStatusCode(24130);
      paramCCEntryInfo.setStatusMsg("SubmittedBy is Null");
      return paramCCEntryInfo;
    }
    if (paramCCEntryInfo.getLogId() == null)
    {
      paramCCEntryInfo.setStatusCode(24140);
      paramCCEntryInfo.setStatusMsg("LogId is Null");
      return paramCCEntryInfo;
    }
    String str1 = paramCCEntryInfo.getEntryCategory();
    if (str1 != null)
    {
      if ((str1.compareTo("USER_ENTRY") != 0) && (str1.compareTo("PRENOTE_ENTRY") != 0) && (str1.compareTo("ANTICIPATORY_ENTRY") != 0))
      {
        paramCCEntryInfo.setStatusCode(24650);
        paramCCEntryInfo.setStatusMsg("Invalid EntryCategory. Valid values are: USER_ENTRY, PRENOTE_ENTRY, ANTICIPATORY_ENTRY. Entry Category received =" + str1);
        return paramCCEntryInfo;
      }
      long l = Long.parseLong(paramCCEntryInfo.getAmount());
      if ((l < 0L) || ((l == 0L) && (str1.compareTo("PRENOTE_ENTRY") != 0)))
      {
        paramCCEntryInfo.setStatusCode(24720);
        paramCCEntryInfo.setStatusMsg("Amount is not a positive number: " + l);
        return paramCCEntryInfo;
      }
    }
    else
    {
      paramCCEntryInfo.setStatusCode(24730);
      paramCCEntryInfo.setStatusMsg("CC Entry Category is null: " + str1);
      return paramCCEntryInfo;
    }
    String str2 = paramCCEntryInfo.getStatus();
    if (str2 == null)
    {
      paramCCEntryInfo.setStatus("WILLPROCESSON");
    }
    else if ((str2.compareTo("WILLPROCESSON") == 0) || (str2.compareTo("POSTEDON") == 0) || (str2.compareTo("CANCELEDON") == 0) || (str2.compareTo("APPROVAL_REJECTED") == 0) || (str2.compareTo("LIMIT_CHECK_FAILED") == 0) || (str2.compareTo("APPROVAL_PENDING") == 0))
    {
      paramCCEntryInfo.setStatusCode(24740);
      paramCCEntryInfo.setStatusMsg("Invalid CC Entry status: " + paramCCEntryInfo.getStatus());
    }
    try
    {
      new SimpleDateFormat("yyyyMMdd").parse(paramCCEntryInfo.getDueDate());
    }
    catch (Exception localException1)
    {
      paramCCEntryInfo.setStatusCode(24540);
      paramCCEntryInfo.setStatusMsg("The format of Due Date is invalid, Correct Format =yyyyMMdd, Received date string =" + paramCCEntryInfo.getDueDate());
      return paramCCEntryInfo;
    }
    paramCCEntryInfo.setDueDate(String.valueOf(BPWUtil.getDateDBFormat(paramCCEntryInfo.getDueDate())));
    if (!al(paramCCEntryInfo.getTransactionType()))
    {
      paramCCEntryInfo.setStatusCode(24480);
      paramCCEntryInfo.setStatusMsg("This transaction type is not valid. Valid types are CONCENTRATION,DISBURSEMENT, Transaction type received =" + paramCCEntryInfo.getTransactionType());
      return paramCCEntryInfo;
    }
    CCLocationInfo localCCLocationInfo = new CCLocationInfo();
    localCCLocationInfo.setLocationId(paramCCEntryInfo.getLocationId());
    localCCLocationInfo = getCCLocation(paramFFSConnectionHolder, localCCLocationInfo);
    if (localCCLocationInfo.getStatusCode() != 0)
    {
      paramCCEntryInfo.setStatusCode(24550);
      paramCCEntryInfo.setStatusMsg("Location does not exist with LocationId =" + paramCCEntryInfo.getLocationId());
      return paramCCEntryInfo;
    }
    paramCCEntryInfo.setCompId(localCCLocationInfo.getCompId());
    if (paramCCEntryInfo.getTransactionType().compareTo("CONCENTRATION") == 0)
    {
      float f = 0.0F;
      try
      {
        f = Integer.parseInt(paramCCEntryInfo.getAmount());
      }
      catch (Exception localException2)
      {
        throw new FFSException(localException2, localException2.getMessage());
      }
      if ((localCCLocationInfo.getDepositMin() != null) && (f < Integer.parseInt(localCCLocationInfo.getDepositMin())))
      {
        paramCCEntryInfo.setStatusCode(24570);
        paramCCEntryInfo.setStatusMsg("Amount is less than the minimum amount permissible, amount received =" + BPWUtil.ACHAmountToString(paramCCEntryInfo.getAmount()) + ", minimum amount =" + BPWUtil.ACHAmountToString(localCCLocationInfo.getDepositMin()));
        return paramCCEntryInfo;
      }
      if ((localCCLocationInfo.getDepositMax() != null) && (f > Integer.parseInt(localCCLocationInfo.getDepositMax())))
      {
        paramCCEntryInfo.setStatusCode(24580);
        paramCCEntryInfo.setStatusMsg("Amount is greater than the maximum amount permissible, amount received =" + BPWUtil.ACHAmountToString(paramCCEntryInfo.getAmount()) + ", maximum amount =" + BPWUtil.ACHAmountToString(localCCLocationInfo.getDepositMax()));
        return paramCCEntryInfo;
      }
    }
    if (!validatePrenoteStatus(paramFFSConnectionHolder, localCCLocationInfo, paramCCEntryInfo)) {
      return paramCCEntryInfo;
    }
    CCCompanyAcctInfo localCCCompanyAcctInfo = new CCCompanyAcctInfo();
    localCCCompanyAcctInfo.setCompId(localCCLocationInfo.getCompId());
    localCCCompanyAcctInfo.setTransactionType(paramCCEntryInfo.getTransactionType());
    localCCCompanyAcctInfo = getCCCompanyAcctByCompId(paramFFSConnectionHolder, localCCCompanyAcctInfo);
    if (localCCCompanyAcctInfo.getStatusCode() != 0)
    {
      paramCCEntryInfo.setStatusCode(24591);
      paramCCEntryInfo.setStatusMsg("No account is configured for this Company, Transaction Type =" + paramCCEntryInfo.getTransactionType() + ", CompId =" + localCCLocationInfo.getCompId());
      return paramCCEntryInfo;
    }
    if (!k(paramFFSConnectionHolder, localCCLocationInfo.getCompId()))
    {
      paramCCEntryInfo.setStatusCode(24600);
      paramCCEntryInfo.setStatusMsg("No CutOff assigned to this Company CompId =" + localCCLocationInfo.getCompId());
      return paramCCEntryInfo;
    }
    CutOffInfo localCutOffInfo = getNextCashConCutOff(paramFFSConnectionHolder, localCCLocationInfo.getCompId(), paramCCEntryInfo.getTransactionType());
    if (localCutOffInfo.getStatusCode() != 0)
    {
      paramCCEntryInfo.setStatusCode(24660);
      paramCCEntryInfo.setStatusMsg("No CutOff found for this CompId and Transaction Type CompId =" + localCCLocationInfo.getCompId() + ", Transaction Type =" + paramCCEntryInfo.getTransactionType());
      return paramCCEntryInfo;
    }
    paramCCEntryInfo.setWillProcessTime(localCutOffInfo.getNextProcessTime());
    if (paramCCEntryInfo.getLastModifier() == null) {
      paramCCEntryInfo.setLastModifier(paramCCEntryInfo.getSubmittedBy());
    }
    paramCCEntryInfo.setStatusCode(0);
    paramCCEntryInfo.setStatusMsg("Success");
    return paramCCEntryInfo;
  }
  
  public static boolean validatePrenoteStatus(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    String str1 = "CashCon.validatePrenoteStatus: ";
    FFSDebug.log(str1 + "start", 6);
    String str2 = ACHAdapterUtil.getProperty("bpw.cashcon.enforce.prenote.period", "N");
    if (!str2.equalsIgnoreCase("Y"))
    {
      FFSDebug.log(str1 + " Prenote status not checked", 6);
      return true;
    }
    String str3 = paramCCEntryInfo.getEntryCategory();
    if ((str3 != null) && (str3.compareTo("PRENOTE_ENTRY") == 0)) {
      return true;
    }
    String str4 = null;
    String str5 = "";
    String str6 = null;
    int i = Integer.parseInt(paramCCEntryInfo.getDueDate()) / 100;
    boolean bool = true;
    try
    {
      if (paramCCEntryInfo.getTransactionType().compareTo("CONCENTRATION") == 0)
      {
        str4 = paramCCLocationInfo.getDepPrenoteStatus();
        str5 = paramCCLocationInfo.getDepositPrenote();
        str6 = paramCCLocationInfo.getDepPrenSubDate();
      }
      else
      {
        str4 = paramCCLocationInfo.getDisPrenoteStatus();
        str5 = paramCCLocationInfo.getDisbursePrenote();
        str6 = paramCCLocationInfo.getDisPrenSubDate();
      }
      FFSDebug.log(str1 + "Do prenote= " + str5 + " Prenote status= " + str4 + ", Prenote submit date= " + str6, 6);
      if ((str5 != null) && (str5.equalsIgnoreCase("Y")))
      {
        String str7 = getFIIdForThisEntry(paramFFSConnectionHolder, paramCCEntryInfo);
        int j;
        if (str6 == null)
        {
          Calendar localCalendar1 = Calendar.getInstance();
          Calendar localCalendar2 = Calendar.getInstance();
          localCalendar2.add(11, 23);
          localCalendar2.add(12, 59);
          String[] arrayOfString = { "CASHCONTRN" };
          Calendar[] arrayOfCalendar = DBUtil.getScheduleRunDates(paramFFSConnectionHolder, localCalendar1, localCalendar2, str7, arrayOfString);
          Calendar localCalendar3 = arrayOfCalendar[0];
          j = BPWUtil.calendarToDueDateFormatInt(localCalendar3);
        }
        else
        {
          j = Integer.parseInt(FFSUtil.convertDateFormat("yyyy/MM/dd HH:mm:ss", "yyyyMMdd", str6));
        }
        for (int k = 0; k < 6; k++) {
          j = SmartCalendar.getACHBusinessDay(str7, j, true);
        }
        FFSDebug.log(str1 + " Due date: " + i + ", Submit date: " + j, 6);
        if ((str4 == null) || (str4.trim().length() == 0))
        {
          if (j > i)
          {
            paramCCEntryInfo.setStatusCode(24630);
            paramCCEntryInfo.setStatusMsg("The Prenote for this location has not been processed yet, LocationId =" + paramCCLocationInfo.getLocationId());
            bool = false;
          }
        }
        else if (str4.compareTo("Success") != 0) {
          if (str4.compareTo("Pending") == 0)
          {
            if (j > i)
            {
              paramCCEntryInfo.setStatusCode(24770);
              paramCCEntryInfo.setStatusMsg("The Prenote for this location is Pending, LocationId =" + paramCCLocationInfo.getLocationId());
              bool = false;
            }
          }
          else if (str4.compareTo("Failed") == 0)
          {
            paramCCEntryInfo.setStatusCode(24640);
            paramCCEntryInfo.setStatusMsg("Prenote process has failed, LocationId =" + paramCCLocationInfo.getLocationId());
            bool = false;
          }
        }
      }
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(str1 + " failed." + localFFSException.toString());
      throw localFFSException;
    }
    catch (Exception localException)
    {
      FFSDebug.log(str1 + "failed." + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log(str1 + bool, 6);
    return bool;
  }
  
  private static boolean k(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    boolean bool = false;
    String str1 = "CashCon.isCutOffPresentForCompany :";
    FFSDebug.log(str1 + "start, compId =" + paramString, 6);
    String str2 = "SELECT count(CompId) from CC_CompanyCutOff Where CompId = ?";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      int i = 0;
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
      if (i > 0) {
        bool = true;
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
    FFSDebug.log(str1 + "end , cutOffPresent =" + bool, 6);
    return bool;
  }
  
  private static boolean al(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    return (paramString.compareTo("CONCENTRATION") == 0) || (paramString.compareTo("DISBURSEMENT") == 0);
  }
  
  public static boolean isTransactionPending(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    boolean bool = false;
    String str1 = "CashCon.isTransactionPending (Company) :";
    FFSDebug.log(str1 + "start, compId =" + paramCCCompanyInfo.getCompId(), 6);
    String str2 = "SELECT count(loc.LocationId) FROM CC_Entry entry, CC_Location loc  WHERE entry.LocationId = loc.LocationId AND loc.CompId = ?  AND entry.Status = 'WILLPROCESSON' AND loc.Status != 'CANCELEDON'";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramCCCompanyInfo.getCompId() };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      int i = 0;
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
      if (i > 0) {
        bool = true;
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
    FFSDebug.log(str1 + "end , isTransactionPending =" + bool, 6);
    return bool;
  }
  
  public static boolean isTransactionPending(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    boolean bool = false;
    String str1 = "CashCon.isTransactionPending (Location) :";
    FFSDebug.log(str1 + "start, LocationId =" + paramCCLocationInfo.getLocationId(), 6);
    String str2 = "SELECT count(loc.LocationId) FROM CC_Entry entry, CC_Location loc  WHERE entry.LocationId = loc.LocationId AND loc.LocationId = ?  AND entry.Status = 'WILLPROCESSON' AND loc.Status != 'CANCELEDON'";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramCCLocationInfo.getLocationId() };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      int i = 0;
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
      if (i > 0) {
        bool = true;
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
    FFSDebug.log(str1 + "end , isTransactionPending =" + bool, 6);
    return bool;
  }
  
  private static int a(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    String str1 = "CashCon.deleteCompanyCutOffByCompId :";
    FFSDebug.log(str1 + "start, CompId =" + paramCCCompanyInfo.getCompId(), 6);
    String str2 = "Delete from CC_CompanyCutOff  Where CompId = ?";
    int i = -1;
    Object[] arrayOfObject = { paramCCCompanyInfo.getCompId() };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "end, No of rows deleted =" + i, 6);
    return i;
  }
  
  public static int deleteCompanyCutOffByCutOffId(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    String str1 = "CashCon.deleteCompanyCutOffByCutOffId :";
    FFSDebug.log(str1 + "start, CutOffId =" + paramCutOffInfo.getCutOffId(), 6);
    String str2 = "Delete from CC_CompanyCutOff  Where CutOffId = ?";
    int i = -1;
    Object[] arrayOfObject = { paramCutOffInfo.getCutOffId() };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    paramCutOffInfo.setStatusCode(0);
    paramCutOffInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + "end, No of rows deleted =" + i, 6);
    return i;
  }
  
  private static CCLocationInfo a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    String str1 = "CashCon.getCCLocationByIdBankRTNAndAccountNumber :";
    String str2 = "cCLocationId =" + paramString1 + ", bankRTN =" + paramString2 + ", accountNum =" + paramString3 + " ";
    FFSDebug.log(str1 + "start, " + str2, 6);
    String str3 = "SELECT cc.LocationId, cc.LocationName, cc.CCLocationId, cc.CompId, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BankRtn, cc.BankName, cc.AccountNum, cc.AccountType, cc.DepositMin, cc.DepositMax, cc.AnticipatoryDepos, cc.ThresholdDeposAmt, cc.ConsolidateDepos, cc.DepositPrenote, cc.DepPrenSubDate, cc.DepPrenoteStatus, cc.DisbursePrenote, cc.DisPrenSubDate, cc.DisPrenoteStatus, cc.Memo, cc.Status, cc.LogId, cc.LastRequestTime, cc.SubmittedBy, cc.AgentId, cc.AgentType  FROM CC_Location cc  Where cc.CCLocationId = ? AND cc.BankRtn = ? AND cc.AccountNum = ? AND cc.Status !='CANCELEDON'";
    FFSResultSet localFFSResultSet = null;
    CCLocationInfo localCCLocationInfo = new CCLocationInfo();
    Object[] arrayOfObject = { paramString1, paramString2, paramString3 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localCCLocationInfo = a(localFFSResultSet, localCCLocationInfo);
      }
      else
      {
        localCCLocationInfo.setStatusCode(16020);
        localCCLocationInfo.setStatusMsg("CCLocationInfo with " + str2 + " record not found");
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, str5, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end, Status =" + localCCLocationInfo.getStatusMsg(), 6);
    return localCCLocationInfo;
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, long paramLong, CCEntryHistInfo paramCCEntryHistInfo)
    throws FFSException
  {
    if (paramCCEntryHistInfo.getCursorId() == null) {
      paramCCEntryHistInfo.setCursorId("0");
    }
    String str1 = "CashCon.getHistory :";
    FFSDebug.log(str1 + "start, cursorId =" + paramCCEntryHistInfo.getCursorId() + ", pageSize=" + paramLong, 6);
    int i = 0;
    EntitlementGroupMember localEntitlementGroupMember = null;
    if ((paramCCEntryHistInfo.getViewEntitlementCheck()) && (paramCCEntryHistInfo.getViewerId() != null)) {
      i = 1;
    }
    String str2 = paramCCEntryHistInfo.getHistId();
    if ((str2 == null) || (str2.equals("0"))) {
      jdMethod_if(paramCCEntryHistInfo);
    } else {
      a(paramCCEntryHistInfo);
    }
    String str3 = "FIId =" + paramCCEntryHistInfo.getFIId() + ", CustomerId =" + paramCCEntryHistInfo.getCustomerId() + ", CompId =" + paramCCEntryHistInfo.getCompId() + ", SubmittedBy = " + paramCCEntryHistInfo.getSubmittedBy() + ", Start Date =" + paramCCEntryHistInfo.getStartDate() + ", End Date =" + paramCCEntryHistInfo.getEndDate() + ", Ascending =" + paramCCEntryHistInfo.getAscending();
    if (paramCCEntryHistInfo.getLocationIdList() != null) {
      str3 = str3 + ", Location Ids =" + Arrays.asList(paramCCEntryHistInfo.getLocationIdList());
    }
    if (paramCCEntryHistInfo.getTransactionTypeList() != null) {
      str3 = str3 + ", Transaction Types =" + Arrays.asList(paramCCEntryHistInfo.getTransactionTypeList());
    }
    if (paramCCEntryHistInfo.getCategoryTypeList() != null) {
      str3 = str3 + ", Category Types =" + Arrays.asList(paramCCEntryHistInfo.getCategoryTypeList());
    }
    if (paramCCEntryHistInfo.getStatusList() != null) {
      str3 = str3 + ", Status List =" + Arrays.asList(paramCCEntryHistInfo.getStatusList());
    }
    FFSDebug.log(str1 + "start, " + str3, 6);
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList1 = new ArrayList();
    a(paramCCEntryHistInfo, localStringBuffer, localArrayList1);
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = "";
    if ((paramCCEntryHistInfo.getCursorId() != null) && (!paramCCEntryHistInfo.getCursorId().equals("0")))
    {
      str5 = paramCCEntryHistInfo.getCursorId().substring(0, 8);
      str6 = paramCCEntryHistInfo.getCursorId().substring(8 + kc);
    }
    if (paramCCEntryHistInfo.getAscending() == true)
    {
      str4 = " Order By DueDate ASC, EntryId ASC";
      str7 = " AND DueDate >= ? ";
    }
    else
    {
      str4 = " Order By DueDate DESC, EntryId DESC";
      str7 = " AND DueDate <= ? ";
    }
    DBUtil.appendToCondition(localStringBuffer, str5, str7, localArrayList1);
    localStringBuffer.append(str4);
    String str8 = localStringBuffer.toString();
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = (Object[])localArrayList1.toArray(new Object[0]);
    CCEntryInfo localCCEntryInfo = null;
    ArrayList localArrayList2 = new ArrayList();
    long l = 0L;
    List localList = null;
    if ((paramCCEntryHistInfo.getLocationIdList() != null) && (paramCCEntryHistInfo.getLocationIdList().length != 0)) {
      localList = Arrays.asList(paramCCEntryHistInfo.getLocationIdList());
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str8, arrayOfObject);
      int j = 1;
      while (localFFSResultSet.getNextRow())
      {
        localCCEntryInfo = new CCEntryInfo();
        localCCEntryInfo = a(localFFSResultSet, localCCEntryInfo);
        if ((localList == null) || (localList.contains(localCCEntryInfo.getLocationId())))
        {
          if ((j == 1) && (str5 != null) && (str6 != null)) {
            if (localCCEntryInfo.getDueDate().compareTo(str5) != 0)
            {
              j = 0;
            }
            else
            {
              if (localCCEntryInfo.getEntryId().compareTo(str6) < 1) {
                continue;
              }
              j = 0;
            }
          }
          if ((i == 0) || (LimitCheckApprovalProcessor.checkEntitlementCCEntryViewing(localCCEntryInfo, localEntitlementGroupMember, null)))
          {
            localArrayList2.add(localCCEntryInfo);
            l += 1L;
            if (l == paramLong) {
              break;
            }
          }
        }
      }
      int k = localArrayList2.size();
      if (k != 0) {
        paramCCEntryHistInfo.setCursorId(((CCEntryInfo)localArrayList2.get(k - 1)).getDueDate() + kd + ((CCEntryInfo)localArrayList2.get(k - 1)).getEntryId());
      }
      if (paramCCEntryHistInfo.getTotalTrans() == 0L)
      {
        FFSDebug.log(str1 + "TotalTrans() is 0", 6);
        FFSDebug.log("rows = " + l + ", pageSize = " + paramLong, 6);
        if ((paramLong == 0L) || (l < paramLong)) {
          a(paramCCEntryHistInfo, l);
        } else {
          a(paramCCEntryHistInfo, a(l, localFFSResultSet, localList));
        }
      }
    }
    catch (FFSException localFFSException)
    {
      str9 = "*** " + str1 + " failed: ";
      str10 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str9, str10, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str9 = "*** " + str1 + " failed: ";
      String str10 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str9, str10, 0);
      throw new FFSException(localException, str9);
    }
    finally
    {
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
    }
    paramCCEntryHistInfo.setCCEntryInfoList((CCEntryInfo[])localArrayList2.toArray(new CCEntryInfo[0]));
    FFSDebug.log(str1 + "rows =" + localArrayList2.size() + ", TotalTrans =" + paramCCEntryHistInfo.getTotalTrans(), 6);
    FFSDebug.log(str1 + "end, histId =" + str2 + ", cursorId in histInfo =" + paramCCEntryHistInfo.getCursorId() + ", pageSize=" + paramLong, 6);
  }
  
  private static boolean an(String paramString)
  {
    boolean bool = true;
    try
    {
      bool = BPWUtil.validateABA(paramString);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log("Invalid BankRTN. Exception: " + localFFSException.toString(), 0);
      bool = false;
    }
    return bool;
  }
  
  private static boolean am(String paramString)
  {
    boolean bool = true;
    try
    {
      bool = BPWUtil.validatePayAcct(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Invalid Bank Acct Num, Length should be less than 22 . Exception: " + localException.toString(), 0);
      bool = false;
    }
    return bool;
  }
  
  private static CCCompanyInfo a(FFSResultSet paramFFSResultSet, CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    paramCCCompanyInfo.setCompId(paramFFSResultSet.getColumnString("CompId"));
    paramCCCompanyInfo.setCustomerId(paramFFSResultSet.getColumnString("CustomerId"));
    paramCCCompanyInfo.setCCCompId(paramFFSResultSet.getColumnString("CCCompId"));
    paramCCCompanyInfo.setCompName(paramFFSResultSet.getColumnString("CompName"));
    paramCCCompanyInfo.setConcentrateAcctId(paramFFSResultSet.getColumnString("ConcentrateAcctId"));
    paramCCCompanyInfo.setDisburseAcctId(paramFFSResultSet.getColumnString("DisburseAcctId"));
    paramCCCompanyInfo.setBatchType(paramFFSResultSet.getColumnString("BatchType"));
    paramCCCompanyInfo.setStatus(paramFFSResultSet.getColumnString("Status"));
    paramCCCompanyInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    paramCCCompanyInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    paramCCCompanyInfo.setStatusCode(0);
    paramCCCompanyInfo.setStatusMsg("Success");
    return paramCCCompanyInfo;
  }
  
  private static CCCompanyAcctInfo a(FFSConnectionHolder paramFFSConnectionHolder, FFSResultSet paramFFSResultSet, CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    paramCCCompanyAcctInfo.setAcctId(paramFFSResultSet.getColumnString("AcctId"));
    paramCCCompanyAcctInfo.setCompId(paramFFSResultSet.getColumnString("CompId"));
    paramCCCompanyAcctInfo.setTransactionType(paramFFSResultSet.getColumnString("TransactionType"));
    BPWBankAcctInfo localBPWBankAcctInfo = BPWBankAcct.getBPWBankAcctInfoById(paramFFSConnectionHolder, paramCCCompanyAcctInfo.getAcctId());
    a(paramCCCompanyAcctInfo, localBPWBankAcctInfo);
    return paramCCCompanyAcctInfo;
  }
  
  private static CCLocationInfo a(FFSResultSet paramFFSResultSet, CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    paramCCLocationInfo.setLocationId(paramFFSResultSet.getColumnString("LocationId"));
    paramCCLocationInfo.setLocationName(paramFFSResultSet.getColumnString("LocationName"));
    paramCCLocationInfo.setCCLocationId(paramFFSResultSet.getColumnString("CCLocationId"));
    paramCCLocationInfo.setCompId(paramFFSResultSet.getColumnString("CompId"));
    paramCCLocationInfo.setConcentrateAcctId(paramFFSResultSet.getColumnString("ConcentrateAcctId"));
    paramCCLocationInfo.setDisburseAcctId(paramFFSResultSet.getColumnString("DisburseAcctId"));
    paramCCLocationInfo.setBankRtn(paramFFSResultSet.getColumnString("BankRtn"));
    paramCCLocationInfo.setBankName(paramFFSResultSet.getColumnString("BankName"));
    paramCCLocationInfo.setAccountNum(paramFFSResultSet.getColumnString("AccountNum"));
    paramCCLocationInfo.setAccountType(paramFFSResultSet.getColumnString("AccountType"));
    paramCCLocationInfo.setDepositMin(paramFFSResultSet.getColumnString("DepositMin"));
    paramCCLocationInfo.setDepositMax(paramFFSResultSet.getColumnString("DepositMax"));
    paramCCLocationInfo.setAnticipatoryDepos(paramFFSResultSet.getColumnString("AnticipatoryDepos"));
    paramCCLocationInfo.setThresholdDeposAmt(paramFFSResultSet.getColumnString("ThresholdDeposAmt"));
    paramCCLocationInfo.setConsolidateDepos(paramFFSResultSet.getColumnString("ConsolidateDepos"));
    paramCCLocationInfo.setDepositPrenote(paramFFSResultSet.getColumnString("DepositPrenote"));
    paramCCLocationInfo.setDepPrenSubDate(paramFFSResultSet.getColumnString("DepPrenSubDate"));
    paramCCLocationInfo.setDepPrenoteStatus(paramFFSResultSet.getColumnString("DepPrenoteStatus"));
    paramCCLocationInfo.setDisbursePrenote(paramFFSResultSet.getColumnString("DisbursePrenote"));
    paramCCLocationInfo.setDisPrenSubDate(paramFFSResultSet.getColumnString("DisPrenSubDate"));
    paramCCLocationInfo.setDisPrenoteStatus(paramFFSResultSet.getColumnString("DisPrenoteStatus"));
    paramCCLocationInfo.setMemo(paramFFSResultSet.getColumnString("Memo"));
    paramCCLocationInfo.setStatus(paramFFSResultSet.getColumnString("Status"));
    paramCCLocationInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    paramCCLocationInfo.setLastRequestTime(paramFFSResultSet.getColumnString("LastRequestTime"));
    paramCCLocationInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    paramCCLocationInfo.setAgentId(paramFFSResultSet.getColumnString("AgentId"));
    paramCCLocationInfo.setAgentType(paramFFSResultSet.getColumnString("AgentType"));
    paramCCLocationInfo.setStatusCode(0);
    paramCCLocationInfo.setStatusMsg("Success");
    return paramCCLocationInfo;
  }
  
  private static CutOffInfo a(FFSResultSet paramFFSResultSet, CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    paramCutOffInfo.setCutOffId(paramFFSResultSet.getColumnString("CutOffId"));
    paramCutOffInfo.setFIId(paramFFSResultSet.getColumnString("FIID"));
    paramCutOffInfo.setInstructionType(paramFFSResultSet.getColumnString("InstructionType"));
    paramCutOffInfo.setFrequency(paramFFSResultSet.getColumnInt("Frequency"));
    paramCutOffInfo.setDay(paramFFSResultSet.getColumnInt("Day"));
    paramCutOffInfo.setProcessTime(paramFFSResultSet.getColumnString("ProcessTime"));
    paramCutOffInfo.setExtension(paramFFSResultSet.getColumnString("Extension"));
    paramCutOffInfo.setNextProcessTime(paramFFSResultSet.getColumnString("NextProcessTime"));
    paramCutOffInfo.setLastProcessTime(paramFFSResultSet.getColumnString("LastProcessTime"));
    paramCutOffInfo.setStatus(paramFFSResultSet.getColumnString("Status"));
    paramCutOffInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    paramCutOffInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    paramCutOffInfo.setMemo(paramFFSResultSet.getColumnString("Memo"));
    paramCutOffInfo.setStatusCode(0);
    paramCutOffInfo.setStatusMsg("Success");
    return paramCutOffInfo;
  }
  
  private static CCEntryInfo a(FFSResultSet paramFFSResultSet, CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    return a(paramFFSResultSet, paramCCEntryInfo, true);
  }
  
  private static CCEntryInfo a(FFSResultSet paramFFSResultSet, CCEntryInfo paramCCEntryInfo, boolean paramBoolean)
    throws FFSException
  {
    paramCCEntryInfo.setEntryId(paramFFSResultSet.getColumnString("EntryId"));
    paramCCEntryInfo.setLocationId(paramFFSResultSet.getColumnString("LocationId"));
    paramCCEntryInfo.setAmount(paramFFSResultSet.getColumnString("Amount"));
    if (paramBoolean == true) {
      paramCCEntryInfo.setCompId(paramFFSResultSet.getColumnString("CompId"));
    }
    paramCCEntryInfo.setDueDate(BPWUtil.getDateBeanFormat(paramFFSResultSet.getColumnInt("DueDate")));
    paramCCEntryInfo.setWillProcessTime(paramFFSResultSet.getColumnString("WillProcessTime"));
    paramCCEntryInfo.setProcessedTime(paramFFSResultSet.getColumnString("ProcessedTime"));
    paramCCEntryInfo.setTransactionType(paramFFSResultSet.getColumnString("TransactionType"));
    paramCCEntryInfo.setMemo(paramFFSResultSet.getColumnString("Memo"));
    paramCCEntryInfo.setCreatedDate(paramFFSResultSet.getColumnString("CreatedDate"));
    paramCCEntryInfo.setEntryCategory(paramFFSResultSet.getColumnString("EntryCategory"));
    paramCCEntryInfo.setStatus(paramFFSResultSet.getColumnString("Status"));
    paramCCEntryInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    paramCCEntryInfo.setLastProcessId(paramFFSResultSet.getColumnString("LastProcessId"));
    paramCCEntryInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    paramCCEntryInfo.setLastModifier(paramFFSResultSet.getColumnString("LastModifier"));
    paramCCEntryInfo.setStatusCode(0);
    paramCCEntryInfo.setStatusMsg("Success");
    return paramCCEntryInfo;
  }
  
  private static CCCompanyCutOffInfo a(FFSResultSet paramFFSResultSet, CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    paramCCCompanyCutOffInfo.setCompCutOffId(paramFFSResultSet.getColumnString("CompCutOffId"));
    paramCCCompanyCutOffInfo.setCutOffId(paramFFSResultSet.getColumnString("CutOffId"));
    paramCCCompanyCutOffInfo.setCompId(paramFFSResultSet.getColumnString("CompId"));
    paramCCCompanyCutOffInfo.setTransactionType(paramFFSResultSet.getColumnString("TransactionType"));
    paramCCCompanyCutOffInfo.setStatusCode(0);
    paramCCCompanyCutOffInfo.setStatusMsg("Success");
    return paramCCCompanyCutOffInfo;
  }
  
  public static CCEntryInfo[] getUnprocessedEntries(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    String str1 = "CashCon.getUnprocessedEntries :";
    String str2 = "locationId =" + paramString1 + ", nextProcessTime =" + paramString2 + ", lastProcessId is null " + ", transType = " + paramString3;
    FFSDebug.log(str1 + "start " + str2, 6);
    String str3 = "SELECT a.EntryId, a.LocationId, a.Amount, a.DueDate, a.WillProcessTime,a.ProcessedTime, a.TransactionType, a.Memo, a.CreatedDate, a.EntryCategory,a.Status, a.SubmittedBy, a.LastProcessId, a.LogId, a.LastModifier  From CC_Entry a WHERE a.LocationId = ? AND a.Status ='WILLPROCESSON' AND a.LastProcessId is null AND a.TransactionType = ? AND  a.DueDate <= ? ";
    FFSResultSet localFFSResultSet = null;
    CCEntryInfo localCCEntryInfo = null;
    ArrayList localArrayList = new ArrayList();
    Object[] arrayOfObject = { paramString1, paramString3, paramString2 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localCCEntryInfo = new CCEntryInfo();
        localCCEntryInfo = a(localFFSResultSet, localCCEntryInfo, false);
        localArrayList.add(localCCEntryInfo);
      }
      if (localArrayList.size() == 0)
      {
        CCEntryInfo[] arrayOfCCEntryInfo1 = null;
        return arrayOfCCEntryInfo1;
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, str5, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    CCEntryInfo[] arrayOfCCEntryInfo2 = (CCEntryInfo[])localArrayList.toArray(new CCEntryInfo[0]);
    FFSDebug.log(str1 + "end, status =" + arrayOfCCEntryInfo2[0].getStatusMsg(), 6);
    return arrayOfCCEntryInfo2;
  }
  
  public static CCEntryInfo[] getProcessedEntries(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    String str1 = "CashCon.getProcessedEntries :";
    String str2 = "locationId =" + paramString1 + ", nextProcessTime =" + paramString2 + ", lastProcessId =" + paramString3 + ", transtype = " + paramString4;
    FFSDebug.log(str1 + "start " + str2, 6);
    String str3 = "SELECT a.EntryId, a.LocationId, a.Amount, a.DueDate, a.WillProcessTime,a.ProcessedTime, a.TransactionType, a.Memo, a.CreatedDate, a.EntryCategory,a.Status, a.SubmittedBy, a.LastProcessId, a.LogId, a.LastModifier  From CC_Entry a WHERE  a.Status ='POSTEDON' AND a.LocationId = ? AND a.DueDate <= ? AND a.LastProcessId = ? AND a.TransactionType = ? ";
    FFSResultSet localFFSResultSet = null;
    CCEntryInfo localCCEntryInfo = null;
    ArrayList localArrayList = new ArrayList();
    Object[] arrayOfObject = { paramString1, paramString2, paramString3, paramString4 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localCCEntryInfo = new CCEntryInfo();
        localCCEntryInfo = a(localFFSResultSet, localCCEntryInfo, false);
        localArrayList.add(localCCEntryInfo);
      }
      if (localArrayList.size() == 0)
      {
        CCEntryInfo[] arrayOfCCEntryInfo1 = null;
        return arrayOfCCEntryInfo1;
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, str5, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    CCEntryInfo[] arrayOfCCEntryInfo2 = (CCEntryInfo[])localArrayList.toArray(new CCEntryInfo[0]);
    FFSDebug.log(str1 + "end, status =" + arrayOfCCEntryInfo2[0].getStatusMsg(), 6);
    return arrayOfCCEntryInfo2;
  }
  
  private static boolean a(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    String str1 = "CashCon.checkActiveLocationForCompAcct :";
    FFSDebug.log(str1 + "start, AcctId =" + paramCCCompanyAcctInfo.getAcctId(), 6);
    String str2 = "SELECT count(a.LocationId) From CC_Location a, CC_CompanyAcct b  Where b.CompId = a.CompId AND b.AcctId = ?  AND (b.AcctId = a.ConcentrateAcctId OR b.AcctId = a.DisburseAcctId )  AND a.Status != 'CANCELEDON'";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramCCCompanyAcctInfo.getAcctId() };
    int i = 0;
    boolean bool = false;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
      bool = i > 0;
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
    FFSDebug.log(str1 + "end, activeLocPresent =" + bool, 6);
    return bool;
  }
  
  public static CCBatchHistInfo addCCBatchHistInfo(FFSConnectionHolder paramFFSConnectionHolder, CCBatchHistInfo paramCCBatchHistInfo)
    throws FFSException
  {
    String str1 = "CashCon.addCCBatchHistInfo: ";
    FFSDebug.log(str1 + "start. ", 6);
    try
    {
      if (!a(paramFFSConnectionHolder, paramCCBatchHistInfo))
      {
        FFSDebug.log(str1 + "failed to validate CCBatchHistInfo " + paramCCBatchHistInfo.getStatusMsg(), 6);
        return paramCCBatchHistInfo;
      }
      String str2 = DBUtil.getNextIndexStringWithPadding("CCBatchHistId", 32, '0');
      str3 = "INSERT INTO CC_BatchHist (BatchHistId, CompId, ProcessId, EffectEntryDate,BatchNumber, NumOfDeposit, NumOfDisburse, TotalCreditAmt, TotalDebitAmt, TransactionType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      localObject = new Object[] { str2, paramCCBatchHistInfo.getCompId(), paramCCBatchHistInfo.getProcessId(), paramCCBatchHistInfo.getEffectiveEntryDate(), paramCCBatchHistInfo.getBatchNumber(), new Integer(paramCCBatchHistInfo.getNumberOfDeposits()), new Integer(paramCCBatchHistInfo.getNumberOfDisburses()), paramCCBatchHistInfo.getTotalCreditAmount(), paramCCBatchHistInfo.getTotalDebitAmount(), paramCCBatchHistInfo.getTransactionType() };
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, (Object[])localObject);
      paramCCBatchHistInfo.setStatusCode(0);
      paramCCBatchHistInfo.setStatusMsg("Success");
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      localObject = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, (String)localObject, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      Object localObject = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, (String)localObject, 0);
      throw new FFSException(localException, str3);
    }
    return paramCCBatchHistInfo;
  }
  
  private static boolean a(FFSConnectionHolder paramFFSConnectionHolder, CCBatchHistInfo paramCCBatchHistInfo)
    throws FFSException
  {
    if (paramCCBatchHistInfo == null) {
      throw new FFSException(26018, "CCBatchHist is null");
    }
    if (paramCCBatchHistInfo.getBatchHistId() != null)
    {
      paramCCBatchHistInfo.setStatusCode(26019);
      paramCCBatchHistInfo.setStatusMsg("CCBatchHistId is not null");
      return false;
    }
    String str = paramCCBatchHistInfo.getCompId();
    if (str != null)
    {
      CCCompanyInfo localCCCompanyInfo = new CCCompanyInfo();
      localCCCompanyInfo.setCompId(str);
      localCCCompanyInfo = getCCCompany(paramFFSConnectionHolder, localCCCompanyInfo);
      if (localCCCompanyInfo.getStatusCode() != 0)
      {
        paramCCBatchHistInfo.setStatusCode(localCCCompanyInfo.getStatusCode());
        paramCCBatchHistInfo.setStatusMsg(localCCCompanyInfo.getStatusMsg());
        return false;
      }
    }
    if (paramCCBatchHistInfo.getProcessId() == null)
    {
      paramCCBatchHistInfo.setStatusCode(26020);
      paramCCBatchHistInfo.setStatusMsg("ProcessId is null");
      return false;
    }
    return true;
  }
  
  public static void updateCCEntryInfoFromAdapter(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    String str1 = "CashCon.updateCCEntryInfoFromAdapter: ";
    FFSDebug.log(str1 + "start, EntryId =" + paramCCEntryInfo.getEntryId() + ", LastProcessId =" + paramCCEntryInfo.getLastProcessId() + ", ProcessedTime =" + paramCCEntryInfo.getProcessedTime() + ", Status =" + paramCCEntryInfo.getStatus(), 6);
    if (paramCCEntryInfo.getEntryId() == null)
    {
      paramCCEntryInfo.setStatusCode(24610);
      paramCCEntryInfo.setStatusMsg("CC Entry Id cannot be Null");
      FFSDebug.log(str1 + "end. Error =" + paramCCEntryInfo.getStatusMsg(), 6);
      return;
    }
    if (paramCCEntryInfo.getLastProcessId() == null)
    {
      paramCCEntryInfo.setStatusCode(24670);
      paramCCEntryInfo.setStatusMsg("LastProcessedId is Null");
      FFSDebug.log(str1 + "end. Error =" + paramCCEntryInfo.getStatusMsg(), 6);
      return;
    }
    if (paramCCEntryInfo.getProcessedTime() == null)
    {
      paramCCEntryInfo.setStatusCode(24680);
      paramCCEntryInfo.setStatusMsg("ProcessedTime is Null");
      FFSDebug.log(str1 + "end. Error =" + paramCCEntryInfo.getStatusMsg(), 6);
      return;
    }
    if (paramCCEntryInfo.getStatus() == null)
    {
      paramCCEntryInfo.setStatusCode(24690);
      paramCCEntryInfo.setStatusMsg("Entry Status is Null");
      FFSDebug.log(str1 + "end. Error =" + paramCCEntryInfo.getStatusMsg(), 6);
      return;
    }
    String str2 = "UPDATE CC_Entry set LastProcessId = ?, ProcessedTime = ?, Status = ?  Where EntryId = ? AND Status != 'CANCELEDON'";
    int i = -1;
    Object[] arrayOfObject = { paramCCEntryInfo.getLastProcessId(), paramCCEntryInfo.getProcessedTime(), paramCCEntryInfo.getStatus(), paramCCEntryInfo.getEntryId() };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (i == 0)
      {
        paramCCEntryInfo.setStatusCode(16020);
        paramCCEntryInfo.setStatusMsg("CCEntryInfo with EntryId" + paramCCEntryInfo.getEntryId() + " record not found");
      }
      else
      {
        paramCCEntryInfo.setStatusCode(0);
        paramCCEntryInfo.setStatusMsg("Success");
      }
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "end, No of rows updated =" + i + ", status =" + paramCCEntryInfo.getStatusMsg(), 6);
  }
  
  public static void updateDepositPrenoteOfCCLocation(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    String str1 = "CashCon.updateDepositPrenoteOfCCLocation: ";
    FFSDebug.log(str1 + "start DepPrenoteStatus =" + paramCCLocationInfo.getDepPrenoteStatus() + ", DepPrenSubDate =" + paramCCLocationInfo.getDepPrenSubDate(), 6);
    if (paramCCLocationInfo.getLocationId() == null)
    {
      paramCCLocationInfo.setStatusCode(24360);
      paramCCLocationInfo.setStatusMsg("Location Id cannot be Null");
      FFSDebug.log(str1 + "end. Error =" + paramCCLocationInfo.getStatusMsg(), 6);
      return;
    }
    Object[] arrayOfObject = null;
    String str2 = "UPDATE CC_Location set DepPrenoteStatus = ? ";
    if (paramCCLocationInfo.getDepPrenSubDate() != null)
    {
      str2 = str2 + ", DepPrenSubDate = ? ";
      arrayOfObject = new Object[3];
      arrayOfObject[1] = paramCCLocationInfo.getDepPrenSubDate();
      arrayOfObject[2] = paramCCLocationInfo.getLocationId();
    }
    else
    {
      arrayOfObject = new Object[2];
      arrayOfObject[1] = paramCCLocationInfo.getLocationId();
    }
    arrayOfObject[0] = paramCCLocationInfo.getDepPrenoteStatus();
    str2 = str2 + " Where LocationId = ?";
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (i == 0)
      {
        paramCCLocationInfo.setStatusCode(16020);
        paramCCLocationInfo.setStatusMsg("CCLocationInfo with LocationId" + paramCCLocationInfo.getLocationId() + " record not found");
      }
      else
      {
        paramCCLocationInfo.setStatusCode(0);
        paramCCLocationInfo.setStatusMsg("Success");
      }
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "end, No of rows updated =" + i + ", status =" + paramCCLocationInfo.getStatusMsg(), 6);
  }
  
  public static void updateDisbursePrenoteOfCCLocation(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    String str1 = "CashCon.updateDisbursePrenoteOfCCLocation: ";
    FFSDebug.log(str1 + "start DisPrenoteStatus =" + paramCCLocationInfo.getDisPrenoteStatus() + ", DisPrenSubDate =" + paramCCLocationInfo.getDisPrenSubDate(), 6);
    if (paramCCLocationInfo.getLocationId() == null)
    {
      paramCCLocationInfo.setStatusCode(24360);
      paramCCLocationInfo.setStatusMsg("Location Id cannot be Null");
      FFSDebug.log(str1 + "end. Error =" + paramCCLocationInfo.getStatusMsg(), 6);
      return;
    }
    String str2 = "UPDATE CC_Location set DisPrenoteStatus = ? ";
    Object[] arrayOfObject = null;
    if (paramCCLocationInfo.getDisPrenSubDate() != null)
    {
      str2 = str2 + ", DisPrenSubDate = ? ";
      arrayOfObject = new Object[3];
      arrayOfObject[1] = paramCCLocationInfo.getDisPrenSubDate();
      arrayOfObject[2] = paramCCLocationInfo.getLocationId();
    }
    else
    {
      arrayOfObject = new Object[2];
      arrayOfObject[1] = paramCCLocationInfo.getLocationId();
    }
    arrayOfObject[0] = paramCCLocationInfo.getDisPrenoteStatus();
    str2 = str2 + " Where LocationId = ?";
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (i == 0)
      {
        paramCCLocationInfo.setStatusCode(16020);
        paramCCLocationInfo.setStatusMsg("CCLocationInfo with LocationId" + paramCCLocationInfo.getLocationId() + " record not found");
      }
      else
      {
        paramCCLocationInfo.setStatusCode(0);
        paramCCLocationInfo.setStatusMsg("Success");
      }
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "end, No of rows updated =" + i + ", status =" + paramCCLocationInfo.getStatusMsg(), 6);
  }
  
  public static void updateLastRequestTimeOfCCLocation(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    String str1 = "CashCon.updateLastRequestTimeOfCCLocation: ";
    FFSDebug.log(str1 + "start LastRequestTime =" + paramCCLocationInfo.getLastRequestTime(), 6);
    if (paramCCLocationInfo.getLocationId() == null)
    {
      paramCCLocationInfo.setStatusCode(24360);
      paramCCLocationInfo.setStatusMsg("Location Id cannot be Null");
      FFSDebug.log(str1 + "end. Error =" + paramCCLocationInfo.getStatusMsg(), 6);
      return;
    }
    String str2 = "UPDATE CC_Location set LastRequestTime = ? Where LocationId =? ";
    paramCCLocationInfo.setLastRequestTime(FFSUtil.getDateString("yyyy/MM/dd HH:mm:ss"));
    int i = -1;
    Object[] arrayOfObject = { paramCCLocationInfo.getLastRequestTime(), paramCCLocationInfo.getLocationId() };
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (i == 0)
      {
        paramCCLocationInfo.setStatusCode(16020);
        paramCCLocationInfo.setStatusMsg("CCLocationInfo with LocationId" + paramCCLocationInfo.getLocationId() + " record not found");
      }
      else
      {
        paramCCLocationInfo.setStatusCode(0);
        paramCCLocationInfo.setStatusMsg("Success");
      }
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "end, No of rows updated =" + i + ", status =" + paramCCLocationInfo.getStatusMsg(), 6);
  }
  
  public static void logCCompanyTransAuditLog(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyInfo paramCCCompanyInfo, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "CashCon.logCCompanyTransAuditLog: ";
    FFSDebug.log(str1 + "start ...", 6);
    String str2 = paramCCCompanyInfo.getCustomerId();
    BigDecimal localBigDecimal = new BigDecimal(0.0D);
    String str3 = paramString + ", CCCompanyID = " + paramCCCompanyInfo.getCCCompId() + ", CCCompanyName = " + paramCCCompanyInfo.getCompName();
    FFSDebug.log(str1 + str3, 6);
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramCCCompanyInfo.getSubmittedBy(), paramCCCompanyInfo.getAgentId(), paramCCCompanyInfo.getAgentType(), str3, paramCCCompanyInfo.getLogId(), paramInt, Integer.parseInt(str2), localBigDecimal, null, paramCCCompanyInfo.getCompId(), paramCCCompanyInfo.getStatus(), null, null, null, null, 0);
    TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    FFSDebug.log(str1 + "done.", 6);
  }
  
  public static void logCCLocationTransAuditLog(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "CashCon.logCCLocationTransAuditLog: ";
    FFSDebug.log(str1 + "start ...", 6);
    CCCompanyInfo localCCCompanyInfo = a(paramFFSConnectionHolder, paramCCLocationInfo);
    if (localCCCompanyInfo.getStatusCode() != 0) {
      throw new FFSException(localCCCompanyInfo.getStatusCode(), localCCCompanyInfo.getStatusMsg());
    }
    String str2 = localCCCompanyInfo.getCustomerId();
    BigDecimal localBigDecimal = new BigDecimal(0.0D);
    String str3 = paramString + ", CClocationID = " + paramCCLocationInfo.getCCLocationId() + ", CCLocationName = " + paramCCLocationInfo.getLocationName();
    FFSDebug.log(str1 + str3, 6);
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramCCLocationInfo.getSubmittedBy(), paramCCLocationInfo.getAgentId(), paramCCLocationInfo.getAgentType(), str3, paramCCLocationInfo.getLogId(), paramInt, Integer.parseInt(str2), localBigDecimal, null, paramCCLocationInfo.getLocationId(), paramCCLocationInfo.getStatus(), null, null, null, null, 0);
    TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    FFSDebug.log(str1 + "done.", 6);
  }
  
  public static void logCCEntryTransAuditLog(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "CashCon.logCCEntryTransAuditLog: ";
    FFSDebug.log(str1 + "start ...", 6);
    CCCompanyInfo localCCCompanyInfo = getCCCompanyForEntry(paramFFSConnectionHolder, paramCCEntryInfo);
    String str2 = localCCCompanyInfo.getCustomerId();
    BigDecimal localBigDecimal = FFSUtil.getBigDecimal(paramCCEntryInfo.getAmount(), 2).movePointLeft(2);
    localBigDecimal = FFSUtil.setScale(localBigDecimal, 2);
    String str3 = paramString + ", TransactionType = " + paramCCEntryInfo.getTransactionType() + ", Amount = " + localBigDecimal.toString();
    FFSDebug.log(str1 + str3, 6);
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramCCEntryInfo.getSubmittedBy(), paramCCEntryInfo.getAgentId(), paramCCEntryInfo.getAgentType(), str3, paramCCEntryInfo.getLogId(), paramInt, Integer.parseInt(str2), localBigDecimal, null, paramCCEntryInfo.getEntryId(), paramCCEntryInfo.getStatus(), null, null, null, null, 0);
    TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    FFSDebug.log(str1 + "done.", 6);
  }
  
  private static CCCompanyInfo a(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo)
    throws FFSException
  {
    CCCompanyInfo localCCCompanyInfo = new CCCompanyInfo();
    localCCCompanyInfo.setCompId(paramCCLocationInfo.getCompId());
    localCCCompanyInfo = getCCCompany(paramFFSConnectionHolder, localCCCompanyInfo);
    return localCCCompanyInfo;
  }
  
  public static CCCompanyInfo getCCCompanyForEntry(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    CCLocationInfo localCCLocationInfo = new CCLocationInfo();
    localCCLocationInfo.setLocationId(paramCCEntryInfo.getLocationId());
    localCCLocationInfo = getCCLocation(paramFFSConnectionHolder, localCCLocationInfo);
    if (localCCLocationInfo.getStatusCode() != 0) {
      throw new FFSException(localCCLocationInfo.getStatusCode(), localCCLocationInfo.getStatusMsg());
    }
    return a(paramFFSConnectionHolder, localCCLocationInfo);
  }
  
  private static String a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    String str1 = "CashCon.existCCCompAcctByCompIdBankRTNBankAcctNum";
    FFSDebug.log(str1 + " start: comp ID = " + paramString1, 6);
    FFSDebug.log(str1 + " start: bankRTN = " + paramString2, 6);
    FFSDebug.log(str1 + " start: bankAcctNum = " + paramString3, 6);
    FFSDebug.log(str1 + " start: transType = " + paramString4, 6);
    FFSResultSet localFFSResultSet = null;
    String str2 = null;
    try
    {
      String str3 = "SELECT a.AcctId FROM BPW_BankAcct a, CC_CompanyAcct b  WHERE b.CompId = ? AND a.BankRTN = ? AND a.AcctNumber = ? AND b.TransactionType = ? AND a.AcctId = b.AcctId  AND a.AcctStatus != 'CLOSED' ";
      localObject1 = new Object[] { paramString1, paramString2, paramString3, paramString4 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, (Object[])localObject1);
      if (localFFSResultSet.getNextRow()) {
        str2 = localFFSResultSet.getColumnString("AcctId");
      }
    }
    catch (Exception localException1)
    {
      Object localObject1 = str1 + ": failed: ";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, (String)localObject1 + str4);
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
    FFSDebug.log(str1 + " end. CompAcctId: " + str2, 6);
    return str2;
  }
  
  private static String jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    CustomerInfo localCustomerInfo = Customer.getCustomerInfo(paramCCCompanyInfo.getCustomerId(), paramFFSConnectionHolder, paramCCCompanyInfo);
    if (localCustomerInfo == null)
    {
      String str = BPWLocaleUtil.getMessage(19130, new String[] { paramCCCompanyInfo.getCustomerId() }, "TRANSFER_MESSAGE");
      throw new FFSException(19130, str);
    }
    return localCustomerInfo.fIID;
  }
  
  public static int cleanup(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "CashCon.cleanup :";
    FFSDebug.log(str1 + "start, customerId =" + paramString, 6);
    String str2 = null;
    String str3 = null;
    Object[] arrayOfObject1 = null;
    Object[] arrayOfObject2 = null;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    String str4 = localSimpleDateFormat.format(localCalendar.getTime());
    localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String str5 = localSimpleDateFormat.format(localCalendar.getTime());
    if ((paramString != null) && (paramString.trim().length() != 0))
    {
      str2 = "DELETE FROM CC_Entry WHERE (Status = 'POSTEDON' AND ProcessedTime <= ? AND LocationId IN (SELECT D.LocationId FROM BPW_Customer B, CC_Company C, CC_Location D WHERE B.CustomerID = C.CustomerId AND C.CompId = D.CompId AND B.CustomerID = ?))";
      str3 = "DELETE FROM CC_BatchHist WHERE (EffectEntryDate <= ? AND CompId IN (SELECT C.CompId FROM BPW_Customer B, CC_Company C WHERE B.CustomerID = C.CustomerId AND B.CustomerID = ?))";
      arrayOfObject1 = new Object[] { str4.trim(), paramString };
      arrayOfObject2 = new Object[] { str5.trim(), paramString };
    }
    else
    {
      str2 = "DELETE FROM CC_Entry WHERE (Status = 'POSTEDON' AND ProcessedTime <= ? AND LocationId IN (SELECT D.LocationId FROM BPW_Customer B, CC_Company C, CC_Location D WHERE B.CustomerID = C.CustomerId AND C.CompId = D.CompId))";
      str3 = "DELETE FROM CC_BatchHist WHERE (EffectEntryDate <= ? AND CompId IN (SELECT C.CompId FROM BPW_Customer B, CC_Company C WHERE B.CustomerID = C.CustomerId))";
      arrayOfObject1 = new Object[] { str4.trim() };
      arrayOfObject2 = new Object[] { str5.trim() };
    }
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject1);
      i += DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject2);
    }
    catch (FFSException localFFSException)
    {
      str6 = "*** " + str1 + " failed: ";
      str7 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str6, str7, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str6 = "*** " + str1 + " failed: ";
      String str7 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str6, str7, 0);
      throw new FFSException(localException, str6);
    }
    FFSDebug.log(str1 + "end, No of rows deleted = " + i, 6);
    return i;
  }
  
  public static CCEntryInfo[] getCCEntriesByLocationCategoryStatusAndTransType(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    String str1 = "CashCon.getCCEntriesByLocationCategoryStatusAndTransType :";
    FFSDebug.log(str1 + "start, locationId=" + paramString1 + ", category= " + paramString2 + ", status=" + paramString3 + ", transaction type=" + paramString4, 6);
    String str2 = "SELECT a.EntryId, a.LocationId, a.Amount, a.DueDate, a.WillProcessTime,a.ProcessedTime, a.TransactionType, a.Memo, a.CreatedDate, a.EntryCategory,a.Status, a.SubmittedBy, a.LastProcessId, a.LogId, a.LastModifier, b.CompId  From CC_Entry a, CC_Location b WHERE a.LocationId = b.LocationId  AND a.LocationId = ? AND a.EntryCategory = ? AND a.Status = ?  AND a.TransactionType = ? ORDER BY a.CreatedDate";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString1, paramString2, paramString3, paramString4 };
    ArrayList localArrayList = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        CCEntryInfo localCCEntryInfo = new CCEntryInfo();
        localCCEntryInfo = a(localFFSResultSet, localCCEntryInfo);
        localArrayList.add(localCCEntryInfo);
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
    FFSDebug.log(str1 + "end, size =" + localArrayList.size(), 6);
    return (CCEntryInfo[])localArrayList.toArray(new CCEntryInfo[0]);
  }
  
  private static void a(CCCompanyAcctInfo paramCCCompanyAcctInfo, BPWBankAcctInfo paramBPWBankAcctInfo)
  {
    paramCCCompanyAcctInfo.setAcctDesc(paramBPWBankAcctInfo.getAcctDesc());
    paramCCCompanyAcctInfo.setAcctGroup(paramBPWBankAcctInfo.getAcctGroup());
    paramCCCompanyAcctInfo.setAcctNickName(paramBPWBankAcctInfo.getAcctNickName());
    paramCCCompanyAcctInfo.setAcctNumber(paramBPWBankAcctInfo.getAcctNumber());
    paramCCCompanyAcctInfo.setAcctRank(paramBPWBankAcctInfo.getAcctRank());
    paramCCCompanyAcctInfo.setAcctStatus(paramBPWBankAcctInfo.getAcctStatus());
    paramCCCompanyAcctInfo.setAcctType(paramBPWBankAcctInfo.getAcctType());
    paramCCCompanyAcctInfo.setActivationDate(paramBPWBankAcctInfo.getActivationDate());
    paramCCCompanyAcctInfo.setAddrLine1(paramBPWBankAcctInfo.getAddrLine1());
    paramCCCompanyAcctInfo.setAddrLine2(paramBPWBankAcctInfo.getAddrLine2());
    paramCCCompanyAcctInfo.setAmtLimit(paramBPWBankAcctInfo.getAmtLimit());
    paramCCCompanyAcctInfo.setBankName(paramBPWBankAcctInfo.getBankName());
    paramCCCompanyAcctInfo.setBankRTN(paramBPWBankAcctInfo.getBankRTN());
    paramCCCompanyAcctInfo.setBranchName(paramBPWBankAcctInfo.getBranchName());
    paramCCCompanyAcctInfo.setCity(paramBPWBankAcctInfo.getCity());
    paramCCCompanyAcctInfo.setCountry(paramBPWBankAcctInfo.getCountry());
    paramCCCompanyAcctInfo.setCurrency(paramBPWBankAcctInfo.getCurrency());
    paramCCCompanyAcctInfo.setDayPhone(paramBPWBankAcctInfo.getDayPhone());
    paramCCCompanyAcctInfo.setFIId(paramBPWBankAcctInfo.getFIId());
    paramCCCompanyAcctInfo.setLastChangeDate(paramBPWBankAcctInfo.getLastChangeDate());
    paramCCCompanyAcctInfo.setNameOnAcct(paramBPWBankAcctInfo.getNameOnAcct());
    paramCCCompanyAcctInfo.setState(paramBPWBankAcctInfo.getState());
    paramCCCompanyAcctInfo.setStatusCode(paramBPWBankAcctInfo.getStatusCode());
    paramCCCompanyAcctInfo.setStatusMsg(paramBPWBankAcctInfo.getStatusMsg());
    paramCCCompanyAcctInfo.setSubmitDate(paramBPWBankAcctInfo.getSubmitDate());
    paramCCCompanyAcctInfo.setZipCode(paramBPWBankAcctInfo.getZipCode());
  }
  
  public static String getFIIdForThisEntry(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    String str1 = "CashCon.getFIIdForThisEntry :";
    FFSDebug.log(str1 + "start, locationId=" + paramCCEntryInfo.getLocationId(), 6);
    String str2 = null;
    String str3 = "SELECT cust.FIID from BPW_Customer cust, CC_Company comp, CC_Location loc  Where loc.CompId = comp.CompId  AND comp.CustomerId = cust.CustomerID  AND loc.LocationId = ? AND loc.Status != 'CANCELEDON'";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramCCEntryInfo.getLocationId() };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str2 = localFFSResultSet.getColumnString("FIID");
      } else {
        throw new FFSException("Could not get FIID for this Entry. LocationId =" + paramCCEntryInfo.getLocationId());
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, str5, 0);
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
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end, FIId =" + str2, 6);
    return str2;
  }
  
  private static CCCompanyCutOffInfo jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyCutOffInfo paramCCCompanyCutOffInfo)
    throws FFSException
  {
    String str1 = "CashCon.getCompanyByCompIdCutOffIdTranType :";
    FFSDebug.log(str1 + "start, compId =" + paramCCCompanyCutOffInfo.getCompId() + ", cutOffId =" + paramCCCompanyCutOffInfo.getCutOffId() + ", transaction type =" + paramCCCompanyCutOffInfo.getTransactionType(), 6);
    String str2 = "SELECT a.CompCutOffId, a.CutOffId, a.CompId, a.TransactionType  From CC_CompanyCutOff a  Where a.CompId = ? AND a.CutOffId = ?  AND a.TransactionType = ?";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramCCCompanyCutOffInfo.getCompId(), paramCCCompanyCutOffInfo.getCutOffId(), paramCCCompanyCutOffInfo.getTransactionType() };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        paramCCCompanyCutOffInfo = a(localFFSResultSet, paramCCCompanyCutOffInfo);
      }
      else
      {
        paramCCCompanyCutOffInfo.setStatusCode(16020);
        paramCCCompanyCutOffInfo.setStatusMsg(" record not found");
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
    FFSDebug.log(str1 + "end, status =" + paramCCCompanyCutOffInfo.getStatusMsg(), 6);
    return paramCCCompanyCutOffInfo;
  }
  
  public static CCCompanyInfo[] getCCCompanyArrayByFIId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    String str1 = "CashCon.getCCCompanyArrayByFIId: ";
    String str2 = " fIId =" + paramString1 + " startCompId =" + paramString2 + ", Page size =" + paramInt;
    FFSDebug.log(str1 + "start " + str2, 6);
    ArrayList localArrayList1 = new ArrayList();
    if (paramString1 != null)
    {
      StringBuffer localStringBuffer = new StringBuffer("SELECT cc.CompId, cc.CustomerId, cc.CCCompId, cc.CompName, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BatchType, cc.Status, cc.SubmittedBy, cc.LogId  FROM CC_Company cc , BPW_Customer cust  Where cc.Status != 'CANCELEDON' AND cust.CustomerID = cc.CustomerId ");
      ArrayList localArrayList2 = new ArrayList();
      DBUtil.appendToCondition(localStringBuffer, paramString1, " AND cust.FIID = ? ", localArrayList2);
      DBUtil.appendToCondition(localStringBuffer, paramString2, " AND cc.CompId > ?", localArrayList2);
      localStringBuffer.append(" ORDER BY cc.CompId ASC");
      String str3 = localStringBuffer.toString();
      FFSResultSet localFFSResultSet = null;
      Object[] arrayOfObject = (Object[])localArrayList2.toArray(new Object[0]);
      CCCompanyInfo localCCCompanyInfo = null;
      try
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
        while ((localFFSResultSet.getNextRow()) && (localArrayList1.size() <= paramInt))
        {
          localCCCompanyInfo = new CCCompanyInfo();
          localCCCompanyInfo = a(localFFSResultSet, localCCCompanyInfo);
          localArrayList1.add(localCCCompanyInfo);
        }
      }
      catch (FFSException localFFSException)
      {
        str4 = "*** " + str1 + " failed: ";
        str5 = FFSDebug.stackTrace(localFFSException);
        FFSDebug.log(str4, str5, 0);
        throw localFFSException;
      }
      catch (Exception localException1)
      {
        String str4 = "*** " + str1 + " failed: ";
        String str5 = FFSDebug.stackTrace(localException1);
        FFSDebug.log(str4, str5, 0);
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
            FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
          }
        }
      }
    }
    FFSDebug.log(str1 + "end, count =" + localArrayList1.size(), 6);
    return (CCCompanyInfo[])localArrayList1.toArray(new CCCompanyInfo[0]);
  }
  
  private static CCEntryHistInfo jdMethod_do(CCEntryHistInfo paramCCEntryHistInfo)
  {
    String str1 = "CashCon.validateHistoryDates: ";
    String str2;
    if ((paramCCEntryHistInfo.getStartDate() != null) && (!BPWUtil.checkFrontEndDateFormat(paramCCEntryHistInfo.getStartDate())))
    {
      paramCCEntryHistInfo.setStatusCode(17205);
      str2 = str1 + "Invalid start date value for getting histories." + ": " + paramCCEntryHistInfo.getStartDate();
      paramCCEntryHistInfo.setStatusMsg(str2);
      FFSDebug.log(str2, 0);
      return paramCCEntryHistInfo;
    }
    if ((paramCCEntryHistInfo.getEndDate() != null) && (!BPWUtil.checkFrontEndDateFormat(paramCCEntryHistInfo.getEndDate())))
    {
      paramCCEntryHistInfo.setStatusCode(17206);
      str2 = str1 + "Invalid end date value for getting histories." + ": " + paramCCEntryHistInfo.getEndDate();
      paramCCEntryHistInfo.setStatusMsg(str2);
      FFSDebug.log(str2, 0);
      return paramCCEntryHistInfo;
    }
    if ((paramCCEntryHistInfo.getStartDate() != null) && (paramCCEntryHistInfo.getStartDate().trim().length() == 8)) {
      paramCCEntryHistInfo.setStartDate(paramCCEntryHistInfo.getStartDate().trim() + "00");
    }
    if ((paramCCEntryHistInfo.getEndDate() != null) && (paramCCEntryHistInfo.getEndDate().trim().length() == 8)) {
      paramCCEntryHistInfo.setEndDate(paramCCEntryHistInfo.getEndDate().trim() + "00");
    }
    paramCCEntryHistInfo.setStatusCode(0);
    paramCCEntryHistInfo.setStatusMsg("Success");
    return paramCCEntryHistInfo;
  }
  
  public static CCLocationInfo[] getMaturedDepositLocationInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "CashCon.getMaturedDepositLocationInfo: ";
    FFSDebug.log(str1 + "start. MatureDate =" + paramString, 6);
    ArrayList localArrayList = new ArrayList();
    if (paramString == null)
    {
      localObject1 = "***" + str1 + " failed: matureDate is null";
      FFSDebug.log((String)localObject1, 0);
      return null;
    }
    Object localObject1 = null;
    CCLocationInfo localCCLocationInfo = null;
    String str2 = "SELECT cc.LocationId, cc.LocationName, cc.CCLocationId, cc.CompId, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BankRtn, cc.BankName, cc.AccountNum, cc.AccountType, cc.DepositMin, cc.DepositMax, cc.AnticipatoryDepos, cc.ThresholdDeposAmt, cc.ConsolidateDepos, cc.DepositPrenote, cc.DepPrenSubDate, cc.DepPrenoteStatus, cc.DisbursePrenote, cc.DisPrenSubDate, cc.DisPrenoteStatus, cc.Memo, cc.Status, cc.LogId, cc.LastRequestTime, cc.SubmittedBy, cc.AgentId, cc.AgentType  FROM CC_Location cc  WHERE DepPrenoteStatus = 'Pending' AND DepPrenSubDate IS NOT NULL  AND DepPrenSubDate < ?";
    Object[] arrayOfObject = { paramString };
    try
    {
      localObject1 = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (((FFSResultSet)localObject1).getNextRow())
      {
        localCCLocationInfo = new CCLocationInfo();
        localCCLocationInfo = a((FFSResultSet)localObject1, localCCLocationInfo);
        localArrayList.add(localCCLocationInfo);
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
      if (localObject1 != null) {
        try
        {
          ((FFSResultSet)localObject1).close();
          localObject1 = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + " end. Number of locations: " + localArrayList.size(), 6);
    return (CCLocationInfo[])localArrayList.toArray(new CCLocationInfo[0]);
  }
  
  public static CCLocationInfo[] getMaturedDisbursementLocationInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "CashCon.getMaturedDisbursementLocationInfo: ";
    FFSDebug.log(str1 + "start. MatureDate =" + paramString, 6);
    ArrayList localArrayList = new ArrayList();
    if (paramString == null)
    {
      localObject1 = "***" + str1 + " failed: matureDate is null";
      FFSDebug.log((String)localObject1, 0);
      return null;
    }
    Object localObject1 = null;
    CCLocationInfo localCCLocationInfo = null;
    String str2 = "SELECT cc.LocationId, cc.LocationName, cc.CCLocationId, cc.CompId, cc.ConcentrateAcctId, cc.DisburseAcctId, cc.BankRtn, cc.BankName, cc.AccountNum, cc.AccountType, cc.DepositMin, cc.DepositMax, cc.AnticipatoryDepos, cc.ThresholdDeposAmt, cc.ConsolidateDepos, cc.DepositPrenote, cc.DepPrenSubDate, cc.DepPrenoteStatus, cc.DisbursePrenote, cc.DisPrenSubDate, cc.DisPrenoteStatus, cc.Memo, cc.Status, cc.LogId, cc.LastRequestTime, cc.SubmittedBy, cc.AgentId, cc.AgentType  FROM CC_Location cc  WHERE DisPrenoteStatus = 'Pending' AND DisPrenSubDate IS NOT NULL  AND DisPrenSubDate < ?";
    Object[] arrayOfObject = { paramString };
    try
    {
      localObject1 = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (((FFSResultSet)localObject1).getNextRow())
      {
        localCCLocationInfo = new CCLocationInfo();
        localCCLocationInfo = a((FFSResultSet)localObject1, localCCLocationInfo);
        localArrayList.add(localCCLocationInfo);
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
      if (localObject1 != null) {
        try
        {
          ((FFSResultSet)localObject1).close();
          localObject1 = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + " end. Number of locations: " + localArrayList.size(), 6);
    return (CCLocationInfo[])localArrayList.toArray(new CCLocationInfo[0]);
  }
  
  public static int updateMaturedDepositLocationPrenoteStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "CashCon.updateMaturedDepositLocationPrenoteStatus: ";
    FFSDebug.log(str1 + "start. matureDateStr : " + paramString, 6);
    String str2 = "UPDATE CC_Location SET DepPrenoteStatus = 'Success' WHERE DepPrenoteStatus = 'Pending' AND DepPrenSubDate IS NOT NULL  AND DepPrenSubDate < ?";
    Object[] arrayOfObject = { paramString };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "done, rows updated =" + i, 6);
    return i;
  }
  
  public static int updateMaturedDisbursementLocationPrenoteStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "CashCon.updateMaturedDisbursementLocationPrenoteStatus: ";
    FFSDebug.log(str1 + "start. matureDateStr : " + paramString, 6);
    String str2 = "UPDATE CC_Location SET DisPrenoteStatus = 'Success' WHERE DisPrenoteStatus = 'Pending' AND DisPrenSubDate IS NOT NULL  AND DisPrenSubDate < ?";
    Object[] arrayOfObject = { paramString };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "done, rows updated =" + i, 6);
    return i;
  }
  
  private static long a(long paramLong, FFSResultSet paramFFSResultSet, List paramList)
    throws FFSException
  {
    CCEntryInfo localCCEntryInfo = null;
    if (paramFFSResultSet != null) {
      while (paramFFSResultSet.getNextRow())
      {
        localCCEntryInfo = new CCEntryInfo();
        localCCEntryInfo = a(paramFFSResultSet, localCCEntryInfo);
        if ((paramList == null) || (paramList.contains(localCCEntryInfo.getLocationId()))) {
          paramLong += 1L;
        }
      }
    }
    return paramLong;
  }
  
  private static String a(CCEntryHistInfo paramCCEntryHistInfo, StringBuffer paramStringBuffer, ArrayList paramArrayList)
  {
    if (paramCCEntryHistInfo.getFIId() != null) {
      paramStringBuffer.append("SELECT a.EntryId, a.LocationId, a.Amount, a.DueDate, a.WillProcessTime,a.ProcessedTime, a.TransactionType, a.Memo, a.CreatedDate, a.EntryCategory,a.Status, a.SubmittedBy, a.LastProcessId, a.LogId, a.LastModifier, b.CompId  From CC_Entry a, CC_Location b , CC_Company c, BPW_Customer d  Where a.LocationId = b.LocationId AND b.CompId = c.CompId  AND c.CustomerId = d.CustomerID ");
    } else if ((paramCCEntryHistInfo.getCustomerId() != null) || (paramCCEntryHistInfo.getCompId() != null)) {
      paramStringBuffer.append("SELECT a.EntryId, a.LocationId, a.Amount, a.DueDate, a.WillProcessTime,a.ProcessedTime, a.TransactionType, a.Memo, a.CreatedDate, a.EntryCategory,a.Status, a.SubmittedBy, a.LastProcessId, a.LogId, a.LastModifier, b.CompId  From CC_Entry a, CC_Location b , CC_Company c  Where a.LocationId = b.LocationId AND b.CompId = c.CompId ");
    } else {
      paramStringBuffer.append("SELECT a.EntryId, a.LocationId, a.Amount, a.DueDate, a.WillProcessTime,a.ProcessedTime, a.TransactionType, a.Memo, a.CreatedDate, a.EntryCategory,a.Status, a.SubmittedBy, a.LastProcessId, a.LogId, a.LastModifier, b.CompId  From CC_Entry a, CC_Location b WHERE a.LocationId = b.LocationId  AND a.Status is not null ");
    }
    DBUtil.appendToCondition(paramStringBuffer, paramCCEntryHistInfo.getFIId(), " AND d.FIID = ?", paramArrayList);
    DBUtil.appendToCondition(paramStringBuffer, paramCCEntryHistInfo.getCustomerId(), " AND c.CustomerId = ?", paramArrayList);
    DBUtil.appendToCondition(paramStringBuffer, paramCCEntryHistInfo.getCompId(), " AND c.CompId = ?", paramArrayList);
    DBUtil.appendToCondition(paramStringBuffer, paramCCEntryHistInfo.getStartDate(), " AND a.DueDate >= ?", paramArrayList);
    DBUtil.appendToCondition(paramStringBuffer, paramCCEntryHistInfo.getEndDate(), " AND a.DueDate <= ?", paramArrayList);
    DBUtil.appendArrayToCondition(paramStringBuffer, paramCCEntryHistInfo.getTransactionTypeList(), " AND a.TransactionType IN (", paramArrayList);
    DBUtil.appendArrayToCondition(paramStringBuffer, paramCCEntryHistInfo.getCategoryTypeList(), " AND a.EntryCategory IN (", paramArrayList);
    DBUtil.appendArrayToCondition(paramStringBuffer, paramCCEntryHistInfo.getStatusList(), " AND a.Status IN (", paramArrayList);
    DBUtil.appendToCondition(paramStringBuffer, paramCCEntryHistInfo.getSubmittedBy(), " AND a.SubmittedBy = ? ", paramArrayList);
    return paramStringBuffer.toString();
  }
  
  private static void a(CCEntryHistInfo paramCCEntryHistInfo, long paramLong)
  {
    if (paramCCEntryHistInfo.getHistId() != null)
    {
      StringBuffer localStringBuffer = new StringBuffer(paramCCEntryHistInfo.getHistId());
      localStringBuffer.append(kd).append("TOTALTRANS").append("=").append(paramLong);
      paramCCEntryHistInfo.setHistId(localStringBuffer.toString());
      paramCCEntryHistInfo.setTotalTrans(paramLong);
    }
  }
  
  private static void jdMethod_if(CCEntryHistInfo paramCCEntryHistInfo)
    throws FFSException
  {
    String str = DBUtil.getNextIndexString("HistID");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("HISTID").append("=").append(str);
    if (paramCCEntryHistInfo.getFIId() != null) {
      localStringBuffer.append(kd).append("FIID").append("=").append(paramCCEntryHistInfo.getFIId());
    }
    if (paramCCEntryHistInfo.getCustomerId() != null) {
      localStringBuffer.append(kd).append("CUSTOMERID").append("=").append(paramCCEntryHistInfo.getCustomerId());
    }
    if (paramCCEntryHistInfo.getCompId() != null) {
      localStringBuffer.append(kd).append("COMPANYID").append("=").append(paramCCEntryHistInfo.getCompId());
    }
    if (paramCCEntryHistInfo.getStartDate() != null) {
      localStringBuffer.append(kd).append("STARTDATE").append("=").append(paramCCEntryHistInfo.getStartDate());
    }
    if (paramCCEntryHistInfo.getEndDate() != null) {
      localStringBuffer.append(kd).append("ENDDATE").append("=").append(paramCCEntryHistInfo.getEndDate());
    }
    if ((paramCCEntryHistInfo.getLocationIdList() != null) && (paramCCEntryHistInfo.getLocationIdList().length != 0)) {
      localStringBuffer.append(kd).append("LOCATIONID").append("=").append(BPWUtil.getStringFromArray(paramCCEntryHistInfo.getLocationIdList(), kb));
    }
    if ((paramCCEntryHistInfo.getTransactionTypeList() != null) && (paramCCEntryHistInfo.getTransactionTypeList().length != 0)) {
      localStringBuffer.append(kd).append("TRANTYPE").append("=").append(BPWUtil.getStringFromArray(paramCCEntryHistInfo.getTransactionTypeList(), kb));
    }
    if ((paramCCEntryHistInfo.getCategoryTypeList() != null) && (paramCCEntryHistInfo.getCategoryTypeList().length != 0)) {
      localStringBuffer.append(kd).append("CATEGORY").append("=").append(BPWUtil.getStringFromArray(paramCCEntryHistInfo.getCategoryTypeList(), kb));
    }
    if ((paramCCEntryHistInfo.getStatusList() != null) && (paramCCEntryHistInfo.getStatusList().length != 0)) {
      localStringBuffer.append(kd).append("STATUS").append("=").append(BPWUtil.getStringFromArray(paramCCEntryHistInfo.getStatusList(), kb));
    }
    if (paramCCEntryHistInfo.getSubmittedBy() != null) {
      localStringBuffer.append(kd).append("SUBMITTEDBY").append("=").append(paramCCEntryHistInfo.getSubmittedBy());
    }
    localStringBuffer.append(kd).append("ASCENDING").append("=").append(paramCCEntryHistInfo.getAscending());
    paramCCEntryHistInfo.setHistId(localStringBuffer.toString());
  }
  
  private static void a(CCEntryHistInfo paramCCEntryHistInfo)
    throws FFSException
  {
    String str1 = paramCCEntryHistInfo.getHistId();
    if (str1 != null)
    {
      HashMap localHashMap = BPWUtil.stringToHashMap(str1, kd);
      if (localHashMap != null)
      {
        paramCCEntryHistInfo.setFIId((String)localHashMap.get("FIID"));
        paramCCEntryHistInfo.setCustomerId((String)localHashMap.get("CUSTOMERID"));
        paramCCEntryHistInfo.setCompId((String)localHashMap.get("COMPANYID"));
        paramCCEntryHistInfo.setStartDate((String)localHashMap.get("STARTDATE"));
        paramCCEntryHistInfo.setEndDate((String)localHashMap.get("ENDDATE"));
        paramCCEntryHistInfo.setLocationIdList(BPWUtil.getArrayFromString((String)localHashMap.get("LOCATIONID"), kb));
        paramCCEntryHistInfo.setTransactionTypeList(BPWUtil.getArrayFromString((String)localHashMap.get("TRANTYPE"), kb));
        paramCCEntryHistInfo.setCategoryTypeList(BPWUtil.getArrayFromString((String)localHashMap.get("CATEGORY"), kb));
        paramCCEntryHistInfo.setStatusList(BPWUtil.getArrayFromString((String)localHashMap.get("STATUS"), kb));
        paramCCEntryHistInfo.setSubmittedBy((String)localHashMap.get("SUBMITTEDBY"));
        String str2 = (String)localHashMap.get("TOTALTRANS");
        if (str2 != null) {
          paramCCEntryHistInfo.setTotalTrans(Long.parseLong(str2));
        }
        paramCCEntryHistInfo.setAscending(Boolean.valueOf((String)localHashMap.get("ASCENDING")).booleanValue());
      }
    }
  }
  
  public static void createSessionData(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    jdMethod_for(paramPagingInfo, localStringBuffer, localArrayList);
    String str = localStringBuffer.toString();
    paramPagingInfo.setTotalTrans(0);
    jdMethod_case(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
    if (paramPagingInfo.getStatusCode() != 0) {}
  }
  
  private static void jdMethod_for(PagingInfo paramPagingInfo, StringBuffer paramStringBuffer, ArrayList paramArrayList)
  {
    String str1 = "CashCon.createPagingSearchCriteria: ";
    FFSDebug.log(str1 + "start ...", 6);
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    String str2 = (String)localHashMap2.get("FIID");
    String str3 = (String)localHashMap2.get("CustomerId");
    String str4 = (String)localHashMap2.get("CompId");
    if (str2 != null) {
      paramStringBuffer.append("SELECT a.EntryId, a.LocationId, a.Amount, a.DueDate, a.WillProcessTime,a.ProcessedTime, a.TransactionType, a.Memo, a.CreatedDate, a.EntryCategory,a.Status, a.SubmittedBy, a.LastProcessId, a.LogId, a.LastModifier, b.CompId  From CC_Entry a, CC_Location b , CC_Company c, BPW_Customer d  Where a.LocationId = b.LocationId AND b.CompId = c.CompId  AND c.CustomerId = d.CustomerID ");
    } else if ((str3 != null) || (str4 != null)) {
      paramStringBuffer.append("SELECT a.EntryId, a.LocationId, a.Amount, a.DueDate, a.WillProcessTime,a.ProcessedTime, a.TransactionType, a.Memo, a.CreatedDate, a.EntryCategory,a.Status, a.SubmittedBy, a.LastProcessId, a.LogId, a.LastModifier, b.CompId  From CC_Entry a, CC_Location b , CC_Company c  Where a.LocationId = b.LocationId AND b.CompId = c.CompId ");
    } else {
      paramStringBuffer.append("SELECT a.EntryId, a.LocationId, a.Amount, a.DueDate, a.WillProcessTime,a.ProcessedTime, a.TransactionType, a.Memo, a.CreatedDate, a.EntryCategory,a.Status, a.SubmittedBy, a.LastProcessId, a.LogId, a.LastModifier, b.CompId  From CC_Entry a, CC_Location b WHERE a.LocationId = b.LocationId  AND a.Status is not null ");
    }
    DBUtil.appendToCondition(paramStringBuffer, str2, " AND d.FIID = ?", paramArrayList);
    DBUtil.appendToCondition(paramStringBuffer, str3, " AND c.CustomerId = ?", paramArrayList);
    DBUtil.appendToCondition(paramStringBuffer, str4, " AND c.CompId = ?", paramArrayList);
    DBUtil.appendToCondition(paramStringBuffer, paramPagingInfo.getStartDate(), " AND a.DueDate >= ?", paramArrayList);
    DBUtil.appendToCondition(paramStringBuffer, paramPagingInfo.getEndDate(), " AND a.DueDate <= ?", paramArrayList);
    DBUtil.appendArrayToCondition(paramStringBuffer, (String[])localHashMap2.get("TransType"), " AND a.TransactionType IN (", paramArrayList);
    DBUtil.appendArrayToCondition(paramStringBuffer, (String[])localHashMap2.get("CategoryList"), " AND a.EntryCategory IN (", paramArrayList);
    DBUtil.appendArrayToCondition(paramStringBuffer, (String[])localHashMap2.get("StatusList"), " AND a.Status IN (", paramArrayList);
    String[] arrayOfString = (String[])localHashMap2.get("SubmittedBys");
    if (arrayOfString != null) {
      DBUtil.appendToCondition(paramStringBuffer, arrayOfString[0], " AND a.SubmittedBy = ? ", paramArrayList);
    }
    FFSDebug.log(str1 + "end, SQL query =" + paramStringBuffer.toString(), 6);
  }
  
  private static void jdMethod_case(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "CashCon.createSessionDataCashCon: ";
    FFSDebug.log(str1 + " start", 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = localPropertyConfig.getBatchSize();
      int j = paramPagingInfo.getTotalTrans();
      String str3 = paramPagingInfo.getPagingContext().getSessionId();
      if ((str3 == null) || (str3.trim().length() == 0))
      {
        str3 = DBUtil.getNextIndexString("SessionID");
        paramPagingInfo.getPagingContext().setSessionId(str3);
      }
      paramString = paramString + " Order By EntryId";
      FFSDebug.log(str1 + "Sql Statement: " + paramString, 6);
      for (int k = 0; k < paramArrayList.size(); k++) {
        FFSDebug.log(str1 + " Sql Param:" + k + " :" + String.valueOf(paramArrayList.get(k)), 6);
      }
      Object[] arrayOfObject = paramArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString, arrayOfObject);
      HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
      HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
      CCEntryInfo localCCEntryInfo = null;
      ACHHistInfo localACHHistInfo = null;
      List localList = null;
      String[] arrayOfString = (String[])localHashMap2.get("LocationIds");
      if ((arrayOfString != null) && (arrayOfString.length != 0)) {
        localList = Arrays.asList(arrayOfString);
      }
      int m = BPWUtil.getSessionSize();
      while (localFFSResultSet.getNextRow())
      {
        localCCEntryInfo = new CCEntryInfo();
        localCCEntryInfo = a(localFFSResultSet, localCCEntryInfo);
        if ((localList == null) || (localList.contains(localCCEntryInfo.getLocationId())))
        {
          j++;
          if (j > m)
          {
            paramPagingInfo.setStatusCode(28020);
            paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
            break;
          }
          localACHHistInfo = new ACHHistInfo();
          localACHHistInfo.setObjInfo(localCCEntryInfo);
          localACHHistInfo.setCursorId(String.valueOf(j));
          CashConTempHist.createTempHist(paramFFSConnectionHolder, str3, j, localACHHistInfo);
          if (j % i == 0) {
            paramFFSConnectionHolder.conn.commit();
          }
        }
      }
      paramPagingInfo.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1 + " : end", 6);
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + " failed : ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + " failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  public static PagingInfo getSessionPage(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "CashCon.getSessionPage";
    FFSDebug.log(str1 + " : start", 6);
    String str2 = paramPagingInfo.getPagingContext().getSessionId();
    if ((str2 == null) || (str2.trim().length() == 0))
    {
      paramPagingInfo.setStatusCode(16000);
      paramPagingInfo.setStatusMsg("sessionId is null");
      FFSDebug.log(str1 + " : " + "Null sessionId passed", 0);
      return paramPagingInfo;
    }
    try
    {
      ArrayList localArrayList = CashConTempHist.getSessionPage(paramFFSConnectionHolder, paramPagingInfo);
      paramPagingInfo.setPagingResult(localArrayList);
      CashConTempHist.getBounds(paramFFSConnectionHolder, paramPagingInfo);
      paramPagingInfo.setStatusCode(0);
      return paramPagingInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed : ";
      FFSDebug.log(str3 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str3);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.CashCon
 * JD-Core Version:    0.7.0.1
 */