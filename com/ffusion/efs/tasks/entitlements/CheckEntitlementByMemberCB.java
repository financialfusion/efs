package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckEntitlementByMemberCB
  extends BaseTask
  implements Task
{
  private static String KF = "TRUE";
  private static String Kz = "FALSE";
  private String Ky = null;
  private String KC = null;
  private String KG = null;
  private String KB = null;
  private String KH = null;
  private String KA = null;
  private String KE = null;
  private String KD = "CheckEntitlement";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (!p())
    {
      q();
      return this.taskErrorURL;
    }
    int i = -1;
    try
    {
      i = Integer.parseInt(this.KB);
    }
    catch (Exception localException)
    {
      this.error = 35035;
      q();
      return super.getTaskErrorURL();
    }
    try
    {
      Entitlement localEntitlement = new Entitlement(this.Ky, this.KC, this.KG);
      EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setEntitlementGroupId(i);
      localEntitlementGroupMember.setId(this.KH);
      localEntitlementGroupMember.setMemberType(this.KA);
      localEntitlementGroupMember.setMemberSubType(this.KE);
      boolean bool = Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement);
      if (bool) {
        bool = EntitlementsUtil.checkEntitlementAndParents(localEntitlementGroupMember, localEntitlement) == null;
      }
      if (bool) {
        localHttpSession.setAttribute(this.KD, KF);
      } else {
        localHttpSession.setAttribute(this.KD, Kz);
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
    return str;
  }
  
  private void q()
  {
    this.Ky = null;
    this.KC = null;
    this.KG = null;
    this.KB = null;
  }
  
  private boolean p()
  {
    boolean bool = true;
    if ((this.KB == null) || (this.KB.length() == 0))
    {
      this.error = 35003;
      bool = false;
    }
    if ((this.KH == null) || (this.KH.length() == 0))
    {
      this.error = 35026;
      bool = false;
    }
    if ((this.KA == null) || (this.KA.length() == 0))
    {
      this.error = 35024;
      bool = false;
    }
    if ((this.KE == null) || (this.KE.length() == 0))
    {
      this.error = 35025;
      bool = false;
    }
    return bool;
  }
  
  public void setOperationName(String paramString)
  {
    this.Ky = paramString;
  }
  
  public String getOperationName()
  {
    return this.Ky;
  }
  
  public void setObjectType(String paramString)
  {
    this.KC = paramString;
  }
  
  public String getObjectType()
  {
    return this.KC;
  }
  
  public void setObjectId(String paramString)
  {
    this.KG = paramString;
  }
  
  public String getObjectId()
  {
    return this.KG;
  }
  
  public void setGroupId(String paramString)
  {
    this.KB = paramString;
  }
  
  public String getGroupId()
  {
    return this.KB;
  }
  
  public void setMemberId(String paramString)
  {
    this.KH = paramString;
  }
  
  public String getMemberId()
  {
    return this.KH;
  }
  
  public void setMemberType(String paramString)
  {
    this.KA = paramString;
  }
  
  public String getMemberType()
  {
    return this.KA;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.KE = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.KE;
  }
  
  public void setAttributeName(String paramString)
  {
    this.KD = paramString;
  }
  
  public String getAttributeName()
  {
    return this.KD;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.CheckEntitlementByMemberCB
 * JD-Core Version:    0.7.0.1
 */