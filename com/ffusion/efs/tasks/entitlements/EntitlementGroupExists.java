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

public class EntitlementGroupExists
  extends BaseTask
  implements Task
{
  private String aN;
  private String aK;
  private int aL = -1;
  private String aM;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (!jdMethod_long()) {
      return this.taskErrorURL;
    }
    boolean bool = false;
    try
    {
      bool = Entitlements.entitlementGroupExists(this.aN, this.aK, this.aL);
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
        str = this.aM;
      }
    }
    else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  private boolean jdMethod_long()
  {
    boolean bool = true;
    if ((this.aN == null) || (this.aN.length() == 0))
    {
      this.error = 35006;
      bool = false;
    }
    if ((this.aK == null) || (this.aK.length() == 0))
    {
      this.error = 35037;
      bool = false;
    }
    if (this.aL == -1)
    {
      this.error = 35027;
      bool = false;
    }
    if ((this.aM == null) || (this.aM.length() == 0))
    {
      this.error = 35015;
      bool = false;
    }
    return bool;
  }
  
  public void setGroupName(String paramString)
  {
    this.aN = paramString;
  }
  
  public String getGroupName()
  {
    return this.aN;
  }
  
  public void setGroupType(String paramString)
  {
    this.aK = paramString;
  }
  
  public String getGroupType()
  {
    return this.aK;
  }
  
  public void setSvcBureauId(String paramString)
  {
    this.aL = Integer.parseInt(paramString);
  }
  
  public String getSvcBureauId()
  {
    return Integer.toString(this.aL);
  }
  
  public void setNotExistsURL(String paramString)
  {
    this.aM = paramString;
  }
  
  public String getNotExistsURL()
  {
    return this.aM;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.EntitlementGroupExists
 * JD-Core Version:    0.7.0.1
 */