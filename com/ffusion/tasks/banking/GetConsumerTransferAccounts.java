package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.BlockedAccts;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.blockedaccts.BlockedAccount;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetConsumerTransferAccounts
  extends BaseTask
{
  private String aQi = "BankingAccounts";
  private String aQh = "TransferToAccounts";
  private String aQf = "TransferFromAccounts";
  private boolean aQg = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    String str = this.successURL;
    Accounts localAccounts1 = (Accounts)localHttpSession.getAttribute(this.aQi);
    if (localAccounts1 == null)
    {
      this.error = 1001;
      return this.taskErrorURL;
    }
    if (this.aQh.equals(""))
    {
      this.error = 1045;
      return this.taskErrorURL;
    }
    if (this.aQf.equals(""))
    {
      this.error = 1046;
      return this.taskErrorURL;
    }
    Accounts localAccounts2 = new Accounts(localLocale);
    Accounts localAccounts3 = new Accounts(localLocale);
    try
    {
      for (int i = 0; i < localAccounts1.size(); i++)
      {
        Account localAccount = (Account)localAccounts1.get(i);
        BlockedAccount localBlockedAccount = new BlockedAccount();
        localBlockedAccount.setAccountNumber(localAccount.getNumber());
        localBlockedAccount.setRoutingNumber(localAccount.getRoutingNum());
        localBlockedAccount.setBankName(localAccount.getBankName());
        localBlockedAccount.setUserDirectoryID(localAccount.getDirectoryID());
        if (!BlockedAccts.isBlockedAccount(localSecureUser, localBlockedAccount, new HashMap()))
        {
          if (((localAccount.isFilterable("TransferTo")) || (localAccount.isFilterable("ExternalTransferTo"))) && (checkAccountForBanking(localAccount, localLocale, localSecureUser, false))) {
            localAccounts2.add(localAccount);
          }
          if (((localAccount.isFilterable("TransferFrom")) || (localAccount.isFilterable("ExternalTransferFrom"))) && (checkAccountForBanking(localAccount, localLocale, localSecureUser, true))) {
            localAccounts3.add(localAccount);
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this.aQh, localAccounts2);
    localHttpSession.setAttribute(this.aQf, localAccounts3);
    return str;
  }
  
  public void setDestinationToAccounts(String paramString)
  {
    this.aQh = paramString;
  }
  
  public void setDestinationFromAccounts(String paramString)
  {
    this.aQf = paramString;
  }
  
  public void setSourceAccounts(String paramString)
  {
    this.aQi = paramString;
  }
  
  public void setCheckEntitlements(String paramString)
  {
    this.aQg = Boolean.getBoolean(paramString);
  }
  
  protected boolean checkAccountForBanking(Account paramAccount, Locale paramLocale, SecureUser paramSecureUser, boolean paramBoolean)
    throws CSILException
  {
    if ((paramAccount.getTypeValue() == 0) && ("1".equals(paramAccount.getCoreAccount()))) {
      return false;
    }
    if (!this.aQg) {
      return true;
    }
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    int i = paramSecureUser.getBusinessID();
    String[] arrayOfString = new String[3];
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    arrayOfString[0] = "Access";
    if (paramAccount.getCoreAccount() != null)
    {
      if (paramAccount.getCoreAccount().equals("1"))
      {
        if (paramBoolean) {
          arrayOfString[1] = "Transfers From";
        } else {
          arrayOfString[1] = "Transfers To";
        }
      }
      else if (paramAccount.getCoreAccount().equals("2"))
      {
        ExtTransferAccount localExtTransferAccount = (ExtTransferAccount)paramAccount.get("ExternalTransferACCOUNT");
        int j = 0;
        if (localExtTransferAccount != null) {
          j = localExtTransferAccount.getVerifyStatusValue();
        }
        if (paramBoolean)
        {
          if (j == 5) {
            return false;
          }
          arrayOfString[1] = "External Transfers From";
        }
        else
        {
          arrayOfString[1] = "External Transfers To";
          if (j == 5) {
            arrayOfString[2] = "External Transfers To Unverified Account";
          }
        }
      }
      else
      {
        return false;
      }
    }
    else {
      return false;
    }
    localMultiEntitlement.setOperations(arrayOfString);
    localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(paramAccount) });
    return EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, i) == null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetConsumerTransferAccounts
 * JD-Core Version:    0.7.0.1
 */