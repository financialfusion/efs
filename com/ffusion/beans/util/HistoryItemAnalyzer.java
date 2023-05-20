package com.ffusion.beans.util;

import com.ffusion.util.beans.LocalizableString;

public abstract interface HistoryItemAnalyzer
{
  public abstract boolean equal(Object paramObject1, Object paramObject2);
  
  public abstract String getDisplayString(Object paramObject);
  
  public abstract LocalizableString buildLocalizableDisplayString(Object paramObject);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.HistoryItemAnalyzer
 * JD-Core Version:    0.7.0.1
 */