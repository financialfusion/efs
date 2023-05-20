package com.ffusion.services;

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
import com.ffusion.util.MultiException;
import java.util.HashMap;
import java.util.Properties;

public abstract interface BillPresentment
{
  public static final int ERROR_INVALID_SIGNON = 6000;
  public static final int ERROR_NO_DATABASE_CONNECTION = 6001;
  public static final int ERROR_UNABLE_TO_COMPLETE_REQUEST = 6002;
  public static final int ERROR_INVALID_REQUEST = 6003;
  public static final int ERROR_PUBLISHER_ID = 6010;
  public static final int ERROR_PUBLISHER_URL = 6011;
  public static final int ERROR_BILLER_ID = 6012;
  public static final int ERROR_CONSUMER_ID = 6013;
  public static final int ERROR_ACCOUNT = 6014;
  public static final int ERROR_SUMMARY_ID = 6015;
  public static final int ERROR_TAX_ID = 6016;
  public static final int ERROR_TCURL = 6017;
  public static final int ERROR_TCID = 6018;
  public static final int EBPP_SUCCESS = 0;
  
  public abstract void initialize(Properties paramProperties)
    throws MultiException;
  
  public abstract boolean signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws MultiException;
  
  public abstract void signOff(SecureUser paramSecureUser, HashMap paramHashMap)
    throws MultiException;
  
  public abstract boolean changePIN(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws MultiException;
  
  public abstract Publishers getPublishers(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws MultiException;
  
  public abstract Consumer getConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException;
  
  public abstract Consumers getConsumerList(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException;
  
  public abstract int getEnrollmentStatus(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException;
  
  public abstract Consumer enrollConsumer(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException;
  
  public abstract Consumer modifyConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException;
  
  public abstract void deleteConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException;
  
  public abstract ConsumerStatus getConsumerStatus(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException;
  
  public abstract ConsumerStatus modifyConsumerStatus(SecureUser paramSecureUser, ConsumerStatus paramConsumerStatus, HashMap paramHashMap)
    throws MultiException;
  
  public abstract EBillAccounts getAccounts(SecureUser paramSecureUser, Biller paramBiller, Consumer paramConsumer, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws MultiException;
  
  public abstract EBillAccount activateAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws MultiException;
  
  public abstract void deactivateAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws MultiException;
  
  public abstract EBillAccount modifyAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, boolean paramBoolean, HashMap paramHashMap)
    throws MultiException;
  
  public abstract Billers getBillers(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws MultiException;
  
  public abstract BillSummaries getBillSummaries(SecureUser paramSecureUser, Consumer paramConsumer, BillSummary paramBillSummary, HashMap paramHashMap)
    throws MultiException;
  
  public abstract BillSummary modifyBillSummary(SecureUser paramSecureUser, BillSummary paramBillSummary, PaymentInfo paramPaymentInfo, HashMap paramHashMap)
    throws MultiException;
  
  public abstract TCURL addTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws MultiException;
  
  public abstract TCURL getTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws MultiException;
  
  public abstract TCURL modifyTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws MultiException;
  
  public abstract void deleteTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws MultiException;
  
  public abstract Biller modifyBiller(SecureUser paramSecureUser, Biller paramBiller, HashMap paramHashMap)
    throws MultiException;
  
  public abstract Publisher addPublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws MultiException;
  
  public abstract Publisher modifyPublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws MultiException;
  
  public abstract void deletePublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws MultiException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BillPresentment
 * JD-Core Version:    0.7.0.1
 */