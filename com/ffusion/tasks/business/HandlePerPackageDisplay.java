package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.TypePropertyListComparator;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.admin.HandleParentChildDisplay;
import com.ffusion.util.ListUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HandlePerPackageDisplay
  extends BaseTask
  implements BusinessTask
{
  protected String _listName;
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
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)localHttpSession.getAttribute(this._listName);
    if (localEntitlementTypePropertyLists == null)
    {
      this.error = 4149;
      return super.getTaskErrorURL();
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("display name");
    TypePropertyListComparator localTypePropertyListComparator = new TypePropertyListComparator(getLocale(localHttpSession, localSecureUser), localArrayList);
    Object[] arrayOfObject = null;
    arrayOfObject = localEntitlementTypePropertyLists.toArray();
    Arrays.sort(arrayOfObject, localTypePropertyListComparator);
    try
    {
      localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)ListUtil.toList(arrayOfObject, EntitlementTypePropertyLists.class);
    }
    catch (IllegalAccessException localIllegalAccessException) {}catch (InstantiationException localInstantiationException) {}
    arrayOfObject = null;
    HandleParentChildDisplay.reorderLists(localEntitlementTypePropertyLists, null, 0);
    localHttpSession.setAttribute(this._listName, localEntitlementTypePropertyLists);
    jdMethod_for(localEntitlementTypePropertyLists);
    if (this._parentChildListName != null) {
      localHttpSession.setAttribute(this._parentChildListName, this._parentChildLists);
    }
    if (this._childParentListName != null) {
      localHttpSession.setAttribute(this._childParentListName, this._childParentLists);
    }
    return str;
  }
  
  private void jdMethod_for(EntitlementTypePropertyLists paramEntitlementTypePropertyLists)
  {
    this._parentChildLists = new ArrayList();
    this._childParentLists = new ArrayList();
    HashMap localHashMap = new HashMap();
    for (int i = 0; i < paramEntitlementTypePropertyLists.size(); i++)
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)paramEntitlementTypePropertyLists.get(i);
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      localArrayList1.add(localEntitlementTypePropertyList.getOperationName());
      if ((localEntitlementTypePropertyList.isPropertySet("control parent")) && (paramEntitlementTypePropertyLists.getByOperationName(localEntitlementTypePropertyList.getPropertyValue("control parent", 0)) != null)) {
        for (int j = 0; j < localEntitlementTypePropertyList.numPropertyValues("control parent"); j++)
        {
          String str = localEntitlementTypePropertyList.getPropertyValue("control parent", j);
          ArrayList localArrayList3;
          if (localHashMap.containsKey(str))
          {
            localArrayList3 = (ArrayList)this._parentChildLists.get(((Integer)localHashMap.get(str)).intValue());
          }
          else
          {
            localArrayList3 = new ArrayList();
            localArrayList3.add(str);
            localHashMap.put(str, new Integer(this._parentChildLists.size()));
            this._parentChildLists.add(localArrayList3);
          }
          localArrayList3.add(localEntitlementTypePropertyList.getOperationName());
          localArrayList1.add(str);
        }
      }
      if (localArrayList1.size() > 1) {
        this._childParentLists.add(localArrayList1);
      }
    }
  }
  
  public void setEntitlementTypePropertyLists(String paramString)
  {
    this._listName = paramString;
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
 * Qualified Name:     com.ffusion.tasks.business.HandlePerPackageDisplay
 * JD-Core Version:    0.7.0.1
 */