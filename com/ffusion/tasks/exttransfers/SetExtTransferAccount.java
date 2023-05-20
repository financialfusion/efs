package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetExtTransferAccount
  extends ModifyExtTransferAccount
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    ExtTransferAccounts localExtTransferAccounts = (ExtTransferAccounts)localHttpSession.getAttribute("ExternalTransferAccounts");
    if (localExtTransferAccounts == null)
    {
      this.error = 4201;
      str = this.taskErrorURL;
    }
    else
    {
      ExtTransferAccount localExtTransferAccount = localExtTransferAccounts.getByID(this.bpwID);
      localHttpSession.setAttribute("ExternalTransferACCOUNT", localExtTransferAccount);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.SetExtTransferAccount
 * JD-Core Version:    0.7.0.1
 */