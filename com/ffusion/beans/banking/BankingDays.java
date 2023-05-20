package com.ffusion.beans.banking;

import com.ffusion.beans.ExtendABean;
import java.io.Serializable;
import java.util.Calendar;

public class BankingDays
  extends ExtendABean
  implements Comparable, Serializable
{
  private String a5P;
  private String a5K;
  private String a5Q;
  private String a5N;
  private String a5M;
  private Calendar a5J;
  private Calendar a5L;
  private boolean[] a5O;
  
  public BankingDays() {}
  
  public BankingDays(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Calendar paramCalendar1, Calendar paramCalendar2, boolean[] paramArrayOfBoolean)
  {
    this.a5P = paramString1;
    this.a5K = paramString2;
    this.a5Q = paramString3;
    this.a5N = paramString4;
    this.a5M = paramString5;
    this.a5J = paramCalendar1;
    this.a5L = paramCalendar2;
    this.a5O = paramArrayOfBoolean;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return 0;
  }
  
  public String getFiID()
  {
    return this.a5P;
  }
  
  public void setFiID(String paramString)
  {
    this.a5P = paramString;
  }
  
  public String getCustomerID()
  {
    return this.a5K;
  }
  
  public void setCustomerID(String paramString)
  {
    this.a5K = paramString;
  }
  
  public String getTransType()
  {
    return this.a5Q;
  }
  
  public void setTransType(String paramString)
  {
    this.a5Q = paramString;
  }
  
  public String getCompId()
  {
    return this.a5N;
  }
  
  public void setCompId(String paramString)
  {
    this.a5N = paramString;
  }
  
  public String getSec()
  {
    return this.a5M;
  }
  
  public void setSec(String paramString)
  {
    this.a5M = paramString;
  }
  
  public Calendar getStartDate()
  {
    return this.a5J;
  }
  
  public void setStartDate(Calendar paramCalendar)
  {
    this.a5J = paramCalendar;
  }
  
  public Calendar getEndDate()
  {
    return this.a5L;
  }
  
  public void setEndDate(Calendar paramCalendar)
  {
    this.a5L = paramCalendar;
  }
  
  public boolean[] getBankingDays()
  {
    return this.a5O;
  }
  
  public void setBankingDays(boolean[] paramArrayOfBoolean)
  {
    this.a5O = paramArrayOfBoolean;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.BankingDays
 * JD-Core Version:    0.7.0.1
 */