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

public class IsObjectInUse
  extends BaseTask
  implements Task
{
  private String ax;
  private String av;
  private String aw;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (!jdMethod_goto()) {
      return this.taskErrorURL;
    }
    boolean bool = false;
    try
    {
      bool = Entitlements.isObjectInUse(this.ax, this.av);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      if (bool) {
        str = this.aw;
      } else {
        str = this.successURL;
      }
    }
    else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  private boolean jdMethod_goto()
  {
    boolean bool = true;
    if ((this.aw == null) || (this.aw.length() == 0))
    {
      this.error = 35038;
      bool = false;
    }
    return bool;
  }
  
  public void setObjectType(String paramString)
  {
    this.ax = paramString;
  }
  
  public String getObjectType()
  {
    return this.ax;
  }
  
  public void setObjectId(String paramString)
  {
    this.av = paramString;
  }
  
  public String getObjectId()
  {
    return this.av;
  }
  
  public void setObjectInUseURL(String paramString)
  {
    this.aw = paramString;
  }
  
  public String getObjectInUseURL()
  {
    return this.aw;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.IsObjectInUse
 * JD-Core Version:    0.7.0.1
 */