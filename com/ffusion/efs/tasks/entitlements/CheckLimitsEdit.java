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

public class CheckLimitsEdit
  extends BaseTask
  implements Task
{
  private String cH;
  private String cz;
  private String cF;
  private BigDecimal cC = null;
  private BigDecimal cE = null;
  private Date cA;
  private Date cG;
  private String cy = null;
  private static final String cD = "FALSE";
  private static final String cB = "TRUE";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (!jdMethod_null()) {
      return this.taskErrorURL;
    }
    Entitlement localEntitlement = new Entitlement(this.cH, this.cz, this.cF);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Limits localLimits = null;
    try
    {
      if (this.cy == null) {
        this.cy = Util.getLimitBaseCurrency();
      }
      localLimits = Entitlements.checkLimitsEdit(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlement, this.cC, this.cE, this.cy, this.cA, this.cG);
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
  
  private boolean jdMethod_null()
  {
    boolean bool = true;
    if ((this.cH == null) || (this.cH.length() == 0))
    {
      this.error = 35028;
      bool = false;
    }
    if ((this.cz == null) || (this.cz.length() == 0))
    {
      this.error = 35029;
      bool = false;
    }
    if ((this.cF == null) || (this.cF.length() == 0))
    {
      this.error = 35030;
      bool = false;
    }
    if (this.cC == null)
    {
      this.error = 35010;
      bool = false;
    }
    if (this.cE == null)
    {
      this.error = 35011;
      bool = false;
    }
    if (this.cA == null)
    {
      this.error = 35012;
      bool = false;
    }
    if (this.cG == null)
    {
      this.error = 35013;
      bool = false;
    }
    return bool;
  }
  
  public void setOperationName(String paramString)
  {
    this.cH = paramString;
  }
  
  public String getOperationName()
  {
    return this.cH;
  }
  
  public void setObjectType(String paramString)
  {
    this.cz = paramString;
  }
  
  public String getObjectType()
  {
    return this.cz;
  }
  
  public void setObjectId(String paramString)
  {
    this.cF = paramString;
  }
  
  public String getObjectId()
  {
    return this.cF;
  }
  
  public void setOldAmount(String paramString)
  {
    this.cC = new BigDecimal(paramString);
  }
  
  public String getOldAmount()
  {
    return this.cC.toString();
  }
  
  public void setNewAmount(String paramString)
  {
    this.cE = new BigDecimal(paramString);
  }
  
  public String getNewAmount()
  {
    return this.cE.toString();
  }
  
  public void setOldDate(String paramString)
  {
    try
    {
      this.cA = DateFormatUtil.getFormatter("MM/dd/yyyy").parse(paramString);
    }
    catch (Exception localException)
    {
      this.error = 35009;
    }
  }
  
  public Date getOldDate()
  {
    return this.cA;
  }
  
  public void setNewDate(String paramString)
  {
    try
    {
      this.cG = DateFormatUtil.getFormatter("MM/dd/yyyy").parse(paramString);
    }
    catch (Exception localException)
    {
      this.error = 35009;
    }
  }
  
  public Date getNewDate()
  {
    return this.cG;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.cy = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.cy;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.CheckLimitsEdit
 * JD-Core Version:    0.7.0.1
 */