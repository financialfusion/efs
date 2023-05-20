package com.ffusion.tasks.bptw;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BPTW;
import com.ffusion.services.bptw.BillPay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPayments
  extends BaseTask
  implements Task
{
  private String kh = "";
  private int kk = 0;
  private boolean km = false;
  private String ki = "";
  private String kl = "";
  private String kj = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BillPay localBillPay = (BillPay)localHttpSession.getAttribute("BptwBillPay");
    User localUser = (User)localHttpSession.getAttribute("User");
    if (localUser == null)
    {
      this.error = 17006;
      return this.taskErrorURL;
    }
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    Accounts localAccounts = new Accounts(localLocale);
    Account localAccount = localAccounts.create(localBillPay.getBankID(), this.kh, this.kk);
    localAccount.setFilterable("BillPay");
    String str = this.kj;
    if ((this.kj == null) || (this.kj.trim().equals(""))) {
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
    localHashMap.put("User", localUser);
    localHashMap.put("PAYEES", localPayees);
    try
    {
      localPayees = BPTW.getPayees(localSecureUser, localHashMap);
    }
    catch (CSILException localCSILException2)
    {
      this.error = MapError.mapError(localCSILException2);
    }
    localHttpSession.setAttribute("Payees", localPayees);
    if (this.error != 0) {
      return this.serviceErrorURL;
    }
    Payments localPayments = new Payments(localLocale);
    RecPayments localRecPayments = new RecPayments(localLocale);
    localHashMap = new HashMap();
    if (localBillPay != null) {
      localHashMap.put("SERVICE", localBillPay);
    }
    localHashMap.put("PAYMENTS", localPayments);
    localHashMap.put("RECPAYMENTS", localRecPayments);
    try
    {
      Object[] arrayOfObject = BPTW.getPayments(localSecureUser, localAccounts, localPayees, localHashMap);
      localPayments = (Payments)arrayOfObject[0];
      localRecPayments = (RecPayments)arrayOfObject[1];
    }
    catch (CSILException localCSILException3)
    {
      this.error = MapError.mapError(localCSILException3);
    }
    if (this.km)
    {
      localHttpSession.setAttribute("RecPayments", localRecPayments);
      this.successURL = this.ki;
    }
    else
    {
      localHttpSession.setAttribute("Payments", localPayments);
      this.successURL = this.kl;
    }
    if (this.error != 0) {
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  public void setAccountNum(String paramString)
  {
    this.kh = paramString;
  }
  
  public String getAccountNum()
  {
    return this.kh;
  }
  
  public void setAccountType(String paramString)
  {
    try
    {
      this.kk = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setAccountType(int paramInt)
  {
    this.kk = paramInt;
  }
  
  public String getAccountType()
  {
    return String.valueOf(this.kk);
  }
  
  public int getAccountTypeValue()
  {
    return this.kk;
  }
  
  public void setRecurring(String paramString)
  {
    if (paramString.equalsIgnoreCase("true")) {
      this.km = true;
    } else {
      this.km = false;
    }
  }
  
  public void setRecurring(boolean paramBoolean)
  {
    this.km = paramBoolean;
  }
  
  public String getRecurring()
  {
    if (this.km) {
      return "true";
    }
    return "false";
  }
  
  public boolean getRecurringValue()
  {
    return this.km;
  }
  
  public void setRecurringURL(String paramString)
  {
    this.ki = paramString;
  }
  
  public String getRecurringURL()
  {
    return this.ki;
  }
  
  public void setNonrecurringURL(String paramString)
  {
    this.kl = paramString;
  }
  
  public String getNonrecurringURL()
  {
    return this.kl;
  }
  
  public void setUserId(String paramString)
  {
    this.kj = paramString;
  }
  
  public String getUserId()
  {
    return this.kj;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bptw.GetPayments
 * JD-Core Version:    0.7.0.1
 */