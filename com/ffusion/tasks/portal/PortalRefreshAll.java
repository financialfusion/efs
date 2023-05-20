package com.ffusion.tasks.portal;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.portal.Forecast;
import com.ffusion.beans.portal.ForecastDay;
import com.ffusion.beans.portal.Forecasts;
import com.ffusion.beans.portal.GeographicUnits;
import com.ffusion.beans.portal.News;
import com.ffusion.beans.portal.NewsHeadlines;
import com.ffusion.beans.portal.PortalItems;
import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.StockIndexes;
import com.ffusion.beans.portal.Stocks;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Portal;
import com.ffusion.services.PortalData3;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
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

public class PortalRefreshAll
  extends BaseTask
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PortalData3 localPortalData3 = (PortalData3)localHttpSession.getAttribute("PortalData");
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if (localLocale == null)
    {
      this.error = 9015;
      return this.taskErrorURL;
    }
    News localNews1 = (News)localHttpSession.getAttribute("NewsSettings");
    Stocks localStocks1 = (Stocks)localHttpSession.getAttribute("StockSettings");
    StockIndexes localStockIndexes1 = (StockIndexes)localHttpSession.getAttribute("StockIndexSettings");
    Forecasts localForecasts1 = (Forecasts)localHttpSession.getAttribute("ForecastSettings");
    Stocks localStocks2 = null;
    Forecasts localForecasts2 = null;
    News localNews2 = null;
    StockIndexes localStockIndexes2 = null;
    ArrayList localArrayList1 = new ArrayList(4);
    ArrayList localArrayList2 = new ArrayList(4);
    PortalItems localPortalItems = (PortalItems)localHttpSession.getAttribute("PortalItems");
    if (localPortalItems == null)
    {
      this.error = 9009;
      return this.taskErrorURL;
    }
    if ((localPortalItems.contains("NEWSFRONTPAGE")) || (localPortalItems.contains("NEWSBUSINESS")) || (localPortalItems.contains("NEWSSPORTS")))
    {
      if (localNews1 == null)
      {
        this.error = 9026;
        return this.taskErrorURL;
      }
      if (localNews1.size() > 0)
      {
        localNews2 = new News();
        localArrayList1.add(localNews2);
        localArrayList2.add(localNews1);
      }
    }
    if (localPortalItems.contains("STOCKS"))
    {
      if (localStocks1 == null)
      {
        this.error = 9027;
        return this.taskErrorURL;
      }
      if (localStocks1.size() > 0)
      {
        localStocks2 = new Stocks();
        localArrayList1.add(localStocks2);
        localArrayList2.add(localStocks1);
      }
    }
    if (localPortalItems.contains("STOCK_INDEXES"))
    {
      if (localStockIndexes1 == null)
      {
        this.error = 9028;
        return this.taskErrorURL;
      }
      if (localStockIndexes1.size() > 0)
      {
        localStockIndexes2 = new StockIndexes();
        localArrayList1.add(localStockIndexes2);
        localArrayList2.add(localStockIndexes1);
      }
    }
    if (localPortalItems.contains("FORECASTS"))
    {
      if (localForecasts1 == null)
      {
        this.error = 9022;
        return this.taskErrorURL;
      }
      if (localForecasts1.size() > 0)
      {
        localForecasts2 = new Forecasts();
        localArrayList1.add(localForecasts2);
        localArrayList2.add(localForecasts1);
      }
    }
    if (localArrayList2.size() > 0)
    {
      HashMap localHashMap = new HashMap();
      if (localPortalData3 != null) {
        localHashMap.put("SERVICE", localPortalData3);
      }
      localHashMap.put("CONTENTS", localArrayList1);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      this.error = 0;
      try
      {
        localArrayList1 = Portal.getAll(localSecureUser, localArrayList2, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
    }
    if (this.error != 0) {
      return this.serviceErrorURL;
    }
    if (localPortalItems.contains("FORECASTS")) {
      this.error = jdMethod_for(localForecasts2, localForecasts1, localHttpSession, paramHttpServlet.getServletContext(), localLocale);
    }
    if ((this.error == 0) && ((localPortalItems.contains("NEWSFRONTPAGE")) || (localPortalItems.contains("NEWSBUSINESS")) || (localPortalItems.contains("NEWSSPORTS")))) {
      this.error = jdMethod_for(localNews2, localHttpSession);
    }
    if ((this.error == 0) && (localPortalItems.contains("STOCKS"))) {
      this.error = jdMethod_for(localStocks2, localStocks1, localHttpSession);
    }
    if ((this.error == 0) && (localPortalItems.contains("STOCK_INDEXES"))) {
      this.error = jdMethod_for(localStockIndexes2, localHttpSession);
    }
    if (this.error != 0) {
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  private int jdMethod_for(News paramNews, HttpSession paramHttpSession)
  {
    if (paramNews != null)
    {
      Iterator localIterator = paramNews.iterator();
      while (localIterator.hasNext())
      {
        NewsHeadlines localNewsHeadlines = (NewsHeadlines)localIterator.next();
        String str = localNewsHeadlines.getCategory();
        if (str.equals("FRONTPAGE")) {
          paramHttpSession.setAttribute("NewsFrontpage", localNewsHeadlines);
        } else if (str.equals("BUSINESS")) {
          paramHttpSession.setAttribute("NewsBusiness", localNewsHeadlines);
        } else if (str.equals("SPORTS")) {
          paramHttpSession.setAttribute("NewsSports", localNewsHeadlines);
        }
      }
    }
    return 0;
  }
  
  private int jdMethod_for(Stocks paramStocks1, Stocks paramStocks2, HttpSession paramHttpSession)
  {
    if (paramStocks1 == null) {
      paramStocks1 = new Stocks();
    }
    paramStocks1.setLocale((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    paramHttpSession.setAttribute("Stocks", paramStocks1);
    Stocks localStocks = jdMethod_new(paramStocks2);
    paramHttpSession.setAttribute("StocksEdit", localStocks);
    return 0;
  }
  
  private Stocks jdMethod_new(Stocks paramStocks)
  {
    Stocks localStocks = new Stocks();
    Stock localStock1 = 0;
    Iterator localIterator = paramStocks.iterator();
    Stock localStock3;
    while (localIterator.hasNext())
    {
      localStock2 = (Stock)localIterator.next();
      localStock3 = new Stock();
      localStock3.setSymbol(localStock2.getSymbol());
      localStock3.setShares(localStock2.getShares());
      localStock3.setPurchasePrice(localStock2.getPurchasePrice());
      localStock3.setCounter(localStock1);
      localStocks.add(localStock3);
      localStock1++;
    }
    for (Stock localStock2 = localStock1; localStock2 < 10; localStock2++)
    {
      localStock3 = new Stock();
      localStock3.setCounter(localStock2);
      localStocks.add(localStock3);
    }
    return localStocks;
  }
  
  private int jdMethod_for(StockIndexes paramStockIndexes, HttpSession paramHttpSession)
  {
    if (paramStockIndexes == null) {
      paramStockIndexes = new StockIndexes();
    }
    paramHttpSession.setAttribute("StockIndexes", paramStockIndexes);
    return 0;
  }
  
  private int jdMethod_for(Forecasts paramForecasts1, Forecasts paramForecasts2, HttpSession paramHttpSession, ServletContext paramServletContext, Locale paramLocale)
  {
    if (paramForecasts1 == null) {
      paramForecasts1 = new Forecasts();
    }
    jdMethod_for(paramForecasts2, paramForecasts1);
    String[] arrayOfString = (String[])paramHttpSession.getAttribute("Days");
    if (arrayOfString == null) {
      return 9023;
    }
    jdMethod_for(paramForecasts1, arrayOfString);
    paramHttpSession.setAttribute("Forecasts", paramForecasts1);
    Forecasts localForecasts = (Forecasts)paramForecasts2.clone();
    paramHttpSession.setAttribute("ForecastSettingsTemp", localForecasts);
    GeographicUnits localGeographicUnits1 = (GeographicUnits)paramServletContext.getAttribute("ForecastStatesContext");
    if (localGeographicUnits1 == null) {
      return 9024;
    }
    paramHttpSession.setAttribute("ForecastStates", Portal.getForecastStates(localGeographicUnits1, paramLocale));
    GeographicUnits localGeographicUnits2 = (GeographicUnits)paramServletContext.getAttribute("ForecastCountriesContext");
    if (localGeographicUnits2 == null) {
      return 9025;
    }
    paramHttpSession.setAttribute("ForecastCountries", Portal.getForecastCountries(localGeographicUnits2, paramLocale));
    return 0;
  }
  
  private void jdMethod_for(Forecasts paramForecasts1, Forecasts paramForecasts2)
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
  
  private void jdMethod_for(Forecasts paramForecasts, String[] paramArrayOfString)
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
 * Qualified Name:     com.ffusion.tasks.portal.PortalRefreshAll
 * JD-Core Version:    0.7.0.1
 */