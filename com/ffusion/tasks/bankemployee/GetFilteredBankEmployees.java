package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFilteredBankEmployees
  extends BaseTask
  implements BankEmployeeTask
{
  private String tT = "BankEmployees";
  private String tW;
  private String tY;
  private String t0;
  private String t1;
  private String tZ;
  private String tS;
  private boolean tV = false;
  private HashMap tX = null;
  private String tU = null;
  
  public GetFilteredBankEmployees()
  {
    this.tX.put("Personal Banker", new Entitlement("Personal Banker", null, null));
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BankEmployees localBankEmployees1 = null;
    BankEmployees localBankEmployees2 = null;
    BankEmployee localBankEmployee1 = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (this.tW == null) {
      this.tW = localSecureUser.getBankID();
    }
    int i = 0;
    if ((this.tY != null) && (this.tY.length() > 0)) {
      try
      {
        i = Integer.parseInt(this.tY);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        this.error = 5532;
        str = this.taskErrorURL;
        return str;
      }
    }
    if ((this.t0 != null) || (this.t1 != null) || (this.tZ != null) || (this.tS != null) || (this.tY != null))
    {
      localBankEmployee1 = new BankEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localBankEmployee1.setFirstName(this.t0);
      localBankEmployee1.setLastName(this.t1);
      localBankEmployee1.setUserName(this.tZ);
      localBankEmployee1.setJobTypeId(this.tS);
      if (this.tY != null) {
        localBankEmployee1.addAffiliateBankId(i);
      }
    }
    try
    {
      HashMap localHashMap = new HashMap();
      if (this.tV) {
        localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
      }
      localBankEmployees1 = BankEmployeeAdmin.getBankEmployeeList(localSecureUser, localBankEmployee1, localHashMap);
      if ((this.tU != null) && (!this.tU.equals("")))
      {
        localBankEmployees2 = new BankEmployees((Locale)localHttpSession.getAttribute("java.util.Locale"));
        Entitlement localEntitlement = (Entitlement)this.tX.get(this.tU);
        if (localEntitlement != null)
        {
          Iterator localIterator = localBankEmployees1.iterator();
          while (localIterator.hasNext())
          {
            BankEmployee localBankEmployee2 = (BankEmployee)localIterator.next();
            int j = localBankEmployee2.getJobTypeId();
            if (Entitlements.checkEntitlement(j, localEntitlement)) {
              localBankEmployees2.add(localBankEmployee2);
            }
          }
        }
        else
        {
          this.error = 5534;
          str = this.taskErrorURL;
          return str;
        }
      }
      else
      {
        localBankEmployees2 = localBankEmployees1;
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if ((this.error == 0) || (this.error == 12))
    {
      localHttpSession.setAttribute(this.tT, localBankEmployees2);
      str = this.successURL;
    }
    return str;
  }
  
  public void setBankEmployeesSessionName(String paramString)
  {
    this.tT = paramString;
  }
  
  public void setBankId(String paramString)
  {
    this.tW = paramString;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.tY = paramString;
  }
  
  public void setFirstName(String paramString)
  {
    this.t0 = paramString;
  }
  
  public void setLastName(String paramString)
  {
    this.t1 = paramString;
  }
  
  public void setUserName(String paramString)
  {
    this.tZ = paramString;
  }
  
  public void setJobType(String paramString)
  {
    this.tS = paramString;
  }
  
  public String getEntitlementOperationName()
  {
    String str = "";
    if (this.tU != null) {
      str = this.tU;
    }
    return str;
  }
  
  public void setEntitlementOperationName(String paramString)
  {
    if (paramString.length() == 0) {
      this.tU = null;
    } else {
      this.tU = paramString;
    }
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this.tV = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this.tV);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetFilteredBankEmployees
 * JD-Core Version:    0.7.0.1
 */