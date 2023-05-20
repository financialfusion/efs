package com.ffusion.tasks.portal;

import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.StockIndex;
import com.ffusion.beans.portal.StockIndexes;
import com.ffusion.beans.portal.Stocks;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditStocks
  extends EditPortal
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Stocks localStocks = (Stocks)localHttpSession.getAttribute("StocksEdit");
    if (localStocks != null)
    {
      Collections.sort(localStocks);
      localObject = jdMethod_for(localStocks);
      localHttpSession.setAttribute("StockSettings", localObject);
      localStocks = jdMethod_int(localStocks);
      localHttpSession.setAttribute("StocksEdit", localStocks);
      if (!processShowItem(paramHttpServletRequest, "STOCKS"))
      {
        this.error = 9009;
        str = this.taskErrorURL;
      }
    }
    Object localObject = (StockIndexes)localHttpSession.getAttribute("StockIndexesEdit");
    if (localObject != null)
    {
      localHttpSession.setAttribute("StockIndexSettings", localObject);
      if (!processShowItem(paramHttpServletRequest, "STOCK_INDEXES"))
      {
        this.error = 9009;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  private Stocks jdMethod_for(Stocks paramStocks)
  {
    Stocks localStocks = new Stocks();
    Iterator localIterator = paramStocks.iterator();
    while (localIterator.hasNext())
    {
      Stock localStock1 = (Stock)localIterator.next();
      if (!localStock1.getSymbol().equals(""))
      {
        Stock localStock2 = new Stock();
        localStock2.set(localStock1);
        localStocks.add(localStock2);
      }
    }
    return localStocks;
  }
  
  private Stocks jdMethod_int(Stocks paramStocks)
  {
    Stocks localStocks1 = new Stocks();
    Stocks localStocks2 = new Stocks();
    Iterator localIterator = paramStocks.iterator();
    Stock localStock1;
    Stock localStock2;
    while (localIterator.hasNext())
    {
      localStock1 = (Stock)localIterator.next();
      if (!localStock1.getSymbol().equals(""))
      {
        localStock2 = new Stock();
        localStock2.set(localStock1);
        localStocks1.add(localStock2);
      }
      else
      {
        localStock2 = new Stock();
        localStock2.set(localStock1);
        localStocks2.add(localStock2);
      }
    }
    localIterator = localStocks2.iterator();
    while (localIterator.hasNext())
    {
      localStock1 = (Stock)localIterator.next();
      localStock2 = new Stock();
      localStock2.set(localStock1);
      localStocks1.add(localStock2);
    }
    localStocks1.trimToSize();
    return localStocks1;
  }
  
  public StockIndexes getNewStockIndexSettings(HttpServletRequest paramHttpServletRequest)
  {
    StockIndexes localStockIndexes = new StockIndexes();
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("Index");
    for (int i = 0; i < arrayOfString.length; i++)
    {
      StockIndex localStockIndex = new StockIndex();
      localStockIndex.setSymbol(arrayOfString[i]);
      localStockIndexes.add(localStockIndex);
    }
    return localStockIndexes;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.EditStocks
 * JD-Core Version:    0.7.0.1
 */