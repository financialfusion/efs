package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTransferById
  extends ExtendedBaseTask
  implements Task
{
  private Transfer zt = new Transfer();
  private String zu = "TransferAccounts";
  
  public GetTransferById()
  {
    this.beanSessionName = "Transfer";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.id == null) || (this.id.length() == 0))
    {
      this.error = 81;
      return this.taskErrorURL;
    }
    this.zt.setID(this.id);
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.zu);
    localHashMap.put("ACCOUNTS", localAccounts);
    Transfer localTransfer = null;
    try
    {
      localTransfer = Banking.getTransferByID(localSecureUser, this.zt, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this.beanSessionName, localTransfer);
    return str;
  }
  
  public void setDestination(String paramString)
  {
    this.zt.setTransferDestination(paramString);
  }
  
  public void setAccountsSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.zu = "TransferAccounts";
    } else {
      this.zu = paramString;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetTransferById
 * JD-Core Version:    0.7.0.1
 */