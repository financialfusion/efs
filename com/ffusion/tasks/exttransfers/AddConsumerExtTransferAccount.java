package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.BlockedAccts;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.ExternalTransferAdmin;
import com.ffusion.csil.handlers.AccountHandler;
import com.ffusion.tasks.MapError;
import com.ffusion.util.CommBankIdentifier;
import com.ffusion.util.Strings;
import com.ffusion.util.beans.blockedaccts.BlockedAccount;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddConsumerExtTransferAccount
  extends ModifyExtTransferAccount
  implements Task
{
  protected String checkNum2;
  protected String accountNumberVerify;
  protected Boolean iAgree;
  protected boolean isConsumer = false;
  protected String sourceAccts = "BankingAccounts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if ((this.recipientId == null) || (this.recipientId.length() == 0)) {
      this.recipientId = "0000";
    }
    str = addTransferAccount(localHttpSession);
    if (this.error == 0) {
      localHttpSession.setAttribute("ExternalTransferAccountsUpdated", "true");
    }
    return str;
  }
  
  protected String addTransferAccount(HttpSession paramHttpSession)
  {
    String str = null;
    this.modify = false;
    if (validateInput(paramHttpSession))
    {
      ExtTransferCompany localExtTransferCompany = new ExtTransferCompany(this.locale);
      localExtTransferCompany.setCustID(this.custID);
      str = processAddExtTransferAccount(this, localExtTransferCompany, paramHttpSession);
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    boolean bool1 = true;
    boolean bool2 = false;
    if (this.validate != null) {
      this.validate = null;
    }
    if (("FEDABA".equals(this.acctBankIDType)) && (!this.isBankIdentifier))
    {
      if ((this.recipientName == null) || (this.recipientName.trim().length() < 1) || (this.recipientName.trim().length() > 22)) {
        return setError(4212);
      }
      if ((this.recipientId != null) && (this.recipientId.trim().length() > 15)) {
        return setError(4213);
      }
    }
    if (((this.iAgree == null) || (!this.iAgree.booleanValue())) && (this.isConsumer)) {
      return setError(4219);
    }
    if (((this.checkNumber != null) && (this.checkNumber.length() > 0)) || ((this.checkNum2 != null) && (this.checkNum2.length() > 0)))
    {
      if (this.checkNumber == null) {
        this.checkNumber = "";
      }
      if (this.checkNum2 == null) {
        this.checkNum2 = "";
      }
      if ((this.isConsumer) && (numStringCompare(this.checkNumber, this.checkNum2) != 0)) {
        return setError(4220);
      }
    }
    if (this.isConsumer)
    {
      if ((this.accountNumberVerify == null) || (this.accountNumberVerify.length() == 0)) {
        return setError(18018);
      }
      if (!Strings.isValidAccountNumber(this.accountNumberVerify, localSecureUser.getLocale())) {
        return setError(18014);
      }
    }
    if ((this.number == null) || (this.number.length() == 0)) {
      return setError(18019);
    }
    if (!Strings.isValidAccountNumber(this.number, localSecureUser.getLocale())) {
      return setError(18014);
    }
    if ((this.number.length() < 3) || ((this.number.length() > 17) && ("FEDABA".equals(this.acctBankIDType)) && (!this.isBankIdentifier))) {
      return setError(18023);
    }
    if (this.number.length() > 40) {
      return setError(89);
    }
    if ((this.isConsumer) && (numStringCompare(this.accountNumberVerify, this.number) != 0)) {
      return setError(18017);
    }
    if (("FEDABA".equals(this.acctBankIDType)) && (!this.isBankIdentifier))
    {
      try
      {
        bool2 = CommBankIdentifier.isValidCheckZeros(this.routingNumber);
      }
      catch (Exception localException)
      {
        bool2 = false;
      }
      if (!bool2) {
        return setError(18013);
      }
    }
    else if (("SWIFT".equals(this.acctBankIDType)) && ((this.routingNumber == null) || ((this.routingNumber.length() != 8) && (this.routingNumber.length() != 11))))
    {
      return setError(18022);
    }
    if ((this.bankName == null) || (this.bankName.trim().length() == 0)) {
      return setError(18021);
    }
    bool1 = validateLimits();
    return bool1;
  }
  
  protected String processAddExtTransferAccount(ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    Account localAccount = null;
    this.error = 0;
    this.nextURL = this.successURL;
    com.ffusion.beans.accounts.Accounts localAccounts1;
    Object localObject;
    try
    {
      int i = Integer.parseInt(this.custID);
      localAccounts1 = com.ffusion.csil.core.Accounts.getAccountsById(localSecureUser, i, localHashMap);
      setBankId(localSecureUser.getBankID());
      if (this.isConsumer)
      {
        if (this.verifyStatus != 5) {
          setVerifyStatusValue(1);
        }
      }
      else if ((this.verifyStatus != 5) && (this.verifyStatus != 1)) {
        setVerifyStatusValue(0);
      }
      if (localAccounts1 == null)
      {
        setError(18006);
        return this.taskErrorURL;
      }
      localObject = localAccounts1.getByAccountNumberAndType(this.number, this.type);
      if (localObject != null)
      {
        if (((Account)localObject).getCoreAccount().equals("2")) {
          setError(4222);
        } else {
          setError(4227);
        }
        return this.taskErrorURL;
      }
      BlockedAccount localBlockedAccount = new BlockedAccount();
      localBlockedAccount.setAccountNumber(this.number);
      localBlockedAccount.setRoutingNumber(this.routingNumber);
      localBlockedAccount.setBankName(this.bankName);
      localBlockedAccount.setUserDirectoryID(i);
      if (BlockedAccts.isBlockedAccount(localSecureUser, localBlockedAccount, localHashMap))
      {
        if (this.isConsumer) {
          setError(4223);
        } else {
          setError(4226);
        }
        return this.taskErrorURL;
      }
      localAccount = new Account();
      localAccount.setBankName(this.bankName);
      localAccount.setRoutingNum(this.routingNumber);
      localAccount.setNumber(this.number);
      localAccount.setType(getTypeValue());
      localAccount.setID(this.number, "" + getTypeValue());
      if ((getCurrencyCode() == null) || (getCurrencyCode().length() == 0)) {
        localAccount.setCurrencyCode(localSecureUser.getBaseCurrency());
      } else {
        localAccount.setCurrencyCode(getCurrencyCode());
      }
      if ((this.nickname == null) || (this.nickname.length() == 0))
      {
        localAccount.setNickName(this.bankName + " " + localAccount.getType());
        paramExtTransferAccount.setNickname(this.bankName + " " + localAccount.getType());
      }
      else
      {
        localAccount.setNickName(this.nickname);
        paramExtTransferAccount.setNickname(this.nickname);
      }
      localAccount.setPositivePay("0");
      localAccount.setCoreAccount("2");
      localAccount.setZBAFlag("B");
      localAccount.setShowPreviousDayOpeningLedger("N");
      localAccount.put("HIDE", "0");
      com.ffusion.beans.accounts.Accounts localAccounts2 = new com.ffusion.beans.accounts.Accounts(localSecureUser.getLocale());
      localAccounts2.add(localAccount);
      AccountHandler.addAccounts(localSecureUser, localAccounts2, i, localHashMap);
      try
      {
        ExternalTransferAdmin.addExtTransferAccount(localSecureUser, paramExtTransferAccount, paramExtTransferCompany, localHashMap);
      }
      catch (Exception localException2)
      {
        AccountHandler.deleteAccount(localSecureUser, localAccount, i, localHashMap);
        throw localException2;
      }
      paramExtTransferAccount.mapAccount(localAccount);
      localAccount.put("ExternalTransferACCOUNT", this);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      this.nextURL = this.serviceErrorURL;
    }
    catch (Exception localException1)
    {
      DebugLog.log("ERROR: Exception thrown when adding transfer account:");
      localException1.printStackTrace();
      this.nextURL = this.taskErrorURL;
    }
    if (this.error == 0)
    {
      ExtTransferAccount localExtTransferAccount = new ExtTransferAccount(this.locale);
      localExtTransferAccount.set(this);
      paramHttpSession.setAttribute("ExternalTransferACCOUNT", localExtTransferAccount);
      this.user = ((User)paramHttpSession.getAttribute(this.userSessionName));
      processEntitlementsAndLimits(localSecureUser);
      updateAccountEntitlements(localSecureUser, localAccount);
      localAccounts1 = (com.ffusion.beans.accounts.Accounts)paramHttpSession.getAttribute(this.sourceAccts);
      if (localAccounts1 != null) {
        localAccounts1.add(localAccount);
      }
      localObject = (ExtTransferAccounts)paramHttpSession.getAttribute("ExternalTransferAccounts");
      if (localObject != null) {
        ((ExtTransferAccounts)localObject).add(paramExtTransferAccount);
      }
    }
    return this.nextURL;
  }
  
  public void setCheckNumberVerify(String paramString)
  {
    this.checkNum2 = paramString;
  }
  
  public String getCheckNumberVerify()
  {
    return this.checkNum2;
  }
  
  public void setAccountNumberVerify(String paramString)
  {
    if (paramString != null) {
      this.accountNumberVerify = paramString.trim();
    }
  }
  
  public String getAccountNumberVerify()
  {
    return this.accountNumberVerify;
  }
  
  public void setIAgree(String paramString)
  {
    this.iAgree = new Boolean(paramString);
  }
  
  public String getIAgree()
  {
    if (this.iAgree == null) {
      return "";
    }
    return "" + this.iAgree.booleanValue();
  }
  
  public void setIsConsumer(String paramString)
  {
    this.isConsumer = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setPrimaryAcctHolder(String paramString)
  {
    super.setPrimaryAcctHolder(paramString);
    setRecipientName(paramString);
  }
  
  public String getPrimaryAcctHolder()
  {
    String str = super.getPrimaryAcctHolder();
    if ((str == null) || (str.length() == 0)) {
      str = getRecipientName();
    }
    return str;
  }
  
  public void setSourceAccounts(String paramString)
  {
    this.sourceAccts = paramString;
  }
  
  public void setNumber(String paramString)
  {
    if (paramString != null) {
      super.setNumber(paramString.trim());
    }
  }
  
  protected void updateAccountEntitlements(SecureUser paramSecureUser, Account paramAccount)
  {
    try
    {
      EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
      MultiEntitlement localMultiEntitlement = new MultiEntitlement();
      localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(paramAccount) });
      localMultiEntitlement.setOperations(new String[] { "External Transfers From" });
      if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getProfileID()) == null) {
        paramAccount.setFilterable("ExternalTransferFrom");
      }
      localMultiEntitlement.setOperations(new String[] { "External Transfers To" });
      if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getProfileID()) == null) {
        paramAccount.setFilterable("ExternalTransferTo");
      }
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.AddConsumerExtTransferAccount
 * JD-Core Version:    0.7.0.1
 */