package com.ffusion.ffs.bpw.fulfill;

import com.ffusion.ffs.bpw.custimpl.ToBackend;
import com.ffusion.ffs.bpw.interfaces.IImmediateBillPayHandler;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.IntraTrnRslt;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.util.FFSDebug;
import java.util.HashMap;

public class DefaultImmediateHandler
  implements IImmediateBillPayHandler
{
  public PmtInfo[] processBillPayment(PmtInfo[] paramArrayOfPmtInfo, String paramString, HashMap paramHashMap)
    throws Exception
  {
    FFSDebug.log("DefaultImmediateHandler.processBillPayment starts6");
    if ((paramArrayOfPmtInfo != null) && (paramArrayOfPmtInfo.length > 0))
    {
      PmtInfo localPmtInfo = paramArrayOfPmtInfo[0];
      String str = "PROCESSEDON";
      try
      {
        IntraTrnInfo localIntraTrnInfo = getIntraTransferInfoFromPmtInfo(localPmtInfo, paramString);
        ToBackend localToBackend = getIntraTransferBackendHandler();
        IntraTrnRslt localIntraTrnRslt = localToBackend.ProcessImmediateIntraTrn(localIntraTrnInfo);
        str = getStatus(localIntraTrnRslt.status);
        localPmtInfo.setStatus(str);
        localPmtInfo.setStatusCode(0);
        localPmtInfo.setStatusMsg("Success");
      }
      catch (Exception localException)
      {
        FFSDebug.log("DefaultImmediateHandler.processBillPayment exception during IntraTransfer: " + localException.toString(), 6);
        throw localException;
      }
      FFSDebug.log("DefaultImmediateHandler.processBillPayment returns status: " + localPmtInfo.getStatus(), 6);
    }
    FFSDebug.log("DefaultImmediateHandler.processBillPayment ends", 6);
    return paramArrayOfPmtInfo;
  }
  
  public IntraTrnInfo getIntraTransferInfoFromPmtInfo(PmtInfo paramPmtInfo, String paramString)
  {
    PayeeInfo localPayeeInfo = paramPmtInfo.getPayeeInfo();
    PayeeRouteInfo localPayeeRouteInfo = localPayeeInfo.getPayeeRouteInfo();
    IntraTrnInfo localIntraTrnInfo = new IntraTrnInfo();
    localIntraTrnInfo.customerId = paramPmtInfo.getCustomerID();
    localIntraTrnInfo.bankId = localPayeeRouteInfo.BankID;
    localIntraTrnInfo.acctIdFrom = paramPmtInfo.getAcctDebitID();
    localIntraTrnInfo.acctTypeFrom = paramPmtInfo.getAcctDebitType();
    localIntraTrnInfo.acctIdTo = localPayeeRouteInfo.AcctID;
    localIntraTrnInfo.acctTypeTo = localPayeeRouteInfo.AcctType;
    localIntraTrnInfo.amount = paramPmtInfo.getAmt();
    localIntraTrnInfo.curDef = paramPmtInfo.getCurDef();
    localIntraTrnInfo.dateToPost = paramPmtInfo.dateToPost;
    localIntraTrnInfo.srvrTid = paramPmtInfo.getSrvrTID();
    localIntraTrnInfo.logId = paramPmtInfo.getLogID();
    localIntraTrnInfo.eventId = "0";
    localIntraTrnInfo.eventSequence = 1;
    localIntraTrnInfo.possibleDuplicate = false;
    localIntraTrnInfo.recSrvrTid = paramPmtInfo.getRecSrvrTID();
    localIntraTrnInfo.batchKey = paramString;
    localIntraTrnInfo.lastModified = paramPmtInfo.getLastModified();
    localIntraTrnInfo.submittedBy = paramPmtInfo.getSubmittedBy();
    localIntraTrnInfo.fiId = paramPmtInfo.getFIID();
    localIntraTrnInfo.fromBankId = paramPmtInfo.getBankID();
    return localIntraTrnInfo;
  }
  
  public static String getStatus(int paramInt)
  {
    if (paramInt == 0) {
      return "PROCESSEDON";
    }
    if (paramInt == 10504) {
      return "NOFUNDSON";
    }
    if (paramInt == 2000) {
      return "FAILEDON";
    }
    return "FAILEDON";
  }
  
  public ToBackend getIntraTransferBackendHandler()
  {
    return new ToBackend();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.fulfill.DefaultImmediateHandler
 * JD-Core Version:    0.7.0.1
 */