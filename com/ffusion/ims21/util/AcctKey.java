package com.ffusion.ims21.util;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import java.util.Iterator;

public class AcctKey
{
  static final int jdField_do = 127;
  static final int a = 0;
  static final int jdField_for = 8;
  static final int jdField_if = 9;
  protected int acctId = 0;
  protected int acctSign = 0;
  protected int acctBal = 0;
  
  public AcctKey(int paramInt)
  {
    setValue(paramInt);
  }
  
  public AcctKey() {}
  
  public String getHexValue()
  {
    return Integer.toHexString(getValue());
  }
  
  public int getValue()
  {
    int i = 0;
    i |= (0x7F & this.acctId) << 0;
    i |= (0x1 & this.acctSign) << 8;
    i |= (0xFFFFFF & this.acctBal) << 9;
    return i;
  }
  
  public void setValue(int paramInt)
  {
    this.acctId = (0x7F & paramInt >>> 0);
    this.acctSign = (0x1 & paramInt >>> 8);
    this.acctBal = (0xFFFFFF & paramInt >>> 9);
  }
  
  public int getAcctId()
  {
    return this.acctId;
  }
  
  public void setAcctId(int paramInt)
  {
    this.acctId = (0x7F & paramInt);
  }
  
  public int getAcctBal()
  {
    if (this.acctSign == 1) {
      return this.acctBal * -1;
    }
    return this.acctBal;
  }
  
  public void setAcctBal(int paramInt)
  {
    if (paramInt < 0)
    {
      this.acctSign = 1;
      this.acctBal = (paramInt * -1);
    }
    else
    {
      this.acctSign = 0;
      this.acctBal = paramInt;
    }
  }
  
  public String toString()
  {
    return "\n\t\tacctId = " + getAcctId() + "\n \tacctBal = " + getAcctBal() + "\n \tKey = 0x" + Integer.toHexString(getValue());
  }
  
  public static String getAccountsURI(Accounts paramAccounts)
  {
    String str = null;
    int[] arrayOfInt = new int[127];
    for (int i = 0; i < arrayOfInt.length; i++) {
      arrayOfInt[i] = 0;
    }
    Iterator localIterator = paramAccounts.iterator();
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = (Account)localIterator.next();
      int j = ((Account)localObject).getTypeValue();
      Balance localBalance = ((Account)localObject).getCurrentBalance();
      if (localBalance == null)
      {
        localBalance = ((Account)localObject).getAvailableBalance();
        if (localBalance == null) {}
      }
      else
      {
        Currency localCurrency = localBalance.getAmountValue();
        int k = (int)localCurrency.doubleValue();
        k /= 100;
        if (j < 127) {
          arrayOfInt[j] += k;
        }
      }
    }
    for (i = 0; i < arrayOfInt.length; i++) {
      if (arrayOfInt[i] != 0)
      {
        localObject = new AcctKey();
        ((AcctKey)localObject).setAcctId(i);
        ((AcctKey)localObject).setAcctBal(arrayOfInt[i]);
        if (str == null) {
          str = "";
        } else {
          str = str + "-";
        }
        str = str + Integer.toHexString(((AcctKey)localObject).getValue());
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.util.AcctKey
 * JD-Core Version:    0.7.0.1
 */