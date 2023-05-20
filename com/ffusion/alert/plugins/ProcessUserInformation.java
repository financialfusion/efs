package com.ffusion.alert.plugins;

import com.ffusion.alert.clientEJB.IAEAlertClient;
import com.ffusion.alert.clientEJB.IAEAlertClientHome;
import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.plugins.message.Message;
import com.ffusion.alert.plugins.message.MessageImpl;
import com.ffusion.alert.plugins.message.MessageList;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportColumns;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportResults;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.reporting.ReportRows;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.ejb.ICSILEJB;
import com.ffusion.fx.FXUtil;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.HfnEncrypt;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProcessUserInformation
  extends BaseAlert
  implements IAEChannel, AlertChannelConstants
{
  private static final int aSO = 131072;
  private static final DateFormat aSP = new SimpleDateFormat("yyyyMMddHHmmss");
  protected String systemUser = "FileProcessAlert";
  protected String alertDeliveryTime = "";
  protected Properties deliveryProperties;
  protected IAEAlertClient uaeClientBean;
  protected String provider_url = "";
  protected String context_username = "";
  protected String context_password = "";
  protected String context_factory = "";
  protected String jndi_name = "";
  protected String appServer;
  protected long accountCount;
  protected final HashMap _acctBalanceBAICodeMap = new HashMap();
  protected final HashMap _baiTypeCodeMap = new HashMap();
  protected static final String THIS_PLUGIN_NAME = "ProcessUserInformation";
  protected static final String USER_INFO_FILE_PATH = "USER_INFO_FILE_PATH";
  public static final String USER_INFO_ARCHIVE_DIR = "USER_INFO_ARCHIVE_DIR";
  public static final String USER_INFO_ERROR_DIR = "USER_INFO_ERROR_DIR";
  protected static final String FILE_FORMAT = "FILE_FORMAT";
  protected static final String SYSTEM_USER = "SYSTEM_USER";
  protected static final String FILE_PROCESS_TIME = "FILE_PROCESS_TIME";
  public static final String DEPOSIT_BALANCE_BAI_CODES = "DEPOSIT_BALANCE_BAI_CODES";
  public static final String CC_BALANCE_BAI_CODES = "CC_BALANCE_BAI_CODES";
  public static final String ASSET_BALANCE_BAI_CODES = "ASSET_BALANCE_BAI_CODES";
  public static final String LOAN_BALANCE_BAI_CODES = "LOAN_BALANCE_BAI_CODES";
  public static final String OTHER_BALANCE_BAI_CODES = "OTHER_BALANCE_BAI_CODES";
  protected static final String BAI = "BAI";
  protected static final String UIF = "UIF";
  protected static final String WAS40 = "WAS4.0";
  protected static final String WEBSPHERE = "WEBSPHERE";
  protected static final String EASERVER = "EASERVER";
  protected static final char RECORD_DELIMETER = '/';
  protected static final char FIELD_DELIMETER = ',';
  protected static final String FILE_BEGIN_ID = "01";
  protected static final String BANK_BEGIN_ID = "02";
  protected static final String ACCOUNT_BEGIN_ID = "03";
  protected static final String ACCOUNT_END_ID = "49";
  protected static final String TRANSACTION_DETAIL_ID = "16";
  protected static final String CONTINUATION_ID = "88";
  protected static final String TRAILER_ID = "99";
  protected static final int CODE_LENGTH = 3;
  protected static final int ACCOUNT_RECORD = 1;
  protected static final int TRANSACTION_RECORD = 2;
  protected static final int IGNORE_RECORD = 3;
  protected static final int CODE_FIELD = 1;
  protected static final int SENDER_ID_FIELD = 2;
  protected static final int RECEIVER_ID_FIELD = 3;
  protected static final int FILE_CREATION_DATE = 4;
  protected static final int FILE_CREATE_TIME = 5;
  protected static final int FILE_ID_NUMBER = 6;
  protected final int PHYSICAL_RECORD_LEN = 7;
  protected static final int BLOCK_SIZE = 8;
  protected static final int VERSION_NUMBER = 9;
  protected static final int BANK_ROUTING_NUMBER = 3;
  protected static final int CUSTOMER_ACCT_NUMBER = 2;
  protected static final int CURRENCY_CODE = 3;
  protected static final int ACCOUNT_TYPE_CODE = 4;
  protected static final int ACCOUNT_AMOUNT = 5;
  protected static final int ITEM_COUNT = 6;
  protected static final int ACCOUNT_FUNDS_TYPE = 7;
  protected static final int AVAILABILITY = 8;
  protected static final int TRANSACTION_TYPE_CODE = 2;
  protected static final int TRANSACTION_AMOUNT = 3;
  protected static final int TRANSACTION_FUNDS_TYPE = 4;
  protected static final int BANK_REF_NUMBER = 5;
  protected static final int CUSTOMER_REF_NUMBER = 6;
  protected static final int TRANSFER_TEXT = 7;
  protected static final char FUNDS_TYPE_S = 'S';
  protected static final char FUNDS_TYPE_V = 'V';
  protected static final char FUNDS_TYPE_D = 'D';
  protected static final String TC_OVERDRAFT = "563";
  protected static final String USER = "USER";
  protected static final String USER_ID = "ID";
  protected static final String ACCOUNT = "ACCOUNT";
  protected static final String NUMBER = "NUMBER";
  protected static final String TYPE = "TYPE";
  protected static final String AVAILABLEBALANCE = "AVAILABLEBALANCE";
  protected static final String NSF = "NSF";
  protected static final String UIF_FILE_NOT_FOUND = "UIF File not found error.";
  protected static final String UIF_INVALID_FILE_FORMAT = "UIF Invalid file format error.";
  protected static final String UIF_FILE_READ_ERROR = "UIF File read error.";
  protected static final int ALERT_TRANSACTION_RPT_PROCESSING_DATE_INDEX = 0;
  protected static final int ALERT_TRANSACTION_RPT_TRANS_TYPE_INDEX = 1;
  protected static final int ALERT_TRANSACTION_RPT_AMOUNT_INDEX = 2;
  protected static final int ALERT_TRANSACTION_RPT_MEMO_INDEX = 3;
  protected static final int ALERT_TRANSACTION_RPT_ACCOUNT_ID_INDEX = 4;
  protected static final int DEFAULT_EFS_ALERT_PAGE_SIZE = 2500;
  protected static final int DEFAULT_NUM_EFS_WORKER_THREADS = 5;
  protected static final int DEFAULT_ALERT_PAGE_SIZE = 200;
  protected static final int DEFAULT_NUM_WORKER_THREADS = 20;
  protected int alertPageSize = 200;
  protected int numWorkerThreads = 20;
  protected int efsAlertPageSize = 2500;
  protected int numEFSWorkerThreads = 5;
  protected static final int IS_ABOVE = 0;
  protected static final int IS_BELOW = 1;
  protected static final int IS_EQUAL = 2;
  protected static final int IS_ANYAMOUNT = 3;
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    init(paramProperties, paramIAEAlertEngine, paramPrintWriter, 0);
  }
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter, int paramInt)
    throws Exception
  {
    setUpEnvironment(paramProperties, paramIAEAlertEngine, paramPrintWriter, new OnAlertConnectionHandler()
    {
      public void initializePriorToCSILConnect()
      {
        ProcessUserInformation.this.context_factory = ProcessUserInformation.this.getInitProps().getProperty("UAE_INITIAL_CONTEXT_FACTORY");
        ProcessUserInformation.this.provider_url = ProcessUserInformation.this.getInitProps().getProperty("UAE_PROVIDER_URL");
        ProcessUserInformation.this.context_username = ProcessUserInformation.this.getInitProps().getProperty("UAE_SECURITY_PRINCIPAL");
        ProcessUserInformation.this.logEntry("Attempting to decrypt the UAE password");
        try
        {
          ProcessUserInformation.this.context_password = HfnEncrypt.decryptHexEncode(ProcessUserInformation.this.getInitProps().getProperty("UAE_SECURITY_CREDENTIALS"));
        }
        catch (Exception localException)
        {
          ProcessUserInformation.this.logEntry(localException);
          ProcessUserInformation.this.context_password = ProcessUserInformation.this.getInitProps().getProperty("UAE_SECURITY_CREDENTIALS");
        }
        ProcessUserInformation.this.jndi_name = ProcessUserInformation.this.getInitProps().getProperty("UAE_JNDI_NAME");
        ProcessUserInformation.this.alertDeliveryTime = ProcessUserInformation.this.getInitProps().getProperty("ALERT_DELIVERY_TIME");
        ProcessUserInformation.this.appServer = ProcessUserInformation.this.getInitProps().getProperty("UAE_APPSERVER", "");
      }
      
      public void initializePriorToStartingAlertThread() {}
    });
    if (isUseCsil()) {
      initBAITypeMaps(paramProperties);
    }
    FXUtil.initialize(new HashMap());
    getWorkerThreadsAndPageSizeInfo(paramProperties);
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
    str1 = paramProperties.getProperty("EFS_APP_ALERTS_PAGE_SIZE");
    str2 = paramProperties.getProperty("EFS_NUM_PROCESSING_THREADS");
    if (str1 != null) {
      try
      {
        this.efsAlertPageSize = Integer.parseInt(str1);
      }
      catch (Exception localException3)
      {
        this.efsAlertPageSize = 2500;
      }
    }
    if (str2 != null) {
      try
      {
        this.numEFSWorkerThreads = Integer.parseInt(str2);
      }
      catch (Exception localException4)
      {
        this.numEFSWorkerThreads = 5;
      }
    }
    logEntry("efs alert page size : " + this.efsAlertPageSize + " worker thread count: " + this.numEFSWorkerThreads);
  }
  
  protected void initBAITypeMaps(Properties paramProperties)
    throws NamingException, CreateException, RemoteException, EJBException, CSILException
  {
    ICSILEJB localICSILEJB = null;
    try
    {
      reconnectToCsil(false);
      localICSILEJB = getCsilInstance();
    }
    catch (Exception localException1)
    {
      logEntry(localException1);
    }
    String str = paramProperties.getProperty("DEPOSIT_BALANCE_BAI_CODES");
    int[] arrayOfInt;
    int i;
    if ((str != null) && (str.length() > 0))
    {
      arrayOfInt = ak(str);
      this._acctBalanceBAICodeMap.put(new Integer(1), arrayOfInt);
      if ((localICSILEJB != null) && (arrayOfInt != null)) {
        for (i = 0; i < arrayOfInt.length; i++) {
          try
          {
            BAITypeCodeInfo localBAITypeCodeInfo1 = localICSILEJB.getBAITypeCodeInfo(arrayOfInt[i]);
            this._baiTypeCodeMap.put(new Integer(arrayOfInt[i]), localBAITypeCodeInfo1);
          }
          catch (RemoteException localRemoteException)
          {
            try
            {
              reconnectToCsil(true);
              localICSILEJB = getCsilInstance();
              BAITypeCodeInfo localBAITypeCodeInfo6 = localICSILEJB.getBAITypeCodeInfo(arrayOfInt[i]);
              this._baiTypeCodeMap.put(new Integer(arrayOfInt[i]), localBAITypeCodeInfo6);
            }
            catch (Exception localException7)
            {
              logEntry("Failed to get BAI type code info for code " + arrayOfInt[i]);
              logEntry(localException7);
            }
          }
          catch (Exception localException2)
          {
            logEntry("Failed to get BAI type code info for code " + arrayOfInt[i]);
            logEntry(localException2);
          }
        }
      }
    }
    str = paramProperties.getProperty("ASSET_BALANCE_BAI_CODES");
    if ((str != null) && (str.length() > 0))
    {
      arrayOfInt = ak(str);
      this._acctBalanceBAICodeMap.put(new Integer(2), arrayOfInt);
      if ((localICSILEJB != null) && (arrayOfInt != null)) {
        for (i = 0; i < arrayOfInt.length; i++) {
          try
          {
            BAITypeCodeInfo localBAITypeCodeInfo2 = localICSILEJB.getBAITypeCodeInfo(arrayOfInt[i]);
            this._baiTypeCodeMap.put(new Integer(arrayOfInt[i]), localBAITypeCodeInfo2);
          }
          catch (Exception localException3)
          {
            logEntry("Failed to get BAI type code info for code " + arrayOfInt[i]);
            logEntry(localException3);
          }
        }
      }
    }
    str = paramProperties.getProperty("LOAN_BALANCE_BAI_CODES");
    if ((str != null) && (str.length() > 0))
    {
      arrayOfInt = ak(str);
      this._acctBalanceBAICodeMap.put(new Integer(3), arrayOfInt);
      if ((localICSILEJB != null) && (arrayOfInt != null)) {
        for (i = 0; i < arrayOfInt.length; i++) {
          try
          {
            BAITypeCodeInfo localBAITypeCodeInfo3 = localICSILEJB.getBAITypeCodeInfo(arrayOfInt[i]);
            this._baiTypeCodeMap.put(new Integer(arrayOfInt[i]), localBAITypeCodeInfo3);
          }
          catch (Exception localException4)
          {
            logEntry("Failed to get BAI type code info for code " + arrayOfInt[i]);
            logEntry(localException4);
          }
        }
      }
    }
    str = paramProperties.getProperty("CC_BALANCE_BAI_CODES");
    if ((str != null) && (str.length() > 0))
    {
      arrayOfInt = ak(str);
      this._acctBalanceBAICodeMap.put(new Integer(4), arrayOfInt);
      if ((localICSILEJB != null) && (arrayOfInt != null)) {
        for (i = 0; i < arrayOfInt.length; i++) {
          try
          {
            BAITypeCodeInfo localBAITypeCodeInfo4 = localICSILEJB.getBAITypeCodeInfo(arrayOfInt[i]);
            this._baiTypeCodeMap.put(new Integer(arrayOfInt[i]), localBAITypeCodeInfo4);
          }
          catch (Exception localException5)
          {
            logEntry("Failed to get BAI type code info for code " + arrayOfInt[i]);
            logEntry(localException5);
          }
        }
      }
    }
    str = paramProperties.getProperty("OTHER_BALANCE_BAI_CODES");
    if ((str != null) && (str.length() > 0))
    {
      arrayOfInt = ak(str);
      this._acctBalanceBAICodeMap.put(new Integer(5), arrayOfInt);
      if ((localICSILEJB != null) && (arrayOfInt != null)) {
        for (i = 0; i < arrayOfInt.length; i++) {
          try
          {
            BAITypeCodeInfo localBAITypeCodeInfo5 = localICSILEJB.getBAITypeCodeInfo(arrayOfInt[i]);
            this._baiTypeCodeMap.put(new Integer(arrayOfInt[i]), localBAITypeCodeInfo5);
          }
          catch (Exception localException6)
          {
            logEntry("Failed to get BAI type code info for code " + arrayOfInt[i]);
            logEntry(localException6);
          }
        }
      }
    }
  }
  
  private int[] ak(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    int i = localStringTokenizer.countTokens();
    ArrayList localArrayList = new ArrayList(i);
    while (localStringTokenizer.hasMoreTokens())
    {
      localObject = localStringTokenizer.nextToken();
      try
      {
        localArrayList.add(new Integer((String)localObject));
      }
      catch (NumberFormatException localNumberFormatException)
      {
        logEntry("Failed to parse BAI type code '" + (String)localObject + "' from BAI type code string due to NumberFormatException. Code ignored.");
      }
    }
    Object localObject = new int[localArrayList.size()];
    for (int j = 0; j < localObject.length; j++) {
      localObject[j] = ((Integer)localArrayList.get(j)).intValue();
    }
    return localObject;
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    if (isUseCsil()) {
      return processCBAlerts(paramIAEAlertInstance, paramProperties);
    }
    return processEFSAlerts(paramIAEAlertInstance, paramProperties);
  }
  
  public int processCBAlerts(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    logEntry("Processing Alert...");
    int i = 0;
    try
    {
      int j = 0;
      AEApplicationInfo localAEApplicationInfo = getAppInfo();
      int k = 0;
      this.deliveryProperties = paramProperties;
      IAEAlertDefinition[] arrayOfIAEAlertDefinition = null;
      if ((!this.appServer.equalsIgnoreCase("WAS4.0")) && (!this.appServer.equalsIgnoreCase("WEBSPHERE")) && (!this.appServer.equalsIgnoreCase("EASERVER")))
      {
        localObject1 = new Properties();
        ((Properties)localObject1).put("java.naming.factory.initial", this.context_factory);
        ((Properties)localObject1).put("java.naming.provider.url", this.provider_url);
        ((Properties)localObject1).put("java.naming.security.principal", this.context_username);
        ((Properties)localObject1).put("java.naming.security.credentials", this.context_password);
        localObject2 = new InitialContext((Hashtable)localObject1);
        IAEAlertClientHome localIAEAlertClientHome = (IAEAlertClientHome)((Context)localObject2).lookup(this.jndi_name);
        this.uaeClientBean = localIAEAlertClientHome.create();
      }
      reconnectToCsil(true);
      Object localObject1 = new ArrayList();
      Object localObject2 = new b(this.numWorkerThreads, (ArrayList)localObject1);
      createEJBs(this.numWorkerThreads);
      synchronized (localObject2)
      {
        ((b)localObject2).a(getCsilInstances());
      }
      while (k == 0)
      {
        if ((!this.appServer.equalsIgnoreCase("WAS4.0")) && (!this.appServer.equalsIgnoreCase("WEBSPHERE")) && (!this.appServer.equalsIgnoreCase("EASERVER"))) {
          arrayOfIAEAlertDefinition = this.uaeClientBean.getAppAlertsForChannelPaged(localAEApplicationInfo, true, getBalanceCheckPluginName(), j, this.alertPageSize);
        } else {
          arrayOfIAEAlertDefinition = getEngine().getAppAlertsForChannelPaged(localAEApplicationInfo, true, getBalanceCheckPluginName(), j, this.alertPageSize);
        }
        synchronized (localObject1)
        {
          if (((ArrayList)localObject1).size() <= 0) {}
        }
        if ((arrayOfIAEAlertDefinition.length > 0) && (arrayOfIAEAlertDefinition.length < this.alertPageSize)) {
          k = 1;
        } else {
          if (arrayOfIAEAlertDefinition.length == 0) {
            break;
          }
        }
        synchronized (localObject2)
        {
          ((b)localObject2).a(arrayOfIAEAlertDefinition);
        }
        j = arrayOfIAEAlertDefinition[(arrayOfIAEAlertDefinition.length - 1)].getId();
      }
      synchronized (localObject1)
      {
        if (((ArrayList)localObject1).size() <= 0) {}
      }
      synchronized (localObject2)
      {
        ((b)localObject2).jdMethod_if();
      }
      if ((!this.appServer.equalsIgnoreCase("WAS4.0")) && (!this.appServer.equalsIgnoreCase("WEBSPHERE")) && (!this.appServer.equalsIgnoreCase("EASERVER"))) {
        this.uaeClientBean.remove();
      }
    }
    catch (Exception localException)
    {
      logEntry(localException);
    }
    finally
    {
      cleaupCSILEJBs();
    }
    return i;
  }
  
  public int processEFSAlerts(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    logEntry("Processing Alert...");
    int i = 0;
    try
    {
      int j = 0;
      AEApplicationInfo localAEApplicationInfo = getAppInfo();
      int k = 0;
      this.deliveryProperties = paramProperties;
      IAEAlertDefinition[] arrayOfIAEAlertDefinition = null;
      int m = 1;
      String str1 = getInitProps().getProperty("USER_INFO_FILE_PATH");
      String str2 = getInitProps().getProperty("USER_INFO_ARCHIVE_DIR");
      String str3 = getInitProps().getProperty("USER_INFO_ERROR_DIR");
      File localFile = new File(str1);
      if (!localFile.exists()) {
        return 0;
      }
      if ((!this.appServer.equalsIgnoreCase("WAS4.0")) && (!this.appServer.equalsIgnoreCase("WEBSPHERE")) && (!this.appServer.equalsIgnoreCase("EASERVER")))
      {
        localObject1 = new Properties();
        ((Properties)localObject1).put("java.naming.factory.initial", this.context_factory);
        ((Properties)localObject1).put("java.naming.provider.url", this.provider_url);
        ((Properties)localObject1).put("java.naming.security.principal", this.context_username);
        ((Properties)localObject1).put("java.naming.security.credentials", this.context_password);
        localObject2 = new InitialContext((Hashtable)localObject1);
        IAEAlertClientHome localIAEAlertClientHome = (IAEAlertClientHome)((Context)localObject2).lookup(this.jndi_name);
        this.uaeClientBean = localIAEAlertClientHome.create();
      }
      Object localObject1 = new ArrayList();
      Object localObject2 = new d(this.numEFSWorkerThreads, (ArrayList)localObject1);
      synchronized (localObject2)
      {
        ((d)localObject2).jdMethod_for();
      }
      while ((k == 0) && (m != 0))
      {
        while ((k == 0) && (((d)localObject2).jdMethod_do()))
        {
          if ((!this.appServer.equalsIgnoreCase("WAS4.0")) && (!this.appServer.equalsIgnoreCase("WEBSPHERE")) && (!this.appServer.equalsIgnoreCase("EASERVER"))) {
            arrayOfIAEAlertDefinition = this.uaeClientBean.getAppAlertsForChannelPaged(localAEApplicationInfo, true, getBalanceCheckPluginName(), j, this.efsAlertPageSize);
          } else {
            arrayOfIAEAlertDefinition = getEngine().getAppAlertsForChannelPaged(localAEApplicationInfo, true, getBalanceCheckPluginName(), j, this.efsAlertPageSize);
          }
          ((d)localObject2).a(arrayOfIAEAlertDefinition);
          j = arrayOfIAEAlertDefinition[(arrayOfIAEAlertDefinition.length - 1)].getId();
          if (((arrayOfIAEAlertDefinition.length > 0) && (arrayOfIAEAlertDefinition.length < this.efsAlertPageSize)) || (arrayOfIAEAlertDefinition.length == 0)) {
            k = 1;
          }
        }
        synchronized (localObject2)
        {
          ((d)localObject2).a();
        }
        synchronized (localObject1)
        {
          if (((ArrayList)localObject1).size() > 0) {
            localObject1.wait();
          }
        }
        m = ((d)localObject2).jdMethod_int() == 0 ? 1 : 0;
      }
      synchronized (localObject1)
      {
        if (((ArrayList)localObject1).size() > 0) {
          localObject1.wait();
        }
      }
      synchronized (localObject2)
      {
        ((d)localObject2).jdMethod_new();
      }
      if (m != 0) {
        jdMethod_for(str1, str2, isDebugOn(), getWriter());
      } else {
        jdMethod_for(str1, str3, isDebugOn(), getWriter());
      }
      try
      {
        localFile.delete();
      }
      catch (SecurityException localSecurityException)
      {
        logEntry(localSecurityException, "Error deleting the file");
      }
      if ((!this.appServer.equalsIgnoreCase("WAS4.0")) && (!this.appServer.equalsIgnoreCase("WEBSPHERE")) && (!this.appServer.equalsIgnoreCase("EASERVER"))) {
        this.uaeClientBean.remove();
      }
    }
    catch (Exception localException)
    {
      logEntry(localException);
    }
    return i;
  }
  
  protected void addNsfNewAccountMessages(MessageList paramMessageList, String paramString)
  {
    paramMessageList.getSecureMessage().addContent(paramString);
  }
  
  public void stop()
  {
    logEntry("Stopping ProcessUserInformation plugin...");
  }
  
  protected int processBAIFile(IAEAlertDefinition[] paramArrayOfIAEAlertDefinition, HashMap paramHashMap)
  {
    logEntry("Processing BAI File...");
    File localFile = null;
    RandomAccessFile localRandomAccessFile = null;
    this.accountCount = 0L;
    int i = 0;
    String str1 = getInitProps().getProperty("USER_INFO_FILE_PATH");
    try
    {
      localFile = new File(str1);
      if (!localFile.exists())
      {
        j = 0;
        return j;
      }
      localRandomAccessFile = new RandomAccessFile(localFile, "r");
      int j = 0;
      ArrayList localArrayList = new ArrayList();
      StringBuffer localStringBuffer = new StringBuffer();
      String str2 = "";
      String str3 = "";
      if (!readBAIFileHeader(localRandomAccessFile, localStringBuffer))
      {
        logEntry("Invalid BAI file format.  File header missing.");
        throw new Exception("Invalid BAI file format.  File header missing.");
      }
      j = Integer.parseInt(localStringBuffer.toString());
      for (;;)
      {
        if (!readRecord(localRandomAccessFile, j, localArrayList))
        {
          int k = 0;
          return k;
        }
        String str4 = (String)localArrayList.get(0);
        if (str4.equals("02")) {
          str3 = (String)localArrayList.get(2);
        }
        if (str4.equals("03"))
        {
          this.accountCount += 1L;
          str2 = (String)localArrayList.get(1);
          baiProcessAccountRecord(paramArrayOfIAEAlertDefinition, paramHashMap, localArrayList, str3);
        }
        else if (str4.equals("16"))
        {
          baiProcessTransactionRecord(paramArrayOfIAEAlertDefinition, paramHashMap, str2, localArrayList, str3);
        }
        else if (str4.equals("99"))
        {
          i = 1;
          break;
        }
        localArrayList.clear();
      }
    }
    catch (EOFException localEOFException)
    {
      logEntry(localEOFException);
    }
    catch (Exception localException)
    {
      logEntry(localException);
    }
    finally
    {
      if (localRandomAccessFile != null) {
        try
        {
          localRandomAccessFile.close();
        }
        catch (IOException localIOException)
        {
          logEntry(localIOException);
        }
      }
    }
    return i == 1 ? 0 : -1;
  }
  
  protected boolean readBAIFileHeader(RandomAccessFile paramRandomAccessFile, StringBuffer paramStringBuffer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    try
    {
      i = readBAIField(paramRandomAccessFile, localStringBuffer);
      if ((i != -1) && (!localStringBuffer.toString().equals("01"))) {
        return false;
      }
      i += readBAIField(paramRandomAccessFile, localStringBuffer);
      i += readBAIField(paramRandomAccessFile, localStringBuffer);
      i += readBAIField(paramRandomAccessFile, localStringBuffer);
      i += readBAIField(paramRandomAccessFile, localStringBuffer);
      i += readBAIField(paramRandomAccessFile, localStringBuffer);
      i += readBAIField(paramRandomAccessFile, paramStringBuffer);
      paramStringBuffer.delete(0, paramStringBuffer.length());
      paramStringBuffer.append("0");
      readBAIEndOfRecord(paramRandomAccessFile, false, Integer.parseInt(paramStringBuffer.toString()), i);
    }
    catch (Exception localException)
    {
      logEntry(localException, "Exception occurred reading BAI file header with message : ");
      return false;
    }
    return true;
  }
  
  protected boolean readRecord(RandomAccessFile paramRandomAccessFile, int paramInt, ArrayList paramArrayList)
  {
    try
    {
      int i = 0;
      int j = 0;
      int k = 0;
      int m = 0;
      int n = 0;
      StringBuffer localStringBuffer = new StringBuffer();
      k = readBAIField(paramRandomAccessFile, localStringBuffer);
      n++;
      String str = localStringBuffer.toString();
      if ((str == null) || (str.length() <= 0)) {
        return true;
      }
      paramArrayList.add(str);
      if (str.equals("02")) {
        while (i == 0)
        {
          localStringBuffer.delete(0, localStringBuffer.length());
          n++;
          j = readBAIField(paramRandomAccessFile, localStringBuffer);
          if (j == -1)
          {
            paramArrayList.add(localStringBuffer.toString());
            k += localStringBuffer.length() + 1;
            if (paramInt > 0) {
              paramRandomAccessFile.skipBytes(paramInt - k);
            }
            localStringBuffer.delete(0, localStringBuffer.length());
            k = readBAIField(paramRandomAccessFile, localStringBuffer);
            if (!localStringBuffer.toString().equals("88"))
            {
              paramRandomAccessFile.seek(paramRandomAccessFile.getFilePointer() - 3L);
              return true;
            }
          }
          else
          {
            paramArrayList.add(localStringBuffer.toString());
            k += j;
          }
        }
      }
      if ((!str.equals("03")) && (!str.equals("16")) && (!str.equals("88")))
      {
        if (paramInt > 0) {
          paramRandomAccessFile.skipBytes(paramInt - k);
        } else {
          readBAIEndOfRecord(paramRandomAccessFile, false, paramInt, k);
        }
        return true;
      }
      while (i == 0)
      {
        localStringBuffer.delete(0, localStringBuffer.length());
        n++;
        if (m == n)
        {
          if (paramInt == 0) {
            baiReadTextField(paramRandomAccessFile);
          }
          j = -1;
        }
        else
        {
          j = readBAIField(paramRandomAccessFile, localStringBuffer);
        }
        if ((n == 4) && (str.equals("16"))) {
          m = calculateTextField(paramRandomAccessFile, localStringBuffer.toString());
        }
        if (j == -1)
        {
          paramArrayList.add(localStringBuffer.toString());
          k += localStringBuffer.length() + 1;
          if (paramInt > 0) {
            paramRandomAccessFile.skipBytes(paramInt - k);
          }
          localStringBuffer.delete(0, localStringBuffer.length());
          k = readBAIField(paramRandomAccessFile, localStringBuffer);
          if (!localStringBuffer.toString().equals("88"))
          {
            paramRandomAccessFile.seek(paramRandomAccessFile.getFilePointer() - 3L);
            return true;
          }
        }
        else
        {
          paramArrayList.add(localStringBuffer.toString());
          k += j;
        }
      }
    }
    catch (Exception localException)
    {
      logEntry(localException);
    }
    return false;
  }
  
  protected int calculateTextField(RandomAccessFile paramRandomAccessFile, String paramString)
  {
    try
    {
      if (paramString.charAt(0) == 'S') {
        return 10;
      }
      if (paramString.charAt(0) == 'V') {
        return 9;
      }
      if (paramString.charAt(0) == 'D')
      {
        StringBuffer localStringBuffer = new StringBuffer();
        readBAIField(paramRandomAccessFile, localStringBuffer);
        int i = Integer.parseInt(localStringBuffer.toString());
        return 4 + i * 2;
      }
      return 7;
    }
    catch (Exception localException)
    {
      logEntry(localException);
    }
    return 0;
  }
  
  protected int readBAIField(RandomAccessFile paramRandomAccessFile, StringBuffer paramStringBuffer)
    throws Exception
  {
    int i = 0;
    try
    {
      int j = 0;
      for (int k = 0;; k = 1)
      {
        do
        {
          i = (char)paramRandomAccessFile.read();
          if (i == 65535) {
            break;
          }
          j++;
        } while ((Character.isWhitespace(i)) && (k == 0));
        if (i == 44) {
          return j;
        }
        if (i == 47) {
          return -1;
        }
        paramStringBuffer.append(i);
      }
    }
    catch (Exception localException)
    {
      logEntry(localException);
      throw localException;
    }
    if (i == 65535) {
      throw new Exception("End of file reached");
    }
    return 0;
  }
  
  protected int baiReadTextField(RandomAccessFile paramRandomAccessFile)
  {
    try
    {
      StringBuffer localStringBuffer = new StringBuffer();
      int i = paramRandomAccessFile.read();
      localStringBuffer.append((char)i);
      if ((char)i == '/') {
        return 1;
      }
      i = paramRandomAccessFile.read();
      localStringBuffer.append((char)i);
      i = paramRandomAccessFile.read();
      localStringBuffer.append((char)i);
      i = paramRandomAccessFile.read();
      for (int j = 4; i != -1; j++)
      {
        String str = localStringBuffer.toString().substring(localStringBuffer.length() - 3, localStringBuffer.length());
        if ((str.equals("49,")) || (str.equals("16,")) || (str.equals("88,")))
        {
          paramRandomAccessFile.seek(paramRandomAccessFile.getFilePointer() - 3L);
          return j;
        }
        localStringBuffer.append((char)i);
        i = paramRandomAccessFile.read();
      }
    }
    catch (Exception localException)
    {
      logEntry(localException);
    }
    return -1;
  }
  
  protected int readBAIEndOfRecord(RandomAccessFile paramRandomAccessFile, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    try
    {
      int i = paramRandomAccessFile.read();
      if (paramBoolean)
      {
        StringBuffer localStringBuffer = new StringBuffer();
        while (i != -1)
        {
          if ((char)i == ',')
          {
            String str = localStringBuffer.toString().substring(localStringBuffer.length() - 3, localStringBuffer.length() - 1);
            if ((str.equals("49")) || (str.equals("16")) || (str.equals("88"))) {
              return 0;
            }
          }
          else
          {
            localStringBuffer.append((char)i);
          }
          i = paramRandomAccessFile.read();
        }
      }
      else
      {
        while (i != -1)
        {
          if ((char)i == '/') {
            return 0;
          }
          i = paramRandomAccessFile.read();
        }
      }
    }
    catch (Exception localException)
    {
      logEntry(localException);
    }
    return 0;
  }
  
  protected boolean baiProcessAccountRecord(IAEAlertDefinition[] paramArrayOfIAEAlertDefinition, HashMap paramHashMap, ArrayList paramArrayList, String paramString)
  {
    logEntry("Processing method baiProcessAccountRecord()...");
    AlertUserInfo localAlertUserInfo = new AlertUserInfo();
    localAlertUserInfo.setAccountID((String)paramArrayList.get(1));
    String str1 = (String)paramArrayList.get(4);
    if ((str1 == null) || (str1.trim().length() == 0)) {
      str1 = "0";
    }
    localAlertUserInfo.setBalance(Double.parseDouble(str1) / 100.0D);
    localAlertUserInfo.setNsf(((String)paramArrayList.get(2)).equals("563"));
    String str2 = localAlertUserInfo.getAccountID();
    String str3 = paramString + " : " + str2;
    if (paramHashMap.containsKey(str3))
    {
      String str4 = (String)paramHashMap.get(str3);
      if (str4 == null) {
        return false;
      }
      StringTokenizer localStringTokenizer = new StringTokenizer(str4, ",");
      if (localStringTokenizer != null) {
        while (localStringTokenizer.hasMoreTokens())
        {
          String str5 = localStringTokenizer.nextToken();
          if (str5 != null) {
            for (int i = 0; i < paramArrayOfIAEAlertDefinition.length; i++) {
              if (paramArrayOfIAEAlertDefinition[i].getId() == Integer.parseInt(str5))
              {
                checkForAlert(paramArrayOfIAEAlertDefinition[i], localAlertUserInfo);
                break;
              }
            }
          }
        }
      }
    }
    return true;
  }
  
  protected boolean baiProcessTransactionRecord(IAEAlertDefinition[] paramArrayOfIAEAlertDefinition, HashMap paramHashMap, String paramString1, ArrayList paramArrayList, String paramString2)
  {
    logEntry("Processing method baiProcessTransactionRecord()...");
    AlertUserInfo localAlertUserInfo = new AlertUserInfo();
    if (paramArrayList.get(1).equals("563"))
    {
      localAlertUserInfo.setNsf(true);
      localAlertUserInfo.setBalance(0.0D);
      localAlertUserInfo.setAccountID(paramString1);
    }
    String str1 = paramString2 + " : " + paramString1;
    if (paramHashMap.containsKey(str1))
    {
      String str2 = (String)paramHashMap.get(str1);
      if (str2 == null) {
        return false;
      }
      StringTokenizer localStringTokenizer = new StringTokenizer(str2, ",");
      if (localStringTokenizer != null) {
        while (localStringTokenizer.hasMoreTokens())
        {
          String str3 = localStringTokenizer.nextToken();
          if (str3 != null) {
            for (int i = 0; i < paramArrayOfIAEAlertDefinition.length; i++) {
              if (paramArrayOfIAEAlertDefinition[i].getId() == Integer.parseInt(str3))
              {
                checkForAlert(paramArrayOfIAEAlertDefinition[i], localAlertUserInfo);
                break;
              }
            }
          }
        }
      }
    }
    return true;
  }
  
  protected int processUIFFile(IAEAlertDefinition[] paramArrayOfIAEAlertDefinition, HashMap paramHashMap)
    throws Exception
  {
    logEntry("Processing method processUIFFile...");
    String str = "";
    ArrayList localArrayList = new ArrayList();
    try
    {
      FileInputStream localFileInputStream = null;
      try
      {
        localFileInputStream = new FileInputStream(getInitProps().getProperty("USER_INFO_FILE_PATH"));
      }
      catch (Throwable localThrowable3)
      {
        throw new Exception("UIF File not found error.");
      }
      if (localFileInputStream == null) {
        throw new Exception("UIF File not found error.");
      }
      str = Strings.streamToString(localFileInputStream);
    }
    catch (Throwable localThrowable1)
    {
      throw new Exception("UIF File read error.");
    }
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new c(paramArrayOfIAEAlertDefinition, paramHashMap, localArrayList), str);
    }
    catch (Throwable localThrowable2)
    {
      throw new Exception("UIF Invalid file format error.");
    }
    return 0;
  }
  
  protected void checkForAlert(IAEAlertDefinition paramIAEAlertDefinition, AlertUserInfo paramAlertUserInfo)
  {
    int i = getPluginLocation(paramIAEAlertDefinition, getBalanceCheckPluginName());
    Properties localProperties = paramIAEAlertDefinition.getDeliveryInfo()[i].getDeliveryProperties();
    checkForAlert(paramIAEAlertDefinition, paramAlertUserInfo, i, localProperties);
  }
  
  protected void checkForAlert(IAEAlertDefinition paramIAEAlertDefinition, AlertUserInfo paramAlertUserInfo, int paramInt, Properties paramProperties)
  {
    logEntry("Checking for alert...");
    try
    {
      String str1 = "";
      double d = 0.0D;
      boolean bool1 = false;
      if ((paramAlertUserInfo != null) && (paramIAEAlertDefinition.getDeliveryInfo()[paramInt].isSuspended() != true))
      {
        SecureUser localSecureUser = new SecureUser();
        localSecureUser.setXML(paramProperties.getProperty("SECUREUSER"));
        String str2 = paramProperties.getProperty("PREFERREDLANGUAGE");
        if ((str2 != null) && (str2.trim().length() > 0)) {
          localSecureUser.setLocale(str2);
        }
        str1 = paramAlertUserInfo.getAccountID();
        d = paramAlertUserInfo.getBalanceValue();
        bool1 = paramAlertUserInfo.getNsfValue();
        if ((paramProperties.getProperty("REALACCOUNTNUMBER") != null) && (paramProperties.getProperty("REALACCOUNTNUMBER").equals(str1)))
        {
          int i = 0;
          String str3 = "";
          if ((paramProperties.getProperty("MINAMOUNT") != null) && (paramProperties.getProperty("MINAMOUNT").length() != 0))
          {
            DecimalFormat localDecimalFormat = (DecimalFormat)DecimalFormat.getCurrencyInstance(paramAlertUserInfo.getLocale());
            char c1 = localDecimalFormat.getDecimalFormatSymbols().getGroupingSeparator();
            String str4 = localDecimalFormat.getDecimalFormatSymbols().getCurrencySymbol();
            str3 = paramProperties.getProperty("MINAMOUNT");
            str3 = Strings.removeChars(str3, c1);
            str3 = Strings.replaceStr(str3, str4, "");
            if (Double.parseDouble(str3) > d) {
              i = 1;
            }
          }
          boolean bool2 = false;
          Object localObject1;
          String str5;
          if ((paramProperties.getProperty("MAXAMOUNT") != null) && (paramProperties.getProperty("MAXAMOUNT").length() != 0))
          {
            localObject1 = (DecimalFormat)DecimalFormat.getCurrencyInstance(paramAlertUserInfo.getLocale());
            char c2 = ((DecimalFormat)localObject1).getDecimalFormatSymbols().getGroupingSeparator();
            str5 = ((DecimalFormat)localObject1).getDecimalFormatSymbols().getCurrencySymbol();
            str3 = paramProperties.getProperty("MAXAMOUNT");
            str3 = Strings.removeChars(str3, c2);
            str3 = Strings.replaceStr(str3, str5, "");
            if (Double.parseDouble(str3) < d) {
              bool2 = true;
            }
          }
          Object localObject2;
          Object localObject3;
          Object localObject4;
          Object localObject5;
          Object localObject6;
          Object localObject7;
          if ((i != 0) || (bool2))
          {
            localObject1 = new MessageList();
            localObject2 = new Currency(Double.toString(d), paramAlertUserInfo.getLocale());
            addAccountBalanceMessage(paramProperties, localSecureUser.getLocale(), (Currency)localObject2, "amount", Double.parseDouble(str3), bool2, (MessageList)localObject1);
            addAccountBalanceLastMessage(localSecureUser.getLocale(), (MessageList)localObject1);
            createAlert(paramIAEAlertDefinition, paramInt, (MessageList)localObject1);
            str5 = paramProperties.getProperty("ONETIME");
            if ((str5 != null) && (str5.length() != 0) && (Boolean.valueOf(str5).booleanValue() == true))
            {
              localObject3 = paramIAEAlertDefinition.getDeliveryInfo();
              localObject3[paramInt].setSuspended(true);
              if ((!this.appServer.equalsIgnoreCase("WAS4.0")) && (!this.appServer.equalsIgnoreCase("WEBSPHERE")) && (!this.appServer.equalsIgnoreCase("EASERVER")))
              {
                localObject4 = new Properties();
                ((Properties)localObject4).put("java.naming.factory.initial", this.context_factory);
                ((Properties)localObject4).put("java.naming.provider.url", this.provider_url);
                ((Properties)localObject4).put("java.naming.security.principal", this.context_username);
                ((Properties)localObject4).put("java.naming.security.credentials", this.context_password);
                localObject5 = new InitialContext((Hashtable)localObject4);
                localObject6 = (IAEAlertClientHome)((Context)localObject5).lookup(this.jndi_name);
                this.uaeClientBean = ((IAEAlertClientHome)localObject6).create();
                localObject7 = this.uaeClientBean.updateAlert(getAppInfo(), paramIAEAlertDefinition.getId(), paramIAEAlertDefinition.getUserId(), paramIAEAlertDefinition.getType(), paramIAEAlertDefinition.getPriority(), paramIAEAlertDefinition.getScheduleInfo(), (IAEDeliveryInfo[])localObject3, paramIAEAlertDefinition.getMessage());
                this.uaeClientBean.remove();
              }
              else
              {
                getEngine().updateAlert(getAppInfo(), paramIAEAlertDefinition.getId(), paramIAEAlertDefinition.getUserId(), paramIAEAlertDefinition.getType(), paramIAEAlertDefinition.getPriority(), paramIAEAlertDefinition.getScheduleInfo(), (IAEDeliveryInfo[])localObject3, paramIAEAlertDefinition.getMessage());
              }
            }
          }
          if (bool1)
          {
            localObject1 = new Currency(Double.toString(d), paramAlertUserInfo.getLocale());
            localObject2 = new MessageList();
            initializeNsfMessages((MessageList)localObject2, localSecureUser.getLocale());
            str5 = paramProperties.getProperty("ACCOUNTNUMBER");
            if (str5 != null) {
              addNsfNewAccountMessages((MessageList)localObject2, str5);
            }
            createAlert(paramIAEAlertDefinition, paramInt, (MessageList)localObject2);
            localObject3 = paramProperties.getProperty("ONETIME");
            if ((localObject3 != null) && (((String)localObject3).length() != 0) && (Boolean.valueOf((String)localObject3).booleanValue() == true))
            {
              localObject4 = paramIAEAlertDefinition.getDeliveryInfo();
              localObject4[paramInt].setSuspended(true);
              if ((!this.appServer.equalsIgnoreCase("WAS4.0")) && (!this.appServer.equalsIgnoreCase("WEBSPHERE")) && (!this.appServer.equalsIgnoreCase("EASERVER")))
              {
                localObject5 = new Properties();
                ((Properties)localObject5).put("java.naming.factory.initial", this.context_factory);
                ((Properties)localObject5).put("java.naming.provider.url", this.provider_url);
                ((Properties)localObject5).put("java.naming.security.principal", this.context_username);
                ((Properties)localObject5).put("java.naming.security.credentials", this.context_password);
                localObject6 = new InitialContext((Hashtable)localObject5);
                localObject7 = (IAEAlertClientHome)((Context)localObject6).lookup(this.jndi_name);
                this.uaeClientBean = ((IAEAlertClientHome)localObject7).create();
                IAEAlertDefinition localIAEAlertDefinition = this.uaeClientBean.updateAlert(getAppInfo(), paramIAEAlertDefinition.getId(), paramIAEAlertDefinition.getUserId(), paramIAEAlertDefinition.getType(), paramIAEAlertDefinition.getPriority(), paramIAEAlertDefinition.getScheduleInfo(), (IAEDeliveryInfo[])localObject4, paramIAEAlertDefinition.getMessage());
                this.uaeClientBean.remove();
              }
              else
              {
                getEngine().updateAlert(getAppInfo(), paramIAEAlertDefinition.getId(), paramIAEAlertDefinition.getUserId(), paramIAEAlertDefinition.getType(), paramIAEAlertDefinition.getPriority(), paramIAEAlertDefinition.getScheduleInfo(), (IAEDeliveryInfo[])localObject4, paramIAEAlertDefinition.getMessage());
              }
            }
          }
        }
      }
    }
    catch (Exception localException)
    {
      logEntry(localException);
    }
  }
  
  protected void checkForBalanceAlert(IAEAlertDefinition paramIAEAlertDefinition, String paramString, int paramInt, Properties paramProperties)
  {
    logEntry("Checking for balance alert...");
    try
    {
      if (!paramIAEAlertDefinition.getDeliveryInfo()[paramInt].isSuspended())
      {
        String str = paramProperties.getProperty("ONETIME");
        if ((str != null) && (str.length() != 0) && (Boolean.valueOf(str).booleanValue()))
        {
          IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = paramIAEAlertDefinition.getDeliveryInfo();
          arrayOfIAEDeliveryInfo[paramInt].setSuspended(true);
          if ((!this.appServer.equalsIgnoreCase("WAS4.0")) && (!this.appServer.equalsIgnoreCase("WEBSPHERE")) && (!this.appServer.equalsIgnoreCase("EASERVER")))
          {
            Properties localProperties = new Properties();
            localProperties.put("java.naming.factory.initial", this.context_factory);
            localProperties.put("java.naming.provider.url", this.provider_url);
            localProperties.put("java.naming.security.principal", this.context_username);
            localProperties.put("java.naming.security.credentials", this.context_password);
            InitialContext localInitialContext = new InitialContext(localProperties);
            IAEAlertClientHome localIAEAlertClientHome = (IAEAlertClientHome)localInitialContext.lookup(this.jndi_name);
            this.uaeClientBean = localIAEAlertClientHome.create();
            IAEAlertDefinition localIAEAlertDefinition = this.uaeClientBean.updateAlert(getAppInfo(), paramIAEAlertDefinition.getId(), paramIAEAlertDefinition.getUserId(), paramIAEAlertDefinition.getType(), paramIAEAlertDefinition.getPriority(), paramIAEAlertDefinition.getScheduleInfo(), arrayOfIAEDeliveryInfo, paramIAEAlertDefinition.getMessage());
            this.uaeClientBean.remove();
          }
          else
          {
            getEngine().updateAlert(getAppInfo(), paramIAEAlertDefinition.getId(), paramIAEAlertDefinition.getUserId(), paramIAEAlertDefinition.getType(), paramIAEAlertDefinition.getPriority(), paramIAEAlertDefinition.getScheduleInfo(), arrayOfIAEDeliveryInfo, paramIAEAlertDefinition.getMessage());
          }
        }
      }
    }
    catch (Exception localException)
    {
      logEntry(localException);
    }
  }
  
  protected void addAccountBalanceMessage(Properties paramProperties, Locale paramLocale, Currency paramCurrency, String paramString, double paramDouble, boolean paramBoolean, MessageList paramMessageList)
  {
    Currency localCurrency = new Currency(Double.toString(paramDouble), paramLocale);
    String str1 = paramBoolean ? ResourceUtil.getString("bodyAccountBalanceSecureAbove", "com.ffusion.beans.alerts.resources", paramLocale) : ResourceUtil.getString("bodyAccountBalanceSecureNotAbove", "com.ffusion.beans.alerts.resources", paramLocale);
    Object[] arrayOfObject1 = { paramProperties.getProperty("ACCOUNTNUMBER"), str1, localCurrency.toString(), paramString, paramCurrency.toString() };
    String str2 = paramBoolean ? ResourceUtil.getString("bodyAccountBalanceUnSecureAbove", "com.ffusion.beans.alerts.resources", paramLocale) : ResourceUtil.getString("bodyAccountBalanceUnSecureNotAbove", "com.ffusion.beans.alerts.resources", paramLocale);
    Object[] arrayOfObject2 = { paramString, str2 };
    if ((paramMessageList.getSecureMessage().getSubject() == null) || (paramMessageList.getSecureMessage().getSubject().length() <= 0))
    {
      paramMessageList.getSecureMessage().setSubject(ResourceUtil.getString("subjectAccountBalance", "com.ffusion.beans.alerts.resources", paramLocale));
      paramMessageList.getUnsecureMessage().setSubject(paramMessageList.getSecureMessage().getSubject());
    }
    paramMessageList.getSecureMessage().addContent(MessageFormat.format(ResourceUtil.getString("bodyAccountBalanceSecure", "com.ffusion.beans.alerts.resources", paramLocale), arrayOfObject1));
    paramMessageList.getUnsecureMessage().addContent(MessageFormat.format(ResourceUtil.getString("bodyAccountBalanceUnSecure", "com.ffusion.beans.alerts.resources", paramLocale), arrayOfObject2));
  }
  
  protected void addAccountBalanceLastMessage(Locale paramLocale, MessageList paramMessageList)
  {
    Object[] arrayOfObject = new Object[0];
    paramMessageList.getUnsecureMessage().addContent(MessageFormat.format(ResourceUtil.getString("bodyAccountBalanceUnSecureInfo", "com.ffusion.beans.alerts.resources", paramLocale), arrayOfObject));
  }
  
  protected void initializeNsfMessages(MessageList paramMessageList, Locale paramLocale)
  {
    paramMessageList.getUnsecureMessage().addContent(ResourceUtil.getString("bodyNsfUnsecureMessage", "com.ffusion.beans.alerts.resources", paramLocale));
    paramMessageList.getSecureMessage().addContent(ResourceUtil.getString("bodyNsfSecureMessage", "com.ffusion.beans.alerts.resources", paramLocale));
    paramMessageList.setSubject(ResourceUtil.getString("subjectNsfMessage", "com.ffusion.beans.alerts.resources", paramLocale));
  }
  
  protected void checkForAlertDCNSF(IAEAlertDefinition paramIAEAlertDefinition, AlertUserInfo paramAlertUserInfo, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    MessageList localMessageList = new MessageList();
    localMessageList.setSecureMessage(new MessageImpl(paramString1, paramString2));
    localMessageList.setUnsecureMessage(new MessageImpl(paramString3, paramString4));
    checkForAlertDCNSF(paramIAEAlertDefinition, paramAlertUserInfo, localMessageList);
  }
  
  protected void checkForAlertDCNSF(IAEAlertDefinition paramIAEAlertDefinition, AlertUserInfo paramAlertUserInfo, MessageList paramMessageList)
  {
    int i = getPluginLocation(paramIAEAlertDefinition, getBalanceCheckPluginName());
    if (i >= 0)
    {
      Properties localProperties = paramIAEAlertDefinition.getDeliveryInfo()[i].getDeliveryProperties();
      checkForAlertDCNSF(paramIAEAlertDefinition, paramAlertUserInfo, paramMessageList, localProperties, i);
    }
  }
  
  protected void checkForAlertDCNSF(IAEAlertDefinition paramIAEAlertDefinition, AlertUserInfo paramAlertUserInfo, MessageList paramMessageList, Properties paramProperties, int paramInt)
  {
    logEntry("Checking for NSF alert...");
    try
    {
      double d = 0.0D;
      boolean bool = false;
      if ((paramAlertUserInfo != null) && (paramIAEAlertDefinition.getDeliveryInfo()[paramInt].isSuspended() != true))
      {
        bool = paramAlertUserInfo.getNsfValue();
        if (bool)
        {
          AlertUtil.setProperty(paramIAEAlertDefinition, "AlertType", "NSF");
          createAlert(paramIAEAlertDefinition, paramInt, paramMessageList);
          String str = paramProperties.getProperty("ONETIME");
          if (Boolean.valueOf(str).booleanValue())
          {
            IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = paramIAEAlertDefinition.getDeliveryInfo();
            arrayOfIAEDeliveryInfo[paramInt].setSuspended(true);
            if ((!this.appServer.equalsIgnoreCase("WAS4.0")) && (!this.appServer.equalsIgnoreCase("WEBSPHERE")) && (!this.appServer.equalsIgnoreCase("EASERVER")))
            {
              Properties localProperties = new Properties();
              localProperties.put("java.naming.factory.initial", this.context_factory);
              localProperties.put("java.naming.provider.url", this.provider_url);
              localProperties.put("java.naming.security.principal", this.context_username);
              localProperties.put("java.naming.security.credentials", this.context_password);
              InitialContext localInitialContext = new InitialContext(localProperties);
              IAEAlertClientHome localIAEAlertClientHome = (IAEAlertClientHome)localInitialContext.lookup(this.jndi_name);
              this.uaeClientBean = localIAEAlertClientHome.create();
              IAEAlertDefinition localIAEAlertDefinition = this.uaeClientBean.updateAlert(getAppInfo(), paramIAEAlertDefinition.getId(), paramIAEAlertDefinition.getUserId(), paramIAEAlertDefinition.getType(), paramIAEAlertDefinition.getPriority(), paramIAEAlertDefinition.getScheduleInfo(), arrayOfIAEDeliveryInfo, paramMessageList.getSecureMessage().getContent());
              this.uaeClientBean.remove();
            }
            else
            {
              getEngine().updateAlert(getAppInfo(), paramIAEAlertDefinition.getId(), paramIAEAlertDefinition.getUserId(), paramIAEAlertDefinition.getType(), paramIAEAlertDefinition.getPriority(), paramIAEAlertDefinition.getScheduleInfo(), arrayOfIAEDeliveryInfo, paramMessageList.getSecureMessage().getContent());
            }
          }
        }
      }
    }
    catch (Exception localException)
    {
      logEntry(localException);
    }
  }
  
  protected MessageList initializeTransactionMessages(Locale paramLocale)
  {
    MessageList localMessageList = new MessageList();
    localMessageList.setSubject(ResourceUtil.getString("subjectTransMsg", "com.ffusion.beans.alerts.resources", paramLocale));
    return localMessageList;
  }
  
  protected void checkForAlertTransaction(int paramInt, IAEAlertDefinition paramIAEAlertDefinition, ReportRows paramReportRows, Properties paramProperties)
  {
    logEntry("Checking for transaction alert...");
    try
    {
      boolean bool = false;
      String str1 = paramProperties.getProperty("Criteria");
      String str2 = paramProperties.getProperty("TransactionType");
      String str3 = paramProperties.getProperty("Amount");
      SecureUser localSecureUser = new SecureUser();
      localSecureUser.setXML(paramProperties.getProperty("SECUREUSER"));
      String str4 = paramProperties.getProperty("PREFERREDLANGUAGE");
      if ((str4 != null) && (str4.trim().length() > 0)) {
        localSecureUser.setLocale(str4);
      }
      Currency localCurrency = str1.equalsIgnoreCase("ANY AMOUNT") ? null : new Currency(str3, localSecureUser.getLocale());
      MessageList localMessageList = initializeTransactionMessages(localSecureUser.getLocale());
      if (str1.equalsIgnoreCase("More Than")) {
        bool = processTransactionAlerts(paramReportRows, 0, localCurrency, new AlertTransactionHandler()
        {
          public boolean isValid(double paramAnonymousDouble1, double paramAnonymousDouble2)
          {
            return paramAnonymousDouble1 < paramAnonymousDouble2;
          }
        }, localMessageList, localSecureUser);
      } else if (str1.equalsIgnoreCase("Less Than")) {
        bool = processTransactionAlerts(paramReportRows, 1, localCurrency, new AlertTransactionHandler()
        {
          public boolean isValid(double paramAnonymousDouble1, double paramAnonymousDouble2)
          {
            return paramAnonymousDouble1 > paramAnonymousDouble2;
          }
        }, localMessageList, localSecureUser);
      } else if (str1.equalsIgnoreCase("EXACT")) {
        bool = processTransactionAlerts(paramReportRows, 2, localCurrency, new AlertTransactionHandler()
        {
          public boolean isValid(double paramAnonymousDouble1, double paramAnonymousDouble2)
          {
            return paramAnonymousDouble1 == paramAnonymousDouble2;
          }
        }, localMessageList, localSecureUser);
      } else if (str1.equalsIgnoreCase("ANY AMOUNT")) {
        bool = processTransactionAlerts(paramReportRows, 3, localCurrency, new AlertTransactionHandler()
        {
          public boolean isValid(double paramAnonymousDouble1, double paramAnonymousDouble2)
          {
            return true;
          }
        }, localMessageList, localSecureUser);
      }
      AlertUtil.setProperty(paramIAEAlertDefinition, "AlertType", "Transaction");
      if ((!paramIAEAlertDefinition.getDeliveryInfo()[paramInt].isSuspended()) && (bool))
      {
        createAlert(paramIAEAlertDefinition, paramInt, localMessageList);
        String str5 = paramProperties.getProperty("ONETIME");
        if ((str5 != null) && (str5.length() > 0) && (Boolean.valueOf(str5).booleanValue()))
        {
          IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = paramIAEAlertDefinition.getDeliveryInfo();
          arrayOfIAEDeliveryInfo[paramInt].setSuspended(true);
          if ((!this.appServer.equalsIgnoreCase("WAS4.0")) && (!this.appServer.equalsIgnoreCase("WEBSPHERE")) && (!this.appServer.equalsIgnoreCase("EASERVER")))
          {
            Properties localProperties = new Properties();
            localProperties.put("java.naming.factory.initial", this.context_factory);
            localProperties.put("java.naming.provider.url", this.provider_url);
            localProperties.put("java.naming.security.principal", this.context_username);
            localProperties.put("java.naming.security.credentials", this.context_password);
            InitialContext localInitialContext = new InitialContext(localProperties);
            IAEAlertClientHome localIAEAlertClientHome = (IAEAlertClientHome)localInitialContext.lookup(this.jndi_name);
            this.uaeClientBean = localIAEAlertClientHome.create();
            IAEAlertDefinition localIAEAlertDefinition = this.uaeClientBean.updateAlert(getAppInfo(), paramIAEAlertDefinition.getId(), paramIAEAlertDefinition.getUserId(), paramIAEAlertDefinition.getType(), paramIAEAlertDefinition.getPriority(), paramIAEAlertDefinition.getScheduleInfo(), arrayOfIAEDeliveryInfo, localMessageList.getSecureMessage().getSubject());
            this.uaeClientBean.remove();
          }
          else
          {
            getEngine().updateAlert(getAppInfo(), paramIAEAlertDefinition.getId(), paramIAEAlertDefinition.getUserId(), paramIAEAlertDefinition.getType(), paramIAEAlertDefinition.getPriority(), paramIAEAlertDefinition.getScheduleInfo(), arrayOfIAEDeliveryInfo, localMessageList.getSecureMessage().getSubject());
          }
        }
      }
    }
    catch (Exception localException)
    {
      logEntry(localException);
    }
  }
  
  public void prepareMessagesBeforeTransactions(int paramInt1, String paramString, int paramInt2, Currency paramCurrency, MessageList paramMessageList, Locale paramLocale)
  {
    String str = null;
    if (paramInt1 == 0) {
      str = ResourceUtil.getString("bodyTransMsgAbove", "com.ffusion.beans.alerts.resources", paramLocale);
    } else if (paramInt1 == 1) {
      str = ResourceUtil.getString("bodyTransMsgBelow", "com.ffusion.beans.alerts.resources", paramLocale);
    } else if (paramInt1 == 2) {
      str = ResourceUtil.getString("bodyTransMsgEqual", "com.ffusion.beans.alerts.resources", paramLocale);
    }
    Object[] arrayOfObject;
    if (str != null)
    {
      if (paramCurrency != null)
      {
        arrayOfObject = new Object[] { paramString, str, paramCurrency.toString() };
        paramMessageList.getSecureMessage().addContent(MessageFormat.format(ResourceUtil.getString("bodyTransMsgThresholdCondition", "com.ffusion.beans.alerts.resources", paramLocale), arrayOfObject));
      }
      else
      {
        arrayOfObject = new Object[] { paramString, str };
        paramMessageList.getSecureMessage().addContent(MessageFormat.format(ResourceUtil.getString("bodyTransMsgNoThresholdCondition", "com.ffusion.beans.alerts.resources", paramLocale), arrayOfObject));
      }
    }
    else
    {
      arrayOfObject = new Object[] { Integer.toString(paramInt2), paramString };
      paramMessageList.getSecureMessage().addContent(MessageFormat.format(ResourceUtil.getString("bodyTransMsg", "com.ffusion.beans.alerts.resources", paramLocale), arrayOfObject));
    }
  }
  
  protected void messageIndent(MessageList paramMessageList)
  {
    paramMessageList.getSecureMessage().addContent("\n    ");
  }
  
  protected void setTxnProcessingDateMessages(MessageList paramMessageList, String paramString)
  {
    paramMessageList.getSecureMessage().addContent(paramString);
  }
  
  protected void setTxnMemoMessages(MessageList paramMessageList, String paramString)
  {
    String str = paramString == null ? "..." : paramString;
    int i = paramString.indexOf('\r');
    if (i == -1) {
      i = paramString.indexOf('\n');
    }
    if (i != -1) {
      str = paramString.substring(0, i);
    }
    if (str.length() < 40)
    {
      StringBuffer localStringBuffer = new StringBuffer(str);
      for (int j = str.length(); j < 40; j++) {
        localStringBuffer.append(" ");
      }
      str = localStringBuffer.toString();
    }
    else if (str.length() > 40)
    {
      str = str.substring(0, 37) + "...";
    }
    paramMessageList.getSecureMessage().addContent("   ");
    paramMessageList.getSecureMessage().addContent(str);
  }
  
  protected void setTxnTypeMessages(MessageList paramMessageList, String paramString1, String paramString2)
  {
    if (paramString1.length() < 16)
    {
      StringBuffer localStringBuffer = new StringBuffer(paramString1);
      for (int i = paramString1.length(); i < 16; i++) {
        localStringBuffer.append(" ");
      }
      paramString1 = localStringBuffer.toString();
    }
    else if (paramString1.length() > 16)
    {
      paramString1 = paramString1.substring(0, 13) + "...";
    }
    paramMessageList.getSecureMessage().addContent("   ");
    paramMessageList.getSecureMessage().addContent(paramString1);
    paramMessageList.getSecureMessage().addContent("   ");
    paramMessageList.getSecureMessage().addContent(paramString2);
  }
  
  public void sendingTxnAlertMessages(MessageList paramMessageList, int paramInt1, int paramInt2, Locale paramLocale)
  {
    String str = null;
    if (paramInt2 == 0) {
      str = ResourceUtil.getString("bodyTransMsgAbove", "com.ffusion.beans.alerts.resources", paramLocale);
    } else if (paramInt2 == 1) {
      str = ResourceUtil.getString("bodyTransMsgBelow", "com.ffusion.beans.alerts.resources", paramLocale);
    } else if (paramInt2 == 2) {
      str = ResourceUtil.getString("bodyTransMsgEqual", "com.ffusion.beans.alerts.resources", paramLocale);
    }
    Object[] arrayOfObject;
    if (str != null)
    {
      arrayOfObject = new Object[] { Integer.toString(paramInt1), str };
      paramMessageList.getUnsecureMessage().addContent(MessageFormat.format(ResourceUtil.getString("bodyTransMsgUnsecureCond", "com.ffusion.beans.alerts.resources", paramLocale), arrayOfObject));
    }
    else
    {
      arrayOfObject = new Object[] { Integer.toString(paramInt1) };
      paramMessageList.getUnsecureMessage().addContent(MessageFormat.format(ResourceUtil.getString("bodyTransMsgUnsecure", "com.ffusion.beans.alerts.resources", paramLocale), arrayOfObject));
    }
  }
  
  protected boolean processTransactionAlerts(ReportRows paramReportRows, int paramInt, Currency paramCurrency, AlertTransactionHandler paramAlertTransactionHandler, MessageList paramMessageList, SecureUser paramSecureUser)
  {
    boolean bool = false;
    double d1 = paramCurrency == null ? 0.0D : Math.abs(paramCurrency.doubleValue());
    ReportRow localReportRow = (ReportRow)paramReportRows.get(0);
    ReportDataItems localReportDataItems = localReportRow.getDataItems();
    ReportDataItem localReportDataItem = (ReportDataItem)localReportDataItems.get(4);
    String str1 = (String)localReportDataItem.getData();
    prepareMessagesBeforeTransactions(paramInt, str1, paramReportRows.size(), paramCurrency, paramMessageList, paramSecureUser.getLocale());
    Iterator localIterator = paramReportRows.iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      localReportRow = (ReportRow)localIterator.next();
      localReportDataItems = localReportRow.getDataItems();
      localReportDataItem = (ReportDataItem)localReportDataItems.get(2);
      Currency localCurrency = (Currency)localReportDataItem.getData();
      double d2 = Math.abs(localCurrency.doubleValue());
      if ((paramAlertTransactionHandler != null) && (paramAlertTransactionHandler.isValid(d1, d2)))
      {
        messageIndent(paramMessageList);
        localReportDataItem = (ReportDataItem)localReportDataItems.get(0);
        DateTime localDateTime = (DateTime)localReportDataItem.getData();
        localDateTime.setFormat("M/dd/yyyy");
        setTxnProcessingDateMessages(paramMessageList, localDateTime.toString());
        localReportDataItem = (ReportDataItem)localReportDataItems.get(3);
        String str2 = (String)localReportDataItem.getData();
        setTxnMemoMessages(paramMessageList, str2);
        localReportDataItem = (ReportDataItem)localReportDataItems.get(1);
        String str3 = (String)localReportDataItem.getData();
        setTxnTypeMessages(paramMessageList, str3, localCurrency.toString());
        bool = true;
      }
    }
    if (bool) {
      sendingTxnAlertMessages(paramMessageList, i, paramInt, paramSecureUser.getLocale());
    }
    return bool;
  }
  
  protected void createAlert(IAEAlertDefinition paramIAEAlertDefinition, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    MessageList localMessageList = new MessageList();
    localMessageList.setSecureMessage(new MessageImpl(paramString1, paramString2));
    localMessageList.setUnsecureMessage(new MessageImpl(paramString3, paramString4));
    createAlert(paramIAEAlertDefinition, paramInt, localMessageList);
  }
  
  protected void createAlert(IAEAlertDefinition paramIAEAlertDefinition, int paramInt, MessageList paramMessageList)
  {
    logEntry("Creating alert...");
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
      logEntry(localException);
    }
  }
  
  protected void createAlert(IAEAlertDefinition paramIAEAlertDefinition, int paramInt)
  {
    logEntry("Creating alert...");
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
    Properties localProperties = new Properties();
    for (j = 0; j < arrayOfIAEDeliveryInfo2.length; j++) {
      localProperties = arrayOfIAEDeliveryInfo2[j].getDeliveryProperties();
    }
    try
    {
      j = getEngine().createAlert(getAppInfo(), paramIAEAlertDefinition.getUserId(), 0, 1, null, arrayOfIAEDeliveryInfo2, paramIAEAlertDefinition.getMessage());
      logEntry("New alertID: " + j);
    }
    catch (Exception localException)
    {
      logEntry(localException);
    }
  }
  
  protected String getAccountIdFromColumn(ReportRow paramReportRow, HashMap paramHashMap, String paramString)
  {
    int i = ((Integer)paramHashMap.get(paramString)).intValue();
    ReportDataItem localReportDataItem = (ReportDataItem)paramReportRow.getDataItems().get(i);
    return (String)localReportDataItem.getData();
  }
  
  protected IReportResult getAccountReport(Accounts paramAccounts, SecureUser paramSecureUser, CSILEJBWrapper paramCSILEJBWrapper, ReportCriteria paramReportCriteria)
    throws Exception
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("Accounts", paramAccounts);
    localHashMap.put("AccountsForReport", paramAccounts);
    Business localBusiness = new Business(paramSecureUser.getLocale());
    localBusiness.setId(paramSecureUser.getBusinessID());
    localBusiness.setBankId(paramSecureUser.getBankID());
    localHashMap.put("BusinessForReport", localBusiness);
    IReportResult localIReportResult = null;
    try
    {
      localIReportResult = paramCSILEJBWrapper.getEJB().getAccountReportData(paramReportCriteria, localHashMap);
    }
    catch (RemoteException localRemoteException)
    {
      paramCSILEJBWrapper.reconnect();
      localIReportResult = paramCSILEJBWrapper.getEJB().getAccountReportData(paramReportCriteria, localHashMap);
    }
    return localIReportResult;
  }
  
  protected int getTransactionType(String paramString)
  {
    int i = 1000;
    if (paramString.equalsIgnoreCase("All Transactions"))
    {
      i = 1000;
    }
    else if (paramString.equalsIgnoreCase("Wire"))
    {
      i = 31;
    }
    else if (paramString.equalsIgnoreCase("Wire In"))
    {
      i = 33;
    }
    else if (paramString.equalsIgnoreCase("ACH"))
    {
      i = 32;
    }
    else if (paramString.equalsIgnoreCase("ACH In"))
    {
      i = 34;
    }
    else if (paramString.equalsIgnoreCase("Bill"))
    {
      i = 25;
    }
    else if (paramString.equalsIgnoreCase("FX"))
    {
      i = 36;
    }
    else if (paramString.equalsIgnoreCase("TAX"))
    {
      i = 37;
    }
    else if (paramString.equalsIgnoreCase("Transfer"))
    {
      i = 16;
    }
    else if (paramString.equalsIgnoreCase("Lockbox Credit"))
    {
      i = 38;
    }
    else if (paramString.equalsIgnoreCase("Disbursement Credit"))
    {
      i = 40;
    }
    else if (paramString.equalsIgnoreCase("ZBA Credit"))
    {
      i = 42;
    }
    else
    {
      logEntry("TransactionType: " + paramString + " is not supported yet ");
      return -1;
    }
    return i;
  }
  
  private static void jdMethod_for(String paramString1, String paramString2, boolean paramBoolean, PrintWriter paramPrintWriter)
  {
    String str1 = "ProcessUserInformation.copyFile";
    if (paramPrintWriter == null) {
      paramBoolean = false;
    }
    if ((paramString1 == null) || (paramString1.length() <= 0))
    {
      AlertUtil.logEntry(str1 + ": source file is null", paramBoolean, paramPrintWriter);
      return;
    }
    File localFile1 = new File(paramString1);
    if ((paramString2 == null) || (paramString2.length() <= 0))
    {
      AlertUtil.logEntry(str1 + ": no destination path specified.", paramBoolean, paramPrintWriter);
      return;
    }
    try
    {
      if ((!localFile1.exists()) || (!localFile1.isFile())) {
        return;
      }
      File localFile2 = new File(paramString2);
      if (localFile2.exists())
      {
        if (!localFile2.isDirectory()) {
          AlertUtil.logEntry(str1 + " failed:" + paramString2 + " is not directory.", paramBoolean, paramPrintWriter);
        }
      }
      else
      {
        localFile2.mkdirs();
        AlertUtil.logEntry(str1 + ": destination directory " + paramString2 + " created.", paramBoolean, paramPrintWriter);
      }
    }
    catch (SecurityException localSecurityException1)
    {
      AlertUtil.logEntry(str1 + " failed due to SecurityException with message : " + localSecurityException1.getMessage(), paramBoolean, paramPrintWriter);
      if (paramBoolean) {
        localSecurityException1.printStackTrace(paramPrintWriter);
      }
      return;
    }
    StringBuffer localStringBuffer = new StringBuffer(localFile1.getName());
    int i = -1;
    for (int j = localStringBuffer.length() - 1; j >= 0; j--) {
      if (localStringBuffer.charAt(j) == '.')
      {
        i = j;
        break;
      }
    }
    if (i >= 0)
    {
      str2 = aSP.format(new Date());
      localStringBuffer.insert(i, '-');
      localStringBuffer.insert(i + 1, str2);
    }
    else
    {
      str2 = aSP.format(new Date());
      localStringBuffer.append('-');
      localStringBuffer.append(str2);
    }
    String str2 = localStringBuffer.toString();
    FileInputStream localFileInputStream = null;
    FileOutputStream localFileOutputStream = null;
    try
    {
      localFileInputStream = new FileInputStream(localFile1);
      File localFile3 = new File(paramString2, str2);
      localFileOutputStream = new FileOutputStream(localFile3);
      byte[] arrayOfByte = new byte[131072];
      for (int k = localFileInputStream.read(arrayOfByte); k > 0; k = localFileInputStream.read(arrayOfByte)) {
        localFileOutputStream.write(arrayOfByte, 0, k);
      }
      localFileOutputStream.flush();
    }
    catch (SecurityException localSecurityException2)
    {
      AlertUtil.logEntry(str1 + ": failed to move the file with message : " + localSecurityException2.getMessage(), paramBoolean, paramPrintWriter);
      if (paramBoolean) {
        localSecurityException2.printStackTrace(paramPrintWriter);
      }
    }
    catch (IOException localIOException1)
    {
      AlertUtil.logEntry(str1 + ": failed to move the file with message : " + localIOException1.getMessage(), paramBoolean, paramPrintWriter);
      if (paramBoolean) {
        localIOException1.printStackTrace(paramPrintWriter);
      }
    }
    finally
    {
      if (localFileInputStream != null) {
        try
        {
          localFileInputStream.close();
        }
        catch (IOException localIOException2) {}
      }
      if (localFileOutputStream != null) {
        try
        {
          localFileOutputStream.close();
        }
        catch (IOException localIOException3) {}
      }
    }
  }
  
  private boolean jdMethod_for(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws RemoteException, NamingException, CreateException, EJBException, CSILException
  {
    Account localAccount = new Account();
    localAccount.setBankID(paramString1);
    localAccount.setRoutingNum(paramString2);
    localAccount.setID(paramString3);
    boolean bool;
    try
    {
      bool = getCsilInstance().isAccountEntitled(paramSecureUser, localAccount, paramHashMap);
    }
    catch (RemoteException localRemoteException)
    {
      reconnectToCsil(true);
      bool = getCsilInstance().isAccountEntitled(paramSecureUser, localAccount, paramHashMap);
    }
    return bool;
  }
  
  protected String getPluginName()
  {
    return "ProcessUserInformation";
  }
  
  protected String getBalanceCheckPluginName()
  {
    return "BalanceCheck";
  }
  
  protected BAITypeCodeInfo getBAITypeCodeInfo(int paramInt)
    throws RemoteException, NamingException, CreateException, EJBException, CSILException
  {
    BAITypeCodeInfo localBAITypeCodeInfo = (BAITypeCodeInfo)this._baiTypeCodeMap.get(new Integer(paramInt));
    if (localBAITypeCodeInfo != null) {
      return localBAITypeCodeInfo;
    }
    try
    {
      localBAITypeCodeInfo = getCsilInstance().getBAITypeCodeInfo(paramInt);
    }
    catch (RemoteException localRemoteException)
    {
      reconnectToCsil(true);
      localBAITypeCodeInfo = getCsilInstance().getBAITypeCodeInfo(paramInt);
    }
    if (localBAITypeCodeInfo != null) {
      this._baiTypeCodeMap.put(new Integer(paramInt), localBAITypeCodeInfo);
    }
    return localBAITypeCodeInfo;
  }
  
  protected int[] getBAITypeCodes(int paramInt)
  {
    return (int[])this._acctBalanceBAICodeMap.get(new Integer(paramInt));
  }
  
  protected void checkAccountBalanceData(IAEAlertDefinition paramIAEAlertDefinition, int paramInt, ReportResult paramReportResult, Account paramAccount, Properties paramProperties)
  {
    int i = 0;
    int j = 0;
    double d1 = 0.0D;
    SecureUser localSecureUser = new SecureUser();
    localSecureUser.setXML(paramProperties.getProperty("SECUREUSER"));
    String str1 = paramProperties.getProperty("PREFERREDLANGUAGE");
    if ((str1 != null) && (str1.trim().length() > 0)) {
      localSecureUser.setLocale(str1);
    }
    char c;
    if ((paramProperties.containsKey("MINAMOUNT")) && (paramProperties.getProperty("MINAMOUNT").length() > 0))
    {
      localObject1 = (DecimalFormat)DecimalFormat.getCurrencyInstance(localSecureUser.getLocale());
      c = ((DecimalFormat)localObject1).getDecimalFormatSymbols().getGroupingSeparator();
      localObject2 = ((DecimalFormat)localObject1).getDecimalFormatSymbols().getCurrencySymbol();
      localObject3 = paramProperties.getProperty("MINAMOUNT");
      localObject3 = Strings.removeChars((String)localObject3, c);
      localObject3 = Strings.replaceStr((String)localObject3, (String)localObject2, "");
      try
      {
        d1 = Double.parseDouble((String)localObject3);
        j = 1;
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        logEntry("Account balance alert: the threshold amount must be a number. Alert processing aborted.");
        return;
      }
    }
    else if ((paramProperties.containsKey("MAXAMOUNT")) && (paramProperties.getProperty("MAXAMOUNT").length() > 0))
    {
      localObject1 = (DecimalFormat)DecimalFormat.getCurrencyInstance(localSecureUser.getLocale());
      c = ((DecimalFormat)localObject1).getDecimalFormatSymbols().getGroupingSeparator();
      localObject2 = ((DecimalFormat)localObject1).getDecimalFormatSymbols().getCurrencySymbol();
      localObject3 = paramProperties.getProperty("MAXAMOUNT");
      localObject3 = Strings.removeChars((String)localObject3, c);
      localObject3 = Strings.replaceStr((String)localObject3, (String)localObject2, "");
      try
      {
        d1 = Double.parseDouble((String)localObject3);
        i = 1;
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        logEntry("Account balance alert: the threshold amount must be a number. Alert processing aborted.");
        return;
      }
    }
    else
    {
      return;
    }
    Object localObject1 = getBAITypeCodes(paramAccount.getAccountGroup());
    if ((localObject1 == null) || (localObject1.length <= 0)) {
      return;
    }
    int[] arrayOfInt = new int[localObject1.length];
    Arrays.fill(arrayOfInt, -1);
    Object localObject2 = paramReportResult.getColumns();
    Object localObject3 = paramReportResult.getRows();
    if ((localObject3 == null) || (((ReportRows)localObject3).isEmpty()) || (localObject2 == null) || (((ReportColumns)localObject2).isEmpty())) {
      return;
    }
    int k = 0;
    Object localObject4;
    int i4;
    for (int m = 0; m < ((ReportColumns)localObject2).size(); m++)
    {
      ReportColumn localReportColumn = (ReportColumn)((ReportColumns)localObject2).get(m);
      localObject4 = localReportColumn.getLabels();
      String str2 = (String)((ArrayList)localObject4).get(((ArrayList)localObject4).size() - 1);
      int i3 = getBAITypeCodeFromColumnName(str2);
      if (i3 >= 0) {
        for (i4 = 0; i4 < localObject1.length; i4++) {
          if (localObject1[i4] == i3)
          {
            arrayOfInt[i4] = m;
            k = 1;
            break;
          }
        }
      }
    }
    if (k == 0)
    {
      StringBuffer localStringBuffer = new StringBuffer("No report columns found for BAI codes ");
      for (int i1 = 0; i1 < localObject1.length; i1++)
      {
        if (i1 > 0) {
          localStringBuffer.append(",");
        }
        localStringBuffer.append(localObject1[i1]);
      }
      logEntry(localStringBuffer.toString());
      return;
    }
    for (int n = 0; n < ((ReportRows)localObject3).size(); n++)
    {
      ReportRow localReportRow = (ReportRow)((ReportRows)localObject3).get(n);
      localObject4 = localReportRow.getDataItems();
      int i2 = 0;
      MessageList localMessageList = new MessageList();
      for (i4 = 0; i4 < localObject1.length; i4++) {
        if (arrayOfInt[i4] >= 0)
        {
          Currency localCurrency = (Currency)((ReportDataItem)((ReportDataItems)localObject4).get(arrayOfInt[i4])).getData();
          if (localCurrency != null)
          {
            double d2 = localCurrency.getAmountValue().doubleValue();
            String str3;
            if ((i != 0) && (d2 > d1))
            {
              str3 = null;
              try
              {
                BAITypeCodeInfo localBAITypeCodeInfo1 = getBAITypeCodeInfo(localObject1[i4]);
                str3 = localBAITypeCodeInfo1.getDescription(paramReportResult.getLocale());
              }
              catch (Exception localException1)
              {
                logEntry(localException1);
                str3 = "Undescribed Amount";
              }
              addAccountBalanceMessage(paramProperties, localSecureUser.getLocale(), localCurrency, str3, d1, true, localMessageList);
              i2 = 1;
            }
            else if ((j != 0) && (d2 < d1))
            {
              str3 = null;
              try
              {
                BAITypeCodeInfo localBAITypeCodeInfo2 = getBAITypeCodeInfo(localObject1[i4]);
                str3 = localBAITypeCodeInfo2.getDescription(paramReportResult.getLocale());
              }
              catch (Exception localException2)
              {
                logEntry(localException2);
                str3 = "Undescribed Amount";
              }
              addAccountBalanceMessage(paramProperties, localSecureUser.getLocale(), localCurrency, str3, d1, false, localMessageList);
              i2 = 1;
            }
          }
        }
      }
      if (i2 != 0)
      {
        addAccountBalanceLastMessage(localSecureUser.getLocale(), localMessageList);
        AlertUtil.setProperty(paramIAEAlertDefinition, "AlertType", "AccountBalance");
        createAlert(paramIAEAlertDefinition, paramInt, localMessageList);
      }
    }
  }
  
  protected Account getEntitledAccountByBusinessEmployee(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, ICSILEJB paramICSILEJB)
    throws NamingException, CreateException, RemoteException, EJBException, CSILException
  {
    Accounts localAccounts = null;
    try
    {
      localAccounts = paramICSILEJB.getEntitledAccountsByBusinessEmployee(paramSecureUser, new HashMap());
    }
    catch (RemoteException localRemoteException)
    {
      reconnectToCsil(true);
      paramICSILEJB = getCsilInstance();
      localAccounts = paramICSILEJB.getEntitledAccountsByBusinessEmployee(paramSecureUser, new HashMap());
    }
    if ((localAccounts == null) || (localAccounts.isEmpty())) {
      return null;
    }
    Object localObject = null;
    for (int i = 0; i < localAccounts.size(); i++)
    {
      Account localAccount = (Account)localAccounts.get(i);
      if ((localAccount.getBankID().equals(paramString1)) && (localAccount.getRoutingNum().equals(paramString2)) && (localAccount.getID().equals(paramString3)))
      {
        localObject = localAccount;
        break;
      }
    }
    return localObject;
  }
  
  protected static final int getBAITypeCodeFromColumnName(String paramString)
  {
    String str1 = "BAI_";
    if (!paramString.startsWith(str1)) {
      return -1;
    }
    int i = -1;
    String str2 = paramString.substring(str1.length());
    try
    {
      i = Integer.parseInt(str2);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return -1;
    }
    return i;
  }
  
  public void doProcessAlert(int paramInt1, int paramInt2, CSILEJBWrapper paramCSILEJBWrapper, IAEAlertDefinition[] paramArrayOfIAEAlertDefinition)
  {
    Calendar localCalendar = Calendar.getInstance();
    long l = Long.parseLong(getInitProps().getProperty("FREQ", Long.toString(86400000L)));
    String str1 = AlertUtil.getDateString(localCalendar.getTime(), AlertUtil.sdfMMDDYYYY);
    String str2 = AlertUtil.getDateString(localCalendar.getTime(), AlertUtil.sdfHHMMSS);
    localCalendar.add(13, (int)(-1L * (l / 1000L)));
    String str3 = AlertUtil.getDateString(localCalendar.getTime(), AlertUtil.sdfMMDDYYYY);
    String str4 = AlertUtil.getDateString(localCalendar.getTime(), AlertUtil.sdfHHMMSS);
    ReportCriteria localReportCriteria = new ReportCriteria();
    ReportSortCriteria localReportSortCriteria = new ReportSortCriteria();
    Properties localProperties1 = new Properties();
    Properties localProperties2 = new Properties();
    localProperties1.setProperty("StartDate", str3);
    localProperties1.setProperty("StartTime", str4);
    localProperties1.setProperty("EndDate", str1);
    localProperties1.setProperty("EndTime", str2);
    localProperties1.setProperty("Date Range Type", "Absolute");
    localProperties1.setProperty("TransactionStatus", "Completed");
    localProperties1.setProperty("DataClassification", "P");
    localProperties1.setProperty("Accounts", "AllAccounts");
    localProperties2.setProperty("DESTINATION", "OBJECT");
    localProperties2.setProperty("FORMAT", "BAI2");
    localReportCriteria.setSortCriteria(localReportSortCriteria);
    localReportCriteria.setSearchCriteria(localProperties1);
    try
    {
      Accounts localAccounts = new Accounts();
      SecureUser localSecureUser = new SecureUser();
      for (int i = paramInt1; i < paramInt2; i++)
      {
        int j = getPluginLocation(paramArrayOfIAEAlertDefinition[i], getBalanceCheckPluginName());
        if (j >= 0)
        {
          Properties localProperties3 = paramArrayOfIAEAlertDefinition[i].getDeliveryInfo()[j].getDeliveryProperties();
          localSecureUser.setXML(localProperties3.getProperty("SECUREUSER"));
          String str5 = localProperties3.getProperty("PREFERREDLANGUAGE");
          if ((str5 != null) && (str5.trim().length() > 0)) {
            localSecureUser.setLocale(str5);
          }
          localReportCriteria.setLocale(localSecureUser.getLocale());
          String str6 = localProperties3.getProperty("REALACCOUNTNUMBER");
          String str7 = localProperties3.getProperty("ROUTINGNUMBER");
          String str8 = localProperties3.getProperty("AlertType");
          String str9 = localSecureUser.getBankID();
          AlertUserInfo localAlertUserInfo = new AlertUserInfo();
          Object localObject1;
          Object localObject2;
          Object localObject3;
          Object localObject4;
          Object localObject5;
          Object localObject6;
          if (str8.equals("AccountBalance"))
          {
            localObject1 = new Account();
            ((Account)localObject1).setBankID(str9);
            ((Account)localObject1).setRoutingNum(str7);
            ((Account)localObject1).setID(str6);
            ((Account)localObject1).setLocale(localSecureUser.getLocale());
            ((Account)localObject1).setShowPreviousDayOpeningLedger("Y");
            int k = str6.lastIndexOf("-");
            int n = 0;
            localObject2 = null;
            if ((k > 0) && (k < str6.length() - 1))
            {
              n = Integer.parseInt(str6.substring(k + 1));
              localObject2 = str6.substring(0, k);
            }
            if ((localObject2 != null) && (((String)localObject2).length() > 0)) {
              ((Account)localObject1).setNumber((String)localObject2);
            }
            ((Account)localObject1).setType(n);
            ((Account)localObject1).setAccountGroup(Account.getAccountGroupFromType(n));
            localObject3 = new HashMap();
            int i2 = 0;
            try
            {
              i2 = paramCSILEJBWrapper.getEJB().isAccountEntitledNoCache(localSecureUser, (Account)localObject1, (HashMap)localObject3) != null ? 1 : 0;
            }
            catch (RemoteException localRemoteException2)
            {
              paramCSILEJBWrapper.reconnect();
              i2 = paramCSILEJBWrapper.getEJB().isAccountEntitledNoCache(localSecureUser, (Account)localObject1, (HashMap)localObject3) != null ? 1 : 0;
            }
            if (i2 != 0)
            {
              localAccounts = new Accounts();
              localAccounts.add(localObject1);
              localProperties2.setProperty("REPORTTYPE", "BalanceSummaryReport");
              localReportCriteria.setReportOptions(localProperties2);
              localObject4 = (ReportResult)getAccountReport(localAccounts, localSecureUser, paramCSILEJBWrapper, localReportCriteria);
              if (localObject4 != null)
              {
                localObject5 = ((ReportResult)localObject4).getSubReports();
                if ((localObject5 != null) && (!((ReportResults)localObject5).isEmpty()))
                {
                  localObject6 = (ReportResult)((ReportResults)localObject5).get(0);
                  checkAccountBalanceData(paramArrayOfIAEAlertDefinition[i], j, (ReportResult)localObject6, (Account)localObject1, localProperties3);
                }
              }
            }
          }
          else
          {
            ReportRows localReportRows2;
            if (str8.equals("NSF"))
            {
              localObject1 = new HashMap();
              try
              {
                localAccounts = paramCSILEJBWrapper.getEJB().getEntitledAccountsByBusinessEmployee(localSecureUser, (HashMap)localObject1);
              }
              catch (RemoteException localRemoteException1)
              {
                paramCSILEJBWrapper.reconnect();
                localAccounts = paramCSILEJBWrapper.getEJB().getEntitledAccountsByBusinessEmployee(localSecureUser, (HashMap)localObject1);
              }
              if ((localAccounts != null) && (!localAccounts.isEmpty()))
              {
                localProperties1.setProperty("TransactionType", Integer.toString(35));
                localProperties2.setProperty("REPORTTYPE", "AlertTransaction");
                localReportCriteria.setReportOptions(localProperties2);
                localReportCriteria.setSearchCriteria(localProperties1);
                ReportResult localReportResult = (ReportResult)getAccountReport(localAccounts, localSecureUser, paramCSILEJBWrapper, localReportCriteria);
                if (localReportResult != null)
                {
                  ReportRows localReportRows1 = localReportResult.getRows();
                  if ((localReportRows1 != null) && (localReportRows1.size() > 0))
                  {
                    localAlertUserInfo.setNsf(true);
                    localObject2 = localReportRows1.iterator();
                    localObject3 = new MessageList();
                    initializeNsfMessages((MessageList)localObject3, localSecureUser.getLocale());
                    ReportRow localReportRow = null;
                    localObject4 = null;
                    localObject5 = null;
                    localObject6 = null;
                    localReportRows2 = null;
                    String str10 = null;
                    Object localObject7 = null;
                    while (((Iterator)localObject2).hasNext())
                    {
                      localReportRow = (ReportRow)((Iterator)localObject2).next();
                      localObject4 = localReportRow.getDataItems();
                      localObject5 = (ReportDataItem)((ReportDataItems)localObject4).get(4);
                      localObject6 = (String)((ReportDataItem)localObject5).getData();
                      str10 = "\n\t" + (String)localObject6;
                      addNsfNewAccountMessages((MessageList)localObject3, str10);
                    }
                    checkForAlertDCNSF(paramArrayOfIAEAlertDefinition[i], localAlertUserInfo, (MessageList)localObject3, localProperties3, j);
                  }
                }
              }
            }
            else if ((str8.equalsIgnoreCase("Transaction")) && (str6 != null))
            {
              localObject1 = new Account();
              ((Account)localObject1).setBankID(str9);
              ((Account)localObject1).setRoutingNum(str7);
              ((Account)localObject1).setID(str6);
              ((Account)localObject1).setLocale(localSecureUser.getLocale());
              ((Account)localObject1).setShowPreviousDayOpeningLedger("Y");
              int m = str6.lastIndexOf("-");
              int i1 = 0;
              localObject2 = null;
              if ((m > 0) && (m < str6.length() - 1))
              {
                i1 = Integer.parseInt(str6.substring(m + 1));
                localObject2 = str6.substring(0, m);
              }
              if ((localObject2 != null) && (((String)localObject2).length() > 0)) {
                ((Account)localObject1).setNumber((String)localObject2);
              }
              ((Account)localObject1).setType(i1);
              ((Account)localObject1).setAccountGroup(Account.getAccountGroupFromType(i1));
              localObject3 = new HashMap();
              int i3 = 0;
              try
              {
                i3 = paramCSILEJBWrapper.getEJB().isAccountEntitledNoCache(localSecureUser, (Account)localObject1, (HashMap)localObject3) != null ? 1 : 0;
              }
              catch (RemoteException localRemoteException3)
              {
                paramCSILEJBWrapper.reconnect();
                i3 = paramCSILEJBWrapper.getEJB().isAccountEntitledNoCache(localSecureUser, (Account)localObject1, (HashMap)localObject3) != null ? 1 : 0;
              }
              if (i3 != 0)
              {
                localAccounts = new Accounts();
                localAccounts.add(localObject1);
                int i4 = getTransactionType(localProperties3.getProperty("TransactionType"));
                localObject5 = String.valueOf(i4);
                if (i4 == 1000) {
                  localObject5 = "AllTransactionTypes";
                }
                localProperties1.setProperty("TransactionType", (String)localObject5);
                localProperties2.setProperty("REPORTTYPE", "AlertTransaction");
                localReportCriteria.setReportOptions(localProperties2);
                localReportCriteria.setSearchCriteria(localProperties1);
                localObject6 = (ReportResult)getAccountReport(localAccounts, localSecureUser, paramCSILEJBWrapper, localReportCriteria);
                if (localObject6 != null)
                {
                  localReportRows2 = ((ReportResult)localObject6).getRows();
                  if ((localReportRows2 != null) && (localReportRows2.size() > 0)) {
                    checkForAlertTransaction(j, paramArrayOfIAEAlertDefinition[i], localReportRows2, localProperties3);
                  }
                }
              }
            }
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
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
  
  private class a
    extends Thread
  {
    private IAEAlertDefinition[] jdField_if = null;
    private HashMap jdField_do = new HashMap();
    private int jdField_for = 0;
    private ProcessUserInformation.d a;
    
    public a(ProcessUserInformation.d paramd)
    {
      this.a = paramd;
    }
    
    public int a()
    {
      return this.jdField_for;
    }
    
    public void a(IAEAlertDefinition[] paramArrayOfIAEAlertDefinition)
    {
      this.jdField_if = paramArrayOfIAEAlertDefinition;
      this.jdField_do.clear();
      this.jdField_for = 0;
      for (int i = 0; i < this.jdField_if.length; i++)
      {
        int j = ProcessUserInformation.this.getPluginLocation(this.jdField_if[i], ProcessUserInformation.this.getBalanceCheckPluginName());
        if (j >= 0)
        {
          Properties localProperties = this.jdField_if[i].getDeliveryInfo()[j].getDeliveryProperties();
          String str1 = localProperties.getProperty("REALACCOUNTNUMBER");
          String str2 = localProperties.getProperty("ROUTINGNUMBER");
          String str3 = str2 + " : " + str1;
          if (this.jdField_do.containsKey(str3)) {
            this.jdField_do.put(str3, this.jdField_do.get(str3) + "," + Integer.toString(this.jdField_if[i].getId()));
          } else {
            this.jdField_do.put(str3, Integer.toString(this.jdField_if[i].getId()));
          }
        }
      }
    }
    
    public void run()
    {
      try
      {
        for (;;)
        {
          if ((this.jdField_if == null) || (this.jdField_if.length <= 0)) {
            synchronized (this.a)
            {
              this.a.wait();
            }
          }
          if (ProcessUserInformation.this.getInitProps().getProperty("FILE_FORMAT").equals("BAI")) {
            this.jdField_for = ProcessUserInformation.this.processBAIFile(this.jdField_if, this.jdField_do);
          } else {
            this.jdField_for = ProcessUserInformation.this.processUIFFile(this.jdField_if, this.jdField_do);
          }
          this.jdField_if = null;
          this.jdField_do.clear();
          synchronized (this.a)
          {
            this.a.jdField_if();
            this.a.wait();
          }
        }
      }
      catch (Exception localException)
      {
        ProcessUserInformation.this.logEntry(localException);
      }
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
  
  private class d
  {
    private ProcessUserInformation.a[] jdField_if = null;
    private int a = 0;
    private int jdField_for = 0;
    private ArrayList jdField_do;
    
    public d(int paramInt, ArrayList paramArrayList)
    {
      this.a = paramInt;
      this.jdField_do = paramArrayList;
    }
    
    public void jdField_for()
    {
      this.jdField_if = new ProcessUserInformation.a[this.a];
      for (int i = 0; i < this.a; i++)
      {
        this.jdField_if[i] = new ProcessUserInformation.a(ProcessUserInformation.this, this);
        this.jdField_if[i].start();
      }
    }
    
    public void a(IAEAlertDefinition[] paramArrayOfIAEAlertDefinition)
    {
      if (this.jdField_for < this.a)
      {
        this.jdField_if[this.jdField_for].a(paramArrayOfIAEAlertDefinition);
        this.jdField_for += 1;
      }
    }
    
    public void a()
    {
      synchronized (this.jdField_do)
      {
        this.jdField_do.add(new Integer(this.jdField_for));
      }
      notifyAll();
    }
    
    public void jdField_if()
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
    
    public void jdMethod_new()
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
    
    public int jdMethod_int()
    {
      int i = 0;
      if (this.jdField_if != null) {
        for (int j = 0; (i == 0) && (j < this.a); j++) {
          i = this.jdField_if[j].a();
        }
      }
      return i;
    }
    
    public boolean jdField_do()
    {
      return this.jdField_for < this.a;
    }
  }
  
  private class e
    extends Thread
  {
    private int jdField_do = -1;
    private int jdField_int = -1;
    private CSILEJBWrapper a = null;
    private IAEAlertDefinition[] jdField_if = null;
    private ProcessUserInformation.b jdField_for;
    
    public e(CSILEJBWrapper paramCSILEJBWrapper, ProcessUserInformation.b paramb)
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
          if ((this.jdField_if == null) || (this.jdField_if.length <= 0)) {
            synchronized (this.jdField_for)
            {
              this.jdField_for.wait();
            }
          }
          ProcessUserInformation.this.doProcessAlert(this.jdField_do, this.jdField_int, this.a, this.jdField_if);
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
        this.a = null;
        this.jdField_if = null;
        this.jdField_for = null;
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
    private ProcessUserInformation.e[] jdField_if = null;
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
      this.jdField_if = new ProcessUserInformation.e[this.a];
      for (int i = 0; i < this.a; i++)
      {
        this.jdField_if[i] = new ProcessUserInformation.e(ProcessUserInformation.this, (CSILEJBWrapper)paramArrayList.get(i), this);
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
  
  public class c
    extends XMLHandler
  {
    private String jdField_try = "";
    private String jdField_else = "";
    private String jdField_byte = "";
    private boolean jdField_case = false;
    private AlertUserInfo jdField_int;
    private IAEAlertDefinition[] jdField_char;
    private HashMap jdField_new;
    
    public c(IAEAlertDefinition[] paramArrayOfIAEAlertDefinition, HashMap paramHashMap, ArrayList paramArrayList)
    {
      this.jdField_char = paramArrayOfIAEAlertDefinition;
      this.jdField_new = paramHashMap;
    }
    
    public void startElement(String paramString)
    {
      if (paramString.equals("USER"))
      {
        this.jdField_int = new AlertUserInfo();
      }
      else if (paramString.equals("ACCOUNT"))
      {
        String str = this.jdField_int.getDirectoryID();
        this.jdField_int = new AlertUserInfo();
        this.jdField_int.setDirectoryID(str);
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
      String str2 = getElement();
      if (str2.equals("ID")) {
        this.jdField_int.setDirectoryID(str1);
      } else if (str2.equals("NSF")) {
        this.jdField_int.setNsf(str1);
      } else if (str2.equals("NUMBER")) {
        this.jdField_int.setAccountID(str1);
      } else if (str2.equals("AVAILABLEBALANCE")) {
        this.jdField_int.setBalance(str1);
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {
      if (paramString.equals("ACCOUNT"))
      {
        String str1 = this.jdField_int.getAccountID();
        if (this.jdField_new.containsKey(str1))
        {
          String str2 = (String)this.jdField_new.get(str1);
          if (str2 == null) {
            return;
          }
          Object localObject;
          if (str2.indexOf(',') != -1)
          {
            localObject = new StringTokenizer(str2, ",");
            if (localObject != null) {
              while (((StringTokenizer)localObject).hasMoreTokens())
              {
                String str3 = ((StringTokenizer)localObject).nextToken();
                if (str3 != null) {
                  for (int j = 0; j < this.jdField_char.length; j++) {
                    if (this.jdField_char[j].getId() == Integer.parseInt(str3))
                    {
                      ProcessUserInformation.this.checkForAlert(this.jdField_char[j], this.jdField_int);
                      break;
                    }
                  }
                }
              }
            }
          }
          else
          {
            localObject = str2;
            if (localObject != null) {
              for (int i = 0; i < this.jdField_char.length; i++) {
                if (this.jdField_char[i].getId() == Integer.parseInt((String)localObject))
                {
                  ProcessUserInformation.this.checkForAlert(this.jdField_char[i], this.jdField_int);
                  break;
                }
              }
            }
          }
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.ProcessUserInformation
 * JD-Core Version:    0.7.0.1
 */