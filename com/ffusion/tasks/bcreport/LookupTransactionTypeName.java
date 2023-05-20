package com.ffusion.tasks.bcreport;

import com.ffusion.beans.user.UserLocale;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LookupTransactionTypeName
  extends BaseTask
  implements Task
{
  private String aPb;
  private String aPa = "";
  private String aO9 = "com.ffusion.beans.banking.resources";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    this.aPa = ResourceUtil.getString("TransactionTypeText" + this.aPb, this.aO9, localUserLocale.getLocale());
    return str;
  }
  
  public void setCode(String paramString)
  {
    this.aPb = paramString;
  }
  
  public String getName()
  {
    return this.aPa;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bcreport.LookupTransactionTypeName
 * JD-Core Version:    0.7.0.1
 */