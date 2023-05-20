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

public class ModifyMessageQueueResponse
  extends MessageQueueResponse
  implements Task
{
  protected static String RESPONSENAME = "RESPONSENAME";
  protected static String RESPONSETEXT = "RESPONSETEXT";
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  protected String nextURL;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.nextURL = this.successURL;
    this.error = 0;
    if (this.initFlag)
    {
      if (!init(localHttpSession)) {
        this.nextURL = this.taskErrorURL;
      } else {
        this.nextURL = this.successURL;
      }
    }
    else if (validateInput(localHttpSession))
    {
      if (this.processFlag) {
        modifyResponse(localHttpSession);
      } else {
        this.nextURL = this.successURL;
      }
    }
    else {
      this.nextURL = this.taskErrorURL;
    }
    return this.nextURL;
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
  
  protected void modifyResponse(HttpSession paramHttpSession)
  {
    this.processFlag = false;
    HashMap localHashMap = null;
    localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    setModifierId(Integer.toString(localSecureUser.getProfileID()));
    try
    {
      MessageAdmin.modifyMessageQueueResponse(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.nextURL = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      MessageQueueResponses localMessageQueueResponses = (MessageQueueResponses)paramHttpSession.getAttribute("MessageQueueResponses");
      if (localMessageQueueResponses != null)
      {
        MessageQueueResponse localMessageQueueResponse = localMessageQueueResponses.getByID(getResponseID());
        if (localMessageQueueResponse != null) {
          localMessageQueueResponse.set(this);
        } else {
          localMessageQueueResponses.add(this);
        }
      }
      else
      {
        this.error = 8029;
        this.nextURL = this.taskErrorURL;
      }
    }
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    this.error = 0;
    if (this.validate != null)
    {
      if ((this.validate.indexOf(RESPONSENAME) != -1) && ((getResponseName() == null) || (getResponseName().length() == 0))) {
        this.error = 8040;
      } else if ((this.validate.indexOf(RESPONSETEXT) != -1) && ((getResponseText() == null) || (getResponseText().length() == 0))) {
        this.error = 8041;
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
 * Qualified Name:     com.ffusion.tasks.messages.ModifyMessageQueueResponse
 * JD-Core Version:    0.7.0.1
 */