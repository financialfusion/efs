package com.ffusion.services.portal;

import com.ffusion.beans.portal.Forecast;
import com.ffusion.beans.portal.Forecasts;
import com.ffusion.beans.portal.News;
import com.ffusion.beans.portal.NewsHeadline;
import com.ffusion.beans.portal.NewsHeadlines;
import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.StockIndex;
import com.ffusion.beans.portal.StockIndexes;
import com.ffusion.beans.portal.StockSymbols;
import com.ffusion.beans.portal.Stocks;
import com.ffusion.services.PortalData3;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DataDemo
  implements PortalData3
{
  private String jdField_char = null;
  protected static HashMap m_InitHashMap;
  
  public int initialize(String paramString)
  {
    this.jdField_char = paramString;
    return 0;
  }
  
  public void setInitURL(String paramString)
  {
    initialize(paramString);
  }
  
  private Reader a()
  {
    StringReader localStringReader = null;
    try
    {
      if (m_InitHashMap == null) {
        m_InitHashMap = new HashMap();
      }
      String str = "";
      if ((m_InitHashMap != null) && (m_InitHashMap.get(this.jdField_char) != null))
      {
        str = (String)m_InitHashMap.get(this.jdField_char);
      }
      else
      {
        InputStream localInputStream = ResourceUtil.getResourceAsStream(this, this.jdField_char);
        if (localInputStream != null)
        {
          str = Strings.streamToString(localInputStream);
          m_InitHashMap.put(this.jdField_char, str);
        }
      }
      localStringReader = new StringReader(str);
    }
    catch (Throwable localThrowable) {}
    return localStringReader;
  }
  
  public Stock getStock(String paramString)
  {
    if (this.jdField_char == null) {
      return null;
    }
    Stocks localStocks = new Stocks();
    Reader localReader = a();
    try
    {
      if (localReader != null)
      {
        localStocks.startXMLParsing(localReader);
        for (int i = 0; i < localStocks.size(); i++)
        {
          Stock localStock = (Stock)localStocks.get(i);
          if (localStock.getSymbol().equalsIgnoreCase(paramString)) {
            return localStock;
          }
        }
        return new Stock();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("getStock error", localThrowable);
    }
    return null;
  }
  
  public Stock getStock(String paramString, boolean paramBoolean)
  {
    return getStock(paramString);
  }
  
  public News getNews(News paramNews)
  {
    if (this.jdField_char == null) {
      return null;
    }
    News localNews = new News();
    Reader localReader = a();
    try
    {
      if (localReader != null)
      {
        localNews.startXMLParsing(localReader);
        if (paramNews.size() > 0)
        {
          NewsHeadlines localNewsHeadlines1 = (NewsHeadlines)paramNews.get(0);
          Iterator localIterator;
          if (localNewsHeadlines1.size() > 0)
          {
            localObject1 = (NewsHeadline)localNewsHeadlines1.get(0);
            localObject2 = localNews.iterator();
            while (((Iterator)localObject2).hasNext())
            {
              localNewsHeadlines2 = (NewsHeadlines)((Iterator)localObject2).next();
              localIterator = localNewsHeadlines2.iterator();
              while (localIterator.hasNext())
              {
                NewsHeadline localNewsHeadline = (NewsHeadline)localIterator.next();
                if (localNewsHeadline.getStoryID().compareToIgnoreCase(((NewsHeadline)localObject1).getStoryID()) == 0)
                {
                  localNewsHeadlines1.set(0, localNewsHeadline);
                  return paramNews;
                }
              }
            }
            return null;
          }
          Object localObject1 = paramNews.iterator();
          Object localObject2 = null;
          NewsHeadlines localNewsHeadlines2 = null;
          while (((Iterator)localObject1).hasNext())
          {
            localObject2 = (NewsHeadlines)((Iterator)localObject1).next();
            localIterator = localNews.iterator();
            while (localIterator.hasNext())
            {
              localNewsHeadlines2 = (NewsHeadlines)localIterator.next();
              if (localNewsHeadlines2.getCategory().equals(((NewsHeadlines)localObject2).getCategory())) {
                localNewsHeadlines2.setMaxHeadlines(((NewsHeadlines)localObject2).getMaxHeadlinesValue());
              }
            }
          }
          localObject1 = localNews.iterator();
          NewsHeadlines localNewsHeadlines3 = null;
          while (((Iterator)localObject1).hasNext())
          {
            localNewsHeadlines3 = (NewsHeadlines)((Iterator)localObject1).next();
            int i = localNewsHeadlines3.size();
            int j = localNewsHeadlines3.getMaxHeadlinesValue();
            while (i > j)
            {
              localNewsHeadlines3.remove(i - 1);
              i--;
            }
          }
        }
        return localNews;
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("getNews error", localThrowable);
    }
    return null;
  }
  
  public Stocks getStocks(Stocks paramStocks)
  {
    if (this.jdField_char == null) {
      return null;
    }
    Stocks localStocks = new Stocks();
    Reader localReader = a();
    if (localReader != null) {
      try
      {
        localStocks.startXMLParsing(localReader);
        int i = localStocks.size();
        int j = paramStocks.size();
        for (int k = i - 1; k >= 0; k--)
        {
          int m = 1;
          for (int n = 0; n < j; n++)
          {
            Stock localStock1 = (Stock)localStocks.get(k);
            Stock localStock2 = (Stock)paramStocks.get(n);
            if (localStock1.getSymbol().equals(localStock2.getSymbol()))
            {
              m = 0;
              localStock1.setPurchasePrice(localStock2.getPurchasePrice());
              localStock1.setShares(localStock2.getShares());
              break;
            }
          }
          if (m != 0) {
            localStocks.remove(k);
          }
        }
        return localStocks;
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("getStocks error", localThrowable);
      }
    }
    return null;
  }
  
  public StockIndexes getStockIndexes(StockIndexes paramStockIndexes)
  {
    if (this.jdField_char == null) {
      return null;
    }
    StockIndexes localStockIndexes = new StockIndexes();
    Reader localReader = a();
    if (localReader != null) {
      try
      {
        localStockIndexes.startXMLParsing(localReader);
        int i = localStockIndexes.size();
        int j = paramStockIndexes.size();
        for (int k = i - 1; k >= 0; k--)
        {
          int m = 1;
          for (int n = 0; n < j; n++)
          {
            StockIndex localStockIndex1 = (StockIndex)localStockIndexes.get(k);
            StockIndex localStockIndex2 = (StockIndex)paramStockIndexes.get(n);
            if (localStockIndex1.getSymbol().equals(localStockIndex2.getSymbol()))
            {
              m = 0;
              break;
            }
          }
          if (m != 0) {
            localStockIndexes.remove(k);
          }
        }
        return localStockIndexes;
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("getStockIndexes error", localThrowable);
      }
    }
    return null;
  }
  
  public StockSymbols getStockSymbols(String paramString)
  {
    if (this.jdField_char == null) {
      return null;
    }
    StockSymbols localStockSymbols = new StockSymbols();
    localStockSymbols.setSearchName(paramString);
    if (paramString.compareTo("") == 0) {
      return localStockSymbols;
    }
    Reader localReader = a();
    if (localReader != null) {
      try
      {
        localStockSymbols.startXMLParsing(localReader);
        return localStockSymbols;
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("getStockSymbols error", localThrowable);
      }
    }
    return null;
  }
  
  public Forecasts getForecasts(Forecasts paramForecasts)
  {
    if (this.jdField_char == null) {
      return null;
    }
    Forecasts localForecasts = new Forecasts();
    Reader localReader = a();
    if (localReader != null) {
      try
      {
        localForecasts.startXMLParsing(localReader);
        int i = localForecasts.size();
        int j = paramForecasts.size();
        for (int k = i - 1; k >= 0; k--)
        {
          int m = 1;
          for (int n = 0; n < j; n++)
          {
            Forecast localForecast1 = (Forecast)localForecasts.get(k);
            Forecast localForecast2 = (Forecast)paramForecasts.get(n);
            if (localForecast1.getCity().equals(localForecast2.getCity()))
            {
              m = 0;
              break;
            }
          }
          if (m != 0) {
            localForecasts.remove(k);
          }
        }
        return localForecasts;
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("getForecasts error", localThrowable);
      }
    }
    return null;
  }
  
  public int getAll(ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    int i = 0;
    for (int j = 0; j < paramArrayList1.size(); j++)
    {
      Object localObject2 = paramArrayList1.get(j);
      Object localObject1 = null;
      Object localObject3;
      if ((localObject2 instanceof Forecasts))
      {
        for (i = 0; i < paramArrayList2.size(); i++)
        {
          localObject1 = paramArrayList2.get(i);
          if ((localObject1 instanceof Forecasts)) {
            break;
          }
        }
        if (i < paramArrayList2.size())
        {
          localObject3 = getForecasts((Forecasts)localObject2);
          ((Forecasts)localObject1).addAll((Collection)localObject3);
        }
      }
      else if ((localObject2 instanceof News))
      {
        for (i = 0; i < paramArrayList2.size(); i++)
        {
          localObject1 = paramArrayList2.get(i);
          if ((localObject1 instanceof News)) {
            break;
          }
        }
        if (i < paramArrayList2.size())
        {
          localObject3 = getNews((News)localObject2);
          ((News)localObject1).addAll((Collection)localObject3);
        }
      }
      else if ((localObject2 instanceof Stocks))
      {
        for (i = 0; i < paramArrayList2.size(); i++)
        {
          localObject1 = paramArrayList2.get(i);
          if ((localObject1 instanceof Stocks)) {
            break;
          }
        }
        if (i < paramArrayList2.size())
        {
          localObject3 = getStocks((Stocks)localObject2);
          ((Stocks)localObject1).addAll((Collection)localObject3);
        }
      }
      else if ((localObject2 instanceof StockIndexes))
      {
        for (i = 0; i < paramArrayList2.size(); i++)
        {
          localObject1 = paramArrayList2.get(i);
          if ((localObject1 instanceof StockIndexes)) {
            break;
          }
        }
        if (i < paramArrayList2.size())
        {
          localObject3 = getStockIndexes((StockIndexes)localObject2);
          ((StockIndexes)localObject1).addAll((Collection)localObject3);
        }
      }
    }
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.portal.DataDemo
 * JD-Core Version:    0.7.0.1
 */