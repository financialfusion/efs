package com.ffusion.tasks.billpay;

import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayments;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FindRecPayments
  extends FindPayments
{
  private boolean HB = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = processThis(localHttpSession, "RecPayments");
    if (this.HB)
    {
      Payments localPayments = (Payments)localHttpSession.getAttribute("Payments");
      RecPayments localRecPayments = (RecPayments)localHttpSession.getAttribute("RecPayments");
      for (int i = localPayments.size() - 1; i >= 0; i--)
      {
        Payment localPayment = (Payment)localPayments.get(i);
        if ((localPayment.getRecPaymentID() != null) && (localRecPayments.getByID(localPayment.getRecPaymentID()) != null) && (localPayment.getStatus() == 2)) {
          localPayments.remove(i);
        }
      }
      this.HB = false;
    }
    return str;
  }
  
  public void setRemovePending(String paramString)
  {
    this.HB = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getRemovePending()
  {
    return String.valueOf(this.HB);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.FindRecPayments
 * JD-Core Version:    0.7.0.1
 */