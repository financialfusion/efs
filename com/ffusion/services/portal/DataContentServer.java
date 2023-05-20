package com.ffusion.services.portal;

import com.ffusion.beans.portal.Forecasts;
import com.ffusion.beans.portal.News;
import com.ffusion.beans.portal.NewsHeadline;
import com.ffusion.beans.portal.PortalContent;
import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.StockIndexes;
import com.ffusion.beans.portal.StockSymbols;
import com.ffusion.beans.portal.Stocks;
import com.ffusion.services.PortalData4;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class DataContentServer
  implements PortalData4
{
  private static final String jdField_do = " HTTP/1.0\r\nConnection:Keep-Alive\r\nPragma:no-cache\r\nContent-type:text/xml\r\nUser-Agent:HFN 0100\r\nAccept:text/xml\r\nHost:";
  private static final String a = "\r\nContent-Length:";
  private static final String jdField_try = "CONTENTRQ";
  private static final String jdField_byte = "SIGNONRQ";
  private static final String jdField_new = "USERNAME";
  private static final String jdField_case = "PASSWORD";
  private static final String jdField_int = "CONTENTGETRQ";
  private static PortalContent jdField_if = null;
  private static int jdField_for = 5;
  protected static HashMap m_InitHashMap;
  
  public int initialize(String paramString)
  {
    int i = 0;
    jdField_if = new PortalContent();
    StringReader localStringReader = null;
    try
    {
      if (m_InitHashMap == null) {
        m_InitHashMap = new HashMap();
      }
      String str = "";
      if ((m_InitHashMap != null) && (m_InitHashMap.get(paramString) != null))
      {
        str = (String)m_InitHashMap.get(paramString);
      }
      else
      {
        InputStream localInputStream = ResourceUtil.getResourceAsStream(this, paramString);
        if (localInputStream == null)
        {
          i = 7;
        }
        else
        {
          str = Strings.streamToString(localInputStream);
          m_InitHashMap.put(paramString, str);
        }
      }
      localStringReader = new StringReader(str);
    }
    catch (Exception localException1) {}
    if (localStringReader == null) {
      i = 9000;
    } else {
      jdField_if.startXMLParsing(localStringReader);
    }
    try
    {
      jdField_for = Integer.parseInt(jdField_if.getContentServerMaxLoadTime());
      if (jdField_for == 0) {
        jdField_for = 5;
      }
    }
    catch (Exception localException2) {}
    try
    {
      localStringReader.close();
    }
    catch (Exception localException3) {}
    return i;
  }
  
  public void setInitURL(String paramString)
  {
    initialize(paramString);
  }
  
  public Stock getStock(String paramString, boolean paramBoolean)
  {
    try
    {
      Stocks localStocks1 = new Stocks();
      Stock localStock = new Stock();
      localStock.setNewsFlag(paramBoolean);
      localStock.setSymbol(paramString);
      localStocks1.add(localStock);
      Stocks localStocks2 = getStocks(localStocks1);
      return (Stock)localStocks2.get(0);
    }
    catch (Exception localException) {}
    return null;
  }
  
  public Stock getStock(String paramString)
  {
    return getStock(paramString, false);
  }
  
  public Stocks getStocks(Stocks paramStocks)
  {
    if (jdField_if.getContentServerURL() == null) {
      return null;
    }
    Stocks localStocks = new Stocks();
    if (paramStocks.size() > 0)
    {
      localObject = new StringBuffer();
      XMLHandler.appendBeginTag((StringBuffer)localObject, "CONTENTRQ");
      XMLHandler.appendBeginTag((StringBuffer)localObject, "SIGNONRQ");
      XMLHandler.appendTag((StringBuffer)localObject, "USERNAME", jdField_if.getContentServerUsername());
      XMLHandler.appendTag((StringBuffer)localObject, "PASSWORD", jdField_if.getContentServerPassword());
      XMLHandler.appendEndTag((StringBuffer)localObject, "SIGNONRQ");
      XMLHandler.appendBeginTag((StringBuffer)localObject, "CONTENTGETRQ");
      ((StringBuffer)localObject).append(paramStocks.toXML());
      XMLHandler.appendEndTag((StringBuffer)localObject, "CONTENTGETRQ");
      XMLHandler.appendEndTag((StringBuffer)localObject, "CONTENTRQ");
      getContent(localStocks, ((StringBuffer)localObject).toString(), jdField_if.getContentServerURL());
    }
    Object localObject = new Stocks();
    Iterator localIterator = paramStocks.iterator();
    while (localIterator.hasNext())
    {
      Stock localStock1 = (Stock)localIterator.next();
      Stock localStock2 = localStocks.getStock(localStock1.getSymbol());
      if (localStock2 != null) {
        if (((Stocks)localObject).getStock(localStock2.getSymbol()) == null)
        {
          localStock2.setShares(localStock1.getShares());
          localStock2.setPurchasePrice(localStock1.getPurchasePrice());
          ((Stocks)localObject).add(localStock2);
        }
        else
        {
          Stock localStock3 = new Stock();
          localStock3.set(localStock2);
          localStock3.setShares(localStock1.getShares());
          localStock3.setPurchasePrice(localStock1.getPurchasePrice());
          ((Stocks)localObject).add(localStock3);
        }
      }
    }
    Collections.sort((List)localObject);
    return localObject;
  }
  
  public StockIndexes getStockIndexes(StockIndexes paramStockIndexes)
  {
    if (jdField_if.getContentServerURL() == null) {
      return null;
    }
    StockIndexes localStockIndexes = new StockIndexes();
    if (paramStockIndexes.size() > 0)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      XMLHandler.appendBeginTag(localStringBuffer, "CONTENTRQ");
      XMLHandler.appendBeginTag(localStringBuffer, "SIGNONRQ");
      XMLHandler.appendTag(localStringBuffer, "USERNAME", jdField_if.getContentServerUsername());
      XMLHandler.appendTag(localStringBuffer, "PASSWORD", jdField_if.getContentServerPassword());
      XMLHandler.appendEndTag(localStringBuffer, "SIGNONRQ");
      XMLHandler.appendBeginTag(localStringBuffer, "CONTENTGETRQ");
      localStringBuffer.append(paramStockIndexes.toXML());
      XMLHandler.appendEndTag(localStringBuffer, "CONTENTGETRQ");
      XMLHandler.appendEndTag(localStringBuffer, "CONTENTRQ");
      getContent(localStockIndexes, localStringBuffer.toString(), jdField_if.getContentServerURL());
    }
    return localStockIndexes;
  }
  
  public StockSymbols getStockSymbols(String paramString)
  {
    if (jdField_if.getContentServerURL() == null) {
      return null;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "CONTENTRQ");
    XMLHandler.appendBeginTag(localStringBuffer, "SIGNONRQ");
    XMLHandler.appendTag(localStringBuffer, "USERNAME", jdField_if.getContentServerUsername());
    XMLHandler.appendTag(localStringBuffer, "PASSWORD", jdField_if.getContentServerPassword());
    XMLHandler.appendEndTag(localStringBuffer, "SIGNONRQ");
    XMLHandler.appendBeginTag(localStringBuffer, "CONTENTGETRQ");
    XMLHandler.openTag(localStringBuffer, "STOCK_SYMBOLS");
    XMLHandler.appendAttribute(localStringBuffer, "NAME", paramString);
    XMLHandler.closeTag(localStringBuffer, true);
    XMLHandler.appendEndTag(localStringBuffer, "CONTENTGETRQ");
    XMLHandler.appendEndTag(localStringBuffer, "CONTENTRQ");
    StockSymbols localStockSymbols = new StockSymbols();
    localStockSymbols.setSearchName(paramString);
    getContent(localStockSymbols, localStringBuffer.toString(), jdField_if.getContentServerURL());
    return localStockSymbols;
  }
  
  public Forecasts getForecasts(Forecasts paramForecasts)
  {
    if (jdField_if.getContentServerURL() == null) {
      return null;
    }
    Forecasts localForecasts = new Forecasts();
    if (paramForecasts.size() > 0)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      XMLHandler.appendBeginTag(localStringBuffer, "CONTENTRQ");
      XMLHandler.appendBeginTag(localStringBuffer, "SIGNONRQ");
      XMLHandler.appendTag(localStringBuffer, "USERNAME", jdField_if.getContentServerUsername());
      XMLHandler.appendTag(localStringBuffer, "PASSWORD", jdField_if.getContentServerPassword());
      XMLHandler.appendEndTag(localStringBuffer, "SIGNONRQ");
      XMLHandler.appendBeginTag(localStringBuffer, "CONTENTGETRQ");
      localStringBuffer.append(a(paramForecasts.toXML()));
      XMLHandler.appendEndTag(localStringBuffer, "CONTENTGETRQ");
      XMLHandler.appendEndTag(localStringBuffer, "CONTENTRQ");
      getContent(localForecasts, localStringBuffer.toString(), jdField_if.getContentServerURL());
    }
    return localForecasts;
  }
  
  public News getNews(News paramNews)
  {
    if (jdField_if.getContentServerURL() == null) {
      return null;
    }
    News localNews = new News();
    if (paramNews.size() > 0)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      XMLHandler.appendBeginTag(localStringBuffer, "CONTENTRQ");
      XMLHandler.appendBeginTag(localStringBuffer, "SIGNONRQ");
      XMLHandler.appendTag(localStringBuffer, "USERNAME", jdField_if.getContentServerUsername());
      XMLHandler.appendTag(localStringBuffer, "PASSWORD", jdField_if.getContentServerPassword());
      XMLHandler.appendEndTag(localStringBuffer, "SIGNONRQ");
      XMLHandler.appendBeginTag(localStringBuffer, "CONTENTGETRQ");
      localStringBuffer.append(paramNews.toXML());
      XMLHandler.appendEndTag(localStringBuffer, "CONTENTGETRQ");
      XMLHandler.appendEndTag(localStringBuffer, "CONTENTRQ");
      getContent(localNews, localStringBuffer.toString(), jdField_if.getContentServerURL());
    }
    return localNews;
  }
  
  public int getAll(ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    int i = 0;
    if (jdField_if.getContentServerURL() == null) {
      return i;
    }
    if (paramArrayList1.size() == 0) {
      return i;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "CONTENTRQ");
    XMLHandler.appendBeginTag(localStringBuffer, "SIGNONRQ");
    XMLHandler.appendTag(localStringBuffer, "USERNAME", jdField_if.getContentServerUsername());
    XMLHandler.appendTag(localStringBuffer, "PASSWORD", jdField_if.getContentServerPassword());
    XMLHandler.appendEndTag(localStringBuffer, "SIGNONRQ");
    XMLHandler.appendBeginTag(localStringBuffer, "CONTENTGETRQ");
    for (int j = 0; j < paramArrayList1.size(); j++)
    {
      Object localObject = paramArrayList1.get(j);
      if ((localObject instanceof Forecasts)) {
        localStringBuffer.append(((Forecasts)localObject).toXML());
      } else if ((localObject instanceof News)) {
        localStringBuffer.append(((News)localObject).toXML());
      } else if ((localObject instanceof Stocks)) {
        localStringBuffer.append(((Stocks)localObject).toXML());
      } else if ((localObject instanceof StockSymbols)) {
        localStringBuffer.append(((StockSymbols)localObject).toXML());
      } else if ((localObject instanceof StockIndexes)) {
        localStringBuffer.append(((StockIndexes)localObject).toXML());
      }
    }
    XMLHandler.appendEndTag(localStringBuffer, "CONTENTGETRQ");
    XMLHandler.appendEndTag(localStringBuffer, "CONTENTRQ");
    getContent(paramArrayList2, localStringBuffer.toString(), jdField_if.getContentServerURL());
    return i;
  }
  
  public ArrayList getHeadlines(Stock paramStock)
  {
    ArrayList localArrayList = new ArrayList();
    String str1 = paramStock.getNews();
    StringTokenizer localStringTokenizer1 = new StringTokenizer(str1, "\n");
    if (!localStringTokenizer1.hasMoreTokens()) {
      return localArrayList;
    }
    while (localStringTokenizer1.hasMoreTokens())
    {
      String str2 = localStringTokenizer1.nextToken();
      if (!str2.trim().equals(""))
      {
        StringTokenizer localStringTokenizer2 = new StringTokenizer(str2, ",");
        String str3 = localStringTokenizer2.nextToken();
        str3 = str3.substring(str3.indexOf("/") + 1);
        String str4 = localStringTokenizer2.nextToken();
        String str5 = localStringTokenizer2.nextToken();
        String str6 = localStringTokenizer2.nextToken();
        if (!str6.startsWith("http:")) {
          str6 = jdField_if.getStockNewsURL() + str6;
        }
        for (String str7 = localStringTokenizer2.nextToken(); localStringTokenizer2.hasMoreTokens(); str7 = str7 + "," + localStringTokenizer2.nextToken()) {}
        NewsHeadline localNewsHeadline = new NewsHeadline();
        localNewsHeadline.setDate(str3);
        localNewsHeadline.setTime(str4);
        localNewsHeadline.setSource(str5);
        localNewsHeadline.setURL(str6);
        localNewsHeadline.setHeadline(str7);
        localArrayList.add(localNewsHeadline);
      }
    }
    return localArrayList;
  }
  
  public NewsHeadline getNews(String paramString)
    throws Throwable
  {
    URL localURL = null;
    localURL = new URL(XMLUtil.XMLDecode(paramString));
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localURL.openStream()));
    StringBuffer localStringBuffer = new StringBuffer();
    for (String str = localBufferedReader.readLine(); str != null; str = localBufferedReader.readLine())
    {
      int i = str.indexOf("/gifs/bw.gif");
      if (i != -1) {
        str = str.substring(0, i) + "http://www.hfn.newsalert.com" + str.substring(i);
      }
      int j = str.indexOf("/info/bw.html");
      if (j != -1) {
        str = str.substring(0, j) + "http://www.hfn.newsalert.com" + str.substring(j);
      }
      localStringBuffer.append(str);
      localStringBuffer.append("\n");
    }
    NewsHeadline localNewsHeadline = new NewsHeadline();
    localNewsHeadline.setStory(localStringBuffer.toString());
    return localNewsHeadline;
  }
  
  public void getContent(Object paramObject, String paramString1, String paramString2)
  {
    BufferedReader localBufferedReader = null;
    BufferedWriter localBufferedWriter = null;
    String str1 = null;
    Socket localSocket = null;
    try
    {
      URL localURL = new URL(paramString2);
      int i = localURL.getPort();
      if (i == -1) {
        if (localURL.getProtocol().equalsIgnoreCase("https")) {
          i = 443;
        } else {
          i = 80;
        }
      }
      localSocket = new Socket(localURL.getHost(), i);
      localSocket.setSoTimeout(jdField_for * 1000);
      String str2 = "POST " + localURL.getFile() + " HTTP/1.0\r\nConnection:Keep-Alive\r\nPragma:no-cache\r\nContent-type:text/xml\r\nUser-Agent:HFN 0100\r\nAccept:text/xml\r\nHost:" + localURL.getHost() + "\r\nContent-Length:";
      localBufferedWriter = new BufferedWriter(new OutputStreamWriter(localSocket.getOutputStream()));
      localBufferedWriter.write(str2);
      localBufferedWriter.write(Integer.toString(paramString1.length()) + "\r\n\r\n");
      localBufferedWriter.write(paramString1);
      localBufferedWriter.flush();
      localBufferedReader = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
      int j = jdField_if(localBufferedReader);
      str1 = new String(a(localBufferedReader, j));
      if ((paramObject instanceof Forecasts))
      {
        ((Forecasts)paramObject).startXMLParsing(str1);
      }
      else if ((paramObject instanceof News))
      {
        ((News)paramObject).startXMLParsing(str1);
      }
      else if ((paramObject instanceof Stocks))
      {
        ((Stocks)paramObject).startXMLParsing(str1);
      }
      else if ((paramObject instanceof StockSymbols))
      {
        ((StockSymbols)paramObject).startXMLParsing(str1);
      }
      else if ((paramObject instanceof StockIndexes))
      {
        ((StockIndexes)paramObject).startXMLParsing(str1);
      }
      else if ((paramObject instanceof ArrayList))
      {
        ArrayList localArrayList = (ArrayList)paramObject;
        for (int n = 0; n < localArrayList.size(); n++)
        {
          Object localObject1 = localArrayList.get(n);
          int k;
          int m;
          if ((localObject1 instanceof Forecasts))
          {
            k = str1.indexOf("<FORECASTS");
            m = str1.indexOf("</FORECASTS>");
            if ((k != -1) && (m != -1))
            {
              m += "FORECASTS".length() + 3;
              ((Forecasts)localObject1).startXMLParsing(str1.substring(k, m));
            }
          }
          else if ((localObject1 instanceof News))
          {
            k = str1.indexOf("<NEWS");
            m = str1.indexOf("</NEWS>");
            if ((k != -1) && (m != -1))
            {
              m += "NEWS".length() + 3;
              ((News)localObject1).startXMLParsing(str1.substring(k, m));
            }
          }
          else if ((localObject1 instanceof Stocks))
          {
            k = str1.indexOf("<STOCKS");
            m = str1.indexOf("</STOCKS>");
            if ((k != -1) && (m != -1))
            {
              m += "STOCKS".length() + 3;
              ((Stocks)localObject1).startXMLParsing(str1.substring(k, m));
            }
          }
          else if ((localObject1 instanceof StockIndexes))
          {
            k = str1.indexOf("<STOCK_INDEXES");
            m = str1.indexOf("</STOCK_INDEXES>");
            if ((k != -1) && (m != -1))
            {
              m += "STOCK_INDEXES".length() + 3;
              ((StockIndexes)localObject1).startXMLParsing(str1.substring(k, m));
            }
          }
          else if ((localObject1 instanceof StockSymbols))
          {
            ((StockSymbols)localObject1).startXMLParsing(str1);
          }
        }
      }
    }
    catch (InterruptedIOException localInterruptedIOException)
    {
      DebugLog.throwing("Timed out getting portal data", localInterruptedIOException);
    }
    catch (IOException localIOException)
    {
      DebugLog.throwing("Exception getting portal data", localIOException);
    }
    finally
    {
      try
      {
        localBufferedWriter.close();
      }
      catch (Exception localException1) {}
      try
      {
        localBufferedReader.close();
      }
      catch (Exception localException2) {}
      try
      {
        localSocket.close();
        localSocket = null;
      }
      catch (Exception localException3) {}
    }
  }
  
  private int jdField_if(BufferedReader paramBufferedReader)
    throws IOException
  {
    int i = -1;
    for (String str1 = paramBufferedReader.readLine(); str1 != null; str1 = paramBufferedReader.readLine())
    {
      str1 = str1.toLowerCase();
      int j = str1.indexOf("content-length:");
      if (j != -1)
      {
        j += 15;
        String str2 = str1.substring(j);
        str2 = str2.trim();
        i = Integer.valueOf(str2).intValue();
      }
      else
      {
        j = str1.indexOf("http/");
        if (j != -1)
        {
          j = str1.indexOf(' ', j);
          int k = str1.indexOf(' ', j + 1);
          String str3 = str1.substring(j + 1, k);
          int m = Integer.valueOf(str3).intValue();
          if (m != 0)
          {
            if (m == 500) {
              throw new IOException("Error, no connection");
            }
            if (m == 400) {
              throw new IOException("Error, not supported");
            }
            if ((m != 200) && (m != 100) && (m != 206)) {
              throw new IOException("Error, no connection");
            }
          }
        }
        if (str1.length() == 0) {
          break;
        }
      }
    }
    return i;
  }
  
  private char[] a(BufferedReader paramBufferedReader, int paramInt)
    throws IOException
  {
    char[] arrayOfChar = null;
    if (paramInt > 0)
    {
      arrayOfChar = new char[paramInt];
      int i = 0;
      while (paramInt > 0)
      {
        int j = paramBufferedReader.read(arrayOfChar, i, paramInt);
        paramInt -= j;
        i += j;
      }
    }
    else
    {
      arrayOfChar = a(paramBufferedReader);
    }
    return arrayOfChar;
  }
  
  private char[] a(BufferedReader paramBufferedReader)
    throws IOException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (String str = paramBufferedReader.readLine(); str != null; str = paramBufferedReader.readLine())
    {
      localStringBuffer.append(str);
      localStringBuffer.append("\n");
    }
    return localStringBuffer.toString().toCharArray();
  }
  
  private String a(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return paramString;
    }
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    int i = paramString.length() - 1;
    while ((i = paramString.lastIndexOf('\'', i)) != -1)
    {
      localStringBuffer.insert(i--, '\'');
      if (i == -1) {
        break;
      }
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.portal.DataContentServer
 * JD-Core Version:    0.7.0.1
 */