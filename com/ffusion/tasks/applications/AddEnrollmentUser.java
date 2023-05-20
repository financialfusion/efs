package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddEnrollmentUser
  extends BaseTask
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    Application localApplication = (Application)localHttpSession.getAttribute("Application");
    if (localApplication == null)
    {
      this.error = 7201;
      str = this.taskErrorURL;
    }
    else
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      User localUser = new User(localLocale);
      jdMethod_for(localUser, localApplication);
      try
      {
        localUser = UserAdmin.addUser(localSecureUser, localUser, null);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  private void jdMethod_for(User paramUser, Application paramApplication)
  {
    HashMap localHashMap = paramApplication.getFieldValues();
    paramUser.setFirstName(paramApplication.getFirstName());
    paramUser.setLastName(paramApplication.getLastName());
    paramUser.setEmail(paramApplication.getEmailAddress());
    paramUser.setSSN(paramApplication.getSsn());
    paramUser.setMiddleName((String)localHashMap.get("middle_name"));
    paramUser.setTitle((String)localHashMap.get("title"));
    paramUser.setStreet((String)localHashMap.get("address1"));
    paramUser.setStreet2((String)localHashMap.get("address2"));
    paramUser.setCity((String)localHashMap.get("city"));
    paramUser.setState((String)localHashMap.get("state"));
    paramUser.setCountry((String)localHashMap.get("country"));
    paramUser.setZipCode((String)localHashMap.get("zipcode"));
    paramUser.setPhone((String)localHashMap.get("home_phone"));
    paramUser.setPhone2((String)localHashMap.get("work_phone"));
    paramUser.setGender((String)localHashMap.get("gender"));
    paramUser.setServiceLevel((String)localHashMap.get("service_level"));
    paramUser.setUserName((String)localHashMap.get("user_name"));
    paramUser.setPassword((String)localHashMap.get("password"));
    paramUser.setPasswordReminder((String)localHashMap.get("password_reminder"));
    paramUser.setPasswordClue((String)localHashMap.get("password_clue"));
    Calendar localCalendar = Calendar.getInstance();
    Date localDate = localCalendar.getTime();
    long l = localDate.getTime();
    paramUser.setCustId(String.valueOf(l));
    paramUser.setTimeout("1200");
    String str1 = (String)localHashMap.get("DOB_DAY");
    String str2 = (String)localHashMap.get("DOB_MONTH");
    String str3 = (String)localHashMap.get("DOB_YEAR");
    String str4 = str2 + "/" + str1 + "/" + str3;
    paramUser.set("BIRTH_DATE", str4);
    paramUser.set("MOTHERS_MAIDEN", (String)localHashMap.get("MOTHERS_MAIDEN"));
    paramUser.set("RESIDENCE", (String)localHashMap.get("RESIDENCE"));
    paramUser.set("INCOME", (String)localHashMap.get("INCOME"));
    paramUser.set("INTENDED_USE", (String)localHashMap.get("INTENDED_USE"));
    paramUser.set("MARITAL_STATUS", (String)localHashMap.get("marital_status"));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.AddEnrollmentUser
 * JD-Core Version:    0.7.0.1
 */