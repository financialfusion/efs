package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.bpw.util.BPWUtil;

public class FundsAllocInfo
  extends BPWInfoBase
{
  public String customerId;
  public String bankId;
  public String acctDebitId;
  public String acctDebitType;
  public String amount;
  public String curDef;
  public String srvrTid;
  public String payeeID;
  public String payeeName;
  public int eventSequence;
  public boolean possibleDuplicate;
  public String acctCreditId;
  public String batchKey;
  public String fiId;
  public String recSrvrTID;
  protected PmtInfo l6 = null;
  
  public FundsAllocInfo() {}
  
  public FundsAllocInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, int paramInt, boolean paramBoolean, String paramString9, String paramString10)
  {
    this("1000", paramString1, paramString2, paramString3, paramString4, paramString5, null, paramString6, paramString7, paramString8, paramInt, paramBoolean, paramString9, paramString10, null);
  }
  
  public FundsAllocInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, int paramInt, boolean paramBoolean, String paramString10, String paramString11)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, null, paramString7, paramString8, paramString9, paramInt, paramBoolean, paramString10, paramString11, null);
  }
  
  public FundsAllocInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, int paramInt, boolean paramBoolean, String paramString10, String paramString11, String paramString12)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, null, paramString7, paramString8, paramString9, paramInt, paramBoolean, paramString10, paramString11, paramString12);
  }
  
  public FundsAllocInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, int paramInt, boolean paramBoolean, String paramString11, String paramString12, String paramString13)
  {
    this.fiId = paramString1;
    this.customerId = paramString2;
    this.bankId = paramString3;
    this.acctDebitId = paramString4;
    this.acctDebitType = paramString5;
    this.amount = paramString6;
    this.curDef = BPWUtil.validateCurrencyString(paramString7);
    this.srvrTid = paramString8;
    this.payeeID = paramString9;
    this.payeeName = paramString10;
    this.eventSequence = paramInt;
    this.possibleDuplicate = paramBoolean;
    this.acctCreditId = paramString11;
    this.batchKey = paramString12;
    this.recSrvrTID = paramString13;
  }
  
  public FundsAllocInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, int paramInt, boolean paramBoolean, String paramString11, String paramString12, String paramString13, PmtInfo paramPmtInfo)
  {
    this.fiId = paramString1;
    this.customerId = paramString2;
    this.bankId = paramString3;
    this.acctDebitId = paramString4;
    this.acctDebitType = paramString5;
    this.amount = paramString6;
    this.curDef = BPWUtil.validateCurrencyString(paramString7);
    this.srvrTid = paramString8;
    this.payeeID = paramString9;
    this.payeeName = paramString10;
    this.eventSequence = paramInt;
    this.possibleDuplicate = paramBoolean;
    this.acctCreditId = paramString11;
    this.batchKey = paramString12;
    this.recSrvrTID = paramString13;
    this.l6 = paramPmtInfo;
  }
  
  public PmtInfo getPmtInfo()
  {
    return this.l6;
  }
  
  public void setPmtInfo(PmtInfo paramPmtInfo)
  {
    this.l6 = paramPmtInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.FundsAllocInfo
 * JD-Core Version:    0.7.0.1
 */