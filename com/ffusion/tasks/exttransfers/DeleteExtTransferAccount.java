package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ExternalTransferAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.settings.AccountSettings;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteExtTransferAccount
  extends AddExtTransferAccount
{
  protected String nextURL = null;
  protected boolean isConsumer = false;
  protected boolean deleteProfileAccount = false;
  protected String sourceAccts = "BankingAccounts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    str = processDeleteExtTransferAccount(localHttpSession);
    if (this.error == 0) {
      localHttpSession.setAttribute("ExternalTransferAccountsUpdated", "true");
    }
    return str;
  }
  
  protected String processDeleteExtTransferAccount(HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    this.nextURL = this.successURL;
    try
    {
      this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
      ExtTransferCompany localExtTransferCompany = new ExtTransferCompany(this.locale);
      localExtTransferCompany.setCustID(this.custID);
      ExternalTransferAdmin.deleteExtTransferAccount(localSecureUser, this, localExtTransferCompany, localHashMap);
      if ((this.isConsumer) || (this.deleteProfileAccount)) {
        try
        {
          int i = Integer.parseInt(this.custID);
          com.ffusion.beans.accounts.Accounts localAccounts1 = com.ffusion.csil.core.Accounts.getAccountsById(localSecureUser, i, localHashMap);
          if (localAccounts1 == null)
          {
            setError(18006);
            return this.taskErrorURL;
          }
          ExtTransferAccounts localExtTransferAccounts2 = (ExtTransferAccounts)paramHttpSession.getAttribute("ExternalTransferAccounts");
          ExtTransferAccount localExtTransferAccount = null;
          if (localExtTransferAccounts2 != null)
          {
            localExtTransferAccount = localExtTransferAccounts2.getByBpwID(getBpwID());
            if (localExtTransferAccount != null) {
              set(localExtTransferAccount);
            }
          }
          Account localAccount1 = localAccounts1.getByIDAndRoutingNum(AccountSettings.buildAccountId(this.number, "" + this.type), this.routingNumber);
          if ((localAccount1 != null) && ("2".equals(localAccount1.getCoreAccount())))
          {
            com.ffusion.csil.core.Accounts.deleteAccount(localSecureUser, localAccount1, i, localHashMap);
            com.ffusion.beans.accounts.Accounts localAccounts2 = (com.ffusion.beans.accounts.Accounts)paramHttpSession.getAttribute(this.sourceAccts);
            if (localAccounts2 != null)
            {
              Account localAccount2 = localAccounts2.getByIDAndBankIDAndRoutingNum(AccountSettings.buildAccountId(this.number, "" + this.type), this.bankId, this.routingNumber);
              if (localAccount2 != null) {
                localAccounts2.remove(localAccount2);
              }
            }
          }
        }
        catch (Exception localException2) {}
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      this.nextURL = this.serviceErrorURL;
    }
    catch (Exception localException1)
    {
      DebugLog.log("ERROR: Exception thrown when deleting transfer:");
      localException1.printStackTrace();
      this.nextURL = this.taskErrorURL;
    }
    if (this.error == 0)
    {
      ExtTransferAccounts localExtTransferAccounts1 = (ExtTransferAccounts)paramHttpSession.getAttribute("ExternalTransferAccounts");
      if (localExtTransferAccounts1 != null) {
        localExtTransferAccounts1.removeByID(getBpwID());
      }
    }
    return this.nextURL;
  }
  
  public void setIsConsumer(String paramString)
  {
    this.isConsumer = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setDeleteProfileAccount(String paramString)
  {
    this.deleteProfileAccount = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setSourceAccounts(String paramString)
  {
    this.sourceAccts = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.DeleteExtTransferAccount
 * JD-Core Version:    0.7.0.1
 */