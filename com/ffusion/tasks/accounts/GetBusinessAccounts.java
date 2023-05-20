package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBusinessAccounts
  extends BaseTask
  implements Task
{
  private String ng = "com.ffusion.services.Banking";
  private boolean nf = false;
  private boolean nh = false;
  private boolean nd = false;
  private String ne = "Accounts";
  private String nc = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Object localObject1 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.ne);
    Business localBusiness = (Business)localHttpSession.getAttribute("Business");
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = getLocale(localHttpSession, localSecureUser);
    if (localBusiness == null)
    {
      this.error = 4104;
      str = this.taskErrorURL;
    }
    else
    {
      HashMap localHashMap = new HashMap();
      try
      {
        Object localObject2;
        if (this.nf)
        {
          localObject2 = localHttpSession.getAttribute(this.ng);
          localObject1 = com.ffusion.csil.core.Accounts.syncBusinessAccounts(localSecureUser, localBusiness, localObject2, localHashMap);
          this.nf = false;
        }
        else
        {
          localObject1 = com.ffusion.csil.core.Accounts.getAccountsById(localSecureUser, localBusiness.getIdValue(), localHashMap);
        }
        if (this.nh)
        {
          localObject2 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
          MultiEntitlement localMultiEntitlement = new MultiEntitlement();
          localMultiEntitlement.setOperations(new String[] { null });
          String[] arrayOfString1 = { "Account" };
          String[] arrayOfString2 = new String[1];
          com.ffusion.beans.accounts.Accounts localAccounts = new com.ffusion.beans.accounts.Accounts();
          for (int i = 0; i < ((com.ffusion.beans.accounts.Accounts)localObject1).size(); i++)
          {
            arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId((Account)((com.ffusion.beans.accounts.Accounts)localObject1).get(i));
            localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
            try
            {
              if (EntitlementsUtil.checkAccountEntitlement((EntitlementGroupMember)localObject2, localMultiEntitlement, localSecureUser.getBusinessID()) == null) {
                localAccounts.add(((com.ffusion.beans.accounts.Accounts)localObject1).get(i));
              }
            }
            catch (Exception localException) {}
          }
          if (this.nd) {
            localHttpSession.setAttribute("All" + this.ne, localObject1);
          }
          localObject1 = localAccounts;
        }
        ((com.ffusion.beans.accounts.Accounts)localObject1).setSecureUser(localSecureUser);
        ((com.ffusion.beans.accounts.Accounts)localObject1).setLocale(localLocale);
        localHttpSession.setAttribute(this.ne, localObject1);
        this.nc = this.successURL;
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setAccountsName(String paramString)
  {
    this.ne = paramString;
  }
  
  public void setReload(String paramString)
  {
    this.nf = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCheckEntitlements(String paramString)
  {
    this.nh = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setStoreAllAccounts(String paramString)
  {
    this.nd = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setBankingServiceSessionName(String paramString)
  {
    this.ng = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.GetBusinessAccounts
 * JD-Core Version:    0.7.0.1
 */