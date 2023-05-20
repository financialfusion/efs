package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountReps
  extends BaseTask
  implements BankEmployeeTask
{
  protected boolean _entBypass = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BankEmployees localBankEmployees = new BankEmployees((Locale)localHttpSession.getAttribute("java.util.Locale"));
    Entitlement localEntitlement = new Entitlement("Account Manager", null, null);
    try
    {
      HashMap localHashMap = new HashMap();
      if (this._entBypass) {
        localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      EntitlementGroup localEntitlementGroup = null;
      EntitlementGroups localEntitlementGroups = Entitlements.getEntitlementGroupsByType("EmployeeType");
      Iterator localIterator = localEntitlementGroups.iterator();
      while (localIterator.hasNext())
      {
        localEntitlementGroup = (EntitlementGroup)localIterator.next();
        if (Entitlements.checkEntitlement(localEntitlementGroup.getGroupId(), localEntitlement)) {
          localBankEmployees.set(BankEmployeeAdmin.getBankEmployeesByJobTypeId(localSecureUser, localEntitlementGroup.getGroupId(), localHashMap));
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    finally
    {
      this._entBypass = false;
    }
    if ((this.error == 0) || (this.error == 12))
    {
      localHttpSession.setAttribute("AccountReps", localBankEmployees);
      str = this.successURL;
    }
    return str;
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this._entBypass = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this._entBypass);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetAccountReps
 * JD-Core Version:    0.7.0.1
 */