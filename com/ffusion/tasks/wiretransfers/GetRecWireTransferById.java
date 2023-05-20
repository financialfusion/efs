package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRecWireTransferById
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  public GetRecWireTransferById()
  {
    this.beanSessionName = "RecWireTransfer";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.id == null) || (this.id.length() == 0))
    {
      this.error = 81;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("WiresAccounts");
    localHashMap.put("ACCOUNTS", localAccounts);
    WireTransfer localWireTransfer = null;
    try
    {
      localWireTransfer = Wire.getRecWireTransferById(localSecureUser, this.id, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this.beanSessionName, localWireTransfer);
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetRecWireTransferById
 * JD-Core Version:    0.7.0.1
 */