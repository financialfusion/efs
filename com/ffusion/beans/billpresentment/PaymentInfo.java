package com.ffusion.beans.billpresentment;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.Locale;
import java.util.StringTokenizer;

public class PaymentInfo
  extends ExtendABean
  implements Comparable, Serializable
{
  public static final String PAYMENTINFO = "PAYMENTINFO";
  public static final String PAYMENTINFOID = "PAYMENTINFOID";
  public static final String BILLSUMMARYID = "BILLSUMMARYID";
  public static final String PAYMENTREFNUM = "PAYMENTREFNUM";
  public static final String PAYMENTACCOUNTID = "PAYMENTACCOUNTID";
  public static final String PAYMENTDATE = "PAYMENTDATE";
  public static final String AMOUNTPAID = "AMOUNTPAID";
  public static final String ERRORCODE = "ERRORCODE";
  private long aVK;
  private String aVL;
  protected String paymentRefNum;
  protected String paymentAccountID;
  protected DateTime paymentDate;
  protected Currency amountPaid;
  protected String errorCode;
  protected String trackingID;
  
  public PaymentInfo()
  {
    this.datetype = "SHORT";
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
    if (this.paymentDate != null) {
      this.paymentDate.setLocale(paramLocale);
    }
    if (this.amountPaid != null) {
      this.amountPaid.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.paymentDate != null) {
      this.paymentDate.setFormat(paramString);
    }
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase("BILLSUMMARYID")) {
        return this.aVL.equals(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "PAYMENTINFOID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    PaymentInfo localPaymentInfo = (PaymentInfo)paramObject;
    int i = 1;
    if (paramString.equals("PAYMENTINFOID"))
    {
      i = (int)(this.aVK - localPaymentInfo.getPaymentInfoID());
    }
    else if ((paramString.equals("BILLSUMMARYID")) && (this.aVL != null) && (localPaymentInfo.getBillSummaryID() != null))
    {
      i = this.aVL.compareTo(localPaymentInfo.getBillSummaryID());
    }
    else if (paramString.equals("PAYMENTREFNUM"))
    {
      String str1 = getPaymentRefNum();
      if (str1 == null) {
        str1 = "";
      }
      String str2 = localPaymentInfo.getPaymentRefNum();
      if (str2 == null) {
        str2 = "";
      }
      i = str1.compareTo(str2);
    }
    else if ((paramString.equals("PAYMENTACCOUNTID")) && (getPaymentAccountID() != null) && (localPaymentInfo.getPaymentAccountID() != null))
    {
      i = getPaymentAccountID().compareTo(localPaymentInfo.getPaymentAccountID());
    }
    else if ((paramString.equals("PAYMENTDATE")) && (this.paymentDate != null) && (localPaymentInfo.getPaymentDateValue() != null))
    {
      i = this.paymentDate.equals(localPaymentInfo.getPaymentDateValue()) ? 0 : this.paymentDate.before(localPaymentInfo.getPaymentDateValue()) ? -1 : 1;
    }
    else if (paramString.equals("AMOUNTPAID"))
    {
      i = this.amountPaid.compareTo(localPaymentInfo.getAmountPaidValue());
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public PaymentInfo(Locale paramLocale)
  {
    super(paramLocale);
    this.datetype = "SHORT";
  }
  
  public final void setPaymentRefNum(String paramString)
  {
    this.paymentRefNum = paramString;
  }
  
  public final String getPaymentRefNum()
  {
    return this.paymentRefNum;
  }
  
  public final void setPaymentAccountID(String paramString)
  {
    this.paymentAccountID = paramString;
  }
  
  public final String getPaymentAccountID()
  {
    return this.paymentAccountID;
  }
  
  public final void setPaymentDate(String paramString)
  {
    try
    {
      if (this.paymentDate == null) {
        this.paymentDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.paymentDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.paymentDate = null;
    }
  }
  
  public final void setPaymentDate(DateTime paramDateTime)
  {
    this.paymentDate = paramDateTime;
  }
  
  public final DateTime getPaymentDateValue()
  {
    return this.paymentDate;
  }
  
  public final String getPaymentDate()
  {
    return this.paymentDate.toString();
  }
  
  public final void setAmountPaid(String paramString)
  {
    if (this.amountPaid == null) {
      this.amountPaid = new Currency(paramString, this.locale);
    } else {
      this.amountPaid.fromString(paramString);
    }
  }
  
  public final void setAmountPaid(Currency paramCurrency)
  {
    this.amountPaid = paramCurrency;
  }
  
  public final Currency getAmountPaidValue()
  {
    return this.amountPaid;
  }
  
  public final String getAmountPaid()
  {
    return this.amountPaid.toString();
  }
  
  public final void setID(long paramLong)
  {
    this.aVK = paramLong;
  }
  
  public final void setID(String paramString)
  {
    this.aVK = Long.parseLong(paramString);
  }
  
  public final void setPaymentInfoID(long paramLong)
  {
    this.aVK = paramLong;
  }
  
  public final void setPaymentInfoID(String paramString)
  {
    this.aVK = Long.parseLong(paramString);
  }
  
  public final long getID()
  {
    return this.aVK;
  }
  
  public final long getPaymentInfoID()
  {
    return this.aVK;
  }
  
  public final void setBillSummaryID(String paramString)
  {
    this.aVL = paramString;
  }
  
  public final String getBillSummaryID()
  {
    return this.aVL;
  }
  
  public final void setErrorCode(String paramString)
  {
    this.errorCode = paramString;
  }
  
  public final String getErrorCode()
  {
    return this.errorCode;
  }
  
  public void set(PaymentInfo paramPaymentInfo)
  {
    super.set(paramPaymentInfo);
    setPaymentInfoID(paramPaymentInfo.getPaymentInfoID());
    setBillSummaryID(paramPaymentInfo.getBillSummaryID());
    setPaymentRefNum(paramPaymentInfo.getPaymentRefNum());
    setPaymentAccountID(paramPaymentInfo.getPaymentAccountID());
    if (paramPaymentInfo.getPaymentDateValue() != null) {
      setPaymentDate((DateTime)paramPaymentInfo.getPaymentDateValue().clone());
    } else {
      setPaymentDate((DateTime)null);
    }
    if (paramPaymentInfo.getAmountPaidValue() != null) {
      setAmountPaid((Currency)paramPaymentInfo.getAmountPaidValue().clone());
    } else {
      setAmountPaid((Currency)null);
    }
    setErrorCode(paramPaymentInfo.getErrorCode());
    setLocale(paramPaymentInfo.locale);
    setDateFormat(paramPaymentInfo.getDateFormat());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("PAYMENTINFOID"))
      {
        this.aVK = Long.parseLong(paramString2);
      }
      else if (paramString1.equals("BILLSUMMARYID"))
      {
        this.aVL = paramString2;
      }
      else if (paramString1.equals("PAYMENTREFNUM"))
      {
        this.paymentRefNum = paramString2;
      }
      else if (paramString1.equals("PAYMENTACCOUNTID"))
      {
        this.paymentAccountID = paramString2;
      }
      else if (paramString1.equals("PAYMENTDATE"))
      {
        if (this.paymentDate == null)
        {
          this.paymentDate = new DateTime(this.locale);
          this.paymentDate.setFormat(this.datetype);
        }
        this.paymentDate.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("AMOUNTPAID"))
      {
        if (this.amountPaid == null) {
          this.amountPaid = new Currency(paramString2, this.locale);
        } else {
          this.amountPaid.fromString(paramString2);
        }
      }
      else if (paramString1.equals("ERRORCODE"))
      {
        this.errorCode = paramString2;
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
    XMLHandler.appendBeginTag(localStringBuffer, "PAYMENTINFO");
    XMLHandler.appendTag(localStringBuffer, "PAYMENTINFOID", this.aVK);
    XMLHandler.appendTag(localStringBuffer, "BILLSUMMARYID", this.aVL);
    XMLHandler.appendTag(localStringBuffer, "PAYMENTREFNUM", this.paymentRefNum);
    XMLHandler.appendTag(localStringBuffer, "PAYMENTACCOUNTID", this.paymentAccountID);
    XMLHandler.appendTag(localStringBuffer, "PAYMENTDATE", this.paymentDate);
    if (this.amountPaid != null) {
      XMLHandler.appendTag(localStringBuffer, "AMOUNTPAID", this.amountPaid.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "ERRORCODE", this.errorCode);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PAYMENTINFO");
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
  
  public String getDateFormat()
  {
    return super.getDateFormat();
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      PaymentInfo.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.PaymentInfo
 * JD-Core Version:    0.7.0.1
 */