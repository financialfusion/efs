package com.ffusion.tasks;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.UserLocale;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public abstract class BaseTask
  implements Task
{
  public static final String SERVICE_ERROR_SESSION_NAME = "com.ffusion.tasks.BaseTask.taskErrorURL";
  public static final String TASK_ERROR_SESSION_NAME = "com.ffusion.tasks.BaseTask.serviceErrorURL";
  public static final String ERROR_REDIRECT_SESSION_NAME = "com.ffusion.tasks.BaseTask.errorRedirectURL";
  public static final String SERVICE_ERROR = "SE";
  public static final String SERVICE_ERROR_WHY = "SE_WHY";
  public static final String SERVICE_ERROR_WHERE = "SE_WHERE";
  public static final String TASK_ERROR_WHY = "TE_WHY";
  public static final String TASK_ERROR_WHERE = "TE_WHERE";
  public static final String TASK_ERROR = "TE";
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  public static final String TASKTIMEOUTSECS = "TaskTimeoutSecs";
  public static final String TASK_RESOURCES_BUNDLE_NAME = "com.ffusion.tasks.resources";
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public String getSuccessURL()
  {
    return this.successURL;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public String getTaskErrorURL()
  {
    return this.taskErrorURL;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getServiceErrorURL()
  {
    return this.serviceErrorURL;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setErrorCode(int paramInt)
  {
    this.error = paramInt;
  }
  
  public static long getTaskTimeoutValue(ServletContext paramServletContext)
  {
    long l = 120L;
    try
    {
      String str = (String)paramServletContext.getAttribute("TaskTimeoutSecs");
      if (str != null) {
        l = Long.parseLong(str);
      }
      if (l == 0L) {
        l = 120L;
      }
    }
    catch (Throwable localThrowable) {}
    return l * 1000L;
  }
  
  public static Locale getLocale(HttpSession paramHttpSession, SecureUser paramSecureUser)
  {
    Locale localLocale = null;
    if (localLocale == null)
    {
      UserLocale localUserLocale = (UserLocale)paramHttpSession.getAttribute("UserLocale");
      localLocale = localUserLocale == null ? null : localUserLocale.getLocale();
    }
    if (localLocale == null) {
      localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    }
    if ((localLocale == null) && (paramSecureUser != null)) {
      localLocale = paramSecureUser.getLocale();
    }
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    return localLocale;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.BaseTask
 * JD-Core Version:    0.7.0.1
 */