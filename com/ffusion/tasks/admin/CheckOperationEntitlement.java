package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckOperationEntitlement
  extends BaseTask
  implements AdminTask, Task
{
  private static String adq = "TRUE";
  private static String ado = "FALSE";
  private int adk = -1;
  private String adj = null;
  private String adn = null;
  private String adp = null;
  private String adl = null;
  private int adm = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = super.getSuccessURL();
    boolean bool = false;
    try
    {
      if ((this.adj != null) && (this.adn == null)) {
        this.adn = "Account";
      }
      Object localObject1;
      if ((this.adn != null) && (this.adn.equals("Account")))
      {
        localObject1 = (SecureUser)localHttpSession.getAttribute("SecureUser");
        if (((SecureUser)localObject1).getBusinessID() > 0) {
          this.adm = ((SecureUser)localObject1).getBusinessID();
        }
        MultiEntitlement localMultiEntitlement = new MultiEntitlement();
        localMultiEntitlement.setOperations(new String[] { this.adp });
        localMultiEntitlement.setObjects(new String[] { this.adn }, new String[] { this.adj });
        bool = EntitlementsUtil.checkAccountEntitlement(this.adk, localMultiEntitlement, this.adm) == null;
      }
      else
      {
        localObject1 = new Entitlement(this.adp, this.adn, this.adj);
        bool = Entitlements.checkEntitlement(this.adk, (Entitlement)localObject1);
      }
      if (bool) {
        localHttpSession.setAttribute(this.adl, adq);
      } else {
        localHttpSession.setAttribute(this.adl, ado);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    finally
    {
      this.adk = -1;
      this.adj = null;
      this.adn = null;
      this.adp = null;
      this.adl = null;
    }
    return str;
  }
  
  public void setGroupID(String paramString)
  {
    this.adk = Integer.parseInt(paramString);
  }
  
  public void setObjectID(String paramString)
  {
    this.adj = paramString;
  }
  
  public void setObjectType(String paramString)
  {
    this.adn = paramString;
  }
  
  public void setOperationName(String paramString)
  {
    this.adp = paramString;
  }
  
  public void setAttributeName(String paramString)
  {
    this.adl = paramString;
  }
  
  public void setBusinessDirectoryID(String paramString)
  {
    this.adm = Integer.parseInt(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.CheckOperationEntitlement
 * JD-Core Version:    0.7.0.1
 */