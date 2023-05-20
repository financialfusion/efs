package com.ffusion.tasks.reporting;

import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SortEntitlementTypes
  extends BaseTask
  implements Task
{
  private String Ut;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)localHttpSession.getAttribute(this.Ut);
    Iterator localIterator = localEntitlementTypePropertyLists.iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
      if (!localEntitlementTypePropertyList.isPropertySet("display name")) {
        localEntitlementTypePropertyList.addProperty("display name", localEntitlementTypePropertyList.getOperationName());
      }
    }
    Collections.sort(localEntitlementTypePropertyLists, new DisplayNameComparator((Locale)localHttpSession.getAttribute("java.util.Locale")));
    return str;
  }
  
  public void setTypesName(String paramString)
  {
    this.Ut = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.SortEntitlementTypes
 * JD-Core Version:    0.7.0.1
 */