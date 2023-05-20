package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.scheduling.db.ScheduleConstants;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSyncRqV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSyncRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTokenRqV1Un;

public class PayeeSyncProcessor
  implements DBConsts, FFSConst, OFXConsts, BPWResource, ScheduleConstants
{
  private PayeeProcessor o = null;
  private int n;
  
  public PayeeSyncProcessor(PayeeProcessor paramPayeeProcessor)
  {
    this.o = paramPayeeProcessor;
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.n = localPropertyConfig.LogLevel;
  }
  
  public TypePayeeSyncRsV1 processPayeeSyncRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    FFSDebug.log("Start PayeeSyncProcessor.processPayeeSyncRqV1 start, uid= " + PmtProcessor.getUID(paramTypeUserData), 6);
    TypePayeeSyncRsV1 localTypePayeeSyncRsV1 = null;
    String str1 = "";
    try
    {
      String str2 = paramTypePayeeSyncRqV1.PayeeSyncRq.SyncRqV1Cm.TokenRqV1Un.__memberName;
      if (str2.equals("Refresh"))
      {
        localTypePayeeSyncRsV1 = processPayeeRefreshRqV1(paramTypePayeeSyncRqV1, paramTypeUserData, str1);
      }
      else if (str2.equals("Token"))
      {
        localTypePayeeSyncRsV1 = processPayeeTokenRqV1(paramTypePayeeSyncRqV1, paramTypeUserData, str1);
      }
      else
      {
        if (str2.equals("TokenOnly")) {
          throw new OFXException("Token only requests not supported", 2000);
        }
        throw new OFXException("Unsupported request type", 2000);
      }
    }
    catch (OFXException localOFXException)
    {
      FFSDebug.log("=== User Error, OFXException:" + localOFXException.getExceptionMsg());
      localTypePayeeSyncRsV1 = jdMethod_if();
    }
    catch (Exception localException)
    {
      String str3 = localException.toString();
      FFSDebug.log("*** PayeeSyncProcessor.processPayeeRefreshRqV1 failed:" + str3);
      localTypePayeeSyncRsV1 = jdMethod_if();
    }
    FFSDebug.log("Start PayeeSyncProcessor.processPayeeSyncRqV1 end, uid= " + PmtProcessor.getUID(paramTypeUserData), 6);
    return localTypePayeeSyncRsV1;
  }
  
  private TypePayeeSyncRsV1 jdMethod_if()
  {
    TypePayeeSyncRsV1 localTypePayeeSyncRsV1 = new TypePayeeSyncRsV1();
    localTypePayeeSyncRsV1.PayeeSyncRs = new TypePayeeSyncRsV1Aggregate();
    localTypePayeeSyncRsV1.PayeeSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypePayeeSyncRsV1.PayeeSyncRs.SyncRsV1Cm.Token = "-1";
    return localTypePayeeSyncRsV1;
  }
  
  public TypePayeeSyncRsV1 processPayeeRefreshRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    TypePayeeSyncRsV1 localTypePayeeSyncRsV1 = new TypePayeeSyncRsV1();
    localTypePayeeSyncRsV1.PayeeSyncRs = new TypePayeeSyncRsV1Aggregate();
    localTypePayeeSyncRsV1.PayeeSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypePayeeSyncRsV1.PayeeSyncRs.SyncRsV1Cm.Token = "0";
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
      if (localAuditAgent == null) {
        throw new BPWException("AuditAgent is null!!");
      }
      str = PmtProcessor.getUID(paramTypeUserData);
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      TypePayeeTrnRsV1[] arrayOfTypePayeeTrnRsV1 = localAuditAgent.getOFX151PayeeList(str, localFFSConnectionHolder);
      int i = arrayOfTypePayeeTrnRsV1.length;
      if (paramTypePayeeSyncRqV1.PayeeSyncRq.PayeeTrnRq == null)
      {
        localTypePayeeSyncRsV1.PayeeSyncRs.PayeeTrnRs = new TypePayeeTrnRsV1Aggregate[i];
        for (j = 0; j < i; j++) {
          localTypePayeeSyncRsV1.PayeeSyncRs.PayeeTrnRs[j] = arrayOfTypePayeeTrnRsV1[j].PayeeTrnRs;
        }
      }
      int j = paramTypePayeeSyncRqV1.PayeeSyncRq.PayeeTrnRq.length;
      int k = j + i;
      localTypePayeeSyncRsV1.PayeeSyncRs.PayeeTrnRs = new TypePayeeTrnRsV1Aggregate[k];
      for (int m = 0; m < j; m++)
      {
        TypePayeeTrnRqV1 localTypePayeeTrnRqV1 = new TypePayeeTrnRqV1(paramTypePayeeSyncRqV1.PayeeSyncRq.PayeeTrnRq[m]);
        localTypePayeeSyncRsV1.PayeeSyncRs.PayeeTrnRs[m] = this.o.processPayeeTrnRqV1(localTypePayeeTrnRqV1, paramTypeUserData, paramString).PayeeTrnRs;
      }
      for (m = j; m < j + i; m++) {
        localTypePayeeSyncRsV1.PayeeSyncRs.PayeeTrnRs[m] = arrayOfTypePayeeTrnRsV1[(m - j)].PayeeTrnRs;
      }
    }
    catch (Exception localException)
    {
      String str = "*** PayeeSyncProcessor.processPayeeRefreshRqV1 failed:";
      FFSDebug.log(localException, str);
      FFSDebug.console(str + localException.toString());
      throw new Exception(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypePayeeSyncRsV1;
  }
  
  public TypePayeeSyncRsV1 processPayeeTokenRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    return processPayeeRefreshRqV1(paramTypePayeeSyncRqV1, paramTypeUserData, paramString);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.PayeeSyncProcessor
 * JD-Core Version:    0.7.0.1
 */