package com.ffusion.ffs.util;

import java.io.Serializable;

public class FFSCache
  implements Serializable
{
  public String cacheId = null;
  
  public void reset()
  {
    this.cacheId = null;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSCache
 * JD-Core Version:    0.7.0.1
 */