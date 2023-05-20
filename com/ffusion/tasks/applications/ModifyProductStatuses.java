package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueI18N;
import com.ffusion.beans.messages.MessageQueueMembers;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.util.LanguageDefn;
import com.ffusion.beans.util.LanguageDefns;
import com.ffusion.beans.util.StringList;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Applications;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.services.MessagingAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyProductStatuses
  extends BaseTask
  implements Task, com.ffusion.tasks.messages.Task
{
  protected StringList currentStatusIDs = new StringList();
  protected HashMap newStatuses = new HashMap();
  protected Statuses statuses;
  protected Product product;
  protected MessageQueues messageQueues;
  protected int newStatusIdx;
  protected boolean clearListProcessMode = false;
  protected String currentLanguage = null;
  protected HashMap messageQueueI18N = new HashMap();
  protected boolean initialize = false;
  protected boolean validate = false;
  protected boolean process = false;
  protected String validateStatus = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.initialize)
    {
      this.initialize = false;
      initialize(localHttpSession);
      return str;
    }
    if (this.clearListProcessMode)
    {
      this.clearListProcessMode = false;
      StringList localStringList = (StringList)this.newStatuses.get(this.currentLanguage);
      if (localStringList != null) {
        localStringList.clear();
      }
      return str;
    }
    if (this.process)
    {
      this.process = false;
      this.messageQueues = ((MessageQueues)localHttpSession.getAttribute("MessageQueues"));
      if (this.messageQueues == null)
      {
        this.error = 8001;
        return this.taskErrorURL;
      }
      this.statuses = ((Statuses)localHttpSession.getAttribute("Statuses"));
      if (this.statuses == null)
      {
        this.error = 7270;
        return this.taskErrorURL;
      }
      this.product = ((Product)localHttpSession.getAttribute("Product"));
      if (this.product == null)
      {
        this.error = 7252;
        return this.taskErrorURL;
      }
      str = jdMethod_int(localHttpSession);
      if (this.error == 0) {
        str = jdMethod_new(localHttpSession);
      }
    }
    return str;
  }
  
  public int validateStatuses(HttpSession paramHttpSession)
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
    int k = 0;
    int m = 0;
    int n = 0;
    for (int i1 = 0; i1 < localLanguageDefns.size(); i1++)
    {
      LanguageDefn localLanguageDefn = (LanguageDefn)localLanguageDefns.get(i1);
      StringList localStringList = (StringList)this.newStatuses.get(localLanguageDefn.getLanguage());
      if (localStringList != null)
      {
        String str = null;
        if (localStringList.size() >= 1)
        {
          str = (String)localStringList.get(0);
          if ((str != null) && (str.length() != 0)) {
            i++;
          }
        }
        if (localStringList.size() >= 2)
        {
          str = (String)localStringList.get(1);
          if ((str != null) && (str.length() != 0)) {
            j++;
          }
        }
        if (localStringList.size() >= 3)
        {
          str = (String)localStringList.get(2);
          if ((str != null) && (str.length() != 0)) {
            k++;
          }
        }
        if (localStringList.size() >= 4)
        {
          str = (String)localStringList.get(3);
          if ((str != null) && (str.length() != 0)) {
            m++;
          }
        }
        if (localStringList.size() >= 5)
        {
          str = (String)localStringList.get(4);
          if ((str != null) && (str.length() != 0)) {
            n++;
          }
        }
      }
    }
    if (((i != 0) && (i != localLanguageDefns.size())) || ((j != 0) && (j != localLanguageDefns.size())) || ((k != 0) && (k != localLanguageDefns.size())) || ((m != 0) && (m != localLanguageDefns.size())) || ((n != 0) && (n != localLanguageDefns.size()))) {
      return 1;
    }
    return 0;
  }
  
  private String jdMethod_new(HttpSession paramHttpSession)
  {
    HashMap localHashMap = null;
    String str = this.successURL;
    this.statuses.setFilter("PRODUCT_ID=" + this.product.getProductID());
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.beans.applications.resources", localLocale);
    if (localResourceBundle == null)
    {
      this.error = 7350;
      return this.taskErrorURL;
    }
    for (int i = 0; i < 5; i++)
    {
      Status localStatus = new Status();
      localStatus.setBankID(this.product.getBankID());
      localStatus.setProductID(this.product.getProductID());
      int j = 0;
      Iterator localIterator = this.newStatuses.keySet().iterator();
      Object localObject1;
      Object localObject2;
      while (localIterator.hasNext())
      {
        localObject1 = (String)localIterator.next();
        if (localObject1 != null)
        {
          StringList localStringList = (StringList)this.newStatuses.get(localObject1);
          if (localStringList.size() > i)
          {
            localObject2 = (String)localStringList.get(i);
            if ((localObject2 != null) && (((String)localObject2).length() != 0))
            {
              localStatus.setName((String)localObject1, (String)localObject2);
              j = 1;
            }
          }
        }
      }
      if (j != 0)
      {
        localObject1 = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        try
        {
          localStatus = Applications.addStatus((SecureUser)localObject1, localStatus, localHashMap);
        }
        catch (CSILException localCSILException1)
        {
          this.error = MapError.mapError(localCSILException1);
          str = this.serviceErrorURL;
        }
        if (this.error != 0) {
          return str;
        }
        this.statuses.add(localStatus);
        MessageQueue localMessageQueue = new MessageQueue();
        localMessageQueue.setBankId(localStatus.getBankID());
        localMessageQueue.setQueueProductID(localStatus.getProductID());
        localMessageQueue.setQueueStatusID(localStatus.getID());
        localMessageQueue.setQueueType("1");
        localMessageQueue.setQueueName("APP_CENTER");
        localMessageQueue.setQueueProductDesc(this.product.getTitle());
        localObject2 = this.product.getLanguages();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (String)((Iterator)localObject2).next();
          if (localObject3 != null) {
            localMessageQueue.setQueueProductDesc((String)localObject3, this.product.getTitle((String)localObject3));
          }
        }
        Object localObject3 = this.newStatuses.keySet().iterator();
        while (((Iterator)localObject3).hasNext())
        {
          localObject4 = (String)((Iterator)localObject3).next();
          if (localObject4 != null)
          {
            localObject5 = (StringList)this.newStatuses.get(localObject4);
            if (i < ((StringList)localObject5).size()) {
              localMessageQueue.setQueueStatusName((String)localObject4, (String)((StringList)localObject5).get(i));
            }
          }
        }
        localMessageQueue.setQueueAutoReplyEmailAddress(localResourceBundle.getString("EMPLOYEE_EMAIL_ADDRESS"));
        localMessageQueue.setQueueAutoReplySubject(localResourceBundle.getString("EMPLOYEE_EMAIL_SUBJECT"));
        localMessageQueue.setQueueAutoReplyEmployeeID("1");
        localMessageQueue.setQueueAutoReplyText(localResourceBundle.getString("AUTO_RESPONSE_EMAIL_TEXT"));
        Object localObject4 = localMessageQueue.getActiveQueueMembers();
        Object localObject5 = ((MessageQueueMembers)localObject4).create();
        ((BankEmployee)localObject5).setId("1");
        MessagingAdmin localMessagingAdmin = (MessagingAdmin)paramHttpSession.getAttribute("com.ffusion.services.Messaging3");
        localHashMap = null;
        if (localMessagingAdmin != null)
        {
          localHashMap = new HashMap();
          localHashMap.put("SERVICE", localMessagingAdmin);
        }
        try
        {
          localMessageQueue = MessageAdmin.addMessageQueue((SecureUser)localObject1, localMessageQueue, localHashMap);
        }
        catch (CSILException localCSILException2)
        {
          this.error = MapError.mapError(localCSILException2);
          str = this.serviceErrorURL;
        }
        if (this.error == 0)
        {
          this.messageQueues.add(localMessageQueue);
          paramHttpSession.setAttribute("MessageQueues", this.messageQueues);
        }
      }
    }
    return str;
  }
  
  private String jdMethod_int(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    HashMap localHashMap = null;
    this.statuses.setFilter("PRODUCT_ID=" + this.product.getProductID());
    int i = 0;
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.statuses.iterator();
    while (localIterator.hasNext()) {
      if (i < 5)
      {
        localIterator.next();
        i++;
      }
      else
      {
        Status localStatus = (Status)localIterator.next();
        localStatus.setProductID(this.product.getProductID());
        localStatus.setBankID(this.product.getBankID());
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        try
        {
          Applications.deleteStatus(localSecureUser, localStatus, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
        if (this.error == 0)
        {
          localArrayList.add(localStatus);
          MessageQueue localMessageQueue = this.messageQueues.getByStatProdID(localStatus.getID(), this.product.getProductID());
          if (localMessageQueue != null) {
            this.messageQueues.remove(localMessageQueue);
          }
          paramHttpSession.setAttribute("MessageQueues", this.messageQueues);
        }
      }
    }
    i = 0;
    localIterator = localArrayList.iterator();
    while (localIterator.hasNext()) {
      if (i < 5)
      {
        localIterator.next();
        i++;
      }
      else
      {
        this.statuses.remove((Status)localIterator.next());
      }
    }
    return str;
  }
  
  public void setCurrentStatusIDs(String paramString)
  {
    if (paramString.equals("")) {
      return;
    }
    if (paramString.indexOf(",") != -1)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      while (localStringTokenizer.hasMoreTokens()) {
        this.currentStatusIDs.add(localStringTokenizer.nextToken());
      }
    }
    else
    {
      this.currentStatusIDs.add(paramString);
    }
  }
  
  public void setNewStatus(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    StringList localStringList = (StringList)this.newStatuses.get(this.currentLanguage);
    if (paramString.indexOf(",") != -1)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      while (localStringTokenizer.hasMoreTokens()) {
        localStringList.add(localStringTokenizer.nextToken());
      }
    }
    else
    {
      localStringList.add(paramString);
    }
  }
  
  public String getNewStatus()
  {
    StringList localStringList = (StringList)this.newStatuses.get(this.currentLanguage);
    if (localStringList == null) {
      return "";
    }
    if (localStringList.size() > this.newStatusIdx) {
      return (String)localStringList.get(this.newStatusIdx);
    }
    return "";
  }
  
  public void setGetNewStatusIndex(String paramString)
  {
    try
    {
      this.newStatusIdx = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.newStatusIdx = 0;
    }
  }
  
  public void setClearNewStatusList(String paramString)
  {
    this.clearListProcessMode = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCurrentLanguage(String paramString)
  {
    this.currentLanguage = paramString;
    if (!this.messageQueueI18N.containsKey(paramString)) {
      this.messageQueueI18N.put(paramString, new MessageQueueI18N());
    }
    if (!this.newStatuses.containsKey(paramString)) {
      this.newStatuses.put(paramString, new StringList());
    }
  }
  
  public String getCurrentLanguage()
  {
    return this.currentLanguage;
  }
  
  protected void initialize(HttpSession paramHttpSession)
  {
    this.statuses = ((Statuses)paramHttpSession.getAttribute("Statuses"));
    if (this.statuses == null) {
      return;
    }
    this.newStatuses.clear();
    String str1 = getCurrentLanguage();
    for (int i = 5; i < this.statuses.size(); i++)
    {
      Status localStatus = (Status)this.statuses.get(i);
      setCurrentStatusIDs(String.valueOf(localStatus.getID()));
      setCurrentLanguage("en_US");
      setNewStatus(localStatus.getName());
      Iterator localIterator = localStatus.getLanguages();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        setCurrentLanguage(str2);
        setNewStatus(localStatus.getName(str2));
      }
    }
    setCurrentLanguage(str1);
  }
  
  public void setInitialize(String paramString)
  {
    this.initialize = true;
  }
  
  public void setValidate(String paramString)
  {
    this.validate = true;
  }
  
  public void setProcess(String paramString)
  {
    this.process = true;
  }
  
  public String getValidateStatus()
  {
    return this.validateStatus;
  }
  
  public boolean isAllRequiredValueSpecified(String paramString)
  {
    return true;
  }
  
  public boolean isAnyValueSpecified(String paramString)
  {
    StringList localStringList = (StringList)this.newStatuses.get(paramString);
    if (localStringList == null) {
      return false;
    }
    String str = null;
    if (localStringList.size() >= 1)
    {
      str = (String)localStringList.get(0);
      if ((str != null) && (str.length() != 0)) {
        return true;
      }
    }
    if (localStringList.size() >= 2)
    {
      str = (String)localStringList.get(1);
      if ((str != null) && (str.length() != 0)) {
        return true;
      }
    }
    if (localStringList.size() >= 3)
    {
      str = (String)localStringList.get(2);
      if ((str != null) && (str.length() != 0)) {
        return true;
      }
    }
    if (localStringList.size() >= 4)
    {
      str = (String)localStringList.get(3);
      if ((str != null) && (str.length() != 0)) {
        return true;
      }
    }
    if (localStringList.size() >= 5)
    {
      str = (String)localStringList.get(4);
      if ((str != null) && (str.length() != 0)) {
        return true;
      }
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.ModifyProductStatuses
 * JD-Core Version:    0.7.0.1
 */