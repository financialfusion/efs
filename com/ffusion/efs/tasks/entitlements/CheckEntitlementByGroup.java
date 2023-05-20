package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.Strings;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckEntitlementByGroup
  extends BaseTask
  implements Task
{
  private String Mn = null;
  private String Mp = null;
  private String Mk = null;
  private String Mj = null;
  private String Ml = "CheckEntitlement";
  private String Mi = "AdminInitMap";
  private static String Mo = "TRUE";
  private static String Mm = "FALSE";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (!z())
    {
      y();
      return this.taskErrorURL;
    }
    boolean bool1 = true;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
    int i = -1;
    try
    {
      i = Integer.parseInt(this.Mj);
    }
    catch (Exception localException)
    {
      this.error = 35035;
      y();
      return super.getTaskErrorURL();
    }
    try
    {
      String str2 = null;
      String str3 = null;
      String str4 = null;
      boolean bool2 = false;
      boolean bool3 = false;
      boolean bool4 = false;
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(localEntitlementGroupMember, i);
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(localEntitlementGroupMember, 1);
      if (localEntitlements2 != null) {
        localEntitlements1.addAll(localEntitlements2);
      }
      Iterator localIterator = localEntitlements1.iterator();
      Object localObject;
      while ((bool1) && (localIterator.hasNext()))
      {
        localObject = (Entitlement)localIterator.next();
        str2 = ((Entitlement)localObject).getOperationName();
        str3 = ((Entitlement)localObject).getObjectType();
        str4 = ((Entitlement)localObject).getObjectId();
        bool2 = Strings.areStringsEqual(this.Mp, str3);
        bool3 = Strings.areStringsEqual(this.Mk, str4);
        bool4 = Strings.areStringsEqual(this.Mn, str2);
        bool1 = (!bool2) || (!bool3) || (!bool4);
      }
      if (bool1)
      {
        localObject = r(localHttpSession);
        Entitlement localEntitlement = new Entitlement(this.Mn, this.Mp, this.Mk);
        bool1 = jdMethod_int((HashMap)localObject, localEntitlement, localEntitlements1);
      }
      if (bool1) {
        localHttpSession.setAttribute(this.Ml, Mo);
      } else {
        localHttpSession.setAttribute(this.Ml, Mm);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      str1 = this.successURL;
    } else {
      str1 = this.serviceErrorURL;
    }
    y();
    return str1;
  }
  
  private boolean jdMethod_int(HashMap paramHashMap, Entitlement paramEntitlement, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    int i;
    String str1;
    if (paramHashMap.containsKey(paramEntitlement.getOperationName()))
    {
      i = 1;
      str1 = (String)paramHashMap.get(paramEntitlement.getOperationName());
    }
    else
    {
      i = 0;
      str1 = paramEntitlement.getOperationName();
    }
    if (str1 == null) {
      return true;
    }
    EntitlementTypePropertyList localEntitlementTypePropertyList1 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(str1);
    if (localEntitlementTypePropertyList1 == null) {
      return true;
    }
    int j = localEntitlementTypePropertyList1.numPropertyValues("control parent");
    for (int k = 0; k < j; k++)
    {
      String str2 = localEntitlementTypePropertyList1.getPropertyValue("control parent", k);
      Entitlement localEntitlement = new Entitlement(paramEntitlement);
      if (i != 0)
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList2 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(str2);
        String str3 = localEntitlementTypePropertyList2.getPropertyValue("admin partner", 0);
        if (str3 == null) {
          continue;
        }
        localEntitlement.setOperationName(str3);
      }
      else
      {
        localEntitlement.setOperationName(str2);
      }
      if (paramEntitlements.contains(localEntitlement)) {
        return false;
      }
      if (!jdMethod_int(paramHashMap, localEntitlement, paramEntitlements)) {
        return false;
      }
    }
    return true;
  }
  
  private HashMap r(HttpSession paramHttpSession)
    throws CSILException
  {
    HashMap localHashMap = (HashMap)paramHttpSession.getAttribute(this.Mi);
    if (localHashMap == null)
    {
      localHashMap = new HashMap();
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
      Iterator localIterator = localEntitlementTypePropertyLists.iterator();
      while (localIterator.hasNext())
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
        if (localEntitlementTypePropertyList.isPropertySet("admin partner")) {
          localHashMap.put(localEntitlementTypePropertyList.getPropertyValue("admin partner", 0), localEntitlementTypePropertyList.getOperationName());
        }
      }
      paramHttpSession.setAttribute(this.Mi, localHashMap);
    }
    return localHashMap;
  }
  
  private void y()
  {
    this.Mn = null;
    this.Mp = null;
    this.Mk = null;
    this.Mj = null;
  }
  
  private boolean z()
  {
    boolean bool = true;
    if ((this.Mj == null) || (this.Mj.length() == 0))
    {
      this.error = 35003;
      bool = false;
    }
    return bool;
  }
  
  public void setOperationName(String paramString)
  {
    this.Mn = paramString;
  }
  
  public String getOperationName()
  {
    return this.Mn;
  }
  
  public void setObjectType(String paramString)
  {
    this.Mp = paramString;
  }
  
  public String getObjectType()
  {
    return this.Mp;
  }
  
  public void setObjectId(String paramString)
  {
    this.Mk = paramString;
  }
  
  public String getObjectId()
  {
    return this.Mk;
  }
  
  public void setGroupId(String paramString)
  {
    this.Mj = paramString;
  }
  
  public String getGroupId()
  {
    return this.Mj;
  }
  
  public void setAttributeName(String paramString)
  {
    this.Ml = paramString;
  }
  
  public String getAttributeName()
  {
    return this.Ml;
  }
  
  public void setAdminInitMapName(String paramString)
  {
    this.Mi = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.CheckEntitlementByGroup
 * JD-Core Version:    0.7.0.1
 */