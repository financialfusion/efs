package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSUtil;

public class ACHAddendaInfo
  implements FFSConst, ACHConsts
{
  protected String zR = null;
  protected String zI = null;
  protected Object zE = null;
  protected String zN = null;
  protected String zQ = null;
  protected String zH = null;
  protected String zP = null;
  protected String zF = null;
  protected String zO = null;
  protected String zG = "MsgSet";
  protected String zL = "add";
  protected int zM = -1;
  protected String zK = null;
  protected String zJ = null;
  
  public String getLogId()
  {
    return this.zJ;
  }
  
  public void setLogId(String paramString)
  {
    this.zJ = paramString;
  }
  
  public String getAddendaId()
  {
    return this.zR;
  }
  
  public void setAddendaId(String paramString)
  {
    this.zR = paramString;
  }
  
  public String getRecordId()
  {
    return this.zI;
  }
  
  public void setRecordId(String paramString)
  {
    this.zI = paramString;
  }
  
  public Object getAddenda()
  {
    return this.zE;
  }
  
  public void setAddenda(Object paramObject)
  {
    this.zE = paramObject;
  }
  
  public String getAddendaContent()
  {
    return this.zN;
  }
  
  public void setAddendaContent(String paramString)
  {
    this.zN = paramString;
  }
  
  public String getClassCode()
  {
    return this.zQ;
  }
  
  public void setClassCode(String paramString)
  {
    this.zQ = paramString;
  }
  
  public String getSubmitDate()
  {
    return this.zH;
  }
  
  public void setSubmitDate(String paramString)
  {
    this.zH = paramString;
  }
  
  public String getSubmittedBy()
  {
    return this.zP;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.zP = paramString;
  }
  
  public String getStartDate()
  {
    return this.zF;
  }
  
  public void setStartDate(String paramString)
  {
    this.zF = paramString;
  }
  
  public String getAddendaStatus()
  {
    return this.zO;
  }
  
  public void setAddendaStatus(String paramString)
  {
    this.zO = paramString;
  }
  
  public String getAchVersion()
  {
    return this.zG;
  }
  
  public void setAchVersion(String paramString)
  {
    this.zG = paramString;
  }
  
  public String getAction()
  {
    return this.zL;
  }
  
  public void setAction(String paramString)
  {
    this.zL = paramString;
  }
  
  public int getStatusCode()
  {
    return this.zM;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.zM = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.zK;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.zK = paramString;
  }
  
  public short getFieldValueShort(String paramString)
  {
    return FFSUtil.getACHMBClassFieldValueShort(this.zE, paramString);
  }
  
  public int getFieldValueInt(String paramString)
  {
    return FFSUtil.getACHMBClassFieldValueInt(this.zE, paramString);
  }
  
  public long getFieldValueLong(String paramString)
  {
    return FFSUtil.getACHMBClassFieldValueLong(this.zE, paramString);
  }
  
  public String getFieldValueString(String paramString)
  {
    return FFSUtil.getACHMBClassFieldValueString(this.zE, paramString);
  }
  
  public Object getFieldValueObject(String paramString)
  {
    return FFSUtil.getACHMBClassFieldValueObject(this.zE, paramString);
  }
  
  public void setFieldValueObject(String paramString, Object paramObject)
  {
    try
    {
      FFSUtil.setACHMBClassFieldValueObject(this.zE, paramString, paramObject);
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHAddendaInfo
 * JD-Core Version:    0.7.0.1
 */