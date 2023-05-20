package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EntitlementTypeExists
  extends BaseTask
  implements Task
{
  private String ah;
  private String ai;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (!jdMethod_case()) {
      return this.taskErrorURL;
    }
    boolean bool = false;
    try
    {
      bool = Entitlements.entitlementTypeExists(this.ah);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      if (bool) {
        str = this.successURL;
      } else {
        str = this.ai;
      }
    }
    else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  private boolean jdMethod_case()
  {
    boolean bool = true;
    if ((this.ah == null) || (this.ah.length() == 0))
    {
      this.error = 35004;
      bool = false;
    }
    if ((this.ai == null) || (this.ai.length() == 0))
    {
      this.error = 35015;
      bool = false;
    }
    return bool;
  }
  
  public void setTypeName(String paramString)
  {
    this.ah = paramString;
  }
  
  public String getTypeName()
  {
    return this.ah;
  }
  
  public void setNotExistsURL(String paramString)
  {
    this.ai = paramString;
  }
  
  public String getNotExistsURL()
  {
    return this.ai;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.EntitlementTypeExists
 * JD-Core Version:    0.7.0.1
 */