package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.TypePropertyList;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEntitledComponents
  extends BaseTask
  implements AdminTask, Task
{
  private int ada = -1;
  private int adc = -1;
  private String ac9 = null;
  private String ac6 = null;
  private HashMap ac7 = new HashMap();
  private String ac5 = "ComponentNames";
  private String adb = "LocalizedComponentNames";
  private boolean ac8 = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.ac7.put("component", null);
    try
    {
      HashMap localHashMap = new HashMap();
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(this.ac7);
      TreeSet localTreeSet = new TreeSet();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      EntitlementGroupMember localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
      boolean bool = true;
      EntitlementGroup localEntitlementGroup = null;
      EntitlementGroupMember localEntitlementGroupMember2 = null;
      if (this.adc == -1)
      {
        localEntitlementGroup = Entitlements.getEntitlementGroup(this.ada);
      }
      else
      {
        localEntitlementGroupMember2 = new EntitlementGroupMember();
        localEntitlementGroupMember2.setEntitlementGroupId(this.ada);
        localEntitlementGroupMember2.setId("" + this.adc);
        localEntitlementGroupMember2.setMemberType(this.ac9);
        localEntitlementGroupMember2.setMemberSubType(this.ac6);
        bool = Entitlements.canAdministerAnyGroup(localEntitlementGroupMember2);
      }
      Object localObject1 = localEntitlementTypePropertyLists.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (EntitlementTypePropertyList)((Iterator)localObject1).next();
        if (!((EntitlementTypePropertyList)localObject2).isPropertySet("hide", "yes")) {
          if ((!this.ac8) || ((this.ac8) && (EntitlementsUtil.isCrossAccount((EntitlementTypePropertyList)localObject2)))) {
            if (((EntitlementTypePropertyList)localObject2).isPropertySet("admin partner"))
            {
              localObject3 = new Entitlement(((EntitlementTypePropertyList)localObject2).getPropertyValue("admin partner", 0), null, null);
              if (EntitlementsUtil.checkEntitlementAndParents(localEntitlementGroupMember1, (Entitlement)localObject3) != null) {}
            }
            else
            {
              localObject3 = new Entitlement(((EntitlementTypePropertyList)localObject2).getOperationName(), null, null);
              int i = 0;
              if (this.adc == -1)
              {
                if (this.ac8) {
                  i = EntitlementsUtil.checkEntitlementAndParents(localEntitlementGroup.getParentId(), (Entitlement)localObject3) == null ? 1 : 0;
                } else {
                  i = EntitlementsUtil.checkEntitlementAndParents(localEntitlementGroup.getGroupId(), (Entitlement)localObject3) == null ? 1 : 0;
                }
              }
              else if (this.ac8) {
                i = EntitlementsUtil.checkEntitlementAndParents(this.ada, (Entitlement)localObject3) == null ? 1 : 0;
              } else {
                i = EntitlementsUtil.checkEntitlementAndParents(localEntitlementGroupMember2, (Entitlement)localObject3) == null ? 1 : 0;
              }
              if ((bool) && (i == 0) && (((EntitlementTypePropertyList)localObject2).isPropertySet("admin partner")))
              {
                ((Entitlement)localObject3).setOperationName(((EntitlementTypePropertyList)localObject2).getPropertyValue("admin partner", 0));
                if (this.adc == -1)
                {
                  if (this.ac8) {
                    i = EntitlementsUtil.checkEntitlementAndParents(localEntitlementGroup.getParentId(), (Entitlement)localObject3) == null ? 1 : 0;
                  } else {
                    i = EntitlementsUtil.checkEntitlementAndParents(localEntitlementGroup.getGroupId(), (Entitlement)localObject3) == null ? 1 : 0;
                  }
                }
                else if (this.ac8) {
                  i = EntitlementsUtil.checkEntitlementAndParents(this.ada, (Entitlement)localObject3) == null ? 1 : 0;
                } else {
                  i = EntitlementsUtil.checkEntitlementAndParents(localEntitlementGroupMember2, (Entitlement)localObject3) == null ? 1 : 0;
                }
              }
              if (i != 0)
              {
                localTreeSet.add(((EntitlementTypePropertyList)localObject2).getPropertyValue("component", 0));
                localHashMap.put(((EntitlementTypePropertyList)localObject2).getPropertyValue("component", 0), EntitlementsUtil.getPropertyValue((TypePropertyList)localObject2, "component", localSecureUser.getLocale()));
              }
            }
          }
        }
      }
      localObject1 = new ArrayList(localTreeSet);
      jdMethod_byte((ArrayList)localObject1);
      localHttpSession.setAttribute(this.ac5, localObject1);
      Object localObject2 = new ArrayList();
      Object localObject3 = ((ArrayList)localObject1).iterator();
      while (((Iterator)localObject3).hasNext()) {
        ((ArrayList)localObject2).add(localHashMap.get(((Iterator)localObject3).next()));
      }
      localHttpSession.setAttribute(this.adb, localObject2);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return null;
  }
  
  private void jdMethod_byte(ArrayList paramArrayList)
  {
    for (int i = 0; i < paramArrayList.size(); i++) {
      if (paramArrayList.get(i).equals("Other"))
      {
        paramArrayList.remove(i);
        paramArrayList.add("Other");
        break;
      }
    }
  }
  
  public void setGroupID(String paramString)
  {
    this.ada = Integer.parseInt(paramString);
  }
  
  public void setUserID(String paramString)
  {
    this.adc = Integer.parseInt(paramString);
  }
  
  public void setMemberType(String paramString)
  {
    this.ac9 = paramString;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.ac6 = paramString;
  }
  
  public void setCategoryValue(String paramString)
  {
    if (!paramString.equals("cross account"))
    {
      Object localObject = this.ac7.get("category");
      if (localObject != null)
      {
        ArrayList localArrayList = null;
        if ((localObject instanceof String))
        {
          localArrayList = new ArrayList();
          localArrayList.add(localObject);
        }
        else
        {
          localArrayList = (ArrayList)localObject;
        }
        localArrayList.add(paramString);
        this.ac7.put("category", localArrayList);
      }
      else
      {
        this.ac7.put("category", paramString);
      }
    }
    else
    {
      this.ac8 = true;
    }
  }
  
  public void setCollectionName(String paramString)
  {
    this.ac5 = paramString;
  }
  
  public void setLocalizedCollectionName(String paramString)
  {
    this.adb = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetEntitledComponents
 * JD-Core Version:    0.7.0.1
 */