package com.ffusion.ffs.bpw.util.swift;

import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.msgbroker.interfaces.IMBMessage;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;

public class MT103TransferInfoMapper
  implements ISWIFTMapper
{
  public Object parse(IMBMessage paramIMBMessage)
    throws SWIFTParseException
  {
    TransferInfo localTransferInfo = new TransferInfo();
    if (paramIMBMessage == null) {
      return localTransferInfo;
    }
    Transfer localTransfer = null;
    MT103TransferMapper localMT103TransferMapper = new MT103TransferMapper();
    localTransfer = (Transfer)localMT103TransferMapper.parse(paramIMBMessage);
    if (localTransfer != null) {
      localTransferInfo = localTransfer.getTransferInfo();
    }
    return localTransferInfo;
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
      localObject = "bean object passed should be instance of com.ffusion.ffs.bpw.interfaces.TransferInfo";
      DebugLog.log((String)localObject);
      throw new SWIFTParseException((String)localObject);
    }
    Object localObject = (TransferInfo)paramObject;
    Transfers localTransfers = new Transfers();
    Transfer localTransfer = (Transfer)localTransfers.create();
    localTransfer.setTransferInfo((TransferInfo)localObject, null);
    if (localTransfer.getAmountRounded().equals("0.00"))
    {
      double d = new Double(((TransferInfo)localObject).getAmount()).doubleValue();
      if (d > 0.0D)
      {
        BigDecimal localBigDecimal = new BigDecimal(((TransferInfo)localObject).getAmount());
        localTransfer.setAmount(localBigDecimal);
      }
    }
    localTransfer.setDateFormat("yyMMdd");
    MT103TransferMapper localMT103TransferMapper = new MT103TransferMapper();
    IMBMessage localIMBMessage = null;
    localIMBMessage = localMT103TransferMapper.build(localTransfer);
    return localIMBMessage;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.swift.MT103TransferInfoMapper
 * JD-Core Version:    0.7.0.1
 */