package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Businesses;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Business;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPendingTransactionBusinessList
  extends BaseTask
  implements BusinessTask
{
  private String hz = "PendingTransactionBusinesses";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Businesses localBusinesses = null;
    HashMap localHashMap = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localBusinesses = Business.getPendingTransactionBusinessList(localSecureUser, localHashMap);
      localHttpSession.setAttribute(this.hz, localBusinesses);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.hz = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.hz;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetPendingTransactionBusinessList
 * JD-Core Version:    0.7.0.1
 */