package com.ffusion.beans.lockbox;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.text.Collator;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class LockboxAccount
  extends ExtendABean
{
  public static final String STR_OUTER_TAG = "LOCKBOX_ACCT";
  protected String _acctID = null;
  protected String _acctNumber = null;
  protected String _bankID = null;
  protected String _nickname = null;
  protected String _currencyType = null;
  protected String _routingNum = null;
  
  public LockboxAccount() {}
  
  public LockboxAccount(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public String getAccountID()
  {
    return this._acctID;
  }
  
  public void setAccountID(String paramString)
  {
    if ((this._acctID != null) && (!this._acctID.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "LockBoxAccount.setAccountID.  Cannot modify the value of account ID.");
      return;
    }
    this._acctID = paramString;
  }
  
  public String getAccountNumber()
  {
    return this._acctNumber;
  }
  
  public void setAccountNumber(String paramString)
  {
    if ((this._acctNumber != null) && (!this._acctNumber.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "LockBoxAccount.setAccountNumber.  Cannot modify the value of account number.");
      return;
    }
    this._acctNumber = paramString;
  }
  
  public String getBankID()
  {
    return this._bankID;
  }
  
  public void setBankID(String paramString)
  {
    if ((this._bankID != null) && (!this._bankID.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "LockBoxAccount.setBankID.  Cannot modify the value of bank ID.");
      return;
    }
    this._bankID = paramString;
  }
  
  public String getRoutingNumber()
  {
    return this._routingNum;
  }
  
  public void setRoutingNumber(String paramString)
  {
    if ((this._routingNum != null) && (!this._routingNum.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "LockBoxAccount.setRoutingNumber.  Cannot modify the value of routing number.");
      return;
    }
    this._routingNum = paramString;
  }
  
  public String getNickname()
  {
    return this._nickname;
  }
  
  public void setNickname(String paramString)
  {
    this._nickname = paramString;
  }
  
  public String getCurrencyType()
  {
    return this._currencyType;
  }
  
  public void setCurrencyType(String paramString)
  {
    this._currencyType = paramString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("acctID = ").append(this._acctID);
    localStringBuffer.append("acct# = ").append(this._acctNumber);
    localStringBuffer.append(",bank id = ").append(this._bankID);
    localStringBuffer.append(",routing# = ").append(this._routingNum);
    localStringBuffer.append(",nickname = ").append(this._nickname);
    localStringBuffer.append(",currencytype = ").append(this._currencyType).append(".");
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
      if (str1.equalsIgnoreCase("NICKNAME")) {
        return this._nickname.equals(str2);
      }
      if (str1.equalsIgnoreCase("BANK_ID")) {
        return this._bankID.equals(str2);
      }
      if (str1.equalsIgnoreCase("ROUTING_NUM")) {
        return this._routingNum.equals(str2);
      }
      if (str1.equalsIgnoreCase("CURRENCY_TYPE")) {
        return this._currencyType.equals(str2);
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
    LockboxAccount localLockboxAccount = (LockboxAccount)paramObject;
    Collator localCollator = doGetCollator();
    if (paramString.equalsIgnoreCase("ACCT_ID")) {
      return localCollator.compare(this._acctID, localLockboxAccount._acctID);
    }
    if (paramString.equalsIgnoreCase("ACCT_NUM")) {
      return localCollator.compare(this._acctNumber, localLockboxAccount._acctNumber);
    }
    if (paramString.equalsIgnoreCase("NICKNAME")) {
      return localCollator.compare(this._nickname, localLockboxAccount._nickname);
    }
    if (paramString.equalsIgnoreCase("BANK_ID")) {
      return localCollator.compare(this._bankID, localLockboxAccount._bankID);
    }
    if (paramString.equalsIgnoreCase("ROUTING_NUM")) {
      return localCollator.compare(this._routingNum, localLockboxAccount._routingNum);
    }
    if (paramString.equalsIgnoreCase("CURRENCY_TYPE")) {
      return localCollator.compare(this._currencyType, localLockboxAccount._currencyType);
    }
    return super.compare(paramObject, paramString);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    if (paramString1.equals("ACCT_ID"))
    {
      setAccountID(paramString2);
      return true;
    }
    if (paramString1.equals("ACCT_NUM"))
    {
      setAccountNumber(paramString2);
      return true;
    }
    if (paramString1.equals("NICKNAME"))
    {
      this._nickname = paramString2;
      return true;
    }
    if (paramString1.equals("BANK_ID"))
    {
      setBankID(paramString2);
      return true;
    }
    if (paramString1.equals("ROUTING_NUM"))
    {
      setRoutingNumber(paramString2);
      return true;
    }
    if (paramString1.equals("CURRENCY_TYPE"))
    {
      this._currencyType = paramString2;
      return true;
    }
    return super.set(paramString1, paramString2);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LockboxAccount))) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    LockboxAccount localLockboxAccount = (LockboxAccount)paramObject;
    return (areFieldStringEqual(this._acctID, localLockboxAccount.getAccountID())) && (areFieldStringEqual(this._acctNumber, localLockboxAccount.getAccountNumber())) && (areFieldStringEqual(this._bankID, localLockboxAccount.getBankID())) && (areFieldStringEqual(this._routingNum, localLockboxAccount.getRoutingNumber())) && (areFieldStringEqual(this._nickname, localLockboxAccount.getNickname())) && (areFieldStringEqual(this._currencyType, localLockboxAccount.getCurrencyType()));
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOCKBOX_ACCT");
    if (this._acctID != null) {
      XMLHandler.appendTag(localStringBuffer, "ACCT_ID", this._acctID);
    }
    if (this._acctNumber != null) {
      XMLHandler.appendTag(localStringBuffer, "ACCT_NUM", this._acctNumber);
    }
    if (this._bankID != null) {
      XMLHandler.appendTag(localStringBuffer, "BANK_ID", this._bankID);
    }
    if (this._routingNum != null) {
      XMLHandler.appendTag(localStringBuffer, "ROUTING_NUM", this._routingNum);
    }
    if (this._nickname != null) {
      XMLHandler.appendTag(localStringBuffer, "NICKNAME", this._nickname);
    }
    if (this._currencyType != null) {
      XMLHandler.appendTag(localStringBuffer, "CURRENCY_TYPE", this._currencyType);
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "LOCKBOX_ACCT");
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
      LockboxAccount.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.lockbox.LockboxAccount
 * JD-Core Version:    0.7.0.1
 */