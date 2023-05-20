package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.AccountGroup;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRestrictedAccountGroupsForGroup
  extends BaseTask
  implements AdminTask, Task
{
  private String aas = "GroupAccountGroups";
  private int aar = -1;
  private String aaq = "";
  private int aau = -1;
  private int aat = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = super.getSuccessURL();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BusinessAccountGroups localBusinessAccountGroups = null;
    HashMap localHashMap = new HashMap();
    try
    {
      if (this.aat != -1) {
        localBusinessAccountGroups = AccountGroup.getAccountGroups(localSecureUser, this.aat, localHashMap);
      }
      if (localBusinessAccountGroups != null)
      {
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
        if (this.aaq.length() > 0)
        {
          localObject1 = new EntitlementGroupMember();
          ((EntitlementGroupMember)localObject1).setEntitlementGroupId(this.aar);
          ((EntitlementGroupMember)localObject1).setId(this.aaq);
          ((EntitlementGroupMember)localObject1).setMemberType("USER");
          ((EntitlementGroupMember)localObject1).setMemberSubType(Integer.toString(this.aau));
          localEntitlements = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements((EntitlementGroupMember)localObject1, this.aar);
        }
        else
        {
          localEntitlements = com.ffusion.csil.core.Entitlements.getCumulativeEntitlements(this.aar);
        }
        Object localObject1 = new HashMap(localEntitlements.size());
        Iterator localIterator = localEntitlements.iterator();
        String str2 = null;
        while (localIterator.hasNext())
        {
          localObject2 = (Entitlement)localIterator.next();
          str2 = ((Entitlement)localObject2).getObjectType();
          if ((str2 != null) && (str2.equals("AccountGroup")) && (((Entitlement)localObject2).getOperationName() == null)) {
            ((HashMap)localObject1).put(((Entitlement)localObject2).getObjectId(), localObject2);
          }
        }
        if (((HashMap)localObject1).size() == 0)
        {
          localHttpSession.setAttribute(this.aas, localBusinessAccountGroups);
          localObject2 = str1;
          return localObject2;
        }
        Object localObject2 = localBusinessAccountGroups.iterator();
        while (((Iterator)localObject2).hasNext())
        {
          BusinessAccountGroup localBusinessAccountGroup = (BusinessAccountGroup)((Iterator)localObject2).next();
          String str3 = EntitlementsUtil.getEntitlementObjectId(localBusinessAccountGroup);
          if (((HashMap)localObject1).containsKey(str3)) {
            ((Iterator)localObject2).remove();
          }
        }
        localHttpSession.setAttribute(this.aas, localBusinessAccountGroups);
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 4512;
      str1 = this.taskErrorURL;
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    finally
    {
      this.aar = -1;
      this.aaq = "";
      this.aau = -1;
      this.aat = -1;
    }
    return str1;
  }
  
  public void setBusDirectoryId(String paramString)
  {
    try
    {
      this.aat = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.aat = -1;
    }
  }
  
  public void setGroupID(String paramString)
  {
    this.aar = Integer.parseInt(paramString);
  }
  
  public void setMemberID(String paramString)
  {
    this.aaq = paramString;
  }
  
  public void setUserType(String paramString)
  {
    this.aau = Integer.parseInt(paramString);
  }
  
  public void setGroupAccountGroupsName(String paramString)
  {
    this.aas = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetRestrictedAccountGroupsForGroup
 * JD-Core Version:    0.7.0.1
 */