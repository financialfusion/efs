package com.ffusion.alert.plugins;

import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.plugins.message.Message;
import com.ffusion.alert.plugins.message.MessageList;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.ejb.ICSILEJB;
import com.ffusion.util.ResourceUtil;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

public class BankMessage
  extends BaseAlert
  implements IAEChannel, AlertChannelConstants
{
  public static final String THIS_PLUGIN_NAME = "BankMessage";
  private static final String aSI = "MESSAGETHREADS";
  protected static final int DEFAULT_ALERT_PAGE_SIZE = 200;
  protected static final int DEFAULT_NUM_WORKER_THREADS = 20;
  protected int alertPageSize = 200;
  protected int numWorkerThreads = 20;
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    logEntry("Attempting to set context parameters from properties file");
    setUpEnvironment(paramProperties, paramIAEAlertEngine, paramPrintWriter);
    logEntry("Completed connect to CSIL EJB ... Now ending Initializing BankMessage plugin...");
    getWorkerThreadsAndPageSizeInfo(paramProperties);
  }
  
  protected MessageList initializeMessages(Locale paramLocale)
  {
    MessageList localMessageList = new MessageList();
    localMessageList.setSubject(ResourceUtil.getString("subjectBankMsg", "com.ffusion.beans.alerts.resources", paramLocale));
    return localMessageList;
  }
  
  protected void setMessageContent(MessageList paramMessageList, int paramInt, Locale paramLocale)
  {
    if (paramMessageList != null)
    {
      Object[] arrayOfObject = { Integer.toString(paramInt) };
      paramMessageList.getSecureMessage().addContent(MessageFormat.format(ResourceUtil.getString("bodyBankSecureMsg", "com.ffusion.beans.alerts.resources", paramLocale), arrayOfObject));
      paramMessageList.getUnsecureMessage().addContent(MessageFormat.format(ResourceUtil.getString("bodyBankUnSecureMsg", "com.ffusion.beans.alerts.resources", paramLocale), arrayOfObject));
    }
  }
  
  protected void processBankMessages(IAEAlertDefinition paramIAEAlertDefinition, int paramInt, CSILEJBWrapper paramCSILEJBWrapper, SecureUser paramSecureUser)
    throws CreateException, NamingException, RemoteException, EJBException, CSILException, Exception
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("MESSAGETHREADS", new MessageThreads(Locale.getDefault()));
    logEntry("Now Making CSIL call to getNumberOfUnreadMessages...");
    int i = 0;
    try
    {
      i = paramCSILEJBWrapper.getEJB().getNumberOfUnreadMessagesExcludingAlerts(paramSecureUser, localHashMap);
    }
    catch (RemoteException localRemoteException)
    {
      paramCSILEJBWrapper.reconnect();
      i = paramCSILEJBWrapper.getEJB().getNumberOfUnreadMessagesExcludingAlerts(paramSecureUser, localHashMap);
    }
    logEntry("Number of messages from CSIL EJB call is: " + i);
    MessageList localMessageList = null;
    if (i > 0)
    {
      localMessageList = initializeMessages(paramSecureUser.getLocale());
      setMessageContent(localMessageList, i, paramSecureUser.getLocale());
      jdMethod_for(paramIAEAlertDefinition, paramInt, localMessageList);
    }
  }
  
  protected void getWorkerThreadsAndPageSizeInfo(Properties paramProperties)
  {
    String str1 = paramProperties.getProperty("APP_ALERTS_PAGE_SIZE");
    String str2 = paramProperties.getProperty("NUM_PROCESSING_THREADS");
    if (str1 != null) {
      try
      {
        this.alertPageSize = Integer.parseInt(str1);
      }
      catch (Exception localException1)
      {
        this.alertPageSize = 200;
      }
    }
    if (str2 != null) {
      try
      {
        this.numWorkerThreads = Integer.parseInt(str2);
      }
      catch (Exception localException2)
      {
        this.numWorkerThreads = 20;
      }
    }
    logEntry("alert page size : " + this.alertPageSize + " worker thread count: " + this.numWorkerThreads);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    logEntry("Processing processAlert method...");
    int i = 0;
    try
    {
      int j = 0;
      AEApplicationInfo localAEApplicationInfo = getAppInfo();
      IAEAlertDefinition[] arrayOfIAEAlertDefinition = null;
      int k = 0;
      logEntry("Attempting re-connection to CSIL EJB (if necessary)... ");
      reconnectToCsil(true);
      b localb = new b(this.numWorkerThreads);
      ArrayList localArrayList = new ArrayList();
      localb.jdMethod_if(localArrayList);
      createEJBs(this.numWorkerThreads);
      synchronized (localb)
      {
        localb.a(getCsilInstances());
      }
      while (k == 0)
      {
        arrayOfIAEAlertDefinition = getEngine().getAppAlertsForChannelPaged(localAEApplicationInfo, true, getPluginName(), j, this.alertPageSize);
        synchronized (localArrayList)
        {
          if (localArrayList.size() <= 0) {}
        }
        if ((arrayOfIAEAlertDefinition == null) || ((arrayOfIAEAlertDefinition != null) && (arrayOfIAEAlertDefinition.length == 0))) {
          break;
        }
        synchronized (localb)
        {
          localb.a(arrayOfIAEAlertDefinition);
        }
        j = arrayOfIAEAlertDefinition[(arrayOfIAEAlertDefinition.length - 1)].getId();
        if (arrayOfIAEAlertDefinition.length < this.alertPageSize) {
          k = 1;
        }
      }
      synchronized (localArrayList)
      {
        if (localArrayList.size() <= 0) {}
      }
      synchronized (localb) {}
    }
    catch (CreateException localCreateException)
    {
      logEntry(localCreateException);
      i = -1003;
    }
    catch (NamingException localNamingException)
    {
      logEntry(localNamingException);
      i = -1002;
    }
    catch (RemoteException localRemoteException)
    {
      logEntry(localRemoteException);
      i = -1001;
    }
    catch (EJBException localEJBException)
    {
      logEntry(localEJBException);
      i = -1000;
    }
    catch (CSILException localCSILException)
    {
      logEntry(localCSILException);
      i = -1004;
    }
    catch (Exception localException)
    {
      logEntry(localException);
      i = -2000;
    }
    finally
    {
      cleaupCSILEJBs();
    }
    return i;
  }
  
  private void jdMethod_for(IAEAlertDefinition paramIAEAlertDefinition, int paramInt, MessageList paramMessageList)
  {
    logEntry("Creating Alert");
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo1 = paramIAEAlertDefinition.getDeliveryInfo();
    ArrayList localArrayList = new ArrayList(arrayOfIAEDeliveryInfo1.length - 1);
    int i = 0;
    for (int j = 0; i < arrayOfIAEDeliveryInfo1.length - 1; j++)
    {
      if (i == paramInt) {
        j++;
      }
      IAEDeliveryInfo localIAEDeliveryInfo = arrayOfIAEDeliveryInfo1[j];
      Properties localProperties = localIAEDeliveryInfo.getDeliveryProperties();
      AlertUtil.setProperty(localProperties, "subject", paramMessageList.getSecureMessage().getSubject());
      boolean bool = Boolean.valueOf(localIAEDeliveryInfo.getDeliveryProperties().getProperty("secure")).booleanValue();
      if (bool) {
        AlertUtil.setProperty(localProperties, "body", paramMessageList.getSecureMessage().getContent());
      } else {
        AlertUtil.setProperty(localProperties, "body", paramMessageList.getUnsecureMessage().getContent());
      }
      localIAEDeliveryInfo.setDeliveryProperties(localProperties);
      localArrayList.add(localIAEDeliveryInfo);
      i++;
    }
    try
    {
      IAEDeliveryInfo[] arrayOfIAEDeliveryInfo2 = (IAEDeliveryInfo[])localArrayList.toArray(new IAEDeliveryInfo[0]);
      j = getEngine().createAlert(getAppInfo(), paramIAEAlertDefinition.getUserId(), 0, 1, null, arrayOfIAEDeliveryInfo2, paramMessageList.getUnsecureMessage().getContent());
      logEntry("New alertID to secure and unsecure channels: " + j);
    }
    catch (Exception localException)
    {
      logEntry(localException, "Exception while creating alert: " + localException.getLocalizedMessage());
    }
  }
  
  public void stop()
  {
    logEntry("Stopping BankMessage plugin...");
  }
  
  protected String getPluginName()
  {
    return "BankMessage";
  }
  
  protected int getPluginLocation(IAEAlertDefinition paramIAEAlertDefinition, String paramString1, String paramString2)
  {
    int i = -1;
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = paramIAEAlertDefinition.getDeliveryInfo();
    for (int j = 0; j < arrayOfIAEDeliveryInfo.length; j++)
    {
      IAEDeliveryInfo localIAEDeliveryInfo = arrayOfIAEDeliveryInfo[j];
      if (((paramIAEAlertDefinition.getUserId() == null) || (!paramIAEAlertDefinition.getUserId().equalsIgnoreCase(getPluginName() + paramString2))) && (localIAEDeliveryInfo.getDeliveryChannelName().equals(paramString1)))
      {
        i = j;
        break;
      }
    }
    return i;
  }
  
  protected void reconnectToCsil(boolean paramBoolean)
    throws NamingException, RemoteException, CreateException, EJBException, CSILException
  {
    synchronized (this)
    {
      if ((paramBoolean) || (getCsilInstance() == null))
      {
        setCsilInstance(null);
        setCsilInstance(AlertUtil.connectToCSILEJB(getInitProps()));
      }
    }
  }
  
  public void doProcessAlert(int paramInt1, int paramInt2, CSILEJBWrapper paramCSILEJBWrapper, IAEAlertDefinition[] paramArrayOfIAEAlertDefinition)
  {
    SecureUser localSecureUser = new SecureUser();
    for (int i = paramInt1; i < paramInt2; i++) {
      try
      {
        int j = getPluginLocation(paramArrayOfIAEAlertDefinition[i], getPluginName());
        if (j != -1)
        {
          Properties localProperties = paramArrayOfIAEAlertDefinition[i].getDeliveryInfo()[j].getDeliveryProperties();
          localSecureUser.setXML(localProperties.getProperty("SECUREUSER"));
          String str = localProperties.getProperty("PREFERREDLANGUAGE");
          if ((str != null) && (str.trim().length() > 0)) {
            localSecureUser.setLocale(str);
          }
          processBankMessages(paramArrayOfIAEAlertDefinition[i], j, paramCSILEJBWrapper, localSecureUser);
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  private class a
    extends Thread
  {
    private int jdField_do = -1;
    private int jdField_int = -1;
    private CSILEJBWrapper a = null;
    private IAEAlertDefinition[] jdField_if = null;
    private BankMessage.b jdField_for = null;
    
    public a(CSILEJBWrapper paramCSILEJBWrapper)
    {
      this(paramCSILEJBWrapper, null);
    }
    
    public a(CSILEJBWrapper paramCSILEJBWrapper, BankMessage.b paramb)
    {
      this.a = paramCSILEJBWrapper;
      this.jdField_for = paramb;
    }
    
    void a(BankMessage.b paramb)
    {
      this.jdField_for = paramb;
    }
    
    public void run()
    {
      try
      {
        for (;;)
        {
          if ((this.jdField_if == null) || (this.jdField_if.length <= 0)) {
            synchronized (this.jdField_for)
            {
              this.jdField_for.wait();
            }
          }
          BankMessage.this.doProcessAlert(this.jdField_do, this.jdField_int, this.a, this.jdField_if);
          this.jdField_if = null;
          synchronized (this.jdField_for)
          {
            this.jdField_for.a();
            this.jdField_for.wait();
          }
        }
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        this.jdField_for = null;
        this.jdField_if = null;
      }
    }
    
    public void a(int paramInt1, int paramInt2, IAEAlertDefinition[] paramArrayOfIAEAlertDefinition)
    {
      this.jdField_do = paramInt1;
      this.jdField_int = paramInt2;
      this.jdField_if = ((IAEAlertDefinition[])paramArrayOfIAEAlertDefinition.clone());
    }
    
    public void finalize()
      throws Throwable
    {
      this.a = null;
      this.jdField_if = null;
      this.jdField_for = null;
      super.finalize();
    }
  }
  
  private class b
  {
    private BankMessage.a[] jdField_if = null;
    private int a = 0;
    private int jdField_for = 0;
    private ArrayList jdField_do = null;
    
    public b(int paramInt)
    {
      this.a = paramInt;
    }
    
    void jdField_if(ArrayList paramArrayList)
    {
      this.jdField_do = paramArrayList;
    }
    
    public void a(ArrayList paramArrayList)
    {
      this.jdField_if = new BankMessage.a[this.a];
      for (int i = 0; i < this.a; i++)
      {
        this.jdField_if[i] = new BankMessage.a(BankMessage.this, (CSILEJBWrapper)paramArrayList.get(i), this);
        this.jdField_if[i].start();
      }
    }
    
    public void a(IAEAlertDefinition[] paramArrayOfIAEAlertDefinition)
    {
      this.jdField_for = 0;
      int i = paramArrayOfIAEAlertDefinition.length;
      int j = i / this.a;
      if (j <= 0)
      {
        this.jdField_for = 1;
        this.jdField_if[0].a(0, i, paramArrayOfIAEAlertDefinition);
      }
      else
      {
        if (i % this.a != 0) {
          j++;
        }
        this.jdField_for = this.a;
        int k = 0;
        int m = 0;
        for (int n = 0; n < this.a; n++)
        {
          k = m;
          m += j;
          if (m > i) {
            m = i;
          }
          this.jdField_if[n].a(k, m, paramArrayOfIAEAlertDefinition);
        }
      }
      synchronized (this.jdField_do)
      {
        this.jdField_do.add(new Integer(this.jdField_for));
      }
      notifyAll();
    }
    
    public void a()
    {
      if (this.jdField_for > 0) {
        this.jdField_for -= 1;
      }
      if (this.jdField_for == 0) {
        synchronized (this.jdField_do)
        {
          this.jdField_do.remove(0);
          this.jdField_do.notifyAll();
        }
      }
    }
    
    public void jdField_if()
    {
      notifyAll();
      if (this.jdField_if != null) {
        for (int i = 0; i < this.a; i++) {
          try
          {
            this.jdField_if[i].interrupt();
          }
          catch (Exception localException) {}
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.BankMessage
 * JD-Core Version:    0.7.0.1
 */