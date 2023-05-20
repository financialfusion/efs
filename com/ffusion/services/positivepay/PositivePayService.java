package com.ffusion.services.positivepay;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.CutOffDefinition;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.ValidationError;
import com.ffusion.beans.positivepay.PPayAccount;
import com.ffusion.beans.positivepay.PPayAccounts;
import com.ffusion.beans.positivepay.PPayCheckRecord;
import com.ffusion.beans.positivepay.PPayCheckRecords;
import com.ffusion.beans.positivepay.PPayDecision;
import com.ffusion.beans.positivepay.PPayDecisions;
import com.ffusion.beans.positivepay.PPayIssue;
import com.ffusion.beans.positivepay.PPayIssues;
import com.ffusion.beans.positivepay.PPaySummaries;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.systemadmin.DRKey;
import com.ffusion.beans.systemadmin.DRSetting;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.FIException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Reporting;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.csil.handlers.ACH;
import com.ffusion.csil.handlers.PositivePayHandler;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.efs.adapters.profile.AffiliateBankAdapter;
import com.ffusion.efs.adapters.profile.BusinessAdapter;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.msgbroker.factory.MBInstanceFactory;
import com.ffusion.msgbroker.factory.MBMessageFactory;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.PositivePayCheckRecord.TypePositivePayCheckRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.PositivePayDecisions.TypePositivePayDecisions;
import com.ffusion.msgbroker.interfaces.IBuilder;
import com.ffusion.msgbroker.interfaces.IMBInstance;
import com.ffusion.msgbroker.interfaces.IMBMessage;
import com.ffusion.msgbroker.interfaces.MBException;
import com.ffusion.positivepay.PPayException;
import com.ffusion.positivepay.adapter.IPPayAdapter;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.AccountService3;
import com.ffusion.services.PositivePay2;
import com.ffusion.services.fileimporter.FileImporterService;
import com.ffusion.systemadmin.SystemAdminUtil;
import com.ffusion.systemadmin.adapter.SystemAdminAdapter;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.RoutingNumberUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.db.DBCookie;
import com.ffusion.util.logging.DebugLog;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class PositivePayService
  implements PositivePay2
{
  public static final String SERVICE_INIT_XML = "positivepay.xml";
  public static final Locale currentLocale = ;
  public static final String PPAY_ADAPTER_KEY = "PPAYADAPTER";
  public static final String JAVA_CLASS = "JAVA_CLASS";
  public static final String PPAY_ADAPTER_SETTINGS = "PPAYADAPTER_SETTINGS";
  public static final String PPAY_DECISION_FILE_KEY = "PPAYDECISIONFILE";
  public static final String MESSAGEBROKER_KEY = "MESSAGEBROKER";
  public static final String POSITIVEPAY_CHECK_RECORD_FILE_TYPE = "Positive Pay Check Record";
  public static final String POSITIVEPAY_CHECK_RECORD_FILE_NAME = "ManualCheckRecords";
  public static final String ACCOUNTS_FOR_REPORTS = "Accounts";
  private String c = null;
  private String jdField_long = null;
  private String jdField_null = null;
  private String jdField_else = null;
  public IPPayAdapter ppayAdapter = null;
  private String b = null;
  private HashMap jdField_goto = null;
  private IMBInstance jdField_char = null;
  private String jdField_case = null;
  private FileImporterService jdField_void = null;
  
  public void initialize()
    throws PPayException
  {
    String str = "PositivePayService.initialize";
    HashMap localHashMap1 = a();
    this.b = ((String)localHashMap1.get("PPAYDECISIONFILE"));
    if (this.b == null)
    {
      DebugLog.log("The <PPAYDECISIONFILE> tag was not found in the XML configuration file.");
      throw new PPayException(str, 10, "The <PPAYDECISIONFILE> tag was not found in the XML configuration file.");
    }
    try
    {
      File localFile = new File(this.b);
      if (localFile.exists())
      {
        if (!localFile.isDirectory()) {
          throw new PPayException("Positive pay initialization", 22, "Not a directory");
        }
      }
      else {
        localFile.mkdirs();
      }
    }
    catch (SecurityException localSecurityException) {}
    HashMap localHashMap2 = (HashMap)localHashMap1.get("PPAYADAPTER");
    if (localHashMap2 == null)
    {
      DebugLog.log("The <PPAYADAPTER> tag was not found in the XML configuration file.");
      throw new PPayException(str, 10, "The <PPAYADAPTER> tag was not found in the XML configuration file.");
    }
    a(localHashMap2);
    this.jdField_goto = ((HashMap)localHashMap1.get("MESSAGEBROKER"));
    if (this.jdField_goto == null)
    {
      DebugLog.log("The <MESSAGEBROKER> tag was not found in the XML configuration file.");
      throw new PPayException(str, 10, "The <MESSAGEBROKER> tag was not found in the XML configuration file.");
    }
    this.jdField_char = jdMethod_if(this.jdField_goto);
    this.jdField_void = FileImporterService.getInstance();
  }
  
  public PPayAccounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PositivePayService.getAccounts";
    a(str1);
    AccountService3 localAccountService3 = (AccountService3)paramHashMap.get("SERVICE");
    if (localAccountService3 == null) {
      throw new PPayException(str1, 20, "An accounts service must be in the extra hashmap before the call to " + str1 + " is made.");
    }
    Accounts localAccounts = null;
    try
    {
      localAccounts = localAccountService3.getAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      localObject = "The accounts requested for reporting could not be retrieved.  The error  received from the back end was " + localProfileException;
      DebugLog.log((String)localObject);
      throw new PPayException(21, localProfileException);
    }
    PPayAccounts localPPayAccounts = new PPayAccounts(currentLocale);
    Object localObject = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    for (int i = 0; i < localAccounts.size(); i++)
    {
      Account localAccount = (Account)localAccounts.get(i);
      String str2 = EntitlementsUtil.getEntitlementObjectId(localAccount);
      Entitlement localEntitlement = new Entitlement();
      localEntitlement.setOperationName("Positive Pay");
      localEntitlement.setObjectType("Account");
      localEntitlement.setObjectId(str2);
      try
      {
        if ((localAccount.getPositivePay() != null) && (localAccount.getPositivePay().equals("1")) && (Entitlements.checkEntitlement((EntitlementGroupMember)localObject, localEntitlement)))
        {
          PPayAccount localPPayAccount = localPPayAccounts.add();
          a(localPPayAccount, localAccount);
        }
      }
      catch (CSILException localCSILException)
      {
        String str3 = "An entitlements check on the set of retrieved accounts could not be completed. The error received from the entitlements system is " + localCSILException;
        DebugLog.log(str3);
        throw new PPayException(27, localCSILException);
      }
    }
    return localPPayAccounts;
  }
  
  public PPaySummaries getSummaries(PPayAccounts paramPPayAccounts, HashMap paramHashMap)
    throws PPayException
  {
    String str = "PositivePayService.getSummaries";
    a(str);
    return this.ppayAdapter.getSummaries(paramPPayAccounts, paramHashMap);
  }
  
  public int getNumIssues(PPayAccounts paramPPayAccounts, HashMap paramHashMap)
    throws PPayException
  {
    String str = "PositivePayService.getNumIssues";
    a(str);
    return this.ppayAdapter.getNumIssues(paramPPayAccounts, paramHashMap);
  }
  
  public PPayIssues getIssuesForAccount(PPayAccount paramPPayAccount, HashMap paramHashMap)
    throws PPayException
  {
    String str = "PositivePayService.getIssuesForAccount";
    a(str);
    return this.ppayAdapter.getIssuesForAccount(paramPPayAccount, paramHashMap);
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PositivePayService.getReportData";
    a(str1);
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    if (localProperties1 == null)
    {
      str2 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
      DebugLog.log(str2);
      throw new PPayException(str1, 5, str2);
    }
    String str2 = (String)localProperties1.get("Account");
    if (str2 == null)
    {
      localObject1 = "An account search criteria should be specified within the search criteria. This search criteria is used to indicate which accounts should be included in this report. Since this criteria was not found, the default value of 'All' is being applied.";
      DebugLog.log((String)localObject1);
      str2 = "All";
    }
    Object localObject1 = null;
    if (str2.indexOf("All") != -1)
    {
      localObject1 = (PPayAccounts)paramHashMap.get("accounts");
    }
    else
    {
      localObject2 = new StringTokenizer(str2, ",");
      int i = ((StringTokenizer)localObject2).countTokens();
      if (i < 1)
      {
        localObject3 = "The account list is not properly delimited.";
        DebugLog.log((String)localObject3);
        throw new PPayException(str1, 5, (String)localObject3);
      }
      Object localObject3 = (AccountService3)paramHashMap.get("SERVICE");
      if (localObject3 == null)
      {
        localObject4 = "An accounts service must be in the extra hashmap before the call to " + str1 + " is made.";
        DebugLog.log((String)localObject4);
        throw new PPayException(str1, 20, (String)localObject4);
      }
      localObject4 = null;
      try
      {
        localObject4 = ((AccountService3)localObject3).getAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
      }
      catch (ProfileException localProfileException)
      {
        localObject6 = "The account requested for reporting could not be retrieved.  The error  received from the back end was " + localProfileException;
        DebugLog.log((String)localObject6);
        throw new PPayException(21, localProfileException);
      }
      localObject1 = new PPayAccounts(currentLocale);
      for (int k = 0; k < i; k++)
      {
        localObject6 = ((StringTokenizer)localObject2).nextToken();
        StringTokenizer localStringTokenizer = new StringTokenizer((String)localObject6, ":");
        int m = localStringTokenizer.countTokens();
        if (m < 2)
        {
          str4 = "The account id and bank ID were not correctly passed in as a report criteria. The proper format is the account number folled by the delimiter ' :' and then the bank ID.";
          DebugLog.log(str4);
          throw new PPayException(str1, 5, str4);
        }
        String str4 = localStringTokenizer.nextToken();
        String str5 = localStringTokenizer.nextToken();
        String str6 = null;
        if (m > 2) {
          str6 = localStringTokenizer.nextToken();
        }
        for (k = 0; k < ((Accounts)localObject4).size(); k++)
        {
          Account localAccount = (Account)((Accounts)localObject4).get(k);
          PPayAccount localPPayAccount;
          if (str6 == null)
          {
            if ((localAccount.getID().equals(str4)) && (localAccount.getBankID().equals(str5)))
            {
              localPPayAccount = ((PPayAccounts)localObject1).add();
              a(localPPayAccount, localAccount);
            }
          }
          else if ((localAccount.getID().equals(str4)) && (localAccount.getRoutingNum().equals(str6)) && (localAccount.getBankID().equals(str5)))
          {
            localPPayAccount = ((PPayAccounts)localObject1).add();
            a(localPPayAccount, localAccount);
          }
        }
      }
    }
    Object localObject2 = new HashMap();
    ((HashMap)localObject2).put("Accounts", localObject1);
    ((HashMap)localObject2).putAll(paramHashMap);
    Accounts localAccounts = new Accounts();
    for (int j = 0; j < ((PPayAccounts)localObject1).size(); j++)
    {
      localObject4 = (PPayAccount)((PPayAccounts)localObject1).get(j);
      localObject5 = localAccounts.create("");
      a((Account)localObject5, (PPayAccount)localObject4);
    }
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    Object localObject4 = localProperties2.getProperty("REPORTTYPE");
    String str3;
    if ((((String)localObject4).equals("Positive Pay Exceptions Report")) || (((String)localObject4).equals("Positive Pay Decisions Report")))
    {
      localObject5 = new HashMap();
      localObject6 = new HashMap();
      try
      {
        Reporting.calculateDateRange(paramSecureUser, null, paramReportCriteria, (HashMap)localObject5, (HashMap)localObject6, paramHashMap);
        a(localAccounts, (HashMap)localObject5, (HashMap)localObject6, paramReportCriteria);
        ((HashMap)localObject2).put("StartDates", localObject5);
        ((HashMap)localObject2).put("EndDates", localObject6);
      }
      catch (CSILException localCSILException1)
      {
        str3 = "Unable to calculate the date ranges. A report cannot be run.";
        DebugLog.log(str3);
        if (localCSILException1.getCode() == -1009) {
          throw new PPayException(str1, localCSILException1.getServiceError(), str3);
        }
        throw new PPayException(str1, localCSILException1.getCode(), str3);
      }
      return this.ppayAdapter.getBCReportData(paramReportCriteria, paramHashMap);
    }
    Object localObject5 = new HashMap();
    Object localObject6 = new HashMap();
    try
    {
      Reporting.calculateDateRange(paramSecureUser, localAccounts, paramReportCriteria, (HashMap)localObject5, (HashMap)localObject6, paramHashMap);
      a(localAccounts, (HashMap)localObject5, (HashMap)localObject6, paramReportCriteria);
      ((HashMap)localObject2).put("StartDates", localObject5);
      ((HashMap)localObject2).put("EndDates", localObject6);
    }
    catch (CSILException localCSILException2)
    {
      str3 = "Unable to calcualte the date ranges. A report cannot be run.";
      DebugLog.log(str3);
      if (localCSILException2.getCode() == -1009) {
        throw new PPayException(str1, localCSILException2.getServiceError(), str3);
      }
      throw new PPayException(str1, localCSILException2.getCode(), str3);
    }
    return this.ppayAdapter.getReportData(paramReportCriteria, (HashMap)localObject2);
  }
  
  private void a(Accounts paramAccounts, HashMap paramHashMap1, HashMap paramHashMap2, ReportCriteria paramReportCriteria)
    throws PPayException
  {
    for (int i = 0; i < paramAccounts.size(); i++)
    {
      Account localAccount = (Account)paramAccounts.get(i);
      String str = localAccount.getRoutingNum();
      if (str != null)
      {
        DateTime localDateTime1 = (DateTime)paramHashMap1.get(str);
        DateTime localDateTime2 = (DateTime)paramHashMap2.get(str);
        Object localObject;
        DateTime localDateTime3;
        if ((localDateTime1 == null) && (localDateTime2 == null))
        {
          localObject = new DateTime(Locale.getDefault());
          ((DateTime)localObject).add(5, 1);
          ((DateTime)localObject).add(14, -1);
          localDateTime3 = new DateTime((Calendar)localObject, Locale.getDefault());
          localDateTime3.add(5, -1 * a(paramReportCriteria));
          localDateTime3.set(11, 0);
          localDateTime3.set(12, 0);
          localDateTime3.set(13, 0);
          localDateTime3.set(14, 0);
          paramHashMap1.put(str, localDateTime3);
          paramHashMap2.put(str, localObject);
        }
        else if ((localDateTime1 != null) && (localDateTime2 == null))
        {
          localDateTime3 = new DateTime(localDateTime1, Locale.getDefault());
          localDateTime3.add(5, a(paramReportCriteria));
          localDateTime3.add(5, 1);
          localDateTime3.add(14, -1);
          paramHashMap2.put(str, localDateTime3);
        }
        else if ((localDateTime1 == null) && (localDateTime2 != null))
        {
          localDateTime3 = new DateTime(localDateTime2, Locale.getDefault());
          localDateTime3.add(5, -1 * a(paramReportCriteria));
          localDateTime3.set(11, 0);
          localDateTime3.set(12, 0);
          localDateTime3.set(13, 0);
          localDateTime3.set(14, 0);
          paramHashMap1.put(str, localDateTime3);
        }
        else
        {
          localDateTime3 = new DateTime(localDateTime2, Locale.getDefault());
          localDateTime3.add(5, -1 * a(paramReportCriteria));
          localDateTime3.set(11, 0);
          localDateTime3.set(12, 0);
          localDateTime3.set(13, 0);
          localDateTime3.set(14, 0);
          if (localDateTime1.before(localDateTime3))
          {
            localObject = "The date range for routing number " + str + " is too long - " + "the maximum range allowed is " + a(paramReportCriteria) + " days.";
            PPayException localPPayException = new PPayException("PositivePayService.updateDates", 28, (String)localObject);
            DebugLog.throwing("PositivePayService.updateDates", localPPayException);
            throw localPPayException;
          }
        }
      }
    }
  }
  
  public void submitDecisions(SecureUser paramSecureUser, PPayDecisions paramPPayDecisions, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PositivePayService.submitDecisions";
    a(str1);
    User localUser = new User();
    localUser.setId(Integer.toString(paramSecureUser.getProfileID()));
    try
    {
      localUser = UserAdmin.getUserById(paramSecureUser, localUser, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing("An error occurred while retreiving user information.", localCSILException);
      throw new PPayException(localCSILException, str1, 15, "An error occurred while retreiving user information.");
    }
    if (localUser == null)
    {
      DebugLog.log("The submitting user was not found.");
      throw new PPayException(str1, 26, "The submitting user was not found.");
    }
    for (int i = 0; i < paramPPayDecisions.size(); i++) {
      ((PPayDecision)paramPPayDecisions.get(i)).setSubmittingUserName(localUser.getName());
    }
    String str2 = paramSecureUser.getBPWFIID() + "-" + paramSecureUser.getBusinessCustId() + "-" + paramSecureUser.getUserName();
    Date localDate = new Date(System.currentTimeMillis());
    String str3 = DateFormatUtil.getFormatter("yyyyMMdd'-'HHmmss").format(localDate);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(str2);
    localStringBuffer.append("-");
    localStringBuffer.append(str3);
    a(paramPPayDecisions, localStringBuffer.toString());
    this.ppayAdapter.submitDecisions(paramPPayDecisions, paramHashMap);
  }
  
  private void a(PPayDecisions paramPPayDecisions, String paramString)
    throws PPayException
  {
    String str1 = "PositivePayService.generateDecisionFile";
    com.ffusion.msgbroker.generated.MessageBroker.mdf.PositivePayDecisions.TypeRecord[] arrayOfTypeRecord = new com.ffusion.msgbroker.generated.MessageBroker.mdf.PositivePayDecisions.TypeRecord[paramPPayDecisions.size()];
    int i = 0;
    Object localObject2;
    Object localObject3;
    for (int j = 0; j < paramPPayDecisions.size(); j++)
    {
      localObject1 = (PPayDecision)paramPPayDecisions.get(j);
      localObject2 = ((PPayDecision)localObject1).getCheckRecord();
      if (localObject2 == null)
      {
        DebugLog.log("The check record in a decision cannot be null.");
        throw new PPayException(str1, 5, "The check record in a decision cannot be null.");
      }
      localObject3 = ((PPayCheckRecord)localObject2).getAccountID();
      String str2 = ((PPayCheckRecord)localObject2).getBankID();
      String str3 = ((PPayCheckRecord)localObject2).getCheckNumber();
      DateTime localDateTime = ((PPayCheckRecord)localObject2).getCheckDate();
      Currency localCurrency = ((PPayCheckRecord)localObject2).getAmount();
      String str4 = ((PPayCheckRecord)localObject2).getAdditionalData();
      String str5 = ((PPayDecision)localObject1).getRejectReason();
      String str6 = ((PPayDecision)localObject1).getDecision();
      if ((localObject3 == null) || (str2 == null) || (str3 == null) || (localDateTime == null) || (localCurrency == null) || (str5 == null) || (str6 == null))
      {
        DebugLog.log("All fields must be filled in before a decision may be accepted.  One or more fields was not filled in for the decision detailed below :\n" + ((PPayDecision)localObject1).toXML());
        throw new PPayException(str1, 5, "All fields must be filled in before a decision may be accepted.  One or more fields was not filled in for this decision.");
      }
      if ((str6 == null) || ((!str6.equals("Pay")) && (!str6.equals("Return"))))
      {
        str7 = "The decision submitted for this issue is invalid.  The currently supported decision types are Pay and Return.  The decision submitted was \"" + str6 + "\".";
        DebugLog.log(str7);
        throw new PPayException(str1, 3, str7);
      }
      if (str4 == null) {
        str4 = "";
      }
      String str7 = DateFormatUtil.getFormatter("yyyyMMdd").format(localDateTime.getTime());
      String str8 = null;
      if (((PPayCheckRecord)localObject2).getVoidCheck() == true) {
        str8 = "V";
      } else {
        str8 = " ";
      }
      long l = new Long(localCurrency.getAmountValue().movePointRight(2).toString()).longValue();
      com.ffusion.msgbroker.generated.MessageBroker.mdf.PositivePayDecisions.TypeRecord localTypeRecord = new com.ffusion.msgbroker.generated.MessageBroker.mdf.PositivePayDecisions.TypeRecord((String)localObject3, str2, str3, str7, l, str8, str4, str5, str6);
      arrayOfTypeRecord[i] = localTypeRecord;
      i++;
    }
    TypePositivePayDecisions localTypePositivePayDecisions = new TypePositivePayDecisions(arrayOfTypeRecord);
    Object localObject1 = null;
    try
    {
      localObject2 = MBMessageFactory.createIDLMessage(this.jdField_long, this.c, localTypePositivePayDecisions);
      localObject3 = this.jdField_char.createBuilder();
      localObject1 = ((IBuilder)localObject3).buildString((IMBMessage)localObject2);
    }
    catch (MBException localMBException)
    {
      DebugLog.throwing("A message broker error has occurred while creating the decision output file.", localMBException);
      throw new PPayException(localMBException, str1, 15, "A message broker error has occurred while creating the decision output file.");
    }
    FileWriter localFileWriter = null;
    try
    {
      localObject3 = new File(this.b, paramString);
      localFileWriter = new FileWriter((File)localObject3);
      localFileWriter.write(((String)localObject1).toCharArray());
    }
    catch (IOException localIOException1)
    {
      DebugLog.throwing("Unable to write the decision file to the specified output directory ( " + this.b + " ).", localIOException1);
      throw new PPayException(localIOException1, str1, 12, "Unable to write the decision file to the specified output directory ( " + this.b + " ).");
    }
    finally
    {
      if (localFileWriter != null) {
        try
        {
          localFileWriter.close();
        }
        catch (IOException localIOException2) {}
      }
    }
  }
  
  public void submitCheckRecords(SecureUser paramSecureUser, PPayCheckRecords paramPPayCheckRecords, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PositivePayService.submitCheckRecords";
    a(str1);
    com.ffusion.msgbroker.generated.MessageBroker.mdf.PositivePayCheckRecord.TypeRecord[] arrayOfTypeRecord = new com.ffusion.msgbroker.generated.MessageBroker.mdf.PositivePayCheckRecord.TypeRecord[paramPPayCheckRecords.size()];
    validatePositivePayCheckRecords(paramPPayCheckRecords, paramHashMap);
    int i = 0;
    Object localObject2;
    Object localObject3;
    for (int j = 0; j < paramPPayCheckRecords.size(); j++)
    {
      localObject1 = (PPayCheckRecord)paramPPayCheckRecords.get(j);
      localObject2 = ((PPayCheckRecord)localObject1).getAccountID();
      localObject3 = ((PPayCheckRecord)localObject1).getBankID();
      String str2 = ((PPayCheckRecord)localObject1).getCheckNumber();
      DateTime localDateTime = ((PPayCheckRecord)localObject1).getCheckDate();
      Currency localCurrency = ((PPayCheckRecord)localObject1).getAmount();
      String str3 = ((PPayCheckRecord)localObject1).getAdditionalData();
      if ((localObject2 == null) || (localObject3 == null) || (str2 == null) || (localDateTime == null) || (localCurrency == null))
      {
        DebugLog.log("All fields must be filled in before a check record may be accepted.  One or more fields was not filled in for the check record detailed below :\n" + ((PPayCheckRecord)localObject1).toXML());
        throw new PPayException(str1, 5, "All fields must be filled in before a check record may be accepted.  One or more fields was not filled in for this check record.");
      }
      if (str3 == null) {
        str3 = "";
      }
      String str4 = DateFormatUtil.getFormatter("yyyyMMdd").format(localDateTime.getTime());
      String str5 = null;
      if (((PPayCheckRecord)localObject1).getVoidCheck() == true) {
        str5 = "V";
      } else {
        str5 = " ";
      }
      long l = new Long(localCurrency.getAmountValue().movePointRight(2).toString()).longValue();
      com.ffusion.msgbroker.generated.MessageBroker.mdf.PositivePayCheckRecord.TypeRecord localTypeRecord = new com.ffusion.msgbroker.generated.MessageBroker.mdf.PositivePayCheckRecord.TypeRecord((String)localObject2, (String)localObject3, str2, str4, l, str5, str3);
      arrayOfTypeRecord[i] = localTypeRecord;
      i++;
    }
    TypePositivePayCheckRecord localTypePositivePayCheckRecord = new TypePositivePayCheckRecord(arrayOfTypeRecord);
    Object localObject1 = null;
    try
    {
      localObject2 = MBMessageFactory.createIDLMessage(this.jdField_else, this.jdField_null, localTypePositivePayCheckRecord);
      localObject3 = this.jdField_char.createBuilder();
      localObject1 = ((IBuilder)localObject3).buildString((IMBMessage)localObject2);
    }
    catch (MBException localMBException)
    {
      DebugLog.throwing("A message broker error has occurred while submitting check records.", localMBException);
      throw new PPayException(localMBException, str1, 23, "A message broker error has occurred while submitting check records.");
    }
    try
    {
      this.jdField_void.upload(paramSecureUser, ((String)localObject1).getBytes(), "Positive Pay Check Record", "ManualCheckRecords", paramHashMap);
    }
    catch (FIException localFIException)
    {
      DebugLog.throwing("A problem occured while attempting to upload a positive pay check record through the file importer service.", localFIException);
      throw new PPayException(localFIException, str1, 17, "A problem occured while attempting to upload a positive pay check record through the file importer service.");
    }
  }
  
  public void deleteAccount(PPayAccount paramPPayAccount, HashMap paramHashMap)
    throws PPayException
  {
    String str = "PositivePayService.deleteAccount";
    a(str);
    this.ppayAdapter.deleteAccount(paramPPayAccount, paramHashMap);
  }
  
  public void cleanup(int paramInt, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PositivePayService.cleanup";
    a(str1);
    if (paramInt >= 0)
    {
      this.ppayAdapter.cleanup(paramInt, paramHashMap);
    }
    else
    {
      HashMap localHashMap1 = null;
      HashMap localHashMap2 = null;
      HashMap localHashMap3 = null;
      HashMap localHashMap4 = null;
      HashMap localHashMap5 = null;
      EntitlementGroups localEntitlementGroups1 = null;
      DRKey localDRKey = null;
      DRSetting localDRSetting = null;
      String str2 = " ";
      localDRKey = new DRKey();
      localDRKey.setDataType("PositivePay");
      localDRKey.setDataClass(str2);
      try
      {
        localHashMap1 = SystemAdminAdapter.getDataRetentionSettings(0, 0, paramHashMap);
        localEntitlementGroups1 = Entitlements.getEntitlementGroupsByType("MarketSegment");
        Iterator localIterator1 = localEntitlementGroups1.iterator();
        while (localIterator1.hasNext())
        {
          EntitlementGroup localEntitlementGroup1 = (EntitlementGroup)localIterator1.next();
          EntitlementGroups localEntitlementGroups2 = Entitlements.getChildrenByGroupType(localEntitlementGroup1.getGroupId(), "ServicesPackage");
          localHashMap3 = SystemAdminAdapter.getDataRetentionSettings(2, localEntitlementGroup1.getGroupId(), paramHashMap);
          Iterator localIterator2 = localEntitlementGroups2.iterator();
          while (localIterator2.hasNext())
          {
            EntitlementGroup localEntitlementGroup2 = (EntitlementGroup)localIterator2.next();
            EntitlementGroups localEntitlementGroups3 = Entitlements.getChildrenByGroupType(localEntitlementGroup2.getGroupId(), "BusinessAdmin");
            EntitlementGroups localEntitlementGroups4 = Entitlements.getChildrenByGroupType(localEntitlementGroup2.getGroupId(), "ConsumerAdmin");
            localHashMap4 = SystemAdminAdapter.getDataRetentionSettings(3, localEntitlementGroup2.getGroupId(), paramHashMap);
            Iterator localIterator3 = localEntitlementGroups3.iterator();
            EntitlementGroup localEntitlementGroup3;
            Object localObject1;
            Iterator localIterator4;
            Object localObject2;
            Object localObject3;
            Object localObject4;
            Object localObject5;
            Object localObject6;
            Object localObject7;
            while (localIterator3.hasNext())
            {
              localEntitlementGroup3 = (EntitlementGroup)localIterator3.next();
              localObject1 = Entitlements.getMembers(localEntitlementGroup3.getGroupId());
              localIterator4 = ((EntitlementGroupMembers)localObject1).iterator();
              while (localIterator4.hasNext())
              {
                localObject2 = (EntitlementGroupMember)localIterator4.next();
                int i = -1;
                localObject3 = null;
                localObject4 = null;
                HashMap localHashMap6 = null;
                localObject5 = null;
                localObject6 = null;
                try
                {
                  i = Integer.parseInt(((EntitlementGroupMember)localObject2).getId());
                }
                catch (Exception localException2)
                {
                  i = -1;
                }
                localObject3 = BusinessAdapter.getBusiness(i);
                if (localObject3 != null)
                {
                  localHashMap5 = SystemAdminAdapter.getDataRetentionSettings(4, ((Business)localObject3).getIdValue(), paramHashMap);
                  localHashMap2 = SystemAdminAdapter.getDataRetentionSettings(1, ((Business)localObject3).getAffiliateBankID(), paramHashMap);
                  localObject4 = new ArrayList(5);
                  ((ArrayList)localObject4).add(0, localHashMap5);
                  ((ArrayList)localObject4).add(0, localHashMap4);
                  ((ArrayList)localObject4).add(0, localHashMap3);
                  ((ArrayList)localObject4).add(0, localHashMap2);
                  ((ArrayList)localObject4).add(0, localHashMap1);
                  localHashMap6 = SystemAdminUtil.getOverriddenDataRetentionSettings((ArrayList)localObject4, paramHashMap);
                  localDRSetting = (DRSetting)localHashMap6.get(localDRKey);
                  if (localDRSetting != null)
                  {
                    localObject5 = AccountAdapter.getAccountsById(((Business)localObject3).getIdValue());
                    localObject6 = new PPayAccounts(((Accounts)localObject5).getLocale());
                    localObject7 = ((Accounts)localObject5).iterator();
                    while (((Iterator)localObject7).hasNext()) {
                      a(((PPayAccounts)localObject6).add(), (Account)((Iterator)localObject7).next());
                    }
                    this.ppayAdapter.cleanup((PPayAccounts)localObject6, localDRSetting.getRetentionDays(), paramHashMap);
                  }
                }
              }
            }
            localIterator3 = localEntitlementGroups4.iterator();
            while (localIterator3.hasNext())
            {
              localEntitlementGroup3 = (EntitlementGroup)localIterator3.next();
              localObject1 = Entitlements.getChildrenByGroupType(localEntitlementGroup3.getGroupId(), "USER");
              localIterator4 = ((EntitlementGroups)localObject1).iterator();
              while (localIterator4.hasNext())
              {
                localObject2 = (EntitlementGroup)localIterator4.next();
                EntitlementGroupMembers localEntitlementGroupMembers = Entitlements.getMembers(((EntitlementGroup)localObject2).getGroupId());
                localObject3 = localEntitlementGroupMembers.iterator();
                while (((Iterator)localObject3).hasNext())
                {
                  localObject4 = (EntitlementGroupMember)((Iterator)localObject3).next();
                  int j = -1;
                  localObject5 = null;
                  localObject6 = null;
                  localObject7 = null;
                  Accounts localAccounts = null;
                  PPayAccounts localPPayAccounts = null;
                  try
                  {
                    j = Integer.parseInt(((EntitlementGroupMember)localObject4).getId());
                  }
                  catch (Exception localException3)
                  {
                    j = -1;
                  }
                  localObject5 = CustomerAdapter.getUserById(j);
                  if (localObject5 != null)
                  {
                    localHashMap2 = SystemAdminAdapter.getDataRetentionSettings(1, ((User)localObject5).getAffiliateBankID(), paramHashMap);
                    localObject6 = new ArrayList(5);
                    ((ArrayList)localObject6).add(0, localHashMap4);
                    ((ArrayList)localObject6).add(0, localHashMap3);
                    ((ArrayList)localObject6).add(0, localHashMap2);
                    ((ArrayList)localObject6).add(0, localHashMap1);
                    localObject7 = SystemAdminUtil.getOverriddenDataRetentionSettings((ArrayList)localObject6, paramHashMap);
                    localDRSetting = (DRSetting)((HashMap)localObject7).get(localDRKey);
                    if (localDRSetting != null)
                    {
                      localAccounts = AccountAdapter.getAccountsById(((User)localObject5).getIdValue());
                      localPPayAccounts = new PPayAccounts(localAccounts.getLocale());
                      Iterator localIterator5 = localAccounts.iterator();
                      while (localIterator5.hasNext()) {
                        a(localPPayAccounts.add(), (Account)localIterator5.next());
                      }
                      this.ppayAdapter.cleanup(localPPayAccounts, localDRSetting.getRetentionDays(), paramHashMap);
                    }
                  }
                }
              }
            }
          }
        }
      }
      catch (Exception localException1)
      {
        if ((localException1 instanceof PPayException)) {
          throw ((PPayException)localException1);
        }
        throw new PPayException(1000, localException1);
      }
    }
  }
  
  public void submitDefaultDecisions(int paramInt, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PositivePayService.submitDefaultDecisions";
    try
    {
      CutOffDefinition localCutOffDefinition = AffiliateBankAdapter.getCutOffDefinition(paramInt, 1);
      SecureUser localSecureUser = new SecureUser();
      localSecureUser.setUserType(1);
      localSecureUser.setAffiliateID(paramInt);
      AffiliateBank localAffiliateBank = AffiliateBankAdapter.getAffiliateBankByID(localSecureUser, paramInt);
      String str2 = localAffiliateBank.getBankID();
      localAffiliateBank = ACH.getAffiliateBank(null, localAffiliateBank, paramHashMap);
      String str3 = localAffiliateBank.getAffiliateRoutingNum();
      Date localDate = new Date();
      Locale localLocale = Locale.getDefault();
      localSecureUser.setBankID(str2);
      BankIdentifier localBankIdentifier = new BankIdentifier(localLocale);
      localBankIdentifier.setBankDirectoryId(str3);
      boolean bool1 = SmartCalendar.isBankingDay(localSecureUser, localBankIdentifier, localDate, paramHashMap);
      if (!bool1) {
        return;
      }
      boolean bool2 = SmartCalendar.isHoliday(localSecureUser, localBankIdentifier, localDate, paramHashMap);
      if ((bool2) && (!Boolean.valueOf(localCutOffDefinition.getProcessOnHolidays()).booleanValue())) {
        return;
      }
      Business localBusiness1 = new Business(localLocale);
      localBusiness1.setBankId(str2);
      localBusiness1.setAffiliateBankID(Integer.parseInt(localAffiliateBank.getId()));
      DBCookie localDBCookie = new DBCookie();
      for (;;)
      {
        Business localBusiness2 = BusinessAdapter.getBusinesses(localBusiness1, localDBCookie, false, 0);
        if (localBusiness2 == null) {
          break;
        }
        String str4 = localBusiness2.getPPayDefaultDecision();
        if ((str4 != null) && (str4.length() > 0) && (!str4.equals("None")))
        {
          Account localAccount = new Account();
          localAccount.setLocale(localLocale);
          localAccount.setPositivePay("1");
          localAccount.setBankID(str2);
          Accounts localAccounts = AccountAdapter.searchAccounts(localAccount, localBusiness2.getIdValue());
          if ((localAccounts != null) && (!localAccounts.isEmpty()))
          {
            PPayAccounts localPPayAccounts = new PPayAccounts(localLocale);
            Object localObject2;
            for (int i = 0; i < localAccounts.size(); i++)
            {
              localObject1 = (Account)localAccounts.get(i);
              localObject2 = localPPayAccounts.add();
              a((PPayAccount)localObject2, (Account)localObject1);
            }
            EntitlementGroupMember localEntitlementGroupMember = localBusiness2.getEntitlementGroupMember();
            localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
            Object localObject1 = Entitlements.getEntitlementGroup(localEntitlementGroupMember.getEntitlementGroupId());
            if (localObject1 == null)
            {
              DebugLog.log(Level.WARNING, str1 + ": No entitlement data found for business " + localBusiness2.getBusinessName());
            }
            else
            {
              localBusiness2.setServicesPackageId(((EntitlementGroup)localObject1).getParentId());
              localObject2 = Entitlements.getChildrenByGroupType(((EntitlementGroup)localObject1).getGroupId(), "Business");
              if (((EntitlementGroups)localObject2).size() > 0) {
                localBusiness2.setEntitlementGroupId(((EntitlementGroup)((EntitlementGroups)localObject2).get(0)).getGroupId());
              }
              localPPayAccounts = PositivePayHandler.removeUnentitledAccounts(localBusiness2, localPPayAccounts, paramHashMap);
              localObject2 = new PPayDecisions(Locale.getDefault());
              for (int j = 0; j < localPPayAccounts.size(); j++)
              {
                localObject3 = (PPayAccount)localPPayAccounts.get(j);
                localObject4 = getIssuesForAccount((PPayAccount)localObject3, paramHashMap);
                localObject5 = a((PPayIssues)localObject4, str4);
                for (int k = 0; k < ((PPayDecisions)localObject5).size(); k++) {
                  ((PPayDecisions)localObject2).add(((PPayDecisions)localObject5).get(k));
                }
              }
              String str5 = "Business-" + localBusiness2.getId();
              Object localObject3 = new Date(System.currentTimeMillis());
              Object localObject4 = DateFormatUtil.getFormatter("yyyyMMdd'-'HHmmss").format((Date)localObject3);
              Object localObject5 = new StringBuffer();
              ((StringBuffer)localObject5).append(str5);
              ((StringBuffer)localObject5).append("-");
              ((StringBuffer)localObject5).append((String)localObject4);
              if (((PPayDecisions)localObject2).size() > 0) {
                a((PPayDecisions)localObject2, ((StringBuffer)localObject5).toString());
              }
              this.ppayAdapter.submitDefaultDecisions(localPPayAccounts, str4, paramHashMap);
            }
          }
        }
      }
    }
    catch (PPayException localPPayException)
    {
      throw localPPayException;
    }
    catch (CSILException localCSILException)
    {
      throw new PPayException(1000, localCSILException);
    }
    catch (Exception localException)
    {
      throw new PPayException(1000, localException);
    }
  }
  
  private PPayDecisions a(PPayIssues paramPPayIssues, String paramString)
  {
    PPayDecisions localPPayDecisions = new PPayDecisions(Locale.getDefault());
    for (int i = 0; i < paramPPayIssues.size(); i++)
    {
      PPayIssue localPPayIssue = (PPayIssue)paramPPayIssues.get(i);
      PPayDecision localPPayDecision = localPPayDecisions.add();
      localPPayDecision.setCheckRecord(localPPayIssue.getCheckRecord());
      localPPayDecision.setRejectReason(localPPayIssue.getRejectReason());
      localPPayDecision.setIssueDate(localPPayIssue.getIssueDate());
      HashMap localHashMap = localPPayIssue.getHash();
      localHashMap.putAll(localPPayDecision.getHash());
      localPPayDecision.setHash(localHashMap);
      localPPayDecision.setDecision(paramString);
    }
    return localPPayDecisions;
  }
  
  private HashMap a()
    throws PPayException
  {
    String str1 = "PositivePayService.retrieveConfigInfo";
    InputStream localInputStream = ResourceUtil.getResourceAsStream(this, "positivepay.xml");
    if (localInputStream == null)
    {
      DebugLog.log("During the initialization of the positive pay service the XML configuration file positivepay.xml could  not be found.");
      throw new PPayException(str1, 9, "During the initialization of the positive pay service the XML configuration file positivepay.xml could not be found.");
    }
    String str2 = null;
    try
    {
      str2 = Strings.streamToString(localInputStream);
    }
    catch (IOException localIOException)
    {
      DebugLog.throwing("Unable to read from the XML configuration file while initializing the positive pay service.", localIOException);
      throw new PPayException(localIOException, str1, 9, "Unable to read from the XML configuration file while initializing the positive pay service.");
    }
    XMLTag localXMLTag = new XMLTag(true);
    try
    {
      localXMLTag.build(str2);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Unable to read from the XML configuration file while initializing the positive pay service.", localException);
      throw new PPayException(localException, str1, 9, "Unable to read from the XML configuration file while initializing the positive pay service.");
    }
    return localXMLTag.getTagHashMap();
  }
  
  private void a(HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PositivePayService.initializePPayAdapter";
    String str2 = (String)paramHashMap.get("JAVA_CLASS");
    if (str2 == null)
    {
      DebugLog.log("The <JAVA_CLASS> tag was not found in the XML configuration file.");
      throw new PPayException(str1, 10, "The <JAVA_CLASS> tag was not found in the XML configuration file.");
    }
    HashMap localHashMap = (HashMap)paramHashMap.get("PPAYADAPTER_SETTINGS");
    if (localHashMap == null)
    {
      DebugLog.log("The <PPAYADAPTER_SETTINGS> tag was not found in the XML configuration file.");
      throw new PPayException(str1, 10, "The <PPAYADAPTER_SETTINGS> tag was not found in the XML configuration file.");
    }
    try
    {
      Class localClass = Class.forName(str2);
      Object localObject = localClass.newInstance();
      if (!(localObject instanceof IPPayAdapter))
      {
        DebugLog.log("The adapter class specified ( " + str2 + " ) " + "does not conform to the IPPayAdapter interface as required.");
        throw new PPayException(str1, 11, "The adapter class specified ( " + str2 + " ) " + "does not conform to the IPPayAdapter interface as required.");
      }
      this.ppayAdapter = ((IPPayAdapter)localObject);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      DebugLog.throwing("The class \"" + str2 + "\" specified in the " + "configuration XML could not be found in the classpath.", localClassNotFoundException);
      throw new PPayException(localClassNotFoundException, str1, 10, "The class \"" + str2 + "\" specified in the " + "configuration XML could not be found in the classpath.");
    }
    catch (InstantiationException localInstantiationException)
    {
      DebugLog.throwing("The class \"" + str2 + "\" specified in the " + "configuration XML could not be instantiated.  Please " + "ensure that this class is not abstract or an interface.", localInstantiationException);
      throw new PPayException(localInstantiationException, str1, 11, "The class \"" + str2 + "\" specified in the " + "configuration XML could not be instantiated.  Please " + "ensure that this class is not abstract or an interface.");
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      DebugLog.throwing("The service does not have access to the class \"" + str2 + "\" as specified in the " + "configuration XML.  Ensure that this class is " + "not declared private or package protected.", localIllegalAccessException);
      throw new PPayException(localIllegalAccessException, str1, 11, "The service does not have access to the class \"" + str2 + "\" as specified in the " + "configuration XML.  Ensure that this class is " + "not declared private or package protected.");
    }
    this.ppayAdapter.initialize(localHashMap);
  }
  
  private IMBInstance jdMethod_if(HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PositivePayService.getMessageBrokerInstance";
    int i = 0;
    String str2 = (String)paramHashMap.get("USERID");
    String str3 = (String)paramHashMap.get("PASSWORD");
    String str4 = (String)paramHashMap.get("DBTYPE");
    this.c = ((String)paramHashMap.get("DECISIONMESSAGENAME"));
    this.jdField_long = ((String)paramHashMap.get("DECISIONMESSAGESET"));
    this.jdField_null = ((String)paramHashMap.get("CHECKRECORDMESSAGENAME"));
    this.jdField_else = ((String)paramHashMap.get("CHECKRECORDMESSAGESET"));
    if ((str2 == null) || (str3 == null) || (str4 == null) || (this.c == null) || (this.jdField_long == null) || (this.jdField_null == null) || (this.jdField_else == null)) {
      i = 1;
    }
    int j = 0;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    String str9 = null;
    String str10 = null;
    String str11 = null;
    if (paramHashMap.get("CTXFACTORY") != null)
    {
      j = 1;
      str6 = (String)paramHashMap.get("CTXFACTORY");
      str7 = (String)paramHashMap.get("JNDIURL");
      str8 = (String)paramHashMap.get("JNDICTX");
      if ((str7 == null) || (str8 == null)) {
        i = 1;
      }
    }
    else
    {
      str9 = (String)paramHashMap.get("DBNAME");
      if (str9 == null) {
        i = 1;
      }
      if ((str4 != null) && (!str4.equals("DB2:AS390")))
      {
        str5 = (String)paramHashMap.get("USETHINDRIVER");
        str10 = (String)paramHashMap.get("MACHINE");
        str11 = (String)paramHashMap.get("PORTNUM");
        if ((str5 == null) || (str10 == null) || (str11 == null)) {
          i = 1;
        }
      }
    }
    if (i != 0)
    {
      DebugLog.log("Some connection parameters needed to initialize message broker were not found in the configuration file.  All required connection paramters must be present in order for the Positive Pay server to initialize.");
      throw new PPayException(str1, 10, "Some connection parameters needed to initialize message broker were not found in the configuration file.  All required connection paramters must be present in order for the Positive Pay server to initialize.");
    }
    boolean bool = false;
    if ((str5 != null) && (str5.equalsIgnoreCase("true"))) {
      bool = true;
    } else {
      bool = false;
    }
    IMBInstance localIMBInstance = null;
    try
    {
      if ((str4.equalsIgnoreCase("ASA")) || (str4.equalsIgnoreCase("ASE"))) {
        if (j != 0) {
          localIMBInstance = MBInstanceFactory.createHAInstanceFromJConnect(str2, str3, str6, str7, str8);
        } else {
          localIMBInstance = MBInstanceFactory.createInstanceFromJConnect(str2, str3, str10, str11, str9);
        }
      }
      if (str4.equalsIgnoreCase("ORACLE:OCI8")) {
        localIMBInstance = MBInstanceFactory.createInstanceFromOracle(str2, str3, str10, str11, str9, bool);
      }
      if (str4.equalsIgnoreCase("ORACLE:THIN")) {
        localIMBInstance = MBInstanceFactory.createInstanceFromOracle(str2, str3, str10, str11, str9, bool);
      }
      if (str4.equalsIgnoreCase("DB2:NET")) {
        localIMBInstance = MBInstanceFactory.createInstanceFromDB2(str2, str3, str10, str11, str9, false);
      }
      if (str4.equalsIgnoreCase("DB2:APP")) {
        localIMBInstance = MBInstanceFactory.createInstanceFromDB2(str2, str3, str10, str11, str9, true);
      }
      if (str4.equalsIgnoreCase("DB2:UN2")) {
        localIMBInstance = MBInstanceFactory.createInstanceFromDB2UniversalDriver(str2, str3, str9);
      }
      if (str4.equalsIgnoreCase("DB2:AS390")) {
        localIMBInstance = MBInstanceFactory.createInstanceFromDB2390(str2, str3, str9);
      }
      if (str4.equalsIgnoreCase("MSSQL:THIN")) {
        localIMBInstance = MBInstanceFactory.createInstanceFromMSSQL(str2, str3, str10, str11, str9);
      }
    }
    catch (MBException localMBException1)
    {
      DebugLog.throwing("Could not connect to DB", localMBException1);
      throw new PPayException(localMBException1, str1, 13, "Could not connect to DB.");
    }
    if (localIMBInstance == null)
    {
      DebugLog.log("The MBInstanceFactory returned a null instance,or failed due to an unsupported db type");
      throw new PPayException(str1, 13, "MBInstanceFactory returned a null instance,or failed due to an unsupported db type");
    }
    try
    {
      localIMBInstance.loadMessageSet(this.jdField_long);
      localIMBInstance.loadMessageSet(this.jdField_else);
    }
    catch (MBException localMBException2)
    {
      DebugLog.throwing("Could not load the MB message set", localMBException2);
      throw new PPayException(localMBException2, str1, 14, "Could not load MB message set");
    }
    return localIMBInstance;
  }
  
  private void a(String paramString)
    throws PPayException
  {
    if ((this.ppayAdapter == null) || (this.b == null) || (this.jdField_char == null) || (this.jdField_void == null))
    {
      DebugLog.log("The Positive Pay Service must be initialized before any calls are made to the service method " + paramString + ".");
      throw new PPayException(paramString, 18, "The Positive Pay Service must be initialized before any calls are made to the service method " + paramString + ".");
    }
  }
  
  private static void a(Account paramAccount, PPayAccount paramPPayAccount)
  {
    paramAccount.setID(paramPPayAccount.getAccountID());
    paramAccount.setBankID(paramPPayAccount.getBankID());
    paramAccount.setRoutingNum(paramPPayAccount.getRoutingNumber());
    paramAccount.setBankName(paramPPayAccount.getBankName());
    paramAccount.setNickName(paramPPayAccount.getNickName());
    paramAccount.setCurrencyCode(paramPPayAccount.getCurrencyType());
  }
  
  private static void a(PPayAccount paramPPayAccount, Account paramAccount)
  {
    paramPPayAccount.setAccountID(paramAccount.getID());
    paramPPayAccount.setBankID(paramAccount.getBankID());
    paramPPayAccount.setRoutingNumber(paramAccount.getRoutingNum());
    paramPPayAccount.setBankName(paramAccount.getBankName());
    paramPPayAccount.setNickName(paramAccount.getNickName());
    paramPPayAccount.setCurrencyType(paramAccount.getCurrencyCode());
  }
  
  protected static void validatePositivePayCheckRecords(PPayCheckRecords paramPPayCheckRecords, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PositivePayService.validatePositivePayCheckRecords";
    int i = 0;
    ErrorMessages localErrorMessages = new ErrorMessages();
    for (int j = 0; j < paramPPayCheckRecords.size(); j++)
    {
      PPayCheckRecord localPPayCheckRecord = (PPayCheckRecord)paramPPayCheckRecords.get(j);
      String str2 = localPPayCheckRecord.getRoutingNumber();
      if ((str2 != null) && (!str2.equals("")))
      {
        boolean bool = RoutingNumberUtil.isValidRoutingNumber(str2, true);
        if (!bool)
        {
          i = 1;
          localErrorMessages.setOperationCanContinue(false);
          localErrorMessages.add(new ValidationError("Record " + (j + 1), "The routing number provided for this record ('" + str2 + "') is invalid."));
          paramHashMap.put("ERRORS_ENCOUNTERED", localErrorMessages);
        }
      }
    }
    if (i != 0) {
      throw new PPayException(str1, 29, "One or more of the routing numbers provided in the check records are invalid.");
    }
  }
  
  private static int a(ReportCriteria paramReportCriteria)
  {
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str = localProperties.getProperty("MAX_DAYS_IN_DATE_RANGE");
    int i = 31;
    if (str != null) {
      try
      {
        i = Integer.parseInt(str);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.log(Level.WARNING, "The maximum number of days in a date range report option has been specified incorrectly. The value provided ( " + str + " ) is not a valid integer.");
      }
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.positivepay.PositivePayService
 * JD-Core Version:    0.7.0.1
 */