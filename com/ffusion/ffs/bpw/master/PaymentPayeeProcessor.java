package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.CustPayee;
import com.ffusion.ffs.bpw.db.CustPayeeRoute;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Fulfillment;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PayeeToRoute;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.db.Trans;
import com.ffusion.ffs.bpw.fulfill.FulfillAgent;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class PaymentPayeeProcessor
  implements DBConsts, BPWResource, FFSConst, ACHConsts
{
  private int cH = 1;
  private boolean cJ;
  private boolean cK;
  private int cI = 1;
  private boolean cM = false;
  private boolean cL = false;
  
  public PaymentPayeeProcessor()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.cI = localPropertyConfig.LogLevel;
    this.cM = localPropertyConfig.UseExtdPayeeID;
    this.cL = localPropertyConfig.OneCustPayeeToOnePersonalPayee;
    this.cH = localPropertyConfig.LogLevel;
  }
  
  public String[] getGlobalPayeeGroups()
    throws OutOfMemoryError, FFSException
  {
    arrayOfString = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      arrayOfString = Payee.getGlobalPayeeGroups(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      return arrayOfString;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
  
  public PayeeInfo[] searchGlobalPayees(PayeeInfo paramPayeeInfo, int paramInt)
    throws FFSException
  {
    arrayOfPayeeInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      arrayOfPayeeInfo = Payee.searchGlobalPayees(localFFSConnectionHolder, paramPayeeInfo, paramInt);
      localFFSConnectionHolder.conn.commit();
      return arrayOfPayeeInfo;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
  
  public PayeeInfo addGlobalPayee(PayeeInfo paramPayeeInfo)
    throws FFSException
  {
    FFSDebug.log("PaymentPayeeProcessor.addGlobalPayee: ", ": Start ", 6);
    paramPayeeInfo = jdMethod_do(paramPayeeInfo);
    if (paramPayeeInfo.getStatusCode() != 0) {
      return paramPayeeInfo;
    }
    String str1 = null;
    Payee localPayee = new Payee(paramPayeeInfo);
    FFSConnectionHolder localFFSConnectionHolder;
    Object localObject1;
    Object localObject2;
    if (!a(paramPayeeInfo, false))
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      try
      {
        localPayee.setPayeeID();
        localPayee.setStatus("NEW");
        localPayee.storeToDB(localFFSConnectionHolder);
        str1 = localPayee.getPayeeID();
        paramPayeeInfo.PayeeRouteInfo.PayeeID = str1;
        paramPayeeInfo.PayeeRouteInfo.ExtdPayeeID = localPayee.getExtdPayeeID();
        PayeeToRoute localPayeeToRoute = new PayeeToRoute(paramPayeeInfo.PayeeRouteInfo);
        localPayeeToRoute.storeToDB(localFFSConnectionHolder);
        if (this.cH >= 3)
        {
          localObject1 = BPWLocaleUtil.getMessage(1069, null, "BILLPAY_AUDITLOG_MESSAGE");
          localObject2 = new String[] { localObject1 };
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject2, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), "", paramPayeeInfo.getAgentId(), paramPayeeInfo.getAgentType(), localLocalizableString, paramPayeeInfo.TranID, 6302, 0, null, paramPayeeInfo.PayeeRouteInfo.CurrencyCode, null, paramPayeeInfo.Status, null, null, null, null, 0);
        }
        localFFSConnectionHolder.conn.commit();
        try
        {
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Throwable localThrowable1)
        {
          localThrowable1.printStackTrace();
        }
        if (this.cH < 3) {
          break label550;
        }
      }
      catch (BPWException localBPWException1)
      {
        localFFSConnectionHolder.conn.rollback();
        throw new FFSException(localBPWException1.getErrorCode(), localBPWException1.getMessage());
      }
      catch (Throwable localThrowable2)
      {
        localFFSConnectionHolder.conn.rollback();
        throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
      }
      finally
      {
        try
        {
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Throwable localThrowable5)
        {
          localThrowable5.printStackTrace();
        }
      }
    }
    else
    {
      paramPayeeInfo.setStatusCode(26042);
      paramPayeeInfo.setStatusMsg("GLOBAL PAYEE already Exists");
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      String str2 = BPWLocaleUtil.getMessage(1070, null, "BILLPAY_AUDITLOG_MESSAGE");
      localObject1 = new String[] { str2 };
      localObject2 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject1, "BILLPAY_AUDITLOG_MESSAGE");
      try
      {
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), "", paramPayeeInfo.getAgentId(), paramPayeeInfo.getAgentType(), (ILocalizable)localObject2, paramPayeeInfo.TranID, 6302, 0, null, paramPayeeInfo.PayeeRouteInfo.CurrencyCode, null, paramPayeeInfo.Status, null, null, null, null, 0);
        localFFSConnectionHolder.conn.commit();
        try
        {
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Throwable localThrowable3)
        {
          localThrowable3.printStackTrace();
        }
        FFSDebug.log("PaymentPayeeProcessor.addGlobalPayee: ", ": End ", 6);
      }
      catch (BPWException localBPWException2)
      {
        localFFSConnectionHolder.conn.rollback();
        throw new FFSException(localBPWException2.getErrorCode(), localBPWException2.getMessage());
      }
      catch (Throwable localThrowable4)
      {
        localFFSConnectionHolder.conn.rollback();
        throw new FFSException(localThrowable4, FFSDebug.stackTrace(localThrowable4));
      }
      finally
      {
        try
        {
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Throwable localThrowable6)
        {
          localThrowable6.printStackTrace();
        }
      }
    }
    label550:
    return paramPayeeInfo;
  }
  
  private PayeeInfo jdMethod_do(PayeeInfo paramPayeeInfo)
    throws BPWException, FFSException
  {
    FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", ": Start ", 6);
    if (paramPayeeInfo == null)
    {
      paramPayeeInfo = new PayeeInfo();
      paramPayeeInfo.setStatusCode(16000);
      str = BPWLocaleUtil.getMessage(16000, new String[] { "PayeeInfo" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", str, 0);
      return paramPayeeInfo;
    }
    if (paramPayeeInfo.PayeeLevelType == null)
    {
      paramPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "PayeeLevelType" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", str, 0);
      return paramPayeeInfo;
    }
    if (paramPayeeInfo.Addr1 == null)
    {
      paramPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "Addr1" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", str, 0);
      return paramPayeeInfo;
    }
    if (paramPayeeInfo.City == null)
    {
      paramPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "City" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", str, 0);
      return paramPayeeInfo;
    }
    if (paramPayeeInfo.State == null)
    {
      paramPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "State" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", str, 0);
      return paramPayeeInfo;
    }
    if (paramPayeeInfo.Zipcode == null)
    {
      paramPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "Zipcode" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", str, 0);
      return paramPayeeInfo;
    }
    if (paramPayeeInfo.Country == null)
    {
      paramPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "Country" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", str, 0);
      return paramPayeeInfo;
    }
    if (paramPayeeInfo.PayeeRouteInfo == null)
    {
      paramPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "PayeeRouteInfo" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", str, 0);
      return paramPayeeInfo;
    }
    if (paramPayeeInfo.PayeeRouteInfo.RouteID == 0)
    {
      paramPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "PayeeRouteInfo.RouteID" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", str, 0);
      return paramPayeeInfo;
    }
    if (a(paramPayeeInfo.PayeeRouteInfo))
    {
      if (paramPayeeInfo.PayeeRouteInfo.BankID == null)
      {
        paramPayeeInfo.setStatusCode(16010);
        str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "PayeeRouteInfo.BankID" }, "BILLPAY_AUDITLOG_MESSAGE");
        paramPayeeInfo.setStatusMsg(str);
        FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", str, 0);
        return paramPayeeInfo;
      }
      if (paramPayeeInfo.PayeeRouteInfo.AcctID == null)
      {
        paramPayeeInfo.setStatusCode(16010);
        str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "PayeeRouteInfo.AcctID" }, "BILLPAY_AUDITLOG_MESSAGE");
        paramPayeeInfo.setStatusMsg(str);
        FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", str, 0);
        return paramPayeeInfo;
      }
      if (paramPayeeInfo.PayeeRouteInfo.AcctType == null)
      {
        paramPayeeInfo.setStatusCode(16010);
        str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "PayeeRouteInfo.AcctType" }, "BILLPAY_AUDITLOG_MESSAGE");
        paramPayeeInfo.setStatusMsg(str);
        FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", str, 0);
        return paramPayeeInfo;
      }
    }
    if (paramPayeeInfo.PayeeRouteInfo.CurrencyCode == null)
    {
      paramPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "PayeeRouteInfo.CurrencyCode" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", str, 0);
      return paramPayeeInfo;
    }
    String str = BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE");
    paramPayeeInfo.setStatusCode(0);
    paramPayeeInfo.setStatusMsg(str);
    FFSDebug.log("PaymentPayeeProcessor.validateGlobalPayeeInfo: ", ": End ", 6);
    return paramPayeeInfo;
  }
  
  private CustomerPayeeInfo a(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws BPWException, FFSException
  {
    FFSDebug.log("PaymentPayeeProcessor.validateCustomerPayeeInfo : ", ": Start ", 6);
    if (paramCustomerPayeeInfo == null)
    {
      paramCustomerPayeeInfo = new CustomerPayeeInfo();
      paramCustomerPayeeInfo.setStatusCode(16000);
      str = BPWLocaleUtil.getMessage(26102, new String[] { "CustomerPayeeInfo" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramCustomerPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateCustomerPayeeInfo : ", str, 0);
      return paramCustomerPayeeInfo;
    }
    if (paramCustomerPayeeInfo.CustomerID == null)
    {
      paramCustomerPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(26102, new String[] { "CustomerPayeeInfo", "CustomerID" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramCustomerPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateCustomerPayeeInfo : ", str, 0);
      return paramCustomerPayeeInfo;
    }
    if (paramCustomerPayeeInfo.payeeInfo == null)
    {
      paramCustomerPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "CustomerPayeeInfo", "PayeeInfo" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramCustomerPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateCustomerPayeeInfo : ", str, 0);
      return paramCustomerPayeeInfo;
    }
    if (paramCustomerPayeeInfo.payeeInfo.Addr1 == null)
    {
      paramCustomerPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "Addr1" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramCustomerPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateCustomerPayeeInfo : ", str, 0);
      return paramCustomerPayeeInfo;
    }
    if (paramCustomerPayeeInfo.payeeInfo.City == null)
    {
      paramCustomerPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "City" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramCustomerPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateCustomerPayeeInfo : ", str, 0);
      return paramCustomerPayeeInfo;
    }
    if (paramCustomerPayeeInfo.payeeInfo.State == null)
    {
      paramCustomerPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "State" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramCustomerPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateCustomerPayeeInfo : ", str, 0);
      return paramCustomerPayeeInfo;
    }
    if (paramCustomerPayeeInfo.payeeInfo.Zipcode == null)
    {
      paramCustomerPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "Zipcode" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramCustomerPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateCustomerPayeeInfo : ", str, 0);
      return paramCustomerPayeeInfo;
    }
    if (paramCustomerPayeeInfo.payeeInfo.Country == null)
    {
      paramCustomerPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "Country" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramCustomerPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateCustomerPayeeInfo : ", str, 0);
      return paramCustomerPayeeInfo;
    }
    if (paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo == null)
    {
      paramCustomerPayeeInfo.setStatusCode(16010);
      str = BPWLocaleUtil.getMessage(16010, new String[] { "PayeeInfo", "PayeeRouteInfo" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramCustomerPayeeInfo.setStatusMsg(str);
      FFSDebug.log("PaymentPayeeProcessor.validateCustomerPayeeInfo : ", str, 0);
      return paramCustomerPayeeInfo;
    }
    String str = BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE");
    paramCustomerPayeeInfo.setStatusCode(0);
    paramCustomerPayeeInfo.setStatusMsg(str);
    FFSDebug.log("PaymentPayeeProcessor.validateCustomerPayeeInfo : ", ": End ", 6);
    return paramCustomerPayeeInfo;
  }
  
  private CustomerPayeeInfo a(CustomerPayeeInfo paramCustomerPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException, FFSException
  {
    String str = null;
    Customer localCustomer = new Customer();
    CustomerInfo localCustomerInfo = null;
    localCustomerInfo = Customer.getCustomerByID(paramCustomerPayeeInfo.CustomerID, paramFFSConnectionHolder);
    if (localCustomerInfo == null)
    {
      paramCustomerPayeeInfo.setStatusCode(26101);
      str = BPWLocaleUtil.getMessage(26101, null, "BILLPAY_AUDITLOG_MESSAGE");
      paramCustomerPayeeInfo.setStatusMsg("Invalid Cudtomer");
      FFSDebug.log("PaymentPayeeProcessor.validateCustomer : ", str, 0);
      return paramCustomerPayeeInfo;
    }
    str = BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE");
    paramCustomerPayeeInfo.setStatusCode(0);
    paramCustomerPayeeInfo.setStatusMsg(str);
    FFSDebug.log("PaymentPayeeProcessor.validateCustomer : ", ": End ", 6);
    return paramCustomerPayeeInfo;
  }
  
  private static final boolean a(PayeeRouteInfo paramPayeeRouteInfo)
    throws FFSException
  {
    FFSDebug.log("PaymentPayeeProcessor.isFulfillmentSystemON_US: ", ": Start ", 6);
    String str = null;
    FulfillmentInfo localFulfillmentInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      localFulfillmentInfo = Fulfillment.findByRouteID(paramPayeeRouteInfo.RouteID, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      if (localFulfillmentInfo != null)
      {
        str = localFulfillmentInfo.FulfillmentSystemName.trim();
        if (str.equalsIgnoreCase("ON_US"))
        {
          FFSDebug.log("PaymentPayeeProcessor.isFulfillmentSystemON_US: ", ": End ", 6);
          boolean bool = true;
          return bool;
        }
      }
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Throwable localThrowable1)
      {
        localThrowable1.printStackTrace();
      }
      FFSDebug.log("PaymentPayeeProcessor.isFulfillmentSystemON_US: ", ": End ", 6);
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Throwable localThrowable4)
      {
        localThrowable4.printStackTrace();
      }
    }
    return false;
  }
  
  private static final boolean a(PayeeInfo paramPayeeInfo, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("PaymentPayeeProcessor.checkGlobalPayeeAlreadyExists: ", ": Start ", 6);
    String str1 = null;
    String str2 = null;
    String str3 = null;
    Payee localPayee = new Payee(paramPayeeInfo);
    if ((paramPayeeInfo.Addr2 == null) || (paramPayeeInfo.Addr2.trim().equals(""))) {
      paramPayeeInfo.Addr2 = null;
    }
    if ((paramPayeeInfo.Addr3 == null) || (paramPayeeInfo.Addr3.trim().equals(""))) {
      paramPayeeInfo.Addr3 = null;
    }
    Object localObject;
    if ((paramPayeeInfo.PayeeName != null) && (!paramPayeeInfo.PayeeName.trim().equals("")))
    {
      str2 = paramPayeeInfo.PayeeName;
      str3 = "en_US";
      localObject = searchGlobalPayees(str2, str3);
      str1 = localPayee.matchPayee((PayeeInfo[])localObject);
      if (str1 != null)
      {
        if (paramBoolean) {
          return !paramPayeeInfo.PayeeID.equals(str1);
        }
        FFSDebug.log("PaymentPayeeProcessor.checkGlobalPayeeAlreadyExists: ", ": End ", 6);
        return true;
      }
    }
    if ((paramPayeeInfo.PayeeNamesI18N != null) && (!paramPayeeInfo.PayeeNamesI18N.isEmpty()))
    {
      localObject = paramPayeeInfo.PayeeNamesI18N.keySet();
      Iterator localIterator = ((Set)localObject).iterator();
      while (localIterator.hasNext())
      {
        str3 = (String)localIterator.next();
        str2 = (String)paramPayeeInfo.PayeeNamesI18N.get(str3);
        if ((str2 != null) && (!str2.trim().equals("")))
        {
          PayeeInfo[] arrayOfPayeeInfo = searchGlobalPayees(str2, str3);
          str1 = localPayee.matchPayee(arrayOfPayeeInfo);
          if (str1 != null)
          {
            if (paramBoolean) {
              return !paramPayeeInfo.PayeeID.equals(str1);
            }
            FFSDebug.log("PaymentPayeeProcessor.checkGlobalPayeeAlreadyExists: ", ": End ", 6);
            return true;
          }
        }
      }
    }
    FFSDebug.log("PaymentPayeeProcessor.checkGlobalPayeeAlreadyExists: ", ": End ", 6);
    return false;
  }
  
  public static final PayeeInfo[] searchGlobalPayees(String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("PaymentPayeeProcessor.searchGlobalPayees: ", ": Start ", 6);
    PayeeInfo[] arrayOfPayeeInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      arrayOfPayeeInfo = Payee.searchGlobalPayees(paramString1, paramString2, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Throwable localThrowable1)
      {
        localThrowable1.printStackTrace();
      }
      FFSDebug.log("PaymentPayeeProcessor.searchGlobalPayees: ", ": End ", 6);
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo getGlobalPayee(String paramString)
    throws FFSException
  {
    localPayeeInfo = null;
    PayeeRouteInfo localPayeeRouteInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    FFSConnection[] arrayOfFFSConnection = DBUtil.getConnections(2);
    localFFSConnectionHolder.conn = arrayOfFFSConnection[0];
    try
    {
      localPayeeInfo = Payee.getGlobalPayee(localFFSConnectionHolder, paramString);
      localPayeeRouteInfo = PayeeToRoute.getPayeeRouteById(paramString, localFFSConnectionHolder);
      if (localPayeeInfo != null)
      {
        localPayeeInfo.setPayeeRouteInfo(localPayeeRouteInfo);
      }
      else
      {
        localPayeeInfo = new PayeeInfo();
        localPayeeInfo.setStatusCode(26044);
        localPayeeInfo.setStatusMsg("Failed to retrieve Payee By ID ");
      }
      localFFSConnectionHolder.conn.commit();
      return localPayeeInfo;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
  
  public PayeeInfo updateGlobalPayee(PayeeInfo paramPayeeInfo)
    throws RemoteException, FFSException
  {
    String str1 = "updateGlobalPayee";
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    paramPayeeInfo = jdMethod_do(paramPayeeInfo);
    if (paramPayeeInfo.getStatusCode() != 0) {
      return paramPayeeInfo;
    }
    Object localObject1;
    Object localObject2;
    if (!a(paramPayeeInfo, true))
    {
      try
      {
        String str2 = Payee.getGlobalPayeeStatusByID(localFFSConnectionHolder, paramPayeeInfo);
        if ((str2.equals("NEW")) || (str2.equals("ACTIVE")))
        {
          Payee.updatePayee(localFFSConnectionHolder, paramPayeeInfo);
          localObject1 = new PayeeToRoute(paramPayeeInfo.getPayeeRouteInfo());
          ((PayeeToRoute)localObject1).updateOrInsert(localFFSConnectionHolder);
        }
        else
        {
          paramPayeeInfo.setStatusCode(26043);
        }
        if (this.cH >= 3)
        {
          localObject1 = BPWLocaleUtil.getMessage(1071, null, "BILLPAY_AUDITLOG_MESSAGE");
          localObject2 = new String[] { localObject1 };
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject2, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), "", paramPayeeInfo.getAgentId(), paramPayeeInfo.getAgentType(), localLocalizableString, paramPayeeInfo.TranID, 6303, 0, null, paramPayeeInfo.PayeeRouteInfo.CurrencyCode, null, paramPayeeInfo.Status, null, null, null, null, 0);
        }
        localFFSConnectionHolder.conn.commit();
        try
        {
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Throwable localThrowable1)
        {
          localThrowable1.printStackTrace();
        }
        if (this.cH < 3) {
          return paramPayeeInfo;
        }
      }
      catch (Throwable localThrowable2)
      {
        localFFSConnectionHolder.conn.rollback();
        throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
      }
      finally
      {
        try
        {
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Throwable localThrowable5)
        {
          localThrowable5.printStackTrace();
        }
      }
    }
    else
    {
      paramPayeeInfo.setStatusCode(26042);
      paramPayeeInfo.setStatusMsg("GLOBAL PAYEE already Exists");
      String str3 = BPWLocaleUtil.getMessage(1072, null, "BILLPAY_AUDITLOG_MESSAGE");
      localObject1 = new String[] { str3 };
      localObject2 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject1, "BILLPAY_AUDITLOG_MESSAGE");
      try
      {
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), "", paramPayeeInfo.getAgentId(), paramPayeeInfo.getAgentType(), (ILocalizable)localObject2, paramPayeeInfo.TranID, 6303, 0, null, paramPayeeInfo.PayeeRouteInfo.CurrencyCode, null, paramPayeeInfo.Status, null, null, null, null, 0);
        localFFSConnectionHolder.conn.commit();
        return paramPayeeInfo;
      }
      catch (BPWException localBPWException)
      {
        localFFSConnectionHolder.conn.rollback();
        throw new FFSException(localBPWException.getErrorCode(), localBPWException.getMessage());
      }
      catch (Throwable localThrowable4)
      {
        localFFSConnectionHolder.conn.rollback();
        throw new FFSException(localThrowable4, FFSDebug.stackTrace(localThrowable4));
      }
      finally
      {
        try
        {
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Throwable localThrowable6)
        {
          localThrowable6.printStackTrace();
        }
      }
    }
  }
  
  public CustomerPayeeInfo addCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException
  {
    FFSDebug.log("PaymentPayeeProcessor.addCustomerPayee start", 6);
    String str1 = null;
    int i = -1;
    String str2 = null;
    FFSConnection[] arrayOfFFSConnection = DBUtil.getConnections(2);
    FFSConnectionHolder localFFSConnectionHolder1 = new FFSConnectionHolder();
    localFFSConnectionHolder1.conn = arrayOfFFSConnection[0];
    FFSConnectionHolder localFFSConnectionHolder2 = new FFSConnectionHolder();
    localFFSConnectionHolder2.conn = arrayOfFFSConnection[1];
    paramCustomerPayeeInfo = a(paramCustomerPayeeInfo);
    if (paramCustomerPayeeInfo.getStatusCode() != 0) {
      return paramCustomerPayeeInfo;
    }
    paramCustomerPayeeInfo = a(paramCustomerPayeeInfo, localFFSConnectionHolder1);
    if (paramCustomerPayeeInfo.getStatusCode() != 0) {
      return paramCustomerPayeeInfo;
    }
    int j = paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo.RouteID;
    if (j == 0) {
      j = jdMethod_goto();
    }
    String str3 = paramCustomerPayeeInfo.CustomerID;
    String str4 = paramCustomerPayeeInfo.payeeInfo.getSubmittedBy();
    String str5 = paramCustomerPayeeInfo.payeeInfo.TranID;
    try
    {
      Object localObject2;
      if (Trans.checkDuplicateTIDAndSaveTID(paramCustomerPayeeInfo.payeeInfo.TranID))
      {
        if (this.cI >= 3)
        {
          int k = Integer.parseInt(str3);
          str6 = BPWLocaleUtil.getMessage(101, null, "BILLPAY_AUDITLOG_MESSAGE");
          localObject2 = new String[] { str6 };
          localObject3 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject2, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), str4, paramCustomerPayeeInfo.payeeInfo.getAgentId(), paramCustomerPayeeInfo.payeeInfo.getAgentType(), (ILocalizable)localObject3, str5, 4416, k, null, paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo.CurrencyCode, null, paramCustomerPayeeInfo.payeeInfo.Status, null, null, null, null, 0);
        }
        localFFSConnectionHolder1.conn.commit();
        localObject1 = paramCustomerPayeeInfo;
        return localObject1;
      }
      Object localObject1 = new PayeeInfo();
      str6 = null;
      Object localObject4;
      Object localObject5;
      if (!this.cL)
      {
        if (paramCustomerPayeeInfo.payeeInfo.PayeeID == null)
        {
          localObject2 = new Payee();
          ((Payee)localObject2).setRouteID(j);
          str1 = ((Payee)localObject2).addPayee(paramCustomerPayeeInfo.payeeInfo, localFFSConnectionHolder1, paramCustomerPayeeInfo.payeeInfo.TranID);
          localObject1 = Payee.findPayeeByID(str1, localFFSConnectionHolder1);
          paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo.RouteID = j;
          ((PayeeInfo)localObject1).setPayeeRouteInfo(paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo);
          ((PayeeInfo)localObject1).PayeeRouteInfo.PayeeID = ((PayeeInfo)localObject1).PayeeID;
          ((PayeeInfo)localObject1).PayeeRouteInfo.ExtdPayeeID = ((PayeeInfo)localObject1).ExtdPayeeID;
          localObject3 = new PayeeToRoute(((PayeeInfo)localObject1).PayeeRouteInfo);
          localObject4 = PayeeToRoute.getPayeeRouteById(str1, localFFSConnectionHolder1);
          if (((PayeeRouteInfo)localObject4).PayeeID == null) {
            ((PayeeToRoute)localObject3).storeToDB(localFFSConnectionHolder1);
          }
          if ((((PayeeInfo)localObject1).Status.equals("INACTIVE")) || (((PayeeInfo)localObject1).Status.equals("CLOSED"))) {
            throw new FFSException("PaymentPayeeProcessor:addCustomerPayee:Payee is not Active");
          }
        }
        else if (paramCustomerPayeeInfo.payeeInfo.PayeeID != null)
        {
          str1 = paramCustomerPayeeInfo.payeeInfo.PayeeID;
          if (!this.cM) {
            localObject1 = Payee.findPayeeByID(str1, localFFSConnectionHolder1);
          } else {
            localObject1 = Payee.findPayeeByExtendedID(str1, localFFSConnectionHolder1);
          }
          if (localObject1 == null) {
            throw new FFSException("addCustomerPayee:Invalid PayeeID");
          }
          ((PayeeInfo)localObject1).PayeeRouteInfo = PayeeToRoute.getPayeeRouteById(str1, localFFSConnectionHolder1);
          if ((((PayeeInfo)localObject1).Status.equals("INACTIVE")) || (((PayeeInfo)localObject1).Status.equals("CLOSED"))) {
            throw new FFSException("addCustomerPayee:Payee is not Active");
          }
        }
        else
        {
          throw new FFSException("addCustomerPayee: request not supported");
        }
        localObject2 = new CustPayee();
        str2 = paramCustomerPayeeInfo.PayAcct;
        localObject3 = null;
        if (paramCustomerPayeeInfo.NameOnAcct != null) {
          localObject3 = paramCustomerPayeeInfo.NameOnAcct;
        }
        i = ((CustPayee)localObject2).addCustPayee(str3, ((PayeeInfo)localObject1).PayeeID, str2, (String)localObject3, ((PayeeInfo)localObject1).PayeeRouteInfo.RouteID, localFFSConnectionHolder1, localFFSConnectionHolder2);
      }
      else
      {
        localObject2 = new CustPayee();
        str2 = paramCustomerPayeeInfo.PayAcct;
        if (paramCustomerPayeeInfo.payeeInfo.PayeeID == null)
        {
          localObject3 = new Payee();
          str1 = ((Payee)localObject3).matchGlobalPayee((PayeeInfo)localObject1, localFFSConnectionHolder1);
          if (str1 == null)
          {
            localObject4 = ((Payee)localObject3).matchPayees(paramCustomerPayeeInfo.payeeInfo, localFFSConnectionHolder1);
            if (localObject4.length > 0)
            {
              localObject5 = ((CustPayee)localObject2).matchCustPayees(str3, (String[])localObject4, str2, localFFSConnectionHolder1);
              if (localObject5 == null)
              {
                ((Payee)localObject3).setRouteID(j);
                str1 = ((Payee)localObject3).addPayeeNoMatch(paramCustomerPayeeInfo.payeeInfo, localFFSConnectionHolder1, paramCustomerPayeeInfo.payeeInfo.TranID);
              }
              else
              {
                str1 = ((CustomerPayeeInfo)localObject5).PayeeID;
              }
            }
            else
            {
              ((Payee)localObject3).setRouteID(j);
              str1 = ((Payee)localObject3).addPayeeNoMatch(paramCustomerPayeeInfo.payeeInfo, localFFSConnectionHolder1, paramCustomerPayeeInfo.payeeInfo.TranID);
            }
          }
          ((PayeeInfo)localObject1).PayeeRouteInfo = paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo;
          ((PayeeInfo)localObject1).PayeeRouteInfo.PayeeID = ((PayeeInfo)localObject1).PayeeID;
          ((PayeeInfo)localObject1).PayeeRouteInfo.ExtdPayeeID = ((PayeeInfo)localObject1).ExtdPayeeID;
          paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo.RouteID = j;
          localObject4 = new PayeeToRoute(((PayeeInfo)localObject1).PayeeRouteInfo);
          localObject5 = PayeeToRoute.getPayeeRouteById(str1, localFFSConnectionHolder1);
          if (((PayeeRouteInfo)localObject5).PayeeID == null) {
            ((PayeeToRoute)localObject4).storeToDB(localFFSConnectionHolder1);
          }
          if ((((PayeeInfo)localObject1).Status.equals("INACTIVE")) || (((PayeeInfo)localObject1).Status.equals("CLOSED"))) {
            throw new FFSException("addCustomerPayee:Payee is not Active");
          }
        }
        else if (paramCustomerPayeeInfo.payeeInfo.PayeeID != null)
        {
          str1 = paramCustomerPayeeInfo.payeeInfo.PayeeID;
          if (!this.cM) {
            localObject1 = Payee.findPayeeByID(str1, localFFSConnectionHolder1);
          } else {
            localObject1 = Payee.findPayeeByExtendedID(str1, localFFSConnectionHolder1);
          }
          if (localObject1 == null)
          {
            paramCustomerPayeeInfo.setStatusCode(26102);
            paramCustomerPayeeInfo.setStatusMsg("Invalid Payee Information ");
            localObject3 = paramCustomerPayeeInfo;
            return localObject3;
          }
          if ((((PayeeInfo)localObject1).Status.equals("INACTIVE")) || (((PayeeInfo)localObject1).Status.equals("CLOSED")))
          {
            paramCustomerPayeeInfo.setStatusCode(26102);
            paramCustomerPayeeInfo.setStatusMsg("Invalid Payee Information ");
            localObject3 = paramCustomerPayeeInfo;
            return localObject3;
          }
          localObject3 = new String[1];
          localObject3[0] = str1;
          localObject4 = ((CustPayee)localObject2).matchCustPayees(str3, (String[])localObject3, str2, localFFSConnectionHolder1);
          if (localObject4 == null)
          {
            localObject5 = new Payee();
            if (((PayeeInfo)localObject1).PayeeLevelType.equals("PERSONAL")) {
              str1 = ((Payee)localObject5).addPayeeNoMatch((PayeeInfo)localObject1, localFFSConnectionHolder1, paramCustomerPayeeInfo.payeeInfo.TranID);
            }
          }
          ((PayeeInfo)localObject1).PayeeRouteInfo = PayeeToRoute.getPayeeRouteById(str1, localFFSConnectionHolder1);
        }
        else
        {
          paramCustomerPayeeInfo.setStatusCode(26102);
          paramCustomerPayeeInfo.setStatusMsg("Invalid Payee Information ");
          localObject3 = paramCustomerPayeeInfo;
          return localObject3;
        }
        localObject3 = null;
        if (paramCustomerPayeeInfo.NameOnAcct != null) {
          localObject3 = paramCustomerPayeeInfo.NameOnAcct;
        }
        i = ((CustPayee)localObject2).addCustPayee(paramCustomerPayeeInfo.payeeInfo.getSubmittedBy(), ((PayeeInfo)localObject1).PayeeID, str2, (String)localObject3, ((PayeeInfo)localObject1).RouteID, localFFSConnectionHolder1, localFFSConnectionHolder2);
      }
      paramCustomerPayeeInfo.payeeInfo.PayeeID = ((PayeeInfo)localObject1).PayeeID;
      paramCustomerPayeeInfo.payeeInfo.ExtdPayeeID = ((PayeeInfo)localObject1).ExtdPayeeID;
      paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo = ((PayeeInfo)localObject1).getPayeeRouteInfo();
      paramCustomerPayeeInfo.PayeeListID = i;
      paramCustomerPayeeInfo.PayAcct = str2;
      paramCustomerPayeeInfo.payeeInfo.PayeeLevelType = ((PayeeInfo)localObject1).PayeeLevelType;
      if (this.cI >= 3)
      {
        int m = Integer.parseInt(str3);
        localObject3 = BPWLocaleUtil.getMessage(100, null, "BILLPAY_AUDITLOG_MESSAGE");
        localObject4 = new String[] { localObject3 };
        localObject5 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject4, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), str4, paramCustomerPayeeInfo.payeeInfo.getAgentId(), paramCustomerPayeeInfo.payeeInfo.getAgentType(), (ILocalizable)localObject5, str5, 4415, m, null, paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo.CurrencyCode, null, paramCustomerPayeeInfo.payeeInfo.Status, null, null, null, null, 0);
      }
      localFFSConnectionHolder1.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder1.conn.rollback();
      String str6 = localException.toString();
      String str7 = "*** PaymentPayeeProcessor.addCustomerpayee";
      FFSDebug.log(localException, str7, 0);
      paramCustomerPayeeInfo.setStatusCode(26102);
      paramCustomerPayeeInfo.setStatusMsg("Invalid Payee Information ");
      Object localObject3 = paramCustomerPayeeInfo;
      return localObject3;
    }
    finally
    {
      localFFSConnectionHolder2.conn.rollback();
      DBUtil.freeConnection(localFFSConnectionHolder1.conn);
      DBUtil.freeConnection(localFFSConnectionHolder2.conn);
    }
    FFSDebug.log("PaymentPayeeProcessor.addCustomerpayee end, uid=" + str3 + ", payeeListID=" + i, 6);
    return paramCustomerPayeeInfo;
  }
  
  public CustomerPayeeInfo deleteCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException
  {
    FFSDebug.log("Start PaymentPayeeProcessor.deleteCustomerPayee start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    paramCustomerPayeeInfo = a(paramCustomerPayeeInfo);
    if (paramCustomerPayeeInfo.getStatusCode() != 0) {
      return paramCustomerPayeeInfo;
    }
    paramCustomerPayeeInfo = a(paramCustomerPayeeInfo, localFFSConnectionHolder);
    if (paramCustomerPayeeInfo.getStatusCode() != 0) {
      return paramCustomerPayeeInfo;
    }
    String str1 = null;
    String str2 = paramCustomerPayeeInfo.CustomerID;
    String str3 = paramCustomerPayeeInfo.payeeInfo.getSubmittedBy();
    Object localObject1 = null;
    String str4 = paramCustomerPayeeInfo.payeeInfo.TranID;
    try
    {
      if (Trans.checkDuplicateTIDAndSaveTID(paramCustomerPayeeInfo.payeeInfo.TranID))
      {
        if (this.cI >= 3)
        {
          int i = Integer.parseInt(str2);
          String str5 = BPWLocaleUtil.getMessage(105, null, "BILLPAY_AUDITLOG_MESSAGE");
          localObject3 = new String[] { str5 };
          localObject4 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject3, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), str3, paramCustomerPayeeInfo.payeeInfo.getAgentId(), paramCustomerPayeeInfo.payeeInfo.getAgentType(), (ILocalizable)localObject4, str4, 4416, i, null, paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo.CurrencyCode, null, paramCustomerPayeeInfo.payeeInfo.Status, null, null, null, null, 0);
        }
        localFFSConnectionHolder.conn.commit();
        CustomerPayeeInfo localCustomerPayeeInfo = paramCustomerPayeeInfo;
        return localCustomerPayeeInfo;
      }
      int j;
      try
      {
        j = paramCustomerPayeeInfo.PayeeListID;
      }
      catch (Exception localException2)
      {
        paramCustomerPayeeInfo.setStatusCode(26102);
        paramCustomerPayeeInfo.setStatusMsg("Invalid Payee Information ");
        localObject3 = paramCustomerPayeeInfo;
        return localObject3;
      }
      localObject2 = new CustPayee();
      ((CustPayee)localObject2).findCustPayeeByPayeeListID(str2, j, localFFSConnectionHolder);
      localObject3 = ((CustPayee)localObject2).getStatus();
      if (localObject3 == null)
      {
        paramCustomerPayeeInfo.setStatusCode(26102);
        paramCustomerPayeeInfo.setStatusMsg("Invalid Payee Information ");
        localObject4 = paramCustomerPayeeInfo;
        return localObject4;
      }
      if (((String)localObject3).equals("CLOSED"))
      {
        paramCustomerPayeeInfo.setStatusCode(26102);
        paramCustomerPayeeInfo.setStatusMsg("Invalid Payee Information ");
        localObject4 = paramCustomerPayeeInfo;
        return localObject4;
      }
      localObject4 = CustPayeeRoute.getActiveCustPayeeRoute(str2, j, localFFSConnectionHolder);
      if (localObject4 == null)
      {
        paramCustomerPayeeInfo.setStatusCode(26102);
        paramCustomerPayeeInfo.setStatusMsg("Invalid Payee Information ");
        localObject5 = paramCustomerPayeeInfo;
        return localObject5;
      }
      if ((((CustPayeeRoute)localObject4).Status.equals("CLOSED")) || (((CustPayeeRoute)localObject4).Status.equals("CANC")) || (((CustPayeeRoute)localObject4).Status.equals("PENDING")) || (((CustPayeeRoute)localObject4).Status.equals("CANC_INPROCESS")))
      {
        FFSDebug.log("CustPayee had been Closed, Can't delete ! ");
        paramCustomerPayeeInfo.setStatusCode(26102);
        paramCustomerPayeeInfo.setStatusMsg("Invalid Payee Information ");
        localObject5 = paramCustomerPayeeInfo;
        return localObject5;
      }
      Object localObject5 = null;
      if (((CustPayeeRoute)localObject4).Status.equals("NEW"))
      {
        if (!PmtInstruction.hasPendingPmt(str2, j, localFFSConnectionHolder)) {
          localObject5 = "CLOSED";
        } else {
          localObject5 = "PENDING";
        }
      }
      else if ((((CustPayeeRoute)localObject4).Status.equals("FAILEDON")) || (((CustPayeeRoute)localObject4).Status.equals("ERROR"))) {
        localObject5 = "CLOSED";
      } else if (!PmtInstruction.hasPendingPmt(str2, j, localFFSConnectionHolder)) {
        localObject5 = "CANC";
      } else {
        localObject5 = "PENDING";
      }
      str1 = ((CustPayee)localObject2).getPayeeID();
      PayeeInfo localPayeeInfo = Payee.findPayeeByID(str1, localFFSConnectionHolder);
      localPayeeInfo.setPayeeRouteInfo(paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo);
      CustPayeeRoute.updateCustPayeeRouteStatus(str2, j, paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo.RouteID, (String)localObject5, localFFSConnectionHolder);
      if (((String)localObject5).equals("CLOSED")) {
        CustPayee.updateStatus(str2, j, (String)localObject5, localFFSConnectionHolder);
      }
      if ((this.cL) && (localPayeeInfo.PayeeLevelType.equals("PERSONAL"))) {
        Payee.updateStatus(str1, "CLOSED", localFFSConnectionHolder);
      }
      if (this.cI >= 3)
      {
        int k = Integer.parseInt(str2);
        String str6 = BPWLocaleUtil.getMessage(104, null, "BILLPAY_AUDITLOG_MESSAGE");
        String[] arrayOfString = { str6 };
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), str3, paramCustomerPayeeInfo.payeeInfo.getAgentId(), paramCustomerPayeeInfo.payeeInfo.getAgentType(), localLocalizableString, str4, 4419, k, null, paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo.CurrencyCode, null, paramCustomerPayeeInfo.payeeInfo.Status, null, null, null, null, 0);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      localFFSConnectionHolder.conn.rollback();
      paramCustomerPayeeInfo.setStatusCode(26102);
      paramCustomerPayeeInfo.setStatusMsg("Invalid Payee Information ");
      localObject2 = paramCustomerPayeeInfo;
      return localObject2;
    }
    catch (Exception localException1)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject2 = localException1.toString();
      Object localObject3 = "*** PaymentPayeeProcessor.deleteCustomerPayee failed:";
      FFSDebug.log(localException1, (String)localObject3);
      paramCustomerPayeeInfo.setStatusCode(26102);
      paramCustomerPayeeInfo.setStatusMsg("Invalid Payee Information ");
      Object localObject4 = paramCustomerPayeeInfo;
      return localObject4;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("PaymentPayeeProcessor.deleteCustomerPayee end, uid=" + str2, 6);
    return paramCustomerPayeeInfo;
  }
  
  public CustomerPayeeInfo updateCustomerPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException
  {
    FFSDebug.log("PayeePaymentProcessor.updateCustomerPayee start", 6);
    FFSConnection[] arrayOfFFSConnection = DBUtil.getConnections(2);
    FFSConnectionHolder localFFSConnectionHolder1 = new FFSConnectionHolder();
    localFFSConnectionHolder1.conn = arrayOfFFSConnection[0];
    FFSConnectionHolder localFFSConnectionHolder2 = new FFSConnectionHolder();
    localFFSConnectionHolder2.conn = arrayOfFFSConnection[1];
    paramCustomerPayeeInfo = a(paramCustomerPayeeInfo);
    if (paramCustomerPayeeInfo.getStatusCode() != 0) {
      return paramCustomerPayeeInfo;
    }
    paramCustomerPayeeInfo = a(paramCustomerPayeeInfo, localFFSConnectionHolder1);
    if (paramCustomerPayeeInfo.getStatusCode() != 0) {
      return paramCustomerPayeeInfo;
    }
    boolean bool = true;
    Object localObject1 = null;
    String str1 = paramCustomerPayeeInfo.CustomerID;
    String str2 = paramCustomerPayeeInfo.payeeInfo.getSubmittedBy();
    Object localObject2 = null;
    String str3 = paramCustomerPayeeInfo.payeeInfo.TranID;
    try
    {
      if (Trans.checkDuplicateTIDAndSaveTID(paramCustomerPayeeInfo.payeeInfo.TranID))
      {
        if (this.cI >= 3)
        {
          int i = Integer.parseInt(str1);
          localObject4 = BPWLocaleUtil.getMessage(103, null, "BILLPAY_AUDITLOG_MESSAGE");
          String[] arrayOfString1 = { localObject4 };
          LocalizableString localLocalizableString1 = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString1, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), str2, paramCustomerPayeeInfo.payeeInfo.getAgentId(), paramCustomerPayeeInfo.payeeInfo.getAgentType(), localLocalizableString1, str3, 4416, i, null, paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo.CurrencyCode, null, paramCustomerPayeeInfo.payeeInfo.Status, null, null, null, null, 0);
        }
        localFFSConnectionHolder1.conn.commit();
        localObject3 = paramCustomerPayeeInfo;
        return localObject3;
      }
      Object localObject3 = paramCustomerPayeeInfo.PayAcct;
      localObject4 = null;
      if (paramCustomerPayeeInfo.NameOnAcct != null) {
        localObject4 = paramCustomerPayeeInfo.NameOnAcct;
      }
      int j;
      try
      {
        j = paramCustomerPayeeInfo.PayeeListID;
      }
      catch (Exception localException2)
      {
        paramCustomerPayeeInfo.setStatusCode(26102);
        paramCustomerPayeeInfo.setStatusMsg("Invalid Payee Information ");
        CustomerPayeeInfo localCustomerPayeeInfo2 = paramCustomerPayeeInfo;
        return localCustomerPayeeInfo2;
      }
      localCustomerPayeeInfo1 = new CustomerPayeeInfo(str1, "", j, (String)localObject3, (String)localObject4, "", 0, "", "", -1, -1);
      localCustomerPayeeInfo1 = modPayee(localFFSConnectionHolder1, localFFSConnectionHolder2, bool, paramCustomerPayeeInfo, paramCustomerPayeeInfo.payeeInfo, paramCustomerPayeeInfo.payeeInfo.TranID);
      paramCustomerPayeeInfo.setPayeeInfo(localCustomerPayeeInfo1.payeeInfo);
      if (localCustomerPayeeInfo1.PayeeListID != 0) {
        paramCustomerPayeeInfo.PayeeListID = localCustomerPayeeInfo1.PayeeListID;
      }
      paramCustomerPayeeInfo.PayAcct = localCustomerPayeeInfo1.PayAcct;
      if (this.cI >= 3)
      {
        int k = Integer.parseInt(str1);
        String str5 = BPWLocaleUtil.getMessage(102, null, "BILLPAY_AUDITLOG_MESSAGE");
        String[] arrayOfString2 = { str5 };
        LocalizableString localLocalizableString2 = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString2, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), str2, paramCustomerPayeeInfo.payeeInfo.getAgentId(), paramCustomerPayeeInfo.payeeInfo.getAgentType(), localLocalizableString2, str3, 4416, k, null, paramCustomerPayeeInfo.payeeInfo.PayeeRouteInfo.CurrencyCode, null, paramCustomerPayeeInfo.payeeInfo.Status, null, null, null, null, 0);
      }
      localFFSConnectionHolder1.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      localFFSConnectionHolder1.conn.rollback();
      paramCustomerPayeeInfo.setStatusCode(26102);
      paramCustomerPayeeInfo.setStatusMsg("Invalid Payee Information ");
      localObject4 = paramCustomerPayeeInfo;
      return localObject4;
    }
    catch (Exception localException1)
    {
      localFFSConnectionHolder1.conn.rollback();
      Object localObject4 = localException1.toString();
      String str4 = "*** PaymentPayeeProcessor.updateCustomerPayee failed:";
      FFSDebug.log(localException1, str4);
      paramCustomerPayeeInfo.setStatusCode(26102);
      paramCustomerPayeeInfo.setStatusMsg("Invalid Payee Information ");
      CustomerPayeeInfo localCustomerPayeeInfo1 = paramCustomerPayeeInfo;
      return localCustomerPayeeInfo1;
    }
    finally
    {
      localFFSConnectionHolder2.conn.rollback();
      DBUtil.freeConnection(localFFSConnectionHolder1.conn);
      DBUtil.freeConnection(localFFSConnectionHolder2.conn);
    }
    FFSDebug.log("PaymentPayeeProcessor.updateCustomerPayee end, uid=" + str1, 6);
    return paramCustomerPayeeInfo;
  }
  
  public CustomerPayeeInfo[] getCustomerPayees(CustomerPayeeInfo paramCustomerPayeeInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    CustomerPayeeInfo[] arrayOfCustomerPayeeInfo1 = null;
    CustomerPayeeInfo[] arrayOfCustomerPayeeInfo2 = null;
    CustomerPayeeInfo[] arrayOfCustomerPayeeInfo3 = new CustomerPayeeInfo[1];
    PayeeInfo localPayeeInfo = null;
    PayeeRouteInfo localPayeeRouteInfo = null;
    int i = 0;
    String str1 = null;
    localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    if (paramCustomerPayeeInfo == null)
    {
      paramCustomerPayeeInfo = new CustomerPayeeInfo();
      paramCustomerPayeeInfo.setStatusCode(16000);
      str1 = BPWLocaleUtil.getMessage(26102, new String[] { "CustomerPayeeInfo" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramCustomerPayeeInfo.setStatusMsg(str1);
      FFSDebug.log("PaymentPayeeProcessor:getCustomerPayees", str1, 0);
      arrayOfCustomerPayeeInfo3[0] = paramCustomerPayeeInfo;
      return arrayOfCustomerPayeeInfo3;
    }
    if (paramCustomerPayeeInfo.CustomerID == null)
    {
      paramCustomerPayeeInfo.setStatusCode(16010);
      str1 = BPWLocaleUtil.getMessage(26102, new String[] { "CustomerPayeeInfo", "CustomerID" }, "BILLPAY_AUDITLOG_MESSAGE");
      paramCustomerPayeeInfo.setStatusMsg(str1);
      FFSDebug.log("PaymentPayeeProcessor:getCustomerPayees", str1, 0);
      arrayOfCustomerPayeeInfo3[0] = paramCustomerPayeeInfo;
      return arrayOfCustomerPayeeInfo3;
    }
    paramCustomerPayeeInfo = a(paramCustomerPayeeInfo, localFFSConnectionHolder);
    if (paramCustomerPayeeInfo.getStatusCode() != 0)
    {
      arrayOfCustomerPayeeInfo3[0] = paramCustomerPayeeInfo;
      return arrayOfCustomerPayeeInfo3;
    }
    try
    {
      String str2 = paramCustomerPayeeInfo.CustomerID;
      arrayOfCustomerPayeeInfo1 = CustPayee.getCustPayeeByUID(str2, localFFSConnectionHolder);
      int j = arrayOfCustomerPayeeInfo1.length;
      ArrayList localArrayList = new ArrayList();
      for (int k = 0; k < j; k++)
      {
        localPayeeInfo = new PayeeInfo();
        localPayeeRouteInfo = new PayeeRouteInfo();
        CustPayeeRoute localCustPayeeRoute = CustPayeeRoute.getActiveCustPayeeRoute(str2, arrayOfCustomerPayeeInfo1[k].PayeeListID, localFFSConnectionHolder);
        if ((localCustPayeeRoute != null) && (!localCustPayeeRoute.Status.equals("PENDING")) && (!localCustPayeeRoute.Status.equals("CANC")) && (!localCustPayeeRoute.Status.equals("CANC_INPROCESS")) && (!localCustPayeeRoute.Status.equals("CLOSED")))
        {
          localPayeeInfo = Payee.findPayeeByID(arrayOfCustomerPayeeInfo1[k].PayeeID, localFFSConnectionHolder);
          if (localPayeeInfo.PayeeType == 0) {
            localPayeeInfo = Payee.getGlobalPayee(localFFSConnectionHolder, arrayOfCustomerPayeeInfo1[k].PayeeID);
          }
          localPayeeRouteInfo = PayeeToRoute.getPayeeRouteById(arrayOfCustomerPayeeInfo1[k].PayeeID, localFFSConnectionHolder);
          arrayOfCustomerPayeeInfo1[k].payeeInfo = localPayeeInfo;
          arrayOfCustomerPayeeInfo1[k].payeeInfo.PayeeRouteInfo = localPayeeRouteInfo;
          localArrayList.add(arrayOfCustomerPayeeInfo1[k]);
        }
      }
      i = localArrayList.size();
      arrayOfCustomerPayeeInfo2 = (CustomerPayeeInfo[])localArrayList.toArray(new CustomerPayeeInfo[i]);
    }
    catch (Exception localException)
    {
      String str3 = "*** PaymentPayeeProcessor.getCustomerPayees failed:";
      FFSDebug.log(localException, str3);
      throw new FFSException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfCustomerPayeeInfo2;
  }
  
  private int jdMethod_goto()
    throws BPWException
  {
    FulfillAgent localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
    if (localFulfillAgent == null) {
      throw new BPWException("No Fulfillment system.");
    }
    return localFulfillAgent.getDefaultFulfillment();
  }
  
  public CustomerPayeeInfo modPayee(FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2, boolean paramBoolean, CustomerPayeeInfo paramCustomerPayeeInfo, PayeeInfo paramPayeeInfo, String paramString)
    throws Exception
  {
    String str1 = paramCustomerPayeeInfo.CustomerID;
    int i = paramCustomerPayeeInfo.PayeeListID;
    String str2 = paramCustomerPayeeInfo.PayAcct;
    String str3 = paramCustomerPayeeInfo.NameOnAcct;
    PayeeRouteInfo localPayeeRouteInfo = new PayeeRouteInfo();
    CustPayee localCustPayee = new CustPayee();
    localCustPayee.findCustPayeeByPayeeListID(str1, i, paramFFSConnectionHolder1);
    Object localObject1 = null;
    int j = 0;
    localObject1 = localCustPayee.getPayeeID();
    if (localObject1 == null)
    {
      FFSDebug.log("===PaymentPayeeProcessor:modpayee  : payeeID = " + (String)localObject1, 0);
      throw new FFSException("Invalid PayeeListID");
    }
    PayeeInfo localPayeeInfo = Payee.findPayeeByID((String)localObject1, paramFFSConnectionHolder1);
    if ((localPayeeInfo.Status.equals("INACTIVE")) || (localPayeeInfo.Status.equals("CLOSED"))) {
      throw new FFSException("Payee is not Active");
    }
    String str4 = localCustPayee.getStatus();
    if (str4 == null) {
      throw new FFSException("Invalid PayeeListID");
    }
    if (str4.equals("CLOSED")) {
      throw new FFSException("Payee can Not be modified");
    }
    CustPayeeRoute localCustPayeeRoute = CustPayeeRoute.getActiveCustPayeeRoute(str1, i, paramFFSConnectionHolder1);
    if (localCustPayeeRoute == null) {
      throw new FFSException("Payee can Not be modified");
    }
    if ((localCustPayeeRoute.Status.equals("CLOSED")) || (localCustPayeeRoute.Status.equals("CANC")) || (localCustPayeeRoute.Status.equals("PENDING")) || (localCustPayeeRoute.Status.equals("CANC_INPROCESS")))
    {
      FFSDebug.log("CustPayee had been Closed, Can't modify ! ");
      throw new FFSException("Payee can Not be modified");
    }
    int k = 0;
    String str5 = localCustPayee.getPayAcct();
    if (str5 == null)
    {
      if ((str2 == null) || (str2.equals(""))) {
        k = 0;
      } else {
        k = 1;
      }
    }
    else if (str5.equals(str2)) {
      k = 0;
    } else {
      k = 1;
    }
    int m = 0;
    String str6 = localCustPayee.getNameOnAcct();
    if (str6 == null)
    {
      if ((str3 == null) || (str3.equals(""))) {
        m = 0;
      } else {
        m = 1;
      }
    }
    else if (str6.equals(str3)) {
      m = 0;
    } else {
      m = 1;
    }
    if (paramPayeeInfo.PayeeRouteInfo.RouteID == 0) {
      j = jdMethod_goto();
    } else {
      j = paramPayeeInfo.PayeeRouteInfo.RouteID;
    }
    String str7 = null;
    if (paramBoolean)
    {
      if (paramPayeeInfo.PayeeLevelType.equals("PERSONAL"))
      {
        localObject2 = new Payee();
        PayeeToRoute localPayeeToRoute;
        if (!this.cL)
        {
          ((Payee)localObject2).setRouteID(j);
          ((Payee)localObject2).addPayee(paramPayeeInfo, paramFFSConnectionHolder1, paramString);
          str7 = ((Payee)localObject2).getPayeeID();
          paramPayeeInfo.PayeeID = str7;
          paramPayeeInfo.ExtdPayeeID = ((Payee)localObject2).getExtdPayeeID();
          paramPayeeInfo.PayeeRouteInfo.PayeeID = str7;
          paramPayeeInfo.PayeeRouteInfo.ExtdPayeeID = ((Payee)localObject2).getExtdPayeeID();
          paramPayeeInfo.PayeeRouteInfo.RouteID = j;
          localPayeeToRoute = new PayeeToRoute(paramPayeeInfo.PayeeRouteInfo);
          localPayeeToRoute.updateOrInsert(paramFFSConnectionHolder1);
        }
        else
        {
          str7 = ((Payee)localObject2).matchGlobalPayee(paramPayeeInfo, paramFFSConnectionHolder1);
          if (str7 == null)
          {
            ((Payee)localObject2).setRouteID(j);
            ((Payee)localObject2).addPayeeNoMatch(paramPayeeInfo, paramFFSConnectionHolder1, paramString);
            str7 = ((Payee)localObject2).getPayeeID();
            paramPayeeInfo.PayeeID = str7;
            paramPayeeInfo.ExtdPayeeID = ((Payee)localObject2).getExtdPayeeID();
            paramPayeeInfo.PayeeRouteInfo.PayeeID = str7;
            paramPayeeInfo.PayeeRouteInfo.ExtdPayeeID = ((Payee)localObject2).getExtdPayeeID();
            paramPayeeInfo.PayeeRouteInfo.RouteID = j;
            localPayeeToRoute = new PayeeToRoute(paramPayeeInfo.PayeeRouteInfo);
            localPayeeToRoute.updateOrInsert(paramFFSConnectionHolder1);
          }
        }
      }
      else
      {
        str7 = paramPayeeInfo.PayeeID;
      }
      if (((String)localObject1).equals(str7))
      {
        if ((k != 0) || (m != 0))
        {
          localCustPayee.modCustPayee(str2, str3, str5, (String)localObject1, "MODACCT", paramFFSConnectionHolder1);
          if ((localCustPayeeRoute.Status.equals("FAILEDON")) || (localCustPayeeRoute.Status.equals("ERROR"))) {
            throw new FFSException("Payee Can Not be Modified");
          }
          if (!localCustPayeeRoute.Status.equals("NEW")) {
            CustPayeeRoute.updateCustPayeeRouteStatus(str1, i, j, "MODACCT", paramFFSConnectionHolder1);
          }
          jdMethod_if(str1, i, str2, str3, paramFFSConnectionHolder1);
        }
      }
      else
      {
        if ((localPayeeInfo.PayeeType == 0) || (localPayeeInfo.PayeeType == 1) || (localPayeeInfo.PayeeType == 2)) {
          throw new FFSException("Payee Can Not be Modified");
        }
        if ((localCustPayeeRoute.Status.equals("NEW")) || (localCustPayeeRoute.Status.equals("FAILEDON")) || (localCustPayeeRoute.Status.equals("ERROR")))
        {
          CustPayeeRoute.updateCustPayeeRouteStatus(str1, i, j, "CLOSED", paramFFSConnectionHolder1);
          CustPayee.updateStatus(str1, i, "CLOSED", paramFFSConnectionHolder1);
        }
        else
        {
          CustPayeeRoute.updateCustPayeeRouteStatus(str1, i, j, "CANC", paramFFSConnectionHolder1);
        }
        localObject2 = new CustPayee();
        paramCustomerPayeeInfo.PayeeListID = ((CustPayee)localObject2).addCustPayee(str1, str7, str2, str3, j, paramFFSConnectionHolder1, paramFFSConnectionHolder2);
        a(str1, i, paramCustomerPayeeInfo.PayeeListID, str2, str3, str7, paramFFSConnectionHolder1);
        localObject1 = str7;
      }
    }
    else
    {
      if ((k != 0) || (m != 0))
      {
        localCustPayee.modCustPayee(str2, str3, str5, (String)localObject1, "MODACCT", paramFFSConnectionHolder1);
        if ((localCustPayeeRoute.Status.equals("FAILEDON")) || (localCustPayeeRoute.Status.equals("ERROR"))) {
          throw new FFSException("Payee Can not be modified ");
        }
        if (!localCustPayeeRoute.Status.equals("NEW")) {
          CustPayeeRoute.updateCustPayeeRouteStatus(str1, i, j, "MODACCT", paramFFSConnectionHolder1);
        }
        jdMethod_if(str1, i, str2, str3, paramFFSConnectionHolder1);
      }
      throw new FFSException("Invalid Operation");
    }
    Object localObject2 = Payee.findPayeeByID((String)localObject1, paramFFSConnectionHolder1);
    ((PayeeInfo)localObject2).setPayeeRouteInfo(paramPayeeInfo.PayeeRouteInfo);
    paramCustomerPayeeInfo.setPayeeInfo((PayeeInfo)localObject2);
    paramCustomerPayeeInfo.payeeInfo.NickName = paramPayeeInfo.NickName;
    paramCustomerPayeeInfo.PayAcct = str2;
    return paramCustomerPayeeInfo;
  }
  
  private void jdMethod_if(String paramString1, int paramInt, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    try
    {
      AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT SrvrTID FROM BPW_PmtInstruction WHERE CustomerID=? AND PayeeListID=? AND Status=?", new Object[] { paramString1, new Integer(paramInt), "WILLPROCESSON" });
      String str;
      while (localFFSResultSet.getNextRow())
      {
        str = localFFSResultSet.getColumnString(1);
        DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_PmtInstruction SET PayAcct=? WHERE SrvrTID=?", new Object[] { paramString2, str });
      }
      localFFSResultSet.close();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT RecSrvrTID FROM BPW_RecPmtInstruction WHERE CustomerID=? AND PayeeListID=? AND Status=?", new Object[] { paramString1, new Integer(paramInt), "WILLPROCESSON" });
      while (localFFSResultSet.getNextRow())
      {
        str = localFFSResultSet.getColumnString(1);
        DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_RecPmtInstruction SET PayAcct=? WHERE RecSrvrTID=?", new Object[] { paramString2, str });
      }
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException) {}
    }
  }
  
  private void a(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, String paramString4, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    try
    {
      AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
      PayeeInfo localPayeeInfo = Payee.findPayeeByID(paramString4, paramFFSConnectionHolder);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT SrvrTID FROM BPW_PmtInstruction WHERE CustomerID=? AND PayeeListID=? AND Status IN (?,?,?,?,?)", new Object[] { paramString1, new Integer(paramInt1), "WILLPROCESSON", "FUNDSALLOCATED", "NOFUNDSON", "INFUNDSALLOC", "FUNDSALLOCACTIVE" });
      String str;
      while (localFFSResultSet.getNextRow())
      {
        str = localFFSResultSet.getColumnString(1);
        DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_PmtInstruction SET PayAcct=?,PayeeListID=?,PayeeID=? WHERE SrvrTID=?", new Object[] { paramString2, new Integer(paramInt2), paramString4, str });
      }
      localFFSResultSet.close();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT RecSrvrTID FROM BPW_RecPmtInstruction WHERE CustomerID=? AND PayeeListID=? AND Status IN (?,?,?,?,?)", new Object[] { paramString1, new Integer(paramInt1), "WILLPROCESSON", "FUNDSALLOCATED", "NOFUNDSON", "INFUNDSALLOC", "FUNDSALLOCACTIVE" });
      while (localFFSResultSet.getNextRow())
      {
        str = localFFSResultSet.getColumnString(1);
        DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_RecPmtInstruction SET PayAcct=?,PayeeListID=?,PayeeID=? WHERE RecSrvrTID=?", new Object[] { paramString2, new Integer(paramInt2), paramString4, str });
      }
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException) {}
    }
  }
  
  public PayeeInfo deleteGlobalPayee(PayeeInfo paramPayeeInfo)
    throws FFSException
  {
    FFSDebug.log("PaymentPayeeProcessor.deleteGlobalPayee: ", ": Start ", 6);
    String str1 = null;
    Payee localPayee = new Payee(paramPayeeInfo);
    FFSConnectionHolder localFFSConnectionHolder;
    Object localObject1;
    Object localObject2;
    if (!jdMethod_if(paramPayeeInfo))
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      try
      {
        localPayee.removeFromDB(localFFSConnectionHolder);
        str1 = localPayee.getPayeeID();
        paramPayeeInfo.PayeeRouteInfo.PayeeID = str1;
        PayeeToRoute localPayeeToRoute = new PayeeToRoute(paramPayeeInfo.PayeeRouteInfo);
        localPayeeToRoute.removeFromDB(localFFSConnectionHolder);
        if (this.cH >= 3)
        {
          localObject1 = BPWLocaleUtil.getMessage(1073, null, "BILLPAY_AUDITLOG_MESSAGE");
          localObject2 = new String[] { localObject1 };
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject2, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), "", paramPayeeInfo.getAgentId(), paramPayeeInfo.getAgentType(), localLocalizableString, paramPayeeInfo.TranID, 6304, 0, null, paramPayeeInfo.PayeeRouteInfo.CurrencyCode, null, paramPayeeInfo.Status, null, null, null, null, 0);
        }
        localFFSConnectionHolder.conn.commit();
        try
        {
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Throwable localThrowable1)
        {
          localThrowable1.printStackTrace();
        }
        if (this.cH < 3) {
          break label544;
        }
      }
      catch (BPWException localBPWException1)
      {
        localFFSConnectionHolder.conn.rollback();
        throw new FFSException(localBPWException1.getErrorCode(), localBPWException1.getMessage());
      }
      catch (Throwable localThrowable2)
      {
        localFFSConnectionHolder.conn.rollback();
        throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
      }
      finally
      {
        try
        {
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Throwable localThrowable5)
        {
          localThrowable5.printStackTrace();
        }
      }
    }
    else
    {
      if (this.cJ == true)
      {
        paramPayeeInfo.setStatusCode(26046);
        paramPayeeInfo.setStatusMsg("GLOBAL PAYEE has a pending payment");
      }
      else if (this.cK == true)
      {
        paramPayeeInfo.setStatusCode(26045);
        paramPayeeInfo.setStatusMsg("GLOBAL PAYEE has a pending link");
      }
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      String str2 = BPWLocaleUtil.getMessage(1074, null, "BILLPAY_AUDITLOG_MESSAGE");
      localObject1 = new String[] { str2 };
      localObject2 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject1, "BILLPAY_AUDITLOG_MESSAGE");
      try
      {
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), "", paramPayeeInfo.getAgentId(), paramPayeeInfo.getAgentType(), (ILocalizable)localObject2, paramPayeeInfo.TranID, 6304, 0, null, paramPayeeInfo.PayeeRouteInfo.CurrencyCode, null, paramPayeeInfo.Status, null, null, null, null, 0);
        localFFSConnectionHolder.conn.commit();
        try
        {
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Throwable localThrowable3)
        {
          localThrowable3.printStackTrace();
        }
        FFSDebug.log("PaymentPayeeProcessor.deleteGlobalPayee: ", ": End ", 6);
      }
      catch (BPWException localBPWException2)
      {
        localFFSConnectionHolder.conn.rollback();
        throw new FFSException(localBPWException2.getErrorCode(), localBPWException2.getMessage());
      }
      catch (Throwable localThrowable4)
      {
        localFFSConnectionHolder.conn.rollback();
        throw new FFSException(localThrowable4, FFSDebug.stackTrace(localThrowable4));
      }
      finally
      {
        try
        {
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Throwable localThrowable6)
        {
          localThrowable6.printStackTrace();
        }
      }
    }
    label544:
    return paramPayeeInfo;
  }
  
  private boolean jdMethod_if(PayeeInfo paramPayeeInfo)
    throws BPWException, FFSException
  {
    FFSDebug.log("PaymentPayeeProcessor.hasPendingPayments: ", ": Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      this.cJ = PmtInstruction.hasPendingPmt(paramPayeeInfo.PayeeID, localFFSConnectionHolder);
      this.cK = Payee.hasPendingLink(paramPayeeInfo.PayeeID, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Throwable localThrowable1)
      {
        localThrowable1.printStackTrace();
      }
      if (this.cJ) {
        break label136;
      }
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
    if (this.cK)
    {
      label136:
      FFSDebug.log("PaymentPayeeProcessor.hasPendingPayments: ", ": End ", 6);
      return true;
    }
    FFSDebug.log("PaymentPayeeProcessor.hasPendingPayments: ", ": End ", 6);
    return false;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.PaymentPayeeProcessor
 * JD-Core Version:    0.7.0.1
 */