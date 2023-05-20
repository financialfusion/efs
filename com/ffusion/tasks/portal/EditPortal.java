package com.ffusion.tasks.portal;

import com.ffusion.beans.portal.PortalItems;
import com.ffusion.tasks.BaseTask;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class EditPortal
  extends BaseTask
  implements Task
{
  protected String showItem = "";
  
  protected boolean processShowItem(HttpServletRequest paramHttpServletRequest, String paramString)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PortalItems localPortalItems = (PortalItems)localHttpSession.getAttribute("PortalItems");
    if (localPortalItems == null) {
      return false;
    }
    if (this.showItem.equalsIgnoreCase("true"))
    {
      if (!localPortalItems.contains(paramString)) {
        localPortalItems.add(paramString);
      }
    }
    else if (this.showItem.equalsIgnoreCase("false")) {
      localPortalItems.remove(paramString);
    }
    return true;
  }
  
  public void setShowItem(String paramString)
  {
    this.showItem = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.EditPortal
 * JD-Core Version:    0.7.0.1
 */