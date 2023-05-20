package com.ffusion.beans.cashcon;

import com.ffusion.util.beans.ExtendABean;

public class LocationSearchCriteria
  extends ExtendABean
{
  private int jdField_if = -1;
  private String jdField_do;
  private String a;
  private int jdField_for;
  
  public int getDivisionID()
  {
    return this.jdField_if;
  }
  
  public void setDivisionID(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public String getLocationName()
  {
    return this.jdField_do;
  }
  
  public String getLocationNameLowerCase()
  {
    return this.jdField_do == null ? null : this.jdField_do.toLowerCase();
  }
  
  public void setLocationName(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getLocationID()
  {
    return this.a;
  }
  
  public String getLocationIDLowerCase()
  {
    return this.a == null ? null : this.a.toLowerCase();
  }
  
  public void setLocationID(String paramString)
  {
    this.a = paramString;
  }
  
  public int getMaxResults()
  {
    return this.jdField_for;
  }
  
  public void setMaxResults(int paramInt)
  {
    this.jdField_for = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.LocationSearchCriteria
 * JD-Core Version:    0.7.0.1
 */