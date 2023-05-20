package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAssignedEmployees
  extends BaseTask
  implements BankEmployeeTask
{
  private String uk;
  protected boolean _entBypass = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    BankEmployee localBankEmployee = new BankEmployee(localLocale);
    localBankEmployee.setSupervisor(this.uk);
    BankEmployees localBankEmployees = new BankEmployees(localLocale);
    try
    {
      HashMap localHashMap = new HashMap();
      if (this._entBypass) {
        localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localBankEmployees = BankEmployeeAdmin.getBankEmployeeList(localSecureUser, localBankEmployee, localHashMap);
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
      str = this.successURL;
      localHttpSession.setAttribute("AssignedEmployees", localBankEmployees);
    }
    return str;
  }
  
  public void setEmployeeId(String paramString)
  {
    this.uk = paramString;
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
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetAssignedEmployees
 * JD-Core Version:    0.7.0.1
 */