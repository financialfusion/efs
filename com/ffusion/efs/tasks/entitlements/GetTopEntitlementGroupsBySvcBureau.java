package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTopEntitlementGroupsBySvcBureau
  extends BaseTask
  implements Task
{
  private int j = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.j == -1)
    {
      this.error = 35027;
      return this.taskErrorURL;
    }
    EntitlementGroups localEntitlementGroups = null;
    try
    {
      localEntitlementGroups = Entitlements.getTopEntitlementGroupsBySvcBureau(this.j);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("Entitlement_EntitlementGroups", localEntitlementGroups);
      str = this.successURL;
    }
    else
    {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setSvcBureauId(String paramString)
  {
    this.j = Integer.parseInt(paramString);
  }
  
  public String getSvcBureauId()
  {
    return Integer.toString(this.j);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetTopEntitlementGroupsBySvcBureau
 * JD-Core Version:    0.7.0.1
 */