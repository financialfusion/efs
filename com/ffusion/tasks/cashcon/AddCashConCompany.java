package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.CashConCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.core.CashCon;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.efs.tasks.entitlements.Task;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddCashConCompany
  extends ModifyCashConCompany
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    CashConCompany localCashConCompany = (CashConCompany)localHttpSession.getAttribute(this.cashconName);
    if (localCashConCompany == null)
    {
      this.error = 24002;
      str = this.taskErrorURL;
    }
    else if ((this.fiId == null) || (this.fiId.length() == 0))
    {
      this.error = 24004;
      str = this.taskErrorURL;
    }
    else if (validateInput(localCashConCompany))
    {
      if (this.m_doProcess)
      {
        HashMap localHashMap = new HashMap();
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        try
        {
          CashCon.addCashConCompany(localSecureUser, localCashConCompany, this.fiId, localHashMap);
          if ((!localCashConCompany.getConcEnabled()) || (!localCashConCompany.getDisbEnabled()))
          {
            com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
            Entitlement localEntitlement;
            if (!localCashConCompany.getConcEnabled())
            {
              localEntitlement = new Entitlement();
              localEntitlement.setObjectType("CashConCompany");
              localEntitlement.setObjectId(localCashConCompany.getBPWID());
              localEntitlement.setOperationName("Cash Con - Deposit Entry");
              localEntitlements.add(localEntitlement);
            }
            if (!localCashConCompany.getDisbEnabled())
            {
              localEntitlement = new Entitlement();
              localEntitlement.setObjectType("CashConCompany");
              localEntitlement.setObjectId(localCashConCompany.getBPWID());
              localEntitlement.setOperationName("Cash Con - Disbursement Request");
              localEntitlements.add(localEntitlement);
            }
            com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), getEntitlementGroupId(), localEntitlements);
          }
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException, localHttpSession);
          str = this.serviceErrorURL;
        }
      }
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.AddCashConCompany
 * JD-Core Version:    0.7.0.1
 */