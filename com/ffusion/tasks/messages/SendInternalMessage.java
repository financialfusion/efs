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

public class SendInternalMessage
  extends MessageValidate
  implements Task
{
  private String rg = "MessageThread";
  private Message rk = null;
  private boolean ri = false;
  private String rj = null;
  private boolean rh = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    MessageThread localMessageThread = (MessageThread)localHttpSession.getAttribute(this.rg);
    if (localMessageThread == null)
    {
      this.error = 8024;
      return this.taskErrorURL;
    }
    this.rk.setMsgThreadID(localMessageThread.getThreadID());
    this.rk.setSubject(localMessageThread.getSubject());
    this.rk.setCaseNum(localMessageThread.getCaseNum());
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.rk.setFrom(localSecureUser.getProfileID());
    this.rk.setFromType(String.valueOf(0));
    if (!Profile.isValidId(this.rk.getTypeValue()))
    {
      this.error = 8053;
      return this.taskErrorURL;
    }
    if (this.ri)
    {
      if (!valueExists(this.rj))
      {
        this.error = 8085;
        return this.taskErrorURL;
      }
      this.rk.setComment(this.rj);
      this.rk.setMemo("");
    }
    else
    {
      if (!valueExists(this.rj))
      {
        this.error = 8007;
        return this.taskErrorURL;
      }
      this.rk.setMemo(this.rj);
      this.rk.setComment("");
    }
    if (this.rh)
    {
      HashMap localHashMap = new HashMap();
      try
      {
        Messages.sendInternalMessage(localSecureUser, localHashMap, this.rk);
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
      this.rg = paramString;
    }
  }
  
  public String getSourceSessionKey()
  {
    return this.rg;
  }
  
  public void setSaveAsComment(boolean paramBoolean)
  {
    this.ri = paramBoolean;
  }
  
  public void setSaveAsComment(String paramString)
  {
    this.ri = paramString.equalsIgnoreCase("true");
  }
  
  public boolean getSaveAsCommentBoolean()
  {
    return this.ri;
  }
  
  public String getSaveAsComment()
  {
    return this.ri == true ? "true" : "false";
  }
  
  public void setMessageType(String paramString)
  {
    this.rk.setType(paramString);
  }
  
  public String getMessageType()
  {
    return this.rk.getType();
  }
  
  public void setToId(String paramString)
  {
    this.rk.setTo(paramString);
  }
  
  public String getToId()
  {
    return this.rk.getTo();
  }
  
  public void setText(String paramString)
  {
    this.rj = paramString;
  }
  
  public String getText()
  {
    return this.rj;
  }
  
  public void setProcess(String paramString)
  {
    this.rh = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendInternalMessage
 * JD-Core Version:    0.7.0.1
 */