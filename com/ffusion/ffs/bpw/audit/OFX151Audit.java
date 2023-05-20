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
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumExtdPmtForEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumIDScopeEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctFromV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPayeeV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPayeeV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPmtV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtModRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtPrcStsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraModRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtModRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStatusV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSubAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeWireTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferPrcStsAggregate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OFX151Audit
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
    localSrvrTrans.setSyncType("OFX151PmtSync");
    localSrvrTrans.setSubmitdate(FFSUtil.getDateString());
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str5 = localBPWMsgBroker.buildMsg(paramTypePmtTrnRsV1, "PmtTrnRsV1", "OFX151");
    localSrvrTrans.setStatus(paramString2);
    localSrvrTrans.setResponse(str5);
    String str6 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc.substring(0, 8);
    localSrvrTrans.setDtProcessed(str6);
    mapToSrvrTrans(paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo, localSrvrTrans);
    String str7 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.CheckNum;
    localSrvrTrans.setCheckNum(str7);
    localSrvrTrans.storeToDB(paramFFSConnectionHolder);
  }
  
  public void modPmtRsV1(TypePmtTrnRsV1 paramTypePmtTrnRsV1, PmtInfo paramPmtInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str1 = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtModRs.SrvrTID;
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(str1, paramFFSConnectionHolder);
    TypePmtTrnRsV1 localTypePmtTrnRsV1 = (TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "PmtTrnRsV1", "OFX151");
    if (localTypePmtTrnRsV1 == null) {
      throw new BPWException("Transaction has been canceled.");
    }
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.CurDef = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtModRs.CurDef;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtModRs.PmtInfo;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts = paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtModRs.PmtPrcSts;
    String str2 = localBPWMsgBroker.buildMsg(localTypePmtTrnRsV1, "PmtTrnRsV1", "OFX151");
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
    String str4 = localBPWMsgBroker.buildMsg(paramTypePmtTrnRsV1, "PmtTrnRsV1", "OFX151");
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
    String str2 = localBPWMsgBroker.buildMsg(paramTypePmtTrnRsV1, "PmtTrnRsV1", "OFX151");
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
    String str1 = paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsV1Cm.TrnUID;
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str2 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.SrvrTID;
    localSrvrTrans.setSrvrTID(str2);
    localSrvrTrans.setCustomerID(paramString1);
    String str3 = MsgBuilder.getAcctType(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferInfo.BCCAcctFromV1Un);
    String str4 = MsgBuilder.getAcctID(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferInfo.BCCAcctFromV1Un);
    String str5 = MsgBuilder.getBankID(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferInfo.BCCAcctFromV1Un);
    localSrvrTrans.setBankID(str5);
    localSrvrTrans.setAcctID(str4);
    localSrvrTrans.setAcctType(str3);
    localSrvrTrans.setSyncType("OFX151XferSync");
    localSrvrTrans.setSubmitdate(FFSUtil.getDateString());
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str6 = localBPWMsgBroker.buildMsg(paramTypeIntraTrnRsV1, "IntraTrnRsV1", "OFX151");
    String str7 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferPrcSts.DtXferPrc.substring(0, 8);
    localSrvrTrans.setDtProcessed(str7);
    localSrvrTrans.setStatus(paramString2);
    localSrvrTrans.setResponse(str6);
    localSrvrTrans.storeToDB(paramFFSConnectionHolder);
  }
  
  public void saveWireRsV1(TypeWireTrnRsV1 paramTypeWireTrnRsV1, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String str1 = paramTypeWireTrnRsV1.WireTrnRs.TrnRsV1Cm.TrnUID;
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str2 = paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsV1Un.WireRs.SrvrTID;
    localSrvrTrans.setSrvrTID(str2);
    localSrvrTrans.setCustomerID(paramString);
    String str3 = MsgBuilder.getAcctType(paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsV1Un.WireRs.BankAcctFrom.AcctType);
    String str4 = paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsV1Un.WireRs.BankAcctFrom.AcctID;
    String str5 = paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsV1Un.WireRs.BankAcctFrom.BankID;
    localSrvrTrans.setBankID(str5);
    localSrvrTrans.setAcctID(str4);
    localSrvrTrans.setAcctType(str3);
    localSrvrTrans.setSyncType("OFX151WireSync");
    localSrvrTrans.setSubmitdate(FFSUtil.getDateString());
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str6 = localBPWMsgBroker.buildMsg(paramTypeWireTrnRsV1, "WireTrnRsV1", "OFX151");
    String str7 = paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsV1Un.WireRs.WireRsV1Un.DtXferPrj.substring(0, 8);
    localSrvrTrans.setDtProcessed(str7);
    localSrvrTrans.setStatus("WILLPROCESSON");
    localSrvrTrans.setResponse(str6);
    localSrvrTrans.storeToDB(paramFFSConnectionHolder);
  }
  
  public void modXferRsV1(TypeIntraTrnRsV1 paramTypeIntraTrnRsV1, XferInstruction paramXferInstruction, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String str1 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.SrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str2 = localBPWMsgBroker.buildMsg(paramTypeIntraTrnRsV1, "IntraTrnRsV1", "OFX151");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str3 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferPrcSts.DtXferPrc.substring(0, 8);
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
    String str1 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.SrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str2 = localBPWMsgBroker.buildMsg(paramTypeIntraTrnRsV1, "IntraTrnRsV1", "OFX151");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str3 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferPrcSts.DtXferPrc.substring(0, 8);
    localSrvrTrans.setDtProcessed(str3);
    localSrvrTrans.updateResponseBySrvrTID(str2, str1, paramFFSConnectionHolder, paramString);
  }
  
  public void updateWireRsV1(TypeWireTrnRsV1 paramTypeWireTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    String str1 = paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsV1Un.WireRs.SrvrTID;
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
    RsCmBuilder.updateRsXferPrcSts("CANCELEDON", str1, paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferPrcSts);
    String str2 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.SrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str3 = localBPWMsgBroker.buildMsg(paramTypeIntraTrnRsV1, "IntraTrnRsV1", "OFX151");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str4 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferPrcSts.DtXferPrc.substring(0, 8);
    localSrvrTrans.setDtProcessed(str4);
    localSrvrTrans.updateResponseBySrvrTID(str3, str2, paramFFSConnectionHolder, "CANCELEDON");
  }
  
  public void cancelWireRsV1(TypeWireTrnRsV1 paramTypeWireTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str1 = localSimpleDateFormat.format(new Date());
    String str2 = paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsV1Un.WireRs.SrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str3 = localBPWMsgBroker.buildMsg(paramTypeWireTrnRsV1, "WireTrnRsV1", "OFX151");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    String str4 = paramTypeWireTrnRsV1.WireTrnRs.WireTrnRsV1Un.WireRs.WireRsV1Un.DtXferPrj.substring(0, 8);
    localSrvrTrans.setDtProcessed(str4);
    localSrvrTrans.updateResponseBySrvrTID(str3, str2, paramFFSConnectionHolder, "CANCELEDON");
  }
  
  public void saveRecXferRsV1(TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    RecSrvrTrans localRecSrvrTrans = new RecSrvrTrans();
    String str1 = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecSrvrTID;
    localRecSrvrTrans.setRecSrvrTID(str1);
    localRecSrvrTrans.setCustomerID(paramString);
    String str2 = MsgBuilder.getAcctType(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.XferInfo.BCCAcctFromV1Un);
    String str3 = MsgBuilder.getAcctID(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.XferInfo.BCCAcctFromV1Un);
    String str4 = MsgBuilder.getBankID(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.XferInfo.BCCAcctFromV1Un);
    localRecSrvrTrans.setBankID(str4);
    localRecSrvrTrans.setAcctID(str3);
    localRecSrvrTrans.setAcctType(str2);
    localRecSrvrTrans.setSyncType("OFX151RecXferSync");
    localRecSrvrTrans.setSubmitdate(FFSUtil.getDateString());
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str5 = localBPWMsgBroker.buildMsg(paramTypeRecIntraTrnRsV1, "RecIntraTrnRsV1", "OFX151");
    localRecSrvrTrans.setStatus("WILLPROCESSON");
    localRecSrvrTrans.setResponse(str5);
    localRecSrvrTrans.storeToDB(paramFFSConnectionHolder);
  }
  
  public void updateRecXferRsV1(TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    String str1 = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecSrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str2 = localBPWMsgBroker.buildMsg(paramTypeRecIntraTrnRsV1, "RecIntraTrnRsV1", "OFX151");
    RecSrvrTrans.updateResponseByRecSrvrTID(str2, str1, paramFFSConnectionHolder, paramString);
  }
  
  public void modRecXferRsV1(TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1, RecXferInstruction paramRecXferInstruction, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str1 = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraModRs.RecSrvrTID;
    String[] arrayOfString = RecSrvrTrans.findResponseByRecSrvrTID(str1, paramFFSConnectionHolder);
    TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = (TypeRecIntraTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "RecIntraTrnRsV1", "OFX151");
    if (localTypeRecIntraTrnRsV1 == null) {
      throw new BPWException("Transaction has been canceled.");
    }
    localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraModRs.IntraRs;
    localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecurrInst = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraModRs.RecurrInst;
    String str2 = localBPWMsgBroker.buildMsg(localTypeRecIntraTrnRsV1, "RecIntraTrnRsV1", "OFX151");
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
    RsCmBuilder.updateRsXferPrcSts("CANCELEDON", str1, paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.XferPrcSts);
    String str2 = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecSrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str3 = localBPWMsgBroker.buildMsg(paramTypeRecIntraTrnRsV1, "RecIntraTrnRsV1", "OFX151");
    RecSrvrTrans.updateResponseByRecSrvrTID(str3, str2, paramFFSConnectionHolder, "CANCELEDON");
  }
  
  public void saveRecPmtRsV1(TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    RecSrvrTrans localRecSrvrTrans = new RecSrvrTrans();
    String str1 = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecSrvrTID;
    localRecSrvrTrans.setRecSrvrTID(str1);
    localRecSrvrTrans.setCustomerID(paramString);
    String str2 = MsgBuilder.getAcctType(paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.BankAcctFrom.AcctType);
    String str3 = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.BankAcctFrom.AcctID;
    String str4 = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.BankAcctFrom.BankID;
    localRecSrvrTrans.setBankID(str4);
    localRecSrvrTrans.setAcctID(str3);
    localRecSrvrTrans.setAcctType(str2);
    localRecSrvrTrans.setSyncType("OFX151RecPmtSync");
    localRecSrvrTrans.setSubmitdate(FFSUtil.getDateString());
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str5 = localBPWMsgBroker.buildMsg(paramTypeRecPmtTrnRsV1, "RecPmtTrnRsV1", "OFX151");
    localRecSrvrTrans.setStatus("WILLPROCESSON");
    localRecSrvrTrans.setResponse(str5);
    localRecSrvrTrans.storeToDB(paramFFSConnectionHolder);
  }
  
  public void updateRecPmtRsV1(TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    String str1 = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecSrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str2 = localBPWMsgBroker.buildMsg(paramTypeRecPmtTrnRsV1, "RecPmtTrnRsV1", "OFX151");
    RecSrvrTrans.updateResponseByRecSrvrTID(str2, str1, paramFFSConnectionHolder, paramString);
  }
  
  public void modRecPmtRsV1(TypeRecPmtTrnRsV1 paramTypeRecPmtTrnRsV1, RecPmtInfo paramRecPmtInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str1 = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtModRs.RecSrvrTID;
    String[] arrayOfString = RecSrvrTrans.findResponseByRecSrvrTID(str1, paramFFSConnectionHolder);
    TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = (TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "RecPmtTrnRsV1", "OFX151");
    if (localTypeRecPmtTrnRsV1 == null) {
      throw new BPWException("Transaction has been canceled.");
    }
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.CurDef = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtModRs.CurDef;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtModRs.PmtInfo;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtModRs.RecurrInst;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.InitialAmtExists = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtModRs.InitialAmtExists;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.InitialAmt = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtModRs.InitialAmt;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.FinalAmtExists = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtModRs.FinalAmtExists;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.FinalAmt = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtModRs.FinalAmt;
    String str2 = localBPWMsgBroker.buildMsg(localTypeRecPmtTrnRsV1, "RecPmtTrnRsV1", "OFX151");
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
    paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInsts = -1;
    paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInstsExists = true;
    String str1 = paramTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecSrvrTID;
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String str2 = localBPWMsgBroker.buildMsg(paramTypeRecPmtTrnRsV1, "RecPmtTrnRsV1", "OFX151");
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
        arrayOfTypePayeeTrnRsV1[j] = jdMethod_if(arrayOfPayeeInfo[j], arrayOfCustomerPayeeInfo[j]);
        localArrayList.add(arrayOfTypePayeeTrnRsV1[j]);
      }
    }
    return (TypePayeeTrnRsV1[])localArrayList.toArray(new TypePayeeTrnRsV1[0]);
  }
  
  private TypePayeeTrnRsV1 jdMethod_if(PayeeInfo paramPayeeInfo, CustomerPayeeInfo paramCustomerPayeeInfo)
    throws BPWException
  {
    TypePayeeTrnRsV1 localTypePayeeTrnRsV1 = new TypePayeeTrnRsV1();
    localTypePayeeTrnRsV1.PayeeTrnRs = new TypePayeeTrnRsV1Aggregate();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1UnExists = true;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un = new TypePayeeTrnRsV1Un();
    localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsV1Cm = new TypeTrnRsV1Cm();
    localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsV1Cm.TrnUID = "0";
    localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsV1Cm.Status = new TypeStatusV1Aggregate();
    if (paramCustomerPayeeInfo.ErrCode != 0)
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsV1Cm.Status.Code = paramCustomerPayeeInfo.ErrCode;
      localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsV1Cm.Status.Severity = EnumSeverityEnum.ERROR;
      if (paramCustomerPayeeInfo.ErrMsg != null)
      {
        localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsV1Cm.Status.MessageExists = true;
        localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsV1Cm.Status.Message = paramCustomerPayeeInfo.ErrMsg;
      }
    }
    else
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsV1Cm.Status.Code = 0;
      localTypePayeeTrnRsV1.PayeeTrnRs.TrnRsV1Cm.Status.Severity = EnumSeverityEnum.INFO;
    }
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.__memberName = "PayeeRs";
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs = new TypePayeeRsV1Aggregate();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeLstID = String.valueOf(paramCustomerPayeeInfo.PayeeListID);
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1CmExists = true;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm = new TypePayeeRsV1Cm();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee = new TypePayeeV1Aggregate();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee.Name = paramPayeeInfo.PayeeName;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee.City = paramPayeeInfo.City;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee.State = paramPayeeInfo.State;
    if (paramPayeeInfo.Country != null)
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee.CountryExists = true;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee.Country = paramPayeeInfo.Country;
    }
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee.PostalCode = paramPayeeInfo.Zipcode;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee.Phone = paramPayeeInfo.Phone;
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee.AddressCm = new TypeAddressCm();
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee.AddressCm.Addr1 = paramPayeeInfo.Addr1;
    if (paramPayeeInfo.Addr2 != null)
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee.AddressCm.SubAddressCmExists = true;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee.AddressCm.SubAddressCm = new TypeSubAddressCm();
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee.AddressCm.SubAddressCm.Addr2 = paramPayeeInfo.Addr2;
      if (paramPayeeInfo.Addr3 != null)
      {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee.AddressCm.SubAddressCm.Addr3Exists = true;
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee.AddressCm.SubAddressCm.Addr3 = paramPayeeInfo.Addr3;
      }
    }
    localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.BankAcctToExists = false;
    if (paramCustomerPayeeInfo.PayAcct != null)
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayAcct = new String[1];
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayAcct[0] = paramCustomerPayeeInfo.PayAcct;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.NameOnAcct = new String[1];
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.NameOnAcct[0] = paramCustomerPayeeInfo.NameOnAcct;
      if (paramCustomerPayeeInfo.NameOnAcct != null) {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.NameOnAcctExists = true;
      } else {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.NameOnAcctExists = false;
      }
    }
    else
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.PayAcct = new String[0];
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.NameOnAcct = new String[0];
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.NameOnAcctExists = false;
    }
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    if ((!localPropertyConfig.UseExtdPayeeID) || (!paramPayeeInfo.ExtdPayeeID.equals("0")))
    {
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayeeExists = true;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee = new TypeExtdPayeeV1Aggregate();
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1CmExists = true;
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1Cm = new TypeExtdPayeeV1Cm();
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1Cm.PayeeID = (!localPropertyConfig.UseExtdPayeeID ? paramPayeeInfo.PayeeID : paramPayeeInfo.ExtdPayeeID);
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1Cm.Name = paramPayeeInfo.PayeeName;
      if (paramPayeeInfo.PayeeType == 3) {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1Cm.IDScope = EnumIDScopeEnum.USER;
      } else {
        localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1Cm.IDScope = EnumIDScopeEnum.GLOBAL;
      }
      localTypePayeeTrnRsV1.PayeeTrnRs.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.DaysToPay = paramPayeeInfo.DaysToPay;
    }
    return localTypePayeeTrnRsV1;
  }
  
  public void updatePayAcctInPmtRsV1(String paramString1, String paramString2, String paramString3, String paramString4, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    TypePmtTrnRsV1 localTypePmtTrnRsV1 = (TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(paramString1, "PmtTrnRsV1", "OFX151");
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
    String str1 = localBPWMsgBroker.buildMsg(localTypePmtTrnRsV1, "PmtTrnRsV1", "OFX151");
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
    TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = (TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(paramString1, "RecPmtTrnRsV1", "OFX151");
    if (localTypeRecPmtTrnRsV1 == null) {
      throw new BPWException("Transaction has been canceled.");
    }
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.PayAcct = paramString3;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.NameOnAcct = paramString4;
    if (paramString4 != null) {
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.NameOnAcctExists = true;
    } else {
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.NameOnAcctExists = false;
    }
    String str = localBPWMsgBroker.buildMsg(localTypeRecPmtTrnRsV1, "RecPmtTrnRsV1", "OFX151");
    RecSrvrTrans.updateResponseByRecSrvrTID(str, paramString2, paramFFSConnectionHolder, "WILLPROCESSON");
  }
  
  public void updatePayeeInPmtRsV1(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, PayeeInfo paramPayeeInfo, TypePayeeV1Aggregate paramTypePayeeV1Aggregate, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    boolean bool = localPropertyConfig.UseExtdPayeeID;
    TypePmtTrnRsV1 localTypePmtTrnRsV1 = (TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(paramString1, "PmtTrnRsV1", "OFX151");
    if (localTypePmtTrnRsV1 == null) {
      throw new BPWException("Transaction has been canceled.");
    }
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PayeeLstID = paramString5;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.PayAcct = paramString3;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.NameOnAcct = paramString4;
    if (paramString4 != null) {
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.NameOnAcctExists = true;
    } else {
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.NameOnAcctExists = false;
    }
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeLstID = paramString5;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeLstIDExists = true;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeUn.PayeeID = paramPayeeInfo.PayeeID;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeUn.Payee = paramTypePayeeV1Aggregate;
    localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayeeExists = false;
    if ((!bool) || (!paramPayeeInfo.ExtdPayeeID.equals("0")))
    {
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayeeExists = true;
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee = new TypeExtdPayeeV1Aggregate();
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeV1CmExists = true;
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeV1Cm = new TypeExtdPayeeV1Cm();
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeV1Cm.PayeeID = (!bool ? paramPayeeInfo.PayeeID : paramPayeeInfo.ExtdPayeeID);
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeV1Cm.Name = paramPayeeInfo.PayeeName;
      if (paramPayeeInfo.PayeeType == 3) {
        localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeV1Cm.IDScope = EnumIDScopeEnum.USER;
      } else {
        localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeV1Cm.IDScope = EnumIDScopeEnum.GLOBAL;
      }
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.ExtdPayee.DaysToPay = paramPayeeInfo.DaysToPay;
    }
    String str1 = localBPWMsgBroker.buildMsg(localTypePmtTrnRsV1, "PmtTrnRsV1", "OFX151");
    SrvrTrans localSrvrTrans = new SrvrTrans();
    if (localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc == null) {
      throw new BPWException("DtPmtPrc is null");
    }
    String str2 = localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc.substring(0, 8);
    localSrvrTrans.setDtProcessed(str2);
    localSrvrTrans.updateResponseBySrvrTID(str1, paramString2, paramFFSConnectionHolder, "WILLPROCESSON");
  }
  
  public void updatePayeeInRecPmtRsV1(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, PayeeInfo paramPayeeInfo, TypePayeeV1Aggregate paramTypePayeeV1Aggregate, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    boolean bool = localPropertyConfig.UseExtdPayeeID;
    TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = (TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(paramString1, "RecPmtTrnRsV1", "OFX151");
    if (localTypeRecPmtTrnRsV1 == null) {
      throw new BPWException("Transaction has been canceled.");
    }
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PayeeLstID = paramString5;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.PayAcct = paramString3;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.NameOnAcct = paramString4;
    if (paramString4 != null) {
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.NameOnAcctExists = true;
    } else {
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.NameOnAcctExists = false;
    }
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.PayeeLstID = paramString5;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.PayeeLstIDExists = true;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.PayeeUn.PayeeID = paramPayeeInfo.PayeeID;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.PayeeUn.Payee = paramTypePayeeV1Aggregate;
    localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayeeExists = false;
    if ((!bool) || (!paramPayeeInfo.ExtdPayeeID.equals("0")))
    {
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayeeExists = true;
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee = new TypeExtdPayeeV1Aggregate();
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee.ExtdPayeeV1CmExists = true;
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee.ExtdPayeeV1Cm = new TypeExtdPayeeV1Cm();
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee.ExtdPayeeV1Cm.PayeeID = (!bool ? paramPayeeInfo.PayeeID : paramPayeeInfo.ExtdPayeeID);
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee.ExtdPayeeV1Cm.Name = paramPayeeInfo.PayeeName;
      if (paramPayeeInfo.PayeeType == 3) {
        localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee.ExtdPayeeV1Cm.IDScope = EnumIDScopeEnum.USER;
      } else {
        localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee.ExtdPayeeV1Cm.IDScope = EnumIDScopeEnum.GLOBAL;
      }
      localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee.DaysToPay = paramPayeeInfo.DaysToPay;
    }
    String str = localBPWMsgBroker.buildMsg(localTypeRecPmtTrnRsV1, "RecPmtTrnRsV1", "OFX151");
    RecSrvrTrans.updateResponseByRecSrvrTID(str, paramString2, paramFFSConnectionHolder, "WILLPROCESSON");
  }
  
  public void mapToSrvrTrans(TypePmtInfoV1Aggregate paramTypePmtInfoV1Aggregate, SrvrTrans paramSrvrTrans)
  {
    String str1 = null;
    if ((paramTypePmtInfoV1Aggregate.ExtdPmt != null) && (paramTypePmtInfoV1Aggregate.ExtdPmt.length > 0) && (paramTypePmtInfoV1Aggregate.ExtdPmt[0] != null) && (paramTypePmtInfoV1Aggregate.ExtdPmt[0].ExtdPmtForExists))
    {
      int i = paramTypePmtInfoV1Aggregate.ExtdPmt[0].ExtdPmtFor.value();
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
    String str2 = paramTypePmtInfoV1Aggregate.Memo;
    paramSrvrTrans.setMemo(str2);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.audit.OFX151Audit
 * JD-Core Version:    0.7.0.1
 */