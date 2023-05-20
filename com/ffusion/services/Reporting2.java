package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.reporting.RptException;
import java.util.HashMap;

public abstract interface Reporting2
  extends Reporting
{
  public abstract ReportIdentification getReport(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws RptException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Reporting2
 * JD-Core Version:    0.7.0.1
 */