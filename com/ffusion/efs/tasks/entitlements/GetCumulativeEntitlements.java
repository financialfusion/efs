package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCumulativeEntitlements
  extends BaseTask
  implements Task
{
  private int aC;
  private String aA;
  private String aB;
  private String az;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.aC == 0)
    {
      this.error = 35003;
      return this.taskErrorURL;
    }
    EntitlementGroupMember localEntitlementGroupMember = null;
    if ((this.aB != null) && (this.az != null) && (this.aA != null))
    {
      localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setEntitlementGroupId(this.aC);
      localEntitlementGroupMember.setMemberType(this.aB);
      localEntitlementGroupMember.setMemberSubType(this.az);
      localEntitlementGroupMember.setId(this.aA);
    }
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
    try
    {
      if (localEntitlementGroupMember == null) {
        localEntitlements = com.ffusion.csil.core.Entitlements.getCumulativeEntitlements(this.aC);
      } else {
        localEntitlements = com.ffusion.csil.core.Entitlements.getCumulativeEntitlements(localEntitlementGroupMember);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("Entitlement_Entitlements", localEntitlements);
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
    this.aC = Integer.parseInt(paramString);
  }
  
  public int getGroupId()
  {
    return this.aC;
  }
  
  public void setMemberId(String paramString)
  {
    this.aA = paramString;
  }
  
  public String getMemberId()
  {
    return this.aA;
  }
  
  public void setMemberType(String paramString)
  {
    this.aB = paramString;
  }
  
  public String getMemberType()
  {
    return this.aB;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.az = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.az;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetCumulativeEntitlements
 * JD-Core Version:    0.7.0.1
 */