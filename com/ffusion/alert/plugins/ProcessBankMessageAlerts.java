package com.ffusion.alert.plugins;

import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.plugins.message.Message;
import com.ffusion.alert.plugins.message.MessageImpl;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.beans.alerts.UserAlerts;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.ejb.ICSILEJB;
import com.ffusion.util.beans.LocalizableString;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

public class ProcessBankMessageAlerts
  extends BaseAlert
  implements IAEChannel, AlertChannelConstants
{
  protected static final String THIS_PLUGIN_NAME = "ProcessBankMessageAlerts";
  protected int alertPageSize = 200;
  protected int numWorkerThreads = 2;
  private boolean aRZ = false;
  private int aR0 = 0;
  private static final String aRY = "MESSAGETHREADS";
  private static final String aR1 = "numMessages";
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    logEntry("Attempting to set context parameters from properties file");
    setUpEnvironment(paramProperties, paramIAEAlertEngine, paramPrintWriter);
    initializeChannelProperties(paramProperties);
    logEntry("Initialization for ProcessBankMessageAlerts completed");
  }
  
  protected void initializeChannelProperties(Properties paramProperties)
  {
    String str1 = paramProperties.getProperty("APP_ALERTS_PAGE_SIZE");
    String str2 = paramProperties.getProperty("NUM_PROCESSING_THREADS");
    String str3 = paramProperties.getProperty("PERFORMANCE_TESTING");
    if (str1 != null) {
      try
      {
        this.alertPageSize = Integer.parseInt(str1);
      }
      catch (Exception localException1) {}
    }
    if (str2 != null) {
      try
      {
        this.numWorkerThreads = Integer.parseInt(str2);
      }
      catch (Exception localException2) {}
    }
    if (str3 != null) {
      try
      {
        this.aRZ = Boolean.valueOf(str3).booleanValue();
      }
      catch (Exception localException3)
      {
        this.aRZ = false;
      }
    }
    logEntry("alert page size : " + this.alertPageSize + " worker thread count: " + this.numWorkerThreads);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    long l1 = System.currentTimeMillis();
    logEntry("Processing Alert...");
    int i = 0;
    try
    {
      int j = 0;
      int k = 0;
      UserAlerts localUserAlerts = new UserAlerts();
      HashMap localHashMap = new HashMap();
      synchronized (this)
      {
        if (getCsilInstance() == null) {
          setCsilInstance(AlertUtil.connectToCSILEJB(getInitProps()));
        }
      }
      ??? = new ArrayList();
      b localb = new b(this.numWorkerThreads, (ArrayList)???);
      createEJBs(this.numWorkerThreads);
      synchronized (localb)
      {
        localb.a(getCsilInstances());
      }
      while (k == 0)
      {
        try
        {
          localUserAlerts = getCsilInstance().getPagedUserAlerts(6, j, this.alertPageSize, localHashMap);
        }
        catch (Exception ???)
        {
          CSILEJBWrapper localCSILEJBWrapper = new CSILEJBWrapper(getCsilInstance());
          localCSILEJBWrapper.reconnect(getInitProps());
          localUserAlerts = getCsilInstance().getPagedUserAlerts(6, j, this.alertPageSize, localHashMap);
        }
        synchronized (???)
        {
          if (((ArrayList)???).size() > 0) {
            ???.wait();
          }
        }
        if ((localUserAlerts == null) || ((localUserAlerts != null) && (localUserAlerts.size() == 0))) {
          break;
        }
        synchronized (localb)
        {
          localb.a(localUserAlerts);
        }
        j = ((UserAlert)localUserAlerts.get(localUserAlerts.size() - 1)).getUserAlertIdValue();
        if (localUserAlerts.size() < this.alertPageSize) {
          k = 1;
        }
      }
      synchronized (???)
      {
        if (((ArrayList)???).size() > 0) {
          ???.wait();
        }
      }
      synchronized (localb)
      {
        localb.jdMethod_if();
      }
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
      if (this.aRZ)
      {
        long l2 = System.currentTimeMillis();
        long l3 = l2 - l1;
        long l4 = l3 / this.aR0;
        AlertUtil.logEntry("Performance testing: processed " + this.aR0 + " alerts and it took " + l3 + " ms", true, getWriter());
        AlertUtil.logEntry("The average time for each alert is " + l4 + " ms", true, getWriter());
        this.aR0 = 0;
      }
      cleaupCSILEJBs();
    }
    return i;
  }
  
  public void stop()
  {
    logEntry("Stopping ProcessBankMessageAlerts plugin...");
  }
  
  protected String getPluginName()
  {
    return "ProcessBankMessageAlerts";
  }
  
  private void jdMethod_for(SecureUser paramSecureUser, UserAlert paramUserAlert, int paramInt)
  {
    logEntry("Creating Alert");
    Object localObject = new MessageImpl();
    Message localMessage = null;
    HashMap localHashMap = new HashMap();
    DeliveryInfos localDeliveryInfos = paramUserAlert.getDeliveryInfos();
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = AlertUtil.convertDeliveryInfos(localDeliveryInfos);
    String str = String.valueOf(paramInt);
    localHashMap.put("numMessages", str);
    for (int i = 0; i < arrayOfIAEDeliveryInfo.length; i++)
    {
      IAEDeliveryInfo localIAEDeliveryInfo = arrayOfIAEDeliveryInfo[i];
      localObject = getAlertMessage(paramUserAlert, localIAEDeliveryInfo, localHashMap);
      Properties localProperties = localIAEDeliveryInfo.getDeliveryProperties();
      AlertUtil.setProperty(localProperties, "subject", ((Message)localObject).getSubject());
      AlertUtil.setProperty(localProperties, "body", ((Message)localObject).getContent());
      AlertUtil.setProperty(localProperties, "AlertType", "BankMessage");
      localIAEDeliveryInfo.setDeliveryProperties(localProperties);
      arrayOfIAEDeliveryInfo[i] = localIAEDeliveryInfo;
    }
    localMessage = jdMethod_for(paramUserAlert, false, localHashMap);
    try
    {
      i = getEngine().createAlert(getAppInfo(), AlertUtil.getUserIdForAlert(paramSecureUser), 0, 1, null, arrayOfIAEDeliveryInfo, localMessage.getContent());
      logEntry("New alertID to bank message channels: " + i);
    }
    catch (Exception localException)
    {
      logEntry(localException, "Exception while creating alert: " + localException.getLocalizedMessage());
    }
  }
  
  protected void processBankMessages(UserAlert paramUserAlert, CSILEJBWrapper paramCSILEJBWrapper, SecureUser paramSecureUser, UserAlerts paramUserAlerts)
    throws CreateException, NamingException, RemoteException, EJBException, CSILException, Exception
  {
    HashMap localHashMap = new HashMap();
    int i = 0;
    logEntry("Now Making CSIL call to getNumberOfUnreadMessagesExcludingAlerts...");
    try
    {
      localHashMap.put("MESSAGETHREADS", new MessageThreads(Locale.getDefault()));
      i = paramCSILEJBWrapper.getEJB().getNumberOfUnreadMessagesExcludingAlerts(paramSecureUser, localHashMap);
    }
    catch (Exception localException)
    {
      paramCSILEJBWrapper.reconnect(getInitProps());
      i = paramCSILEJBWrapper.getEJB().getNumberOfUnreadMessagesExcludingAlerts(paramSecureUser, localHashMap);
    }
    if (i > 0)
    {
      jdMethod_for(paramSecureUser, paramUserAlert, i);
      this.aR0 += 1;
      boolean bool = paramUserAlert.getOneTimeValue();
      if (bool)
      {
        paramUserAlert.setStatus(0);
        paramUserAlerts.add(paramUserAlert);
      }
    }
  }
  
  protected Message getAlertMessage(UserAlert paramUserAlert, IAEDeliveryInfo paramIAEDeliveryInfo, HashMap paramHashMap)
  {
    Object localObject = new MessageImpl();
    boolean bool = Boolean.valueOf(paramIAEDeliveryInfo.getDeliveryProperties().getProperty("secure")).booleanValue();
    localObject = jdMethod_for(paramUserAlert, bool, paramHashMap);
    return localObject;
  }
  
  private Message jdMethod_for(UserAlert paramUserAlert, boolean paramBoolean, HashMap paramHashMap)
  {
    MessageImpl localMessageImpl = new MessageImpl();
    Object[] arrayOfObject1;
    LocalizableString localLocalizableString1;
    String str;
    Object[] arrayOfObject2;
    LocalizableString localLocalizableString2;
    if (paramBoolean)
    {
      arrayOfObject1 = new Object[0];
      localLocalizableString1 = new LocalizableString("com.ffusion.beans.alerts.resources", "subjectSecBankMsg", arrayOfObject1);
      localMessageImpl.setSubject((String)localLocalizableString1.localize(paramUserAlert.getLocale()));
      str = (String)paramHashMap.get("numMessages");
      arrayOfObject2 = new Object[] { str };
      localLocalizableString2 = new LocalizableString("com.ffusion.beans.alerts.resources", "bodyBankSecureMsg", arrayOfObject2);
      localMessageImpl.addContent((String)localLocalizableString2.localize(paramUserAlert.getLocale()));
    }
    else
    {
      arrayOfObject1 = new Object[0];
      localLocalizableString1 = new LocalizableString("com.ffusion.beans.alerts.resources", "subjectUnsecBankMsg", arrayOfObject1);
      localMessageImpl.setSubject((String)localLocalizableString1.localize(paramUserAlert.getLocale()));
      str = (String)paramHashMap.get("numMessages");
      arrayOfObject2 = new Object[] { str };
      localLocalizableString2 = new LocalizableString("com.ffusion.beans.alerts.resources", "bodyBankUnSecureMsg", arrayOfObject2);
      localMessageImpl.addContent((String)localLocalizableString2.localize(paramUserAlert.getLocale()));
    }
    return localMessageImpl;
  }
  
  public void doProcessAlert(int paramInt1, int paramInt2, CSILEJBWrapper paramCSILEJBWrapper, UserAlerts paramUserAlerts)
  {
    SecureUser localSecureUser = new SecureUser();
    UserAlerts localUserAlerts = new UserAlerts();
    HashMap localHashMap = new HashMap();
    try
    {
      for (int i = paramInt1; i < paramInt2; i++)
      {
        UserAlert localUserAlert = (UserAlert)paramUserAlerts.get(i);
        localSecureUser.setXML(localUserAlert.getAdditionalProperty("SECUREUSER"));
        processBankMessages(localUserAlert, paramCSILEJBWrapper, localSecureUser, localUserAlerts);
      }
      if (localUserAlerts.size() > 0) {
        paramCSILEJBWrapper.getEJB().modifyUserAlerts(localUserAlerts, localHashMap);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  private class a
    extends Thread
  {
    private int jdField_do = -1;
    private int jdField_int = -1;
    private CSILEJBWrapper a = null;
    private UserAlerts jdField_if = null;
    private ProcessBankMessageAlerts.b jdField_for = null;
    
    public a(CSILEJBWrapper paramCSILEJBWrapper, ProcessBankMessageAlerts.b paramb)
    {
      this.a = paramCSILEJBWrapper;
      this.jdField_for = paramb;
    }
    
    public void run()
    {
      try
      {
        for (;;)
        {
          if ((this.jdField_if == null) || (this.jdField_if.size() <= 0)) {
            synchronized (this.jdField_for)
            {
              this.jdField_for.wait();
            }
          }
          ProcessBankMessageAlerts.this.doProcessAlert(this.jdField_do, this.jdField_int, this.a, this.jdField_if);
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
    
    public void a(int paramInt1, int paramInt2, UserAlerts paramUserAlerts)
    {
      this.jdField_do = paramInt1;
      this.jdField_int = paramInt2;
      this.jdField_if = ((UserAlerts)paramUserAlerts.clone());
    }
    
    public void finalize()
      throws Throwable
    {
      this.jdField_if = null;
      this.jdField_for = null;
      super.finalize();
    }
  }
  
  private class b
  {
    private ProcessBankMessageAlerts.a[] jdField_if = null;
    private int a = 0;
    private int jdField_for = 0;
    private ArrayList jdField_do = null;
    
    public b(int paramInt, ArrayList paramArrayList)
    {
      this.a = paramInt;
      this.jdField_do = paramArrayList;
    }
    
    public void a(ArrayList paramArrayList)
    {
      this.jdField_if = new ProcessBankMessageAlerts.a[this.a];
      for (int i = 0; i < this.a; i++)
      {
        this.jdField_if[i] = new ProcessBankMessageAlerts.a(ProcessBankMessageAlerts.this, (CSILEJBWrapper)paramArrayList.get(i), this);
        this.jdField_if[i].start();
      }
    }
    
    public void a(UserAlerts paramUserAlerts)
    {
      this.jdField_for = 0;
      int i = paramUserAlerts.size();
      int j = i / this.a;
      if (j <= 0)
      {
        this.jdField_for = 1;
        this.jdField_if[0].a(0, i, paramUserAlerts);
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
          this.jdField_if[n].a(k, m, paramUserAlerts);
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
 * Qualified Name:     com.ffusion.alert.plugins.ProcessBankMessageAlerts
 * JD-Core Version:    0.7.0.1
 */