package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.ffs.bpw.db.InstructionActivityLog;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.services.Banking;
import com.ffusion.services.Banking4;
import com.ffusion.services.Banking8;
import com.ffusion.services.BillPay;
import com.ffusion.services.BillPay4;
import com.ffusion.services.IBPTW;
import com.ffusion.services.bptw.BPTWException;
import com.ffusion.services.bptw.BptwService;
import com.ffusion.services.bptw.Log;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class BPTW
  extends Initialize
{
  private static IBPTW a62;
  private static final String a63 = "BPTW";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.initialize");
    String str = "BPTW.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "BPTW", str, 20107);
    try
    {
      a62 = (IBPTW)HandlerUtil.instantiateService(localHashMap, str, 20107);
      a62.initialize("");
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(20107, localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return a62;
  }
  
  public static SecureUser signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.signOn");
    checkExtra(paramHashMap);
    if (paramSecureUser == null) {
      paramSecureUser = new SecureUser();
    }
    return paramSecureUser;
  }
  
  public static SecureUser signOff(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.signOff");
    checkExtra(paramHashMap);
    return paramSecureUser;
  }
  
  public static CustomerInfo[] addCustomers(SecureUser paramSecureUser, CustomerInfo[] paramArrayOfCustomerInfo, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.addCustomers");
    checkExtra(paramHashMap);
    int i = 0;
    try
    {
      BptwService localBptwService = (BptwService)paramHashMap.get("SERVICE");
      if ((localBptwService == null) || (!(localBptwService instanceof BptwService))) {
        a62.addCustomers(paramSecureUser, paramArrayOfCustomerInfo, paramHashMap);
      } else {
        i = localBptwService.addCustomers(paramArrayOfCustomerInfo);
      }
      if (i == 0)
      {
        localCSILException = new CSILException(-1009);
        DebugLog.throwing("BPTW.addCustomers", localCSILException);
        throw localCSILException;
      }
    }
    catch (BPTWException localBPTWException)
    {
      CSILException localCSILException = new CSILException(-1009, mapError(localBPTWException.getCode()), localBPTWException);
      DebugLog.throwing("BPTW.addCustomers", localCSILException);
      throw localCSILException;
    }
    return paramArrayOfCustomerInfo;
  }
  
  public static CustomerInfo[] modifyCustomers(SecureUser paramSecureUser, CustomerInfo[] paramArrayOfCustomerInfo, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.modifyCustomers");
    checkExtra(paramHashMap);
    int i = 0;
    try
    {
      BptwService localBptwService = (BptwService)paramHashMap.get("SERVICE");
      if ((localBptwService == null) || (!(localBptwService instanceof BptwService))) {
        a62.modifyCustomers(paramSecureUser, paramArrayOfCustomerInfo, paramHashMap);
      } else {
        i = localBptwService.modifyCustomers(paramArrayOfCustomerInfo);
      }
    }
    catch (BPTWException localBPTWException)
    {
      CSILException localCSILException = new CSILException(-1009, mapError(localBPTWException.getCode()), localBPTWException);
      DebugLog.throwing("BPTW.modifyCustomers", localCSILException);
      throw localCSILException;
    }
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramArrayOfCustomerInfo;
  }
  
  public static String[] deleteCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.deleteCustomers");
    checkExtra(paramHashMap);
    int i = 0;
    try
    {
      BptwService localBptwService = (BptwService)paramHashMap.get("SERVICE");
      if ((localBptwService == null) || (!(localBptwService instanceof BptwService))) {
        a62.deleteCustomers(paramSecureUser, paramArrayOfString, paramHashMap);
      } else {
        i = localBptwService.deleteCustomers(paramArrayOfString);
      }
    }
    catch (BPTWException localBPTWException)
    {
      CSILException localCSILException = new CSILException(-1009, mapError(localBPTWException.getCode()), localBPTWException);
      DebugLog.throwing("BPTW.deleteCustomers", localCSILException);
      throw localCSILException;
    }
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramArrayOfString;
  }
  
  public static String[] deactivateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.deactivateCustomers");
    checkExtra(paramHashMap);
    int i = 0;
    try
    {
      BptwService localBptwService = (BptwService)paramHashMap.get("SERVICE");
      if ((localBptwService == null) || (!(localBptwService instanceof BptwService))) {
        a62.deactivateCustomers(paramSecureUser, paramArrayOfString, paramHashMap);
      } else {
        i = localBptwService.deactivateCustomers(paramArrayOfString);
      }
    }
    catch (BPTWException localBPTWException)
    {
      CSILException localCSILException = new CSILException(-1009, mapError(localBPTWException.getCode()), localBPTWException);
      DebugLog.throwing("BPTW.deactivateCustomers", localCSILException);
      throw localCSILException;
    }
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramArrayOfString;
  }
  
  public static String[] activateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.activateCustomers");
    checkExtra(paramHashMap);
    int i = 0;
    try
    {
      BptwService localBptwService = (BptwService)paramHashMap.get("SERVICE");
      if ((localBptwService == null) || (!(localBptwService instanceof BptwService))) {
        a62.activateCustomers(paramSecureUser, paramArrayOfString, paramHashMap);
      } else {
        i = localBptwService.activateCustomers(paramArrayOfString);
      }
    }
    catch (BPTWException localBPTWException)
    {
      CSILException localCSILException = new CSILException(-1009, mapError(localBPTWException.getCode()), localBPTWException);
      DebugLog.throwing("BPTW.deactivateCustomers", localCSILException);
      throw localCSILException;
    }
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramArrayOfString;
  }
  
  public static CustomerInfo[] getCustomersInfo(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.getCustomersInfo");
    checkExtra(paramHashMap);
    CustomerInfo[] arrayOfCustomerInfo;
    try
    {
      BptwService localBptwService = (BptwService)paramHashMap.get("SERVICE");
      if ((localBptwService == null) || (!(localBptwService instanceof BptwService))) {
        arrayOfCustomerInfo = a62.getCustomersInfo(paramSecureUser, paramArrayOfString, paramHashMap);
      } else {
        arrayOfCustomerInfo = localBptwService.getCustomersInfo(paramArrayOfString);
      }
    }
    catch (BPTWException localBPTWException)
    {
      CSILException localCSILException = new CSILException(-1009, mapError(localBPTWException.getCode()), localBPTWException);
      DebugLog.throwing("BPTW.getCustomersInfo", localCSILException);
      throw localCSILException;
    }
    return arrayOfCustomerInfo;
  }
  
  public static PayeeInfo[] getLinkedPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.getLinkedPayees");
    checkExtra(paramHashMap);
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      BptwService localBptwService = (BptwService)paramHashMap.get("SERVICE");
      if ((localBptwService == null) || (!(localBptwService instanceof BptwService))) {
        arrayOfPayeeInfo = a62.getLinkedPayees(paramSecureUser, paramHashMap);
      } else {
        arrayOfPayeeInfo = localBptwService.getLinkedPayees();
      }
    }
    catch (BPTWException localBPTWException)
    {
      CSILException localCSILException = new CSILException(-1009, mapError(localBPTWException.getCode()), localBPTWException);
      DebugLog.throwing("BPTW.getLinkedPayees", localCSILException);
      throw localCSILException;
    }
    return arrayOfPayeeInfo;
  }
  
  public static PayeeInfo[] getMostUsedPayees(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.getMostUsedPayees");
    checkExtra(paramHashMap);
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      BptwService localBptwService = (BptwService)paramHashMap.get("SERVICE");
      if ((localBptwService == null) || (!(localBptwService instanceof BptwService))) {
        arrayOfPayeeInfo = a62.getMostUsedPayees(paramSecureUser, paramInt, paramHashMap);
      } else {
        arrayOfPayeeInfo = localBptwService.getMostUsedPayees(paramInt);
      }
    }
    catch (BPTWException localBPTWException)
    {
      CSILException localCSILException = new CSILException(-1009, mapError(localBPTWException.getCode()), localBPTWException);
      DebugLog.throwing("BPTW.getMostUsedPayees", localCSILException);
      throw localCSILException;
    }
    return arrayOfPayeeInfo;
  }
  
  public static PayeeInfo[] getPreferredPayees(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.getPreferredPayees");
    checkExtra(paramHashMap);
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      BptwService localBptwService = (BptwService)paramHashMap.get("SERVICE");
      if ((localBptwService == null) || (!(localBptwService instanceof BptwService))) {
        arrayOfPayeeInfo = a62.getPreferredPayees(paramSecureUser, paramString, paramHashMap);
      } else {
        arrayOfPayeeInfo = localBptwService.getPreferredPayees(paramString);
      }
    }
    catch (BPTWException localBPTWException)
    {
      CSILException localCSILException = new CSILException(-1009, mapError(localBPTWException.getCode()), localBPTWException);
      DebugLog.throwing("BPTW.getPreferredPayees", localCSILException);
      throw localCSILException;
    }
    return arrayOfPayeeInfo;
  }
  
  public static InstructionActivityLog[] getLogInfo(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.getPreferredPayees");
    checkExtra(paramHashMap);
    Log localLog = (Log)paramHashMap.get("SERVICE");
    if ((localLog == null) || (!(localLog instanceof Log)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    InstructionActivityLog[] arrayOfInstructionActivityLog = null;
    arrayOfInstructionActivityLog = localLog.getLogInfo(paramString1, paramString2);
    return arrayOfInstructionActivityLog;
  }
  
  public static SecureUser signOnBanking(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.signOnBanking");
    checkExtra(paramHashMap);
    if (paramSecureUser == null) {
      paramSecureUser = new SecureUser();
    }
    Banking4 localBanking4 = (Banking4)paramHashMap.get("SERVICE");
    if ((localBanking4 == null) || (!(localBanking4 instanceof Banking4)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    boolean bool = localBanking4.signOn(paramSecureUser, paramString1, paramString2);
    if (!bool) {
      throwing(-1009);
    }
    return paramSecureUser;
  }
  
  public static SecureUser signOnBillPay(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.BPTW.signOnBillPay");
    checkExtra(paramHashMap);
    if (paramSecureUser == null) {
      paramSecureUser = new SecureUser();
    }
    BillPay4 localBillPay4 = (BillPay4)paramHashMap.get("SERVICE");
    if ((localBillPay4 == null) || (!(localBillPay4 instanceof BillPay4)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    boolean bool = localBillPay4.signOn(paramSecureUser, paramString1, paramString2);
    if (!bool) {
      throwing(-1009);
    }
    return paramSecureUser;
  }
  
  public static Transfers getTransfers(SecureUser paramSecureUser, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.getTransfers");
    checkExtra(paramHashMap);
    Banking8 localBanking8 = (Banking8)paramHashMap.get("SERVICE");
    Transfers localTransfers1 = (Transfers)paramHashMap.get("TRANSFERS");
    if (localTransfers1 == null)
    {
      debug("Missing required parameter: extra.'TRANSFERS' - creating new Transfers()");
      localTransfers1 = new Transfers();
    }
    if ((localBanking8 == null) || (!(localBanking8 instanceof Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    Calendar localCalendar1 = GregorianCalendar.getInstance();
    localCalendar1.add(1, -100);
    Calendar localCalendar2 = GregorianCalendar.getInstance();
    localCalendar2.add(1, 100);
    PagingContext localPagingContext = new PagingContext(localCalendar1, localCalendar2);
    localPagingContext.setDirection("FIRST");
    paramHashMap.put("PAGESIZE", "0");
    paramHashMap.put("TRANSFER_STATUS", "WILLPROCESSON");
    paramHashMap.put("Dests", "BOTH");
    paramHashMap.put("TransType", "Current,Recurring");
    User localUser = (User)paramHashMap.get("User");
    try
    {
      int i = paramSecureUser.getPrimaryUserID();
      if (localUser != null) {
        paramSecureUser.setPrimaryUserID(localUser.getId());
      }
      Transfers localTransfers2 = localBanking8.getPagedTransfers(paramSecureUser, localPagingContext, paramHashMap);
      localTransfers1.addAll(localTransfers2);
      paramSecureUser.setPrimaryUserID(i);
    }
    catch (Exception localException)
    {
      throwing(-1009, localException);
    }
    return localTransfers1;
  }
  
  public static RecTransfers getRecTransfers(SecureUser paramSecureUser, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Banking.getRecTransfers");
    checkExtra(paramHashMap);
    Banking8 localBanking8 = (Banking8)paramHashMap.get("SERVICE");
    RecTransfers localRecTransfers = (RecTransfers)paramHashMap.get("RECTRANSFERS");
    if (localRecTransfers == null)
    {
      debug("Missing required parameter: extra.'RECTRANSFERS' - creating new RecTransfers()");
      localRecTransfers = new RecTransfers();
    }
    if ((localBanking8 == null) || (!(localBanking8 instanceof Banking)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    Calendar localCalendar1 = GregorianCalendar.getInstance();
    localCalendar1.add(1, -100);
    Calendar localCalendar2 = GregorianCalendar.getInstance();
    localCalendar2.add(1, 100);
    PagingContext localPagingContext = new PagingContext(localCalendar1, localCalendar2);
    localPagingContext.setDirection("FIRST");
    paramHashMap.put("PAGESIZE", "0");
    paramHashMap.put("Dests", "BOTH");
    paramHashMap.put("TransType", "Recmodel");
    User localUser = (User)paramHashMap.get("User");
    try
    {
      int i = paramSecureUser.getPrimaryUserID();
      if (localUser != null) {
        paramSecureUser.setPrimaryUserID(localUser.getId());
      }
      Transfers localTransfers = localBanking8.getPagedTransfers(paramSecureUser, localPagingContext, paramHashMap);
      localRecTransfers.addAll(localTransfers);
      paramSecureUser.setPrimaryUserID(i);
    }
    catch (Exception localException)
    {
      throwing(-1009, localException);
    }
    return localRecTransfers;
  }
  
  public static Payees getPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Billpay.getPayees");
    checkExtra(paramHashMap);
    BillPay localBillPay = (BillPay)paramHashMap.get("SERVICE");
    if ((localBillPay == null) || (!(localBillPay instanceof BillPay4)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = 0;
    Payees localPayees = (Payees)paramHashMap.get("PAYEES");
    if (localPayees == null)
    {
      debug("Missing required parameter: extra.'PAYEES' - creating new Payees()");
      localPayees = new Payees();
    }
    Object localObject1;
    Object localObject2;
    if ((paramHashMap.get("User") instanceof User))
    {
      localObject1 = (User)paramHashMap.get("User");
      localObject2 = ((User)localObject1).getSecureUser();
      if (((User)localObject1).getPrimarySecondaryValue() == 2)
      {
        User localUser = UserAdmin.getPrimaryUser(paramSecureUser, (User)localObject1, new HashMap());
        if (localUser != null) {
          ((SecureUser)localObject2).setPrimaryUserID(localUser.getId());
        }
      }
      else
      {
        ((SecureUser)localObject2).setPrimaryUserID(((SecureUser)localObject2).getProfileID());
      }
      ((BillPay4)localBillPay).setSecureUser((SecureUser)localObject2);
    }
    else if ((paramHashMap.get("User") instanceof String))
    {
      localObject1 = new SecureUser();
      localObject2 = (String)paramHashMap.get("User");
      if (localObject2 != null)
      {
        ((SecureUser)localObject1).setBusinessID((String)localObject2);
        ((BillPay4)localBillPay).setSecureUser((SecureUser)localObject1);
      }
    }
    i = localBillPay.getPayees(localPayees);
    if (i != 0) {
      throwing(-1009, i);
    }
    return localPayees;
  }
  
  public static Object[] getPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Billpay.getPayments");
    checkExtra(paramHashMap);
    BillPay localBillPay = (BillPay)paramHashMap.get("SERVICE");
    if ((localBillPay == null) || (!(localBillPay instanceof BillPay4)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    RecPayments localRecPayments = (RecPayments)paramHashMap.get("RECPAYMENTS");
    Payments localPayments = (Payments)paramHashMap.get("PAYMENTS");
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
    int i = 0;
    User localUser1 = (User)paramHashMap.get("User");
    if (localUser1 != null)
    {
      SecureUser localSecureUser = localUser1.getSecureUser();
      if (localUser1.getPrimarySecondaryValue() == 2)
      {
        User localUser2 = UserAdmin.getPrimaryUser(paramSecureUser, localUser1, new HashMap());
        if (localUser2 != null) {
          localSecureUser.setPrimaryUserID(localUser2.getId());
        }
      }
      else
      {
        localSecureUser.setPrimaryUserID(localSecureUser.getProfileID());
      }
      ((BillPay4)localBillPay).setSecureUser(localSecureUser);
    }
    i = localBillPay.getPayments(paramAccounts, localPayments, localRecPayments, paramPayees);
    if (i != 0) {
      throwing(-1009, i);
    }
    return new Object[] { localPayments, localRecPayments };
  }
  
  protected static int mapError(int paramInt)
  {
    switch (paramInt)
    {
    case 3200: 
      return 19003;
    }
    return 16002;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.BPTW
 * JD-Core Version:    0.7.0.1
 */