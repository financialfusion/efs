package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
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

public class GetWireAmounts
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected WireTransfer clonedWire = new WireTransfer();
  protected boolean bAdd = true;
  
  public GetWireAmounts()
  {
    this.beanSessionName = "WireTransfer";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    WireTransfer localWireTransfer = (WireTransfer)localHttpSession.getAttribute(this.beanSessionName);
    if (localWireTransfer == null)
    {
      this.error = 12005;
      return this.taskErrorURL;
    }
    try
    {
      this.clonedWire.set(localWireTransfer);
      Wire.getWireAmounts(localSecureUser, this.clonedWire, this.bAdd, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return str;
  }
  
  public void setAdd(String paramString)
  {
    this.bAdd = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAmount()
  {
    return this.clonedWire.getDisplayAmount();
  }
  
  public String getOrigAmount()
  {
    return this.clonedWire.getDisplayOrigAmount();
  }
  
  public String getPaymentAmount()
  {
    return this.clonedWire.getPaymentAmount();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetWireAmounts
 * JD-Core Version:    0.7.0.1
 */