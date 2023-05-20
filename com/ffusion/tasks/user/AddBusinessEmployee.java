package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddBusinessEmployee
  extends AddUser
{
  public static final String USERPASSWORD = "USERPASSWORD";
  public static final String BUSINESSID = "BUSINESSID";
  public static final String BUSINESSCUSTID = "BUSINESSCUSTID";
  public static final String PRIMARYUSER = "PRIMARYUSER";
  protected int businessId;
  protected String businessCustId;
  protected String primaryUser;
  protected String businessEmployeeSessionName = "BusinessEmployee";
  protected String businessEmployeesSessionName = "BusinessEmployees";
  private SecureUser pc = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.nextURL = this.successURL;
    this.error = 0;
    this.pc = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    if (this.addUserFromSession)
    {
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)localHttpSession.getAttribute("BusinessEmployee");
      if (localBusinessEmployee != null)
      {
        this.businessId = localBusinessEmployee.getBusinessId();
        this.businessCustId = localBusinessEmployee.getBusinessCustId();
        this.primaryUser = localBusinessEmployee.getPrimaryUser();
        this.entitlementGroupId = localBusinessEmployee.getEntitlementGroupId();
        set(localBusinessEmployee);
      }
    }
    if (validateInput(localHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        if (addBusinessEmployee(localHttpSession))
        {
          if ((this.additionalSettingsFile != null) && (this.additionalSettingsFile.trim().length() > 0) && (addAdditionalSettings() != 0)) {
            this.nextURL = this.serviceErrorURL;
          }
        }
        else {
          this.nextURL = this.serviceErrorURL;
        }
      }
      else
      {
        this.nextURL = this.validInputURL;
      }
    }
    else {
      return this.nextURL;
    }
    return this.nextURL;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null)
    {
      if (this.validate.indexOf("BUSINESSID") != -1) {
        if (!j(paramHttpSession))
        {
          this.nextURL = this.taskErrorURL;
          bool = setError(4108);
        }
        else if (this.nextURL == this.serviceErrorURL)
        {
          bool = false;
        }
      }
      if ((this.validate.indexOf("BUSINESSCUSTID") != -1) && ((this.businessCustId == null) || (this.businessCustId.length() == 0)))
      {
        this.nextURL = this.taskErrorURL;
        bool = setError(4139);
      }
      if ((this.validate.indexOf("PRIMARYUSER") != -1) && ((this.primaryUser == null) || (this.primaryUser.length() == 0)))
      {
        this.nextURL = this.taskErrorURL;
        bool = setError(65);
      }
      if ((bool) && (this.validate.indexOf("USERPASSWORD") != -1) && (verifyPassword(paramHttpSession) != true))
      {
        this.nextURL = this.taskErrorURL;
        return false;
      }
      try
      {
        bool = super.validateInput(paramHttpSession);
        if (!bool) {
          this.nextURL = this.taskErrorURL;
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        this.nextURL = this.serviceErrorURL;
      }
    }
    this.validate = null;
    return bool;
  }
  
  protected boolean addBusinessEmployee(HttpSession paramHttpSession)
  {
    this.error = 0;
    boolean bool = false;
    BusinessEmployee localBusinessEmployee = null;
    try
    {
      localBusinessEmployee = new BusinessEmployee((Locale)paramHttpSession.getAttribute("java.util.Locale"));
      localBusinessEmployee.setBusinessId(this.businessId);
      localBusinessEmployee.setBusinessCustId(this.businessCustId);
      localBusinessEmployee.setPrimaryUser(this.primaryUser);
      localBusinessEmployee.setEntitlementGroupId(this.entitlementGroupId);
      localBusinessEmployee.set(this);
      if ((localBusinessEmployee.getAccountStatus() == null) || (localBusinessEmployee.getAccountStatus().length() == 0)) {
        localBusinessEmployee.setAccountStatus("0");
      }
      localBusinessEmployee.put("PASSWORD_STATUS", Integer.toString(2));
      UserAdmin.addBusinessEmployee(this.pc, localBusinessEmployee, null);
      setId(localBusinessEmployee.getId());
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.nextURL = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      paramHttpSession.setAttribute(this.businessEmployeeSessionName, localBusinessEmployee);
      bool = true;
      addUserHistory(paramHttpSession, "Add", localBusinessEmployee, null);
      BusinessEmployees localBusinessEmployees = (BusinessEmployees)paramHttpSession.getAttribute(this.businessEmployeesSessionName);
      if (localBusinessEmployees != null) {
        localBusinessEmployees.add(localBusinessEmployee);
      }
    }
    return bool;
  }
  
  protected boolean duplicateUserName(HttpSession paramHttpSession)
  {
    User localUser1 = (User)paramHttpSession.getAttribute(this.oldUserSessionName);
    int i = 1;
    boolean bool = false;
    if ((localUser1 != null) && (localUser1.getUserName().equals(getUserName()))) {
      i = 0;
    }
    if (i != 0)
    {
      HashMap localHashMap = new HashMap();
      User localUser2 = new User((Locale)paramHttpSession.getAttribute("java.util.Locale"));
      localUser2.setUserName(this.userName);
      String str = (String)get("BANK_ID");
      localUser2.set("BANK_ID", str);
      localHashMap.put("USER", localUser2);
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      try
      {
        localHashMap.put("BUSINESS_CUST_ID", this.businessCustId);
        bool = UserAdmin.userExists(localSecureUser, this.userName, str, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        this.nextURL = this.serviceErrorURL;
      }
    }
    return bool;
  }
  
  public void setBusinessId(String paramString)
  {
    try
    {
      this.businessId = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setBusinessCustId(String paramString)
  {
    this.businessCustId = paramString;
  }
  
  public void setPrimaryUser(String paramString)
  {
    this.primaryUser = paramString;
  }
  
  private boolean j(HttpSession paramHttpSession)
  {
    boolean bool = false;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      bool = UserAdmin.businessIdExists(localSecureUser, this.businessId, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.nextURL = this.serviceErrorURL;
    }
    return bool;
  }
  
  public void setBusinessEmployeeSessionName(String paramString)
  {
    this.businessEmployeeSessionName = paramString;
  }
  
  public void setBusinessEmployeesSessionName(String paramString)
  {
    this.businessEmployeesSessionName = paramString;
  }
  
  public void setUserServiceName(String paramString) {}
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.AddBusinessEmployee
 * JD-Core Version:    0.7.0.1
 */