package com.ffusion.beans.cashcon;

import com.ffusion.beans.util.HistoryItemAnalyzer;
import com.ffusion.util.beans.LocalizableString;

class CashConAccountHistAnalyzer
  implements HistoryItemAnalyzer
{
  public boolean equal(Object paramObject1, Object paramObject2)
  {
    CashConAccount localCashConAccount1 = (CashConAccount)paramObject1;
    CashConAccount localCashConAccount2 = (CashConAccount)paramObject2;
    String str1 = localCashConAccount1.getBPWID();
    String str2 = localCashConAccount2.getBPWID();
    if ((str1 == null) && (str2 == null)) {
      return true;
    }
    if (((str1 == null) && (str2 != null)) || ((str1 != null) && (str2 == null))) {
      return false;
    }
    return localCashConAccount1.getBPWID().equals(localCashConAccount2.getBPWID());
  }
  
  public String getDisplayString(Object paramObject)
  {
    CashConAccount localCashConAccount = (CashConAccount)paramObject;
    return localCashConAccount.getBankName() + " - " + localCashConAccount.getNumber() + " - " + localCashConAccount.getTypeString();
  }
  
  public LocalizableString buildLocalizableDisplayString(Object paramObject)
  {
    CashConAccount localCashConAccount = (CashConAccount)paramObject;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = localCashConAccount.getBankName();
    arrayOfObject[1] = localCashConAccount.buildLocalizableAccountID();
    return new LocalizableString("com.ffusion.beans.cashcon.resources", "CashConAccountDisplayText", arrayOfObject);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.CashConAccountHistAnalyzer
 * JD-Core Version:    0.7.0.1
 */