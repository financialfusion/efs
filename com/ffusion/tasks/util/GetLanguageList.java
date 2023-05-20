package com.ffusion.tasks.util;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.LanguageDefns;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetLanguageList
  extends BaseTask
{
  private LanguageDefns Rc;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      this.Rc = Util.getLanguageList(localSecureUser, null);
      if ((localSecureUser != null) && (this.Rc != null)) {
        this.Rc.setLocale(localSecureUser.getLocale());
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  public LanguageDefns getLanguagesList()
  {
    if (this.Rc == null) {
      return new LanguageDefns();
    }
    return this.Rc;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetLanguageList
 * JD-Core Version:    0.7.0.1
 */