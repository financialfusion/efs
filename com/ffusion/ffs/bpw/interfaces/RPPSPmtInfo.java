package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class RPPSPmtInfo
  implements Serializable
{
  protected int jdField_int;
  protected String jdField_char;
  protected String jdField_byte;
  protected int jdField_case;
  protected int jdField_if;
  protected String a;
  protected String jdField_do;
  protected String jdField_for;
  protected int jdField_try = -1;
  protected String jdField_new = null;
  
  public RPPSPmtInfo() {}
  
  public RPPSPmtInfo(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3, String paramString3, String paramString4, String paramString5)
  {
    this.jdField_int = paramInt1;
    this.jdField_char = paramString1;
    this.jdField_byte = paramString2;
    this.jdField_case = paramInt2;
    this.jdField_if = paramInt3;
    this.a = paramString3;
    this.jdField_do = paramString4;
    this.jdField_for = paramString5;
  }
  
  public int getTraceNum()
  {
    return this.jdField_int;
  }
  
  public void setTraceNum(int paramInt)
  {
    this.jdField_int = paramInt;
  }
  
  public String getSrvrTId()
  {
    return this.jdField_char;
  }
  
  public void setSrvrTId(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String getPayAccount()
  {
    return this.jdField_byte;
  }
  
  public void setPayAccount(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public int getBatchNum()
  {
    return this.jdField_case;
  }
  
  public void setBatchNum(int paramInt)
  {
    this.jdField_case = paramInt;
  }
  
  public String getSubmitDate()
  {
    return this.jdField_do;
  }
  
  public void setSubmitDate(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public int getFileId()
  {
    return this.jdField_if;
  }
  
  public void setFileId(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public String getLogId()
  {
    return this.jdField_for;
  }
  
  public void setLogId(String paramString)
  {
    this.jdField_for = paramString;
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
  
  public String getConsumerName()
  {
    return this.a;
  }
  
  public void setConsumerName(String paramString)
  {
    this.a = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.RPPSPmtInfo
 * JD-Core Version:    0.7.0.1
 */