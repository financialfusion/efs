package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Products;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Applications;
import com.ffusion.csil.core.Messages;
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

public class SendAutoEmail
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
    MessageQueues localMessageQueues = (MessageQueues)localHttpSession.getAttribute("MessageQueues");
    if (localMessageQueues == null)
    {
      this.error = 8001;
      DebugLog.log("SendAutoEmail Task Error: " + this.error);
      return this.taskErrorURL;
    }
    Application localApplication = (Application)localHttpSession.getAttribute("Application");
    if (localApplication == null)
    {
      this.error = 7201;
      DebugLog.log("SendAutoEmail Task Error: " + this.error);
      return this.taskErrorURL;
    }
    MessageQueue localMessageQueue = localMessageQueues.getByStatProdID("1", localApplication.getProductID());
    if (localMessageQueue == null)
    {
      this.error = 8021;
      DebugLog.log("SendAutoEmail Task Error: " + this.error);
      return this.taskErrorURL;
    }
    Products localProducts = (Products)localHttpSession.getAttribute("Products");
    Product localProduct = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localProducts == null)
    {
      localProduct = new Product();
      localProducts = new Products();
      localHashMap.put("PRODUCTS", localProducts);
      try
      {
        localProducts = Applications.getProducts(localSecureUser, localProduct, localHashMap);
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
    String str2 = localApplication.getEmailAddress();
    if ((str2 == null) || (str2.equals("")))
    {
      this.error = 7386;
      DebugLog.log("SendAutoEmail Task Error: " + this.error);
      return this.taskErrorURL;
    }
    String str3 = localMessageQueue.getQueueAutoReplySubject();
    String str4 = localMessageQueue.getQueueAutoReplyText();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.beans.applications.resources", localLocale);
    if (localResourceBundle == null)
    {
      this.error = 7350;
      DebugLog.log("SendAutoEmail Task Error: " + this.error);
      return this.taskErrorURL;
    }
    String str5 = localResourceBundle.getString("TRACKING_NUMBER_TEXT");
    String str6 = localResourceBundle.getString("PRODUCT_TEXT");
    localProduct = localProducts.getByID(localApplication.getProductID());
    setSubject(str3);
    setMemo(str4);
    String str7 = localMessageQueue.getQueueAutoReplyEmailAddress();
    setTo(str2);
    setFrom(str7);
    setMemo(str4 + "\r\n\r\n" + str5 + "\r\n\r\n" + localApplication.getTrackingNumber() + "\r\n" + str6 + " " + localProduct.getTitle());
    localHashMap = null;
    Messaging localMessaging = (Messaging)localHttpSession.getAttribute("com.ffusion.tasks.messages.EmailMessages");
    if (localMessaging != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localMessaging);
    }
    try
    {
      Messages.sendMessage(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException2)
    {
      this.error = MapError.mapError(localCSILException2);
      str1 = this.serviceErrorURL;
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
 * Qualified Name:     com.ffusion.tasks.applications.SendAutoEmail
 * JD-Core Version:    0.7.0.1
 */