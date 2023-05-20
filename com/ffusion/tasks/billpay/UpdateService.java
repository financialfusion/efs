package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.services.BillPay;
import com.ffusion.services.BillPay4;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateService
  extends BaseTask
  implements Task
{
  private String GU;
  private String GT;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BillPay localBillPay = (BillPay)localHttpSession.getAttribute("com.ffusion.services.BillPay");
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    String str = null;
    if (localBillPay != null)
    {
      if (this.GU != null) {
        localBillPay.setUserName(this.GU);
      }
      if (this.GT != null) {
        localBillPay.setPassword(this.GT);
      }
      if ((localSecureUser != null) && ((localBillPay instanceof BillPay4))) {
        ((BillPay4)localBillPay).setSecureUser(localSecureUser);
      }
      str = this.successURL;
    }
    else
    {
      this.error = 2001;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setUserName(String paramString)
  {
    this.GU = paramString;
  }
  
  public void setPassword(String paramString)
  {
    this.GT = paramString;
  }
  
  public String getUserName()
  {
    return this.GU;
  }
  
  public String getPassword()
  {
    return this.GT;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.UpdateService
 * JD-Core Version:    0.7.0.1
 */