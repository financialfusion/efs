package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.wiretransfers.WireTransfers;
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

public class GetApprovalWireTransfers
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected boolean viewAll = false;
  protected String wireDestination;
  protected String approvalState;
  
  public GetApprovalWireTransfers()
  {
    this.beanSessionName = "PendingApprovalWireTransfers";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireTransfers localWireTransfers = null;
    HashMap localHashMap = new HashMap();
    if (this.viewAll == true) {
      localHashMap.put("VIEW", "ALL");
    }
    if ((this.wireDestination != null) && (this.wireDestination.length() > 0)) {
      localHashMap.put("DESTINATION", this.wireDestination);
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("WiresAccounts");
    localHashMap.put("ACCOUNTS", localAccounts);
    String[] arrayOfString = { "Pending", "Rejected" };
    if ((this.approvalState != null) && (this.approvalState.length() > 0))
    {
      arrayOfString = new String[1];
      arrayOfString[0] = this.approvalState;
    }
    try
    {
      localWireTransfers = Wire.getApprovalWires(localSecureUser, arrayOfString, localHashMap);
      localHttpSession.setAttribute(this.beanSessionName, localWireTransfers);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setViewAll(String paramString)
  {
    this.viewAll = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setApprovalState(String paramString)
  {
    this.approvalState = paramString;
  }
  
  public void setType(String paramString)
  {
    this.wireDestination = paramString;
  }
  
  public String getType()
  {
    return this.wireDestination;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetApprovalWireTransfers
 * JD-Core Version:    0.7.0.1
 */