package com.ffusion.tasks;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class SignOn
  extends BaseTask
{
  protected static String USERNAME = "USERNAME";
  protected static String PASSWORD = "PASSWORD";
  protected String validate;
  protected int minUserNameLength = 0;
  protected int minPasswordLength = 0;
  protected String userName;
  protected String password;
  protected String custId;
  protected String mustChangePasswordURL;
  protected String inactiveAccountURL;
  protected String invalidPasswordURL;
  protected String mustCompletePwdQAsURL = "mustanswer-pwdqas.jsp";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (validateInput(localHttpSession))
    {
      int i = signOn(paramHttpServletRequest);
      switch (i)
      {
      case 0: 
        str = this.successURL;
        break;
      case 100: 
      case 101: 
        str = this.invalidPasswordURL;
        break;
      case 102: 
        str = this.mustChangePasswordURL;
        break;
      case 1: 
      case 2: 
      case 3: 
      case 4: 
      case 5: 
      case 6: 
        str = this.serviceErrorURL;
        break;
      default: 
        str = this.taskErrorURL;
      }
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected abstract int signOn(HttpServletRequest paramHttpServletRequest);
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    this.error = 0;
    if ((this.validate != null) && (this.validate.indexOf(USERNAME) != -1) && ((this.userName == null) || (this.userName.length() < this.minUserNameLength) || (this.userName.length() > 20) || (this.userName.trim().length() == 0))) {
      this.error = 1;
    }
    this.validate = null;
    return this.error == 0;
  }
  
  public void setMinimumUserNameLength(String paramString)
  {
    try
    {
      this.minUserNameLength = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.minUserNameLength = 0;
    }
  }
  
  public void setMinimumPasswordLength(String paramString)
  {
    try
    {
      this.minPasswordLength = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.minPasswordLength = 0;
    }
  }
  
  public void setUserName(String paramString)
  {
    this.userName = paramString;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setCustId(String paramString)
  {
    this.custId = paramString;
  }
  
  public String getCustId()
  {
    return this.custId;
  }
  
  public void setMustChangePasswordURL(String paramString)
  {
    this.mustChangePasswordURL = paramString;
  }
  
  public void setMustCompletePwdQAsURL(String paramString)
  {
    this.mustCompletePwdQAsURL = paramString;
  }
  
  public void setInactiveAccountURL(String paramString)
  {
    this.inactiveAccountURL = paramString;
  }
  
  public void setInvalidPasswordURL(String paramString)
  {
    this.invalidPasswordURL = paramString;
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.SignOn
 * JD-Core Version:    0.7.0.1
 */