package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountsForGroup
  extends BaseTask
  implements AdminTask, Task
{
  private String acM = "GroupAccounts";
  private int acO = -1;
  private boolean acN = true;
  private boolean acP = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = super.getSuccessURL();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    com.ffusion.beans.accounts.Accounts localAccounts = null;
    try
    {
      localAccounts = com.ffusion.csil.core.Accounts.getAccountsByBusinessEmployee(localSecureUser, null);
      MultiEntitlement localMultiEntitlement = new MultiEntitlement();
      String[] arrayOfString1 = { "Account" };
      String[] arrayOfString2 = new String[1];
      localMultiEntitlement.setOperations(new String[0]);
      Iterator localIterator = localAccounts.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount = (Account)localIterator.next();
        arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId(localAccount);
        localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
        if (this.acN)
        {
          Entitlement localEntitlement1 = EntitlementsUtil.checkAccountEntitlement(this.acO, localMultiEntitlement, localSecureUser.getBusinessID());
          if ((localEntitlement1 != null) && (this.acP))
          {
            localMultiEntitlement.setOperations(new String[] { "Access (admin)" });
            localEntitlement1 = EntitlementsUtil.checkAccountAndAccountGroupEntitlement(this.acO, localMultiEntitlement, localSecureUser.getBusinessID());
            localMultiEntitlement.setOperations(null);
          }
          if (localEntitlement1 != null) {
            localIterator.remove();
          }
        }
        else
        {
          boolean bool = false;
          Entitlement localEntitlement2 = new Entitlement("Access", "Account", arrayOfString2[0]);
          if (!Entitlements.checkEntitlement(this.acO, localEntitlement2))
          {
            if (this.acP)
            {
              localEntitlement2.setOperationName("Access (admin)");
              bool = Entitlements.checkEntitlement(this.acO, localEntitlement2);
            }
          }
          else {
            bool = true;
          }
          if (!bool) {
            localIterator.remove();
          }
        }
      }
      localHttpSession.setAttribute(this.acM, localAccounts);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 4512;
      str = this.taskErrorURL;
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    finally
    {
      this.acO = -1;
    }
    return str;
  }
  
  public void setGroupID(String paramString)
  {
    this.acO = Integer.parseInt(paramString);
  }
  
  public void setGroupAccountsName(String paramString)
  {
    this.acM = paramString;
  }
  
  public void setCheckAdminAccess(String paramString)
  {
    this.acP = new Boolean(paramString).booleanValue();
  }
  
  public void setCheckAccountGroupAccess(String paramString)
  {
    this.acN = new Boolean(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetAccountsForGroup
 * JD-Core Version:    0.7.0.1
 */