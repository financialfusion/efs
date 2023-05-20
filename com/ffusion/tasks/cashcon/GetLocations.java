package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.LocationSearchCriteria;
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

public class GetLocations
  extends BaseTask
  implements Task
{
  protected LocationSearchCriteria _criteria = new LocationSearchCriteria();
  protected String _collectionName = "Locations";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if ((getDivisionID() == null) || (getDivisionID().length() == 0))
    {
      this.error = 24010;
      str = this.taskErrorURL;
    }
    if (this.error == 0)
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      LocationSearchResults localLocationSearchResults = null;
      try
      {
        localLocationSearchResults = CashCon.getLocations(localSecureUser, this._criteria, localHashMap);
        localHttpSession.setAttribute(this._collectionName, localLocationSearchResults);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setDivisionID(String paramString)
  {
    int i = -1;
    try
    {
      i = Integer.parseInt(paramString);
      if (i <= 0) {
        i = -1;
      }
    }
    catch (NumberFormatException localNumberFormatException) {}
    this._criteria.setDivisionID(i);
  }
  
  public String getDivisionID()
  {
    return this._criteria.getDivisionID() <= 0 ? "" : String.valueOf(this._criteria.getDivisionID());
  }
  
  public void setLocationName(String paramString)
  {
    this._criteria.setLocationName(paramString);
  }
  
  public String getLocationName()
  {
    return this._criteria.getLocationName();
  }
  
  public void setLocationID(String paramString)
  {
    this._criteria.setLocationID(paramString);
  }
  
  public String getLocationID()
  {
    return this._criteria.getLocationID();
  }
  
  public void setMaxResults(String paramString)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
    this._criteria.setMaxResults(i);
  }
  
  public String getMaxResults()
  {
    return String.valueOf(this._criteria.getMaxResults());
  }
  
  public void setCollectionName(String paramString)
  {
    this._collectionName = paramString;
  }
  
  public String getCollectionName()
  {
    return this._collectionName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.GetLocations
 * JD-Core Version:    0.7.0.1
 */