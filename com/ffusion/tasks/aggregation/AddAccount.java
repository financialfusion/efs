package com.ffusion.tasks.aggregation;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.aggregation.AccountNVPair;
import com.ffusion.beans.aggregation.AccountNVPairs;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddAccount
  extends ModifyAccount
  implements Task
{
  private boolean nN = false;
  private String nL = "AccountAggregationCollection";
  private String nM = "";
  
  public AddAccount()
  {
    this.datetype = "SHORT";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag) {
      str = initProcess(paramHttpServletRequest, localHttpSession);
    } else {
      str = addAccount(localHttpSession);
    }
    return str;
  }
  
  protected String initProcess(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    setLocale(this.locale);
    setDateFormat(this.datetype);
    com.ffusion.services.AccountAggregation localAccountAggregation = (com.ffusion.services.AccountAggregation)paramHttpSession.getAttribute(getServiceName());
    HashMap localHashMap = null;
    if (localAccountAggregation != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localAccountAggregation);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      com.ffusion.beans.aggregation.Account localAccount = com.ffusion.csil.core.AccountAggregation.getRequiredAccountFields(localSecureUser, this, localHashMap);
      set(localAccount);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      paramHttpSession.setAttribute("AccountRequiredFields", getAccountNVPairs());
    }
    else
    {
      this.error = 11014;
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  protected String addAccount(HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        if (!this.nN)
        {
          this.nN = true;
          str = doProcess(paramHttpSession);
          this.nN = false;
        }
        else
        {
          this.error = 11011;
          str = this.taskErrorURL;
        }
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = super.validateInput(paramHttpSession);
    if (bool)
    {
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      com.ffusion.beans.accounts.Account localAccount1 = new com.ffusion.beans.accounts.Account();
      jdMethod_for(this, localAccount1);
      com.ffusion.beans.accounts.Account localAccount2 = new com.ffusion.beans.accounts.Account();
      HashMap localHashMap = new HashMap();
      try
      {
        localAccount2.setBankID(localSecureUser.getBankID());
        localAccount2.setID(localAccount1.getID());
        com.ffusion.beans.accounts.Accounts localAccounts = com.ffusion.csil.core.Accounts.searchAccounts(localSecureUser, localAccount2, localSecureUser.getProfileID(), localHashMap);
        if ((localAccounts != null) && (localAccounts.size() > 0))
        {
          this.error = 11017;
          bool = false;
        }
        localAccount2.setRoutingNum(localAccount1.getRoutingNum());
        localAccounts = com.ffusion.csil.core.Accounts.searchAccounts(localSecureUser, localAccount2, 0, localHashMap);
        if ((localAccounts != null) && (localAccounts.size() > 0))
        {
          this.error = 11017;
          bool = false;
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
    }
    return bool;
  }
  
  protected String doProcess(HttpSession paramHttpSession)
  {
    String str = null;
    com.ffusion.services.AccountAggregation localAccountAggregation = (com.ffusion.services.AccountAggregation)paramHttpSession.getAttribute(getServiceName());
    HashMap localHashMap = null;
    if (localAccountAggregation != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localAccountAggregation);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      com.ffusion.beans.aggregation.Account localAccount = com.ffusion.csil.core.AccountAggregation.addAccount(localSecureUser, this, localHashMap);
      set(localAccount);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      com.ffusion.beans.accounts.Account localAccount1 = new com.ffusion.beans.accounts.Account();
      jdMethod_for(this, localAccount1);
      paramHttpSession.setAttribute(getAccountInSessionName(), localAccount1);
      com.ffusion.beans.accounts.Accounts localAccounts = (com.ffusion.beans.accounts.Accounts)paramHttpSession.getAttribute(getAccountsInSessionName());
      if (localAccounts != null) {
        localAccounts.add(localAccount1);
      }
      str = this.successURL;
    }
    return str;
  }
  
  public void setAccountsInSessionName(String paramString)
  {
    this.nL = paramString;
  }
  
  public String getAccountsInSessionName()
  {
    return this.nL;
  }
  
  public void setCurrentRequiredFieldName(String paramString)
  {
    this.nM = paramString;
  }
  
  public void setCurrentRequiredFieldValue(String paramString)
  {
    AccountNVPairs localAccountNVPairs = getAccountNVPairs();
    AccountNVPair localAccountNVPair = localAccountNVPairs.getByName(this.nM);
    localAccountNVPair.setValue(paramString);
    if (this.nM.equals("ACCOUNT_NUMBER")) {
      setNumber(paramString);
    } else if (this.nM.equals("NICKNAME")) {
      setNickName(paramString);
    } else if (this.nM.equals("CURRENCY_CODE")) {
      set("CURRENCY_CODE", paramString);
    }
  }
  
  private void jdMethod_for(com.ffusion.beans.aggregation.Account paramAccount, com.ffusion.beans.accounts.Account paramAccount1)
  {
    if ((paramAccount == null) || (paramAccount1 == null)) {
      return;
    }
    paramAccount1.setLocale(paramAccount.getLocale());
    paramAccount1.setType(com.ffusion.beans.aggregation.Account.getAccountTypeFromAggregationType(paramAccount.getTypeValue()));
    paramAccount1.setID(paramAccount.getNumber(), String.valueOf(paramAccount.getTypeValue()));
    paramAccount1.setStatus(paramAccount.getStatus());
    paramAccount1.setNickName(paramAccount.getNickName());
    paramAccount1.setNumber(paramAccount.getNumber());
    paramAccount1.setBankName(paramAccount.getInstitutionName());
    paramAccount1.setRoutingNum(paramAccount.getInstitutionID());
    paramAccount1.setCurrencyCode((String)paramAccount.get("CURRENCY_CODE"));
    paramAccount1.setCoreAccount("0");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.AddAccount
 * JD-Core Version:    0.7.0.1
 */