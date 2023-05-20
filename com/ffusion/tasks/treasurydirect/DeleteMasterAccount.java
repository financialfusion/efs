package com.ffusion.tasks.treasurydirect;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
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
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteMasterAccount
  extends BaseTask
  implements TreasuryDirectTask
{
  private String aOE = null;
  private String aOF = null;
  private String aOG = "Business";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = null;
    Account localAccount = null;
    Business localBusiness = null;
    this.error = 0;
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localBusiness = (Business)localHttpSession.getAttribute(this.aOG);
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (this.aOE == null)
    {
      this.error = 80101;
      return this.taskErrorURL;
    }
    if (localBusiness == null)
    {
      this.error = 74;
      return this.taskErrorURL;
    }
    localAccount = new Account();
    localAccount.setID(this.aOE);
    if (this.aOF != null) {
      localAccount.setRoutingNum(this.aOF);
    }
    try
    {
      TreasuryDirect.deleteMasterAccount(localSecureUser, localBusiness, localAccount, localHashMap);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = localAccount.getRoutingNum();
      try
      {
        arrayOfObject[1] = AccountUtil.buildLocalizableAccountID(localAccount.getID());
      }
      catch (UtilException localUtilException)
      {
        DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + localAccount.getID());
        localUtilException.printStackTrace();
        arrayOfObject[1] = localAccount.getID();
      }
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "MODIFY_ACCOUNT", arrayOfObject);
      HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, localBusiness.getId());
      int i = this.aOE.lastIndexOf('-');
      String str2 = this.aOE.substring(0, i);
      localHistoryTracker.logChange(Account.class.getName(), "ISMASTER", ResourceUtil.getString("IsMaster_True", "com.ffusion.beans.accounts.resources", localSecureUser.getLocale()), ResourceUtil.getString("IsMaster_False", "com.ffusion.beans.accounts.resources", localSecureUser.getLocale()), localLocalizableString);
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for DeleteMasterAccount: " + localProfileException.toString());
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  public String getBusinessSessionName()
  {
    return this.aOG;
  }
  
  public void setBusinessSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aOG = "Business";
    } else {
      this.aOG = paramString.trim();
    }
  }
  
  public String getID()
  {
    return this.aOE;
  }
  
  public void setID(String paramString)
  {
    this.aOE = paramString;
  }
  
  public String getRoutingNum()
  {
    return this.aOF;
  }
  
  public void setRoutingNum(String paramString)
  {
    this.aOF = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.treasurydirect.DeleteMasterAccount
 * JD-Core Version:    0.7.0.1
 */