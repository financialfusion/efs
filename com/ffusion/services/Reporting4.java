package com.ffusion.services;

import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.RptException;
import java.util.HashMap;
import java.util.Locale;

public abstract interface Reporting4
  extends Reporting3
{
  public abstract ReportCriteria getDefaultReportCriteria(String paramString, Locale paramLocale, HashMap paramHashMap)
    throws RptException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Reporting4
 * JD-Core Version:    0.7.0.1
 */