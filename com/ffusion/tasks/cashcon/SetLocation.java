package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.CashConCompanies;
import com.ffusion.beans.cashcon.Location;
import com.ffusion.beans.cashcon.LocationSearchResult;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CashCon;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetLocation
  extends BaseTask
  implements Task
{
  protected String _bpwid;
  protected String _locationsName = "Locations";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    LocationSearchResults localLocationSearchResults = (LocationSearchResults)localHttpSession.getAttribute(this._locationsName);
    if (localLocationSearchResults != null)
    {
      LocationSearchResult localLocationSearchResult = localLocationSearchResults.getByBPWID(this._bpwid);
      if (localLocationSearchResult != null)
      {
        try
        {
          HashMap localHashMap = new HashMap();
          CashConCompanies localCashConCompanies = (CashConCompanies)localHttpSession.getAttribute("CashConCompanies");
          localHashMap.put("CashConCompanies", localCashConCompanies);
          Location localLocation = CashCon.getLocation(localSecureUser, localLocationSearchResult.getLocationBPWID(), localHashMap);
          localHttpSession.setAttribute("Location", localLocation);
          str = this.successURL;
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
      }
      else
      {
        this.error = 24005;
        str = this.taskErrorURL;
      }
    }
    else
    {
      this.error = 24006;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public final void setBPWID(String paramString)
  {
    this._bpwid = paramString;
  }
  
  public final void setCollectionName(String paramString)
  {
    this._locationsName = paramString;
  }
  
  public final String getCollectionName()
  {
    return this._locationsName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.SetLocation
 * JD-Core Version:    0.7.0.1
 */