package com.ffusion.alert.plugins;

import com.ffusion.alert.clientEJB.IAEAlertClient;
import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.plugins.message.Message;
import com.ffusion.alert.plugins.message.MessageList;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsItemCount;
import com.ffusion.beans.approvals.ApprovalsItemCounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.ejb.ICSILEJB;
import com.ffusion.util.HfnEncrypt;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableList;
import com.ffusion.util.beans.LocalizableString;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

public class PendingCheck
  implements IAEChannel, AlertChannelConstants
{
  private String aSe = "PendingCheckAlert";
  private String aR3 = "";
  private AEApplicationInfo aSh;
  private Properties aR7;
  private boolean aSp;
  private Properties aR6;
  private IAEAlertClient aSg;
  private ICSILEJB aSn;
  private String aR8 = "";
  private String aR4 = "";
  private String aR2 = "";
  private String aSb = "";
  private String aSa = "";
  private String aSm;
  private String aSl = "";
  private boolean aSd = false;
  private IAEAlertEngine aSj;
  private PrintWriter aSi;
  private long aR9;
  private IAEAlertDefinition[] aSf;
  private HashMap aSo;
  private static final String aR5 = "PendingCheck";
  static final String aSk = "WAS4.0";
  protected static final int DEFAULT_ALERT_PAGE_SIZE = 200;
  protected static final int DEFAULT_NUM_WORKER_THREADS = 20;
  protected int alertPageSize = 200;
  protected int numWorkerThreads = 20;
  private ArrayList aSc = null;
  
  protected void createEJBs(int paramInt)
    throws NamingException, RemoteException, CreateException, EJBException, CSILException
  {
    this.aSc = new ArrayList(paramInt);
    for (int i = 0; i < paramInt; i++) {
      this.aSc.add(new CSILEJBWrapper(AlertUtil.connectToCSILEJB(this.aR7), this.aR7));
    }
  }
  
  protected void cleaupCSILEJBs()
  {
    if (this.aSc != null) {
      for (int i = 0; i < this.aSc.size(); i++) {
        try
        {
          ((CSILEJBWrapper)this.aSc.get(i)).remove();
        }
        catch (Exception localException) {}
      }
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
  }
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this.aR7 = paramProperties;
    this.aSi = paramPrintWriter;
    this.aSj = paramIAEAlertEngine;
    this.aSp = Boolean.valueOf(this.aR7.getProperty("DEBUG")).booleanValue();
    AlertUtil.logEntry("Initializing PendingCheck plugin...", this.aSp, paramPrintWriter);
    try
    {
      AlertUtil.logEntry("Attempting to decrypt the APP_INFO password", this.aSp, paramPrintWriter);
      String str = HfnEncrypt.decryptHexEncode(paramProperties.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts"));
      if (str == null) {
        str = paramProperties.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts");
      }
      this.aSh = new AEApplicationInfo(paramProperties.getProperty("APP_INFO_USERNAME", "ApplicationAlerts"), str);
    }
    catch (Exception localException1)
    {
      AlertUtil.logEntry("Exception occurred decrypting the APP_INFO password with message: " + localException1.getMessage(), this.aSp, paramPrintWriter);
      this.aSh = new AEApplicationInfo(paramProperties.getProperty("APP_INFO_USERNAME", "ApplicationAlerts"), paramProperties.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts"));
    }
    this.aSb = this.aR7.getProperty("UAE_INITIAL_CONTEXT_FACTORY");
    this.aR8 = this.aR7.getProperty("UAE_PROVIDER_URL");
    this.aR4 = this.aR7.getProperty("UAE_SECURITY_PRINCIPAL");
    AlertUtil.logEntry("Attempting to decrypt the UAE password", this.aSp, paramPrintWriter);
    try
    {
      this.aR2 = HfnEncrypt.decryptHexEncode(this.aR7.getProperty("UAE_SECURITY_CREDENTIALS"));
    }
    catch (Exception localException2)
    {
      AlertUtil.logEntry("Exception occurred decrypting the UAE password with message: " + localException2.getMessage(), this.aSp, paramPrintWriter);
      this.aR2 = this.aR7.getProperty("UAE_SECURITY_CREDENTIALS");
    }
    this.aSa = this.aR7.getProperty("UAE_JNDI_NAME");
    this.aR3 = this.aR7.getProperty("ALERT_DELIVERY_TIME");
    this.aSm = this.aR7.getProperty("UAE_APPSERVER", "");
    this.aSl = this.aR7.getProperty("USECSIL", "");
    AlertUtil.logEntry("Using CSIL? " + this.aSl, this.aSp, paramPrintWriter);
    if ((this.aSl != null) && (this.aSl.equalsIgnoreCase("True")))
    {
      this.aSd = true;
      this.aSn = AlertUtil.connectToCSILEJB(paramProperties);
    }
    getWorkerThreadsAndPageSizeInfo(paramProperties);
    AlertUtil.logEntry("alert page size : " + this.alertPageSize + " worker thread count: " + this.numWorkerThreads, this.aSp, paramPrintWriter);
    Properties localProperties = new Properties();
    AlertUtil.logEntry("Creating Alert Thread", this.aSp, paramPrintWriter);
    CreateAlertThread localCreateAlertThread = new CreateAlertThread(paramProperties, getPluginName(), paramPrintWriter, paramIAEAlertEngine, localProperties);
    localCreateAlertThread.start();
    AlertUtil.logEntry("End Initializing PendingCheck plugin...", this.aSp, paramPrintWriter);
  }
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter, int paramInt)
    throws Exception
  {
    this.aR7 = paramProperties;
    this.aSi = paramPrintWriter;
    this.aSj = paramIAEAlertEngine;
    AlertUtil.logEntry("Initializing PendingCheck plugin...", this.aSp, paramPrintWriter);
    try
    {
      AlertUtil.logEntry("Attempting to decrypt the APP_INFO password", this.aSp, paramPrintWriter);
      String str = HfnEncrypt.decryptHexEncode(paramProperties.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts"));
      if (str == null) {
        str = paramProperties.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts");
      }
      this.aSh = new AEApplicationInfo(paramProperties.getProperty("APP_INFO_USERNAME", "ApplicationAlerts"), str);
    }
    catch (Exception localException)
    {
      AlertUtil.logEntry("Exception occurred decrypting the APP_INFO password with message: " + localException.getMessage(), this.aSp, paramPrintWriter);
      this.aSh = new AEApplicationInfo(paramProperties.getProperty("APP_INFO_USERNAME", "ApplicationAlerts"), paramProperties.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts"));
    }
    this.aSb = this.aR7.getProperty("UAE_INITIAL_CONTEXT_FACTORY");
    this.aR8 = this.aR7.getProperty("UAE_PROVIDER_URL");
    this.aR4 = this.aR7.getProperty("UAE_SECURITY_PRINCIPAL");
    this.aR2 = this.aR7.getProperty("UAE_SECURITY_CREDENTIALS");
    this.aSa = this.aR7.getProperty("UAE_JNDI_NAME");
    this.aR3 = this.aR7.getProperty("ALERT_DELIVERY_TIME");
    this.aSm = this.aR7.getProperty("UAE_APPSERVER", "");
    this.aSp = Boolean.valueOf(this.aR7.getProperty("DEBUG")).booleanValue();
    this.aSl = this.aR7.getProperty("USECSIL", "");
    if ((this.aSl != null) && (this.aSl.equals("True")))
    {
      this.aSd = true;
      this.aSn = AlertUtil.connectToCSILEJB(paramProperties);
    }
    getWorkerThreadsAndPageSizeInfo(paramProperties);
    AlertUtil.logEntry("alert page size : " + this.alertPageSize + " worker thread count: " + this.numWorkerThreads, this.aSp, paramPrintWriter);
    Properties localProperties = new Properties();
    CreateAlertThread localCreateAlertThread = new CreateAlertThread(paramProperties, getPluginName(), paramPrintWriter, paramIAEAlertEngine, localProperties);
    localCreateAlertThread.start();
    AlertUtil.logEntry("End Initializing PendingCheck plugin...", this.aSp, paramPrintWriter);
  }
  
  protected void initializePositivePayMessages(MessageList paramMessageList, int paramInt)
  {
    initializePositivePayMessages(paramMessageList, paramInt, Locale.getDefault());
  }
  
  protected void initializePositivePayMessages(MessageList paramMessageList, int paramInt, Locale paramLocale)
  {
    paramMessageList.setSubject(ResourceUtil.getString("subjectPositivePay", "com.ffusion.beans.alerts.resources", paramLocale));
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.toString(paramInt);
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.alerts.resources", "securePositivePay", arrayOfObject);
    paramMessageList.getSecureMessage().addContent((String)localLocalizableString.localize(paramLocale));
  }
  
  protected void initializePaymentApprovalMessages(MessageList paramMessageList, int paramInt, Iterator paramIterator)
  {
    initializePaymentApprovalMessages(paramMessageList, paramInt, paramIterator, Locale.getDefault());
  }
  
  protected void initializePaymentApprovalMessages(MessageList paramMessageList, int paramInt, Iterator paramIterator, Locale paramLocale)
  {
    paramMessageList.setSubject(ResourceUtil.getString("subjectPendingApproval", "com.ffusion.beans.alerts.resources", paramLocale));
    LocalizableList localLocalizableList = null;
    LocalizableString localLocalizableString = null;
    Object localObject = null;
    while (paramIterator.hasNext())
    {
      arrayOfObject = new Object[2];
      ApprovalsItemCount localApprovalsItemCount = (ApprovalsItemCount)paramIterator.next();
      if (localLocalizableList == null) {
        localLocalizableList = new LocalizableList("com.ffusion.beans.alerts.resources", "pendingApprovalEntrySeparator");
      }
      arrayOfObject[0] = Integer.toString(localApprovalsItemCount.getNumItems());
      arrayOfObject[1] = jdMethod_for(localApprovalsItemCount.getItemType(), localApprovalsItemCount.getItemSubType(), localApprovalsItemCount.getNumItems());
      localLocalizableList.add(new LocalizableString("com.ffusion.beans.alerts.resources", "bodyPendingApproval", arrayOfObject));
    }
    if (localLocalizableList.size() > 0)
    {
      localObject = (ILocalizable)localLocalizableList.get(localLocalizableList.size() - 1);
      localLocalizableList.remove(localLocalizableList.size() - 1);
      if (localLocalizableList.size() > 0)
      {
        arrayOfObject = new Object[2];
        arrayOfObject[0] = localLocalizableList;
        arrayOfObject[1] = localObject;
        localObject = new LocalizableString("com.ffusion.beans.alerts.resources", "pendingApprovalEntry", arrayOfObject);
      }
    }
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.toString(paramInt);
    arrayOfObject[1] = localObject;
    localLocalizableString = new LocalizableString("com.ffusion.beans.alerts.resources", "securePendingApproval", arrayOfObject);
    paramMessageList.getSecureMessage().addContent((String)localLocalizableString.localize(paramLocale));
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    AlertUtil.logEntry("Processing Alert...", this.aSp, this.aSi);
    int i = 0;
    try
    {
      int j = 0;
      int k = 0;
      IAEAlertDefinition[] arrayOfIAEAlertDefinition = null;
      ArrayList localArrayList = new ArrayList();
      b localb = new b(this.numWorkerThreads, localArrayList);
      createEJBs(this.numWorkerThreads);
      synchronized (localb)
      {
        localb.a(this.aSc);
      }
      while (k == 0)
      {
        arrayOfIAEAlertDefinition = this.aSj.getAppAlertsForChannelPaged(this.aSh, true, getPluginName(), j, this.alertPageSize);
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
    catch (Exception localException)
    {
      if (this.aSp)
      {
        AlertUtil.logEntry("Exception occurred with message " + localException.getMessage(), this.aSp, this.aSi);
        localException.printStackTrace(this.aSi);
      }
    }
    finally
    {
      cleaupCSILEJBs();
    }
    return i;
  }
  
  public void stop()
  {
    AlertUtil.logEntry("Stopping PendingCheck plugin...", this.aSp, this.aSi);
  }
  
  private void jdMethod_for(IAEAlertDefinition paramIAEAlertDefinition, String paramString)
  {
    MessageList localMessageList = new MessageList();
    localMessageList.getSecureMessage().setContent(paramString);
  }
  
  private void jdMethod_for(IAEAlertDefinition paramIAEAlertDefinition, MessageList paramMessageList, int paramInt, Properties paramProperties)
  {
    AlertUtil.logEntry("Checking for alert...", this.aSp, this.aSi);
    try
    {
      if (paramIAEAlertDefinition.getDeliveryInfo()[paramInt].isSuspended() != true)
      {
        jdMethod_for(paramIAEAlertDefinition, paramInt, paramMessageList.getSecureMessage().getContent());
        IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = paramIAEAlertDefinition.getDeliveryInfo();
      }
    }
    catch (Exception localException)
    {
      AlertUtil.logEntry("Exception occurred in checkForAlert with message: " + localException.getMessage(), this.aSp, this.aSi);
    }
  }
  
  private void jdMethod_for(IAEAlertDefinition paramIAEAlertDefinition, int paramInt, String paramString)
  {
    AlertUtil.logEntry("Creating alert...", this.aSp, this.aSi);
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo1 = paramIAEAlertDefinition.getDeliveryInfo();
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo2 = new IAEDeliveryInfo[arrayOfIAEDeliveryInfo1.length - 1];
    int i = 0;
    for (int j = 0; i < arrayOfIAEDeliveryInfo1.length - 1; j++)
    {
      if (i == paramInt) {
        j++;
      }
      arrayOfIAEDeliveryInfo2[i] = arrayOfIAEDeliveryInfo1[j];
      i++;
    }
    try
    {
      Date localDate = null;
      if ((this.aR3 == null) || (this.aR3.trim().length() == 0)) {
        try
        {
          DateTime localDateTime1 = new DateTime();
          localDateTime1.setFormat("M/dd/yyyy");
          DateTime localDateTime2 = new DateTime(localDateTime1.toString() + " " + this.aR3, Locale.getDefault(), "M/dd/yyyy hh:mm");
          localDateTime2.setFormat("M/dd/yyyy hh:mm:ss");
          if (localDateTime2.before(new DateTime())) {
            localDate = new Date(localDateTime2.getTime().getTime() + 86400000L);
          } else {
            localDate = new Date(localDateTime2.getTime().getTime());
          }
        }
        catch (Exception localException2)
        {
          localDate = new Date(System.currentTimeMillis() + 60000L);
        }
      } else {
        localDate = new Date(System.currentTimeMillis() + 60000L);
      }
      int k = this.aSj.createAlert(this.aSh, paramIAEAlertDefinition.getUserId(), 0, 1, null, arrayOfIAEDeliveryInfo2, paramString);
      AlertUtil.logEntry("New alertID: " + k, this.aSp, this.aSi);
    }
    catch (Exception localException1)
    {
      AlertUtil.logEntry("Exception occurred in createAlert with message: " + localException1.getMessage(), this.aSp, this.aSi);
    }
  }
  
  private int jdMethod_int(IAEAlertDefinition paramIAEAlertDefinition, String paramString)
  {
    int i = 0;
    Properties localProperties = null;
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = paramIAEAlertDefinition.getDeliveryInfo();
    for (int j = 0; j < arrayOfIAEDeliveryInfo.length; j++)
    {
      IAEDeliveryInfo localIAEDeliveryInfo = arrayOfIAEDeliveryInfo[j];
      if (localIAEDeliveryInfo.getDeliveryChannelName().equals(paramString))
      {
        localProperties = localIAEDeliveryInfo.getDeliveryProperties();
        i = j;
        break;
      }
      i = -1;
    }
    return i;
  }
  
  private static String jdMethod_for(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 != 1) {
      return "of unknown type";
    }
    if (paramInt3 > 1)
    {
      switch (paramInt2)
      {
      case 1: 
        return "Account Transfers";
      case 2: 
        return "Recurring Account Transfers";
      case 3: 
        return "Bill Payments";
      case 4: 
        return "Recurring Bill Payments";
      case 5: 
        return "Wire Transfers";
      case 6: 
        return "Recurring Wire Transfers";
      case 7: 
        return "ACH Batches";
      case 8: 
        return "Recurring ACH Batches";
      case 9: 
        return "Tax Payments";
      case 12: 
        return "Recurring Tax Payments";
      case 13: 
        return "Child Support Payments";
      case 14: 
        return "Recurring Child Support Payments";
      case 10: 
        return "Wire Batches";
      case 11: 
        return "Cash Concentrations";
      }
      return "of unknown type";
    }
    switch (paramInt2)
    {
    case 1: 
      return "Account Transfer";
    case 2: 
      return "Recurring Account Transfer";
    case 3: 
      return "Bill Payment";
    case 4: 
      return "Recurring Bill Payment";
    case 5: 
      return "Wire Transfer";
    case 6: 
      return "Recurring Wire Transfer";
    case 7: 
      return "ACH Batch";
    case 8: 
      return "Recurring ACH Batch";
    case 9: 
      return "Tax Payment";
    case 12: 
      return "Recurring Tax Payment";
    case 13: 
      return "Child Support Payment";
    case 14: 
      return "Recurring Child Support Payment";
    case 10: 
      return "Wire Batch";
    case 11: 
      return "Cash Concentration";
    }
    return "of unknown type";
  }
  
  protected String getPluginName()
  {
    return "PendingCheck";
  }
  
  public void doProcessAlert(int paramInt1, int paramInt2, CSILEJBWrapper paramCSILEJBWrapper, IAEAlertDefinition[] paramArrayOfIAEAlertDefinition)
  {
    HashMap localHashMap = new HashMap();
    int i = 0;
    int j = 0;
    SecureUser localSecureUser = new SecureUser();
    for (int k = paramInt1; k < paramInt2; k++) {
      if ((paramArrayOfIAEAlertDefinition[k].getUserId() == null) || (!paramArrayOfIAEAlertDefinition[k].getUserId().equalsIgnoreCase(this.aSe)))
      {
        i = jdMethod_int(paramArrayOfIAEAlertDefinition[k], getPluginName());
        if (i != -1)
        {
          Properties localProperties = paramArrayOfIAEAlertDefinition[k].getDeliveryInfo()[i].getDeliveryProperties();
          String str1 = localProperties.getProperty("SECUREUSER");
          localSecureUser.setXML(str1);
          String str2 = localProperties.getProperty("PREFERREDLANGUAGE");
          if ((str2 != null) && (str2.trim().length() > 0)) {
            localSecureUser.setLocale(str2);
          }
          String str3 = localProperties.getProperty("AlertType");
          if (str3.equalsIgnoreCase("PositivePay"))
          {
            j = 0;
            try
            {
              j = paramCSILEJBWrapper.getEJB().getNumIssues(localSecureUser, localHashMap);
            }
            catch (CSILException localCSILException)
            {
              AlertUtil.logEntry("Exception occurred in doProcessAlert.", this.aSp, this.aSi);
            }
            catch (Exception localException1)
            {
              try
              {
                paramCSILEJBWrapper.reconnect();
                j = paramCSILEJBWrapper.getEJB().getNumIssues(localSecureUser, localHashMap);
              }
              catch (Exception localException3) {}
            }
            if (j > 0)
            {
              MessageList localMessageList1 = new MessageList();
              initializePositivePayMessages(localMessageList1, j, localSecureUser.getLocale());
              AlertUtil.setProperty(paramArrayOfIAEAlertDefinition[k], "subject", localMessageList1.getSecureMessage().getSubject());
              AlertUtil.setProperty(paramArrayOfIAEAlertDefinition[k], "body", localMessageList1.getSecureMessage().getContent());
              jdMethod_for(paramArrayOfIAEAlertDefinition[k], localMessageList1, i, localProperties);
            }
          }
          else if (str3.equalsIgnoreCase("PaymentApprovals"))
          {
            try
            {
              j = paramCSILEJBWrapper.getEJB().getNumberOfPendingApprovals(localSecureUser, localHashMap);
            }
            catch (Exception localException2)
            {
              try
              {
                paramCSILEJBWrapper.reconnect();
                j = paramCSILEJBWrapper.getEJB().getNumberOfPendingApprovals(localSecureUser, localHashMap);
              }
              catch (Exception localException4) {}
            }
            if (j > 0)
            {
              MessageList localMessageList2 = new MessageList();
              ApprovalsItemCounts localApprovalsItemCounts = null;
              try
              {
                localApprovalsItemCounts = paramCSILEJBWrapper.getEJB().getNumberOfPendingApprovalsDetail(localSecureUser, localHashMap);
              }
              catch (Exception localException5)
              {
                try
                {
                  paramCSILEJBWrapper.reconnect();
                  localApprovalsItemCounts = paramCSILEJBWrapper.getEJB().getNumberOfPendingApprovalsDetail(localSecureUser, localHashMap);
                }
                catch (Exception localException6) {}
              }
              Iterator localIterator = localApprovalsItemCounts.iterator();
              initializePaymentApprovalMessages(localMessageList2, j, localIterator, localSecureUser.getLocale());
              AlertUtil.setProperty(paramArrayOfIAEAlertDefinition[k], "subject", localMessageList2.getSecureMessage().getSubject());
              AlertUtil.setProperty(paramArrayOfIAEAlertDefinition[k], "body", localMessageList2.getSecureMessage().getContent());
              jdMethod_for(paramArrayOfIAEAlertDefinition[k], localMessageList2, i, localProperties);
            }
          }
        }
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
    private PendingCheck.b jdField_if;
    
    public a(CSILEJBWrapper paramCSILEJBWrapper, PendingCheck.b paramb)
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
          PendingCheck.this.doProcessAlert(this.jdField_for, this.jdField_int, this.a, this.jdField_do);
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
        this.a = null;
        this.jdField_if = null;
        this.jdField_do = null;
      }
    }
    
    public void finalize()
      throws Throwable
    {
      this.a = null;
      this.jdField_if = null;
      this.jdField_do = null;
      super.finalize();
    }
    
    public void a(int paramInt1, int paramInt2, IAEAlertDefinition[] paramArrayOfIAEAlertDefinition)
    {
      this.jdField_for = paramInt1;
      this.jdField_int = paramInt2;
      this.jdField_do = ((IAEAlertDefinition[])paramArrayOfIAEAlertDefinition.clone());
    }
  }
  
  private class b
  {
    private PendingCheck.a[] jdField_if = null;
    private int a = 0;
    private int jdField_for = 0;
    private ArrayList jdField_do;
    
    public b(int paramInt, ArrayList paramArrayList)
    {
      this.a = paramInt;
      this.jdField_do = paramArrayList;
    }
    
    public void a(ArrayList paramArrayList)
    {
      this.jdField_if = new PendingCheck.a[this.a];
      for (int i = 0; i < this.a; i++)
      {
        this.jdField_if[i] = new PendingCheck.a(PendingCheck.this, (CSILEJBWrapper)paramArrayList.get(i), this);
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
      this.jdField_do = null;
      this.jdField_if = null;
      super.finalize();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.PendingCheck
 * JD-Core Version:    0.7.0.1
 */