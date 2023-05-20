package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.Form;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Applications;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetApplication
  extends BaseTask
  implements Task
{
  String uP;
  protected String searchLanguage = "en_US";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HashMap localHashMap = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    Application localApplication = new Application(localLocale);
    if (this.searchLanguage.length() != 0) {
      localApplication.setSearchLanguage(this.searchLanguage);
    } else {
      localApplication.setSearchLanguage(null);
    }
    localApplication.setAppID(this.uP);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localApplication = Applications.getApplication(localSecureUser, localApplication, true, localHashMap);
      if ((localApplication.getCustomerID() != null) && (localApplication.getCustomerID().equals("0")))
      {
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.applications.resources", "PUBLIC_WEBSITE_USER_NAME", null);
        localApplication.setUserName((String)localLocalizableString.localize(localLocale));
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("Application", localApplication);
      Form localForm = localApplication.getForm();
      if (localForm != null) {
        localHttpSession.setAttribute("FormFields", localForm.getFormFields());
      }
    }
    return str;
  }
  
  public void setAppID(String paramString)
  {
    this.uP = paramString;
  }
  
  public void setSearchLanguage(String paramString)
  {
    this.searchLanguage = paramString;
  }
  
  public String getSearchLanguage()
  {
    return this.searchLanguage;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.GetApplication
 * JD-Core Version:    0.7.0.1
 */