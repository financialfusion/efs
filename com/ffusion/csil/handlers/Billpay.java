package com.ffusion.csil.handlers;

import com.ffusion.beans.Currency;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.PaymentBatch;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.register.ServerTransaction;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.util.RegisterUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Register;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.BillPay3;
import com.ffusion.services.BillPay4;
import com.ffusion.services.BillPay5;
import com.ffusion.services.BillPay6;
import com.ffusion.services.BillPay7;
import com.ffusion.services.BillPay8;
import com.ffusion.services.BillPay9;
import com.ffusion.services.ProfileBillPay;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

public class Billpay
  extends Initialize
{
  private static Entitlement a6v = new Entitlement("Account Register", null, null);
  private static final String a6C = "Billpay";
  private static final String a6y = "ProfileBillpay";
  private static final int a6z = 30;
  private static BillPay4 a6w = null;
  private static ProfileBillPay a6B = null;
  private static String a6x;
  private static String a6A;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.initialize";
    a6x = HandlerUtil.getGlobalPageSize(paramHashMap);
    HashMap localHashMap1 = HandlerUtil.getServiceSettings(paramHashMap, "Billpay", str, 20107);
    a6w = (BillPay4)HandlerUtil.instantiateService(localHashMap1, str, 20107);
    HashMap localHashMap2 = HandlerUtil.getServiceSettings(paramHashMap, "ProfileBillpay", str, 20107);
    a6B = (ProfileBillPay)HandlerUtil.instantiateService(localHashMap2, str, 20107);
    try
    {
      a6w.initialize(null);
      a6B.initialize();
      a6A = HandlerUtil.getGlobalPayeeDisplaySize(paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(20107, localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService(String paramString)
  {
    if (paramString.equals("ProfileBillpay")) {
      return a6B;
    }
    return a6w;
  }
  
  public static SecureUser signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    checkExtra(paramHashMap);
    if (paramSecureUser == null) {
      paramSecureUser = new SecureUser();
    }
    BillPay3 localBillPay3 = getBillPayService(paramHashMap);
    if ((localBillPay3 instanceof BillPay4))
    {
      ((BillPay4)localBillPay3).signOn(paramSecureUser, paramString1, paramString2);
    }
    else
    {
      int i = 0;
      localBillPay3.signOn(paramString1, paramString2);
      if (i != 0) {
        throwing(-1009, i);
      }
    }
    return paramSecureUser;
  }
  
  public static SecureUser signOff(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    checkExtra(paramHashMap);
    int i = 0;
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramSecureUser;
  }
  
  public static SecureUser changePIN(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    int i = getBillPayService(paramHashMap).changePIN(paramSecureUser.getPassword(), paramString);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramSecureUser;
  }
  
  public static Accounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    Accounts localAccounts = null;
    if (paramHashMap != null) {
      localAccounts = (Accounts)paramHashMap.get("ACCOUNTS");
    }
    if (localAccounts == null)
    {
      debug("Missing required parameter: extra.'ACCOUNTS' - creating new Accounts()");
      localAccounts = new Accounts();
    }
    int i = getBillPayService(paramHashMap).getAccounts(localAccounts);
    if (i != 0) {
      throwing(-1009, i);
    }
    return localAccounts;
  }
  
  public static Payees getPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    Payees localPayees1 = null;
    if (paramHashMap != null) {
      localPayees1 = (Payees)paramHashMap.get("PAYEES");
    }
    if (localPayees1 == null)
    {
      debug("Missing required parameter: extra.'PAYEES' - creating new Payees()");
      localPayees1 = new Payees();
    }
    try
    {
      int i = getBillPayService(paramHashMap).getPayees(localPayees1);
      if (i != 0) {
        throwing(-1009, i);
      }
      localObject = a6B.getPayees(paramSecureUser, paramHashMap);
      Payee localPayee1 = null;
      Payee localPayee2 = null;
      Payees localPayees2 = null;
      ListIterator localListIterator = localPayees1.listIterator();
      while (localListIterator.hasNext())
      {
        localPayee1 = (Payee)localListIterator.next();
        localPayee2 = (Payee)((Payees)localObject).getFirstByFilter("HostID=" + localPayee1.getHostID());
        if (localPayee2 != null)
        {
          localPayee1.setNickName(localPayee2.getNickName());
          localPayee1.putAll(localPayee2.getHash());
          ((Payees)localObject).remove(localPayee2);
        }
        else
        {
          localPayees2 = new Payees();
          localPayees2.add(localPayee1);
          if (localPayee1.getName().length() <= 30) {
            localPayee1.setNickName(localPayee1.getName());
          } else {
            localPayee1.setNickName(localPayee1.getName().substring(0, 30));
          }
          try
          {
            a6B.addPayees(paramSecureUser, localPayees2, paramHashMap);
          }
          catch (ProfileException localProfileException2) {}
        }
      }
    }
    catch (ProfileException localProfileException1)
    {
      Object localObject = new CSILException(-1009, u(localProfileException1.code), localProfileException1);
      DebugLog.throwing("Billpay.getPayees", (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    return localPayees1;
  }
  
  public static Payees getGlobalPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    Payees localPayees = null;
    try
    {
      localPayees = getBillPayService(paramHashMap).getGlobalPayees(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
    return localPayees;
  }
  
  public static Payee getPayeeByID(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    Payee localPayee = null;
    try
    {
      localPayee = ((BillPay5)getBillPayService(paramHashMap)).getPayeeByID(paramSecureUser, paramString, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
    return localPayee;
  }
  
  public static RecPayment getRecPaymentByID(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, Payees paramPayees, HashMap paramHashMap)
    throws CSILException
  {
    RecPayment localRecPayment = null;
    try
    {
      localRecPayment = ((BillPay5)getBillPayService(paramHashMap)).getRecPaymentByID(paramSecureUser, paramString, paramAccounts, paramPayees, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
    return localRecPayment;
  }
  
  public static Payment getPaymentByID(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, Payees paramPayees, HashMap paramHashMap)
    throws CSILException
  {
    Payment localPayment = null;
    try
    {
      localPayment = ((BillPay7)getBillPayService(paramHashMap)).getPaymentByID(paramSecureUser, paramString, paramAccounts, paramPayees, paramHashMap);
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v))
      {
        Payments localPayments = new Payments();
        localPayments.add(localPayment);
        Register.getRegisterCategoriesForPayments(paramSecureUser, localPayments, new RecPayments(), paramHashMap);
      }
    }
    catch (Exception localException)
    {
      throw new CSILException(22000, localException);
    }
    return localPayment;
  }
  
  public static Payees addPayees(SecureUser paramSecureUser, Payees paramPayees, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      int i = getBillPayService(paramHashMap).addPayees(paramPayees);
      if (i != 0) {
        throwing(-1009, i);
      }
      a6B.addPayees(paramSecureUser, paramPayees, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      localProfileException.printStackTrace();
      CSILException localCSILException = new CSILException(-1009, u(localProfileException.code), localProfileException);
      DebugLog.throwing("Billpay.addPayees", localCSILException);
      throw localCSILException;
    }
    return paramPayees;
  }
  
  public static Payee modifyPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      Payees localPayees = new Payees();
      localPayees.add(paramPayee);
      localObject = paramPayee.getHostID();
      int i = getBillPayService(paramHashMap).modifyPayees(localPayees);
      if (i != 0) {
        throwing(-1009, i);
      }
      paramPayee = (Payee)localPayees.get(0);
      if ((paramPayee.getHostID() != null) && (paramPayee.getHostID().equalsIgnoreCase((String)localObject)))
      {
        a6B.modifyPayee(paramSecureUser, paramPayee, paramHashMap);
      }
      else
      {
        String str = paramPayee.getHostID();
        paramPayee.setHostID((String)localObject);
        a6B.deletePayee(paramSecureUser, paramPayee, paramHashMap);
        paramPayee.setHostID(str);
        a6B.addPayees(paramSecureUser, localPayees, paramHashMap);
      }
    }
    catch (ProfileException localProfileException)
    {
      Object localObject = new CSILException(-1009, u(localProfileException.code), localProfileException);
      DebugLog.throwing("Billpay.modifyPayee", (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    return paramPayee;
  }
  
  public static Payee deletePayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      Payees localPayees = new Payees();
      localPayees.add(paramPayee);
      int i = getBillPayService(paramHashMap).deletePayees(localPayees);
      if (i != 0)
      {
        i = 2013;
        throwing(-1009, i);
      }
      a6B.deletePayee(paramSecureUser, paramPayee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, u(localProfileException.code), localProfileException);
      DebugLog.throwing("Billpay.deletePayee", localCSILException);
      throw localCSILException;
    }
    return paramPayee;
  }
  
  public static Payments sendPayments(SecureUser paramSecureUser, Payments paramPayments, HashMap paramHashMap)
    throws CSILException
  {
    Account localAccount = null;
    Payees localPayees = null;
    if (paramHashMap != null)
    {
      localAccount = (Account)paramHashMap.get("ACCOUNT");
      localPayees = (Payees)paramHashMap.get("PAYEES");
    }
    if (localAccount == null)
    {
      debug("Missing required parameter: extra.'ACCOUNT' - using payments[0].getAccount()");
      localAccount = ((Payment)paramPayments.get(0)).getAccount();
    }
    if (localPayees == null)
    {
      debug("Missing required parameter: extra.'PAYEES' - creating new Payees()");
      localPayees = new Payees();
    }
    int i = getBillPayService(paramHashMap).sendPayments(localAccount, paramPayments, localPayees, paramHashMap);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v)) && (RegisterUtil.isRegisterEnabled(localAccount)))
    {
      ArrayList localArrayList = new ArrayList();
      ListIterator localListIterator = paramPayments.listIterator();
      while (localListIterator.hasNext())
      {
        Payment localPayment = (Payment)localListIterator.next();
        String str = (String)localPayment.get("REGISTER_CATEGORY_ID");
        if (str != null) {
          localArrayList.add(new ServerTransaction(localPayment.getID(), null, Integer.parseInt(str)));
        }
      }
      if (localArrayList.size() > 0) {
        Register.addSrvrTranRegisterCategory(paramSecureUser, localArrayList, paramHashMap);
      }
    }
    return paramPayments;
  }
  
  public static Payment modifyPayment(SecureUser paramSecureUser, Payment paramPayment1, Payment paramPayment2, HashMap paramHashMap)
    throws CSILException
  {
    Account localAccount = null;
    if (paramHashMap != null) {
      localAccount = (Account)paramHashMap.get("ACCOUNT");
    }
    if (localAccount == null)
    {
      debug("Missing required parameter: extra.'ACCOUNT' - using Payment.getAccount()");
      localAccount = paramPayment1.getAccount();
    }
    int i = getBillPayService(paramHashMap).modifyPayment(localAccount, paramPayment1, paramHashMap);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v)) && (RegisterUtil.isRegisterEnabled(localAccount)))
    {
      String str = (String)paramPayment1.get("REGISTER_CATEGORY_ID");
      if (str != null)
      {
        ServerTransaction localServerTransaction = new ServerTransaction(paramPayment1.getID(), null, Integer.parseInt(str));
        Register.modifySrvrTranRegisterCategory(paramSecureUser, localServerTransaction, paramHashMap);
      }
    }
    return paramPayment1;
  }
  
  public static Payments cancelPayments(SecureUser paramSecureUser, Payments paramPayments, HashMap paramHashMap)
    throws CSILException
  {
    Account localAccount = null;
    if (paramHashMap != null) {
      localAccount = (Account)paramHashMap.get("ACCOUNT");
    }
    if (localAccount == null) {
      debug("Missing required parameter: extra.'ACCOUNT' - using Payment.cancelPayments()");
    }
    int i = getBillPayService(paramHashMap).cancelPayments(paramPayments);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v)) && (localAccount != null) && (RegisterUtil.isRegisterEnabled(localAccount)))
    {
      ListIterator localListIterator = paramPayments.listIterator();
      while (localListIterator.hasNext())
      {
        Payment localPayment = (Payment)localListIterator.next();
        Register.deleteSrvrTranRegisterCategory(paramSecureUser, localPayment.getID(), false, paramHashMap);
      }
    }
    return paramPayments;
  }
  
  public static Object[] getPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, HashMap paramHashMap)
    throws CSILException
  {
    RecPayments localRecPayments = null;
    Payments localPayments = null;
    if (paramHashMap != null)
    {
      localRecPayments = (RecPayments)paramHashMap.get("RECPAYMENTS");
      localPayments = (Payments)paramHashMap.get("PAYMENTS");
    }
    if (localRecPayments == null)
    {
      debug("Missing required parameter: extra.'RECPAYMENTS' - creating new RecPayments()");
      localRecPayments = new RecPayments();
    }
    if (localPayments == null)
    {
      debug("Missing required parameter: extra.'PAYMENTS' - creating new Payments()");
      localPayments = new Payments();
    }
    int i = getBillPayService(paramHashMap).getPayments(paramAccounts, localPayments, localRecPayments, paramPayees);
    if (i != 0) {
      throwing(-1009, i);
    }
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v)) {
      Register.getRegisterCategoriesForPayments(paramSecureUser, localPayments, localRecPayments, paramHashMap);
    }
    return new Object[] { localPayments, localRecPayments };
  }
  
  public static RecPayments sendRecPayments(SecureUser paramSecureUser, RecPayments paramRecPayments, HashMap paramHashMap)
    throws CSILException
  {
    Account localAccount = null;
    Payees localPayees = null;
    if (paramHashMap != null)
    {
      localAccount = (Account)paramHashMap.get("ACCOUNT");
      localPayees = (Payees)paramHashMap.get("PAYEES");
    }
    if (localAccount == null)
    {
      debug("Missing required parameter: extra.'ACCOUNT' - using first recpayment's account");
      localAccount = ((RecPayment)paramRecPayments.get(0)).getAccount();
    }
    if (localPayees == null)
    {
      debug("Missing required parameter: extra.'PAYEES' - creating new Payees()");
      localPayees = new Payees();
    }
    int i = getBillPayService(paramHashMap).sendRecPayments(localAccount, paramRecPayments, localPayees, paramHashMap);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v)) && (RegisterUtil.isRegisterEnabled(localAccount)))
    {
      ArrayList localArrayList = new ArrayList();
      ListIterator localListIterator = paramRecPayments.listIterator();
      while (localListIterator.hasNext())
      {
        RecPayment localRecPayment = (RecPayment)localListIterator.next();
        String str = (String)localRecPayment.get("REGISTER_CATEGORY_ID");
        if (str != null) {
          localArrayList.add(new ServerTransaction(null, localRecPayment.getRecPaymentID(), Integer.parseInt(str)));
        }
      }
      if (localArrayList.size() > 0) {
        Register.addSrvrTranRegisterCategory(paramSecureUser, localArrayList, paramHashMap);
      }
    }
    return paramRecPayments;
  }
  
  public static RecPayment deleteRecPayment(SecureUser paramSecureUser, RecPayment paramRecPayment, HashMap paramHashMap)
    throws CSILException
  {
    Account localAccount = null;
    if (paramHashMap != null) {
      localAccount = (Account)paramHashMap.get("ACCOUNT");
    }
    if (localAccount == null) {
      debug("Missing required parameter: extra.'ACCOUNT' - using Payment.deleteRecPayment()");
    }
    int i = getBillPayService(paramHashMap).deleteRecPayment(paramRecPayment);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v)) && (localAccount != null) && (RegisterUtil.isRegisterEnabled(localAccount))) {
      Register.deleteSrvrTranRegisterCategory(paramSecureUser, paramRecPayment.getRecPaymentID(), true, paramHashMap);
    }
    return paramRecPayment;
  }
  
  public static RecPayment modifyRecPayment(SecureUser paramSecureUser, RecPayment paramRecPayment1, RecPayment paramRecPayment2, HashMap paramHashMap)
    throws CSILException
  {
    Account localAccount = null;
    if (paramHashMap != null) {
      localAccount = (Account)paramHashMap.get("ACCOUNT");
    }
    if (localAccount == null)
    {
      debug("Missing required parameter: extra.'ACCOUNT' - using recPayment.getAccount()");
      localAccount = paramRecPayment1.getAccount();
    }
    int i = getBillPayService(paramHashMap).modifyRecPayment(localAccount, paramRecPayment1, paramHashMap);
    if (i != 0) {
      throwing(-1009, i);
    }
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v)) && (RegisterUtil.isRegisterEnabled(localAccount)))
    {
      String str = (String)paramRecPayment1.get("REGISTER_CATEGORY_ID");
      if (str != null)
      {
        ServerTransaction localServerTransaction = new ServerTransaction(null, paramRecPayment1.getRecPaymentID(), Integer.parseInt(str));
        Register.modifySrvrTranRegisterCategory(paramSecureUser, localServerTransaction, paramHashMap);
      }
    }
    return paramRecPayment1;
  }
  
  public static RecPayment skipRecPayment(SecureUser paramSecureUser, Account paramAccount, RecPayment paramRecPayment, HashMap paramHashMap)
    throws CSILException
  {
    int i = getBillPayService(paramHashMap).skipRecPayment(paramAccount, paramRecPayment);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramRecPayment;
  }
  
  public static Payments getPagedPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      checkExtraParams(paramHashMap);
      Payments localPayments = getBillPayService(paramHashMap).getPagedPendingPayments(paramSecureUser, paramAccounts, paramPayees, paramPagingContext, paramHashMap);
      return localPayments;
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static Payments getNextPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      checkExtraParams(paramHashMap);
      Payments localPayments = getBillPayService(paramHashMap).getNextPendingPayments(paramSecureUser, paramAccounts, paramPayees, paramPagingContext, paramHashMap);
      return localPayments;
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static Payments getPreviousPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      checkExtraParams(paramHashMap);
      return getBillPayService(paramHashMap).getPreviousPendingPayments(paramSecureUser, paramAccounts, paramPayees, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static Payments getPagedCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      checkExtraParams(paramHashMap);
      return getBillPayService(paramHashMap).getPagedCompletedPayments(paramSecureUser, paramAccounts, paramPayees, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static Payments getNextCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      checkExtraParams(paramHashMap);
      return getBillPayService(paramHashMap).getNextCompletedPayments(paramSecureUser, paramAccounts, paramPayees, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static Payments getPreviousCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      checkExtraParams(paramHashMap);
      return getBillPayService(paramHashMap).getPreviousCompletedPayments(paramSecureUser, paramAccounts, paramPayees, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      checkExtraParams(paramHashMap);
      return getBillPayService(paramHashMap).getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  private static int u(int paramInt)
  {
    switch (paramInt)
    {
    case 13: 
      return 19003;
    }
    return 16002;
  }
  
  protected static BillPay3 getBillPayService(HashMap paramHashMap)
    throws CSILException
  {
    Object localObject = null;
    try
    {
      if (paramHashMap != null) {
        localObject = (BillPay3)paramHashMap.get("SERVICE");
      }
      if (localObject == null) {
        localObject = a6w;
      }
      if (localObject == null)
      {
        debug("No Billpay service available");
        throwing(-1009, -1010);
      }
    }
    catch (Exception localException)
    {
      debug("No Billpay service available:" + localException.getMessage());
      throwing(-1009, -1010);
    }
    return localObject;
  }
  
  protected static void checkExtraParams(HashMap paramHashMap)
  {
    if (!paramHashMap.containsKey("PAGESIZE")) {
      paramHashMap.put("PAGESIZE", a6x);
    }
  }
  
  public static Payments getLastPayments(SecureUser paramSecureUser, Payees paramPayees, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return ((BillPay8)getBillPayService(paramHashMap)).getLastPayments(paramSecureUser, paramPayees, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static Payments getPagedPayments(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Billpay.getPagedPayments";
    BillPay3 localBillPay3 = getBillPayService(paramHashMap);
    if (!(localBillPay3 instanceof BillPay6))
    {
      debug("The billpay service must implement the BillPay6 interface");
      throwing(-1009, -1010);
    }
    try
    {
      Payments localPayments1 = ((BillPay6)localBillPay3).getPagedPayments(paramSecureUser, paramPagingContext, paramHashMap);
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v))
      {
        ListIterator localListIterator = localPayments1.listIterator();
        Payments localPayments2 = new Payments(localPayments1.getLocale());
        RecPayments localRecPayments = new RecPayments(localPayments1.getLocale());
        while (localListIterator.hasNext())
        {
          Payment localPayment = (Payment)localListIterator.next();
          if ((localPayment instanceof RecPayment)) {
            localRecPayments.add(localPayment);
          } else {
            localPayments2.add(localPayment);
          }
        }
        Register.getRegisterCategoriesForPayments(paramSecureUser, localPayments2, localRecPayments, paramHashMap);
      }
      return localPayments1;
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static PaymentBatch getPaymentBatchByID(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws CSILException
  {
    BillPay3 localBillPay3 = getBillPayService(paramHashMap);
    if (!(localBillPay3 instanceof BillPay8))
    {
      debug("The billpay service must implement the BillPay8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      PaymentBatch localPaymentBatch = ((BillPay8)localBillPay3).getPaymentBatchByID(paramSecureUser, paramPaymentBatch, paramHashMap);
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v)) {
        Register.getRegisterCategoriesForPayments(paramSecureUser, localPaymentBatch.getPayments(), new RecPayments(), paramHashMap);
      }
      return localPaymentBatch;
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static FundsTransactions getAllPaymentTemplates(SecureUser paramSecureUser, Payment paramPayment, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    BillPay3 localBillPay3 = getBillPayService(paramHashMap);
    if (!(localBillPay3 instanceof BillPay8))
    {
      debug("The billpay service must implement the BillPay8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      FundsTransactions localFundsTransactions = ((BillPay8)localBillPay3).getAllPaymentTemplates(paramSecureUser, paramPayment, paramPagingContext, paramHashMap);
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v))
      {
        Payments localPayments = new Payments();
        for (int i = 0; i < localFundsTransactions.size(); i++)
        {
          FundsTransaction localFundsTransaction = (FundsTransaction)localFundsTransactions.get(i);
          if ((localFundsTransaction instanceof Payment)) {
            localPayments.add(localFundsTransaction);
          }
        }
        Register.getRegisterCategoriesForPayments(paramSecureUser, localPayments, new RecPayments(), paramHashMap);
      }
      return localFundsTransactions;
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static Payment addPaymentTemplate(SecureUser paramSecureUser, Payment paramPayment, HashMap paramHashMap)
    throws CSILException
  {
    BillPay3 localBillPay3 = getBillPayService(paramHashMap);
    if (!(localBillPay3 instanceof BillPay8))
    {
      debug("The billpay service must implement the BillPay8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      paramPayment = ((BillPay8)localBillPay3).addPaymentTemplate(paramSecureUser, paramPayment, paramHashMap);
      if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v)) && (RegisterUtil.isRegisterEnabled(paramPayment.getAccount())))
      {
        ArrayList localArrayList = new ArrayList();
        String str = (String)paramPayment.get("REGISTER_CATEGORY_ID");
        if (str != null)
        {
          localArrayList.add(new ServerTransaction(paramPayment.getID(), null, Integer.parseInt(str)));
          Register.addSrvrTranRegisterCategory(paramSecureUser, localArrayList, paramHashMap);
        }
      }
      return paramPayment;
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static Payment modifyPaymentTemplate(SecureUser paramSecureUser, Payment paramPayment, HashMap paramHashMap)
    throws CSILException
  {
    BillPay3 localBillPay3 = getBillPayService(paramHashMap);
    if (!(localBillPay3 instanceof BillPay8))
    {
      debug("The billpay service must implement the BillPay8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      paramPayment = ((BillPay8)localBillPay3).modifyPaymentTemplate(paramSecureUser, paramPayment, paramHashMap);
      if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v)) && (RegisterUtil.isRegisterEnabled(paramPayment.getAccount())))
      {
        String str = (String)paramPayment.get("REGISTER_CATEGORY_ID");
        if (str != null)
        {
          ServerTransaction localServerTransaction = new ServerTransaction(paramPayment.getID(), null, Integer.parseInt(str));
          Register.modifySrvrTranRegisterCategory(paramSecureUser, localServerTransaction, paramHashMap);
        }
      }
      return paramPayment;
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static void deletePaymentTemplate(SecureUser paramSecureUser, Payment paramPayment, HashMap paramHashMap)
    throws CSILException
  {
    BillPay3 localBillPay3 = getBillPayService(paramHashMap);
    if (!(localBillPay3 instanceof BillPay8))
    {
      debug("The billpay service must implement the BillPay8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      ((BillPay8)localBillPay3).deletePaymentTemplate(paramSecureUser, paramPayment, paramHashMap);
      if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v)) && (paramPayment.getAccount() != null) && (RegisterUtil.isRegisterEnabled(paramPayment.getAccount()))) {
        Register.deleteSrvrTranRegisterCategory(paramSecureUser, paramPayment.getID(), false, paramHashMap);
      }
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static void addPaymentBatch(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws CSILException
  {
    BillPay3 localBillPay3 = getBillPayService(paramHashMap);
    if (!(localBillPay3 instanceof BillPay8))
    {
      debug("The billpay service must implement the BillPay8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      ((BillPay8)localBillPay3).addPaymentBatch(paramSecureUser, paramPaymentBatch, paramHashMap);
      if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v)) && (paramPaymentBatch.getPayments() != null) && (RegisterUtil.isRegisterEnabled(((Payment)paramPaymentBatch.getPayments().get(0)).getAccount())))
      {
        ArrayList localArrayList = new ArrayList();
        ListIterator localListIterator = paramPaymentBatch.getPayments().listIterator();
        while (localListIterator.hasNext())
        {
          Payment localPayment = (Payment)localListIterator.next();
          String str = (String)localPayment.get("REGISTER_CATEGORY_ID");
          if (str != null) {
            localArrayList.add(new ServerTransaction(localPayment.getID(), null, Integer.parseInt(str)));
          }
        }
        if (localArrayList.size() > 0) {
          Register.addSrvrTranRegisterCategory(paramSecureUser, localArrayList, paramHashMap);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static void cancelPaymentBatch(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws CSILException
  {
    BillPay3 localBillPay3 = getBillPayService(paramHashMap);
    if (!(localBillPay3 instanceof BillPay8))
    {
      debug("The billpay service must implement the BillPay8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      ((BillPay8)localBillPay3).cancelPaymentBatch(paramSecureUser, paramPaymentBatch, paramHashMap);
      if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), a6v)) && (paramPaymentBatch.getPayments() != null) && (RegisterUtil.isRegisterEnabled(((Payment)paramPaymentBatch.getPayments().get(0)).getAccount())))
      {
        ListIterator localListIterator = paramPaymentBatch.getPayments().listIterator();
        while (localListIterator.hasNext())
        {
          Payment localPayment = (Payment)localListIterator.next();
          Register.deleteSrvrTranRegisterCategory(paramSecureUser, localPayment.getID(), false, paramHashMap);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static PaymentBatch modifyPaymentBatch(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws CSILException
  {
    BillPay3 localBillPay3 = getBillPayService(paramHashMap);
    if (!(localBillPay3 instanceof BillPay8))
    {
      debug("The billpay service must implement the BillPay8 interface");
      throwing(-1009, -1010);
    }
    try
    {
      paramPaymentBatch = ((BillPay8)localBillPay3).modifyPaymentBatch(paramSecureUser, paramPaymentBatch, paramHashMap);
      if ((paramPaymentBatch.getPayments() != null) && (RegisterUtil.isRegisterEnabled(((Payment)paramPaymentBatch.getPayments().get(0)).getAccount())))
      {
        ListIterator localListIterator = paramPaymentBatch.getPayments().listIterator();
        while (localListIterator.hasNext())
        {
          Payment localPayment = (Payment)localListIterator.next();
          String str = (String)localPayment.get("REGISTER_CATEGORY_ID");
          if (str != null)
          {
            ServerTransaction localServerTransaction = new ServerTransaction(localPayment.getID(), null, Integer.parseInt(str));
            Register.modifySrvrTranRegisterCategory(paramSecureUser, localServerTransaction, paramHashMap);
          }
        }
      }
      return paramPaymentBatch;
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
  }
  
  public static String getGlobalPayeeDisplayCount()
  {
    return a6A;
  }
  
  public static Currency getDefaultCurrency()
    throws CSILException
  {
    if (!(a6w instanceof BillPay9))
    {
      debug("The billpay service must implement the BillPay9 interface");
      throwing(-1009, -1010);
    }
    Currency localCurrency = ((BillPay9)a6w).getDefaultCurrency();
    return localCurrency;
  }
  
  public static Payee getGlobalPayee(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "getGlobalPayee";
    Payee localPayee = null;
    if (!(a6w instanceof BillPay9))
    {
      debug("The billpay service must implement the BillPay9 interface");
      throwing(-1009, -1010);
    }
    try
    {
      localPayee = ((BillPay9)a6w).getGlobalPayee(paramSecureUser, paramInt, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      throw new CSILException(-1009, localException);
    }
    return localPayee;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Billpay
 * JD-Core Version:    0.7.0.1
 */