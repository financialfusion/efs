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
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetSupervisorsForSupervisor
  extends BaseTask
  implements BankEmployeeTask
{
  private String un = "Supervisors";
  protected String id;
  protected String affBankId;
  private boolean um = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BankEmployees localBankEmployees1 = new BankEmployees();
    BankEmployees localBankEmployees2 = new BankEmployees();
    if ((this.id != null) && (this.id.length() != 0))
    {
      try
      {
        int i = 0;
        HashMap localHashMap = new HashMap();
        if (this.um) {
          localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
        }
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        BankEmployee localBankEmployee1 = new BankEmployee(Locale.getDefault());
        localBankEmployee1.setSupervisor(this.id);
        if ((this.affBankId != null) && (this.affBankId.length() > 0)) {
          try
          {
            i = Integer.parseInt(this.affBankId);
            localBankEmployee1.addAffiliateBankId(i);
          }
          catch (NumberFormatException localNumberFormatException)
          {
            this.error = 5532;
            str = this.taskErrorURL;
          }
        }
        localBankEmployees1 = BankEmployeeAdmin.getBankEmployeeList(localSecureUser, localBankEmployee1, localHashMap);
        Iterator localIterator = localBankEmployees1.iterator();
        while (localIterator.hasNext())
        {
          BankEmployee localBankEmployee2 = (BankEmployee)localIterator.next();
          Integer localInteger = new Integer(localBankEmployee2.getId());
          if (i > 0)
          {
            if (BankEmployeeAdmin.hasDirectReports(localSecureUser, localInteger.intValue(), i, localHashMap)) {
              localBankEmployees2.add(localBankEmployee2);
            }
          }
          else if (BankEmployeeAdmin.hasDirectReports(localSecureUser, localInteger.intValue(), localHashMap)) {
            localBankEmployees2.add(localBankEmployee2);
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
        this.um = false;
      }
      if (this.error == 0) {
        localHttpSession.setAttribute(this.un, localBankEmployees2);
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
    this.un = paramString;
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this.um = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this.um);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetSupervisorsForSupervisor
 * JD-Core Version:    0.7.0.1
 */