package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.ConsumerStatus;
import com.ffusion.beans.billpresentment.TCURL;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTCURL
  extends TCURL
  implements Task
{
  private String Jy;
  private String Ju;
  private String Jx;
  private int Jv;
  private boolean Jw = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.Jx;
    if (this.Jw)
    {
      if (getIDValue() == 0L)
      {
        localObject = (ConsumerStatus)localHttpSession.getAttribute("ConsumerStatus");
        if (localObject == null)
        {
          this.Jv = 6508;
          return this.Jy;
        }
        setID(((ConsumerStatus)localObject).getTCurl());
      }
      Object localObject = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      this.Jv = 0;
      try
      {
        TCURL localTCURL = new TCURL();
        localTCURL = BillPresentment.getTCURL(localSecureUser, this, (HashMap)localObject);
        set(localTCURL);
      }
      catch (CSILException localCSILException)
      {
        this.Jv = MapError.mapError(localCSILException);
      }
      if (this.Jv == 0)
      {
        str = this.Jx;
        localHttpSession.setAttribute("TCURL", this);
      }
      else
      {
        str = this.Ju;
      }
    }
    return str;
  }
  
  public final void setProcess(String paramString)
  {
    this.Jw = paramString.trim().toLowerCase().equals("true");
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.Jx = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.Ju = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.Jy = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.Jv);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.GetTCURL
 * JD-Core Version:    0.7.0.1
 */