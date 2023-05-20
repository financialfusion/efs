package com.ffusion.tasks.bankemployee;

import com.ffusion.services.BankEmployeeAdmin2;
import com.ffusion.tasks.InitService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Initialize
  extends InitService
  implements BankEmployeeTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    if ((this.className == null) || (this.className.length() == 0))
    {
      this.error = 5516;
      str = this.taskErrorURL;
    }
    else
    {
      BankEmployeeAdmin2 localBankEmployeeAdmin2 = null;
      try
      {
        HttpSession localHttpSession = paramHttpServletRequest.getSession();
        Class localClass = Class.forName(this.className);
        localBankEmployeeAdmin2 = (BankEmployeeAdmin2)localClass.newInstance();
        if ((this.error = localBankEmployeeAdmin2.initialize(this.initURL)) != 0) {
          str = this.serviceErrorURL;
        } else {
          localHttpSession.setAttribute("BankEmployeeService", localBankEmployeeAdmin2);
        }
      }
      catch (Exception localException)
      {
        this.error = 5519;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.Initialize
 * JD-Core Version:    0.7.0.1
 */