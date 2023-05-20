package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.services.Alerts;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditBusinessEmployee
  extends AddBusinessEmployee
{
  protected boolean initFlag = false;
  private static final String pe = "com.ffusion.util.logging.audit.task";
  private static final String pd = "AuditMessage_EditBusinessEmployee_1";
  private static final String pf = "AuditMessage_EditBusinessEmployee_2";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.nextURL = this.successURL;
    this.error = 0;
    this.oldUserSessionName = ("OldBusinessEmployee" + this.businessEmployeeSessionName);
    BusinessEmployee localBusinessEmployee1;
    BusinessEmployee localBusinessEmployee2;
    if (this.initFlag == true)
    {
      localBusinessEmployee1 = (BusinessEmployee)localHttpSession.getAttribute(this.businessEmployeeSessionName);
      if (localBusinessEmployee1 != null)
      {
        this.nextURL = this.validInputURL;
        this.businessId = localBusinessEmployee1.getBusinessId();
        this.primaryUser = localBusinessEmployee1.getPrimaryUser();
        this.entitlementGroupId = localBusinessEmployee1.getEntitlementGroupId();
        setConfirmPassword(localBusinessEmployee1.getPassword());
        set(localBusinessEmployee1);
        localBusinessEmployee2 = new BusinessEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
        localBusinessEmployee2.setBusinessId(localBusinessEmployee1.getBusinessId());
        localBusinessEmployee2.setPrimaryUser(localBusinessEmployee1.getPrimaryUser());
        localBusinessEmployee2.setEntitlementGroupId(localBusinessEmployee1.getEntitlementGroupId());
        localBusinessEmployee2.set(localBusinessEmployee1);
        localHttpSession.setAttribute(this.oldUserSessionName, localBusinessEmployee2);
        this.initFlag = false;
      }
      else
      {
        this.error = 3520;
        this.nextURL = this.taskErrorURL;
      }
    }
    else if (validateInput(localHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        localBusinessEmployee1 = (BusinessEmployee)localHttpSession.getAttribute(this.businessEmployeeSessionName);
        if (localBusinessEmployee1 != null)
        {
          localBusinessEmployee2 = (BusinessEmployee)localHttpSession.getAttribute(this.oldUserSessionName);
          if (localBusinessEmployee2 != null)
          {
            modifyBusinessEmployee(localHttpSession, localBusinessEmployee1, localBusinessEmployee2);
          }
          else
          {
            this.error = 3522;
            this.nextURL = this.taskErrorURL;
          }
        }
        else
        {
          this.error = 3520;
          this.nextURL = this.taskErrorURL;
        }
      }
      else
      {
        this.nextURL = this.validInputURL;
      }
    }
    else
    {
      this.nextURL = this.taskErrorURL;
    }
    return this.nextURL;
  }
  
  protected boolean modifyBusinessEmployee(HttpSession paramHttpSession, BusinessEmployee paramBusinessEmployee1, BusinessEmployee paramBusinessEmployee2)
  {
    boolean bool = true;
    BusinessEmployee localBusinessEmployee = null;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    Object localObject;
    try
    {
      localBusinessEmployee = new BusinessEmployee((Locale)paramHttpSession.getAttribute("java.util.Locale"));
      localBusinessEmployee.setBusinessId(this.businessId);
      localBusinessEmployee.setPrimaryUser(this.primaryUser);
      localBusinessEmployee.setEntitlementGroupId(this.entitlementGroupId);
      localBusinessEmployee.set(this);
      Alerts localAlerts = null;
      localAlerts = (Alerts)paramHttpSession.getAttribute("com.ffusion.services.Alerts");
      if (localAlerts == null) {
        localAlerts = (Alerts)paramHttpSession.getAttribute("com.ffusion.services.Alerts");
      }
      localObject = new HashMap();
      ((HashMap)localObject).put("SERVICE", localAlerts);
      paramBusinessEmployee1 = UserAdmin.modifyBusinessEmployee(localSecureUser, localBusinessEmployee, (HashMap)localObject);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.nextURL = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      paramBusinessEmployee1.setBusinessId(this.businessId);
      paramBusinessEmployee1.setPrimaryUser(this.primaryUser);
      paramBusinessEmployee1.setEntitlementGroupId(this.entitlementGroupId);
      paramBusinessEmployee1.set(this);
      paramHttpSession.setAttribute(this.businessEmployeeSessionName, paramBusinessEmployee1);
      addUserHistory(paramHttpSession, "Modify", paramBusinessEmployee1, paramBusinessEmployee2);
      BusinessEmployees localBusinessEmployees = (BusinessEmployees)paramHttpSession.getAttribute(this.businessEmployeesSessionName);
      if (localBusinessEmployees != null)
      {
        localObject = localBusinessEmployees.getByID(getId());
        if (paramBusinessEmployee2.getAccountStatus().equals(getAccountStatus()))
        {
          if (localObject != null) {
            ((BusinessEmployee)localObject).set(localBusinessEmployee);
          }
        }
        else if (localObject == null) {
          localBusinessEmployees.add(localBusinessEmployee);
        } else {
          localBusinessEmployees.remove(localObject);
        }
        paramHttpSession.setAttribute(this.businessEmployeesSessionName, localBusinessEmployees);
      }
      if (!paramBusinessEmployee2.getPasswordStatusStr().equals(paramBusinessEmployee1.getPasswordStatusStr()))
      {
        localObject = String.valueOf(localSecureUser.getProfileID());
        String str = String.valueOf(localSecureUser.getUserType());
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramBusinessEmployee1.getUserName();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessEmployee_1", arrayOfObject);
        AuditLog.log("", (String)localObject, str, localLocalizableString, null, 3212, this.businessId);
        if (((paramBusinessEmployee1.getPasswordStatusIntValue() == 0) || (paramBusinessEmployee1.getPasswordStatusIntValue() == 2)) && (paramBusinessEmployee2.getPasswordStatusIntValue() != 0) && (paramBusinessEmployee2.getPasswordStatusIntValue() != 2))
        {
          arrayOfObject = new Object[1];
          arrayOfObject[0] = paramBusinessEmployee1.getUserName();
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditBusinessEmployee_2", arrayOfObject);
          AuditLog.log(this.id, (String)localObject, str, localLocalizableString, null, 3201, this.businessId);
        }
      }
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setUserServiceName(String paramString) {}
  
  public void setResetPasswordQA(String paramString)
  {
    if ((paramString != null) && (paramString.equals("true")))
    {
      setPasswordReminder("");
      setPasswordReminder2("");
      setPasswordClue(null);
      setPasswordClue2(null);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.EditBusinessEmployee
 * JD-Core Version:    0.7.0.1
 */