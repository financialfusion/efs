package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class RPPSRecordInfo
  implements Serializable
{
  protected int jdField_try;
  protected String jdField_do;
  protected String a;
  protected String jdField_byte;
  protected int jdField_for;
  protected String jdField_if;
  protected int jdField_new = -1;
  protected String jdField_int = null;
  
  public RPPSRecordInfo() {}
  
  public RPPSRecordInfo(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, String paramString4)
  {
    this.jdField_try = paramInt1;
    this.jdField_do = paramString1;
    this.a = paramString2;
    this.jdField_byte = paramString3;
    this.jdField_for = paramInt2;
    this.jdField_if = paramString4;
  }
  
  public int getEntryId()
  {
    return this.jdField_try;
  }
  
  public void setEntryId(int paramInt)
  {
    this.jdField_try = paramInt;
  }
  
  public String getFiId()
  {
    return this.a;
  }
  
  public void setFiId(String paramString)
  {
    this.a = paramString;
  }
  
  public String getSrvrTId()
  {
    return this.jdField_do;
  }
  
  public void setSrvrTId(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getPayeeId()
  {
    return this.jdField_byte;
  }
  
  public void setPayeeId(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public int getTxCode()
  {
    return this.jdField_for;
  }
  
  public void setTxCode(int paramInt)
  {
    this.jdField_for = paramInt;
  }
  
  public String getLogId()
  {
    return this.jdField_if;
  }
  
  public void setLogId(String paramString)
  {
    this.jdField_if = paramString;
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
    return this.jdField_int;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.jdField_int = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.RPPSRecordInfo
 * JD-Core Version:    0.7.0.1
 */