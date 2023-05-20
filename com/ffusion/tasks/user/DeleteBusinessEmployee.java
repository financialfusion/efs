package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.Alerts;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteBusinessEmployee
  extends BaseTask
  implements UserTask
{
  private String xr = "UserService";
  protected String businessEmployeesSessionName = "BusinessEmployees";
  private String xq;
  private int xo;
  private int xp;
  protected boolean processFlag = false;
  protected String businessEmployeeAccountStatus;
  private String xn;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.taskErrorURL;
    this.error = 0;
    if (this.processFlag == true)
    {
      BusinessEmployee localBusinessEmployee = new BusinessEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localBusinessEmployee.setId(this.xq);
      localBusinessEmployee.setBusinessId(this.xo);
      localBusinessEmployee.setEntitlementGroupId(this.xp);
      if ((this.businessEmployeeAccountStatus == null) || (this.businessEmployeeAccountStatus.length() == 0)) {
        localBusinessEmployee.setAccountStatus("32");
      } else {
        localBusinessEmployee.setAccountStatus(this.businessEmployeeAccountStatus);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      Alerts localAlerts = null;
      localAlerts = (Alerts)localHttpSession.getAttribute("com.ffusion.services.Alerts");
      HashMap localHashMap = new HashMap();
      localHashMap.put("SERVICE", localAlerts);
      try
      {
        UserAdmin.deleteBusinessEmployee(localSecureUser, localBusinessEmployee, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      Object localObject;
      if ((this.error == 0) || (this.error == 3009))
      {
        this.processFlag = false;
        str = this.successURL;
        localObject = (BusinessEmployees)localHttpSession.getAttribute(this.businessEmployeesSessionName);
        if (localObject != null)
        {
          ((BusinessEmployees)localObject).removeByID(localBusinessEmployee.getId());
          localHttpSession.setAttribute(this.businessEmployeesSessionName, localObject);
        }
      }
      if (this.error == 0)
      {
        localObject = (BusinessEmployee)localHttpSession.getAttribute(this.xn);
        if (localObject != null)
        {
          HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 1, ((BusinessEmployee)localObject).getId());
          ((BusinessEmployee)localObject).logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
          try
          {
            HistoryAdapter.addHistory(localHistoryTracker.getHistories());
          }
          catch (ProfileException localProfileException)
          {
            DebugLog.log(Level.SEVERE, "Add History failed for DeleteBusinessEmployee: " + localProfileException.toString());
          }
        }
      }
    }
    return str;
  }
  
  public void setId(String paramString)
  {
    this.xq = paramString;
  }
  
  public String getId()
  {
    return this.xq;
  }
  
  public void setBusinessId(String paramString)
  {
    try
    {
      this.xo = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getBusinessId()
  {
    return Integer.toString(this.xo);
  }
  
  public void setGroupId(String paramString)
  {
    try
    {
      this.xp = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getGroupId()
  {
    return Integer.toString(this.xp);
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setBusinessEmployeesSessionName(String paramString)
  {
    this.businessEmployeesSessionName = paramString;
  }
  
  public void setBusinessEmployeeAccountStatus(String paramString)
  {
    this.businessEmployeeAccountStatus = paramString;
  }
  
  public String getBusinessEmployeeAccountStatus()
  {
    return this.businessEmployeeAccountStatus;
  }
  
  public void setUserServiceName(String paramString)
  {
    this.xr = paramString;
  }
  
  public void setHistoryEmployeeName(String paramString)
  {
    this.xn = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.DeleteBusinessEmployee
 * JD-Core Version:    0.7.0.1
 */