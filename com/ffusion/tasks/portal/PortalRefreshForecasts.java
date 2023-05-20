package com.ffusion.tasks.portal;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.portal.Forecast;
import com.ffusion.beans.portal.ForecastDay;
import com.ffusion.beans.portal.Forecasts;
import com.ffusion.beans.portal.GeographicUnits;
import com.ffusion.beans.portal.PortalItems;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Portal;
import com.ffusion.services.PortalData;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PortalRefreshForecasts
  extends BaseTask
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ServletContext localServletContext = paramHttpServlet.getServletContext();
    PortalData localPortalData = (PortalData)localHttpSession.getAttribute("PortalData");
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    Forecasts localForecasts1 = (Forecasts)localHttpSession.getAttribute("ForecastSettings");
    PortalItems localPortalItems = (PortalItems)localHttpSession.getAttribute("PortalItems");
    if (localLocale == null)
    {
      this.error = 9015;
      str = this.taskErrorURL;
    }
    else if (localForecasts1 == null)
    {
      this.error = 9022;
      str = this.taskErrorURL;
    }
    else if (localPortalItems == null)
    {
      this.error = 9009;
      str = this.taskErrorURL;
    }
    else
    {
      Forecasts localForecasts2 = null;
      Object localObject;
      if (localPortalItems.contains("FORECASTS")) {
        try
        {
          HashMap localHashMap = null;
          if (localPortalData != null)
          {
            localHashMap = new HashMap(1);
            localHashMap.put("SERVICE", localPortalData);
          }
          localObject = (SecureUser)localHttpSession.getAttribute("SecureUser");
          localForecasts2 = Portal.getForecasts((SecureUser)localObject, localForecasts1, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
      }
      if (this.error == 0)
      {
        if (localForecasts2 == null) {
          localForecasts2 = new Forecasts();
        }
        jdMethod_int(localForecasts1, localForecasts2);
        String[] arrayOfString = (String[])localHttpSession.getAttribute("Days");
        localObject = (GeographicUnits)localServletContext.getAttribute("ForecastStatesContext");
        GeographicUnits localGeographicUnits = (GeographicUnits)localServletContext.getAttribute("ForecastCountriesContext");
        if (arrayOfString == null)
        {
          this.error = 9023;
          str = this.taskErrorURL;
        }
        else if (localObject == null)
        {
          this.error = 9024;
          str = this.taskErrorURL;
        }
        else if (localGeographicUnits == null)
        {
          this.error = 9025;
          str = this.taskErrorURL;
        }
        else
        {
          jdMethod_int(localForecasts2, arrayOfString);
          localHttpSession.setAttribute("Forecasts", localForecasts2);
          Forecasts localForecasts3 = (Forecasts)localForecasts1.clone();
          localHttpSession.setAttribute("ForecastSettingsTemp", localForecasts3);
          localHttpSession.setAttribute("ForecastStates", Portal.getForecastStates((GeographicUnits)localObject, localLocale));
          localHttpSession.setAttribute("ForecastCountries", Portal.getForecastCountries(localGeographicUnits, localLocale));
        }
      }
    }
    return str;
  }
  
  private void jdMethod_int(Forecasts paramForecasts1, Forecasts paramForecasts2)
  {
    String str1 = paramForecasts1.getScale();
    String str2 = paramForecasts2.getScale();
    if (str2.equalsIgnoreCase(str1)) {
      return;
    }
    Iterator localIterator1 = paramForecasts2.iterator();
    while (localIterator1.hasNext())
    {
      Forecast localForecast = (Forecast)localIterator1.next();
      Iterator localIterator2 = localForecast.iterator();
      while (localIterator2.hasNext())
      {
        ForecastDay localForecastDay = (ForecastDay)localIterator2.next();
        String str3 = localForecastDay.getLow();
        int i = Integer.parseInt(str3);
        String str4 = localForecastDay.getHigh();
        int j = Integer.parseInt(str4);
        if (str1.equalsIgnoreCase("F"))
        {
          i = (int)(1.8F * i + 32.0F);
          j = (int)(1.8F * j + 32.0F);
        }
        else
        {
          i = (int)(0.5555556F * (i - 32));
          j = (int)(0.5555556F * (j - 32));
        }
        localForecastDay.setLow(Integer.toString(i));
        localForecastDay.setHigh(Integer.toString(j));
      }
    }
  }
  
  private void jdMethod_int(Forecasts paramForecasts, String[] paramArrayOfString)
  {
    Iterator localIterator1 = paramForecasts.iterator();
    while (localIterator1.hasNext())
    {
      Forecast localForecast = (Forecast)localIterator1.next();
      Iterator localIterator2 = localForecast.iterator();
      int i = 0;
      int j = 0;
      while (localIterator2.hasNext())
      {
        ForecastDay localForecastDay = (ForecastDay)localIterator2.next();
        if (i == 0) {
          j = getDayOfWeek(localForecastDay);
        }
        int k = j + i;
        if (k > 7) {
          k -= 7;
        }
        localForecastDay.setDay(paramArrayOfString[(k - 1)]);
        i++;
      }
    }
  }
  
  public int getDayOfWeek(ForecastDay paramForecastDay)
  {
    int i = Integer.parseInt(paramForecastDay.getDate());
    Calendar localCalendar = GregorianCalendar.getInstance();
    int j = localCalendar.get(5);
    int k = localCalendar.get(2);
    if (j != i)
    {
      if (j < i)
      {
        if (k == 0) {
          k = 11;
        } else {
          k--;
        }
        localCalendar.set(2, k);
      }
      localCalendar.set(5, i);
    }
    return localCalendar.get(7);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.PortalRefreshForecasts
 * JD-Core Version:    0.7.0.1
 */