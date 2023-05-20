package com.ffusion.util.logging;

import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.Sortable;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class PerfLogRecord
  extends LogRecord
  implements Sortable
{
  private long aJ;
  private boolean aN;
  private long aK;
  private int aI;
  private int aM;
  private int aL;
  
  public PerfLogRecord(String paramString, long paramLong, boolean paramBoolean)
  {
    super(Level.SEVERE, paramString);
    this.aJ = paramLong;
    this.aN = paramBoolean;
    this.aK = (System.currentTimeMillis() - paramLong);
    String str = paramString + DateFormatUtil.getFormatter("MMddyyHH").format(new Date(paramLong));
    this.aI = str.hashCode();
    if (paramBoolean)
    {
      this.aM = 1;
      this.aL = 0;
    }
    else
    {
      this.aM = 0;
      this.aL = 1;
    }
  }
  
  public void add(PerfLogRecord paramPerfLogRecord)
  {
    this.aM += paramPerfLogRecord.aM;
    this.aL += paramPerfLogRecord.aL;
    this.aK += paramPerfLogRecord.aK;
  }
  
  public long getDuration()
  {
    return this.aK;
  }
  
  public void setDuration(long paramLong)
  {
    this.aK = paramLong;
  }
  
  public int getHashcode()
  {
    return this.aI;
  }
  
  public void setHashcode(int paramInt)
  {
    this.aI = paramInt;
  }
  
  public long getStartTime()
  {
    return this.aJ;
  }
  
  public void setStartTime(long paramLong)
  {
    this.aJ = paramLong;
  }
  
  public int getSuccessCount()
  {
    return this.aM;
  }
  
  public int getFailCount()
  {
    return this.aL;
  }
  
  public boolean getSuccess()
  {
    return this.aN;
  }
  
  public void setSuccess(boolean paramBoolean)
  {
    this.aN = paramBoolean;
  }
  
  public long getAverageResponseTime()
  {
    return this.aK / (this.aM + this.aL);
  }
  
  public boolean equals(Object paramObject)
  {
    return (getClass().equals(paramObject.getClass())) && (this.aI == paramObject.hashCode());
  }
  
  public int compare(Object paramObject, String paramString)
  {
    PerfLogRecord localPerfLogRecord = (PerfLogRecord)paramObject;
    if (this.aK > localPerfLogRecord.getDuration()) {
      return 1;
    }
    if (this.aK < localPerfLogRecord.getDuration()) {
      return -1;
    }
    return 0;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("module=" + getMessage() + ", startTime=" + getStartTime() + ", nSuccess=" + this.aM);
    localStringBuffer.append(", nFail=" + this.aL + ", avgRespTime=" + getAverageResponseTime());
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.PerfLogRecord
 * JD-Core Version:    0.7.0.1
 */