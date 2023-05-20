package com.ffusion.ffs.bpw.audit;

import com.ffusion.ffs.bpw.db.CustPayee;
import com.ffusion.ffs.bpw.db.CustPayeeRoute;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.RecSrvrTrans;
import com.ffusion.ffs.bpw.db.RecXferInstruction;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumExtdPmtForEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumIDScopeEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPmtAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtPrcStsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStatusAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSubAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeWireTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferPrcStsAggregate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OFX200Audit
  implements DBConsts, BPWResource
{
  public void savePmtRsV1(TypePmtTrnRsV1 paramTypePmtTrnRsV1, String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str1 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.SrvrTID;
    localSrvrTrans.setSrvrTID(str1);
    localSrvrTrans.setCustomerID(paramString1);
    String str2 = MsgBuilder.getAcctType(paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctFrom.AcctType);
    String str3 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctFrom.AcctID;
    String str4 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctFrom.BankID;
    localSrvrTrans.setBankID(str4);
    localSrvrTrans.setAcctID(str3);
    localSrvrTrans.setAcctType(str2);
    localSrvrTrans.setSyncType("OFX200PmtSync");
    String str5 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc.substring(0, 8);
    localSrvrTrans.setDtProcessed(str5);
    localSrvrTrans.setSubmitdate(FFSUtil.getDateString());
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str6 = localBPWMsgBroker.buildMsg(paramTypePmtTrnRsV1, "PmtTrnRsV1", "OFX200");
    localSrvrTrans.setStatus(paramString2);
    localSrvrTrans.setResponse(str6);
    localSrvrTrans.storeToDB(paramFFSConnectionHolder);
  }
  
  public void modPmtRsV1(TypePmtTrnRsV1 paramTypePmtTrnRsV1, PmtInfo paramPmtInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str1 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtModRs.SrvrTID;
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(str1, paramFFSConnectionHolder);
    TypePmtTrnRsV1 localTypePmtTrnRsV1 = (TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "PmtTrnRsV1", "OFX200");
    if (localTypePmtTrnRsV1 == null) {
      throw new BPWException("Transaction has been canceled.");
    }
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.CurDef = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtModRs.CurDef;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtModRs.PmtInfo;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtModRs.PmtPrcSts;
    String str2 = localBPWMsgBroker.buildMsg(localTypePmtTrnRsV1, "PmtTrnRsV1", "OFX200");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str3 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtModRs.PmtPrcSts.DtPmtPrc.substring(0, 8);
    localSrvrTrans.setDtProcessed(str3);
    mapToSrvrTrans(paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtModRs.PmtInfo, localSrvrTrans);
    localSrvrTrans.setBankID(paramPmtInfo.BankID);
    localSrvrTrans.setAcctID(paramPmtInfo.AcctDebitID);
    localSrvrTrans.setAcctType(paramPmtInfo.AcctDebitType);
    localSrvrTrans.setResponse(str2);
    localSrvrTrans.setStatus("WILLPROCESSON");
    localSrvrTrans.setSrvrTID(str1);
    localSrvrTrans.updateSrvrTrans(paramFFSConnectionHolder);
  }
  
  public void cancelPmtRsV1(TypePmtTrnRsV1 paramTypePmtTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str1 = localSimpleDateFormat.format(new Date());
    String str2 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc.substring(0, 8);
    paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
    paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = EnumPmtProcessStatusEnum.CANCELEDON;
    String str3 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.SrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str4 = localBPWMsgBroker.buildMsg(paramTypePmtTrnRsV1, "PmtTrnRsV1", "OFX200");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    localSrvrTrans.setDtProcessed(str2);
    mapToSrvrTrans(paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo, localSrvrTrans);
    String str5 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.CheckNum;
    localSrvrTrans.setCheckNum(str5);
    localSrvrTrans.updateResponseBySrvrTID(str4, str3, paramFFSConnectionHolder, "CANCELEDON");
  }
  
  public void updatePmtRsV1(TypePmtTrnRsV1 paramTypePmtTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    String str1 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.SrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str2 = localBPWMsgBroker.buildMsg(paramTypePmtTrnRsV1, "PmtTrnRsV1", "OFX200");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str3 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc.substring(0, 8);
    localSrvrTrans.setDtProcessed(str3);
    mapToSrvrTrans(paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo, localSrvrTrans);
    String str4 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.CheckNum;
    localSrvrTrans.setCheckNum(str4);
    localSrvrTrans.updateResponseBySrvrTID(str2, str1, paramFFSConnectionHolder, paramString);
  }
  
  public void saveXferRsV1(TypeIntraTrnRsV1 paramTypeIntraTrnRsV1, String paramString1, FFSConnectionHolder paramFFSConnectionHolder, String paramString2)
    throws BPWException
  {
    String str1 = paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsCm.TrnUID;
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str2 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.SrvrTID;
    localSrvrTrans.setSrvrTID(str2);
    localSrvrTrans.setCustomerID(paramString1);
    String str3 = MsgBuilder.getAcctType(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferInfo.BCCAcctFromUn);
    String str4 = MsgBuilder.getAcctID(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferInfo.BCCAcctFromUn);
    String str5 = MsgBuilder.getBankID(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferInfo.BCCAcctFromUn);
    localSrvrTrans.setBankID(str5);
    localSrvrTrans.setAcctID(str4);
    localSrvrTrans.setAcctType(str3);
    localSrvrTrans.setSyncType("OFX200XferSync");
    localSrvrTrans.setSubmitdate(FFSUtil.getDateString());
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str6 = localBPWMsgBroker.buildMsg(paramTypeIntraTrnRsV1, "IntraTrnRsV1", "OFX200");
    String str7 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferPrcSts.DtXferPrc.substring(0, 8);
    localSrvrTrans.setDtProcessed(str7);
    localSrvrTrans.setStatus(paramString2);
    localSrvrTrans.setResponse(str6);
    localSrvrTrans.storeToDB(paramFFSConnectionHolder);
  }
  
  public void saveWireRsV1(TypeWireTrnRsV1 paramTypeWireTrnRsV1, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String str1 = paramTypeWireTrnRsV1.WireTrnRs.TrnRsCm.TrnUID;
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str2 = paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsUn.WireRs.SrvrTID;
    localSrvrTrans.setSrvrTID(str2);
    localSrvrTrans.setCustomerID(paramString);
    String str3 = MsgBuilder.getAcctType(paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsUn.WireRs.BankAcctFrom.AcctType);
    String str4 = paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsUn.WireRs.BankAcctFrom.AcctID;
    String str5 = paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsUn.WireRs.BankAcctFrom.BankID;
    localSrvrTrans.setBankID(str5);
    localSrvrTrans.setAcctID(str4);
    localSrvrTrans.setAcctType(str3);
    localSrvrTrans.setSyncType("OFX200WireSync");
    localSrvrTrans.setSubmitdate(FFSUtil.getDateString());
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str6 = localBPWMsgBroker.buildMsg(paramTypeWireTrnRsV1, "WireTrnRsV1", "OFX200");
    String str7 = paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsUn.WireRs.WireRsUn.DtXferPrj.substring(0, 8);
    localSrvrTrans.setDtProcessed(str7);
    localSrvrTrans.setStatus("WILLPROCESSON");
    localSrvrTrans.setResponse(str6);
    localSrvrTrans.storeToDB(paramFFSConnectionHolder);
  }
  
  public void modXferRsV1(TypeIntraTrnRsV1 paramTypeIntraTrnRsV1, XferInstruction paramXferInstruction, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String str1 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.SrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str2 = localBPWMsgBroker.buildMsg(paramTypeIntraTrnRsV1, "IntraTrnRsV1", "OFX200");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str3 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferPrcSts.DtXferPrc.substring(0, 8);
    localSrvrTrans.setDtProcessed(str3);
    localSrvrTrans.setBankID(paramXferInstruction.BankID);
    localSrvrTrans.setAcctID(paramXferInstruction.AcctDebitID);
    localSrvrTrans.setAcctType(paramXferInstruction.AcctDebitType);
    localSrvrTrans.setResponse(str2);
    localSrvrTrans.setStatus("WILLPROCESSON");
    localSrvrTrans.setSrvrTID(str1);
    localSrvrTrans.updateSrvrTrans(paramFFSConnectionHolder);
  }
  
  public void updateXferRsV1(TypeIntraTrnRsV1 paramTypeIntraTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    String str1 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.SrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str2 = localBPWMsgBroker.buildMsg(paramTypeIntraTrnRsV1, "IntraTrnRsV1", "OFX200");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str3 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferPrcSts.DtXferPrc.substring(0, 8);
    localSrvrTrans.setDtProcessed(str3);
    localSrvrTrans.updateResponseBySrvrTID(str2, str1, paramFFSConnectionHolder, paramString);
  }
  
  public void updateWireRsV1(TypeWireTrnRsV1 paramTypeWireTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    String str1 = paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsUn.WireRs.SrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str2 = localBPWMsgBroker.buildMsg(paramTypeWireTrnRsV1, "WireTrnRsV1", "OFX151");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str3 = paramString2.substring(0, 8);
    localSrvrTrans.setDtProcessed(str3);
    localSrvrTrans.updateResponseBySrvrTID(str2, str1, paramFFSConnectionHolder, paramString1);
  }
  
  public void cancelXferRsV1(TypeIntraTrnRsV1 paramTypeIntraTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str1 = localSimpleDateFormat.format(new Date());
    RsCmBuilder.updateRsXferPrcSts("CANCELEDON", str1, paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferPrcSts);
    String str2 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.SrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str3 = localBPWMsgBroker.buildMsg(paramTypeIntraTrnRsV1, "IntraTrnRsV1", "OFX200");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str4 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferPrcSts.DtXferPrc.substring(0, 8);
    localSrvrTrans.setDtProcessed(str4);
    localSrvrTrans.updateResponseBySrvrTID(str3, str2, paramFFSConnectionHolder, "CANCELEDON");
  }
  
  public void cancelWireRsV1(TypeWireTrnRsV1 paramTypeWireTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str1 = localSimpleDateFormat.format(new Date());
    String str2 = paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsUn.WireRs.SrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str3 = localBPWMsgBroker.buildMsg(paramTypeWireTrnRsV1, "WireTrnRsV1", "OFX200");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str4 = paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsUn.WireRs.WireRsUn.DtXferPrj.substring(0, 8);
    localSrvrTrans.setDtProcessed(str4);
    localSrvrTrans.updateResponseBySrvrTID(str3, str2, paramFFSConnectionHolder, "CANCELEDON");
  }
  
  public void saveRecXferRsV1(TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    RecSrvrTrans localRecSrvrTrans = new RecSrvrTrans();
    String str1 = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecSrvrTID;
    localRecSrvrTrans.setRecSrvrTID(str1);
    localRecSrvrTrans.setCustomerID(paramString);
    String str2 = MsgBuilder.getAcctType(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.XferInfo.BCCAcctFromUn);
    String str3 = MsgBuilder.getAcctID(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.XferInfo.BCCAcctFromUn);
    String str4 = MsgBuilder.getBankID(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.XferInfo.BCCAcctFromUn);
    localRecSrvrTrans.setBankID(str4);
    localRecSrvrTrans.setAcctID(str3);
    localRecSrvrTrans.setAcctType(str2);
    localRecSrvrTrans.setSyncType("OFX200RecXferSync");
    localRecSrvrTrans.setSubmitdate(FFSUtil.getDateString());
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str5 = localBPWMsgBroker.buildMsg(paramTypeRecIntraTrnRsV1, "RecIntraTrnRsV1", "OFX200");
    localRecSrvrTrans.setStatus("WILLPROCESSON");
    localRecSrvrTrans.setResponse(str5);
    localRecSrvrTrans.storeToDB(paramFFSConnectionHolder);
  }
  
  public void updateRecXferRsV1(TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    String str1 = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecSrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str2 = localBPWMsgBroker.buildMsg(paramTypeRecIntraTrnRsV1, "RecIntraTrnRsV1", "OFX200");
    RecSrvrTrans.updateResponseByRecSrvrTID(str2, str1, paramFFSConnectionHolder, paramString);
  }
  
  public void modRecXferRsV1(TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1, RecXferInstruction paramRecXferInstruction, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str1 = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraModRs.RecSrvrTID;
    String[] arrayOfString = RecSrvrTrans.findResponseByRecSrvrTID(str1, paramFFSConnectionHolder);
    TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = (TypeRecIntraTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "RecIntraTrnRsV1", "OFX200");
    if (localTypeRecIntraTrnRsV1 == null) {
      throw new BPWException("Transaction has been canceled.");
    }
    localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraModRs.IntraRs;
    localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecurrInst = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraModRs.RecurrInst;
    String str2 = localBPWMsgBroker.buildMsg(localTypeRecIntraTrnRsV1, "RecIntraTrnRsV1", "OFX200");
    RecSrvrTrans localRecSrvrTrans = new RecSrvrTrans();
    localRecSrvrTrans.setBankID(paramRecXferInstruction.BankID);
    localRecSrvrTrans.setAcctID(paramRecXferInstruction.AcctDebitID);
    localRecSrvrTrans.setAcctType(paramRecXferInstruction.AcctDebitType);
    localRecSrvrTrans.setResponse(str2);
    localRecSrvrTrans.setStatus("WILLPROCESSON");
    localRecSrvrTrans.setRecSrvrTID(str1);
    localRecSrvrTrans.updateRecSrvrTrans(paramFFSConnectionHolder);
  }
  
  public void cancelRecXferRsV1(TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str1 = localSimpleDateFormat.format(new Date());
    RsCmBuilder.updateRsXferPrcSts("CANCELEDON", str1, paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.XferPrcSts);
    String str2 = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecSrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str3 = localBPWMsgBroker.buildMsg(paramTypeRecIntraTrnRsV1, "RecIntraTrnRsV1", "OFX200");
    RecSrvrTrans.updateResponseByRecSrvrTID(str3, str2, paramFFSConnectionHolder, "CANCELEDON");
  }
  
  public void saveRecPmtRsV1(TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    RecSrvrTrans localRecSrvrTrans = new RecSrvrTrans();
    String str1 = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecSrvrTID;
    localRecSrvrTrans.setRecSrvrTID(str1);
    localRecSrvrTrans.setCustomerID(paramString);
    String str2 = MsgBuilder.getAcctType(paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.BankAcctFrom.AcctType);
    String str3 = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.BankAcctFrom.AcctID;
    String str4 = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.BankAcctFrom.BankID;
    localRecSrvrTrans.setBankID(str4);
    localRecSrvrTrans.setAcctID(str3);
    localRecSrvrTrans.setAcctType(str2);
    localRecSrvrTrans.setSyncType("OFX200RecPmtSync");
    localRecSrvrTrans.setSubmitdate(FFSUtil.getDateString());
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str5 = localBPWMsgBroker.buildMsg(paramTypeRecPmtTrnRsV1, "RecPmtTrnRsV1", "OFX200");
    localRecSrvrTrans.setStatus("WILLPROCESSON");
    localRecSrvrTrans.setResponse(str5);
    localRecSrvrTrans.storeToDB(paramFFSConnectionHolder);
  }
  
  public void updateRecPmtRsV1(TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    String str1 = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecSrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str2 = localBPWMsgBroker.buildMsg(paramTypeRecPmtTrnRsV1, "RecPmtTrnRsV1", "OFX200");
    RecSrvrTrans.updateResponseByRecSrvrTID(str2, str1, paramFFSConnectionHolder, paramString);
  }
  
  public void modRecPmtRsV1(TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, RecPmtInfo paramRecPmtInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str1 = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtModRs.RecSrvrTID;
    String[] arrayOfString = RecSrvrTrans.findResponseByRecSrvrTID(str1, paramFFSConnectionHolder);
    TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = (TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "RecPmtTrnRsV1", "OFX200");
    if (localTypeRecPmtTrnRsV1 == null) {
      throw new BPWException("Transaction has been canceled.");
    }
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.CurDef = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtModRs.CurDef;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtModRs.PmtInfo;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtModRs.RecurrInst;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.InitialAmtExists = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtModRs.InitialAmtExists;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.InitialAmt = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtModRs.InitialAmt;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.FinalAmtExists = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtModRs.FinalAmtExists;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.FinalAmt = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtModRs.FinalAmt;
    String str2 = localBPWMsgBroker.buildMsg(localTypeRecPmtTrnRsV1, "RecPmtTrnRsV1", "OFX200");
    RecSrvrTrans localRecSrvrTrans = new RecSrvrTrans();
    localRecSrvrTrans.setBankID(paramRecPmtInfo.BankID);
    localRecSrvrTrans.setAcctID(paramRecPmtInfo.AcctDebitID);
    localRecSrvrTrans.setAcctType(paramRecPmtInfo.AcctDebitType);
    localRecSrvrTrans.setResponse(str2);
    localRecSrvrTrans.setStatus("WILLPROCESSON");
    localRecSrvrTrans.setRecSrvrTID(str1);
    localRecSrvrTrans.updateRecSrvrTrans(paramFFSConnectionHolder);
  }
  
  public void cancelRecPmtRsV1(TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInsts = -1;
    paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInstsExists = true;
    String str1 = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecSrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str2 = localBPWMsgBroker.buildMsg(paramTypeRecPmtTrnRsV1, "RecPmtTrnRsV1", "OFX200");
    RecSrvrTrans.updateResponseByRecSrvrTID(str2, str1, paramFFSConnectionHolder, "CANCELEDON");
  }
  
  public TypePayeeTrnRsV1[] getPayeeList(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    CustomerPayeeInfo[] arrayOfCustomerPayeeInfo = CustPayee.getCustPayeeByUID(paramString, paramFFSConnectionHolder);
    int i = arrayOfCustomerPayeeInfo.length;
    PayeeInfo[] arrayOfPayeeInfo = new PayeeInfo[i];
    TypePayeeTrnRsV1[] arrayOfTypePayeeTrnRsV1 = new TypePayeeTrnRsV1[i];
    ArrayList localArrayList = new ArrayList();
    for (int j = 0; j < i; j++)
    {
      CustPayeeRoute localCustPayeeRoute = CustPayeeRoute.getActiveCustPayeeRoute(paramString, arrayOfCustomerPayeeInfo[j].PayeeListID, paramFFSConnectionHolder);
      if ((localCustPayeeRoute != null) && (!localCustPayeeRoute.Status.equals("PENDING")) && (!localCustPayeeRoute.Status.equals("CANC")) && (!localCustPayeeRoute.Status.equals("CANC_INPROCESS")) && (!localCustPayeeRoute.Status.equals("CLOSED")))
      {
        arrayOfPayeeInfo[j] = Payee.findPayeeByID(arrayOfCustomerPayeeInfo[j].PayeeID, paramFFSConnectionHolder);
        arrayOfTypePayeeTrnRsV1[j] = a(arrayOfPayeeInfo[j], arrayOfCustomerPayeeInfo[j]);
        localArrayList.add(arrayOfTypePayeeTrnRsV1[j]);
      }
    }
    return (TypePayeeTrnRsV1[])localArrayList.toArray(new TypePayeeTrnRsV1[0]);
  }
  
  private TypePayeeTrnRsV1 a(PayeeInfo paramPayeeInfo, CustomerPayeeInfo paramCustomerPayeeInfo)
    throws BPWException
  {
    TypePayeeTrnRsV1 localTypePayeeTrnRsV1 = new TypePayeeTrnRsV1();
    localTypePayeeTrnRsV1.PayeeTrnRs = new TypePayeeTrnRsV1Aggregate();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUnExists = true;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn = new TypePayeeTrnRsUn();
    localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsCm = new TypeTrnRsCm();
    localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsCm.TrnUID = "0";
    localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsCm.Status = new TypeStatusAggregate();
    if (paramCustomerPayeeInfo.ErrCode != 0)
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsCm.Status.Code = paramCustomerPayeeInfo.ErrCode;
      localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsCm.Status.Severity = EnumSeverityEnum.ERROR;
      if (paramCustomerPayeeInfo.ErrMsg != null)
      {
        localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsCm.Status.MessageExists = true;
        localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsCm.Status.Message = paramCustomerPayeeInfo.ErrMsg;
      }
    }
    else
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsCm.Status.Code = 0;
      localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsCm.Status.Severity = EnumSeverityEnum.INFO;
    }
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.__memberName = "PayeeRs";
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs = new TypePayeeRsAggregate();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeLstID = String.valueOf(paramCustomerPayeeInfo.PayeeListID);
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCmExists = true;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm = new TypePayeeRsCm();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee = new TypePayeeAggregate();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee.Name = paramPayeeInfo.PayeeName;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee.City = paramPayeeInfo.City;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee.State = paramPayeeInfo.State;
    if (paramPayeeInfo.Country != null)
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee.CountryExists = true;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee.Country = paramPayeeInfo.Country;
    }
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee.PostalCode = paramPayeeInfo.Zipcode;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee.Phone = paramPayeeInfo.Phone;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee.AddressCm = new TypeAddressCm();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee.AddressCm.Addr1 = paramPayeeInfo.Addr1;
    if (paramPayeeInfo.Addr2 != null)
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee.AddressCm.SubAddressCmExists = true;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee.AddressCm.SubAddressCm = new TypeSubAddressCm();
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee.AddressCm.SubAddressCm.Addr2 = paramPayeeInfo.Addr2;
      if (paramPayeeInfo.Addr3 != null)
      {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee.AddressCm.SubAddressCm.Addr3Exists = true;
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee.AddressCm.SubAddressCm.Addr3 = paramPayeeInfo.Addr3;
      }
    }
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayeeRsCm.BankAcctToExists = false;
    if (paramCustomerPayeeInfo.PayAcct != null)
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayAcct = new String[1];
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayAcct[0] = paramCustomerPayeeInfo.PayAcct;
      if (paramCustomerPayeeInfo.PayAcct != null) {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayAcctExists = true;
      } else {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayAcctExists = false;
      }
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.NameOnAcct = new String[1];
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.NameOnAcct[0] = paramCustomerPayeeInfo.NameOnAcct;
      if (paramCustomerPayeeInfo.NameOnAcct != null) {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.NameOnAcctExists = true;
      } else {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.NameOnAcctExists = false;
      }
    }
    else
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayAcct = new String[0];
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.PayAcctExists = false;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.NameOnAcct = new String[0];
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.NameOnAcctExists = false;
    }
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    if ((!localPropertyConfig.UseExtdPayeeID) || (!paramPayeeInfo.ExtdPayeeID.equals("0")))
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.ExtdPayeeExists = true;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.ExtdPayee = new TypeExtdPayeeAggregate();
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCmExists = true;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCm = new TypeExtdPayeeCm();
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCm.PayeeID = (!localPropertyConfig.UseExtdPayeeID ? paramPayeeInfo.PayeeID : paramPayeeInfo.ExtdPayeeID);
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCm.Name = paramPayeeInfo.PayeeName;
      if (paramPayeeInfo.PayeeType == 3) {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCm.IDScope = EnumIDScopeEnum.USER;
      } else {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCm.IDScope = EnumIDScopeEnum.GLOBAL;
      }
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsUn.PayeeRs.ExtdPayee.DaysToPay = paramPayeeInfo.DaysToPay;
    }
    return localTypePayeeTrnRsV1;
  }
  
  public void updatePayAcctInPmtRsV1(String paramString1, String paramString2, String paramString3, String paramString4, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    TypePmtTrnRsV1 localTypePmtTrnRsV1 = (TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(paramString1, "PmtTrnRsV1", "OFX200");
    if (localTypePmtTrnRsV1 == null) {
      throw new BPWException("Transaction has been canceled.");
    }
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.PayAcct = paramString3;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.NameOnAcct = paramString4;
    if (paramString4 != null) {
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.NameOnAcctExists = true;
    } else {
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.NameOnAcctExists = false;
    }
    String str1 = localBPWMsgBroker.buildMsg(localTypePmtTrnRsV1, "PmtTrnRsV1", "OFX200");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    if (localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc == null) {
      throw new BPWException("DtPmtPrc is null");
    }
    String str2 = localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc.substring(0, 8);
    localSrvrTrans.setDtProcessed(str2);
    localSrvrTrans.updateResponseBySrvrTID(str1, paramString2, paramFFSConnectionHolder, "WILLPROCESSON");
  }
  
  public void updatePayAcctInRecPmtRsV1(String paramString1, String paramString2, String paramString3, String paramString4, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = (TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(paramString1, "RecPmtTrnRsV1", "OFX200");
    if (localTypeRecPmtTrnRsV1 == null) {
      throw new BPWException("Transaction has been canceled.");
    }
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.PayAcct = paramString3;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.NameOnAcct = paramString4;
    if (paramString4 != null) {
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.NameOnAcctExists = true;
    } else {
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.NameOnAcctExists = false;
    }
    String str = localBPWMsgBroker.buildMsg(localTypeRecPmtTrnRsV1, "RecPmtTrnRsV1", "OFX200");
    RecSrvrTrans.updateResponseByRecSrvrTID(str, paramString2, paramFFSConnectionHolder, "WILLPROCESSON");
  }
  
  public void updatePayeeInPmtRsV1(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, PayeeInfo paramPayeeInfo, TypePayeeAggregate paramTypePayeeAggregate, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    boolean bool = localPropertyConfig.UseExtdPayeeID;
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramString2, paramFFSConnectionHolder);
    TypePmtTrnRsV1 localTypePmtTrnRsV1 = (TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(paramString1, "PmtTrnRsV1", "OFX200");
    if (localTypePmtTrnRsV1 == null) {
      throw new BPWException("Transaction has been canceled.");
    }
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PayeeLstID = paramString5;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.PayAcct = paramString3;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.NameOnAcct = paramString4;
    if (paramString4 != null) {
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.NameOnAcctExists = false;
    } else {
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.NameOnAcctExists = true;
    }
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeLstID = paramString5;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeLstIDExists = true;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeUn.PayeeID = paramPayeeInfo.PayeeID;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeUn.Payee = paramTypePayeeAggregate;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayeeExists = false;
    if ((!bool) || (!paramPayeeInfo.ExtdPayeeID.equals("0")))
    {
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayeeExists = true;
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee = new TypeExtdPayeeAggregate();
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCmExists = true;
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm = new TypeExtdPayeeCm();
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm.PayeeID = (!bool ? paramPayeeInfo.PayeeID : paramPayeeInfo.ExtdPayeeID);
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm.Name = paramPayeeInfo.PayeeName;
      if (paramPayeeInfo.PayeeType == 3) {
        localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm.IDScope = EnumIDScopeEnum.USER;
      } else {
        localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm.IDScope = EnumIDScopeEnum.GLOBAL;
      }
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee.DaysToPay = paramPayeeInfo.DaysToPay;
    }
    String str1 = localBPWMsgBroker.buildMsg(localTypePmtTrnRsV1, "PmtTrnRsV1", "OFX200");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    if (localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc == null) {
      throw new BPWException("DtPmtPrc is null");
    }
    String str2 = localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc.substring(0, 8);
    localSrvrTrans.setDtProcessed(str2);
    localSrvrTrans.updateResponseBySrvrTID(str1, paramString2, paramFFSConnectionHolder, "WILLPROCESSON");
  }
  
  public void updatePayeeInRecPmtRsV1(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, PayeeInfo paramPayeeInfo, TypePayeeAggregate paramTypePayeeAggregate, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    boolean bool = localPropertyConfig.UseExtdPayeeID;
    TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = (TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(paramString1, "RecPmtTrnRsV1", "OFX200");
    if (localTypeRecPmtTrnRsV1 == null) {
      throw new BPWException("Transaction has been canceled.");
    }
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PayeeLstID = paramString5;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.PayAcct = paramString3;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.NameOnAcct = paramString4;
    if (paramString4 != null) {
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.NameOnAcctExists = true;
    } else {
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.NameOnAcctExists = false;
    }
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.PayeeLstID = paramString5;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.PayeeLstIDExists = true;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.PayeeUn.PayeeID = paramPayeeInfo.PayeeID;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.PayeeUn.Payee = paramTypePayeeAggregate;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.ExtdPayeeExists = false;
    if ((!bool) || (!paramPayeeInfo.ExtdPayeeID.equals("0")))
    {
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.ExtdPayeeExists = true;
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.ExtdPayee = new TypeExtdPayeeAggregate();
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.ExtdPayee.ExtdPayeeCmExists = true;
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.ExtdPayee.ExtdPayeeCm = new TypeExtdPayeeCm();
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.ExtdPayee.ExtdPayeeCm.PayeeID = (!bool ? paramPayeeInfo.PayeeID : paramPayeeInfo.ExtdPayeeID);
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.ExtdPayee.ExtdPayeeCm.Name = paramPayeeInfo.PayeeName;
      if (paramPayeeInfo.PayeeType == 3) {
        localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.ExtdPayee.ExtdPayeeCm.IDScope = EnumIDScopeEnum.USER;
      } else {
        localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.ExtdPayee.ExtdPayeeCm.IDScope = EnumIDScopeEnum.GLOBAL;
      }
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.ExtdPayee.DaysToPay = paramPayeeInfo.DaysToPay;
    }
    String str = localBPWMsgBroker.buildMsg(localTypeRecPmtTrnRsV1, "RecPmtTrnRsV1", "OFX200");
    RecSrvrTrans.updateResponseByRecSrvrTID(str, paramString2, paramFFSConnectionHolder, "WILLPROCESSON");
  }
  
  public void mapToSrvrTrans(TypePmtInfoAggregate paramTypePmtInfoAggregate, SrvrTrans paramSrvrTrans)
  {
    String str1 = null;
    if ((paramTypePmtInfoAggregate.ExtdPmt != null) && (paramTypePmtInfoAggregate.ExtdPmt.length > 0) && (paramTypePmtInfoAggregate.ExtdPmt[0] != null) && (paramTypePmtInfoAggregate.ExtdPmt[0].ExtdPmtForExists))
    {
      int i = paramTypePmtInfoAggregate.ExtdPmt[0].ExtdPmtFor.value();
      switch (i)
      {
      case 0: 
        str1 = "BUSINESS";
        break;
      case 1: 
        str1 = "INDIVIDUAL";
        break;
      default: 
        str1 = "UNKNOWN";
      }
    }
    paramSrvrTrans.setTransFor(str1);
    String str2 = paramTypePmtInfoAggregate.Memo;
    paramSrvrTrans.setMemo(str2);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.audit.OFX200Audit
 * JD-Core Version:    0.7.0.1
 */