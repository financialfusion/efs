package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.portal.Forecasts;
import com.ffusion.beans.portal.News;
import com.ffusion.beans.portal.NewsHeadline;
import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.StockIndexes;
import com.ffusion.beans.portal.StockSymbols;
import com.ffusion.beans.portal.Stocks;
import com.ffusion.csil.CSILException;
import com.ffusion.services.PortalData3;
import com.ffusion.services.PortalData4;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;

public class Portal
  extends Initialize
{
  private static final String a6Z = "Portal";
  private static PortalData3 a61 = null;
  private static final String a60 = "INIT_URL";
  private static final String a6Y = "portal_content.xml";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Portal.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Portal", str1, 20107);
    a61 = (PortalData3)HandlerUtil.instantiateService(localHashMap, str1, 20107);
    try
    {
      String str2 = (String)localHashMap.get("INIT_URL");
      if ((str2 == null) || (str2.length() == 0)) {
        str2 = "portal_content.xml";
      }
      a61.initialize(str2);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(20107, localException);
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return a61;
  }
  
  public static Stock getStock(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Portal.getStock");
    return getPortalService(paramHashMap).getStock(paramString);
  }
  
  public static Stock getStock(SecureUser paramSecureUser, String paramString, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Portal.getStock");
    return getPortalService(paramHashMap).getStock(paramString, paramBoolean);
  }
  
  public static Stocks getStocks(SecureUser paramSecureUser, Stocks paramStocks, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Portal.getStocks");
    return getPortalService(paramHashMap).getStocks(paramStocks);
  }
  
  public static StockIndexes getStockIndexes(SecureUser paramSecureUser, StockIndexes paramStockIndexes, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Portal.getStockIndexes");
    return getPortalService(paramHashMap).getStockIndexes(paramStockIndexes);
  }
  
  public static StockSymbols getStockSymbols(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Portal.getStockSymbols");
    return getPortalService(paramHashMap).getStockSymbols(paramString);
  }
  
  public static Forecasts getForecasts(SecureUser paramSecureUser, Forecasts paramForecasts, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Portal.getForecasts");
    return getPortalService(paramHashMap).getForecasts(paramForecasts);
  }
  
  public static News getNews(SecureUser paramSecureUser, News paramNews, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Portal.getNews");
    return getPortalService(paramHashMap).getNews(paramNews);
  }
  
  public static ArrayList getAll(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Portal.getAll");
    checkExtra(paramHashMap);
    ArrayList localArrayList = (ArrayList)paramHashMap.get("CONTENTS");
    if (localArrayList == null)
    {
      debug("Missing required parameter: extra.'CONTENTS' - creating new ArrayList()");
      localArrayList = new ArrayList();
    }
    int i = getPortalService(paramHashMap).getAll(paramArrayList, localArrayList);
    if (i != 0) {
      throwing(-1009, i);
    }
    return localArrayList;
  }
  
  public static ArrayList getHeadlines(SecureUser paramSecureUser, Stock paramStock, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.PortalContentProvider.getHeadlines");
    return ((PortalData4)getPortalService(paramHashMap)).getHeadlines(paramStock);
  }
  
  public static NewsHeadline getNews(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.PortalContentProvider.getNews");
    NewsHeadline localNewsHeadline = null;
    try
    {
      localNewsHeadline = ((PortalData4)getPortalService(paramHashMap)).getNews(paramString);
    }
    catch (Throwable localThrowable)
    {
      throwing(-1009, new Exception(localThrowable.toString()));
    }
    return localNewsHeadline;
  }
  
  protected static PortalData3 getPortalService(HashMap paramHashMap)
    throws CSILException
  {
    PortalData3 localPortalData3 = null;
    try
    {
      if (paramHashMap != null) {
        localPortalData3 = (PortalData3)paramHashMap.get("SERVICE");
      }
      if (localPortalData3 == null) {
        localPortalData3 = a61;
      }
      if (localPortalData3 == null)
      {
        debug("No Portal service available");
        throwing(-1009, -1010);
      }
    }
    catch (Exception localException)
    {
      debug("No Portal service available:" + localException.getMessage());
      throwing(-1009, -1010);
    }
    return localPortalData3;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Portal
 * JD-Core Version:    0.7.0.1
 */