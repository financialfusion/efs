package com.ffusion.beans.accounts;

public class AccountKey
{
  private String jdField_do = null;
  private String a = null;
  private String jdField_for = null;
  private int jdField_if = 0;
  
  public AccountKey(String paramString1, String paramString2, String paramString3)
  {
    this.jdField_do = paramString1;
    this.a = paramString2;
    this.jdField_for = paramString3;
    this.jdField_if = 0;
  }
  
  public AccountKey(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    this.jdField_do = paramString1;
    this.a = paramString2;
    this.jdField_for = paramString3;
    this.jdField_if = paramInt;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof AccountKey))) {
      return false;
    }
    AccountKey localAccountKey = (AccountKey)paramObject;
    if (((this.jdField_do != null) || (localAccountKey.jdField_do == null)) && ((this.a != null) || (localAccountKey.a == null))) {}
    return (((this.jdField_for == null ? 1 : 0) & (localAccountKey.jdField_for != null ? 1 : 0)) == 0) && (this.jdField_if == localAccountKey.jdField_if) && (this.jdField_do.equals(localAccountKey.jdField_do)) && (this.a.equals(localAccountKey.a)) && (this.jdField_for.equals(localAccountKey.jdField_for));
  }
  
  public int hashCode()
  {
    StringBuffer localStringBuffer = new StringBuffer(this.jdField_do);
    localStringBuffer.append(",");
    localStringBuffer.append(this.a);
    localStringBuffer.append(",");
    localStringBuffer.append(this.jdField_for);
    return localStringBuffer.toString().hashCode();
  }
  
  public String getAccountID()
  {
    return this.jdField_do;
  }
  
  public String getRoutingNumber()
  {
    return this.jdField_for;
  }
  
  public String getBankID()
  {
    return this.a;
  }
  
  public int getAccountType()
  {
    return this.jdField_if;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.AccountKey
 * JD-Core Version:    0.7.0.1
 */