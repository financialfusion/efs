package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BlockedAccts;
import com.ffusion.csil.core.ExternalTransferAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.blockedaccts.BlockedAccount;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.settings.AccountSettings;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddExtTransferAccount
  extends ModifyExtTransferAccount
  implements Task
{
  protected String consumerID = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    this.user = ((User)localHttpSession.getAttribute(this.userSessionName));
    str = addTransferAccount(localHttpSession);
    if (this.error == 0) {
      localHttpSession.setAttribute("ExternalTransferAccountsUpdated", "true");
    }
    return str;
  }
  
  protected String addTransferAccount(HttpSession paramHttpSession)
  {
    String str = null;
    int i = 1;
    this.modify = false;
    if (validateInput(paramHttpSession))
    {
      ExtTransferCompany localExtTransferCompany = new ExtTransferCompany(this.locale);
      parseNumbers(this.numbers);
      localExtTransferCompany.setCustID(this.custID);
      str = processAddExtTransferAccount(this, localExtTransferCompany, paramHttpSession);
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String processAddExtTransferAccount(ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    boolean bool = false;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    this.nextURL = this.successURL;
    Object localObject1;
    try
    {
      int i = Integer.parseInt(this.custID);
      localObject1 = com.ffusion.csil.core.Accounts.getAccountsById(localSecureUser, i, localHashMap);
      setBankId(localSecureUser.getBankID());
      if (localObject1 == null)
      {
        this.nextURL = this.taskErrorURL;
      }
      else
      {
        localObject2 = ((com.ffusion.beans.accounts.Accounts)localObject1).getByIDAndBankIDAndRoutingNum(AccountSettings.buildAccountId(this.number, "" + this.type), this.bankId, this.routingNumber);
        if (localObject2 != null) {
          mapAccount((Account)localObject2);
        } else {
          throw new CSILException(39711);
        }
      }
      Object localObject2 = new BlockedAccount(this.routingNumber, this.bankName, this.number, localSecureUser.getProfileID());
      bool = BlockedAccts.isBlockedAccount(localSecureUser, (BlockedAccount)localObject2, localHashMap);
      if (bool)
      {
        setError(4223);
        return this.taskErrorURL;
      }
      if ((this.consumerID != null) && (User.CUSTOMER_TYPE_CONSUMER.equals(this.user.getCustomerType())))
      {
        paramExtTransferCompany = new ExtTransferCompany(this.locale);
        paramExtTransferCompany.setCustID(this.consumerID);
      }
      ExternalTransferAdmin.addExtTransferAccount(localSecureUser, paramExtTransferAccount, paramExtTransferCompany, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      this.nextURL = this.serviceErrorURL;
    }
    catch (Exception localException)
    {
      DebugLog.log("ERROR: Exception thrown when adding transfer:");
      localException.printStackTrace();
      this.nextURL = this.taskErrorURL;
    }
    if (this.error == 0)
    {
      ExtTransferAccount localExtTransferAccount = new ExtTransferAccount(this.locale);
      setBankId(localSecureUser.getBankID());
      localExtTransferAccount.set(this);
      paramHttpSession.setAttribute("ExternalTransferACCOUNT", localExtTransferAccount);
      processEntitlementsAndLimits(localSecureUser);
      localObject1 = (ExtTransferAccounts)paramHttpSession.getAttribute("ExternalTransferAccounts");
      if (localObject1 != null) {
        ((ExtTransferAccounts)localObject1).add(paramExtTransferAccount);
      }
    }
    return this.nextURL;
  }
  
  public void setConsumerID(String paramString)
  {
    this.consumerID = paramString;
  }
  
  public String getConsumerID()
  {
    return this.consumerID;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.AddExtTransferAccount
 * JD-Core Version:    0.7.0.1
 */