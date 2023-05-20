package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.aggregation.Account;
import com.ffusion.beans.aggregation.Accounts;
import com.ffusion.beans.aggregation.Institution;
import com.ffusion.beans.aggregation.Institutions;
import com.ffusion.beans.aggregation.Transactions;
import com.ffusion.csil.CSILException;
import com.ffusion.services.AccountAggregation2;
import com.ffusion.services.aggregation.AggregationException;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;

public class AccountAggregation
  extends Initialize
{
  private static final String a6a = "Account Aggregation";
  private static AccountAggregation2 a6b = null;
  
  public static void initialize(HashMap paramHashMap)
  {
    String str = "com.ffusion.csil.handlers.AccountAggregation.initialize";
    debug(str);
    try
    {
      HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Account Aggregation", str, 20107);
      a6b = (AccountAggregation2)HandlerUtil.instantiateService(localHashMap, str, 20107);
      a6b.initialize(localHashMap);
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, "AccountAggregation.initialize: " + localException.toString());
    }
  }
  
  public static Object getService()
  {
    return a6b;
  }
  
  public static SecureUser signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.AccountAggregation.signOn");
    if (paramSecureUser == null) {
      paramSecureUser = new SecureUser();
    }
    boolean bool = false;
    try
    {
      bool = a6b.signOn(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (AggregationException localAggregationException)
    {
      throwing(-1009, 90007);
    }
    if (!bool) {
      throwing(-1009, 90007);
    }
    return paramSecureUser;
  }
  
  public static Institutions getInstitutions(SecureUser paramSecureUser, Institution paramInstitution, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.AccountAggregation.getInstitutions");
    Institutions localInstitutions = (Institutions)paramHashMap.get("INSTITUTIONS");
    if (localInstitutions == null)
    {
      debug("Missing required parameter: extra.'INSTITUTIONS' - creating new Institutions()");
      localInstitutions = new Institutions(paramInstitution.getLocale());
    }
    int i = 0;
    i = a6b.getInstitutions(localInstitutions, paramInstitution);
    if (i != 0) {
      throwing(-1009, i);
    }
    return localInstitutions;
  }
  
  public static Institution getInstitutionAccountTypes(SecureUser paramSecureUser, Institution paramInstitution, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.AccountAggregation.getInstitutionAccountTypes");
    int i = a6b.getInstitutionAccountTypes(paramInstitution);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramInstitution;
  }
  
  public static Account getRequiredAccountFields(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.AccountAggregation.getRequiredAccountFields");
    int i = 0;
    i = a6b.getRequiredAccountFields(paramAccount);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramAccount;
  }
  
  public static Account addAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.AccountAggregation.addAccount");
    int i = 0;
    try
    {
      i = a6b.addAccount(paramSecureUser, paramAccount, paramHashMap);
    }
    catch (AggregationException localAggregationException)
    {
      throw new CSILException("com.ffusion.csil.handlers.AccountAggregation.addAccount", jdMethod_for(localAggregationException), localAggregationException);
    }
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramAccount;
  }
  
  public static Account modifyAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.AccountAggregation.modifyAccount");
    int i = a6b.modifyAccount(paramAccount);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramAccount;
  }
  
  public static Account deleteAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.AccountAggregation.deleteAccount");
    int i = 0;
    try
    {
      i = a6b.deleteAccount(paramSecureUser, paramAccount, paramHashMap);
    }
    catch (AggregationException localAggregationException)
    {
      throw new CSILException("com.ffusion.csil.handlers.AccountAggregation.deleteAccount", jdMethod_for(localAggregationException), localAggregationException);
    }
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramAccount;
  }
  
  public static Accounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.AccountAggregation.getAccounts");
    Accounts localAccounts = (Accounts)paramHashMap2.get("ACCOUNTS");
    if (localAccounts == null)
    {
      debug("Missing required parameter: extra.'ACCOUNTS' - creating new Accounts()");
      localAccounts = new Accounts();
    }
    int i = 0;
    i = a6b.getAccounts(localAccounts, paramHashMap1);
    if (i != 0) {
      throwing(-1009, i);
    }
    return localAccounts;
  }
  
  public static Account refreshAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.AccountAggregation.refreshAccount");
    int i = 0;
    i = a6b.refreshAccount(paramAccount);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramAccount;
  }
  
  public static Transactions getTransactions(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.AccountAggregation.getTransactions");
    int i = 0;
    i = a6b.getTransactions(paramAccount);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramAccount.getTransactions();
  }
  
  public static Transactions getTransactions(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.AccountAggregation.getTransactions");
    int i = 0;
    i = a6b.getTransactions(paramAccount);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramAccount.getTransactions();
  }
  
  public static Transactions getNextTransactions(SecureUser paramSecureUser, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      Transactions localTransactions = a6b.getNextTransactions(paramSecureUser, paramAccount, paramPagingContext, paramHashMap);
      return localTransactions;
    }
    catch (AggregationException localAggregationException)
    {
      throw new CSILException(-1009, jdMethod_for(localAggregationException), localAggregationException);
    }
  }
  
  public static Transactions getPreviousTransactions(SecureUser paramSecureUser, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      Transactions localTransactions = a6b.getPreviousTransactions(paramSecureUser, paramAccount, paramPagingContext, paramHashMap);
      return localTransactions;
    }
    catch (AggregationException localAggregationException)
    {
      throw new CSILException(-1009, jdMethod_for(localAggregationException), localAggregationException);
    }
  }
  
  public static Transactions getPagedTransactions(SecureUser paramSecureUser, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      Transactions localTransactions = a6b.getPagedTransactions(paramSecureUser, paramAccount, paramPagingContext, paramHashMap);
      return localTransactions;
    }
    catch (AggregationException localAggregationException)
    {
      throw new CSILException(-1009, jdMethod_for(localAggregationException), localAggregationException);
    }
  }
  
  public static AccountSummaries getSummary(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      AccountSummaries localAccountSummaries = a6b.getSummary(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
      return localAccountSummaries;
    }
    catch (AggregationException localAggregationException)
    {
      throw new CSILException(-1009, jdMethod_for(localAggregationException), localAggregationException);
    }
  }
  
  private static int jdMethod_for(AggregationException paramAggregationException)
  {
    return paramAggregationException.getCode();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.AccountAggregation
 * JD-Core Version:    0.7.0.1
 */