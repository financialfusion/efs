package com.ffusion.beans;

import java.util.Comparator;

public class TransactionType
  extends ExtendABean
{
  private int a5S;
  private String a5R;
  
  public TransactionType(int paramInt, String paramString)
  {
    this.a5S = paramInt;
    this.a5R = paramString;
  }
  
  public int getId()
  {
    return this.a5S;
  }
  
  public void setId(int paramInt)
  {
    this.a5S = paramInt;
  }
  
  public String getName()
  {
    return this.a5R;
  }
  
  public void setName(String paramString)
  {
    this.a5R = paramString;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    if (!(paramObject instanceof TransactionType)) {
      return false;
    }
    TransactionType localTransactionType = (TransactionType)paramObject;
    if ((this.a5R == null) && (localTransactionType.getName() == null)) {
      return this.a5S == localTransactionType.getId();
    }
    if ((this.a5R != null) && (localTransactionType.getName() != null)) {
      return (this.a5S == localTransactionType.getId()) && (this.a5R.equals(localTransactionType.getName()));
    }
    return false;
  }
  
  public static Comparator getDescNameComparator()
  {
    new Comparator()
    {
      public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        TransactionType localTransactionType1 = (TransactionType)paramAnonymousObject1;
        TransactionType localTransactionType2 = (TransactionType)paramAnonymousObject2;
        return localTransactionType1.a5R.compareTo(localTransactionType2.a5R);
      }
    };
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.TransactionType
 * JD-Core Version:    0.7.0.1
 */