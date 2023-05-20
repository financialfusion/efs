package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetGlobalMessageFilters
  extends BaseTask
{
  public static final String GLOBAL_MESSAGE_FEATURES = "gmFeatures";
  static final String UZ = "category";
  static final String US = "hide";
  static final String UX = "yes";
  static final String UT = "display name";
  static final String UY = "per consumer";
  static final String UW = "per business";
  static final String UU = "per package";
  protected String _featuresSessionName = "gmFeatures";
  protected String _servicePackage = "";
  protected String _groupType = "";
  private String UV = "per package";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = localSecureUser.getLocale();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = new EntitlementTypePropertyLists();
    HashMap localHashMap1 = new HashMap();
    try
    {
      ArrayList localArrayList = com.ffusion.csil.core.Entitlements.getEntitlementTypes("hide", "yes");
      HashMap localHashMap2 = new HashMap();
      localHashMap2.put("category", this.UV);
      switch (Integer.parseInt(this._groupType))
      {
      case 0: 
      case 4: 
        localHashMap2.put("category", this.UV);
        localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(localHashMap2);
        break;
      case 3: 
        localHashMap2.put("category", "per consumer");
        localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(localHashMap2);
        break;
      case 2: 
        localHashMap2.put("category", "per business");
        localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(localHashMap2);
        break;
      case 1: 
        if ((this._servicePackage != null) && (!this._servicePackage.equals(""))) {
          localEntitlementTypePropertyLists = jdMethod_byte(Integer.parseInt(this._servicePackage));
        } else {
          throw new IOException("Service Package Not Specified");
        }
        break;
      }
      Object localObject = null;
      Iterator localIterator;
      if ((localArrayList != null) && (localArrayList.size() > 0))
      {
        localIterator = localEntitlementTypePropertyLists.iterator();
        while (localIterator.hasNext())
        {
          localObject = localIterator.next();
          if (localArrayList.contains(((EntitlementTypePropertyList)localObject).getOperationName())) {
            localIterator.remove();
          } else {
            localHashMap1.put(((EntitlementTypePropertyList)localObject).getOperationName(), EntitlementsUtil.getPropertyValue((EntitlementTypePropertyList)localObject, "display name", localLocale));
          }
        }
      }
      else
      {
        localIterator = localEntitlementTypePropertyLists.iterator();
        while (localIterator.hasNext())
        {
          localObject = localIterator.next();
          localHashMap1.put(((EntitlementTypePropertyList)localObject).getOperationName(), EntitlementsUtil.getPropertyValue((EntitlementTypePropertyList)localObject, "display name", localLocale));
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this._featuresSessionName, localHashMap1);
    return str;
  }
  
  public void setFeaturesSessionName(String paramString)
  {
    this._featuresSessionName = paramString;
  }
  
  public void setServicePackage(String paramString)
  {
    this._servicePackage = paramString;
  }
  
  public void setGroupType(String paramString)
  {
    this._groupType = paramString;
  }
  
  private void z(String paramString)
  {
    this.UV = paramString;
  }
  
  private EntitlementTypePropertyLists jdMethod_byte(int paramInt)
    throws CSILException
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("category", this.UV);
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(localHashMap);
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = com.ffusion.csil.core.Entitlements.getCumulativeEntitlements(Integer.parseInt(this._servicePackage));
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = new com.ffusion.csil.beans.entitlements.Entitlements();
    Iterator localIterator = localEntitlements1.iterator();
    while (localIterator.hasNext())
    {
      localObject = (Entitlement)localIterator.next();
      jdMethod_for((Entitlement)localObject, localEntitlements2);
    }
    Object localObject = new EntitlementTypePropertyLists();
    for (int i = 0; i < localEntitlementTypePropertyLists.size(); i++)
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localEntitlementTypePropertyLists.get(i);
      int j = 0;
      for (int k = 0; k < localEntitlements2.size(); k++)
      {
        Entitlement localEntitlement = (Entitlement)localEntitlements2.get(k);
        if (localEntitlementTypePropertyList.getOperationName().equals(localEntitlement.getOperationName()))
        {
          j = 1;
          localEntitlements2.remove(k);
          break;
        }
      }
      if (j == 0) {
        ((EntitlementTypePropertyLists)localObject).add(localEntitlementTypePropertyList);
      }
    }
    return localObject;
  }
  
  private void jdMethod_for(Entitlement paramEntitlement, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    if (paramEntitlements.indexOf(paramEntitlement) == -1)
    {
      paramEntitlements.add(paramEntitlement);
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties("control parent", paramEntitlement.getOperationName());
      if (localEntitlementTypePropertyLists != null) {
        for (int i = 0; i < localEntitlementTypePropertyLists.size(); i++)
        {
          EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localEntitlementTypePropertyLists.get(i);
          Entitlement localEntitlement = new Entitlement(localEntitlementTypePropertyList.getOperationName(), null, null);
          jdMethod_for(localEntitlement, paramEntitlements);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetGlobalMessageFilters
 * JD-Core Version:    0.7.0.1
 */