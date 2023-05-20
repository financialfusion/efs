package com.ffusion.tasks.admin;

import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEntitlementObjectID
  extends BaseTask
  implements AdminTask
{
  private String aap = null;
  private String aao = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount = (Account)localHttpSession.getAttribute(this.aap);
    if (localAccount == null)
    {
      this.error = 4506;
      return super.getTaskErrorURL();
    }
    try
    {
      this.aao = EntitlementsUtil.getEntitlementObjectId(localAccount);
      if ((this.aao == null) || (this.aao.length() <= 0))
      {
        this.error = 4516;
        return super.getTaskErrorURL();
      }
    }
    catch (Exception localException)
    {
      this.error = 4516;
      return super.getTaskErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setAccountAttributeName(String paramString)
  {
    this.aap = paramString;
  }
  
  public String getEntitlementObjectID()
  {
    return this.aao;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetEntitlementObjectID
 * JD-Core Version:    0.7.0.1
 */