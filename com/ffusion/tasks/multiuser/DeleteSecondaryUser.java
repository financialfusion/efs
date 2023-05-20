package com.ffusion.tasks.multiuser;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteSecondaryUser
  extends EditSecondaryUser
{
  private String pD;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    this.sUser = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    this.locale = BaseTask.getLocale(localHttpSession, this.sUser);
    if (this.initialize)
    {
      str1 = initialize(localHttpSession);
    }
    else
    {
      str1 = validateInput();
      if ((this.error == 0) && (this.process)) {
        try
        {
          HashMap localHashMap = new HashMap();
          modifyTransactionsOwnership(localHttpSession);
          setAccountStatus(User.STATUS_DELETED);
          UserAdmin.modifyUser(this.sUser, this, this.oldSecondaryUser, localHashMap);
          addHistory();
          if ((this.error == 0) && (this.pD != null))
          {
            StringTokenizer localStringTokenizer = new StringTokenizer(this.pD, ",");
            String str2 = null;
            Users localUsers = null;
            Object localObject = null;
            while (localStringTokenizer.hasMoreTokens())
            {
              str2 = localStringTokenizer.nextToken();
              localUsers = (Users)localHttpSession.getAttribute(str2);
              if (localUsers != null) {
                localUsers.removeByID(getId());
              }
            }
          }
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str1 = this.serviceErrorURL;
        }
      }
    }
    return str1;
  }
  
  public void setSecondaryUserCollectionNames(String paramString)
  {
    this.pD = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.DeleteSecondaryUser
 * JD-Core Version:    0.7.0.1
 */