package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.TaxForms;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetUserTaxForms
  extends BaseTask
  implements Task
{
  protected boolean reload = false;
  protected String collectionName = "TaxForms";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    TaxForms localTaxForms = (TaxForms)localHttpSession.getAttribute(this.collectionName);
    String str = null;
    this.error = 0;
    if (this.reload)
    {
      localTaxForms = null;
      localHttpSession.removeAttribute(this.collectionName);
      this.reload = false;
    }
    if (localTaxForms == null)
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        localTaxForms = ACH.getUserTaxForms(localSecureUser, localHashMap);
        if (localTaxForms != null) {
          localTaxForms.setSortedBy("STATE,NAME");
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
      if (this.error == 0)
      {
        localHttpSession.setAttribute(this.collectionName, localTaxForms);
        str = this.successURL;
      }
      else
      {
        str = this.serviceErrorURL;
      }
    }
    else
    {
      str = this.successURL;
    }
    return str;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCollectionName(String paramString)
  {
    this.collectionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetUserTaxForms
 * JD-Core Version:    0.7.0.1
 */