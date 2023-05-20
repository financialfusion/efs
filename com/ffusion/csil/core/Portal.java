package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.portal.Forecasts;
import com.ffusion.beans.portal.GeographicUnit;
import com.ffusion.beans.portal.GeographicUnits;
import com.ffusion.beans.portal.News;
import com.ffusion.beans.portal.NewsHeadline;
import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.StockIndexes;
import com.ffusion.beans.portal.StockSymbols;
import com.ffusion.beans.portal.Stocks;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.PerfLog;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

public class Portal
  extends Initialize
{
  private static Entitlement axD = new Entitlement("Portal", null, null);
  private static final String axF = "com.ffusion.util.logging.audit.portal";
  private static final String axE = "AuditEntFault_1";
  public static final String DISPLAY_PROPERTIES_FILE = "portal_names";
  public static final String DISPLAY_PROPERTY_PREFIX_COUNTRY = "Country_";
  public static final String DISPLAY_PROPERTY_PREFIX_STATE = "State_";
  public static final String DISPLAY_PROPERTY_PREFIX_CITY = "City_";
  public static final String DISPLAY_PROPERTY_FULL_CITY_FORMAT = "CityFullDisplayFormat";
  public static final String DISPLAY_PROPERTY_FULL_CITY_NO_STATE_FORMAT = "CityNoStateFullDisplayFormat";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Portal.initialize");
    com.ffusion.csil.handlers.Portal.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.Portal.getService();
  }
  
  public static Stock getStock(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Portal.GetStock";
    if (b(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      Stock localStock = com.ffusion.csil.handlers.Portal.getStock(paramSecureUser, paramString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localStock;
    }
    throw new CSILException(str, 20001);
  }
  
  public static Stock getStock(SecureUser paramSecureUser, String paramString, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Portal.GetStock";
    if (b(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      Stock localStock = com.ffusion.csil.handlers.Portal.getStock(paramSecureUser, paramString, paramBoolean, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localStock;
    }
    throw new CSILException(str, 20001);
  }
  
  public static Stocks getStocks(SecureUser paramSecureUser, Stocks paramStocks, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Portal.GetStocks";
    if (b(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      Stocks localStocks = com.ffusion.csil.handlers.Portal.getStocks(paramSecureUser, paramStocks, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localStocks;
    }
    throw new CSILException(str, 20001);
  }
  
  public static StockIndexes getStockIndexes(SecureUser paramSecureUser, StockIndexes paramStockIndexes, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Portal.GetStockIndexes";
    if (b(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      StockIndexes localStockIndexes = com.ffusion.csil.handlers.Portal.getStockIndexes(paramSecureUser, paramStockIndexes, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localStockIndexes;
    }
    throw new CSILException(str, 20001);
  }
  
  public static StockSymbols getStockSymbols(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Portal.GetStockSymbols";
    if (b(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      StockSymbols localStockSymbols = com.ffusion.csil.handlers.Portal.getStockSymbols(paramSecureUser, paramString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localStockSymbols;
    }
    throw new CSILException(str, 20001);
  }
  
  public static Forecasts getForecasts(SecureUser paramSecureUser, Forecasts paramForecasts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Portal.GetForecasts";
    if (b(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      Forecasts localForecasts = com.ffusion.csil.handlers.Portal.getForecasts(paramSecureUser, paramForecasts, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localForecasts;
    }
    throw new CSILException(str, 20001);
  }
  
  public static News getNews(SecureUser paramSecureUser, News paramNews, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Portal.GetNews";
    if (b(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      News localNews = com.ffusion.csil.handlers.Portal.getNews(paramSecureUser, paramNews, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localNews;
    }
    throw new CSILException(str, 20001);
  }
  
  public static ArrayList getAll(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Portal.GetAll";
    if (b(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      ArrayList localArrayList = com.ffusion.csil.handlers.Portal.getAll(paramSecureUser, paramArrayList, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localArrayList;
    }
    throw new CSILException(str, 20001);
  }
  
  public static ArrayList getHeadlines(SecureUser paramSecureUser, Stock paramStock, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PortalContentProvider.GetHeadlines";
    if (b(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      ArrayList localArrayList = com.ffusion.csil.handlers.Portal.getHeadlines(paramSecureUser, paramStock, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localArrayList;
    }
    throw new CSILException(str, 20001);
  }
  
  public static NewsHeadline getNews(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PortalContentProvider.GetNews";
    if (b(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      NewsHeadline localNewsHeadline = com.ffusion.csil.handlers.Portal.getNews(paramSecureUser, paramString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localNewsHeadline;
    }
    throw new CSILException(str, 20001);
  }
  
  private static boolean b(SecureUser paramSecureUser)
    throws CSILException
  {
    boolean bool = Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axD);
    if (!bool)
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.portal", "AuditEntFault_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    }
    return bool;
  }
  
  public static GeographicUnits getForecastCountries(GeographicUnits paramGeographicUnits, Locale paramLocale)
  {
    return jdMethod_for(paramGeographicUnits, "Country_", paramLocale);
  }
  
  public static GeographicUnits getForecastStates(GeographicUnits paramGeographicUnits, Locale paramLocale)
  {
    return jdMethod_for(paramGeographicUnits, "State_", paramLocale);
  }
  
  public static GeographicUnits getForecastCities(GeographicUnits paramGeographicUnits, Locale paramLocale)
  {
    ResourceBundle localResourceBundle = null;
    try
    {
      localResourceBundle = ResourceUtil.getBundle("portal_names", paramLocale);
      if (localResourceBundle == null) {
        return paramGeographicUnits;
      }
    }
    catch (Exception localException)
    {
      return paramGeographicUnits;
    }
    GeographicUnits localGeographicUnits = new GeographicUnits();
    Iterator localIterator = paramGeographicUnits.iterator();
    while (localIterator.hasNext())
    {
      GeographicUnit localGeographicUnit1 = (GeographicUnit)localIterator.next();
      GeographicUnit localGeographicUnit2 = new GeographicUnit();
      String str = localGeographicUnit1.getAbbr();
      localGeographicUnit2.setAbbr(str);
      localGeographicUnit2.setName(jdMethod_for(localGeographicUnit1.getAbbr().replace(' ', '_'), "City_", localResourceBundle, localGeographicUnit1.getName()));
      localGeographicUnits.add(localGeographicUnit2);
    }
    return localGeographicUnits;
  }
  
  public static String getFullCityName(String paramString, Locale paramLocale)
  {
    String str1 = null;
    ResourceBundle localResourceBundle = null;
    try
    {
      localResourceBundle = ResourceUtil.getBundle("portal_names", paramLocale);
      if (localResourceBundle == null) {
        return paramString;
      }
    }
    catch (Exception localException1)
    {
      return paramString;
    }
    String str2 = jdMethod_for(paramString.replace(' ', '_'), "City_", localResourceBundle, paramString);
    if (str2.equals(paramString)) {
      return paramString;
    }
    String str3 = null;
    String str4 = null;
    String str5 = null;
    Object[] arrayOfObject = null;
    int i;
    if (paramString.endsWith(" US"))
    {
      i = paramString.lastIndexOf(" US");
      str3 = paramString.substring(i - 2, i);
      str4 = "US";
      str5 = "CityFullDisplayFormat";
      arrayOfObject = new Object[] { paramString, str3, str4, str2, jdMethod_for(str3, "State_", localResourceBundle, str3), jdMethod_for(str4, "Country_", localResourceBundle, str4) };
    }
    else
    {
      i = paramString.lastIndexOf(' ');
      str4 = paramString.substring(i + 1);
      str5 = "CityNoStateFullDisplayFormat";
      arrayOfObject = new Object[] { paramString, str4, str2, jdMethod_for(str4, "Country_", localResourceBundle, str4) };
    }
    try
    {
      String str6 = localResourceBundle.getString(str5);
      str1 = MessageFormat.format(str6, arrayOfObject);
    }
    catch (Exception localException2)
    {
      return paramString;
    }
    return str1;
  }
  
  private static GeographicUnits jdMethod_for(GeographicUnits paramGeographicUnits, String paramString, Locale paramLocale)
  {
    ResourceBundle localResourceBundle = null;
    try
    {
      localResourceBundle = ResourceUtil.getBundle("portal_names", paramLocale);
      if (localResourceBundle == null) {
        return paramGeographicUnits;
      }
    }
    catch (Exception localException)
    {
      return paramGeographicUnits;
    }
    GeographicUnits localGeographicUnits = new GeographicUnits();
    Iterator localIterator = paramGeographicUnits.iterator();
    while (localIterator.hasNext())
    {
      GeographicUnit localGeographicUnit1 = (GeographicUnit)localIterator.next();
      GeographicUnit localGeographicUnit2 = new GeographicUnit();
      localGeographicUnit2.setAbbr(localGeographicUnit1.getAbbr());
      localGeographicUnit2.setName(jdMethod_for(localGeographicUnit1.getAbbr(), paramString, localResourceBundle, localGeographicUnit1.getName()));
      localGeographicUnits.add(localGeographicUnit2);
    }
    return localGeographicUnits;
  }
  
  private static String jdMethod_for(String paramString1, String paramString2, ResourceBundle paramResourceBundle, String paramString3)
  {
    String str = null;
    try
    {
      str = paramResourceBundle.getString(paramString2 + paramString1);
    }
    catch (Exception localException)
    {
      str = paramString3;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Portal
 * JD-Core Version:    0.7.0.1
 */