package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.RecTransfer;
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

public class GetRecTransferById
  extends ExtendedBaseTask
  implements Task
{
  private Transfer zb = new Transfer();
  private String zc = "TransferAccounts";
  
  public GetRecTransferById()
  {
    this.beanSessionName = "RecTransfer";
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
    this.zb.setID(this.id);
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.zc);
    localHashMap.put("ACCOUNTS", localAccounts);
    RecTransfer localRecTransfer = null;
    try
    {
      localRecTransfer = Banking.getRecTransferByID(localSecureUser, this.zb, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this.beanSessionName, localRecTransfer);
    return str;
  }
  
  public void setDestination(String paramString)
  {
    this.zb.setTransferDestination(paramString);
  }
  
  public void setAccountsSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.zc = "TransferAccounts";
    } else {
      this.zc = paramString;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetRecTransferById
 * JD-Core Version:    0.7.0.1
 */