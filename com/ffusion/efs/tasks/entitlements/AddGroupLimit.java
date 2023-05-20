package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddGroupLimit
  extends BaseTask
  implements Task
{
  private static final int z = -1;
  private String x;
  private int u = -1;
  private int w = -1;
  private int A = -1;
  private String v;
  private String r;
  private String B;
  private boolean s;
  private boolean q = true;
  private String p = null;
  private static final String y = "FALSE";
  private static final String t = "TRUE";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.error != 0) {
      return this.taskErrorURL;
    }
    if (!jdMethod_byte()) {
      return this.taskErrorURL;
    }
    Entitlement localEntitlement = new Entitlement(this.x, this.r, this.B);
    Limit localLimit1 = new Limit();
    localLimit1.setEntitlement(localEntitlement);
    localLimit1.setGroupId(this.u);
    localLimit1.setParentId(this.w);
    localLimit1.setPeriod(this.A);
    localLimit1.setData(this.v);
    localLimit1.setAllowApproval(this.s);
    localLimit1.setRunningTotalType('G');
    localLimit1.setCrossCurrency(this.q);
    if (this.p == null) {
      this.p = Util.getLimitBaseCurrency();
    }
    localLimit1.setCurrencyCode(this.p);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Limit localLimit2 = null;
    try
    {
      localLimit2 = Entitlements.addGroupLimit(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localLimit1);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("Entitlement_Limit", localLimit2);
      str = this.successURL;
    }
    else
    {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  private boolean jdMethod_byte()
  {
    boolean bool = true;
    if (this.u == -1)
    {
      this.error = 35003;
      bool = false;
    }
    if (this.w == -1)
    {
      this.error = 35021;
      bool = false;
    }
    if (this.A == -1)
    {
      this.error = 35020;
      bool = false;
    }
    if ((this.v == null) || (this.v.length() == 0))
    {
      this.error = 35019;
      bool = false;
    }
    return bool;
  }
  
  public void setOperationName(String paramString)
  {
    this.x = paramString;
  }
  
  public String getOperationName()
  {
    return this.x;
  }
  
  public void setGroupId(String paramString)
  {
    this.u = Integer.parseInt(paramString);
  }
  
  public String getGroupId()
  {
    return Integer.toString(this.u);
  }
  
  public void setParentId(String paramString)
  {
    this.w = Integer.parseInt(paramString);
  }
  
  public String getParentId()
  {
    return Integer.toString(this.w);
  }
  
  public void setPeriod(String paramString)
  {
    this.A = Integer.parseInt(paramString);
  }
  
  public String getPeriod()
  {
    return Integer.toString(this.A);
  }
  
  public void setData(String paramString)
  {
    this.v = paramString;
  }
  
  public String getData()
  {
    return this.v;
  }
  
  public void setObjectType(String paramString)
  {
    this.r = paramString;
  }
  
  public String getObjectType()
  {
    return this.r;
  }
  
  public void setObjectId(String paramString)
  {
    this.B = paramString;
  }
  
  public String getObjectId()
  {
    return this.B;
  }
  
  public void setAllowApproval(String paramString)
  {
    if (paramString.equalsIgnoreCase("TRUE")) {
      this.s = true;
    } else if (paramString.equalsIgnoreCase("FALSE")) {
      this.s = false;
    } else {
      this.error = 35031;
    }
  }
  
  public String getAllowApproval()
  {
    if (this.s) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setCrossCurrency(String paramString)
  {
    if (paramString.equalsIgnoreCase("TRUE")) {
      this.q = true;
    } else if (paramString.equalsIgnoreCase("FALSE")) {
      this.q = false;
    } else {
      this.error = 35046;
    }
  }
  
  public String getCrossCurrency()
  {
    if (this.q) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.p = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.p;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.AddGroupLimit
 * JD-Core Version:    0.7.0.1
 */