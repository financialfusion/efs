package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.MapError;
import com.ffusion.util.Qsort;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetStatesWithTaxForms
  extends ArrayList
  implements Task
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected int error = 0;
  protected int type = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    if (size() == 0)
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        if (this.type != -1) {
          localHashMap.put("TaxFormType", "" + this.type);
        }
        String[] arrayOfString = ACH.getStatesWithTaxForms(localSecureUser, localHashMap);
        int i = arrayOfString.length;
        for (int j = 0; j < i; j++) {
          add(arrayOfString[j]);
        }
        Qsort.sortStrings(this, 1);
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
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
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
  
  public String getType()
  {
    if (this.type == 2) {
      return "STATE";
    }
    if (this.type == 4) {
      return "CHILDSUPPORT";
    }
    return "";
  }
  
  public void setType(String paramString)
  {
    if (paramString.equalsIgnoreCase("STATE")) {
      this.type = 2;
    } else if (paramString.equalsIgnoreCase("CHILDSUPPORT")) {
      this.type = 4;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetStatesWithTaxForms
 * JD-Core Version:    0.7.0.1
 */