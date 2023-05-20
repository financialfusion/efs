package com.ffusion.csil.core;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bcreport.ReportLogRecords;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.ReportAuditAdapter;
import com.ffusion.ffs.bpw.BPWServices.BPWServices;
import com.ffusion.services.InitFileHandler;
import com.ffusion.util.ContextPool;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import com.microstar.xml.HandlerBase;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

public class AuditLog
  extends Initialize
{
  private static BPWServices aCe = null;
  private static final String aB9 = "CallBackBeanURL";
  private static final String aB6 = "ContextFactory";
  private static final String aCa = "ContextUserName";
  private static final String aCh = "ContextPassword";
  private static final String aCf = "BPWCallBackJNDIName";
  private static final String aCg = "ContextProperty";
  private static String aCc = "BPWServices";
  private static String aCb;
  private static String aB7 = "system";
  private static String aCd = "weblogic";
  private static String aB5;
  private static HashMap aB8 = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.AuditLog.initialize");
    try
    {
      InitFileHandler localInitFileHandler = new InitFileHandler();
      localInitFileHandler.initialize("bptw.xml", new a());
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Unable to initialize audit log service.", localException);
      throw new CSILException(-1007, localException);
    }
  }
  
  public static ReportLogRecords getAuditEntries(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, DateTime paramDateTime1, DateTime paramDateTime2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AuditLog.getAuditEntries";
    ReportLogRecords localReportLogRecords = new ReportLogRecords();
    long l = System.currentTimeMillis();
    localReportLogRecords = ReportAuditAdapter.getAuditEntries(paramSecureUser, paramString1, paramString2, paramString3, paramDateTime1, paramDateTime2);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localReportLogRecords;
  }
  
  public static void flushAuditLog() {}
  
  protected static ContextPool getContextPool()
  {
    ContextPool localContextPool = null;
    try
    {
      DebugLog.log(Level.INFO, "provider_url = " + aCb);
      try
      {
        localContextPool = new ContextPool(aCb, aB7, aCd, aB5);
        localContextPool.setInitialContexts(5);
        localContextPool.setIncrementalContexts(1);
        localContextPool.setMaxContexts(50);
        if (aB8 != null)
        {
          Iterator localIterator = aB8.entrySet().iterator();
          Properties localProperties = localContextPool.getProperties();
          while (localIterator.hasNext())
          {
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            localProperties.put(localEntry.getKey(), localEntry.getValue().toString());
          }
        }
        localContextPool.createPool();
      }
      catch (Throwable localThrowable1)
      {
        DebugLog.throwing("Error: " + localThrowable1.toString(), localThrowable1);
        throw localThrowable1;
      }
    }
    catch (Throwable localThrowable2)
    {
      DebugLog.throwing("Couldn't get handler in bptw service", localThrowable2);
    }
    return localContextPool;
  }
  
  public static class a
    extends HandlerBase
  {
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("BPWCallBackJNDIName"))
      {
        AuditLog.access$002(paramString2);
      }
      else if (paramString1.equals("CallBackBeanURL"))
      {
        AuditLog.access$102(paramString2);
      }
      else if (paramString1.equals("ContextFactory"))
      {
        AuditLog.access$202(paramString2);
      }
      else if (paramString1.equals("ContextUserName"))
      {
        AuditLog.access$302(paramString2);
      }
      else if (paramString1.equals("ContextPassword"))
      {
        AuditLog.access$402(paramString2);
      }
      else if (paramString1.equals("ContextProperty"))
      {
        if (AuditLog.aB8 == null) {
          AuditLog.access$502(new HashMap());
        }
        String str1 = null;
        String str2 = null;
        int i = paramString2.indexOf("=");
        if (i > 0)
        {
          str1 = paramString2.substring(0, i);
          str2 = paramString2.substring(i + 1);
          AuditLog.aB8.put(str1, str2);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.AuditLog
 * JD-Core Version:    0.7.0.1
 */