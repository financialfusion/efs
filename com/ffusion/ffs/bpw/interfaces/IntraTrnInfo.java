package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.util.enums.UserAssignedAmount;

public class IntraTrnInfo
  extends BPWInfoBase
{
  public String customerId;
  public String bankId;
  public String branchId;
  public String acctIdFrom;
  public String acctTypeFrom;
  public String acctIdTo;
  public String acctTypeTo;
  public String amount;
  public String curDef;
  public String toAmount;
  public String toAmtCurrency;
  public UserAssignedAmount userAssignedAmount = UserAssignedAmount.SINGLE;
  public String dateToPost;
  public String srvrTid;
  public String logId;
  public String eventId;
  public int eventSequence;
  public boolean possibleDuplicate;
  public String status;
  public String recSrvrTid;
  public String batchKey;
  public int statusCode = -1;
  public String statusMsg = null;
  public String lastModified;
  public String submittedBy;
  public String fiId;
  public String fromBankId;
  public String fromBranchId;
  
  public IntraTrnInfo() {}
  
  public IntraTrnInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, int paramInt, boolean paramBoolean, String paramString12, String paramString13)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, null, paramString9, paramString10, "", paramString11, paramInt, paramBoolean, paramString12, paramString13, "", "1000", paramString2, paramString3);
  }
  
  public IntraTrnInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, int paramInt, boolean paramBoolean, String paramString12, String paramString13, String paramString14)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, null, paramString9, paramString10, "", paramString11, paramInt, paramBoolean, paramString12, paramString13, paramString14, "1000", paramString2, paramString3);
  }
  
  public IntraTrnInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt, boolean paramBoolean, String paramString13, String paramString14, String paramString15)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, null, paramString9, paramString10, paramString11, paramString12, paramInt, paramBoolean, paramString13, paramString14, paramString15, "1000", paramString2, paramString3);
  }
  
  public IntraTrnInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt, boolean paramBoolean, String paramString13, String paramString14, String paramString15, String paramString16)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, null, paramString9, paramString10, paramString11, paramString12, paramInt, paramBoolean, paramString13, paramString14, paramString15, paramString16, paramString2, paramString3);
  }
  
  public IntraTrnInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt, boolean paramBoolean, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, null, paramString9, paramString10, paramString11, paramString12, paramInt, paramBoolean, paramString13, paramString14, paramString15, paramString16, paramString17, paramString18);
  }
  
  public IntraTrnInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, int paramInt, boolean paramBoolean, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, String paramString19)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, "0.00", null, UserAssignedAmount.SINGLE, paramString10, paramString11, paramString12, paramString13, paramInt, paramBoolean, paramString14, paramString15, paramString16, paramString17, paramString18, paramString19);
  }
  
  public IntraTrnInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, UserAssignedAmount paramUserAssignedAmount, String paramString12, String paramString13, String paramString14, String paramString15, int paramInt, boolean paramBoolean, String paramString16, String paramString17, String paramString18, String paramString19, String paramString20, String paramString21)
  {
    this.customerId = paramString1;
    this.bankId = paramString2;
    this.branchId = paramString3;
    this.acctIdFrom = paramString4;
    this.acctTypeFrom = paramString5;
    this.acctIdTo = paramString6;
    this.acctTypeTo = paramString7;
    this.amount = paramString8;
    this.curDef = BPWUtil.validateCurrencyString(paramString9);
    this.toAmount = paramString10;
    this.toAmtCurrency = paramString11;
    this.userAssignedAmount = paramUserAssignedAmount;
    this.dateToPost = paramString12;
    this.srvrTid = paramString13;
    this.eventId = paramString15;
    this.eventSequence = paramInt;
    this.possibleDuplicate = paramBoolean;
    this.batchKey = paramString16;
    this.recSrvrTid = paramString17;
    this.status = paramString18;
    this.logId = paramString14;
    this.fiId = paramString19;
    this.fromBankId = paramString20;
    this.fromBranchId = paramString21;
  }
  
  public int getStatusCode()
  {
    return this.statusCode;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.statusCode = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.statusMsg;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.statusMsg = paramString;
  }
  
  public String getLogId()
  {
    return this.logId;
  }
  
  public void setLogId(String paramString)
  {
    this.logId = paramString;
  }
  
  public String getSubmittedBy()
  {
    return this.submittedBy;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.submittedBy = paramString;
  }
  
  public String getCustomerId()
  {
    return this.customerId;
  }
  
  public void setCustomerId(String paramString)
  {
    this.customerId = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.IntraTrnInfo
 * JD-Core Version:    0.7.0.1
 */