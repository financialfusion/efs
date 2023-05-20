package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;
import java.util.Hashtable;

public class RPPSFIInfo
  implements Serializable
{
  protected String a;
  protected String jdField_try;
  protected String jdField_else;
  protected String jdField_null;
  protected String jdField_for;
  protected String jdField_if;
  protected String jdField_int;
  protected String jdField_do;
  protected String jdField_goto;
  protected String jdField_char;
  protected String jdField_new;
  protected int jdField_case = -1;
  protected String jdField_byte = null;
  protected Hashtable jdField_long = null;
  
  public RPPSFIInfo() {}
  
  public RPPSFIInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Hashtable paramHashtable, String paramString7, String paramString8, String paramString9, String paramString10)
  {
    this.jdField_try = paramString1;
    this.jdField_else = paramString2;
    this.jdField_null = paramString3;
    this.jdField_for = paramString4;
    this.jdField_if = paramString5;
    this.jdField_int = paramString6;
    this.jdField_long = paramHashtable;
    this.jdField_do = paramString7;
    this.jdField_goto = paramString8;
    this.jdField_char = paramString9;
    this.jdField_new = paramString10;
  }
  
  public String getRPPSFIId()
  {
    return this.a;
  }
  
  public void setRPPSFIId(String paramString)
  {
    this.a = paramString;
  }
  
  public String getFiRPPSId()
  {
    return this.jdField_try;
  }
  
  public void setFiRPPSId(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public String getFiId()
  {
    return this.jdField_else;
  }
  
  public void setFiId(String paramString)
  {
    this.jdField_else = paramString;
  }
  
  public String getFiRPPSName()
  {
    return this.jdField_null;
  }
  
  public void setFiRPPSName(String paramString)
  {
    this.jdField_null = paramString;
  }
  
  public String getRppsId()
  {
    return this.jdField_for;
  }
  
  public void setRppsId(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getRppsName()
  {
    return this.jdField_if;
  }
  
  public void setRppsName(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getFiStatus()
  {
    return this.jdField_int;
  }
  
  public void setFiStatus(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getSubmitDate()
  {
    return this.jdField_do;
  }
  
  public void setSubmitDate(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getActivationDate()
  {
    return this.jdField_goto;
  }
  
  public void setActivationDate(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  public String getLogId()
  {
    return this.jdField_new;
  }
  
  public void setLogId(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public int getStatusCode()
  {
    return this.jdField_case;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.jdField_case = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.jdField_byte;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public Hashtable getMemo()
  {
    return this.jdField_long;
  }
  
  public void setMemo(Hashtable paramHashtable)
  {
    this.jdField_long = paramHashtable;
  }
  
  public String getCreditCap()
  {
    return this.jdField_char;
  }
  
  public long getCreditCapLong()
  {
    if (this.jdField_char != null) {
      return Long.parseLong(this.jdField_char);
    }
    return 0L;
  }
  
  public void setCreditCap(String paramString)
  {
    this.jdField_char = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.RPPSFIInfo
 * JD-Core Version:    0.7.0.1
 */