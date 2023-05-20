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
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctFromUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSyncRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSyncRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTokenRqUn;

public class RecXferSyncProcessor2
  implements DBConsts, OFXConsts, BPWResource, ScheduleConstants
{
  RecXferProcessor2 jdField_byte = null;
  private PropertyConfig jdField_case;
  private int jdField_try = 1;
  
  public RecXferSyncProcessor2(RecXferProcessor2 paramRecXferProcessor2)
  {
    this.jdField_byte = paramRecXferProcessor2;
    this.jdField_case = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
    this.jdField_try = this.jdField_case.LogLevel;
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
      String str2 = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.SyncRqCm.TokenRqUn.__memberName;
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
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.SyncRsCm = new TypeSyncRsCm();
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.SyncRsCm.Token = "-1";
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.BCCAcctFromUn = new TypeBCCAcctFromUn();
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.BCCAcctFromUn.__memberName = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BCCAcctFromUn.__memberName;
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.BCCAcctFromUn.CCAcctFrom = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BCCAcctFromUn.CCAcctFrom;
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.BCCAcctFromUn.BankAcctFrom = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BCCAcctFromUn.BankAcctFrom;
    return localTypeRecIntraSyncRsV1;
  }
  
  public TypeRecIntraSyncRsV1 processRecIntraRefreshRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    TypeRecIntraSyncRsV1 localTypeRecIntraSyncRsV1 = new TypeRecIntraSyncRsV1();
    localTypeRecIntraSyncRsV1.RecIntraSyncRs = new TypeRecIntraSyncRsV1Aggregate();
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.SyncRsCm = new TypeSyncRsCm();
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.SyncRsCm.Token = "0";
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.BCCAcctFromUn = new TypeBCCAcctFromUn();
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.BCCAcctFromUn.__memberName = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BCCAcctFromUn.__memberName;
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.BCCAcctFromUn.CCAcctFrom = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BCCAcctFromUn.CCAcctFrom;
    localTypeRecIntraSyncRsV1.RecIntraSyncRs.BCCAcctFromUn.BankAcctFrom = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BCCAcctFromUn.BankAcctFrom;
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("AuditAgent is null!!");
    }
    String str1;
    String str2;
    if (paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BCCAcctFromUn.__memberName.equals("BankAcctFrom"))
    {
      str1 = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BCCAcctFromUn.BankAcctFrom.AcctID;
      str2 = MsgBuilder.getAcctType(paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BCCAcctFromUn.BankAcctFrom.AcctType);
    }
    else
    {
      str1 = paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BCCAcctFromUn.CCAcctFrom.AcctID;
      str2 = "CREDITCARD";
    }
    String str3 = MsgBuilder.getBankID(paramTypeRecIntraSyncRqV1.RecIntraSyncRq.BCCAcctFromUn);
    String[] arrayOfString = null;
    if (str3 == null) {
      arrayOfString = localAuditAgent.getRecSrvrTrans(paramTypeUserData._uid, str1, str2, "OFX200RecXferSync");
    } else {
      arrayOfString = localAuditAgent.getRecSrvrTrans(paramTypeUserData._uid, str1, str2, "OFX200RecXferSync", str3);
    }
    int i = arrayOfString.length;
    TypeRecIntraTrnRsV1[] arrayOfTypeRecIntraTrnRsV1 = new TypeRecIntraTrnRsV1[i];
    for (int j = 0; j < i; j++)
    {
      String str4 = (String)arrayOfString[j];
      BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
      arrayOfTypeRecIntraTrnRsV1[j] = ((TypeRecIntraTrnRsV1)localBPWMsgBroker.parseMsg(str4, "RecIntraTrnRsV1", "OFX200"));
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
      localTypeRecIntraSyncRsV1.RecIntraSyncRs.RecIntraTrnRs[n] = this.jdField_byte.processRecIntraTrnRqV1(localTypeRecIntraTrnRqV1, paramTypeUserData, paramString).RecIntraTrnRs;
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
 * Qualified Name:     com.ffusion.ffs.bpw.master.RecXferSyncProcessor2
 * JD-Core Version:    0.7.0.1
 */