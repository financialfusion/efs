package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.BankMessageInfo;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.efs.adapters.profile.SignonSettings;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.PasswordUtil;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ResetUserPassword
  extends BaseTask
  implements UserTask
{
  public static final String RESET_PSWD_SUBJECT = "RESET_PSWD_SUBJECT";
  public static final String RESET_PSWD_BODY = "RESET_PSWD_BODY";
  public static final String RESET_PSWD_BODY2 = "RESET_PSWD_BODY2";
  private String wu = "BusinessEmployee";
  private boolean wB = false;
  private String wy;
  private String wz;
  private String wv;
  private int wr = SignonSettings.getMinPasswordLength();
  private int ws = 1;
  private int wx = 1;
  private boolean ww = false;
  private boolean wq = true;
  protected String fromEmail = null;
  protected String emailSubject = null;
  protected String emailMemo = null;
  private String wt = null;
  private String wA = "en_US";
  private int wC = 1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    User localUser1 = (User)localHttpSession.getAttribute(this.wu);
    if (localUser1 == null)
    {
      this.error = 3513;
      return this.taskErrorURL;
    }
    this.wy = localUser1.getPassword();
    if (validatePassword() == true)
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      HashMap localHashMap = new HashMap();
      try
      {
        UserAdmin.resetUserPassword(localSecureUser, localUser1, this.wy, this.wz, localHashMap);
        User localUser2 = new User((Locale)localHttpSession.getAttribute("java.util.Locale"));
        localUser2.setId(localUser1.getId());
        localUser2.put("PASSWORD_STATUS", "2");
        UserAdmin.modifyPasswordStatus(localSecureUser, localUser2, localHashMap);
        if (this.wB == true)
        {
          String str2 = localUser1.getEmail();
          if ((str2 != null) && (str2.length() != 0))
          {
            StringBuffer localStringBuffer = new StringBuffer();
            Object localObject;
            if (this.fromEmail == null)
            {
              localHashMap.put("PREFERRED_LANG", this.wA);
              localObject = Messages.getEmailSettings(localSecureUser, localUser1.getIdValue(), localHashMap);
              this.fromEmail = ((BankMessageInfo)localObject).getNotifyFromEmail();
            }
            if (this.emailSubject == null) {
              this.emailSubject = ResourceUtil.getString("RESET_PSWD_SUBJECT", "com.ffusion.beans.user.resources", Locale.US);
            }
            MessageFormat localMessageFormat;
            Object[] arrayOfObject;
            if ((this.emailMemo == null) && (this.wt == null))
            {
              localObject = ResourceUtil.getString("RESET_PSWD_BODY", "com.ffusion.beans.user.resources", Locale.US);
              localMessageFormat = new MessageFormat((String)localObject);
              arrayOfObject = new Object[] { this.wz };
              localStringBuffer.append(localMessageFormat.format(arrayOfObject));
            }
            else if ((this.emailMemo != null) && (this.wt == null))
            {
              localObject = ResourceUtil.getString("RESET_PSWD_BODY2", "com.ffusion.beans.user.resources", Locale.US);
              localMessageFormat = new MessageFormat((String)localObject);
              arrayOfObject = new Object[] { this.emailMemo, this.wz };
              localStringBuffer.append(localMessageFormat.format(arrayOfObject));
            }
            else if ((this.emailMemo == null) && (this.wt != null))
            {
              localStringBuffer.append(this.wt);
              localStringBuffer.append(this.wz);
              localStringBuffer.append('.');
            }
            else
            {
              localStringBuffer.append(this.emailMemo);
              localStringBuffer.append(' ');
              localStringBuffer.append(this.wt);
              localStringBuffer.append(this.wz);
              localStringBuffer.append('.');
            }
            this.error = Messages.sendEmail(localSecureUser, str2, this.fromEmail, this.emailSubject, localStringBuffer.toString());
            if (this.error != 0) {
              return this.serviceErrorURL;
            }
          }
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        return this.serviceErrorURL;
      }
    }
    else
    {
      return this.taskErrorURL;
    }
    return str1;
  }
  
  protected boolean validatePassword()
  {
    int i = PasswordUtil.validatePassword(this.wz, this.wC);
    if (i != 0)
    {
      this.error = i;
      return false;
    }
    if (!jdMethod_goto(this.wz, this.wv))
    {
      this.error = 5;
      return false;
    }
    return true;
  }
  
  public void setNewPassword(String paramString)
  {
    this.wz = paramString;
  }
  
  public void setConfirmPassword(String paramString)
  {
    this.wv = paramString;
  }
  
  public void setMinPasswordLength(String paramString)
  {
    try
    {
      this.wr = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setMinLettersInPassword(String paramString)
  {
    try
    {
      this.ws = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setMinNumbersInPassword(String paramString)
  {
    try
    {
      this.wx = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setAllowSpecialCharactersInPassword(String paramString)
  {
    this.ww = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setBusinessEmployeeSessionName(String paramString)
  {
    this.wu = paramString;
  }
  
  protected int countLetters(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    for (int k = 0; k < j; k++) {
      if (Character.isLetter(paramString.charAt(k))) {
        i++;
      }
    }
    return i;
  }
  
  protected boolean hasSpecialChars(String paramString)
  {
    int i = paramString.length();
    for (int j = 0; j < i; j++) {
      if (!Character.isLetterOrDigit(paramString.charAt(j))) {
        return true;
      }
    }
    return false;
  }
  
  public void setEmailNotification(String paramString)
  {
    this.wB = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEmailNotification()
  {
    return String.valueOf(this.wB);
  }
  
  public void setFromEmail(String paramString)
  {
    this.fromEmail = paramString;
  }
  
  public String getFromEmail()
  {
    return this.fromEmail;
  }
  
  public void setEmailSubject(String paramString)
  {
    this.emailSubject = paramString;
  }
  
  public String getEmailSubject()
  {
    return this.emailSubject;
  }
  
  public void setEmailMemo(String paramString)
  {
    this.emailMemo = paramString;
  }
  
  public String getEmailMemo()
  {
    return this.emailMemo;
  }
  
  public void setSendPassword(String paramString)
  {
    this.wq = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getSendPassword()
  {
    return this.wq ? "true" : "false";
  }
  
  public void setPreferredLanguage(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.wA = "en_US";
    } else {
      this.wA = paramString;
    }
  }
  
  public void setUserType(String paramString)
  {
    try
    {
      this.wC = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.wC = 1;
    }
  }
  
  private static boolean jdMethod_goto(String paramString1, String paramString2)
  {
    boolean bool = false;
    if (paramString1 == paramString2) {
      bool = true;
    } else if ((paramString1 == null) && (paramString2.length() == 0)) {
      bool = true;
    } else if ((paramString2 == null) && (paramString1.length() == 0)) {
      bool = true;
    } else if (paramString1.equals(paramString2)) {
      bool = true;
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.ResetUserPassword
 * JD-Core Version:    0.7.0.1
 */