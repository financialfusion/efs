package com.ffusion.beans.lockbox;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.util.Locale;
import java.util.StringTokenizer;

public class LockboxSummary
  extends ExtendABean
{
  public static final String STR_OUTER_TAG = "LOCKBOX_SUMMARY";
  protected LockboxAccount _lockboxAcct;
  protected Currency _totalLockboxCredits;
  protected DateTime _summaryDate;
  protected int _totalNumLockboxCredits = -1;
  protected Currency _totalLockboxDebits;
  protected int _totalNumLockboxDebits = -1;
  protected Currency _immediateFloat;
  protected Currency _oneDayFloat;
  protected Currency _twoDayFloat;
  
  public LockboxSummary() {}
  
  public LockboxSummary(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public LockboxAccount getLockboxAccount()
  {
    return this._lockboxAcct;
  }
  
  public String getLockboxAccountID()
  {
    return this._lockboxAcct.getAccountID();
  }
  
  public String getLockboxAccountNumber()
  {
    return this._lockboxAcct.getAccountNumber();
  }
  
  public String getLockboxAccountNickname()
  {
    return this._lockboxAcct.getNickname();
  }
  
  public Currency getTotalLockboxCredits()
  {
    return this._totalLockboxCredits;
  }
  
  public DateTime getSummaryDate()
  {
    return this._summaryDate;
  }
  
  public int getTotalNumLockboxCredits()
  {
    return this._totalNumLockboxCredits;
  }
  
  public Currency getTotalLockboxDebits()
  {
    return this._totalLockboxDebits;
  }
  
  public int getTotalNumLockboxDebits()
  {
    return this._totalNumLockboxDebits;
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
  
  public void setLockboxAccount(LockboxAccount paramLockboxAccount)
  {
    this._lockboxAcct = paramLockboxAccount;
  }
  
  public void setSummaryDate(DateTime paramDateTime)
  {
    this._summaryDate = paramDateTime;
  }
  
  public void setTotalLockboxCredits(Currency paramCurrency)
  {
    this._totalLockboxCredits = paramCurrency;
  }
  
  public void setTotalNumLockboxCredits(int paramInt)
  {
    this._totalNumLockboxCredits = paramInt;
  }
  
  public void setTotalLockboxDebits(Currency paramCurrency)
  {
    this._totalLockboxDebits = paramCurrency;
  }
  
  public void setTotalNumLockboxDebits(int paramInt)
  {
    this._totalNumLockboxDebits = paramInt;
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
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("_lockboxAcct = ").append(this._lockboxAcct);
    localStringBuffer.append(",_summaryDate = ").append(this._summaryDate);
    localStringBuffer.append(",_totalNumLockboxCredits = ").append(this._totalNumLockboxCredits);
    if (this._totalNumLockboxCredits != 0) {
      localStringBuffer.append(",_totalLockboxCredits = ").append(this._totalLockboxCredits);
    }
    localStringBuffer.append(",_totalNumLockboxDebits = ").append(this._totalNumLockboxDebits);
    if (this._totalNumLockboxDebits != 0) {
      localStringBuffer.append(",_totalLockboxDebits = ").append(this._totalLockboxDebits);
    }
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
      if ((!str1.equalsIgnoreCase("LOCKBOX_ACCT")) && (!str1.equalsIgnoreCase("TOTAL_CREDITS")))
      {
        if (str1.equalsIgnoreCase("NUM_CREDITS")) {
          try
          {
            int i = Integer.parseInt(str2);
            return this._totalNumLockboxCredits == i;
          }
          catch (NumberFormatException localNumberFormatException1)
          {
            return false;
          }
        }
        if (!str1.equalsIgnoreCase("TOTAL_DEBITS"))
        {
          if (str1.equalsIgnoreCase("NUM_DEBITS")) {
            try
            {
              int j = Integer.parseInt(str2);
              return this._totalNumLockboxDebits == j;
            }
            catch (NumberFormatException localNumberFormatException2)
            {
              return false;
            }
          }
          if ((str1.equalsIgnoreCase("AS_OF_DATE")) || (str1.equalsIgnoreCase("IMMEDIATE_FLOAT")) || (str1.equalsIgnoreCase("ONE_DAY_FLOAT")) || (!str1.equalsIgnoreCase("TWO_DAY_FLOAT"))) {}
        }
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "LOCKBOX_ACCT");
  }
  
  public int compare(Object paramObject, String paramString)
    throws ClassCastException
  {
    LockboxSummary localLockboxSummary = (LockboxSummary)paramObject;
    try
    {
      if (paramString.equalsIgnoreCase("LOCKBOX_ACCT")) {
        return this._lockboxAcct.compareTo(localLockboxSummary._lockboxAcct);
      }
      if (paramString.equalsIgnoreCase("TOTAL_CREDITS")) {
        return this._totalLockboxCredits.compareTo(localLockboxSummary._totalLockboxCredits);
      }
      if (paramString.equalsIgnoreCase("NUM_CREDITS")) {
        return this._totalNumLockboxCredits - localLockboxSummary._totalNumLockboxCredits;
      }
      if (paramString.equalsIgnoreCase("TOTAL_DEBITS")) {
        return this._totalLockboxDebits.compareTo(localLockboxSummary._totalLockboxDebits);
      }
      if (paramString.equalsIgnoreCase("NUM_DEBITS")) {
        return this._totalNumLockboxDebits - localLockboxSummary._totalNumLockboxDebits;
      }
      if (paramString.equalsIgnoreCase("AS_OF_DATE")) {
        return this._summaryDate.compare(localLockboxSummary._summaryDate, null);
      }
      if ((paramString.equalsIgnoreCase("IMMEDIATE_FLOAT")) && (this._immediateFloat != null) && (localLockboxSummary._immediateFloat != null)) {
        return this._immediateFloat.compareTo(localLockboxSummary._immediateFloat);
      }
      if (paramString.equalsIgnoreCase("ONE_DAY_FLOAT")) {
        return this._oneDayFloat.compareTo(localLockboxSummary._oneDayFloat);
      }
      if (paramString.equalsIgnoreCase("TWO_DAY_FLOAT")) {
        return this._twoDayFloat.compareTo(localLockboxSummary._twoDayFloat);
      }
    }
    catch (Exception localException) {}
    return super.compare(paramObject, paramString);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    if (paramString1.equals("NUM_CREDITS")) {
      try
      {
        int i = Integer.parseInt(paramString2);
        this._totalNumLockboxCredits = i;
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        DebugLog.throwing("Invalid lockbox total number of credits: not a number", localNumberFormatException1);
        return false;
      }
    } else if (paramString1.equals("NUM_DEBITS")) {
      try
      {
        int j = Integer.parseInt(paramString2);
        this._totalNumLockboxDebits = j;
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        DebugLog.throwing("Invalid lockbox total number of debits: not a number", localNumberFormatException2);
        return false;
      }
    }
    return super.set(paramString1, paramString2);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LockboxSummary))) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    LockboxSummary localLockboxSummary = (LockboxSummary)paramObject;
    return (this._totalNumLockboxDebits == localLockboxSummary._totalNumLockboxDebits) && (this._totalNumLockboxCredits == localLockboxSummary._totalNumLockboxCredits) && (this._lockboxAcct.compareTo(localLockboxSummary._lockboxAcct) == 0) && (this._totalLockboxCredits.compareTo(localLockboxSummary._totalLockboxCredits) == 0) && (this._totalLockboxDebits.compareTo(localLockboxSummary._totalLockboxDebits) == 0) && (this._summaryDate.compare(localLockboxSummary._summaryDate, null) == 0) && (this._immediateFloat.compareTo(localLockboxSummary._immediateFloat) == 0) && (this._oneDayFloat.compareTo(localLockboxSummary._oneDayFloat) == 0) && (this._twoDayFloat.compareTo(localLockboxSummary._twoDayFloat) == 0);
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOCKBOX_SUMMARY");
    if (this._lockboxAcct != null) {
      XMLHandler.appendAggregate(localStringBuffer, "LOCKBOX_ACCT", this._lockboxAcct.getXML());
    }
    if (this._totalLockboxCredits != null) {
      XMLHandler.appendAggregate(localStringBuffer, "TOTAL_CREDITS", this._totalLockboxCredits.getXML());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_CREDITS", Integer.toString(this._totalNumLockboxCredits));
    if (this._totalLockboxDebits != null) {
      XMLHandler.appendAggregate(localStringBuffer, "TOTAL_DEBITS", this._totalLockboxDebits.getXML());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_DEBITS", Integer.toString(this._totalNumLockboxDebits));
    if (this._immediateFloat != null) {
      XMLHandler.appendAggregate(localStringBuffer, "IMMEDIATE_FLOAT", this._immediateFloat.getXML());
    }
    if (this._oneDayFloat != null) {
      XMLHandler.appendAggregate(localStringBuffer, "ONE_DAY_FLOAT", this._oneDayFloat.getXML());
    }
    if (this._twoDayFloat != null) {
      XMLHandler.appendAggregate(localStringBuffer, "TWO_DAY_FLOAT", this._twoDayFloat.getXML());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "LOCKBOX_SUMMARY");
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
      LockboxSummary.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      Object localObject;
      if (paramString.equalsIgnoreCase("LOCKBOX_ACCT"))
      {
        localObject = new LockboxAccount(LockboxSummary.this.locale);
        ((LockboxAccount)localObject).continueXMLParsing(getHandler());
        LockboxSummary.this._lockboxAcct = ((LockboxAccount)localObject);
      }
      else if (paramString.equalsIgnoreCase("TOTAL_CREDITS"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxSummary.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxSummary.this._totalLockboxCredits = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("TOTAL_DEBITS"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxSummary.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxSummary.this._totalLockboxDebits = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("IMMEDIATE_FLOAT"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxSummary.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxSummary.this._immediateFloat = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("ONE_DAY_FLOAT"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxSummary.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxSummary.this._oneDayFloat = ((Currency)localObject);
      }
      else if (paramString.equalsIgnoreCase("TWO_DAY_FLOAT"))
      {
        localObject = new Currency();
        ((Currency)localObject).setLocale(LockboxSummary.this.locale);
        ((Currency)localObject).continueXMLParsing(getHandler());
        LockboxSummary.this._twoDayFloat = ((Currency)localObject);
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.lockbox.LockboxSummary
 * JD-Core Version:    0.7.0.1
 */