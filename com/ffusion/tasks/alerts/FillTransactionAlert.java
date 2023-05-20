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

public class FillTransactionAlert
  extends FillUserAlert
{
  protected String _account;
  protected String _tranType;
  protected String _comparisonType;
  protected String _amount;
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
        this._amount = ((AccountAlert)localObject).getAdditionalProperty("AMOUNT");
        this._tranType = ((AccountAlert)localObject).getAdditionalProperty("TRANSACTION TYPE");
        this._comparisonType = ((AccountAlert)localObject).getAdditionalProperty("COMPARE");
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
    if (jdMethod_void(localHttpSession))
    {
      AccountAlert localAccountAlert;
      if (this._updateExisitngAlertBean) {
        localAccountAlert = (AccountAlert)localHttpSession.getAttribute(this._alertSessionKey);
      } else {
        localAccountAlert = new AccountAlert();
      }
      localAccountAlert.setAlertType(3);
      localAccountAlert.setStatus(1);
      localAccountAlert.setDirectoryId(((SecureUser)localObject).getProfileID());
      localAccountAlert.setOneTime(this._sendOnce);
      if ((this._tranType != null) && (this._tranType.length() > 0)) {
        localAccountAlert.setAdditionalProperty("TRANSACTION TYPE", this._tranType);
      }
      localAccountAlert.setAdditionalProperty("COMPARE", this._comparisonType);
      if ((this._amount != null) && (this._amount.trim().length() > 0)) {
        localAccountAlert.setAdditionalProperty("AMOUNT", this._amount);
      } else {
        localAccountAlert.removeAdditionalProperty("AMOUNT");
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
  
  private boolean jdMethod_void(HttpSession paramHttpSession)
  {
    double d = 0.0D;
    if (!this._comparisonType.equals("1")) {
      if ((this._amount != null) && (this._amount.trim().length() > 0))
      {
        try
        {
          Currency localCurrency = new Currency(this._amount, Locale.getDefault());
          if (localCurrency.validateCurrency(this._amount))
          {
            d = localCurrency.getAmountValue().doubleValue();
          }
          else
          {
            this.error = 19120;
            return false;
          }
        }
        catch (Exception localException)
        {
          this.error = 19120;
          return false;
        }
        if (d < 0.0D)
        {
          this.error = 19120;
          return false;
        }
      }
      else
      {
        this.error = 19120;
        return false;
      }
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
  
  public void setAmount(String paramString)
  {
    this._amount = paramString;
  }
  
  public String getAmount()
  {
    return this._amount;
  }
  
  public void setTransactionType(String paramString)
  {
    this._tranType = paramString;
  }
  
  public String getTransactionType()
  {
    return this._tranType;
  }
  
  public void setComparisonType(String paramString)
  {
    this._comparisonType = paramString;
  }
  
  public String getComparisonType()
  {
    return this._comparisonType;
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
 * Qualified Name:     com.ffusion.tasks.alerts.FillTransactionAlert
 * JD-Core Version:    0.7.0.1
 */