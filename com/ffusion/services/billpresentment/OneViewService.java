package com.ffusion.services.billpresentment;

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
import com.ffusion.efs.adapters.billpresentment.OneViewAdapter;
import com.ffusion.services.BillPresentment;
import com.ffusion.util.MultiException;
import java.util.HashMap;
import java.util.Properties;

public class OneViewService
  implements BillPresentment
{
  static final int ERROR_INVALID_SIGNON = 6000;
  static final int ERROR_NO_DATABASE_CONNECTION = 6001;
  static final int ERROR_UNABLE_TO_COMPLETE_REQUEST = 6002;
  static final int ERROR_INVALID_REQUEST = 6003;
  public static final int EBPP_SUCCESS = 0;
  
  public void initialize(Properties paramProperties)
    throws MultiException
  {
    OneViewAdapter.initialize(paramProperties);
  }
  
  public boolean signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
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
    return OneViewAdapter.getPublishers(paramPublisher);
  }
  
  public Consumer getConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.getConsumer(paramConsumer);
  }
  
  public Consumers getConsumerList(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.getConsumerList(paramPublisher, paramConsumer);
  }
  
  public int getEnrollmentStatus(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.getEnrollmentStatus(paramPublisher, paramConsumer);
  }
  
  public Consumer enrollConsumer(SecureUser paramSecureUser, Publisher paramPublisher, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.enrollConsumer(paramPublisher, paramConsumer);
  }
  
  public Consumer modifyConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.modifyConsumer(paramConsumer);
  }
  
  public void deleteConsumer(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException
  {
    OneViewAdapter.deleteConsumer(paramConsumer);
  }
  
  public ConsumerStatus getConsumerStatus(SecureUser paramSecureUser, Consumer paramConsumer, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.getConsumerStatus(paramConsumer);
  }
  
  public ConsumerStatus modifyConsumerStatus(SecureUser paramSecureUser, ConsumerStatus paramConsumerStatus, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.modifyConsumerStatus(paramConsumerStatus);
  }
  
  public EBillAccounts getAccounts(SecureUser paramSecureUser, Biller paramBiller, Consumer paramConsumer, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.getAccounts(paramBiller, paramConsumer, paramEBillAccount);
  }
  
  public EBillAccount activateAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.activateAccount(paramEBillAccount);
  }
  
  public void deactivateAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, HashMap paramHashMap)
    throws MultiException
  {
    OneViewAdapter.deactivateAccount(paramEBillAccount);
  }
  
  public EBillAccount modifyAccount(SecureUser paramSecureUser, EBillAccount paramEBillAccount, boolean paramBoolean, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.modifyAccount(paramEBillAccount, paramBoolean);
  }
  
  public Billers getBillers(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.getBillers(paramPublisher);
  }
  
  public BillSummaries getBillSummaries(SecureUser paramSecureUser, Consumer paramConsumer, BillSummary paramBillSummary, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.getBillSummaries(paramConsumer, paramBillSummary);
  }
  
  public BillSummary modifyBillSummary(SecureUser paramSecureUser, BillSummary paramBillSummary, PaymentInfo paramPaymentInfo, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.modifyBillSummary(paramBillSummary, paramPaymentInfo);
  }
  
  public TCURL addTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.addTCURL(paramTCURL);
  }
  
  public TCURL getTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.getTCURL(paramTCURL);
  }
  
  public TCURL modifyTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.modifyTCURL(paramTCURL);
  }
  
  public void deleteTCURL(SecureUser paramSecureUser, TCURL paramTCURL, HashMap paramHashMap)
    throws MultiException
  {
    OneViewAdapter.deleteTCURL(paramTCURL);
  }
  
  public Biller modifyBiller(SecureUser paramSecureUser, Biller paramBiller, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.modifyBiller(paramBiller);
  }
  
  public Publisher addPublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.addPublisher(paramPublisher);
  }
  
  public Publisher modifyPublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws MultiException
  {
    return OneViewAdapter.modifyPublisher(paramPublisher);
  }
  
  public void deletePublisher(SecureUser paramSecureUser, Publisher paramPublisher, HashMap paramHashMap)
    throws MultiException
  {
    OneViewAdapter.deletePublisher(paramPublisher);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.billpresentment.OneViewService
 * JD-Core Version:    0.7.0.1
 */