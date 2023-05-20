package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCumulativeLimits
  extends BaseTask
  implements Task
{
  private int Ln;
  private int Lg;
  private int Lt = -1;
  private String Lx;
  private int Lu;
  private Date Li;
  private boolean Lo;
  private boolean Lh;
  private String Lp;
  private String Lq;
  private String Le;
  private String Lk;
  private String Ll;
  private String Lv;
  private Boolean Lf = null;
  private String Lj = null;
  private static final String Lr = "UNSET";
  private static final String Ls = "FALSE";
  private static final String Lm = "TRUE";
  private static final int Lw = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.error != 0) {
      return this.taskErrorURL;
    }
    int i = 0;
    if ((this.Lg != 0) || (this.Lu != 0) || ((this.Lx != null) && (this.Lx.length() != 0)) || (this.Li != null) || ((this.Lp != null) && (this.Lp.length() != 0)) || ((this.Lq != null) && (this.Lq.length() != 0)) || ((this.Le != null) && (this.Le.length() != 0)) || (this.Lh) || (this.Lt != -1) || (this.Lj != null) || (this.Lf != null)) {
      i = 1;
    }
    if (this.Ln == 0)
    {
      this.error = 35003;
      return this.taskErrorURL;
    }
    EntitlementGroupMember localEntitlementGroupMember = null;
    if ((this.Ll != null) && (this.Lv != null) && (this.Lk != null))
    {
      localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setEntitlementGroupId(this.Ln);
      localEntitlementGroupMember.setMemberType(this.Ll);
      localEntitlementGroupMember.setMemberSubType(this.Lv);
      localEntitlementGroupMember.setId(this.Lk);
    }
    Limits localLimits = null;
    try
    {
      if (i != 0)
      {
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        Entitlement localEntitlement = new Entitlement(this.Lp, this.Lq, this.Le);
        Limit localLimit = new Limit();
        localLimit.setLimitId(this.Lg);
        localLimit.setPeriod(this.Lu);
        localLimit.setData(this.Lx);
        localLimit.setModifiedDate(this.Li);
        localLimit.setEntitlement(localEntitlement);
        if (this.Lt != -1) {
          localLimit.setParentId(this.Lt);
        }
        if (this.Lh) {
          localLimit.setAllowApproval(this.Lo);
        }
        if (this.Lf != null) {
          localLimit.setCrossCurrency(this.Lf.booleanValue());
        }
        if (this.Lj != null) {
          localLimit.setCurrencyCode(this.Lj);
        }
        if (localEntitlementGroupMember == null) {
          localLimits = Entitlements.getCumulativeLimits(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), this.Ln, localLimit);
        } else {
          localLimits = Entitlements.getCumulativeLimits(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroupMember, localLimit);
        }
      }
      else if (localEntitlementGroupMember == null)
      {
        localLimits = Entitlements.getCumulativeLimits(this.Ln);
      }
      else
      {
        localLimits = Entitlements.getCumulativeLimits(localEntitlementGroupMember);
      }
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
    this.Lg = 0;
    this.Lu = 0;
    this.Lx = null;
    this.Li = null;
    this.Lp = null;
    this.Lq = null;
    this.Le = null;
    this.Lh = false;
    this.Lt = -1;
    this.Lf = null;
    this.Lj = null;
    return str;
  }
  
  public void setGroupId(String paramString)
  {
    this.Ln = Integer.parseInt(paramString);
  }
  
  public int getGroupId()
  {
    return this.Ln;
  }
  
  public void setLimitId(String paramString)
  {
    this.Lg = Integer.parseInt(paramString);
  }
  
  public String getLimitId()
  {
    return Integer.toString(this.Lg);
  }
  
  public void setParentId(String paramString)
  {
    this.Lt = Integer.parseInt(paramString);
  }
  
  public String getParentId()
  {
    return Integer.toString(this.Lt);
  }
  
  public void setData(String paramString)
  {
    this.Lx = paramString;
  }
  
  public String getData()
  {
    return this.Lx;
  }
  
  public void setPeriod(String paramString)
  {
    this.Lu = Integer.parseInt(paramString);
  }
  
  public String getPeriod()
  {
    return Integer.toString(this.Lu);
  }
  
  public void setModifiedDate(String paramString)
  {
    try
    {
      this.Li = DateFormatUtil.getFormatter("MM/dd/yyyy").parse(paramString);
    }
    catch (Exception localException)
    {
      this.error = 35009;
    }
  }
  
  public Date getModifiedDate()
  {
    return this.Li;
  }
  
  public void setAllowApproval(String paramString)
  {
    if (paramString.equalsIgnoreCase("TRUE"))
    {
      this.Lo = true;
      this.Lh = true;
    }
    else if (paramString.equalsIgnoreCase("FALSE"))
    {
      this.Lo = false;
      this.Lh = true;
    }
    else
    {
      this.error = 35031;
    }
  }
  
  public String getAllowApproval()
  {
    if (this.Lo) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setOperationName(String paramString)
  {
    this.Lp = paramString;
  }
  
  public String getOperationName()
  {
    return this.Lp;
  }
  
  public void setObjectType(String paramString)
  {
    this.Lq = paramString;
  }
  
  public String getObjectType()
  {
    return this.Lq;
  }
  
  public void setObjectId(String paramString)
  {
    this.Le = paramString;
  }
  
  public String getObjectId()
  {
    return this.Le;
  }
  
  public void setMemberId(String paramString)
  {
    this.Lk = paramString;
  }
  
  public String getMemberId()
  {
    return this.Lk;
  }
  
  public void setMemberType(String paramString)
  {
    this.Ll = paramString;
  }
  
  public String getMemberType()
  {
    return this.Ll;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.Lv = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.Lv;
  }
  
  public void setCrossCurrency(String paramString)
  {
    if (paramString.equalsIgnoreCase("TRUE")) {
      this.Lf = Boolean.TRUE;
    } else if (paramString.equalsIgnoreCase("FALSE")) {
      this.Lf = Boolean.FALSE;
    } else {
      this.error = 35046;
    }
  }
  
  public String getCrossCurrency()
  {
    if (this.Lf == null) {
      return "UNSET";
    }
    if (this.Lf.booleanValue()) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.Lj = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.Lj;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetCumulativeLimits
 * JD-Core Version:    0.7.0.1
 */