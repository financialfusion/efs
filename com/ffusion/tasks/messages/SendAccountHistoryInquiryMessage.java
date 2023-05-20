package com.ffusion.tasks.messages;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.messages.Message;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.CommBankIdentifier;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.InvalidDateTimeException;
import com.ffusion.util.beans.LocalizableCurrency;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendAccountHistoryInquiryMessage
  extends BaseTask
{
  public static final String SEND_ACCOUNT_HISTORY_INQUIRY_COMMENT = "SendAccountHistoryInquiryComment";
  protected Currency _amount = null;
  protected DateTime _date = null;
  protected int _transactionType = -1;
  protected String _accountName = "Account";
  protected String _accountNumber = null;
  protected String _amountStr = null;
  protected String _dateFormat = "SHORT";
  protected String _dateStr = null;
  protected String _description = null;
  protected String _question = null;
  protected String _referenceNumber = null;
  protected String _routingNumber = null;
  protected String _transactionName = "Transaction";
  protected boolean _processFlag = false;
  protected boolean _validateFlag = true;
  protected String _extraValidationFields = "QUESTION";
  protected int maxSize = 1024;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    Account localAccount = null;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    LocalizableString localLocalizableString = null;
    Locale localLocale = null;
    LocalizableCurrency localLocalizableCurrency = null;
    LocalizableDate localLocalizableDate = null;
    Message localMessage = null;
    Object[] arrayOfObject = null;
    String str1 = this.successURL;
    String str2 = null;
    Transaction localTransaction = null;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localTransaction = (Transaction)localHttpSession.getAttribute(this._transactionName);
    if (localSecureUser == null)
    {
      this.error = 38;
    }
    else if (localTransaction != null)
    {
      localAccount = (Account)localHttpSession.getAttribute(this._accountName);
      if (localAccount == null)
      {
        this.error = 100118;
      }
      else
      {
        setAccountNumber(localAccount.getNumber());
        setRoutingNumber(localAccount.getRoutingNum());
        this._transactionType = localTransaction.getTypeValue();
        setDescription(localTransaction.getDescription());
        setReferenceNumber(localTransaction.getReferenceNumber());
        setAmount(localTransaction.getAmount());
        setDate(localTransaction.getDate());
      }
    }
    if ((this.error == 0) && (this._validateFlag)) {
      validate(localSecureUser);
    }
    if (this.error != 0)
    {
      str1 = this.taskErrorURL;
    }
    else if (this._processFlag)
    {
      localLocale = localSecureUser.getLocale();
      if ((this._question != null) && (this._question.length() > this.maxSize))
      {
        this.error = 8077;
        str1 = this.taskErrorURL;
        return str1;
      }
      try
      {
        localMessage = new Message(localLocale);
        ServiceCenterTask.setupServiceCenterMessage(localSecureUser, localMessage, "2", localHashMap1);
        localLocalizableCurrency = new LocalizableCurrency(this._amount);
        localLocalizableDate = new LocalizableDate(this._date, false);
        localHashMap2 = Banking.getTransactionTypes(localLocale, new HashMap());
        if ((this._referenceNumber == null) || (this._referenceNumber.length() == 0))
        {
          localLocalizableString = new LocalizableString("com.ffusion.tasks.service_center", "NoReferenceNum", null);
          str2 = (String)localLocalizableString.localize(localLocale);
        }
        else
        {
          str2 = this._referenceNumber;
        }
        arrayOfObject = new Object[7];
        arrayOfObject[0] = this._routingNumber;
        arrayOfObject[1] = this._accountNumber;
        arrayOfObject[2] = localLocalizableDate.localize(localLocale);
        arrayOfObject[3] = localLocalizableCurrency.localize(localLocale);
        arrayOfObject[4] = ((String)localHashMap2.get(new Integer(this._transactionType)));
        arrayOfObject[5] = str2;
        arrayOfObject[6] = this._description;
        localLocalizableString = new LocalizableString("com.ffusion.tasks.service_center", "SendAccountHistoryInquiryComment", arrayOfObject);
        localMessage.setComment((String)localLocalizableString.localize(localSecureUser.getLocale()));
        localMessage.setMemo(this._question);
        Messages.sendMessage(localSecureUser, localMessage, localHashMap1);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  public void setAmount(String paramString)
  {
    if (paramString != null) {
      this._amountStr = paramString.trim();
    } else {
      this._amountStr = null;
    }
  }
  
  public void setDate(String paramString)
  {
    if (paramString != null) {
      this._dateStr = paramString.trim();
    } else {
      this._dateStr = null;
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
  
  public void setDescription(String paramString)
  {
    if (paramString != null) {
      this._description = paramString.trim();
    } else {
      this._description = null;
    }
  }
  
  public void setReferenceNumber(String paramString)
  {
    if (paramString != null) {
      this._referenceNumber = paramString.trim();
    } else {
      this._referenceNumber = null;
    }
  }
  
  public void setAccountNumber(String paramString)
  {
    if (paramString != null) {
      this._accountNumber = paramString.trim();
    } else {
      this._accountNumber = null;
    }
  }
  
  public void setRoutingNumber(String paramString)
  {
    if (paramString != null) {
      this._routingNumber = paramString.trim();
    } else {
      this._routingNumber = null;
    }
  }
  
  public void setTransactionType(String paramString)
  {
    try
    {
      this._transactionType = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this._transactionType = -1;
    }
  }
  
  public void setDateFormat(String paramString)
  {
    if (paramString != null)
    {
      this._dateFormat = paramString;
      if (this._date != null) {
        this._date.setFormat(paramString);
      }
    }
  }
  
  public void setTransactionName(String paramString)
  {
    if (paramString != null) {
      this._transactionName = paramString.trim();
    } else {
      this._transactionName = "Transaction";
    }
  }
  
  public void setAccountName(String paramString)
  {
    if (paramString != null) {
      this._accountName = paramString.trim();
    } else {
      this._accountName = "Account";
    }
  }
  
  public String getAmount()
  {
    return this._amountStr;
  }
  
  public String getDate()
  {
    return this._dateStr;
  }
  
  public String getQuestion()
  {
    return this._question;
  }
  
  public String getRoutingNumber()
  {
    return this._routingNumber;
  }
  
  public String getAccountNumber()
  {
    return this._accountNumber;
  }
  
  public String getDescription()
  {
    return this._description;
  }
  
  public int getTransactionType()
  {
    return this._transactionType;
  }
  
  public String getReferenceNumber()
  {
    return this._referenceNumber;
  }
  
  public void setValidate(String paramString)
  {
    this._validateFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this._processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setExtraValidation(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this._extraValidationFields = paramString.trim();
    } else {
      this._extraValidationFields = null;
    }
  }
  
  protected boolean validate(SecureUser paramSecureUser)
  {
    boolean bool1 = true;
    Locale localLocale = null;
    boolean bool2 = false;
    if (paramSecureUser != null)
    {
      localLocale = paramSecureUser.getLocale();
      if (this._transactionType < 0)
      {
        this.error = 100115;
      }
      else if ((this._routingNumber == null) || (this._routingNumber.length() == 0))
      {
        this.error = 100000;
      }
      else
      {
        try
        {
          bool2 = CommBankIdentifier.isValidCheckZeros(this._routingNumber);
        }
        catch (Exception localException)
        {
          bool2 = false;
        }
        if (!bool2)
        {
          this.error = 100008;
        }
        else if ((this._accountNumber == null) || (this._accountNumber.length() == 0))
        {
          this.error = 100001;
        }
        else if ((this._dateStr == null) || (this._dateStr.length() == 0))
        {
          this.error = 100102;
        }
        else
        {
          if ((this._amountStr == null) || (this._amountStr.length() == 0))
          {
            this.error = 100100;
          }
          else
          {
            this._amount = new Currency(this._amountStr, paramSecureUser.getBaseCurrency(), localLocale);
            if (!this._amount.validateCurrency(this._amountStr)) {
              this.error = 100101;
            } else if ((this._extraValidationFields != null) && (this._extraValidationFields.length() != 0)) {
              if ((this._extraValidationFields.indexOf("QUESTION") >= 0) && ((this._question == null) || (this._question.length() == 0))) {
                this.error = 8007;
              } else if ((this._extraValidationFields.indexOf("TRANS_DESCRIPTION") >= 0) && ((this._description == null) || (this._description.length() == 0))) {
                this.error = 100116;
              }
            }
          }
          try
          {
            this._date = new DateTime(this._dateStr, localLocale, this._dateFormat);
            if (!ServiceCenterTask.checkSearchDate(this._date, localLocale)) {
              this.error = 100124;
            }
          }
          catch (InvalidDateTimeException localInvalidDateTimeException)
          {
            this.error = 100103;
          }
        }
      }
    }
    if (this.error != 0) {
      bool1 = false;
    }
    return bool1;
  }
  
  public void setMaxMessageSize(String paramString)
  {
    this.maxSize = Integer.valueOf(paramString).intValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendAccountHistoryInquiryMessage
 * JD-Core Version:    0.7.0.1
 */