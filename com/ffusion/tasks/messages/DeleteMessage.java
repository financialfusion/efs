package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteMessage
  extends Message
  implements Task
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    if (this.initFlag)
    {
      if (!init(localHttpSession)) {
        str = this.taskErrorURL;
      } else {
        str = this.successURL;
      }
    }
    else if (validateInput(localHttpSession))
    {
      if (this.processFlag)
      {
        str = deleteMsg(localHttpSession);
        if (this.error == 0)
        {
          com.ffusion.beans.messages.Messages localMessages = (com.ffusion.beans.messages.Messages)localHttpSession.getAttribute("Messages");
          if (localMessages == null)
          {
            this.error = 8001;
            str = this.taskErrorURL;
          }
          else
          {
            localMessages.setFilter("All");
            localMessages.removeByID(getID());
          }
        }
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    Message localMessage = (Message)paramHttpSession.getAttribute("Message");
    if (localMessage == null)
    {
      this.error = 8002;
      this.initFlag = false;
      return false;
    }
    set(localMessage);
    this.initFlag = false;
    return true;
  }
  
  protected String deleteMsg(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    HashMap localHashMap = null;
    this.processFlag = false;
    localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      com.ffusion.csil.core.Messages.deleteMessage(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    return true;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.DeleteMessage
 * JD-Core Version:    0.7.0.1
 */