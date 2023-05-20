package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.BPWXferExtraInfo;
import com.ffusion.ffs.bpw.db.RecSrvrTIDToSrvrTID;
import com.ffusion.ffs.bpw.db.RecSrvrTrans;
import com.ffusion.ffs.bpw.db.RecXferInstruction;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.TransferCalloutHandlerBase;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.TransferIntraMap;
import com.ffusion.ffs.bpw.master.LimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.master.RecXferProcessor2;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferInfoAggregate;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.enums.UserAssignedAmount;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

public class RecXferHandler
  implements DBConsts, FFSConst, BPWResource
{
  private PropertyConfig hN = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
  private int hM = 1;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("=== RecXferHandler.eventHander: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length, 6);
    try
    {
      Object localObject1;
      if (paramInt != 0)
      {
        if (paramInt == 1)
        {
          AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
          if (localAuditAgent == null) {
            throw new BPWException("RecXferHandler.eventHandler:AuditAgent is null!!");
          }
          localObject1 = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
          if (localObject1 == null) {
            throw new FFSException("RecXferHandler.eventHandler:BPWMsgBroker is null!!");
          }
          TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
          for (int i = 0; i < paramEventInfoArray._array.length; i++)
          {
            String str1 = paramEventInfoArray._array[i].InstructionID;
            FFSDebug.log("=== RecXferHandler.eventHander: eventSeq=" + paramInt + ",RecSrvrTID=" + str1, 6);
            String[] arrayOfString = RecSrvrTrans.findResponseByRecSrvrTID(str1, paramFFSConnectionHolder);
            Object localObject2;
            if (arrayOfString[0] == null)
            {
              localObject2 = "*** RecXferHandler.eventHandler failed: could not find the RecSrvrTID=" + str1 + " in BPW_RecSrvrTrans";
              FFSDebug.console((String)localObject2);
              FFSDebug.log((String)localObject2);
              ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECINTRATRN", str1);
            }
            else
            {
              localObject2 = ScheduleInfo.getScheduleInfo(paramEventInfoArray._array[i].FIId, "RECINTRATRN", str1, paramFFSConnectionHolder);
              Object localObject3;
              Object localObject4;
              if ((localObject2 != null) && (((ScheduleInfo)localObject2).StatusOption != 1))
              {
                String str2;
                ScheduleInfo localScheduleInfo;
                String str3;
                XferInstruction localXferInstruction;
                String str4;
                Object localObject5;
                Object localObject6;
                Object localObject8;
                Object localObject10;
                Object localObject11;
                Object localObject12;
                Object localObject13;
                Object localObject14;
                Object localObject15;
                if (arrayOfString[0].startsWith("OFX151"))
                {
                  localObject3 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)((BPWMsgBroker)localObject1).parseMsg(arrayOfString[1], "RecIntraTrnRsV1", "OFX151");
                  if (((ScheduleInfo)localObject2).Status.compareTo("Close") == 0)
                  {
                    RecXferInstruction.updateStatus(paramFFSConnectionHolder, str1, "POSTEDON");
                    ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecurrInst.NInsts = 0;
                    localObject4 = a((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject3);
                    localAuditAgent.updateRecXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject4, paramFFSConnectionHolder, "POSTEDON");
                  }
                  else
                  {
                    localObject4 = RecXferInstruction.getRecXferInstruction(paramFFSConnectionHolder, str1);
                    if (localObject4 == null)
                    {
                      str2 = "*** RecXferHandler.eventHandler failed: could not find the RecSrvrTID=" + str1 + " in BPW_RecXferInstruction";
                      FFSDebug.console(str2);
                      FFSDebug.log(str2);
                      ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECINTRATRN", str1);
                    }
                    else
                    {
                      str2 = BPWTrackingIDGenerator.getNextId();
                      localScheduleInfo = new ScheduleInfo();
                      localScheduleInfo.FIId = ((RecXferInstruction)localObject4).FIID;
                      localScheduleInfo.Status = "Active";
                      localScheduleInfo.Frequency = 10;
                      localScheduleInfo.InstanceCount = 1;
                      localScheduleInfo.LogID = str2;
                      localScheduleInfo.NextInstanceDate = ((ScheduleInfo)localObject2).NextInstanceDate;
                      localScheduleInfo.StartDate = localScheduleInfo.NextInstanceDate;
                      RsCmBuilder.updateXferRsDateXferPrj(Integer.toString(localScheduleInfo.NextInstanceDate) + "0000", ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs);
                      ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecurrInst.NInsts = (((ScheduleInfo)localObject2).InstanceCount - ((ScheduleInfo)localObject2).CurInstanceNum + 1);
                      ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.XferInfo.DtDue = (Integer.toString(((ScheduleInfo)localObject2).NextInstanceDate) + "0000");
                      localAuditAgent.updateRecXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject3, paramFFSConnectionHolder, "WILLPROCESSON");
                      str3 = String.valueOf(localScheduleInfo.NextInstanceDate);
                      localXferInstruction = new XferInstruction("", ((RecXferInstruction)localObject4).CustomerID, ((RecXferInstruction)localObject4).FIID, ((RecXferInstruction)localObject4).Amount, ((RecXferInstruction)localObject4).CurDef, ((RecXferInstruction)localObject4).ToAmount, ((RecXferInstruction)localObject4).ToAmtCurrency, ((RecXferInstruction)localObject4).userAssignedAmount, ((RecXferInstruction)localObject4).BankID, ((RecXferInstruction)localObject4).BranchID, ((RecXferInstruction)localObject4).AcctDebitID, ((RecXferInstruction)localObject4).AcctDebitType, ((RecXferInstruction)localObject4).AcctCreditID, ((RecXferInstruction)localObject4).AcctCreditType, "", str3, "WILLPROCESSON", str2, str1, ((RecXferInstruction)localObject4).SubmittedBy, ((RecXferInstruction)localObject4).SubmittedBy, ((RecXferInstruction)localObject4).fromBankID, ((RecXferInstruction)localObject4).fromBranchID);
                      if (!a(localXferInstruction))
                      {
                        str4 = "RecXferHandler.eventHandler failed to process: Entitlement check failed. Customer Id= " + localXferInstruction.CustomerID;
                        FFSDebug.log(str4, 6);
                        RecXferInstruction.updateStatus(paramFFSConnectionHolder, str1, "FAILEDON");
                        ScheduleInfo.delete(paramFFSConnectionHolder, str1, "RECINTRATRN");
                        localObject5 = new SimpleDateFormat("yyyyMMddHHmmss");
                        localObject6 = ((SimpleDateFormat)localObject5).format(new Date());
                        RsCmBuilder.updateRsXferPrcSts("FAILEDON", (String)localObject6, ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.XferPrcSts);
                        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecurrInst.NInsts = -1;
                        localAuditAgent.updateRecXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject3, paramFFSConnectionHolder, "FAILEDON");
                        if (this.hM >= 4)
                        {
                          localObject8 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType);
                          localObject10 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType);
                          localObject11 = new BigDecimal(localXferInstruction.Amount);
                          localObject12 = localXferInstruction.ToAmount;
                          if (localObject12 == null) {
                            localObject12 = "0.00";
                          }
                          localObject13 = new BigDecimal((String)localObject12);
                          localObject14 = BPWLocaleUtil.getLocalizedMessage(601, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
                          a(paramFFSConnectionHolder, localXferInstruction.SubmittedBy, Integer.parseInt(localXferInstruction.CustomerID), localXferInstruction.LogID, 4600, (BigDecimal)localObject11, localXferInstruction.CurDef, (BigDecimal)localObject13, localXferInstruction.ToAmtCurrency, localXferInstruction.userAssignedAmount, str1, (String)localObject8, (String)localObject10, localXferInstruction.BankID, localXferInstruction.fromBankID, "FAILEDON", (ILocalizable)localObject14);
                        }
                      }
                      else
                      {
                        str4 = XferInstruction.create(paramFFSConnectionHolder, localXferInstruction);
                        localXferInstruction.SrvrTID = str4;
                        if ((((RecXferInstruction)localObject4).extraFields != null) && (!((HashMap)((RecXferInstruction)localObject4).extraFields).isEmpty())) {
                          BPWXferExtraInfo.insertHashtable(str4, new Hashtable((HashMap)((RecXferInstruction)localObject4).extraFields), paramFFSConnectionHolder);
                        }
                        if (this.hM >= 4)
                        {
                          localObject5 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType);
                          localObject6 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType);
                          localObject8 = new BigDecimal(localXferInstruction.Amount);
                          localObject10 = localXferInstruction.ToAmount;
                          if (localObject10 == null) {
                            localObject10 = "0.00";
                          }
                          localObject11 = new BigDecimal((String)localObject10);
                          localObject12 = BPWLocaleUtil.getLocalizedMessage(913, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
                          a(paramFFSConnectionHolder, localXferInstruction.SubmittedBy, Integer.parseInt(localXferInstruction.CustomerID), localXferInstruction.LogID, 4600, (BigDecimal)localObject8, localXferInstruction.CurDef, (BigDecimal)localObject11, localXferInstruction.ToAmtCurrency, localXferInstruction.userAssignedAmount, str4, (String)localObject5, (String)localObject6, localXferInstruction.BankID, localXferInstruction.fromBankID, "WILLPROCESSON", (ILocalizable)localObject12);
                        }
                        RecSrvrTIDToSrvrTID.create(paramFFSConnectionHolder, str1, str4);
                        localObject5 = new HashMap();
                        localObject6 = a(paramFFSConnectionHolder, localXferInstruction, (HashMap)localObject5, localScheduleInfo);
                        localObject8 = a(((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.XferInfo, (a)localObject6, ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.CurDef);
                        localAuditAgent.saveXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localObject8, ((RecXferInstruction)localObject4).CustomerID, paramFFSConnectionHolder, ((a)localObject6).jdField_for);
                        if ((((a)localObject6).jdField_for.equals("FAILEDON")) && (this.hM >= 4))
                        {
                          localObject10 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType);
                          localObject11 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType);
                          localObject12 = new BigDecimal(localXferInstruction.Amount);
                          localObject13 = localXferInstruction.ToAmount;
                          if (localObject13 == null) {
                            localObject13 = "0.00";
                          }
                          localObject14 = new BigDecimal((String)localObject13);
                          localObject15 = BPWLocaleUtil.getLocalizedMessage(401, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
                          a(paramFFSConnectionHolder, localXferInstruction.SubmittedBy, Integer.parseInt(localXferInstruction.CustomerID), localXferInstruction.LogID, 4600, (BigDecimal)localObject12, localXferInstruction.CurDef, (BigDecimal)localObject14, localXferInstruction.ToAmtCurrency, localXferInstruction.userAssignedAmount, str1, (String)localObject10, (String)localObject11, localXferInstruction.BankID, localXferInstruction.fromBankID, "FAILEDON", (ILocalizable)localObject15);
                        }
                      }
                    }
                  }
                }
                else if (arrayOfString[0].startsWith("OFX200"))
                {
                  localObject3 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)((BPWMsgBroker)localObject1).parseMsg(arrayOfString[1], "RecIntraTrnRsV1", "OFX200");
                  if (((ScheduleInfo)localObject2).Status.compareTo("Close") == 0)
                  {
                    RecXferInstruction.updateStatus(paramFFSConnectionHolder, str1, "POSTEDON");
                    ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecurrInst.NInsts = 0;
                    localObject4 = a((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject3);
                    localAuditAgent.updateRecXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject4, paramFFSConnectionHolder, "POSTEDON");
                  }
                  else
                  {
                    localObject4 = RecXferInstruction.getRecXferInstruction(paramFFSConnectionHolder, str1);
                    if (localObject4 == null)
                    {
                      str2 = "*** RecXferHandler.eventHandler failed: could not find the RecSrvrTID=" + str1 + " in BPW_RecXferInstruction";
                      FFSDebug.console(str2);
                      FFSDebug.log(str2);
                      ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECINTRATRN", str1);
                    }
                    else
                    {
                      str2 = BPWTrackingIDGenerator.getNextId();
                      localScheduleInfo = new ScheduleInfo();
                      localScheduleInfo.FIId = ((RecXferInstruction)localObject4).FIID;
                      localScheduleInfo.Status = "Active";
                      localScheduleInfo.Frequency = 10;
                      localScheduleInfo.InstanceCount = 1;
                      localScheduleInfo.LogID = str2;
                      localScheduleInfo.NextInstanceDate = ((ScheduleInfo)localObject2).NextInstanceDate;
                      localScheduleInfo.StartDate = localScheduleInfo.NextInstanceDate;
                      RsCmBuilder.updateXferRsDateXferPrj(Integer.toString(localScheduleInfo.NextInstanceDate) + "0000", ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs);
                      ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecurrInst.NInsts = (((ScheduleInfo)localObject2).InstanceCount - ((ScheduleInfo)localObject2).CurInstanceNum + 1);
                      ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.XferInfo.DtDue = (Integer.toString(((ScheduleInfo)localObject2).NextInstanceDate) + "0000");
                      localAuditAgent.updateRecXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject3, paramFFSConnectionHolder, "WILLPROCESSON");
                      str3 = String.valueOf(localScheduleInfo.NextInstanceDate);
                      localXferInstruction = new XferInstruction("", ((RecXferInstruction)localObject4).CustomerID, ((RecXferInstruction)localObject4).FIID, ((RecXferInstruction)localObject4).Amount, ((RecXferInstruction)localObject4).CurDef, ((RecXferInstruction)localObject4).ToAmount, ((RecXferInstruction)localObject4).ToAmtCurrency, ((RecXferInstruction)localObject4).userAssignedAmount, ((RecXferInstruction)localObject4).BankID, ((RecXferInstruction)localObject4).BranchID, ((RecXferInstruction)localObject4).AcctDebitID, ((RecXferInstruction)localObject4).AcctDebitType, ((RecXferInstruction)localObject4).AcctCreditID, ((RecXferInstruction)localObject4).AcctCreditType, "", str3, "WILLPROCESSON", str2, str1, ((RecXferInstruction)localObject4).SubmittedBy, ((RecXferInstruction)localObject4).SubmittedBy, ((RecXferInstruction)localObject4).fromBankID, ((RecXferInstruction)localObject4).fromBranchID);
                      if (!a(localXferInstruction))
                      {
                        str4 = "RecXferHandler.eventHandler failed to process: Entitlement check failed. Customer Id= " + localXferInstruction.CustomerID;
                        FFSDebug.log(str4, 6);
                        RecXferInstruction.updateStatus(paramFFSConnectionHolder, str1, "FAILEDON");
                        ScheduleInfo.delete(paramFFSConnectionHolder, str1, "RECINTRATRN");
                        localObject5 = new SimpleDateFormat("yyyyMMddHHmmss");
                        localObject6 = ((SimpleDateFormat)localObject5).format(new Date());
                        RsCmBuilder.updateRsXferPrcSts("FAILEDON", (String)localObject6, ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.XferPrcSts);
                        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecurrInst.NInsts = -1;
                        localAuditAgent.updateRecXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject3, paramFFSConnectionHolder, "FAILEDON");
                        if (this.hM >= 4)
                        {
                          localObject8 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType);
                          localObject10 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType);
                          localObject11 = new BigDecimal(localXferInstruction.Amount);
                          localObject12 = localXferInstruction.ToAmount;
                          if (localObject12 == null) {
                            localObject12 = "0.00";
                          }
                          localObject13 = new BigDecimal((String)localObject12);
                          localObject14 = BPWLocaleUtil.getLocalizedMessage(601, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
                          a(paramFFSConnectionHolder, localXferInstruction.SubmittedBy, Integer.parseInt(localXferInstruction.CustomerID), localXferInstruction.LogID, 4600, (BigDecimal)localObject11, localXferInstruction.CurDef, (BigDecimal)localObject13, localXferInstruction.ToAmtCurrency, localXferInstruction.userAssignedAmount, str1, (String)localObject8, (String)localObject10, localXferInstruction.BankID, localXferInstruction.fromBankID, "FAILEDON", (ILocalizable)localObject14);
                        }
                      }
                      else
                      {
                        str4 = XferInstruction.create(paramFFSConnectionHolder, localXferInstruction);
                        localXferInstruction.SrvrTID = str4;
                        localObject5 = TransferIntraMap.mapXferInstToTransferInfo(localXferInstruction, "Recurring", "INTERNAL", "Add");
                        try
                        {
                          localTransferCalloutHandlerBase.begin((TransferInfo)localObject5, "AddIntraTransferFromScheduleOFX200");
                        }
                        catch (Throwable localThrowable1)
                        {
                          XferInstruction.updateStatus(paramFFSConnectionHolder, str4, "FAILED");
                          try
                          {
                            localObject5 = TransferIntraMap.mapXferInstToTransferInfo(localXferInstruction, "Recurring", "INTERNAL", "Add");
                            localTransferCalloutHandlerBase.failure((TransferInfo)localObject5, "AddIntraTransferFromScheduleOFX200");
                          }
                          catch (Throwable localThrowable2) {}
                          continue;
                        }
                        try
                        {
                          if ((((RecXferInstruction)localObject4).extraFields != null) && (!((HashMap)((RecXferInstruction)localObject4).extraFields).isEmpty())) {
                            BPWXferExtraInfo.insertHashtable(str4, new Hashtable((HashMap)((RecXferInstruction)localObject4).extraFields), paramFFSConnectionHolder);
                          }
                          if (this.hM >= 4)
                          {
                            localObject7 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType);
                            localObject9 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType);
                            localObject10 = new BigDecimal(localXferInstruction.Amount);
                            localObject11 = localXferInstruction.ToAmount;
                            if (localObject11 == null) {
                              localObject11 = "0.00";
                            }
                            localObject12 = new BigDecimal((String)localObject11);
                            localObject13 = BPWLocaleUtil.getLocalizedMessage(913, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
                            a(paramFFSConnectionHolder, localXferInstruction.SubmittedBy, Integer.parseInt(localXferInstruction.CustomerID), localXferInstruction.LogID, 4600, (BigDecimal)localObject10, localXferInstruction.CurDef, (BigDecimal)localObject12, localXferInstruction.ToAmtCurrency, localXferInstruction.userAssignedAmount, str4, (String)localObject7, (String)localObject9, localXferInstruction.BankID, localXferInstruction.fromBankID, "WILLPROCESSON", (ILocalizable)localObject13);
                          }
                          RecSrvrTIDToSrvrTID.create(paramFFSConnectionHolder, str1, str4);
                          Object localObject7 = new HashMap();
                          Object localObject9 = a(paramFFSConnectionHolder, localXferInstruction, (HashMap)localObject7, localScheduleInfo);
                          localObject10 = a(((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.XferInfo, (a)localObject9, ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.CurDef);
                          localAuditAgent.saveXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localObject10, ((RecXferInstruction)localObject4).CustomerID, paramFFSConnectionHolder, ((a)localObject9).jdField_for);
                          if ((((a)localObject9).jdField_for.equals("FAILEDON")) && (this.hM >= 4))
                          {
                            localObject11 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType);
                            localObject12 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType);
                            localObject13 = new BigDecimal(localXferInstruction.Amount);
                            localObject14 = localXferInstruction.ToAmount;
                            if (localObject14 == null) {
                              localObject14 = "0.00";
                            }
                            localObject15 = new BigDecimal((String)localObject14);
                            LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(401, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
                            a(paramFFSConnectionHolder, localXferInstruction.SubmittedBy, Integer.parseInt(localXferInstruction.CustomerID), localXferInstruction.LogID, 4600, (BigDecimal)localObject13, localXferInstruction.CurDef, (BigDecimal)localObject15, localXferInstruction.ToAmtCurrency, localXferInstruction.userAssignedAmount, str1, (String)localObject11, (String)localObject12, localXferInstruction.BankID, localXferInstruction.fromBankID, "LIMIT_CHECK_FAILED", localLocalizableString);
                          }
                          try
                          {
                            localObject5 = TransferIntraMap.mapXferInstToTransferInfo(localXferInstruction, "Recurring", "INTERNAL", "Add");
                            localTransferCalloutHandlerBase.end((TransferInfo)localObject5, "AddIntraTransferFromScheduleOFX200");
                          }
                          catch (Throwable localThrowable4) {}
                        }
                        catch (Exception localException2)
                        {
                          try
                          {
                            localObject5 = TransferIntraMap.mapXferInstToTransferInfo(localXferInstruction, "Recurring", "INTERNAL", "Add");
                            localTransferCalloutHandlerBase.end((TransferInfo)localObject5, "AddIntraTransferFromScheduleOFX200");
                          }
                          catch (Throwable localThrowable3) {}
                          throw localException2;
                        }
                      }
                    }
                  }
                }
                else
                {
                  throw new FFSException("Not supported OFX version!");
                }
              }
              else if (arrayOfString[0].startsWith("OFX151"))
              {
                localObject3 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)((BPWMsgBroker)localObject1).parseMsg(arrayOfString[1], "RecIntraTrnRsV1", "OFX151");
                RecXferInstruction.updateStatus(paramFFSConnectionHolder, str1, "POSTEDON");
                ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecurrInst.NInsts = 0;
                localObject4 = a((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject3);
                localAuditAgent.updateRecXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1)localObject4, paramFFSConnectionHolder, "POSTEDON");
              }
              else if (arrayOfString[0].startsWith("OFX200"))
              {
                localObject3 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)((BPWMsgBroker)localObject1).parseMsg(arrayOfString[1], "RecIntraTrnRsV1", "OFX200");
                RecXferInstruction.updateStatus(paramFFSConnectionHolder, str1, "POSTEDON");
                ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject3).RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecurrInst.NInsts = 0;
                localObject4 = a((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject3);
                localAuditAgent.updateRecXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1)localObject4, paramFFSConnectionHolder, "POSTEDON");
              }
            }
          }
        }
        if (paramInt != 2) {}
      }
      FFSDebug.log("=== RecXferHandler.eventHander: end", 6);
    }
    catch (Exception localException1)
    {
      localObject1 = "*** RecXferHandler.eventHandler failed:";
      FFSDebug.log((String)localObject1 + localException1.toString());
      throw new FFSException(localException1.toString());
    }
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== RecXferHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType);
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder);
    FFSDebug.log("=== RecXferHandler.resubmitEventHandler: end");
  }
  
  private com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1 a(TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate, a parama, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCurrencyEnum paramEnumCurrencyEnum)
    throws Exception
  {
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Aggregate();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1UnExists = true;
    TypeIntraTrnRsV1Un localTypeIntraTrnRsV1Un = new TypeIntraTrnRsV1Un();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un = localTypeIntraTrnRsV1Un;
    localTypeIntraTrnRsV1Un.__memberName = "IntraRs";
    TypeIntraRsV1Aggregate localTypeIntraRsV1Aggregate = new TypeIntraRsV1Aggregate();
    localTypeIntraTrnRsV1Un.IntraRs = localTypeIntraRsV1Aggregate;
    if (paramEnumCurrencyEnum != null) {
      localTypeIntraRsV1Aggregate.CurDef = paramEnumCurrencyEnum;
    } else {
      localTypeIntraRsV1Aggregate.CurDef = MsgBuilder.getOFX151CurrencyEnum(BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum));
    }
    localTypeIntraRsV1Aggregate.SrvrTID = parama.jdField_case;
    localTypeIntraRsV1Aggregate.RecSrvrTIDExists = true;
    localTypeIntraRsV1Aggregate.RecSrvrTID = parama.jdField_if;
    localTypeIntraRsV1Aggregate.XferInfo = paramTypeXferInfoV1Aggregate;
    localTypeIntraRsV1Aggregate.XferPrcStsExists = true;
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferPrcStsAggregate();
    localTypeIntraRsV1Aggregate.XferPrcSts = localTypeXferPrcStsAggregate;
    String str = SmartCalendar.getPayday(parama.jdField_int, parama.a / 100, "BookTransfer") + "000000";
    RsCmBuilder.updateRsXferPrcSts(parama.jdField_for, parama.jdField_try, localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.XferPrcSts);
    RsCmBuilder.updateXferRsDateXferPrj(str, localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs);
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm localTypeTrnRqCm = new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm();
    localTypeTrnRqCm.TrnUID = "0";
    localTypeTrnRqCm.CltCookieExists = false;
    localTypeTrnRqCm.TANExists = false;
    localTypeIntraTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(localTypeTrnRqCm, parama.jdField_do);
    return new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1(localTypeIntraTrnRsV1Aggregate);
  }
  
  private com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1 a(TypeXferInfoAggregate paramTypeXferInfoAggregate, a parama, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum paramEnumCurrencyEnum)
    throws Exception
  {
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1Aggregate();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsUnExists = true;
    TypeIntraTrnRsUn localTypeIntraTrnRsUn = new TypeIntraTrnRsUn();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn = localTypeIntraTrnRsUn;
    localTypeIntraTrnRsUn.__memberName = "IntraRs";
    TypeIntraRsAggregate localTypeIntraRsAggregate = new TypeIntraRsAggregate();
    localTypeIntraTrnRsUn.IntraRs = localTypeIntraRsAggregate;
    if (paramEnumCurrencyEnum != null) {
      localTypeIntraRsAggregate.CurDef = paramEnumCurrencyEnum;
    } else {
      localTypeIntraRsAggregate.CurDef = MsgBuilder.getOFX200CurrencyEnum(BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum));
    }
    localTypeIntraRsAggregate.SrvrTID = parama.jdField_case;
    localTypeIntraRsAggregate.RecSrvrTIDExists = true;
    localTypeIntraRsAggregate.RecSrvrTID = parama.jdField_if;
    localTypeIntraRsAggregate.XferInfo = paramTypeXferInfoAggregate;
    localTypeIntraRsAggregate.XferPrcStsExists = true;
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferPrcStsAggregate();
    localTypeIntraRsAggregate.XferPrcSts = localTypeXferPrcStsAggregate;
    String str = SmartCalendar.getPayday(parama.jdField_int, parama.a / 100, "BookTransfer") + "000000";
    RsCmBuilder.updateRsXferPrcSts(parama.jdField_for, parama.jdField_try, localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.XferPrcSts);
    RsCmBuilder.updateXferRsDateXferPrj(str, localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs);
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm localTypeTrnRqCm = new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm();
    localTypeTrnRqCm.TrnUID = "0";
    localTypeTrnRqCm.CltCookieExists = false;
    localTypeTrnRqCm.TANExists = false;
    localTypeIntraTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(localTypeTrnRqCm, parama.jdField_do);
    return new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1(localTypeIntraTrnRsV1Aggregate);
  }
  
  private com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1 a(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1)
  {
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.XferPrcSts;
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str = localSimpleDateFormat.format(new Date());
    RsCmBuilder.updateRsXferPrcSts("POSTEDON", str, localTypeXferPrcStsAggregate);
    RsCmBuilder.updateXferRsDatePosted(str, paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs);
    return new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1(paramTypeRecIntraTrnRsV1.RecIntraTrnRs);
  }
  
  private com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1 a(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1 paramTypeRecIntraTrnRsV1)
  {
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.XferPrcSts;
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str = localSimpleDateFormat.format(new Date());
    RsCmBuilder.updateRsXferPrcSts("POSTEDON", str, localTypeXferPrcStsAggregate);
    RsCmBuilder.updateXferRsDatePosted(str, paramTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs);
    return new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1(paramTypeRecIntraTrnRsV1.RecIntraTrnRs);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, int paramInt1, String paramString2, int paramInt2, BigDecimal paramBigDecimal1, String paramString3, BigDecimal paramBigDecimal2, String paramString4, UserAssignedAmount paramUserAssignedAmount, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, ILocalizable paramILocalizable)
    throws FFSException
  {
    TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), paramString1, null, null, paramILocalizable, paramString2, paramInt2, paramInt1, paramBigDecimal1, paramString3, paramBigDecimal2, paramString4, paramUserAssignedAmount, paramString5, paramString10, paramString7, paramString8, paramString6, paramString9, 0);
  }
  
  private a a(FFSConnectionHolder paramFFSConnectionHolder, XferInstruction paramXferInstruction, HashMap paramHashMap, ScheduleInfo paramScheduleInfo)
    throws FFSException
  {
    FFSDebug.log("RecXferHandler.doLimitChecking", ": starts....", 6);
    String str = null;
    IntraTrnInfo localIntraTrnInfo = RecXferProcessor2.mapToIntraTrnInfo(paramXferInstruction, paramHashMap);
    a locala = new a(null);
    locala.a = paramScheduleInfo.StartDate;
    locala.jdField_int = paramScheduleInfo.FIId;
    locala.jdField_byte = localIntraTrnInfo.curDef;
    locala.jdField_case = localIntraTrnInfo.srvrTid;
    locala.jdField_if = localIntraTrnInfo.recSrvrTid;
    locala.jdField_try = (Integer.toString(locala.a) + "0000");
    int i = LimitCheckApprovalProcessor.processIntraTrnAdd(paramFFSConnectionHolder, localIntraTrnInfo, paramHashMap);
    str = LimitCheckApprovalProcessor.mapToStatus(i);
    if ("APPROVAL_PENDING".equalsIgnoreCase(str))
    {
      paramXferInstruction.Status = "APPROVAL_PENDING";
      XferInstruction.updateStatus(paramFFSConnectionHolder, localIntraTrnInfo.srvrTid, str);
      locala.jdField_do = 0;
      locala.jdField_for = "WILLPROCESSON";
    }
    else if ("WILLPROCESSON".equalsIgnoreCase(str))
    {
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "INTRATRN", localIntraTrnInfo.srvrTid, paramScheduleInfo, "BookTransfer");
      locala.jdField_do = 0;
      locala.jdField_for = "WILLPROCESSON";
    }
    else
    {
      XferInstruction.updateStatus(paramFFSConnectionHolder, localIntraTrnInfo.srvrTid, str);
      FFSDebug.log("RecXferHandler.doLimitChecking", ": srvrTid: " + localIntraTrnInfo.srvrTid + ", status: " + str, 6);
      locala.jdField_do = 2000;
      locala.jdField_for = "FAILEDON";
    }
    FFSDebug.log("RecXferHandler.doLimitChecking", ": ends....", 6);
    return locala;
  }
  
  private boolean a(XferInstruction paramXferInstruction)
    throws FFSException
  {
    FFSDebug.log("RecXferHandler.doEntitlementCheck", ": starts....", 6);
    HashMap localHashMap = new HashMap();
    IntraTrnInfo localIntraTrnInfo = RecXferProcessor2.mapToIntraTrnInfo(paramXferInstruction, localHashMap);
    return LimitCheckApprovalProcessor.checkEntitlementIntra(localIntraTrnInfo, localHashMap);
  }
  
  private class a
  {
    public int a;
    public String jdField_int;
    public String jdField_byte;
    public String jdField_case;
    public String jdField_if;
    public String jdField_try;
    public String jdField_for;
    public int jdField_new;
    public int jdField_do;
    
    private a() {}
    
    a(RecXferHandler.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.RecXferHandler
 * JD-Core Version:    0.7.0.1
 */