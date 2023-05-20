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

public class GetChildrenByGroupType
  extends BaseTask
  implements Task
{
  private int LD = -1;
  private String LC;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.LD == -1)
    {
      this.error = 35003;
      return this.taskErrorURL;
    }
    if ((this.LC == null) || (this.LC.length() == 0))
    {
      this.error = 35014;
      return this.taskErrorURL;
    }
    EntitlementGroups localEntitlementGroups = null;
    try
    {
      localEntitlementGroups = Entitlements.getChildrenByGroupType(this.LD, this.LC);
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
  
  public void setGroupId(String paramString)
  {
    this.LD = Integer.parseInt(paramString);
  }
  
  public String getGroupId()
  {
    return Integer.toString(this.LD);
  }
  
  public void setGroupType(String paramString)
  {
    this.LC = paramString;
  }
  
  public String getGroupType()
  {
    return this.LC;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetChildrenByGroupType
 * JD-Core Version:    0.7.0.1
 */