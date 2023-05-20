package com.ffusion.tasks.billpay;

import com.ffusion.services.BillPay;
import com.ffusion.services.BillPay2;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InitService
  extends com.ffusion.tasks.InitService
  implements Task
{
  private String kA = "com.ffusion.services.BillPay";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    BillPay localBillPay = null;
    String str = null;
    try
    {
      this.error = 0;
      Class localClass = Class.forName(this.className);
      localBillPay = (BillPay)localClass.newInstance();
      if ((localBillPay instanceof BillPay2)) {
        this.error = ((BillPay2)localBillPay).initialize(this.initURL);
      } else {
        localBillPay.setInitURL(this.initURL);
      }
      if (this.error != 0)
      {
        str = this.serviceErrorURL;
      }
      else
      {
        HttpSession localHttpSession = paramHttpServletRequest.getSession();
        localHttpSession.setAttribute(this.kA, localBillPay);
        str = this.successURL;
      }
    }
    catch (Exception localException)
    {
      this.error = 2001;
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setServiceName(String paramString)
  {
    this.kA = paramString;
  }
  
  public String getServiceName()
  {
    return this.kA;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.InitService
 * JD-Core Version:    0.7.0.1
 */