package com.ffusion.beans.alerts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class Alert
  extends ExtendABean
  implements Serializable
{
  public static final String ALERT = "ALERT";
  public static final String PRIORITY = "PRIORITY";
  public static final String STARTDATE = "STARTDATE";
  public static final String ENDDATE = "ENDDATE";
  public static final String INTERVAL = "INTERVAL";
  public static final String TIMEZONE = "TIMEZONE";
  public static final String APPINFO = "APPINFO";
  public static final String MESSAGE = "MESSAGE";
  public static final String DELIVERYINFOS = "DELIVERYINFOS";
  public static final String AMOUNT = "Amount";
  public static final String MAXAMOUNT = "MaxAmount";
  public static final String MINAMOUNT = "MinAmount";
  public static final String CRITERIA = "Criteria";
  public static final String ALERTSTOCKS = "AlertStocks";
  public static final String RESPECTDST = "RespectDST";
  public static final String INTERVALTYPE = "IntervalType";
  public static final int SI_INTERVAL_ABSOLUTE = 0;
  public static final int SI_INTERVAL_MONTH_START = 1;
  public static final int SI_INTERVAL_MONTH_END = 2;
  public static final int SI_INTERVAL_SEMI_MONTHLY = 3;
  public static final String ALERT_MESSAGE_RESOURCE = "com.ffusion.beans.alerts.resources";
  public static final String SUBJECT_BNK_MSG = "subjectBankMsg";
  public static final String SUBJECT_SECURE_BANK_MSG = "subjectSecBankMsg";
  public static final String SUBJECT_UNSECURE_BANK_MSG = "subjectUnsecBankMsg";
  public static final String BODY_BNK_SECURE_MSG = "bodyBankSecureMsg";
  public static final String BODY_BNK_UNSECURE_MSG = "bodyBankUnSecureMsg";
  public static final String SUBJECT_ACCOUNT_BALANCE = "subjectAccountBalance";
  public static final String BODY_ACCOUNT_BALANCE_SECURE_ABOVE = "bodyAccountBalanceSecureAbove";
  public static final String BODY_ACCOUNT_BALANCE_SECURE_NOTABOVE = "bodyAccountBalanceSecureNotAbove";
  public static final String BODY_ACCOUNT_BALANCE_UNSECURE_ABOVE = "bodyAccountBalanceUnSecureAbove";
  public static final String BODY_ACCOUNT_BALANCE_UNSECURE_NOTABOVE = "bodyAccountBalanceUnSecureNotAbove";
  public static final String BODY_ACCOUNT_BALANCE_SECURE = "bodyAccountBalanceSecure";
  public static final String BODY_ACCOUNT_BALANCE_UNSECURE = "bodyAccountBalanceUnSecure";
  public static final String BODY_ACCOUNT_BALANCE_UNSECURE_INFO = "bodyAccountBalanceUnSecureInfo";
  public static final String SUBJECT_NSF_MSG = "subjectNsfMessage";
  public static final String BODY_NSF_SECURE_MSG = "bodyNsfSecureMessage";
  public static final String BODY_NSF_UNSECURE_MSG = "bodyNsfUnsecureMessage";
  public static final String SUBJECT_TRANS_MSG = "subjectTransMsg";
  public static final String BODY_TRANS_MSG_THRESHOLD_CONDITION = "bodyTransMsgThresholdCondition";
  public static final String BODY_TRANS_MSG_NOTHRESHOLD_CONDITION = "bodyTransMsgNoThresholdCondition";
  public static final String BODY_TRANS_MSG = "bodyTransMsg";
  public static final String BODY_TRANS_MSG_ABOVE = "bodyTransMsgAbove";
  public static final String BODY_TRANS_MSG_BELOW = "bodyTransMsgBelow";
  public static final String BODY_TRANS_MSG_EQUAL = "bodyTransMsgEqual";
  public static final String BODY_TRANS_MSG_UNSECURE_COND = "bodyTransMsgUnsecureCond";
  public static final String BODY_TRANS_MSG_UNSECURE = "bodyTransMsgUnsecure";
  public static final String SUBJECT_SEC_STOCK = "subjectSecureStock";
  public static final String SUBJECT_UNSEC_STOCK = "subjectUnsecureStock";
  public static final String BODY_STOCK = "bodyStock";
  public static final String BODY_STOCK_CONSUMER_SEC = "bodyStockConsumerSecure";
  public static final String BODY_STOCK_CONSUMER_SEC_STOCK_ENTRY = "bodyStockConsumerSecureStockEntry";
  public static final String BODY_STOCK_CONSUMER_UNSEC = "bodyStockConsumerUnsecure";
  public static final String BODY_STOCK_CONSUMER_UNSEC_STOCK_ENTRY = "bodyStockConsumerUnsecureStockEntry";
  public static final String BODY_STOCK_CONSUMER_STOCK_ENTRY_SEP = "bodyStockConsumerSecureStockEntrySeparator";
  public static final String STOCKS_VALIDATION_ERROR_MSG = "STOCKS_VALIDATION_ERROR_MSG";
  public static final String UNAVAILABLE_STOCK_SYMBOL = "unavailableStockSymbol";
  public static final String LAST_STOCK_PRICE = "lastStockPrice";
  public static final String SUBJECT_STOCK = "subjectStock";
  public static final String SUBJECT_POSITIVE_PAY = "subjectPositivePay";
  public static final String SUBJECT_PENDING_APPROVAL = "subjectPendingApproval";
  public static final String SECURE_POSITIVE_PAY = "securePositivePay";
  public static final String SECURE_PENDING_APPROVAL = "securePendingApproval";
  public static final String BODY_PENDING_APPROVAL_ENTRY = "bodyPendingApproval";
  public static final String PENDING_APPROVAL_ENTRY = "pendingApprovalEntry";
  public static final String PENDING_APPROVAL_ENTRY_SEP = "pendingApprovalEntrySeparator";
  protected String id;
  protected int type;
  protected int priority;
  protected DateTime startDate;
  protected DateTime endDate;
  protected long interval;
  protected TimeZone timeZone;
  protected String appInfo;
  protected String message;
  protected AlertStocks alertStocks;
  protected DeliveryInfos deliveryInfos;
  protected DeliveryInfo currentDeliveryInfo = null;
  protected String trackingID;
  protected float amount;
  protected float maxAmount;
  protected float minAmount;
  protected int intervalType = 0;
  protected boolean respectDST = false;
  
  public Alert()
  {
    this.deliveryInfos = new DeliveryInfos();
    this.alertStocks = new AlertStocks();
  }
  
  public Alert(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.deliveryInfos = new DeliveryInfos(paramLocale);
    this.alertStocks = new AlertStocks(paramLocale);
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.startDate != null) {
      this.startDate.setLocale(paramLocale);
    }
    if (this.endDate != null) {
      this.endDate.setLocale(paramLocale);
    }
    if (this.deliveryInfos != null) {
      this.deliveryInfos.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.startDate != null) {
      this.startDate.setFormat(paramString);
    }
    if (this.endDate != null) {
      this.endDate.setFormat(paramString);
    }
    if (this.deliveryInfos != null) {
      this.deliveryInfos.setDateFormat(paramString);
    }
  }
  
  public AlertStocks getStocks()
  {
    if (this.deliveryInfos.size() < 1) {
      return null;
    }
    String str = getDeliveryInfoByID("1").getPropertiesValue().getProperty("AlertStocks");
    AlertStocks localAlertStocks = new AlertStocks();
    if ((str == null) || (str.length() == 0)) {
      return localAlertStocks;
    }
    localAlertStocks.setXML(str);
    return localAlertStocks;
  }
  
  public void setStocks(AlertStocks paramAlertStocks)
  {
    if (this.deliveryInfos.size() < 1) {
      return;
    }
    Properties localProperties = getDeliveryInfoByID("1").getPropertiesValue();
    if (localProperties.containsKey("AlertStocks")) {
      localProperties.remove("AlertStocks");
    }
    localProperties.setProperty("AlertStocks", paramAlertStocks.getXML());
  }
  
  public void addStock(AlertStock paramAlertStock)
  {
    if (this.deliveryInfos.size() < 1) {
      return;
    }
    this.alertStocks = getStocks();
    this.alertStocks.add(paramAlertStock);
    setStocks(this.alertStocks);
  }
  
  public void removeStock(AlertStock paramAlertStock)
  {
    if (this.deliveryInfos.size() < 1) {
      return;
    }
    this.alertStocks = getStocks();
    int i = this.alertStocks.indexOf(paramAlertStock);
    if (i != -1) {
      this.alertStocks.remove(i);
    }
    setStocks(this.alertStocks);
  }
  
  public String getID()
  {
    return this.id;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getAmount()
  {
    return String.valueOf(this.amount);
  }
  
  public float getAmountValue()
  {
    return this.amount;
  }
  
  public void setAmount(String paramString)
  {
    if (paramString != null) {
      this.amount = new Currency(paramString, this.locale).getAmountValue().floatValue();
    } else {
      this.amount = 0.0F;
    }
  }
  
  public String getMinAmount()
  {
    return String.valueOf(this.minAmount);
  }
  
  public float getMinAmountValue()
  {
    return this.minAmount;
  }
  
  public void setMinAmount(String paramString)
  {
    if (paramString != null) {
      this.minAmount = new Currency(paramString, this.locale).getAmountValue().floatValue();
    } else {
      this.minAmount = 0.0F;
    }
  }
  
  public String getMaxAmount()
  {
    return String.valueOf(this.maxAmount);
  }
  
  public float getMaxAmountValue()
  {
    return this.maxAmount;
  }
  
  public void setMaxAmount(String paramString)
  {
    if (paramString != null) {
      this.maxAmount = new Currency(paramString, this.locale).getAmountValue().floatValue();
    } else {
      this.maxAmount = 0.0F;
    }
  }
  
  public String getType()
  {
    return String.valueOf(this.type);
  }
  
  public int getTypeValue()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    if (paramString != null) {
      this.type = Integer.parseInt(paramString);
    }
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public String getPriority()
  {
    return String.valueOf(this.priority);
  }
  
  public int getPriorityValue()
  {
    return this.priority;
  }
  
  public void setPriority(String paramString)
  {
    this.priority = Integer.parseInt(paramString);
  }
  
  public void setPriority(int paramInt)
  {
    this.priority = paramInt;
  }
  
  public String getStartDate()
  {
    if (this.startDate != null) {
      return this.startDate.toString();
    }
    return "";
  }
  
  public DateTime getStartDateValue()
  {
    return this.startDate;
  }
  
  public void setStartDate(String paramString)
  {
    try
    {
      if (this.startDate == null)
      {
        if (this.timeZone == null) {
          this.startDate = new DateTime(paramString, this.locale, this.datetype);
        } else {
          this.startDate = new DateTime(paramString, this.locale, this.timeZone, this.datetype);
        }
      }
      else {
        this.startDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.startDate = null;
    }
  }
  
  public void setStartDate(DateTime paramDateTime)
  {
    this.startDate = paramDateTime;
  }
  
  public String getEndDate()
  {
    if (this.endDate != null) {
      return this.endDate.toString();
    }
    return "";
  }
  
  public DateTime getEndDateValue()
  {
    return this.endDate;
  }
  
  public void setEndDate(String paramString)
  {
    try
    {
      if (this.endDate == null)
      {
        if (this.timeZone == null) {
          this.endDate = new DateTime(paramString, this.locale, this.datetype);
        } else {
          this.endDate = new DateTime(paramString, this.locale, this.timeZone, this.datetype);
        }
      }
      else {
        this.endDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.endDate = null;
    }
  }
  
  public void setEndDate(DateTime paramDateTime)
  {
    this.endDate = paramDateTime;
  }
  
  public String getInterval()
  {
    return String.valueOf(this.interval);
  }
  
  public long getIntervalValue()
  {
    return this.interval;
  }
  
  public void setInterval(String paramString)
  {
    this.interval = Long.parseLong(paramString);
  }
  
  public void setInterval(long paramLong)
  {
    this.interval = paramLong;
  }
  
  public String getTimeZone()
  {
    return this.timeZone.getID();
  }
  
  public TimeZone getRegularTimeZoneValue()
  {
    return this.timeZone;
  }
  
  public SimpleTimeZone getTimeZoneValue()
  {
    if ((this.timeZone instanceof SimpleTimeZone)) {
      return (SimpleTimeZone)this.timeZone;
    }
    if (this.timeZone != null) {
      return new SimpleTimeZone(this.timeZone.getRawOffset(), this.timeZone.getID());
    }
    return null;
  }
  
  public void setTimeZone(String paramString)
  {
    try
    {
      this.timeZone = TimeZone.getTimeZone(paramString);
      if (this.timeZone != null)
      {
        if (this.startDate != null) {
          this.startDate.setTimeZone(this.timeZone);
        }
        if (this.endDate != null) {
          this.endDate.setTimeZone(this.timeZone);
        }
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception:", localException);
      this.timeZone = null;
    }
  }
  
  public void setTimeZone(TimeZone paramTimeZone)
  {
    this.timeZone = paramTimeZone;
  }
  
  public String getAppInfo()
  {
    return this.appInfo;
  }
  
  public void setAppInfo(String paramString)
  {
    this.appInfo = paramString;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String paramString)
  {
    this.message = paramString;
  }
  
  public DeliveryInfos getDeliveryInfosValue()
  {
    return this.deliveryInfos;
  }
  
  public void setDeliveryInfos(DeliveryInfos paramDeliveryInfos)
  {
    this.deliveryInfos = paramDeliveryInfos;
  }
  
  public int getIntervalType()
  {
    return this.intervalType;
  }
  
  public void setIntervalType(int paramInt)
  {
    this.intervalType = paramInt;
  }
  
  public boolean getRespectDST()
  {
    return this.respectDST;
  }
  
  public void setRespectDST(boolean paramBoolean)
  {
    this.respectDST = paramBoolean;
  }
  
  protected DeliveryInfo getDeliveryInfoByID(String paramString)
  {
    if (this.deliveryInfos != null)
    {
      Iterator localIterator = this.deliveryInfos.iterator();
      while (localIterator.hasNext())
      {
        DeliveryInfo localDeliveryInfo = (DeliveryInfo)localIterator.next();
        if (localDeliveryInfo.getID().equals(paramString)) {
          return localDeliveryInfo;
        }
      }
    }
    return null;
  }
  
  public void setDeliveryInfoCount(String paramString)
  {
    this.deliveryInfos = new DeliveryInfos(this.locale);
    try
    {
      int i = Integer.parseInt(paramString);
      for (int j = 1; j <= i; j++)
      {
        DeliveryInfo localDeliveryInfo = new DeliveryInfo(this.locale);
        localDeliveryInfo.setID("" + j);
        this.deliveryInfos.add(localDeliveryInfo);
      }
    }
    catch (Exception localException) {}
  }
  
  public String getDeliveryInfoCount()
  {
    return String.valueOf(this.deliveryInfos.size());
  }
  
  public void setCurrentDeliveryInfo(String paramString)
  {
    this.currentDeliveryInfo = getDeliveryInfoByID(paramString);
  }
  
  public String getCurrentDeliveryInfo()
  {
    if (this.currentDeliveryInfo != null) {
      return this.currentDeliveryInfo.getID();
    }
    return "-1";
  }
  
  public String getDeliveryInfoID()
  {
    if (this.currentDeliveryInfo != null) {
      return this.currentDeliveryInfo.getID();
    }
    return "";
  }
  
  public void setDeliveryInfoChannelName(String paramString)
  {
    if (this.currentDeliveryInfo != null) {
      this.currentDeliveryInfo.setChannelName(paramString);
    }
  }
  
  public String getDeliveryInfoChannelName()
  {
    if (this.currentDeliveryInfo != null) {
      return this.currentDeliveryInfo.getChannelName();
    }
    return "";
  }
  
  public void setDeliveryInfoOrder(String paramString)
  {
    if (this.currentDeliveryInfo != null) {
      this.currentDeliveryInfo.setOrder(paramString);
    }
  }
  
  public String getDeliveryInfoOrder()
  {
    if (this.currentDeliveryInfo != null) {
      return this.currentDeliveryInfo.getOrder();
    }
    return "";
  }
  
  public void setDeliveryInfoMaxDelay(String paramString)
  {
    if (this.currentDeliveryInfo != null) {
      this.currentDeliveryInfo.setMaxDelay(paramString);
    }
  }
  
  public String getDeliveryInfoMaxDelay()
  {
    if (this.currentDeliveryInfo != null) {
      return this.currentDeliveryInfo.getMaxDelay();
    }
    return "";
  }
  
  public void setDeliveryInfoProperties(String paramString)
  {
    if (this.currentDeliveryInfo != null) {
      this.currentDeliveryInfo.setPropertiesString(paramString);
    }
  }
  
  public String getDeliveryInfoProperties()
  {
    if (this.currentDeliveryInfo != null) {
      return this.currentDeliveryInfo.getPropertiesString();
    }
    return "";
  }
  
  public void setDeliveryInfoPropertyKey(String paramString)
  {
    if (this.currentDeliveryInfo != null) {
      this.currentDeliveryInfo.setPropertyKey(paramString);
    }
  }
  
  public String getDeliveryInfoPropertyKey()
  {
    if (this.currentDeliveryInfo != null) {
      return this.currentDeliveryInfo.getPropertyKey();
    }
    return "";
  }
  
  public void setDeliveryInfoPropertyValue(String paramString)
  {
    if (this.currentDeliveryInfo != null) {
      this.currentDeliveryInfo.setPropertyValue(paramString);
    }
  }
  
  public String getDeliveryInfoPropertyValue()
  {
    Object localObject = "";
    if (this.currentDeliveryInfo != null)
    {
      localObject = this.currentDeliveryInfo.getProperty();
      if ((this.currentDeliveryInfo.propertyKey.equalsIgnoreCase("Criteria")) || (this.currentDeliveryInfo.propertyKey.equalsIgnoreCase("TransactionType")))
      {
        String str1 = null;
        String str2 = null;
        str1 = ((String)localObject).replaceAll(" ", "").toLowerCase();
        if (!str1.equalsIgnoreCase(""))
        {
          str2 = ResourceUtil.getString(str1, "com.ffusion.beans.alerts.resources", this.locale);
          if (str2 != null) {
            localObject = str2;
          }
        }
      }
    }
    return localObject;
  }
  
  public void setDeliveryInfoSuspended(String paramString)
  {
    if (this.currentDeliveryInfo != null) {
      this.currentDeliveryInfo.setSuspended(paramString);
    }
  }
  
  public String getDeliveryInfoSuspended()
  {
    if (this.currentDeliveryInfo != null) {
      return this.currentDeliveryInfo.getSuspended();
    }
    return "";
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "STARTDATE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    Alert localAlert = (Alert)paramObject;
    int i = 1;
    if ((paramString.equals("ID")) && (this.id != null) && (localAlert.getID() != null))
    {
      i = localCollator.compare(this.id, localAlert.getID());
    }
    else if (paramString.equals("TYPE"))
    {
      i = getTypeValue() - localAlert.getTypeValue();
    }
    else if (paramString.equals("Amount"))
    {
      if (getAmountValue() - localAlert.getAmountValue() == 0.0F) {
        i = 0;
      } else if (getAmountValue() - localAlert.getAmountValue() < 0.0F) {
        i = -1;
      }
    }
    else if (paramString.equals("MinAmount"))
    {
      if (getMinAmountValue() - localAlert.getMinAmountValue() == 0.0F) {
        i = 0;
      } else if (getMinAmountValue() - localAlert.getMinAmountValue() < 0.0F) {
        i = -1;
      }
    }
    else if (paramString.equals("MaxAmount"))
    {
      if (getMaxAmountValue() - localAlert.getMaxAmountValue() == 0.0F) {
        i = 0;
      } else if (getMaxAmountValue() - localAlert.getMaxAmountValue() < 0.0F) {
        i = -1;
      }
    }
    else if (paramString.equals("PRIORITY"))
    {
      i = getPriorityValue() - localAlert.getPriorityValue();
    }
    else if ((paramString.equals("STARTDATE")) && (this.startDate != null) && (localAlert.getStartDateValue() != null))
    {
      i = this.startDate.equals(localAlert.getStartDateValue()) ? 0 : this.startDate.before(localAlert.getStartDateValue()) ? -1 : 1;
    }
    else if ((paramString.equals("ENDDATE")) && (this.endDate != null) && (localAlert.getEndDateValue() != null))
    {
      i = this.endDate.equals(localAlert.getEndDateValue()) ? 0 : this.endDate.before(localAlert.getEndDateValue()) ? -1 : 1;
    }
    else if (paramString.equals("INTERVAL"))
    {
      i = Integer.parseInt(String.valueOf(getIntervalValue() - localAlert.getIntervalValue()));
    }
    else if ((paramString.equals("TIMEZONE")) && (this.timeZone != null) && (localAlert.getRegularTimeZoneValue() != null))
    {
      i = this.timeZone.equals(localAlert.getRegularTimeZoneValue()) ? 0 : 1;
    }
    else if ((paramString.equals("APPINFO")) && (this.appInfo != null) && (localAlert.getAppInfo() != null))
    {
      i = localCollator.compare(this.appInfo, localAlert.getAppInfo());
    }
    else if ((paramString.equals("MESSAGE")) && (this.message != null) && (localAlert.getMessage() != null))
    {
      i = localCollator.compare(this.message, localAlert.getMessage());
    }
    else if ((paramString.equals("DELIVERYINFOS")) && (this.deliveryInfos != null) && (localAlert.getDeliveryInfosValue() != null))
    {
      i = this.deliveryInfos.size() - localAlert.getDeliveryInfosValue().size();
      if ((i == 0) && (!this.deliveryInfos.equals(localAlert.getDeliveryInfosValue()))) {
        i = 1;
      }
    }
    else if (paramString.equals("IntervalType"))
    {
      i = this.intervalType - localAlert.intervalType;
    }
    else if (paramString.equals("RespectDST"))
    {
      i = this.respectDST == localAlert.respectDST ? 0 : 1;
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public void set(Alert paramAlert)
  {
    if ((this == paramAlert) || (paramAlert == null)) {
      return;
    }
    setID(paramAlert.getID());
    setType(paramAlert.getTypeValue());
    setPriority(paramAlert.getPriorityValue());
    if (paramAlert.getStartDateValue() != null) {
      setStartDate((DateTime)paramAlert.getStartDateValue().clone());
    } else {
      setStartDate((DateTime)null);
    }
    if (paramAlert.getEndDateValue() != null) {
      setEndDate((DateTime)paramAlert.getEndDateValue().clone());
    } else {
      setEndDate((DateTime)null);
    }
    setInterval(paramAlert.getIntervalValue());
    setTimeZone(paramAlert.getRegularTimeZoneValue());
    setAppInfo(paramAlert.getAppInfo());
    setMessage(paramAlert.getMessage());
    if (paramAlert.getDeliveryInfosValue() != null) {
      setDeliveryInfos((DeliveryInfos)paramAlert.getDeliveryInfosValue().clone());
    } else {
      setDeliveryInfos((DeliveryInfos)null);
    }
    setIntervalType(paramAlert.getIntervalType());
    setRespectDST(paramAlert.getRespectDST());
    super.set(paramAlert);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("ID"))
      {
        this.id = paramString2;
      }
      else if (paramString1.equals("TYPE"))
      {
        this.type = Integer.parseInt(paramString2);
      }
      else if (paramString1.equals("PRIORITY"))
      {
        this.priority = Integer.parseInt(paramString2);
      }
      else if (paramString1.equals("STARTDATE"))
      {
        if (this.startDate == null)
        {
          this.startDate = new DateTime(this.locale);
          this.startDate.setFormat(this.datetype);
        }
        this.startDate.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("ENDDATE"))
      {
        if (this.endDate == null)
        {
          this.endDate = new DateTime(this.locale);
          this.endDate.setFormat(this.datetype);
        }
        this.endDate.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("INTERVAL"))
      {
        this.interval = Long.parseLong(paramString2);
      }
      else if (paramString1.equals("TIMEZONE"))
      {
        if (this.timeZone == null) {
          this.timeZone = TimeZone.getDefault();
        }
      }
      else if (paramString1.equals("APPINFO"))
      {
        this.appInfo = paramString2;
      }
      else if (paramString1.equals("MESSAGE"))
      {
        this.message = paramString2;
      }
      else if (paramString1.equals("IntervalType"))
      {
        if ((paramString2 == null) || (paramString2.length() <= 0)) {
          this.intervalType = 0;
        } else {
          try
          {
            this.intervalType = Integer.parseInt(paramString2);
          }
          catch (NumberFormatException localNumberFormatException)
          {
            this.intervalType = 0;
          }
        }
      }
      else if (paramString1.equals("RespectDST"))
      {
        this.respectDST = Boolean.valueOf(paramString2).booleanValue();
      }
      else
      {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception:", localException);
    }
    return bool;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ALERT");
    XMLHandler.appendTag(localStringBuffer, "ID", this.id);
    XMLHandler.appendTag(localStringBuffer, "TYPE", this.type);
    XMLHandler.appendTag(localStringBuffer, "PRIORITY", this.priority);
    if (this.startDate != null) {
      XMLHandler.appendTag(localStringBuffer, "STARTDATE", this.startDate.toXMLFormat());
    }
    if (this.endDate != null) {
      XMLHandler.appendTag(localStringBuffer, "ENDDATE", this.endDate.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "INTERVAL", this.interval);
    if (this.timeZone != null) {
      XMLHandler.appendTag(localStringBuffer, "TIMEZONE", this.timeZone.getID());
    }
    XMLHandler.appendTag(localStringBuffer, "APPINFO", this.appInfo);
    XMLHandler.appendTag(localStringBuffer, "MESSAGE", this.message);
    XMLHandler.appendTag(localStringBuffer, "IntervalType", Integer.toString(this.intervalType));
    XMLHandler.appendTag(localStringBuffer, "RespectDST", String.valueOf(this.respectDST));
    if (this.deliveryInfos != null) {
      localStringBuffer.append(this.deliveryInfos.getXML());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ALERT");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("DELIVERYINFOS")) {
        Alert.this.deliveryInfos.continueXMLParsing(getHandler());
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      Alert.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.alerts.Alert
 * JD-Core Version:    0.7.0.1
 */