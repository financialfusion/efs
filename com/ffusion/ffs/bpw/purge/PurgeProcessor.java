package com.ffusion.ffs.bpw.purge;

import com.ffusion.ffs.bpw.db.CleanupRequest;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Purge;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CleanupRequestInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class PurgeProcessor
  implements FFSConst, DBConsts, BPWResource
{
  private String c5;
  private int c7;
  private int c8;
  private int c3;
  private Thread c4 = null;
  private PurgeRunnable c2;
  private String c6 = null;
  
  public PurgeProcessor()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.c5 = localPropertyConfig.PurgeWakeupTime;
    this.c7 = localPropertyConfig.DayToPurgeClosedInstruction;
    this.c8 = localPropertyConfig.DayToPurgeEventHistory;
    this.c3 = localPropertyConfig.DayToPurgeActivityLog;
    this.c6 = localPropertyConfig.otherProperties.getProperty("bpw.transactions.cleanup.data.retention.enabled", "N");
  }
  
  public void start()
    throws Exception
  {
    this.c2 = new PurgeRunnable(jdMethod_null(this.c5), this.c7, this.c8, this.c3);
    this.c4 = new Thread(this.c2);
    this.c4.start();
  }
  
  public void stop()
    throws Exception
  {
    this.c2.stop();
    if (this.c4 != null) {
      try
      {
        this.c4.interrupt();
      }
      catch (Exception localException) {}
    }
  }
  
  private Calendar jdMethod_null(String paramString)
    throws Exception
  {
    DateFormat localDateFormat = DateFormat.getTimeInstance(3);
    try
    {
      localDateFormat.parse(paramString);
    }
    catch (ParseException localParseException1)
    {
      FFSDebug.log("PurgeProcessor failed to parse: " + paramString, 6);
      try
      {
        localDateFormat = DateFormat.getTimeInstance(3, Locale.US);
        localDateFormat.parse(paramString);
      }
      catch (ParseException localParseException2)
      {
        FFSDebug.log("PurgeProcessor failed to parse: " + paramString + " with US locale", 6);
        throw localParseException2;
      }
    }
    Calendar localCalendar1 = localDateFormat.getCalendar();
    Calendar localCalendar2 = Calendar.getInstance();
    int i = localCalendar2.get(11);
    int j = localCalendar2.get(12);
    int m = localCalendar1.get(11);
    int n = localCalendar1.get(12);
    int k = i * 60 + j;
    int i1 = m * 60 + n;
    int i4;
    if (i1 > k) {
      i4 = i1 - k;
    } else {
      i4 = i1 + 1440 - k;
    }
    int i2 = i4 / 60;
    int i3 = i4 % 60;
    localCalendar2.add(11, i2);
    localCalendar2.add(12, i3);
    localCalendar2.set(13, 0);
    FFSDebug.log("PurgeProcessor:computeNextWakeupTime=" + localCalendar2.getTime().toString(), 6);
    return localCalendar2;
  }
  
  public void cleanup(String paramString1, String paramString2, int paramInt, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "PurgeProcessor.cleanup(String customerID, String type, int ageInDays, HashMap extra): ";
    FFSDebug.log(str1, "Start ", 6);
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      FFSDebug.log(str1, "failed. type not specified", 0);
      throw new FFSException(str1 + "failed. type not specified");
    }
    if (paramInt < 0)
    {
      FFSDebug.log(str1, "failed. ageInDays is invalid", 0);
      throw new FFSException(str1 + "failed. ageInDays is invalid");
    }
    if (this.c6.trim().equalsIgnoreCase("N"))
    {
      localObject1 = str1 + "Failed: " + "Data retention is not enabled";
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException((String)localObject1);
    }
    Object localObject1 = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localObject1 = DBUtil.getConnection();
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = ((FFSConnection)localObject1);
      CleanupRequestInfo localCleanupRequestInfo = new CleanupRequestInfo();
      localCleanupRequestInfo.setCustomerId(paramString1);
      localCleanupRequestInfo.setPaymentTypeList(paramString2, ";");
      localCleanupRequestInfo.setAgeInDaysList(Integer.toString(paramInt), ";");
      localCleanupRequestInfo.setMemo(null);
      CleanupRequest.create(localFFSConnectionHolder, localCleanupRequestInfo);
      ((FFSConnection)localObject1).commit();
      Purge.cleanup(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      ((FFSConnection)localObject1).rollback();
      String str2 = "PurgeProcessor.cleanup Failed:  Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(str2);
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection((FFSConnection)localObject1);
      }
    }
  }
  
  public void cleanup(String paramString, ArrayList paramArrayList1, ArrayList paramArrayList2, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "PurgeProcessor.cleanup(String customerID, ArrayList typeList, ArrayList ageInDaysList, HashMap extra): ";
    FFSDebug.log(str1, "Start ", 6);
    if ((paramArrayList1 == null) || (paramArrayList1.size() == 0))
    {
      FFSDebug.log(str1, "failed. typeList not specified", 0);
      throw new FFSException(str1 + "failed. type not specified");
    }
    if ((paramArrayList2 == null) || (paramArrayList2.size() == 0))
    {
      FFSDebug.log(str1, "failed. ageInDaysList is invalid", 0);
      throw new FFSException(str1 + "failed. ageInDays is invalid");
    }
    if (this.c6.trim().equalsIgnoreCase("N"))
    {
      localObject1 = str1 + "Failed: " + "Data retention is not enabled";
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException((String)localObject1);
    }
    Object localObject1 = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    String[] arrayOfString1 = (String[])paramArrayList1.toArray(new String[paramArrayList1.size()]);
    String[] arrayOfString2 = (String[])paramArrayList2.toArray(new String[paramArrayList1.size()]);
    try
    {
      localObject1 = DBUtil.getConnection();
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = ((FFSConnection)localObject1);
      CleanupRequestInfo localCleanupRequestInfo = new CleanupRequestInfo();
      localCleanupRequestInfo.setCustomerId(paramString);
      localCleanupRequestInfo.setPaymentTypeList(arrayOfString1);
      localCleanupRequestInfo.setAgeInDaysList(arrayOfString2);
      localCleanupRequestInfo.setMemo(null);
      CleanupRequest.create(localFFSConnectionHolder, localCleanupRequestInfo);
      ((FFSConnection)localObject1).commit();
      Purge.cleanup(localFFSConnectionHolder);
      ((FFSConnection)localObject1).commit();
    }
    catch (Throwable localThrowable)
    {
      ((FFSConnection)localObject1).rollback();
      String str2 = "PurgeProcessor.cleanup Failed:  Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(str2);
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection((FFSConnection)localObject1);
      }
    }
  }
  
  public void cleanup(ArrayList paramArrayList1, ArrayList paramArrayList2, ArrayList paramArrayList3, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "PurgeProcessor.cleanup(ArrayList customerIDList, ArrayList typeList, ArrayList ageInDaysList, HashMap extra): ";
    FFSDebug.log(str1, "Start ", 6);
    if ((paramArrayList1 == null) || (paramArrayList1.size() == 0))
    {
      FFSDebug.log(str1, "failed. customerIDList not specified", 0);
      throw new FFSException(str1 + "failed. customerID not specified");
    }
    if ((paramArrayList2 == null) || (paramArrayList2.size() == 0))
    {
      FFSDebug.log(str1, "failed. typeList not specified", 0);
      throw new FFSException(str1 + "failed. type not specified");
    }
    if ((paramArrayList3 == null) || (paramArrayList3.size() == 0))
    {
      FFSDebug.log(str1, "failed. ageInDaysList is invalid", 0);
      throw new FFSException(str1 + "failed. ageInDays is invalid");
    }
    if (this.c6.trim().equalsIgnoreCase("N"))
    {
      localObject1 = str1 + "Failed: " + "Data retention is not enabled";
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException((String)localObject1);
    }
    Object localObject1 = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    String[] arrayOfString1 = (String[])paramArrayList2.toArray(new String[paramArrayList2.size()]);
    String[] arrayOfString2 = (String[])paramArrayList3.toArray(new String[paramArrayList2.size()]);
    try
    {
      localObject1 = DBUtil.getConnection();
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = ((FFSConnection)localObject1);
      CleanupRequestInfo localCleanupRequestInfo = new CleanupRequestInfo();
      for (int i = 0; i < paramArrayList1.size(); i++)
      {
        localCleanupRequestInfo.setCustomerId((String)paramArrayList1.get(i));
        localCleanupRequestInfo.setPaymentTypeList(arrayOfString1);
        localCleanupRequestInfo.setAgeInDaysList(arrayOfString2);
        localCleanupRequestInfo.setMemo(null);
        CleanupRequest.create(localFFSConnectionHolder, localCleanupRequestInfo);
      }
      ((FFSConnection)localObject1).commit();
      Purge.cleanup(localFFSConnectionHolder);
      ((FFSConnection)localObject1).commit();
    }
    catch (Throwable localThrowable)
    {
      ((FFSConnection)localObject1).rollback();
      String str2 = "PurgeProcessor.cleanup Failed:  Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(str2);
    }
    finally
    {
      if (localObject1 != null) {
        DBUtil.freeConnection((FFSConnection)localObject1);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.purge.PurgeProcessor
 * JD-Core Version:    0.7.0.1
 */