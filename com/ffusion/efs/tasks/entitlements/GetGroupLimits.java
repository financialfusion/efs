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
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetGroupLimits
  extends BaseTask
  implements Task
{
  private int U;
  private int N;
  private int aa = -1;
  private String ae;
  private int ab;
  private Date P;
  private boolean V;
  private boolean O;
  private String W;
  private String X;
  private String L;
  private String R;
  private String S;
  private String ac;
  private Boolean M = null;
  private String Q = null;
  private static final String Y = "UNSET";
  private static final String Z = "FALSE";
  private static final String T = "TRUE";
  private static final int ad = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.error != 0) {
      return this.taskErrorURL;
    }
    int i = 0;
    EntitlementGroupMember localEntitlementGroupMember = null;
    if ((this.S != null) && (this.ac != null) && (this.R != null) && (this.U != 0))
    {
      localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setEntitlementGroupId(this.U);
      localEntitlementGroupMember.setMemberType(this.S);
      localEntitlementGroupMember.setMemberSubType(this.ac);
      localEntitlementGroupMember.setId(this.R);
    }
    if ((this.N != 0) || (this.ab != 0) || ((this.ae != null) && (this.ae.length() != 0)) || (this.P != null) || (this.W != null) || (this.X != null) || (this.L != null) || (this.O) || (this.aa != -1) || (this.Q != null) || (this.M != null))
    {
      i = 1;
    }
    else if ((this.U == 0) && (i == 0))
    {
      this.error = 35003;
      return this.taskErrorURL;
    }
    Limits localLimits = null;
    localHttpSession.setAttribute("Entitlement_Limits", new Limits());
    try
    {
      if (i != 0)
      {
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        Entitlement localEntitlement = new Entitlement(this.W, this.X, this.L);
        int j = 0;
        int k = 0;
        int m = 0;
        Limit localLimit = new Limit();
        localLimit.setLimitId(this.N);
        localLimit.setPeriod(this.ab);
        localLimit.setData(this.ae);
        localLimit.setModifiedDate(this.P);
        localLimit.setEntitlement(localEntitlement);
        if (this.aa != -1) {
          localLimit.setParentId(this.aa);
        }
        if (this.O) {
          localLimit.setAllowApproval(this.V);
        }
        if (this.M != null) {
          localLimit.setCrossCurrency(this.M.booleanValue());
        }
        if (this.Q != null) {
          localLimit.setCurrencyCode(this.Q);
        }
        HashMap localHashMap = new HashMap();
        if (localEntitlementGroupMember == null) {
          localLimits = Entitlements.getGroupLimits(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), this.U, localLimit, localHashMap);
        } else {
          localLimits = Entitlements.getGroupLimits(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroupMember, localLimit, localHashMap);
        }
      }
      else if (localEntitlementGroupMember == null)
      {
        localLimits = Entitlements.getGroupLimits(this.U);
      }
      else
      {
        localLimits = Entitlements.getGroupLimits(localEntitlementGroupMember);
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
    return str;
  }
  
  public void setGroupId(String paramString)
  {
    this.U = Integer.parseInt(paramString);
  }
  
  public int getGroupId()
  {
    return this.U;
  }
  
  public void setLimitId(String paramString)
  {
    this.N = Integer.parseInt(paramString);
  }
  
  public String getLimitId()
  {
    return Integer.toString(this.N);
  }
  
  public void setParentId(String paramString)
  {
    this.aa = Integer.parseInt(paramString);
  }
  
  public String getParentId()
  {
    return Integer.toString(this.aa);
  }
  
  public void setData(String paramString)
  {
    this.ae = paramString;
  }
  
  public String getData()
  {
    return this.ae;
  }
  
  public void setPeriod(String paramString)
  {
    this.ab = Integer.parseInt(paramString);
  }
  
  public String getPeriod()
  {
    return Integer.toString(this.ab);
  }
  
  public void setModifiedDate(String paramString)
  {
    try
    {
      this.P = DateFormatUtil.getFormatter("MM/dd/yyyy").parse(paramString);
    }
    catch (Exception localException)
    {
      this.error = 35009;
    }
  }
  
  public Date getModifiedDate()
  {
    return this.P;
  }
  
  public void setAllowApproval(String paramString)
  {
    if (paramString.equalsIgnoreCase("TRUE"))
    {
      this.V = true;
      this.O = true;
    }
    else if (paramString.equalsIgnoreCase("FALSE"))
    {
      this.V = false;
      this.O = true;
    }
    else
    {
      this.error = 35031;
    }
  }
  
  public String getAllowApproval()
  {
    if (this.V) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setOperationName(String paramString)
  {
    this.W = paramString;
  }
  
  public String getOperationName()
  {
    return this.W;
  }
  
  public void setObjectType(String paramString)
  {
    this.X = paramString;
  }
  
  public String getObjectType()
  {
    return this.X;
  }
  
  public void setObjectId(String paramString)
  {
    this.L = paramString;
  }
  
  public String getObjectId()
  {
    return this.L;
  }
  
  public void setMemberId(String paramString)
  {
    this.R = paramString;
  }
  
  public String getMemberId()
  {
    return this.R;
  }
  
  public void setMemberType(String paramString)
  {
    this.S = paramString;
  }
  
  public String getMemberType()
  {
    return this.S;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.ac = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.ac;
  }
  
  public void setCrossCurrency(String paramString)
  {
    if (paramString.equalsIgnoreCase("TRUE")) {
      this.M = Boolean.TRUE;
    } else if (paramString.equalsIgnoreCase("FALSE")) {
      this.M = Boolean.FALSE;
    } else {
      this.error = 35046;
    }
  }
  
  public String getCrossCurrency()
  {
    if (this.M == null) {
      return "UNSET";
    }
    if (this.M.booleanValue()) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.Q = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.Q;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetGroupLimits
 * JD-Core Version:    0.7.0.1
 */