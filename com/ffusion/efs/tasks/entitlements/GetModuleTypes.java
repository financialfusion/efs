package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.Module;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.CollatorUtil;
import com.ffusion.util.logging.AuditLogUtil;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetModuleTypes
  extends BaseTask
  implements Task
{
  private String Mh = "Module_Types";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = getLocale(localHttpSession, localSecureUser);
    try
    {
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties("module", "yes");
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      for (int i = 0; i < localEntitlementTypePropertyLists.size(); i++)
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localEntitlementTypePropertyLists.get(i);
        String str1 = localEntitlementTypePropertyList.getPropertyValue("module_name", 0);
        int j = AuditLogUtil.getModuleIdFromModuleName(str1, localLocale);
        if (!localArrayList2.contains(new Integer(j)))
        {
          localArrayList2.add(new Integer(j));
          String str2 = "TransactionModuleName";
          str2 = str2 + j;
          String str3 = AuditLogUtil.getModuleNameFromModule(j, localLocale);
          localArrayList1.add(new Module(j, str1, str3));
        }
      }
      Collections.sort(localArrayList1, new Comparator()
      {
        private final Locale val$locale;
        
        public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
        {
          int i = 0;
          String str1 = ((Module)paramAnonymousObject1).getDisplayName();
          String str2 = ((Module)paramAnonymousObject2).getDisplayName();
          Collator localCollator = CollatorUtil.getCollator(this.val$locale);
          i = localCollator.compare(str1, str2);
          return i;
        }
      });
      localHttpSession.setAttribute(this.Mh, localArrayList1);
    }
    catch (Exception localException)
    {
      return super.getTaskErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setListName(String paramString)
  {
    this.Mh = paramString;
  }
  
  public String getListName()
  {
    return this.Mh;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetModuleTypes
 * JD-Core Version:    0.7.0.1
 */