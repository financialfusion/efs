package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.bpw.db.RecXferInstruction;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.master.CommonProcessor;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumFreqEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctFromUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctToUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctToAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraCanRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraCanRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraCanRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraCanRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStatusAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferPrcStsAggregate;
import com.ffusion.util.enums.UserAssignedAmount;
import java.util.HashMap;
import java.util.Hashtable;

public class TransferIntraMap
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
    localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn = new TypeIntraTrnRqUn();
    String str = paramTransferInfo.getAction();
    EnumCurrencyEnum localEnumCurrencyEnum;
    if ("Add".equals(str))
    {
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.__preValueTag = "INTRARQ";
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.__memberName = "IntraRq";
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraRq = new TypeIntraRqAggregate();
      localEnumCurrencyEnum = a(paramTransferInfo);
      if (localEnumCurrencyEnum != null)
      {
        localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraRq.CurDefExists = true;
        localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraRq.CurDef = localEnumCurrencyEnum;
      }
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraRq.XferInfo = mapTransferInfoToTypeXferInfo(paramTransferInfo);
    }
    else if ("Modify".equals(str))
    {
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.__preValueTag = "INTRAMODRQ";
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.__memberName = "IntraModRq";
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraModRq = new TypeIntraModRqAggregate();
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraModRq.SrvrTID = paramTransferInfo.getSrvrTId();
      localEnumCurrencyEnum = a(paramTransferInfo);
      if (localEnumCurrencyEnum != null)
      {
        localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraModRq.CurDefExists = true;
        localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraModRq.CurDef = localEnumCurrencyEnum;
      }
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraModRq.XferInfo = mapTransferInfoToTypeXferInfo(paramTransferInfo);
    }
    else if ("Cancel".equals(str))
    {
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.__preValueTag = "INTRACANRQ";
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.__memberName = "IntraCanRq";
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraCanRq = new TypeIntraCanRqAggregate();
      localTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraCanRq.SrvrTID = paramTransferInfo.getSrvrTId();
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
    String str = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.__memberName;
    if ("IntraRq".equals(str))
    {
      localTransferInfo.setAction("Add");
      localTransferInfo = mapTypeXferInfoToTransferInfo(localTransferInfo, paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraRq.XferInfo, paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraRq.CurDef);
    }
    else if ("IntraModRq".equals(str))
    {
      localTransferInfo.setAction("Modify");
      localTransferInfo = mapTypeXferInfoToTransferInfo(localTransferInfo, paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraModRq.XferInfo, paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraModRq.CurDef);
      if (paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraModRq.SrvrTID != null) {
        localTransferInfo.setSrvrTId(paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraModRq.SrvrTID);
      }
    }
    else if ("IntraCanRq".equals(str))
    {
      localTransferInfo.setAction("Cancel");
      if (paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraCanRq.SrvrTID != null) {
        localTransferInfo.setSrvrTId(paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraCanRq.SrvrTID);
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
  
  public static final TransferInfo mapIntraRqToTransferInfo(TypeXferInfoAggregate paramTypeXferInfoAggregate, TypeUserData paramTypeUserData, String paramString1, String paramString2, String paramString3, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException
  {
    TransferInfo localTransferInfo = new TransferInfo();
    if (paramTypeXferInfoAggregate == null) {
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
      localTransferInfo = mapTypeXferInfoToTransferInfo(localTransferInfo, paramTypeXferInfoAggregate, paramEnumCurrencyEnum);
    }
    else if ("IntraModRq".equals(paramString2))
    {
      localTransferInfo.setAction("Modify");
      localTransferInfo = mapTypeXferInfoToTransferInfo(localTransferInfo, paramTypeXferInfoAggregate, paramEnumCurrencyEnum);
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
      if (paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsCm.Status.Code == 0)
      {
        localTransferInfo.setStatusCode(paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsCm.Status.Code);
        localTransferInfo.setStatusMsg(paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsCm.Status.Message);
        localTransferInfo.setTrnId(paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsCm.TrnUID);
        localTransferInfo = mapUserDataToTransferInfo(localTransferInfo, paramTypeUserData);
        String str1 = null;
        String str2 = null;
        FFSDebug.log("TransferIntraMap.mapIntraRsToTransferInfo _memberName = " + paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.__memberName, 6);
        if (paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.__memberName.equals("IntraRs"))
        {
          localTransferInfo.setSrvrTId(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.SrvrTID);
          localTransferInfo = mapTypeXferInfoToTransferInfo(localTransferInfo, paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferInfo, paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.CurDef);
          if (paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferPrcSts != null)
          {
            str1 = String.valueOf(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferPrcSts.XferPrcCode.value());
            str2 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferPrcSts.DtXferPrc;
            localTransferInfo.setPrcStatus(str1);
            localTransferInfo.setDateToPost(str2);
          }
        }
        else if (paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.__memberName.equals("IntraModRs"))
        {
          localTransferInfo.setSrvrTId(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraModRs.SrvrTID);
          localTransferInfo = mapTypeXferInfoToTransferInfo(localTransferInfo, paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraModRs.XferInfo, paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraModRs.CurDef);
          if (paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraModRs.XferPrcSts != null)
          {
            str1 = String.valueOf(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraModRs.XferPrcSts.XferPrcCode.value());
            str2 = paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraModRs.XferPrcSts.DtXferPrc;
            localTransferInfo.setPrcStatus(str1);
            localTransferInfo.setDateToPost(str2);
          }
        }
        else if (paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.__memberName.equals("IntraCanRs"))
        {
          localTransferInfo.setSrvrTId(paramTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraCanRs.SrvrTID);
        }
        else
        {
          FFSDebug.log("TransferIntraMap.mapIntraRsToTransferInfo: OFX RESPONSE TYPE NOT DETERMINED!!! ", 0);
          throw new BPWException("RESPONSE OFX TYPE NOT DETERMINED !!!");
        }
      }
      else
      {
        localTransferInfo.setStatusCode(paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsCm.Status.Code);
        localTransferInfo.setStatusMsg(paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsCm.Status.Message);
        localTransferInfo.setTrnId(paramTypeIntraTrnRsV1.IntraTrnRs.TrnRsCm.TrnUID);
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
    localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn = new TypeRecIntraTrnRqUn();
    String str = paramRecTransferInfo.getAction();
    EnumCurrencyEnum localEnumCurrencyEnum;
    if ("Add".equals(str))
    {
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.__preValueTag = "RECINTRARQ";
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.__memberName = "RecIntraRq";
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq = new TypeRecIntraRqAggregate();
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.IntraRq = new TypeIntraRqAggregate();
      localEnumCurrencyEnum = a(paramRecTransferInfo);
      if (localEnumCurrencyEnum != null)
      {
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.IntraRq.CurDefExists = true;
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.IntraRq.CurDef = localEnumCurrencyEnum;
      }
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.IntraRq.XferInfo = mapTransferInfoToTypeXferInfo(paramRecTransferInfo);
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.RecurrInst = new TypeRecurrInstAggregate();
      try
      {
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.RecurrInst.Freq = EnumFreqEnum.from_int(CommonProcessor.mapBPWFreqToOFX200Freq(FFSUtil.getFreqInt(paramRecTransferInfo.getFrequency())));
      }
      catch (Exception localException1)
      {
        throw new BPWException("Recurring frequency not valid. Freq =" + paramRecTransferInfo.getFrequency());
      }
      if (paramRecTransferInfo.getPmtsCount() > 0)
      {
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.RecurrInst.NInsts = paramRecTransferInfo.getPmtsCount();
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.RecurrInst.NInstsExists = true;
      }
    }
    else if ("Modify".equals(str))
    {
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.__preValueTag = "RECINTRAMODRQ";
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.__memberName = "RecIntraModRq";
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq = new TypeRecIntraModRqAggregate();
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.IntraRq = new TypeIntraRqAggregate();
      if (paramRecTransferInfo.getSrvrTId() != null) {
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecSrvrTID = paramRecTransferInfo.getSrvrTId();
      }
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.ModPending = true;
      localEnumCurrencyEnum = a(paramRecTransferInfo);
      if (localEnumCurrencyEnum != null)
      {
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.IntraRq.CurDefExists = true;
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.IntraRq.CurDef = localEnumCurrencyEnum;
      }
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.IntraRq.XferInfo = mapTransferInfoToTypeXferInfo(paramRecTransferInfo);
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecurrInst = new TypeRecurrInstAggregate();
      try
      {
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecurrInst.Freq = EnumFreqEnum.from_int(CommonProcessor.mapBPWFreqToOFX200Freq(FFSUtil.getFreqInt(paramRecTransferInfo.getFrequency())));
      }
      catch (Exception localException2)
      {
        throw new BPWException("Recurring frequency not valid. Freq =" + paramRecTransferInfo.getFrequency());
      }
      if (paramRecTransferInfo.getPmtsCount() > 0)
      {
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecurrInst.NInsts = paramRecTransferInfo.getPmtsCount();
        localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecurrInst.NInstsExists = true;
      }
    }
    else if ("Cancel".equals(str))
    {
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.__preValueTag = "RECINTRACANRQ";
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.__memberName = "RecIntraCanRq";
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraCanRq = new TypeRecIntraCanRqAggregate();
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraCanRq.RecSrvrTID = paramRecTransferInfo.getSrvrTId();
      localTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraCanRq.CanPending = true;
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
      if (paramTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsCm.Status.Code == 0)
      {
        localRecTransferInfo.setStatusCode(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsCm.Status.Code);
        localRecTransferInfo.setStatusMsg(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsCm.Status.Message);
        localRecTransferInfo.setTrnId(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsCm.TrnUID);
        localRecTransferInfo = (RecTransferInfo)mapUserDataToTransferInfo(localRecTransferInfo, paramTypeUserData);
        String str;
        int i;
        int j;
        if (paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.__memberName.equals("RecIntraRs"))
        {
          localRecTransferInfo = (RecTransferInfo)mapTypeXferInfoToTransferInfo(localRecTransferInfo, paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.XferInfo, paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.CurDef);
          str = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecSrvrTID;
          i = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecurrInst.NInsts;
          j = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecurrInst.Freq.value();
          localRecTransferInfo.setSrvrTId(str);
          localRecTransferInfo.setFrequency(FFSUtil.getFreqString(CommonProcessor.mapOFX200FreqToBPWFreq(j)));
          localRecTransferInfo.setPmtsCount(i);
        }
        else if (paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.__memberName.equals("RecIntraModRs"))
        {
          localRecTransferInfo = (RecTransferInfo)mapTypeXferInfoToTransferInfo(localRecTransferInfo, paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraModRs.IntraRs.XferInfo, paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraModRs.IntraRs.CurDef);
          str = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraModRs.RecSrvrTID;
          i = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraModRs.RecurrInst.NInsts;
          j = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraModRs.RecurrInst.Freq.value();
          localRecTransferInfo.setSrvrTId(str);
          localRecTransferInfo.setFrequency(FFSUtil.getFreqString(CommonProcessor.mapOFX200FreqToBPWFreq(j)));
          localRecTransferInfo.setPmtsCount(i);
        }
        else if (paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.__memberName.equals("RecIntraCanRs"))
        {
          localRecTransferInfo.setSrvrTId(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraCanRs.RecSrvrTID);
        }
        else
        {
          FFSDebug.log("TransferIntraMap.mapRecIntraRsToRecTransferInfo: OFX RESPONSE TYPE NOT DETERMINED!!! ", 0);
          throw new BPWException("RESPONSE OFX TYPE NOT DETERMINED !!!");
        }
      }
      else
      {
        localRecTransferInfo.setStatusCode(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsCm.Status.Code);
        localRecTransferInfo.setStatusMsg(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsCm.Status.Message);
        localRecTransferInfo.setTrnId(paramTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsCm.TrnUID);
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
      String str = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.__memberName;
      int i;
      if ("RecIntraRq".equals(str))
      {
        localRecTransferInfo.setAction("Add");
        localRecTransferInfo = (RecTransferInfo)mapTypeXferInfoToTransferInfo(localRecTransferInfo, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.IntraRq.XferInfo, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.IntraRq.CurDef);
        i = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.RecurrInst.Freq.value();
        localRecTransferInfo.setFrequency(FFSUtil.getFreqString(CommonProcessor.mapOFX200FreqToBPWFreq(i)));
        localRecTransferInfo.setPmtsCount(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.RecurrInst.NInsts);
      }
      else if ("RecIntraModRq".equals(str))
      {
        localRecTransferInfo.setAction("Modify");
        localRecTransferInfo = (RecTransferInfo)mapTypeXferInfoToTransferInfo(localRecTransferInfo, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.IntraRq.XferInfo, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.IntraRq.CurDef);
        if (paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecSrvrTID != null) {
          localRecTransferInfo.setSrvrTId(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecSrvrTID);
        }
        i = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecurrInst.Freq.value();
        localRecTransferInfo.setFrequency(FFSUtil.getFreqString(CommonProcessor.mapOFX200FreqToBPWFreq(i)));
        localRecTransferInfo.setPmtsCount(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecurrInst.NInsts);
      }
      else if ("RecIntraCanRq".equals(str))
      {
        localRecTransferInfo.setAction("Cancel");
        if (paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraCanRq.RecSrvrTID != null) {
          localRecTransferInfo.setSrvrTId(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraCanRq.RecSrvrTID);
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
  
  public static TypeXferInfoAggregate mapTransferInfoToTypeXferInfo(TransferInfo paramTransferInfo)
    throws BPWException
  {
    TypeXferInfoAggregate localTypeXferInfoAggregate = new TypeXferInfoAggregate();
    if (localTypeXferInfoAggregate.BCCAcctFromUn == null) {
      localTypeXferInfoAggregate.BCCAcctFromUn = new TypeBCCAcctFromUn();
    }
    if (localTypeXferInfoAggregate.BCCAcctToUn == null) {
      localTypeXferInfoAggregate.BCCAcctToUn = new TypeBCCAcctToUn();
    }
    localTypeXferInfoAggregate.BCCAcctFromUn.__memberName = "BankAcctFrom";
    localTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom = new TypeBankAcctFromAggregate();
    ExtTransferAcctInfo localExtTransferAcctInfo1 = paramTransferInfo.getAccountFromInfo();
    if (localExtTransferAcctInfo1 != null)
    {
      if (localExtTransferAcctInfo1.getAcctBankRtn() != null) {
        localTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.BankID = localExtTransferAcctInfo1.getAcctBankRtn();
      }
      localTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.AcctID = localExtTransferAcctInfo1.getAcctNum();
      localTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.AcctType = MsgBuilder.getOFX200AcctEnum(localExtTransferAcctInfo1.getAcctType());
    }
    else
    {
      localTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.BankID = paramTransferInfo.getBankFromRtn();
      localTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.AcctID = paramTransferInfo.getAccountFromNum();
      localTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.AcctType = MsgBuilder.getOFX200AcctEnum(paramTransferInfo.getAccountFromType());
    }
    localTypeXferInfoAggregate.BCCAcctToUn.__memberName = "BankAcctTo";
    localTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo = new TypeBankAcctToAggregate();
    ExtTransferAcctInfo localExtTransferAcctInfo2 = paramTransferInfo.getAccountToInfo();
    if (localExtTransferAcctInfo2 != null)
    {
      if (localExtTransferAcctInfo2.getAcctBankRtn() != null) {
        localTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.BankID = localExtTransferAcctInfo2.getAcctBankRtn();
      }
      localTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.AcctID = localExtTransferAcctInfo2.getAcctNum();
      localTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.AcctType = MsgBuilder.getOFX200AcctEnum(localExtTransferAcctInfo2.getAcctType());
    }
    else
    {
      localTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.BankID = paramTransferInfo.getBankToRtn();
      localTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.AcctID = paramTransferInfo.getAccountToNum();
      localTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.AcctType = MsgBuilder.getOFX200AcctEnum(paramTransferInfo.getAccountToType());
    }
    localTypeXferInfoAggregate.TrnAmt = Double.parseDouble(paramTransferInfo.getAmount());
    if (paramTransferInfo.getUserAssignedAmount() == UserAssignedAmount.TO) {
      localTypeXferInfoAggregate.TrnAmt = Double.parseDouble(paramTransferInfo.getToAmount());
    }
    if ((paramTransferInfo.getDateDue() != null) && (!paramTransferInfo.getDateDue().trim().equals("")))
    {
      localTypeXferInfoAggregate.DtDue = paramTransferInfo.getDateDue();
      localTypeXferInfoAggregate.DtDueExists = true;
    }
    return localTypeXferInfoAggregate;
  }
  
  public static TransferInfo mapTypeXferInfoToTransferInfo(TransferInfo paramTransferInfo, TypeXferInfoAggregate paramTypeXferInfoAggregate, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException
  {
    if ((paramTypeXferInfoAggregate.BCCAcctFromUn == null) || (paramTypeXferInfoAggregate.BCCAcctFromUn.__memberName == null)) {
      throw new BPWException("ofxXferInfo.BCCAcctFromUn is Null !!! ");
    }
    if ((paramTypeXferInfoAggregate.BCCAcctToUn == null) || (paramTypeXferInfoAggregate.BCCAcctToUn.__memberName == null)) {
      throw new BPWException("ofxXferInfo.BCCAcctToUn is Null !!! ");
    }
    ExtTransferAcctInfo localExtTransferAcctInfo;
    if (paramTypeXferInfoAggregate.BCCAcctFromUn.__memberName.equals("BankAcctFrom"))
    {
      localExtTransferAcctInfo = new ExtTransferAcctInfo();
      localExtTransferAcctInfo.setAcctBankRtn(paramTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.BankID);
      localExtTransferAcctInfo.setAcctNum(paramTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.AcctID);
      localExtTransferAcctInfo.setAcctType(MsgBuilder.getAcctType(paramTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.AcctType));
      paramTransferInfo.setAccountFromInfo(localExtTransferAcctInfo);
    }
    else if (!paramTypeXferInfoAggregate.BCCAcctFromUn.__memberName.equals("CCAcctFrom")) {}
    if (paramTypeXferInfoAggregate.BCCAcctToUn.__memberName.equals("BankAcctTo"))
    {
      localExtTransferAcctInfo = new ExtTransferAcctInfo();
      localExtTransferAcctInfo.setAcctBankRtn(paramTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.BankID);
      localExtTransferAcctInfo.setAcctNum(paramTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.AcctID);
      localExtTransferAcctInfo.setAcctType(MsgBuilder.getAcctType(paramTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.AcctType));
      paramTransferInfo.setAccountToInfo(localExtTransferAcctInfo);
    }
    else if (!paramTypeXferInfoAggregate.BCCAcctToUn.__memberName.equals("CCAcctTo")) {}
    paramTransferInfo.setAmount(String.valueOf(paramTypeXferInfoAggregate.TrnAmt));
    paramTransferInfo.setAmountCurrency(BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum));
    if (paramTypeXferInfoAggregate.DtDue != null) {
      paramTransferInfo.setDateDue(paramTypeXferInfoAggregate.DtDue);
    }
    return paramTransferInfo;
  }
  
  public static TransferInfo mapXferInstToTransferInfo(XferInstruction paramXferInstruction, String paramString1, String paramString2, String paramString3)
  {
    TransferInfo localTransferInfo = new TransferInfo(paramXferInstruction.SrvrTID, paramXferInstruction.FIID, paramXferInstruction.CustomerID, paramXferInstruction.BankID, paramXferInstruction.BranchID, paramXferInstruction.AcctDebitID, paramXferInstruction.AcctDebitType, paramXferInstruction.AcctCreditID, paramXferInstruction.AcctCreditType, paramXferInstruction.Amount, "", paramXferInstruction.DateToPost, paramXferInstruction.RecSrvrTID, paramXferInstruction.LogID, paramXferInstruction.Status, paramString1, paramXferInstruction.SubmittedBy, paramString2);
    localTransferInfo.setAmountCurrency(paramXferInstruction.CurDef);
    localTransferInfo.setToAmount(String.valueOf(paramXferInstruction.ToAmount));
    localTransferInfo.setToAmountCurrency(paramXferInstruction.ToAmtCurrency);
    localTransferInfo.setUserAssignedAmount(paramXferInstruction.userAssignedAmount);
    localTransferInfo.setAction(paramString3);
    return localTransferInfo;
  }
  
  public static XferInstruction mapTransferInfoToXferInst(TransferInfo paramTransferInfo)
  {
    XferInstruction localXferInstruction = new XferInstruction();
    localXferInstruction.SrvrTID = paramTransferInfo.getSrvrTId();
    localXferInstruction.CustomerID = paramTransferInfo.getCustomerId();
    localXferInstruction.FIID = paramTransferInfo.getFIId();
    localXferInstruction.Amount = paramTransferInfo.getAmount();
    localXferInstruction.CurDef = paramTransferInfo.getAmountCurrency();
    localXferInstruction.ToAmount = paramTransferInfo.getToAmount();
    localXferInstruction.ToAmtCurrency = paramTransferInfo.getToAmountCurrency();
    localXferInstruction.userAssignedAmount = paramTransferInfo.getUserAssignedAmount();
    localXferInstruction.BankID = paramTransferInfo.getBankToRtn();
    localXferInstruction.BranchID = paramTransferInfo.getBranchID();
    localXferInstruction.AcctDebitID = paramTransferInfo.getAccountFromNum();
    localXferInstruction.AcctDebitType = paramTransferInfo.getAccountFromType();
    localXferInstruction.AcctCreditID = paramTransferInfo.getAccountToNum();
    localXferInstruction.AcctCreditType = paramTransferInfo.getAccountToType();
    localXferInstruction.DateCreate = paramTransferInfo.getDateCreate();
    localXferInstruction.DateToPost = String.valueOf(BPWUtil.getDateDBFormat(paramTransferInfo.getDateToPost()));
    localXferInstruction.Status = paramTransferInfo.getPrcStatus();
    localXferInstruction.LogID = paramTransferInfo.getLogId();
    localXferInstruction.RecSrvrTID = paramTransferInfo.getSourceRecSrvrTId();
    localXferInstruction.SubmittedBy = paramTransferInfo.getSubmittedBy();
    localXferInstruction.LastModified = paramTransferInfo.getLastChangeDate();
    localXferInstruction.fromBankID = paramTransferInfo.getBankFromRtn();
    return localXferInstruction;
  }
  
  public static RecTransferInfo mapRecXferInstToRecTransferInfo(RecXferInstruction paramRecXferInstruction)
  {
    RecTransferInfo localRecTransferInfo = new RecTransferInfo();
    localRecTransferInfo.setSrvrTId(paramRecXferInstruction.RecSrvrTID);
    localRecTransferInfo.setSourceRecSrvrTId(paramRecXferInstruction.RecSrvrTID);
    localRecTransferInfo.setCustomerId(paramRecXferInstruction.CustomerID);
    localRecTransferInfo.setFIId(paramRecXferInstruction.FIID);
    localRecTransferInfo.setAmount(paramRecXferInstruction.Amount);
    localRecTransferInfo.setAmountCurrency(paramRecXferInstruction.CurDef);
    localRecTransferInfo.setToAmount(paramRecXferInstruction.ToAmount);
    localRecTransferInfo.setToAmountCurrency(paramRecXferInstruction.ToAmtCurrency);
    localRecTransferInfo.setUserAssignedAmount(paramRecXferInstruction.userAssignedAmount);
    localRecTransferInfo.setBankToRtn(paramRecXferInstruction.BankID);
    localRecTransferInfo.setBranchID(paramRecXferInstruction.BranchID);
    localRecTransferInfo.setAccountFromNum(paramRecXferInstruction.AcctDebitID);
    localRecTransferInfo.setAccountFromType(paramRecXferInstruction.AcctDebitType);
    localRecTransferInfo.setAccountToNum(paramRecXferInstruction.AcctCreditID);
    localRecTransferInfo.setAccountToType(paramRecXferInstruction.AcctCreditType);
    localRecTransferInfo.setFrequency(FFSUtil.getFreqString(paramRecXferInstruction.Frequency));
    localRecTransferInfo.setStartDate(BPWUtil.getDateBeanFormat(paramRecXferInstruction.StartDate));
    localRecTransferInfo.setEndDate(BPWUtil.getDateBeanFormat(paramRecXferInstruction.EndDate));
    localRecTransferInfo.setPmtsCount(paramRecXferInstruction.InstanceCount);
    localRecTransferInfo.setDateCreate(paramRecXferInstruction.DateCreate);
    localRecTransferInfo.setPrcStatus(paramRecXferInstruction.Status);
    localRecTransferInfo.setLogId(paramRecXferInstruction.LogID);
    localRecTransferInfo.setSubmittedBy(paramRecXferInstruction.SubmittedBy);
    localRecTransferInfo.setBankFromRtn(paramRecXferInstruction.fromBankID);
    localRecTransferInfo.setTransferDest("ITOI");
    if (paramRecXferInstruction.extraFields != null) {
      localRecTransferInfo.setExtInfo(new Hashtable((HashMap)paramRecXferInstruction.extraFields));
    }
    return localRecTransferInfo;
  }
  
  public static RecXferInstruction mapRecTransferInfoToRecXferInst(RecTransferInfo paramRecTransferInfo)
  {
    RecXferInstruction localRecXferInstruction = new RecXferInstruction();
    localRecXferInstruction.RecSrvrTID = paramRecTransferInfo.getSourceRecSrvrTId();
    localRecXferInstruction.CustomerID = paramRecTransferInfo.getCustomerId();
    localRecXferInstruction.FIID = paramRecTransferInfo.getFIId();
    localRecXferInstruction.Amount = paramRecTransferInfo.getAmount();
    localRecXferInstruction.CurDef = paramRecTransferInfo.getAmountCurrency();
    localRecXferInstruction.ToAmount = paramRecTransferInfo.getToAmount();
    localRecXferInstruction.ToAmtCurrency = paramRecTransferInfo.getToAmountCurrency();
    localRecXferInstruction.userAssignedAmount = paramRecTransferInfo.getUserAssignedAmount();
    localRecXferInstruction.BankID = paramRecTransferInfo.getBankToRtn();
    localRecXferInstruction.BranchID = paramRecTransferInfo.getBranchID();
    localRecXferInstruction.AcctDebitID = paramRecTransferInfo.getAccountFromNum();
    localRecXferInstruction.AcctDebitType = paramRecTransferInfo.getAccountFromType();
    localRecXferInstruction.AcctCreditID = paramRecTransferInfo.getAccountToNum();
    localRecXferInstruction.AcctCreditType = paramRecTransferInfo.getAccountToType();
    localRecXferInstruction.Frequency = FFSUtil.getFreqInt(paramRecTransferInfo.getFrequency());
    localRecXferInstruction.StartDate = BPWUtil.getDateDBFormat(paramRecTransferInfo.getStartDate());
    localRecXferInstruction.EndDate = BPWUtil.getDateDBFormat(paramRecTransferInfo.getEndDate());
    localRecXferInstruction.InstanceCount = paramRecTransferInfo.getPmtsCount();
    localRecXferInstruction.DateCreate = paramRecTransferInfo.getDateCreate();
    localRecXferInstruction.Status = paramRecTransferInfo.getPrcStatus();
    localRecXferInstruction.LogID = paramRecTransferInfo.getLogId();
    localRecXferInstruction.SubmittedBy = paramRecTransferInfo.getSubmittedBy();
    localRecXferInstruction.fromBankID = paramRecTransferInfo.getBankFromRtn();
    return localRecXferInstruction;
  }
  
  public static IntraTrnInfo mapXferInstToIntraTrnInfo(XferInstruction paramXferInstruction)
  {
    IntraTrnInfo localIntraTrnInfo = new IntraTrnInfo();
    localIntraTrnInfo.customerId = paramXferInstruction.CustomerID;
    localIntraTrnInfo.fromBankId = paramXferInstruction.fromBankID;
    localIntraTrnInfo.bankId = paramXferInstruction.BankID;
    localIntraTrnInfo.fromBranchId = paramXferInstruction.fromBranchID;
    localIntraTrnInfo.branchId = paramXferInstruction.BranchID;
    localIntraTrnInfo.acctIdFrom = paramXferInstruction.AcctDebitID;
    localIntraTrnInfo.acctTypeFrom = paramXferInstruction.AcctDebitType;
    localIntraTrnInfo.acctIdTo = paramXferInstruction.AcctCreditID;
    localIntraTrnInfo.acctTypeTo = paramXferInstruction.AcctCreditType;
    localIntraTrnInfo.amount = String.valueOf(paramXferInstruction.Amount);
    localIntraTrnInfo.curDef = paramXferInstruction.CurDef;
    localIntraTrnInfo.toAmount = String.valueOf(paramXferInstruction.ToAmount);
    localIntraTrnInfo.toAmtCurrency = paramXferInstruction.ToAmtCurrency;
    localIntraTrnInfo.userAssignedAmount = paramXferInstruction.userAssignedAmount;
    localIntraTrnInfo.dateToPost = paramXferInstruction.DateToPost;
    localIntraTrnInfo.srvrTid = paramXferInstruction.SrvrTID;
    localIntraTrnInfo.logId = paramXferInstruction.LogID;
    localIntraTrnInfo.status = paramXferInstruction.Status;
    localIntraTrnInfo.recSrvrTid = paramXferInstruction.RecSrvrTID;
    localIntraTrnInfo.lastModified = paramXferInstruction.LastModified;
    localIntraTrnInfo.submittedBy = paramXferInstruction.SubmittedBy;
    localIntraTrnInfo.fiId = paramXferInstruction.FIID;
    localIntraTrnInfo.extraFields = paramXferInstruction.extraFields;
    return localIntraTrnInfo;
  }
  
  public static IntraTrnInfo mapTranInfoToIntraTrnInfo(TransferInfo paramTransferInfo)
  {
    IntraTrnInfo localIntraTrnInfo = new IntraTrnInfo();
    localIntraTrnInfo.customerId = paramTransferInfo.getCustomerId();
    localIntraTrnInfo.fromBankId = paramTransferInfo.getBankFromRtn();
    localIntraTrnInfo.bankId = paramTransferInfo.getBankToRtn();
    localIntraTrnInfo.branchId = paramTransferInfo.getBranchID();
    localIntraTrnInfo.acctIdFrom = paramTransferInfo.getAccountFromNum();
    localIntraTrnInfo.acctTypeFrom = paramTransferInfo.getAccountFromType();
    localIntraTrnInfo.acctIdTo = paramTransferInfo.getAccountToNum();
    localIntraTrnInfo.acctTypeTo = paramTransferInfo.getAccountToType();
    localIntraTrnInfo.amount = paramTransferInfo.getAmount();
    localIntraTrnInfo.curDef = paramTransferInfo.getAmountCurrency();
    localIntraTrnInfo.toAmount = paramTransferInfo.getToAmount();
    localIntraTrnInfo.toAmtCurrency = paramTransferInfo.getToAmountCurrency();
    localIntraTrnInfo.userAssignedAmount = paramTransferInfo.getUserAssignedAmount();
    localIntraTrnInfo.dateToPost = paramTransferInfo.getDateToPost();
    localIntraTrnInfo.srvrTid = paramTransferInfo.getSrvrTId();
    localIntraTrnInfo.logId = paramTransferInfo.getLogId();
    localIntraTrnInfo.status = paramTransferInfo.getPrcStatus();
    localIntraTrnInfo.recSrvrTid = paramTransferInfo.getSourceRecSrvrTId();
    localIntraTrnInfo.lastModified = paramTransferInfo.getLastChangeDate();
    localIntraTrnInfo.submittedBy = paramTransferInfo.getSubmittedBy();
    localIntraTrnInfo.fiId = paramTransferInfo.getFIId();
    localIntraTrnInfo.extraFields = paramTransferInfo.getExtInfo();
    return localIntraTrnInfo;
  }
  
  private static EnumCurrencyEnum a(TransferInfo paramTransferInfo)
    throws BPWException
  {
    EnumCurrencyEnum localEnumCurrencyEnum = null;
    if (paramTransferInfo.getUserAssignedAmount() == UserAssignedAmount.TO)
    {
      if ((paramTransferInfo.t6 != null) && (paramTransferInfo.t6.trim().length() > 0)) {
        localEnumCurrencyEnum = MsgBuilder.getOFX200CurrencyEnum(BPWUtil.validateCurrencyString(paramTransferInfo.t6));
      }
    }
    else if ((paramTransferInfo.t4 != null) && (paramTransferInfo.t4.trim().length() > 0)) {
      localEnumCurrencyEnum = MsgBuilder.getOFX200CurrencyEnum(BPWUtil.validateCurrencyString(paramTransferInfo.t4));
    }
    return localEnumCurrencyEnum;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.TransferIntraMap
 * JD-Core Version:    0.7.0.1
 */