package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.MessageQueueResponse;
import com.ffusion.beans.messages.MessageQueueResponses;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteMessageQResponse
  extends MessageQueueResponse
  implements Task
{
  protected static String RESPONSE_ID = "RESPONSE_ID";
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
        str = deleteQResponse(localHttpSession);
        if (this.error == 0)
        {
          MessageQueueResponses localMessageQueueResponses = (MessageQueueResponses)localHttpSession.getAttribute("MessageQueueResponses");
          if (localMessageQueueResponses == null)
          {
            this.error = 8029;
            str = this.taskErrorURL;
          }
          else
          {
            localMessageQueueResponses.removeByID(getResponseID());
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
    this.initFlag = false;
    MessageQueueResponse localMessageQueueResponse = (MessageQueueResponse)paramHttpSession.getAttribute("MessageQueueResponse");
    if (localMessageQueueResponse == null)
    {
      this.error = 8030;
      return false;
    }
    set(localMessageQueueResponse);
    return true;
  }
  
  protected String deleteQResponse(HttpSession paramHttpSession)
  {
    this.processFlag = false;
    String str = this.successURL;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      MessageAdmin.deleteMessageQueueResponse(localSecureUser, this, localHashMap);
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
      if ((this.validate.indexOf(RESPONSE_ID) != -1) && ((getResponseID() == null) || (getResponseID().length() == 0))) {
        this.error = 8039;
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
 * Qualified Name:     com.ffusion.tasks.messages.DeleteMessageQResponse
 * JD-Core Version:    0.7.0.1
 */