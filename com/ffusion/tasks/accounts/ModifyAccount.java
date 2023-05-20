package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyAccount
  extends Account
  implements Task
{
  protected String successURL;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error;
  protected String validate;
  protected boolean processFlag = false;
  protected String accountCollection;
  protected boolean bInit = false;
  public static final String MODIFY_ACCOUNT_BUSINESS = "MODIFY_ACCOUNT_BUSINESS";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = null;
    com.ffusion.beans.accounts.Accounts localAccounts;
    Object localObject1;
    if (this.bInit)
    {
      localAccounts = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.accountCollection);
      if (localAccounts != null)
      {
        localObject1 = localAccounts.getByID(getID());
        if (localObject1 != null)
        {
          set((Account)localObject1);
          str1 = this.successURL;
          this.bInit = false;
        }
        else
        {
          str1 = this.taskErrorURL;
          this.error = 18003;
        }
      }
      else
      {
        str1 = this.taskErrorURL;
        this.error = 39;
      }
    }
    else if (validateInput(localHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        localAccounts = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.accountCollection);
        localObject1 = new HashMap();
        Business localBusiness = (Business)localHttpSession.getAttribute("Business");
        if (localBusiness != null) {
          ((HashMap)localObject1).put("MODIFY_ACCOUNT_BUSINESS", localBusiness);
        }
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        Object localObject2 = this;
        this.error = 0;
        try
        {
          localObject2 = com.ffusion.csil.core.Accounts.modifyAccount(localSecureUser, (Account)localObject2, (HashMap)localObject1);
          set((Account)localObject2);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str1 = this.serviceErrorURL;
        }
        int i = this.error;
        if (i == 0)
        {
          localAccounts.removeByID(getID());
          localAccounts.add(this);
          String str2 = localAccounts.getSortedBy();
          if (str2 != null) {
            localAccounts.setSortedBy(localAccounts.getSortedBy());
          }
          str1 = this.successURL;
        }
      }
      else
      {
        str1 = this.successURL;
      }
    }
    else
    {
      str1 = this.taskErrorURL;
    }
    return str1;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null)
    {
      if ((this.validate.indexOf("NICKNAME") != -1) && ((getNickName() == null) || (getNickName().trim().length() == 0)))
      {
        bool = false;
        this.error = 34;
      }
      this.validate = null;
    }
    return bool;
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setAccountCollection(String paramString)
  {
    this.accountCollection = paramString;
  }
  
  public void setInit(String paramString)
  {
    this.bInit = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.ModifyAccount
 * JD-Core Version:    0.7.0.1
 */