package com.ffusion.tasks.billpay;

import com.ffusion.services.BillPay2;
import com.ffusion.tasks.InitService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Initialize
  extends InitService
  implements Task
{
  private String kz = "com.ffusion.services.BillPay";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    BillPay2 localBillPay2 = null;
    String str = null;
    if (this.className == null)
    {
      this.error = 2031;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        Class localClass = Class.forName(this.className);
        localBillPay2 = (BillPay2)localClass.newInstance();
        if ((this.error = localBillPay2.initialize(this.initURL)) != 0)
        {
          str = this.serviceErrorURL;
        }
        else
        {
          HttpSession localHttpSession = paramHttpServletRequest.getSession();
          localHttpSession.setAttribute(this.kz, localBillPay2);
          str = this.successURL;
        }
      }
      catch (Exception localException)
      {
        this.error = 2001;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  public void setServiceName(String paramString)
  {
    this.kz = paramString;
  }
  
  public String getServiceName()
  {
    return this.kz;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.Initialize
 * JD-Core Version:    0.7.0.1
 */