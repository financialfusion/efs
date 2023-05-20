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

public class GetLimitTypes
  extends BaseTask
  implements Task
{
  private String LS = "Limit_Types";
  private ArrayList LR = new ArrayList();
  private ArrayList LT = new ArrayList();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      ArrayList localArrayList = null;
      if (this.LR.size() == 1)
      {
        localArrayList = Entitlements.getLimitTypes((String)this.LR.get(0), (String)this.LT.get(0));
      }
      else
      {
        HashMap localHashMap = v();
        localArrayList = Entitlements.getLimitTypes(localHashMap);
      }
      localHttpSession.setAttribute(this.LS, localArrayList);
    }
    catch (Exception localException)
    {
      return super.getTaskErrorURL();
    }
    return super.getSuccessURL();
  }
  
  private HashMap v()
  {
    HashMap localHashMap = new HashMap();
    for (int i = 0; i < this.LR.size(); i++) {
      jdMethod_new(localHashMap, (String)this.LR.get(i), (String)this.LT.get(i));
    }
    return localHashMap;
  }
  
  private void jdMethod_new(HashMap paramHashMap, String paramString1, String paramString2)
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
      this.LR.add(paramString);
    }
  }
  
  public void setSearchCritValue(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() <= 0)) {
      this.LR.remove(this.LR.size() - 1);
    } else {
      this.LT.add(paramString);
    }
  }
  
  public void setListName(String paramString)
  {
    this.LS = paramString;
  }
  
  public String getListName()
  {
    return this.LS;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetLimitTypes
 * JD-Core Version:    0.7.0.1
 */