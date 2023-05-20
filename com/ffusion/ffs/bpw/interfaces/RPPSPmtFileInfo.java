package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class RPPSPmtFileInfo
  implements Serializable
{
  protected int jdField_for;
  protected String jdField_goto;
  protected String jdField_long;
  protected String jdField_char;
  protected String jdField_else;
  protected int jdField_null;
  protected String a;
  protected String jdField_if;
  protected String jdField_try;
  protected String jdField_do;
  protected String jdField_case;
  protected String jdField_int;
  protected int jdField_byte = -1;
  protected String jdField_new = null;
  
  public RPPSPmtFileInfo() {}
  
  public RPPSPmtFileInfo(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt2, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    this.jdField_for = paramInt1;
    this.jdField_goto = paramString1;
    this.jdField_long = paramString2;
    this.jdField_char = paramString3;
    this.jdField_else = paramString4;
    this.jdField_null = paramInt2;
    this.a = paramString5;
    this.jdField_else = paramString4;
    this.jdField_null = paramInt2;
    this.a = paramString5;
    this.jdField_if = paramString6;
    this.jdField_try = paramString7;
    this.jdField_do = paramString8;
    this.jdField_int = paramString9;
  }
  
  public String getTransDate()
  {
    return this.jdField_goto;
  }
  
  public void setTransDate(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  public String getTransTime()
  {
    return this.jdField_long;
  }
  
  public void setTransTime(String paramString)
  {
    this.jdField_long = paramString;
  }
  
  public String getFiId()
  {
    return this.jdField_char;
  }
  
  public void setFiId(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String getFileIdModifier()
  {
    return this.jdField_else;
  }
  
  public void setFileIdModifier(String paramString)
  {
    this.jdField_else = paramString;
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
  
  public int getTotalEntryCount()
  {
    return this.jdField_null;
  }
  
  public void setTotalEntryCount(int paramInt)
  {
    this.jdField_null = paramInt;
  }
  
  public String getFileName()
  {
    return this.jdField_try;
  }
  
  public void setFileName(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public String getConfirmed()
  {
    return this.jdField_do;
  }
  
  public void setConfirmed(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getCompleted()
  {
    return this.jdField_case;
  }
  
  public void setCompleted(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public int getFileId()
  {
    return this.jdField_for;
  }
  
  public void setFileId(int paramInt)
  {
    this.jdField_for = paramInt;
  }
  
  public String getLogId()
  {
    return this.jdField_int;
  }
  
  public void setLogId(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public int getStatusCode()
  {
    return this.jdField_byte;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.jdField_byte = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.jdField_new;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.jdField_new = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.RPPSPmtFileInfo
 * JD-Core Version:    0.7.0.1
 */