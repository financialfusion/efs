package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class ACHCompanySummaryInfo
  implements Serializable
{
  String jdField_char;
  String jdField_else;
  String jdField_case;
  String jdField_int;
  long jdField_byte;
  long jdField_for;
  String a;
  String jdField_if;
  String jdField_do;
  int jdField_try = 0;
  String jdField_new = "Success";
  
  public ACHCompanySummaryInfo() {}
  
  public ACHCompanySummaryInfo(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong1, long paramLong2, String paramString5, String paramString6, String paramString7)
  {
    paramString1 = paramString1;
    this.jdField_else = paramString2;
    this.jdField_case = paramString3;
    this.jdField_int = paramString4;
    this.jdField_byte = paramLong1;
    this.jdField_for = paramLong2;
    paramString5 = paramString5;
    paramString6 = paramString6;
    paramString7 = paramString7;
  }
  
  public String getCustomerId()
  {
    return this.jdField_char;
  }
  
  public void setCustomerId(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String getCompanyName()
  {
    return this.jdField_else;
  }
  
  public void setCompanyName(String paramString)
  {
    this.jdField_else = paramString;
  }
  
  public String getCompanyId()
  {
    return this.jdField_case;
  }
  
  public void setCompanyId(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public String getBatchCategory()
  {
    return this.jdField_int;
  }
  
  public void setBatchCategory(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public long getNumberOfBatches()
  {
    return this.jdField_byte;
  }
  
  public void setNumberOfBatches(long paramLong)
  {
    this.jdField_byte = paramLong;
  }
  
  public long getNumberOfBatchEntries()
  {
    return this.jdField_for;
  }
  
  public void setNumberOfBatchEntries(long paramLong)
  {
    this.jdField_for = paramLong;
  }
  
  public String getTotalDebit()
  {
    return this.a;
  }
  
  public void setTotalDebit(String paramString)
  {
    this.a = paramString;
  }
  
  public String getTotalCredit()
  {
    return this.jdField_if;
  }
  
  public void setTotalCredit(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getGrandTotal()
  {
    return this.jdField_do;
  }
  
  public void setGrandTotal(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public int getStatusCode()
  {
    return this.jdField_try;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.jdField_try = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.jdField_new;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public void add(ACHCompanySummaryInfo paramACHCompanySummaryInfo)
  {
    this.jdField_byte += paramACHCompanySummaryInfo.getNumberOfBatches();
    this.jdField_for += paramACHCompanySummaryInfo.getNumberOfBatchEntries();
    this.jdField_if = ("" + (Long.parseLong(getTotalCredit()) + Long.parseLong(paramACHCompanySummaryInfo.getTotalCredit())));
    this.a = ("" + (Long.parseLong(getTotalDebit()) + Long.parseLong(paramACHCompanySummaryInfo.getTotalDebit())));
    this.jdField_do = ("" + (Long.parseLong(getGrandTotal()) + Long.parseLong(paramACHCompanySummaryInfo.getGrandTotal())));
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryInfo
 * JD-Core Version:    0.7.0.1
 */