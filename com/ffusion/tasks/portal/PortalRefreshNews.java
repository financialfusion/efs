package com.ffusion.tasks.portal;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.portal.News;
import com.ffusion.beans.portal.NewsHeadlines;
import com.ffusion.beans.portal.PortalItems;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Portal;
import com.ffusion.services.PortalData;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PortalRefreshNews
  extends BaseTask
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PortalItems localPortalItems = (PortalItems)localHttpSession.getAttribute("PortalItems");
    if (localPortalItems == null)
    {
      this.error = 9009;
      str1 = this.taskErrorURL;
    }
    else if ((localPortalItems.contains("NEWSFRONTPAGE")) || (localPortalItems.contains("NEWSBUSINESS")) || (localPortalItems.contains("NEWSSPORTS")))
    {
      PortalData localPortalData = (PortalData)localHttpSession.getAttribute("PortalData");
      News localNews1 = (News)localHttpSession.getAttribute("NewsSettings");
      if (localNews1 == null)
      {
        this.error = 9026;
        str1 = this.taskErrorURL;
      }
      News localNews2 = null;
      Object localObject;
      try
      {
        HashMap localHashMap = null;
        if (localPortalData != null)
        {
          localHashMap = new HashMap(1);
          localHashMap.put("SERVICE", localPortalData);
        }
        localObject = (SecureUser)localHttpSession.getAttribute("SecureUser");
        localNews2 = Portal.getNews((SecureUser)localObject, localNews1, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
      if ((this.error == 0) && (localNews2 != null))
      {
        Iterator localIterator = localNews2.iterator();
        while (localIterator.hasNext())
        {
          localObject = (NewsHeadlines)localIterator.next();
          String str2 = ((NewsHeadlines)localObject).getCategory();
          if (str2.equals("FRONTPAGE")) {
            localHttpSession.setAttribute("NewsFrontpage", localObject);
          } else if (str2.equals("BUSINESS")) {
            localHttpSession.setAttribute("NewsBusiness", localObject);
          } else if (str2.equals("SPORTS")) {
            localHttpSession.setAttribute("NewsSports", localObject);
          }
        }
      }
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.PortalRefreshNews
 * JD-Core Version:    0.7.0.1
 */