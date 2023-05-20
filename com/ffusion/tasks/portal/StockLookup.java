package com.ffusion.tasks.portal;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.StockSymbol;
import com.ffusion.beans.portal.StockSymbols;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Portal;
import com.ffusion.services.PortalData;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StockLookup
  extends BaseTask
  implements Task
{
  public static final String STOCK_LOOKUP_SESSION = "StockLookup";
  public static final String NO_MATCH = "PortalNoMatch";
  public static final String PORTAL_STOCK_SYMBOLS = "PortalStockSymbols";
  private String mo = "";
  private String mp = "";
  private String mq = "";
  private String ml = "";
  private String mm = "";
  private String mn = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    this.mn = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      if (this.mp.equalsIgnoreCase("company"))
      {
        PortalData localPortalData = (PortalData)localHttpSession.getAttribute("PortalData");
        StockSymbols localStockSymbols = null;
        try
        {
          HashMap localHashMap = null;
          if (localPortalData != null)
          {
            localHashMap = new HashMap(1);
            localHashMap.put("SERVICE", localPortalData);
          }
          SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
          localStockSymbols = Portal.getStockSymbols(localSecureUser, this.mo, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          this.mn = this.serviceErrorURL;
        }
        if (this.error == 0) {
          if (localStockSymbols.size() == 0)
          {
            f(localHttpSession);
          }
          else if (localStockSymbols.size() == 1)
          {
            StockSymbol localStockSymbol = (StockSymbol)localStockSymbols.get(0);
            this.mo = localStockSymbol.getSymbol();
            g(localHttpSession);
          }
          else
          {
            localHttpSession.setAttribute("PortalStockSymbols", localStockSymbols);
            this.mn = this.mq;
          }
        }
      }
      else
      {
        g(localHttpSession);
      }
    }
    catch (Exception localException)
    {
      this.error = 9034;
      this.mn = this.taskErrorURL;
    }
    this.mp = "";
    return this.mn;
  }
  
  private void f(HttpSession paramHttpSession)
  {
    paramHttpSession.setAttribute("PortalNoMatch", this.mo);
    this.mn = this.mm;
  }
  
  private void g(HttpSession paramHttpSession)
  {
    PortalData localPortalData = (PortalData)paramHttpSession.getAttribute("PortalData");
    Stock localStock = null;
    ArrayList localArrayList = null;
    try
    {
      HashMap localHashMap = null;
      if (localPortalData != null)
      {
        localHashMap = new HashMap(1);
        localHashMap.put("SERVICE", localPortalData);
      }
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      localStock = Portal.getStock(localSecureUser, this.mo, true, localHashMap);
      localArrayList = Portal.getHeadlines(localSecureUser, localStock, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.mn = this.serviceErrorURL;
    }
    if (this.error == 0) {
      if (!localStock.getCompanyName().equals(""))
      {
        paramHttpSession.setAttribute("PortalStockNews", localArrayList);
        paramHttpSession.setAttribute("StockLookup", localStock);
        this.mn = this.ml;
      }
      else
      {
        f(paramHttpSession);
      }
    }
  }
  
  public String getSymbolCompany()
  {
    return this.mo;
  }
  
  public void setSymbolCompany(String paramString)
  {
    this.mo = paramString.toLowerCase();
  }
  
  public String getSymbolCompanyFlag()
  {
    return this.mp;
  }
  
  public void setSymbolCompanyFlag(String paramString)
  {
    this.mp = paramString;
  }
  
  public String getQuoteURL()
  {
    return this.ml;
  }
  
  public void setQuoteURL(String paramString)
  {
    this.ml = paramString;
  }
  
  public String getSymbolURL()
  {
    return this.mq;
  }
  
  public void setSymbolURL(String paramString)
  {
    this.mq = paramString;
  }
  
  public String getNoMatchURL()
  {
    return this.mm;
  }
  
  public void setNoMatchURL(String paramString)
  {
    this.mm = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.StockLookup
 * JD-Core Version:    0.7.0.1
 */