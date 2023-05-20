package com.ffusion.services;

import com.ffusion.beans.portal.Forecasts;
import com.ffusion.beans.portal.News;
import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.StockIndexes;
import com.ffusion.beans.portal.StockSymbols;
import com.ffusion.beans.portal.Stocks;
import java.io.Serializable;

public abstract interface PortalData
  extends Serializable
{
  public abstract void setInitURL(String paramString);
  
  public abstract Stock getStock(String paramString);
  
  public abstract Stock getStock(String paramString, boolean paramBoolean);
  
  public abstract Stocks getStocks(Stocks paramStocks);
  
  public abstract StockIndexes getStockIndexes(StockIndexes paramStockIndexes);
  
  public abstract StockSymbols getStockSymbols(String paramString);
  
  public abstract Forecasts getForecasts(Forecasts paramForecasts);
  
  public abstract News getNews(News paramNews);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.PortalData
 * JD-Core Version:    0.7.0.1
 */