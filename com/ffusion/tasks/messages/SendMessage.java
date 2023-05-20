package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendMessage
  extends Message
  implements Task
{
  protected static String FROM = "FROM";
  protected static String TO = "TO";
  protected static String SUBJECT = "SUBJECT";
  protected static String DATE = "DATE";
  protected static String MEMO = "MEMO";
  protected int error;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String messagingServiceName = "com.ffusion.services.Messaging3";
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected int maxSize = 1024;
  
  public SendMessage()
  {
    setDateFormat("SHORT");
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    setLocale((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if (getMemo().length() > this.maxSize)
    {
      this.error = 8077;
      str = this.taskErrorURL;
      return str;
    }
    if (validateInput(localHttpSession))
    {
      if (this.processFlag) {
        str = sendMessage(localHttpSession);
      } else {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String sendMessage(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.error = 0;
    this.processFlag = false;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if ((getMemo() == null) || (getMemo().trim().length() == 0))
    {
      this.error = 8007;
      return this.taskErrorURL;
    }
    this.memo = getMemo().trim();
    try
    {
      Messages.sendMessage(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      if (this.messagingServiceName.compareTo("com.ffusion.tasks.messages.EmailMessages") == 0)
      {
        paramHttpSession.setAttribute("EmailSent", "false");
      }
      else
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    this.error = 0;
    if (this.validate != null)
    {
      if ((this.validate.indexOf(FROM) != -1) && ((getFrom() == null) || (getFrom().length() == 0))) {
        this.error = 8003;
      }
      if ((this.error == 0) && (this.validate.indexOf(TO) != -1) && ((getTo() == null) || (getTo().trim().length() == 0))) {
        this.error = 8004;
      }
      if ((this.error == 0) && (this.validate.indexOf(SUBJECT) != -1) && ((getSubject() == null) || (getSubject().trim().length() == 0))) {
        this.error = 8005;
      }
      if ((this.error == 0) && (this.validate.indexOf(SUBJECT) != -1) && (getSubject().length() > 100)) {
        this.error = 8060;
      }
      if ((this.error == 0) && (this.validate.indexOf(MEMO) != -1) && ((getMemo() == null) || (getMemo().trim().length() == 0))) {
        this.error = 8007;
      }
      if ((this.error == 0) && (this.validate.indexOf(DATE) != -1) && (getDateValue() == null)) {
        this.error = 8006;
      }
      if (this.error == 0) {
        this.error = validateExtra();
      }
      this.validate = null;
    }
    return this.error == 0;
  }
  
  protected int validateExtra()
  {
    return 0;
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
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setError(String paramString)
  {
    this.error = Integer.parseInt(paramString);
  }
  
  public void setMessagingServiceName(String paramString)
  {
    this.messagingServiceName = paramString;
  }
  
  public void setTo(String paramString)
  {
    if (paramString.startsWith("E"))
    {
      setToType("EMPLOYEE");
      super.setTo(paramString.substring(1));
    }
    else if (paramString.startsWith("Q"))
    {
      setToType("QUEUE");
      super.setTo(paramString.substring(1));
    }
    else
    {
      super.setTo(paramString);
    }
  }
  
  public void setMaxMessageSize(String paramString)
  {
    this.maxSize = Integer.valueOf(paramString).intValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendMessage
 * JD-Core Version:    0.7.0.1
 */