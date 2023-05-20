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

public class GetSubordinateBankEmployeesForSupervisor
  extends BaseTask
  implements BankEmployeeTask
{
  private String tG = "BankEmployees";
  private String tH;
  private String tI;
  private boolean tF = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BankEmployees localBankEmployees = new BankEmployees();
    this.error = 0;
    String str = this.successURL;
    try
    {
      HashMap localHashMap = new HashMap();
      if (this.tF) {
        localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      BankEmployee localBankEmployee = new BankEmployee(localSecureUser.getLocale());
      if ((this.error == 0) && (this.tH != null) && (this.tH.length() > 0))
      {
        localBankEmployee.setId(this.tH);
      }
      else
      {
        this.error = 5501;
        str = this.taskErrorURL;
      }
      if ((this.error == 0) && (this.tI != null) && (this.tI.length() > 0)) {
        try
        {
          int i = Integer.parseInt(this.tI);
          localBankEmployee.addAffiliateBankId(i);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          this.error = 5532;
          str = this.taskErrorURL;
        }
      }
      if (this.error == 0)
      {
        localBankEmployees = BankEmployeeAdmin.getSubordinateBankEmployees(localSecureUser, localBankEmployee, localHashMap);
        localHttpSession.setAttribute(this.tG, localBankEmployees);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    finally
    {
      this.tF = false;
    }
    return str;
  }
  
  public String getSupervisorId()
  {
    return this.tH;
  }
  
  public void setSupervisorId(String paramString)
  {
    this.tH = paramString;
  }
  
  public String getBankEmployeesSessionName()
  {
    return this.tG;
  }
  
  public void setBankEmployeesSessionName(String paramString)
  {
    this.tG = paramString;
  }
  
  public String getAffiliateBankId()
  {
    return this.tI;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.tI = paramString;
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this.tF = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this.tF);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetSubordinateBankEmployeesForSupervisor
 * JD-Core Version:    0.7.0.1
 */