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
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCSRs
  extends BaseTask
  implements BankEmployeeTask
{
  private static Entitlement t5 = new Entitlement("Personal Banker", null, null);
  private String t4 = "CSRs";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    BankEmployees localBankEmployees = null;
    localBankEmployees = new BankEmployees((Locale)localHttpSession.getAttribute("java.util.Locale"));
    this.error = 0;
    if (this.error == 0) {
      try
      {
        HashMap localHashMap = null;
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        EntitlementGroups localEntitlementGroups = Entitlements.getEntitlementGroupsByType("EmployeeType");
        EntitlementGroup localEntitlementGroup = null;
        Iterator localIterator = localEntitlementGroups.iterator();
        while (localIterator.hasNext())
        {
          localEntitlementGroup = (EntitlementGroup)localIterator.next();
          if (Entitlements.checkEntitlement(localEntitlementGroup.getGroupId(), t5)) {
            localBankEmployees.set(BankEmployeeAdmin.getBankEmployeesByJobTypeId(localSecureUser, localEntitlementGroup.getGroupId(), localHashMap));
          }
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    if ((this.error == 0) || (this.error == 12))
    {
      localHttpSession.setAttribute(this.t4, localBankEmployees);
      str = this.successURL;
    }
    return str;
  }
  
  public String getSessionName()
  {
    return this.t4;
  }
  
  public void setSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.t4 = "CSRs";
    } else {
      this.t4 = paramString;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetCSRs
 * JD-Core Version:    0.7.0.1
 */