package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.util.enums.UserAssignedAmount;

public class RecTransferInfo
  extends TransferInfo
{
  protected String um = null;
  protected String uo = null;
  protected String un = null;
  protected int uq = 1;
  protected String[] up = null;
  
  public RecTransferInfo() {}
  
  public RecTransferInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18)
  {
    super(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramString13, paramString14, paramString15, paramString16, paramString17, paramString18);
  }
  
  public RecTransferInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, UserAssignedAmount paramUserAssignedAmount, boolean paramBoolean, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, String paramString19, String paramString20, String paramString21)
  {
    super(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramString13, paramUserAssignedAmount, paramBoolean, paramString14, paramString15, paramString16, paramString17, paramString18, paramString19, paramString20, paramString21);
  }
  
  public void populate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, int paramInt, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18)
  {
    this.tq = paramString1;
    this.ud = paramString2;
    this.tX = paramString3;
    this.tO = paramString16;
    this.tr = paramString4;
    this.t2 = paramString5;
    this.tJ = paramString6;
    this.tF = paramString7;
    this.ue = paramString8;
    this.tG = paramString9;
    this.tC = paramString10;
    this.um = paramString11;
    this.uo = paramString12;
    this.un = paramString13;
    this.uq = paramInt;
    this.ul = paramString15;
    this.logId = paramString14;
    this.submittedBy = paramString17;
    this.uk = paramString18;
  }
  
  public String getStartDate()
  {
    return this.um;
  }
  
  public void setStartDate(String paramString)
  {
    this.um = paramString;
  }
  
  public String getEndDate()
  {
    return this.uo;
  }
  
  public void setEndDate(String paramString)
  {
    this.uo = paramString;
  }
  
  public String getFrequency()
  {
    return this.un;
  }
  
  public void setFrequency(String paramString)
  {
    this.un = paramString;
  }
  
  public int getPmtsCount()
  {
    return this.uq;
  }
  
  public void setPmtsCount(int paramInt)
  {
    this.uq = paramInt;
  }
  
  public String[] getSingleIds()
  {
    return this.up;
  }
  
  public void setSingleIds(String[] paramArrayOfString)
  {
    this.up = paramArrayOfString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(super.toString()).append(", ");
    localStringBuffer.append("startDate=").append(this.um).append(", ");
    localStringBuffer.append("endDate=").append(this.uo).append(", ");
    localStringBuffer.append("frequency=").append(this.un).append(", ");
    localStringBuffer.append("pmtsCount=").append(this.uq).append(", ");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.RecTransferInfo
 * JD-Core Version:    0.7.0.1
 */