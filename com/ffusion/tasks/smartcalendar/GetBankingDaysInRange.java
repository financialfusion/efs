package com.ffusion.tasks.smartcalendar;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.banking.BankingDays;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBankingDaysInRange
  extends BaseTask
{
  private int aNy = 0;
  private String aNx = null;
  private String aNA = null;
  private String aNC = null;
  private boolean[] aNB;
  private int aNz;
  
  public String getACHCompanyId()
  {
    return this.aNx;
  }
  
  public void setACHCompanyId(String paramString)
  {
    this.aNx = paramString;
  }
  
  public String getSECCode()
  {
    return this.aNA;
  }
  
  public void setSECCode(String paramString)
  {
    this.aNA = paramString;
  }
  
  public String getWireDest()
  {
    return this.aNC;
  }
  
  public void setWireDest(String paramString)
  {
    this.aNC = paramString;
  }
  
  public String getTransactionType()
  {
    return this.aNy + "";
  }
  
  public void setTransactionType(String paramString)
  {
    try
    {
      this.aNy = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.aNy = 0;
    }
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    String str1 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    this.aNz = 0;
    try
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      Date localDate1 = (Date)localHttpSession.getAttribute("SCStartDate");
      Date localDate2 = (Date)localHttpSession.getAttribute("SCEndDate");
      BankIdentifier localBankIdentifier = (BankIdentifier)localHttpSession.getAttribute("SCBankIdentifier");
      String str2 = "";
      if ((this.aNy == 1) || (this.aNy == 2))
      {
        str2 = "INTRATRN";
      }
      else if ((this.aNy == 3) || (this.aNy == 4))
      {
        str2 = "PMTTRN";
      }
      else if ((this.aNy == 19) || (this.aNy == 20))
      {
        str2 = "ETFTRN";
      }
      else if ((this.aNy == 9) || (this.aNy == 10) || (this.aNy == 12) || (this.aNy == 13) || (this.aNy == 17) || (this.aNy == 18))
      {
        if ((this.aNx != null) && (this.aNx.length() != 0) && (this.aNA != null) && (this.aNA.length() != 0)) {
          str2 = "ACHBATCHTRN";
        }
      }
      else if ((this.aNy == 5) || (this.aNy == 14))
      {
        str2 = "WIRETRN";
        localHashMap.put("WireDest", this.aNC);
      }
      else if ((this.aNy == 15) || (this.aNy == 16))
      {
        str2 = "CASHCONTRN";
      }
      if ((this.aNy == 0) || (str2.equals("")))
      {
        this.aNB = SmartCalendar.getBankingDaysInRange(localSecureUser, localBankIdentifier, localDate1, localDate2, localHashMap);
      }
      else
      {
        GregorianCalendar localGregorianCalendar1 = new GregorianCalendar();
        localGregorianCalendar1.setTime(localDate1);
        GregorianCalendar localGregorianCalendar2 = new GregorianCalendar();
        localGregorianCalendar2.setTime(localDate2);
        BankingDays localBankingDays = new BankingDays();
        localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
        if ((localSecureUser.getAffiliateID() != null) && (localSecureUser.getAffiliateID().trim().length() > 0))
        {
          localObject = AffiliateBankAdmin.getAffiliateBankInfoByID(localSecureUser, localSecureUser.getAffiliateIDValue(), localHashMap);
          localBankingDays.setFiID(((AffiliateBank)localObject).getFIBPWID());
        }
        Object localObject = new SimpleDateFormat("MM/dd/yyyy");
        String str3 = ((SimpleDateFormat)localObject).format(localGregorianCalendar1.getTime());
        System.err.println("start date is " + str3);
        str3 = ((SimpleDateFormat)localObject).format(localGregorianCalendar2.getTime());
        System.err.println("end date is " + str3);
        localBankingDays.setStartDate(localGregorianCalendar1);
        localBankingDays.setEndDate(localGregorianCalendar2);
        localBankingDays.setTransType(String.valueOf(str2));
        localBankingDays.setCompId(this.aNx);
        localBankingDays.setSec(this.aNA);
        localBankingDays = PaymentsAdmin.getBankingDaysInRange(localBankingDays, localHashMap);
        this.aNB = localBankingDays.getBankingDays();
      }
    }
    catch (CSILException localCSILException)
    {
      str1 = this.taskErrorURL;
      this.error = localCSILException.getCode();
    }
    return str1;
  }
  
  public String getNextBusinessDay()
  {
    String str;
    if (!hasNextBusinessDay())
    {
      str = "";
    }
    else
    {
      str = jdMethod_try(this.aNB[this.aNz]);
      this.aNz += 1;
    }
    return str;
  }
  
  private String jdMethod_try(boolean paramBoolean)
  {
    if (paramBoolean) {
      return "Y";
    }
    return "F";
  }
  
  public boolean getNextBusinessDayValue()
  {
    int i;
    if (!hasNextBusinessDay())
    {
      i = 0;
    }
    else
    {
      i = this.aNB[this.aNz];
      this.aNz += 1;
    }
    return i;
  }
  
  public boolean hasNextBusinessDay()
  {
    return (this.aNB.length != 0) && (this.aNz < this.aNB.length);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.smartcalendar.GetBankingDaysInRange
 * JD-Core Version:    0.7.0.1
 */