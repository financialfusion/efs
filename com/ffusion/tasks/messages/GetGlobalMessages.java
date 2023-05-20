package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.GlobalMessageSearchCriteria;
import com.ffusion.beans.messages.GlobalMessages;
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

public class GetGlobalMessages
  extends BaseTask
  implements Task
{
  private String fz = "GlobalMessages";
  private String fA = "en_US";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    GlobalMessages localGlobalMessages = null;
    try
    {
      GlobalMessageSearchCriteria localGlobalMessageSearchCriteria = new GlobalMessageSearchCriteria();
      localGlobalMessageSearchCriteria.setSearchLanguage(getSearchLanguage());
      localGlobalMessages = Messages.getGlobalMessages(localSecureUser, localGlobalMessageSearchCriteria, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute(this.fz, localGlobalMessages);
    }
    return str;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.fz = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.fz;
  }
  
  public void setSearchLanguage(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.fA = null;
    } else {
      this.fA = paramString;
    }
  }
  
  public String getSearchLanguage()
  {
    return this.fA;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetGlobalMessages
 * JD-Core Version:    0.7.0.1
 */