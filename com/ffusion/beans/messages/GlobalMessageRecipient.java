package com.ffusion.beans.messages;

import com.ffusion.util.Sortable;

public class GlobalMessageRecipient
  implements Sortable, GlobalMessageConsts
{
  int ao6 = -1;
  int ao3 = -1;
  int ao1 = -1;
  int ao5 = -1;
  String ao2;
  String ao4;
  
  public GlobalMessageRecipient() {}
  
  public GlobalMessageRecipient(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, int paramInt4)
  {
    this.ao6 = paramInt1;
    this.ao3 = paramInt2;
    this.ao1 = paramInt3;
    this.ao2 = paramString1;
    this.ao4 = paramString2;
    this.ao5 = paramInt4;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    GlobalMessageRecipient localGlobalMessageRecipient = (GlobalMessageRecipient)paramObject;
    int i = 1;
    if (paramString.equals("RECIPIENTLISTID"))
    {
      if (this.ao6 == localGlobalMessageRecipient.getRcptListID()) {
        i = 0;
      } else {
        i = this.ao6 > localGlobalMessageRecipient.getRcptListID() ? 1 : -1;
      }
    }
    else if (paramString.equals("RECIPIENTTYPE"))
    {
      if (this.ao3 == localGlobalMessageRecipient.getRcptType()) {
        i = 0;
      } else {
        i = this.ao3 > localGlobalMessageRecipient.getRcptType() ? 1 : -1;
      }
    }
    else if (paramString.equals("RECIPIENTDIRID"))
    {
      if (this.ao1 == localGlobalMessageRecipient.getRcptDirID()) {
        i = 0;
      } else {
        i = this.ao1 > localGlobalMessageRecipient.getRcptDirID() ? 1 : -1;
      }
    }
    else if (paramString.equals("CUSTOMERID")) {
      i = this.ao2.compareTo(localGlobalMessageRecipient.getCustomerID());
    } else if ((paramString.equals("CUSTOMERNAME")) && (this.ao4 != null) && (localGlobalMessageRecipient.getCustomerName() != null)) {
      i = this.ao4.compareTo(localGlobalMessageRecipient.getCustomerName());
    }
    return i;
  }
  
  public String getCustomerID()
  {
    return this.ao2;
  }
  
  public void setCustomerID(String paramString)
  {
    this.ao2 = paramString;
  }
  
  public String getCustomerName()
  {
    return this.ao4;
  }
  
  public void setCustomerName(String paramString)
  {
    this.ao4 = paramString;
  }
  
  public int getRcptDirID()
  {
    return this.ao1;
  }
  
  public void setRcptDirID(int paramInt)
  {
    this.ao1 = paramInt;
  }
  
  public int getRcptListID()
  {
    return this.ao6;
  }
  
  public void setRcptListID(int paramInt)
  {
    this.ao6 = paramInt;
  }
  
  public int getRcptType()
  {
    return this.ao3;
  }
  
  public void setRcptType(int paramInt)
  {
    this.ao3 = paramInt;
  }
  
  public int getAffiliateBankID()
  {
    return this.ao5;
  }
  
  public void setAffiliateBankID(int paramInt)
  {
    this.ao5 = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.GlobalMessageRecipient
 * JD-Core Version:    0.7.0.1
 */