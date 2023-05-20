package com.ffusion.tasks.alerts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.AccountAlert;
import com.ffusion.beans.user.ContactPoints;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FillBalanceAlert
  extends FillUserAlert
{
  protected String _account;
  protected String _minAmount;
  protected String _maxAmount;
  protected String _sendOnce;
  protected boolean _init = false;
  protected boolean _updateExisitngAlertBean = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this._init)
    {
      localObject = (AccountAlert)localHttpSession.getAttribute(this._alertSessionKey);
      if (localObject == null)
      {
        this.error = 19001;
        str = this.taskErrorURL;
      }
      else
      {
        this._sendOnce = ((AccountAlert)localObject).getOneTime();
        this._minAmount = ((AccountAlert)localObject).getAdditionalProperty("MIN_AMOUNT");
        this._maxAmount = ((AccountAlert)localObject).getAdditionalProperty("MAX_AMOUNT");
        if (((AccountAlert)localObject).getAccountId() == null) {
          this._account = "All Accounts";
        } else {
          this._account = (((AccountAlert)localObject).getRoutingNumber() + ";" + ((AccountAlert)localObject).getAccountId() + ";" + ((AccountAlert)localObject).getAccountType());
        }
        initDeliveryInfos(localHttpSession);
      }
      return str;
    }
    Object localObject = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ContactPoints localContactPoints = (ContactPoints)localHttpSession.getAttribute(this._contactPointsCollections);
    if (localContactPoints == null)
    {
      this.error = 19307;
      return this.taskErrorURL;
    }
    if (jdMethod_goto(localHttpSession))
    {
      AccountAlert localAccountAlert;
      if (this._updateExisitngAlertBean) {
        localAccountAlert = (AccountAlert)localHttpSession.getAttribute(this._alertSessionKey);
      } else {
        localAccountAlert = new AccountAlert();
      }
      localAccountAlert.setAlertType(1);
      localAccountAlert.setStatus(1);
      localAccountAlert.setDirectoryId(((SecureUser)localObject).getProfileID());
      localAccountAlert.setOneTime(this._sendOnce);
      if ((this._minAmount != null) && (this._minAmount.trim().length() > 0)) {
        localAccountAlert.setAdditionalProperty("MIN_AMOUNT", this._minAmount);
      } else {
        localAccountAlert.removeAdditionalProperty("MIN_AMOUNT");
      }
      if ((this._maxAmount != null) && (this._maxAmount.trim().length() > 0)) {
        localAccountAlert.setAdditionalProperty("MAX_AMOUNT", this._maxAmount);
      } else {
        localAccountAlert.removeAdditionalProperty("MAX_AMOUNT");
      }
      localAccountAlert.setDeliveryInfos(constructDeliveryInfos(localHttpSession));
      if (!this._account.equals("All Accounts"))
      {
        String[] arrayOfString = this._account.split(";");
        localAccountAlert.setRoutingNumber(arrayOfString[0]);
        localAccountAlert.setAccountId(arrayOfString[1]);
        localAccountAlert.setAccountType(arrayOfString[2]);
      }
      else
      {
        localAccountAlert.setRoutingNumber(null);
        localAccountAlert.setAccountId(null);
        localAccountAlert.setAccountType(-2147483648);
      }
      localHttpSession.setAttribute(this._alertSessionKey, localAccountAlert);
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  private boolean jdMethod_goto(HttpSession paramHttpSession)
  {
    int i = 0;
    double d1 = 0.0D;
    double d2 = 0.0D;
    if ((this._minAmount != null) && (this._minAmount.trim().length() > 0))
    {
      i = 1;
      try
      {
        Currency localCurrency1 = new Currency(this._minAmount, Locale.getDefault());
        if (localCurrency1.validateCurrency(this._minAmount))
        {
          d1 = localCurrency1.getAmountValue().doubleValue();
        }
        else
        {
          this.error = 19110;
          return false;
        }
      }
      catch (Exception localException1)
      {
        this.error = 19110;
        return false;
      }
      if (d1 < 0.0D)
      {
        this.error = 19110;
        return false;
      }
    }
    if ((this._maxAmount != null) && (this._maxAmount.trim().length() > 0))
    {
      i = 1;
      try
      {
        Currency localCurrency2 = new Currency(this._maxAmount, Locale.getDefault());
        if (localCurrency2.validateCurrency(this._maxAmount))
        {
          d2 = localCurrency2.getAmountValue().doubleValue();
        }
        else
        {
          this.error = 19111;
          return false;
        }
      }
      catch (Exception localException2)
      {
        this.error = 19111;
        return false;
      }
      if (d2 < 0.0D)
      {
        this.error = 19111;
        return false;
      }
    }
    if (i == 0)
    {
      this.error = 19116;
      return false;
    }
    if ((d2 != 0.0D) && (d1 > d2))
    {
      this.error = 19117;
      return false;
    }
    if ((this._sendOnce == null) || (this._sendOnce.length() == 0) || ((!this._sendOnce.equalsIgnoreCase("true")) && (!this._sendOnce.equalsIgnoreCase("false"))))
    {
      this.error = 19112;
      return false;
    }
    return validateDeliveryInfos(paramHttpSession);
  }
  
  public void setAccount(String paramString)
  {
    this._account = paramString;
  }
  
  public String getAccount()
  {
    return this._account;
  }
  
  public void setMinAmount(String paramString)
  {
    this._minAmount = paramString;
  }
  
  public String getMinAmount()
  {
    return this._minAmount;
  }
  
  public void setMaxAmount(String paramString)
  {
    this._maxAmount = paramString;
  }
  
  public String getMaxAmount()
  {
    return this._maxAmount;
  }
  
  public void setSendOnce(String paramString)
  {
    this._sendOnce = paramString;
  }
  
  public String getSendOnce()
  {
    return this._sendOnce;
  }
  
  public void setInit(String paramString)
  {
    try
    {
      this._init = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public String getInit()
  {
    return Boolean.toString(this._init);
  }
  
  public void setUpdateExisitngAlertBean(String paramString)
  {
    try
    {
      this._updateExisitngAlertBean = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public String getUpdateExisitngAlertBean()
  {
    return Boolean.toString(this._updateExisitngAlertBean);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.FillBalanceAlert
 * JD-Core Version:    0.7.0.1
 */