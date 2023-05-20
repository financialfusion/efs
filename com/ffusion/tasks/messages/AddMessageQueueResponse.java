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

public class AddMessageQueueResponse
  extends MessageQueueResponse
  implements Task
{
  protected static String QUEUE_ID = "QUEUE_ID";
  protected static String RESPONSE_NAME = "RESPONSE_NAME";
  protected static String RESPONSE_TEXT = "RESPONSE_TEXT";
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  protected String validate;
  protected boolean processFlag = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    if (validateInput(localHttpSession))
    {
      if (this.processFlag) {
        str = addQueueResponse(localHttpSession);
      } else {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String addQueueResponse(HttpSession paramHttpSession)
  {
    this.processFlag = false;
    String str = this.successURL;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    setModifierId(Integer.toString(localSecureUser.getProfileID()));
    try
    {
      MessageQueueResponse localMessageQueueResponse = MessageAdmin.addMessageQueueResponse(localSecureUser, this, localHashMap);
      set(localMessageQueueResponse);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      MessageQueueResponses localMessageQueueResponses = (MessageQueueResponses)paramHttpSession.getAttribute("MessageQueueResponses");
      if (localMessageQueueResponses != null)
      {
        localMessageQueueResponses.add(this);
      }
      else
      {
        this.error = 8029;
        str = this.taskErrorURL;
      }
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
      } else if ((this.validate.indexOf(RESPONSE_NAME) != -1) && ((getResponseName() == null) || (getResponseName().length() == 0))) {
        this.error = 8040;
      } else if ((this.validate.indexOf(RESPONSE_TEXT) != -1) && ((getResponseText() == null) || (getResponseText().length() == 0))) {
        this.error = 8041;
      }
      this.validate = null;
    }
    return this.error == 0;
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
 * Qualified Name:     com.ffusion.tasks.messages.AddMessageQueueResponse
 * JD-Core Version:    0.7.0.1
 */