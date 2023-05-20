package com.ffusion.tasks.user;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditUser
  extends AddUser
  implements UserTask
{
  protected boolean bInit = false;
  String pj;
  private static final String ph = "com.ffusion.util.logging.audit.task";
  private static final String pg = "AuditMessage_EditUser_1";
  private static final String pi = "AuditMessage_EditUser_2";
  private static final String pl = "AuditMessage_EditUser_3";
  private static final String pk = "AuditMessage_EditUser_4";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.nextURL = this.successURL;
    this.error = 0;
    User localUser1;
    User localUser2;
    if (this.bInit == true)
    {
      localUser1 = (User)localHttpSession.getAttribute(this.userSessionName);
      convertEmptyStringsToNull(localUser1);
      if (localUser1 != null)
      {
        this.nextURL = this.validInputURL;
        set(localUser1);
        localUser2 = new User((Locale)localHttpSession.getAttribute("java.util.Locale"));
        localUser2.set(localUser1);
        localHttpSession.setAttribute(this.oldUserSessionName, localUser2);
        this.bInit = false;
        Object localObject = localUser2.get("BIRTH_DATE");
        if (localObject != null)
        {
          if ((localObject instanceof DateTime)) {
            ((DateTime)localObject).setFormat("MM/dd/yyyy");
          }
          this.pj = localObject.toString();
        }
        if (getEmail() != null) {
          setVerifyEmail(getEmail());
        }
        setPassword2(getNewPassword());
        setOldPasswordReminder(getPasswordReminder());
        setOldPasswordReminder2(getPasswordReminder2());
        initializeLimits();
      }
      else
      {
        this.error = 3513;
        this.nextURL = this.taskErrorURL;
      }
    }
    else
    {
      try
      {
        convertEmptyStringsToNull(this);
        if (validateInput(localHttpSession))
        {
          if (this.processFlag)
          {
            this.processFlag = false;
            localUser1 = (User)localHttpSession.getAttribute(this.userSessionName);
            convertEmptyStringsToNull(localUser1);
            if (localUser1 != null)
            {
              localUser2 = (User)localHttpSession.getAttribute(this.oldUserSessionName);
              if (localUser2 != null)
              {
                if (!modifyUser(localHttpSession, localUser1, localUser2))
                {
                  if (this.nextURL == this.successURL) {
                    this.nextURL = this.serviceErrorURL;
                  }
                }
                else {
                  localHttpSession.removeAttribute(this.oldUserSessionName);
                }
              }
              else
              {
                this.error = 3502;
                this.nextURL = this.taskErrorURL;
              }
            }
            else
            {
              this.error = 3513;
              this.nextURL = this.taskErrorURL;
            }
          }
          else
          {
            this.nextURL = this.validInputURL;
          }
        }
        else {
          this.nextURL = this.taskErrorURL;
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        this.nextURL = this.serviceErrorURL;
      }
    }
    return this.nextURL;
  }
  
  protected boolean modifyUser(HttpSession paramHttpSession, User paramUser1, User paramUser2)
  {
    boolean bool = true;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    try
    {
      UserAdmin.modifyUser(localSecureUser, this, localHashMap);
      if ((getPrimarySecondary() != null) && (getPrimarySecondary().equals(User.USER_TYPE_PRIMARY)))
      {
        UserAdmin.modifyUserRegisteredWithBPW(localSecureUser, this, localHashMap);
        if (getAccountStatus().equals(User.STATUS_ACTIVE)) {
          UserAdmin.activateUserRegisteredWithBPW(localSecureUser, this, localHashMap);
        } else {
          UserAdmin.deactivateUserRegisteredWithBPW(localSecureUser, this, localHashMap);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.nextURL = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      paramUser1.set(this);
      paramHttpSession.setAttribute(this.userSessionName, paramUser1);
      HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 1, paramUser1.getId());
      processEntitlements(localSecureUser, localHistoryTracker);
      processLimits(localSecureUser);
      paramUser2.remove("BIRTH_DATE");
      paramUser2.put("BIRTH_DATE", this.pj);
      Object localObject1 = get("BIRTH_DATE");
      if (localObject1 != null)
      {
        if ((localObject1 instanceof DateTime)) {
          ((DateTime)localObject1).setFormat("MM/dd/yyyy");
        }
        localObject1 = localObject1.toString();
      }
      remove("BIRTH_DATE");
      put("BIRTH_DATE", localObject1);
      addUserHistory(paramHttpSession, "Modify", this, paramUser2, localHistoryTracker.getHistories());
      Users localUsers = (Users)paramHttpSession.getAttribute(this.usersSessionName);
      Object localObject2;
      if (localUsers != null)
      {
        localObject2 = localUsers.getByID(getId());
        if (paramUser2.getAccountStatus().equals(getAccountStatus()))
        {
          if (localObject2 != null) {
            ((User)localObject2).set(this);
          }
        }
        else if (localObject2 == null) {
          localUsers.add(this);
        } else {
          localUsers.remove(localObject2);
        }
        paramHttpSession.setAttribute(this.usersSessionName, localUsers);
      }
      Object localObject4;
      if (!paramUser2.getTimeout().equals(paramUser1.getTimeout()))
      {
        localObject2 = new Object[2];
        localObject2[0] = paramUser1.getUserName();
        int i = -1;
        try
        {
          i = Integer.parseInt(paramUser1.getTimeout()) / 60;
        }
        catch (Exception localException) {}
        localObject2[1] = (i >= 0 ? Integer.toString(i) : paramUser1.getTimeout());
        localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditUser_3", (Object[])localObject2);
        Initialize.audit(localSecureUser, (ILocalizable)localObject4, TrackingIDGenerator.GetNextID(), 3227);
      }
      Object localObject3;
      if (!paramUser2.getPasswordStatusStr().equals(paramUser1.getPasswordStatusStr()))
      {
        localObject2 = String.valueOf(localSecureUser.getProfileID());
        localObject3 = String.valueOf(localSecureUser.getUserType());
        localObject4 = new Object[1];
        localObject4[0] = paramUser1.getUserName();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditUser_1", (Object[])localObject4);
        AuditLog.log("", (String)localObject2, (String)localObject3, localLocalizableString, null, 3228, 0);
        if (((paramUser1.getPasswordStatusIntValue() == 0) || (paramUser1.getPasswordStatusIntValue() == 2)) && (paramUser2.getPasswordStatusIntValue() != 0) && (paramUser2.getPasswordStatusIntValue() != 2))
        {
          localObject4 = new Object[1];
          localObject4[0] = paramUser1.getUserName();
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditUser_2", (Object[])localObject4);
          AuditLog.log(this.id, (String)localObject2, (String)localObject3, localLocalizableString, null, 3201, 0);
        }
      }
      if (paramUser2.getTermsAccepted() < paramUser1.getTermsAccepted())
      {
        localObject2 = new Object[1];
        localObject2[0] = paramUser1.getUserName();
        localObject3 = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditUser_4", (Object[])localObject2);
        Initialize.audit(localSecureUser, (ILocalizable)localObject3, TrackingIDGenerator.GetNextID(), 3215);
      }
      if (localSecureUser.getId().equals(paramUser1.getCustId()))
      {
        localSecureUser.updatePassword(paramUser1.getPassword());
        paramHttpSession.setAttribute("SecureUser", localSecureUser);
      }
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  public void setInit(String paramString)
  {
    this.bInit = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setUserServiceName(String paramString) {}
  
  public void setState(String paramString)
  {
    if ((paramString != null) && (paramString.equals(""))) {
      super.setState(null);
    } else {
      super.setState(paramString);
    }
  }
  
  public void setCountry(String paramString)
  {
    if ((paramString != null) && (paramString.equals(""))) {
      super.setCountry(null);
    } else {
      super.setCountry(paramString);
    }
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
    throws CSILException
  {
    if (aPasswordEntered())
    {
      if ((this.newPassword == null) || (this.newPassword.length() <= 0) || (this.password2 == null) || (this.password2.length() <= 0)) {
        return setError(3);
      }
      if (this.newPassword.equals(this.password)) {
        return setError(12);
      }
    }
    return super.validateInput(paramHttpSession);
  }
  
  public void setResetPasswordQA(String paramString)
  {
    if ((paramString != null) && (paramString.equals("true")))
    {
      setPasswordReminder("");
      setPasswordReminder2("");
      setPasswordClue(null);
      setPasswordClue2(null);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.EditUser
 * JD-Core Version:    0.7.0.1
 */