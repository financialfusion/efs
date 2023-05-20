package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.PaymentBatch;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CancelPaymentBatch
  extends ExtendedBaseTask
  implements Task
{
  public CancelPaymentBatch()
  {
    this.beanSessionName = "PaymentBatch";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PaymentBatch localPaymentBatch = (PaymentBatch)localHttpSession.getAttribute(this.beanSessionName);
    if (localPaymentBatch == null)
    {
      this.error = 2003;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      Billpay.cancelPaymentBatch(localSecureUser, localPaymentBatch, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    localHttpSession.removeAttribute(this.beanSessionName);
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.CancelPaymentBatch
 * JD-Core Version:    0.7.0.1
 */