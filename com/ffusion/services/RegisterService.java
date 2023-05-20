package com.ffusion.services;

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
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.PagingContext;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface RegisterService
{
  public abstract void initialize(HashMap paramHashMap);
  
  public abstract void addRegisterTransaction(RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void addRegisterTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void addUpdateRegisterTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void addBankTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void deleteOldUnreconciledTransactions(int paramInt, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void deleteRegisterTransaction(RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void deleteRegisterTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void deleteRegisterTransactionsByServerTID(ArrayList paramArrayList, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void getRegisterTransaction(RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void getRegisterTransactions(RegisterTransactions paramRegisterTransactions, DateTime paramDateTime1, DateTime paramDateTime2, boolean paramBoolean, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void modifyRegisterTransaction(RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void modifyRegisterTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void addRegisterCategory(RegisterCategory paramRegisterCategory, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void addSrvrTranRegisterCategory(ArrayList paramArrayList)
    throws ProfileException;
  
  public abstract void modifySrvrTranRegisterCategory(ServerTransaction paramServerTransaction)
    throws ProfileException;
  
  public abstract void deleteSrvrTranRegisterCategory(String paramString, boolean paramBoolean, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void deleteRegisterCategory(RegisterCategory paramRegisterCategory, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void getRegisterCategories(RegisterCategories paramRegisterCategories, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void getRegisterDefaultCategories(RegisterCategories paramRegisterCategories, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void getRegisterCategoriesForPayments(Payments paramPayments, RecPayments paramRecPayments, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void getRegisterCategoriesForTransfers(Transfers paramTransfers, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void modifyRegisterCategory(RegisterCategory paramRegisterCategory, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void addRegisterPayee(RegisterPayee paramRegisterPayee, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void deleteRegisterPayee(RegisterPayee paramRegisterPayee, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void getRegisterPayees(RegisterPayees paramRegisterPayees, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void modifyRegisterPayee(RegisterPayee paramRegisterPayee, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void transferDefaultCategories(SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void reassignTransactionsCategory(String paramString1, String paramString2, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void reconcileRegisterTransactions(RegisterTransactions paramRegisterTransactions1, RegisterTransactions paramRegisterTransactions2, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void modifyRegisterAccountData(Account paramAccount, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void modifyRegisterAccountsData(Accounts paramAccounts, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void setDefaultRegisterAccount(String paramString, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void addRecurringTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void deleteOldAndFailedTransactions(ArrayList paramArrayList, int paramInt, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void deleteRegisterTransactionsByTranId(String paramString, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void getRegisterTransactionsByTranId(RegisterTransactions paramRegisterTransactions, String paramString, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract void modifyRegisterTransactionsByTranId(RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException;
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract RegisterTransactions getPagedTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract RegisterTransactions getNextTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract RegisterTransactions getPreviousTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract RegisterTransactions getLastTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract RegisterTransaction getLastMatchingTransaction(SecureUser paramSecureUser, RegisterTransaction paramRegisterTransaction, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void autoreconcileOldTransactions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.RegisterService
 * JD-Core Version:    0.7.0.1
 */