package com.ffusion.tasks.banking;

import com.ffusion.beans.FundsTransactionTemplates;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFundsTransactionTemplates
  extends BaseTask
  implements Task
{
  protected String sessionName = "TransferTemplates";
  private String e7 = "BankingAccounts";
  protected int type = 1;
  protected boolean reload = false;
  boolean e8 = false;
  private Locale e6;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.sessionName == null)
    {
      str = this.taskErrorURL;
      return str;
    }
    FundsTransactionTemplates localFundsTransactionTemplates = (FundsTransactionTemplates)localHttpSession.getAttribute(this.sessionName);
    if (this.reload)
    {
      localFundsTransactionTemplates = null;
      localHttpSession.removeAttribute(this.sessionName);
      this.reload = false;
    }
    if (localFundsTransactionTemplates == null)
    {
      this.error = 0;
      localFundsTransactionTemplates = new FundsTransactionTemplates();
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        int i;
        switch (this.type)
        {
        case 1: 
          i = 1001;
          break;
        case 2: 
          i = 1003;
          break;
        case 3: 
          i = 1007;
          break;
        case 4: 
          i = 1005;
          break;
        default: 
          i = 1001;
        }
        localHashMap.put("Accounts", localHttpSession.getAttribute(this.e7));
        if (this.type == 2) {
          localHashMap.put("Payees", localHttpSession.getAttribute("Payees"));
        }
        if (this.e8 == true) {
          localHashMap.put("TemplateType", "Business");
        }
        localFundsTransactionTemplates = Banking.getFundsTransactionTemplates(localSecureUser, i, localHashMap);
        localHttpSession.setAttribute(this.sessionName, localFundsTransactionTemplates);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setSessionName(String paramString)
  {
    this.sessionName = paramString;
  }
  
  public void setAccountsCollection(String paramString)
  {
    this.e7 = paramString;
  }
  
  public String getAccountsCollection()
  {
    return this.e7;
  }
  
  public void setType(String paramString)
  {
    try
    {
      this.type = new Integer(paramString).intValue();
    }
    catch (NumberFormatException localNumberFormatException)
    {
      localNumberFormatException.printStackTrace();
    }
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setBusinessTemplate(String paramString)
  {
    this.e8 = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetFundsTransactionTemplates
 * JD-Core Version:    0.7.0.1
 */