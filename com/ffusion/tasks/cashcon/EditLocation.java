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
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.Strings;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditLocation
  extends BaseTask
  implements Task
{
  protected String _bankCollectionName = "AffiliateBanks";
  protected String _accountsCollectionName = "BankingAccounts";
  protected String _ccCompanyCollectionName = "CashConCompanies";
  protected int _divisionID = -1;
  protected String _locationName = null;
  protected String _locationID = null;
  protected boolean _active = false;
  protected boolean _customLocalBank = false;
  protected String _localRoutingNumber = null;
  protected String _localBankName = null;
  protected String _localAccountID = null;
  protected int _localBankID = 0;
  protected boolean _customLocalAccount = false;
  protected String _localAccountNumber = null;
  protected int _localAccountType = 0;
  protected String _cashConCompanyBPWID = null;
  protected String _concAccountBPWID = null;
  protected String _disbAccountBPWID = null;
  protected String _depositMinimum = null;
  protected String _depositMaximum = null;
  protected String _anticDeposit = null;
  protected String _threshDeposit = null;
  protected boolean _consolidateDeposits = false;
  protected boolean _depositPrenote = false;
  protected boolean _disbursementPrenote = false;
  protected String _locationBPWID = null;
  protected String _logId = null;
  protected String _submittedBy = null;
  private String z0 = "USD";
  private boolean zZ = false;
  protected String _sessionKey = "Location";
  private String zX = null;
  private String zY = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = processValidate(localHttpSession);
    if ((this.error == 0) && (this.zZ))
    {
      this.zZ = false;
      return processLocation(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    }
    return str;
  }
  
  protected String processValidate(HttpSession paramHttpSession)
    throws IOException
  {
    this.error = 0;
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    AffiliateBanks localAffiliateBanks = (AffiliateBanks)paramHttpSession.getAttribute(this._bankCollectionName);
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this._accountsCollectionName);
    CashConCompanies localCashConCompanies = (CashConCompanies)paramHttpSession.getAttribute(this._ccCompanyCollectionName);
    Currency localCurrency1 = null;
    Currency localCurrency2 = null;
    if (localAffiliateBanks == null)
    {
      this.error = 24011;
      return this.taskErrorURL;
    }
    if (localCashConCompanies == null)
    {
      this.error = 24003;
      return this.taskErrorURL;
    }
    if ((this._locationName == null) || (this._locationName.length() <= 0))
    {
      this.error = 121;
      return this.taskErrorURL;
    }
    if ((this._locationID == null) || (this._locationID.length() <= 0))
    {
      this.error = 122;
      return this.taskErrorURL;
    }
    if ((this._locationName.length() > 16) || (this._locationID.length() > 15))
    {
      this.error = 87;
      return this.taskErrorURL;
    }
    if (!this._customLocalBank)
    {
      if ((this._localRoutingNumber.length() > 9) || (this._localBankName.length() > 255))
      {
        this.error = 88;
        return this.taskErrorURL;
      }
    }
    else
    {
      localObject = localAffiliateBanks.getAffiliateBankByAffiliateBankID(this._localBankID);
      if (localObject == null)
      {
        this.error = 24112;
        return this.taskErrorURL;
      }
    }
    if (!this._customLocalAccount)
    {
      if (this._localAccountNumber.length() > 17)
      {
        this.error = 89;
        return this.taskErrorURL;
      }
      if (!Strings.isValidAccountNumber(this._localAccountNumber, localLocale))
      {
        this.error = 136;
        return this.taskErrorURL;
      }
    }
    else
    {
      localObject = localAccounts.getByID(this._localAccountID);
      if (localObject == null)
      {
        this.error = 24113;
        return this.taskErrorURL;
      }
    }
    Object localObject = null;
    if ((this._cashConCompanyBPWID != null) && (this._cashConCompanyBPWID.length() != 0)) {
      localObject = localCashConCompanies.getByID(this._cashConCompanyBPWID);
    }
    if ((localObject != null) && (((CashConCompany)localObject).getConcEnabled()) && (this._concAccountBPWID != null) && (this._concAccountBPWID.length() != 0))
    {
      CashConAccount localCashConAccount = ((CashConCompany)localObject).getConcAccounts().getByID(this._concAccountBPWID);
      if (localCashConAccount != null)
      {
        this.z0 = localCashConAccount.getCurrency();
        try
        {
          if ((this._depositMinimum != null) && (this._depositMinimum.length() != 0) && (this._depositMinimum.length() <= 11))
          {
            localCurrency2 = new Currency(this._depositMinimum, this.z0, localLocale);
            if (localCurrency2.doubleValue() < 0.0D)
            {
              this.error = 24108;
              return this.taskErrorURL;
            }
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException1)
        {
          this.error = 24108;
          return this.taskErrorURL;
        }
        try
        {
          if ((this._depositMaximum != null) && (this._depositMaximum.length() != 0) && (this._depositMaximum.length() <= 11))
          {
            localCurrency1 = new Currency(this._depositMaximum, this.z0, localLocale);
            if ((localCurrency2 != null) && (localCurrency1.doubleValue() < localCurrency2.doubleValue()))
            {
              this.error = 24109;
              return this.taskErrorURL;
            }
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException2)
        {
          this.error = 24109;
          return this.taskErrorURL;
        }
        try
        {
          if ((this._anticDeposit != null) && (this._anticDeposit.length() != 0) && (this._anticDeposit.length() <= 11))
          {
            localCurrency1 = new Currency(this._anticDeposit, this.z0, localLocale);
            if (localCurrency1.doubleValue() < 0.0D)
            {
              this.error = 24110;
              return this.taskErrorURL;
            }
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException3)
        {
          this.error = 24110;
          return this.taskErrorURL;
        }
        try
        {
          if ((this._threshDeposit != null) && (this._threshDeposit.length() != 0) && (this._threshDeposit.length() <= 11))
          {
            localCurrency1 = new Currency(this._threshDeposit, this.z0, localLocale);
            if (localCurrency1.doubleValue() < 0.0D)
            {
              this.error = 24111;
              return this.taskErrorURL;
            }
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException4)
        {
          this.error = 24111;
          return this.taskErrorURL;
        }
      }
    }
    return this.successURL;
  }
  
  protected String processLocation(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    String str2 = getClass().getName() + ".processLocation";
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
      localLocation.setLocationBPWID(this._locationBPWID);
      localLocation.setActive(this._active);
      localLocation.setLogId(this._logId);
      localLocation.setSubmittedBy(this._submittedBy);
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
            this.z0 = ((CashConAccount)localObject2).getCurrency();
            localLocation.setConcAccount(((CashConAccount)localObject2).getNickname());
            localLocation.setConcAccountBPWID(((CashConAccount)localObject2).getBPWID());
            try
            {
              if ((this._depositMinimum != null) && (this._depositMinimum.length() != 0) && (this._depositMinimum.length() <= 11)) {
                localLocation.setDepositMinimum(new Currency(this._depositMinimum, this.z0, localLocale));
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
                localLocation.setDepositMaximum(new Currency(this._depositMaximum, this.z0, localLocale));
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
                localLocation.setAnticDeposit(new Currency(this._anticDeposit, this.z0, localLocale));
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
                localLocation.setThreshDeposit(new Currency(this._threshDeposit, this.z0, localLocale));
              }
            }
            catch (IllegalArgumentException localIllegalArgumentException4)
            {
              this.error = 24111;
              return this.taskErrorURL;
            }
            localLocation.setConsolidateDeposits(this._consolidateDeposits);
            localLocation.setDepositPrenote(this._depositPrenote);
            localLocation.setDepPrenoteStatus(this.zY);
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
            localLocation.setDisPrenoteStatus(this.zX);
          }
        }
      }
      CashCon.modifyLocation(localSecureUser, localLocation, localHashMap);
      Object localObject2 = (Location)localHttpSession.getAttribute(this._sessionKey);
      if (localObject2 != null)
      {
        HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, Integer.toString(localSecureUser.getBusinessID()));
        localLocation.logChanges(localHistoryTracker, (Location)localObject2, "Modify cash concentration location with original name as " + ((Location)localObject2).getLocationName());
        jdMethod_int(str2, localHistoryTracker);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, localHttpSession);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  public String getAccountsCollectionName()
  {
    return this._accountsCollectionName;
  }
  
  public void setAccountsCollectionName(String paramString)
  {
    this._accountsCollectionName = paramString;
  }
  
  public String getBankCollectionName()
  {
    return this._bankCollectionName;
  }
  
  public void setBankCollectionName(String paramString)
  {
    this._bankCollectionName = paramString;
  }
  
  public String getCCCompanyCollectionName()
  {
    return this._ccCompanyCollectionName;
  }
  
  public void setCCCompanyCollectionName(String paramString)
  {
    this._ccCompanyCollectionName = paramString;
  }
  
  public String getDivisionID()
  {
    return this._divisionID <= 0 ? "" : String.valueOf(this._divisionID);
  }
  
  public void setDivisionID(String paramString)
  {
    int i = -1;
    try
    {
      i = Integer.parseInt(paramString);
      if (i <= 0) {
        i = -1;
      }
    }
    catch (NumberFormatException localNumberFormatException) {}
    this._divisionID = i;
  }
  
  public String getLocationName()
  {
    return this._locationName;
  }
  
  public void setLocationName(String paramString)
  {
    this._locationName = paramString;
  }
  
  public String getLocationID()
  {
    return this._locationID;
  }
  
  public void setLocationID(String paramString)
  {
    this._locationID = paramString;
  }
  
  public String getActive()
  {
    return String.valueOf(this._active);
  }
  
  public void setActive(String paramString)
  {
    this._active = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getCustomLocalBank()
  {
    return String.valueOf(this._customLocalBank);
  }
  
  public void setCustomLocalBank(String paramString)
  {
    this._customLocalBank = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getLocalRoutingNumber()
  {
    return this._localRoutingNumber;
  }
  
  public void setLocalRoutingNumber(String paramString)
  {
    this._localRoutingNumber = paramString;
  }
  
  public String getLocalBankName()
  {
    return this._localBankName;
  }
  
  public void setLocalBankName(String paramString)
  {
    this._localBankName = paramString;
  }
  
  public String getLocalBankID()
  {
    return String.valueOf(this._localBankID);
  }
  
  public void setLocalBankID(String paramString)
  {
    this._localBankID = 0;
    try
    {
      this._localBankID = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public String getCustomLocalAccount()
  {
    return String.valueOf(this._customLocalAccount);
  }
  
  public void setCustomLocalAccount(String paramString)
  {
    this._customLocalAccount = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getLocalAccountID()
  {
    return this._localAccountID;
  }
  
  public void setLocalAccountID(String paramString)
  {
    this._localAccountID = paramString;
  }
  
  public String getLocalAccountNumber()
  {
    return this._localAccountNumber;
  }
  
  public void setLocalAccountNumber(String paramString)
  {
    this._localAccountNumber = paramString;
  }
  
  public String getLocalAccountType()
  {
    return String.valueOf(this._localAccountType);
  }
  
  public void setLocalAccountType(String paramString)
  {
    try
    {
      this._localAccountType = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this._localAccountType = 0;
    }
  }
  
  public String getCashConCompanyBPWID()
  {
    return this._cashConCompanyBPWID;
  }
  
  public void setCashConCompanyBPWID(String paramString)
  {
    this._cashConCompanyBPWID = paramString;
  }
  
  public String getConcAccountBPWID()
  {
    return this._concAccountBPWID;
  }
  
  public void setConcAccountBPWID(String paramString)
  {
    this._concAccountBPWID = paramString;
  }
  
  public String getDisbAccountBPWID()
  {
    return this._disbAccountBPWID;
  }
  
  public void setDisbAccountBPWID(String paramString)
  {
    this._disbAccountBPWID = paramString;
  }
  
  public String getDepositMinimum()
  {
    return this._depositMinimum;
  }
  
  public void setDepositMinimum(String paramString)
  {
    if (paramString != null) {
      this._depositMinimum = paramString.trim();
    } else {
      this._depositMinimum = null;
    }
  }
  
  public String getDepositMaximum()
  {
    return this._depositMaximum;
  }
  
  public void setDepositMaximum(String paramString)
  {
    if (paramString != null) {
      this._depositMaximum = paramString.trim();
    } else {
      this._depositMaximum = null;
    }
  }
  
  public String getAnticDeposit()
  {
    return this._anticDeposit;
  }
  
  public void setAnticDeposit(String paramString)
  {
    if (paramString != null) {
      this._anticDeposit = paramString.trim();
    } else {
      this._anticDeposit = null;
    }
  }
  
  public String getThreshDeposit()
  {
    return this._threshDeposit;
  }
  
  public void setThreshDeposit(String paramString)
  {
    if (paramString != null) {
      this._threshDeposit = paramString.trim();
    } else {
      this._threshDeposit = null;
    }
  }
  
  public String getConsolidateDeposits()
  {
    return String.valueOf(this._consolidateDeposits);
  }
  
  public void setConsolidateDeposits(String paramString)
  {
    this._consolidateDeposits = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getDepositPrenote()
  {
    return String.valueOf(this._depositPrenote);
  }
  
  public void setDepositPrenote(String paramString)
  {
    this._depositPrenote = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getDisbursementPrenote()
  {
    return String.valueOf(this._disbursementPrenote);
  }
  
  public void setDisbursementPrenote(String paramString)
  {
    this._disbursementPrenote = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getSubmittedBy()
  {
    return this._submittedBy;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this._submittedBy = paramString;
  }
  
  public String getLogId()
  {
    return this._logId;
  }
  
  public void setLogId(String paramString)
  {
    this._logId = paramString;
  }
  
  public String getLocationBPWID()
  {
    return this._locationBPWID;
  }
  
  public void setLocationBPWID(String paramString)
  {
    this._locationBPWID = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.z0;
  }
  
  public void setProcessFlag(String paramString)
  {
    this.zZ = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setDisPrenoteStatus(String paramString)
  {
    this.zX = paramString;
  }
  
  public String getDisPrenoteStatus()
  {
    return this.zX;
  }
  
  public void setDepPrenoteStatus(String paramString)
  {
    this.zY = paramString;
  }
  
  public String getDepPrenoteStatus()
  {
    return this.zY;
  }
  
  public void setSessionKey(String paramString)
  {
    this._sessionKey = paramString;
  }
  
  private static void jdMethod_int(String paramString, HistoryTracker paramHistoryTracker)
  {
    if (paramString == null) {
      paramString = EditLocation.class.getName() + ".logHistoryRecord";
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
 * Qualified Name:     com.ffusion.tasks.cashcon.EditLocation
 * JD-Core Version:    0.7.0.1
 */