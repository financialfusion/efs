package com.ffusion.tasks.portal;

import com.ffusion.beans.portal.Forecasts;
import com.ffusion.beans.portal.GeographicUnits;
import com.ffusion.beans.portal.PortalItems;
import com.ffusion.csil.core.Portal;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCountryCities
  extends EditPortal
{
  public static final String COUNTRY_CITIES_FOUND = "COUNTRY_CITIES_FOUND";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ServletContext localServletContext = paramHttpServlet.getServletContext();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if (localLocale == null)
    {
      this.error = 9015;
      return this.taskErrorURL;
    }
    Forecasts localForecasts = (Forecasts)localHttpSession.getAttribute("ForecastsEdit");
    PortalItems localPortalItems = (PortalItems)localHttpSession.getAttribute("PortalItems");
    GeographicUnits localGeographicUnits1 = (GeographicUnits)localHttpSession.getAttribute("ForecastCountries");
    HashMap localHashMap = (HashMap)localServletContext.getAttribute("ForecastCountryCityList");
    if (localForecasts == null)
    {
      this.error = 9005;
      str1 = this.taskErrorURL;
    }
    else if (localPortalItems == null)
    {
      this.error = 9009;
      str1 = this.taskErrorURL;
    }
    else if (localGeographicUnits1 == null)
    {
      this.error = 9011;
      str1 = this.taskErrorURL;
    }
    else if (localHashMap == null)
    {
      this.error = 9012;
      str1 = this.taskErrorURL;
    }
    else
    {
      localHttpSession.setAttribute("ForecastSettingsTemp", localForecasts);
      localForecasts = new Forecasts();
      localHttpSession.setAttribute("ForecastsEdit", localForecasts);
      String str2 = localGeographicUnits1.getSelected();
      if (!str2.equals("UN"))
      {
        GeographicUnits localGeographicUnits2 = (GeographicUnits)localHashMap.get(str2);
        if ((localGeographicUnits2 != null) && (localGeographicUnits2.size() != 0))
        {
          localGeographicUnits2.setSelected("");
          localHttpSession.setAttribute("ForecastCountryCities", Portal.getForecastCities(localGeographicUnits2, localLocale));
          localHttpSession.setAttribute("COUNTRY_CITIES_FOUND", "true");
        }
        else
        {
          localHttpSession.setAttribute("COUNTRY_CITIES_FOUND", "false");
        }
      }
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.GetCountryCities
 * JD-Core Version:    0.7.0.1
 */