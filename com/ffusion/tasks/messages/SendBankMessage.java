package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.services.Messaging;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import javax.servlet.http.HttpSession;

public class SendBankMessage
  extends SendMessage
{
  protected String accountsName;
  protected String accountID;
  
  protected String sendMessage(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.error = 0;
    this.processFlag = false;
    HashMap localHashMap = new HashMap();
    if (this.accountsName == null) {
      this.error = 8010;
    } else if (this.accountID != null) {
      this.error = 8011;
    }
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this.accountsName);
    if (localAccounts == null)
    {
      this.error = 8012;
    }
    else
    {
      Account localAccount = localAccounts.getByID(this.accountID);
      if (localAccount == null)
      {
        this.error = 8013;
      }
      else
      {
        localHashMap.put("OBJECT", localAccount);
        localHashMap.put("SERVICE", (Messaging)paramHttpSession.getAttribute("com.ffusion.services.Messaging3"));
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        try
        {
          Messages.sendMessage(localSecureUser, this, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
      }
    }
    return str;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
  
  public void setAccountID(String paramString)
  {
    this.accountID = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendBankMessage
 * JD-Core Version:    0.7.0.1
 */