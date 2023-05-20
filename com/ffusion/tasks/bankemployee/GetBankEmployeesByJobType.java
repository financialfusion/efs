package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBankEmployeesByJobType
  extends BaseTask
  implements BankEmployeeTask
{
  private String tC = "BankEmployees";
  private String tA = "BankEmployees";
  private int tz = 0;
  private String tE = null;
  private boolean tB = false;
  private boolean tD = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.tz <= 0)
    {
      this.error = 5526;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    if (this.tD) {
      localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
    }
    Object localObject1 = (BankEmployees)localHttpSession.getAttribute(this.tC);
    if (localObject1 == null) {
      localObject1 = new BankEmployees(localSecureUser.getLocale());
    }
    int i = 0;
    if (this.tE != null) {
      try
      {
        i = Integer.parseInt(this.tE);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        this.error = 5532;
        str = this.taskErrorURL;
        return str;
      }
    }
    try
    {
      BankEmployees localBankEmployees = BankEmployeeAdmin.getBankEmployeesByJobTypeId(localSecureUser, i, this.tz, localHashMap);
      if (this.tB == true) {
        ((BankEmployees)localObject1).addAll(localBankEmployees);
      } else {
        localObject1 = localBankEmployees;
      }
      localHttpSession.setAttribute(this.tA, localObject1);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    finally
    {
      this.tD = false;
    }
    return str;
  }
  
  public void setJobType(String paramString)
  {
    try
    {
      this.tz = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.tE = paramString;
  }
  
  public void setSourceSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.tC = paramString;
    }
  }
  
  public String getSourceSessionKey()
  {
    return this.tC;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.tA = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.tA;
  }
  
  public void setAppendTo(boolean paramBoolean)
  {
    this.tB = paramBoolean;
  }
  
  public void setAppendTo(String paramString)
  {
    this.tB = paramString.equalsIgnoreCase("true");
  }
  
  public boolean getAppendToBoolean()
  {
    return this.tB;
  }
  
  public String getAppendTo()
  {
    return this.tB == true ? "true" : "false";
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this.tD = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this.tD);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetBankEmployeesByJobType
 * JD-Core Version:    0.7.0.1
 */