package com.ffusion.tasks.billpay;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.BankIdentifier;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPaymentByID
  extends ExtendedBaseTask
  implements Task
{
  protected boolean isRecurring = false;
  
  public GetPaymentByID()
  {
    this.beanSessionName = "Payment";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.id == null) || (this.id.length() == 0))
    {
      this.error = 81;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("BillPayAccounts");
    Payees localPayees = (Payees)localHttpSession.getAttribute("Payees");
    Object localObject = null;
    try
    {
      if (this.isRecurring) {
        localObject = Billpay.getRecPaymentByID(localSecureUser, this.id, localAccounts, localPayees, localHashMap);
      } else {
        localObject = Billpay.getPaymentByID(localSecureUser, this.id, localAccounts, localPayees, localHashMap);
      }
      try
      {
        BankIdentifier localBankIdentifier = new BankIdentifier(this.locale);
        localBankIdentifier.setBankDirectoryId(((Payment)localObject).getAccount().getRoutingNum());
        Date localDate = null;
        if ((localObject != null) && (((Payment)localObject).getPayDateValue() != null) && (((Payment)localObject).getPayee() != null)) {
          localDate = SmartCalendar.getOffsetBankingDay(localSecureUser, localBankIdentifier, ((Payment)localObject).getPayDateValue().getTime(), ((Payment)localObject).getPayee().getDaysToPayValue(), new HashMap());
        }
        Calendar localCalendar = Calendar.getInstance();
        if (localDate != null) {
          localCalendar.setTime(localDate);
        }
        ((Payment)localObject).setDeliverByDate(localCalendar);
      }
      catch (CSILException localCSILException1) {}
    }
    catch (CSILException localCSILException2)
    {
      this.error = MapError.mapError(localCSILException2);
      return this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this.beanSessionName, localObject);
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetPaymentByID
 * JD-Core Version:    0.7.0.1
 */