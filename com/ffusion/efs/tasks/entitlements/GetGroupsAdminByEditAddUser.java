package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetGroupsAdminByEditAddUser
  extends BaseTask
  implements Task
{
  private String LB;
  private String Lz;
  private int LA = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.Lz == null) {
      return this.taskErrorURL;
    }
    if (this.LA == -1)
    {
      this.error = 35026;
      return this.taskErrorURL;
    }
    EntitlementGroups localEntitlementGroups = new EntitlementGroups();
    Object localObject;
    if (this.LB == null)
    {
      try
      {
        EntitlementAdmins localEntitlementAdmins1 = Entitlements.getAdminInfoFor(this.LA);
        for (int i = 0; i < localEntitlementAdmins1.size(); i++)
        {
          localObject = (EntitlementAdmin)localEntitlementAdmins1.get(i);
          localEntitlementGroups.add(Entitlements.getEntitlementGroup(((EntitlementAdmin)localObject).getTargetGroupId()));
        }
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
      }
    }
    else
    {
      EntitlementGroupMember localEntitlementGroupMember = (EntitlementGroupMember)localHttpSession.getAttribute(this.LB);
      if ((localEntitlementGroupMember == null) || (!(localEntitlementGroupMember instanceof EntitlementGroupMember)))
      {
        this.error = 35026;
        return this.taskErrorURL;
      }
      try
      {
        EntitlementAdmins localEntitlementAdmins2 = Entitlements.getAdminInfoFor(this.LA);
        localObject = Entitlements.getAdminInfoFor(localEntitlementGroupMember);
        EntitlementAdmin localEntitlementAdmin;
        for (int j = 0; j < localEntitlementAdmins2.size(); j++)
        {
          localEntitlementAdmin = (EntitlementAdmin)localEntitlementAdmins2.get(j);
          EntitlementGroup localEntitlementGroup1 = Entitlements.getEntitlementGroup(localEntitlementAdmin.getTargetGroupId());
          localEntitlementGroups.add(localEntitlementGroup1);
        }
        for (j = 0; j < ((EntitlementAdmins)localObject).size(); j++)
        {
          localEntitlementAdmin = (EntitlementAdmin)((EntitlementAdmins)localObject).get(j);
          int k = localEntitlementAdmin.getTargetGroupId();
          int m = 0;
          for (int n = 0; n < localEntitlementGroups.size(); n++)
          {
            EntitlementGroup localEntitlementGroup2 = (EntitlementGroup)localEntitlementGroups.get(n);
            if (localEntitlementGroup2.getGroupId() == k)
            {
              m = 1;
              break;
            }
          }
          if (m == 0) {
            localEntitlementGroups.add(Entitlements.getEntitlementGroup(k));
          }
        }
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
      }
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.Lz, localEntitlementGroups);
      str = this.successURL;
    }
    else
    {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setMemberSessionName(String paramString)
  {
    this.LB = paramString;
  }
  
  public String getMemberSessionName()
  {
    return this.LB;
  }
  
  public void setEntGroupsName(String paramString)
  {
    this.Lz = paramString;
  }
  
  public String getEntGroupsName()
  {
    return this.Lz;
  }
  
  public void setEntGroupId(String paramString)
  {
    this.LA = Integer.parseInt(paramString);
  }
  
  public String getEntGroupId()
  {
    return String.valueOf(this.LA);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetGroupsAdminByEditAddUser
 * JD-Core Version:    0.7.0.1
 */