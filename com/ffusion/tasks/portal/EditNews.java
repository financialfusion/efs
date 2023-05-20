package com.ffusion.tasks.portal;

import com.ffusion.beans.portal.News;
import com.ffusion.beans.portal.NewsHeadlines;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditNews
  extends EditPortal
  implements Task
{
  private String mj = "";
  private boolean mi = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    NewsHeadlines localNewsHeadlines1 = (NewsHeadlines)localHttpSession.getAttribute("NewsFrontpageSettings");
    NewsHeadlines localNewsHeadlines2 = (NewsHeadlines)localHttpSession.getAttribute("NewsBusinessSettings");
    NewsHeadlines localNewsHeadlines3 = (NewsHeadlines)localHttpSession.getAttribute("NewsSportsSettings");
    if (localNewsHeadlines1 == null)
    {
      this.error = 9006;
      str = this.taskErrorURL;
    }
    else if (localNewsHeadlines2 == null)
    {
      this.error = 9007;
      str = this.taskErrorURL;
    }
    else if (localNewsHeadlines3 == null)
    {
      this.error = 9008;
      str = this.taskErrorURL;
    }
    else
    {
      if (this.mj.equalsIgnoreCase("NEWSFRONTPAGE")) {
        this.mi = processShowItem(paramHttpServletRequest, "NEWSFRONTPAGE");
      } else if (this.mj.equalsIgnoreCase("NEWSBUSINESS")) {
        this.mi = processShowItem(paramHttpServletRequest, "NEWSBUSINESS");
      } else if (this.mj.equalsIgnoreCase("NEWSSPORTS")) {
        this.mi = processShowItem(paramHttpServletRequest, "NEWSSPORTS");
      }
      if (!this.mi)
      {
        this.error = 9009;
        str = this.taskErrorURL;
      }
      else
      {
        News localNews = new News();
        if (localNewsHeadlines1.getMaxHeadlinesValue() > 0) {
          localNews.add(localNewsHeadlines1);
        }
        if (localNewsHeadlines2.getMaxHeadlinesValue() > 0) {
          localNews.add(localNewsHeadlines2);
        }
        if (localNewsHeadlines3.getMaxHeadlinesValue() > 0) {
          localNews.add(localNewsHeadlines3);
        }
        localHttpSession.setAttribute("NewsSettings", localNews);
      }
    }
    return str;
  }
  
  public void setCategory(String paramString)
  {
    this.mj = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.EditNews
 * JD-Core Version:    0.7.0.1
 */