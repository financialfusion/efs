package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyList;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyLists;
import com.ffusion.csil.beans.entitlements.TypePropertyListComparator;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import com.ffusion.util.ListUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTypesForEditingLimits
  extends BaseTask
  implements Task
{
  private String aKv;
  private String aKx;
  private String aKw;
  private String aKr;
  private String aKB;
  private String aKD;
  private String aKt;
  private String aKs;
  private String aKz;
  private String aKA;
  private String aKE;
  private String aKC;
  private String aKu;
  private String aKy;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("category", this.aKv);
      if (this.aKx != null) {
        localHashMap.put("component", this.aKx);
      }
      int i = (this.aKw != null) && (!this.aKw.equals("")) ? 1 : 0;
      int j = (this.aKr != null) && (!this.aKr.equals("")) ? 1 : 0;
      int k = (this.aKB != null) && (!this.aKB.equals("")) ? 1 : 0;
      int m = (this.aKD != null) && (!this.aKD.equals("")) ? 1 : 0;
      int n = (this.aKt != null) && (!this.aKt.equals("")) ? 1 : 0;
      int i1 = (this.aKs != null) && (!this.aKs.equals("")) ? 1 : 0;
      int i2 = (this.aKz != null) && (!this.aKz.equals("")) ? 1 : 0;
      int i3 = (this.aKA != null) && (!this.aKA.equals("")) ? 1 : 0;
      int i4 = (this.aKE != null) && (!this.aKE.equals("")) ? 1 : 0;
      int i5 = (this.aKC != null) && (!this.aKC.equals("")) ? 1 : 0;
      int i6 = (this.aKu != null) && (!this.aKu.equals("")) ? 1 : 0;
      int i7 = (this.aKy != null) && (!this.aKy.equals("")) ? 1 : 0;
      LimitTypePropertyLists localLimitTypePropertyLists = Entitlements.getLimitTypesWithProperties(localHashMap);
      EntitlementTypePropertyLists localEntitlementTypePropertyLists1 = Entitlements.getEntitlementTypesWithProperties(localHashMap);
      EntitlementTypePropertyLists localEntitlementTypePropertyLists2 = null;
      if (i != 0) {
        localEntitlementTypePropertyLists2 = new EntitlementTypePropertyLists();
      }
      EntitlementTypePropertyLists localEntitlementTypePropertyLists3 = null;
      if (k != 0) {
        localEntitlementTypePropertyLists3 = new EntitlementTypePropertyLists();
      }
      EntitlementTypePropertyLists localEntitlementTypePropertyLists4 = null;
      if (j != 0) {
        localEntitlementTypePropertyLists4 = new EntitlementTypePropertyLists();
      }
      EntitlementTypePropertyLists localEntitlementTypePropertyLists5 = null;
      if (m != 0) {
        localEntitlementTypePropertyLists5 = new EntitlementTypePropertyLists();
      }
      EntitlementTypePropertyLists localEntitlementTypePropertyLists6 = null;
      if (n != 0) {
        localEntitlementTypePropertyLists6 = new EntitlementTypePropertyLists();
      }
      EntitlementTypePropertyLists localEntitlementTypePropertyLists7 = null;
      if (i1 != 0) {
        localEntitlementTypePropertyLists7 = new EntitlementTypePropertyLists();
      }
      EntitlementTypePropertyLists localEntitlementTypePropertyLists8 = null;
      if (i2 != 0) {
        localEntitlementTypePropertyLists8 = new EntitlementTypePropertyLists();
      }
      EntitlementTypePropertyLists localEntitlementTypePropertyLists9 = null;
      if (i3 != 0) {
        localEntitlementTypePropertyLists9 = new EntitlementTypePropertyLists();
      }
      EntitlementTypePropertyLists localEntitlementTypePropertyLists10 = null;
      if (i4 != 0) {
        localEntitlementTypePropertyLists10 = new EntitlementTypePropertyLists();
      }
      EntitlementTypePropertyLists localEntitlementTypePropertyLists11 = null;
      if (i5 != 0) {
        localEntitlementTypePropertyLists11 = new EntitlementTypePropertyLists();
      }
      EntitlementTypePropertyLists localEntitlementTypePropertyLists12 = null;
      if (i6 != 0) {
        localEntitlementTypePropertyLists12 = new EntitlementTypePropertyLists();
      }
      EntitlementTypePropertyLists localEntitlementTypePropertyLists13 = null;
      if (i7 != 0) {
        localEntitlementTypePropertyLists13 = new EntitlementTypePropertyLists();
      }
      Object localObject1 = localEntitlementTypePropertyLists1.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (EntitlementTypePropertyList)((Iterator)localObject1).next();
        if (!((EntitlementTypePropertyList)localObject2).isPropertySet("hide", "yes"))
        {
          localObject3 = localLimitTypePropertyLists.getByOperationName(((EntitlementTypePropertyList)localObject2).getOperationName());
          int i8 = (localObject3 != null) && (!((LimitTypePropertyList)localObject3).isPropertySet("hide", "yes")) ? 1 : 0;
          if ((i8 != 0) && ((i2 != 0) || (i3 != 0) || (i4 != 0) || (i7 != 0))) {
            ((EntitlementTypePropertyList)localObject2).addProperty("isLimit", "true");
          }
          if (((EntitlementTypePropertyList)localObject2).isPropertySet("category", "per account"))
          {
            if (i8 != 0)
            {
              if (i != 0) {
                localEntitlementTypePropertyLists2.add(localObject2);
              }
            }
            else if (j != 0) {
              localEntitlementTypePropertyLists4.add(localObject2);
            }
            if (i2 != 0) {
              localEntitlementTypePropertyLists8.add(localObject2);
            }
          }
          if (((EntitlementTypePropertyList)localObject2).isPropertySet("category", "per location"))
          {
            if (i8 != 0)
            {
              if (n != 0) {
                localEntitlementTypePropertyLists6.add(localObject2);
              }
            }
            else if (i1 != 0) {
              localEntitlementTypePropertyLists7.add(localObject2);
            }
            if (i4 != 0) {
              localEntitlementTypePropertyLists10.add(localObject2);
            }
          }
          if (((EntitlementTypePropertyList)localObject2).isPropertySet("category", "per wire template"))
          {
            if (i8 != 0)
            {
              if (i5 != 0) {
                localEntitlementTypePropertyLists11.add(localObject2);
              }
            }
            else if (i6 != 0) {
              localEntitlementTypePropertyLists12.add(localObject2);
            }
            if (i7 != 0) {
              localEntitlementTypePropertyLists13.add(localObject2);
            }
          }
          if (EntitlementsUtil.isCrossAccount((EntitlementTypePropertyList)localObject2))
          {
            if (i8 != 0)
            {
              if (k != 0) {
                localEntitlementTypePropertyLists3.add(localObject2);
              }
            }
            else if (m != 0) {
              localEntitlementTypePropertyLists5.add(localObject2);
            }
            if (i3 != 0) {
              localEntitlementTypePropertyLists9.add(localObject2);
            }
          }
        }
      }
      localObject1 = new ArrayList();
      ((List)localObject1).add("display name");
      Object localObject2 = new TypePropertyListComparator(getLocale(localHttpSession, localSecureUser), (List)localObject1);
      Object localObject3 = null;
      if (i != 0)
      {
        localObject3 = localEntitlementTypePropertyLists2.toArray();
        Arrays.sort((Object[])localObject3, (Comparator)localObject2);
        try
        {
          localEntitlementTypePropertyLists2 = (EntitlementTypePropertyLists)ListUtil.toList((Object[])localObject3, EntitlementTypePropertyLists.class);
        }
        catch (IllegalAccessException localIllegalAccessException1) {}catch (InstantiationException localInstantiationException1) {}
        localObject3 = null;
      }
      if (k != 0)
      {
        localObject3 = localEntitlementTypePropertyLists3.toArray();
        Arrays.sort((Object[])localObject3, (Comparator)localObject2);
        try
        {
          localEntitlementTypePropertyLists3 = (EntitlementTypePropertyLists)ListUtil.toList((Object[])localObject3, EntitlementTypePropertyLists.class);
        }
        catch (IllegalAccessException localIllegalAccessException2) {}catch (InstantiationException localInstantiationException2) {}
        localObject3 = null;
      }
      if (j != 0)
      {
        localObject3 = localEntitlementTypePropertyLists4.toArray();
        Arrays.sort((Object[])localObject3, (Comparator)localObject2);
        try
        {
          localEntitlementTypePropertyLists4 = (EntitlementTypePropertyLists)ListUtil.toList((Object[])localObject3, EntitlementTypePropertyLists.class);
        }
        catch (IllegalAccessException localIllegalAccessException3) {}catch (InstantiationException localInstantiationException3) {}
        localObject3 = null;
      }
      if (m != 0)
      {
        localObject3 = localEntitlementTypePropertyLists5.toArray();
        Arrays.sort((Object[])localObject3, (Comparator)localObject2);
        try
        {
          localEntitlementTypePropertyLists5 = (EntitlementTypePropertyLists)ListUtil.toList((Object[])localObject3, EntitlementTypePropertyLists.class);
        }
        catch (IllegalAccessException localIllegalAccessException4) {}catch (InstantiationException localInstantiationException4) {}
        localObject3 = null;
      }
      if (n != 0)
      {
        localObject3 = localEntitlementTypePropertyLists6.toArray();
        Arrays.sort((Object[])localObject3, (Comparator)localObject2);
        try
        {
          localEntitlementTypePropertyLists6 = (EntitlementTypePropertyLists)ListUtil.toList((Object[])localObject3, EntitlementTypePropertyLists.class);
        }
        catch (IllegalAccessException localIllegalAccessException5) {}catch (InstantiationException localInstantiationException5) {}
        localObject3 = null;
      }
      if (i1 != 0)
      {
        localObject3 = localEntitlementTypePropertyLists7.toArray();
        Arrays.sort((Object[])localObject3, (Comparator)localObject2);
        try
        {
          localEntitlementTypePropertyLists7 = (EntitlementTypePropertyLists)ListUtil.toList((Object[])localObject3, EntitlementTypePropertyLists.class);
        }
        catch (IllegalAccessException localIllegalAccessException6) {}catch (InstantiationException localInstantiationException6) {}
        localObject3 = null;
      }
      if (i2 != 0)
      {
        localObject3 = localEntitlementTypePropertyLists8.toArray();
        Arrays.sort((Object[])localObject3, (Comparator)localObject2);
        try
        {
          localEntitlementTypePropertyLists8 = (EntitlementTypePropertyLists)ListUtil.toList((Object[])localObject3, EntitlementTypePropertyLists.class);
        }
        catch (IllegalAccessException localIllegalAccessException7) {}catch (InstantiationException localInstantiationException7) {}
        localObject3 = null;
      }
      if (i3 != 0)
      {
        localObject3 = localEntitlementTypePropertyLists9.toArray();
        Arrays.sort((Object[])localObject3, (Comparator)localObject2);
        try
        {
          localEntitlementTypePropertyLists9 = (EntitlementTypePropertyLists)ListUtil.toList((Object[])localObject3, EntitlementTypePropertyLists.class);
        }
        catch (IllegalAccessException localIllegalAccessException8) {}catch (InstantiationException localInstantiationException8) {}
        localObject3 = null;
      }
      if (i4 != 0)
      {
        localObject3 = localEntitlementTypePropertyLists10.toArray();
        Arrays.sort((Object[])localObject3, (Comparator)localObject2);
        try
        {
          localEntitlementTypePropertyLists10 = (EntitlementTypePropertyLists)ListUtil.toList((Object[])localObject3, EntitlementTypePropertyLists.class);
        }
        catch (IllegalAccessException localIllegalAccessException9) {}catch (InstantiationException localInstantiationException9) {}
        localObject3 = null;
      }
      if (i != 0) {
        localHttpSession.setAttribute(this.aKw, localEntitlementTypePropertyLists2);
      }
      if (j != 0) {
        localHttpSession.setAttribute(this.aKr, localEntitlementTypePropertyLists4);
      }
      if (k != 0) {
        localHttpSession.setAttribute(this.aKB, localEntitlementTypePropertyLists3);
      }
      if (m != 0) {
        localHttpSession.setAttribute(this.aKD, localEntitlementTypePropertyLists5);
      }
      if (n != 0) {
        localHttpSession.setAttribute(this.aKt, localEntitlementTypePropertyLists6);
      }
      if (i1 != 0) {
        localHttpSession.setAttribute(this.aKs, localEntitlementTypePropertyLists7);
      }
      if (i2 != 0) {
        localHttpSession.setAttribute(this.aKz, localEntitlementTypePropertyLists8);
      }
      if (i3 != 0) {
        localHttpSession.setAttribute(this.aKA, localEntitlementTypePropertyLists9);
      }
      if (i4 != 0) {
        localHttpSession.setAttribute(this.aKE, localEntitlementTypePropertyLists10);
      }
      if (i5 != 0) {
        localHttpSession.setAttribute(this.aKC, localEntitlementTypePropertyLists11);
      }
      if (i6 != 0) {
        localHttpSession.setAttribute(this.aKu, localEntitlementTypePropertyLists12);
      }
      if (i7 != 0) {
        localHttpSession.setAttribute(this.aKy, localEntitlementTypePropertyLists13);
      }
    }
    catch (Exception localException)
    {
      return super.getTaskErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setCategoryValue(String paramString)
  {
    this.aKv = paramString;
  }
  
  public void setComponentValue(String paramString)
  {
    this.aKx = paramString;
  }
  
  public void setAccountWithLimitsName(String paramString)
  {
    this.aKw = paramString;
  }
  
  public void setAccountWithoutLimitsName(String paramString)
  {
    this.aKr = paramString;
  }
  
  public void setNonAccountWithLimitsName(String paramString)
  {
    this.aKB = paramString;
  }
  
  public void setNonAccountWithoutLimitsName(String paramString)
  {
    this.aKD = paramString;
  }
  
  public void setLocationWithLimitsName(String paramString)
  {
    this.aKt = paramString;
  }
  
  public void setLocationWithoutLimitsName(String paramString)
  {
    this.aKs = paramString;
  }
  
  public void setAccountMerged(String paramString)
  {
    this.aKz = paramString;
  }
  
  public void setNonAccountMerged(String paramString)
  {
    this.aKA = paramString;
  }
  
  public void setLocationMerged(String paramString)
  {
    this.aKE = paramString;
  }
  
  public void setWireTemplateWithLimitsName(String paramString)
  {
    this.aKC = paramString;
  }
  
  public void setWireTemplateWithoutLimitsName(String paramString)
  {
    this.aKu = paramString;
  }
  
  public void setWireTemplateMerged(String paramString)
  {
    this.aKy = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetTypesForEditingLimits
 * JD-Core Version:    0.7.0.1
 */