package com.ffusion.ffs.ofx.interfaces;

import java.io.Serializable;

public final class OFXUnknownData
  implements Serializable
{
  public String _tag;
  public String _value;
  
  public OFXUnknownData() {}
  
  public OFXUnknownData(String paramString1, String paramString2)
  {
    this._tag = paramString1;
    this._value = paramString2;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.ofx.interfaces.OFXUnknownData
 * JD-Core Version:    0.7.0.1
 */