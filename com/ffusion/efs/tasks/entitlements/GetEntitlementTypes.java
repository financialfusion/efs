package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEntitlementTypes
  extends BaseTask
  implements Task
{
  private String L1 = "Entitlement_Types";
  private ArrayList L0 = new ArrayList();
  private ArrayList L2 = new ArrayList();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      ArrayList localArrayList = null;
      if (this.L0.size() == 1)
      {
        localArrayList = Entitlements.getEntitlementTypes((String)this.L0.get(0), (String)this.L2.get(0));
      }
      else
      {
        HashMap localHashMap = w();
        localArrayList = Entitlements.getEntitlementTypes(localHashMap);
      }
      localHttpSession.setAttribute(this.L1, localArrayList);
    }
    catch (Exception localException)
    {
      return super.getTaskErrorURL();
    }
    return super.getSuccessURL();
  }
  
  private HashMap w()
  {
    HashMap localHashMap = new HashMap();
    for (int i = 0; i < this.L0.size(); i++) {
      jdMethod_try(localHashMap, (String)this.L0.get(i), (String)this.L2.get(i));
    }
    return localHashMap;
  }
  
  private void jdMethod_try(HashMap paramHashMap, String paramString1, String paramString2)
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
      this.L0.add(paramString);
    }
  }
  
  public void setSearchCritValue(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() <= 0)) {
      this.L0.remove(this.L0.size() - 1);
    } else {
      this.L2.add(paramString);
    }
  }
  
  public void setListName(String paramString)
  {
    this.L1 = paramString;
  }
  
  public String getListName()
  {
    return this.L1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetEntitlementTypes
 * JD-Core Version:    0.7.0.1
 */