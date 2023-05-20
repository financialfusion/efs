package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetGlobalMessageById
  extends BaseTask
  implements Task
{
  private String ti = "GlobalMessage";
  private int tj = 0;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    GlobalMessage localGlobalMessage = null;
    try
    {
      localGlobalMessage = Messages.getGlobalMessageById(localSecureUser, this.tj, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute(this.ti, localGlobalMessage);
    }
    return str;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.ti = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.ti;
  }
  
  public void setSearchId(String paramString)
  {
    try
    {
      this.tj = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetGlobalMessageById
 * JD-Core Version:    0.7.0.1
 */