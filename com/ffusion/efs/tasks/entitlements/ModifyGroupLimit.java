package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyGroupLimit
  extends BaseTask
  implements Task
{
  private static final int cR = -1;
  private String cP;
  private int cM = -1;
  private int cO = -1;
  private int cT = -1;
  private String cN;
  private String cK;
  private String cU;
  private Boolean cJ = null;
  private String cI = null;
  private static final String cS = "UNSET";
  private static final String cQ = "FALSE";
  private static final String cL = "TRUE";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    Limit localLimit = (Limit)localHttpSession.getAttribute("Entitlement_Limit");
    if (localLimit == null)
    {
      this.error = 35002;
      return this.taskErrorURL;
    }
    if ((this.cP != null) && (this.cP.length() != 0))
    {
      localObject = new Entitlement(this.cP, this.cK, this.cU);
      localLimit.setEntitlement((Entitlement)localObject);
    }
    if (this.cM != -1) {
      localLimit.setGroupId(this.cM);
    }
    if (this.cO != -1) {
      localLimit.setParentId(this.cO);
    }
    if (this.cT != -1) {
      localLimit.setPeriod(this.cT);
    }
    if ((this.cN != null) && (this.cN.length() != 0)) {
      localLimit.setData(this.cN);
    }
    if (this.cJ != null) {
      localLimit.setCrossCurrency(this.cJ.booleanValue());
    }
    if (this.cI != null) {
      localLimit.setCurrencyCode(this.cI);
    }
    Object localObject = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      Entitlements.modifyGroupLimit(EntitlementsUtil.getEntitlementGroupMember((SecureUser)localObject), localLimit);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("Entitlement_Limit", localLimit);
      str = this.successURL;
    }
    else
    {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setLimitName(String paramString)
  {
    this.cP = paramString;
  }
  
  public String getLimitName()
  {
    return this.cP;
  }
  
  public void setGroupId(String paramString)
  {
    this.cM = Integer.parseInt(paramString);
  }
  
  public String getGroupId()
  {
    return Integer.toString(this.cM);
  }
  
  public void setParentId(String paramString)
  {
    this.cO = Integer.parseInt(paramString);
  }
  
  public String getParentId()
  {
    return Integer.toString(this.cO);
  }
  
  public void setPeriod(String paramString)
  {
    this.cT = Integer.parseInt(paramString);
  }
  
  public int getPeriod()
  {
    return this.cT;
  }
  
  public void setData(String paramString)
  {
    this.cN = paramString;
  }
  
  public String getData()
  {
    return this.cN;
  }
  
  public void setObjectType(String paramString)
  {
    this.cK = paramString;
  }
  
  public String getObjectType()
  {
    return this.cK;
  }
  
  public void setObjectId(String paramString)
  {
    this.cU = paramString;
  }
  
  public String getObjectId()
  {
    return this.cU;
  }
  
  public void setCrossCurrency(String paramString)
  {
    if (paramString.equalsIgnoreCase("TRUE")) {
      this.cJ = Boolean.TRUE;
    } else if (paramString.equalsIgnoreCase("FALSE")) {
      this.cJ = Boolean.FALSE;
    }
  }
  
  public String getCrossCurrency()
  {
    if (this.cJ == null) {
      return "UNSET";
    }
    if (this.cJ.booleanValue()) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.cI = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.cI;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.ModifyGroupLimit
 * JD-Core Version:    0.7.0.1
 */