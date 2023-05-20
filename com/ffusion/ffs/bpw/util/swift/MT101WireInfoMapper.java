package com.ffusion.ffs.bpw.util.swift;

import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.msgbroker.interfaces.IMBMessage;
import com.ffusion.util.logging.DebugLog;

public class MT101WireInfoMapper
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
    MT101WireMapper localMT101WireMapper = new MT101WireMapper();
    localWireTransfer = (WireTransfer)localMT101WireMapper.parse(paramIMBMessage);
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
    MT101WireMapper localMT101WireMapper = new MT101WireMapper();
    IMBMessage localIMBMessage = null;
    localIMBMessage = localMT101WireMapper.build(localWireTransfer);
    return localIMBMessage;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.swift.MT101WireInfoMapper
 * JD-Core Version:    0.7.0.1
 */