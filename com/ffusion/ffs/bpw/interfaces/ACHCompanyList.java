package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class ACHCompanyList
  implements Serializable
{
  protected String jdField_try = null;
  protected String a = null;
  protected String jdField_else = null;
  protected String jdField_char = null;
  protected String[] jdField_byte = null;
  protected String jdField_if = null;
  protected String jdField_do = null;
  protected String[] jdField_int = null;
  protected ACHCompanyInfo[] jdField_case = null;
  protected int jdField_new = 0;
  protected String jdField_for = "Success";
  
  public String getFiId()
  {
    return this.jdField_try;
  }
  
  public void setFiId(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public String getOdfiAchId()
  {
    return this.a;
  }
  
  public void setOdfiAchId(String paramString)
  {
    this.a = paramString;
  }
  
  public String getCustomerId()
  {
    return this.jdField_else;
  }
  
  public void setCustomerId(String paramString)
  {
    this.jdField_else = paramString;
  }
  
  public String getCompAchId()
  {
    return this.jdField_char;
  }
  
  public void setCompAchId(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String[] getCompIdList()
  {
    return this.jdField_byte;
  }
  
  public void setCompIdList(String[] paramArrayOfString)
  {
    this.jdField_byte = paramArrayOfString;
  }
  
  public String getCompType()
  {
    return this.jdField_do;
  }
  
  public void setCompType(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getCompGroup()
  {
    return this.jdField_if;
  }
  
  public void setCompGroup(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String[] getCompStatusList()
  {
    return this.jdField_int;
  }
  
  public void setCompStatusList(String[] paramArrayOfString)
  {
    this.jdField_int = paramArrayOfString;
  }
  
  public ACHCompanyInfo[] getACHCompanyInfoList()
  {
    return this.jdField_case;
  }
  
  public void setACHCompanyInfoList(ACHCompanyInfo[] paramArrayOfACHCompanyInfo)
  {
    this.jdField_case = paramArrayOfACHCompanyInfo;
  }
  
  public int getStatusCode()
  {
    return this.jdField_new;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.jdField_new = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.jdField_for;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.jdField_for = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHCompanyList
 * JD-Core Version:    0.7.0.1
 */