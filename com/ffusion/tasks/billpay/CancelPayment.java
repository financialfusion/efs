package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CancelPayment
  extends ExtendedBaseTask
  implements Task
{
  private Payment FK;
  
  public CancelPayment()
  {
    this.beanSessionName = "Payment";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (localHttpSession.getAttribute(this.beanSessionName) == null)
    {
      this.error = 2006;
      str = this.taskErrorURL;
    }
    else
    {
      this.error = doProcess(localHttpSession);
      if (this.error == 0) {
        str = this.successURL;
      } else {
        str = this.serviceErrorURL;
      }
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("PaymentsUpdated", "true");
    }
    return str;
  }
  
  public int doProcess(HttpSession paramHttpSession)
  {
    int i = 0;
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute("com.ffusion.services.BillPay");
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    this.FK = ((Payment)paramHttpSession.getAttribute(this.beanSessionName));
    Object localObject1;
    Object localObject2;
    Object localObject3;
    if ((this.FK instanceof RecPayment))
    {
      localObject1 = null;
      if (localBillPay != null)
      {
        localObject1 = new HashMap();
        ((HashMap)localObject1).put("SERVICE", localBillPay);
      }
      int j = 0;
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      try
      {
        if ((this.FK.getPaymentType().equals("TEMPLATE")) || (this.FK.getPaymentType().equals("RECTEMPLATE")))
        {
          Billpay.deletePaymentTemplate(localSecureUser, this.FK, (HashMap)localObject1);
          j = 1;
        }
        else
        {
          RecPayment localRecPayment = Billpay.deleteRecPayment(localSecureUser, (RecPayment)this.FK, (HashMap)localObject1);
          ((RecPayment)this.FK).set(localRecPayment);
        }
      }
      catch (CSILException localCSILException1)
      {
        i = MapError.mapError(localCSILException1, paramHttpSession);
      }
      if ((i == 0) && (paramHttpSession.getAttribute("RecPayments") != null) && (j == 0))
      {
        localObject2 = (RecPayments)paramHttpSession.getAttribute("RecPayments");
        localObject3 = ((RecPayments)localObject2).getByRecID(this.FK.getID());
        ((RecPayments)localObject2).remove(localObject3);
      }
    }
    else
    {
      localObject1 = new Payments(localLocale);
      ((Payments)localObject1).add(this.FK);
      HashMap localHashMap = null;
      int k = 0;
      if (localBillPay != null)
      {
        localHashMap = new HashMap();
        localHashMap.put("SERVICE", localBillPay);
      }
      localObject2 = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      try
      {
        if ((this.FK.getPaymentType().equals("TEMPLATE")) || (this.FK.getPaymentType().equals("RECTEMPLATE")))
        {
          Billpay.deletePaymentTemplate((SecureUser)localObject2, this.FK, localHashMap);
          k = 1;
        }
        else
        {
          localObject3 = Billpay.cancelPayments((SecureUser)localObject2, (Payments)localObject1, localHashMap);
          this.FK.set((Payment)((Payments)localObject3).get(0));
        }
      }
      catch (CSILException localCSILException2)
      {
        i = MapError.mapError(localCSILException2, paramHttpSession);
      }
      if ((i == 0) && (k == 0))
      {
        this.FK.setStatus(3);
        Payments localPayments = (Payments)paramHttpSession.getAttribute("Payments");
        Payment localPayment = localPayments.getByID(this.FK.getID());
        localPayments.remove(localPayment);
      }
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.CancelPayment
 * JD-Core Version:    0.7.0.1
 */