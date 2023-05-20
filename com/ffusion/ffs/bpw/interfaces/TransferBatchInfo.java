package com.ffusion.ffs.bpw.interfaces;

public class TransferBatchInfo
  extends BPWInfoBase
  implements Cloneable, TransferHistInfo
{
  protected String ur;
  protected String uw;
  protected String uC;
  protected String uy;
  protected String ut;
  protected String us;
  protected String ux;
  protected String uz;
  protected String uB;
  protected TransferInfo[] uA;
  protected String uv;
  protected String uu;
  protected long uD = 0L;
  
  public TransferBatchInfo() {}
  
  public TransferBatchInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, TransferInfo[] paramArrayOfTransferInfo)
  {
    this.ur = paramString1;
    this.uw = paramString2;
    this.uC = paramString3;
    this.uy = paramString4;
    this.ut = paramString5;
    this.us = paramString6;
    this.ux = paramString7;
    this.uz = paramString8;
    this.uB = paramString9;
    this.uA = paramArrayOfTransferInfo;
  }
  
  public String getBatchID()
  {
    return this.ur;
  }
  
  public void setBatchID(String paramString)
  {
    this.ur = paramString;
  }
  
  public String getFIID()
  {
    return this.uw;
  }
  
  public void setFIID(String paramString)
  {
    this.uw = paramString;
  }
  
  public String getCustomerId()
  {
    return this.uC;
  }
  
  public void setCustomerId(String paramString)
  {
    this.uC = paramString;
  }
  
  public String getBatchName()
  {
    return this.uy;
  }
  
  public void setBatchName(String paramString)
  {
    this.uy = paramString;
  }
  
  public String getBatchType()
  {
    return this.ut;
  }
  
  public void setBatchType(String paramString)
  {
    this.ut = paramString;
  }
  
  public String getTotalAmount()
  {
    return this.us;
  }
  
  public void setTotalAmount(String paramString)
  {
    this.us = paramString;
  }
  
  public String getAmountCurrency()
  {
    return this.ux;
  }
  
  public void setAmountCurrency(String paramString)
  {
    this.ux = paramString;
  }
  
  public String getTransferCount()
  {
    return this.uz;
  }
  
  public void setTransferCount(String paramString)
  {
    this.uz = paramString;
  }
  
  public String getMemo()
  {
    return this.uB;
  }
  
  public void setMemo(String paramString)
  {
    this.uB = paramString;
  }
  
  public TransferInfo[] getTransfers()
  {
    return this.uA;
  }
  
  public void setTransfers(TransferInfo[] paramArrayOfTransferInfo)
  {
    this.uA = paramArrayOfTransferInfo;
  }
  
  public String getBatchStatus()
  {
    return this.uv;
  }
  
  public void setBatchStatus(String paramString)
  {
    this.uv = paramString;
  }
  
  public String getTrnId()
  {
    return this.uu;
  }
  
  public void setTrnId(String paramString)
  {
    this.uu = paramString;
  }
  
  public long getRecordCursor()
  {
    return this.uD;
  }
  
  public void setRecordCursor(long paramLong)
  {
    this.uD = paramLong;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("batchID=").append(this.ur).append(", ");
    if (this.uw != null) {
      localStringBuffer.append("fiId=").append(this.uw).append(", ");
    } else {
      localStringBuffer.append("fiId=null").append(", ");
    }
    if (this.uC != null) {
      localStringBuffer.append("customerId=").append(this.uC).append(", ");
    } else {
      localStringBuffer.append("customerId=null").append(", ");
    }
    if (this.uy != null) {
      localStringBuffer.append("batchName=").append(this.uy).append(", ");
    } else {
      localStringBuffer.append("batchName=null").append(", ");
    }
    if (this.ut != null) {
      localStringBuffer.append("batchType=").append(this.ut).append(", ");
    } else {
      localStringBuffer.append("batchType=null").append(", ");
    }
    if (this.us != null) {
      localStringBuffer.append("totalAmount=").append(this.us).append(", ");
    } else {
      localStringBuffer.append("totalAmount=null").append(", ");
    }
    if (this.ux != null) {
      localStringBuffer.append("amountCurrency=").append(this.ux).append(", ");
    } else {
      localStringBuffer.append("amountCurrency=null").append(", ");
    }
    if (this.uz != null) {
      localStringBuffer.append("transferCount=").append(this.uz).append(", ");
    } else {
      localStringBuffer.append("transferCount=null").append(", ");
    }
    if (this.uB != null) {
      localStringBuffer.append("memo=").append(this.uB).append(", ");
    } else {
      localStringBuffer.append("memo=null").append(", ");
    }
    if (this.logId != null) {
      localStringBuffer.append("logId=").append(this.logId).append(", ");
    } else {
      localStringBuffer.append("logId=null").append(", ");
    }
    if (this.submittedBy != null) {
      localStringBuffer.append("submittedBy=").append(this.submittedBy).append(", ");
    } else {
      localStringBuffer.append("submittedBy=null").append(", ");
    }
    if (this.uA != null) {
      for (int i = 0; i < this.uA.length; i++) {
        if (this.uA[i] != null) {
          localStringBuffer.append("transfers[" + i + "]" + "=").append(this.uA[i]).append(", ");
        } else {
          localStringBuffer.append("transfers[" + i + "]" + "=null,");
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  public Object clone()
  {
    try
    {
      TransferBatchInfo localTransferBatchInfo = (TransferBatchInfo)super.clone();
      TransferInfo[] arrayOfTransferInfo = (TransferInfo[])this.uA.clone();
      localTransferBatchInfo.setTransfers(arrayOfTransferInfo);
      return localTransferBatchInfo;
    }
    catch (Exception localException) {}
    return null;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.TransferBatchInfo
 * JD-Core Version:    0.7.0.1
 */