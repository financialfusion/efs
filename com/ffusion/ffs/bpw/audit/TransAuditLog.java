package com.ffusion.ffs.bpw.audit;

import com.ffusion.ffs.bpw.db.BPWAuditLogRecord;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.enums.UserType;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.enums.UserAssignedAmount;
import com.ffusion.util.logging.AuditAdapter;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.AuditLogUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.LinkedList;

public class TransAuditLog
{
  private static void a(AuditLogRecord paramAuditLogRecord, Connection paramConnection)
    throws FFSException
  {
    String str1 = "TransAuditLog.log: ";
    LinkedList localLinkedList = new LinkedList();
    localLinkedList = a(paramAuditLogRecord);
    try
    {
      AuditAdapter.writeRecords(paramConnection, localLinkedList);
    }
    catch (Exception localException)
    {
      FFSDebug.log(str1 + "Error while logging: " + paramAuditLogRecord.toString());
      String str2 = FFSDebug.stackTrace(localException);
      throw new FFSException(str2);
    }
  }
  
  private static void a(BPWAuditLogRecord paramBPWAuditLogRecord, Connection paramConnection)
    throws FFSException
  {
    String str1 = "TransAuditLog.log: ";
    LinkedList localLinkedList = new LinkedList();
    localLinkedList = a(paramBPWAuditLogRecord);
    try
    {
      AuditAdapter.writeRecords(paramConnection, localLinkedList);
    }
    catch (Exception localException)
    {
      FFSDebug.log(str1 + "Error while logging: " + paramBPWAuditLogRecord.recordToString());
      String str2 = FFSDebug.stackTrace(localException);
      throw new FFSException(str2);
    }
  }
  
  public static void logTransAuditLog(AuditLogRecord paramAuditLogRecord, Connection paramConnection)
    throws FFSException
  {
    a(paramAuditLogRecord, paramConnection);
  }
  
  public static void logTransAuditLog(BPWAuditLogRecord paramBPWAuditLogRecord, Connection paramConnection)
    throws FFSException
  {
    a(paramBPWAuditLogRecord, paramConnection);
  }
  
  public static void logTransAuditLog(Connection paramConnection, String paramString1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, int paramInt3)
    throws FFSException
  {
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramString1, paramString2, paramString3, paramILocalizable, paramString4, paramInt1, paramInt2, paramBigDecimal, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramInt3);
    a(localAuditLogRecord, paramConnection);
  }
  
  public static void logTransAuditLog(Connection paramConnection, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt3)
    throws FFSException
  {
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramString1, paramString2, paramString3, paramString4, paramString5, paramInt1, paramInt2, paramBigDecimal, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramInt3);
    a(localAuditLogRecord, paramConnection);
  }
  
  public static void logTransAuditLog(Connection paramConnection, String paramString1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt1, int paramInt2, BigDecimal paramBigDecimal1, String paramString5, BigDecimal paramBigDecimal2, String paramString6, UserAssignedAmount paramUserAssignedAmount, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt3)
    throws FFSException
  {
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramString1, 0, paramString2, paramString3, paramILocalizable, paramString4, paramInt1, paramInt2, paramBigDecimal1, paramString5, paramBigDecimal2, paramString6, paramUserAssignedAmount, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramInt3);
    a(localAuditLogRecord, paramConnection);
  }
  
  private static LinkedList a(BPWAuditLogRecord paramBPWAuditLogRecord)
    throws FFSException
  {
    String str1 = "TransAuditLog.prepAuditLogRecord";
    if ((paramBPWAuditLogRecord.getUserType() != null) && (paramBPWAuditLogRecord.getUserType().equals(UserType.CONSUMER)))
    {
      paramBPWAuditLogRecord.setBusinessID(0);
    }
    else if ((paramBPWAuditLogRecord.getBusinessID() != 0) && (paramBPWAuditLogRecord.getUserID().equals(Integer.toString(paramBPWAuditLogRecord.getBusinessID()))))
    {
      paramBPWAuditLogRecord.setBusinessID(0);
    }
    else if (paramBPWAuditLogRecord.getBusinessID() != 0)
    {
      localObject1 = new FFSConnectionHolder();
      try
      {
        ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
        if (((FFSConnectionHolder)localObject1).conn == null)
        {
          localObject2 = str1 + " failed: Failed to obtain a " + "connection from from the BPTW connection pool.";
          FFSDebug.log((String)localObject2, 0);
          throw new FFSException((String)localObject2);
        }
        Object localObject2 = Customer.getCustomerByID(Integer.toString(paramBPWAuditLogRecord.getBusinessID()), (FFSConnectionHolder)localObject1);
        if (((CustomerInfo)localObject2).getResolvedUserType().equals(UserType.CONSUMER)) {
          paramBPWAuditLogRecord.setBusinessID(0);
        }
      }
      catch (BPWException localBPWException)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        String str2 = str1 + " failed: " + FFSDebug.stackTrace(localBPWException);
        FFSDebug.log(str2, 0);
        throw new FFSException(str2);
      }
      finally
      {
        if (((FFSConnectionHolder)localObject1).conn != null) {
          DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
        }
      }
    }
    Object localObject1 = new AuditLogRecord(paramBPWAuditLogRecord.getUserID(), paramBPWAuditLogRecord.getPrimaryUserID(), paramBPWAuditLogRecord.getAgentID(), paramBPWAuditLogRecord.getAgentType(), paramBPWAuditLogRecord.getLocalizableMessage(), paramBPWAuditLogRecord.getTranID(), paramBPWAuditLogRecord.getTranType(), paramBPWAuditLogRecord.getBusinessID(), paramBPWAuditLogRecord.getAmountValue(), paramBPWAuditLogRecord.getCurrencyCode(), paramBPWAuditLogRecord.getSrvrTid(), paramBPWAuditLogRecord.getState(), paramBPWAuditLogRecord.getToAcctID(), paramBPWAuditLogRecord.getToAcctRtgNum(), paramBPWAuditLogRecord.getFromAcctID(), paramBPWAuditLogRecord.getFromAcctRtgNum(), paramBPWAuditLogRecord.getModule());
    return a((AuditLogRecord)localObject1);
  }
  
  private static LinkedList a(AuditLogRecord paramAuditLogRecord)
    throws FFSException
  {
    String str1 = "TransAuditLog.prepAuditLogRecord";
    if (paramAuditLogRecord.getTranID() == null)
    {
      localObject1 = str1 + " failed: tranId cannot be null";
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException((String)localObject1);
    }
    if (paramAuditLogRecord.getUserID() == null)
    {
      localObject1 = str1 + " failed: userId cannot be null";
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException((String)localObject1);
    }
    paramAuditLogRecord.setPrimaryUserID(paramAuditLogRecord.getBusinessID());
    if ((paramAuditLogRecord.getBusinessID() != 0) && (paramAuditLogRecord.getUserID().equals(Integer.toString(paramAuditLogRecord.getBusinessID()))))
    {
      paramAuditLogRecord.setBusinessID(0);
    }
    else if (paramAuditLogRecord.getBusinessID() != 0)
    {
      localObject1 = new FFSConnectionHolder();
      try
      {
        ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
        if (((FFSConnectionHolder)localObject1).conn == null)
        {
          localObject2 = str1 + " failed: Failed to obtain a " + "connection from from the BPTW connection pool.";
          FFSDebug.log((String)localObject2, 0);
          throw new FFSException((String)localObject2);
        }
        Object localObject2 = Customer.getCustomerByID(Integer.toString(paramAuditLogRecord.getBusinessID()), (FFSConnectionHolder)localObject1);
        if (localObject2 != null) {
          if (((CustomerInfo)localObject2).getResolvedUserType().equals(UserType.CONSUMER)) {
            paramAuditLogRecord.setBusinessID(0);
          } else if ((((CustomerInfo)localObject2).getResolvedUserType().equals(UserType.BUSINESS)) && (paramAuditLogRecord.getUserID() != null) && (paramAuditLogRecord.getUserID().length() > 0)) {
            try
            {
              paramAuditLogRecord.setPrimaryUserID(Integer.parseInt(paramAuditLogRecord.getUserID()));
            }
            catch (NumberFormatException localNumberFormatException) {}
          }
        }
      }
      catch (BPWException localBPWException)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        String str3 = str1 + " failed: " + FFSDebug.stackTrace(localBPWException);
        FFSDebug.log(str3, 0);
        throw new FFSException(str3);
      }
      finally
      {
        if (((FFSConnectionHolder)localObject1).conn != null) {
          DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
        }
      }
    }
    Object localObject1 = new LinkedList();
    String str2 = "TransAuditLog.log: ";
    if ((paramAuditLogRecord.getCurrencyCode() == null) || (paramAuditLogRecord.getCurrencyCode().trim().length() == 0)) {
      paramAuditLogRecord.setCurrencyCode(BPWLocaleUtil.getCurCode());
    }
    try
    {
      paramAuditLogRecord.setAmount(new BigDecimal(paramAuditLogRecord.getAmount()).setScale(2, 6));
    }
    catch (Exception localException) {}
    paramAuditLogRecord.setModule(AuditLogUtil.getModuleFromTranType(paramAuditLogRecord.getTranType()));
    ((LinkedList)localObject1).add(paramAuditLogRecord);
    return localObject1;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.audit.TransAuditLog
 * JD-Core Version:    0.7.0.1
 */