package com.ffusion.tasks.accounts;

import com.ffusion.beans.Contact;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.CommBankIdentifier;
import com.ffusion.util.Strings;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddFreeFormAccounts
  extends BaseTask
  implements Task
{
  public static final String DEFAULT_PREFIX = "FreeForm";
  public static final String MAX_NUM_FREE_FORM_ACCTS_SESS_NAME = "MaxNumFreeFormAccts";
  public static final String BANK_NAME_NAME = "BankName";
  public static final String ROUTING_NUM_NAME = "RoutingNum";
  public static final String ACCT_NUM_NAME = "AcctNum";
  public static final String ACCT_TYPE_NAME = "AcctType";
  public static final String ACCT_CURRENCY_NAME = "AcctCurrency";
  public static final String ACCT_NICK_NAME_NAME = "AcctNickName";
  public static final String ACCT_PPAY_NAME = "AcctPPay";
  public static final String ADD_ACCT_NAME = "AddAcct";
  public static final String ACCT_CORE_NAME = "AcctCore";
  public static final String ACCT_ZBA_FLAG = "ZBAFlag";
  public static final String ACCT_SHOW_PREVIOUS_DAY_OPENING_LEDGER = "ShowPreviousDayOpeningLedger";
  public static final String ACCT_PPAY_VALUE_DISABLED = "0";
  public static final String ACCT_PPAY_VALUE_ENABLED = "1";
  private static final String ka = "0";
  private static final String kb = "1";
  public static final String ADD_ACCT_VALUE = "add";
  public static final String ACCT_CORE_CORE = "core";
  public static final String ACCT_CORE_EXTERNAL = "external";
  private String j9 = "Accounts";
  private String ke;
  private String kc = "FreeForm";
  protected boolean _autoEntitleAccounts = true;
  protected boolean _enrollingBusiness = true;
  private boolean j8 = false;
  private String kd;
  protected boolean m_checkForDuplicateAccount = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    Business localBusiness = null;
    int i = 0;
    com.ffusion.beans.accounts.Accounts localAccounts1 = new com.ffusion.beans.accounts.Accounts(localSecureUser.getLocale());
    com.ffusion.beans.accounts.Accounts localAccounts2 = new com.ffusion.beans.accounts.Accounts(localSecureUser.getLocale());
    Account localAccount1 = null;
    int j = 0;
    String str2 = "ADD_CORE_ACCOUNT";
    String str3 = this.kc + "BankName";
    String str4 = this.kc + "RoutingNum";
    String str5 = this.kc + "AcctNum";
    String str6 = this.kc + "AcctType";
    String str7 = this.kc + "AcctCurrency";
    String str8 = this.kc + "AcctNickName";
    String str9 = this.kc + "AcctPPay";
    String str10 = this.kc + "AddAcct";
    String str11 = this.kc + "AcctCore";
    String str12 = this.kc + "ZBAFlag";
    String str13 = this.kc + "ShowPreviousDayOpeningLedger";
    String str14 = null;
    String str15 = null;
    String str16 = null;
    String str17 = null;
    String str18 = null;
    String str19 = null;
    String str20 = null;
    String str21 = null;
    String str22 = null;
    String str23 = null;
    String str24 = null;
    Account localAccount2 = null;
    com.ffusion.beans.accounts.Accounts localAccounts3 = null;
    boolean bool = false;
    this.error = 0;
    localBusiness = (Business)localHttpSession.getAttribute(this.ke);
    if ((this.error == 0) && (localBusiness == null))
    {
      this.error = 4104;
      str1 = this.taskErrorURL;
    }
    if (this.error == 0)
    {
      j = localBusiness.getIdValue();
      try
      {
        i = Integer.parseInt((String)localHttpSession.getAttribute("MaxNumFreeFormAccts"));
      }
      catch (Exception localException1)
      {
        i = 0;
      }
      for (int k = 1; k <= i; k++)
      {
        str21 = (String)localHttpSession.getAttribute(str10 + k);
        if ((str21 != null) && (str21.equals("add")))
        {
          str14 = (String)localHttpSession.getAttribute(str3 + k);
          str15 = (String)localHttpSession.getAttribute(str4 + k);
          str16 = (String)localHttpSession.getAttribute(str5 + k);
          str17 = (String)localHttpSession.getAttribute(str6 + k);
          str18 = (String)localHttpSession.getAttribute(str7 + k);
          str19 = (String)localHttpSession.getAttribute(str8 + k);
          str20 = (String)localHttpSession.getAttribute(str9 + k);
          str22 = (String)localHttpSession.getAttribute(str11 + k);
          str23 = (String)localHttpSession.getAttribute(str12 + k);
          str24 = (String)localHttpSession.getAttribute(str13 + k);
          try
          {
            bool = CommBankIdentifier.isValidCheckZeros(str15);
          }
          catch (Exception localException2)
          {
            bool = false;
          }
          if (!bool)
          {
            this.error = 18013;
            str1 = this.taskErrorURL;
            break;
          }
          if ((str16 == null) || (str16.trim().length() == 0))
          {
            this.error = 18014;
            str1 = this.taskErrorURL;
            break;
          }
          if ((str16.trim().length() < 3) || (str16.trim().length() > 37))
          {
            this.error = 18020;
            str1 = this.taskErrorURL;
            break;
          }
          if ((str22.equals("external")) && (str16.trim().length() > 17) && (!CommBankIdentifier.getBankIdentifierFlag()))
          {
            this.error = 18023;
            str1 = this.taskErrorURL;
            break;
          }
          if (!Strings.isValidAccountNumber(str16, localSecureUser.getLocale()))
          {
            this.error = 18014;
            str1 = this.taskErrorURL;
            break;
          }
          if (str20 == null) {
            str20 = "0";
          }
          if (str22.equals("core"))
          {
            str22 = "1";
            str2 = "ADD_CORE_ACCOUNT";
          }
          else
          {
            str22 = "0";
            str2 = "ADD_EXT_ACCOUNT";
          }
          localAccount1 = new Account();
          localAccount1.setBankName(str14);
          localAccount1.setRoutingNum(str15);
          localAccount1.setNumber(str16);
          localAccount1.setType(Integer.parseInt(str17));
          localAccount1.setID(str16, str17);
          localAccount1.setCurrencyCode(str18);
          localAccount1.setNickName(str19);
          localAccount1.setPositivePay(str20);
          localAccount1.setCoreAccount(str22);
          localAccount1.setZBAFlag(str23);
          localAccount1.setShowPreviousDayOpeningLedger(str24);
          if ((!this.j8) && (localBusiness.getStatusValue() == 1))
          {
            Contact localContact = new Contact();
            localContact.set(localBusiness);
            localContact.clear();
            localAccount1.setContact(localContact);
          }
          try
          {
            localAccount2 = new Account();
            localAccount2.setBankID(localBusiness.getBankId());
            localAccount2.setID(localAccount1.getID());
            localAccounts3 = com.ffusion.csil.core.Accounts.searchAccounts(localSecureUser, localAccount2, j, localHashMap);
            if ((localAccounts3 != null) && (!localAccounts3.isEmpty()))
            {
              localAccounts2.add(localAccount1);
              localAccount2 = null;
              localAccounts3 = null;
            }
            else if (this.m_checkForDuplicateAccount)
            {
              localAccount2.setRoutingNum(localAccount1.getRoutingNum());
              localAccounts3 = com.ffusion.csil.core.Accounts.searchAccounts(localSecureUser, localAccount2, 0, localHashMap);
              if (((localAccounts3 == null) || (localAccounts3.size() == 0)) && (localAccounts1.getByIDAndRoutingNum(localAccount1.getID(), localAccount1.getRoutingNum()) == null)) {
                localAccounts1.add(localAccount1);
              } else {
                localAccounts2.add(localAccount1);
              }
            }
            else
            {
              localAccounts1.add(localAccount1);
            }
          }
          catch (CSILException localCSILException2)
          {
            this.error = MapError.mapError(localCSILException2);
            str1 = this.serviceErrorURL;
          }
          finally
          {
            localAccount2 = null;
            localAccounts3 = null;
          }
        }
      }
      if (this.error == 0) {
        if (localAccounts2.size() > 0)
        {
          this.error = 18016;
          str1 = this.taskErrorURL;
        }
        else if (!this.j8)
        {
          try
          {
            if (this._enrollingBusiness)
            {
              com.ffusion.csil.core.Accounts.addAccounts(localSecureUser, localAccounts1, j, localHashMap);
            }
            else
            {
              com.ffusion.csil.core.Accounts.addAccounts(localSecureUser, localAccounts1, this._autoEntitleAccounts, this._enrollingBusiness, j, localHashMap);
              if (localAccount1 != null)
              {
                Object[] arrayOfObject = new Object[2];
                arrayOfObject[0] = localAccount1.getRoutingNum();
                arrayOfObject[1] = localAccount1.buildLocalizableAccountID();
                LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", str2, arrayOfObject);
                HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, Integer.toString(j));
                localHistoryTracker.logChange(Business.BEAN_NAME, Business.ACCOUNTS, null, localAccount1.getNumber(), localLocalizableString);
                try
                {
                  HistoryAdapter.addHistory(localHistoryTracker.getHistories());
                }
                catch (ProfileException localProfileException)
                {
                  DebugLog.log(Level.SEVERE, "Add History failed for AddFreeFormAccounts: " + localProfileException.toString());
                }
              }
            }
          }
          catch (CSILException localCSILException1)
          {
            this.error = MapError.mapError(localCSILException1);
            str1 = this.serviceErrorURL;
          }
        }
      }
    }
    if (this.kd != null) {
      localHttpSession.setAttribute(this.kd, localAccounts2);
    }
    return str1;
  }
  
  public String getPrefix()
  {
    return this.kc;
  }
  
  public void setPrefix(String paramString)
  {
    this.kc = paramString;
    if (this.kc == null) {
      this.kc = "FreeForm";
    }
  }
  
  public String getBusinessSessionName()
  {
    return this.ke;
  }
  
  public void setBusinessSessionName(String paramString)
  {
    this.ke = paramString;
  }
  
  public void setAutoEntitleAccounts(String paramString)
  {
    this._autoEntitleAccounts = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAutoEntitleAccounts()
  {
    return String.valueOf(this._autoEntitleAccounts);
  }
  
  public void setEnrollingBusiness(String paramString)
  {
    this._enrollingBusiness = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEnrollingBusiness()
  {
    return String.valueOf(this._enrollingBusiness);
  }
  
  public void setAccountsKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.j9 = paramString;
    }
  }
  
  public String getAccountsKey()
  {
    return this.j9;
  }
  
  public void setValidateOnly(String paramString)
  {
    this.j8 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getValidateOnly()
  {
    return String.valueOf(this.j8);
  }
  
  public String getInvalidAccountsSessionName()
  {
    return this.kd;
  }
  
  public void setInvalidAccountsSessionName(String paramString)
  {
    this.kd = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.AddFreeFormAccounts
 * JD-Core Version:    0.7.0.1
 */