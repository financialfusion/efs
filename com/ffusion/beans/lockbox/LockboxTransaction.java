package com.ffusion.beans.lockbox;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.Locale;
import java.util.StringTokenizer;

public class LockboxTransaction
  extends ExtendABean
{
  public static final String STR_OUTER_TAG = "LOCKBOX_TRANSACTION";
  protected String _acctID;
  protected String _acctNumber;
  protected String _bankID;
  protected String _lockboxNumber;
  protected long _transIndex;
  protected int _transID;
  protected int _transType;
  protected DateTime _processDate;
  protected Currency _amount;
  protected String _description;
  protected Currency _immediateFloat;
  protected Currency _oneDayFloat;
  protected Currency _twoDayFloat;
  protected int _numRejectedChecks;
  protected Currency _rejectedAmt;
  protected DateTime _valueDateTime;
  protected String _bankRefNumber;
  protected String _customerRefNumber;
  
  public LockboxTransaction() {}
  
  public LockboxTransaction(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public String getAccountID()
  {
    return this._acctID;
  }
  
  public String getAccountNumber()
  {
    return this._acctNumber;
  }
  
  public String getBankID()
  {
    return this._bankID;
  }
  
  public String getLockboxNumber()
  {
    return this._lockboxNumber;
  }
  
  public long getTransactionIndex()
  {
    return this._transIndex;
  }
  
  public int getTransactionID()
  {
    return this._transID;
  }
  
  public int getTransactionType()
  {
    return this._transType;
  }
  
  public DateTime getProcessingDate()
  {
    return this._processDate;
  }
  
  public Currency getAmount()
  {
    return this._amount;
  }
  
  public String getDescription()
  {
    return this._description;
  }
  
  public Currency getImmediateFloat()
  {
    return this._immediateFloat;
  }
  
  public String getImmediateFloatAmount()
  {
    return this._immediateFloat.getAmountValue().toString();
  }
  
  public Currency getOneDayFloat()
  {
    return this._oneDayFloat;
  }
  
  public String getOneDayFloatAmount()
  {
    return this._oneDayFloat.getAmountValue().toString();
  }
  
  public Currency getTwoDayFloat()
  {
    return this._twoDayFloat;
  }
  
  public String getTwoDayFloatAmount()
  {
    return this._twoDayFloat.getAmountValue().toString();
  }
  
  public int getNumRejectedChecks()
  {
    return this._numRejectedChecks;
  }
  
  public Currency getRejectedAmount()
  {
    return this._rejectedAmt;
  }
  
  public DateTime getValueDateTime()
  {
    return this._valueDateTime;
  }
  
  public String getBankReferenceNumber()
  {
    return this._bankRefNumber;
  }
  
  public String getCustomerReferenceNumber()
  {
    return this._customerRefNumber;
  }
  
  public void setAccountID(String paramString)
  {
    this._acctID = paramString;
  }
  
  public void setAccountNumber(String paramString)
  {
    this._acctNumber = paramString;
  }
  
  public void setBankID(String paramString)
  {
    this._bankID = paramString;
  }
  
  public void setLockboxNumber(String paramString)
  {
    this._lockboxNumber = paramString;
  }
  
  public void setTransactionIndex(long paramLong)
  {
    this._transIndex = paramLong;
  }
  
  public void setTransactionID(int paramInt)
  {
    this._transID = paramInt;
  }
  
  public void setTransactionType(int paramInt)
  {
    this._transType = paramInt;
  }
  
  public void setProcessingDate(DateTime paramDateTime)
  {
    this._processDate = paramDateTime;
  }
  
  public void setAmount(Currency paramCurrency)
  {
    this._amount = paramCurrency;
  }
  
  public void setDescription(String paramString)
  {
    this._description = paramString;
  }
  
  public void setImmediateFloat(Currency paramCurrency)
  {
    this._immediateFloat = paramCurrency;
  }
  
  public void setOneDayFloat(Currency paramCurrency)
  {
    this._oneDayFloat = paramCurrency;
  }
  
  public void setTwoDayFloat(Currency paramCurrency)
  {
    this._twoDayFloat = paramCurrency;
  }
  
  public void setNumRejectedChecks(int paramInt)
  {
    this._numRejectedChecks = paramInt;
  }
  
  public void setRejectedAmount(Currency paramCurrency)
  {
    this._rejectedAmt = paramCurrency;
  }
  
  public void setValueDateTime(DateTime paramDateTime)
  {
    this._valueDateTime = paramDateTime;
  }
  
  public void setBankReferenceNumber(String paramString)
  {
    this._bankRefNumber = paramString;
  }
  
  public void setCustomerReferenceNumber(String paramString)
  {
    this._customerRefNumber = paramString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("acctID = ").append(this._acctID);
    localStringBuffer.append("acct# = ").append(this._acctNumber);
    localStringBuffer.append(",bank id = ").append(this._bankID);
    localStringBuffer.append(",lockboxNumber = ").append(this._lockboxNumber);
    localStringBuffer.append(",transIndex = ").append(this._transIndex);
    localStringBuffer.append(",transID = ").append(this._transID);
    localStringBuffer.append(",transType = ").append(this._transType);
    localStringBuffer.append(",processDate = ").append(this._processDate);
    localStringBuffer.append(",amount = ").append(this._amount);
    localStringBuffer.append(",description = ").append(this._description);
    localStringBuffer.append(",immediateFloat = ").append(this._immediateFloat);
    localStringBuffer.append(",oneDayFloat = ").append(this._oneDayFloat);
    localStringBuffer.append(",twoDayFloat = ").append(this._twoDayFloat);
    localStringBuffer.append(",numRejectedChecks = ").append(this._numRejectedChecks);
    localStringBuffer.append(",rejectedAmt = ").append(this._rejectedAmt);
    localStringBuffer.append(",valueDateTime = ").append(this._valueDateTime);
    localStringBuffer.append(",bankRefNumber = ").append(this._bankRefNumber);
    localStringBuffer.append(",customerRefNumber = ").append(this._customerRefNumber);
    localStringBuffer.append(".");
    return localStringBuffer.toString();
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() >= 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase("ACCT_ID")) {
        return this._acctID.equalsIgnoreCase(str2);
      }
      if (str1.equalsIgnoreCase("ACCT_NUM")) {
        return this._acctNumber.equalsIgnoreCase(str2);
      }
      if (str1.equalsIgnoreCase("BANK_ID")) {
        return this._bankID.equalsIgnoreCase(str2);
      }
      if (str1.equalsIgnoreCase("LOCKBOX_NUM")) {
        return this._lockboxNumber.equalsIgnoreCase(str2);
      }
      if (str1.equalsIgnoreCase("TRANS_INDEX")) {
        try
        {
          long l = Long.parseLong(str2);
          return this._transIndex == l;
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          return false;
        }
      }
      if (str1.equalsIgnoreCase("TRANS_ID")) {
        try
        {
          int i = Integer.parseInt(str2);
          return this._transID == i;
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          return false;
        }
      }
      if (str1.equalsIgnoreCase("TRANS_TYPE")) {
        try
        {
          int j = Integer.parseInt(str2);
          return this._transType == j;
        }
        catch (NumberFormatException localNumberFormatException3)
        {
          return false;
        }
      }
      if (str1.equalsIgnoreCase("PROCESS_DATE")) {
        try
        {
          return this._processDate.compare(new DateTime(str2, this.locale), null) == 0;
        }
        catch (InvalidDateTimeException localInvalidDateTimeException)
        {
          DebugLog.throwing("Invalid lockbox item process date format.", localInvalidDateTimeException);
          return false;
        }
      }
      if (!str1.equalsIgnoreCase("AMOUNT"))
      {
        if (str1.equalsIgnoreCase("DESCRIPTION")) {
          return this._description.equalsIgnoreCase(str2);
        }
        if ((!str1.equalsIgnoreCase("IMMEDIATE_FLOAT")) && (!str1.equalsIgnoreCase("ONE_DAY_FLOAT")) && (!str1.equalsIgnoreCase("TWO_DAY_FLOAT")) && (!str1.equalsIgnoreCase("ONE_DAY_FLOAT")))
        {
          if (str1.equalsIgnoreCase("NUM_REJECTED_CHECKS")) {
            try
            {
              int k = Integer.parseInt(str2);
              return this._numRejectedChecks == k;
            }
            catch (NumberFormatException localNumberFormatException4)
            {
              return false;
            }
          }
          if ((!str1.equalsIgnoreCase("AMT_REJECTED")) && (!str1.equalsIgnoreCase("AS_OF_DATE")))
          {
            if (str1.equalsIgnoreCase("BANK_REF_NUM")) {
              return this._bankRefNumber.equalsIgnoreCase(str2);
            }
            if (str1.equalsIgnoreCase("CUSTOMER_REF_NUM")) {
              return this._customerRefNumber.equalsIgnoreCase(str2);
            }
          }
        }
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "ACCT_NUM");
  }
  
  public int compare(Object paramObject, String paramString)
    throws ClassCastException
  {
    LockboxTransaction localLockboxTransaction = (LockboxTransaction)paramObject;
    Collator localCollator = doGetCollator();
    if (paramString.equalsIgnoreCase("ACCT_ID")) {
      return localCollator.compare(this._acctID, localLockboxTransaction._acctID);
    }
    if (paramString.equalsIgnoreCase("ACCT_NUM")) {
      return localCollator.compare(this._acctNumber, localLockboxTransaction._acctNumber);
    }
    if (paramString.equalsIgnoreCase("BANK_ID")) {
      return localCollator.compare(this._bankID, localLockboxTransaction._bankID);
    }
    if (paramString.equalsIgnoreCase("LOCKBOX_NUM")) {
      return localCollator.compare(this._lockboxNumber, localLockboxTransaction._lockboxNumber);
    }
    if (paramString.equalsIgnoreCase("TRANS_INDEX"))
    {
      if (this._transIndex < localLockboxTransaction._transIndex) {
        return -1;
      }
      if (this._transIndex == localLockboxTransaction._transIndex) {
        return 0;
      }
      return 1;
    }
    if (paramString.equalsIgnoreCase("TRANS_ID")) {
      return this._transID - localLockboxTransaction._transID;
    }
    if (paramString.equalsIgnoreCase("TRANS_TYPE")) {
      return this._transType - localLockboxTransaction._transType;
    }
    if (paramString.equalsIgnoreCase("PROCESS_DATE")) {
      return this._processDate.compare(localLockboxTransaction._processDate, null);
    }
    if (paramString.equalsIgnoreCase("AMOUNT")) {
      return this._amount.compareTo(localLockboxTransaction._amount);
    }
    if (paramString.equalsIgnoreCase("DESCRIPTION")) {
      return localCollator.compare(this._description, localLockboxTransaction._description);
    }
    if (paramString.equalsIgnoreCase("IMMEDIATE_FLOAT")) {
      return Currency.compare(this._immediateFloat, localLockboxTransaction._immediateFloat);
    }
    if (paramString.equalsIgnoreCase("ONE_DAY_FLOAT")) {
      return Currency.compare(this._oneDayFloat, localLockboxTransaction._oneDayFloat);
    }
    if (paramString.equalsIgnoreCase("TWO_DAY_FLOAT")) {
      return Currency.compare(this._twoDayFloat, localLockboxTransaction._twoDayFloat);
    }
    if (paramString.equalsIgnoreCase("NUM_REJECTED_CHECKS")) {
      return this._numRejectedChecks - localLockboxTransaction._numRejectedChecks;
    }
    if (paramString.equalsIgnoreCase("AMT_REJECTED")) {
      return this._rejectedAmt.compareTo(localLockboxTransaction._rejectedAmt);
    }
    if (paramString.equalsIgnoreCase("AS_OF_DATE")) {
      return this._valueDateTime.compare(localLockboxTransaction._valueDateTime, null);
    }
    if (paramString.equalsIgnoreCase("BANK_REF_NUM")) {
      return localCollator.compare(this._bankRefNumber, localLockboxTransaction._bankRefNumber);
    }
    if (paramString.equalsIgnoreCase("CUSTOMER_REF_NUM")) {
      return localCollator.compare(this._customerRefNumber, localLockboxTransaction._customerRefNumber);
    }
    return super.compare(paramObject, paramString);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("ACCT_ID"))
    {
      this._acctID = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("ACCT_NUM"))
    {
      this._acctNumber = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("BANK_ID"))
    {
      this._bankID = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("LOCKBOX_NUM"))
    {
      this._lockboxNumber = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("TRANS_INDEX")) {
      try
      {
        long l = Long.parseLong(paramString2);
        this._transIndex = l;
        return true;
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        DebugLog.throwing("Invalid transaction index: not a number", localNumberFormatException1);
        return false;
      }
    }
    if (paramString1.equalsIgnoreCase("TRANS_ID")) {
      try
      {
        int i = Integer.parseInt(paramString2);
        this._transID = i;
        return true;
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        DebugLog.throwing("Invalid transaction ID: not a number", localNumberFormatException2);
        return false;
      }
    }
    if (paramString1.equalsIgnoreCase("TRANS_TYPE")) {
      try
      {
        int j = Integer.parseInt(paramString2);
        this._transType = j;
        return true;
      }
      catch (NumberFormatException localNumberFormatException3)
      {
        DebugLog.throwing("Invalid transaction index type: not a number", localNumberFormatException3);
        return false;
      }
    }
    if (paramString1.equalsIgnoreCase("DESCRIPTION"))
    {
      this._description = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("NUM_REJECTED_CHECKS")) {
      try
      {
        int k = Integer.parseInt(paramString2);
        this._numRejectedChecks = k;
        return true;
      }
      catch (NumberFormatException localNumberFormatException4)
      {
        DebugLog.throwing("Invalid number of rejected checks: not a number", localNumberFormatException4);
        return false;
      }
    }
    if (paramString1.equalsIgnoreCase("BANK_REF_NUM"))
    {
      this._bankRefNumber = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("CUSTOMER_REF_NUM"))
    {
      this._customerRefNumber = paramString2;
      return true;
    }
    return super.set(paramString1, paramString2);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LockboxTransaction))) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    LockboxTransaction localLockboxTransaction = (LockboxTransaction)paramObject;
    return (this._transIndex == localLockboxTransaction._transIndex) && (this._transID == localLockboxTransaction._transID) && (this._transType == localLockboxTransaction._transType) && (this._numRejectedChecks == localLockboxTransaction._numRejectedChecks) && (this._acctID.equalsIgnoreCase(localLockboxTransaction._acctID)) && (this._acctNumber.equalsIgnoreCase(localLockboxTransaction._acctNumber)) && (this._bankID.equalsIgnoreCase(localLockboxTransaction._bankID)) && (this._lockboxNumber.equalsIgnoreCase(localLockboxTransaction._lockboxNumber)) && (this._description.equalsIgnoreCase(localLockboxTransaction._description)) && (this._bankRefNumber.equalsIgnoreCase(localLockboxTransaction._bankRefNumber)) && (this._customerRefNumber.equalsIgnoreCase(localLockboxTransaction._customerRefNumber)) && (this._processDate.compare(localLockboxTransaction._processDate, null) == 0) && (this._amount.compareTo(localLockboxTransaction._amount) == 0) && (this._valueDateTime.compare(localLockboxTransaction._valueDateTime, null) == 0) && (this._immediateFloat.compareTo(localLockboxTransaction._immediateFloat) == 0) && (this._oneDayFloat.compareTo(localLockboxTransaction._oneDayFloat) == 0) && (this._twoDayFloat.compareTo(localLockboxTransaction._twoDayFloat) == 0);
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOCKBOX_TRANSACTION");
    if (this._acctID != null) {
      XMLHandler.appendTag(localStringBuffer, "ACCT_ID", this._acctID);
    }
    if (this._acctNumber != null) {
      XMLHandler.appendTag(localStringBuffer, "ACCT_NUM", this._acctNumber);
    }
    if (this._bankID != null) {
      XMLHandler.appendTag(localStringBuffer, "BANK_ID", this._bankID);
    }
    if (this._lockboxNumber != null) {
      XMLHandler.appendTag(localStringBuffer, "LOCKBOX_NUM", this._lockboxNumber);
    }
    XMLHandler.appendTag(localStringBuffer, "TRANS_INDEX", Long.toString(this._transIndex));
    XMLHandler.appendTag(localStringBuffer, "TRANS_ID", Integer.toString(this._transID));
    XMLHandler.appendTag(localStringBuffer, "TRANS_TYPE", Integer.toString(this._transType));
    if (this._processDate != null) {
      XMLHandler.appendAggregate(localStringBuffer, "PROCESS_DATE", this._processDate.getXML());
    }
    if (this._amount != null) {
      XMLHandler.appendAggregate(localStringBuffer, "AMOUNT", this._amount.getXML());
    }
    if (this._description != null) {
      XMLHandler.appendTag(localStringBuffer, "DESCRIPTION", this._description);
    }
    if (this._immediateFloat != null) {
      XMLHandler.appendAggregate(localStringBuffer, "IMMEDIATE_FLOAT", this._immediateFloat.getXML());
    }
    if (this._oneDayFloat != null) {
      XMLHandler.appendAggregate(localStringBuffer, "ONE_DAY_FLOAT", this._oneDayFloat.getXML());
    }
    if (this._twoDayFloat != null) {
      XMLHandler.appendAggregate(localStringBuffer, "TWO_DAY_FLOAT", this._twoDayFloat.getXML());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_REJECTED_CHECKS", Integer.toString(this._numRejectedChecks));
    if (this._rejectedAmt != null) {
      XMLHandler.appendAggregate(localStringBuffer, "AMT_REJECTED", this._rejectedAmt.getXML());
    }
    if (this._valueDateTime != null) {
      XMLHandler.appendAggregate(localStringBuffer, "AS_OF_DATE", this._valueDateTime.getXML());
    }
    if (this._bankRefNumber != null) {
      XMLHandler.appendTag(localStringBuffer, "BANK_REF_NUM", this._bankRefNumber);
    }
    if (this._customerRefNumber != null) {
      XMLHandler.appendTag(localStringBuffer, "CUSTOMER_REF_NUM", this._customerRefNumber);
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "LOCKBOX_TRANSACTION");
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
  
  private class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      LockboxTransaction.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      Object localObject;
      if (paramString.equalsIgnoreCase("PROCESS_DATE"))
      {
        localObject = new DateTime(LockboxTransaction.this.locale);
        ((DateTime)localObject).continueXMLParsing(getHandler());
        LockboxTransaction.this._processDate = ((DateTime)localObject);
      }
      else if (paramString.equalsIgnoreCase("AMOUNT"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxTransaction.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxTransaction.this._amount = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("IMMEDIATE_FLOAT"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxTransaction.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxTransaction.this._immediateFloat = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("ONE_DAY_FLOAT"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxTransaction.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxTransaction.this._oneDayFloat = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("TWO_DAY_FLOAT"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxTransaction.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxTransaction.this._twoDayFloat = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("AMT_REJECTED"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxTransaction.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxTransaction.this._rejectedAmt = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("AS_OF_DATE"))
      {
        localObject = new DateTime(LockboxTransaction.this.locale);
        ((DateTime)localObject).continueXMLParsing(getHandler());
        LockboxTransaction.this._valueDateTime = ((DateTime)localObject);
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.lockbox.LockboxTransaction
 * JD-Core Version:    0.7.0.1
 */