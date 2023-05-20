package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTemplatesAccounts
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected int businessID;
  protected boolean reload = false;
  protected String accountsName = "BankingAccounts";
  
  public GetTemplatesAccounts()
  {
    this.collectionSessionName = "WiresAccounts";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    Accounts localAccounts1 = (Accounts)localHttpSession.getAttribute(this.accountsName);
    Accounts localAccounts2 = (Accounts)localHttpSession.getAttribute(this.collectionSessionName);
    if (this.reload)
    {
      localAccounts2 = null;
      localHttpSession.removeAttribute(this.collectionSessionName);
      this.reload = false;
    }
    if (localAccounts2 == null) {
      try
      {
        HashMap localHashMap = new HashMap();
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        if (localSecureUser.getUserType() == 2)
        {
          if (this.businessID == 0)
          {
            this.error = 12034;
            return this.taskErrorURL;
          }
          localSecureUser.setBusinessID(this.businessID);
        }
        localHashMap.put("ACCOUNTS", localAccounts1);
        localAccounts2 = Wire.getWiresAccounts(localSecureUser, "TEMPLATE_WIRE", localHashMap);
        localHttpSession.setAttribute(this.collectionSessionName, localAccounts2);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        return this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setBusinessID(String paramString)
  {
    try
    {
      this.businessID = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getBusinessID()
  {
    return String.valueOf(this.businessID);
  }
  
  public String getAccountsName()
  {
    return this.accountsName;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetTemplatesAccounts
 * JD-Core Version:    0.7.0.1
 */