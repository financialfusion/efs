package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.PayeeI18N;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Billpay;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPayees
  extends BaseTask
  implements Task
{
  protected boolean reload = false;
  protected String payeeName = "Payees";
  protected String serviceName = "com.ffusion.services.BillPay";
  protected String userSessionName;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Payees localPayees = (Payees)localHttpSession.getAttribute(this.payeeName);
    String str = this.successURL;
    if (this.reload)
    {
      localPayees = null;
      localHttpSession.removeAttribute(this.payeeName);
      this.reload = false;
    }
    if (localPayees == null)
    {
      this.error = getPayeesFromBillPay(localHttpSession);
      if (this.error != 0) {
        str = this.serviceErrorURL;
      }
    }
    localPayees = (Payees)localHttpSession.getAttribute(this.payeeName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    jdMethod_int(localPayees, localSecureUser);
    return str;
  }
  
  protected int getPayeesFromBillPay(HttpSession paramHttpSession)
  {
    Payees localPayees = new Payees();
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute(this.serviceName);
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = null;
    if (localBillPay != null) {
      localHashMap.put("SERVICE", localBillPay);
    }
    localHashMap.put("PAYEES", localPayees);
    if ((this.userSessionName == null) || (this.userSessionName.trim().equals("")))
    {
      localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    }
    else
    {
      User localUser = (User)paramHttpSession.getAttribute(this.userSessionName);
      if (localUser == null)
      {
        this.error = 2033;
        return this.error;
      }
      localSecureUser = localUser.getSecureUser();
      try
      {
        EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
        localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
        localSecureUser.setEntitlementID(localEntitlementGroupMember.getEntitlementGroupId());
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        return this.error;
      }
    }
    this.error = 0;
    try
    {
      localPayees = Billpay.getPayees(localSecureUser, localHashMap);
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
    }
    if (this.error == 0)
    {
      jdMethod_int(localPayees, localSecureUser);
      Collections.sort(localPayees);
      paramHttpSession.setAttribute(this.payeeName, localPayees);
    }
    return this.error;
  }
  
  private void jdMethod_int(Payees paramPayees, SecureUser paramSecureUser)
  {
    for (int i = 0; i < paramPayees.size(); i++)
    {
      Payee localPayee = (Payee)paramPayees.get(i);
      if ((localPayee != null) && (localPayee.getPayeeI18NInfo() != null))
      {
        PayeeI18N localPayeeI18N = (PayeeI18N)localPayee.getPayeeI18NInfo().get(paramSecureUser.getLocaleLanguage());
        if (localPayeeI18N != null) {
          localPayee.setCurrentLanguage(paramSecureUser.getLocaleLanguage());
        } else {
          localPayee.setCurrentLanguage("en_US");
        }
      }
    }
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setPayeeName(String paramString)
  {
    this.payeeName = paramString;
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
  
  public void setUserSessionName(String paramString)
  {
    this.userSessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetPayees
 * JD-Core Version:    0.7.0.1
 */