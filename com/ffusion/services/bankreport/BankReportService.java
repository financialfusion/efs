package com.ffusion.services.bankreport;

import com.ffusion.bankreport.BRException;
import com.ffusion.bankreport.BankReportUtil;
import com.ffusion.bankreport.IBankReportProcessor;
import com.ffusion.bankreport.adapter.BankReportAdapter;
import com.ffusion.bankreport.adapter.IBankReportAdapter;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.bankreport.BankReport;
import com.ffusion.beans.bankreport.BankReportDefinition;
import com.ffusion.beans.bankreport.BankReportDefinitions;
import com.ffusion.beans.bankreport.BankReports;
import com.ffusion.beans.bankreport.ProcessorInfo;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.IBankReportService;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class BankReportService
  implements IBankReportService
{
  protected static final String METHOD_PROCESS_BANK_REPORT_FILE = "BankReportService.processBankReportFile";
  protected static final String METHOD_GET_REPORT_DEFINITIONS = "BankReportService.getReportDefinitions";
  protected static final String METHOD_GET_REPORTS = "BankReportService.getReports";
  protected static final String METHOD_GET_ACCOUNTS_FOR_REPORT = "BankReportService.getAccountsForReport";
  protected static final String METHOD_GET_REPORT_DATA = "BankReportService.getReportData";
  protected static final String METHOD_CLEANUP_OLD_REPORTS = "BankReportService.cleanupOldReports";
  protected static final String METHOD_REMOVE_REPORT = "BankReportService.removeReport";
  protected static final String METHOD_REMOVE_ACCOUNT_DATA = "BankReportService.removeAccountData";
  protected static final String METHOD_REMOVE_BUSINESS_DATA = "BankReportService.removeBusinessData";
  protected static final String METHOD_REMOVE_USER_DATA = "BankReportService.removeUserData";
  protected static String DB_PROPERTIES_TAG = "DB_PROPERTIES";
  protected static String EXTRA_FILE_PATH = "FILE_PATH";
  protected static String DATA_RETENTION_OPTION = "DataRetention";
  private static final String jdField_int = "select LINEDATA from BR_BANKREPORTDATA where REPORTID = ? and ( ( ACCOUNTID is null and RTGNUM is null ) ";
  private static final String jdField_do = "or ( ACCOUNTID = ? and RTGNUM = ? ) ";
  private static final String jdField_for = " ) order by LINENUM asc";
  private IBankReportAdapter a;
  private HashMap jdField_if;
  private HashMap jdField_new;
  
  public void initialize(HashMap paramHashMap)
    throws BRException
  {
    this.jdField_if = new HashMap();
    this.jdField_new = new HashMap();
    InputStream localInputStream = null;
    String str = null;
    try
    {
      localInputStream = ResourceUtil.getResourceAsStream(this, "bankreports.xml");
      str = Strings.streamToString(localInputStream);
    }
    catch (Exception localException1)
    {
      throw new BRException(60001, "Failed to retrieve BankReport service settings XML resource", localException1);
    }
    XMLTag localXMLTag1 = null;
    try
    {
      localXMLTag1 = new XMLTag(true);
      localXMLTag1.build(str);
      str = null;
    }
    catch (Exception localException2)
    {
      throw new BRException(60001, "Error when parsing XML file bankreports.xml", localException2);
    }
    XMLTag localXMLTag2 = localXMLTag1.getContainedTag("PROCESSOR_LIST");
    ArrayList localArrayList = (ArrayList)XMLUtil.tagToHashMap(localXMLTag2).get("Processor");
    Object localObject4;
    Object localObject5;
    Object localObject6;
    Object localObject7;
    Object localObject8;
    if (localArrayList != null)
    {
      localObject1 = localArrayList.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (HashMap)((Iterator)localObject1).next();
        localObject3 = new ProcessorInfo(Locale.getDefault());
        localObject4 = (ArrayList)((HashMap)((HashMap)localObject2).get("REPORT_TYPE_LIST")).get("ReportType");
        ((ProcessorInfo)localObject3).setReportTypes((ArrayList)localObject4);
        localObject5 = (String)((HashMap)localObject2).get("ProcessorClass");
        ((ProcessorInfo)localObject3).setProcessorClassName((String)localObject5);
        localObject6 = ((HashMap)localObject2).get("ProcessorConfig");
        if ((localObject6 instanceof HashMap))
        {
          localObject7 = (HashMap)((HashMap)localObject2).get("ProcessorConfig");
          ((ProcessorInfo)localObject3).setProcessorConfig((HashMap)localObject7);
        }
        localObject7 = ((ArrayList)localObject4).iterator();
        while (((Iterator)localObject7).hasNext())
        {
          localObject8 = (String)((Iterator)localObject7).next();
          if (this.jdField_if.get(localObject8) == null)
          {
            this.jdField_if.put(localObject8, localObject3);
          }
          else
          {
            DebugLog.log("Error: there is a processor already defined for the report type " + (String)localObject8 + ".");
            throw new BRException(60001, "Error when parsing XML file bankreports.xml");
          }
        }
      }
    }
    Object localObject1 = localXMLTag1.getContainedTag("REPORT_DEFINITION_LIST");
    Object localObject2 = (ArrayList)XMLUtil.tagToHashMap((XMLTag)localObject1).get("ReportDefinition");
    if (localObject2 != null)
    {
      localObject3 = ((ArrayList)localObject2).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject4 = (HashMap)((Iterator)localObject3).next();
        localObject5 = new BankReportDefinition(Locale.getDefault());
        localObject6 = (String)((HashMap)localObject4).get("ReportKey");
        ((BankReportDefinition)localObject5).setReportKey((String)localObject6);
        localObject7 = (String)((HashMap)localObject4).get("ReportType");
        ((BankReportDefinition)localObject5).setReportType((String)localObject7);
        if (this.jdField_if.get(localObject7) == null)
        {
          DebugLog.log("Error: there is no processor defined for the report type " + (String)localObject7);
          throw new BRException(60001, "Error when parsing XML file bankreports.xml");
        }
        localObject8 = (HashMap)((HashMap)localObject4).get("Options");
        ((BankReportDefinition)localObject5).setOptions((HashMap)localObject8);
        this.jdField_new.put(localObject6, localObject5);
      }
    }
    Object localObject3 = (Properties)paramHashMap.get(DB_PROPERTIES_TAG);
    if (localObject3 == null)
    {
      localObject4 = "<" + DB_PROPERTIES_TAG + "> tag not found " + "in XML configuration file during " + "initialization of the Bank Report service";
      DebugLog.log(Level.SEVERE, (String)localObject4);
      throw new BRException(60001, (String)localObject4);
    }
    this.a = new BankReportAdapter();
    this.a.initialize((Properties)localObject3);
  }
  
  public void processBankReportFile(InputStream paramInputStream, HashMap paramHashMap)
    throws BRException
  {
    verifyInitialized("BankReportService.processBankReportFile");
    String str = (String)paramHashMap.get(EXTRA_FILE_PATH);
    BankReportDefinition localBankReportDefinition = (BankReportDefinition)this.jdField_new.get(str);
    if (localBankReportDefinition == null) {
      throw new BRException(60114, "Could not process the bank report file.");
    }
    ProcessorInfo localProcessorInfo = (ProcessorInfo)this.jdField_if.get(localBankReportDefinition.getReportType());
    if (localProcessorInfo == null)
    {
      localObject = "Processor not found for Bank Report type: " + localBankReportDefinition.getReportType();
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new BRException(60302, (String)localObject);
    }
    Object localObject = BankReportUtil.createProcessorInstance(localProcessorInfo);
    ((IBankReportProcessor)localObject).setBankReportAdapter(this.a);
    try
    {
      ((IBankReportProcessor)localObject).processReport(localBankReportDefinition, paramInputStream, paramHashMap);
    }
    catch (BRException localBRException)
    {
      DebugLog.throwing("com.ffusion.services.bankreport.BankReportService.processBankReportFile: An error occurred while invoking the processor for a bank report file of type '" + localBankReportDefinition.getReportType() + "'", localBRException);
      throw localBRException;
    }
  }
  
  public BankReportDefinitions getReportDefinitions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws BRException
  {
    verifyInitialized("BankReportService.getReportDefinitions");
    Locale localLocale = Locale.getDefault();
    BankReportDefinitions localBankReportDefinitions = new BankReportDefinitions(localLocale);
    Iterator localIterator = this.jdField_new.values().iterator();
    while (localIterator.hasNext())
    {
      BankReportDefinition localBankReportDefinition = new BankReportDefinition(localLocale);
      localBankReportDefinition.set((BankReportDefinition)localIterator.next());
      localBankReportDefinitions.add(localBankReportDefinition);
    }
    return localBankReportDefinitions;
  }
  
  public BankReports getReports(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws BRException
  {
    verifyInitialized("BankReportService.getReports");
    return this.a.getReports(paramSecureUser.getBusinessID(), paramString, paramHashMap);
  }
  
  public Accounts getAccountsForReport(SecureUser paramSecureUser, BankReport paramBankReport, HashMap paramHashMap)
    throws BRException
  {
    verifyInitialized("BankReportService.getAccountsForReport");
    return this.a.getAccountsForReport(paramBankReport.getReportID(), paramHashMap);
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BRException
  {
    verifyInitialized("BankReportService.getReportData");
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if (localProperties == null) {
      throw new BRException(60111, "Could not obtain the bank report data.");
    }
    Enumeration localEnumeration = localProperties.propertyNames();
    while (localEnumeration.hasMoreElements())
    {
      str1 = (String)localEnumeration.nextElement();
      String str2 = paramReportCriteria.getDisplayValue(str1);
      if ((str2 == null) || (str2.length() == 0)) {
        paramReportCriteria.setHiddenSearchCriterion(str1, true);
      }
    }
    String str1 = localProperties.getProperty("Report_HIDE");
    int i = -1;
    if (str1 == null) {
      throw new BRException(60112, "Could not obtain the bank report data.");
    }
    i = Integer.parseInt(str1);
    String str3 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    if (str3 == null) {
      throw new BRException(60100, "Could not obtain the bank report data.");
    }
    ReportResult localReportResult = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = this.a.getConnection();
      Accounts localAccounts = (Accounts)paramHashMap.get("ACCOUNTS");
      StringBuffer localStringBuffer = new StringBuffer("select LINEDATA from BR_BANKREPORTDATA where REPORTID = ? and ( ( ACCOUNTID is null and RTGNUM is null ) ");
      int j = 0;
      if ((localAccounts != null) && (localAccounts.size() > 0)) {
        j = localAccounts.size();
      }
      a(paramReportCriteria, localAccounts, str3, paramReportCriteria.getLocale());
      for (int k = 0; k < j; k++) {
        localStringBuffer.append("or ( ACCOUNTID = ? and RTGNUM = ? ) ");
      }
      localStringBuffer.append(" ) order by LINENUM asc");
      String str4 = localStringBuffer.toString();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str4);
      localPreparedStatement.setInt(1, i);
      for (int m = 0; m < j; m++)
      {
        localObject1 = (Account)localAccounts.get(m);
        localPreparedStatement.setString(2 * m + 2, ((Account)localObject1).getID());
        localPreparedStatement.setString(2 * m + 3, ((Account)localObject1).getRoutingNum());
      }
      Locale localLocale = paramSecureUser.getLocale();
      localReportResult = new ReportResult(localLocale);
      localReportResult.init(paramReportCriteria);
      Object localObject1 = new ReportDataDimensions(localLocale);
      ((ReportDataDimensions)localObject1).setNumColumns(1);
      ((ReportDataDimensions)localObject1).setNumRows(-1);
      localReportResult.setDataDimensions((ReportDataDimensions)localObject1);
      BankReportDefinitions localBankReportDefinitions = getReportDefinitions(paramSecureUser, paramHashMap);
      Object localObject2 = null;
      for (int n = 0; n < localBankReportDefinitions.size(); n++)
      {
        localObject3 = (BankReportDefinition)localBankReportDefinitions.get(n);
        if ((((BankReportDefinition)localObject3).getReportType() != null) && (((BankReportDefinition)localObject3).getReportType().equals(str3)))
        {
          localObject2 = localObject3;
          break;
        }
      }
      if (localObject2 == null) {
        throw new BRException(60113, "Could not obtain the bank report data.");
      }
      String str5 = (String)localObject2.getOptions().get("FontStyle");
      Object localObject3 = new ReportColumn(localLocale);
      ((ReportColumn)localObject3).setDataType("java.lang.String");
      ((ReportColumn)localObject3).setWidthAsPercent(100);
      if ((str5 != null) && (str5.length() > 0)) {
        ((ReportColumn)localObject3).setReportColumnProperty("DATASTYLE", str5);
      }
      ((ReportColumn)localObject3).setReportColumnProperty("HTML_FORCE_SPACES", "true");
      localReportResult.addColumn((ReportColumn)localObject3);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str4);
      while (localResultSet.next())
      {
        ReportRow localReportRow = new ReportRow(localLocale);
        ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
        ReportDataItem localReportDataItem = localReportDataItems.add();
        localReportDataItem.setData(localResultSet.getString(1));
        localReportRow.setDataItems(localReportDataItems);
        localReportResult.addRow(localReportRow);
      }
    }
    catch (Throwable localThrowable1)
    {
      if ((localThrowable1 instanceof SQLException)) {
        throw new BRException(60010, "Could not obtain the bank report data.", localThrowable1);
      }
      throw new BRException("Could not obtain the bank report data.", localThrowable1);
    }
    finally
    {
      try
      {
        if (localReportResult != null) {
          localReportResult.fini();
        }
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        this.a.releaseConnection(localConnection);
      }
      catch (Throwable localThrowable2) {}
    }
    return localReportResult;
  }
  
  public void cleanupOldReports(int paramInt, String paramString, HashMap paramHashMap)
    throws BRException
  {
    verifyInitialized("BankReportService.cleanupOldReports");
    if (paramInt >= 0)
    {
      this.a.cleanupOldReports(paramInt, paramString, paramHashMap);
    }
    else
    {
      Iterator localIterator = this.jdField_new.values().iterator();
      while (localIterator.hasNext())
      {
        BankReportDefinition localBankReportDefinition = (BankReportDefinition)localIterator.next();
        String str1 = localBankReportDefinition.getReportType();
        String str2 = (String)localBankReportDefinition.getOptions().get(DATA_RETENTION_OPTION);
        if (str2 != null) {
          try
          {
            int i = Integer.parseInt(str2);
            this.a.cleanupOldReports(i, str1, paramHashMap);
          }
          catch (NumberFormatException localNumberFormatException)
          {
            DebugLog.log(Level.WARNING, "Invalid number of days for DataRetention for Bank Report Type: " + str1);
          }
        }
      }
    }
  }
  
  public void removeReport(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws BRException
  {
    verifyInitialized("BankReportService.removeReport");
    this.a.removeReport(paramInt, paramHashMap);
  }
  
  public void removeAccountData(int paramInt, Account paramAccount, HashMap paramHashMap)
    throws BRException
  {
    verifyInitialized("BankReportService.removeAccountData");
    this.a.removeAccountData(paramInt, paramAccount, paramHashMap);
  }
  
  public void removeBusinessData(int paramInt, HashMap paramHashMap)
    throws BRException
  {
    verifyInitialized("BankReportService.removeBusinessData");
    this.a.removeDirectoryData(paramInt, paramHashMap);
  }
  
  public void removeUserData(int paramInt, HashMap paramHashMap)
    throws BRException
  {
    verifyInitialized("BankReportService.removeUserData");
    this.a.removeDirectoryData(paramInt, paramHashMap);
  }
  
  protected void verifyInitialized(String paramString)
    throws BRException
  {
    if (this.a == null)
    {
      String str = "The Bank Report Service must be initialized before any calls are made to the service method " + paramString + ".";
      DebugLog.log(Level.SEVERE, str);
      throw new BRException(60002, str);
    }
  }
  
  protected IBankReportAdapter getBankReportAdapter()
  {
    return this.a;
  }
  
  protected ProcessorInfo getProcessorInfo(String paramString)
  {
    return (ProcessorInfo)this.jdField_if.get(paramString);
  }
  
  protected BankReportDefinition getBankReportDefinition(String paramString)
  {
    return (BankReportDefinition)this.jdField_new.get(paramString);
  }
  
  protected Iterator getBankReportDefinitionsIterator()
  {
    return this.jdField_new.values().iterator();
  }
  
  private static void a(ReportCriteria paramReportCriteria, Accounts paramAccounts, String paramString, Locale paramLocale)
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    String str1 = localProperties.getProperty("Accounts");
    if ((str1 == null) || (str1.length() <= 0)) {
      return;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
    int i = localStringTokenizer.countTokens();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int j = 0; j < i; j++)
    {
      if (j > 0) {
        localStringBuffer.append("&&");
      }
      String str2 = localStringTokenizer.nextToken().trim();
      Account localAccount = paramAccounts.getByID(str2);
      if (localAccount != null)
      {
        if ((localAccount.getRoutingNum() != null) && (localAccount.getRoutingNum().length() > 0))
        {
          localStringBuffer.append(localAccount.getRoutingNum());
          localStringBuffer.append(" ");
          localStringBuffer.append(":");
          localStringBuffer.append(" ");
        }
        try
        {
          localStringBuffer.append(AccountUtil.buildAccountDisplayText(localAccount.getID(), paramLocale));
        }
        catch (UtilException localUtilException1)
        {
          DebugLog.throwing("Error while constructing account display string for report type, '" + paramString + "'.", localUtilException1);
          localStringBuffer.append(str2);
        }
        if ((localAccount.getNickName() != null) && (localAccount.getNickName().length() > 0))
        {
          localStringBuffer.append(" - ");
          localStringBuffer.append(localAccount.getNickName());
        }
        localStringBuffer.append(" - ");
        localStringBuffer.append(localAccount.getCurrencyCode());
      }
      else
      {
        try
        {
          localStringBuffer.append(AccountUtil.buildAccountDisplayText(str2, paramLocale));
        }
        catch (UtilException localUtilException2)
        {
          DebugLog.throwing("Error while constructing account display string for report type, '" + paramString + "'.", localUtilException2);
          localStringBuffer.append(str2);
        }
      }
    }
    paramReportCriteria.setDisplayValue("Accounts", localStringBuffer.toString());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.bankreport.BankReportService
 * JD-Core Version:    0.7.0.1
 */