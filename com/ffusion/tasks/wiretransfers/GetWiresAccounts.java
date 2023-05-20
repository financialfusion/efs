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

public class GetWiresAccounts
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected boolean reload = false;
  protected String accountsName = "BankingAccounts";
  protected String type = null;
  protected String flowName = null;
  protected String source = "FREEFORM";
  
  public GetWiresAccounts()
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
        if (this.type != null) {
          if (this.source.equalsIgnoreCase("TEMPLATE"))
          {
            if (this.type.equals("DOMESTIC")) {
              this.flowName = "DOMESTIC_TEMPLATED_WIRE";
            } else if (this.type.equals("INTERNATIONAL")) {
              this.flowName = "INTERNATIONAL_TEMPLATED_WIRE";
            } else if (this.type.equals("DRAWDOWN")) {
              this.flowName = "DRAWDOWN_TEMPLATED_WIRE";
            } else if (this.type.equals("FEDWIRE")) {
              this.flowName = "FED_TEMPLATED_WIRE";
            } else if (this.type.equals("BOOKTRANSFER")) {
              this.flowName = "BOOK_TEMPLATED_WIRE";
            }
          }
          else if (this.type.equals("DOMESTIC")) {
            this.flowName = "DOMESTIC_FREEFORM_WIRE";
          } else if (this.type.equals("INTERNATIONAL")) {
            this.flowName = "INTERNATIONAL_FREEFORM_WIRE";
          } else if (this.type.equals("DRAWDOWN")) {
            this.flowName = "DRAWDOWN_FREEFORM_WIRE";
          } else if (this.type.equals("FEDWIRE")) {
            this.flowName = "FED_FREEFORM_WIRE";
          } else if (this.type.equals("BOOKTRANSFER")) {
            this.flowName = "BOOK_FREEFORM_WIRE";
          }
        }
        localHashMap.put("ACCOUNTS", localAccounts1);
        localAccounts2 = Wire.getWiresAccounts(localSecureUser, this.flowName, localHashMap);
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
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setOpName(String paramString)
  {
    this.flowName = paramString;
  }
  
  public String getOpName()
  {
    return this.flowName;
  }
  
  public String getSource()
  {
    return this.source;
  }
  
  public void setSource(String paramString)
  {
    this.source = paramString;
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
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetWiresAccounts
 * JD-Core Version:    0.7.0.1
 */