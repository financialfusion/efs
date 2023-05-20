package com.ffusion.tasks.accounts;

import com.ffusion.beans.Contact;
import com.ffusion.beans.Email;
import com.ffusion.beans.Phone;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.RegisterUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Util;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyAccountById
  extends Account
  implements Task
{
  protected String successURL;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error;
  protected String validate;
  protected String verifyFormat;
  protected boolean processFlag = false;
  protected String accountCollection;
  protected boolean bInit = false;
  protected int directoryId = -1;
  protected SecureUser sUser = null;
  private Account ni = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = null;
    String str2 = "ModifyAccountByID.process";
    com.ffusion.beans.accounts.Accounts localAccounts;
    String str3;
    Object localObject1;
    Object localObject2;
    if (this.bInit)
    {
      this.sUser = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
      localAccounts = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.accountCollection);
      if (localAccounts != null)
      {
        str3 = getID();
        localObject1 = getRoutingNum();
        localObject2 = null;
        if ((str3 != null) && (str3.trim().length() > 0) && (localObject1 != null) && (((String)localObject1).trim().length() > 0)) {
          localObject2 = localAccounts.getByIDAndRoutingNum(str3, (String)localObject1);
        } else if ((str3 != null) && (str3.trim().length() > 0)) {
          localObject2 = localAccounts.getByID(str3);
        }
        if (localObject2 != null)
        {
          try
          {
            com.ffusion.csil.core.Accounts.getAccountAddress(this.sUser, (Account)localObject2, null);
          }
          catch (CSILException localCSILException3)
          {
            this.error = MapError.mapError(localCSILException3);
            return this.serviceErrorURL;
          }
          this.ni = ((Account)localObject2);
          set((Account)localObject2);
          if ((this.ni != null) && (this.ni.getContact() != null)) {
            setContact((Contact)this.ni.getContact().clone());
          }
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
    else
    {
      try
      {
        if (validateInput(localHttpSession))
        {
          if (this.processFlag)
          {
            if (this.directoryId > 0)
            {
              this.processFlag = false;
              localAccounts = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.accountCollection);
              str3 = null;
              this.sUser = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
              localObject1 = this;
              this.error = 0;
              Object localObject3;
              Object localObject4;
              try
              {
                localObject1 = com.ffusion.csil.core.Accounts.modifyAccountById(this.sUser, this.directoryId, (Account)localObject1, str3);
                localObject2 = new Object[2];
                localObject2[0] = ((Account)localObject1).getRoutingNum();
                localObject2[1] = ((Account)localObject1).buildLocalizableAccountID();
                localObject3 = new LocalizableString("com.ffusion.beans.history.resources", "MODIFY_ACCOUNT", (Object[])localObject2);
                localObject4 = new HistoryTracker(this.sUser, 2, Integer.toString(this.directoryId));
                ((Account)localObject1).logChanges((HistoryTracker)localObject4, this.ni, (ILocalizable)localObject3);
                try
                {
                  HistoryAdapter.addHistory(((HistoryTracker)localObject4).getHistories());
                }
                catch (ProfileException localProfileException)
                {
                  DebugLog.log(Level.SEVERE, "Add History failed for " + str2 + ": " + localProfileException.toString());
                  DebugLog.throwing(str2, localProfileException);
                }
                if (localObject1 != this) {
                  set((Account)localObject1);
                }
                this.ni.set((Account)localObject1);
              }
              catch (CSILException localCSILException2)
              {
                this.error = MapError.mapError(localCSILException2);
                str1 = this.serviceErrorURL;
              }
              int i = this.error;
              if (i == 0)
              {
                localAccounts.removeByID(getID());
                localAccounts.add(this);
                localObject3 = localAccounts.getSortedBy();
                if (localObject3 != null) {
                  localAccounts.setSortedBy(localAccounts.getSortedBy());
                }
                str1 = this.successURL;
                localObject4 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute("BankingAccounts");
                if (localObject4 != null)
                {
                  ((com.ffusion.beans.accounts.Accounts)localObject4).setFilter("All");
                  Account localAccount = ((com.ffusion.beans.accounts.Accounts)localObject4).getByID(((Account)localObject1).getID());
                  if (localAccount != null)
                  {
                    localAccount.setNickName(((Account)localObject1).getNickName());
                    localAccount.setContact(((Account)localObject1).getContact());
                    if (RegisterUtil.isRegisterEnabled((Account)localObject1)) {
                      localAccount.set("REG_ENABLED", "true");
                    } else {
                      localAccount.set("REG_ENABLED", "false");
                    }
                  }
                }
              }
            }
            else
            {
              str1 = this.taskErrorURL;
              this.error = 18012;
            }
          }
          else {
            str1 = this.successURL;
          }
        }
        else {
          str1 = this.taskErrorURL;
        }
      }
      catch (CSILException localCSILException1)
      {
        MapError.mapError(localCSILException1);
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
    throws CSILException
  {
    boolean bool = true;
    if (this.validate != null) {
      bool = validationCheck(paramHttpSession);
    }
    if (bool)
    {
      if (this.verifyFormat != null) {
        bool = verifyFormatCheck(paramHttpSession);
      }
    }
    else {
      this.verifyFormat = null;
    }
    return bool;
  }
  
  protected boolean validationCheck(HttpSession paramHttpSession)
  {
    if (this.validate == null) {
      return true;
    }
    if ((this.validate.indexOf("NICKNAME") != -1) && ((getNickName() == null) || (getNickName().trim().length() == 0)))
    {
      this.error = 34;
      return false;
    }
    if ((this.validate.indexOf("STREET") != -1) && ((getContact().getStreet() == null) || (getContact().getStreet().trim().length() == 0) || (getContact().getStreet().trim().length() > 40)))
    {
      this.error = 23;
      return false;
    }
    if ((this.validate.indexOf("STREET2") != -1) && ((getContact().getStreet2() == null) || (getContact().getStreet2().trim().length() == 0) || (getContact().getStreet2().trim().length() > 40)))
    {
      this.error = 23;
      return false;
    }
    if ((this.validate.indexOf("CITY") != -1) && ((getContact().getCity() == null) || (getContact().getCity().trim().length() == 0) || (getContact().getCity().trim().length() > 20)))
    {
      this.error = 24;
      return false;
    }
    if ((this.validate.indexOf("ZIPCODE") != -1) && ((getContact().getZipCodeValue() == null) || (getContact().getZipCodeValue().getString().length() == 0) || (getContact().getZipCodeValue().getString().length() > 11)))
    {
      this.error = 26;
      return false;
    }
    if ((this.validate.indexOf("STATE") != -1) && ((getContact().getState() == null) || (getContact().getState().length() == 0)))
    {
      this.error = 25;
      return false;
    }
    if ((this.validate.indexOf("COUNTRY") != -1) && ((getContact().getCountry() == null) || (getContact().getCountry().length() == 0)))
    {
      this.error = 53;
      return false;
    }
    if ((this.validate.indexOf("PHONE") != -1) && ((getContact().getPhoneValue() == null) || (getContact().getPhoneValue().getString().length() == 0) || (getContact().getPhoneValue().toString().length() > 14)))
    {
      this.error = 27;
      return false;
    }
    if ((this.validate.indexOf("FAXPHONE") != -1) && ((getContact().getFaxPhoneValue() == null) || (getContact().getFaxPhoneValue().getString().length() == 0) || (getContact().getFaxPhoneValue().toString().length() > 14)))
    {
      this.error = 119;
      return false;
    }
    if ((this.validate.indexOf("EMAIL") != -1) && ((getContact().getEmailValue() == null) || (getContact().getEmailValue().getString().length() == 0) || (getContact().getEmailValue().toString().length() > 40)))
    {
      this.error = 31;
      return false;
    }
    if ((this.validate.indexOf("EMAIL") != -1) && (getContact().getEmailValue() != null) && (!getContact().getEmailValue().isValid()))
    {
      this.error = 31;
      return false;
    }
    this.validate = null;
    return true;
  }
  
  protected boolean verifyFormatCheck(HttpSession paramHttpSession)
    throws CSILException
  {
    ZipCode localZipCode = getContact().getZipCodeValue();
    Phone localPhone1 = getContact().getPhoneValue();
    Phone localPhone2 = getContact().getPhone2Value();
    Phone localPhone3 = getContact().getDataPhoneValue();
    Phone localPhone4 = getContact().getFaxPhoneValue();
    String str1 = getContact().getCountry();
    if ((this.verifyFormat.indexOf("STREET") != -1) && (getContact().getStreet() != null) && (getContact().getStreet().trim().length() != 0) && (getContact().getStreet().trim().length() > 40))
    {
      this.error = 23;
      return false;
    }
    if ((this.verifyFormat.indexOf("STREET2") != -1) && (getContact().getStreet2() != null) && (getContact().getStreet2().trim().length() != 0) && (getContact().getStreet2().trim().length() > 40))
    {
      this.error = 23;
      return false;
    }
    if ((this.verifyFormat.indexOf("CITY") != -1) && (getContact().getCity() != null) && (getContact().getCity().trim().length() != 0) && (getContact().getCity().trim().length() > 20))
    {
      this.error = 24;
      return false;
    }
    if ((this.verifyFormat.indexOf("ZIPCODE") != -1) && (getContact().getZipCodeValue() != null) && (getContact().getZipCodeValue().getString().length() != 0) && (getContact().getZipCodeValue().getString().length() > 11))
    {
      this.error = 26;
      return false;
    }
    if ((this.verifyFormat.indexOf("PHONE") != -1) && (getContact().getPhoneValue() != null) && (getContact().getPhoneValue().getString().length() != 0) && (getContact().getPhoneValue().toString().length() > 14))
    {
      this.error = 27;
      return false;
    }
    if ((this.verifyFormat.indexOf("FAXPHONE") != -1) && (getContact().getFaxPhoneValue() != null) && (getContact().getFaxPhoneValue().getString().length() != 0) && (getContact().getFaxPhoneValue().toString().length() > 14))
    {
      this.error = 119;
      return false;
    }
    if ((this.verifyFormat.indexOf("EMAIL") != -1) && (getContact().getEmailValue() != null) && (getContact().getEmailValue().getString().length() != 0) && (getContact().getEmailValue().toString().length() > 40))
    {
      this.error = 31;
      return false;
    }
    if ((this.verifyFormat.indexOf("EMAIL") != -1) && (getContact().getEmailValue() != null) && (getContact().getEmailValue().getString().length() != 0) && (!getContact().getEmailValue().isValid()))
    {
      this.error = 31;
      return false;
    }
    if ((str1 != null) && (str1.length() > 0) && (Util.isRegisteredCountry(this.sUser, str1, new HashMap())))
    {
      String str2;
      if ((this.verifyFormat.indexOf("ZIPCODE") != -1) && (localZipCode != null) && (localZipCode.getString().length() > 0))
      {
        str2 = Util.validateZipCodeFormat(this.sUser, str1, localZipCode.getString(), new HashMap());
        if (str2 == null)
        {
          this.error = 26;
          return false;
        }
        localZipCode.setFormat(str2);
      }
      if ((this.verifyFormat.indexOf("PHONE") != -1) && (localPhone1 != null) && (localPhone1.getString().length() > 0))
      {
        str2 = Util.validatePhoneFormat(this.sUser, str1, localPhone1.getString(), new HashMap());
        if (str2 == null)
        {
          this.error = 27;
          return false;
        }
        localPhone1.setFormat(str2);
      }
      if ((this.verifyFormat.indexOf("PHONE2") != -1) && (localPhone2 != null) && (localPhone2.getString().length() > 0))
      {
        str2 = Util.validatePhoneFormat(this.sUser, str1, localPhone2.getString(), new HashMap());
        if (str2 == null)
        {
          this.error = 27;
          return false;
        }
        localPhone2.setFormat(str2);
      }
      if ((this.verifyFormat.indexOf("DATAPHONE") != -1) && (localPhone3 != null) && (localPhone3.getString().length() > 0))
      {
        str2 = Util.validatePhoneFormat(this.sUser, str1, localPhone3.getString(), new HashMap());
        if (str2 == null)
        {
          this.error = 120;
          return false;
        }
        localPhone3.setFormat(str2);
      }
      if ((this.verifyFormat.indexOf("FAXPHONE") != -1) && (localPhone4 != null) && (localPhone4.getString().length() > 0))
      {
        str2 = Util.validatePhoneFormat(this.sUser, str1, localPhone4.getString(), new HashMap());
        if (str2 == null)
        {
          this.error = 119;
          return false;
        }
        localPhone4.setFormat(str2);
      }
    }
    this.verifyFormat = null;
    return true;
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
  
  public void setVerifyFormat(String paramString)
  {
    this.verifyFormat = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.verifyFormat = paramString.toUpperCase();
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
  
  public void setDirectoryId(String paramString)
  {
    this.directoryId = Integer.parseInt(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.ModifyAccountById
 * JD-Core Version:    0.7.0.1
 */