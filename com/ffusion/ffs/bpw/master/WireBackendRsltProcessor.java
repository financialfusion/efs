package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Wire;
import com.ffusion.ffs.bpw.db.WirePayee;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;

public class WireBackendRsltProcessor
  implements BPWResource, FFSConst, DBConsts
{
  public void processRslt(WireInfo[] paramArrayOfWireInfo)
    throws FFSException
  {
    String str1 = " WireBackendRsltProcessor.ProcessRslt: ";
    if ((paramArrayOfWireInfo == null) || (paramArrayOfWireInfo.length == 0))
    {
      FFSDebug.log("ERRORCODE:20010", str1, "Empty result batch received, nothing to process!", 0);
      return;
    }
    FFSDebug.log(str1, "start, wireRslts.length: " + paramArrayOfWireInfo.length, 6);
    int i = 0;
    String str2 = null;
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      str2 = localPropertyConfig.otherProperties.getProperty("bpw.wire.audit");
      if (str2 == null) {
        i = 0;
      } else {
        i = Integer.parseInt(str2);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log(str1, "Invalid Audit log level value", str2, 0);
      i = 0;
    }
    boolean bool = true;
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1;
    if (paramArrayOfWireInfo[0].getDbTransKey() != null)
    {
      DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
      localObject1 = paramArrayOfWireInfo[0].getDbTransKey();
      localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup((String)localObject1);
    }
    if (localFFSConnectionHolder == null)
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      bool = false;
    }
    if (localFFSConnectionHolder.conn == null)
    {
      FFSDebug.log("ERRORCODE:20020", str1, "Failed to obtain connection to bpw database....", 0);
      throw new BPWException(str1 + "Failed to obtain connection to bpw database....");
    }
    try
    {
      FFSDebug.log(str1, "Processing wire results. total#: " + paramArrayOfWireInfo.length, 6);
      for (int j = 0; j < paramArrayOfWireInfo.length; j++) {
        if (paramArrayOfWireInfo[j] == null)
        {
          FFSDebug.log("ERRORCODE:20030", str1, "Invalid wire transfer result #: " + j, 0);
          paramArrayOfWireInfo[j] = new WireInfo();
          paramArrayOfWireInfo[j].setStatusCode(16000);
          paramArrayOfWireInfo[j].setStatusMsg(" is null");
        }
        else if ((paramArrayOfWireInfo[j].getWirePayeeInfo() == null) && (paramArrayOfWireInfo[j].getWirePayeeId() != null) && (paramArrayOfWireInfo[j].getWirePayeeId().length() > 0))
        {
          localObject1 = WirePayee.getWirePayeeInfo(localFFSConnectionHolder, paramArrayOfWireInfo[j].getWirePayeeId(), false);
          if (((WirePayeeInfo)localObject1).getStatusCode() != 0)
          {
            FFSDebug.log("ERRORCODE:20130", str1, "Failed to retrieve corresponding wire payee ", "transfer result: " + paramArrayOfWireInfo[j], 0);
            if (i >= 1) {
              a(paramArrayOfWireInfo[j], "Process wire result failed, since failed to retrieve corresponding wire payee.");
            }
            paramArrayOfWireInfo[j].setStatusCode(((WirePayeeInfo)localObject1).getStatusCode());
            paramArrayOfWireInfo[j].setStatusMsg(((WirePayeeInfo)localObject1).getStatusMsg());
          }
          else
          {
            paramArrayOfWireInfo[j].setWirePayeeInfo((WirePayeeInfo)localObject1);
          }
        }
        else if ((paramArrayOfWireInfo[j].getSrvrTid() == null) || (paramArrayOfWireInfo[j].getSrvrTid().length() == 0))
        {
          FFSDebug.log("ERRORCODE:20040", str1, "Invalid srvrTid for wire ", "transfer result: " + paramArrayOfWireInfo[j], 0);
          if (i >= 1) {
            a(paramArrayOfWireInfo[j], "Process wire result failed, wire transfer Id is missing.");
          }
          paramArrayOfWireInfo[j].setStatusCode(16010);
          paramArrayOfWireInfo[j].setStatusMsg(" contains null ");
        }
        else
        {
          FFSDebug.log(str1, "Processing wire transfer result: " + paramArrayOfWireInfo[j], 6);
          jdMethod_case(localFFSConnectionHolder, paramArrayOfWireInfo[j]);
          FFSDebug.log(str1, "Processed wire transfer result: " + paramArrayOfWireInfo[j], 6);
        }
      }
      FFSDebug.log(str1, "Processed all wire transfer results", 6);
      if (!bool) {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (Throwable localThrowable)
    {
      if (!bool) {
        localFFSConnectionHolder.conn.rollback();
      }
      localObject1 = str1 + "Failed. Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException((String)localObject1);
    }
    finally
    {
      if (!bool)
      {
        FFSDebug.log(str1, "Releasing DB connection", 6);
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    FFSDebug.log(str1, " done, ownConn: " + bool, 6);
  }
  
  private static void jdMethod_case(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    FFSDebug.log("WireBackendRsltProcessor.processOneWireRslt: ", " wireRslt: ", paramWireInfo.toString(), 6);
    if (paramWireInfo == null)
    {
      FFSDebug.log("ERRORCODE:20015", "WireBackendRsltProcessor.processOneWireRslt: ", "Empty backend response ", 0);
      return;
    }
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    int i = 0;
    String str1 = null;
    try
    {
      str1 = localPropertyConfig.otherProperties.getProperty("bpw.wire.audit");
      if (str1 == null) {
        i = 0;
      } else {
        i = Integer.parseInt(str1);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireBackendRsltProcessor.processOneWireRslt: ", "Invalid Audit log level value", str1, 0);
      i = 0;
    }
    String str2 = paramWireInfo.getPrcStatus();
    WireInfo localWireInfo = Wire.getWireInfo(paramFFSConnectionHolder, paramWireInfo);
    if ((localWireInfo == null) || (localWireInfo.getStatusCode() == 16020))
    {
      FFSDebug.log("ERRORCODE:20090", "WireBackendRsltProcessor.processOneWireRslt: ", "NO RECORD FOUND FOR THIS RESULT: ", paramWireInfo.toString(), 6);
      if (i >= 1) {
        a(paramWireInfo, "Process wire result failed, wire transfer is not found in database.");
      }
      return;
    }
    String str3 = localWireInfo.getPrcStatus();
    FFSDebug.log("WireBackendRsltProcessor.processOneWireRslt: ", "backend handler returned wire's status rsltStatus: ", str2, 6);
    FFSDebug.log("WireBackendRsltProcessor.processOneWireRslt: ", "wire's current status in database currStatus: ", str3, 6);
    String str4;
    if ((str2 == null) || (str2.length() == 0))
    {
      FFSDebug.log("ERRORCODE:20070", "WireBackendRsltProcessor.processOneWireRslt: ", "INVALID STATUS PASSED FROM BACKEND: ", str2, ", for Wire: ", paramWireInfo.toString(), 0);
      if (i >= 1)
      {
        str4 = "Process wire result failed because backend handler returned invalid status: " + str2;
        a(localWireInfo, str4);
      }
      if (!a(paramFFSConnectionHolder, localWireInfo, i))
      {
        localWireInfo.setPrcStatus("LIMIT_REVERT_FAILED");
        Wire.updateStatus(paramFFSConnectionHolder, localWireInfo);
      }
      return;
    }
    if ((str3 == null) || (str3.length() == 0))
    {
      FFSDebug.log("ERRORCODE:20060", "WireBackendRsltProcessor.processOneWireRslt: ", "INVALID STATUS IN BPW DB: ", str3, ", for Wire: ", localWireInfo.toString(), 0);
      if (i >= 1)
      {
        str4 = "Process wire result failed because wire is currently in invalid status in database: " + str3;
        a(localWireInfo, str4);
      }
      if (!a(paramFFSConnectionHolder, localWireInfo, i))
      {
        localWireInfo.setPrcStatus("LIMIT_REVERT_FAILED");
        Wire.updateStatus(paramFFSConnectionHolder, localWireInfo);
      }
      return;
    }
    if ((!"ACCEPTED".equalsIgnoreCase(str3)) && (!"ACKNOWLEDGED".equalsIgnoreCase(str3)) && (!"POSTEDON".equalsIgnoreCase(str3)) && (!"BACKENDPENDING".equalsIgnoreCase(str3)) && (!"COMPLETED".equalsIgnoreCase(str3)) && (!"CONFIRMED".equalsIgnoreCase(str3)) && (!"INPROCESS".equalsIgnoreCase(str3)) && (!"IMMED_INPROCESS".equalsIgnoreCase(str3)) && (!"FUNDSAPPROVED".equalsIgnoreCase(str3)) && (!str3.startsWith("CUSTOM_")) && (!paramWireInfo.getPossibleDuplicate()))
    {
      FFSDebug.log("ERRORCODE:20060", "WireBackendRsltProcessor.processOneWireRslt: ", "INVALID STATUS IN BPW DB: ", str3, ", for Wire: ", localWireInfo.toString(), 0);
      if (i >= 1)
      {
        str4 = "Process wire result failed because wire is currently in invalid status in database: " + str3;
        a(localWireInfo, str4);
      }
      if (!a(paramFFSConnectionHolder, localWireInfo, i))
      {
        localWireInfo.setPrcStatus("LIMIT_REVERT_FAILED");
        Wire.updateStatus(paramFFSConnectionHolder, localWireInfo);
      }
      return;
    }
    try
    {
      Wire.updateFromBackendStatus(paramFFSConnectionHolder, paramWireInfo);
      a(localWireInfo, paramWireInfo);
      if (("BACKENDFAILED".equalsIgnoreCase(str2)) || ("BACKENDREJECTED".equalsIgnoreCase(str2)))
      {
        FFSDebug.log("WireBackendRsltProcessor.processOneWireRslt: ", "creating schedule to revert funds ", "for a failed wire: ", paramWireInfo.toString(), 6);
        paramWireInfo.setFiID(localWireInfo.getFiID());
        paramWireInfo.setLogId(localWireInfo.getLogId());
        jdMethod_byte(paramFFSConnectionHolder, paramWireInfo);
        if (!a(paramFFSConnectionHolder, localWireInfo, i))
        {
          localWireInfo.setPrcStatus("LIMIT_REVERT_FAILED");
          Wire.updateStatus(paramFFSConnectionHolder, localWireInfo);
        }
      }
      if (str2.equals("COMPLETED"))
      {
        if (i >= 1) {
          a(paramFFSConnectionHolder, localWireInfo, str2);
        }
      }
      else if (i >= 4) {
        a(paramFFSConnectionHolder, localWireInfo, str2);
      }
    }
    catch (Throwable localThrowable)
    {
      String str5 = "WireBackendRsltProcessor.processOneWireRslt:  Error:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5, 0);
      if (i >= 1) {
        a(paramWireInfo, "Process wire result failed, unknown error occurred.");
      }
      throw new FFSException(str5);
    }
    FFSDebug.log("WireBackendRsltProcessor.processOneWireRslt: ", " done", 6);
  }
  
  private static void a(WireInfo paramWireInfo1, WireInfo paramWireInfo2)
  {
    String str1 = "WireBackendRsltProcessor.updateWireInfo";
    if ((paramWireInfo1 == null) || (paramWireInfo2 == null)) {
      return;
    }
    String str2 = paramWireInfo2.getPrcStatus();
    paramWireInfo1.setPrcStatus(str2);
    String str3 = paramWireInfo2.getDatePosted();
    if ((str3 == null) || (str3.trim().length() == 0)) {
      str3 = FFSUtil.getDateString("yyyyMMddHHmmss");
    }
    paramWireInfo1.setDatePosted(str3);
    String str4 = paramWireInfo2.getAmount();
    if ((str4 != null) && (str4.trim().length() > 0)) {
      paramWireInfo1.setAmount(str4);
    }
    String str5 = paramWireInfo2.getAmtCurrency();
    if ((str5 != null) && (str5.trim().length() > 0)) {
      paramWireInfo1.setAmtCurrency(str5);
    }
    String str6 = paramWireInfo2.getDestCurrency();
    if ((str6 != null) && (str6.trim().length() > 0)) {
      paramWireInfo1.setDestCurrency(str6);
    }
    String str7 = paramWireInfo2.getWireFee();
    if ((str7 != null) && (str7.trim().length() > 0)) {
      paramWireInfo1.setWireFee(str7);
    }
    String str8 = paramWireInfo2.getPayInstruct();
    if ((str8 != null) && (str8.trim().length() > 0)) {
      paramWireInfo1.setPayInstruct(str8);
    }
    String str9 = paramWireInfo2.getExchangeRate();
    if ((str9 != null) && (str9.trim().length() > 0)) {
      paramWireInfo1.setExchangeRate(str9);
    }
    String str10 = paramWireInfo2.getConfirmNum();
    if ((str10 != null) && (str10.trim().length() > 0)) {
      paramWireInfo1.setConfirmNum(str10);
    }
    String str11 = paramWireInfo2.getConfirmNum2();
    if ((str11 != null) && (str11.trim().length() > 0)) {
      paramWireInfo1.setConfirmNum2(str11);
    }
    String str12 = paramWireInfo2.getConfirmMsg();
    if ((str12 != null) && (str12.trim().length() > 0)) {
      paramWireInfo1.setConfirmMsg(str12);
    }
    String str13 = paramWireInfo2.getContractNumber();
    if ((str13 != null) && (str13.trim().length() > 0)) {
      paramWireInfo1.setContractNumber(str13);
    }
    String str14 = paramWireInfo2.getBanktoBankInfo();
    if ((str14 != null) && (str14.trim().length() > 0)) {
      paramWireInfo1.setBanktoBankInfo(str14);
    }
    String str15 = paramWireInfo2.getAgentId();
    if ((str15 != null) && (str15.trim().length() > 0)) {
      paramWireInfo1.setAgentId(str15);
    }
    String str16 = paramWireInfo2.getAgentType();
    if ((str16 != null) && (str16.trim().length() > 0)) {
      paramWireInfo1.setAgentType(str16);
    }
    String str17 = paramWireInfo2.getAgentName();
    if ((str17 != null) && (str17.trim().length() > 0)) {
      paramWireInfo1.setAgentName(str17);
    }
    String str18 = paramWireInfo2.getProcessedBy();
    if ((str18 != null) && (str18.trim().length() > 0)) {
      paramWireInfo1.setProcessedBy(str18);
    }
  }
  
  private static String jdMethod_byte(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    FFSDebug.log("WireBackendRsltProcessor.revertFundsApproval: ", "starts. wireRslt: ", paramWireInfo.toString(), 6);
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = localPropertyConfig.otherProperties.getProperty("bpw.wire.funds.approval.support", "false");
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramWireInfo.getFiID();
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = 10;
    localScheduleInfo.StartDate = DBUtil.getCurrentStartDate();
    localScheduleInfo.InstanceCount = 1;
    localScheduleInfo.LogID = paramWireInfo.getLogId();
    localScheduleInfo.CurInstanceNum = 1;
    if ("false".equalsIgnoreCase(str)) {
      return ScheduleInfo.createSchedule(paramFFSConnectionHolder, "FAILEDWIRETRN", paramWireInfo.getSrvrTid(), localScheduleInfo);
    }
    return ScheduleInfo.createSchedule(paramFFSConnectionHolder, "WIREREVERTTRN", paramWireInfo.getSrvrTid(), localScheduleInfo);
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, String paramString)
    throws Exception
  {
    if (paramWireInfo == null) {
      return;
    }
    try
    {
      AuditLogRecord localAuditLogRecord = null;
      str1 = null;
      String str2 = null;
      String str3 = null;
      String str4 = null;
      int i = 0;
      String str5 = null;
      int j = 0;
      String str6 = "Wire backend result processing";
      if (("COMPLETED".equals(paramString)) || ("POSTEDON".equals(paramString))) {
        str6 = "The single wire transfer has successfully completed backend processing.";
      } else if ("ACCEPTED".equals(paramString)) {
        str6 = "The single wire transfer has successfully accepted by backend processing.";
      } else if ("BACKENDPENDING".equals(paramString)) {
        str6 = "The single wire transfer is pending backend processing.";
      } else if ("BACKENDFAILED".equals(paramString)) {
        str6 = "The single wire transfer was failed in backend processing.";
      } else if ("BACKENDREJECTED".equals(paramString)) {
        str6 = "The single wire transfer was rejected in backend processing.";
      } else if ("CONFIRMED".equals(paramString)) {
        str6 = "The single wire transfer was successfully confirmed by backend processing.";
      } else if ("ACKNOWLEDGED".equals(paramString)) {
        str6 = "The single wire transfer was successfully acknowledged by backend processing.";
      }
      str1 = paramWireInfo.getAmount();
      if ((str1 == null) || (str1.trim().length() == 0)) {
        str1 = "-1";
      }
      if (paramWireInfo.getWirePayeeInfo() != null)
      {
        if (paramWireInfo.getWireDest().equals("HOST")) {
          str2 = "HOST";
        } else {
          str2 = paramWireInfo.getWirePayeeInfo().buildBankAcctId();
        }
        if (paramWireInfo.getWirePayeeInfo().getBeneficiaryBankInfo() != null) {
          str3 = paramWireInfo.getWirePayeeInfo().getBeneficiaryBankInfo().getFedRTN();
        }
      }
      str5 = paramWireInfo.getWireDest();
      str4 = paramWireInfo.getFromAcctId();
      if ((str4 == null) || (str4.trim().length() == 0)) {
        str4 = "ACCOUNT";
      } else if (str5.equals("HOST")) {
        str4 = "HOST";
      } else {
        str4 = paramWireInfo.buildFromAcctId();
      }
      if (str5.equals("INTERNATIONAL"))
      {
        if ((paramWireInfo.getPrcStatus().equals("POSTEDON")) || (paramWireInfo.getPrcStatus().equals("COMPLETED"))) {
          i = 3605;
        } else {
          i = 4009;
        }
      }
      else if (str5.equals("DRAWDOWN"))
      {
        if ((paramWireInfo.getPrcStatus().equals("POSTEDON")) || (paramWireInfo.getPrcStatus().equals("COMPLETED"))) {
          i = 3606;
        } else {
          i = 4011;
        }
      }
      else if (str5.equals("BOOKTRANSFER"))
      {
        if ((paramWireInfo.getPrcStatus().equals("POSTEDON")) || (paramWireInfo.getPrcStatus().equals("COMPLETED"))) {
          i = 3607;
        } else {
          i = 4012;
        }
      }
      else if (str5.equals("FEDWIRE"))
      {
        if ((paramWireInfo.getPrcStatus().equals("POSTEDON")) || (paramWireInfo.getPrcStatus().equals("COMPLETED"))) {
          i = 3608;
        } else {
          i = 4013;
        }
      }
      else if (str5.equals("HOST"))
      {
        if ((paramWireInfo.getPrcStatus().equals("POSTEDON")) || (paramWireInfo.getPrcStatus().equals("COMPLETED"))) {
          i = 3612;
        } else {
          i = 4015;
        }
      }
      else if ((paramWireInfo.getPrcStatus().equals("POSTEDON")) || (paramWireInfo.getPrcStatus().equals("COMPLETED"))) {
        i = 3604;
      } else {
        i = 4010;
      }
      if ((paramWireInfo.getCustomerID().equals(paramWireInfo.getUserId())) || (paramWireInfo.getCustomerID().equals(paramWireInfo.getSubmitedBy()))) {
        j = 0;
      } else {
        j = Integer.parseInt(paramWireInfo.getCustomerID());
      }
      if (!str5.equals("DRAWDOWN")) {
        localAuditLogRecord = new AuditLogRecord(paramWireInfo.getUserId(), null, null, str6, paramWireInfo.getExtId(), i, j, new BigDecimal(str1), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramString, str2, str3, str4, paramWireInfo.getFromBankId(), -1);
      } else {
        localAuditLogRecord = new AuditLogRecord(paramWireInfo.getUserId(), null, null, str6, paramWireInfo.getExtId(), i, j, new BigDecimal(str1), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramString, str4, paramWireInfo.getFromBankId(), str2, str3, -1);
      }
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      String str1 = "WireBackendRsltProcessor.doAuditLog failed " + localNumberFormatException.toString();
      FFSDebug.log(str1 + FFSDebug.stackTrace(localNumberFormatException), 0);
      throw new FFSException(localNumberFormatException, str1);
    }
  }
  
  private static void a(WireInfo paramWireInfo, String paramString)
  {
    String str1 = "WireBackendRsltProcessor.doErrorAuditLog";
    FFSConnectionHolder localFFSConnectionHolder = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    int i = 0;
    AuditLogRecord localAuditLogRecord = null;
    if (paramWireInfo == null) {
      return;
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        String str6 = str1 + "Can not get DB Connection.";
        FFSDebug.log(str6, 0);
      }
      str5 = paramWireInfo.getAmount();
      if ((str5 == null) || (str5.trim().length() == 0)) {
        str5 = "-1";
      }
      if (paramWireInfo.getWirePayeeInfo() != null)
      {
        if (paramWireInfo.getWireDest().equals("HOST")) {
          str2 = "HOST";
        } else {
          str2 = paramWireInfo.getWirePayeeInfo().buildBankAcctId();
        }
        if (paramWireInfo.getWirePayeeInfo().getBeneficiaryBankInfo() != null) {
          str3 = paramWireInfo.getWirePayeeInfo().getBeneficiaryBankInfo().getFedRTN();
        }
      }
      str4 = paramWireInfo.getFromAcctId();
      if ((str4 == null) || (str4.trim().length() == 0)) {
        str4 = "ACCOUNT";
      } else {
        str4 = paramWireInfo.buildFromAcctId();
      }
      if (paramWireInfo.getWireDest().equals("HOST")) {
        str4 = "HOST";
      }
      if ((paramWireInfo.getCustomerID().equals(paramWireInfo.getUserId())) || (paramWireInfo.getCustomerID().equals(paramWireInfo.getSubmitedBy()))) {
        i = 0;
      } else {
        i = Integer.parseInt(paramWireInfo.getCustomerID());
      }
      localAuditLogRecord = new AuditLogRecord(paramWireInfo.getUserId(), null, null, paramString, paramWireInfo.getExtId(), 4008, i, new BigDecimal(str5), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramWireInfo.getPrcStatus(), str2, str3, str4, paramWireInfo.getFromBankId(), -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, localFFSConnectionHolder.conn.getConnection());
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      String str7 = str1 + " failed " + localException.toString();
      FFSDebug.log(str7 + FFSDebug.stackTrace(localException), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private static boolean a(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, int paramInt)
  {
    String str1 = "WireBackendRsltProcessor.revertLimit: ";
    FFSDebug.log(str1 + "start", 6);
    try
    {
      int i = LimitCheckApprovalProcessor.processWireDelete(paramFFSConnectionHolder, paramWireInfo, null);
      String str2 = LimitCheckApprovalProcessor.mapToStatus(i);
      FFSDebug.log(str1, "retStatus LimitCheck:" + str2, 6);
      if ("LIMIT_REVERT_FAILED".equals(str2))
      {
        FFSDebug.log(str1, "Limit Revert Failed", 6);
        paramWireInfo.setStatusCode(19020);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19020, null, "WIRE_MESSAGE"));
        if (paramInt >= 1)
        {
          paramWireInfo.setPrcStatus("LIMIT_REVERT_FAILED");
          a(paramWireInfo, "Failed to revert limit.");
        }
        FFSDebug.log(str1, "returning from here", 6);
        return false;
      }
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log(str1 + FFSDebug.stackTrace(localThrowable), 6);
      if (paramInt >= 1)
      {
        paramWireInfo.setPrcStatus("LIMIT_REVERT_FAILED");
        a(paramWireInfo, "Failed to revert limit.");
      }
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.WireBackendRsltProcessor
 * JD-Core Version:    0.7.0.1
 */