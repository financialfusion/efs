package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.TransferBatch;
import com.ffusion.beans.banking.Transfers;
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

public class SetBatchEffectiveDate
  extends ExtendedBaseTask
  implements Task
{
  private TransferBatch en;
  private DateTime eq = new DateTime();
  private boolean[] eo;
  private int ep;
  protected String datetype = "SHORT";
  
  public SetBatchEffectiveDate()
  {
    this.beanSessionName = "TransferBatch";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
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
    this.en = ((TransferBatch)localHttpSession.getAttribute(this.beanSessionName));
    if (this.en == null)
    {
      this.error = 1004;
      str = this.taskErrorURL;
      return str;
    }
    this.eo = new boolean[this.en.getTransfers().size()];
    try
    {
      for (int i = 0; i < this.en.getTransfers().size(); i++)
      {
        Transfer localTransfer = (Transfer)this.en.getTransfers().get(i);
        Object localObject1;
        Object localObject2;
        if (jdMethod_for(localTransfer))
        {
          localObject1 = new BankIdentifier(this.locale);
          localObject2 = (AffiliateBank)localHttpSession.getAttribute("AffiliateBank");
          if (localObject2 != null)
          {
            ((BankIdentifier)localObject1).setBankDirectoryId(((AffiliateBank)localObject2).getAffiliateRoutingNum());
          }
          else
          {
            localObject2 = AffiliateBankAdmin.getAffiliateBankByID(localSecureUser, localSecureUser.getAffiliateIDValue(), null);
            ((BankIdentifier)localObject1).setBankDirectoryId(((AffiliateBank)localObject2).getAffiliateRoutingNum());
          }
          boolean bool = SmartCalendar.getIgnoreForTransfers(localSecureUser, (BankIdentifier)localObject1, new HashMap());
          if (!bool)
          {
            Date localDate1 = SmartCalendar.getTransactionDay(localSecureUser, (BankIdentifier)localObject1, localTransfer.getDateValue().getTime(), new HashMap());
            Calendar localCalendar1 = Calendar.getInstance(localSecureUser.getLocale());
            localCalendar1.setTime(localDate1);
            Calendar localCalendar2 = Calendar.getInstance(localSecureUser.getLocale());
            localCalendar2.set(11, 0);
            localCalendar2.set(12, 0);
            localCalendar2.set(13, 0);
            localCalendar2.set(14, 0);
            Date localDate2 = localCalendar2.getTime();
            while (localDate1.before(localDate2))
            {
              localCalendar1.add(6, 1);
              localDate1 = SmartCalendar.getTransactionDay(localSecureUser, (BankIdentifier)localObject1, localCalendar1.getTime(), new HashMap());
            }
            DateTime localDateTime = new DateTime(localDate1, localSecureUser.getLocale());
            if (this.dateFormat != null) {
              localDateTime.setFormat(this.dateFormat);
            }
            if ((localDateTime.get(1) != localTransfer.getDateValue().get(1)) || (localDateTime.get(2) != localTransfer.getDateValue().get(2)) || (localDateTime.get(5) != localTransfer.getDateValue().get(5)))
            {
              this.eo[i] = true;
              localTransfer.setDate(localDateTime);
            }
          }
        }
        else
        {
          localHashMap = new HashMap();
          if (localSecureUser.getBusinessID() == 0) {
            localTransfer.setCustomerID(localSecureUser.getProfileID());
          } else {
            localTransfer.setCustomerID(localSecureUser.getBusinessID());
          }
          localTransfer.setBankID(localSecureUser.getBPWFIID());
          localObject1 = Banking.getEffectiveDate(localSecureUser, localTransfer, localHashMap);
          if (localObject1 == null)
          {
            localObject2 = new DateTime();
            localObject1 = new DateTime((Calendar)localObject2, localSecureUser.getLocale()).getTime();
          }
          this.eq = new DateTime((Date)localObject1, localSecureUser.getLocale());
          if (this.dateFormat != null) {
            this.eq.setFormat(this.dateFormat);
          }
          if ((localTransfer.getDateValue() == null) || (localTransfer.getDateValue().before(this.eq)))
          {
            this.eo[i] = true;
            localTransfer.setDate(this.eq);
          }
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
  
  private boolean jdMethod_for(Transfer paramTransfer)
  {
    if (paramTransfer == null) {
      return true;
    }
    return ((paramTransfer.getFromAccount() == null) || (paramTransfer.getFromAccount().getCoreAccount() == null) || (paramTransfer.getFromAccount().getCoreAccount().equals("1"))) && ((paramTransfer.getToAccount() == null) || (paramTransfer.getToAccount().getCoreAccount() == null) || (paramTransfer.getToAccount().getCoreAccount().equals("1")));
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.eq != null) {
      this.eq.setFormat(paramString);
    }
  }
  
  public boolean[] getDateChangedValue()
  {
    return this.eo;
  }
  
  public void setCurrentTransfer(String paramString)
  {
    this.ep = Integer.valueOf(paramString).intValue();
  }
  
  public String getCurrentTransferDateChanged()
  {
    if (this.eo[this.ep] != 0) {
      return "true";
    }
    return "false";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.SetBatchEffectiveDate
 * JD-Core Version:    0.7.0.1
 */