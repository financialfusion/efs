package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;
import java.util.Hashtable;

public class RPPSBillerInfo
  implements Serializable
{
  protected String jdField_goto;
  protected String d;
  protected String e;
  protected String jdField_case;
  protected String jdField_if;
  protected String a;
  protected String jdField_char;
  protected String jdField_long;
  protected boolean jdField_byte;
  protected boolean c;
  protected boolean jdField_do;
  protected boolean jdField_try;
  protected boolean b;
  protected String f;
  protected String jdField_for;
  protected String jdField_new;
  protected String jdField_void;
  protected int jdField_int = -1;
  protected String jdField_else;
  protected Hashtable jdField_null = null;
  
  public RPPSBillerInfo() {}
  
  public RPPSBillerInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, String paramString9, String paramString10, String paramString11, String paramString12)
  {
    this.jdField_goto = paramString1;
    this.d = paramString2;
    this.e = paramString3;
    this.jdField_case = paramString4;
    this.jdField_if = paramString5;
    this.a = paramString6;
    this.jdField_char = paramString7;
    this.jdField_long = paramString8;
    this.jdField_byte = paramBoolean1;
    this.c = paramBoolean2;
    this.jdField_do = paramBoolean3;
    this.jdField_try = paramBoolean4;
    this.b = paramBoolean5;
    this.f = paramString9;
    this.jdField_for = paramString10;
    this.jdField_new = paramString11;
    this.jdField_void = paramString12;
  }
  
  public String getBillerRPPSId()
  {
    return this.jdField_goto;
  }
  
  public void setBillerRPPSId(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  public String getBillerAliasId()
  {
    return this.d;
  }
  
  public void setBillerAliasId(String paramString)
  {
    this.d = paramString;
  }
  
  public String getFiRPPSId()
  {
    return this.e;
  }
  
  public void setFiRPPSId(String paramString)
  {
    this.e = paramString;
  }
  
  public String getBillerName()
  {
    return this.jdField_case;
  }
  
  public void setBillerName(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public String getEffectiveDate()
  {
    return this.jdField_if;
  }
  
  public void setEffectiveDate(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getTrnABA()
  {
    return this.a;
  }
  
  public void setTrnABA(String paramString)
  {
    this.a = paramString;
  }
  
  public String getBillerClass()
  {
    return this.jdField_char;
  }
  
  public void setBillerClass(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String getBillerType()
  {
    return this.jdField_long;
  }
  
  public void setBillerType(String paramString)
  {
    this.jdField_long = paramString;
  }
  
  public boolean isPrenotes()
  {
    return this.jdField_byte;
  }
  
  public void setPrenotes(boolean paramBoolean)
  {
    this.jdField_byte = paramBoolean;
  }
  
  public boolean isGuarPayOnly()
  {
    return this.c;
  }
  
  public void setGuarPayOnly(boolean paramBoolean)
  {
    this.c = paramBoolean;
  }
  
  public boolean isDmpPrenotes()
  {
    return this.jdField_do;
  }
  
  public void setDmpPrenotes(boolean paramBoolean)
  {
    this.jdField_do = paramBoolean;
  }
  
  public boolean isDmpPayOnly()
  {
    return this.jdField_try;
  }
  
  public void setDmpPayOnly(boolean paramBoolean)
  {
    this.jdField_try = paramBoolean;
  }
  
  public boolean isPrivateFlag()
  {
    return this.b;
  }
  
  public void setPrivateFlag(boolean paramBoolean)
  {
    this.b = paramBoolean;
  }
  
  public String getOldName()
  {
    return this.f;
  }
  
  public void setOldName(String paramString)
  {
    this.f = paramString;
  }
  
  public String getSubmitDate()
  {
    return this.jdField_for;
  }
  
  public void setSubmitDate(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getLogId()
  {
    return this.jdField_void;
  }
  
  public void setLogId(String paramString)
  {
    this.jdField_void = paramString;
  }
  
  public int getStatusCode()
  {
    return this.jdField_int;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.jdField_int = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.jdField_else;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.jdField_else = paramString;
  }
  
  public Hashtable getMemo()
  {
    return this.jdField_null;
  }
  
  public void setMemo(Hashtable paramHashtable)
  {
    this.jdField_null = paramHashtable;
  }
  
  public String getBillerStatus()
  {
    return this.jdField_new;
  }
  
  public void setBillerStatus(String paramString)
  {
    this.jdField_new = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.RPPSBillerInfo
 * JD-Core Version:    0.7.0.1
 */