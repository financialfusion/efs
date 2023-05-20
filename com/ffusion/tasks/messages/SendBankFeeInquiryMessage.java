package com.ffusion.tasks.messages;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.csil.CSILException;
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

public class SendBankFeeInquiryMessage
  extends BaseTask
{
  public static final String SEND_BANK_FEE_INQUIRY_COMMENT = "SendBankFeeInquiryComment";
  protected Currency _amount = null;
  protected DateTime _date = null;
  protected String _accountNumber = null;
  protected String _amountStr = null;
  protected String _question = null;
  protected int maxSize = 1024;
  protected String _dateFormat = "SHORT";
  protected String _dateStr = null;
  protected String _routingNumber = null;
  protected boolean _processFlag = false;
  protected boolean _validateFlag = true;
  protected String _extraValidationFields = "QUESTION";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    Locale localLocale = null;
    LocalizableCurrency localLocalizableCurrency = null;
    LocalizableDate localLocalizableDate = null;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    LocalizableString localLocalizableString = null;
    Message localMessage = null;
    Object[] arrayOfObject = null;
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null) {
      this.error = 38;
    } else if (this._validateFlag) {
      validate(localSecureUser);
    }
    if (this.error != 0)
    {
      str = this.taskErrorURL;
    }
    else if (this._processFlag)
    {
      localLocale = localSecureUser.getLocale();
      if ((this._question != null) && (this._question.length() > this.maxSize))
      {
        this.error = 8077;
        return this.taskErrorURL;
      }
      try
      {
        localMessage = new Message(localLocale);
        ServiceCenterTask.setupServiceCenterMessage(localSecureUser, localMessage, "28", localHashMap);
        localLocalizableCurrency = new LocalizableCurrency(this._amount);
        localLocalizableDate = new LocalizableDate(this._date, false);
        arrayOfObject = new Object[4];
        arrayOfObject[0] = this._routingNumber;
        arrayOfObject[1] = this._accountNumber;
        arrayOfObject[2] = localLocalizableDate.localize(localLocale);
        arrayOfObject[3] = localLocalizableCurrency.localize(localLocale);
        localLocalizableString = new LocalizableString("com.ffusion.tasks.service_center", "SendBankFeeInquiryComment", arrayOfObject);
        localMessage.setComment((String)localLocalizableString.localize(localLocale));
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
      if ((this._routingNumber == null) || (this._routingNumber.length() == 0))
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
        else if ((this._dateStr == null) || (this._dateStr.length() <= 0))
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
            } else if ((this._amount.isNegative()) || (this._amount.getIsZero())) {
              this.error = 100101;
            } else if ((this._extraValidationFields != null) && (this._extraValidationFields.length() != 0) && (this._extraValidationFields.indexOf("QUESTION") >= 0) && ((this._question == null) || (this._question.length() == 0))) {
              this.error = 8007;
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
 * Qualified Name:     com.ffusion.tasks.messages.SendBankFeeInquiryMessage
 * JD-Core Version:    0.7.0.1
 */