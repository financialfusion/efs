package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyAccountNames
  implements BusinessTask
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected int error;
  protected String employeeName;
  protected String accountsName;
  public static final String ACCOUNTPREFIX = "Acct_";
  public static final String ACCOUNTNUMBERSEPARATOR = "-";
  public static final String ACCOUNTNICKNAMESUFFIX = "_Nick";
  private static final String hu = "com.ffusion.util.logging.audit.task";
  private static final String ht = "AuditMessage_ModifyAccountNames_1";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BusinessEmployee localBusinessEmployee = (BusinessEmployee)localHttpSession.getAttribute(this.employeeName);
    if (localBusinessEmployee == null)
    {
      this.error = 4126;
      str1 = this.taskErrorURL;
      return str1;
    }
    com.ffusion.beans.accounts.Accounts localAccounts = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.accountsName);
    if (localAccounts == null)
    {
      this.error = 39;
      str1 = this.taskErrorURL;
      return str1;
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, localSecureUser.getBusinessID() + "");
    Iterator localIterator = localAccounts.iterator();
    while ((localIterator.hasNext()) && (this.error == 0))
    {
      Account localAccount = (Account)localIterator.next();
      String str2 = (String)localHttpSession.getAttribute("Acct_" + localAccount.getBankID() + "-" + localAccount.getRoutingNum() + "-" + localAccount.getID() + "_Nick");
      if ((str2 != null) && (str2.length() > 40))
      {
        this.error = 34;
        return this.taskErrorURL;
      }
      String str3 = localAccount.getNickName();
      if (str2.compareTo(str3) != 0)
      {
        localAccount.setNickName(str2);
        Object[] arrayOfObject = new Object[5];
        arrayOfObject[0] = str2;
        arrayOfObject[1] = localAccount.getRoutingNum();
        arrayOfObject[2] = localAccount.buildLocalizableAccountID();
        arrayOfObject[3] = str3;
        arrayOfObject[4] = localAccount.getCurrencyCode();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyAccountNames_1", arrayOfObject);
        try
        {
          localAccount = com.ffusion.csil.core.Accounts.modifyAccountById(localSecureUser, localBusinessEmployee.getBusinessId(), localAccount, null);
          String str4 = TrackingIDGenerator.GetNextID();
          Initialize.audit(localSecureUser, localLocalizableString, str4, 3209);
          localHistoryTracker.logChange(localHistoryTracker.lookupField(Business.BEAN_NAME, Business.ACCOUNT_NICKNAME), str3, str2, null);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str1 = this.serviceErrorURL;
        }
      }
    }
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for SetAdministrators: " + localProfileException.toString());
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
  
  public void setEmployeeName(String paramString)
  {
    this.employeeName = paramString;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.ModifyAccountNames
 * JD-Core Version:    0.7.0.1
 */