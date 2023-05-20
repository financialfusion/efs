package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueMembers;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.util.LanguageDefn;
import com.ffusion.beans.util.LanguageDefns;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyMessageQueue
  extends MessageQueue
  implements Task
{
  protected static String QUEUENAME = "QUEUENAME";
  protected static String AUTOMESSAGESUBJECT = "AUTOMESSAGESUBJECT";
  protected static String AUTOMESSAGETEXT = "AUTOMESSAGETEXT";
  protected static String QUEUEMEMBERS = "QUEUEMEMBERS";
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String warningURL = null;
  protected int error = 0;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  private String qH = "TempMessageQueueActiveMembers";
  private String qF = "TempMessageQueueInactiveMembers";
  private static final String qG = "";
  
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
    else
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      LanguageDefns localLanguageDefns = null;
      try
      {
        localLanguageDefns = Util.getLanguageList(localSecureUser, new HashMap());
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      str = validateInput(localLanguageDefns, localHttpSession, paramHttpServletResponse);
      if ((str != null) && (!str.equals(this.successURL))) {
        return str;
      }
      if (this.processFlag)
      {
        if (modifyQueue(localHttpSession))
        {
          str = this.successURL;
          ServletContext localServletContext = paramHttpServlet.getServletContext();
          localServletContext.removeAttribute("InActiveMessageQueuesContext");
          localServletContext.removeAttribute("ActiveMessageQueuesContext");
        }
        else
        {
          str = this.serviceErrorURL;
        }
      }
      else {
        str = this.successURL;
      }
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    MessageQueue localMessageQueue = (MessageQueue)paramHttpSession.getAttribute("MessageQueue");
    if (localMessageQueue == null)
    {
      this.error = 8021;
      return false;
    }
    set(localMessageQueue);
    return true;
  }
  
  protected boolean modifyQueue(HttpSession paramHttpSession)
  {
    this.processFlag = false;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      MessageAdmin.modifyMessageQueue(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      paramHttpSession.setAttribute("MessageQueue", this);
      return true;
    }
    return false;
  }
  
  protected String validateInput(LanguageDefns paramLanguageDefns, HttpSession paramHttpSession, HttpServletResponse paramHttpServletResponse)
  {
    this.error = 0;
    if (this.validate != null)
    {
      int i = 0;
      int j = 0;
      int k = 0;
      int m = 0;
      int n = 0;
      if ((paramLanguageDefns == null) || (paramLanguageDefns.size() <= 0))
      {
        this.error = 8080;
        return this.taskErrorURL;
      }
      Object localObject2;
      Object localObject3;
      Object localObject1;
      if (paramLanguageDefns.size() > 1)
      {
        for (int i1 = 0; i1 < paramLanguageDefns.size(); i1++)
        {
          localObject2 = (LanguageDefn)paramLanguageDefns.get(i1);
          localObject3 = ((LanguageDefn)localObject2).getLanguage();
          int i3 = 0;
          int i4 = 0;
          int i5 = 0;
          int i6 = 0;
          int i7 = 0;
          MessageQueues localMessageQueues = (MessageQueues)paramHttpSession.getAttribute("MessageQueues");
          MessageQueue localMessageQueue2 = localMessageQueues.getByName((String)localObject3, getQueueName((String)localObject3));
          if ((localMessageQueue2 != null) && (localMessageQueue2.getId() != getId()))
          {
            this.error = 8058;
            return this.taskErrorURL;
          }
          if ((getQueueName((String)localObject3) != null) && (getQueueName((String)localObject3).length() != 0))
          {
            i4 = 1;
            i++;
          }
          if ((getQueueAutoReplySubject((String)localObject3) != null) && (getQueueAutoReplySubject((String)localObject3).length() != 0))
          {
            i5 = 1;
            m++;
          }
          if ((getQueueAutoReplyText((String)localObject3) != null) && (getQueueAutoReplyText((String)localObject3).length() != 0))
          {
            i6 = 1;
            k++;
          }
          if ((getDescription((String)localObject3) != null) && (getDescription((String)localObject3).length() != 0))
          {
            i7 = 1;
            j++;
          }
          if ((i4 != 0) && (i5 != 0))
          {
            i3 = 1;
            n = 1;
          }
          if (((i4 != 0) || (i5 != 0) || (i6 != 0) || (i7 != 0)) && (i3 == 0))
          {
            this.error = 8078;
            return this.taskErrorURL;
          }
        }
        if (n == 0)
        {
          this.error = 8079;
          return this.taskErrorURL;
        }
      }
      else
      {
        localObject1 = (LanguageDefn)paramLanguageDefns.get(0);
        localObject2 = ((LanguageDefn)localObject1).getLanguage();
        localObject3 = (MessageQueues)paramHttpSession.getAttribute("MessageQueues");
        MessageQueue localMessageQueue1 = ((MessageQueues)localObject3).getByName((String)localObject2, getQueueName((String)localObject2));
        if ((localMessageQueue1 != null) && (localMessageQueue1.getId() != getId()))
        {
          this.error = 8058;
          return this.taskErrorURL;
        }
        if ((this.validate.indexOf(QUEUENAME) != -1) && ((getQueueName((String)localObject2) == null) || (getQueueName((String)localObject2).length() == 0)))
        {
          this.error = 8036;
          return this.taskErrorURL;
        }
        if ((this.validate.indexOf(AUTOMESSAGESUBJECT) != -1) && ((getQueueAutoReplySubject((String)localObject2) == null) || (getQueueAutoReplySubject((String)localObject2).length() == 0)))
        {
          this.error = 8037;
          return this.taskErrorURL;
        }
        if ((this.validate.indexOf(AUTOMESSAGETEXT) != -1) && ((getQueueAutoReplyText((String)localObject2) == null) || (getQueueAutoReplyText((String)localObject2).length() == 0)))
        {
          this.error = 8038;
          return this.taskErrorURL;
        }
      }
      if ((!getIsCorporate()) && (!getIsConsumer()))
      {
        this.error = 8081;
        return this.taskErrorURL;
      }
      if (this.validate.indexOf(QUEUEMEMBERS) != -1)
      {
        localObject1 = (MessageQueueMembers)paramHttpSession.getAttribute(this.qH);
        localObject2 = (MessageQueueMembers)paramHttpSession.getAttribute(this.qF);
        if ((localObject1 != null) && (localObject2 != null))
        {
          int i2 = ((MessageQueueMembers)localObject1).size() + ((MessageQueueMembers)localObject2).size();
          if (i2 == 0)
          {
            this.error = 8028;
            return this.taskErrorURL;
          }
        }
      }
      if (((i > 0) && (i < paramLanguageDefns.size())) || ((m > 0) && (m < paramLanguageDefns.size())) || ((j > 0) && (j < paramLanguageDefns.size())) || ((k > 0) && (k < paramLanguageDefns.size())))
      {
        try
        {
          paramHttpServletResponse.sendRedirect(this.warningURL);
        }
        catch (Exception localException) {}
        this.validate = null;
        return this.warningURL;
      }
      this.validate = null;
    }
    return this.successURL;
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
  
  public void setWarningURL(String paramString)
  {
    this.warningURL = paramString;
  }
  
  public void setQueueAutoReplyText(String paramString)
  {
    if ("".equals(paramString)) {
      paramString = null;
    }
    super.setQueueAutoReplyText(paramString);
  }
  
  public void setIsConsumer(String paramString)
  {
    setIsConsumer(new Boolean(paramString).booleanValue());
  }
  
  public void setQueueAutoReplySubject(String paramString)
  {
    if ("".equals(paramString)) {
      paramString = null;
    }
    super.setQueueAutoReplySubject(paramString);
  }
  
  public void setDescription(String paramString)
  {
    if ("".equals(paramString)) {
      paramString = null;
    }
    super.setDescription(paramString);
  }
  
  public void setQueueName(String paramString)
  {
    if ("".equals(paramString)) {
      paramString = null;
    }
    super.setQueueName(paramString);
  }
  
  public void setIsCorporate(String paramString)
  {
    setIsCorporate(new Boolean(paramString).booleanValue());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.ModifyMessageQueue
 * JD-Core Version:    0.7.0.1
 */