package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;
import java.util.Hashtable;

public class ACHCompanyInfo
  extends BPWInfoBase
  implements Serializable
{
  protected String o8;
  protected String pe;
  protected String o9;
  protected String pm;
  protected String o3;
  protected String pg;
  protected String oW;
  protected String oY;
  protected String pd;
  protected String o1;
  protected String pb;
  protected String o4;
  protected String o2;
  protected String pj;
  protected String pi;
  protected String ph;
  protected String pf;
  protected String oX;
  protected String o7;
  protected String pc;
  protected String o0;
  protected String o6;
  protected String pl;
  protected String pa;
  protected String o5;
  protected Hashtable pk = null;
  protected String oZ;
  
  public Hashtable getMemo()
  {
    return this.pk;
  }
  
  public void setMemo(Hashtable paramHashtable)
  {
    this.pk = paramHashtable;
  }
  
  public ACHCompanyInfo()
  {
    this.statusCode = -1;
    this.statusMsg = null;
  }
  
  public ACHCompanyInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, String paramString19, String paramString20, String paramString21, String paramString22, String paramString23, String paramString24, String paramString25, String paramString26, String paramString27)
  {
    this.statusCode = -1;
    this.statusMsg = null;
    this.o8 = paramString1;
    this.pe = paramString2;
    this.o9 = paramString3;
    this.pm = paramString4;
    this.o3 = paramString5;
    this.pg = paramString6;
    this.oW = paramString7;
    this.oY = paramString9;
    this.pd = paramString10;
    this.o1 = paramString11;
    this.pb = paramString12;
    this.o4 = paramString13;
    this.o2 = paramString14;
    this.pj = paramString15;
    this.pi = paramString16;
    this.ph = paramString17;
    this.pf = paramString18;
    this.oX = paramString19;
    this.o7 = paramString20;
    this.pc = paramString21;
    this.o0 = paramString22;
    this.o6 = paramString23;
    this.pl = paramString24;
    this.submitDate = paramString25;
    this.pa = paramString26;
    this.logId = paramString27;
    this.o5 = paramString8;
  }
  
  public String getCompId()
  {
    return this.o8;
  }
  
  public void setCompId(String paramString)
  {
    this.o8 = paramString;
  }
  
  public String getODFIACHId()
  {
    return this.pe;
  }
  
  public String getODFIACHId8()
  {
    if ((this.pe != null) && (this.pe.trim().length() > 8)) {
      return this.pe.trim().substring(0, 8);
    }
    return this.pe;
  }
  
  public void setODFIACHId(String paramString)
  {
    this.pe = paramString;
  }
  
  public String getCompName()
  {
    return this.o9;
  }
  
  public void setCompName(String paramString)
  {
    this.o9 = paramString;
  }
  
  public String getNickName()
  {
    return this.pm;
  }
  
  public void setNickName(String paramString)
  {
    this.pm = paramString;
  }
  
  public String getCompACHId()
  {
    return this.o3;
  }
  
  public void setCompACHId(String paramString)
  {
    this.o3 = paramString;
  }
  
  public String getBankAcctId()
  {
    return this.pg;
  }
  
  public void setBankAcctId(String paramString)
  {
    this.pg = paramString;
  }
  
  public String getBankId()
  {
    return this.oW;
  }
  
  public void setBankId(String paramString)
  {
    this.oW = paramString;
  }
  
  public String getBankAcctType()
  {
    return this.oY;
  }
  
  public void setBankAcctType(String paramString)
  {
    this.oY = paramString;
  }
  
  public String getCompStatus()
  {
    return this.pd;
  }
  
  public void setCompStatus(String paramString)
  {
    this.pd = paramString;
  }
  
  public String getCompDesc()
  {
    return this.o1;
  }
  
  public void setCompDesc(String paramString)
  {
    this.o1 = paramString;
  }
  
  public String getCompType()
  {
    return this.pb;
  }
  
  public void setCompType(String paramString)
  {
    this.pb = paramString;
  }
  
  public String getCompGroup()
  {
    return this.o4;
  }
  
  public void setCompGroup(String paramString)
  {
    this.o4 = paramString;
  }
  
  public String getCompRank()
  {
    return this.o2;
  }
  
  public void setCompRank(String paramString)
  {
    this.o2 = paramString;
  }
  
  public String getAddr1()
  {
    return this.pj;
  }
  
  public void setAddr1(String paramString)
  {
    this.pj = paramString;
  }
  
  public String getAddr2()
  {
    return this.pi;
  }
  
  public void setAddr2(String paramString)
  {
    this.pi = paramString;
  }
  
  public String getAddr3()
  {
    return this.ph;
  }
  
  public void setAddr3(String paramString)
  {
    this.ph = paramString;
  }
  
  public String getCity()
  {
    return this.pf;
  }
  
  public void setCity(String paramString)
  {
    this.pf = paramString;
  }
  
  public String getState()
  {
    return this.oX;
  }
  
  public void setState(String paramString)
  {
    this.oX = paramString;
  }
  
  public String getPostalCode()
  {
    return this.o7;
  }
  
  public void setPostalCode(String paramString)
  {
    this.o7 = paramString;
  }
  
  public String getCountry()
  {
    return this.pc;
  }
  
  public void setCountry(String paramString)
  {
    this.pc = paramString;
  }
  
  public String getPhone()
  {
    return this.o0;
  }
  
  public void setPhone(String paramString)
  {
    this.o0 = paramString;
  }
  
  public String getExtension()
  {
    return this.o6;
  }
  
  public void setExtension(String paramString)
  {
    this.o6 = paramString;
  }
  
  public String getContactName()
  {
    return this.pl;
  }
  
  public void setContactName(String paramString)
  {
    this.pl = paramString;
  }
  
  public String getActivationDate()
  {
    return this.pa;
  }
  
  public void setActivationDate(String paramString)
  {
    this.pa = paramString;
  }
  
  public String getCustomerId()
  {
    return this.o5;
  }
  
  public void setCustomerId(String paramString)
  {
    this.o5 = paramString;
  }
  
  public String getBatchType()
  {
    return this.oZ;
  }
  
  public void setBatchType(String paramString)
  {
    this.oZ = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo
 * JD-Core Version:    0.7.0.1
 */