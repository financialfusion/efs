package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.services.AccountService;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccounts
  extends BaseTask
  implements Task
{
  protected boolean reload = false;
  protected boolean checkEntitlements = false;
  protected String accountsName = "Accounts";
  protected String serviceName = "com.ffusion.service.AccountService";
  private Boolean mI = Boolean.FALSE;
  private boolean mH = false;
  private String mG = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Object localObject1 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.accountsName);
    if (this.reload)
    {
      localObject1 = null;
      localHttpSession.removeAttribute(this.accountsName);
      this.reload = false;
      this.mH = false;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = getLocale(localHttpSession, localSecureUser);
    if (localObject1 == null) {
      localObject1 = new com.ffusion.beans.accounts.Accounts(localLocale);
    }
    ((com.ffusion.beans.accounts.Accounts)localObject1).setSecureUser(localSecureUser);
    AccountService localAccountService = (AccountService)localHttpSession.getAttribute(this.serviceName);
    this.error = 0;
    int i = 0;
    synchronized (this)
    {
      if ((!this.mI.booleanValue()) && (!this.mH))
      {
        this.mI = Boolean.TRUE;
        i = 1;
      }
    }
    if (i != 0)
    {
      try
      {
        ??? = null;
        if (localAccountService != null)
        {
          ??? = new HashMap();
          ((HashMap)???).put("SERVICE", localAccountService);
          ((HashMap)???).put("ACCOUNTS", localObject1);
        }
        this.error = 0;
        localObject1 = com.ffusion.csil.core.Accounts.getAccounts(localSecureUser, (HashMap)???);
        ((com.ffusion.beans.accounts.Accounts)localObject1).setSecureUser(localSecureUser);
        com.ffusion.beans.accounts.Accounts localAccounts = new com.ffusion.beans.accounts.Accounts(localLocale);
        localAccounts.setSecureUser(localSecureUser);
        EntitlementGroupMember localEntitlementGroupMember;
        if (this.checkEntitlements)
        {
          localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
          jdMethod_for((com.ffusion.beans.accounts.Accounts)localObject1, localAccounts, localEntitlementGroupMember, localSecureUser.getBusinessID());
          localObject1 = localAccounts;
        }
        else if ((localSecureUser.getProfileID() != localSecureUser.getPrimaryUserID()) && (localSecureUser.getBusinessID() == 0))
        {
          localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
          localEntitlementGroupMember.setId(Integer.toString(localSecureUser.getPrimaryUserID()));
          jdMethod_for((com.ffusion.beans.accounts.Accounts)localObject1, localAccounts, localEntitlementGroupMember, localSecureUser.getBusinessID());
          localObject1 = localAccounts;
        }
        ((com.ffusion.beans.accounts.Accounts)localObject1).setLocale(localLocale);
        localHttpSession.setAttribute(this.accountsName, localObject1);
        this.mG = this.successURL;
        this.mH = true;
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
        this.mG = this.serviceErrorURL;
      }
      finally
      {
        this.mI = Boolean.FALSE;
      }
    }
    else
    {
      long l1 = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
      long l2 = System.currentTimeMillis();
      while ((!this.mH) && (this.mI.booleanValue() == true))
      {
        if (System.currentTimeMillis() - l2 > l1)
        {
          if (this.error != 0) {
            break;
          }
          this.error = 1;
          break;
        }
        try
        {
          Thread.sleep(2000L);
        }
        catch (Exception localException) {}
      }
      str = this.mG;
    }
    return str;
  }
  
  public String getAccountsName()
  {
    return this.accountsName;
  }
  
  public String getServiceName()
  {
    return this.serviceName;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCheckEntitlements(String paramString)
  {
    this.checkEntitlements = Boolean.valueOf(paramString).booleanValue();
  }
  
  private void jdMethod_for(com.ffusion.beans.accounts.Accounts paramAccounts1, com.ffusion.beans.accounts.Accounts paramAccounts2, EntitlementGroupMember paramEntitlementGroupMember, int paramInt)
  {
    if ((paramAccounts1 != null) && (paramEntitlementGroupMember != null) && (paramAccounts2 != null))
    {
      MultiEntitlement localMultiEntitlement = new MultiEntitlement();
      Entitlement localEntitlement = new Entitlement("AccountAggregation", null, null);
      int i = 1;
      String[] arrayOfString1 = new String[0];
      String[] arrayOfString2 = { "Account" };
      String[] arrayOfString3 = new String[1];
      for (int j = 0; j < paramAccounts1.size(); j++)
      {
        i = 1;
        Account localAccount = (Account)paramAccounts1.get(j);
        arrayOfString3[0] = EntitlementsUtil.getEntitlementObjectId(localAccount);
        try
        {
          if (("0".equals(localAccount.getCoreAccount())) && (paramInt == 0) && (!Entitlements.checkEntitlement(paramEntitlementGroupMember, localEntitlement))) {
            i = 0;
          }
          if (i != 0)
          {
            localMultiEntitlement.setOperations(arrayOfString1);
            localMultiEntitlement.setObjects(arrayOfString2, arrayOfString3);
            if (EntitlementsUtil.checkAccountEntitlement(paramEntitlementGroupMember, localMultiEntitlement, paramInt) != null) {
              i = 0;
            }
          }
        }
        catch (Exception localException)
        {
          i = 0;
        }
        if (i != 0) {
          paramAccounts2.add(localAccount);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.GetAccounts
 * JD-Core Version:    0.7.0.1
 */