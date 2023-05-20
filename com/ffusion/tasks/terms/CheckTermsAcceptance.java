package com.ffusion.tasks.terms;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Terms;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckTermsAcceptance
  extends BaseTask
  implements TermsTask
{
  protected boolean _processFlag = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    boolean bool = false;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null) {
      this.error = 38;
    }
    if (this.error == 0)
    {
      if (this._processFlag) {
        if (localSecureUser.getAgent() != null) {
          str = null;
        } else {
          try
          {
            bool = Terms.checkTermsAcceptance(localSecureUser, localHashMap);
            if (!bool) {
              str = null;
            }
          }
          catch (CSILException localCSILException)
          {
            this.error = MapError.mapError(localCSILException);
            str = this.serviceErrorURL;
          }
        }
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setProcess(String paramString)
  {
    this._processFlag = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.terms.CheckTermsAcceptance
 * JD-Core Version:    0.7.0.1
 */