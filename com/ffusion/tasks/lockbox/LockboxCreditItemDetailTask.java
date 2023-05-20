package com.ffusion.tasks.lockbox;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.lockbox.LockboxCreditItem;
import com.ffusion.beans.lockbox.LockboxCreditItems;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LockboxCreditItemDetailTask
  extends BaseTask
{
  private String aNv;
  
  public void setItemIndex(String paramString)
  {
    this.aNv = paramString;
  }
  
  public String getItemIndex()
  {
    return this.aNv;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return super.getServiceErrorURL();
    }
    long l = -1L;
    if ((this.aNv == null) || (this.aNv.length() <= 0))
    {
      this.error = 47;
      return super.getServiceErrorURL();
    }
    try
    {
      l = Long.parseLong(this.aNv);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 47;
      return super.getServiceErrorURL();
    }
    LockboxCreditItems localLockboxCreditItems = (LockboxCreditItems)localHttpSession.getAttribute("LOCKBOX_CREDIT_ITEMS");
    if (localLockboxCreditItems == null)
    {
      this.error = 51;
      return super.getServiceErrorURL();
    }
    LockboxCreditItem localLockboxCreditItem = null;
    for (int i = 0; i < localLockboxCreditItems.size(); i++) {
      if (((LockboxCreditItem)localLockboxCreditItems.get(i)).getItemIndex() == l)
      {
        localLockboxCreditItem = (LockboxCreditItem)localLockboxCreditItems.get(i);
        break;
      }
    }
    if (localLockboxCreditItem == null)
    {
      this.error = 63;
      return super.getServiceErrorURL();
    }
    localHttpSession.setAttribute("LOCKBOX_CREDIT_ITEM", localLockboxCreditItem);
    return super.getSuccessURL();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.lockbox.LockboxCreditItemDetailTask
 * JD-Core Version:    0.7.0.1
 */