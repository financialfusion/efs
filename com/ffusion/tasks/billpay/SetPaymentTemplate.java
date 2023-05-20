package com.ffusion.tasks.billpay;

import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetPaymentTemplate
  extends ExtendedBaseTask
  implements Task
{
  public SetPaymentTemplate()
  {
    this.beanSessionName = "Payment";
    this.collectionSessionName = "PaymentTemplates";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    FundsTransactions localFundsTransactions = (FundsTransactions)localHttpSession.getAttribute(this.collectionSessionName);
    String str = null;
    if (localFundsTransactions == null)
    {
      this.error = 2003;
      str = this.taskErrorURL;
    }
    else
    {
      FundsTransaction localFundsTransaction = localFundsTransactions.getByID(this.id);
      if ((localFundsTransaction == null) || (!(localFundsTransaction instanceof Payment)))
      {
        this.error = 2009;
        str = this.taskErrorURL;
      }
      else
      {
        Payment localPayment = (Payment)localFundsTransaction;
        localHttpSession.setAttribute(this.beanSessionName, localPayment);
        Payee localPayee = localPayment.getPayee();
        if (localPayee != null) {
          localHttpSession.setAttribute("Payee", localPayee);
        } else {
          localHttpSession.removeAttribute("Payee");
        }
        str = this.successURL;
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.SetPaymentTemplate
 * JD-Core Version:    0.7.0.1
 */