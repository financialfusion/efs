package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEntitlementGroupPropertyValues
  extends BaseTask
  implements Task
{
  private String aI;
  private String aJ;
  private String aH;
  private String aG;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    if (this.aH == null)
    {
      this.error = 35045;
      return this.taskErrorURL;
    }
    if (this.aJ == null)
    {
      this.error = 35001;
      return this.taskErrorURL;
    }
    EntitlementGroup localEntitlementGroup = null;
    try
    {
      Integer localInteger = new Integer(this.aJ);
      localEntitlementGroup = Entitlements.getEntitlementGroup(localInteger.intValue());
    }
    catch (Exception localException)
    {
      this.error = 35035;
      return this.taskErrorURL;
    }
    String str2 = (String)((HashMap)localHttpSession.getAttribute("firstChoicePropertyNamesMap")).get(this.aH);
    String str3 = (String)((HashMap)localHttpSession.getAttribute("secondChoicePropertyNamesMap")).get(this.aH);
    String str4 = EntitlementsUtil.getPropertyValue(localEntitlementGroup.getProperties(), str2, str3, this.aH);
    if (str4 != null) {
      setPropertyValue(str4);
    } else {
      setPropertyValue(this.aI);
    }
    return str1;
  }
  
  public void setDefaultPropertyValue(String paramString)
  {
    this.aI = paramString;
  }
  
  public void setEntGroupId(String paramString)
  {
    this.aJ = paramString;
  }
  
  public void setPropertyName(String paramString)
  {
    this.aH = paramString;
  }
  
  public String getPropertyValue()
  {
    return this.aG;
  }
  
  public void setPropertyValue(String paramString)
  {
    this.aG = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetEntitlementGroupPropertyValues
 * JD-Core Version:    0.7.0.1
 */