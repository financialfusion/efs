package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetUserAccountsByBusinessID
  extends BaseTask
  implements AdminTask, Task
{
  private static final String adU = "USER";
  private String adR = "GroupAccounts";
  private int adS = -1;
  private String adQ = "";
  private int adV = -1;
  private int adT = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    if (this.adT <= 0)
    {
      this.error = 4523;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    com.ffusion.beans.accounts.Accounts localAccounts = null;
    Object localObject = null;
    try
    {
      localAccounts = com.ffusion.csil.core.Accounts.getAccountsById(localSecureUser, this.adT, null);
      EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setId(this.adQ);
      localEntitlementGroupMember.setMemberType("USER");
      localEntitlementGroupMember.setMemberSubType(Integer.toString(this.adV));
      localEntitlementGroupMember.setEntitlementGroupId(this.adS);
      MultiEntitlement localMultiEntitlement = new MultiEntitlement();
      String[] arrayOfString1 = { "Account" };
      String[] arrayOfString2 = new String[1];
      localMultiEntitlement.setOperations(new String[1]);
      Iterator localIterator = localAccounts.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount = (Account)localIterator.next();
        arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId(localAccount);
        localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
        if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, this.adT) != null) {
          localIterator.remove();
        }
      }
      localHttpSession.setAttribute(this.adR, localAccounts);
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
    this.adS = -1;
    this.adQ = "";
    this.adV = -1;
    return str;
  }
  
  public void setGroupID(String paramString)
  {
    this.adS = Integer.parseInt(paramString);
  }
  
  public void setMemberID(String paramString)
  {
    this.adQ = paramString;
  }
  
  public void setUserType(String paramString)
  {
    this.adV = Integer.parseInt(paramString);
  }
  
  public void setGroupAccountsName(String paramString)
  {
    this.adR = paramString;
  }
  
  public void setDirectoryID(String paramString)
  {
    this.adT = Integer.parseInt(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetUserAccountsByBusinessID
 * JD-Core Version:    0.7.0.1
 */