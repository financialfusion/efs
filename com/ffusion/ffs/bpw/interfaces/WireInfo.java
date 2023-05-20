package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.util.FFSUtil;
import java.util.Enumeration;
import java.util.Hashtable;

public class WireInfo
  extends BPWInfoBase
  implements Cloneable
{
  protected String wx;
  protected String wv;
  protected String vG;
  protected String ws;
  protected String wA;
  protected String wF;
  protected String vO;
  protected String v5;
  protected String wm;
  protected String vI;
  protected String wj;
  protected String v3;
  protected String wl;
  protected String vA;
  protected String wr;
  protected String wB;
  protected String v7;
  protected String wo;
  protected String vv;
  protected String vN;
  protected String vH;
  protected String ww;
  protected String vy;
  protected String vW;
  protected String wG;
  protected String vE;
  protected String wt;
  protected String vL;
  protected String wn;
  protected String vM;
  protected String vZ;
  protected String wi;
  protected String wh;
  protected String wH;
  protected String vu;
  protected String wp;
  protected String wE;
  protected String vR;
  protected String vr;
  protected String wf;
  protected String wD;
  protected String vt;
  protected String wy;
  protected String vF;
  protected String vX;
  protected int vY;
  protected boolean vs;
  protected String v0;
  protected String vU;
  protected String vD;
  protected String wb;
  protected String vC;
  protected String wd;
  protected String v8;
  protected String v6;
  protected String wu;
  protected CustomerInfo customerInfo;
  protected BPWFIInfo v9;
  protected Hashtable vz;
  protected String vK = null;
  protected WirePayeeInfo wC = null;
  protected String wz = null;
  protected WirePayeeInfo we = null;
  protected String vB = null;
  protected String v2 = null;
  protected String wa;
  protected String v4;
  protected int wq;
  protected int v1;
  protected long vw = 0L;
  protected String vx;
  protected String wk;
  protected String vJ;
  protected String vV;
  protected String vT;
  protected String wc;
  protected String vS;
  protected String vQ;
  protected String vP;
  protected String wg;
  
  public String getHostId()
  {
    return this.vV;
  }
  
  public void setHostId(String paramString)
  {
    this.vV = paramString;
  }
  
  public String getUserId()
  {
    return this.vT;
  }
  
  public void setUserId(String paramString)
  {
    this.vT = paramString;
  }
  
  public String getExtBackendTid()
  {
    return this.vL;
  }
  
  public void setExtBackendTid(String paramString)
  {
    this.vL = paramString;
  }
  
  public String getTemplateId()
  {
    return this.wn;
  }
  
  public void setTemplateId(String paramString)
  {
    this.wn = paramString;
  }
  
  public String getProcessedBy()
  {
    return this.vx;
  }
  
  public void setProcessedBy(String paramString)
  {
    this.vx = paramString;
  }
  
  public String getAction()
  {
    return this.wk;
  }
  
  public void setAction(String paramString)
  {
    this.wk = paramString;
  }
  
  public String getMethod()
  {
    return this.vJ;
  }
  
  public void setMethod(String paramString)
  {
    this.vJ = paramString;
  }
  
  public String getEventId()
  {
    return this.vG;
  }
  
  public void setEventId(String paramString)
  {
    this.vG = paramString;
  }
  
  public String getWireCategory()
  {
    return this.wp;
  }
  
  public void setWireCategory(String paramString)
  {
    this.wp = paramString;
  }
  
  public String getWireGroup()
  {
    return this.ws;
  }
  
  public void setWireGroup(String paramString)
  {
    this.ws = paramString;
  }
  
  public String getWireDest()
  {
    return this.wA;
  }
  
  public void setWireDest(String paramString)
  {
    this.wA = paramString;
  }
  
  public String getConfirmNum2()
  {
    return this.wx;
  }
  
  public void setConfirmNum2(String paramString)
  {
    this.wx = paramString;
  }
  
  public String getDestCurrency()
  {
    return this.vD;
  }
  
  public void setDestCurrency(String paramString)
  {
    this.vD = paramString;
  }
  
  public String getDbTransKey()
  {
    return this.wv;
  }
  
  public void setDbTransKey(String paramString)
  {
    this.wv = paramString;
  }
  
  public String getDateToPost()
  {
    return this.wF;
  }
  
  public void setDateToPost(String paramString)
  {
    this.wF = paramString;
  }
  
  public boolean getPossibleDuplicate()
  {
    return this.vs;
  }
  
  public void setPossibleDuplicate(boolean paramBoolean)
  {
    this.vs = paramBoolean;
  }
  
  public int getEventSequence()
  {
    return this.vY;
  }
  
  public void setEventSequence(int paramInt)
  {
    this.vY = paramInt;
  }
  
  public String getConfirmNum()
  {
    return this.vO;
  }
  
  public void setConfirmNum(String paramString)
  {
    this.vO = paramString;
  }
  
  public String getMemo()
  {
    return this.v5;
  }
  
  public void setMemo(String paramString)
  {
    this.v5 = paramString;
  }
  
  public String getAmtCurrency()
  {
    return this.wb;
  }
  
  public void setAmtCurrency(String paramString)
  {
    this.wb = paramString;
  }
  
  public String getConfirmMsg()
  {
    return this.wm;
  }
  
  public void setConfirmMsg(String paramString)
  {
    this.wm = paramString;
  }
  
  public String getWireFee()
  {
    return this.vI;
  }
  
  public void setWireFee(String paramString)
  {
    this.vI = paramString;
  }
  
  public String getCustomerRef()
  {
    return this.wj;
  }
  
  public void setCustomerRef(String paramString)
  {
    this.wj = paramString;
  }
  
  public String getOriginatorCharges()
  {
    return this.v3;
  }
  
  public void setOriginatorCharges(String paramString)
  {
    this.v3 = paramString;
  }
  
  public String getReceiverCharges()
  {
    return this.wl;
  }
  
  public void setReceiverCharges(String paramString)
  {
    this.wl = paramString;
  }
  
  public String getWireChargesDetails()
  {
    return this.vA;
  }
  
  public void setWireChargesDetails(String paramString)
  {
    this.vA = paramString;
  }
  
  public String getOrgChargesAccountNum()
  {
    return this.wr;
  }
  
  public void setOrgChargesAccountNum(String paramString)
  {
    this.wr = paramString;
  }
  
  public String getBenefChargesAccountNum()
  {
    return this.wB;
  }
  
  public void setBenefChargesAccountNum(String paramString)
  {
    this.wB = paramString;
  }
  
  public String getWireType()
  {
    return this.vN;
  }
  
  public void setWireType(String paramString)
  {
    this.vN = paramString;
  }
  
  public String getWireState()
  {
    return this.vH;
  }
  
  public void setWireState(String paramString)
  {
    this.vH = paramString;
  }
  
  public String getBatchId()
  {
    return this.ww;
  }
  
  public void setBatchId(String paramString)
  {
    this.ww = paramString;
  }
  
  public String getExtId()
  {
    return this.vy;
  }
  
  public void setExtId(String paramString)
  {
    this.vy = paramString;
  }
  
  public String getWireSource()
  {
    return this.vM;
  }
  
  public void setWireSource(String paramString)
  {
    this.vM = paramString;
  }
  
  public String getWireName()
  {
    return this.vZ;
  }
  
  public void setWireName(String paramString)
  {
    this.vZ = paramString;
  }
  
  public String getNickName()
  {
    return this.wi;
  }
  
  public void setNickName(String paramString)
  {
    this.wi = paramString;
  }
  
  public String getWireLimit()
  {
    return this.wh;
  }
  
  public void setWireLimit(String paramString)
  {
    this.wh = paramString;
  }
  
  public String getSettlementDate()
  {
    return this.wH;
  }
  
  public void setSettlementDate(String paramString)
  {
    this.wH = paramString;
  }
  
  public String getCreateDate()
  {
    return this.vu;
  }
  
  public void setCreateDate(String paramString)
  {
    this.vu = paramString;
  }
  
  public String getWireScope()
  {
    return this.wE;
  }
  
  public void setWireScope(String paramString)
  {
    this.wE = paramString;
  }
  
  public String getMathRule()
  {
    return this.v7;
  }
  
  public void setMathRule(String paramString)
  {
    this.v7 = paramString;
  }
  
  public String getOrigToBeneficiaryMemo()
  {
    return this.vR;
  }
  
  public void setOrigToBeneficiaryMemo(String paramString)
  {
    this.vR = paramString;
  }
  
  public String getOrigToBeneficiaryInfo()
  {
    return this.vr;
  }
  
  public void setOrigToBeneficiaryInfo(String paramString)
  {
    this.vr = paramString;
  }
  
  public String getBanktoBankInfo()
  {
    return this.wf;
  }
  
  public void setBanktoBankInfo(String paramString)
  {
    this.wf = paramString;
  }
  
  public String getContractNumber()
  {
    return this.wo;
  }
  
  public void setContractNumber(String paramString)
  {
    this.wo = paramString;
  }
  
  public String getFromBankId()
  {
    return this.wD;
  }
  
  public void setFromBankId(String paramString)
  {
    this.wD = paramString;
  }
  
  public String getFromBranchId()
  {
    return this.vt;
  }
  
  public void setFromBranchId(String paramString)
  {
    this.vt = paramString;
  }
  
  public String getFromAcctId()
  {
    return this.wy;
  }
  
  public void setFromAcctId(String paramString)
  {
    this.wy = paramString;
  }
  
  public String getFromAcctType()
  {
    return this.vF;
  }
  
  public void setFromAcctType(String paramString)
  {
    this.vF = paramString;
  }
  
  public String getFromAcctIdWithType()
  {
    return BPWUtil.getAccountIDWithAccountType(this.wy, this.vF);
  }
  
  public String buildFromAcctId()
    throws Exception
  {
    return BPWUtil.getAccountIDWithAccountType(this.wy, this.vF);
  }
  
  public String getFromAcctKey()
  {
    return this.vX;
  }
  
  public void setFromAcctKey(String paramString)
  {
    this.vX = paramString;
  }
  
  public String getAmount()
  {
    return this.v0;
  }
  
  public void setAmount(String paramString)
  {
    this.v0 = paramString;
  }
  
  public void setOrigAmount(String paramString)
  {
    this.vC = paramString;
  }
  
  public String getOrigAmount()
  {
    return this.vC;
  }
  
  public void setOrigCurrency(String paramString)
  {
    this.wd = paramString;
  }
  
  public String getOrigCurrency()
  {
    return this.wd;
  }
  
  public String getAmountType()
  {
    return this.vU;
  }
  
  public void setAmountType(String paramString)
  {
    this.vU = paramString;
  }
  
  public String getDateDue()
  {
    return this.v8;
  }
  
  public void setDateDue(String paramString)
  {
    this.v8 = paramString;
  }
  
  public String getPayInstruct()
  {
    return this.v6;
  }
  
  public void setPayInstruct(String paramString)
  {
    this.v6 = paramString;
  }
  
  public String getCustomerID()
  {
    return this.wu;
  }
  
  public void setCustomerID(String paramString)
  {
    this.wu = paramString;
  }
  
  public String getExchangeRate()
  {
    return this.vv;
  }
  
  public void setExchangeRate(String paramString)
  {
    this.vv = paramString;
  }
  
  public CustomerInfo getCustomerInfo()
  {
    return this.customerInfo;
  }
  
  public void setCustomerInfo(CustomerInfo paramCustomerInfo)
  {
    this.customerInfo = paramCustomerInfo;
  }
  
  public BPWFIInfo getFiInfo()
  {
    return this.v9;
  }
  
  public void setFiInfo(BPWFIInfo paramBPWFIInfo)
  {
    this.v9 = paramBPWFIInfo;
  }
  
  public String getFiID()
  {
    return this.vE;
  }
  
  public void setFiID(String paramString)
  {
    this.vE = paramString;
  }
  
  public String getWirePayeeId()
  {
    return this.vK;
  }
  
  public void setWirePayeeId(String paramString)
  {
    this.vK = paramString;
  }
  
  public WirePayeeInfo getWirePayeeInfo()
  {
    return this.wC;
  }
  
  public void setWirePayeeInfo(WirePayeeInfo paramWirePayeeInfo)
  {
    this.wC = paramWirePayeeInfo;
  }
  
  public WirePayeeInfo getWireCreditInfo()
  {
    return this.we;
  }
  
  public void setWireCreditInfo(WirePayeeInfo paramWirePayeeInfo)
  {
    this.we = paramWirePayeeInfo;
  }
  
  public String getWireCreditId()
  {
    return this.vB;
  }
  
  public void setWireCreditId(String paramString)
  {
    this.vB = paramString;
  }
  
  public String getPayeeAcct()
  {
    return this.wz;
  }
  
  public void setPayeeAcct(String paramString)
  {
    this.wz = paramString;
  }
  
  public String getSubmitedBy()
  {
    return this.submittedBy;
  }
  
  public void setSubmitedBy(String paramString)
  {
    this.submittedBy = paramString;
  }
  
  public Hashtable getExtInfo()
  {
    return this.vz;
  }
  
  public void setExtInfo(Hashtable paramHashtable)
  {
    this.vz = paramHashtable;
  }
  
  public String getRecSrvrTid()
  {
    return this.v2;
  }
  
  public void setRecSrvrTid(String paramString)
  {
    this.v2 = paramString;
  }
  
  public String getSrvrTid()
  {
    return this.wt;
  }
  
  public void setSrvrTid(String paramString)
  {
    this.wt = paramString;
  }
  
  public String getDatePosted()
  {
    return this.wa;
  }
  
  public void setDatePosted(String paramString)
  {
    this.wa = paramString;
  }
  
  public String getPrcStatus()
  {
    return this.v4;
  }
  
  public void setPrcStatus(String paramString)
  {
    this.v4 = paramString;
  }
  
  public int getPrcCode()
  {
    return this.wq;
  }
  
  public void setPrcCode(int paramInt)
  {
    this.wq = paramInt;
  }
  
  public int getCode()
  {
    return this.v1;
  }
  
  public void setCode(int paramInt)
  {
    this.v1 = paramInt;
  }
  
  public String getLogDate()
  {
    return this.wG;
  }
  
  public void setLogDate(String paramString)
  {
    this.wG = paramString;
  }
  
  public String getTrnId()
  {
    return this.vW;
  }
  
  public void setTrnId(String paramString)
  {
    this.vW = paramString;
  }
  
  public String getByOrderOfName()
  {
    return this.wc;
  }
  
  public void setByOrderOfName(String paramString)
  {
    this.wc = paramString;
  }
  
  public String getByOrderOfAddr1()
  {
    return this.vS;
  }
  
  public void setByOrderOfAddr1(String paramString)
  {
    this.vS = paramString;
  }
  
  public String getByOrderOfAddr2()
  {
    return this.vQ;
  }
  
  public void setByOrderOfAddr2(String paramString)
  {
    this.vQ = paramString;
  }
  
  public String getByOrderOfAddr3()
  {
    return this.vP;
  }
  
  public void setByOrderOfAddr3(String paramString)
  {
    this.vP = paramString;
  }
  
  public String getByOrderOfAcctNum()
  {
    return this.wg;
  }
  
  public void setByOrderOfAcctNum(String paramString)
  {
    this.wg = paramString;
  }
  
  public long getRecordCursor()
  {
    return this.vw;
  }
  
  public String getRecordCursorStr()
  {
    return new Long(this.vw).toString();
  }
  
  public void setRecordCursor(long paramLong)
  {
    this.vw = paramLong;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("srvrTid=").append(this.wt).append(", ");
    if (this.wD != null) {
      localStringBuffer.append("fromBankId=").append(this.wD).append(", ");
    } else {
      localStringBuffer.append("fromBankId=").append(", ");
    }
    if (this.vt != null) {
      localStringBuffer.append("fromBranchId=").append(this.vt).append(", ");
    } else {
      localStringBuffer.append("fromBranchId=").append(", ");
    }
    if (this.wy != null) {
      localStringBuffer.append("fromAcctId=").append(this.wy).append(", ");
    } else {
      localStringBuffer.append("fromAcctId=").append(", ");
    }
    if (this.vF != null) {
      localStringBuffer.append("fromAcctType=").append(this.vF).append(", ");
    } else {
      localStringBuffer.append("fromAcctType=").append(", ");
    }
    if (this.vX != null) {
      localStringBuffer.append("fromAcctKey=").append(this.vX).append(", ");
    } else {
      localStringBuffer.append("fromAcctKey=").append(", ");
    }
    if (this.v0 != null) {
      localStringBuffer.append("amount=").append(this.v0).append(", ");
    } else {
      localStringBuffer.append("amount=").append(", ");
    }
    if (this.v8 != null) {
      localStringBuffer.append("dateDue=").append(this.v8).append(", ");
    } else {
      localStringBuffer.append("dateDue=").append(", ");
    }
    if (this.v6 != null) {
      localStringBuffer.append("payInstruct=").append(this.v6).append(", ");
    } else {
      localStringBuffer.append("payInstruct=").append(", ");
    }
    if (this.wu != null) {
      localStringBuffer.append("customerID=").append(this.wu).append(", ");
    } else {
      localStringBuffer.append("customerID=").append(", ");
    }
    if (this.wF != null) {
      localStringBuffer.append("dateToPost=").append(this.wF).append(", ");
    } else {
      localStringBuffer.append("dateToPost=").append(", ");
    }
    if (this.v5 != null) {
      localStringBuffer.append("memo=").append(this.v5).append(", ");
    } else {
      localStringBuffer.append("memo=").append(", ");
    }
    if (this.wb != null) {
      localStringBuffer.append("amtCurrency=").append(this.wb).append(", ");
    } else {
      localStringBuffer.append("amtCurrency=").append(", ");
    }
    if (this.vI != null) {
      localStringBuffer.append("wireFee=").append(this.vI).append(", ");
    } else {
      localStringBuffer.append("wireFee=").append(", ");
    }
    localStringBuffer.append("wireType=").append(this.vN).append(", ");
    if (this.wp != null) {
      localStringBuffer.append("wireCategory=").append(this.wp).append(", ");
    } else {
      localStringBuffer.append("wireCategory=").append(", ");
    }
    if (this.ws != null) {
      localStringBuffer.append("wireGroup=").append(this.ws).append(", ");
    } else {
      localStringBuffer.append("wireGroup=").append(", ");
    }
    localStringBuffer.append("wireDest=").append(this.wA).append(", ");
    if (this.vW != null) {
      localStringBuffer.append("trnId=").append(this.vW).append(", ");
    } else {
      localStringBuffer.append("trnId=").append(", ");
    }
    if (this.vE != null) {
      localStringBuffer.append("fiID=").append(this.vE).append(", ");
    } else {
      localStringBuffer.append("fiID=").append(", ");
    }
    if (this.submittedBy != null) {
      localStringBuffer.append("submittedBy=").append(this.submittedBy).append(", ");
    } else {
      localStringBuffer.append("submittedBy=").append(", ");
    }
    if (this.vz != null) {
      localStringBuffer.append("extInfo=").append(FFSUtil.hashtableToString(this.vz)).append(", ");
    } else {
      localStringBuffer.append("extInfo=").append(", ");
    }
    if (this.vK != null) {
      localStringBuffer.append("wirePayeeId=").append(this.vK).append(", ");
    } else {
      localStringBuffer.append("wirePayeeId=").append(", ");
    }
    if (this.wz != null) {
      localStringBuffer.append("payeeAcct=").append(this.wz).append(", ");
    } else {
      localStringBuffer.append("payeeAcct=").append(", ");
    }
    if (this.v2 != null) {
      localStringBuffer.append("recSrvrTid=").append(this.v2).append(", ");
    } else {
      localStringBuffer.append("recSrvrTid=").append(", ");
    }
    if (this.vO != null) {
      localStringBuffer.append("confirmNum=").append(this.vO).append(", ");
    } else {
      localStringBuffer.append("confirmNum=").append(", ");
    }
    if (this.wm != null) {
      localStringBuffer.append("confirmMsg=").append(this.wm).append(", ");
    } else {
      localStringBuffer.append("confirmMsg=").append(", ");
    }
    if (this.vD != null) {
      localStringBuffer.append("destCurrency=").append(this.vD).append(", ");
    } else {
      localStringBuffer.append("destCurrency=").append(", ");
    }
    if (this.wx != null) {
      localStringBuffer.append("confirmNum2=").append(this.wx).append(", ");
    } else {
      localStringBuffer.append("confirmNum2=").append(", ");
    }
    if (this.vv != null) {
      localStringBuffer.append("exchangeRate=").append(this.vv).append(", ");
    } else {
      localStringBuffer.append("exchangeRate=").append(", ");
    }
    if (this.logId != null) {
      localStringBuffer.append("logId=").append(this.logId).append(", ");
    } else {
      localStringBuffer.append("logId=").append(", ");
    }
    if (this.wa != null) {
      localStringBuffer.append("datePosted=").append(this.wa).append(", ");
    } else {
      localStringBuffer.append("datePosted=").append(", ");
    }
    if (this.v4 != null) {
      localStringBuffer.append("prcStatus=").append(this.v4).append(", ");
    } else {
      localStringBuffer.append("prcStatus=").append(", ");
    }
    localStringBuffer.append("prcCode=").append(this.wq).append(", ");
    localStringBuffer.append("code=").append(this.v1).append(", ");
    if (this.wo != null) {
      localStringBuffer.append("contractNumber=").append(this.wo).append(", ");
    } else {
      localStringBuffer.append("contractNumber=").append(", ");
    }
    if (this.ww != null) {
      localStringBuffer.append("batchId=").append(this.ww).append(", ");
    } else {
      localStringBuffer.append("batchId=").append(", ");
    }
    if (this.vy != null) {
      localStringBuffer.append("extId=").append(this.vy).append(", ");
    } else {
      localStringBuffer.append("extId=").append(", ");
    }
    if (this.vM != null) {
      localStringBuffer.append("wireSource=").append(this.vM).append(", ");
    } else {
      localStringBuffer.append("wireSource=").append(", ");
    }
    if (this.vZ != null) {
      localStringBuffer.append("wireName=").append(this.vZ).append(", ");
    } else {
      localStringBuffer.append("wireName=").append(", ");
    }
    if (this.wi != null) {
      localStringBuffer.append("nickName=").append(this.wi).append(", ");
    } else {
      localStringBuffer.append("nickName=").append(", ");
    }
    if (this.wh != null) {
      localStringBuffer.append("wireLimit=").append(this.wh).append(", ");
    } else {
      localStringBuffer.append("wireLimit=").append(", ");
    }
    if (this.wH != null) {
      localStringBuffer.append("settlementDate=").append(this.wH).append(", ");
    } else {
      localStringBuffer.append("settlementDate=").append(", ");
    }
    if (this.vu != null) {
      localStringBuffer.append("createDate=").append(this.vu).append(", ");
    } else {
      localStringBuffer.append("createDate=").append(", ");
    }
    if (this.wE != null) {
      localStringBuffer.append("wireScope=").append(this.wE).append(", ");
    } else {
      localStringBuffer.append("wireScope=").append(", ");
    }
    if (this.v7 != null) {
      localStringBuffer.append("mathRule=").append(this.v7).append(", ");
    } else {
      localStringBuffer.append("mathRule=").append(", ");
    }
    if (this.vR != null) {
      localStringBuffer.append("origToBeneficiaryMemo=").append(this.vR).append(", ");
    } else {
      localStringBuffer.append("origToBeneficiaryMemo=").append(", ");
    }
    if (this.vr != null) {
      localStringBuffer.append("origToBeneficiaryInfo=").append(this.vr).append(", ");
    } else {
      localStringBuffer.append("origToBeneficiaryInfo=").append(", ");
    }
    if (this.wf != null) {
      localStringBuffer.append("banktoBankInfo=").append(this.wf).append(", ");
    } else {
      localStringBuffer.append("banktoBankInfo=").append(", ");
    }
    if (this.vx != null) {
      localStringBuffer.append("processedBy=").append(this.vx).append(", ");
    } else {
      localStringBuffer.append("processedBy=").append(", ");
    }
    if (this.vL != null) {
      localStringBuffer.append("extBackendTid=").append(this.vL).append(", ");
    } else {
      localStringBuffer.append("extBackendTid=").append(", ");
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
    if (this.vJ != null) {
      localStringBuffer.append("method=").append(this.vJ).append(", ");
    } else {
      localStringBuffer.append("method=").append(", ");
    }
    if (this.wn != null) {
      localStringBuffer.append("templateId=").append(this.wn).append(", ");
    } else {
      localStringBuffer.append("templateId=").append(", ");
    }
    if (this.wj != null) {
      localStringBuffer.append("customerRef=").append(this.wj).append(", ");
    } else {
      localStringBuffer.append("customerRef=").append(", ");
    }
    if (this.v3 != null) {
      localStringBuffer.append("originatorCharges=").append(this.v3).append(", ");
    } else {
      localStringBuffer.append("originatorCharges=").append(", ");
    }
    if (this.wl != null) {
      localStringBuffer.append("receiverCharges=").append(this.wl).append(", ");
    } else {
      localStringBuffer.append("receiverCharges=").append(", ");
    }
    if (this.vA != null) {
      localStringBuffer.append("wireChargesDetails=").append(this.vA).append(", ");
    } else {
      localStringBuffer.append("wireChargesDetails=").append(", ");
    }
    if (this.wr != null) {
      localStringBuffer.append("orgChargesAccountNum=").append(this.wr).append(", ");
    } else {
      localStringBuffer.append("orgChargesAccountNum=").append(", ");
    }
    if (this.wB != null) {
      localStringBuffer.append("benefChargesAccountNum=").append(this.wB).append(", ");
    } else {
      localStringBuffer.append("benefChargesAccountNum=").append(", ");
    }
    if (this.vV != null) {
      localStringBuffer.append("hostId=").append(this.vV).append(", ");
    } else {
      localStringBuffer.append("hostId=").append(", ");
    }
    if (this.vT != null) {
      localStringBuffer.append("userId=").append(this.vT).append(", ");
    } else {
      localStringBuffer.append("userId=").append(", ");
    }
    if (this.lc != null) {
      localStringBuffer.append("agentType=").append(this.lc).append(", ");
    } else {
      localStringBuffer.append("agentType=").append(", ");
    }
    if (this.vC != null) {
      localStringBuffer.append("origAmount=").append(this.vC).append(", ");
    } else {
      localStringBuffer.append("origAmount=").append(", ");
    }
    if (this.wd != null) {
      localStringBuffer.append("origCurrency=").append(this.wd).append(", ");
    } else {
      localStringBuffer.append("origCurrency=").append(", ");
    }
    if (this.vB != null) {
      localStringBuffer.append("wireCreditId=").append(this.vB).append(", ");
    } else {
      localStringBuffer.append("wireCreditId=").append(", ");
    }
    if (this.wc != null) {
      localStringBuffer.append("byOrderOfName=").append(this.wc).append(", ");
    } else {
      localStringBuffer.append("byOrderOfName=").append(", ");
    }
    if (this.vS != null) {
      localStringBuffer.append("byOrderOfAddr1=").append(this.vS).append(", ");
    } else {
      localStringBuffer.append("byOrderOfAddr1=").append(", ");
    }
    if (this.vQ != null) {
      localStringBuffer.append("byOrderOfAddr2=").append(this.vQ).append(", ");
    } else {
      localStringBuffer.append("byOrderOfAddr2=").append(", ");
    }
    if (this.vP != null) {
      localStringBuffer.append("byOrderOfAddr3=").append(this.vP).append(", ");
    } else {
      localStringBuffer.append("byOrderOfAddr3=").append(", ");
    }
    if (this.wg != null) {
      localStringBuffer.append("byOrderOfAcctNum=").append(this.wg).append(", ");
    } else {
      localStringBuffer.append("byOrderOfAcctNum=").append(", ");
    }
    return localStringBuffer.toString();
  }
  
  public WireInfo getWireInfo(String paramString)
  {
    Hashtable localHashtable = FFSUtil.stringToHashtable(paramString);
    return getWireInfo(localHashtable);
  }
  
  public WireInfo getWireInfo(Hashtable paramHashtable)
  {
    if (paramHashtable == null) {
      return this;
    }
    this.wt = ((String)paramHashtable.remove("srvrTid"));
    this.wD = ((String)paramHashtable.remove("fromBankId"));
    this.vt = ((String)paramHashtable.remove("fromBranchId"));
    this.wy = ((String)paramHashtable.remove("fromAcctId"));
    this.vF = ((String)paramHashtable.remove("fromAcctType"));
    this.vX = ((String)paramHashtable.remove("fromAcctKey"));
    this.v0 = ((String)paramHashtable.remove("amount"));
    this.v8 = ((String)paramHashtable.remove("dateDue"));
    this.v6 = ((String)paramHashtable.remove("payInstruct"));
    this.wu = ((String)paramHashtable.remove("customerID"));
    this.wF = ((String)paramHashtable.remove("dateToPost"));
    this.v5 = ((String)paramHashtable.remove("memo"));
    this.wb = ((String)paramHashtable.remove("amtCurrency"));
    this.vI = ((String)paramHashtable.remove("wireFee"));
    this.vN = ((String)paramHashtable.remove("wireType"));
    this.wp = ((String)paramHashtable.remove("wireCategory"));
    this.ws = ((String)paramHashtable.remove("wireGroup"));
    this.wA = ((String)paramHashtable.remove("wireDest"));
    this.vW = ((String)paramHashtable.remove("trnId"));
    this.vE = ((String)paramHashtable.remove("fiID"));
    this.submittedBy = ((String)paramHashtable.remove("submittedBy"));
    this.vz = FFSUtil.stringToHashtable((String)paramHashtable.remove("extInfo"));
    this.vK = ((String)paramHashtable.remove("wirePayeeId"));
    this.wz = ((String)paramHashtable.remove("payeeAcct"));
    this.v2 = ((String)paramHashtable.remove("recSrvrTid"));
    this.vO = ((String)paramHashtable.remove("confirmNum"));
    this.wm = ((String)paramHashtable.remove("confirmMsg"));
    this.wx = ((String)paramHashtable.remove("confirmNum2"));
    this.vD = ((String)paramHashtable.remove("destCurrency"));
    this.vv = ((String)paramHashtable.remove("exchangeRate"));
    this.logId = ((String)paramHashtable.remove("logId"));
    this.wa = ((String)paramHashtable.remove("datePosted"));
    this.v4 = ((String)paramHashtable.remove("prcStatus"));
    this.wq = Integer.parseInt((String)paramHashtable.remove("prcCode"));
    this.v1 = Integer.parseInt((String)paramHashtable.remove("code"));
    this.ww = ((String)paramHashtable.remove("batchId"));
    this.vy = ((String)paramHashtable.remove("extId"));
    this.wo = ((String)paramHashtable.remove("contractNumber"));
    this.vM = ((String)paramHashtable.remove("wireSource"));
    this.vL = ((String)paramHashtable.remove("extBackendTid"));
    this.vZ = ((String)paramHashtable.remove("wireName"));
    this.wi = ((String)paramHashtable.remove("nickName"));
    this.wh = ((String)paramHashtable.remove("wireLimit"));
    this.wH = ((String)paramHashtable.remove("settlementDate"));
    this.vu = ((String)paramHashtable.remove("createDate"));
    this.wE = ((String)paramHashtable.remove("wireScope"));
    this.v7 = ((String)paramHashtable.remove("mathRule"));
    this.vR = ((String)paramHashtable.remove("origToBeneficiaryMemo"));
    this.vr = ((String)paramHashtable.remove("origToBeneficiaryInfo"));
    this.wf = ((String)paramHashtable.remove("banktoBankInfo"));
    this.vx = ((String)paramHashtable.remove("processedBy"));
    this.ld = ((String)paramHashtable.remove("agentId"));
    this.le = ((String)paramHashtable.remove("agentName"));
    this.vJ = ((String)paramHashtable.remove("method"));
    this.wn = ((String)paramHashtable.remove("templateId"));
    this.wj = ((String)paramHashtable.remove("customerRef"));
    this.v3 = ((String)paramHashtable.remove("originatorCharges"));
    this.wl = ((String)paramHashtable.remove("receiverCharges"));
    this.vA = ((String)paramHashtable.remove("wireChargesDetails"));
    this.wr = ((String)paramHashtable.remove("orgChargesAccountNum"));
    this.wB = ((String)paramHashtable.remove("benefChargesAccountNum"));
    this.vV = ((String)paramHashtable.remove("hostId"));
    this.vT = ((String)paramHashtable.remove("userId"));
    this.lc = ((String)paramHashtable.remove("agentType"));
    this.vC = ((String)paramHashtable.remove("origAmount"));
    this.wd = ((String)paramHashtable.remove("origCurrency"));
    this.vB = ((String)paramHashtable.remove("wireCreditId"));
    this.wc = ((String)paramHashtable.remove("byOrderOfName"));
    this.vS = ((String)paramHashtable.remove("byOrderOfAddr1"));
    this.vQ = ((String)paramHashtable.remove("byOrderOfAddr2"));
    this.vP = ((String)paramHashtable.remove("byOrderOfAddr3"));
    this.wg = ((String)paramHashtable.remove("byOrderOfAcctNum"));
    Enumeration localEnumeration = paramHashtable.keys();
    String str = null;
    while (localEnumeration.hasMoreElements())
    {
      str = (String)localEnumeration.nextElement();
      this.vz.put(str, paramHashtable.remove(str));
    }
    return this;
  }
  
  public Object clone()
  {
    WireInfo localWireInfo = new WireInfo();
    localWireInfo.wt = this.wt;
    localWireInfo.wD = this.wD;
    localWireInfo.vt = this.vt;
    localWireInfo.wy = this.wy;
    localWireInfo.vF = this.vF;
    localWireInfo.vX = this.vX;
    localWireInfo.v0 = this.v0;
    localWireInfo.v8 = this.v8;
    localWireInfo.v6 = this.v6;
    localWireInfo.wu = this.wu;
    localWireInfo.wF = this.wF;
    localWireInfo.v5 = this.v5;
    localWireInfo.wb = this.wb;
    localWireInfo.vI = this.vI;
    localWireInfo.vN = this.vN;
    localWireInfo.wp = this.wp;
    localWireInfo.ws = this.ws;
    localWireInfo.wA = this.wA;
    localWireInfo.vW = this.vW;
    localWireInfo.vE = this.vE;
    localWireInfo.submittedBy = this.submittedBy;
    localWireInfo.vz = this.vz;
    localWireInfo.vK = this.vK;
    localWireInfo.wz = this.wz;
    localWireInfo.v2 = this.v2;
    localWireInfo.vO = this.vO;
    localWireInfo.wm = this.wm;
    localWireInfo.wx = this.wx;
    localWireInfo.vD = this.vD;
    localWireInfo.vv = this.vv;
    localWireInfo.logId = this.logId;
    localWireInfo.wa = this.wa;
    localWireInfo.v4 = this.v4;
    localWireInfo.wq = this.wq;
    localWireInfo.v1 = this.v1;
    localWireInfo.ww = this.ww;
    localWireInfo.vy = this.vy;
    localWireInfo.wo = this.wo;
    localWireInfo.vM = this.vM;
    localWireInfo.vL = this.vL;
    localWireInfo.vZ = this.vZ;
    localWireInfo.wi = this.wi;
    localWireInfo.wh = this.wh;
    localWireInfo.wH = this.wH;
    localWireInfo.vu = this.vu;
    localWireInfo.wE = this.wE;
    localWireInfo.v7 = this.v7;
    localWireInfo.vR = this.vR;
    localWireInfo.vr = this.vr;
    localWireInfo.wf = this.wf;
    localWireInfo.vx = this.vx;
    localWireInfo.ld = this.ld;
    localWireInfo.le = this.le;
    localWireInfo.vJ = this.vJ;
    localWireInfo.wn = this.wn;
    localWireInfo.wj = this.wj;
    localWireInfo.v3 = this.v3;
    localWireInfo.wl = this.wl;
    localWireInfo.vA = this.vA;
    localWireInfo.wr = this.wr;
    localWireInfo.wB = this.wB;
    localWireInfo.vV = this.vV;
    localWireInfo.vT = this.vT;
    localWireInfo.lc = this.lc;
    localWireInfo.vC = this.vC;
    localWireInfo.wd = this.wd;
    localWireInfo.vB = this.vB;
    localWireInfo.wc = this.wc;
    localWireInfo.vS = this.vS;
    localWireInfo.vQ = this.vQ;
    localWireInfo.vP = this.vP;
    localWireInfo.wg = this.wg;
    return localWireInfo;
  }
  
  public boolean isTemplateWire()
  {
    return ("TEMPLATE".equalsIgnoreCase(getWireType())) || ("RECTEMPLATE".equalsIgnoreCase(getWireType()));
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.WireInfo
 * JD-Core Version:    0.7.0.1
 */