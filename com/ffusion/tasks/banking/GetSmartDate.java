package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.BankIdentifier;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetSmartDate
  extends ExtendedBaseTask
  implements Task
{
  private Transfer yd;
  protected String datetype = "SHORT";
  protected AffiliateBank affiliateBank = null;
  protected boolean ignoreSmartDate = false;
  
  public GetSmartDate()
  {
    this.beanSessionName = "Transfer";
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
    this.yd = ((Transfer)localHttpSession.getAttribute(this.beanSessionName));
    if (this.yd == null)
    {
      this.error = 1004;
      return this.taskErrorURL;
    }
    try
    {
      this.yd.setCustomerID(localSecureUser.getBusinessID());
      this.yd.setBankID(localSecureUser.getBPWFIID());
      Object localObject1;
      Object localObject2;
      if (this.affiliateBank == null)
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
        this.ignoreSmartDate = SmartCalendar.getIgnoreForTransfers(localSecureUser, (BankIdentifier)localObject1, new HashMap());
      }
      if (!this.ignoreSmartDate)
      {
        localObject1 = ACH.getSmartDate(localSecureUser, this.yd.getDateValue());
        localObject2 = new DateTime((Date)localObject1, localSecureUser.getLocale());
        ((DateTime)localObject2).setFormat(this.datetype);
        this.yd.setDate((DateTime)localObject2);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    this.datetype = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetSmartDate
 * JD-Core Version:    0.7.0.1
 */