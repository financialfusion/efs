package com.ffusion.beans.accountgroups;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.util.HistoryItemAnalyzer;
import com.ffusion.util.beans.LocalizableString;

class AccountHistAnalyzer
  implements HistoryItemAnalyzer
{
  public boolean equal(Object paramObject1, Object paramObject2)
  {
    Account localAccount1 = (Account)paramObject1;
    Account localAccount2 = (Account)paramObject2;
    return localAccount1.equals(localAccount2);
  }
  
  public String getDisplayString(Object paramObject)
  {
    Account localAccount = (Account)paramObject;
    return localAccount.getBankName() + " - " + localAccount.getNumber() + " - " + localAccount.getType();
  }
  
  public LocalizableString buildLocalizableDisplayString(Object paramObject)
  {
    Account localAccount = (Account)paramObject;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = localAccount.getBankName();
    arrayOfObject[1] = localAccount.buildLocalizableAccountID();
    return new LocalizableString("com.ffusion.beans.accounts.resources", "AccountGroupDisplayText", arrayOfObject);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accountgroups.AccountHistAnalyzer
 * JD-Core Version:    0.7.0.1
 */