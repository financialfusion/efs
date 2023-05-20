package com.ffusion.webservices.beans;

public class BankIdentifier
  extends Bean
{
  protected String bankDirectoryType;
  protected String bankDirectoryId;
  
  public String getBankDirectoryType()
  {
    return this.bankDirectoryType;
  }
  
  public void setBankDirectoryType(String paramString)
  {
    this.bankDirectoryType = paramString;
  }
  
  public String getBankDirectoryId()
  {
    return this.bankDirectoryId;
  }
  
  public void setBankDirectoryId(String paramString)
  {
    this.bankDirectoryId = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.beans.BankIdentifier
 * JD-Core Version:    0.7.0.1
 */