package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMessageThreadsCount
  extends BaseTask
  implements Task
{
  protected String threadStatus = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str2 = null;
    MessageThreads localMessageThreads = new MessageThreads((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if ((this.threadStatus != null) && (this.threadStatus.length() > 0))
    {
      localObject = localMessageThreads.create();
      ((MessageThread)localObject).setThreadStatus(this.threadStatus);
    }
    Object localObject = new HashMap();
    ((HashMap)localObject).put("MESSAGETHREADS", localMessageThreads);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      str2 = MessageAdmin.getMessageThreadsCount(localSecureUser, (HashMap)localObject);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("MessageThreadsCount", str2);
    } else {
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  public void setThreadStatus(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      this.threadStatus = null;
    }
    else
    {
      paramString = paramString.trim().toUpperCase();
      if (paramString.equals("MESSAGE_ALL")) {
        this.threadStatus = "0";
      } else if (paramString.equals("MESSAGE_NEW")) {
        this.threadStatus = "1";
      } else if (paramString.equals("MESSAGE_OPEN")) {
        this.threadStatus = "2";
      } else if (paramString.equals("MESSAGE_IQUERY")) {
        this.threadStatus = "3";
      } else if (paramString.equals("MESSAGE_CQUERY")) {
        this.threadStatus = "4";
      } else if (paramString.equals("MESSAGE_CLOSED")) {
        this.threadStatus = "5";
      } else if (paramString.equals("MESSAGE_NOTES")) {
        this.threadStatus = "6";
      } else if (paramString.equals("MESSAGE_AUTOREPLY")) {
        this.threadStatus = "7";
      } else if (paramString.equals("MESSAGE_PENDING")) {
        this.threadStatus = "9";
      } else if (paramString.equals("MESSAGE_DENIED")) {
        this.threadStatus = "10";
      } else {
        this.threadStatus = paramString;
      }
    }
  }
  
  public String getThreadStatus()
  {
    if (this.threadStatus != null) {
      return this.threadStatus;
    }
    return "";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetMessageThreadsCount
 * JD-Core Version:    0.7.0.1
 */