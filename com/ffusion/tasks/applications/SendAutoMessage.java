package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Products;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Applications;
import com.ffusion.services.Messaging;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.messages.SendMessage;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendAutoMessage
  extends SendMessage
  implements Task
{
  protected String successURL;
  protected int error = 0;
  protected String taskErrorURL;
  protected String serviceErrorURL;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    User localUser = (User)localHttpSession.getAttribute("User");
    if (localUser == null)
    {
      this.error = 7386;
      DebugLog.log("SendAutoMessage Task Error: " + this.error);
      return this.taskErrorURL;
    }
    MessageQueues localMessageQueues = (MessageQueues)localHttpSession.getAttribute("MessageQueues");
    if (localMessageQueues == null)
    {
      this.error = 8001;
      DebugLog.log("SendAutoMessage Task Error: " + this.error);
      return this.taskErrorURL;
    }
    Application localApplication = (Application)localHttpSession.getAttribute("Application");
    if (localApplication == null)
    {
      this.error = 7201;
      DebugLog.log("SendAutoMessage Task Error: " + this.error);
      return this.taskErrorURL;
    }
    MessageQueue localMessageQueue = localMessageQueues.getByStatProdID("1", localApplication.getProductID());
    if (localMessageQueue == null)
    {
      this.error = 8021;
      DebugLog.log("SendAutoMessage Task Error: " + this.error);
      return this.taskErrorURL;
    }
    MessageThreads localMessageThreads = (MessageThreads)localHttpSession.getAttribute("MessageThreads");
    if (localMessageThreads == null)
    {
      this.error = 8025;
      DebugLog.log("SendAutoMessage Task Error: " + this.error);
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Products localProducts = (Products)localHttpSession.getAttribute("Products");
    Product localProduct = null;
    if (localProducts == null)
    {
      localProduct = new Product();
      localProducts = new Products();
      localHashMap.put("PRODUCTS", localProducts);
      try
      {
        Applications.getProducts(localSecureUser, localProduct, localHashMap);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        str1 = this.serviceErrorURL;
      }
      if (this.error == 0) {
        localHttpSession.setAttribute("Products", localProducts);
      }
    }
    com.ffusion.beans.messages.Messages localMessages1 = (com.ffusion.beans.messages.Messages)localHttpSession.getAttribute("Messages");
    if (localMessages1 == null)
    {
      this.error = 8001;
      DebugLog.log("SendAutoMessage Task Error: " + this.error);
      return this.taskErrorURL;
    }
    String str2 = localMessageQueue.getQueueAutoReplySubject();
    String str3 = localMessageQueue.getQueueAutoReplyText();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.beans.applications.resources", localLocale);
    if (localResourceBundle == null)
    {
      this.error = 7350;
      DebugLog.log("SendAutoMessage Task Error: " + this.error);
      return this.taskErrorURL;
    }
    String str4 = localResourceBundle.getString("TRACKING_NUMBER_TEXT");
    String str5 = localResourceBundle.getString("PRODUCT_TEXT");
    localProduct = localProducts.getByID(localApplication.getProductID());
    setSubject(str2);
    str3 = str3 + "<br><br>" + str4 + "<br><br>" + localApplication.getTrackingNumber() + "<br>" + str5 + " " + localProduct.getTitle();
    setMemo(str3);
    setTo(localUser.getCustId());
    setFrom(localMessageQueue.getQueueAutoReplyEmployeeID());
    setFromType("0");
    setToType("1");
    MessageThread localMessageThread = localMessageThreads.createNoAdd();
    localHashMap.clear();
    localHashMap.put("OBJECT", localMessageThread);
    localHashMap.put("SERVICE", (Messaging)localHttpSession.getAttribute("com.ffusion.services.Messaging3"));
    try
    {
      localMessageThread = com.ffusion.csil.core.Messages.sendMessageThread(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException2)
    {
      this.error = MapError.mapError(localCSILException2);
      str1 = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localMessages1.add(this);
      com.ffusion.beans.messages.Messages localMessages2 = new com.ffusion.beans.messages.Messages(localLocale);
      localMessages2.add(this);
      localMessageThread.setMessages(localMessages2);
      localMessageThreads.add(localMessageThread);
    }
    return str1;
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.SendAutoMessage
 * JD-Core Version:    0.7.0.1
 */