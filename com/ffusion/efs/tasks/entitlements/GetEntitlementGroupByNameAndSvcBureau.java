package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEntitlementGroupByNameAndSvcBureau
  extends BaseTask
  implements Task
{
  private String L5;
  private String L3;
  private int L4 = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if ((this.L5 == null) || (this.L5.length() == 0))
    {
      this.error = 35006;
      return this.taskErrorURL;
    }
    if ((this.L3 == null) || (this.L3.length() == 0))
    {
      this.error = 35037;
      return this.taskErrorURL;
    }
    if (this.L4 == -1)
    {
      this.error = 35027;
      return this.taskErrorURL;
    }
    EntitlementGroup localEntitlementGroup = null;
    try
    {
      localEntitlementGroup = Entitlements.getEntitlementGroupByNameAndSvcBureau(this.L5, this.L3, this.L4);
    }
    catch (CSILException localCSILException)
    {
      localCSILException.printStackTrace();
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      if (localEntitlementGroup == null) {
        localHttpSession.removeAttribute("Entitlement_EntitlementGroup");
      } else {
        localHttpSession.setAttribute("Entitlement_EntitlementGroup", localEntitlementGroup);
      }
      str = this.successURL;
    }
    else
    {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setGroupName(String paramString)
  {
    this.L5 = paramString;
  }
  
  public String getGroupName()
  {
    return this.L5;
  }
  
  public void setGroupType(String paramString)
  {
    this.L3 = paramString;
  }
  
  public String getGroupType()
  {
    return this.L3;
  }
  
  public void setSvcBureauId(String paramString)
  {
    this.L4 = Integer.parseInt(paramString);
  }
  
  public String getSvcBureauId()
  {
    return Integer.toString(this.L4);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetEntitlementGroupByNameAndSvcBureau
 * JD-Core Version:    0.7.0.1
 */