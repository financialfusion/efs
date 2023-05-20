package com.ffusion.beans.cashcon;

import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.ExtendABean;
import com.ffusion.util.beans.LocalizableAccountID;
import com.ffusion.util.settings.AccountSettings;
import com.ffusion.util.settings.SystemException;

public class CashConAccount
  extends ExtendABean
  implements CashConAccountTypes, Comparable
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.cashcon.resources";
  public static final String KEY_ACCOUNT_DISPLAY_TEXT = "AccountDisplayText";
  public static final String KEY_CC_ACCOUNT_DISPLAY_TEXT = "CashConAccountDisplayText";
  public static final String KEY_ACCOUNT_TYPE = "CashConAccountType";
  private String jdField_int;
  private String jdField_case;
  private String a;
  private String jdField_if;
  private String jdField_for;
  private int jdField_do = 0;
  private String jdField_try;
  private boolean jdField_new;
  
  public void set(CashConAccount paramCashConAccount)
  {
    this.jdField_int = paramCashConAccount.getBPWID();
    this.jdField_case = paramCashConAccount.getBankName();
    this.a = paramCashConAccount.getNumber();
    this.jdField_if = paramCashConAccount.getNickname();
    this.jdField_for = paramCashConAccount.getCurrency();
    this.jdField_do = paramCashConAccount.getType();
    this.jdField_try = paramCashConAccount.getRoutingNumber();
    this.jdField_new = paramCashConAccount.getDefaultAccount();
  }
  
  public String getBPWID()
  {
    return this.jdField_int;
  }
  
  public String getBankName()
  {
    return this.jdField_case;
  }
  
  public String getNumber()
  {
    return this.a;
  }
  
  public String getNickname()
  {
    return this.jdField_if;
  }
  
  public String getCurrency()
  {
    return this.jdField_for;
  }
  
  public int getType()
  {
    return this.jdField_do;
  }
  
  public String getTypeString()
  {
    try
    {
      return ResourceUtil.getString("CashConAccountType" + this.jdField_do, "com.ffusion.beans.cashcon.resources", this.locale);
    }
    catch (Exception localException) {}
    return String.valueOf(this.jdField_do);
  }
  
  public String getRoutingNumber()
  {
    return this.jdField_try;
  }
  
  public boolean getDefaultAccount()
  {
    return this.jdField_new;
  }
  
  public void setBPWID(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public void setBankName(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public void setNumber(String paramString)
  {
    this.a = paramString;
  }
  
  public void setNickname(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public void setCurrency(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public void setType(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public void setDefaultAccount(boolean paramBoolean)
  {
    this.jdField_new = paramBoolean;
  }
  
  public String getDisplayText()
  {
    String str = null;
    try
    {
      str = AccountSettings.buildAccountDisplayText(getNumber(), "com.ffusion.beans.cashcon.resources", "AccountDisplayText", "com.ffusion.beans.cashcon.resources", "CashConAccountType" + getType(), this.locale);
    }
    catch (SystemException localSystemException)
    {
      str = getNumber();
    }
    return str;
  }
  
  public int compareTo(Object paramObject)
  {
    return compare(paramObject, "NUMBER");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    CashConAccount localCashConAccount = (CashConAccount)paramObject;
    if (paramString.equals("NUMBER")) {
      return compareStringsIgnoreCase(this.a, localCashConAccount.getNumber());
    }
    if (paramString.equals("NICKNAME")) {
      return compareStringsIgnoreCase(this.jdField_if, localCashConAccount.getNickname());
    }
    if (paramString.equals("BANKNAME")) {
      return compareStringsIgnoreCase(this.jdField_case, localCashConAccount.getBankName());
    }
    if (paramString.equals("TYPE"))
    {
      if (this.jdField_do < localCashConAccount.getType()) {
        return -1;
      }
      if (this.jdField_do > localCashConAccount.getType()) {
        return 1;
      }
      return 0;
    }
    if (paramString.equals("ROUTINGNUMBER")) {
      return compareStringsIgnoreCase(this.jdField_try, localCashConAccount.getRoutingNumber());
    }
    return super.compare(paramObject, paramString);
  }
  
  public LocalizableAccountID buildLocalizableAccountID()
  {
    return new LocalizableAccountID(getNumber(), "com.ffusion.beans.cashcon.resources", "AccountDisplayText", "com.ffusion.beans.cashcon.resources", "CashConAccountType" + getType());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.CashConAccount
 * JD-Core Version:    0.7.0.1
 */