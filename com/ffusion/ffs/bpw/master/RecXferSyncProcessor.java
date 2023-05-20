package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
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
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSyncRqV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSyncRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTokenRqV1Un;

public class RecXferSyncProcessor
  implements DBConsts, OFXConsts, BPWResource, ScheduleConstants
{
  RecXferProcessor jdField_int = null;
  private PropertyConfig jdField_new;
  private int jdField_for = 1;
  
  public RecXferSyncProcessor(RecXferProcessor paramRecXferProcessor)
  {
    this.jdField_int = paramRecXferProcessor;
    this.jdField_new = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
    this.jdField_for = this.jdField_new.LogLevel;
  }
  
  public TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    TypeRecIntraSyncRsV1 localTypeRecIntraSyncRsV1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    String str1 = "";
    try
    {
      String str2 = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.SyncRqV1Cm.TokenRqV1Un.__memberName;
      if (str2.equals("Refresh"))
      {
        localTypeRecIntraSyncRsV1 = processRecIntraRefreshRqV1(paramTypeRecIntraSyncRqV1, paramTypeUserData, str1);
      }
      else if (str2.equals("Token"))
      {
        localTypeRecIntraSyncRsV1 = processRecIntraTokenRqV1(paramTypeRecIntraSyncRqV1, paramTypeUserData, str1);
      }
      else
      {
        if (str2.equals("TokenOnly")) {
          throw new BPWException("This server doesn't support token only request.");
        }
        throw new BPWException("Unsupported sync option!");
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str3 = "RecXferSyncProcessor.processRecIntraSyncRqV1 failed : ";
      FFSDebug.log(localException, str3);
      localTypeRecIntraSyncRsV1 = a(paramTypeRecIntraSyncRqV1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeRecIntraSyncRsV1;
  }
  
  private TypeRecIntraSyncRsV1 a(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1)
  {
    TypeRecIntraSyncRsV1 localTypeRecIntraSyncRsV1 = new TypeRecIntraSyncRsV1();
    localTypeRecIntraSyncRsV1.RecIntraSyncRs = new TypeRecIntraSyncRsV1Aggregate();
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.SyncRsV1Cm.Token = "-1";
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.BankAcctFrom = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BankAcctFrom;
    return localTypeRecIntraSyncRsV1;
  }
  
  public TypeRecIntraSyncRsV1 processRecIntraRefreshRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    TypeRecIntraSyncRsV1 localTypeRecIntraSyncRsV1 = new TypeRecIntraSyncRsV1();
    localTypeRecIntraSyncRsV1.RecIntraSyncRs = new TypeRecIntraSyncRsV1Aggregate();
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.SyncRsV1Cm.Token = "0";
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.BankAcctFrom = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BankAcctFrom;
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("AuditAgent is null!!");
    }
    String str1 = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BankAcctFrom.AcctID;
    String str2 = MsgBuilder.getAcctType(paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BankAcctFrom.AcctType);
    String str3 = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BankAcctFrom.BankID;
    String[] arrayOfString = localAuditAgent.getRecSrvrTrans(paramTypeUserData._uid, str1, str2, "OFX151RecXferSync", str3);
    int i = arrayOfString.length;
    TypeRecIntraTrnRsV1[] arrayOfTypeRecIntraTrnRsV1 = new TypeRecIntraTrnRsV1[i];
    for (int j = 0; j < i; j++)
    {
      String str4 = (String)arrayOfString[j];
      BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
      arrayOfTypeRecIntraTrnRsV1[j] = ((TypeRecIntraTrnRsV1)localBPWMsgBroker.parseMsg(str4, "RecIntraTrnRsV1", "OFX151"));
    }
    j = arrayOfTypeRecIntraTrnRsV1.length;
    if (paramTypeRecIntraSyncRqV1.RecIntraSyncRq.RecIntraTrnRq == null)
    {
      localTypeRecIntraSyncRsV1.RecIntraSyncRs.RecIntraTrnRs = new TypeRecIntraTrnRsV1Aggregate[j];
      for (k = 0; k < j; k++) {
        localTypeRecIntraSyncRsV1.RecIntraSyncRs.RecIntraTrnRs[k] = arrayOfTypeRecIntraTrnRsV1[k].RecIntraTrnRs;
      }
    }
    int k = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.RecIntraTrnRq.length;
    int m = k + j;
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.RecIntraTrnRs = new TypeRecIntraTrnRsV1Aggregate[m];
    for (int n = 0; n < k; n++)
    {
      TypeRecIntraTrnRqV1 localTypeRecIntraTrnRqV1 = new TypeRecIntraTrnRqV1(paramTypeRecIntraSyncRqV1.RecIntraSyncRq.RecIntraTrnRq[n]);
      localTypeRecIntraSyncRsV1.RecIntraSyncRs.RecIntraTrnRs[n] = this.jdField_int.processRecIntraTrnRqV1(localTypeRecIntraTrnRqV1, paramTypeUserData, paramString).RecIntraTrnRs;
    }
    for (n = k; n < k + j; n++) {
      localTypeRecIntraSyncRsV1.RecIntraSyncRs.RecIntraTrnRs[n] = arrayOfTypeRecIntraTrnRsV1[(n - k)].RecIntraTrnRs;
    }
    return localTypeRecIntraSyncRsV1;
  }
  
  public TypeRecIntraSyncRsV1 processRecIntraTokenRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    return processRecIntraRefreshRqV1(paramTypeRecIntraSyncRqV1, paramTypeUserData, paramString);
  }
  
  public String getUID(TypeUserData paramTypeUserData)
  {
    return paramTypeUserData._uid;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.RecXferSyncProcessor
 * JD-Core Version:    0.7.0.1
 */