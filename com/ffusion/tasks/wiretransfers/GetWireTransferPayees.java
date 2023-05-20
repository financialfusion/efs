package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransferPayees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetWireTransferPayees
  extends BaseTask
  implements WireTaskDefines
{
  protected static boolean reload = false;
  protected String payeeName = "WireTransferPayees";
  protected String businessID = null;
  private String Dr = "REGULAR";
  private String Ds;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    WireTransferPayees localWireTransferPayees = (WireTransferPayees)localHttpSession.getAttribute(this.payeeName);
    if (reload)
    {
      localWireTransferPayees = null;
      localHttpSession.removeAttribute(this.payeeName);
      reload = false;
    }
    if (localWireTransferPayees == null)
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        WireTransferPayee localWireTransferPayee = new WireTransferPayee();
        if (localSecureUser.getUserType() == 2) {
          localWireTransferPayee.setCustomerID(this.businessID);
        } else {
          localWireTransferPayee.setCustomerID(localSecureUser.getBusinessID());
        }
        localWireTransferPayee.setPayeeDestination(this.Dr);
        if (localSecureUser.getUserType() != 2) {
          this.Ds = String.valueOf(localSecureUser.getProfileID());
        }
        localWireTransferPayee.setSubmittedBy(this.Ds);
        localWireTransferPayees = Wire.getWireTransferPayees(localSecureUser, localWireTransferPayee, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        return this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        Collections.sort(localWireTransferPayees);
        localHttpSession.setAttribute(this.payeeName, localWireTransferPayees);
      }
    }
    return str;
  }
  
  public void setReload(String paramString)
  {
    reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setPayeeName(String paramString)
  {
    this.payeeName = paramString;
  }
  
  public void setBusinessID(String paramString)
  {
    this.businessID = paramString;
  }
  
  public String getBusinessID()
  {
    return this.businessID;
  }
  
  public void setPayeeDestination(String paramString)
  {
    this.Dr = paramString;
  }
  
  public String getPayeeDestination()
  {
    return this.Dr;
  }
  
  public String getUserId()
  {
    return this.Ds;
  }
  
  public void setUserId(String paramString)
  {
    this.Ds = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetWireTransferPayees
 * JD-Core Version:    0.7.0.1
 */