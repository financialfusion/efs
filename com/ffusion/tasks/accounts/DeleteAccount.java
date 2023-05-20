package com.ffusion.tasks.accounts;

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

public class DeleteAccount
  extends BaseTask
  implements Task
{
  private int nn;
  private String nq;
  private String nm = null;
  private String nl = null;
  private String no = null;
  private int nj;
  private String nk = "Accounts";
  private boolean np = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Account localAccount = new Account();
    localAccount.setID(this.nq);
    if (this.nm != null) {
      localAccount.setCoreAccount(this.nm);
    }
    if (this.nl != null) {
      localAccount.setRoutingNum(this.nl);
    }
    if (this.no != null) {
      localAccount.setNumber(this.no);
    }
    localAccount.setType(this.nj);
    try
    {
      Boolean localBoolean = new Boolean(this.np);
      localHashMap.put("IsEnrollingBusinessFlag", localBoolean);
      com.ffusion.csil.core.Accounts.deleteAccount(localSecureUser, localAccount, this.nn, localHashMap);
      if (!this.np)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localAccount.getRoutingNum();
        arrayOfObject[1] = localAccount.buildLocalizableAccountID();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "DELETE_ACCOUNT", arrayOfObject);
        HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, Integer.toString(this.nn));
        localHistoryTracker.logChange(Business.BEAN_NAME, Business.ACCOUNTS, localAccount.getNumber(), null, localLocalizableString);
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for DeleteAccount: " + localProfileException.toString());
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      if ((this.nq != null) && (this.nq.length() > 0))
      {
        com.ffusion.beans.accounts.Accounts localAccounts = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.nk);
        if (localAccounts != null)
        {
          localAccounts.removeByID(this.nq);
        }
        else
        {
          this.error = 18006;
          return this.taskErrorURL;
        }
      }
      else
      {
        localHttpSession.removeAttribute(this.nk);
      }
    }
    return str;
  }
  
  public void setAccountId(String paramString)
  {
    this.nq = paramString;
  }
  
  public void setDirectoryId(String paramString)
  {
    try
    {
      this.nn = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setSourceSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.nk = paramString;
    }
  }
  
  public String getSourceSessionKey()
  {
    return this.nk;
  }
  
  public String getCoreAccount()
  {
    return this.nm;
  }
  
  public void setCoreAccount(String paramString)
  {
    this.nm = paramString;
  }
  
  public String getRoutingNum()
  {
    return this.nl;
  }
  
  public void setRoutingNum(String paramString)
  {
    this.nl = paramString;
  }
  
  public String getAccountNum()
  {
    return this.no;
  }
  
  public void setAccountNum(String paramString)
  {
    this.no = paramString;
  }
  
  public int getTypeNum()
  {
    return this.nj;
  }
  
  public void setTypeNum(String paramString)
  {
    this.nj = Integer.parseInt(paramString);
  }
  
  public void setEnrollingBusiness(String paramString)
  {
    this.np = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEnrollingBusiness()
  {
    return String.valueOf(this.np);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.DeleteAccount
 * JD-Core Version:    0.7.0.1
 */