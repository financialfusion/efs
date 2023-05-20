package com.ffusion.tasks.billpay;

import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetPayment
  extends BaseTask
  implements Task
{
  protected String paymentID;
  protected String name = "Payments";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Payments localPayments = (Payments)localHttpSession.getAttribute(this.name);
    Payees localPayees = (Payees)localHttpSession.getAttribute("Payees");
    String str = null;
    if (localPayments == null)
    {
      this.error = 2003;
      str = this.taskErrorURL;
    }
    else if (localPayees == null)
    {
      this.error = 2002;
      str = this.taskErrorURL;
    }
    else
    {
      Payment localPayment = localPayments.getByID(this.paymentID);
      if (localPayment == null)
      {
        this.error = 2009;
        str = this.taskErrorURL;
      }
      else
      {
        localHttpSession.setAttribute("Payment", localPayment);
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
  
  public void setID(String paramString)
  {
    this.paymentID = paramString;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.SetPayment
 * JD-Core Version:    0.7.0.1
 */