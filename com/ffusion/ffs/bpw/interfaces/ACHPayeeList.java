package com.ffusion.ffs.bpw.interfaces;

public class ACHPayeeList
  extends BPWPayeeList
{
  protected String[] jdField_char = null;
  protected String jdField_try = null;
  protected String jdField_byte = null;
  protected String jdField_case = null;
  
  public ACHPayeeList()
  {
    this.payeeCategory = "BPW_PAYEE_CATEGORY_ACH";
  }
  
  public String[] getCompIdList()
  {
    return this.jdField_char;
  }
  
  public void setCompIdList(String[] paramArrayOfString)
  {
    this.jdField_char = paramArrayOfString;
  }
  
  public void setPrenoteCreditStatus(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public String getPrenoteCreditStatus()
  {
    return this.jdField_try;
  }
  
  public void setPrenoteDateStart(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public String getPrenoteDateStart()
  {
    return this.jdField_byte;
  }
  
  public void setPrenoteDateEnd(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public String getPrenoteDateEnd()
  {
    return this.jdField_case;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHPayeeList
 * JD-Core Version:    0.7.0.1
 */