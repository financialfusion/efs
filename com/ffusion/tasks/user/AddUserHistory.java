package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddUserHistory
  extends BaseTask
  implements UserTask
{
  private String v1 = "UserService";
  private String vZ = "";
  private String v0 = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    BankEmployee localBankEmployee = (BankEmployee)localHttpSession.getAttribute("BankEmployee");
    if (localBankEmployee == null)
    {
      str = this.taskErrorURL;
      this.error = 3507;
    }
    else
    {
      User localUser = (User)localHttpSession.getAttribute("User");
      if (localUser == null)
      {
        this.error = 3513;
        return this.taskErrorURL;
      }
      if (this.vZ.equals("Modify"))
      {
        str = this.successURL;
      }
      else if (this.vZ.equals("Add"))
      {
        str = this.successURL;
      }
      else
      {
        Histories localHistories1 = new Histories((Locale)localHttpSession.getAttribute("java.util.Locale"));
        History localHistory = AddUser.buildHistory(localHttpSession, localUser, "Comment", "", "", this.v0);
        localHistories1.add(localHistory);
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        try
        {
          UserAdmin.addHistory(localSecureUser, localHistories1, null);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
        if (this.error == 0)
        {
          str = this.successURL;
          Histories localHistories2 = (Histories)localHttpSession.getAttribute("UserHistories");
          if (localHistories2 != null)
          {
            localHistories2.add(localHistory);
            localHttpSession.setAttribute("UserHistories", localHistories2);
          }
        }
        else
        {
          str = this.serviceErrorURL;
        }
      }
    }
    return str;
  }
  
  public void setAction(String paramString)
  {
    this.vZ = paramString;
  }
  
  public void setComments(String paramString)
  {
    this.v0 = paramString;
  }
  
  public void setUserServiceName(String paramString)
  {
    this.v1 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.AddUserHistory
 * JD-Core Version:    0.7.0.1
 */