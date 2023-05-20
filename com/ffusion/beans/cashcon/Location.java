package com.ffusion.beans.cashcon;

import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import java.util.Locale;

public class Location
  extends ExtendABean
  implements Comparable, CashConAccountTypes
{
  public static final String BEAN_NAME = Location.class.getName();
  private int l;
  private String n;
  private String e;
  private String d;
  private boolean m;
  private String t;
  private String i;
  private String j;
  private int o;
  private String b;
  private String jdField_null;
  private String g;
  private String jdField_goto;
  private String jdField_long;
  private String c;
  private Currency p;
  private Currency u;
  private Currency jdField_else;
  private Currency h;
  private boolean r;
  private boolean q;
  private boolean s;
  private String k;
  private String f;
  private String jdField_char = null;
  private String jdField_void = null;
  
  public Location(Locale paramLocale)
  {
    setLocale(paramLocale);
  }
  
  public int getDivisionID()
  {
    return this.l;
  }
  
  public void setDivisionID(int paramInt)
  {
    this.l = paramInt;
  }
  
  public String getLocationName()
  {
    return this.n;
  }
  
  public String getLocationNameLowerCase()
  {
    return this.n == null ? null : this.n.toLowerCase();
  }
  
  public void setLocationName(String paramString)
  {
    this.n = paramString;
  }
  
  public String getLocationID()
  {
    return this.e;
  }
  
  public String getLocationIDLowerCase()
  {
    return this.e == null ? null : this.e.toLowerCase();
  }
  
  public void setLocationID(String paramString)
  {
    this.e = paramString;
  }
  
  public String getLocationBPWID()
  {
    return this.d;
  }
  
  public void setLocationBPWID(String paramString)
  {
    this.d = paramString;
  }
  
  public boolean isActive()
  {
    return this.m;
  }
  
  public String getActive()
  {
    return String.valueOf(this.m);
  }
  
  public void setActive(boolean paramBoolean)
  {
    this.m = paramBoolean;
  }
  
  public String getLocalRoutingNumber()
  {
    return this.t;
  }
  
  public void setLocalRoutingNumber(String paramString)
  {
    this.t = paramString;
  }
  
  public String getLocalBankName()
  {
    return this.i;
  }
  
  public void setLocalBankName(String paramString)
  {
    this.i = paramString;
  }
  
  public String getLocalAccountNumber()
  {
    return this.j;
  }
  
  public void setLocalAccountNumber(String paramString)
  {
    this.j = paramString;
  }
  
  public int getLocalAccountType()
  {
    return this.o;
  }
  
  public void setLocalAccountType(int paramInt)
  {
    this.o = paramInt;
  }
  
  public String getCashConCompanyName()
  {
    return this.b;
  }
  
  public void setCashConCompanyName(String paramString)
  {
    this.b = paramString;
  }
  
  public String getCashConCompanyBPWID()
  {
    return this.jdField_null;
  }
  
  public void setCashConCompanyBPWID(String paramString)
  {
    this.jdField_null = paramString;
  }
  
  public void setLogId(String paramString)
  {
    this.f = paramString;
  }
  
  public String getLogId()
  {
    return this.f;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.k = paramString;
  }
  
  public String getSubmittedBy()
  {
    return this.k;
  }
  
  public String getConcAccount()
  {
    return this.g;
  }
  
  public void setConcAccount(String paramString)
  {
    this.g = paramString;
  }
  
  public String getConcAccountBPWID()
  {
    return this.jdField_goto;
  }
  
  public void setConcAccountBPWID(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  public String getDisbAccount()
  {
    return this.jdField_long;
  }
  
  public void setDisbAccount(String paramString)
  {
    this.jdField_long = paramString;
  }
  
  public String getDisbAccountBPWID()
  {
    return this.c;
  }
  
  public void setDisbAccountBPWID(String paramString)
  {
    this.c = paramString;
  }
  
  public Currency getDepositMinimum()
  {
    return this.p;
  }
  
  public void setDepositMinimum(Currency paramCurrency)
  {
    this.p = paramCurrency;
  }
  
  public Currency getDepositMaximum()
  {
    return this.u;
  }
  
  public void setDepositMaximum(Currency paramCurrency)
  {
    this.u = paramCurrency;
  }
  
  public Currency getAnticDeposit()
  {
    return this.jdField_else;
  }
  
  public void setAnticDeposit(Currency paramCurrency)
  {
    this.jdField_else = paramCurrency;
  }
  
  public Currency getThreshDeposit()
  {
    return this.h;
  }
  
  public void setThreshDeposit(Currency paramCurrency)
  {
    this.h = paramCurrency;
  }
  
  public boolean getConsolidateDeposits()
  {
    return this.r;
  }
  
  public void setConsolidateDeposits(boolean paramBoolean)
  {
    this.r = paramBoolean;
  }
  
  public boolean getDepositPrenote()
  {
    return this.q;
  }
  
  public void setDepositPrenote(boolean paramBoolean)
  {
    this.q = paramBoolean;
  }
  
  public boolean getDisbursementPrenote()
  {
    return this.s;
  }
  
  public void setDisbursementPrenote(boolean paramBoolean)
  {
    this.s = paramBoolean;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "LOCATION_BPWID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Location localLocation = (Location)paramObject;
    int i1 = 1;
    String str1;
    String str3;
    if (paramString.equals("LOCATION_NAME"))
    {
      str1 = this.n;
      str3 = localLocation.getLocationName();
      i1 = compareStrings(str1, str3);
    }
    else if (paramString.equals("LOCATION_ID"))
    {
      str1 = this.e;
      str3 = localLocation.getLocationID();
      i1 = compareStrings(str1, str3);
    }
    else if (paramString.equals("LOG_ID"))
    {
      str1 = this.f;
      str3 = localLocation.getLogId();
      i1 = compareStrings(str1, str3);
    }
    else if (paramString.equals("SUBMITTED_BY"))
    {
      str1 = this.k;
      str3 = localLocation.getSubmittedBy();
      i1 = compareStrings(str1, str3);
    }
    else if (paramString.equals("LOCATION_BPWID"))
    {
      str1 = this.d;
      str3 = localLocation.getLocationBPWID();
      i1 = compareStrings(str1, str3);
    }
    else if (paramString.equals("ACTIVE"))
    {
      boolean bool1 = this.m;
      boolean bool3 = localLocation.isActive();
      if (bool1 == bool3) {
        i1 = 0;
      } else if (!bool1) {
        i1 = -1;
      } else {
        i1 = 1;
      }
    }
    else
    {
      String str2;
      String str4;
      if (paramString.equals("LOCAL_ROUTING_NUMBER"))
      {
        str2 = this.t;
        str4 = localLocation.getLocalRoutingNumber();
        i1 = compareStrings(str2, str4);
      }
      else if (paramString.equals("LOCAL_BANK_NAME"))
      {
        str2 = this.i;
        str4 = localLocation.getLocalBankName();
        i1 = compareStrings(str2, str4);
      }
      else if (paramString.equals("LOCAL_ACCOUNT_NUMBER"))
      {
        str2 = this.j;
        str4 = localLocation.getLocalAccountNumber();
        i1 = compareStrings(str2, str4);
      }
      else if (paramString.equals("LOCAL_ACCOUNT_TYPE"))
      {
        int i2 = this.o;
        int i3 = localLocation.getLocalAccountType();
        i1 = i2 - i3;
      }
      else
      {
        Object localObject1;
        Object localObject2;
        if (paramString.equals("CASHCON_COMPANY_NAME"))
        {
          localObject1 = this.b;
          localObject2 = localLocation.getCashConCompanyName();
          i1 = compareStrings((String)localObject1, (String)localObject2);
        }
        else if (paramString.equals("CASHCON_COMPANY_BPWID"))
        {
          localObject1 = this.jdField_null;
          localObject2 = localLocation.getCashConCompanyBPWID();
          i1 = compareStrings((String)localObject1, (String)localObject2);
        }
        else if (paramString.equals("CONC_ACCOUNT"))
        {
          localObject1 = this.g;
          localObject2 = localLocation.getConcAccount();
          i1 = compareStrings((String)localObject1, (String)localObject2);
        }
        else if (paramString.equals("CONC_ACCOUNT_BPWID"))
        {
          localObject1 = this.jdField_goto;
          localObject2 = localLocation.getConcAccountBPWID();
          i1 = compareStrings((String)localObject1, (String)localObject2);
        }
        else if (paramString.equals("DISB_ACCOUNT"))
        {
          localObject1 = this.jdField_long;
          localObject2 = localLocation.getDisbAccount();
          i1 = compareStrings((String)localObject1, (String)localObject2);
        }
        else if (paramString.equals("DISB_ACCOUNT_BPWID"))
        {
          localObject1 = this.c;
          localObject2 = localLocation.getDisbAccountBPWID();
          i1 = compareStrings((String)localObject1, (String)localObject2);
        }
        else if (paramString.equals("DEPOSIT_MINIMUM"))
        {
          localObject1 = this.p;
          localObject2 = localLocation.getDepositMinimum();
          if ((localObject1 == null) && (localObject2 == null)) {
            i1 = 0;
          } else if (localObject1 == null) {
            i1 = -1;
          } else if (localObject2 == null) {
            i1 = 1;
          } else {
            i1 = ((Currency)localObject1).compareTo((Currency)localObject2);
          }
        }
        else if (paramString.equals("DEPOSIT_MAXIMUM"))
        {
          localObject1 = this.u;
          localObject2 = localLocation.getDepositMaximum();
          if ((localObject1 == null) && (localObject2 == null)) {
            i1 = 0;
          } else if (localObject1 == null) {
            i1 = -1;
          } else if (localObject2 == null) {
            i1 = 1;
          } else {
            i1 = ((Currency)localObject1).compareTo((Currency)localObject2);
          }
        }
        else if (paramString.equals("ANTIC_DEPOSIT"))
        {
          localObject1 = this.jdField_else;
          localObject2 = localLocation.getAnticDeposit();
          if ((localObject1 == null) && (localObject2 == null)) {
            i1 = 0;
          } else if (localObject1 == null) {
            i1 = -1;
          } else if (localObject2 == null) {
            i1 = 1;
          } else {
            i1 = ((Currency)localObject1).compareTo((Currency)localObject2);
          }
        }
        else if (paramString.equals("THRESH_DEPOSIT"))
        {
          localObject1 = this.h;
          localObject2 = localLocation.getThreshDeposit();
          if ((localObject1 == null) && (localObject2 == null)) {
            i1 = 0;
          } else if (localObject1 == null) {
            i1 = -1;
          } else if (localObject2 == null) {
            i1 = 1;
          } else {
            i1 = ((Currency)localObject1).compareTo((Currency)localObject2);
          }
        }
        else
        {
          boolean bool2;
          boolean bool4;
          if (paramString.equals("CONSOLIDATE_DEPOSITS"))
          {
            bool2 = this.r;
            bool4 = localLocation.getConsolidateDeposits();
            if (bool2 == bool4) {
              i1 = 0;
            } else if (!bool2) {
              i1 = -1;
            } else {
              i1 = 1;
            }
          }
          else if (paramString.equals("DEPOSIT_PRENOTE"))
          {
            bool2 = this.q;
            bool4 = localLocation.getDepositPrenote();
            if (bool2 == bool4) {
              i1 = 0;
            } else if (!bool2) {
              i1 = -1;
            } else {
              i1 = 1;
            }
          }
          else if (paramString.equals("DISBURSEMENT_PRENOTE"))
          {
            bool2 = this.s;
            bool4 = localLocation.getDisbursementPrenote();
            if (bool2 == bool4) {
              i1 = 0;
            } else if (!bool2) {
              i1 = -1;
            } else {
              i1 = 1;
            }
          }
          else
          {
            i1 = super.compare(paramObject, paramString);
          }
        }
      }
    }
    return i1;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Location)) {
      return false;
    }
    if (compare(paramObject, "LOCATION_NAME") != 0) {
      return false;
    }
    if (compare(paramObject, "LOCATION_ID") != 0) {
      return false;
    }
    if (compare(paramObject, "SUBMITTED_BY") != 0) {
      return false;
    }
    if (compare(paramObject, "LOG_ID") != 0) {
      return false;
    }
    if (compare(paramObject, "LOCATION_BPWID") != 0) {
      return false;
    }
    if (compare(paramObject, "ACTIVE") != 0) {
      return false;
    }
    if (compare(paramObject, "LOCAL_ROUTING_NUMBER") != 0) {
      return false;
    }
    if (compare(paramObject, "LOCAL_BANK_NAME") != 0) {
      return false;
    }
    if (compare(paramObject, "LOCAL_ACCOUNT_TYPE") != 0) {
      return false;
    }
    if (compare(paramObject, "CASHCON_COMPANY_NAME") != 0) {
      return false;
    }
    if (compare(paramObject, "CASHCON_COMPANY_BPWID") != 0) {
      return false;
    }
    if (compare(paramObject, "CONC_ACCOUNT") != 0) {
      return false;
    }
    if (compare(paramObject, "CONC_ACCOUNT_BPWID") != 0) {
      return false;
    }
    if (compare(paramObject, "DISB_ACCOUNT") != 0) {
      return false;
    }
    if (compare(paramObject, "DISB_ACCOUNT_BPWID") != 0) {
      return false;
    }
    if (compare(paramObject, "DEPOSIT_MINIMUM") != 0) {
      return false;
    }
    if (compare(paramObject, "DEPOSIT_MAXIMUM") != 0) {
      return false;
    }
    if (compare(paramObject, "ANTIC_DEPOSIT") != 0) {
      return false;
    }
    if (compare(paramObject, "THRESH_DEPOSIT") != 0) {
      return false;
    }
    if (compare(paramObject, "CONSOLIDATE_DEPOSITS") != 0) {
      return false;
    }
    if (compare(paramObject, "DEPOSIT_PRENOTE") != 0) {
      return false;
    }
    return compare(paramObject, "DISBURSEMENT_PRENOTE") == 0;
  }
  
  public void set(Location paramLocation)
  {
    setLocale(paramLocation.locale);
    setLocationName(paramLocation.getLocationName());
    setLocationID(paramLocation.getLocationID());
    setLocationBPWID(paramLocation.getLocationBPWID());
    setActive(paramLocation.isActive());
    setLocalRoutingNumber(paramLocation.getLocalRoutingNumber());
    setLocalBankName(paramLocation.getLocalBankName());
    setLocalAccountNumber(paramLocation.getLocalAccountNumber());
    setLocalAccountType(paramLocation.getLocalAccountType());
    setCashConCompanyName(paramLocation.getCashConCompanyName());
    setCashConCompanyBPWID(paramLocation.getCashConCompanyBPWID());
    setConcAccount(paramLocation.getConcAccount());
    setConcAccountBPWID(paramLocation.getConcAccountBPWID());
    setDisbAccount(paramLocation.getDisbAccount());
    setDisbAccountBPWID(paramLocation.getDisbAccountBPWID());
    setDepositMinimum(paramLocation.getDepositMinimum());
    setDepositMaximum(paramLocation.getDepositMaximum());
    setAnticDeposit(paramLocation.getAnticDeposit());
    setThreshDeposit(paramLocation.getThreshDeposit());
    setConsolidateDeposits(paramLocation.getConsolidateDeposits());
    setDepositPrenote(paramLocation.getDepositPrenote());
    setDisbursementPrenote(paramLocation.getDisbursementPrenote());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("LOCATION_NAME")) {
        this.n = paramString2;
      } else if (paramString1.equals("LOCATION_ID")) {
        this.e = paramString2;
      } else if (paramString1.equals("SUBMITTED_BY")) {
        this.k = paramString2;
      } else if (paramString1.equals("LOG_ID")) {
        this.f = paramString2;
      } else if (paramString1.equals("LOCATION_BPWID")) {
        this.d = paramString2;
      } else if (paramString1.equals("ACTIVE")) {
        this.m = Boolean.valueOf(paramString2).booleanValue();
      } else if (paramString1.equals("LOCAL_ROUTING_NUMBER")) {
        this.t = paramString2;
      } else if (paramString1.equals("LOCAL_BANK_NAME")) {
        this.i = paramString2;
      } else if (paramString1.equals("LOCAL_ACCOUNT_NUMBER")) {
        this.j = paramString2;
      } else if (paramString1.equals("LOCAL_ACCOUNT_TYPE")) {
        this.o = Integer.parseInt(paramString2);
      } else if (paramString1.equals("CASHCON_COMPANY_NAME")) {
        this.b = paramString2;
      } else if (paramString1.equals("CASHCON_COMPANY_BPWID")) {
        this.jdField_null = paramString2;
      } else if (paramString1.equals("CONC_ACCOUNT")) {
        this.g = paramString2;
      } else if (paramString1.equals("CONC_ACCOUNT_BPWID")) {
        this.jdField_goto = paramString2;
      } else if (paramString1.equals("DISB_ACCOUNT")) {
        this.jdField_long = paramString2;
      } else if (paramString1.equals("DISB_ACCOUNT_BPWID")) {
        this.c = paramString2;
      } else if (paramString1.equals("DEPOSIT_MINIMUM"))
      {
        if (this.p == null) {
          this.p = new Currency(paramString2, this.locale);
        } else {
          this.p.fromString(paramString2);
        }
      }
      else if (paramString1.equals("DEPOSIT_MAXIMUM"))
      {
        if (this.u == null) {
          this.u = new Currency(paramString2, this.locale);
        } else {
          this.u.fromString(paramString2);
        }
      }
      else if (paramString1.equals("ANTIC_DEPOSIT"))
      {
        if (this.jdField_else == null) {
          this.jdField_else = new Currency(paramString2, this.locale);
        } else {
          this.jdField_else.fromString(paramString2);
        }
      }
      else if (paramString1.equals("THRESH_DEPOSIT"))
      {
        if (this.h == null) {
          this.h = new Currency(paramString2, this.locale);
        } else {
          this.h.fromString(paramString2);
        }
      }
      else if (paramString1.equals("CONSOLIDATE_DEPOSITS")) {
        this.r = Boolean.valueOf(paramString2).booleanValue();
      } else if (paramString1.equals("DEPOSIT_PRENOTE")) {
        this.q = Boolean.valueOf(paramString2).booleanValue();
      } else if (paramString1.equals("DISBURSEMENT_PRENOTE")) {
        this.s = Boolean.valueOf(paramString2).booleanValue();
      } else {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException) {}
    return bool;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("locationName=").append(this.n);
    localStringBuffer.append(" locationID=").append(this.e);
    localStringBuffer.append(" locationBPWID=").append(this.d);
    localStringBuffer.append(" active=").append(this.m);
    localStringBuffer.append(" localRoutingNumber=").append(this.t);
    localStringBuffer.append(" localBankName=").append(this.i);
    localStringBuffer.append(" localAccountNumber=").append(this.j);
    localStringBuffer.append(" localAccountType=").append(this.o);
    localStringBuffer.append(" cashConCompanyName=").append(this.b);
    localStringBuffer.append(" cashConCompanyBPWID=").append(this.jdField_null);
    localStringBuffer.append(" concAccount=").append(this.g);
    localStringBuffer.append(" concAccountBPWID=").append(this.jdField_goto);
    localStringBuffer.append(" disbAccount=").append(this.jdField_long);
    localStringBuffer.append(" disbAccountBPWID=").append(this.c);
    localStringBuffer.append(" depositMinimum=").append(this.p);
    localStringBuffer.append(" depositMaximum=").append(this.u);
    localStringBuffer.append(" anticDeposit=").append(this.jdField_else);
    localStringBuffer.append(" threshDeposit=").append(this.h);
    localStringBuffer.append(" consolidateDeposits=").append(this.r);
    localStringBuffer.append(" depositPrenote=").append(this.q);
    localStringBuffer.append(" disbursementPrenote=").append(this.s);
    return localStringBuffer.toString();
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOCATION");
    if (this.n != null) {
      XMLHandler.appendTag(localStringBuffer, "LOCATION_NAME", this.n);
    }
    if (this.e != null) {
      XMLHandler.appendTag(localStringBuffer, "LOCATION_ID", this.e);
    }
    if (this.k != null) {
      XMLHandler.appendTag(localStringBuffer, "SUBMITTED_BY", this.k);
    }
    if (this.f != null) {
      XMLHandler.appendTag(localStringBuffer, "LOG_ID", this.f);
    }
    if (this.d != null) {
      XMLHandler.appendTag(localStringBuffer, "LOCATION_BPWID", this.d);
    }
    XMLHandler.appendTag(localStringBuffer, "ACTIVE", String.valueOf(this.m));
    if (this.t != null) {
      XMLHandler.appendTag(localStringBuffer, "LOCAL_ROUTING_NUMBER", this.t);
    }
    if (this.i != null) {
      XMLHandler.appendTag(localStringBuffer, "LOCAL_BANK_NAME", this.i);
    }
    if (this.j != null) {
      XMLHandler.appendTag(localStringBuffer, "LOCAL_ACCOUNT_NUMBER", this.j);
    }
    if (this.o != 0) {
      XMLHandler.appendTag(localStringBuffer, "LOCAL_ACCOUNT_TYPE", this.o);
    }
    if (this.b != null) {
      XMLHandler.appendTag(localStringBuffer, "CASHCON_COMPANY_NAME", this.b);
    }
    if (this.jdField_null != null) {
      XMLHandler.appendTag(localStringBuffer, "CASHCON_COMPANY_BPWID", this.jdField_null);
    }
    if (this.g != null) {
      XMLHandler.appendTag(localStringBuffer, "CONC_ACCOUNT", this.g);
    }
    if (this.jdField_goto != null) {
      XMLHandler.appendTag(localStringBuffer, "CONC_ACCOUNT_BPWID", this.jdField_goto);
    }
    if (this.jdField_long != null) {
      XMLHandler.appendTag(localStringBuffer, "DISB_ACCOUNT", this.jdField_long);
    }
    if (this.c != null) {
      XMLHandler.appendTag(localStringBuffer, "DISB_ACCOUNT_BPWID", this.c);
    }
    if (this.p != null) {
      XMLHandler.appendTag(localStringBuffer, "DEPOSIT_MINIMUM", this.p.doubleValue());
    }
    if (this.u != null) {
      XMLHandler.appendTag(localStringBuffer, "DEPOSIT_MAXIMUM", this.u.doubleValue());
    }
    if (this.jdField_else != null) {
      XMLHandler.appendTag(localStringBuffer, "ANTIC_DEPOSIT", this.jdField_else.doubleValue());
    }
    if (this.h != null) {
      XMLHandler.appendTag(localStringBuffer, "THRESH_DEPOSIT", this.h.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "CONSOLIDATE_DEPOSITS", String.valueOf(this.r));
    XMLHandler.appendTag(localStringBuffer, "DEPOSIT_PRENOTE", String.valueOf(this.q));
    XMLHandler.appendTag(localStringBuffer, "DISBURSEMENT_PRENOTE", String.valueOf(this.s));
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "LOCATION");
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
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    String str1;
    if (paramString1.equals("LOCATION_NAME"))
    {
      str1 = this.n;
      if (str1 != null) {
        return isFilterable(str1, paramString2, paramString3);
      }
    }
    else if (paramString1.equals("LOCATION_ID"))
    {
      str1 = this.e;
      if (str1 != null) {
        return isFilterable(str1, paramString2, paramString3);
      }
    }
    else if (paramString1.equals("SUBMITTED_BY"))
    {
      str1 = this.k;
      if (str1 != null) {
        return isFilterable(str1, paramString2, paramString3);
      }
    }
    else if (paramString1.equals("LOG_ID"))
    {
      str1 = this.f;
      if (str1 != null) {
        return isFilterable(str1, paramString2, paramString3);
      }
    }
    else if (paramString1.equals("LOCATION_BPWID"))
    {
      str1 = this.d;
      if (str1 != null) {
        return isFilterable(str1, paramString2, paramString3);
      }
    }
    else
    {
      if (paramString1.equals("ACTIVE"))
      {
        boolean bool1 = this.m;
        return isFilterable(String.valueOf(bool1), paramString2, paramString3);
      }
      String str2;
      if (paramString1.equals("LOCAL_ROUTING_NUMBER"))
      {
        str2 = this.t;
        if (str2 != null) {
          return isFilterable(str2, paramString2, paramString3);
        }
      }
      else if (paramString1.equals("LOCAL_BANK_NAME"))
      {
        str2 = this.i;
        if (str2 != null) {
          return isFilterable(str2, paramString2, paramString3);
        }
      }
      else if (paramString1.equals("LOCAL_ACCOUNT_NUMBER"))
      {
        str2 = this.j;
        if (str2 != null) {
          return isFilterable(str2, paramString2, paramString3);
        }
      }
      else
      {
        if (paramString1.equals("LOCAL_ACCOUNT_TYPE"))
        {
          int i1 = this.o;
          return isFilterable(String.valueOf(i1), paramString2, paramString3);
        }
        Object localObject;
        if (paramString1.equals("CASHCON_COMPANY_NAME"))
        {
          localObject = this.b;
          if (localObject != null) {
            return isFilterable((String)localObject, paramString2, paramString3);
          }
        }
        else if (paramString1.equals("CASHCON_COMPANY_BPWID"))
        {
          localObject = this.jdField_null;
          if (localObject != null) {
            return isFilterable((String)localObject, paramString2, paramString3);
          }
        }
        else if (paramString1.equals("CONC_ACCOUNT"))
        {
          localObject = this.g;
          if (localObject != null) {
            return isFilterable((String)localObject, paramString2, paramString3);
          }
        }
        else if (paramString1.equals("CONC_ACCOUNT_BPWID"))
        {
          localObject = this.jdField_goto;
          if (localObject != null) {
            return isFilterable((String)localObject, paramString2, paramString3);
          }
        }
        else if (paramString1.equals("DISB_ACCOUNT"))
        {
          localObject = this.jdField_long;
          if (localObject != null) {
            return isFilterable((String)localObject, paramString2, paramString3);
          }
        }
        else if (paramString1.equals("DISB_ACCOUNT_BPWID"))
        {
          localObject = this.c;
          if (localObject != null) {
            return isFilterable((String)localObject, paramString2, paramString3);
          }
        }
        else if (paramString1.equals("DEPOSIT_MINIMUM"))
        {
          localObject = this.p;
          if (localObject != null) {
            return isFilterable(((Currency)localObject).getCurrencyStringNoSymbolNoComma(), paramString2, paramString3);
          }
        }
        else if (paramString1.equals("DEPOSIT_MAXIMUM"))
        {
          localObject = this.u;
          if (localObject != null) {
            return isFilterable(((Currency)localObject).getCurrencyStringNoSymbolNoComma(), paramString2, paramString3);
          }
        }
        else if (paramString1.equals("ANTIC_DEPOSIT"))
        {
          localObject = this.jdField_else;
          if (localObject != null) {
            return isFilterable(((Currency)localObject).getCurrencyStringNoSymbolNoComma(), paramString2, paramString3);
          }
        }
        else if (paramString1.equals("THRESH_DEPOSIT"))
        {
          localObject = this.h;
          if (localObject != null) {
            return isFilterable(((Currency)localObject).getCurrencyStringNoSymbolNoComma(), paramString2, paramString3);
          }
        }
        else
        {
          boolean bool2;
          if (paramString1.equals("CONSOLIDATE_DEPOSITS"))
          {
            bool2 = this.r;
            return isFilterable(String.valueOf(bool2), paramString2, paramString3);
          }
          if (paramString1.equals("DEPOSIT_PRENOTE"))
          {
            bool2 = this.q;
            return isFilterable(String.valueOf(bool2), paramString2, paramString3);
          }
          if (paramString1.equals("DISBURSEMENT_PRENOTE"))
          {
            bool2 = this.s;
            return isFilterable(String.valueOf(bool2), paramString2, paramString3);
          }
          return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
        }
      }
    }
    return false;
  }
  
  public void setDisPrenoteStatus(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String getDisPrenoteStatus()
  {
    return this.jdField_char;
  }
  
  public void setDepPrenoteStatus(String paramString)
  {
    this.jdField_void = paramString;
  }
  
  public String getDepPrenoteStatus()
  {
    return this.jdField_void;
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "LOCATION_BPWID", this.d, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Location paramLocation, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "LOCATION_NAME", paramLocation.n, this.n, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "LOCATION_ID", paramLocation.e, this.e, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "SUBMITTED_BY", paramLocation.k, this.k, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "LOG_ID", paramLocation.f, this.f, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACTIVE", paramLocation.m, this.m, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "LOCAL_ROUTING_NUMBER", paramLocation.t, this.t, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "BANK_NAME", paramLocation.i, this.i, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "LOCAL_ACCOUNT_NUMBER", paramLocation.j, this.j, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "LOCAL_ACCOUNT_TYPE", Account.getType(paramLocation.o, this.locale), Account.getType(this.o, this.locale), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CASHCON_COMPANY_NAME", paramLocation.b, this.b, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CONC_ACCOUNT", paramLocation.g, this.g, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CONC_ACCOUNT_BPWID", paramLocation.jdField_goto, this.jdField_goto, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "DISB_ACCOUNT", paramLocation.jdField_long, this.jdField_long, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "DISB_ACCOUNT_BPWID", paramLocation.c, this.c, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "DEPOSIT_MINIMUM", paramLocation.p, this.p, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "DEPOSIT_MAXIMUM", paramLocation.u, this.u, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ANTIC_DEPOSIT", paramLocation.jdField_else, this.jdField_else, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "THRESH_DEPOSIT", paramLocation.h, this.h, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CONSOLIDATE_DEPOSITS", paramLocation.r, this.r, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "DEPOSIT_PRENOTE", paramLocation.q, this.q, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "DISBURSEMENT_PRENOTE", paramLocation.s, this.s, paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logDelete(BEAN_NAME, "LOCATION_NAME", this.n, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "LOCATION_ID", this.e, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "SUBMITTED_BY", this.k, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "LOG_ID", this.f, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "ACTIVE", String.valueOf(this.m), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "LOCAL_ROUTING_NUMBER", this.t, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "BANK_NAME", this.i, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "LOCAL_ACCOUNT_NUMBER", this.j, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "LOCAL_ACCOUNT_TYPE", Account.getType(this.o, this.locale), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "CASHCON_COMPANY_NAME", this.b, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "CONC_ACCOUNT", this.g, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "CONC_ACCOUNT_BPWID", this.jdField_goto, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "DISB_ACCOUNT", this.jdField_long, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "DISB_ACCOUNT_BPWID", this.c, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "DEPOSIT_MINIMUM", this.p, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "DEPOSIT_MAXIMUM", this.u, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "ANTIC_DEPOSIT", this.jdField_else, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "THRESH_DEPOSIT", this.h, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "CONSOLIDATE_DEPOSITS", String.valueOf(this.r), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "DEPOSIT_PRENOTE", String.valueOf(this.q), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "DISBURSEMENT_PRENOTE", String.valueOf(this.s), paramString);
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      Location.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.Location
 * JD-Core Version:    0.7.0.1
 */