package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.entitlements.EntitlementCodes;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetDescendantsByType
  extends BaseTask
  implements Task, EntitlementCodes
{
  private String Oc = null;
  private String Od = null;
  private String Oe = "Descendants";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    if (this.Oc == null)
    {
      this.error = 14132;
      return super.getTaskErrorURL();
    }
    int i = -1;
    try
    {
      i = Integer.parseInt(this.Oc);
    }
    catch (Exception localException)
    {
      this.error = 14132;
      return super.getTaskErrorURL();
    }
    EntitlementGroups localEntitlementGroups = new EntitlementGroups();
    try
    {
      EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(i);
      if (localEntitlementGroup == null)
      {
        this.error = 14132;
        return super.getTaskErrorURL();
      }
      jdMethod_int(localEntitlementGroup, localEntitlementGroups);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    localHttpSession.setAttribute(this.Oe, localEntitlementGroups);
    return super.getSuccessURL();
  }
  
  private void jdMethod_int(EntitlementGroup paramEntitlementGroup, EntitlementGroups paramEntitlementGroups)
    throws CSILException
  {
    if (this.Od == null) {
      paramEntitlementGroups.add(paramEntitlementGroup);
    } else if (paramEntitlementGroup.getEntGroupType().equals(this.Od)) {
      paramEntitlementGroups.add(paramEntitlementGroup);
    }
    Iterator localIterator = null;
    EntitlementGroups localEntitlementGroups = null;
    EntitlementGroup localEntitlementGroup = null;
    localEntitlementGroups = Entitlements.getChildrenEntitlementGroups(paramEntitlementGroup.getGroupId());
    if (localEntitlementGroups != null)
    {
      localIterator = localEntitlementGroups.iterator();
      while (localIterator.hasNext())
      {
        localEntitlementGroup = (EntitlementGroup)localIterator.next();
        jdMethod_int(localEntitlementGroup, paramEntitlementGroups);
      }
    }
  }
  
  public void setGroupID(String paramString)
  {
    this.Oc = paramString;
  }
  
  public void setGroupType(String paramString)
  {
    this.Od = paramString;
  }
  
  public void setAttributeName(String paramString)
  {
    this.Oe = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetDescendantsByType
 * JD-Core Version:    0.7.0.1
 */