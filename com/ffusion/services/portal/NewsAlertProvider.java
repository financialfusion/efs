package com.ffusion.services.portal;

import com.ffusion.beans.portal.NewsHeadline;
import com.ffusion.beans.portal.Stock;
import com.ffusion.services.PortalContentProvider;
import com.ffusion.util.XMLUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class NewsAlertProvider
  implements PortalContentProvider
{
  private static final String a = "http://sites.stockpoint.com/financialfusion/article.asp?StoryId=";
  
  public ArrayList getHeadlines(Stock paramStock)
  {
    ArrayList localArrayList = new ArrayList();
    String str1 = paramStock.getNews();
    StringBuffer localStringBuffer = new StringBuffer("");
    StringTokenizer localStringTokenizer1 = new StringTokenizer(str1, "\n");
    if (!localStringTokenizer1.hasMoreTokens()) {
      return localArrayList;
    }
    String str2 = paramStock.getSymbol();
    while (localStringTokenizer1.hasMoreTokens())
    {
      String str3 = localStringTokenizer1.nextToken();
      if (!str3.trim().equals(""))
      {
        StringTokenizer localStringTokenizer2 = new StringTokenizer(str3, ",");
        String str4 = localStringTokenizer2.nextToken();
        str4 = str4.substring(str4.indexOf("/") + 1);
        String str5 = localStringTokenizer2.nextToken();
        String str6 = localStringTokenizer2.nextToken();
        String str7 = localStringTokenizer2.nextToken();
        if (!str7.startsWith("http:")) {
          str7 = "http://sites.stockpoint.com/financialfusion/article.asp?StoryId=" + str7;
        }
        for (String str8 = localStringTokenizer2.nextToken(); localStringTokenizer2.hasMoreTokens(); str8 = str8 + "," + localStringTokenizer2.nextToken()) {}
        NewsHeadline localNewsHeadline = new NewsHeadline();
        localNewsHeadline.setDate(str4);
        localNewsHeadline.setTime(str5);
        localNewsHeadline.setSource(str6);
        localNewsHeadline.setURL(str7);
        localNewsHeadline.setHeadline(str8);
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.portal.NewsAlertProvider
 * JD-Core Version:    0.7.0.1
 */