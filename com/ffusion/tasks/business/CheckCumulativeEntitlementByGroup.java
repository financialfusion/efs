package com.ffusion.tasks.business;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.efs.tasks.entitlements.Task;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckCumulativeEntitlementByGroup
  extends BaseTask
  implements Task
{
  private String MY = null;
  private String M2 = null;
  private String M6 = null;
  private String M1 = null;
  private String M7 = null;
  private String M0 = null;
  private String M4 = null;
  private String M3 = "CheckEntitlement";
  private static String M5 = "TRUE";
  private static String MZ = "FALSE";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (!A())
    {
      B();
      return this.taskErrorURL;
    }
    int i = -1;
    try
    {
      i = Integer.parseInt(this.M1);
    }
    catch (Exception localException)
    {
      this.error = 35035;
      B();
      return super.getTaskErrorURL();
    }
    try
    {
      EntitlementGroupMember localEntitlementGroupMember = null;
      if ((this.M0 != null) && (this.M4 != null) && (this.M7 != null))
      {
        localEntitlementGroupMember = new EntitlementGroupMember();
        localEntitlementGroupMember.setEntitlementGroupId(i);
        localEntitlementGroupMember.setMemberType(this.M0);
        localEntitlementGroupMember.setMemberSubType(this.M4);
        localEntitlementGroupMember.setId(this.M7);
      }
      Entitlement localEntitlement = new Entitlement(this.MY, this.M2, this.M6);
      int j = 0;
      if (localEntitlementGroupMember == null) {
        j = EntitlementsUtil.checkEntitlementAndParents(i, localEntitlement) == null ? 1 : 0;
      } else {
        j = EntitlementsUtil.checkEntitlementAndParents(localEntitlementGroupMember, localEntitlement) == null ? 1 : 0;
      }
      if (j != 0) {
        localHttpSession.setAttribute(this.M3, M5);
      } else {
        localHttpSession.setAttribute(this.M3, MZ);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    B();
    return str;
  }
  
  private void B()
  {
    this.MY = null;
    this.M2 = null;
    this.M6 = null;
    this.M1 = null;
  }
  
  private boolean A()
  {
    boolean bool = true;
    if ((this.M1 == null) || (this.M1.length() == 0))
    {
      this.error = 35003;
      bool = false;
    }
    return bool;
  }
  
  public void setOperationName(String paramString)
  {
    this.MY = paramString;
  }
  
  public String getOperationName()
  {
    return this.MY;
  }
  
  public void setObjectType(String paramString)
  {
    this.M2 = paramString;
  }
  
  public String getObjectType()
  {
    return this.M2;
  }
  
  public void setObjectId(String paramString)
  {
    this.M6 = paramString;
  }
  
  public String getObjectId()
  {
    return this.M6;
  }
  
  public void setGroupId(String paramString)
  {
    this.M1 = paramString;
  }
  
  public String getGroupId()
  {
    return this.M1;
  }
  
  public void setAttributeName(String paramString)
  {
    this.M3 = paramString;
  }
  
  public String getAttributeName()
  {
    return this.M3;
  }
  
  public void setMemberId(String paramString)
  {
    this.M7 = paramString;
  }
  
  public String getMemberId()
  {
    return this.M7;
  }
  
  public void setMemberType(String paramString)
  {
    this.M0 = paramString;
  }
  
  public String getMemberType()
  {
    return this.M0;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.M4 = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.M4;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.CheckCumulativeEntitlementByGroup
 * JD-Core Version:    0.7.0.1
 */