package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticateBankEmployee
  extends BaseTask
  implements BankEmployeeTask
{
  protected String access = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str2 = this.taskErrorURL;
    BankEmployee localBankEmployee = (BankEmployee)localHttpSession.getAttribute("BankEmployee");
    if (localBankEmployee != null)
    {
      ArrayList localArrayList = localBankEmployee.getAdminAccess();
      StringTokenizer localStringTokenizer = new StringTokenizer(this.access, ",");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str1 = localStringTokenizer.nextToken();
        if (localArrayList.contains(str1)) {
          str2 = this.successURL;
        }
      }
      if (str2 == this.taskErrorURL) {
        this.error = 5509;
      }
    }
    else
    {
      this.error = 5502;
    }
    return str2;
  }
  
  public void setAccess(String paramString)
  {
    this.access = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.AuthenticateBankEmployee
 * JD-Core Version:    0.7.0.1
 */