package com.ffusion.util.smartcalendar.adapter;

import com.ffusion.util.beans.BankIdentifier;

class SCCalendarAssociationKey
{
  private BankIdentifier jdField_if;
  private String jdField_do;
  private int a;
  
  public SCCalendarAssociationKey(BankIdentifier paramBankIdentifier, String paramString)
  {
    this.jdField_if = paramBankIdentifier;
    this.jdField_do = paramString;
    a();
  }
  
  public BankIdentifier getBankIdentifier()
  {
    return this.jdField_if;
  }
  
  public String getServiceBureauId()
  {
    return this.jdField_do;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof SCCalendarAssociationKey))
    {
      SCCalendarAssociationKey localSCCalendarAssociationKey = (SCCalendarAssociationKey)paramObject;
      if (localSCCalendarAssociationKey == this) {
        return true;
      }
      BankIdentifier localBankIdentifier1 = localSCCalendarAssociationKey.getBankIdentifier();
      BankIdentifier localBankIdentifier2 = getBankIdentifier();
      if ((localBankIdentifier1 != null) && (localBankIdentifier2 != null))
      {
        if (!localBankIdentifier1.equals(localBankIdentifier2)) {
          return false;
        }
      }
      else if (localBankIdentifier1 != localBankIdentifier2) {
        return false;
      }
      String str1 = localSCCalendarAssociationKey.getServiceBureauId();
      String str2 = getServiceBureauId();
      if ((str1 != null) && (str2 != null))
      {
        if (!str1.equalsIgnoreCase(str2)) {
          return false;
        }
      }
      else if (str1 != str2) {
        return false;
      }
    }
    else
    {
      return false;
    }
    return true;
  }
  
  public int hashCode()
  {
    return this.a;
  }
  
  private void a()
  {
    this.a = (this.jdField_if.hashCode() * 3 + this.jdField_do.hashCode() * 5);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.smartcalendar.adapter.SCCalendarAssociationKey
 * JD-Core Version:    0.7.0.1
 */