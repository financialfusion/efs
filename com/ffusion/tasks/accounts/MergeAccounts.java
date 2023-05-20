package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MergeAccounts
  extends BaseTask
  implements Task
{
  protected String primaryAccountsName = "Accounts";
  protected String secondaryAccountsName = "Accounts";
  public static final String HIDE = "HIDE";
  public static final String SHOWACCOUNT = "0";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.beans.accounts.Accounts localAccounts1 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.primaryAccountsName);
    com.ffusion.beans.accounts.Accounts localAccounts2 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.secondaryAccountsName);
    if (localAccounts1 == null)
    {
      this.error = 39;
      str = this.taskErrorURL;
    }
    else if (localAccounts2 == null)
    {
      this.error = 39;
      str = this.taskErrorURL;
    }
    else
    {
      Iterator localIterator = localAccounts1.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount1 = (Account)localIterator.next();
        Account localAccount2 = localAccounts2.getByID(localAccount1.getID());
        HashMap localHashMap;
        Object localObject;
        if (localAccount2 == null)
        {
          if ((localAccount1.getNickName() == null) || (localAccount1.getNickName().trim().length() == 0)) {
            localAccount1.setNickName(localAccount1.getType());
          }
          localAccount1.set("HIDE", "0");
          if (localAccount1.getZBAFlag() == null) {
            localAccount1.setZBAFlag("N");
          }
          if (localAccount1.getShowPreviousDayOpeningLedger() == null) {
            localAccount1.setShowPreviousDayOpeningLedger("Y");
          }
          localHashMap = null;
          localHashMap = new HashMap();
          localObject = (SecureUser)localHttpSession.getAttribute("SecureUser");
          if (((SecureUser)localObject).getPrimaryUserID() == ((SecureUser)localObject).getProfileID()) {
            try
            {
              localAccount1 = com.ffusion.csil.core.Accounts.addAccount((SecureUser)localObject, localAccount1, localHashMap);
            }
            catch (CSILException localCSILException)
            {
              this.error = MapError.mapError(localCSILException);
              str = this.serviceErrorURL;
            }
          } else {
            localIterator.remove();
          }
        }
        else
        {
          if ((localAccount2.getNickName() == null) || (localAccount2.getNickName().trim().length() == 0)) {
            localAccount1.setNickName(localAccount2.getType());
          } else {
            localAccount1.setNickName(localAccount2.getNickName());
          }
          if (localAccount1.getZBAFlag() == null)
          {
            localAccount1.setZBAFlag(localAccount2.getZBAFlag());
            if (localAccount1.getZBAFlag() == null) {
              localAccount1.setZBAFlag("N");
            }
          }
          if (localAccount1.getShowPreviousDayOpeningLedger() == null)
          {
            localAccount1.setShowPreviousDayOpeningLedger(localAccount2.getShowPreviousDayOpeningLedger());
            if (localAccount1.getShowPreviousDayOpeningLedger() == null) {
              localAccount1.setShowPreviousDayOpeningLedger("Y");
            }
          }
          localHashMap = localAccount2.getHash();
          if (localHashMap != null)
          {
            localObject = localHashMap.entrySet().iterator();
            while (((Iterator)localObject).hasNext())
            {
              Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
              localAccount1.put((String)localEntry.getKey(), localEntry.getValue());
            }
          }
        }
        localAccounts2.removeByID(localAccount1.getID());
      }
    }
    return str;
  }
  
  public void setPrimaryAccountsName(String paramString)
  {
    this.primaryAccountsName = paramString;
  }
  
  public void setSecondaryAccountsName(String paramString)
  {
    this.secondaryAccountsName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.MergeAccounts
 * JD-Core Version:    0.7.0.1
 */