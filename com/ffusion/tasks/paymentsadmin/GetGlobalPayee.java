package com.ffusion.tasks.paymentsadmin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetGlobalPayee
  extends BaseTask
  implements GlobalPayeeTask
{
  private int GP = -1;
  private String GO = null;
  protected ArrayList retrievedLanguageList = new ArrayList();
  protected boolean _entBypass = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    if (this._entBypass) {
      localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Payee localPayee = new Payee();
    try
    {
      localPayee = PaymentsAdmin.getGlobalPayee(localSecureUser, this.GP, localHashMap);
      localPayee.setSearchLanguage(this.GO);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    finally
    {
      this._entBypass = false;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("Payee", localPayee);
      str = this.successURL;
    }
    return str;
  }
  
  public void setID(String paramString)
  {
    try
    {
      this.GP = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.GP = -1;
    }
  }
  
  public void setCurrentLanguage(String paramString)
  {
    this.GO = paramString;
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
 * Qualified Name:     com.ffusion.tasks.paymentsadmin.GetGlobalPayee
 * JD-Core Version:    0.7.0.1
 */