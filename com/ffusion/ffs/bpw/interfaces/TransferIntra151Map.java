package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.bpw.custimpl.ifx.IFXCodeMsgMap;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumFreqEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumXferStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBCCAcctFromV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBCCAcctToV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctFromV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctToV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraCanRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraCanRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraModRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraModRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraCanRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraCanRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraModRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraModRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStatusV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferPrcStsAggregate;

public class TransferIntra151Map
{
  public static final TypeIntraTrnRqV1 mapTransferInfoToIntraRq(TransferInfo paramTransferInfo, TypeUserData paramTypeUserData)
    throws BPWException
  {
    FFSDebug.log("TransferIntraMap.mapTransferInfoToIntraRq called, TransferInfo = " + paramTransferInfo, 6);
    if (paramTransferInfo == null) {
      throw new BPWException("TransferInfo is null while mapping from TransferInfo to OFX Intra object");
    }
    if (paramTypeUserData == null)
    {
      FFSDebug.log("mapTransferInfoToIntraRq: TypeUserData is Null", 0);
      throw new BPWException("TypeUserData object is null while mapping from TransferInfo to OFX Intra object");
    }
    paramTypeUserData = mapTransferInfoToUserData(paramTransferInfo, paramTypeUserData);
    FFSDebug.log("TransferIntraMap.mapTransferInfoToIntraRq: AFter Setting the userData _uid = " + paramTypeUserData._uid + " _fiid = " + paramTypeUserData._fid + " _submittedBy = " + paramTypeUserData._submittedBy, 6);
    TypeIntraTrnRqV1 localTypeIntraTrnRqV1 = new TypeIntraTrnRqV1();
    localTypeIntraTrnRqV1.IntraTrnRq = new TypeIntraTrnRqV1Aggregate();
    localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un = new TypeIntraTrnRqV1Un();
    String str = paramTransferInfo.getAction();
    if ("Add".equals(str))
    {
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.__preValueTag = "INTRARQ";
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.__memberName = "IntraRq";
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraRq = new TypeIntraRqV1Aggregate();
      if ((paramTransferInfo.t4 != null) && (paramTransferInfo.t4.trim().length() > 0))
      {
        localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraRq.CurDefExists = true;
        localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraRq.CurDef = MsgBuilder.getOFX151CurrencyEnum(BPWUtil.validateCurrencyString(paramTransferInfo.t4));
      }
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraRq.XferInfo = mapTransferInfoToTypeXferInfo(paramTransferInfo);
    }
    else if ("Modify".equals(str))
    {
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.__preValueTag = "INTRAMODRQ";
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.__memberName = "IntraModRq";
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraModRq = new TypeIntraModRqV1Aggregate();
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraModRq.SrvrTID = paramTransferInfo.getSrvrTId();
      if ((paramTransferInfo.t4 != null) && (paramTransferInfo.t4.trim().length() > 0))
      {
        localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraModRq.CurDefExists = true;
        localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraModRq.CurDef = MsgBuilder.getOFX151CurrencyEnum(BPWUtil.validateCurrencyString(paramTransferInfo.t4));
      }
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraModRq.XferInfo = mapTransferInfoToTypeXferInfo(paramTransferInfo);
    }
    else if ("Cancel".equals(str))
    {
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.__preValueTag = "INTRACANRQ";
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.__memberName = "IntraCanRq";
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraCanRq = new TypeIntraCanRqV1Aggregate();
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraCanRq.SrvrTID = paramTransferInfo.getSrvrTId();
    }
    else
    {
      FFSDebug.log("TransferIntraMap.mapTransferInfoToIntraRq: Invalid TransferInfo.Action", 0);
      throw new BPWException("Invalid TransferInfo.Action provided while mapping from TransferInfo to OFX Intra object");
    }
    localTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm = new TypeTrnRqCm();
    localTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm.TrnUID = paramTransferInfo.getTrnId();
    return localTypeIntraTrnRqV1;
  }
  
  public static final TransferInfo mapIntraRqToTransferInfo(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws BPWException
  {
    TransferInfo localTransferInfo = new TransferInfo();
    if (paramTypeIntraTrnRqV1 == null) {
      throw new BPWException("TypeIntraTrnRqV1 is null while mapping from OFX Intra object to TransferInfo");
    }
    if (paramTypeUserData == null)
    {
      FFSDebug.log("TransferIntraMap.mapIntraRqToTransferInfo : TypeUserData is Null", 0);
      throw new BPWException("TypeUserData object is null while mapping from OFX Intra object to TransferInfo");
    }
    localTransferInfo = mapUserDataToTransferInfo(localTransferInfo, paramTypeUserData);
    String str = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.__memberName;
    if ("IntraRq".equals(str))
    {
      localTransferInfo.setAction("Add");
      localTransferInfo = mapTypeXferInfoToTransferInfo(localTransferInfo, paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraRq.XferInfo, paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraRq.CurDef);
    }
    else if ("IntraModRq".equals(str))
    {
      localTransferInfo.setAction("Modify");
      localTransferInfo = mapTypeXferInfoToTransferInfo(localTransferInfo, paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraModRq.XferInfo, paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraModRq.CurDef);
      if (paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraModRq.SrvrTID != null) {
        localTransferInfo.setSrvrTId(paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraModRq.SrvrTID);
      }
    }
    else if ("IntraCanRq".equals(str))
    {
      localTransferInfo.setAction("Cancel");
      if (paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraCanRq.SrvrTID != null) {
        localTransferInfo.setSrvrTId(paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraCanRq.SrvrTID);
      }
    }
    else
    {
      FFSDebug.log("TransferIntraMap.mapIntraRqToTransferInfo: Invalid IntraTrnRqUn.__memberName", 0);
      throw new BPWException("Invalid IntraTrnRqUn.__memberName provided while mapping from OFX Intra object to TransferInfo");
    }
    localTransferInfo.setTrnId(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm.TrnUID);
    return localTransferInfo;
  }
  
  public static final TransferInfo mapIntraRqToTransferInfo(TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate, TypeUserData paramTypeUserData, String paramString1, String paramString2, String paramString3, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException
  {
    TransferInfo localTransferInfo = new TransferInfo();
    if (paramTypeXferInfoV1Aggregate == null) {
      throw new BPWException("TypeXferInfoAggregate is null while mapping from OFX Intra object to TransferInfo");
    }
    if (paramTypeUserData == null)
    {
      FFSDebug.log("TransferIntraMap.mapIntraRqToTransferInfo : TypeUserData is Null", 0);
      throw new BPWException("TypeUserData object is null while mapping from OFX Intra object to TransferInfo");
    }
    localTransferInfo = mapUserDataToTransferInfo(localTransferInfo, paramTypeUserData);
    if ("IntraRq".equals(paramString2))
    {
      localTransferInfo.setAction("Add");
      localTransferInfo = mapTypeXferInfoToTransferInfo(localTransferInfo, paramTypeXferInfoV1Aggregate, paramEnumCurrencyEnum);
    }
    else if ("IntraModRq".equals(paramString2))
    {
      localTransferInfo.setAction("Modify");
      localTransferInfo = mapTypeXferInfoToTransferInfo(localTransferInfo, paramTypeXferInfoV1Aggregate, paramEnumCurrencyEnum);
    }
    else if ("IntraCanRq".equals(paramString2))
    {
      localTransferInfo.setAction("Cancel");
    }
    else
    {
      FFSDebug.log("TransferIntraMap.mapIntraRqToTransferInfo: Invalid action", 0);
      throw new BPWException("Invalid action provided while mapping from OFX Intra object to TransferInfo");
    }
    if (paramString3 != null) {
      localTransferInfo.setSrvrTId(paramString3);
    }
    localTransferInfo.setTrnId(paramString1);
    return localTransferInfo;
  }
  
  public static final TransferInfo mapIntraRsToTransferInfo(TypeIntraTrnRsV1 paramTypeIntraTrnRsV1, TypeUserData paramTypeUserData)
    throws BPWException
  {
    FFSDebug.log("TransferIntraMap.mapIntraRsToTransferInfo called ", 6);
    TransferInfo localTransferInfo = new TransferInfo();
    if (paramTypeIntraTrnRsV1 != null) {
      if (paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsV1Cm.Status.Code == 0)
      {
        localTransferInfo.setStatusCode(paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsV1Cm.Status.Code);
        localTransferInfo.setStatusMsg(paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsV1Cm.Status.Message);
        localTransferInfo.setTrnId(paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsV1Cm.TrnUID);
        localTransferInfo = mapUserDataToTransferInfo(localTransferInfo, paramTypeUserData);
        String str1 = null;
        String str2 = null;
        FFSDebug.log("TransferIntraMap.mapIntraRsToTransferInfo _memberName = " + paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.__memberName, 6);
        if (paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.__memberName.equals("IntraRs"))
        {
          localTransferInfo.setSrvrTId(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.SrvrTID);
          localTransferInfo = mapTypeXferInfoToTransferInfo(localTransferInfo, paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferInfo, paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.CurDef);
          if (paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferPrcSts != null)
          {
            str1 = String.valueOf(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferPrcSts.XferPrcCode.value());
            str2 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferPrcSts.DtXferPrc;
            localTransferInfo.setPrcStatus(str1);
            localTransferInfo.setDateToPost(str2);
          }
        }
        else if (paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.__memberName.equals("IntraModRs"))
        {
          localTransferInfo.setSrvrTId(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraModRs.SrvrTID);
          localTransferInfo = mapTypeXferInfoToTransferInfo(localTransferInfo, paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraModRs.XferInfo, paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraModRs.CurDef);
          if (paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraModRs.XferPrcSts != null)
          {
            str1 = String.valueOf(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraModRs.XferPrcSts.XferPrcCode.value());
            str2 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraModRs.XferPrcSts.DtXferPrc;
            localTransferInfo.setPrcStatus(str1);
            localTransferInfo.setDateToPost(str2);
          }
        }
        else if (paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.__memberName.equals("IntraCanRs"))
        {
          localTransferInfo.setSrvrTId(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraCanRs.SrvrTID);
        }
        else
        {
          FFSDebug.log("TransferIntraMap.mapIntraRsToTransferInfo: OFX RESPONSE TYPE NOT DETERMINED!!! ", 0);
          throw new BPWException("RESPONSE OFX TYPE NOT DETERMINED !!!");
        }
      }
      else
      {
        localTransferInfo.setStatusCode(paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsV1Cm.Status.Code);
        localTransferInfo.setStatusMsg(paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsV1Cm.Status.Message);
        localTransferInfo.setTrnId(paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsV1Cm.TrnUID);
      }
    }
    FFSDebug.log("TransferIntraMap.mapIntraRsToTransferInfo end !!!  Result TransferInfo = " + localTransferInfo, 6);
    return localTransferInfo;
  }
  
  public static final TypeRecIntraTrnRqV1 mapRecTransferInfoToRecIntraRq(RecTransferInfo paramRecTransferInfo, TypeUserData paramTypeUserData)
    throws BPWException
  {
    FFSDebug.log("TransferIntraMap.mapRecTransferInfoToRecIntraRq called, TransferInfo = " + paramRecTransferInfo, 6);
    if (paramRecTransferInfo == null) {
      throw new BPWException("TransferInfo is null while mapping from TransferInfo to OFX Intra object");
    }
    if (paramTypeUserData == null)
    {
      FFSDebug.log("TransferIntraMap.mapTransferInfoToIntraRq : TypeUserData is Null", 0);
      throw new BPWException("TypeUserData object is null while mapping from TransferInfo to OFX Intra object");
    }
    paramTypeUserData = mapTransferInfoToUserData(paramRecTransferInfo, paramTypeUserData);
    FFSDebug.log("TransferIntraMap.mapRecTransferInfoToIntraRq: AFter Setting the userData _uid = " + paramTypeUserData._uid + " _fiid = " + paramTypeUserData._fid + " _submittedBy = " + paramTypeUserData._submittedBy, 6);
    TypeRecIntraTrnRqV1 localTypeRecIntraTrnRqV1 = new TypeRecIntraTrnRqV1();
    localTypeRecIntraTrnRqV1.RecIntraTrnRq = new TypeRecIntraTrnRqV1Aggregate();
    localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un = new TypeRecIntraTrnRqV1Un();
    String str = paramRecTransferInfo.getAction();
    if ("Add".equals(str))
    {
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.__preValueTag = "RECINTRARQ";
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.__memberName = "RecIntraRq";
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq = new TypeRecIntraRqV1Aggregate();
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.IntraRq = new TypeIntraRqV1Aggregate();
      if ((paramRecTransferInfo.t4 != null) && (paramRecTransferInfo.t4.trim().length() > 0))
      {
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.IntraRq.CurDefExists = true;
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.IntraRq.CurDef = MsgBuilder.getOFX151CurrencyEnum(BPWUtil.validateCurrencyString(paramRecTransferInfo.t4));
      }
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.IntraRq.XferInfo = mapTransferInfoToTypeXferInfo(paramRecTransferInfo);
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.RecurrInst = new TypeRecurrInstAggregate();
      try
      {
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.RecurrInst.Freq = EnumFreqEnum.from_int(Integer.parseInt(IFXCodeMsgMap.getStatusCode("Freq_" + paramRecTransferInfo.getFrequency())));
      }
      catch (Exception localException1)
      {
        throw new BPWException("Recurring frequency not valid. Freq =" + paramRecTransferInfo.getFrequency());
      }
      if (paramRecTransferInfo.getPmtsCount() > 0)
      {
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.RecurrInst.NInsts = paramRecTransferInfo.getPmtsCount();
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.RecurrInst.NInstsExists = true;
      }
    }
    else if ("Modify".equals(str))
    {
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.__preValueTag = "RECINTRAMODRQ";
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.__memberName = "RecIntraModRq";
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq = new TypeRecIntraModRqV1Aggregate();
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.IntraRq = new TypeIntraRqV1Aggregate();
      if (paramRecTransferInfo.getSrvrTId() != null) {
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.RecSrvrTID = paramRecTransferInfo.getSrvrTId();
      }
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.ModPending = true;
      if ((paramRecTransferInfo.t4 != null) && (paramRecTransferInfo.t4.trim().length() > 0))
      {
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.IntraRq.CurDefExists = true;
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.IntraRq.CurDef = MsgBuilder.getOFX151CurrencyEnum(BPWUtil.validateCurrencyString(paramRecTransferInfo.t4));
      }
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.IntraRq.XferInfo = mapTransferInfoToTypeXferInfo(paramRecTransferInfo);
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.RecurrInst = new TypeRecurrInstAggregate();
      try
      {
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.RecurrInst.Freq = EnumFreqEnum.from_int(Integer.parseInt(IFXCodeMsgMap.getStatusCode("Freq_" + paramRecTransferInfo.getFrequency())));
      }
      catch (Exception localException2)
      {
        throw new BPWException("Recurring frequency not valid. Freq =" + paramRecTransferInfo.getFrequency());
      }
      if (paramRecTransferInfo.getPmtsCount() > 0)
      {
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.RecurrInst.NInsts = paramRecTransferInfo.getPmtsCount();
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.RecurrInst.NInstsExists = true;
      }
    }
    else if ("Cancel".equals(str))
    {
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.__preValueTag = "RECINTRACANRQ";
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.__memberName = "RecIntraCanRq";
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraCanRq = new TypeRecIntraCanRqV1Aggregate();
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraCanRq.RecSrvrTID = paramRecTransferInfo.getSrvrTId();
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraCanRq.CanPending = true;
    }
    else
    {
      FFSDebug.log("TransferIntraMap.mapTransferInfoToIntraRq : Invalid RecTransferInfo.Action ", 0);
      throw new BPWException("Invalid RecTransferInfo.Action provided while mapping from TransferInfo to OFX Intra object");
    }
    localTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm = new TypeTrnRqCm();
    localTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm.TrnUID = paramRecTransferInfo.getTrnId();
    return localTypeRecIntraTrnRqV1;
  }
  
  public static final RecTransferInfo mapRecIntraRsToRecTransferInfo(TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1, TypeUserData paramTypeUserData)
    throws BPWException
  {
    FFSDebug.log("TransferIntraMap.mapRecIntraRsToRecTransferInfo called ", 6);
    RecTransferInfo localRecTransferInfo = new RecTransferInfo();
    if (paramTypeRecIntraTrnRsV1 != null) {
      if (paramTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsV1Cm.Status.Code == 0)
      {
        localRecTransferInfo.setStatusCode(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsV1Cm.Status.Code);
        localRecTransferInfo.setStatusMsg(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsV1Cm.Status.Message);
        localRecTransferInfo.setTrnId(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsV1Cm.TrnUID);
        localRecTransferInfo = (RecTransferInfo)mapUserDataToTransferInfo(localRecTransferInfo, paramTypeUserData);
        String str1;
        int i;
        String str2;
        if (paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.__memberName.equals("RecIntraRs"))
        {
          localRecTransferInfo = (RecTransferInfo)mapTypeXferInfoToTransferInfo(localRecTransferInfo, paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.XferInfo, paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.CurDef);
          str1 = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecSrvrTID;
          i = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecurrInst.NInsts;
          str2 = String.valueOf(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecurrInst.Freq.value());
          localRecTransferInfo.setSrvrTId(str1);
          localRecTransferInfo.setFrequency(IFXCodeMsgMap.getStatusCode("Freq_" + str2));
          localRecTransferInfo.setPmtsCount(i);
        }
        else if (paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.__memberName.equals("RecIntraModRs"))
        {
          localRecTransferInfo = (RecTransferInfo)mapTypeXferInfoToTransferInfo(localRecTransferInfo, paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraModRs.IntraRs.XferInfo, paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraModRs.IntraRs.CurDef);
          str1 = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraModRs.RecSrvrTID;
          i = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraModRs.RecurrInst.NInsts;
          str2 = String.valueOf(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraModRs.RecurrInst.Freq.value());
          localRecTransferInfo.setSrvrTId(str1);
          localRecTransferInfo.setFrequency(IFXCodeMsgMap.getStatusCode("Freq_" + str2));
          localRecTransferInfo.setPmtsCount(i);
        }
        else if (paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.__memberName.equals("RecIntraCanRs"))
        {
          localRecTransferInfo.setSrvrTId(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraCanRs.RecSrvrTID);
        }
        else
        {
          FFSDebug.log("TransferIntraMap.mapRecIntraRsToRecTransferInfo: OFX RESPONSE TYPE NOT DETERMINED!!! ", 0);
          throw new BPWException("RESPONSE OFX TYPE NOT DETERMINED !!!");
        }
      }
      else
      {
        localRecTransferInfo.setStatusCode(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsV1Cm.Status.Code);
        localRecTransferInfo.setStatusMsg(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsV1Cm.Status.Message);
        localRecTransferInfo.setTrnId(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsV1Cm.TrnUID);
      }
    }
    FFSDebug.log("TransferIntraMap.mapRecIntraRsToTransferInfo end !!!  Result TransferInfo = " + localRecTransferInfo, 6);
    return localRecTransferInfo;
  }
  
  public static final RecTransferInfo mapRecIntraRqToRecTransferInfo(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws BPWException
  {
    RecTransferInfo localRecTransferInfo = new RecTransferInfo();
    if (paramTypeRecIntraTrnRqV1 != null)
    {
      String str1 = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.__memberName;
      String str2;
      if ("RecIntraRq".equals(str1))
      {
        localRecTransferInfo.setAction("Add");
        localRecTransferInfo = (RecTransferInfo)mapTypeXferInfoToTransferInfo(localRecTransferInfo, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.IntraRq.XferInfo, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.IntraRq.CurDef);
        str2 = String.valueOf(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.RecurrInst.Freq.value());
        localRecTransferInfo.setFrequency(IFXCodeMsgMap.getStatusCode("Freq_" + str2));
        localRecTransferInfo.setPmtsCount(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.RecurrInst.NInsts);
      }
      else if ("RecIntraModRq".equals(str1))
      {
        localRecTransferInfo.setAction("Modify");
        localRecTransferInfo = (RecTransferInfo)mapTypeXferInfoToTransferInfo(localRecTransferInfo, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.IntraRq.XferInfo, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.IntraRq.CurDef);
        if (paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.RecSrvrTID != null) {
          localRecTransferInfo.setSrvrTId(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.RecSrvrTID);
        }
        str2 = String.valueOf(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.RecurrInst.Freq.value());
        localRecTransferInfo.setFrequency(IFXCodeMsgMap.getStatusCode("Freq_" + str2));
        localRecTransferInfo.setPmtsCount(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.RecurrInst.NInsts);
      }
      else if ("RecIntraCanRq".equals(str1))
      {
        localRecTransferInfo.setAction("Cancel");
        if (paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraCanRq.RecSrvrTID != null) {
          localRecTransferInfo.setSrvrTId(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraCanRq.RecSrvrTID);
        }
      }
      else
      {
        FFSDebug.log("TransferIntraMap.mapRecIntraRqToRecTransferInfo : Invalid RecIntraTrnRqUn.__memberName ", 0);
        throw new BPWException("Invalid RecIntraTrnRqUn.__memberName provided while mapping from OFX Intra object to TransferInfo");
      }
      mapUserDataToTransferInfo(localRecTransferInfo, paramTypeUserData);
      localRecTransferInfo.setTrnId(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm.TrnUID);
    }
    return localRecTransferInfo;
  }
  
  public static TypeUserData mapTransferInfoToUserData(TransferInfo paramTransferInfo, TypeUserData paramTypeUserData)
    throws BPWException
  {
    paramTypeUserData._fid = paramTransferInfo.getFIId();
    paramTypeUserData._uid = paramTransferInfo.getCustomerId();
    paramTypeUserData._tran_id = paramTransferInfo.getTrnId();
    paramTypeUserData._submittedBy = paramTransferInfo.getSubmittedBy();
    return paramTypeUserData;
  }
  
  public static TransferInfo mapUserDataToTransferInfo(TransferInfo paramTransferInfo, TypeUserData paramTypeUserData)
    throws BPWException
  {
    paramTransferInfo.setFIId(paramTypeUserData._fid);
    paramTransferInfo.setCustomerId(paramTypeUserData._uid);
    paramTransferInfo.setTrnId(paramTypeUserData._tran_id);
    paramTransferInfo.setSubmittedBy(paramTypeUserData._submittedBy);
    return paramTransferInfo;
  }
  
  public static TypeXferInfoV1Aggregate mapTransferInfoToTypeXferInfo(TransferInfo paramTransferInfo)
    throws BPWException
  {
    TypeXferInfoV1Aggregate localTypeXferInfoV1Aggregate = new TypeXferInfoV1Aggregate();
    if (localTypeXferInfoV1Aggregate.BCCAcctFromV1Un == null) {
      localTypeXferInfoV1Aggregate.BCCAcctFromV1Un = new TypeBCCAcctFromV1Un();
    }
    if (localTypeXferInfoV1Aggregate.BCCAcctToV1Un == null) {
      localTypeXferInfoV1Aggregate.BCCAcctToV1Un = new TypeBCCAcctToV1Un();
    }
    localTypeXferInfoV1Aggregate.BCCAcctFromV1Un.__memberName = "BankAcctFrom";
    localTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom = new TypeBankAcctFromV1Aggregate();
    ExtTransferAcctInfo localExtTransferAcctInfo1 = paramTransferInfo.getAccountFromInfo();
    if (localExtTransferAcctInfo1 != null)
    {
      if (localExtTransferAcctInfo1.getAcctBankRtn() != null) {
        localTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.BankID = localExtTransferAcctInfo1.getAcctBankRtn();
      }
      localTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.AcctID = localExtTransferAcctInfo1.getAcctNum();
      localTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.AcctType = MsgBuilder.getOFX151AcctEnum(localExtTransferAcctInfo1.getAcctType());
    }
    else
    {
      localTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.BankID = paramTransferInfo.getBankFromRtn();
      localTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.AcctID = paramTransferInfo.getAccountFromNum();
      localTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.AcctType = MsgBuilder.getOFX151AcctEnum(paramTransferInfo.getAccountFromType());
    }
    localTypeXferInfoV1Aggregate.BCCAcctToV1Un.__memberName = "BankAcctTo";
    localTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo = new TypeBankAcctToV1Aggregate();
    ExtTransferAcctInfo localExtTransferAcctInfo2 = paramTransferInfo.getAccountToInfo();
    if (localExtTransferAcctInfo2 != null)
    {
      if (localExtTransferAcctInfo2.getAcctBankRtn() != null) {
        localTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.BankID = localExtTransferAcctInfo2.getAcctBankRtn();
      }
      localTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.AcctID = localExtTransferAcctInfo2.getAcctNum();
      localTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.AcctType = MsgBuilder.getOFX151AcctEnum(localExtTransferAcctInfo2.getAcctType());
    }
    else
    {
      localTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.BankID = paramTransferInfo.getBankToRtn();
      localTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.AcctID = paramTransferInfo.getAccountToNum();
      localTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.AcctType = MsgBuilder.getOFX151AcctEnum(paramTransferInfo.getAccountToType());
    }
    localTypeXferInfoV1Aggregate.TrnAmt = Double.parseDouble(paramTransferInfo.getAmount());
    if ((paramTransferInfo.getDateDue() != null) && (!paramTransferInfo.getDateDue().trim().equals("")))
    {
      localTypeXferInfoV1Aggregate.DtDue = paramTransferInfo.getDateDue();
      localTypeXferInfoV1Aggregate.DtDueExists = true;
    }
    return localTypeXferInfoV1Aggregate;
  }
  
  public static TransferInfo mapTypeXferInfoToTransferInfo(TransferInfo paramTransferInfo, TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException
  {
    if ((paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un == null) || (paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.__memberName == null)) {
      throw new BPWException("ofxXferInfo.BCCAcctFromUn is Null !!! ");
    }
    if ((paramTypeXferInfoV1Aggregate.BCCAcctToV1Un == null) || (paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.__memberName == null)) {
      throw new BPWException("ofxXferInfo.BCCAcctToUn is Null !!! ");
    }
    ExtTransferAcctInfo localExtTransferAcctInfo;
    if (paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.__memberName.equals("BankAcctFrom"))
    {
      localExtTransferAcctInfo = new ExtTransferAcctInfo();
      localExtTransferAcctInfo.setAcctBankRtn(paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.BankID);
      localExtTransferAcctInfo.setAcctNum(paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.AcctID);
      localExtTransferAcctInfo.setAcctType(MsgBuilder.getAcctType(paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.AcctType));
      paramTransferInfo.setAccountFromInfo(localExtTransferAcctInfo);
    }
    else if (!paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.__memberName.equals("CCAcctFrom")) {}
    if (paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.__memberName.equals("BankAcctTo"))
    {
      localExtTransferAcctInfo = new ExtTransferAcctInfo();
      localExtTransferAcctInfo.setAcctBankRtn(paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.BankID);
      localExtTransferAcctInfo.setAcctNum(paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.AcctID);
      localExtTransferAcctInfo.setAcctType(MsgBuilder.getAcctType(paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.AcctType));
      paramTransferInfo.setAccountToInfo(localExtTransferAcctInfo);
    }
    else if (!paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.__memberName.equals("CCAcctTo")) {}
    paramTransferInfo.setAmount(String.valueOf(paramTypeXferInfoV1Aggregate.TrnAmt));
    paramTransferInfo.setAmountCurrency(BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum));
    if (paramTypeXferInfoV1Aggregate.DtDue != null) {
      paramTransferInfo.setDateDue(paramTypeXferInfoV1Aggregate.DtDue);
    }
    return paramTransferInfo;
  }
  
  public static final TransferInfo mapXferInstToTransferInfo(XferInstruction paramXferInstruction, String paramString1, String paramString2, String paramString3)
  {
    TransferInfo localTransferInfo = new TransferInfo(paramXferInstruction.SrvrTID, paramXferInstruction.FIID, paramXferInstruction.CustomerID, paramXferInstruction.BankID, paramXferInstruction.BranchID, paramXferInstruction.AcctDebitID, paramXferInstruction.AcctDebitType, paramXferInstruction.AcctCreditID, paramXferInstruction.AcctCreditType, paramXferInstruction.Amount, "", paramXferInstruction.DateToPost, paramXferInstruction.RecSrvrTID, paramXferInstruction.LogID, paramXferInstruction.Status, paramString1, paramXferInstruction.SubmittedBy, paramString2);
    localTransferInfo.setAmountCurrency(paramXferInstruction.CurDef);
    localTransferInfo.setAction(paramString3);
    return localTransferInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.TransferIntra151Map
 * JD-Core Version:    0.7.0.1
 */