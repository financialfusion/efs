package com.ffusion.reporting;

import java.util.HashMap;
import java.util.Locale;

public abstract interface IReportResult
{
  public abstract Object export(String paramString, HashMap paramHashMap);
  
  public abstract Locale getLocale();
  
  public abstract void setLocale(Locale paramLocale);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.reporting.IReportResult
 * JD-Core Version:    0.7.0.1
 */