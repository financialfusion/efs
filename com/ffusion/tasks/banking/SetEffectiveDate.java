package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.Banking;
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

public class SetEffectiveDate
  extends ExtendedBaseTask
  implements Task
{
  private Transfer yh;
  private DateTime yk = new DateTime();
  private boolean yi = false;
  private boolean yj = false;
  protected String datetype = "SHORT";
  
  public SetEffectiveDate()
  {
    this.beanSessionName = "Transfer";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.yi = false;
    String str = null;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 1037;
      str = this.taskErrorURL;
      return str;
    }
    HashMap localHashMap = null;
    this.yh = ((Transfer)localHttpSession.getAttribute(this.beanSessionName));
    if (this.yh == null)
    {
      this.error = 1004;
      str = this.taskErrorURL;
      return str;
    }
    try
    {
      localHashMap = new HashMap();
      if (localSecureUser.getBusinessID() == 0) {
        this.yh.setCustomerID(localSecureUser.getPrimaryUserID());
      } else {
        this.yh.setCustomerID(localSecureUser.getBusinessID());
      }
      this.yh.setBankID(localSecureUser.getBPWFIID());
      Date localDate1 = Banking.getEffectiveDate(localSecureUser, this.yh, localHashMap);
      Object localObject;
      if (localDate1 == null)
      {
        localObject = new DateTime();
        localDate1 = new DateTime((Calendar)localObject, localSecureUser.getLocale()).getTime();
      }
      this.yk = new DateTime(localDate1, localSecureUser.getLocale());
      if (this.dateFormat != null) {
        this.yk.setFormat(this.dateFormat);
      }
      if (this.yj) {
        if (jdMethod_int(this.yh))
        {
          localObject = new BankIdentifier(this.locale);
          AffiliateBank localAffiliateBank = (AffiliateBank)localHttpSession.getAttribute("AffiliateBank");
          if (localAffiliateBank != null)
          {
            ((BankIdentifier)localObject).setBankDirectoryId(localAffiliateBank.getAffiliateRoutingNum());
          }
          else
          {
            localAffiliateBank = AffiliateBankAdmin.getAffiliateBankByID(localSecureUser, localSecureUser.getAffiliateIDValue(), null);
            ((BankIdentifier)localObject).setBankDirectoryId(localAffiliateBank.getAffiliateRoutingNum());
          }
          boolean bool = SmartCalendar.getIgnoreForTransfers(localSecureUser, (BankIdentifier)localObject, new HashMap());
          if (!bool)
          {
            Date localDate2 = SmartCalendar.getTransactionDay(localSecureUser, (BankIdentifier)localObject, this.yh.getDateValue().getTime(), new HashMap());
            Calendar localCalendar1 = Calendar.getInstance(localSecureUser.getLocale());
            localCalendar1.setTime(localDate2);
            Calendar localCalendar2 = Calendar.getInstance(localSecureUser.getLocale());
            localCalendar2.set(11, 0);
            localCalendar2.set(12, 0);
            localCalendar2.set(13, 0);
            localCalendar2.set(14, 0);
            Date localDate3 = localCalendar2.getTime();
            while (localDate2.before(localDate3))
            {
              localCalendar1.add(6, 1);
              localDate2 = SmartCalendar.getTransactionDay(localSecureUser, (BankIdentifier)localObject, localCalendar1.getTime(), new HashMap());
            }
            DateTime localDateTime = new DateTime(localDate2, localSecureUser.getLocale());
            if (this.dateFormat != null) {
              localDateTime.setFormat(this.dateFormat);
            }
            if ((localDateTime.get(1) != this.yh.getDateValue().get(1)) || (localDateTime.get(2) != this.yh.getDateValue().get(2)) || (localDateTime.get(5) != this.yh.getDateValue().get(5)))
            {
              this.yi = true;
              this.yh.setDate(localDateTime);
            }
          }
        }
        else if ((this.yh.getDateValue() == null) || (this.yh.getDateValue().before(this.yk)))
        {
          this.yi = true;
          this.yh.setDate(this.yk);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  private boolean jdMethod_int(Transfer paramTransfer)
  {
    if (paramTransfer == null) {
      return true;
    }
    return ((paramTransfer.getFromAccount() == null) || (paramTransfer.getFromAccount().getCoreAccount() == null) || (paramTransfer.getFromAccount().getCoreAccount().equals("1"))) && ((paramTransfer.getToAccount() == null) || (paramTransfer.getToAccount().getCoreAccount() == null) || (paramTransfer.getToAccount().getCoreAccount().equals("1")));
  }
  
  public String getEffectiveDate()
  {
    return this.yk.toString();
  }
  
  public DateTime getEffectiveDateValue()
  {
    return this.yk;
  }
  
  public void setEffectiveDate(DateTime paramDateTime)
  {
    this.yk = paramDateTime;
  }
  
  public void setEffectiveDate(String paramString)
  {
    try
    {
      if (this.yk == null) {
        this.yk = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.yk.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.yk != null) {
      this.yk.setFormat(paramString);
    }
  }
  
  public String getDateChanged()
  {
    if (this.yi) {
      return "true";
    }
    return "false";
  }
  
  public boolean getDateChangedValue()
  {
    return this.yi;
  }
  
  public void setProcess(String paramString)
  {
    this.yj = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.SetEffectiveDate
 * JD-Core Version:    0.7.0.1
 */