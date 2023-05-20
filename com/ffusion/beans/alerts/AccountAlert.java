package com.ffusion.beans.alerts;

import com.ffusion.util.XMLHandler;
import java.util.Locale;

public class AccountAlert
  extends UserAlert
{
  public static final String ACCOUNT_ALERT = "ACCOUNT_ALERT";
  public static final String ACCOUNT_ID = "ACCOUNT_ID";
  public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
  public static final String NICKNAME = "NICKNAME";
  public static final String ROUTING_NUMBER = "ROUTING_NUMBER";
  public static final String KEY_ACCOUNT_VALUE_ALL_ACCOUNTS = "All Accounts";
  public static final String KEY_BALANCE_MIN_AMOUNT = "MIN_AMOUNT";
  public static final String KEY_BALANCE_MAX_AMOUNT = "MAX_AMOUNT";
  public static final String KEY_TRANS_TYPE = "TRANSACTION TYPE";
  public static final String KEY_TRANS_COMPARE = "COMPARE";
  public static final String KEY_TRANS_AMOUNT = "AMOUNT";
  public static final int KEY_TRANS_COMPARE_VALUE_ANY = 1;
  public static final int KEY_TRANS_COMPARE_VALUE_LESS = 2;
  public static final int KEY_TRANS_COMPARE_VALUE_MORE = 3;
  public static final int KEY_TRANS_COMPARE_VALUE_EXACT = 4;
  public static final String KEY_TRANS_COMPARE_VALUE_ANY_STR = "1";
  public static final String KEY_TRANS_COMPARE_VALUE_LESS_STR = "2";
  public static final String KEY_TRANS_COMPARE_VALUE_MORE_STR = "3";
  public static final String KEY_TRANS_COMPARE_VALUE_EXACT_STR = "4";
  private String jdField_if = null;
  private int a = -2147483648;
  private String jdField_for = null;
  private String jdField_do = null;
  
  public AccountAlert() {}
  
  public AccountAlert(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setAccountId(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getAccountId()
  {
    return this.jdField_if;
  }
  
  public void setAccountType(int paramInt)
  {
    this.a = paramInt;
  }
  
  public void setAccountType(String paramString)
  {
    try
    {
      this.a = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.a = -2147483648;
    }
  }
  
  public String getAccountType()
  {
    return String.valueOf(this.a);
  }
  
  public int getAccountTypeValue()
  {
    return this.a;
  }
  
  public void setNickname(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getNickname()
  {
    return this.jdField_for;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getRoutingNumber()
  {
    return this.jdField_do;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ACCOUNT_ID")) {
      this.jdField_if = paramString2;
    } else if (paramString1.equals("ACCOUNT_TYPE")) {
      try
      {
        this.a = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        this.a = -2147483648;
        bool = false;
      }
    } else if (paramString1.equals("NICKNAME")) {
      this.jdField_for = paramString2;
    } else if (paramString1.equals("ROUTING_NUMBER")) {
      this.jdField_do = paramString2;
    } else {
      super.set(paramString1, paramString2);
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACCOUNT_ALERT");
    XMLHandler.appendTag(localStringBuffer, "ACCOUNT_ID", this.jdField_if);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNT_TYPE", this.a);
    XMLHandler.appendTag(localStringBuffer, "NICKNAME", this.jdField_for);
    XMLHandler.appendTag(localStringBuffer, "ROUTING_NUMBER", this.jdField_do);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACCOUNT_ALERT");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.alerts.AccountAlert
 * JD-Core Version:    0.7.0.1
 */