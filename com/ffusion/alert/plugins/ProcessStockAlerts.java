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
import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.Stocks;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.ejb.ICSILEJB;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableList;
import com.ffusion.util.beans.LocalizableString;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

public class ProcessStockAlerts
  extends BaseAlert
  implements IAEChannel, AlertChannelConstants
{
  protected static final String THIS_PLUGIN_NAME = "ProcessStockAlerts";
  protected int alertPageSize = 200;
  protected int numWorkerThreads = 2;
  private boolean aSL = false;
  private static final String aSN = ",";
  private static final String aSM = "stocks";
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    logEntry("Attempting to set context parameters from properties file");
    setUpEnvironment(paramProperties, paramIAEAlertEngine, paramPrintWriter);
    initializeChannelProperties(paramProperties);
    logEntry("Initialization for ProcessStockAlerts completed");
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
        this.aSL = Boolean.valueOf(str3).booleanValue();
      }
      catch (Exception localException3)
      {
        this.aSL = false;
      }
    }
    logEntry("alert page size : " + this.alertPageSize + " worker thread count: " + this.numWorkerThreads);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    long l1 = System.currentTimeMillis();
    int i = 0;
    logEntry("Processing Alert...");
    int j = 0;
    try
    {
      int k = 0;
      int m = 0;
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
      while (m == 0)
      {
        try
        {
          localUserAlerts = getCsilInstance().getPagedUserAlerts(5, k, this.alertPageSize, localHashMap);
          i += localUserAlerts.size();
        }
        catch (Exception ???)
        {
          CSILEJBWrapper localCSILEJBWrapper = new CSILEJBWrapper(getCsilInstance());
          localCSILEJBWrapper.reconnect(getInitProps());
          localUserAlerts = getCsilInstance().getPagedUserAlerts(5, k, this.alertPageSize, localHashMap);
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
        k = ((UserAlert)localUserAlerts.get(localUserAlerts.size() - 1)).getUserAlertIdValue();
        if (localUserAlerts.size() < this.alertPageSize) {
          m = 1;
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
      j = -1003;
    }
    catch (NamingException localNamingException)
    {
      logEntry(localNamingException);
      j = -1002;
    }
    catch (RemoteException localRemoteException)
    {
      logEntry(localRemoteException);
      j = -1001;
    }
    catch (EJBException localEJBException)
    {
      logEntry(localEJBException);
      j = -1000;
    }
    catch (CSILException localCSILException)
    {
      logEntry(localCSILException);
      j = -1004;
    }
    catch (Exception localException)
    {
      logEntry(localException);
      j = -2000;
    }
    finally
    {
      if (this.aSL)
      {
        long l2 = System.currentTimeMillis();
        long l3 = l2 - l1;
        long l4 = l3 / i;
        AlertUtil.logEntry("Performance testing: processed " + i + " alerts and it took " + l3 + " ms", true, getWriter());
        AlertUtil.logEntry("The average time for each alert is " + l4 + " ms", true, getWriter());
      }
      cleaupCSILEJBs();
    }
    return j;
  }
  
  public void stop()
  {
    logEntry("Stopping ProcessStockAlerts plugin...");
  }
  
  protected String getPluginName()
  {
    return "ProcessStockAlerts";
  }
  
  private void jdMethod_for(SecureUser paramSecureUser, UserAlert paramUserAlert, Stocks paramStocks)
  {
    logEntry("Creating Alert");
    Object localObject = new MessageImpl();
    Message localMessage = null;
    HashMap localHashMap = new HashMap();
    localHashMap.put("stocks", paramStocks);
    DeliveryInfos localDeliveryInfos = paramUserAlert.getDeliveryInfos();
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = AlertUtil.convertDeliveryInfos(localDeliveryInfos);
    for (int i = 0; i < arrayOfIAEDeliveryInfo.length; i++)
    {
      IAEDeliveryInfo localIAEDeliveryInfo = arrayOfIAEDeliveryInfo[i];
      localObject = getAlertMessage(paramUserAlert, localIAEDeliveryInfo, localHashMap);
      Properties localProperties = localIAEDeliveryInfo.getDeliveryProperties();
      AlertUtil.setProperty(localProperties, "subject", ((Message)localObject).getSubject());
      AlertUtil.setProperty(localProperties, "body", ((Message)localObject).getContent());
      AlertUtil.setProperty(localProperties, "AlertType", "StockPortfolio");
      localIAEDeliveryInfo.setDeliveryProperties(localProperties);
      arrayOfIAEDeliveryInfo[i] = localIAEDeliveryInfo;
    }
    localMessage = jdMethod_int(paramUserAlert, false, localHashMap);
    try
    {
      i = getEngine().createAlert(getAppInfo(), AlertUtil.getUserIdForAlert(paramSecureUser), 0, 1, null, arrayOfIAEDeliveryInfo, localMessage.getContent());
      logEntry("New alertID to stock portfolio channels: " + i);
    }
    catch (Exception localException)
    {
      logEntry(localException, "Exception while creating alert: " + localException.getLocalizedMessage());
    }
  }
  
  protected void processStock(UserAlert paramUserAlert, CSILEJBWrapper paramCSILEJBWrapper, SecureUser paramSecureUser, UserAlerts paramUserAlerts)
    throws CreateException, NamingException, RemoteException, EJBException, CSILException, Exception
  {
    Stocks localStocks1 = new Stocks();
    HashMap localHashMap = new HashMap();
    if ((paramUserAlert != null) && (paramUserAlert.getDeliveryInfos() != null))
    {
      logEntry("Processing Alert... ejb process");
      String str2 = paramUserAlert.getAdditionalProperty("PortfolioStockList");
      String str3 = paramUserAlert.getAdditionalProperty("FreeFormStockList");
      String str1 = str2.concat(",").concat(str3);
      StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str4 = localStringTokenizer.nextToken();
        Stock localStock = new Stock();
        localStock.setSymbol(str4);
        localStocks1.add(localStock);
      }
      if (localStocks1.size() > 0)
      {
        boolean bool = paramUserAlert.getOneTimeValue();
        Stocks localStocks2 = paramCSILEJBWrapper.getEJB().getStocks(paramSecureUser, localStocks1, localHashMap);
        jdMethod_for(paramSecureUser, paramUserAlert, localStocks2);
        if (bool)
        {
          paramUserAlert.setStatus(0);
          paramUserAlerts.add(paramUserAlert);
        }
      }
    }
  }
  
  protected Message getAlertMessage(UserAlert paramUserAlert, IAEDeliveryInfo paramIAEDeliveryInfo, HashMap paramHashMap)
  {
    Object localObject = new MessageImpl();
    boolean bool = Boolean.valueOf(paramIAEDeliveryInfo.getDeliveryProperties().getProperty("secure")).booleanValue();
    localObject = jdMethod_int(paramUserAlert, bool, paramHashMap);
    return localObject;
  }
  
  private Message jdMethod_int(UserAlert paramUserAlert, boolean paramBoolean, HashMap paramHashMap)
  {
    MessageImpl localMessageImpl = new MessageImpl();
    Stocks localStocks = (Stocks)paramHashMap.get("stocks");
    Object localObject1;
    Object localObject2;
    Object localObject4;
    Object localObject3;
    if (paramBoolean)
    {
      localObject1 = new Object[0];
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.beans.alerts.resources", "subjectSecureStock", (Object[])localObject1);
      localMessageImpl.setSubject((String)localLocalizableString1.localize(paramUserAlert.getLocale()));
      localObject2 = new LocalizableList("com.ffusion.beans.alerts.resources", "bodyStockConsumerSecureStockEntrySeparator");
      for (int j = 0; j < localStocks.size(); j++)
      {
        localObject4 = (Stock)localStocks.get(j);
        Object[] arrayOfObject = { ((Stock)localObject4).getSymbol(), ((Stock)localObject4).getLastSalePrice(), ((Stock)localObject4).getChangePercent() };
        LocalizableString localLocalizableString3 = new LocalizableString("com.ffusion.beans.alerts.resources", "bodyStockConsumerSecureStockEntry", arrayOfObject);
        ((LocalizableList)localObject2).add(localLocalizableString3);
      }
      localObject4 = new Object[1];
      localObject4[0] = localObject2;
      localObject3 = new LocalizableString("com.ffusion.beans.alerts.resources", "bodyStockConsumerSecure", (Object[])localObject4);
      localMessageImpl.addContent((String)((ILocalizable)localObject3).localize(paramUserAlert.getLocale()));
    }
    else
    {
      localMessageImpl.setSubject(ResourceUtil.getString("subjectUnsecureStock", "com.ffusion.beans.alerts.resources", paramUserAlert.getLocale()));
      localObject1 = new LocalizableList("com.ffusion.beans.alerts.resources", "bodyStockConsumerSecureStockEntrySeparator");
      for (int i = 0; i < localStocks.size(); i++)
      {
        localObject2 = (Stock)localStocks.get(i);
        localObject3 = new Object[] { ((Stock)localObject2).getSymbol(), ((Stock)localObject2).getLastSalePrice(), ((Stock)localObject2).getChangePercent() };
        localObject4 = new LocalizableString("com.ffusion.beans.alerts.resources", "bodyStockConsumerUnsecureStockEntry", (Object[])localObject3);
        ((LocalizableList)localObject1).add(localObject4);
      }
      localObject2 = new Object[1];
      localObject2[0] = localObject1;
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.beans.alerts.resources", "bodyStockConsumerUnsecure", (Object[])localObject2);
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
        processStock(localUserAlert, paramCSILEJBWrapper, localSecureUser, localUserAlerts);
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
    private ProcessStockAlerts.b jdField_for = null;
    
    public a(CSILEJBWrapper paramCSILEJBWrapper, ProcessStockAlerts.b paramb)
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
          ProcessStockAlerts.this.doProcessAlert(this.jdField_do, this.jdField_int, this.a, this.jdField_if);
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
    private ProcessStockAlerts.a[] jdField_if = null;
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
      this.jdField_if = new ProcessStockAlerts.a[this.a];
      for (int i = 0; i < this.a; i++)
      {
        this.jdField_if[i] = new ProcessStockAlerts.a(ProcessStockAlerts.this, (CSILEJBWrapper)paramArrayList.get(i), this);
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
 * Qualified Name:     com.ffusion.alert.plugins.ProcessStockAlerts
 * JD-Core Version:    0.7.0.1
 */