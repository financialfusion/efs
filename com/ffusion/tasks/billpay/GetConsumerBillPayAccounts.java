package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetConsumerBillPayAccounts
  extends BaseTask
{
  private String RC = "BankingAccounts";
  private String RB = "BillPayAccounts";
  private boolean RA = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    String str = this.successURL;
    Accounts localAccounts1 = (Accounts)localHttpSession.getAttribute(this.RC);
    if (localAccounts1 == null)
    {
      this.error = 2005;
      return this.taskErrorURL;
    }
    if (this.RB == "")
    {
      this.error = 2037;
      return this.taskErrorURL;
    }
    Accounts localAccounts2 = new Accounts(localLocale);
    try
    {
      for (int i = 0; i < localAccounts1.size(); i++)
      {
        Account localAccount = (Account)localAccounts1.get(i);
        if (checkAccountForBillPay(localAccount, localLocale, localSecureUser))
        {
          if (i == 0) {
            localHttpSession.setAttribute("BillPayAccount", localAccount);
          }
          localAccounts2.add(localAccount);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this.RB, localAccounts2);
    return str;
  }
  
  public void setDestinationAccounts(String paramString)
  {
    this.RB = paramString;
  }
  
  public void setSourceAccounts(String paramString)
  {
    this.RC = paramString;
  }
  
  public void setCheckEntitlements(String paramString)
  {
    this.RA = Boolean.getBoolean(paramString);
  }
  
  protected boolean checkAccountForBillPay(Account paramAccount, Locale paramLocale, SecureUser paramSecureUser)
    throws CSILException
  {
    if (!paramAccount.isFilterable("BillPay")) {
      return false;
    }
    if ((paramAccount.getTypeValue() == 0) && ("1".equals(paramAccount.getCoreAccount()))) {
      return false;
    }
    if (!this.RA) {
      return true;
    }
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    int i = paramSecureUser.getBusinessID();
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { "Access", "Payments" });
    localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(paramAccount) });
    return EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, i) == null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetConsumerBillPayAccounts
 * JD-Core Version:    0.7.0.1
 */