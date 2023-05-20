package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.services.Messaging;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMessages
  extends BaseTask
  implements Task
{
  protected boolean refreshFlag = false;
  protected boolean sentItems = false;
  protected boolean receivedItems = false;
  protected String collectionSessionName = "Messages";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((localHttpSession.getAttribute("com.ffusion.tasks.messages.GeneralMessages") == null) || (this.refreshFlag))
    {
      com.ffusion.beans.messages.Messages localMessages = (com.ffusion.beans.messages.Messages)localHttpSession.getAttribute(this.collectionSessionName);
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      localMessages = new com.ffusion.beans.messages.Messages(localLocale);
      localHttpSession.setAttribute(this.collectionSessionName, localMessages);
      HashMap localHashMap = new HashMap();
      localHashMap.put("MESSAGES", localMessages);
      if (this.sentItems == true) {
        localHashMap.put("SENTMESSAGES", "1");
      } else if (this.receivedItems == true) {
        localHashMap.put("RECEIVEDMESSAGES", "1");
      }
      Messaging localMessaging = (Messaging)localHttpSession.getAttribute("com.ffusion.services.Messaging3");
      if (localMessaging != null) {
        localHashMap.put("SERVICE", localMessaging);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        localMessages = com.ffusion.csil.core.Messages.getMessages(localSecureUser, localHashMap);
        localHttpSession.setAttribute(this.collectionSessionName, localMessages);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0) {
        localHttpSession.setAttribute("com.ffusion.tasks.messages.GeneralMessages", Boolean.TRUE);
      }
    }
    return str;
  }
  
  public void setRefresh(String paramString)
  {
    this.refreshFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getRefresh()
  {
    return String.valueOf(this.refreshFlag);
  }
  
  public void setSentItems(String paramString)
  {
    this.sentItems = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setReceivedItems(String paramString)
  {
    this.receivedItems = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCollectionSessionName(String paramString)
  {
    this.collectionSessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetMessages
 * JD-Core Version:    0.7.0.1
 */