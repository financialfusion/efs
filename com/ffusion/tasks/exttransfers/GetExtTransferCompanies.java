package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.exttransfers.ExtTransferCompanies;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ExternalTransferAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetExtTransferCompanies
  implements Task
{
  protected String fiId;
  protected String custId;
  protected String nextURL = null;
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected Locale locale;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (getTransferCompanies(localHttpSession)) {
      str = this.successURL;
    } else {
      str = this.taskErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("ExternalTransferAccountsUpdated", "true");
    }
    return str;
  }
  
  protected boolean getTransferCompanies(HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    ExtTransferCompanies localExtTransferCompanies = null;
    try
    {
      localExtTransferCompanies = ExternalTransferAdmin.getExtTransferCompanies(localSecureUser, this.custId, this.fiId, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
    }
    catch (Exception localException)
    {
      DebugLog.log("ERROR: Exception thrown when get external Transfer companies:");
      localException.printStackTrace();
    }
    if (this.error == 0) {
      paramHttpSession.setAttribute("ExternalTransferCompanies", localExtTransferCompanies);
    }
    return this.error == 0;
  }
  
  public void setFiId(String paramString)
  {
    this.fiId = paramString;
  }
  
  public void setCustId(String paramString)
  {
    this.custId = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.GetExtTransferCompanies
 * JD-Core Version:    0.7.0.1
 */