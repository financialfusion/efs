package com.ffusion.services.demo;

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

public class BillPresentment
  implements com.ffusion.services.BillPresentment
{
  private Publishers jdField_byte;
  private Billers jdField_new;
  private Consumers jdField_if;
  private BillSummaries a;
  private EBillAccounts jdField_try;
  private ConsumerStatus jdField_for;
  private TCURL jdField_do;
  private int jdField_int = 1000;
  
  public void initialize(Properties paramProperties)
    throws MultiException
  {}
  
  public boolean signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws MultiException
  {
    return true;
  }
  
  public void signOff(SecureUser paramSecureUser, HashMap paramHashMap)
    throws MultiException
  {}
  
  public boolean changePIN(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws MultiException
  {
    return true;
  }
  
  public Publishers getPublishers(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public Consumer getConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public Consumers getConsumerList(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public int getEnrollmentStatus(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException
  {
    return 0;
  }
  
  public Consumer enrollConsumer(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public Consumer modifyConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public void deleteConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException
  {}
  
  public ConsumerStatus getConsumerStatus(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public ConsumerStatus modifyConsumerStatus(SecureUser paramSecureUser, ConsumerStatus paramConsumerStatus, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public EBillAccounts getAccounts(SecureUser paramSecureUser, Biller paramBiller, Consumer paramConsumer, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public EBillAccount activateAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public void deactivateAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws MultiException
  {}
  
  public EBillAccount modifyAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, boolean paramBoolean, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public Billers getBillers(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public BillSummaries getBillSummaries(SecureUser paramSecureUser, Consumer paramConsumer, BillSummary paramBillSummary, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public BillSummary modifyBillSummary(SecureUser paramSecureUser, BillSummary paramBillSummary, PaymentInfo paramPaymentInfo, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public TCURL addTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public TCURL getTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public TCURL modifyTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public void deleteTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws MultiException
  {}
  
  public Biller modifyBiller(SecureUser paramSecureUser, Biller paramBiller, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public Publisher addPublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public Publisher modifyPublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws MultiException
  {
    return null;
  }
  
  public void deletePublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws MultiException
  {}
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.BillPresentment
 * JD-Core Version:    0.7.0.1
 */