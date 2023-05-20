package com.ffusion.beans.lockbox;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.text.Collator;
import java.util.Locale;
import java.util.StringTokenizer;

public class LockboxCreditItem
  extends ExtendABean
{
  public static final String STR_OUTER_TAG = "LOCKBOX_CREDIT_ITEM";
  public static final int NUM_FIELDS = 29;
  protected String _acctID;
  protected String _acctNumber;
  protected String _bankID;
  protected String _routingNumber;
  protected String _lockboxNumber;
  protected long _itemIndex;
  protected int _itemID;
  protected DateTime _processDate;
  protected String _docType;
  protected String _payer;
  protected Currency _checkAmt;
  protected String _checkNumber;
  protected DateTime _checkDate;
  protected String _couponAcctNumber;
  protected Currency _couponAmt1;
  protected Currency _couponAmt2;
  protected DateTime _couponDate1;
  protected DateTime _couponDate2;
  protected String _couponRefNumber;
  protected String _checkRtnNumber;
  protected String _checkAcctNumber;
  protected String _lockboxWorkType;
  protected String _lockboxBatNumber;
  protected String _lockboxSeqNumber;
  protected String _memo;
  protected Currency _immediateFloat;
  protected Currency _oneDayFloat;
  protected Currency _twoDayFloat;
  protected DateTime _valueDateTime;
  protected String _bankRefNumber;
  protected String _customerRefNumber;
  
  public LockboxCreditItem() {}
  
  public LockboxCreditItem(Locale paramLocale)
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
  
  public String getRoutingNumber()
  {
    return this._routingNumber;
  }
  
  public String getLockboxNumber()
  {
    return this._lockboxNumber;
  }
  
  public long getItemIndex()
  {
    return this._itemIndex;
  }
  
  public int getItemID()
  {
    return this._itemID;
  }
  
  public DateTime getProcessingDate()
  {
    return this._processDate;
  }
  
  public String getDocumentType()
  {
    return this._docType;
  }
  
  public String getPayer()
  {
    return this._payer;
  }
  
  public Currency getCheckAmount()
  {
    return this._checkAmt;
  }
  
  public String getCheckNumber()
  {
    return this._checkNumber;
  }
  
  public DateTime getCheckDate()
  {
    return this._checkDate;
  }
  
  public String getCouponAccountNumber()
  {
    return this._couponAcctNumber;
  }
  
  public Currency getCouponAmount1()
  {
    return this._couponAmt1;
  }
  
  public Currency getCouponAmount2()
  {
    return this._couponAmt2;
  }
  
  public DateTime getCouponDate1()
  {
    return this._couponDate1;
  }
  
  public String getCouponDate1String()
  {
    if (this._couponDate1 != null) {
      return this._couponDate1.toString();
    }
    return null;
  }
  
  public DateTime getCouponDate2()
  {
    return this._couponDate2;
  }
  
  public String getCouponDate2String()
  {
    if (this._couponDate2 != null) {
      return this._couponDate2.toString();
    }
    return null;
  }
  
  public String getCouponReferenceNumber()
  {
    return this._couponRefNumber;
  }
  
  public String getCheckRoutingNumber()
  {
    return this._checkRtnNumber;
  }
  
  public String getCheckAccountNumber()
  {
    return this._checkAcctNumber;
  }
  
  public String getLockboxWorkType()
  {
    return this._lockboxWorkType;
  }
  
  public String getLockboxBatchNumber()
  {
    return this._lockboxBatNumber;
  }
  
  public String getLockboxSequenceNumber()
  {
    return this._lockboxSeqNumber;
  }
  
  public String getMemo()
  {
    return this._memo;
  }
  
  public Currency getImmediateFloat()
  {
    return this._immediateFloat;
  }
  
  public Currency getOneDayFloat()
  {
    return this._oneDayFloat;
  }
  
  public Currency getTwoDayFloat()
  {
    return this._twoDayFloat;
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
  
  public void setRoutingNumber(String paramString)
  {
    this._routingNumber = paramString;
  }
  
  public void setLockboxNumber(String paramString)
  {
    this._lockboxNumber = paramString;
  }
  
  public void setItemIndex(long paramLong)
  {
    this._itemIndex = paramLong;
  }
  
  public void setItemID(int paramInt)
  {
    this._itemID = paramInt;
  }
  
  public void setProcessingDate(DateTime paramDateTime)
  {
    this._processDate = paramDateTime;
  }
  
  public void setDocumentType(String paramString)
  {
    this._docType = paramString;
  }
  
  public void setPayer(String paramString)
  {
    this._payer = paramString;
  }
  
  public void setCheckAmount(Currency paramCurrency)
  {
    this._checkAmt = paramCurrency;
  }
  
  public void setCheckNumber(String paramString)
  {
    this._checkNumber = paramString;
  }
  
  public void setCheckDate(DateTime paramDateTime)
  {
    this._checkDate = paramDateTime;
  }
  
  public void setCouponAccountNumber(String paramString)
  {
    this._couponAcctNumber = paramString;
  }
  
  public void setCouponAmount1(Currency paramCurrency)
  {
    this._couponAmt1 = paramCurrency;
  }
  
  public void setCouponAmount2(Currency paramCurrency)
  {
    this._couponAmt2 = paramCurrency;
  }
  
  public void setCouponDate1(DateTime paramDateTime)
  {
    this._couponDate1 = paramDateTime;
  }
  
  public void setCouponDate2(DateTime paramDateTime)
  {
    this._couponDate2 = paramDateTime;
  }
  
  public void setCouponReferenceNumber(String paramString)
  {
    this._couponRefNumber = paramString;
  }
  
  public void setCheckRoutingNumber(String paramString)
  {
    this._checkRtnNumber = paramString;
  }
  
  public void setCheckAccountNumber(String paramString)
  {
    this._checkAcctNumber = paramString;
  }
  
  public void setLockboxWorkType(String paramString)
  {
    this._lockboxWorkType = paramString;
  }
  
  public void setLockboxBatchNumber(String paramString)
  {
    this._lockboxBatNumber = paramString;
  }
  
  public void setLockboxSequenceNumber(String paramString)
  {
    this._lockboxSeqNumber = paramString;
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
  
  public void setMemo(String paramString)
  {
    this._memo = paramString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("acctID = ").append(this._acctID);
    localStringBuffer.append("acct# = ").append(this._acctNumber);
    localStringBuffer.append(",bank id = ").append(this._bankID);
    localStringBuffer.append(",routing# = ").append(this._routingNumber);
    localStringBuffer.append(",lockboxNumber = ").append(this._lockboxNumber);
    localStringBuffer.append(",itemIndex = ").append(this._itemIndex);
    localStringBuffer.append(",itemID = ").append(this._itemID);
    localStringBuffer.append(",processDate = ").append(this._processDate);
    localStringBuffer.append(",docType = ").append(this._docType);
    localStringBuffer.append(",payer = ").append(this._payer);
    localStringBuffer.append(",checkAmt = ").append(this._checkAmt);
    localStringBuffer.append(",checkNumber = ").append(this._checkNumber);
    localStringBuffer.append(",checkDate = ").append(this._checkDate);
    localStringBuffer.append(",couponAcctNumber = ").append(this._couponAcctNumber);
    localStringBuffer.append(",couponAmt1 = ").append(this._couponAmt1);
    localStringBuffer.append(",couponAmt2 = ").append(this._couponAmt2);
    localStringBuffer.append(",couponDate1 = ").append(this._couponDate1);
    localStringBuffer.append(",couponDate2 = ").append(this._couponDate2);
    localStringBuffer.append(",couponRefNumber = ").append(this._couponRefNumber);
    localStringBuffer.append(",checkRtnNumber = ").append(this._checkRtnNumber);
    localStringBuffer.append(",checkAcctNumber = ").append(this._checkAcctNumber);
    localStringBuffer.append(",lockboxWorkType= ").append(this._lockboxWorkType);
    localStringBuffer.append(",lockboxBatNumber = ").append(this._lockboxBatNumber);
    localStringBuffer.append(",lockboxSeqNumber = ").append(this._lockboxSeqNumber);
    localStringBuffer.append(",memo = ").append(this._memo);
    localStringBuffer.append(",immediateFloat = ").append(this._immediateFloat);
    localStringBuffer.append(",oneDayFloat = ").append(this._oneDayFloat);
    localStringBuffer.append(",twoDayFloat = ").append(this._twoDayFloat);
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
        return this._acctID.equals(str2);
      }
      if (str1.equalsIgnoreCase("ACCT_NUM")) {
        return this._acctNumber.equals(str2);
      }
      if (str1.equalsIgnoreCase("BANK_ID")) {
        return this._bankID.equals(str2);
      }
      if (str1.equalsIgnoreCase("ROUTING_NUM")) {
        return this._routingNumber.equals(str2);
      }
      if (str1.equalsIgnoreCase("LOCKBOX_NUM")) {
        return this._lockboxNumber.equals(str2);
      }
      if (str1.equalsIgnoreCase("ITEM_INDEX")) {
        try
        {
          long l = Long.parseLong(str2);
          return this._itemIndex == l;
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          DebugLog.throwing("Invalid lockbox item index number: not a number", localNumberFormatException1);
          return false;
        }
      }
      if (str1.equalsIgnoreCase("ITEM_ID")) {
        try
        {
          int i = Integer.parseInt(str2);
          return this._itemID == i;
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          DebugLog.throwing("Invalid lockbox item ID: not a number", localNumberFormatException2);
          return false;
        }
      }
      if (str1.equalsIgnoreCase("PROCESS_DATE")) {
        try
        {
          return this._processDate.compare(new DateTime(str2, this.locale), null) == 0;
        }
        catch (InvalidDateTimeException localInvalidDateTimeException1)
        {
          DebugLog.throwing("Invalid lockbox item process date format.", localInvalidDateTimeException1);
          return false;
        }
      }
      if (str1.equalsIgnoreCase("DOC_TYPE")) {
        return this._docType.equalsIgnoreCase(str2);
      }
      if (str1.equalsIgnoreCase("PAYER")) {
        return this._payer.equalsIgnoreCase(str2);
      }
      if (str1.equalsIgnoreCase("COUPON_ACCT_NUM")) {
        return this._couponAcctNumber.equalsIgnoreCase(str2);
      }
      if ((!str1.equalsIgnoreCase("COUPON_AMT1")) && (!str1.equalsIgnoreCase("COUPON_AMT2")))
      {
        if (str1.equalsIgnoreCase("COUPON_DATE1")) {
          try
          {
            return this._couponDate1.compare(new DateTime(str2, this.locale), null) == 0;
          }
          catch (InvalidDateTimeException localInvalidDateTimeException2)
          {
            DebugLog.throwing("Invalid lockbox coupon process date 1 format.", localInvalidDateTimeException2);
            return false;
          }
        }
        if (str1.equalsIgnoreCase("COUPON_DATE2")) {
          try
          {
            return this._couponDate2.compare(new DateTime(str2, this.locale), null) == 0;
          }
          catch (InvalidDateTimeException localInvalidDateTimeException3)
          {
            DebugLog.throwing("Invalid lockbox coupon process date 2 format.", localInvalidDateTimeException3);
            return false;
          }
        }
        if (str1.equalsIgnoreCase("COUPON_REF_NUM")) {
          return this._couponRefNumber.equalsIgnoreCase(str2);
        }
        if (str1.equalsIgnoreCase("CHECK_RTN_NUM")) {
          return this._checkRtnNumber.equalsIgnoreCase(str2);
        }
        if (str1.equalsIgnoreCase("CHECK_ACCT_NUM")) {
          return this._checkAcctNumber.equalsIgnoreCase(str2);
        }
        if (str1.equalsIgnoreCase("LOCKBOX_WORK_TYPE")) {
          return this._lockboxWorkType.equalsIgnoreCase(str2);
        }
        if (str1.equalsIgnoreCase("LOCKBOX_BAT_NUM")) {
          return this._lockboxBatNumber.equalsIgnoreCase(str2);
        }
        if (str1.equalsIgnoreCase("LOCKBOX_SEQ_NUM")) {
          return this._lockboxSeqNumber.equalsIgnoreCase(str2);
        }
        if ((!str1.equalsIgnoreCase("MEMO")) && (!str1.equalsIgnoreCase("IMMEDIATE_FLOAT")) && (!str1.equalsIgnoreCase("ONE_DAY_FLOAT")) && (!str1.equalsIgnoreCase("TWO_DAY_FLOAT")) && (!str1.equalsIgnoreCase("AS_OF_DATE")))
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
    LockboxCreditItem localLockboxCreditItem = (LockboxCreditItem)paramObject;
    Collator localCollator = doGetCollator();
    if (paramString.equalsIgnoreCase("ACCT_ID")) {
      return localCollator.compare(this._acctID, localLockboxCreditItem._acctID);
    }
    if (paramString.equalsIgnoreCase("ACCT_NUM")) {
      return localCollator.compare(this._acctNumber, localLockboxCreditItem._acctNumber);
    }
    if (paramString.equalsIgnoreCase("CHECK_AMT")) {
      return localCollator.compare(this._checkAmt.toString(), localLockboxCreditItem._checkAmt.toString());
    }
    if (paramString.equalsIgnoreCase("CHECK_NUM")) {
      return localCollator.compare(this._checkNumber, localLockboxCreditItem._checkNumber);
    }
    if (paramString.equalsIgnoreCase("BANK_ID")) {
      return localCollator.compare(this._bankID, localLockboxCreditItem._bankID);
    }
    if (paramString.equalsIgnoreCase("ROUTING_NUM")) {
      return localCollator.compare(this._routingNumber, localLockboxCreditItem._routingNumber);
    }
    if (paramString.equalsIgnoreCase("LOCKBOX_NUM")) {
      return localCollator.compare(this._lockboxNumber, localLockboxCreditItem._lockboxNumber);
    }
    if (paramString.equalsIgnoreCase("ITEM_INDEX")) {
      try
      {
        long l1 = localLockboxCreditItem.getItemIndex();
        long l2 = this._itemIndex - l1;
        if (l2 > 0L) {
          return 1;
        }
        if (l2 < 0L) {
          return -1;
        }
        return 0;
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        DebugLog.throwing("Invalid lockbox item index number: not a number", localNumberFormatException1);
        return 0;
      }
    }
    if (paramString.equalsIgnoreCase("ITEM_ID")) {
      try
      {
        int i = localLockboxCreditItem.getItemID();
        return this._itemID - i;
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        DebugLog.throwing("Invalid lockbox item ID: not a number", localNumberFormatException2);
        return 0;
      }
    }
    if (paramString.equalsIgnoreCase("PROCESS_DATE")) {
      return this._processDate.compare(localLockboxCreditItem._processDate, null);
    }
    if (paramString.equalsIgnoreCase("DOC_TYPE")) {
      return localCollator.compare(this._docType, localLockboxCreditItem._docType);
    }
    if (paramString.equalsIgnoreCase("PAYER")) {
      return localCollator.compare(this._payer, localLockboxCreditItem._payer);
    }
    if (paramString.equalsIgnoreCase("COUPON_ACCT_NUM")) {
      return localCollator.compare(this._couponAcctNumber, localLockboxCreditItem._couponAcctNumber);
    }
    if (paramString.equalsIgnoreCase("COUPON_AMT1"))
    {
      if (this._couponAmt1 == localLockboxCreditItem._couponAmt1) {
        return 0;
      }
      if (this._couponAmt1 == null) {
        return -1;
      }
      if (localLockboxCreditItem._couponAmt1 == null) {
        return 1;
      }
      return this._couponAmt1.compareTo(localLockboxCreditItem._couponAmt1);
    }
    if (paramString.equalsIgnoreCase("COUPON_AMT2"))
    {
      if (this._couponAmt2 == localLockboxCreditItem._couponAmt2) {
        return 0;
      }
      if (this._couponAmt2 == null) {
        return -1;
      }
      if (localLockboxCreditItem._couponAmt2 == null) {
        return 1;
      }
      return this._couponAmt2.compareTo(localLockboxCreditItem._couponAmt2);
    }
    if (paramString.equalsIgnoreCase("COUPON_DATE1")) {
      return this._couponDate1.compare(localLockboxCreditItem._couponDate1, null);
    }
    if (paramString.equalsIgnoreCase("COUPON_DATE2")) {
      return this._couponDate2.compare(localLockboxCreditItem._couponDate2, null);
    }
    if (paramString.equalsIgnoreCase("COUPON_REF_NUM")) {
      return localCollator.compare(this._couponRefNumber, localLockboxCreditItem._couponRefNumber);
    }
    if (paramString.equalsIgnoreCase("CHECK_RTN_NUM")) {
      return localCollator.compare(this._checkRtnNumber, localLockboxCreditItem._checkRtnNumber);
    }
    if (paramString.equalsIgnoreCase("CHECK_ACCT_NUM")) {
      return localCollator.compare(this._checkAcctNumber, localLockboxCreditItem._checkAcctNumber);
    }
    if (paramString.equalsIgnoreCase("LOCKBOX_WORK_TYPE")) {
      return localCollator.compare(this._lockboxWorkType, localLockboxCreditItem._lockboxWorkType);
    }
    if (paramString.equalsIgnoreCase("LOCKBOX_BAT_NUM")) {
      return localCollator.compare(this._lockboxBatNumber, localLockboxCreditItem._lockboxBatNumber);
    }
    if (paramString.equalsIgnoreCase("LOCKBOX_SEQ_NUM")) {
      return localCollator.compare(this._lockboxSeqNumber, localLockboxCreditItem._lockboxSeqNumber);
    }
    if (paramString.equalsIgnoreCase("MEMO")) {
      return localCollator.compare(this._memo, localLockboxCreditItem._memo);
    }
    if (paramString.equalsIgnoreCase("BANK_REF_NUM")) {
      return localCollator.compare(this._bankRefNumber, localLockboxCreditItem._bankRefNumber);
    }
    if (paramString.equalsIgnoreCase("CUSTOMER_REF_NUM")) {
      return localCollator.compare(this._customerRefNumber, localLockboxCreditItem._customerRefNumber);
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
    if (paramString1.equalsIgnoreCase("ROUTING_NUM"))
    {
      this._routingNumber = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("LOCKBOX_NUM"))
    {
      this._lockboxNumber = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("ITEM_INDEX")) {
      try
      {
        this._itemIndex = Long.parseLong(paramString2);
        return true;
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        DebugLog.throwing("Invalid lockbox item index number: not a number", localNumberFormatException1);
        return false;
      }
    }
    if (paramString1.equalsIgnoreCase("ITEM_ID")) {
      try
      {
        this._itemID = Integer.parseInt(paramString2);
        return true;
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        DebugLog.throwing("Invalid lockbox item ID: not a number", localNumberFormatException2);
        return false;
      }
    }
    if (paramString1.equalsIgnoreCase("DOC_TYPE"))
    {
      this._docType = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("PAYER"))
    {
      this._payer = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("CHECK_NUM"))
    {
      this._checkNumber = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("COUPON_ACCT_NUM"))
    {
      this._couponAcctNumber = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("COUPON_REF_NUM"))
    {
      this._couponRefNumber = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("CHECK_RTN_NUM"))
    {
      this._checkRtnNumber = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("CHECK_ACCT_NUM"))
    {
      this._checkAcctNumber = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("LOCKBOX_WORK_TYPE"))
    {
      this._lockboxWorkType = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("LOCKBOX_BAT_NUM"))
    {
      this._lockboxBatNumber = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("LOCKBOX_SEQ_NUM"))
    {
      this._lockboxSeqNumber = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("MEMO"))
    {
      this._memo = paramString2;
      return true;
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
    if ((paramObject == null) || (!(paramObject instanceof LockboxCreditItem))) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    LockboxCreditItem localLockboxCreditItem = (LockboxCreditItem)paramObject;
    return (this._itemIndex == localLockboxCreditItem.getItemIndex()) && (this._itemID == localLockboxCreditItem.getItemID()) && (areFieldStringEqual(this._acctID, localLockboxCreditItem.getAccountID())) && (areFieldStringEqual(this._acctNumber, localLockboxCreditItem.getAccountNumber())) && (areFieldStringEqual(this._bankID, localLockboxCreditItem.getBankID())) && (areFieldStringEqual(this._routingNumber, localLockboxCreditItem.getRoutingNumber())) && (areFieldStringEqual(this._lockboxNumber, localLockboxCreditItem.getLockboxNumber())) && (areFieldStringEqual(this._docType, localLockboxCreditItem.getDocumentType())) && (areFieldStringEqual(this._payer, localLockboxCreditItem.getPayer())) && (areFieldStringEqual(this._checkNumber, localLockboxCreditItem.getCheckNumber())) && (areFieldStringEqual(this._couponAcctNumber, localLockboxCreditItem.getCouponAccountNumber())) && (areFieldStringEqual(this._couponRefNumber, localLockboxCreditItem.getCouponReferenceNumber())) && (areFieldStringEqual(this._checkRtnNumber, localLockboxCreditItem.getCheckRoutingNumber())) && (areFieldStringEqual(this._lockboxWorkType, localLockboxCreditItem.getLockboxWorkType())) && (areFieldStringEqual(this._lockboxBatNumber, localLockboxCreditItem.getLockboxBatchNumber())) && (areFieldStringEqual(this._memo, localLockboxCreditItem.getMemo())) && (areFieldStringEqual(this._bankRefNumber, localLockboxCreditItem.getBankReferenceNumber())) && (areFieldStringEqual(this._customerRefNumber, localLockboxCreditItem.getCustomerReferenceNumber())) && (this._processDate.compare(localLockboxCreditItem.getProcessingDate(), null) == 0) && (this._checkAmt.compareTo(localLockboxCreditItem.getCheckAmount()) == 0) && (this._checkDate.compare(localLockboxCreditItem.getCheckDate(), null) == 0) && (this._couponAmt1.compareTo(localLockboxCreditItem.getCouponAmount1()) == 0) && (this._couponAmt2.compareTo(localLockboxCreditItem.getCouponAmount2()) == 0) && (this._couponDate1.compare(localLockboxCreditItem.getCouponDate1(), null) == 0) && (this._couponDate2.compare(localLockboxCreditItem.getCouponDate2(), null) == 0) && (this._immediateFloat.compareTo(localLockboxCreditItem.getImmediateFloat()) == 0) && (this._oneDayFloat.compareTo(localLockboxCreditItem.getOneDayFloat()) == 0) && (this._twoDayFloat.compareTo(localLockboxCreditItem.getTwoDayFloat()) == 0) && (this._valueDateTime.compare(localLockboxCreditItem.getValueDateTime(), null) == 0);
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOCKBOX_CREDIT_ITEM");
    XMLHandler.appendTag(localStringBuffer, "ACCT_ID", this._acctID);
    XMLHandler.appendTag(localStringBuffer, "ACCT_NUM", this._acctNumber);
    XMLHandler.appendTag(localStringBuffer, "BANK_ID", this._bankID);
    XMLHandler.appendTag(localStringBuffer, "ROUTING_NUM", this._routingNumber);
    XMLHandler.appendTag(localStringBuffer, "LOCKBOX_NUM", this._lockboxNumber);
    XMLHandler.appendTag(localStringBuffer, "ITEM_INDEX", Long.toString(this._itemIndex));
    XMLHandler.appendTag(localStringBuffer, "ITEM_ID", Integer.toString(this._itemID));
    if (this._processDate != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "PROCESS_DATE");
      localStringBuffer.append(this._processDate.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "PROCESS_DATE");
    }
    XMLHandler.appendTag(localStringBuffer, "DOC_TYPE", this._docType);
    XMLHandler.appendTag(localStringBuffer, "PAYER", this._payer);
    if (this._checkAmt != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "CHECK_AMT");
      localStringBuffer.append(this._checkAmt.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "CHECK_AMT");
    }
    XMLHandler.appendTag(localStringBuffer, "CHECK_NUM", this._checkNumber);
    if (this._checkDate != null) {
      XMLHandler.appendAggregate(localStringBuffer, "CHECK_DATE", this._checkDate.getXML());
    }
    XMLHandler.appendTag(localStringBuffer, "COUPON_ACCT_NUM", this._couponAcctNumber);
    if (this._couponAmt1 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "COUPON_AMT1");
      localStringBuffer.append(this._couponAmt1.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "COUPON_AMT1");
    }
    if (this._couponAmt2 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "COUPON_AMT2");
      localStringBuffer.append(this._couponAmt2.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "COUPON_AMT2");
    }
    if (this._couponDate1 != null) {
      XMLHandler.appendAggregate(localStringBuffer, "COUPON_DATE1", this._couponDate1.getXML());
    }
    if (this._couponDate2 != null) {
      XMLHandler.appendAggregate(localStringBuffer, "COUPON_DATE2", this._couponDate2.getXML());
    }
    XMLHandler.appendTag(localStringBuffer, "COUPON_REF_NUM", this._couponRefNumber);
    XMLHandler.appendTag(localStringBuffer, "CHECK_RTN_NUM", this._checkRtnNumber);
    XMLHandler.appendTag(localStringBuffer, "CHECK_ACCT_NUM", this._checkAcctNumber);
    XMLHandler.appendTag(localStringBuffer, "LOCKBOX_WORK_TYPE", this._lockboxWorkType);
    XMLHandler.appendTag(localStringBuffer, "LOCKBOX_BAT_NUM", this._lockboxBatNumber);
    XMLHandler.appendTag(localStringBuffer, "LOCKBOX_SEQ_NUM", this._lockboxSeqNumber);
    XMLHandler.appendTag(localStringBuffer, "MEMO", this._memo);
    if (this._immediateFloat != null) {
      XMLHandler.appendAggregate(localStringBuffer, "IMMEDIATE_FLOAT", this._immediateFloat.getXML());
    }
    if (this._oneDayFloat != null) {
      XMLHandler.appendAggregate(localStringBuffer, "ONE_DAY_FLOAT", this._oneDayFloat.getXML());
    }
    if (this._twoDayFloat != null) {
      XMLHandler.appendAggregate(localStringBuffer, "TWO_DAY_FLOAT", this._twoDayFloat.getXML());
    }
    if (this._valueDateTime != null) {
      XMLHandler.appendAggregate(localStringBuffer, "AS_OF_DATE", this._valueDateTime.getXML());
    }
    XMLHandler.appendTag(localStringBuffer, "BANK_REF_NUM", this._bankRefNumber);
    XMLHandler.appendTag(localStringBuffer, "CUSTOMER_REF_NUM", this._customerRefNumber);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "LOCKBOX_CREDIT_ITEM");
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
  
  public void toDelimitedFormat(StringBuffer paramStringBuffer, char paramChar) {}
  
  private class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      LockboxCreditItem.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      Object localObject;
      if (paramString.equalsIgnoreCase("PROCESS_DATE"))
      {
        localObject = new DateTime(LockboxCreditItem.this.locale);
        ((DateTime)localObject).continueXMLParsing(getHandler());
        LockboxCreditItem.this._processDate = ((DateTime)localObject);
      }
      else if (paramString.equalsIgnoreCase("CHECK_AMT"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxCreditItem.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxCreditItem.this._checkAmt = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("CHECK_DATE"))
      {
        localObject = new DateTime(LockboxCreditItem.this.locale);
        ((DateTime)localObject).continueXMLParsing(getHandler());
        LockboxCreditItem.this._checkDate = ((DateTime)localObject);
      }
      else if (paramString.equalsIgnoreCase("COUPON_AMT1"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxCreditItem.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxCreditItem.this._couponAmt1 = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("COUPON_AMT2"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxCreditItem.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxCreditItem.this._couponAmt2 = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("COUPON_DATE1"))
      {
        localObject = new DateTime(LockboxCreditItem.this.locale);
        ((DateTime)localObject).continueXMLParsing(getHandler());
        LockboxCreditItem.this._couponDate1 = ((DateTime)localObject);
      }
      else if (paramString.equalsIgnoreCase("COUPON_DATE2"))
      {
        localObject = new DateTime(LockboxCreditItem.this.locale);
        ((DateTime)localObject).continueXMLParsing(getHandler());
        LockboxCreditItem.this._couponDate2 = ((DateTime)localObject);
      }
      else if (paramString.equalsIgnoreCase("IMMEDIATE_FLOAT"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxCreditItem.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxCreditItem.this._immediateFloat = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("ONE_DAY_FLOAT"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxCreditItem.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxCreditItem.this._oneDayFloat = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("TWO_DAY_FLOAT"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxCreditItem.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxCreditItem.this._twoDayFloat = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("AS_OF_DATE"))
      {
        localObject = new DateTime(LockboxCreditItem.this.locale);
        ((DateTime)localObject).continueXMLParsing(getHandler());
        LockboxCreditItem.this._valueDateTime = ((DateTime)localObject);
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.lockbox.LockboxCreditItem
 * JD-Core Version:    0.7.0.1
 */