package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.enums.UserAssignedAmount;
import java.util.Enumeration;
import java.util.Hashtable;

public class TransferInfo
  extends BPWInfoBase
  implements Cloneable, TransferHistInfo
{
  protected String tq;
  protected String ud;
  protected String tX;
  protected String tO;
  protected String uk;
  protected String tE;
  protected String ub;
  protected String t2;
  protected String tr;
  protected String tN;
  protected String tJ;
  protected String tF;
  protected String tQ;
  protected ExtTransferAcctInfo tv;
  protected String ui;
  protected String ue;
  protected String tG;
  protected String uf;
  protected ExtTransferAcctInfo tz;
  protected String tC;
  protected String t4;
  protected String uj;
  protected String t6;
  protected UserAssignedAmount tV = UserAssignedAmount.SINGLE;
  protected boolean tU = false;
  protected String tH;
  protected String t7;
  protected String tY;
  protected String tB;
  protected String ug;
  protected String t5;
  protected String tW;
  protected String tA;
  protected String tp;
  protected String tM;
  protected String tu;
  protected String tK;
  protected String t1;
  protected String uc;
  protected String ul;
  protected String tx;
  protected String tw;
  protected String uh;
  protected String ty;
  protected String ua;
  protected int t9 = 0;
  protected int t3 = 0;
  protected boolean t0;
  protected long tD = 0L;
  protected String tT;
  protected Hashtable tI;
  protected String tZ;
  protected String tR;
  protected ExtTransferCompanyInfo tS;
  protected int tt = 1;
  protected String ts;
  protected int tP = 0;
  protected int t8 = 0;
  protected String tL;
  
  public TransferInfo() {}
  
  public TransferInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, null, "0.00", null, UserAssignedAmount.SINGLE, false, paramString11, paramString12, paramString13, paramString14, paramString15, paramString16, paramString17, paramString18);
  }
  
  public TransferInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, UserAssignedAmount paramUserAssignedAmount, boolean paramBoolean, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, String paramString19, String paramString20, String paramString21)
  {
    this.tq = paramString1;
    this.ud = paramString2;
    this.tX = paramString3;
    this.tO = paramString19;
    this.tr = paramString4;
    this.t2 = paramString5;
    this.tJ = paramString6;
    this.tF = paramString7;
    this.ue = paramString8;
    this.tG = paramString9;
    this.tC = paramString10;
    this.t4 = paramString11;
    this.uj = paramString12;
    this.t6 = paramString13;
    this.tV = paramUserAssignedAmount;
    this.tU = paramBoolean;
    this.ug = paramString14;
    this.t5 = paramString15;
    this.uc = paramString16;
    this.ul = paramString18;
    this.logId = paramString17;
    this.submittedBy = paramString20;
    this.uk = paramString21;
  }
  
  public void populate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18)
  {
    this.tq = paramString1;
    this.ud = paramString2;
    this.tX = paramString3;
    this.tO = paramString16;
    this.tr = paramString4;
    this.t2 = paramString5;
    this.tJ = paramString6;
    this.tF = paramString7;
    this.ue = paramString8;
    this.tG = paramString9;
    this.tC = paramString10;
    this.ug = paramString11;
    this.t5 = paramString12;
    this.uc = paramString13;
    this.ul = paramString15;
    this.logId = paramString14;
    this.submittedBy = paramString17;
    this.uk = paramString18;
  }
  
  public Hashtable getExtInfo()
  {
    return this.tI;
  }
  
  public void setExtInfo(Hashtable paramHashtable)
  {
    this.tI = paramHashtable;
  }
  
  public String getSrvrTId()
  {
    return this.tq;
  }
  
  public void setSrvrTId(String paramString)
  {
    this.tq = paramString;
  }
  
  public String getCustomerId()
  {
    return this.tX;
  }
  
  public void setCustomerId(String paramString)
  {
    this.tX = paramString;
  }
  
  public String getTransferType()
  {
    return this.tO;
  }
  
  public void setTransferType(String paramString)
  {
    this.tO = paramString;
  }
  
  public String getTransferDest()
  {
    return this.uk;
  }
  
  public void setTransferDest(String paramString)
  {
    this.uk = paramString;
  }
  
  public String getTransferGroup()
  {
    return this.tE;
  }
  
  public void setTransferGroup(String paramString)
  {
    this.tE = paramString;
  }
  
  public String getTransferCategory()
  {
    return this.ub;
  }
  
  public void setTransferCategory(String paramString)
  {
    this.ub = paramString;
  }
  
  public String getFIId()
  {
    return this.ud;
  }
  
  public void setFIId(String paramString)
  {
    this.ud = paramString;
  }
  
  public void setBranchID(String paramString)
  {
    this.t2 = paramString;
  }
  
  public String getBranchID()
  {
    return this.t2;
  }
  
  public String getBankFromRtn()
  {
    return this.tr;
  }
  
  public void setBankFromRtn(String paramString)
  {
    this.tr = paramString;
  }
  
  public String getBankFromRtnType()
  {
    return this.tN;
  }
  
  public void setBankFromRtnType(String paramString)
  {
    this.tN = paramString;
  }
  
  public String getAccountFromNum17()
  {
    String str = null;
    if ((this.tJ != null) && (this.tJ.length() > 17)) {
      str = this.tJ.substring(0, 17);
    } else {
      str = this.tJ;
    }
    return str;
  }
  
  public String getAccountFromNum()
  {
    return this.tJ;
  }
  
  public void setAccountFromNum(String paramString)
  {
    this.tJ = paramString;
  }
  
  public String getAccountFromType()
  {
    return this.tF;
  }
  
  public void setAccountFromType(String paramString)
  {
    this.tF = paramString;
  }
  
  public String getFromAcctIdWithType()
  {
    return BPWUtil.getAccountIDWithAccountType(this.tJ, this.tF);
  }
  
  public String buildFromAcctId()
    throws Exception
  {
    return BPWUtil.getAccountIDWithAccountType(this.tJ, this.tF);
  }
  
  public String getAccountFromId()
  {
    return this.tQ;
  }
  
  public void setAccountFromId(String paramString)
  {
    this.tQ = paramString;
  }
  
  public ExtTransferAcctInfo getAccountFromInfo()
  {
    return this.tv;
  }
  
  public void setAccountFromInfo(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    this.tv = paramExtTransferAcctInfo;
  }
  
  public String getBankToRtn()
  {
    return this.ui;
  }
  
  public void setBankToRtn(String paramString)
  {
    this.ui = paramString;
  }
  
  public String buildToAcctId()
    throws Exception
  {
    return BPWUtil.getAccountIDWithAccountType(this.ue, this.tG);
  }
  
  public String getExternalAcctId()
  {
    return this.uf;
  }
  
  public void setExternalAcctId(String paramString)
  {
    this.uf = paramString;
  }
  
  public String getAccountToNum()
  {
    return this.ue;
  }
  
  public void setAccountToNum(String paramString)
  {
    this.ue = paramString;
  }
  
  public String getAccountToType()
  {
    return this.tG;
  }
  
  public void setAccountToType(String paramString)
  {
    this.tG = paramString;
  }
  
  public String getAccountToId()
  {
    return this.uf;
  }
  
  public void setAccountToId(String paramString)
  {
    this.uf = paramString;
  }
  
  public ExtTransferAcctInfo getAccountToInfo()
  {
    return this.tz;
  }
  
  public void setAccountToInfo(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    this.tz = paramExtTransferAcctInfo;
  }
  
  public String getAmount()
  {
    return this.tC;
  }
  
  public void setAmount(String paramString)
  {
    this.tC = paramString;
  }
  
  public String getAmountCurrency()
  {
    return this.t4;
  }
  
  public void setAmountCurrency(String paramString)
  {
    this.t4 = paramString;
  }
  
  public String getOrigAmount()
  {
    return this.t7;
  }
  
  public void setOrigAmount(String paramString)
  {
    this.t7 = paramString;
  }
  
  public String getOrigCurrency()
  {
    return this.tY;
  }
  
  public void setOrigCurrency(String paramString)
  {
    this.tY = paramString;
  }
  
  public String getDateCreate()
  {
    return this.tB;
  }
  
  public void setDateCreate(String paramString)
  {
    this.tB = paramString;
  }
  
  public String getDateDue()
  {
    return this.ug;
  }
  
  public void setDateDue(String paramString)
  {
    this.ug = paramString;
  }
  
  public String getDateToPost()
  {
    return this.t5;
  }
  
  public void setDateToPost(String paramString)
  {
    this.t5 = paramString;
  }
  
  public String getProcessDate()
  {
    return this.tW;
  }
  
  public void setProcessDate(String paramString)
  {
    this.tW = paramString;
  }
  
  public String getDatePosted()
  {
    return this.tA;
  }
  
  public void setDatePosted(String paramString)
  {
    this.tA = paramString;
  }
  
  public String getLastProcessId()
  {
    return this.tp;
  }
  
  public void setLastProcessId(String paramString)
  {
    this.tp = paramString;
  }
  
  public String getMemo()
  {
    return this.tM;
  }
  
  public void setMemo(String paramString)
  {
    this.tM = paramString;
  }
  
  public String getTemplateScope()
  {
    return this.tu;
  }
  
  public void setTemplateScope(String paramString)
  {
    this.tu = paramString;
  }
  
  public String getTemplateNickName()
  {
    return this.tK;
  }
  
  public void setTemplateNickName(String paramString)
  {
    this.tK = paramString;
  }
  
  public String getSourceTemplateId()
  {
    return this.t1;
  }
  
  public void setSourceTemplateId(String paramString)
  {
    this.t1 = paramString;
  }
  
  public String getSourceRecSrvrTId()
  {
    return this.uc;
  }
  
  public void setSourceRecSrvrTId(String paramString)
  {
    this.uc = paramString;
  }
  
  public String getPrcStatus()
  {
    return this.ul;
  }
  
  public void setPrcStatus(String paramString)
  {
    this.ul = paramString;
  }
  
  public String getTrnId()
  {
    return this.tT;
  }
  
  public void setTrnId(String paramString)
  {
    this.tT = paramString;
  }
  
  public long getRecordCursor()
  {
    return this.tD;
  }
  
  public String getRecordCursorStr()
  {
    return new Long(this.tD).toString();
  }
  
  public void setRecordCursor(long paramLong)
  {
    this.tD = paramLong;
  }
  
  public String getOriginatingUserId()
  {
    return this.tx;
  }
  
  public void setOriginatingUserId(String paramString)
  {
    this.tx = paramString;
  }
  
  public String getConfirmNum()
  {
    return this.tw;
  }
  
  public void setConfirmNum(String paramString)
  {
    this.tw = paramString;
  }
  
  public String getConfirmMsg()
  {
    return this.uh;
  }
  
  public void setConfirmMsg(String paramString)
  {
    this.uh = paramString;
  }
  
  public String getTypeDetail()
  {
    return this.ty;
  }
  
  public void setTypeDetail(String paramString)
  {
    this.ty = paramString;
  }
  
  public String getLastChangeDate()
  {
    return this.ua;
  }
  
  public void setLastChangeDate(String paramString)
  {
    this.ua = paramString;
  }
  
  public int getFundsProcessing()
  {
    return this.t9;
  }
  
  public void setFundsProcessing(int paramInt)
  {
    this.t9 = paramInt;
  }
  
  public int getProcessType()
  {
    return this.t3;
  }
  
  public void setProcessType(int paramInt)
  {
    this.t3 = paramInt;
  }
  
  public boolean getPossibleDuplicate()
  {
    return this.t0;
  }
  
  public void setPossibleDuplicate(boolean paramBoolean)
  {
    this.t0 = paramBoolean;
  }
  
  public String getDbTransKey()
  {
    return this.tZ;
  }
  
  public void setDbTransKey(String paramString)
  {
    this.tZ = paramString;
  }
  
  public String getEventId()
  {
    return this.tR;
  }
  
  public void setEventId(String paramString)
  {
    this.tR = paramString;
  }
  
  public ExtTransferCompanyInfo getExtTransferCompanyInfo()
  {
    return this.tS;
  }
  
  public void setExtTransferCompanyInfo(ExtTransferCompanyInfo paramExtTransferCompanyInfo)
  {
    this.tS = paramExtTransferCompanyInfo;
  }
  
  public int getProcessLeadNumber()
  {
    return this.tt;
  }
  
  public void setProcessLeadNumber(int paramInt)
  {
    this.tt = paramInt;
  }
  
  public String getAction()
  {
    return this.ts;
  }
  
  public void setAction(String paramString)
  {
    this.ts = paramString;
  }
  
  public int getFundsRetry()
  {
    return this.tP;
  }
  
  public void setFundsRetry(int paramInt)
  {
    this.tP = paramInt;
  }
  
  public int getProcessNumber()
  {
    return this.t8;
  }
  
  public void setProcessNumber(int paramInt)
  {
    this.t8 = paramInt;
  }
  
  public String getBatchId()
  {
    return this.tL;
  }
  
  public void setBatchId(String paramString)
  {
    this.tL = paramString;
  }
  
  public String getToAmount()
  {
    return this.uj;
  }
  
  public void setToAmount(String paramString)
  {
    this.uj = paramString;
  }
  
  public String getToAmountCurrency()
  {
    return this.t6;
  }
  
  public void setToAmountCurrency(String paramString)
  {
    this.t6 = paramString;
  }
  
  public UserAssignedAmount getUserAssignedAmount()
  {
    return this.tV;
  }
  
  public void setUserAssignedAmount(UserAssignedAmount paramUserAssignedAmount)
  {
    this.tV = paramUserAssignedAmount;
  }
  
  public String getUserAssignedAmountValue()
  {
    if ((this.tV != null) && (this.tV == UserAssignedAmount.TO)) {
      return getToAmount();
    }
    return getAmount();
  }
  
  public String getUserAssignedAmountCurrency()
  {
    if ((this.tV != null) && (this.tV == UserAssignedAmount.TO)) {
      return getToAmountCurrency();
    }
    return getAmountCurrency();
  }
  
  public void setEstimatedAmount(boolean paramBoolean)
  {
    this.tU = paramBoolean;
  }
  
  public boolean getEstimatedAmount()
  {
    return this.tU;
  }
  
  public boolean getIsAmountEstimated()
  {
    return (this.tU) && (this.tV == UserAssignedAmount.TO);
  }
  
  public boolean getIsToAmountEstimated()
  {
    return (this.tU) && (this.tV == UserAssignedAmount.FROM);
  }
  
  public void setEstimatedAmountValue(String paramString)
  {
    this.tH = paramString;
  }
  
  public String getEstimatedAmountValue()
  {
    return this.tH;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("srvrTId=").append(this.tq).append(", ");
    if (this.ud != null) {
      localStringBuffer.append("fiId=").append(this.ud).append(", ");
    } else {
      localStringBuffer.append("fiId=, ");
    }
    if (this.tX != null) {
      localStringBuffer.append("customerId=").append(this.tX).append(", ");
    } else {
      localStringBuffer.append("customerId=, ");
    }
    if (this.tO != null) {
      localStringBuffer.append("transferType=").append(this.tO).append(", ");
    } else {
      localStringBuffer.append("transferType=, ");
    }
    if (this.uk != null) {
      localStringBuffer.append("transferDest=").append(this.uk).append(", ");
    } else {
      localStringBuffer.append("transferDest=, ");
    }
    if (this.tE != null) {
      localStringBuffer.append("transferGroup=").append(this.tE).append(", ");
    } else {
      localStringBuffer.append("transferGroup=, ");
    }
    if (this.ub != null) {
      localStringBuffer.append("transferCategory=").append(this.ub).append(", ");
    } else {
      localStringBuffer.append("transferCategory=, ");
    }
    if (this.t2 != null) {
      localStringBuffer.append("branchId=").append(this.t2).append(", ");
    } else {
      localStringBuffer.append("branchId=, ");
    }
    if (this.tr != null) {
      localStringBuffer.append("bankFromRtn=").append(this.tr).append(", ");
    } else {
      localStringBuffer.append("bankFromRtn=, ");
    }
    if (this.tN != null) {
      localStringBuffer.append("bankFromRtnType=").append(this.tN).append(", ");
    } else {
      localStringBuffer.append("bankFromRtnType=, ");
    }
    if (this.tJ != null) {
      localStringBuffer.append("accountFromNum=").append(this.tJ).append(", ");
    } else {
      localStringBuffer.append("accountFromNum=, ");
    }
    if (this.tF != null) {
      localStringBuffer.append("accountFromType=").append(this.tF).append(", ");
    } else {
      localStringBuffer.append("accountFromType=, ");
    }
    if (this.tQ != null) {
      localStringBuffer.append("accountFromId=").append(this.tQ).append(", ");
    } else {
      localStringBuffer.append("accountFromId=, ");
    }
    if (this.tv != null) {
      localStringBuffer.append("accountFromInfo=[").append(this.tv.toString()).append("], ");
    } else {
      localStringBuffer.append("accountFromInfo=, ");
    }
    if (this.ui != null) {
      localStringBuffer.append("bankToRtn=").append(this.ui).append(", ");
    } else {
      localStringBuffer.append("bankToRtn=, ");
    }
    if (this.ue != null) {
      localStringBuffer.append("accountToNum=").append(this.ue).append(", ");
    } else {
      localStringBuffer.append("accountToNum=, ");
    }
    if (this.tG != null) {
      localStringBuffer.append("accountToType=").append(this.tG).append(", ");
    } else {
      localStringBuffer.append("accountToType=, ");
    }
    if (this.uf != null) {
      localStringBuffer.append("accountToId=").append(this.uf).append(", ");
    } else {
      localStringBuffer.append("accountToId=, ");
    }
    if (this.tz != null) {
      localStringBuffer.append("accountToInfo=[").append(this.tz.toString()).append("], ");
    } else {
      localStringBuffer.append("accountToInfo=null").append(", ");
    }
    if (this.tC != null) {
      localStringBuffer.append("amount=").append(this.tC).append(", ");
    } else {
      localStringBuffer.append("amount=, ");
    }
    if (this.t4 != null) {
      localStringBuffer.append("amountCurrency=").append(this.t4).append(", ");
    } else {
      localStringBuffer.append("amountCurrency=, ");
    }
    if (this.t7 != null) {
      localStringBuffer.append("origAmount=").append(this.t7).append(", ");
    } else {
      localStringBuffer.append("origAmount=, ");
    }
    if (this.tY != null) {
      localStringBuffer.append("origCurrency=").append(this.tY).append(", ");
    } else {
      localStringBuffer.append("origCurrency=, ");
    }
    if (this.tB != null) {
      localStringBuffer.append("dateCreate=").append(this.tB).append(", ");
    } else {
      localStringBuffer.append("dateCreate=, ");
    }
    if (this.tT != null) {
      localStringBuffer.append("trnId=").append(this.tT).append(", ");
    } else {
      localStringBuffer.append("trnId=, ");
    }
    if (this.ug != null) {
      localStringBuffer.append("dateDue=").append(this.ug).append(", ");
    } else {
      localStringBuffer.append("dateDue=, ");
    }
    if (this.t5 != null) {
      localStringBuffer.append("dateToPost=").append(this.t5).append(", ");
    } else {
      localStringBuffer.append("dateToPost=, ");
    }
    if (this.tW != null) {
      localStringBuffer.append("processDate=").append(this.tW).append(", ");
    } else {
      localStringBuffer.append("processDate=, ");
    }
    if (this.tA != null) {
      localStringBuffer.append("datePosted=").append(this.tA).append(", ");
    } else {
      localStringBuffer.append("datePosted=, ");
    }
    if (this.tp != null) {
      localStringBuffer.append("lastProcessId=").append(this.tp).append(", ");
    } else {
      localStringBuffer.append("lastProcessId=, ");
    }
    if (this.tM != null) {
      localStringBuffer.append("memo=").append(this.tM).append(", ");
    } else {
      localStringBuffer.append("memo=, ");
    }
    if (this.tu != null) {
      localStringBuffer.append("templateScope=").append(this.tu).append(", ");
    } else {
      localStringBuffer.append("templateScope=, ");
    }
    if (this.tK != null) {
      localStringBuffer.append("templateNickName=").append(this.tK).append(", ");
    } else {
      localStringBuffer.append("templateNickName=, ");
    }
    if (this.t1 != null) {
      localStringBuffer.append("sourceTemplateId=").append(this.t1).append(", ");
    } else {
      localStringBuffer.append("sourceTemplateId=, ");
    }
    if (this.uc != null) {
      localStringBuffer.append("sourceRecSrvrTId=").append(this.uc).append(", ");
    } else {
      localStringBuffer.append("sourceRecSrvrTId=, ");
    }
    if (this.ul != null) {
      localStringBuffer.append("prcStatus=").append(this.ul).append(", ");
    } else {
      localStringBuffer.append("prcStatus=, ");
    }
    if (this.submittedBy != null) {
      localStringBuffer.append("submittedBy=").append(this.submittedBy).append(", ");
    } else {
      localStringBuffer.append("submittedBy=, ");
    }
    if (this.logId != null) {
      localStringBuffer.append("logId=").append(this.logId).append(", ");
    } else {
      localStringBuffer.append("logId=, ");
    }
    if (this.tx != null) {
      localStringBuffer.append("originatingUserId=").append(this.tx).append(", ");
    } else {
      localStringBuffer.append("originatingUserId=, ");
    }
    if (this.tw != null) {
      localStringBuffer.append("confirmNum=").append(this.tw).append(", ");
    } else {
      localStringBuffer.append("confirmNum=, ");
    }
    if (this.uh != null) {
      localStringBuffer.append("confirmMsg=").append(this.uh).append(", ");
    } else {
      localStringBuffer.append("confirmMsg=, ");
    }
    if (this.ty != null) {
      localStringBuffer.append("typeDetail=").append(this.ty).append(", ");
    } else {
      localStringBuffer.append("typeDetail=, ");
    }
    if (this.tL != null) {
      localStringBuffer.append("batchId=").append(this.tL).append(", ");
    } else {
      localStringBuffer.append("batchId=, ");
    }
    if (this.ua != null) {
      localStringBuffer.append("lastChangeDate=").append(this.ua).append(", ");
    } else {
      localStringBuffer.append("lastChangeDate=, ");
    }
    localStringBuffer.append("fundsProcessing=").append(this.t9).append(", ");
    localStringBuffer.append("processType=").append(this.t3).append(", ");
    localStringBuffer.append("possibleDuplicate=").append(this.t0).append(", ");
    if (this.ts != null) {
      localStringBuffer.append("action=").append(this.ts).append(", ");
    } else {
      localStringBuffer.append("action=, ");
    }
    localStringBuffer.append("fundsRetry=").append(this.tP).append(", ");
    if (this.uj != null) {
      localStringBuffer.append("toAmount=").append(this.uj).append(", ");
    } else {
      localStringBuffer.append("toAmount=, ");
    }
    if (this.t6 != null) {
      localStringBuffer.append("toAmountCurrency=").append(this.t6).append(", ");
    } else {
      localStringBuffer.append("toAmountCurrency=, ");
    }
    localStringBuffer.append("userAssignedAmount=").append(this.tV);
    return localStringBuffer.toString();
  }
  
  public TransferInfo getTransferInfo(String paramString)
  {
    Hashtable localHashtable = FFSUtil.stringToHashtable(paramString);
    return getTransferInfo(localHashtable);
  }
  
  public TransferInfo getTransferInfo(Hashtable paramHashtable)
  {
    if (paramHashtable == null) {
      return this;
    }
    this.tq = ((String)paramHashtable.remove("srvrTId"));
    this.ud = ((String)paramHashtable.remove("fiId"));
    this.tX = ((String)paramHashtable.remove("customerId"));
    this.tO = ((String)paramHashtable.remove("transferType"));
    this.uk = ((String)paramHashtable.remove("transferDest"));
    this.tE = ((String)paramHashtable.remove("transferGroup"));
    this.ub = ((String)paramHashtable.remove("transferCategory"));
    this.t2 = ((String)paramHashtable.remove("branchId"));
    this.tr = ((String)paramHashtable.remove("bankFromRtn"));
    this.tN = ((String)paramHashtable.remove("bankFromRtnType"));
    this.tJ = ((String)paramHashtable.remove("accountFromNum"));
    this.tF = ((String)paramHashtable.remove("accountFromType"));
    this.tQ = ((String)paramHashtable.remove("accountFromId"));
    String str1 = (String)paramHashtable.remove("accountFromInfo");
    if (str1 == null)
    {
      this.tv = null;
    }
    else
    {
      str1 = str1.substring(1, str1.length() - 1);
      localObject1 = FFSUtil.stringToHashtable(str1);
      this.tv = new ExtTransferAcctInfo();
      this.tv.getExtTransferAcctInfo((Hashtable)localObject1);
    }
    this.ui = ((String)paramHashtable.remove("bankToRtn"));
    this.ue = ((String)paramHashtable.remove("accountToNum"));
    this.tG = ((String)paramHashtable.remove("accountToType"));
    this.uf = ((String)paramHashtable.remove("accountToId"));
    Object localObject1 = (String)paramHashtable.remove("accountToInfo");
    if (localObject1 == null)
    {
      this.tz = null;
    }
    else
    {
      localObject1 = ((String)localObject1).substring(1, ((String)localObject1).length() - 1);
      localObject2 = FFSUtil.stringToHashtable((String)localObject1);
      this.tz = new ExtTransferAcctInfo();
      this.tz.getExtTransferAcctInfo((Hashtable)localObject2);
    }
    this.tC = ((String)paramHashtable.remove("amount"));
    this.t4 = ((String)paramHashtable.remove("amountCurrency"));
    this.t7 = ((String)paramHashtable.remove("origAmount"));
    this.tY = ((String)paramHashtable.remove("origCurrency"));
    this.tB = ((String)paramHashtable.remove("dateCreate"));
    this.tT = ((String)paramHashtable.remove("trnId"));
    this.ug = ((String)paramHashtable.remove("dateDue"));
    this.t5 = ((String)paramHashtable.remove("dateToPost"));
    this.tW = ((String)paramHashtable.remove("processDate"));
    this.tp = ((String)paramHashtable.remove("lastProcessId"));
    this.tM = ((String)paramHashtable.remove("memo"));
    this.tu = ((String)paramHashtable.remove("templateScope"));
    this.tK = ((String)paramHashtable.remove("templateNickName"));
    this.t1 = ((String)paramHashtable.remove("sourceTemplateId"));
    this.uc = ((String)paramHashtable.remove("sourceRecSrvrTId"));
    this.ul = ((String)paramHashtable.remove("prcStatus"));
    this.submittedBy = ((String)paramHashtable.remove("submittedBy"));
    this.logId = ((String)paramHashtable.remove("logId"));
    this.tx = ((String)paramHashtable.remove("originatingUserId"));
    this.tw = ((String)paramHashtable.remove("confirmNum"));
    this.uh = ((String)paramHashtable.remove("confirmMsg"));
    this.ty = ((String)paramHashtable.remove("typeDetail"));
    this.tL = ((String)paramHashtable.remove("batchId"));
    this.ua = ((String)paramHashtable.remove("lastChangeDate"));
    this.ts = ((String)paramHashtable.remove("action"));
    Object localObject2 = (String)paramHashtable.remove("fundsProcessing");
    if (localObject2 != null) {
      this.t9 = Integer.parseInt((String)localObject2);
    }
    localObject2 = (String)paramHashtable.remove("processType");
    if (localObject2 != null) {
      this.t3 = Integer.parseInt((String)localObject2);
    }
    localObject2 = (String)paramHashtable.remove("possibleDuplicate");
    if (localObject2 != null) {
      this.t0 = Boolean.valueOf((String)localObject2).booleanValue();
    }
    this.uj = ((String)paramHashtable.remove("toAmount"));
    this.t6 = ((String)paramHashtable.remove("toAmountCurrency"));
    localObject2 = (String)paramHashtable.remove("userAssignedAmount");
    if (localObject2 != null) {
      this.tV = UserAssignedAmount.getEnum((String)localObject2);
    }
    Enumeration localEnumeration = paramHashtable.keys();
    String str2 = null;
    while (localEnumeration.hasMoreElements())
    {
      str2 = (String)localEnumeration.nextElement();
      if (this.tI == null) {
        this.tI = new Hashtable();
      }
      this.tI.put(str2, paramHashtable.remove(str2));
    }
    return this;
  }
  
  public Object clone()
  {
    try
    {
      TransferInfo localTransferInfo = (TransferInfo)super.clone();
      if (this.tv != null) {
        localTransferInfo.setAccountFromInfo((ExtTransferAcctInfo)this.tv.clone());
      }
      if (this.tz != null) {
        localTransferInfo.setAccountToInfo((ExtTransferAcctInfo)this.tz.clone());
      }
      return localTransferInfo;
    }
    catch (Exception localException) {}
    return null;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.TransferInfo
 * JD-Core Version:    0.7.0.1
 */