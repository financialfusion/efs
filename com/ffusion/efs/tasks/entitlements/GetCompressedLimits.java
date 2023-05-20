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

public class GetCompressedLimits
  extends BaseTask
  implements Task
{
  private int MA;
  private int Mt;
  private int MG = -1;
  private String MK;
  private int MH;
  private Date Mv;
  private boolean MB;
  private boolean Mu;
  private String MC;
  private String MD;
  private String Mr;
  private String Mx;
  private String My;
  private String MI;
  private Boolean Ms = null;
  private String Mw = null;
  private static final String ME = "UNSET";
  private static final String MF = "FALSE";
  private static final String Mz = "TRUE";
  private static final int MJ = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.error != 0) {
      return this.taskErrorURL;
    }
    int i = 0;
    if ((this.Mt != 0) || (this.MH != 0) || ((this.MK != null) && (this.MK.length() != 0)) || (this.Mv != null) || ((this.MC != null) && (this.MC.length() != 0)) || ((this.MD != null) && (this.MD.length() != 0)) || ((this.Mr != null) && (this.Mr.length() != 0)) || (this.Mu) || (this.MG != -1) || (this.Mw != null) || (this.Ms != null)) {
      i = 1;
    }
    if (this.MA == 0)
    {
      this.error = 35003;
      return this.taskErrorURL;
    }
    EntitlementGroupMember localEntitlementGroupMember = null;
    if ((this.My != null) && (this.MI != null) && (this.Mx != null))
    {
      localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setEntitlementGroupId(this.MA);
      localEntitlementGroupMember.setMemberType(this.My);
      localEntitlementGroupMember.setMemberSubType(this.MI);
      localEntitlementGroupMember.setId(this.Mx);
    }
    Limits localLimits = null;
    try
    {
      if (i != 0)
      {
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        Entitlement localEntitlement = new Entitlement(this.MC, this.MD, this.Mr);
        Limit localLimit = new Limit();
        localLimit.setLimitId(this.Mt);
        localLimit.setPeriod(this.MH);
        localLimit.setData(this.MK);
        localLimit.setModifiedDate(this.Mv);
        localLimit.setEntitlement(localEntitlement);
        if (this.MG != -1) {
          localLimit.setParentId(this.MG);
        }
        if (this.Mu) {
          localLimit.setAllowApproval(this.MB);
        }
        if (this.Ms != null) {
          localLimit.setCrossCurrency(this.Ms.booleanValue());
        }
        if (this.Mw != null) {
          localLimit.setCurrencyCode(this.Mw);
        }
        if (localEntitlementGroupMember == null) {
          localLimits = Entitlements.getCompressedLimits(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), this.MA, localLimit);
        } else {
          localLimits = Entitlements.getCompressedLimits(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroupMember, localLimit);
        }
      }
      else if (localEntitlementGroupMember == null)
      {
        localLimits = Entitlements.getCompressedLimits(this.MA);
      }
      else
      {
        localLimits = Entitlements.getCompressedLimits(localEntitlementGroupMember);
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
    this.Mt = 0;
    this.MH = 0;
    this.MK = null;
    this.Mv = null;
    this.MC = null;
    this.MD = null;
    this.Mr = null;
    this.Mu = false;
    this.MG = -1;
    this.Ms = null;
    this.Mw = null;
    return str;
  }
  
  public void setGroupId(String paramString)
  {
    this.MA = Integer.parseInt(paramString);
  }
  
  public int getGroupId()
  {
    return this.MA;
  }
  
  public void setLimitId(String paramString)
  {
    this.Mt = Integer.parseInt(paramString);
  }
  
  public String getLimitId()
  {
    return Integer.toString(this.Mt);
  }
  
  public void setParentId(String paramString)
  {
    this.MG = Integer.parseInt(paramString);
  }
  
  public String getParentId()
  {
    return Integer.toString(this.MG);
  }
  
  public void setData(String paramString)
  {
    this.MK = paramString;
  }
  
  public String getData()
  {
    return this.MK;
  }
  
  public void setPeriod(String paramString)
  {
    this.MH = Integer.parseInt(paramString);
  }
  
  public String getPeriod()
  {
    return Integer.toString(this.MH);
  }
  
  public void setModifiedDate(String paramString)
  {
    try
    {
      this.Mv = DateFormatUtil.getFormatter("MM/dd/yyyy").parse(paramString);
    }
    catch (Exception localException)
    {
      this.error = 35009;
    }
  }
  
  public Date getModifiedDate()
  {
    return this.Mv;
  }
  
  public void setAllowApproval(String paramString)
  {
    if (paramString.equalsIgnoreCase("TRUE"))
    {
      this.MB = true;
      this.Mu = true;
    }
    else if (paramString.equalsIgnoreCase("FALSE"))
    {
      this.MB = false;
      this.Mu = true;
    }
    else
    {
      this.error = 35031;
    }
  }
  
  public String getAllowApproval()
  {
    if (this.MB) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setOperationName(String paramString)
  {
    this.MC = paramString;
  }
  
  public String getOperationName()
  {
    return this.MC;
  }
  
  public void setObjectType(String paramString)
  {
    this.MD = paramString;
  }
  
  public String getObjectType()
  {
    return this.MD;
  }
  
  public void setObjectId(String paramString)
  {
    this.Mr = paramString;
  }
  
  public String getObjectId()
  {
    return this.Mr;
  }
  
  public void setMemberId(String paramString)
  {
    this.Mx = paramString;
  }
  
  public String getMemberId()
  {
    return this.Mx;
  }
  
  public void setMemberType(String paramString)
  {
    this.My = paramString;
  }
  
  public String getMemberType()
  {
    return this.My;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.MI = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.MI;
  }
  
  public void setCrossCurrency(String paramString)
  {
    if (paramString.equalsIgnoreCase("TRUE")) {
      this.Ms = Boolean.TRUE;
    } else if (paramString.equalsIgnoreCase("FALSE")) {
      this.Ms = Boolean.FALSE;
    } else {
      this.error = 35046;
    }
  }
  
  public String getCrossCurrency()
  {
    if (this.Ms == null) {
      return "UNSET";
    }
    if (this.Ms.booleanValue()) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.Mw = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.Mw;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetCompressedLimits
 * JD-Core Version:    0.7.0.1
 */