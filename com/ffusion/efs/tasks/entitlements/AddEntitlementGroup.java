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

public class AddEntitlementGroup
  extends BaseTask
  implements Task
{
  private String KN;
  private int KJ = -1;
  private String KK;
  private int KL = -1;
  private ArrayList KI = new ArrayList();
  private ArrayList KM = new ArrayList();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if ((this.KN == null) || (this.KN.length() == 0))
    {
      this.error = 35006;
      return this.taskErrorURL;
    }
    if (this.KJ == -1)
    {
      this.error = 35021;
      return this.taskErrorURL;
    }
    if (this.KL == -1)
    {
      this.error = 35027;
      return this.taskErrorURL;
    }
    EntitlementGroup localEntitlementGroup1 = new EntitlementGroup();
    localEntitlementGroup1.setGroupName(this.KN);
    localEntitlementGroup1.setParentId(this.KJ);
    localEntitlementGroup1.setEntGroupType(this.KK);
    localEntitlementGroup1.setSvcBureauId(Integer.toString(this.KL));
    for (int i = 0; i < this.KI.size(); i++)
    {
      localEntitlementGroup1.getProperties().setCurrentProperty((String)this.KI.get(i));
      localEntitlementGroup1.getProperties().setValueOfCurrentProperty((String)this.KM.get(i));
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroup localEntitlementGroup2 = null;
    try
    {
      localEntitlementGroup2 = Entitlements.addEntitlementGroup(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroup1);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("Entitlement_EntitlementGroup", localEntitlementGroup2);
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
    this.KN = paramString;
  }
  
  public String getGroupName()
  {
    return this.KN;
  }
  
  public void setParentId(String paramString)
  {
    this.KJ = Integer.parseInt(paramString);
  }
  
  public String getParentId()
  {
    return Integer.toString(this.KJ);
  }
  
  public void setGroupType(String paramString)
  {
    this.KK = paramString;
  }
  
  public String getGroupType()
  {
    return this.KK;
  }
  
  public void setSvcBureauId(String paramString)
  {
    this.KL = Integer.parseInt(paramString);
  }
  
  public String getSvcBureauId()
  {
    return Integer.toString(this.KL);
  }
  
  public void setProperty(String paramString)
  {
    this.KI.add(paramString);
  }
  
  public void setValueOfProperty(String paramString)
  {
    this.KM.add(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.AddEntitlementGroup
 * JD-Core Version:    0.7.0.1
 */