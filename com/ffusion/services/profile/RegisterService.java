package com.ffusion.services.profile;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.beans.register.RegisterCategory;
import com.ffusion.beans.register.RegisterPayee;
import com.ffusion.beans.register.RegisterPayees;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.register.ServerTransaction;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Reporting;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.RegisterAdapter;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.InvalidDateTimeException;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

public class RegisterService
  implements com.ffusion.services.RegisterService
{
  private static String a = "MM/dd/yyyy HH:mm:ss";
  protected static int _numberOfDaysBeforeAutoReconcile = 30;
  
  public void initialize(HashMap paramHashMap)
  {
    try
    {
      paramHashMap.put("NUMBER_OF_DAYS_BEFORE_AUTO_RECONCILE", new Integer(_numberOfDaysBeforeAutoReconcile));
      RegisterAdapter.initialize(paramHashMap);
    }
    catch (Exception localException)
    {
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      localException.printStackTrace(localPrintWriter);
      DebugLog.log(Level.SEVERE, localStringWriter.toString());
      return;
    }
    DebugLog.log(Level.INFO, "Register Service initialized");
  }
  
  public void addRegisterTransaction(RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.addRegisterTransaction(paramRegisterTransaction, paramSecureUser);
  }
  
  public void addRegisterTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.addRegisterTransactions(paramRegisterTransactions, paramSecureUser);
  }
  
  public void addUpdateRegisterTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.addUpdateRegisterTransactions(paramRegisterTransactions, paramSecureUser);
  }
  
  public void addBankTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.addBankTransactions(paramRegisterTransactions, paramSecureUser);
  }
  
  public void deleteOldUnreconciledTransactions(int paramInt, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.deleteOldUnreconciledRegisterTransactions(paramSecureUser, paramInt);
  }
  
  public void deleteRegisterTransaction(RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.deleteRegisterTransaction(paramRegisterTransaction);
  }
  
  public void deleteRegisterTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.deleteRegisterTransactions(paramRegisterTransactions);
  }
  
  public void deleteRegisterTransactionsByServerTID(ArrayList paramArrayList, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.deleteRegisterTransactionsByServerTID(paramArrayList);
  }
  
  public void getRegisterTransaction(RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.getRegisterTransaction(paramRegisterTransaction, paramSecureUser);
  }
  
  public void getRegisterTransactions(RegisterTransactions paramRegisterTransactions, DateTime paramDateTime1, DateTime paramDateTime2, boolean paramBoolean, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.getRegisterTransactions(paramRegisterTransactions, paramDateTime1, paramDateTime2, paramBoolean, paramSecureUser);
  }
  
  public void modifyRegisterTransaction(RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.modifyRegisterTransaction(paramRegisterTransaction, paramSecureUser);
  }
  
  public void modifyRegisterTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.modifyRegisterTransactions(paramRegisterTransactions, paramSecureUser);
  }
  
  public void addRegisterCategory(RegisterCategory paramRegisterCategory, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.addRegisterCategory(paramRegisterCategory, paramSecureUser);
  }
  
  public void addSrvrTranRegisterCategory(ArrayList paramArrayList)
    throws ProfileException
  {
    RegisterAdapter.addSrvrTranRegisterCategory(paramArrayList);
  }
  
  public void modifySrvrTranRegisterCategory(ServerTransaction paramServerTransaction)
    throws ProfileException
  {
    RegisterAdapter.modifySrvrTranRegisterCategory(paramServerTransaction);
  }
  
  public void deleteSrvrTranRegisterCategory(String paramString, boolean paramBoolean, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.deleteSrvrTranRegisterCategory(paramString, paramBoolean);
  }
  
  public void deleteRegisterCategory(RegisterCategory paramRegisterCategory, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.deleteRegisterCategory(paramRegisterCategory, paramSecureUser);
  }
  
  public void getRegisterCategories(RegisterCategories paramRegisterCategories, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.getRegisterCategories(paramRegisterCategories, paramSecureUser);
  }
  
  public void getRegisterDefaultCategories(RegisterCategories paramRegisterCategories, SecureUser paramSecureUser)
    throws ProfileException
  {
    paramRegisterCategories.setLocale(paramSecureUser.getLocale());
    RegisterAdapter.getRegisterDefaultCategories(paramRegisterCategories);
  }
  
  public void getRegisterCategoriesForPayments(Payments paramPayments, RecPayments paramRecPayments, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.getRegisterCategoriesForPayments(paramPayments, paramRecPayments, paramSecureUser);
  }
  
  public void getRegisterCategoriesForTransfers(Transfers paramTransfers, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.getRegisterCategoriesForTransfers(paramTransfers, paramSecureUser);
  }
  
  public void modifyRegisterCategory(RegisterCategory paramRegisterCategory, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.modifyRegisterCategory(paramRegisterCategory, paramSecureUser);
  }
  
  public void addRegisterPayee(RegisterPayee paramRegisterPayee, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.addRegisterPayee(paramRegisterPayee, paramSecureUser);
  }
  
  public void deleteRegisterPayee(RegisterPayee paramRegisterPayee, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.deleteRegisterPayee(paramRegisterPayee);
  }
  
  public void getRegisterPayees(RegisterPayees paramRegisterPayees, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.getRegisterPayees(paramRegisterPayees, paramSecureUser);
  }
  
  public void modifyRegisterPayee(RegisterPayee paramRegisterPayee, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.modifyRegisterPayee(paramRegisterPayee);
  }
  
  public void transferDefaultCategories(SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.transferDefaultCategories(paramSecureUser);
  }
  
  public void reassignTransactionsCategory(String paramString1, String paramString2, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.reassignTransactionsCategory(paramString1, paramString2, paramSecureUser);
  }
  
  public void reconcileRegisterTransactions(RegisterTransactions paramRegisterTransactions1, RegisterTransactions paramRegisterTransactions2, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.reconcileRegisterTransactions(paramRegisterTransactions1, paramRegisterTransactions2, paramSecureUser);
  }
  
  public void modifyRegisterAccountData(Account paramAccount, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.modifyRegisterAccountData(paramAccount, paramSecureUser);
  }
  
  public void modifyRegisterAccountsData(Accounts paramAccounts, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.modifyRegisterAccountsData(paramAccounts, paramSecureUser);
  }
  
  public void setDefaultRegisterAccount(String paramString, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.setDefaultRegisterAccount(paramString, paramSecureUser);
  }
  
  public void addRecurringTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.addRecurringTransactions(paramRegisterTransactions, paramSecureUser);
  }
  
  public void deleteOldAndFailedTransactions(ArrayList paramArrayList, int paramInt, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.deleteRegisterTransactionsByServerTID(paramArrayList);
    RegisterAdapter.deleteOldUnreconciledRegisterTransactions(paramSecureUser, paramInt);
  }
  
  public void deleteRegisterTransactionsByTranId(String paramString, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.deleteRegisterTransactionsByTranId(paramString);
  }
  
  public void getRegisterTransactionsByTranId(RegisterTransactions paramRegisterTransactions, String paramString, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.getRegisterTransactionsByTranId(paramRegisterTransactions, paramString, paramSecureUser);
  }
  
  public void modifyRegisterTransactionsByTranId(RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterAdapter.modifyRegisterTransactionsByTranId(paramRegisterTransaction, paramSecureUser);
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "RegisterService.getReportData";
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    try
    {
      Reporting.calculateDateRange(paramSecureUser, null, paramReportCriteria, localHashMap1, localHashMap2, paramHashMap);
      paramHashMap.put("StartDates", localHashMap1);
      paramHashMap.put("EndDates", localHashMap2);
    }
    catch (CSILException localCSILException)
    {
      String str2 = "Unable to calculate the date ranges. A report cannot be run.";
      DebugLog.log(str2);
      if (localCSILException.getCode() == -1009) {
        throw new ProfileException(localCSILException, str1, localCSILException.getServiceError(), str2);
      }
      throw new ProfileException(localCSILException, str1, localCSILException.getCode(), str2);
    }
    return RegisterAdapter.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
  }
  
  public RegisterTransactions getPagedTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws ProfileException
  {
    paramPagingContext.setDirection("first");
    a(paramSecureUser, paramPagingContext, paramHashMap);
    return RegisterAdapter.getPagedTransactions(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public RegisterTransactions getNextTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws ProfileException
  {
    paramPagingContext.setDirection("next");
    a(paramSecureUser, paramPagingContext, paramHashMap);
    return RegisterAdapter.getNextTransactions(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public RegisterTransactions getPreviousTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws ProfileException
  {
    paramPagingContext.setDirection("previous");
    a(paramSecureUser, paramPagingContext, paramHashMap);
    return RegisterAdapter.getPreviousTransactions(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public RegisterTransactions getLastTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws ProfileException
  {
    paramPagingContext.setDirection("last");
    a(paramSecureUser, paramPagingContext, paramHashMap);
    return RegisterAdapter.getLastTransactions(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public RegisterTransaction getLastMatchingTransaction(SecureUser paramSecureUser, RegisterTransaction paramRegisterTransaction, HashMap paramHashMap)
    throws ProfileException
  {
    return RegisterAdapter.getLastMatchingTransaction(paramSecureUser, paramRegisterTransaction, paramHashMap);
  }
  
  private static void a(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "calculateDateTimesForPagedData";
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    try
    {
      Reporting.calculateDateRange(paramSecureUser, null, (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria"), localHashMap1, localHashMap2, paramHashMap);
      Iterator localIterator;
      Object localObject2;
      String str2;
      ProfileException localProfileException2;
      Object localObject3;
      if (((String)paramPagingContext.getMap().get("TransactionsFilter")).equals("Current"))
      {
        localIterator = localHashMap2.keySet().iterator();
        localObject1 = Calendar.getInstance();
        localObject2 = "HH:mm";
        str2 = localReportCriteria.getSearchCriteria().getProperty("EndTime");
        if ((str2 != null) && (str2.trim().length() != 0) && (!str2.trim().equalsIgnoreCase((String)localObject2)))
        {
          DateTime localDateTime1 = null;
          try
          {
            Locale localLocale = (Locale)paramHashMap.get("Locale");
            if (localLocale == null) {
              localLocale = Locale.getDefault();
            }
            localDateTime1 = new DateTime(str2, localLocale, (String)localObject2);
          }
          catch (InvalidDateTimeException localInvalidDateTimeException1)
          {
            localProfileException2 = new ProfileException(6000, localInvalidDateTimeException1);
            DebugLog.throwing("Unable to parse start time.", localProfileException2);
            throw localProfileException2;
          }
          ((Calendar)localObject1).set(11, localDateTime1.get(11));
          ((Calendar)localObject1).set(12, localDateTime1.get(12));
          ((Calendar)localObject1).set(13, localDateTime1.get(13));
          ((Calendar)localObject1).set(14, localDateTime1.get(14));
        }
        else
        {
          ((Calendar)localObject1).set(11, 23);
          ((Calendar)localObject1).set(12, 59);
          ((Calendar)localObject1).set(13, 59);
          ((Calendar)localObject1).set(14, 999);
        }
        ((Calendar)localObject1).get(14);
        try
        {
          if ((localReportCriteria.getSearchCriteria().getProperty("EndDate") == null) || (DateFormatUtil.getFormatter(a).parse(localReportCriteria.getSearchCriteria().getProperty("EndDate")).after(((Calendar)localObject1).getTime())))
          {
            while (localIterator.hasNext()) {
              localHashMap2.put(localIterator.next(), new DateTime(((Calendar)localObject1).getTime(), paramSecureUser.getLocale()));
            }
            paramPagingContext.setEndDate((Calendar)localObject1);
            localReportCriteria.getSearchCriteria().setProperty("EndDate", DateFormatUtil.getFormatter(a).format(((Calendar)localObject1).getTime()));
          }
        }
        catch (ParseException localParseException1)
        {
          localObject3 = new ProfileException(6001, localParseException1);
          DebugLog.throwing("Unable to parse end time.", (Throwable)localObject3);
          throw ((Throwable)localObject3);
        }
      }
      else if (((String)paramPagingContext.getMap().get("TransactionsFilter")).equals("Future"))
      {
        localIterator = localHashMap1.keySet().iterator();
        localObject1 = "HH:mm";
        localObject2 = Calendar.getInstance();
        str2 = localReportCriteria.getSearchCriteria().getProperty("StartTime");
        if ((str2 != null) && (str2.trim().length() != 0) && (!str2.trim().equalsIgnoreCase((String)localObject1)))
        {
          DateTime localDateTime2 = null;
          try
          {
            localObject3 = (Locale)paramHashMap.get("Locale");
            if (localObject3 == null) {
              localObject3 = Locale.getDefault();
            }
            localDateTime2 = new DateTime(str2, (Locale)localObject3, (String)localObject1);
          }
          catch (InvalidDateTimeException localInvalidDateTimeException2)
          {
            localProfileException2 = new ProfileException(6000, localInvalidDateTimeException2);
            DebugLog.throwing("Unable to parse start time.", localProfileException2);
            throw localProfileException2;
          }
          ((Calendar)localObject2).set(11, localDateTime2.get(11));
          ((Calendar)localObject2).set(12, localDateTime2.get(12));
          ((Calendar)localObject2).set(13, localDateTime2.get(13));
          ((Calendar)localObject2).set(14, localDateTime2.get(14));
          ((Calendar)localObject2).add(5, 1);
        }
        else
        {
          ((Calendar)localObject2).set(14, 0);
          ((Calendar)localObject2).set(13, 0);
          ((Calendar)localObject2).set(12, 0);
          ((Calendar)localObject2).set(11, 0);
          ((Calendar)localObject2).add(5, 1);
        }
        try
        {
          if ((localReportCriteria.getSearchCriteria().getProperty("StartDate") == null) || (DateFormatUtil.getFormatter(a).parse(localReportCriteria.getSearchCriteria().getProperty("StartDate")).before(((Calendar)localObject2).getTime())))
          {
            while (localIterator.hasNext()) {
              localHashMap1.put(localIterator.next(), new DateTime(((Calendar)localObject2).getTime(), paramSecureUser.getLocale()));
            }
            paramPagingContext.setStartDate((Calendar)localObject2);
            localReportCriteria.getSearchCriteria().setProperty("StartDate", DateFormatUtil.getFormatter(a).format(((Calendar)localObject2).getTime()));
          }
        }
        catch (ParseException localParseException2)
        {
          ProfileException localProfileException1 = new ProfileException(6001, localParseException2);
          DebugLog.throwing("Unable to parse end time.", localProfileException1);
          throw localProfileException1;
        }
      }
      paramHashMap.put("StartDates", localHashMap1);
      paramHashMap.put("EndDates", localHashMap2);
    }
    catch (CSILException localCSILException)
    {
      Object localObject1 = "Unable to calculate the date ranges. A report cannot be run.";
      DebugLog.log((String)localObject1);
      if (localCSILException.getCode() == -1009) {
        throw new ProfileException(localCSILException, str1, localCSILException.getServiceError(), (String)localObject1);
      }
      throw new ProfileException(localCSILException, str1, localCSILException.getCode(), (String)localObject1);
    }
  }
  
  public void autoreconcileOldTransactions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    RegisterAdapter.autoreconcileOldTransactions(paramSecureUser, _numberOfDaysBeforeAutoReconcile, paramHashMap);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.profile.RegisterService
 * JD-Core Version:    0.7.0.1
 */