package com.ffusion.tasks.accountgroups;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AccountGroup;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteAccountGroup
  extends BaseTask
  implements AccountGroupsTask
{
  private String aM7 = "AccountGroup";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BusinessAccountGroup localBusinessAccountGroup = (BusinessAccountGroup)localHttpSession.getAttribute(this.aM7);
    if (localBusinessAccountGroup == null) {
      this.error = 70006;
    } else {
      try
      {
        AccountGroup.deleteAccountGroup(localSecureUser, localBusinessAccountGroup, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    if (this.error == 0) {
      str = this.successURL;
    } else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setAccountGroup(String paramString)
  {
    this.aM7 = paramString;
  }
  
  public String getAccountGroup()
  {
    return this.aM7;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accountgroups.DeleteAccountGroup
 * JD-Core Version:    0.7.0.1
 */