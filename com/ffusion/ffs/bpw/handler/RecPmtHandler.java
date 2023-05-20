package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.BPWExtraInfo;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.db.RecPmtInstruction;
import com.ffusion.ffs.bpw.db.RecSrvrTIDToSrvrTID;
import com.ffusion.ffs.bpw.db.RecSrvrTrans;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.fulfill.FulfillAgent;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.ffs.bpw.master.LimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPayeeV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPayeeV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStatusV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStatusAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;
import com.ffusion.util.ILocalizable;
import java.math.BigDecimal;
import java.util.HashMap;

public class RecPmtHandler
{
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("=== RecPmtHandler.eventHander: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length, 6);
    FulfillAgent localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
    if (localFulfillAgent == null) {
      throw new FFSException("FulfillAgent is null!!");
    }
    try
    {
      String str1;
      if (paramInt != 0)
      {
        if (paramInt == 1) {
          for (int i = 0; i < paramEventInfoArray._array.length; i++)
          {
            str1 = paramEventInfoArray._array[i].InstructionID;
            FFSDebug.log("=== RecPmtHandler.eventHander: eventSeq=" + paramInt + ",RecSrvrTID=" + str1, 6);
            RecPmtInstruction localRecPmtInstruction = RecPmtInstruction.getRecPmtInstr(str1, paramFFSConnectionHolder);
            String str2 = localRecPmtInstruction.getStatus();
            String[] arrayOfString = RecSrvrTrans.findResponseByRecSrvrTID(str1, paramFFSConnectionHolder);
            Object localObject1;
            if (localRecPmtInstruction == null)
            {
              localObject1 = "*** RecPmtHandler.eventHandler failed: could not find the RecSrvrTID=" + str1 + " in BPW_RecPmtInstruction";
              FFSDebug.console((String)localObject1);
              FFSDebug.log((String)localObject1);
              ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECPMTTRN", str1);
              if (arrayOfString[0] != null) {
                RecSrvrTrans.cancelRecSrvrTrans(str1, paramFFSConnectionHolder);
              }
            }
            else
            {
              localObject1 = localRecPmtInstruction.getRecPmtInfo();
              String str3 = BPWTrackingIDGenerator.getNextId();
              BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
              if (localBPWMsgBroker == null) {
                throw new FFSException("RecXferHandler.eventHandler:BPWMsgBroker is null!!");
              }
              Object localObject2;
              if (arrayOfString[0] == null)
              {
                localObject2 = "*** RecPmtHandler.eventHandler failed: could not find the RecSrvrTID=" + str1 + " in BPW_RecSrvrTrans";
                FFSDebug.console((String)localObject2);
                FFSDebug.log((String)localObject2);
                ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECPMTTRN", str1);
                localRecPmtInstruction.removeFromDB(paramFFSConnectionHolder);
              }
              else
              {
                localObject2 = ScheduleInfo.getScheduleInfo(paramEventInfoArray._array[i].FIId, "RECPMTTRN", str1, paramFFSConnectionHolder);
                AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
                if (localAuditAgent == null) {
                  throw new BPWException("RecPmtHandler.eventHandler: AuditAgent is null!!");
                }
                int j = 825;
                Object localObject4;
                Object localObject6;
                String str4;
                Object localObject7;
                if ((localObject2 != null) && (((ScheduleInfo)localObject2).StatusOption != 1))
                {
                  localObject3 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
                  PmtInfo localPmtInfo;
                  Object localObject5;
                  Object localObject8;
                  Object localObject9;
                  if (arrayOfString[0].startsWith("OFX151"))
                  {
                    localObject4 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "RecPmtTrnRsV1", "OFX151");
                    if (((ScheduleInfo)localObject2).Status.compareTo("Close") == 0)
                    {
                      RecPmtInstruction.updateStatus(paramFFSConnectionHolder, str1, "POSTEDON");
                      str2 = "POSTEDON";
                      ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInsts = 0;
                      ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.DtDue = (Integer.toString(((ScheduleInfo)localObject2).NextInstanceDate) + "0000");
                      localAuditAgent.updateRecPmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4, paramFFSConnectionHolder, "POSTEDON");
                    }
                    else
                    {
                      localPmtInfo = new PmtInfo("", ((RecPmtInfo)localObject1).CustomerID, ((RecPmtInfo)localObject1).FIID, ((RecPmtInfo)localObject1).PayeeID, ((RecPmtInfo)localObject1).PayAcct, ((RecPmtInfo)localObject1).PayeeListID, ((RecPmtInfo)localObject1).getAmt(), ((RecPmtInfo)localObject1).BankID, ((RecPmtInfo)localObject1).AcctDebitID, ((RecPmtInfo)localObject1).AcctDebitType, ((RecPmtInfo)localObject1).Memo, ((RecPmtInfo)localObject1).ExtdPmtInfo, ((RecPmtInfo)localObject1).DateCreate, ((RecPmtInfo)localObject1).CurDef, "WILLPROCESSON", ((ScheduleInfo)localObject2).NextInstanceDate, "Recurring", str1, null, null, ((RecPmtInfo)localObject1).submittedBy);
                      if (((ScheduleInfo)localObject2).CurInstanceNum == ((ScheduleInfo)localObject2).InstanceCount) {
                        localPmtInfo.setAmt(((RecPmtInfo)localObject1).getFinalAmount());
                      }
                      if (!a(localPmtInfo))
                      {
                        localObject5 = "RecPmtHandler.eventHandler failed to process: Entitlement check failed. Customer Id= " + localPmtInfo.CustomerID;
                        FFSDebug.log((String)localObject5, 6);
                        RecPmtInstruction.updateStatus(paramFFSConnectionHolder, str1, "FAILEDON");
                        str2 = "FAILEDON";
                        ScheduleInfo.delete(paramFFSConnectionHolder, str1, "RECPMTTRN");
                        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInsts = -1;
                        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInstsExists = true;
                        localAuditAgent.updateRecPmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4, paramFFSConnectionHolder, str2);
                        j = 826;
                      }
                      else
                      {
                        localPmtInfo.LogID = str3;
                        localObject5 = new PmtInstruction();
                        ((PmtInstruction)localObject5).setPmtInfo(localPmtInfo);
                        ((PmtInstruction)localObject5).setSrvrTID();
                        ((PmtInstruction)localObject5).getPmtInfo().LogID = str3;
                        localObject6 = Payee.findPayeeByID(((RecPmtInfo)localObject1).PayeeID, paramFFSConnectionHolder);
                        ((PmtInstruction)localObject5).setRouteID(((PayeeInfo)localObject6).getRouteID());
                        ((PmtInstruction)localObject5).storeToDB(paramFFSConnectionHolder);
                        str4 = ((PmtInstruction)localObject5).getSrvrTID();
                        RecSrvrTIDToSrvrTID.create(paramFFSConnectionHolder, str1, str4);
                        localPmtInfo.SrvrTID = str4;
                        localPmtInfo.payeeInfo = ((PayeeInfo)localObject6);
                        localObject7 = new HashMap();
                        a(paramFFSConnectionHolder, localPmtInfo, (HashMap)localObject7, (ScheduleInfo)localObject2, ((PropertyConfig)localObject3).getFundsAllocRetries());
                        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInsts = (((ScheduleInfo)localObject2).InstanceCount - ((ScheduleInfo)localObject2).CurInstanceNum + 1);
                        if ((((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInsts == 1) && (((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.FinalAmtExists)) {
                          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.TrnAmt = ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.FinalAmt;
                        }
                        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.DtDue = (Integer.toString(((ScheduleInfo)localObject2).NextInstanceDate) + "0000");
                        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.InitialAmtExists = false;
                        localAuditAgent.updateRecPmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4, paramFFSConnectionHolder, "WILLPROCESSON");
                        localObject8 = MsgBuilder.getOFX151AcctEnum(localPmtInfo.AcctDebitType);
                        localObject9 = a(localPmtInfo, ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo, (PayeeInfo)localObject6, str1, (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumAccountEnum)localObject8);
                        localAuditAgent.savePmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject9, ((RecPmtInfo)localObject1).CustomerID, localPmtInfo.Status, paramFFSConnectionHolder);
                      }
                    }
                  }
                  else if (arrayOfString[0].startsWith("OFX200"))
                  {
                    localObject4 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "RecPmtTrnRsV1", "OFX200");
                    if (((ScheduleInfo)localObject2).Status.compareTo("Close") == 0)
                    {
                      RecPmtInstruction.updateStatus(paramFFSConnectionHolder, str1, "POSTEDON");
                      str2 = "POSTEDON";
                      ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInsts = 0;
                      ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.DtDue = (Integer.toString(((ScheduleInfo)localObject2).NextInstanceDate) + "0000");
                      localAuditAgent.updateRecPmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4, paramFFSConnectionHolder, "POSTEDON");
                    }
                    else
                    {
                      localPmtInfo = new PmtInfo("", ((RecPmtInfo)localObject1).CustomerID, ((RecPmtInfo)localObject1).FIID, ((RecPmtInfo)localObject1).PayeeID, ((RecPmtInfo)localObject1).PayAcct, ((RecPmtInfo)localObject1).PayeeListID, ((RecPmtInfo)localObject1).getAmt(), ((RecPmtInfo)localObject1).BankID, ((RecPmtInfo)localObject1).AcctDebitID, ((RecPmtInfo)localObject1).AcctDebitType, ((RecPmtInfo)localObject1).Memo, ((RecPmtInfo)localObject1).ExtdPmtInfo, ((RecPmtInfo)localObject1).DateCreate, ((RecPmtInfo)localObject1).CurDef, "WILLPROCESSON", ((ScheduleInfo)localObject2).NextInstanceDate, "Recurring", str1, null, null, ((RecPmtInfo)localObject1).submittedBy);
                      if (((ScheduleInfo)localObject2).CurInstanceNum == ((ScheduleInfo)localObject2).InstanceCount) {
                        localPmtInfo.setAmt(((RecPmtInfo)localObject1).getFinalAmount());
                      }
                      if (!a(localPmtInfo))
                      {
                        localObject5 = "RecPmtHandler.eventHandler failed to process: Entitlement check failed. Customer Id= " + localPmtInfo.CustomerID;
                        FFSDebug.log((String)localObject5, 6);
                        RecPmtInstruction.updateStatus(paramFFSConnectionHolder, str1, "FAILEDON");
                        str2 = "FAILEDON";
                        ScheduleInfo.delete(paramFFSConnectionHolder, str1, "RECPMTTRN");
                        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInsts = -1;
                        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInstsExists = true;
                        localAuditAgent.updateRecPmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4, paramFFSConnectionHolder, str2);
                        j = 826;
                      }
                      else
                      {
                        localPmtInfo.LogID = str3;
                        localObject5 = new PmtInstruction();
                        ((PmtInstruction)localObject5).setPmtInfo(localPmtInfo);
                        ((PmtInstruction)localObject5).setSrvrTID();
                        localObject6 = Payee.findPayeeByID(((RecPmtInfo)localObject1).PayeeID, paramFFSConnectionHolder);
                        ((PmtInstruction)localObject5).setRouteID(((PayeeInfo)localObject6).getRouteID());
                        ((PmtInstruction)localObject5).storeToDB(paramFFSConnectionHolder);
                        if ((localPmtInfo.ExtdPmtInfo != null) && (!localPmtInfo.ExtdPmtInfo.trim().equals("")) && (BPWExtraInfo.processXtraInfoAsString(paramFFSConnectionHolder, localPmtInfo.FIID, ((PmtInstruction)localObject5).getSrvrTID(), "IFXPMT", localPmtInfo.ExtdPmtInfo, "add") != 0))
                        {
                          str4 = "*** RecPmtHandler.eventHandler failed to process ExtraInfo:";
                          FFSDebug.log(str4, 0);
                          throw new FFSException(str4);
                        }
                        str4 = ((PmtInstruction)localObject5).getSrvrTID();
                        RecSrvrTIDToSrvrTID.create(paramFFSConnectionHolder, str1, str4);
                        localObject7 = new HashMap();
                        localPmtInfo.SrvrTID = str4;
                        localPmtInfo.payeeInfo = ((PayeeInfo)localObject6);
                        a(paramFFSConnectionHolder, localPmtInfo, (HashMap)localObject7, (ScheduleInfo)localObject2, ((PropertyConfig)localObject3).getFundsAllocRetries());
                        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInsts = (((ScheduleInfo)localObject2).InstanceCount - ((ScheduleInfo)localObject2).CurInstanceNum + 1);
                        if ((((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInsts == 1) && (((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.FinalAmtExists)) {
                          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.TrnAmt = ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.FinalAmt;
                        }
                        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.DtDue = (Integer.toString(((ScheduleInfo)localObject2).NextInstanceDate) + "0000");
                        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.InitialAmtExists = false;
                        localAuditAgent.updateRecPmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4, paramFFSConnectionHolder, "WILLPROCESSON");
                        localObject8 = MsgBuilder.getOFX200AcctEnum(localPmtInfo.AcctDebitType);
                        localObject9 = a(localPmtInfo, ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject4).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo, (PayeeInfo)localObject6, str1, (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum)localObject8);
                        localAuditAgent.savePmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject9, ((RecPmtInfo)localObject1).CustomerID, localPmtInfo.Status, paramFFSConnectionHolder);
                      }
                    }
                  }
                  else
                  {
                    throw new FFSException("Not supported OFXversion!");
                  }
                }
                else if (localObject2 != null)
                {
                  if (arrayOfString[0].startsWith("OFX151"))
                  {
                    localObject3 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "RecPmtTrnRsV1", "OFX151");
                    RecPmtInstruction.updateStatus(paramFFSConnectionHolder, str1, "POSTEDON");
                    str2 = "POSTEDON";
                    ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject3).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInsts = 0;
                    ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject3).RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.DtDue = (Integer.toString(((ScheduleInfo)localObject2).NextInstanceDate) + "0000");
                    localAuditAgent.updateRecPmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1)localObject3, paramFFSConnectionHolder, "POSTEDON");
                  }
                  else if (arrayOfString[0].startsWith("OFX200"))
                  {
                    localObject3 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "RecPmtTrnRsV1", "OFX200");
                    RecPmtInstruction.updateStatus(paramFFSConnectionHolder, str1, "POSTEDON");
                    str2 = "POSTEDON";
                    ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject3).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInsts = 0;
                    ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject3).RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.DtDue = (Integer.toString(((ScheduleInfo)localObject2).NextInstanceDate) + "0000");
                    localAuditAgent.updateRecPmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1)localObject3, paramFFSConnectionHolder, "POSTEDON");
                  }
                }
                else
                {
                  FFSDebug.log("Inconsistent data found: the ScheduleInfo object is null for recurring payment RecSrvrTID = " + str1 + "Please contact support.", 6);
                }
                Object localObject3 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
                if (((PropertyConfig)localObject3).LogLevel >= 4)
                {
                  localObject4 = new Object[1];
                  int k = 4455;
                  if (paramEventInfoArray._array[0].InstructionType.equals("CHECKFREE_PMTTRN"))
                  {
                    localObject4[0] = BPWLocaleUtil.getLocalizableMessage(200, null, "BILLPAY_AUDITLOG_MESSAGE");
                    k = 4456;
                  }
                  else if (paramEventInfoArray._array[0].InstructionType.equals("METAVANTE_PMTTRN"))
                  {
                    localObject4[0] = BPWLocaleUtil.getLocalizableMessage(201, null, "BILLPAY_AUDITLOG_MESSAGE");
                    k = 4458;
                  }
                  else if (paramEventInfoArray._array[0].InstructionType.equals("ON_US_PMTTRN"))
                  {
                    localObject4[0] = BPWLocaleUtil.getLocalizableMessage(202, null, "BILLPAY_AUDITLOG_MESSAGE");
                    k = 4460;
                  }
                  else if (paramEventInfoArray._array[0].InstructionType.equals("ORCC_PMTTRN"))
                  {
                    localObject4[0] = BPWLocaleUtil.getLocalizableMessage(203, null, "BILLPAY_AUDITLOG_MESSAGE");
                    k = 4462;
                  }
                  else if (paramEventInfoArray._array[0].InstructionType.equals("RPPS_PMTTRN"))
                  {
                    localObject4[0] = BPWLocaleUtil.getLocalizableMessage(204, null, "BILLPAY_AUDITLOG_MESSAGE");
                    k = 4464;
                  }
                  else if (paramEventInfoArray._array[0].InstructionType.equals("BANKSIM_PMTTRN"))
                  {
                    localObject4[0] = BPWLocaleUtil.getLocalizableMessage(205, null, "BILLPAY_AUDITLOG_MESSAGE");
                    k = 4466;
                  }
                  else if (paramEventInfoArray._array[0].InstructionType.equals("SAMPLE_PMTTRN"))
                  {
                    localObject4[0] = BPWLocaleUtil.getLocalizableMessage(206, null, "BILLPAY_AUDITLOG_MESSAGE");
                    k = 4468;
                  }
                  else
                  {
                    localObject4[0] = paramEventInfoArray._array[0].InstructionType;
                  }
                  int m = Integer.parseInt(((RecPmtInfo)localObject1).CustomerID);
                  localObject6 = FFSUtil.getBigDecimal(((RecPmtInfo)localObject1).getAmt());
                  str4 = BPWUtil.getAccountIDWithAccountType(((RecPmtInfo)localObject1).AcctDebitID, ((RecPmtInfo)localObject1).AcctDebitType);
                  localObject7 = BPWLocaleUtil.getLocalizableMessage(j, (Object[])localObject4, "BILLPAY_AUDITLOG_MESSAGE");
                  TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), ((RecPmtInfo)localObject1).submittedBy, null, null, (ILocalizable)localObject7, ((RecPmtInfo)localObject1).LogID, k, m, (BigDecimal)localObject6, ((RecPmtInfo)localObject1).CurDef, str1, str2, ((RecPmtInfo)localObject1).PayAcct, null, str4, null, 0);
                }
              }
            }
          }
        }
        if (paramInt != 2) {}
      }
      FFSDebug.log("==== RecPmtHandler.eventHander: end", 6);
    }
    catch (Exception localException)
    {
      str1 = "*** RecPmtHandler.eventHandler failed:";
      FFSDebug.log(str1 + localException.toString());
      throw new FFSException(localException.toString());
    }
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== RecPmtHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType);
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder);
    FFSDebug.log("=== RecPmtHandler.resubmitEventHandler: end");
  }
  
  private com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1 a(PmtInfo paramPmtInfo, TypePmtInfoV1Aggregate paramTypePmtInfoV1Aggregate, PayeeInfo paramPayeeInfo, String paramString, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumAccountEnum paramEnumAccountEnum)
    throws Exception
  {
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1Aggregate localTypePmtTrnRsV1Aggregate = new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1Aggregate();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1UnExists = true;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un = new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1Un();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName = "PmtRs";
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs = new TypePmtRsV1Aggregate();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.SrvrTID = paramPmtInfo.SrvrTID;
    String str1 = String.valueOf(paramPmtInfo.PayeeListID);
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PayeeLstID = str1;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CurDef = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCurrencyEnum.from_int(BPWUtil.getCurdefInt(paramPmtInfo.CurDef));
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayeeExists = false;
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    boolean bool = localPropertyConfig.UseExtdPayeeID;
    if ((!bool) || (!paramPayeeInfo.ExtdPayeeID.equals("0")))
    {
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayeeExists = true;
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee = new TypeExtdPayeeV1Aggregate();
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeV1CmExists = true;
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeV1Cm = new TypeExtdPayeeV1Cm();
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeV1Cm.PayeeID = (!bool ? paramPayeeInfo.PayeeID : paramPayeeInfo.ExtdPayeeID);
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeV1Cm.Name = paramPayeeInfo.PayeeName;
      if (paramPayeeInfo.PayeeType == 3) {
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeV1Cm.IDScope = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumIDScopeEnum.USER;
      } else {
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeV1Cm.IDScope = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumIDScopeEnum.GLOBAL;
      }
    }
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CheckNumExists = false;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTIDExists = true;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTID = paramString;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo = paramTypePmtInfoV1Aggregate;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts = new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtPrcStsAggregate();
    localTypePmtTrnRsV1Aggregate.TrnRsV1Cm = new TypeTrnRsV1Cm();
    localTypePmtTrnRsV1Aggregate.TrnRsV1Cm.Status = new TypeStatusV1Aggregate();
    String str2 = String.valueOf(paramPmtInfo.StartDate);
    int i = Integer.parseInt(str2.substring(0, 8));
    int j = SmartCalendar.getPayday(paramPmtInfo.FIID, i);
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.DtDue = str2.substring(0, 8);
    localTypePmtTrnRsV1Aggregate.TrnRsV1Cm.TrnUID = "0";
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = ("" + j + "000000");
    if (paramPmtInfo.Status.equals("WILLPROCESSON"))
    {
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.WILLPROCESSON;
      localTypePmtTrnRsV1Aggregate.TrnRsV1Cm.Status.Code = 0;
    }
    else
    {
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.FAILEDON;
      localTypePmtTrnRsV1Aggregate.TrnRsV1Cm.Status.Code = 2000;
      localTypePmtTrnRsV1Aggregate.TrnRsV1Cm.Status.MessageExists = true;
      localTypePmtTrnRsV1Aggregate.TrnRsV1Cm.Status.Message = MsgBuilder.getCmStatusMessage(localTypePmtTrnRsV1Aggregate.TrnRsV1Cm.Status.Code);
    }
    localTypePmtTrnRsV1Aggregate.TrnRsV1Cm.Status.Severity = MsgBuilder.getOFX151CmStatusSeverity(localTypePmtTrnRsV1Aggregate.TrnRsV1Cm.Status.Code);
    return new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1(localTypePmtTrnRsV1Aggregate);
  }
  
  private com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1 a(PmtInfo paramPmtInfo, TypePmtInfoAggregate paramTypePmtInfoAggregate, PayeeInfo paramPayeeInfo, String paramString, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum paramEnumAccountEnum)
    throws Exception
  {
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Aggregate localTypePmtTrnRsV1Aggregate = new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Aggregate();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1UnExists = true;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un = new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Un();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName = "PmtRs";
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs = new TypePmtRsAggregate();
    localTypePmtTrnRsV1Aggregate.TrnRsCm = new TypeTrnRsCm();
    localTypePmtTrnRsV1Aggregate.TrnRsCm.Status = new TypeStatusAggregate();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.SrvrTID = paramPmtInfo.SrvrTID;
    String str1 = String.valueOf(paramPmtInfo.PayeeListID);
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PayeeLstID = str1;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CurDef = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum.from_int(BPWUtil.getCurdefInt(paramPmtInfo.CurDef));
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayeeExists = false;
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    boolean bool = localPropertyConfig.UseExtdPayeeID;
    if ((!bool) || (!paramPayeeInfo.ExtdPayeeID.equals("0")))
    {
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayeeExists = true;
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee = new TypeExtdPayeeAggregate();
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCmExists = true;
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm = new TypeExtdPayeeCm();
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm.PayeeID = (!bool ? paramPayeeInfo.PayeeID : paramPayeeInfo.ExtdPayeeID);
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm.Name = paramPayeeInfo.PayeeName;
      if (paramPayeeInfo.PayeeType == 3) {
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm.IDScope = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumIDScopeEnum.USER;
      } else {
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm.IDScope = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumIDScopeEnum.GLOBAL;
      }
    }
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CheckNumExists = false;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTIDExists = true;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTID = paramString;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo = paramTypePmtInfoAggregate;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts = new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtPrcStsAggregate();
    String str2 = String.valueOf(paramPmtInfo.StartDate);
    int i = Integer.parseInt(str2.substring(0, 8));
    int j = SmartCalendar.getPayday(paramPmtInfo.FIID, i);
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = ("" + j + "000000");
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.DtDue = str2.substring(0, 8);
    localTypePmtTrnRsV1Aggregate.TrnRsCm.TrnUID = "0";
    if (paramPmtInfo.Status.equals("WILLPROCESSON"))
    {
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.WILLPROCESSON;
      localTypePmtTrnRsV1Aggregate.TrnRsCm.Status.Code = 0;
    }
    else
    {
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.FAILEDON;
      localTypePmtTrnRsV1Aggregate.TrnRsCm.Status.Code = 2000;
      localTypePmtTrnRsV1Aggregate.TrnRsCm.Status.MessageExists = true;
      localTypePmtTrnRsV1Aggregate.TrnRsCm.Status.Message = MsgBuilder.getCmStatusMessage(localTypePmtTrnRsV1Aggregate.TrnRsCm.Status.Code);
    }
    localTypePmtTrnRsV1Aggregate.TrnRsCm.Status.Severity = MsgBuilder.getOFX200CmStatusSeverity(localTypePmtTrnRsV1Aggregate.TrnRsCm.Status.Code);
    return new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1(localTypePmtTrnRsV1Aggregate);
  }
  
  private String a(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, HashMap paramHashMap, ScheduleInfo paramScheduleInfo, int paramInt)
    throws Exception
  {
    FFSDebug.log("RecPmtHandler.doLimitChecking", ": starts....", 6);
    String str = null;
    int i = LimitCheckApprovalProcessor.processPmtAdd(paramFFSConnectionHolder, paramPmtInfo, paramHashMap);
    str = LimitCheckApprovalProcessor.mapToStatus(i);
    if ("APPROVAL_PENDING".equalsIgnoreCase(str))
    {
      paramPmtInfo.Status = "APPROVAL_PENDING";
      PmtInstruction.updateStatus(paramFFSConnectionHolder, paramPmtInfo.SrvrTID, str);
      paramPmtInfo.Status = "WILLPROCESSON";
    }
    else if ("WILLPROCESSON".equalsIgnoreCase(str))
    {
      paramPmtInfo.Status = "WILLPROCESSON";
      ScheduleInfo localScheduleInfo = new ScheduleInfo();
      localScheduleInfo.FIId = paramPmtInfo.FIID;
      localScheduleInfo.Status = "Active";
      localScheduleInfo.Frequency = 10;
      localScheduleInfo.StartDate = paramScheduleInfo.NextInstanceDate;
      localScheduleInfo.InstanceCount = 1;
      localScheduleInfo.LogID = paramPmtInfo.LogID;
      FFSDebug.log("RecPmtHandler.doLimitChecking", ": creating FUNDTRN schedule. srvrTID: ", paramPmtInfo.SrvrTID, 6);
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "FUNDTRN", paramPmtInfo.SrvrTID, localScheduleInfo);
      localScheduleInfo.Frequency = 11;
      localScheduleInfo.InstanceCount = paramInt;
      localScheduleInfo.NextInstanceDate = localScheduleInfo.StartDate;
      ScheduleInfo.moveNextInstanceDate(localScheduleInfo);
      localScheduleInfo.StartDate = localScheduleInfo.NextInstanceDate;
      FFSDebug.log("RecPmtHandler.doLimitChecking", ": creating RECFUNDTRN schedule. srvrTID: ", paramPmtInfo.SrvrTID, 6);
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "RECFUNDTRN", paramPmtInfo.SrvrTID, localScheduleInfo);
    }
    else
    {
      PmtInstruction.updateStatus(paramFFSConnectionHolder, paramPmtInfo.SrvrTID, str);
      paramPmtInfo.Status = "FAILEDON";
      FFSDebug.log("RecPmtHandler.doLimitChecking", ": srvrTid: " + paramPmtInfo.SrvrTID + ", status: " + str, 6);
    }
    FFSDebug.log("RecPmtHandler.doLimitChecking", ": ends....", 6);
    return str;
  }
  
  private boolean a(PmtInfo paramPmtInfo)
    throws Exception
  {
    FFSDebug.log("RecPmtHandler.doEntitlementCheck", ": starts....", 6);
    return LimitCheckApprovalProcessor.checkEntitlementPmt(paramPmtInfo, null);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.RecPmtHandler
 * JD-Core Version:    0.7.0.1
 */