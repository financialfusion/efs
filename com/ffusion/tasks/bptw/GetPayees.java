package com.ffusion.tasks.bptw;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.PayeeI18N;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BPTW;
import com.ffusion.services.bptw.BillPay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPayees
  extends BaseTask
  implements Task
{
  private String kr = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BillPay localBillPay = (BillPay)localHttpSession.getAttribute("BptwBillPay");
    String str = this.kr;
    User localUser = (User)localHttpSession.getAttribute("User");
    if ((this.kr == null) || (this.kr.trim().equals("")))
    {
      if (localUser == null)
      {
        this.error = 17006;
        return this.taskErrorURL;
      }
      str = localUser.getId();
    }
    HashMap localHashMap = new HashMap();
    if (localBillPay != null) {
      localHashMap.put("SERVICE", localBillPay);
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      BPTW.signOnBillPay(localSecureUser, str, "0", localHashMap);
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
    }
    Payees localPayees = new Payees();
    localHashMap = new HashMap();
    if (localBillPay != null) {
      localHashMap.put("SERVICE", localBillPay);
    }
    if (localUser != null) {
      localHashMap.put("User", localUser);
    } else {
      localHashMap.put("User", this.kr);
    }
    localHashMap.put("PAYEES", localPayees);
    try
    {
      localPayees = BPTW.getPayees(localSecureUser, localHashMap);
      jdMethod_for(localPayees, localSecureUser);
    }
    catch (CSILException localCSILException2)
    {
      this.error = MapError.mapError(localCSILException2);
    }
    localHttpSession.setAttribute("Payees", localPayees);
    if (this.error != 0) {
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  private void jdMethod_for(Payees paramPayees, SecureUser paramSecureUser)
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
  
  public void setUserId(String paramString)
  {
    this.kr = paramString;
  }
  
  public String getUserId()
  {
    return this.kr;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bptw.GetPayees
 * JD-Core Version:    0.7.0.1
 */