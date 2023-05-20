package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.bpw.util.BPWUtil;
import java.util.Hashtable;

public class WirePayeeInfo
  extends BPWInfoBase
{
  protected String sl;
  protected String rW;
  protected String r4;
  protected String r8;
  protected String so;
  protected String sm;
  protected String rV;
  protected String sj;
  protected BPWBankInfo r7;
  protected String r2;
  protected String se;
  protected String r0;
  protected String r5;
  protected String sg;
  protected String sf;
  protected String sd;
  protected String sa;
  protected String rY;
  protected String rX;
  protected String rZ;
  protected String r1;
  protected String sb;
  protected String r9;
  protected String r6;
  protected BPWBankInfo[] sn = null;
  protected Hashtable si = null;
  protected String sh;
  protected String rT;
  protected String rU;
  protected Hashtable sk;
  protected String r3;
  protected String sc;
  
  public WirePayeeInfo()
  {
    this.statusCode = -1;
    this.statusMsg = null;
  }
  
  public String getRouteId()
  {
    return this.sh;
  }
  
  public void setRouteId(String paramString)
  {
    this.sh = paramString;
  }
  
  public String getPayeeExtId()
  {
    return this.rT;
  }
  
  public void setPayeeExtId(String paramString)
  {
    this.rT = paramString;
  }
  
  public String getExtId()
  {
    return this.rU;
  }
  
  public void setExtId(String paramString)
  {
    this.rU = paramString;
  }
  
  public Hashtable getExtInfo()
  {
    return this.sk;
  }
  
  public void setExtInfo(Hashtable paramHashtable)
  {
    this.sk = paramHashtable;
  }
  
  public String getPayeeDest()
  {
    return this.r3;
  }
  
  public void setPayeeDest(String paramString)
  {
    this.r3 = paramString;
  }
  
  public Hashtable getMemo()
  {
    return this.si;
  }
  
  public void setMemo(Hashtable paramHashtable)
  {
    this.si = paramHashtable;
  }
  
  public String getPayeeId()
  {
    return this.sl;
  }
  
  public void setPayeeId(String paramString)
  {
    this.sl = paramString;
  }
  
  public String getPayeeType()
  {
    return this.rW;
  }
  
  public void setPayeeType(String paramString)
  {
    this.rW = paramString;
  }
  
  public String getPayeeGroup()
  {
    return this.r4;
  }
  
  public void setPayeeGroup(String paramString)
  {
    this.r4 = paramString;
  }
  
  public String getBeneficiaryName()
  {
    return this.r8;
  }
  
  public void setBeneficiaryName(String paramString)
  {
    this.r8 = paramString;
  }
  
  public String getNickName()
  {
    return this.so;
  }
  
  public void setNickName(String paramString)
  {
    this.so = paramString;
  }
  
  public String getContactName()
  {
    return this.sm;
  }
  
  public void setContactName(String paramString)
  {
    this.sm = paramString;
  }
  
  public String getCustomerID()
  {
    return this.rV;
  }
  
  public void setCustomerID(String paramString)
  {
    this.rV = paramString;
  }
  
  public String getBranchId()
  {
    return this.r2;
  }
  
  public void setBranchId(String paramString)
  {
    this.r2 = paramString;
  }
  
  public String getBankAcctId()
  {
    return this.se;
  }
  
  public void setBankAcctId(String paramString)
  {
    this.se = paramString;
  }
  
  public String getBankAcctType()
  {
    return this.r0;
  }
  
  public void setBankAcctType(String paramString)
  {
    this.r0 = paramString;
  }
  
  public String getAcctKey()
  {
    return this.r5;
  }
  
  public void setAcctKey(String paramString)
  {
    this.r5 = paramString;
  }
  
  public String getBeneficiaryBankId()
  {
    return this.sj;
  }
  
  public void setBeneficiaryBankId(String paramString)
  {
    this.sj = paramString;
  }
  
  public BPWBankInfo getBeneficiaryBankInfo()
  {
    return this.r7;
  }
  
  public void setBeneficiaryBankInfo(BPWBankInfo paramBPWBankInfo)
  {
    this.r7 = paramBPWBankInfo;
  }
  
  public String getPayeeAddr1()
  {
    return this.sg;
  }
  
  public void setPayeeAddr1(String paramString)
  {
    this.sg = paramString;
  }
  
  public String getPayeeAddr2()
  {
    return this.sf;
  }
  
  public void setPayeeAddr2(String paramString)
  {
    this.sf = paramString;
  }
  
  public String getPayeeAddr3()
  {
    return this.sd;
  }
  
  public void setPayeeAddr3(String paramString)
  {
    this.sd = paramString;
  }
  
  public String getPayeeCity()
  {
    return this.sa;
  }
  
  public void setPayeeCity(String paramString)
  {
    this.sa = paramString;
  }
  
  public String getPayeeState()
  {
    return this.rY;
  }
  
  public void setPayeeState(String paramString)
  {
    this.rY = paramString;
  }
  
  public String getPayeeZipcode()
  {
    return this.rX;
  }
  
  public void setPayeeZipcode(String paramString)
  {
    this.rX = paramString;
  }
  
  public String getPayeeCountry()
  {
    return this.rZ;
  }
  
  public void setPayeeCountry(String paramString)
  {
    this.rZ = paramString;
  }
  
  public String getPayeePhone()
  {
    return this.r1;
  }
  
  public void setPayeePhone(String paramString)
  {
    this.r1 = paramString;
  }
  
  public String getStatus()
  {
    return this.sb;
  }
  
  public void setStatus(String paramString)
  {
    this.sb = paramString;
  }
  
  public String getLastModDate()
  {
    return this.r9;
  }
  
  public void setLastModDate(String paramString)
  {
    this.r9 = paramString;
  }
  
  public String getActivationDate()
  {
    return this.r6;
  }
  
  public void setActivationDate(String paramString)
  {
    this.r6 = paramString;
  }
  
  public String getAction()
  {
    return this.sc;
  }
  
  public void setAction(String paramString)
  {
    this.sc = paramString;
  }
  
  public BPWBankInfo[] getIntermediateBanks()
  {
    return this.sn;
  }
  
  public String buildBankAcctId()
    throws Exception
  {
    return BPWUtil.getAccountIDWithAccountType(this.se, this.r0);
  }
  
  public BPWBankInfo getIntermediateBankAt(int paramInt)
  {
    if ((this.sn == null) || (paramInt < 0) || (paramInt >= this.sn.length)) {
      return null;
    }
    return this.sn[paramInt];
  }
  
  public void setIntermediateBanks(BPWBankInfo[] paramArrayOfBPWBankInfo)
  {
    this.sn = paramArrayOfBPWBankInfo;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.sl != null) {
      localStringBuffer.append("payeeId=").append(this.sl);
    } else {
      localStringBuffer.append("payeeId=null");
    }
    if (this.rW != null) {
      localStringBuffer.append(",payeeType=").append(this.rW);
    } else {
      localStringBuffer.append(",payeeType=null");
    }
    if (this.r4 != null) {
      localStringBuffer.append(",payeeGroup=").append(this.r4);
    } else {
      localStringBuffer.append(",payeeGroup=null");
    }
    if (this.r8 != null) {
      localStringBuffer.append(",beneficiaryName=").append(this.r8);
    } else {
      localStringBuffer.append(",beneficiaryName=null");
    }
    if (this.so != null) {
      localStringBuffer.append(",nickName=").append(this.so);
    } else {
      localStringBuffer.append(",nickName=null");
    }
    if (this.sm != null) {
      localStringBuffer.append(",contactName=").append(this.sm);
    } else {
      localStringBuffer.append(",contactName=null");
    }
    if (this.rV != null) {
      localStringBuffer.append(",customerID=").append(this.rV);
    } else {
      localStringBuffer.append(",customerID=null");
    }
    if (this.sh != null) {
      localStringBuffer.append(",routeId=").append(this.sh);
    } else {
      localStringBuffer.append(",routeId=null");
    }
    if (this.rT != null) {
      localStringBuffer.append(",payeeExtId=").append(this.rT);
    } else {
      localStringBuffer.append(",payeeExtId=null");
    }
    if (this.rU != null) {
      localStringBuffer.append(",extId=").append(this.rU);
    } else {
      localStringBuffer.append(",extId=null");
    }
    if (this.sj != null) {
      localStringBuffer.append(",beneficiaryBankId=").append(this.sj);
    } else {
      localStringBuffer.append(",beneficiaryBankId=null");
    }
    if (this.r2 != null) {
      localStringBuffer.append(",branchId=").append(this.r2);
    } else {
      localStringBuffer.append(",branchId=null");
    }
    if (this.se != null) {
      localStringBuffer.append(",bankAcctId=").append(this.se);
    } else {
      localStringBuffer.append(",bankAcctId=null");
    }
    if (this.r0 != null) {
      localStringBuffer.append(",bankAcctType=").append(this.r0);
    } else {
      localStringBuffer.append(",bankAcctType=null");
    }
    if (this.r5 != null) {
      localStringBuffer.append(",acctKey=").append(this.r5);
    } else {
      localStringBuffer.append(",acctKey=null");
    }
    if (this.sg != null) {
      localStringBuffer.append(",payeeAddr1=").append(this.sg);
    } else {
      localStringBuffer.append(",payeeAddr1=null");
    }
    if (this.sf != null) {
      localStringBuffer.append(",payeeAddr2=").append(this.sf);
    } else {
      localStringBuffer.append(",payeeAddr2=null");
    }
    if (this.sd != null) {
      localStringBuffer.append(",payeeAddr3=").append(this.sd);
    } else {
      localStringBuffer.append(",payeeAddr3=null");
    }
    if (this.sa != null) {
      localStringBuffer.append(",payeeCity=").append(this.sa);
    } else {
      localStringBuffer.append(",payeeCity=null");
    }
    if (this.rY != null) {
      localStringBuffer.append(",payeeState=").append(this.rY);
    } else {
      localStringBuffer.append(",payeeState=null");
    }
    if (this.rX != null) {
      localStringBuffer.append(",payeeZipcode=").append(this.rX);
    } else {
      localStringBuffer.append(",payeeZipcode=null");
    }
    if (this.rZ != null) {
      localStringBuffer.append(",payeeCountry=").append(this.rZ);
    } else {
      localStringBuffer.append(",payeeCountry=null");
    }
    if (this.r1 != null) {
      localStringBuffer.append(",payeePhone=").append(this.r1);
    } else {
      localStringBuffer.append(",payeePhone=null");
    }
    if (this.sb != null) {
      localStringBuffer.append(",status=").append(this.r1);
    } else {
      localStringBuffer.append(",status=null");
    }
    if (this.submitDate != null) {
      localStringBuffer.append(",submitDate=").append(this.submitDate);
    } else {
      localStringBuffer.append(",submitDate=null");
    }
    if (this.r9 != null) {
      localStringBuffer.append(",lastModDate=").append(this.r9);
    } else {
      localStringBuffer.append(",lastModDate=null");
    }
    if (this.r6 != null) {
      localStringBuffer.append(",activationDate=").append(this.r6);
    } else {
      localStringBuffer.append(",activationDate=null");
    }
    if (this.logId != null) {
      localStringBuffer.append(",logId=").append(this.logId);
    } else {
      localStringBuffer.append(",logId=null");
    }
    if (this.submittedBy != null) {
      localStringBuffer.append(",submittedBy=").append(this.submittedBy);
    } else {
      localStringBuffer.append(",submittedBy=null");
    }
    if (this.r3 != null) {
      localStringBuffer.append(",payeeDest=").append(this.r3);
    } else {
      localStringBuffer.append(",payeeDest=null");
    }
    return localStringBuffer.toString();
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof WirePayeeInfo)) {
      return false;
    }
    WirePayeeInfo localWirePayeeInfo = (WirePayeeInfo)paramObject;
    if (this.r5 != null ? !this.r5.equals(localWirePayeeInfo.r5) : localWirePayeeInfo.r5 != null) {
      return false;
    }
    if (this.se != null ? !this.se.equals(localWirePayeeInfo.se) : localWirePayeeInfo.se != null) {
      return false;
    }
    if (this.r0 != null ? !this.r0.equals(localWirePayeeInfo.r0) : localWirePayeeInfo.r0 != null) {
      return false;
    }
    if (this.sj != null ? !this.sj.equals(localWirePayeeInfo.sj) : localWirePayeeInfo.sj != null) {
      return false;
    }
    if (this.r7 != null ? !this.r7.equals(localWirePayeeInfo.r7) : localWirePayeeInfo.r7 != null) {
      return false;
    }
    if (this.r8 != null ? !this.r8.equals(localWirePayeeInfo.r8) : localWirePayeeInfo.r8 != null) {
      return false;
    }
    if (this.sm != null ? !this.sm.equals(localWirePayeeInfo.sm) : localWirePayeeInfo.sm != null) {
      return false;
    }
    if (this.rV != null ? !this.rV.equals(localWirePayeeInfo.rV) : localWirePayeeInfo.rV != null) {
      return false;
    }
    if (this.rU != null ? !this.rU.equals(localWirePayeeInfo.rU) : localWirePayeeInfo.rU != null) {
      return false;
    }
    if ((this.sn != null) && (this.sn.length > 0))
    {
      BPWBankInfo[] arrayOfBPWBankInfo = localWirePayeeInfo.sn;
      if (this.sn.length != arrayOfBPWBankInfo.length) {
        return false;
      }
      for (int i = 0; i < this.sn.length; i++) {
        if (!this.sn[i].equals(arrayOfBPWBankInfo[i])) {
          return false;
        }
      }
    }
    if (this.so != null ? !this.so.equals(localWirePayeeInfo.so) : localWirePayeeInfo.so != null) {
      return false;
    }
    if (this.sg != null ? !this.sg.equals(localWirePayeeInfo.sg) : localWirePayeeInfo.sg != null) {
      return false;
    }
    if (this.sf != null ? !this.sf.equals(localWirePayeeInfo.sf) : localWirePayeeInfo.sf != null) {
      return false;
    }
    if (this.sd != null ? !this.sd.equals(localWirePayeeInfo.sd) : localWirePayeeInfo.sd != null) {
      return false;
    }
    if (this.sa != null ? !this.sa.equals(localWirePayeeInfo.sa) : localWirePayeeInfo.sa != null) {
      return false;
    }
    if (this.rZ != null ? !this.rZ.equals(localWirePayeeInfo.rZ) : localWirePayeeInfo.rZ != null) {
      return false;
    }
    if (this.r3 != null ? !this.r3.equals(localWirePayeeInfo.r3) : localWirePayeeInfo.r3 != null) {
      return false;
    }
    if (this.r4 != null ? !this.r4.equals(localWirePayeeInfo.r4) : localWirePayeeInfo.r4 != null) {
      return false;
    }
    if (this.sl != null ? !this.sl.equals(localWirePayeeInfo.sl) : localWirePayeeInfo.sl != null) {
      return false;
    }
    if (this.r1 != null ? !this.r1.equals(localWirePayeeInfo.r1) : localWirePayeeInfo.r1 != null) {
      return false;
    }
    if (this.rY != null ? !this.rY.equals(localWirePayeeInfo.rY) : localWirePayeeInfo.rY != null) {
      return false;
    }
    if (this.rW != null ? !this.rW.equals(localWirePayeeInfo.rW) : localWirePayeeInfo.rW != null) {
      return false;
    }
    if (this.rX != null ? !this.rX.equals(localWirePayeeInfo.rX) : localWirePayeeInfo.rX != null) {
      return false;
    }
    return this.sh != null ? this.sh.equals(localWirePayeeInfo.sh) : localWirePayeeInfo.sh == null;
  }
  
  public int hashCode()
  {
    int i = this.sl != null ? this.sl.hashCode() : 0;
    i = 29 * i + (this.rW != null ? this.rW.hashCode() : 0);
    i = 29 * i + (this.r4 != null ? this.r4.hashCode() : 0);
    i = 29 * i + (this.r8 != null ? this.r8.hashCode() : 0);
    i = 29 * i + (this.so != null ? this.so.hashCode() : 0);
    i = 29 * i + (this.sm != null ? this.sm.hashCode() : 0);
    i = 29 * i + (this.rV != null ? this.rV.hashCode() : 0);
    i = 29 * i + (this.sj != null ? this.sj.hashCode() : 0);
    i = 29 * i + (this.r7 != null ? this.r7.hashCode() : 0);
    i = 29 * i + (this.r2 != null ? this.r2.hashCode() : 0);
    i = 29 * i + (this.se != null ? this.se.hashCode() : 0);
    i = 29 * i + (this.r0 != null ? this.r0.hashCode() : 0);
    i = 29 * i + (this.r5 != null ? this.r5.hashCode() : 0);
    i = 29 * i + (this.sg != null ? this.sg.hashCode() : 0);
    i = 29 * i + (this.sf != null ? this.sf.hashCode() : 0);
    i = 29 * i + (this.sd != null ? this.sd.hashCode() : 0);
    i = 29 * i + (this.sa != null ? this.sa.hashCode() : 0);
    i = 29 * i + (this.rY != null ? this.rY.hashCode() : 0);
    i = 29 * i + (this.rX != null ? this.rX.hashCode() : 0);
    i = 29 * i + (this.rZ != null ? this.rZ.hashCode() : 0);
    i = 29 * i + (this.r1 != null ? this.r1.hashCode() : 0);
    i = 29 * i + (this.sh != null ? this.sh.hashCode() : 0);
    i = 29 * i + (this.rT != null ? this.rT.hashCode() : 0);
    i = 29 * i + (this.rU != null ? this.rU.hashCode() : 0);
    i = 29 * i + (this.r3 != null ? this.r3.hashCode() : 0);
    return i;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.WirePayeeInfo
 * JD-Core Version:    0.7.0.1
 */