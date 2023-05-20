package com.ffusion.services.aggregation;

import com.ffusion.banksim.interfaces.BSException;
import com.ffusion.banksim.proxy.BankSim;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.AccountSummary;
import com.ffusion.beans.accounts.CreditCardAcctSummary;
import com.ffusion.beans.accounts.DepositAcctSummary;
import com.ffusion.beans.accounts.LoanAcctSummary;
import com.ffusion.beans.aggregation.AccountNVPair;
import com.ffusion.beans.aggregation.AccountNVPairs;
import com.ffusion.beans.aggregation.Accounts;
import com.ffusion.beans.aggregation.Institution;
import com.ffusion.beans.aggregation.InstitutionAccountType;
import com.ffusion.beans.aggregation.InstitutionAccountTypes;
import com.ffusion.beans.aggregation.Institutions;
import com.ffusion.beans.aggregation.Transactions;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Reporting;
import com.ffusion.services.AccountAggregation2;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class AggregationService
  implements AccountAggregation2
{
  public static final int DEFAULT_PAGE_SIZE = 20;
  public static final String FIRST_PAGING_INDEX = "FIRST_PAGING_INDEX";
  public static final String LAST_PAGING_INDEX = "LAST_PAGING_INDEX";
  
  public int initialize(String paramString)
  {
    initialize(new HashMap(0));
    return 0;
  }
  
  public void initialize(HashMap paramHashMap) {}
  
  public AccountSummaries getSummary(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws AggregationException
  {
    if (paramAccount == null) {
      throw new AggregationException(2);
    }
    Locale localLocale = paramAccount.getLocale();
    DateTime localDateTime = paramCalendar1 != null ? new DateTime(paramCalendar1, localLocale) : new DateTime(localLocale);
    String str = "USD";
    if (paramAccount.get("CURRENCY_CODE") != null) {
      str = (String)paramAccount.get("CURRENCY_CODE");
    }
    AccountSummaries localAccountSummaries = new AccountSummaries();
    int i = paramAccount.getTypeValue();
    Object localObject1 = new AccountSummary();
    Object localObject2;
    if ((i == 1) || (i == 2) || (i == 12))
    {
      localObject2 = new DepositAcctSummary();
      ((DepositAcctSummary)localObject2).setTotalCredits(new Currency("52174", str, localLocale));
      ((DepositAcctSummary)localObject2).setTotalCreditAmtMTD(new Currency("500", str, localLocale));
      ((DepositAcctSummary)localObject2).setCreditsNotDetailed(new Currency("2510", str, localLocale));
      ((DepositAcctSummary)localObject2).setDepositsSubjectToFloat(new Currency("5370", str, localLocale));
      ((DepositAcctSummary)localObject2).setTotalAdjCreditsYTD(new Currency("2516", str, localLocale));
      ((DepositAcctSummary)localObject2).setTotalLockboxDeposits(new Currency("540", str, localLocale));
      ((DepositAcctSummary)localObject2).setTotalDebits(new Currency("52310", str, localLocale));
      ((DepositAcctSummary)localObject2).setTotalDebitAmtMTD(new Currency("733", str, localLocale));
      ((DepositAcctSummary)localObject2).setTodaysTotalDebits(new Currency("2510", str, localLocale));
      ((DepositAcctSummary)localObject2).setTotalDebitsLessWireAndCharge(new Currency("5370", str, localLocale));
      ((DepositAcctSummary)localObject2).setTotalAdjDebitsYTD(new Currency("2510", str, localLocale));
      ((DepositAcctSummary)localObject2).setTotalDebitsExcludeReturns(new Currency("500", str, localLocale));
      ((DepositAcctSummary)localObject2).setImmedAvailAmt(new Currency("52310", str, localLocale));
      ((DepositAcctSummary)localObject2).setOneDayAvailAmt(new Currency("507", str, localLocale));
      ((DepositAcctSummary)localObject2).setMoreThanOneDayAvailAmt(new Currency("2510", str, localLocale));
      ((DepositAcctSummary)localObject2).setAvailOverdraft(new Currency("5370", str, localLocale));
      ((DepositAcctSummary)localObject2).setRestrictedCash(new Currency("2510", str, localLocale));
      ((DepositAcctSummary)localObject2).setAccruedInterest(new Currency("450", str, localLocale));
      ((DepositAcctSummary)localObject2).setAccruedDividend(new Currency("52310", str, localLocale));
      ((DepositAcctSummary)localObject2).setTotalOverdraftAmt(new Currency("400", str, localLocale));
      ((DepositAcctSummary)localObject2).setNextOverdraftPmtDate(localDateTime);
      ((DepositAcctSummary)localObject2).setInterestRate(1.2004F);
      ((DepositAcctSummary)localObject2).setOpeningLedger(new Currency("2510", str, localLocale));
      ((DepositAcctSummary)localObject2).setClosingLedger(new Currency("501", str, localLocale));
      ((DepositAcctSummary)localObject2).setCurrentAvailBal(new Currency("52310", str, localLocale));
      ((DepositAcctSummary)localObject2).setLedgerBal(new Currency("500", str, localLocale));
      ((DepositAcctSummary)localObject2).setOneDayFloat(new Currency("2510", str, localLocale));
      ((DepositAcctSummary)localObject2).setTwoDayFloat(new Currency("5375", str, localLocale));
      ((DepositAcctSummary)localObject2).setTotalFloat(new Currency("2510", str, localLocale));
      ((DepositAcctSummary)localObject2).setCurrentLedger(new Currency("52100", str, localLocale));
      ((DepositAcctSummary)localObject2).setInterestYTD(new Currency("352", str, localLocale));
      ((DepositAcctSummary)localObject2).setPriorYearInterest(new Currency("350", str, localLocale));
      localObject1 = localObject2;
    }
    else if (i == 4)
    {
      localObject2 = new LoanAcctSummary();
      ((LoanAcctSummary)localObject2).setAvailCredit(new Currency("5050", str, localLocale));
      ((LoanAcctSummary)localObject2).setAmtDue(new Currency("370", str, localLocale));
      ((LoanAcctSummary)localObject2).setInterestRate(5.4444F);
      ((LoanAcctSummary)localObject2).setDueDate(localDateTime);
      ((LoanAcctSummary)localObject2).setMaturityDate(localDateTime);
      ((LoanAcctSummary)localObject2).setAccruedInterest(new Currency("911", str, localLocale));
      ((LoanAcctSummary)localObject2).setOpeningBal(new Currency("700", str, localLocale));
      ((LoanAcctSummary)localObject2).setCollateralDescription("Beach House");
      ((LoanAcctSummary)localObject2).setPrincipalPastDue(new Currency("70045", str, localLocale));
      ((LoanAcctSummary)localObject2).setInterestPastDue(new Currency("737", str, localLocale));
      ((LoanAcctSummary)localObject2).setLateFees(new Currency("7035", str, localLocale));
      ((LoanAcctSummary)localObject2).setNextPrincipalAmt(new Currency("700", str, localLocale));
      ((LoanAcctSummary)localObject2).setNextInterestAmt(new Currency("7370", str, localLocale));
      ((LoanAcctSummary)localObject2).setOpenDate(localDateTime);
      ((LoanAcctSummary)localObject2).setCurrentBalance(new Currency("4000", str, localLocale));
      ((LoanAcctSummary)localObject2).setNextPaymentDate(localDateTime);
      ((LoanAcctSummary)localObject2).setNextPaymentAmt(new Currency("1050", str, localLocale));
      ((LoanAcctSummary)localObject2).setInterestYTD(new Currency("200", str, localLocale));
      ((LoanAcctSummary)localObject2).setPriorYearInterest(new Currency("180", str, localLocale));
      ((LoanAcctSummary)localObject2).setLoanTerm("1");
      ((LoanAcctSummary)localObject2).setTodaysPayoff(new Currency("50", str, localLocale));
      ((LoanAcctSummary)localObject2).setPayoffGoodThru(localDateTime);
      localObject1 = localObject2;
    }
    else if (i == 3)
    {
      localObject2 = new CreditCardAcctSummary();
      ((CreditCardAcctSummary)localObject2).setAvailCredit(new Currency("50370", str, localLocale));
      ((CreditCardAcctSummary)localObject2).setAmtDue(new Currency("50", str, localLocale));
      ((CreditCardAcctSummary)localObject2).setInterestRate(10.766F);
      ((CreditCardAcctSummary)localObject2).setDueDate(localDateTime);
      ((CreditCardAcctSummary)localObject2).setCardHolderName("Brad Bowman");
      ((CreditCardAcctSummary)localObject2).setCardExpDate(localDateTime);
      ((CreditCardAcctSummary)localObject2).setCreditLimit(new Currency("5800", str, localLocale));
      ((CreditCardAcctSummary)localObject2).setLastPaymentAmt(new Currency("7370", str, localLocale));
      ((CreditCardAcctSummary)localObject2).setNextPaymentMinAmt(new Currency("6600", str, localLocale));
      ((CreditCardAcctSummary)localObject2).setLastPaymentDate(localDateTime);
      ((CreditCardAcctSummary)localObject2).setNextPaymentDue(localDateTime);
      ((CreditCardAcctSummary)localObject2).setCurrentBalance(new Currency("43700", str, localLocale));
      ((CreditCardAcctSummary)localObject2).setLastAdvanceDate(localDateTime);
      ((CreditCardAcctSummary)localObject2).setLastAdvanceAmt(new Currency("2000", str, localLocale));
      ((CreditCardAcctSummary)localObject2).setPayoffAmount(new Currency("6600", str, localLocale));
      localObject1 = localObject2;
    }
    ((AccountSummary)localObject1).setAccountNumber(paramAccount.getNumber());
    ((AccountSummary)localObject1).setAccountID(paramAccount.getID());
    ((AccountSummary)localObject1).setBankID("1000");
    ((AccountSummary)localObject1).setRoutingNumber(paramAccount.getInstitutionID());
    ((AccountSummary)localObject1).setSummaryDate(localDateTime);
    ((AccountSummary)localObject1).setValueDate(localDateTime);
    ((AccountSummary)localObject1).setLocale(localLocale);
    ((AccountSummary)localObject1).setDateFormat(paramAccount.getDateFormat());
    localAccountSummaries.add(localObject1);
    return localAccountSummaries;
  }
  
  public Transactions getPagedTransactions(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws AggregationException
  {
    if (paramAccount == null) {
      throw new AggregationException(2);
    }
    com.ffusion.beans.accounts.Account localAccount = new com.ffusion.beans.accounts.Account();
    a(paramAccount, localAccount);
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    if (localReportCriteria == null) {
      throw new AggregationException(401);
    }
    String str = localReportCriteria.getSearchCriteria().getProperty("Date Range Type");
    Object localObject1;
    Object localObject2;
    if ("Relative".equalsIgnoreCase(str))
    {
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = new HashMap();
      localObject1 = (SecureUser)paramHashMap.get("SecureUser");
      if (localObject1 == null) {
        localObject1 = paramSecureUser;
      }
      try
      {
        Reporting.calculateDateRange((SecureUser)localObject1, null, localReportCriteria, localHashMap1, localHashMap2, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localObject2 = "Unable to calculate the date range.";
        DebugLog.log((String)localObject2);
        if (localCSILException.getCode() == -1009) {
          throw new AggregationException(localCSILException.getServiceError());
        }
        throw new AggregationException(localCSILException.getCode());
      }
    }
    try
    {
      BankSim.openPagedTransactions(localAccount, paramPagingContext, paramHashMap);
      int i = jdMethod_int(paramHashMap);
      int j = BankSim.getNumberOfTransactions(localAccount);
      localObject1 = BankSim.getPrevPage(localAccount, i);
      Transactions localTransactions = paramAccount.getTransactions();
      if (localTransactions != null)
      {
        localTransactions.clear();
      }
      else
      {
        paramAccount.setTransactions(new Transactions(paramAccount.getLocale()));
        localTransactions = paramAccount.getTransactions();
      }
      Object localObject3;
      while (((Enumeration)localObject1).hasMoreElements())
      {
        localObject2 = localTransactions.create();
        localObject3 = (com.ffusion.beans.banking.Transaction)((Enumeration)localObject1).nextElement();
        a((com.ffusion.beans.banking.Transaction)localObject3, (com.ffusion.beans.aggregation.Transaction)localObject2);
      }
      BankSim.closePagedTransactions(localAccount);
      if (localTransactions.size() > 0)
      {
        localObject2 = (com.ffusion.beans.aggregation.Transaction)localTransactions.get(0);
        paramPagingContext.setLastIndex(Long.parseLong(((com.ffusion.beans.aggregation.Transaction)localObject2).getID()));
        localObject2 = (com.ffusion.beans.aggregation.Transaction)localTransactions.get(localTransactions.size() - 1);
        paramPagingContext.setFirstIndex(Long.parseLong(((com.ffusion.beans.aggregation.Transaction)localObject2).getID()));
        localObject3 = new HashMap();
        long l1 = paramPagingContext.getLastIndex() - j + 1L;
        long l2 = paramPagingContext.getLastIndex();
        ((HashMap)localObject3).put("FIRST_PAGING_INDEX", new Long(l1));
        ((HashMap)localObject3).put("LAST_PAGING_INDEX", new Long(l2));
        ((HashMap)localObject3).put("ReportCriteria", localReportCriteria);
        paramPagingContext.setMap((HashMap)localObject3);
        paramPagingContext.setFirstPage(paramPagingContext.getFirstIndex() <= l1);
        paramPagingContext.setLastPage(true);
      }
      else
      {
        paramPagingContext.setFirstPage(true);
        paramPagingContext.setLastPage(true);
      }
      localTransactions.setPagingContext(paramPagingContext);
      return localTransactions;
    }
    catch (BSException localBSException) {}
    return new Transactions();
  }
  
  public Transactions getNextTransactions(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws AggregationException
  {
    throw new AggregationException(11005);
  }
  
  public Transactions getPreviousTransactions(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws AggregationException
  {
    throw new AggregationException(11005);
  }
  
  public void setSettings(String paramString) {}
  
  public String getSettings()
  {
    return null;
  }
  
  public int getInstitutions(Institutions paramInstitutions, Institution paramInstitution)
  {
    Institution localInstitution = paramInstitutions.add();
    localInstitution.setID("110220330");
    localInstitution.setName("Bank of Affluence");
    localInstitution.setGroupID("1");
    localInstitution.setCustomerSupportPhoneNumber("1-800-555-5555");
    localInstitution.setTechnicalSupportPhoneNumber("1-800-555-5555");
    localInstitution.setFax("1-800-555-5555");
    localInstitution.setURL("www.financialfusion.com");
    localInstitution.setTaxID("1111111");
    localInstitution.setStreet("445 Wes Graham Way");
    localInstitution.setCity("Waterloo");
    localInstitution.setState("ON");
    localInstitution.setZipCode("N2L 6R2");
    localInstitution.setCountry("Canada");
    localInstitution = paramInstitutions.add();
    localInstitution.setID("110220440");
    localInstitution.setName("Bank of Finance");
    localInstitution.setGroupID("1");
    localInstitution.setCustomerSupportPhoneNumber("1-800-555-5555");
    localInstitution.setTechnicalSupportPhoneNumber("1-800-555-5555");
    localInstitution.setFax("1-800-555-5555");
    localInstitution.setURL("www.financialfusion.com");
    localInstitution.setTaxID("2222222");
    localInstitution.setStreet("123 Fake Street");
    localInstitution.setCity("Springfield");
    localInstitution.setState("OH");
    localInstitution.setZipCode("45678");
    localInstitution.setCountry("USA");
    localInstitution = paramInstitutions.add();
    localInstitution.setID("110220550");
    localInstitution.setName("Bank of Business");
    localInstitution.setGroupID("1");
    localInstitution.setCustomerSupportPhoneNumber("1-800-555-5555");
    localInstitution.setTechnicalSupportPhoneNumber("1-800-555-5555");
    localInstitution.setFax("1-800-555-5555");
    localInstitution.setURL("www.financialfusion.com");
    localInstitution.setTaxID("3333333");
    localInstitution.setStreet("10 Church Street");
    localInstitution.setCity("Waga Waga");
    localInstitution.setState("NSW");
    localInstitution.setZipCode("2890");
    localInstitution.setCountry("Australia");
    return 0;
  }
  
  public int getInstitutionAccountTypes(Institution paramInstitution)
  {
    InstitutionAccountTypes localInstitutionAccountTypes = paramInstitution.getInstitutionAccountTypes();
    Locale localLocale = paramInstitution.getLocale();
    if (localInstitutionAccountTypes == null)
    {
      localInstitutionAccountTypes = new InstitutionAccountTypes(localLocale);
      paramInstitution.setInstitutionAccountTypes(localInstitutionAccountTypes);
    }
    else
    {
      localInstitutionAccountTypes.setLocale(localLocale);
    }
    InstitutionAccountType localInstitutionAccountType1 = localInstitutionAccountTypes.getByType(String.valueOf(1));
    InstitutionAccountType localInstitutionAccountType2;
    if (localInstitutionAccountType1 == null)
    {
      localInstitutionAccountType2 = localInstitutionAccountTypes.add();
      localInstitutionAccountType2.setTypeValue(1);
    }
    localInstitutionAccountType1 = localInstitutionAccountTypes.getByType(String.valueOf(2));
    if (localInstitutionAccountType1 == null)
    {
      localInstitutionAccountType2 = localInstitutionAccountTypes.add();
      localInstitutionAccountType2.setTypeValue(2);
    }
    return 0;
  }
  
  public int getRequiredAccountFields(com.ffusion.beans.aggregation.Account paramAccount)
  {
    Locale localLocale = paramAccount.getLocale();
    AccountNVPairs localAccountNVPairs = paramAccount.getAccountNVPairs();
    if (localAccountNVPairs == null)
    {
      localAccountNVPairs = new AccountNVPairs(localLocale);
      paramAccount.setAccountNVPairs(localAccountNVPairs);
    }
    else
    {
      localAccountNVPairs.setLocale(localLocale);
    }
    AccountNVPair localAccountNVPair = localAccountNVPairs.add();
    localAccountNVPair.setName("ACCOUNT_NUMBER");
    localAccountNVPair.setDescription(ResourceUtil.getString("com.ffusion.beans.accounts.resources", "ACCOUNT_NUMBER", localLocale));
    localAccountNVPair = localAccountNVPairs.add();
    localAccountNVPair.setName("NICKNAME");
    localAccountNVPair.setDescription(ResourceUtil.getString("com.ffusion.beans.accounts.resources", "NICKNAME", localLocale));
    localAccountNVPair = localAccountNVPairs.add();
    localAccountNVPair.setName("CURRENCY_CODE");
    localAccountNVPair.setDescription(ResourceUtil.getString("com.ffusion.beans.accounts.resources", "CURRENCY_CODE", localLocale));
    return 0;
  }
  
  public int addAccount(com.ffusion.beans.aggregation.Account paramAccount)
  {
    return 11005;
  }
  
  public int modifyAccount(com.ffusion.beans.aggregation.Account paramAccount)
  {
    return 11005;
  }
  
  public int deleteAccount(com.ffusion.beans.aggregation.Account paramAccount)
  {
    return 11005;
  }
  
  public int getAccounts(Accounts paramAccounts, HashMap paramHashMap)
  {
    return 11005;
  }
  
  public int refreshAccount(com.ffusion.beans.aggregation.Account paramAccount)
  {
    return 11005;
  }
  
  public int getTransactions(com.ffusion.beans.aggregation.Account paramAccount)
  {
    String str = getClass().getName() + ".getTransactions";
    com.ffusion.beans.accounts.Account localAccount = new com.ffusion.beans.accounts.Account();
    a(paramAccount, localAccount);
    try
    {
      Enumeration localEnumeration = BankSim.getTransactions(localAccount, null, null);
      Transactions localTransactions = new Transactions();
      localTransactions.setLocale(paramAccount.getLocale());
      while (localEnumeration.hasMoreElements())
      {
        com.ffusion.beans.aggregation.Transaction localTransaction = localTransactions.create();
        com.ffusion.beans.banking.Transaction localTransaction1 = (com.ffusion.beans.banking.Transaction)localEnumeration.nextElement();
        a(localTransaction1, localTransaction);
      }
      paramAccount.setTransactions(localTransactions);
    }
    catch (BSException localBSException) {}
    return 0;
  }
  
  public int signOn(String paramString1, String paramString2)
  {
    return 11005;
  }
  
  public int changePIN(String paramString1, String paramString2)
  {
    return 11005;
  }
  
  public void setInitURL(String paramString) {}
  
  public boolean signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws AggregationException
  {
    return true;
  }
  
  public int addAccount(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, HashMap paramHashMap)
    throws AggregationException
  {
    return 0;
  }
  
  public int deleteAccount(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, HashMap paramHashMap)
    throws AggregationException
  {
    return 0;
  }
  
  private void a(com.ffusion.beans.aggregation.Account paramAccount, com.ffusion.beans.accounts.Account paramAccount1)
  {
    if ((paramAccount == null) || (paramAccount1 == null)) {
      return;
    }
    paramAccount1.setLocale(paramAccount.getLocale());
    paramAccount1.setID(paramAccount.getID());
    paramAccount1.setNickName(paramAccount.getNickName());
    paramAccount1.setNumber(paramAccount.getNumber());
    paramAccount1.setBankName(paramAccount.getInstitutionName());
    paramAccount1.setType(com.ffusion.beans.aggregation.Account.getAccountTypeFromAggregationType(paramAccount.getTypeValue()));
    paramAccount1.setStatus(paramAccount.getStatus());
    paramAccount1.setRoutingNum(paramAccount.getInstitutionID());
  }
  
  private int jdMethod_int(HashMap paramHashMap)
    throws AggregationException
  {
    int i = 20;
    if (paramHashMap != null)
    {
      Object localObject = paramHashMap.get("PAGESIZE");
      if (localObject != null) {
        try
        {
          String str = (String)localObject;
          i = Integer.parseInt(str);
        }
        catch (ClassCastException localClassCastException)
        {
          throw new AggregationException(3);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new AggregationException(3);
        }
      }
    }
    return i;
  }
  
  private void a(com.ffusion.beans.banking.Transaction paramTransaction, com.ffusion.beans.aggregation.Transaction paramTransaction1)
  {
    if ((paramTransaction == null) || (paramTransaction1 == null)) {
      return;
    }
    paramTransaction1.setID(paramTransaction.getID());
    paramTransaction1.setType(paramTransaction.getTypeValue());
    paramTransaction1.setCategory(paramTransaction.getCategory());
    paramTransaction1.setDescription(paramTransaction.getDescription());
    paramTransaction1.setReferenceNumber(paramTransaction.getReferenceNumber());
    paramTransaction1.setMemo(paramTransaction.getMemo());
    paramTransaction1.setDate(paramTransaction.getDateValue());
    paramTransaction1.setAmount(paramTransaction.getAmountValue());
    paramTransaction1.setTrackingID(paramTransaction.getTrackingID());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.aggregation.AggregationService
 * JD-Core Version:    0.7.0.1
 */