package com.ffusion.alert.plugins;

import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.plugins.message.Message;
import com.ffusion.alert.plugins.message.MessageImpl;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.alerts.AccountAlert;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.beans.alerts.UserAlerts;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.FIException;
import com.ffusion.csil.ejb.ICSILEJB;
import com.ffusion.dataconsolidator.adapter.DCException;
import com.ffusion.dataconsolidator.adapter.DCUtil;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIAccountIdentifier;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIAccountTrailer;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIFileHeader;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIFileTrailer;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIGroupHeader;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIGroupTrailer;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIParser;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIParserCallback;
import com.ffusion.fileimporter.fileadapters.baiparser.BAISummaryStatus;
import com.ffusion.fileimporter.fileadapters.baiparser.BAITransactionDetail;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.TransactionTypeCache;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.beans.LocalizableCurrency;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableList;
import com.ffusion.util.beans.LocalizableString;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

public class ProcessAccountAlerts
  extends BaseAlert
  implements IAEChannel, BAIParserCallback
{
  private static final String aS1 = ".xml";
  public static final String PLUGIN_NAME = "ProcessAccountAlerts";
  public static final String USER_INFO_FILE_PATH = "USER_INFO_FILE_PATH";
  public static final String USER_INFO_ARCHIVE_DIR = "USER_INFO_ARCHIVE_DIR";
  public static final String USER_INFO_ERROR_DIR = "USER_INFO_ERROR_DIR";
  public static final String NSF_TRANSACTION_BAI_CODE = "NSF_TRANSACTION_BAI_CODE";
  public static final String DEPOSIT_BALANCE_BAI_CODE = "DEPOSIT_BALANCE_BAI_CODE";
  public static final String LOAN_BALANCE_BAI_CODE = "LOAN_BALANCE_BAI_CODE";
  public static final String ASSET_BALANCE_BAI_CODE = "ASSET_BALANCE_BAI_CODE";
  public static final String CC_BALANCE_BAI_CODE = "CC_BALANCE_BAI_CODE";
  public static final String PREVIOUS_DAY_BALANCE_CODE = "PREVIOUS_DAY_BALANCE_CODE";
  public static final String CURRENT_BALANCE_CODE = "CURRENT_BALANCE_CODE";
  public static final String AVAILABLE_BALANCE_CODE = "AVAILABLE_BALANCE_CODE";
  public static final String APP_ACCOUNT_BATCH_SIZE = "APP_ACCOUNT_BATCH_SIZE";
  public static final String BAI_ACCOUNT_IDENTIFIER_IS_ID = "BAI_ACCOUNT_IDENTIFIER_IS_ID";
  public static final String TRANSACTION_TYPES_FILE = "transactionTypes.xml";
  public static final String BAITYPECODES_FILENAME = "baiTypeCodes.xml";
  public static final String KEY_BELOW_MINIMUM_BALANCE = "KEY_BELOW_MINIMUM_BALANCE";
  public static final String KEY_ABOVE_MAXIMUM_BALANCE = "KEY_ABOVE_MAXIMUM_BALANCE";
  public static final String KEY_BAI_SUMMARIES = "KEY_BAI_SUMMARIES";
  public static final String KEY_TRANSACTIONS_FOR_ALERT = "KEY_TRANSACTIONS_FOR_ALERT";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.alerts.resources";
  public static final String RESOURCE_ACCOUNT_BALANCE_ALERT_SECURE_SUBJECT = "ACCOUNT_BALANCE_ALERT_SECURE_SUBJECT";
  public static final String RESOURCE_ACCOUNT_BALANCE_ALERT_UNSECURE_SUBJECT = "ACCOUNT_BALANCE_ALERT_UNSECURE_SUBJECT";
  public static final String RESOURCE_ACCOUNT_BALANCE_ALERT_SECURE_BODY_BELOW_MIN = "ACCOUNT_BALANCE_ALERT_SECURE_BODY_BELOW_MIN";
  public static final String RESOURCE_ACCOUNT_BALANCE_ALERT_UNSECURE_BODY_BELOW_MIN = "ACCOUNT_BALANCE_ALERT_UNSECURE_BODY_BELOW_MIN";
  public static final String RESOURCE_ACCOUNT_BALANCE_ALERT_SECURE_BODY_ABOVE_MAX = "ACCOUNT_BALANCE_ALERT_SECURE_BODY_ABOVE_MAX";
  public static final String RESOURCE_ACCOUNT_BALANCE_ALERT_UNSECURE_BODY_ABOVE_MAX = "ACCOUNT_BALANCE_ALERT_UNSECURE_BODY_ABOVE_MAX";
  public static final String RESOURCE_ACCOUNT_SUMMARY_ALERT_SECURE_SUBJECT = "ACCOUNT_SUMMARY_ALERT_SECURE_SUBJECT";
  public static final String RESOURCE_ACCOUNT_SUMMARY_ALERT_UNSECURE_SUBJECT = "ACCOUNT_SUMMARY_ALERT_UNSECURE_SUBJECT";
  public static final String RESOURCE_ACCOUNT_SUMMARY_ALERT_SECURE_BODY = "ACCOUNT_SUMMARY_ALERT_SECURE_BODY";
  public static final String RESOURCE_ACCOUNT_SUMMARY_ALERT_UNSECURE_BODY = "ACCOUNT_SUMMARY_ALERT_UNSECURE_BODY";
  public static final String RESOURCE_TRANSACTION_ALERT_SECURE_SUBJECT = "TRANSACTION_ALERT_SECURE_SUBJECT";
  public static final String RESOURCE_TRANSACTION_ALERT_UNSECURE_SUBJECT = "TRANSACTION_ALERT_UNSECURE_SUBJECT";
  public static final String RESOURCE_TRANSACTION_ALERT_SECURE_BODY_OVER = "TRANSACTION_ALERT_SECURE_BODY_OVER";
  public static final String RESOURCE_TRANSACTION_ALERT_UNSECURE_BODY_OVER = "TRANSACTION_ALERT_UNSECURE_BODY_OVER";
  public static final String RESOURCE_TRANSACTION_ALERT_SECURE_BODY_UNDER = "TRANSACTION_ALERT_SECURE_BODY_UNDER";
  public static final String RESOURCE_TRANSACTION_ALERT_UNSECURE_BODY_UNDER = "TRANSACTION_ALERT_UNSECURE_BODY_UNDER";
  public static final String RESOURCE_TRANSACTION_ALERT_SECURE_BODY_EQUAL = "TRANSACTION_ALERT_SECURE_BODY_EQUAL";
  public static final String RESOURCE_TRANSACTION_ALERT_UNSECURE_BODY_EQUAL = "TRANSACTION_ALERT_UNSECURE_BODY_EQUAL";
  public static final String RESOURCE_TRANSACTION_ALERT_SECURE_BODY_NO_CONDITION = "TRANSACTION_ALERT_SECURE_BODY_NO_CONDITION";
  public static final String RESOURCE_TRANSACTION_ALERT_UNSECURE_BODY_NO_CONDITION = "TRANSACTION_ALERT_UNSECURE_BODY_NO_CONDITION";
  public static final String RESOURCE_TRANSACTION_ALERT_LIST_ITEM_NON_TRUNCATED_MEMO = "TRANSACTION_ALERT_LIST_ITEM_NON_TRUNCATED_MEMO";
  public static final String RESOURCE_TRANSACTION_ALERT_LIST_ITEM_TRUNCATED_MEMO = "TRANSACTION_ALERT_LIST_ITEM_TRUNCATED_MEMO";
  public static final String RESOURCE_TRANSACTION_ALERT_LIST_SEPERATOR = "TRANSACTION_ALERT_LIST_SEPERATOR";
  public static final String RESOURCE_NSF_ALERT_SECURE_SUBJECT = "NSF_ALERT_SECURE_SUBJECT";
  public static final String RESOURCE_NSF_ALERT_UNSECURE_SUBJECT = "NSF_ALERT_UNSECURE_SUBJECT";
  public static final String RESOURCE_NSF_ALERT_SECURE_BODY = "NSF_ALERT_SECURE_BODY";
  public static final String RESOURCE_NSF_ALERT_UNSECURE_BODY = "NSF_ALERT_UNSECURE_BODY";
  public static final String RESOURCE_ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_ITEM = "ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_ITEM";
  public static final String RESOURCE_ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_SEPERATOR = "ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_SEPERATOR";
  public static final String RESOURCE_ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_ITEM_PRIOR_DAY = "ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_ITEM_PRIOR_DAY";
  public static final String RESOURCE_ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_ITEM_CURRENT_BALANCE = "ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_ITEM_CURRENT_BALANCE";
  public static final String RESOURCE_ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_ITEM_AVAILABLE_BALANCE = "ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_ITEM_AVAILABLE_BALANCE";
  public static final int STATUS_CODE_SUCCESS = 0;
  public static final int STATUS_CODE_FAILURE_ERROR_COULD_NOT_ACCESS_INPUT_FILE = -2;
  public static final int STATUS_CODE_FAILURE_UNABLE_TO_PARSE_BAI_FILE = -3;
  public static final int STATUS_CODE_FAILURE_BANKING_EXCEPTION = -4;
  protected IAEAlertEngine _engine = null;
  protected PrintWriter _writer = null;
  protected String _userInfoFilePath = null;
  protected String _userInfoArchiveDir = null;
  protected String _userInfoErrorDir = null;
  protected int _nsfTransactionBAICode = 563;
  protected int _depositBalanceBAICode = 15;
  protected int _loanBalanceBAICode = 15;
  protected int _assetBalanceBAICode = 15;
  protected int _creditCardBalanceBAICode = 15;
  protected int _priorDayBalanceBAICode = 15;
  protected int _currentBalanceBAICode = 30;
  protected int _availableBalanceBAICode = 60;
  protected int _applicationAccountBatchSize = 100;
  protected boolean _parseIdentifierAsAccountID = true;
  private static final int aS2 = 0;
  private static final int aSU = 4;
  private static final int aSQ = 5;
  private static final DateFormat aST = new SimpleDateFormat("yyyy/MM/dd HH:mm");
  protected HashMap _baiCodeCache = null;
  protected HashMap _transTypeBAICodeCache = null;
  private BAIFileHeader aS3 = null;
  private BAIGroupHeader aTf = null;
  private BAIAccountIdentifier aTe = null;
  private Transactions aS7 = null;
  private ArrayList aSV = null;
  private ArrayList aS4 = null;
  private Accounts aS9 = null;
  private IAEAlertInstance aSZ = null;
  private boolean aTc = false;
  private int aSY = 0;
  private int aS8 = 0;
  private int aSS = 0;
  private int aSR = 0;
  private int aS0 = 0;
  private long aTb = 0L;
  private int aSW = 0;
  private long aS5 = 0L;
  private long aSX = 0L;
  private long aTd = 0L;
  private long aTa = 0L;
  private long aS6 = 0L;
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this._engine = paramIAEAlertEngine;
    this._writer = paramPrintWriter;
    setUpEnvironment(paramProperties, paramIAEAlertEngine, paramPrintWriter);
    initializeChannelVariables(paramProperties);
    TransactionTypeCache.initTransactionTypesCache("transactionTypes.xml");
    ae();
    ac();
  }
  
  private void ac()
    throws Exception
  {
    if (this._baiCodeCache == null) {
      return;
    }
    ArrayList localArrayList = null;
    int i = 0;
    String str = "baiTypeCodes.xml".substring(0, "baiTypeCodes.xml".length() - ".xml".length());
    localArrayList = LocaleUtil.getXMLFilesWithBase(str);
    i = localArrayList.size();
    for (int j = 0; j < i; j++) {
      ao((String)localArrayList.get(j));
    }
  }
  
  private void ao(String paramString)
    throws Exception
  {
    String str1 = null;
    str1 = LocaleUtil.getLocaleIDFromXMLFileName(paramString, "baiTypeCodes.xml");
    if (str1.length() == 0) {
      return;
    }
    HashMap localHashMap1 = XMLUtil.readXmlToHashMap(this, paramString);
    HashMap localHashMap2 = (HashMap)localHashMap1.get("TYPE_CODE_LIST");
    if (localHashMap2 == null) {
      throw new DCException("The TYPE_CODE_LIST tag did not exist in the document");
    }
    ArrayList localArrayList1 = (ArrayList)localHashMap2.get("TYPE_CODE");
    if (localArrayList1 == null) {
      throw new DCException("TYPE_CODE tags were not found in the XML document");
    }
    Object localObject = localHashMap1.get("CUSTOM_TYPE_CODE_LIST");
    if ((localObject != null & localObject instanceof HashMap))
    {
      HashMap localHashMap3 = (HashMap)localObject;
      ArrayList localArrayList2 = (ArrayList)localHashMap3.get("TYPE_CODE");
      if (localArrayList2 != null) {
        localArrayList1.addAll(localArrayList2);
      }
    }
    int i = localArrayList1.size();
    Integer localInteger = null;
    String str2 = null;
    HashMap localHashMap4 = null;
    BAITypeCodeInfo localBAITypeCodeInfo = null;
    HashMap localHashMap5 = new HashMap(i);
    Iterator localIterator = null;
    for (int j = 0; j < i; j++)
    {
      localHashMap4 = (HashMap)localArrayList1.get(j);
      try
      {
        localInteger = new Integer((String)localHashMap4.get("CODE"));
      }
      catch (NumberFormatException localNumberFormatException)
      {
        continue;
      }
      str2 = (String)localHashMap4.get("DESCRIPTION");
      if (str2 != null) {
        localHashMap5.put(localInteger, str2);
      }
    }
    localIterator = this._baiCodeCache.keySet().iterator();
    while (localIterator.hasNext())
    {
      localInteger = (Integer)localIterator.next();
      localBAITypeCodeInfo = (BAITypeCodeInfo)this._baiCodeCache.get(localInteger);
      if (localHashMap5.containsKey(localInteger)) {
        localBAITypeCodeInfo.setDescription(str1, (String)localHashMap5.get(localInteger));
      } else {
        localBAITypeCodeInfo.setDescription(str1, localBAITypeCodeInfo.getDescription());
      }
    }
  }
  
  protected void initializeChannelVariables(Properties paramProperties)
    throws Exception
  {
    this._userInfoFilePath = paramProperties.getProperty("USER_INFO_FILE_PATH");
    if (this._userInfoFilePath == null)
    {
      AlertUtil.logEntry("The file path to the BAI file containing balance information was not provided.  This file path is critical to the operation of this channel.  Please provide the file path and try initializing the channel again.", isDebugOn(), this._writer);
      throw new Exception("The file path to the BAI file containing balance information was not provided.  This file path is critical to the operation of this channel.  Please provide the file path and try initializing the channel again.");
    }
    this._userInfoArchiveDir = paramProperties.getProperty("USER_INFO_ARCHIVE_DIR");
    if (this._userInfoArchiveDir == null)
    {
      AlertUtil.logEntry("The file path to the directory in which the successfully processed BAI file should be stored was not provided.  This file path is critical to the operation of this channel.  Please provide the file path and try initializing the channel again.", isDebugOn(), this._writer);
      throw new Exception("The file path to the directory in which the successfully processed BAI file should be stored was not provided.  This file path is critical to the operation of this channel.  Please provide the file path and try initializing the channel again.");
    }
    this._userInfoErrorDir = paramProperties.getProperty("USER_INFO_ERROR_DIR");
    if (this._userInfoErrorDir == null)
    {
      AlertUtil.logEntry("The file path to the directory in which a BAI file that could not be processed should be stored was not provided.  This file path is critical to the operation of this channel.  Please provide the file path and try initializing the channel again.", isDebugOn(), this._writer);
      throw new Exception("The file path to the directory in which a BAI file that could not be processed should be stored was not provided.  This file path is critical to the operation of this channel.  Please provide the file path and try initializing the channel again.");
    }
    String str1 = paramProperties.getProperty("NSF_TRANSACTION_BAI_CODE");
    if (str1 != null) {
      try
      {
        this._nsfTransactionBAICode = Integer.parseInt(str1);
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        AlertUtil.logEntry("The BAI type code provided for NSF transactions was not a valid BAI code.  The value provided was \"" + str1 + "\".  The default value of code " + this._nsfTransactionBAICode + " will be used.", isDebugOn(), this._writer);
      }
    }
    String str2 = paramProperties.getProperty("DEPOSIT_BALANCE_BAI_CODE");
    if (str2 != null) {
      try
      {
        this._depositBalanceBAICode = Integer.parseInt(str2);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        AlertUtil.logEntry("The BAI type code provided for Deposit Account balances was not a valid BAI code.  The value provided was \"" + str2 + "\". The default value of code " + this._depositBalanceBAICode + " will be used.", isDebugOn(), this._writer);
      }
    }
    String str3 = paramProperties.getProperty("LOAN_BALANCE_BAI_CODE");
    if (str3 != null) {
      try
      {
        this._loanBalanceBAICode = Integer.parseInt(str3);
      }
      catch (NumberFormatException localNumberFormatException3)
      {
        AlertUtil.logEntry("The BAI type code provided for Loan Account balances was not a valid BAI code.  The value provided was \"" + str3 + "\". The default value of code " + this._loanBalanceBAICode + " will be used.", isDebugOn(), this._writer);
      }
    }
    String str4 = paramProperties.getProperty("ASSET_BALANCE_BAI_CODE");
    if (str4 != null) {
      try
      {
        this._assetBalanceBAICode = Integer.parseInt(str4);
      }
      catch (NumberFormatException localNumberFormatException4)
      {
        AlertUtil.logEntry("The BAI type code provided for Asset Account balances was not a valid BAI code.  The value provided was \"" + str4 + "\". The default value of code " + this._assetBalanceBAICode + " will be used.", isDebugOn(), this._writer);
      }
    }
    String str5 = paramProperties.getProperty("CC_BALANCE_BAI_CODE");
    if (str5 != null) {
      try
      {
        this._creditCardBalanceBAICode = Integer.parseInt(str5);
      }
      catch (NumberFormatException localNumberFormatException5)
      {
        AlertUtil.logEntry("The BAI type code provided for Credit Card Account balances was not a valid BAI code.  The value provided was \"" + str5 + "\". The default value of code " + this._creditCardBalanceBAICode + " will be used.", isDebugOn(), this._writer);
      }
    }
    String str6 = paramProperties.getProperty("APP_ACCOUNT_BATCH_SIZE");
    if (str6 != null) {
      try
      {
        this._applicationAccountBatchSize = Integer.parseInt(str6);
      }
      catch (NumberFormatException localNumberFormatException6)
      {
        AlertUtil.logEntry("The application setting account batch size provided was not a valid number.  The value provided was \"" + str6 + "\". The default value of " + this._applicationAccountBatchSize + " accounts per batch will be used.", isDebugOn(), this._writer);
      }
    }
    String str7 = paramProperties.getProperty("PREVIOUS_DAY_BALANCE_CODE");
    if (str7 != null) {
      try
      {
        this._priorDayBalanceBAICode = Integer.parseInt(str7);
      }
      catch (NumberFormatException localNumberFormatException7)
      {
        AlertUtil.logEntry("The BAI type code provided to represent the previous day balance for an account was not a valid BAI code.  The value provided was \"" + str7 + "\". The default value of code " + this._priorDayBalanceBAICode + " will be used.", isDebugOn(), this._writer);
      }
    }
    String str8 = paramProperties.getProperty("CURRENT_BALANCE_CODE");
    if (str8 != null) {
      try
      {
        this._currentBalanceBAICode = Integer.parseInt(str8);
      }
      catch (NumberFormatException localNumberFormatException8)
      {
        AlertUtil.logEntry("The BAI type code provided to represent the current balance for an account was not a valid BAI code.  The value provided was \"" + str8 + "\". The default value of code " + this._currentBalanceBAICode + " will be used.", isDebugOn(), this._writer);
      }
    }
    String str9 = paramProperties.getProperty("AVAILABLE_BALANCE_CODE");
    if (str9 != null) {
      try
      {
        this._availableBalanceBAICode = Integer.parseInt(str9);
      }
      catch (NumberFormatException localNumberFormatException9)
      {
        AlertUtil.logEntry("The BAI type code provided to represent the available balance for an account was not a valid BAI code.  The value provided was \"" + str9 + "\". The default value of code " + this._availableBalanceBAICode + " will be used.", isDebugOn(), this._writer);
      }
    }
    String str10 = paramProperties.getProperty("BAI_ACCOUNT_IDENTIFIER_IS_ID");
    if (str10 != null) {
      try
      {
        this._parseIdentifierAsAccountID = new Boolean(str10).booleanValue();
      }
      catch (NumberFormatException localNumberFormatException10)
      {
        AlertUtil.logEntry("The channel setting indicating whether BAI account identifiers are account IDs or account numbers provided was not a valid entry.  The valid values for this property are \"true\" or \"false\".  The value provided was \"" + str10 + "\". The default value of " + this._parseIdentifierAsAccountID + " will be used.", isDebugOn(), this._writer);
      }
    }
    String str11 = paramProperties.getProperty("PERFORMANCE_TESTING");
    if (str11 != null) {
      try
      {
        this.aTc = Boolean.valueOf(str11).booleanValue();
      }
      catch (Exception localException)
      {
        this.aTc = false;
      }
    }
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    long l1 = System.currentTimeMillis();
    this.aSZ = paramIAEAlertInstance;
    FileInputStream localFileInputStream = null;
    try
    {
      localFileInputStream = new FileInputStream(this._userInfoFilePath);
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      AlertUtil.logEntry(this.aSZ, "The file path provided for the input BAI file was not valid.  The path provided was \"" + this._userInfoFilePath + "\".  Please specify a valid path to the input file and reinitialize this channel.", isDebugOn(), this._writer);
      return -2;
    }
    catch (SecurityException localSecurityException)
    {
      AlertUtil.logEntry(this.aSZ, "The file path provided for the input BAI file could not be accessed by the application due to a security constraint.  The path provided was \"" + this._userInfoFilePath + "\".  Please ensure that the application has access to the directory and file specified and try using " + "this channel again.", isDebugOn(), this._writer);
      return -2;
    }
    BAIParser localBAIParser = new BAIParser(this);
    int i = 0;
    try
    {
      this.aTd = System.currentTimeMillis();
      localBAIParser.processBAIFile(localFileInputStream, new HashMap());
    }
    catch (FIException localFIException)
    {
      if (localFIException.getCode() == 8) {
        i = -4;
      } else {
        i = -3;
      }
      AlertUtil.logEntry(this.aSZ, "The BAI file provided could not be parsed.  The error condition that occurred follows: " + localFIException.getMessage(), isDebugOn(), this._writer);
      if (isDebugOn()) {
        localFIException.printStackTrace(this._writer);
      }
    }
    finally
    {
      this.aS3 = null;
      this.aTf = null;
      this.aTe = null;
      this.aS7 = null;
      this.aSV = null;
      this.aS4 = null;
      this.aS9 = null;
      if (this.aTc)
      {
        long l2 = System.currentTimeMillis();
        long l3 = l2 - l1;
        long l4 = l3 / this.aSY;
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append('\n');
        localStringBuffer.append("Performance Testing Statistics\n");
        localStringBuffer.append("------------------------------\n");
        localStringBuffer.append('\n');
        localStringBuffer.append("All times are in milliseconds.\n");
        localStringBuffer.append('\n');
        localStringBuffer.append("Total Execution Time: ").append(l3).append('\n');
        localStringBuffer.append("Total Time Spent Processing the BAI File: ").append(this.aSX).append('\n');
        localStringBuffer.append("Number of Account Alerts Retrieved: ").append(this.aSW).append('\n');
        localStringBuffer.append("Total Time Spent Retrieving Account Alerts: ").append(this.aTb).append('\n');
        localStringBuffer.append("Average Time Spent Retrieving Each Account Alert: ").append(this.aTb / this.aSW).append('\n');
        localStringBuffer.append("Total Time Spent Filling Account Collection Information: ").append(this.aS6).append('\n');
        localStringBuffer.append("Number of Alerts Created: ").append(this.aSY).append('\n');
        localStringBuffer.append("Total Time Spent Creating Alerts: ").append(this.aS5).append('\n');
        localStringBuffer.append("Average Time Spent Creating Each Alert: ").append(this.aS5 / this.aSY).append('\n');
        localStringBuffer.append("Number of Account Balance Alerts Created: ").append(this.aS8).append('\n');
        localStringBuffer.append("Number of NSF Alerts Created: ").append(this.aSS).append('\n');
        localStringBuffer.append("Number of Account Summary Alerts Created: ").append(this.aSR).append('\n');
        localStringBuffer.append("Number of Transaction Alerts Created: ").append(this.aS0).append('\n');
        AlertUtil.logEntry(localStringBuffer.toString(), true, getWriter());
        this.aSY = 0;
        this.aS8 = 0;
        this.aSS = 0;
        this.aSR = 0;
        this.aS0 = 0;
        this.aSX = 0L;
        this.aTd = 0L;
        this.aTa = 0L;
        this.aS5 = 0L;
        this.aSW = 0;
        this.aTb = 0L;
        this.aS6 = 0L;
      }
      try
      {
        localFileInputStream.close();
      }
      catch (IOException localIOException) {}
    }
    String str = null;
    if (i == 0) {
      str = this._userInfoArchiveDir;
    } else {
      str = this._userInfoErrorDir;
    }
    jdMethod_for(paramIAEAlertInstance, this._userInfoFilePath, str, isDebugOn(), this._writer);
    this.aSZ = null;
    return i;
  }
  
  public void processBAIRecord(Object paramObject)
    throws FIException
  {
    Object localObject1;
    if ((paramObject instanceof BAIFileHeader))
    {
      if (this.aS3 != null)
      {
        localObject1 = new FIException("Process Account Alerts Channel", 9704, "Found unexpected file header record");
        throw ((Throwable)localObject1);
      }
      this.aS3 = ((BAIFileHeader)paramObject);
      localObject1 = new StringBuffer("Loading file with identification number ");
      ((StringBuffer)localObject1).append(this.aS3._fileIdentificationNumber);
      if (this.aS3._fileCreationDate != null)
      {
        ((StringBuffer)localObject1).append(". The file create time is ");
        ((StringBuffer)localObject1).append(aST.format(this.aS3._fileCreationDate.getTime()));
      }
      ((StringBuffer)localObject1).append(".");
      AlertUtil.logEntry(this.aSZ, ((StringBuffer)localObject1).toString(), isDebugOn(), this._writer);
    }
    else
    {
      Object localObject2;
      if ((paramObject instanceof BAIGroupHeader))
      {
        if ((this.aS3 == null) || (this.aTf != null))
        {
          localObject1 = new FIException("Process Account Alerts Channel", 9706, "Found unexpected group header record");
          throw ((Throwable)localObject1);
        }
        this.aTf = ((BAIGroupHeader)paramObject);
        localObject1 = new StringBuffer("Loading ");
        switch (this.aTf._asOfDateModifier)
        {
        case 3: 
        case 4: 
          ((StringBuffer)localObject1).append("intra-day data for bank with ABA# ");
          break;
        case 1: 
        case 2: 
        default: 
          ((StringBuffer)localObject1).append("previous-day data for bank with ABA# ");
        }
        ((StringBuffer)localObject1).append(this.aTf._ultimateReceiverID);
        ((StringBuffer)localObject1).append(" as of ");
        ((StringBuffer)localObject1).append(aST.format(this.aTf._asOfDate.getTime()));
        AlertUtil.logEntry(this.aSZ, ((StringBuffer)localObject1).toString(), isDebugOn(), this._writer);
        if (this.aTf._groupStatus != 1)
        {
          localObject2 = new FIException("Process Account Alerts Channel", 9716, "Process Failed For BAI processFile record");
          throw ((Throwable)localObject2);
        }
      }
      else if ((paramObject instanceof BAIAccountIdentifier))
      {
        if ((this.aS3 == null) || (this.aTf == null) || (this.aTe != null))
        {
          localObject1 = new StringBuffer("Line ");
          ((StringBuffer)localObject1).append(((BAIAccountIdentifier)paramObject)._lineNumber).append(":");
          ((StringBuffer)localObject1).append(" Found unexpected account identifier record.");
          AlertUtil.logEntry(this.aSZ, ((StringBuffer)localObject1).toString(), isDebugOn(), this._writer);
          localObject2 = new FIException("Process Account Alerts Channel", 9710, "Found unexpected account identifier record");
          throw ((Throwable)localObject2);
        }
        this.aTe = ((BAIAccountIdentifier)paramObject);
        AlertUtil.logEntry(this.aSZ, "Processing account " + this.aTe._customerAccountNumber + ".", isDebugOn(), this._writer);
        this.aS7 = null;
      }
      else if ((paramObject instanceof BAITransactionDetail))
      {
        if ((this.aS3 == null) || (this.aTf == null) || (this.aTe == null))
        {
          localObject1 = new FIException("Process Account Alerts Channel", 9714, "Found unexpected transaction detail record");
          throw ((Throwable)localObject1);
        }
        localObject1 = (BAITransactionDetail)paramObject;
        localObject2 = this.aTe._currencyCode;
        if (this.aTe._currencyCode != null) {
          localObject2 = this.aTf._currencyCode;
        }
        jdMethod_for((BAITransactionDetail)localObject1, (String)localObject2);
      }
      else if ((paramObject instanceof BAIAccountTrailer))
      {
        AlertUtil.logEntry(this.aSZ, "Processing trailer record for account " + this.aTe._customerAccountNumber + ".", isDebugOn(), this._writer);
        if ((this.aS3 == null) || (this.aTf == null) || (this.aTe == null))
        {
          localObject1 = new FIException("Process Account Alerts Channel", 9711, "Found unexpected account trailer record");
          throw ((Throwable)localObject1);
        }
        if (this.aS9 == null) {
          this.aS9 = new Accounts(Locale.getDefault());
        }
        if (this.aS4 == null) {
          this.aS4 = new ArrayList();
        }
        if (this.aSV == null) {
          this.aSV = new ArrayList();
        }
        localObject1 = null;
        if (this._parseIdentifierAsAccountID)
        {
          localObject1 = new Account(Locale.getDefault(), this.aTe._customerAccountNumber, this.aTf._originatorID, this.aS3._senderIdentification);
        }
        else
        {
          localObject1 = new Account(Locale.getDefault(), null, this.aTf._originatorID, this.aS3._senderIdentification);
          ((Account)localObject1).setNumber(this.aTe._customerAccountNumber);
        }
        this.aS9.add(localObject1);
        this.aS4.add(this.aS7);
        this.aSV.add(this.aTe._baiSummaryStatus);
        if (this.aS9.size() >= this._applicationAccountBatchSize)
        {
          this.aTa = System.currentTimeMillis();
          this.aSX += this.aTa - this.aTd;
          int i = ad();
          if (i != 0) {
            throw new FIException("ProcessAccountAlerts Channel", 8, "Problems Interacting with Banking Server");
          }
          this.aS9 = null;
          this.aS4 = null;
          this.aSV = null;
          this.aTd = System.currentTimeMillis();
        }
        this.aTe = null;
        this.aS7 = null;
      }
      else if ((paramObject instanceof BAIGroupTrailer))
      {
        AlertUtil.logEntry(this.aSZ, "Processing group trailer record for " + this.aTf._originatorID + ".", isDebugOn(), this._writer);
        if ((this.aS3 == null) || (this.aTf == null) || (this.aTe != null))
        {
          localObject1 = new FIException("Process Account Alerts Channel", 9712, "Found unexpected group trailer record");
          throw ((Throwable)localObject1);
        }
        localObject1 = (BAIGroupTrailer)paramObject;
        StringBuffer localStringBuffer = new StringBuffer("Finished loading data for ABA#");
        localStringBuffer.append(this.aTf._ultimateReceiverID);
        localStringBuffer.append(". A total of ");
        localStringBuffer.append(((BAIGroupTrailer)localObject1)._numberOfAccounts);
        localStringBuffer.append(" accounts loaded. Total amount reported is ");
        localStringBuffer.append(((BAIGroupTrailer)localObject1)._groupControlTotal.toString());
        localStringBuffer.append(".");
        AlertUtil.logEntry(this.aSZ, localStringBuffer.toString(), isDebugOn(), this._writer);
        this.aTf = null;
      }
      else if ((paramObject instanceof BAIFileTrailer))
      {
        AlertUtil.logEntry(this.aSZ, "Processing file trailer record.", isDebugOn(), this._writer);
        if ((this.aS3 == null) || (this.aTf != null) || (this.aTe != null))
        {
          localObject1 = new FIException("Process Account Alerts Channel", 9713, "Found unexpected file trailer record");
          throw ((Throwable)localObject1);
        }
        localObject1 = new StringBuffer("Finished loading file with identification number ");
        ((StringBuffer)localObject1).append(this.aS3._fileIdentificationNumber);
        ((StringBuffer)localObject1).append(". The total number of reported groups is ");
        ((StringBuffer)localObject1).append(this.aS3._numberOfGroups);
        ((StringBuffer)localObject1).append(". The total number of reported accounts is ");
        ((StringBuffer)localObject1).append(this.aS3._numberOfAccounts);
        ((StringBuffer)localObject1).append(". The total number of records is ");
        ((StringBuffer)localObject1).append(this.aS3._numberOfRecords);
        ((StringBuffer)localObject1).append(". The total amount reported is ");
        ((StringBuffer)localObject1).append(this.aS3._runningControlTotal);
        AlertUtil.logEntry(this.aSZ, ((StringBuffer)localObject1).toString(), isDebugOn(), this._writer);
        this.aS3 = null;
        ad();
        this.aS9 = null;
        this.aS4 = null;
        this.aSV = null;
      }
    }
  }
  
  protected ArrayList getBAICodesForTransactionType(int paramInt)
  {
    if (this._transTypeBAICodeCache == null) {
      this._transTypeBAICodeCache = new HashMap();
    }
    String str = Integer.toString(paramInt);
    ArrayList localArrayList1 = (ArrayList)this._transTypeBAICodeCache.get(str);
    if (localArrayList1 != null) {
      return localArrayList1;
    }
    ArrayList localArrayList2 = new ArrayList(this._baiCodeCache.values());
    ArrayList localArrayList3 = new ArrayList();
    for (int i = 0; i < localArrayList2.size(); i++)
    {
      BAITypeCodeInfo localBAITypeCodeInfo1 = (BAITypeCodeInfo)localArrayList2.get(i);
      if (localBAITypeCodeInfo1.getLevel() == 2) {
        localArrayList3.add(localBAITypeCodeInfo1);
      }
    }
    if ((localArrayList3 == null) || (localArrayList3.isEmpty()))
    {
      localArrayList1 = new ArrayList(0);
      this._transTypeBAICodeCache.put(str, localArrayList1);
      return localArrayList1;
    }
    localArrayList1 = new ArrayList();
    for (i = 0; i < localArrayList3.size(); i++)
    {
      BAITypeCodeInfo localBAITypeCodeInfo2 = (BAITypeCodeInfo)localArrayList3.get(i);
      if (localBAITypeCodeInfo2.getTransactionType() == paramInt) {
        localArrayList1.add(new Integer(localBAITypeCodeInfo2.getCode()));
      }
    }
    localArrayList1.trimToSize();
    this._transTypeBAICodeCache.put(str, localArrayList1);
    return localArrayList1;
  }
  
  public void stop() {}
  
  protected String getPluginName()
  {
    return "ProcessAccountAlerts";
  }
  
  private int ad()
  {
    int i = 0;
    if ((this.aS9 == null) || (this.aS9.size() == 0)) {
      return i;
    }
    ICSILEJB localICSILEJB = null;
    try
    {
      reconnectToCsil(false);
      localICSILEJB = getCsilInstance();
    }
    catch (Exception localException1)
    {
      AlertUtil.logEntry(this.aSZ, "Could not connect to the banking application to retrieve account and alert information: " + localException1.getMessage(), isDebugOn(), this._writer);
      if (isDebugOn()) {
        localException1.printStackTrace(this._writer);
      }
      return -4;
    }
    HashMap localHashMap = new HashMap();
    long l1 = 0L;
    long l2 = System.currentTimeMillis();
    try
    {
      this.aS9 = localICSILEJB.fillAccountCollection(this.aS9, localHashMap);
      l1 = System.currentTimeMillis();
      this.aS6 += l1 - l2;
    }
    catch (RemoteException localRemoteException1)
    {
      try
      {
        reconnectToCsil(false);
        localICSILEJB.fillAccountCollection(this.aS9, localHashMap);
        l1 = System.currentTimeMillis();
        this.aS6 += l1 - l2;
      }
      catch (Exception localException2)
      {
        if ((localException2 instanceof RemoteException))
        {
          AlertUtil.logEntry(this.aSZ, "A remote exception occurred while attempting to retrieve account information from the banking server.  The following exception occurred while adding the alert to the alert's engine:\n ", isDebugOn(), this._writer);
          if (isDebugOn()) {
            localException2.printStackTrace(this._writer);
          }
          return -4;
        }
        if ((localException2 instanceof CSILException))
        {
          AlertUtil.logEntry(this.aSZ, "A problem occurred while attempting to retrieve account information from the banking server.  The following exception occurred:\n ", isDebugOn(), this._writer);
          if (isDebugOn()) {
            localException2.printStackTrace(this._writer);
          }
          return -4;
        }
        AlertUtil.logEntry(this.aSZ, "A naming exception occurred while attempting to connect to the banking server.  The following exception occurred:\n ", isDebugOn(), this._writer);
        if (isDebugOn()) {
          localException2.printStackTrace(this._writer);
        }
        return -4;
      }
    }
    catch (CSILException localCSILException1)
    {
      AlertUtil.logEntry(this.aSZ, "A problem occurred while attempting to retrieve account information from the banking server.  The following exception occurred:\n ", isDebugOn(), this._writer);
      if (isDebugOn()) {
        localCSILException1.printStackTrace(this._writer);
      }
      return -4;
    }
    ArrayList localArrayList = null;
    long l3 = System.currentTimeMillis();
    long l4 = 0L;
    try
    {
      localArrayList = localICSILEJB.getAlertsForAccounts(this.aS9, localHashMap);
      l4 = System.currentTimeMillis();
      this.aTb += l4 - l3;
      this.aSW = (localArrayList == null ? this.aSW : this.aSW + localArrayList.size());
    }
    catch (RemoteException localRemoteException2)
    {
      try
      {
        reconnectToCsil(false);
        localArrayList = localICSILEJB.getAlertsForAccounts(this.aS9, localHashMap);
        l4 = System.currentTimeMillis();
        this.aTb += l4 - l3;
        this.aSW = (localArrayList == null ? this.aSW : this.aSW + localArrayList.size());
      }
      catch (Exception localException3)
      {
        if ((localException3 instanceof RemoteException))
        {
          AlertUtil.logEntry(this.aSZ, "A remote exception occurred while attempting to retrieve information about the alert's defined for banking users from the banking server.  The following exception occurred while adding the alert to the alert's engine:\n ", isDebugOn(), this._writer);
          if (isDebugOn()) {
            localException3.printStackTrace(this._writer);
          }
          return -4;
        }
        if ((localException3 instanceof CSILException))
        {
          AlertUtil.logEntry(this.aSZ, "A problem occurred while attempting to retrieve user's alert information from the banking server.  The following exception occurred:\n ", isDebugOn(), this._writer);
          if (isDebugOn()) {
            localException3.printStackTrace(this._writer);
          }
          return -4;
        }
        AlertUtil.logEntry(this.aSZ, "A naming exception occurred while attempting to connect to the banking server.  The following exception occurred:\n ", isDebugOn(), this._writer);
        if (isDebugOn()) {
          localException3.printStackTrace(this._writer);
        }
        return -4;
      }
    }
    catch (CSILException localCSILException2)
    {
      AlertUtil.logEntry(this.aSZ, "A problem occurred while attempting to retrieve user's alert information from the banking server.  The following exception occurred:\n ", isDebugOn(), this._writer);
      if (isDebugOn()) {
        localCSILException2.printStackTrace(this._writer);
      }
      return -4;
    }
    UserAlerts localUserAlerts = new UserAlerts();
    for (int j = 0; j < localArrayList.size(); j++) {
      jdMethod_for((UserAlerts)localArrayList.get(j), (Account)this.aS9.get(j), (Transactions)this.aS4.get(j), (Vector)this.aSV.get(j), localUserAlerts);
    }
    if (localUserAlerts.size() > 0) {
      try
      {
        localICSILEJB.modifyUserAlerts(localUserAlerts, localHashMap);
      }
      catch (RemoteException localRemoteException3)
      {
        try
        {
          reconnectToCsil(false);
          localICSILEJB.modifyUserAlerts(localUserAlerts, localHashMap);
        }
        catch (Exception localException4)
        {
          if ((localException4 instanceof RemoteException))
          {
            AlertUtil.logEntry(this.aSZ, "A remote exception occurred while attempting to mark one time alerts as fired on the banking server.  The following exception occurred while attempting to modify the alert:\n", isDebugOn(), this._writer);
            if (isDebugOn()) {
              localException4.printStackTrace(this._writer);
            }
            return -4;
          }
          if ((localException4 instanceof CSILException))
          {
            AlertUtil.logEntry(this.aSZ, "A problem occurred while attempting to mark one time alerts as fired on the banking server.  The following exception occurred:\n ", isDebugOn(), this._writer);
            if (isDebugOn()) {
              localException4.printStackTrace(this._writer);
            }
            return -4;
          }
          AlertUtil.logEntry(this.aSZ, "A naming exception occurred while attempting to connect to the banking server.  The following exception occurred:\n ", isDebugOn(), this._writer);
          if (isDebugOn()) {
            localException4.printStackTrace(this._writer);
          }
          return -4;
        }
      }
      catch (CSILException localCSILException3)
      {
        AlertUtil.logEntry(this.aSZ, "A problem occurred while attempting to notify the banking server that one time alerts have been fired.  The following exception occurred:\n ", isDebugOn(), this._writer);
        if (isDebugOn()) {
          localCSILException3.printStackTrace(this._writer);
        }
        return -4;
      }
    }
    return i;
  }
  
  private void jdMethod_for(UserAlerts paramUserAlerts1, Account paramAccount, Transactions paramTransactions, Vector paramVector, UserAlerts paramUserAlerts2)
  {
    if (paramUserAlerts1 == null) {
      return;
    }
    for (int i = 0; i < paramUserAlerts1.size(); i++)
    {
      UserAlert localUserAlert = (UserAlert)paramUserAlerts1.get(i);
      boolean bool = false;
      switch (localUserAlert.getAlertTypeValue())
      {
      case 1: 
        bool = jdMethod_try(localUserAlert, paramAccount, paramTransactions, paramVector);
        break;
      case 2: 
        bool = jdMethod_int(localUserAlert, paramAccount, paramTransactions, paramVector);
        break;
      case 3: 
        bool = jdMethod_for(localUserAlert, paramAccount, paramTransactions, paramVector);
        break;
      case 4: 
        bool = jdMethod_new(localUserAlert, paramAccount, paramTransactions, paramVector);
      }
      if ((bool) && (localUserAlert.getOneTimeValue()))
      {
        localUserAlert.setStatus(0);
        paramUserAlerts2.add(localUserAlert);
      }
    }
  }
  
  private boolean jdMethod_try(UserAlert paramUserAlert, Account paramAccount, Transactions paramTransactions, Vector paramVector)
  {
    if (!(paramUserAlert instanceof AccountAlert)) {
      return false;
    }
    if (paramVector == null) {
      return false;
    }
    int i = Account.getAccountSystemTypeFromGroup(paramAccount.getTypeValue());
    int j = -1;
    switch (i)
    {
    case 1: 
      j = this._depositBalanceBAICode;
      break;
    case 2: 
      j = this._assetBalanceBAICode;
      break;
    case 3: 
      j = this._loanBalanceBAICode;
      break;
    case 4: 
      j = this._creditCardBalanceBAICode;
      break;
    default: 
      j = this._depositBalanceBAICode;
    }
    Object localObject1 = null;
    Object localObject2;
    for (int k = 0; k < paramVector.size(); k++)
    {
      localObject2 = (BAISummaryStatus)paramVector.get(k);
      if (((BAISummaryStatus)localObject2)._typeCode == j)
      {
        localObject1 = localObject2;
        break;
      }
    }
    if ((localObject1 != null) && ((localObject1._data instanceof BigDecimal)))
    {
      String str = paramUserAlert.getAdditionalProperty("MIN_AMOUNT");
      localObject2 = paramUserAlert.getAdditionalProperty("MAX_AMOUNT");
      BigDecimal localBigDecimal1 = (BigDecimal)localObject1._data;
      boolean bool1 = false;
      boolean bool2 = false;
      if (str != null) {
        try
        {
          BigDecimal localBigDecimal2 = new BigDecimal(str);
          if (localBigDecimal2.compareTo(localBigDecimal1) > 0) {
            bool1 = true;
          }
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          AlertUtil.logEntry(this.aSZ, "The minimum balance amount for account " + paramAccount.getID() + ":" + paramAccount.getRoutingNum() + ":" + paramAccount.getBankID() + " was not a valid number.  The value provided was \"" + str + "\".", isDebugOn(), this._writer);
        }
      }
      if (localObject2 != null) {
        try
        {
          BigDecimal localBigDecimal3 = new BigDecimal((String)localObject2);
          if (localBigDecimal3.compareTo(localBigDecimal1) < 0) {
            bool2 = true;
          }
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          AlertUtil.logEntry(this.aSZ, "The maximum balance amount for account " + paramAccount.getID() + ":" + paramAccount.getRoutingNum() + ":" + paramAccount.getBankID() + " was not a valid number.  The value provided was \"" + (String)localObject2 + "\".", isDebugOn(), this._writer);
        }
      }
      if ((bool1) || (bool2))
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("KEY_BELOW_MINIMUM_BALANCE", new Boolean(bool1));
        localHashMap.put("KEY_ABOVE_MAXIMUM_BALANCE", new Boolean(bool2));
        jdMethod_for(paramUserAlert, paramAccount, localHashMap);
        this.aS8 += 1;
        return true;
      }
    }
    return false;
  }
  
  private boolean jdMethod_int(UserAlert paramUserAlert, Account paramAccount, Transactions paramTransactions, Vector paramVector)
  {
    if (paramVector == null) {
      return false;
    }
    Vector localVector = getSummaryListForAccount(paramAccount, paramVector);
    if (localVector.size() > 0)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("KEY_BAI_SUMMARIES", localVector);
      jdMethod_for(paramUserAlert, paramAccount, localHashMap);
      this.aSR += 1;
      return true;
    }
    return false;
  }
  
  private boolean jdMethod_for(UserAlert paramUserAlert, Account paramAccount, Transactions paramTransactions, Vector paramVector)
  {
    if (paramTransactions == null) {
      return false;
    }
    String str1 = paramUserAlert.getAdditionalProperty("TRANSACTION TYPE");
    String str2 = paramUserAlert.getAdditionalProperty("COMPARE");
    String str3 = paramUserAlert.getAdditionalProperty("AMOUNT");
    int i = 1;
    if (str2 != null) {
      try
      {
        i = Integer.parseInt(str2);
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        AlertUtil.logEntry(this.aSZ, "The compare type specified for this transaction alert is not a valid integer as required.  The value provided was \"" + str2 + "\".", isDebugOn(), this._writer);
        return false;
      }
    }
    BigDecimal localBigDecimal1 = null;
    if ((str3 != null) && (i != 1)) {
      try
      {
        localBigDecimal1 = new BigDecimal(str3);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        AlertUtil.logEntry(this.aSZ, "The amount for comparison specified for this transaction alert is not a valid number as required.  The value provided was \"" + str3 + "\".", isDebugOn(), this._writer);
        return false;
      }
    }
    int j = -1;
    if (str1 != null) {
      try
      {
        j = Integer.parseInt(str1);
      }
      catch (NumberFormatException localNumberFormatException3)
      {
        AlertUtil.logEntry(this.aSZ, "The transaction type specified for this transaction alert is not a valid integer as required.  The value provided was \"" + str1 + "\".", isDebugOn(), this._writer);
        return false;
      }
    }
    ArrayList localArrayList = getBAICodesForTransactionType(j);
    Transactions localTransactions = new Transactions();
    for (int k = 0; k < paramTransactions.size(); k++)
    {
      Transaction localTransaction = (Transaction)paramTransactions.get(k);
      if ((j == -1) || (jdMethod_for(localTransaction.getSubTypeValue(), localArrayList)))
      {
        Currency localCurrency = localTransaction.getAmountValue();
        BigDecimal localBigDecimal2 = null;
        if (localCurrency != null)
        {
          localBigDecimal2 = localCurrency.getAmountValue();
          if (localBigDecimal2.compareTo(new BigDecimal("0")) < 0) {
            localBigDecimal2 = localBigDecimal2.negate();
          }
        }
        if (i == 1) {
          localTransactions.add(localTransaction);
        } else if ((i == 2) && (localBigDecimal2 != null))
        {
          if (localBigDecimal2.compareTo(localBigDecimal1) < 0) {
            localTransactions.add(localTransaction);
          }
        }
        else if ((i == 3) && (localBigDecimal2 != null))
        {
          if (localBigDecimal2.compareTo(localBigDecimal1) > 0) {
            localTransactions.add(localTransaction);
          }
        }
        else if ((i == 4) && (localBigDecimal2 != null) && (localBigDecimal2.compareTo(localBigDecimal1) == 0)) {
          localTransactions.add(localTransaction);
        }
      }
    }
    if (localTransactions.size() > 0)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("KEY_TRANSACTIONS_FOR_ALERT", localTransactions);
      jdMethod_for(paramUserAlert, paramAccount, localHashMap);
      this.aS0 += 1;
      return true;
    }
    return false;
  }
  
  private boolean jdMethod_for(int paramInt, ArrayList paramArrayList)
  {
    for (int i = 0; i < paramArrayList.size(); i++) {
      if (paramInt == ((Integer)paramArrayList.get(i)).intValue()) {
        return true;
      }
    }
    return false;
  }
  
  private boolean jdMethod_new(UserAlert paramUserAlert, Account paramAccount, Transactions paramTransactions, Vector paramVector)
  {
    if (paramTransactions == null) {
      return false;
    }
    for (int i = 0; i < paramTransactions.size(); i++)
    {
      Transaction localTransaction = (Transaction)paramTransactions.get(i);
      if (localTransaction.getSubTypeValue() == this._nsfTransactionBAICode)
      {
        jdMethod_for(paramUserAlert, paramAccount, new HashMap());
        this.aSS += 1;
        return true;
      }
    }
    return false;
  }
  
  private void jdMethod_for(UserAlert paramUserAlert, Account paramAccount, HashMap paramHashMap)
  {
    DeliveryInfos localDeliveryInfos = paramUserAlert.getDeliveryInfos();
    String str = null;
    switch (paramUserAlert.getAlertTypeValue())
    {
    case 1: 
      str = "AccountBalance";
      break;
    case 2: 
      str = "ACCOUNTSUMMARY";
      break;
    case 3: 
      str = "Transaction";
      break;
    case 4: 
      str = "NSF";
    }
    for (int i = 0; i < localDeliveryInfos.size(); i++)
    {
      DeliveryInfo localDeliveryInfo = (DeliveryInfo)localDeliveryInfos.get(i);
      localObject = null;
      try
      {
        localObject = getAlertMessage(paramUserAlert, paramAccount, localDeliveryInfo, paramHashMap);
      }
      catch (Exception localException2)
      {
        return;
      }
      Properties localProperties = localDeliveryInfo.getPropertiesValue();
      localProperties.setProperty("subject", ((Message)localObject).getSubject());
      localProperties.setProperty("body", ((Message)localObject).getContent());
      localProperties.setProperty("AlertType", str);
    }
    Message localMessage = null;
    try
    {
      localMessage = jdMethod_for(paramUserAlert, paramAccount, false, paramHashMap);
    }
    catch (Exception localException1)
    {
      return;
    }
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = AlertUtil.convertDeliveryInfos(localDeliveryInfos);
    Object localObject = new SecureUser();
    ((SecureUser)localObject).fromXML(paramUserAlert.getAdditionalProperty("SECUREUSER"));
    try
    {
      long l = System.currentTimeMillis();
      int j = getEngine().createAlert(getAppInfo(), AlertUtil.getUserIdForAlert((SecureUser)localObject), 0, 1, null, arrayOfIAEDeliveryInfo, localMessage.getContent());
      this.aSY += 1;
      this.aS5 += System.currentTimeMillis() - l;
    }
    catch (AEException localAEException)
    {
      AlertUtil.logEntry(this.aSZ, "An immediate alert could not be created to deliver this alert to the customer's contact points.  The following exception occurred while adding the alert to the alert's engine:\n ", isDebugOn(), this._writer);
      if (isDebugOn()) {
        localAEException.printStackTrace(this._writer);
      }
    }
  }
  
  protected Message getAlertMessage(UserAlert paramUserAlert, Account paramAccount, DeliveryInfo paramDeliveryInfo, HashMap paramHashMap)
    throws Exception
  {
    Properties localProperties = paramDeliveryInfo.getPropertiesValue();
    boolean bool = new Boolean(localProperties.getProperty("secure")).booleanValue();
    Message localMessage = null;
    try
    {
      localMessage = jdMethod_for(paramUserAlert, paramAccount, bool, paramHashMap);
    }
    catch (Exception localException)
    {
      AlertUtil.logEntry(this.aSZ, "Unable to retrieve a message for an alert to be fired.  The alert will not be processed.  The exception is as follows:\n", isDebugOn(), this._writer);
      if (isDebugOn()) {
        localException.printStackTrace(this._writer);
      }
    }
    return localMessage;
  }
  
  private Message jdMethod_for(UserAlert paramUserAlert, Account paramAccount, boolean paramBoolean, HashMap paramHashMap)
    throws Exception
  {
    MessageImpl localMessageImpl = new MessageImpl();
    Object localObject2;
    Object localObject3;
    Object localObject4;
    if (paramUserAlert.getAlertTypeValue() == 1)
    {
      boolean bool = ((Boolean)paramHashMap.get("KEY_BELOW_MINIMUM_BALANCE")).booleanValue();
      localObject2 = new Object[2];
      localObject2[0] = paramAccount.getConsumerDisplayText();
      if (bool)
      {
        localObject3 = new BigDecimal(paramUserAlert.getAdditionalProperty("MIN_AMOUNT"));
        localObject2[1] = new LocalizableCurrency(new Currency((BigDecimal)localObject3, paramAccount.getCurrencyCode(), paramUserAlert.getLocale()));
      }
      else
      {
        localObject3 = new BigDecimal(paramUserAlert.getAdditionalProperty("MAX_AMOUNT"));
        localObject2[1] = new LocalizableCurrency(new Currency((BigDecimal)localObject3, paramAccount.getCurrencyCode(), paramUserAlert.getLocale()));
      }
      localObject3 = null;
      localObject4 = null;
      if (paramBoolean)
      {
        localObject3 = new LocalizableString("com.ffusion.beans.alerts.resources", "ACCOUNT_BALANCE_ALERT_SECURE_SUBJECT", (Object[])localObject2);
        if (bool) {
          localObject4 = new LocalizableString("com.ffusion.beans.alerts.resources", "ACCOUNT_BALANCE_ALERT_SECURE_BODY_BELOW_MIN", (Object[])localObject2);
        } else {
          localObject4 = new LocalizableString("com.ffusion.beans.alerts.resources", "ACCOUNT_BALANCE_ALERT_SECURE_BODY_ABOVE_MAX", (Object[])localObject2);
        }
      }
      else
      {
        localObject3 = new LocalizableString("com.ffusion.beans.alerts.resources", "ACCOUNT_BALANCE_ALERT_UNSECURE_SUBJECT", (Object[])localObject2);
        if (bool) {
          localObject4 = new LocalizableString("com.ffusion.beans.alerts.resources", "ACCOUNT_BALANCE_ALERT_UNSECURE_BODY_BELOW_MIN", (Object[])localObject2);
        } else {
          localObject4 = new LocalizableString("com.ffusion.beans.alerts.resources", "ACCOUNT_BALANCE_ALERT_UNSECURE_BODY_ABOVE_MAX", (Object[])localObject2);
        }
      }
      localMessageImpl.setSubject((String)((LocalizableString)localObject3).localize(paramUserAlert.getLocale()));
      localMessageImpl.setContent((String)((LocalizableString)localObject4).localize(paramUserAlert.getLocale()));
    }
    else
    {
      Object localObject1;
      if (paramUserAlert.getAlertTypeValue() == 2)
      {
        localObject1 = (Vector)paramHashMap.get("KEY_BAI_SUMMARIES");
        localObject2 = new Object[2];
        localObject2[0] = paramAccount.getConsumerDisplayText();
        localObject2[1] = getSummaryDisplayListForAccount(paramUserAlert, paramAccount, (Vector)localObject1);
        localObject3 = null;
        localObject4 = null;
        if (paramBoolean)
        {
          localObject3 = new LocalizableString("com.ffusion.beans.alerts.resources", "ACCOUNT_SUMMARY_ALERT_SECURE_SUBJECT", (Object[])localObject2);
          localObject4 = new LocalizableString("com.ffusion.beans.alerts.resources", "ACCOUNT_SUMMARY_ALERT_SECURE_BODY", (Object[])localObject2);
        }
        else
        {
          localObject3 = new LocalizableString("com.ffusion.beans.alerts.resources", "ACCOUNT_SUMMARY_ALERT_UNSECURE_SUBJECT", (Object[])localObject2);
          localObject4 = new LocalizableString("com.ffusion.beans.alerts.resources", "ACCOUNT_SUMMARY_ALERT_UNSECURE_BODY", (Object[])localObject2);
        }
        localMessageImpl.setSubject((String)((LocalizableString)localObject3).localize(paramUserAlert.getLocale()));
        localMessageImpl.setContent((String)((LocalizableString)localObject4).localize(paramUserAlert.getLocale()));
      }
      else if (paramUserAlert.getAlertTypeValue() == 3)
      {
        localObject1 = (Transactions)paramHashMap.get("KEY_TRANSACTIONS_FOR_ALERT");
        localObject2 = paramUserAlert.getAdditionalProperty("TRANSACTION TYPE");
        localObject3 = paramUserAlert.getAdditionalProperty("COMPARE");
        localObject4 = paramUserAlert.getAdditionalProperty("AMOUNT");
        int i = 1;
        if (localObject3 != null) {
          try
          {
            i = Integer.parseInt((String)localObject3);
          }
          catch (NumberFormatException localNumberFormatException1)
          {
            AlertUtil.logEntry(this.aSZ, "The compare type specified for this transaction alert is not a valid integer as required.  The value provided was \"" + (String)localObject3 + "\".", isDebugOn(), this._writer);
            throw new Exception("The compare type specified for this transaction alert is not a valid integer as required.  The value provided was \"" + (String)localObject3 + "\".");
          }
        }
        BigDecimal localBigDecimal = null;
        if ((localObject4 != null) && (i != 1)) {
          try
          {
            localBigDecimal = new BigDecimal((String)localObject4);
          }
          catch (NumberFormatException localNumberFormatException2)
          {
            AlertUtil.logEntry(this.aSZ, "The amount for comparison specified for this transaction alert is not a valid number as required.  The value provided was \"" + (String)localObject4 + "\".", isDebugOn(), this._writer);
            throw new Exception("The amount for comparison specified for this transaction alert is not a valid number as required.  The value provided was \"" + (String)localObject4 + "\".");
          }
        }
        int j = -1;
        if (localObject2 != null) {
          try
          {
            j = Integer.parseInt((String)localObject2);
          }
          catch (NumberFormatException localNumberFormatException3)
          {
            AlertUtil.logEntry(this.aSZ, "The transaction type specified for this transaction alert is not a valid integer as required.  The value provided was \"" + (String)localObject2 + "\".", isDebugOn(), this._writer);
            throw new Exception("The transaction type specified for this transaction alert is not a valid integer as required.  The value provided was \"" + (String)localObject2 + "\".");
          }
        }
        String str1 = null;
        if (localObject2 != null) {
          str1 = (String)TransactionTypeCache.getTransactionTypeCache(paramUserAlert.getLocale()).get(new Integer(j));
        }
        LocalizableString localLocalizableString1 = null;
        LocalizableString localLocalizableString2 = null;
        LocalizableList localLocalizableList = new LocalizableList("com.ffusion.beans.alerts.resources", "TRANSACTION_ALERT_LIST_SEPERATOR");
        Object localObject5;
        for (int k = 0; k < ((Transactions)localObject1).size(); k++)
        {
          localObject5 = (Transaction)((Transactions)localObject1).get(k);
          ((Transaction)localObject5).setLocale(paramUserAlert.getLocale());
          Object[] arrayOfObject2 = new Object[4];
          arrayOfObject2[0] = new LocalizableDate(((Transaction)localObject5).getProcessingDate(), false);
          String str2 = (String)TransactionTypeCache.getTransactionTypeCache(paramUserAlert.getLocale()).get(new Integer(((Transaction)localObject5).getTypeValue()));
          arrayOfObject2[1] = (str2 == null ? "" : str2);
          int m = 0;
          if (((Transaction)localObject5).getDescription() != null)
          {
            if (((Transaction)localObject5).getDescription().length() > 40)
            {
              arrayOfObject2[2] = ((Transaction)localObject5).getDescription().substring(0, 40);
              m = 1;
            }
            else
            {
              arrayOfObject2[2] = ((Transaction)localObject5).getDescription();
            }
          }
          else {
            arrayOfObject2[2] = "";
          }
          Currency localCurrency = ((Transaction)localObject5).getAmountValue();
          localCurrency.setLocale(paramUserAlert.getLocale());
          arrayOfObject2[3] = new LocalizableCurrency(localCurrency);
          String str3 = m != 0 ? "TRANSACTION_ALERT_LIST_ITEM_TRUNCATED_MEMO" : "TRANSACTION_ALERT_LIST_ITEM_NON_TRUNCATED_MEMO";
          localLocalizableList.add(new LocalizableString("com.ffusion.beans.alerts.resources", str3, arrayOfObject2));
        }
        Object[] arrayOfObject1 = new Object[5];
        arrayOfObject1[0] = new Integer(((Transactions)localObject1).size());
        arrayOfObject1[1] = (str1 == null ? "" : str1);
        arrayOfObject1[2] = paramAccount.getConsumerDisplayText();
        if ((localObject4 == null) || (i == 1)) {
          arrayOfObject1[3] = "";
        } else {
          arrayOfObject1[3] = new LocalizableCurrency(new Currency(localBigDecimal, paramAccount.getCurrencyCode(), paramUserAlert.getLocale()));
        }
        arrayOfObject1[4] = localLocalizableList;
        if (paramBoolean)
        {
          localObject5 = null;
          if (i == 1) {
            localObject5 = "TRANSACTION_ALERT_SECURE_BODY_NO_CONDITION";
          } else if (i == 2) {
            localObject5 = "TRANSACTION_ALERT_SECURE_BODY_UNDER";
          } else if (i == 3) {
            localObject5 = "TRANSACTION_ALERT_SECURE_BODY_OVER";
          } else if (i == 4) {
            localObject5 = "TRANSACTION_ALERT_SECURE_BODY_EQUAL";
          } else {
            localObject5 = "TRANSACTION_ALERT_SECURE_BODY_NO_CONDITION";
          }
          localLocalizableString1 = new LocalizableString("com.ffusion.beans.alerts.resources", (String)localObject5, arrayOfObject1);
          localLocalizableString2 = new LocalizableString("com.ffusion.beans.alerts.resources", "TRANSACTION_ALERT_SECURE_SUBJECT", arrayOfObject1);
        }
        else
        {
          localObject5 = null;
          if (i == 1) {
            localObject5 = "TRANSACTION_ALERT_UNSECURE_BODY_NO_CONDITION";
          } else if (i == 2) {
            localObject5 = "TRANSACTION_ALERT_UNSECURE_BODY_UNDER";
          } else if (i == 3) {
            localObject5 = "TRANSACTION_ALERT_UNSECURE_BODY_OVER";
          } else if (i == 4) {
            localObject5 = "TRANSACTION_ALERT_UNSECURE_BODY_EQUAL";
          } else {
            localObject5 = "TRANSACTION_ALERT_UNSECURE_BODY_NO_CONDITION";
          }
          localLocalizableString1 = new LocalizableString("com.ffusion.beans.alerts.resources", (String)localObject5, arrayOfObject1);
          localLocalizableString2 = new LocalizableString("com.ffusion.beans.alerts.resources", "TRANSACTION_ALERT_UNSECURE_SUBJECT", arrayOfObject1);
        }
        localMessageImpl.setSubject((String)localLocalizableString2.localize(paramUserAlert.getLocale()));
        localMessageImpl.setContent((String)localLocalizableString1.localize(paramUserAlert.getLocale()));
      }
      else if (paramUserAlert.getAlertTypeValue() == 4)
      {
        localObject1 = new Object[1];
        localObject1[0] = paramAccount.getConsumerDisplayText();
        localObject2 = null;
        localObject3 = null;
        if (paramBoolean)
        {
          localObject2 = new LocalizableString("com.ffusion.beans.alerts.resources", "NSF_ALERT_SECURE_BODY", (Object[])localObject1);
          localObject3 = new LocalizableString("com.ffusion.beans.alerts.resources", "NSF_ALERT_SECURE_SUBJECT", (Object[])localObject1);
        }
        else
        {
          localObject2 = new LocalizableString("com.ffusion.beans.alerts.resources", "NSF_ALERT_UNSECURE_BODY", (Object[])localObject1);
          localObject3 = new LocalizableString("com.ffusion.beans.alerts.resources", "NSF_ALERT_UNSECURE_SUBJECT", (Object[])localObject1);
        }
        localMessageImpl.setSubject((String)((LocalizableString)localObject3).localize(paramUserAlert.getLocale()));
        localMessageImpl.setContent((String)((LocalizableString)localObject2).localize(paramUserAlert.getLocale()));
      }
    }
    return localMessageImpl;
  }
  
  protected Vector getSummaryListForAccount(Account paramAccount, Vector paramVector)
  {
    Vector localVector = new Vector();
    int i = Account.getAccountSystemTypeFromGroup(Account.getAccountGroupFromType(paramAccount.getTypeValue()));
    for (int j = 0; j < paramVector.size(); j++)
    {
      BAISummaryStatus localBAISummaryStatus = (BAISummaryStatus)paramVector.get(j);
      if ((localBAISummaryStatus._fundsType.equals("0")) || (localBAISummaryStatus._fundsType.equals("Z"))) {
        switch (i)
        {
        case 1: 
        case 2: 
          if ((localBAISummaryStatus._typeCode == this._priorDayBalanceBAICode) || (localBAISummaryStatus._typeCode == this._currentBalanceBAICode) || (localBAISummaryStatus._typeCode == this._availableBalanceBAICode)) {
            localVector.add(localBAISummaryStatus);
          }
          break;
        case 3: 
        case 4: 
          if (localBAISummaryStatus._typeCode == this._currentBalanceBAICode) {
            localVector.add(localBAISummaryStatus);
          }
          break;
        }
      }
    }
    return localVector;
  }
  
  protected LocalizableList getSummaryDisplayListForAccount(UserAlert paramUserAlert, Account paramAccount, Vector paramVector)
  {
    LocalizableList localLocalizableList = new LocalizableList("com.ffusion.beans.alerts.resources", "ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_SEPERATOR");
    for (int i = 0; i < paramVector.size(); i++)
    {
      BAISummaryStatus localBAISummaryStatus = (BAISummaryStatus)paramVector.get(i);
      String str = null;
      if (localBAISummaryStatus._typeCode == this._priorDayBalanceBAICode) {
        str = "ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_ITEM_PRIOR_DAY";
      } else if (localBAISummaryStatus._typeCode == this._currentBalanceBAICode) {
        str = "ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_ITEM_CURRENT_BALANCE";
      } else if (localBAISummaryStatus._typeCode == this._availableBalanceBAICode) {
        str = "ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_ITEM_AVAILABLE_BALANCE";
      }
      if (str != null)
      {
        Object[] arrayOfObject = new Object[1];
        if ((localBAISummaryStatus._data instanceof BigDecimal)) {
          arrayOfObject[0] = new LocalizableCurrency(new Currency((BigDecimal)localBAISummaryStatus._data, paramAccount.getCurrencyCode(), paramUserAlert.getLocale()));
        }
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.alerts.resources", str, arrayOfObject);
        arrayOfObject = new Object[1];
        arrayOfObject[0] = localLocalizableString;
        localLocalizableList.add(new LocalizableString("com.ffusion.beans.alerts.resources", "ACCOUNT_SUMMARY_ALERT_SUMMARY_LIST_ITEM", arrayOfObject));
      }
    }
    return localLocalizableList;
  }
  
  private void ae()
    throws Exception
  {
    if (this._baiCodeCache != null) {
      return;
    }
    HashMap localHashMap1 = TransactionTypeCache.getTransactionTypes(0, new HashMap(0));
    HashMap localHashMap2 = XMLUtil.readXmlToHashMap(this, "baiTypeCodes.xml");
    HashMap localHashMap3 = (HashMap)localHashMap2.get("TYPE_CODE_LIST");
    if (localHashMap3 == null) {
      throw new Exception("The TYPE_CODE_LIST tag did not exist in the document");
    }
    ArrayList localArrayList1 = (ArrayList)localHashMap3.get("TYPE_CODE");
    if (localArrayList1 == null) {
      throw new Exception("TYPE_CODE tags were not found in the XML document");
    }
    Object localObject = localHashMap2.get("CUSTOM_TYPE_CODE_LIST");
    if ((localObject != null & localObject instanceof HashMap))
    {
      HashMap localHashMap4 = (HashMap)localObject;
      ArrayList localArrayList2 = (ArrayList)localHashMap4.get("TYPE_CODE");
      if (localArrayList2 != null) {
        localArrayList1.addAll(localArrayList2);
      }
    }
    try
    {
      this._baiCodeCache = new HashMap();
      for (int i = 0; i < localArrayList1.size(); i++)
      {
        HashMap localHashMap5 = (HashMap)localArrayList1.get(i);
        String str1 = (String)localHashMap5.get("CODE");
        String str2 = (String)localHashMap5.get("DESCRIPTION");
        String str3 = (String)localHashMap5.get("TRANSACTION");
        String str4 = (String)localHashMap5.get("LEVEL");
        String str5 = (String)localHashMap5.get("DATA_TYPE");
        String str6 = (String)localHashMap5.get("SUBLEVEL");
        String str7 = (String)localHashMap5.get("TRANSACTION_TYPE");
        if ((str1 == null) || (str2 == null) || (str3 == null) || (str4 == null) || (str5 == null)) {
          throw new Exception("Missing data in BAI type codes xml");
        }
        if (str6 == null) {
          str6 = "Regular";
        }
        int j;
        try
        {
          j = Integer.parseInt(str1);
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          throw new Exception("Non-integer BAI code " + str1 + " in BAI type codes xml");
        }
        int k = am(str3);
        int m = al(str4);
        int n = an(str5);
        int i1;
        if (n == 4)
        {
          String str8 = (String)localHashMap5.get("NUM_DECIMALS");
          if (str8 == null) {
            throw new Exception("Missing NUM_DECIMALS tag in BAI type codes xml");
          }
          try
          {
            i1 = Integer.parseInt(str8);
          }
          catch (NumberFormatException localNumberFormatException2)
          {
            throw new Exception("Non-integer number of decimals (" + str8 + ") in BAI type codes xml");
          }
        }
        else
        {
          i1 = -1;
        }
        int i2;
        if (str7 == null) {
          i2 = p(k);
        } else if (jdMethod_try(str7, localHashMap1)) {
          try
          {
            i2 = Integer.parseInt(str7);
          }
          catch (NumberFormatException localNumberFormatException3)
          {
            throw new Exception("Transaction type " + str7 + " provided for BAI code " + j + " in BAI type codes xml is not valid; " + "value must be an integer");
          }
        } else {
          throw new Exception("Invalid transaction type " + str7 + " specified in BAI type codes xml");
        }
        BAITypeCodeInfo localBAITypeCodeInfo = new BAITypeCodeInfo(j, str2, k, m, n, i1, i2, null);
        localBAITypeCodeInfo.setSubLevel(str6);
        this._baiCodeCache.put(new Integer(j), localBAITypeCodeInfo);
      }
    }
    catch (Exception localException)
    {
      this._baiCodeCache = null;
      throw localException;
    }
  }
  
  private boolean jdMethod_try(String paramString, HashMap paramHashMap)
  {
    if ((paramString == null) || (paramHashMap == null)) {
      return false;
    }
    Integer localInteger = null;
    try
    {
      localInteger = new Integer(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return false;
    }
    return paramHashMap.containsKey(localInteger);
  }
  
  private int p(int paramInt)
    throws Exception
  {
    if (paramInt == 0) {
      return 0;
    }
    if (paramInt == 1) {
      return 4;
    }
    if (paramInt == 2) {
      return 5;
    }
    throw new Exception(paramInt + " is not a valid transaction code");
  }
  
  private int am(String paramString)
    throws Exception
  {
    if ("NA".equalsIgnoreCase(paramString)) {
      return 0;
    }
    if ("CR".equalsIgnoreCase(paramString)) {
      return 1;
    }
    if ("DB".equalsIgnoreCase(paramString)) {
      return 2;
    }
    throw new Exception("transaction string value " + paramString + " provided; " + "value must be " + "NA" + " or " + "CR" + " or " + "DB");
  }
  
  private int al(String paramString)
    throws Exception
  {
    if ("STATUS".equalsIgnoreCase(paramString)) {
      return 0;
    }
    if ("SUMMARY".equalsIgnoreCase(paramString)) {
      return 1;
    }
    if ("DETAIL".equalsIgnoreCase(paramString)) {
      return 2;
    }
    throw new Exception("level string value " + paramString + " provided; " + "value must be " + "STATUS" + " or " + "SUMMARY" + " or " + "DETAIL");
  }
  
  private int an(String paramString)
    throws Exception
  {
    if ("Amount".equalsIgnoreCase(paramString)) {
      return 0;
    }
    if ("String".equalsIgnoreCase(paramString)) {
      return 1;
    }
    if ("Date".equalsIgnoreCase(paramString)) {
      return 2;
    }
    if ("Time".equalsIgnoreCase(paramString)) {
      return 3;
    }
    if (paramString.toLowerCase().startsWith("Decimal".toLowerCase())) {
      return 4;
    }
    throw new Exception("data type string value " + paramString + " provided; " + "value must be " + "Amount" + " or " + "String" + " or " + "Date" + " or " + "Time" + " or should start with " + "Decimal");
  }
  
  private void jdMethod_for(IAEAlertInstance paramIAEAlertInstance, String paramString1, String paramString2, boolean paramBoolean, PrintWriter paramPrintWriter)
  {
    String str1 = "ProcessUserInformation.copyFile";
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    if ((paramString1 == null) || (paramString1.length() <= 0))
    {
      AlertUtil.logEntry(this.aSZ, str1 + ": source file is null", paramBoolean, paramPrintWriter);
      return;
    }
    File localFile1 = new File(paramString1);
    if ((paramString2 == null) || (paramString2.length() <= 0))
    {
      AlertUtil.logEntry(this.aSZ, str1 + ": no destination path specified.", paramBoolean, paramPrintWriter);
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
          AlertUtil.logEntry(this.aSZ, str1 + " failed:" + paramString2 + " is not directory.", paramBoolean, paramPrintWriter);
        }
      }
      else
      {
        localFile2.mkdirs();
        AlertUtil.logEntry(this.aSZ, str1 + ": destination directory " + paramString2 + " created.", paramBoolean, paramPrintWriter);
      }
    }
    catch (SecurityException localSecurityException1)
    {
      AlertUtil.logEntry(this.aSZ, str1 + " failed due to SecurityException with message : " + localSecurityException1.getMessage(), paramBoolean, paramPrintWriter);
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
      str2 = localSimpleDateFormat.format(new Date());
      localStringBuffer.insert(i, '-');
      localStringBuffer.insert(i + 1, str2);
    }
    else
    {
      str2 = localSimpleDateFormat.format(new Date());
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
      AlertUtil.logEntry(this.aSZ, str1 + ": failed to move the file with message : " + localSecurityException2.getMessage(), paramBoolean, paramPrintWriter);
      if (paramBoolean) {
        localSecurityException2.printStackTrace(paramPrintWriter);
      }
    }
    catch (IOException localIOException1)
    {
      AlertUtil.logEntry(this.aSZ, str1 + ": failed to move the file with message : " + localIOException1.getMessage(), paramBoolean, paramPrintWriter);
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
  
  private void jdMethod_for(BAITransactionDetail paramBAITransactionDetail, String paramString)
  {
    if (this.aS7 == null) {
      this.aS7 = new Transactions();
    }
    Transaction localTransaction = this.aS7.create();
    localTransaction.setType(o(paramBAITransactionDetail._typeCode));
    localTransaction.setSubType(paramBAITransactionDetail._typeCode);
    localTransaction.setProcessingDate(new DateTime(this.aTf._asOfDate, Locale.getDefault()));
    localTransaction.setDataSourceLoadTime(new DateTime(this.aTf._asOfDate, Locale.getDefault()));
    localTransaction.setDate(new DateTime(this.aTf._asOfDate, Locale.getDefault()));
    localTransaction.setDescription(paramBAITransactionDetail._text);
    localTransaction.setBankReferenceNumber(paramBAITransactionDetail._bankReferenceNumber);
    localTransaction.setCustomerReferenceNumber(paramBAITransactionDetail._customerReferenceNumber);
    localTransaction.setID(paramBAITransactionDetail._bankReferenceNumber);
    if (paramBAITransactionDetail._amount != null)
    {
      localObject1 = (BAITypeCodeInfo)this._baiCodeCache.get(new Integer(paramBAITransactionDetail._typeCode));
      if (localObject1 == null) {
        localObject1 = DCUtil.genericObjectForUnknownBAITypeCodes(paramBAITransactionDetail._typeCode, "TypeCode " + paramBAITransactionDetail._typeCode + " not found from " + "baiTypeCodes.xml", 2, 0, -1);
      }
      int i = 0;
      if (((BAITypeCodeInfo)localObject1).getTransaction() == 2)
      {
        if (paramBAITransactionDetail._amount.compareTo(new BigDecimal("0")) > 0) {
          paramBAITransactionDetail._amount = paramBAITransactionDetail._amount.negate();
        }
      }
      else if ((((BAITypeCodeInfo)localObject1).getTransaction() == 1) && (paramBAITransactionDetail._amount.compareTo(new BigDecimal("0")) < 0)) {
        paramBAITransactionDetail._amount = paramBAITransactionDetail._amount.negate();
      }
      localTransaction.setAmount(new Currency(paramBAITransactionDetail._amount, paramString, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._immediateAvailabilityAmount != null) {
      localTransaction.setImmediateAvailAmount(new Currency(paramBAITransactionDetail._immediateAvailabilityAmount, paramString, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._oneDayAvailabilityAmount != null) {
      localTransaction.setOneDayAvailAmount(new Currency(paramBAITransactionDetail._oneDayAvailabilityAmount, paramString, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._moreThanOneDayAvailabilityAmount != null) {
      localTransaction.setMoreThanOneDayAvailAmount(new Currency(paramBAITransactionDetail._moreThanOneDayAvailabilityAmount, paramString, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._valueDate != null)
    {
      localObject1 = new DateTime(paramBAITransactionDetail._valueDate, Locale.getDefault());
      localTransaction.setValueDate((DateTime)localObject1);
    }
    Object localObject1 = paramBAITransactionDetail.getCustomData();
    Set localSet = ((HashMap)localObject1).keySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      Object localObject3 = ((HashMap)localObject1).get(localObject2);
      localTransaction.put(localObject2, localObject3);
    }
  }
  
  private int o(int paramInt)
  {
    int i = 30;
    BAITypeCodeInfo localBAITypeCodeInfo = (BAITypeCodeInfo)this._baiCodeCache.get(new Integer(paramInt));
    if (localBAITypeCodeInfo == null)
    {
      if (paramInt >= 400) {
        i = 5;
      } else if (paramInt < 400) {
        i = 4;
      }
      return i;
    }
    i = localBAITypeCodeInfo.getTransactionType();
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.ProcessAccountAlerts
 * JD-Core Version:    0.7.0.1
 */