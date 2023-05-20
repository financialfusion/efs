package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEntitlementPropertyValues
  extends BaseTask
  implements Task
{
  private String cw;
  private String cx;
  private String cv;
  private String cu;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = super.getSuccessURL();
    if (this.cx == null)
    {
      this.error = 35043;
      return super.getTaskErrorURL();
    }
    if (this.cv == null)
    {
      this.error = 35045;
      return super.getTaskErrorURL();
    }
    EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)paramHttpServletRequest.getAttribute(this.cx);
    if (localEntitlementTypePropertyList == null) {
      localEntitlementTypePropertyList = (EntitlementTypePropertyList)localHttpSession.getAttribute(this.cx);
    }
    if (localEntitlementTypePropertyList == null)
    {
      this.error = 35044;
      return super.getTaskErrorURL();
    }
    String str2 = (String)((HashMap)localHttpSession.getAttribute("firstChoicePropertyNamesMap")).get(this.cv);
    String str3 = (String)((HashMap)localHttpSession.getAttribute("secondChoicePropertyNamesMap")).get(this.cv);
    String str4 = EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList, str2, str3, this.cv);
    if (str4 != null) {
      setPropertyValue(str4);
    } else {
      setPropertyValue(this.cw);
    }
    return str1;
  }
  
  public void setDefaultPropertyValue(String paramString)
  {
    this.cw = paramString;
  }
  
  public void setEntTypePropertyListName(String paramString)
  {
    this.cx = paramString;
  }
  
  public void setPropertyName(String paramString)
  {
    this.cv = paramString;
  }
  
  public String getPropertyValue()
  {
    return this.cu;
  }
  
  public void setPropertyValue(String paramString)
  {
    this.cu = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetEntitlementPropertyValues
 * JD-Core Version:    0.7.0.1
 */