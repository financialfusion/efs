package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpresentment.BillSummaries;
import com.ffusion.beans.billpresentment.BillSummary;
import com.ffusion.beans.billpresentment.PaymentInfo;
import com.ffusion.beans.billpresentment.PaymentInfos;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.FilteredList;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateBillSummariesFromPayments
  extends BaseTask
  implements Task
{
  public static final String TRANSACTIONID = "TRANSACTIONID";
  public static final String CONFIRMATIONNUMBER = "CONFIRMATIONNUMBER";
  public static final String REFERENCENUMBER = "REFERENCENUMBER";
  private String In;
  private String Im;
  private String Il = "TRANSACTIONID";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    Payments localPayments = (Payments)localHttpSession.getAttribute("PaymentsToPay");
    BillSummaries localBillSummaries1 = (BillSummaries)localHttpSession.getAttribute("BillsToPay");
    BillSummaries localBillSummaries2 = new BillSummaries((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if ((localPayments == null) || (localPayments.size() < 1))
    {
      this.error = 6517;
      str = this.taskErrorURL;
    }
    else if ((localBillSummaries1 == null) || (localBillSummaries1.size() < 1))
    {
      this.error = 6505;
      str = this.taskErrorURL;
    }
    else
    {
      Iterator localIterator = localPayments.iterator();
      while (localIterator.hasNext())
      {
        Payment localPayment = (Payment)localIterator.next();
        BillSummary localBillSummary = localBillSummaries1.getByBillSummaryID((String)localPayment.get("BILLSUMMARYID"));
        PaymentInfo localPaymentInfo = localBillSummary.getPaymentInfos().add();
        int i = 0;
        if ((this.Il.indexOf("TRANSACTIONID") != -1) && (localPayment.getTransactionID() != null) && (localPayment.getTransactionID().trim().length() != 0))
        {
          localPaymentInfo.setPaymentRefNum(localPayment.getTransactionID());
          i = 1;
        }
        if ((i != 1) && (this.Il.indexOf("REFERENCENUMBER") != -1) && ((localPayment.getReferenceNumber() != null) || (localPayment.getReferenceNumber().trim().length() != 0)))
        {
          localPaymentInfo.setPaymentRefNum(localPayment.getReferenceNumber());
          i = 1;
        }
        if ((i != 1) && (this.Il.indexOf("CONFIRMATIONNUMBER") != -1) && ((localPayment.getConfirmationNum() != null) || (localPayment.getConfirmationNum().trim().length() != 0)))
        {
          localPaymentInfo.setPaymentRefNum(localPayment.getConfirmationNum());
          i = 1;
        }
        localPaymentInfo.setPaymentAccountID(localPayment.getAccountID());
        if (localPayment.getPayDate() != null) {
          localPaymentInfo.setPaymentDate(localPayment.getPayDate());
        }
        localPaymentInfo.setAmountPaid(localPayment.getAmountValue());
        localPaymentInfo.setErrorCode("");
        if ((localPaymentInfo.getPaymentRefNum() == null) || (localPaymentInfo.getPaymentRefNum().trim().length() == 0))
        {
          localPaymentInfo.setErrorCode(String.valueOf(6901));
          if (this.Im != null) {
            localBillSummary.setStatusCode(this.Im);
          }
          localBillSummaries1.remove(localBillSummary);
          localBillSummaries2.add(localBillSummary);
        }
        else if (this.In != null)
        {
          localBillSummary.setStatusCode(this.In);
        }
        localPaymentInfo.setBillSummaryID(localBillSummary.getBillSummaryID());
        HashMap localHashMap = new HashMap();
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        this.error = 0;
        try
        {
          localBillSummary = BillPresentment.modifyBillSummary(localSecureUser, localBillSummary, localPaymentInfo, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
        }
        if (this.error != 0) {
          return this.serviceErrorURL;
        }
        localBillSummary.setClearPaymentFields(true);
      }
      localHttpSession.setAttribute("BillPayErrors", localBillSummaries2);
    }
    return str;
  }
  
  public final void setSuccessStatus(String paramString)
  {
    this.In = paramString;
  }
  
  public final String getSuccessStatus()
  {
    return this.In;
  }
  
  public final void setFailureStatus(String paramString)
  {
    this.Im = paramString;
  }
  
  public final String getFailureStatus()
  {
    return this.Im;
  }
  
  public final void setRefNumFieldToUse(String paramString)
  {
    this.Il = paramString;
  }
  
  public final String getRefNumFieldToUse()
  {
    return this.Il;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.UpdateBillSummariesFromPayments
 * JD-Core Version:    0.7.0.1
 */