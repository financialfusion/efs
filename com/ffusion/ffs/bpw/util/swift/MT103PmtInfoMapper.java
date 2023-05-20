package com.ffusion.ffs.bpw.util.swift;

import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.util.BeansConverter;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.msgbroker.interfaces.IMBMessage;
import com.ffusion.util.logging.DebugLog;

public class MT103PmtInfoMapper
  implements ISWIFTMapper
{
  public Object parse(IMBMessage paramIMBMessage)
    throws SWIFTParseException
  {
    Payment localPayment = null;
    PmtInfo localPmtInfo = new PmtInfo();
    if (paramIMBMessage == null) {
      return localPmtInfo;
    }
    MT103PaymentMapper localMT103PaymentMapper = new MT103PaymentMapper();
    localPayment = (Payment)localMT103PaymentMapper.parse(paramIMBMessage);
    if (localPayment != null) {
      BeansConverter.setPmtInfoFromPmt(localPmtInfo, localPayment);
    }
    return localPmtInfo;
  }
  
  public IMBMessage build(Object paramObject)
    throws SWIFTParseException
  {
    if (paramObject == null)
    {
      localObject = "bean object passed found null";
      DebugLog.log((String)localObject);
      throw new SWIFTParseException((String)localObject);
    }
    if (!(paramObject instanceof TransferInfo))
    {
      localObject = "bean object passed should be instance of com.ffusion.ffs.bpw.interfaces.PmtInfo";
      DebugLog.log((String)localObject);
      throw new SWIFTParseException((String)localObject);
    }
    Object localObject = (PmtInfo)paramObject;
    Payments localPayments = new Payments();
    Payment localPayment = (Payment)localPayments.create();
    Payees localPayees = new Payees();
    localPayees.add(BeansConverter.PayeeInfoToPayee(((PmtInfo)localObject).payeeInfo));
    Accounts localAccounts = new Accounts();
    if (localObject != null) {
      BeansConverter.setPmtFromPmtInfo(localPayment, (PmtInfo)localObject, localPayees, localAccounts, false);
    }
    MT103PaymentMapper localMT103PaymentMapper = new MT103PaymentMapper();
    IMBMessage localIMBMessage = null;
    localIMBMessage = localMT103PaymentMapper.build(localPayment);
    return localIMBMessage;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.swift.MT103PmtInfoMapper
 * JD-Core Version:    0.7.0.1
 */