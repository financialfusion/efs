package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.beans.entitlements.LimitTypePropertyLists;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetLimitTypesWithProperties
  extends BaseTask
  implements Task
{
  private String Lc = "Limit_Types";
  private ArrayList Lb = new ArrayList();
  private ArrayList Ld = new ArrayList();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      LimitTypePropertyLists localLimitTypePropertyLists = null;
      if (this.Lb.size() == 1)
      {
        localLimitTypePropertyLists = Entitlements.getLimitTypesWithProperties((String)this.Lb.get(0), (String)this.Ld.get(0));
      }
      else
      {
        HashMap localHashMap = s();
        localLimitTypePropertyLists = Entitlements.getLimitTypesWithProperties(localHashMap);
      }
      localHttpSession.setAttribute(this.Lc, localLimitTypePropertyLists);
    }
    catch (Exception localException)
    {
      return super.getTaskErrorURL();
    }
    return super.getSuccessURL();
  }
  
  private HashMap s()
  {
    HashMap localHashMap = new HashMap();
    for (int i = 0; i < this.Lb.size(); i++) {
      jdMethod_int(localHashMap, (String)this.Lb.get(i), (String)this.Ld.get(i));
    }
    return localHashMap;
  }
  
  private void jdMethod_int(HashMap paramHashMap, String paramString1, String paramString2)
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
      this.Lb.add(paramString);
    }
  }
  
  public void setSearchCritValue(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() <= 0)) {
      this.Lb.remove(this.Lb.size() - 1);
    } else {
      this.Ld.add(paramString);
    }
  }
  
  public void setListName(String paramString)
  {
    this.Lc = paramString;
  }
  
  public String getListName()
  {
    return this.Lc;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetLimitTypesWithProperties
 * JD-Core Version:    0.7.0.1
 */