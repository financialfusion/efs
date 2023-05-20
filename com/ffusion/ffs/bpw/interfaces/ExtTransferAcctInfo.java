package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.util.FFSUtil;
import java.util.Hashtable;

public class ExtTransferAcctInfo
  extends BPWInfoBase
  implements Cloneable
{
  protected String nt;
  protected String nq;
  protected String nJ;
  protected String nD;
  protected String nv;
  protected String nx;
  protected String nn;
  protected String no;
  protected String nE;
  protected String nC;
  protected String nl;
  protected String nI;
  protected String nG;
  protected String nA;
  protected String nm;
  protected String np;
  protected String nw;
  protected String nH;
  protected String nk;
  protected String ny;
  protected String nz;
  protected String nF;
  protected int ni;
  protected int nu;
  protected String ns;
  protected String nr;
  protected String nj;
  protected String nB;
  
  public ExtTransferAcctInfo() {}
  
  public ExtTransferAcctInfo(String paramString1, String paramString2, String paramString3)
  {
    this.nx = paramString1;
    this.nD = paramString2;
    this.nv = paramString3;
  }
  
  public String getAcctId()
  {
    return this.nt;
  }
  
  public void setAcctId(String paramString)
  {
    this.nt = paramString;
  }
  
  public String getCustomerId()
  {
    return this.nq;
  }
  
  public void setCustomerId(String paramString)
  {
    this.nq = paramString;
  }
  
  public String getNickName()
  {
    return this.nJ;
  }
  
  public void setNickName(String paramString)
  {
    this.nJ = paramString;
  }
  
  public String getAcctNum17()
  {
    String str = null;
    if ((this.nD != null) && (this.nD.length() > 17)) {
      str = this.nD.substring(0, 17);
    } else {
      str = this.nD;
    }
    return str;
  }
  
  public String getAcctNum()
  {
    return this.nD;
  }
  
  public void setAcctNum(String paramString)
  {
    this.nD = paramString;
  }
  
  public String getAcctType()
  {
    return this.nv;
  }
  
  public void setAcctType(String paramString)
  {
    this.nv = paramString;
  }
  
  public String buildAcctId()
    throws Exception
  {
    return BPWUtil.getAccountIDWithAccountType(this.nD, this.nv);
  }
  
  public String getAcctBankRtn()
  {
    return this.nx;
  }
  
  public void setAcctBankRtn(String paramString)
  {
    this.nx = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.nn;
  }
  
  public void setCurrencyCode(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 3)) {
      throw new IllegalArgumentException("Currency code '" + paramString + "' has too many characters.");
    }
    this.nn = paramString;
  }
  
  public String getAcctScope()
  {
    return this.no;
  }
  
  public void setAcctScope(String paramString)
  {
    this.no = paramString;
  }
  
  public String getAcctDest()
  {
    return this.nE;
  }
  
  public void setAcctDest(String paramString)
  {
    this.nE = paramString;
  }
  
  public String getAcctCategory()
  {
    return this.nC;
  }
  
  public void setAcctCategory(String paramString)
  {
    this.nC = paramString;
  }
  
  public String getBankRtnType()
  {
    return this.nl;
  }
  
  public void setBankRtnType(String paramString)
  {
    this.nl = paramString;
  }
  
  public String getRecipientId()
  {
    return this.nI;
  }
  
  public void setRecipientId(String paramString)
  {
    this.nI = paramString;
  }
  
  public String getRecipientType()
  {
    return this.nG;
  }
  
  public void setRecipientType(String paramString)
  {
    this.nG = paramString;
  }
  
  public String getRecipientName()
  {
    return this.nA;
  }
  
  public void setRecipientName(String paramString)
  {
    this.nA = paramString;
  }
  
  public String getPrenote()
  {
    return this.nm;
  }
  
  public void setPrenote(String paramString)
  {
    this.nm = paramString;
  }
  
  public String getPrenoteStatus()
  {
    return this.np;
  }
  
  public void setPrenoteStatus(String paramString)
  {
    this.np = paramString;
  }
  
  public String getPrenoteSubDate()
  {
    return this.nw;
  }
  
  public void setPrenoteSubDate(String paramString)
  {
    this.nw = paramString;
  }
  
  public String getPrenoteType()
  {
    return this.nH;
  }
  
  public void setPrenoteType(String paramString)
  {
    this.nH = paramString;
  }
  
  public String getCreateDate()
  {
    return this.nk;
  }
  
  public void setCreateDate(String paramString)
  {
    this.nk = paramString;
  }
  
  public String getStatus()
  {
    return this.ny;
  }
  
  public void setStatus(String paramString)
  {
    this.ny = paramString;
  }
  
  public String getAction()
  {
    return this.nz;
  }
  
  public void setAction(String paramString)
  {
    this.nz = paramString;
  }
  
  public int getVerifyStatus()
  {
    return this.ni;
  }
  
  public void setVerifyStatus(int paramInt)
  {
    this.ni = paramInt;
  }
  
  public int getVerifyFailedCount()
  {
    return this.nu;
  }
  
  public void setVerifyFailedCount(int paramInt)
  {
    this.nu = paramInt;
  }
  
  public String getAgreedToTerms()
  {
    return this.nF;
  }
  
  public void setAgreedToTerms(String paramString)
  {
    this.nF = paramString;
  }
  
  public String getDepositDate()
  {
    if (this.ns == null) {
      return "0";
    }
    return this.ns;
  }
  
  public void setDepositDate(String paramString)
  {
    if (paramString == null) {
      paramString = "0";
    }
    this.ns = paramString;
  }
  
  public void setDepositDate(int paramInt)
  {
    this.ns = Integer.toString(paramInt);
  }
  
  public String getPrimaryAcctHolder()
  {
    return this.nr;
  }
  
  public void setPrimaryAcctHolder(String paramString)
  {
    this.nr = paramString;
  }
  
  public String getSecondaryAcctHolder()
  {
    return this.nj;
  }
  
  public void setSecondaryAcctHolder(String paramString)
  {
    this.nj = paramString;
  }
  
  public String getCheckNumber()
  {
    return this.nB;
  }
  
  public void setCheckNumber(String paramString)
  {
    this.nB = paramString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("acctId=").append(this.nt).append(", ");
    if (this.nq != null) {
      localStringBuffer.append("customerId=").append(this.nq).append(", ");
    } else {
      localStringBuffer.append("customerId=, ");
    }
    if (this.nJ != null) {
      localStringBuffer.append("nickName=").append(this.nJ).append(", ");
    } else {
      localStringBuffer.append("nickName=, ");
    }
    if (this.nD != null) {
      localStringBuffer.append("acctNum=").append(this.nD).append(", ");
    } else {
      localStringBuffer.append("acctNum=, ");
    }
    if (this.nv != null) {
      localStringBuffer.append("acctType=").append(this.nv).append(", ");
    } else {
      localStringBuffer.append("acctType=, ");
    }
    if (this.nx != null) {
      localStringBuffer.append("acctBankRtn=").append(this.nx).append(", ");
    } else {
      localStringBuffer.append("acctBankRtn=, ");
    }
    if (this.no != null) {
      localStringBuffer.append("acctScope=").append(this.no).append(", ");
    } else {
      localStringBuffer.append("acctScope=, ");
    }
    if (this.nE != null) {
      localStringBuffer.append("acctDest=").append(this.nE).append(", ");
    } else {
      localStringBuffer.append("acctDest=, ");
    }
    if (this.nC != null) {
      localStringBuffer.append("acctCategory=").append(this.nC).append(", ");
    } else {
      localStringBuffer.append("acctCategory=, ");
    }
    if (this.nl != null) {
      localStringBuffer.append("bankRtnType=").append(this.nl).append(", ");
    } else {
      localStringBuffer.append("bankRtnType=, ");
    }
    if (this.nI != null) {
      localStringBuffer.append("recipientId=").append(this.nI).append(", ");
    } else {
      localStringBuffer.append("recipientId=, ");
    }
    if (this.nG != null) {
      localStringBuffer.append("recipientType=").append(this.nG).append(", ");
    } else {
      localStringBuffer.append("recipientType=, ");
    }
    if (this.nA != null) {
      localStringBuffer.append("recipientName=").append(this.nA).append(", ");
    } else {
      localStringBuffer.append("recipientName=, ");
    }
    if (this.nm != null) {
      localStringBuffer.append("prenote=").append(this.nm).append(", ");
    } else {
      localStringBuffer.append("prenote=, ");
    }
    if (this.nw != null) {
      localStringBuffer.append("prenoteSubDate=").append(this.nw).append(", ");
    } else {
      localStringBuffer.append("prenoteSubDate=, ");
    }
    if (this.submittedBy != null) {
      localStringBuffer.append("submittedBy=").append(this.submittedBy).append(", ");
    } else {
      localStringBuffer.append("submittedBy=, ");
    }
    if (this.nk != null) {
      localStringBuffer.append("createDate=").append(this.nk).append(", ");
    } else {
      localStringBuffer.append("createDate=, ");
    }
    if (this.logId != null) {
      localStringBuffer.append("logId=").append(this.logId).append(", ");
    } else {
      localStringBuffer.append("logId=, ");
    }
    if (this.ny != null) {
      localStringBuffer.append("status=").append(this.ny).append(", ");
    } else {
      localStringBuffer.append("status=, ");
    }
    if (this.nz != null) {
      localStringBuffer.append("action=").append(this.nz).append(", ");
    } else {
      localStringBuffer.append("action=, ");
    }
    if (this.nF != null) {
      localStringBuffer.append("agreedToTerms=").append(this.nF).append(", ");
    } else {
      localStringBuffer.append("agreedToTerms=, ");
    }
    if (this.ns != null) {
      localStringBuffer.append("depositDate=").append(this.ns).append(", ");
    } else {
      localStringBuffer.append("depositDate=, ");
    }
    if (this.nr != null) {
      localStringBuffer.append("primaryAcctHolder=").append(this.nr).append(", ");
    } else {
      localStringBuffer.append("primaryAcctHolder=, ");
    }
    if (this.nj != null) {
      localStringBuffer.append("secondaryAcctHolder=").append(this.nj).append(", ");
    } else {
      localStringBuffer.append("secondaryAcctHolder=, ");
    }
    if (this.nB != null) {
      localStringBuffer.append("checkNumber=").append(this.nB).append(", ");
    } else {
      localStringBuffer.append("checkNumber=, ");
    }
    localStringBuffer.append("verifyStatus=").append(this.ni + ", ");
    localStringBuffer.append("verifyFailedCount=").append(this.nu + ", ");
    return localStringBuffer.toString();
  }
  
  public ExtTransferAcctInfo getExtTransferAcctInfo(String paramString)
  {
    Hashtable localHashtable = FFSUtil.stringToHashtable(paramString);
    return getExtTransferAcctInfo(localHashtable);
  }
  
  public ExtTransferAcctInfo getExtTransferAcctInfo(Hashtable paramHashtable)
  {
    if (paramHashtable == null) {
      return this;
    }
    this.nt = ((String)paramHashtable.remove("acctId"));
    this.nq = ((String)paramHashtable.remove("customerId"));
    this.nJ = ((String)paramHashtable.remove("nickName"));
    this.nD = ((String)paramHashtable.remove("acctNum"));
    this.nv = ((String)paramHashtable.remove("acctType"));
    this.nx = ((String)paramHashtable.remove("acctBankRtn"));
    this.no = ((String)paramHashtable.remove("acctScope"));
    this.nE = ((String)paramHashtable.remove("acctDest"));
    this.nC = ((String)paramHashtable.remove("acctCategory"));
    this.nl = ((String)paramHashtable.remove("bankRtnType"));
    this.nI = ((String)paramHashtable.remove("recipientId"));
    this.nG = ((String)paramHashtable.remove("recipientType"));
    this.nA = ((String)paramHashtable.remove("recipientName"));
    this.nm = ((String)paramHashtable.remove("prenote"));
    this.nw = ((String)paramHashtable.remove("prenoteSubDate"));
    this.submittedBy = ((String)paramHashtable.remove("submittedBy"));
    this.nk = ((String)paramHashtable.remove("createDate"));
    this.logId = ((String)paramHashtable.remove("logId"));
    this.ny = ((String)paramHashtable.remove("status"));
    this.nz = ((String)paramHashtable.remove("action"));
    this.nF = ((String)paramHashtable.remove("agreedToTerms"));
    this.ns = ((String)paramHashtable.remove("depositDate"));
    this.nr = ((String)paramHashtable.remove("primaryAcctHolder"));
    this.nj = ((String)paramHashtable.remove("secondaryAcctHolder"));
    this.nB = ((String)paramHashtable.remove("checkNumber"));
    String str = (String)paramHashtable.remove("verifyStatus");
    if (str != null) {
      this.ni = Integer.parseInt(str);
    }
    str = (String)paramHashtable.remove("verifyFailedCount");
    if (str != null) {
      this.nu = Integer.parseInt(str);
    }
    return this;
  }
  
  public Object clone()
  {
    try
    {
      return super.clone();
    }
    catch (Exception localException) {}
    return null;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo
 * JD-Core Version:    0.7.0.1
 */