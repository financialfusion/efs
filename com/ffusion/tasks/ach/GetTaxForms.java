package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.TaxForms;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTaxForms
  extends TaxForms
  implements Task
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected int error = 0;
  protected boolean processed = false;
  protected String state;
  protected int type = 1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    if (!this.processed)
    {
      this.processed = true;
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        TaxForms localTaxForms = ACH.getTaxForms(localSecureUser, this.type, this.state, localHashMap);
        set(localTaxForms);
        localTaxForms = null;
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
      if (this.error != 0) {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public String getType()
  {
    if (this.type == 1) {
      return "FEDERAL";
    }
    if (this.type == 2) {
      return "STATE";
    }
    if (this.type == 4) {
      return "CHILDSUPPORT";
    }
    return "UNKNOWN";
  }
  
  public void setType(String paramString)
  {
    if (paramString.equalsIgnoreCase("FEDERAL")) {
      this.type = 1;
    } else if (paramString.equalsIgnoreCase("STATE")) {
      this.type = 2;
    } else if (paramString.equalsIgnoreCase("CHILDSUPPORT")) {
      this.type = 4;
    } else if (paramString.equalsIgnoreCase("OTHER")) {
      this.type = 3;
    }
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public String getState()
  {
    return this.state;
  }
  
  public void setState(String paramString)
  {
    this.state = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetTaxForms
 * JD-Core Version:    0.7.0.1
 */