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

public class CheckEntitlementByMember
  extends BaseTask
  implements Task
{
  private String cV = null;
  private String c0 = null;
  private String c4 = null;
  private String cZ = null;
  private String c5 = null;
  private String cY = null;
  private String c2 = null;
  private String c1 = "CheckEntitlement";
  private String cX = "AdminInitMap";
  private static String c3 = "TRUE";
  private static String cW = "FALSE";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (!jdMethod_void())
    {
      b();
      return this.taskErrorURL;
    }
    boolean bool1 = true;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroupMember localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
    EntitlementGroupMember localEntitlementGroupMember2 = new EntitlementGroupMember();
    int i = -1;
    try
    {
      i = Integer.parseInt(this.cZ);
      localEntitlementGroupMember2.setEntitlementGroupId(i);
    }
    catch (Exception localException)
    {
      this.error = 35035;
      b();
      return super.getTaskErrorURL();
    }
    localEntitlementGroupMember2.setId(this.c5);
    localEntitlementGroupMember2.setMemberType(this.cY);
    localEntitlementGroupMember2.setMemberSubType(this.c2);
    try
    {
      String str2 = null;
      String str3 = null;
      String str4 = null;
      boolean bool2 = false;
      boolean bool3 = false;
      boolean bool4 = false;
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2);
      Iterator localIterator = localEntitlements.iterator();
      Object localObject;
      while ((bool1) && (localIterator.hasNext()))
      {
        localObject = (Entitlement)localIterator.next();
        str2 = ((Entitlement)localObject).getOperationName();
        str3 = ((Entitlement)localObject).getObjectType();
        str4 = ((Entitlement)localObject).getObjectId();
        bool2 = Strings.areStringsEqual(this.c0, str3);
        bool3 = Strings.areStringsEqual(this.c4, str4);
        bool4 = Strings.areStringsEqual(this.cV, str2);
        bool1 = (!bool2) || (!bool3) || (!bool4);
      }
      if (bool1)
      {
        localObject = jdMethod_for(localHttpSession);
        Entitlement localEntitlement = new Entitlement(this.cV, this.c0, this.c4);
        bool1 = jdMethod_for((HashMap)localObject, localEntitlement, localEntitlements);
      }
      if (bool1) {
        localHttpSession.setAttribute(this.c1, c3);
      } else {
        localHttpSession.setAttribute(this.c1, cW);
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
    b();
    return str1;
  }
  
  private boolean jdMethod_for(HashMap paramHashMap, Entitlement paramEntitlement, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
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
      if (!jdMethod_for(paramHashMap, localEntitlement, paramEntitlements)) {
        return false;
      }
    }
    return true;
  }
  
  private HashMap jdMethod_for(HttpSession paramHttpSession)
    throws CSILException
  {
    HashMap localHashMap = (HashMap)paramHttpSession.getAttribute(this.cX);
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
      paramHttpSession.setAttribute(this.cX, localHashMap);
    }
    return localHashMap;
  }
  
  private void b()
  {
    this.cV = null;
    this.c0 = null;
    this.c4 = null;
  }
  
  private boolean jdMethod_void()
  {
    boolean bool = true;
    if ((this.cZ == null) || (this.cZ.length() == 0))
    {
      this.error = 35003;
      bool = false;
    }
    return bool;
  }
  
  public void setOperationName(String paramString)
  {
    this.cV = paramString;
  }
  
  public String getOperationName()
  {
    return this.cV;
  }
  
  public void setObjectType(String paramString)
  {
    this.c0 = paramString;
  }
  
  public String getObjectType()
  {
    return this.c0;
  }
  
  public void setObjectId(String paramString)
  {
    this.c4 = paramString;
  }
  
  public String getObjectId()
  {
    return this.c4;
  }
  
  public void setGroupId(String paramString)
  {
    this.cZ = paramString;
  }
  
  public String getGroupId()
  {
    return this.cZ;
  }
  
  public void setMemberId(String paramString)
  {
    this.c5 = paramString;
  }
  
  public String getMemberId()
  {
    return this.c5;
  }
  
  public void setMemberType(String paramString)
  {
    this.cY = paramString;
  }
  
  public String getMemberType()
  {
    return this.cY;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.c2 = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.c2;
  }
  
  public void setAttributeName(String paramString)
  {
    this.c1 = paramString;
  }
  
  public String getAttributeName()
  {
    return this.c1;
  }
  
  public void setAdminInitMapName(String paramString)
  {
    this.cX = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.CheckEntitlementByMember
 * JD-Core Version:    0.7.0.1
 */