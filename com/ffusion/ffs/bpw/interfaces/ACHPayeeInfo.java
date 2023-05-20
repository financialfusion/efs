package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;
import java.text.Collator;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;

public class ACHPayeeInfo
  extends BPWInfoBase
  implements Serializable
{
  protected String n3;
  protected String oh;
  protected String oj;
  protected String oa;
  protected String n6 = "Company";
  protected String nU;
  protected String og;
  protected String nY;
  protected String n0;
  protected String nO = "Checking";
  protected String oe;
  protected String oc;
  protected String n2;
  protected String ob;
  protected String nR;
  protected String nQ;
  protected String nP;
  protected String nZ;
  protected String ol;
  protected String nV;
  protected String od;
  protected String nT;
  protected String n1;
  protected String n7;
  protected String ok;
  protected String n9;
  protected boolean nL = false;
  protected String nS = "Not Required";
  protected String oi = "Not Required";
  protected String n4;
  protected String nN;
  protected String n8 = "PPD";
  protected String n5 = "ACH_PRENOTE_DEMAND_CREDIT";
  protected String nM;
  protected short nX;
  protected Hashtable nW = null;
  protected int of = 0;
  
  public ACHPayeeInfo()
  {
    this.statusCode = -1;
    this.statusMsg = null;
  }
  
  public Hashtable getMemo()
  {
    return this.nW;
  }
  
  public void setMemo(Hashtable paramHashtable)
  {
    this.nW = paramHashtable;
  }
  
  public String getPayeeID()
  {
    return this.n3;
  }
  
  public void setPayeeID(String paramString)
  {
    this.n3 = paramString;
  }
  
  public String getCompId()
  {
    return this.oa;
  }
  
  public void setCompId(String paramString)
  {
    this.oa = paramString;
  }
  
  public String getPayAcct()
  {
    return this.oh;
  }
  
  public void setPayAcct(String paramString)
  {
    this.oh = paramString;
  }
  
  public String getPayeeType()
  {
    return this.oj;
  }
  
  public void setPayeeType(String paramString)
  {
    this.oj = paramString;
  }
  
  public String getPayeeGroup()
  {
    return this.n6;
  }
  
  public void setPayeeGroup(String paramString)
  {
    this.n6 = paramString;
  }
  
  public String getPayeeName()
  {
    return this.nU;
  }
  
  public void setPayeeName(String paramString)
  {
    this.nU = paramString;
  }
  
  public String getNickName()
  {
    return this.og;
  }
  
  public void setNickName(String paramString)
  {
    this.og = paramString;
  }
  
  public String getBankAcctId()
  {
    return this.nY;
  }
  
  public void setBankAcctId(String paramString)
  {
    this.nY = paramString;
  }
  
  public String getBankRTN()
  {
    return this.n0;
  }
  
  public String getBankRTN8()
  {
    if (this.n0.trim().length() > 8) {
      return this.n0.trim().substring(0, 8);
    }
    return this.n0;
  }
  
  public void setBankRTN(String paramString)
  {
    this.n0 = paramString;
  }
  
  public String getBankAcctType()
  {
    return this.nO;
  }
  
  public void setBankAcctType(String paramString)
  {
    this.nO = paramString;
  }
  
  public String getCardNum()
  {
    return this.oe;
  }
  
  public void setCardNum(String paramString)
  {
    this.oe = paramString;
  }
  
  public String getCardExpireDate()
  {
    return this.oc;
  }
  
  public void setCardExpireDate(String paramString)
  {
    this.oc = paramString;
  }
  
  public String getCardTransCode()
  {
    return this.n2;
  }
  
  public void setCardTransCode(String paramString)
  {
    this.n2 = paramString;
  }
  
  public String getCardAuthCode()
  {
    return this.ob;
  }
  
  public void setCardAuthCode(String paramString)
  {
    this.ob = paramString;
  }
  
  public String getAddr1()
  {
    return this.nR;
  }
  
  public void setAddr1(String paramString)
  {
    this.nR = paramString;
  }
  
  public String getAddr2()
  {
    return this.nQ;
  }
  
  public void setAddr2(String paramString)
  {
    this.nQ = paramString;
  }
  
  public String getAddr3()
  {
    return this.nP;
  }
  
  public void setAddr3(String paramString)
  {
    this.nP = paramString;
  }
  
  public String getCity()
  {
    return this.nZ;
  }
  
  public void setCity(String paramString)
  {
    this.nZ = paramString;
  }
  
  public String getState()
  {
    return this.ol;
  }
  
  public void setState(String paramString)
  {
    this.ol = paramString;
  }
  
  public String getPostalCode()
  {
    return this.nV;
  }
  
  public void setPostalCode(String paramString)
  {
    this.nV = paramString;
  }
  
  public String getCountry()
  {
    return this.od;
  }
  
  public void setCountry(String paramString)
  {
    this.od = paramString;
  }
  
  public String getPhone()
  {
    return this.nT;
  }
  
  public void setPhone(String paramString)
  {
    this.nT = paramString;
  }
  
  public String getExtension()
  {
    return this.n1;
  }
  
  public void setExtension(String paramString)
  {
    this.n1 = paramString;
  }
  
  public String getStatus()
  {
    return this.n7;
  }
  
  public void setStatus(String paramString)
  {
    this.n7 = paramString;
  }
  
  public String getContactName()
  {
    return this.ok;
  }
  
  public void setContactName(String paramString)
  {
    this.ok = paramString;
  }
  
  public String getActivationDate()
  {
    return this.n9;
  }
  
  public void setActivationDate(String paramString)
  {
    this.n9 = paramString;
  }
  
  public boolean getDoPrenote()
  {
    return this.nL;
  }
  
  public void setDoPrenote(boolean paramBoolean)
  {
    this.nL = paramBoolean;
  }
  
  public String getPrenoteCreditStatus()
  {
    return this.nS;
  }
  
  public void setPrenoteCreditStatus(String paramString)
  {
    this.nS = paramString;
  }
  
  public String getPrenoteDebitStatus()
  {
    return this.oi;
  }
  
  public void setPrenoteDebitStatus(String paramString)
  {
    this.oi = paramString;
  }
  
  public String getPrenoteSubmitDate()
  {
    return this.n4;
  }
  
  public void setPrenoteSubmitDate(String paramString)
  {
    this.n4 = paramString;
  }
  
  public String getPrenoteSecCode()
  {
    return this.n8;
  }
  
  public void setPrenoteSecCode(String paramString)
  {
    this.n8 = paramString;
  }
  
  public String getPrenoteDemand()
  {
    return this.n5;
  }
  
  public void setPrenoteDemand(String paramString)
  {
    this.n5 = paramString;
  }
  
  public String getManagedPayee()
  {
    return this.nM;
  }
  
  public void setManagedPayee(String paramString)
  {
    this.nM = paramString;
  }
  
  public short getCheckDigit()
  {
    return this.nX;
  }
  
  public void setCheckDigit(short paramShort)
  {
    this.nX = paramShort;
  }
  
  public boolean checkAndSetPrenoteStatuses()
  {
    if (!this.nL)
    {
      this.nS = "Not Required";
      this.oi = "Not Required";
    }
    else
    {
      if ("ACH_PRENOTE_DEMAND_CREDIT".equals(this.n5))
      {
        this.nS = "Pending";
        this.oi = "Not Required";
      }
      else if ("ACH_PRENOTE_DEMAND_DEBIT".equals(this.n5))
      {
        this.nS = "Not Required";
        this.oi = "Pending";
      }
      else if ("ACH_PRENOTE_DEMAND_BOTH".equals(this.n5))
      {
        this.nS = "Pending";
        this.oi = "Pending";
      }
      else
      {
        this.statusCode = 23560;
        String str = "Unknown prenote demand: " + this.n5;
        this.statusMsg = str;
        return false;
      }
      this.n4 = null;
    }
    return true;
  }
  
  public String getPrenoteMaturedDate()
  {
    return this.nN;
  }
  
  public void setPrenoteMaturedDate(String paramString)
  {
    this.nN = paramString;
  }
  
  public boolean comparePrenoteInfo(ACHPayeeInfo paramACHPayeeInfo)
  {
    if (!jdMethod_case(getPrenoteSecCode(), paramACHPayeeInfo.getPrenoteSecCode())) {
      return false;
    }
    if (!jdMethod_case(getPrenoteDemand(), paramACHPayeeInfo.getPrenoteDemand())) {
      return false;
    }
    if (!jdMethod_case(getPayeeName(), paramACHPayeeInfo.getPayeeName())) {
      return false;
    }
    if (!jdMethod_case(getPayAcct(), paramACHPayeeInfo.getPayAcct())) {
      return false;
    }
    if (!jdMethod_case(getBankRTN(), paramACHPayeeInfo.getBankRTN())) {
      return false;
    }
    if (!jdMethod_case(getBankAcctType(), paramACHPayeeInfo.getBankAcctType())) {
      return false;
    }
    return jdMethod_case(getBankAcctId(), paramACHPayeeInfo.getBankAcctId());
  }
  
  private boolean jdMethod_case(String paramString1, String paramString2)
  {
    Collator localCollator = Collator.getInstance(Locale.getDefault());
    if (paramString1 == null)
    {
      if (paramString2 != null) {
        return false;
      }
    }
    else
    {
      if (paramString2 == null) {
        return false;
      }
      if (localCollator.compare(paramString1, paramString2) != 0) {
        return false;
      }
    }
    return true;
  }
  
  public String getDFIAccountNumber(String paramString)
  {
    if (this.nY == null) {
      return null;
    }
    String str = " ";
    StringBuffer localStringBuffer = new StringBuffer();
    StringTokenizer localStringTokenizer = new StringTokenizer(this.nY, str);
    while (localStringTokenizer.hasMoreElements()) {
      localStringBuffer.append(localStringTokenizer.nextToken());
    }
    if ((paramString != null) && (paramString.equals("ADV")))
    {
      if (localStringBuffer.length() > 15) {
        return localStringBuffer.substring(0, 15);
      }
    }
    else if (localStringBuffer.length() > 17) {
      return localStringBuffer.substring(0, 17);
    }
    return new String(localStringBuffer);
  }
  
  public int getSecurePayee()
  {
    return this.of;
  }
  
  public void setSecurePayee(int paramInt)
  {
    this.of = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo
 * JD-Core Version:    0.7.0.1
 */