package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class BPWBankInfo
  implements Serializable
{
  protected String jdField_if;
  protected String jdField_else;
  protected String jdField_case;
  protected String jdField_int;
  protected String jdField_do;
  protected String a;
  protected String jdField_new;
  protected String jdField_goto;
  protected String jdField_char;
  protected String jdField_try;
  protected String jdField_null;
  protected String jdField_void;
  protected String jdField_for;
  protected String d;
  protected String c;
  protected int jdField_byte = -1;
  protected String jdField_long;
  protected String b = "";
  
  public String getBankId()
  {
    return this.jdField_if;
  }
  
  public void setBankId(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getExtBankId()
  {
    return this.jdField_else;
  }
  
  public void setExtBankId(String paramString)
  {
    this.jdField_else = paramString;
  }
  
  public String getBankName()
  {
    return this.jdField_case;
  }
  
  public void setBankName(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public String getBankAddr1()
  {
    return this.jdField_int;
  }
  
  public void setBankAddr1(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getBankAddr2()
  {
    return this.jdField_do;
  }
  
  public void setBankAddr2(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getBankAddr3()
  {
    return this.a;
  }
  
  public void setBankAddr3(String paramString)
  {
    this.a = paramString;
  }
  
  public String getBankCity()
  {
    return this.jdField_new;
  }
  
  public void setBankCity(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public String getBankState()
  {
    return this.jdField_goto;
  }
  
  public void setBankState(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  public String getBankPSCode()
  {
    return this.jdField_char;
  }
  
  public void setBankPSCode(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String getBankCountry()
  {
    return this.jdField_try;
  }
  
  public void setBankCountry(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public String getBankPhone()
  {
    return this.jdField_null;
  }
  
  public void setBankPhone(String paramString)
  {
    this.jdField_null = paramString;
  }
  
  public String getSwiftRTN()
  {
    return this.jdField_void;
  }
  
  public void setSwiftRTN(String paramString)
  {
    this.jdField_void = paramString;
  }
  
  public String getChipsRTN()
  {
    return this.jdField_for;
  }
  
  public void setChipsRTN(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getFedRTN()
  {
    return this.d;
  }
  
  public void setFedRTN(String paramString)
  {
    this.d = paramString;
  }
  
  public String getOtherRTN()
  {
    return this.c;
  }
  
  public void setOtherRTN(String paramString)
  {
    this.c = paramString;
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
    return this.jdField_long;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.jdField_long = paramString;
  }
  
  public String getAction()
  {
    return this.b;
  }
  
  public void setAction(String paramString)
  {
    this.b = paramString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("bankId=").append(this.jdField_if);
    localStringBuffer.append(",bankName=").append(this.jdField_case);
    localStringBuffer.append(",extBankId=").append(this.jdField_else);
    localStringBuffer.append(",bankAddr1=").append(this.jdField_int);
    localStringBuffer.append(",bankAddr2=").append(this.jdField_do);
    localStringBuffer.append(",bankAddr3=").append(this.a);
    localStringBuffer.append(",bankCity=").append(this.jdField_new);
    localStringBuffer.append(",bankState=").append(this.jdField_goto);
    localStringBuffer.append(",bankPSCode=").append(this.jdField_char);
    localStringBuffer.append(",bankCountry=").append(this.jdField_try);
    localStringBuffer.append(",bankPhone=").append(this.jdField_null);
    localStringBuffer.append(",swiftRTN=").append(this.jdField_void);
    localStringBuffer.append(",chipsRTN=").append(this.jdField_for);
    localStringBuffer.append(",fedRTN=").append(this.d);
    localStringBuffer.append(",otherRTN=").append(this.c);
    return localStringBuffer.toString();
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof BPWBankInfo)) {
      return false;
    }
    BPWBankInfo localBPWBankInfo = (BPWBankInfo)paramObject;
    if (this.jdField_int != null ? !this.jdField_int.equals(localBPWBankInfo.jdField_int) : localBPWBankInfo.jdField_int != null) {
      return false;
    }
    if (this.jdField_do != null ? !this.jdField_do.equals(localBPWBankInfo.jdField_do) : localBPWBankInfo.jdField_do != null) {
      return false;
    }
    if (this.a != null ? !this.a.equals(localBPWBankInfo.a) : localBPWBankInfo.a != null) {
      return false;
    }
    if (this.jdField_new != null ? !this.jdField_new.equals(localBPWBankInfo.jdField_new) : localBPWBankInfo.jdField_new != null) {
      return false;
    }
    if (this.jdField_try != null ? !this.jdField_try.equals(localBPWBankInfo.jdField_try) : localBPWBankInfo.jdField_try != null) {
      return false;
    }
    if (this.jdField_if != null ? !this.jdField_if.equals(localBPWBankInfo.jdField_if) : localBPWBankInfo.jdField_if != null) {
      return false;
    }
    if (this.jdField_case != null ? !this.jdField_case.equals(localBPWBankInfo.jdField_case) : localBPWBankInfo.jdField_case != null) {
      return false;
    }
    if (this.jdField_char != null ? !this.jdField_char.equals(localBPWBankInfo.jdField_char) : localBPWBankInfo.jdField_char != null) {
      return false;
    }
    if (this.jdField_null != null ? !this.jdField_null.equals(localBPWBankInfo.jdField_null) : localBPWBankInfo.jdField_null != null) {
      return false;
    }
    if (this.jdField_goto != null ? !this.jdField_goto.equals(localBPWBankInfo.jdField_goto) : localBPWBankInfo.jdField_goto != null) {
      return false;
    }
    if (this.jdField_for != null ? !this.jdField_for.equals(localBPWBankInfo.jdField_for) : localBPWBankInfo.jdField_for != null) {
      return false;
    }
    if (this.jdField_else != null ? !this.jdField_else.equals(localBPWBankInfo.jdField_else) : localBPWBankInfo.jdField_else != null) {
      return false;
    }
    if (this.d != null ? !this.d.equals(localBPWBankInfo.d) : localBPWBankInfo.d != null) {
      return false;
    }
    if (this.c != null ? !this.c.equals(localBPWBankInfo.c) : localBPWBankInfo.c != null) {
      return false;
    }
    return this.jdField_void != null ? this.jdField_void.equals(localBPWBankInfo.jdField_void) : localBPWBankInfo.jdField_void == null;
  }
  
  public int hashCode()
  {
    int i = this.jdField_if != null ? this.jdField_if.hashCode() : 0;
    i = 29 * i + (this.jdField_else != null ? this.jdField_else.hashCode() : 0);
    i = 29 * i + (this.jdField_case != null ? this.jdField_case.hashCode() : 0);
    i = 29 * i + (this.jdField_int != null ? this.jdField_int.hashCode() : 0);
    i = 29 * i + (this.jdField_do != null ? this.jdField_do.hashCode() : 0);
    i = 29 * i + (this.a != null ? this.a.hashCode() : 0);
    i = 29 * i + (this.jdField_new != null ? this.jdField_new.hashCode() : 0);
    i = 29 * i + (this.jdField_goto != null ? this.jdField_goto.hashCode() : 0);
    i = 29 * i + (this.jdField_char != null ? this.jdField_char.hashCode() : 0);
    i = 29 * i + (this.jdField_try != null ? this.jdField_try.hashCode() : 0);
    i = 29 * i + (this.jdField_null != null ? this.jdField_null.hashCode() : 0);
    i = 29 * i + (this.jdField_void != null ? this.jdField_void.hashCode() : 0);
    i = 29 * i + (this.jdField_for != null ? this.jdField_for.hashCode() : 0);
    i = 29 * i + (this.d != null ? this.d.hashCode() : 0);
    i = 29 * i + (this.c != null ? this.c.hashCode() : 0);
    return i;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.BPWBankInfo
 * JD-Core Version:    0.7.0.1
 */