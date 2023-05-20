package com.ffusion.beans.alerts;

import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

public class UserAlert
  extends ExtendABean
{
  public static final String USER_ALERT = "USER_ALERT";
  public static final String ALERT_TYPE = "ALERT_TYPE";
  public static final String DIRECTORY_ID = "DIRECTORY_ID";
  public static final String ONE_TIME = "ONE_TIME";
  public static final String STATUS = "STATUS";
  public static final String USER_ALERT_ID = "USER_ALERT_ID";
  public static final String ADDITIONAL_PROPERTIES = "ADDITIONAL_PROPERTIES";
  public static final String ADDITIONAL_PROPERTY = "ADDITIONAL_PROPERTY";
  public static final String ALERTS_RESOURCE_BUNDLE = "com.ffusion.beans.alerts.resources";
  public static final String USER_ALERT_TYPE_PREFIX = "UserAlertType_";
  public static final int ALERT_TYPE_ACCOUNT_BALANCE = 1;
  public static final int ALERT_TYPE_ACCOUNT_SUMMARY = 2;
  public static final int ALERT_TYPE_TRANSACTION = 3;
  public static final int ALERT_TYPE_NSF = 4;
  public static final int ALERT_TYPE_STOCK = 5;
  public static final int ALERT_TYPE_BANK_MESSAGE = 6;
  public static final int ALERT_STATUS_ACTIVE = 1;
  public static final int ALERT_STATUS_INACTIVE = 0;
  public static final String KEY_PORTFOLIO_STOCK_LIST = "PortfolioStockList";
  public static final String KEY_FREEFORM_STOCK_LIST = "FreeFormStockList";
  public static final String DELIVERY_INFO_KEY_CONTACT_POINT_ID = "ContactPointID";
  public static final int INVALID_INT = -2147483648;
  protected int _alertType = -2147483648;
  protected DeliveryInfos _deliveryInfos = null;
  protected int _directoryId = -2147483648;
  protected boolean _oneTime = false;
  protected int _status = -2147483648;
  protected int _userAlertId = -2147483648;
  protected Properties _additionalProperties = new Properties();
  
  public UserAlert() {}
  
  public UserAlert(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public void setAlertType(int paramInt)
  {
    this._alertType = paramInt;
  }
  
  public void setAlertType(String paramString)
  {
    try
    {
      this._alertType = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this._alertType = -2147483648;
    }
  }
  
  public String getAlertType()
  {
    return String.valueOf(this._alertType);
  }
  
  public String getAlertTypeForLogs()
  {
    switch (this._alertType)
    {
    case 1: 
      return "AccountBalance";
    case 6: 
      return "BankMessage";
    case 4: 
      return "NSF";
    case 5: 
      return "StockPortfolio";
    case 2: 
      return "ACCOUNTSUMMARY";
    case 3: 
      return "Transaction";
    }
    return Integer.toString(this._alertType);
  }
  
  public int getAlertTypeValue()
  {
    return this._alertType;
  }
  
  public void setDeliveryInfos(DeliveryInfos paramDeliveryInfos)
  {
    this._deliveryInfos = paramDeliveryInfos;
  }
  
  public DeliveryInfos getDeliveryInfos()
  {
    return this._deliveryInfos;
  }
  
  public void setDirectoryId(int paramInt)
  {
    this._directoryId = paramInt;
  }
  
  public void setDirectoryId(String paramString)
  {
    try
    {
      this._directoryId = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this._directoryId = -2147483648;
    }
  }
  
  public String getDirectoryId()
  {
    return String.valueOf(this._directoryId);
  }
  
  public int getDirectoryIdValue()
  {
    return this._directoryId;
  }
  
  public void setOneTime(boolean paramBoolean)
  {
    this._oneTime = paramBoolean;
  }
  
  public void setOneTime(String paramString)
  {
    this._oneTime = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getOneTime()
  {
    return String.valueOf(this._oneTime);
  }
  
  public boolean getOneTimeValue()
  {
    return this._oneTime;
  }
  
  public void setAdditionalProperty(String paramString1, String paramString2)
  {
    this._additionalProperties.setProperty(paramString1, paramString2);
  }
  
  public String getAdditionalProperty(String paramString)
  {
    return this._additionalProperties.getProperty(paramString);
  }
  
  public void removeAdditionalProperty(String paramString)
  {
    this._additionalProperties.remove(paramString);
  }
  
  public Properties getAdditionalProperties()
  {
    return this._additionalProperties;
  }
  
  public void setAdditionalProperites(Properties paramProperties)
  {
    this._additionalProperties = paramProperties;
  }
  
  public void setStatus(int paramInt)
  {
    this._status = paramInt;
  }
  
  public void setStatus(String paramString)
  {
    try
    {
      this._status = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this._status = -2147483648;
    }
  }
  
  public String getStatus()
  {
    return String.valueOf(this._status);
  }
  
  public int getStatusValue()
  {
    return this._status;
  }
  
  public void setUserAlertId(int paramInt)
  {
    this._userAlertId = paramInt;
  }
  
  public void setUserAlertId(String paramString)
  {
    try
    {
      this._userAlertId = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this._userAlertId = -2147483648;
    }
  }
  
  public String getUserAlertId()
  {
    return String.valueOf(this._userAlertId);
  }
  
  public int getUserAlertIdValue()
  {
    return this._userAlertId;
  }
  
  public String getDisplayText()
  {
    String str = "UserAlertType_" + this._alertType;
    return ResourceUtil.getString(str, "com.ffusion.beans.alerts.resources", this.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ALERT_TYPE")) {
      try
      {
        this._alertType = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        this._alertType = -2147483648;
        bool = false;
      }
    } else if (paramString1.equals("DIRECTORY_ID")) {
      try
      {
        this._directoryId = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        this._directoryId = -2147483648;
        bool = false;
      }
    } else if (paramString1.equals("ONE_TIME")) {
      this._oneTime = Boolean.valueOf(paramString2).booleanValue();
    } else if (paramString1.equals("STATUS")) {
      try
      {
        this._status = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException3)
      {
        this._status = -2147483648;
        bool = false;
      }
    } else if (paramString1.equals("USER_ALERT_ID")) {
      try
      {
        this._userAlertId = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException4)
      {
        this._userAlertId = -2147483648;
        bool = false;
      }
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "USER_ALERT");
    XMLHandler.appendTag(localStringBuffer, "ALERT_TYPE", this._alertType);
    XMLHandler.appendTag(localStringBuffer, "DIRECTORY_ID", this._directoryId);
    XMLHandler.appendTag(localStringBuffer, "ONE_TIME", String.valueOf(this._oneTime));
    XMLHandler.appendTag(localStringBuffer, "STATUS", this._status);
    XMLHandler.appendTag(localStringBuffer, "USER_ALERT_ID", this._userAlertId);
    if (this._deliveryInfos != null) {
      localStringBuffer.append(this._deliveryInfos.getXML());
    }
    XMLHandler.appendBeginTag(localStringBuffer, "ADDITIONAL_PROPERTIES");
    Iterator localIterator = this._additionalProperties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = (String)this._additionalProperties.get(str1);
      XMLHandler.appendBeginTag(localStringBuffer, "ADDITIONAL_PROPERTY");
      XMLHandler.appendTag(localStringBuffer, "NAME", str1);
      XMLHandler.appendTag(localStringBuffer, "VALUE", str2);
      XMLHandler.appendEndTag(localStringBuffer, "ADDITIONAL_PROPERTY");
    }
    XMLHandler.appendEndTag(localStringBuffer, "ADDITIONAL_PROPERTIES");
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "USER_ALERT");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new b(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new b());
  }
  
  class a
    extends XMLHandler
  {
    private static final int jdField_byte = -1;
    private static final int jdField_new = 1;
    private static final int jdField_case = 2;
    private int jdField_int = -1;
    private String jdField_try;
    
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("NAME")) {
        this.jdField_int = 1;
      } else if (paramString.equals("VALUE")) {
        this.jdField_int = 2;
      } else {
        this.jdField_int = -1;
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
      throws Exception
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if (this.jdField_int == 1)
      {
        this.jdField_try = str;
        this.jdField_int = -1;
      }
      else if (this.jdField_int == 2)
      {
        UserAlert.this.setAdditionalProperty(this.jdField_try, str);
        this.jdField_int = -1;
      }
    }
  }
  
  class b
    extends ExtendABean.InternalXMLHandler
  {
    String jdField_int;
    String jdField_new;
    boolean jdField_try = false;
    
    public b()
    {
      super();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("DELIVERYINFOS"))
      {
        this.jdField_try = false;
        DeliveryInfos localDeliveryInfos = new DeliveryInfos();
        localDeliveryInfos.continueXMLParsing(getHandler());
        UserAlert.this._deliveryInfos = localDeliveryInfos;
      }
      else if (paramString.equals("ADDITIONAL_PROPERTIES"))
      {
        getHandler().continueWith(new UserAlert.a(UserAlert.this));
      }
      else if (paramString.equals("EXTENDEDDATA"))
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.alerts.UserAlert
 * JD-Core Version:    0.7.0.1
 */