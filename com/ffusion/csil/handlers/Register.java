package com.ffusion.csil.handlers;

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
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.RegisterService;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class Register
  extends Initialize
{
  private static final String a69 = "Register";
  private static RegisterService a7a = null;
  
  public static void initialize(HashMap paramHashMap)
  {
    String str = "RegisterHandler.initialize";
    try
    {
      HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Register", str, 20107);
      a7a = (RegisterService)HandlerUtil.instantiateService(localHashMap, str, 20107);
      a7a.initialize(localHashMap);
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, "Register.initialize: " + localException.toString());
    }
  }
  
  public static Object getService()
  {
    return a7a;
  }
  
  public static RegisterTransaction addRegisterTransaction(SecureUser paramSecureUser, RegisterTransaction paramRegisterTransaction, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.addRegisterTransaction";
    try
    {
      a7a.addRegisterTransaction(paramRegisterTransaction, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterTransaction;
  }
  
  public static RegisterTransactions addRegisterTransactions(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.addRegisterTransactions";
    try
    {
      a7a.addRegisterTransactions(paramRegisterTransactions, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterTransactions;
  }
  
  public static RegisterTransactions addUpdateRegisterTransactions(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.addUpdateRegisterTransactions";
    try
    {
      a7a.addUpdateRegisterTransactions(paramRegisterTransactions, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterTransactions;
  }
  
  public static RegisterTransactions addBankTransactions(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.addBankTransactions";
    try
    {
      a7a.addBankTransactions(paramRegisterTransactions, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterTransactions;
  }
  
  public static void deleteOldUnreconciledTransactions(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.deleteOldUnreconciledTransactions";
    try
    {
      a7a.deleteOldUnreconciledTransactions(paramInt, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static RegisterTransaction deleteRegisterTransaction(SecureUser paramSecureUser, RegisterTransaction paramRegisterTransaction, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.deleteOldAndFailedTransactions";
    try
    {
      a7a.deleteRegisterTransaction(paramRegisterTransaction, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterTransaction;
  }
  
  public static RegisterTransactions deleteRegisterTransactions(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.deleteRegisterTransactions";
    try
    {
      a7a.deleteRegisterTransactions(paramRegisterTransactions, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterTransactions;
  }
  
  public static void deleteRegisterTransactionsByServerTID(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.deleteRegisterTransactionsByServerTID";
    try
    {
      a7a.deleteRegisterTransactionsByServerTID(paramArrayList, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static RegisterTransaction getRegisterTransaction(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.getRegisterTransaction";
    RegisterTransaction localRegisterTransaction = null;
    if ((paramHashMap == null) || (paramHashMap.get("REGISTERTRANSACTION") == null))
    {
      debug("Missing required parameter: extra.'TRANSACTION'");
      localRegisterTransaction = new RegisterTransaction();
      localRegisterTransaction.setReferenceNumber(paramString2);
      localRegisterTransaction.set("ACCOUNT", paramString1);
    }
    else
    {
      localRegisterTransaction = (RegisterTransaction)paramHashMap.get("REGISTERTRANSACTION");
    }
    try
    {
      a7a.getRegisterTransaction(localRegisterTransaction, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localRegisterTransaction;
  }
  
  public static RegisterTransactions getRegisterTransactions(SecureUser paramSecureUser, DateTime paramDateTime1, DateTime paramDateTime2, String paramString1, String paramString2, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.getRegisterTransactions";
    RegisterTransactions localRegisterTransactions = null;
    if ((paramHashMap == null) || (paramHashMap.get("REGISTERTRANSACTIONS") == null))
    {
      debug("Missing required parameter: extra.'TRANSACTIONS'");
      localRegisterTransactions = new RegisterTransactions();
      RegisterTransaction localRegisterTransaction = localRegisterTransactions.create();
      localRegisterTransaction.setReferenceNumber(paramString2);
      localRegisterTransaction.set("ACCOUNT", paramString1);
    }
    else
    {
      localRegisterTransactions = (RegisterTransactions)paramHashMap.get("REGISTERTRANSACTIONS");
    }
    try
    {
      a7a.getRegisterTransactions(localRegisterTransactions, paramDateTime1, paramDateTime2, paramBoolean, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localRegisterTransactions;
  }
  
  public static RegisterTransaction modifyRegisterTransaction(SecureUser paramSecureUser, RegisterTransaction paramRegisterTransaction, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.modifyRegisterTransaction";
    try
    {
      a7a.modifyRegisterTransaction(paramRegisterTransaction, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterTransaction;
  }
  
  public static RegisterTransactions modifyRegisterTransactions(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.modifyRegisterTransactions";
    try
    {
      a7a.modifyRegisterTransactions(paramRegisterTransactions, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterTransactions;
  }
  
  public static RegisterCategory addRegisterCategory(SecureUser paramSecureUser, RegisterCategory paramRegisterCategory, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.addRegisterCategory";
    try
    {
      a7a.addRegisterCategory(paramRegisterCategory, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterCategory;
  }
  
  public static ArrayList addSrvrTranRegisterCategory(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.addSrvrTranRegisterCategory";
    try
    {
      a7a.addSrvrTranRegisterCategory(paramArrayList);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramArrayList;
  }
  
  public static ServerTransaction modifySrvrTranRegisterCategory(SecureUser paramSecureUser, ServerTransaction paramServerTransaction, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.addSrvrTranRegisterCategory";
    try
    {
      a7a.modifySrvrTranRegisterCategory(paramServerTransaction);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramServerTransaction;
  }
  
  public static void deleteSrvrTranRegisterCategory(SecureUser paramSecureUser, String paramString, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.deleteSrvrTranRegisterCategory";
    try
    {
      a7a.deleteSrvrTranRegisterCategory(paramString, paramBoolean, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static RegisterCategory deleteRegisterCategory(SecureUser paramSecureUser, RegisterCategory paramRegisterCategory, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.deleteRegisterCategory";
    try
    {
      a7a.deleteRegisterCategory(paramRegisterCategory, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterCategory;
  }
  
  public static RegisterCategories getRegisterCategories(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.getRegisterCategories";
    RegisterCategories localRegisterCategories = null;
    if ((paramHashMap == null) || (paramHashMap.get("REGISTERCATEGORIES") == null))
    {
      debug("Missing required parameter: extra.'CATEGORIES'");
      localRegisterCategories = new RegisterCategories();
    }
    else
    {
      localRegisterCategories = (RegisterCategories)paramHashMap.get("REGISTERCATEGORIES");
    }
    try
    {
      a7a.getRegisterCategories(localRegisterCategories, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localRegisterCategories;
  }
  
  public static RegisterCategories getRegisterDefaultCategories(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.getRegisterDefaultCategories";
    RegisterCategories localRegisterCategories = null;
    if ((paramHashMap == null) || (paramHashMap.get("REGISTERCATEGORIES") == null))
    {
      debug("Missing required parameter: extra.'CATEGORIES'");
      localRegisterCategories = new RegisterCategories();
    }
    else
    {
      localRegisterCategories = (RegisterCategories)paramHashMap.get("REGISTERCATEGORIES");
    }
    try
    {
      a7a.getRegisterDefaultCategories(localRegisterCategories, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localRegisterCategories;
  }
  
  public static Payments getRegisterCategoriesForPayments(SecureUser paramSecureUser, Payments paramPayments, RecPayments paramRecPayments, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.getRegisterCategoriesForPayments";
    try
    {
      a7a.getRegisterCategoriesForPayments(paramPayments, paramRecPayments, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramPayments;
  }
  
  public static Transfers getRegisterCategoriesForTransfers(SecureUser paramSecureUser, Transfers paramTransfers, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.getRegisterCategoriesForTransfers";
    try
    {
      a7a.getRegisterCategoriesForTransfers(paramTransfers, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramTransfers;
  }
  
  public static RegisterCategory modifyRegisterCategory(SecureUser paramSecureUser, RegisterCategory paramRegisterCategory, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.modifyRegisterCategory";
    try
    {
      a7a.modifyRegisterCategory(paramRegisterCategory, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterCategory;
  }
  
  public static RegisterPayee addRegisterPayee(SecureUser paramSecureUser, RegisterPayee paramRegisterPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.modifyRegisterCategory";
    try
    {
      a7a.addRegisterPayee(paramRegisterPayee, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterPayee;
  }
  
  public static RegisterPayee deleteRegisterPayee(SecureUser paramSecureUser, RegisterPayee paramRegisterPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.modifyRegisterCategory";
    try
    {
      a7a.deleteRegisterPayee(paramRegisterPayee, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterPayee;
  }
  
  public static RegisterPayees getRegisterPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.getRegisterPayees";
    RegisterPayees localRegisterPayees = null;
    if ((paramHashMap == null) || (paramHashMap.get("REGISTERPAYEES") == null))
    {
      debug("Missing required parameter: extra.'REGISTERCATEGORIES'");
      localRegisterPayees = new RegisterPayees();
    }
    else
    {
      localRegisterPayees = (RegisterPayees)paramHashMap.get("REGISTERPAYEES");
    }
    try
    {
      a7a.getRegisterPayees(localRegisterPayees, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localRegisterPayees;
  }
  
  public static RegisterPayee modifyRegisterPayee(SecureUser paramSecureUser, RegisterPayee paramRegisterPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.modifyRegisterPayee";
    try
    {
      a7a.modifyRegisterPayee(paramRegisterPayee, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterPayee;
  }
  
  public static void transferDefaultCategories(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.modifyRegisterPayee";
    try
    {
      a7a.transferDefaultCategories(paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void reassignTransactionsCategory(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.reassignTransactionsCategory";
    try
    {
      a7a.reassignTransactionsCategory(paramString1, paramString2, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void reconcileRegisterTransactions(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions1, RegisterTransactions paramRegisterTransactions2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.reconcileRegisterTransactions";
    try
    {
      a7a.reconcileRegisterTransactions(paramRegisterTransactions1, paramRegisterTransactions2, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Account modifyRegisterAccountData(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.modifyRegisterAccountData";
    try
    {
      a7a.modifyRegisterAccountData(paramAccount, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramAccount;
  }
  
  public static Accounts modifyRegisterAccountsData(SecureUser paramSecureUser, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.modifyRegisterAccountsData";
    try
    {
      a7a.modifyRegisterAccountsData(paramAccounts, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramAccounts;
  }
  
  public static void setDefaultRegisterAccount(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.setDefaultRegisterAccount";
    try
    {
      a7a.setDefaultRegisterAccount(paramString, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static RegisterTransactions addRecurringTransactions(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.addRecurringTransactions";
    try
    {
      a7a.addRecurringTransactions(paramRegisterTransactions, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramRegisterTransactions;
  }
  
  public static void deleteOldAndFailedTransactions(SecureUser paramSecureUser, ArrayList paramArrayList, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.deleteOldAndFailedTransactions";
    try
    {
      a7a.deleteOldAndFailedTransactions(paramArrayList, paramInt, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteRegisterTransactionsByTranId(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.deleteRegisterTransactionsByServerTID";
    try
    {
      a7a.deleteRegisterTransactionsByTranId(paramString, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static RegisterTransactions getRegisterTransactionsByTranId(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.getRegisterTransactionsByTranId";
    RegisterTransactions localRegisterTransactions = null;
    if ((paramHashMap == null) || (paramHashMap.get("REGISTERTRANSACTIONS") == null))
    {
      debug("Missing required parameter: extra.'TRANSACTIONS'");
      localRegisterTransactions = new RegisterTransactions();
    }
    else
    {
      localRegisterTransactions = (RegisterTransactions)paramHashMap.get("REGISTERTRANSACTIONS");
    }
    try
    {
      a7a.getRegisterTransactionsByTranId(localRegisterTransactions, paramString, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localRegisterTransactions;
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.getReportData";
    IReportResult localIReportResult = null;
    try
    {
      localIReportResult = a7a.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localIReportResult;
  }
  
  public static void modifyRegisterTransactionsByTranId(SecureUser paramSecureUser, RegisterTransaction paramRegisterTransaction, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.modifyRegisterTransactionsByTranId";
    try
    {
      a7a.modifyRegisterTransactionsByTranId(paramRegisterTransaction, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static RegisterTransactions getPagedTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.getPagedTransactions";
    try
    {
      return a7a.getPagedTransactions(paramSecureUser, paramPagingContext, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static RegisterTransactions getNextTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.getNextTransactions";
    try
    {
      return a7a.getNextTransactions(paramSecureUser, paramPagingContext, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static RegisterTransactions getPreviousTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.getPreviousTransactions";
    try
    {
      return a7a.getPreviousTransactions(paramSecureUser, paramPagingContext, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static RegisterTransactions getLastTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.getLastTransactions";
    try
    {
      return a7a.getLastTransactions(paramSecureUser, paramPagingContext, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static RegisterTransaction getLastMatchingTransaction(SecureUser paramSecureUser, RegisterTransaction paramRegisterTransaction, HashMap paramHashMap)
    throws CSILException
  {
    String str = "RegisterHandler.getLastMatchingTransactions";
    try
    {
      return a7a.getLastMatchingTransaction(paramSecureUser, paramRegisterTransaction, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void autoreconcileOldTransactions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.autoreconcileOldTransactions";
    try
    {
      a7a.autoreconcileOldTransactions(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(22000, localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Register
 * JD-Core Version:    0.7.0.1
 */