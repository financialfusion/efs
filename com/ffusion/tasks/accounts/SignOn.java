package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Accounts;
import com.ffusion.services.AccountService;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignOn
  extends com.ffusion.tasks.SignOn
  implements Task
{
  private String mF = "com.ffusion.service.AccountService";
  
  protected int signOn(HttpServletRequest paramHttpServletRequest)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    AccountService localAccountService = (AccountService)localHttpSession.getAttribute(this.mF);
    HashMap localHashMap = null;
    if (localAccountService != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localAccountService);
    }
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null) {
      localSecureUser = new SecureUser();
    }
    try
    {
      localSecureUser = Accounts.signOn(localSecureUser, this.userName, this.password, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("SecureUser", localSecureUser);
    }
    return this.error;
  }
  
  public void setServiceName(String paramString)
  {
    this.mF = paramString;
  }
  
  public String getServiceName()
  {
    return this.mF;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.SignOn
 * JD-Core Version:    0.7.0.1
 */