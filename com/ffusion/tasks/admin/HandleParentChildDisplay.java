package com.ffusion.tasks.admin;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HandleParentChildDisplay
  extends BaseTask
  implements AdminTask
{
  protected String _listName;
  protected String _prefix;
  protected String _adminPrefix;
  protected ArrayList _parentChildLists;
  protected ArrayList _childParentLists;
  protected String _parentChildListName;
  protected String _childParentListName;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)localHttpSession.getAttribute(this._listName);
    if (localEntitlementTypePropertyLists == null)
    {
      this.error = 4549;
      return super.getTaskErrorURL();
    }
    reorderLists(localEntitlementTypePropertyLists, null, 0);
    localHttpSession.setAttribute(this._listName, localEntitlementTypePropertyLists);
    if (this._prefix != null) {
      jdMethod_int(localEntitlementTypePropertyLists);
    }
    if (this._parentChildListName != null) {
      localHttpSession.setAttribute(this._parentChildListName, this._parentChildLists);
    }
    if (this._childParentListName != null) {
      localHttpSession.setAttribute(this._childParentListName, this._childParentLists);
    }
    return str;
  }
  
  public static void reorderLists(EntitlementTypePropertyLists paramEntitlementTypePropertyLists, EntitlementTypePropertyList paramEntitlementTypePropertyList, int paramInt)
  {
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = new EntitlementTypePropertyLists();
    EntitlementTypePropertyList localEntitlementTypePropertyList1 = 0;
    Iterator localIterator1;
    if (paramEntitlementTypePropertyList == null)
    {
      localIterator1 = paramEntitlementTypePropertyLists.iterator();
      while (localIterator1.hasNext())
      {
        localEntitlementTypePropertyList2 = (EntitlementTypePropertyList)localIterator1.next();
        if ((!localEntitlementTypePropertyList2.isPropertySet("display parent")) || (paramEntitlementTypePropertyLists.getByOperationName(localEntitlementTypePropertyList2.getPropertyValue("display parent", 0)) == null)) {
          localEntitlementTypePropertyLists.add(localEntitlementTypePropertyList2);
        }
      }
      localEntitlementTypePropertyList1 = 0;
    }
    else
    {
      localIterator1 = paramEntitlementTypePropertyLists.iterator();
      while (localIterator1.hasNext())
      {
        localEntitlementTypePropertyList2 = (EntitlementTypePropertyList)localIterator1.next();
        if (localEntitlementTypePropertyList2.isPropertySet("display parent", paramEntitlementTypePropertyList.getOperationName())) {
          localEntitlementTypePropertyLists.add(localEntitlementTypePropertyList2);
        }
      }
      localEntitlementTypePropertyList1 = paramEntitlementTypePropertyLists.indexOf(paramEntitlementTypePropertyList) + 1;
    }
    if (localEntitlementTypePropertyLists.size() == 0) {
      return;
    }
    int i = 0;
    for (EntitlementTypePropertyList localEntitlementTypePropertyList2 = localEntitlementTypePropertyList1; i < localEntitlementTypePropertyLists.size(); localEntitlementTypePropertyList2++)
    {
      Object localObject = paramEntitlementTypePropertyLists.remove(paramEntitlementTypePropertyLists.indexOf(localEntitlementTypePropertyLists.get(i)));
      paramEntitlementTypePropertyLists.add(localEntitlementTypePropertyList2, localObject);
      i++;
    }
    Iterator localIterator2 = localEntitlementTypePropertyLists.iterator();
    while (localIterator2.hasNext())
    {
      localEntitlementTypePropertyList2 = (EntitlementTypePropertyList)localIterator2.next();
      localEntitlementTypePropertyList2.addProperty("indent level", String.valueOf(paramInt));
      reorderLists(paramEntitlementTypePropertyLists, localEntitlementTypePropertyList2, paramInt + 1);
    }
  }
  
  private void jdMethod_int(EntitlementTypePropertyLists paramEntitlementTypePropertyLists)
  {
    this._parentChildLists = new ArrayList();
    this._childParentLists = new ArrayList();
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    for (int i = 0; i < paramEntitlementTypePropertyLists.size(); i++)
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)paramEntitlementTypePropertyLists.get(i);
      localHashMap1.put(localEntitlementTypePropertyList.getOperationName(), new Integer(i));
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      localArrayList1.add(this._prefix + i);
      if (this._adminPrefix != null) {
        localArrayList2.add(this._adminPrefix + i);
      }
      if ((localEntitlementTypePropertyList.isPropertySet("control parent")) && (paramEntitlementTypePropertyLists.getByOperationName(localEntitlementTypePropertyList.getPropertyValue("control parent", 0)) != null)) {
        for (int j = 0; j < localEntitlementTypePropertyList.numPropertyValues("control parent"); j++)
        {
          String str1 = this._prefix + localHashMap1.get(localEntitlementTypePropertyList.getPropertyValue("control parent", j));
          ArrayList localArrayList3;
          if (localHashMap2.containsKey(str1))
          {
            localArrayList3 = (ArrayList)this._parentChildLists.get(((Integer)localHashMap2.get(str1)).intValue());
          }
          else
          {
            localArrayList3 = new ArrayList();
            localArrayList3.add(str1);
            localHashMap2.put(str1, new Integer(this._parentChildLists.size()));
            this._parentChildLists.add(localArrayList3);
          }
          localArrayList3.add(this._prefix + i);
          localArrayList1.add(str1);
          if (this._adminPrefix != null)
          {
            String str2 = this._adminPrefix + localHashMap1.get(localEntitlementTypePropertyList.getPropertyValue("control parent", j));
            ArrayList localArrayList4;
            if (localHashMap2.containsKey(str2))
            {
              localArrayList4 = (ArrayList)this._parentChildLists.get(((Integer)localHashMap2.get(str2)).intValue());
            }
            else
            {
              localArrayList4 = new ArrayList();
              localArrayList4.add(str2);
              localHashMap2.put(str2, new Integer(this._parentChildLists.size()));
              this._parentChildLists.add(localArrayList4);
            }
            localArrayList4.add(this._adminPrefix + i);
            localArrayList2.add(str2);
          }
        }
      }
      if (localArrayList1.size() > 1)
      {
        this._childParentLists.add(localArrayList1);
        if (this._adminPrefix != null) {
          this._childParentLists.add(localArrayList2);
        }
      }
    }
  }
  
  public static ArrayList buildParentChildMapFromParentList(EntitlementTypePropertyLists paramEntitlementTypePropertyLists, HashMap paramHashMap)
    throws CSILException
  {
    ArrayList localArrayList1 = new ArrayList();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
    if (paramHashMap == null) {
      localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(new HashMap());
    } else {
      localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(paramHashMap);
    }
    Iterator localIterator = paramEntitlementTypePropertyLists.iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
      String str = localEntitlementTypePropertyList.getOperationName();
      ArrayList localArrayList2 = new ArrayList();
      localArrayList2.add(str);
      jdMethod_for(str, localArrayList2, localEntitlementTypePropertyLists);
      localArrayList1.add(localArrayList2);
    }
    return localArrayList1;
  }
  
  private static void jdMethod_for(String paramString, ArrayList paramArrayList, EntitlementTypePropertyLists paramEntitlementTypePropertyLists)
  {
    Iterator localIterator = paramEntitlementTypePropertyLists.iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
      if (localEntitlementTypePropertyList.isPropertySet("control parent"))
      {
        String str1 = localEntitlementTypePropertyList.getOperationName();
        int i = localEntitlementTypePropertyList.numPropertyValues("control parent");
        for (int j = 0; j < i; j++)
        {
          String str2 = localEntitlementTypePropertyList.getPropertyValue("control parent", j);
          if ((str2.equals(paramString)) && (!paramArrayList.contains(str1)))
          {
            paramArrayList.add(str1);
            jdMethod_for(str1, paramArrayList, paramEntitlementTypePropertyLists);
            break;
          }
        }
      }
    }
  }
  
  public void setEntitlementTypePropertyLists(String paramString)
  {
    this._listName = paramString;
  }
  
  public void setPrefixName(String paramString)
  {
    this._prefix = paramString;
  }
  
  public String getPrefixName()
  {
    return this._prefix;
  }
  
  public void setAdminPrefixName(String paramString)
  {
    this._adminPrefix = paramString;
  }
  
  public String getAdminPrefixName()
  {
    return this._adminPrefix;
  }
  
  public void setParentChildName(String paramString)
  {
    this._parentChildListName = paramString;
  }
  
  public void setChildParentName(String paramString)
  {
    this._childParentListName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.HandleParentChildDisplay
 * JD-Core Version:    0.7.0.1
 */