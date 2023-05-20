package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.interfaces.IAEStatusConstants;
import com.ffusion.alert.shared.AELog;
import com.ffusion.alert.shared.AEUtils;
import com.ffusion.alert.shared.ISchedulable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public final class AlertInstance
  implements IAEAlertInstance, ISchedulable, Cloneable, IAEErrConstants, IAEStatusConstants
{
  private static final String dO = "type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled";
  static final int dY = 15;
  static final String dS = "a.id,a.type,a.priority,a.appId,a.userId,a.messageInfo,a.properties,a.startDate,a.endDate,a.interval,a.intervalType,a.timeZone,a.sequence,a.nextRaised,a.lastScheduled";
  private static final String dQ = "insert into AE_Alert({|||id,|}type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled) values({|||AE_Seq.nextval,|}?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  private static final String dM = "update AE_Alert set type=?, priority=?, appId=?, userId=?,messageInfo=?, properties=?, startDate=?, endDate=?, interval=?, intervalType=?, timeZone=?, sequence=?, nextRaised=?, lastScheduled=? where id=?";
  private static final String dV = "update AE_Alert set sequence=?, nextRaised=?, lastScheduled=? where id=?";
  private static final String dG = "delete from AE_Alert where nextRaised = 0 and id not in( select alertId from AE_CrashRecovery ) and lastScheduled < ?";
  private static final String dH = "select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where id=?";
  private static final String dK = "select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > 0 and nextRaised < ? order by nextRaised asc";
  private static final String dZ = "select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and type = ?";
  private static final String dP = "select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and appId <> ? order by id";
  private static final String dN = "select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and appId=? order by id";
  private static final String d1 = "select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and appId=? and id > ? and exists( select di.id from AE_DeliveryInfo di where di.alertId=AE_Alert.id and di.channelId=? ) order by id";
  private static final String dR = "select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and appId=? and userId=? order by id";
  private static final String dJ = "select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and appId=? and userId is null order by id";
  private static final String d0 = "select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and appId <> ? and userId=? order by id";
  private static final String dI = "select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and appId <> ? and userId is null order by id";
  private static final String dL = "select messageInfo, timeZone from AE_Alert where id=?";
  private int dU;
  private int[] dF;
  private long dX;
  private long dW;
  private AlertDefinition dT = new AlertDefinition();
  
  public final int getId()
  {
    return this.dT.getId();
  }
  
  private final void j(int paramInt)
  {
    this.dT.jdMethod_case(paramInt);
  }
  
  public final int getType()
  {
    return this.dT.getType();
  }
  
  public final void m(int paramInt)
  {
    this.dT.jdMethod_char(paramInt);
  }
  
  public final int getPriority()
  {
    return this.dT.getPriority();
  }
  
  public final void r(int paramInt)
  {
    this.dT.jdMethod_byte(paramInt);
  }
  
  public final int aB()
  {
    return this.dT.jdMethod_try();
  }
  
  public final void n(int paramInt)
  {
    this.dT.jdMethod_try(paramInt);
  }
  
  public final String getApplicationName()
  {
    return this.dT.getApplicationName();
  }
  
  public final void C(String paramString)
  {
    this.dT.jdMethod_for(paramString);
  }
  
  public String getUserId()
  {
    return this.dT.getUserId();
  }
  
  public void D(String paramString)
  {
    this.dT.jdMethod_if(paramString);
  }
  
  public final String getMessage()
  {
    return this.dT.getMessage();
  }
  
  public final void E(String paramString)
  {
    this.dT.jdMethod_do(paramString);
  }
  
  public final byte[] getMessageBytes()
  {
    return this.dT.getMessageBytes();
  }
  
  public final void jdMethod_if(byte[] paramArrayOfByte)
  {
    this.dT.a(paramArrayOfByte);
  }
  
  public final void q(int paramInt)
  {
    this.dT.jdMethod_new(paramInt);
  }
  
  public final int aD()
  {
    return this.dT.jdMethod_new();
  }
  
  public final void jdMethod_for(int paramInt, boolean paramBoolean)
  {
    this.dT.jdMethod_if(paramInt, paramBoolean);
  }
  
  public final boolean l(int paramInt)
  {
    return this.dT.jdMethod_goto(paramInt);
  }
  
  public final AEScheduleInfo getScheduleInfo()
  {
    return this.dT.getScheduleInfo();
  }
  
  public final void jdMethod_if(AEScheduleInfo paramAEScheduleInfo)
  {
    this.dT.a(paramAEScheduleInfo);
  }
  
  public final IAEDeliveryInfo[] getDeliveryInfo()
  {
    return this.dT.getDeliveryInfo();
  }
  
  public final void jdMethod_if(DeliveryInfo[] paramArrayOfDeliveryInfo)
  {
    this.dT.a(paramArrayOfDeliveryInfo);
  }
  
  public final int getStatus()
  {
    return this.dT.getStatus();
  }
  
  public final void p(int paramInt)
  {
    this.dT.jdMethod_else(paramInt);
  }
  
  public final int getSequence()
  {
    return this.dU;
  }
  
  public void o(int paramInt)
  {
    this.dU = paramInt;
  }
  
  public final long getNextRaised()
  {
    return this.dX;
  }
  
  public final void jdMethod_new(long paramLong)
  {
    this.dX = paramLong;
  }
  
  public final long aC()
  {
    return this.dW;
  }
  
  public final void jdMethod_int(long paramLong)
  {
    this.dW = paramLong;
  }
  
  public final void jdMethod_if(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      throw new NullPointerException();
    }
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      paramArrayOfInt[i] = 1;
    }
    this.dF = paramArrayOfInt;
  }
  
  public final int k(int paramInt)
  {
    if (this.dF == null) {
      throw new NullPointerException();
    }
    return this.dF[paramInt];
  }
  
  public final void jdMethod_do(int paramInt1, int paramInt2)
  {
    if (this.dF == null) {
      throw new NullPointerException();
    }
    this.dF[paramInt1] = paramInt2;
  }
  
  public void jdMethod_if(AERepository paramAERepository, DBConnection paramDBConnection)
    throws AEException
  {
    boolean bool1 = false;
    try
    {
      bool1 = paramDBConnection.aU();
      if (bool1) {
        paramDBConnection.jdMethod_try(false);
      }
      byte[] arrayOfByte1 = getMessageBytes();
      byte[] arrayOfByte2 = AEUtils.a(this.dT.getScheduleInfo().getRegularTimeZone());
      boolean bool2 = DBSqlUtils.a(paramDBConnection.aS(), arrayOfByte1);
      bool2 |= DBSqlUtils.a(paramDBConnection.aS(), arrayOfByte2);
      if (this.dT.getId() == 0)
      {
        localObject1 = new Object[] { new Integer(this.dT.getType()), new Integer(this.dT.getPriority()), new Integer(this.dT.jdMethod_try()), this.dT.getUserId(), bool2 ? DBSQLConstants.eO : arrayOfByte1, new Integer(this.dT.jdMethod_new()), new Long(this.dT.getScheduleInfo().getStart()), new Long(this.dT.getScheduleInfo().getEnd()), new Long(this.dT.getScheduleInfo().getInterval()), new Long(this.dT.getScheduleInfo().getIntervalType()), bool2 ? DBSQLConstants.eO : arrayOfByte2, new Integer(this.dU), new Long(this.dX), new Long(this.dW) };
        paramDBConnection.jdMethod_if("insert into AE_Alert({|||id,|}type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled) values({|||AE_Seq.nextval,|}?,?,?,?,?,?,?,?,?,?,?,?,?,?)", (Object[])localObject1);
        this.dT.jdMethod_case(paramAERepository.h(paramDBConnection));
      }
      else
      {
        localObject1 = new Object[] { new Integer(this.dT.getType()), new Integer(this.dT.getPriority()), new Integer(this.dT.jdMethod_try()), this.dT.getUserId(), bool2 ? DBSQLConstants.eO : arrayOfByte1, new Integer(this.dT.jdMethod_new()), new Long(this.dT.getScheduleInfo().getStart()), new Long(this.dT.getScheduleInfo().getEnd()), new Long(this.dT.getScheduleInfo().getInterval()), new Long(this.dT.getScheduleInfo().getIntervalType()), bool2 ? DBSQLConstants.eO : arrayOfByte2, new Integer(this.dU), new Long(this.dX), new Long(this.dW), new Integer(this.dT.getId()) };
        paramDBConnection.jdMethod_if("update AE_Alert set type=?, priority=?, appId=?, userId=?,messageInfo=?, properties=?, startDate=?, endDate=?, interval=?, intervalType=?, timeZone=?, sequence=?, nextRaised=?, lastScheduled=? where id=?", (Object[])localObject1);
      }
      if (bool2)
      {
        localObject1 = new Object[] { arrayOfByte1, arrayOfByte2 };
        paramDBConnection.a("select messageInfo, timeZone from AE_Alert where id=?", this.dT.getId(), (Object[])localObject1);
      }
      Object localObject1 = (DeliveryInfo[])this.dT.getDeliveryInfo();
      for (int i = 0; i < localObject1.length; i++)
      {
        localObject1[i].jdMethod_do(this.dT.getId());
        localObject1[i].a(paramAERepository, paramDBConnection);
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
    catch (AEException localAEException)
    {
      try
      {
        if (bool1) {
          paramDBConnection.a1();
        }
      }
      catch (SQLException localSQLException4) {}
      throw localAEException;
    }
    finally
    {
      try
      {
        if (bool1) {
          paramDBConnection.jdMethod_try(true);
        }
      }
      catch (SQLException localSQLException5)
      {
        throw new AEException(1, DBSqlUtils.a(localSQLException5));
      }
    }
  }
  
  public void a(AERepository paramAERepository, DBConnection paramDBConnection, long paramLong)
    throws AEException
  {
    try
    {
      Object[] arrayOfObject = { new Integer(this.dU), new Long(paramLong), new Long(this.dW), new Integer(this.dT.getId()) };
      paramDBConnection.jdMethod_if("update AE_Alert set sequence=?, nextRaised=?, lastScheduled=? where id=?", arrayOfObject);
    }
    catch (SQLException localSQLException)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException));
    }
  }
  
  public static AlertInstance jdMethod_if(DBConnection paramDBConnection, int paramInt)
    throws AEException
  {
    return jdMethod_for(paramDBConnection, paramInt, true);
  }
  
  public static AlertInstance jdMethod_do(DBConnection paramDBConnection, int paramInt)
    throws AEException
  {
    return jdMethod_for(paramDBConnection, paramInt, false);
  }
  
  private static AlertInstance jdMethod_for(DBConnection paramDBConnection, int paramInt, boolean paramBoolean)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      Object[] arrayOfObject = { new Integer(paramInt) };
      localDBResultSet = paramDBConnection.I("select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where id=?");
      AlertInstance localAlertInstance1 = null;
      localDBResultSet.a(arrayOfObject);
      while (localDBResultSet.a())
      {
        localAlertInstance1 = new AlertInstance();
        a(paramDBConnection, localDBResultSet, localAlertInstance1, paramBoolean);
      }
      AlertInstance localAlertInstance2 = localAlertInstance1;
      return localAlertInstance2;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdMethod_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  public static void a(DBConnection paramDBConnection, long paramLong)
    throws AEException
  {
    try
    {
      Object[] arrayOfObject = { new Long(paramLong) };
      int i = paramDBConnection.jdMethod_if("delete from AE_Alert where nextRaised = 0 and id not in( select alertId from AE_CrashRecovery ) and lastScheduled < ?", arrayOfObject);
    }
    catch (SQLException localSQLException)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException));
    }
  }
  
  public static ArrayList a(DBConnection paramDBConnection, long paramLong, int paramInt)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      Object[] arrayOfObject = { new Long(paramLong) };
      localDBResultSet = paramDBConnection.I("select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > 0 and nextRaised < ? order by nextRaised asc");
      AlertInstance localAlertInstance = null;
      ArrayList localArrayList1 = new ArrayList();
      localDBResultSet.a(arrayOfObject, paramInt);
      while (localDBResultSet.a())
      {
        localAlertInstance = new AlertInstance();
        a(paramDBConnection, localDBResultSet, localAlertInstance);
        localArrayList1.add(localAlertInstance);
      }
      ArrayList localArrayList2 = localArrayList1;
      return localArrayList2;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdMethod_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  public static ArrayList jdMethod_do(DBConnection paramDBConnection, int paramInt, boolean paramBoolean)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      Object[] arrayOfObject = { new Long(paramBoolean ? 0L : -1L), new Integer(paramInt) };
      localDBResultSet = paramDBConnection.I("select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and type = ?");
      AlertInstance localAlertInstance = null;
      ArrayList localArrayList1 = new ArrayList();
      localDBResultSet.a(arrayOfObject);
      while (localDBResultSet.a())
      {
        localAlertInstance = new AlertInstance();
        a(paramDBConnection, localDBResultSet, localAlertInstance);
        localArrayList1.add(localAlertInstance);
      }
      ArrayList localArrayList2 = localArrayList1;
      return localArrayList2;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdMethod_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  public static ArrayList jdMethod_if(DBConnection paramDBConnection, int paramInt, boolean paramBoolean)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      Object[] arrayOfObject = { new Long(paramBoolean ? 0L : -1L), new Integer(paramInt) };
      localDBResultSet = paramDBConnection.I("select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and appId <> ? order by id");
      AlertInstance localAlertInstance = null;
      ArrayList localArrayList1 = new ArrayList();
      localDBResultSet.a(arrayOfObject);
      while (localDBResultSet.a())
      {
        localAlertInstance = new AlertInstance();
        a(paramDBConnection, localDBResultSet, localAlertInstance);
        localArrayList1.add(localAlertInstance);
      }
      ArrayList localArrayList2 = localArrayList1;
      return localArrayList2;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdMethod_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  public static ArrayList jdMethod_int(DBConnection paramDBConnection, int paramInt, boolean paramBoolean)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      Object[] arrayOfObject = { new Long(paramBoolean ? 0L : -1L), new Integer(paramInt) };
      localDBResultSet = paramDBConnection.I("select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and appId=? order by id");
      AlertInstance localAlertInstance = null;
      ArrayList localArrayList1 = new ArrayList();
      localDBResultSet.a(arrayOfObject);
      while (localDBResultSet.a())
      {
        localAlertInstance = new AlertInstance();
        a(paramDBConnection, localDBResultSet, localAlertInstance);
        localArrayList1.add(localAlertInstance);
      }
      ArrayList localArrayList2 = localArrayList1;
      return localArrayList2;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdMethod_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  public static ArrayList a(DBConnection paramDBConnection, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      Object[] arrayOfObject = { new Long(paramBoolean ? 0L : -1L), new Integer(paramInt1), new Integer(paramInt3), new Integer(paramInt2) };
      localDBResultSet = paramDBConnection.I("select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and appId=? and id > ? and exists( select di.id from AE_DeliveryInfo di where di.alertId=AE_Alert.id and di.channelId=? ) order by id");
      AlertInstance localAlertInstance = null;
      ArrayList localArrayList1 = new ArrayList();
      localDBResultSet.a(arrayOfObject, paramInt4);
      while ((localDBResultSet.a()) && (localArrayList1.size() < paramInt4))
      {
        localAlertInstance = new AlertInstance();
        a(paramDBConnection, localDBResultSet, localAlertInstance);
        localArrayList1.add(localAlertInstance);
      }
      ArrayList localArrayList2 = localArrayList1;
      return localArrayList2;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdMethod_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  public static ArrayList a(DBConnection paramDBConnection, int paramInt, String paramString, boolean paramBoolean)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      Object[] arrayOfObject;
      if (paramString == null)
      {
        localDBResultSet = paramDBConnection.I("select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and appId=? and userId is null order by id");
        arrayOfObject = new Object[] { new Long(paramBoolean ? 0L : -1L), new Integer(paramInt) };
      }
      else
      {
        localDBResultSet = paramDBConnection.I("select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and appId=? and userId=? order by id");
        arrayOfObject = new Object[] { new Long(paramBoolean ? 0L : -1L), new Integer(paramInt), paramString };
      }
      AlertInstance localAlertInstance = null;
      ArrayList localArrayList1 = new ArrayList();
      localDBResultSet.a(arrayOfObject);
      while (localDBResultSet.a())
      {
        localAlertInstance = new AlertInstance();
        a(paramDBConnection, localDBResultSet, localAlertInstance);
        localArrayList1.add(localAlertInstance);
      }
      ArrayList localArrayList2 = localArrayList1;
      return localArrayList2;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdMethod_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  public static ArrayList a(DBConnection paramDBConnection, String paramString, int paramInt, boolean paramBoolean)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      Object[] arrayOfObject;
      if (paramString == null)
      {
        localDBResultSet = paramDBConnection.I("select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and appId <> ? and userId is null order by id");
        arrayOfObject = new Object[] { new Long(paramBoolean ? 0L : -1L), new Integer(paramInt) };
      }
      else
      {
        localDBResultSet = paramDBConnection.I("select id,type,priority,appId,userId,messageInfo,properties,startDate,endDate,interval,intervalType,timeZone,sequence,nextRaised,lastScheduled from AE_Alert where nextRaised > ? and appId <> ? and userId=? order by id");
        arrayOfObject = new Object[] { new Long(paramBoolean ? 0L : -1L), new Integer(paramInt), paramString };
      }
      AlertInstance localAlertInstance = null;
      ArrayList localArrayList1 = new ArrayList();
      localDBResultSet.a(arrayOfObject);
      while (localDBResultSet.a())
      {
        localAlertInstance = new AlertInstance();
        a(paramDBConnection, localDBResultSet, localAlertInstance);
        localArrayList1.add(localAlertInstance);
      }
      ArrayList localArrayList2 = localArrayList1;
      return localArrayList2;
    }
    catch (SQLException localSQLException1)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException1));
    }
    finally
    {
      try
      {
        if (localDBResultSet != null) {
          localDBResultSet.jdMethod_for();
        }
      }
      catch (SQLException localSQLException2) {}
    }
  }
  
  static void a(DBConnection paramDBConnection, DBResultSet paramDBResultSet, AlertInstance paramAlertInstance)
    throws SQLException, AEException
  {
    a(paramDBConnection, paramDBResultSet, paramAlertInstance, true);
  }
  
  static void a(DBConnection paramDBConnection, DBResultSet paramDBResultSet, AlertInstance paramAlertInstance, boolean paramBoolean)
    throws SQLException, AEException
  {
    paramAlertInstance.j(paramDBResultSet.jdMethod_int(1));
    paramAlertInstance.m(paramDBResultSet.jdMethod_int(2));
    paramAlertInstance.r(paramDBResultSet.jdMethod_int(3));
    paramAlertInstance.n(paramDBResultSet.jdMethod_int(4));
    paramAlertInstance.D(paramDBResultSet.jdMethod_try(5));
    paramAlertInstance.jdMethod_if(paramDBResultSet.jdMethod_if(6));
    paramAlertInstance.q(paramDBResultSet.jdMethod_int(7));
    boolean bool = paramAlertInstance.l(1);
    long l1 = paramDBResultSet.jdMethod_char(8);
    long l2 = paramDBResultSet.jdMethod_char(9);
    long l3 = paramDBResultSet.jdMethod_char(10);
    int i = paramDBResultSet.jdMethod_int(11);
    byte[] arrayOfByte = paramDBResultSet.jdMethod_if(12);
    Object localObject1 = AEUtils.jdMethod_if(arrayOfByte);
    if ((localObject1 instanceof SimpleTimeZone))
    {
      localObject2 = (SimpleTimeZone)localObject1;
      paramAlertInstance.jdMethod_if(new AEScheduleInfo(l1, l2, l3, i, (SimpleTimeZone)localObject2, bool));
    }
    else
    {
      localObject2 = (TimeZone)localObject1;
      paramAlertInstance.jdMethod_if(new AEScheduleInfo(l1, l2, l3, i, (TimeZone)localObject2, bool));
    }
    paramAlertInstance.o(paramDBResultSet.jdMethod_int(13));
    paramAlertInstance.jdMethod_new(paramDBResultSet.jdMethod_char(14));
    paramAlertInstance.jdMethod_int(paramDBResultSet.jdMethod_char(15));
    Object localObject2 = DeliveryInfo.a(paramDBConnection, paramAlertInstance.getId(), paramBoolean);
    paramAlertInstance.jdMethod_if((DeliveryInfo[])localObject2);
    paramAlertInstance.jdMethod_if(new int[localObject2.length]);
  }
  
  public Object clone()
  {
    AlertInstance localAlertInstance = null;
    try
    {
      localAlertInstance = (AlertInstance)super.clone();
      IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = this.dT.getDeliveryInfo();
      if (arrayOfIAEDeliveryInfo != null)
      {
        int i = arrayOfIAEDeliveryInfo.length;
        localAlertInstance.jdMethod_if(new int[i]);
      }
    }
    catch (CloneNotSupportedException localCloneNotSupportedException) {}
    return localAlertInstance;
  }
  
  public int compareTo(Object paramObject)
  {
    try
    {
      AlertInstance localAlertInstance = (AlertInstance)paramObject;
      if (getNextRaised() < localAlertInstance.getNextRaised()) {
        return -1;
      }
      if (getNextRaised() == localAlertInstance.getNextRaised())
      {
        if (getPriority() < localAlertInstance.getPriority()) {
          return -1;
        }
        if (getPriority() == localAlertInstance.getPriority()) {
          return 1;
        }
        return 1;
      }
      return 1;
    }
    catch (ClassCastException localClassCastException)
    {
      AELog.a(localClassCastException, "Alert.compareTo() comparing non-Alert.", 1);
    }
    return 1;
  }
  
  public String toString()
  {
    return super.toString() + "( " + getId() + ", " + this.dU + " )";
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.AlertInstance
 * JD-Core Version:    0.7.0.1
 */