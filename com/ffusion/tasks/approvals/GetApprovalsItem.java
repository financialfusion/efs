package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsItems;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetApprovalsItem
  extends BaseTask
{
  String aOi = null;
  String aOh = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    int i = 0;
    ApprovalsItem localApprovalsItem = null;
    try
    {
      if (this.aOi != null)
      {
        ApprovalsItems localApprovalsItems = (ApprovalsItems)localHttpSession.getAttribute(this.aOh);
        int j = 0;
        int k = Integer.valueOf(this.aOi).intValue();
        while (i == 0)
        {
          localApprovalsItem = (ApprovalsItem)localApprovalsItems.get(j);
          if (localApprovalsItem.getItemID() == k) {
            i = 1;
          }
          j++;
          if (j == localApprovalsItems.size()) {
            i = 1;
          }
        }
      }
      localHttpSession.removeAttribute("ApprovalsItem");
      if (localApprovalsItem != null) {
        localHttpSession.setAttribute("ApprovalsItem", localApprovalsItem);
      }
    }
    catch (Exception localException)
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setItemID(String paramString)
  {
    this.aOi = paramString;
  }
  
  public void setName(String paramString)
  {
    this.aOh = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.GetApprovalsItem
 * JD-Core Version:    0.7.0.1
 */