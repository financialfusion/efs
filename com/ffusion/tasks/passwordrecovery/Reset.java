package com.ffusion.tasks.passwordrecovery;

import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PasswordRecovery;
import com.ffusion.efs.adapters.profile.SignonSettings;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.PasswordUtil;
import com.ffusion.tasks.util.TaskUtil;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Reset
  extends BaseTask
{
  public static final String PASSWORD_RESET_FAILURE = "PasswordResetFailure";
  public static final String SERVICE_ERROR = "Service Error";
  protected int _minLetters = 0;
  protected int _minNumbers = 0;
  protected int _minPasswordLength = SignonSettings.getMinPasswordLength();
  protected boolean _allowSpecialChars = false;
  protected String _resetFailureURL = null;
  protected int _userType = 1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    User localUser = (User)localHttpSession.getAttribute("User");
    Locale localLocale = (Locale)localHttpSession.getAttribute("Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    this.error = 0;
    if ((this._resetFailureURL == null) || (this._resetFailureURL.length() <= 0)) {
      this._resetFailureURL = super.getServiceErrorURL();
    }
    Object localObject1 = null;
    Object localObject2 = null;
    String str1;
    while (localEnumeration.hasMoreElements())
    {
      str1 = (String)localEnumeration.nextElement();
      String str2 = paramHttpServletRequest.getParameter(str1);
      if (str1.equals("PRNewPassword")) {
        localObject1 = str2;
      } else if (str1.equals("ConfirmPassword")) {
        localObject2 = str2;
      }
      localHashMap1.put(str1, str2);
    }
    if ((localObject1 == null) || (localObject1.length() <= 0)) {
      this.error = 3;
    } else if ((localObject2 == null) || (localObject2.length() <= 0)) {
      this.error = 5;
    } else if (!localObject1.equals(localObject2)) {
      this.error = 5;
    }
    if (this.error != 0)
    {
      str1 = TaskUtil.getTaskErrorDescription(this.error, localLocale);
      setErrorMessageIntoSession(str1, localHttpSession);
      return this._resetFailureURL;
    }
    this.error = PasswordUtil.validatePassword(localObject1, this._userType);
    if (this.error != 0)
    {
      str1 = TaskUtil.getTaskErrorDescription(this.error, localLocale);
      setErrorMessageIntoSession(str1, localHttpSession);
      return this._resetFailureURL;
    }
    try
    {
      PasswordRecovery.reset(localUser, localHashMap1, localHashMap2);
      localHttpSession.setAttribute("Password", localUser.getPassword());
      localHttpSession.setAttribute("UserName", localUser.getUserName());
      str1 = getSuccessURL();
    }
    catch (CSILException localCSILException)
    {
      this.error = localCSILException.getServiceError();
      String str3 = TaskUtil.getServiceErrorDescription(this.error, localLocale);
      setErrorMessageIntoSession(str3, localHttpSession);
      return this._resetFailureURL;
    }
    return str1;
  }
  
  protected void setErrorMessageIntoSession(String paramString, HttpSession paramHttpSession)
  {
    if (paramHttpSession == null) {
      return;
    }
    paramHttpSession.setAttribute("PasswordResetFailure", paramString);
  }
  
  public void setResetFailureURL(String paramString)
  {
    this._resetFailureURL = paramString;
  }
  
  public void setUserType(String paramString)
  {
    try
    {
      this._userType = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this._userType = 1;
    }
  }
  
  public void setMinLetterLength(String paramString)
  {
    this._minLetters = Integer.valueOf(paramString).intValue();
  }
  
  public void setMinNumberLength(String paramString)
  {
    this._minNumbers = Integer.valueOf(paramString).intValue();
  }
  
  public void setMinPasswordLength(String paramString)
  {
    this._minPasswordLength = Integer.parseInt(paramString);
  }
  
  public void setSpecCharsAllowed(String paramString)
  {
    this._allowSpecialChars = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.passwordrecovery.Reset
 * JD-Core Version:    0.7.0.1
 */