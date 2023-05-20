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

public class GetBankEmployeeList
  extends BaseTask
  implements BankEmployeeTask
{
  protected String listSessionName = "BankEmployees";
  protected String affBankId = null;
  private boolean t3 = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BankEmployees localBankEmployees = new BankEmployees((Locale)localHttpSession.getAttribute("java.util.Locale"));
    try
    {
      HashMap localHashMap = new HashMap();
      if (this.t3) {
        localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      BankEmployee localBankEmployee = null;
      if ((this.affBankId != null) && (this.affBankId.length() > 0))
      {
        localBankEmployee = new BankEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
        int i = 0;
        try
        {
          i = Integer.parseInt(this.affBankId);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          this.error = 5532;
          str = this.taskErrorURL;
        }
        localBankEmployee.addAffiliateBankId(i);
      }
      localBankEmployees = BankEmployeeAdmin.getBankEmployeeList(localSecureUser, localBankEmployee, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    finally
    {
      this.t3 = false;
    }
    if ((this.error == 0) || (this.error == 12))
    {
      localHttpSession.setAttribute(this.listSessionName, localBankEmployees);
      str = this.successURL;
    }
    return str;
  }
  
  public void setListSessionName(String paramString)
  {
    this.listSessionName = paramString;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.affBankId = paramString;
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this.t3 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this.t3);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetBankEmployeeList
 * JD-Core Version:    0.7.0.1
 */