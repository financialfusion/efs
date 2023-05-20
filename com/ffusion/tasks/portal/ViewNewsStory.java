package com.ffusion.tasks.portal;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.portal.News;
import com.ffusion.beans.portal.NewsHeadline;
import com.ffusion.beans.portal.NewsHeadlines;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Portal;
import com.ffusion.services.PortalData;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewNewsStory
  extends BaseTask
  implements Task
{
  public static final String VIEW_NEWS_STORY_SESSION = "ViewNewsStory";
  private String mA = "";
  private String mz = "<br>";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PortalData localPortalData = (PortalData)localHttpSession.getAttribute("PortalData");
    News localNews = new News();
    NewsHeadlines localNewsHeadlines = new NewsHeadlines();
    NewsHeadline localNewsHeadline = new NewsHeadline();
    localNewsHeadline.setStoryID(this.mA);
    localNewsHeadlines.setCategory("STORY");
    localNewsHeadlines.add(localNewsHeadline);
    localNews.add(localNewsHeadlines);
    try
    {
      HashMap localHashMap = null;
      if (localPortalData != null)
      {
        localHashMap = new HashMap();
        localHashMap.put("SERVICE", localPortalData);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localNews = Portal.getNews(localSecureUser, localNews, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localNewsHeadlines = (NewsHeadlines)localNews.get(0);
      localNewsHeadline = (NewsHeadline)localNewsHeadlines.get(0);
      localNewsHeadline.setStory(jdMethod_goto(localNewsHeadline.getStory()));
      localHttpSession.setAttribute("NewsStory", localNewsHeadline);
    }
    return str;
  }
  
  private String jdMethod_goto(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      StringReader localStringReader = new StringReader(paramString);
      BufferedReader localBufferedReader = new BufferedReader(localStringReader);
      String str = "";
      while ((str = localBufferedReader.readLine()) != null)
      {
        localStringBuffer.append(str);
        localStringBuffer.append(this.mz);
      }
    }
    catch (IOException localIOException) {}
    return localStringBuffer.toString();
  }
  
  public void setStoryID(String paramString)
  {
    this.mA = paramString;
  }
  
  public String getStoryID()
  {
    return this.mA;
  }
  
  public void setLineBreak(String paramString)
  {
    this.mz = paramString;
  }
  
  public String getLineBreak()
  {
    return this.mz;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.ViewNewsStory
 * JD-Core Version:    0.7.0.1
 */