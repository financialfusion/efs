package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.CashConCompanies;
import com.ffusion.beans.cashcon.CashConCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CashCon;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteCashConCompany
  extends BaseTask
  implements Task
{
  protected String bpwID;
  protected String fiId;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.bpwID == null) || (this.bpwID.length() == 0))
    {
      this.error = 24002;
      str = this.taskErrorURL;
    }
    else
    {
      CashConCompanies localCashConCompanies = (CashConCompanies)localHttpSession.getAttribute("CashConCompanies");
      CashConCompany localCashConCompany = localCashConCompanies.getByID(this.bpwID);
      if (localCashConCompany == null)
      {
        localCashConCompany = new CashConCompany();
        localCashConCompany.setBPWID(this.bpwID);
      }
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        CashCon.deleteCashConCompany(localSecureUser, localCashConCompany, this.fiId, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException, localHttpSession);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setFiId(String paramString)
  {
    this.fiId = paramString;
  }
  
  public void setBPWID(String paramString)
  {
    this.bpwID = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.DeleteCashConCompany
 * JD-Core Version:    0.7.0.1
 */