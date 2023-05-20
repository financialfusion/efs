package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.util.RoutingNumberUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyACHCompanyAndOffsetAccounts
  extends ModifyACHCompany
{
  private int As = 1;
  private int Ax = 32;
  private int Aw = 1;
  private int At = 32;
  private int Ap = 1;
  private int Aq = 32;
  private String Ar = "ACHOffsetAccounts";
  private String Au = "ACHOffsetAccountsAdd";
  private String Av = "ACHOffsetAccountsDel";
  private String Ao = "MODIFY";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag)
    {
      if (init(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else if (validateCompanyInput(localHttpSession))
    {
      ACHOffsetAccounts localACHOffsetAccounts1 = (ACHOffsetAccounts)localHttpSession.getAttribute(this.Ar);
      ACHOffsetAccounts localACHOffsetAccounts2 = (ACHOffsetAccounts)localHttpSession.getAttribute(this.Au);
      ACHOffsetAccounts localACHOffsetAccounts3 = (ACHOffsetAccounts)localHttpSession.getAttribute(this.Av);
      if ((getACHBatchTypeValue() == 2) || (getACHBatchTypeValue() == 3))
      {
        int i = 0;
        int j = 0;
        int k = 0;
        if (localACHOffsetAccounts1 != null) {
          i = localACHOffsetAccounts1.size();
        }
        if (localACHOffsetAccounts2 != null) {
          j = localACHOffsetAccounts2.size();
        }
        if (localACHOffsetAccounts3 != null) {
          k = localACHOffsetAccounts3.size();
        }
        if (i + j - k < 1)
        {
          this.error = 16530;
          str = this.taskErrorURL;
        }
      }
      if ((this.error == 0) && (validateAccountInput(localACHOffsetAccounts2)))
      {
        if (this.processFlag)
        {
          this.processFlag = false;
          if (this.Ao.toUpperCase() == "ADD") {
            str = addACHCompany(localHttpSession);
          } else {
            str = modifyACHCompany(localHttpSession);
          }
          if (this.error == 0) {
            str = modifyOffsetAccounts(localHttpSession, localACHOffsetAccounts2, localACHOffsetAccounts3);
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
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    if (this.Ao == "MODIFY") {
      return super.init(paramHttpSession);
    }
    this.initFlag = false;
    return true;
  }
  
  protected String addACHCompany(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.error = 0;
    ACHCompanies localACHCompanies = (ACHCompanies)paramHttpSession.getAttribute(this.achCompaniesName);
    if (localACHCompanies == null)
    {
      this.error = 16505;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        HashMap localHashMap = new HashMap();
        localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        AffiliateBank localAffiliateBank = AffiliateBankAdmin.getAffiliateBankInfoByID(localSecureUser, Integer.parseInt(this.affiliateBankID), localHashMap);
        ACHCompany localACHCompany = ACH.addACHCompany(localSecureUser, this, localAffiliateBank.getFIBPWID(), this._autoEntitleACHCompany, localHashMap);
        processEntitlements(localSecureUser);
        set(localACHCompany);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException, paramHttpSession);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        paramHttpSession.setAttribute(this.achCompanyName, this);
        localACHCompanies.add(this);
      }
    }
    return str;
  }
  
  protected String modifyOffsetAccounts(HttpSession paramHttpSession, ACHOffsetAccounts paramACHOffsetAccounts1, ACHOffsetAccounts paramACHOffsetAccounts2)
  {
    String str = this.successURL;
    str = addOffsetAccounts(paramHttpSession, paramACHOffsetAccounts1);
    if (this.error == 0) {
      str = delOffsetAccounts(paramHttpSession, paramACHOffsetAccounts2);
    }
    if (this.error == 0) {}
    return str;
  }
  
  protected boolean validateCompanyInput(HttpSession paramHttpSession)
  {
    return validateInput(paramHttpSession);
  }
  
  protected boolean validateAccountInput(ACHOffsetAccounts paramACHOffsetAccounts)
  {
    boolean bool = true;
    if (paramACHOffsetAccounts != null) {
      for (int i = 0; i < paramACHOffsetAccounts.size(); i++)
      {
        ACHOffsetAccount localACHOffsetAccount = (ACHOffsetAccount)paramACHOffsetAccounts.get(i);
        if (this.validate != null)
        {
          if ((bool) && (this.validate.indexOf("OFFSETACCOUNTNUMBER") != -1)) {
            bool = validateOffsetAccountNumber(localACHOffsetAccount);
          }
          if ((bool) && (this.validate.indexOf("OFFSETACCOUNTNAME") != -1)) {
            bool = validateOffsetAccountName(localACHOffsetAccount);
          }
          if ((bool) && (this.validate.indexOf("OFFSETACCOUNTTYPE") != -1)) {
            bool = validateOffsetAccountType(localACHOffsetAccount);
          }
          if ((bool) && (this.validate.indexOf("OFFSETACCOUNTBANKID") != -1)) {
            bool = validateOffsetAccountRoutingNumber(localACHOffsetAccount);
          }
          this.validate = null;
        }
      }
    }
    return bool;
  }
  
  protected boolean validateOffsetAccountNumber(ACHOffsetAccount paramACHOffsetAccount)
  {
    if (paramACHOffsetAccount.getNumber() == null) {
      this.error = 16508;
    } else if ((paramACHOffsetAccount.getNumber().length() > this.Ax) || (Strings.removeChars(paramACHOffsetAccount.getNumber(), ' ').length() > 17)) {
      this.error = 16509;
    } else if (paramACHOffsetAccount.getNumber().length() < this.As) {
      this.error = 16510;
    }
    return this.error == 0;
  }
  
  protected boolean validateOffsetAccountName(ACHOffsetAccount paramACHOffsetAccount)
  {
    if (paramACHOffsetAccount.getNickName().length() > this.Aq) {
      this.error = 16512;
    }
    return this.error == 0;
  }
  
  protected boolean validateOffsetAccountType(ACHOffsetAccount paramACHOffsetAccount)
  {
    if ((paramACHOffsetAccount.getTypeValue() < 1) || (paramACHOffsetAccount.getTypeValue() > 4)) {
      this.error = 16516;
    }
    return this.error == 0;
  }
  
  protected boolean validateOffsetAccountRoutingNumber(ACHOffsetAccount paramACHOffsetAccount)
  {
    if ((paramACHOffsetAccount.getRoutingNum() == null) || (paramACHOffsetAccount.getRoutingNum().length() == 0)) {
      this.error = 16514;
    } else if (!RoutingNumberUtil.isValidRoutingNumber(paramACHOffsetAccount.getRoutingNum(), true)) {
      this.error = 16515;
    }
    return this.error == 0;
  }
  
  public final void setModeAddModify(String paramString)
  {
    this.Ao = paramString;
  }
  
  public final String getModeAddModify()
  {
    return this.Ao;
  }
  
  public final void setOffsetAccountsExisitingInSessionName(String paramString)
  {
    this.Ar = paramString;
  }
  
  public final String getOffsetAccountsExisitingInSessionName()
  {
    return this.Ar;
  }
  
  public final void setOffsetAccountsAddInSessionName(String paramString)
  {
    this.Au = paramString;
  }
  
  public final String getOffsetAccountsAddInSessionName()
  {
    return this.Au;
  }
  
  public final void setOffsetAccountsDelInSessionName(String paramString)
  {
    this.Av = paramString;
  }
  
  public final String getOffsetAccountsDelInSessionName()
  {
    return this.Av;
  }
  
  protected String addOffsetAccounts(HttpSession paramHttpSession, ACHOffsetAccounts paramACHOffsetAccounts)
  {
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      HashMap localHashMap = null;
      if (paramACHOffsetAccounts != null) {
        for (int i = 0; i < paramACHOffsetAccounts.size(); i++)
        {
          ACHOffsetAccount localACHOffsetAccount = (ACHOffsetAccount)paramACHOffsetAccounts.get(i);
          ACH.addOffsetAccount(localSecureUser, localACHOffsetAccount, this, localHashMap);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected String delOffsetAccounts(HttpSession paramHttpSession, ACHOffsetAccounts paramACHOffsetAccounts)
  {
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      HashMap localHashMap = null;
      if (paramACHOffsetAccounts != null) {
        for (int i = 0; i < paramACHOffsetAccounts.size(); i++)
        {
          ACHOffsetAccount localACHOffsetAccount = (ACHOffsetAccount)paramACHOffsetAccounts.get(i);
          ACH.deleteOffsetAccount(localSecureUser, localACHOffsetAccount, this, localHashMap);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      str = this.serviceErrorURL;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.ModifyACHCompanyAndOffsetAccounts
 * JD-Core Version:    0.7.0.1
 */