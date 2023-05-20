package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueI18N;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.util.LanguageDefn;
import com.ffusion.beans.util.LanguageDefns;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Applications;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.services.MessagingAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyProduct
  extends Product
  implements Task
{
  protected String autoReplyEmployeeID;
  protected String autoReplyEmail;
  protected String successURL = null;
  protected String taskErrorURL = null;
  protected String serviceErrorURL = null;
  protected int error = 0;
  protected String currentLanguage = null;
  protected HashMap messageQueueI18N = new HashMap();
  protected boolean initialize = false;
  protected boolean validate = false;
  protected boolean process = false;
  private String uV = "MessageQueues";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HashMap localHashMap = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.initialize)
    {
      this.initialize = false;
      initialize(localHttpSession);
      return str1;
    }
    if (this.process)
    {
      this.process = false;
      Product localProduct = null;
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        String str2 = getCurrentLanguage();
        setCurrentLanguage("en_US");
        localProduct = Applications.modifyProduct(localSecureUser, this, localHashMap);
        setCurrentLanguage(str2);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        str1 = this.serviceErrorURL;
        return str1;
      }
      localHttpSession.setAttribute("Product", localProduct);
      MessageQueue localMessageQueue1 = (MessageQueue)localHttpSession.getAttribute("MessageQueue");
      if (localMessageQueue1 == null)
      {
        this.error = 8021;
        return this.taskErrorURL;
      }
      MessageQueue localMessageQueue2 = new MessageQueue();
      localMessageQueue2.set(localMessageQueue1);
      Iterator localIterator = this.messageQueueI18N.keySet().iterator();
      while (localIterator.hasNext())
      {
        localObject = (String)localIterator.next();
        if (localObject != null)
        {
          MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.messageQueueI18N.get(localObject);
          localMessageQueue2.setQueueAutoReplySubject((String)localObject, localMessageQueueI18N.getQueueAutoReplySubject());
          localMessageQueue2.setQueueAutoReplyText((String)localObject, localMessageQueueI18N.getQueueAutoReplyText());
        }
      }
      localMessageQueue2.setQueueAutoReplyEmailAddress(this.autoReplyEmail);
      localMessageQueue2.setQueueAutoReplyEmployeeID(this.autoReplyEmployeeID);
      localHashMap = null;
      Object localObject = (MessagingAdmin)localHttpSession.getAttribute("com.ffusion.services.Messaging3");
      if (localObject != null)
      {
        localHashMap = new HashMap();
        localHashMap.put("SERVICE", localObject);
      }
      try
      {
        localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
        localMessageQueue2 = MessageAdmin.modifyMessageQueue(localSecureUser, localMessageQueue2, localHashMap);
        localHashMap = EntitlementsUtil.removeEntitlementBypass(localHashMap);
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        str1 = this.serviceErrorURL;
        return str1;
      }
      MessageQueues localMessageQueues = (MessageQueues)localHttpSession.getAttribute(this.uV);
      localMessageQueues.setFilter("All");
      MessageQueue localMessageQueue3 = null;
      Statuses localStatuses = (Statuses)localHttpSession.getAttribute("Statuses");
      if (localStatuses == null)
      {
        this.error = 7270;
        return this.taskErrorURL;
      }
      for (int i = 0; i < localStatuses.size(); i++)
      {
        Status localStatus = (Status)localStatuses.get(i);
        if ((localStatus.getID() != null) && (!localStatus.getID().equals("1")))
        {
          localMessageQueue3 = localMessageQueues.getByStatProdID(localStatus.getID(), this.productID);
          if (localMessageQueue3 != null)
          {
            MessageQueue localMessageQueue4 = new MessageQueue();
            localMessageQueue4.set(localMessageQueue3);
            localMessageQueue4.setIsConsumer(localMessageQueue1.getIsConsumer());
            localMessageQueue4.setIsCorporate(localMessageQueue1.getIsCorporate());
            try
            {
              localMessageQueue4 = MessageAdmin.modifyMessageQueue(localSecureUser, localMessageQueue4, localHashMap);
            }
            catch (CSILException localCSILException3)
            {
              this.error = MapError.mapError(localCSILException3);
              str1 = this.serviceErrorURL;
              return str1;
            }
          }
        }
      }
      localHttpSession.setAttribute("MessageQueue", localMessageQueue2);
      ServletContext localServletContext = paramHttpServlet.getServletContext();
      localServletContext.removeAttribute("InActiveMessageQueuesContext");
      localServletContext.removeAttribute("ActiveMessageQueuesContext");
    }
    return str1;
  }
  
  public int validateProduct(HttpSession paramHttpSession)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    LanguageDefns localLanguageDefns = null;
    try
    {
      localLanguageDefns = Util.getLanguageList(localSecureUser, new HashMap());
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return 3;
    }
    int i = 0;
    int j = 0;
    for (int k = 0; k < localLanguageDefns.size(); k++)
    {
      LanguageDefn localLanguageDefn = (LanguageDefn)localLanguageDefns.get(k);
      MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.messageQueueI18N.get(localLanguageDefn.getLanguage());
      if ((getDescription(localLanguageDefn.getLanguage()) != null) && (getDescription(localLanguageDefn.getLanguage()).length() != 0)) {
        i++;
      }
      if ((localMessageQueueI18N != null) && (localMessageQueueI18N.getQueueAutoReplyText() != null) && (localMessageQueueI18N.getQueueAutoReplyText().length() != 0)) {
        j++;
      }
    }
    if (((i != 0) && (i != localLanguageDefns.size())) || ((j != 0) && (j != localLanguageDefns.size()))) {
      return 1;
    }
    return 0;
  }
  
  public void setAutoReplyEmployeeID(String paramString)
  {
    this.autoReplyEmployeeID = paramString;
  }
  
  public void setAutoReplyEmail(String paramString)
  {
    this.autoReplyEmail = paramString;
  }
  
  public void setAutoReplySubject(String paramString)
  {
    MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.messageQueueI18N.get(this.currentLanguage);
    if (localMessageQueueI18N == null) {
      return;
    }
    localMessageQueueI18N.setQueueAutoReplySubject(paramString);
  }
  
  public void setAutoReplyMessage(String paramString)
  {
    MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.messageQueueI18N.get(this.currentLanguage);
    if (localMessageQueueI18N == null) {
      return;
    }
    localMessageQueueI18N.setQueueAutoReplyText(paramString);
  }
  
  public String getAutoReplyEmployeeID()
  {
    return this.autoReplyEmployeeID;
  }
  
  public String getAutoReplyEmail()
  {
    return this.autoReplyEmail;
  }
  
  public String getAutoReplySubject()
  {
    MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.messageQueueI18N.get(this.currentLanguage);
    if (localMessageQueueI18N == null) {
      return "";
    }
    return localMessageQueueI18N.getQueueAutoReplySubject();
  }
  
  public String getAutoReplyMessage()
  {
    MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.messageQueueI18N.get(this.currentLanguage);
    if (localMessageQueueI18N == null) {
      return "";
    }
    return localMessageQueueI18N.getQueueAutoReplyText();
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
  
  public void setCurrentLanguage(String paramString)
  {
    this.currentLanguage = paramString;
    if (!this.messageQueueI18N.containsKey(paramString)) {
      this.messageQueueI18N.put(paramString, new MessageQueueI18N());
    }
  }
  
  public String getCurrentLanguage()
  {
    return this.currentLanguage;
  }
  
  public void setTitle(String paramString)
  {
    if (this.currentLanguage != null) {
      super.setTitle(this.currentLanguage, paramString);
    } else {
      super.setTitle(paramString);
    }
  }
  
  public String getTitle()
  {
    if (this.currentLanguage != null) {
      return super.getTitle(this.currentLanguage);
    }
    return super.getTitle();
  }
  
  public void setDescription(String paramString)
  {
    if (this.currentLanguage != null) {
      super.setDescription(this.currentLanguage, paramString);
    } else {
      super.setDescription(paramString);
    }
  }
  
  public String getDescription()
  {
    if (this.currentLanguage != null) {
      return super.getDescription(this.currentLanguage);
    }
    return super.getDescription();
  }
  
  public void setInitialize(String paramString)
  {
    this.initialize = true;
  }
  
  public void setProcess(String paramString)
  {
    this.process = true;
  }
  
  public void setValidate(String paramString)
  {
    this.validate = true;
  }
  
  protected void initialize(HttpSession paramHttpSession)
  {
    Product localProduct = (Product)paramHttpSession.getAttribute("Product");
    if (localProduct == null) {
      return;
    }
    set(localProduct);
    MessageQueue localMessageQueue = (MessageQueue)paramHttpSession.getAttribute("MessageQueue");
    if (localMessageQueue == null) {
      return;
    }
    setAutoReplyEmail(localMessageQueue.getQueueAutoReplyEmailAddress());
    setAutoReplyEmployeeID(localMessageQueue.getQueueAutoReplyEmployeeID());
    String str1 = getCurrentLanguage();
    setCurrentLanguage("en_US");
    setAutoReplySubject(localMessageQueue.getQueueAutoReplySubject("en_US"));
    setAutoReplyMessage(localMessageQueue.getQueueAutoReplyText("en_US"));
    Iterator localIterator = localMessageQueue.getLanguages();
    if (localIterator != null) {
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        if (str2 != null)
        {
          setCurrentLanguage(str2);
          setAutoReplySubject(localMessageQueue.getQueueAutoReplySubject(str2));
          setAutoReplyMessage(localMessageQueue.getQueueAutoReplyText(str2));
        }
      }
    }
    setCurrentLanguage(str1);
  }
  
  public boolean isAllRequiredValueSpecified(String paramString)
  {
    String str = getTitle(paramString);
    if ((str == null) || (str.length() == 0)) {
      return false;
    }
    MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.messageQueueI18N.get(paramString);
    return ((localMessageQueueI18N == null) || (localMessageQueueI18N.getQueueAutoReplySubject() != null)) && (localMessageQueueI18N.getQueueAutoReplySubject().length() != 0);
  }
  
  public boolean isAnyValueSpecified(String paramString)
  {
    String str1 = getTitle(paramString);
    if ((str1 != null) && (str1.length() != 0)) {
      return true;
    }
    String str2 = getDescription(paramString);
    if ((str2 != null) && (str2.length() != 0)) {
      return true;
    }
    MessageQueueI18N localMessageQueueI18N = (MessageQueueI18N)this.messageQueueI18N.get(paramString);
    if ((localMessageQueueI18N != null) && (localMessageQueueI18N.getQueueAutoReplySubject() != null) && (localMessageQueueI18N.getQueueAutoReplySubject().length() != 0)) {
      return true;
    }
    return (localMessageQueueI18N != null) && (localMessageQueueI18N.getQueueAutoReplyText() != null) && (localMessageQueueI18N.getQueueAutoReplyText().length() != 0);
  }
  
  public void setMessageQueuesSessionName(String paramString)
  {
    this.uV = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.ModifyProduct
 * JD-Core Version:    0.7.0.1
 */