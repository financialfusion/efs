package com.ffusion.tasks.portal;

import com.ffusion.beans.portal.PortalItems;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MoveItem
  extends BaseTask
  implements Task
{
  private String mw = "";
  private String mx = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PortalItems localPortalItems = (PortalItems)localHttpSession.getAttribute("PortalItems");
    if (localPortalItems == null)
    {
      this.error = 9009;
      str = this.taskErrorURL;
    }
    else if (this.mw.equalsIgnoreCase("down"))
    {
      localPortalItems.setMoveDown(this.mx);
    }
    else if (this.mw.equalsIgnoreCase("up"))
    {
      localPortalItems.setMoveUp(this.mx);
    }
    return str;
  }
  
  public void setMove(String paramString)
  {
    this.mw = paramString;
  }
  
  public void setItem(String paramString)
  {
    this.mx = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.MoveItem
 * JD-Core Version:    0.7.0.1
 */