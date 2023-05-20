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
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddAccountBC
  extends BaseTask
  implements Task
{
  private String m2;
  private String m3 = "BackendAccounts";
  private String mX = "Accounts";
  private String m0;
  private String mW;
  private String mY;
  private int m1;
  private String mZ = null;
  protected boolean _autoEntitleAccounts = true;
  protected boolean _enrollingBusiness = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    try
    {
      Business localBusiness = (Business)localHttpSession.getAttribute(this.m2);
      if (localBusiness == null)
      {
        this.error = 4104;
        return this.taskErrorURL;
      }
      com.ffusion.beans.accounts.Accounts localAccounts1 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.m3);
      if (localAccounts1 == null)
      {
        this.error = 18006;
        return this.taskErrorURL;
      }
      Account localAccount = localAccounts1.getByIDAndBankIDAndRoutingNum(this.m0, this.mY, this.mW);
      if (localAccount == null)
      {
        this.error = 18004;
        return this.taskErrorURL;
      }
      if (localAccount.getZBAFlag() == null) {
        localAccount.setZBAFlag("B");
      }
      if (localAccount.getShowPreviousDayOpeningLedger() == null) {
        localAccount.setShowPreviousDayOpeningLedger("Y");
      }
      if (localBusiness.getStatusValue() == 1)
      {
        localObject = new Contact();
        ((Contact)localObject).set(localBusiness);
        ((Contact)localObject).clear();
        localAccount.setContact((Contact)localObject);
      }
      Object localObject = new com.ffusion.beans.accounts.Accounts();
      ((com.ffusion.beans.accounts.Accounts)localObject).add(localAccount);
      com.ffusion.csil.core.Accounts.addAccounts(localSecureUser, (com.ffusion.beans.accounts.Accounts)localObject, this._autoEntitleAccounts, this._enrollingBusiness, this.m1, localHashMap);
      com.ffusion.beans.accounts.Accounts localAccounts2 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.mX);
      if (localAccounts2 != null) {
        localAccounts2.add(localAccount);
      }
      if (!this._enrollingBusiness)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localAccount.getRoutingNum();
        arrayOfObject[1] = localAccount.buildLocalizableAccountID();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "ADD_CORE_ACCOUNT", arrayOfObject);
        HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, Integer.toString(this.m1));
        localHistoryTracker.logChange(Business.BEAN_NAME, Business.ACCOUNTS, null, localAccount.getNumber(), localLocalizableString);
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for AddAccountBC: " + localProfileException.toString());
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setDirectoryId(String paramString)
  {
    try
    {
      this.m1 = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setAccountId(String paramString)
  {
    this.m0 = paramString;
  }
  
  public void setRoutingNum(String paramString)
  {
    this.mW = paramString;
  }
  
  public void setBankId(String paramString)
  {
    this.mY = paramString;
  }
  
  public void setBackendAccountKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.m3 = paramString;
    }
  }
  
  public String getBackendAccountKey()
  {
    return this.m3;
  }
  
  public void setProfileAccountKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.mX = paramString;
    }
  }
  
  public String getProfileAccountKey()
  {
    return this.mX;
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
  
  public String getBusinessSessionName()
  {
    return this.m2;
  }
  
  public void setBusinessSessionName(String paramString)
  {
    this.m2 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.AddAccountBC
 * JD-Core Version:    0.7.0.1
 */