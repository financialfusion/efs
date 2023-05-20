package com.ffusion.fileimporter.fileadapters;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.positivepay.PPayCheckRecord;
import com.ffusion.beans.positivepay.PPayIssue;
import com.ffusion.beans.positivepay.PPayIssues;
import com.ffusion.csil.FIException;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.msgbroker.factory.MBInstanceFactory;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.PositivePayIssues.TypeRecord;
import com.ffusion.msgbroker.interfaces.IMBInstance;
import com.ffusion.msgbroker.interfaces.IMBMessage;
import com.ffusion.msgbroker.interfaces.IParser;
import com.ffusion.msgbroker.interfaces.MBException;
import com.ffusion.positivepay.PPayException;
import com.ffusion.positivepay.adapter.IPPayAdapter;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMLogRecord;
import com.ffusion.util.filemonitor.FMLog;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class PPayIssuesFileAdapter
  implements IFileAdapter
{
  private String aVk = null;
  private String aVj = null;
  private String aVd = null;
  private boolean aU6;
  private String aVg = null;
  private String aUX = null;
  private String aU9 = null;
  private String aVe = null;
  private String aVf = null;
  private String aVa = null;
  private String aVh = null;
  private String aVb = null;
  private IMBInstance aVc = null;
  private IPPayAdapter aVi = null;
  private static final String aUZ = "com.ffusion.util.logging.audit.pp";
  private static final String aU8 = "IssueLogMessage_1";
  private static final String aU7 = "IssueLogMessage_2";
  private static final String aU5 = "IssueLogMessage_3";
  private static final String aU4 = "IssueLogMessage_4";
  private static final String aU3 = "IssueLogMessage_5";
  private static final String aU2 = "IssueLogMessage_6";
  private static final String aU1 = "IssueLogMessage_7";
  private static final String aU0 = "IssueLogMessage_8";
  private static final String aUY = "IssueLogMessage_9";
  
  public synchronized void initialize(HashMap paramHashMap)
    throws FIException
  {
    String str1 = "PPayIssuesFileAdapter : initialize";
    HashMap localHashMap1 = null;
    HashMap localHashMap2 = null;
    HashMap localHashMap3 = null;
    try
    {
      localHashMap1 = (HashMap)paramHashMap.get("MESSAGEBROKER");
      localHashMap2 = (HashMap)paramHashMap.get("PPAYADAPTER");
      localHashMap3 = (HashMap)localHashMap2.get("PPAYADAPTER_SETTINGS");
    }
    catch (Exception localException1)
    {
      FIException localFIException1 = new FIException(str1, 9610, "Performed a get on a null HashMap", localException1);
      DebugLog.throwing("Performed a get on a null HashMap", localFIException1);
      throw localFIException1;
    }
    if ((localHashMap1 == null) || (localHashMap3 == null))
    {
      DebugLog.log("Settings HashMaps cannot be null. ");
      throw new FIException(str1, 9610, "Settings HashMaps cannot be null");
    }
    int i = 0;
    this.aVe = ((String)localHashMap1.get("USERID"));
    this.aVf = ((String)localHashMap1.get("PASSWORD"));
    this.aVd = ((String)localHashMap1.get("DBTYPE"));
    this.aVk = ((String)localHashMap1.get("MESSAGESET"));
    this.aVj = ((String)localHashMap1.get("MESSAGENAME"));
    if ((this.aVe == null) || (this.aVf == null) || (this.aVd == null) || (this.aVk == null) || (this.aVj == null)) {
      i = 1;
    }
    int j = 0;
    String str2 = null;
    if (localHashMap1.get("CTXFACTORY") != null)
    {
      j = 1;
      this.aVa = ((String)localHashMap1.get("CTXFACTORY"));
      this.aVh = ((String)localHashMap1.get("JNDIURL"));
      this.aVb = ((String)localHashMap1.get("JNDICTX"));
      if ((this.aVh == null) || (this.aVb == null)) {
        i = 1;
      }
    }
    else
    {
      this.aU9 = ((String)localHashMap1.get("DBNAME"));
      if (this.aU9 == null) {
        i = 1;
      }
      if ((this.aVd != null) && (!this.aVd.equals("DB2:AS390")))
      {
        str2 = (String)localHashMap1.get("USETHINDRIVER");
        this.aVg = ((String)localHashMap1.get("MACHINE"));
        this.aUX = ((String)localHashMap1.get("PORTNUM"));
        if ((str2 == null) || (this.aVg == null) || (this.aUX == null)) {
          i = 1;
        }
      }
    }
    if (i != 0)
    {
      DebugLog.log("All connection fields required to connect to the database being used must be non-null");
      throw new FIException(str1, 9610, "All necessary MB connection params are required");
    }
    if ((str2 != null) && (str2.equalsIgnoreCase("true"))) {
      this.aU6 = true;
    } else {
      this.aU6 = false;
    }
    try
    {
      if ((this.aVd.equalsIgnoreCase("ASA")) || (this.aVd.equalsIgnoreCase("ASE"))) {
        if (j != 0) {
          this.aVc = MBInstanceFactory.createHAInstanceFromJConnect(this.aVe, this.aVf, this.aVa, this.aVh, this.aVb);
        } else {
          this.aVc = MBInstanceFactory.createInstanceFromJConnect(this.aVe, this.aVf, this.aVg, this.aUX, this.aU9);
        }
      }
      if ((this.aVd.equalsIgnoreCase("ORACLE:OCI8")) || (this.aVd.equalsIgnoreCase("ORACLE:THIN"))) {
        this.aVc = MBInstanceFactory.createInstanceFromOracle(this.aVe, this.aVf, this.aVg, this.aUX, this.aU9, this.aU6);
      }
      if (this.aVd.equalsIgnoreCase("DB2:NET")) {
        this.aVc = MBInstanceFactory.createInstanceFromDB2(this.aVe, this.aVf, this.aVg, this.aUX, this.aU9, false);
      }
      if (this.aVd.equalsIgnoreCase("DB2:APP")) {
        this.aVc = MBInstanceFactory.createInstanceFromDB2(this.aVe, this.aVf, this.aVg, this.aUX, this.aU9, true);
      }
      if (this.aVd.equalsIgnoreCase("DB2:UN2")) {
        this.aVc = MBInstanceFactory.createInstanceFromDB2UniversalDriver(this.aVe, this.aVf, this.aU9);
      }
      if (this.aVd.equalsIgnoreCase("DB2:AS390")) {
        this.aVc = MBInstanceFactory.createInstanceFromDB2390(this.aVe, this.aVf, this.aU9);
      }
      if (this.aVd.equalsIgnoreCase("MSSQL:THIN")) {
        this.aVc = MBInstanceFactory.createInstanceFromMSSQL(this.aVe, this.aVf, this.aVg, this.aUX, this.aU9);
      }
    }
    catch (MBException localMBException1)
    {
      localObject = new FIException(9600, localMBException1);
      DebugLog.throwing("Could not connect to DB", (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    if (this.aVc == null)
    {
      DebugLog.log("The MBInstanceFactory returned a null instance,or failed due to an unsupported db type");
      throw new FIException(str1, 9601, "MBInstanceFactory returned a null instance,or failed due to an unsupported db type");
    }
    try
    {
      this.aVc.loadMessageSet(this.aVk);
    }
    catch (MBException localMBException2)
    {
      localObject = new FIException(str1, 9605, "Could not load MB message set", localMBException2);
      DebugLog.throwing("Could not load the MB message set", (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    String str3 = (String)localHashMap2.get("JAVA_CLASS");
    Object localObject = null;
    FIException localFIException2;
    try
    {
      localObject = Class.forName(str3);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localFIException2 = new FIException(9606, localClassNotFoundException);
      DebugLog.throwing("Could not load the specified JAVA_CLASS", localFIException2);
      throw localFIException2;
    }
    try
    {
      this.aVi = ((IPPayAdapter)((Class)localObject).newInstance());
    }
    catch (Exception localException2)
    {
      localFIException2 = new FIException(9607, localException2);
      DebugLog.throwing("Specified JAVA_CLASS could not be instantiated. This could be due to either a ClassCastException where the class is not of type IPPayAdapter, or an instantiation or access exception.", localFIException2);
      throw localFIException2;
    }
    try
    {
      this.aVi.initialize(localHashMap3);
    }
    catch (PPayException localPPayException)
    {
      localFIException2 = new FIException(str1, 9611, "Could not initialize the PPayAdapter", localPPayException);
      DebugLog.throwing("Could not initialize the PPayAdapter", localFIException2);
      throw localFIException2;
    }
  }
  
  public void open(HashMap paramHashMap)
    throws FIException
  {}
  
  public void close(HashMap paramHashMap)
    throws FIException
  {}
  
  public void processFile(InputStream paramInputStream, HashMap paramHashMap)
    throws FIException
  {
    String str1 = "PPayIssuesFileAdapter : processFile";
    FMLogRecord localFMLogRecord1 = jdMethod_case("Positive Pay Check Record", paramHashMap);
    localFMLogRecord1.setStatus("In Process");
    try
    {
      FMLog.log(localFMLogRecord1);
    }
    catch (FMException localFMException1)
    {
      DebugLog.throwing(str1, localFMException1);
    }
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    String str2 = null;
    StringBuffer localStringBuffer = null;
    try
    {
      str2 = localBufferedReader.readLine();
      localStringBuffer = new StringBuffer();
      while (str2 != null)
      {
        localStringBuffer.append(str2);
        localStringBuffer.append("\r\n");
        str2 = localBufferedReader.readLine();
      }
    }
    catch (IOException localIOException)
    {
      localFMLogRecord1 = jdMethod_case("Positive Pay Check Record", paramHashMap);
      localFMLogRecord1.setStatus("Failed");
      localObject1 = new LocalizableString("com.ffusion.util.logging.audit.pp", "IssueLogMessage_1", null);
      localFMLogRecord1.setLocalizableMessage((ILocalizable)localObject1);
      try
      {
        FMLog.log(localFMLogRecord1);
      }
      catch (FMException localFMException2)
      {
        DebugLog.throwing(str1, localFMException2);
      }
      finally
      {
        localObject1 = null;
      }
      DebugLog.throwing("InputStream readLine failed", localIOException);
      throw new FIException(str1, 9608, "InputStream readLine failed.");
    }
    String str3 = localStringBuffer.toString();
    if (this.aVc == null)
    {
      DebugLog.log("MB Instance is null. Cannot proceed with parse.");
      throw new FIException(str1, 9603, "MB Instance is null, cannot proceed.");
    }
    Object localObject1 = null;
    Object localObject4;
    try
    {
      localObject1 = this.aVc.createParser();
    }
    catch (MBException localMBException1)
    {
      localFMLogRecord1 = jdMethod_case("Positive Pay Check Record", paramHashMap);
      localFMLogRecord1.setStatus("Failed");
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.pp", "IssueLogMessage_2", null);
      localFMLogRecord1.setLocalizableMessage(localLocalizableString1);
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
        localLocalizableString1 = null;
      }
      localObject4 = new FIException(9603, localMBException1);
      DebugLog.throwing("Unable to create a MB issue file parser", (Throwable)localObject4);
      throw ((Throwable)localObject4);
    }
    if (localObject1 == null)
    {
      DebugLog.log("MB parser used to parse issue file is null");
      throw new FIException(str1, 9603, "createParser returned null");
    }
    IMBMessage localIMBMessage = null;
    try
    {
      localIMBMessage = ((IParser)localObject1).parseToIDL(str3, this.aVk, this.aVj);
    }
    catch (MBException localMBException2)
    {
      localFMLogRecord1 = jdMethod_case("Positive Pay Check Record", paramHashMap);
      localFMLogRecord1.setStatus("Failed");
      localObject4 = new LocalizableString("com.ffusion.util.logging.audit.pp", "IssueLogMessage_3", null);
      localFMLogRecord1.setLocalizableMessage((ILocalizable)localObject4);
      try
      {
        FMLog.log(localFMLogRecord1);
      }
      catch (FMException localFMException5)
      {
        DebugLog.throwing(str1, localFMException5);
      }
      finally
      {
        localObject4 = null;
      }
      localObject6 = new FIException(9604, localMBException2);
      DebugLog.throwing("Unable to parse MBMessage", (Throwable)localObject6);
      throw ((Throwable)localObject6);
    }
    if (localIMBMessage == null)
    {
      localFMLogRecord1 = jdMethod_case("Positive Pay Check Record", paramHashMap);
      localFMLogRecord1.setStatus("Failed");
      localObject3 = new LocalizableString("com.ffusion.util.logging.audit.pp", "IssueLogMessage_4", null);
      localFMLogRecord1.setLocalizableMessage((ILocalizable)localObject3);
      try
      {
        FMLog.log(localFMLogRecord1);
      }
      catch (FMException localFMException4)
      {
        DebugLog.throwing(str1, localFMException4);
      }
      finally
      {
        localObject3 = null;
      }
      DebugLog.log("MB parseToIDL returned null");
      throw new FIException(str1, 9604, "MB parseToIDL returned null");
    }
    Object localObject3 = null;
    try
    {
      localObject3 = (TypeRecord[])localIMBMessage.getField("Record");
    }
    catch (MBException localMBException3)
    {
      localFMLogRecord1 = jdMethod_case("Positive Pay Check Record", paramHashMap);
      localFMLogRecord1.setStatus("Failed");
      localObject6 = new LocalizableString("com.ffusion.util.logging.audit.pp", "IssueLogMessage_5", null);
      localFMLogRecord1.setLocalizableMessage((ILocalizable)localObject6);
      try
      {
        FMLog.log(localFMLogRecord1);
      }
      catch (FMException localFMException6)
      {
        DebugLog.throwing(str1, localFMException6);
      }
      finally
      {
        localObject6 = null;
      }
      localObject8 = new FIException(str1, 9608, "Could not getField on MBMessage", localMBException3);
      DebugLog.throwing("Could not getField on MBMessage. Unable to retrieve Records", (Throwable)localObject8);
      throw ((Throwable)localObject8);
    }
    Locale localLocale = Locale.getDefault();
    Object localObject6 = new PPayIssues(localLocale);
    Object localObject8 = DateFormatUtil.getFormatter("yyyyMMdd");
    Date localDate1 = new Date(System.currentTimeMillis());
    Date localDate2 = null;
    String str4 = null;
    String str5 = null;
    BigDecimal localBigDecimal = null;
    Currency localCurrency = new Currency();
    localCurrency.setLocale(localLocale);
    String str6 = "Fusion Bank";
    String str7 = "660110110";
    for (int i = 0; i < localObject3.length; i++)
    {
      PPayIssue localPPayIssue = new PPayIssue(((PPayIssues)localObject6).getLocale());
      PPayCheckRecord localPPayCheckRecord = new PPayCheckRecord(localPPayIssue.getLocale());
      Account localAccount = null;
      Object localObject12;
      try
      {
        if (localObject3[i].accountNumber == null)
        {
          DebugLog.log(getClass().getName() + " : The account number is missing.");
          localObject11 = new Object[1];
          localObject11[0] = Integer.toString(i + 1);
          localObject12 = new LocalizableString("com.ffusion.util.logging.audit.pp", "IssueLogMessage_6", (Object[])localObject11);
          localFMLogRecord1 = jdMethod_case("Positive Pay Check Record", paramHashMap);
          localFMLogRecord1.setLocalizableMessage((ILocalizable)localObject12);
          localFMLogRecord1.setStatus("Error");
          try
          {
            FMLog.log(localFMLogRecord1);
          }
          catch (FMException localFMException9)
          {
            DebugLog.throwing(str1, localFMException9);
          }
          finally
          {
            localObject11 = null;
            localObject12 = null;
          }
          continue;
        }
        Object localObject11 = AccountAdapter.getAccountsByAccountNumber(localObject3[i].bankID, str7, localObject3[i].accountNumber);
        if ((localObject11 != null) && (((Accounts)localObject11).size() > 0))
        {
          if (((Accounts)localObject11).size() > 1)
          {
            DebugLog.log(getClass().getName() + " : More than one account found with account number = " + localObject3[i].accountNumber);
            localObject12 = new Object[4];
            localObject12[0] = Integer.toString(i + 1);
            localObject12[1] = localObject3[i].accountNumber;
            localObject12[2] = localObject3[i].bankID;
            localObject12[3] = str7;
            LocalizableString localLocalizableString3 = new LocalizableString("com.ffusion.util.logging.audit.pp", "IssueLogMessage_7", (Object[])localObject12);
            localFMLogRecord1 = jdMethod_case("Positive Pay Check Record", paramHashMap);
            localFMLogRecord1.setLocalizableMessage(localLocalizableString3);
            localFMLogRecord1.setStatus("Error");
            try
            {
              FMLog.log(localFMLogRecord1);
            }
            catch (FMException localFMException11)
            {
              DebugLog.throwing(str1, localFMException11);
            }
            finally
            {
              localObject12 = null;
              localLocalizableString3 = null;
            }
            continue;
          }
          localAccount = (Account)((Accounts)localObject11).get(0);
        }
      }
      catch (Exception localException)
      {
        localAccount = null;
      }
      if (localAccount == null)
      {
        DebugLog.log(getClass().getName() + " : No account found with account number = " + localObject3[i].accountNumber);
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = Integer.toString(i + 1);
        arrayOfObject[1] = localObject3[i].accountNumber;
        arrayOfObject[2] = localObject3[i].bankID;
        arrayOfObject[3] = str7;
        localObject12 = new LocalizableString("com.ffusion.util.logging.audit.pp", "IssueLogMessage_8", arrayOfObject);
        localFMLogRecord1 = jdMethod_case("Positive Pay Check Record", paramHashMap);
        localFMLogRecord1.setLocalizableMessage((ILocalizable)localObject12);
        localFMLogRecord1.setStatus("Error");
        try
        {
          FMLog.log(localFMLogRecord1);
        }
        catch (FMException localFMException10)
        {
          DebugLog.throwing(str1, localFMException10);
        }
        finally
        {
          arrayOfObject = null;
          localObject12 = null;
        }
      }
      else
      {
        localPPayCheckRecord.setAccountID(localAccount.getID());
        localPPayCheckRecord.setBankID(localObject3[i].bankID);
        localPPayCheckRecord.setBankName(str6);
        localPPayCheckRecord.setRoutingNumber(str7);
        localPPayCheckRecord.setCheckNumber(localObject3[i].checkNumber);
        if (localObject3[i].amount % 100L < 10L) {
          str4 = "0" + Long.toString(localObject3[i].amount % 100L);
        } else {
          str4 = Long.toString(localObject3[i].amount % 100L);
        }
        str5 = Long.toString(localObject3[i].amount / 100L) + "." + str4;
        localBigDecimal = new BigDecimal(str5);
        localCurrency.setAmount(localBigDecimal);
        localPPayCheckRecord.setAmount(localCurrency);
        localPPayCheckRecord.setAdditionalData(localObject3[i].additionalData);
        if (localObject3[i].voidCheck.equals("V")) {
          localPPayCheckRecord.setVoidCheck(true);
        } else {
          localPPayCheckRecord.setVoidCheck(false);
        }
        localDate2 = ((DateFormat)localObject8).parse(localObject3[i].checkDate, new ParsePosition(0));
        localPPayCheckRecord.setCheckDate(new DateTime(localDate2, localLocale));
        localPPayIssue.setCheckRecord(localPPayCheckRecord);
        localPPayIssue.setRejectReason(localObject3[i].rejectReason);
        localPPayIssue.setIssueDate(new DateTime(localDate1, localLocale));
        ((PPayIssues)localObject6).add(localPPayIssue);
      }
    }
    try
    {
      FMLogRecord localFMLogRecord2 = jdMethod_case("Positive Pay Check Record", paramHashMap);
      paramHashMap.put("FM_LOG_RECORD_DATA", localFMLogRecord2);
      this.aVi.insertIssues((PPayIssues)localObject6, paramHashMap);
      localFMLogRecord1 = jdMethod_case("Positive Pay Check Record", paramHashMap);
      localFMLogRecord1.setStatus("Complete");
      try
      {
        FMLog.log(localFMLogRecord1);
      }
      catch (FMException localFMException7)
      {
        DebugLog.throwing(str1, localFMException7);
      }
    }
    catch (PPayException localPPayException)
    {
      localFMLogRecord1 = jdMethod_case("Positive Pay Check Record", paramHashMap);
      localFMLogRecord1.setStatus("Failed");
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.pp", "IssueLogMessage_9", null);
      localFMLogRecord1.setLocalizableMessage(localLocalizableString2);
      try
      {
        FMLog.log(localFMLogRecord1);
      }
      catch (FMException localFMException8)
      {
        DebugLog.throwing(str1, localFMException8);
      }
      finally
      {
        localLocalizableString2 = null;
      }
      FIException localFIException = new FIException(str1, 9609, "Insert issue into adapter failed", localPPayException);
      DebugLog.throwing("Could not issert issue into PPayAdapter.", localFIException);
      throw localFIException;
    }
  }
  
  private static FMLogRecord jdMethod_case(String paramString, HashMap paramHashMap)
  {
    String str1 = (String)paramHashMap.get("FILE_NAME");
    String str2 = (String)paramHashMap.get("FROM_SYSTEM");
    String str3 = (String)paramHashMap.get("TO_SYSTEM");
    String str4 = (String)paramHashMap.get("HOST_NAME");
    String str5 = (String)paramHashMap.get("ACTIVITY_TYPE");
    FMLogRecord localFMLogRecord = new FMLogRecord();
    localFMLogRecord.setFileType(paramString);
    localFMLogRecord.setFileName(str1);
    localFMLogRecord.setHostName(str4);
    localFMLogRecord.setFromSystem(str2);
    if (str3 == null) {
      localFMLogRecord.setToSystem("CBS Positive Pay");
    } else {
      localFMLogRecord.setToSystem(str3);
    }
    if (str5 == null) {
      localFMLogRecord.setActivityType("Process");
    } else {
      localFMLogRecord.setActivityType(str5);
    }
    try
    {
      ILocalizable localILocalizable = (ILocalizable)paramHashMap.get("COMMENT");
      localFMLogRecord.setLocalizableMessage(localILocalizable);
    }
    catch (ClassCastException localClassCastException)
    {
      String str6 = (String)paramHashMap.get("COMMENT");
      localFMLogRecord.setMessage(str6);
    }
    catch (NullPointerException localNullPointerException) {}
    localFMLogRecord.setMillis(System.currentTimeMillis());
    return localFMLogRecord;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.PPayIssuesFileAdapter
 * JD-Core Version:    0.7.0.1
 */