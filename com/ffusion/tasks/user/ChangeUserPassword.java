package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.ChangePassword;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpSession;

public class ChangeUserPassword
  extends ChangePassword
  implements UserTask
{
  private String xB = "UserService";
  
  protected String changePassword(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.error = 0;
    User localUser1 = (User)paramHttpSession.getAttribute("User");
    if (localUser1 == null)
    {
      str = this.taskErrorURL;
      this.error = 3513;
    }
    else
    {
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      try
      {
        UserAdmin.modifyUserPassword(localSecureUser, localUser1, this.newPassword, this.currentPassword, null);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
        HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 1, localUser1.getId(), null);
        localHistoryTracker.logChange(User.BEAN_NAME, "PASSWORD", (String)null, (String)null, localHistoryTracker.buildLocalizableComment(17));
        try
        {
          HashMap localHashMap = null;
          User localUser2 = new User(localLocale);
          localUser2.setId(localUser1.getId());
          localUser2.put("PASSWORD_STATUS", "0");
          UserAdmin.modifyPasswordStatus(localSecureUser, localUser2, localHashMap);
          UserAdmin.addHistory(localSecureUser, localHistoryTracker.getHistories(), localHashMap);
        }
        catch (CSILException localCSILException2)
        {
          this.error = MapError.mapError(localCSILException2);
        }
        localUser1.setPassword(this.newPassword);
        paramHttpSession.setAttribute("User", localUser1);
      }
    }
    return str;
  }
  
  protected boolean validatePasswords(HttpSession paramHttpSession)
  {
    boolean bool = false;
    bool = super.validatePasswords(paramHttpSession);
    if (bool == true)
    {
      User localUser = (User)paramHttpSession.getAttribute("User");
      if (localUser == null)
      {
        this.error = 3513;
        bool = false;
      }
      else if (!this.currentPassword.equals(localUser.getPassword()))
      {
        this.error = 4;
        bool = false;
      }
    }
    return bool;
  }
  
  public void setUserServiceName(String paramString)
  {
    this.xB = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.ChangeUserPassword
 * JD-Core Version:    0.7.0.1
 */