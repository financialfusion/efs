package com.ffusion.tasks.util;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.CountryDefns;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCountryList
  extends ExtendedBaseTask
{
  private static final String dB = "CountryList";
  
  public GetCountryList()
  {
    this.collectionSessionName = "CountryList";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    try
    {
      if (localSecureUser == null) {
        localHashMap.put("LOCALE", (Locale)localHttpSession.getAttribute("java.util.Locale"));
      }
      CountryDefns localCountryDefns = Util.getCountryList(localSecureUser, localHashMap);
      if (localCountryDefns == null) {
        localCountryDefns = new CountryDefns();
      }
      localHttpSession.setAttribute(this.collectionSessionName, localCountryDefns);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetCountryList
 * JD-Core Version:    0.7.0.1
 */