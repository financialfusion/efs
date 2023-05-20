package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupProperties;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyEntitlementGroup
  extends BaseTask
  implements Task
{
  private static final int K7 = -1;
  private String La;
  private int K5 = -1;
  private String K6;
  private int K8 = -1;
  private ArrayList K4 = new ArrayList();
  private ArrayList K9 = new ArrayList();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    EntitlementGroup localEntitlementGroup = (EntitlementGroup)localHttpSession.getAttribute("Entitlement_EntitlementGroup");
    if (localEntitlementGroup == null)
    {
      this.error = 35001;
      return this.taskErrorURL;
    }
    if ((this.La != null) && (this.La.length() != 0)) {
      localEntitlementGroup.setGroupName(this.La);
    }
    if (this.K5 != -1) {
      localEntitlementGroup.setParentId(this.K5);
    }
    if ((this.K6 != null) && (this.K6.length() != 0)) {
      localEntitlementGroup.setEntGroupType(this.K6);
    }
    if (this.K8 != -1) {
      localEntitlementGroup.setSvcBureauId(Integer.toString(this.K8));
    }
    for (int i = 0; i < this.K4.size(); i++)
    {
      localEntitlementGroup.getProperties().setCurrentProperty((String)this.K4.get(i));
      localEntitlementGroup.getProperties().setValueOfCurrentProperty((String)this.K9.get(i));
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      Entitlements.modifyEntitlementGroup(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroup);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("Entitlement_EntitlementGroup", localEntitlementGroup);
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
    this.La = paramString;
  }
  
  public String getGroupName()
  {
    return this.La;
  }
  
  public void setParentId(String paramString)
  {
    this.K5 = Integer.parseInt(paramString);
  }
  
  public String getParentId()
  {
    return Integer.toString(this.K5);
  }
  
  public void setGroupType(String paramString)
  {
    this.K6 = paramString;
  }
  
  public String getGroupType()
  {
    return this.K6;
  }
  
  public void setSvcBureauId(String paramString)
  {
    this.K8 = Integer.parseInt(paramString);
  }
  
  public String getSvcBureauId()
  {
    return Integer.toString(this.K8);
  }
  
  public void setProperty(String paramString)
  {
    this.K4.add(paramString);
  }
  
  public void setValueOfProperty(String paramString)
  {
    this.K9.add(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.ModifyEntitlementGroup
 * JD-Core Version:    0.7.0.1
 */