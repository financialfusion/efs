package com.ffusion.beans.billpresentment;

import com.ffusion.beans.Contact;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.Locale;

public class EBillAccount
  extends Contact
  implements Comparable, Serializable
{
  public static final String EBILLACCOUNT = "EBILLACCOUNT";
  public static final String CREATEDDATE = "CREATEDDATE";
  public static final String ACCOUNTID = "ACCOUNTID";
  public static final String CONSUMERID = "CONSUMERID";
  public static final String BILLERID = "BILLERID";
  public static final String PAYEEID = "PAYEEID";
  public static final String ACCOUNTNUMBER = "ACCOUNTNUMBER";
  public static final String STATUSCODE = "STATUSCODE";
  public static final String NICKNAME = "NICKNAME";
  public static final String ACCOUNTHOLDERSNAME = "ACCOUNTHOLDERSNAME";
  public static final String PREAUTHTOKEN = "PREAUTHTOKEN";
  public static final String REASONTEXT = "REASONTEXT";
  public static final String NEW = "NEW";
  public static final String PENDING = "PEND";
  public static final String REJECTED = "REJECT";
  public static final String ACTIVE = "ACTIVE";
  public static final String CHANGE = "CHANGE";
  public static final String DELETEPENDING = "DELPEN";
  public static final String DELETED = "DELETE";
  public static final String CLOSED = "CLOSED";
  private DateTime H4;
  private long H2;
  private long HZ;
  private long HY;
  private String H3;
  private String H8;
  private String H0;
  private String H7;
  private String H6;
  private String H1;
  private String H5;
  protected String trackingID;
  
  public EBillAccount()
  {
    this.datetype = "SHORT";
  }
  
  public EBillAccount(Locale paramLocale)
  {
    super(paramLocale);
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
    if (this.H4 != null) {
      this.H4.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.H4 != null) {
      this.H4.setFormat(paramString);
    }
  }
  
  public final void setCreatedDate(DateTime paramDateTime)
  {
    this.H4 = paramDateTime;
  }
  
  public void setCreatedDate(String paramString)
  {
    try
    {
      if (this.H4 == null) {
        this.H4 = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.H4.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.H4 = null;
    }
  }
  
  public final String getCreatedDate()
  {
    if (this.H4 != null) {
      return this.H4.toString();
    }
    return "";
  }
  
  public final DateTime getCreatedDateValue()
  {
    return this.H4;
  }
  
  public final void setAccountID(String paramString)
  {
    try
    {
      this.H2 = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception setting AccountID:", localException);
    }
  }
  
  public final void setAccountID(long paramLong)
  {
    this.H2 = paramLong;
  }
  
  public final String getAccountID()
  {
    return String.valueOf(this.H2);
  }
  
  public final long getAccountIDValue()
  {
    return this.H2;
  }
  
  public final void setConsumerID(String paramString)
  {
    try
    {
      this.HZ = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception setting consumerID:", localException);
    }
  }
  
  public final void setConsumerID(long paramLong)
  {
    this.HZ = paramLong;
  }
  
  public final String getConsumerID()
  {
    return String.valueOf(this.HZ);
  }
  
  public final long getConsumerIDValue()
  {
    return this.HZ;
  }
  
  public final void setBillerID(String paramString)
  {
    try
    {
      this.HY = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception setting BillerID:", localException);
    }
  }
  
  public final void setBillerID(long paramLong)
  {
    this.HY = paramLong;
  }
  
  public final String getBillerID()
  {
    return String.valueOf(this.HY);
  }
  
  public final long getBillerIDValue()
  {
    return this.HY;
  }
  
  public final void setPayeeID(String paramString)
  {
    this.H3 = paramString;
  }
  
  public final String getPayeeID()
  {
    return this.H3;
  }
  
  public final void setPreAuthToken(String paramString)
  {
    this.H1 = paramString;
  }
  
  public final String getPreAuthToken()
  {
    return this.H1;
  }
  
  public final void setReasonText(String paramString)
  {
    this.H5 = paramString;
  }
  
  public final String getReasonText()
  {
    return this.H5;
  }
  
  public final void setAccountNumber(String paramString)
  {
    this.H8 = paramString;
  }
  
  public final String getAccountNumber()
  {
    return this.H8;
  }
  
  public final void setStatusCode(String paramString)
  {
    this.H0 = paramString;
  }
  
  public final String getStatusCode()
  {
    return this.H0;
  }
  
  public final void setNickName(String paramString)
  {
    this.H7 = paramString;
  }
  
  public final String getNickName()
  {
    return this.H7;
  }
  
  public final void setAccountHoldersName(String paramString)
  {
    this.H6 = paramString;
  }
  
  public final String getAccountHoldersName()
  {
    return this.H6;
  }
  
  public final boolean isFilterable(String paramString)
  {
    if (super.isFilterable(paramString) != true) {
      return false;
    }
    return this.H0 != null ? this.H0.equals(paramString) : false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "BILLERID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    EBillAccount localEBillAccount = (EBillAccount)paramObject;
    int i = 1;
    if ((paramString.equals("CREATEDDATE")) && (getCreatedDate() != null) && (localEBillAccount.getCreatedDate() != null)) {
      i = getCreatedDate().compareTo(localEBillAccount.getCreatedDate());
    } else if (paramString.equals("ACCOUNTID")) {
      i = (int)(getAccountIDValue() - localEBillAccount.getAccountIDValue());
    } else if (paramString.equals("CONSUMERID")) {
      i = (int)(getConsumerIDValue() - localEBillAccount.getConsumerIDValue());
    } else if (paramString.equals("BILLERID")) {
      i = (int)(getBillerIDValue() - localEBillAccount.getBillerIDValue());
    } else if ((paramString.equals("PAYEEID")) && (getPayeeID() != null) && (localEBillAccount.getPayeeID() != null)) {
      i = getPayeeID().compareTo(localEBillAccount.getPayeeID());
    } else if ((paramString.equals("ACCOUNTNUMBER")) && (getAccountNumber() != null) && (localEBillAccount.getAccountNumber() != null)) {
      i = getAccountNumber().compareTo(localEBillAccount.getAccountNumber());
    } else if ((paramString.equals("STATUSCODE")) && (getStatusCode() != null) && (localEBillAccount.getStatusCode() != null)) {
      i = getStatusCode().compareTo(localEBillAccount.getStatusCode());
    } else if ((paramString.equals("NICKNAME")) && (getNickName() != null) && (localEBillAccount.getNickName() != null)) {
      i = getNickName().compareTo(localEBillAccount.getNickName());
    } else if ((paramString.equals("ACCOUNTHOLDERSNAME")) && (getAccountHoldersName() != null) && (localEBillAccount.getAccountHoldersName() != null)) {
      i = getAccountHoldersName().compareTo(localEBillAccount.getAccountHoldersName());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public void set(EBillAccount paramEBillAccount)
  {
    if ((this == paramEBillAccount) || (paramEBillAccount == null)) {
      return;
    }
    super.set(paramEBillAccount);
    if (paramEBillAccount.getCreatedDateValue() != null) {
      setCreatedDate((DateTime)paramEBillAccount.getCreatedDateValue().clone());
    } else {
      setCreatedDate((DateTime)null);
    }
    setAccountID(paramEBillAccount.getAccountIDValue());
    setConsumerID(paramEBillAccount.getConsumerIDValue());
    setBillerID(paramEBillAccount.getBillerIDValue());
    setPayeeID(paramEBillAccount.getPayeeID());
    setAccountNumber(paramEBillAccount.getAccountNumber());
    setStatusCode(paramEBillAccount.getStatusCode());
    setNickName(paramEBillAccount.getNickName());
    setAccountHoldersName(paramEBillAccount.getAccountHoldersName());
    setPreAuthToken(paramEBillAccount.getPreAuthToken());
    setReasonText(paramEBillAccount.getReasonText());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("CREATEDDATE"))
    {
      if (this.H4 == null)
      {
        this.H4 = new DateTime(this.locale);
        this.H4.setFormat(this.datetype);
      }
      this.H4.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("ACCOUNTID"))
    {
      setAccountID(paramString2);
    }
    else if (paramString1.equals("CONSUMERID"))
    {
      setConsumerID(paramString2);
    }
    else if (paramString1.equals("BILLERID"))
    {
      setBillerID(paramString2);
    }
    else if (paramString1.equals("PAYEEID"))
    {
      setPayeeID(paramString2);
    }
    else if (paramString1.equals("ACCOUNTNUMBER"))
    {
      setAccountNumber(paramString2);
    }
    else if (paramString1.equals("STATUSCODE"))
    {
      setStatusCode(paramString2);
    }
    else if (paramString1.equals("NICKNAME"))
    {
      setNickName(paramString2);
    }
    else if (paramString1.equals("ACCOUNTHOLDERSNAME"))
    {
      setAccountHoldersName(paramString2);
    }
    else if (paramString1.equals("PREAUTHTOKEN"))
    {
      setPreAuthToken(paramString2);
    }
    else if (paramString1.equals("REASONTEXT"))
    {
      setReasonText(paramString2);
    }
    else
    {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "EBILLACCOUNT");
    XMLHandler.appendTag(localStringBuffer, "CREATEDDATE", this.H4);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTID", this.H2);
    XMLHandler.appendTag(localStringBuffer, "CONSUMERID", this.HZ);
    XMLHandler.appendTag(localStringBuffer, "BILLERID", this.HY);
    XMLHandler.appendTag(localStringBuffer, "PAYEEID", this.H3);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTNUMBER", this.H8);
    XMLHandler.appendTag(localStringBuffer, "STATUSCODE", this.H0);
    XMLHandler.appendTag(localStringBuffer, "NICKNAME", this.H7);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTHOLDERSNAME", this.H6);
    XMLHandler.appendTag(localStringBuffer, "PREAUTHTOKEN", this.H1);
    XMLHandler.appendTag(localStringBuffer, "REASONTEXT", this.H5);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "EBILLACCOUNT");
    if (localStringBuffer.toString() == null) {
      return "";
    }
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
    a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if (str.length() > 0) {
        EBillAccount.this.set(getElement(), str);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.EBillAccount
 * JD-Core Version:    0.7.0.1
 */