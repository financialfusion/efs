package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
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

public class ReleaseWires
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected boolean processFlag = false;
  protected String validate = null;
  
  public ReleaseWires()
  {
    this.beanSessionName = "WiresRelease";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    WireTransfers localWireTransfers = (WireTransfers)localHttpSession.getAttribute(this.beanSessionName);
    if (localWireTransfers == null)
    {
      this.error = 12005;
      return this.taskErrorURL;
    }
    if (validateInput(localSecureUser, localWireTransfers) == true)
    {
      if (this.processFlag == true)
      {
        this.processFlag = false;
        try
        {
          Wire.releaseWires(localSecureUser, localWireTransfers, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          return this.serviceErrorURL;
        }
      }
    }
    else {
      return this.taskErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(SecureUser paramSecureUser, WireTransfers paramWireTransfers)
  {
    if (this.validate == null) {
      return true;
    }
    try
    {
      Wire.confirmWiresLimits(paramSecureUser, paramWireTransfers, new HashMap());
    }
    catch (CSILException localCSILException)
    {
      this.error = 22000;
      return false;
    }
    this.validate = null;
    return true;
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.validate = paramString.toUpperCase();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.ReleaseWires
 * JD-Core Version:    0.7.0.1
 */