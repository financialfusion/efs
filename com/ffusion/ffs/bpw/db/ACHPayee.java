package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeList;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
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
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class ACHPayee
  implements DBConsts, FFSConst, ACHConsts, BPWResource
{
  public static ACHPayeeInfo create(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str1 = "ACHPayee.create:";
    FFSDebug.log(str1 + " start ...", 6);
    paramACHPayeeInfo = jdMethod_for(paramFFSConnectionHolder, paramACHPayeeInfo);
    if (paramACHPayeeInfo.getStatusCode() != 0) {
      return paramACHPayeeInfo;
    }
    if (paramACHPayeeInfo.getPayeeID() != null)
    {
      paramACHPayeeInfo.setStatusCode(24020);
      paramACHPayeeInfo.setStatusMsg("PayeeID should be null when adding a new ACH Payee");
      FFSDebug.log(str1 + paramACHPayeeInfo.getStatusMsg(), 0);
      return paramACHPayeeInfo;
    }
    if (!jdMethod_int(paramACHPayeeInfo)) {
      return paramACHPayeeInfo;
    }
    ACHCompanyInfo localACHCompanyInfo = ACHCompany.getCompanyInfo(paramACHPayeeInfo.getCompId(), paramFFSConnectionHolder);
    if ((localACHCompanyInfo == null) || (localACHCompanyInfo.getStatusCode() != 0))
    {
      paramACHPayeeInfo.setStatusCode(23430);
      paramACHPayeeInfo.setStatusMsg(paramACHPayeeInfo.getCompId() + " " + "ACHCompany does not exist");
      return paramACHPayeeInfo;
    }
    ACHPayeeInfo localACHPayeeInfo = a(paramFFSConnectionHolder, paramACHPayeeInfo, localACHCompanyInfo.getCustomerId());
    if (localACHPayeeInfo != null)
    {
      if (localACHPayeeInfo.getStatusCode() == 0)
      {
        localACHPayeeInfo.setStatusCode(23440);
        localACHPayeeInfo.setStatusMsg("A managed ACHPayee with the same NickName or Pay Account, Bank Account ID and Bank Routing Number and in the same payee group already exists. Payee group: " + paramACHPayeeInfo.getPayeeGroup());
      }
      return localACHPayeeInfo;
    }
    try
    {
      paramACHPayeeInfo.setStatus("ACTIVE");
      paramACHPayeeInfo.setPayeeID(DBUtil.getNextIndexStringWithPadding("ACHPAYEEID", 10, '0'));
      if (!paramACHPayeeInfo.getDoPrenote())
      {
        paramACHPayeeInfo.setPrenoteCreditStatus("Not Required");
        paramACHPayeeInfo.setPrenoteDebitStatus("Not Required");
      }
      paramACHPayeeInfo.setSubmitDate(FFSUtil.getDateString());
      paramACHPayeeInfo.setActivationDate(FFSUtil.getDateString());
      String str2 = "INSERT INTO ACH_Payee (PayeeId, CompId, PayAcct, PayeeType, PayeeGroup, PayeeName, NickName, BankRTN, BankAcctId, BankAcctType, CardNum, CardExpireDate, CardTransCode, CardAuthCode, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, Extension, Status, ContactName, SubmitDate, ActivationDate, LogId, Memo, PrenoteCredStatus, PrenoteDebStatus, PrenoteSubmitDate, PrenoteSecCode, PrenoteDemand, ManagedPayee, SubmittedBy, SecurePayee ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      localObject = new Object[] { paramACHPayeeInfo.getPayeeID(), paramACHPayeeInfo.getCompId(), paramACHPayeeInfo.getPayAcct(), paramACHPayeeInfo.getPayeeType(), paramACHPayeeInfo.getPayeeGroup(), paramACHPayeeInfo.getPayeeName(), paramACHPayeeInfo.getNickName(), paramACHPayeeInfo.getBankRTN(), paramACHPayeeInfo.getBankAcctId(), paramACHPayeeInfo.getBankAcctType(), paramACHPayeeInfo.getCardNum(), paramACHPayeeInfo.getCardExpireDate(), paramACHPayeeInfo.getCardTransCode(), paramACHPayeeInfo.getCardAuthCode(), paramACHPayeeInfo.getAddr1(), paramACHPayeeInfo.getAddr2(), paramACHPayeeInfo.getAddr3(), paramACHPayeeInfo.getCity(), paramACHPayeeInfo.getState(), paramACHPayeeInfo.getPostalCode(), paramACHPayeeInfo.getCountry(), paramACHPayeeInfo.getPhone(), paramACHPayeeInfo.getExtension(), paramACHPayeeInfo.getStatus(), paramACHPayeeInfo.getContactName(), paramACHPayeeInfo.getSubmitDate(), paramACHPayeeInfo.getActivationDate(), paramACHPayeeInfo.getLogId(), FFSUtil.hashtableToString(paramACHPayeeInfo.getMemo()), paramACHPayeeInfo.getPrenoteCreditStatus(), paramACHPayeeInfo.getPrenoteDebitStatus(), paramACHPayeeInfo.getPrenoteSubmitDate(), paramACHPayeeInfo.getPrenoteSecCode(), paramACHPayeeInfo.getPrenoteDemand(), paramACHPayeeInfo.getManagedPayee(), paramACHPayeeInfo.getSubmittedBy(), new Integer(paramACHPayeeInfo.getSecurePayee()) };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      FFSDebug.log(str1 + " Number of records added " + i, 6);
      paramACHPayeeInfo.setStatusCode(0);
      paramACHPayeeInfo.setStatusMsg("Success");
      FFSDebug.log(str1 + " end", 6);
      return paramACHPayeeInfo;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = "***" + str1 + " failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject, 0);
      throw new FFSException(localThrowable, (String)localObject);
    }
  }
  
  public static ACHPayeeInfo modify(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str1 = "ACHPayee.modify: ";
    FFSDebug.log(str1, "start ...", 6);
    String str2 = paramACHPayeeInfo.getPayeeID();
    if (str2 == null)
    {
      localObject1 = str1 + "failed: payeeID is null";
      paramACHPayeeInfo.setStatusCode(16010);
      paramACHPayeeInfo.setStatusMsg("ACHPayeeInfo  contains null  payeeID");
      FFSDebug.log((String)localObject1, 0);
      return paramACHPayeeInfo;
    }
    Object localObject1 = getACHPayeeInfo(paramFFSConnectionHolder, str2);
    if (("N".compareTo(paramACHPayeeInfo.getManagedPayee()) == 0) && ("Y".compareTo(((ACHPayeeInfo)localObject1).getManagedPayee()) == 0))
    {
      paramACHPayeeInfo.setStatusCode(23540);
      localObject2 = "Can't change a managed payee to unmanaged payee";
      paramACHPayeeInfo.setStatusMsg((String)localObject2);
      FFSDebug.log(str1 + (String)localObject2, 0);
      return paramACHPayeeInfo;
    }
    paramACHPayeeInfo = jdMethod_for(paramFFSConnectionHolder, paramACHPayeeInfo);
    if (paramACHPayeeInfo.getStatusCode() != 0) {
      return paramACHPayeeInfo;
    }
    Object localObject2 = ACHCompany.getCompanyInfo(((ACHPayeeInfo)localObject1).getCompId(), paramFFSConnectionHolder);
    if ((localObject2 == null) || (((ACHCompanyInfo)localObject2).getStatusCode() != 0))
    {
      paramACHPayeeInfo.setStatusCode(23430);
      paramACHPayeeInfo.setStatusMsg(paramACHPayeeInfo.getCompId() + " " + "ACHCompany does not exist");
      return paramACHPayeeInfo;
    }
    ACHPayeeInfo localACHPayeeInfo = a(paramFFSConnectionHolder, paramACHPayeeInfo, ((ACHCompanyInfo)localObject2).getCustomerId());
    if (localACHPayeeInfo != null)
    {
      if (localACHPayeeInfo.getStatusCode() == 0)
      {
        paramACHPayeeInfo.setStatusCode(23440);
        paramACHPayeeInfo.setStatusMsg("A managed ACHPayee with the same NickName or Pay Account, Bank Account ID and Bank Routing Number and in the same payee group already exists. Payee group: " + paramACHPayeeInfo.getPayeeGroup());
      }
      else
      {
        paramACHPayeeInfo.setStatusCode(localACHPayeeInfo.getStatusCode());
        paramACHPayeeInfo.setStatusMsg(localACHPayeeInfo.getStatusMsg());
      }
      return paramACHPayeeInfo;
    }
    if (!jdMethod_int(paramACHPayeeInfo)) {
      return paramACHPayeeInfo;
    }
    boolean bool = a((ACHPayeeInfo)localObject1, paramACHPayeeInfo);
    String str3;
    if (jdMethod_byte(((ACHPayeeInfo)localObject1).getPayeeGroup(), paramACHPayeeInfo.getPayeeGroup()))
    {
      str3 = "Can't down grade payee group from " + ((ACHPayeeInfo)localObject1).getPayeeGroup() + " to " + paramACHPayeeInfo.getPayeeGroup();
      paramACHPayeeInfo.setStatusCode(23990);
      paramACHPayeeInfo.setStatusMsg(str3);
      FFSDebug.log(str1 + str3, 0);
      return paramACHPayeeInfo;
    }
    if (("User".equals(((ACHPayeeInfo)localObject1).getPayeeGroup())) && (!((ACHPayeeInfo)localObject1).getSubmittedBy().equals(paramACHPayeeInfo.getSubmittedBy())))
    {
      str3 = "Can't modify a User group payee by a different user The original ueser was " + ((ACHPayeeInfo)localObject1).getSubmittedBy() + " The new user is " + paramACHPayeeInfo.getSubmittedBy();
      paramACHPayeeInfo.setStatusCode(24000);
      paramACHPayeeInfo.setStatusMsg(str3);
      FFSDebug.log(str1 + str3, 0);
      return paramACHPayeeInfo;
    }
    try
    {
      str3 = "UPDATE ACH_Payee SET PayeeType = ?, PayeeGroup = ?, PayeeName = ?, NickName = ?, BankAcctType = ?, CardNum = ?, CardExpireDate = ?, CardTransCode = ?, CardAuthCode = ?, Addr1 = ?, Addr2 = ?, Addr3 = ?, City = ?, State = ?, Zipcode = ?, Country = ?, Phone = ?, Extension = ?, ContactName = ?, Memo = ?, PayAcct = ?, BankAcctId = ?, BankRTN = ?, PrenoteSecCode = ?,PrenoteCredStatus = ?, PrenoteDebStatus = ?, PrenoteDemand = ?, ManagedPayee = ?, SecurePayee = ? WHERE PayeeId = ? AND Status != 'CLOSED'";
      localObject3 = new Object[] { paramACHPayeeInfo.getPayeeType(), paramACHPayeeInfo.getPayeeGroup(), paramACHPayeeInfo.getPayeeName(), paramACHPayeeInfo.getNickName(), paramACHPayeeInfo.getBankAcctType(), paramACHPayeeInfo.getCardNum(), paramACHPayeeInfo.getCardExpireDate(), paramACHPayeeInfo.getCardTransCode(), paramACHPayeeInfo.getCardAuthCode(), paramACHPayeeInfo.getAddr1(), paramACHPayeeInfo.getAddr2(), paramACHPayeeInfo.getAddr3(), paramACHPayeeInfo.getCity(), paramACHPayeeInfo.getState(), paramACHPayeeInfo.getPostalCode(), paramACHPayeeInfo.getCountry(), paramACHPayeeInfo.getPhone(), paramACHPayeeInfo.getExtension(), paramACHPayeeInfo.getContactName(), FFSUtil.hashtableToString(paramACHPayeeInfo.getMemo()), paramACHPayeeInfo.getPayAcct(), paramACHPayeeInfo.getBankAcctId(), paramACHPayeeInfo.getBankRTN(), paramACHPayeeInfo.getPrenoteSecCode(), paramACHPayeeInfo.getPrenoteCreditStatus(), paramACHPayeeInfo.getPrenoteDebitStatus(), paramACHPayeeInfo.getPrenoteDemand(), paramACHPayeeInfo.getManagedPayee(), new Integer(paramACHPayeeInfo.getSecurePayee()), str2 };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, (Object[])localObject3);
      FFSDebug.log(str1, ": Number of records updated " + i, 6);
      FFSDebug.log(str1, "End", 6);
      if (i == 0)
      {
        paramACHPayeeInfo.setStatusCode(16020);
        paramACHPayeeInfo.setStatusMsg("ACHPayeeInfo  record not found");
        return paramACHPayeeInfo;
      }
      if (bool) {
        ACHRecord.setACHRecordDirtyFlag(paramFFSConnectionHolder, str2);
      }
      paramACHPayeeInfo.setStatusCode(0);
      paramACHPayeeInfo.setStatusMsg("Success");
      return paramACHPayeeInfo;
    }
    catch (Throwable localThrowable)
    {
      Object localObject3 = "*** ACHPayee.modify failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject3, 0);
      throw new FFSException(localThrowable, (String)localObject3);
    }
  }
  
  public static ACHPayeeInfo delete(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str1 = "ACHPayee.delete:";
    FFSDebug.log(str1 + " start ...", 6);
    if (paramACHPayeeInfo == null)
    {
      localObject1 = "***" + str1 + " failed:" + " ACH Payee Object passed is null";
      FFSDebug.log((String)localObject1, 0);
      paramACHPayeeInfo = new ACHPayeeInfo();
      paramACHPayeeInfo.setStatusCode(16000);
      paramACHPayeeInfo.setStatusMsg("ACHPayeeInfo  is null");
      return paramACHPayeeInfo;
    }
    if (!jdMethod_int(paramACHPayeeInfo)) {
      return paramACHPayeeInfo;
    }
    if (paramACHPayeeInfo.getPayeeID() == null)
    {
      localObject1 = "***" + str1 + " failed:" + " PayeeID is null";
      FFSDebug.log((String)localObject1, 0);
      paramACHPayeeInfo.setStatusCode(16010);
      paramACHPayeeInfo.setStatusMsg("ACHPayeeInfo  contains null  FIId");
      return paramACHPayeeInfo;
    }
    Object localObject1 = getACHPayeeInfo(paramFFSConnectionHolder, paramACHPayeeInfo.getPayeeID());
    if ((((ACHPayeeInfo)localObject1).getPayeeGroup().equals("User")) && (!((ACHPayeeInfo)localObject1).getSubmittedBy().equals(paramACHPayeeInfo.getSubmittedBy())))
    {
      localObject2 = "Can't delete a User group payee by a different user. The original user was " + ((ACHPayeeInfo)localObject1).getSubmittedBy() + ". The new user is " + paramACHPayeeInfo.getSubmittedBy();
      paramACHPayeeInfo.setStatusCode(24010);
      paramACHPayeeInfo.setStatusMsg((String)localObject2);
      FFSDebug.log(str1 + (String)localObject2, 0);
      return paramACHPayeeInfo;
    }
    Object localObject2 = { paramACHPayeeInfo.getPayeeID() };
    try
    {
      String str2 = "UPDATE ACH_Payee SET Status = 'CLOSED' WHERE PayeeId = ? AND Status != 'CLOSED'";
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject2);
      FFSDebug.log(str1 + " Number of ACHPayeeInfo records" + " closed " + i, 6);
      FFSDebug.log(str1 + " end", 6);
      if (i == 0)
      {
        paramACHPayeeInfo.setStatusCode(16020);
        paramACHPayeeInfo.setStatusMsg("ACHPayeeInfo  record not found");
      }
      else if (i > 1)
      {
        paramFFSConnectionHolder.conn.rollback();
        paramACHPayeeInfo.setStatusCode(23270);
        paramACHPayeeInfo.setStatusMsg("More than One Record found For payeeId =" + paramACHPayeeInfo.getPayeeID());
      }
      else
      {
        paramACHPayeeInfo.setStatus("CLOSED");
        paramACHPayeeInfo.setStatusCode(0);
        paramACHPayeeInfo.setStatusMsg("Success");
      }
      return paramACHPayeeInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "***" + str1 + " failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  public static ACHPayeeInfo getACHPayeeInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    return getACHPayeeInfo(paramFFSConnectionHolder, paramString, false);
  }
  
  public static ACHPayeeInfo getACHPayeeInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("ACHPayee.getACHPayeeInfo start. PayeeId: " + paramString, 6);
    ACHPayeeInfo localACHPayeeInfo1 = null;
    if (paramString == null)
    {
      localObject1 = "***ACHPayee.getACHPayeeInfo failed: payeeID is null";
      FFSDebug.log((String)localObject1, 0);
      localACHPayeeInfo1 = new ACHPayeeInfo();
      localACHPayeeInfo1.setStatusCode(16000);
      localACHPayeeInfo1.setStatusMsg("payeeID  is null");
      return localACHPayeeInfo1;
    }
    Object localObject1 = null;
    String str = "SELECT  PayeeId, CompId, PayAcct, PayeeType, PayeeGroup, PayeeName, NickName, BankRTN, BankAcctId, BankAcctType, CardNum, CardExpireDate, CardTransCode, CardAuthCode, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, Extension, Status, ContactName, SubmitDate, ActivationDate, LogId, Memo, PrenoteCredStatus, PrenoteDebStatus, PrenoteSubmitDate,  PrenoteSecCode, PrenoteDemand, ManagedPayee, SubmittedBy, SecurePayee  FROM ACH_Payee  WHERE PayeeId = ? ";
    if (!paramBoolean) {
      str = str + " AND Status != 'CLOSED'";
    }
    Object[] arrayOfObject = { paramString };
    try
    {
      ACHPayeeInfo localACHPayeeInfo2 = new ACHPayeeInfo();
      localObject1 = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (((FFSResultSet)localObject1).getNextRow())
      {
        a((FFSResultSet)localObject1, localACHPayeeInfo2);
        if (localACHPayeeInfo2.getDoPrenote() == true) {
          localACHPayeeInfo2.setPrenoteMaturedDate(getPrenoteMaturedDate(paramFFSConnectionHolder, localACHPayeeInfo2));
        }
        localACHPayeeInfo2.setStatusCode(0);
        localACHPayeeInfo2.setStatusMsg("Success");
      }
      else
      {
        localACHPayeeInfo2.setStatusCode(16020);
        localACHPayeeInfo2.setStatusMsg("ACHPayeeInfo  record not found");
      }
      FFSDebug.log("ACHPayee.getACHPayeeInfo end", 6);
      localObject2 = localACHPayeeInfo2;
      return localObject2;
    }
    catch (Throwable localThrowable)
    {
      Object localObject2 = "*** ACHPayee.getACHPayeeInfo failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localThrowable, (String)localObject2);
    }
    finally
    {
      if (localObject1 != null) {
        try
        {
          ((FFSResultSet)localObject1).close();
          localObject1 = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("***ACHPayee.getACHPayeeInfo failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
  }
  
  public static String getACHPayeeStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("***ACHPayee.getACHPayeeStatus start ...", 6);
    if (paramString == null)
    {
      str1 = "***ACHPayee.getACHPayeeStatus failed: payeeID is null";
      FFSDebug.log(str1, 0);
      return null;
    }
    String str1 = "SELECT Status FROM ACH_Payee WHERE PayeeId = ?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    String str2 = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str2 = localFFSResultSet.getColumnString(1);
      }
      FFSDebug.log("***ACHPayee.getACHPayeeStatus end", 6);
      String str3 = str2;
      return str3;
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** ACHPayee.getACHPayeeStatus failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable, str4);
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
          FFSDebug.log("***ACHPayee.getACHPayeeInfo failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
  }
  
  public static ACHPayeeInfo activatePayee(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHPayee.activatePayee: start, payeeId =" + paramString, 6);
    ACHPayeeInfo localACHPayeeInfo1 = null;
    if (paramString == null)
    {
      localACHPayeeInfo1 = new ACHPayeeInfo();
      localACHPayeeInfo1.setStatusCode(16000);
      localACHPayeeInfo1.setStatusMsg("payeeId  is null");
      return localACHPayeeInfo1;
    }
    String str1 = "SELECT  PayeeId, CompId, PayAcct, PayeeType, PayeeGroup, PayeeName, NickName, BankRTN, BankAcctId, BankAcctType, CardNum, CardExpireDate, CardTransCode, CardAuthCode, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, Extension, Status, ContactName, SubmitDate, ActivationDate, LogId, Memo, PrenoteCredStatus, PrenoteDebStatus, PrenoteSubmitDate,  PrenoteSecCode, PrenoteDemand, ManagedPayee, SubmittedBy, SecurePayee  FROM ACH_Payee  WHERE PayeeId = ? ";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localACHPayeeInfo1 = new ACHPayeeInfo();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        a(localFFSResultSet, localACHPayeeInfo1);
        if (localACHPayeeInfo1.getDoPrenote() == true) {
          localACHPayeeInfo1.setPrenoteMaturedDate(getPrenoteMaturedDate(paramFFSConnectionHolder, localACHPayeeInfo1));
        }
        if (!localACHPayeeInfo1.getStatus().equals("CLOSED"))
        {
          localACHPayeeInfo1.setStatusCode(23280);
          localACHPayeeInfo1.setStatusMsg("Payee is already ACTIVE , payeeId " + paramString);
          ACHPayeeInfo localACHPayeeInfo2 = localACHPayeeInfo1;
          return localACHPayeeInfo2;
        }
        str1 = "Update ACH_Payee set Status = ?, ActivationDate = ?  Where PayeeId = ?";
        int i = 0;
        localACHPayeeInfo1.setActivationDate(FFSUtil.getDateString());
        localACHPayeeInfo1.setStatus("ACTIVE");
        localObject1 = new Object[] { localACHPayeeInfo1.getStatus(), localACHPayeeInfo1.getActivationDate(), localACHPayeeInfo1.getPayeeID() };
        try
        {
          i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, (Object[])localObject1);
        }
        catch (Exception localException1)
        {
          String str2 = "*** ACHPayee.activatePayee failed: ";
          String str3 = FFSDebug.stackTrace(localException1);
          FFSDebug.log(str3, 0);
          throw new FFSException(localException1, str2);
        }
        localACHPayeeInfo1.setStatusCode(0);
        localACHPayeeInfo1.setStatusMsg("Success");
      }
      else
      {
        localACHPayeeInfo1.setStatusCode(16020);
        localACHPayeeInfo1.setStatusMsg("ACHPayeeInfo  record not found");
      }
      FFSDebug.log("***ACHPayee.activatePayee end", 6);
      ACHPayeeInfo localACHPayeeInfo3 = localACHPayeeInfo1;
      return localACHPayeeInfo3;
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = "*** ACHPayee.activatePayee failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable, (String)localObject1);
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
          FFSDebug.log("***ACHPayee.activatePayee failed " + localException2.toString(), 0);
        }
      }
    }
  }
  
  public static ACHPayeeInfo updatePayeeStatus(ACHPayeeInfo paramACHPayeeInfo, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    if (paramACHPayeeInfo == null)
    {
      paramACHPayeeInfo = new ACHPayeeInfo();
      paramACHPayeeInfo.setStatusCode(16000);
      paramACHPayeeInfo.setStatusMsg("ACHPayeeInfo  is null");
      return paramACHPayeeInfo;
    }
    FFSDebug.log("ACHPayee.updatePayeeStatus: start, payeeId=" + paramACHPayeeInfo.getPayeeID(), 6);
    String str1 = "Update ACH_Payee set  Status = ?  Where PayeeId = ?";
    Object[] arrayOfObject = { paramString, paramACHPayeeInfo.getPayeeID() };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
      paramACHPayeeInfo.setStatus(paramString);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** ACHPayee.updatePayeeStatus failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, str2);
    }
    FFSDebug.log("ACHPayee.updatePayeeStatus: done", 6);
    return paramACHPayeeInfo;
  }
  
  private static void a(FFSResultSet paramFFSResultSet, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    paramACHPayeeInfo.setPayeeID(paramFFSResultSet.getColumnString("PayeeId"));
    paramACHPayeeInfo.setCompId(paramFFSResultSet.getColumnString("CompId"));
    paramACHPayeeInfo.setPayAcct(paramFFSResultSet.getColumnString("PayAcct"));
    paramACHPayeeInfo.setPayeeType(paramFFSResultSet.getColumnString("PayeeType"));
    paramACHPayeeInfo.setPayeeGroup(paramFFSResultSet.getColumnString("PayeeGroup"));
    paramACHPayeeInfo.setPayeeName(paramFFSResultSet.getColumnString("PayeeName"));
    paramACHPayeeInfo.setNickName(paramFFSResultSet.getColumnString("NickName"));
    paramACHPayeeInfo.setBankRTN(paramFFSResultSet.getColumnString("BankRTN"));
    paramACHPayeeInfo.setBankAcctId(paramFFSResultSet.getColumnString("BankAcctId"));
    paramACHPayeeInfo.setBankAcctType(paramFFSResultSet.getColumnString("BankAcctType"));
    paramACHPayeeInfo.setCardNum(paramFFSResultSet.getColumnString("CardNum"));
    paramACHPayeeInfo.setCardExpireDate(paramFFSResultSet.getColumnString("CardExpireDate"));
    paramACHPayeeInfo.setCardTransCode(paramFFSResultSet.getColumnString("CardTransCode"));
    paramACHPayeeInfo.setCardAuthCode(paramFFSResultSet.getColumnString("CardAuthCode"));
    paramACHPayeeInfo.setAddr1(paramFFSResultSet.getColumnString("Addr1"));
    paramACHPayeeInfo.setAddr2(paramFFSResultSet.getColumnString("Addr2"));
    paramACHPayeeInfo.setAddr3(paramFFSResultSet.getColumnString("Addr3"));
    paramACHPayeeInfo.setCity(paramFFSResultSet.getColumnString("City"));
    paramACHPayeeInfo.setState(paramFFSResultSet.getColumnString("State"));
    paramACHPayeeInfo.setPostalCode(paramFFSResultSet.getColumnString("Zipcode"));
    paramACHPayeeInfo.setCountry(paramFFSResultSet.getColumnString("Country"));
    paramACHPayeeInfo.setPhone(paramFFSResultSet.getColumnString("Phone"));
    paramACHPayeeInfo.setExtension(paramFFSResultSet.getColumnString("Extension"));
    paramACHPayeeInfo.setStatus(paramFFSResultSet.getColumnString("Status"));
    paramACHPayeeInfo.setContactName(paramFFSResultSet.getColumnString("ContactName"));
    paramACHPayeeInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
    paramACHPayeeInfo.setActivationDate(paramFFSResultSet.getColumnString("ActivationDate"));
    paramACHPayeeInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    paramACHPayeeInfo.setMemo(FFSUtil.stringToHashtable(paramFFSResultSet.getColumnString("Memo")));
    paramACHPayeeInfo.setPrenoteCreditStatus(paramFFSResultSet.getColumnString("PrenoteCredStatus"));
    paramACHPayeeInfo.setPrenoteDebitStatus(paramFFSResultSet.getColumnString("PrenoteDebStatus"));
    paramACHPayeeInfo.setPrenoteSubmitDate(paramFFSResultSet.getColumnString("PrenoteSubmitDate"));
    paramACHPayeeInfo.setManagedPayee(paramFFSResultSet.getColumnString("ManagedPayee"));
    paramACHPayeeInfo.setPrenoteSecCode(paramFFSResultSet.getColumnString("PrenoteSecCode"));
    paramACHPayeeInfo.setPrenoteDemand(paramFFSResultSet.getColumnString("PrenoteDemand"));
    paramACHPayeeInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    paramACHPayeeInfo.setSecurePayee(paramFFSResultSet.getColumnInt("SecurePayee"));
    if ((paramACHPayeeInfo.getPrenoteCreditStatus().compareTo("Not Required") == 0) && (paramACHPayeeInfo.getPrenoteDebitStatus().compareTo("Not Required") == 0)) {
      paramACHPayeeInfo.setDoPrenote(false);
    } else {
      paramACHPayeeInfo.setDoPrenote(true);
    }
    paramACHPayeeInfo.setCheckDigit(BPWUtil.calculateCheckDigit(paramACHPayeeInfo.getBankRTN8()));
    paramACHPayeeInfo.setStatusCode(0);
    paramACHPayeeInfo.setStatusMsg("Success");
  }
  
  private static ACHPayeeInfo jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    Object localObject = null;
    String str1 = "ACHPayee.vaildateACHPayeeInfo:";
    FFSDebug.log(str1 + " start", 6);
    if (paramACHPayeeInfo == null)
    {
      paramACHPayeeInfo = new ACHPayeeInfo();
      paramACHPayeeInfo.setStatusCode(16000);
      paramACHPayeeInfo.setStatusMsg("PAYEEINFO:  is null");
      return paramACHPayeeInfo;
    }
    String str2;
    if (paramACHPayeeInfo.getPayeeName() == null)
    {
      paramACHPayeeInfo.setStatusCode(16010);
      str2 = "PAYEENAME   contains null ";
      paramACHPayeeInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + str2, 0);
    }
    else if (paramACHPayeeInfo.getCompId() == null)
    {
      paramACHPayeeInfo.setStatusCode(16010);
      str2 = "COMPID   contains null ";
      paramACHPayeeInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + str2, 0);
    }
    else if (paramACHPayeeInfo.getBankRTN() == null)
    {
      paramACHPayeeInfo.setStatusCode(16010);
      str2 = "BANKRTN   contains null ";
      paramACHPayeeInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + str2, 0);
    }
    else if (paramACHPayeeInfo.getBankAcctId() == null)
    {
      paramACHPayeeInfo.setStatusCode(16010);
      str2 = "BANKACCTID   contains null ";
      paramACHPayeeInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + str2, 0);
    }
    else if (paramACHPayeeInfo.getBankAcctType() == null)
    {
      paramACHPayeeInfo.setStatusCode(16010);
      str2 = "BANKACCTType   contains null ";
      paramACHPayeeInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + str2, 0);
    }
    else if (paramACHPayeeInfo.getPayAcct() == null)
    {
      paramACHPayeeInfo.setStatusCode(16010);
      str2 = "PAYACCT   contains null ";
      paramACHPayeeInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + str2, 0);
    }
    else if (paramACHPayeeInfo.getNickName() == null)
    {
      paramACHPayeeInfo.setStatusCode(16010);
      str2 = "NICKNAME   contains null ";
      paramACHPayeeInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + str2, 0);
    }
    else if (paramACHPayeeInfo.getManagedPayee() == null)
    {
      paramACHPayeeInfo.setStatusCode(16010);
      str2 = "MANAGED PAYEE   contains null ";
      paramACHPayeeInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + str2, 0);
    }
    else if ((!paramACHPayeeInfo.getManagedPayee().equals("Y")) && (!paramACHPayeeInfo.getManagedPayee().equals("N")) && (!paramACHPayeeInfo.getManagedPayee().equals("Template")))
    {
      paramACHPayeeInfo.setStatusCode(23470);
      str2 = "Invalid value for managedPayee field in ACHPayeeInfo: " + paramACHPayeeInfo.getManagedPayee();
      paramACHPayeeInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + str2, 0);
    }
    else if (!jdMethod_byte(paramACHPayeeInfo))
    {
      paramACHPayeeInfo.setStatusCode(23670);
      paramACHPayeeInfo.setStatusMsg("Invalid BankRTN : " + paramACHPayeeInfo.getBankRTN());
    }
    else if (!jdMethod_for(paramACHPayeeInfo))
    {
      paramACHPayeeInfo.setStatusCode(23760);
      paramACHPayeeInfo.setStatusMsg("Pay Acct length has exceeded the maximum value = 22 : Pay Acct =" + paramACHPayeeInfo.getPayAcct());
    }
    else if (!jdMethod_do(paramACHPayeeInfo))
    {
      paramACHPayeeInfo.setStatusCode(17210);
      paramACHPayeeInfo.setStatusMsg("Invalid Payee Secure mode. Valid values are 0 and 1, But we got :" + paramACHPayeeInfo.getSecurePayee());
    }
    else
    {
      paramACHPayeeInfo.setStatusCode(0);
      paramACHPayeeInfo.setStatusMsg("Success");
    }
    FFSDebug.log(str1 + " done, Status =" + paramACHPayeeInfo.getStatusMsg(), 6);
    return paramACHPayeeInfo;
  }
  
  private static boolean jdMethod_byte(ACHPayeeInfo paramACHPayeeInfo)
  {
    boolean bool = true;
    try
    {
      bool = BPWUtil.validateABA(paramACHPayeeInfo.getBankRTN());
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log("Invalid BankRTN. Exception: " + localFFSException.toString(), 0);
      bool = false;
    }
    return bool;
  }
  
  private static boolean jdMethod_for(ACHPayeeInfo paramACHPayeeInfo)
  {
    boolean bool = true;
    try
    {
      bool = BPWUtil.validatePayAcct(paramACHPayeeInfo.getPayAcct());
    }
    catch (Exception localException)
    {
      FFSDebug.log("Pay Acct length has exceeded the maximum value = 22. Exception: " + localException.toString(), 0);
      bool = false;
    }
    return bool;
  }
  
  private static boolean jdMethod_new(ACHPayeeInfo paramACHPayeeInfo)
  {
    boolean bool = true;
    try
    {
      bool = BPWUtil.validatePayeeName(paramACHPayeeInfo.getPayeeName());
    }
    catch (Exception localException)
    {
      FFSDebug.log("Payee Name length has exceeded the maximum value = 22. Exception: " + localException.toString(), 0);
      bool = false;
    }
    return bool;
  }
  
  private static ACHPayeeInfo a(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo, ArrayList paramArrayList, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHPayee.getAllBatchesForPayee ";
    ACHRecordInfo[] arrayOfACHRecordInfo1 = null;
    int i = 0;
    TreeMap localTreeMap = null;
    FFSDebug.log(str1, "Start", 6);
    if (paramArrayList == null)
    {
      String str2 = str1 + "failed:" + " Null ArrayList Object passed";
      paramACHPayeeInfo.setStatusCode(16000);
      paramACHPayeeInfo.setStatusMsg("ArrayList parameter  is null");
      FFSDebug.log(str2, 0);
      return paramACHPayeeInfo;
    }
    arrayOfACHRecordInfo1 = ACHRecord.getRecordsByPayee(paramFFSConnectionHolder, paramACHPayeeInfo.getPayeeID(), true, paramBoolean);
    if (arrayOfACHRecordInfo1 == null) {
      return paramACHPayeeInfo;
    }
    i = arrayOfACHRecordInfo1.length;
    localTreeMap = new TreeMap();
    FFSDebug.log(str1, "Going to Separate batches", 6);
    for (int j = 0; j < i; j++)
    {
      localObject = (ArrayList)localTreeMap.get(arrayOfACHRecordInfo1[j].getBatchId());
      FFSDebug.log(str1, "ID of Batch#" + j, arrayOfACHRecordInfo1[j].getBatchId(), 6);
      arrayOfACHRecordInfo1[j].setAction("mod");
      if (localObject == null)
      {
        localObject = new ArrayList();
        ((ArrayList)localObject).add(arrayOfACHRecordInfo1[j]);
        localTreeMap.put(arrayOfACHRecordInfo1[j].getBatchId(), localObject);
      }
      else
      {
        ((ArrayList)localObject).add(arrayOfACHRecordInfo1[j]);
      }
    }
    FFSDebug.log(str1, "Batches are separated", 6);
    FFSDebug.log(str1, "Number of Batches=" + localTreeMap.size(), 6);
    FFSDebug.log(str1, "Going to populate batchInfos", 6);
    Set localSet = localTreeMap.keySet();
    Object localObject = localSet.iterator();
    while (((Iterator)localObject).hasNext())
    {
      String str3 = (String)((Iterator)localObject).next();
      FFSDebug.log(str1, "populating for batchId=" + str3, 6);
      ACHBatchInfo localACHBatchInfo = ACHBatch.getACHBatchById(paramFFSConnectionHolder, str3, paramBoolean);
      ArrayList localArrayList = (ArrayList)localTreeMap.get(str3);
      ACHRecordInfo[] arrayOfACHRecordInfo2 = (ACHRecordInfo[])localArrayList.toArray(new ACHRecordInfo[0]);
      localACHBatchInfo.setRecords(arrayOfACHRecordInfo2);
      paramArrayList.add(localACHBatchInfo);
    }
    FFSDebug.log(str1, "Done", 6);
    return paramACHPayeeInfo;
  }
  
  private static String a(ACHPayeeList paramACHPayeeList, ArrayList paramArrayList)
  {
    StringBuffer localStringBuffer = new StringBuffer("SELECT a.PayeeId, a.CompId, a.PayAcct, a.PayeeType, a.PayeeGroup, a.PayeeName, a.NickName, a.BankRTN, a.BankAcctId, a.BankAcctType, a.CardNum, a.CardExpireDate, a.CardTransCode, a.CardAuthCode, a.Addr1, a.Addr2, a.Addr3, a.City, a.State, a.Zipcode, a.Country, a.Phone, a.Extension, a.Status, a.ContactName, a.SubmitDate, a.ActivationDate, a.LogId, a.Memo, a.PrenoteCredStatus, a.PrenoteDebStatus, a.PrenoteSubmitDate, a.PrenoteSecCode, a.PrenoteDemand, a.ManagedPayee, a.SubmittedBy, a.SecurePayee  FROM ACH_Payee a, ACH_Company b  WHERE a.CompId = b.CompId  AND a.Status != 'CLOSED'  AND b.CompStatus != 'CLOSED'");
    DBUtil.appendToCondition(localStringBuffer, paramACHPayeeList.getCustId(), " AND b.CustomerId = ? ", paramArrayList);
    DBUtil.appendArrayToCondition(localStringBuffer, paramACHPayeeList.getPayeeTypeList(), " AND a.PayeeType IN ( ", paramArrayList);
    DBUtil.appendArrayToCondition(localStringBuffer, paramACHPayeeList.getPayeeGroupList(), " AND a.PayeeGroup IN ( ", paramArrayList);
    DBUtil.appendToCondition(localStringBuffer, paramACHPayeeList.getFIId(), " AND b.ACHFIId IN (SELECT ACHFIId FROM ACH_FIInfo WHERE FIId = ?  AND FIStatus != 'CLOSED') ", paramArrayList);
    DBUtil.appendArrayToCondition(localStringBuffer, paramACHPayeeList.getSubmittedBy(), " AND a.SubmittedBy IN ( ", paramArrayList);
    DBUtil.appendToCondition(localStringBuffer, paramACHPayeeList.getManagedPayee(), " AND a.ManagedPayee = ? ", paramArrayList);
    DBUtil.appendArrayToCondition(localStringBuffer, paramACHPayeeList.getPayeeStatusList(), " AND a.Status IN ( ", paramArrayList);
    DBUtil.appendArrayToCondition(localStringBuffer, paramACHPayeeList.getCompIdList(), " AND a.CompId IN ( ", paramArrayList);
    DBUtil.appendToCondition(localStringBuffer, paramACHPayeeList.getPrenoteCreditStatus(), " AND a.PrenoteCredStatus = ? ", paramArrayList);
    return localStringBuffer.toString();
  }
  
  public static ACHPayeeList getACHPayeeList(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeList paramACHPayeeList)
    throws FFSException
  {
    String str1 = "ACHPayee.getACHPayeeList: ";
    FFSDebug.log(str1 + "start...", 6);
    if (paramACHPayeeList == null)
    {
      FFSDebug.log(str1 + "parameter achPayeeList is null");
      paramACHPayeeList.setStatusCode(16000);
      paramACHPayeeList.setStatusMsg(" is null");
      return paramACHPayeeList;
    }
    paramACHPayeeList.setSubmittedBy(DBUtil.removeDupString(paramACHPayeeList.getSubmittedBy()));
    paramACHPayeeList.setCompIdList(DBUtil.removeDupString(paramACHPayeeList.getCompIdList()));
    paramACHPayeeList.setPayeeGroupList(DBUtil.removeDupString(paramACHPayeeList.getPayeeGroupList()));
    paramACHPayeeList.setPayeeStatusList(DBUtil.removeDupString(paramACHPayeeList.getPayeeStatusList()));
    paramACHPayeeList.setPayeeTypeList(DBUtil.removeDupString(paramACHPayeeList.getPayeeTypeList()));
    ArrayList localArrayList1 = new ArrayList();
    String str2 = a(paramACHPayeeList, localArrayList1);
    Object[] arrayOfObject = (Object[])localArrayList1.toArray(new Object[0]);
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      ArrayList localArrayList2 = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        localObject1 = new ACHPayeeInfo();
        a(localFFSResultSet, (ACHPayeeInfo)localObject1);
        int i = 1;
        if (((ACHPayeeInfo)localObject1).getDoPrenote() == true)
        {
          String str3 = ((ACHPayeeInfo)localObject1).getPrenoteMaturedDate();
          if (str3 == null)
          {
            str3 = getPrenoteMaturedDate(paramFFSConnectionHolder, (ACHPayeeInfo)localObject1);
            ((ACHPayeeInfo)localObject1).setPrenoteMaturedDate(str3);
          }
          str3 = BPWUtil.getDateInNewFormat(str3, "yyyyMMdd", "yyyy/MM/dd");
          if ((paramACHPayeeList.getPrenoteDateStart() != null) && (paramACHPayeeList.getPrenoteDateStart().compareTo(str3) > 0) && (((ACHPayeeInfo)localObject1).getPrenoteSubmitDate() != null) && (paramACHPayeeList.getPrenoteDateStart().compareTo(((ACHPayeeInfo)localObject1).getPrenoteSubmitDate()) > 0)) {
            i = 0;
          }
          if ((i == 1) && (paramACHPayeeList.getPrenoteDateEnd() != null) && (paramACHPayeeList.getPrenoteDateEnd().compareTo(str3) < 0) && (((ACHPayeeInfo)localObject1).getPrenoteSubmitDate() != null) && (paramACHPayeeList.getPrenoteDateEnd().compareTo(((ACHPayeeInfo)localObject1).getPrenoteSubmitDate()) < 0)) {
            i = 0;
          }
        }
        if (i == 1) {
          localArrayList2.add(localObject1);
        }
      }
      paramACHPayeeList.setPayees((Object[])localArrayList2.toArray(new Object[0]));
      if (localArrayList2.isEmpty())
      {
        paramACHPayeeList.setStatusCode(16020);
        paramACHPayeeList.setStatusMsg(" record not found");
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = "***" + str1 + " failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable, (String)localObject1);
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
          FFSDebug.log("***" + str1 + " failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
    FFSDebug.log(str1 + "end.", 6);
    return paramACHPayeeList;
  }
  
  private static boolean jdMethod_try(ACHPayeeInfo paramACHPayeeInfo)
  {
    if (paramACHPayeeInfo.getPayeeID() == null)
    {
      paramACHPayeeInfo.setStatusCode(16010);
      paramACHPayeeInfo.setStatusMsg("PAYEEID  contains null ");
      return true;
    }
    return false;
  }
  
  private static boolean av(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    return (paramString.compareTo("Not Required") == 0) || (paramString.compareTo("Failed") == 0) || (paramString.compareTo("Pending") == 0) || (paramString.compareTo("Success") == 0);
  }
  
  public static ACHPayeeInfo updateACHPayeePrenoteStatus(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str1 = "ACHPayee.updateACHPayeePrenoteStatus: ";
    FFSDebug.log(str1 + "start", 6);
    if (paramACHPayeeInfo == null)
    {
      paramACHPayeeInfo = new ACHPayeeInfo();
      paramACHPayeeInfo.setStatusCode(16000);
      str2 = "Parameter ACHPayeeInfo  is null";
      FFSDebug.log(str1 + str2, 0);
      paramACHPayeeInfo.setStatusMsg(str2);
      return paramACHPayeeInfo;
    }
    if (jdMethod_try(paramACHPayeeInfo) == true)
    {
      FFSDebug.log(str1 + paramACHPayeeInfo.getStatusMsg(), 0);
      return paramACHPayeeInfo;
    }
    if ((!av(paramACHPayeeInfo.getPrenoteCreditStatus())) || (!av(paramACHPayeeInfo.getPrenoteDebitStatus())))
    {
      paramACHPayeeInfo.setStatusCode(23490);
      paramACHPayeeInfo.setStatusMsg("Invalid ACH Payee prenote status: " + paramACHPayeeInfo.getPrenoteCreditStatus() + ", " + paramACHPayeeInfo.getPrenoteDebitStatus());
      FFSDebug.log(str1 + paramACHPayeeInfo.getStatusMsg(), 0);
      return paramACHPayeeInfo;
    }
    String str2 = "UPDATE ACH_Payee SET PrenoteCredStatus = ?, PrenoteDebStatus = ? WHERE PayeeId = ?  AND Status != 'CLOSED'";
    Object[] arrayOfObject = { paramACHPayeeInfo.getPrenoteCreditStatus(), paramACHPayeeInfo.getPrenoteDebitStatus(), paramACHPayeeInfo.getPayeeID() };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (i == 0)
      {
        str3 = " record not found: " + paramACHPayeeInfo.getPayeeID();
        paramACHPayeeInfo.setStatusCode(16020);
        paramACHPayeeInfo.setStatusMsg(str3);
        FFSDebug.log(str1 + str3, 0);
      }
      else
      {
        paramACHPayeeInfo.setStatusCode(0);
        paramACHPayeeInfo.setStatusMsg("Success");
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = "***" + str1 + " failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
    FFSDebug.log(str1 + "done.", 6);
    return paramACHPayeeInfo;
  }
  
  public static int updateMaturedACHPayeePrenoteStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    int i = jdMethod_new(paramFFSConnectionHolder, paramString, true);
    i += jdMethod_new(paramFFSConnectionHolder, paramString, false);
    return i;
  }
  
  private static int jdMethod_new(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHPayee.updateMaturedACHPayeePrenoteStatus: ";
    FFSDebug.log(str1 + "start. Is credit: " + paramBoolean, 6);
    FFSDebug.log(str1 + "start. matureDateStr : " + paramString, 6);
    String str2 = null;
    if (paramBoolean) {
      str2 = "UPDATE ACH_Payee SET PrenoteCredStatus = 'Success' WHERE PrenoteCredStatus = 'Pending' AND PrenoteSubmitDate IS NOT NULL  AND PrenoteSubmitDate < ?";
    } else {
      str2 = "UPDATE ACH_Payee SET PrenoteDebStatus = 'Success' WHERE PrenoteDebStatus = 'Pending' AND PrenoteSubmitDate IS NOT NULL  AND PrenoteSubmitDate < ?";
    }
    Object[] arrayOfObject = { paramString };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Throwable localThrowable)
    {
      String str3 = "***" + str1 + " failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
    FFSDebug.log(str1 + "done.", 6);
    return i;
  }
  
  public static ACHPayeeInfo updateACHPayeePrenoteSubmitDate(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str1 = "ACHPayee.updateACHPayeePrenoteSubmitDate: ";
    FFSDebug.log(str1 + "start", 6);
    if (paramACHPayeeInfo == null)
    {
      paramACHPayeeInfo = new ACHPayeeInfo();
      paramACHPayeeInfo.setStatusCode(16000);
      str2 = "Parameter ACHPayeeInfo  is null";
      FFSDebug.log(str1 + str2, 0);
      paramACHPayeeInfo.setStatusMsg(str2);
      return paramACHPayeeInfo;
    }
    if (jdMethod_try(paramACHPayeeInfo) == true)
    {
      FFSDebug.log(str1 + paramACHPayeeInfo.getStatusMsg(), 0);
      return paramACHPayeeInfo;
    }
    String str2 = "UPDATE ACH_Payee SET PrenoteSubmitDate = ? WHERE PayeeId = ?";
    Object[] arrayOfObject = { paramACHPayeeInfo.getPrenoteSubmitDate(), paramACHPayeeInfo.getPayeeID() };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (i == 0)
      {
        str3 = " record not found: " + paramACHPayeeInfo.getPayeeID();
        paramACHPayeeInfo.setStatusCode(16020);
        paramACHPayeeInfo.setStatusMsg(str3);
        FFSDebug.log(str1 + str3, 0);
      }
      else
      {
        paramACHPayeeInfo.setStatusCode(0);
        paramACHPayeeInfo.setStatusMsg("Success");
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = "***" + str1 + " failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
    FFSDebug.log(str1 + "done.", 6);
    return paramACHPayeeInfo;
  }
  
  public static boolean checkPayeePrenoteForACHBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "ACHPayee.checkPayeePrenoteForACHBatch: ";
    FFSDebug.log(str1 + "start", 6);
    String str2 = "SELECT COUNT(ar.RecordId) FROM ACH_Record ar, ACH_Payee ap WHERE  ar.BatchId = ? AND ar.PayeeId = ap.PayeeId AND ( ( ap.PrenoteCredStatus != 'Success'  AND ap.PrenoteCredStatus != 'Not Required')  OR  ( ap.PrenoteDebStatus != 'Success'  AND ap.PrenoteDebStatus != 'Not Required') )";
    Object[] arrayOfObject = { paramString1 };
    int i = 0;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = "***" + str1 + " failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
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
          FFSDebug.log("***" + str1 + " failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
    FFSDebug.log(str1 + "done. recordCount:" + i, 6);
    return i == 0;
  }
  
  public static String getPrenoteMaturedDate(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws Exception
  {
    String str1 = null;
    String str2 = paramACHPayeeInfo.getCompId();
    if (str2 != null)
    {
      String str3 = ACHCompany.getFiIdbyCompId(paramFFSConnectionHolder, str2);
      int i = 0;
      String str4 = paramACHPayeeInfo.getPrenoteSubmitDate();
      if ((str4 == null) || (str4.length() == 0))
      {
        i = DBUtil.getNextScheduleRunDate(paramFFSConnectionHolder, str3, "ACHBATCHTRN");
      }
      else
      {
        str1 = BPWUtil.getDateInNewFormat(str4, "yyyy/MM/dd", "yyyyMMdd");
        i = Integer.parseInt(str1);
      }
      if (str3 != null)
      {
        for (int j = 0; j < 6; j++) {
          i = SmartCalendar.getACHBusinessDay(str3, i, true);
        }
        str1 = Integer.toString(i);
      }
    }
    return str1;
  }
  
  private static boolean jdMethod_int(ACHPayeeInfo paramACHPayeeInfo)
  {
    String str1 = paramACHPayeeInfo.getLogId();
    if (str1 == null)
    {
      paramACHPayeeInfo.setStatusCode(23870);
      paramACHPayeeInfo.setStatusMsg("LogID field is null");
      return false;
    }
    if (str1.length() > 32)
    {
      paramACHPayeeInfo.setStatusCode(23880);
      paramACHPayeeInfo.setStatusMsg("LogID field is too long: " + str1);
      return false;
    }
    String str2 = paramACHPayeeInfo.getSubmittedBy();
    if (str2 == null)
    {
      paramACHPayeeInfo.setStatusCode(23890);
      paramACHPayeeInfo.setStatusMsg("SubmittedBy field is null");
      return false;
    }
    if (str2.length() > 32)
    {
      paramACHPayeeInfo.setStatusCode(23900);
      paramACHPayeeInfo.setStatusMsg("SubmittedBy field is too long: " + str2);
      return false;
    }
    return true;
  }
  
  public static void doTransAuditLog(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    ACHCompanyInfo localACHCompanyInfo = ACHCompany.getCompanyInfo(paramACHPayeeInfo.getCompId(), paramFFSConnectionHolder);
    if (localACHCompanyInfo.getStatusCode() != 0) {
      throw new FFSException("Failed to log into AuditLog: Invalid CompId " + paramACHPayeeInfo.getCompId());
    }
    BigDecimal localBigDecimal = new BigDecimal(0.0D);
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramString1, paramACHPayeeInfo.getAgentId(), paramACHPayeeInfo.getAgentType(), paramString2, paramACHPayeeInfo.getLogId(), paramInt, Integer.parseInt(localACHCompanyInfo.getCustomerId()), localBigDecimal, null, paramACHPayeeInfo.getPayeeID(), paramACHPayeeInfo.getStatus(), null, null, null, null, 0);
    TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
  }
  
  public static ACHPayeeInfo[] getMaturedACHPayeeInfo(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ACHPayee.getMatureACHPayeeInfo: ";
    FFSDebug.log(str1 + "start. MatureDate " + paramString, 6);
    ArrayList localArrayList = new ArrayList();
    if (paramString == null)
    {
      localObject1 = "***" + str1 + " failed: matureDate is null";
      FFSDebug.log((String)localObject1, 0);
      return null;
    }
    Object localObject1 = null;
    String str2 = "SELECT  PayeeId, CompId, PayAcct, PayeeType, PayeeGroup, PayeeName, NickName, BankRTN, BankAcctId, BankAcctType, CardNum, CardExpireDate, CardTransCode, CardAuthCode, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, Extension, Status, ContactName, SubmitDate, ActivationDate, LogId, Memo, PrenoteCredStatus, PrenoteDebStatus, PrenoteSubmitDate,  PrenoteSecCode, PrenoteDemand, ManagedPayee, SubmittedBy, SecurePayee  FROM ACH_Payee  WHERE (PrenoteCredStatus = 'Pending' OR PrenoteDebStatus = 'Pending') AND PrenoteSubmitDate IS NOT NULL  AND PrenoteSubmitDate < ?";
    Object[] arrayOfObject = { paramString };
    try
    {
      localObject1 = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (((FFSResultSet)localObject1).getNextRow())
      {
        localObject2 = new ACHPayeeInfo();
        a((FFSResultSet)localObject1, (ACHPayeeInfo)localObject2);
        if (((ACHPayeeInfo)localObject2).getDoPrenote() == true) {
          ((ACHPayeeInfo)localObject2).setPrenoteMaturedDate(getPrenoteMaturedDate(paramFFSConnectionHolder, (ACHPayeeInfo)localObject2));
        }
        ((ACHPayeeInfo)localObject2).setStatusCode(0);
        ((ACHPayeeInfo)localObject2).setStatusMsg("Success");
        localArrayList.add(localObject2);
      }
      FFSDebug.log(str1 + " end. Number of payees: " + localArrayList.size(), 6);
      Object localObject2 = (ACHPayeeInfo[])localArrayList.toArray(new ACHPayeeInfo[0]);
      return localObject2;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "*** " + str1 + " failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      if (localObject1 != null) {
        try
        {
          ((FFSResultSet)localObject1).close();
          localObject1 = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("*** " + str1 + " failed to close ResultSet" + localException.toString(), 0);
        }
      }
    }
  }
  
  private static boolean jdMethod_do(ACHPayeeInfo paramACHPayeeInfo)
  {
    return (paramACHPayeeInfo.getSecurePayee() == 0) || (paramACHPayeeInfo.getSecurePayee() == 1);
  }
  
  private static ACHPayeeInfo a(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo, String paramString)
    throws FFSException
  {
    if ((paramACHPayeeInfo.getManagedPayee().equals("N") == true) || (paramACHPayeeInfo.getManagedPayee().equals("Template") == true)) {
      return null;
    }
    ACHPayeeInfo localACHPayeeInfo = null;
    String str = paramACHPayeeInfo.getPayeeGroup();
    if (str == null)
    {
      localACHPayeeInfo = new ACHPayeeInfo();
      localACHPayeeInfo.setStatusCode(23950);
      localACHPayeeInfo.setStatusMsg("Invalid payee group: " + str);
      return localACHPayeeInfo;
    }
    if (str.equals("Business"))
    {
      localACHPayeeInfo = jdMethod_if(paramFFSConnectionHolder, paramACHPayeeInfo, paramString);
    }
    else if (str.equals("Company"))
    {
      localACHPayeeInfo = jdMethod_if(paramFFSConnectionHolder, paramACHPayeeInfo);
    }
    else if (str.equals("User"))
    {
      localACHPayeeInfo = jdMethod_do(paramFFSConnectionHolder, paramACHPayeeInfo);
    }
    else
    {
      localACHPayeeInfo = new ACHPayeeInfo();
      localACHPayeeInfo.setStatusCode(23950);
      localACHPayeeInfo.setStatusMsg("Invalid payee group: " + str);
    }
    return localACHPayeeInfo;
  }
  
  private static ACHPayeeInfo jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo, String paramString)
    throws FFSException
  {
    String str = "SELECT  ap.PayeeId, ap.CompId, ap.PayAcct, ap.PayeeType, ap.PayeeGroup, ap.PayeeName, ap.NickName, ap.BankRTN, ap.BankAcctId, ap.BankAcctType, ap.CardNum, ap.CardExpireDate, ap.CardTransCode, ap.CardAuthCode, ap.Addr1, ap.Addr2, ap.Addr3, ap.City, ap.State, ap.Zipcode, ap.Country, ap.Phone, ap.Extension, ap.Status, ap.ContactName, ap.SubmitDate, ap.ActivationDate, ap.LogId, ap.Memo, ap.PrenoteCredStatus, ap.PrenoteDebStatus, ap.PrenoteSubmitDate,  ap.PrenoteSecCode, ap.PrenoteDemand, ap.ManagedPayee, ap.SubmittedBy, ap.SecurePayee  FROM ACH_Payee ap , ACH_Company ac, BPW_Customer bc WHERE ap.PayeeId != ? AND ((ap.PayAcct = ? AND ap.BankAcctId = ? AND ap.BankRTN = ?) OR ap.NickName = ?) AND ap.Status != 'CLOSED' AND ap.ManagedPayee = 'Y' AND ap.PayeeGroup = 'Business' AND ac.CompId = ap.CompId AND bc.CustomerID = ac.CustomerId AND bc.CustomerID = ?";
    String[] arrayOfString = { paramACHPayeeInfo.getPayeeID(), paramACHPayeeInfo.getPayAcct(), paramACHPayeeInfo.getBankAcctId(), paramACHPayeeInfo.getBankRTN(), paramACHPayeeInfo.getNickName(), paramString };
    if (paramACHPayeeInfo.getPayeeID() == null)
    {
      str = "SELECT  ap.PayeeId, ap.CompId, ap.PayAcct, ap.PayeeType, ap.PayeeGroup, ap.PayeeName, ap.NickName, ap.BankRTN, ap.BankAcctId, ap.BankAcctType, ap.CardNum, ap.CardExpireDate, ap.CardTransCode, ap.CardAuthCode, ap.Addr1, ap.Addr2, ap.Addr3, ap.City, ap.State, ap.Zipcode, ap.Country, ap.Phone, ap.Extension, ap.Status, ap.ContactName, ap.SubmitDate, ap.ActivationDate, ap.LogId, ap.Memo, ap.PrenoteCredStatus, ap.PrenoteDebStatus, ap.PrenoteSubmitDate,  ap.PrenoteSecCode, ap.PrenoteDemand, ap.ManagedPayee, ap.SubmittedBy, ap.SecurePayee  FROM ACH_Payee ap , ACH_Company ac, BPW_Customer bc WHERE ((ap.PayAcct = ? AND ap.BankAcctId = ? AND ap.BankRTN = ?) OR ap.NickName = ?) AND ap.Status != 'CLOSED' AND ap.ManagedPayee = 'Y' AND ap.PayeeGroup = 'Business' AND ac.CompId = ap.CompId AND bc.CustomerID = ac.CustomerId AND bc.CustomerID = ?";
      arrayOfString = new String[5];
      arrayOfString[0] = paramACHPayeeInfo.getPayAcct();
      arrayOfString[1] = paramACHPayeeInfo.getBankAcctId();
      arrayOfString[2] = paramACHPayeeInfo.getBankRTN();
      arrayOfString[3] = paramACHPayeeInfo.getNickName();
      arrayOfString[4] = paramString;
    }
    ACHPayeeInfo[] arrayOfACHPayeeInfo = a(paramFFSConnectionHolder, str, arrayOfString);
    if ((arrayOfACHPayeeInfo != null) && (arrayOfACHPayeeInfo.length > 0)) {
      return arrayOfACHPayeeInfo[0];
    }
    return null;
  }
  
  private static ACHPayeeInfo jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str = "SELECT  ap.PayeeId, ap.CompId, ap.PayAcct, ap.PayeeType, ap.PayeeGroup, ap.PayeeName, ap.NickName, ap.BankRTN, ap.BankAcctId, ap.BankAcctType, ap.CardNum, ap.CardExpireDate, ap.CardTransCode, ap.CardAuthCode, ap.Addr1, ap.Addr2, ap.Addr3, ap.City, ap.State, ap.Zipcode, ap.Country, ap.Phone, ap.Extension, ap.Status, ap.ContactName, ap.SubmitDate, ap.ActivationDate, ap.LogId, ap.Memo, ap.PrenoteCredStatus, ap.PrenoteDebStatus, ap.PrenoteSubmitDate,  ap.PrenoteSecCode, ap.PrenoteDemand, ap.ManagedPayee, ap.SubmittedBy, ap.SecurePayee  FROM ACH_Payee ap , ACH_Company ac WHERE ap.PayeeId != ? AND ac.CompId = ? AND ap.CompId = ac.CompId AND ((ap.PayAcct = ? AND ap.BankAcctId = ? AND ap.BankRTN = ?) OR ap.NickName = ?) AND ap.Status != 'CLOSED' AND ap.ManagedPayee = 'Y' AND ap.PayeeGroup = 'Company'";
    String[] arrayOfString = { paramACHPayeeInfo.getPayeeID(), paramACHPayeeInfo.getCompId(), paramACHPayeeInfo.getPayAcct(), paramACHPayeeInfo.getBankAcctId(), paramACHPayeeInfo.getBankRTN(), paramACHPayeeInfo.getNickName() };
    if (paramACHPayeeInfo.getPayeeID() == null)
    {
      str = "SELECT  ap.PayeeId, ap.CompId, ap.PayAcct, ap.PayeeType, ap.PayeeGroup, ap.PayeeName, ap.NickName, ap.BankRTN, ap.BankAcctId, ap.BankAcctType, ap.CardNum, ap.CardExpireDate, ap.CardTransCode, ap.CardAuthCode, ap.Addr1, ap.Addr2, ap.Addr3, ap.City, ap.State, ap.Zipcode, ap.Country, ap.Phone, ap.Extension, ap.Status, ap.ContactName, ap.SubmitDate, ap.ActivationDate, ap.LogId, ap.Memo, ap.PrenoteCredStatus, ap.PrenoteDebStatus, ap.PrenoteSubmitDate,  ap.PrenoteSecCode, ap.PrenoteDemand, ap.ManagedPayee, ap.SubmittedBy, ap.SecurePayee  FROM ACH_Payee ap , ACH_Company ac WHERE ac.CompId = ? AND ap.CompId = ac.CompId AND (( ap.PayAcct = ? AND ap.BankAcctId = ? AND ap.BankRTN = ?) OR ap.NickName = ?) AND ap.Status != 'CLOSED' AND ap.ManagedPayee = 'Y' AND ap.PayeeGroup = 'Company'";
      arrayOfString = new String[5];
      arrayOfString[0] = paramACHPayeeInfo.getCompId();
      arrayOfString[1] = paramACHPayeeInfo.getPayAcct();
      arrayOfString[2] = paramACHPayeeInfo.getBankAcctId();
      arrayOfString[3] = paramACHPayeeInfo.getBankRTN();
      arrayOfString[4] = paramACHPayeeInfo.getNickName();
    }
    ACHPayeeInfo[] arrayOfACHPayeeInfo = a(paramFFSConnectionHolder, str, arrayOfString);
    if ((arrayOfACHPayeeInfo != null) && (arrayOfACHPayeeInfo.length > 0)) {
      return arrayOfACHPayeeInfo[0];
    }
    return null;
  }
  
  private static ACHPayeeInfo jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str = "SELECT  ap.PayeeId, ap.CompId, ap.PayAcct, ap.PayeeType, ap.PayeeGroup, ap.PayeeName, ap.NickName, ap.BankRTN, ap.BankAcctId, ap.BankAcctType, ap.CardNum, ap.CardExpireDate, ap.CardTransCode, ap.CardAuthCode, ap.Addr1, ap.Addr2, ap.Addr3, ap.City, ap.State, ap.Zipcode, ap.Country, ap.Phone, ap.Extension, ap.Status, ap.ContactName, ap.SubmitDate, ap.ActivationDate, ap.LogId, ap.Memo, ap.PrenoteCredStatus, ap.PrenoteDebStatus, ap.PrenoteSubmitDate,  ap.PrenoteSecCode, ap.PrenoteDemand, ap.ManagedPayee, ap.SubmittedBy, ap.SecurePayee  FROM ACH_Payee ap  WHERE ap.PayeeId != ? AND ap.PayAcct = ? AND ((ap.BankAcctId = ? AND ap.BankRTN = ?) OR ap.NickName = ?) AND ap.Status != 'CLOSED' AND ap.ManagedPayee = 'Y' AND ap.SubmittedBy = ? AND ap.PayeeGroup ='User'";
    String[] arrayOfString = { paramACHPayeeInfo.getPayeeID(), paramACHPayeeInfo.getPayAcct(), paramACHPayeeInfo.getBankAcctId(), paramACHPayeeInfo.getBankRTN(), paramACHPayeeInfo.getNickName(), paramACHPayeeInfo.getSubmittedBy() };
    if (paramACHPayeeInfo.getPayeeID() == null)
    {
      str = "SELECT  ap.PayeeId, ap.CompId, ap.PayAcct, ap.PayeeType, ap.PayeeGroup, ap.PayeeName, ap.NickName, ap.BankRTN, ap.BankAcctId, ap.BankAcctType, ap.CardNum, ap.CardExpireDate, ap.CardTransCode, ap.CardAuthCode, ap.Addr1, ap.Addr2, ap.Addr3, ap.City, ap.State, ap.Zipcode, ap.Country, ap.Phone, ap.Extension, ap.Status, ap.ContactName, ap.SubmitDate, ap.ActivationDate, ap.LogId, ap.Memo, ap.PrenoteCredStatus, ap.PrenoteDebStatus, ap.PrenoteSubmitDate,  ap.PrenoteSecCode, ap.PrenoteDemand, ap.ManagedPayee, ap.SubmittedBy, ap.SecurePayee  FROM ACH_Payee ap  WHERE (ap.PayAcct = ? AND (ap.BankAcctId = ? AND ap.BankRTN = ?) OR ap.NickName = ?) AND ap.Status != 'CLOSED' AND ap.ManagedPayee = 'Y' AND ap.SubmittedBy = ? AND  ap.PayeeGroup ='User'";
      arrayOfString = new String[5];
      arrayOfString[0] = paramACHPayeeInfo.getPayAcct();
      arrayOfString[1] = paramACHPayeeInfo.getBankAcctId();
      arrayOfString[2] = paramACHPayeeInfo.getBankRTN();
      arrayOfString[3] = paramACHPayeeInfo.getNickName();
      arrayOfString[4] = paramACHPayeeInfo.getSubmittedBy();
    }
    ACHPayeeInfo[] arrayOfACHPayeeInfo = a(paramFFSConnectionHolder, str, arrayOfString);
    if ((arrayOfACHPayeeInfo != null) && (arrayOfACHPayeeInfo.length > 0)) {
      return arrayOfACHPayeeInfo[0];
    }
    return null;
  }
  
  private static ACHPayeeInfo[] a(FFSConnectionHolder paramFFSConnectionHolder, String paramString, String[] paramArrayOfString)
    throws FFSException
  {
    String str1 = "ACHPayee.getPayee:";
    FFSDebug.log(str1 + " start", 6);
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString, paramArrayOfString);
      while (localFFSResultSet.getNextRow())
      {
        ACHPayeeInfo localACHPayeeInfo = new ACHPayeeInfo();
        a(localFFSResultSet, localACHPayeeInfo);
        localArrayList.add(localACHPayeeInfo);
      }
    }
    catch (Exception localException1)
    {
      String str2 = "*** " + str1 + " failed to get number of payees.";
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
          FFSDebug.log(str1 + " failed to close ResultSet " + localException2.toString(), 0);
        }
      }
    }
    FFSDebug.log(str1 + " done ", 6);
    return (ACHPayeeInfo[])localArrayList.toArray(new ACHPayeeInfo[0]);
  }
  
  private static boolean a(ACHPayeeInfo paramACHPayeeInfo1, ACHPayeeInfo paramACHPayeeInfo2)
  {
    return (!paramACHPayeeInfo1.getPayAcct().equals(paramACHPayeeInfo2.getPayAcct())) || (!paramACHPayeeInfo1.getBankAcctId().equals(paramACHPayeeInfo2.getBankAcctId())) || (!paramACHPayeeInfo1.getBankRTN().equals(paramACHPayeeInfo2.getBankRTN()));
  }
  
  private static boolean jdMethod_byte(String paramString1, String paramString2)
  {
    if ("Business".equals(paramString1))
    {
      if (!"Business".equals(paramString2)) {
        return true;
      }
    }
    else if (("Company".equals(paramString1)) && ("User".equals(paramString2))) {
      return true;
    }
    return false;
  }
  
  public static ACHPayeeInfo[] getACHPayeeInfoInBatch(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    ACHRecordInfo[] arrayOfACHRecordInfo = paramACHBatchInfo.getRecords();
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    for (int i = 0; i < arrayOfACHRecordInfo.length; i++)
    {
      String str = arrayOfACHRecordInfo[i].getPayeeId();
      if (!localArrayList2.contains(str))
      {
        localArrayList2.add(str);
        ACHPayeeInfo localACHPayeeInfo = getACHPayeeInfo(paramFFSConnectionHolder, str);
        localArrayList1.add(localACHPayeeInfo);
      }
    }
    return (ACHPayeeInfo[])localArrayList1.toArray(new ACHPayeeInfo[0]);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ACHPayee
 * JD-Core Version:    0.7.0.1
 */