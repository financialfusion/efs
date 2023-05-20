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
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.InvalidDateTimeException;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendOrderStatementCopyMessage
  extends BaseTask
{
  public static final String SEND_ORDER_STATEMENT_COPY_COMMENT = "SendOrderStatementCopyComment";
  public static final String SEND_ORDER_STATEMENT_COPY_MEMO = "SendOrderStatementCopyMemo";
  public static final String SEND_ORDER_STATEMENT_COPY_DELIVERY = "DeliveryMethod";
  protected DateTime _beginDate = null;
  protected DateTime _endDate = null;
  protected String _faxNumber = null;
  protected String _accountNumber = null;
  protected String _beginDateStr = null;
  protected String _dateFormat = "SHORT";
  protected int _deliveryMethod = 0;
  protected String _endDateStr = null;
  protected String _routingNumber = null;
  protected boolean _processFlag = false;
  protected boolean _validateFlag = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    Locale localLocale = null;
    LocalizableDate localLocalizableDate1 = null;
    LocalizableDate localLocalizableDate2 = null;
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
      validateRequiredInfo(localSecureUser);
    }
    if (this.error != 0)
    {
      str = this.taskErrorURL;
    }
    else if (this._processFlag)
    {
      localLocale = localSecureUser.getLocale();
      try
      {
        localMessage = new Message(localLocale);
        ServiceCenterTask.setupServiceCenterMessage(localSecureUser, localMessage, "26", localHashMap);
        localLocalizableDate1 = new LocalizableDate(this._beginDate, false);
        localLocalizableDate2 = new LocalizableDate(this._endDate, false);
        arrayOfObject = new Object[1];
        arrayOfObject[0] = this._faxNumber;
        localLocalizableString = new LocalizableString("com.ffusion.tasks.service_center", "DeliveryMethod" + String.valueOf(this._deliveryMethod), arrayOfObject);
        arrayOfObject = new Object[5];
        arrayOfObject[0] = this._routingNumber;
        arrayOfObject[1] = this._accountNumber;
        arrayOfObject[2] = localLocalizableDate1.localize(localLocale);
        arrayOfObject[3] = localLocalizableDate2.localize(localLocale);
        arrayOfObject[4] = localLocalizableString.localize(localLocale);
        localLocalizableString = new LocalizableString("com.ffusion.tasks.service_center", "SendOrderStatementCopyComment", arrayOfObject);
        localMessage.setComment((String)localLocalizableString.localize(localLocale));
        localLocalizableString = new LocalizableString("com.ffusion.tasks.service_center", "SendOrderStatementCopyMemo", null);
        localMessage.setMemo((String)localLocalizableString.localize(localSecureUser.getLocale()));
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
  
  public void setBeginDate(String paramString)
  {
    if (paramString != null) {
      this._beginDateStr = paramString.trim();
    } else {
      this._beginDateStr = null;
    }
  }
  
  public void setEndDate(String paramString)
  {
    if (paramString != null) {
      this._endDateStr = paramString.trim();
    } else {
      this._endDateStr = null;
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
  
  public void setDateFormat(String paramString)
  {
    if (paramString != null)
    {
      this._dateFormat = paramString;
      if (this._endDate != null) {
        this._endDate.setFormat(paramString);
      }
      if (this._beginDate != null) {
        this._beginDate.setFormat(paramString);
      }
    }
  }
  
  public String getBeginDate()
  {
    return this._beginDateStr;
  }
  
  public String getEndDate()
  {
    return this._endDateStr;
  }
  
  public String getRoutingNumber()
  {
    return this._routingNumber;
  }
  
  public String getAccountNumber()
  {
    return this._accountNumber;
  }
  
  public int getDeliveryMethod()
  {
    return this._deliveryMethod;
  }
  
  public String getFaxNumber()
  {
    return this._faxNumber.toString();
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
    Locale localLocale = null;
    String str = null;
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
        catch (Exception localException1)
        {
          bool2 = false;
        }
        if (!bool2) {
          this.error = 100008;
        } else if ((this._accountNumber == null) || (this._accountNumber.length() == 0)) {
          this.error = 100001;
        } else if ((this._beginDateStr == null) || (this._beginDateStr.length() == 0)) {
          this.error = 100106;
        } else {
          try
          {
            this._beginDate = new DateTime(this._beginDateStr, localLocale, this._dateFormat);
            if (!ServiceCenterTask.checkSearchDate(this._beginDate, localLocale)) {
              this.error = 100125;
            }
            if ((this._endDateStr == null) || (this._endDateStr.length() == 0)) {
              this.error = 100107;
            } else {
              try
              {
                this._endDate = new DateTime(this._endDateStr, localLocale, this._dateFormat);
                if (!ServiceCenterTask.checkSearchDate(this._endDate, localLocale)) {
                  this.error = 100126;
                }
                DateTime localDateTime = new DateTime(Calendar.getInstance(localLocale), localLocale);
                localDateTime.set(11, 0);
                localDateTime.clear(12);
                localDateTime.clear(13);
                localDateTime.clear(14);
                localDateTime.add(5, 1);
                if (this._endDate.compare(this._beginDate) < 0) {
                  this.error = 100119;
                } else if (localDateTime.compare(this._endDate) <= 0) {
                  this.error = 100126;
                } else if (this._deliveryMethod == 0) {
                  this.error = 100112;
                } else if (this._deliveryMethod == 2) {
                  if ((this._faxNumber == null) || (this._faxNumber.length() == 0)) {
                    this.error = 100110;
                  } else {
                    try
                    {
                      str = Util.validatePhoneFormat(paramSecureUser, localLocale.getISO3Country(), this._faxNumber, new HashMap());
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
              catch (InvalidDateTimeException localInvalidDateTimeException1)
              {
                this.error = 100109;
              }
            }
          }
          catch (InvalidDateTimeException localInvalidDateTimeException2)
          {
            this.error = 100108;
          }
        }
      }
    }
    if (this.error != 0) {
      bool1 = false;
    }
    return bool1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendOrderStatementCopyMessage
 * JD-Core Version:    0.7.0.1
 */