package com.ffusion.ffs.bpw.interfaces;

import java.util.Calendar;

public class BankingDays
  extends BPWInfoBase
{
  private String pF;
  private String pA;
  private String pG;
  private String pD;
  private String pC;
  private Calendar pz;
  private Calendar pB;
  private boolean[] pE;
  
  public BankingDays() {}
  
  public BankingDays(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Calendar paramCalendar1, Calendar paramCalendar2, boolean[] paramArrayOfBoolean)
  {
    this.pF = paramString1;
    this.pA = paramString2;
    this.pG = paramString3;
    this.pD = paramString4;
    this.pC = paramString5;
    this.pz = paramCalendar1;
    this.pB = paramCalendar2;
    this.pE = paramArrayOfBoolean;
  }
  
  public String getFiID()
  {
    return this.pF;
  }
  
  public void setFiID(String paramString)
  {
    this.pF = paramString;
  }
  
  public String getCustomerID()
  {
    return this.pA;
  }
  
  public void setCustomerID(String paramString)
  {
    this.pA = paramString;
  }
  
  public String getTransType()
  {
    return this.pG;
  }
  
  public void setTransType(String paramString)
  {
    this.pG = paramString;
  }
  
  public String getCompId()
  {
    return this.pD;
  }
  
  public void setCompId(String paramString)
  {
    this.pD = paramString;
  }
  
  public String getSec()
  {
    return this.pC;
  }
  
  public void setSec(String paramString)
  {
    this.pC = paramString;
  }
  
  public Calendar getStartDate()
  {
    return this.pz;
  }
  
  public void setStartDate(Calendar paramCalendar)
  {
    this.pz = paramCalendar;
  }
  
  public Calendar getEndDate()
  {
    return this.pB;
  }
  
  public void setEndDate(Calendar paramCalendar)
  {
    this.pB = paramCalendar;
  }
  
  public boolean[] getBankingDays()
  {
    return this.pE;
  }
  
  public void setBankingDays(boolean[] paramArrayOfBoolean)
  {
    this.pE = paramArrayOfBoolean;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.BankingDays
 * JD-Core Version:    0.7.0.1
 */