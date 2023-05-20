package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.IAEAuditInfo;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class AuditInfo
  implements IAEAuditInfo
{
  private static final String jdField_case = "alertId,alertSequence,channelId,deliveryInfoId,auditTime,auditType,status,timeInfo,deliveryOrder,channelName,deliveryString,maxDelay,properties,appId,appName,userId";
  private static final String jdField_int = "ai.alertId,ai.alertSequence,ai.channelId,ai.deliveryInfoId,ai.auditTime,ai.auditType,ai.status,ai.timeInfo,ai.deliveryOrder,ai.channelName,ai.deliveryString,ai.maxDelay,ai.properties,ai.appId,ai.appName,ai.userId";
  private static final String a = "insert into AE_AuditLog({|||id,|}alertId,alertSequence,channelId,deliveryInfoId,auditTime,auditType,status,timeInfo,deliveryOrder,channelName,deliveryString,maxDelay,properties,appId,appName,userId) values({|||AE_Seq.nextval,|}?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  private static final String l = "select ai.id,ai.alertId,ai.alertSequence,ai.channelId,ai.deliveryInfoId,ai.auditTime,ai.auditType,ai.status,ai.timeInfo,ai.deliveryOrder,ai.channelName,ai.deliveryString,ai.maxDelay,ai.properties,ai.appId,ai.appName,ai.userId from AE_AuditLog ai where ai.auditTime >= ? and ai.auditTime <= ? and ai.appId <> ? order by ai.auditTime, ai.id";
  private static final String e = "select ai.id,ai.alertId,ai.alertSequence,ai.channelId,ai.deliveryInfoId,ai.auditTime,ai.auditType,ai.status,ai.timeInfo,ai.deliveryOrder,ai.channelName,ai.deliveryString,ai.maxDelay,ai.properties,ai.appId,ai.appName,ai.userId from AE_AuditLog ai where ai.auditTime >= ? and ai.auditTime <= ? and ai.appId = ? order by ai.auditTime, ai.id";
  private static final String h = "select ai.id,ai.alertId,ai.alertSequence,ai.channelId,ai.deliveryInfoId,ai.auditTime,ai.auditType,ai.status,ai.timeInfo,ai.deliveryOrder,ai.channelName,ai.deliveryString,ai.maxDelay,ai.properties,ai.appId,ai.appName,ai.userId from AE_AuditLog ai where ai.auditTime >= ? and ai.auditTime <= ? and ai.appId = ? and ai.userId = ? order by ai.auditTime, ai.id";
  private static final String n = "select ai.id,ai.alertId,ai.alertSequence,ai.channelId,ai.deliveryInfoId,ai.auditTime,ai.auditType,ai.status,ai.timeInfo,ai.deliveryOrder,ai.channelName,ai.deliveryString,ai.maxDelay,ai.properties,ai.appId,ai.appName,ai.userId from AE_AuditLog ai where ai.auditTime >= ? and ai.auditTime <= ? and ai.appId = ? and ai.userId is null order by ai.auditTime, ai.id";
  private static final String m = "select ai.id,ai.alertId,ai.alertSequence,ai.channelId,ai.deliveryInfoId,ai.auditTime,ai.auditType,ai.status,ai.timeInfo,ai.deliveryOrder,ai.channelName,ai.deliveryString,ai.maxDelay,ai.properties,ai.appId,ai.appName,ai.userId from AE_AuditLog ai where ai.auditTime >= ? and ai.auditTime <= ? and ai.appId <> ? and ai.userId = ? order by ai.auditTime, ai.id";
  private static final String jdField_for = "select ai.id,ai.alertId,ai.alertSequence,ai.channelId,ai.deliveryInfoId,ai.auditTime,ai.auditType,ai.status,ai.timeInfo,ai.deliveryOrder,ai.channelName,ai.deliveryString,ai.maxDelay,ai.properties,ai.appId,ai.appName,ai.userId from AE_AuditLog ai where ai.auditTime >= ? and ai.auditTime <= ? and ai.appId <> ? and ai.userId is null order by ai.auditTime, ai.id";
  private static final String jdField_try = "select deliveryString from AE_AuditLog where id=?";
  private int jdField_new;
  private int f;
  private int j;
  private int d;
  private int jdField_void;
  private long b;
  private int jdField_null;
  private int jdField_do;
  private long jdField_if;
  private int jdField_byte;
  private String jdField_long;
  private long jdField_goto;
  private int g;
  private int i;
  private String c;
  private String jdField_else;
  private String jdField_char;
  private IAEDeliveryInfo k;
  
  public final int jdField_do()
  {
    return this.jdField_new;
  }
  
  private final void jdField_int(int paramInt)
  {
    this.jdField_new = paramInt;
  }
  
  public final int getAlertId()
  {
    return this.f;
  }
  
  public final void jdField_new(int paramInt)
  {
    this.f = paramInt;
  }
  
  public final int getAlertSequence()
  {
    return this.j;
  }
  
  public final void jdField_char(int paramInt)
  {
    this.j = paramInt;
  }
  
  public final int a()
  {
    return this.d;
  }
  
  public final void jdField_for(int paramInt)
  {
    this.d = paramInt;
  }
  
  public final int jdField_for()
  {
    return this.jdField_void;
  }
  
  public final void jdField_byte(int paramInt)
  {
    this.jdField_void = paramInt;
  }
  
  public final long jdField_byte()
  {
    return this.b;
  }
  
  public final Date getAuditDate()
  {
    return new Date(this.b);
  }
  
  public final void a(long paramLong)
  {
    this.b = paramLong;
  }
  
  public final int getAuditType()
  {
    return this.jdField_null;
  }
  
  public final void jdField_do(int paramInt)
  {
    this.jdField_null = paramInt;
  }
  
  public final int getAuditStatus()
  {
    return this.jdField_do;
  }
  
  public final void a(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public final long getTimeInfo()
  {
    return this.jdField_if;
  }
  
  public final void jdField_do(long paramLong)
  {
    this.jdField_if = paramLong;
  }
  
  public final IAEDeliveryInfo getDeliveryInfo()
  {
    return this.k;
  }
  
  public final void a(IAEDeliveryInfo paramIAEDeliveryInfo)
  {
    this.k = paramIAEDeliveryInfo;
    DeliveryInfo localDeliveryInfo = (DeliveryInfo)paramIAEDeliveryInfo;
    if (this.k != null)
    {
      jdField_if(localDeliveryInfo.getDeliveryOrder());
      jdField_do(localDeliveryInfo.getDeliveryChannelName());
      a(localDeliveryInfo.jdField_for());
      jdField_if(localDeliveryInfo.getMaxDelay());
      jdField_case(localDeliveryInfo.jdField_do());
    }
  }
  
  public final void jdField_if(int paramInt)
  {
    this.jdField_byte = paramInt;
  }
  
  public final int jdField_case()
  {
    return this.jdField_byte;
  }
  
  public final String getChannelName()
  {
    return this.jdField_char;
  }
  
  public final void jdField_do(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public final void a(String paramString)
  {
    this.jdField_long = paramString;
  }
  
  public final String jdField_int()
  {
    return this.jdField_long;
  }
  
  public final void jdField_if(long paramLong)
  {
    this.jdField_goto = paramLong;
  }
  
  public final long jdField_char()
  {
    return this.jdField_goto;
  }
  
  public final void jdField_case(int paramInt)
  {
    this.g = paramInt;
  }
  
  public final int jdField_try()
  {
    return this.g;
  }
  
  public final int jdField_if()
  {
    return this.i;
  }
  
  public final void jdField_try(int paramInt)
  {
    this.i = paramInt;
  }
  
  public final String getUserId()
  {
    return this.c;
  }
  
  public final void jdField_for(String paramString)
  {
    this.c = paramString;
  }
  
  public final void jdField_if(String paramString)
  {
    this.jdField_else = paramString;
  }
  
  public final String getApplicationName()
  {
    return this.jdField_else;
  }
  
  public final void jdField_new()
  {
    this.jdField_new = 0;
    this.f = 0;
    this.j = 0;
    this.d = 0;
    this.jdField_void = 0;
    this.b = 0L;
    this.jdField_null = 0;
    this.jdField_do = 0;
    this.jdField_if = 0L;
    this.jdField_byte = 0;
    this.jdField_long = null;
    this.jdField_char = null;
    this.jdField_goto = 0L;
    this.k = null;
    this.i = 0;
    this.c = null;
    this.jdField_else = null;
  }
  
  public void a(AERepository paramAERepository, DBConnection paramDBConnection, boolean paramBoolean)
    throws AEException
  {
    boolean bool1 = false;
    CrashRecovery localCrashRecovery = new CrashRecovery();
    try
    {
      bool1 = paramDBConnection.aU();
      if (bool1) {
        paramDBConnection.jdField_try(false);
      }
      boolean bool2 = DBSqlUtils.jdField_if(paramDBConnection.aS(), this.jdField_long);
      if (this.jdField_new == 0)
      {
        Object[] arrayOfObject1 = { this.f == 0 ? null : new Integer(this.f), this.f == 0 ? null : new Integer(this.j), this.d == 0 ? null : new Integer(this.d), this.jdField_void == 0 ? null : new Integer(this.jdField_void), new Long(this.b), new Integer(this.jdField_null), new Integer(this.jdField_do), this.b == 0L ? null : new Long(this.jdField_if), new Integer(this.jdField_byte), this.jdField_char == null ? null : this.jdField_char, bool2 ? " " : this.jdField_long, this.jdField_goto == 0L ? null : new Long(this.jdField_goto), new Integer(this.g), this.i == 0 ? null : new Integer(this.i), this.jdField_else == null ? null : this.jdField_else, this.c == null ? null : this.c };
        paramDBConnection.jdField_if("insert into AE_AuditLog({|||id,|}alertId,alertSequence,channelId,deliveryInfoId,auditTime,auditType,status,timeInfo,deliveryOrder,channelName,deliveryString,maxDelay,properties,appId,appName,userId) values({|||AE_Seq.nextval,|}?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", arrayOfObject1);
        int i1 = paramAERepository.h(paramDBConnection);
        if (paramBoolean) {
          this.jdField_new = i1;
        }
        localCrashRecovery.jdField_for(this.f);
        localCrashRecovery.jdField_if(this.j);
        localCrashRecovery.jdField_new(this.d);
        localCrashRecovery.jdField_do(this.jdField_void);
        localCrashRecovery.jdField_if(this.b);
        localCrashRecovery.a(this.jdField_null);
        localCrashRecovery.jdField_int(this.jdField_do);
        localCrashRecovery.a(this.jdField_if);
        localCrashRecovery.a(paramAERepository, paramDBConnection, paramBoolean);
        if (bool2)
        {
          Object[] arrayOfObject2 = { this.jdField_long };
          paramDBConnection.a("select deliveryString from AE_AuditLog where id=?", i1, arrayOfObject2);
        }
      }
      if (bool1) {
        paramDBConnection.a0();
      }
    }
    catch (IOException localIOException)
    {
      try
      {
        if (bool1) {
          paramDBConnection.a1();
        }
      }
      catch (SQLException localSQLException2) {}
      throw new AEException(1, localIOException);
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        if (bool1) {
          paramDBConnection.a1();
        }
      }
      catch (SQLException localSQLException3) {}
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if (bool1) {
          paramDBConnection.jdField_try(true);
        }
      }
      catch (SQLException localSQLException4)
      {
        throw new AEException(1, DBSqlUtils.a(localSQLException4));
      }
    }
  }
  
  public static ArrayList a(DBConnection paramDBConnection, int paramInt, long paramLong1, long paramLong2)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      localDBResultSet = paramDBConnection.I("select ai.id,ai.alertId,ai.alertSequence,ai.channelId,ai.deliveryInfoId,ai.auditTime,ai.auditType,ai.status,ai.timeInfo,ai.deliveryOrder,ai.channelName,ai.deliveryString,ai.maxDelay,ai.properties,ai.appId,ai.appName,ai.userId from AE_AuditLog ai where ai.auditTime >= ? and ai.auditTime <= ? and ai.appId <> ? order by ai.auditTime, ai.id");
      Object[] arrayOfObject = { new Long(paramLong1), new Long(paramLong2), new Integer(paramInt) };
      localDBResultSet.a(arrayOfObject);
      ArrayList localArrayList = new ArrayList();
      while (localDBResultSet.a())
      {
        localObject1 = new AuditInfo();
        a(localDBResultSet, (AuditInfo)localObject1);
        localArrayList.add(localObject1);
      }
      Object localObject1 = localArrayList;
      return localObject1;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, localSQLException1);
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdField_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  public static ArrayList jdField_if(DBConnection paramDBConnection, int paramInt, long paramLong1, long paramLong2)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      localDBResultSet = paramDBConnection.I("select ai.id,ai.alertId,ai.alertSequence,ai.channelId,ai.deliveryInfoId,ai.auditTime,ai.auditType,ai.status,ai.timeInfo,ai.deliveryOrder,ai.channelName,ai.deliveryString,ai.maxDelay,ai.properties,ai.appId,ai.appName,ai.userId from AE_AuditLog ai where ai.auditTime >= ? and ai.auditTime <= ? and ai.appId = ? order by ai.auditTime, ai.id");
      Object[] arrayOfObject = { new Long(paramLong1), new Long(paramLong2), new Integer(paramInt) };
      localDBResultSet.a(arrayOfObject);
      ArrayList localArrayList = new ArrayList();
      while (localDBResultSet.a())
      {
        localObject1 = new AuditInfo();
        a(localDBResultSet, (AuditInfo)localObject1);
        localArrayList.add(localObject1);
      }
      Object localObject1 = localArrayList;
      return localObject1;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, localSQLException1);
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdField_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  public static ArrayList a(DBConnection paramDBConnection, int paramInt, String paramString, long paramLong1, long paramLong2)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      Object[] arrayOfObject;
      if (paramString == null)
      {
        localDBResultSet = paramDBConnection.I("select ai.id,ai.alertId,ai.alertSequence,ai.channelId,ai.deliveryInfoId,ai.auditTime,ai.auditType,ai.status,ai.timeInfo,ai.deliveryOrder,ai.channelName,ai.deliveryString,ai.maxDelay,ai.properties,ai.appId,ai.appName,ai.userId from AE_AuditLog ai where ai.auditTime >= ? and ai.auditTime <= ? and ai.appId = ? and ai.userId is null order by ai.auditTime, ai.id");
        arrayOfObject = new Object[] { new Long(paramLong1), new Long(paramLong2), new Integer(paramInt) };
      }
      else
      {
        localDBResultSet = paramDBConnection.I("select ai.id,ai.alertId,ai.alertSequence,ai.channelId,ai.deliveryInfoId,ai.auditTime,ai.auditType,ai.status,ai.timeInfo,ai.deliveryOrder,ai.channelName,ai.deliveryString,ai.maxDelay,ai.properties,ai.appId,ai.appName,ai.userId from AE_AuditLog ai where ai.auditTime >= ? and ai.auditTime <= ? and ai.appId = ? and ai.userId = ? order by ai.auditTime, ai.id");
        arrayOfObject = new Object[] { new Long(paramLong1), new Long(paramLong2), new Integer(paramInt), paramString };
      }
      localDBResultSet.a(arrayOfObject);
      ArrayList localArrayList = new ArrayList();
      while (localDBResultSet.a())
      {
        localObject1 = new AuditInfo();
        a(localDBResultSet, (AuditInfo)localObject1);
        localArrayList.add(localObject1);
      }
      Object localObject1 = localArrayList;
      return localObject1;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, localSQLException1);
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdField_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  public static ArrayList a(DBConnection paramDBConnection, String paramString, int paramInt, long paramLong1, long paramLong2)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      Object[] arrayOfObject;
      if (paramString == null)
      {
        localDBResultSet = paramDBConnection.I("select ai.id,ai.alertId,ai.alertSequence,ai.channelId,ai.deliveryInfoId,ai.auditTime,ai.auditType,ai.status,ai.timeInfo,ai.deliveryOrder,ai.channelName,ai.deliveryString,ai.maxDelay,ai.properties,ai.appId,ai.appName,ai.userId from AE_AuditLog ai where ai.auditTime >= ? and ai.auditTime <= ? and ai.appId <> ? and ai.userId is null order by ai.auditTime, ai.id");
        arrayOfObject = new Object[] { new Long(paramLong1), new Long(paramLong2), new Integer(paramInt) };
      }
      else
      {
        localDBResultSet = paramDBConnection.I("select ai.id,ai.alertId,ai.alertSequence,ai.channelId,ai.deliveryInfoId,ai.auditTime,ai.auditType,ai.status,ai.timeInfo,ai.deliveryOrder,ai.channelName,ai.deliveryString,ai.maxDelay,ai.properties,ai.appId,ai.appName,ai.userId from AE_AuditLog ai where ai.auditTime >= ? and ai.auditTime <= ? and ai.appId <> ? and ai.userId = ? order by ai.auditTime, ai.id");
        arrayOfObject = new Object[] { new Long(paramLong1), new Long(paramLong2), new Integer(paramInt), paramString };
      }
      localDBResultSet.a(arrayOfObject);
      ArrayList localArrayList = new ArrayList();
      while (localDBResultSet.a())
      {
        localObject1 = new AuditInfo();
        a(localDBResultSet, (AuditInfo)localObject1);
        localArrayList.add(localObject1);
      }
      Object localObject1 = localArrayList;
      return localObject1;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, localSQLException1);
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdField_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  private static void a(DBResultSet paramDBResultSet, AuditInfo paramAuditInfo)
    throws SQLException
  {
    paramAuditInfo.jdField_int(paramDBResultSet.jdField_int(1));
    paramAuditInfo.jdField_new(paramDBResultSet.jdField_int(2));
    paramAuditInfo.jdField_char(paramDBResultSet.jdField_int(3));
    paramAuditInfo.jdField_for(paramDBResultSet.jdField_int(4));
    paramAuditInfo.jdField_byte(paramDBResultSet.jdField_int(5));
    paramAuditInfo.a(paramDBResultSet.jdField_char(6));
    paramAuditInfo.jdField_do(paramDBResultSet.jdField_int(7));
    paramAuditInfo.a(paramDBResultSet.jdField_int(8));
    paramAuditInfo.jdField_do(paramDBResultSet.jdField_char(9));
    paramAuditInfo.jdField_if(paramDBResultSet.jdField_int(10));
    paramAuditInfo.jdField_do(paramDBResultSet.jdField_try(11));
    paramAuditInfo.a(paramDBResultSet.jdField_do(12));
    paramAuditInfo.jdField_if(paramDBResultSet.jdField_char(13));
    paramAuditInfo.jdField_case(paramDBResultSet.jdField_int(14));
    paramAuditInfo.jdField_try(paramDBResultSet.jdField_int(15));
    paramAuditInfo.jdField_if(paramDBResultSet.jdField_try(16));
    paramAuditInfo.jdField_for(paramDBResultSet.jdField_try(17));
    if (paramAuditInfo.getAuditType() == 2)
    {
      DeliveryInfo localDeliveryInfo = new DeliveryInfo();
      localDeliveryInfo.jdField_do(paramAuditInfo.getAlertId());
      localDeliveryInfo.setDeliveryOrder(paramAuditInfo.jdField_case());
      localDeliveryInfo.jdField_if(paramAuditInfo.a());
      localDeliveryInfo.setDeliveryChannelName(paramAuditInfo.getChannelName());
      localDeliveryInfo.a(paramAuditInfo.jdField_int());
      localDeliveryInfo.setMaxDelay(paramAuditInfo.jdField_char());
      paramAuditInfo.a(localDeliveryInfo);
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.AuditInfo
 * JD-Core Version:    0.7.0.1
 */