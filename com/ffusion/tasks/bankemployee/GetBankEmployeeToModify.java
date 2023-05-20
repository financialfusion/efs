package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
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

public class GetBankEmployeeToModify
  extends BaseTask
  implements BankEmployeeTask
{
  protected String id;
  private boolean uq = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.id != null) && (this.id.length() != 0))
    {
      BankEmployee localBankEmployee = new BankEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localBankEmployee.setId(this.id);
      try
      {
        HashMap localHashMap = new HashMap(1);
        if (this.uq) {
          localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
        }
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        localBankEmployee = BankEmployeeAdmin.getBankEmployeeById(localSecureUser, localBankEmployee, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      finally
      {
        this.uq = false;
      }
      if (this.error == 0) {
        localHttpSession.setAttribute("ModifiedBankEmployee", localBankEmployee);
      }
    }
    else
    {
      this.error = 5501;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this.uq = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this.uq);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetBankEmployeeToModify
 * JD-Core Version:    0.7.0.1
 */