package com.ffusion.tasks;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class ChangePassword
  extends BaseTask
{
  protected String validInputURL;
  protected boolean processFlag = false;
  private int ff;
  private int fb;
  private int fc;
  private boolean fd;
  protected String currentPassword;
  protected String newPassword;
  private String fa;
  protected String currentFormPassword;
  private int fe = 1;
  
  protected abstract String changePassword(HttpSession paramHttpSession);
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (validatePasswords(localHttpSession))
    {
      str = changePassword(localHttpSession);
    }
    else if (this.processFlag)
    {
      this.processFlag = false;
      str = this.taskErrorURL;
    }
    else
    {
      str = this.validInputURL;
    }
    return str;
  }
  
  protected boolean validatePasswords(HttpSession paramHttpSession)
  {
    if ((this.currentPassword == null) || (this.newPassword == null) || (this.fa == null)) {
      this.error = 3;
    } else if (!this.newPassword.equals(this.fa)) {
      this.error = 5;
    } else if (this.newPassword.equals(this.currentPassword)) {
      this.error = 12;
    } else if ((this.currentFormPassword != null) && (!this.currentFormPassword.equals(this.currentPassword))) {
      this.error = 4;
    }
    if (this.error == 0) {
      this.error = PasswordUtil.validatePassword(this.newPassword, this.fe);
    }
    return this.error == 0;
  }
  
  protected boolean validateMinLetters()
  {
    boolean bool = true;
    if (this.ff > 0)
    {
      int i = this.newPassword.length();
      int j = 0;
      for (int k = 0; k < i; k++) {
        if (Character.isLetter(this.newPassword.charAt(k))) {
          j++;
        }
      }
      if (j < this.ff) {
        bool = false;
      }
    }
    return bool;
  }
  
  protected boolean validateMinNumbers()
  {
    boolean bool = true;
    if (this.fb > 0)
    {
      int i = this.newPassword.length();
      int j = 0;
      for (int k = 0; k < i; k++) {
        if (Character.isDigit(this.newPassword.charAt(k))) {
          j++;
        }
      }
      if (j < this.fb) {
        bool = false;
      }
    }
    return bool;
  }
  
  protected boolean validateNoSpecialChars()
  {
    boolean bool = true;
    if (this.fd == true)
    {
      int i = this.newPassword.length();
      for (int j = 0; j < i; j++) {
        if (!Character.isLetterOrDigit(this.newPassword.charAt(j)))
        {
          bool = false;
          break;
        }
      }
    }
    return bool;
  }
  
  public void setMinPasswordLength(String paramString)
  {
    this.fc = Integer.valueOf(paramString).intValue();
  }
  
  public String getMinPasswordLength()
  {
    return String.valueOf(this.fc);
  }
  
  public void setMinLetterLength(String paramString)
  {
    this.ff = Integer.valueOf(paramString).intValue();
  }
  
  public String getMinLetterLength()
  {
    return String.valueOf(this.ff);
  }
  
  public void setMinNumberLength(String paramString)
  {
    this.fb = Integer.valueOf(paramString).intValue();
  }
  
  public String getMinNumberLength()
  {
    return String.valueOf(this.fb);
  }
  
  public void setNoSpecialCharacters(String paramString)
  {
    this.fd = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getNoSpecialCharacters()
  {
    return String.valueOf(this.fd);
  }
  
  public void setCurrentPassword(String paramString)
  {
    this.currentPassword = paramString;
  }
  
  public String getCurrentPassword()
  {
    return this.currentPassword;
  }
  
  public void setCurrentFormPassword(String paramString)
  {
    this.currentFormPassword = paramString;
  }
  
  public String getCurrentFormPassword()
  {
    return this.currentFormPassword;
  }
  
  public void setNewPassword(String paramString)
  {
    this.newPassword = paramString;
  }
  
  public String getNewPassword()
  {
    return this.newPassword;
  }
  
  public void setVerifyPassword(String paramString)
  {
    this.fa = paramString;
  }
  
  public String getVerifyPassword()
  {
    return this.fa;
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidInputURL(String paramString)
  {
    this.validInputURL = paramString;
  }
  
  public void setUserType(String paramString)
  {
    try
    {
      this.fe = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.fe = 1;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ChangePassword
 * JD-Core Version:    0.7.0.1
 */