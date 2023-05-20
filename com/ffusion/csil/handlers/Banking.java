package com.ffusion.csil.handlers;

import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.beans.FundsTransactionTemplates;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.accounts.ExtendedAccountSummaries;
import com.ffusion.beans.accounts.FixedDepositInstrument;
import com.ffusion.beans.accounts.FixedDepositInstruments;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.TransactionTypeInfo;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.TransferBatch;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.register.ServerTransaction;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.tw.TWTransactions;
import com.ffusion.beans.util.RegisterUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Register;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.AccountService3;
import com.ffusion.services.Banking3;
import com.ffusion.services.Banking4;
import com.ffusion.services.Banking7;
import com.ffusion.services.Banking8;
import com.ffusion.services.BankingException;
import com.ffusion.services.BillPay;
import com.ffusion.tw.TransactionWarehouse;
import com.ffusion.util.FFIStringTokenizer;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

public class Banking
  extends Initialize
{
  private static Entitlement a7n = new Entitlement("Account Register", null, null);
  private static TransactionWarehouse a7s = null;
  private static final String a7q = "Banking";
  private static String a7o;
  private static Banking8 a7r = null;
  private static boolean a7p;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.initialize";
    debug("com.ffusion.handlers.Banking.initialize");
    a7s = new TransactionWarehouse(paramHashMap);
    a7o = HandlerUtil.getGlobalPageSize(paramHashMap);
    a7p = HandlerUtil.useCompatibilityMode(paramHashMap);
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Banking", str1, 20107);
    a7r = (Banking8)HandlerUtil.instantiateService(localHashMap, str1, 20107);
    String str2 = null;
    if (localHashMap.get("INIT_URL") != null) {
      str2 = (String)localHashMap.get("INIT_URL");
    }
    try
    {
      a7r.initialize(str2);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(20107, localException);
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return null;
  }
  
  public static SecureUser signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.signOn");
    checkExtra(paramHashMap);
    if (paramSecureUser == null) {
      paramSecureUser = new SecureUser();
    }
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    if ((localBanking instanceof Banking4))
    {
      if (!((Banking4)localBanking).signOn(paramSecureUser, paramString1, paramString2)) {
        throwing(-1009, 100);
      }
    }
    else
    {
      int i = localBanking.signOn(paramString1, paramString2);
      if (i != 0) {
        throwing(-1009, i);
      }
    }
    return paramSecureUser;
  }
  
  public static SecureUser signOff(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.signOff");
    checkExtra(paramHashMap);
    if (paramSecureUser == null) {
      paramSecureUser = new SecureUser();
    }
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = 0;
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramSecureUser;
  }
  
  public static SecureUser changePIN(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.changePIN");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = 0;
    i = localBanking.changePIN(paramString1, paramString2);
    if (i != 0) {
      throwing(-1009, -1010);
    }
    return paramSecureUser;
  }
  
  public static Accounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.getAccounts");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    Accounts localAccounts = (Accounts)paramHashMap.get("ACCOUNTS");
    if (localAccounts == null)
    {
      debug("Missing required parameter: extra.'ACCOUNTS'");
      localAccounts = new Accounts();
    }
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = 0;
    localAccounts.setSecureUser(paramSecureUser);
    i = localBanking.getAccounts(localAccounts);
    if (i != 0) {
      throwing(-1009, i);
    }
    return localAccounts;
  }
  
  public static Transactions getTransactions(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.getTransactions");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = 0;
    i = localBanking.getTransactions(paramAccount, paramCalendar1, paramCalendar2);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramAccount.getTransactions();
  }
  
  public static Transactions getTransactions(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.getTransactions");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = 0;
    i = localBanking.getTransactions(paramAccount, paramCalendar1, paramCalendar2);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramAccount.getTransactions();
  }
  
  public static Transactions getTransactions(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.getTransactions");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = 0;
    i = localBanking.getTransactions(paramAccount);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramAccount.getTransactions();
  }
  
  public static Transfers getTransfers(SecureUser paramSecureUser, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.getTransfers");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    Transfers localTransfers = (Transfers)paramHashMap.get("TRANSFERS");
    if (localTransfers == null)
    {
      debug("Missing required parameter: extra.'TRANSFERS' - creating new Transfers()");
      localTransfers = new Transfers();
    }
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = 0;
    i = localBanking.getTransfers(paramAccounts, localTransfers);
    if (i != 0) {
      throwing(-1009, i);
    }
    return localTransfers;
  }
  
  public static RecTransfers getRecTransfers(SecureUser paramSecureUser, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.getRecTransfers");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    RecTransfers localRecTransfers = (RecTransfers)paramHashMap.get("RECTRANSFERS");
    if (localRecTransfers == null)
    {
      debug("Missing required parameter: extra.'RECTRANSFERS' - creating new RecTransfers()");
      localRecTransfers = new RecTransfers();
    }
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = 0;
    i = localBanking.getRecTransfers(paramAccounts, localRecTransfers);
    if (i != 0) {
      throwing(-1009, i);
    }
    return localRecTransfers;
  }
  
  public static RecTransfer getRecTransferById(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.getRecTransferById");
    Banking3 localBanking3 = getBanking3Service(paramHashMap);
    if ((localBanking3 == null) || (!(localBanking3 instanceof Banking8)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    RecTransfer localRecTransfer = null;
    try
    {
      localRecTransfer = ((Banking8)localBanking3).getRecTransferByID(paramSecureUser, paramString, paramAccounts, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
    return localRecTransfer;
  }
  
  public static Transfer sendTransfer(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.sendTransfer");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    Account localAccount1 = (Account)paramHashMap.get("FROMACCOUNT");
    if (localAccount1 == null)
    {
      debug("Missing required parameter: extra.'FROMACCOUNT' - using transfer.getFromAccount()");
      localAccount1 = paramTransfer.getFromAccount();
    }
    Account localAccount2 = (Account)paramHashMap.get("TOACCOUNT");
    if (localAccount2 == null)
    {
      debug("Missing required parameter: extra.'TOACCOUNT' - using transfer.getToAccount()");
      localAccount2 = paramTransfer.getToAccount();
    }
    int i = localBanking.sendTransfer(localAccount1, localAccount2, paramTransfer);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a7n)) && ((RegisterUtil.isRegisterEnabled(localAccount1)) || (RegisterUtil.isRegisterEnabled(localAccount2))))
    {
      String str = (String)paramTransfer.get("REGISTER_CATEGORY_ID");
      if (str != null)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(new ServerTransaction("tx_" + paramTransfer.getID(), null, Integer.parseInt(str)));
        Register.addSrvrTranRegisterCategory(paramSecureUser, localArrayList, paramHashMap);
      }
    }
    return paramTransfer;
  }
  
  public static TransferBatch addTransferBatch(SecureUser paramSecureUser, TransferBatch paramTransferBatch, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.addTransferBatch");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof Banking8)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = ((Banking8)localBanking).addTransferBatch(paramTransferBatch);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a7n)) && (paramTransferBatch.getTransfers() != null))
    {
      Iterator localIterator = paramTransferBatch.getTransfers().iterator();
      while (localIterator.hasNext())
      {
        Transfer localTransfer = (Transfer)localIterator.next();
        if ((RegisterUtil.isRegisterEnabled(localTransfer.getFromAccount())) || (RegisterUtil.isRegisterEnabled(localTransfer.getToAccount())))
        {
          String str = (String)localTransfer.get("REGISTER_CATEGORY_ID");
          if (str != null)
          {
            ArrayList localArrayList = new ArrayList();
            localArrayList.add(new ServerTransaction("tx_" + localTransfer.getID(), null, Integer.parseInt(str)));
            Register.addSrvrTranRegisterCategory(paramSecureUser, localArrayList, paramHashMap);
          }
        }
      }
    }
    return paramTransferBatch;
  }
  
  public static TransferBatch modifyTransferBatch(SecureUser paramSecureUser, TransferBatch paramTransferBatch, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.modifyTransferBatch");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof Banking8)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = ((Banking8)localBanking).modifyTransferBatch(paramTransferBatch);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a7n)) && (paramTransferBatch.getTransfers() != null))
    {
      Iterator localIterator = paramTransferBatch.getTransfers().iterator();
      while (localIterator.hasNext())
      {
        Transfer localTransfer = (Transfer)localIterator.next();
        if ((RegisterUtil.isRegisterEnabled(localTransfer.getFromAccount())) || (RegisterUtil.isRegisterEnabled(localTransfer.getToAccount())))
        {
          String str = (String)localTransfer.get("REGISTER_CATEGORY_ID");
          if (str != null)
          {
            ServerTransaction localServerTransaction = new ServerTransaction("tx_" + localTransfer.getID(), null, Integer.parseInt(str));
            Register.modifySrvrTranRegisterCategory(paramSecureUser, localServerTransaction, paramHashMap);
          }
        }
      }
    }
    return paramTransferBatch;
  }
  
  public static TransferBatch cancelTransferBatch(SecureUser paramSecureUser, TransferBatch paramTransferBatch, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.cancelTransferBatch");
    checkExtra(paramHashMap);
    Banking8 localBanking8 = (Banking8)paramHashMap.get("SERVICE");
    if ((localBanking8 == null) || (!(localBanking8 instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = 0;
    i = localBanking8.cancelTransferBatch(paramTransferBatch);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a7n)) && (paramTransferBatch.getTransfers() != null))
    {
      Iterator localIterator = paramTransferBatch.getTransfers().iterator();
      while (localIterator.hasNext())
      {
        Transfer localTransfer = (Transfer)localIterator.next();
        if ((RegisterUtil.isRegisterEnabled(localTransfer.getFromAccount())) || (RegisterUtil.isRegisterEnabled(localTransfer.getToAccount()))) {
          Register.deleteSrvrTranRegisterCategory(paramSecureUser, "tx_" + localTransfer.getID(), false, paramHashMap);
        }
      }
    }
    return paramTransferBatch;
  }
  
  public static Transfer cancelTransfer(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.cancelTransfer");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = 0;
    i = localBanking.cancelTransfer(paramTransfer);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a7n)) && ((RegisterUtil.isRegisterEnabled(paramTransfer.getFromAccount())) || (RegisterUtil.isRegisterEnabled(paramTransfer.getToAccount())))) {
      Register.deleteSrvrTranRegisterCategory(paramSecureUser, "tx_" + paramTransfer.getID(), false, paramHashMap);
    }
    return paramTransfer;
  }
  
  public static RecTransfer sendRecTransfer(SecureUser paramSecureUser, RecTransfer paramRecTransfer, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.sendRecTransfer");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    Account localAccount1 = (Account)paramHashMap.get("FROMACCOUNT");
    if (localAccount1 == null)
    {
      debug("Missing required parameter: extra.'FROMACCOUNT' - using rectransfer.getFromAccount()");
      localAccount1 = paramRecTransfer.getFromAccount();
    }
    Account localAccount2 = (Account)paramHashMap.get("TOACCOUNT");
    if (localAccount2 == null)
    {
      debug("Missing required parameter: extra.'TOACCOUNT' - using rectransfer.getToAccount()");
      localAccount2 = paramRecTransfer.getToAccount();
    }
    int i = 0;
    i = localBanking.sendRecTransfer(localAccount1, localAccount2, paramRecTransfer);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a7n)) && ((RegisterUtil.isRegisterEnabled(localAccount1)) || (RegisterUtil.isRegisterEnabled(localAccount2))))
    {
      String str = (String)paramRecTransfer.get("REGISTER_CATEGORY_ID");
      if (str != null)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(new ServerTransaction(null, "tx_" + paramRecTransfer.getID(), Integer.parseInt(str)));
        Register.addSrvrTranRegisterCategory(paramSecureUser, localArrayList, paramHashMap);
      }
    }
    return paramRecTransfer;
  }
  
  public static RecTransfer cancelRecTransfer(SecureUser paramSecureUser, RecTransfer paramRecTransfer, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.cancelRecTransfer");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = 0;
    i = localBanking.cancelRecTransfer(paramRecTransfer);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a7n)) && ((RegisterUtil.isRegisterEnabled(paramRecTransfer.getFromAccount())) || (RegisterUtil.isRegisterEnabled(paramRecTransfer.getToAccount())))) {
      Register.deleteSrvrTranRegisterCategory(paramSecureUser, "tx_" + paramRecTransfer.getID(), true, paramHashMap);
    }
    return paramRecTransfer;
  }
  
  public static Transfer modifyTransfer(SecureUser paramSecureUser, Transfer paramTransfer1, Transfer paramTransfer2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.modifyTransfer");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    Account localAccount1 = (Account)paramHashMap.get("FROMACCOUNT");
    if (localAccount1 == null)
    {
      debug("Missing required parameter: extra.'FROMACCOUNT' - using transfer.getFromAccount()");
      localAccount1 = paramTransfer1.getFromAccount();
    }
    Account localAccount2 = (Account)paramHashMap.get("TOACCOUNT");
    if (localAccount2 == null)
    {
      debug("Missing required parameter: extra.'TOACCOUNT' - using transfer.getToAccount()");
      localAccount2 = paramTransfer1.getToAccount();
    }
    int i = 0;
    i = localBanking.modifyTransfer(localAccount1, localAccount2, paramTransfer1);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a7n)) && ((RegisterUtil.isRegisterEnabled(localAccount1)) || (RegisterUtil.isRegisterEnabled(localAccount2))))
    {
      String str = (String)paramTransfer1.get("REGISTER_CATEGORY_ID");
      if (str != null)
      {
        ServerTransaction localServerTransaction = new ServerTransaction("tx_" + paramTransfer1.getID(), null, Integer.parseInt(str));
        Register.modifySrvrTranRegisterCategory(paramSecureUser, localServerTransaction, paramHashMap);
      }
    }
    return paramTransfer1;
  }
  
  public static RecTransfer modifyRecTransfer(SecureUser paramSecureUser, RecTransfer paramRecTransfer1, RecTransfer paramRecTransfer2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.modifyRecTransfer");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    Account localAccount1 = (Account)paramHashMap.get("FROMACCOUNT");
    if (localAccount1 == null)
    {
      debug("Missing required parameter: extra.'FROMACCOUNT' - using transfer.getFromAccount()");
      localAccount1 = paramRecTransfer1.getFromAccount();
    }
    Account localAccount2 = (Account)paramHashMap.get("TOACCOUNT");
    if (localAccount2 == null)
    {
      debug("Missing required parameter: extra.'TOACCOUNT' - using transfer.getToAccount()");
      localAccount2 = paramRecTransfer1.getToAccount();
    }
    int i = 0;
    i = localBanking.modifyRecTransfer(localAccount1, localAccount2, paramRecTransfer1);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a7n)) && ((RegisterUtil.isRegisterEnabled(localAccount1)) || (RegisterUtil.isRegisterEnabled(localAccount2))))
    {
      String str = (String)paramRecTransfer1.get("REGISTER_CATEGORY_ID");
      if (str != null)
      {
        ServerTransaction localServerTransaction = new ServerTransaction(null, "tx_" + paramRecTransfer1.getID(), Integer.parseInt(str));
        Register.modifySrvrTranRegisterCategory(paramSecureUser, localServerTransaction, paramHashMap);
      }
    }
    return paramRecTransfer1;
  }
  
  public static RecTransfer skipRecTransfer(SecureUser paramSecureUser, RecTransfer paramRecTransfer, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.skipRecTransfer");
    checkExtra(paramHashMap);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHashMap.get("SERVICE");
    if ((localBanking == null) || (!(localBanking instanceof com.ffusion.services.Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    Account localAccount1 = (Account)paramHashMap.get("FROMACCOUNT");
    if (localAccount1 == null)
    {
      debug("Missing required parameter: extra.'FROMACCOUNT' - using rectransfer.getFromAccount()");
      localAccount1 = paramRecTransfer.getFromAccount();
    }
    Account localAccount2 = (Account)paramHashMap.get("TOACCOUNT");
    if (localAccount2 == null)
    {
      debug("Missing required parameter: extra.'TOACCOUNT' - using rectransfer.getToAccount()");
      localAccount2 = paramRecTransfer.getToAccount();
    }
    int i = 0;
    i = localBanking.skipRecTransfer(localAccount1, localAccount2, paramRecTransfer);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramRecTransfer;
  }
  
  public static RecTransfer getRecTransferByID(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.getRecTransferByID");
    RecTransfer localRecTransfer = null;
    try
    {
      localRecTransfer = a7r.getRecTransferByID(paramSecureUser, paramTransfer, paramHashMap);
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(22000, localBankingException.getErrorCode(), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
    return localRecTransfer;
  }
  
  public static Transfer getTransferByID(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.getTransferByID");
    Transfer localTransfer = null;
    try
    {
      localTransfer = a7r.getTransferByID(paramSecureUser, paramTransfer, paramHashMap);
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(22000, localBankingException.getErrorCode(), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
    return localTransfer;
  }
  
  public static Transfers getTransferTemplates(SecureUser paramSecureUser, Transfer paramTransfer, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.getTransferTemplates");
    Transfers localTransfers = null;
    try
    {
      checkExtra(paramHashMap);
      Banking8 localBanking8 = (Banking8)paramHashMap.get("SERVICE");
      if (localBanking8 == null) {
        localTransfers = a7r.getTransferTemplates(paramSecureUser, paramTransfer, paramPagingContext, paramHashMap);
      } else {
        localTransfers = localBanking8.getTransferTemplates(paramSecureUser, paramTransfer, paramPagingContext, paramHashMap);
      }
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(22000, localBankingException.getErrorCode(), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
    return localTransfers;
  }
  
  public static FixedDepositInstruments getFixedDepositInstruments(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      FixedDepositInstruments localFixedDepositInstruments = getBanking3Service(paramHashMap).getFixedDepositInstruments(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
      return localFixedDepositInstruments;
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, jdMethod_for(localBankingException), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static void updateFixedDepositInstrument(SecureUser paramSecureUser, FixedDepositInstrument paramFixedDepositInstrument, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      getBanking3Service(paramHashMap).updateFixedDepositInstrument(paramFixedDepositInstrument, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static Transactions getRecentTransactions(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      checkExtraParams(paramHashMap);
      Transactions localTransactions = getBanking3Service(paramHashMap).getRecentTransactions(paramAccount, paramHashMap);
      if (a7p) {
        paramAccount.setTransactions(localTransactions);
      }
      return localTransactions;
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, jdMethod_for(localBankingException), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static Transactions getRecentTransactions(SecureUser paramSecureUser, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      checkExtraParams(paramHashMap);
      Banking3 localBanking3 = getBanking3Service(paramHashMap);
      if (!(localBanking3 instanceof Banking8))
      {
        debug("The banking service must implement the Banking8 interface");
        throwing(-1009, -1010);
      }
      Transactions localTransactions = ((Banking8)localBanking3).getRecentTransactions(paramAccount, paramPagingContext, paramHashMap);
      if (a7p) {
        paramAccount.setTransactions(localTransactions);
      }
      return localTransactions;
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, jdMethod_for(localBankingException), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static Transactions getPagedTransactions(SecureUser paramSecureUser, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      checkExtraParams(paramHashMap);
      paramHashMap.put("SecureUser", paramSecureUser);
      Transactions localTransactions = getBanking3Service(paramHashMap).getPagedTransactions(paramAccount, paramPagingContext, paramHashMap);
      paramHashMap.remove("SecureUser");
      if (a7p) {
        paramAccount.setTransactions(localTransactions);
      }
      return localTransactions;
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, jdMethod_for(localBankingException), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static Transactions getNextTransactions(SecureUser paramSecureUser, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      checkExtraParams(paramHashMap);
      paramHashMap.put("SecureUser", paramSecureUser);
      Transactions localTransactions = getBanking3Service(paramHashMap).getNextTransactions(paramAccount, paramPagingContext, paramHashMap);
      paramHashMap.remove("SecureUser");
      if (a7p) {
        paramAccount.setTransactions(localTransactions);
      }
      return localTransactions;
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, jdMethod_for(localBankingException), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static Transactions getPreviousTransactions(SecureUser paramSecureUser, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      checkExtraParams(paramHashMap);
      paramHashMap.put("SecureUser", paramSecureUser);
      Transactions localTransactions = getBanking3Service(paramHashMap).getPreviousTransactions(paramAccount, paramPagingContext, paramHashMap);
      paramHashMap.remove("SecureUser");
      if (a7p) {
        paramAccount.setTransactions(localTransactions);
      }
      return localTransactions;
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, jdMethod_for(localBankingException), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static Transactions getFDInstrumentTransactions(SecureUser paramSecureUser, FixedDepositInstrument paramFixedDepositInstrument, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return getBanking3Service(paramHashMap).getFDInstrumentTransactions(paramFixedDepositInstrument, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, jdMethod_for(localBankingException), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static int getNumTransactions(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    Banking3 localBanking3 = getBanking3Service(paramHashMap);
    if (!(localBanking3 instanceof Banking8))
    {
      debug("The banking service must implement the Banking8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      return ((Banking8)localBanking3).getNumTransactions(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, jdMethod_for(localBankingException), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static int getNumTransactions(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Properties paramProperties, HashMap paramHashMap)
    throws CSILException
  {
    Banking3 localBanking3 = getBanking3Service(paramHashMap);
    if (!(localBanking3 instanceof Banking8))
    {
      debug("The banking service must implement the Banking8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      return ((Banking8)localBanking3).getNumTransactions(paramAccount, paramCalendar1, paramCalendar2, paramProperties, paramHashMap);
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, jdMethod_for(localBankingException), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static AccountHistories getHistory(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return getBanking3Service(paramHashMap).getHistory(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, jdMethod_for(localBankingException), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static AccountSummaries getSummary(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return getBanking3Service(paramHashMap).getSummary(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, jdMethod_for(localBankingException), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static ExtendedAccountSummaries getExtendedSummary(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return getBanking3Service(paramHashMap).getExtendedSummary(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, jdMethod_for(localBankingException), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      checkExtra(paramHashMap);
      if (!paramHashMap.containsKey("com.ffusion.service.AccountService")) {
        paramHashMap.put("com.ffusion.service.AccountService", AccountHandler.getAccountService());
      }
      Properties localProperties = paramReportCriteria.getSearchCriteria();
      if (localProperties == null) {
        throw new BankingException(403);
      }
      String str1 = localProperties.getProperty("Accounts");
      if (str1 == null) {
        str1 = "AllAccounts";
      }
      int i = 0;
      StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",", false);
      Object localObject1;
      while ((localStringTokenizer.hasMoreTokens()) && (i == 0))
      {
        localObject1 = localStringTokenizer.nextToken();
        if (("AllAccounts".equals(localObject1)) || ("AllAccounts".equals(localObject1))) {
          i = 1;
        }
      }
      if (i != 0)
      {
        localObject1 = new Accounts();
        try
        {
          Object localObject2 = null;
          if ((paramHashMap != null) && ((localObject2 = paramHashMap.get("com.ffusion.service.AccountService")) != null))
          {
            AccountService3 localAccountService3 = (AccountService3)localObject2;
            localObject1 = localAccountService3.getAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
            localObject1 = jdMethod_for(paramSecureUser, (Accounts)localObject1, paramHashMap);
          }
          else
          {
            throw new BankingException(409);
          }
        }
        catch (ClassCastException localClassCastException)
        {
          throw new BankingException(409);
        }
        catch (ProfileException localProfileException)
        {
          throw new BankingException(410, localProfileException.getMessage());
        }
        paramHashMap.put("Accounts", localObject1);
      }
      else
      {
        localObject1 = new StringTokenizer(str1, ",");
        int j = ((StringTokenizer)localObject1).countTokens();
        if (j < 1) {
          throw new BankingException(405, "Value for SearchCriteria key Accounts has incorrect format.");
        }
        for (int k = 0; k < j; k++)
        {
          String str2 = ((StringTokenizer)localObject1).nextToken();
          Account localAccount = new Account();
          FFIStringTokenizer localFFIStringTokenizer = new FFIStringTokenizer(str2, ":");
          int m = localFFIStringTokenizer.countTokens();
          if (m < 2) {
            throw new BankingException(405, "Value for SearchCriteria key Accounts has incorrect format.");
          }
          localAccount.setID(localFFIStringTokenizer.nextToken());
          localAccount.setBankID(localFFIStringTokenizer.nextToken());
          if (m >= 3) {
            localAccount.setRoutingNum(localFFIStringTokenizer.nextToken());
          }
          if (m >= 4) {
            localAccount.setNickName(localFFIStringTokenizer.nextToken());
          }
          if (m >= 5) {
            localAccount.setCurrencyCode(localFFIStringTokenizer.nextToken());
          }
          if (!jdMethod_for(paramSecureUser, localAccount)) {
            throw new CSILException("Banking.getReportData", 20020, "User is not entitled to the requested account (" + str2 + ")");
          }
        }
      }
      return getBanking3Service(paramHashMap).getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, jdMethod_for(localBankingException), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static FundsTransactions getFundsTransactions(SecureUser paramSecureUser, Accounts paramAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    FundsTransactions localFundsTransactions = null;
    try
    {
      BillPay localBillPay = (BillPay)paramHashMap.get("SERVICE");
      if ((localBillPay == null) || (!(localBillPay instanceof BillPay)))
      {
        debug("BillPay service is required");
        throwing(-1009, -1010);
      }
      Banking3 localBanking3 = getBanking3Service(paramHashMap);
      Transfers localTransfers = new Transfers();
      localBanking3.getTransfers(paramAccounts, localTransfers);
      RecTransfers localRecTransfers = new RecTransfers();
      localBanking3.getRecTransfers(paramAccounts, localRecTransfers);
      Payments localPayments = new Payments();
      RecPayments localRecPayments = new RecPayments();
      localBillPay.getPayments(paramAccounts, localPayments, localRecPayments, null);
      localFundsTransactions = new FundsTransactions();
      for (int i = 0; i < localTransfers.size(); i++) {
        localFundsTransactions.add(localTransfers.get(i));
      }
      for (i = 0; i < localRecTransfers.size(); i++) {
        localFundsTransactions.add(localRecTransfers.get(i));
      }
      for (i = 0; i < localPayments.size(); i++) {
        localFundsTransactions.add(localPayments.get(i));
      }
      for (i = 0; i < localRecPayments.size(); i++) {
        localFundsTransactions.add(localRecPayments.get(i));
      }
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
    return localFundsTransactions;
  }
  
  public static FundsTransactions getPendingFundsTransactions(SecureUser paramSecureUser, Accounts paramAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    FundsTransactions localFundsTransactions = null;
    return localFundsTransactions;
  }
  
  public static FundsTransactions getCompletedFundsTransactions(SecureUser paramSecureUser, Accounts paramAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    FundsTransactions localFundsTransactions = null;
    return localFundsTransactions;
  }
  
  public static Transfers getPagedPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    try
    {
      return getBanking3Service(paramHashMap).getPagedPendingTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, localBankingException.getErrorCode(), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static Transfers getNextPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    try
    {
      return getBanking3Service(paramHashMap).getNextPendingTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, localBankingException.getErrorCode(), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static Transfers getPreviousPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    try
    {
      return getBanking3Service(paramHashMap).getPreviousPendingTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, localBankingException.getErrorCode(), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static Transfers getPagedCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    try
    {
      return getBanking3Service(paramHashMap).getPagedCompletedTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, localBankingException.getErrorCode(), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static Transfers getNextCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    try
    {
      return getBanking3Service(paramHashMap).getNextCompletedTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, localBankingException.getErrorCode(), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static Transfers getPreviousCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    try
    {
      return getBanking3Service(paramHashMap).getPreviousCompletedTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(-1009, localBankingException.getErrorCode(), localBankingException);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static FundsTransactionTemplates getFundTransactionTemplates(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    FundsTransactionTemplates localFundsTransactionTemplates = new FundsTransactionTemplates();
    int i = paramSecureUser.getProfileID();
    if ((paramHashMap != null) && ((String)paramHashMap.get("TemplateType") != null))
    {
      if (((String)paramHashMap.get("TemplateType")).equalsIgnoreCase("Business")) {
        i = paramSecureUser.getBusinessID();
      }
      if (((String)paramHashMap.get("TemplateType")).equalsIgnoreCase("All")) {
        i = -1;
      }
    }
    try
    {
      TWTransactions localTWTransactions = a7s.getTransactions(paramInt, i);
      TWTransaction localTWTransaction = null;
      if (localTWTransactions != null)
      {
        Iterator localIterator = localTWTransactions.iterator();
        while (localIterator.hasNext())
        {
          localTWTransaction = (TWTransaction)localIterator.next();
          FundsTransactionTemplate localFundsTransactionTemplate = (FundsTransactionTemplate)localTWTransaction.getTransaction();
          Accounts localAccounts = (Accounts)paramHashMap.get("Accounts");
          Object localObject;
          if (paramInt == 1001)
          {
            localObject = (RecTransfer)localFundsTransactionTemplate.getFundsTransaction();
          }
          else if (paramInt == 1003)
          {
            localObject = (Payees)paramHashMap.get("Payees");
            RecPayment localRecPayment = (RecPayment)localFundsTransactionTemplate.getFundsTransaction();
          }
          else if (paramInt == 1007)
          {
            localObject = (ACHBatch)localFundsTransactionTemplate.getFundsTransaction();
          }
          localFundsTransactionTemplate.setID(localTWTransaction.getID());
          localFundsTransactionTemplate.setUserID(localTWTransaction.getUserID());
          localFundsTransactionTemplates.add(localFundsTransactionTemplate);
        }
      }
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
    return localFundsTransactionTemplates;
  }
  
  public static FundsTransactionTemplate addFundsTransactionTemplate(SecureUser paramSecureUser, FundsTransactionTemplate paramFundsTransactionTemplate, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      String str = paramFundsTransactionTemplate.getFundsTransaction().getTrackingID();
      if (str == null) {
        paramFundsTransactionTemplate.getFundsTransaction().setTrackingID("0");
      }
      int i = paramSecureUser.getProfileID();
      if ((paramHashMap != null) && ((String)paramHashMap.get("TemplateType") != null) && (((String)paramHashMap.get("TemplateType")).equalsIgnoreCase("Business"))) {
        i = paramSecureUser.getBusinessID();
      }
      TWTransaction localTWTransaction = a7s.addTransaction(i, paramFundsTransactionTemplate);
      paramFundsTransactionTemplate.setID(localTWTransaction.getID());
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
    return paramFundsTransactionTemplate;
  }
  
  public static FundsTransactionTemplate modifyFundsTransactionTemplate(SecureUser paramSecureUser, FundsTransactionTemplate paramFundsTransactionTemplate, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      int i = paramSecureUser.getProfileID();
      if ((paramHashMap != null) && ((String)paramHashMap.get("TemplateType") != null) && (((String)paramHashMap.get("TemplateType")).equalsIgnoreCase("Business"))) {
        i = paramSecureUser.getBusinessID();
      }
      if (i != paramFundsTransactionTemplate.getUserID())
      {
        a7s.deleteTransaction(paramFundsTransactionTemplate.getID());
        addFundsTransactionTemplate(paramSecureUser, paramFundsTransactionTemplate, paramHashMap);
      }
      else
      {
        a7s.modifyTransaction(paramFundsTransactionTemplate.getID(), paramFundsTransactionTemplate);
      }
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
    return paramFundsTransactionTemplate;
  }
  
  public static void deleteFundsTransactionTemplate(SecureUser paramSecureUser, FundsTransactionTemplate paramFundsTransactionTemplate, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a7s.deleteTransaction(paramFundsTransactionTemplate.getID());
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
  }
  
  public static HashMap getTransactionTypes(HashMap paramHashMap)
    throws CSILException
  {
    HashMap localHashMap = null;
    Banking3 localBanking3 = getBanking3Service(paramHashMap);
    if (!(localBanking3 instanceof Banking8))
    {
      debug("The banking service must implement the Banking8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      localHashMap = ((Banking8)localBanking3).getTransactionTypes();
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(22000, localBankingException);
    }
    return localHashMap;
  }
  
  public static HashMap getTransactionTypes(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a7r.getTransactionTypes(paramInt, paramHashMap);
    }
    catch (BankingException localBankingException)
    {
      CSILException localCSILException = new CSILException(-1009, jdMethod_for(localBankingException));
      throw localCSILException;
    }
  }
  
  public static TransactionTypeInfo getTransactionTypeInfo(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a7r.getTransactionTypeInfo(paramInt, paramHashMap);
    }
    catch (BankingException localBankingException)
    {
      CSILException localCSILException = new CSILException(-1009, jdMethod_for(localBankingException));
      throw localCSILException;
    }
  }
  
  public static HashMap getTransactionTypes(Locale paramLocale, HashMap paramHashMap)
    throws CSILException
  {
    HashMap localHashMap = null;
    Banking3 localBanking3 = getBanking3Service(paramHashMap);
    if (!(localBanking3 instanceof Banking8))
    {
      debug("The banking service must implement the Banking8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      localHashMap = ((Banking8)localBanking3).getTransactionTypes(paramLocale, paramHashMap);
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(22000, localBankingException);
    }
    return localHashMap;
  }
  
  public static Transfers getPagedTransfers(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getPagedTransfers";
    Banking3 localBanking3 = getBanking3Service(paramHashMap);
    if (!(localBanking3 instanceof Banking8))
    {
      debug("The banking service must implement the Banking8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      Transfers localTransfers = ((Banking8)localBanking3).getPagedTransfers(paramSecureUser, paramPagingContext, paramHashMap);
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a7n)) {
        Register.getRegisterCategoriesForTransfers(paramSecureUser, localTransfers, paramHashMap);
      }
      return localTransfers;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(22000, localBankingException);
    }
  }
  
  public static FundsTransactions getPagedFundsTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getPagedFundsTransactions";
    Banking3 localBanking3 = getBanking3Service(paramHashMap);
    if (!(localBanking3 instanceof Banking8))
    {
      debug("The banking service must implement the Banking8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      FundsTransactions localFundsTransactions = ((Banking8)localBanking3).getPagedFundsTransactions(paramSecureUser, paramPagingContext, paramHashMap);
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a7n))
      {
        Transfers localTransfers = new Transfers();
        Iterator localIterator1 = localFundsTransactions.iterator();
        while (localIterator1.hasNext())
        {
          FundsTransaction localFundsTransaction = (FundsTransaction)localIterator1.next();
          if ((localFundsTransaction instanceof Transfer))
          {
            localTransfers.add(localFundsTransaction);
          }
          else if ((localFundsTransaction instanceof TransferBatch))
          {
            TransferBatch localTransferBatch = (TransferBatch)localFundsTransaction;
            if (localTransferBatch.getTransfers() != null)
            {
              Iterator localIterator2 = localTransferBatch.getTransfers().iterator();
              while (localIterator2.hasNext())
              {
                Transfer localTransfer = (Transfer)localIterator2.next();
                localTransfers.add(localTransfer);
              }
            }
          }
        }
        Register.getRegisterCategoriesForTransfers(paramSecureUser, localTransfers, paramHashMap);
      }
      return localFundsTransactions;
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(22000, localBankingException);
    }
  }
  
  public static ArrayList getTransactionTypeDescriptions(HashMap paramHashMap)
    throws CSILException
  {
    ArrayList localArrayList = null;
    Banking3 localBanking3 = getBanking3Service(paramHashMap);
    if (!(localBanking3 instanceof Banking8))
    {
      debug("The banking service must implement the Banking8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      localArrayList = ((Banking8)localBanking3).getTransactionTypeDescriptions();
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(22000, localBankingException);
    }
    return localArrayList;
  }
  
  public static ArrayList getTransactionTypeDescriptions(Locale paramLocale, HashMap paramHashMap)
    throws CSILException
  {
    ArrayList localArrayList = null;
    Banking3 localBanking3 = getBanking3Service(paramHashMap);
    if (!(localBanking3 instanceof Banking8))
    {
      debug("The banking service must implement the Banking8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      localArrayList = ((Banking8)localBanking3).getTransactionTypeDescriptions(paramLocale, paramHashMap);
    }
    catch (BankingException localBankingException)
    {
      throw new CSILException(22000, localBankingException);
    }
    return localArrayList;
  }
  
  protected static Banking3 getBanking3Service(HashMap paramHashMap)
    throws CSILException
  {
    Object localObject = null;
    checkExtra(paramHashMap);
    try
    {
      localObject = (Banking3)paramHashMap.get("SERVICE");
      if (localObject == null) {
        localObject = a7r;
      }
      if (localObject == null)
      {
        debug("Missing required parameter: extra.'SERVICE'");
        throwing(-1009, -1010);
      }
    }
    catch (ClassCastException localClassCastException)
    {
      debug("Unexpected type for required parameter: extra.'SERVICE': " + localClassCastException.getMessage());
      throwing(-1009, -1010);
    }
    return localObject;
  }
  
  protected static Banking7 getBanking7Service(HashMap paramHashMap)
    throws CSILException
  {
    Object localObject = null;
    checkExtra(paramHashMap);
    try
    {
      localObject = (Banking7)paramHashMap.get("SERVICE");
      if (localObject == null) {
        localObject = a7r;
      }
      if (localObject == null)
      {
        debug("Missing required parameter: extra.'SERVICE'");
        throwing(-1009, -1010);
      }
    }
    catch (ClassCastException localClassCastException)
    {
      debug("Unexpected type for required parameter: extra.'SERVICE': " + localClassCastException.getMessage());
      throwing(-1009, -1010);
    }
    return localObject;
  }
  
  private static int jdMethod_for(BankingException paramBankingException)
  {
    switch (paramBankingException.getErrorCode())
    {
    case 1: 
      return 1027;
    case 2: 
      return 1016;
    case 3: 
      return 1025;
    case 4: 
      return 1026;
    case 5: 
      return -1009;
    case 401: 
      return 1021;
    case 1022: 
      return 1022;
    case 403: 
      return 1017;
    case 404: 
      return 1018;
    case 405: 
      return 1019;
    case 406: 
      return 1028;
    case 1023: 
      return 1023;
    case 408: 
      return 1024;
    case 409: 
      return 1014;
    case 410: 
      return 1015;
    case 411: 
      return 1029;
    case 412: 
      return 25018;
    case 413: 
      return paramBankingException.getErrorCode();
    case 442: 
      return 20020;
    case 1080: 
      return 1080;
    case 465: 
      return 1085;
    case 466: 
      return 1086;
    case 467: 
      return 1087;
    case 468: 
      return 1088;
    case 469: 
      return 1089;
    case 470: 
      return 1090;
    case 471: 
      return 1091;
    case 472: 
      return 1092;
    case 473: 
      return 1093;
    case 474: 
      return 1094;
    case 475: 
      return 1095;
    case 476: 
      return 1096;
    }
    return paramBankingException.getErrorCode();
  }
  
  protected static void checkExtraParams(HashMap paramHashMap)
  {
    if (!paramHashMap.containsKey("PAGESIZE")) {
      paramHashMap.put("PAGESIZE", a7o);
    }
  }
  
  private static boolean jdMethod_for(SecureUser paramSecureUser, Account paramAccount)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { null });
    localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(paramAccount) });
    return EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID()) == null;
  }
  
  private static Accounts jdMethod_for(SecureUser paramSecureUser, Accounts paramAccounts, HashMap paramHashMap)
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { null });
    Accounts localAccounts = new Accounts(paramAccounts.getLocale());
    String[] arrayOfString1 = { "Account" };
    String[] arrayOfString2 = new String[1];
    for (int i = 0; i < paramAccounts.size(); i++)
    {
      arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId((Account)paramAccounts.get(i));
      localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
      try
      {
        if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID()) == null) {
          localAccounts.add(paramAccounts.get(i));
        }
      }
      catch (Exception localException) {}
    }
    return localAccounts;
  }
  
  public static Date getEffectiveDate(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getEffectiveDate";
    try
    {
      return a7r.getEffectiveDate(paramSecureUser, paramTransfer, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Banking
 * JD-Core Version:    0.7.0.1
 */