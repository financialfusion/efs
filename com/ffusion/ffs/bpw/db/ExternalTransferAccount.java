package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.CommBankIdentifier;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.enums.UserAssignedAmount;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ExternalTransferAccount
  implements DBConsts, FFSConst, ACHConsts, BPWResource
{
  public static boolean isBPWManagedCustomer()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    try
    {
      String str = localPropertyConfig.otherProperties.getProperty("bpw.transfer.manageCustomers", "BPW");
      return str.equalsIgnoreCase("BPW");
    }
    catch (Exception localException) {}
    return true;
  }
  
  public static ExtTransferAcctInfo add(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.add: ";
    FFSDebug.log(str1, "start ...", paramExtTransferAcctInfo.toString(), 6);
    String str2 = null;
    String str3 = null;
    String str4 = null;
    try
    {
      String str5;
      if (paramExtTransferAcctInfo == null)
      {
        str5 = "failed: Null ExtTransferAcctInfo Object passed";
        FFSDebug.log("******", str1, str5, 0);
        paramExtTransferAcctInfo = new ExtTransferAcctInfo();
        paramExtTransferAcctInfo.setStatusCode(16000);
        str6 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg(str6);
        return paramExtTransferAcctInfo;
      }
      if ((paramExtTransferAcctInfo.getLogId() == null) || (paramExtTransferAcctInfo.getLogId().trim().length() == 0))
      {
        paramExtTransferAcctInfo.setStatusCode(16010);
        str5 = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "LogId" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg(str5);
        FFSDebug.log(str1, str5, 0);
        return paramExtTransferAcctInfo;
      }
      if ((paramExtTransferAcctInfo.getAcctScope() == null) || (paramExtTransferAcctInfo.getAcctScope().trim().length() == 0)) {
        paramExtTransferAcctInfo.setAcctScope("MANAGED");
      }
      if ((paramExtTransferAcctInfo.getBankRtnType() == null) || (paramExtTransferAcctInfo.getBankRtnType().trim().length() == 0)) {
        paramExtTransferAcctInfo.setBankRtnType("FEDABA");
      }
      if ((paramExtTransferAcctInfo.getAcctDest() == null) || (paramExtTransferAcctInfo.getAcctDest().trim().length() == 0)) {
        paramExtTransferAcctInfo.setAcctDest("EXTERNAL");
      }
      boolean bool = paramExtTransferAcctInfo.getAcctDest().equals("EXTERNAL");
      if (!a(paramExtTransferAcctInfo, bool))
      {
        str6 = "failed. ";
        FFSDebug.log("****", str1, str6, paramExtTransferAcctInfo.getStatusMsg(), 0);
        return paramExtTransferAcctInfo;
      }
      if (!jdMethod_try(paramExtTransferAcctInfo))
      {
        FFSDebug.log("****", str1, paramExtTransferAcctInfo.getStatusMsg(), 0);
        return paramExtTransferAcctInfo;
      }
      if ((bool) && (!jdMethod_new(paramExtTransferAcctInfo)))
      {
        str6 = "failed. ";
        FFSDebug.log("****", str1, str6, paramExtTransferAcctInfo.getStatusMsg(), 0);
        return paramExtTransferAcctInfo;
      }
      if (!jdMethod_int(paramExtTransferAcctInfo))
      {
        str6 = "failed. ";
        FFSDebug.log("****", str1, str6, paramExtTransferAcctInfo.getStatusMsg(), 0);
        return paramExtTransferAcctInfo;
      }
      if (!jdMethod_if(paramFFSConnectionHolder, paramExtTransferAcctInfo))
      {
        str6 = "failed: ";
        FFSDebug.log("******", str1, str6, paramExtTransferAcctInfo.getStatusMsg(), 0);
        return paramExtTransferAcctInfo;
      }
      str6 = paramExtTransferAcctInfo.getSubmittedBy();
      if ((str6 == null) || (str6.trim().length() == 0))
      {
        str7 = "failed: Null SubmittedBy field";
        FFSDebug.log("******", str1, str7, 0);
        paramExtTransferAcctInfo.setStatusCode(16010);
        localObject = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "SubmittedBy" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject);
        return paramExtTransferAcctInfo;
      }
      paramExtTransferAcctInfo.setStatus("ACTIVE");
      if (paramExtTransferAcctInfo.getVerifyStatus() == 1) {
        paramExtTransferAcctInfo.setStatus("INACTIVE");
      }
      String str7 = paramExtTransferAcctInfo.getAcctScope();
      if (str7.equalsIgnoreCase("MANAGED"))
      {
        str3 = getExtTrnAcctIdByAcctRtnCustStatus(paramFFSConnectionHolder, paramExtTransferAcctInfo);
        if (str3 != null)
        {
          paramExtTransferAcctInfo.setStatusCode(19700);
          paramExtTransferAcctInfo.setStatusMsg("Duplicate record.");
          localObject = "failed: ";
          FFSDebug.log("******", str1, (String)localObject, paramExtTransferAcctInfo.getStatusMsg(), 0);
          return paramExtTransferAcctInfo;
        }
      }
      paramExtTransferAcctInfo.setAcctId(DBUtil.getNextIndexStringWithPadding("ETFACCTID", 18, '0'));
      paramExtTransferAcctInfo.setCreateDate(FFSUtil.getDateString());
      str4 = "INSERT INTO ETF_ExternalAcct (AcctId, CustomerId, NickName, NickName_LWRCase, AcctNum, AcctType, AcctBankRtn, AcctScope,AcctDest, AcctCategory, BankRtnType, RecipientId, RecipientType, RecipientName, Prenote, PrenoteType, SubmittedBy, CreateDate, LogId, AgreedToTerms, VerifyStatus, VerifyFailedCount, PrimaryAcctHolder, SecondaryAcctHolder, CheckNum, Status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      FFSDebug.log(str1, "adding external transfer account: " + paramExtTransferAcctInfo, 6);
      if ((paramExtTransferAcctInfo.getNickName() != null) && (paramExtTransferAcctInfo.getNickName().trim().length() != 0)) {
        str2 = paramExtTransferAcctInfo.getNickName().toLowerCase();
      }
      Object localObject = { paramExtTransferAcctInfo.getAcctId(), paramExtTransferAcctInfo.getCustomerId(), paramExtTransferAcctInfo.getNickName(), str2, paramExtTransferAcctInfo.getAcctNum(), paramExtTransferAcctInfo.getAcctType(), paramExtTransferAcctInfo.getAcctBankRtn(), paramExtTransferAcctInfo.getAcctScope(), paramExtTransferAcctInfo.getAcctDest(), paramExtTransferAcctInfo.getAcctCategory(), paramExtTransferAcctInfo.getBankRtnType(), paramExtTransferAcctInfo.getRecipientId(), paramExtTransferAcctInfo.getRecipientType(), paramExtTransferAcctInfo.getRecipientName(), paramExtTransferAcctInfo.getPrenote(), paramExtTransferAcctInfo.getPrenoteType(), paramExtTransferAcctInfo.getSubmittedBy(), paramExtTransferAcctInfo.getCreateDate(), paramExtTransferAcctInfo.getLogId(), paramExtTransferAcctInfo.getAgreedToTerms(), new Integer(paramExtTransferAcctInfo.getVerifyStatus()), new Integer(paramExtTransferAcctInfo.getVerifyFailedCount()), paramExtTransferAcctInfo.getPrimaryAcctHolder(), paramExtTransferAcctInfo.getSecondaryAcctHolder(), paramExtTransferAcctInfo.getCheckNumber(), paramExtTransferAcctInfo.getStatus() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str4, (Object[])localObject);
      FFSDebug.log(str1, "Number of records added " + i, 6);
      paramExtTransferAcctInfo.setStatusCode(0);
      paramExtTransferAcctInfo.setStatusMsg("Success");
      FFSDebug.log(str1, "End", 6);
      return paramExtTransferAcctInfo;
    }
    catch (Throwable localThrowable)
    {
      String str6 = "***" + str1 + "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str6, 0);
      throw new FFSException(localThrowable, str6);
    }
  }
  
  public static ExtTransferAcctInfo getExternalTransferAccount(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    return getExternalTransferAccount(paramFFSConnectionHolder, paramExtTransferAcctInfo, true, true);
  }
  
  public static ExtTransferAcctInfo getExternalTransferAccount(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.getExternalTransferAccount: ";
    FFSDebug.log(str1, "start ...", 6);
    FFSResultSet localFFSResultSet = null;
    String str2 = null;
    if (paramExtTransferAcctInfo == null)
    {
      localObject1 = "failed: Null ExtTransferAcctInfo Object passed";
      FFSDebug.log("******", str1, (String)localObject1, 0);
      paramExtTransferAcctInfo = new ExtTransferAcctInfo();
      paramExtTransferAcctInfo.setStatusCode(16000);
      localObject2 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg((String)localObject2);
      return paramExtTransferAcctInfo;
    }
    str2 = paramExtTransferAcctInfo.getAcctId();
    if (str2 == null)
    {
      localObject1 = "failed: Null acctId field passed";
      FFSDebug.log("******", str1, (String)localObject1, 0);
      paramExtTransferAcctInfo.setStatusCode(16010);
      paramExtTransferAcctInfo.setStatusMsg("acctId  contains null ");
      return paramExtTransferAcctInfo;
    }
    Object localObject1 = new StringBuffer("SELECT a.AcctId, a.CustomerId, a.NickName, a.AcctNum, a.AcctType, a.AcctBankRtn, a.AcctScope, a.AcctDest, a.AcctCategory, a.BankRtnType, a.RecipientId, a.RecipientType, a.RecipientName, a.Prenote, a.PrenoteStatus, a.PrenoteSubDate, a.PrenoteType, a.SubmittedBy, a.CreateDate, a.LogId, a.Status, a.VerifyStatus, a.VerifyFailedCount, a.AgreedToTerms, a.DepositDate, a.PrimaryAcctHolder, a.SecondaryAcctHolder, a.CheckNum FROM ETF_ExternalAcct a  WHERE a.AcctId = ? ");
    jdMethod_if((StringBuffer)localObject1, paramBoolean2);
    a((StringBuffer)localObject1, paramBoolean1);
    Object localObject2 = { str2 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, ((StringBuffer)localObject1).toString(), (Object[])localObject2);
      if (localFFSResultSet.getNextRow())
      {
        a(localFFSResultSet, paramExtTransferAcctInfo);
        paramExtTransferAcctInfo.setStatusCode(0);
        paramExtTransferAcctInfo.setStatusMsg("Success");
      }
      else
      {
        paramExtTransferAcctInfo.setStatusCode(16020);
        paramExtTransferAcctInfo.setStatusMsg(" record not found");
      }
      FFSDebug.log("***", str1, "End", 6);
      ExtTransferAcctInfo localExtTransferAcctInfo = paramExtTransferAcctInfo;
      return localExtTransferAcctInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("****", str1, str3, 0);
      throw new FFSException(localThrowable, str3);
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
          FFSDebug.log("****", str1, "failed to close ResultSet", localException.toString(), 0);
        }
      }
    }
  }
  
  public static ExtTransferAcctInfo getExternalTransferAccountByAcctNum(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    return getExternalTransferAccountByAcctNum(paramFFSConnectionHolder, paramExtTransferAcctInfo, true);
  }
  
  public static ExtTransferAcctInfo getExternalTransferAccountByAcctNum(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.getExternalTransferAccountByAcctNum: ";
    FFSDebug.log(str1, "start ...", 6);
    FFSResultSet localFFSResultSet = null;
    String str2 = null;
    if (paramExtTransferAcctInfo == null)
    {
      localObject1 = "failed: Null ExtTransferAcctInfo Object passed";
      FFSDebug.log("******", str1, (String)localObject1, 0);
      paramExtTransferAcctInfo = new ExtTransferAcctInfo();
      paramExtTransferAcctInfo.setStatusCode(16000);
      localObject2 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg((String)localObject2);
      return paramExtTransferAcctInfo;
    }
    str2 = paramExtTransferAcctInfo.getAcctNum();
    if (str2 == null)
    {
      localObject1 = "failed: Null acctId field passed";
      FFSDebug.log("******", str1, (String)localObject1, 0);
      paramExtTransferAcctInfo.setStatusCode(16010);
      localObject2 = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "acctId" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg((String)localObject2);
      return paramExtTransferAcctInfo;
    }
    Object localObject1 = new StringBuffer("SELECT a.AcctId, a.CustomerId, a.NickName, a.AcctNum, a.AcctType, a.AcctBankRtn, a.AcctScope, a.AcctDest, a.AcctCategory, a.BankRtnType, a.RecipientId, a.RecipientType, a.RecipientName, a.Prenote, a.PrenoteStatus, a.PrenoteSubDate, a.PrenoteType, a.SubmittedBy, a.CreateDate, a.LogId, a.Status, a.VerifyStatus, a.VerifyFailedCount, a.AgreedToTerms, a.DepositDate, a.PrimaryAcctHolder, a.SecondaryAcctHolder, a.CheckNum FROM ETF_ExternalAcct a  WHERE a.AcctNum = ? ");
    ((StringBuffer)localObject1).append(" AND a.Status <> '");
    ((StringBuffer)localObject1).append("CANCELEDON");
    ((StringBuffer)localObject1).append("'");
    a((StringBuffer)localObject1, paramBoolean);
    Object localObject2 = { str2 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, ((StringBuffer)localObject1).toString(), (Object[])localObject2);
      if (localFFSResultSet.getNextRow())
      {
        a(localFFSResultSet, paramExtTransferAcctInfo);
        paramExtTransferAcctInfo.setStatusCode(0);
        paramExtTransferAcctInfo.setStatusMsg("Success");
      }
      else
      {
        paramExtTransferAcctInfo.setStatusCode(16020);
        paramExtTransferAcctInfo.setStatusMsg(" record not found");
      }
      FFSDebug.log("***", str1, "End", 6);
      ExtTransferAcctInfo localExtTransferAcctInfo = paramExtTransferAcctInfo;
      return localExtTransferAcctInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("****", str1, str3, 0);
      throw new FFSException(localThrowable, str3);
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
          FFSDebug.log("****", str1, "failed to close ResultSet", localException.toString(), 0);
        }
      }
    }
  }
  
  public static String getExtTrnAcctIdByAcctRtnCustStatus(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.getExtTrnAcctIdByAcctRtnCustStatus: ";
    FFSDebug.log(str1, "start ...", 6);
    FFSResultSet localFFSResultSet = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    if (paramExtTransferAcctInfo == null)
    {
      localObject1 = "failed: Null ExtTransferAcctInfo Object passed";
      FFSDebug.log("******", str1, (String)localObject1, 0);
      paramExtTransferAcctInfo = new ExtTransferAcctInfo();
      paramExtTransferAcctInfo.setStatusCode(16000);
      localObject2 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg((String)localObject2);
      return null;
    }
    str3 = paramExtTransferAcctInfo.getAcctNum();
    str4 = paramExtTransferAcctInfo.getAcctType();
    str5 = paramExtTransferAcctInfo.getAcctBankRtn();
    str6 = paramExtTransferAcctInfo.getCustomerId();
    str7 = paramExtTransferAcctInfo.getStatus();
    str8 = paramExtTransferAcctInfo.getAcctScope();
    Object localObject1 = new StringBuffer("SELECT a.AcctId, a.CustomerId, a.NickName, a.AcctNum, a.AcctType, a.AcctBankRtn, a.AcctScope, a.AcctDest, a.AcctCategory, a.BankRtnType, a.RecipientId, a.RecipientType, a.RecipientName, a.Prenote, a.PrenoteStatus, a.PrenoteSubDate, a.PrenoteType, a.SubmittedBy, a.CreateDate, a.LogId, a.Status, a.VerifyStatus, a.VerifyFailedCount, a.AgreedToTerms, a.DepositDate, a.PrimaryAcctHolder, a.SecondaryAcctHolder, a.CheckNum FROM ETF_ExternalAcct a  WHERE a.AcctNum = ? AND a.AcctType = ? AND a.AcctBankRtn = ? AND a.CustomerId = ?");
    Object localObject2 = new ArrayList();
    ((ArrayList)localObject2).add(str3);
    ((ArrayList)localObject2).add(str4);
    ((ArrayList)localObject2).add(str5);
    ((ArrayList)localObject2).add(str6);
    if ((str7 != null) && (!str7.equals("")))
    {
      ((StringBuffer)localObject1).append(" AND a.Status = ?");
      ((ArrayList)localObject2).add(str7);
    }
    if ((str8 != null) && (!str8.equals("")))
    {
      ((StringBuffer)localObject1).append(" AND a.AcctScope = ?");
      ((ArrayList)localObject2).add(str8);
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, ((StringBuffer)localObject1).toString(), ((ArrayList)localObject2).toArray());
      if (localFFSResultSet.getNextRow()) {
        str2 = localFFSResultSet.getColumnString("AcctId");
      }
      FFSDebug.log("***", str1, "End", 6);
      String str9 = str2;
      return str9;
    }
    catch (Throwable localThrowable)
    {
      String str10 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("****", str1, str10, 0);
      throw new FFSException(localThrowable, str10);
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
          FFSDebug.log("****", str1, "failed to close ResultSet", localException.toString(), 0);
        }
      }
    }
  }
  
  public static ExtTransferAcctList getExternalTransferAccountList(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctList paramExtTransferAcctList)
    throws FFSException
  {
    return getExternalTransferAccountList(paramFFSConnectionHolder, paramExtTransferAcctList, true);
  }
  
  public static ExtTransferAcctList getExternalTransferAccountList(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctList paramExtTransferAcctList, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.getExternalTransferAccountList: ";
    FFSDebug.log(str1, "start ...", 6);
    FFSResultSet localFFSResultSet = null;
    String str2 = null;
    String[] arrayOfString = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    String str9 = null;
    String str10 = null;
    StringBuffer localStringBuffer = null;
    ArrayList localArrayList1 = null;
    Object[] arrayOfObject = null;
    ArrayList localArrayList2 = null;
    ExtTransferAcctInfo localExtTransferAcctInfo = null;
    Object localObject1;
    String str11;
    if (paramExtTransferAcctList == null)
    {
      localObject1 = "failed: Null ExtTransferAcctList Object passed";
      FFSDebug.log("******", str1, (String)localObject1, 0);
      paramExtTransferAcctList = new ExtTransferAcctList();
      paramExtTransferAcctList.setStatusCode(16000);
      str11 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctList" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctList.setStatusMsg(str11);
      return paramExtTransferAcctList;
    }
    FFSDebug.log(str1, "FIID=" + paramExtTransferAcctList.getFIId(), ", CustomerId=" + paramExtTransferAcctList.getCustomerId(), ", AcctBankRtn=" + paramExtTransferAcctList.getAcctBankRtn(), ", recipientId =" + paramExtTransferAcctList.getRecipientId(), ", recipientName =" + paramExtTransferAcctList.getRecipientName(), ", recipientType =" + paramExtTransferAcctList.getRecipientType(), ", prenoteStatus =" + paramExtTransferAcctList.getPrenoteStatus(), ", nickName =" + paramExtTransferAcctList.getNickName(), ", acctNum =" + paramExtTransferAcctList.getAcctNum(), ", acctType =" + paramExtTransferAcctList.getAcctType(), 6);
    localArrayList1 = new ArrayList();
    localStringBuffer = new StringBuffer("SELECT a.AcctId, a.CustomerId, a.NickName, a.AcctNum, a.AcctType, a.AcctBankRtn, a.AcctScope, a.AcctDest, a.AcctCategory, a.BankRtnType, a.RecipientId, a.RecipientType, a.RecipientName, a.Prenote, a.PrenoteStatus, a.PrenoteSubDate, a.PrenoteType, a.SubmittedBy, a.CreateDate, a.LogId, a.Status, a.VerifyStatus, a.VerifyFailedCount, a.AgreedToTerms, a.DepositDate, a.PrimaryAcctHolder, a.SecondaryAcctHolder, a.CheckNum FROM ETF_ExternalAcct a ");
    str2 = paramExtTransferAcctList.getFIId();
    if ((str2 != null) && (str2.length() > 0))
    {
      localStringBuffer.append(", BPW_Customer b WHERE a.CustomerId = b.CustomerID ");
      localStringBuffer.append(" AND b.FIID = ?");
      localArrayList1.add(str2);
    }
    arrayOfString = paramExtTransferAcctList.getCustomerId();
    if ((arrayOfString != null) && (arrayOfString.length > 0))
    {
      if (localArrayList1.size() == 0) {
        localStringBuffer.append(" WHERE ");
      } else {
        localStringBuffer.append(" AND ");
      }
      if (arrayOfString.length == 1)
      {
        localStringBuffer.append(" a.CustomerId = ? ");
        localArrayList1.add(arrayOfString[0]);
      }
      else
      {
        DBUtil.appendArrayToCondition(localStringBuffer, arrayOfString, " a.CustomerId IN (", localArrayList1);
      }
    }
    str3 = paramExtTransferAcctList.getAcctBankRtn();
    if ((str3 != null) && (str3.trim().length() > 0))
    {
      if (localArrayList1.size() == 0) {
        localStringBuffer.append(" WHERE ");
      } else {
        localStringBuffer.append(" AND ");
      }
      localStringBuffer.append(" a.AcctBankRtn = ? ");
      localArrayList1.add(str3);
    }
    str4 = paramExtTransferAcctList.getRecipientId();
    if ((str4 != null) && (str4.trim().length() > 0))
    {
      if (localArrayList1.size() == 0) {
        localStringBuffer.append(" WHERE ");
      } else {
        localStringBuffer.append(" AND ");
      }
      localStringBuffer.append(" a.RecipientId = ? ");
      localArrayList1.add(str4);
    }
    str5 = paramExtTransferAcctList.getRecipientName();
    if ((str5 != null) && (str5.trim().length() > 0))
    {
      if (localArrayList1.size() == 0) {
        localStringBuffer.append(" WHERE ");
      } else {
        localStringBuffer.append(" AND ");
      }
      localStringBuffer.append(" a.RecipientName = ? ");
      localArrayList1.add(str5);
    }
    str6 = paramExtTransferAcctList.getRecipientType();
    if ((str6 != null) && (str6.trim().length() > 0))
    {
      if (localArrayList1.size() == 0) {
        localStringBuffer.append(" WHERE ");
      } else {
        localStringBuffer.append(" AND ");
      }
      localStringBuffer.append(" a.RecipientType = ? ");
      localArrayList1.add(str6);
    }
    str7 = paramExtTransferAcctList.getPrenoteStatus();
    if ((str7 != null) && (str7.trim().length() > 0))
    {
      if (localArrayList1.size() == 0) {
        localStringBuffer.append(" WHERE ");
      } else {
        localStringBuffer.append(" AND ");
      }
      localStringBuffer.append(" a.PrenoteStatus = ? ");
      localArrayList1.add(str7);
    }
    str8 = paramExtTransferAcctList.getNickName();
    if ((str8 != null) && (str8.trim().length() > 0))
    {
      localObject1 = new StringBuffer();
      if (localArrayList1.size() == 0) {
        localStringBuffer.append(" WHERE ");
      } else {
        localStringBuffer.append(" AND ");
      }
      localStringBuffer.append(" a.NickName_LWRCase LIKE ? ");
      ((StringBuffer)localObject1).append(str8.toLowerCase());
      ((StringBuffer)localObject1).append("%");
      localArrayList1.add(((StringBuffer)localObject1).toString());
    }
    str9 = paramExtTransferAcctList.getAcctNum();
    if ((str9 != null) && (str9.trim().length() > 0))
    {
      localObject1 = new StringBuffer();
      if (localArrayList1.size() == 0) {
        localStringBuffer.append(" WHERE ");
      } else {
        localStringBuffer.append(" AND ");
      }
      localStringBuffer.append(" a.AcctNum Like ? ");
      ((StringBuffer)localObject1).append(str9);
      ((StringBuffer)localObject1).append("%");
      localArrayList1.add(((StringBuffer)localObject1).toString());
    }
    str10 = paramExtTransferAcctList.getAcctType();
    if ((str10 != null) && (str10.trim().length() > 0))
    {
      if (localArrayList1.size() == 0) {
        localStringBuffer.append(" WHERE ");
      } else {
        localStringBuffer.append(" AND ");
      }
      localStringBuffer.append(" a.AcctType = ? ");
      localArrayList1.add(str10);
    }
    localStringBuffer.append(" AND a.Status <> '");
    localStringBuffer.append("CANCELEDON");
    localStringBuffer.append("' ");
    a(localStringBuffer, paramBoolean);
    try
    {
      arrayOfObject = localArrayList1.toArray(new Object[0]);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      localArrayList2 = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        localExtTransferAcctInfo = new ExtTransferAcctInfo();
        a(localFFSResultSet, localExtTransferAcctInfo);
        localExtTransferAcctInfo.setStatusCode(0);
        localExtTransferAcctInfo.setStatusMsg("Success");
        localArrayList2.add(localExtTransferAcctInfo);
      }
      if (localArrayList2.size() == 0)
      {
        paramExtTransferAcctList.setStatusCode(16020);
        paramExtTransferAcctList.setStatusMsg("ExtTransferAcctInfo  record not found");
      }
      else
      {
        paramExtTransferAcctList.setExtTransferAccts((ExtTransferAcctInfo[])localArrayList2.toArray(new ExtTransferAcctInfo[0]));
        paramExtTransferAcctList.setStatusCode(0);
        paramExtTransferAcctList.setStatusMsg("Success");
      }
      FFSDebug.log("***", str1, "End", 6);
      localObject1 = paramExtTransferAcctList;
      return localObject1;
    }
    catch (Throwable localThrowable)
    {
      str11 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("****", str1, str11, 0);
      throw new FFSException(localThrowable, str11);
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
          FFSDebug.log("****", str1, "failed to close ResultSet", localException.toString(), 0);
        }
      }
    }
  }
  
  private static void a(FFSResultSet paramFFSResultSet, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    paramExtTransferAcctInfo.setAcctId(paramFFSResultSet.getColumnString("AcctId"));
    paramExtTransferAcctInfo.setCustomerId(paramFFSResultSet.getColumnString("CustomerId"));
    paramExtTransferAcctInfo.setNickName(paramFFSResultSet.getColumnString("NickName"));
    paramExtTransferAcctInfo.setAcctNum(paramFFSResultSet.getColumnString("AcctNum"));
    paramExtTransferAcctInfo.setAcctType(paramFFSResultSet.getColumnString("AcctType"));
    paramExtTransferAcctInfo.setAcctBankRtn(paramFFSResultSet.getColumnString("AcctBankRtn"));
    paramExtTransferAcctInfo.setAcctScope(paramFFSResultSet.getColumnString("AcctScope"));
    paramExtTransferAcctInfo.setAcctDest(paramFFSResultSet.getColumnString("AcctDest"));
    paramExtTransferAcctInfo.setAcctCategory(paramFFSResultSet.getColumnString("AcctCategory"));
    paramExtTransferAcctInfo.setBankRtnType(paramFFSResultSet.getColumnString("BankRtnType"));
    paramExtTransferAcctInfo.setRecipientId(paramFFSResultSet.getColumnString("RecipientId"));
    paramExtTransferAcctInfo.setRecipientType(paramFFSResultSet.getColumnString("RecipientType"));
    paramExtTransferAcctInfo.setRecipientName(paramFFSResultSet.getColumnString("RecipientName"));
    paramExtTransferAcctInfo.setPrenote(paramFFSResultSet.getColumnString("Prenote"));
    paramExtTransferAcctInfo.setPrenoteStatus(paramFFSResultSet.getColumnString("PrenoteStatus"));
    paramExtTransferAcctInfo.setPrenoteSubDate(paramFFSResultSet.getColumnString("PrenoteSubDate"));
    paramExtTransferAcctInfo.setPrenoteType(paramFFSResultSet.getColumnString("PrenoteType"));
    paramExtTransferAcctInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    paramExtTransferAcctInfo.setCreateDate(paramFFSResultSet.getColumnString("CreateDate"));
    paramExtTransferAcctInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    paramExtTransferAcctInfo.setStatus(paramFFSResultSet.getColumnString("Status"));
    paramExtTransferAcctInfo.setAgreedToTerms(paramFFSResultSet.getColumnString("AgreedToTerms"));
    paramExtTransferAcctInfo.setVerifyStatus(paramFFSResultSet.getColumnInt("VerifyStatus"));
    paramExtTransferAcctInfo.setVerifyFailedCount(paramFFSResultSet.getColumnInt("VerifyFailedCount"));
    paramExtTransferAcctInfo.setDepositDate(paramFFSResultSet.getColumnString("DepositDate"));
    paramExtTransferAcctInfo.setPrimaryAcctHolder(paramFFSResultSet.getColumnString("PrimaryAcctHolder"));
    paramExtTransferAcctInfo.setSecondaryAcctHolder(paramFFSResultSet.getColumnString("SecondaryAcctHolder"));
    paramExtTransferAcctInfo.setCheckNumber(paramFFSResultSet.getColumnString("CheckNum"));
  }
  
  private static boolean a(ExtTransferAcctInfo paramExtTransferAcctInfo, boolean paramBoolean)
    throws BPWException
  {
    String str1 = "ExternalTransferAccount.validateETFAcctInfo: ";
    FFSDebug.log(str1, "Starts", 6);
    FFSDebug.log(str1, "validateRecipient=" + paramBoolean + ", " + paramExtTransferAcctInfo.toString(), 6);
    String str2;
    if ((paramExtTransferAcctInfo.getAcctNum() == null) || (paramExtTransferAcctInfo.getAcctNum().trim().length() == 0))
    {
      paramExtTransferAcctInfo.setStatusCode(16010);
      str2 = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "Account Number" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return false;
    }
    if ((paramExtTransferAcctInfo.getAcctType() == null) || (paramExtTransferAcctInfo.getAcctType().trim().length() == 0))
    {
      paramExtTransferAcctInfo.setStatusCode(16010);
      str2 = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "Account Type" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return false;
    }
    if ((paramExtTransferAcctInfo.getAcctBankRtn() == null) || (paramExtTransferAcctInfo.getAcctBankRtn().trim().length() == 0))
    {
      paramExtTransferAcctInfo.setStatusCode(16010);
      str2 = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "Account Bank RTN" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return false;
    }
    if ((paramBoolean) && (!"SWIFT".equals(paramExtTransferAcctInfo.getBankRtnType())))
    {
      if ((paramExtTransferAcctInfo.getRecipientId() == null) || (paramExtTransferAcctInfo.getRecipientId().trim().length() == 0))
      {
        paramExtTransferAcctInfo.setStatusCode(16010);
        str2 = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "Recipient Id" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg(str2);
        FFSDebug.log(str1, str2, 0);
        return false;
      }
      if ((paramExtTransferAcctInfo.getRecipientType() == null) || (paramExtTransferAcctInfo.getRecipientType().trim().length() == 0))
      {
        paramExtTransferAcctInfo.setStatusCode(16010);
        str2 = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "Recipient Type" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg(str2);
        FFSDebug.log(str1, str2, 0);
        return false;
      }
      if ((paramExtTransferAcctInfo.getRecipientName() == null) || (paramExtTransferAcctInfo.getRecipientName().trim().length() == 0))
      {
        paramExtTransferAcctInfo.setStatusCode(16010);
        str2 = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "Recipient Name" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg(str2);
        FFSDebug.log(str1, str2, 0);
        return false;
      }
    }
    if ((paramExtTransferAcctInfo.getSubmittedBy() == null) || (paramExtTransferAcctInfo.getSubmittedBy().trim().length() == 0))
    {
      paramExtTransferAcctInfo.setStatusCode(16010);
      str2 = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "SubmittedBy" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return false;
    }
    FFSDebug.log(str1, "Prenote" + paramExtTransferAcctInfo.getPrenote(), 6);
    if ((paramExtTransferAcctInfo.getPrenote() == null) || (paramExtTransferAcctInfo.getPrenote().compareTo("N") == 0)) {
      paramExtTransferAcctInfo.setPrenoteStatus(null);
    }
    FFSDebug.log(str1, "Done", 6);
    return true;
  }
  
  public static ExtTransferAcctInfo cancel(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    return cancel(paramFFSConnectionHolder, paramExtTransferAcctInfo, true);
  }
  
  public static ExtTransferAcctInfo cancel(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.cancel: ";
    FFSDebug.log("***", str1, "start ...", 6);
    StringBuffer localStringBuffer = null;
    String str2;
    if (paramExtTransferAcctInfo == null)
    {
      localObject = "failed: External Transfer Account info Object passed is null";
      FFSDebug.log("****", str1, (String)localObject, 0);
      paramExtTransferAcctInfo = new ExtTransferAcctInfo();
      paramExtTransferAcctInfo.setStatusCode(16000);
      str2 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg(str2);
      return paramExtTransferAcctInfo;
    }
    if (paramExtTransferAcctInfo.getAcctId() == null)
    {
      localObject = "***ExternalTransferAccount.delete failed: AcctId is null";
      FFSDebug.log((String)localObject, 0);
      paramExtTransferAcctInfo.setStatusCode(16010);
      str2 = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "AcctId" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg(str2);
      return paramExtTransferAcctInfo;
    }
    Object localObject = { "CANCELEDON", paramExtTransferAcctInfo.getAcctId() };
    try
    {
      localStringBuffer = new StringBuffer("Update ETF_ExternalAcct set Status = ?  WHERE AcctId = ? ");
      a(localStringBuffer, paramBoolean);
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject);
      FFSDebug.log(str1, "Number of ExtTransferAcctInfo records closed " + i, 6);
      FFSDebug.log("***", str1, "End", 6);
      if (i == 0)
      {
        paramExtTransferAcctInfo.setStatusCode(16020);
        paramExtTransferAcctInfo.setStatusMsg("ExtTransferAcctInfo  record not found");
      }
      else if (i > 1)
      {
        paramExtTransferAcctInfo.setStatusCode(23270);
        paramExtTransferAcctInfo.setStatusMsg("More than One Record found For AcctId =" + paramExtTransferAcctInfo.getAcctId());
      }
      else
      {
        paramExtTransferAcctInfo.setStatusCode(0);
        paramExtTransferAcctInfo.setStatusMsg("Success");
      }
      return paramExtTransferAcctInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("****", str1, str3, 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  public static ExtTransferAcctInfo modify(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    return modify(paramFFSConnectionHolder, paramExtTransferAcctInfo, true);
  }
  
  public static ExtTransferAcctInfo modify(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.modify: ";
    FFSDebug.log(str1, "start ...", 6);
    StringBuffer localStringBuffer = null;
    String str2 = null;
    String str3 = null;
    ExtTransferAcctInfo localExtTransferAcctInfo = null;
    try
    {
      String str4;
      if (paramExtTransferAcctInfo == null)
      {
        str4 = "failed: External Transfer Account info Object passed is null";
        FFSDebug.log("****", str1, str4, 0);
        paramExtTransferAcctInfo = new ExtTransferAcctInfo();
        paramExtTransferAcctInfo.setStatusCode(16000);
        str5 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg(str5);
        return paramExtTransferAcctInfo;
      }
      if (paramExtTransferAcctInfo.getAcctId() == null)
      {
        str4 = "failed: AcctId is null";
        FFSDebug.log("**** ", str1, str4, 0);
        paramExtTransferAcctInfo.setStatusCode(16010);
        str5 = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "AcctId" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg(str5);
        return paramExtTransferAcctInfo;
      }
      if ((paramExtTransferAcctInfo.getAcctScope() == null) || (paramExtTransferAcctInfo.getAcctScope().trim().length() == 0)) {
        paramExtTransferAcctInfo.setAcctScope("MANAGED");
      }
      if ((paramExtTransferAcctInfo.getBankRtnType() == null) || (paramExtTransferAcctInfo.getBankRtnType().trim().length() == 0)) {
        paramExtTransferAcctInfo.setBankRtnType("FEDABA");
      }
      if ((paramExtTransferAcctInfo.getAcctDest() == null) || (paramExtTransferAcctInfo.getAcctDest().trim().length() == 0)) {
        paramExtTransferAcctInfo.setAcctDest("EXTERNAL");
      }
      boolean bool = paramExtTransferAcctInfo.getAcctDest().equals("EXTERNAL");
      if (!a(paramExtTransferAcctInfo, bool))
      {
        str5 = "failed: ";
        FFSDebug.log("***", str1, str5, paramExtTransferAcctInfo.getStatusMsg(), 0);
        return paramExtTransferAcctInfo;
      }
      if (!jdMethod_try(paramExtTransferAcctInfo))
      {
        FFSDebug.log("****", str1, paramExtTransferAcctInfo.getStatusMsg(), 0);
        return paramExtTransferAcctInfo;
      }
      if ((bool) && (!jdMethod_new(paramExtTransferAcctInfo)))
      {
        str5 = "failed. ";
        FFSDebug.log("****", str1, str5, paramExtTransferAcctInfo.getStatusMsg(), 0);
        return paramExtTransferAcctInfo;
      }
      if (!jdMethod_int(paramExtTransferAcctInfo))
      {
        str5 = "failed. ";
        FFSDebug.log("****", str1, str5, paramExtTransferAcctInfo.getStatusMsg(), 0);
        return paramExtTransferAcctInfo;
      }
      if (!jdMethod_if(paramFFSConnectionHolder, paramExtTransferAcctInfo))
      {
        str5 = "*** ExternalTransferAccount.modifyExternal account failed: ";
        FFSDebug.log(str5, paramExtTransferAcctInfo.getStatusMsg(), 0);
        return paramExtTransferAcctInfo;
      }
      str5 = paramExtTransferAcctInfo.getAcctScope();
      if (str5.equalsIgnoreCase("MANAGED"))
      {
        str2 = getExtTrnAcctIdByAcctRtnCustStatus(paramFFSConnectionHolder, paramExtTransferAcctInfo);
        if ((str2 != null) && (!str2.equals(paramExtTransferAcctInfo.getAcctId())))
        {
          paramExtTransferAcctInfo.setStatusCode(19700);
          paramExtTransferAcctInfo.setStatusMsg("Duplicate record.");
          localObject = "failed: ";
          FFSDebug.log("******", str1, (String)localObject, paramExtTransferAcctInfo.getStatusMsg(), 0);
          return paramExtTransferAcctInfo;
        }
      }
      localExtTransferAcctInfo = new ExtTransferAcctInfo();
      localExtTransferAcctInfo.setAcctId(paramExtTransferAcctInfo.getAcctId());
      localExtTransferAcctInfo = getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo, paramBoolean, true);
      if (localExtTransferAcctInfo.getStatusCode() != 0)
      {
        paramExtTransferAcctInfo.setStatusCode(localExtTransferAcctInfo.getStatusCode());
        paramExtTransferAcctInfo.setStatusMsg(localExtTransferAcctInfo.getStatusMsg());
        return paramExtTransferAcctInfo;
      }
      if ((paramExtTransferAcctInfo.getPrenote() != null) && (paramExtTransferAcctInfo.getPrenote().trim().equalsIgnoreCase("Y")) && (a(localExtTransferAcctInfo, paramExtTransferAcctInfo))) {
        paramExtTransferAcctInfo.setPrenoteStatus(null);
      }
      if ((paramExtTransferAcctInfo.getNickName() != null) && (paramExtTransferAcctInfo.getNickName().trim().length() != 0)) {
        str3 = paramExtTransferAcctInfo.getNickName().toLowerCase();
      }
      localStringBuffer = new StringBuffer("Update ETF_ExternalAcct set AcctNum = ?, AcctType = ?, AcctBankRtn = ?, AcctScope = ?, AcctDest = ?, AcctCategory = ?, BankRtnType = ?, RecipientId = ?, RecipientType = ?, RecipientName = ?, Prenote = ?, PrenoteType = ?, NickName = ?, NickName_LWRCase = ?, PrimaryAcctHolder = ?, SecondaryAcctHolder = ?, CheckNum = ? WHERE AcctId = ? ");
      a(localStringBuffer, paramBoolean);
      Object localObject = { paramExtTransferAcctInfo.getAcctNum(), paramExtTransferAcctInfo.getAcctType(), paramExtTransferAcctInfo.getAcctBankRtn(), paramExtTransferAcctInfo.getAcctScope(), paramExtTransferAcctInfo.getAcctDest(), paramExtTransferAcctInfo.getAcctCategory(), paramExtTransferAcctInfo.getBankRtnType(), paramExtTransferAcctInfo.getRecipientId(), paramExtTransferAcctInfo.getRecipientType(), paramExtTransferAcctInfo.getRecipientName(), paramExtTransferAcctInfo.getPrenote(), paramExtTransferAcctInfo.getPrenoteType(), paramExtTransferAcctInfo.getNickName(), str3, paramExtTransferAcctInfo.getPrimaryAcctHolder(), paramExtTransferAcctInfo.getSecondaryAcctHolder(), paramExtTransferAcctInfo.getCheckNumber(), paramExtTransferAcctInfo.getAcctId() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject);
      FFSDebug.log(str1, "Number of ExtTransferAcctInfo records updated " + i, 6);
      if (i == 0)
      {
        paramExtTransferAcctInfo.setStatusCode(16020);
        paramExtTransferAcctInfo.setStatusMsg("ExtTransferAcctInfo  record not found");
      }
      else
      {
        paramExtTransferAcctInfo.setStatusCode(0);
        paramExtTransferAcctInfo.setStatusMsg("Success");
      }
      FFSDebug.log(str1, "End", 6);
      return paramExtTransferAcctInfo;
    }
    catch (Throwable localThrowable)
    {
      String str5 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str5, 0);
      throw new FFSException(localThrowable, str5);
    }
  }
  
  public static ExtTransferAcctInfo modifyPrenote(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.modifyPrenote: ";
    FFSDebug.log(str1, "start ...", 6);
    StringBuffer localStringBuffer = null;
    try
    {
      String str2;
      if (paramExtTransferAcctInfo == null)
      {
        str2 = "failed: External Transfer Account info Object passed is null";
        FFSDebug.log("****", str1, str2, 0);
        paramExtTransferAcctInfo = new ExtTransferAcctInfo();
        paramExtTransferAcctInfo.setStatusCode(16000);
        localObject = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject);
        return paramExtTransferAcctInfo;
      }
      if (paramExtTransferAcctInfo.getAcctId() == null)
      {
        str2 = "failed: AcctId is null";
        FFSDebug.log("**** ", str1, str2, 0);
        paramExtTransferAcctInfo.setStatusCode(16010);
        localObject = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "AcctId" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject);
        return paramExtTransferAcctInfo;
      }
      int i = 0;
      if (paramBoolean)
      {
        localStringBuffer = new StringBuffer("Update ETF_ExternalAcct set PrenoteStatus = ?, PrenoteSubDate = ? WHERE AcctId = ? ");
        localObject = new Object[] { paramExtTransferAcctInfo.getPrenoteStatus(), paramExtTransferAcctInfo.getPrenoteSubDate(), paramExtTransferAcctInfo.getAcctId() };
        i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject);
      }
      else
      {
        localStringBuffer = new StringBuffer("Update ETF_ExternalAcct set PrenoteStatus = ? WHERE AcctId = ? ");
        localObject = new Object[] { paramExtTransferAcctInfo.getPrenoteStatus(), paramExtTransferAcctInfo.getAcctId() };
        i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject);
      }
      FFSDebug.log(str1, "Number of ExtTransferAcctInfo records updated " + i, 6);
      if (i == 0)
      {
        paramExtTransferAcctInfo.setStatusCode(16020);
        paramExtTransferAcctInfo.setStatusMsg("ExtTransferAcctInfo  record not found");
      }
      else
      {
        paramExtTransferAcctInfo.setStatusCode(0);
        paramExtTransferAcctInfo.setStatusMsg("Success");
      }
      FFSDebug.log(str1, "End", 6);
      return paramExtTransferAcctInfo;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, (String)localObject, 0);
      throw new FFSException(localThrowable, (String)localObject);
    }
  }
  
  private static boolean jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.validateCustomer: ";
    FFSDebug.log(str1, "Starts ...", 6);
    if (isBPWManagedCustomer() == true)
    {
      String str2 = paramExtTransferAcctInfo.getCustomerId();
      Object localObject;
      if ((str2 == null) || (str2.trim().length() == 0))
      {
        paramExtTransferAcctInfo.setStatusCode(16010);
        localObject = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "CustomerId" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject);
        FFSDebug.log(str1, (String)localObject, 0);
        return false;
      }
      try
      {
        localObject = Customer.getCustomerByID(str2, paramFFSConnectionHolder);
        if ((localObject == null) || (((CustomerInfo)localObject).getCustomerID() == null))
        {
          paramExtTransferAcctInfo.setStatusCode(19130);
          str3 = BPWLocaleUtil.getMessage(19130, new String[] { str2 }, "TRANSFER_MESSAGE");
          paramExtTransferAcctInfo.setStatusMsg(str3);
          FFSDebug.log(str1, str3, 0);
          return false;
        }
      }
      catch (Throwable localThrowable)
      {
        String str3 = str1 + "failed: " + localThrowable.toString();
        FFSDebug.log(str3, 0);
        throw new FFSException(localThrowable, str3);
      }
    }
    FFSDebug.log(str1, "Done, Customer is valid", 6);
    return true;
  }
  
  private static boolean jdMethod_try(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    try
    {
      String str1 = paramExtTransferAcctInfo.getAcctScope();
      if ((!str1.equalsIgnoreCase("MANAGED")) && (!str1.equalsIgnoreCase("UNMANAGED")))
      {
        str2 = BPWLocaleUtil.getMessage(21610, new String[] { str1 }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusCode(21610);
        paramExtTransferAcctInfo.setStatusMsg(str2);
        return false;
      }
      String str2 = paramExtTransferAcctInfo.getAcctDest();
      if ((!str2.equalsIgnoreCase("INTERNAL")) && (!str2.equalsIgnoreCase("EXTERNAL")))
      {
        str3 = BPWLocaleUtil.getMessage(21620, new String[] { str2 }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusCode(21620);
        paramExtTransferAcctInfo.setStatusMsg(str3);
        return false;
      }
      String str3 = paramExtTransferAcctInfo.getBankRtnType();
      if ((!str3.equalsIgnoreCase("FEDABA")) && (!str3.equalsIgnoreCase("SWIFT")) && (!str3.equalsIgnoreCase("CHIPS")) && (!str3.equalsIgnoreCase("OTHER")))
      {
        String str4 = BPWLocaleUtil.getMessage(21630, new String[] { str3 }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusCode(21630);
        paramExtTransferAcctInfo.setStatusMsg(str4);
        return false;
      }
    }
    catch (Exception localException) {}
    return true;
  }
  
  private static boolean jdMethod_new(ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws BPWException
  {
    String str1 = "ExternalTransferAccount.validateRecipientType: ";
    FFSDebug.log(str1, "Starts ...", 6);
    FFSDebug.log(str1, ", RecipientType = ", paramExtTransferAcctInfo.getRecipientType(), 6);
    String str2 = paramExtTransferAcctInfo.getRecipientType();
    if (!"SWIFT".equals(paramExtTransferAcctInfo.getBankRtnType()))
    {
      if ("BUSINESS".equalsIgnoreCase(str2))
      {
        paramExtTransferAcctInfo.setRecipientType("BUSINESS");
      }
      else if ("PERSONAL".equalsIgnoreCase(str2))
      {
        paramExtTransferAcctInfo.setRecipientType("PERSONAL");
      }
      else
      {
        paramExtTransferAcctInfo.setStatusCode(21130);
        String str3 = BPWLocaleUtil.getMessage(21130, null, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg(str3);
        FFSDebug.log(str1, str3, 0);
        return false;
      }
    }
    else {
      FFSDebug.log(str1, "SWIFT needs no validation", 6);
    }
    FFSDebug.log(str1, "Done", 6);
    return true;
  }
  
  public static ExtTransferAcctInfo[] getExtTransferAcctWithUnpostedPrenoteByCustomerId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    return getExtTransferAcctWithUnpostedPrenoteByCustomerId(paramFFSConnectionHolder, paramString, true);
  }
  
  public static ExtTransferAcctInfo[] getExtTransferAcctWithUnpostedPrenoteByCustomerId(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.getExtTransferAcctWithPrenoteByCustomerId: ";
    FFSDebug.log(str1 + "start. CustomerId=" + paramString, 6);
    ArrayList localArrayList = new ArrayList();
    StringBuffer localStringBuffer = new StringBuffer("SELECT a.AcctId, a.CustomerId, a.NickName, a.AcctNum, a.AcctType, a.AcctBankRtn, a.AcctScope, a.AcctDest, a.AcctCategory, a.BankRtnType, a.RecipientId, a.RecipientType, a.RecipientName, a.Prenote, a.PrenoteStatus, a.PrenoteSubDate, a.PrenoteType, a.SubmittedBy, a.CreateDate, a.LogId, a.Status, a.VerifyStatus, a.VerifyFailedCount, a.AgreedToTerms, a.DepositDate, a.PrimaryAcctHolder, a.SecondaryAcctHolder, a.CheckNum FROM ETF_ExternalAcct a  WHERE a.CustomerId = ? AND a.Prenote = 'Y' AND ( a.PrenoteStatus is null OR   a.PrenoteStatus <> 'Success') AND a.Status <> 'CANCELEDON'");
    a(localStringBuffer, paramBoolean);
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      ExtTransferAcctInfo localExtTransferAcctInfo;
      while (localFFSResultSet.getNextRow())
      {
        localExtTransferAcctInfo = new ExtTransferAcctInfo();
        a(localFFSResultSet, localExtTransferAcctInfo);
        localExtTransferAcctInfo.setStatusCode(0);
        localExtTransferAcctInfo.setStatusMsg("Success");
        localArrayList.add(localExtTransferAcctInfo);
      }
      FFSDebug.log("***", str1, "End", 6);
      if (localArrayList.size() == 0)
      {
        localExtTransferAcctInfo = null;
        return localExtTransferAcctInfo;
      }
    }
    catch (Throwable localThrowable)
    {
      String str2 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("****", str1, str2, 0);
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
          FFSDebug.log("****", str1, "failed to close ResultSet", localException.toString(), 0);
        }
      }
    }
    return (ExtTransferAcctInfo[])localArrayList.toArray(new ExtTransferAcctInfo[0]);
  }
  
  private static boolean jdMethod_int(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    boolean bool = true;
    FFSDebug.log("ExternalTransferAccount.validateBankRTN: AcctBankRtn=", paramExtTransferAcctInfo.getAcctBankRtn(), 6);
    if ((paramExtTransferAcctInfo.getBankRtnType().equals("FEDABA")) && (!CommBankIdentifier.getBankIdentifierFlag())) {
      try
      {
        bool = BPWUtil.validateABA(paramExtTransferAcctInfo.getAcctBankRtn());
        FFSDebug.log("ExternalTransferAccount.validateBankRTN: result=" + bool, 6);
        if (bool)
        {
          paramExtTransferAcctInfo.setStatusCode(0);
          paramExtTransferAcctInfo.setStatusMsg("Success");
        }
        else
        {
          paramExtTransferAcctInfo.setStatusCode(23670);
          paramExtTransferAcctInfo.setStatusMsg("Invalid BankRTN");
        }
      }
      catch (FFSException localFFSException)
      {
        paramExtTransferAcctInfo.setStatusCode(23670);
        paramExtTransferAcctInfo.setStatusMsg("Invalid BankRTN");
        FFSDebug.log("Invalid BankRTN. Exception: " + localFFSException.toString(), 0);
        return false;
      }
    }
    return bool;
  }
  
  private static boolean a(ExtTransferAcctInfo paramExtTransferAcctInfo1, ExtTransferAcctInfo paramExtTransferAcctInfo2)
  {
    return (!paramExtTransferAcctInfo1.getAcctType().equals(paramExtTransferAcctInfo2.getAcctType())) || (!paramExtTransferAcctInfo1.getAcctNum().equals(paramExtTransferAcctInfo2.getAcctNum())) || (!paramExtTransferAcctInfo1.getAcctBankRtn().equals(paramExtTransferAcctInfo2.getAcctBankRtn())) || (!paramExtTransferAcctInfo1.getRecipientId().equals(paramExtTransferAcctInfo2.getRecipientId())) || (!paramExtTransferAcctInfo1.getRecipientType().equals(paramExtTransferAcctInfo2.getRecipientType())) || (!paramExtTransferAcctInfo1.getRecipientName().equals(paramExtTransferAcctInfo2.getRecipientName()));
  }
  
  public static int updateMaturedExtAcctPrenoteStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.updateMaturedExtAcctPrenoteStatus: ";
    FFSDebug.log(str1 + "start. matureDateStr : " + paramString2, 6);
    String str2 = "UPDATE ETF_ExternalAcct  SET PrenoteStatus = 'Success' WHERE ( (CustomerId IN (SELECT CustomerID FROM BPW_Customer b WHERE b.FIID = ?)) OR (AcctId IN (SELECT AccountFromId FROM BPW_Transfer c  WHERE c.FIId = ?)) OR (AcctId IN (SELECT ExternalAcctId FROM BPW_Transfer d WHERE  d.FIId = ?)) ) AND PrenoteStatus = 'Pending' AND PrenoteSubDate IS NOT NULL  AND PrenoteSubDate < ?";
    Object[] arrayOfObject = { paramString1, paramString1, paramString1, paramString2 };
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
  
  public static ExtTransferAcctInfo[] getMaturedExtTransferAcctInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    return getMaturedExtTransferAcctInfo(paramFFSConnectionHolder, paramString1, paramString2, true);
  }
  
  public static ExtTransferAcctInfo[] getMaturedExtTransferAcctInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.getMaturedExtTransferAcctInfo: ";
    FFSDebug.log(str1 + "start. MatureDate =" + paramString2, 6);
    ArrayList localArrayList = new ArrayList();
    if (paramString2 == null)
    {
      localObject1 = "***" + str1 + " failed: matureDate is null";
      FFSDebug.log((String)localObject1, 0);
      return null;
    }
    Object localObject1 = null;
    ExtTransferAcctInfo localExtTransferAcctInfo = null;
    StringBuffer localStringBuffer = new StringBuffer("SELECT a.AcctId, a.CustomerId, a.NickName, a.AcctNum, a.AcctType, a.AcctBankRtn, a.AcctScope, a.AcctDest, a.AcctCategory, a.BankRtnType, a.RecipientId, a.RecipientType, a.RecipientName, a.Prenote, a.PrenoteStatus, a.PrenoteSubDate, a.PrenoteType, a.SubmittedBy, a.CreateDate, a.LogId, a.Status, a.VerifyStatus, a.VerifyFailedCount, a.AgreedToTerms, a.DepositDate, a.PrimaryAcctHolder, a.SecondaryAcctHolder, a.CheckNum FROM ETF_ExternalAcct a , BPW_Customer b, BPW_Transfer c WHERE ( (a.CustomerId = b.CustomerID AND b.FIID = ?) OR   ( (a.AcctId = c.ExternalAcctId OR a.AcctId = c.AccountFromId) AND c.FIId = ? ) )   AND a.PrenoteStatus = 'Pending' AND a.PrenoteSubDate IS NOT NULL AND PrenoteSubDate < ?");
    a(localStringBuffer, paramBoolean);
    Object[] arrayOfObject = { paramString1, paramString1, paramString2 };
    try
    {
      localObject1 = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      while (((FFSResultSet)localObject1).getNextRow())
      {
        localExtTransferAcctInfo = new ExtTransferAcctInfo();
        a((FFSResultSet)localObject1, localExtTransferAcctInfo);
        localExtTransferAcctInfo.setStatusCode(0);
        localExtTransferAcctInfo.setStatusMsg("Success");
        localArrayList.add(localExtTransferAcctInfo);
      }
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** " + str1 + " failed: ";
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str2 = "*** " + str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localException1, str2);
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
    FFSDebug.log(str1 + " end. Number of ext accounts : " + localArrayList.size(), 6);
    return (ExtTransferAcctInfo[])localArrayList.toArray(new ExtTransferAcctInfo[0]);
  }
  
  public static void logExtTransferAccountTransAuditLog(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, ILocalizable paramILocalizable, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "ExtTransferAcctInfo.logExtTransferAccountTransAuditLog: ";
    FFSDebug.log(str1 + "start ...", 6);
    String str2 = paramExtTransferAcctInfo.getCustomerId();
    BigDecimal localBigDecimal = new BigDecimal(0.0D);
    String str3 = paramString + ", " + paramExtTransferAcctInfo.toString();
    FFSDebug.log(str1 + str3, 6);
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = paramILocalizable;
    arrayOfObject[1] = paramExtTransferAcctInfo.getAcctNum();
    arrayOfObject[2] = paramExtTransferAcctInfo.getAcctType();
    arrayOfObject[3] = paramExtTransferAcctInfo.getAcctBankRtn();
    paramILocalizable = BPWLocaleUtil.getLocalizableMessage(1064, arrayOfObject, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramExtTransferAcctInfo.getSubmittedBy(), null, null, paramILocalizable, paramExtTransferAcctInfo.getLogId(), paramInt, Integer.parseInt(str2), localBigDecimal, null, paramExtTransferAcctInfo.getAcctId(), paramExtTransferAcctInfo.getStatus(), null, null, null, null, 0);
    TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    FFSDebug.log(str1 + "done.", 6);
  }
  
  public static ExtTransferAcctInfo[] getUnMaturedExtTransferAcctInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.getUnMaturedExtTransferAcctInfo: ";
    FFSDebug.log(str1 + "start. FIID =" + paramString + ", excluding unmanaged account =" + paramBoolean, 6);
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    ExtTransferAcctInfo localExtTransferAcctInfo = null;
    StringBuffer localStringBuffer = new StringBuffer("SELECT a.AcctId, a.CustomerId, a.NickName, a.AcctNum, a.AcctType, a.AcctBankRtn, a.AcctScope, a.AcctDest, a.AcctCategory, a.BankRtnType, a.RecipientId, a.RecipientType, a.RecipientName, a.Prenote, a.PrenoteStatus, a.PrenoteSubDate, a.PrenoteType, a.SubmittedBy, a.CreateDate, a.LogId, a.Status, a.VerifyStatus, a.VerifyFailedCount, a.AgreedToTerms, a.DepositDate, a.PrimaryAcctHolder, a.SecondaryAcctHolder, a.CheckNum FROM ETF_ExternalAcct a , BPW_Customer b, BPW_Transfer c  WHERE  ( (a.CustomerId = b.CustomerID AND b.FIID = ?) OR    ( (a.AcctId = c.ExternalAcctId OR a.AcctId = c.AccountFromId) AND c.FIId = ? ) )   AND a.Prenote = 'Y' AND a.PrenoteStatus is null ");
    a(localStringBuffer, paramBoolean);
    Object[] arrayOfObject = { paramString, paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localExtTransferAcctInfo = new ExtTransferAcctInfo();
        a(localFFSResultSet, localExtTransferAcctInfo);
        localExtTransferAcctInfo.setStatusCode(0);
        localExtTransferAcctInfo.setStatusMsg("Success");
        localArrayList.add(localExtTransferAcctInfo);
      }
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** " + str1 + " failed: ";
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str2 = "*** " + str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localException1, str2);
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
    FFSDebug.log(str1 + " end. Number of ext accounts : " + localArrayList.size(), 6);
    return (ExtTransferAcctInfo[])localArrayList.toArray(new ExtTransferAcctInfo[0]);
  }
  
  public static int deleteExternalAccounts(FFSConnectionHolder paramFFSConnectionHolder, String[] paramArrayOfString)
    throws FFSException
  {
    FFSDebug.log("ExternalTransferAccount.deleteExternalAccounts start.", 6);
    String str1 = "DELETE FROM ETF_ExternalAcct WHERE AcctId=?";
    try
    {
      DBUtil.executeStatementBatch(paramFFSConnectionHolder, str1, DBUtil.arrayStringToArrayList(paramArrayOfString));
    }
    catch (Exception localException)
    {
      String str2 = "*** ExternalTransferAccount.deleteExternalAccounts failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("ExternalTransferAccount.deleteExternalAccounts done. Deleted : " + paramArrayOfString.length + " external accounts", 6);
    return paramArrayOfString.length;
  }
  
  private static void a(StringBuffer paramStringBuffer, boolean paramBoolean)
  {
    if ((paramStringBuffer != null) && (paramBoolean))
    {
      paramStringBuffer.append(" and ");
      paramStringBuffer.append("AcctScope");
      paramStringBuffer.append(" = '");
      paramStringBuffer.append("MANAGED");
      paramStringBuffer.append("'");
    }
  }
  
  private static void jdMethod_if(StringBuffer paramStringBuffer, boolean paramBoolean)
  {
    if ((paramStringBuffer != null) && (paramBoolean))
    {
      paramStringBuffer.append(" and a.Status <> '");
      paramStringBuffer.append("CANCELEDON");
      paramStringBuffer.append("'");
    }
  }
  
  public static ExtTransferAcctInfo depositOrVerifyAccount(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.depositOrVerifyAccount: ";
    FFSDebug.log(str1, "start ...", 6);
    StringBuffer localStringBuffer = null;
    try
    {
      String str2;
      if (paramExtTransferAcctInfo == null)
      {
        str2 = "failed: External Transfer Account info Object passed is null";
        FFSDebug.log("****", str1, str2, 0);
        paramExtTransferAcctInfo = new ExtTransferAcctInfo();
        paramExtTransferAcctInfo.setStatusCode(16000);
        localObject = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject);
        return paramExtTransferAcctInfo;
      }
      if (paramExtTransferAcctInfo.getAcctId() == null)
      {
        str2 = "failed: AcctId is null";
        FFSDebug.log("**** ", str1, str2, 0);
        paramExtTransferAcctInfo.setStatusCode(16010);
        localObject = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "AcctId" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject);
        return paramExtTransferAcctInfo;
      }
      int i = 0;
      if (paramBoolean)
      {
        localStringBuffer = new StringBuffer("Update ETF_ExternalAcct set VerifyStatus = ?, VerifyFailedCount = ?, DepositDate = ? WHERE AcctId = ? ");
        paramExtTransferAcctInfo.setVerifyFailedCount(0);
        paramExtTransferAcctInfo.setVerifyStatus(2);
        paramExtTransferAcctInfo.setDepositDate(FFSUtil.getDateString());
        localObject = new Object[] { new Integer(paramExtTransferAcctInfo.getVerifyStatus()), new Integer(paramExtTransferAcctInfo.getVerifyFailedCount()), paramExtTransferAcctInfo.getDepositDate(), paramExtTransferAcctInfo.getAcctId() };
        i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject);
      }
      else
      {
        localObject = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
        int j = 3;
        try
        {
          String str3 = ((PropertyConfig)localObject).otherProperties.getProperty("bpw.external.transfer.account.verify.retries", "3");
          j = new Integer(str3).intValue();
        }
        catch (Exception localException) {}
        int k = paramExtTransferAcctInfo.getVerifyFailedCount() + 1;
        paramExtTransferAcctInfo.setVerifyFailedCount(k);
        Object[] arrayOfObject;
        if (k < j)
        {
          localStringBuffer = new StringBuffer("Update ETF_ExternalAcct set VerifyFailedCount = ? WHERE AcctId = ? ");
          arrayOfObject = new Object[] { new Integer(paramExtTransferAcctInfo.getVerifyFailedCount()), paramExtTransferAcctInfo.getAcctId() };
          i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
        }
        else
        {
          paramExtTransferAcctInfo.setStatus("CANCELEDON");
          localStringBuffer = new StringBuffer("Update ETF_ExternalAcct set Status = ?  WHERE AcctId = ? ");
          arrayOfObject = new Object[] { "CANCELEDON", paramExtTransferAcctInfo.getAcctId() };
          i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
        }
      }
      FFSDebug.log(str1, "Number of ExtTransferAcctInfo records updated " + i, 6);
      if (i == 0)
      {
        paramExtTransferAcctInfo.setStatusCode(16020);
        paramExtTransferAcctInfo.setStatusMsg("ExtTransferAcctInfo  record not found");
      }
      else
      {
        paramExtTransferAcctInfo.setStatusCode(0);
        paramExtTransferAcctInfo.setStatusMsg("Success");
      }
      FFSDebug.log(str1, "End", 6);
      return paramExtTransferAcctInfo;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, (String)localObject, 0);
      throw new FFSException(localThrowable, (String)localObject);
    }
  }
  
  public static ExtTransferAcctInfo activateOrDeactivateAccount(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.activateOrDeactivateAccount: ";
    FFSDebug.log(str1, "start ...", 6);
    StringBuffer localStringBuffer = null;
    try
    {
      String str2;
      if (paramExtTransferAcctInfo == null)
      {
        str2 = "failed: External Transfer Account info Object passed is null";
        FFSDebug.log("****", str1, str2, 0);
        paramExtTransferAcctInfo = new ExtTransferAcctInfo();
        paramExtTransferAcctInfo.setStatusCode(16000);
        localObject = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject);
        return paramExtTransferAcctInfo;
      }
      if (paramExtTransferAcctInfo.getAcctId() == null)
      {
        str2 = "failed: AcctId is null";
        FFSDebug.log("**** ", str1, str2, 0);
        paramExtTransferAcctInfo.setStatusCode(16010);
        localObject = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "AcctId" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject);
        return paramExtTransferAcctInfo;
      }
      int i = 0;
      paramExtTransferAcctInfo.setStatus(paramBoolean ? "ACTIVE" : "INACTIVE");
      localStringBuffer = new StringBuffer("Update ETF_ExternalAcct set Status = ?  WHERE AcctId = ? ");
      localObject = new Object[] { paramBoolean ? "ACTIVE" : "INACTIVE", paramExtTransferAcctInfo.getAcctId() };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject);
      FFSDebug.log(str1, "Number of ExtTransferAcctInfo records updated " + i, 6);
      if (i == 0)
      {
        paramExtTransferAcctInfo.setStatusCode(16020);
        paramExtTransferAcctInfo.setStatusMsg("ExtTransferAcctInfo  record not found");
      }
      else
      {
        paramExtTransferAcctInfo.setStatusCode(0);
        paramExtTransferAcctInfo.setStatusMsg("Success");
      }
      FFSDebug.log(str1, "End", 6);
      return paramExtTransferAcctInfo;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, (String)localObject, 0);
      throw new FFSException(localThrowable, (String)localObject);
    }
  }
  
  public static int[] getDepositedAmounts(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, String paramString)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.getDepsoitedAmounts: ";
    FFSDebug.log(str1, "start ...", 6);
    String str2 = "";
    if (paramExtTransferAcctInfo == null)
    {
      localObject1 = "failed: External Transfer Account info Object passed is null";
      FFSDebug.log("****", str1, (String)localObject1, 0);
      paramExtTransferAcctInfo = new ExtTransferAcctInfo();
      paramExtTransferAcctInfo.setStatusCode(16000);
      localObject2 = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
      paramExtTransferAcctInfo.setStatusMsg((String)localObject2);
      return new int[0];
    }
    Object localObject1 = new ArrayList();
    Object localObject2 = null;
    StringBuffer localStringBuffer = new StringBuffer("SELECT SrvrTId, CustomerId, TransferType, TransferDest, TransferGroup, TransferCategory, BankFromRtn, AccountFromNum, AccountFromType, ExternalAcctId, Amount, AmountCurrency, OrigAmount, OrigCurrency, DateCreate, DateDue, DateToPost, DatePosted, LastProcessId, Memo, TemplateScope, TemplateNickName, SourceTemplateId, SourceRecSrvrTId, Status, SubmittedBy, LogId, ProcessLeadNumber, FIId, AccountFromId, ProcessDate, OriginatingUserId, ConfirmMsg, ConfirmNum, FundsProcessing, ProcessType, TypeDetail, LastChangeDate, Action, FundsRetry, BankFromRtnType, ProcessNumber, ToAmount, ToAmountCurrency, UserAssignedAmount  FROM BPW_Transfer  WHERE CustomerId = ? and ExternalAcctId = ? ORDER BY DateCreate DESC");
    Object[] arrayOfObject = { paramString, paramExtTransferAcctInfo.getAcctId() };
    Object localObject3;
    try
    {
      localObject2 = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      while (((FFSResultSet)localObject2).getNextRow())
      {
        String str3 = null;
        if (UserAssignedAmount.getEnum(((FFSResultSet)localObject2).getColumnInt("UserAssignedAmount")) == UserAssignedAmount.TO) {
          str3 = ((FFSResultSet)localObject2).getColumnString("ToAmount");
        } else {
          str3 = ((FFSResultSet)localObject2).getColumnString("Amount");
        }
        if (str2.length() > 0) {
          str2 = str2 + ",";
        }
        str2 = str2 + str3;
        ((ArrayList)localObject1).add(str3);
      }
    }
    catch (FFSException localFFSException)
    {
      str4 = "*** " + str1 + " failed: ";
      localObject3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, (String)localObject3, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed: ";
      localObject3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, (String)localObject3, 0);
      throw new FFSException(localException1, str4);
    }
    finally
    {
      if (localObject2 != null) {
        try
        {
          ((FFSResultSet)localObject2).close();
          localObject2 = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log(str1 + "failed :" + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    FFSDebug.log(str1 + " end. Number of ext deposit amounts: " + ((ArrayList)localObject1).size() + "  Values: " + str2, 6);
    int[] arrayOfInt = new int[((ArrayList)localObject1).size()];
    for (int i = 0; i < ((ArrayList)localObject1).size(); i++)
    {
      localObject3 = new BigDecimal((String)((ArrayList)localObject1).get(i));
      localObject3 = ((BigDecimal)localObject3).movePointRight(2);
      arrayOfInt[i] = ((BigDecimal)localObject3).intValue();
    }
    return arrayOfInt;
  }
  
  public static ExtTransferAcctInfo verifyAccountSuccess(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    String str1 = "ExternalTransferAccount.verifyAccountSuccess: ";
    FFSDebug.log(str1, "start ...", 6);
    StringBuffer localStringBuffer = null;
    try
    {
      String str2;
      if (paramExtTransferAcctInfo == null)
      {
        str2 = "failed: External Transfer Account info Object passed is null";
        FFSDebug.log("****", str1, str2, 0);
        paramExtTransferAcctInfo = new ExtTransferAcctInfo();
        paramExtTransferAcctInfo.setStatusCode(16000);
        localObject = BPWLocaleUtil.getMessage(16000, new String[] { "ExtTransferAcctInfo" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject);
        return paramExtTransferAcctInfo;
      }
      if (paramExtTransferAcctInfo.getAcctId() == null)
      {
        str2 = "failed: AcctId is null";
        FFSDebug.log("**** ", str1, str2, 0);
        paramExtTransferAcctInfo.setStatusCode(16010);
        localObject = BPWLocaleUtil.getMessage(16010, new String[] { "ExtTransferAcctInfo", "AcctId" }, "TRANSFER_MESSAGE");
        paramExtTransferAcctInfo.setStatusMsg((String)localObject);
        return paramExtTransferAcctInfo;
      }
      int i = 0;
      localStringBuffer = new StringBuffer("Update ETF_ExternalAcct set VerifyStatus = ? WHERE AcctId = ? ");
      paramExtTransferAcctInfo.setVerifyStatus(4);
      localObject = new Object[] { new Integer(paramExtTransferAcctInfo.getVerifyStatus()), paramExtTransferAcctInfo.getAcctId() };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject);
      FFSDebug.log(str1, "Number of ExtTransferAcctInfo records updated " + i, 6);
      if (i == 0)
      {
        paramExtTransferAcctInfo.setStatusCode(16020);
        paramExtTransferAcctInfo.setStatusMsg("ExtTransferAcctInfo  record not found");
      }
      else
      {
        paramExtTransferAcctInfo.setStatusCode(0);
        paramExtTransferAcctInfo.setStatusMsg("Success");
      }
      FFSDebug.log(str1, "End", 6);
      return paramExtTransferAcctInfo;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, (String)localObject, 0);
      throw new FFSException(localThrowable, (String)localObject);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ExternalTransferAccount
 * JD-Core Version:    0.7.0.1
 */