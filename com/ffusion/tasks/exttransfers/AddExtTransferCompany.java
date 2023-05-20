package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.exttransfers.ExtTransferCompanies;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.ExternalTransferAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddExtTransferCompany
  extends ModifyExtTransferCompany
{
  protected String nextURL = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    str = addExtTransferCompany(localHttpSession);
    if (this.error == 0) {
      localHttpSession.setAttribute("ExternalTransferAccountsUpdated", "true");
    }
    return str;
  }
  
  protected String addExtTransferCompany(HttpSession paramHttpSession)
  {
    String str = null;
    int i = 1;
    if (validateInput(paramHttpSession))
    {
      if ((this.fiId == null) || (this.fiId.trim().length() == 0))
      {
        this.error = 4208;
        str = this.taskErrorURL;
      }
      else
      {
        str = processAddExtTransferCompany(paramHttpSession);
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String processAddExtTransferCompany(HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      AffiliateBank localAffiliateBank = AffiliateBankAdmin.getAffiliateBankInfoByID(localSecureUser, Integer.parseInt(this.affiliateId), localHashMap);
      this.fiId = localAffiliateBank.getFIBPWID();
      ExtTransferCompany localExtTransferCompany = ExternalTransferAdmin.addExtTransferCompany(localSecureUser, this, this.fiId, localHashMap);
      set(localExtTransferCompany);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      this.nextURL = this.serviceErrorURL;
    }
    catch (Exception localException)
    {
      DebugLog.log("ERROR: Exception thrown when adding transfer:");
      localException.printStackTrace();
      this.nextURL = this.taskErrorURL;
    }
    if (this.error == 0)
    {
      paramHttpSession.setAttribute("ExternalTransferCompany", this);
      ExtTransferCompanies localExtTransferCompanies = (ExtTransferCompanies)paramHttpSession.getAttribute("ExternalTransferCompanies");
      if (localExtTransferCompanies != null) {
        localExtTransferCompanies.add(this);
      }
    }
    else
    {
      this.nextURL = this.taskErrorURL;
    }
    return this.nextURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.AddExtTransferCompany
 * JD-Core Version:    0.7.0.1
 */