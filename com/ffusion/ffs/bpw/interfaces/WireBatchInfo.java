package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.util.FFSUtil;
import java.util.Hashtable;

public class WireBatchInfo
  extends BPWInfoBase
{
  protected String mw;
  protected String mF;
  protected String mE;
  protected String mx;
  protected String mB;
  protected String mq;
  protected String ms;
  protected String mJ;
  protected String mU;
  protected String my;
  protected WireInfo[] mu;
  protected String mP;
  protected String mz = null;
  protected String mS;
  protected String mK;
  protected String mA;
  protected String mM;
  protected String mn;
  protected String mX;
  protected int mr;
  protected boolean mL;
  protected String mV;
  protected String mW;
  protected String mC;
  protected Hashtable mv;
  protected String mR = null;
  protected String mp;
  protected String mZ;
  protected int mY;
  protected int mo;
  protected long mt = 0L;
  protected String m0;
  protected String mD;
  protected String mG;
  protected String mQ;
  protected String mI;
  protected String mT;
  protected String mm;
  protected String mN;
  protected String mO;
  protected String mH;
  
  public String getBatchId()
  {
    return this.mw;
  }
  
  public void setBatchId(String paramString)
  {
    this.mw = paramString;
  }
  
  public String getFIId()
  {
    return this.mF;
  }
  
  public void setFIId(String paramString)
  {
    this.mF = paramString;
  }
  
  public String getCustomerId()
  {
    return this.mE;
  }
  
  public void setCustomerId(String paramString)
  {
    this.mE = paramString;
  }
  
  public String getUserId()
  {
    return this.mx;
  }
  
  public void setUserId(String paramString)
  {
    this.mx = paramString;
  }
  
  public String getBatchName()
  {
    return this.mB;
  }
  
  public void setBatchName(String paramString)
  {
    this.mB = paramString;
  }
  
  public String getBatchType()
  {
    return this.mq;
  }
  
  public void setBatchType(String paramString)
  {
    this.mq = paramString;
  }
  
  public String getBatchCategory()
  {
    return this.ms;
  }
  
  public void setBatchCategory(String paramString)
  {
    this.ms = paramString;
  }
  
  public String getTotalAmount()
  {
    return this.mJ;
  }
  
  public void setTotalAmount(String paramString)
  {
    this.mJ = paramString;
  }
  
  public String getWireCount()
  {
    return this.mU;
  }
  
  public void setWireCount(String paramString)
  {
    this.mU = paramString;
  }
  
  public String getMemo()
  {
    return this.my;
  }
  
  public void setMemo(String paramString)
  {
    this.my = paramString;
  }
  
  public WireInfo[] getWires()
  {
    return this.mu;
  }
  
  public WireInfo getWiresAt(int paramInt)
  {
    if ((this.mu == null) || (paramInt < 0) || (paramInt >= this.mu.length)) {
      return null;
    }
    return this.mu[paramInt];
  }
  
  public void setWires(WireInfo[] paramArrayOfWireInfo)
  {
    this.mu = paramArrayOfWireInfo;
  }
  
  public String getBatchDest()
  {
    return this.mP;
  }
  
  public void setBatchDest(String paramString)
  {
    this.mP = paramString;
  }
  
  public String getExtId()
  {
    return this.mz;
  }
  
  public void setExtId(String paramString)
  {
    this.mz = paramString;
  }
  
  public String getConfirmNum2()
  {
    return this.mS;
  }
  
  public void setConfirmNum2(String paramString)
  {
    this.mS = paramString;
  }
  
  public String getDbTransKey()
  {
    return this.mK;
  }
  
  public void setDbTransKey(String paramString)
  {
    this.mK = paramString;
  }
  
  public String getEventId()
  {
    return this.mA;
  }
  
  public void setEventId(String paramString)
  {
    this.mA = paramString;
  }
  
  public String getDateToPost()
  {
    return this.mM;
  }
  
  public void setDateToPost(String paramString)
  {
    this.mM = paramString;
  }
  
  public String getConfirmNum()
  {
    return this.mn;
  }
  
  public void setConfirmNum(String paramString)
  {
    this.mn = paramString;
  }
  
  public String getConfirmMsg()
  {
    return this.mX;
  }
  
  public void setConfirmMsg(String paramString)
  {
    this.mX = paramString;
  }
  
  public boolean getPossibleDuplicate()
  {
    return this.mL;
  }
  
  public void setPossibleDuplicate(boolean paramBoolean)
  {
    this.mL = paramBoolean;
  }
  
  public int getEventSequence()
  {
    return this.mr;
  }
  
  public void setEventSequence(int paramInt)
  {
    this.mr = paramInt;
  }
  
  public String getDateDue()
  {
    return this.mV;
  }
  
  public void setDateDue(String paramString)
  {
    this.mV = paramString;
  }
  
  public String getPayInstruct()
  {
    return this.mW;
  }
  
  public void setPayInstruct(String paramString)
  {
    this.mW = paramString;
  }
  
  public String getPrcStatus()
  {
    return this.mZ;
  }
  
  public void setPrcStatus(String paramString)
  {
    this.mZ = paramString;
  }
  
  public int getPrcCode()
  {
    return this.mY;
  }
  
  public void setPrcCode(int paramInt)
  {
    this.mY = paramInt;
  }
  
  public int getCode()
  {
    return this.mo;
  }
  
  public void setCode(int paramInt)
  {
    this.mo = paramInt;
  }
  
  public String getTrnId()
  {
    return this.mC;
  }
  
  public void setTrnId(String paramString)
  {
    this.mC = paramString;
  }
  
  public String getMathRule()
  {
    return this.m0;
  }
  
  public void setMathRule(String paramString)
  {
    this.m0 = paramString;
  }
  
  public String getContractNumber()
  {
    return this.mD;
  }
  
  public void setContractNumber(String paramString)
  {
    this.mD = paramString;
  }
  
  public String getExchangeRate()
  {
    return this.mG;
  }
  
  public void setExchangeRate(String paramString)
  {
    this.mG = paramString;
  }
  
  public String getAmtCurrency()
  {
    return this.mQ;
  }
  
  public void setAmtCurrency(String paramString)
  {
    this.mQ = paramString;
  }
  
  public String getDestCurrency()
  {
    return this.mI;
  }
  
  public void setDestCurrency(String paramString)
  {
    this.mI = paramString;
  }
  
  public String getSettlementDate()
  {
    return this.mT;
  }
  
  public void setSettlementDate(String paramString)
  {
    this.mT = paramString;
  }
  
  public String getTotalCreditAmount()
  {
    return this.mm;
  }
  
  public void setTotalCreditAmount(String paramString)
  {
    this.mm = paramString;
  }
  
  public String getTotalDebitAmount()
  {
    return this.mN;
  }
  
  public void setTotalDebitAmount(String paramString)
  {
    this.mN = paramString;
  }
  
  public long getRecordCursor()
  {
    return this.mt;
  }
  
  public String getRecordCursorStr()
  {
    return new Long(this.mt).toString();
  }
  
  public void setRecordCursor(long paramLong)
  {
    this.mt = paramLong;
  }
  
  public String getDatePosted()
  {
    return this.mp;
  }
  
  public void setDatePosted(String paramString)
  {
    this.mp = paramString;
  }
  
  public Hashtable getExtInfo()
  {
    return this.mv;
  }
  
  public void setExtInfo(Hashtable paramHashtable)
  {
    this.mv = paramHashtable;
  }
  
  public String getRecWireBatchId()
  {
    return this.mR;
  }
  
  public void setRecWireBatchId(String paramString)
  {
    this.mR = paramString;
  }
  
  public void setOrigAmount(String paramString)
  {
    this.mO = paramString;
  }
  
  public String getOrigAmount()
  {
    return this.mO;
  }
  
  public void setOrigCurrency(String paramString)
  {
    this.mH = paramString;
  }
  
  public String getOrigCurrency()
  {
    return this.mH;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.mw != null) {
      localStringBuffer.append("batchId=").append(this.mw).append(", ");
    } else {
      localStringBuffer.append("batchId=").append(", ");
    }
    if (this.mF != null) {
      localStringBuffer.append("fIId=").append(this.mF).append(", ");
    } else {
      localStringBuffer.append("fIId=").append(", ");
    }
    if (this.mE != null) {
      localStringBuffer.append("customerId=").append(this.mE).append(", ");
    } else {
      localStringBuffer.append("customerId=").append(", ");
    }
    if (this.mx != null) {
      localStringBuffer.append("userId=").append(this.mx).append(", ");
    } else {
      localStringBuffer.append("userId=").append(", ");
    }
    if (this.mB != null) {
      localStringBuffer.append("batchName=").append(this.mB).append(", ");
    } else {
      localStringBuffer.append("batchName=").append(", ");
    }
    if (this.mq != null) {
      localStringBuffer.append("batchType=").append(this.mq).append(", ");
    } else {
      localStringBuffer.append("batchType=").append(", ");
    }
    if (this.ms != null) {
      localStringBuffer.append("batchCategory=").append(this.ms).append(", ");
    } else {
      localStringBuffer.append("batchCategory=").append(", ");
    }
    if (this.mJ != null) {
      localStringBuffer.append("totalAmount=").append(this.mJ).append(", ");
    } else {
      localStringBuffer.append("totalAmount=").append(", ");
    }
    if (this.mU != null) {
      localStringBuffer.append("wireCount=").append(this.mU).append(", ");
    } else {
      localStringBuffer.append("wireCount=").append(", ");
    }
    if (this.submittedBy != null) {
      localStringBuffer.append("submittedBy=").append(this.submittedBy).append(", ");
    } else {
      localStringBuffer.append("submittedBy=").append(", ");
    }
    if (this.submitDate != null) {
      localStringBuffer.append("submitDate=").append(this.submitDate).append(", ");
    } else {
      localStringBuffer.append("submitDate=").append(", ");
    }
    if (this.mv != null) {
      localStringBuffer.append("extInfo=").append(FFSUtil.hashtableToString(this.mv)).append(", ");
    } else {
      localStringBuffer.append("extInfo=").append(", ");
    }
    if (this.my != null) {
      localStringBuffer.append("memo=").append(this.my).append(", ");
    } else {
      localStringBuffer.append("memo=").append(", ");
    }
    if (this.logId != null) {
      localStringBuffer.append("logId=").append(this.logId).append(", ");
    } else {
      localStringBuffer.append("logId=").append(", ");
    }
    if (this.mn != null) {
      localStringBuffer.append("confirmNum=").append(this.mn).append(", ");
    } else {
      localStringBuffer.append("confirmNum=").append(", ");
    }
    if (this.mS != null) {
      localStringBuffer.append("confirmNum2=").append(this.mS).append(", ");
    } else {
      localStringBuffer.append("confirmNum2=").append(", ");
    }
    if (this.mX != null) {
      localStringBuffer.append("confirmMsg=").append(this.mX).append(", ");
    } else {
      localStringBuffer.append("confirmMsg=").append(", ");
    }
    if (this.mM != null) {
      localStringBuffer.append("dateToPost=").append(this.mM).append(", ");
    } else {
      localStringBuffer.append("dateToPost=").append(", ");
    }
    if (this.mW != null) {
      localStringBuffer.append("payInstruct=").append(this.mW).append(", ");
    } else {
      localStringBuffer.append("payInstruct=").append(", ");
    }
    if (this.mR != null) {
      localStringBuffer.append("recWireBatchId=").append(this.mR).append(", ");
    } else {
      localStringBuffer.append("recWireBatchId=").append(", ");
    }
    if (this.mp != null) {
      localStringBuffer.append("datePosted=").append(this.mp).append(", ");
    } else {
      localStringBuffer.append("datePosted=").append(", ");
    }
    if (this.mZ != null) {
      localStringBuffer.append("prcStatus=").append(this.mZ).append(", ");
    } else {
      localStringBuffer.append("prcStatus=").append(", ");
    }
    localStringBuffer.append("prcCode=").append(this.mY).append(", ");
    localStringBuffer.append("code=").append(this.mo).append(", ");
    if (this.m0 != null) {
      localStringBuffer.append("mathRule=").append(this.m0).append(", ");
    } else {
      localStringBuffer.append("mathRule=").append(", ");
    }
    if (this.mD != null) {
      localStringBuffer.append("contractNumber=").append(this.mD).append(", ");
    } else {
      localStringBuffer.append("contractNumber=").append(", ");
    }
    if (this.mG != null) {
      localStringBuffer.append("exchangeRate=").append(this.mG).append(", ");
    } else {
      localStringBuffer.append("exchangeRate=").append(", ");
    }
    if (this.mQ != null) {
      localStringBuffer.append("amtCurrency=").append(this.mQ).append(", ");
    } else {
      localStringBuffer.append("amtCurrency=").append(", ");
    }
    if (this.mI != null) {
      localStringBuffer.append("destCurrency=").append(this.mI).append(", ");
    } else {
      localStringBuffer.append("destCurrency=").append(", ");
    }
    if (this.mT != null) {
      localStringBuffer.append("settlementDate=").append(this.mT).append(", ");
    } else {
      localStringBuffer.append("settlementDate=").append(", ");
    }
    if (this.mm != null) {
      localStringBuffer.append("totalCreditAmount=").append(this.mm).append(", ");
    } else {
      localStringBuffer.append("totalCreditAmount=").append(", ");
    }
    if (this.mN != null) {
      localStringBuffer.append("totalDebitAmount=").append(this.mN).append(", ");
    } else {
      localStringBuffer.append("totalDebitAmount=").append(", ");
    }
    if (this.ld != null) {
      localStringBuffer.append("agentId=").append(this.ld).append(", ");
    } else {
      localStringBuffer.append("agentId=").append(", ");
    }
    if (this.le != null) {
      localStringBuffer.append("agentName=").append(this.le).append(", ");
    } else {
      localStringBuffer.append("agentName=").append(", ");
    }
    if (this.lc != null) {
      localStringBuffer.append("agentType=").append(this.lc).append(", ");
    } else {
      localStringBuffer.append("agentType=").append(", ");
    }
    if (this.mO != null) {
      localStringBuffer.append("origAmount=").append(this.mO).append(", ");
    } else {
      localStringBuffer.append("origAmount=").append(", ");
    }
    if (this.mH != null) {
      localStringBuffer.append("origCurrency=").append(this.mH).append(", ");
    } else {
      localStringBuffer.append("origCurrency=").append(", ");
    }
    if (this.mu != null) {
      for (int i = 0; i < this.mu.length; i++) {
        localStringBuffer.append("\nWire#" + i).append(this.mu[i].toString());
      }
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.WireBatchInfo
 * JD-Core Version:    0.7.0.1
 */