package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.EBillAccount;
import com.ffusion.beans.billpresentment.EBillAccounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeactivateEBillAccount
  extends BaseTask
  implements Task
{
  private String JF = "ACTIVE";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    EBillAccount localEBillAccount = (EBillAccount)localHttpSession.getAttribute("EBillAccount");
    if (localEBillAccount == null)
    {
      str = this.taskErrorURL;
      this.error = 6509;
    }
    else if ((this.JF == null) || ((localEBillAccount.getStatusCode() != null) && (this.JF.indexOf(localEBillAccount.getStatusCode()) == -1)))
    {
      str = this.taskErrorURL;
      this.error = 6607;
    }
    else
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      this.error = 0;
      try
      {
        BillPresentment.deactivateAccount(localSecureUser, localEBillAccount, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
      if (this.error == 0)
      {
        EBillAccounts localEBillAccounts = (EBillAccounts)localHttpSession.getAttribute("EBillAccounts");
        if (localEBillAccounts != null)
        {
          localHttpSession.removeAttribute("EBillAccount");
          str = this.successURL;
        }
        else
        {
          this.error = 6510;
          str = this.taskErrorURL;
        }
      }
      else
      {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public final void setValidDeactivateStatus(String paramString)
  {
    this.JF = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.DeactivateEBillAccount
 * JD-Core Version:    0.7.0.1
 */