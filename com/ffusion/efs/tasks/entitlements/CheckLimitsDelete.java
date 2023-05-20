package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
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

public class CheckLimitsDelete
  extends BaseTask
  implements Task
{
  private String KP;
  private String KR;
  private String KO;
  private BigDecimal KS = null;
  private Date KT;
  private String KQ = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (!r()) {
      return this.taskErrorURL;
    }
    Entitlement localEntitlement = new Entitlement(this.KP, this.KR, this.KO);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      if (this.KQ == null) {
        this.KQ = Util.getLimitBaseCurrency();
      }
      Entitlements.checkLimitsDelete(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlement, this.KS, this.KQ, this.KT);
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
  
  private boolean r()
  {
    boolean bool = true;
    if ((this.KP == null) || (this.KP.length() == 0))
    {
      this.error = 35028;
      bool = false;
    }
    if ((this.KR == null) || (this.KR.length() == 0))
    {
      this.error = 35029;
      bool = false;
    }
    if ((this.KO == null) || (this.KO.length() == 0))
    {
      this.error = 35030;
      bool = false;
    }
    if (this.KS == null)
    {
      this.error = 35008;
      bool = false;
    }
    if (this.KT == null)
    {
      this.error = 35009;
      bool = false;
    }
    return bool;
  }
  
  public void setOperationName(String paramString)
  {
    this.KP = paramString;
  }
  
  public String getOperationName()
  {
    return this.KP;
  }
  
  public void setObjectType(String paramString)
  {
    this.KR = paramString;
  }
  
  public String getObjectType()
  {
    return this.KR;
  }
  
  public void setObjectId(String paramString)
  {
    this.KO = paramString;
  }
  
  public String getObjectId()
  {
    return this.KO;
  }
  
  public void setAmount(String paramString)
  {
    this.KS = new BigDecimal(paramString);
  }
  
  public String getAmount()
  {
    return this.KS.toString();
  }
  
  public void setTransactionDate(String paramString)
  {
    try
    {
      this.KT = DateFormatUtil.getFormatter("MM/dd/yyyy").parse(paramString);
    }
    catch (Exception localException)
    {
      this.error = 35009;
    }
  }
  
  public Date getTransactionDate()
  {
    return this.KT;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.KQ = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.KQ;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.CheckLimitsDelete
 * JD-Core Version:    0.7.0.1
 */