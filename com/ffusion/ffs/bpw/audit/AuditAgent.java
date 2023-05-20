package com.ffusion.ffs.bpw.audit;

import com.ffusion.ffs.bpw.db.RecSrvrTrans;
import com.ffusion.ffs.bpw.db.RecXferInstruction;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeAggregate;

public class AuditAgent
  implements DBConsts
{
  OFX151Audit c1 = new OFX151Audit();
  OFX200Audit c0 = new OFX200Audit();
  
  public void savePmtRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1 paramTypePmtTrnRsV1, String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c1.savePmtRsV1(paramTypePmtTrnRsV1, paramString1, paramString2, paramFFSConnectionHolder);
  }
  
  public void savePmtRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1 paramTypePmtTrnRsV1, String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c0.savePmtRsV1(paramTypePmtTrnRsV1, paramString1, paramString2, paramFFSConnectionHolder);
  }
  
  public void modPmtRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1 paramTypePmtTrnRsV1, PmtInfo paramPmtInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c1.modPmtRsV1(paramTypePmtTrnRsV1, paramPmtInfo, paramFFSConnectionHolder);
  }
  
  public void modPmtRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1 paramTypePmtTrnRsV1, PmtInfo paramPmtInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c0.modPmtRsV1(paramTypePmtTrnRsV1, paramPmtInfo, paramFFSConnectionHolder);
  }
  
  public void cancelOFX151PmtRsV1(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramString, paramFFSConnectionHolder);
    if (arrayOfString[0] == null) {
      throw new BPWException(" *** AuditAgent.cancelOFX151PmtRsV1: Response not found!");
    }
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1 localTypePmtTrnRsV1 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "PmtTrnRsV1", "OFX151");
    this.c1.cancelPmtRsV1(localTypePmtTrnRsV1, paramFFSConnectionHolder);
  }
  
  public void cancelOFX200PmtRsV1(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramString, paramFFSConnectionHolder);
    if (arrayOfString[0] == null) {
      throw new BPWException(" *** AuditAgent.cancelOFX200PmtRsV1: Response not found!");
    }
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1 localTypePmtTrnRsV1 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "PmtTrnRsV1", "OFX200");
    this.c0.cancelPmtRsV1(localTypePmtTrnRsV1, paramFFSConnectionHolder);
  }
  
  public void updatePmtRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1 paramTypePmtTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    this.c1.updatePmtRsV1(paramTypePmtTrnRsV1, paramFFSConnectionHolder, paramString);
  }
  
  public void updatePmtRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1 paramTypePmtTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    this.c0.updatePmtRsV1(paramTypePmtTrnRsV1, paramFFSConnectionHolder, paramString);
  }
  
  public void saveXferRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1 paramTypeIntraTrnRsV1, String paramString1, FFSConnectionHolder paramFFSConnectionHolder, String paramString2)
    throws BPWException
  {
    this.c1.saveXferRsV1(paramTypeIntraTrnRsV1, paramString1, paramFFSConnectionHolder, paramString2);
  }
  
  public void saveXferRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1 paramTypeIntraTrnRsV1, String paramString1, FFSConnectionHolder paramFFSConnectionHolder, String paramString2)
    throws BPWException
  {
    this.c0.saveXferRsV1(paramTypeIntraTrnRsV1, paramString1, paramFFSConnectionHolder, paramString2);
  }
  
  public void saveWireRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRsV1 paramTypeWireTrnRsV1, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c1.saveWireRsV1(paramTypeWireTrnRsV1, paramString, paramFFSConnectionHolder);
  }
  
  public void saveWireRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireTrnRsV1 paramTypeWireTrnRsV1, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c0.saveWireRsV1(paramTypeWireTrnRsV1, paramString, paramFFSConnectionHolder);
  }
  
  public void modXferRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1 paramTypeIntraTrnRsV1, XferInstruction paramXferInstruction, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c1.modXferRsV1(paramTypeIntraTrnRsV1, paramXferInstruction, paramFFSConnectionHolder);
  }
  
  public void modXferRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1 paramTypeIntraTrnRsV1, XferInstruction paramXferInstruction, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c0.modXferRsV1(paramTypeIntraTrnRsV1, paramXferInstruction, paramFFSConnectionHolder);
  }
  
  public void updateXferRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1 paramTypeIntraTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    this.c1.updateXferRsV1(paramTypeIntraTrnRsV1, paramFFSConnectionHolder, paramString);
  }
  
  public void updateXferRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1 paramTypeIntraTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    this.c0.updateXferRsV1(paramTypeIntraTrnRsV1, paramFFSConnectionHolder, paramString);
  }
  
  public void updateWireRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRsV1 paramTypeWireTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    this.c1.updateWireRsV1(paramTypeWireTrnRsV1, paramFFSConnectionHolder, paramString1, paramString2);
  }
  
  public void updateWireRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireTrnRsV1 paramTypeWireTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    this.c0.updateWireRsV1(paramTypeWireTrnRsV1, paramFFSConnectionHolder, paramString1, paramString2);
  }
  
  public void cancelOFX151XferRsV1(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramString, paramFFSConnectionHolder);
    if (arrayOfString[0] == null) {
      throw new BPWException(" *** AuditAgent.cancelOFX151XferRsV1: Response not found!");
    }
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "IntraTrnRsV1", "OFX151");
    this.c1.cancelXferRsV1(localTypeIntraTrnRsV1, paramFFSConnectionHolder);
  }
  
  public void cancelOFX200XferRsV1(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramString, paramFFSConnectionHolder);
    if (arrayOfString[0] == null) {
      throw new BPWException(" *** AuditAgent.cancelOFX151XferRsV1: Response not found!");
    }
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "IntraTrnRsV1", "OFX200");
    this.c0.cancelXferRsV1(localTypeIntraTrnRsV1, paramFFSConnectionHolder);
  }
  
  public void cancelOFX151WireRsV1(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramString, paramFFSConnectionHolder);
    if (arrayOfString[0] == null) {
      throw new BPWException(" *** AuditAgent.cancelOFX151WireRsV1: Response not found!");
    }
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRsV1 localTypeWireTrnRsV1 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "WireTrnRsV1", "OFX151");
    this.c1.cancelWireRsV1(localTypeWireTrnRsV1, paramFFSConnectionHolder);
  }
  
  public void cancelOFX200WireRsV1(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramString, paramFFSConnectionHolder);
    if (arrayOfString[0] == null) {
      throw new BPWException(" *** AuditAgent.cancelOFX200WireRsV1: Response not found!");
    }
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireTrnRsV1 localTypeWireTrnRsV1 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "WireTrnRsV1", "OFX200");
    this.c0.cancelWireRsV1(localTypeWireTrnRsV1, paramFFSConnectionHolder);
  }
  
  public void saveRecXferRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c1.saveRecXferRsV1(paramTypeRecIntraTrnRsV1, paramString, paramFFSConnectionHolder);
  }
  
  public void saveRecXferRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c0.saveRecXferRsV1(paramTypeRecIntraTrnRsV1, paramString, paramFFSConnectionHolder);
  }
  
  public void updateRecXferRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    this.c1.updateRecXferRsV1(paramTypeRecIntraTrnRsV1, paramFFSConnectionHolder, paramString);
  }
  
  public void updateRecXferRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    this.c0.updateRecXferRsV1(paramTypeRecIntraTrnRsV1, paramFFSConnectionHolder, paramString);
  }
  
  public void modRecXferRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1, RecXferInstruction paramRecXferInstruction, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c1.modRecXferRsV1(paramTypeRecIntraTrnRsV1, paramRecXferInstruction, paramFFSConnectionHolder);
  }
  
  public void modRecXferRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1, RecXferInstruction paramRecXferInstruction, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c0.modRecXferRsV1(paramTypeRecIntraTrnRsV1, paramRecXferInstruction, paramFFSConnectionHolder);
  }
  
  public void cancelOFX151RecXferRsV1(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = RecSrvrTrans.findResponseByRecSrvrTID(paramString, paramFFSConnectionHolder);
    String str = arrayOfString[1];
    if (arrayOfString[0] == null) {
      throw new BPWException(" *** AuditAgent.cancelOFX151RecXferRsV1: Response not found!");
    }
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "RecIntraTrnRsV1", "OFX151");
    this.c1.cancelRecXferRsV1(localTypeRecIntraTrnRsV1, paramFFSConnectionHolder);
  }
  
  public void cancelOFX200RecXferRsV1(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = RecSrvrTrans.findResponseByRecSrvrTID(paramString, paramFFSConnectionHolder);
    String str = arrayOfString[1];
    if (arrayOfString[0] == null) {
      throw new BPWException(" *** AuditAgent.cancelOFX200RecXferRsV1: Response not found!");
    }
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "RecIntraTrnRsV1", "OFX200");
    this.c0.cancelRecXferRsV1(localTypeRecIntraTrnRsV1, paramFFSConnectionHolder);
  }
  
  public void saveRecPmtRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c1.saveRecPmtRsV1(paramTypeRecPmtTrnRsV1, paramString, paramFFSConnectionHolder);
  }
  
  public void saveRecPmtRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c0.saveRecPmtRsV1(paramTypeRecPmtTrnRsV1, paramString, paramFFSConnectionHolder);
  }
  
  public void updateRecPmtRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    this.c1.updateRecPmtRsV1(paramTypeRecPmtTrnRsV1, paramFFSConnectionHolder, paramString);
  }
  
  public void updateRecPmtRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    this.c0.updateRecPmtRsV1(paramTypeRecPmtTrnRsV1, paramFFSConnectionHolder, paramString);
  }
  
  public void modRecPmtRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, RecPmtInfo paramRecPmtInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c1.modRecPmtRsV1(paramTypeRecPmtTrnRsV1, paramRecPmtInfo, paramFFSConnectionHolder);
  }
  
  public void modRecPmtRsV1(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, RecPmtInfo paramRecPmtInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.c0.modRecPmtRsV1(paramTypeRecPmtTrnRsV1, paramRecPmtInfo, paramFFSConnectionHolder);
  }
  
  public void cancelOFX151RecPmtRsV1(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = RecSrvrTrans.findResponseByRecSrvrTID(paramString, paramFFSConnectionHolder);
    String str = arrayOfString[1];
    if (arrayOfString[0] == null) {
      throw new BPWException(" *** AuditAgent.cancelOFX151RecPmtRsV1: Response not found!");
    }
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "RecPmtTrnRsV1", "OFX151");
    this.c1.cancelRecPmtRsV1(localTypeRecPmtTrnRsV1, paramFFSConnectionHolder);
  }
  
  public void cancelOFX200RecPmtRsV1(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = RecSrvrTrans.findResponseByRecSrvrTID(paramString, paramFFSConnectionHolder);
    String str = arrayOfString[1];
    if (arrayOfString[0] == null) {
      throw new BPWException(" *** AuditAgent.cancelOFX200RecPmtRsV1: Response not found!");
    }
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "RecPmtTrnRsV1", "OFX200");
    this.c0.cancelRecPmtRsV1(localTypeRecPmtTrnRsV1, paramFFSConnectionHolder);
  }
  
  public String[] getSrvrTrans(String paramString1, String paramString2, String paramString3, String paramString4)
    throws BPWException
  {
    String[] arrayOfString = null;
    arrayOfString = SrvrTrans.getSrvrTrans(paramString1, paramString2, paramString3, paramString4);
    return arrayOfString;
  }
  
  public String[] getSrvrTrans(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws BPWException
  {
    String[] arrayOfString = null;
    arrayOfString = SrvrTrans.getSrvrTransWithBank(paramString1, paramString2, paramString3, paramString4, paramString5);
    return arrayOfString;
  }
  
  public String[] getPendingSrvrTrans(String paramString1, String paramString2, String paramString3, String paramString4)
    throws BPWException
  {
    String[] arrayOfString = null;
    arrayOfString = SrvrTrans.getSrvrTrans(paramString1, paramString2, paramString3, paramString4, "WILLPROCESSON");
    return arrayOfString;
  }
  
  public String[] getRecSrvrTrans(String paramString1, String paramString2, String paramString3, String paramString4)
    throws BPWException
  {
    String[] arrayOfString = null;
    arrayOfString = RecSrvrTrans.getRecSrvrTrans(paramString1, paramString2, paramString3, paramString4);
    return arrayOfString;
  }
  
  public String[] getRecSrvrTrans(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws BPWException
  {
    String[] arrayOfString = null;
    arrayOfString = RecSrvrTrans.getRecSrvrTransWithBank(paramString1, paramString2, paramString3, paramString4, paramString5);
    return arrayOfString;
  }
  
  public String[] getPendingRecSrvrTrans(String paramString1, String paramString2, String paramString3, String paramString4)
    throws BPWException
  {
    String[] arrayOfString = null;
    arrayOfString = RecSrvrTrans.getRecSrvrTrans(paramString1, paramString2, paramString3, paramString4, "WILLPROCESSON");
    return arrayOfString;
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1[] getOFX151PayeeList(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    return this.c1.getPayeeList(paramString, paramFFSConnectionHolder);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1[] getOFX200PayeeList(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    return this.c0.getPayeeList(paramString, paramFFSConnectionHolder);
  }
  
  public void updatePayAcctInPmtRsV1(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramString1, paramFFSConnectionHolder);
    if (arrayOfString[0].equals("OFX151PmtSync")) {
      this.c1.updatePayAcctInPmtRsV1(arrayOfString[1], paramString1, paramString2, paramString3, paramFFSConnectionHolder);
    } else if (arrayOfString[0].equals("OFX200PmtSync")) {
      this.c0.updatePayAcctInPmtRsV1(arrayOfString[1], paramString1, paramString2, paramString3, paramFFSConnectionHolder);
    }
  }
  
  public void updatePayAcctInRecPmtRsV1(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String[] arrayOfString = RecSrvrTrans.findResponseByRecSrvrTID(paramString1, paramFFSConnectionHolder);
    if (arrayOfString[0].equals("OFX151RecPmtSync")) {
      this.c1.updatePayAcctInRecPmtRsV1(arrayOfString[1], paramString1, paramString2, paramString3, paramFFSConnectionHolder);
    } else if (arrayOfString[0].equals("OFX200RecPmtSync")) {
      this.c0.updatePayAcctInRecPmtRsV1(arrayOfString[1], paramString1, paramString2, paramString3, paramFFSConnectionHolder);
    }
  }
  
  public void updatePayeeInPmtRsV1(String paramString1, String paramString2, String paramString3, String paramString4, PayeeInfo paramPayeeInfo, TypePayeeV1Aggregate paramTypePayeeV1Aggregate, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramString1, paramFFSConnectionHolder);
    if (arrayOfString[0].equals("OFX151PmtSync"))
    {
      this.c1.updatePayeeInPmtRsV1(arrayOfString[1], paramString1, paramString2, paramString3, paramString4, paramPayeeInfo, paramTypePayeeV1Aggregate, paramFFSConnectionHolder);
    }
    else
    {
      FFSDebug.log("*** Protocol error: Cannot mix old OFX200 message with new OFX151 message");
      throw new BPWException("*** Protocol error: Cannot mix old OFX200 message with new OFX151 message");
    }
  }
  
  public void updatePayeeInPmtRsV1(String paramString1, String paramString2, String paramString3, String paramString4, PayeeInfo paramPayeeInfo, TypePayeeAggregate paramTypePayeeAggregate, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramString1, paramFFSConnectionHolder);
    if (arrayOfString[0].equals("OFX200PmtSync"))
    {
      this.c0.updatePayeeInPmtRsV1(arrayOfString[1], paramString1, paramString2, paramString3, paramString4, paramPayeeInfo, paramTypePayeeAggregate, paramFFSConnectionHolder);
    }
    else
    {
      FFSDebug.log("*** Protocol error: Cannot mix old OFX151 message with new OFX200 message");
      throw new BPWException("*** Protocol error: Cannot mix old OFX151 message with new OFX200 message");
    }
  }
  
  public void updatePayeeInRecPmtRsV1(String paramString1, String paramString2, String paramString3, String paramString4, PayeeInfo paramPayeeInfo, TypePayeeV1Aggregate paramTypePayeeV1Aggregate, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String[] arrayOfString = RecSrvrTrans.findResponseByRecSrvrTID(paramString1, paramFFSConnectionHolder);
    if (arrayOfString[0].equals("OFX151RecPmtSync"))
    {
      this.c1.updatePayeeInRecPmtRsV1(arrayOfString[1], paramString1, paramString2, paramString3, paramString4, paramPayeeInfo, paramTypePayeeV1Aggregate, paramFFSConnectionHolder);
    }
    else
    {
      FFSDebug.log("*** Protocol error: Cannot mix old OFX200 message with new OFX151 message");
      throw new BPWException("*** Protocol error: Cannot mix old OFX200 message with new OFX151 message");
    }
  }
  
  public void updatePayeeInRecPmtRsV1(String paramString1, String paramString2, String paramString3, String paramString4, PayeeInfo paramPayeeInfo, TypePayeeAggregate paramTypePayeeAggregate, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String[] arrayOfString = RecSrvrTrans.findResponseByRecSrvrTID(paramString1, paramFFSConnectionHolder);
    if (arrayOfString[0].equals("OFX200RecPmtSync"))
    {
      this.c0.updatePayeeInRecPmtRsV1(arrayOfString[1], paramString1, paramString2, paramString3, paramString4, paramPayeeInfo, paramTypePayeeAggregate, paramFFSConnectionHolder);
    }
    else
    {
      FFSDebug.log("*** Protocol error: Cannot mix old OFX151 message with new OFX200 message");
      throw new BPWException("*** Protocol error: Cannot mix old OFX151 message with new OFX200 message");
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.audit.AuditAgent
 * JD-Core Version:    0.7.0.1
 */