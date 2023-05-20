package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Accounts;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SaveRestrictedCalculations
  extends BaseTask
  implements Task
{
  private String j7 = null;
  private String j5 = null;
  private String j4 = null;
  private String j1 = null;
  private String j6;
  private String j3 = "RestrictedCalculations";
  private String j2 = "NewRestrictedCalculations";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.j1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.j7 == null)
    {
      this.error = 18009;
      this.j1 = this.taskErrorURL;
    }
    else if (this.j5 == null)
    {
      this.error = 18010;
      this.j1 = this.taskErrorURL;
    }
    else if (this.j4 == null)
    {
      this.error = 18011;
      this.j1 = this.taskErrorURL;
    }
    else
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      HashMap localHashMap = new HashMap();
      Business localBusiness = (Business)localHttpSession.getAttribute("ModifyBusiness");
      try
      {
        Account localAccount = new Account();
        localAccount.setID(this.j7);
        localAccount.setBankID(this.j5);
        localAccount.setRoutingNum(this.j4);
        ArrayList localArrayList1 = (ArrayList)localHttpSession.getAttribute(this.j2);
        Accounts.saveRestrictedCalculations(localSecureUser, localAccount, localArrayList1, localHashMap, localBusiness);
        ArrayList localArrayList2 = (ArrayList)localHttpSession.getAttribute(this.j3);
        HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, this.j6);
        StringBuffer localStringBuffer1 = new StringBuffer("Account ");
        localStringBuffer1.append(this.j4);
        localStringBuffer1.append(":");
        try
        {
          localStringBuffer1.append(AccountUtil.buildAccountDisplayText(this.j7, localSecureUser.getLocale()));
        }
        catch (UtilException localUtilException)
        {
          DebugLog.throwing("Error while constructing account display string.", localUtilException);
          localStringBuffer1.append(this.j7);
        }
        StringBuffer localStringBuffer2 = new StringBuffer(localStringBuffer1.toString());
        localStringBuffer2.append(" restricted.");
        StringBuffer localStringBuffer3 = new StringBuffer(localStringBuffer1.toString());
        localStringBuffer3.append(" not restricted.");
        ArrayList localArrayList3 = new ArrayList(localArrayList1);
        Iterator localIterator = localArrayList2.iterator();
        while (localIterator.hasNext())
        {
          localObject = (String)localIterator.next();
          int i = localArrayList3.indexOf(localObject);
          if (i == -1) {
            try
            {
              String str2 = ResourceUtil.getString((String)localObject, "com.ffusion.beans.accounts.resources", Locale.getDefault());
              localHistoryTracker.logChange(localHistoryTracker.lookupField(Business.BEAN_NAME, Business.ACCOUNT_CALCULATIONS), null, str2, localStringBuffer3.toString());
            }
            catch (Exception localException1)
            {
              localStringBuffer1 = new StringBuffer("Unable to lookup ");
              localStringBuffer1.append((String)localObject);
              localStringBuffer1.append(" in the resource bundle ");
              localStringBuffer1.append("com.ffusion.beans.accounts.resources");
              DebugLog.log(Level.SEVERE, localStringBuffer1.toString());
            }
          } else {
            localArrayList3.remove(i);
          }
        }
        Object localObject = localArrayList3.iterator();
        while (((Iterator)localObject).hasNext())
        {
          String str1 = (String)((Iterator)localObject).next();
          try
          {
            String str3 = ResourceUtil.getString(str1, "com.ffusion.beans.accounts.resources", Locale.getDefault());
            localHistoryTracker.logChange(localHistoryTracker.lookupField(Business.BEAN_NAME, Business.ACCOUNT_CALCULATIONS), null, str3, localStringBuffer2.toString());
          }
          catch (Exception localException2)
          {
            localStringBuffer1 = new StringBuffer("Unable to lookup ");
            localStringBuffer1.append(str1);
            localStringBuffer1.append(" in the resource bundle ");
            localStringBuffer1.append("com.ffusion.beans.accounts.resources");
            DebugLog.log(Level.SEVERE, localStringBuffer1.toString());
          }
        }
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for SaveRestrictedCalculations: " + localProfileException.toString());
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        this.j1 = this.serviceErrorURL;
      }
    }
    if (this.error == 0) {
      this.j1 = this.successURL;
    }
    return this.j1;
  }
  
  public void setAccountId(String paramString)
  {
    this.j7 = paramString;
  }
  
  public void setBankId(String paramString)
  {
    this.j5 = paramString;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.j4 = paramString;
  }
  
  public void setHistoryId(String paramString)
  {
    this.j6 = paramString;
  }
  
  public void setOldListName(String paramString)
  {
    this.j3 = paramString;
  }
  
  public void setNewListName(String paramString)
  {
    this.j2 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.SaveRestrictedCalculations
 * JD-Core Version:    0.7.0.1
 */