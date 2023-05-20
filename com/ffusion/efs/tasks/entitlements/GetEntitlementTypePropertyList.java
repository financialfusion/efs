package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEntitlementTypePropertyList
  extends BaseTask
  implements Task
{
  protected String _listName = "Entitlement_Type";
  protected String _typeToLookup;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
      localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(new HashMap());
      localHttpSession.setAttribute(this._listName, localEntitlementTypePropertyLists.getByOperationName(this._typeToLookup));
    }
    catch (Exception localException)
    {
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setListName(String paramString)
  {
    this._listName = paramString;
  }
  
  public String getListName()
  {
    return this._listName;
  }
  
  public void setTypeToLookup(String paramString)
  {
    this._typeToLookup = paramString;
  }
  
  public String getTypeToLookup()
  {
    return this._typeToLookup;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetEntitlementTypePropertyList
 * JD-Core Version:    0.7.0.1
 */