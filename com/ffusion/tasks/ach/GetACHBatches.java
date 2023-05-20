package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetACHBatches
  extends BaseTask
  implements Task
{
  protected String batchesName = "ACHBatches";
  protected String achType = "ACHBatch";
  protected String companyID;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str;
    if (getACHBatches(localHttpSession)) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean getACHBatches(HttpSession paramHttpSession)
  {
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    ACHBatches localACHBatches = new ACHBatches(localLocale);
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      localACHBatches = ACH.getACHBatches(localSecureUser, this.companyID, this.achType, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      paramHttpSession.setAttribute(this.batchesName, localACHBatches);
    }
    return this.error == 0;
  }
  
  public final void setBatchesName(String paramString)
  {
    this.batchesName = paramString;
  }
  
  public final String getBatchesName()
  {
    return this.batchesName;
  }
  
  public String getCompanyID()
  {
    return this.companyID;
  }
  
  public void setCompanyID(String paramString)
  {
    this.companyID = paramString;
  }
  
  public final void setACHType(String paramString)
  {
    this.achType = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetACHBatches
 * JD-Core Version:    0.7.0.1
 */