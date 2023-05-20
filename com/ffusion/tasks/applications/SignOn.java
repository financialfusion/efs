package com.ffusion.tasks.applications;

import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.bankemployee.BankEmployeeTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOn
  extends BaseTask
  implements Task, BankEmployeeTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    return "";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.SignOn
 * JD-Core Version:    0.7.0.1
 */