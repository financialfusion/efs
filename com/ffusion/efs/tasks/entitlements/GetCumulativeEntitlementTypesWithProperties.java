package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCumulativeEntitlementTypesWithProperties
  extends BaseTask
  implements Task
{
  private String LV = "Entitlement_Types";
  protected String _memberId;
  protected String _memberType;
  protected String _memberSubType;
  protected int _groupId = -1;
  private String LU = "";
  private String LW = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this._groupId == -1)
    {
      this.error = 35003;
      return this.taskErrorURL;
    }
    try
    {
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
      if (this._memberType == null)
      {
        localEntitlementTypePropertyLists = Entitlements.getCumulativeEntitlementTypesWithProperties(this._groupId, this.LU, this.LW);
      }
      else
      {
        EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
        localEntitlementGroupMember.setId(this._memberId);
        localEntitlementGroupMember.setMemberType(this._memberType);
        localEntitlementGroupMember.setMemberSubType(this._memberSubType);
        localEntitlementGroupMember.setEntitlementGroupId(this._groupId);
        localEntitlementTypePropertyLists = Entitlements.getCumulativeEntitlementTypesWithProperties(localEntitlementGroupMember, this.LU, this.LW);
      }
      localHttpSession.setAttribute(this.LV, localEntitlementTypePropertyLists);
    }
    catch (Exception localException)
    {
      return super.getTaskErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setSearchCritName(String paramString)
  {
    this.LU = paramString;
  }
  
  public void setSearchCritValue(String paramString)
  {
    this.LW = paramString;
  }
  
  public void setListName(String paramString)
  {
    this.LV = paramString;
  }
  
  public String getListName()
  {
    return this.LV;
  }
  
  public void setMemberId(String paramString)
  {
    this._memberId = paramString;
  }
  
  public String getMemberId()
  {
    return this._memberId;
  }
  
  public void setMemberType(String paramString)
  {
    this._memberType = paramString;
  }
  
  public String getMemberType()
  {
    return this._memberType;
  }
  
  public void setMemberSubType(String paramString)
  {
    this._memberSubType = paramString;
  }
  
  public String getMemberSubType()
  {
    return this._memberSubType;
  }
  
  public void setEntitlementGroupId(String paramString)
  {
    this._groupId = Integer.parseInt(paramString);
  }
  
  public String getEntitlementGroupId()
  {
    return Integer.toString(this._groupId);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetCumulativeEntitlementTypesWithProperties
 * JD-Core Version:    0.7.0.1
 */