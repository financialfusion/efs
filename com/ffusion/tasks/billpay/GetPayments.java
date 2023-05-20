package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPayments
  extends BaseTask
  implements Task
{
  protected boolean reload = false;
  private String Hb = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("BillPayAccounts");
    Payees localPayees = (Payees)localHttpSession.getAttribute("Payees");
    Payments localPayments = (Payments)localHttpSession.getAttribute("Payments");
    RecPayments localRecPayments = (RecPayments)localHttpSession.getAttribute("RecPayments");
    if (this.reload)
    {
      localPayments = null;
      localHttpSession.removeAttribute("Payments");
      localRecPayments = null;
      localHttpSession.removeAttribute("RecPayments");
      this.reload = false;
    }
    if (localPayments == null)
    {
      if (localPayees == null)
      {
        this.error = 2002;
        str = this.taskErrorURL;
      }
      else if (localAccounts == null)
      {
        this.error = 2005;
        str = this.taskErrorURL;
      }
      else
      {
        Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
        BillPay localBillPay = (BillPay)localHttpSession.getAttribute("com.ffusion.services.BillPay");
        localPayments = new Payments(localLocale);
        localRecPayments = new RecPayments(localLocale);
        HashMap localHashMap = new HashMap();
        if (localBillPay != null) {
          localHashMap.put("SERVICE", localBillPay);
        }
        localHashMap.put("PAYMENTS", localPayments);
        localHashMap.put("RECPAYMENTS", localRecPayments);
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        synchronized (this)
        {
          try
          {
            Object[] arrayOfObject = Billpay.getPayments(localSecureUser, localAccounts, localPayees, localHashMap);
            localPayments = (Payments)arrayOfObject[0];
            localRecPayments = (RecPayments)arrayOfObject[1];
          }
          catch (CSILException localCSILException)
          {
            this.error = MapError.mapError(localCSILException);
          }
        }
        if ((this.error == 0) || (localPayments.size() > 0) || (localRecPayments.size() > 0))
        {
          if (this.Hb != null)
          {
            localPayments.setDateFormat(this.Hb);
            localRecPayments.setDateFormat(this.Hb);
          }
          localHttpSession.setAttribute("Payments", localPayments);
          localHttpSession.setAttribute("RecPayments", localRecPayments);
          localHttpSession.setAttribute("PayHistoryPayees", localPayments.getPayees(5));
          if (this.error == 0) {
            str = this.successURL;
          } else {
            str = this.serviceErrorURL;
          }
        }
        else
        {
          str = this.serviceErrorURL;
        }
      }
    }
    else {
      str = this.successURL;
    }
    return str;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setDateFormat(String paramString)
  {
    String str = paramString == null ? null : paramString.trim();
    if ((str == null) || (str.length() == 0)) {
      this.Hb = null;
    } else {
      this.Hb = str;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetPayments
 * JD-Core Version:    0.7.0.1
 */