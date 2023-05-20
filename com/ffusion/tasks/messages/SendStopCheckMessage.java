package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.stoppayments.StopCheck;
import com.ffusion.beans.stoppayments.StopChecks;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.efs.adapters.profile.MessageQueueAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendStopCheckMessage
  extends SendMessage
{
  private String qX;
  private String qV;
  private StopCheck qW;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    setLocale((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if (this.initFlag) {
      initProcess(paramHttpServlet.getServletContext(), localHttpSession);
    }
    if ((this.error == 0) && (validateInput(localHttpSession)))
    {
      if ((getMemo() != null) && (getMemo().length() > this.maxSize))
      {
        this.error = 8077;
        str = this.taskErrorURL;
        return str;
      }
      if (this.processFlag) {
        str = sendMessage(localHttpSession);
      } else {
        str = this.successURL;
      }
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String sendMessage(HttpSession paramHttpSession)
  {
    String str = this.taskErrorURL;
    this.error = 0;
    if (this.processFlag)
    {
      this.processFlag = false;
      HashMap localHashMap = new HashMap();
      localHashMap.put("SERVICE", paramHttpSession.getAttribute("com.ffusion.services.Messaging3"));
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      try
      {
        Messages.sendMessage(localSecureUser, this, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0) {
        str = this.successURL;
      }
    }
    return str;
  }
  
  protected void initProcess(ServletContext paramServletContext, HttpSession paramHttpSession)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    String str = localSecureUser.getLocaleLanguage();
    Locale localLocale = null;
    UserLocale localUserLocale = (UserLocale)paramHttpSession.getAttribute("UserLocale");
    localLocale = localUserLocale.getLocale();
    this.initFlag = false;
    if (this.qX == null)
    {
      this.error = 8055;
    }
    else
    {
      StopChecks localStopChecks = (StopChecks)paramHttpSession.getAttribute(this.qV);
      if (localStopChecks == null)
      {
        this.error = 8056;
      }
      else
      {
        this.qW = localStopChecks.getByID(this.qX);
        if (this.qW == null)
        {
          this.error = 8057;
        }
        else
        {
          MessageQueue localMessageQueue = jdMethod_byte("14", localSecureUser.getBankID());
          if (localMessageQueue == null)
          {
            this.error = 8021;
          }
          else
          {
            setToName(localMessageQueue.getQueueName(str));
            setTo(localMessageQueue.getQueueID());
            setToType("QUEUE");
            setSubject(localMessageQueue.getQueueName(str));
            setComment(this.qW.getInquireComment(getSubject()));
          }
        }
      }
    }
  }
  
  public void setStopID(String paramString)
  {
    this.qX = paramString;
  }
  
  public void setCollectionName(String paramString)
  {
    this.qV = paramString;
  }
  
  public String getAccountID()
  {
    if (this.qW == null) {
      return null;
    }
    return this.qW.getAccountID();
  }
  
  public String getAmount()
  {
    if (this.qW == null) {
      return null;
    }
    return this.qW.getAmount();
  }
  
  public String getReason()
  {
    if (this.qW == null) {
      return null;
    }
    return this.qW.getReason();
  }
  
  private static MessageQueues jdMethod_case(String paramString1, String paramString2)
  {
    MessageQueue localMessageQueue = new MessageQueue();
    localMessageQueue.setBankId(paramString2);
    localMessageQueue.setQueueID(paramString1);
    localMessageQueue.setQueueType("0");
    MessageQueues localMessageQueues;
    try
    {
      localMessageQueues = MessageQueueAdapter.getQueues(localMessageQueue);
    }
    catch (ProfileException localProfileException)
    {
      return null;
    }
    return localMessageQueues;
  }
  
  private MessageQueue jdMethod_byte(String paramString1, String paramString2)
  {
    MessageQueue localMessageQueue = null;
    MessageQueues localMessageQueues = jdMethod_case(paramString1, paramString2);
    if (localMessageQueues == null)
    {
      this.error = 8020;
    }
    else
    {
      localMessageQueue = localMessageQueues.getByID(paramString1);
      if (localMessageQueue == null) {
        this.error = 8021;
      }
    }
    return localMessageQueue;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendStopCheckMessage
 * JD-Core Version:    0.7.0.1
 */