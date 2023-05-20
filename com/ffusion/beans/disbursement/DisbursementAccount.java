package com.ffusion.beans.disbursement;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.text.Collator;
import java.util.Locale;
import java.util.logging.Level;

public class DisbursementAccount
  extends ExtendABean
  implements Comparable
{
  public static final String DISBURSEMENTACCOUNT = "DISBURSEMENTACCOUNT";
  public static final String ACCOUNTID = "ACCOUNTID";
  public static final String ACCOUNTNUMBER = "ACCOUNTNUMBER";
  public static final String ACCOUNTNAME = "ACCOUNTNAME";
  public static final String BANKID = "BANKID";
  public static final String BANKNAME = "BANKNAME";
  public static final String ROUTINGNUMBER = "ROUTINGNUMBER";
  public static final String CURRENCYTYPE = "CURRENCYTYPE";
  private String aYm;
  private String aYn;
  private String aYp;
  private String aYq;
  private String aYo;
  private String aYr;
  private String aYs;
  
  public DisbursementAccount() {}
  
  public DisbursementAccount(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public String getAccountID()
  {
    return this.aYm;
  }
  
  public String getAccountNumber()
  {
    return this.aYn;
  }
  
  public String getAccountName()
  {
    return this.aYp;
  }
  
  public String getBankID()
  {
    return this.aYq;
  }
  
  public String getBankName()
  {
    return this.aYs;
  }
  
  public String getRoutingNumber()
  {
    return this.aYr;
  }
  
  public String getCurrencyType()
  {
    return this.aYo;
  }
  
  public void setAccountID(String paramString)
  {
    if ((this.aYm != null) && (!this.aYm.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "DisbursementAccount.setAccountID.  Cannot modify the value of account ID.");
      return;
    }
    this.aYm = paramString;
  }
  
  public void setAccountNumber(String paramString)
  {
    if ((this.aYn != null) && (!this.aYn.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "DisbursementAccount.setAccountNumber.  Cannot modify the value of account number.");
      return;
    }
    this.aYn = paramString;
  }
  
  public void setAccountName(String paramString)
  {
    this.aYp = paramString;
  }
  
  public void setBankID(String paramString)
  {
    if ((this.aYq != null) && (!this.aYq.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "DisbursementAccount.setBankID.  Cannot modify the value of bank ID.");
      return;
    }
    this.aYq = paramString;
  }
  
  public void setBankName(String paramString)
  {
    this.aYs = paramString;
  }
  
  public void setRoutingNumber(String paramString)
  {
    if ((this.aYr != null) && (!this.aYr.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "DisbursementAccount.setRoutingNumber.  Cannot modify the value of routing number.");
      return;
    }
    this.aYr = paramString;
  }
  
  public void setCurrencyType(String paramString)
  {
    this.aYo = paramString;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "ACCOUNTNUMBER");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    DisbursementAccount localDisbursementAccount = (DisbursementAccount)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("ACCOUNTID")) && (this.aYm != null) && (localDisbursementAccount.getAccountID() != null)) {
      return localCollator.compare(getAccountID(), localDisbursementAccount.getAccountID());
    }
    if ((paramString.equals("ACCOUNTNUMBER")) && (this.aYn != null) && (localDisbursementAccount.getAccountNumber() != null)) {
      return localCollator.compare(getAccountNumber(), localDisbursementAccount.getAccountNumber());
    }
    if ((paramString.equals("ACCOUNTNAME")) && (this.aYp != null) && (localDisbursementAccount.getAccountName() != null)) {
      return localCollator.compare(getAccountName(), localDisbursementAccount.getAccountName());
    }
    if ((paramString.equals("BANKID")) && (this.aYq != null) && (localDisbursementAccount.getBankID() != null)) {
      return localCollator.compare(getBankID(), localDisbursementAccount.getBankID());
    }
    if ((paramString.equals("BANKNAME")) && (this.aYs != null) && (localDisbursementAccount.getBankName() != null)) {
      return localCollator.compare(getBankName(), localDisbursementAccount.getBankName());
    }
    if ((paramString.equals("ROUTINGNUMBER")) && (this.aYr != null) && (localDisbursementAccount.getRoutingNumber() != null)) {
      return localCollator.compare(getRoutingNumber(), localDisbursementAccount.getRoutingNumber());
    }
    if ((paramString.equals("CURRENCYTYPE")) && (this.aYo != null) && (localDisbursementAccount.getCurrencyType() != null)) {
      return localCollator.compare(getCurrencyType(), localDisbursementAccount.getCurrencyType());
    }
    i = super.compare(paramObject, paramString);
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ACCOUNTID")) && (getAccountID() != null)) {
      return isFilterable(getAccountID(), paramString2, paramString3);
    }
    if ((paramString1.equals("ACCOUNTNUMBER")) && (getAccountNumber() != null)) {
      return isFilterable(getAccountNumber(), paramString2, paramString3);
    }
    if ((paramString1.equals("ACCOUNTNAME")) && (getAccountName() != null)) {
      return isFilterable(getAccountName(), paramString2, paramString3);
    }
    if ((paramString1.equals("BANKID")) && (getBankID() != null)) {
      return isFilterable(getBankID(), paramString2, paramString3);
    }
    if ((paramString1.equals("CURRENCYTYPE")) && (getCurrencyType() != null)) {
      return isFilterable(getCurrencyType(), paramString2, paramString3);
    }
    if ((paramString1.equals("BANKNAME")) && (getBankName() != null)) {
      return isFilterable(getBankName(), paramString2, paramString3);
    }
    if ((paramString1.equals("ROUTINGNUMBER")) && (getRoutingNumber() != null)) {
      return isFilterable(getRoutingNumber(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void set(DisbursementAccount paramDisbursementAccount)
  {
    if ((this == paramDisbursementAccount) || (paramDisbursementAccount == null)) {
      return;
    }
    setAccountID(paramDisbursementAccount.getAccountID());
    setAccountNumber(paramDisbursementAccount.getAccountNumber());
    setAccountName(paramDisbursementAccount.getAccountName());
    setBankID(paramDisbursementAccount.getBankID());
    setCurrencyType(paramDisbursementAccount.getCurrencyType());
    setBankName(paramDisbursementAccount.getBankName());
    setRoutingNumber(paramDisbursementAccount.getRoutingNumber());
    super.set(paramDisbursementAccount);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ACCOUNTID")) {
      setAccountID(paramString2);
    } else if (paramString1.equals("ACCOUNTNUMBER")) {
      setAccountNumber(paramString2);
    } else if (paramString1.equals("ACCOUNTNAME")) {
      setAccountName(paramString2);
    } else if (paramString1.equals("BANKID")) {
      setBankID(paramString2);
    } else if (paramString1.equals("CURRENCYTYPE")) {
      setCurrencyType(paramString2);
    } else if (paramString1.equals("BANKNAME")) {
      setBankName(paramString2);
    } else if (paramString1.equals("ROUTINGNUMBER")) {
      setRoutingNumber(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "DISBURSEMENTACCOUNT");
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTID", getAccountID());
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTNUMBER", getAccountNumber());
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTNAME", getAccountName());
    XMLHandler.appendTag(localStringBuffer, "BANKID", getBankID());
    XMLHandler.appendTag(localStringBuffer, "BANKNAME", getBankName());
    XMLHandler.appendTag(localStringBuffer, "ROUTINGNUMBER", getRoutingNumber());
    XMLHandler.appendTag(localStringBuffer, "CURRENCYTYPE", getCurrencyType());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "DISBURSEMENTACCOUNT");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.disbursement.DisbursementAccount
 * JD-Core Version:    0.7.0.1
 */