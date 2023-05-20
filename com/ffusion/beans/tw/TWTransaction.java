package com.ffusion.beans.tw;

import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.tw.TransactionWarehouse;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.StringTokenizer;

public class TWTransaction
  extends ExtendABean
{
  private int a43;
  private int a4W;
  private int a4Y;
  private String a4X;
  private Currency a40;
  private DateTime a42;
  private String a4Z;
  private String a41;
  private IApprovable a4V;
  
  public TWTransaction(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setID(int paramInt)
  {
    this.a43 = paramInt;
  }
  
  public int getID()
  {
    return this.a43;
  }
  
  public void setUserID(int paramInt)
  {
    this.a4W = paramInt;
  }
  
  public int getUserID()
  {
    return this.a4W;
  }
  
  public void setTransactionType(int paramInt)
  {
    this.a4Y = paramInt;
  }
  
  public int getTransactionType()
  {
    return this.a4Y;
  }
  
  public void setTrackingID(String paramString)
  {
    this.a4X = paramString;
  }
  
  public String getTrackingID()
  {
    return this.a4X;
  }
  
  public void setUserName(String paramString)
  {
    this.a4Z = paramString;
  }
  
  public String getUserName()
  {
    return this.a4Z;
  }
  
  public void setPassword(String paramString)
  {
    this.a41 = paramString;
  }
  
  public String getPassword()
  {
    return this.a41;
  }
  
  public void setAmount(Currency paramCurrency)
  {
    this.a40 = paramCurrency;
  }
  
  public Currency getAmount()
  {
    return this.a40;
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.a42 != null) {
      this.a42.setFormat(paramString);
    }
  }
  
  public void setSubmissionDateTime(DateTime paramDateTime)
  {
    this.a42 = paramDateTime;
  }
  
  public DateTime getSubmissionDateTime()
  {
    return this.a42;
  }
  
  public void setTransaction(IApprovable paramIApprovable)
  {
    this.a4V = paramIApprovable;
  }
  
  public IApprovable getTransaction()
  {
    return this.a4V;
  }
  
  public String getTypeAsString()
  {
    String str = this.a4V.getApprovalSubTypeName();
    if (str == null) {
      str = "Unknown";
    }
    return str;
  }
  
  public String getDisplayTypeAsString()
  {
    String str1 = "com.ffusion.approvals.resources";
    int i = this.a4V.getApprovalSubType();
    String str2 = ResourceUtil.getString("ApprovalsType1SubType" + i + "AuditDesc", "com.ffusion.approvals.resources", super.getLocale());
    if (str2 == null) {
      str2 = "Unknown";
    }
    return str2;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a40 != null) {
      this.a40.setLocale(paramLocale);
    }
    if ((this.a4V != null) && ((this.a4V instanceof ExtendABean))) {
      ((ExtendABean)this.a4V).setLocale(paramLocale);
    }
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      int i;
      if (str1.equalsIgnoreCase("TWID"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          return false;
        }
        return this.a43 == i;
      }
      if (str1.equalsIgnoreCase("USERID"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          return false;
        }
        return this.a4W == i;
      }
      if (str1.equalsIgnoreCase("TRANSACTIONTYPE"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException3)
        {
          return false;
        }
        return this.a4Y == i;
      }
      if (str1.equalsIgnoreCase("TRACKINGID")) {
        return this.a4X.equals(str2);
      }
      if (str1.equalsIgnoreCase("USERNAME")) {
        return this.a4Z.equals(str2);
      }
      if (str1.equalsIgnoreCase("PASSWORD")) {
        return this.a41.equals(str2);
      }
      if (str1.equalsIgnoreCase("AMOUNT")) {
        return this.a40.equals(new BigDecimal(str2));
      }
      if (str1.equalsIgnoreCase("DTSUBMIT")) {
        return this.a42.isFilterable(paramString);
      }
      if ((this.a4V instanceof ExtendABean)) {
        return ((ExtendABean)this.a4V).isFilterable(paramString);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "TWID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    TWTransaction localTWTransaction = (TWTransaction)paramObject;
    int i = 1;
    if (paramString.equals("TWID")) {
      i = this.a43 - localTWTransaction.getID();
    } else if (paramString.equals("USERID")) {
      i = this.a4W - localTWTransaction.getUserID();
    } else if (paramString.equals("TRANSACTIONTYPE")) {
      i = this.a4Y - localTWTransaction.getTransactionType();
    } else if ((paramString.equals("TRACKINGID")) && (this.a4X != null) && (localTWTransaction.getTrackingID() != null)) {
      i = this.a4X.compareTo(localTWTransaction.getTrackingID());
    } else if ((paramString.equals("USERNAME")) && (this.a4Z != null) && (localTWTransaction.getUserName() != null)) {
      i = this.a4Z.compareTo(localTWTransaction.getUserName());
    } else if ((paramString.equals("PASSWORD")) && (this.a41 != null) && (localTWTransaction.getPassword() != null)) {
      i = this.a4X.compareTo(localTWTransaction.getTrackingID());
    } else if ((paramString.equals("AMOUNT")) && (this.a40 != null) && (localTWTransaction.getAmount() != null)) {
      i = this.a40.compareTo(localTWTransaction.getAmount());
    } else if ((paramString.equals("DTSUBMIT")) && (this.a42 != null) && (localTWTransaction.getSubmissionDateTime() != null)) {
      i = this.a42.compare(localTWTransaction.getSubmissionDateTime(), paramString);
    } else if ((paramString.equals("TRANSACTION")) && (this.a4V != null) && (localTWTransaction.getTransaction() != null))
    {
      if ((this.a4V instanceof ExtendABean)) {
        i = ((ExtendABean)this.a4V).compare(paramObject, paramString);
      }
    }
    else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof TWTransaction)) {
      return false;
    }
    TWTransaction localTWTransaction = (TWTransaction)paramObject;
    return (this.a43 == localTWTransaction.getID()) && (this.a4W == localTWTransaction.getUserID()) && (this.a4Y == localTWTransaction.getTransactionType()) && (areObjectsEqual(this.a4X, localTWTransaction.getTrackingID())) && (areObjectsEqual(this.a4Z, localTWTransaction.getUserName())) && (areObjectsEqual(this.a41, localTWTransaction.getPassword())) && (areObjectsEqual(this.a40, localTWTransaction.getAmount())) && (areObjectsEqual(this.a42, localTWTransaction.getSubmissionDateTime())) && (areObjectsEqual(this.a4V, localTWTransaction.getTransaction()));
  }
  
  public void set(TWTransaction paramTWTransaction)
  {
    super.set(paramTWTransaction);
    setID(paramTWTransaction.getID());
    setUserID(paramTWTransaction.getUserID());
    setUserName(paramTWTransaction.getUserName());
    setPassword(paramTWTransaction.getPassword());
    setTransactionType(paramTWTransaction.getTransactionType());
    setTrackingID(paramTWTransaction.getTrackingID());
    setAmount(paramTWTransaction.getAmount());
    setSubmissionDateTime(paramTWTransaction.getSubmissionDateTime());
    setTransaction(paramTWTransaction.getTransaction());
    setLocale(paramTWTransaction.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("TWID")) {
        this.a43 = Integer.parseInt(paramString2);
      } else if (paramString1.equals("USERID")) {
        this.a4W = Integer.parseInt(paramString2);
      } else if (paramString1.equals("TRANSACTIONTYPE")) {
        this.a4Y = Integer.parseInt(paramString2);
      } else if (paramString1.equals("TRACKINGID")) {
        this.a4X = paramString2;
      } else if (paramString1.equals("USERNAME")) {
        this.a4Z = paramString2;
      } else if (paramString1.equals("PASSWORD")) {
        this.a41 = paramString2;
      } else if (paramString1.equals("AMOUNT")) {
        this.a40 = new Currency(paramString2, this.locale);
      } else if (paramString1.equals("DTSUBMIT")) {
        this.a42 = new DateTime(paramString2, this.locale);
      } else {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception", localException);
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
    XMLHandler.appendBeginTag(localStringBuffer, "TW_TRANSACTION");
    XMLHandler.appendTag(localStringBuffer, "ID", this.a43);
    XMLHandler.appendTag(localStringBuffer, "USERID", this.a4W);
    XMLHandler.appendTag(localStringBuffer, "TRANSACTIONTYPE", this.a4Y);
    XMLHandler.appendTag(localStringBuffer, "TRACKINGID", this.a4X);
    XMLHandler.appendTag(localStringBuffer, "USERNAME", this.a4Z);
    XMLHandler.appendTag(localStringBuffer, "PASSWORD", this.a41);
    if (this.a40 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "AMOUNT");
      localStringBuffer.append(this.a40.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "AMOUNT");
    }
    if (this.a42 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "DTSUBMIT");
      localStringBuffer.append(this.a42.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "DTSUBMIT");
    }
    if (this.a4V != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "TRANSACTION");
      localStringBuffer.append(this.a4V.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "TRANSACTION");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "TW_TRANSACTION");
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
  
  public boolean areObjectsEqual(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == paramObject2) {
      return true;
    }
    if ((paramObject1 == null) || (paramObject2 == null)) {
      return false;
    }
    return paramObject1.equals(paramObject2);
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("twID=").append(this.a43);
    localStringBuffer.append(" userID=").append(this.a4W);
    localStringBuffer.append(" transactionType=").append(this.a4Y);
    localStringBuffer.append(" trackingID=").append(this.a4X);
    localStringBuffer.append(" amount=").append(this.a40);
    localStringBuffer.append(" dtSubmit=").append(this.a42);
    localStringBuffer.append(" (transaction=").append(this.a4V).append(")");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      TWTransaction.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("AMOUNT"))
      {
        if (TWTransaction.this.a40 == null)
        {
          TWTransaction.this.a40 = new Currency();
          TWTransaction.this.a40.setLocale(TWTransaction.this.locale);
        }
        TWTransaction.this.a40.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("TRANSACTION"))
      {
        if (TWTransaction.this.a4V == null) {
          TWTransaction.this.a4V = TransactionWarehouse.getApprovable(TWTransaction.this.a4Y, null);
        }
        TWTransaction.this.a4V.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("DTSUBMIT"))
      {
        if (TWTransaction.this.a42 == null) {
          TWTransaction.this.a42 = new DateTime(TWTransaction.this.locale);
        }
        TWTransaction.this.a42.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.tw.TWTransaction
 * JD-Core Version:    0.7.0.1
 */