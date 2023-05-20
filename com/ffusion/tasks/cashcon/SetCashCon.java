package com.ffusion.tasks.cashcon;

import com.ffusion.beans.cashcon.CashCon;
import com.ffusion.beans.cashcon.CashCons;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetCashCon
  extends BaseTask
  implements Task
{
  protected String id;
  protected String cashconsName = "CashCons";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    CashCons localCashCons = (CashCons)localHttpSession.getAttribute(this.cashconsName);
    if (localCashCons != null)
    {
      CashCon localCashCon = localCashCons.getByID(this.id);
      if (localCashCon != null)
      {
        localHttpSession.setAttribute("CashCon", localCashCon);
        str = this.successURL;
      }
      else
      {
        this.error = 24000;
        str = this.taskErrorURL;
      }
    }
    else
    {
      this.error = 24001;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public final void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public final void setCollectionName(String paramString)
  {
    this.cashconsName = paramString;
  }
  
  public final String getCollectionName()
  {
    return this.cashconsName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.SetCashCon
 * JD-Core Version:    0.7.0.1
 */