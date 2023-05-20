package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
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

public class ModifyTCURL
  extends TCURL
  implements Task
{
  private boolean JC = true;
  private boolean JB = true;
  private String JE;
  private String Jz;
  private String JD;
  private int JA;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    boolean bool = true;
    String str = this.JD;
    if (this.JC) {
      bool = init(localHttpSession);
    }
    if (bool)
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      this.JA = 0;
      try
      {
        TCURL localTCURL = BillPresentment.modifyTCURL(localSecureUser, this, localHashMap);
        set(localTCURL);
      }
      catch (CSILException localCSILException)
      {
        this.JA = MapError.mapError(localCSILException);
      }
      if (this.JA == 0) {
        localHttpSession.setAttribute("TCURL", this);
      } else {
        str = this.Jz;
      }
    }
    else
    {
      str = this.JE;
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    TCURL localTCURL = (TCURL)paramHttpSession.getAttribute("TCURL");
    if (localTCURL == null)
    {
      this.JA = 6513;
      return false;
    }
    set(localTCURL);
    return true;
  }
  
  public final void setInitialize(String paramString)
  {
    if (paramString.trim().toLowerCase().equals("true")) {
      this.JC = true;
    } else {
      this.JC = false;
    }
  }
  
  public final void setProcess(String paramString)
  {
    if (paramString.trim().toLowerCase().equals("true")) {
      this.JB = true;
    } else {
      this.JB = false;
    }
  }
  
  public final String getProcess()
  {
    return String.valueOf(this.JB);
  }
  
  public final boolean getProcessValue()
  {
    return this.JB;
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.JD = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.Jz = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.JE = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.JA);
  }
  
  public final void setError(String paramString)
  {
    this.JA = Integer.parseInt(paramString);
  }
  
  public final void setError(int paramInt)
  {
    this.JA = paramInt;
  }
  
  public final int getErrorValue()
  {
    return this.JA;
  }
  
  public final String getSuccessURL()
  {
    return this.JD;
  }
  
  public final String getServiceErrorURL()
  {
    return this.Jz;
  }
  
  public final String getTaskErrorURL()
  {
    return this.JE;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.ModifyTCURL
 * JD-Core Version:    0.7.0.1
 */