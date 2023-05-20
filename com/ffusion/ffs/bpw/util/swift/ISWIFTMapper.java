package com.ffusion.ffs.bpw.util.swift;

import com.ffusion.msgbroker.interfaces.IMBMessage;

public abstract interface ISWIFTMapper
{
  public abstract Object parse(IMBMessage paramIMBMessage)
    throws SWIFTParseException;
  
  public abstract IMBMessage build(Object paramObject)
    throws SWIFTParseException;
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.swift.ISWIFTMapper
 * JD-Core Version:    0.7.0.1
 */