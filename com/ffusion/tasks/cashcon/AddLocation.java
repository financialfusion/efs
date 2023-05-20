package com.ffusion.tasks.cashcon;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.cashcon.CashConAccount;
import com.ffusion.beans.cashcon.CashConAccounts;
import com.ffusion.beans.cashcon.CashConCompanies;
import com.ffusion.beans.cashcon.CashConCompany;
import com.ffusion.beans.cashcon.Location;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CashCon;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddLocation
  extends EditLocation
  implements Task
{
  protected String _accountCollectionName = "Accounts";
  protected boolean _autoEntitleLocation = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = processValidate(localHttpSession);
    if (this.error == 0) {
      return processLocation(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    }
    return str;
  }
  
  protected String processLocation(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = getClass().getName() + ".process";
    String str2 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    AffiliateBanks localAffiliateBanks = (AffiliateBanks)localHttpSession.getAttribute(this._bankCollectionName);
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this._accountsCollectionName);
    CashConCompanies localCashConCompanies = (CashConCompanies)localHttpSession.getAttribute(this._ccCompanyCollectionName);
    try
    {
      Location localLocation = new Location(localLocale);
      localLocation.setDivisionID(this._divisionID);
      localLocation.setLocationName(this._locationName);
      localLocation.setLocationID(this._locationID);
      localLocation.setActive(this._active);
      if (!this._customLocalBank)
      {
        localLocation.setLocalRoutingNumber(this._localRoutingNumber);
        localLocation.setLocalBankName(this._localBankName);
      }
      else
      {
        localObject1 = localAffiliateBanks.getAffiliateBankByAffiliateBankID(this._localBankID);
        localLocation.setLocalRoutingNumber(((AffiliateBank)localObject1).getAffiliateRoutingNum());
        localLocation.setLocalBankName(((AffiliateBank)localObject1).getAffiliateBankName());
      }
      if (!this._customLocalAccount)
      {
        localLocation.setLocalAccountNumber(this._localAccountNumber);
        localLocation.setLocalAccountType(this._localAccountType);
      }
      else
      {
        localObject1 = localAccounts.getByID(this._localAccountID);
        localLocation.setLocalAccountNumber(((Account)localObject1).getNumber());
        localLocation.setLocalAccountType(((Account)localObject1).getTypeValue());
      }
      Object localObject1 = null;
      if ((this._cashConCompanyBPWID != null) && (this._cashConCompanyBPWID.length() != 0)) {
        localObject1 = localCashConCompanies.getByID(this._cashConCompanyBPWID);
      }
      if (localObject1 != null)
      {
        localLocation.setCashConCompanyName(((CashConCompany)localObject1).getCompanyName());
        localLocation.setCashConCompanyBPWID(((CashConCompany)localObject1).getBPWID());
        if ((((CashConCompany)localObject1).getConcEnabled()) && (this._concAccountBPWID != null) && (this._concAccountBPWID.length() != 0))
        {
          localObject2 = ((CashConCompany)localObject1).getConcAccounts().getByID(this._concAccountBPWID);
          if (localObject2 != null)
          {
            localLocation.setConcAccount(((CashConAccount)localObject2).getNickname());
            localLocation.setConcAccountBPWID(((CashConAccount)localObject2).getBPWID());
            try
            {
              if ((this._depositMinimum != null) && (this._depositMinimum.length() != 0) && (this._depositMinimum.length() <= 11)) {
                localLocation.setDepositMinimum(new Currency(this._depositMinimum, localLocale));
              }
            }
            catch (IllegalArgumentException localIllegalArgumentException1)
            {
              this.error = 24108;
              return this.taskErrorURL;
            }
            try
            {
              if ((this._depositMaximum != null) && (this._depositMaximum.length() != 0) && (this._depositMaximum.length() <= 11)) {
                localLocation.setDepositMaximum(new Currency(this._depositMaximum, localLocale));
              }
            }
            catch (IllegalArgumentException localIllegalArgumentException2)
            {
              this.error = 24109;
              return this.taskErrorURL;
            }
            try
            {
              if ((this._anticDeposit != null) && (this._anticDeposit.length() != 0) && (this._anticDeposit.length() <= 11)) {
                localLocation.setAnticDeposit(new Currency(this._anticDeposit, localLocale));
              }
            }
            catch (IllegalArgumentException localIllegalArgumentException3)
            {
              this.error = 24110;
              return this.taskErrorURL;
            }
            try
            {
              if ((this._threshDeposit != null) && (this._threshDeposit.length() != 0) && (this._threshDeposit.length() <= 11)) {
                localLocation.setThreshDeposit(new Currency(this._threshDeposit, localLocale));
              }
            }
            catch (IllegalArgumentException localIllegalArgumentException4)
            {
              this.error = 24111;
              return this.taskErrorURL;
            }
            localLocation.setConsolidateDeposits(this._consolidateDeposits);
            localLocation.setDepositPrenote(this._depositPrenote);
          }
        }
        if ((((CashConCompany)localObject1).getDisbEnabled()) && (this._disbAccountBPWID != null) && (this._disbAccountBPWID.length() != 0))
        {
          localObject2 = ((CashConCompany)localObject1).getDisbAccounts().getByID(this._disbAccountBPWID);
          if (localObject2 != null)
          {
            localLocation.setDisbAccount(((CashConAccount)localObject2).getNickname());
            localLocation.setDisbAccountBPWID(((CashConAccount)localObject2).getBPWID());
            localLocation.setDisbursementPrenote(this._disbursementPrenote);
          }
        }
      }
      else
      {
        this.error = 24002;
        return this.taskErrorURL;
      }
      CashCon.addLocation(localSecureUser, localLocation, this._autoEntitleLocation, localHashMap);
      Object localObject2 = new HistoryTracker(localSecureUser, 2, Integer.toString(localSecureUser.getBusinessID()));
      localLocation.logCreation((HistoryTracker)localObject2, "Add new cash concentration location with name " + localLocation.getLocationName());
      jdMethod_new(str1, (HistoryTracker)localObject2);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, localHttpSession);
      str2 = this.serviceErrorURL;
    }
    return str2;
  }
  
  public String getAccountCollectionName()
  {
    return this._accountCollectionName;
  }
  
  public void setAccountCollectionName(String paramString)
  {
    this._accountCollectionName = paramString;
  }
  
  public String getAutoEntitleLocation()
  {
    return String.valueOf(this._autoEntitleLocation);
  }
  
  public void setAutoEntitleLocation(String paramString)
  {
    this._autoEntitleLocation = Boolean.valueOf(paramString).booleanValue();
  }
  
  private static void jdMethod_new(String paramString, HistoryTracker paramHistoryTracker)
  {
    if (paramString == null) {
      paramString = AddLocation.class.getName() + ".logHistoryRecord";
    }
    try
    {
      HistoryAdapter.addHistory(paramHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.WARNING, "Add History failed for " + paramString + ": " + localProfileException.toString());
      DebugLog.throwing(paramString, localProfileException);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.AddLocation
 * JD-Core Version:    0.7.0.1
 */