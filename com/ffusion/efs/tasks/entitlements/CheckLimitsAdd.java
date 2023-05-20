package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckLimitsAdd
  extends BaseTask
  implements Task
{
  private String Ma;
  private String Mc;
  private String L9;
  private BigDecimal Me = null;
  private Date Mg;
  private String Mb = null;
  private static final String Mf = "FALSE";
  private static final String Md = "TRUE";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (!x()) {
      return this.taskErrorURL;
    }
    Entitlement localEntitlement = new Entitlement(this.Ma, this.Mc, this.L9);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Limits localLimits = null;
    try
    {
      if (this.Mb == null) {
        this.Mb = Util.getLimitBaseCurrency();
      }
      localLimits = Entitlements.checkLimitsAdd(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlement, this.Me, this.Mb, this.Mg);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("Entitlement_Limits", localLimits);
      str = this.successURL;
    }
    else
    {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  private boolean x()
  {
    boolean bool = true;
    if ((this.Ma == null) || (this.Ma.length() == 0))
    {
      this.error = 35028;
      bool = false;
    }
    if ((this.Mc == null) || (this.Mc.length() == 0))
    {
      this.error = 35029;
      bool = false;
    }
    if ((this.L9 == null) || (this.L9.length() == 0))
    {
      this.error = 35030;
      bool = false;
    }
    if (this.Me == null)
    {
      this.error = 35008;
      bool = false;
    }
    if (this.Mg == null)
    {
      this.error = 35009;
      bool = false;
    }
    return bool;
  }
  
  public void setOperationName(String paramString)
  {
    this.Ma = paramString;
  }
  
  public String getOperationName()
  {
    return this.Ma;
  }
  
  public void setObjectType(String paramString)
  {
    this.Mc = paramString;
  }
  
  public String getObjectType()
  {
    return this.Mc;
  }
  
  public void setObjectId(String paramString)
  {
    this.L9 = paramString;
  }
  
  public String getObjectId()
  {
    return this.L9;
  }
  
  public void setAmount(String paramString)
  {
    this.Me = new BigDecimal(paramString);
  }
  
  public String getAmount()
  {
    return this.Me.toString();
  }
  
  public void setTransactionDate(String paramString)
  {
    try
    {
      this.Mg = DateFormatUtil.getFormatter("MM/dd/yyyy").parse(paramString);
    }
    catch (Exception localException)
    {
      this.error = 35009;
    }
  }
  
  public Date getTransactionDate()
  {
    return this.Mg;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.Mb = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.Mb;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.CheckLimitsAdd
 * JD-Core Version:    0.7.0.1
 */