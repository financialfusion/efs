package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteMessageQueue
  extends MessageQueue
  implements Task
{
  protected static String QUEUE_ID = "QUEUE_ID";
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
        str = deleteQueue(localHttpSession);
        if (this.error == 0)
        {
          MessageQueues localMessageQueues = (MessageQueues)localHttpSession.getAttribute("MessageQueues");
          if (localMessageQueues == null)
          {
            this.error = 8020;
            str = this.taskErrorURL;
          }
          else
          {
            localMessageQueues.setFilter("All");
            localMessageQueues.removeByID(getQueueID());
            ServletContext localServletContext = paramHttpServlet.getServletContext();
            localServletContext.removeAttribute("InActiveMessageQueuesContext");
            localServletContext.removeAttribute("ActiveMessageQueuesContext");
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
    MessageQueue localMessageQueue = (MessageQueue)paramHttpSession.getAttribute("MessageQueue");
    if (localMessageQueue == null)
    {
      this.error = 8021;
      this.initFlag = false;
      return false;
    }
    set(localMessageQueue);
    this.initFlag = false;
    return true;
  }
  
  protected String deleteQueue(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    HashMap localHashMap = null;
    this.processFlag = false;
    localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      MessageAdmin.deleteMessageQueue(localSecureUser, this, localHashMap);
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
    this.error = 0;
    if (this.validate != null)
    {
      if ((this.validate.indexOf(QUEUE_ID) != -1) && ((getQueueID() == null) || (getQueueID().length() == 0))) {
        this.error = 8033;
      }
      this.validate = null;
    }
    return this.error == 0;
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
 * Qualified Name:     com.ffusion.tasks.messages.DeleteMessageQueue
 * JD-Core Version:    0.7.0.1
 */