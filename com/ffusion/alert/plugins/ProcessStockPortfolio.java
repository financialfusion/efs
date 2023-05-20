package com.ffusion.alert.plugins;

import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.plugins.message.Message;
import com.ffusion.alert.plugins.message.MessageList;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.AlertStock;
import com.ffusion.beans.alerts.Alerts;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.portal.Stock;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.ejb.ICSILEJB;
import com.ffusion.services.PortalData3;
import com.ffusion.tasks.alerts.GetStockFromAlert;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableString;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

public class ProcessStockPortfolio
  extends BaseAlert
  implements IAEChannel, AlertChannelConstants
{
  private boolean aSG = false;
  private PortalData3 aSF = null;
  protected String portalDataClassName = "";
  protected String portalDataInitURL = "";
  protected static final String PORTAL_DATA_CLASS_NAME = "PORTAL_DATA_CLASS_NAME";
  protected static final String PORTAL_DATA_INIT_URL = "PORTAL_DATA_INIT_URL";
  protected static final String THRESHOLD_SET = "THRESHOLD_SET";
  private IAEAlertDefinition[] aSH;
  public static final String ONETIME = "ONETIME";
  public static final String CRITERIA = "Criteria";
  public static final String AMOUNT = "Amount";
  public static final String SUBJECT = "subject";
  public static final String STOCK = "Stock";
  protected boolean b_useCsilEJB = false;
  protected static final int DEFAULT_ALERT_PAGE_SIZE = 200;
  protected static final int DEFAULT_NUM_WORKER_THREADS = 20;
  protected int alertPageSize = 200;
  protected int numWorkerThreads = 20;
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    setUpEnvironment(paramProperties, paramIAEAlertEngine, paramPrintWriter);
    jdMethod_byte(Boolean.valueOf(paramProperties.getProperty("THRESHOLD_SET")).booleanValue());
    this.portalDataClassName = getInitProps().getProperty("PORTAL_DATA_CLASS_NAME");
    this.portalDataInitURL = getInitProps().getProperty("PORTAL_DATA_INIT_URL");
    getWorkerThreadsAndPageSizeInfo(paramProperties);
    int i = 0;
    try
    {
      Class localClass = Class.forName(this.portalDataClassName);
      PortalData3 localPortalData3 = (PortalData3)localClass.newInstance();
      i = localPortalData3.initialize(this.portalDataInitURL);
      if (i != 0)
      {
        logEntry("ERROR: Unable to initialize PortalData service.");
        return;
      }
      setPortalData(localPortalData3);
      logEntry("Successfully initialized PortalData service.");
      if (isUseCsil()) {
        return;
      }
    }
    catch (Exception localException)
    {
      logEntry(localException);
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
  
  protected void setUnavailableStockMessage(MessageList paramMessageList, Locale paramLocale, AlertStock paramAlertStock)
  {
    Object[] arrayOfObject = new Object[0];
    arrayOfObject[0] = defaultIfNull(paramAlertStock.getSymbol());
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.alerts.resources", "unavailableStockSymbol", arrayOfObject);
    paramMessageList.getUnsecureMessage().addContent((String)localLocalizableString.localize(paramLocale));
  }
  
  protected void setStockMessage(MessageList paramMessageList, Locale paramLocale, AlertStock paramAlertStock, double paramDouble1, String paramString, double paramDouble2)
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = defaultIfNull(paramAlertStock.getSymbol());
    arrayOfObject[1] = ("" + paramDouble1);
    if (!paramString.equalsIgnoreCase("ANY AMOUNT"))
    {
      arrayOfObject[2] = paramString;
      arrayOfObject[3] = ("" + paramDouble2);
    }
    else
    {
      arrayOfObject[2] = "";
      arrayOfObject[3] = "";
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.alerts.resources", "lastStockPrice", arrayOfObject);
    paramMessageList.getUnsecureMessage().addContent((String)localLocalizableString.localize(paramLocale));
    paramMessageList.getSecureMessage().addContent((String)localLocalizableString.localize(paramLocale));
  }
  
  protected MessageList getMessageListInstance(Locale paramLocale)
  {
    MessageList localMessageList = new MessageList();
    localMessageList.setSubject(ResourceUtil.getString("subjectStock", "com.ffusion.beans.alerts.resources", paramLocale));
    return localMessageList;
  }
  
  private void jdMethod_for(IAEAlertDefinition paramIAEAlertDefinition, int paramInt, CSILEJBWrapper paramCSILEJBWrapper, SecureUser paramSecureUser, HashMap paramHashMap)
    throws RemoteException, NamingException, CreateException, CSILException, Exception
  {
    if ((paramIAEAlertDefinition != null) && (ab()) && (paramIAEAlertDefinition.getDeliveryInfo() != null))
    {
      logEntry("Processing Alert... ejb process");
      AlertStock localAlertStock = null;
      int i = 1;
      boolean bool = true;
      Stock localStock = null;
      Alerts localAlerts = new Alerts();
      Alert localAlert1 = convertAlert(paramIAEAlertDefinition, paramSecureUser.getLocale());
      localAlertStock = GetStockFromAlert.execute(localAlert1);
      bool = Boolean.valueOf(defaultIfNull(localAlertStock.getOnetime())).booleanValue();
      MessageList localMessageList = getMessageListInstance(paramSecureUser.getLocale());
      try
      {
        localStock = paramCSILEJBWrapper.getEJB().getStock(paramSecureUser, localAlertStock.getSymbol(), paramHashMap);
      }
      catch (RemoteException localRemoteException)
      {
        paramCSILEJBWrapper.reconnect();
        localStock = paramCSILEJBWrapper.getEJB().getStock(paramSecureUser, localAlertStock.getSymbol(), paramHashMap);
      }
      if ((localStock == null) || (localStock.getLastSalePrice() == null) || (localStock.getLastSalePrice().length() == 0)) {
        setUnavailableStockMessage(localMessageList, paramSecureUser.getLocale(), localAlertStock);
      }
      if ((localStock != null) && (localStock.getLastSalePrice() != null) && (localStock.getLastSalePrice().length() != 0))
      {
        double d1 = localAlertStock.getAmountValue();
        String str = localAlertStock.getCriteria();
        double d2 = Double.parseDouble(localStock.getLastSalePrice());
        if ((str != null) && ((str.equalsIgnoreCase("More Than")) || (str.equalsIgnoreCase("Less Than")) || (str.equalsIgnoreCase("ANY AMOUNT")))) {
          setStockMessage(localMessageList, paramSecureUser.getLocale(), localAlertStock, d2, str, d1);
        } else {
          i = 0;
        }
        if (bool) {
          localAlerts.add(localAlert1);
        }
      }
      if (i != 0)
      {
        AlertUtil.setProperty(paramIAEAlertDefinition, "subject", localMessageList.getUnsecureMessage().getSubject());
        AlertUtil.setProperty(paramIAEAlertDefinition, "body", localMessageList.getUnsecureMessage().getContent());
        AlertUtil.setProperty(paramIAEAlertDefinition, "AlertType", "Stock");
        createAlert(paramIAEAlertDefinition, localMessageList.getUnsecureMessage().getContent(), paramInt);
      }
      if (localAlerts.size() > 0)
      {
        Alert localAlert2 = null;
        Iterator localIterator = localAlerts.iterator();
        while (localIterator.hasNext()) {
          try
          {
            localAlert2 = (Alert)localIterator.next();
            suspendAlert(paramIAEAlertDefinition.getUserId(), paramIAEAlertDefinition, localAlert2, paramInt);
          }
          catch (Exception localException)
          {
            logEntry(localException);
          }
        }
      }
    }
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    logEntry("Processing Alert...");
    int i = 0;
    int j = -1;
    IAEAlertDefinition[] arrayOfIAEAlertDefinition = null;
    try
    {
      int k = 0;
      AEApplicationInfo localAEApplicationInfo = getAppInfo();
      int m = 0;
      reconnectToCsil(true);
      ArrayList localArrayList = new ArrayList();
      b localb = new b(this.numWorkerThreads, localArrayList);
      createEJBs(this.numWorkerThreads);
      synchronized (localb)
      {
        localb.a(getCsilInstances());
      }
      while (m == 0)
      {
        arrayOfIAEAlertDefinition = getEngine().getAppAlertsForChannelPaged(localAEApplicationInfo, true, getPluginName(), k, this.alertPageSize);
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
        k = arrayOfIAEAlertDefinition[(arrayOfIAEAlertDefinition.length - 1)].getId();
        if (arrayOfIAEAlertDefinition.length < this.alertPageSize) {
          m = 1;
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
  
  public void stop()
  {
    logEntry("Stopping ProcessStockPortfolio plugin...");
  }
  
  protected void createAlert(IAEAlertDefinition paramIAEAlertDefinition, String paramString, int paramInt)
  {
    try
    {
      IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = getAlertsDeliveryInfo(paramIAEAlertDefinition, paramInt);
      int i = getEngine().createAlert(getAppInfo(), paramIAEAlertDefinition.getUserId(), 0, 1, null, arrayOfIAEDeliveryInfo, paramString);
      logEntry("New alertID: " + i);
    }
    catch (Exception localException)
    {
      logEntry(localException);
    }
  }
  
  protected Alert convertAlert(IAEAlertDefinition paramIAEAlertDefinition, Locale paramLocale)
  {
    Alert localAlert = new Alert(paramLocale);
    localAlert.setID(String.valueOf(paramIAEAlertDefinition.getId()));
    localAlert.setType(paramIAEAlertDefinition.getType());
    localAlert.setPriority(paramIAEAlertDefinition.getPriority());
    Date localDate1 = paramIAEAlertDefinition.getScheduleInfo().getStartDate();
    Calendar localCalendar1 = Calendar.getInstance(paramIAEAlertDefinition.getScheduleInfo().getRegularTimeZone(), paramLocale);
    localCalendar1.setTime(localDate1);
    Date localDate2 = paramIAEAlertDefinition.getScheduleInfo().getEndDate();
    Calendar localCalendar2 = Calendar.getInstance(paramIAEAlertDefinition.getScheduleInfo().getRegularTimeZone(), paramLocale);
    localCalendar2.setTime(localDate2);
    localAlert.setStartDate(new DateTime(localCalendar1, paramLocale));
    localAlert.setEndDate(new DateTime(localCalendar2, paramLocale));
    localAlert.setInterval(paramIAEAlertDefinition.getScheduleInfo().getInterval());
    localAlert.setTimeZone(paramIAEAlertDefinition.getScheduleInfo().getRegularTimeZone());
    localAlert.setAppInfo(paramIAEAlertDefinition.getApplicationName());
    int i = paramIAEAlertDefinition.getDeliveryInfo().length;
    DeliveryInfos localDeliveryInfos = new DeliveryInfos();
    for (int j = 0; j < i; j++)
    {
      DeliveryInfo localDeliveryInfo = new DeliveryInfo(localAlert.getLocale());
      localDeliveryInfo.setID(String.valueOf(j + 1));
      localDeliveryInfo.setChannelName(paramIAEAlertDefinition.getDeliveryInfo()[j].getDeliveryChannelName());
      localDeliveryInfo.setOrder(paramIAEAlertDefinition.getDeliveryInfo()[j].getDeliveryOrder());
      localDeliveryInfo.setMaxDelay(String.valueOf(paramIAEAlertDefinition.getDeliveryInfo()[j].getMaxDelay()));
      localDeliveryInfo.setProperties(paramIAEAlertDefinition.getDeliveryInfo()[j].getDeliveryProperties());
      if (paramIAEAlertDefinition.getDeliveryInfo()[j].isSuspended()) {
        localDeliveryInfo.setSuspended(true);
      }
      localDeliveryInfos.add(localDeliveryInfo);
    }
    localAlert.setDeliveryInfos(localDeliveryInfos);
    localAlert.setMessage(paramIAEAlertDefinition.getMessage());
    return localAlert;
  }
  
  private boolean ab()
  {
    return this.aSG;
  }
  
  private void jdMethod_byte(boolean paramBoolean)
  {
    this.aSG = paramBoolean;
  }
  
  protected PortalData3 getPortalData()
  {
    return this.aSF;
  }
  
  protected void setPortalData(PortalData3 paramPortalData3)
  {
    this.aSF = paramPortalData3;
  }
  
  protected String getPluginName()
  {
    return "ProcessStockPortfolio";
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
    HashMap localHashMap = new HashMap();
    localHashMap.put("SERVICE", getPortalData());
    for (int i = paramInt1; i < paramInt2; i++) {
      try
      {
        int j = getPluginLocation(paramArrayOfIAEAlertDefinition[i], getPluginName());
        if (j != -1)
        {
          Properties localProperties = paramArrayOfIAEAlertDefinition[i].getDeliveryInfo()[j].getDeliveryProperties();
          localSecureUser.setXML(localProperties.getProperty("SECUREUSER"));
          String str = localProperties.getProperty("PREFERREDLANGUAGE");
          if ((str != null) && (str.trim().length() != 0)) {
            localSecureUser.setLocale(str);
          }
          jdMethod_for(paramArrayOfIAEAlertDefinition[i], j, paramCSILEJBWrapper, localSecureUser, localHashMap);
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
    private int jdField_for = -1;
    private int jdField_int = -1;
    private CSILEJBWrapper a = null;
    private IAEAlertDefinition[] jdField_do = null;
    private ProcessStockPortfolio.b jdField_if = null;
    
    public a(CSILEJBWrapper paramCSILEJBWrapper, ProcessStockPortfolio.b paramb)
    {
      this.a = paramCSILEJBWrapper;
      this.jdField_if = paramb;
    }
    
    public void run()
    {
      try
      {
        for (;;)
        {
          if ((this.jdField_do == null) || (this.jdField_do.length <= 0)) {
            synchronized (this.jdField_if)
            {
              this.jdField_if.wait();
            }
          }
          ProcessStockPortfolio.this.doProcessAlert(this.jdField_for, this.jdField_int, this.a, this.jdField_do);
          this.jdField_do = null;
          synchronized (this.jdField_if)
          {
            this.jdField_if.a();
            this.jdField_if.wait();
          }
        }
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        this.jdField_if = null;
        this.jdField_do = null;
        this.a = null;
      }
    }
    
    public void a(int paramInt1, int paramInt2, IAEAlertDefinition[] paramArrayOfIAEAlertDefinition)
    {
      this.jdField_for = paramInt1;
      this.jdField_int = paramInt2;
      this.jdField_do = ((IAEAlertDefinition[])paramArrayOfIAEAlertDefinition.clone());
    }
    
    public void finalize()
      throws Throwable
    {
      this.jdField_if = null;
      this.jdField_do = null;
      this.a = null;
      super.finalize();
    }
  }
  
  private class b
  {
    private ProcessStockPortfolio.a[] jdField_if = null;
    private ArrayList jdField_do;
    private int a = 0;
    private int jdField_for = 0;
    
    public b(int paramInt, ArrayList paramArrayList)
    {
      this.a = paramInt;
      this.jdField_do = paramArrayList;
    }
    
    public void a(ArrayList paramArrayList)
    {
      this.jdField_if = new ProcessStockPortfolio.a[this.a];
      for (int i = 0; i < this.a; i++)
      {
        this.jdField_if[i] = new ProcessStockPortfolio.a(ProcessStockPortfolio.this, (CSILEJBWrapper)paramArrayList.get(i), this);
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
    
    public void finalize()
      throws Throwable
    {
      this.jdField_if = null;
      this.jdField_do = null;
      super.finalize();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.ProcessStockPortfolio
 * JD-Core Version:    0.7.0.1
 */