package com.ffusion.tasks.util;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.StateProvinceDefn;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetStateProvinceDefnForState
  extends ExtendedBaseTask
{
  private String dG = null;
  private String dH = null;
  private StateProvinceDefn dF = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      this.dF = Util.getStateProvinceDefnForState(localSecureUser, this.dH, this.dG, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  public void setCountryCode(String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      this.dG = "USA";
    } else {
      this.dG = paramString;
    }
  }
  
  public void setStateCode(String paramString)
  {
    this.dH = paramString;
  }
  
  public StateProvinceDefn getStateProvinceDefn()
  {
    return this.dF;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetStateProvinceDefnForState
 * JD-Core Version:    0.7.0.1
 */