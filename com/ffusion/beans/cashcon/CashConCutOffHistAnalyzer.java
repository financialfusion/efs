package com.ffusion.beans.cashcon;

import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.beans.util.HistoryItemAnalyzer;
import com.ffusion.util.beans.LocalizableString;

class CashConCutOffHistAnalyzer
  implements HistoryItemAnalyzer
{
  public boolean equal(Object paramObject1, Object paramObject2)
  {
    CutOffTime localCutOffTime1 = (CutOffTime)paramObject1;
    CutOffTime localCutOffTime2 = (CutOffTime)paramObject2;
    String str1 = localCutOffTime1.getCutOffId();
    String str2 = localCutOffTime2.getCutOffId();
    if ((str1 == null) && (str2 == null)) {
      return true;
    }
    if (((str1 == null) && (str2 != null)) || ((str1 != null) && (str2 == null))) {
      return false;
    }
    return localCutOffTime1.getCutOffId().equals(localCutOffTime2.getCutOffId());
  }
  
  public String getDisplayString(Object paramObject)
  {
    CutOffTime localCutOffTime = (CutOffTime)paramObject;
    return localCutOffTime.getDayOfWeekString() + " " + localCutOffTime.getTimeOfDay();
  }
  
  public LocalizableString buildLocalizableDisplayString(Object paramObject)
  {
    CutOffTime localCutOffTime = (CutOffTime)paramObject;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = new LocalizableString("com.ffusion.beans.affiliatebank.resources", "Day" + localCutOffTime.getDayOfWeekValue(), null);
    arrayOfObject[1] = localCutOffTime.getTimeOfDay();
    return new LocalizableString("com.ffusion.beans.affiliatebank.resources", "CutOffDisplayText", arrayOfObject);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.CashConCutOffHistAnalyzer
 * JD-Core Version:    0.7.0.1
 */