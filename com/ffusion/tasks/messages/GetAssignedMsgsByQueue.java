package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.MessageCounts;
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

public class GetAssignedMsgsByQueue
  extends BaseTask
  implements Task
{
  private String r1 = null;
  private String r0 = "MessageCounts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (!f(this.r1))
    {
      this.error = 8033;
      return this.taskErrorURL;
    }
    MessageCounts localMessageCounts = null;
    try
    {
      localMessageCounts = Messages.getAssignedMessageCountsByQueue(localSecureUser, this.r1, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute(this.r0, localMessageCounts);
    }
    return str;
  }
  
  public String getQueueId()
  {
    return this.r1;
  }
  
  public void setQueueId(String paramString)
  {
    this.r1 = paramString;
  }
  
  private boolean f(String paramString)
  {
    return (paramString != null) && (paramString.trim().length() != 0);
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.r0 = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.r0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetAssignedMsgsByQueue
 * JD-Core Version:    0.7.0.1
 */