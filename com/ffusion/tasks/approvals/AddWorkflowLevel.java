package com.ffusion.tasks.approvals;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsLevel;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Approvals;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddWorkflowLevel
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ApprovalsLevel localApprovalsLevel = (ApprovalsLevel)localHttpSession.getAttribute("ApprovalsLevel");
    try
    {
      Currency localCurrency1 = localApprovalsLevel.getMinAmount();
      Currency localCurrency2 = localApprovalsLevel.getMaxAmount();
      if ((localCurrency2 == null) && (localCurrency1 != null))
      {
        if (!Currency.isValid(localCurrency1.getAmountValue().toString(), localCurrency1.getLocale()))
        {
          this.error = 30115;
          return this.taskErrorURL;
        }
      }
      else if ((localCurrency1 == null) && (localCurrency2 != null))
      {
        if (!Currency.isValid(localCurrency2.getAmountValue().toString(), localCurrency2.getLocale()))
        {
          this.error = 30115;
          return this.taskErrorURL;
        }
      }
      else if ((localCurrency1 != null) && (localCurrency2 != null) && ((!Currency.isValid(localCurrency1.getAmountValue().toString(), localCurrency1.getLocale())) || (!Currency.isValid(localCurrency2.getAmountValue().toString(), localCurrency2.getLocale()))))
      {
        this.error = 30115;
        return this.taskErrorURL;
      }
      Approvals.addWorkflowLevel(localSecureUser, localApprovalsLevel, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      str = this.serviceErrorURL;
      this.error = MapError.mapError(localCSILException);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.AddWorkflowLevel
 * JD-Core Version:    0.7.0.1
 */