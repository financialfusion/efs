package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendEMail
  extends BaseTask
  implements Task
{
  protected static String FROM = "FROM";
  protected static String TO = "TO";
  protected static String SUBJECT = "SUBJECT";
  protected static String MEMO = "MEMO";
  protected String validate;
  protected boolean processFlag = false;
  protected String from = Messages.getNotificationFromEmail();
  protected String to = "";
  protected String subject = "";
  protected String memo = "";
  protected int maxSize = 1024;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (getMemo().length() > this.maxSize)
    {
      this.error = 8077;
      str = this.taskErrorURL;
      return str;
    }
    if (validateInput(localHttpSession))
    {
      if (this.processFlag) {
        str = sendEMail(localHttpSession);
      } else {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String sendEMail(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.error = 0;
    this.processFlag = false;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if ((this.memo == null) || (this.memo.trim().length() == 0))
    {
      this.error = 8007;
      return this.taskErrorURL;
    }
    try
    {
      Messages.sendEmail(localSecureUser, this.to, this.from, this.subject, this.memo);
    }
    catch (CSILException localCSILException)
    {
      paramHttpSession.setAttribute("EmailSent", "false");
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
      if ((this.validate.indexOf(FROM) != -1) && ((this.from == null) || (this.from.length() == 0))) {
        this.error = 8003;
      }
      if ((this.error == 0) && (this.validate.indexOf(TO) != -1) && ((this.to == null) || (this.to.length() == 0))) {
        this.error = 8004;
      }
      if ((this.error == 0) && (this.validate.indexOf(SUBJECT) != -1) && ((this.subject == null) || (this.subject.length() == 0) || (this.subject.length() > 100))) {
        this.error = 8005;
      }
      if ((this.error == 0) && (this.validate.indexOf(MEMO) != -1) && ((this.memo == null) || (this.memo.length() == 0))) {
        this.error = 8007;
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
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setFrom(String paramString)
  {
    this.from = paramString;
  }
  
  public String getFrom()
  {
    return this.from;
  }
  
  public void setTo(String paramString)
  {
    this.to = paramString;
  }
  
  public String getTo()
  {
    return this.to;
  }
  
  public void setSubject(String paramString)
  {
    this.subject = paramString;
  }
  
  public String getSubject()
  {
    return this.subject;
  }
  
  public void setMemo(String paramString)
  {
    this.memo = paramString;
  }
  
  public String getMemo()
  {
    return this.memo;
  }
  
  public void setMaxMessageSize(String paramString)
  {
    this.maxSize = Integer.valueOf(paramString).intValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendEMail
 * JD-Core Version:    0.7.0.1
 */