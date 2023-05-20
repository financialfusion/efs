package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.authentication.Credentials;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.SignonSettings;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.SignOn;
import com.ffusion.util.beans.DateTime;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOnBankEmployee
  extends SignOn
  implements BankEmployeeTask
{
  private String oe = "Credentials";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (validateInput(localHttpSession))
    {
      this.error = signOn(paramHttpServletRequest);
      if (this.error == 0) {
        str = this.successURL;
      } else if ((this.error == 5500) || (this.error == 46) || (this.error == 5509)) {
        str = this.taskErrorURL;
      } else if (this.error == 3007) {
        str = this.mustChangePasswordURL;
      } else if (this.error == 3024) {
        str = this.mustChangePasswordURL;
      } else if (this.error == 3017) {
        str = this.inactiveAccountURL;
      } else if (this.error == 3018) {
        str = this.successURL;
      } else {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public int signOn(HttpServletRequest paramHttpServletRequest)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.services.BankEmployeeAdmin localBankEmployeeAdmin = (com.ffusion.services.BankEmployeeAdmin)localHttpSession.getAttribute("BankEmployeeService");
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    BankEmployee localBankEmployee1 = new BankEmployee(localLocale);
    localBankEmployee1.setUserName(this.userName);
    localBankEmployee1.setPassword(this.password);
    SecureUser localSecureUser = new SecureUser();
    localSecureUser.setUserName(this.userName);
    localSecureUser.setPassword(this.password);
    localSecureUser.setUserType(2);
    localSecureUser.setLocale(localLocale);
    try
    {
      HashMap localHashMap = new HashMap();
      if (localBankEmployeeAdmin != null) {
        localHashMap.put("SERVICE", localBankEmployeeAdmin);
      }
      Credentials localCredentials = (Credentials)localHttpSession.getAttribute(this.oe);
      if (localCredentials != null)
      {
        localHashMap.put("Credentials", localCredentials);
        localHttpSession.removeAttribute(this.oe);
      }
      localBankEmployee1 = com.ffusion.csil.core.BankEmployeeAdmin.signonBankEmployee(localSecureUser, localBankEmployee1, localHashMap);
      localHashMap.remove("Credentials");
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
    }
    if ((this.error == 0) && (localBankEmployee1.getStatusInt() == 0))
    {
      localObject = (DateTime)localSecureUser.get("LAST_ACTIVE_DATE");
      long l = -1L;
      if (localObject != null) {
        l = System.currentTimeMillis() - ((DateTime)localObject).getTime().getTime();
      }
      Integer localInteger = new Integer(localSecureUser.getEntitlementID());
      if ((!SignonSettings.getExemptJobTypeIds().contains(localInteger)) && (l >= SignonSettings.getInactivityPeriod() * 86400000L))
      {
        try
        {
          BankEmployee localBankEmployee2 = new BankEmployee(localLocale);
          localBankEmployee2.put("PASSWORD_STATUS", new Integer(4));
          localBankEmployee2.setId(String.valueOf(localSecureUser.getProfileID()));
          com.ffusion.csil.core.BankEmployeeAdmin.modifyBankEmployeePasswordStatus(localSecureUser, localBankEmployee2, new HashMap());
        }
        catch (CSILException localCSILException2)
        {
          this.error = MapError.mapError(localCSILException2);
        }
        localHttpSession.removeAttribute("SecureUser");
        this.error = 3017;
        return this.error;
      }
      localHttpSession.setAttribute("BankEmployee", localBankEmployee1);
      localHttpSession.setAttribute("BankId", localBankEmployee1.getBankId());
      localHttpSession.setAttribute("SecureUser", localSecureUser);
    }
    else if ((this.error == 0) && (localBankEmployee1.getStatusInt() != 0))
    {
      this.error = 46;
    }
    else if (this.error == 12)
    {
      this.error = 46;
    }
    Object localObject = (Integer)localSecureUser.get("AUTHENTICATE");
    if (localObject != null) {
      this.error = ((Integer)localObject).intValue();
    }
    return this.error;
  }
  
  private void jdMethod_for(BankEmployee paramBankEmployee, SecureUser paramSecureUser)
  {
    int i = paramBankEmployee.getJobTypeId();
    if (i <= 0)
    {
      i = 8;
      ArrayList localArrayList = paramBankEmployee.getAdminAccess();
      for (int j = 0; j < localArrayList.size(); j++)
      {
        String str = (String)localArrayList.get(j);
        if (str.equals("ACCESS_ADMINISTRATOR"))
        {
          i = 1;
          break;
        }
        if ((str.equals("ACCESS_SUPERVISOR")) && (i != 1)) {
          i = 2;
        } else if ((str.equals("ACCESS_CSR")) && (i != 1) && (i != 2)) {
          i = 4;
        }
      }
    }
    paramSecureUser.setEntitlementID(i);
  }
  
  public void setCredentialsCollection(String paramString)
  {
    this.oe = paramString;
  }
  
  public String getCredentialsCollection()
  {
    return this.oe;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.SignOnBankEmployee
 * JD-Core Version:    0.7.0.1
 */