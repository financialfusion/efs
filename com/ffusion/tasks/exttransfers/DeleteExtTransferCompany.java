package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.exttransfers.ExtTransferCompanies;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ExternalTransferAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteExtTransferCompany
  extends ModifyExtTransferCompany
{
  protected String nextURL = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    str = processDeleteExtTransferCompany(localHttpSession);
    if (this.error == 0) {
      localHttpSession.setAttribute("ExternalTransferAccountsUpdated", "true");
    }
    return str;
  }
  
  protected String processDeleteExtTransferCompany(HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    String str = this.successURL;
    try
    {
      ExternalTransferAdmin.deleteExtTransferCompany(localSecureUser, this, this.fiId, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      str = this.serviceErrorURL;
    }
    catch (Exception localException)
    {
      DebugLog.log("ERROR: Exception thrown when deleting external transfer company:");
      localException.printStackTrace();
      str = this.taskErrorURL;
    }
    if (this.error == 0)
    {
      ExtTransferCompanies localExtTransferCompanies = (ExtTransferCompanies)paramHttpSession.getAttribute("ExternalTransferCompanies");
      if (localExtTransferCompanies != null) {
        localExtTransferCompanies.removeByID(getBpwID());
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.DeleteExtTransferCompany
 * JD-Core Version:    0.7.0.1
 */