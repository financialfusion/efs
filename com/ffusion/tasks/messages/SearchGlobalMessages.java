package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.GlobalMessage;
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

public class SearchGlobalMessages
  extends BaseTask
  implements Task
{
  private String sg = "GlobalMessages";
  private String sh = "SearchMessage";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    GlobalMessages localGlobalMessages = null;
    Object localObject = localHttpSession.getAttribute(this.sh);
    try
    {
      if (localObject != null)
      {
        if ((localObject instanceof GlobalMessage)) {
          localGlobalMessages = Messages.getGlobalMessages(localSecureUser, (GlobalMessage)localObject, localHashMap);
        } else if ((localObject instanceof GlobalMessageSearchCriteria)) {
          localGlobalMessages = Messages.getGlobalMessages(localSecureUser, (GlobalMessageSearchCriteria)localObject, localHashMap);
        }
      }
      else {
        localGlobalMessages = Messages.getGlobalMessages(localSecureUser, (GlobalMessage)null, localHashMap);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute(this.sg, localGlobalMessages);
    }
    return str;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.sg = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.sg;
  }
  
  public void setSearchBean(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.sh = paramString;
    }
  }
  
  public String getSearchBean()
  {
    return this.sh;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SearchGlobalMessages
 * JD-Core Version:    0.7.0.1
 */