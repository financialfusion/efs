package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignOn
  extends com.ffusion.tasks.SignOn
  implements Task
{
  private String in = "com.ffusion.services.BillPay";
  
  protected int signOn(HttpServletRequest paramHttpServletRequest)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BillPay localBillPay = (BillPay)localHttpSession.getAttribute(this.in);
    HashMap localHashMap = null;
    if (localBillPay != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localBillPay);
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      localSecureUser = Billpay.signOn(localSecureUser, this.userName, this.password, localHashMap);
      localHttpSession.setAttribute("SecureUser", localSecureUser);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    return this.error;
  }
  
  public void setServiceName(String paramString)
  {
    this.in = paramString;
  }
  
  public String getServiceName()
  {
    return this.in;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.SignOn
 * JD-Core Version:    0.7.0.1
 */