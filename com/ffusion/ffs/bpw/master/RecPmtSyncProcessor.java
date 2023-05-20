package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.scheduling.db.ScheduleConstants;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctFromV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSyncRqV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSyncRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTokenRqV1Un;

public class RecPmtSyncProcessor
  implements DBConsts, OFXConsts, BPWResource, ScheduleConstants
{
  private RecPmtProcessor ar = null;
  private int aq;
  
  public RecPmtSyncProcessor(RecPmtProcessor paramRecPmtProcessor)
  {
    this.ar = paramRecPmtProcessor;
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.aq = localPropertyConfig.LogLevel;
  }
  
  public TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    TypeRecPmtSyncRsV1 localTypeRecPmtSyncRsV1 = null;
    String str1 = "";
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      String str2 = paramTypeRecPmtSyncRqV1.RecPmtSyncRq.SyncRqV1Cm.TokenRqV1Un.__memberName;
      if (str2.equals("Refresh"))
      {
        localTypeRecPmtSyncRsV1 = processRecPmtRefreshRqV1(paramTypeRecPmtSyncRqV1, paramTypeUserData, str1);
      }
      else if (str2.equals("Token"))
      {
        localTypeRecPmtSyncRsV1 = processRecPmtTokenRqV1(paramTypeRecPmtSyncRqV1, paramTypeUserData, str1);
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
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("=== User error, OFXException:" + localOFXException.getExceptionMsg());
      localTypeRecPmtSyncRsV1 = a(paramTypeRecPmtSyncRqV1);
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str3 = localException.toString();
      FFSDebug.log("*** RecPmtSyncProcessor.processRecPmtSyncRqV1 failed:" + str3);
      localTypeRecPmtSyncRsV1 = a(paramTypeRecPmtSyncRqV1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeRecPmtSyncRsV1;
  }
  
  private TypeRecPmtSyncRsV1 a(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1)
  {
    TypeRecPmtSyncRsV1 localTypeRecPmtSyncRsV1 = new TypeRecPmtSyncRsV1();
    localTypeRecPmtSyncRsV1.RecPmtSyncRs = new TypeRecPmtSyncRsV1Aggregate();
    localTypeRecPmtSyncRsV1.RecPmtSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypeRecPmtSyncRsV1.RecPmtSyncRs.SyncRsV1Cm.Token = "-1";
    localTypeRecPmtSyncRsV1.RecPmtSyncRs.BankAcctFrom = paramTypeRecPmtSyncRqV1.RecPmtSyncRq.BankAcctFrom;
    return localTypeRecPmtSyncRsV1;
  }
  
  public TypeRecPmtSyncRsV1 processRecPmtRefreshRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    TypeRecPmtSyncRsV1 localTypeRecPmtSyncRsV1 = new TypeRecPmtSyncRsV1();
    localTypeRecPmtSyncRsV1.RecPmtSyncRs = new TypeRecPmtSyncRsV1Aggregate();
    localTypeRecPmtSyncRsV1.RecPmtSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypeRecPmtSyncRsV1.RecPmtSyncRs.SyncRsV1Cm.Token = "0";
    localTypeRecPmtSyncRsV1.RecPmtSyncRs.BankAcctFrom = paramTypeRecPmtSyncRqV1.RecPmtSyncRq.BankAcctFrom;
    try
    {
      AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
      if (localAuditAgent == null) {
        throw new BPWException("AuditAgent is null!!");
      }
      str1 = paramTypeRecPmtSyncRqV1.RecPmtSyncRq.BankAcctFrom.AcctID;
      String str2 = MsgBuilder.getAcctType(paramTypeRecPmtSyncRqV1.RecPmtSyncRq.BankAcctFrom.AcctType);
      String[] arrayOfString = localAuditAgent.getRecSrvrTrans(paramTypeUserData._uid, str1, str2, "OFX151RecPmtSync");
      int i = arrayOfString.length;
      TypeRecPmtTrnRsV1[] arrayOfTypeRecPmtTrnRsV1 = new TypeRecPmtTrnRsV1[i];
      for (int j = 0; j < i; j++)
      {
        String str3 = (String)arrayOfString[j];
        BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
        arrayOfTypeRecPmtTrnRsV1[j] = ((TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(str3, "RecPmtTrnRsV1", "OFX151"));
      }
      if (paramTypeRecPmtSyncRqV1.RecPmtSyncRq.RecPmtTrnRq == null)
      {
        localTypeRecPmtSyncRsV1.RecPmtSyncRs.RecPmtTrnRs = new TypeRecPmtTrnRsV1Aggregate[i];
        for (j = 0; j < i; j++) {
          localTypeRecPmtSyncRsV1.RecPmtSyncRs.RecPmtTrnRs[j] = arrayOfTypeRecPmtTrnRsV1[j].RecPmtTrnRs;
        }
      }
      j = paramTypeRecPmtSyncRqV1.RecPmtSyncRq.RecPmtTrnRq.length;
      int k = j + i;
      localTypeRecPmtSyncRsV1.RecPmtSyncRs.RecPmtTrnRs = new TypeRecPmtTrnRsV1Aggregate[k];
      for (int m = 0; m < j; m++)
      {
        TypeRecPmtTrnRqV1 localTypeRecPmtTrnRqV1 = new TypeRecPmtTrnRqV1(paramTypeRecPmtSyncRqV1.RecPmtSyncRq.RecPmtTrnRq[m]);
        localTypeRecPmtSyncRsV1.RecPmtSyncRs.RecPmtTrnRs[m] = this.ar.processRecPmtTrnRqV1(localTypeRecPmtTrnRqV1, paramTypeUserData, paramString).RecPmtTrnRs;
      }
      for (m = j; m < j + i; m++) {
        localTypeRecPmtSyncRsV1.RecPmtSyncRs.RecPmtTrnRs[m] = arrayOfTypeRecPmtTrnRsV1[(m - j)].RecPmtTrnRs;
      }
    }
    catch (Exception localException)
    {
      String str1 = "*** RecPmtSyncProcessor.processRecPmtRefreshRqV1 failed:";
      FFSDebug.log(localException, str1);
      FFSDebug.console("*** RecPmtSyncProcessor.processRecPmtRefreshRqV1 failed:" + localException.toString());
      throw new Exception(localException.toString());
    }
    return localTypeRecPmtSyncRsV1;
  }
  
  public TypeRecPmtSyncRsV1 processRecPmtTokenRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    return processRecPmtRefreshRqV1(paramTypeRecPmtSyncRqV1, paramTypeUserData, paramString);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.RecPmtSyncProcessor
 * JD-Core Version:    0.7.0.1
 */