package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
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

public class CheckEntitlement
  extends BaseTask
  implements Task
{
  private String LG;
  private String LH;
  private String LE;
  private String LF;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (!t()) {
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    boolean bool = false;
    try
    {
      EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
      Entitlement localEntitlement = new Entitlement(this.LG, this.LH, this.LE);
      bool = Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement);
      if (bool) {
        bool = EntitlementsUtil.checkEntitlementAndParents(localEntitlementGroupMember, localEntitlement) == null;
      }
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
        str = this.LF;
      }
    }
    else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  private boolean t()
  {
    boolean bool = true;
    if ((this.LF == null) || (this.LF.length() == 0))
    {
      this.error = 35005;
      bool = false;
    }
    return bool;
  }
  
  public void setOperationName(String paramString)
  {
    this.LG = paramString;
  }
  
  public String getOperationName()
  {
    return this.LG;
  }
  
  public void setObjectType(String paramString)
  {
    this.LH = paramString;
  }
  
  public String getObjectType()
  {
    return this.LH;
  }
  
  public void setObjectId(String paramString)
  {
    this.LE = paramString;
  }
  
  public String getObjectId()
  {
    return this.LE;
  }
  
  public void setNotEntitledURL(String paramString)
  {
    this.LF = paramString;
  }
  
  public String getNotEntitledURL()
  {
    return this.LF;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.CheckEntitlement
 * JD-Core Version:    0.7.0.1
 */