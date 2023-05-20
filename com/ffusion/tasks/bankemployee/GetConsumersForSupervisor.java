package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.UserAdmin;
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

public class GetConsumersForSupervisor
  extends BaseTask
  implements BankEmployeeTask
{
  private String tt = "Consumers";
  protected String id;
  private boolean tu = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BankEmployees localBankEmployees = new BankEmployees();
    Users localUsers = new Users(Locale.getDefault());
    if ((this.id != null) && (this.id.length() != 0))
    {
      try
      {
        HashMap localHashMap = new HashMap();
        if (this.tu) {
          localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
        }
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        BankEmployee localBankEmployee = new BankEmployee(Locale.getDefault());
        localBankEmployee.setSupervisor(this.id);
        localBankEmployees = BankEmployeeAdmin.getBankEmployeeList(localSecureUser, localBankEmployee, localHashMap);
        User localUser = new User(Locale.getDefault());
        localUser.setBankId(localSecureUser.getBankID());
        for (int i = 0; i < localBankEmployees.size(); i++)
        {
          localUser.setPersonalBanker(((BankEmployee)localBankEmployees.get(i)).getId());
          localUsers.addAll(UserAdmin.getConsumers(localSecureUser, localUser, localHashMap));
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      finally
      {
        this.tu = false;
      }
      if (this.error == 0) {
        localHttpSession.setAttribute(this.tt, localUsers);
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
  
  public void SetConsumersSessionName(String paramString)
  {
    this.tt = paramString;
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this.tu = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this.tu);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetConsumersForSupervisor
 * JD-Core Version:    0.7.0.1
 */