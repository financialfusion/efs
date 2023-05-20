package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteACHOffsetAccount
  extends BaseTask
  implements Task
{
  protected String achCompanyName = "ACHCOMPANY";
  protected String offsetAccountsName = "ACHOffsetAccounts";
  protected String offsetAccountName = "ACHOffsetAccount";
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    ACHOffsetAccount localACHOffsetAccount = (ACHOffsetAccount)localHttpSession.getAttribute(this.offsetAccountName);
    if (localACHOffsetAccount == null)
    {
      this.error = 16014;
      str = this.taskErrorURL;
    }
    else if (validateInput(localHttpSession, localACHOffsetAccount))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = deleteOffsetAccount(localHttpSession, localACHOffsetAccount);
      }
      else
      {
        str = this.successURL;
      }
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession, ACHOffsetAccount paramACHOffsetAccount)
  {
    boolean bool = true;
    if (this.validate != null) {
      this.validate = null;
    }
    return bool;
  }
  
  protected String deleteOffsetAccount(HttpSession paramHttpSession, ACHOffsetAccount paramACHOffsetAccount)
  {
    String str = this.successURL;
    this.error = 0;
    ACHOffsetAccounts localACHOffsetAccounts = (ACHOffsetAccounts)paramHttpSession.getAttribute(this.offsetAccountsName);
    ACHCompany localACHCompany = (ACHCompany)paramHttpSession.getAttribute(this.achCompanyName);
    if (localACHOffsetAccounts == null)
    {
      this.error = 16013;
      str = this.taskErrorURL;
    }
    else if (localACHCompany == null)
    {
      this.error = 16506;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        HashMap localHashMap = null;
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        ACH.deleteOffsetAccount(localSecureUser, paramACHOffsetAccount, localACHCompany, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException, paramHttpSession);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        localACHOffsetAccounts.removeByID(paramACHOffsetAccount.getID());
        paramHttpSession.removeAttribute(this.offsetAccountName);
      }
    }
    return str;
  }
  
  public final void setOffsetAccountsInSessionName(String paramString)
  {
    this.offsetAccountsName = paramString;
  }
  
  public final String getOffsetAccountsInSessionName()
  {
    return this.offsetAccountsName;
  }
  
  public final void setOffsetAccountInSessionName(String paramString)
  {
    this.offsetAccountName = paramString;
  }
  
  public final String getOffsetAccountInSessionName()
  {
    return this.offsetAccountName;
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (paramString.trim().length() > 0) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setACHCompanyInSessionName(String paramString)
  {
    this.achCompanyName = paramString;
  }
  
  public final String getACHCompanyInSessionName()
  {
    return this.achCompanyName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.DeleteACHOffsetAccount
 * JD-Core Version:    0.7.0.1
 */