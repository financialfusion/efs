package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.util.FFSUtil;
import java.util.Hashtable;

public class RecWireInfo
  extends WireInfo
{
  protected String wI = null;
  protected String wL = null;
  protected String wK = null;
  protected int wO = 1;
  protected String wJ = null;
  protected String wN = null;
  protected String[] wM;
  
  public String getStartDate()
  {
    return this.wI;
  }
  
  public void setStartDate(String paramString)
  {
    this.wI = paramString;
  }
  
  public String getEndDate()
  {
    return this.wL;
  }
  
  public void setEndDate(String paramString)
  {
    this.wL = paramString;
  }
  
  public String getFrequency()
  {
    return this.wK;
  }
  
  public void setFrequency(String paramString)
  {
    this.wK = paramString;
  }
  
  public int getPmtsCount()
  {
    return this.wO;
  }
  
  public void setPmtsCount(int paramInt)
  {
    this.wO = paramInt;
  }
  
  public String getStartAmount()
  {
    return this.wJ;
  }
  
  public void setStartAmount(String paramString)
  {
    this.wJ = paramString;
  }
  
  public String getEndAmount()
  {
    return this.wN;
  }
  
  public void setEndAmount(String paramString)
  {
    this.wN = paramString;
  }
  
  public String[] getSingleIds()
  {
    return this.wM;
  }
  
  public void setSingleIds(String[] paramArrayOfString)
  {
    this.wM = paramArrayOfString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(super.toString()).append(", ");
    localStringBuffer.append("startDate=").append(this.wI).append(", ");
    localStringBuffer.append("endDate=").append(this.wL).append(", ");
    localStringBuffer.append("frequency=").append(this.wK).append(", ");
    localStringBuffer.append("pmtsCount=").append(this.wO).append(", ");
    if (this.wJ != null) {
      localStringBuffer.append("startAmount=").append(this.wJ).append(", ");
    } else {
      localStringBuffer.append("startAmount=").append(", ");
    }
    if (this.wN != null) {
      localStringBuffer.append("endAmount=").append(this.wN);
    } else {
      localStringBuffer.append("endAmount=");
    }
    return localStringBuffer.toString();
  }
  
  public RecWireInfo getRecWireInfo(String paramString)
  {
    Hashtable localHashtable = FFSUtil.stringToHashtable(paramString);
    return getRecWireInfo(localHashtable);
  }
  
  public RecWireInfo getRecWireInfo(Hashtable paramHashtable)
  {
    if (paramHashtable == null) {
      return this;
    }
    super.getWireInfo(paramHashtable);
    this.wI = ((String)paramHashtable.get("startDate"));
    this.wL = ((String)paramHashtable.get("endDate"));
    this.wK = ((String)paramHashtable.get("frequency"));
    this.wO = Integer.parseInt((String)paramHashtable.get("pmtsCount"));
    this.wJ = ((String)paramHashtable.get("startAmount"));
    this.wN = ((String)paramHashtable.get("endAmount"));
    return this;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.RecWireInfo
 * JD-Core Version:    0.7.0.1
 */