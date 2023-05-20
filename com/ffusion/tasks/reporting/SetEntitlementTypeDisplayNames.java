package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetEntitlementTypeDisplayNames
  extends BaseTask
  implements Task
{
  private String Ug;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = localSecureUser.getLocale();
    String str2 = ReportConsts.getText(10415, localLocale);
    EntitlementTypePropertyLists localEntitlementTypePropertyLists1 = (EntitlementTypePropertyLists)localHttpSession.getAttribute(this.Ug);
    EntitlementTypePropertyLists localEntitlementTypePropertyLists2 = null;
    try
    {
      localEntitlementTypePropertyLists2 = Entitlements.getEntitlementTypesWithProperties(new HashMap());
    }
    catch (CSILException localCSILException) {}
    Iterator localIterator = localEntitlementTypePropertyLists1.iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList1 = (EntitlementTypePropertyList)localIterator.next();
      String str3 = EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList1, "display name", localLocale);
      if (str3 == null)
      {
        localEntitlementTypePropertyList1.setCurrentProperty("display name");
        str3 = localEntitlementTypePropertyList1.getValue();
      }
      localEntitlementTypePropertyList1.setCurrentProperty("display parent");
      String str4 = localEntitlementTypePropertyList1.getValue();
      if ((localEntitlementTypePropertyList1.isPropertySet("category", "per ACH company")) || (localEntitlementTypePropertyList1.isPropertySet("category", "cross ACH company")))
      {
        localEntitlementTypePropertyList1.setCurrentProperty("module_name");
        String str5 = localEntitlementTypePropertyList1.getValue();
        if ((str4.equals("")) && (str5.equals("ACH")))
        {
          localEntitlementTypePropertyList1.clearProperty("display name");
          localEntitlementTypePropertyList1.addProperty("display name", str2 + " - " + str3);
        }
        else if (!str4.equals(""))
        {
          EntitlementTypePropertyList localEntitlementTypePropertyList2 = localEntitlementTypePropertyLists2.getByOperationName(str4);
          String str6 = EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList2, "display name", localLocale);
          if (str6 == null)
          {
            localEntitlementTypePropertyList2.setCurrentProperty("display name");
            str6 = localEntitlementTypePropertyList2.getValue();
          }
          EntitlementsUtil.setPropertyValue(localEntitlementTypePropertyList1, "display name", str6 + " - " + str3, localLocale);
        }
      }
    }
    return str1;
  }
  
  public void setTypesName(String paramString)
  {
    this.Ug = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.SetEntitlementTypeDisplayNames
 * JD-Core Version:    0.7.0.1
 */