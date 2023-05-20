package com.ffusion.tasks.billpay;

import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetRecPayment
  extends SetPayment
{
  public SetRecPayment()
  {
    this.name = "RecPayments";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    RecPayments localRecPayments = (RecPayments)localHttpSession.getAttribute(this.name);
    Payees localPayees = (Payees)localHttpSession.getAttribute("Payees");
    String str = null;
    if (localRecPayments == null)
    {
      this.error = 2004;
      str = this.taskErrorURL;
    }
    else if (localPayees == null)
    {
      this.error = 2002;
      str = this.taskErrorURL;
    }
    else
    {
      RecPayment localRecPayment = (RecPayment)localRecPayments.getByID(this.paymentID);
      if (localRecPayment == null)
      {
        this.error = 2010;
        str = this.taskErrorURL;
      }
      else
      {
        localHttpSession.setAttribute("Payment", localRecPayment);
        Payee localPayee = localRecPayment.getPayee();
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
 * Qualified Name:     com.ffusion.tasks.billpay.SetRecPayment
 * JD-Core Version:    0.7.0.1
 */