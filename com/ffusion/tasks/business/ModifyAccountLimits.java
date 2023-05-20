package com.ffusion.tasks.business;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.util.LastRequest;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyAccountLimits
  implements BusinessTask
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected int error;
  protected String accountsName;
  protected String groupId;
  protected String operationName;
  protected String objectType;
  protected int period;
  public static final String ACCOUNTPREFIX = "Acct_";
  public static final String ACCOUNTNUMBERSEPARATOR = "-";
  public static final String ACCOUNTLIMITSUFFIX = "_Limit";
  private static final String gK = "com.ffusion.util.logging.audit.task";
  private static final String gJ = "AuditMessage_ModifyAccountLimits_1";
  private static final String gI = "AuditMessage_ModifyAccountLimits_2";
  private static final String gH = "AuditMessage_ModifyAccountLimits_3";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localHttpSession.setAttribute("LastRequest", new LastRequest(paramHttpServletRequest));
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.accountsName);
    if (localAccounts == null)
    {
      this.error = 39;
      str1 = this.taskErrorURL;
      return str1;
    }
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    Iterator localIterator1 = localAccounts.iterator();
    while ((localIterator1.hasNext()) && (this.error == 0))
    {
      Account localAccount = (Account)localIterator1.next();
      String str2 = (String)localHttpSession.getAttribute("Acct_" + localAccount.getBankID() + "-" + localAccount.getRoutingNum() + "-" + localAccount.getNumber() + "-" + localAccount.getTypeValue() + "_Limit");
      if (!Currency.isValid(str2, localLocale))
      {
        this.error = 4136;
        return this.taskErrorURL;
      }
      try
      {
        Entitlement localEntitlement = new Entitlement(this.operationName, this.objectType, EntitlementsUtil.getEntitlementObjectId(localAccount));
        Limit localLimit1 = new Limit();
        localLimit1.setEntitlement(localEntitlement);
        localLimit1.setPeriod(this.period);
        Limits localLimits = Entitlements.getGroupLimits(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), Integer.parseInt(this.groupId), localLimit1, null);
        Iterator localIterator2 = localLimits.iterator();
        Object[] arrayOfObject = null;
        LocalizableString localLocalizableString = null;
        Limit localLimit2 = null;
        if (localIterator2.hasNext())
        {
          localLimit2 = (Limit)localIterator2.next();
          if ((str2 == null) || (str2.length() <= 0))
          {
            Entitlements.deleteGroupLimit(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localLimit2);
            arrayOfObject = new Object[1];
            arrayOfObject[0] = localAccount.buildLocalizableAccountID();
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyAccountLimits_1", arrayOfObject);
          }
          else
          {
            localLimit2.setData(str2);
            localLimit2 = Entitlements.modifyGroupLimit(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localLimit2);
            arrayOfObject = new Object[1];
            arrayOfObject[0] = localAccount.buildLocalizableAccountID();
            arrayOfObject[1] = str2;
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyAccountLimits_2", arrayOfObject);
          }
        }
        else if ((str2 != null) && (str2.length() > 0))
        {
          localLimit2 = localLimit1;
          localLimit2.setLimitName(null);
          localLimit2.setGroupId(Integer.parseInt(this.groupId));
          localLimit2.setData(str2);
          localLimit2 = Entitlements.addGroupLimit(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localLimit2);
          arrayOfObject = new Object[1];
          arrayOfObject[0] = localAccount.buildLocalizableAccountID();
          arrayOfObject[1] = str2;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyAccountLimits_3", arrayOfObject);
        }
        if (localLocalizableString != null)
        {
          String str3 = TrackingIDGenerator.GetNextID();
          try
          {
            Currency localCurrency = new Currency(str2, localLocale, localLocale.getISO3Country());
            Initialize.audit(localSecureUser, localLocalizableString, str3, 3225, localCurrency);
          }
          catch (Exception localException)
          {
            Initialize.audit(localSecureUser, localLocalizableString, str3, 3225);
          }
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public int getErrorValue()
  {
    return this.error;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this.groupId = paramString;
  }
  
  public void setOperationName(String paramString)
  {
    this.operationName = paramString;
  }
  
  public void setObjectType(String paramString)
  {
    this.objectType = paramString;
  }
  
  public void setPeriod(String paramString)
  {
    try
    {
      this.period = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.ModifyAccountLimits
 * JD-Core Version:    0.7.0.1
 */