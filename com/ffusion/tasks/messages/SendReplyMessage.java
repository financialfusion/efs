package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.efs.adapters.profile.Profile;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendReplyMessage
  extends MessageValidate
  implements Task
{
  private String ra = "MessageThread";
  private Message q9 = null;
  private boolean rc = false;
  private boolean rb = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    MessageThread localMessageThread = (MessageThread)localHttpSession.getAttribute(this.ra);
    if (localMessageThread == null)
    {
      this.error = 8024;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.q9.setMsgThreadID(localMessageThread.getThreadID());
    this.q9.setSubject(localMessageThread.getSubject());
    this.q9.setCaseNum(localMessageThread.getCaseNum());
    this.q9.setTo(localMessageThread.getDirectoryID());
    this.q9.setFrom(localSecureUser.getProfileID());
    this.q9.setToType(String.valueOf(1));
    this.q9.setFromType(String.valueOf(0));
    if (!Profile.isValidId(this.q9.getTypeValue()))
    {
      this.error = 8053;
      return this.taskErrorURL;
    }
    if (!valueExists(this.q9.getMemo()))
    {
      this.error = 8007;
      return this.taskErrorURL;
    }
    if ((this.validate != null) && (!validate(this.q9))) {
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    if (this.rb) {
      try
      {
        Messages.sendReplyMessage(localSecureUser, localHashMap, this.q9, this.rc);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setSourceSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.ra = paramString;
    }
  }
  
  public String getSourceSessionKey()
  {
    return this.ra;
  }
  
  public void setEmailNotification(boolean paramBoolean)
  {
    this.rc = paramBoolean;
  }
  
  public void setEmailNotification(String paramString)
  {
    this.rc = paramString.equalsIgnoreCase("true");
  }
  
  public boolean getEmailNotificationBoolean()
  {
    return this.rc;
  }
  
  public String getEmailNotification()
  {
    return this.rc == true ? "true" : "false";
  }
  
  public void setMessageType(String paramString)
  {
    this.q9.setType(paramString);
  }
  
  public String getMessageType()
  {
    return this.q9.getType();
  }
  
  public void setMemo(String paramString)
  {
    this.q9.setMemo(paramString);
  }
  
  public String getMemo()
  {
    return this.q9.getMemo();
  }
  
  public void setComment(String paramString)
  {
    this.q9.setComment(paramString);
  }
  
  public String getComment()
  {
    return this.q9.getComment();
  }
  
  public void setProcess(String paramString)
  {
    this.rb = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendReplyMessage
 * JD-Core Version:    0.7.0.1
 */