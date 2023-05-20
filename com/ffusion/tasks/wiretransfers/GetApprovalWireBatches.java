package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireBatches;
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

public class GetApprovalWireBatches
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected boolean viewAll = false;
  protected String batchDestination;
  protected String approvalState;
  
  public GetApprovalWireBatches()
  {
    this.beanSessionName = "PendingApprovalWireBatches";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireBatches localWireBatches = null;
    HashMap localHashMap = new HashMap();
    if (this.viewAll == true) {
      localHashMap.put("VIEW", "ALL");
    }
    if ((this.batchDestination != null) && (this.batchDestination.length() > 0)) {
      localHashMap.put("DESTINATION", this.batchDestination);
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    String[] arrayOfString = { "Pending", "Rejected" };
    if ((this.approvalState != null) && (this.approvalState.length() > 0))
    {
      arrayOfString = new String[1];
      arrayOfString[0] = this.approvalState;
    }
    try
    {
      localWireBatches = Wire.getApprovalWireBatches(localSecureUser, arrayOfString, localHashMap);
      localHttpSession.setAttribute(this.beanSessionName, localWireBatches);
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
    this.batchDestination = paramString;
  }
  
  public String getType()
  {
    return this.batchDestination;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetApprovalWireBatches
 * JD-Core Version:    0.7.0.1
 */