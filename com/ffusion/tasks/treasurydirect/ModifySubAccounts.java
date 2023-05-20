package com.ffusion.tasks.treasurydirect;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.TreasuryDirect;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
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

public class ModifySubAccounts
  extends AddSubAccounts
  implements TreasuryDirectTask
{
  public ModifySubAccounts()
  {
    this._validate = false;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str2 = "ModifySubAccounts.process";
    this.error = 0;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = null;
    Accounts localAccounts1 = null;
    Accounts localAccounts2 = null;
    Account localAccount1 = null;
    Business localBusiness = null;
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localAccounts2 = (Accounts)localHttpSession.getAttribute(super.getSubAccountCollectionName());
    localBusiness = (Business)localHttpSession.getAttribute(super.getBusinessSessionName());
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (localAccounts2 == null)
    {
      this.error = 80102;
      return this.taskErrorURL;
    }
    if (this._masterID == null)
    {
      this.error = 80101;
      return this.taskErrorURL;
    }
    if (localBusiness == null)
    {
      this.error = 74;
      return this.taskErrorURL;
    }
    if ((this._validate) && (!super.validateInput(localAccounts2, new Accounts(localSecureUser.getLocale())))) {
      return this.taskErrorURL;
    }
    localAccount1 = new Account();
    localAccount1.setID(this._masterID);
    if (this._routingNum != null) {
      localAccount1.setRoutingNum(this._routingNum);
    }
    try
    {
      localAccounts1 = TreasuryDirect.getSubAccounts(localSecureUser, localBusiness, localAccount1, localHashMap);
      TreasuryDirect.modifySubAccounts(localSecureUser, localBusiness, localAccount1, localAccounts2, localHashMap);
      Iterator localIterator = localAccounts2.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount3 = (Account)localIterator.next();
        String str3 = localAccount3.getID();
        String str4 = localAccount3.getRoutingNum();
        Account localAccount2 = localAccounts1.getByIDAndRoutingNum(str3, str4);
        localAccount2.setContactId(-1);
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localAccount3.getRoutingNum();
        try
        {
          arrayOfObject[1] = AccountUtil.buildLocalizableAccountID(localAccount3.getID());
        }
        catch (UtilException localUtilException)
        {
          DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + localAccount3.getID());
          localUtilException.printStackTrace();
          arrayOfObject[1] = localAccount3.getID();
        }
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "MODIFY_SUB_ACCOUNT", arrayOfObject);
        HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, localBusiness.getId());
        localAccount3.logChanges(localHistoryTracker, localAccount2, localLocalizableString);
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
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.treasurydirect.ModifySubAccounts
 * JD-Core Version:    0.7.0.1
 */