package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployee;
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

public class CheckUserEntitlement
  extends BaseTask
  implements AdminTask
{
  private String adh;
  private String adi;
  private String add;
  private String adg;
  private String adf = "";
  private String ade = "CheckEntitlement";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    EntitlementGroupMember localEntitlementGroupMember = null;
    if (this.adf.length() > 0)
    {
      localEntitlementGroupMember = ((BusinessEmployee)localHttpSession.getAttribute(this.adf)).getEntitlementGroupMember();
    }
    else
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
    }
    boolean bool = false;
    try
    {
      bool = Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement(this.adh, this.adi, this.add));
      localHttpSession.setAttribute(this.ade, new Boolean(bool).toString().toUpperCase());
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      if (bool) {
        str = this.successURL;
      } else {
        str = this.adg;
      }
    }
    else {
      str = this.serviceErrorURL;
    }
    this.adf = "";
    this.ade = "CheckEntitlement";
    return str;
  }
  
  public void setOperationName(String paramString)
  {
    this.adh = paramString;
  }
  
  public String getOperationName()
  {
    return this.adh;
  }
  
  public void setObjectType(String paramString)
  {
    this.adi = paramString;
  }
  
  public String getObjectType()
  {
    return this.adi;
  }
  
  public void setObjectId(String paramString)
  {
    this.add = paramString;
  }
  
  public String getObjectId()
  {
    return this.add;
  }
  
  public void setUserName(String paramString)
  {
    this.adf = paramString;
  }
  
  public void setAttributeName(String paramString)
  {
    this.ade = paramString;
  }
  
  public String getAttributeName()
  {
    return this.ade;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.CheckUserEntitlement
 * JD-Core Version:    0.7.0.1
 */