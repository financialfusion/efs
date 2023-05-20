package com.ffusion.tasks.approvals;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SortACHApprovalsEntitlements
  extends BaseTask
  implements IApprovalsTask
{
  private String aNY;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = getSuccessURL();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.aNY == null)
    {
      this.error = 31005;
      str = getTaskErrorURL();
    }
    else
    {
      try
      {
        EntitlementTypePropertyLists localEntitlementTypePropertyLists1 = (EntitlementTypePropertyLists)localHttpSession.getAttribute(this.aNY);
        if (localEntitlementTypePropertyLists1 == null)
        {
          this.error = 31007;
          str = getTaskErrorURL();
        }
        else
        {
          EntitlementTypePropertyLists localEntitlementTypePropertyLists2 = Entitlements.getEntitlementTypesWithProperties(new HashMap());
          EntitlementTypePropertyLists localEntitlementTypePropertyLists3 = new EntitlementTypePropertyLists();
          EntitlementTypePropertyLists localEntitlementTypePropertyLists4 = new EntitlementTypePropertyLists();
          EntitlementTypePropertyLists localEntitlementTypePropertyLists5 = null;
          Iterator localIterator = localEntitlementTypePropertyLists1.iterator();
          while (localIterator.hasNext())
          {
            EntitlementTypePropertyList localEntitlementTypePropertyList1 = (EntitlementTypePropertyList)localIterator.next();
            if (localEntitlementTypePropertyList1.isPropertySet("module_name"))
            {
              if (!localEntitlementTypePropertyList1.isPropertySet("display name")) {
                localEntitlementTypePropertyList1.addProperty("display name", localEntitlementTypePropertyList1.getOperationName());
              }
              localEntitlementTypePropertyLists3.add(localEntitlementTypePropertyList1);
            }
            else
            {
              EntitlementTypePropertyList localEntitlementTypePropertyList2 = localEntitlementTypePropertyLists2.getByOperationName(localEntitlementTypePropertyList1.getPropertyValue("display parent", 0));
              if (localEntitlementTypePropertyList2.isPropertySet("display name")) {
                localEntitlementTypePropertyList1.addProperty("module_name", localEntitlementTypePropertyList2.getPropertyValue("display name", 0));
              } else {
                localEntitlementTypePropertyList1.addProperty("module_name", localEntitlementTypePropertyList2.getOperationName());
              }
              if (!localEntitlementTypePropertyList1.isPropertySet("display name")) {
                localEntitlementTypePropertyList1.addProperty("display name", localEntitlementTypePropertyList1.getOperationName());
              }
              localEntitlementTypePropertyLists4.add(localEntitlementTypePropertyList1);
            }
          }
          localEntitlementTypePropertyLists3.setSortedBy("module_name,display name");
          localEntitlementTypePropertyLists4.setSortedBy("module_name,display name");
          localEntitlementTypePropertyLists5 = new EntitlementTypePropertyLists();
          localIterator = localEntitlementTypePropertyLists3.iterator();
          while (localIterator.hasNext()) {
            localEntitlementTypePropertyLists5.add((EntitlementTypePropertyList)localIterator.next());
          }
          localIterator = localEntitlementTypePropertyLists4.iterator();
          while (localIterator.hasNext()) {
            localEntitlementTypePropertyLists5.add((EntitlementTypePropertyList)localIterator.next());
          }
          localHttpSession.setAttribute(this.aNY, localEntitlementTypePropertyLists5);
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = getServiceErrorURL();
      }
    }
    return str;
  }
  
  public void setEntitlementsCollectionName(String paramString)
  {
    this.aNY = ((paramString == null) || (paramString.length() == 0) ? null : paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.SortACHApprovalsEntitlements
 * JD-Core Version:    0.7.0.1
 */