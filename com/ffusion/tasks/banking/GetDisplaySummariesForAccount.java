package com.ffusion.tasks.banking;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.AccountSummary;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.accounts.AssetAcctSummary;
import com.ffusion.beans.accounts.CreditCardAcctSummary;
import com.ffusion.beans.accounts.DepositAcctSummary;
import com.ffusion.beans.accounts.LoanAcctSummary;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.fx.FXRateSheet;
import com.ffusion.beans.fx.FXRates;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.FX;
import com.ffusion.services.Banking3;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.DateFormatUtil;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetDisplaySummariesForAccount
  extends BaseTask
  implements Task
{
  private String xU = null;
  String xR = "BankingAccounts";
  String xQ = "AccountSummaries";
  String xT = "P";
  Date xW;
  String xP = "MM/dd/yyyy";
  boolean xS = false;
  boolean xV = false;
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public void setAllSummariesOnSameDate(String paramString)
  {
    this.xS = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAllSummariesOnSameDate()
  {
    return String.valueOf(this.xS);
  }
  
  public void setAccountsName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.xR = "BankingAccounts";
    } else {
      this.xR = paramString;
    }
  }
  
  public String getAccountsName()
  {
    return this.xR;
  }
  
  public void setSummariesName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.xQ = "AccountSummaries";
    } else {
      this.xQ = paramString;
    }
  }
  
  public String getSummariesName()
  {
    return this.xQ;
  }
  
  public void setDataClassification(String paramString)
  {
    this.xT = paramString;
  }
  
  public String getDataClassification()
  {
    return this.xT;
  }
  
  public void setDateFormat(String paramString)
  {
    if (!paramString.equals(this.xP)) {
      this.xP = paramString;
    }
  }
  
  public String getDateFormat()
  {
    return this.xP;
  }
  
  public void setDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.xP))) {
      this.xW = null;
    } else {
      try
      {
        this.xW = DateFormatUtil.getFormatter(this.xP).parse(paramString);
      }
      catch (ParseException localParseException)
      {
        this.xW = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.xP + ")");
      }
    }
  }
  
  public String getDate()
  {
    String str = null;
    if (this.xW != null) {
      str = DateFormatUtil.getFormatter(this.xP).format(this.xW);
    }
    return str;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.xR);
    AccountSummaries localAccountSummaries = (AccountSummaries)localHttpSession.getAttribute(this.xQ);
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    int i = 4;
    String str2 = Integer.toString(localSecureUser.getProfileID());
    Locale localLocale = BaseTask.getLocale(localHttpSession, localSecureUser);
    String str3 = localSecureUser.getBusinessName();
    if ((str3 != null) && (str3.trim().length() != 0)) {
      i = 3;
    }
    Object localObject1;
    Object localObject2;
    Object localObject3;
    if (i == 3)
    {
      localObject1 = (Business)localHttpSession.getAttribute("Business");
      try
      {
        EntitlementGroups localEntitlementGroups = Entitlements.getChildrenByGroupType(((Business)localObject1).getEntitlementGroupIdValue(), "Business");
        if (localEntitlementGroups.size() == 0)
        {
          str2 = ((Business)localObject1).getEntitlementGroupId();
        }
        else
        {
          localObject2 = localEntitlementGroups.iterator();
          if (((Iterator)localObject2).hasNext())
          {
            localObject3 = (EntitlementGroup)((Iterator)localObject2).next();
            str2 = Integer.toString(((EntitlementGroup)localObject3).getGroupId());
          }
        }
      }
      catch (CSILException localCSILException2)
      {
        str1 = this.serviceErrorURL;
      }
    }
    if (localAccounts == null) {
      this.error = 1001;
    } else if (localSecureUser == null) {
      this.error = 1037;
    } else {
      try
      {
        this.error = 0;
        localObject1 = new HashMap();
        ((HashMap)localObject1).put("DATA_CLASSIFICATION", this.xT);
        FXRateSheet localFXRateSheet = null;
        localObject2 = null;
        if ((this.xU != null) && (this.xU.trim().length() == 3)) {
          localObject2 = this.xU;
        } else {
          localObject2 = localSecureUser.getBaseCurrency();
        }
        localFXRateSheet = FX.getFXRateSheetForTarget(localSecureUser, (String)localObject2, i, str2, (HashMap)localObject1);
        localObject3 = new BigDecimal(1.0D);
        Iterator localIterator;
        Object localObject4;
        if (localAccountSummaries != null)
        {
          if (((this.xU == null) || (this.xU.trim().length() == 0)) && (localFXRateSheet != null)) {
            localAccountSummaries.setDisplayFXRates(localFXRateSheet.getRates());
          }
          localIterator = localAccountSummaries.iterator();
          while (localIterator.hasNext())
          {
            localObject4 = (AccountSummary)localIterator.next();
            localObject3 = new BigDecimal(1.0D);
            Object localObject5;
            if ((localObject4 instanceof DepositAcctSummary))
            {
              localObject5 = (DepositAcctSummary)localObject4;
              localObject3 = jdMethod_for(localFXRateSheet, ((DepositAcctSummary)localObject5).getCurrencyCode(null));
              ((DepositAcctSummary)localObject5).calculateDisplayBalances(this.xU, (BigDecimal)localObject3);
            }
            else if ((localObject4 instanceof LoanAcctSummary))
            {
              localObject5 = (LoanAcctSummary)localObject4;
              localObject3 = jdMethod_for(localFXRateSheet, ((LoanAcctSummary)localObject5).getCurrencyCode(null));
              ((LoanAcctSummary)localObject5).calculateDisplayBalances(this.xU, (BigDecimal)localObject3);
            }
            else if ((localObject4 instanceof CreditCardAcctSummary))
            {
              localObject5 = (CreditCardAcctSummary)localObject4;
              localObject3 = jdMethod_for(localFXRateSheet, ((CreditCardAcctSummary)localObject5).getCurrencyCode(null));
              ((CreditCardAcctSummary)localObject5).calculateDisplayBalances(this.xU, (BigDecimal)localObject3);
            }
            else if ((localObject4 instanceof AssetAcctSummary))
            {
              localObject5 = (AssetAcctSummary)localObject4;
              localObject3 = jdMethod_for(localFXRateSheet, ((AssetAcctSummary)localObject5).getCurrencyCode(null));
              ((AssetAcctSummary)localObject5).calculateDisplayBalances(this.xU, (BigDecimal)localObject3);
            }
          }
        }
        if (localAccounts != null)
        {
          localAccounts.setDisplayCurrency(this.xU);
          if (((this.xU == null) || (this.xU.trim().length() == 0)) && (localFXRateSheet != null)) {
            localAccounts.setDisplayFXRates(localFXRateSheet.getRates());
          }
          localIterator = localAccounts.iterator();
          while (localIterator.hasNext())
          {
            localObject4 = (Account)localIterator.next();
            localObject3 = jdMethod_for(localFXRateSheet, ((Account)localObject4).getCurrencyCode());
            ((Account)localObject4).calculateDisplayBalances(this.xU, (BigDecimal)localObject3);
          }
        }
        str1 = this.successURL;
      }
      catch (CSILException localCSILException1)
      {
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  public String getDisplayCurrency()
  {
    return this.xU;
  }
  
  public void setDisplayCurrency(String paramString)
  {
    this.xU = paramString;
  }
  
  private BigDecimal jdMethod_for(FXRateSheet paramFXRateSheet, String paramString)
  {
    BigDecimal localBigDecimal = new BigDecimal(1.0D);
    if ((this.xU != null) && (!this.xU.equals(paramString))) {
      if ((paramFXRateSheet == null) || (paramFXRateSheet.getRates() == null))
      {
        localBigDecimal = null;
      }
      else
      {
        FXRate localFXRate = paramFXRateSheet.getRates().getByCurrencyCode(paramString, this.xU);
        if (localFXRate != null) {
          localBigDecimal = localFXRate.getBuyPrice().getAmountValue();
        } else {
          localBigDecimal = null;
        }
      }
    }
    return localBigDecimal;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetDisplaySummariesForAccount
 * JD-Core Version:    0.7.0.1
 */