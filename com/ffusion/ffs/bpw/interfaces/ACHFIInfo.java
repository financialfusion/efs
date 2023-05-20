package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;
import java.util.Hashtable;

public class ACHFIInfo
  implements Serializable
{
  protected String jdField_for;
  protected String jdField_goto;
  protected String jdField_try;
  protected String jdField_byte;
  protected String jdField_null;
  protected String jdField_long;
  protected String a;
  protected String jdField_if;
  protected String jdField_case;
  protected String jdField_do;
  protected int jdField_new = -1;
  protected String jdField_int = null;
  protected String jdField_else = "Y";
  protected Hashtable jdField_char = null;
  
  public Hashtable getMemo()
  {
    return this.jdField_char;
  }
  
  public void setMemo(Hashtable paramHashtable)
  {
    this.jdField_char = paramHashtable;
  }
  
  public ACHFIInfo() {}
  
  public ACHFIInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    this.jdField_try = paramString1;
    this.jdField_goto = paramString2;
    this.jdField_byte = paramString3;
    this.jdField_long = paramString4;
    this.a = paramString5;
    this.jdField_null = paramString6;
    this.jdField_if = paramString7;
    this.jdField_case = paramString8;
    this.jdField_do = paramString9;
  }
  
  public String getACHFIId()
  {
    return this.jdField_for;
  }
  
  public void setACHFIId(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getODFIACHId()
  {
    return this.jdField_try;
  }
  
  public String getODFIACHId8()
  {
    String str = null;
    if ((this.jdField_try != null) && (this.jdField_try.length() > 8)) {
      str = this.jdField_try.substring(0, 8);
    } else {
      str = this.jdField_try;
    }
    return str;
  }
  
  public void setODFIACHId(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public String getRDFIACHId()
  {
    return this.jdField_long;
  }
  
  public void setRDFIACHId(String paramString)
  {
    this.jdField_long = paramString;
  }
  
  public String getRDFIName()
  {
    return this.a;
  }
  
  public void setRDFIName(String paramString)
  {
    this.a = paramString;
  }
  
  public String getFIId()
  {
    return this.jdField_goto;
  }
  
  public void setFIId(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  public String getODFIName()
  {
    return this.jdField_byte;
  }
  
  public void setODFIName(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public String getFIStatus()
  {
    return this.jdField_null;
  }
  
  public void setFIStatus(String paramString)
  {
    this.jdField_null = paramString;
  }
  
  public String getSubmitDate()
  {
    return this.jdField_if;
  }
  
  public void setSubmitDate(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getActivationDate()
  {
    return this.jdField_case;
  }
  
  public void setActivationDate(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public String getLogId()
  {
    return this.jdField_do;
  }
  
  public void setLogId(String paramString)
  {
    this.jdField_do = paramString;
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
  
  public String getCashConDFI()
  {
    return this.jdField_else;
  }
  
  public void setCashConDFI(String paramString)
  {
    this.jdField_else = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHFIInfo
 * JD-Core Version:    0.7.0.1
 */