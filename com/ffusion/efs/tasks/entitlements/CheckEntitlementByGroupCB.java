package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckEntitlementByGroupCB
  extends BaseTask
  implements Task
{
  private static String au = "TRUE";
  private static String ar = "FALSE";
  private String as = null;
  private String at = null;
  private String ap = null;
  private String ao = null;
  private String aq = ar;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (!jdMethod_else())
    {
      jdMethod_char();
      return this.taskErrorURL;
    }
    int i = -1;
    try
    {
      i = Integer.parseInt(this.ao);
    }
    catch (Exception localException)
    {
      this.error = 35035;
      jdMethod_char();
      return super.getTaskErrorURL();
    }
    try
    {
      Entitlement localEntitlement = new Entitlement(this.as, this.at, this.ap);
      boolean bool = Entitlements.checkEntitlement(i, localEntitlement);
      if (bool) {
        bool = EntitlementsUtil.checkEntitlementAndParents(i, localEntitlement) == null;
      }
      if (bool) {
        this.aq = au;
      } else {
        this.aq = ar;
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  private void jdMethod_char()
  {
    this.as = null;
    this.at = null;
    this.ap = null;
    this.ao = null;
    this.aq = ar;
  }
  
  private boolean jdMethod_else()
  {
    boolean bool = true;
    if ((this.ao == null) || (this.ao.length() == 0))
    {
      this.error = 35003;
      bool = false;
    }
    return bool;
  }
  
  public void setOperationName(String paramString)
  {
    this.as = paramString;
  }
  
  public String getOperationName()
  {
    return this.as;
  }
  
  public void setObjectType(String paramString)
  {
    this.at = paramString;
  }
  
  public String getObjectType()
  {
    return this.at;
  }
  
  public void setObjectId(String paramString)
  {
    this.ap = paramString;
  }
  
  public String getObjectId()
  {
    return this.ap;
  }
  
  public void setGroupId(String paramString)
  {
    this.ao = paramString;
  }
  
  public String getGroupId()
  {
    return this.ao;
  }
  
  public String getEntitled()
  {
    return this.aq;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.CheckEntitlementByGroupCB
 * JD-Core Version:    0.7.0.1
 */