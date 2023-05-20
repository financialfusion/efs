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

public class GetBankEmployeesForSupervisor
  extends BaseTask
  implements BankEmployeeTask
{
  private String tL = "BankEmployees";
  protected String id;
  protected String affBankId;
  protected boolean _entBypass = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BankEmployees localBankEmployees = new BankEmployees();
    if ((this.id != null) && (this.id.length() != 0))
    {
      try
      {
        HashMap localHashMap = new HashMap();
        if (this._entBypass) {
          localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
        }
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        BankEmployee localBankEmployee = new BankEmployee(Locale.getDefault());
        localBankEmployee.setSupervisor(this.id);
        if ((this.affBankId != null) && (this.affBankId.length() > 0))
        {
          int i = 0;
          try
          {
            i = Integer.parseInt(this.affBankId);
            if (i > 0) {
              localBankEmployee.addAffiliateBankId(i);
            }
          }
          catch (NumberFormatException localNumberFormatException)
          {
            this.error = 5532;
            str = this.taskErrorURL;
          }
        }
        if (this.error == 0) {
          localBankEmployees = BankEmployeeAdmin.getBankEmployeeList(localSecureUser, localBankEmployee, localHashMap);
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
      if (this.error == 0) {
        localHttpSession.setAttribute(this.tL, localBankEmployees);
      }
    }
    else
    {
      this.error = 5501;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setSupervisorId(String paramString)
  {
    this.id = paramString;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.affBankId = paramString;
  }
  
  public void setBankEmployeeSessionName(String paramString)
  {
    this.tL = paramString;
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
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetBankEmployeesForSupervisor
 * JD-Core Version:    0.7.0.1
 */