package com.ffusion.beans.disbursement;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FX;
import com.ffusion.util.XMLHandler;
import java.text.Collator;
import java.util.HashMap;
import java.util.Locale;

public class DisbursementPresentmentSummary
  extends ExtendABean
  implements Comparable
{
  public static final String DISBURSEMENTPRESENTMENTSUMMARY = "DISBURSEMENTPRESENTMENTSUMMARY";
  public static final String TOTALDEBITS = "TOTALDEBITS";
  public static final String TOTALCREDITS = "TOTALCREDITS";
  public static final String NUMBERITEMSPENDING = "NUMBERITEMSPENDING";
  public static final String PRESENTMENT = "PRESENTMENT";
  private int aYk;
  private Currency aYi;
  private Currency aYj;
  private String aYg;
  private SecureUser aYl;
  private DisbursementPresentmentSummaries aYh = new DisbursementPresentmentSummaries();
  
  public DisbursementPresentmentSummary() {}
  
  public DisbursementPresentmentSummary(SecureUser paramSecureUser)
  {
    this.aYl = paramSecureUser;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.aYi != null) {
      this.aYi.setLocale(paramLocale);
    }
    if (this.aYj != null) {
      this.aYj.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
  }
  
  public int getNumItemsPending()
  {
    return this.aYk;
  }
  
  public Currency getTotalDebits()
  {
    return this.aYi;
  }
  
  public Currency getTotalCredits()
  {
    return this.aYj;
  }
  
  public String getPresentment()
  {
    return this.aYg;
  }
  
  public void setNumItemsPending(int paramInt)
  {
    this.aYk = paramInt;
  }
  
  public void setTotalDebits(Currency paramCurrency)
  {
    this.aYi = paramCurrency;
  }
  
  public void setTotalCredits(Currency paramCurrency)
  {
    this.aYj = paramCurrency;
  }
  
  public void setPresentment(String paramString)
  {
    this.aYg = paramString;
  }
  
  public void addSubSummary(DisbursementPresentmentSummary paramDisbursementPresentmentSummary)
  {
    this.aYh.add(paramDisbursementPresentmentSummary);
    this.aYg = paramDisbursementPresentmentSummary.getPresentment();
    this.aYk += paramDisbursementPresentmentSummary.getNumItemsPending();
    if (this.aYi == null) {
      this.aYi = new Currency("0", this.aYl.getBaseCurrency(), this.locale);
    }
    if (this.aYj == null) {
      this.aYj = new Currency("0", this.aYl.getBaseCurrency(), this.locale);
    }
    String str;
    if (paramDisbursementPresentmentSummary.getTotalDebits() == null) {
      str = paramDisbursementPresentmentSummary.getTotalCredits().getCurrencyCode();
    } else {
      str = paramDisbursementPresentmentSummary.getTotalDebits().getCurrencyCode();
    }
    if (str.equals(this.aYl.getBaseCurrency()))
    {
      if (paramDisbursementPresentmentSummary.getTotalDebits() != null) {
        this.aYi.addAmount(paramDisbursementPresentmentSummary.getTotalDebits());
      }
      if (paramDisbursementPresentmentSummary.getTotalCredits() != null) {
        this.aYj.addAmount(paramDisbursementPresentmentSummary.getTotalCredits());
      }
    }
    else
    {
      HashMap localHashMap = new HashMap();
      try
      {
        if (paramDisbursementPresentmentSummary.getTotalDebits() != null) {
          this.aYi.addAmount(FX.convertToBaseCurrency(this.aYl, paramDisbursementPresentmentSummary.getTotalDebits(), localHashMap));
        }
        if (paramDisbursementPresentmentSummary.getTotalCredits() != null) {
          this.aYj.addAmount(FX.convertToBaseCurrency(this.aYl, paramDisbursementPresentmentSummary.getTotalCredits(), localHashMap));
        }
      }
      catch (CSILException localCSILException)
      {
        localCSILException.printStackTrace();
      }
    }
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "PRESENTMENT");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    DisbursementPresentmentSummary localDisbursementPresentmentSummary = (DisbursementPresentmentSummary)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if (paramString.equals("NUMBERITEMSPENDING")) {
      i = this.aYk - localDisbursementPresentmentSummary.getNumItemsPending();
    } else if ((paramString.equals("TOTALDEBITS")) && (getTotalDebits() != null) && (localDisbursementPresentmentSummary.getTotalDebits() != null)) {
      i = getTotalDebits().compareTo(localDisbursementPresentmentSummary.getTotalDebits());
    } else if ((paramString.equals("TOTALCREDITS")) && (getTotalCredits() != null) && (localDisbursementPresentmentSummary.getTotalCredits() != null)) {
      i = getTotalCredits().compareTo(localDisbursementPresentmentSummary.getTotalCredits());
    } else if (paramString.equals("MEMO")) {
      i = localCollator.compare(getPresentment(), localDisbursementPresentmentSummary.getPresentment());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    boolean bool = false;
    if ((paramString1.equals("TOTALDEBITS")) && (getTotalDebits() != null)) {
      bool = getTotalDebits().isFilterable("VALUE" + paramString2 + paramString3);
    } else if ((paramString1.equals("TOTALCREDITS")) && (getTotalCredits() != null)) {
      bool = getTotalCredits().isFilterable("VALUE" + paramString2 + paramString3);
    } else if ((paramString1.equals("PRESENTMENT")) && (getPresentment() != null)) {
      bool = isFilterable(getPresentment(), paramString2, paramString3);
    } else if (paramString1.equals("NUMBERITEMSPENDING")) {
      bool = isFilterable(String.valueOf(getNumItemsPending()), paramString2, paramString3);
    } else {
      bool = super.isFilterablePreParsed(paramString1, paramString2, paramString3);
    }
    return bool;
  }
  
  public void set(DisbursementPresentmentSummary paramDisbursementPresentmentSummary)
  {
    if ((this == paramDisbursementPresentmentSummary) || (paramDisbursementPresentmentSummary == null)) {
      return;
    }
    if (paramDisbursementPresentmentSummary.getTotalDebits() != null) {
      setTotalDebits((Currency)paramDisbursementPresentmentSummary.getTotalDebits().clone());
    } else {
      setTotalDebits(null);
    }
    if (paramDisbursementPresentmentSummary.getTotalCredits() != null) {
      setTotalCredits((Currency)paramDisbursementPresentmentSummary.getTotalCredits().clone());
    } else {
      setTotalCredits(null);
    }
    setPresentment(paramDisbursementPresentmentSummary.getPresentment());
    setNumItemsPending(paramDisbursementPresentmentSummary.getNumItemsPending());
    super.set(paramDisbursementPresentmentSummary);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("NUMBERITEMSPENDING")) {
      try
      {
        this.aYk = Integer.parseInt(paramString2);
      }
      catch (Exception localException) {}
    } else if (paramString1.equals("TOTALDEBITS"))
    {
      if (this.aYi == null) {
        this.aYi = new Currency(paramString2, this.locale);
      } else {
        this.aYi.fromString(paramString2);
      }
    }
    else if (paramString1.equals("TOTALCREDITS"))
    {
      if (this.aYj == null) {
        this.aYj = new Currency(paramString2, this.locale);
      } else {
        this.aYj.fromString(paramString2);
      }
    }
    else if (paramString1.equals("PRESENTMENT")) {
      setPresentment(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "DISBURSEMENTPRESENTMENTSUMMARY");
    XMLHandler.appendTag(localStringBuffer, "NUMBERITEMSPENDING", getNumItemsPending());
    XMLHandler.appendTag(localStringBuffer, "TOTALDEBITS", getTotalDebits() == null ? 0.0D : getTotalDebits().doubleValue());
    XMLHandler.appendTag(localStringBuffer, "TOTALCREDITS", getTotalCredits() == null ? 0.0D : getTotalCredits().doubleValue());
    XMLHandler.appendTag(localStringBuffer, "PRESENTMENT", getPresentment());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "DISBURSEMENTPRESENTMENTSUMMARY");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.disbursement.DisbursementPresentmentSummary
 * JD-Core Version:    0.7.0.1
 */