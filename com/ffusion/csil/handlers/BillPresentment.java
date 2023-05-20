package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.BillSummaries;
import com.ffusion.beans.billpresentment.BillSummary;
import com.ffusion.beans.billpresentment.Biller;
import com.ffusion.beans.billpresentment.Billers;
import com.ffusion.beans.billpresentment.Consumer;
import com.ffusion.beans.billpresentment.ConsumerStatus;
import com.ffusion.beans.billpresentment.Consumers;
import com.ffusion.beans.billpresentment.EBillAccount;
import com.ffusion.beans.billpresentment.EBillAccounts;
import com.ffusion.beans.billpresentment.PaymentInfo;
import com.ffusion.beans.billpresentment.Publisher;
import com.ffusion.beans.billpresentment.Publishers;
import com.ffusion.beans.billpresentment.TCURL;
import com.ffusion.csil.CSILException;
import com.ffusion.util.MultiException;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.Properties;

public class BillPresentment
  extends Initialize
{
  private static final String a68 = "BillPresentment";
  private static com.ffusion.services.BillPresentment a67 = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "BillPresentment", str, 20107);
    a67 = (com.ffusion.services.BillPresentment)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      Properties localProperties = (Properties)localHashMap.get("DB_PROPERTIES");
      a67.initialize(localProperties);
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
    return a67;
  }
  
  public static boolean signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.signOn";
    try
    {
      return a67.signOn(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void signOff(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a67.signOff(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throwing(jdMethod_int(localException), localException);
    }
  }
  
  public static boolean changePIN(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.changePIN";
    try
    {
      return a67.changePIN(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Publishers getPublishers(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.getPublishers";
    try
    {
      return a67.getPublishers(paramSecureUser, paramPublisher, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Consumer getConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.getConsumer";
    try
    {
      return a67.getConsumer(paramSecureUser, paramConsumer, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Consumers getConsumerList(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.getConsumerList";
    try
    {
      return a67.getConsumerList(paramSecureUser, paramPublisher, paramConsumer, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static int getEnrollmentStatus(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.getEnrollmentStatus";
    try
    {
      return a67.getEnrollmentStatus(paramSecureUser, paramPublisher, paramConsumer, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Consumer enrollConsumer(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.enrollConsumer";
    try
    {
      return a67.enrollConsumer(paramSecureUser, paramPublisher, paramConsumer, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Consumer modifyConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.modifyConsumer";
    try
    {
      return a67.modifyConsumer(paramSecureUser, paramConsumer, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a67.deleteConsumer(paramSecureUser, paramConsumer, paramHashMap);
    }
    catch (Exception localException)
    {
      throwing(jdMethod_int(localException), localException);
    }
  }
  
  public static ConsumerStatus getConsumerStatus(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.getConsumerStatus";
    try
    {
      return a67.getConsumerStatus(paramSecureUser, paramConsumer, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ConsumerStatus modifyConsumerStatus(SecureUser paramSecureUser, ConsumerStatus paramConsumerStatus, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.modifyConsumerStatus";
    try
    {
      return a67.modifyConsumerStatus(paramSecureUser, paramConsumerStatus, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static EBillAccounts getAccounts(SecureUser paramSecureUser, Biller paramBiller, Consumer paramConsumer, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.getAccounts";
    try
    {
      return a67.getAccounts(paramSecureUser, paramBiller, paramConsumer, paramEBillAccount, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static EBillAccount activateAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.activateAccount";
    try
    {
      return a67.activateAccount(paramSecureUser, paramEBillAccount, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deactivateAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a67.deactivateAccount(paramSecureUser, paramEBillAccount, paramHashMap);
    }
    catch (Exception localException)
    {
      throwing(jdMethod_int(localException), localException);
    }
  }
  
  public static EBillAccount modifyAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.modifyAccount";
    try
    {
      return a67.modifyAccount(paramSecureUser, paramEBillAccount, paramBoolean, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Billers getBillers(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.getBillers";
    try
    {
      return a67.getBillers(paramSecureUser, paramPublisher, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static BillSummaries getBillSummaries(SecureUser paramSecureUser, Consumer paramConsumer, BillSummary paramBillSummary, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.getBillSummaries";
    try
    {
      return a67.getBillSummaries(paramSecureUser, paramConsumer, paramBillSummary, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static BillSummary modifyBillSummary(SecureUser paramSecureUser, BillSummary paramBillSummary, PaymentInfo paramPaymentInfo, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.modifyBillSummary";
    try
    {
      return a67.modifyBillSummary(paramSecureUser, paramBillSummary, paramPaymentInfo, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static TCURL addTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.addTCURL";
    try
    {
      return a67.addTCURL(paramSecureUser, paramTCURL, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static TCURL getTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.getTCURL";
    try
    {
      return a67.getTCURL(paramSecureUser, paramTCURL, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static TCURL modifyTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.modifyTCURL";
    try
    {
      return a67.modifyTCURL(paramSecureUser, paramTCURL, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a67.deleteTCURL(paramSecureUser, paramTCURL, paramHashMap);
    }
    catch (Exception localException)
    {
      throwing(jdMethod_int(localException), localException);
    }
  }
  
  public static Biller modifyBiller(SecureUser paramSecureUser, Biller paramBiller, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.modifyBiller";
    try
    {
      return a67.modifyBiller(paramSecureUser, paramBiller, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Publisher addPublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.addPublisher";
    try
    {
      return a67.addPublisher(paramSecureUser, paramPublisher, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Publisher modifyPublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.modifyPublisher";
    try
    {
      return a67.modifyPublisher(paramSecureUser, paramPublisher, paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_int(localException), localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deletePublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a67.deletePublisher(paramSecureUser, paramPublisher, paramHashMap);
    }
    catch (Exception localException)
    {
      throwing(jdMethod_int(localException), localException);
    }
  }
  
  private static int jdMethod_int(Exception paramException)
  {
    int i = 1;
    if ((paramException instanceof MultiException)) {
      i = ((MultiException)paramException).getCode();
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.BillPresentment
 * JD-Core Version:    0.7.0.1
 */