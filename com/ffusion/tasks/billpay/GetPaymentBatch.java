package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.PaymentBatch;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPaymentBatch
  extends ExtendedBaseTask
  implements Task
{
  protected boolean isRecurring = false;
  protected String serviceName = "com.ffusion.services.BillPay";
  
  public GetPaymentBatch()
  {
    this.beanSessionName = "PaymentBatch";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.id == null) || (this.id.length() == 0))
    {
      this.error = 81;
      return this.taskErrorURL;
    }
    Payees localPayees = (Payees)localHttpSession.getAttribute("Payees");
    if (localPayees == null)
    {
      this.error = 2002;
      return this.taskErrorURL;
    }
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("BillPayAccounts");
    if (localAccounts == null)
    {
      this.error = 2005;
      return this.taskErrorURL;
    }
    BillPay localBillPay = (BillPay)localHttpSession.getAttribute(this.serviceName);
    HashMap localHashMap = new HashMap();
    if (localBillPay != null) {
      localHashMap.put("SERVICE", localBillPay);
    }
    localHashMap.put("PAYEES", localPayees);
    localHashMap.put("ACCOUNTS", localAccounts);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    PaymentBatch localPaymentBatch = new PaymentBatch();
    localPaymentBatch.setID(this.id);
    try
    {
      localPaymentBatch = Billpay.getPaymentBatchByID(localSecureUser, localPaymentBatch, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this.beanSessionName, localPaymentBatch);
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetPaymentBatch
 * JD-Core Version:    0.7.0.1
 */