package com.ffusion.tasks.blockedaccts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BlockedAccts;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.CommBankIdentifier;
import com.ffusion.util.beans.blockedaccts.BlockedAccount;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchResult;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyBlockedAccount
  extends BaseTask
  implements BlockedAccountTask
{
  protected BlockedAccount _newAccount = new BlockedAccount();
  protected BlockedAccountSearchResult _oldAccount;
  protected boolean _alreadyExists = false;
  protected boolean _initFlag = false;
  protected boolean _processFlag = false;
  protected boolean _validateFlag = true;
  protected String _userName = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    this.error = 0;
    if (this._initFlag) {
      str = initProcess(paramHttpServletRequest.getSession());
    } else {
      str = processModifyBlockedAccount(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    }
    return str;
  }
  
  protected String initProcess(HttpSession paramHttpSession)
  {
    this._initFlag = false;
    BlockedAccountSearchResult localBlockedAccountSearchResult = (BlockedAccountSearchResult)paramHttpSession.getAttribute("OldBlockedAccount");
    String str = this.successURL;
    if (localBlockedAccountSearchResult == null)
    {
      this.error = 100004;
      str = this.taskErrorURL;
    }
    else
    {
      set(localBlockedAccountSearchResult);
      paramHttpSession.removeAttribute("OldBlockedAccount");
    }
    return str;
  }
  
  protected String processModifyBlockedAccount(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    Businesses localBusinesses = null;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    Users localUsers = null;
    this.error = 0;
    this._alreadyExists = false;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null) {
      this.error = 38;
    } else if (this._validateFlag) {
      validateInput(localSecureUser);
    }
    if (this.error != 0) {
      str1 = this.taskErrorURL;
    }
    if ((this._processFlag) && (this.error == 0))
    {
      if ((this._userName != null) && (this._userName.trim().length() != 0))
      {
        localHashMap.put("NewUserName", this._userName.trim());
        if (this._newAccount.getUserDirectoryID() == 0) {
          validateUser(localSecureUser);
        }
      }
      String str2 = this._oldAccount.getUserName();
      if ((str2 != null) && (str2.trim().length() != 0)) {
        localHashMap.put("OldUserName", str2.trim());
      }
      if (this.error == 0) {
        try
        {
          localUsers = new Users(localSecureUser.getLocale());
          localBusinesses = new Businesses();
          jdMethod_int(localSecureUser, localUsers, localBusinesses);
          BlockedAccts.modifyBlockedAccount(localSecureUser, this._oldAccount, this._newAccount, localHashMap);
          localHttpSession.setAttribute("UserWarnings", localUsers);
          localHttpSession.setAttribute("BusinessWarnings", localBusinesses);
        }
        catch (CSILException localCSILException)
        {
          if (localCSILException.getCode() == 50009)
          {
            this._alreadyExists = true;
          }
          else
          {
            this.error = MapError.mapError(localCSILException);
            str1 = this.serviceErrorURL;
          }
        }
      }
    }
    return str1;
  }
  
  public String getUserName()
  {
    return this._userName;
  }
  
  public void setUserName(String paramString)
  {
    if (paramString != null) {
      this._userName = paramString.trim();
    } else {
      this._userName = null;
    }
  }
  
  public void setProcess(String paramString)
  {
    this._processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setInitialize(String paramString)
  {
    this._initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this._validateFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setRoutingNumber(String paramString)
  {
    this._newAccount.setRoutingNumber(paramString);
  }
  
  public void setAccountNumber(String paramString)
  {
    this._newAccount.setAccountNumber(paramString);
  }
  
  public void setBankName(String paramString)
  {
    this._newAccount.setBankName(paramString);
  }
  
  public String getRoutingNumber()
  {
    return this._newAccount.getRoutingNumber();
  }
  
  public String getAccountNumber()
  {
    return this._newAccount.getAccountNumber();
  }
  
  public String getBankName()
  {
    return this._newAccount.getBankName();
  }
  
  public boolean getBlockedAccountAlreadyExists()
  {
    return this._alreadyExists;
  }
  
  protected boolean validateInput(SecureUser paramSecureUser)
  {
    boolean bool1 = false;
    boolean bool2 = false;
    if (this._newAccount != null)
    {
      String str1 = this._newAccount.getAccountNumber();
      String str2 = this._newAccount.getBankName();
      String str3 = this._newAccount.getRoutingNumber();
      if ((str2 != null) && (str2.length() != 0))
      {
        if ((str3 != null) && (str3.length() != 0))
        {
          try
          {
            bool2 = CommBankIdentifier.isValidCheckZeros(str3);
          }
          catch (Exception localException)
          {
            bool2 = false;
          }
          if (bool2)
          {
            if ((str1 != null) && (str1.length() != 0)) {
              bool1 = validateUser(paramSecureUser);
            } else {
              this.error = 100001;
            }
          }
          else {
            this.error = 100008;
          }
        }
        else
        {
          this.error = 100000;
        }
      }
      else {
        this.error = 100002;
      }
    }
    return bool1;
  }
  
  protected boolean validateUser(SecureUser paramSecureUser)
  {
    boolean bool = false;
    HashMap localHashMap = new HashMap(1);
    int i = 0;
    if ((this._userName != null) && (this._userName.length() != 0))
    {
      User localUser = new User();
      localUser.setUserName(this._userName);
      localUser.setBankId(paramSecureUser.getBankID());
      localHashMap.put("UseLikeComparison", "false");
      try
      {
        Users localUsers = UserAdmin.getUsers(paramSecureUser, localUser, localHashMap);
        if (localUsers.size() == 1)
        {
          localUser = (User)localUsers.get(0);
          i = localUser.getIdValue();
        }
        else
        {
          this.error = 100003;
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = 100003;
      }
    }
    if (this.error == 0)
    {
      this._newAccount.setUserDirectoryID(i);
      bool = true;
    }
    return bool;
  }
  
  protected void set(BlockedAccountSearchResult paramBlockedAccountSearchResult)
  {
    this._oldAccount = paramBlockedAccountSearchResult;
    this._newAccount = new BlockedAccount();
    this._newAccount.setRoutingNumber(paramBlockedAccountSearchResult.getRoutingNumber());
    this._newAccount.setAccountNumber(paramBlockedAccountSearchResult.getAccountNumber());
    this._newAccount.setBankName(paramBlockedAccountSearchResult.getBankName());
    this._newAccount.setUserDirectoryID(paramBlockedAccountSearchResult.getUserDirectoryID());
    this._userName = paramBlockedAccountSearchResult.getUserName();
  }
  
  public void setReset(String paramString)
  {
    set(this._oldAccount);
  }
  
  private void jdMethod_int(SecureUser paramSecureUser, Users paramUsers, Businesses paramBusinesses)
    throws CSILException
  {
    com.ffusion.beans.accounts.Accounts localAccounts = k(paramSecureUser);
    if ((localAccounts != null) && (!localAccounts.isEmpty()))
    {
      jdMethod_int(paramSecureUser, localAccounts, paramUsers);
      jdMethod_int(paramSecureUser, localAccounts, paramBusinesses);
    }
  }
  
  private void jdMethod_int(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, Users paramUsers)
    throws CSILException
  {
    Account localAccount = null;
    HashMap localHashMap = new HashMap();
    User localUser1 = new User();
    User localUser2 = null;
    if ((paramAccounts != null) && (!paramAccounts.isEmpty()))
    {
      Iterator localIterator = paramAccounts.iterator();
      while (localIterator.hasNext())
      {
        localAccount = (Account)localIterator.next();
        localUser1 = new User();
        localUser1.setId(String.valueOf(localAccount.getDirectoryID()));
        try
        {
          localUser2 = UserAdmin.getUserById(paramSecureUser, localUser1, localHashMap);
          if (localUser2 != null)
          {
            paramUsers.add(localUser2);
            localIterator.remove();
          }
        }
        catch (CSILException localCSILException)
        {
          if (localCSILException.getCode() != 3005) {
            throw localCSILException;
          }
        }
      }
    }
  }
  
  private void jdMethod_int(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, Businesses paramBusinesses)
    throws CSILException
  {
    Account localAccount = null;
    HashMap localHashMap = new HashMap();
    com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
    if ((paramAccounts != null) && (!paramAccounts.isEmpty()))
    {
      Iterator localIterator = paramAccounts.iterator();
      while (localIterator.hasNext())
      {
        localAccount = (Account)localIterator.next();
        localBusiness1.setId(localAccount.getDirectoryID());
        try
        {
          com.ffusion.beans.business.Business localBusiness2 = com.ffusion.csil.core.Business.getBusiness(paramSecureUser, localBusiness1, localHashMap);
          if (localBusiness2 != null) {
            paramBusinesses.add(localBusiness2);
          }
        }
        catch (CSILException localCSILException)
        {
          throw localCSILException;
        }
      }
    }
  }
  
  private com.ffusion.beans.accounts.Accounts k(SecureUser paramSecureUser)
    throws CSILException
  {
    Account localAccount = null;
    com.ffusion.beans.accounts.Accounts localAccounts = null;
    HashMap localHashMap = null;
    try
    {
      localAccount = new Account();
      localAccount.setStrippedAccountNumber(BlockedAccts.getStrippedAccountNumber(this._newAccount.getAccountNumber(), localHashMap));
      localAccount.setRoutingNum(this._newAccount.getRoutingNumber());
      localAccount.setBankID(paramSecureUser.getBankID());
      localAccounts = com.ffusion.csil.core.Accounts.searchAccounts(paramSecureUser, localAccount, this._newAccount.getUserDirectoryID(), localHashMap);
      if (localAccounts != null) {
        localAccounts.setFilter("STRIPPED_ACCOUNT_NUMBER=" + localAccount.getStrippedAccountNumber());
      }
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    return localAccounts;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.blockedaccts.ModifyBlockedAccount
 * JD-Core Version:    0.7.0.1
 */