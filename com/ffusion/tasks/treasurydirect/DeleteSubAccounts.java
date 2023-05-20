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
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteSubAccounts
  extends BaseTask
  implements TreasuryDirectTask
{
  private String aO0 = "ListSubAccountsForMasterAccountEdit";
  private String aOY = null;
  private String aOV = null;
  private String aOZ = null;
  private String aOX = null;
  private String aOU = "Business";
  private Accounts aO2 = new Accounts();
  private Accounts aOW = null;
  private boolean aO1 = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.aO1)
    {
      this.aOW = ((Accounts)localHttpSession.getAttribute(this.aO0));
      if (this.aOW == null)
      {
        this.error = 80102;
        return this.taskErrorURL;
      }
      this.aO1 = false;
      str = this.successURL;
    }
    else
    {
      SecureUser localSecureUser = null;
      Account localAccount1 = null;
      Business localBusiness = null;
      HashMap localHashMap = new HashMap();
      localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localBusiness = (Business)localHttpSession.getAttribute(this.aOU);
      if (localSecureUser == null)
      {
        this.error = 38;
        return this.taskErrorURL;
      }
      if (this.aOY == null)
      {
        this.error = 80101;
        return this.taskErrorURL;
      }
      if (localBusiness == null)
      {
        this.error = 74;
        return this.taskErrorURL;
      }
      if (this.aO2 == null)
      {
        this.error = 80105;
        return this.taskErrorURL;
      }
      localAccount1 = new Account();
      localAccount1.setID(this.aOY);
      if (this.aOV != null) {
        localAccount1.setRoutingNum(this.aOV);
      }
      try
      {
        TreasuryDirect.deleteSubAccounts(localSecureUser, localBusiness, localAccount1, this.aO2, localHashMap);
        for (int i = 0; i < this.aO2.size(); i++)
        {
          Account localAccount2 = (Account)this.aO2.get(i);
          this.aOW.removeByID(localAccount2.getID());
        }
        localHttpSession.setAttribute(this.aO0, this.aOW);
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[2] = this.aOV;
        try
        {
          arrayOfObject[3] = AccountUtil.buildLocalizableAccountID(this.aOY);
        }
        catch (UtilException localUtilException1)
        {
          DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + this.aOY);
          localUtilException1.printStackTrace();
          arrayOfObject[3] = this.aOY;
        }
        for (int j = 0; j < this.aO2.size(); j++)
        {
          Account localAccount3 = (Account)this.aO2.get(j);
          arrayOfObject[0] = localAccount3.getRoutingNum();
          try
          {
            arrayOfObject[1] = AccountUtil.buildLocalizableAccountID(localAccount3.getID());
          }
          catch (UtilException localUtilException2)
          {
            DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + localAccount3.getID());
            localUtilException2.printStackTrace();
            arrayOfObject[1] = localAccount3.getID();
          }
          LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "DELETE_SUB_ACCOUNT", arrayOfObject);
          HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, localBusiness.getId());
          localHistoryTracker.logChange(Business.BEAN_NAME, Business.ACCOUNTS, localAccount3.getNumber(), null, localLocalizableString);
          try
          {
            HistoryAdapter.addHistory(localHistoryTracker.getHistories());
          }
          catch (ProfileException localProfileException)
          {
            DebugLog.log(Level.SEVERE, "Add History failed for DeleteMasterAccount: " + localProfileException.toString());
          }
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public String getSubAccountCollectionName()
  {
    return this.aO0;
  }
  
  public void setSubAccountCollectionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aO0 = "ListSubAccountsForMasterAccountEdit";
    } else {
      this.aO0 = paramString.trim();
    }
  }
  
  public String getBusinessSessionName()
  {
    return this.aOU;
  }
  
  public void setBusinessSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aOU = "Business";
    } else {
      this.aOU = paramString.trim();
    }
  }
  
  public String getMasterID()
  {
    return this.aOY;
  }
  
  public void setMasterID(String paramString)
  {
    this.aOY = paramString;
  }
  
  public String getMasterRoutingNum()
  {
    return this.aOV;
  }
  
  public void setMasterRoutingNum(String paramString)
  {
    this.aOV = paramString;
  }
  
  public String getAddSubID()
  {
    return this.aOZ;
  }
  
  public void setAddSubID(String paramString)
  {
    this.aOZ = paramString;
  }
  
  public String getAddSubRoutingNum()
  {
    return this.aOX;
  }
  
  public void setAddSubRoutingNum(String paramString)
  {
    this.aOX = paramString;
  }
  
  public void setAddSubAccount(String paramString)
  {
    Account localAccount1 = this.aOW.getByIDAndRoutingNum(this.aOZ, this.aOX);
    if (localAccount1 != null)
    {
      Account localAccount2 = new Account();
      localAccount2.set(localAccount1);
      this.aO2.add(localAccount2);
    }
  }
  
  public void setClearDeleteAccountCollection(String paramString)
  {
    this.aO2 = new Accounts();
  }
  
  public void setInit(String paramString)
  {
    this.aO1 = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.treasurydirect.DeleteSubAccounts
 * JD-Core Version:    0.7.0.1
 */