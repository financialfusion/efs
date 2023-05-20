package com.ffusion.tasks.business;

import com.ffusion.beans.business.MarketSegment;
import com.ffusion.beans.business.MarketSegments;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetMarketSegment
  extends BaseTask
  implements BusinessTask
{
  private int gY;
  private String gX = "MarketSegment";
  protected String marketSegmentsCollectionName = "MarketSegments";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    MarketSegment localMarketSegment = null;
    MarketSegments localMarketSegments = (MarketSegments)localHttpSession.getAttribute(this.marketSegmentsCollectionName);
    if (localMarketSegments == null)
    {
      this.error = 4110;
      str = this.taskErrorURL;
    }
    else
    {
      localMarketSegment = localMarketSegments.getById(this.gY);
      if (localMarketSegment == null)
      {
        this.error = 4112;
        str = this.taskErrorURL;
      }
      else
      {
        localHttpSession.setAttribute(this.gX, localMarketSegment);
      }
    }
    return str;
  }
  
  public void setMarketSegmentId(String paramString)
  {
    try
    {
      this.gY = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("SetMarketSegment Task Exception: ", localException);
    }
  }
  
  public void setMarketSegmentSessionName(String paramString)
  {
    this.gX = paramString;
  }
  
  public void setMarketSegmentsCollectionName(String paramString)
  {
    this.marketSegmentsCollectionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.SetMarketSegment
 * JD-Core Version:    0.7.0.1
 */