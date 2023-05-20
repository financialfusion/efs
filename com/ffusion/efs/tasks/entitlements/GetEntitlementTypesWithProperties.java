package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.admin.HandleParentChildDisplay;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEntitlementTypesWithProperties
  extends BaseTask
  implements Task
{
  private String m = "Entitlement_Types";
  private String k;
  private boolean n = false;
  private ArrayList l = new ArrayList();
  private ArrayList o = new ArrayList();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
      Object localObject;
      if (this.l.size() == 1)
      {
        localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties((String)this.l.get(0), (String)this.o.get(0));
      }
      else
      {
        localObject = jdMethod_try();
        localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties((HashMap)localObject);
      }
      localHttpSession.setAttribute(this.m, localEntitlementTypePropertyLists);
      if ((this.k != null) && (!this.k.equals("")))
      {
        localObject = null;
        HashMap localHashMap = new HashMap();
        if (this.n) {
          localHashMap.put("category", "per ACH company");
        } else {
          localHashMap.put("category", "cross ACH company");
        }
        localObject = HandleParentChildDisplay.buildParentChildMapFromParentList(localEntitlementTypePropertyLists, localHashMap);
        if (localObject != null) {
          localHttpSession.setAttribute(this.k, localObject);
        }
      }
    }
    catch (Exception localException)
    {
      return super.getTaskErrorURL();
    }
    return super.getSuccessURL();
  }
  
  private HashMap jdMethod_try()
  {
    HashMap localHashMap = new HashMap();
    for (int i = 0; i < this.l.size(); i++) {
      jdMethod_for(localHashMap, (String)this.l.get(i), (String)this.o.get(i));
    }
    return localHashMap;
  }
  
  private void jdMethod_for(HashMap paramHashMap, String paramString1, String paramString2)
  {
    Object localObject = paramHashMap.get(paramString1);
    if (localObject == null)
    {
      paramHashMap.put(paramString1, paramString2);
    }
    else
    {
      ArrayList localArrayList;
      if ((localObject instanceof String))
      {
        if (!paramString2.equals(localObject))
        {
          localArrayList = new ArrayList();
          localArrayList.add(localObject);
          localArrayList.add(paramString2);
          paramHashMap.put(paramString1, localArrayList);
        }
      }
      else if ((localObject instanceof ArrayList))
      {
        localArrayList = (ArrayList)localObject;
        if (!localArrayList.contains(paramString2)) {
          localArrayList.add(paramString2);
        }
      }
    }
  }
  
  public void setSearchCritName(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.l.add(paramString);
    }
  }
  
  public void setSearchCritValue(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() <= 0)) {
      this.l.remove(this.l.size() - 1);
    } else {
      this.o.add(paramString);
    }
  }
  
  public void setListName(String paramString)
  {
    this.m = paramString;
  }
  
  public String getListName()
  {
    return this.m;
  }
  
  public void setParentChildName(String paramString)
  {
    this.k = paramString;
  }
  
  public void setPerACHCompanyFlag(String paramString)
  {
    this.n = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetEntitlementTypesWithProperties
 * JD-Core Version:    0.7.0.1
 */