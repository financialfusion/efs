package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;
import java.util.Hashtable;

public class BPWFIInfo
  implements Serializable
{
  protected String k;
  protected String c;
  protected String[] i;
  protected String j;
  protected String r;
  protected String s;
  protected String jdField_long;
  protected String b;
  protected String jdField_int;
  protected String jdField_do;
  protected String jdField_else;
  protected String jdField_case;
  protected String jdField_byte;
  protected String d;
  protected String z;
  protected String jdField_null;
  protected String o;
  protected String h;
  protected String e;
  protected String v;
  protected String y;
  protected String n;
  protected String w;
  protected String g;
  protected String a;
  protected String jdField_for;
  protected String jdField_char;
  protected String f;
  protected String jdField_goto;
  protected String x;
  protected String l;
  protected int jdField_new;
  protected int jdField_try;
  protected int q = 0;
  protected int u = 90;
  protected String m;
  protected int jdField_if = -1;
  protected String t = null;
  protected String p = null;
  protected Hashtable jdField_void = new Hashtable();
  
  public Hashtable getMemo()
  {
    return this.jdField_void;
  }
  
  public void setMemo(Hashtable paramHashtable)
  {
    this.jdField_void = paramHashtable;
  }
  
  public BPWFIInfo() {}
  
  public BPWFIInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, String paramString19, String paramString20, String paramString21, String paramString22, String paramString23)
  {
    this.k = paramString1;
    this.c = paramString2;
    this.j = paramString3;
    this.r = paramString4;
    this.s = paramString5;
    this.jdField_long = paramString6;
    this.b = paramString7;
    this.jdField_int = paramString8;
    this.jdField_do = paramString9;
    this.jdField_else = paramString10;
    this.jdField_case = paramString11;
    this.jdField_byte = paramString12;
    this.d = paramString13;
    this.z = paramString14;
    this.jdField_null = paramString15;
    this.o = paramString16;
    this.h = paramString17;
    this.e = paramString18;
    this.v = paramString19;
    this.y = paramString20;
    this.n = paramString21;
    this.w = paramString22;
    this.jdField_char = paramString23;
  }
  
  public BPWFIInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, String paramString19, String paramString20, String paramString21, String paramString22, String paramString23, String paramString24)
  {
    this.k = paramString1;
    this.c = paramString2;
    this.j = paramString3;
    this.r = paramString4;
    this.s = paramString5;
    this.jdField_long = paramString6;
    this.b = paramString7;
    this.jdField_int = paramString8;
    this.jdField_do = paramString9;
    this.jdField_else = paramString10;
    this.jdField_case = paramString11;
    this.jdField_byte = paramString12;
    this.d = paramString13;
    this.z = paramString14;
    this.jdField_null = paramString15;
    this.o = paramString16;
    this.h = paramString17;
    this.e = paramString18;
    this.v = paramString19;
    this.y = paramString20;
    this.n = paramString21;
    this.w = paramString22;
    this.jdField_char = paramString23;
    this.p = paramString24;
  }
  
  public String getFIId()
  {
    return this.k;
  }
  
  public void setFIId(String paramString)
  {
    this.k = paramString;
  }
  
  public String getFIRTN()
  {
    return this.c;
  }
  
  public void setFIRTN(String paramString)
  {
    this.c = paramString;
  }
  
  public String getFIType()
  {
    return this.j;
  }
  
  public void setFIType(String paramString)
  {
    this.j = paramString;
  }
  
  public String getFIName()
  {
    return this.r;
  }
  
  public void setFIName(String paramString)
  {
    this.r = paramString;
  }
  
  public String getNickName()
  {
    return this.s;
  }
  
  public void setNickName(String paramString)
  {
    this.s = paramString;
  }
  
  public String getFIStatus()
  {
    return this.jdField_long;
  }
  
  public void setFIStatus(String paramString)
  {
    this.jdField_long = paramString;
  }
  
  public String getFIDesc()
  {
    return this.b;
  }
  
  public void setFIDesc(String paramString)
  {
    this.b = paramString;
  }
  
  public String getFIGroup()
  {
    return this.jdField_int;
  }
  
  public void setFIGroup(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getFIRank()
  {
    return this.jdField_do;
  }
  
  public void setFIRank(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getAddr1()
  {
    return this.jdField_else;
  }
  
  public void setAddr1(String paramString)
  {
    this.jdField_else = paramString;
  }
  
  public String getAddr2()
  {
    return this.jdField_case;
  }
  
  public void setAddr2(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public String getAddr3()
  {
    return this.jdField_byte;
  }
  
  public void setAddr3(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public String getCity()
  {
    return this.d;
  }
  
  public void setCity(String paramString)
  {
    this.d = paramString;
  }
  
  public String getState()
  {
    return this.z;
  }
  
  public void setState(String paramString)
  {
    this.z = paramString;
  }
  
  public String getPostalCode()
  {
    return this.jdField_null;
  }
  
  public void setPostalCode(String paramString)
  {
    this.jdField_null = paramString;
  }
  
  public String getCountry()
  {
    return this.o;
  }
  
  public void setCountry(String paramString)
  {
    this.o = paramString;
  }
  
  public String getDayPhone()
  {
    return this.h;
  }
  
  public void setDayPhone(String paramString)
  {
    this.h = paramString;
  }
  
  public String getExtension()
  {
    return this.e;
  }
  
  public void setExtension(String paramString)
  {
    this.e = paramString;
  }
  
  public String getSubmitDate()
  {
    return this.v;
  }
  
  public void setSubmitDate(String paramString)
  {
    this.v = paramString;
  }
  
  public String getContactName()
  {
    return this.y;
  }
  
  public void setContactName(String paramString)
  {
    this.y = paramString;
  }
  
  public String getActivationDate()
  {
    return this.n;
  }
  
  public void setActivationDate(String paramString)
  {
    this.n = paramString;
  }
  
  public String getAmtLimit()
  {
    return this.w;
  }
  
  public void setAmtLimit(String paramString)
  {
    this.w = paramString;
  }
  
  public String getSwiftRTN()
  {
    return this.g;
  }
  
  public void setSwiftRTN(String paramString)
  {
    this.g = paramString;
  }
  
  public String getChipsRTN()
  {
    return this.a;
  }
  
  public void setChipsRTN(String paramString)
  {
    this.a = paramString;
  }
  
  public String getOtherRTN()
  {
    return this.jdField_for;
  }
  
  public void setOtherRTN(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getLogId()
  {
    return this.jdField_char;
  }
  
  public void setLogId(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public int getStatusCode()
  {
    return this.jdField_if;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.t;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.t = paramString;
  }
  
  public String[] getACHId()
  {
    return this.i;
  }
  
  public void setACHId(String[] paramArrayOfString)
  {
    this.i = paramArrayOfString;
  }
  
  public String getCurrencyCode()
  {
    return this.p;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.p = paramString;
  }
  
  public int getACHTransWareHouse()
  {
    return this.q;
  }
  
  public void setACHTransWareHouse(int paramInt)
  {
    this.q = paramInt;
  }
  
  public int getACHMaxNoFutureDays()
  {
    return this.u;
  }
  
  public void setACHMaxNoFutureDays(int paramInt)
  {
    this.u = paramInt;
  }
  
  public String getACHSameDayEffDate()
  {
    return this.m;
  }
  
  public void setACHSameDayEffDate(String paramString)
  {
    this.m = paramString;
  }
  
  public String getETFDepositAcct()
  {
    return this.f;
  }
  
  public void setETFDepositAcct(String paramString)
  {
    this.f = paramString;
  }
  
  public String getETFDepositAcctType()
  {
    return this.jdField_goto;
  }
  
  public void setETFDepositAcctType(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  public String getETFVirtualUserID()
  {
    return this.x;
  }
  
  public void setETFVirtualUserID(String paramString)
  {
    this.x = paramString;
  }
  
  public String getETFCompanyID()
  {
    return this.l;
  }
  
  public void setETFCompanyID(String paramString)
  {
    this.l = paramString;
  }
  
  public int getETFMinDepositAmt()
  {
    return this.jdField_new;
  }
  
  public void setETFMinDepositAmt(int paramInt)
  {
    this.jdField_new = paramInt;
  }
  
  public int getETFMaxDepositAmt()
  {
    return this.jdField_try;
  }
  
  public void setETFMaxDepositAmt(int paramInt)
  {
    this.jdField_try = paramInt;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.k != null) {
      localStringBuffer.append("fIId=").append(this.k).append(", ");
    } else {
      localStringBuffer.append("fIId=null").append(", ");
    }
    if (this.c != null) {
      localStringBuffer.append("fIRTN=").append(this.c).append(", ");
    } else {
      localStringBuffer.append("fIRTN=null").append(", ");
    }
    if (this.jdField_void != null) {
      localStringBuffer.append("memo=").append(this.jdField_void.toString()).append(", ");
    } else {
      localStringBuffer.append("memo=null").append(", ");
    }
    if (this.p != null) {
      localStringBuffer.append("currencyCode=").append(this.p).append(", ");
    } else {
      localStringBuffer.append("currencyCode=null").append(", ");
    }
    localStringBuffer.append("statusCode=").append(this.jdField_if).append(", ");
    localStringBuffer.append("ACHTransWareHouse=").append(this.q).append(", ");
    localStringBuffer.append("ACHMaxNoFutureDays=").append(this.u).append(", ");
    localStringBuffer.append("etfMinDepositAmt=").append(this.jdField_new).append(", ");
    localStringBuffer.append("etfMaxDepositAmt=").append(this.jdField_try).append(", ");
    if (this.t != null) {
      localStringBuffer.append("statusMsg=").append(this.t).append(", ");
    } else {
      localStringBuffer.append("statusMsg=null").append(", ");
    }
    if (this.jdField_char != null) {
      localStringBuffer.append("logId=").append(this.jdField_char).append(", ");
    } else {
      localStringBuffer.append("logId=null").append(", ");
    }
    if (this.f != null) {
      localStringBuffer.append("etfDepositAcct=").append(this.f).append(", ");
    } else {
      localStringBuffer.append("etfDepositAcct=null").append(", ");
    }
    if (this.jdField_goto != null) {
      localStringBuffer.append("etfDepositAcctType=").append(this.jdField_goto).append(", ");
    } else {
      localStringBuffer.append("etfDepositAcctType=null").append(", ");
    }
    if (this.x != null) {
      localStringBuffer.append("etfVirtualUserID=").append(this.x).append(", ");
    } else {
      localStringBuffer.append("etfVirtualUserID=null").append(", ");
    }
    if (this.l != null) {
      localStringBuffer.append("etfCompanyID=").append(this.l).append(", ");
    } else {
      localStringBuffer.append("etfCompanyID=null").append(", ");
    }
    if (this.m != null) {
      localStringBuffer.append("ACHSameDayEffDate=").append(this.m).append(", ");
    } else {
      localStringBuffer.append("ACHSameDayEffDate=null").append(", ");
    }
    if (this.j != null) {
      localStringBuffer.append("fIType=").append(this.j).append(", ");
    } else {
      localStringBuffer.append("fIType=null").append(", ");
    }
    if (this.r != null) {
      localStringBuffer.append("fIName=").append(this.r).append(", ");
    } else {
      localStringBuffer.append("fIName=null").append(", ");
    }
    if (this.s != null) {
      localStringBuffer.append("nickName=").append(this.s).append(", ");
    } else {
      localStringBuffer.append("nickName=null").append(", ");
    }
    if (this.jdField_long != null) {
      localStringBuffer.append("fIStatus=").append(this.jdField_long).append(", ");
    } else {
      localStringBuffer.append("fIStatus=null").append(", ");
    }
    if (this.b != null) {
      localStringBuffer.append("fIDesc=").append(this.b).append(", ");
    } else {
      localStringBuffer.append("fIDesc=null").append(", ");
    }
    if (this.jdField_int != null) {
      localStringBuffer.append("fIGroup=").append(this.jdField_int).append(", ");
    } else {
      localStringBuffer.append("fIGroup=null").append(", ");
    }
    if (this.jdField_do != null) {
      localStringBuffer.append("fIRank=").append(this.jdField_do).append(", ");
    } else {
      localStringBuffer.append("fIRank=null").append(", ");
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.BPWFIInfo
 * JD-Core Version:    0.7.0.1
 */