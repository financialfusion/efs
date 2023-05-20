package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.portal.NewsHeadline;
import com.ffusion.beans.portal.Stock;
import com.ffusion.csil.CSILException;
import java.util.ArrayList;
import java.util.HashMap;

public class PortalContentProvider
  extends Initialize
{
  public static void initialize(HashMap paramHashMap)
  {
    debug("com.ffusion.csil.handlers.PortalContentProvider.initialize");
  }
  
  public static ArrayList getHeadlines(SecureUser paramSecureUser, Stock paramStock, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.PortalContentProvider.getHeadlines");
    checkExtra(paramHashMap);
    com.ffusion.services.PortalContentProvider localPortalContentProvider = (com.ffusion.services.PortalContentProvider)paramHashMap.get("SERVICE");
    if ((localPortalContentProvider == null) || (!(localPortalContentProvider instanceof com.ffusion.services.PortalContentProvider)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    return localPortalContentProvider.getHeadlines(paramStock);
  }
  
  public static NewsHeadline getNews(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.PortalContentProvider.getNews");
    checkExtra(paramHashMap);
    com.ffusion.services.PortalContentProvider localPortalContentProvider = (com.ffusion.services.PortalContentProvider)paramHashMap.get("SERVICE");
    if ((localPortalContentProvider == null) || (!(localPortalContentProvider instanceof com.ffusion.services.PortalContentProvider)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    NewsHeadline localNewsHeadline = null;
    try
    {
      localNewsHeadline = localPortalContentProvider.getNews(paramString);
    }
    catch (Throwable localThrowable)
    {
      throwing(-1009, new Exception(localThrowable.toString()));
    }
    return localNewsHeadline;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.PortalContentProvider
 * JD-Core Version:    0.7.0.1
 */