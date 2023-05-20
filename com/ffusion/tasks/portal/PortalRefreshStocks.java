package com.ffusion.tasks.portal;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.portal.PortalItems;
import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.StockIndexes;
import com.ffusion.beans.portal.Stocks;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Portal;
import com.ffusion.services.PortalData;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PortalRefreshStocks
  extends BaseTask
  implements Task
{
  private static final int mB = 10;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PortalData localPortalData = (PortalData)localHttpSession.getAttribute("PortalData");
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    Stocks localStocks1 = (Stocks)localHttpSession.getAttribute("StockSettings");
    if (localLocale == null)
    {
      this.error = 9015;
      str = this.taskErrorURL;
    }
    else if (localStocks1 == null)
    {
      this.error = 9027;
      str = this.taskErrorURL;
    }
    else
    {
      PortalItems localPortalItems = (PortalItems)localHttpSession.getAttribute("PortalItems");
      StockIndexes localStockIndexes = (StockIndexes)localHttpSession.getAttribute("StockIndexSettings");
      if (localPortalItems == null)
      {
        this.error = 9009;
        str = this.taskErrorURL;
      }
      else if (localStockIndexes == null)
      {
        this.error = 9028;
        str = this.taskErrorURL;
      }
      else
      {
        Stocks localStocks2 = null;
        if (localStocks1.size() > 0) {
          try
          {
            HashMap localHashMap1 = null;
            if (localPortalData != null)
            {
              localHashMap1 = new HashMap(1);
              localHashMap1.put("SERVICE", localPortalData);
            }
            localObject = (SecureUser)localHttpSession.getAttribute("SecureUser");
            localStocks2 = Portal.getStocks((SecureUser)localObject, localStocks1, localHashMap1);
          }
          catch (CSILException localCSILException1)
          {
            this.error = MapError.mapError(localCSILException1);
            str = this.serviceErrorURL;
          }
        }
        if (localStocks2 == null) {
          localStocks2 = new Stocks();
        }
        localStocks2.setLocale(localLocale);
        localHttpSession.setAttribute("Stocks", localStocks2);
        Stocks localStocks3 = jdMethod_try(localStocks1);
        localHttpSession.setAttribute("StocksEdit", localStocks3);
        Object localObject = null;
        if (localPortalItems.contains("STOCK_INDEXES")) {
          try
          {
            HashMap localHashMap2 = null;
            if (localPortalData != null)
            {
              localHashMap2 = new HashMap(1);
              localHashMap2.put("SERVICE", localPortalData);
            }
            SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
            localObject = Portal.getStockIndexes(localSecureUser, localStockIndexes, localHashMap2);
          }
          catch (CSILException localCSILException2)
          {
            this.error = MapError.mapError(localCSILException2);
            str = this.serviceErrorURL;
          }
        }
        if (localObject == null) {
          localObject = new StockIndexes();
        }
        localHttpSession.setAttribute("StockIndexes", localObject);
      }
    }
    return str;
  }
  
  private Stocks jdMethod_try(Stocks paramStocks)
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.PortalRefreshStocks
 * JD-Core Version:    0.7.0.1
 */