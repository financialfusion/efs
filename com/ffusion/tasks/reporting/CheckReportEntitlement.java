package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckReportEntitlement
  extends BaseTask
{
  private ArrayList Uk = new ArrayList();
  private String Uj = "false";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      for (int i = 0; i < this.Uk.size(); i++) {
        if (!EntitlementsUtil.checkReportTypeEntitlement(localSecureUser, (String)this.Uk.get(i), false))
        {
          setReportDenied("true");
        }
        else
        {
          setReportDenied("false");
          break;
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setReportType(String paramString)
  {
    this.Uk.add(paramString);
  }
  
  public void setReportClear(String paramString)
  {
    this.Uk.clear();
  }
  
  public void setReportDenied(String paramString)
  {
    this.Uj = paramString;
  }
  
  public String getReportDenied()
  {
    return this.Uj;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.CheckReportEntitlement
 * JD-Core Version:    0.7.0.1
 */