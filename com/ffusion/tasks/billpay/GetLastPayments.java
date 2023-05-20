package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payments;
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

public class GetLastPayments
  extends ExtendedBaseTask
  implements Task
{
  protected String payeeName = "Payees";
  
  public GetLastPayments()
  {
    this.collectionSessionName = "LastPayments";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    Payments localPayments = null;
    Payees localPayees = (Payees)localHttpSession.getAttribute(this.payeeName);
    if (localPayees == null)
    {
      this.error = 2002;
      str = this.taskErrorURL;
    }
    else
    {
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      BillPay localBillPay = (BillPay)localHttpSession.getAttribute("com.ffusion.services.BillPay");
      HashMap localHashMap = new HashMap();
      if (localBillPay != null) {
        localHashMap.put("SERVICE", localBillPay);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      synchronized (this)
      {
        try
        {
          localPayments = Billpay.getLastPayments(localSecureUser, localPayees, localHashMap);
          localHttpSession.setAttribute("LastPayments", localPayments);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
      }
    }
    return str;
  }
  
  public void setPayeeName(String paramString)
  {
    this.payeeName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetLastPayments
 * JD-Core Version:    0.7.0.1
 */