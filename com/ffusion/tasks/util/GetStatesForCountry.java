package com.ffusion.tasks.util;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.StateProvinceDefn;
import com.ffusion.beans.util.StateProvinceDefns;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetStatesForCountry
  extends BaseTask
{
  private String Q2 = null;
  private ArrayList Q1 = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.Q1 = new ArrayList();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    try
    {
      if (localSecureUser == null) {
        localHashMap.put("LOCALE", (Locale)localHttpSession.getAttribute("java.util.Locale"));
      }
      StateProvinceDefns localStateProvinceDefns = Util.getStatesForCountry(localSecureUser, this.Q2, localHashMap);
      if (localStateProvinceDefns == null) {
        localStateProvinceDefns = new StateProvinceDefns();
      }
      Iterator localIterator = localStateProvinceDefns.iterator();
      while (localIterator.hasNext())
      {
        StateProvinceDefn localStateProvinceDefn = (StateProvinceDefn)localIterator.next();
        this.Q1.add(localStateProvinceDefn);
      }
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
      this.Q2 = "USA";
    } else {
      this.Q2 = paramString;
    }
  }
  
  public String getCountryCode()
  {
    return this.Q2;
  }
  
  public ArrayList getStateList()
  {
    return this.Q1;
  }
  
  public boolean getIfStatesExists()
  {
    return (this.Q1 != null) && (this.Q1.size() > 0);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetStatesForCountry
 * JD-Core Version:    0.7.0.1
 */