package com.ffusion.tasks.multiuser;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetSecondaryUsersActiveState
  extends BaseTask
{
  protected SecureUser sUser;
  protected Locale locale;
  protected Users originalSecondaryUsers;
  protected Users modifiedSecondaryUsers;
  protected String originalSecondaryUsersSessionName = "SecondaryUsers";
  protected String modifiedSecondaryUsersSessionName = "ModifiedSecondaryUsers";
  protected boolean initialize;
  protected boolean process;
  private boolean aMO = false;
  private boolean aMP = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    this.sUser = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    this.locale = BaseTask.getLocale(localHttpSession, this.sUser);
    if (this.initialize)
    {
      if (!initialize(localHttpSession)) {
        str = this.taskErrorURL;
      }
    }
    else if (validateInput()) {
      try
      {
        User localUser1 = null;
        User localUser2 = null;
        HashMap localHashMap = new HashMap();
        if (this.process)
        {
          boolean bool = false;
          StringBuffer localStringBuffer = new StringBuffer();
          int i = 0;
          int j = this.modifiedSecondaryUsers.size();
          while (i < j)
          {
            localUser2 = (User)this.modifiedSecondaryUsers.get(i);
            localStringBuffer.setLength(0);
            localStringBuffer.append("ActivateUser_");
            localStringBuffer.append(i);
            bool = Boolean.valueOf((String)localHttpSession.getAttribute(localStringBuffer.toString())).booleanValue();
            if (bool) {
              localUser2.setAccountStatus(User.STATUS_ACTIVE);
            } else {
              localUser2.setAccountStatus(User.STATUS_INACTIVE);
            }
            if (!this.aMO)
            {
              localUser1 = this.originalSecondaryUsers.getByID(localUser2.getId());
              UserAdmin.modifyUser(this.sUser, localUser2, localUser1, localHashMap);
              addHistory(localUser1, localUser2);
            }
            i++;
          }
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    } else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setInitialize(String paramString)
  {
    this.initialize = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.process = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setUpdateOnly(String paramString)
  {
    this.aMO = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setOriginalSecondaryUsersSessionName(String paramString)
  {
    if (paramString == null) {
      this.originalSecondaryUsersSessionName = "SecondaryUsers";
    } else {
      this.originalSecondaryUsersSessionName = paramString;
    }
  }
  
  public void setModifiedSecondaryUsersSessionName(String paramString)
  {
    if (paramString == null) {
      this.modifiedSecondaryUsersSessionName = "ModifiedSecondaryUsers";
    } else {
      this.modifiedSecondaryUsersSessionName = paramString;
    }
  }
  
  public void setIsModificationTemporary(String paramString)
  {
    this.aMP = Boolean.valueOf(paramString).booleanValue();
  }
  
  protected boolean initialize(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if ((bool) && ((this.originalSecondaryUsersSessionName == null) || (this.originalSecondaryUsersSessionName.length() == 0)))
    {
      this.error = 28023;
      bool = false;
    }
    if ((bool) && ((this.modifiedSecondaryUsersSessionName == null) || (this.modifiedSecondaryUsersSessionName.length() == 0)))
    {
      this.error = 28024;
      bool = false;
    }
    if (bool)
    {
      this.originalSecondaryUsers = ((Users)paramHttpSession.getAttribute(this.originalSecondaryUsersSessionName));
      if (this.originalSecondaryUsers == null)
      {
        this.error = 28014;
        bool = false;
      }
      else
      {
        User localUser1 = null;
        User localUser2 = null;
        this.modifiedSecondaryUsers = new Users(this.locale);
        int i = 0;
        int j = this.originalSecondaryUsers.size();
        while (i < j)
        {
          localUser1 = (User)this.originalSecondaryUsers.get(i);
          localUser2 = this.modifiedSecondaryUsers.add();
          localUser2.set(localUser1);
          i++;
        }
        paramHttpSession.setAttribute(this.modifiedSecondaryUsersSessionName, this.modifiedSecondaryUsers);
      }
    }
    return bool;
  }
  
  protected boolean validateInput()
  {
    boolean bool = true;
    if ((bool) && (this.sUser == null))
    {
      this.error = 38;
      bool = false;
    }
    if ((bool) && (this.originalSecondaryUsers == null))
    {
      this.error = 28014;
      bool = false;
    }
    return bool;
  }
  
  protected void addHistory(User paramUser1, User paramUser2)
  {
    HistoryTracker localHistoryTracker = new HistoryTracker(this.sUser, 1, paramUser2.getId());
    int i;
    if (this.aMP) {
      i = 20;
    } else {
      i = 17;
    }
    paramUser2.logChanges(localHistoryTracker, paramUser1, localHistoryTracker.buildLocalizableComment(i));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for com.ffusion.tasks.multiuser.SetSecondaryUsersActiveState: " + localProfileException.toString());
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.SetSecondaryUsersActiveState
 * JD-Core Version:    0.7.0.1
 */