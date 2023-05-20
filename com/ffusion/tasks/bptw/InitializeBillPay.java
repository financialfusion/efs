package com.ffusion.tasks.bptw;

import com.ffusion.services.bptw.BillPay;
import com.ffusion.tasks.InitService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InitializeBillPay
  extends InitService
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (localHttpSession.getAttribute("BptwBillPay") != null) {
      return this.successURL;
    }
    if (this.className == null)
    {
      this.error = 17000;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        Class localClass = Class.forName(this.className);
        BillPay localBillPay = (BillPay)localClass.newInstance();
        if ((this.error = localBillPay.initialize(this.initURL)) != 0)
        {
          str = this.serviceErrorURL;
        }
        else
        {
          localHttpSession.setAttribute("BptwBillPay", localBillPay);
          str = this.successURL;
        }
      }
      catch (Exception localException)
      {
        this.error = 17001;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bptw.InitializeBillPay
 * JD-Core Version:    0.7.0.1
 */