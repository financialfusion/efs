package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.CommBankIdentifier;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendAddBillPayAccountMessage
  extends BaseTask
{
  public static final String ADD_BILL_PAY_ACCOUNT_COMMENT_KEY = "AddBillPayAccountComment";
  public static final String ADD_BILL_PAY_ACCOUNT_MEMO_KEY = "AddBillPayAccountMemo";
  protected String _accountNumber = null;
  protected String _routingNumber = null;
  protected boolean _processFlag = false;
  protected boolean _validateFlag = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
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
      validateRequiredInfo();
    }
    if (this.error != 0) {
      str = this.taskErrorURL;
    } else if (this._processFlag) {
      try
      {
        localMessage = new Message(localSecureUser.getLocale());
        ServiceCenterTask.setupServiceCenterMessage(localSecureUser, localMessage, "23", localHashMap);
        arrayOfObject = new Object[2];
        arrayOfObject[0] = this._routingNumber;
        arrayOfObject[1] = this._accountNumber;
        localLocalizableString = new LocalizableString("com.ffusion.tasks.service_center", "AddBillPayAccountComment", arrayOfObject);
        localMessage.setComment((String)localLocalizableString.localize(localSecureUser.getLocale()));
        localLocalizableString = new LocalizableString("com.ffusion.tasks.service_center", "AddBillPayAccountMemo", null);
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
  
  public String getAccountNumber()
  {
    return this._accountNumber;
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
  
  protected boolean validateRequiredInfo()
  {
    boolean bool1 = true;
    boolean bool2 = false;
    if ((this._accountNumber == null) || (this._accountNumber.length() == 0))
    {
      this.error = 100001;
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
      if (!bool2) {
        this.error = 100008;
      }
    }
    if (this.error != 0) {
      bool1 = false;
    }
    return bool1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendAddBillPayAccountMessage
 * JD-Core Version:    0.7.0.1
 */