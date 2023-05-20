package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.CommBankIdentifier;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendOrderCopyClearedChecksMessage
  extends BaseTask
{
  public static final String SEND_ORDER_COPY_CLEARED_CHECKS_FULL_COMMENT_KEY = "OrderClearedChecksFullComment";
  public static final String SEND_ORDER_COPY_CLEARED_CHECKS_MEMO_KEY = "OrderClearedChecksMemo";
  public static final String SEND_ORDER_COPY_CLEARED_CHECKS_NUMBERS_KEY = "OrderClearedChecksNumbersComment";
  protected int _deliveryMethod = 0;
  protected int _numChecks = 6;
  protected String _accountNumber = null;
  protected String _checkNumberPrefix = "CheckNumber";
  protected String _faxNumber = null;
  protected String _routingNumber = null;
  protected String[] _checkNumbers = null;
  protected boolean _processFlag = false;
  protected boolean _validateFlag = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Object localObject = null;
    Message localMessage = null;
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this._checkNumbers = new String[this._numChecks];
    for (int i = 0; i < this._numChecks; i++) {
      this._checkNumbers[i] = ((String)localHttpSession.getAttribute(this._checkNumberPrefix + String.valueOf(i + 1)));
    }
    if (localSecureUser == null) {
      this.error = 38;
    } else if (this._validateFlag) {
      validateRequiredInfo(localSecureUser);
    }
    if (this.error != 0) {
      str = this.taskErrorURL;
    } else if (this._processFlag) {
      try
      {
        localMessage = new Message(localSecureUser.getLocale());
        ServiceCenterTask.setupServiceCenterMessage(localSecureUser, localMessage, "25", localHashMap);
        localObject = createBody(localSecureUser.getLocale());
        localMessage.setComment((String)((ILocalizable)localObject).localize(localSecureUser.getLocale()));
        localObject = new LocalizableString("com.ffusion.tasks.service_center", "OrderClearedChecksMemo", null);
        localMessage.setMemo((String)((ILocalizable)localObject).localize(localSecureUser.getLocale()));
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
  
  public void setNumberOfChecks(String paramString)
  {
    try
    {
      this._numChecks = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this._numChecks = 0;
    }
  }
  
  public void setFaxNumber(String paramString)
  {
    if (paramString != null) {
      this._faxNumber = paramString.trim();
    } else {
      this._faxNumber = null;
    }
  }
  
  public void setDeliveryMethod(String paramString)
  {
    try
    {
      this._deliveryMethod = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this._deliveryMethod = 0;
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
  
  public void setCheckNumberPrefix(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this._checkNumberPrefix = paramString.trim();
    } else {
      this._checkNumberPrefix = "AccountType";
    }
  }
  
  public int getNumberOfChecks()
  {
    return this._numChecks;
  }
  
  public String getAccountNumber()
  {
    return this._accountNumber;
  }
  
  public int getDeliveryMethod()
  {
    return this._deliveryMethod;
  }
  
  public String getCheckNumberPrefix()
  {
    return this._checkNumberPrefix;
  }
  
  public String getFaxNumber()
  {
    return this._faxNumber;
  }
  
  public String getRoutingNumber()
  {
    return this._routingNumber;
  }
  
  public void setValidate(String paramString)
  {
    this._validateFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this._processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  protected boolean validateRequiredInfo(SecureUser paramSecureUser)
  {
    boolean bool1 = true;
    String str = null;
    boolean bool2 = false;
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
      catch (Exception localException1)
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
      else
      {
        bool1 = false;
        for (int i = 0; (i < this._numChecks) && (!bool1); i++) {
          if ((this._checkNumbers[i] != null) && (this._checkNumbers[i].trim().length() > 0)) {
            bool1 = true;
          }
        }
        if (!bool1) {
          this.error = 100117;
        } else if (this._deliveryMethod == 0) {
          this.error = 100112;
        } else if (this._deliveryMethod == 2) {
          if ((this._faxNumber == null) || (this._faxNumber.length() == 0)) {
            this.error = 100110;
          } else {
            try
            {
              str = Util.validatePhoneFormat(paramSecureUser, paramSecureUser.getLocale().getISO3Country(), this._faxNumber, new HashMap());
              if ((str == null) || (str == "")) {
                this.error = 100111;
              }
            }
            catch (Exception localException2)
            {
              this.error = 100111;
            }
          }
        }
      }
    }
    if (this.error != 0) {
      bool1 = false;
    }
    return bool1;
  }
  
  protected ILocalizable createBody(Locale paramLocale)
  {
    LocalizableString localLocalizableString1 = null;
    LocalizableString localLocalizableString2 = null;
    int i = 0;
    Object[] arrayOfObject = null;
    String str1 = null;
    String str2 = null;
    StringBuffer localStringBuffer = new StringBuffer();
    arrayOfObject = new Object[1];
    arrayOfObject[0] = this._faxNumber;
    localLocalizableString2 = new LocalizableString("com.ffusion.tasks.service_center", "DeliveryMethod" + String.valueOf(this._deliveryMethod), arrayOfObject);
    str2 = (String)localLocalizableString2.localize(paramLocale);
    arrayOfObject = new Object[2];
    for (int j = 0; j < this._numChecks; j++)
    {
      str1 = this._checkNumbers[j];
      if ((str1 != null) && (str1.trim().length() > 0))
      {
        i++;
        arrayOfObject[0] = String.valueOf(i);
        arrayOfObject[1] = str1.trim();
        localLocalizableString2 = new LocalizableString("com.ffusion.tasks.service_center", "OrderClearedChecksNumbersComment", arrayOfObject);
        localStringBuffer.append(localLocalizableString2.localize(paramLocale));
      }
    }
    arrayOfObject = new Object[4];
    arrayOfObject[0] = this._routingNumber;
    arrayOfObject[1] = this._accountNumber;
    arrayOfObject[2] = localStringBuffer.toString();
    arrayOfObject[3] = str2;
    localLocalizableString1 = new LocalizableString("com.ffusion.tasks.service_center", "OrderClearedChecksFullComment", arrayOfObject);
    return localLocalizableString1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendOrderCopyClearedChecksMessage
 * JD-Core Version:    0.7.0.1
 */