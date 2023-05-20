package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.custimpl.FundsAllocator;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.BankInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.FundsAllocInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.beans.LocalizableString;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class RevertFundsAllocHandler
  implements DBConsts, FFSConst, BPWResource
{
  private ArrayList hU = null;
  private FundsAllocator hT = null;
  private static int hV = 1;
  private String hS = "RevertFunds";
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== RevertFundsAllocHandler.eventHandler: begin, eventSeq=" + paramInt, 6);
    String str1 = paramEventInfoArray._array[0].FIId;
    Object localObject1;
    if (paramInt == 0)
    {
      this.hU = new ArrayList();
      String str2 = this.hS + Integer.toString(hV);
      localObject1 = new FundsAllocInfo(str1, "", "", "", "", "", "", "", "", paramInt, false, "", str2);
      this.hU.add(localObject1);
    }
    else
    {
      if (paramInt == 1) {
        for (int i = 0; i < paramEventInfoArray._array.length; i++)
        {
          localObject1 = paramEventInfoArray._array[i].InstructionID;
          FFSDebug.log("=== RevertFundsAllocHandler.eventHandler: eventSeq=" + paramInt + ",srvrTID=" + (String)localObject1, 6);
          try
          {
            PmtInstruction localPmtInstruction = PmtInstruction.getPmtInstr((String)localObject1, paramFFSConnectionHolder);
            Object localObject2;
            if (localPmtInstruction == null)
            {
              localObject2 = "*** RevertFundsAllocHandler.eventHandler failed: could not find the SrvrTID=" + (String)localObject1 + " in BPW_PmtInstruction";
              FFSDebug.console((String)localObject2);
              FFSDebug.log((String)localObject2);
            }
            else
            {
              localObject2 = localPmtInstruction.getPmtInfo();
              PayeeInfo localPayeeInfo = Payee.findPayeeByID(((PmtInfo)localObject2).PayeeID, paramFFSConnectionHolder);
              Object localObject3;
              if (localPayeeInfo == null)
              {
                localObject3 = "*** RevertFundsAllocHandler.eventHandler failed: could not find the PayeeID=" + ((PmtInfo)localObject2).PayeeID + " in BPW_Payee for pmt of SrvrTID=" + (String)localObject1;
                FFSDebug.console((String)localObject3);
                FFSDebug.log((String)localObject3);
              }
              else
              {
                localObject3 = BPWRegistryUtil.getBankInfo(((PmtInfo)localObject2).BankID);
                PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
                String str4 = null;
                if (localObject3 == null)
                {
                  str5 = localPropertyConfig.otherProperties.getProperty("bpw.pmt.bankinfo.lookup.level", String.valueOf("STRICT"));
                  if (str5.equalsIgnoreCase("STRICT"))
                  {
                    localObject4 = "*** RevertFundsAllocHandler.eventHandler failed:could not find the BankID in BPW_Bank, bpw.pmt.bankinfo.lookup.level = STRICT ";
                    FFSDebug.console((String)localObject4);
                    FFSDebug.log((String)localObject4, 0);
                    throw new FFSException((String)localObject4);
                  }
                  if (str5.equalsIgnoreCase("LENIENT"))
                  {
                    localObject4 = "*** RevertFundsAllocHandler.eventHandler : bpw.pmt.bankinfo.lookup.level = LENIENT ";
                    FFSDebug.log((String)localObject4, 1);
                  }
                  else if (str5.equalsIgnoreCase("NONE"))
                  {
                    localObject4 = "*** RevertFundsAllocHandler.eventHandler : bpw.pmt.bankinfo.lookup.level = NONE ";
                    FFSDebug.log((String)localObject4, 6);
                  }
                  else
                  {
                    localObject4 = "*** RevertFundsAllocHandler.eventHandler failed: Incorrect value for bpw.pmt.bankinfo.lookup.level =" + str5;
                    FFSDebug.console((String)localObject4);
                    FFSDebug.log((String)localObject4);
                    throw new Exception((String)localObject4);
                  }
                  str4 = String.valueOf(-1);
                }
                else
                {
                  str4 = ((BankInfo)localObject3).debitGLAcct;
                }
                String str5 = this.hS + Integer.toString(hV);
                Object localObject4 = new FundsAllocInfo(((PmtInfo)localObject2).FIID, ((PmtInfo)localObject2).CustomerID, ((PmtInfo)localObject2).BankID, ((PmtInfo)localObject2).AcctDebitID, ((PmtInfo)localObject2).AcctDebitType, ((PmtInfo)localObject2).getAmt(), ((PmtInfo)localObject2).CurDef, (String)localObject1, ((PmtInfo)localObject2).PayeeID, localPayeeInfo.PayeeName, paramInt, false, str4, str5, ((PmtInfo)localObject2).RecSrvrTID);
                if (((PmtInfo)localObject2).extraFields != null) {
                  ((FundsAllocInfo)localObject4).extraFields = ((HashMap)((PmtInfo)localObject2).extraFields);
                }
                this.hU.add(localObject4);
                PmtInstruction.updateStatus(paramFFSConnectionHolder, (String)localObject1, "INFUNDSREVERT");
                if (localPropertyConfig.LogLevel >= 4)
                {
                  int j = Integer.parseInt(((PmtInfo)localObject2).CustomerID);
                  BigDecimal localBigDecimal = FFSUtil.getBigDecimal(((PmtInfo)localObject2).getAmt());
                  String str6 = BPWUtil.getAccountIDWithAccountType(((PmtInfo)localObject2).AcctDebitID, ((PmtInfo)localObject2).AcctDebitType);
                  LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(702, null, "BILLPAY_AUDITLOG_MESSAGE");
                  TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), ((PmtInfo)localObject2).submittedBy, null, null, localLocalizableString, ((PmtInfo)localObject2).LogID, 4453, j, localBigDecimal, ((PmtInfo)localObject2).CurDef, (String)localObject1, "INFUNDSREVERT", ((PmtInfo)localObject2).PayAcct, null, str6, null, 0);
                }
              }
            }
          }
          catch (Exception localException)
          {
            FFSDebug.log("*** RevertFundsAllocHandler.eventHandler failed:" + localException.toString());
            throw new FFSException(localException.toString());
          }
        }
      }
      String str3;
      if (paramInt == 2)
      {
        str3 = this.hS + Integer.toString(hV);
        hV += 1;
        DBConnCache.bind(str3, paramFFSConnectionHolder);
        localObject1 = new FundsAllocInfo(str1, "", "", "", "", "", "", "", "", paramInt, false, "", str3);
        this.hU.add(localObject1);
        FundsAllocInfo[] arrayOfFundsAllocInfo = (FundsAllocInfo[])this.hU.toArray(new FundsAllocInfo[0]);
        this.hT.revertFundsAllocation(arrayOfFundsAllocInfo);
        DBConnCache.unbind(str3);
        this.hU.clear();
        this.hU = null;
      }
      else if ((paramInt != 3) && (paramInt == 4))
      {
        str3 = this.hS + Integer.toString(hV);
        hV += 1;
        DBConnCache.bind(str3, paramFFSConnectionHolder);
        localObject1 = (FundsAllocInfo[])this.hU.toArray(new FundsAllocInfo[0]);
        this.hT.revertFundsAllocation((FundsAllocInfo[])localObject1);
        DBConnCache.unbind(str3);
        this.hU.clear();
      }
    }
    FFSDebug.log("=== RevertFundsAllocHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== RevertFundsAllocHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length);
    String str1 = paramEventInfoArray._array[0].FIId;
    Object localObject1;
    if (paramInt == 0)
    {
      this.hU = new ArrayList();
      String str2 = this.hS + Integer.toString(hV);
      localObject1 = new FundsAllocInfo(str1, "", "", "", "", "", "", "", "", paramInt, true, "", str2);
      this.hU.add(localObject1);
    }
    else
    {
      if (paramInt == 1) {
        for (int i = 0; i < paramEventInfoArray._array.length; i++)
        {
          localObject1 = paramEventInfoArray._array[i].InstructionID;
          FFSDebug.log("=== RevertFundsAllocHandler.resubmitEventHandler: eventSeq=" + paramInt + ",srvrTID=" + (String)localObject1);
          try
          {
            PmtInstruction localPmtInstruction = PmtInstruction.getPmtInstr((String)localObject1, paramFFSConnectionHolder);
            Object localObject2;
            if (localPmtInstruction == null)
            {
              localObject2 = "*** RevertFundsAllocHandler.resubmitEventHandler failed: could not find the SrvrTID=" + (String)localObject1 + " in BPW_PmtInstruction";
              FFSDebug.console((String)localObject2);
              FFSDebug.log((String)localObject2);
            }
            else
            {
              localObject2 = localPmtInstruction.getPmtInfo();
              PayeeInfo localPayeeInfo = Payee.findPayeeByID(((PmtInfo)localObject2).PayeeID, paramFFSConnectionHolder);
              Object localObject3;
              if (localPayeeInfo == null)
              {
                localObject3 = "*** RevertFundsAllocHandler.resubmitEventHandler failed: could not find the PayeeID=" + ((PmtInfo)localObject2).PayeeID + " in BPW_Payee for pmt of SrvrTID=" + (String)localObject1;
                FFSDebug.console((String)localObject3);
                FFSDebug.log((String)localObject3);
              }
              else
              {
                localObject3 = BPWRegistryUtil.getBankInfo(((PmtInfo)localObject2).BankID);
                String str4 = null;
                PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
                if (localObject3 == null)
                {
                  str5 = localPropertyConfig.otherProperties.getProperty("bpw.pmt.bankinfo.lookup.level", String.valueOf("STRICT"));
                  if (str5.equalsIgnoreCase("STRICT"))
                  {
                    localObject4 = "*** RevertFundsAllocHandler.resubmitEventHandler failed:could not find the BankID in BPW_Bank, bpw.pmt.bankinfo.lookup.level = STRICT ";
                    FFSDebug.console((String)localObject4);
                    FFSDebug.log((String)localObject4, 0);
                    throw new FFSException((String)localObject4);
                  }
                  if (str5.equalsIgnoreCase("LENIENT"))
                  {
                    localObject4 = "*** RevertFundsAllocHandler.resubmitEventHandler : bpw.pmt.bankinfo.lookup.level = LENIENT ";
                    FFSDebug.log((String)localObject4, 1);
                  }
                  else if (str5.equalsIgnoreCase("NONE"))
                  {
                    localObject4 = "*** RevertFundsAllocHandler.resubmitEventHandler : bpw.pmt.bankinfo.lookup.level = NONE ";
                    FFSDebug.log((String)localObject4, 6);
                  }
                  else
                  {
                    localObject4 = "*** RevertFundsAllocHandler.resubmitEventHandler failed: Incorrect value for bpw.pmt.bankinfo.lookup.level =" + str5;
                    FFSDebug.console((String)localObject4);
                    FFSDebug.log((String)localObject4);
                    throw new Exception((String)localObject4);
                  }
                  str4 = String.valueOf(-1);
                }
                else
                {
                  str4 = ((BankInfo)localObject3).debitGLAcct;
                }
                String str5 = this.hS + Integer.toString(hV);
                Object localObject4 = new FundsAllocInfo(((PmtInfo)localObject2).FIID, ((PmtInfo)localObject2).CustomerID, ((PmtInfo)localObject2).BankID, ((PmtInfo)localObject2).AcctDebitID, ((PmtInfo)localObject2).AcctDebitType, ((PmtInfo)localObject2).getAmt(), ((PmtInfo)localObject2).CurDef, (String)localObject1, ((PmtInfo)localObject2).PayeeID, localPayeeInfo.PayeeName, paramInt, true, str4, str5, ((PmtInfo)localObject2).RecSrvrTID);
                if (((PmtInfo)localObject2).extraFields != null) {
                  ((FundsAllocInfo)localObject4).extraFields = ((HashMap)((PmtInfo)localObject2).extraFields);
                }
                this.hU.add(localObject4);
                PmtInstruction.updateStatus(paramFFSConnectionHolder, (String)localObject1, "INFUNDSREVERT");
                if (localPropertyConfig.LogLevel >= 4)
                {
                  int j = Integer.parseInt(((PmtInfo)localObject2).CustomerID);
                  BigDecimal localBigDecimal = FFSUtil.getBigDecimal(((PmtInfo)localObject2).getAmt());
                  String str6 = BPWUtil.getAccountIDWithAccountType(((PmtInfo)localObject2).AcctDebitID, ((PmtInfo)localObject2).AcctDebitType);
                  LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(703, null, "BILLPAY_AUDITLOG_MESSAGE");
                  TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), ((PmtInfo)localObject2).submittedBy, null, null, localLocalizableString, ((PmtInfo)localObject2).LogID, 4453, j, localBigDecimal, ((PmtInfo)localObject2).CurDef, (String)localObject1, "INFUNDSREVERT", ((PmtInfo)localObject2).PayAcct, null, str6, null, 0);
                }
              }
            }
          }
          catch (Exception localException)
          {
            throw new FFSException(localException.getMessage());
          }
        }
      }
      String str3;
      if (paramInt == 2)
      {
        str3 = this.hS + Integer.toString(hV);
        hV += 1;
        DBConnCache.bind(str3, paramFFSConnectionHolder);
        localObject1 = new FundsAllocInfo(str1, "", "", "", "", "", "", "", "", paramInt, true, "", str3);
        this.hU.add(localObject1);
        FundsAllocInfo[] arrayOfFundsAllocInfo = (FundsAllocInfo[])this.hU.toArray(new FundsAllocInfo[0]);
        this.hT.revertFundsAllocation(arrayOfFundsAllocInfo);
        DBConnCache.unbind(str3);
        this.hU = null;
      }
      else if ((paramInt != 3) && (paramInt == 4))
      {
        str3 = this.hS + Integer.toString(hV);
        hV += 1;
        DBConnCache.bind(str3, paramFFSConnectionHolder);
        localObject1 = (FundsAllocInfo[])this.hU.toArray(new FundsAllocInfo[0]);
        this.hT.revertFundsAllocation((FundsAllocInfo[])localObject1);
        DBConnCache.unbind(str3);
        this.hU.clear();
      }
    }
    FFSDebug.log("==== RevertFundsAllocHandler.resubmitEventHandler: end");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.RevertFundsAllocHandler
 * JD-Core Version:    0.7.0.1
 */