package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.exttransfers.ExtTransferCompanies;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetExtTransferCompany
  extends ModifyExtTransferCompany
{
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String nextURL = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    ExtTransferCompanies localExtTransferCompanies = (ExtTransferCompanies)localHttpSession.getAttribute("ExternalTransferCompanies");
    if (localExtTransferCompanies == null)
    {
      this.error = 4203;
      str = this.taskErrorURL;
    }
    else
    {
      ExtTransferCompany localExtTransferCompany = localExtTransferCompanies.getByID(this.bpwID);
      localHttpSession.setAttribute("ExternalTransferCompany", localExtTransferCompany);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.SetExtTransferCompany
 * JD-Core Version:    0.7.0.1
 */