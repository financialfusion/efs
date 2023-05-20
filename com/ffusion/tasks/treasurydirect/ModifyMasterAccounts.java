package com.ffusion.tasks.treasurydirect;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyMasterAccounts
  extends BaseTask
  implements TreasuryDirectTask
{
  private String aOz = "MasterAccountAddCollection";
  private String aOx = "ListAccountsForMasterAccountAdd";
  private int aOy = -1;
  protected String _validate = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str2 = "ModifyMasterAccounts.process";
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = null;
    com.ffusion.beans.accounts.Accounts localAccounts1 = null;
    com.ffusion.beans.accounts.Accounts localAccounts2 = null;
    this.error = 0;
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localAccounts1 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.aOz);
    localAccounts2 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.aOx);
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (this.aOy <= 0)
    {
      this.error = 80104;
      return this.taskErrorURL;
    }
    if (localAccounts1 == null)
    {
      this.error = 39;
      return this.taskErrorURL;
    }
    if (localAccounts2 == null)
    {
      this.error = 39;
      return this.taskErrorURL;
    }
    if ((this._validate != null) && (this._validate.trim().length() > 0) && (!validateInput(localHttpSession))) {
      return this.taskErrorURL;
    }
    Iterator localIterator = localAccounts1.iterator();
    while (localIterator.hasNext())
    {
      Account localAccount1 = (Account)localIterator.next();
      String str3 = localAccount1.getID();
      String str4 = localAccount1.getRoutingNum();
      Account localAccount2 = localAccounts2.getByIDAndRoutingNum(str3, str4);
      try
      {
        localAccount1 = com.ffusion.csil.core.Accounts.modifyAccountById(localSecureUser, this.aOy, localAccount1, localHashMap);
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localAccount1.getRoutingNum();
        try
        {
          arrayOfObject[1] = AccountUtil.buildLocalizableAccountID(localAccount1.getID());
        }
        catch (UtilException localUtilException)
        {
          DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + localAccount1.getID());
          localUtilException.printStackTrace();
          arrayOfObject[1] = localAccount1.getID();
        }
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "MODIFY_ACCOUNT", arrayOfObject);
        HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, Integer.toString(this.aOy));
        localAccount1.logChanges(localHistoryTracker, localAccount2, localLocalizableString);
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for " + str2 + ": " + localProfileException.toString());
          DebugLog.throwing(str2, localProfileException);
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        return this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  public String getAccountCollectionName()
  {
    return this.aOz;
  }
  
  public void setAccountCollectionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aOz = "MasterAccountAddCollection";
    } else {
      this.aOz = paramString.trim();
    }
  }
  
  public String getOriginalAccountCollectionName()
  {
    return this.aOx;
  }
  
  public void setOriginalAccountCollectionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aOx = "ListAccountsForMasterAccountAdd";
    } else {
      this.aOx = paramString.trim();
    }
  }
  
  public int getDirectoryID()
  {
    return this.aOy;
  }
  
  public void setDirectoryID(String paramString)
  {
    this.aOy = Integer.parseInt(paramString);
  }
  
  public void setValidate(String paramString)
  {
    this._validate = null;
    if (!paramString.equalsIgnoreCase("")) {
      this._validate = paramString.toUpperCase();
    }
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.treasurydirect.ModifyMasterAccounts
 * JD-Core Version:    0.7.0.1
 */