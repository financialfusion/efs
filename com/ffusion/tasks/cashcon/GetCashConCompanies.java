package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.CashConCompanies;
import com.ffusion.beans.cashcon.CashConCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.core.CashCon;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCashConCompanies
  extends BaseTask
  implements Task
{
  protected String compId;
  protected String fiId;
  protected String collectionName = "CashConCompanies";
  private int zO;
  protected String includeDeletedCompanies;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    CashConCompanies localCashConCompanies = null;
    try
    {
      if (this.includeDeletedCompanies != null) {
        localHashMap.put("IncludeDeletedCompanies", this.includeDeletedCompanies);
      }
      localCashConCompanies = CashCon.getCashConCompanies(localSecureUser, this.compId, this.fiId, localHashMap);
      if ((localCashConCompanies != null) && (localCashConCompanies.size() > 0))
      {
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = com.ffusion.csil.core.Entitlements.getCumulativeEntitlements(getEntitlementGroupId());
        Entitlement localEntitlement = new Entitlement();
        localEntitlement.setObjectType("CashConCompany");
        for (int i = 0; i < localCashConCompanies.size(); i++)
        {
          CashConCompany localCashConCompany = (CashConCompany)localCashConCompanies.get(i);
          localEntitlement.setObjectId(localCashConCompany.getBPWID());
          localEntitlement.setOperationName("Cash Con - Deposit Entry");
          if (localEntitlements.contains(localEntitlement)) {
            localCashConCompany.setConcEnabled(false);
          } else {
            localCashConCompany.setConcEnabled(true);
          }
          localEntitlement.setOperationName("Cash Con - Disbursement Request");
          if (localEntitlements.contains(localEntitlement)) {
            localCashConCompany.setDisbEnabled(false);
          } else {
            localCashConCompany.setDisbEnabled(true);
          }
        }
      }
      localHttpSession.setAttribute(this.collectionName, localCashConCompanies);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setCompId(String paramString)
  {
    this.compId = paramString;
  }
  
  public void setFiId(String paramString)
  {
    this.fiId = paramString;
  }
  
  public final void setCollectionName(String paramString)
  {
    this.collectionName = paramString;
  }
  
  public void setEntitlementGroupId(String paramString)
  {
    this.zO = Integer.parseInt(paramString);
  }
  
  public int getEntitlementGroupId()
  {
    return this.zO;
  }
  
  public void setIncludeDeletedCompanies(String paramString)
  {
    this.includeDeletedCompanies = paramString;
  }
  
  public String getIncludeDeletedCompanies()
  {
    return this.includeDeletedCompanies;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.GetCashConCompanies
 * JD-Core Version:    0.7.0.1
 */