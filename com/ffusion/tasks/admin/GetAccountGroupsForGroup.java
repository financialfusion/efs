package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.core.AccountGroup;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountGroupsForGroup
  extends BaseTask
  implements AdminTask, Task
{
  private static final String aal = "USER";
  private String aak = "GroupAccountGroups";
  private int aaj = -1;
  private int aan = -1;
  private boolean aam = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = super.getSuccessURL();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    BusinessAccountGroups localBusinessAccountGroups = null;
    try
    {
      if (this.aan != -1) {
        localBusinessAccountGroups = AccountGroup.getAccountGroups(localSecureUser, this.aan, localHashMap);
      }
      if (localBusinessAccountGroups != null)
      {
        Iterator localIterator = localBusinessAccountGroups.iterator();
        while (localIterator.hasNext())
        {
          BusinessAccountGroup localBusinessAccountGroup = (BusinessAccountGroup)localIterator.next();
          Entitlement localEntitlement = new Entitlement("Access", "AccountGroup", EntitlementsUtil.getEntitlementObjectId(localBusinessAccountGroup));
          if (!Entitlements.checkEntitlement(this.aaj, localEntitlement)) {
            if (this.aam)
            {
              localEntitlement.setOperationName("Access (admin)");
              if (!Entitlements.checkEntitlement(this.aaj, localEntitlement)) {
                localIterator.remove();
              }
            }
            else
            {
              localIterator.remove();
            }
          }
        }
        localHttpSession.setAttribute(this.aak, localBusinessAccountGroups);
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 4512;
      str = this.taskErrorURL;
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    finally
    {
      this.aaj = -1;
    }
    return str;
  }
  
  public void setGroupID(String paramString)
  {
    try
    {
      this.aaj = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.aaj = -1;
    }
  }
  
  public void setGroupAccountGroupsName(String paramString)
  {
    this.aak = paramString;
  }
  
  public void setBusDirectoryId(String paramString)
  {
    try
    {
      this.aan = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.aan = -1;
    }
  }
  
  public void setCheckAdminAccess(String paramString)
  {
    this.aam = new Boolean(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetAccountGroupsForGroup
 * JD-Core Version:    0.7.0.1
 */