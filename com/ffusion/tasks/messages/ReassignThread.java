package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.efs.adapters.profile.Profile;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReassignThread
  extends BaseTask
  implements Task
{
  private static String rQ = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    MessageThread localMessageThread = (MessageThread)localHttpSession.getAttribute("MessageThread");
    if (localSecureUser == null)
    {
      this.error = 1037;
      str = this.taskErrorURL;
    }
    else if (localMessageThread == null)
    {
      this.error = 8024;
      str = this.taskErrorURL;
    }
    else if (!Profile.isValidId(rQ))
    {
      this.error = 8004;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        Messages.reassignThread(localSecureUser, localHashMap, localMessageThread, rQ);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    rQ = null;
    return str;
  }
  
  public void setNewOwner(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      rQ = paramString;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.ReassignThread
 * JD-Core Version:    0.7.0.1
 */