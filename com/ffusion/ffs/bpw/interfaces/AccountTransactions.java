package com.ffusion.ffs.bpw.interfaces;

public class AccountTransactions
  extends BPWInfoBase
  implements Cloneable
{
  protected ExtTransferAcctInfo qa = null;
  protected String qc = "TRANSFERANDTEMPLATE";
  protected String qb = "FromAccountAndToAccount";
  protected int p8 = 0;
  protected TransferInfo[] p9 = null;
  protected long qd = -1L;
  protected int statusCode = -1;
  protected String statusMsg;
  
  public ExtTransferAcctInfo getSearchForAccount()
  {
    return this.qa;
  }
  
  public void setSearchForAccount(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    this.qa = paramExtTransferAcctInfo;
  }
  
  public String getTransactionType()
  {
    return this.qc;
  }
  
  public void setTransactionType(String paramString)
  {
    this.qc = paramString;
  }
  
  public String getAccountScope()
  {
    return this.qb;
  }
  
  public void setAccountScope(String paramString)
  {
    this.qb = paramString;
  }
  
  public int getIncludeTransactions()
  {
    return this.p8;
  }
  
  public void setIncludeTransactions(int paramInt)
  {
    this.p8 = paramInt;
  }
  
  public TransferInfo[] getTransferInfo()
  {
    return this.p9;
  }
  
  public void setTransferInfo(TransferInfo[] paramArrayOfTransferInfo)
  {
    this.p9 = paramArrayOfTransferInfo;
  }
  
  public long getTotalTransactions()
  {
    return this.qd;
  }
  
  public void setTotalTransactions(long paramLong)
  {
    this.qd = paramLong;
  }
  
  public int getStatusCode()
  {
    return this.statusCode;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.statusCode = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.statusMsg;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.statusMsg = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.AccountTransactions
 * JD-Core Version:    0.7.0.1
 */