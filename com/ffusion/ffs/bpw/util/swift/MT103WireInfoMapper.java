package com.ffusion.ffs.bpw.util.swift;

import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.msgbroker.interfaces.IMBMessage;
import com.ffusion.util.logging.DebugLog;

public class MT103WireInfoMapper
  implements ISWIFTMapper
{
  public Object parse(IMBMessage paramIMBMessage)
    throws SWIFTParseException
  {
    WireInfo localWireInfo = new WireInfo();
    if (paramIMBMessage == null) {
      return localWireInfo;
    }
    WireTransfer localWireTransfer = null;
    MT103WireMapper localMT103WireMapper = new MT103WireMapper();
    localWireTransfer = (WireTransfer)localMT103WireMapper.parse(paramIMBMessage);
    if (localWireTransfer != null) {
      localWireInfo = localWireTransfer.getWireInfo();
    }
    return localWireInfo;
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
    if (!(paramObject instanceof WireInfo))
    {
      localObject = "bean object passed should be instance of com.ffusion.ffs.bpw.interfaces.WireInfo";
      DebugLog.log((String)localObject);
      throw new SWIFTParseException((String)localObject);
    }
    Object localObject = (WireInfo)paramObject;
    WireTransfers localWireTransfers = new WireTransfers();
    WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.create();
    localWireTransfer.setWireInfo((WireInfo)localObject);
    MT103WireMapper localMT103WireMapper = new MT103WireMapper();
    IMBMessage localIMBMessage = null;
    localIMBMessage = localMT103WireMapper.build(localWireTransfer);
    return localIMBMessage;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.swift.MT103WireInfoMapper
 * JD-Core Version:    0.7.0.1
 */