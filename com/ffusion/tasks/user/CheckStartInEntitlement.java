package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckStartInEntitlement
  extends BaseTask
  implements UserTask
{
  private final String vB = String.valueOf(Boolean.TRUE);
  private final String vE = String.valueOf(Boolean.FALSE);
  private String vH = null;
  private String vF = null;
  private String vD = null;
  private String vz = null;
  private ArrayList vy = new ArrayList();
  private EntitlementGroupMember vG = null;
  private boolean vA = false;
  private String vC = this.vE;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    if ((this.vH == null) || (this.vH.length() == 0))
    {
      this.error = 3535;
      return this.taskErrorURL;
    }
    if ((this.vF == null) || (this.vF.length() == 0))
    {
      this.error = 1;
      return this.taskErrorURL;
    }
    if ((this.vD == null) || (this.vD.length() == 0))
    {
      this.error = 3536;
      return this.taskErrorURL;
    }
    if (this.vy.size() == 0)
    {
      this.error = 3537;
      return this.taskErrorURL;
    }
    int i;
    try
    {
      i = Integer.parseInt(this.vD);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 3536;
      return this.taskErrorURL;
    }
    Object localObject1;
    Object localObject2;
    if (!this.vA)
    {
      localObject1 = new SecureUser();
      ((SecureUser)localObject1).setBankID(this.vH);
      ((SecureUser)localObject1).setUserName(this.vF);
      ((SecureUser)localObject1).setUserType(i);
      ((SecureUser)localObject1).setBusinessCustId(this.vz);
      boolean bool = false;
      try
      {
        bool = UserAdmin.getInfoForAuditing((SecureUser)localObject1, new HashMap());
      }
      catch (CSILException localCSILException1)
      {
        this.error = localCSILException1.getCode();
        return this.serviceErrorURL;
      }
      if (!bool)
      {
        this.vG = null;
      }
      else
      {
        localObject2 = new EntitlementGroupMember();
        ((EntitlementGroupMember)localObject2).setMemberType("USER");
        ((EntitlementGroupMember)localObject2).setMemberSubType(this.vD);
        ((EntitlementGroupMember)localObject2).setId(String.valueOf(((SecureUser)localObject1).getProfileID()));
        try
        {
          this.vG = Entitlements.getMember((EntitlementGroupMember)localObject2);
        }
        catch (CSILException localCSILException2)
        {
          this.error = localCSILException2.getCode();
          return this.serviceErrorURL;
        }
        this.vA = true;
      }
    }
    this.vC = this.vE;
    if (this.vG == null)
    {
      this.vC = this.vB;
    }
    else
    {
      localObject1 = this.vy.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        String str = (String)((Iterator)localObject1).next();
        localObject2 = new Entitlement(str, null, null);
        try
        {
          if (Entitlements.checkEntitlement(this.vG, (Entitlement)localObject2))
          {
            this.vC = this.vB;
            break;
          }
        }
        catch (CSILException localCSILException3)
        {
          this.error = localCSILException3.getCode();
          return this.serviceErrorURL;
        }
      }
    }
    this.vy.clear();
    return this.successURL;
  }
  
  public void setBankId(String paramString)
  {
    this.vH = paramString;
    this.vA = false;
  }
  
  public void setUserName(String paramString)
  {
    this.vF = paramString;
    this.vA = false;
  }
  
  public void setUserType(String paramString)
  {
    this.vD = paramString;
    this.vA = false;
  }
  
  public void setBusinessCustomerId(String paramString)
  {
    this.vz = paramString;
    this.vA = false;
  }
  
  public void setOperation(String paramString)
  {
    this.vy.add(paramString);
  }
  
  public String getResult()
  {
    return this.vC;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.CheckStartInEntitlement
 * JD-Core Version:    0.7.0.1
 */