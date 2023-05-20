package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.bpw.enums.FrequencyType;

public class RecPmtInfo
  extends PmtInfo
{
  public String NameOnAcct;
  public String DateCreate;
  public double InitialAmt;
  private String nh;
  public double FinalAmt;
  public String _finalAmount;
  public int Frequency;
  public int EndDate;
  public int InstanceCount;
  
  public RecPmtInfo() {}
  
  public RecPmtInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt1, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, int paramInt2, int paramInt3, int paramInt4, int paramInt5, String paramString17)
  {
    this.RecSrvrTID = paramString1;
    this.CustomerID = paramString2;
    this.FIID = paramString3;
    this.PayeeID = paramString4;
    this.PayAcct = paramString5;
    this.NameOnAcct = paramString6;
    this.PayeeListID = paramInt1;
    setAmt(String.valueOf(paramString7));
    this.BankID = paramString8;
    this.AcctDebitID = paramString9;
    this.AcctDebitType = paramString10;
    this.Memo = paramString11;
    this.ExtdPmtInfo = paramString12;
    this.DateCreate = paramString13;
    this.CurDef = paramString14;
    setInitialAmount(paramString15);
    setFinalAmount(paramString16);
    setRecFrequency(paramInt2);
    this.StartDate = paramInt3;
    this.EndDate = paramInt4;
    this.InstanceCount = paramInt5;
    this.Status = paramString17;
  }
  
  public RecPmtInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt1, double paramDouble1, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, double paramDouble2, double paramDouble3, int paramInt2, int paramInt3, int paramInt4, int paramInt5, String paramString14)
  {
    this.RecSrvrTID = paramString1;
    this.CustomerID = paramString2;
    this.FIID = paramString3;
    this.PayeeID = paramString4;
    this.PayAcct = paramString5;
    this.NameOnAcct = paramString6;
    this.PayeeListID = paramInt1;
    this.Amount = paramDouble1;
    this.BankID = paramString7;
    this.AcctDebitID = paramString8;
    this.AcctDebitType = paramString9;
    this.Memo = paramString10;
    this.ExtdPmtInfo = paramString11;
    this.DateCreate = paramString12;
    this.CurDef = paramString13;
    this.InitialAmt = paramDouble2;
    this.FinalAmt = paramDouble3;
    setRecFrequency(paramInt2);
    this.StartDate = paramInt3;
    this.EndDate = paramInt4;
    this.InstanceCount = paramInt5;
    this.Status = paramString14;
  }
  
  public RecPmtInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt1, double paramDouble1, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, double paramDouble2, double paramDouble3, int paramInt2, int paramInt3, int paramInt4, int paramInt5, String paramString14, String paramString15, String paramString16)
  {
    this.RecSrvrTID = paramString1;
    this.CustomerID = paramString2;
    this.FIID = paramString3;
    this.PayeeID = paramString4;
    this.PayAcct = paramString5;
    this.NameOnAcct = paramString6;
    this.PayeeListID = paramInt1;
    this.Amount = paramDouble1;
    this.BankID = paramString7;
    this.AcctDebitID = paramString8;
    this.AcctDebitType = paramString9;
    this.Memo = paramString10;
    this.ExtdPmtInfo = paramString11;
    this.DateCreate = paramString12;
    this.CurDef = paramString13;
    this.InitialAmt = paramDouble2;
    this.FinalAmt = paramDouble3;
    setRecFrequency(paramInt2);
    this.StartDate = paramInt3;
    this.EndDate = paramInt4;
    this.InstanceCount = paramInt5;
    this.Status = paramString14;
    setLogId(paramString15);
    setSubmittedBy(paramString16);
  }
  
  public RecPmtInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt1, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, int paramInt2, int paramInt3, int paramInt4, int paramInt5, String paramString17, String paramString18, String paramString19)
  {
    this.RecSrvrTID = paramString1;
    this.CustomerID = paramString2;
    this.FIID = paramString3;
    this.PayeeID = paramString4;
    this.PayAcct = paramString5;
    this.NameOnAcct = paramString6;
    this.PayeeListID = paramInt1;
    setAmt(paramString7);
    this.BankID = paramString8;
    this.AcctDebitID = paramString9;
    this.AcctDebitType = paramString10;
    this.Memo = paramString11;
    this.ExtdPmtInfo = paramString12;
    this.DateCreate = paramString13;
    this.CurDef = paramString14;
    setInitialAmount(paramString15);
    setFinalAmount(paramString16);
    setRecFrequency(paramInt2);
    this.StartDate = paramInt3;
    this.EndDate = paramInt4;
    this.InstanceCount = paramInt5;
    this.Status = paramString17;
    setLogId(paramString18);
    setSubmittedBy(paramString19);
  }
  
  public String getDateCreate()
  {
    return this.DateCreate;
  }
  
  public void setDateCreate(String paramString)
  {
    this.DateCreate = paramString;
  }
  
  public int getEndDate()
  {
    return this.EndDate;
  }
  
  public void setEndDate(int paramInt)
  {
    this.EndDate = paramInt;
  }
  
  public double getFinalAmt()
  {
    return this.FinalAmt;
  }
  
  public String getFinalAmount()
  {
    return this._finalAmount != null ? this._finalAmount : String.valueOf(this.FinalAmt);
  }
  
  public void setFinalAmt(double paramDouble)
  {
    this.FinalAmt = paramDouble;
  }
  
  public void setFinalAmount(String paramString)
  {
    this._finalAmount = paramString;
    this.FinalAmt = (paramString != null ? Double.parseDouble(paramString) : 0.0D);
  }
  
  public void setFrequency(int paramInt)
  {
    setRecFrequency(paramInt);
  }
  
  public FrequencyType getRecFrequency()
  {
    return super.getRecFrequency() != null ? super.getRecFrequency() : FrequencyType.getByIFXFreq(this.Frequency);
  }
  
  public void setRecFrequency(FrequencyType paramFrequencyType)
  {
    super.setRecFrequency(paramFrequencyType);
    this.Frequency = (paramFrequencyType != null ? paramFrequencyType.getIFXFreq() : 0);
  }
  
  public double getInitialAmt()
  {
    return this.InitialAmt;
  }
  
  public String getInitialAmount()
  {
    return this.nh != null ? this.nh : String.valueOf(this.InitialAmt);
  }
  
  public void setInitialAmt(double paramDouble)
  {
    this.InitialAmt = paramDouble;
  }
  
  public void setInitialAmount(String paramString)
  {
    this.nh = paramString;
    this.InitialAmt = (paramString != null ? Double.parseDouble(paramString) : 0.0D);
  }
  
  public int getInstanceCount()
  {
    return this.InstanceCount;
  }
  
  public void setInstanceCount(int paramInt)
  {
    this.InstanceCount = paramInt;
  }
  
  public String getNameOnAcct()
  {
    return this.NameOnAcct;
  }
  
  public void setNameOnAcct(String paramString)
  {
    this.NameOnAcct = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.RecPmtInfo
 * JD-Core Version:    0.7.0.1
 */