package com.ffusion.tasks.util;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCodeForBankLookupCountryName
  extends BaseTask
{
  private String Ra = null;
  private String Rb = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      this.Rb = Util.getCodeForBankLookupCountryName(localSecureUser, this.Ra, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  public void setCountryName(String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      this.Ra = "UNITED STATES";
    } else {
      this.Ra = paramString;
    }
  }
  
  public String getCountryCode()
  {
    if (this.Rb == null) {
      this.Rb = "";
    }
    return this.Rb;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetCodeForBankLookupCountryName
 * JD-Core Version:    0.7.0.1
 */