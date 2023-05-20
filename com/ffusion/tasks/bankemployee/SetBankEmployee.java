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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetBankEmployee
  extends BaseTask
  implements BankEmployeeTask
{
  private String uj = "BankEmployees";
  private String ui = "BankEmployee";
  private String uh = null;
  protected boolean _entBypass = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    BankEmployees localBankEmployees = (BankEmployees)localHttpSession.getAttribute(this.uj);
    if (localBankEmployees == null)
    {
      this.error = 5503;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 1037;
      return this.taskErrorURL;
    }
    BankEmployee localBankEmployee = localBankEmployees.getByID(this.uh);
    if (localBankEmployee == null) {
      try
      {
        HashMap localHashMap = new HashMap();
        if (this._entBypass) {
          localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
        }
        localObject1 = new BankEmployee(localSecureUser.getLocale());
        ((BankEmployee)localObject1).setId(this.uh);
        localBankEmployee = BankEmployeeAdmin.getBankEmployeeById(localSecureUser, (BankEmployee)localObject1, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        Object localObject1 = this.serviceErrorURL;
        return localObject1;
      }
      finally
      {
        this._entBypass = false;
      }
    }
    if (localBankEmployee != null) {
      localHttpSession.setAttribute(this.ui, localBankEmployee);
    }
    return this.successURL;
  }
  
  public void setEmployeesCollectionKeyName(String paramString)
  {
    this.uj = paramString;
  }
  
  public void setEmployeeKeyName(String paramString)
  {
    this.ui = paramString;
  }
  
  public void setEmployeeId(String paramString)
  {
    this.uh = paramString;
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
 * Qualified Name:     com.ffusion.tasks.bankemployee.SetBankEmployee
 * JD-Core Version:    0.7.0.1
 */