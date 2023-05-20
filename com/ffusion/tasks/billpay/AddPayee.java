package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.PayeeRoute;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import javax.servlet.http.HttpSession;

public class AddPayee
  extends EditPayee
{
  int HH;
  Integer HF = null;
  String HG = null;
  
  protected int validateInput(HttpSession paramHttpSession, Payees paramPayees)
    throws CSILException
  {
    int i = 0;
    if (this.validate != null)
    {
      if ((this.validate.indexOf("NAME") != -1) && ((this.name == null) || (this.name.length() == 0) || (this.name.length() > this.HH))) {
        i = 2026;
      } else if ((this.validate.indexOf("NAME") != -1) && (paramPayees.getByName(this.name) != null)) {
        i = 2012;
      } else {
        i = super.validateInput(paramHttpSession, paramPayees);
      }
      this.validate = null;
    }
    return i;
  }
  
  protected void initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    if (this.payeeRoute == null) {
      this.payeeRoute = new PayeeRoute();
    }
  }
  
  protected int doProcess(HttpSession paramHttpSession, Payees paramPayees)
  {
    Payees localPayees = new Payees();
    localPayees.add(this);
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute("com.ffusion.services.BillPay");
    HashMap localHashMap = null;
    if (localBillPay != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localBillPay);
    }
    if (this.HF != null)
    {
      if (localHashMap == null) {
        localHashMap = new HashMap();
      }
      localHashMap.put("PROFILEID_KEY", this.HF);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      localPayees = Billpay.addPayees(localSecureUser, localPayees, localHashMap);
      set((Payee)localPayees.get(0));
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      Payee localPayee = paramPayees.create();
      localPayee.set(this);
      localPayee.setCurrentLanguage(localSecureUser.getLocaleLanguage());
      paramHttpSession.setAttribute("Payee", localPayee);
      paramPayees.setSortedBy("NAME");
    }
    return this.error;
  }
  
  public void setMaxNameLength(String paramString)
  {
    this.HH = Integer.valueOf(paramString).intValue();
  }
  
  public void setCustomerProfileID(String paramString)
  {
    this.HF = new Integer(paramString);
  }
  
  public void setConfirmAccountNumber(String paramString)
  {
    this.HG = paramString;
  }
  
  public String getConfirmAccountNumber()
  {
    return this.HG;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.AddPayee
 * JD-Core Version:    0.7.0.1
 */