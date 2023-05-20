package com.ffusion.tasks.billpay;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.beans.FundsTransactionTemplates;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetPaymentFromTemplate
  extends BaseTask
  implements Task
{
  private String HA = "AddPayment";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    FundsTransactionTemplates localFundsTransactionTemplates = (FundsTransactionTemplates)localHttpSession.getAttribute("PaymentTemplates");
    int i = 0;
    try
    {
      i = Integer.parseInt((String)localHttpSession.getAttribute("LoadPaymentTemplate"));
    }
    catch (NumberFormatException localNumberFormatException) {}
    String str = null;
    RecPayment localRecPayment1 = (RecPayment)localHttpSession.getAttribute(this.HA);
    if ((localFundsTransactionTemplates == null) || (localRecPayment1 == null))
    {
      this.error = 2003;
      str = this.taskErrorURL;
    }
    else if (i == 0)
    {
      this.error = 2009;
      str = this.taskErrorURL;
    }
    else
    {
      FundsTransactionTemplate localFundsTransactionTemplate = localFundsTransactionTemplates.getByTemplateID(i);
      RecPayment localRecPayment2 = null;
      if (localFundsTransactionTemplate != null) {
        localRecPayment2 = (RecPayment)localFundsTransactionTemplate.getFundsTransaction();
      }
      if (localRecPayment2 == null)
      {
        this.error = 2009;
        str = this.taskErrorURL;
      }
      else
      {
        localRecPayment1.set(localRecPayment2);
        localRecPayment1.setPayDate(new DateTime(localRecPayment2.getLocale()));
        localHttpSession.setAttribute(this.HA, localRecPayment1);
        str = this.successURL;
      }
    }
    return str;
  }
  
  public void setName(String paramString)
  {
    this.HA = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.SetPaymentFromTemplate
 * JD-Core Version:    0.7.0.1
 */