package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class CrashRecovery
{
  private static final String jdField_try = "alertId,alertSequence,channelId,deliveryInfoId,auditTime,auditType,status,timeInfo";
  private static final String jdField_int = "cr.alertId,cr.alertSequence,cr.channelId,cr.deliveryInfoId,cr.auditTime,cr.auditType,cr.status,cr.timeInfo";
  private static final String a = "insert into AE_CrashRecovery({|||id,|}alertId,alertSequence,channelId,deliveryInfoId,auditTime,auditType,status,timeInfo) values({|||AE_Seq.nextval,|}?,?,?,?,?,?,?,?)";
  private static final String d = "select cr.id,cr.alertId,cr.alertSequence,cr.channelId,cr.deliveryInfoId,cr.auditTime,cr.auditType,cr.status,cr.timeInfo from AE_CrashRecovery cr, AE_Alert a where cr.alertId = a.id and cr.auditTime >= ? and cr.auditTime <= ? and a.appId <> ? order by cr.auditTime, cr.id";
  private static final String jdField_long = "select cr.id,cr.alertId,cr.alertSequence,cr.channelId,cr.deliveryInfoId,cr.auditTime,cr.auditType,cr.status,cr.timeInfo from AE_CrashRecovery cr, AE_Alert a where cr.alertId = a.id and cr.auditTime >= ? and cr.auditTime <= ? and a.appId = ? order by cr.auditTime, cr.id";
  private static final String jdField_void = "select cr.id,cr.alertId,cr.alertSequence,cr.channelId,cr.deliveryInfoId,cr.auditTime,cr.auditType,cr.status,cr.timeInfo from AE_CrashRecovery cr, AE_Alert a where cr.alertId = a.id and cr.auditTime >= ? and cr.auditTime <= ? and a.appId = ? and a.userId = ? order by cr.auditTime, cr.id";
  private static final String f = "select cr.id,cr.alertId,cr.alertSequence,cr.channelId,cr.deliveryInfoId,cr.auditTime,cr.auditType,cr.status,cr.timeInfo from AE_CrashRecovery cr, AE_Alert a where cr.alertId = a.id and cr.auditTime >= ? and cr.auditTime <= ? and a.appId = ? and a.userId is null order by cr.auditTime, cr.id";
  private static final String e = "select cr.id,cr.alertId,cr.alertSequence,cr.channelId,cr.deliveryInfoId,cr.auditTime,cr.auditType,cr.status,cr.timeInfo from AE_CrashRecovery, AE_Alert a where cr.alertId = a.id and cr.auditTime >= ? and cr.auditTime <= ? and a.appId <> ? and a.userId = ? order by cr.auditTime, cr.id";
  private static final String jdField_for = "select cr.id,cr.alertId,cr.alertSequence,cr.channelId,cr.deliveryInfoId,cr.auditTime,cr.auditType,cr.status,cr.timeInfo from AE_CrashRecovery cr, AE_Alert a where cr.alertId = a.id and cr.auditTime >= ? and cr.auditTime <= ? and a.appId <> ? and a.userId is null order by cr.auditTime, cr.id";
  private int jdField_new;
  private int jdField_null;
  private int b;
  private int jdField_goto;
  private int jdField_char;
  private long jdField_else;
  private int jdField_case;
  private int jdField_do;
  private long jdField_if;
  private String jdField_byte;
  private IAEDeliveryInfo c;
  
  public final int jdField_char()
  {
    return this.jdField_new;
  }
  
  public final void jdField_try(int paramInt)
  {
    this.jdField_new = paramInt;
  }
  
  public final int jdField_else()
  {
    return this.jdField_null;
  }
  
  public final void jdField_for(int paramInt)
  {
    this.jdField_null = paramInt;
  }
  
  public final int jdField_if()
  {
    return this.b;
  }
  
  public final void jdField_if(int paramInt)
  {
    this.b = paramInt;
  }
  
  public final int jdField_do()
  {
    return this.jdField_goto;
  }
  
  public final void jdField_new(int paramInt)
  {
    this.jdField_goto = paramInt;
  }
  
  public final int jdField_long()
  {
    return this.jdField_char;
  }
  
  public final void jdField_do(int paramInt)
  {
    this.jdField_char = paramInt;
  }
  
  public final long jdField_case()
  {
    return this.jdField_else;
  }
  
  public final Date jdField_int()
  {
    return new Date(this.jdField_else);
  }
  
  public final void jdField_if(long paramLong)
  {
    this.jdField_else = paramLong;
  }
  
  public final int jdField_try()
  {
    return this.jdField_case;
  }
  
  public final void a(int paramInt)
  {
    this.jdField_case = paramInt;
  }
  
  public final int jdField_byte()
  {
    return this.jdField_do;
  }
  
  public final void jdField_int(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public final long jdField_new()
  {
    return this.jdField_if;
  }
  
  public final void a(long paramLong)
  {
    this.jdField_if = paramLong;
  }
  
  public final IAEDeliveryInfo a()
  {
    return this.c;
  }
  
  public final void a(IAEDeliveryInfo paramIAEDeliveryInfo)
  {
    this.c = paramIAEDeliveryInfo;
  }
  
  public final String jdField_goto()
  {
    return this.jdField_byte;
  }
  
  public final void a(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public final void jdField_for()
  {
    this.jdField_new = 0;
    this.jdField_null = 0;
    this.b = 0;
    this.jdField_goto = 0;
    this.jdField_char = 0;
    this.jdField_else = 0L;
    this.jdField_case = 0;
    this.jdField_do = 0;
    this.jdField_if = 0L;
    this.jdField_byte = null;
    this.c = null;
  }
  
  public void a(AERepository paramAERepository, DBConnection paramDBConnection, boolean paramBoolean)
    throws AEException
  {
    boolean bool = false;
    try
    {
      bool = paramDBConnection.aU();
      if (bool) {
        paramDBConnection.jdField_try(false);
      }
      if (this.jdField_new == 0)
      {
        Object[] arrayOfObject = { this.jdField_null == 0 ? null : new Integer(this.jdField_null), this.jdField_null == 0 ? null : new Integer(this.b), this.jdField_goto == 0 ? null : new Integer(this.jdField_goto), this.jdField_char == 0 ? null : new Integer(this.jdField_char), new Long(this.jdField_else), new Integer(this.jdField_case), new Integer(this.jdField_do), this.jdField_else == 0L ? null : new Long(this.jdField_if) };
        paramDBConnection.jdField_if("insert into AE_CrashRecovery({|||id,|}alertId,alertSequence,channelId,deliveryInfoId,auditTime,auditType,status,timeInfo) values({|||AE_Seq.nextval,|}?,?,?,?,?,?,?,?)", arrayOfObject);
        if (paramBoolean) {
          this.jdField_new = paramAERepository.h(paramDBConnection);
        }
      }
      if (bool) {
        paramDBConnection.a0();
      }
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        if (bool) {
          paramDBConnection.a1();
        }
      }
      catch (SQLException localSQLException2) {}
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if (bool) {
          paramDBConnection.jdField_try(true);
        }
      }
      catch (SQLException localSQLException3)
      {
        throw new AEException(1, DBSqlUtils.a(localSQLException3));
      }
    }
  }
  
  public static ArrayList a(DBConnection paramDBConnection, int paramInt, long paramLong1, long paramLong2)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      localDBResultSet = paramDBConnection.I("select cr.id,cr.alertId,cr.alertSequence,cr.channelId,cr.deliveryInfoId,cr.auditTime,cr.auditType,cr.status,cr.timeInfo from AE_CrashRecovery cr, AE_Alert a where cr.alertId = a.id and cr.auditTime >= ? and cr.auditTime <= ? and a.appId <> ? order by cr.auditTime, cr.id");
      Object[] arrayOfObject = { new Long(paramLong1), new Long(paramLong2), new Integer(paramInt) };
      localDBResultSet.a(arrayOfObject);
      ArrayList localArrayList = new ArrayList();
      while (localDBResultSet.a())
      {
        localObject1 = new CrashRecovery();
        a(localDBResultSet, (CrashRecovery)localObject1);
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
      localDBResultSet = paramDBConnection.I("select cr.id,cr.alertId,cr.alertSequence,cr.channelId,cr.deliveryInfoId,cr.auditTime,cr.auditType,cr.status,cr.timeInfo from AE_CrashRecovery cr, AE_Alert a where cr.alertId = a.id and cr.auditTime >= ? and cr.auditTime <= ? and a.appId = ? order by cr.auditTime, cr.id");
      Object[] arrayOfObject = { new Long(paramLong1), new Long(paramLong2), new Integer(paramInt) };
      localDBResultSet.a(arrayOfObject);
      ArrayList localArrayList = new ArrayList();
      while (localDBResultSet.a())
      {
        localObject1 = new CrashRecovery();
        a(localDBResultSet, (CrashRecovery)localObject1);
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
        localDBResultSet = paramDBConnection.I("select cr.id,cr.alertId,cr.alertSequence,cr.channelId,cr.deliveryInfoId,cr.auditTime,cr.auditType,cr.status,cr.timeInfo from AE_CrashRecovery cr, AE_Alert a where cr.alertId = a.id and cr.auditTime >= ? and cr.auditTime <= ? and a.appId = ? and a.userId is null order by cr.auditTime, cr.id");
        arrayOfObject = new Object[] { new Long(paramLong1), new Long(paramLong2), new Integer(paramInt) };
      }
      else
      {
        localDBResultSet = paramDBConnection.I("select cr.id,cr.alertId,cr.alertSequence,cr.channelId,cr.deliveryInfoId,cr.auditTime,cr.auditType,cr.status,cr.timeInfo from AE_CrashRecovery cr, AE_Alert a where cr.alertId = a.id and cr.auditTime >= ? and cr.auditTime <= ? and a.appId = ? and a.userId = ? order by cr.auditTime, cr.id");
        arrayOfObject = new Object[] { new Long(paramLong1), new Long(paramLong2), new Integer(paramInt), paramString };
      }
      localDBResultSet.a(arrayOfObject);
      ArrayList localArrayList = new ArrayList();
      while (localDBResultSet.a())
      {
        localObject1 = new CrashRecovery();
        a(localDBResultSet, (CrashRecovery)localObject1);
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
        localDBResultSet = paramDBConnection.I("select cr.id,cr.alertId,cr.alertSequence,cr.channelId,cr.deliveryInfoId,cr.auditTime,cr.auditType,cr.status,cr.timeInfo from AE_CrashRecovery cr, AE_Alert a where cr.alertId = a.id and cr.auditTime >= ? and cr.auditTime <= ? and a.appId <> ? and a.userId is null order by cr.auditTime, cr.id");
        arrayOfObject = new Object[] { new Long(paramLong1), new Long(paramLong2), new Integer(paramInt) };
      }
      else
      {
        localDBResultSet = paramDBConnection.I("select cr.id,cr.alertId,cr.alertSequence,cr.channelId,cr.deliveryInfoId,cr.auditTime,cr.auditType,cr.status,cr.timeInfo from AE_CrashRecovery, AE_Alert a where cr.alertId = a.id and cr.auditTime >= ? and cr.auditTime <= ? and a.appId <> ? and a.userId = ? order by cr.auditTime, cr.id");
        arrayOfObject = new Object[] { new Long(paramLong1), new Long(paramLong2), new Integer(paramInt), paramString };
      }
      localDBResultSet.a(arrayOfObject);
      ArrayList localArrayList = new ArrayList();
      while (localDBResultSet.a())
      {
        localObject1 = new CrashRecovery();
        a(localDBResultSet, (CrashRecovery)localObject1);
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
  
  private static void a(DBResultSet paramDBResultSet, CrashRecovery paramCrashRecovery)
    throws SQLException
  {
    paramCrashRecovery.jdField_try(paramDBResultSet.jdField_int(1));
    paramCrashRecovery.jdField_for(paramDBResultSet.jdField_int(2));
    paramCrashRecovery.jdField_if(paramDBResultSet.jdField_int(3));
    paramCrashRecovery.jdField_new(paramDBResultSet.jdField_int(4));
    paramCrashRecovery.jdField_do(paramDBResultSet.jdField_int(5));
    paramCrashRecovery.jdField_if(paramDBResultSet.jdField_char(6));
    paramCrashRecovery.a(paramDBResultSet.jdField_int(7));
    paramCrashRecovery.jdField_int(paramDBResultSet.jdField_int(8));
    paramCrashRecovery.a(paramDBResultSet.jdField_char(9));
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.CrashRecovery
 * JD-Core Version:    0.7.0.1
 */