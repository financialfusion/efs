package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class ACHCompanySummaryList
  implements Serializable
{
  private String jdField_for;
  private String[] a;
  private String[] jdField_new;
  private ACHCompanySummaryInfo[] jdField_int;
  private int jdField_do = 0;
  private String jdField_if = "Success";
  
  public String getCustomerId()
  {
    return this.jdField_for;
  }
  
  public void setCustomerId(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String[] getCompanyIdList()
  {
    return this.a;
  }
  
  public void setCompanyIdList(String[] paramArrayOfString)
  {
    this.a = paramArrayOfString;
  }
  
  public String[] getBatchCategoryList()
  {
    return this.jdField_new;
  }
  
  public void setBatchCategoryList(String[] paramArrayOfString)
  {
    this.jdField_new = paramArrayOfString;
  }
  
  public ACHCompanySummaryInfo[] getACHCompanySummaryInfoList()
  {
    return this.jdField_int;
  }
  
  public void setACHCompanySummaryInfoList(ACHCompanySummaryInfo[] paramArrayOfACHCompanySummaryInfo)
  {
    this.jdField_int = paramArrayOfACHCompanySummaryInfo;
  }
  
  public int getStatusCode()
  {
    return this.jdField_do;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.jdField_if;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.jdField_if = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryList
 * JD-Core Version:    0.7.0.1
 */