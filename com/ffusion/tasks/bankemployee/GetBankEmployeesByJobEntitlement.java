package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBankEmployeesByJobEntitlement
  extends BaseTask
  implements BankEmployeeTask
{
  private String ub = "BankEmployees";
  private String t9 = "BankEmployees";
  private String uc;
  private int ud = -1;
  private int ue = -1;
  private boolean ua = false;
  protected boolean _entBypass = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.uc == null) || (this.uc.length() == 0))
    {
      this.error = 5526;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap(1);
    if (this._entBypass) {
      localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
    }
    BankEmployees localBankEmployees1 = (BankEmployees)localHttpSession.getAttribute(this.ub);
    if ((localBankEmployees1 == null) || (!this.ua)) {
      localBankEmployees1 = new BankEmployees(localSecureUser.getLocale());
    }
    try
    {
      Entitlement localEntitlement1 = new Entitlement(this.uc, null, null);
      Entitlement localEntitlement2 = new Entitlement("BC Manage Multiple Banks Simultaneously", null, null);
      EntitlementGroups localEntitlementGroups = Entitlements.getEntitlementGroupsByType("EmployeeType");
      Iterator localIterator1 = localEntitlementGroups.iterator();
      while (localIterator1.hasNext())
      {
        EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator1.next();
        if (Entitlements.checkEntitlement(localEntitlementGroup.getGroupId(), localEntitlement1))
        {
          BankEmployees localBankEmployees2 = BankEmployeeAdmin.getBankEmployeesByJobTypeId(localSecureUser, localEntitlementGroup.getGroupId(), localHashMap);
          if ((this.ud < 0) && (this.ue < 0))
          {
            localBankEmployees1.addAll(localBankEmployees2);
          }
          else
          {
            Iterator localIterator2 = localBankEmployees2.iterator();
            while (localIterator2.hasNext())
            {
              BankEmployee localBankEmployee = (BankEmployee)localIterator2.next();
              if (Entitlements.checkEntitlement(localBankEmployee.getEntitlementGroupMember(), localEntitlement2))
              {
                localBankEmployees1.add(localBankEmployee);
              }
              else
              {
                ArrayList localArrayList = localBankEmployee.getAffiliateBankIds();
                if ((localArrayList != null) && (localArrayList.size() > 0)) {
                  for (int i = 0; i < localArrayList.size(); i++)
                  {
                    String str2 = (String)localArrayList.get(i);
                    if ((this.ud > 0) && (String.valueOf(this.ud).equalsIgnoreCase(str2))) {
                      localBankEmployees1.add(localBankEmployee);
                    } else if ((this.ue > 0) && (String.valueOf(this.ue).equalsIgnoreCase(str2))) {
                      localBankEmployees1.add(localBankEmployee);
                    }
                  }
                }
              }
            }
          }
        }
      }
      localHttpSession.setAttribute(this.t9, localBankEmployees1);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    finally
    {
      this._entBypass = false;
    }
    return str1;
  }
  
  public void setJobEntitlement(String paramString)
  {
    this.uc = paramString;
  }
  
  public void setSourceSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.ub = paramString;
    }
  }
  
  public String getSourceSessionKey()
  {
    return this.ub;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.t9 = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.t9;
  }
  
  public void setAppendTo(boolean paramBoolean)
  {
    this.ua = paramBoolean;
  }
  
  public void setAppendTo(String paramString)
  {
    this.ua = paramString.equalsIgnoreCase("true");
  }
  
  public boolean getAppendToBoolean()
  {
    return this.ua;
  }
  
  public String getAppendTo()
  {
    return this.ua == true ? "true" : "false";
  }
  
  public void setAffiliateBankID(String paramString)
  {
    this.ud = Integer.parseInt(paramString);
  }
  
  public void setMsgCreatorAffiliateBankID(String paramString)
  {
    try
    {
      this.ue = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.ue = -1;
    }
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this._entBypass = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this._entBypass);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetBankEmployeesByJobEntitlement
 * JD-Core Version:    0.7.0.1
 */