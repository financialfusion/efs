package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBusiness
  extends BaseTask
  implements BusinessTask
{
  protected int id = 0;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if (this.id == 0)
    {
      this.error = 4108;
    }
    else
    {
      com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business(localLocale);
      localBusiness.setId(this.id);
      try
      {
        HashMap localHashMap = null;
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        if (localSecureUser == null) {
          localSecureUser = new SecureUser(localLocale);
        }
        localBusiness = com.ffusion.csil.core.Business.getBusiness(localSecureUser, localBusiness, localHashMap);
        localBusiness.setLocale(localSecureUser.getLocale());
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
        return str;
      }
      if (localBusiness.getBusinessName() == null)
      {
        str = this.serviceErrorURL;
        this.error = 4101;
      }
      else
      {
        localHttpSession.setAttribute("Business", localBusiness);
        str = this.successURL;
      }
    }
    return str;
  }
  
  public String getId()
  {
    return String.valueOf(this.id);
  }
  
  public int getIdValue()
  {
    return this.id;
  }
  
  public void setId(int paramInt)
  {
    this.id = paramInt;
  }
  
  public void setId(String paramString)
  {
    this.id = Integer.parseInt(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetBusiness
 * JD-Core Version:    0.7.0.1
 */