package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.messages.GlobalMessages;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetGlobalMessage
  extends BaseTask
  implements Task
{
  private int tr = 0;
  private String tq = "GlobalMessages";
  private String tp = "GlobalMessage";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    GlobalMessages localGlobalMessages = (GlobalMessages)localHttpSession.getAttribute(this.tq);
    if (localGlobalMessages == null)
    {
      this.error = 8047;
      return this.taskErrorURL;
    }
    GlobalMessage localGlobalMessage = localGlobalMessages.getByID(this.tr);
    if (localGlobalMessage == null)
    {
      this.error = 8048;
      return this.taskErrorURL;
    }
    localHttpSession.setAttribute(this.tp, localGlobalMessage);
    return str;
  }
  
  public void setGlobalMessageId(String paramString)
  {
    try
    {
      this.tr = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setSourceSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.tq = paramString;
    }
  }
  
  public String getSourceSessionKey()
  {
    return this.tq;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.tp = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.tp;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SetGlobalMessage
 * JD-Core Version:    0.7.0.1
 */