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
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.treasurydirect.TreasuryDirectUtil;
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

public class AddSubAccounts
  extends BaseTask
  implements TreasuryDirectTask
{
  protected String _subAccountsCollectionName = "SubAccountAddCollection";
  protected String _existingSubAccountsCollectionName = "ListSubAccountsForMasterAccountEdit";
  protected String _masterID = null;
  protected String _routingNum = null;
  protected String _businessSessionName = "Business";
  protected boolean _validate = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = null;
    Accounts localAccounts1 = null;
    Accounts localAccounts2 = null;
    Account localAccount1 = null;
    Business localBusiness = null;
    this.error = 0;
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localAccounts1 = (Accounts)localHttpSession.getAttribute(this._subAccountsCollectionName);
    if (this._validate) {
      localAccounts2 = (Accounts)localHttpSession.getAttribute(this._existingSubAccountsCollectionName);
    }
    localBusiness = (Business)localHttpSession.getAttribute(this._businessSessionName);
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (this._masterID == null)
    {
      this.error = 80101;
      return this.taskErrorURL;
    }
    if ((localAccounts1 == null) || ((this._validate) && (localAccounts2 == null)))
    {
      this.error = 80102;
      return this.taskErrorURL;
    }
    if (localBusiness == null)
    {
      this.error = 74;
      return this.taskErrorURL;
    }
    if ((this._validate) && (!validateInput(localAccounts1, localAccounts2))) {
      return this.taskErrorURL;
    }
    localAccount1 = new Account();
    localAccount1.setID(this._masterID);
    if (this._routingNum != null) {
      localAccount1.setRoutingNum(this._routingNum);
    }
    try
    {
      TreasuryDirect.addSubAccounts(localSecureUser, localBusiness, localAccount1, localAccounts1, localHashMap);
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[2] = this._routingNum;
      try
      {
        arrayOfObject[3] = AccountUtil.buildLocalizableAccountID(this._masterID);
      }
      catch (UtilException localUtilException1)
      {
        DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + this._masterID);
        localUtilException1.printStackTrace();
        arrayOfObject[3] = this._masterID;
      }
      for (int i = 0; i < localAccounts1.size(); i++)
      {
        Account localAccount2 = (Account)localAccounts1.get(i);
        arrayOfObject[0] = localAccount2.getRoutingNum();
        try
        {
          arrayOfObject[1] = AccountUtil.buildLocalizableAccountID(localAccount2.getID());
        }
        catch (UtilException localUtilException2)
        {
          DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + localAccount2.getID());
          localUtilException2.printStackTrace();
          arrayOfObject[1] = localAccount2.getID();
        }
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "ADD_SUB_ACCOUNT", arrayOfObject);
        HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, localBusiness.getId());
        localHistoryTracker.logChange(Business.BEAN_NAME, Business.ACCOUNTS, null, localAccount2.getNumber(), localLocalizableString);
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for AddSubAccounts: " + localProfileException.toString());
          DebugLog.throwing("AddSubAccounts", localProfileException);
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
  
  public String getSubAccountCollectionName()
  {
    return this._subAccountsCollectionName;
  }
  
  public void setSubAccountCollectionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this._subAccountsCollectionName = "SubAccountAddCollection";
    } else {
      this._subAccountsCollectionName = paramString.trim();
    }
  }
  
  public String getBusinessSessionName()
  {
    return this._businessSessionName;
  }
  
  public void setBusinessSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this._businessSessionName = "Business";
    } else {
      this._businessSessionName = paramString.trim();
    }
  }
  
  public String getID()
  {
    return this._masterID;
  }
  
  public void setID(String paramString)
  {
    this._masterID = paramString;
  }
  
  public String getRoutingNum()
  {
    return this._routingNum;
  }
  
  public void setRoutingNum(String paramString)
  {
    this._routingNum = paramString;
  }
  
  public void setValidate(String paramString)
  {
    this._validate = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getExistingSubAccountsCollectionName()
  {
    return this._existingSubAccountsCollectionName;
  }
  
  public void setExistingSubAccountsCollectionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this._existingSubAccountsCollectionName = "ListSubAccountsForMasterAccountEdit";
    } else {
      this._existingSubAccountsCollectionName = paramString.trim();
    }
  }
  
  protected boolean validateInput(Accounts paramAccounts1, Accounts paramAccounts2)
  {
    boolean bool = true;
    Account localAccount = null;
    Iterator localIterator = paramAccounts1.iterator();
    while (localIterator.hasNext())
    {
      localAccount = (Account)localIterator.next();
      if ((localAccount.getLocationID() == null) || (localAccount.getLocationID().trim().length() == 0))
      {
        bool = false;
        this.error = 80110;
      }
      else
      {
        bool = (bool) && (TreasuryDirectUtil.validateLocationID(localAccount.getLocationID()));
        if (!bool) {
          this.error = 80103;
        }
      }
    }
    if (bool)
    {
      bool = jdMethod_for(paramAccounts1, paramAccounts2);
      if (!bool) {
        this.error = 80111;
      }
    }
    return bool;
  }
  
  private boolean jdMethod_for(Accounts paramAccounts1, Accounts paramAccounts2)
  {
    boolean bool = true;
    Accounts localAccounts = new Accounts(paramAccounts2.getLocale());
    Account localAccount = null;
    Iterator localIterator = paramAccounts1.iterator();
    while (localIterator.hasNext())
    {
      localAccount = (Account)localIterator.next();
      localAccounts.add(localAccount);
    }
    localIterator = paramAccounts2.iterator();
    while (localIterator.hasNext())
    {
      localAccount = (Account)localIterator.next();
      localAccounts.add(localAccount);
    }
    bool = TreasuryDirectUtil.hasUniqueLocationIds(localAccounts);
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.treasurydirect.AddSubAccounts
 * JD-Core Version:    0.7.0.1
 */