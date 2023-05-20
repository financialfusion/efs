package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateNewCase
  extends MessageValidate
  implements Task
{
  private Message re = null;
  private String rd = null;
  private boolean rf = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.re.setType(1);
    if (!valueExists(this.re.getTo()))
    {
      this.error = 8004;
      return this.taskErrorURL;
    }
    this.re.setToType(String.valueOf(1));
    if (localSecureUser.getProfileID() <= 0)
    {
      this.error = 8003;
      return this.taskErrorURL;
    }
    this.re.setFrom(localSecureUser.getProfileID());
    this.re.setFromType(String.valueOf(0));
    if (!valueExists(this.rd))
    {
      this.error = 8033;
      return this.taskErrorURL;
    }
    if (!isValidId(this.rd))
    {
      this.error = 8054;
      return this.taskErrorURL;
    }
    if ((this.validate != null) && (!validate(this.re))) {
      return this.taskErrorURL;
    }
    if (!valueExists(this.re.getMemo()))
    {
      this.error = 8007;
      return this.taskErrorURL;
    }
    try
    {
      Messages.createNewCase(localSecureUser, localHashMap, this.re, this.rd, this.rf);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setToID(String paramString)
  {
    this.re.setTo(paramString);
  }
  
  public String getToID()
  {
    return this.re.getTo();
  }
  
  public void setQueueID(String paramString)
  {
    this.rd = paramString;
  }
  
  public void setQueueID(int paramInt)
  {
    this.rd = String.valueOf(paramInt);
  }
  
  public String getQueueID()
  {
    return this.rd;
  }
  
  public int getQueueIDValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.rd);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return i;
  }
  
  public void setSubject(String paramString)
  {
    this.re.setSubject(paramString);
  }
  
  public String getSubject()
  {
    return this.re.getSubject();
  }
  
  public void setMemo(String paramString)
  {
    this.re.setMemo(paramString);
  }
  
  public String getMemo()
  {
    return this.re.getMemo();
  }
  
  public void setComment(String paramString)
  {
    this.re.setComment(paramString);
  }
  
  public String getComment()
  {
    return this.re.getComment();
  }
  
  public void setEmailNotification(boolean paramBoolean)
  {
    this.rf = paramBoolean;
  }
  
  public void setEmailNotification(String paramString)
  {
    this.rf = paramString.equalsIgnoreCase("true");
  }
  
  public boolean getEmailNotificationBoolean()
  {
    return this.rf;
  }
  
  public String getEmailNotification()
  {
    return this.rf == true ? "true" : "false";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.CreateNewCase
 * JD-Core Version:    0.7.0.1
 */