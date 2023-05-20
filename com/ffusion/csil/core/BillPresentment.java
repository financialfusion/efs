package com.ffusion.csil.core;

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
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;

public class BillPresentment
  extends Initialize
{
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.BillPresentment.initialize");
    com.ffusion.csil.handlers.BillPresentment.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.BillPresentment.getService();
  }
  
  public static boolean signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.SignOn";
    long l = System.currentTimeMillis();
    boolean bool = com.ffusion.csil.handlers.BillPresentment.signOn(paramSecureUser, paramString1, paramString2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return bool;
  }
  
  public static void signOff(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.SignOff";
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.BillPresentment.signOff(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static boolean changePIN(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.ChangePIN";
    long l = System.currentTimeMillis();
    boolean bool = com.ffusion.csil.handlers.BillPresentment.changePIN(paramSecureUser, paramString1, paramString2, paramHashMap);
    audit(paramSecureUser, "Bill presentment PIN change", null, 2100);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return bool;
  }
  
  public static Publishers getPublishers(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.GetPublishers";
    long l = System.currentTimeMillis();
    Publishers localPublishers = com.ffusion.csil.handlers.BillPresentment.getPublishers(paramSecureUser, paramPublisher, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localPublishers;
  }
  
  public static Consumer getConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.GetConsumer";
    long l = System.currentTimeMillis();
    Consumer localConsumer = com.ffusion.csil.handlers.BillPresentment.getConsumer(paramSecureUser, paramConsumer, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localConsumer;
  }
  
  public static Consumers getConsumerList(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.GetConsumerList";
    long l = System.currentTimeMillis();
    Consumers localConsumers = com.ffusion.csil.handlers.BillPresentment.getConsumerList(paramSecureUser, paramPublisher, paramConsumer, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localConsumers;
  }
  
  public static int getEnrollmentStatus(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.GetEnrollmentStatus";
    long l = System.currentTimeMillis();
    int i = com.ffusion.csil.handlers.BillPresentment.getEnrollmentStatus(paramSecureUser, paramPublisher, paramConsumer, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return i;
  }
  
  public static Consumer enrollConsumer(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.EnrollConsumer";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    Consumer localConsumer = com.ffusion.csil.handlers.BillPresentment.enrollConsumer(paramSecureUser, paramPublisher, paramConsumer, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Enroll the consumer with ID ");
    localStringBuffer.append(paramConsumer.getConsumerID());
    localStringBuffer.append(" with the bill presentment publisher ID ");
    localStringBuffer.append(paramPublisher.getPublisherID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2101);
    debug(paramSecureUser, str1);
    return localConsumer;
  }
  
  public static Consumer modifyConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.ModifyConsumer";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    Consumer localConsumer = com.ffusion.csil.handlers.BillPresentment.modifyConsumer(paramSecureUser, paramConsumer, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Modify the bill presentment consumer with ID ");
    localStringBuffer.append(paramConsumer.getConsumerID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2102);
    debug(paramSecureUser, str1);
    return localConsumer;
  }
  
  public static void deleteConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.DeleteConsumer";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    com.ffusion.csil.handlers.BillPresentment.deleteConsumer(paramSecureUser, paramConsumer, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Delete the bill presentment consumer with ID ");
    localStringBuffer.append(paramConsumer.getConsumerID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2103);
    debug(paramSecureUser, str1);
  }
  
  public static ConsumerStatus getConsumerStatus(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.GetConsumerStatus";
    long l = System.currentTimeMillis();
    ConsumerStatus localConsumerStatus = com.ffusion.csil.handlers.BillPresentment.getConsumerStatus(paramSecureUser, paramConsumer, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localConsumerStatus;
  }
  
  public static ConsumerStatus modifyConsumerStatus(SecureUser paramSecureUser, ConsumerStatus paramConsumerStatus, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.ModifyConsumerStatus";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    ConsumerStatus localConsumerStatus = com.ffusion.csil.handlers.BillPresentment.modifyConsumerStatus(paramSecureUser, paramConsumerStatus, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Modify the bill presentment consumer with ID ");
    localStringBuffer.append(paramConsumerStatus.getID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2104);
    debug(paramSecureUser, str1);
    return localConsumerStatus;
  }
  
  public static EBillAccounts getAccounts(SecureUser paramSecureUser, Biller paramBiller, Consumer paramConsumer, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.GetAccounts";
    long l = System.currentTimeMillis();
    EBillAccounts localEBillAccounts = com.ffusion.csil.handlers.BillPresentment.getAccounts(paramSecureUser, paramBiller, paramConsumer, paramEBillAccount, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localEBillAccounts;
  }
  
  public static EBillAccount activateAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.ActivateAccount";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    EBillAccount localEBillAccount = com.ffusion.csil.handlers.BillPresentment.activateAccount(paramSecureUser, paramEBillAccount, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Activate the bill presentment consumer with ID ");
    localStringBuffer.append(paramEBillAccount.getAccountID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2105);
    debug(paramSecureUser, str1);
    return localEBillAccount;
  }
  
  public static void deactivateAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.DeactivateAccount";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    com.ffusion.csil.handlers.BillPresentment.deactivateAccount(paramSecureUser, paramEBillAccount, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Deactivate the bill presentment consumer with ID ");
    localStringBuffer.append(paramEBillAccount.getAccountID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2106);
    debug(paramSecureUser, str1);
  }
  
  public static EBillAccount modifyAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.ModifyAccount";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    EBillAccount localEBillAccount = com.ffusion.csil.handlers.BillPresentment.modifyAccount(paramSecureUser, paramEBillAccount, paramBoolean, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Modify the bill presentment account with ID ");
    localStringBuffer.append(paramEBillAccount.getAccountID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2107);
    debug(paramSecureUser, str1);
    return localEBillAccount;
  }
  
  public static Billers getBillers(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.GetBillers";
    long l = System.currentTimeMillis();
    Billers localBillers = com.ffusion.csil.handlers.BillPresentment.getBillers(paramSecureUser, paramPublisher, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localBillers;
  }
  
  public static BillSummaries getBillSummaries(SecureUser paramSecureUser, Consumer paramConsumer, BillSummary paramBillSummary, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.GetBillSummaries";
    long l = System.currentTimeMillis();
    BillSummaries localBillSummaries = com.ffusion.csil.handlers.BillPresentment.getBillSummaries(paramSecureUser, paramConsumer, paramBillSummary, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localBillSummaries;
  }
  
  public static BillSummary modifyBillSummary(SecureUser paramSecureUser, BillSummary paramBillSummary, PaymentInfo paramPaymentInfo, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.ModifyBillSummary";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    BillSummary localBillSummary = com.ffusion.csil.handlers.BillPresentment.modifyBillSummary(paramSecureUser, paramBillSummary, paramPaymentInfo, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Modify the bill summary with ID ");
    localStringBuffer.append(paramBillSummary.getBillSummaryID());
    localStringBuffer.append(" and payment ID ");
    localStringBuffer.append(paramPaymentInfo.getID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2108, paramPaymentInfo.getAmountPaidValue());
    debug(paramSecureUser, str1);
    return localBillSummary;
  }
  
  public static TCURL addTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.AddTCURL";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    TCURL localTCURL = com.ffusion.csil.handlers.BillPresentment.addTCURL(paramSecureUser, paramTCURL, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Add terms and conditions URL with ID ");
    localStringBuffer.append(paramTCURL.getID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2109);
    debug(paramSecureUser, str1);
    return localTCURL;
  }
  
  public static TCURL getTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BillPresentment.getTCURL";
    long l = System.currentTimeMillis();
    TCURL localTCURL = com.ffusion.csil.handlers.BillPresentment.getTCURL(paramSecureUser, paramTCURL, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localTCURL;
  }
  
  public static TCURL modifyTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.ModifyTCURL";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    TCURL localTCURL = com.ffusion.csil.handlers.BillPresentment.modifyTCURL(paramSecureUser, paramTCURL, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Modify terms and conditions URL with ID ");
    localStringBuffer.append(paramTCURL.getID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2110);
    debug(paramSecureUser, str1);
    return localTCURL;
  }
  
  public static void deleteTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.DeleteTCURL";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    com.ffusion.csil.handlers.BillPresentment.deleteTCURL(paramSecureUser, paramTCURL, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Delete terms and conditions URL with ID ");
    localStringBuffer.append(paramTCURL.getID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2111);
    debug(paramSecureUser, str1);
  }
  
  public static Biller modifyBiller(SecureUser paramSecureUser, Biller paramBiller, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.ModifyBiller";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    Biller localBiller = com.ffusion.csil.handlers.BillPresentment.modifyBiller(paramSecureUser, paramBiller, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Modify the biller with ID ");
    localStringBuffer.append(paramBiller.getBillerID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2112);
    debug(paramSecureUser, str1);
    return localBiller;
  }
  
  public static Publisher addPublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.AddPublisher";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    Publisher localPublisher = com.ffusion.csil.handlers.BillPresentment.addPublisher(paramSecureUser, paramPublisher, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Add a bill presentment publisher with ID ");
    localStringBuffer.append(paramPublisher.getPublisherID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2113);
    debug(paramSecureUser, str1);
    return localPublisher;
  }
  
  public static Publisher modifyPublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.ModifyPublisher";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    Publisher localPublisher = com.ffusion.csil.handlers.BillPresentment.modifyPublisher(paramSecureUser, paramPublisher, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Modify the bill presentment publisher with ID ");
    localStringBuffer.append(paramPublisher.getPublisherID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2114);
    debug(paramSecureUser, str1);
    return localPublisher;
  }
  
  public static void deletePublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BillPresentment.DeletePublisher";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    com.ffusion.csil.handlers.BillPresentment.deletePublisher(paramSecureUser, paramPublisher, paramHashMap);
    PerfLog.log(str1, l, true);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Delete the bill presentment publisher with ID ");
    localStringBuffer.append(paramPublisher.getPublisherID());
    audit(paramSecureUser, localStringBuffer.toString(), str2, 2115);
    debug(paramSecureUser, str1);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.BillPresentment
 * JD-Core Version:    0.7.0.1
 */