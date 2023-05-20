package com.ffusion.tasks.bankemployee;

import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadProfileProperties
  extends BaseTask
  implements BankEmployeeTask
{
  String t2 = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    return str;
  }
  
  public void setInitURL(String paramString)
  {
    this.t2 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.LoadProfileProperties
 * JD-Core Version:    0.7.0.1
 */