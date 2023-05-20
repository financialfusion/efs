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
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateAccountsBC
  extends BaseTask
  implements Task
{
  private int nb;
  private String na = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap1 = new HashMap();
    try
    {
      com.ffusion.beans.accounts.Accounts localAccounts1 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute("OriginalAccounts");
      com.ffusion.beans.accounts.Accounts localAccounts2 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute("Accounts");
      if ((localAccounts2 == null) || (localAccounts1 == null))
      {
        this.error = 18006;
        return this.taskErrorURL;
      }
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      com.ffusion.beans.accounts.Accounts localAccounts3 = new com.ffusion.beans.accounts.Accounts(localLocale);
      HashMap localHashMap2 = new HashMap();
      Iterator localIterator1 = localAccounts1.iterator();
      while (localIterator1.hasNext())
      {
        localObject1 = (Account)localIterator1.next();
        localHashMap2.put(((Account)localObject1).getID(), localObject1);
      }
      Object localObject1 = new HistoryTracker(localSecureUser, 2, Integer.toString(this.nb));
      Iterator localIterator2 = localAccounts2.iterator();
      Account localAccount;
      while (localIterator2.hasNext())
      {
        localObject2 = (Account)localIterator2.next();
        localAccount = (Account)localHashMap2.get(((Account)localObject2).getID());
        if (localAccount == null)
        {
          localAccounts3.add(localObject2);
          ((HistoryTracker)localObject1).logChange(((HistoryTracker)localObject1).lookupField(Business.BEAN_NAME, Business.ACCOUNTS), null, ((Account)localObject2).getNumber(), 3);
        }
        else
        {
          localHashMap2.remove(((Account)localObject2).getID());
        }
      }
      Object localObject2 = localHashMap2.values().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localAccount = (Account)((Iterator)localObject2).next();
        com.ffusion.csil.core.Accounts.deleteAccount(localSecureUser, localAccount, this.nb, localHashMap1);
        ((HistoryTracker)localObject1).logChange(((HistoryTracker)localObject1).lookupField(Business.BEAN_NAME, Business.ACCOUNTS), localAccount.getNumber(), null, 4);
      }
      com.ffusion.csil.core.Accounts.addAccounts(localSecureUser, localAccounts3, this.nb, localHashMap1);
      try
      {
        HistoryAdapter.addHistory(((HistoryTracker)localObject1).getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for UpdateAccountsBC: " + localProfileException.toString());
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
      this.nb = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.UpdateAccountsBC
 * JD-Core Version:    0.7.0.1
 */