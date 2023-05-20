package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.shared.AELog;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlertRecovery
  implements DBSQLConstants
{
  private static final String e3 = "select a.id,a.type,a.priority,a.appId,a.userId,a.messageInfo,a.properties,a.startDate,a.endDate,a.interval,a.intervalType,a.timeZone,a.sequence,a.nextRaised,a.lastScheduled,cr4.alertSequence,cr4.timeInfo from AE_Alert a {left outer join|left outer join|left outer join|,|left outer join} AE_ALogMaxSeqDisp cr4 {on|on|on|where|on} (a.id = cr4.alertId{|||(+)|}) {where|where|where|and|where} a.sequence > 0 and not (a.sequence <= cr4.alertSequence and cr4.alertSequence is not null ) order by a.id";
  private static final String e1 = "select cr4.alertId, cr4.alertSequence, cr4.timeInfo, cr4.deliveryInfoId, cr5.status from AE_DInfoALogIdeal cr4 {left outer join|left outer join|left outer join|,|left outer join} AE_CrashRecovery cr5 {on|on|on|where|on} ( cr4.deliveryInfoId = cr5.deliveryInfoId{|||(+)|} and cr4.alertId = cr5.alertId{|||(+)|} and cr4.alertSequence = cr5.alertSequence{|||(+)|} ) order by cr4.alertId, cr4.alertSequence, cr4.deliveryOrder, cr4.deliveryInfoId";
  private static final String e0 = "delete from AE_CrashRecovery where AE_CrashRecovery.auditTime < ? and exists( select * from AE_CrashRecovery cr2 where cr2.alertId = AE_CrashRecovery.alertId and cr2.alertSequence = AE_CrashRecovery.alertSequence and cr2.auditTime < ? and cr2.auditType = 3) and ( exists( select * from AE_CrashRecovery cr3 where cr3.alertId = AE_CrashRecovery.alertId and cr3.alertSequence > AE_CrashRecovery.alertSequence ) or AE_CrashRecovery.alertSequence = ( select a.sequence from AE_Alert a where a.id = AE_CrashRecovery.alertId and a.nextRaised = 0 ) ) ";
  private static final String e2 = "delete from AE_AuditLog where AE_AuditLog.auditTime < ? and exists( select * from AE_AuditLog al2 where al2.alertId = AE_AuditLog.alertId and al2.alertSequence = AE_AuditLog.alertSequence and al2.auditTime < ? and al2.auditType = 3) ";
  
  public static void jdMethod_do(AERepository paramAERepository, DBConnection paramDBConnection)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      localDBResultSet = paramDBConnection.I("select a.id,a.type,a.priority,a.appId,a.userId,a.messageInfo,a.properties,a.startDate,a.endDate,a.interval,a.intervalType,a.timeZone,a.sequence,a.nextRaised,a.lastScheduled,cr4.alertSequence,cr4.timeInfo from AE_Alert a {left outer join|left outer join|left outer join|,|left outer join} AE_ALogMaxSeqDisp cr4 {on|on|on|where|on} (a.id = cr4.alertId{|||(+)|}) {where|where|where|and|where} a.sequence > 0 and not (a.sequence <= cr4.alertSequence and cr4.alertSequence is not null ) order by a.id");
      localDBResultSet.jdMethod_try();
      while (localDBResultSet.a())
      {
        AlertInstance localAlertInstance = new AlertInstance();
        AlertInstance.a(paramDBConnection, localDBResultSet, localAlertInstance);
        int i = localDBResultSet.jdMethod_int(16);
        long l1 = localDBResultSet.jdMethod_char(17);
        localAlertInstance.o(i);
        AEScheduleInfo localAEScheduleInfo = localAlertInstance.getScheduleInfo();
        long l2;
        if (l1 == 0L)
        {
          l2 = localAEScheduleInfo.getStart();
        }
        else
        {
          l2 = l1 + localAEScheduleInfo.getInterval();
          if (l2 >= localAEScheduleInfo.getEnd()) {
            l2 = 0L;
          }
        }
        localAlertInstance.jdMethod_new(l2);
        localAlertInstance.jdMethod_int(l1);
        localAlertInstance.a(paramAERepository, paramDBConnection, l2);
        AELog.a("AlertRecovery - reset alert ", localAlertInstance.getId(), " to sequence ", localAlertInstance.getSequence(), 1);
      }
      localDBResultSet.jdMethod_for();
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
  
  public static ArrayList e(DBConnection paramDBConnection)
    throws AEException
  {
    DBResultSet localDBResultSet = null;
    try
    {
      ArrayList localArrayList1 = new ArrayList();
      AlertInstance localAlertInstance = null;
      DeliveryInfo[] arrayOfDeliveryInfo = null;
      int i = 0;
      localDBResultSet = paramDBConnection.I("select cr4.alertId, cr4.alertSequence, cr4.timeInfo, cr4.deliveryInfoId, cr5.status from AE_DInfoALogIdeal cr4 {left outer join|left outer join|left outer join|,|left outer join} AE_CrashRecovery cr5 {on|on|on|where|on} ( cr4.deliveryInfoId = cr5.deliveryInfoId{|||(+)|} and cr4.alertId = cr5.alertId{|||(+)|} and cr4.alertSequence = cr5.alertSequence{|||(+)|} ) order by cr4.alertId, cr4.alertSequence, cr4.deliveryOrder, cr4.deliveryInfoId");
      localDBResultSet.jdMethod_try();
      while (localDBResultSet.a())
      {
        int j = localDBResultSet.jdMethod_int(1);
        int k = localDBResultSet.jdMethod_int(2);
        long l = localDBResultSet.jdMethod_char(3);
        int m = localDBResultSet.jdMethod_int(4);
        int n = localDBResultSet.jdMethod_int(5);
        if (localDBResultSet.jdMethod_new()) {
          n = 1;
        }
        if ((localAlertInstance == null) || (localAlertInstance.getId() != j))
        {
          localAlertInstance = AlertInstance.jdMethod_do(paramDBConnection, j);
          localAlertInstance.o(k);
          localAlertInstance.jdMethod_new(l);
          arrayOfDeliveryInfo = (DeliveryInfo[])localAlertInstance.getDeliveryInfo();
          i = 0;
        }
        else if (localAlertInstance.getSequence() != k)
        {
          localAlertInstance = (AlertInstance)localAlertInstance.clone();
          localAlertInstance.o(k);
          localAlertInstance.jdMethod_new(l);
          arrayOfDeliveryInfo = (DeliveryInfo[])localAlertInstance.getDeliveryInfo();
          i = 0;
        }
        if (arrayOfDeliveryInfo[i].getId() != m) {
          throw new RuntimeException("Wrong id for DeliveryInfo, expected " + m + " got: " + arrayOfDeliveryInfo[i].getId());
        }
        localAlertInstance.jdMethod_do(i, localDBResultSet.jdMethod_new() ? 1 : arrayOfDeliveryInfo[i].a() ? -1 : n);
        i++;
        if (i == arrayOfDeliveryInfo.length) {
          localArrayList1.add(localAlertInstance);
        }
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
  
  public static void jdMethod_if(DBConnection paramDBConnection, long paramLong)
    throws AEException
  {
    try
    {
      Long localLong = new Long(paramLong);
      Object[] arrayOfObject = { localLong, localLong };
      int i = paramDBConnection.jdMethod_if("delete from AE_AuditLog where AE_AuditLog.auditTime < ? and exists( select * from AE_AuditLog al2 where al2.alertId = AE_AuditLog.alertId and al2.alertSequence = AE_AuditLog.alertSequence and al2.auditTime < ? and al2.auditType = 3) ", arrayOfObject);
    }
    catch (SQLException localSQLException)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException));
    }
  }
  
  public static void jdMethod_do(DBConnection paramDBConnection, long paramLong)
    throws AEException
  {
    try
    {
      Long localLong = new Long(paramLong);
      Object[] arrayOfObject = { localLong, localLong };
      int i = paramDBConnection.jdMethod_if("delete from AE_CrashRecovery where AE_CrashRecovery.auditTime < ? and exists( select * from AE_CrashRecovery cr2 where cr2.alertId = AE_CrashRecovery.alertId and cr2.alertSequence = AE_CrashRecovery.alertSequence and cr2.auditTime < ? and cr2.auditType = 3) and ( exists( select * from AE_CrashRecovery cr3 where cr3.alertId = AE_CrashRecovery.alertId and cr3.alertSequence > AE_CrashRecovery.alertSequence ) or AE_CrashRecovery.alertSequence = ( select a.sequence from AE_Alert a where a.id = AE_CrashRecovery.alertId and a.nextRaised = 0 ) ) ", arrayOfObject);
    }
    catch (SQLException localSQLException)
    {
      throw new AEException(1, DBSqlUtils.a(localSQLException));
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.AlertRecovery
 * JD-Core Version:    0.7.0.1
 */