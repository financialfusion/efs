package com.ffusion.fileimporter.fileadapters;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountHistory;
import com.ffusion.beans.accounts.AccountKey;
import com.ffusion.beans.accounts.AccountSummary;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.accounts.AssetAcctSummary;
import com.ffusion.beans.accounts.CreditCardAcctSummary;
import com.ffusion.beans.accounts.DepositAcctSummary;
import com.ffusion.beans.accounts.ExtendedAccountSummaries;
import com.ffusion.beans.accounts.ExtendedAccountSummary;
import com.ffusion.beans.accounts.LoanAcctSummary;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.beans.dataconsolidator.ImportedFile;
import com.ffusion.beans.dataconsolidator.MasterSubRelation;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementSummary;
import com.ffusion.beans.disbursement.DisbursementTransaction;
import com.ffusion.beans.disbursement.DisbursementTransactions;
import com.ffusion.beans.fx.FXCurrencies;
import com.ffusion.beans.fx.FXCurrency;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxCreditItem;
import com.ffusion.beans.lockbox.LockboxCreditItems;
import com.ffusion.beans.lockbox.LockboxSummary;
import com.ffusion.beans.lockbox.LockboxTransaction;
import com.ffusion.beans.lockbox.LockboxTransactions;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.FIException;
import com.ffusion.csil.core.DataConsolidator;
import com.ffusion.csil.core.FX;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.csil.handlers.AccountHandler;
import com.ffusion.csil.handlers.AffiliateBankAdmin;
import com.ffusion.csil.handlers.TreasuryDirect;
import com.ffusion.dataconsolidator.adapter.DCAdapter;
import com.ffusion.dataconsolidator.adapter.DCException;
import com.ffusion.dataconsolidator.adapter.DCUtil;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIAccountIdentifier;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIAccountTrailer;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIFileHeader;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIFileTrailer;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIGroupHeader;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIGroupTrailer;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIParser;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIParserCallback;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIParserConsts;
import com.ffusion.fileimporter.fileadapters.baiparser.BAISummaryStatus;
import com.ffusion.fileimporter.fileadapters.baiparser.BAITransactionDetail;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMLogRecord;
import com.ffusion.util.filemonitor.FMLog;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;

public class BAIAdapter
  implements IFileAdapter, BAIParserCallback, BAIParserConsts
{
  private static final Integer aUq = new Integer(200);
  private static final String aUA = "1000";
  private static final int aTV = Integer.parseInt("100");
  private static final int aT0 = Integer.parseInt("400");
  private static final int aTQ = 1000;
  private static final int aUh = 1001;
  private static final int aTI = 1002;
  private static final int aT4 = 1003;
  private static final int aUc = 1004;
  private static final DateFormat aUi = new SimpleDateFormat("yyyy/MM/dd HH:mm");
  private static boolean aUm = false;
  private static FXCurrencies aTT = null;
  private static ImportedFile aUE = null;
  private static final String aUH = "com.ffusion.util.logging.audit.bai";
  private static final String aUB = "AuditMessage_0";
  private static final String aUz = "AuditMessage_1";
  private static final String aUy = "AuditMessage_2";
  private static final String aUw = "AuditMessage_3";
  private static final String aUv = "AuditMessage_4";
  private static final String aUu = "AuditMessage_5";
  private static final String aUt = "AuditMessage_6";
  private static final String aUp = "AuditMessage_7";
  private static final String aUo = "AuditMessage_8";
  private static final String aUn = "AuditMessage_9";
  private static final String aTM = "AuditMessage_10";
  private static final String aTL = "AuditMessage_11";
  private static final String aTJ = "AuditMessage_12";
  private static final String aTH = "AuditMessage_13";
  private static final String aTG = "AuditMessage_14";
  private static final String aTF = "AuditMessage_15";
  private static final String aTE = "AuditMessage_16";
  private static final String aTD = "AuditMessage_17";
  private static final String aTC = "AuditMessage_18";
  private static final String aTB = "AuditMessage_19";
  private static final String aUl = "AuditMessage_20";
  private static final String aUk = "AuditMessage_21";
  private static final String aUg = "AuditMessage_22";
  private static final String aUf = "AuditMessage_23";
  private static final String aUe = "AuditMessage_24";
  private Connection aTY = null;
  private String aTK = null;
  private HashMap aUG = null;
  private BAIFileHeader aUd = null;
  private BAIGroupHeader aTO = null;
  private BAIAccountIdentifier aTP = null;
  private Account aT7 = null;
  private boolean aTA = false;
  private Accounts aTU = new Accounts();
  private Business aUj = null;
  private Transactions aT9 = null;
  private LockboxTransactions aTX = null;
  private LockboxCreditItems aUx = null;
  private LockboxAccount aUs = null;
  private DisbursementAccount aT3 = null;
  private DisbursementTransactions aT2 = null;
  private int aT6 = 0;
  private HashMap aTS = new HashMap();
  private int aUF = 0;
  private int aTZ = 0;
  private int aUa = 0;
  private int aTN = 0;
  private int aUr = 0;
  private HashMap aT5 = null;
  private ArrayList aTW = new ArrayList();
  private DateTime aUD = null;
  private String aUC = null;
  private String aUb = null;
  private String aTz = null;
  private String aT8 = null;
  private String aTR = null;
  private HashSet aT1 = null;
  
  public void initialize(HashMap paramHashMap)
    throws FIException
  {
    if ((paramHashMap == null) || (paramHashMap.isEmpty())) {
      return;
    }
    String str = (String)paramHashMap.get("ALLOWUNKNOWNACCOUNTS");
    aUm = Boolean.valueOf(str).booleanValue();
    if (aTT == null) {
      try
      {
        SecureUser localSecureUser = new SecureUser();
        aTT = FX.getCurrencies(localSecureUser, null);
      }
      catch (CSILException localCSILException)
      {
        DebugLog.throwing("Unable to get currency definitions from Foreign Exchange module.", localCSILException);
      }
    }
  }
  
  public void open(HashMap paramHashMap)
    throws FIException
  {
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      HashMap localHashMap = new HashMap();
      if (paramHashMap != null) {
        localHashMap.putAll(paramHashMap);
      }
      localHashMap.put("BATCHSIZE", aUq);
      DCAdapter.commit(localConnection);
    }
    catch (Exception localException1)
    {
      if (localConnection != null) {
        try
        {
          localConnection.rollback();
        }
        catch (Exception localException2)
        {
          DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.open", localException2);
        }
      }
      FIException localFIException = new FIException("com.ffusion.fileimporter.BAIAdapter.open", 9700, "Open Failed For BAI Adapter", localException1);
      DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.open", localFIException);
      throw localFIException;
    }
    finally
    {
      if (localConnection != null) {
        try
        {
          DCAdapter.releaseDBConnection(localConnection);
        }
        catch (Exception localException3) {}
      }
    }
  }
  
  public void close(HashMap paramHashMap)
    throws FIException
  {}
  
  public void processFile(InputStream paramInputStream, HashMap paramHashMap)
    throws FIException
  {
    String str1 = "BAIAdapter.processFile";
    DebugLog.log(Level.INFO, str1 + " Started...");
    if (paramHashMap != null)
    {
      this.aTK = ((String)paramHashMap.get("BAI_FILE_IDENTIFIER"));
      if (this.aTK == null) {
        this.aTK = ((String)paramHashMap.get("FILE_NAME"));
      }
      if (this.aTK == null)
      {
        localObject1 = new FIException("com.ffusion.fileimporter.BAIAdapter.processFile", 9722, "Extra does not contain a file identifier");
        DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processFile", (Throwable)localObject1);
        throw ((Throwable)localObject1);
      }
    }
    else
    {
      localObject1 = new FIException("com.ffusion.fileimporter.BAIAdapter.processFile", 9722, "Extra does not contain a file identifier");
      DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processFile", (Throwable)localObject1);
      throw ((Throwable)localObject1);
    }
    Object localObject1 = jdMethod_byte("BAI", paramHashMap);
    ((FMLogRecord)localObject1).setStatus("In Process");
    try
    {
      FMLog.log((FMLogRecord)localObject1);
    }
    catch (FMException localFMException1)
    {
      DebugLog.throwing(str1, localFMException1);
    }
    try
    {
      this.aTY = DCAdapter.getDBConnection();
      BAIParser localBAIParser = new BAIParser(this);
      localBAIParser.processBAIFile(paramInputStream, paramHashMap);
      if (this.aTP != null)
      {
        localObject2 = new FIException("com.ffusion.fileimporter.BAIAdapter.processFile", 9723);
        DebugLog.throwing("Missing account trailer.", (Throwable)localObject2);
        throw ((Throwable)localObject2);
      }
      if (this.aTO != null)
      {
        localObject2 = new FIException("com.ffusion.fileimporter.BAIAdapter.processFile", 9724);
        DebugLog.throwing("Missing group trailer.", (Throwable)localObject2);
        throw ((Throwable)localObject2);
      }
      if (this.aUd != null)
      {
        localObject2 = new FIException("com.ffusion.fileimporter.BAIAdapter.processFile", 9725);
        DebugLog.throwing("Missing file trailer.", (Throwable)localObject2);
        throw ((Throwable)localObject2);
      }
      Object localObject2 = new DateTime();
      HashMap localHashMap = new HashMap();
      localHashMap.put("DCACCOUNTIDLIST", this.aT1);
      DCAdapter.modifyDataSourceLoadTime(this.aTY, (DateTime)localObject2, aUE, localHashMap);
      this.aT1 = null;
      DCAdapter.commit(this.aTY);
      localObject1 = jdMethod_byte("BAI", paramHashMap);
      ((FMLogRecord)localObject1).setStatus("Complete");
      String str2 = ((FMLogRecord)localObject1).getMessage();
      Object[] arrayOfObject = new Object[2];
      if (str2 == null)
      {
        arrayOfObject[0] = "";
        arrayOfObject[1] = Integer.toString(this.aT6);
      }
      else
      {
        arrayOfObject[0] = str2;
        arrayOfObject[1] = Integer.toString(this.aT6);
      }
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_0", arrayOfObject);
      ((FMLogRecord)localObject1).setLocalizableMessage(localLocalizableString);
      try
      {
        FMLog.log((FMLogRecord)localObject1);
      }
      catch (FMException localFMException5)
      {
        DebugLog.throwing(str1, localFMException5);
      }
      finally
      {
        arrayOfObject = null;
        str2 = null;
      }
    }
    catch (FIException localFIException1)
    {
      try
      {
        DCAdapter.rollback(this.aTY);
      }
      catch (Exception localException2) {}
      localObject1 = jdMethod_byte("BAI", paramHashMap);
      ((FMLogRecord)localObject1).setStatus("Error");
      ((FMLogRecord)localObject1).setMessage(localFIException1.why);
      try
      {
        FMLog.log((FMLogRecord)localObject1);
      }
      catch (FMException localFMException2)
      {
        DebugLog.throwing(str1, localFMException2);
      }
      localObject1 = jdMethod_byte("BAI", paramHashMap);
      ((FMLogRecord)localObject1).setStatus("Failed");
      try
      {
        FMLog.log((FMLogRecord)localObject1);
      }
      catch (FMException localFMException3)
      {
        DebugLog.throwing(str1, localFMException3);
      }
      FIException localFIException2 = new FIException("com.ffusion.fileimporter.BAIAdapter.processFile", 9715, "Process Failed For BAI processFile", localFIException1);
      DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processFile", localFIException2);
      throw localFIException2;
    }
    catch (Exception localException1)
    {
      try
      {
        DCAdapter.rollback(this.aTY);
      }
      catch (Exception localException3) {}
      localObject1 = jdMethod_byte("BAI", paramHashMap);
      ((FMLogRecord)localObject1).setStatus("Failed");
      try
      {
        FMLog.log((FMLogRecord)localObject1);
      }
      catch (FMException localFMException4)
      {
        DebugLog.throwing(str1, localFMException4);
      }
      FIException localFIException3 = new FIException("com.ffusion.fileimporter.BAIAdapter.processFile", 9715, "Process Failed For BAI processFile", localException1);
      DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processFile", localFIException3);
      throw localFIException3;
    }
    finally
    {
      if (this.aTY != null) {
        try
        {
          DCAdapter.releaseDBConnection(this.aTY);
          this.aTY = null;
        }
        catch (Exception localException4)
        {
          FIException localFIException4 = new FIException("com.ffusion.fileimporter.BAIAdapter.processFile", 9715, "Process Failed For BAI processFile", localException4);
          DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processFile", localFIException4);
          throw localFIException4;
        }
      }
      this.aUd = null;
      this.aTO = null;
      this.aTP = null;
      this.aT7 = null;
      this.aUG = null;
      this.aTK = null;
    }
    DebugLog.log(Level.INFO, str1 + " Finished.");
  }
  
  public void processBAIRecord(Object paramObject)
    throws FIException
  {
    String str1 = "BAIAdapter.processBAIRecord";
    LocalizableString localLocalizableString = null;
    Object[] arrayOfObject = null;
    Object localObject1;
    if ((paramObject instanceof BAIFileHeader))
    {
      DebugLog.log(Level.INFO, "Processing BAI file header...");
      this.aUd = ((BAIFileHeader)paramObject);
      if (this.aUd._fileCreationDate != null)
      {
        arrayOfObject = new Object[2];
        arrayOfObject[0] = this.aUd._fileIdentificationNumber;
        arrayOfObject[1] = aUi.format(this.aUd._fileCreationDate.getTime());
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_2", arrayOfObject);
      }
      else
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = this.aUd._fileIdentificationNumber;
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_1", arrayOfObject);
      }
      localObject1 = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
      ((FMLogRecord)localObject1).setStatus("Info");
      try
      {
        FMLog.log((FMLogRecord)localObject1);
      }
      catch (FMException localFMException1)
      {
        DebugLog.throwing(str1, localFMException1);
      }
      finally
      {
        arrayOfObject = null;
        localLocalizableString = null;
      }
      this.aUG = new HashMap();
      this.aUG.put("BAI_FILE_IDENTIFIER", this.aUd._fileIdentificationNumber);
      this.aUG.put("BAI_FILE_NAME", this.aTK);
      this.aUG.put("BAI_FILE_DATE", this.aUd._fileCreationDate);
      aUE = new ImportedFile(2, this.aUd._fileIdentificationNumber, this.aUC, this.aUd._fileCreationDate);
      this.aUF = 0;
      this.aTZ = 0;
      this.aUa = 0;
      this.aTN = 0;
      this.aUr = 0;
      this.aT5 = new HashMap();
      this.aT1 = new HashSet();
      DebugLog.log(Level.INFO, "Done processing BAI file header.");
    }
    else if ((paramObject instanceof BAIGroupHeader))
    {
      DebugLog.log(Level.INFO, "Processing BAI group header.");
      this.aTO = ((BAIGroupHeader)paramObject);
      this.aUD = new DateTime(this.aTO._asOfDate, Locale.getDefault());
      DebugLog.log(Level.INFO, "Processing group " + this.aTO._originatorID + ".");
      localObject1 = "AuditMessage_3";
      arrayOfObject = new Object[2];
      switch (this.aTO._asOfDateModifier)
      {
      case 3: 
      case 4: 
        this.aUG.put("DATA_CLASSIFICATION", "I");
        localObject1 = "AuditMessage_4";
        break;
      case 1: 
      case 2: 
      default: 
        this.aUG.put("DATA_CLASSIFICATION", "P");
        localObject1 = "AuditMessage_5";
      }
      arrayOfObject[0] = this.aTO._ultimateReceiverID;
      arrayOfObject[1] = aUi.format(this.aTO._asOfDate.getTime());
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", (String)localObject1, arrayOfObject);
      FMLogRecord localFMLogRecord2 = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
      localFMLogRecord2.setStatus("Info");
      try
      {
        FMLog.log(localFMLogRecord2);
      }
      catch (FMException localFMException4)
      {
        DebugLog.throwing(str1, localFMException4);
      }
      finally
      {
        arrayOfObject = null;
        localLocalizableString = null;
      }
      if (this.aTO._groupStatus != 1)
      {
        FIException localFIException = new FIException("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", 9716, "Process Failed For BAI processFile record");
        DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", localFIException);
        throw localFIException;
      }
      DebugLog.log(Level.INFO, "Done processing BAI group header.");
    }
    else
    {
      Object localObject2;
      Object localObject3;
      Object localObject9;
      if ((paramObject instanceof BAIAccountIdentifier))
      {
        DebugLog.log(Level.INFO, "Processing BAI account record.");
        this.aT9 = null;
        this.aTX = null;
        this.aUx = null;
        this.aT2 = null;
        this.aTP = ((BAIAccountIdentifier)paramObject);
        if (!ap(this.aTP._currencyCode))
        {
          arrayOfObject = new Object[4];
          arrayOfObject[0] = Integer.toString(this.aTP._lineNumber);
          arrayOfObject[1] = this.aTP._currencyCode;
          arrayOfObject[2] = this.aUd._senderIdentification;
          try
          {
            arrayOfObject[3] = AccountUtil.buildAccountDisplayText(this.aTP._customerAccountNumber, Locale.getDefault());
          }
          catch (UtilException localUtilException1)
          {
            DebugLog.log(Level.FINE, "Unable to build account display text for " + this.aTP._customerAccountNumber);
            arrayOfObject[3] = this.aTP._customerAccountNumber;
          }
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_6", arrayOfObject);
          localObject2 = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
          ((FMLogRecord)localObject2).setStatus("Warning");
          try
          {
            FMLog.log((FMLogRecord)localObject2);
          }
          catch (FMException localFMException2)
          {
            DebugLog.throwing(str1, localFMException2);
          }
          finally
          {
            arrayOfObject = null;
            localLocalizableString = null;
          }
        }
        localObject2 = this.aTU.create(this.aUd._senderIdentification, this.aTP._customerAccountNumber, this.aTP._customerAccountNumber, 0);
        DebugLog.log(Level.INFO, "Processing account " + this.aTP._customerAccountNumber + ".");
        ((Account)localObject2).setRoutingNum(this.aTO._originatorID);
        ((Account)localObject2).setCurrencyCode("USD");
        try
        {
          this.aT7 = DCAdapter.getAccount((Account)localObject2, this.aTY, null);
        }
        catch (Exception localException5)
        {
          this.aT7 = null;
        }
        try
        {
          this.aT7 = AccountAdapter.getAccount(this.aUd._senderIdentification, ((Account)localObject2).getRoutingNum(), this.aTP._customerAccountNumber);
        }
        catch (Exception localException6)
        {
          this.aT7 = null;
        }
        if (this.aT7 == null)
        {
          if (aUm)
          {
            this.aTA = false;
            this.aT7 = ((Account)localObject2);
            arrayOfObject = new Object[3];
            arrayOfObject[0] = Integer.toString(this.aTP._lineNumber);
            arrayOfObject[1] = ((Account)localObject2).getRoutingNum();
            try
            {
              arrayOfObject[2] = AccountUtil.buildAccountDisplayText(((Account)localObject2).getID(), Locale.getDefault());
            }
            catch (UtilException localUtilException2)
            {
              DebugLog.log(Level.FINE, "Unable to build account display text for " + ((Account)localObject2).getID());
              arrayOfObject[2] = ((Account)localObject2).getID();
            }
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_7", arrayOfObject);
            FMLogRecord localFMLogRecord3 = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
            localFMLogRecord3.setStatus("Warning");
            try
            {
              FMLog.log(localFMLogRecord3);
            }
            catch (FMException localFMException5)
            {
              DebugLog.throwing(str1, localFMException5);
            }
            finally
            {
              arrayOfObject = null;
              localLocalizableString = null;
            }
          }
          else
          {
            this.aTA = true;
            arrayOfObject = new Object[3];
            arrayOfObject[0] = Integer.toString(this.aTP._lineNumber);
            arrayOfObject[1] = ((Account)localObject2).getRoutingNum();
            try
            {
              arrayOfObject[2] = AccountUtil.buildAccountDisplayText(((Account)localObject2).getID(), Locale.getDefault());
            }
            catch (UtilException localUtilException3)
            {
              DebugLog.log(Level.WARNING, "Unable to build account display text for " + ((Account)localObject2).getID());
              arrayOfObject[2] = ((Account)localObject2).getID();
            }
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_8", arrayOfObject);
            localObject3 = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
            ((FMLogRecord)localObject3).setStatus("Warning");
            try
            {
              FMLog.log((FMLogRecord)localObject3);
            }
            catch (FMException localFMException6)
            {
              DebugLog.throwing(str1, localFMException6);
            }
            finally
            {
              arrayOfObject = null;
              localLocalizableString = null;
            }
          }
          this.aT9 = null;
          this.aUj = null;
        }
        else
        {
          localObject3 = this.aT7.getCurrencyCode();
          Object localObject6;
          if (!((String)localObject3).equalsIgnoreCase(this.aTP._currencyCode))
          {
            arrayOfObject = new Object[5];
            arrayOfObject[0] = Integer.toString(this.aTP._lineNumber);
            arrayOfObject[1] = ((Account)localObject2).getRoutingNum();
            try
            {
              arrayOfObject[2] = AccountUtil.buildAccountDisplayText(this.aT7.getID(), Locale.getDefault());
            }
            catch (UtilException localUtilException4)
            {
              DebugLog.log(Level.FINE, "Unable to build account display text for " + this.aT7.getID());
              arrayOfObject[2] = this.aT7.getID();
            }
            arrayOfObject[3] = this.aTP._currencyCode;
            arrayOfObject[4] = localObject3;
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_9", arrayOfObject);
            localObject6 = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
            ((FMLogRecord)localObject6).setStatus("Warning");
            try
            {
              FMLog.log((FMLogRecord)localObject6);
            }
            catch (FMException localFMException8)
            {
              DebugLog.throwing(str1, localFMException8);
            }
            this.aTA = true;
            localObject9 = new StringBuffer();
            ((StringBuffer)localObject9).append(((FMLogRecord)localObject6).getMessage());
            ((StringBuffer)localObject9).append(" Account skipped.");
            DebugLog.log(Level.INFO, ((StringBuffer)localObject9).toString());
            arrayOfObject = null;
            localLocalizableString = null;
          }
          try
          {
            localObject6 = new SecureUser();
            ((SecureUser)localObject6).setBankID("1000");
            localObject9 = AccountHandler.getBusinessesForAccount((SecureUser)localObject6, this.aT7, this.aUG);
            if ((localObject9 == null) || (((Businesses)localObject9).size() == 0)) {
              return;
            }
            this.aUj = ((Business)((Businesses)localObject9).get(0));
          }
          catch (CSILException localCSILException)
          {
            localObject9 = new FIException("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", 9715, "Unexpected exception when retrieving the business for the account.", localCSILException);
            DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", localCSILException);
            throw ((Throwable)localObject9);
          }
        }
        DebugLog.log(Level.INFO, "Done processing BAI account record.");
      }
      else
      {
        Object localObject7;
        if ((paramObject instanceof BAITransactionDetail))
        {
          DebugLog.log(Level.FINE, "Processing transaction detail record.");
          localObject2 = (BAITransactionDetail)paramObject;
          localObject3 = ((BAITransactionDetail)localObject2)._warnings.iterator();
          while (((Iterator)localObject3).hasNext())
          {
            localObject7 = (String)((Iterator)localObject3).next();
            arrayOfObject = new Object[1];
            arrayOfObject[0] = localObject7;
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_24", arrayOfObject);
            localObject9 = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
            ((FMLogRecord)localObject9).setStatus("Warning");
            try
            {
              FMLog.log((FMLogRecord)localObject9);
            }
            catch (FMException localFMException9)
            {
              DebugLog.throwing(str1, localFMException9);
            }
            finally
            {
              localLocalizableString = null;
            }
          }
          if (!this.aTA) {
            if (BAITypeCodes.isTransactionCode(((BAITransactionDetail)localObject2)._typeCode)) {
              jdMethod_try((BAITransactionDetail)localObject2);
            } else if (BAITypeCodes.isLockboxTransactionCode(((BAITransactionDetail)localObject2)._typeCode)) {
              jdMethod_new((BAITransactionDetail)localObject2);
            } else if (BAITypeCodes.isLockboxCreditItemCode(((BAITransactionDetail)localObject2)._typeCode)) {
              jdMethod_for((BAITransactionDetail)localObject2);
            } else if (BAITypeCodes.isDspTransactionCode(((BAITransactionDetail)localObject2)._typeCode)) {
              jdMethod_int((BAITransactionDetail)localObject2);
            } else {
              jdMethod_try((BAITransactionDetail)localObject2);
            }
          }
          DebugLog.log(Level.FINE, "Done processing transaction detail record.");
        }
        else if ((paramObject instanceof BAIAccountTrailer))
        {
          DebugLog.log(Level.INFO, "Processing trailer record for account " + this.aTP._customerAccountNumber + ".");
          if (!this.aTA)
          {
            if (this.aT9 != null) {
              try
              {
                int i = Account.getAccountSystemTypeFromGroup(this.aT7.getAccountGroup());
                if (i == 1)
                {
                  localObject3 = (this.aTO._asOfDateModifier == 3) || (this.aTO._asOfDateModifier == 4) ? "I" : "P";
                  jdMethod_for(this.aT7, this.aT9, (String)localObject3, this.aTY);
                }
                DCAdapter.addTransactions(this.aT7, this.aT9, 2, this.aTY, this.aUG);
                if (this.aUG.get("DCACCOUNTID") != null) {
                  this.aT1.add(this.aUG.get("DCACCOUNTID"));
                }
              }
              catch (Exception localException1)
              {
                localObject3 = new FIException("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", 9715, "failed on adding a transaction", localException1);
                DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", (Throwable)localObject3);
                throw ((Throwable)localObject3);
              }
            }
            if (this.aTX != null) {
              try
              {
                DCAdapter.addLockboxTransactions(this.aUs, this.aTX, 2, this.aTY, this.aUG);
              }
              catch (Exception localException2)
              {
                localObject3 = new FIException("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", 9715, "failed on adding a lockbox transaction", localException2);
                DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", (Throwable)localObject3);
                throw ((Throwable)localObject3);
              }
            }
            if (this.aUx != null) {
              try
              {
                DCAdapter.addLockboxCreditItems(this.aUs, this.aUx, 2, this.aTY, this.aUG);
              }
              catch (Exception localException3)
              {
                localObject3 = new FIException("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", 9715, "failed on adding a lockbox credit item", localException3);
                DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", (Throwable)localObject3);
                throw ((Throwable)localObject3);
              }
            }
            if (this.aT2 != null) {
              try
              {
                DCAdapter.addDisbursementTransactions(this.aT3, this.aT2, 2, this.aTY, this.aUG);
              }
              catch (Exception localException4)
              {
                localObject3 = new FIException("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", 9715, "failed on adding a disbursement transaction", localException4);
                DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", (Throwable)localObject3);
                throw ((Throwable)localObject3);
              }
            }
            int j = Account.getAccountSystemTypeFromGroup(this.aT7.getAccountGroup());
            if ((!this.aTW.contains(this.aT7.getRoutingNum())) && (j == 1))
            {
              try
              {
                localObject3 = jdMethod_int(this.aT7);
                if (localObject3 == null)
                {
                  localObject7 = (this.aTO._asOfDateModifier == 3) || (this.aTO._asOfDateModifier == 4) ? "I" : "P";
                  jdMethod_byte(this.aT7, this.aTP._baiSummaryStatus, this.aTO._asOfDate.getTime(), (String)localObject7, this.aTY);
                }
                else
                {
                  jdMethod_else((ArrayList)localObject3);
                }
              }
              catch (Exception localException7)
              {
                localObject7 = new FIException("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", 9715, "failed when doing the IR calculations for deposit account with ID " + this.aT7.getID() + ".", localException7);
                DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", (Throwable)localObject7);
                throw ((Throwable)localObject7);
              }
            }
            else
            {
              String str2 = (this.aTO._asOfDateModifier == 3) || (this.aTO._asOfDateModifier == 4) ? "I" : "P";
              jdMethod_byte(this.aT7, this.aTP._baiSummaryStatus, this.aTO._asOfDate.getTime(), str2, this.aTY);
            }
            try
            {
              performRollup();
            }
            catch (Exception localException8)
            {
              localObject7 = new FIException("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", 9715, "Unexpected exception while performing rollup operation.", localException8);
              DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", localException8);
              throw ((Throwable)localObject7);
            }
          }
          this.aT3 = null;
          this.aUs = null;
          this.aTP = null;
          this.aTA = false;
          this.aT7 = null;
          DebugLog.log(Level.INFO, "Done processing account trailer record.");
        }
        else
        {
          Object localObject4;
          if ((paramObject instanceof BAIGroupTrailer))
          {
            DebugLog.log(Level.INFO, "Processing group trailer record for " + this.aTO._originatorID + ".");
            BAIGroupTrailer localBAIGroupTrailer = (BAIGroupTrailer)paramObject;
            arrayOfObject = new Object[3];
            arrayOfObject[0] = this.aTO._ultimateReceiverID;
            arrayOfObject[1] = Integer.toString(localBAIGroupTrailer._numberOfAccounts);
            arrayOfObject[2] = localBAIGroupTrailer._groupControlTotal.toString();
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_10", arrayOfObject);
            localObject4 = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
            ((FMLogRecord)localObject4).setStatus("Info");
            try
            {
              FMLog.log((FMLogRecord)localObject4);
            }
            catch (FMException localFMException7)
            {
              DebugLog.throwing(str1, localFMException7);
            }
            finally
            {
              arrayOfObject = null;
              localLocalizableString = null;
            }
            this.aTO = null;
            DebugLog.log(Level.INFO, "Done processing group trailer record.");
          }
          else if ((paramObject instanceof BAIFileTrailer))
          {
            DebugLog.log(Level.INFO, "Processing file trailer record.");
            try
            {
              DCUtil.recalculateIRCalculations(this.aTY, this.aT5, this.aUG);
              DCUtil.recalculateTransactionRunningBalances(this.aTY, this.aT5, this.aUG);
            }
            catch (DCException localDCException)
            {
              localObject4 = new FIException("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", 9715, "Unexpetced exception when recalculating the master accounts.", localDCException);
              DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", localDCException);
              throw ((Throwable)localObject4);
            }
            logMasterSubStatisticsToFileMonitor();
            this.aT6 = this.aUd._numberOfRecords;
            arrayOfObject = new Object[5];
            arrayOfObject[0] = this.aUd._fileIdentificationNumber;
            arrayOfObject[1] = Integer.toString(this.aUd._numberOfGroups);
            arrayOfObject[2] = Integer.toString(this.aUd._numberOfAccounts);
            arrayOfObject[3] = Integer.toString(this.aUd._numberOfRecords);
            arrayOfObject[4] = this.aUd._runningControlTotal.toString();
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_11", arrayOfObject);
            FMLogRecord localFMLogRecord1 = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
            localFMLogRecord1.setStatus("Info");
            try
            {
              FMLog.log(localFMLogRecord1);
            }
            catch (FMException localFMException3)
            {
              DebugLog.throwing(str1, localFMException3);
            }
            finally
            {
              arrayOfObject = null;
              localLocalizableString = null;
            }
            this.aUd = null;
            this.aUC = null;
            this.aUb = null;
            this.aTz = null;
            this.aT8 = null;
            this.aTR = null;
            this.aTS.clear();
            DebugLog.log(Level.INFO, "Done processing file trailer record.");
          }
        }
      }
    }
  }
  
  protected void logMasterSubStatisticsToFileMonitor()
  {
    String str = "BAIAdapter.processBAIRecord";
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.toString(this.aTZ);
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_12", arrayOfObject);
    FMLogRecord localFMLogRecord = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
    localFMLogRecord.setStatus("Info");
    try
    {
      FMLog.log(localFMLogRecord);
    }
    catch (FMException localFMException1)
    {
      DebugLog.throwing(str, localFMException1);
    }
    finally
    {
      arrayOfObject = null;
      localLocalizableString = null;
    }
    arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.toString(this.aTZ + this.aT5.size());
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_13", arrayOfObject);
    localFMLogRecord = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
    localFMLogRecord.setStatus("Info");
    try
    {
      FMLog.log(localFMLogRecord);
    }
    catch (FMException localFMException2)
    {
      DebugLog.throwing(str, localFMException2);
    }
    finally
    {
      arrayOfObject = null;
      localLocalizableString = null;
    }
    arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.toString(this.aUa);
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_14", arrayOfObject);
    localFMLogRecord = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
    localFMLogRecord.setStatus("Info");
    try
    {
      FMLog.log(localFMLogRecord);
    }
    catch (FMException localFMException3)
    {
      DebugLog.throwing(str, localFMException3);
    }
    finally
    {
      arrayOfObject = null;
      localLocalizableString = null;
    }
    arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.toString(this.aTN);
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_15", arrayOfObject);
    localFMLogRecord = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
    localFMLogRecord.setStatus("Info");
    try
    {
      FMLog.log(localFMLogRecord);
    }
    catch (FMException localFMException4)
    {
      DebugLog.throwing(str, localFMException4);
    }
    finally
    {
      arrayOfObject = null;
      localLocalizableString = null;
    }
    arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.toString(this.aUr);
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_16", arrayOfObject);
    localFMLogRecord = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
    localFMLogRecord.setStatus("Info");
    try
    {
      FMLog.log(localFMLogRecord);
    }
    catch (FMException localFMException5)
    {
      DebugLog.throwing(str, localFMException5);
    }
    finally
    {
      arrayOfObject = null;
      localLocalizableString = null;
    }
    arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.toString(this.aUF);
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_17", arrayOfObject);
    localFMLogRecord = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
    localFMLogRecord.setStatus("Info");
    try
    {
      FMLog.log(localFMLogRecord);
    }
    catch (FMException localFMException6)
    {
      DebugLog.throwing(str, localFMException6);
    }
    finally
    {
      arrayOfObject = null;
      localLocalizableString = null;
    }
  }
  
  protected void performRollup()
    throws Exception
  {
    String str = getDataClassification(this.aTO._asOfDateModifier);
    if (str.equals("I")) {
      return;
    }
    if (this.aUj == null) {
      return;
    }
    SecureUser localSecureUser = new SecureUser();
    localSecureUser.setBankID("1000");
    Accounts localAccounts = TreasuryDirect.getMasterAccounts(localSecureUser, this.aUj, this.aT7, this.aUG);
    if ((localAccounts == null) || (localAccounts.size() == 0)) {
      return;
    }
    Object localObject1;
    Object localObject2;
    if (this.aUj.getLocationIdPlacementValue() == 0)
    {
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = Integer.toString(this.aTP._lineNumber);
      arrayOfObject[1] = this.aUj.getBusinessName();
      arrayOfObject[2] = this.aT7.getRoutingNum();
      arrayOfObject[3] = this.aT7.getDisplayText();
      localObject1 = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_18", arrayOfObject);
      localObject2 = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, (ILocalizable)localObject1);
      ((FMLogRecord)localObject2).setStatus("Warning");
      try
      {
        FMLog.log((FMLogRecord)localObject2);
      }
      catch (FMException localFMException1)
      {
        DebugLog.throwing("BAIAdapter.performRollup", localFMException1);
      }
      finally
      {
        arrayOfObject = null;
        localObject1 = null;
      }
      this.aUF += 1;
      return;
    }
    this.aTZ += 1;
    for (int i = 0; i < localAccounts.size(); i++)
    {
      localObject1 = (Account)localAccounts.get(i);
      localObject2 = TreasuryDirect.getSubAccount(localSecureUser, this.aUj, (Account)localObject1, this.aT7, this.aUG);
      Object localObject3;
      Object localObject5;
      if ((((Account)localObject2).getLocationID() == null) || (((Account)localObject2).getLocationID().trim().length() == 0))
      {
        localObject3 = new Object[5];
        localObject3[0] = Integer.toString(this.aTP._lineNumber);
        localObject3[1] = ((Account)localObject2).getRoutingNum();
        localObject3[2] = ((Account)localObject2).getDisplayText();
        localObject3[3] = ((Account)localObject1).getRoutingNum();
        localObject3[4] = ((Account)localObject1).getDisplayText();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_19", (Object[])localObject3);
        localObject5 = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
        ((FMLogRecord)localObject5).setStatus("Warning");
        try
        {
          FMLog.log((FMLogRecord)localObject5);
        }
        catch (FMException localFMException2)
        {
          DebugLog.throwing("BAIAdapter.performRollup", localFMException2);
        }
        finally
        {
          localObject3 = null;
          localLocalizableString = null;
        }
      }
      else
      {
        localObject3 = new MasterSubRelation();
        ((MasterSubRelation)localObject3).setBankId(this.aUj.getBankId());
        ((MasterSubRelation)localObject3).setBusinessId(this.aUj.getIdValue());
        ((MasterSubRelation)localObject3).setDataDate(this.aUD);
        ((MasterSubRelation)localObject3).setMasterRoutingNumber(((Account)localObject1).getRoutingNum());
        ((MasterSubRelation)localObject3).setMasterAccountId(((Account)localObject1).getID());
        ((MasterSubRelation)localObject3).setSubRoutingNumber(((Account)localObject2).getRoutingNum());
        ((MasterSubRelation)localObject3).setSubAccountId(((Account)localObject2).getID());
        ((MasterSubRelation)localObject3).setWithholdNZBSubAccounts(((Account)localObject1).getWithholdNonZeroBalanceSubAccountsValue());
        ((MasterSubRelation)localObject3).setIncludeZBACreditInRollup(((Account)localObject2).getIncludeZBACreditInRollupValue());
        ((MasterSubRelation)localObject3).setIncludeZBADebitInRollup(((Account)localObject2).getIncludeZBADebitInRollupValue());
        ((MasterSubRelation)localObject3).setLocationId(((Account)localObject2).getLocationID());
        ((MasterSubRelation)localObject3).setLocationIdPlacement(this.aUj.getLocationIdPlacementValue());
        DCAdapter.addMasterSubRelations(this.aTY, this.aUD, (MasterSubRelation)localObject3, this.aUG);
        int j = 0;
        localObject5 = getClosingLedger(this.aTP._baiSummaryStatus);
        Object localObject6;
        Object localObject8;
        Object localObject9;
        if ((localObject5 == null) || (!((Currency)localObject5).equals(0.0D)))
        {
          this.aTN += 1;
          if (((Account)localObject1).getWithholdNonZeroBalanceSubAccountsValue())
          {
            localObject6 = new Object[5];
            localObject6[0] = Integer.toString(this.aTP._lineNumber);
            localObject6[1] = ((Account)localObject2).getRoutingNum();
            localObject6[2] = ((Account)localObject2).getDisplayText();
            localObject6[3] = ((Account)localObject1).getRoutingNum();
            localObject6[4] = ((Account)localObject1).getDisplayText();
            localObject8 = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_20", (Object[])localObject6);
            localObject9 = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, (ILocalizable)localObject8);
            ((FMLogRecord)localObject9).setStatus("Warning");
            try
            {
              FMLog.log((FMLogRecord)localObject9);
            }
            catch (FMException localFMException3)
            {
              DebugLog.throwing("BAIAdapter.performRollup", localFMException3);
            }
            finally
            {
              localObject6 = null;
              localObject8 = null;
            }
          }
          else
          {
            j = 1;
            this.aUr += 1;
            localObject6 = new Object[5];
            localObject6[0] = Integer.toString(this.aTP._lineNumber);
            localObject6[1] = ((Account)localObject2).getRoutingNum();
            localObject6[2] = ((Account)localObject2).getDisplayText();
            localObject6[3] = ((Account)localObject1).getRoutingNum();
            localObject6[4] = ((Account)localObject1).getDisplayText();
            localObject8 = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_21", (Object[])localObject6);
            localObject9 = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, (ILocalizable)localObject8);
            ((FMLogRecord)localObject9).setStatus("Warning");
            try
            {
              FMLog.log((FMLogRecord)localObject9);
            }
            catch (FMException localFMException4)
            {
              DebugLog.throwing("BAIAdapter.performRollup", localFMException4);
            }
            finally
            {
              localObject6 = null;
              localObject8 = null;
            }
          }
        }
        else
        {
          j = 1;
        }
        if (j != 0)
        {
          this.aUa += 1;
          localObject6 = DCAdapter.getDatesWithNonZeroClosingLedger(this.aTY, this.aT7, this.aUD, this.aUG);
          localObject8 = new HashMap();
          localObject9 = new ArrayList();
          Object localObject13;
          for (int k = ((ArrayList)localObject6).size() - 1; k >= 0; k--)
          {
            localObject11 = (DateTime)((ArrayList)localObject6).get(k);
            localObject13 = DCAdapter.getMasterSubRelation(this.aTY, this.aUj.getIdValue(), (DateTime)localObject11, (Account)localObject1, (Account)localObject2, this.aUG);
            if ((localObject13 == null) || (!((MasterSubRelation)localObject13).getWithholdNZBSubAccounts())) {
              break;
            }
            ((ArrayList)localObject9).add(localObject11);
            ((HashMap)localObject8).put(localObject11, localObject13);
          }
          for (k = ((ArrayList)localObject9).size() - 1; k >= 0; k--)
          {
            localObject11 = (DateTime)((ArrayList)localObject9).get(k);
            localObject13 = getTransactions(this.aT7, (Calendar)localObject11, (Calendar)localObject11, "P", this.aUj, this.aTY);
            MasterSubRelation localMasterSubRelation = (MasterSubRelation)((HashMap)localObject8).get(localObject11);
            rollupTransactions((Account)localObject1, this.aUD, (Transactions)localObject13, localMasterSubRelation.getLocationIdPlacement(), localMasterSubRelation.getLocationId(), localMasterSubRelation.getIncludeZBACreditInRollup(), localMasterSubRelation.getIncludeZBADebitInRollup(), localMasterSubRelation.getBAIFileIdentifier(), localMasterSubRelation.getBAIFileName(), localMasterSubRelation.getBAIFileDate());
          }
          DateTime localDateTime = new DateTime(this.aUd._fileCreationDate, Locale.getDefault());
          rollupTransactions((Account)localObject1, this.aUD, this.aT9, this.aUj.getLocationIdPlacementValue(), ((Account)localObject2).getLocationID(), ((Account)localObject2).getIncludeZBACreditInRollupValue(), ((Account)localObject2).getIncludeZBADebitInRollupValue(), this.aUd._fileIdentificationNumber, this.aTK, localDateTime);
          Object localObject11 = new AccountKey(((Account)localObject1).getID(), ((Account)localObject1).getBankID(), ((Account)localObject1).getRoutingNum(), ((Account)localObject1).getTypeValue());
          if (this.aT5.containsKey(localObject11))
          {
            localObject13 = (ArrayList)this.aT5.get(localObject11);
            if (!((ArrayList)localObject13).contains(this.aUD)) {
              ((ArrayList)localObject13).add(this.aUD);
            }
          }
          else
          {
            localObject13 = new ArrayList();
            ((ArrayList)localObject13).add(this.aUD);
            this.aT5.put(localObject11, localObject13);
          }
        }
      }
    }
  }
  
  protected void rollupTransactions(Account paramAccount, DateTime paramDateTime1, Transactions paramTransactions, int paramInt, String paramString1, boolean paramBoolean1, boolean paramBoolean2, String paramString2, String paramString3, DateTime paramDateTime2)
    throws Exception
  {
    if ((paramTransactions == null) || (paramTransactions.size() == 0)) {
      return;
    }
    Transactions localTransactions = new Transactions(paramTransactions.getLocale());
    for (int i = 0; i < paramTransactions.size(); i++)
    {
      Transaction localTransaction = (Transaction)paramTransactions.get(i);
      if (((paramBoolean1) || (localTransaction.getSubTypeValue() != 275)) && ((paramBoolean2) || (localTransaction.getSubTypeValue() != 575)))
      {
        localTransactions.add(localTransaction);
        if (paramInt == 1) {
          localTransaction.setBankReferenceNumber(paramString1);
        } else if (paramInt == 2) {
          localTransaction.setCustomerReferenceNumber(paramString1);
        }
        localTransaction.setProcessingDate(paramDateTime1);
        localTransaction.setTransactionIndex(0L);
      }
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("DATA_CLASSIFICATION", "P");
    localHashMap.put("BAI_FILE_IDENTIFIER", paramString2);
    localHashMap.put("BAI_FILE_NAME", paramString3);
    localHashMap.put("BAI_FILE_DATE", paramDateTime2);
    DCAdapter.addTransactions(paramAccount, localTransactions, 2, this.aTY, localHashMap);
    if (localHashMap.get("DCACCOUNTID") != null) {
      this.aT1.add(localHashMap.get("DCACCOUNTID"));
    }
  }
  
  protected static Transactions getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, Business paramBusiness, Connection paramConnection)
    throws DCException
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("DATA_CLASSIFICATION", paramString);
    Properties localProperties = new Properties();
    DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
    if (paramCalendar1 != null) {
      localProperties.setProperty("StartDate", localDateFormat.format(paramCalendar1.getTime()));
    }
    if (paramCalendar2 != null) {
      localProperties.setProperty("EndDate", localDateFormat.format(paramCalendar2.getTime()));
    }
    if (paramAccount.isMaster()) {
      if (paramBusiness.getLocationIdPlacementValue() == 2) {
        localProperties.setProperty("CustomerReferenceNumberNull", "true");
      } else if (paramBusiness.getLocationIdPlacementValue() == 1) {
        localProperties.setProperty("BankReferenceNumberNull", "true");
      }
    }
    return DCAdapter.getTransactions(paramConnection, paramAccount, localProperties, localHashMap);
  }
  
  protected Currency getClosingLedger(Vector paramVector)
  {
    for (int i = 0; i < paramVector.size(); i++)
    {
      BAISummaryStatus localBAISummaryStatus = (BAISummaryStatus)paramVector.get(i);
      if (localBAISummaryStatus._typeCode == 15) {
        return new Currency((BigDecimal)localBAISummaryStatus._data, this.aT7.getLocale());
      }
    }
    return null;
  }
  
  protected String getDataClassification(int paramInt)
  {
    return (paramInt == 3) || (paramInt == 4) ? "I" : "P";
  }
  
  private void jdMethod_else(ArrayList paramArrayList)
    throws Exception
  {
    String str = getDataClassification(this.aTO._asOfDateModifier);
    jdMethod_for(this.aT7, this.aTO._asOfDate.getTime(), paramArrayList, str, this.aTP._baiSummaryStatus);
    if (str.equals("P"))
    {
      BankIdentifier localBankIdentifier = new BankIdentifier(this.aT7.getLocale());
      localBankIdentifier.setBankDirectoryType("FedABA");
      localBankIdentifier.setBankDirectoryId(this.aT7.getRoutingNum());
      SecureUser localSecureUser = new SecureUser();
      localSecureUser.setBankID("1000");
      Date localDate1 = SmartCalendar.getOffsetBankingDay(localSecureUser, localBankIdentifier, this.aTO._asOfDate.getTime(), 1, new HashMap());
      GregorianCalendar[] arrayOfGregorianCalendar = jdMethod_for(this.aT7.getLocale(), localDate1);
      HashMap localHashMap = new HashMap();
      localHashMap.put("DATA_CLASSIFICATION", "I");
      Date localDate2 = null;
      DateTime localDateTime = jdMethod_for(arrayOfGregorianCalendar, localHashMap);
      Calendar localCalendar;
      if (localDateTime != null)
      {
        localDate2 = localDateTime.getTime();
        localCalendar = Calendar.getInstance();
        localCalendar.setTime(localDate2);
        localCalendar.add(13, 1);
        localDate2 = localCalendar.getTime();
      }
      if (localDate2 == null)
      {
        localCalendar = Calendar.getInstance();
        localCalendar.setTime(localDate1);
        localCalendar.set(11, 0);
        localCalendar.set(12, 0);
        localCalendar.set(13, 1);
        localDate2 = localCalendar.getTime();
      }
      jdMethod_for(this.aT7, localDate2, paramArrayList, "I", new Vector());
    }
  }
  
  private DateTime jdMethod_for(GregorianCalendar[] paramArrayOfGregorianCalendar, HashMap paramHashMap)
    throws DCException
  {
    Object localObject = DCAdapter.getHistoryMaxDate(this.aT7, paramArrayOfGregorianCalendar[0], paramArrayOfGregorianCalendar[1], this.aTY, paramHashMap);
    DateTime localDateTime = DCAdapter.getSummaryMaxDate(this.aT7, paramArrayOfGregorianCalendar[0], paramArrayOfGregorianCalendar[1], this.aTY, paramHashMap);
    if ((localDateTime != null) && ((localObject == null) || ((localObject != null) && (((DateTime)localObject).compare(localDateTime) < 0)))) {
      localObject = localDateTime;
    }
    LockboxAccount localLockboxAccount = new LockboxAccount();
    localLockboxAccount.setAccountID(this.aT7.getID());
    localLockboxAccount.setAccountNumber(this.aT7.getNumber());
    localLockboxAccount.setRoutingNumber(this.aT7.getRoutingNum());
    localLockboxAccount.setBankID(this.aT7.getBankID());
    localLockboxAccount.setCurrencyType(this.aT7.getCurrencyCode());
    localDateTime = DCAdapter.getLockboxSummariesMaxDate(localLockboxAccount, paramArrayOfGregorianCalendar[0], paramArrayOfGregorianCalendar[1], this.aTY, paramHashMap);
    if ((localDateTime != null) && ((localObject == null) || ((localObject != null) && (((DateTime)localObject).compare(localDateTime) < 0)))) {
      localObject = localDateTime;
    }
    DisbursementAccount localDisbursementAccount = new DisbursementAccount();
    localDisbursementAccount.setAccountID(this.aT7.getID());
    localDisbursementAccount.setAccountNumber(this.aT7.getNumber());
    localDisbursementAccount.setRoutingNumber(this.aT7.getRoutingNum());
    localDisbursementAccount.setBankID(this.aT7.getBankID());
    localDisbursementAccount.setCurrencyType(this.aT7.getCurrencyCode());
    localDateTime = DCAdapter.getDisbursementSummariesMaxDate(localDisbursementAccount, paramArrayOfGregorianCalendar[0], paramArrayOfGregorianCalendar[1], this.aTY, paramHashMap);
    if ((localDateTime != null) && ((localObject == null) || ((localObject != null) && (((DateTime)localObject).compare(localDateTime) < 0)))) {
      localObject = localDateTime;
    }
    localDateTime = DCAdapter.getExtendedSummaryMaxDate(this.aT7, paramArrayOfGregorianCalendar[0], paramArrayOfGregorianCalendar[1], this.aTY, paramHashMap);
    if ((localDateTime != null) && ((localObject == null) || ((localObject != null) && (((DateTime)localObject).compare(localDateTime) < 0)))) {
      localObject = localDateTime;
    }
    return localObject;
  }
  
  private void jdMethod_for(Account paramAccount, Date paramDate, ArrayList paramArrayList, String paramString, Vector paramVector)
    throws Exception
  {
    GregorianCalendar[] arrayOfGregorianCalendar = jdMethod_for(paramAccount.getLocale(), paramDate);
    String str = paramAccount.getZBAFlag();
    paramAccount.setZBAFlag("B");
    Transactions localTransactions = DCUtil.getTransactions(paramAccount, arrayOfGregorianCalendar[0], arrayOfGregorianCalendar[1], paramString, this.aUj, this.aTY);
    LockboxTransactions localLockboxTransactions = DCUtil.getLockboxTransactions(paramAccount, arrayOfGregorianCalendar[0], arrayOfGregorianCalendar[1], paramString, this.aTY);
    paramAccount.setZBAFlag(str);
    if ((localTransactions == null) || (localLockboxTransactions == null)) {
      return;
    }
    Vector localVector1 = jdMethod_for(localTransactions, localLockboxTransactions, paramVector, paramString, paramArrayList);
    Vector localVector2 = null;
    Object localObject3;
    Object localObject4;
    if (paramString.equals("I"))
    {
      localObject1 = new BankIdentifier(paramAccount.getLocale());
      ((BankIdentifier)localObject1).setBankDirectoryType("FedABA");
      ((BankIdentifier)localObject1).setBankDirectoryId(paramAccount.getRoutingNum());
      localObject2 = new SecureUser();
      ((SecureUser)localObject2).setBankID("1000");
      localObject3 = SmartCalendar.getPreviousDay((SecureUser)localObject2, (BankIdentifier)localObject1, paramDate, new HashMap());
      arrayOfGregorianCalendar = jdMethod_for(paramAccount.getLocale(), (Date)localObject3);
      localObject4 = new HashMap();
      ((HashMap)localObject4).put("DATA_CLASSIFICATION", "P");
      AccountHistories localAccountHistories = DCAdapter.getHistory(paramAccount, arrayOfGregorianCalendar[0], arrayOfGregorianCalendar[1], this.aTY, (HashMap)localObject4);
      if (localAccountHistories != null)
      {
        if (localAccountHistories.size() > 1) {
          localAccountHistories = DCUtil.combineAccountHistoriesByDate(localAccountHistories);
        }
        if (!localAccountHistories.isEmpty())
        {
          AccountHistory localAccountHistory = (AccountHistory)localAccountHistories.get(0);
          localVector2 = DCUtil.getIRCalculationsSummaryStatusItems(localTransactions, localLockboxTransactions, localAccountHistory, paramArrayList, "I", null, new HashMap());
          localVector2 = jdMethod_for(paramVector, localVector2);
        }
      }
    }
    Object localObject1 = new HashMap();
    ((HashMap)localObject1).put("DATA_CLASSIFICATION", paramString);
    arrayOfGregorianCalendar = jdMethod_for(paramAccount.getLocale(), paramDate);
    Object localObject2 = jdMethod_for(arrayOfGregorianCalendar, (HashMap)localObject1);
    if ((localObject2 == null) || (((DateTime)localObject2).compare(new DateTime(paramDate, paramAccount.getLocale())) < 0))
    {
      if (localVector1 != null) {
        paramVector.addAll(localVector1);
      }
      if (localVector2 != null) {
        paramVector.addAll(localVector2);
      }
    }
    else
    {
      localObject3 = new GregorianCalendar();
      ((Calendar)localObject3).setTime(((DateTime)localObject2).getTime());
      ((Calendar)localObject3).add(13, 1);
      localObject4 = new Vector();
      if (localVector1 != null) {
        ((Vector)localObject4).addAll(localVector1);
      }
      if (localVector2 != null) {
        ((Vector)localObject4).addAll(localVector2);
      }
      str = paramAccount.getZBAFlag();
      paramAccount.setZBAFlag("B");
      jdMethod_byte(paramAccount, (Vector)localObject4, ((Calendar)localObject3).getTime(), paramString, this.aTY);
      paramAccount.setZBAFlag(str);
    }
    str = paramAccount.getZBAFlag();
    paramAccount.setZBAFlag("B");
    jdMethod_byte(paramAccount, paramVector, paramDate, paramString, this.aTY);
    paramAccount.setZBAFlag(str);
  }
  
  private void jdMethod_byte(Account paramAccount, Vector paramVector, Date paramDate, String paramString, Connection paramConnection)
    throws FIException
  {
    if ((paramVector == null) || (paramVector.isEmpty())) {
      return;
    }
    jdMethod_int(paramAccount, paramVector, paramDate, paramString, paramConnection);
    jdMethod_new(paramAccount, paramVector, paramDate, paramString, paramConnection);
    jdMethod_for(paramAccount, paramVector, paramDate, paramString, paramConnection);
    jdMethod_try(paramAccount, paramVector, paramDate, paramString, paramConnection);
    jdMethod_for(paramVector, paramDate, paramString, paramConnection);
  }
  
  private void jdMethod_int(Account paramAccount, Vector paramVector, Date paramDate, String paramString, Connection paramConnection)
    throws FIException
  {
    DebugLog.log(Level.FINE, "Processing history records for account " + this.aT7.getID() + ".");
    AccountHistory localAccountHistory = new AccountHistory();
    DateTime localDateTime = new DateTime(paramDate, Locale.getDefault());
    localAccountHistory.setHistoryDate(localDateTime);
    boolean bool = DCUtil.convertSummaryStatusItemsToAccountHistory(paramVector, localAccountHistory);
    if (bool) {
      try
      {
        HashMap localHashMap = (HashMap)this.aUG.clone();
        localHashMap.put("DATA_CLASSIFICATION", paramString);
        DCAdapter.addHistory(paramAccount, localAccountHistory, 2, paramConnection, localHashMap);
        int i = Account.getAccountSystemTypeFromGroup(this.aT7.getAccountGroup());
        if ((localAccountHistory.getClosingLedger() != null) && (paramString.equals("P")) && (i == 1)) {
          jdMethod_for(this.aT7, localAccountHistory, this.aTY);
        }
      }
      catch (Exception localException)
      {
        FIException localFIException = new FIException("com.ffusion.fileimporter.BAIAdapter.addAccountHistory", 9715, "failed on adding an account history", localException);
        DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.addAccountHistory", localFIException);
        throw localFIException;
      }
    }
    DebugLog.log(Level.FINE, "Done processing history records for account " + this.aT7.getID() + ".");
  }
  
  private void jdMethod_new(Account paramAccount, Vector paramVector, Date paramDate, String paramString, Connection paramConnection)
    throws FIException
  {
    DebugLog.log(Level.FINE, "Processing account summary records for account " + paramAccount.getID() + ".");
    Object localObject = null;
    int i = Account.getAccountSystemTypeFromGroup(paramAccount.getAccountGroup());
    if (i == 1) {
      localObject = new DepositAcctSummary();
    } else if (i == 2) {
      localObject = new AssetAcctSummary();
    } else if (i == 4) {
      localObject = new CreditCardAcctSummary();
    } else if (i == 3) {
      localObject = new LoanAcctSummary();
    } else {
      localObject = new DepositAcctSummary();
    }
    DateTime localDateTime = new DateTime(paramDate, Locale.getDefault());
    ((AccountSummary)localObject).setSummaryDate(localDateTime);
    boolean bool = DCUtil.convertSummaryStatusItemsToAccountSummary(paramVector, paramAccount.getAccountGroup(), (AccountSummary)localObject, paramAccount.getCurrencyCode());
    if (bool) {
      try
      {
        HashMap localHashMap = (HashMap)this.aUG.clone();
        localHashMap.put("DATA_CLASSIFICATION", paramString);
        DCAdapter.addSummary(paramAccount, (AccountSummary)localObject, 2, paramConnection, localHashMap);
      }
      catch (Exception localException)
      {
        FIException localFIException = new FIException("com.ffusion.fileimporter.BAIAdapter.addAccountSummary", 9715, "failed on adding an account summary", localException);
        DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.addAccountSummary", localFIException);
        throw localFIException;
      }
    }
    DebugLog.log(Level.FINE, "Done processing account summary records for account " + paramAccount.getID() + ".");
  }
  
  private void jdMethod_for(Account paramAccount, Vector paramVector, Date paramDate, String paramString, Connection paramConnection)
    throws FIException
  {
    DebugLog.log(Level.FINE, "Processing extended account summary records for account " + paramAccount.getID() + ".");
    Object localObject2;
    Object localObject3;
    Object localObject4;
    for (int i = 0; i < paramVector.size(); i++)
    {
      localObject1 = (BAISummaryStatus)paramVector.get(i);
      BAITypeCodeInfo localBAITypeCodeInfo = null;
      try
      {
        localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(((BAISummaryStatus)localObject1)._typeCode);
      }
      catch (CSILException localCSILException)
      {
        localObject2 = new Object[3];
        localObject2[0] = Integer.toString(this.aTP._lineNumber);
        localObject2[1] = Integer.toString(((BAISummaryStatus)localObject1)._typeCode);
        localObject3 = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_22", (Object[])localObject2);
        localObject4 = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, (ILocalizable)localObject3);
        ((FMLogRecord)localObject4).setStatus("Warning");
        try
        {
          FMLog.log((FMLogRecord)localObject4);
        }
        catch (FMException localFMException)
        {
          DebugLog.throwing("BAIAdapter.addAccountHistory", localFMException);
        }
        finally
        {
          localObject2 = null;
          localObject3 = null;
        }
        DebugLog.log(Level.WARNING, "TypeCode " + ((BAISummaryStatus)localObject1)._typeCode + " not found from " + "baiTypeCodes.xml");
      }
    }
    DateTime localDateTime = new DateTime(paramDate, Locale.getDefault());
    Object localObject1 = new ExtendedAccountSummaries();
    boolean bool = DCUtil.convertSummaryStatusItemsToExtendedAccountSummaries(paramVector, (ExtendedAccountSummaries)localObject1, localDateTime);
    for (int j = 0; j < ((ExtendedAccountSummaries)localObject1).size(); j++)
    {
      localObject2 = (ExtendedAccountSummary)((ExtendedAccountSummaries)localObject1).get(j);
      try
      {
        localObject3 = (HashMap)this.aUG.clone();
        ((HashMap)localObject3).put("DATA_CLASSIFICATION", paramString);
        DCAdapter.addExtendedSummary(paramAccount, (ExtendedAccountSummary)localObject2, 2, paramConnection, (HashMap)localObject3);
      }
      catch (Exception localException)
      {
        localObject4 = new FIException("com.ffusion.fileimporter.BAIAdapter.addExtAccountSummary", 9715, "failed on adding an extended account summary", localException);
        DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.addExtAccountSummary", (Throwable)localObject4);
        throw ((Throwable)localObject4);
      }
    }
    DebugLog.log(Level.FINE, "Done processing extended account summary records for account " + paramAccount.getID() + ".");
  }
  
  private int q(int paramInt)
  {
    int i = 30;
    BAITypeCodeInfo localBAITypeCodeInfo = null;
    try
    {
      localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(paramInt);
    }
    catch (CSILException localCSILException)
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
  
  private void jdMethod_try(BAITransactionDetail paramBAITransactionDetail)
    throws FIException
  {
    DebugLog.log(Level.FINE, "Processing transaction records for account " + this.aTP._customerAccountNumber + ".");
    if (this.aT9 == null) {
      this.aT9 = new Transactions();
    }
    Transaction localTransaction = this.aT9.create();
    localTransaction.setType(q(paramBAITransactionDetail._typeCode));
    localTransaction.setSubType(paramBAITransactionDetail._typeCode);
    localTransaction.setProcessingDate(this.aUD);
    localTransaction.setDataSourceLoadTime(this.aUD);
    localTransaction.setDate(this.aUD);
    localTransaction.setDescription(paramBAITransactionDetail._text);
    localTransaction.setBankReferenceNumber(paramBAITransactionDetail._bankReferenceNumber);
    localTransaction.setCustomerReferenceNumber(paramBAITransactionDetail._customerReferenceNumber);
    if (this.aT7.isMaster()) {
      if (this.aUj.getLocationIdPlacementValue() == 1) {
        localTransaction.setBankReferenceNumber(null);
      } else if (this.aUj.getLocationIdPlacementValue() == 2) {
        localTransaction.setCustomerReferenceNumber(null);
      }
    }
    localTransaction.setID(paramBAITransactionDetail._bankReferenceNumber);
    if (paramBAITransactionDetail._amount != null) {
      localTransaction.setAmount(new Currency(paramBAITransactionDetail._amount, Locale.getDefault()));
    } else {
      localTransaction.setRunningBalance(null);
    }
    if (paramBAITransactionDetail._immediateAvailabilityAmount != null) {
      localTransaction.setImmediateAvailAmount(new Currency(paramBAITransactionDetail._immediateAvailabilityAmount, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._oneDayAvailabilityAmount != null) {
      localTransaction.setOneDayAvailAmount(new Currency(paramBAITransactionDetail._oneDayAvailabilityAmount, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._moreThanOneDayAvailabilityAmount != null) {
      localTransaction.setMoreThanOneDayAvailAmount(new Currency(paramBAITransactionDetail._moreThanOneDayAvailabilityAmount, Locale.getDefault()));
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
    DebugLog.log(Level.FINE, "Done processing transaction records for account " + this.aTP._customerAccountNumber + ".");
  }
  
  private void jdMethod_try(Account paramAccount, Vector paramVector, Date paramDate, String paramString, Connection paramConnection)
    throws FIException
  {
    DebugLog.log(Level.FINE, "Processing lockbox summary records for account " + paramAccount.getID() + ".");
    LockboxAccount localLockboxAccount = new LockboxAccount();
    LockboxSummary localLockboxSummary = new LockboxSummary();
    localLockboxAccount.setAccountID(this.aT7.getID());
    localLockboxAccount.setAccountNumber(this.aT7.getNumber());
    localLockboxAccount.setRoutingNumber(this.aT7.getRoutingNum());
    localLockboxAccount.setBankID(this.aT7.getBankID());
    localLockboxAccount.setCurrencyType(this.aT7.getCurrencyCode());
    localLockboxSummary.setLockboxAccount(localLockboxAccount);
    DateTime localDateTime = new DateTime(paramDate, Locale.getDefault());
    localLockboxSummary.setSummaryDate(localDateTime);
    boolean bool = DCUtil.convertSummaryStatusItemsToLockboxSummary(paramVector, localLockboxSummary);
    if (bool) {
      try
      {
        HashMap localHashMap = (HashMap)this.aUG.clone();
        localHashMap.put("DATA_CLASSIFICATION", paramString);
        DCAdapter.addLockboxSummary(localLockboxSummary, 2, this.aTY, localHashMap);
      }
      catch (Exception localException)
      {
        FIException localFIException = new FIException("com.ffusion.fileimporter.BAIAdapter.addLockboxSummary", 9715, "failed on adding a lockbox summary", localException);
        DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.addLockboxSummary", localFIException);
        throw localFIException;
      }
    }
    DebugLog.log(Level.FINE, "Done processing lockbox summary records for account " + paramAccount.getID() + ".");
  }
  
  private void jdMethod_new(BAITransactionDetail paramBAITransactionDetail)
    throws FIException
  {
    DebugLog.log(Level.FINE, "Processing lockbox transaction records for account " + this.aTP._customerAccountNumber + ".");
    if (this.aTX == null) {
      this.aTX = new LockboxTransactions();
    }
    if (this.aUs == null)
    {
      this.aUs = new LockboxAccount();
      this.aUs.setAccountID(this.aT7.getID());
      this.aUs.setAccountNumber(this.aT7.getNumber());
      this.aUs.setRoutingNumber(this.aT7.getRoutingNum());
      this.aUs.setBankID(this.aT7.getBankID());
      this.aUs.setCurrencyType(this.aT7.getCurrencyCode());
    }
    LockboxTransaction localLockboxTransaction = this.aTX.add();
    localLockboxTransaction.setAccountID(this.aT7.getID());
    localLockboxTransaction.setAccountNumber(this.aT7.getNumber());
    localLockboxTransaction.setBankID(this.aT7.getBankID());
    DateTime localDateTime = new DateTime(this.aTO._asOfDate, Locale.getDefault());
    localLockboxTransaction.setTransactionType(paramBAITransactionDetail._typeCode);
    localLockboxTransaction.setProcessingDate(localDateTime);
    localLockboxTransaction.setLockboxNumber(paramBAITransactionDetail._bankReferenceNumber);
    localLockboxTransaction.setDescription(paramBAITransactionDetail._text);
    localLockboxTransaction.setBankReferenceNumber(paramBAITransactionDetail._bankReferenceNumber);
    localLockboxTransaction.setCustomerReferenceNumber(paramBAITransactionDetail._customerReferenceNumber);
    if (paramBAITransactionDetail._amount != null) {
      localLockboxTransaction.setAmount(new Currency(paramBAITransactionDetail._amount, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._immediateAvailabilityAmount != null) {
      localLockboxTransaction.setImmediateFloat(new Currency(paramBAITransactionDetail._immediateAvailabilityAmount, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._oneDayAvailabilityAmount != null) {
      localLockboxTransaction.setOneDayFloat(new Currency(paramBAITransactionDetail._oneDayAvailabilityAmount, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._moreThanOneDayAvailabilityAmount != null) {
      localLockboxTransaction.setTwoDayFloat(new Currency(paramBAITransactionDetail._moreThanOneDayAvailabilityAmount, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._valueDate != null)
    {
      localDateTime = new DateTime(paramBAITransactionDetail._valueDate, Locale.getDefault());
      localLockboxTransaction.setValueDateTime(localDateTime);
    }
    HashMap localHashMap = paramBAITransactionDetail.getCustomData();
    Set localSet = localHashMap.keySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      Object localObject1 = localIterator.next();
      Object localObject2 = localHashMap.get(localObject1);
      localLockboxTransaction.put(localObject1, localObject2);
    }
    DebugLog.log(Level.FINE, "Done processing lockbox transaction records for account " + this.aTP._customerAccountNumber + ".");
  }
  
  private void jdMethod_for(BAITransactionDetail paramBAITransactionDetail)
    throws FIException
  {
    DebugLog.log(Level.FINE, "Processing lockbox credit item records for account " + this.aTP._customerAccountNumber + ".");
    if (this.aUx == null) {
      this.aUx = new LockboxCreditItems();
    }
    if (this.aUs == null)
    {
      this.aUs = new LockboxAccount();
      this.aUs.setAccountID(this.aT7.getID());
      this.aUs.setAccountNumber(this.aT7.getNumber());
      this.aUs.setRoutingNumber(this.aT7.getRoutingNum());
      this.aUs.setBankID(this.aT7.getBankID());
      this.aUs.setCurrencyType(this.aT7.getCurrencyCode());
    }
    LockboxCreditItem localLockboxCreditItem = this.aUx.add();
    localLockboxCreditItem.setAccountID(this.aT7.getID());
    localLockboxCreditItem.setAccountNumber(this.aT7.getNumber());
    localLockboxCreditItem.setBankID(this.aT7.getBankID());
    DateTime localDateTime = new DateTime(this.aTO._asOfDate, Locale.getDefault());
    localLockboxCreditItem.setItemID(paramBAITransactionDetail._typeCode);
    localLockboxCreditItem.setProcessingDate(localDateTime);
    localLockboxCreditItem.setLockboxNumber(paramBAITransactionDetail._bankReferenceNumber);
    localLockboxCreditItem.setMemo(paramBAITransactionDetail._text);
    localLockboxCreditItem.setBankReferenceNumber(paramBAITransactionDetail._bankReferenceNumber);
    localLockboxCreditItem.setCustomerReferenceNumber(paramBAITransactionDetail._customerReferenceNumber);
    if (paramBAITransactionDetail._amount != null) {
      localLockboxCreditItem.setCheckAmount(new Currency(paramBAITransactionDetail._amount, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._immediateAvailabilityAmount != null) {
      localLockboxCreditItem.setImmediateFloat(new Currency(paramBAITransactionDetail._immediateAvailabilityAmount, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._oneDayAvailabilityAmount != null) {
      localLockboxCreditItem.setOneDayFloat(new Currency(paramBAITransactionDetail._oneDayAvailabilityAmount, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._moreThanOneDayAvailabilityAmount != null) {
      localLockboxCreditItem.setTwoDayFloat(new Currency(paramBAITransactionDetail._moreThanOneDayAvailabilityAmount, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._valueDate != null)
    {
      localDateTime = new DateTime(paramBAITransactionDetail._valueDate, Locale.getDefault());
      localLockboxCreditItem.setValueDateTime(localDateTime);
    }
    HashMap localHashMap = paramBAITransactionDetail.getCustomData();
    Set localSet = localHashMap.keySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      Object localObject1 = localIterator.next();
      Object localObject2 = localHashMap.get(localObject1);
      localLockboxCreditItem.put(localObject1, localObject2);
    }
    DebugLog.log(Level.FINE, "Done processing lockbox credit item records for account " + this.aTP._customerAccountNumber + ".");
  }
  
  private void jdMethod_for(Vector paramVector, Date paramDate, String paramString, Connection paramConnection)
    throws FIException
  {
    DebugLog.log(Level.FINE, "Processing disbursement summary records for account " + this.aT7.getID() + ".");
    DisbursementAccount localDisbursementAccount = new DisbursementAccount();
    DisbursementSummary localDisbursementSummary = new DisbursementSummary();
    localDisbursementAccount.setAccountID(this.aT7.getID());
    localDisbursementAccount.setAccountNumber(this.aT7.getNumber());
    localDisbursementAccount.setRoutingNumber(this.aT7.getRoutingNum());
    localDisbursementAccount.setBankID(this.aT7.getBankID());
    localDisbursementAccount.setCurrencyType(this.aT7.getCurrencyCode());
    localDisbursementSummary.setAccount(localDisbursementAccount);
    DateTime localDateTime = new DateTime(paramDate, Locale.getDefault());
    localDisbursementSummary.setSummaryDate(localDateTime);
    boolean bool = DCUtil.convertSummaryStatusItemsToDisbursementSummary(paramVector, localDisbursementSummary);
    if (bool) {
      try
      {
        HashMap localHashMap = (HashMap)this.aUG.clone();
        localHashMap.put("DATA_CLASSIFICATION", paramString);
        DCAdapter.addDisbursementSummary(localDisbursementSummary, 2, paramConnection, localHashMap);
      }
      catch (Exception localException)
      {
        FIException localFIException = new FIException("com.ffusion.fileimporter.BAIAdapter.addDspSummary", 9715, "failed on adding a disbursment summary", localException);
        DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.addDspSummary", localFIException);
        throw localFIException;
      }
    }
    DebugLog.log(Level.FINE, "Done processing disbursement summary records for account " + this.aT7.getID() + ".");
  }
  
  private void jdMethod_int(BAITransactionDetail paramBAITransactionDetail)
    throws FIException
  {
    DebugLog.log(Level.FINE, "Processing disbursement transaction records for account " + this.aTP._customerAccountNumber + ".");
    if (this.aT2 == null) {
      this.aT2 = new DisbursementTransactions();
    }
    if (this.aT3 == null)
    {
      this.aT3 = new DisbursementAccount();
      this.aT3.setAccountID(this.aT7.getID());
      this.aT3.setAccountNumber(this.aT7.getNumber());
      this.aT3.setRoutingNumber(this.aT7.getRoutingNum());
      this.aT3.setBankID(this.aT7.getBankID());
      this.aT3.setCurrencyType(this.aT7.getCurrencyCode());
    }
    DisbursementTransaction localDisbursementTransaction = this.aT2.create();
    localDisbursementTransaction.setAccountID(this.aT7.getID());
    localDisbursementTransaction.setAccountNumber(this.aT7.getNumber());
    localDisbursementTransaction.setBankID(this.aT7.getBankID());
    DateTime localDateTime1 = new DateTime(this.aUd._fileCreationDate, Locale.getDefault());
    localDateTime1.setFormat("MM/dd/yyyy HH:mm");
    localDisbursementTransaction.setPresentment(localDateTime1 + " " + this.aUd._fileIdentificationNumber);
    DateTime localDateTime2 = new DateTime(this.aTO._asOfDate, Locale.getDefault());
    localDisbursementTransaction.setTransID(paramBAITransactionDetail._typeCode);
    localDisbursementTransaction.setProcessingDate(localDateTime2);
    localDisbursementTransaction.setMemo(paramBAITransactionDetail._text);
    localDisbursementTransaction.setBankReferenceNumber(paramBAITransactionDetail._bankReferenceNumber);
    localDisbursementTransaction.setCustomerReferenceNumber(paramBAITransactionDetail._customerReferenceNumber);
    localDisbursementTransaction.setPayee("payee");
    localDisbursementTransaction.setCheckNumber("12345");
    localDisbursementTransaction.setCheckDate(localDateTime2);
    if (paramBAITransactionDetail._amount != null) {
      localDisbursementTransaction.setCheckAmount(new Currency(paramBAITransactionDetail._amount, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._immediateAvailabilityAmount != null) {
      localDisbursementTransaction.setImmediateFundsNeeded(new Currency(paramBAITransactionDetail._immediateAvailabilityAmount, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._oneDayAvailabilityAmount != null) {
      localDisbursementTransaction.setOneDayFundsNeeded(new Currency(paramBAITransactionDetail._oneDayAvailabilityAmount, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._moreThanOneDayAvailabilityAmount != null) {
      localDisbursementTransaction.setTwoDayFundsNeeded(new Currency(paramBAITransactionDetail._moreThanOneDayAvailabilityAmount, Locale.getDefault()));
    }
    if (paramBAITransactionDetail._valueDate != null)
    {
      localDateTime2 = new DateTime(paramBAITransactionDetail._valueDate, Locale.getDefault());
      localDisbursementTransaction.setValueDateTime(localDateTime2);
    }
    HashMap localHashMap = paramBAITransactionDetail.getCustomData();
    Set localSet = localHashMap.keySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      Object localObject1 = localIterator.next();
      Object localObject2 = localHashMap.get(localObject1);
      localDisbursementTransaction.put(localObject1, localObject2);
    }
    DebugLog.log(Level.FINE, "Done processing disbursement transaction records for account " + this.aTP._customerAccountNumber + ".");
  }
  
  private Vector jdMethod_for(Transactions paramTransactions, LockboxTransactions paramLockboxTransactions, Vector paramVector, String paramString, ArrayList paramArrayList)
    throws FIException
  {
    if (((paramTransactions == null) || (paramTransactions.isEmpty())) && ((paramLockboxTransactions == null) || (paramLockboxTransactions.isEmpty()))) {
      return new Vector(0);
    }
    DebugLog.log(Level.INFO, "Calculating status/summary for account " + this.aTP._customerAccountNumber + ".");
    Locale localLocale = paramTransactions.getLocale();
    Vector localVector1 = DCUtil.getIRCalculationsSummaryStatusItems(paramTransactions, paramLockboxTransactions, null, paramArrayList, paramString, null, new HashMap());
    Vector localVector2 = jdMethod_for(paramVector, localVector1);
    DebugLog.log(Level.INFO, "Done calculating status/summary for account " + this.aTP._customerAccountNumber + ".");
    return localVector2;
  }
  
  private Vector jdMethod_for(Vector paramVector1, Vector paramVector2)
  {
    if ((paramVector1 == null) || (paramVector1.isEmpty())) {
      return paramVector2;
    }
    if ((paramVector2 == null) || (paramVector2.isEmpty())) {
      return null;
    }
    Vector localVector = new Vector();
    for (int i = 0; i < paramVector2.size(); i++)
    {
      BAISummaryStatus localBAISummaryStatus1 = (BAISummaryStatus)paramVector2.elementAt(i);
      int j = 0;
      for (int k = 0; k < paramVector1.size(); k++)
      {
        BAISummaryStatus localBAISummaryStatus2 = (BAISummaryStatus)paramVector1.elementAt(k);
        if (localBAISummaryStatus1._typeCode == localBAISummaryStatus2._typeCode)
        {
          j = 1;
          break;
        }
      }
      if (j == 0) {
        localVector.addElement(localBAISummaryStatus1);
      }
    }
    return localVector;
  }
  
  private FMLogRecord jdMethod_byte(String paramString, HashMap paramHashMap)
  {
    String str1 = (String)paramHashMap.get("FILE_NAME");
    String str2 = (String)paramHashMap.get("FROM_SYSTEM");
    String str3 = (String)paramHashMap.get("HOST_NAME");
    String str4 = (String)paramHashMap.get("ACTIVITY_TYPE");
    String str5 = (String)paramHashMap.get("TO_SYSTEM");
    Object localObject = null;
    Object[] arrayOfObject = null;
    try
    {
      localObject = (ILocalizable)paramHashMap.get("COMMENT");
    }
    catch (ClassCastException localClassCastException)
    {
      arrayOfObject = new Object[1];
      arrayOfObject[0] = ((String)paramHashMap.get("COMMENT"));
      localObject = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_24", arrayOfObject);
    }
    FMLogRecord localFMLogRecord = jdMethod_for(paramString, str1, str3, str4, str2, str5, (ILocalizable)localObject);
    arrayOfObject = null;
    localObject = null;
    this.aUC = str1;
    this.aUb = str3;
    this.aTz = str4;
    this.aT8 = str2;
    this.aTR = str5;
    return localFMLogRecord;
  }
  
  private FMLogRecord jdMethod_for(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, ILocalizable paramILocalizable)
  {
    FMLogRecord localFMLogRecord = new FMLogRecord();
    localFMLogRecord.setFileType(paramString1);
    localFMLogRecord.setFileName(paramString2);
    localFMLogRecord.setHostName(paramString3);
    if (paramString6 == null) {
      localFMLogRecord.setToSystem("CBS DC");
    } else {
      localFMLogRecord.setToSystem(paramString6);
    }
    if (paramString5 == null) {
      localFMLogRecord.setFromSystem("CBS BAI");
    } else {
      localFMLogRecord.setFromSystem(paramString5);
    }
    if (paramString4 == null) {
      localFMLogRecord.setActivityType("process");
    } else {
      localFMLogRecord.setActivityType(paramString4);
    }
    localFMLogRecord.setLocalizableMessage(paramILocalizable);
    localFMLogRecord.setMillis(System.currentTimeMillis());
    return localFMLogRecord;
  }
  
  private boolean ap(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      return false;
    }
    if ((aTT == null) || (aTT.isEmpty())) {
      return false;
    }
    for (int i = 0; i < aTT.size(); i++)
    {
      FXCurrency localFXCurrency = (FXCurrency)aTT.get(i);
      if (localFXCurrency.getCurrencyCode().equalsIgnoreCase(paramString)) {
        return true;
      }
    }
    return false;
  }
  
  private void jdMethod_for(Account paramAccount, Transactions paramTransactions, String paramString, Connection paramConnection)
    throws DCException, Exception
  {
    if ((paramTransactions == null) || (paramTransactions.isEmpty())) {
      return;
    }
    Transaction localTransaction1 = (Transaction)paramTransactions.get(0);
    DateTime localDateTime = localTransaction1.getProcessingDate();
    GregorianCalendar[] arrayOfGregorianCalendar = jdMethod_for(localDateTime.getLocale(), localDateTime.getTime());
    Transaction localTransaction2 = DCAdapter.getLatestTransactionByAccountDates(paramAccount, arrayOfGregorianCalendar[0], arrayOfGregorianCalendar[1], paramString, paramConnection, new HashMap(1));
    Currency localCurrency = null;
    if (localTransaction2 == null)
    {
      SecureUser localSecureUser = new SecureUser();
      localSecureUser.setBankID("1000");
      BankIdentifier localBankIdentifier = new BankIdentifier(paramAccount.getLocale());
      localBankIdentifier.setBankDirectoryId(paramAccount.getRoutingNum());
      Date localDate = SmartCalendar.getPreviousDay(localSecureUser, localBankIdentifier, localDateTime.getTime(), new HashMap());
      arrayOfGregorianCalendar = jdMethod_for(localDateTime.getLocale(), localDate);
      HashMap localHashMap = new HashMap();
      localHashMap.put("DATA_CLASSIFICATION", "P");
      AccountHistories localAccountHistories = DCAdapter.getHistory(paramAccount, arrayOfGregorianCalendar[0], arrayOfGregorianCalendar[1], paramConnection, localHashMap);
      if ((localAccountHistories != null) && (localAccountHistories.size() > 1)) {
        localAccountHistories = DCUtil.combineAccountHistoriesByDate(localAccountHistories);
      }
      if ((localAccountHistories != null) && (localAccountHistories.size() > 0))
      {
        AccountHistory localAccountHistory = (AccountHistory)localAccountHistories.get(0);
        localCurrency = localAccountHistory.getClosingLedger();
      }
    }
    else
    {
      localCurrency = localTransaction2.getRunningBalanceValue();
    }
    DCUtil.updateTransactionRunningBalances(paramTransactions, localCurrency, true);
  }
  
  private void jdMethod_for(Account paramAccount, AccountHistory paramAccountHistory, Connection paramConnection)
    throws DCException, Exception
  {
    if ((paramAccountHistory == null) || (paramAccountHistory.getClosingLedger() == null)) {
      return;
    }
    DateTime localDateTime = paramAccountHistory.getHistoryDate();
    SecureUser localSecureUser = new SecureUser();
    localSecureUser.setBankID("1000");
    BankIdentifier localBankIdentifier = new BankIdentifier(paramAccount.getLocale());
    localBankIdentifier.setBankDirectoryId(paramAccount.getRoutingNum());
    Date localDate = SmartCalendar.getOffsetBankingDay(localSecureUser, localBankIdentifier, localDateTime.getTime(), 1, new HashMap());
    GregorianCalendar[] arrayOfGregorianCalendar = jdMethod_for(localDateTime.getLocale(), localDate);
    HashMap localHashMap = new HashMap();
    localHashMap.put("DATA_CLASSIFICATION", "P");
    Transactions localTransactions = DCAdapter.getTransactions(paramConnection, paramAccount, arrayOfGregorianCalendar[0], arrayOfGregorianCalendar[1], localHashMap);
    localTransactions = DCUtil.updateTransactionRunningBalances(localTransactions, paramAccountHistory.getClosingLedger(), false);
    DCAdapter.updateTransactions(paramAccount, localTransactions, paramConnection, localHashMap);
    localHashMap.clear();
    localHashMap.put("DATA_CLASSIFICATION", "I");
    localTransactions = DCAdapter.getTransactions(paramConnection, paramAccount, arrayOfGregorianCalendar[0], arrayOfGregorianCalendar[1], localHashMap);
    localTransactions = DCUtil.updateTransactionRunningBalances(localTransactions, paramAccountHistory.getClosingLedger(), false);
    DCAdapter.updateTransactions(paramAccount, localTransactions, paramConnection, localHashMap);
  }
  
  private GregorianCalendar[] jdMethod_for(Locale paramLocale, Date paramDate)
  {
    GregorianCalendar[] arrayOfGregorianCalendar = new GregorianCalendar[2];
    GregorianCalendar localGregorianCalendar1 = new GregorianCalendar(paramLocale);
    localGregorianCalendar1.setTime(paramDate);
    localGregorianCalendar1.set(11, 0);
    localGregorianCalendar1.set(12, 0);
    localGregorianCalendar1.set(13, 0);
    localGregorianCalendar1.set(14, 0);
    GregorianCalendar localGregorianCalendar2 = (GregorianCalendar)localGregorianCalendar1.clone();
    localGregorianCalendar2.set(11, 23);
    localGregorianCalendar2.set(12, 59);
    localGregorianCalendar2.set(13, 59);
    localGregorianCalendar2.set(14, 999);
    arrayOfGregorianCalendar[0] = localGregorianCalendar1;
    arrayOfGregorianCalendar[1] = localGregorianCalendar2;
    return arrayOfGregorianCalendar;
  }
  
  private ArrayList jdMethod_int(Account paramAccount)
    throws Exception
  {
    String str = paramAccount.getRoutingNum();
    SecureUser localSecureUser = new SecureUser();
    HashMap localHashMap = new HashMap();
    ArrayList localArrayList1 = (ArrayList)this.aTS.get(str);
    if (localArrayList1 == null)
    {
      localObject1 = null;
      try
      {
        localObject1 = AffiliateBankAdmin.getAffiliateBankByRoutingNumber(localSecureUser, paramAccount.getRoutingNum(), localHashMap);
      }
      catch (Exception localException)
      {
        DebugLog.throwing("Unable to retrieve bank information for the bank identified by routing number '" + paramAccount.getRoutingNum() + "'.  No calculations will be performed for accounts " + "belonging to this bank.", localException);
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = Integer.toString(this.aTP._lineNumber);
        arrayOfObject[1] = paramAccount.getRoutingNum();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bai", "AuditMessage_23", arrayOfObject);
        FMLogRecord localFMLogRecord = jdMethod_for("BAI", this.aUC, this.aUb, this.aTz, this.aT8, this.aTR, localLocalizableString);
        localFMLogRecord.setStatus("Warning");
        try
        {
          FMLog.log(localFMLogRecord);
        }
        catch (FMException localFMException)
        {
          DebugLog.throwing("BAIAdapter.getRestrictedCalculations", localFMException);
        }
        finally
        {
          arrayOfObject = null;
          localLocalizableString = null;
        }
        if (!this.aTW.contains(paramAccount.getRoutingNum())) {
          this.aTW.add(paramAccount.getRoutingNum());
        }
        return null;
      }
      localArrayList1 = AffiliateBankAdmin.getRestrictedCalculations(localSecureUser, ((AffiliateBank)localObject1).getAffiliateBankID(), localHashMap);
      if (localArrayList1 != null) {
        this.aTS.put(str, localArrayList1);
      }
    }
    Object localObject1 = AccountHandler.getRestrictedCalculations(localSecureUser, paramAccount, localHashMap);
    ArrayList localArrayList2 = DCUtil.mergeBankAndAccountRestrictions(localArrayList1, (ArrayList)localObject1);
    return localArrayList2;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.BAIAdapter
 * JD-Core Version:    0.7.0.1
 */