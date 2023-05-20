package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.bpw.enums.FrequencyType;
import com.ffusion.ffs.bpw.enums.PaymentType;

public class PmtInfo
  extends BPWInfoBase
{
  public String SrvrTID;
  public String CustomerID;
  public String FIID;
  public String PayeeID;
  public String PayAcct;
  protected int ne;
  public int PayeeListID;
  public double Amount;
  private String ng;
  public String BankID;
  public String AcctDebitID;
  public String AcctDebitType;
  public String Memo;
  public String ExtdPmtInfo;
  public String OriginatedDate;
  public String CurDef;
  public String Status;
  public int StartDate;
  public String ConfirmationNumber;
  public String PaymentType;
  public PayeeInfo payeeInfo;
  public String LogID;
  public String RecSrvrTID;
  public int statusCode = -1;
  public String statusMsg = null;
  public String AdjustedDueDt;
  public String StatusDt;
  public String lastModified;
  public String submittedBy;
  public int cursorID;
  public String frequency;
  protected FrequencyType nb;
  public String batchID;
  public String templateID;
  public String paymentName;
  public String dateToPost;
  public String action;
  protected Boolean nf = null;
  protected Boolean nd = null;
  protected String nc = null;
  public static final String EXTRAFIELDS_HASHKEY_CHECKNUM = "BPTW_CHECK_NUM";
  public static final String EXTRAFIELDS_HASHKEY_INVOICE = "INVOICE";
  
  public PmtInfo() {}
  
  public PmtInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, double paramDouble, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, int paramInt2, String paramString14, String paramString15, PayeeInfo paramPayeeInfo, String paramString16, String paramString17)
  {
    this.SrvrTID = paramString1;
    this.CustomerID = paramString2;
    this.FIID = paramString3;
    this.PayeeID = paramString4;
    this.PayAcct = paramString5;
    this.PayeeListID = paramInt1;
    this.Amount = paramDouble;
    this.BankID = paramString6;
    this.AcctDebitID = paramString7;
    this.AcctDebitType = paramString8;
    this.Memo = paramString9;
    this.ExtdPmtInfo = paramString10;
    this.OriginatedDate = paramString11;
    this.CurDef = paramString12;
    this.Status = paramString13;
    this.StartDate = paramInt2;
    this.PaymentType = paramString14;
    this.RecSrvrTID = paramString15;
    this.payeeInfo = paramPayeeInfo;
    this.lastModified = paramString16;
    this.submittedBy = paramString17;
    super.setSubmittedBy(paramString17);
  }
  
  public PmtInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, int paramInt2, String paramString15, String paramString16, PayeeInfo paramPayeeInfo, String paramString17, String paramString18)
  {
    this.SrvrTID = paramString1;
    this.CustomerID = paramString2;
    this.FIID = paramString3;
    this.PayeeID = paramString4;
    this.PayAcct = paramString5;
    this.PayeeListID = paramInt1;
    setAmt(paramString6);
    this.BankID = paramString7;
    this.AcctDebitID = paramString8;
    this.AcctDebitType = paramString9;
    this.Memo = paramString10;
    this.ExtdPmtInfo = paramString11;
    this.OriginatedDate = paramString12;
    this.CurDef = paramString13;
    this.Status = paramString14;
    this.StartDate = paramInt2;
    this.PaymentType = paramString15;
    this.RecSrvrTID = paramString16;
    this.payeeInfo = paramPayeeInfo;
    this.lastModified = paramString17;
    this.submittedBy = paramString18;
    super.setSubmittedBy(paramString18);
  }
  
  public String getAcctDebitID()
  {
    return this.AcctDebitID;
  }
  
  public void setAcctDebitID(String paramString)
  {
    this.AcctDebitID = paramString;
  }
  
  public String getAcctDebitType()
  {
    return this.AcctDebitType;
  }
  
  public void setAcctDebitType(String paramString)
  {
    this.AcctDebitType = paramString;
  }
  
  public String getAction()
  {
    return this.action;
  }
  
  public void setAction(String paramString)
  {
    this.action = paramString;
  }
  
  public String getAdjustedDueDt()
  {
    return this.AdjustedDueDt;
  }
  
  public void setAdjustedDueDt(String paramString)
  {
    this.AdjustedDueDt = paramString;
  }
  
  public double getAmount()
  {
    return this.Amount;
  }
  
  public String getAmt()
  {
    return this.ng != null ? this.ng : String.valueOf(this.Amount);
  }
  
  public void setAmount(double paramDouble)
  {
    this.Amount = paramDouble;
  }
  
  public void setAmt(String paramString)
  {
    this.ng = paramString;
    this.Amount = (paramString != null ? Double.parseDouble(paramString) : 0.0D);
  }
  
  public String getBankID()
  {
    return this.BankID;
  }
  
  public void setBankID(String paramString)
  {
    this.BankID = paramString;
  }
  
  public String getBatchID()
  {
    return this.batchID;
  }
  
  public void setBatchID(String paramString)
  {
    this.batchID = paramString;
  }
  
  public String getConfirmationNumber()
  {
    return this.ConfirmationNumber;
  }
  
  public void setConfirmationNumber(String paramString)
  {
    this.ConfirmationNumber = paramString;
  }
  
  public String getCurDef()
  {
    return this.CurDef;
  }
  
  public void setCurDef(String paramString)
  {
    this.CurDef = paramString;
  }
  
  public int getCursorID()
  {
    return this.cursorID;
  }
  
  public void setCursorID(int paramInt)
  {
    this.cursorID = paramInt;
  }
  
  public String getCustomerID()
  {
    return this.CustomerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.CustomerID = paramString;
  }
  
  public String getDateToPost()
  {
    return this.dateToPost;
  }
  
  public void setDateToPost(String paramString)
  {
    this.dateToPost = paramString;
  }
  
  public String getExtdPmtInfo()
  {
    return this.ExtdPmtInfo;
  }
  
  public void setExtdPmtInfo(String paramString)
  {
    this.ExtdPmtInfo = paramString;
  }
  
  public String getFIID()
  {
    return this.FIID;
  }
  
  public void setFIID(String paramString)
  {
    this.FIID = paramString;
  }
  
  public String getFrequency()
  {
    return getRecFrequencyStr();
  }
  
  public void setFrequency(int paramInt)
  {
    setRecFrequency(paramInt);
  }
  
  public void setFrequency(String paramString)
  {
    setRecFrequency(paramString);
  }
  
  public FrequencyType getRecFrequency()
  {
    if (this.nb != null) {
      return this.nb;
    }
    return FrequencyType.getEnum(this.frequency);
  }
  
  public String getRecFrequencyStr()
  {
    return this.nb != null ? this.nb.getName() : this.frequency;
  }
  
  public int getRecFrequencyValue()
  {
    if (this.nb != null) {
      return this.nb.getValue();
    }
    return FrequencyType.getEnum(this.frequency) != null ? FrequencyType.getEnum(this.frequency).getValue() : FrequencyType.NONE.getValue();
  }
  
  public void setRecFrequency(FrequencyType paramFrequencyType)
  {
    this.nb = paramFrequencyType;
    this.frequency = (paramFrequencyType != null ? paramFrequencyType.getName() : null);
  }
  
  public void setRecFrequency(String paramString)
  {
    setRecFrequency(FrequencyType.getEnum(paramString));
  }
  
  public void setRecFrequency(int paramInt)
  {
    setRecFrequency(FrequencyType.getEnum(paramInt));
  }
  
  public String getLastModified()
  {
    return this.lastModified;
  }
  
  public void setLastModified(String paramString)
  {
    this.lastModified = paramString;
  }
  
  public String getLogID()
  {
    return this.LogID;
  }
  
  public String getLogId()
  {
    return getLogID();
  }
  
  public void setLogID(String paramString)
  {
    this.LogID = paramString;
  }
  
  public void setLogId(String paramString)
  {
    setLogID(paramString);
  }
  
  public String getMemo()
  {
    return this.Memo;
  }
  
  public void setMemo(String paramString)
  {
    this.Memo = paramString;
  }
  
  public String getOriginatedDate()
  {
    return this.OriginatedDate;
  }
  
  public void setOriginatedDate(String paramString)
  {
    this.OriginatedDate = paramString;
  }
  
  public String getPayAcct()
  {
    return this.PayAcct;
  }
  
  public void setPayAcct(String paramString)
  {
    this.PayAcct = paramString;
  }
  
  public String getPayeeID()
  {
    return this.PayeeID;
  }
  
  public void setPayeeID(String paramString)
  {
    this.PayeeID = paramString;
  }
  
  public PayeeInfo getPayeeInfo()
  {
    return this.payeeInfo;
  }
  
  public void setPayeeInfo(PayeeInfo paramPayeeInfo)
  {
    this.payeeInfo = paramPayeeInfo;
  }
  
  public int getPayeeListID()
  {
    return this.PayeeListID;
  }
  
  public void setPayeeListID(int paramInt)
  {
    this.PayeeListID = paramInt;
  }
  
  public String getPaymentName()
  {
    return this.paymentName;
  }
  
  public void setPaymentName(String paramString)
  {
    this.paymentName = paramString;
  }
  
  public String getPaymentType()
  {
    return this.PaymentType;
  }
  
  public void setPaymentType(String paramString)
  {
    this.PaymentType = paramString;
  }
  
  public String getRecSrvrTID()
  {
    return this.RecSrvrTID;
  }
  
  public void setRecSrvrTID(String paramString)
  {
    this.RecSrvrTID = paramString;
  }
  
  public String getSrvrTID()
  {
    return this.SrvrTID;
  }
  
  public void setSrvrTID(String paramString)
  {
    this.SrvrTID = paramString;
  }
  
  public int getStartDate()
  {
    return this.StartDate;
  }
  
  public void setStartDate(int paramInt)
  {
    this.StartDate = paramInt;
  }
  
  public String getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(String paramString)
  {
    this.Status = paramString;
  }
  
  public int getStatusCode()
  {
    return this.statusCode;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.statusCode = paramInt;
  }
  
  public String getStatusDt()
  {
    return this.StatusDt;
  }
  
  public void setStatusDt(String paramString)
  {
    this.StatusDt = paramString;
  }
  
  public String getStatusMsg()
  {
    return this.statusMsg;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.statusMsg = paramString;
  }
  
  public String getSubmittedBy()
  {
    if (this.submittedBy != null) {
      return this.submittedBy;
    }
    return super.getSubmittedBy();
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.submittedBy = paramString;
    super.setSubmittedBy(paramString);
  }
  
  public String getTemplateID()
  {
    return this.templateID;
  }
  
  public void setTemplateID(String paramString)
  {
    this.templateID = paramString;
  }
  
  public Boolean getImmediateFundAllocation()
  {
    return this.nf;
  }
  
  public void setImmediateFundAllocation(Boolean paramBoolean)
  {
    this.nf = paramBoolean;
  }
  
  public void setImmediateFundAllocation(boolean paramBoolean)
  {
    this.nf = new Boolean(paramBoolean);
  }
  
  public Boolean getImmediateProcessing()
  {
    return this.nd;
  }
  
  public void setImmediateProcessing(Boolean paramBoolean)
  {
    this.nd = paramBoolean;
  }
  
  public void setImmediateProcessing(boolean paramBoolean)
  {
    this.nd = new Boolean(paramBoolean);
  }
  
  public int getRouteId()
  {
    return this.ne;
  }
  
  public void setRouteId(int paramInt)
  {
    this.ne = paramInt;
  }
  
  public PaymentType getPaymentTypeEnum()
  {
    return PaymentType.getEnum(getPaymentType());
  }
  
  public String getTrnId()
  {
    return this.nc;
  }
  
  public void setTrnId(String paramString)
  {
    this.nc = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.PmtInfo
 * JD-Core Version:    0.7.0.1
 */