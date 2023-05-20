package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
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

public class ModifyMessageThreadStatus
  extends BaseTask
  implements Task
{
  protected String threadStatus = null;
  protected String threadID = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (this.threadID == null)
    {
      str = this.taskErrorURL;
      this.error = 8063;
      return this.taskErrorURL;
    }
    if (this.threadStatus == null)
    {
      str = this.taskErrorURL;
      this.error = 8064;
      return this.taskErrorURL;
    }
    int i = -1;
    try
    {
      i = Integer.parseInt(this.threadID);
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      this.error = 8065;
      return this.taskErrorURL;
    }
    int j = -1;
    try
    {
      j = Integer.parseInt(this.threadStatus);
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      this.error = 8066;
      return this.taskErrorURL;
    }
    if (!validateThreadStatus(j))
    {
      this.error = 8066;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    try
    {
      Messages.modifyMessageThreadStatus(localSecureUser, i, j, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error != 0) {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public boolean validateThreadStatus(int paramInt)
  {
    return (paramInt >= 1) || (paramInt <= 12);
  }
  
  public void setThreadID(String paramString)
  {
    this.threadID = paramString;
  }
  
  public void setThreadStatus(String paramString)
  {
    this.threadStatus = paramString;
  }
  
  public String getThreadID()
  {
    return this.threadID;
  }
  
  public String getThreadStatus()
  {
    return this.threadStatus;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.ModifyMessageThreadStatus
 * JD-Core Version:    0.7.0.1
 */