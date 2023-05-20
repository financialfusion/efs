package com.ffusion.ffs.bpw.interfaces;

public class CCCompanyAcctInfo
  extends BPWBankAcctInfo
{
  protected String jdField_null;
  protected String n;
  protected String o;
  
  public String getAcctId()
  {
    return this.jdField_null;
  }
  
  public void setAcctId(String paramString)
  {
    this.jdField_null = paramString;
  }
  
  public String getCompId()
  {
    return this.n;
  }
  
  public void setCompId(String paramString)
  {
    this.n = paramString;
  }
  
  public String getTransactionType()
  {
    return this.o;
  }
  
  public void setTransactionType(String paramString)
  {
    this.o = paramString;
  }
  
  public boolean isValidTranType()
  {
    if (this.o == null) {
      return false;
    }
    return (this.o.compareTo("CONCENTRATION") == 0) || (this.o.compareTo("DISBURSEMENT") == 0);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo
 * JD-Core Version:    0.7.0.1
 */