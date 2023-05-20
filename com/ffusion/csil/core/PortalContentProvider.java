package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.portal.NewsHeadline;
import com.ffusion.beans.portal.Stock;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;

public class PortalContentProvider
  extends Initialize
{
  private static Entitlement avq = new Entitlement("Portal", null, null);
  
  public static void initialize(HashMap paramHashMap)
  {
    debug("com.ffusion.csil.core.PortalContentProvider.initialize");
    com.ffusion.csil.handlers.PortalContentProvider.initialize(paramHashMap);
  }
  
  public static ArrayList getHeadlines(SecureUser paramSecureUser, Stock paramStock, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PortalContentProvider.GetHeadlines";
    if (jdMethod_null(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      ArrayList localArrayList = com.ffusion.csil.handlers.PortalContentProvider.getHeadlines(paramSecureUser, paramStock, paramHashMap);
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
    if (jdMethod_null(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      NewsHeadline localNewsHeadline = com.ffusion.csil.handlers.PortalContentProvider.getNews(paramSecureUser, paramString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localNewsHeadline;
    }
    throw new CSILException(str, 20001);
  }
  
  private static boolean jdMethod_null(SecureUser paramSecureUser)
    throws CSILException
  {
    boolean bool = Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), avq);
    if (!bool) {
      logEntitlementFault(paramSecureUser, "The user is not entitled to use portal services.", TrackingIDGenerator.GetNextID());
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.PortalContentProvider
 * JD-Core Version:    0.7.0.1
 */