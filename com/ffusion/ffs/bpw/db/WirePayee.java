package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWPayeeList;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.util.ArrayList;

public class WirePayee
  implements DBConsts, FFSConst, ACHConsts, BPWResource
{
  private static final String yr = " WHERE PayeeType = ? ";
  private static final String yv = " WHERE Status = ? ";
  private static final String yw = " WHERE PayeeGroup = ? ";
  private static final String yu = " WHERE CustomerID = ? ";
  private static final String yt = " AND Status != ? ";
  private static final String ys = "AND PayeeId > ? ";
  private static final String yx = " ORDER BY PayeeId ";
  
  public static WirePayeeInfo create(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("***WirePayee.create start ...", 6);
    String str1 = null;
    String str2 = null;
    String str3 = null;
    Object localObject;
    if (paramWirePayeeInfo == null)
    {
      localObject = "***WirePayee.create failed: Null WirePayeeInfo Object passed";
      FFSDebug.log((String)localObject, 0);
      paramWirePayeeInfo = new WirePayeeInfo();
      paramWirePayeeInfo.setStatusCode(16000);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      return paramWirePayeeInfo;
    }
    FFSDebug.log("***WirePayee.create: beneficiaryBankId=" + paramWirePayeeInfo.getBeneficiaryBankId(), 6);
    FFSDebug.log("***WirePayee.create: beneficiaryBankInfo=" + paramWirePayeeInfo.getBeneficiaryBankInfo(), 6);
    if (!a(paramWirePayeeInfo))
    {
      localObject = "*** WirePayee.create failed: ";
      FFSDebug.log((String)localObject, paramWirePayeeInfo.getStatusMsg(), 0);
      return paramWirePayeeInfo;
    }
    if (!jdMethod_if(paramWirePayeeInfo))
    {
      localObject = "*** WirePayee.create failed: ";
      FFSDebug.log((String)localObject, paramWirePayeeInfo.getStatusMsg(), 0);
      return paramWirePayeeInfo;
    }
    str2 = paramWirePayeeInfo.getPayeeGroup();
    if ((!"SECURE".equals(str2)) && (!"UNMANAGED".equals(str2)))
    {
      paramWirePayeeInfo = findDuplicateWirePayee(paramFFSConnectionHolder, paramWirePayeeInfo);
      FFSDebug.log("***WirePayee.create Status Code:" + paramWirePayeeInfo.getStatusCode(), 6);
      FFSDebug.log("***WirePayee.create Status Msg:", paramWirePayeeInfo.getStatusMsg(), 6);
      if (paramWirePayeeInfo.getStatusCode() == 23330)
      {
        localObject = "*** WirePayee.create failed: ";
        FFSDebug.log((String)localObject, paramWirePayeeInfo.getStatusMsg(), 0);
        return paramWirePayeeInfo;
      }
      if (paramWirePayeeInfo.getExtId() != null)
      {
        paramWirePayeeInfo = jdMethod_int(paramFFSConnectionHolder, paramWirePayeeInfo);
        FFSDebug.log("***WirePayee.create Status Code:" + paramWirePayeeInfo.getStatusCode(), 6);
        FFSDebug.log("***WirePayee.create Status Msg:", paramWirePayeeInfo.getStatusMsg(), 6);
        if (paramWirePayeeInfo.getStatusCode() == 19590)
        {
          localObject = "*** WirePayee.create failed: ";
          FFSDebug.log((String)localObject, paramWirePayeeInfo.getStatusMsg(), 0);
          return paramWirePayeeInfo;
        }
      }
      if (paramWirePayeeInfo.getPayeeExtId() != null)
      {
        paramWirePayeeInfo = jdMethod_for(paramFFSConnectionHolder, paramWirePayeeInfo);
        FFSDebug.log("***WirePayee.create Status Code:" + paramWirePayeeInfo.getStatusCode(), 6);
        FFSDebug.log("***WirePayee.create Status Msg:", paramWirePayeeInfo.getStatusMsg(), 6);
        if (paramWirePayeeInfo.getStatusCode() == 19590)
        {
          localObject = "*** WirePayee.create failed: ";
          FFSDebug.log((String)localObject, paramWirePayeeInfo.getStatusMsg(), 0);
          return paramWirePayeeInfo;
        }
      }
    }
    if (!a(paramFFSConnectionHolder, paramWirePayeeInfo, false))
    {
      localObject = "*** WirePayee.create failed: ";
      FFSDebug.log((String)localObject, paramWirePayeeInfo.getStatusMsg(), 0);
      return paramWirePayeeInfo;
    }
    if ((paramWirePayeeInfo.getBeneficiaryBankId() == null) || (paramWirePayeeInfo.getBeneficiaryBankId().trim().length() == 0)) {
      str1 = paramWirePayeeInfo.getBeneficiaryBankInfo().getBankId();
    } else {
      str1 = paramWirePayeeInfo.getBeneficiaryBankId();
    }
    try
    {
      if (!jdMethod_if(paramFFSConnectionHolder, paramWirePayeeInfo))
      {
        paramWirePayeeInfo.setStatusCode(19190);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(19190, null, "WIRE_MESSAGE"));
        localObject = "*** WirePayee.create failed: ";
        FFSDebug.log((String)localObject, paramWirePayeeInfo.getStatusMsg(), 0);
        return paramWirePayeeInfo;
      }
      paramWirePayeeInfo.setStatus("ACTIVE");
      paramWirePayeeInfo.setPayeeId(DBUtil.getNextIndexStringWithPadding("WIREPAYEEID", 10, '0'));
      paramWirePayeeInfo.setSubmitDate(FFSUtil.getDateString());
      paramWirePayeeInfo.setActivationDate(FFSUtil.getDateString());
      str3 = "INSERT INTO BPW_WirePayee  ( PayeeId, PayeeType, PayeeGroup, BeneficiaryName, NickName, ContactName, CustomerID, PayeeExtID, ExtID, RouteID, BeneficiaryBankId, BankAcctId, BankAcctType, BranchId, AcctKey, PayeeAddr1, PayeeAddr2, PayeeAddr3, PayeeCity, PayeeState, PayeeZipcode, PayeeCountry, PayeePhone, Status, Memo, SubmitDate, ActivationDate, LogId, SubmittedBy, PayeeDest ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
      FFSDebug.log("WirePayee.create: adding wirePayee: " + paramWirePayeeInfo, 6);
      localObject = new Object[] { paramWirePayeeInfo.getPayeeId(), paramWirePayeeInfo.getPayeeType(), paramWirePayeeInfo.getPayeeGroup(), paramWirePayeeInfo.getBeneficiaryName(), paramWirePayeeInfo.getNickName(), paramWirePayeeInfo.getContactName(), paramWirePayeeInfo.getCustomerID(), paramWirePayeeInfo.getPayeeExtId(), paramWirePayeeInfo.getExtId(), paramWirePayeeInfo.getRouteId(), str1, paramWirePayeeInfo.getBankAcctId(), paramWirePayeeInfo.getBankAcctType(), paramWirePayeeInfo.getBranchId(), paramWirePayeeInfo.getAcctKey(), paramWirePayeeInfo.getPayeeAddr1(), paramWirePayeeInfo.getPayeeAddr2(), paramWirePayeeInfo.getPayeeAddr3(), paramWirePayeeInfo.getPayeeCity(), paramWirePayeeInfo.getPayeeState(), paramWirePayeeInfo.getPayeeZipcode(), paramWirePayeeInfo.getPayeeCountry(), paramWirePayeeInfo.getPayeePhone(), paramWirePayeeInfo.getStatus(), FFSUtil.hashtableToString(paramWirePayeeInfo.getMemo()), paramWirePayeeInfo.getSubmitDate(), paramWirePayeeInfo.getActivationDate(), paramWirePayeeInfo.getLogId(), paramWirePayeeInfo.getSubmittedBy(), paramWirePayeeInfo.getPayeeDest() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, (Object[])localObject);
      FFSDebug.log("WirePayee.create: Number of records added " + i, 6);
      if (!jdMethod_try(paramFFSConnectionHolder, paramWirePayeeInfo))
      {
        String str5 = "*** WirePayee.create failed: ";
        FFSDebug.log(str5, paramWirePayeeInfo.getStatusMsg(), 0);
        return paramWirePayeeInfo;
      }
      paramWirePayeeInfo.setStatusCode(0);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      FFSDebug.log("***WirePayee.create end", 6);
      return paramWirePayeeInfo;
    }
    catch (Throwable localThrowable)
    {
      String str4 = "***WirePayee.create failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable, str4);
    }
  }
  
  public static WirePayeeInfo getWirePayeeInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    return getWirePayeeInfo(paramFFSConnectionHolder, paramString, true);
  }
  
  public static WirePayeeInfo getWirePayeeInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("***WirePayee.getWirePayeeInfo start ...", 6);
    WirePayeeInfo localWirePayeeInfo = null;
    if (paramString == null)
    {
      localObject = "***WirePayee.getWirePayeeInfo failed: payeeID is null";
      FFSDebug.log((String)localObject, 0);
      localWirePayeeInfo = new WirePayeeInfo();
      localWirePayeeInfo.setStatusCode(16000);
      localWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "payeeID " }, "WIRE_MESSAGE"));
      return localWirePayeeInfo;
    }
    Object localObject = new WirePayeeInfo();
    ((WirePayeeInfo)localObject).setPayeeId(paramString);
    try
    {
      a(paramFFSConnectionHolder, (WirePayeeInfo)localObject, false, paramBoolean);
      return localObject;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** WirePayee.getWirePayeeInfo failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  private static WirePayeeInfo a(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    FFSDebug.log("***WirePayee.getWirePayeeInfo(payeeInfo) start ...", 6);
    FFSResultSet localFFSResultSet = null;
    BPWBankInfo localBPWBankInfo = null;
    BPWBankInfo[] arrayOfBPWBankInfo = null;
    String str1 = null;
    if (paramWirePayeeInfo == null)
    {
      localObject1 = "***WirePayee.getWirePayeeInfo(payeeInfo) failed: WirePayeeInfo is null";
      FFSDebug.log((String)localObject1, 0);
      paramWirePayeeInfo = new WirePayeeInfo();
      paramWirePayeeInfo.setStatusCode(16000);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      return paramWirePayeeInfo;
    }
    str1 = paramWirePayeeInfo.getPayeeId();
    Object localObject1 = new StringBuffer();
    ((StringBuffer)localObject1).append("SELECT  PayeeId, PayeeType, PayeeGroup, BeneficiaryName, NickName, ContactName, CustomerID, RouteID, PayeeExtID, ExtID, BeneficiaryBankId, BankAcctId, BankAcctType, BranchId, AcctKey, PayeeAddr1, PayeeAddr2, PayeeAddr3, PayeeCity, PayeeState, PayeeZipcode, PayeeCountry, PayeePhone, Status, Memo, SubmitDate, LastModDate, ActivationDate, LogId, SubmittedBy, PayeeDest  FROM BPW_WirePayee WHERE PayeeId = ? ");
    if (paramBoolean1)
    {
      ((StringBuffer)localObject1).append(" AND Status <> '");
      ((StringBuffer)localObject1).append("CLOSED");
      ((StringBuffer)localObject1).append("' ");
    }
    if (paramBoolean2)
    {
      ((StringBuffer)localObject1).append(" AND PayeeGroup NOT IN ('");
      ((StringBuffer)localObject1).append("UNMANAGED");
      ((StringBuffer)localObject1).append("', '");
      ((StringBuffer)localObject1).append("SECURE");
      ((StringBuffer)localObject1).append("') ");
    }
    Object[] arrayOfObject = { str1 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, ((StringBuffer)localObject1).toString(), arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        a(localFFSResultSet, paramWirePayeeInfo);
        localBPWBankInfo = WireBank.getWireBanksByID(paramFFSConnectionHolder, paramWirePayeeInfo.getBeneficiaryBankId());
        paramWirePayeeInfo.setBeneficiaryBankInfo(localBPWBankInfo);
        arrayOfBPWBankInfo = WireBank.getWireBanksByPayee(paramFFSConnectionHolder, paramWirePayeeInfo.getPayeeId());
        paramWirePayeeInfo.setIntermediateBanks(arrayOfBPWBankInfo);
        paramWirePayeeInfo.setStatusCode(0);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      else
      {
        paramWirePayeeInfo.setStatusCode(16020);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      }
      FFSDebug.log("***WirePayee.getWirePayeeInfo(payeeInfo) end", 6);
      WirePayeeInfo localWirePayeeInfo = paramWirePayeeInfo;
      return localWirePayeeInfo;
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** WirePayee.getWirePayeeInfo(payeeInfo) failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
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
          FFSDebug.log("***WirePayee.getWirePayeeInfo(payeeInfo) ", "failed to close ResultSet", localException.toString(), 0);
        }
      }
    }
  }
  
  private static void a(FFSResultSet paramFFSResultSet, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    BPWBankInfo localBPWBankInfo = null;
    paramWirePayeeInfo.setPayeeId(paramFFSResultSet.getColumnString("PayeeId"));
    paramWirePayeeInfo.setPayeeType(paramFFSResultSet.getColumnString("PayeeType"));
    paramWirePayeeInfo.setPayeeGroup(paramFFSResultSet.getColumnString("PayeeGroup"));
    paramWirePayeeInfo.setBeneficiaryName(paramFFSResultSet.getColumnString("BeneficiaryName"));
    paramWirePayeeInfo.setNickName(paramFFSResultSet.getColumnString("NickName"));
    paramWirePayeeInfo.setContactName(paramFFSResultSet.getColumnString("ContactName"));
    paramWirePayeeInfo.setCustomerID(paramFFSResultSet.getColumnString("CustomerID"));
    paramWirePayeeInfo.setPayeeExtId(paramFFSResultSet.getColumnString("PayeeExtId"));
    paramWirePayeeInfo.setExtId(paramFFSResultSet.getColumnString("ExtId"));
    paramWirePayeeInfo.setRouteId(paramFFSResultSet.getColumnString("RouteID"));
    paramWirePayeeInfo.setBeneficiaryBankId(paramFFSResultSet.getColumnString("BeneficiaryBankId"));
    paramWirePayeeInfo.setBranchId(paramFFSResultSet.getColumnString("BranchId"));
    paramWirePayeeInfo.setBankAcctId(paramFFSResultSet.getColumnString("BankAcctId"));
    paramWirePayeeInfo.setBankAcctType(paramFFSResultSet.getColumnString("BankAcctType"));
    paramWirePayeeInfo.setAcctKey(paramFFSResultSet.getColumnString("AcctKey"));
    paramWirePayeeInfo.setPayeeAddr1(paramFFSResultSet.getColumnString("PayeeAddr1"));
    paramWirePayeeInfo.setPayeeAddr2(paramFFSResultSet.getColumnString("PayeeAddr2"));
    paramWirePayeeInfo.setPayeeAddr3(paramFFSResultSet.getColumnString("PAYEEADDR3"));
    paramWirePayeeInfo.setPayeeCity(paramFFSResultSet.getColumnString("PayeeCity"));
    paramWirePayeeInfo.setPayeeState(paramFFSResultSet.getColumnString("PayeeState"));
    paramWirePayeeInfo.setPayeeZipcode(paramFFSResultSet.getColumnString("PayeeZipcode"));
    paramWirePayeeInfo.setPayeeCountry(paramFFSResultSet.getColumnString("PayeeCountry"));
    paramWirePayeeInfo.setPayeePhone(paramFFSResultSet.getColumnString("PayeePhone"));
    paramWirePayeeInfo.setStatus(paramFFSResultSet.getColumnString("Status"));
    paramWirePayeeInfo.setMemo(FFSUtil.stringToHashtable(paramFFSResultSet.getColumnString("Memo")));
    paramWirePayeeInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
    paramWirePayeeInfo.setActivationDate(paramFFSResultSet.getColumnString("ActivationDate"));
    paramWirePayeeInfo.setLastModDate(paramFFSResultSet.getColumnString("LastModDate"));
    paramWirePayeeInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    paramWirePayeeInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    paramWirePayeeInfo.setPayeeDest(paramFFSResultSet.getColumnString("PayeeDest"));
    localBPWBankInfo = new BPWBankInfo();
    localBPWBankInfo.setBankId(paramFFSResultSet.getColumnString("BeneficiaryBankId"));
    paramWirePayeeInfo.setBeneficiaryBankInfo(localBPWBankInfo);
    paramWirePayeeInfo.setStatusCode(0);
    paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
  }
  
  private static boolean a(WirePayeeInfo paramWirePayeeInfo)
    throws BPWException
  {
    String str1 = "WirePayee.validateWirePayeeInfo: ";
    FFSDebug.log(str1, ": start", 6);
    FFSDebug.log(str1, ": Name = ", paramWirePayeeInfo.getBeneficiaryName(), ", BankId = ", paramWirePayeeInfo.getBeneficiaryBankId(), ", BankAcctId = ", paramWirePayeeInfo.getBankAcctId(), ", BankActType = ", paramWirePayeeInfo.getBankAcctType(), ", CustomerId = ", paramWirePayeeInfo.getCustomerID(), 6);
    FFSDebug.log(str1, ": BankInfo = " + paramWirePayeeInfo.getBeneficiaryBankInfo(), 6);
    String str2 = null;
    String str3;
    if ((paramWirePayeeInfo.getBeneficiaryBankInfo() == null) && ((paramWirePayeeInfo.getBeneficiaryBankId() == null) || (paramWirePayeeInfo.getBeneficiaryBankId().trim().length() == 0)))
    {
      paramWirePayeeInfo.setStatusCode(16010);
      str3 = "Both BeneficiaryBankId and BeneficiaryBankInfo  contains null ";
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BeneficiaryBankId, BeneficiaryBankInfo " }, "WIRE_MESSAGE"));
      FFSDebug.log(str1, str3, 0);
      return false;
    }
    if ((paramWirePayeeInfo.getBeneficiaryName() == null) || (paramWirePayeeInfo.getBeneficiaryName().trim().length() == 0))
    {
      paramWirePayeeInfo.setStatusCode(16010);
      str3 = "PAYEENAME   contains null ";
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "WirePayeeInfo", "PAYEENAME  " }, "WIRE_MESSAGE"));
      FFSDebug.log(str1, str3, 0);
      return false;
    }
    if ((paramWirePayeeInfo.getBankAcctId() == null) || (paramWirePayeeInfo.getBankAcctId().trim().length() == 0))
    {
      paramWirePayeeInfo.setStatusCode(16010);
      str3 = "BANKACCTID   contains null ";
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "WirePayeeInfo", "BANKACCTID  " }, "WIRE_MESSAGE"));
      FFSDebug.log(str1, str3, 0);
      return false;
    }
    if ((paramWirePayeeInfo.getCustomerID() == null) || (paramWirePayeeInfo.getCustomerID().trim().length() == 0))
    {
      paramWirePayeeInfo.setStatusCode(16010);
      str3 = "Customer ID   contains null ";
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "WirePayeeInfo", "Customer ID  " }, "WIRE_MESSAGE"));
      FFSDebug.log(str1, str3, 0);
      return false;
    }
    if ((paramWirePayeeInfo.getExtId() == null) || (paramWirePayeeInfo.getExtId().trim().length() == 0))
    {
      paramWirePayeeInfo.setStatusCode(16010);
      str3 = "ExtId   contains null ";
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "WirePayeeInfo", "ExtId  " }, "WIRE_MESSAGE"));
      FFSDebug.log(str1, str3, 0);
      return false;
    }
    str2 = paramWirePayeeInfo.getPayeeDest();
    if ((str2 == null) || (str2.trim().length() == 0))
    {
      paramWirePayeeInfo.setStatusCode(16010);
      str3 = "PayeeDest   contains null ";
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "WirePayeeInfo", "PayeeDest " }, "WIRE_MESSAGE"));
      FFSDebug.log(str1, str3, 0);
      return false;
    }
    FFSDebug.log(str1, ": done", 6);
    return true;
  }
  
  private static boolean jdMethod_if(WirePayeeInfo paramWirePayeeInfo)
  {
    String str1 = "WirePayee.validateWirePayeeGroup: ";
    String str2 = null;
    FFSDebug.log(str1, ": start", 6);
    FFSDebug.log(str1, ": payee group = ", paramWirePayeeInfo.getPayeeGroup(), 6);
    str2 = paramWirePayeeInfo.getPayeeGroup();
    if ((str2 == null) || (str2.trim().length() == 0)) {
      paramWirePayeeInfo.setPayeeGroup("BUSINESS");
    }
    FFSDebug.log(str1, ": done", 6);
    return true;
  }
  
  public static WirePayeeInfo delete(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("***WirePayee.delete start ...", 6);
    StringBuffer localStringBuffer = null;
    if (paramWirePayeeInfo == null)
    {
      localObject = "***WirePayee.delete failed: Wire Payee Object passed is null";
      FFSDebug.log((String)localObject, 0);
      paramWirePayeeInfo = new WirePayeeInfo();
      paramWirePayeeInfo.setStatusCode(16000);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      return paramWirePayeeInfo;
    }
    if (paramWirePayeeInfo.getPayeeId() == null)
    {
      localObject = "***WirePayee.delete failed: PayeeID is null";
      FFSDebug.log((String)localObject, 0);
      paramWirePayeeInfo.setStatusCode(16010);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "WirePayeeInfo ", "PayeeID" }, "WIRE_MESSAGE"));
      return paramWirePayeeInfo;
    }
    Object localObject = { "CLOSED", paramWirePayeeInfo.getPayeeId(), "USER", "USER", paramWirePayeeInfo.getSubmittedBy() };
    try
    {
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("Update BPW_WirePayee Set Status = ? WHERE PayeeId = ? ");
      localStringBuffer.append(" AND ( PayeeGroup <> ? ");
      localStringBuffer.append(" OR ( PayeeGroup = ? AND SubmittedBy = ? ) )");
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject);
      FFSDebug.log("WirePayee.delete: Number of WirePayeeInfo records closed " + i, 6);
      FFSDebug.log("***WirePayee.delete end", 6);
      if (i == 0)
      {
        paramWirePayeeInfo.setStatusCode(16020);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      }
      else if (i > 1)
      {
        paramWirePayeeInfo.setStatusCode(23270);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(23270, new String[] { "PayeeId =", paramWirePayeeInfo.getPayeeId() }, "WIRE_MESSAGE"));
      }
      else
      {
        paramWirePayeeInfo.setStatus("CLOSED");
        paramWirePayeeInfo.setStatusCode(0);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      return paramWirePayeeInfo;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** WirePayee.delete failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public static WirePayeeInfo modifyPayee(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("***WirePayee.modifyPayee start ...", 6);
    Object localObject1 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    WirePayeeInfo localWirePayeeInfo1 = null;
    WirePayeeInfo localWirePayeeInfo2 = null;
    Object localObject2;
    if (paramWirePayeeInfo == null)
    {
      localObject2 = "***WirePayee.delete failed: Wire Payee Object passed is null";
      FFSDebug.log((String)localObject2, 0);
      paramWirePayeeInfo = new WirePayeeInfo();
      paramWirePayeeInfo.setStatusCode(16000);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      return paramWirePayeeInfo;
    }
    if (paramWirePayeeInfo.getPayeeId() == null)
    {
      localObject2 = "***WirePayee.modifyPayee failed: PayeeID is null";
      FFSDebug.log((String)localObject2, 0);
      paramWirePayeeInfo.setStatusCode(16010);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "WirePayeeInfo ", " payeeId" }, "WIRE_MESSAGE"));
      return paramWirePayeeInfo;
    }
    if (!a(paramWirePayeeInfo))
    {
      localObject2 = "*** WirePayee.modifyPayee failed: ";
      FFSDebug.log((String)localObject2, paramWirePayeeInfo.getStatusMsg(), 0);
      return paramWirePayeeInfo;
    }
    if (!jdMethod_if(paramWirePayeeInfo))
    {
      localObject2 = "*** WirePayee.modifyPayee failed: ";
      FFSDebug.log((String)localObject2, paramWirePayeeInfo.getStatusMsg(), 0);
      return paramWirePayeeInfo;
    }
    try
    {
      localWirePayeeInfo1 = new WirePayeeInfo();
      localWirePayeeInfo1.setPayeeId(paramWirePayeeInfo.getPayeeId());
      localWirePayeeInfo1 = a(paramFFSConnectionHolder, localWirePayeeInfo1, true, false);
      if (localWirePayeeInfo1.getStatusCode() != 0)
      {
        paramWirePayeeInfo.setStatusCode(19120);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(19120, null, "WIRE_MESSAGE"));
        localObject2 = paramWirePayeeInfo;
        return localObject2;
      }
      str2 = paramWirePayeeInfo.getPayeeGroup();
      FFSDebug.log("***WirePayee.modifyPayee payeeGroup:", str2, 6);
      if ((!"UNMANAGED".equals(str2)) && (!"SECURE".equals(str2)) && (!str2.equals("BUSINESS")))
      {
        str2 = localWirePayeeInfo1.getPayeeGroup();
        if (str2.equals("BUSINESS"))
        {
          paramWirePayeeInfo.setStatusCode(19570);
          paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(19570, null, "WIRE_MESSAGE"));
          localObject2 = paramWirePayeeInfo;
          return localObject2;
        }
        str3 = localWirePayeeInfo1.getSubmittedBy();
        if (!str3.equals(paramWirePayeeInfo.getSubmittedBy()))
        {
          paramWirePayeeInfo.setStatusCode(19580);
          paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(19580, null, "WIRE_MESSAGE"));
          localObject2 = paramWirePayeeInfo;
          return localObject2;
        }
      }
    }
    catch (Throwable localThrowable1)
    {
      String str4 = "*** WirePayee.modifyPayee failed: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable1, str4);
    }
    finally
    {
      if (localObject1 != null) {
        try
        {
          localObject1.close();
          localObject1 = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("***WirePayee.modifyPayee failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
    Object localObject3;
    if ((!"UNMANAGED".equals(paramWirePayeeInfo.getPayeeGroup())) && (!"SECURE".equals(paramWirePayeeInfo.getPayeeGroup())))
    {
      localWirePayeeInfo2 = new WirePayeeInfo();
      localWirePayeeInfo2.setPayeeId(paramWirePayeeInfo.getPayeeId());
      localWirePayeeInfo2.setBeneficiaryName(paramWirePayeeInfo.getBeneficiaryName());
      localWirePayeeInfo2.setNickName(paramWirePayeeInfo.getNickName());
      localWirePayeeInfo2.setCustomerID(paramWirePayeeInfo.getCustomerID());
      localWirePayeeInfo2.setPayeeExtId(paramWirePayeeInfo.getPayeeExtId());
      localWirePayeeInfo2.setBankAcctId(paramWirePayeeInfo.getBankAcctId());
      localWirePayeeInfo2.setBankAcctType(paramWirePayeeInfo.getBankAcctType());
      localWirePayeeInfo2.setPayeeDest(paramWirePayeeInfo.getPayeeDest());
      localWirePayeeInfo2.setPayeeGroup(paramWirePayeeInfo.getPayeeGroup());
      localWirePayeeInfo2.setSubmittedBy(paramWirePayeeInfo.getSubmittedBy());
      localWirePayeeInfo2.setBeneficiaryBankId(paramWirePayeeInfo.getBeneficiaryBankId());
      localWirePayeeInfo2.setBeneficiaryBankInfo(paramWirePayeeInfo.getBeneficiaryBankInfo());
      localWirePayeeInfo2 = findDuplicateWirePayee(paramFFSConnectionHolder, localWirePayeeInfo2);
      if (localWirePayeeInfo2.getStatusCode() == 23330)
      {
        localObject3 = new StringBuffer();
        ((StringBuffer)localObject3).append("***WirePayee.modifyPayee failed:");
        paramWirePayeeInfo.setStatusCode(19810);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(19810, null, "WIRE_MESSAGE"));
        ((StringBuffer)localObject3).append(paramWirePayeeInfo.getStatusMsg());
        FFSDebug.log(((StringBuffer)localObject3).toString(), 0);
        return paramWirePayeeInfo;
      }
      if (paramWirePayeeInfo.getExtId() != null)
      {
        localWirePayeeInfo2.setExtId(paramWirePayeeInfo.getExtId());
        localWirePayeeInfo2 = jdMethod_int(paramFFSConnectionHolder, localWirePayeeInfo2);
        FFSDebug.log("***WirePayee.modifyPayee Status Code:" + localWirePayeeInfo2.getStatusCode(), 6);
        FFSDebug.log("***WirePayee.modifyPayee Status Msg:", localWirePayeeInfo2.getStatusMsg(), 6);
        if (localWirePayeeInfo2.getStatusCode() == 19590)
        {
          localObject3 = new StringBuffer();
          ((StringBuffer)localObject3).append("***WirePayee.modifyPayee failed:");
          ((StringBuffer)localObject3).append("Duplicate wire payee external Id.");
          ((StringBuffer)localObject3).append(", ExtId=").append(localWirePayeeInfo2.getExtId());
          FFSDebug.log(((StringBuffer)localObject3).toString(), 0);
          paramWirePayeeInfo.setStatusCode(localWirePayeeInfo2.getStatusCode());
          paramWirePayeeInfo.setStatusMsg(localWirePayeeInfo2.getStatusMsg());
          return paramWirePayeeInfo;
        }
      }
    }
    if (!a(paramFFSConnectionHolder, paramWirePayeeInfo, false))
    {
      localObject3 = "*** WirePayee.modifyPayee failed: ";
      FFSDebug.log((String)localObject3, paramWirePayeeInfo.getStatusMsg(), 0);
      return paramWirePayeeInfo;
    }
    try
    {
      if (!jdMethod_if(paramFFSConnectionHolder, paramWirePayeeInfo))
      {
        paramWirePayeeInfo.setStatusCode(19190);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(19190, null, "WIRE_MESSAGE"));
        localObject3 = "*** WirePayee.modifyPayee failed: ";
        FFSDebug.log((String)localObject3, paramWirePayeeInfo.getStatusMsg(), 0);
        return paramWirePayeeInfo;
      }
      if (!jdMethod_do(paramFFSConnectionHolder, paramWirePayeeInfo))
      {
        localObject3 = "*** WirePayee.modifyPayee failed: ";
        FFSDebug.log((String)localObject3, paramWirePayeeInfo.getStatusMsg(), 0);
        return paramWirePayeeInfo;
      }
      paramWirePayeeInfo.setLastModDate(FFSUtil.getDateString());
      str1 = "UPDATE BPW_WirePayee set PayeeType = ?, PayeeGroup = ? , BeneficiaryName = ? , NickName = ? , ContactName = ?, CustomerID = ?, ExtID = ?, RouteID = ?, BeneficiaryBankId = ?, BankAcctId = ?, BankAcctType = ?, BranchId = ?, AcctKey = ?, PayeeAddr1 = ? , PayeeAddr2 = ? , PayeeAddr3 = ? , PayeeCity = ? , PayeeState = ? , PayeeZipcode = ? , PayeeCountry = ? , PayeePhone = ? , Memo = ? , LastModDate = ? ,LogId = ?, SubmittedBy = ?, PayeeDest = ? WHERE  PayeeId = ? ";
      localObject3 = new Object[] { paramWirePayeeInfo.getPayeeType(), paramWirePayeeInfo.getPayeeGroup(), paramWirePayeeInfo.getBeneficiaryName(), paramWirePayeeInfo.getNickName(), paramWirePayeeInfo.getContactName(), paramWirePayeeInfo.getCustomerID(), paramWirePayeeInfo.getExtId(), paramWirePayeeInfo.getRouteId(), paramWirePayeeInfo.getBeneficiaryBankId(), paramWirePayeeInfo.getBankAcctId(), paramWirePayeeInfo.getBankAcctType(), paramWirePayeeInfo.getBranchId(), paramWirePayeeInfo.getAcctKey(), paramWirePayeeInfo.getPayeeAddr1(), paramWirePayeeInfo.getPayeeAddr2(), paramWirePayeeInfo.getPayeeAddr3(), paramWirePayeeInfo.getPayeeCity(), paramWirePayeeInfo.getPayeeState(), paramWirePayeeInfo.getPayeeZipcode(), paramWirePayeeInfo.getPayeeCountry(), paramWirePayeeInfo.getPayeePhone(), FFSUtil.hashtableToString(paramWirePayeeInfo.getMemo()), paramWirePayeeInfo.getLastModDate(), paramWirePayeeInfo.getLogId(), paramWirePayeeInfo.getSubmittedBy(), paramWirePayeeInfo.getPayeeDest(), paramWirePayeeInfo.getPayeeId() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, (Object[])localObject3);
      FFSDebug.log("WirePayee.modifyPayee: Number of WirePayeeInfo records modified " + i, 6);
      FFSDebug.log("***WirePayee.modifyPayee end", 6);
      if (i == 0)
      {
        paramWirePayeeInfo.setStatusCode(16020);
        paramWirePayeeInfo.setStatusMsg("WirePayeeInfo  record not found");
      }
      else
      {
        localWirePayeeInfo1 = jdMethod_new(paramFFSConnectionHolder, localWirePayeeInfo1);
        int j = Wire.updatePayeeOfCompletedWires(paramFFSConnectionHolder, paramWirePayeeInfo.getPayeeId(), localWirePayeeInfo1.getPayeeId());
        FFSDebug.log("***WirePayee.modifyPayee modified wires count" + j, 6);
        paramWirePayeeInfo.setStatusCode(0);
        paramWirePayeeInfo.setStatusMsg("Success");
      }
      FFSDebug.log("***WirePayee.modifyPayee done", 6);
      return paramWirePayeeInfo;
    }
    catch (Throwable localThrowable2)
    {
      String str5 = "*** WirePayee.modifyPayee failed: " + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str5, 0);
      throw new FFSException(localThrowable2, str5);
    }
  }
  
  public static WirePayeeInfo updatePayee(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    String str1 = "WirePayee.updatePayee: ";
    FFSDebug.log(str1, " start", 6);
    Object localObject1 = null;
    String str2 = null;
    WirePayeeInfo localWirePayeeInfo = null;
    String str3;
    if (paramWirePayeeInfo == null)
    {
      str3 = "failed: Wire Payee Object passed is null";
      FFSDebug.log(str1, str3, 0);
      paramWirePayeeInfo = new WirePayeeInfo();
      paramWirePayeeInfo.setStatusCode(16000);
      paramWirePayeeInfo.setStatusMsg("WirePayeeInfo  is null");
      return paramWirePayeeInfo;
    }
    if (paramWirePayeeInfo.getPayeeId() == null)
    {
      str3 = "failed: PayeeID is null";
      FFSDebug.log(str1, str3, 0);
      paramWirePayeeInfo.setStatusCode(16010);
      paramWirePayeeInfo.setStatusMsg("WirePayeeInfo  contains null  payeeId");
      return paramWirePayeeInfo;
    }
    if (!a(paramWirePayeeInfo))
    {
      str3 = "failed. ";
      FFSDebug.log(str1, str3, paramWirePayeeInfo.getStatusMsg(), 0);
      return paramWirePayeeInfo;
    }
    if (!jdMethod_if(paramWirePayeeInfo))
    {
      str3 = "failed. ";
      FFSDebug.log(str1, str3, paramWirePayeeInfo.getStatusMsg(), 0);
      return paramWirePayeeInfo;
    }
    try
    {
      localWirePayeeInfo = new WirePayeeInfo();
      localWirePayeeInfo.setPayeeId(paramWirePayeeInfo.getPayeeId());
      localWirePayeeInfo = a(paramFFSConnectionHolder, localWirePayeeInfo, true, false);
      if (localWirePayeeInfo.getStatusCode() != 0)
      {
        paramWirePayeeInfo.setStatusCode(19120);
        str3 = BPWLocaleUtil.getMessage(19120, new String[] { paramWirePayeeInfo.getPayeeId() }, "WIRE_MESSAGE");
        paramWirePayeeInfo.setStatusMsg(str3);
        localObject3 = paramWirePayeeInfo;
        return localObject3;
      }
    }
    catch (Throwable localThrowable1)
    {
      Object localObject3 = str1 + "failed " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject3, 0);
      throw new FFSException(localThrowable1, (String)localObject3);
    }
    finally
    {
      if (localObject1 != null) {
        try
        {
          localObject1.close();
          localObject1 = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1, "failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
    Object localObject2;
    if (!a(paramFFSConnectionHolder, paramWirePayeeInfo, true))
    {
      localObject2 = "failed. ";
      FFSDebug.log(str1, (String)localObject2, paramWirePayeeInfo.getStatusMsg(), 0);
      return paramWirePayeeInfo;
    }
    try
    {
      if (!jdMethod_if(paramFFSConnectionHolder, paramWirePayeeInfo))
      {
        paramWirePayeeInfo.setStatusCode(19190);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(19190, null, "WIRE_MESSAGE"));
        localObject2 = "failed. ";
        FFSDebug.log(str1, (String)localObject2, paramWirePayeeInfo.getStatusMsg(), 0);
        return paramWirePayeeInfo;
      }
      if (!jdMethod_do(paramFFSConnectionHolder, paramWirePayeeInfo))
      {
        localObject2 = "failed. ";
        FFSDebug.log(str1, (String)localObject2, paramWirePayeeInfo.getStatusMsg(), 0);
        return paramWirePayeeInfo;
      }
      paramWirePayeeInfo.setLastModDate(FFSUtil.getDateString());
      str2 = "UPDATE BPW_WirePayee set PayeeType = ?, PayeeGroup = ? , BeneficiaryName = ? , NickName = ? , ContactName = ?, CustomerID = ?, ExtID = ?, RouteID = ?, BeneficiaryBankId = ?, BankAcctId = ?, BankAcctType = ?, BranchId = ?, AcctKey = ?, PayeeAddr1 = ? , PayeeAddr2 = ? , PayeeAddr3 = ? , PayeeCity = ? , PayeeState = ? , PayeeZipcode = ? , PayeeCountry = ? , PayeePhone = ? , Memo = ? , LastModDate = ? ,LogId = ?, SubmittedBy = ?, PayeeDest = ? WHERE  PayeeId = ? ";
      localObject2 = new Object[] { paramWirePayeeInfo.getPayeeType(), paramWirePayeeInfo.getPayeeGroup(), paramWirePayeeInfo.getBeneficiaryName(), paramWirePayeeInfo.getNickName(), paramWirePayeeInfo.getContactName(), paramWirePayeeInfo.getCustomerID(), paramWirePayeeInfo.getExtId(), paramWirePayeeInfo.getRouteId(), paramWirePayeeInfo.getBeneficiaryBankId(), paramWirePayeeInfo.getBankAcctId(), paramWirePayeeInfo.getBankAcctType(), paramWirePayeeInfo.getBranchId(), paramWirePayeeInfo.getAcctKey(), paramWirePayeeInfo.getPayeeAddr1(), paramWirePayeeInfo.getPayeeAddr2(), paramWirePayeeInfo.getPayeeAddr3(), paramWirePayeeInfo.getPayeeCity(), paramWirePayeeInfo.getPayeeState(), paramWirePayeeInfo.getPayeeZipcode(), paramWirePayeeInfo.getPayeeCountry(), paramWirePayeeInfo.getPayeePhone(), FFSUtil.hashtableToString(paramWirePayeeInfo.getMemo()), paramWirePayeeInfo.getLastModDate(), paramWirePayeeInfo.getLogId(), paramWirePayeeInfo.getSubmittedBy(), paramWirePayeeInfo.getPayeeDest(), paramWirePayeeInfo.getPayeeId() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject2);
      FFSDebug.log(str1, "Number of WirePayeeInfo records modified " + i, 6);
      if (i == 0)
      {
        paramWirePayeeInfo.setStatusCode(16020);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      }
      else
      {
        paramWirePayeeInfo.setStatusCode(0);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      FFSDebug.log(str1, "done", 6);
      return paramWirePayeeInfo;
    }
    catch (Throwable localThrowable2)
    {
      String str4 = str1 + "failed. " + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable2, str4);
    }
  }
  
  public static BPWPayeeList getWirePayeeInfoByType(FFSConnectionHolder paramFFSConnectionHolder, BPWPayeeList paramBPWPayeeList)
    throws FFSException
  {
    FFSDebug.log("***WirePayee.getWirePayeeInfoByType start ...", 6);
    String str1 = null;
    Object[] arrayOfObject = null;
    String str2 = paramBPWPayeeList.getPayeeType();
    String str3 = paramBPWPayeeList.getCursorId();
    if ((str3 == null) || (str3.trim().length() == 0)) {
      str3 = "0";
    }
    FFSDebug.log("***WirePayee.getWirePayeeInfoByType Type:", str2, 6);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT  PayeeId, PayeeType, PayeeGroup, BeneficiaryName, NickName, ContactName, CustomerID, RouteID, PayeeExtID, ExtID, BeneficiaryBankId, BankAcctId, BankAcctType, BranchId, AcctKey, PayeeAddr1, PayeeAddr2, PayeeAddr3, PayeeCity, PayeeState, PayeeZipcode, PayeeCountry, PayeePhone, Status, Memo, SubmitDate, LastModDate, ActivationDate, LogId, SubmittedBy, PayeeDest  FROM BPW_WirePayee ");
    if (str2 != null)
    {
      localStringBuffer.append(" WHERE PayeeType = ? ");
      arrayOfObject = new Object[3];
      arrayOfObject[0] = str2;
      arrayOfObject[1] = "CLOSED";
      arrayOfObject[2] = str3;
    }
    else
    {
      str1 = jdMethod_if("PayeeType", paramBPWPayeeList.getPayeeTypeList());
      if (str1 != null) {
        localStringBuffer.append("WHERE ").append(str1);
      }
      arrayOfObject = new Object[2];
      arrayOfObject[0] = "CLOSED";
      arrayOfObject[1] = str3;
    }
    localStringBuffer.append(" AND Status != ? ");
    localStringBuffer.append("AND PayeeId > ? ");
    localStringBuffer.append(" AND PayeeGroup NOT IN ('");
    localStringBuffer.append("UNMANAGED").append("', '");
    localStringBuffer.append("SECURE").append("') ");
    localStringBuffer.append(" ORDER BY PayeeId ");
    try
    {
      FFSDebug.log("***WirePayee.getWirePayeeInfoByType args:" + arrayOfObject, 6);
      a(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject, paramBPWPayeeList);
      return paramBPWPayeeList;
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** WirePayee.getWirePayeeInfoByType failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable, str4);
    }
  }
  
  public static BPWPayeeList getWirePayeeInfoByStatus(FFSConnectionHolder paramFFSConnectionHolder, BPWPayeeList paramBPWPayeeList)
    throws FFSException
  {
    FFSDebug.log("***WirePayee.getWirePayeeInfoByStatus start ...", 6);
    String str1 = null;
    Object[] arrayOfObject = null;
    String str2 = paramBPWPayeeList.getRequiredStatus();
    String str3 = paramBPWPayeeList.getCursorId();
    FFSDebug.log("***WirePayee.getWirePayeeInfoByStatus reqStatus:", str2, 6);
    if ((str3 == null) || (str3.trim().length() == 0)) {
      str3 = "0";
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT  PayeeId, PayeeType, PayeeGroup, BeneficiaryName, NickName, ContactName, CustomerID, RouteID, PayeeExtID, ExtID, BeneficiaryBankId, BankAcctId, BankAcctType, BranchId, AcctKey, PayeeAddr1, PayeeAddr2, PayeeAddr3, PayeeCity, PayeeState, PayeeZipcode, PayeeCountry, PayeePhone, Status, Memo, SubmitDate, LastModDate, ActivationDate, LogId, SubmittedBy, PayeeDest  FROM BPW_WirePayee ");
    if (str2 != null)
    {
      localStringBuffer.append(" WHERE Status = ? ");
      arrayOfObject = new Object[2];
      arrayOfObject[0] = str2;
      arrayOfObject[1] = str3;
    }
    else
    {
      str1 = jdMethod_if("Status", paramBPWPayeeList.getPayeeStatusList());
      if (str1 != null) {
        localStringBuffer.append("WHERE ").append(str1);
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = str3;
    }
    localStringBuffer.append("AND PayeeId > ? ");
    localStringBuffer.append(" AND PayeeGroup NOT IN ('");
    localStringBuffer.append("UNMANAGED").append("', '");
    localStringBuffer.append("SECURE").append("') ");
    localStringBuffer.append(" ORDER BY PayeeId ");
    try
    {
      a(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject, paramBPWPayeeList);
      return paramBPWPayeeList;
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** WirePayee.getWirePayeeInfoByStatus failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable, str4);
    }
  }
  
  public static BPWPayeeList getWirePayeeInfoByGroup(FFSConnectionHolder paramFFSConnectionHolder, BPWPayeeList paramBPWPayeeList)
    throws FFSException
  {
    FFSDebug.log("***WirePayee.getWirePayeeInfoByGroup start ...", 6);
    String str1 = null;
    Object[] arrayOfObject = null;
    String str2 = paramBPWPayeeList.getPayeeGroup();
    String str3 = paramBPWPayeeList.getCursorId();
    FFSDebug.log("***WirePayee.getWirePayeeInfoByGroup Group:", str2, 6);
    if ((str3 == null) || (str3.trim().length() == 0)) {
      str3 = "0";
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT  PayeeId, PayeeType, PayeeGroup, BeneficiaryName, NickName, ContactName, CustomerID, RouteID, PayeeExtID, ExtID, BeneficiaryBankId, BankAcctId, BankAcctType, BranchId, AcctKey, PayeeAddr1, PayeeAddr2, PayeeAddr3, PayeeCity, PayeeState, PayeeZipcode, PayeeCountry, PayeePhone, Status, Memo, SubmitDate, LastModDate, ActivationDate, LogId, SubmittedBy, PayeeDest  FROM BPW_WirePayee ");
    if (str2 != null)
    {
      localStringBuffer.append(" WHERE PayeeGroup = ? ");
      arrayOfObject = new Object[3];
      arrayOfObject[0] = str2;
      arrayOfObject[1] = "CLOSED";
      arrayOfObject[2] = str3;
    }
    else
    {
      str1 = jdMethod_if("PayeeGroup", paramBPWPayeeList.getPayeeGroupList());
      if (str1 != null)
      {
        localStringBuffer.append("WHERE ").append(str1);
      }
      else
      {
        paramBPWPayeeList.setStatusCode(16020);
        paramBPWPayeeList.setStatusMsg(" record not found");
        return paramBPWPayeeList;
      }
      arrayOfObject = new Object[2];
      arrayOfObject[0] = "CLOSED";
      arrayOfObject[1] = str3;
    }
    localStringBuffer.append(" AND Status != ? ");
    localStringBuffer.append("AND PayeeId > ? ");
    localStringBuffer.append(" AND PayeeGroup NOT IN ('");
    localStringBuffer.append("UNMANAGED").append("', '");
    localStringBuffer.append("SECURE").append("') ");
    localStringBuffer.append(" ORDER BY PayeeId ");
    try
    {
      a(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject, paramBPWPayeeList);
      return paramBPWPayeeList;
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** WirePayee.getWirePayeeInfoByGroup failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable, str4);
    }
  }
  
  public static BPWPayeeList getWirePayeeInfoByCustomer(FFSConnectionHolder paramFFSConnectionHolder, BPWPayeeList paramBPWPayeeList)
    throws FFSException
  {
    FFSDebug.log("***WirePayee.getWirePayeeInfoByCustomer start ...", 6);
    String[] arrayOfString = null;
    String str1 = paramBPWPayeeList.getCustId();
    String str2 = paramBPWPayeeList.getCursorId();
    if (str1 == null)
    {
      localObject = "***WirePayee.getWirePayeeInfoByCustomer failed: customerId is null";
      FFSDebug.log((String)localObject, 0);
      paramBPWPayeeList.setStatusCode(16010);
      paramBPWPayeeList.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BPWPayeeList", "CustomerID " }, "WIRE_MESSAGE"));
      return paramBPWPayeeList;
    }
    Object localObject = new StringBuffer();
    ((StringBuffer)localObject).append("SELECT  PayeeId, PayeeType, PayeeGroup, BeneficiaryName, NickName, ContactName, CustomerID, RouteID, PayeeExtID, ExtID, BeneficiaryBankId, BankAcctId, BankAcctType, BranchId, AcctKey, PayeeAddr1, PayeeAddr2, PayeeAddr3, PayeeCity, PayeeState, PayeeZipcode, PayeeCountry, PayeePhone, Status, Memo, SubmitDate, LastModDate, ActivationDate, LogId, SubmittedBy, PayeeDest  FROM BPW_WirePayee ");
    ((StringBuffer)localObject).append(" WHERE CustomerID = ? ");
    ((StringBuffer)localObject).append(" AND Status != ? ");
    if (paramBPWPayeeList.getSubmittedBy() == null)
    {
      ((StringBuffer)localObject).append(" AND PayeeGroup = '");
      ((StringBuffer)localObject).append("BUSINESS").append("' ");
    }
    else
    {
      ((StringBuffer)localObject).append(" AND ( ( PayeeGroup = '");
      ((StringBuffer)localObject).append("BUSINESS").append("') ");
      ((StringBuffer)localObject).append(" OR ( PayeeGroup NOT IN ('");
      ((StringBuffer)localObject).append("BUSINESS").append("', '");
      ((StringBuffer)localObject).append("UNMANAGED").append("', '");
      ((StringBuffer)localObject).append("SECURE").append("') AND ");
      ((StringBuffer)localObject).append(jdMethod_if("SubmittedBy", paramBPWPayeeList.getSubmittedBy()));
      ((StringBuffer)localObject).append(") ) ");
    }
    arrayOfString = paramBPWPayeeList.getPayeeDestList();
    if ((arrayOfString != null) && (arrayOfString.length > 0))
    {
      ((StringBuffer)localObject).append(" AND ").append(jdMethod_if("PayeeDest", arrayOfString));
    }
    else
    {
      ((StringBuffer)localObject).append(" AND PayeeDest <> '");
      ((StringBuffer)localObject).append("CREDIT");
      ((StringBuffer)localObject).append("' ");
    }
    ((StringBuffer)localObject).append("AND PayeeId > ? ");
    ((StringBuffer)localObject).append(" ORDER BY PayeeId ");
    if ((str2 == null) || (str2.trim().length() == 0)) {
      str2 = "0";
    }
    Object[] arrayOfObject = { str1, "CLOSED", str2 };
    try
    {
      a(paramFFSConnectionHolder, ((StringBuffer)localObject).toString(), arrayOfObject, paramBPWPayeeList);
      return paramBPWPayeeList;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "*** WirePayee.getWirePayeeInfoByCustomer failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  private static BPWPayeeList a(FFSConnectionHolder paramFFSConnectionHolder, String paramString, Object[] paramArrayOfObject, BPWPayeeList paramBPWPayeeList)
    throws FFSException
  {
    FFSDebug.log("***WirePayee.getWirePayeeInfoByCol start ...", 6);
    ArrayList localArrayList = new ArrayList();
    WirePayeeInfo localWirePayeeInfo = null;
    long l = 0L;
    int i = 0;
    if (paramArrayOfObject != null) {
      for (int j = 0; j < paramArrayOfObject.length; j++) {
        FFSDebug.log("***WirePayee.getWirePayeeInfoByCol args[" + j + "]:" + paramArrayOfObject[j], 6);
      }
    }
    FFSDebug.log("***WirePayee.getWirePayeeInfoByCol args" + paramArrayOfObject, 6);
    FFSResultSet localFFSResultSet = null;
    BPWBankInfo localBPWBankInfo = null;
    BPWBankInfo[] arrayOfBPWBankInfo = null;
    try
    {
      l = paramBPWPayeeList.getPageSize();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString, paramArrayOfObject);
      while ((localFFSResultSet.getNextRow()) && ((l <= 0L) || (l != i)))
      {
        localWirePayeeInfo = new WirePayeeInfo();
        a(localFFSResultSet, localWirePayeeInfo);
        localBPWBankInfo = WireBank.getWireBanksByID(paramFFSConnectionHolder, localWirePayeeInfo.getBeneficiaryBankId());
        localWirePayeeInfo.setBeneficiaryBankInfo(localBPWBankInfo);
        arrayOfBPWBankInfo = WireBank.getWireBanksByPayee(paramFFSConnectionHolder, localWirePayeeInfo.getPayeeId());
        localWirePayeeInfo.setIntermediateBanks(arrayOfBPWBankInfo);
        localArrayList.add(localWirePayeeInfo);
        i++;
      }
      if (localArrayList.size() > 0)
      {
        localObject1 = (WirePayeeInfo[])localArrayList.toArray(new WirePayeeInfo[0]);
        paramBPWPayeeList.setPayees((Object[])localObject1);
        paramBPWPayeeList.setCursorId(localObject1[(localObject1.length - 1)].getPayeeId());
        paramBPWPayeeList.setStatusCode(0);
        paramBPWPayeeList.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      else
      {
        paramBPWPayeeList.setPayees(null);
        paramBPWPayeeList.setCursorId(null);
        paramBPWPayeeList.setStatusCode(16020);
        paramBPWPayeeList.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WirePayee" }, "WIRE_MESSAGE"));
      }
      FFSDebug.log("***WirePayee.getWirePayeeInfoByCol end", 6);
      paramBPWPayeeList.setTotalPayees(localArrayList.size());
      Object localObject1 = paramBPWPayeeList;
      return localObject1;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** WirePayee.getWirePayeeInfoByCol failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
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
          FFSDebug.log("***WirePayee.getWirePayeeInfoByCol failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
  }
  
  private static boolean jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    String str1 = null;
    CustomerInfo localCustomerInfo = null;
    if (paramWirePayeeInfo == null) {
      return false;
    }
    str1 = paramWirePayeeInfo.getCustomerID();
    if (str1 == null) {
      return false;
    }
    try
    {
      localCustomerInfo = Customer.getCustomerInfo(str1, paramFFSConnectionHolder, paramWirePayeeInfo);
      if ((localCustomerInfo == null) || (localCustomerInfo.getCustomerID() == null)) {
        return false;
      }
    }
    catch (Throwable localThrowable)
    {
      String str2 = "***WirePayee.validatePayee failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, str2);
    }
    return true;
  }
  
  private static boolean jdMethod_try(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    BPWBankInfo[] arrayOfBPWBankInfo = null;
    int i = 0;
    try
    {
      arrayOfBPWBankInfo = paramWirePayeeInfo.getIntermediateBanks();
      if (arrayOfBPWBankInfo != null) {
        i = arrayOfBPWBankInfo.length;
      }
      for (int j = 0; j < i; j++)
      {
        if (createPayeeIntermediateBankLink(paramFFSConnectionHolder, paramWirePayeeInfo.getPayeeId(), arrayOfBPWBankInfo[j], j + 1) == -1)
        {
          paramWirePayeeInfo.setStatusCode(arrayOfBPWBankInfo[j].getStatusCode());
          paramWirePayeeInfo.setStatusMsg(arrayOfBPWBankInfo[j].getStatusMsg());
          str = "*** WirePayee.addIntermediateBanks failed: " + arrayOfBPWBankInfo[j].getStatusMsg();
          FFSDebug.log(str, 0);
          return false;
        }
        paramWirePayeeInfo.setStatusCode(0);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
    }
    catch (Throwable localThrowable)
    {
      String str = "*** WirePayee.addIntermediateBanks failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
    return true;
  }
  
  private static boolean jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("***WirePayee.updateIntermediateBanks: start", 6);
    BPWBankInfo[] arrayOfBPWBankInfo = null;
    int i = 0;
    try
    {
      arrayOfBPWBankInfo = paramWirePayeeInfo.getIntermediateBanks();
      if (arrayOfBPWBankInfo != null) {
        i = arrayOfBPWBankInfo.length;
      }
      for (int j = 0; j < i; j++) {
        if ((arrayOfBPWBankInfo[j].getBankId() == null) || (arrayOfBPWBankInfo[j].getBankId().trim().length() == 0))
        {
          if (createPayeeIntermediateBankLink(paramFFSConnectionHolder, paramWirePayeeInfo.getPayeeId(), arrayOfBPWBankInfo[j], j + 1) == -1)
          {
            paramWirePayeeInfo.setStatusCode(arrayOfBPWBankInfo[j].getStatusCode());
            paramWirePayeeInfo.setStatusMsg(arrayOfBPWBankInfo[j].getStatusMsg());
            return false;
          }
          paramWirePayeeInfo.setStatusCode(0);
          paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        }
        else if (arrayOfBPWBankInfo[j].getAction().equals("del"))
        {
          if (!removePayeeIntermediateBankLink(paramFFSConnectionHolder, paramWirePayeeInfo.getPayeeId(), arrayOfBPWBankInfo[j])) {
            return false;
          }
          paramWirePayeeInfo.setStatusCode(0);
          paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        }
        else if (arrayOfBPWBankInfo[j].getAction().equals("mod"))
        {
          arrayOfBPWBankInfo[j] = WireBank.modWireBank(paramFFSConnectionHolder, arrayOfBPWBankInfo[j]);
          if (arrayOfBPWBankInfo[j].getStatusCode() != 0)
          {
            paramWirePayeeInfo.setStatusCode(arrayOfBPWBankInfo[j].getStatusCode());
            paramWirePayeeInfo.setStatusMsg(arrayOfBPWBankInfo[j].getStatusMsg());
            return false;
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str = "***WirePayee.updateIntermediateBanks failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
    return true;
  }
  
  private static boolean e(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("***WirePayee.existsPayeeIntermediateBankLink start ...", 6);
    FFSResultSet localFFSResultSet = null;
    String str1 = "SELECT LinkId, BankId, PayeeId FROM BPW_PayeeIntrmdBnks  WHERE PayeeId = ?  AND BankId = ? ";
    Object[] arrayOfObject = { paramString1, paramString2 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        boolean bool = true;
        return bool;
      }
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** WirePayee.existsPayeeIntermediateBankLink failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
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
          FFSDebug.log("***WirePayee.existsPayeeIntermediateBankLink failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
    return false;
  }
  
  public static int createPayeeIntermediateBankLink(FFSConnectionHolder paramFFSConnectionHolder, String paramString, BPWBankInfo paramBPWBankInfo, int paramInt)
    throws FFSException
  {
    String str1 = "INSERT INTO BPW_PayeeIntrmdBnks  ( LinkId, BankId, PayeeId,  Rank)  VALUES (?,?,?,?) ";
    String str2 = null;
    int i = 0;
    try
    {
      if ((paramBPWBankInfo.getBankId() == null) || (paramBPWBankInfo.getBankId().trim().length() == 0))
      {
        paramBPWBankInfo = WireBank.addWireBank(paramFFSConnectionHolder, paramBPWBankInfo);
        if (paramBPWBankInfo.getStatusCode() != 0) {
          return -1;
        }
        i++;
      }
      FFSDebug.log("WirePayee.createPayeeIntermediateBankLink:bankInfo:", "adding link for the bank:", paramBPWBankInfo.toString(), 6);
      if (e(paramFFSConnectionHolder, paramString, paramBPWBankInfo.getBankId()))
      {
        FFSDebug.log("WirePayee.createPayeeIntermediateBankLink:", "a link between PayeeId:", paramString, " and Bank ID::", paramBPWBankInfo.getBankId(), " already exists", 6);
        return i;
      }
      str2 = DBUtil.getNextIndexStringWithPadding("WIREPAYEEBANKLINKID", 10, '0');
      FFSDebug.log("WirePayee.createPayeeIntermediateBankLink", ": linkId:", str2, ", BankId:", paramBPWBankInfo.getBankId(), ", PayeeId:", paramString, ", Rank:" + paramInt, 6);
      Object[] arrayOfObject = { str2, paramBPWBankInfo.getBankId(), paramString, String.valueOf(paramInt) };
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
      FFSDebug.log("WirePayee.createPayeeIntermediateBankLink", " end", 6);
    }
    catch (Throwable localThrowable)
    {
      String str3 = "WirePayee.createPayeeIntermediateBankLink failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
    return i;
  }
  
  public static boolean removePayeeIntermediateBankLink(FFSConnectionHolder paramFFSConnectionHolder, String paramString, BPWBankInfo paramBPWBankInfo)
    throws FFSException
  {
    String str1 = "DELETE FROM BPW_PayeeIntrmdBnks WHERE BankId = ?  AND PayeeId = ?";
    FFSDebug.log("***WirePayee.removePayeeIntermediateBankLink start", 6);
    FFSDebug.log("***WirePayee.removePayeeIntermediateBankLink ", "BankId:", paramBPWBankInfo.getBankId(), "PayeeId:", paramString, 6);
    try
    {
      if ((paramBPWBankInfo.getBankId() == null) || (paramBPWBankInfo.getBankId().trim().length() == 0) || (paramString == null) || (paramString.trim().length() == 0))
      {
        localObject = "***WirePayee.removePayeeIntermediateBankLink failed: BankId or PayeeId are null";
        FFSDebug.log((String)localObject, 0);
        return false;
      }
      Object localObject = { paramBPWBankInfo.getBankId(), paramString };
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, (Object[])localObject);
      FFSDebug.log("***WirePayee.removePayeeIntermediateBankLink end", 6);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "***WirePayee.removePayeeIntermediateBankLink failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, str2);
    }
    return true;
  }
  
  public static String[] getPayeeIdsByBankId(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    StringBuffer localStringBuffer = null;
    String str1 = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = null;
    String str2 = "WirePayee.getPayeeIdsByBankId: ";
    FFSDebug.log(str2, "start", 6);
    localStringBuffer = new StringBuffer();
    if (paramBoolean)
    {
      localStringBuffer.append("SELECT PayeeId From BPW_WirePayee WHERE BeneficiaryBankId = ?");
      localStringBuffer.append(" AND Status != ? ");
    }
    else
    {
      localStringBuffer.append("SELECT a.PayeeId From BPW_PayeeIntrmdBnks a ");
      localStringBuffer.append(", ");
      localStringBuffer.append("BPW_WirePayee");
      localStringBuffer.append(" b ");
      localStringBuffer.append(" WHERE a.PayeeId = b.PayeeId ");
      localStringBuffer.append(" AND a.BankId = ? ");
      localStringBuffer.append(" AND b.Status != ? ");
    }
    try
    {
      Object[] arrayOfObject = { paramString, "CLOSED" };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      localArrayList = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString("PayeeId");
        localArrayList.add(str1);
      }
      FFSDebug.log(str2, "payeeIds found " + localArrayList.size(), 6);
      FFSDebug.log(str2, "end", 6);
      if (localArrayList.size() == 0) {
        return null;
      }
      return (String[])localArrayList.toArray(new String[0]);
    }
    catch (Throwable localThrowable)
    {
      String str3 = str2 + "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  private static boolean a(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "WirePayee.validateBanks: ";
    String str2 = null;
    Object localObject1 = null;
    BPWBankInfo[] arrayOfBPWBankInfo = null;
    int i = 0;
    FFSDebug.log(str1, "start", 6);
    FFSDebug.log(str1, "beneficiaryBankId = " + paramWirePayeeInfo.getBeneficiaryBankId(), 6);
    FFSDebug.log(str1, "beneficiaryBankInfo = " + paramWirePayeeInfo.getBeneficiaryBankInfo(), 6);
    try
    {
      str2 = paramWirePayeeInfo.getBeneficiaryBankId();
      BPWBankInfo localBPWBankInfo = paramWirePayeeInfo.getBeneficiaryBankInfo();
      FFSDebug.log(str1, "modifyInRecord = " + paramBoolean, 6);
      if ((paramBoolean == true) && (localBPWBankInfo != null))
      {
        FFSDebug.log(str1, "modifyInRecord needed ... ", 6);
        str3 = localBPWBankInfo.getBankId();
        Object localObject2;
        if ((str3 == null) || (str3.trim().length() == 0))
        {
          FFSDebug.log(str1, "adding a new bank for beneficiary during modifyInRecord", 6);
          localObject1 = WireBank.addWireBank(paramFFSConnectionHolder, localBPWBankInfo);
          if (((BPWBankInfo)localObject1).getStatusCode() != 0)
          {
            localObject2 = str1 + "failed: implicit bank addition failed. " + ((BPWBankInfo)localObject1).getStatusMsg();
            FFSDebug.log("****", (String)localObject2, " ", localBPWBankInfo.toString(), 0);
            paramWirePayeeInfo.setStatusCode(((BPWBankInfo)localObject1).getStatusCode());
            paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(((BPWBankInfo)localObject1).getStatusCode(), new String[] { str1, ((BPWBankInfo)localObject1).getStatusMsg() }, "WIRE_MESSAGE"));
            return false;
          }
        }
        else
        {
          localObject2 = WireBank.getWireBanksByID(paramFFSConnectionHolder, str3);
          if ((((BPWBankInfo)localObject2).getStatusCode() == 0) && (!((BPWBankInfo)localObject2).equals(localBPWBankInfo)))
          {
            FFSDebug.log(str1, "modifying a bank for beneficiary", 6);
            localBPWBankInfo = WireBank.modWireBank(paramFFSConnectionHolder, localBPWBankInfo);
            if (localBPWBankInfo.getStatusCode() != 0)
            {
              paramWirePayeeInfo.setStatusCode(localBPWBankInfo.getStatusCode());
              paramWirePayeeInfo.setStatusMsg(localBPWBankInfo.getStatusMsg());
              return false;
            }
          }
          localObject1 = localBPWBankInfo;
        }
      }
      else
      {
        if ((localBPWBankInfo == null) && (str2 != null) && (str2.trim().length() > 0))
        {
          localBPWBankInfo = WireBank.getWireBanksByID(paramFFSConnectionHolder, str2);
          FFSDebug.log(str1, "retrieved bankInfo: " + localObject1, 6);
        }
        if (localBPWBankInfo != null)
        {
          FFSDebug.log(str1, "creating a new bank for beneficiary", 6);
          localObject1 = WireBank.addWireBank(paramFFSConnectionHolder, localBPWBankInfo);
          if (((BPWBankInfo)localObject1).getStatusCode() != 0)
          {
            str3 = str1 + "failed: implicit bank addition failed. " + ((BPWBankInfo)localObject1).getStatusMsg();
            FFSDebug.log("****", str3, " ", localBPWBankInfo.toString(), 0);
            paramWirePayeeInfo.setStatusCode(((BPWBankInfo)localObject1).getStatusCode());
            paramWirePayeeInfo.setStatusMsg(str3);
            return false;
          }
        }
      }
      FFSDebug.log(str1, "****+++++++bankInfo2: " + localObject1, 6);
      if ((localObject1 == null) || (((BPWBankInfo)localObject1).getStatusCode() == 16020))
      {
        paramWirePayeeInfo.setStatusCode(19250);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(19250, null, "WIRE_MESSAGE"));
        return false;
      }
      str3 = ((BPWBankInfo)localObject1).getBankId();
      FFSDebug.log(str1, "****+++++++bId: ", str3, 6);
      if ((str3 != null) && (str3.length() > 0)) {
        paramWirePayeeInfo.setBeneficiaryBankId(str3);
      }
      if (paramWirePayeeInfo.getBeneficiaryBankInfo() == null) {
        paramWirePayeeInfo.setBeneficiaryBankInfo((BPWBankInfo)localObject1);
      }
      arrayOfBPWBankInfo = paramWirePayeeInfo.getIntermediateBanks();
      if (arrayOfBPWBankInfo != null) {
        i = arrayOfBPWBankInfo.length;
      }
      for (int j = 0; j < i; j++)
      {
        FFSDebug.log(str1, "intermdtBank:" + arrayOfBPWBankInfo[j], 6);
        FFSDebug.log(str1, "Action", arrayOfBPWBankInfo[j].getAction(), 6);
        StringBuffer localStringBuffer;
        if ((arrayOfBPWBankInfo[j].getAction().equals("del")) || (arrayOfBPWBankInfo[j].getAction().equals("mod")))
        {
          str2 = arrayOfBPWBankInfo[j].getBankId();
          if ((str2 == null) || (str2.trim().length() == 0))
          {
            paramWirePayeeInfo.setStatusCode(16010);
            paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { " Intermediary Bank info ", "BankId" }, "WIRE_MESSAGE"));
            return false;
          }
          localObject1 = WireBank.getWireBanksByID(paramFFSConnectionHolder, str2);
          if (localObject1 == null)
          {
            localStringBuffer = new StringBuffer();
            localStringBuffer.append("The intermediate Wire Bank does not exist in BPW database.");
            localStringBuffer.append(" BankInfo::");
            localStringBuffer.append(arrayOfBPWBankInfo[j].toString());
            paramWirePayeeInfo.setStatusCode(19260);
            paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(19260, new String[] { " BankInfo:: ", arrayOfBPWBankInfo[j].toString() }, "WIRE_MESSAGE"));
            return false;
          }
        }
        else
        {
          str2 = arrayOfBPWBankInfo[j].getBankId();
          if ((str2 != null) && (str2.trim().length() > 0)) {
            localObject1 = WireBank.getWireBanksByID(paramFFSConnectionHolder, str2);
          } else {
            localObject1 = arrayOfBPWBankInfo[j];
          }
          if (localObject1 == null)
          {
            localStringBuffer = new StringBuffer();
            localStringBuffer.append("The intermediate Wire Bank does not exist in BPW database.");
            localStringBuffer.append(" BankInfo::");
            localStringBuffer.append(arrayOfBPWBankInfo[j].toString());
            paramWirePayeeInfo.setStatusCode(19260);
            paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(19260, new String[] { " BankInfo:: ", arrayOfBPWBankInfo[j].toString() }, "WIRE_MESSAGE"));
            return false;
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, str3, 0);
      throw new FFSException(localThrowable, str3);
    }
    return true;
  }
  
  private static String jdMethod_if(String paramString, String[] paramArrayOfString)
  {
    FFSDebug.log("WirePayee.getSQLINCondition: Start", 6);
    StringBuffer localStringBuffer1 = null;
    StringBuffer localStringBuffer2 = null;
    int i = 0;
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      FFSDebug.log("WirePayee.getSQLINCondition: Column Name is not specified", 0);
      return null;
    }
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      FFSDebug.log("WirePayee.getSQLINCondition: No value is specified for the column", 6);
      return null;
    }
    localStringBuffer1 = new StringBuffer();
    localStringBuffer1.append(" ").append(paramString).append(" IN (");
    localStringBuffer2 = new StringBuffer();
    for (int j = 0; j < paramArrayOfString.length; j++) {
      if ((!paramString.equals("PayeeGroup")) || ((!"UNMANAGED".equals(paramArrayOfString[j])) && (!"SECURE".equals(paramArrayOfString[j]))))
      {
        if (i != 0) {
          localStringBuffer2.append(", ");
        }
        localStringBuffer2.append(" '").append(paramArrayOfString[j]).append("' ");
        i = 1;
      }
    }
    if (localStringBuffer2.toString().trim().length() == 0) {
      return null;
    }
    localStringBuffer1.append(localStringBuffer2).append(" ) ");
    FFSDebug.log("WirePayee.getSQLINCondition: Done", 6);
    return localStringBuffer1.toString();
  }
  
  public static WirePayeeInfo getWirePayeeByExtId(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("***WirePayee.getWirePayeeByExtId start ...", 6);
    WirePayeeInfo localWirePayeeInfo1 = null;
    FFSResultSet localFFSResultSet = null;
    BPWBankInfo localBPWBankInfo = null;
    BPWBankInfo[] arrayOfBPWBankInfo = null;
    String str1 = null;
    if (paramWirePayeeInfo == null)
    {
      localObject1 = "***WirePayee.getWirePayeeByExtId failed: payeeInfo is null";
      FFSDebug.log((String)localObject1, 0);
      localWirePayeeInfo1 = new WirePayeeInfo();
      localWirePayeeInfo1.setStatusCode(16000);
      localWirePayeeInfo1.setStatusMsg(BPWLocaleUtil.getMessage(16000, null, "WIRE_MESSAGE"));
      return localWirePayeeInfo1;
    }
    str1 = paramWirePayeeInfo.getPayeeExtId();
    if ((str1 == null) || (str1.trim().length() == 0))
    {
      localObject1 = "***WirePayee.getWirePayeeByExtId failed: extPayeeId is null";
      FFSDebug.log((String)localObject1, 0);
      localWirePayeeInfo1 = new WirePayeeInfo();
      localWirePayeeInfo1.setStatusCode(16000);
      localWirePayeeInfo1.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "extPayeeId " }, "WIRE_MESSAGE"));
      return localWirePayeeInfo1;
    }
    Object localObject1 = new StringBuffer();
    ((StringBuffer)localObject1).append("SELECT  PayeeId, PayeeType, PayeeGroup, BeneficiaryName, NickName, ContactName, CustomerID, RouteID, PayeeExtID, ExtID, BeneficiaryBankId, BankAcctId, BankAcctType, BranchId, AcctKey, PayeeAddr1, PayeeAddr2, PayeeAddr3, PayeeCity, PayeeState, PayeeZipcode, PayeeCountry, PayeePhone, Status, Memo, SubmitDate, LastModDate, ActivationDate, LogId, SubmittedBy, PayeeDest  FROM BPW_WirePayee WHERE PayeeExtID = ? ");
    ((StringBuffer)localObject1).append(" AND Status != ? ");
    Object[] arrayOfObject = { str1, "CLOSED" };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, ((StringBuffer)localObject1).toString(), arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        a(localFFSResultSet, paramWirePayeeInfo);
        localBPWBankInfo = WireBank.getWireBanksByID(paramFFSConnectionHolder, paramWirePayeeInfo.getBeneficiaryBankId());
        paramWirePayeeInfo.setBeneficiaryBankInfo(localBPWBankInfo);
        arrayOfBPWBankInfo = WireBank.getWireBanksByPayee(paramFFSConnectionHolder, paramWirePayeeInfo.getPayeeId());
        paramWirePayeeInfo.setIntermediateBanks(arrayOfBPWBankInfo);
        paramWirePayeeInfo.setStatusCode(0);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      else
      {
        paramWirePayeeInfo.setStatusCode(16020);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      }
      FFSDebug.log("***WirePayee.getWirePayeeByExtId end", 6);
      WirePayeeInfo localWirePayeeInfo2 = paramWirePayeeInfo;
      return localWirePayeeInfo2;
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** WirePayee.getWirePayeeByExtId failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
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
          FFSDebug.log("***WirePayee.getWirePayeeByExtId failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
  }
  
  public static WirePayeeInfo getWirePayeeByBankIdAcctIdCustId(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    FFSDebug.log("***WirePayee.getWirePayeeByBankIdAcctIdCustId start ...", 6);
    WirePayeeInfo localWirePayeeInfo1 = null;
    FFSResultSet localFFSResultSet = null;
    BPWBankInfo localBPWBankInfo = null;
    BPWBankInfo[] arrayOfBPWBankInfo = null;
    ArrayList localArrayList = null;
    String str1 = null;
    int i = 0;
    if (paramWirePayeeInfo == null)
    {
      localObject1 = "***WirePayee.getWirePayeeByBankIdAcctIdCustId failed: payeeInfo is null";
      FFSDebug.log((String)localObject1, 0);
      localWirePayeeInfo1 = new WirePayeeInfo();
      localWirePayeeInfo1.setStatusCode(16000);
      localWirePayeeInfo1.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "payeeInfo " }, "WIRE_MESSAGE"));
      return localWirePayeeInfo1;
    }
    Object localObject1 = new StringBuffer();
    localArrayList = new ArrayList();
    str1 = paramWirePayeeInfo.getSubmittedBy();
    a(paramWirePayeeInfo, (StringBuffer)localObject1, localArrayList);
    ((StringBuffer)localObject1).append(" AND Status != ? ");
    localArrayList.add("CLOSED");
    Object[] arrayOfObject = localArrayList.toArray(new Object[0]);
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, ((StringBuffer)localObject1).toString(), arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        i = 1;
        a(localFFSResultSet, paramWirePayeeInfo);
        FFSDebug.log("***WirePayee.getWirePayeeByBankIdAcctIdCustId ", "submittedBy=", str1, "payeeInfo.getSubmittedBy()", paramWirePayeeInfo.getSubmittedBy(), 6);
        if (str1.equals(paramWirePayeeInfo.getSubmittedBy())) {
          break;
        }
      }
      if (i != 0)
      {
        localBPWBankInfo = WireBank.getWireBanksByID(paramFFSConnectionHolder, paramWirePayeeInfo.getBeneficiaryBankId());
        paramWirePayeeInfo.setBeneficiaryBankInfo(localBPWBankInfo);
        arrayOfBPWBankInfo = WireBank.getWireBanksByPayee(paramFFSConnectionHolder, paramWirePayeeInfo.getPayeeId());
        paramWirePayeeInfo.setIntermediateBanks(arrayOfBPWBankInfo);
        paramWirePayeeInfo.setStatusCode(0);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      else
      {
        paramWirePayeeInfo.setStatusCode(16020);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      }
      FFSDebug.log("***WirePayee.getWirePayeeByBankIdAcctIdCustId end", 6);
      WirePayeeInfo localWirePayeeInfo2 = paramWirePayeeInfo;
      return localWirePayeeInfo2;
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** WirePayee.getWirePayeeByBankIdAcctIdCustId failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
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
          FFSDebug.log("***WirePayee.getWirePayeeByBankIdAcctIdCustId failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
  }
  
  private static void a(WirePayeeInfo paramWirePayeeInfo, StringBuffer paramStringBuffer, ArrayList paramArrayList)
  {
    String str1 = "WirePayee.buildGetSqlByBankIdAcctIdCustId";
    FFSDebug.log("***", str1, " start ...", 6);
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    paramStringBuffer.append("SELECT  PayeeId, PayeeType, PayeeGroup, BeneficiaryName, NickName, ContactName, CustomerID, RouteID, PayeeExtID, ExtID, BeneficiaryBankId, BankAcctId, BankAcctType, BranchId, AcctKey, PayeeAddr1, PayeeAddr2, PayeeAddr3, PayeeCity, PayeeState, PayeeZipcode, PayeeCountry, PayeePhone, Status, Memo, SubmitDate, LastModDate, ActivationDate, LogId, SubmittedBy, PayeeDest  FROM BPW_WirePayee  WHERE BeneficiaryBankId = ?  AND BankAcctId = ?  AND CustomerID = ? ");
    str6 = paramWirePayeeInfo.getPayeeGroup();
    str2 = paramWirePayeeInfo.getBeneficiaryBankId();
    str3 = paramWirePayeeInfo.getBankAcctId();
    str4 = paramWirePayeeInfo.getCustomerID();
    str5 = paramWirePayeeInfo.getSubmittedBy();
    paramArrayList.add(str2);
    paramArrayList.add(str3);
    paramArrayList.add(str4);
    if ("BUSINESS".equals(str6))
    {
      paramStringBuffer.append(" AND ( ( SubmittedBy = ? ");
      paramArrayList.add(str5);
      paramStringBuffer.append(" AND PayeeGroup <> ? ) ");
      paramArrayList.add("BUSINESS");
      paramStringBuffer.append(" OR PayeeGroup = ? ) ");
      paramArrayList.add("BUSINESS");
    }
    else
    {
      paramStringBuffer.append(" AND SubmittedBy = ? ");
      paramArrayList.add(str5);
    }
  }
  
  public static WirePayeeInfo findDuplicateWirePayee(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    String str1 = "WirePayee.findDuplicateWirePayee: ";
    FFSDebug.log(str1, ": start", 6);
    FFSResultSet localFFSResultSet = null;
    BPWBankInfo localBPWBankInfo = null;
    BPWBankInfo[] arrayOfBPWBankInfo = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    ArrayList localArrayList = null;
    StringBuffer localStringBuffer = null;
    int i = 0;
    if (paramWirePayeeInfo == null)
    {
      localObject1 = " failed: payeeInfo is null";
      FFSDebug.log(str1, (String)localObject1, 0);
      return null;
    }
    localStringBuffer = new StringBuffer();
    localArrayList = new ArrayList();
    str6 = paramWirePayeeInfo.getBeneficiaryBankId();
    localBPWBankInfo = paramWirePayeeInfo.getBeneficiaryBankInfo();
    FFSDebug.log(str1, "beneficiaryBankId=" + str6, 6);
    FFSDebug.log(str1, "beneficiaryBankInfo=" + localBPWBankInfo, 6);
    if (localBPWBankInfo != null)
    {
      localStringBuffer.append("SELECT  PayeeId, PayeeType, PayeeGroup, BeneficiaryName, NickName, ContactName, CustomerID, RouteID, PayeeExtID, ExtID, BeneficiaryBankId, BankAcctId, BankAcctType, BranchId, AcctKey, PayeeAddr1, PayeeAddr2, PayeeAddr3, PayeeCity, PayeeState, PayeeZipcode, PayeeCountry, PayeePhone, Status, Memo, SubmitDate, LastModDate, ActivationDate, LogId, SubmittedBy, PayeeDest  FROM BPW_WirePayee a,  BPW_WireBank b  WHERE a.BeneficiaryBankId = b.BankId ");
      str2 = localBPWBankInfo.getFedRTN();
      str3 = localBPWBankInfo.getSwiftRTN();
      str4 = localBPWBankInfo.getChipsRTN();
      str5 = localBPWBankInfo.getOtherRTN();
      if (((str2 == null) || (str2.trim().length() == 0)) && ((str3 == null) || (str3.trim().length() == 0)) && ((str4 == null) || (str4.trim().length() == 0)) && ((str5 == null) || (str5.trim().length() == 0)))
      {
        localStringBuffer.append(" AND b.FedRTN is NULL ");
        localStringBuffer.append(" AND b.SwiftRTN is NULL ");
        localStringBuffer.append(" AND b.ChipsRTN is NULL ");
        localStringBuffer.append(" AND b.OtherRTN is NULL ");
      }
      else
      {
        localStringBuffer.append(" AND ( ");
        if ((localBPWBankInfo.getFedRTN() != null) && (localBPWBankInfo.getFedRTN().length() > 0))
        {
          localArrayList.add(localBPWBankInfo.getFedRTN());
          localStringBuffer.append(" b.FedRTN = ? ");
          i = 1;
        }
        if ((localBPWBankInfo.getSwiftRTN() != null) && (localBPWBankInfo.getSwiftRTN().length() > 0))
        {
          localArrayList.add(localBPWBankInfo.getSwiftRTN());
          if (i != 0) {
            localStringBuffer.append(" OR ");
          }
          localStringBuffer.append(" b.SwiftRTN = ? ");
          i = 1;
        }
        if ((localBPWBankInfo.getChipsRTN() != null) && (localBPWBankInfo.getChipsRTN().length() > 0))
        {
          localArrayList.add(localBPWBankInfo.getChipsRTN());
          if (i != 0) {
            localStringBuffer.append(" OR ");
          }
          localStringBuffer.append(" b.ChipsRTN = ? ");
          i = 1;
        }
        if ((localBPWBankInfo.getOtherRTN() != null) && (localBPWBankInfo.getOtherRTN().length() > 0))
        {
          localArrayList.add(localBPWBankInfo.getOtherRTN());
          if (i != 0) {
            localStringBuffer.append(" OR ");
          }
          localStringBuffer.append(" b.OtherRTN = ? ");
          i = 1;
        }
        localStringBuffer.append(" ) ");
      }
    }
    else if ((str6 != null) && (str6.trim().length() > 0))
    {
      localStringBuffer.append("SELECT  PayeeId, PayeeType, PayeeGroup, BeneficiaryName, NickName, ContactName, CustomerID, RouteID, PayeeExtID, ExtID, BeneficiaryBankId, BankAcctId, BankAcctType, BranchId, AcctKey, PayeeAddr1, PayeeAddr2, PayeeAddr3, PayeeCity, PayeeState, PayeeZipcode, PayeeCountry, PayeePhone, Status, Memo, SubmitDate, LastModDate, ActivationDate, LogId, SubmittedBy, PayeeDest  FROM BPW_WirePayee  WHERE BeneficiaryBankId = ? ");
      localArrayList.add(str6);
    }
    if (paramWirePayeeInfo.getBeneficiaryName() == null)
    {
      localStringBuffer.append(" AND BeneficiaryName is NULL ");
    }
    else
    {
      localStringBuffer.append(" AND BeneficiaryName = ? ");
      localArrayList.add(paramWirePayeeInfo.getBeneficiaryName());
    }
    if ((paramWirePayeeInfo.getNickName() != null) && (paramWirePayeeInfo.getNickName().trim().length() != 0))
    {
      localStringBuffer.append(" AND NickName = ? ");
      localArrayList.add(paramWirePayeeInfo.getNickName());
    }
    if (paramWirePayeeInfo.getCustomerID() == null)
    {
      localStringBuffer.append(" AND CustomerID is NULL ");
    }
    else
    {
      localStringBuffer.append(" AND CustomerID = ? ");
      localArrayList.add(paramWirePayeeInfo.getCustomerID());
    }
    if (paramWirePayeeInfo.getBankAcctId() == null)
    {
      localStringBuffer.append(" AND BankAcctId is NULL ");
    }
    else
    {
      localStringBuffer.append(" AND BankAcctId = ? ");
      localArrayList.add(paramWirePayeeInfo.getBankAcctId());
    }
    if (paramWirePayeeInfo.getBankAcctType() == null)
    {
      localStringBuffer.append(" AND BankAcctType is NULL ");
    }
    else
    {
      localStringBuffer.append(" AND BankAcctType = ? ");
      localArrayList.add(paramWirePayeeInfo.getBankAcctType());
    }
    if (paramWirePayeeInfo.getPayeeDest() == null)
    {
      localStringBuffer.append(" AND PayeeDest is NULL ");
    }
    else
    {
      localStringBuffer.append(" AND PayeeDest = ? ");
      localArrayList.add(paramWirePayeeInfo.getPayeeDest());
    }
    if ("BUSINESS".equals(paramWirePayeeInfo.getPayeeGroup()))
    {
      localStringBuffer.append(" AND PayeeGroup = ?  ");
      localArrayList.add("BUSINESS");
    }
    else
    {
      localStringBuffer.append(" AND PayeeGroup <> ?  ");
      localArrayList.add("BUSINESS");
      localStringBuffer.append(" AND SubmittedBy = ? ");
      localArrayList.add(paramWirePayeeInfo.getSubmittedBy());
    }
    localStringBuffer.append(" AND Status != ? ");
    localArrayList.add("CLOSED");
    Object localObject1 = localArrayList.toArray(new Object[0]);
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        if (paramWirePayeeInfo.getPayeeId() != null)
        {
          String str7 = localFFSResultSet.getColumnString("PayeeId");
          if (paramWirePayeeInfo.getPayeeId().equals(str7))
          {
            paramWirePayeeInfo.setStatusCode(0);
            paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
            FFSDebug.log("***", str1, "WirePayeeInfo ", " record not found", 6);
            FFSDebug.log(str1, ": done", 6);
            localObject2 = paramWirePayeeInfo;
            return localObject2;
          }
        }
        a(localFFSResultSet, paramWirePayeeInfo);
        localBPWBankInfo = WireBank.getWireBanksByID(paramFFSConnectionHolder, paramWirePayeeInfo.getBeneficiaryBankId());
        paramWirePayeeInfo.setBeneficiaryBankInfo(localBPWBankInfo);
        arrayOfBPWBankInfo = WireBank.getWireBanksByPayee(paramFFSConnectionHolder, paramWirePayeeInfo.getPayeeId());
        paramWirePayeeInfo.setIntermediateBanks(arrayOfBPWBankInfo);
        paramWirePayeeInfo.setStatusCode(23330);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(23330, new String[] { "Payee ID: " + paramWirePayeeInfo.getPayeeId() }, "WIRE_MESSAGE"));
      }
      else
      {
        paramWirePayeeInfo.setStatusCode(0);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        FFSDebug.log("***", str1, "WirePayeeInfo ", " record not found", 6);
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject2 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, (String)localObject2, 0);
      throw new FFSException(localThrowable, (String)localObject2);
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
          FFSDebug.log("***WirePayee.findDuplicateWirePayee failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return paramWirePayeeInfo;
  }
  
  private static WirePayeeInfo jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    String str1 = "WirePayee.findDuplicateExtId: ";
    FFSDebug.log(str1, ": start", 6);
    FFSResultSet localFFSResultSet = null;
    BPWBankInfo localBPWBankInfo = null;
    BPWBankInfo[] arrayOfBPWBankInfo = null;
    StringBuffer localStringBuffer = null;
    if (paramWirePayeeInfo == null)
    {
      localObject1 = " failed: payeeInfo is null";
      FFSDebug.log(str1, (String)localObject1, 0);
      return null;
    }
    if (paramWirePayeeInfo.getExtId() == null)
    {
      paramWirePayeeInfo.setStatusCode(0);
      paramWirePayeeInfo.setStatusMsg("Success");
      return paramWirePayeeInfo;
    }
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT  PayeeId, PayeeType, PayeeGroup, BeneficiaryName, NickName, ContactName, CustomerID, RouteID, PayeeExtID, ExtID, BeneficiaryBankId, BankAcctId, BankAcctType, BranchId, AcctKey, PayeeAddr1, PayeeAddr2, PayeeAddr3, PayeeCity, PayeeState, PayeeZipcode, PayeeCountry, PayeePhone, Status, Memo, SubmitDate, LastModDate, ActivationDate, LogId, SubmittedBy, PayeeDest  FROM BPW_WirePayee WHERE ExtID = ? ");
    localStringBuffer.append(" AND Status != ? ");
    Object localObject1 = { paramWirePayeeInfo.getExtId(), "CLOSED" };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        if (paramWirePayeeInfo.getPayeeId() != null)
        {
          String str2 = localFFSResultSet.getColumnString("PayeeId");
          if (paramWirePayeeInfo.getPayeeId().equals(str2))
          {
            paramWirePayeeInfo.setStatusCode(0);
            paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
            FFSDebug.log("***", str1, "WirePayeeInfo ", " record not found", 6);
            FFSDebug.log(str1, ": done", 6);
            localObject2 = paramWirePayeeInfo;
            return localObject2;
          }
        }
        a(localFFSResultSet, paramWirePayeeInfo);
        localBPWBankInfo = WireBank.getWireBanksByID(paramFFSConnectionHolder, paramWirePayeeInfo.getBeneficiaryBankId());
        paramWirePayeeInfo.setBeneficiaryBankInfo(localBPWBankInfo);
        arrayOfBPWBankInfo = WireBank.getWireBanksByPayee(paramFFSConnectionHolder, paramWirePayeeInfo.getPayeeId());
        paramWirePayeeInfo.setIntermediateBanks(arrayOfBPWBankInfo);
        paramWirePayeeInfo.setStatusCode(19590);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(19590, new String[] { "ExtID: " + paramWirePayeeInfo.getExtId() }, "WIRE_MESSAGE"));
      }
      else
      {
        paramWirePayeeInfo.setStatusCode(0);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        FFSDebug.log("***", str1, "WirePayeeInfo ", " record not found", 6);
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject2 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, (String)localObject2, 0);
      throw new FFSException(localThrowable, (String)localObject2);
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
          FFSDebug.log("***WirePayee.findDuplicateExtId failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return paramWirePayeeInfo;
  }
  
  private static WirePayeeInfo jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    String str1 = "WirePayee.findDuplicatePayeeExtId: ";
    FFSDebug.log(str1, ": start", 6);
    FFSResultSet localFFSResultSet = null;
    BPWBankInfo localBPWBankInfo = null;
    BPWBankInfo[] arrayOfBPWBankInfo = null;
    StringBuffer localStringBuffer = null;
    if (paramWirePayeeInfo == null)
    {
      localObject1 = " failed: payeeInfo is null";
      FFSDebug.log(str1, (String)localObject1, 0);
      return null;
    }
    if (paramWirePayeeInfo.getPayeeExtId() == null)
    {
      paramWirePayeeInfo.setStatusCode(0);
      paramWirePayeeInfo.setStatusMsg("Success");
      return paramWirePayeeInfo;
    }
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT  PayeeId, PayeeType, PayeeGroup, BeneficiaryName, NickName, ContactName, CustomerID, RouteID, PayeeExtID, ExtID, BeneficiaryBankId, BankAcctId, BankAcctType, BranchId, AcctKey, PayeeAddr1, PayeeAddr2, PayeeAddr3, PayeeCity, PayeeState, PayeeZipcode, PayeeCountry, PayeePhone, Status, Memo, SubmitDate, LastModDate, ActivationDate, LogId, SubmittedBy, PayeeDest  FROM BPW_WirePayee WHERE PayeeExtID = ? ");
    localStringBuffer.append(" AND Status != ? ");
    Object localObject1 = { paramWirePayeeInfo.getPayeeExtId(), "CLOSED" };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        if (paramWirePayeeInfo.getPayeeId() != null)
        {
          String str2 = localFFSResultSet.getColumnString("PayeeId");
          if (paramWirePayeeInfo.getPayeeId().equals(str2))
          {
            paramWirePayeeInfo.setStatusCode(0);
            paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
            FFSDebug.log("***", str1, "WirePayeeInfo ", " record not found", 6);
            FFSDebug.log(str1, ": done", 6);
            localObject2 = paramWirePayeeInfo;
            return localObject2;
          }
        }
        a(localFFSResultSet, paramWirePayeeInfo);
        localBPWBankInfo = WireBank.getWireBanksByID(paramFFSConnectionHolder, paramWirePayeeInfo.getBeneficiaryBankId());
        paramWirePayeeInfo.setBeneficiaryBankInfo(localBPWBankInfo);
        arrayOfBPWBankInfo = WireBank.getWireBanksByPayee(paramFFSConnectionHolder, paramWirePayeeInfo.getPayeeId());
        paramWirePayeeInfo.setIntermediateBanks(arrayOfBPWBankInfo);
        paramWirePayeeInfo.setStatusCode(19590);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(19590, new String[] { "PayeeExtID: " + paramWirePayeeInfo.getPayeeExtId() }, "WIRE_MESSAGE"));
      }
      else
      {
        paramWirePayeeInfo.setStatusCode(0);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        FFSDebug.log("***", str1, "WirePayeeInfo ", " record not found", 6);
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject2 = "failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("***", str1, (String)localObject2, 0);
      throw new FFSException(localThrowable, (String)localObject2);
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
          FFSDebug.log("***WirePayee.findDuplicateExtId failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return paramWirePayeeInfo;
  }
  
  public static WirePayeeInfo getWirePayee(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    String str1 = "WirePayee.getWirePayee";
    FFSDebug.log("***", str1, " start ...", 6);
    if (paramWirePayeeInfo == null)
    {
      FFSDebug.log("***", str1, " failed: WirePayeeInfo is null", 0);
      paramWirePayeeInfo = new WirePayeeInfo();
      paramWirePayeeInfo.setStatusCode(16000);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "payeeID " }, "WIRE_MESSAGE"));
      return paramWirePayeeInfo;
    }
    try
    {
      if ((paramWirePayeeInfo.getPayeeId() != null) && (paramWirePayeeInfo.getPayeeId().trim().length() > 0)) {
        paramWirePayeeInfo = a(paramFFSConnectionHolder, paramWirePayeeInfo, true, true);
      } else {
        paramWirePayeeInfo = a(paramFFSConnectionHolder, paramWirePayeeInfo);
      }
      return paramWirePayeeInfo;
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** WirePayee.getWirePayee failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  private static WirePayeeInfo a(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    String str1 = "WirePayee.getWirePayeeByAcctIdRTNUser";
    FFSDebug.log("***", str1, " start", 6);
    WirePayeeInfo localWirePayeeInfo1 = null;
    FFSResultSet localFFSResultSet = null;
    BPWBankInfo localBPWBankInfo = null;
    BPWBankInfo[] arrayOfBPWBankInfo = null;
    ArrayList localArrayList = null;
    StringBuffer localStringBuffer = null;
    if (paramWirePayeeInfo == null)
    {
      FFSDebug.log("***", str1, " failed: payeeInfo is null", 0);
      localWirePayeeInfo1 = new WirePayeeInfo();
      localWirePayeeInfo1.setStatusCode(16000);
      localWirePayeeInfo1.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "PayeeInfo " }, "WIRE_MESSAGE"));
      return localWirePayeeInfo1;
    }
    if ((paramWirePayeeInfo.getBankAcctId() == null) || (paramWirePayeeInfo.getBankAcctId().length() == 0))
    {
      FFSDebug.log("***", str1, " failed: BankAcctId is null", 0);
      paramWirePayeeInfo.setStatusCode(16000);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BankAcctId " }, "WIRE_MESSAGE"));
      return paramWirePayeeInfo;
    }
    localBPWBankInfo = paramWirePayeeInfo.getBeneficiaryBankInfo();
    if (localBPWBankInfo == null)
    {
      localObject1 = " failed: beneficiaryBankInfo is null in the payeeInfo";
      FFSDebug.log("****", str1, (String)localObject1, 0);
      paramWirePayeeInfo.setStatusCode(16000);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "beneficiaryBankInfo " }, "WIRE_MESSAGE"));
      return paramWirePayeeInfo;
    }
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT  PayeeId, PayeeType, PayeeGroup, BeneficiaryName, NickName, ContactName, CustomerID, RouteID, PayeeExtID, ExtID, BeneficiaryBankId, BankAcctId, BankAcctType, BranchId, AcctKey, PayeeAddr1, PayeeAddr2, PayeeAddr3, PayeeCity, PayeeState, PayeeZipcode, PayeeCountry, PayeePhone, Status, Memo, SubmitDate, LastModDate, ActivationDate, LogId, SubmittedBy, PayeeDest  FROM BPW_WirePayee a,  BPW_WireBank b  WHERE a.BeneficiaryBankId = b.BankId  AND a.BankAcctId = ? ");
    localArrayList = new ArrayList();
    localArrayList.add(paramWirePayeeInfo.getBankAcctId());
    if (((localBPWBankInfo.getFedRTN() == null) || (localBPWBankInfo.getFedRTN().length() == 0)) && ((localBPWBankInfo.getSwiftRTN() == null) || (localBPWBankInfo.getSwiftRTN().length() == 0)) && ((localBPWBankInfo.getChipsRTN() == null) || (localBPWBankInfo.getChipsRTN().length() == 0)) && ((localBPWBankInfo.getOtherRTN() == null) || (localBPWBankInfo.getOtherRTN().length() == 0)))
    {
      localStringBuffer.append(" AND ( b.FedRTN is NULL ");
      localStringBuffer.append(" AND b.SwiftRTN is NULL ");
      localStringBuffer.append(" AND b.ChipsRTN is NULL ");
      localStringBuffer.append(" AND b.OtherRTN is NULL ) ");
    }
    else
    {
      FFSDebug.log("***", str1, ": BankAcctId=", paramWirePayeeInfo.getBankAcctId(), "FedRtn=", localBPWBankInfo.getFedRTN(), "SwiftRtn=", localBPWBankInfo.getSwiftRTN(), "ChipsRtn=", localBPWBankInfo.getChipsRTN(), "OtherRtn=", localBPWBankInfo.getOtherRTN(), 6);
      if ((localBPWBankInfo.getFedRTN() != null) && (localBPWBankInfo.getFedRTN().length() > 0))
      {
        localArrayList.add(localBPWBankInfo.getFedRTN());
        localStringBuffer.append(" AND b.FedRTN = ? ");
      }
      else if ((localBPWBankInfo.getSwiftRTN() != null) && (localBPWBankInfo.getSwiftRTN().length() > 0))
      {
        localArrayList.add(localBPWBankInfo.getSwiftRTN());
        localStringBuffer.append(" AND b.SwiftRTN = ? ");
      }
      else if ((localBPWBankInfo.getChipsRTN() != null) && (localBPWBankInfo.getChipsRTN().length() > 0))
      {
        localArrayList.add(localBPWBankInfo.getChipsRTN());
        localStringBuffer.append(" AND b.ChipsRTN = ? ");
      }
      else if ((localBPWBankInfo.getOtherRTN() != null) && (localBPWBankInfo.getOtherRTN().length() > 0))
      {
        localArrayList.add(localBPWBankInfo.getOtherRTN());
        localStringBuffer.append(" AND b.OtherRTN = ? ");
      }
    }
    if ((paramWirePayeeInfo.getSubmittedBy() == null) || (paramWirePayeeInfo.getSubmittedBy().length() == 0))
    {
      localStringBuffer.append(" AND a.SubmittedBy is NULL ");
    }
    else
    {
      localArrayList.add(paramWirePayeeInfo.getSubmittedBy());
      localStringBuffer.append(" AND a.SubmittedBy = ? ");
    }
    localStringBuffer.append(" AND a.PayeeDest <> '");
    localStringBuffer.append("CREDIT");
    localStringBuffer.append("' ");
    localStringBuffer.append(" AND a.PayeeGroup NOT IN ('");
    localStringBuffer.append("UNMANAGED");
    localStringBuffer.append("', '");
    localStringBuffer.append("SECURE");
    localStringBuffer.append("') ");
    localStringBuffer.append(" AND Status != ? ");
    localArrayList.add("CLOSED");
    Object localObject1 = localArrayList.toArray(new Object[0]);
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        a(localFFSResultSet, paramWirePayeeInfo);
        localBPWBankInfo = WireBank.getWireBanksByID(paramFFSConnectionHolder, paramWirePayeeInfo.getBeneficiaryBankId());
        paramWirePayeeInfo.setBeneficiaryBankInfo(localBPWBankInfo);
        arrayOfBPWBankInfo = WireBank.getWireBanksByPayee(paramFFSConnectionHolder, paramWirePayeeInfo.getPayeeId());
        paramWirePayeeInfo.setIntermediateBanks(arrayOfBPWBankInfo);
        paramWirePayeeInfo.setStatusCode(0);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      else
      {
        paramWirePayeeInfo.setStatusCode(16020);
        paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WirePayeeInfo " }, "WIRE_MESSAGE"));
      }
      FFSDebug.log("***", str1, " Done", 6);
      WirePayeeInfo localWirePayeeInfo2 = paramWirePayeeInfo;
      return localWirePayeeInfo2;
    }
    catch (Throwable localThrowable)
    {
      String str2 = " failed: " + FFSDebug.stackTrace(localThrowable);
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
          FFSDebug.log("***WirePayee.getWirePayeeByAcctIdRTNUser failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
  }
  
  private static WirePayeeInfo jdMethod_new(FFSConnectionHolder paramFFSConnectionHolder, WirePayeeInfo paramWirePayeeInfo)
    throws FFSException
  {
    String str1 = "WirePayee.createCopy: ";
    FFSDebug.log("***", str1, "start ...", 6);
    String str2 = null;
    paramWirePayeeInfo.setPayeeId(null);
    paramWirePayeeInfo.setStatus("CLOSED");
    paramWirePayeeInfo.setPayeeId(DBUtil.getNextIndexStringWithPadding("WIREPAYEEID", 10, '0'));
    try
    {
      str2 = "INSERT INTO BPW_WirePayee  ( PayeeId, PayeeType, PayeeGroup, BeneficiaryName, NickName, ContactName, CustomerID, PayeeExtID, ExtID, RouteID, BeneficiaryBankId, BankAcctId, BankAcctType, BranchId, AcctKey, PayeeAddr1, PayeeAddr2, PayeeAddr3, PayeeCity, PayeeState, PayeeZipcode, PayeeCountry, PayeePhone, Status, Memo, SubmitDate, ActivationDate, LogId, SubmittedBy, PayeeDest ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
      FFSDebug.log(str1, "adding wirePayee: " + paramWirePayeeInfo, 6);
      Object[] arrayOfObject = { paramWirePayeeInfo.getPayeeId(), paramWirePayeeInfo.getPayeeType(), paramWirePayeeInfo.getPayeeGroup(), paramWirePayeeInfo.getBeneficiaryName(), paramWirePayeeInfo.getNickName(), paramWirePayeeInfo.getContactName(), paramWirePayeeInfo.getCustomerID(), paramWirePayeeInfo.getPayeeExtId(), paramWirePayeeInfo.getExtId(), paramWirePayeeInfo.getRouteId(), paramWirePayeeInfo.getBeneficiaryBankId(), paramWirePayeeInfo.getBankAcctId(), paramWirePayeeInfo.getBankAcctType(), paramWirePayeeInfo.getBranchId(), paramWirePayeeInfo.getAcctKey(), paramWirePayeeInfo.getPayeeAddr1(), paramWirePayeeInfo.getPayeeAddr2(), paramWirePayeeInfo.getPayeeAddr3(), paramWirePayeeInfo.getPayeeCity(), paramWirePayeeInfo.getPayeeState(), paramWirePayeeInfo.getPayeeZipcode(), paramWirePayeeInfo.getPayeeCountry(), paramWirePayeeInfo.getPayeePhone(), paramWirePayeeInfo.getStatus(), FFSUtil.hashtableToString(paramWirePayeeInfo.getMemo()), paramWirePayeeInfo.getSubmitDate(), paramWirePayeeInfo.getActivationDate(), paramWirePayeeInfo.getLogId(), paramWirePayeeInfo.getSubmittedBy(), paramWirePayeeInfo.getPayeeDest() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      FFSDebug.log(str1, "Number of records added " + i, 6);
      if (!jdMethod_try(paramFFSConnectionHolder, paramWirePayeeInfo))
      {
        FFSDebug.log("*** ", str1, "failed: ", paramWirePayeeInfo.getStatusMsg(), 0);
        return paramWirePayeeInfo;
      }
      paramWirePayeeInfo.setStatusCode(0);
      paramWirePayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      FFSDebug.log("*** ", str1, "End", 6);
      return paramWirePayeeInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "***WirePayee.createCopy failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.WirePayee
 * JD-Core Version:    0.7.0.1
 */