package com.ffusion.ffs.bpw.interfaces;

import java.util.HashMap;

public abstract interface IImmediateBillPayHandler
{
  public abstract PmtInfo[] processBillPayment(PmtInfo[] paramArrayOfPmtInfo, String paramString, HashMap paramHashMap)
    throws Exception;
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.IImmediateBillPayHandler
 * JD-Core Version:    0.7.0.1
 */