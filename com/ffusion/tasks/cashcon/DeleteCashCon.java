package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.CashCons;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteCashCon
  extends BaseTask
  implements Task
{
  protected String cashconsName = "CashCons";
  protected String cashconName = "CashCon";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.beans.cashcon.CashCon localCashCon = (com.ffusion.beans.cashcon.CashCon)localHttpSession.getAttribute(this.cashconName);
    if (localCashCon == null)
    {
      this.error = 24000;
      str = this.taskErrorURL;
    }
    else
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        com.ffusion.csil.core.CashCon.deleteCashCon(localSecureUser, localCashCon, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException, localHttpSession);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        CashCons localCashCons = (CashCons)localHttpSession.getAttribute(this.cashconsName);
        if (localCashCons == null)
        {
          this.error = 24001;
          str = this.taskErrorURL;
        }
        else
        {
          localCashCons.remove(localCashCon);
        }
        str = this.successURL;
      }
    }
    return str;
  }
  
  public final void setCollectionName(String paramString)
  {
    this.cashconsName = paramString;
  }
  
  public final String getCollectionName()
  {
    return this.cashconsName;
  }
  
  public final void setCashConSessionName(String paramString)
  {
    this.cashconName = paramString;
  }
  
  public final String getCashConSessionName()
  {
    return this.cashconName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.DeleteCashCon
 * JD-Core Version:    0.7.0.1
 */