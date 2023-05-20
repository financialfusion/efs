package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.messages.Message;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendAddOnlineBankingAccountMessage
  extends BaseTask
{
  public static final String ADD_ONLINE_BANK_ACCOUNT_FULL_COMMENT_KEY = "AddOnlineBankingAccountFullComment";
  public static final String ADD_ONLINE_BANK_ACCOUNT_DEFAULT_COMMENT_KEY = "AddOnlineBankingAccountDefaultComment";
  public static final String ADD_ONLINE_BANK_ACCOUNT_START_COMMENT_KEY = "AddOnlineBankingAccountStartComment";
  protected int maxSize = 1024;
  protected int _numAccounts = 3;
  protected int[] _accountTypes = null;
  protected String _accountNumberPrefix = "AccountNumber";
  protected String _accountTypePrefix = "AccountType";
  protected String _contactNumber = null;
  protected String _question = null;
  protected String _extraValidationFields = null;
  protected String[] _accountNumbers = null;
  protected boolean _processFlag = false;
  protected boolean _validateFlag = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ILocalizable localILocalizable = null;
    Message localMessage = null;
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this._accountNumbers = new String[this._numAccounts];
    this._accountTypes = new int[this._numAccounts];
    for (int i = 0; i < this._numAccounts; i++)
    {
      this._accountNumbers[i] = ((String)localHttpSession.getAttribute(this._accountNumberPrefix + String.valueOf(i + 1)));
      try
      {
        this._accountTypes[i] = Integer.parseInt((String)localHttpSession.getAttribute(this._accountTypePrefix + String.valueOf(i + 1)));
      }
      catch (Exception localException)
      {
        this._accountTypes[i] = -1;
      }
    }
    if (localSecureUser == null) {
      this.error = 38;
    } else if ((K()) && (this._validateFlag)) {
      validate(localSecureUser, localHttpSession);
    }
    if (this.error != 0) {
      str = this.taskErrorURL;
    } else if (this._processFlag) {
      try
      {
        localMessage = new Message(localSecureUser.getLocale());
        ServiceCenterTask.setupServiceCenterMessage(localSecureUser, localMessage, "24", localHashMap);
        localILocalizable = createBody(localSecureUser.getLocale());
        localMessage.setComment((String)localILocalizable.localize(localSecureUser.getLocale()));
        if ((this._question == null) || (this._question.length() == 0)) {
          this._question = ((String)new LocalizableString("com.ffusion.tasks.service_center", "AddOnlineBankingAccountDefaultComment", null).localize(localSecureUser.getLocale()));
        }
        localMessage.setMemo(this._question);
        Messages.sendMessage(localSecureUser, localMessage, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public int getNumberOfAccounts()
  {
    return this._numAccounts;
  }
  
  public String getAccountNumberPrefix()
  {
    return this._accountNumberPrefix;
  }
  
  public String getAccountTypePrefix()
  {
    return this._accountTypePrefix;
  }
  
  public void setAccountNumberPrefix(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this._accountNumberPrefix = paramString.trim();
    } else {
      this._accountNumberPrefix = "AccountNumber";
    }
  }
  
  public void setAccountTypePrefix(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this._accountTypePrefix = paramString.trim();
    } else {
      this._accountTypePrefix = "AccountType";
    }
  }
  
  public void setNumberOfAccounts(String paramString)
  {
    try
    {
      this._numAccounts = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this._numAccounts = 0;
    }
  }
  
  public void setContactPhoneNumber(String paramString)
  {
    if (paramString != null) {
      this._contactNumber = paramString.trim();
    } else {
      this._contactNumber = null;
    }
  }
  
  public void setQuestion(String paramString)
  {
    if (paramString != null) {
      this._question = paramString.trim();
    } else {
      this._question = null;
    }
  }
  
  public void setExtraValidation(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this._extraValidationFields = paramString.trim();
    } else {
      this._extraValidationFields = null;
    }
  }
  
  public String getContactPhoneNumber()
  {
    return this._contactNumber;
  }
  
  public String getQuestion()
  {
    return this._question;
  }
  
  public void setValidate(String paramString)
  {
    this._validateFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this._processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  protected boolean validate(SecureUser paramSecureUser, HttpSession paramHttpSession)
  {
    boolean bool = false;
    int i = 0;
    String str1 = null;
    String str2 = null;
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BankingAccounts");
    localAccounts.setFilter("All");
    for (int j = 0; (j < this._numAccounts) && (this.error == 0); j++)
    {
      str1 = this._accountNumbers[j];
      i = this._accountTypes[j];
      if ((str1 != null) && (str1.trim().length() > 0))
      {
        if (i >= 0)
        {
          bool = true;
        }
        else
        {
          bool = false;
          this.error = 100120;
        }
      }
      else if (i >= 0)
      {
        bool = false;
        this.error = 100120;
      }
      if ((this.error == 0) && (localAccounts.getByAccountNumberAndType(str1, i) != null))
      {
        bool = false;
        this.error = 100128;
      }
    }
    if (!bool)
    {
      if (this.error == 0) {
        this.error = 100104;
      }
    }
    else if ((this._contactNumber != null) && (this._contactNumber.length() > 0)) {
      try
      {
        str2 = Util.validatePhoneFormat(paramSecureUser, paramSecureUser.getLocale().getISO3Country(), this._contactNumber, new HashMap());
        if ((str2 == null) || (str2 == "")) {
          this.error = 100114;
        }
      }
      catch (Exception localException)
      {
        this.error = 100114;
      }
    }
    if ((this.error == 0) && (this._extraValidationFields != null) && (this._extraValidationFields.length() != 0))
    {
      if ((this._extraValidationFields.indexOf("PHONE") >= 0) && ((this._contactNumber == null) || (this._contactNumber.length() == 0))) {
        this.error = 100113;
      }
      if ((this._extraValidationFields.indexOf("QUESTION") >= 0) && ((this._question == null) || (this._question.length() == 0))) {
        this.error = 100105;
      }
    }
    if (this.error != 0) {
      bool = false;
    }
    return bool;
  }
  
  private boolean K()
  {
    boolean bool = true;
    if ((this._question != null) && (this._question.length() > this.maxSize)) {
      this.error = 8077;
    }
    if (this.error != 0) {
      bool = false;
    }
    return bool;
  }
  
  protected ILocalizable createBody(Locale paramLocale)
  {
    LocalizableString localLocalizableString1 = null;
    LocalizableString localLocalizableString2 = null;
    int i = 0;
    int j = 0;
    Object[] arrayOfObject = null;
    String str = null;
    StringBuffer localStringBuffer = new StringBuffer();
    arrayOfObject = new Object[3];
    for (int k = 0; k < this._numAccounts; k++)
    {
      str = this._accountNumbers[k];
      i = this._accountTypes[k];
      if ((str != null) && (str.trim().length() > 0) && (i >= 0))
      {
        j++;
        arrayOfObject[0] = String.valueOf(j);
        try
        {
          localLocalizableString2 = new LocalizableString("com.ffusion.beans.accounts.resources", "AccountType" + i, null);
          arrayOfObject[1] = localLocalizableString2.localize(paramLocale);
        }
        catch (Exception localException)
        {
          localLocalizableString2 = new LocalizableString("com.ffusion.beans.accounts.resources", "AccountType" + String.valueOf(0), null);
          arrayOfObject[1] = localLocalizableString2.localize(paramLocale);
        }
        arrayOfObject[2] = str.trim();
        localLocalizableString2 = new LocalizableString("com.ffusion.tasks.service_center", "AddOnlineBankingAccountStartComment", arrayOfObject);
        localStringBuffer.append(localLocalizableString2.localize(paramLocale));
      }
    }
    arrayOfObject = new Object[2];
    arrayOfObject[0] = localStringBuffer.toString();
    arrayOfObject[1] = this._contactNumber;
    localLocalizableString1 = new LocalizableString("com.ffusion.tasks.service_center", "AddOnlineBankingAccountFullComment", arrayOfObject);
    return localLocalizableString1;
  }
  
  public void setMaxMessageSize(String paramString)
  {
    this.maxSize = Integer.valueOf(paramString).intValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendAddOnlineBankingAccountMessage
 * JD-Core Version:    0.7.0.1
 */