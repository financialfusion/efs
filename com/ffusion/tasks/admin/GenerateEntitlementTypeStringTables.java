package com.ffusion.tasks.admin;

import com.ffusion.beans.util.StringTable;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GenerateEntitlementTypeStringTables
  extends BaseTask
  implements AdminTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(new HashMap());
      StringTable localStringTable1 = new StringTable();
      StringTable localStringTable2 = new StringTable();
      for (int i = 0; i < localEntitlementTypePropertyLists.size(); i++)
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localEntitlementTypePropertyLists.get(i);
        localStringTable1.setKey(localEntitlementTypePropertyList.getOperationName());
        localStringTable2.setKey(localEntitlementTypePropertyList.getOperationName());
        if (localEntitlementTypePropertyList.isPropertySet("display name")) {
          localStringTable1.setValue(localEntitlementTypePropertyList.getPropertyValue("display name", 0));
        } else {
          localStringTable1.setValue(localEntitlementTypePropertyList.getOperationName());
        }
        if (localEntitlementTypePropertyList.isPropertySet("hide")) {
          localStringTable2.setValue(localEntitlementTypePropertyList.getPropertyValue("hide", 0));
        } else {
          localStringTable2.setValue("no");
        }
      }
      localHttpSession.setAttribute("DisplayNames", localStringTable1);
      localHttpSession.setAttribute("Hidden", localStringTable2);
    }
    catch (Exception localException)
    {
      return super.getTaskErrorURL();
    }
    return super.getSuccessURL();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GenerateEntitlementTypeStringTables
 * JD-Core Version:    0.7.0.1
 */