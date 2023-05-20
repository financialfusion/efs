package com.ffusion.services.banksim2;

import com.ffusion.beans.Currency;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.PayeeRoute;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.PaymentBatch;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.util.BeansConverter;
import com.ffusion.beans.wiretransfers.WireAccountMap;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.FX;
import com.ffusion.csil.core.Reporting;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.ffs.bpw.BPWServices.BPWServices;
import com.ffusion.ffs.bpw.BPWServices.BPWServicesHome;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.LastPaymentInfo;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.ffs.bpw.interfaces.SortCriterion;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.IOFX200BPWServices;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumIDScopeEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBillPayMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBillPayMsgsRqV1MessageUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBillPayMsgsRqV1PayeeUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRequest;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRqMsgSetsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeDelRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeDelRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeModRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeModRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtCancRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtCancRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtPrcStsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtCancRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtCancRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStatusAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSubAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSyncRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTokenRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.ValueSetCurrencyEnum;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.services.BillPay9;
import com.ffusion.util.ArrayUtil;
import com.ffusion.util.ContextPool;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

public class BillPay
  extends BillPayBase
  implements BillPay9
{
  private static String aC = "DefaultDaysToPay";
  private String aD = "BPWServices";
  protected RecPayments recpayments;
  protected RecPayment recpayment;
  private String aB;
  private String aA;
  private String az;
  private static final String aH = "Statuses";
  private static final String aG = "[ErrorCode ";
  private final int aE = 10;
  private static final BigDecimal aF = new BigDecimal(BigInteger.ZERO);
  
  public int initialize(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = "banksim.xml";
    }
    return initialize(paramString, new a());
  }
  
  public String getSettings()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BILLPAYSERVICE");
    XMLHandler.appendTag(localStringBuffer, aC, this.defaultDaysToPay);
    localStringBuffer.append(super.getSettings());
    XMLHandler.appendEndTag(localStringBuffer, "BILLPAYSERVICE");
    return localStringBuffer.toString();
  }
  
  public void setUserName(String paramString)
  {
    setUserID(paramString);
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public void setSecureUser(SecureUser paramSecureUser)
  {
    this.secureUser = new SecureUser();
    this.secureUser.set(paramSecureUser);
  }
  
  private void jdMethod_try()
  {
    this.accounts = null;
    this.account = null;
    this.payees = null;
    this.payee = null;
    this.payments = null;
    this.payment = null;
    this.recpayments = null;
    this.recpayment = null;
  }
  
  public int getPayees(Payees paramPayees)
  {
    String str = "BillPay.getPayees";
    int i = 0;
    Payee localPayee = null;
    BPWServices localBPWServices = null;
    CustomerPayeeInfo localCustomerPayeeInfo = null;
    CustomerPayeeInfo[] arrayOfCustomerPayeeInfo = null;
    try
    {
      localBPWServices = getBPWHandler();
    }
    catch (Exception localException1)
    {
      try
      {
        handleBPWException(localException1, 31023, str);
      }
      catch (CSILException localCSILException)
      {
        DebugLog.throwing("getPayees ", localException1);
      }
    }
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".getPayees:");
    }
    this.payees = paramPayees;
    localPayee = new Payee();
    localCustomerPayeeInfo = new CustomerPayeeInfo();
    if (localCustomerPayeeInfo.getCustomerInfo() == null) {
      localCustomerPayeeInfo.setCustomerInfo(new CustomerInfo());
    }
    if (localCustomerPayeeInfo.getPayeeInfo() == null) {
      localCustomerPayeeInfo.setPayeeInfo(new PayeeInfo());
    }
    if (this.secureUser.getBusinessID() == 0)
    {
      localCustomerPayeeInfo.CustomerID = Integer.toString(this.secureUser.getPrimaryUserID());
      localCustomerPayeeInfo.customerInfo.customerID = Integer.toString(this.secureUser.getPrimaryUserID());
    }
    else
    {
      localCustomerPayeeInfo.CustomerID = Integer.toString(this.secureUser.getBusinessID());
      localCustomerPayeeInfo.customerInfo.customerID = Integer.toString(this.secureUser.getBusinessID());
    }
    try
    {
      arrayOfCustomerPayeeInfo = localBPWServices.getCustomerPayees(localCustomerPayeeInfo);
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("getPayees ", localException2);
    }
    for (int j = 0; j < arrayOfCustomerPayeeInfo.length; j++)
    {
      i = arrayOfCustomerPayeeInfo[j].getStatusCode();
      if (i != 0) {
        break;
      }
      localPayee = BeansConverter.PayeeInfoToPayee(arrayOfCustomerPayeeInfo[j].payeeInfo);
      localPayee.setUserAccountNumber(arrayOfCustomerPayeeInfo[j].PayAcct);
      localPayee.setID(Integer.toString(arrayOfCustomerPayeeInfo[j].PayeeListID));
      paramPayees.add(j, localPayee);
    }
    if (i != 0) {
      return mapError(i);
    }
    try
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    catch (Exception localException3)
    {
      DebugLog.throwing("getPayees ", localException3);
    }
    return 0;
  }
  
  public int addPayees(Payees paramPayees)
  {
    String str = "BillPay.addPayees";
    Payee localPayee = null;
    PayeeInfo localPayeeInfo = null;
    Object localObject = null;
    BPWServices localBPWServices = null;
    CustomerPayeeInfo localCustomerPayeeInfo = null;
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    try
    {
      localBPWServices = getBPWHandler();
    }
    catch (Exception localException1)
    {
      try
      {
        handleBPWException(localException1, 31023, str);
      }
      catch (Exception localException3)
      {
        DebugLog.throwing("addPayees ", localException1);
      }
    }
    this.payees = new Payees();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".addPayees:");
    }
    for (int j = 0; j < paramPayees.size(); j++)
    {
      localPayee = new Payee();
      localPayeeInfo = new PayeeInfo();
      localCustomerPayeeInfo = new CustomerPayeeInfo();
      localPayee = (Payee)paramPayees.get(j);
      localPayeeInfo = BeansConverter.payeeToPayeeInfo(localPayee);
      populateCustomerPayeeInfo(localCustomerPayeeInfo, localPayeeInfo, localPayee, this.secureUser);
      try
      {
        localCustomerPayeeInfo = localBPWServices.addCustomerPayee(localCustomerPayeeInfo);
        localArrayList.add(j, localCustomerPayeeInfo);
      }
      catch (Exception localException4)
      {
        DebugLog.throwing("addPayees ", localException4);
      }
    }
    for (j = 0; j < localArrayList.size(); j++)
    {
      i = ((CustomerPayeeInfo)localArrayList.get(j)).getStatusCode();
      if (i != 0) {
        break;
      }
      localPayeeInfo = ((CustomerPayeeInfo)localArrayList.get(j)).payeeInfo;
      localPayee = BeansConverter.PayeeInfoToPayee(localPayeeInfo);
      localPayee.setUserAccountNumber(localCustomerPayeeInfo.PayAcct);
      localPayee.setID(Integer.toString(localCustomerPayeeInfo.PayeeListID));
      paramPayees.set(j, localPayee);
    }
    if (i != 0) {
      return mapError(i);
    }
    try
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("addPayees ", localException2);
    }
    return 0;
  }
  
  public int modifyPayees(Payees paramPayees)
  {
    String str = "BillPay.modifyPayees";
    Payee localPayee = null;
    PayeeInfo localPayeeInfo = null;
    BPWServices localBPWServices = null;
    CustomerPayeeInfo localCustomerPayeeInfo = null;
    ArrayList localArrayList = new ArrayList();
    this.payees = new Payees();
    try
    {
      localBPWServices = getBPWHandler();
    }
    catch (Exception localException1)
    {
      try
      {
        handleBPWException(localException1, 31023, str);
      }
      catch (Exception localException3)
      {
        DebugLog.throwing("modifyPayees ", localException1);
      }
    }
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".modifyPayees:");
    }
    this.payees = new Payees();
    for (int i = 0; i < paramPayees.size(); i++)
    {
      localPayee = new Payee();
      localPayeeInfo = new PayeeInfo();
      localCustomerPayeeInfo = new CustomerPayeeInfo();
      localPayee = (Payee)paramPayees.get(i);
      localPayeeInfo = BeansConverter.payeeToPayeeInfo(localPayee);
      populateCustomerPayeeInfo(localCustomerPayeeInfo, localPayeeInfo, localPayee, this.secureUser);
      try
      {
        localCustomerPayeeInfo = localBPWServices.updateCustomerPayee(localCustomerPayeeInfo);
        localArrayList.add(i, localCustomerPayeeInfo);
      }
      catch (Exception localException4)
      {
        DebugLog.throwing("modifyPayees ", localException4);
      }
    }
    for (i = 0; i < localArrayList.size(); i++)
    {
      this.status = ((CustomerPayeeInfo)localArrayList.get(i)).getStatusCode();
      if (this.status != 0) {
        break;
      }
      localPayeeInfo = ((CustomerPayeeInfo)localArrayList.get(i)).payeeInfo;
      localPayee = BeansConverter.PayeeInfoToPayee(localPayeeInfo);
      localPayee.setUserAccountNumber(localCustomerPayeeInfo.PayAcct);
      localPayee.setID(Integer.toString(localCustomerPayeeInfo.PayeeListID));
      paramPayees.set(i, localPayee);
    }
    if (this.status != 0) {
      return mapError(this.status);
    }
    try
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("modifyPayees ", localException2);
    }
    return 0;
  }
  
  public int deletePayees(Payees paramPayees)
  {
    String str = "BillPay.deletePayees";
    Payee localPayee = null;
    PayeeInfo localPayeeInfo = null;
    BPWServices localBPWServices = null;
    CustomerPayeeInfo localCustomerPayeeInfo = null;
    ArrayList localArrayList = new ArrayList();
    this.payees = new Payees();
    try
    {
      localBPWServices = getBPWHandler();
    }
    catch (Exception localException1)
    {
      try
      {
        handleBPWException(localException1, 31023, str);
      }
      catch (Exception localException3)
      {
        DebugLog.throwing("deletePayees ", localException1);
      }
    }
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".deletePayees:");
    }
    this.payees = new Payees();
    for (int i = 0; i < paramPayees.size(); i++)
    {
      localPayee = new Payee();
      localPayeeInfo = new PayeeInfo();
      localCustomerPayeeInfo = new CustomerPayeeInfo();
      localPayee = (Payee)paramPayees.get(i);
      localPayeeInfo = BeansConverter.payeeToPayeeInfo(localPayee);
      populateCustomerPayeeInfo(localCustomerPayeeInfo, localPayeeInfo, localPayee, this.secureUser);
      try
      {
        localCustomerPayeeInfo = localBPWServices.deleteCustomerPayee(localCustomerPayeeInfo);
        localArrayList.add(i, localCustomerPayeeInfo);
      }
      catch (Exception localException4)
      {
        DebugLog.throwing("deletePayees ", localException4);
      }
    }
    for (i = 0; i < localArrayList.size(); i++)
    {
      this.status = ((CustomerPayeeInfo)localArrayList.get(i)).getStatusCode();
      if (this.status != 0) {
        break;
      }
      localPayeeInfo = ((CustomerPayeeInfo)localArrayList.get(i)).payeeInfo;
      localPayee = BeansConverter.PayeeInfoToPayee(localPayeeInfo);
      localPayee.setUserAccountNumber(localCustomerPayeeInfo.PayAcct);
      localPayee.setID(Integer.toString(localCustomerPayeeInfo.PayeeListID));
      paramPayees.set(i, localPayee);
    }
    if (this.status != 0) {
      return mapError(this.status);
    }
    try
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("deletePayees ", localException2);
    }
    return 0;
  }
  
  public Payees getGlobalPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws Exception
  {
    BPWServices localBPWServices = getBPWHandler();
    PayeeInfo localPayeeInfo1 = null;
    PayeeRouteInfo localPayeeRouteInfo = null;
    PayeeInfo[] arrayOfPayeeInfo = null;
    if (localBPWServices == null) {
      throw new CSILException(31023);
    }
    Payees localPayees = new Payees();
    String str1 = null;
    if ((paramHashMap != null) && (paramHashMap.get("PrefixPayeeName") != null))
    {
      str1 = (String)paramHashMap.get("PrefixPayeeName");
      str1 = str1.trim();
    }
    try
    {
      localPayeeInfo1 = new PayeeInfo();
      localPayeeRouteInfo = new PayeeRouteInfo();
      localPayeeInfo1.PayeeName = str1;
      localPayeeInfo1.NickName = str1;
      localPayeeRouteInfo.RouteID = 0;
      localPayeeInfo1.setPayeeRouteInfo(localPayeeRouteInfo);
      if ((localBPWServices != null) && (str1 != null)) {
        arrayOfPayeeInfo = localBPWServices.searchGlobalPayees(localPayeeInfo1, 7);
      }
      if (arrayOfPayeeInfo != null) {
        for (int i = 0; i < arrayOfPayeeInfo.length; i++)
        {
          PayeeInfo localPayeeInfo2 = arrayOfPayeeInfo[i];
          if (localPayeeInfo2 != null)
          {
            Payee localPayee = BeansConverter.PayeeInfoToPayee(localPayeeInfo2);
            if ((paramHashMap != null) && (paramHashMap.get("DefaultCurrency") != null))
            {
              String str2 = (String)paramHashMap.get("DefaultCurrency");
              if ((localPayee.getPayeeRoute() != null) && (localPayee.getPayeeRoute().getCurrencyCode() != null) && (!str2.equals(localPayee.getPayeeRoute().getCurrencyCode()))) {}
            }
            else
            {
              localPayees.add(localPayee);
            }
          }
        }
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("getGlobalPayees ", localException);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return localPayees;
  }
  
  public Payee getPayeeByID(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
  {
    Payee localPayee = null;
    IOFX200BPWServices localIOFX200BPWServices = null;
    try
    {
      localIOFX200BPWServices = getOFXHandler();
      PayeeInfo localPayeeInfo = localIOFX200BPWServices.findPayeeByID(paramString);
      if (localPayeeInfo != null) {
        localPayee = BeansConverter.PayeeInfoToPayee(localPayeeInfo);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("getPayeeByID ", localException);
    }
    finally
    {
      if (localIOFX200BPWServices != null) {
        removeOFXHandler(localIOFX200BPWServices);
      }
    }
    return localPayee;
  }
  
  public RecPayment getRecPaymentByID(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, Payees paramPayees, HashMap paramHashMap)
    throws CSILException
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramString;
    RecPayment localRecPayment = null;
    IOFX200BPWServices localIOFX200BPWServices = null;
    try
    {
      localIOFX200BPWServices = getOFXHandler();
      RecPmtInfo[] arrayOfRecPmtInfo = null;
      arrayOfRecPmtInfo = localIOFX200BPWServices.getRecPmtById(arrayOfString);
      if ((arrayOfRecPmtInfo != null) && (arrayOfRecPmtInfo.length > 0))
      {
        RecPmtInfo localRecPmtInfo = arrayOfRecPmtInfo[0];
        RecPayments localRecPayments = new RecPayments(paramSecureUser.getLocale());
        localRecPayment = (RecPayment)localRecPayments.createNoAdd();
        BeansConverter.setRecPmtFromRecPmtInfo(localRecPayment, localRecPmtInfo, paramPayees, paramAccounts, true);
        if ((!this.restrictAccounts) && (!Boolean.valueOf(localRecPayment.getAccountEntitled()).booleanValue()))
        {
          String str1 = localRecPmtInfo.AcctDebitType;
          int i = getBPWAccountType(str1);
          String str2 = localRecPmtInfo.AcctDebitID;
          Account localAccount = paramAccounts.createNoAdd(str2, i);
          localRecPayment.setAccount(localAccount);
        }
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("getRecPaymentByID ", localException);
    }
    finally
    {
      if (localIOFX200BPWServices != null) {
        removeOFXHandler(localIOFX200BPWServices);
      }
    }
    return localRecPayment;
  }
  
  public Payment getPaymentByID(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, Payees paramPayees, HashMap paramHashMap)
    throws CSILException
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramString;
    Payment localPayment = null;
    IOFX200BPWServices localIOFX200BPWServices = null;
    try
    {
      localIOFX200BPWServices = getOFXHandler();
      PmtInfo[] arrayOfPmtInfo = null;
      arrayOfPmtInfo = localIOFX200BPWServices.getPmtById(arrayOfString);
      if ((arrayOfPmtInfo != null) && (arrayOfPmtInfo.length > 0))
      {
        PmtInfo localPmtInfo = arrayOfPmtInfo[0];
        Payments localPayments = new Payments(paramSecureUser.getLocale());
        localPayment = (Payment)localPayments.createNoAdd();
        BeansConverter.setPmtFromPmtInfo(localPayment, localPmtInfo, paramPayees, paramAccounts, true);
        if ((!this.restrictAccounts) && (!Boolean.valueOf(localPayment.getAccountEntitled()).booleanValue()))
        {
          String str1 = localPmtInfo.AcctDebitType;
          int i = getBPWAccountType(str1);
          String str2 = localPmtInfo.AcctDebitID;
          Account localAccount = paramAccounts.createNoAdd(str2, i);
          localPayment.setAccount(localAccount);
        }
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("getPaymentByID ", localException);
    }
    finally
    {
      if (localIOFX200BPWServices != null) {
        removeOFXHandler(localIOFX200BPWServices);
      }
    }
    return localPayment;
  }
  
  public int sendPayments(Account paramAccount, Payments paramPayments, Payees paramPayees, HashMap paramHashMap)
  {
    return sendPayments(paramAccount, paramPayments, paramPayees);
  }
  
  public int sendPayments(Account paramAccount, Payments paramPayments, Payees paramPayees)
  {
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".sendPayments:");
    }
    String str = "sendPayments";
    Payment localPayment = null;
    PmtInfo localPmtInfo = null;
    Accounts localAccounts = new Accounts();
    boolean bool = true;
    localAccounts.add(paramAccount);
    ArrayList localArrayList = new ArrayList();
    BPWServices localBPWServices = null;
    this.account = paramAccount;
    this.payees = paramPayees;
    this.payments = new Payments();
    try
    {
      localBPWServices = getBPWHandler();
    }
    catch (Exception localException1)
    {
      try
      {
        logError(1020);
        return 1020;
      }
      catch (Exception localException2)
      {
        DebugLog.throwing("sendPayments", localException1);
      }
    }
    int i = 0;
    for (int j = 0; j < paramPayments.size(); j++)
    {
      localPmtInfo = new PmtInfo();
      localPayment = (Payment)paramPayments.get(j);
      localPayment.setTRNUID(getUniqueSeqNum(this.userID));
      BeansConverter.setPmtInfoFromPmt(localPmtInfo, localPayment);
      localPmtInfo.setPaymentType("Current");
      try
      {
        localPmtInfo = localBPWServices.addBillPayment(localPmtInfo);
        localArrayList.add(j, localPmtInfo);
      }
      catch (Exception localException5)
      {
        DebugLog.throwing("sendPayments", localException5);
        logError(1);
        return 1;
      }
    }
    try
    {
      for (j = 0; j < localArrayList.size(); j++)
      {
        localPmtInfo = (PmtInfo)localArrayList.get(j);
        BeansConverter.setPmtFromPmtInfo(localPayment, localPmtInfo, paramPayees, localAccounts, bool);
        i = a(localPayment, localPmtInfo);
        paramPayments.set(j, localPayment);
        if (i != 0)
        {
          logError(i);
          break;
        }
      }
    }
    catch (Exception localException3)
    {
      DebugLog.throwing("sendPayments", localException3);
    }
    try
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    catch (Exception localException4)
    {
      DebugLog.throwing("sendPayments", localException4);
      logError(1020);
      return 1020;
    }
    return i;
  }
  
  public int modifyPayment(Account paramAccount, Payment paramPayment, HashMap paramHashMap)
  {
    return modifyPayment(paramAccount, paramPayment);
  }
  
  public int modifyPayment(Account paramAccount, Payment paramPayment)
  {
    this.account = paramAccount;
    this.payment = paramPayment;
    String str = "modifyPayment";
    BPWServices localBPWServices = null;
    Accounts localAccounts = new Accounts();
    localAccounts.add(paramAccount);
    boolean bool = true;
    PmtInfo localPmtInfo = new PmtInfo();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".modifyPayment:");
    }
    try
    {
      localBPWServices = getBPWHandler();
    }
    catch (Exception localException1)
    {
      try
      {
        logError(1020);
        return 1020;
      }
      catch (Exception localException2)
      {
        DebugLog.throwing("modifyPayment", localException1);
      }
    }
    int i = 0;
    paramPayment.setTRNUID(getUniqueSeqNum(this.userID));
    BeansConverter.setPmtInfoFromPmt(localPmtInfo, paramPayment);
    localPmtInfo.setPaymentType("Current");
    try
    {
      localPmtInfo = localBPWServices.modifyBillPayment(localPmtInfo);
    }
    catch (Exception localException3)
    {
      DebugLog.throwing("modifyPayment", localException3);
      logError(1);
      return 1;
    }
    try
    {
      BeansConverter.setPmtFromPmtInfo(paramPayment, localPmtInfo, this.payees, localAccounts, bool);
      i = a(paramPayment, localPmtInfo);
      if (i != 0) {
        logError(i);
      }
    }
    catch (Exception localException4)
    {
      DebugLog.throwing("modifyPayment", localException4);
    }
    try
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    catch (Exception localException5)
    {
      DebugLog.throwing("modifyPayment", localException5);
      logError(1020);
      return 1020;
    }
    return i;
  }
  
  public int cancelPayments(Payments paramPayments)
  {
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".cancelPayments:");
    }
    String str = "cancelPayments";
    Payment localPayment = null;
    PmtInfo localPmtInfo = null;
    boolean bool = true;
    ArrayList localArrayList = new ArrayList();
    BPWServices localBPWServices = null;
    this.payments = new Payments();
    try
    {
      localBPWServices = getBPWHandler();
    }
    catch (Exception localException1)
    {
      try
      {
        DebugLog.throwing("cancelPayments", localException1);
        logError(1020);
        return 1020;
      }
      catch (Exception localException2)
      {
        DebugLog.throwing("cancelPayments", localException1);
      }
    }
    int i = 0;
    for (int j = 0; j < paramPayments.size(); j++) {
      try
      {
        localPmtInfo = new PmtInfo();
        localPayment = (Payment)paramPayments.get(j);
        localPayment.setTRNUID(getUniqueSeqNum(this.userID));
        BeansConverter.setPmtInfoFromPmt(localPmtInfo, localPayment);
        localPmtInfo.setPaymentType("Current");
        localPmtInfo = localBPWServices.deleteBillPayment(localPmtInfo);
        localArrayList.add(j, localPmtInfo);
      }
      catch (Exception localException5)
      {
        DebugLog.throwing("cancelPayments", localException5);
        logError(1);
        return 1;
      }
    }
    try
    {
      for (j = 0; j < localArrayList.size(); j++)
      {
        localPmtInfo = (PmtInfo)localArrayList.get(j);
        BeansConverter.setPmtFromPmtInfo(localPayment, localPmtInfo, this.payees, null, bool);
        i = a(localPayment, localPmtInfo);
        paramPayments.set(j, localPayment);
        if (i != 0)
        {
          logError(i);
          break;
        }
      }
    }
    catch (Exception localException3)
    {
      DebugLog.throwing("cancelPayments", localException3);
    }
    try
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    catch (Exception localException4)
    {
      DebugLog.throwing("cancelPayments", localException4);
      logError(1020);
      return 1020;
    }
    return i;
  }
  
  public int getPayments(Accounts paramAccounts, Payments paramPayments, RecPayments paramRecPayments, Payees paramPayees)
  {
    this.accounts = paramAccounts;
    this.payees = paramPayees;
    this.payments = paramPayments;
    this.recpayments = paramRecPayments;
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".getPayments:");
    }
    formatGetPaymentsRequest();
    processOFXRequest();
    jdMethod_try();
    this.status = 0;
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(1020);
      return this.lastError;
    }
    String[] arrayOfString;
    int k;
    Object localObject1;
    Payment localPayment;
    if (paramPayments.size() > 0)
    {
      arrayOfString = new String[paramPayments.size()];
      for (int i = 0; i < paramPayments.size(); i++) {
        arrayOfString[i] = ((Payment)paramPayments.get(i)).getID();
      }
      IOFX200BPWServices localIOFX200BPWServices1 = null;
      try
      {
        localIOFX200BPWServices1 = getOFXHandler();
        PmtInfo[] arrayOfPmtInfo = null;
        arrayOfPmtInfo = localIOFX200BPWServices1.getPmtById(arrayOfString);
        for (k = 0; k < arrayOfPmtInfo.length; k++)
        {
          localObject1 = arrayOfPmtInfo[k];
          localPayment = paramPayments.getByID(((PmtInfo)localObject1).SrvrTID);
          if (localPayment != null)
          {
            localPayment.setTrackingID(((PmtInfo)localObject1).LogID);
            localPayment.setSubmittedBy(((PmtInfo)localObject1).submittedBy);
            localPayment.setFIID(((PmtInfo)localObject1).FIID);
            localPayment.setCustomerID(((PmtInfo)localObject1).CustomerID);
            localPayment.setRecPaymentID(((PmtInfo)localObject1).RecSrvrTID);
            if ((((PmtInfo)localObject1).getStatus() != null) && (((PmtInfo)localObject1).getStatus().equalsIgnoreCase("APPROVAL_PENDING"))) {
              localPayment.setStatus(9);
            }
          }
        }
      }
      catch (Exception localException1)
      {
        DebugLog.throwing("getTransfers ", localException1);
      }
      finally
      {
        if (localIOFX200BPWServices1 != null) {
          removeOFXHandler(localIOFX200BPWServices1);
        }
      }
    }
    if (paramRecPayments.size() > 0)
    {
      arrayOfString = new String[paramRecPayments.size()];
      for (int j = 0; j < paramRecPayments.size(); j++) {
        arrayOfString[j] = ((RecPayment)paramRecPayments.get(j)).getID();
      }
      IOFX200BPWServices localIOFX200BPWServices2 = null;
      try
      {
        localIOFX200BPWServices2 = getOFXHandler();
        RecPmtInfo[] arrayOfRecPmtInfo = null;
        arrayOfRecPmtInfo = localIOFX200BPWServices2.getRecPmtById(arrayOfString);
        for (k = 0; k < arrayOfRecPmtInfo.length; k++)
        {
          localObject1 = arrayOfRecPmtInfo[k];
          localPayment = paramRecPayments.getByID(((RecPmtInfo)localObject1).RecSrvrTID);
          localPayment.setTrackingID(((RecPmtInfo)localObject1).LogID);
          localPayment.setSubmittedBy(((RecPmtInfo)localObject1).submittedBy);
          localPayment.setFIID(((RecPmtInfo)localObject1).FIID);
          localPayment.setCustomerID(((RecPmtInfo)localObject1).CustomerID);
        }
      }
      catch (Exception localException2)
      {
        DebugLog.throwing("getTransfers ", localException2);
      }
      finally
      {
        if (localIOFX200BPWServices2 != null) {
          removeOFXHandler(localIOFX200BPWServices2);
        }
      }
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int sendRecPayments(Account paramAccount, RecPayments paramRecPayments, Payees paramPayees, HashMap paramHashMap)
  {
    return sendRecPayments(paramAccount, paramRecPayments, paramPayees);
  }
  
  public int sendRecPayments(Account paramAccount, RecPayments paramRecPayments, Payees paramPayees)
  {
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".sendRecPayments:");
    }
    String str = "sendRecPayments";
    RecPayment localRecPayment = null;
    RecPmtInfo localRecPmtInfo = null;
    Accounts localAccounts = new Accounts();
    boolean bool = true;
    localAccounts.add(paramAccount);
    ArrayList localArrayList = new ArrayList();
    BPWServices localBPWServices = null;
    try
    {
      localBPWServices = getBPWHandler();
    }
    catch (Exception localException1)
    {
      try
      {
        DebugLog.throwing("sendRecPayments", localException1);
        logError(1020);
        return 1020;
      }
      catch (Exception localException2)
      {
        DebugLog.throwing("sendRecPayments", localException1);
      }
    }
    int i = 0;
    for (int j = 0; j < paramRecPayments.size(); j++)
    {
      localRecPmtInfo = new RecPmtInfo();
      localRecPayment = (RecPayment)paramRecPayments.get(j);
      localRecPayment.setTRNUID(getUniqueSeqNum(this.userID));
      BeansConverter.setPmtInfoFromPmt(localRecPmtInfo, localRecPayment);
      localRecPmtInfo.setPaymentType("Repetitive");
      try
      {
        localRecPmtInfo = (RecPmtInfo)localBPWServices.addBillPayment(localRecPmtInfo);
        localArrayList.add(j, localRecPmtInfo);
      }
      catch (Exception localException4)
      {
        DebugLog.throwing("sendRecPayments", localException4);
        logError(1);
        return 1;
      }
    }
    for (j = 0; j < localArrayList.size(); j++)
    {
      localRecPmtInfo = (RecPmtInfo)localArrayList.get(j);
      BeansConverter.setPmtFromPmtInfo(localRecPayment, localRecPmtInfo, paramPayees, localAccounts, bool);
      i = a(localRecPayment, localRecPmtInfo);
      paramRecPayments.set(j, localRecPayment);
      if (i != 0)
      {
        logError(i);
        break;
      }
    }
    try
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    catch (Exception localException3)
    {
      DebugLog.throwing("sendRecPayments", localException3);
      logError(1020);
      return 1020;
    }
    return i;
  }
  
  public int deleteRecPayment(RecPayment paramRecPayment)
  {
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".deleteRecPayment:");
    }
    String str = "deleteRecPayment";
    RecPmtInfo localRecPmtInfo = null;
    boolean bool = true;
    BPWServices localBPWServices = null;
    try
    {
      localBPWServices = getBPWHandler();
    }
    catch (Exception localException1)
    {
      try
      {
        DebugLog.throwing("deleteRecPayment", localException1);
        logError(1020);
        return 1020;
      }
      catch (Exception localException2)
      {
        DebugLog.throwing("deleteRecPayment", localException1);
      }
    }
    int i = 0;
    localRecPmtInfo = new RecPmtInfo();
    paramRecPayment.setTRNUID(getUniqueSeqNum(this.userID));
    BeansConverter.setPmtInfoFromPmt(localRecPmtInfo, paramRecPayment);
    localRecPmtInfo.setPaymentType("Repetitive");
    try
    {
      localRecPmtInfo = (RecPmtInfo)localBPWServices.deleteBillPayment(localRecPmtInfo);
    }
    catch (Exception localException3)
    {
      DebugLog.throwing("deleteRecPayment", localException3);
      logError(1);
      return 1;
    }
    BeansConverter.setPmtFromPmtInfo(paramRecPayment, localRecPmtInfo, this.payees, null, bool);
    i = a(paramRecPayment, localRecPmtInfo);
    if (i != 0) {
      logError(i);
    }
    try
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    catch (Exception localException4)
    {
      DebugLog.throwing("deleteRecPayment", localException4);
      logError(1020);
      return 1020;
    }
    return i;
  }
  
  public int modifyRecPayment(Account paramAccount, RecPayment paramRecPayment, HashMap paramHashMap)
  {
    return modifyRecPayment(paramAccount, paramRecPayment);
  }
  
  public int modifyRecPayment(Account paramAccount, RecPayment paramRecPayment)
  {
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".modifyRecPayment:");
    }
    String str = "modifyRecPayment";
    RecPmtInfo localRecPmtInfo = null;
    boolean bool = true;
    Accounts localAccounts = new Accounts();
    localAccounts.add(paramAccount);
    BPWServices localBPWServices = null;
    try
    {
      localBPWServices = getBPWHandler();
    }
    catch (Exception localException1)
    {
      try
      {
        DebugLog.throwing("modifyRecPayment", localException1);
        logError(1020);
        return 1020;
      }
      catch (Exception localException2)
      {
        DebugLog.throwing("modifyRecPayment", localException1);
      }
    }
    int i = 0;
    localRecPmtInfo = new RecPmtInfo();
    paramRecPayment.setTRNUID(getUniqueSeqNum(this.userID));
    BeansConverter.setPmtInfoFromPmt(localRecPmtInfo, paramRecPayment);
    localRecPmtInfo.setPaymentType("Repetitive");
    try
    {
      localRecPmtInfo = (RecPmtInfo)localBPWServices.modifyBillPayment(localRecPmtInfo);
    }
    catch (Exception localException3)
    {
      DebugLog.throwing("modifyRecPayment", localException3);
      logError(1);
      return 1;
    }
    BeansConverter.setPmtFromPmtInfo(paramRecPayment, localRecPmtInfo, this.payees, localAccounts, bool);
    i = a(paramRecPayment, localRecPmtInfo);
    if (i != 0) {
      logError(i);
    }
    try
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    catch (Exception localException4)
    {
      DebugLog.throwing("modifyRecPayment", localException4);
      logError(1020);
      return 1020;
    }
    return i;
  }
  
  public int skipRecPayment(Account paramAccount, RecPayment paramRecPayment)
  {
    int i = 0;
    if (i != 0) {
      return 0;
    }
    return this.lastError;
  }
  
  public Payments getPagedPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    paramPagingContext.setDirection("FIRST");
    return a(getProfileID(), paramAccounts, paramPayees, paramPagingContext, 2, paramHashMap);
  }
  
  public Payments getNextPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    paramPagingContext.setDirection("NEXT");
    return a(getProfileID(), paramAccounts, paramPayees, paramPagingContext, 2, paramHashMap);
  }
  
  public Payments getPreviousPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    paramPagingContext.setDirection("PREVIOUS");
    return a(getProfileID(), paramAccounts, paramPayees, paramPagingContext, 2, paramHashMap);
  }
  
  public Payments getPagedCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    paramPagingContext.setDirection("FIRST");
    return a(getProfileID(), paramAccounts, paramPayees, paramPagingContext, 5, paramHashMap);
  }
  
  public Payments getNextCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    paramPagingContext.setDirection("NEXT");
    return a(getProfileID(), paramAccounts, paramPayees, paramPagingContext, 5, paramHashMap);
  }
  
  public Payments getPreviousCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    paramPagingContext.setDirection("PREVIOUS");
    return a(getProfileID(), paramAccounts, paramPayees, paramPagingContext, 5, paramHashMap);
  }
  
  private Payments a(String paramString, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, int paramInt, HashMap paramHashMap)
    throws Exception
  {
    if (paramAccounts == null) {
      return null;
    }
    boolean bool1 = false;
    boolean bool2 = false;
    Payments localPayments = new Payments();
    localPayments.setPagingContext(paramPagingContext);
    int i = 10;
    try
    {
      i = jdMethod_do(paramHashMap);
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("getPaymentsPage ", localException1);
    }
    BPWHist localBPWHist = new BPWHist();
    localBPWHist.setPageSize(i);
    localBPWHist.setVersion("200");
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null) {
      localHashMap = new HashMap();
    }
    paramPagingContext.setMap(localHashMap);
    Object localObject2;
    Object localObject3;
    if (paramPagingContext.getDirection().equalsIgnoreCase("FIRST"))
    {
      localBPWHist.setCursorId(null);
      localBPWHist.setHistId(null);
      localBPWHist.setCustId(paramString);
      localObject1 = new ArrayList();
      ((ArrayList)localObject1).add("0");
      localHashMap.put("PREV", localObject1);
      bool1 = true;
      paramPagingContext.setFirstIndex(0L);
      paramPagingContext.setLastIndex(0L);
      if (paramInt == 2)
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localBPWHist.setStartDate((String)localObject2);
        }
        else
        {
          localObject2 = GregorianCalendar.getInstance();
          localObject3 = formatDate.format(((Calendar)localObject2).getTime());
          localBPWHist.setStartDate((String)localObject3);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localBPWHist.setEndDate((String)localObject2);
        }
        localBPWHist.setRequiredStatus("WILLPROCESSON,FUNDSALLOCATED,BATCH_INPROCESS");
      }
      else if (paramInt == 5)
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localBPWHist.setStartDate((String)localObject2);
        }
        else
        {
          localObject2 = GregorianCalendar.getInstance();
          ((Calendar)localObject2).add(5, -90);
          localObject3 = formatDate.format(((Calendar)localObject2).getTime());
          localBPWHist.setStartDate((String)localObject3);
        }
        localBPWHist.setRequiredStatus("PROCESSEDON,POSTEDON,NOFUNDSON,FAILEDON,LIMIT_CHECK_FAILED,LIMIT_REVERT_FAILED,APPROVAL_FAILED");
      }
      else
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localBPWHist.setStartDate((String)localObject2);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localBPWHist.setEndDate((String)localObject2);
        }
        if (paramHashMap.get("Statuses") != null) {
          localBPWHist.setRequiredStatus((String)paramHashMap.get("Statuses"));
        }
      }
    }
    else
    {
      int j;
      if (paramPagingContext.getDirection().equalsIgnoreCase("NEXT"))
      {
        localObject1 = (String)localHashMap.get("HISTID");
        localObject2 = (String)localHashMap.get("CURSORID");
        localObject3 = (ArrayList)localHashMap.get("PREV");
        j = new Long(paramPagingContext.getFirstIndex()).intValue();
        j++;
        paramPagingContext.setFirstIndex(j);
        paramPagingContext.setLastIndex(paramPagingContext.getLastIndex() + i);
        if (j >= ((ArrayList)localObject3).size()) {
          ((ArrayList)localObject3).add(localObject2);
        } else {
          ((ArrayList)localObject3).set(j, localObject2);
        }
        localHashMap.put("PREV", localObject3);
        localBPWHist.setCursorId((String)localObject2);
        localBPWHist.setHistId((String)localObject1);
        bool1 = false;
      }
      else if (paramPagingContext.getDirection().equalsIgnoreCase("PREVIOUS"))
      {
        localObject1 = (String)localHashMap.get("HISTID");
        localObject2 = (String)localHashMap.get("CURSORID");
        localObject3 = (ArrayList)localHashMap.get("PREV");
        j = new Long(paramPagingContext.getFirstIndex()).intValue();
        if (j != 0) {
          j--;
        }
        paramPagingContext.setFirstIndex(j);
        paramPagingContext.setLastIndex(paramPagingContext.getLastIndex() - i);
        if ((localObject3 != null) && (((ArrayList)localObject3).size() > 0))
        {
          localObject2 = (String)((ArrayList)localObject3).get(j);
          if ("0".equals(localObject2)) {
            bool1 = true;
          }
          localHashMap.put("PREV", localObject3);
        }
        localBPWHist.setCursorId((String)localObject2);
        localBPWHist.setHistId((String)localObject1);
      }
    }
    Object localObject1 = getOFXHandler();
    try
    {
      localBPWHist = ((IOFX200BPWServices)localObject1).getPmtHistory(localBPWHist);
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("getPaymentsPage ", localException2);
      localObject3 = localException2.toString();
      if ((localObject3 != null) && (((String)localObject3).indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localObject1 != null) {
        removeOFXHandler((IOFX200BPWServices)localObject1);
      }
    }
    ArrayList localArrayList = new ArrayList();
    if (localBPWHist != null)
    {
      localObject3 = null;
      try
      {
        localBPWHist.setTrans(ArrayUtil.convertReferences(localBPWHist.getTrans(), new PmtInfo[0].getClass()));
      }
      catch (Exception localException3) {}
      if ((localBPWHist.getTrans() instanceof PmtInfo[])) {
        localObject3 = (PmtInfo[])localBPWHist.getTrans();
      } else {
        DebugLog.log("getPaymentsPage: NOT PmtInfo object");
      }
      PmtInfo localPmtInfo = null;
      localHashMap.put("HISTID", localBPWHist.getHistId());
      localHashMap.put("CURSORID", localBPWHist.getCursorId());
      RecPayments localRecPayments = (RecPayments)paramHashMap.get("RecPayments");
      RecPayment localRecPayment1 = null;
      if (localRecPayments == null)
      {
        localRecPayments = new RecPayments();
        paramHashMap.put("RecPayments", localRecPayments);
      }
      Object localObject5;
      Object localObject6;
      if (localObject3 != null) {
        for (int k = 0; k < localObject3.length; k++)
        {
          localObject5 = (Payment)localPayments.create();
          localPmtInfo = localObject3[k];
          BeansConverter.setPmtFromPmtInfo((Payment)localObject5, localPmtInfo, paramPayees, paramAccounts, true);
          if ((!this.restrictAccounts) && (!Boolean.valueOf(((Payment)localObject5).getAccountEntitled()).booleanValue()))
          {
            localObject6 = localPmtInfo.AcctDebitType;
            int n = getBPWAccountType((String)localObject6);
            String str = localPmtInfo.AcctDebitID;
            Account localAccount = paramAccounts.createNoAdd(str, n);
            ((Payment)localObject5).setAccount(localAccount);
          }
          if ((localPmtInfo.RecSrvrTID != null) && (localPmtInfo.RecSrvrTID.length() > 0))
          {
            localRecPayment1 = localRecPayments.getByRecID(localPmtInfo.RecSrvrTID);
            if ((localRecPayment1 == null) && (!localArrayList.contains(localPmtInfo.RecSrvrTID))) {
              localArrayList.add(localPmtInfo.RecSrvrTID);
            }
            if (localRecPayment1 != null)
            {
              ((Payment)localObject5).set("Frequency", localRecPayment1.getFrequency());
              ((Payment)localObject5).set("NumberPayments", localRecPayment1.getNumberPayments());
            }
          }
          if (((localPmtInfo.SrvrTID == null) || (localPmtInfo.SrvrTID.length() == 0)) && (localPmtInfo.RecSrvrTID != null) && (localPmtInfo.RecSrvrTID.length() > 0)) {
            ((Payment)localObject5).setID("rec_" + localPmtInfo.RecSrvrTID + "_" + k);
          }
        }
      } else {
        bool2 = true;
      }
      if ((localObject3 == null) || (paramPagingContext.getLastIndex() + localPayments.size() >= localBPWHist.getTotalTrans())) {
        bool2 = true;
      }
      if (localArrayList.size() > 0)
      {
        localObject1 = getOFXHandler();
        RecPmtInfo[] arrayOfRecPmtInfo = null;
        try
        {
          localObject5 = (String[])localArrayList.toArray(new String[0]);
          arrayOfRecPmtInfo = ((IOFX200BPWServices)localObject1).getRecPmtById((String[])localObject5);
        }
        catch (Exception localException4)
        {
          DebugLog.throwing("getPaymentsPage ", localException4);
          localObject6 = localPayments;
          return localObject6;
        }
        finally
        {
          if (localObject1 != null) {
            removeOFXHandler((IOFX200BPWServices)localObject1);
          }
        }
        if (arrayOfRecPmtInfo != null)
        {
          for (int m = 0; m < arrayOfRecPmtInfo.length; m++)
          {
            RecPmtInfo localRecPmtInfo = arrayOfRecPmtInfo[m];
            RecPayment localRecPayment2 = (RecPayment)localRecPayments.create();
            setRecPmtFromRecPmtInfo(localRecPayment2, localRecPmtInfo, localPayments);
          }
          localPayments.setFilter("All");
        }
      }
    }
    localPayments.getPagingContext().setLastPage(bool2);
    localPayments.getPagingContext().setFirstPage(bool1);
    return localPayments;
  }
  
  public static void setRecPmtFromRecPmtInfo(RecPayment paramRecPayment, RecPmtInfo paramRecPmtInfo, Payments paramPayments)
  {
    paramRecPayment.setFrequency(BeansConverter.mapBPWFreqToFreq(paramRecPmtInfo.getRecFrequencyValue()));
    paramRecPayment.setNumberPayments(paramRecPmtInfo.InstanceCount);
    paramRecPayment.setTrackingID(paramRecPmtInfo.LogID);
    paramRecPayment.setSubmittedBy(paramRecPmtInfo.submittedBy);
    paramPayments.setFilter("RECPAYMENTID=" + paramRecPmtInfo.RecSrvrTID);
    if (paramPayments.size() > 0)
    {
      localObject1 = paramPayments.iterator();
      int i = 1;
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Payment)((Iterator)localObject1).next();
        if (i != 0)
        {
          i = 0;
          paramRecPayment.set((Payment)localObject2);
        }
        ((Payment)localObject2).set("Frequency", paramRecPayment.getFrequency());
        ((Payment)localObject2).set("NumberPayments", paramRecPayment.getNumberPayments());
      }
    }
    paramRecPayment.setID(paramRecPmtInfo.RecSrvrTID);
    paramRecPayment.setTrackingID(paramRecPmtInfo.LogID);
    paramRecPayment.setRecPaymentID(paramRecPmtInfo.RecSrvrTID);
    paramRecPayment.setAmount(new BigDecimal(paramRecPmtInfo.getAmt()));
    Object localObject1 = paramRecPayment.getDateFormat();
    if (localObject1 == null) {
      localObject1 = "yyyyMMdd";
    }
    paramRecPayment.setDateFormat("yyyyMMdd");
    Object localObject2 = String.valueOf(paramRecPmtInfo.StartDate);
    if ((localObject2 != null) && (((String)localObject2).length() > 8)) {
      localObject2 = ((String)localObject2).substring(0, 8);
    }
    paramRecPayment.setPayDate((String)localObject2);
    paramRecPayment.setDateFormat((String)localObject1);
    paramRecPayment.setMemo(paramRecPmtInfo.Memo);
  }
  
  protected Payments filterPaymentsInReport(SecureUser paramSecureUser, Payments paramPayments, HashMap paramHashMap)
  {
    return paramPayments;
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "BillPay.getReportData";
    ReportResult localReportResult = null;
    Object localObject1 = null;
    Payments localPayments1 = new Payments();
    Payments localPayments2 = new Payments(paramReportCriteria.getLocale());
    Object localObject2 = null;
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if (localProperties == null)
    {
      str2 = "No search criteria found.  Report cannot be run.";
      DebugLog.log(str2);
      throw new Exception(str2);
    }
    String str2 = (String)localProperties.get("Account");
    Accounts localAccounts = (Accounts)paramHashMap.get("ACCOUNTS");
    if ((str2 == null) || ("".equals(str2)))
    {
      localObject3 = "Account search criteria not specified, all accounts will be used.";
      DebugLog.log((String)localObject3);
      localObject1 = localAccounts;
    }
    else if (jdMethod_if(str2))
    {
      localObject1 = localAccounts;
    }
    else
    {
      localObject1 = new Accounts();
      localObject3 = new StringTokenizer(str2, ",");
      while (((StringTokenizer)localObject3).hasMoreTokens())
      {
        localObject4 = ((StringTokenizer)localObject3).nextToken();
        ((Accounts)localObject1).add(localAccounts.getByID((String)localObject4));
      }
    }
    ((Accounts)localObject1).setLocale(paramReportCriteria.getLocale());
    Object localObject3 = (Payees)paramHashMap.get("PAYEES");
    Object localObject4 = paramReportCriteria.getReportOptions();
    if (localObject4 == null)
    {
      DebugLog.log("Missing report options");
      throw new Exception("Missing report options");
    }
    String str3 = ((Properties)localObject4).getProperty("REPORTTYPE");
    if (str3 == null)
    {
      DebugLog.log("Report type not specified");
      throw new Exception("Report type not specified");
    }
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
      str4 = "Unable to calcualte the date ranges. A report cannot be run.";
      DebugLog.log(str4);
      throw new Exception(str4);
    }
    com.ffusion.util.beans.DateTime localDateTime1 = null;
    String str4 = localProperties.getProperty("StartDate");
    if ((str4 != null) && (str4.length() > 0)) {
      try
      {
        localDateTime1 = new com.ffusion.util.beans.DateTime(DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").parse(str4), Locale.getDefault(), "MM/dd/yyyy HH:mm:ss");
      }
      catch (Exception localException1) {}
    } else {
      paramReportCriteria.setHiddenSearchCriterion("StartDate", true);
    }
    com.ffusion.util.beans.DateTime localDateTime2 = null;
    String str5 = localProperties.getProperty("EndDate");
    if ((str5 != null) && (str5.length() > 0)) {
      try
      {
        localDateTime2 = new com.ffusion.util.beans.DateTime(DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").parse(str5), Locale.getDefault(), "MM/dd/yyyy HH:mm:ss");
      }
      catch (Exception localException2) {}
    } else {
      paramReportCriteria.setHiddenSearchCriterion("EndDate", true);
    }
    PagingContext localPagingContext = new PagingContext(localDateTime1, localDateTime2);
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    Locale localLocale1 = paramSecureUser.getLocale();
    if (localLocale1 == null) {
      localLocale1 = Locale.getDefault();
    }
    paramHashMap.put("PAGESIZE", "1000");
    Object localObject9;
    Object localObject10;
    if (str3.equals("Payment History Report"))
    {
      paramHashMap.put("Statuses", "PROCESSEDON,POSTEDON,NOFUNDSON,FAILEDON,LIMIT_CHECK_FAILED,LIMIT_REVERT_FAILED,APPROVAL_FAILED");
    }
    else if (str3.equals("Unprocessed Payment Report"))
    {
      paramHashMap.put("Statuses", "WILLPROCESSON,FUNDSALLOCATED,BATCH_INPROCESS");
    }
    else if (str3.equals("Expense by Payee - Summary"))
    {
      paramHashMap.put("Statuses", "PROCESSEDON");
    }
    else if (str3.equals("Expense by Payee - Detail"))
    {
      paramHashMap.put("Statuses", "PROCESSEDON");
    }
    else if (str3.equals("BillPay By Status"))
    {
      localObject5 = localProperties.getProperty("Status");
      if (localObject5 == null) {
        localObject5 = "";
      }
      if ("".equals(localObject5))
      {
        paramReportCriteria.setDisplayValue("Status", ReportConsts.getText(10018, localLocale1));
      }
      else
      {
        localObject6 = ((String)localObject5).substring(0, ((String)localObject5).length() - 1);
        localObject7 = new StringBuffer();
        localObject8 = new StringTokenizer((String)localObject5, ",");
        while (((StringTokenizer)localObject8).hasMoreTokens())
        {
          ((StringBuffer)localObject7).append(((StringTokenizer)localObject8).nextToken());
          if (((StringTokenizer)localObject8).hasMoreTokens()) {
            ((StringBuffer)localObject7).append(", ");
          }
        }
        paramReportCriteria.setDisplayValue("Status", ((StringBuffer)localObject7).toString());
      }
      Object localObject6 = new StringTokenizer((String)localObject5, ",");
      localObject7 = null;
      localObject8 = new ArrayList();
      while (((StringTokenizer)localObject6).hasMoreTokens())
      {
        localObject7 = ((StringTokenizer)localObject6).nextToken();
        if (localObject7 != null) {
          if (((String)localObject7).equals("Completed"))
          {
            ((ArrayList)localObject8).add("POSTEDON");
            ((ArrayList)localObject8).add("PROCESSEDON");
          }
          else if (((String)localObject7).equals("No Funds Available"))
          {
            ((ArrayList)localObject8).add("NOFUNDSON");
          }
          else if (((String)localObject7).equals("Failed"))
          {
            ((ArrayList)localObject8).add("FAILEDON");
          }
          else if (((String)localObject7).equals("Scheduled"))
          {
            ((ArrayList)localObject8).add("WILLPROCESSON");
          }
          else if (((String)localObject7).equals("Batch In Process"))
          {
            ((ArrayList)localObject8).add("BATCH_INPROCESS");
          }
          else if (((String)localObject7).equals("Limit Check Failed"))
          {
            ((ArrayList)localObject8).add("LIMIT_CHECK_FAILED");
          }
          else if (((String)localObject7).equals("Limit Revert Failed"))
          {
            ((ArrayList)localObject8).add("LIMIT_REVERT_FAILED");
          }
          else if (((String)localObject7).equals("Approval Failed"))
          {
            ((ArrayList)localObject8).add("APPROVAL_FAILED");
          }
          else if (((String)localObject7).equals("Funds Allocated"))
          {
            ((ArrayList)localObject8).add("FUNDSALLOCATED");
          }
          else if (((String)localObject7).equals("Cancelled"))
          {
            ((ArrayList)localObject8).add("CANCELEDON");
          }
          else if (((String)localObject7).equals("Approval Pending"))
          {
            ((ArrayList)localObject8).add("APPROVAL_PENDING");
          }
          else if (((String)localObject7).equals("Approval Rejected"))
          {
            ((ArrayList)localObject8).add("APPROVAL_REJECTED");
          }
        }
      }
      if ((localObject8 != null) && (((ArrayList)localObject8).size() > 0))
      {
        localObject9 = "";
        localObject10 = ((ArrayList)localObject8).iterator();
        while (((Iterator)localObject10).hasNext()) {
          localObject9 = (String)localObject9 + (String)((Iterator)localObject10).next() + ",";
        }
        localObject9 = ((String)localObject9).substring(0, ((String)localObject9).length() - 1);
        paramHashMap.put("Statuses", localObject9);
      }
      else
      {
        paramHashMap.put("Statuses", "PROCESSEDON,POSTEDON,NOFUNDSON,FAILEDON,WILLPROCESSON,FUNDSALLOCATED,BATCH_INPROCESS,LIMIT_CHECK_FAILED,LIMIT_REVERT_FAILED,APPROVAL_FAILED,CANCELEDON,APPROVAL_PENDING,APPROVAL_REJECTED");
      }
    }
    Object localObject5 = new StringBuffer();
    Object localObject11;
    if (paramHashMap.get("Statuses") != null)
    {
      localPayments1 = a(getProfileID(), (Accounts)localObject1, (Payees)localObject3, localPagingContext, 0, paramHashMap);
      boolean bool1 = false;
      if ((localProperties.getProperty("Account") != null) && (!jdMethod_if(localProperties.getProperty("Account"))))
      {
        setFiltersOnList(localPayments1, localProperties, "Account", "ACCOUNTID", "=", bool1);
        bool1 = true;
      }
      if (localProperties.getProperty("Payee") != null)
      {
        setFiltersOnList(localPayments1, localProperties, "Payee", "PAYEEID", "=", bool1);
        bool1 = true;
      }
      if (localProperties.getProperty("AmountFrom") != null)
      {
        setFiltersOnList(localPayments1, localProperties, "AmountFrom", "AMOUNT", ">=", bool1);
        bool1 = true;
      }
      setFiltersOnList(localPayments1, localProperties, "AmountTo", "AMOUNT", "<=", bool1);
      localPayments1 = filterPaymentsInReport(paramSecureUser, localPayments1, paramHashMap);
      localObject7 = paramReportCriteria.getSortCriteria();
      if ((localObject7 != null) && (((ReportSortCriteria)localObject7).size() > 0))
      {
        ((ReportSortCriteria)localObject7).setSortedBy("ORDINAL");
        localObject8 = ((ReportSortCriteria)localObject7).iterator();
        while (((Iterator)localObject8).hasNext())
        {
          localObject9 = (ReportSortCriterion)((Iterator)localObject8).next();
          localObject10 = ((ReportSortCriterion)localObject9).getName();
          localObject11 = ((ReportSortCriterion)localObject9).getAsc() ? "ASC" : "DESC";
          ((StringBuffer)localObject5).append((String)localObject10);
          ((StringBuffer)localObject5).append(" ");
          ((StringBuffer)localObject5).append((String)localObject11);
          if (((Iterator)localObject8).hasNext()) {
            ((StringBuffer)localObject5).append(", ");
          }
        }
      }
    }
    Locale localLocale2 = paramSecureUser.getLocale();
    Object localObject7 = null;
    localObject7 = paramReportCriteria.getSearchCriteria().getProperty("Account");
    if ((localObject7 == null) || (((String)localObject7).length() <= 0) || (jdMethod_if((String)localObject7))) {
      paramReportCriteria.setDisplayValue("Account", ReportConsts.getText(10024, localLocale2));
    } else {
      paramReportCriteria.setDisplayValue("Account", a(localAccounts, (String)localObject7));
    }
    localObject7 = paramReportCriteria.getSearchCriteria().getProperty("Payee");
    if ((localObject7 == null) || ("".equals(localObject7)))
    {
      paramReportCriteria.setDisplayValue("Payee", ReportConsts.getText(10043, localLocale2));
    }
    else
    {
      localObject8 = (Payee)((Payees)localObject3).getFirstByFilter("ID=" + (String)localObject7);
      paramReportCriteria.setDisplayValue("Payee", ((Payee)localObject8).getName());
    }
    localObject7 = paramReportCriteria.getSearchCriteria().getProperty("AmountFrom");
    paramReportCriteria.setHiddenSearchCriterion("AmountFrom", (localObject7 == null) || ("".equals(localObject7)));
    localObject7 = paramReportCriteria.getSearchCriteria().getProperty("AmountTo");
    paramReportCriteria.setHiddenSearchCriterion("AmountTo", (localObject7 == null) || ("".equals(localObject7)));
    Object localObject8 = paramReportCriteria.getReportOptions().getProperty("MAXDISPLAYSIZE");
    int i = 0;
    if (localObject8 != null) {
      i = Integer.parseInt((String)localObject8);
    }
    try
    {
      localReportResult = new ReportResult(localLocale2);
      Object localObject12;
      Object localObject13;
      Object localObject18;
      Object localObject19;
      Object localObject20;
      Object localObject23;
      Object localObject24;
      Object localObject25;
      Object localObject26;
      Object localObject27;
      Object localObject28;
      Object localObject21;
      if ((str3.equals("Payment History Report")) || (str3.equals("Unprocessed Payment Report")))
      {
        boolean bool2 = str3.equals("Payment History Report");
        localReportResult.init(paramReportCriteria);
        localObject11 = localPayments1.iterator();
        localObject12 = new TreeMap();
        localObject13 = new Currency("0.00", paramSecureUser.getBaseCurrency(), Locale.getDefault());
        int j = 0;
        Object localObject15;
        while (((Iterator)localObject11).hasNext())
        {
          localObject15 = (Payment)((Iterator)localObject11).next();
          localPayments2.add(localObject15);
          localObject18 = ((Payment)localObject15).getAmountValue().getCurrencyCode();
          localObject19 = (Currency)((TreeMap)localObject12).get(localObject18);
          if (localObject19 == null)
          {
            localObject19 = new Currency(((Payment)localObject15).getAmountValue().getAmountValue(), (String)localObject18, Locale.getDefault());
            j++;
          }
          else
          {
            ((Currency)localObject19).addAmount(((Payment)localObject15).getAmountValue());
          }
          ((TreeMap)localObject12).put(localObject18, localObject19);
          if (((String)localObject18).equals(paramSecureUser.getBaseCurrency())) {
            ((Currency)localObject13).addAmount(((Payment)localObject15).getAmountValue());
          } else {
            ((Currency)localObject13).addAmount(FX.convertToBaseCurrency(paramSecureUser, ((Payment)localObject15).getAmountValue(), new HashMap()));
          }
        }
        if (((StringBuffer)localObject5).toString().length() > 0) {
          localPayments2.setSortedBy(((StringBuffer)localObject5).toString());
        }
        localObject11 = localPayments2.iterator();
        if (((Iterator)localObject11).hasNext())
        {
          localObject15 = new ReportResult(localLocale2);
          localReportResult.addSubReport((ReportResult)localObject15);
          localObject18 = new ReportDataDimensions(localLocale2);
          ((ReportDataDimensions)localObject18).setNumColumns(7);
          ((ReportDataDimensions)localObject18).setNumRows(-1);
          ((ReportResult)localObject15).setDataDimensions((ReportDataDimensions)localObject18);
          localObject19 = null;
          localObject20 = null;
          localObject19 = new ReportColumn(localLocale2);
          localObject20 = new ArrayList();
          ((ArrayList)localObject20).add(ReportConsts.getColumnName(bool2 ? 2400 : 2410, localLocale2));
          ((ArrayList)localObject20).add(ReportConsts.getColumnName(bool2 ? 2401 : 2411, localLocale2));
          ((ReportColumn)localObject19).setLabels((ArrayList)localObject20);
          localObject20 = null;
          ((ReportColumn)localObject19).setDataType("com.ffusion.util.beans.DateTime");
          ((ReportColumn)localObject19).setWidthAsPercent(10);
          ((ReportResult)localObject15).addColumn((ReportColumn)localObject19);
          localObject19 = null;
          if (bool2)
          {
            localObject19 = new ReportColumn(localLocale2);
            localObject20 = new ArrayList();
            ((ArrayList)localObject20).add(ReportConsts.getColumnName(2402, localLocale2));
            ((ArrayList)localObject20).add(ReportConsts.getColumnName(2401, localLocale2));
            ((ReportColumn)localObject19).setLabels((ArrayList)localObject20);
            localObject20 = null;
            ((ReportColumn)localObject19).setDataType("com.ffusion.util.beans.DateTime");
            ((ReportColumn)localObject19).setWidthAsPercent(10);
            ((ReportResult)localObject15).addColumn((ReportColumn)localObject19);
            localObject19 = null;
          }
          jdMethod_if((ReportResult)localObject15, bool2 ? 2403 : 2412, "java.lang.String", 15, null);
          jdMethod_if((ReportResult)localObject15, bool2 ? 2404 : 2413, "java.lang.String", 15, null);
          jdMethod_if((ReportResult)localObject15, bool2 ? 2405 : 2414, "java.lang.String", 25, null);
          jdMethod_if((ReportResult)localObject15, bool2 ? 2406 : 2415, "java.lang.String", 15, null);
          if (!bool2) {
            jdMethod_if((ReportResult)localObject15, 2416, "java.lang.String", 10, null);
          }
          jdMethod_if((ReportResult)localObject15, bool2 ? 2407 : 2417, "java.lang.String", 10, "RIGHT");
          for (int i1 = 0; (((Iterator)localObject11).hasNext()) && ((i == 0) || (i1 < i)); i1++)
          {
            localObject23 = (Payment)((Iterator)localObject11).next();
            localObject24 = new ReportRow(localLocale2);
            if (i1 % 2 == 1) {
              ((ReportRow)localObject24).set("CELLBACKGROUND", "reportDataShaded");
            }
            localObject25 = new ReportDataItems(localLocale2);
            localObject26 = null;
            ((ReportRow)localObject24).setDataItems((ReportDataItems)localObject25);
            if (bool2)
            {
              localObject26 = ((ReportDataItems)localObject25).add();
              ((ReportDataItem)localObject26).setData(((Payment)localObject23).get("IssueDate"));
              localObject26 = null;
            }
            localObject26 = ((ReportDataItems)localObject25).add();
            ((ReportDataItem)localObject26).setData(((Payment)localObject23).getPayDateValue());
            localObject26 = null;
            localObject26 = ((ReportDataItems)localObject25).add();
            ((ReportDataItem)localObject26).setData(((Payment)localObject23).getPayee().getName());
            localObject26 = null;
            localObject26 = ((ReportDataItems)localObject25).add();
            ((ReportDataItem)localObject26).setData(((Payment)localObject23).getMemo());
            localObject26 = null;
            localObject26 = ((ReportDataItems)localObject25).add();
            ((ReportDataItem)localObject26).setData(((Payment)localObject23).getAccount().getDisplayTextRoutingNumNickNameCurrency());
            localObject26 = null;
            localObject26 = ((ReportDataItems)localObject25).add();
            ((ReportDataItem)localObject26).setData(((Payment)localObject23).getReferenceNumber());
            localObject26 = null;
            if (!bool2)
            {
              localObject26 = ((ReportDataItems)localObject25).add();
              ((ReportDataItem)localObject26).setData(((Payment)localObject23).getStatusName());
              localObject26 = null;
            }
            localObject26 = ((ReportDataItems)localObject25).add();
            localObject27 = new Currency(((Payment)localObject23).getAmountValue().getAmountValue(), ((Payment)localObject23).getAmountValue().getCurrencyCode(), Locale.getDefault());
            ((ReportDataItem)localObject26).setData(((Currency)localObject27).getCurrencyStringNoSymbol() + " " + ((Payment)localObject23).getAmountValue().getCurrencyCode());
            localObject26 = null;
            ((ReportResult)localObject15).addRow((ReportRow)localObject24);
            localObject25 = null;
            localObject24 = null;
          }
          if ((i != 0) && (i1 >= i))
          {
            localObject23 = localReportResult.getProperties();
            if (localObject23 == null)
            {
              localObject23 = new HashMap();
              localReportResult.setProperties((HashMap)localObject23);
            }
            ((HashMap)localObject23).put("TRUNCATED", new Integer(i1));
          }
          if (j > 1)
          {
            localObject23 = ((TreeMap)localObject12).keySet().iterator();
            while (((Iterator)localObject23).hasNext())
            {
              localObject24 = (String)((Iterator)localObject23).next();
              localObject25 = (Currency)((TreeMap)localObject12).get(localObject24);
              localObject15 = new ReportResult(localLocale2);
              localReportResult.addSubReport((ReportResult)localObject15);
              localObject18 = new ReportDataDimensions(localLocale2);
              ((ReportDataDimensions)localObject18).setNumColumns(2);
              ((ReportDataDimensions)localObject18).setNumRows(1);
              ((ReportResult)localObject15).setDataDimensions((ReportDataDimensions)localObject18);
              jdMethod_if((ReportResult)localObject15, -1, "java.lang.String", 90, "RIGHT");
              jdMethod_if((ReportResult)localObject15, -1, "java.lang.String", 10, "RIGHT");
              localObject26 = new ReportRow(localLocale2);
              localObject27 = new ReportDataItems(localLocale2);
              ReportDataItem localReportDataItem1 = null;
              ((ReportRow)localObject26).setDataItems((ReportDataItems)localObject27);
              localReportDataItem1 = ((ReportDataItems)localObject27).add();
              localObject28 = new StringBuffer(ReportConsts.getText(2436, localLocale2));
              ((StringBuffer)localObject28).append(" (");
              ((StringBuffer)localObject28).append(((Currency)localObject25).getCurrencyCode());
              ((StringBuffer)localObject28).append("): ");
              localReportDataItem1.setData(((StringBuffer)localObject28).toString());
              localReportDataItem1 = null;
              localReportDataItem1 = ((ReportDataItems)localObject27).add();
              localReportDataItem1.setData(((Currency)localObject25).getCurrencyStringNoSymbol());
              localReportDataItem1 = null;
              localObject27 = null;
              ((ReportResult)localObject15).addRow((ReportRow)localObject26);
              localObject26 = null;
              localObject15 = null;
            }
          }
          localObject15 = new ReportResult(localLocale2);
          localReportResult.addSubReport((ReportResult)localObject15);
          localObject18 = new ReportDataDimensions(localLocale2);
          ((ReportDataDimensions)localObject18).setNumColumns(2);
          ((ReportDataDimensions)localObject18).setNumRows(1);
          ((ReportResult)localObject15).setDataDimensions((ReportDataDimensions)localObject18);
          jdMethod_if((ReportResult)localObject15, -1, "java.lang.String", 90, "RIGHT");
          jdMethod_if((ReportResult)localObject15, -1, "java.lang.String", 10, "RIGHT");
          localObject23 = new ReportRow(localLocale2);
          localObject24 = new ReportDataItems(localLocale2);
          localObject25 = null;
          ((ReportRow)localObject23).setDataItems((ReportDataItems)localObject24);
          localObject25 = ((ReportDataItems)localObject24).add();
          localObject26 = new StringBuffer(ReportConsts.getText(2441, localLocale2));
          ((StringBuffer)localObject26).append(" (");
          ((StringBuffer)localObject26).append(((Currency)localObject13).getCurrencyCode());
          ((StringBuffer)localObject26).append("): ");
          ((ReportDataItem)localObject25).setData(((StringBuffer)localObject26).toString());
          localObject25 = null;
          localObject25 = ((ReportDataItems)localObject24).add();
          ((ReportDataItem)localObject25).setData(((Currency)localObject13).getCurrencyStringNoSymbol());
          localObject25 = null;
          localObject24 = null;
          ((ReportResult)localObject15).addRow((ReportRow)localObject23);
          localObject23 = null;
          localObject15 = null;
        }
        else
        {
          localObject15 = null;
          localObject18 = new ReportDataDimensions(localLocale2);
          ((ReportDataDimensions)localObject18).setNumColumns(1);
          ((ReportDataDimensions)localObject18).setNumRows(1);
          localReportResult.setDataDimensions((ReportDataDimensions)localObject18);
          localObject15 = new ReportColumn(localLocale2);
          ((ReportColumn)localObject15).setDataType("java.lang.String");
          ((ReportColumn)localObject15).setWidthAsPercent(100);
          localReportResult.addColumn((ReportColumn)localObject15);
          localObject15 = null;
          localObject19 = new ReportRow(localLocale2);
          localObject20 = new ReportDataItems(localLocale2);
          localObject21 = null;
          ((ReportRow)localObject19).setDataItems((ReportDataItems)localObject20);
          localObject21 = ((ReportDataItems)localObject20).add();
          ((ReportDataItem)localObject21).setData(ReportConsts.getText(bool2 ? 2431 : 2433, localLocale2));
          localObject21 = null;
          localObject20 = null;
          localReportResult.addRow((ReportRow)localObject19);
          localObject19 = null;
        }
      }
      else
      {
        Iterator localIterator;
        Object localObject14;
        Object localObject22;
        if (str3.equals("Expense by Payee - Summary"))
        {
          localReportResult.init(paramReportCriteria);
          localIterator = localPayments1.iterator();
          localObject11 = new HashMap();
          localObject12 = new TreeMap();
          localObject14 = new Currency("0", Locale.getDefault());
          int k = 0;
          while (localIterator.hasNext())
          {
            localObject18 = (Payment)localIterator.next();
            localObject19 = ((Payment)localObject18).getPayee().getID();
            localObject20 = (Currency)((HashMap)localObject11).get(localObject19);
            if (localObject20 != null)
            {
              localObject13 = localObject20;
              ((Currency)localObject13).addAmount(((Payment)localObject18).getAmountValue());
            }
            else
            {
              localObject13 = ((Payment)localObject18).getAmountValue();
            }
            ((HashMap)localObject11).put(localObject19, localObject13);
            localObject21 = (Currency)((TreeMap)localObject12).get(((Payment)localObject18).getAmountValue().getCurrencyCode());
            if (localObject21 == null) {
              localObject21 = new Currency(((Payment)localObject18).getAmountValue().getAmountValue(), ((Payment)localObject18).getAmountValue().getCurrencyCode(), Locale.getDefault());
            } else {
              ((Currency)localObject21).addAmount(((Payment)localObject18).getAmountValue());
            }
            ((TreeMap)localObject12).put(((Currency)localObject21).getCurrencyCode(), localObject21);
            if (((Currency)localObject21).getCurrencyCode().equals(paramSecureUser.getBaseCurrency()))
            {
              ((Currency)localObject14).addAmount(((Payment)localObject18).getAmountValue());
            }
            else
            {
              ((Currency)localObject14).addAmount(FX.convertToBaseCurrency(paramSecureUser, ((Payment)localObject18).getAmountValue(), new HashMap()));
              k = 1;
            }
          }
          localObject18 = new Payments();
          localIterator = ((HashMap)localObject11).keySet().iterator();
          while (localIterator.hasNext())
          {
            localObject19 = (String)localIterator.next();
            localObject20 = ((Payees)localObject3).getByID((String)localObject19);
            localObject21 = (Currency)((HashMap)localObject11).get(localObject19);
            localObject23 = (Payment)((Payments)localObject18).create();
            ((Payment)localObject23).setAmount((Currency)localObject21);
            ((Payment)localObject23).setPayee((Payee)localObject20);
          }
          if (((StringBuffer)localObject5).toString().length() > 0) {
            ((Payments)localObject18).setSortedBy(((StringBuffer)localObject5).toString());
          }
          localIterator = ((Payments)localObject18).iterator();
          if (localIterator.hasNext())
          {
            localObject19 = new ReportResult(localLocale2);
            localReportResult.addSubReport((ReportResult)localObject19);
            localObject20 = new ReportDataDimensions(localLocale2);
            ((ReportDataDimensions)localObject20).setNumColumns(2);
            ((ReportDataDimensions)localObject20).setNumRows(-1);
            ((ReportResult)localObject19).setDataDimensions((ReportDataDimensions)localObject20);
            jdMethod_if((ReportResult)localObject19, 2420, "java.lang.String", 65, null);
            jdMethod_if((ReportResult)localObject19, 2421, "java.lang.String", 35, "RIGHT");
            for (int i2 = 0; (localIterator.hasNext()) && ((i == 0) || (i2 < i)); i2++)
            {
              localObject23 = (Payment)localIterator.next();
              localObject24 = new ReportRow(localLocale2);
              if (i2 % 2 == 1) {
                ((ReportRow)localObject24).set("CELLBACKGROUND", "reportDataShaded");
              }
              localObject25 = new ReportDataItems(localLocale2);
              localObject26 = null;
              ((ReportRow)localObject24).setDataItems((ReportDataItems)localObject25);
              localObject26 = ((ReportDataItems)localObject25).add();
              ((ReportDataItem)localObject26).setData(((Payment)localObject23).getPayee().getName());
              localObject26 = null;
              localObject26 = ((ReportDataItems)localObject25).add();
              localObject27 = new Currency(((Payment)localObject23).getAmountValue().getAmountValue(), ((Payment)localObject23).getAmountValue().getCurrencyCode(), Locale.getDefault());
              ((ReportDataItem)localObject26).setData(((Currency)localObject27).getCurrencyStringNoSymbol() + " " + ((Payment)localObject23).getAmountValue().getCurrencyCode());
              localObject26 = null;
              ((ReportResult)localObject19).addRow((ReportRow)localObject24);
              localObject25 = null;
              localObject24 = null;
            }
            if ((i != 0) && (i2 >= i))
            {
              localObject23 = localReportResult.getProperties();
              if (localObject23 == null)
              {
                localObject23 = new HashMap();
                localReportResult.setProperties((HashMap)localObject23);
              }
              ((HashMap)localObject23).put("TRUNCATED", new Integer(i2));
            }
            if (k != 0)
            {
              localIterator = ((TreeMap)localObject12).keySet().iterator();
              while (localIterator.hasNext())
              {
                localObject23 = (String)localIterator.next();
                localObject24 = (Currency)((TreeMap)localObject12).get(localObject23);
                localObject19 = new ReportResult(localLocale2);
                localReportResult.addSubReport((ReportResult)localObject19);
                localObject20 = new ReportDataDimensions(localLocale2);
                ((ReportDataDimensions)localObject20).setNumColumns(2);
                ((ReportDataDimensions)localObject20).setNumRows(1);
                ((ReportResult)localObject19).setDataDimensions((ReportDataDimensions)localObject20);
                jdMethod_if((ReportResult)localObject19, -1, "java.lang.String", 65, "RIGHT");
                jdMethod_if((ReportResult)localObject19, -1, "java.lang.String", 35, "RIGHT");
                localObject25 = new ReportRow(localLocale2);
                localObject26 = new ReportDataItems(localLocale2);
                localObject27 = null;
                ((ReportRow)localObject25).setDataItems((ReportDataItems)localObject26);
                localObject27 = ((ReportDataItems)localObject26).add();
                ((ReportDataItem)localObject27).setData(ReportConsts.getText(2436, localLocale2) + " (" + ((Currency)localObject24).getCurrencyCode() + ")");
                localObject27 = null;
                localObject27 = ((ReportDataItems)localObject26).add();
                ((ReportDataItem)localObject27).setData(((Currency)localObject24).getCurrencyStringNoSymbol());
                localObject27 = null;
                localObject26 = null;
                ((ReportResult)localObject19).addRow((ReportRow)localObject25);
                localObject25 = null;
                localObject19 = null;
              }
            }
            localObject19 = new ReportResult(localLocale2);
            localReportResult.addSubReport((ReportResult)localObject19);
            localObject20 = new ReportDataDimensions(localLocale2);
            ((ReportDataDimensions)localObject20).setNumColumns(2);
            ((ReportDataDimensions)localObject20).setNumRows(1);
            ((ReportResult)localObject19).setDataDimensions((ReportDataDimensions)localObject20);
            jdMethod_if((ReportResult)localObject19, -1, "java.lang.String", 65, "RIGHT");
            jdMethod_if((ReportResult)localObject19, -1, "java.lang.String", 35, "RIGHT");
            localObject23 = new ReportRow(localLocale2);
            localObject24 = new ReportDataItems(localLocale2);
            localObject25 = null;
            ((ReportRow)localObject23).setDataItems((ReportDataItems)localObject24);
            localObject25 = ((ReportDataItems)localObject24).add();
            ((ReportDataItem)localObject25).setData(ReportConsts.getText(2441, localLocale2) + " (" + ((Currency)localObject14).getCurrencyCode() + ")");
            localObject25 = null;
            localObject25 = ((ReportDataItems)localObject24).add();
            ((ReportDataItem)localObject25).setData(((Currency)localObject14).getCurrencyStringNoSymbol());
            localObject25 = null;
            localObject24 = null;
            ((ReportResult)localObject19).addRow((ReportRow)localObject23);
            localObject23 = null;
            localObject19 = null;
          }
          else
          {
            localObject19 = new ReportDataDimensions(localLocale2);
            ((ReportDataDimensions)localObject19).setNumColumns(1);
            ((ReportDataDimensions)localObject19).setNumRows(1);
            localReportResult.setDataDimensions((ReportDataDimensions)localObject19);
            localObject20 = null;
            localObject20 = new ReportColumn(localLocale2);
            ((ReportColumn)localObject20).setDataType("java.lang.String");
            ((ReportColumn)localObject20).setWidthAsPercent(100);
            localReportResult.addColumn((ReportColumn)localObject20);
            localObject20 = null;
            localObject22 = new ReportRow(localLocale2);
            localObject23 = new ReportDataItems(localLocale2);
            localObject24 = null;
            ((ReportRow)localObject22).setDataItems((ReportDataItems)localObject23);
            localObject24 = ((ReportDataItems)localObject23).add();
            ((ReportDataItem)localObject24).setData(ReportConsts.getText(2435, localLocale2));
            localObject24 = null;
            localObject23 = null;
            localReportResult.addRow((ReportRow)localObject22);
            localObject22 = null;
          }
        }
        else
        {
          Object localObject17;
          if (str3.equals("Expense by Payee - Detail"))
          {
            localReportResult.init(paramReportCriteria);
            localIterator = localPayments1.iterator();
            localObject11 = new HashMap();
            localObject12 = new HashMap();
            localObject13 = new Currency("0", Locale.getDefault());
            Object localObject16;
            while (localIterator.hasNext())
            {
              localObject14 = (Payment)localIterator.next();
              localObject16 = ((Payment)localObject14).getPayee().getID();
              localObject18 = (Payments)((HashMap)localObject11).get(localObject16);
              localObject19 = ((Payment)localObject14).getAmountValue().getCurrencyCode();
              if (localObject18 != null)
              {
                ((Payments)localObject18).add(localObject14);
                localObject20 = (Currency)((HashMap)localObject12).get(localObject16);
                ((Currency)localObject20).addAmount(((Payment)localObject14).getAmountValue());
                ((Currency)localObject20).setCurrencyCode((String)localObject19);
              }
              else
              {
                localObject18 = new Payments(((Payment)localObject14).getLocale());
                ((Payments)localObject18).add(localObject14);
                localObject20 = new Currency("0", Locale.getDefault());
                ((Currency)localObject20).addAmount(((Payment)localObject14).getAmountValue());
                ((Currency)localObject20).setCurrencyCode((String)localObject19);
                ((HashMap)localObject11).put(localObject16, localObject18);
                ((HashMap)localObject12).put(localObject16, localObject20);
              }
              if (((String)localObject19).equals(paramSecureUser.getBaseCurrency())) {
                ((Currency)localObject13).addAmount(((Payment)localObject14).getAmountValue());
              } else {
                ((Currency)localObject13).addAmount(FX.convertToBaseCurrency(paramSecureUser, ((Payment)localObject14).getAmountValue(), new HashMap()));
              }
            }
            localObject14 = new FilteredList();
            localIterator = ((HashMap)localObject11).keySet().iterator();
            while (localIterator.hasNext())
            {
              localObject16 = (String)localIterator.next();
              localObject18 = (Payments)((HashMap)localObject11).get(localObject16);
              ((FilteredList)localObject14).add(localObject18);
            }
            if (((StringBuffer)localObject5).toString().length() > 0)
            {
              localIterator = ((FilteredList)localObject14).iterator();
              while (localIterator.hasNext())
              {
                localObject16 = (Payments)localIterator.next();
                ((Payments)localObject16).setSortedBy(((StringBuffer)localObject5).toString());
              }
            }
            localIterator = ((FilteredList)localObject14).iterator();
            if (localIterator.hasNext())
            {
              int m = 0;
              while ((localIterator.hasNext()) && ((i == 0) || (m < i)))
              {
                localObject18 = (Payments)localIterator.next();
                localObject19 = (Payment)((Payments)localObject18).get(0);
                localObject20 = ((Payment)localObject19).getPayee().getID();
                localObject22 = (Currency)((HashMap)localObject12).get(localObject20);
                localObject23 = new ReportResult(localLocale2);
                localReportResult.addSubReport((ReportResult)localObject23);
                localObject24 = new ReportHeading(localLocale2);
                ((ReportHeading)localObject24).setLabel(((Payment)localObject19).getPayeeName());
                ((ReportResult)localObject23).setHeading((ReportHeading)localObject24);
                localObject25 = new ReportResult(localLocale2);
                ((ReportResult)localObject23).addSubReport((ReportResult)localObject25);
                localObject26 = new ReportDataDimensions(localLocale2);
                ((ReportResult)localObject25).setDataDimensions((ReportDataDimensions)localObject26);
                ((ReportDataDimensions)localObject26).setNumColumns(5);
                ((ReportDataDimensions)localObject26).setNumRows(-1);
                jdMethod_if((ReportResult)localObject25, 2425, "com.ffusion.util.beans.DateTime", 15, null);
                jdMethod_if((ReportResult)localObject25, 2426, "java.lang.String", 30, null);
                jdMethod_if((ReportResult)localObject25, 2427, "java.lang.String", 25, null);
                jdMethod_if((ReportResult)localObject25, 2428, "java.lang.String", 15, null);
                jdMethod_if((ReportResult)localObject25, 2429, "com.ffusion.beans.Currency", 15, "RIGHT");
                localObject27 = ((Payments)localObject18).iterator();
                for (int i3 = 0; (((Iterator)localObject27).hasNext()) && ((i == 0) || (m < i)); i3++)
                {
                  localObject28 = (Payment)((Iterator)localObject27).next();
                  localReportRow2 = new ReportRow(localLocale2);
                  if (i3 % 2 == 1) {
                    localReportRow2.set("CELLBACKGROUND", "reportDataShaded");
                  }
                  localReportDataItems = new ReportDataItems(localLocale2);
                  localReportDataItem2 = null;
                  localReportRow2.setDataItems(localReportDataItems);
                  localReportDataItem2 = localReportDataItems.add();
                  localReportDataItem2.setData(((Payment)localObject28).getPayDateValue());
                  localReportDataItem2 = null;
                  localReportDataItem2 = localReportDataItems.add();
                  localReportDataItem2.setData(((Payment)localObject28).getMemo());
                  localReportDataItem2 = null;
                  localReportDataItem2 = localReportDataItems.add();
                  localReportDataItem2.setData(((Payment)localObject28).getAccount().getDisplayTextRoutingNumNickNameCurrency());
                  localReportDataItem2 = null;
                  localReportDataItem2 = localReportDataItems.add();
                  localReportDataItem2.setData(((Payment)localObject28).getReferenceNumber());
                  localReportDataItem2 = null;
                  localReportDataItem2 = localReportDataItems.add();
                  localReportDataItem2.setData(((Payment)localObject28).getAmountValue());
                  localReportDataItem2 = null;
                  ((ReportResult)localObject25).addRow(localReportRow2);
                  localReportDataItems = null;
                  localReportRow2 = null;
                  m++;
                }
                localObject25 = null;
                localObject28 = new ReportResult(localLocale2);
                ((ReportResult)localObject23).addSubReport((ReportResult)localObject28);
                localObject26 = new ReportDataDimensions(localLocale2);
                ((ReportDataDimensions)localObject26).setNumColumns(2);
                ((ReportDataDimensions)localObject26).setNumRows(1);
                ((ReportResult)localObject28).setDataDimensions((ReportDataDimensions)localObject26);
                jdMethod_if((ReportResult)localObject28, -1, "java.lang.String", 85, "RIGHT");
                jdMethod_if((ReportResult)localObject28, -1, "java.lang.String", 15, "RIGHT");
                ReportRow localReportRow2 = new ReportRow(localLocale2);
                ReportDataItems localReportDataItems = new ReportDataItems(localLocale2);
                ReportDataItem localReportDataItem2 = null;
                localReportRow2.setDataItems(localReportDataItems);
                localReportDataItem2 = localReportDataItems.add();
                localReportDataItem2.setData(ReportConsts.getText(2436, localLocale2) + " (" + ((Currency)localObject22).getCurrencyCode() + ")");
                localReportDataItem2 = null;
                localReportDataItem2 = localReportDataItems.add();
                localReportDataItem2.setData(((Currency)localObject22).getCurrencyStringNoSymbol());
                localReportDataItem2 = null;
                localReportDataItems = null;
                ((ReportResult)localObject28).addRow(localReportRow2);
                localReportRow2 = null;
                localObject28 = null;
              }
              localObject18 = new ReportResult(localLocale2);
              localReportResult.addSubReport((ReportResult)localObject18);
              localObject19 = new ReportDataDimensions(localLocale2);
              ((ReportDataDimensions)localObject19).setNumColumns(2);
              ((ReportDataDimensions)localObject19).setNumRows(1);
              ((ReportResult)localObject18).setDataDimensions((ReportDataDimensions)localObject19);
              jdMethod_if((ReportResult)localObject18, -1, "java.lang.String", 85, "RIGHT");
              jdMethod_if((ReportResult)localObject18, -1, "java.lang.String", 15, "RIGHT");
              localObject20 = new ReportRow(localLocale2);
              localObject22 = new ReportDataItems(localLocale2);
              localObject23 = null;
              ((ReportRow)localObject20).setDataItems((ReportDataItems)localObject22);
              localObject23 = ((ReportDataItems)localObject22).add();
              ((ReportDataItem)localObject23).setData(ReportConsts.getText(2441, localLocale2) + " (" + ((Currency)localObject13).getCurrencyCode() + ")");
              localObject23 = null;
              localObject23 = ((ReportDataItems)localObject22).add();
              ((ReportDataItem)localObject23).setData(((Currency)localObject13).toString());
              localObject23 = null;
              localObject22 = null;
              ((ReportResult)localObject18).addRow((ReportRow)localObject20);
              localObject20 = null;
              localObject18 = null;
              if ((i != 0) && (m >= i))
              {
                localObject24 = localReportResult.getProperties();
                if (localObject24 == null)
                {
                  localObject24 = new HashMap();
                  localReportResult.setProperties((HashMap)localObject24);
                }
                ((HashMap)localObject24).put("TRUNCATED", new Integer(m));
              }
            }
            else
            {
              localObject17 = new ReportDataDimensions(localLocale2);
              ((ReportDataDimensions)localObject17).setNumColumns(1);
              ((ReportDataDimensions)localObject17).setNumRows(1);
              localReportResult.setDataDimensions((ReportDataDimensions)localObject17);
              localObject18 = null;
              localObject18 = new ReportColumn(localLocale2);
              ((ReportColumn)localObject18).setDataType("java.lang.String");
              ((ReportColumn)localObject18).setWidthAsPercent(100);
              localReportResult.addColumn((ReportColumn)localObject18);
              localObject18 = null;
              localObject19 = new ReportRow(localLocale2);
              localObject20 = new ReportDataItems(localLocale2);
              localObject22 = null;
              ((ReportRow)localObject19).setDataItems((ReportDataItems)localObject20);
              localObject22 = ((ReportDataItems)localObject20).add();
              ((ReportDataItem)localObject22).setData(ReportConsts.getText(2438, localLocale2));
              localObject22 = null;
              localObject20 = null;
              localReportResult.addRow((ReportRow)localObject19);
              localObject19 = null;
            }
          }
          else if (str3.equals("BillPay By Status"))
          {
            localReportResult.init(paramReportCriteria);
            localIterator = localPayments1.iterator();
            localObject11 = new TreeMap();
            localObject12 = new Currency("0.00", paramSecureUser.getBaseCurrency(), Locale.getDefault());
            while (localIterator.hasNext())
            {
              localObject13 = (Payment)localIterator.next();
              localPayments2.add(localObject13);
              localObject14 = ((Payment)localObject13).getAmountValue().getCurrencyCode();
              localObject17 = (BigDecimal)((TreeMap)localObject11).get(localObject14);
              if (localObject17 == null) {
                localObject17 = new BigDecimal(((Payment)localObject13).getAmountValue().doubleValue());
              } else {
                localObject17 = ((BigDecimal)localObject17).add(((Payment)localObject13).getAmountValue().getAmountValue());
              }
              ((TreeMap)localObject11).put(localObject14, localObject17);
              if (((String)localObject14).equals(paramSecureUser.getBaseCurrency())) {
                ((Currency)localObject12).addAmount((BigDecimal)localObject17);
              } else {
                ((Currency)localObject12).addAmount(FX.convertToBaseCurrency(paramSecureUser, ((Payment)localObject13).getAmountValue(), new HashMap()));
              }
            }
            if (((StringBuffer)localObject5).toString().length() > 0) {
              localPayments2.setSortedBy(((StringBuffer)localObject5).toString());
            }
            localIterator = localPayments2.iterator();
            if (localIterator.hasNext())
            {
              localObject13 = new ReportResult(localLocale2);
              localReportResult.addSubReport((ReportResult)localObject13);
              localObject14 = new ReportDataDimensions(localLocale2);
              ((ReportDataDimensions)localObject14).setNumColumns(6);
              ((ReportDataDimensions)localObject14).setNumRows(-1);
              ((ReportResult)localObject13).setDataDimensions((ReportDataDimensions)localObject14);
              jdMethod_if((ReportResult)localObject13, 2700, "com.ffusion.util.beans.DateTime", 10, null);
              jdMethod_if((ReportResult)localObject13, 2701, "java.lang.String", 25, null);
              jdMethod_if((ReportResult)localObject13, 2702, "java.lang.String", 25, null);
              jdMethod_if((ReportResult)localObject13, 2703, "java.lang.String", 15, null);
              jdMethod_if((ReportResult)localObject13, 2705, "java.lang.String", 15, null);
              jdMethod_if((ReportResult)localObject13, 2704, "java.lang.String", 10, "RIGHT");
              for (int n = 0; (localIterator.hasNext()) && ((i == 0) || (n < i)); n++)
              {
                localObject18 = (Payment)localIterator.next();
                localObject19 = new ReportRow(localLocale2);
                if (n % 2 == 1) {
                  ((ReportRow)localObject19).set("CELLBACKGROUND", "reportDataShaded");
                }
                localObject20 = new ReportDataItems(localLocale2);
                localObject22 = null;
                ((ReportRow)localObject19).setDataItems((ReportDataItems)localObject20);
                localObject22 = ((ReportDataItems)localObject20).add();
                ((ReportDataItem)localObject22).setData(((Payment)localObject18).getPayDateValue());
                localObject22 = null;
                localObject22 = ((ReportDataItems)localObject20).add();
                ((ReportDataItem)localObject22).setData(((Payment)localObject18).getAccount().getDisplayTextRoutingNumNickNameCurrency());
                localObject22 = null;
                localObject22 = ((ReportDataItems)localObject20).add();
                ((ReportDataItem)localObject22).setData(((Payment)localObject18).getPayee().getName());
                localObject22 = null;
                localObject22 = ((ReportDataItems)localObject20).add();
                ((ReportDataItem)localObject22).setData(((Payment)localObject18).getReferenceNumber());
                localObject22 = null;
                localObject22 = ((ReportDataItems)localObject20).add();
                ((ReportDataItem)localObject22).setData(((Payment)localObject18).getStatusName());
                localObject22 = null;
                localObject22 = ((ReportDataItems)localObject20).add();
                localObject23 = new Currency(((Payment)localObject18).getAmountValue().getAmountValue(), ((Payment)localObject18).getAmountValue().getCurrencyCode(), Locale.getDefault());
                ((ReportDataItem)localObject22).setData(((Currency)localObject23).getCurrencyStringNoSymbol() + " " + ((Payment)localObject18).getAmountValue().getCurrencyCode());
                localObject22 = null;
                ((ReportResult)localObject13).addRow((ReportRow)localObject19);
                localObject20 = null;
                localObject19 = null;
              }
              if ((i != 0) && (n >= i))
              {
                localObject18 = localReportResult.getProperties();
                if (localObject18 == null)
                {
                  localObject18 = new HashMap();
                  localReportResult.setProperties((HashMap)localObject18);
                }
                ((HashMap)localObject18).put("TRUNCATED", new Integer(n));
              }
              localObject18 = ((TreeMap)localObject11).keySet().iterator();
              while (((Iterator)localObject18).hasNext())
              {
                localObject19 = (String)((Iterator)localObject18).next();
                localObject20 = (BigDecimal)((TreeMap)localObject11).get(localObject19);
                localObject13 = new ReportResult(localLocale2);
                localReportResult.addSubReport((ReportResult)localObject13);
                localObject14 = new ReportDataDimensions(localLocale2);
                ((ReportDataDimensions)localObject14).setNumColumns(2);
                ((ReportDataDimensions)localObject14).setNumRows(1);
                ((ReportResult)localObject13).setDataDimensions((ReportDataDimensions)localObject14);
                jdMethod_if((ReportResult)localObject13, -1, "java.lang.String", 85, "RIGHT");
                jdMethod_if((ReportResult)localObject13, -1, "java.lang.String", 15, "RIGHT");
                localObject22 = new ReportRow(localLocale2);
                localObject23 = new ReportDataItems(localLocale2);
                localObject24 = null;
                ((ReportRow)localObject22).setDataItems((ReportDataItems)localObject23);
                localObject24 = null;
                localObject24 = ((ReportDataItems)localObject23).add();
                ((ReportDataItem)localObject24).setData(ReportConsts.getText(2722, localLocale2) + " (" + (String)localObject19 + ")");
                localObject24 = null;
                localObject24 = ((ReportDataItems)localObject23).add();
                ((ReportDataItem)localObject24).setData(((BigDecimal)localObject20).toString());
                localObject24 = null;
                localObject23 = null;
                ((ReportResult)localObject13).addRow((ReportRow)localObject22);
                localObject22 = null;
                localObject13 = null;
              }
              localObject13 = new ReportResult(localLocale2);
              localReportResult.addSubReport((ReportResult)localObject13);
              localObject14 = new ReportDataDimensions(localLocale2);
              ((ReportDataDimensions)localObject14).setNumColumns(2);
              ((ReportDataDimensions)localObject14).setNumRows(1);
              ((ReportResult)localObject13).setDataDimensions((ReportDataDimensions)localObject14);
              jdMethod_if((ReportResult)localObject13, -1, "java.lang.String", 85, "RIGHT");
              jdMethod_if((ReportResult)localObject13, -1, "java.lang.String", 15, "RIGHT");
              localObject19 = new ReportRow(localLocale2);
              localObject20 = new ReportDataItems(localLocale2);
              localObject22 = null;
              ((ReportRow)localObject19).setDataItems((ReportDataItems)localObject20);
              localObject22 = ((ReportDataItems)localObject20).add();
              localObject23 = new StringBuffer(ReportConsts.getText(2441, localLocale2));
              ((StringBuffer)localObject23).append(" (");
              ((StringBuffer)localObject23).append(((Currency)localObject12).getCurrencyCode());
              ((StringBuffer)localObject23).append("): ");
              ((ReportDataItem)localObject22).setData(((StringBuffer)localObject23).toString());
              localObject22 = null;
              localObject22 = ((ReportDataItems)localObject20).add();
              ((ReportDataItem)localObject22).setData(((Currency)localObject12).getCurrencyStringNoSymbol());
              localObject22 = null;
              localObject20 = null;
              ((ReportResult)localObject13).addRow((ReportRow)localObject19);
              localObject19 = null;
              localObject13 = null;
            }
            else
            {
              localObject13 = null;
              localObject14 = new ReportDataDimensions(localLocale2);
              ((ReportDataDimensions)localObject14).setNumColumns(1);
              ((ReportDataDimensions)localObject14).setNumRows(1);
              localReportResult.setDataDimensions((ReportDataDimensions)localObject14);
              localObject13 = new ReportColumn(localLocale2);
              ((ReportColumn)localObject13).setDataType("java.lang.String");
              ((ReportColumn)localObject13).setWidthAsPercent(100);
              localReportResult.addColumn((ReportColumn)localObject13);
              localObject13 = null;
              ReportRow localReportRow1 = new ReportRow(localLocale2);
              localObject18 = new ReportDataItems(localLocale2);
              localObject19 = null;
              localReportRow1.setDataItems((ReportDataItems)localObject18);
              localObject19 = ((ReportDataItems)localObject18).add();
              ((ReportDataItem)localObject19).setData(ReportConsts.getText(2721, localLocale2));
              localObject19 = null;
              localObject18 = null;
              localReportResult.addRow(localReportRow1);
              localReportRow1 = null;
            }
          }
          else
          {
            localReportResult = null;
            DebugLog.log("Report type is not valid");
            throw new Exception("Report type is not valid");
          }
        }
      }
    }
    catch (Exception localException3)
    {
      if (localReportResult != null) {
        localReportResult.setError(localException3);
      }
      throw localException3;
    }
    finally
    {
      if (localReportResult != null) {
        localReportResult.fini();
      }
    }
    return localReportResult;
  }
  
  protected void formatGetPayeesRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetPayeesRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn = new TypeBillPayMsgsRqV1PayeeUn[1];
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[0] = new TypeBillPayMsgsRqV1PayeeUn();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[0].__memberName = "PayeeSyncRq";
    TypePayeeSyncRqV1Aggregate localTypePayeeSyncRqV1Aggregate = new TypePayeeSyncRqV1Aggregate();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[0].PayeeSyncRq = localTypePayeeSyncRqV1Aggregate;
    localTypePayeeSyncRqV1Aggregate.SyncRqCm = new TypeSyncRqCm();
    localTypePayeeSyncRqV1Aggregate.SyncRqCm.RejectIfMissing = false;
    localTypePayeeSyncRqV1Aggregate.SyncRqCm.TokenRqUn = new TypeTokenRqUn();
    if ((this.aB != null) && (this.aB.length() > 0))
    {
      localTypePayeeSyncRqV1Aggregate.SyncRqCm.TokenRqUn.__memberName = "Token";
      localTypePayeeSyncRqV1Aggregate.SyncRqCm.TokenRqUn.Token = this.aB;
    }
    else
    {
      localTypePayeeSyncRqV1Aggregate.SyncRqCm.TokenRqUn.__memberName = "Refresh";
      localTypePayeeSyncRqV1Aggregate.SyncRqCm.TokenRqUn.Refresh = true;
    }
  }
  
  protected void formatAddPayeesRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatAddPayeesRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest, ((Payee)this.payees.get(0)).getTrackingID());
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn = new TypeBillPayMsgsRqV1PayeeUn[this.payees.size()];
    for (int i = 0; i < this.payees.size(); i++)
    {
      Payee localPayee = (Payee)this.payees.get(i);
      localPayee.setTRNUID(getUniqueSeqNum(this.userID));
      String str = localPayee.getUserAccountNumber();
      if ((str == null) || (str.length() == 0)) {
        str = "NA";
      }
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i] = new TypeBillPayMsgsRqV1PayeeUn();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i].__memberName = "PayeeTrnRq";
      TypePayeeTrnRqV1Aggregate localTypePayeeTrnRqV1Aggregate = new TypePayeeTrnRqV1Aggregate();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i].PayeeTrnRq = localTypePayeeTrnRqV1Aggregate;
      localTypePayeeTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
      localTypePayeeTrnRqV1Aggregate.TrnRqCm.TrnUID = localPayee.getTRNUID();
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn = new TypePayeeTrnRqUn();
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.__memberName = "PayeeRq";
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq = new TypePayeeRqAggregate();
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayAcct = new String[1];
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayAcct[0] = str;
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayeeRqUn = new TypePayeeRqUn();
      if ((localPayee.getHostID() != null) && (localPayee.getHostID().length() > 0))
      {
        localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayeeRqUn.__memberName = "PayeeID";
        localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayeeRqUn.PayeeID = localPayee.getHostID();
      }
      else
      {
        localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayeeRqUn.__memberName = "Payee";
        localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayeeRqUn.Payee = new TypePayeeAggregate();
        formatPAYEE(localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayeeRqUn.Payee, localPayee);
      }
    }
  }
  
  protected void formatModifyPayeesRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatModifyPayeesRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest, ((Payee)this.payees.get(0)).getTrackingID());
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn = new TypeBillPayMsgsRqV1PayeeUn[this.payees.size()];
    for (int i = 0; i < this.payees.size(); i++)
    {
      Payee localPayee = (Payee)this.payees.get(i);
      localPayee.setTRNUID(getUniqueSeqNum(this.userID));
      String str = localPayee.getUserAccountNumber();
      if ((str == null) || (str.length() == 0)) {
        str = "NA";
      }
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i] = new TypeBillPayMsgsRqV1PayeeUn();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i].__memberName = "PayeeTrnRq";
      TypePayeeTrnRqV1Aggregate localTypePayeeTrnRqV1Aggregate = new TypePayeeTrnRqV1Aggregate();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i].PayeeTrnRq = localTypePayeeTrnRqV1Aggregate;
      localTypePayeeTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
      localTypePayeeTrnRqV1Aggregate.TrnRqCm.TrnUID = localPayee.getTRNUID();
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn = new TypePayeeTrnRqUn();
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.__memberName = "PayeeModRq";
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq = new TypePayeeModRqAggregate();
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq.PayAcct = new String[1];
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq.PayAcct[0] = str;
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq.PayeeLstID = localPayee.getID();
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq.PayeeModRqCmExists = true;
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq.PayeeModRqCm = new TypePayeeModRqCm();
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq.PayeeModRqCm.Payee = new TypePayeeAggregate();
      formatPAYEE(localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq.PayeeModRqCm.Payee, localPayee);
    }
  }
  
  protected void formatDeletePayeesRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatDeletePayeesRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest, ((Payee)this.payees.get(0)).getTrackingID());
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn = new TypeBillPayMsgsRqV1PayeeUn[this.payees.size()];
    for (int i = 0; i < this.payees.size(); i++)
    {
      Payee localPayee = (Payee)this.payees.get(i);
      localPayee.setTRNUID(getUniqueSeqNum(this.userID));
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i] = new TypeBillPayMsgsRqV1PayeeUn();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i].__memberName = "PayeeTrnRq";
      TypePayeeTrnRqV1Aggregate localTypePayeeTrnRqV1Aggregate = new TypePayeeTrnRqV1Aggregate();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i].PayeeTrnRq = localTypePayeeTrnRqV1Aggregate;
      localTypePayeeTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
      localTypePayeeTrnRqV1Aggregate.TrnRqCm.TrnUID = localPayee.getTRNUID();
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn = new TypePayeeTrnRqUn();
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.__memberName = "PayeeDelRq";
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeDelRq = new TypePayeeDelRqAggregate();
      localTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeDelRq.PayeeLstID = localPayee.getID();
    }
  }
  
  protected void formatGetPaymentsRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetPaymentsRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    this.accounts.setFilter("All");
    int i = this.accounts.size();
    int j = 0;
    Account localAccount;
    for (int k = 0; k < i; k++)
    {
      localAccount = (Account)this.accounts.get(k);
      if (localAccount.isFilterable("BillPay")) {
        j++;
      }
    }
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[j * 2];
    j = 0;
    for (k = 0; k < i; k++)
    {
      localAccount = (Account)this.accounts.get(k);
      if (localAccount.isFilterable("BillPay"))
      {
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j] = new TypeBillPayMsgsRqV1MessageUn();
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j].__memberName = "RecPmtSyncRq";
        TypeRecPmtSyncRqV1Aggregate localTypeRecPmtSyncRqV1Aggregate = new TypeRecPmtSyncRqV1Aggregate();
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j].RecPmtSyncRq = localTypeRecPmtSyncRqV1Aggregate;
        localTypeRecPmtSyncRqV1Aggregate.SyncRqCm = new TypeSyncRqCm();
        localTypeRecPmtSyncRqV1Aggregate.SyncRqCm.RejectIfMissing = false;
        localTypeRecPmtSyncRqV1Aggregate.SyncRqCm.TokenRqUn = new TypeTokenRqUn();
        localTypeRecPmtSyncRqV1Aggregate.SyncRqCm.TokenRqUn.__memberName = "Token";
        if ((this.az != null) && (this.az.length() > 0)) {
          localTypeRecPmtSyncRqV1Aggregate.SyncRqCm.TokenRqUn.Token = this.az;
        } else {
          localTypeRecPmtSyncRqV1Aggregate.SyncRqCm.TokenRqUn.Token = "0";
        }
        localTypeRecPmtSyncRqV1Aggregate.BankAcctFrom = new TypeBankAcctFromAggregate();
        formatBANKACCTFROM(localTypeRecPmtSyncRqV1Aggregate.BankAcctFrom, localAccount);
        j++;
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j] = new TypeBillPayMsgsRqV1MessageUn();
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j].__memberName = "PmtSyncRq";
        TypePmtSyncRqV1Aggregate localTypePmtSyncRqV1Aggregate = new TypePmtSyncRqV1Aggregate();
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j].PmtSyncRq = localTypePmtSyncRqV1Aggregate;
        localTypePmtSyncRqV1Aggregate.SyncRqCm = new TypeSyncRqCm();
        localTypePmtSyncRqV1Aggregate.SyncRqCm.RejectIfMissing = false;
        localTypePmtSyncRqV1Aggregate.SyncRqCm.TokenRqUn = new TypeTokenRqUn();
        localTypePmtSyncRqV1Aggregate.SyncRqCm.TokenRqUn.__memberName = "Token";
        if ((this.aA != null) && (this.aA.length() > 0)) {
          localTypePmtSyncRqV1Aggregate.SyncRqCm.TokenRqUn.Token = this.aA;
        } else {
          localTypePmtSyncRqV1Aggregate.SyncRqCm.TokenRqUn.Token = "0";
        }
        localTypePmtSyncRqV1Aggregate.BankAcctFrom = new TypeBankAcctFromAggregate();
        formatBANKACCTFROM(localTypePmtSyncRqV1Aggregate.BankAcctFrom, localAccount);
        j++;
      }
    }
  }
  
  protected void formatSendPaymentsRequest(int paramInt)
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendPaymentsRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest, ((Payment)this.payments.get(0)).getTrackingID());
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[this.payments.size()];
    for (int i = 0; i < this.payments.size(); i++)
    {
      Payment localPayment = (Payment)this.payments.get(i);
      localPayment.setTRNUID(getUniqueSeqNum(this.userID));
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i] = new TypeBillPayMsgsRqV1MessageUn();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].__memberName = "PmtTrnRq";
      TypePmtTrnRqV1Aggregate localTypePmtTrnRqV1Aggregate = new TypePmtTrnRqV1Aggregate();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].PmtTrnRq = localTypePmtTrnRqV1Aggregate;
      localTypePmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
      localTypePmtTrnRqV1Aggregate.TrnRqCm.TrnUID = localPayment.getTRNUID();
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn = new TypePmtTrnRqUn();
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.__memberName = "PmtRq";
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.PmtRq = new TypePmtRqAggregate();
      String str = localPayment.getAmtCurrency();
      if (str == null) {
        str = localPayment.getAccount().getCurrencyCode();
      }
      if (str != null)
      {
        localObject = null;
        try
        {
          int j = ValueSetCurrencyEnum.getIndex(str);
          localObject = EnumCurrencyEnum.from_int(j);
        }
        catch (Throwable localThrowable) {}
        if (localObject != null)
        {
          localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.PmtRq.CurDef = ((EnumCurrencyEnum)localObject);
          localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.PmtRq.CurDefExists = true;
        }
      }
      Object localObject = new TypePmtInfoAggregate();
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.PmtRq.PmtInfo = ((TypePmtInfoAggregate)localObject);
      formatPmtInfo((TypePmtInfoAggregate)localObject, this.account, localPayment, paramInt, this.defaultDaysToPay);
    }
  }
  
  protected void formatDeletePaymentsRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatDeletePaymentsRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest, ((Payment)this.payments.get(0)).getTrackingID());
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[this.payments.size()];
    for (int i = 0; i < this.payments.size(); i++)
    {
      Payment localPayment = (Payment)this.payments.get(i);
      localPayment.setTRNUID(getUniqueSeqNum(this.userID));
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i] = new TypeBillPayMsgsRqV1MessageUn();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].__memberName = "PmtTrnRq";
      TypePmtTrnRqV1Aggregate localTypePmtTrnRqV1Aggregate = new TypePmtTrnRqV1Aggregate();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].PmtTrnRq = localTypePmtTrnRqV1Aggregate;
      localTypePmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
      localTypePmtTrnRqV1Aggregate.TrnRqCm.TrnUID = localPayment.getTRNUID();
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn = new TypePmtTrnRqUn();
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.__memberName = "PmtCancRq";
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.PmtCancRq = new TypePmtCancRqAggregate();
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.PmtCancRq.SrvrTID = localPayment.getID();
    }
  }
  
  protected void formatSendRecPaymentsRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendRecPaymentsRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest, ((RecPayment)this.recpayments.get(0)).getTrackingID());
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[this.recpayments.size()];
    for (int i = 0; i < this.recpayments.size(); i++)
    {
      RecPayment localRecPayment = (RecPayment)this.recpayments.get(i);
      localRecPayment.setTRNUID(getUniqueSeqNum(this.userID));
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i] = new TypeBillPayMsgsRqV1MessageUn();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].__memberName = "RecPmtTrnRq";
      TypeRecPmtTrnRqV1Aggregate localTypeRecPmtTrnRqV1Aggregate = new TypeRecPmtTrnRqV1Aggregate();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].RecPmtTrnRq = localTypeRecPmtTrnRqV1Aggregate;
      localTypeRecPmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
      localTypeRecPmtTrnRqV1Aggregate.TrnRqCm.TrnUID = localRecPayment.getTRNUID();
      localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn = new TypeRecPmtTrnRqUn();
      localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.__memberName = "RecPmtRq";
      TypeRecPmtRqAggregate localTypeRecPmtRqAggregate = new TypeRecPmtRqAggregate();
      localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.RecPmtRq = localTypeRecPmtRqAggregate;
      localTypeRecPmtRqAggregate.RecurrInst = formatRECURRINST(localRecPayment.getFrequencyValue(), localRecPayment.getNumberPaymentsValue());
      localTypeRecPmtRqAggregate.PmtInfo = new TypePmtInfoAggregate();
      String str = localRecPayment.getAmtCurrency();
      if (str == null) {
        str = localRecPayment.getAccount().getCurrencyCode();
      }
      if (str != null)
      {
        EnumCurrencyEnum localEnumCurrencyEnum = null;
        try
        {
          int j = ValueSetCurrencyEnum.getIndex(str);
          localEnumCurrencyEnum = EnumCurrencyEnum.from_int(j);
        }
        catch (Throwable localThrowable) {}
        if (localEnumCurrencyEnum != null)
        {
          localTypeRecPmtRqAggregate.CurDef = localEnumCurrencyEnum;
          localTypeRecPmtRqAggregate.CurDefExists = true;
        }
      }
      formatPmtInfo(localTypeRecPmtRqAggregate.PmtInfo, this.account, localRecPayment, -1, this.defaultDaysToPay);
    }
  }
  
  protected void formatModifyPaymentRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatModifyPaymentRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest, this.payment.getTrackingID());
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[1];
    this.payment.setTRNUID(getUniqueSeqNum(this.userID));
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0] = new TypeBillPayMsgsRqV1MessageUn();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].__memberName = "PmtTrnRq";
    TypePmtTrnRqV1Aggregate localTypePmtTrnRqV1Aggregate = new TypePmtTrnRqV1Aggregate();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].PmtTrnRq = localTypePmtTrnRqV1Aggregate;
    localTypePmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
    localTypePmtTrnRqV1Aggregate.TrnRqCm.TrnUID = this.payment.getTRNUID();
    localTypePmtTrnRqV1Aggregate.PmtTrnRqUn = new TypePmtTrnRqUn();
    localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.__memberName = "PmtModRq";
    TypePmtModRqAggregate localTypePmtModRqAggregate = new TypePmtModRqAggregate();
    localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.PmtModRq = localTypePmtModRqAggregate;
    localTypePmtModRqAggregate.SrvrTID = this.payment.getID();
    String str = this.payment.getAmtCurrency();
    if (str == null) {
      str = this.payment.getAccount().getCurrencyCode();
    }
    if (str != null)
    {
      EnumCurrencyEnum localEnumCurrencyEnum = null;
      try
      {
        int i = ValueSetCurrencyEnum.getIndex(str);
        localEnumCurrencyEnum = EnumCurrencyEnum.from_int(i);
      }
      catch (Throwable localThrowable) {}
      if (localEnumCurrencyEnum != null)
      {
        localTypePmtModRqAggregate.CurDef = localEnumCurrencyEnum;
        localTypePmtModRqAggregate.CurDefExists = true;
      }
    }
    localTypePmtModRqAggregate.PmtInfo = new TypePmtInfoAggregate();
    formatPmtInfo(localTypePmtModRqAggregate.PmtInfo, this.account, this.payment, -1, this.defaultDaysToPay);
  }
  
  protected void formatModifyRecPaymentRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatModifyRecPaymentRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest, this.recpayment.getTrackingID());
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[1];
    this.recpayment.setTRNUID(getUniqueSeqNum(this.userID));
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0] = new TypeBillPayMsgsRqV1MessageUn();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].__memberName = "RecPmtTrnRq";
    TypeRecPmtTrnRqV1Aggregate localTypeRecPmtTrnRqV1Aggregate = new TypeRecPmtTrnRqV1Aggregate();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].RecPmtTrnRq = localTypeRecPmtTrnRqV1Aggregate;
    localTypeRecPmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
    localTypeRecPmtTrnRqV1Aggregate.TrnRqCm.TrnUID = this.recpayment.getTRNUID();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn = new TypeRecPmtTrnRqUn();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.__memberName = "RecPmtModRq";
    TypeRecPmtModRqAggregate localTypeRecPmtModRqAggregate = new TypeRecPmtModRqAggregate();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.RecPmtModRq = localTypeRecPmtModRqAggregate;
    localTypeRecPmtModRqAggregate.RecSrvrTID = this.recpayment.getID();
    localTypeRecPmtModRqAggregate.RecurrInst = formatRECURRINST(this.recpayment.getFrequencyValue(), this.recpayment.getNumberPaymentsValue());
    String str = this.recpayment.getAmtCurrency();
    if (str == null) {
      str = this.recpayment.getAccount().getCurrencyCode();
    }
    if (str != null)
    {
      EnumCurrencyEnum localEnumCurrencyEnum = null;
      try
      {
        int i = ValueSetCurrencyEnum.getIndex(str);
        localEnumCurrencyEnum = EnumCurrencyEnum.from_int(i);
      }
      catch (Throwable localThrowable) {}
      if (localEnumCurrencyEnum != null)
      {
        localTypeRecPmtModRqAggregate.CurDef = localEnumCurrencyEnum;
        localTypeRecPmtModRqAggregate.CurDefExists = true;
      }
    }
    localTypeRecPmtModRqAggregate.PmtInfo = new TypePmtInfoAggregate();
    formatPmtInfo(localTypeRecPmtModRqAggregate.PmtInfo, this.account, this.recpayment, -1, this.defaultDaysToPay);
    localTypeRecPmtModRqAggregate.ModPending = true;
  }
  
  protected void formatDeleteRecPaymentRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetTransactionsRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest, this.recpayment.getTrackingID());
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[1];
    this.recpayment.setTRNUID(getUniqueSeqNum(this.userID));
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0] = new TypeBillPayMsgsRqV1MessageUn();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].__memberName = "RecPmtTrnRq";
    TypeRecPmtTrnRqV1Aggregate localTypeRecPmtTrnRqV1Aggregate = new TypeRecPmtTrnRqV1Aggregate();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].RecPmtTrnRq = localTypeRecPmtTrnRqV1Aggregate;
    localTypeRecPmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
    localTypeRecPmtTrnRqV1Aggregate.TrnRqCm.TrnUID = this.recpayment.getTRNUID();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn = new TypeRecPmtTrnRqUn();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.__memberName = "RecPmtCancRq";
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.RecPmtCancRq = new TypeRecPmtCancRqAggregate();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.RecPmtCancRq.RecSrvrTID = this.recpayment.getID();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.RecPmtCancRq.CanPending = true;
  }
  
  protected int mapError(int paramInt)
  {
    int i = 1;
    switch (paramInt)
    {
    case 0: 
      i = 0;
      break;
    case 2000: 
      i = 1;
      break;
    case 2001: 
      i = 2000;
      break;
    case 2002: 
      i = 2000;
      break;
    case 2003: 
      i = 2000;
      break;
    case 2004: 
      i = 2000;
      break;
    case 2005: 
      i = 2000;
      break;
    case 2006: 
      i = 2000;
      break;
    case 2007: 
      i = 2000;
      break;
    case 2008: 
      i = 2000;
      break;
    case 2009: 
      i = 2000;
      break;
    case 2010: 
      i = 2000;
      break;
    case 2011: 
      i = 2000;
      break;
    case 2012: 
      i = 2003;
      break;
    case 2014: 
      i = 2004;
      break;
    case 2015: 
      i = 2005;
      break;
    case 2016: 
      i = 2006;
      break;
    case 2017: 
      i = 2006;
      break;
    case 2018: 
      i = 3;
      break;
    case 2019: 
      i = 2006;
      break;
    case 10500: 
      i = 2007;
      break;
    case 10501: 
      i = 2008;
      break;
    case 10502: 
      i = 2009;
      break;
    case 10503: 
      i = 2008;
      break;
    case 10505: 
      i = 2;
      break;
    case 10508: 
      i = 2010;
      break;
    case 10510: 
      i = 2008;
      break;
    case 10511: 
      i = 2009;
      break;
    case 10512: 
      i = 2009;
      break;
    case 10513: 
      i = 2009;
      break;
    case 10514: 
      i = 2006;
      break;
    case 10515: 
      i = 2;
      break;
    case 10517: 
      i = 2008;
      break;
    case 10518: 
      i = 3;
      break;
    case 10519: 
      i = 2008;
      break;
    case 26101: 
      i = 2008;
      break;
    case 26102: 
      i = 2008;
      break;
    case 16000: 
      i = 2008;
      break;
    case 16010: 
      i = 2008;
      break;
    case 100000: 
      i = 2015;
      break;
    case 100002: 
      i = 2016;
      break;
    default: 
      i = super.mapError(paramInt);
    }
    return i;
  }
  
  protected int mapBPWError(int paramInt1, int paramInt2)
  {
    int i = 1;
    i = mapError(paramInt1);
    if (i == 1) {
      switch (paramInt1)
      {
      case 26030: 
        i = 2100;
        break;
      case 26029: 
        i = 2111;
        break;
      default: 
        i = paramInt2;
      }
    }
    return i;
  }
  
  protected void handleBPWException(Exception paramException, int paramInt, String paramString)
    throws CSILException
  {
    String str = paramException.getMessage();
    if ((str != null) && (str.indexOf("Server is not running!") > 0)) {
      throw new CSILException(25018);
    }
    DebugLog.throwing(paramString + ":" + str, paramException);
    throw new CSILException(paramString, paramInt, paramException);
  }
  
  protected void processPmtInfo(TypePmtInfoAggregate paramTypePmtInfoAggregate, Payment paramPayment)
  {
    String str1 = paramTypePmtInfoAggregate.BankAcctFrom.AcctID;
    EnumAccountEnum localEnumAccountEnum = paramTypePmtInfoAggregate.BankAcctFrom.AcctType;
    int i = getAccountType(localEnumAccountEnum);
    if (this.accounts != null)
    {
      this.account = this.accounts.getByAccountNumberAndType(str1, i);
      if (this.account == null) {
        this.account = this.accounts.createNoAdd(str1, i);
      }
      if (this.account != null) {
        paramPayment.setAccount(this.account);
      } else {
        paramPayment.setAccountID(str1);
      }
      this.account = null;
    }
    paramPayment.setPayDate(getDate(paramTypePmtInfoAggregate.DtDue));
    if (paramTypePmtInfoAggregate.MemoExists) {
      paramPayment.setMemo(paramTypePmtInfoAggregate.Memo);
    }
    Currency localCurrency = new Currency(new BigDecimal(paramTypePmtInfoAggregate.TrnAmt), paramPayment.getAccount() != null ? paramPayment.getAccount().getCurrencyCode() : "USD", paramPayment.getLocale());
    paramPayment.setAmount(localCurrency);
    String str2 = null;
    String str3 = null;
    if (paramTypePmtInfoAggregate.PayeeLstIDExists) {
      str2 = paramTypePmtInfoAggregate.PayeeLstID;
    }
    if (paramTypePmtInfoAggregate.PayeeUn != null) {
      if ("PayeeID".equals(paramTypePmtInfoAggregate.PayeeUn.__memberName)) {
        str3 = paramTypePmtInfoAggregate.PayeeUn.PayeeID;
      } else if ("Payee".equals(paramTypePmtInfoAggregate.PayeeUn.__memberName)) {
        paramPayment.setPayeeName(paramTypePmtInfoAggregate.PayeeUn.Payee.Name);
      }
    }
    if (((str2 != null) || (str3 != null)) && (this.payees != null))
    {
      if (str3 != null) {
        this.payee = ((Payee)this.payees.getFirstByFilter("HOSTID=" + str3));
      } else {
        this.payee = ((Payee)this.payees.getFirstByFilter("ID=" + str2));
      }
      paramPayment.setPayee(this.payee);
      this.payee = null;
    }
  }
  
  protected static void processPAYEE(TypePayeeAggregate paramTypePayeeAggregate, Payee paramPayee)
  {
    paramPayee.setName(paramTypePayeeAggregate.Name);
    paramPayee.setStreet(paramTypePayeeAggregate.AddressCm.Addr1);
    if (paramTypePayeeAggregate.AddressCm.SubAddressCmExists)
    {
      paramPayee.setStreet2(paramTypePayeeAggregate.AddressCm.SubAddressCm.Addr2);
      if (paramTypePayeeAggregate.AddressCm.SubAddressCm.Addr3Exists) {
        paramPayee.setStreet3(paramTypePayeeAggregate.AddressCm.SubAddressCm.Addr3);
      }
    }
    paramPayee.setCity(paramTypePayeeAggregate.City);
    if (paramTypePayeeAggregate.CountryExists) {
      paramPayee.setCountry(paramTypePayeeAggregate.Country);
    }
    paramPayee.setState(paramTypePayeeAggregate.State);
    paramPayee.setZipCode(paramTypePayeeAggregate.PostalCode);
    paramPayee.setPhone(paramTypePayeeAggregate.Phone);
  }
  
  protected void processOFXRequest()
  {
    TypeOFXRequest localTypeOFXRequest = this.rqmes;
    this.objStatus.clear();
    IOFX200BPWServices localIOFX200BPWServices = null;
    try
    {
      localIOFX200BPWServices = getOFXHandler();
      processTransactionsInSignOnMsgsRqV1(localTypeOFXRequest.OFXRequest.SignOnMsgsRqV1, localIOFX200BPWServices);
      if (localTypeOFXRequest.OFXRequest.BillPayMsgsRqV1Exists) {
        processTransactionsInBillPayMsgsRqV1(localTypeOFXRequest.OFXRequest.BillPayMsgsRqV1, localIOFX200BPWServices);
      }
      if (localTypeOFXRequest.OFXRequest.SignUpMsgsRqV1Exists) {
        processTransactionsInSignUpMsgsRqV1(localTypeOFXRequest.OFXRequest.SignUpMsgsRqV1, localIOFX200BPWServices);
      }
    }
    catch (RemoteException localRemoteException)
    {
      this.objStatus.put("ServiceDown", "-1");
      localRemoteException.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      if (localIOFX200BPWServices != null) {
        removeOFXHandler(localIOFX200BPWServices);
      }
    }
  }
  
  protected void processTransactionsInBillPayMsgsRqV1(TypeBillPayMsgsRqV1Aggregate paramTypeBillPayMsgsRqV1Aggregate, IOFX200BPWServices paramIOFX200BPWServices)
    throws Exception
  {
    int i = 0;
    int j = 0;
    if (paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn != null) {
      i = paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn.length;
    }
    if (paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn != null) {
      j = paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn.length;
    }
    Object localObject1;
    Object localObject2;
    for (int k = 0; k < i; k++)
    {
      if ("PayeeTrnRq".equals(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[k].__memberName))
      {
        localObject1 = new TypePayeeTrnRqV1(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[k].PayeeTrnRq);
        localObject2 = null;
        localObject2 = paramIOFX200BPWServices.processPayeeTrnRqV1((TypePayeeTrnRqV1)localObject1, this._ud);
        if (localObject2 != null) {
          processPayeeTrnRs(((TypePayeeTrnRsV1)localObject2).PayeeTrnRs);
        }
      }
      if ("PayeeSyncRq".equals(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[k].__memberName))
      {
        localObject1 = new TypePayeeSyncRqV1(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[k].PayeeSyncRq);
        localObject2 = null;
        localObject2 = paramIOFX200BPWServices.processPayeeSyncRqV1((TypePayeeSyncRqV1)localObject1, this._ud);
        if (localObject2 != null) {
          processPayeeSyncRs(((TypePayeeSyncRsV1)localObject2).PayeeSyncRs);
        }
      }
    }
    for (k = 0; k < j; k++)
    {
      if ("PmtTrnRq".equals(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].__memberName))
      {
        localObject1 = new TypePmtTrnRqV1(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].PmtTrnRq);
        localObject2 = null;
        localObject2 = paramIOFX200BPWServices.processPmtTrnRqV1((TypePmtTrnRqV1)localObject1, this._ud);
        if (localObject2 != null) {
          processPmtTrnRs(((TypePmtTrnRsV1)localObject2).PmtTrnRs);
        }
      }
      if ("RecPmtTrnRq".equals(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].__memberName))
      {
        localObject1 = new TypeRecPmtTrnRqV1(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].RecPmtTrnRq);
        localObject2 = null;
        localObject2 = paramIOFX200BPWServices.processRecPmtTrnRqV1((TypeRecPmtTrnRqV1)localObject1, this._ud);
        if (localObject2 != null) {
          processRecPmtTrnRs(((TypeRecPmtTrnRsV1)localObject2).RecPmtTrnRs);
        }
      }
      if ("PmtSyncRq".equals(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].__memberName))
      {
        localObject1 = new TypePmtSyncRqV1(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].PmtSyncRq);
        localObject2 = null;
        localObject2 = paramIOFX200BPWServices.processPmtSyncRqV1((TypePmtSyncRqV1)localObject1, this._ud);
        if (localObject2 != null) {
          processPmtSyncRs(((TypePmtSyncRsV1)localObject2).PmtSyncRs);
        }
      }
      if ("RecPmtSyncRq".equals(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].__memberName))
      {
        localObject1 = new TypeRecPmtSyncRqV1(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].RecPmtSyncRq);
        localObject2 = null;
        localObject2 = paramIOFX200BPWServices.processRecPmtSyncRqV1((TypeRecPmtSyncRqV1)localObject1, this._ud);
        if (localObject2 != null) {
          processRecPmtSyncRs(((TypeRecPmtSyncRsV1)localObject2).RecPmtSyncRs);
        }
      }
    }
  }
  
  protected void processPayeeTrnRs(TypePayeeTrnRsV1Aggregate paramTypePayeeTrnRsV1Aggregate)
  {
    processSTATUS(paramTypePayeeTrnRsV1Aggregate.TrnRsCm.Status);
    if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUnExists)
    {
      Payee localPayee = null;
      String str1 = null;
      String str2 = "NA";
      String str3 = null;
      String str4 = null;
      String str5 = null;
      String str6 = paramTypePayeeTrnRsV1Aggregate.TrnRsCm.TrnUID;
      int i = -1;
      TypePayeeAggregate localTypePayeeAggregate = null;
      if ("PayeeRs".equals(paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.__memberName))
      {
        str1 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.PayeeLstID;
        if ((paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.PayAcct != null) && (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.PayAcct.length > 0)) {
          str2 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.PayAcct[0];
        }
        if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.ExtdPayeeExists)
        {
          i = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.ExtdPayee.DaysToPay;
          if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCmExists)
          {
            str4 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCm.Name;
            str3 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCm.PayeeID;
            if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCm.IDScope.value() == 0) {
              str5 = "GLOBAL";
            } else if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCm.IDScope.value() == 1) {
              str5 = "USER";
            }
          }
        }
        if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.PayeeRsCmExists) {
          localTypePayeeAggregate = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee;
        }
      }
      else if ("PayeeModRs".equals(paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.__memberName))
      {
        str1 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.PayeeLstID;
        if ((paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.PayAcct != null) && (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.PayAcct.length > 0)) {
          str2 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.PayAcct[0];
        }
        if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.ExtdPayeeExists) {
          i = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.ExtdPayee.DaysToPay;
        }
        if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.PayeeModRsCmExists) {
          localTypePayeeAggregate = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.PayeeModRsCm.Payee;
        }
      }
      else if ("PayeeDelRs".equals(paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.__memberName))
      {
        str1 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeDelRs.PayeeLstID;
      }
      if ((str1 != null) || (str6 != null))
      {
        if ((str6 != null) && (!"0".equals(str6))) {
          localPayee = (Payee)this.payees.getFirstByFilter("TRNUID=" + str6);
        }
        if (localPayee == null) {
          localPayee = (Payee)this.payees.getFirstByFilter("ID=" + str1);
        }
        if (localPayee == null) {
          localPayee = this.payees.create();
        }
        if ("PayeeDelRs".equals(paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.__memberName))
        {
          localPayee.setStatus(3);
        }
        else
        {
          if (localTypePayeeAggregate != null) {
            processPAYEE(localTypePayeeAggregate, localPayee);
          }
          if (str2 != null) {
            localPayee.setUserAccountNumber(str2);
          }
          if (i != -1) {
            localPayee.setDaysToPay(i);
          }
          if (str3 != null) {
            localPayee.setHostID(str3);
          }
          if (str4 != null) {
            localPayee.setName(str4);
          }
          if (str5 != null) {
            localPayee.set("IDScope", str5);
          }
          localPayee.setStatus(2);
        }
        localPayee.setID(str1);
        Integer localInteger = new Integer(this.status);
        this.objStatus.put(str1, localInteger.toString());
      }
    }
  }
  
  protected void processPayeeSyncRs(TypePayeeSyncRsV1Aggregate paramTypePayeeSyncRsV1Aggregate)
  {
    if ((paramTypePayeeSyncRsV1Aggregate != null) && (paramTypePayeeSyncRsV1Aggregate.PayeeTrnRs != null))
    {
      TypePayeeTrnRsV1Aggregate[] arrayOfTypePayeeTrnRsV1Aggregate = paramTypePayeeSyncRsV1Aggregate.PayeeTrnRs;
      for (int i = 0; i < arrayOfTypePayeeTrnRsV1Aggregate.length; i++) {
        processPayeeTrnRs(arrayOfTypePayeeTrnRsV1Aggregate[i]);
      }
    }
  }
  
  protected void processPmtTrnRs(TypePmtTrnRsV1Aggregate paramTypePmtTrnRsV1Aggregate)
  {
    processSTATUS(paramTypePmtTrnRsV1Aggregate.TrnRsCm.Status);
    TypePmtInfoAggregate localTypePmtInfoAggregate = null;
    String str1 = null;
    TypePmtPrcStsAggregate localTypePmtPrcStsAggregate = null;
    String str2 = paramTypePmtTrnRsV1Aggregate.TrnRsCm.TrnUID;
    String str3 = null;
    Object localObject;
    if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1UnExists)
    {
      if ("PmtRs".equals(paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName))
      {
        localTypePmtInfoAggregate = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo;
        if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CurDef != null) {
          try
          {
            str3 = ValueSetCurrencyEnum.getValue(paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CurDef.value());
          }
          catch (Throwable localThrowable1) {}
        }
        str1 = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.SrvrTID;
        localTypePmtPrcStsAggregate = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts;
      }
      else if ("PmtModRs".equals(paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName))
      {
        localTypePmtInfoAggregate = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.PmtInfo;
        if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.CurDef != null) {
          try
          {
            str3 = ValueSetCurrencyEnum.getValue(paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.CurDef.value());
          }
          catch (Throwable localThrowable2) {}
        }
        str1 = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.SrvrTID;
        if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.PmtPrcStsExists) {
          localTypePmtPrcStsAggregate = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.PmtPrcSts;
        }
      }
      else if ("PmtCancRs".equals(paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName))
      {
        str1 = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtCancRs.SrvrTID;
      }
      if (this.payments != null)
      {
        if ((str2 != null) && (!"0".equals(str2))) {
          this.payment = ((Payment)this.payments.getFirstByFilter("TRNUID=" + str2));
        }
        if ((this.payment == null) && (str1 != null)) {
          this.payment = ((Payment)this.payments.getFirstByFilter("ID=" + str1));
        }
        if (this.payment == null) {
          this.payment = ((Payment)this.payments.create());
        }
      }
      this.payment.setID(str1);
      localObject = new Integer(this.status);
      this.objStatus.put(str1, ((Integer)localObject).toString());
      if (str3 != null) {
        this.payment.setAmtCurrency(str3);
      }
      if ("PmtCancRs".equals(paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName))
      {
        this.payment.setStatus(3);
        this.payment.setError(mapError(this.status));
      }
      else
      {
        if ((this.status == 0) && (this.payment.getStatus() == 1)) {
          this.payment.setStatus(2);
        }
        if (localTypePmtInfoAggregate != null) {
          processPmtInfo(localTypePmtInfoAggregate, this.payment);
        }
        if (localTypePmtPrcStsAggregate != null)
        {
          this.payment.set("PayProcessDate", DateFormatUtil.getFormatter("MM/dd/yyyy").format(getDate(localTypePmtPrcStsAggregate.DtPmtPrc).getTime()));
          if (localTypePmtPrcStsAggregate.PmtPrcCode.value() == 4) {
            this.payment.setStatus(2);
          } else if (localTypePmtPrcStsAggregate.PmtPrcCode.value() == 3) {
            this.payment.setStatus(5);
          } else if (localTypePmtPrcStsAggregate.PmtPrcCode.value() == 2) {
            this.payment.setStatus(11);
          } else if (localTypePmtPrcStsAggregate.PmtPrcCode.value() == 1) {
            this.payment.setStatus(6);
          } else if (localTypePmtPrcStsAggregate.PmtPrcCode.value() == 0) {
            this.payment.setStatus(3);
          }
        }
        if ("PmtRs".equals(paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName))
        {
          if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTIDExists) {
            this.payment.setRecPaymentID(paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTID);
          }
          if (this.payees != null)
          {
            this.payee = ((Payee)this.payees.getFirstByFilter("ID=" + paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PayeeLstID));
            if (this.payee != null) {
              this.payment.setPayee(this.payee);
            }
            this.payee = null;
          }
          if (this.payment.getReferenceNumber() == null) {
            this.payment.setReferenceNumber(str1);
          }
        }
        this.payment.setError(mapError(this.status));
        if (this.status == 2014) {
          this.payment.setStatus(8);
        }
      }
    }
    else if ((paramTypePmtTrnRsV1Aggregate.TrnRsCm.Status.Code == 2000) && (paramTypePmtTrnRsV1Aggregate.TrnRsCm.Status.MessageExists) && (paramTypePmtTrnRsV1Aggregate.TrnRsCm.Status.Message.startsWith("[ErrorCode ")))
    {
      localObject = paramTypePmtTrnRsV1Aggregate.TrnRsCm.Status.Message;
      int i = ((String)localObject).indexOf("[ErrorCode ") + "[ErrorCode ".length();
      int j = ((String)localObject).indexOf("]");
      String str4 = ((String)localObject).substring(i, j);
      this.status = Integer.parseInt(str4);
    }
  }
  
  protected void processPmtSyncRs(TypePmtSyncRsV1Aggregate paramTypePmtSyncRsV1Aggregate)
  {
    if ((paramTypePmtSyncRsV1Aggregate != null) && (paramTypePmtSyncRsV1Aggregate.PmtTrnRs != null))
    {
      TypePmtTrnRsV1Aggregate[] arrayOfTypePmtTrnRsV1Aggregate = paramTypePmtSyncRsV1Aggregate.PmtTrnRs;
      for (int i = 0; i < arrayOfTypePmtTrnRsV1Aggregate.length; i++)
      {
        this.payment = null;
        processPmtTrnRs(arrayOfTypePmtTrnRsV1Aggregate[i]);
      }
    }
  }
  
  protected void processRecPmtTrnRs(TypeRecPmtTrnRsV1Aggregate paramTypeRecPmtTrnRsV1Aggregate)
  {
    processSTATUS(paramTypeRecPmtTrnRsV1Aggregate.TrnRsCm.Status);
    String str1 = null;
    String str2 = paramTypeRecPmtTrnRsV1Aggregate.TrnRsCm.TrnUID;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate = null;
    TypePmtInfoAggregate localTypePmtInfoAggregate = null;
    String str3 = null;
    String str4 = null;
    if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUnExists)
    {
      if ("RecPmtRs".equals(paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.__memberName))
      {
        str1 = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.RecSrvrTID;
        localTypeRecurrInstAggregate = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.RecurrInst;
        localTypePmtInfoAggregate = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.PmtInfo;
        if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.CurDef != null) {
          try
          {
            str4 = ValueSetCurrencyEnum.getValue(paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.CurDef.value());
          }
          catch (Throwable localThrowable1) {}
        }
        str3 = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.PayeeLstID;
      }
      else if ("RecPmtModRs".equals(paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.__memberName))
      {
        str1 = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.RecSrvrTID;
        localTypeRecurrInstAggregate = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.RecurrInst;
        localTypePmtInfoAggregate = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.PmtInfo;
        if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.CurDef != null) {
          try
          {
            str4 = ValueSetCurrencyEnum.getValue(paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.CurDef.value());
          }
          catch (Throwable localThrowable2) {}
        }
      }
      else if ("RecPmtCancRs".equals(paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.__memberName))
      {
        str1 = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtCancRs.RecSrvrTID;
      }
      if (this.recpayments != null)
      {
        if ((str2 != null) && (!"0".equals(str2))) {
          this.recpayment = ((RecPayment)this.recpayments.getFirstByFilter("TRNUID=" + str2));
        }
        if (this.recpayment == null) {
          this.recpayment = ((RecPayment)this.recpayments.getFirstByFilter("ID=" + str1));
        }
        if (this.recpayment == null) {
          this.recpayment = ((RecPayment)this.recpayments.create());
        }
      }
      this.recpayment.setID(str1);
      this.recpayment.setRecPaymentID(str1);
      Integer localInteger = new Integer(this.status);
      this.objStatus.put(str1, localInteger.toString());
      if (str4 != null) {
        this.recpayment.setAmtCurrency(str4);
      }
      if ("RecPmtCancRs".equals(paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.__memberName))
      {
        this.recpayment.setStatus(3);
      }
      else
      {
        if ((this.status == 0) && (this.recpayment.getStatus() == 1)) {
          this.recpayment.setStatus(2);
        }
        if (localTypeRecurrInstAggregate != null)
        {
          if (localTypeRecurrInstAggregate.NInstsExists)
          {
            this.recpayment.setNumberPayments(localTypeRecurrInstAggregate.NInsts);
            if (localTypeRecurrInstAggregate.NInsts < 0) {
              this.recpayment.setStatus(3);
            }
          }
          else
          {
            this.recpayment.setNumberPayments(999);
          }
          this.recpayment.setFrequency(getFrequency(localTypeRecurrInstAggregate.Freq));
        }
        if (localTypePmtInfoAggregate != null) {
          processPmtInfo(localTypePmtInfoAggregate, this.recpayment);
        }
        if ((this.payees != null) && (str3 != null))
        {
          this.payee = ((Payee)this.payees.getFirstByFilter("ID=" + str3));
          if (this.payee != null) {
            this.recpayment.setPayee(this.payee);
          }
          this.payee = null;
        }
        if (this.recpayment.getReferenceNumber() == null) {
          this.recpayment.setReferenceNumber(str1);
        }
      }
    }
  }
  
  protected void processRecPmtSyncRs(TypeRecPmtSyncRsV1Aggregate paramTypeRecPmtSyncRsV1Aggregate)
  {
    if ((paramTypeRecPmtSyncRsV1Aggregate != null) && (paramTypeRecPmtSyncRsV1Aggregate.RecPmtTrnRs != null))
    {
      TypeRecPmtTrnRsV1Aggregate[] arrayOfTypeRecPmtTrnRsV1Aggregate = paramTypeRecPmtSyncRsV1Aggregate.RecPmtTrnRs;
      for (int i = 0; i < arrayOfTypeRecPmtTrnRsV1Aggregate.length; i++)
      {
        this.recpayment = null;
        processRecPmtTrnRs(arrayOfTypeRecPmtTrnRsV1Aggregate[i]);
      }
    }
  }
  
  private int jdMethod_do(HashMap paramHashMap)
    throws Exception
  {
    int i = 10;
    if (paramHashMap != null)
    {
      String str = (String)paramHashMap.get("PAGESIZE");
      if (str != null) {
        try
        {
          i = Integer.parseInt(str);
        }
        catch (Exception localException) {}
      }
    }
    return i;
  }
  
  private static String a(Accounts paramAccounts, String paramString)
  {
    if ((paramAccounts == null) || (paramAccounts.isEmpty())) {
      return paramString;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    StringBuffer localStringBuffer1 = new StringBuffer();
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      StringBuffer localStringBuffer2 = new StringBuffer();
      for (int i = 0; i < paramAccounts.size(); i++)
      {
        Account localAccount = (Account)paramAccounts.get(i);
        if (localAccount.getID().equals(str))
        {
          localStringBuffer2.append(localAccount.getDisplayTextRoutingNumNickNameCurrency());
          break;
        }
      }
      if (localStringBuffer2.length() > 0)
      {
        if (localStringBuffer1.length() > 0) {
          localStringBuffer1.append("&&");
        }
        localStringBuffer1.append(localStringBuffer2);
      }
      else
      {
        return paramString;
      }
    }
    return localStringBuffer1.length() > 0 ? localStringBuffer1.toString() : paramString;
  }
  
  private static boolean jdMethod_if(String paramString)
  {
    if (("".equals(paramString)) || (paramString.startsWith(","))) {
      return true;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", false);
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      if (("AllAccounts".equals(str)) || ("".equals(str))) {
        return true;
      }
    }
    return false;
  }
  
  public Payments getPagedPayments(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "BillPay.getPagedPayments";
    Accounts localAccounts = (Accounts)paramHashMap.get("PAYMENT_ACCOUNTS");
    Payees localPayees = (Payees)paramHashMap.get("PAYMENT_PAYEES");
    RecPayments localRecPayments = (RecPayments)paramHashMap.get("PAYMENT_RECPAYMENTS");
    if (localRecPayments == null) {
      localRecPayments = new RecPayments();
    }
    String str2 = (String)paramHashMap.get("PAYMENT_STATUS");
    String str3 = (String)paramHashMap.get("PmtType");
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null)
    {
      localHashMap = new HashMap();
      paramPagingContext.setMap(localHashMap);
    }
    int i = 10;
    try
    {
      i = jdMethod_do(paramHashMap);
    }
    catch (Exception localException1) {}
    localHashMap.put("PAGE_SIZE", String.valueOf(i));
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
    if (paramPagingContext.getDirection().equals("FIRST"))
    {
      localObject1 = null;
      if (localReportCriteria != null) {
        localObject1 = localReportCriteria.getSortCriteria();
      }
      localObject2 = new ArrayList();
      if (localObject1 != null) {
        for (int j = 0; j < ((ReportSortCriteria)localObject1).size(); j++)
        {
          localObject4 = (ReportSortCriterion)((ReportSortCriteria)localObject1).get(j);
          localObject5 = new SortCriterion();
          ((SortCriterion)localObject5).setName(((ReportSortCriterion)localObject4).getName());
          if (((ReportSortCriterion)localObject4).getAsc()) {
            ((SortCriterion)localObject5).setAscending();
          } else {
            ((SortCriterion)localObject5).setDescending();
          }
          ((ArrayList)localObject2).add(localObject5);
        }
      }
      paramPagingContext.setSortCriteriaList((ArrayList)localObject2);
      if (paramPagingContext.getStartDate() == null)
      {
        localObject3 = GregorianCalendar.getInstance();
        ((Calendar)localObject3).add(5, -30);
        paramPagingContext.setStartDate((Calendar)localObject3);
      }
      if (paramPagingContext.getEndDate() == null)
      {
        localObject3 = GregorianCalendar.getInstance();
        ((Calendar)localObject3).add(5, 30);
        paramPagingContext.setEndDate((Calendar)localObject3);
      }
      localObject3 = new HashMap();
      if (paramSecureUser.getBusinessID() == 0) {
        ((HashMap)localObject3).put("CustomerId", String.valueOf(paramSecureUser.getPrimaryUserID()));
      } else {
        ((HashMap)localObject3).put("CustomerId", String.valueOf(paramSecureUser.getBusinessID()));
      }
      ((HashMap)localObject3).put("FIID", paramSecureUser.getBPWFIID());
      if (str3 != null)
      {
        localObject4 = new StringTokenizer(str3, ",");
        localObject5 = new String[((StringTokenizer)localObject4).countTokens()];
        for (int k = 0; ((StringTokenizer)localObject4).hasMoreTokens(); k++) {
          localObject5[k] = ((StringTokenizer)localObject4).nextToken();
        }
        ((HashMap)localObject3).put("PmtType", localObject5);
      }
      localObject4 = new StringBuffer();
      if ("PAYMENT_STATUS_PENDING".equals(str2))
      {
        ((StringBuffer)localObject4).append("WILLPROCESSON");
        ((StringBuffer)localObject4).append(",").append("FUNDSALLOCATED");
        ((StringBuffer)localObject4).append(",").append("BATCH_INPROCESS");
      }
      else if ("PAYMENT_STATUS_APPROVAL".equals(str2))
      {
        ((StringBuffer)localObject4).append("APPROVAL_PENDING");
        ((StringBuffer)localObject4).append(",").append("APPROVAL_REJECTED");
      }
      else if ("PAYMENT_STATUS_COMPLETED".equals(str2))
      {
        ((StringBuffer)localObject4).append("PROCESSEDON");
        ((StringBuffer)localObject4).append(",").append("POSTEDON");
        ((StringBuffer)localObject4).append(",").append("NOFUNDSON");
        ((StringBuffer)localObject4).append(",").append("FAILEDON");
        ((StringBuffer)localObject4).append(",").append("LIMIT_CHECK_FAILED");
        ((StringBuffer)localObject4).append(",").append("LIMIT_REVERT_FAILED");
        ((StringBuffer)localObject4).append(",").append("APPROVAL_FAILED");
        ((StringBuffer)localObject4).append(",").append("APPROVAL_NOT_ALLOWED");
        ((StringBuffer)localObject4).append(",").append("FUNDSREVERTED");
        ((StringBuffer)localObject4).append(",").append("FUNDSREVERTED_NOTIF");
      }
      if (((StringBuffer)localObject4).toString().equals("")) {
        ((HashMap)localObject3).put("StatusList", str2);
      } else {
        ((HashMap)localObject3).put("StatusList", ((StringBuffer)localObject4).toString());
      }
      if (paramHashMap.get("Amount") != null) {
        ((HashMap)localObject3).put("Amount", paramHashMap.get("Amount"));
      }
      if (paramHashMap.get("PayeeIdList") != null) {
        ((HashMap)localObject3).put("PayeeIdList", paramHashMap.get("PayeeIdList"));
      }
      if (paramHashMap.get("AcctDebitNumber") != null)
      {
        localObject5 = localAccounts.getByID((String)paramHashMap.get("AcctDebitNumber"));
        if (localObject5 != null)
        {
          ((HashMap)localObject3).put("AcctDebitNumber", ((Account)localObject5).getNumber());
          ((HashMap)localObject3).put("AcctDebitType", WireAccountMap.mapAccountTypeToStr(((Account)localObject5).getTypeValue()));
        }
      }
      localHashMap.put("SEARCH_CRITERIA", localObject3);
    }
    Object localObject1 = localHashMap.get("SORT_VALUE_MIN_TransactionIndex");
    Object localObject2 = localHashMap.get("SORT_VALUE_MAX_TransactionIndex");
    if (localObject1 != null) {
      localHashMap.put("SORT_VALUE_MIN_TransactionIndex", String.valueOf(localObject1));
    }
    if (localObject2 != null) {
      localHashMap.put("SORT_VALUE_MAX_TransactionIndex", String.valueOf(localObject2));
    }
    Object localObject3 = new PagingInfo();
    Object localObject4 = new Payments(paramSecureUser.getLocale());
    Object localObject5 = getBPWHandler();
    if (localObject5 == null) {
      throw new CSILException(31023);
    }
    try
    {
      localHashMap.remove("ReportCriteria");
      paramPagingContext.setMap(localHashMap);
      ((PagingInfo)localObject3).setPagingContext(paramPagingContext);
      localObject3 = ((BPWServices)localObject5).getPagedBillPayments((PagingInfo)localObject3);
      ArrayList localArrayList = ((PagingInfo)localObject3).getPagingResult();
      paramPagingContext.setMap(((PagingInfo)localObject3).getPagingContext().getMap());
      paramPagingContext.setSessionId(((PagingInfo)localObject3).getPagingContext().getSessionId());
      localHashMap = paramPagingContext.getMap();
      Object localObject6;
      Object localObject7;
      if (localHashMap != null)
      {
        localHashMap.put("ReportCriteria", localReportCriteria);
        localObject6 = localHashMap.get("LOWER_BOUND_TransactionIndex");
        localObject7 = localHashMap.get("UPPER_BOUND_TransactionIndex");
        try
        {
          long l;
          if (localObject6 != null)
          {
            l = Long.parseLong(localObject6.toString());
            localHashMap.put("LOWER_BOUND_TransactionIndex", new Long(l));
          }
          if (localObject7 != null)
          {
            l = Long.parseLong(localObject7.toString());
            localHashMap.put("UPPER_BOUND_TransactionIndex", new Long(l));
          }
        }
        catch (Exception localException3)
        {
          DebugLog.throwing("getPagedPayments ", localException3);
        }
        localHashMap.put("TOTAL_TRANS", String.valueOf(((PagingInfo)localObject3).getTotalTrans()));
      }
      ((Payments)localObject4).setPagingContext(((PagingInfo)localObject3).getPagingContext());
      if (localArrayList != null)
      {
        localObject6 = localArrayList.iterator();
        while (((Iterator)localObject6).hasNext())
        {
          localObject7 = ((Iterator)localObject6).next();
          if ((localObject7 instanceof PmtInfo))
          {
            PmtInfo localPmtInfo = (PmtInfo)localObject7;
            Object localObject8 = null;
            Object localObject9;
            if ((localObject7 instanceof RecPmtInfo))
            {
              localObject9 = (RecPayment)new RecPayments(((Payments)localObject4).getLocale()).createNoAdd();
              localObject8 = localObject9;
              BeansConverter.setRecPmtFromRecPmtInfo((RecPayment)localObject9, (RecPmtInfo)localPmtInfo, localPayees, localAccounts, true);
              ((Payments)localObject4).add(localObject9);
            }
            else
            {
              localObject8 = (Payment)((Payments)localObject4).create();
              BeansConverter.setPmtFromPmtInfo((Payment)localObject8, localPmtInfo, localPayees, localAccounts, true);
            }
            Object localObject10;
            if ((!this.restrictAccounts) && (!Boolean.valueOf(((Payment)localObject8).getAccountEntitled()).booleanValue()))
            {
              localObject9 = localPmtInfo.AcctDebitType;
              int n = getBPWAccountType((String)localObject9);
              localObject10 = localPmtInfo.AcctDebitID;
              Account localAccount = localAccounts.createNoAdd((String)localObject10, n);
              ((Payment)localObject8).setAccount(localAccount);
            }
            ((Payment)localObject8).setTransactionIndex(localPmtInfo.cursorID);
            if (((Payment)localObject8).getRecPaymentID() != null)
            {
              ((Payment)localObject8).set("FrequencyValue", localPmtInfo.getRecFrequencyStr());
              ((Payment)localObject8).set("Frequency", ((Payment)localObject8).getFrequency(getFrequency(localPmtInfo.getRecFrequencyStr())));
              localObject9 = getRecPaymentByID(paramSecureUser, ((Payment)localObject8).getRecPaymentID(), localAccounts, localPayees, paramHashMap);
              ((Payment)localObject8).set("NumberPayments", ((RecPayment)localObject9).getNumberPayments());
            }
            else
            {
              ((Payment)localObject8).set("FrequencyValue", "NONE");
              ((Payment)localObject8).set("Frequency", ((Payment)localObject8).getFrequency(0));
            }
            if (((Payment)localObject8).getPayee() == null)
            {
              int m = paramSecureUser.getBusinessID();
              if (m == 0) {
                m = paramSecureUser.getProfileID();
              }
              PayeeInfo localPayeeInfo = ((BPWServices)localObject5).getPayeeByListId(String.valueOf(m), String.valueOf(localPmtInfo.PayeeListID));
              if (localPayeeInfo != null)
              {
                localObject10 = new Payee();
                ((Payee)localObject10).setHostID(localPayeeInfo.PayeeID);
                ((Payee)localObject10).setName(localPayeeInfo.PayeeName);
                ((Payee)localObject10).setNickName(localPayeeInfo.NickName);
                ((Payee)localObject10).setStreet(localPayeeInfo.Addr1);
                ((Payee)localObject10).setStreet2(localPayeeInfo.Addr2);
                ((Payee)localObject10).setStreet3(localPayeeInfo.Addr3);
                ((Payee)localObject10).setCity(localPayeeInfo.City);
                ((Payee)localObject10).setState(localPayeeInfo.State);
                ((Payee)localObject10).setZipCode(localPayeeInfo.Zipcode);
                ((Payee)localObject10).setCountry(localPayeeInfo.Country);
                ((Payee)localObject10).setPhone(localPayeeInfo.Phone);
                ((Payee)localObject10).setDaysToPay(localPayeeInfo.DaysToPay);
                ((Payee)localObject10).setTrackingID(localPayeeInfo.TranID);
                ((Payee)localObject10).setUserAccountNumber(((Payment)localObject8).getAccountDisplayText());
                ((Payment)localObject8).setPayee((Payee)localObject10);
              }
            }
          }
        }
      }
    }
    catch (Exception localException2)
    {
      handleBPWException(localException2, 2110, str1);
    }
    finally
    {
      if (localObject5 != null) {
        removeBPWHandler((BPWServices)localObject5);
      }
    }
    return localObject4;
  }
  
  public Payments getLastPayments(SecureUser paramSecureUser, Payees paramPayees, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "BillPay.getLastPayments";
    int i = 0;
    String str2 = null;
    Payments localPayments = new Payments();
    if ((paramPayees != null) && (paramPayees.size() > 0))
    {
      BPWServices localBPWServices = getBPWHandler();
      if (localBPWServices == null) {
        throw new CSILException(31023);
      }
      try
      {
        LastPaymentInfo localLastPaymentInfo = new LastPaymentInfo();
        localLastPaymentInfo.setCustomerId(getProfileID());
        localLastPaymentInfo.setFiId(getFIID());
        CustomerPayeeInfo[] arrayOfCustomerPayeeInfo = new CustomerPayeeInfo[paramPayees.size()];
        Object localObject1;
        for (int j = 0; j < paramPayees.size(); j++)
        {
          localObject1 = (Payee)paramPayees.get(j);
          CustomerPayeeInfo localCustomerPayeeInfo = new CustomerPayeeInfo();
          localCustomerPayeeInfo.CustomerID = localLastPaymentInfo.getCustomerId();
          try
          {
            localCustomerPayeeInfo.PayeeListID = Integer.parseInt(((Payee)localObject1).getID());
          }
          catch (NumberFormatException localNumberFormatException) {}
          arrayOfCustomerPayeeInfo[j] = localCustomerPayeeInfo;
        }
        localLastPaymentInfo.setCustPayeeList(arrayOfCustomerPayeeInfo);
        localLastPaymentInfo = localBPWServices.getLastPayments(localLastPaymentInfo);
        if (localLastPaymentInfo != null)
        {
          i = localLastPaymentInfo.getStatusCode();
          str2 = localLastPaymentInfo.getStatusMsg();
        }
        if ((i == 26027) || (i == 16000))
        {
          i = 0;
        }
        else if (i == 0)
        {
          PmtInfo[] arrayOfPmtInfo = localLastPaymentInfo.getLastPayments();
          localObject1 = new Accounts();
          for (int k = 0; k < arrayOfPmtInfo.length; k++)
          {
            Payment localPayment = (Payment)localPayments.create();
            BeansConverter.setPmtFromPmtInfo(localPayment, arrayOfPmtInfo[k], paramPayees, (Accounts)localObject1, false);
          }
        }
      }
      catch (Exception localException)
      {
        handleBPWException(localException, 2101, str1);
      }
      finally
      {
        if (localBPWServices != null) {
          removeBPWHandler(localBPWServices);
        }
      }
      if (i != 0)
      {
        DebugLog.log("Error occurred in " + str1);
        DebugLog.log("*** BPW ErrorCode: " + i + " BPW ErrorMsg: " + str2);
        throw new CSILException(str1, mapBPWError(i, 2101), str2);
      }
    }
    return localPayments;
  }
  
  public PaymentBatch getPaymentBatchByID(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "BillPay.getLastPayments";
    int i = 0;
    String str2 = null;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(31023);
    }
    try
    {
      PaymentBatchInfo localPaymentBatchInfo = new PaymentBatchInfo();
      localPaymentBatchInfo = localBPWServices.getPaymentBatchById(paramPaymentBatch.getID());
      i = paramPaymentBatch.getErrorValue();
      Payees localPayees = (Payees)paramHashMap.get("PAYEES");
      Accounts localAccounts = (Accounts)paramHashMap.get("ACCOUNTS");
      BeansConverter.setPaymentBatchFromPaymentBatchInfo(paramPaymentBatch, localPaymentBatchInfo, localPayees, localAccounts, true);
    }
    catch (Exception localException)
    {
      handleBPWException(localException, 2102, str1);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      DebugLog.log("Error occurred in " + str1);
      DebugLog.log("*** BPW ErrorCode: " + i + " BPW ErrorMsg: " + str2);
      throw new CSILException(str1, mapBPWError(i, 2102), str2);
    }
    return paramPaymentBatch;
  }
  
  public Currency getDefaultCurrency()
    throws CSILException
  {
    String str1 = "BillPay.getDefaultCurrency";
    int i = 0;
    String str2 = null;
    BPWServices localBPWServices = getBPWHandler();
    String str3 = null;
    Currency localCurrency = null;
    if (localBPWServices == null) {
      throw new CSILException(31023);
    }
    try
    {
      str3 = localBPWServices.getBPWProperty(null, "bpw.transaction.base.currency");
      localCurrency = new Currency("0", str3, Locale.getDefault());
    }
    catch (Exception localException)
    {
      handleBPWException(localException, 2102, str1);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      DebugLog.log("Error occurred in " + str1);
      DebugLog.log("*** BPW ErrorCode: " + i + " BPW ErrorMsg: " + str2);
      throw new CSILException(str1, mapBPWError(i, 2102), str2);
    }
    return localCurrency;
  }
  
  public FundsTransactions getAllPaymentTemplates(SecureUser paramSecureUser, Payment paramPayment, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    int i = 0;
    String str1 = null;
    String str2 = "BillPay.getAllPaymentTemplates";
    Accounts localAccounts = (Accounts)paramHashMap.get("ACCOUNTS");
    Payees localPayees = (Payees)paramHashMap.get("PAYEES");
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null)
    {
      localHashMap = new HashMap();
      paramPagingContext.setMap(localHashMap);
    }
    int j = 10;
    try
    {
      j = jdMethod_do(paramHashMap);
    }
    catch (Exception localException1) {}
    localHashMap.put("PAGE_SIZE", String.valueOf(j));
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
    if (paramPagingContext.getDirection().equals("FIRST"))
    {
      localObject1 = null;
      if (localReportCriteria != null) {
        localObject1 = localReportCriteria.getSortCriteria();
      }
      localObject2 = new ArrayList();
      if (localObject1 != null) {
        for (int k = 0; k < ((ReportSortCriteria)localObject1).size(); k++)
        {
          localObject4 = (ReportSortCriterion)((ReportSortCriteria)localObject1).get(k);
          localObject5 = new SortCriterion();
          ((SortCriterion)localObject5).setName(((ReportSortCriterion)localObject4).getName());
          if (((ReportSortCriterion)localObject4).getAsc())
          {
            ((SortCriterion)localObject5).setAscending();
            System.out.println("GetAllPaymentTemplates: sortBy " + ((ReportSortCriterion)localObject4).getName() + ", Ascending");
          }
          else
          {
            ((SortCriterion)localObject5).setDescending();
            System.out.println("GetAllPaymentTemplates: sortBy " + ((ReportSortCriterion)localObject4).getName() + ", Descending");
          }
          ((ArrayList)localObject2).add(localObject5);
        }
      }
      paramPagingContext.setSortCriteriaList((ArrayList)localObject2);
      localObject3 = new HashMap();
      if (paramSecureUser.getBusinessID() == 0) {
        ((HashMap)localObject3).put("CustomerId", String.valueOf(paramSecureUser.getPrimaryUserID()));
      } else {
        ((HashMap)localObject3).put("CustomerId", String.valueOf(paramSecureUser.getBusinessID()));
      }
      localObject4 = new String[1];
      localObject4[0] = String.valueOf(paramSecureUser.getProfileID());
      ((HashMap)localObject3).put("SubmittedBys", localObject4);
      ((HashMap)localObject3).put("FIID", paramSecureUser.getBPWFIID());
      ((HashMap)localObject3).put("StatusList", "ACTIVE");
      localObject5 = new String[3];
      localObject5[0] = "TEMPLATE";
      localObject5[1] = "BATCHTEMPLATE";
      localObject5[2] = "RECTEMPLATE";
      ((HashMap)localObject3).put("PmtType", localObject5);
      localHashMap.put("SEARCH_CRITERIA", localObject3);
    }
    Object localObject1 = new PagingInfo();
    Object localObject2 = new FundsTransactions();
    Object localObject3 = new Payments(paramSecureUser.getLocale());
    Object localObject4 = new RecPayments(paramSecureUser.getLocale());
    Object localObject5 = getBPWHandler();
    if (localObject5 == null) {
      throw new CSILException(31023);
    }
    try
    {
      localHashMap.remove("ReportCriteria");
      paramPagingContext.setMap(localHashMap);
      ((PagingInfo)localObject1).setPagingContext(paramPagingContext);
      localObject1 = ((BPWServices)localObject5).getPagedBillPayments((PagingInfo)localObject1);
      if (localObject1 != null)
      {
        i = ((PagingInfo)localObject1).getStatusCode();
        str1 = ((PagingInfo)localObject1).getStatusMsg();
      }
      if (i == 0)
      {
        ArrayList localArrayList = ((PagingInfo)localObject1).getPagingResult();
        paramPagingContext.setMap(((PagingInfo)localObject1).getPagingContext().getMap());
        paramPagingContext.setSessionId(((PagingInfo)localObject1).getPagingContext().getSessionId());
        localHashMap = paramPagingContext.getMap();
        if (localHashMap != null)
        {
          localHashMap.put("ReportCriteria", localReportCriteria);
          localHashMap.put("TOTAL_TRANS", String.valueOf(((PagingInfo)localObject1).getTotalTrans()));
        }
        ((FundsTransactions)localObject2).setPagingContext(((PagingInfo)localObject1).getPagingContext());
        if (localArrayList != null)
        {
          Iterator localIterator = localArrayList.iterator();
          for (int m = 0; localIterator.hasNext(); m++)
          {
            Object localObject6 = localIterator.next();
            Object localObject7;
            Object localObject8;
            if ((localObject6 instanceof PmtInfo))
            {
              localObject7 = (PmtInfo)localObject6;
              localObject8 = (Payment)((Payments)localObject3).createNoAdd();
              if ((localObject6 instanceof RecPmtInfo)) {
                localObject8 = (RecPayment)((RecPayments)localObject4).createNoAdd();
              }
              ((FundsTransactions)localObject2).add(localObject8);
              BeansConverter.xsetPmtFromPmtInfo((Payment)localObject8, (PmtInfo)localObject7, localPayees, localAccounts, true);
              if ((!this.restrictAccounts) && (!Boolean.valueOf(((Payment)localObject8).getAccountEntitled()).booleanValue()))
              {
                String str3 = ((PmtInfo)localObject7).AcctDebitType;
                int n = getBPWAccountType(str3);
                String str4 = ((PmtInfo)localObject7).AcctDebitID;
                Account localAccount = localAccounts.createNoAdd(str4, n);
                ((Payment)localObject8).setAccount(localAccount);
              }
              ((Payment)localObject8).setTransactionIndex(((PmtInfo)localObject7).cursorID);
              ((Payment)localObject8).set("FrequencyValue", "NONE");
              ((Payment)localObject8).set("Frequency", ((Payment)localObject8).getFrequency(0));
              if (((Payment)localObject8).getTemplateName() == null) {
                ((Payment)localObject8).setTemplateName("template" + String.valueOf(m));
              }
            }
            else if ((localObject6 instanceof PaymentBatchInfo))
            {
              localObject7 = (PaymentBatchInfo)localObject6;
              localObject8 = new PaymentBatch(paramSecureUser.getLocale());
              ((FundsTransactions)localObject2).add(localObject8);
              BeansConverter.setPaymentBatchFromPaymentBatchInfo((PaymentBatch)localObject8, (PaymentBatchInfo)localObject7, localPayees, localAccounts, true);
            }
          }
        }
      }
    }
    catch (Exception localException2)
    {
      handleBPWException(localException2, 2106, str2);
    }
    finally
    {
      if (localObject5 != null) {
        removeBPWHandler((BPWServices)localObject5);
      }
    }
    if (i != 0)
    {
      DebugLog.log("Error occurred in " + str2);
      DebugLog.log("*** BPW ErrorCode: " + i + " BPW ErrorMsg: " + str1);
      throw new CSILException(str2, mapBPWError(i, 2106), str1);
    }
    return localObject2;
  }
  
  public Payment addPaymentTemplate(SecureUser paramSecureUser, Payment paramPayment, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "BillPay.addPaymentTemplate";
    int i = 0;
    String str2 = null;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(31023);
    }
    try
    {
      Object localObject1 = new PmtInfo();
      if ((paramPayment instanceof RecPayment)) {
        localObject1 = new RecPmtInfo();
      }
      BeansConverter.setPmtInfoFromPmt((PmtInfo)localObject1, paramPayment);
      localObject1 = localBPWServices.addBillPayment((PmtInfo)localObject1);
      i = ((PmtInfo)localObject1).getStatusCode();
      str2 = ((PmtInfo)localObject1).getStatusMsg();
      if (i == 0)
      {
        Payees localPayees = (Payees)paramHashMap.get("PAYEES");
        Account localAccount = (Account)paramHashMap.get("ACCOUNT");
        Accounts localAccounts = new Accounts(localAccount.getLocale());
        localAccounts.add(localAccount);
        BeansConverter.xsetPmtFromPmtInfo(paramPayment, (PmtInfo)localObject1, localPayees, localAccounts, true);
      }
    }
    catch (Exception localException)
    {
      handleBPWException(localException, 2107, str1);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      DebugLog.log("Error occurred in " + str1);
      DebugLog.log("*** BPW ErrorCode: " + i + " BPW ErrorMsg: " + str2);
      throw new CSILException(str1, mapBPWError(i, 2107), str2);
    }
    return paramPayment;
  }
  
  public Payment modifyPaymentTemplate(SecureUser paramSecureUser, Payment paramPayment, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "BillPay.modifyPaymentTemplate";
    int i = 0;
    String str2 = null;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(31023);
    }
    try
    {
      Object localObject1 = new PmtInfo();
      if ((paramPayment instanceof RecPayment)) {
        localObject1 = new RecPmtInfo();
      }
      BeansConverter.setPmtInfoFromPmt((PmtInfo)localObject1, paramPayment);
      localObject1 = localBPWServices.modifyBillPayment((PmtInfo)localObject1);
      i = ((PmtInfo)localObject1).getStatusCode();
      str2 = ((PmtInfo)localObject1).getStatusMsg();
      if (i == 0)
      {
        Payees localPayees = new Payees();
        localPayees.add(paramPayment.getPayee());
        Account localAccount = (Account)paramHashMap.get("ACCOUNT");
        Accounts localAccounts = new Accounts(localAccount.getLocale());
        localAccounts.add(localAccount);
        BeansConverter.xsetPmtFromPmtInfo(paramPayment, (PmtInfo)localObject1, localPayees, localAccounts, true);
      }
    }
    catch (Exception localException)
    {
      handleBPWException(localException, 2108, str1);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      DebugLog.log("Error occurred in " + str1);
      DebugLog.log("*** BPW ErrorCode: " + i + " BPW ErrorMsg: " + str2);
      throw new CSILException(str1, mapBPWError(i, 2108), str2);
    }
    return paramPayment;
  }
  
  public void deletePaymentTemplate(SecureUser paramSecureUser, Payment paramPayment, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "BillPay.deletePaymentTemplate";
    int i = 0;
    String str2 = null;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(31023);
    }
    try
    {
      Object localObject1 = new PmtInfo();
      if ((paramPayment instanceof RecPayment)) {
        localObject1 = new RecPmtInfo();
      }
      BeansConverter.setPmtInfoFromPmt((PmtInfo)localObject1, paramPayment);
      localObject1 = localBPWServices.deleteBillPayment((PmtInfo)localObject1);
      i = ((PmtInfo)localObject1).getStatusCode();
      str2 = ((PmtInfo)localObject1).getStatusMsg();
      if (i != 0) {}
    }
    catch (Exception localException)
    {
      handleBPWException(localException, 2109, str1);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      DebugLog.log("Error occurred in " + str1);
      DebugLog.log("*** BPW ErrorCode: " + i + " BPW ErrorMsg: " + str2);
      throw new CSILException(str1, mapBPWError(i, 2109), str2);
    }
  }
  
  public void addPaymentBatch(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "BillPay.addPaymentBatch";
    int i = 0;
    String str2 = null;
    PaymentBatchInfo localPaymentBatchInfo = null;
    paramPaymentBatch.setID(null);
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(31023);
    }
    try
    {
      localPaymentBatchInfo = new PaymentBatchInfo();
      paramPaymentBatch.setStatus("ACTIVE");
      BeansConverter.setPaymentBatchInfoFromPaymentBatch(localPaymentBatchInfo, paramPaymentBatch);
      populateOBOAgentInfo(this.secureUser, this._ud);
      localPaymentBatchInfo = localBPWServices.addPaymentBatch(localPaymentBatchInfo);
      i = localPaymentBatchInfo.getStatusCode();
    }
    catch (Exception localException)
    {
      handleBPWException(localException, 2103, str1);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      DebugLog.log("Error occurred in " + str1);
      DebugLog.log("*** BPW ErrorCode: " + i + " BPW ErrorMsg: " + str2);
      throw new CSILException(str1, mapBPWError(i, 2103), str2);
    }
    Payees localPayees = (Payees)paramHashMap.get("PAYEES");
    Accounts localAccounts = (Accounts)paramHashMap.get("ACCOUNTS");
    BeansConverter.setPaymentBatchFromPaymentBatchInfo(paramPaymentBatch, localPaymentBatchInfo, localPayees, localAccounts, true);
  }
  
  public void cancelPaymentBatch(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "BillPay.cancelPaymentBatch";
    int i = 0;
    String str2 = null;
    PaymentBatchInfo localPaymentBatchInfo = null;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(31023);
    }
    try
    {
      localPaymentBatchInfo = new PaymentBatchInfo();
      BeansConverter.setPaymentBatchInfoFromPaymentBatch(localPaymentBatchInfo, paramPaymentBatch);
      localPaymentBatchInfo = localBPWServices.deletePaymentBatch(localPaymentBatchInfo);
    }
    catch (Exception localException)
    {
      handleBPWException(localException, 2105, str1);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      DebugLog.log("Error occurred in " + str1);
      DebugLog.log("*** BPW ErrorCode: " + i + " BPW ErrorMsg: " + str2);
      throw new CSILException(str1, mapBPWError(i, 2105), str2);
    }
  }
  
  public PaymentBatch modifyPaymentBatch(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "BillPay.modifyPaymentBatch";
    int i = 0;
    String str2 = null;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(31023);
    }
    try
    {
      for (int j = 0; j < paramPaymentBatch.getPayments().size(); j++)
      {
        Payment localPayment = (Payment)paramPaymentBatch.getPayments().get(j);
        if (localPayment.getStatus() == 1) {
          localPayment.setID("0");
        }
      }
      PaymentBatchInfo localPaymentBatchInfo = new PaymentBatchInfo();
      BeansConverter.setPaymentBatchInfoFromPaymentBatch(localPaymentBatchInfo, paramPaymentBatch);
      for (int k = 0; k < localPaymentBatchInfo.getPayments().length; k++)
      {
        localObject1 = localPaymentBatchInfo.getPayments()[k];
        int m = 0;
        try
        {
          m = Integer.parseInt(((PmtInfo)localObject1).SrvrTID);
        }
        catch (NumberFormatException localNumberFormatException) {}
        if (new BigDecimal(((PmtInfo)localObject1).getAmt()).compareTo(aF) == 0) {
          ((PmtInfo)localObject1).action = "del";
        } else if (m == 0) {
          ((PmtInfo)localObject1).action = "add";
        } else {
          ((PmtInfo)localObject1).action = "mod";
        }
      }
      localPaymentBatchInfo.setPaymentCount(String.valueOf(localPaymentBatchInfo.getPayments().length));
      localPaymentBatchInfo = localBPWServices.modifyPaymentBatch(localPaymentBatchInfo);
      Payees localPayees = (Payees)paramHashMap.get("PAYEES");
      Object localObject1 = (Accounts)paramHashMap.get("ACCOUNTS");
      BeansConverter.setPaymentBatchFromPaymentBatchInfo(paramPaymentBatch, localPaymentBatchInfo, localPayees, (Accounts)localObject1, true);
    }
    catch (Exception localException)
    {
      handleBPWException(localException, 2104, str1);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      DebugLog.log("Error occurred in " + str1);
      DebugLog.log("*** BPW ErrorCode: " + i + " BPW ErrorMsg: " + str2);
      throw new CSILException(str1, mapBPWError(i, 2104), str2);
    }
    return paramPaymentBatch;
  }
  
  protected BPWServices getBPWHandler()
  {
    BPWServices localBPWServices = null;
    ContextPool localContextPool = null;
    Context localContext = null;
    for (int i = 0; i < this.provider_url_list.size(); i++)
    {
      String str = (String)this.provider_url_list.get(i);
      try
      {
        if (debugService)
        {
          DebugLog.log(Level.INFO, "##################### Ping BPW Server ################");
          DebugLog.log(Level.INFO, "provider_url = " + str);
        }
        if (!ping(str)) {
          continue;
        }
      }
      catch (CSILException localCSILException)
      {
        continue;
      }
      try
      {
        DebugLog.log(Level.INFO, "##################### Getting BPW Handler ################");
        DebugLog.log(Level.INFO, "provider_url = " + str);
        localContextPool = getContextPool(str);
        localContext = localContextPool.getContext();
        Object localObject1 = localContext.lookup(this.aD);
        localObject2 = (BPWServicesHome)PortableRemoteObject.narrow(localObject1, BPWServicesHome.class);
        localBPWServices = ((BPWServicesHome)localObject2).create();
        localContextPool.returnContext(localContext);
        return localBPWServices;
      }
      catch (Throwable localThrowable)
      {
        Object localObject2;
        DebugLog.throwing("Couldn't get handler in BankSim service for " + str, localThrowable);
        if ((localContext != null) && (localContextPool != null)) {
          try
          {
            localContext = localContextPool.refreshContext(localContext);
            localObject2 = localContext.lookup(this.aD);
            BPWServicesHome localBPWServicesHome = (BPWServicesHome)PortableRemoteObject.narrow(localObject2, BPWServicesHome.class);
            localBPWServices = localBPWServicesHome.create();
            localContextPool.returnContext(localContext);
            return localBPWServices;
          }
          catch (Exception localException)
          {
            DebugLog.throwing("Couldn't refresh the contexts for " + str, localException);
            if ((localContextPool != null) && (localContext != null)) {
              localContextPool.returnContext(localContext);
            }
          }
        }
      }
    }
    return null;
  }
  
  protected static void removeBPWHandler(BPWServices paramBPWServices)
  {
    if (paramBPWServices != null) {
      try
      {
        if (debugService) {
          DebugLog.log(Level.INFO, "##################### Removing BPW Handler ################");
        }
        paramBPWServices.remove();
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("Error removing bpw handler", localThrowable);
      }
    }
  }
  
  private void a(Payment paramPayment)
  {
    if ((this._limitID != -1) && (paramPayment.get("FAILED_LIMITS") == null))
    {
      Limits localLimits = new Limits();
      Object localObject;
      if (this._multipleLimits != null)
      {
        localObject = new StringTokenizer(this._multipleLimits, ",");
        while (((StringTokenizer)localObject).hasMoreTokens())
        {
          Limit localLimit = new Limit();
          int i = Integer.parseInt(((StringTokenizer)localObject).nextToken());
          localLimit.setLimitId(i);
          localLimit.setGroupId(-1);
          localLimits.add(localLimit);
        }
      }
      else
      {
        localObject = new Limit();
        ((Limit)localObject).setLimitId(this._limitID);
        ((Limit)localObject).setGroupId(-1);
        localLimits.add(localObject);
      }
      paramPayment.put("FAILED_LIMITS", localLimits);
    }
  }
  
  private int a(Payment paramPayment, PmtInfo paramPmtInfo)
  {
    Limits localLimits = null;
    int i = 0;
    if (paramPmtInfo.getStatusCode() != 0)
    {
      i = mapError(paramPmtInfo.getStatusCode());
      int j = paramPmtInfo.getStatusMsg().indexOf("Limit ID: ");
      Object localObject;
      if (j != -1)
      {
        localObject = paramPmtInfo.getStatusMsg().substring(j + "Limit ID: ".length(), paramPmtInfo.getStatusMsg().length());
        try
        {
          this._limitID = Integer.parseInt((String)localObject);
        }
        catch (Throwable localThrowable)
        {
          this._limitID = -1;
        }
        if ((paramPmtInfo.getStatusMsg().indexOf('{') > 0) && (paramPmtInfo.getStatusMsg().indexOf('}') > 0)) {
          this._multipleLimits = paramPmtInfo.getStatusMsg().substring(paramPmtInfo.getStatusMsg().indexOf('{') + 1, paramPmtInfo.getStatusMsg().indexOf('}'));
        } else {
          this._multipleLimits = null;
        }
      }
      if ((this._limitID != -1) && (paramPayment.get("FAILED_LIMITS") == null))
      {
        localLimits = new Limits();
        if (this._multipleLimits != null)
        {
          localObject = new StringTokenizer(this._multipleLimits, ",");
          while (((StringTokenizer)localObject).hasMoreTokens())
          {
            Limit localLimit = new Limit();
            int k = Integer.parseInt(((StringTokenizer)localObject).nextToken());
            localLimit.setLimitId(k);
            localLimit.setGroupId(-1);
            localLimits.add(localLimit);
          }
        }
        else
        {
          localObject = new Limit();
          ((Limit)localObject).setLimitId(this._limitID);
          ((Limit)localObject).setGroupId(-1);
          localLimits.add(localObject);
        }
      }
      paramPayment.put("FAILED_LIMITS", localLimits);
      if (paramPmtInfo.getStatusMsg().indexOf("Approval is not allowed") != -1) {
        i = 20003;
      } else if (paramPmtInfo.getStatusMsg().indexOf("Approval failed because no flow defined") != -1) {
        i = 20011;
      } else if (paramPmtInfo.getStatusMsg().indexOf("Limit Check Failed") != -1) {
        i = 1054;
      } else if (paramPmtInfo.getStatusMsg().indexOf("Limit Revert Failed") != -1) {
        i = 20004;
      } else if (paramPmtInfo.getStatusMsg().indexOf("Approval failed because create item restricted") != -1) {
        i = 30216;
      } else if (paramPmtInfo.getStatusMsg().indexOf("Approval Failed") != -1) {
        i = 30200;
      }
    }
    return i;
  }
  
  private static void jdMethod_if(ReportResult paramReportResult, int paramInt1, String paramString1, int paramInt2, String paramString2)
    throws RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportColumn localReportColumn = new ReportColumn(localLocale);
    if (paramInt1 > 0)
    {
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(paramInt1, localLocale));
      localReportColumn.setLabels(localArrayList);
    }
    if (paramString1 != null) {
      localReportColumn.setDataType(paramString1);
    }
    if (paramInt2 != 0) {
      localReportColumn.setWidthAsPercent(paramInt2);
    }
    if (paramString2 != null) {
      localReportColumn.setJustification(paramString2);
    }
    paramReportResult.addColumn(localReportColumn);
  }
  
  protected CustomerPayeeInfo populateCustomerPayeeInfo(CustomerPayeeInfo paramCustomerPayeeInfo, PayeeInfo paramPayeeInfo, Payee paramPayee, SecureUser paramSecureUser)
  {
    if (paramCustomerPayeeInfo.getCustomerInfo() == null) {
      paramCustomerPayeeInfo.setCustomerInfo(new CustomerInfo());
    }
    if (paramCustomerPayeeInfo.getPayeeInfo() == null) {
      paramCustomerPayeeInfo.setPayeeInfo(new PayeeInfo());
    }
    if (paramSecureUser.getBusinessID() == 0)
    {
      paramCustomerPayeeInfo.CustomerID = Integer.toString(paramSecureUser.getPrimaryUserID());
      paramCustomerPayeeInfo.customerInfo.customerID = Integer.toString(paramSecureUser.getPrimaryUserID());
    }
    else
    {
      paramCustomerPayeeInfo.CustomerID = Integer.toString(paramSecureUser.getBusinessID());
      paramCustomerPayeeInfo.customerInfo.customerID = Integer.toString(paramSecureUser.getBusinessID());
    }
    paramCustomerPayeeInfo.PayAcct = paramPayee.getUserAccountNumber();
    paramCustomerPayeeInfo.PayeeID = paramPayee.getHostID();
    paramCustomerPayeeInfo.setPayeeInfo(paramPayeeInfo);
    paramCustomerPayeeInfo.payeeInfo.TranID = TrackingIDGenerator.GetNextID();
    paramCustomerPayeeInfo.payeeInfo.setSubmittedBy(Integer.toString(paramSecureUser.getProfileID()));
    if (paramPayee.getID() != null) {
      paramCustomerPayeeInfo.PayeeListID = Integer.parseInt(paramPayee.getID());
    }
    return paramCustomerPayeeInfo;
  }
  
  public Payee getGlobalPayee(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPay.getGlobalPayee";
    int i = -1;
    PayeeInfo localPayeeInfo = null;
    Payee localPayee = new Payee();
    BPWServices localBPWServices = null;
    try
    {
      localBPWServices = getBPWHandler();
    }
    catch (Exception localException1)
    {
      try
      {
        handleBPWException(localException1, 31023, str);
      }
      catch (CSILException localCSILException)
      {
        localCSILException.printStackTrace();
      }
    }
    try
    {
      localPayeeInfo = localBPWServices.getGlobalPayee(Integer.toString(paramInt));
      localPayee = BeansConverter.PayeeInfoToPayee(localPayeeInfo);
    }
    catch (Exception localException2)
    {
      try
      {
        handleBPWException(localException2, 2116, str);
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
      }
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return localPayee;
  }
  
  public class a
    extends Base.a
  {
    public a()
    {
      super();
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals(BillPay.aC)) {
        BillPay.this.defaultDaysToPay = Integer.valueOf(paramString2).intValue();
      } else {
        super.attribute(paramString1, paramString2, paramBoolean);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.banksim2.BillPay
 * JD-Core Version:    0.7.0.1
 */