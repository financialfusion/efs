package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.BPWServer;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.custimpl.WireApproval;
import com.ffusion.ffs.bpw.custimpl.WireBackendHandler;
import com.ffusion.ffs.bpw.db.BPWFI;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.DBPropertyConfig;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.Trans;
import com.ffusion.ffs.bpw.db.Wire;
import com.ffusion.ffs.bpw.db.WirePayee;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.BankingDays;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.ProcessingWindowInfo;
import com.ffusion.ffs.bpw.interfaces.ProcessingWindowList;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecWireInfo;
import com.ffusion.ffs.bpw.interfaces.SchedulerInfo;
import com.ffusion.ffs.bpw.interfaces.WireBatchInfo;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.ffs.bpw.interfaces.WireReleaseInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.Scheduler;
import com.ffusion.ffs.scheduling.db.ScheduleConstants;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

public class WireProcessor
  implements DBConsts, FFSConst, OFXConsts, ACHConsts, BPWResource, ScheduleConstants
{
  private static PropertyConfig A;
  private static int C = -1;
  private static String G = null;
  private static int H = -1;
  private static String K = null;
  private boolean I = true;
  private boolean J = false;
  private int B = 0;
  private String D = null;
  private HashMap E = null;
  private boolean F = true;
  private PagedData L = null;
  
  public WireProcessor()
  {
    A = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str1 = A.otherProperties.getProperty("bpw.wire.international.window.duration", "180");
    try
    {
      C = Integer.parseInt(str1);
    }
    catch (Throwable localThrowable1)
    {
      C = 180;
    }
    str1 = A.otherProperties.getProperty("bpw.wire.domestic.window.duration", "180");
    try
    {
      H = Integer.parseInt(str1);
    }
    catch (Throwable localThrowable2)
    {
      H = 180;
    }
    String str2 = A.otherProperties.getProperty("bpw.wire.approval.immediate", "true");
    this.I = (str2.equalsIgnoreCase("true"));
    G = A.otherProperties.getProperty("bpw.wire.international.window.startTime", "13:00");
    K = A.otherProperties.getProperty("bpw.wire.domestic.window.startTime", "13:00");
    this.J = Boolean.valueOf(A.otherProperties.getProperty("bpw.wire.funds.approval.support", "false")).booleanValue();
    String str3 = null;
    try
    {
      str3 = A.otherProperties.getProperty("bpw.wire.audit");
      if (str3 == null) {
        this.B = 0;
      } else {
        this.B = Integer.parseInt(str3);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("WireProcessor. Invalid Audit log level value", str3, 0);
      this.B = 0;
    }
    FFSDebug.log("WireProcessor. Audit log level value", str3, 6);
    this.D = A.otherProperties.getProperty("bpw.wire.validate.wire.destination", "Y");
    FFSDebug.log("#######WireProcessor. validateWireDest=", this.D, 6);
    try
    {
      String str4 = null;
      getWiresConfiguration();
      str4 = (String)this.E.get("bpw.wires.config.supportrelease");
      this.F = (str4.equalsIgnoreCase("TRUE"));
    }
    catch (Exception localException2)
    {
      this.F = true;
    }
    FFSDebug.log("#######WireProcessor. wireSupportRelease=" + this.F, 6);
    this.L = new PagedWire();
  }
  
  public PagingInfo getPagedWire(PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.getPagedWire ";
    FFSDebug.log(str1 + "start...", 6);
    try
    {
      this.L.getPagedData(paramPagingInfo);
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** " + str1 + " failed: ";
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** " + str1 + "failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    return paramPagingInfo;
  }
  
  public static boolean processingWindowIsOpen(String paramString)
    throws FFSException
  {
    String str1 = "WireProcessor.processingWindowIsOpen: ";
    FFSDebug.log(str1, "start wireDest: ", paramString, 6);
    int i = 0;
    String str2 = null;
    int j = -1;
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      FFSDebug.log(str1, "failed. wireDest not specified", 0);
      throw new FFSException(str1 + "failed. wireDest not specified");
    }
    if (paramString.equalsIgnoreCase("INTERNATIONAL")) {
      i = 0;
    } else {
      i = 1;
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("H:mm");
    Calendar localCalendar1 = null;
    Calendar localCalendar2 = null;
    if (A == null) {
      A = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    }
    String str3;
    if (i != 0)
    {
      if ((K == null) || (K.length() == 0)) {
        K = A.otherProperties.getProperty("bpw.wire.domestic.window.startTime", "13:00");
      }
      str2 = K;
      if (H == -1)
      {
        str3 = A.otherProperties.getProperty("bpw.wire.domestic.window.duration", "180");
        try
        {
          H = Integer.parseInt(str3);
        }
        catch (Throwable localThrowable2)
        {
          H = 180;
        }
      }
      j = H;
    }
    else
    {
      if ((G == null) || (G.length() == 0)) {
        G = A.otherProperties.getProperty("bpw.wire.international.window.startTime", "13:00");
      }
      str2 = G;
      if (C == -1)
      {
        str3 = A.otherProperties.getProperty("bpw.wire.international.window.duration", "180");
        try
        {
          C = Integer.parseInt(str3);
        }
        catch (Throwable localThrowable3)
        {
          C = 180;
        }
      }
      j = C;
    }
    FFSDebug.log(str1, ": windowStartTime: " + str2, 6);
    FFSDebug.log("WireProcessor:wireWindowDuration: " + j, 6);
    int n;
    int i3;
    try
    {
      if (!isValidTime(str2))
      {
        str3 = null;
        if (i != 0) {
          str3 = "Invalid <bpw.wire.domestic.window.startTime> server property value=" + str2;
        } else {
          str3 = "Invalid <bpw.wire.international.window.startTime> server property value=" + str2;
        }
        FFSDebug.log(str1, str3, 6);
        throw new FFSException(str3);
      }
      localSimpleDateFormat.parse(str2);
      localCalendar1 = localSimpleDateFormat.getCalendar();
      localCalendar2 = Calendar.getInstance();
      int k = localCalendar2.get(11);
      int m = localCalendar2.get(12);
      int i1 = localCalendar1.get(11);
      int i2 = localCalendar1.get(12);
      n = k * 60 + m;
      i3 = i1 * 60 + i2;
      FFSDebug.log(str1, " curHr: " + k, 6);
      FFSDebug.log(str1, " instHr: " + i1, 6);
    }
    catch (Throwable localThrowable1)
    {
      String str4 = "WireProcessor.processingWindowIsOpen Failed:  Error: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str4, 0);
      throw new FFSException(str4);
    }
    int i4 = i3 + j;
    FFSDebug.log(str1, " startMins: " + i3, 6);
    FFSDebug.log(str1, " curMins: " + n, 6);
    FFSDebug.log(str1, " endMins: " + i4, 6);
    boolean bool;
    if ((i3 <= n) && (n < i4)) {
      bool = true;
    } else {
      bool = false;
    }
    FFSDebug.log("WireProcessor:processingWindowIsOpen: " + bool, 6);
    return bool;
  }
  
  private static boolean jdMethod_try(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.isProcessingWindowOpen: ";
    FFSDebug.log(str1, "start WireInfo: " + paramWireInfo, 6);
    String str2 = null;
    ProcessingWindowInfo[] arrayOfProcessingWindowInfo = null;
    ProcessingWindowList localProcessingWindowList = null;
    boolean bool = false;
    SimpleDateFormat localSimpleDateFormat = null;
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    String[] arrayOfString3 = null;
    String[] arrayOfString4 = null;
    localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    Date localDate = new Date();
    str2 = localSimpleDateFormat.format(localDate);
    localProcessingWindowList = new ProcessingWindowList();
    arrayOfString1 = new String[1];
    arrayOfString1[0] = paramWireInfo.getFiID();
    localProcessingWindowList.setFIId(arrayOfString1);
    arrayOfString2 = new String[1];
    arrayOfString2[0] = paramWireInfo.getCustomerID();
    localProcessingWindowList.setCustomerId(arrayOfString2);
    arrayOfString3 = new String[1];
    arrayOfString3[0] = "WIRES";
    localProcessingWindowList.setPmtType(arrayOfString3);
    arrayOfString4 = new String[1];
    arrayOfString4[0] = paramWireInfo.getWireDest();
    localProcessingWindowList.setPmtSubType(arrayOfString4);
    localProcessingWindowList = DBPropertyConfig.getProcessingWindows(paramFFSConnectionHolder, localProcessingWindowList);
    int i;
    if (localProcessingWindowList.getStatusCode() == 0)
    {
      arrayOfProcessingWindowInfo = localProcessingWindowList.getProcessingWindows();
      for (i = 0; i < arrayOfProcessingWindowInfo.length; i++)
      {
        if (a(arrayOfProcessingWindowInfo[i].getStartTime(), arrayOfProcessingWindowInfo[i].getCloseTime(), str2))
        {
          bool = true;
          break;
        }
        bool = false;
      }
    }
    if (localProcessingWindowList.getStatusCode() == 16020)
    {
      localProcessingWindowList.setCustomerId(null);
      localProcessingWindowList = DBPropertyConfig.getProcessingWindows(paramFFSConnectionHolder, localProcessingWindowList);
      if (localProcessingWindowList.getStatusCode() == 0)
      {
        arrayOfProcessingWindowInfo = localProcessingWindowList.getProcessingWindows();
        for (i = 0; i < arrayOfProcessingWindowInfo.length; i++)
        {
          if (a(arrayOfProcessingWindowInfo[i].getStartTime(), arrayOfProcessingWindowInfo[i].getCloseTime(), str2))
          {
            bool = true;
            break;
          }
          bool = false;
        }
      }
      if (localProcessingWindowList.getStatusCode() == 16020) {
        bool = true;
      } else {
        bool = false;
      }
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  private static int jdMethod_do(WireInfo paramWireInfo)
    throws BPWException
  {
    String str1 = "WireProcessor.isTodayBusinessDay: ";
    FFSDebug.log(str1, "start", 6);
    String str2 = "" + DBUtil.getCurrentStartDate() / 100;
    String str3 = null;
    try
    {
      str3 = DBUtil.getPayday(paramWireInfo.getFiID(), str2);
      FFSDebug.log(str1, " smartDate=", str3, 6);
    }
    catch (Throwable localThrowable)
    {
      String str4 = "Exception in " + str1;
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4 + str5, 0);
      paramWireInfo.setStatusCode(19200);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19200, null, "WIRE_MESSAGE"));
      return -1;
    }
    if (!str2.equals(str3)) {
      return 0;
    }
    FFSDebug.log(str1, "done", 6);
    return 1;
  }
  
  private boolean jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.isWireExpired: ";
    String str2 = null;
    Date localDate1 = null;
    Date localDate2 = new Date();
    boolean bool = false;
    long l1;
    long l2;
    try
    {
      if ((paramWireInfo instanceof RecWireInfo))
      {
        FFSDebug.log(str1 + "checking recurring wire ...", 6);
        RecWireInfo localRecWireInfo = (RecWireInfo)paramWireInfo;
        str2 = localRecWireInfo.getStartDate();
      }
      else
      {
        str2 = paramWireInfo.getDateToPost();
      }
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
      localDate1 = localSimpleDateFormat.parse(str2 + "000000", new ParsePosition(0));
      FFSDebug.log(str1 + "DateToPost = " + localDate1.toString(), 6);
      FFSDebug.log(str1 + "CurrentDate = " + localDate2.toString(), 6);
      l1 = ScheduleInfo.convertInstanceDateToNum(localDate1);
      l2 = ScheduleInfo.convertInstanceDateToNum(localDate2);
    }
    catch (Throwable localThrowable)
    {
      String str3 = FFSDebug.stackTrace(localThrowable);
      throw new FFSException(localThrowable, str3);
    }
    if (l1 < l2)
    {
      FFSDebug.log(str1 + "wire's process date is in the past.", 0);
      bool = true;
    }
    else if ((l1 == l2) && (!jdMethod_if(paramFFSConnectionHolder, paramWireInfo)))
    {
      FFSDebug.log(str1 + "wire processing windows are closed.", 0);
      bool = true;
    }
    FFSDebug.log(str1 + "isWireExpired = " + bool, 6);
    return bool;
  }
  
  private boolean a(FFSConnectionHolder paramFFSConnectionHolder, WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    WireInfo[] arrayOfWireInfo = paramWireBatchInfo.getWires();
    WireInfo localWireInfo = (WireInfo)arrayOfWireInfo[0].clone();
    localWireInfo.setDateToPost(paramWireBatchInfo.getDateToPost());
    localWireInfo.setDateDue(paramWireBatchInfo.getDateDue());
    localWireInfo.setFiID(paramWireBatchInfo.getFIId());
    localWireInfo.setCustomerID(paramWireBatchInfo.getCustomerId());
    boolean bool = jdMethod_int(paramFFSConnectionHolder, localWireInfo);
    localWireInfo = null;
    FFSDebug.log("WireProcessor:isBatchExpired = " + bool, 6);
    return bool;
  }
  
  private static boolean jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.willProcessingWindowOpen: ";
    FFSDebug.log(str1, "starts ...", 6);
    FFSDebug.log(str1, "WireInfo.Destination: " + paramWireInfo.getWireDest(), 6);
    ProcessingWindowInfo[] arrayOfProcessingWindowInfo = null;
    boolean bool = false;
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    String str2 = localSimpleDateFormat.format(new Date());
    ProcessingWindowList localProcessingWindowList = new ProcessingWindowList();
    String[] arrayOfString1 = { paramWireInfo.getFiID() };
    localProcessingWindowList.setFIId(arrayOfString1);
    String[] arrayOfString2 = { paramWireInfo.getCustomerID() };
    localProcessingWindowList.setCustomerId(arrayOfString2);
    String[] arrayOfString3 = { "WIRES" };
    localProcessingWindowList.setPmtType(arrayOfString3);
    String[] arrayOfString4 = { paramWireInfo.getWireDest() };
    localProcessingWindowList.setPmtSubType(arrayOfString4);
    localProcessingWindowList = DBPropertyConfig.getProcessingWindows(paramFFSConnectionHolder, localProcessingWindowList);
    if (localProcessingWindowList.getStatusCode() == 0)
    {
      arrayOfProcessingWindowInfo = localProcessingWindowList.getProcessingWindows();
    }
    else if (localProcessingWindowList.getStatusCode() == 16020)
    {
      localProcessingWindowList.setCustomerId(null);
      localProcessingWindowList = DBPropertyConfig.getProcessingWindows(paramFFSConnectionHolder, localProcessingWindowList);
      if (localProcessingWindowList.getStatusCode() == 0) {
        arrayOfProcessingWindowInfo = localProcessingWindowList.getProcessingWindows();
      } else if (localProcessingWindowList.getStatusCode() == 16020) {
        bool = true;
      } else {
        bool = false;
      }
    }
    else
    {
      bool = false;
    }
    if (arrayOfProcessingWindowInfo != null) {
      for (int i = 0; i < arrayOfProcessingWindowInfo.length; i++) {
        if (str2.compareTo(arrayOfProcessingWindowInfo[i].getCloseTime()) < 0)
        {
          bool = true;
          break;
        }
      }
    }
    localSimpleDateFormat = null;
    localProcessingWindowList = null;
    FFSDebug.log(str1, "willOpen: " + bool, 6);
    FFSDebug.log(str1, "done.", 6);
    return bool;
  }
  
  private static boolean a(String paramString1, String paramString2, String paramString3)
  {
    String str = "WireProcessor.isInBetween: ";
    FFSDebug.log(str, "start startTime:", paramString1, "closeTime:", paramString2, "curTime:", paramString3, 6);
    SimpleDateFormat localSimpleDateFormat = null;
    Date localDate1 = null;
    Date localDate2 = null;
    Date localDate3 = null;
    if ((paramString1 == null) || (paramString1.trim().length() == 0) || (paramString1.trim().length() != 8)) {
      return false;
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0) || (paramString2.trim().length() != 8)) {
      return false;
    }
    if ((paramString3 == null) || (paramString3.trim().length() == 0) || (paramString3.trim().length() != 8)) {
      return false;
    }
    localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    ParsePosition localParsePosition = new ParsePosition(0);
    localDate1 = localSimpleDateFormat.parse(paramString1, localParsePosition);
    localParsePosition = new ParsePosition(0);
    localDate2 = localSimpleDateFormat.parse(paramString2, localParsePosition);
    localParsePosition = new ParsePosition(0);
    localDate3 = localSimpleDateFormat.parse(paramString3, localParsePosition);
    FFSDebug.log(str, "start:" + localDate1, "close:" + localDate2, "cur:" + localDate3, 6);
    return (localDate1.compareTo(localDate3) <= 0) && (localDate2.compareTo(localDate3) >= 0);
  }
  
  public static boolean isValidTime(String paramString)
    throws FFSException
  {
    if (paramString == null) {
      return false;
    }
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    try
    {
      n = paramString.indexOf(":");
      if (n == -1) {
        return false;
      }
      FFSDebug.log("*** WireProcessor.isValidTime: For Hr, startPos:" + m, " EndPos:" + n, 6);
      i = Integer.parseInt(paramString.substring(m, n));
      FFSDebug.log("*** WireProcessor.isValidTime: Hour:" + i, 6);
      if ((i < 0) || (i > 24))
      {
        FFSDebug.log("*** WireProcessor.isValidTime: Invalid value for Hour:" + i, 0);
        return false;
      }
      m = n + 1;
      FFSDebug.log("*** WireProcessor.isValidTime: For Min, startPos:" + m, 6);
      n = paramString.indexOf(":", m);
      FFSDebug.log("*** WireProcessor.isValidTime: For Min, EndPos:" + n, 6);
      if (n == -1) {
        j = Integer.parseInt(paramString.substring(m));
      } else {
        j = Integer.parseInt(paramString.substring(m, n));
      }
      FFSDebug.log("*** WireProcessor.isValidTime: Min:" + j, 6);
      if ((j < 0) || (j > 60))
      {
        FFSDebug.log("*** WireProcessor.isValidTime: Invalid value for Min:" + j, 0);
        return false;
      }
      if (n != -1)
      {
        m = n + 1;
        FFSDebug.log("*** WireProcessor.isValidTime: For Sec, startPos:" + m, 6);
        n = paramString.indexOf(":", m);
        FFSDebug.log("*** WireProcessor.isValidTime: For Sec, EndPos:" + n, 6);
        if (n == -1)
        {
          k = Integer.parseInt(paramString.substring(m));
        }
        else
        {
          FFSDebug.log("*** WireProcessor.isValidTime: Invalid time" + paramString, 0);
          return false;
        }
        FFSDebug.log("*** WireProcessor.isValidTime: Sec:" + k, 6);
        if ((k < 0) || (k > 60))
        {
          FFSDebug.log("*** WireProcessor.isValidTime: Invalid value for Sec:" + k, 0);
          return false;
        }
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      str1 = "*** WireProcessor.isValidTime failed: Invalid time ";
      FFSDebug.log(str1, paramString, 0);
      return false;
    }
    catch (Throwable localThrowable)
    {
      String str1 = "*** WireProcessor.isValidTime failed: ";
      String str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1 + str2, 0);
      throw new FFSException(localThrowable, str1);
    }
    return true;
  }
  
  private boolean jdMethod_if(String paramString)
    throws FFSException
  {
    Date localDate1 = null;
    Date localDate2 = new Date();
    long l1;
    long l2;
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
      localDate1 = localSimpleDateFormat.parse(paramString + "000000", new ParsePosition(0));
      l1 = ScheduleInfo.convertInstanceDateToNum(localDate1);
      l2 = ScheduleInfo.convertInstanceDateToNum(localDate2);
    }
    catch (Throwable localThrowable)
    {
      String str = FFSDebug.stackTrace(localThrowable);
      throw new FFSException(localThrowable, str);
    }
    boolean bool;
    if (l1 <= l2) {
      bool = true;
    } else {
      bool = false;
    }
    FFSDebug.log("WireProcessor: isTodayOrBefored = " + bool, 6);
    return bool;
  }
  
  public WireInfo addWireTrn(WireInfo paramWireInfo)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.addWireTrn: ", ": Start ", 6);
    String str1 = null;
    if (paramWireInfo == null)
    {
      paramWireInfo = new WireInfo();
      paramWireInfo.setStatusCode(16000);
      String str2 = BPWLocaleUtil.getMessage(16000, new String[] { "WireInfo" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str2);
      FFSDebug.log("WireProcessor.addWireTrn: ", str2, 0);
      return paramWireInfo;
    }
    try
    {
      str1 = paramWireInfo.getWireType();
      if ((str1 != null) && (str1.trim().equalsIgnoreCase("TEMPLATE"))) {
        paramWireInfo.setDateDue(null);
      }
      jdMethod_if(paramWireInfo);
      if (paramWireInfo.getStatusCode() != 0)
      {
        if (this.B >= 1) {
          a(paramWireInfo, "Add", null, false);
        }
        return paramWireInfo;
      }
      paramWireInfo = jdMethod_int(paramWireInfo, false);
    }
    catch (Throwable localThrowable)
    {
      String str3 = "*** WireProcessor.addWireTrn failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3 + str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    return paramWireInfo;
  }
  
  public RecWireInfo addRecWireTrn(RecWireInfo paramRecWireInfo)
    throws FFSException
  {
    if (paramRecWireInfo == null)
    {
      paramRecWireInfo = new RecWireInfo();
      paramRecWireInfo.setStatusCode(16000);
      String str1 = BPWLocaleUtil.getMessage(16000, new String[] { "RecWireInfo" }, "WIRE_MESSAGE");
      paramRecWireInfo.setStatusMsg(str1);
      FFSDebug.log("WireProcessor.addRecWireTrn, " + str1, 0);
      return paramRecWireInfo;
    }
    try
    {
      a(paramRecWireInfo);
      if (paramRecWireInfo.getStatusCode() != 0)
      {
        if (this.B >= 1) {
          a(paramRecWireInfo, "Add", null, true);
        }
        return paramRecWireInfo;
      }
      jdMethod_int(paramRecWireInfo, true);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** WireProcessor.addRecWireTrn failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(str2);
    }
    return paramRecWireInfo;
  }
  
  private WireInfo jdMethod_int(WireInfo paramWireInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "WireProcessor.addWireTrn(.. ,boolean):";
    FFSDebug.log(str1, " Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    if (!jdMethod_if(paramWireInfo, paramBoolean))
    {
      if (this.B >= 1) {
        a(paramWireInfo, "Add", null, paramBoolean);
      }
      return paramWireInfo;
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      Object localObject1;
      if (localFFSConnectionHolder.conn == null)
      {
        localObject1 = str1 + "WireProcessor.addWireTrn: " + "Can not get DB Connection.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      paramWireInfo = jdMethod_if(localFFSConnectionHolder, localFFSConnectionHolder, paramWireInfo, paramBoolean, true);
      if (paramWireInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject1 = paramWireInfo;
        return localObject1;
      }
      FFSDebug.log(str1, " commit change", 6);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "*** WireProcessor.addWireTrn failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, " end ", 6);
    return paramWireInfo;
  }
  
  private WireInfo jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2, WireInfo paramWireInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "WireProcessor.addWireTrn(Connection,,): ";
    FFSDebug.log(str1, " Start ", 6);
    String str2 = null;
    String str3 = null;
    String str4 = null;
    try
    {
      Object localObject;
      if ((paramWireInfo.getLogId() == null) || (paramWireInfo.getLogId().trim().length() == 0))
      {
        localObject = DBUtil.getNextIndexString("LogID");
        paramWireInfo.setLogId((String)localObject);
      }
      FFSDebug.log(str1, " logID=", paramWireInfo.getLogId(), 6);
      if (paramFFSConnectionHolder1.conn == null)
      {
        localObject = str1 + "WireProcessor.addWireTrn: " + "DB Connection for main transaction is null.";
        FFSDebug.log((String)localObject, 0);
        throw new FFSException((String)localObject);
      }
      if (paramFFSConnectionHolder2.conn == null)
      {
        localObject = str1 + "WireProcessor.addWireTrn: " + "DB Connection for activity logging is null.";
        FFSDebug.log((String)localObject, 0);
        throw new FFSException((String)localObject);
      }
      if ((paramBoolean2) && (Trans.checkDuplicateTIDAndSaveTID(paramWireInfo.getTrnId())))
      {
        paramWireInfo.setStatusCode(19220);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19220, null, "WIRE_MESSAGE"));
        paramWireInfo.setWireType("DUPLICATE");
        if (this.B >= 1) {
          a(paramFFSConnectionHolder2, paramWireInfo, "AddDup", paramBoolean1);
        }
        paramFFSConnectionHolder2.conn.commit();
        return paramWireInfo;
      }
      str3 = paramWireInfo.getWireType();
      if ((!"RECTEMPLATE".equalsIgnoreCase(str3)) && (!"TEMPLATE".equalsIgnoreCase(str3)) && (jdMethod_int(paramFFSConnectionHolder1, paramWireInfo) == true))
      {
        localObject = BPWLocaleUtil.getMessage(19851, null, "WIRE_MESSAGE");
        paramWireInfo.setStatusCode(19851);
        paramWireInfo.setStatusMsg((String)localObject);
        if (this.B >= 1) {
          a(paramWireInfo, "Add", null, paramBoolean1);
        }
        return paramWireInfo;
      }
      str2 = paramWireInfo.getWireSource();
      if ((str2 == null) || (!str2.trim().equalsIgnoreCase("HOST")))
      {
        jdMethod_for(paramFFSConnectionHolder1, paramWireInfo);
        if (paramWireInfo.getStatusCode() != 0)
        {
          if (this.B >= 1) {
            a(paramWireInfo, "Add", null, paramBoolean1);
          }
          return paramWireInfo;
        }
      }
      str4 = paramWireInfo.getWireDest();
      if ((str4 != null) && (str4.equals("DRAWDOWN")))
      {
        localObject = paramWireInfo.getWireCreditInfo();
        if (localObject == null)
        {
          paramWireInfo.setStatusCode(19560);
          paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19560, null, "WIRE_MESSAGE"));
          if (this.B >= 1) {
            a(paramWireInfo, "Add", null, paramBoolean1);
          }
          return paramWireInfo;
        }
        jdMethod_new(paramFFSConnectionHolder1, paramWireInfo);
        if (paramWireInfo.getStatusCode() != 0)
        {
          if (this.B >= 1) {
            a(paramWireInfo, "Add", null, paramBoolean1);
          }
          return paramWireInfo;
        }
      }
      paramWireInfo = Wire.addWire(paramFFSConnectionHolder1, paramWireInfo, paramBoolean1);
      if (paramWireInfo.getStatusCode() != 0)
      {
        if (this.B >= 1) {
          a(paramWireInfo, "Add", null, paramBoolean1);
        }
        return paramWireInfo;
      }
      if (("RECTEMPLATE".equalsIgnoreCase(str3)) || ("TEMPLATE".equalsIgnoreCase(str3)))
      {
        int i = LimitCheckApprovalProcessor.processWireAdd(paramFFSConnectionHolder1, paramWireInfo, null);
        str6 = LimitCheckApprovalProcessor.mapToStatus(i);
        FFSDebug.log(str1 + ": template.retStatus LimitCheck:" + str6, 6);
        if (("LIMIT_CHECK_FAILED".equals(str6)) || ("LIMIT_REVERT_FAILED".equals(str6)) || ("APPROVAL_FAILED".equals(str6)) || ("APPROVAL_NOT_ALLOWED".equals(str6)))
        {
          if (this.B >= 1) {
            a(paramWireInfo, "Add", null, paramBoolean1);
          }
          return paramWireInfo;
        }
        if ("APPROVAL_PENDING".equals(str6))
        {
          paramWireInfo.setPrcStatus("APPROVAL_PENDING");
          Wire.updateStatus(paramFFSConnectionHolder1, paramWireInfo, "APPROVAL_PENDING", paramBoolean1);
        }
      }
      if ((!paramBoolean1) && (!"TEMPLATE".equalsIgnoreCase(str3)))
      {
        limitCheckWireAdd(paramFFSConnectionHolder1, paramWireInfo, null);
        String str5 = paramWireInfo.getPrcStatus();
        if (("LIMIT_CHECK_FAILED".equals(str5)) || ("LIMIT_REVERT_FAILED".equals(str5)) || ("APPROVAL_FAILED".equals(str5)))
        {
          if (this.B >= 1) {
            a(paramWireInfo, "Add", null, paramBoolean1);
          }
          return paramWireInfo;
        }
        if (("APPROVAL_NOT_ALLOWED".equals(str5)) && ((paramWireInfo.getBatchId() == null) || (paramWireInfo.getBatchId().length() == 0)))
        {
          if (this.B >= 1) {
            a(paramWireInfo, "Add", null, paramBoolean1);
          }
          return paramWireInfo;
        }
        Wire.updateStatus(paramFFSConnectionHolder1, paramWireInfo);
      }
      if (((paramWireInfo.getBatchId() == null) || (paramWireInfo.getBatchId().trim().length() == 0)) && (!this.F) && (paramWireInfo.getPrcStatus().equals("CREATED")) && (!paramBoolean1) && (!"TEMPLATE".equalsIgnoreCase(paramWireInfo.getWireType())))
      {
        FFSDebug.log(" WireProcessor :processWireRelease   processing non-recurring auto release:", 6);
        paramWireInfo = processSingleWireInAutoReleaseMode(paramFFSConnectionHolder1, paramWireInfo);
        if (paramWireInfo.getStatusCode() != 0) {
          return paramWireInfo;
        }
      }
      if ((paramBoolean1) && (!"RECTEMPLATE".equalsIgnoreCase(paramWireInfo.getWireType())))
      {
        FFSDebug.log(" WireProcessor :addWireTrn  processing recurring wire trn:", 6);
        a(paramFFSConnectionHolder1, paramWireInfo, true);
        if (paramWireInfo.getStatusCode() != 0)
        {
          if (this.B >= 1) {
            a(paramWireInfo, "Add", null, paramBoolean1);
          }
          paramFFSConnectionHolder1.conn.rollback();
          return paramWireInfo;
        }
      }
      FFSDebug.log(str1, "audit_Level: " + this.B, 6);
      if (((paramBoolean1) || (paramWireInfo.getPrcStatus().equals("CREATED")) || ("TEMPLATE".equalsIgnoreCase(paramWireInfo.getWireType()))) && (this.B >= 3))
      {
        FFSDebug.log(str1, "audit logging3", 6);
        a(paramFFSConnectionHolder1, paramWireInfo, "Add", paramBoolean1);
      }
      paramWireInfo.setStatusCode(0);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    }
    catch (Throwable localThrowable)
    {
      if (this.B >= 1) {
        a(paramWireInfo, "Add", null, paramBoolean1);
      }
      String str6 = "*** WireProcessor.addWireTrn failed: ";
      String str7 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str6 + str7, 0);
      throw new FFSException(localThrowable, str6);
    }
    FFSDebug.log(str1, " end ", 6);
    return paramWireInfo;
  }
  
  public WireInfo[] addWireTransfers(WireInfo[] paramArrayOfWireInfo)
    throws FFSException
  {
    String str = "WireProcessor.addWireTransfers:";
    FFSDebug.log(str, " Start ", 6);
    a(paramArrayOfWireInfo, false);
    FFSDebug.log(str, " Done", 6);
    return paramArrayOfWireInfo;
  }
  
  public RecWireInfo[] addRecWireTransfers(RecWireInfo[] paramArrayOfRecWireInfo)
    throws FFSException
  {
    String str = "WireProcessor.addRecWireTransfers:";
    FFSDebug.log(str, " Start ", 6);
    a(paramArrayOfRecWireInfo, true);
    FFSDebug.log(str, " Done", 6);
    return paramArrayOfRecWireInfo;
  }
  
  private WireInfo[] a(WireInfo[] paramArrayOfWireInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "WireProcessor.addWireTransfers (boolean):";
    FFSDebug.log(str1, " Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder1 = null;
    FFSConnectionHolder localFFSConnectionHolder2 = null;
    String str2 = null;
    String str3 = null;
    int i = 0;
    if ((paramArrayOfWireInfo == null) || (paramArrayOfWireInfo.length == 0))
    {
      FFSDebug.log(str1, "failed: Empty wire list is passed", 0);
      return null;
    }
    try
    {
      FFSConnection[] arrayOfFFSConnection = DBUtil.getConnections(2);
      if ((arrayOfFFSConnection == null) || (arrayOfFFSConnection.length < 2))
      {
        String str4 = str1 + "Can not get DB Connections.";
        FFSDebug.log(str4, 0);
        throw new FFSException(str4);
      }
      localFFSConnectionHolder1 = new FFSConnectionHolder();
      localFFSConnectionHolder1.conn = arrayOfFFSConnection[0];
      localFFSConnectionHolder2 = new FFSConnectionHolder();
      localFFSConnectionHolder2.conn = arrayOfFFSConnection[1];
      str2 = DBUtil.getNextIndexString("LogID");
      i = paramArrayOfWireInfo.length;
      for (int j = 0; j < i; j++) {
        if (paramArrayOfWireInfo[j] == null)
        {
          if (paramBoolean) {
            paramArrayOfWireInfo[j] = new RecWireInfo();
          } else {
            paramArrayOfWireInfo[j] = new WireInfo();
          }
          paramArrayOfWireInfo[j].setStatusCode(16000);
          localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "Wire #", "" + (j + 1) }, "WIRE_MESSAGE");
          paramArrayOfWireInfo[j].setStatusMsg((String)localObject1);
          FFSDebug.log(str1 + (String)localObject1, 0);
        }
        else
        {
          if (paramBoolean)
          {
            a((RecWireInfo)paramArrayOfWireInfo[j]);
          }
          else
          {
            str3 = paramArrayOfWireInfo[j].getWireType();
            if ((str3 != null) && (str3.trim().equalsIgnoreCase("TEMPLATE"))) {
              paramArrayOfWireInfo[j].setDateDue(null);
            }
            jdMethod_if(paramArrayOfWireInfo[j]);
          }
          WireInfo[] arrayOfWireInfo;
          if (paramArrayOfWireInfo[j].getStatusCode() != 0)
          {
            localFFSConnectionHolder1.conn.rollback();
            localFFSConnectionHolder2.conn.rollback();
            if (this.B >= 1) {
              a(paramArrayOfWireInfo[j], "Add", null, paramBoolean);
            }
            localObject1 = "Error occured at wire#:" + (j + 1) + paramArrayOfWireInfo[j].getStatusMsg();
            FFSDebug.log((String)localObject1, 0);
            arrayOfWireInfo = paramArrayOfWireInfo;
            return arrayOfWireInfo;
          }
          if (!jdMethod_if(paramArrayOfWireInfo[j], paramBoolean))
          {
            localFFSConnectionHolder1.conn.rollback();
            localFFSConnectionHolder2.conn.rollback();
            if (this.B >= 1) {
              a(paramArrayOfWireInfo[j], "Add", null, paramBoolean);
            }
            localObject1 = "Error occured at wire#:" + (j + 1) + paramArrayOfWireInfo[j].getStatusMsg();
            FFSDebug.log((String)localObject1, 0);
            arrayOfWireInfo = paramArrayOfWireInfo;
            return arrayOfWireInfo;
          }
          paramArrayOfWireInfo[j].setLogId(str2);
          paramArrayOfWireInfo[j] = jdMethod_if(localFFSConnectionHolder1, localFFSConnectionHolder2, paramArrayOfWireInfo[j], paramBoolean, true);
          if (paramArrayOfWireInfo[j].getStatusCode() != 0)
          {
            localFFSConnectionHolder1.conn.rollback();
            localFFSConnectionHolder2.conn.rollback();
            if (this.B >= 1) {
              a(paramArrayOfWireInfo[j], "Add", null, paramBoolean);
            }
            localObject1 = paramArrayOfWireInfo;
            return localObject1;
          }
        }
      }
      localFFSConnectionHolder1.conn.commit();
      localFFSConnectionHolder2.conn.commit();
      FFSDebug.log(str1, " Done", 6);
    }
    catch (Throwable localThrowable1)
    {
      localFFSConnectionHolder1.conn.rollback();
      localFFSConnectionHolder2.conn.commit();
      String str5 = " failed: ";
      Object localObject1 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("*** ", str1, str5, (String)localObject1, 0);
      throw new FFSException(localThrowable1, str5);
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder1.conn);
      }
      catch (Throwable localThrowable2)
      {
        FFSDebug.log(str1, " Failed to free the first connection ", 0);
      }
      DBUtil.freeConnection(localFFSConnectionHolder2.conn);
    }
    return paramArrayOfWireInfo;
  }
  
  public WireInfo[] releaseWireTransfer(WireInfo[] paramArrayOfWireInfo)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.releaseWireTransfer: ", " Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    String str1;
    if (paramArrayOfWireInfo == null)
    {
      paramArrayOfWireInfo = new WireInfo[1];
      paramArrayOfWireInfo[0] = new WireInfo();
      paramArrayOfWireInfo[0].setStatusCode(16000);
      str1 = BPWLocaleUtil.getMessage(16000, new String[] { "WireInfo" }, "WIRE_MESSAGE");
      paramArrayOfWireInfo[0].setStatusMsg(str1);
      FFSDebug.log("WireProcessor.releaseWireTransfer: ", str1, 0);
      return paramArrayOfWireInfo;
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        str1 = "WireProcessor.releaseWireTransfer: Can not get DB Connection.";
        FFSDebug.log(str1, 0);
        throw new FFSException(str1);
      }
      paramArrayOfWireInfo = jdMethod_if(localFFSConnectionHolder, paramArrayOfWireInfo);
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "*** WireProcessor.releaseWireTransfer failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("WireProcessor.releaseWireTransfer: ", " end ", 6);
    return paramArrayOfWireInfo;
  }
  
  private WireInfo[] jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, WireInfo[] paramArrayOfWireInfo)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.releaseWireTransfer: ", " Start ", 6);
    ArrayList localArrayList = null;
    if (paramArrayOfWireInfo == null)
    {
      paramArrayOfWireInfo = new WireInfo[1];
      paramArrayOfWireInfo[0] = new WireInfo();
      paramArrayOfWireInfo[0].setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "WireInfo" }, "WIRE_MESSAGE");
      paramArrayOfWireInfo[0].setStatusMsg(str);
      FFSDebug.log("WireProcessor.releaseWireTransfer: ", str, 0);
      return paramArrayOfWireInfo;
    }
    try
    {
      localArrayList = new ArrayList();
      for (int i = 0; i < paramArrayOfWireInfo.length; i++)
      {
        paramArrayOfWireInfo[i] = a(paramFFSConnectionHolder, paramArrayOfWireInfo[i], localArrayList);
        if (paramArrayOfWireInfo[i].getStatusCode() != 0) {
          FFSDebug.log("WireProcessor.releaseWireTransfer: ", " Error at wire #" + (i + 1), ": ", paramArrayOfWireInfo[i].getStatusMsg(), 0);
        } else {
          FFSDebug.log("WireProcessor.releaseWireTransfer: ", " complete releaseOneWireTransfer for all batch", 6);
        }
      }
      paramFFSConnectionHolder.conn.commit();
      WireInfo[] arrayOfWireInfo = (WireInfo[])localArrayList.toArray(new WireInfo[0]);
      if (arrayOfWireInfo.length > 0)
      {
        if (A == null) {
          A = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
        }
        FFSDebug.log("WireProcessor.releaseWireTransfer: ", "supportFundsApprov:" + this.J, 6);
        if (this.J)
        {
          FFSDebug.log("WireProcessor.releaseWireTransfer: ", ": processing immediate funds approval", 6);
          localObject1 = jdMethod_do(paramFFSConnectionHolder, arrayOfWireInfo);
          paramFFSConnectionHolder.conn.commit();
          FFSDebug.log("WireProcessor.releaseWireTransfer: ", ": processing immediate funds approval completed.", 6);
          if ((localObject1 != null) && (((ArrayList)localObject1).size() > 0))
          {
            localObject2 = (WireInfo[])((ArrayList)localObject1).toArray(new WireInfo[0]);
            FFSDebug.log("WireProcessor.releaseWireTransfer: ", ": processing immediate wire to backend.", 6);
            a(paramFFSConnectionHolder, (WireInfo[])localObject2);
            paramFFSConnectionHolder.conn.commit();
            FFSDebug.log("WireProcessor.releaseWireTransfer: ", ": processing immediate wires to backend completed.", 6);
          }
        }
        else
        {
          FFSDebug.log("WireProcessor.releaseWireTransfer: ", ": processing immediate wire to backend.", 6);
          a(paramFFSConnectionHolder, arrayOfWireInfo);
          FFSDebug.log("WireProcessor.releaseWireTransfer: ", ": processing immediate wires to backend completed.", 6);
        }
        paramFFSConnectionHolder.conn.commit();
      }
    }
    catch (Throwable localThrowable)
    {
      paramFFSConnectionHolder.conn.rollback();
      Object localObject1 = "*** WireProcessor.releaseWireTransfer failed: ";
      Object localObject2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1 + (String)localObject2, 0);
      throw new FFSException(localThrowable, (String)localObject1);
    }
    FFSDebug.log("WireProcessor.releaseWireTransfer: ", " end ", 6);
    return paramArrayOfWireInfo;
  }
  
  private WireInfo a(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "WireProcessor.releaseOneWireTransfer(Connection,..):";
    FFSDebug.log(str1, " Start ", 6);
    String str2 = null;
    String str3 = null;
    int i = 0;
    int j = -1;
    String str4;
    if (paramWireInfo == null)
    {
      paramWireInfo = new WireInfo();
      str4 = BPWLocaleUtil.getMessage(16000, new String[] { "WireInfo" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusCode(16000);
      paramWireInfo.setStatusMsg(str4);
      FFSDebug.log(str1 + " " + str4, 0);
      return paramWireInfo;
    }
    try
    {
      if (paramWireInfo.getSrvrTid() == null)
      {
        paramWireInfo.setStatusCode(16000);
        str4 = BPWLocaleUtil.getMessage(16000, new String[] { "SrvrTId" }, "WIRE_MESSAGE");
        paramWireInfo.setStatusMsg(str4);
        FFSDebug.log(str1 + " " + str4, 0);
        if (this.B >= 1) {
          a(paramWireInfo, "Rel", null, false);
        }
        return paramWireInfo;
      }
      if (paramFFSConnectionHolder.conn == null)
      {
        str4 = str1 + "WireProcessor.releaseWireTransfer: " + "DB Connection is null.";
        FFSDebug.log(str4, 0);
        throw new FFSException(str4);
      }
      if ((paramWireInfo.getLogId() == null) || (paramWireInfo.getLogId().trim().length() == 0))
      {
        str3 = DBUtil.getNextIndexString("LogID");
        paramWireInfo.setLogId(str3);
      }
      FFSDebug.log(str1, " logID=", paramWireInfo.getLogId(), 6);
      if (!a(paramFFSConnectionHolder, paramWireInfo))
      {
        FFSDebug.log(str1, paramWireInfo.getStatusMsg(), 0);
        if (this.B >= 1) {
          a(paramWireInfo, "Rel", null, false);
        }
        return paramWireInfo;
      }
      if (!a(paramWireInfo.getPrcStatus()))
      {
        str4 = BPWLocaleUtil.getMessage(19500, new String[] { "Status:", paramWireInfo.getPrcStatus() }, "WIRE_MESSAGE");
        FFSDebug.log(str1, str4, 0);
        paramWireInfo.setStatusCode(19500);
        paramWireInfo.setStatusMsg(str4);
        if (this.B >= 1) {
          a(paramWireInfo, "Rel", null, false);
        }
        return paramWireInfo;
      }
      str2 = paramWireInfo.getPrcStatus();
      if ("RELEASED".equals(str2)) {
        i = 1;
      }
      str4 = paramWireInfo.getProcessedBy();
      str5 = paramWireInfo.getSubmittedBy();
      str6 = paramWireInfo.getUserId();
      String str7 = paramWireInfo.getMemo();
      Hashtable localHashtable = paramWireInfo.getExtInfo();
      paramWireInfo = Wire.getWireInfo(paramFFSConnectionHolder, paramWireInfo, false);
      paramWireInfo.setPrcStatus(str2);
      paramWireInfo.setProcessedBy(str4);
      paramWireInfo.setSubmittedBy(str5);
      paramWireInfo.setUserId(str6);
      paramWireInfo.setMemo(str7);
      paramWireInfo.setExtInfo(localHashtable);
      if (i == 1)
      {
        if (this.B >= 3) {
          a(paramFFSConnectionHolder, paramWireInfo, "Rel", false);
        }
        boolean bool1 = jdMethod_if(paramWireInfo.getDateToPost());
        boolean bool2 = jdMethod_try(paramFFSConnectionHolder, paramWireInfo);
        boolean bool3 = false;
        int k = jdMethod_do(paramWireInfo);
        if ((k == -1) && (paramWireInfo.getStatusCode() != 0))
        {
          FFSDebug.log(str1, "unable to process released wire due to failure in calculating if today is a business day", 0);
          if (this.B >= 1) {
            a(paramWireInfo, "Rel", null, false);
          }
          return paramWireInfo;
        }
        bool3 = k == 1;
        FFSDebug.log(str1, "processingWindowIsOpen: " + bool2, 6);
        FFSDebug.log(str1, "immdWireApproval: " + this.I, 6);
        FFSDebug.log(str1, "isTodayBusinessDay: " + bool3, 6);
        if ((this.I) && (bool1) && (bool2) && (bool3))
        {
          if (A == null) {
            A = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
          }
          FFSDebug.log(str1, "supportFundsApprov:" + this.J, 6);
          Object localObject;
          if (this.J)
          {
            localObject = Customer.getCustomerInfo(paramWireInfo.getCustomerID(), paramFFSConnectionHolder, paramWireInfo);
            paramWireInfo.setCustomerInfo((CustomerInfo)localObject);
            FFSDebug.log(str1, "immdFundsApproval:", "customerInfo:" + paramWireInfo.getCustomerInfo(), 6);
            BPWFIInfo localBPWFIInfo1 = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, paramWireInfo.getFiID());
            paramWireInfo.setFiInfo(localBPWFIInfo1);
            FFSDebug.log(str1, "immdFundsApproval:", "bpwFiInfo:" + paramWireInfo.getFiInfo(), 6);
            paramWireInfo.setPrcStatus("IMMED_INPROCESS");
            paramArrayList.add(paramWireInfo);
          }
          else
          {
            localObject = A.otherProperties.getProperty("bpw.wire.backend.immediate", "true");
            boolean bool4 = ((String)localObject).equalsIgnoreCase("true");
            FFSDebug.log(str1, "immdWireBanckend:" + bool4, 6);
            if (bool4)
            {
              CustomerInfo localCustomerInfo = Customer.getCustomerByID(paramWireInfo.getCustomerID(), paramFFSConnectionHolder);
              paramWireInfo.setCustomerInfo(localCustomerInfo);
              FFSDebug.log(str1, "immdWireBackend:", "customerInfo:" + paramWireInfo.getCustomerInfo(), 6);
              BPWFIInfo localBPWFIInfo2 = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, paramWireInfo.getFiID());
              paramWireInfo.setFiInfo(localBPWFIInfo2);
              FFSDebug.log(str1, "immdWireBackend:", "bpwFiInfo:" + paramWireInfo.getFiInfo(), 6);
              paramWireInfo.setPrcStatus("IMMED_INPROCESS");
              paramArrayList.add(paramWireInfo);
            }
            else
            {
              paramWireInfo.setPrcStatus("WILLPROCESSON");
              jdMethod_do(paramFFSConnectionHolder, paramWireInfo);
            }
          }
          if (paramWireInfo.getStatusCode() != 0)
          {
            if (this.B >= 1) {
              a(paramWireInfo, "Rel", null, false);
            }
            return paramWireInfo;
          }
        }
        else
        {
          paramWireInfo.setPrcStatus("WILLPROCESSON");
          a(paramFFSConnectionHolder, paramWireInfo, false);
          if (paramWireInfo.getStatusCode() != 0)
          {
            if (this.B >= 1) {
              a(paramWireInfo, "Rel", null, false);
            }
            return paramWireInfo;
          }
        }
      }
      paramWireInfo.setLogId(str3);
      if (this.B >= 3) {
        a(paramFFSConnectionHolder, paramWireInfo, "Rel", false);
      }
      paramWireInfo.setStatusCode(0);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      paramWireInfo = Wire.updateReleaseWire(paramFFSConnectionHolder, paramWireInfo);
      if (paramWireInfo.getStatusCode() != 0)
      {
        if (j != -1) {
          paramArrayList.remove(j);
        }
        return paramWireInfo;
      }
    }
    catch (Throwable localThrowable)
    {
      if (this.B >= 1) {
        a(paramWireInfo, "Rel", "Release wire failed, some unknown error occurred", false);
      }
      String str5 = "*** " + str1 + " failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5 + str6, 0);
      throw new FFSException(localThrowable, str5);
    }
    FFSDebug.log(str1, " Done", 6);
    return paramWireInfo;
  }
  
  public WireInfo cancWireTrn(WireInfo paramWireInfo)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.cancWireTrn: ", " starts ", 6);
    if (paramWireInfo == null)
    {
      paramWireInfo = new RecWireInfo();
      paramWireInfo.setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "WireInfo" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str);
      FFSDebug.log("WireProcessor.cancWireTrn: ", str, 0);
      return paramWireInfo;
    }
    jdMethod_new(paramWireInfo, false);
    FFSDebug.log("WireProcessor.cancWireTrn: ", " end ", 6);
    return paramWireInfo;
  }
  
  public RecWireInfo cancRecWireTrn(RecWireInfo paramRecWireInfo)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.cancRecWireTrn: ", " starts ", 6);
    if (paramRecWireInfo == null)
    {
      paramRecWireInfo = new RecWireInfo();
      paramRecWireInfo.setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "RecWireInfo" }, "WIRE_MESSAGE");
      paramRecWireInfo.setStatusMsg(str);
      FFSDebug.log("WireProcessor.cancRecWireTrn, ", str, 0);
      return paramRecWireInfo;
    }
    jdMethod_new(paramRecWireInfo, true);
    FFSDebug.log("WireProcessor.cancRecWireTrn: ", " end ", 6);
    return paramRecWireInfo;
  }
  
  private WireInfo jdMethod_new(WireInfo paramWireInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "WireProcessor.cancWireTrn: ";
    FFSDebug.log(str1, "Start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException(str1 + "Can not get DB Connection.");
      }
      paramWireInfo = jdMethod_do(localFFSConnectionHolder, localFFSConnectionHolder, paramWireInfo, paramBoolean, true);
      FFSDebug.log(str1, paramWireInfo.getStatusMsg(), 6);
      if (paramWireInfo.getStatusCode() != 0)
      {
        String str2 = "*** " + str1 + "failed: ";
        FFSDebug.log(str2, paramWireInfo.getStatusMsg(), 0);
        localFFSConnectionHolder.conn.rollback();
        localObject1 = paramWireInfo;
        return localObject1;
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject1 = "*** " + str1 + "failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1 + str3, 0);
      throw new FFSException(localThrowable, (String)localObject1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    paramWireInfo.setStatusCode(0);
    paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    FFSDebug.log(str1, " Done", 6);
    return paramWireInfo;
  }
  
  private WireInfo jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2, WireInfo paramWireInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "WireProcessor.cancWireTrn: ";
    FFSDebug.log(str1, "Start", 6);
    WireInfo localWireInfo1 = null;
    WirePayeeInfo localWirePayeeInfo1 = null;
    WirePayeeInfo localWirePayeeInfo2 = null;
    String str2 = null;
    String[] arrayOfString = null;
    int i = 0;
    int j = 0;
    int k = 0;
    String str3 = null;
    String str4;
    if (paramWireInfo.getSrvrTid() == null)
    {
      paramWireInfo.setStatusCode(16000);
      str4 = BPWLocaleUtil.getMessage(16000, new String[] { "SrvrTId" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str4);
      FFSDebug.log(str1, str4, 0);
      if (this.B >= 1) {
        a(paramWireInfo, "Can", null, paramBoolean1);
      }
      return paramWireInfo;
    }
    jdMethod_for(paramWireInfo, paramBoolean1);
    if (paramWireInfo.getStatusCode() != 0)
    {
      if (this.B >= 1) {
        a(paramWireInfo, "Can", null, paramBoolean1);
      }
      return paramWireInfo;
    }
    str2 = paramWireInfo.getWireType();
    a(paramWireInfo);
    if (paramWireInfo.getStatusCode() != 0)
    {
      if (this.B >= 1) {
        a(paramWireInfo, "Can", null, paramBoolean1);
      }
      return paramWireInfo;
    }
    try
    {
      str4 = paramWireInfo.getLogId();
      if ((str4 == null) || (str4.trim().length() == 0)) {
        str4 = DBUtil.getNextIndexString("LogID");
      }
      str5 = paramWireInfo.getSrvrTid();
      if ((paramBoolean2) && (Trans.checkDuplicateTIDAndSaveTID(paramWireInfo.getTrnId())))
      {
        if (this.B >= 1) {
          a(paramFFSConnectionHolder2, paramWireInfo, "CanDup", paramBoolean1);
        }
        paramFFSConnectionHolder2.conn.commit();
        paramWireInfo.setStatusCode(19220);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19220, null, "WIRE_MESSAGE"));
        localObject1 = paramWireInfo;
        return localObject1;
      }
      if ((paramWireInfo.getBatchId() != null) && (paramWireInfo.getBatchId().trim().length() != 0)) {
        k = 1;
      }
      WireInfo localWireInfo2;
      if ((k == 0) && (!a(paramWireInfo, paramBoolean1)))
      {
        localObject1 = "failed: " + paramWireInfo.getStatusMsg();
        FFSDebug.log(str1, (String)localObject1, 0);
        if (this.B >= 1) {
          a(paramWireInfo, "Can", null, paramBoolean1);
        }
        localWireInfo2 = paramWireInfo;
        return localWireInfo2;
      }
      FFSDebug.log(str1 + ": WireType: ", paramWireInfo.getWireType(), 6);
      if ("RECTEMPLATE".equalsIgnoreCase(str2)) {
        j = 1;
      }
      if ("TEMPLATE".equalsIgnoreCase(str2)) {
        i = 1;
      }
      localObject1 = paramWireInfo.getUserId();
      localWireInfo1 = new WireInfo();
      localWireInfo1.setSrvrTid(paramWireInfo.getSrvrTid());
      localWireInfo1 = Wire.getWireInfo(paramFFSConnectionHolder1, localWireInfo1, paramBoolean1, false);
      if (localWireInfo1.getStatusCode() != 0)
      {
        paramWireInfo.setStatusCode(localWireInfo1.getStatusCode());
        paramWireInfo.setStatusMsg(localWireInfo1.getStatusMsg());
        localWireInfo2 = paramWireInfo;
        return localWireInfo2;
      }
      localWireInfo1.setLogId(str4);
      localWireInfo1.setUserId((String)localObject1);
      Object localObject3;
      WireInfo localWireInfo3;
      if (((paramBoolean1) && (j == 0)) || ((!paramBoolean1) && (i == 0) && (localWireInfo1.getPrcStatus().equalsIgnoreCase("WILLPROCESSON"))))
      {
        if (k == 0)
        {
          boolean bool = a(paramWireInfo, paramBoolean1, paramFFSConnectionHolder1);
          if (!bool)
          {
            paramWireInfo.setStatusCode(19010);
            localObject3 = BPWLocaleUtil.getMessage(16000, new String[] { "SrvrTId:", str5 }, "WIRE_MESSAGE");
            paramWireInfo.setStatusMsg((String)localObject3);
            FFSDebug.log(str1 + (String)localObject3, 0);
            if (this.B >= 1) {
              a(paramWireInfo, "Can", null, paramBoolean1);
            }
            localWireInfo3 = paramWireInfo;
            return localWireInfo3;
          }
        }
        a(paramFFSConnectionHolder1, paramWireInfo.getSrvrTid(), paramBoolean1);
      }
      if ((!paramBoolean1) || (j != 0))
      {
        int m = LimitCheckApprovalProcessor.processWireDelete(paramFFSConnectionHolder1, localWireInfo1, null);
        localObject3 = LimitCheckApprovalProcessor.mapToStatus(m);
        FFSDebug.log(str1, "retStatus LimitCheck:" + (String)localObject3, 6);
        if ("LIMIT_REVERT_FAILED".equals(localObject3))
        {
          FFSDebug.log(str1, "Limit Revert Failed", 6);
          paramFFSConnectionHolder1.conn.rollback();
          if (this.B >= 1) {
            a(paramWireInfo, "Can", null, paramBoolean1);
          }
          localWireInfo1.setPrcStatus((String)localObject3);
          paramWireInfo.setPrcStatus(localWireInfo1.getPrcStatus());
          paramWireInfo.setStatusCode(localWireInfo1.getStatusCode());
          paramWireInfo.setStatusMsg(localWireInfo1.getStatusMsg());
          FFSDebug.log(str1, "returning from here", 6);
          localWireInfo3 = paramWireInfo;
          return localWireInfo3;
        }
      }
      Wire.cancelWire(paramFFSConnectionHolder1, paramWireInfo, paramBoolean1);
      Object localObject2;
      if (paramWireInfo.getStatusCode() != 0)
      {
        localObject2 = "failed: " + paramWireInfo.getStatusMsg();
        FFSDebug.log(str1, (String)localObject2, 0);
        paramFFSConnectionHolder1.conn.rollback();
        if (this.B >= 1) {
          a(paramWireInfo, "Can", null, paramBoolean1);
        }
        localObject3 = paramWireInfo;
        return localObject3;
      }
      localWirePayeeInfo1 = localWireInfo1.getWirePayeeInfo();
      if ((localWirePayeeInfo1 != null) && (paramWireInfo.getTemplateId() == null) && (paramWireInfo.getRecSrvrTid() == null) && (!paramBoolean1) && (("UNMANAGED".equals(localWirePayeeInfo1.getPayeeGroup())) || ("SECURE".equals(localWirePayeeInfo1.getPayeeGroup()))))
      {
        WirePayee.delete(paramFFSConnectionHolder1, localWirePayeeInfo1);
        if (localWirePayeeInfo1.getStatusCode() != 0)
        {
          paramFFSConnectionHolder1.conn.rollback();
          localObject2 = "failed: " + localWirePayeeInfo1.getStatusMsg();
          FFSDebug.log(str1, (String)localObject2, 0);
          paramWireInfo.setStatusCode(localWirePayeeInfo1.getStatusCode());
          paramWireInfo.setStatusMsg(localWirePayeeInfo1.getStatusMsg());
          if (this.B >= 1) {
            a(paramWireInfo, "Can", null, paramBoolean1);
          }
          localObject3 = paramWireInfo;
          return localObject3;
        }
        WirePayeeProcessor.logAudit(paramFFSConnectionHolder1, localWirePayeeInfo1, "Can");
      }
      str3 = localWireInfo1.getWireDest();
      if ((str3 != null) && (str3.equals("DRAWDOWN")))
      {
        localWirePayeeInfo2 = localWireInfo1.getWireCreditInfo();
        if ((paramWireInfo.getTemplateId() == null) && (paramWireInfo.getRecSrvrTid() == null) && (!paramBoolean1) && (("UNMANAGED".equals(localWirePayeeInfo2.getPayeeGroup())) || ("SECURE".equals(localWirePayeeInfo2.getPayeeGroup()))))
        {
          WirePayee.delete(paramFFSConnectionHolder1, localWirePayeeInfo2);
          if (localWirePayeeInfo2.getStatusCode() != 0)
          {
            paramFFSConnectionHolder1.conn.rollback();
            localObject2 = "failed: " + localWirePayeeInfo2.getStatusMsg();
            FFSDebug.log(str1, (String)localObject2, 0);
            paramWireInfo.setStatusCode(localWirePayeeInfo2.getStatusCode());
            paramWireInfo.setStatusMsg(localWirePayeeInfo2.getStatusMsg());
            if (this.B >= 1) {
              a(paramWireInfo, "Can", null, paramBoolean1);
            }
            localObject3 = paramWireInfo;
            return localObject3;
          }
          WirePayeeProcessor.logAudit(paramFFSConnectionHolder1, localWirePayeeInfo2, "Can");
        }
      }
      if (paramBoolean1 == true)
      {
        localObject2 = (RecWireInfo)paramWireInfo;
        arrayOfString = Wire.getSrvrTIDsForRecSrvrTID(paramFFSConnectionHolder1, ((RecWireInfo)localObject2).getSrvrTid());
        FFSDebug.log(str1, " Number of singleSrvrTIds:" + arrayOfString.length, 6);
        ((RecWireInfo)localObject2).setSingleIds(arrayOfString);
        if (arrayOfString != null)
        {
          localObject3 = new WireInfo();
          for (int i1 = 0; i1 < arrayOfString.length; i1++)
          {
            ((WireInfo)localObject3).setSrvrTid(arrayOfString[i1]);
            localObject3 = Wire.getWireInfo(paramFFSConnectionHolder1, (WireInfo)localObject3, false);
            if (((WireInfo)localObject3).getStatusCode() == 0)
            {
              if (((WireInfo)localObject3).getPrcStatus().equalsIgnoreCase("WILLPROCESSON")) {
                a(paramFFSConnectionHolder1, ((WireInfo)localObject3).getSrvrTid(), false);
              }
              FFSDebug.log(str1, " single wire Instance for limit revert:" + localObject3, 6);
              if (j == 0)
              {
                int i2 = LimitCheckApprovalProcessor.processWireDelete(paramFFSConnectionHolder1, (WireInfo)localObject3, null);
                String str6 = LimitCheckApprovalProcessor.mapToStatus(i2);
                FFSDebug.log(str1, "retStatus LimitCheck:" + str6, 6);
                if ("LIMIT_REVERT_FAILED".equals(str6))
                {
                  FFSDebug.log(str1, "Limit Revert Failed", 6);
                  paramFFSConnectionHolder1.conn.rollback();
                  if (this.B >= 1) {
                    a(paramWireInfo, "Can", null, paramBoolean1);
                  }
                  ((WireInfo)localObject3).setPrcStatus(str6);
                  paramWireInfo.setPrcStatus(((WireInfo)localObject3).getPrcStatus());
                  paramWireInfo.setStatusCode(((WireInfo)localObject3).getStatusCode());
                  paramWireInfo.setStatusMsg(((WireInfo)localObject3).getStatusMsg());
                  FFSDebug.log(str1, "returning from here", 6);
                  WireInfo localWireInfo4 = paramWireInfo;
                  return localWireInfo4;
                }
              }
            }
          }
        }
        Wire.canSingleForRecurring(paramFFSConnectionHolder1, (RecWireInfo)localObject2);
        if (paramWireInfo.getStatusCode() != 0)
        {
          paramFFSConnectionHolder1.conn.rollback();
          if (this.B >= 1) {
            a(paramWireInfo, "Can", null, paramBoolean1);
          }
          localObject3 = paramWireInfo;
          return localObject3;
        }
      }
      if (this.B >= 3)
      {
        localWireInfo1.setPrcStatus("CANCELEDON");
        a(paramFFSConnectionHolder2, localWireInfo1, "Can", paramBoolean1);
        if ((paramBoolean1) && (arrayOfString != null))
        {
          localObject2 = new WireInfo();
          for (int n = 0; n < arrayOfString.length; n++)
          {
            ((WireInfo)localObject2).setSrvrTid(arrayOfString[n]);
            localObject2 = Wire.getWireInfo(paramFFSConnectionHolder1, (WireInfo)localObject2, false, false);
            ((WireInfo)localObject2).setUserId((String)localObject1);
            a(paramFFSConnectionHolder2, (WireInfo)localObject2, "Can", false);
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable = localThrowable;
      if (this.B >= 1) {
        a(localWireInfo1, "Can", "Delete wire failed, unknown error occurred.", paramBoolean1);
      }
      String str5 = "*** " + str1 + "failed: ";
      Object localObject1 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5 + (String)localObject1, 0);
      throw new FFSException(localThrowable, str5);
    }
    finally {}
    paramWireInfo.setStatusCode(0);
    paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    FFSDebug.log(str1, " Done", 6);
    return paramWireInfo;
  }
  
  public WireInfo modWireTrn(WireInfo paramWireInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.modWireTrn: ";
    FFSDebug.log(str1, "Start ", 6);
    String str2 = null;
    if (paramWireInfo == null)
    {
      paramWireInfo = new WireInfo();
      paramWireInfo.setStatusCode(16000);
      String str3 = BPWLocaleUtil.getMessage(16000, new String[] { "WireInfo" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str3);
      FFSDebug.log(str1, str3, 0);
      return paramWireInfo;
    }
    try
    {
      str2 = paramWireInfo.getWireType();
      if ((str2 != null) && (str2.trim().equalsIgnoreCase("TEMPLATE"))) {
        paramWireInfo.setDateDue(null);
      }
      jdMethod_if(paramWireInfo);
      if (paramWireInfo.getStatusCode() != 0)
      {
        if (this.B >= 1) {
          a(paramWireInfo, "Mod", null, false);
        }
        return paramWireInfo;
      }
      paramWireInfo = jdMethod_do(paramWireInfo, false);
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** WireProcessor.modWireTrn failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4 + str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    return paramWireInfo;
  }
  
  public RecWireInfo modRecWireTrn(RecWireInfo paramRecWireInfo)
    throws FFSException
  {
    if (paramRecWireInfo == null)
    {
      paramRecWireInfo = new RecWireInfo();
      paramRecWireInfo.setStatusCode(16000);
      String str1 = BPWLocaleUtil.getMessage(16000, new String[] { "RecWireInfo" }, "WIRE_MESSAGE");
      paramRecWireInfo.setStatusMsg(str1);
      FFSDebug.log("WireProcessor.modRecWireTrn: " + str1, 0);
      return paramRecWireInfo;
    }
    try
    {
      a(paramRecWireInfo);
      if (paramRecWireInfo.getStatusCode() != 0)
      {
        if (this.B >= 1) {
          a(paramRecWireInfo, "Mod", null, true);
        }
        return paramRecWireInfo;
      }
      jdMethod_do(paramRecWireInfo, true);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** WireProcessor.modRecWireTrn failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    return paramRecWireInfo;
  }
  
  private WireInfo jdMethod_do(WireInfo paramWireInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "WireProcessor.modWireTrn(.. ,boolean):";
    FFSDebug.log(str1, " Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1;
    if (paramWireInfo.getSrvrTid() == null)
    {
      paramWireInfo.setStatusCode(16000);
      localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "SrvrTId" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + " " + (String)localObject1, 0);
      if (this.B >= 1) {
        a(paramWireInfo, "Mod", null, paramBoolean);
      }
      return paramWireInfo;
    }
    if (!jdMethod_if(paramWireInfo, paramBoolean))
    {
      if (this.B >= 1) {
        a(paramWireInfo, "Mod", null, paramBoolean);
      }
      return paramWireInfo;
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException(str1 + "Can not get DB Connection.");
      }
      paramWireInfo = a(localFFSConnectionHolder, localFFSConnectionHolder, paramWireInfo, paramBoolean, true);
      if (paramWireInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject1 = paramWireInfo;
        return localObject1;
      }
      FFSDebug.log(str1, " commit change", 6);
      localFFSConnectionHolder.conn.commit();
      paramWireInfo.setStatusCode(0);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      FFSDebug.log(str1, " Done", 6);
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "*** WireProcessor.modWireTrn failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return paramWireInfo;
  }
  
  private WireInfo a(FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2, WireInfo paramWireInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "WireProcessor.modWireTrn(,boolean): ";
    FFSDebug.log(str1, " Start ", 6);
    int i = 0;
    int j = 0;
    String str2 = null;
    String[] arrayOfString = null;
    String str3 = null;
    ArrayList localArrayList = null;
    int k = 0;
    try
    {
      String str4 = paramWireInfo.getLogId();
      if ((str4 == null) || (str4.trim().length() == 0))
      {
        str4 = DBUtil.getNextIndexString("LogID");
        paramWireInfo.setLogId(str4);
      }
      FFSDebug.log(str1, " logID: ", str4, 6);
      if ("RECTEMPLATE".equalsIgnoreCase(paramWireInfo.getWireType())) {
        i = 1;
      }
      if ("TEMPLATE".equalsIgnoreCase(paramWireInfo.getWireType())) {
        j = 1;
      }
      if ((paramWireInfo.getBatchId() != null) && (paramWireInfo.getBatchId().trim().length() != 0)) {
        k = 1;
      }
      if ((paramBoolean2) && (Trans.checkDuplicateTIDAndSaveTID(paramWireInfo.getTrnId())))
      {
        if (this.B >= 1) {
          a(paramFFSConnectionHolder2, paramWireInfo, "ModDup", paramBoolean1);
        }
        paramFFSConnectionHolder2.conn.commit();
        paramWireInfo.setStatusCode(19220);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19220, null, "WIRE_MESSAGE"));
        localObject1 = paramWireInfo;
        return localObject1;
      }
      Object localObject2;
      if (!a(paramWireInfo, paramBoolean1))
      {
        localObject1 = "failed: " + paramWireInfo.getStatusMsg();
        FFSDebug.log(str1, (String)localObject1, 0);
        paramFFSConnectionHolder1.conn.rollback();
        if (this.B >= 1) {
          a(paramWireInfo, "Mod", null, paramBoolean1);
        }
        localObject2 = paramWireInfo;
        return localObject2;
      }
      Object localObject5;
      if ((paramBoolean1) && (i == 0))
      {
        localObject1 = (RecWireInfo)paramWireInfo;
        if (((RecWireInfo)localObject1).getPmtsCount() < 1)
        {
          paramWireInfo.setStatusCode(19180);
          localObject2 = BPWLocaleUtil.getMessage(19180, new String[] { "SrvrTId:", paramWireInfo.getSrvrTid() }, "WIRE_MESSAGE");
          paramWireInfo.setStatusMsg((String)localObject2);
          FFSDebug.log(str1 + ", " + (String)localObject2, 0);
          if (this.B >= 1) {
            a(paramWireInfo, "Mod", null, paramBoolean1);
          }
          localObject5 = paramWireInfo;
          return localObject5;
        }
      }
      localObject1 = new WireInfo();
      ((WireInfo)localObject1).setSrvrTid(paramWireInfo.getSrvrTid());
      localObject1 = Wire.getWireInfo(paramFFSConnectionHolder1, (WireInfo)localObject1, paramBoolean1);
      if (((WireInfo)localObject1).getStatusCode() != 0)
      {
        localObject2 = "Failed to  retrieve old values of wire";
        FFSDebug.log(str1, (String)localObject2, 0);
        paramWireInfo.setStatusCode(((WireInfo)localObject1).getStatusCode());
        paramWireInfo.setStatusMsg(((WireInfo)localObject1).getStatusMsg());
        paramFFSConnectionHolder1.conn.rollback();
        if (this.B >= 1) {
          a(paramWireInfo, "Mod", null, paramBoolean1);
        }
        localObject5 = paramWireInfo;
        return localObject5;
      }
      WireInfo localWireInfo1;
      Object localObject3;
      if (((paramBoolean1) && (i == 0)) || ((!paramBoolean1) && (j == 0) && (((WireInfo)localObject1).getPrcStatus().equalsIgnoreCase("WILLPROCESSON"))))
      {
        if (k == 0)
        {
          boolean bool = a(paramWireInfo, paramBoolean1, paramFFSConnectionHolder1);
          if (!bool)
          {
            paramWireInfo.setStatusCode(19000);
            localObject5 = BPWLocaleUtil.getMessage(19000, new String[] { "SrvrTId:", paramWireInfo.getSrvrTid() }, "WIRE_MESSAGE");
            paramWireInfo.setStatusMsg((String)localObject5);
            FFSDebug.log(str1 + ", " + (String)localObject5, 0);
            if (this.B >= 1) {
              a(paramWireInfo, "Mod", null, paramBoolean1);
            }
            localWireInfo1 = paramWireInfo;
            return localWireInfo1;
          }
        }
        if (paramBoolean1)
        {
          localObject3 = (RecWireInfo)paramWireInfo;
          a(paramFFSConnectionHolder1, (RecWireInfo)localObject3);
        }
      }
      FFSDebug.log(str1, "wire: " + paramWireInfo, 6);
      int m;
      if (!paramBoolean1)
      {
        str2 = paramWireInfo.getWireSource();
        if ((str2 != null) && (str2.trim().equalsIgnoreCase("HOST"))) {
          if (((WireInfo)localObject1).getHostId() == null)
          {
            if (paramWireInfo.getHostId() != null)
            {
              paramFFSConnectionHolder1.conn.rollback();
              localObject3 = "failed: " + BPWLocaleUtil.getMessage(19470, null, "WIRE_MESSAGE");
              FFSDebug.log(str1, (String)localObject3, 0);
              paramWireInfo.setStatusCode(19470);
              paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19470, null, "WIRE_MESSAGE"));
              paramFFSConnectionHolder1.conn.rollback();
              if (this.B >= 1) {
                a(paramWireInfo, "Mod", null, paramBoolean1);
              }
              localObject5 = paramWireInfo;
              return localObject5;
            }
          }
          else if (!((WireInfo)localObject1).getHostId().equalsIgnoreCase(paramWireInfo.getHostId()))
          {
            paramFFSConnectionHolder1.conn.rollback();
            localObject3 = "failed: " + BPWLocaleUtil.getMessage(19470, null, "WIRE_MESSAGE");
            FFSDebug.log(str1, (String)localObject3, 0);
            paramWireInfo.setStatusCode(19470);
            paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19470, null, "WIRE_MESSAGE"));
            paramFFSConnectionHolder1.conn.rollback();
            if (this.B >= 1) {
              a(paramWireInfo, "Mod", null, paramBoolean1);
            }
            localObject5 = paramWireInfo;
            return localObject5;
          }
        }
        if (j == 0)
        {
          if (((WireInfo)localObject1).getPrcStatus().equalsIgnoreCase("WILLPROCESSON")) {
            a(paramFFSConnectionHolder1, paramWireInfo.getSrvrTid(), false);
          }
          m = LimitCheckApprovalProcessor.processWireDelete(paramFFSConnectionHolder1, (WireInfo)localObject1, null);
          localObject5 = LimitCheckApprovalProcessor.mapToStatus(m);
          FFSDebug.log("WireProcessor.modWireTrn: retStatus LimitCheck:" + (String)localObject5, 6);
          if ("LIMIT_REVERT_FAILED".equals(localObject5))
          {
            FFSDebug.log(str1, " Limit Revert Failed.", 6);
            paramFFSConnectionHolder1.conn.rollback();
            if (this.B >= 1) {
              a(paramWireInfo, "Mod", null, paramBoolean1);
            }
            ((WireInfo)localObject1).setPrcStatus((String)localObject5);
            paramWireInfo.setPrcStatus(((WireInfo)localObject1).getPrcStatus());
            paramWireInfo.setStatusCode(((WireInfo)localObject1).getStatusCode());
            paramWireInfo.setStatusMsg(((WireInfo)localObject1).getStatusMsg());
            localWireInfo1 = paramWireInfo;
            return localWireInfo1;
          }
          FFSDebug.log(str1, " new single wireInfo for processWireAdd:" + paramWireInfo, 6);
          limitCheckWireAdd(paramFFSConnectionHolder1, paramWireInfo, null);
          localObject5 = paramWireInfo.getPrcStatus();
          if (("LIMIT_CHECK_FAILED".equals(localObject5)) || ("LIMIT_REVERT_FAILED".equals(localObject5)) || ("APPROVAL_FAILED".equals(localObject5)))
          {
            FFSDebug.log(str1, " Limit Add Failed.", 6);
            paramFFSConnectionHolder1.conn.rollback();
            if (this.B >= 1) {
              a(paramWireInfo, "Mod", null, paramBoolean1);
            }
            localWireInfo1 = paramWireInfo;
            return localWireInfo1;
          }
          Wire.updateStatus(paramFFSConnectionHolder1, paramWireInfo);
        }
      }
      if ((j != 0) || (i != 0))
      {
        m = LimitCheckApprovalProcessor.processWireDelete(paramFFSConnectionHolder1, (WireInfo)localObject1, null);
        localObject5 = LimitCheckApprovalProcessor.mapToStatus(m);
        FFSDebug.log("WireProcessor.modWireTrn: template.retStatus LimitCheck:" + (String)localObject5, 6);
        if ("LIMIT_REVERT_FAILED".equals(localObject5))
        {
          FFSDebug.log(str1, " Template Limit Revert Failed.", 6);
          paramFFSConnectionHolder1.conn.rollback();
          if (this.B >= 1) {
            a(paramWireInfo, "Mod", null, paramBoolean1);
          }
          paramWireInfo.setPrcStatus(((WireInfo)localObject1).getPrcStatus());
          paramWireInfo.setStatusCode(((WireInfo)localObject1).getStatusCode());
          paramWireInfo.setStatusMsg(((WireInfo)localObject1).getStatusMsg());
          localWireInfo1 = paramWireInfo;
          return localWireInfo1;
        }
        FFSDebug.log(str1, " new template for processWireAdd:" + paramWireInfo, 6);
        m = LimitCheckApprovalProcessor.processWireAdd(paramFFSConnectionHolder1, paramWireInfo, null);
        localObject5 = LimitCheckApprovalProcessor.mapToStatus(m);
        FFSDebug.log(str1 + ": template.retStatus LimitCheck:" + (String)localObject5, 6);
        if (("LIMIT_CHECK_FAILED".equals(localObject5)) || ("LIMIT_REVERT_FAILED".equals(localObject5)) || ("APPROVAL_FAILED".equals(localObject5)))
        {
          paramFFSConnectionHolder1.conn.rollback();
          if (this.B >= 1) {
            a(paramWireInfo, "Add", null, paramBoolean1);
          }
          localWireInfo1 = paramWireInfo;
          return localWireInfo1;
        }
        if ("APPROVAL_PENDING".equals(localObject5)) {
          paramWireInfo.setPrcStatus("APPROVAL_PENDING");
        } else if ("APPROVAL_NOT_ALLOWED".equals(localObject5)) {
          paramWireInfo.setPrcStatus("APPROVAL_NOT_ALLOWED");
        } else if (j != 0) {
          paramWireInfo.setPrcStatus("TEMPLATE");
        } else if (i != 0) {
          paramWireInfo.setPrcStatus("RECTEMPLATE");
        }
        Wire.updateStatus(paramFFSConnectionHolder1, paramWireInfo, paramWireInfo.getPrcStatus(), paramBoolean1);
      }
      FFSDebug.log(str1, "wireInfo:" + paramWireInfo, 0);
      str2 = paramWireInfo.getWireSource();
      if ((str2 == null) || (!str2.trim().equalsIgnoreCase("HOST")))
      {
        paramWireInfo = a(paramFFSConnectionHolder1, paramWireInfo, (WireInfo)localObject1, paramBoolean1, true);
        if (paramWireInfo.getStatusCode() != 0)
        {
          localObject4 = paramWireInfo;
          return localObject4;
        }
      }
      str3 = paramWireInfo.getWireDest();
      if ((str3 != null) && (str3.equals("DRAWDOWN")))
      {
        localObject4 = paramWireInfo.getWireCreditInfo();
        if (localObject4 == null)
        {
          paramWireInfo.setStatusCode(19560);
          paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19560, null, "WIRE_MESSAGE"));
          paramFFSConnectionHolder1.conn.rollback();
          if (this.B >= 1) {
            a(paramWireInfo, "Mod", null, paramBoolean1);
          }
          localObject5 = paramWireInfo;
          return localObject5;
        }
        paramWireInfo = a(paramFFSConnectionHolder1, paramWireInfo, (WireInfo)localObject1, paramBoolean1, false);
        if (paramWireInfo.getStatusCode() != 0)
        {
          localObject5 = paramWireInfo;
          return localObject5;
        }
      }
      paramWireInfo = Wire.updateWire(paramFFSConnectionHolder1, paramWireInfo, paramBoolean1);
      if (paramWireInfo.getStatusCode() != 0)
      {
        paramFFSConnectionHolder1.conn.rollback();
        if (this.B >= 1) {
          a(paramWireInfo, "Mod", null, paramBoolean1);
        }
        localObject4 = paramWireInfo;
        return localObject4;
      }
      if ((paramBoolean1) && (i == 0))
      {
        localObject4 = (RecWireInfo)paramWireInfo;
        arrayOfString = Wire.getSrvrTIDsForRecSrvrTID(paramFFSConnectionHolder1, ((RecWireInfo)localObject4).getSrvrTid());
        FFSDebug.log(str1, " Number of singleSrvrTIds = " + arrayOfString.length, 6);
        ((RecWireInfo)localObject4).setSingleIds(arrayOfString);
        if (arrayOfString != null)
        {
          localObject5 = null;
          localArrayList = new ArrayList();
          for (int i1 = 0; i1 < arrayOfString.length; i1++)
          {
            localObject5 = new WireInfo();
            ((WireInfo)localObject5).setSrvrTid(arrayOfString[i1]);
            localObject5 = Wire.getWireInfo(paramFFSConnectionHolder1, (WireInfo)localObject5, false);
            if (((WireInfo)localObject5).getStatusCode() == 0)
            {
              if (((WireInfo)localObject5).getPrcStatus().equalsIgnoreCase("WILLPROCESSON")) {
                a(paramFFSConnectionHolder1, ((WireInfo)localObject5).getSrvrTid(), false);
              }
              FFSDebug.log(str1, " single wire Instance for limit revert:" + localObject5, 6);
              int i2 = LimitCheckApprovalProcessor.processWireDelete(paramFFSConnectionHolder1, (WireInfo)localObject5, null);
              String str5 = LimitCheckApprovalProcessor.mapToStatus(i2);
              FFSDebug.log("WireProcessor.modWireTrn: retStatus LimitCheck:" + str5, 6);
              WireInfo localWireInfo2;
              if ("LIMIT_REVERT_FAILED".equals(str5))
              {
                FFSDebug.log(str1, " Limit Revert Failed.", 6);
                paramFFSConnectionHolder1.conn.rollback();
                if (this.B >= 1) {
                  a(paramWireInfo, "Mod", null, paramBoolean1);
                }
                ((WireInfo)localObject5).setPrcStatus(str5);
                paramWireInfo.setPrcStatus(((WireInfo)localObject5).getPrcStatus());
                paramWireInfo.setStatusCode(((WireInfo)localObject5).getStatusCode());
                paramWireInfo.setStatusMsg(((WireInfo)localObject5).getStatusMsg());
                localWireInfo2 = paramWireInfo;
                return localWireInfo2;
              }
              ((WireInfo)localObject5).setAmount(((RecWireInfo)localObject4).getAmount());
              ((WireInfo)localObject5).setSubmittedBy(((RecWireInfo)localObject4).getSubmittedBy());
              ((WireInfo)localObject5).setFromBankId(((RecWireInfo)localObject4).getFromBankId());
              ((WireInfo)localObject5).setFromAcctId(((RecWireInfo)localObject4).getFromAcctId());
              ((WireInfo)localObject5).setFromAcctType(((RecWireInfo)localObject4).getFromAcctType());
              FFSDebug.log(str1, " modified single wire instance for processWireAdd:" + localObject5, 6);
              limitCheckWireAdd(paramFFSConnectionHolder1, (WireInfo)localObject5, null);
              str5 = ((WireInfo)localObject5).getPrcStatus();
              if (("LIMIT_CHECK_FAILED".equals(str5)) || ("LIMIT_REVERT_FAILED".equals(str5)) || ("APPROVAL_FAILED".equals(str5)))
              {
                FFSDebug.log(str1, " Limit Add Failed.", 6);
                paramFFSConnectionHolder1.conn.rollback();
                if (this.B >= 1) {
                  a(paramWireInfo, "Mod", null, paramBoolean1);
                }
                paramWireInfo.setPrcStatus(((WireInfo)localObject5).getPrcStatus());
                paramWireInfo.setStatusCode(((WireInfo)localObject5).getStatusCode());
                paramWireInfo.setStatusMsg(((WireInfo)localObject5).getStatusMsg());
                paramWireInfo.setExtInfo(((WireInfo)localObject5).getExtInfo());
                localWireInfo2 = paramWireInfo;
                return localWireInfo2;
              }
              FFSDebug.log(str1, " retStatus: [", str5, "]", 6);
              FFSDebug.log(str1, " single wire Instance before status update:" + localObject5, 6);
              Wire.updateStatus(paramFFSConnectionHolder1, (WireInfo)localObject5);
              if ((!this.F) && (((WireInfo)localObject5).getPrcStatus().equals("CREATED"))) {
                localArrayList.add(((WireInfo)localObject5).getSrvrTid());
              }
            }
          }
        }
        Wire.updateSingleForRecurring(paramFFSConnectionHolder1, (RecWireInfo)localObject4);
        if (((RecWireInfo)localObject4).getStatusCode() != 0)
        {
          paramFFSConnectionHolder1.conn.rollback();
          if (this.B >= 1) {
            a(paramWireInfo, "Mod", null, paramBoolean1);
          }
          localObject5 = paramWireInfo;
          return localObject5;
        }
      }
      if ((!paramBoolean1) && (j == 0) && (!this.F) && (k == 0) && (paramWireInfo.getPrcStatus().equals("CREATED")))
      {
        FFSDebug.log(str1, " Doing auto release", 6);
        processSingleWireInAutoReleaseMode(paramFFSConnectionHolder1, paramWireInfo);
        if (paramWireInfo.getStatusCode() != 0)
        {
          localObject4 = paramWireInfo;
          return localObject4;
        }
      }
      int n;
      if ((localArrayList != null) && (localArrayList.size() > 0))
      {
        localObject4 = new WireInfo();
        for (n = 0; n < localArrayList.size(); n++)
        {
          ((WireInfo)localObject4).setSrvrTid((String)localArrayList.get(n));
          localObject4 = Wire.getWireInfo(paramFFSConnectionHolder1, (WireInfo)localObject4, false);
          Object localObject6;
          if (((WireInfo)localObject4).getStatusCode() != 0)
          {
            localObject6 = paramWireInfo;
            return localObject6;
          }
          FFSDebug.log(str1, " Doing auto release of single instance", 6);
          processSingleWireInAutoReleaseMode(paramFFSConnectionHolder1, (WireInfo)localObject4);
          if (((WireInfo)localObject4).getStatusCode() != 0)
          {
            localObject6 = localObject4;
            return localObject6;
          }
        }
      }
      if (this.B >= 3)
      {
        a(paramFFSConnectionHolder2, paramWireInfo, "Mod", paramBoolean1);
        if ((paramBoolean1) && (arrayOfString != null))
        {
          localObject4 = new WireInfo();
          for (n = 0; n < arrayOfString.length; n++)
          {
            ((WireInfo)localObject4).setSrvrTid(arrayOfString[n]);
            localObject4 = Wire.getWireInfo(paramFFSConnectionHolder1, (WireInfo)localObject4);
            ((WireInfo)localObject4).setUserId(paramWireInfo.getUserId());
            a(paramFFSConnectionHolder2, (WireInfo)localObject4, "Mod", false);
          }
        }
      }
      paramWireInfo.setStatusCode(0);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      FFSDebug.log(str1, " Done", 6);
    }
    catch (Throwable localThrowable)
    {
      localThrowable = localThrowable;
      if (this.B >= 1) {
        a(paramWireInfo, "Mod", null, paramBoolean1);
      }
      Object localObject1 = "*** WireProcessor.modWireTrn failed: ";
      Object localObject4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1 + (String)localObject4, 0);
      throw new FFSException(localThrowable, (String)localObject1);
    }
    finally {}
    return paramWireInfo;
  }
  
  private boolean a(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.isWireReleasable: ";
    FFSDebug.log(str1, "Start", 6);
    String str2 = paramWireInfo.getSrvrTid();
    String str3 = Wire.getStatus(paramFFSConnectionHolder, str2, false);
    String str4;
    if (str3 == null)
    {
      paramWireInfo.setStatusCode(16020);
      str4 = BPWLocaleUtil.getMessage(16020, new String[] { " SrvrTId: ", str2 }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str4);
      FFSDebug.log(str1 + ", " + str4, 0);
      return false;
    }
    if ((!"RELEASEPENDING".equalsIgnoreCase(str3)) && (!"CREATED".equalsIgnoreCase(str3)))
    {
      paramWireInfo.setPrcStatus(str3);
      str4 = BPWLocaleUtil.getMessage(19170, new String[] { " SrvrTId: ", str2 }, "WIRE_MESSAGE");
      paramWireInfo.setStatusCode(19170);
      paramWireInfo.setStatusMsg(str4);
      FFSDebug.log(str1 + ", " + str4, 0);
      return false;
    }
    return true;
  }
  
  public boolean isWireBatchDeleteable(String paramString)
    throws FFSException
  {
    String str1 = "WireProcessor.isWireBatchDeleteable: ";
    FFSDebug.log(str1, "Start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    boolean bool = false;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        String str2 = str1 + "Can not get DB Connection.";
        FFSDebug.log(str2, 0);
        throw new FFSException(str2);
      }
      bool = Wire.isWireBatchDeleteable(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3 + str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, "done.", 6);
    return bool;
  }
  
  public boolean isWirebatchModifyable(String paramString)
    throws FFSException
  {
    String str1 = "WireProcessor.isWirebatchModifyable: ";
    FFSDebug.log(str1, "Start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    boolean bool = false;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        String str2 = str1 + "Can not get DB Connection.";
        FFSDebug.log(str2, 0);
        throw new FFSException(str2);
      }
      bool = Wire.isWirebatchModifyable(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3 + str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, "done.", 6);
    return bool;
  }
  
  private boolean a(WireInfo paramWireInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "WireProcessor.hasRequiredStatus: ";
    FFSDebug.log(str1, "Start", 6);
    FFSDebug.log(str1, "isRecurring=" + paramBoolean, 6);
    String str2 = paramWireInfo.getSrvrTid();
    String str3 = Wire.getStatus(str2, paramBoolean);
    paramWireInfo.setPrcStatus(str3);
    if (str3 == null)
    {
      paramWireInfo.setStatusCode(16020);
      localObject = BPWLocaleUtil.getMessage(16020, new String[] { " SrvrTId: ", str2 }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1 + ", " + (String)localObject, 0);
      return false;
    }
    Object localObject = null;
    if (paramBoolean)
    {
      localObject = new String[4];
      if ((paramWireInfo.getWireType() != null) && (paramWireInfo.getWireType().trim().equalsIgnoreCase("RECTEMPLATE")))
      {
        localObject[0] = "RECTEMPLATE";
        localObject[1] = "APPROVAL_PENDING";
        localObject[2] = "APPROVAL_REJECTED";
        localObject[3] = "APPROVAL_NOT_ALLOWED";
      }
      else
      {
        localObject[0] = "WILLPROCESSON";
      }
    }
    else if ((paramWireInfo.getWireType() != null) && (paramWireInfo.getWireType().trim().equalsIgnoreCase("TEMPLATE")))
    {
      localObject = new String[4];
      localObject[0] = "TEMPLATE";
      localObject[1] = "APPROVAL_PENDING";
      localObject[2] = "APPROVAL_REJECTED";
      localObject[3] = "APPROVAL_NOT_ALLOWED";
    }
    else
    {
      localObject = new String[6];
      localObject[0] = "CREATED";
      localObject[1] = "RELEASEPENDING";
      localObject[2] = "APPROVAL_PENDING";
      localObject[3] = "APPROVAL_REJECTED";
      localObject[4] = "APPROVAL_NOT_ALLOWED";
      localObject[5] = "WILLPROCESSON";
    }
    FFSDebug.log(str1 + " wireStatus=", str3, 6);
    int i = 0;
    for (int j = 0; j < localObject.length; j++)
    {
      FFSDebug.log(str1 + " reqStatus=", localObject[j], 6);
      if (str3.equals(localObject[j]))
      {
        i = 1;
        break;
      }
    }
    if (i == 0)
    {
      paramWireInfo.setStatusCode(19170);
      String str4 = BPWLocaleUtil.getMessage(19170, new String[] { " SrvrTId: ", str2 }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str4);
      FFSDebug.log(str1 + ", " + str4, 0);
      return false;
    }
    return true;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, RecWireInfo paramRecWireInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.modifyScheduleForRec";
    String str2 = "RECWIRETRN";
    ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(paramRecWireInfo.getFiID(), str2, paramRecWireInfo.getSrvrTid(), paramFFSConnectionHolder);
    if ((localScheduleInfo == null) || (localScheduleInfo.Status.equals("Processing")))
    {
      String str3 = "*** " + str1 + " failed: Can't find schedule for: " + " InstructionType: " + str2 + " InstructionId: " + paramRecWireInfo.getSrvrTid();
      FFSDebug.log(str3, 0);
      throw new FFSException(21525, str3);
    }
    try
    {
      localScheduleInfo.StartDate = BPWUtil.getDateDBFormat(paramRecWireInfo.getStartDate());
      localScheduleInfo.Frequency = FFSUtil.getFreqInt(paramRecWireInfo.getFrequency());
      localScheduleInfo.InstanceCount = paramRecWireInfo.getPmtsCount();
      localScheduleInfo.NextInstanceDate = DBUtil.getPayday(paramRecWireInfo.getFiID(), localScheduleInfo.StartDate);
      ScheduleInfo.modifySchedule(paramFFSConnectionHolder, localScheduleInfo.ScheduleID, localScheduleInfo);
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localThrowable, str4);
    }
  }
  
  public String getWireStatus(String paramString)
    throws FFSException
  {
    try
    {
      return a(paramString, false);
    }
    catch (Throwable localThrowable)
    {
      String str1 = "*** WireProcessor.getWireStatus failed: ";
      String str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1 + str2, 0);
      throw new FFSException(localThrowable, str1 + str2);
    }
  }
  
  private String a(String paramString, boolean paramBoolean)
    throws FFSException
  {
    try
    {
      return Wire.getStatus(paramString, paramBoolean);
    }
    catch (Throwable localThrowable)
    {
      String str1 = "*** WireProcessor.getTransactionStatus failed: ";
      String str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1 + str2, 0);
      throw new FFSException(localThrowable, str1 + str2);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, boolean paramBoolean)
    throws FFSException
  {
    if (paramBoolean == true)
    {
      jdMethod_if(paramFFSConnectionHolder, (RecWireInfo)paramWireInfo);
      WireInfo localWireInfo = Wire.generateSingleWireFromRecWire(paramFFSConnectionHolder, paramWireInfo.getSrvrTid(), ((RecWireInfo)paramWireInfo).getStartDate(), 0);
      if (localWireInfo.getStatusCode() != 0)
      {
        paramWireInfo.setStatusCode(localWireInfo.getStatusCode());
        paramWireInfo.setStatusMsg(localWireInfo.getStatusMsg());
        return;
      }
      FFSDebug.log("WireProcessor.createSchedule: singleNewWire for processWireAdd:" + localWireInfo, 6);
      if (!"RECTEMPLATE".equalsIgnoreCase(localWireInfo.getWireType()))
      {
        limitCheckWireAdd(paramFFSConnectionHolder, localWireInfo, null);
        String str1 = localWireInfo.getPrcStatus();
        if (("LIMIT_CHECK_FAILED".equals(str1)) || ("LIMIT_REVERT_FAILED".equals(str1)) || ("APPROVAL_FAILED".equals(str1)))
        {
          paramWireInfo.setPrcStatus(localWireInfo.getPrcStatus());
          paramWireInfo.setStatusCode(localWireInfo.getStatusCode());
          paramWireInfo.setStatusMsg(localWireInfo.getStatusMsg());
          paramWireInfo.setExtInfo(localWireInfo.getExtInfo());
          return;
        }
      }
      FFSDebug.log("WireProcessor.createSchedule: singleNewWire before status update:" + localWireInfo, 6);
      Wire.updateStatus(paramFFSConnectionHolder, localWireInfo);
      if ((!this.F) && (localWireInfo.getPrcStatus().equals("CREATED")))
      {
        localWireInfo = processSingleWireInAutoReleaseMode(paramFFSConnectionHolder, localWireInfo);
        if (localWireInfo.getStatusCode() != 0)
        {
          paramWireInfo.setStatusCode(localWireInfo.getStatusCode());
          paramWireInfo.setStatusMsg(localWireInfo.getStatusMsg());
          return;
        }
      }
      try
      {
        if (((!paramBoolean) || (localWireInfo.getPrcStatus().equals("CREATED")) || ("RECTEMPLATE".equalsIgnoreCase(localWireInfo.getWireType()))) && (this.B >= 3))
        {
          FFSDebug.log("WireProcessor.createSchedule: audit logging3", 6);
          a(paramFFSConnectionHolder, localWireInfo, "Add", false);
        }
      }
      catch (Throwable localThrowable)
      {
        String str2 = "*** WireProcessor.createSchedule failed: ";
        String str3 = FFSDebug.stackTrace(localThrowable);
        FFSDebug.log(str2 + str3, 0);
        throw new FFSException(str2 + str3);
      }
    }
    else
    {
      jdMethod_do(paramFFSConnectionHolder, paramWireInfo);
    }
  }
  
  private void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.createScheduleForSingle: ";
    String str2 = null;
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramWireInfo.getFiID();
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = 10;
    localScheduleInfo.LogID = paramWireInfo.getLogId();
    localScheduleInfo.InstanceCount = 1;
    localScheduleInfo.CurInstanceNum = 1;
    if (A == null) {
      A = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    }
    this.J = Boolean.valueOf(A.otherProperties.getProperty("bpw.wire.funds.approval.support", "false")).booleanValue();
    if (this.J) {
      str2 = "WIREAPPROVALTRN";
    } else {
      str2 = "WIRETRN";
    }
    String str3 = paramWireInfo.getSrvrTid();
    try
    {
      FFSDebug.log(str1, "DateToPost: ", paramWireInfo.getDateToPost(), 6);
      localScheduleInfo.StartDate = BPWUtil.getDateDBFormat(paramWireInfo.getDateToPost());
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, str2, str3, localScheduleInfo);
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localThrowable, str4);
    }
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, RecWireInfo paramRecWireInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.createScheduleForRec ";
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramRecWireInfo.getFiID();
    localScheduleInfo.Status = "Active";
    try
    {
      localScheduleInfo.Frequency = FFSUtil.getFreqInt(paramRecWireInfo.getFrequency());
      localScheduleInfo.StartDate = BPWUtil.getDateDBFormat(paramRecWireInfo.getStartDate());
      localScheduleInfo.InstanceCount = paramRecWireInfo.getPmtsCount();
      localScheduleInfo.CurInstanceNum = 1;
      localScheduleInfo.LogID = paramRecWireInfo.getLogId();
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "RECWIRETRN", paramRecWireInfo.getSrvrTid(), localScheduleInfo);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** " + str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  private boolean a(WireInfo paramWireInfo, boolean paramBoolean, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "WireProcessor.canModify: ";
    FFSDebug.log(str1 + "start, isRecurring =" + paramBoolean, 6);
    String str2 = null;
    if (this.J) {
      str2 = "WIREAPPROVALTRN";
    } else {
      str2 = "WIRETRN";
    }
    if (("APPROVAL_REJECTED".equalsIgnoreCase(paramWireInfo.getPrcStatus())) || ("APPROVAL_PENDING".equalsIgnoreCase(paramWireInfo.getPrcStatus())))
    {
      FFSDebug.log(str1 + "Status is: ", paramWireInfo.getPrcStatus(), ", return true", 6);
      return true;
    }
    SchedulerInfo localSchedulerInfo = jdMethod_if(paramWireInfo.getFiID(), false);
    if (!paramBoolean)
    {
      if ((this.F) && ((paramWireInfo.getPrcStatus().equalsIgnoreCase("CREATED")) || (paramWireInfo.getPrcStatus().equalsIgnoreCase("RELEASEPENDING"))))
      {
        FFSDebug.log(str1 + "No Schedule has been created yet, return true", 6);
        return true;
      }
      if ((localSchedulerInfo == null) || (localSchedulerInfo.NextRunTime == null) || (localSchedulerInfo.NextRunTime.compareTo("N/A") == 0))
      {
        FFSDebug.log(str1 + "No " + str2 + " Schedule exists: return true", 6);
        return true;
      }
      FFSDebug.log(str1 + "schedule.NextRunTime =" + localSchedulerInfo.NextRunTime, 6);
      return ScheduleInfo.canModify(paramFFSConnectionHolder, str2, paramWireInfo.getSrvrTid(), paramWireInfo.getFiID(), localSchedulerInfo.NextRunTime);
    }
    String[] arrayOfString = Wire.getSrvrTIDsForRecSrvrTID(paramFFSConnectionHolder, paramWireInfo.getSrvrTid());
    if (arrayOfString != null)
    {
      ((RecWireInfo)paramWireInfo).setSingleIds(arrayOfString);
      int i = arrayOfString.length;
      FFSDebug.log(str1 + "No. of single wire Ids =" + i, 6);
      if ((localSchedulerInfo == null) || (localSchedulerInfo.NextRunTime == null) || (localSchedulerInfo.NextRunTime.compareTo("N/A") == 0))
      {
        FFSDebug.log(str1 + "No " + str2 + " Schedule exists: ", 6);
      }
      else
      {
        FFSDebug.log(str1 + "schedule.NextRunTime =" + localSchedulerInfo.NextRunTime, 6);
        for (int j = 0; j < i; j++)
        {
          String str3 = Wire.getStatus(paramFFSConnectionHolder, arrayOfString[j], false);
          if ((str3 != null) && (str3.length() != 0)) {
            if (("APPROVAL_REJECTED".equalsIgnoreCase(str3)) || ("APPROVAL_PENDING".equalsIgnoreCase(str3))) {
              FFSDebug.log(str1 + "Status is: ", str3, ", return true", 6);
            } else if ((this.F) && ((str3.equalsIgnoreCase("CREATED")) || (str3.equalsIgnoreCase("RELEASEPENDING")))) {
              FFSDebug.log(str1 + "No Schedule has been created yet, continue ..", 6);
            } else if (!ScheduleInfo.canModify(paramFFSConnectionHolder, str2, arrayOfString[j], paramWireInfo.getFiID(), localSchedulerInfo.NextRunTime)) {
              return false;
            }
          }
        }
      }
    }
    FFSDebug.log(str1 + "Now getting schedule for RECWIRETRN for SrvrTid =" + paramWireInfo.getSrvrTid(), 6);
    localSchedulerInfo = jdMethod_if(paramWireInfo.getFiID(), true);
    if ((localSchedulerInfo == null) || (localSchedulerInfo.NextRunTime == null) || (localSchedulerInfo.NextRunTime.compareTo("N/A") == 0))
    {
      FFSDebug.log(str1 + "No RECWIRETRN Schedule exists: return true", 6);
      return true;
    }
    FFSDebug.log(str1 + "schedule.NextRunTime =" + localSchedulerInfo.NextRunTime, 6);
    return ScheduleInfo.canModify(paramFFSConnectionHolder, "RECWIRETRN", paramWireInfo.getSrvrTid(), paramWireInfo.getFiID(), localSchedulerInfo.NextRunTime);
  }
  
  private SchedulerInfo jdMethod_if(String paramString, boolean paramBoolean)
    throws FFSException
  {
    SchedulerInfo localSchedulerInfo = null;
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    String str = null;
    if (paramBoolean == true) {
      str = "RECWIRETRN";
    } else if (this.J) {
      str = "WIREAPPROVALTRN";
    } else {
      str = "WIRETRN";
    }
    if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
      localSchedulerInfo = localScheduler.getSchedulerInfo(str, paramString);
    } else {
      try
      {
        BPWServer localBPWServer = BPWServer.getInstance();
        localSchedulerInfo = localBPWServer.getSchedulerInfoOnRemoteServer(str, paramString);
      }
      catch (Exception localException)
      {
        FFSDebug.log(localException, "", 0);
        return null;
      }
    }
    if (localSchedulerInfo == null) {
      FFSDebug.log(str + " " + "Instruction type does not exist" + " for FIID =" + paramString, 0);
    }
    return localSchedulerInfo;
  }
  
  private boolean jdMethod_if(WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.lockScheduleOfWireBatch: ";
    FFSDebug.log(str1, "starts", 6);
    boolean bool = false;
    String str2 = null;
    String str3 = paramWireBatchInfo.getFIId();
    String str4 = paramWireBatchInfo.getBatchId();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (this.J) {
        str2 = "WIREAPPROVALTRN";
      } else {
        str2 = "WIRETRN";
      }
      bool = ScheduleInfo.lockScheduleOfWireBatch(localFFSConnectionHolder, str2, str4, str3);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str5 = "*** " + str1 + "failed: ";
      String str6 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str6, 0);
      throw new FFSException(localException, str5);
    }
    finally
    {
      FFSDebug.log(str1 + "Free connection.", 6);
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, "okToModify=" + bool, 6);
    FFSDebug.log(str1, "end", 6);
    return bool;
  }
  
  private boolean jdMethod_do(WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.unlockScheduleOfWireBatch: ";
    FFSDebug.log(str1, "starts", 6);
    boolean bool = false;
    String str2 = null;
    String str3 = paramWireBatchInfo.getFIId();
    String str4 = paramWireBatchInfo.getBatchId();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (this.J) {
        str2 = "WIREAPPROVALTRN";
      } else {
        str2 = "WIRETRN";
      }
      bool = ScheduleInfo.unlockScheduleOfWireBatch(localFFSConnectionHolder, str2, str4, str3);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str5 = "*** " + str1 + "failed: ";
      String str6 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str6, 0);
      throw new FFSException(localException, str5);
    }
    finally
    {
      FFSDebug.log(str1 + "Free connection.", 6);
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, "okToModify=" + bool, 6);
    FFSDebug.log(str1, "end", 6);
    return bool;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str = null;
    if (this.J) {
      str = "WIREAPPROVALTRN";
    } else {
      str = "WIRETRN";
    }
    if (paramBoolean == true)
    {
      ScheduleInfo.cancelSchedule(paramFFSConnectionHolder.conn, "RECWIRETRN", paramString);
      String[] arrayOfString = Wire.getSrvrTIDsForRecSrvrTID(paramFFSConnectionHolder, paramString);
      for (int i = 0; i < arrayOfString.length; i++) {
        ScheduleInfo.cancelSchedule(paramFFSConnectionHolder.conn, str, arrayOfString[i]);
      }
    }
    ScheduleInfo.cancelSchedule(paramFFSConnectionHolder.conn, str, paramString);
  }
  
  public WireInfo getWireTransferById(String paramString)
    throws FFSException
  {
    if (paramString == null)
    {
      WireInfo localWireInfo = new WireInfo();
      localWireInfo.setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "wireId " }, "WIRE_MESSAGE");
      localWireInfo.setStatusMsg(str);
      FFSDebug.log("WireProcessor.getWireTransfer, " + str, 0);
      return localWireInfo;
    }
    return a(paramString, false, true);
  }
  
  public RecWireInfo getRecWireTransferById(String paramString, boolean paramBoolean)
    throws FFSException
  {
    if (paramString == null)
    {
      RecWireInfo localRecWireInfo = new RecWireInfo();
      localRecWireInfo.setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "wireId " }, "WIRE_MESSAGE");
      localRecWireInfo.setStatusMsg(str);
      FFSDebug.log("WireProcessor.getRecWireTransfer, " + str, 0);
      return localRecWireInfo;
    }
    return (RecWireInfo)a(paramString, true, paramBoolean);
  }
  
  private WireInfo a(String paramString, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "WireProcessor.getWireTransfer(boolean): ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1 = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException("WireProcessor.getWireTransfer: Can not get DB Connection.");
      }
      if (paramBoolean1) {
        localObject1 = new RecWireInfo();
      } else {
        localObject1 = new WireInfo();
      }
      ((WireInfo)localObject1).setSrvrTid(paramString);
      localObject1 = Wire.getWireInfo(localFFSConnectionHolder, (WireInfo)localObject1, paramBoolean1, paramBoolean2);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "*** WireProcessor.getWireTransfer failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, " Done", 6);
    return localObject1;
  }
  
  public WireInfo[] getWireTransfers(String[] paramArrayOfString)
    throws FFSException
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      WireInfo[] arrayOfWireInfo = new WireInfo[1];
      arrayOfWireInfo[0] = new WireInfo();
      arrayOfWireInfo[0].setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "wireId " }, "WIRE_MESSAGE");
      arrayOfWireInfo[0].setStatusMsg(str);
      FFSDebug.log("WireProcessor.getWireTransfers, " + str, 0);
      return arrayOfWireInfo;
    }
    return a(paramArrayOfString, false);
  }
  
  public RecWireInfo[] getRecWireTransfers(String[] paramArrayOfString)
    throws FFSException
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      RecWireInfo[] arrayOfRecWireInfo = new RecWireInfo[1];
      arrayOfRecWireInfo[0] = new RecWireInfo();
      arrayOfRecWireInfo[0].setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "wireId " }, "WIRE_MESSAGE");
      arrayOfRecWireInfo[0].setStatusMsg(str);
      FFSDebug.log("WireProcessor.getRecWireTransfers, " + str, 0);
      return arrayOfRecWireInfo;
    }
    return (RecWireInfo[])a(paramArrayOfString, true);
  }
  
  private WireInfo[] a(String[] paramArrayOfString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "WireProcessor.getWireTransfers(boolean): ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1 = null;
    Object localObject2 = null;
    ArrayList localArrayList = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException("WireProcessor.getWireTransfer: Can not get DB Connection.");
      }
      localArrayList = new ArrayList();
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        if (paramBoolean) {
          localObject2 = new RecWireInfo();
        } else {
          localObject2 = new WireInfo();
        }
        ((WireInfo)localObject2).setSrvrTid(paramArrayOfString[i]);
        localObject2 = Wire.getWireInfo(localFFSConnectionHolder, (WireInfo)localObject2, paramBoolean);
        localArrayList.add(localObject2);
      }
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1, " Done", 6);
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "*** WireProcessor.getWireTransfer failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    if (paramBoolean) {
      localObject1 = (RecWireInfo[])localArrayList.toArray(new RecWireInfo[0]);
    } else {
      localObject1 = (WireInfo[])localArrayList.toArray(new WireInfo[0]);
    }
    return localObject1;
  }
  
  public WireInfo getWireTransfer(String paramString)
    throws FFSException
  {
    if (paramString == null)
    {
      WireInfo localWireInfo = new WireInfo();
      localWireInfo.setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "extId " }, "WIRE_MESSAGE");
      localWireInfo.setStatusMsg(str);
      FFSDebug.log("WireProcessor.getWireTransfer, " + str, 0);
      return localWireInfo;
    }
    return jdMethod_do(paramString, false);
  }
  
  public RecWireInfo getRecWireTransfer(String paramString)
    throws FFSException
  {
    if (paramString == null)
    {
      RecWireInfo localRecWireInfo = new RecWireInfo();
      localRecWireInfo.setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "wireId " }, "WIRE_MESSAGE");
      localRecWireInfo.setStatusMsg(str);
      FFSDebug.log("WireProcessor.getRecWireTransfer, " + str, 0);
      return localRecWireInfo;
    }
    return (RecWireInfo)jdMethod_do(paramString, true);
  }
  
  private WireInfo jdMethod_do(String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "WireProcessor.getWireTransfer(boolean): ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1 = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException("WireProcessor.getWireTransfer: Can not get DB Connection.");
      }
      if (paramBoolean) {
        localObject1 = new RecWireInfo();
      } else {
        localObject1 = new WireInfo();
      }
      ((WireInfo)localObject1).setExtId(paramString);
      localObject1 = Wire.getWireTransfer(localFFSConnectionHolder, (WireInfo)localObject1, paramBoolean);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "*** WireProcessor.getWireTransfer failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, " Done", 6);
    return localObject1;
  }
  
  private void a(RecWireInfo paramRecWireInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.validateRecurringInfo";
    jdMethod_for(paramRecWireInfo, true);
    if (paramRecWireInfo.getStatusCode() != 0) {
      return;
    }
    a(paramRecWireInfo);
    if (paramRecWireInfo.getStatusCode() != 0) {
      return;
    }
    String str2 = paramRecWireInfo.getFrequency();
    if (str2 == null)
    {
      paramRecWireInfo.setStatusCode(19100);
      paramRecWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19100, null, "WIRE_MESSAGE"));
      return;
    }
    int i = FFSUtil.getFreqInt(str2);
    String str3;
    if (i == -1)
    {
      paramRecWireInfo.setStatusCode(17200);
      str3 = BPWLocaleUtil.getMessage(17200, null, "WIRE_MESSAGE");
      paramRecWireInfo.setStatusMsg(str3);
      FFSDebug.log(str1, str3, 0);
      return;
    }
    if (!"RECTEMPLATE".equalsIgnoreCase(paramRecWireInfo.getWireType()))
    {
      str3 = null;
      if (paramRecWireInfo.getPmtsCount() <= 1)
      {
        paramRecWireInfo.setStatusCode(19350);
        paramRecWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19350, null, "WIRE_MESSAGE"));
        return;
      }
      String str4 = paramRecWireInfo.getStartDate();
      if (str4 == null)
      {
        paramRecWireInfo.setStatusCode(19110);
        paramRecWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19110, null, "WIRE_MESSAGE"));
        return;
      }
      if (!BPWUtil.checkDateBeanFormat(str4))
      {
        paramRecWireInfo.setStatusCode(17201);
        String str5 = BPWLocaleUtil.getMessage(17201, null, "WIRE_MESSAGE");
        paramRecWireInfo.setStatusMsg(str5);
        FFSDebug.log(str1, str5, 0);
        return;
      }
      int j = BPWUtil.getDateDBFormat(str4);
      int k = BPWUtil.getTodayDBFormat();
      if (k > j)
      {
        j = k;
        paramRecWireInfo.setStartDate(BPWUtil.getDateBeanFormat(j));
      }
      String str7;
      try
      {
        str3 = paramRecWireInfo.getSettlementDate();
        FFSDebug.log(str1, "settlementDate:", str3, 6);
        if ((str3 != null) && (str3.trim().length() > 0))
        {
          String str6 = DBUtil.getPayday(paramRecWireInfo.getFiID(), str4);
          FFSDebug.log(str1, "dateToPost:", str6, 6);
          if (Integer.parseInt(str6) > Integer.parseInt(str3))
          {
            paramRecWireInfo.setStatusCode(19490);
            paramRecWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19490, null, "WIRE_MESSAGE"));
            return;
          }
        }
      }
      catch (Exception localException)
      {
        str7 = BPWLocaleUtil.getMessage(19480, null, "WIRE_MESSAGE") + " " + str3;
        str8 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str1, str7, str8, 0);
        paramRecWireInfo.setStatusCode(19480);
        paramRecWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19480, null, "WIRE_MESSAGE"));
        return;
      }
      catch (Throwable localThrowable1)
      {
        str7 = "Exception in " + str1;
        String str8 = FFSDebug.stackTrace(localThrowable1);
        FFSDebug.log(str7 + str8, 0);
        paramRecWireInfo.setStatusCode(19200);
        paramRecWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19200, null, "WIRE_MESSAGE"));
        return;
      }
      try
      {
        int m = CommonProcessor.getEndDate(DBUtil.getPayday(paramRecWireInfo.getFiID(), j), i, paramRecWireInfo.getPmtsCount());
        paramRecWireInfo.setEndDate(BPWUtil.getDateBeanFormat(m));
      }
      catch (Throwable localThrowable2)
      {
        paramRecWireInfo.setStatusCode(17202);
        str7 = BPWLocaleUtil.getMessage(17202, new String[] { "" + j }, "WIRE_MESSAGE");
        paramRecWireInfo.setStatusMsg(str7);
        FFSDebug.log(str1, str7, 0);
        return;
      }
    }
    else
    {
      paramRecWireInfo.setStartDate("" + DBUtil.getCurrentStartDate() / 100);
      paramRecWireInfo.setEndDate(paramRecWireInfo.getStartDate());
    }
    paramRecWireInfo.setStatusCode(0);
    paramRecWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
  }
  
  private void a(WireInfo paramWireInfo)
    throws BPWException
  {
    String str1 = "WireProcessor.validateCommonInfo: ";
    if ((paramWireInfo.getFiID() == null) || (paramWireInfo.getFiID().trim().length() == 0))
    {
      paramWireInfo.setStatusCode(16010);
      localObject = BPWLocaleUtil.getMessage(16010, new String[] { "WireInfo", "FIID" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg((String)localObject);
      return;
    }
    Object localObject = null;
    BPWFIInfoProcessor localBPWFIInfoProcessor = new BPWFIInfoProcessor();
    try
    {
      localObject = localBPWFIInfoProcessor.getBPWFIInfo(paramWireInfo.getFiID());
    }
    catch (FFSException localFFSException)
    {
      String str2 = "Error while retrieving BPWFIInfo";
      String str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str1, str2, str3, 0);
      paramWireInfo.setStatusCode(23170);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(23170, null, "WIRE_MESSAGE"));
      return;
    }
    if (((BPWFIInfo)localObject).getStatusCode() == 16020)
    {
      paramWireInfo.setStatusCode(23170);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(23170, null, "WIRE_MESSAGE"));
      return;
    }
    if ((paramWireInfo.getCustomerID() == null) || (paramWireInfo.getCustomerID().trim().length() == 0))
    {
      paramWireInfo.setStatusCode(16010);
      localObject = BPWLocaleUtil.getMessage(16010, new String[] { "WireInfo", "CustomerId" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg((String)localObject);
      return;
    }
    if (paramWireInfo.getSubmittedBy() == null) {
      paramWireInfo.setSubmittedBy(paramWireInfo.getUserId());
    }
    paramWireInfo.setStatusCode(0);
    paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
  }
  
  private void jdMethod_if(WireInfo paramWireInfo)
    throws BPWException
  {
    String str1 = "WireProcessor.validateDueDate: ";
    String str2 = null;
    String str3 = null;
    jdMethod_for(paramWireInfo, false);
    if (paramWireInfo.getStatusCode() != 0) {
      return;
    }
    a(paramWireInfo);
    if (paramWireInfo.getStatusCode() != 0) {
      return;
    }
    String str4 = paramWireInfo.getAmountType();
    if (str4 == null)
    {
      paramWireInfo.setStatusCode(16010);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "WireInfo", "AmountType" }, "WIRE_MESSAGE"));
      return;
    }
    if ((!str4.equalsIgnoreCase("CREDIT")) && (!str4.equalsIgnoreCase("DEBIT")))
    {
      localObject = new StringBuffer();
      ((StringBuffer)localObject).append(BPWLocaleUtil.getMessage(19400, null, "WIRE_MESSAGE")).append("'");
      ((StringBuffer)localObject).append(str4).append("'");
      FFSDebug.log(str1, ((StringBuffer)localObject).toString(), 0);
      paramWireInfo.setStatusCode(19400);
      paramWireInfo.setStatusMsg(((StringBuffer)localObject).toString());
      return;
    }
    Object localObject = paramWireInfo.getDateDue();
    if (localObject == null)
    {
      paramWireInfo.setDateDue("" + DBUtil.getCurrentStartDate() / 100);
    }
    else if (!BPWUtil.checkDateBeanFormat((String)localObject))
    {
      paramWireInfo.setStatusCode(19210);
      String str5 = BPWLocaleUtil.getMessage(19210, null, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str5);
      return;
    }
    FFSDebug.log(str1, " DateDue: ", paramWireInfo.getDateDue(), " FIId: ", paramWireInfo.getFiID(), 6);
    String str6;
    String str7;
    try
    {
      paramWireInfo.setDateToPost(DBUtil.getPayday(paramWireInfo.getFiID(), paramWireInfo.getDateDue()));
      FFSDebug.log(str1, " DateToPost=", paramWireInfo.getDateToPost(), 6);
    }
    catch (Throwable localThrowable)
    {
      str6 = "Exception in " + str1;
      str7 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str6 + str7, 0);
      paramWireInfo.setStatusCode(19200);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19200, null, "WIRE_MESSAGE"));
      return;
    }
    if (!"TEMPLATE".equalsIgnoreCase(paramWireInfo.getWireType())) {
      try
      {
        str2 = paramWireInfo.getSettlementDate();
        FFSDebug.log(str1, "settlementDate:", str2, 6);
        FFSDebug.log(str1, "dueDate:", paramWireInfo.getDateDue(), 6);
        if ((str2 != null) && (str2.trim().length() > 0))
        {
          str3 = paramWireInfo.getDateDue();
          if (Integer.parseInt(str3) > Integer.parseInt(str2))
          {
            paramWireInfo.setStatusCode(19490);
            paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19490, null, "WIRE_MESSAGE"));
            return;
          }
        }
      }
      catch (Exception localException)
      {
        str6 = BPWLocaleUtil.getMessage(19480, null, "WIRE_MESSAGE") + " " + str2;
        str7 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str1, str6, str7, 0);
        paramWireInfo.setStatusCode(19480);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19480, null, "WIRE_MESSAGE"));
        return;
      }
    }
    paramWireInfo.setStatusCode(0);
    paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
  }
  
  private void jdMethod_for(WireInfo paramWireInfo, boolean paramBoolean)
    throws BPWException
  {
    String str1 = "WireProcessor.validateWireType: ";
    String str2 = paramWireInfo.getWireType();
    if (str2 == null)
    {
      paramWireInfo.setStatusCode(19380);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19380, null, "WIRE_MESSAGE"));
      return;
    }
    String str3;
    if (("RECURRING".equalsIgnoreCase(str2)) || ("RECTEMPLATE".equalsIgnoreCase(str2)))
    {
      if (!paramBoolean)
      {
        str3 = BPWLocaleUtil.getMessage(19360, null, "WIRE_MESSAGE") + ":" + str2;
        FFSDebug.log("***", str1, str3, 0);
        paramWireInfo.setStatusCode(19360);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19360, null, "WIRE_MESSAGE"));
      }
    }
    else if (("SINGLE".equalsIgnoreCase(str2)) || ("TEMPLATE".equalsIgnoreCase(str2)))
    {
      if (paramBoolean)
      {
        str3 = BPWLocaleUtil.getMessage(19360, null, "WIRE_MESSAGE") + ":" + str2;
        FFSDebug.log("***", str1, str3, 0);
        paramWireInfo.setStatusCode(19360);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19370, null, "WIRE_MESSAGE"));
      }
    }
    else
    {
      str3 = BPWLocaleUtil.getMessage(19370, null, "WIRE_MESSAGE") + ":" + str2;
      FFSDebug.log("***", str1, str3, 0);
      paramWireInfo.setStatusCode(19370);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19370, null, "WIRE_MESSAGE"));
      return;
    }
    paramWireInfo.setStatusCode(0);
    paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
  }
  
  private void a(WireBatchInfo paramWireBatchInfo)
    throws BPWException
  {
    String str1 = "WireProcessor.validateBatchDates: ";
    String str2 = paramWireBatchInfo.getDateDue();
    if (str2 == null)
    {
      paramWireBatchInfo.setDateDue("" + DBUtil.getCurrentStartDate() / 100);
    }
    else if (!BPWUtil.checkDateBeanFormat(str2))
    {
      paramWireBatchInfo.setStatusCode(19210);
      String str3 = BPWLocaleUtil.getMessage(19210, null, "WIRE_MESSAGE");
      paramWireBatchInfo.setStatusMsg(str3);
      return;
    }
    FFSDebug.log(str1, " DateDue: ", paramWireBatchInfo.getDateDue(), " FIId: ", paramWireBatchInfo.getFIId(), 6);
    try
    {
      paramWireBatchInfo.setDateToPost(DBUtil.getPayday(paramWireBatchInfo.getFIId(), paramWireBatchInfo.getDateDue()));
      FFSDebug.log(str1, " DateToPost=", paramWireBatchInfo.getDateToPost(), 6);
    }
    catch (Throwable localThrowable)
    {
      String str4 = "Exception in " + str1;
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4 + str5, 0);
      paramWireBatchInfo.setStatusCode(19200);
      paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19200, null, "WIRE_MESSAGE"));
      return;
    }
    paramWireBatchInfo.setStatusCode(0);
    paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
  }
  
  public BPWHist getWireHistory(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.getWireHistory : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramBPWHist = Wire.getWireHistory(localFFSConnectionHolder, paramBPWHist, false);
      localFFSConnectionHolder.conn.commit();
      BPWHist localBPWHist = paramBPWHist;
      return localBPWHist;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "WireProcessor.getWireHistory failed " + localThrowable.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWHist getRecWireHistory(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.getRecWireHistory : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramBPWHist = Wire.getWireHistory(localFFSConnectionHolder, paramBPWHist, true);
      localFFSConnectionHolder.conn.commit();
      BPWHist localBPWHist = paramBPWHist;
      return localBPWHist;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "WireProcessor.getRecWireHistory failed " + localThrowable.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWHist getWireHistoryByCustomer(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.getRecWireHistoryByCustomer: start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramBPWHist = Wire.getWireHistoryByCustomer(localFFSConnectionHolder, paramBPWHist, false);
      localFFSConnectionHolder.conn.commit();
      BPWHist localBPWHist = paramBPWHist;
      return localBPWHist;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "WireProcessor.getRecWireHistoryByCustomer failed " + localThrowable.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWHist getRecWireHistoryByCustomer(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.getRecWireHistoryByCustomer : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramBPWHist = Wire.getWireHistoryByCustomer(localFFSConnectionHolder, paramBPWHist, true);
      localFFSConnectionHolder.conn.commit();
      BPWHist localBPWHist = paramBPWHist;
      return localBPWHist;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "WireProcessor.getRecWireHistoryByCustomer failed " + localThrowable.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public static WireInfo[] getAuditWireTransfer(String paramString)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.getAuditWireTransfer : start ", 6);
    WireInfo[] arrayOfWireInfo1 = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    WireInfo[] arrayOfWireInfo2;
    String str;
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      arrayOfWireInfo2 = new WireInfo[1];
      arrayOfWireInfo2[0] = new WireInfo();
      arrayOfWireInfo2[0].setStatusCode(16000);
      str = BPWLocaleUtil.getMessage(16000, new String[] { "srvrTid " }, "WIRE_MESSAGE");
      arrayOfWireInfo2[0].setStatusMsg(str);
      FFSDebug.log("WireProcessor.getAuditWireTransfer, " + str, 0);
      return arrayOfWireInfo2;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfWireInfo1 = Wire.getAuditWireTransfer(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
      arrayOfWireInfo2 = arrayOfWireInfo1;
      return arrayOfWireInfo2;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "WireProcessor.getAuditWireTransfer failed " + localThrowable.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public static WireInfo[] getAuditWireTransferByExtId(String paramString)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.getAuditWireTransfer : start ", 6);
    WireInfo[] arrayOfWireInfo1 = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    WireInfo[] arrayOfWireInfo2;
    String str;
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      arrayOfWireInfo2 = new WireInfo[1];
      arrayOfWireInfo2[0] = new WireInfo();
      arrayOfWireInfo2[0].setStatusCode(16000);
      str = BPWLocaleUtil.getMessage(16000, new String[] { "extId " }, "WIRE_MESSAGE");
      arrayOfWireInfo2[0].setStatusMsg(str);
      FFSDebug.log("WireProcessor.getAuditWireTransfer, " + str, 0);
      return arrayOfWireInfo2;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfWireInfo1 = Wire.getAuditWireTransferByExtId(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
      arrayOfWireInfo2 = arrayOfWireInfo1;
      return arrayOfWireInfo2;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "WireProcessor.getAuditWireTransfer failed " + localThrowable.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public static WireReleaseInfo getWireReleaseCount(WireReleaseInfo paramWireReleaseInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.getWireReleaseCount:";
    FFSDebug.log(str1, " start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    if (paramWireReleaseInfo == null)
    {
      FFSDebug.log(str1, "failed, Null WireReleaseInfo object is passed", 0);
      return null;
    }
    Object localObject1;
    if ((paramWireReleaseInfo.getFIId() == null) || (paramWireReleaseInfo.getFIId().length() == 0))
    {
      paramWireReleaseInfo.setStatusCode(16000);
      localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "FIID " }, "WIRE_MESSAGE");
      paramWireReleaseInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1, " failed:", (String)localObject1, 0);
      return paramWireReleaseInfo;
    }
    if ((paramWireReleaseInfo.getCustomerId() == null) || (paramWireReleaseInfo.getCustomerId().length() == 0))
    {
      paramWireReleaseInfo.setStatusCode(16000);
      localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "CustomerId " }, "WIRE_MESSAGE");
      paramWireReleaseInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1, " failed:", (String)localObject1, 0);
      return paramWireReleaseInfo;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      Wire.getWireReleaseCount(localFFSConnectionHolder, paramWireReleaseInfo);
      localFFSConnectionHolder.conn.commit();
      localObject1 = paramWireReleaseInfo;
      return localObject1;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = str1 + " failed " + localThrowable.toString();
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, String paramString, boolean paramBoolean)
    throws Exception
  {
    String str1 = "WireProcessor.logWireActivity";
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    AuditLogRecord localAuditLogRecord = null;
    str3 = paramWireInfo.getWireType();
    str2 = paramWireInfo.getPrcStatus();
    int i = 4051;
    String str6;
    if ("Add".equalsIgnoreCase(paramString))
    {
      i = 4051;
      if (paramBoolean)
      {
        if ((str3 != null) && ((str3.equalsIgnoreCase("TEMPLATE")) || (str3.equalsIgnoreCase("RECTEMPLATE")))) {
          str4 = "A recurring wire template has been added successfully.";
        } else {
          str4 = "A recurring wire transfer has been added successfully.";
        }
      }
      else if ((str3 != null) && (str3.equalsIgnoreCase("SINGLE"))) {
        str4 = "A single wire transfer has been added successfully.";
      } else if ((str3 != null) && ((str3.equalsIgnoreCase("TEMPLATE")) || (str3.equalsIgnoreCase("RECTEMPLATE")))) {
        str4 = "A wire transfer template has been added successfully.";
      } else {
        str4 = "A single wire transfer for the recurring model has been added successfully.";
      }
      str5 = paramWireInfo.getUserId();
    }
    else if ("Mod".equalsIgnoreCase(paramString))
    {
      i = 4052;
      paramWireInfo.setPrcStatus("MODIFIED");
      if (paramBoolean)
      {
        if ((str3 != null) && ((str3.equalsIgnoreCase("TEMPLATE")) || (str3.equalsIgnoreCase("RECTEMPLATE")))) {
          str4 = "A recurring wire template has been modified successfully.";
        } else {
          str4 = "A recurring wire transfer has been modified successfully.";
        }
      }
      else if ((str3 != null) && (str3.equalsIgnoreCase("SINGLE"))) {
        str4 = "A single wire transfer has been modified successfully.";
      } else if ((str3 != null) && ((str3.equalsIgnoreCase("TEMPLATE")) || (str3.equalsIgnoreCase("RECTEMPLATE")))) {
        str4 = "A wire transfer template has been modified successfully.";
      } else {
        str4 = "A single wire transfer for the recurring model has been modified successfully.";
      }
    }
    else if ("Can".equalsIgnoreCase(paramString))
    {
      i = 4053;
      if (paramBoolean)
      {
        if ((str3 != null) && ((str3.equalsIgnoreCase("TEMPLATE")) || (str3.equalsIgnoreCase("RECTEMPLATE")))) {
          str4 = "A recurring wire template has been deleted successfully.";
        } else {
          str4 = "A recurring wire transfer has been deleted successfully.";
        }
      }
      else if ((str3 != null) && (str3.equalsIgnoreCase("SINGLE"))) {
        str4 = "A single wire transfer has been deleted successfully.";
      } else if ((str3 != null) && ((str3.equalsIgnoreCase("TEMPLATE")) || (str3.equalsIgnoreCase("RECTEMPLATE")))) {
        str4 = "A wire transfer template has been deleted successfully.";
      } else {
        str4 = "A single wire transfer for the recurring model has been deleted successfully.";
      }
    }
    else if ("Rel".equalsIgnoreCase(paramString))
    {
      i = 4054;
      if (str2.equals("RELEASED")) {
        str4 = "A single wire transfer has been released.";
      } else if (str2.equals("RELEASEPENDING")) {
        str4 = "A single wire transfer is pending release.";
      } else if (str2.equals("IMMED_INPROCESS")) {
        str4 = "Wire will be immediately processed by backend.";
      } else if (str2.equals("WILLPROCESSON")) {
        str4 = "A single wire transfer is scheduled, it will be processed by backend.";
      } else if (str2.equals("RELEASEREJECTED")) {
        str4 = "A single wire transfer was rejected release.";
      } else if (str2.equals("RELEASEFAILED")) {
        str4 = "A single wire transfer has failed release.";
      }
    }
    else if ("AddDup".equalsIgnoreCase(paramString))
    {
      i = 4055;
      paramWireInfo.setPrcStatus("DUPLICATE");
      if ((str3 != null) && ((str3.equalsIgnoreCase("TEMPLATE")) || (str3.equalsIgnoreCase("RECTEMPLATE")))) {
        str4 = "Transaction Id for the template addition is duplicate";
      } else if (paramBoolean) {
        str4 = "Transaction Id for the recurring wire transfer addition is duplicate";
      } else {
        str4 = "Transaction Id for the single wire transfer addition is duplicate";
      }
      str5 = paramWireInfo.getUserId();
    }
    else if ("ModDup".equalsIgnoreCase(paramString))
    {
      i = 4056;
      paramWireInfo.setPrcStatus("DUPLICATE");
      if ((str3 != null) && ((str3.equalsIgnoreCase("TEMPLATE")) || (str3.equalsIgnoreCase("RECTEMPLATE")))) {
        str4 = "Transaction Id for the template modification is duplicate";
      } else if (paramBoolean) {
        str4 = "Transaction Id for the recurring wire transfer modification is duplicate";
      } else {
        str4 = "Transaction Id for the single wire transfer modification is duplicate";
      }
    }
    else if ("CanDup".equalsIgnoreCase(paramString))
    {
      i = 4057;
      paramWireInfo.setPrcStatus("DUPLICATE");
      if ((str3 != null) && ((str3.equalsIgnoreCase("TEMPLATE")) || (str3.equalsIgnoreCase("RECTEMPLATE")))) {
        str4 = "Transaction Id for the template deletion is duplicate";
      } else if (paramBoolean) {
        str4 = "Transaction Id for the recurring wire transfer deletion is duplicate";
      } else {
        str4 = "Transaction Id for the single wire transfer deletion is duplicate";
      }
    }
    else if ("RelDup".equalsIgnoreCase(paramString))
    {
      i = 4058;
      paramWireInfo.setPrcStatus("DUPLICATE");
      str4 = "Transaction Id for the single wire transfer release is duplicate";
    }
    else if ("ProcessApprvlRslt".equalsIgnoreCase(paramString))
    {
      i = 4051;
      str6 = paramWireInfo.getPrcStatus();
      if ((str6.equals("CREATED")) || (str6.equals("TEMPLATE")) || (str6.equals("RECTEMPLATE"))) {
        str4 = "The wire transfer is being approved.";
      } else if (str6.equals("APPROVAL_REJECTED")) {
        str4 = "The wire transfer is being rejected.";
      }
    }
    if (str5 == null) {
      str5 = paramWireInfo.getSubmittedBy();
    }
    if (str5 == null) {
      str5 = "";
    }
    FFSDebug.log("logWireActivity: userId ", str5, 6);
    try
    {
      str6 = null;
      str7 = null;
      String str8 = paramWireInfo.getAmount();
      String str9 = null;
      int j = 0;
      if ((str8 == null) || (str8.trim().length() == 0)) {
        str8 = "-1";
      }
      if (paramWireInfo.getWirePayeeInfo() != null)
      {
        if ((paramWireInfo.getWireDest() != null) && (paramWireInfo.getWireDest().equals("HOST"))) {
          str6 = "HOST";
        } else {
          str6 = paramWireInfo.getWirePayeeInfo().buildBankAcctId();
        }
        if (paramWireInfo.getWirePayeeInfo().getBeneficiaryBankInfo() != null) {
          str7 = paramWireInfo.getWirePayeeInfo().getBeneficiaryBankInfo().getFedRTN();
        }
      }
      if ((paramWireInfo.getWireDest() != null) && (paramWireInfo.getWireDest().equals("HOST"))) {
        str9 = "HOST";
      } else {
        str9 = paramWireInfo.buildFromAcctId();
      }
      FFSDebug.log("logWireActivity: fromAcctId" + str9, 6);
      if ((paramWireInfo.getCustomerID().equals(paramWireInfo.getUserId())) || (paramWireInfo.getCustomerID().equals(paramWireInfo.getSubmittedBy())))
      {
        j = 0;
        FFSDebug.log("logWireActivity: Consumer", 6);
      }
      else
      {
        j = Integer.parseInt(paramWireInfo.getCustomerID());
        FFSDebug.log("logWireActivity: Business", 6);
      }
      String str10 = paramWireInfo.getWireDest();
      if ((str10 != null) && (str10.equals("DRAWDOWN"))) {
        localAuditLogRecord = new AuditLogRecord(str5, paramWireInfo.getAgentId(), paramWireInfo.getAgentType(), str4, paramWireInfo.getExtId(), i, j, new BigDecimal(str8), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramWireInfo.getPrcStatus(), str9, paramWireInfo.getFromBankId(), str6, str7, -1);
      } else {
        localAuditLogRecord = new AuditLogRecord(str5, paramWireInfo.getAgentId(), paramWireInfo.getAgentType(), str4, paramWireInfo.getExtId(), i, j, new BigDecimal(str8), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramWireInfo.getPrcStatus(), str6, str7, str9, paramWireInfo.getFromBankId(), -1);
      }
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      String str7 = str1 + " failed " + localNumberFormatException.toString();
      FFSDebug.log(str7 + FFSDebug.stackTrace(localNumberFormatException), 0);
      throw new FFSException(localNumberFormatException, str7);
    }
    paramWireInfo.setPrcStatus(str2);
  }
  
  private void a(WireInfo paramWireInfo, String paramString1, String paramString2, boolean paramBoolean)
  {
    String str1 = "WireProcessor.logWireActivity";
    FFSConnectionHolder localFFSConnectionHolder = null;
    AuditLogRecord localAuditLogRecord = null;
    String str2 = paramString2;
    String str3 = null;
    int i = paramWireInfo.getStatusCode();
    int j = 0;
    String str4 = paramWireInfo.getWireType();
    int k = 4051;
    if ("Add".equalsIgnoreCase(paramString1))
    {
      k = 4051;
      if (paramString2 == null) {
        if ((str4 != null) && ((str4.equalsIgnoreCase("TEMPLATE")) || (str4.equalsIgnoreCase("RECTEMPLATE")))) {
          str2 = "Add wire/recurring-wire template failed, ";
        } else {
          str2 = "Add wire failed, ";
        }
      }
      str3 = paramWireInfo.getUserId();
    }
    else if ("Mod".equalsIgnoreCase(paramString1))
    {
      k = 4052;
      if (paramString2 == null) {
        if ((str4 != null) && ((str4.equalsIgnoreCase("TEMPLATE")) || (str4.equalsIgnoreCase("RECTEMPLATE")))) {
          str2 = "Modify wire/recurring-wire template failed, ";
        } else {
          str2 = "Modify wire failed, ";
        }
      }
    }
    else if ("Can".equalsIgnoreCase(paramString1))
    {
      k = 4053;
      if (paramString2 == null) {
        if ((str4 != null) && ((str4.equalsIgnoreCase("TEMPLATE")) || (str4.equalsIgnoreCase("RECTEMPLATE")))) {
          str2 = "Delete wire/recurring-wire template failed, ";
        } else {
          str2 = "Delete wire failed, ";
        }
      }
    }
    else if ("Rel".equalsIgnoreCase(paramString1))
    {
      k = 4054;
      if (paramString2 == null) {
        str2 = "Release wire failed, ";
      }
    }
    if (str3 == null) {
      str3 = paramWireInfo.getSubmittedBy();
    }
    if (paramString2 == null) {
      switch (i)
      {
      case 19120: 
        str2 = str2 + "invalid payee Id.";
        break;
      case 23170: 
        str2 = str2 + "invalid FI Id.";
        break;
      case 19130: 
        str2 = str2 + "invalid Customer Id.";
        break;
      case 19270: 
        str2 = str2 + "unable to save extra info.";
        break;
      case 19380: 
        str2 = str2 + "wire type is missing.";
        break;
      case 19360: 
        str2 = str2 + "invalid wire type.";
        break;
      case 19370: 
        str2 = str2 + "unknown wire type.";
        break;
      case 19210: 
        str2 = str2 + "invalid format of due date.";
        break;
      case 19490: 
        str2 = str2 + "settlement date is before due date.";
        break;
      case 19480: 
        str2 = str2 + "invalid settlement date.";
        break;
      case 19100: 
        str2 = str2 + "frequency is missing.";
        break;
      case 17200: 
        str2 = str2 + "invalid frequency.";
        break;
      case 19350: 
        str2 = str2 + "invalid number of payments.";
        break;
      case 19110: 
        str2 = str2 + "start date is missing.";
        break;
      case 17201: 
        str2 = str2 + "invalid start date.";
        break;
      case 19200: 
        str2 = str2 + "error occurred while calculating pay day.";
        break;
      case 17202: 
        str2 = str2 + "error calculating end date.";
        break;
      case 19500: 
        str2 = str2 + "wire is not in required state.";
        break;
      case 19180: 
        str2 = str2 + "Remaining instances count of this recurring wire is less than the instance count in request.";
        break;
      default: 
        if ((paramWireInfo.getStatusMsg() != null) && (paramWireInfo.getStatusMsg().length() > 0)) {
          str2 = str2 + paramWireInfo.getStatusMsg();
        } else {
          str2 = str2 + "due to unknown error.";
        }
        break;
      }
    }
    try
    {
      String str5 = null;
      str6 = null;
      String str7 = paramWireInfo.getAmount();
      String str8 = null;
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        String str9 = str1 + "Can not get DB Connection.";
        FFSDebug.log(str9, 0);
      }
      if ((str7 == null) || (str7.trim().length() == 0)) {
        str7 = "-1";
      }
      if (paramWireInfo.getWirePayeeInfo() != null)
      {
        if ((paramWireInfo.getWireDest() != null) && (paramWireInfo.getWireDest().equals("HOST"))) {
          str5 = "HOST";
        } else {
          str5 = paramWireInfo.getWirePayeeInfo().buildBankAcctId();
        }
        if (paramWireInfo.getWirePayeeInfo().getBeneficiaryBankInfo() != null) {
          str6 = paramWireInfo.getWirePayeeInfo().getBeneficiaryBankInfo().getFedRTN();
        }
      }
      if ((paramWireInfo.getWireDest() != null) && (paramWireInfo.getWireDest().equals("HOST"))) {
        str8 = "HOST";
      } else {
        str8 = paramWireInfo.buildFromAcctId();
      }
      if ((paramWireInfo.getCustomerID() != null) && (paramWireInfo.getCustomerID().length() > 0)) {
        if ((paramWireInfo.getCustomerID().equals(paramWireInfo.getUserId())) || (paramWireInfo.getCustomerID().equals(paramWireInfo.getSubmittedBy()))) {
          j = 0;
        } else {
          try
          {
            j = Integer.parseInt(paramWireInfo.getCustomerID());
          }
          catch (Exception localException2)
          {
            j = 0;
          }
        }
      }
      String str10 = paramWireInfo.getWireDest();
      if ((str10 != null) && (str10.equals("DRAWDOWN"))) {
        localAuditLogRecord = new AuditLogRecord(str3, paramWireInfo.getAgentId(), paramWireInfo.getAgentType(), str2, paramWireInfo.getExtId(), k, j, new BigDecimal(str7), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramWireInfo.getPrcStatus(), str8, paramWireInfo.getFromBankId(), str5, str6, -1);
      } else {
        localAuditLogRecord = new AuditLogRecord(str3, paramWireInfo.getAgentId(), paramWireInfo.getAgentType(), str2, paramWireInfo.getExtId(), k, j, new BigDecimal(str7), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramWireInfo.getPrcStatus(), str5, str6, str8, paramWireInfo.getFromBankId(), -1);
      }
      TransAuditLog.logTransAuditLog(localAuditLogRecord, localFFSConnectionHolder.conn.getConnection());
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      String str6 = str1 + " failed " + localException1.toString();
      FFSDebug.log(str6 + FFSDebug.stackTrace(localException1), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private ArrayList jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, WireInfo[] paramArrayOfWireInfo)
    throws Exception
  {
    FFSDebug.log("WireProcessor.doImmediateFundsApproval: start", 6);
    String str1 = "WireProcessor.doImmediateFundsApproval";
    WireApproval localWireApproval = new WireApproval();
    ArrayList localArrayList = new ArrayList();
    String str2 = DBConnCache.save(paramFFSConnectionHolder);
    for (int i = 0; i < paramArrayOfWireInfo.length; i++)
    {
      paramArrayOfWireInfo[i].setDbTransKey(str2);
      paramArrayOfWireInfo[i].setEventSequence(1);
      paramArrayOfWireInfo[i].setPossibleDuplicate(false);
    }
    WireInfo[] arrayOfWireInfo1 = null;
    String str3;
    try
    {
      FFSDebug.log(str1, ": Calling Approval backend to process immediate wires", 6);
      arrayOfWireInfo1 = localWireApproval.processWireApprovals(paramArrayOfWireInfo, null);
      FFSDebug.log(str1, ": Approval backend completed processing immediate wires", 6);
    }
    catch (Throwable localThrowable1)
    {
      str3 = "WireProcessor.doImmediateFundsApproval: Failed to approve  wire count: " + paramArrayOfWireInfo.length + " Error: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str3, 0);
      throw new FFSException(str3);
    }
    try
    {
      for (int j = 0; j < arrayOfWireInfo1.length; j++) {
        arrayOfWireInfo1[j].setDbTransKey(str2);
      }
      FFSDebug.log(str1, ": Calling Wire Approval Result processor to process immediate wires", 6);
      WireApprovalRsltProcessor.processWireApprovalRsltImmediate(arrayOfWireInfo1);
      FFSDebug.log(str1, ": Approval Result processor completed processing immediate wires", 6);
      for (j = 0; j < arrayOfWireInfo1.length; j++) {
        if ("FUNDSAPPROVED".equalsIgnoreCase(arrayOfWireInfo1[j].getPrcStatus()))
        {
          FFSDebug.log("WireProcessor.doImmediateFundsApproval adding succesful wire  to the list: " + arrayOfWireInfo1[j], 6);
          localArrayList.add(arrayOfWireInfo1[j]);
        }
      }
    }
    catch (Throwable localThrowable2)
    {
      str3 = "WireProcessor.doImmediateFundsApproval: Failed to process apprval result for wire count: " + arrayOfWireInfo1.length + " Error: " + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str3, 0);
      FFSDebug.log(str1, ": Reverting funds approval for failed wires", 6);
      for (int k = 0; k < arrayOfWireInfo1.length; k++) {
        if ("FUNDSAPPROVED".equalsIgnoreCase(arrayOfWireInfo1[k].getPrcStatus()))
        {
          FFSDebug.log("WireProcessor.doImmediateFundsApproval reverting fund approval for wire: " + arrayOfWireInfo1[k], 6);
          localArrayList.add(arrayOfWireInfo1[k]);
        }
      }
      WireInfo[] arrayOfWireInfo2 = (WireInfo[])localArrayList.toArray(new WireInfo[0]);
      if ((arrayOfWireInfo2 != null) && (arrayOfWireInfo2.length > 0))
      {
        localWireApproval.revertWireApproval(arrayOfWireInfo2, new Hashtable());
        FFSDebug.log("WireProcessor.doImmediateFundsApproval Fund reverted for wires: " + arrayOfWireInfo2.length, 6);
      }
      throw new FFSException(str3);
    }
    finally
    {
      DBConnCache.unbind(str2);
    }
    FFSDebug.log("WireProcessor.doImmediateFundsApproval: end", 6);
    return localArrayList;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, WireInfo[] paramArrayOfWireInfo)
    throws Exception
  {
    FFSDebug.log("WireProcessor.doImmediateBackendProcessing: start", 6);
    String str1 = "WireProcessor.doImmediateBackendProcessing";
    WireBackendHandler localWireBackendHandler = new WireBackendHandler();
    Hashtable localHashtable = null;
    WireInfo[] arrayOfWireInfo = null;
    String str2 = DBConnCache.save(paramFFSConnectionHolder);
    for (int i = 0; i < paramArrayOfWireInfo.length; i++) {
      paramArrayOfWireInfo[i].setDbTransKey(str2);
    }
    try
    {
      FFSDebug.log(str1, ": calling backend handler to process immediate wires", 6);
      arrayOfWireInfo = localWireBackendHandler.processWiresImmediate(paramArrayOfWireInfo, localHashtable);
      FFSDebug.log(str1, ": backend handler processed immediate wires", 6);
      WireBackendRsltProcessor localWireBackendRsltProcessor = new WireBackendRsltProcessor();
      FFSDebug.log(str1, ": calling backend result processor to process immediate wires", 6);
      localWireBackendRsltProcessor.processRslt(arrayOfWireInfo);
      FFSDebug.log(str1, ": backend result processor processed immediate wires", 6);
    }
    catch (Throwable localThrowable)
    {
      String str3 = "WireProcessor.doImmediateBackendProcessing: Failed. Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      DBConnCache.unbind(str2);
    }
    FFSDebug.log("WireProcessor.doImmediateBackendProcessing: end", 6);
  }
  
  private boolean jdMethod_if(WireInfo paramWireInfo, boolean paramBoolean)
    throws BPWException
  {
    String str1 = null;
    int i = 0;
    int j = 0;
    String str2 = null;
    if (paramWireInfo == null)
    {
      paramWireInfo = new WireInfo();
      paramWireInfo.setStatusCode(16000);
      localObject = BPWLocaleUtil.getMessage(16000, new String[] { "WireInfo" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg((String)localObject);
      return false;
    }
    str1 = paramWireInfo.getWireType();
    if ((str1 == null) || (str1.trim().length() == 0))
    {
      paramWireInfo.setStatusCode(16010);
      localObject = BPWLocaleUtil.getMessage(16010, new String[] { "WireInfo", "WireType" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg((String)localObject);
      return false;
    }
    if ((str1.trim().equalsIgnoreCase("TEMPLATE")) || (str1.trim().equalsIgnoreCase("RECTEMPLATE"))) {
      i = 1;
    }
    str2 = paramWireInfo.getWireSource();
    if ((str2 != null) && (str2.trim().equalsIgnoreCase("HOST"))) {
      j = 1;
    }
    if ((j != 0) && (((paramWireInfo.getWirePayeeId() != null) && (paramWireInfo.getWirePayeeId().trim().length() > 0)) || (paramWireInfo.getWirePayeeInfo() != null)))
    {
      paramWireInfo.setStatusCode(19440);
      localObject = BPWLocaleUtil.getMessage(19440, new String[] { "WirePayeeId/WirePayeeInfo" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg((String)localObject);
      return false;
    }
    if ((j == 0) && (i == 0))
    {
      if (((paramWireInfo.getWirePayeeId() == null) || (paramWireInfo.getWirePayeeId().trim().length() == 0)) && (paramWireInfo.getWirePayeeInfo() == null))
      {
        paramWireInfo.setStatusCode(16010);
        localObject = BPWLocaleUtil.getMessage(16010, new String[] { "WireInfo", "PayeeId, WirePayeeInfo" }, "WIRE_MESSAGE");
        paramWireInfo.setStatusMsg((String)localObject);
        return false;
      }
      if ((paramWireInfo.getFromBankId() == null) || (paramWireInfo.getFromBankId().trim().length() == 0))
      {
        paramWireInfo.setStatusCode(16010);
        localObject = BPWLocaleUtil.getMessage(16010, new String[] { "WireInfo", "FromBankId" }, "WIRE_MESSAGE");
        paramWireInfo.setStatusMsg((String)localObject);
        return false;
      }
      if ((paramWireInfo.getFromAcctId() == null) || (paramWireInfo.getFromAcctId().trim().length() == 0))
      {
        paramWireInfo.setStatusCode(16010);
        localObject = BPWLocaleUtil.getMessage(16010, new String[] { "WireInfo", "FromAcctId" }, "WIRE_MESSAGE");
        paramWireInfo.setStatusMsg((String)localObject);
        return false;
      }
      if ((paramWireInfo.getFromAcctType() == null) || (paramWireInfo.getFromAcctType().trim().length() == 0))
      {
        paramWireInfo.setStatusCode(16010);
        localObject = BPWLocaleUtil.getMessage(16010, new String[] { "WireInfo", " FromAcctType" }, "WIRE_MESSAGE");
        paramWireInfo.setStatusMsg((String)localObject);
        return false;
      }
    }
    String str3;
    if (paramBoolean)
    {
      localObject = (RecWireInfo)paramWireInfo;
      if ((((RecWireInfo)localObject).getStartDate() == null) || (((RecWireInfo)localObject).getStartDate().trim().length() == 0))
      {
        ((RecWireInfo)localObject).setStatusCode(16010);
        str3 = BPWLocaleUtil.getMessage(16010, new String[] { "RecWireInfo", " StartDate" }, "WIRE_MESSAGE");
        ((RecWireInfo)localObject).setStatusMsg(str3);
        return false;
      }
    }
    else if ((paramWireInfo.getDateDue() == null) || (paramWireInfo.getDateDue().trim().length() == 0))
    {
      paramWireInfo.setStatusCode(16010);
      localObject = BPWLocaleUtil.getMessage(16010, new String[] { "WireInfo", " DateDue" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg((String)localObject);
      return false;
    }
    if ((paramWireInfo.getCustomerID() == null) || (paramWireInfo.getCustomerID().trim().length() == 0))
    {
      paramWireInfo.setStatusCode(16010);
      localObject = BPWLocaleUtil.getMessage(16010, new String[] { "WireInfo", " CustomerID" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg((String)localObject);
      return false;
    }
    Object localObject = paramWireInfo.getAmount();
    String str5;
    if ((localObject == null) || (((String)localObject).trim().length() == 0))
    {
      if (i != 0)
      {
        paramWireInfo.setAmount("0");
      }
      else
      {
        paramWireInfo.setStatusCode(16010);
        str3 = BPWLocaleUtil.getMessage(16010, new String[] { "WireInfo", " Amount" }, "WIRE_MESSAGE");
        paramWireInfo.setStatusMsg(str3);
        return false;
      }
    }
    else {
      try
      {
        if (FFSUtil.isNegative((String)localObject))
        {
          str3 = BPWLocaleUtil.getMessage(24720, new String[] { localObject }, "WIRE_MESSAGE");
          paramWireInfo.setStatusCode(24720);
          paramWireInfo.setStatusMsg(str3);
          return false;
        }
      }
      catch (Exception localException)
      {
        str5 = BPWLocaleUtil.getMessage(23920, new String[] { localObject }, "WIRE_MESSAGE");
        paramWireInfo.setStatusCode(23920);
        paramWireInfo.setStatusMsg(str5);
        return false;
      }
    }
    if (i == 0)
    {
      str4 = paramWireInfo.getWireDest();
      if ((str4 == null) || (str4.trim().length() == 0))
      {
        if (j != 0)
        {
          paramWireInfo.setWireDest("DOMESTIC");
        }
        else
        {
          paramWireInfo.setStatusCode(16010);
          str5 = BPWLocaleUtil.getMessage(16010, new String[] { "WireInfo", " WireDest" }, "WIRE_MESSAGE");
          paramWireInfo.setStatusMsg(str5);
          return false;
        }
      }
      else {
        paramWireInfo.setWireDest(paramWireInfo.getWireDest().toUpperCase());
      }
    }
    String str4 = null;
    str4 = paramWireInfo.getWireChargesDetails();
    if ((str4 == null) || (str4.trim().length() == 0)) {
      paramWireInfo.setWireChargesDetails("OUR");
    }
    if ((paramWireInfo.getExtId() == null) || (paramWireInfo.getExtId().trim().length() == 0))
    {
      paramWireInfo.setStatusCode(16010);
      str5 = BPWLocaleUtil.getMessage(16010, new String[] { "WireInfo", " ExtId " }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str5);
      return false;
    }
    return true;
  }
  
  private WireInfo jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws Exception
  {
    String str1 = "WireProcessor.doImplicitPayeeAdd: ";
    WirePayeeInfo localWirePayeeInfo = null;
    if (paramWireInfo == null)
    {
      paramWireInfo = new WireInfo();
      paramWireInfo.setStatusCode(16000);
      str2 = BPWLocaleUtil.getMessage(16000, new String[] { "WireInfo" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str2);
      return paramWireInfo;
    }
    if (paramFFSConnectionHolder == null)
    {
      paramWireInfo.setStatusCode(16000);
      str2 = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str2);
      return paramWireInfo;
    }
    localWirePayeeInfo = paramWireInfo.getWirePayeeInfo();
    Object localObject;
    String str3;
    if ((paramWireInfo.getWirePayeeId() != null) && (paramWireInfo.getWirePayeeId().trim().length() > 0)) {
      if ((paramWireInfo.getWireSource() != null) && ((paramWireInfo.getWireSource().equalsIgnoreCase("TEMPLATE")) || (paramWireInfo.getWireSource().equalsIgnoreCase("RECTEMPLATE"))) && (localWirePayeeInfo != null))
      {
        FFSDebug.log(str1, "Templated wire ****", 6);
        str2 = localWirePayeeInfo.getPayeeGroup();
        if (("UNMANAGED".equals(str2)) || ("SECURE".equals(str2)))
        {
          FFSDebug.log(str1, "Unmanaged Payee *****", 6);
          localObject = WirePayee.getWirePayeeInfo(paramFFSConnectionHolder, paramWireInfo.getWirePayeeId(), false);
          if (localWirePayeeInfo.equals(localObject))
          {
            FFSDebug.log(str1, "PayeeInfo is same as in database", 6);
            paramWireInfo.setStatusCode(0);
            paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
            return paramWireInfo;
          }
          str3 = BPWTrackingIDGenerator.getNextId();
          localWirePayeeInfo.setExtId(str3);
          FFSDebug.log(str1, "PayeeInfo is different from the same in DB ***", 6);
          FFSDebug.log(str1, "Creating new PayeeInfo with ExtId = " + str3, 6);
        }
        else
        {
          FFSDebug.log(str1, "Managed Payee *****", 6);
          paramWireInfo.setStatusCode(0);
          paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
          return paramWireInfo;
        }
      }
      else
      {
        FFSDebug.log(str1, "Non Templated wire ****", 6);
        paramWireInfo.setStatusCode(0);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        return paramWireInfo;
      }
    }
    if (localWirePayeeInfo == null)
    {
      paramWireInfo.setStatusCode(0);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      return paramWireInfo;
    }
    String str2 = A.otherProperties.getProperty("bpw.wire.payee.add.implicit", "false");
    if (str2.equalsIgnoreCase("false"))
    {
      localObject = BPWLocaleUtil.getMessage(19140, null, "WIRE_MESSAGE");
      paramWireInfo.setStatusCode(19140);
      paramWireInfo.setStatusMsg((String)localObject);
      return paramWireInfo;
    }
    FFSDebug.log("WireProcessor.doImplicitPayeeAdd: validateWireDest flag: ", this.D, 6);
    if (this.D.equals("Y"))
    {
      Wire.validatePayeeDestWireDestCombination(paramWireInfo, localWirePayeeInfo);
      if (paramWireInfo.getStatusCode() != 0) {
        return paramWireInfo;
      }
    }
    try
    {
      localWirePayeeInfo = WirePayee.create(paramFFSConnectionHolder, localWirePayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      str3 = "***WireProcessor.doImplicitPayeeAdd failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
    FFSDebug.log("***WireProcessor.doImplicitPayeeAdd payee Status code:" + localWirePayeeInfo.getStatusCode(), 6);
    if ((localWirePayeeInfo.getStatusCode() == 23330) || (localWirePayeeInfo.getStatusCode() == 0))
    {
      paramWireInfo.setWirePayeeId(localWirePayeeInfo.getPayeeId());
      paramWireInfo.setStatusCode(0);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    }
    else
    {
      paramWireInfo.setStatusCode(localWirePayeeInfo.getStatusCode());
      paramWireInfo.setStatusMsg(localWirePayeeInfo.getStatusMsg());
    }
    if ((localWirePayeeInfo.getStatusCode() == 0) && (this.B >= 3)) {
      WirePayeeProcessor.logAudit(paramFFSConnectionHolder, localWirePayeeInfo, "Add");
    }
    return paramWireInfo;
  }
  
  private WireInfo jdMethod_new(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws Exception
  {
    WirePayeeInfo localWirePayeeInfo = null;
    String str1;
    if (paramWireInfo == null)
    {
      paramWireInfo = new WireInfo();
      paramWireInfo.setStatusCode(16000);
      str1 = BPWLocaleUtil.getMessage(16000, new String[] { "WireInfo" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str1);
      return paramWireInfo;
    }
    if (paramFFSConnectionHolder == null)
    {
      paramWireInfo.setStatusCode(16000);
      str1 = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str1);
      return paramWireInfo;
    }
    localWirePayeeInfo = paramWireInfo.getWireCreditInfo();
    try
    {
      localWirePayeeInfo = WirePayee.create(paramFFSConnectionHolder, localWirePayeeInfo);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "***WireProcessor.saveWireCreditInfo failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, str2);
    }
    FFSDebug.log("***WireProcessor.saveWireCreditInfo payee Status code:" + localWirePayeeInfo.getStatusCode(), 6);
    if ((localWirePayeeInfo.getStatusCode() == 23330) || (localWirePayeeInfo.getStatusCode() == 0))
    {
      paramWireInfo.setWireCreditId(localWirePayeeInfo.getPayeeId());
      paramWireInfo.setStatusCode(0);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    }
    else
    {
      paramWireInfo.setStatusCode(localWirePayeeInfo.getStatusCode());
      paramWireInfo.setStatusMsg(localWirePayeeInfo.getStatusMsg());
    }
    if ((localWirePayeeInfo.getStatusCode() == 0) && (this.B >= 3)) {
      WirePayeeProcessor.logAudit(paramFFSConnectionHolder, localWirePayeeInfo, "Add");
    }
    return paramWireInfo;
  }
  
  public WireBatchInfo addWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.addWireTransferBatch:";
    FFSDebug.log(str1, " Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder1 = null;
    FFSConnectionHolder localFFSConnectionHolder2 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    WireInfo[] arrayOfWireInfo1 = null;
    int i = 0;
    BigDecimal localBigDecimal1 = new BigDecimal(0.0D);
    BigDecimal localBigDecimal2 = new BigDecimal(0.0D);
    BigDecimal localBigDecimal3 = new BigDecimal(0.0D);
    String str5 = null;
    String str6 = null;
    ArrayList localArrayList = null;
    if (paramWireBatchInfo == null)
    {
      paramWireBatchInfo = new WireBatchInfo();
      paramWireBatchInfo.setStatusCode(16000);
      String str7 = BPWLocaleUtil.getMessage(16000, new String[] { "WireBatchInfo" }, "WIRE_MESSAGE");
      paramWireBatchInfo.setStatusMsg(str7);
      FFSDebug.log("WireProcessor.addWireTransferBatch, " + str7, 0);
      return paramWireBatchInfo;
    }
    if (!a(paramWireBatchInfo, false))
    {
      FFSDebug.log("***", str1, "failed:", paramWireBatchInfo.getStatusMsg(), 0);
      if (this.B >= 1) {
        a(paramWireBatchInfo, "Add", null);
      }
      return paramWireBatchInfo;
    }
    a(paramWireBatchInfo);
    if (paramWireBatchInfo.getStatusCode() != 0)
    {
      FFSDebug.log("***", str1, "failed:", paramWireBatchInfo.getStatusMsg(), 0);
      if (this.B >= 1) {
        a(paramWireBatchInfo, "Add", null);
      }
      return paramWireBatchInfo;
    }
    str3 = paramWireBatchInfo.getFIId();
    str4 = paramWireBatchInfo.getCustomerId();
    arrayOfWireInfo1 = paramWireBatchInfo.getWires();
    if ((arrayOfWireInfo1 == null) || (arrayOfWireInfo1.length == 0))
    {
      FFSDebug.log(str1, "failed: ", BPWLocaleUtil.getMessage(19310, null, "WIRE_MESSAGE"), 0);
      paramWireBatchInfo.setStatusCode(19310);
      paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19310, null, "WIRE_MESSAGE"));
      if (this.B >= 1) {
        a(paramWireBatchInfo, "Add", null);
      }
      return paramWireBatchInfo;
    }
    i = arrayOfWireInfo1.length;
    Object localObject1;
    for (int j = 0; j < i; j++)
    {
      arrayOfWireInfo1[j].setDateDue(paramWireBatchInfo.getDateDue());
      arrayOfWireInfo1[j].setSettlementDate(paramWireBatchInfo.getSettlementDate());
      arrayOfWireInfo1[j].setAmtCurrency(paramWireBatchInfo.getAmtCurrency());
      arrayOfWireInfo1[j].setDestCurrency(paramWireBatchInfo.getDestCurrency());
      arrayOfWireInfo1[j].setExchangeRate(paramWireBatchInfo.getExchangeRate());
      arrayOfWireInfo1[j].setContractNumber(paramWireBatchInfo.getContractNumber());
      arrayOfWireInfo1[j].setMathRule(paramWireBatchInfo.getMathRule());
      arrayOfWireInfo1[j].setFiID(str3);
      arrayOfWireInfo1[j].setCustomerID(str4);
      arrayOfWireInfo1[j].setSubmittedBy(paramWireBatchInfo.getSubmittedBy());
      arrayOfWireInfo1[j].setAgentId(paramWireBatchInfo.getAgentId());
      arrayOfWireInfo1[j].setAgentName(paramWireBatchInfo.getAgentName());
      arrayOfWireInfo1[j].setAgentType(paramWireBatchInfo.getAgentType());
      arrayOfWireInfo1[j].setOrigCurrency(paramWireBatchInfo.getOrigCurrency());
      jdMethod_if(arrayOfWireInfo1[j]);
      if (arrayOfWireInfo1[j].getStatusCode() != 0)
      {
        localObject1 = BPWLocaleUtil.getMessage(arrayOfWireInfo1[j].getStatusCode(), new String[] { "Wire#:", "" + (j + 1), arrayOfWireInfo1[j].getStatusMsg() }, "WIRE_MESSAGE");
        FFSDebug.log((String)localObject1, 0);
        paramWireBatchInfo.setStatusCode(arrayOfWireInfo1[j].getStatusCode());
        paramWireBatchInfo.setStatusMsg((String)localObject1);
        if (this.B >= 1) {
          a(paramWireBatchInfo, "Add", null);
        }
        return paramWireBatchInfo;
      }
      if (!jdMethod_if(arrayOfWireInfo1[j], false))
      {
        localObject1 = BPWLocaleUtil.getMessage(arrayOfWireInfo1[j].getStatusCode(), new String[] { "Wire#:", "" + (j + 1), arrayOfWireInfo1[j].getStatusMsg() }, "WIRE_MESSAGE");
        FFSDebug.log((String)localObject1, 0);
        paramWireBatchInfo.setStatusCode(arrayOfWireInfo1[j].getStatusCode());
        paramWireBatchInfo.setStatusMsg((String)localObject1);
        if (this.B >= 1) {
          a(paramWireBatchInfo, "Add", null);
        }
        return paramWireBatchInfo;
      }
    }
    try
    {
      FFSConnection[] arrayOfFFSConnection = DBUtil.getConnections(2);
      if ((arrayOfFFSConnection == null) || (arrayOfFFSConnection.length < 2))
      {
        localObject1 = str1 + "Can not get DB Connections.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      localFFSConnectionHolder1 = new FFSConnectionHolder();
      localFFSConnectionHolder1.conn = arrayOfFFSConnection[0];
      localFFSConnectionHolder2 = new FFSConnectionHolder();
      localFFSConnectionHolder2.conn = arrayOfFFSConnection[1];
      if (Trans.checkDuplicateTIDAndSaveTID(paramWireBatchInfo.getTrnId()))
      {
        paramWireBatchInfo.setStatusCode(19220);
        paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19220, null, "WIRE_MESSAGE"));
        if (this.B >= 1) {
          a(localFFSConnectionHolder2, paramWireBatchInfo, "AddDup");
        }
        localFFSConnectionHolder2.conn.commit();
        localObject1 = paramWireBatchInfo;
        return localObject1;
      }
      Object localObject3;
      if (a(localFFSConnectionHolder1, paramWireBatchInfo) == true)
      {
        localObject1 = BPWLocaleUtil.getMessage(19852, null, "WIRE_MESSAGE");
        paramWireBatchInfo.setStatusCode(19852);
        paramWireBatchInfo.setStatusMsg((String)localObject1);
        if (this.B >= 1) {
          a(paramWireBatchInfo, "Add", null);
        }
        localFFSConnectionHolder1.conn.rollback();
        localFFSConnectionHolder2.conn.rollback();
        localObject3 = paramWireBatchInfo;
        return localObject3;
      }
      str6 = DBUtil.getNextIndexString("LogID");
      paramWireBatchInfo.setLogId(str6);
      FFSDebug.log(str1, " logID = ", str6, 6);
      paramWireBatchInfo.setWireCount(String.valueOf(i));
      paramWireBatchInfo = Wire.addWireTransferBatch(localFFSConnectionHolder1, paramWireBatchInfo);
      if (paramWireBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder1.conn.rollback();
        localFFSConnectionHolder2.conn.rollback();
        if (this.B >= 1) {
          a(paramWireBatchInfo, "Add", null);
        }
        localObject1 = paramWireBatchInfo;
        return localObject1;
      }
      str2 = paramWireBatchInfo.getBatchId();
      localArrayList = new ArrayList();
      for (int k = 0; k < i; k++)
      {
        arrayOfWireInfo1[k].setBatchId(str2);
        arrayOfWireInfo1[k].setLogId(str6);
        arrayOfWireInfo1[k] = jdMethod_if(localFFSConnectionHolder1, localFFSConnectionHolder2, arrayOfWireInfo1[k], false, false);
        localObject3 = arrayOfWireInfo1[k].getPrcStatus();
        if (k == 0)
        {
          paramWireBatchInfo.setPrcStatus((String)localObject3);
          if (arrayOfWireInfo1[k].getStatusCode() != 0)
          {
            localFFSConnectionHolder1.conn.rollback();
            localFFSConnectionHolder2.conn.rollback();
            paramWireBatchInfo.setPrcStatus((String)localObject3);
            paramWireBatchInfo.setStatusCode(arrayOfWireInfo1[k].getStatusCode());
            paramWireBatchInfo.setStatusMsg(arrayOfWireInfo1[k].getStatusMsg());
            if (this.B >= 1) {
              a(paramWireBatchInfo, "Add", null);
            }
            localObject4 = paramWireBatchInfo;
            return localObject4;
          }
        }
        FFSDebug.log(str1, " wires[i].getStatusCode(): " + arrayOfWireInfo1[k].getStatusCode(), 6);
        FFSDebug.log(str1, " wires[i]. status: ", (String)localObject3, 6);
        if ((!this.F) && (arrayOfWireInfo1[k].getPrcStatus().equals("CREATED"))) {
          localArrayList.add(arrayOfWireInfo1[k]);
        }
        Wire.updateStatus(localFFSConnectionHolder1, arrayOfWireInfo1[k]);
        str5 = arrayOfWireInfo1[k].getAmountType();
        Object localObject4 = new BigDecimal(arrayOfWireInfo1[k].getAmount());
        if ((str5 != null) && (str5.equalsIgnoreCase("CREDIT"))) {
          localBigDecimal3 = localBigDecimal3.add((BigDecimal)localObject4);
        } else if ((str5 != null) && (str5.equalsIgnoreCase("DEBIT"))) {
          localBigDecimal2 = localBigDecimal2.add((BigDecimal)localObject4);
        }
        localBigDecimal1 = localBigDecimal1.add((BigDecimal)localObject4);
      }
      paramWireBatchInfo.setTotalDebitAmount(String.valueOf(localBigDecimal2));
      paramWireBatchInfo.setTotalCreditAmount(String.valueOf(localBigDecimal3));
      paramWireBatchInfo.setTotalAmount(String.valueOf(localBigDecimal1));
      paramWireBatchInfo = Wire.updateWireBatchCtrlAmts(localFFSConnectionHolder1, paramWireBatchInfo);
      if (this.B >= 3) {
        a(localFFSConnectionHolder2, paramWireBatchInfo, "Add");
      }
      localFFSConnectionHolder1.conn.commit();
      localFFSConnectionHolder2.conn.commit();
      if ((localArrayList != null) && (localArrayList.size() > 0))
      {
        WireInfo[] arrayOfWireInfo2 = (WireInfo[])localArrayList.toArray(new WireInfo[0]);
        arrayOfWireInfo2 = jdMethod_for(localFFSConnectionHolder1, arrayOfWireInfo2);
        for (int n = 0; n < arrayOfWireInfo2.length; n++) {
          for (int i1 = 0; i1 < i; i1++) {
            if (arrayOfWireInfo2[n].getSrvrTid().equals(arrayOfWireInfo1[i1].getSrvrTid()))
            {
              arrayOfWireInfo1[i1] = arrayOfWireInfo2[n];
              break;
            }
          }
        }
      }
      for (int m = 0; m < i; m++)
      {
        str8 = arrayOfWireInfo1[m].getPrcStatus();
        if (m == 0) {
          paramWireBatchInfo.setPrcStatus(str8);
        }
        String str9 = Wire.calculateBatchStatus(str8, paramWireBatchInfo.getPrcStatus());
        paramWireBatchInfo.setPrcStatus(str9);
      }
      Wire.updateBatchStatus(localFFSConnectionHolder1, paramWireBatchInfo, paramWireBatchInfo.getPrcStatus());
      if (paramWireBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder1.conn.rollback();
        localFFSConnectionHolder2.conn.rollback();
        localObject2 = paramWireBatchInfo;
        return localObject2;
      }
      localFFSConnectionHolder1.conn.commit();
      localFFSConnectionHolder2.conn.commit();
      paramWireBatchInfo.setStatusCode(0);
      paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      FFSDebug.log(str1, " Done", 6);
    }
    catch (Throwable localThrowable1)
    {
      localFFSConnectionHolder1.conn.rollback();
      if (this.B >= 1) {
        a(paramWireBatchInfo, "Add", "Add wire batch failed, unknown error occurred");
      }
      Object localObject2 = "*** WireProcessor.addWireTransferBatch failed: ";
      String str8 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject2 + str8, 0);
      throw new FFSException(localThrowable1, (String)localObject2);
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder1.conn);
      }
      catch (Throwable localThrowable2)
      {
        FFSDebug.log(str1, " Failed to free the first connection ", 0);
      }
      DBUtil.freeConnection(localFFSConnectionHolder2.conn);
    }
    FFSDebug.log(str1, " end ", 6);
    return paramWireBatchInfo;
  }
  
  public WireBatchInfo modWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.modWireTransferBatch:";
    FFSDebug.log(str1, " Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder1 = null;
    FFSConnectionHolder localFFSConnectionHolder2 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    WireInfo[] arrayOfWireInfo = null;
    int i = 0;
    int j = 0;
    BigDecimal localBigDecimal1 = null;
    BigDecimal localBigDecimal2 = null;
    BigDecimal localBigDecimal3 = null;
    BigDecimal localBigDecimal4 = null;
    String str5 = null;
    String str6 = null;
    WireBatchInfo localWireBatchInfo1 = null;
    WireInfo localWireInfo = null;
    String str7 = null;
    ArrayList localArrayList = null;
    str2 = paramWireBatchInfo.getBatchId();
    Object localObject1;
    if (str2 == null)
    {
      paramWireBatchInfo.setStatusCode(16010);
      localObject1 = BPWLocaleUtil.getMessage(16010, new String[] { "WireBatchInfo", "BatchId" }, "WIRE_MESSAGE");
      paramWireBatchInfo.setStatusMsg((String)localObject1);
      if (this.B >= 1) {
        a(paramWireBatchInfo, "Mod", null);
      }
      return paramWireBatchInfo;
    }
    if (!a(paramWireBatchInfo, false))
    {
      FFSDebug.log("***", str1, "failed:", paramWireBatchInfo.getStatusMsg(), 0);
      if (this.B >= 1) {
        a(paramWireBatchInfo, "Mod", null);
      }
      return paramWireBatchInfo;
    }
    a(paramWireBatchInfo);
    if (paramWireBatchInfo.getStatusCode() != 0)
    {
      FFSDebug.log("***", str1, "failed:", paramWireBatchInfo.getStatusMsg(), 0);
      if (this.B >= 1) {
        a(paramWireBatchInfo, "Mod", null);
      }
      return paramWireBatchInfo;
    }
    str3 = paramWireBatchInfo.getFIId();
    str4 = paramWireBatchInfo.getCustomerId();
    arrayOfWireInfo = paramWireBatchInfo.getWires();
    if ((arrayOfWireInfo == null) || (arrayOfWireInfo.length == 0))
    {
      FFSDebug.log(str1, "failed: ", BPWLocaleUtil.getMessage(19310, null, "WIRE_MESSAGE"), 0);
      paramWireBatchInfo.setStatusCode(19310);
      paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19310, null, "WIRE_MESSAGE"));
      if (this.B >= 1) {
        a(paramWireBatchInfo, "Mod", null);
      }
      return paramWireBatchInfo;
    }
    try
    {
      localObject1 = DBUtil.getConnections(2);
      if ((localObject1 == null) || (localObject1.length < 2))
      {
        localObject2 = str1 + "Can not get DB Connections.";
        FFSDebug.log((String)localObject2, 0);
        throw new FFSException((String)localObject2);
      }
      localFFSConnectionHolder1 = new FFSConnectionHolder();
      localFFSConnectionHolder1.conn = localObject1[0];
      localFFSConnectionHolder2 = new FFSConnectionHolder();
      localFFSConnectionHolder2.conn = localObject1[1];
      if (Trans.checkDuplicateTIDAndSaveTID(paramWireBatchInfo.getTrnId()))
      {
        paramWireBatchInfo.setStatusCode(19220);
        paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19220, null, "WIRE_MESSAGE"));
        if (this.B >= 1) {
          a(localFFSConnectionHolder2, paramWireBatchInfo, "ModDup");
        }
        localFFSConnectionHolder2.conn.commit();
        localObject2 = paramWireBatchInfo;
        return localObject2;
      }
      localWireBatchInfo1 = new WireBatchInfo();
      localWireBatchInfo1.setBatchId(str2);
      localObject2 = Wire.getWireTransferBatch(localFFSConnectionHolder1, localWireBatchInfo1, false, false);
      localWireBatchInfo1 = localObject2[0];
      if (localWireBatchInfo1.getStatusCode() == 16020)
      {
        FFSDebug.log("***", str1, "failed:", paramWireBatchInfo.getStatusMsg(), 0);
        localFFSConnectionHolder1.conn.rollback();
        localFFSConnectionHolder2.conn.rollback();
        paramWireBatchInfo.setStatusCode(localWireBatchInfo1.getStatusCode());
        paramWireBatchInfo.setStatusMsg(localWireBatchInfo1.getStatusMsg());
        if (this.B >= 1) {
          a(paramWireBatchInfo, "Mod", null);
        }
        WireBatchInfo localWireBatchInfo2 = paramWireBatchInfo;
        return localWireBatchInfo2;
      }
      localBigDecimal3 = new BigDecimal(localWireBatchInfo1.getTotalCreditAmount());
      localBigDecimal2 = new BigDecimal(localWireBatchInfo1.getTotalDebitAmount());
      localBigDecimal1 = new BigDecimal(localWireBatchInfo1.getTotalAmount());
      try
      {
        i = Integer.parseInt(localWireBatchInfo1.getWireCount());
      }
      catch (Throwable localThrowable2)
      {
        i = 0;
      }
      str6 = DBUtil.getNextIndexString("LogID");
      paramWireBatchInfo.setLogId(str6);
      FFSDebug.log(str1, " logID: ", str6, 6);
      paramWireBatchInfo.setWireCount(String.valueOf(i));
      boolean bool1 = jdMethod_if(paramWireBatchInfo);
      String str9;
      WireBatchInfo localWireBatchInfo3;
      if (!bool1)
      {
        localFFSConnectionHolder1.conn.rollback();
        localFFSConnectionHolder2.conn.rollback();
        paramWireBatchInfo.setStatusCode(19430);
        str9 = BPWLocaleUtil.getMessage(19430, null, "WIRE_MESSAGE") + " batchId: " + str2;
        paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19430, null, "WIRE_MESSAGE"));
        FFSDebug.log("***", str1, "failed:", str9, 0);
        if (this.B >= 1) {
          a(paramWireBatchInfo, "Mod", null);
        }
        localWireBatchInfo3 = paramWireBatchInfo;
        return localWireBatchInfo3;
      }
      if (!isWirebatchModifyable(str2))
      {
        localFFSConnectionHolder1.conn.rollback();
        localFFSConnectionHolder2.conn.rollback();
        paramWireBatchInfo.setStatusCode(19430);
        str9 = BPWLocaleUtil.getMessage(19430, null, "WIRE_MESSAGE") + " batchId: " + str2;
        paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19430, null, "WIRE_MESSAGE"));
        FFSDebug.log("***", str1, "failed:", str9, 0);
        if (this.B >= 1) {
          a(paramWireBatchInfo, "Mod", null);
        }
        localWireBatchInfo3 = paramWireBatchInfo;
        return localWireBatchInfo3;
      }
      j = arrayOfWireInfo.length;
      boolean bool2 = a(localWireBatchInfo1, paramWireBatchInfo);
      Object localObject4;
      Object localObject5;
      for (int k = 0; k < j; k++) {
        if (arrayOfWireInfo[k] != null)
        {
          str7 = arrayOfWireInfo[k].getAction();
          if (((str7 == null) || (str7.trim().length() == 0)) && (bool2 == true)) {
            str7 = "mod";
          }
          if ((str7 != null) && (str7.trim().length() != 0))
          {
            arrayOfWireInfo[k].setDateDue(paramWireBatchInfo.getDateDue());
            arrayOfWireInfo[k].setSettlementDate(paramWireBatchInfo.getSettlementDate());
            arrayOfWireInfo[k].setAmtCurrency(paramWireBatchInfo.getAmtCurrency());
            arrayOfWireInfo[k].setDestCurrency(paramWireBatchInfo.getDestCurrency());
            arrayOfWireInfo[k].setExchangeRate(paramWireBatchInfo.getExchangeRate());
            arrayOfWireInfo[k].setContractNumber(paramWireBatchInfo.getContractNumber());
            arrayOfWireInfo[k].setMathRule(paramWireBatchInfo.getMathRule());
            arrayOfWireInfo[k].setFiID(str3);
            arrayOfWireInfo[k].setCustomerID(str4);
            arrayOfWireInfo[k].setSubmittedBy(paramWireBatchInfo.getSubmittedBy());
            arrayOfWireInfo[k].setAgentId(paramWireBatchInfo.getAgentId());
            arrayOfWireInfo[k].setAgentName(paramWireBatchInfo.getAgentName());
            arrayOfWireInfo[k].setAgentType(paramWireBatchInfo.getAgentType());
            arrayOfWireInfo[k].setOrigCurrency(paramWireBatchInfo.getOrigCurrency());
            jdMethod_if(arrayOfWireInfo[k]);
            if (arrayOfWireInfo[k].getStatusCode() != 0)
            {
              localFFSConnectionHolder1.conn.rollback();
              localFFSConnectionHolder2.conn.rollback();
              localObject4 = BPWLocaleUtil.getMessage(arrayOfWireInfo[k].getStatusCode(), new String[] { "Wire#:", "" + (k + 1), arrayOfWireInfo[k].getStatusMsg() }, "WIRE_MESSAGE");
              FFSDebug.log((String)localObject4, 0);
              paramWireBatchInfo.setStatusCode(arrayOfWireInfo[k].getStatusCode());
              paramWireBatchInfo.setStatusMsg((String)localObject4);
              if (this.B >= 1) {
                a(paramWireBatchInfo, "Mod", null);
              }
              localObject5 = paramWireBatchInfo;
              return localObject5;
            }
            if (!jdMethod_if(arrayOfWireInfo[k], false))
            {
              localFFSConnectionHolder1.conn.rollback();
              localFFSConnectionHolder2.conn.rollback();
              localObject4 = BPWLocaleUtil.getMessage(arrayOfWireInfo[k].getStatusCode(), new String[] { "Wire#:", "" + (k + 1), arrayOfWireInfo[k].getStatusMsg() }, "WIRE_MESSAGE");
              FFSDebug.log((String)localObject4, 0);
              paramWireBatchInfo.setStatusCode(arrayOfWireInfo[k].getStatusCode());
              paramWireBatchInfo.setStatusMsg((String)localObject4);
              if (this.B >= 1) {
                a(paramWireBatchInfo, "Mod", null);
              }
              localObject5 = paramWireBatchInfo;
              return localObject5;
            }
          }
        }
      }
      paramWireBatchInfo = Wire.updateWireTransferBatch(localFFSConnectionHolder1, paramWireBatchInfo);
      if (paramWireBatchInfo.getStatusCode() != 0)
      {
        FFSDebug.log("***", str1, "failed:", paramWireBatchInfo.getStatusMsg(), 0);
        localFFSConnectionHolder1.conn.rollback();
        localFFSConnectionHolder2.conn.rollback();
        if (this.B >= 1) {
          a(paramWireBatchInfo, "Mod", null);
        }
        WireBatchInfo localWireBatchInfo4 = paramWireBatchInfo;
        return localWireBatchInfo4;
      }
      localArrayList = new ArrayList();
      for (int m = 0; m < j; m++) {
        if (arrayOfWireInfo[m] != null)
        {
          str7 = arrayOfWireInfo[m].getAction();
          arrayOfWireInfo[m].setLogId(str6);
          if ((str7 == null) || (str7.trim().length() == 0))
          {
            arrayOfWireInfo[m].setStatusCode(0);
          }
          else if (str7.equalsIgnoreCase("add"))
          {
            arrayOfWireInfo[m].setBatchId(str2);
            FFSDebug.log("Adding Wire: " + arrayOfWireInfo[m], 6);
            arrayOfWireInfo[m] = jdMethod_if(localFFSConnectionHolder1, localFFSConnectionHolder2, arrayOfWireInfo[m], false, false);
            str5 = arrayOfWireInfo[m].getAmountType();
            localBigDecimal4 = new BigDecimal(arrayOfWireInfo[m].getAmount());
            if ((str5 != null) && (str5.equalsIgnoreCase("CREDIT"))) {
              localBigDecimal3 = localBigDecimal3.add(localBigDecimal4);
            } else if ((str5 != null) && (str5.equalsIgnoreCase("DEBIT"))) {
              localBigDecimal2 = localBigDecimal2.add(localBigDecimal4);
            }
            localBigDecimal1 = localBigDecimal1.add(localBigDecimal4);
            i++;
          }
          else if (str7.equalsIgnoreCase("mod"))
          {
            if (localWireInfo == null) {
              localWireInfo = new WireInfo();
            }
            localWireInfo.setSrvrTid(arrayOfWireInfo[m].getSrvrTid());
            localWireInfo = Wire.getWireInfo(localFFSConnectionHolder1, localWireInfo, false);
            if (localWireInfo.getStatusCode() != 0)
            {
              localFFSConnectionHolder1.conn.rollback();
              localFFSConnectionHolder2.conn.rollback();
              localObject4 = new StringBuffer();
              ((StringBuffer)localObject4).append(" failed ").append(localWireInfo.getStatusMsg());
              ((StringBuffer)localObject4).append(" SrvrTid:").append(localWireInfo.getSrvrTid());
              FFSDebug.log(str1, ((StringBuffer)localObject4).toString(), 0);
              paramWireBatchInfo.setStatusCode(localWireInfo.getStatusCode());
              paramWireBatchInfo.setStatusMsg(localWireInfo.getStatusMsg());
              if (this.B >= 1) {
                a(paramWireBatchInfo, "Mod", null);
              }
              localObject5 = paramWireBatchInfo;
              return localObject5;
            }
            str5 = localWireInfo.getAmountType();
            localBigDecimal4 = new BigDecimal(localWireInfo.getAmount());
            if ((str5 != null) && (str5.equalsIgnoreCase("CREDIT"))) {
              localBigDecimal3 = localBigDecimal3.subtract(localBigDecimal4);
            } else if ((str5 != null) && (str5.equalsIgnoreCase("DEBIT"))) {
              localBigDecimal2 = localBigDecimal2.subtract(localBigDecimal4);
            }
            localBigDecimal1 = localBigDecimal1.subtract(localBigDecimal4);
            FFSDebug.log("Modifying Wire: " + arrayOfWireInfo[m], 6);
            arrayOfWireInfo[m] = a(localFFSConnectionHolder1, localFFSConnectionHolder2, arrayOfWireInfo[m], false, false);
            if ((arrayOfWireInfo[m] == null) || (arrayOfWireInfo[m].getStatusCode() != 0))
            {
              localFFSConnectionHolder1.conn.rollback();
              localFFSConnectionHolder2.conn.rollback();
              localObject4 = new StringBuffer();
              ((StringBuffer)localObject4).append(": modify batch failed: ").append(arrayOfWireInfo[m].getStatusMsg());
              ((StringBuffer)localObject4).append(", SrvrTid:").append(arrayOfWireInfo[m].getSrvrTid());
              FFSDebug.log(str1, ((StringBuffer)localObject4).toString(), 0);
              paramWireBatchInfo.setStatusCode(arrayOfWireInfo[m].getStatusCode());
              paramWireBatchInfo.setStatusMsg(arrayOfWireInfo[m].getStatusMsg());
              if (this.B >= 1) {
                a(paramWireBatchInfo, "Mod", null);
              }
              localObject5 = paramWireBatchInfo;
              return localObject5;
            }
            str5 = arrayOfWireInfo[m].getAmountType();
            localBigDecimal4 = new BigDecimal(arrayOfWireInfo[m].getAmount());
            if ((str5 != null) && (str5.equalsIgnoreCase("CREDIT"))) {
              localBigDecimal3 = localBigDecimal3.add(localBigDecimal4);
            } else if ((str5 != null) && (str5.equalsIgnoreCase("DEBIT"))) {
              localBigDecimal2 = localBigDecimal2.add(localBigDecimal4);
            }
            localBigDecimal1 = localBigDecimal1.add(localBigDecimal4);
          }
          else if (str7.equalsIgnoreCase("del"))
          {
            if (localWireInfo == null) {
              localWireInfo = new WireInfo();
            }
            localWireInfo.setSrvrTid(arrayOfWireInfo[m].getSrvrTid());
            localWireInfo = Wire.getWireInfo(localFFSConnectionHolder1, localWireInfo, false);
            if (localWireInfo.getStatusCode() != 0)
            {
              localFFSConnectionHolder1.conn.rollback();
              localFFSConnectionHolder2.conn.rollback();
              localObject4 = new StringBuffer();
              ((StringBuffer)localObject4).append(" failed ").append(localWireInfo.getStatusMsg());
              ((StringBuffer)localObject4).append(" SrvrTid:").append(localWireInfo.getSrvrTid());
              FFSDebug.log(str1, ((StringBuffer)localObject4).toString(), 0);
              paramWireBatchInfo.setStatusCode(localWireInfo.getStatusCode());
              paramWireBatchInfo.setStatusMsg(localWireInfo.getStatusMsg());
              if (this.B >= 1) {
                a(paramWireBatchInfo, "Mod", null);
              }
              localObject5 = paramWireBatchInfo;
              return localObject5;
            }
            str5 = localWireInfo.getAmountType();
            localBigDecimal4 = new BigDecimal(localWireInfo.getAmount());
            if ((str5 != null) && (str5.equalsIgnoreCase("CREDIT"))) {
              localBigDecimal3 = localBigDecimal3.subtract(localBigDecimal4);
            } else if ((str5 != null) && (str5.equalsIgnoreCase("DEBIT"))) {
              localBigDecimal2 = localBigDecimal2.subtract(localBigDecimal4);
            }
            localBigDecimal1 = localBigDecimal1.subtract(localBigDecimal4);
            FFSDebug.log("Canceling Wire: " + arrayOfWireInfo[m], 6);
            arrayOfWireInfo[m] = jdMethod_do(localFFSConnectionHolder1, localFFSConnectionHolder2, arrayOfWireInfo[m], false, false);
            i--;
          }
          else
          {
            localObject4 = new StringBuffer();
            ((StringBuffer)localObject4).append("Invalid action.").append(" '");
            ((StringBuffer)localObject4).append(str7).append("' in Wire number:" + (m + 1));
            FFSDebug.log(str1, ((StringBuffer)localObject4).toString(), 0);
            paramWireBatchInfo.setStatusCode(17180);
            localObject5 = BPWLocaleUtil.getMessage(17180, new String[] { "Wire #:", "" + (m + 1) }, "WIRE_MESSAGE");
            paramWireBatchInfo.setStatusMsg((String)localObject5);
            if (this.B >= 1) {
              a(paramWireBatchInfo, "Mod", null);
            }
            if (m == 0)
            {
              localFFSConnectionHolder1.conn.rollback();
              localFFSConnectionHolder2.conn.rollback();
              WireBatchInfo localWireBatchInfo6 = paramWireBatchInfo;
              return localWireBatchInfo6;
            }
          }
          localObject4 = arrayOfWireInfo[m].getPrcStatus();
          if (m == 0)
          {
            if (arrayOfWireInfo[m].getStatusCode() != 0)
            {
              localFFSConnectionHolder1.conn.rollback();
              localFFSConnectionHolder2.conn.rollback();
              paramWireBatchInfo.setStatusCode(arrayOfWireInfo[m].getStatusCode());
              paramWireBatchInfo.setStatusMsg(arrayOfWireInfo[m].getStatusMsg());
              if (this.B >= 1) {
                a(paramWireBatchInfo, "Mod", null);
              }
              localObject5 = paramWireBatchInfo;
              return localObject5;
            }
            paramWireBatchInfo.setPrcStatus((String)localObject4);
          }
          if ((!this.F) && (arrayOfWireInfo[m].getPrcStatus().equals("CREATED"))) {
            localArrayList.add(arrayOfWireInfo[m]);
          }
        }
      }
      paramWireBatchInfo.setTotalDebitAmount(String.valueOf(localBigDecimal2));
      paramWireBatchInfo.setTotalCreditAmount(String.valueOf(localBigDecimal3));
      paramWireBatchInfo.setTotalAmount(String.valueOf(localBigDecimal1));
      paramWireBatchInfo.setWireCount(String.valueOf(i));
      paramWireBatchInfo = Wire.updateWireBatchCtrlAmts(localFFSConnectionHolder1, paramWireBatchInfo);
      Wire.updateWiresWithBatchValues(localFFSConnectionHolder1, paramWireBatchInfo);
      Object localObject3;
      if (paramWireBatchInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1, paramWireBatchInfo.getStatusMsg(), 0);
        localFFSConnectionHolder1.conn.rollback();
        localFFSConnectionHolder2.conn.rollback();
        if (this.B >= 1) {
          a(paramWireBatchInfo, "Mod", null);
        }
        localObject3 = paramWireBatchInfo;
        return localObject3;
      }
      if (this.B >= 3) {
        a(localFFSConnectionHolder2, paramWireBatchInfo, "Mod");
      }
      localFFSConnectionHolder1.conn.commit();
      localFFSConnectionHolder2.conn.commit();
      if ((localArrayList != null) && (localArrayList.size() > 0))
      {
        localObject3 = (WireInfo[])localArrayList.toArray(new WireInfo[0]);
        localObject3 = jdMethod_for(localFFSConnectionHolder1, (WireInfo[])localObject3);
        for (int i1 = 0; i1 < localObject3.length; i1++) {
          for (int i2 = 0; i2 < i; i2++) {
            if (localObject3[i1].getSrvrTid().equals(arrayOfWireInfo[i2].getSrvrTid()))
            {
              arrayOfWireInfo[i2] = localObject3[i1];
              break;
            }
          }
        }
      }
      for (int n = 0; n < j; n++)
      {
        String str10 = arrayOfWireInfo[n].getPrcStatus();
        if (n == 0) {
          paramWireBatchInfo.setPrcStatus(str10);
        }
        String str11 = Wire.calculateBatchStatus(str10, paramWireBatchInfo.getPrcStatus());
        paramWireBatchInfo.setPrcStatus(str11);
      }
      Wire.updateBatchStatus(localFFSConnectionHolder1, paramWireBatchInfo, paramWireBatchInfo.getPrcStatus());
      if (paramWireBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder1.conn.rollback();
        localFFSConnectionHolder2.conn.rollback();
        WireBatchInfo localWireBatchInfo5 = paramWireBatchInfo;
        return localWireBatchInfo5;
      }
      localFFSConnectionHolder1.conn.commit();
      localFFSConnectionHolder2.conn.commit();
      paramWireBatchInfo.setStatusCode(0);
      paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      FFSDebug.log(str1, " Done", 6);
    }
    catch (Throwable localThrowable1)
    {
      localFFSConnectionHolder1.conn.rollback();
      localFFSConnectionHolder2.conn.rollback();
      if (this.B >= 1) {
        a(paramWireBatchInfo, "Mod", "Modify wire batch failed, unknown error occurred.");
      }
      Object localObject2 = "*** WireProcessor.modWireTransferBatch failed: ";
      String str8 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject2 + str8, 0);
      throw new FFSException(localThrowable1, (String)localObject2);
    }
    finally
    {
      try
      {
        jdMethod_do(paramWireBatchInfo);
      }
      catch (Throwable localThrowable3)
      {
        String str12 = "*** " + str1 + " unlockWireOfBatchScheduleInfo failed: ";
        String str13 = FFSDebug.stackTrace(localThrowable3);
        FFSDebug.log(str12 + str13, 0);
      }
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder1.conn);
      }
      catch (Throwable localThrowable4)
      {
        FFSDebug.log(str1, " Failed to free connection", 6);
      }
      DBUtil.freeConnection(localFFSConnectionHolder2.conn);
    }
    FFSDebug.log(str1, " end ", 6);
    return paramWireBatchInfo;
  }
  
  public WireBatchInfo canWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    String str1 = "WireProcessor.canWireTransferBatch:";
    FFSDebug.log(str1, " Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    String str2 = null;
    String str3 = null;
    Object localObject1;
    if (paramWireBatchInfo == null)
    {
      paramWireBatchInfo = new WireBatchInfo();
      paramWireBatchInfo.setStatusCode(16000);
      paramWireBatchInfo.setStatusCode(16010);
      localObject1 = BPWLocaleUtil.getMessage(16010, new String[] { "WireProcessor", "WireBatchInfo" }, "WIRE_MESSAGE");
      paramWireBatchInfo.setStatusMsg((String)localObject1);
      return paramWireBatchInfo;
    }
    str3 = paramWireBatchInfo.getBatchId();
    if (str3 == null)
    {
      paramWireBatchInfo.setStatusCode(16010);
      localObject1 = BPWLocaleUtil.getMessage(16010, new String[] { "WireBatchInfo", "batchId" }, "WIRE_MESSAGE");
      paramWireBatchInfo.setStatusMsg((String)localObject1);
      if (this.B >= 1) {
        a(paramWireBatchInfo, "Can", null);
      }
      return paramWireBatchInfo;
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        localObject1 = str1 + "Can not get DB Connection.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      if (Trans.checkDuplicateTIDAndSaveTID(paramWireBatchInfo.getTrnId()))
      {
        paramWireBatchInfo.setStatusCode(19220);
        paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19220, null, "WIRE_MESSAGE"));
        if (this.B >= 1) {
          a(localFFSConnectionHolder, paramWireBatchInfo, "CanDup");
        }
        localFFSConnectionHolder.conn.commit();
        localObject1 = paramWireBatchInfo;
        return localObject1;
      }
      str2 = DBUtil.getNextIndexString("LogID");
      paramWireBatchInfo.setLogId(str2);
      FFSDebug.log(str1, " logID=", str2, 6);
      WireBatchInfo localWireBatchInfo1 = new WireBatchInfo();
      localWireBatchInfo1.setBatchId(paramWireBatchInfo.getBatchId());
      localObject1 = Wire.getWireTransferBatch(localFFSConnectionHolder, localWireBatchInfo1, false, true, false);
      localWireBatchInfo1 = localObject1[0];
      boolean bool = jdMethod_if(localWireBatchInfo1);
      if (!bool)
      {
        paramWireBatchInfo.setStatusCode(19430);
        localObject2 = BPWLocaleUtil.getMessage(19430, new String[] { " batchId: ", str3 }, "WIRE_MESSAGE");
        paramWireBatchInfo.setStatusMsg((String)localObject2);
        FFSDebug.log("***", str1, "failed:", paramWireBatchInfo.getStatusMsg(), 0);
        localFFSConnectionHolder.conn.rollback();
        if (this.B >= 1) {
          a(paramWireBatchInfo, "Can", null);
        }
        localObject3 = paramWireBatchInfo;
        return localObject3;
      }
      if (!isWireBatchDeleteable(str3))
      {
        paramWireBatchInfo.setStatusCode(19430);
        localObject2 = BPWLocaleUtil.getMessage(19430, new String[] { " batchId: ", str3 }, "WIRE_MESSAGE");
        paramWireBatchInfo.setStatusMsg((String)localObject2);
        FFSDebug.log("***", str1, "failed:", paramWireBatchInfo.getStatusMsg(), 0);
        localFFSConnectionHolder.conn.rollback();
        if (this.B >= 1) {
          a(paramWireBatchInfo, "Can", null);
        }
        localObject3 = paramWireBatchInfo;
        return localObject3;
      }
      localObject2 = localWireBatchInfo1.getWires();
      Object localObject3 = null;
      if (localObject2 != null) {
        for (int i = 0; i < localObject2.length; i++)
        {
          localObject2[i] = jdMethod_do(localFFSConnectionHolder, localFFSConnectionHolder, localObject2[i], false, false);
          localObject3 = localObject2[i].getPrcStatus();
          if (i == 0) {
            paramWireBatchInfo.setPrcStatus((String)localObject3);
          }
          if (localObject2[i].getStatusCode() != 0)
          {
            localFFSConnectionHolder.conn.rollback();
            paramWireBatchInfo.setPrcStatus((String)localObject3);
            paramWireBatchInfo.setStatusCode(localObject2[i].getStatusCode());
            paramWireBatchInfo.setStatusMsg(localObject2[i].getStatusMsg());
            if (this.B >= 1) {
              a(paramWireBatchInfo, "Can", null);
            }
            WireBatchInfo localWireBatchInfo3 = paramWireBatchInfo;
            return localWireBatchInfo3;
          }
          localObject2[i].setPrcStatus("CANCELEDON");
        }
      }
      paramWireBatchInfo.setWires((WireInfo[])localObject2);
      paramWireBatchInfo.setPrcStatus("CANCELEDON");
      Wire.updateBatchStatus(localFFSConnectionHolder, paramWireBatchInfo, "CANCELEDON");
      if (paramWireBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        if (this.B >= 1) {
          a(paramWireBatchInfo, "Can", null);
        }
        WireBatchInfo localWireBatchInfo2 = paramWireBatchInfo;
        return localWireBatchInfo2;
      }
      if (this.B >= 3) {
        a(localFFSConnectionHolder, paramWireBatchInfo, "Can");
      }
      localFFSConnectionHolder.conn.commit();
      paramWireBatchInfo.setStatusCode(0);
      paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      FFSDebug.log(str1, " Done", 6);
    }
    catch (Throwable localThrowable1)
    {
      localFFSConnectionHolder.conn.rollback();
      if (this.B >= 1) {
        a(paramWireBatchInfo, "Can", "Delete wire batch failed, unknown error occurred.");
      }
      String str4 = "*** WireProcessor.canWireTransferBatch failed: ";
      Object localObject2 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str4, (String)localObject2, 0);
      throw new FFSException(localThrowable1, str4);
    }
    finally
    {
      try
      {
        jdMethod_do(paramWireBatchInfo);
      }
      catch (Throwable localThrowable2)
      {
        String str5 = "*** " + str1 + " unlockWireOfBatchScheduleInfo failed: ";
        String str6 = FFSDebug.stackTrace(localThrowable2);
        FFSDebug.log(str5 + str6, 0);
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return paramWireBatchInfo;
  }
  
  public WireBatchInfo[] getWireTransferBatch(WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.getWireTransferBatch : start ", 6);
    WireBatchInfo[] arrayOfWireBatchInfo1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfWireBatchInfo1 = Wire.getWireTransferBatch(localFFSConnectionHolder, paramWireBatchInfo, false);
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log("WireProcessor.getWireTransferBatch : end", 6);
      WireBatchInfo[] arrayOfWireBatchInfo2 = arrayOfWireBatchInfo1;
      return arrayOfWireBatchInfo2;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "WireProcessor.getWireTransferBatch failed " + localThrowable.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public WireInfo[] getBatchWires(String paramString)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.getBatchWires : start ", 6);
    WireBatchInfo localWireBatchInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1;
    if (paramString == null)
    {
      localObject1 = "batchId  is null";
      FFSDebug.log("WireProcessor.getBatchWires, " + (String)localObject1, 0);
      return null;
    }
    localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localWireBatchInfo = new WireBatchInfo();
      localWireBatchInfo.setBatchId(paramString);
      localWireBatchInfo = Wire.getWiresByBatchId(localFFSConnectionHolder, localWireBatchInfo, false);
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log("WireProcessor.getBatchWires : end", 6);
      localObject1 = localWireBatchInfo.getWires();
      return localObject1;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "WireProcessor.getBatchWires failed " + localThrowable.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWHist getWireBatchHistory(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.getWireBatchHistory : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramBPWHist = Wire.getWireBatchHistory(localFFSConnectionHolder, paramBPWHist);
      localFFSConnectionHolder.conn.commit();
      BPWHist localBPWHist = paramBPWHist;
      return localBPWHist;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "WireProcessor.getWireBatchHistory failed " + localThrowable.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private boolean a(WireBatchInfo paramWireBatchInfo, boolean paramBoolean)
    throws BPWException
  {
    String str1;
    if (paramWireBatchInfo == null)
    {
      paramWireBatchInfo = new WireBatchInfo();
      paramWireBatchInfo.setStatusCode(16000);
      str1 = BPWLocaleUtil.getMessage(16000, new String[] { "WireInfo" }, "WIRE_MESSAGE");
      paramWireBatchInfo.setStatusMsg(str1);
      return false;
    }
    if ((paramWireBatchInfo.getFIId() == null) || (paramWireBatchInfo.getFIId().trim().length() == 0))
    {
      paramWireBatchInfo.setStatusCode(16010);
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { "WireBatchInfo", "FIID" }, "WIRE_MESSAGE");
      paramWireBatchInfo.setStatusMsg(str1);
      return false;
    }
    if ((paramWireBatchInfo.getCustomerId() == null) || (paramWireBatchInfo.getCustomerId().trim().length() == 0))
    {
      paramWireBatchInfo.setStatusCode(16010);
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { "WireBatchInfo", "CustomerId" }, "WIRE_MESSAGE");
      paramWireBatchInfo.setStatusMsg(str1);
      return false;
    }
    if (!paramBoolean)
    {
      str1 = paramWireBatchInfo.getBatchDest();
      if ((str1 == null) || (str1.trim().length() == 0))
      {
        paramWireBatchInfo.setStatusCode(16010);
        String str2 = BPWLocaleUtil.getMessage(16010, new String[] { "WireBatchInfo", "batchDest" }, "WIRE_MESSAGE");
        paramWireBatchInfo.setStatusMsg(str2);
        return false;
      }
      paramWireBatchInfo.setBatchDest(paramWireBatchInfo.getBatchDest().toUpperCase());
    }
    return true;
  }
  
  private boolean a(String paramString)
  {
    FFSDebug.log("WireProcessor.validateWireStatus: Start", 6);
    if ((!"RELEASED".equals(paramString)) && (!"RELEASEREJECTED".equals(paramString)) && (!"RELEASEFAILED".equals(paramString)) && (!"RELEASEPENDING".equals(paramString)))
    {
      String str = "This wire or wire batch contains invalid status. Status:" + paramString;
      FFSDebug.log("WireProcessor.validateWireStatus: Failed ", str, 0);
      return false;
    }
    FFSDebug.log("WireProcessor.validateWireStatus: Done", 6);
    return true;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, WireBatchInfo paramWireBatchInfo, String paramString)
    throws Exception
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    AuditLogRecord localAuditLogRecord = null;
    str1 = paramWireBatchInfo.getPrcStatus();
    int i = 4051;
    if ("Add".equalsIgnoreCase(paramString))
    {
      i = 4059;
      str2 = "A batch of single wires has been added successfully";
      str3 = paramWireBatchInfo.getUserId();
    }
    else if ("Mod".equalsIgnoreCase(paramString))
    {
      paramWireBatchInfo.setPrcStatus("MODIFIED");
      i = 4061;
      str2 = "A batch of single wires has been modified successfully";
    }
    else if ("Can".equalsIgnoreCase(paramString))
    {
      i = 4063;
      str2 = "A batch of single wires has been deleted successfully";
    }
    else if ("AddDup".equalsIgnoreCase(paramString))
    {
      paramWireBatchInfo.setPrcStatus("DUPLICATE");
      i = 4060;
      str2 = "Transaction Id for the wire transfer batch addition is duplicate";
      str3 = paramWireBatchInfo.getUserId();
    }
    else if ("ModDup".equalsIgnoreCase(paramString))
    {
      paramWireBatchInfo.setPrcStatus("DUPLICATE");
      i = 4062;
      str2 = "Transaction Id for the wire transfer batch modification is duplicate";
    }
    else if ("CanDup".equalsIgnoreCase(paramString))
    {
      paramWireBatchInfo.setPrcStatus("DUPLICATE");
      i = 4064;
      str2 = "Transaction Id for the wire transfer batch deletion is duplicate";
    }
    if (str3 == null) {
      str3 = paramWireBatchInfo.getSubmittedBy();
    }
    try
    {
      int j = 0;
      if ((paramWireBatchInfo.getCustomerId().equals(paramWireBatchInfo.getUserId())) || (paramWireBatchInfo.getCustomerId().equals(paramWireBatchInfo.getSubmittedBy()))) {
        j = 0;
      } else {
        try
        {
          j = Integer.parseInt(paramWireBatchInfo.getCustomerId());
        }
        catch (Exception localException)
        {
          j = 0;
        }
      }
      str4 = paramWireBatchInfo.getTotalAmount();
      if ((str4 == null) || (str4.trim().length() == 0)) {
        str4 = "-1";
      }
      localAuditLogRecord = new AuditLogRecord(str3, paramWireBatchInfo.getAgentId(), paramWireBatchInfo.getAgentType(), str2, paramWireBatchInfo.getExtId(), i, j, new BigDecimal(str4), paramWireBatchInfo.getOrigCurrency(), paramWireBatchInfo.getBatchId(), paramWireBatchInfo.getPrcStatus(), null, null, null, null, -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      String str4 = "WireProcessor.addWireTransferBatch failed " + localNumberFormatException.toString();
      FFSDebug.log(str4 + FFSDebug.stackTrace(localNumberFormatException), 0);
      throw new FFSException(localNumberFormatException, str4);
    }
    paramWireBatchInfo.setPrcStatus(str1);
  }
  
  private void a(WireBatchInfo paramWireBatchInfo, String paramString1, String paramString2)
  {
    String str1 = "WireProcessor.logWireBatchAudit";
    FFSConnectionHolder localFFSConnectionHolder = null;
    String str2 = paramString2;
    int i = paramWireBatchInfo.getStatusCode();
    AuditLogRecord localAuditLogRecord = null;
    int j = 0;
    String str3 = null;
    int k = 4051;
    if ("Add".equalsIgnoreCase(paramString1))
    {
      k = 4059;
      if (paramString2 == null) {
        str2 = "Add wire batch failed, ";
      }
      str3 = paramWireBatchInfo.getUserId();
    }
    else if ("Mod".equalsIgnoreCase(paramString1))
    {
      k = 4061;
      if (paramString2 == null) {
        str2 = "Modify wire batch failed, ";
      }
    }
    else if ("Can".equalsIgnoreCase(paramString1))
    {
      k = 4063;
      if (paramString2 == null) {
        str2 = "Delete wire batch failed, ";
      }
    }
    if (str3 == null) {
      str3 = paramWireBatchInfo.getSubmittedBy();
    }
    if (paramString2 == null) {
      switch (i)
      {
      case 19120: 
        str2 = str2 + "some wire has invalid payee Id.";
        break;
      case 23170: 
        str2 = str2 + "some wire has invalid FI Id.";
        break;
      case 19130: 
        str2 = str2 + "some wire has invalid Customer Id.";
        break;
      case 19270: 
        str2 = str2 + "unable to save extra info of some wire.";
        break;
      case 19380: 
        str2 = str2 + "wire type is missing in some wire.";
        break;
      case 19360: 
        str2 = str2 + "some wire has invalid wire type.";
        break;
      case 19370: 
        str2 = str2 + "some wire has unknown wire type.";
        break;
      case 19210: 
        str2 = str2 + "some wire has invalid format of due date.";
        break;
      case 19490: 
        str2 = str2 + "settlement date is before due date in some wire.";
        break;
      case 19480: 
        str2 = str2 + "some wire has invalid settlement date.";
        break;
      case 19200: 
        str2 = str2 + "error occurred while calculating pay day for some wire.";
        break;
      case 19500: 
        str2 = str2 + "some wire is not in required state.";
        break;
      case 19310: 
        str2 = str2 + "does not contain any wire transfer.";
        break;
      case 19400: 
        str2 = str2 + "amount type is not valid (debit/credit).";
        break;
      case 16020: 
        str2 = str2 + "record is not found in database.";
        break;
      case 17180: 
        str2 = str2 + "invalid action for some wire.";
        break;
      default: 
        if ((paramWireBatchInfo.getStatusMsg() != null) && (paramWireBatchInfo.getStatusMsg().length() > 0)) {
          str2 = str2 + paramWireBatchInfo.getStatusMsg();
        } else {
          str2 = str2 + "due to unknown error.";
        }
        break;
      }
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        String str4 = str1 + "Can not get DB Connection.";
        FFSDebug.log(str4, 0);
      }
      if ((paramWireBatchInfo.getCustomerId() != null) && (paramWireBatchInfo.getCustomerId().length() > 0)) {
        if ((paramWireBatchInfo.getCustomerId().equals(paramWireBatchInfo.getUserId())) || (paramWireBatchInfo.getCustomerId().equals(paramWireBatchInfo.getSubmittedBy()))) {
          j = 0;
        } else {
          try
          {
            j = Integer.parseInt(paramWireBatchInfo.getCustomerId());
          }
          catch (Exception localException1)
          {
            j = 0;
          }
        }
      }
      String str5 = paramWireBatchInfo.getTotalAmount();
      if ((str5 == null) || (str5.trim().length() == 0)) {
        str5 = "-1";
      }
      localAuditLogRecord = new AuditLogRecord(str3, paramWireBatchInfo.getAgentId(), paramWireBatchInfo.getAgentType(), str2, paramWireBatchInfo.getExtId(), k, j, new BigDecimal(str5), paramWireBatchInfo.getOrigCurrency(), paramWireBatchInfo.getBatchId(), paramWireBatchInfo.getPrcStatus(), null, null, null, null, -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, localFFSConnectionHolder.conn.getConnection());
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException2)
    {
      String str6 = "WireProcessor.addWireTransferBatch failed " + localException2.toString();
      FFSDebug.log(str6 + FFSDebug.stackTrace(localException2), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public void processApprovalResult(String paramString1, String paramString2, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "WireProcessor.processApprovalResult: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    int i = 0;
    String str2 = null;
    FFSDebug.log(str1, "srvrTid:", paramString1, " decision:", paramString2, 6);
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      i = 17504;
      str2 = "Decision from Approval System is null";
      throw new FFSException(i, str1 + str2);
    }
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      i = 17501;
      str2 = "Transaction id is null";
      throw new FFSException(i, str1 + str2);
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      WireInfo localWireInfo = new WireInfo();
      localWireInfo.setSrvrTid(paramString1);
      localWireInfo = Wire.getWireInfo(localFFSConnectionHolder, localWireInfo);
      if (localWireInfo.getStatusCode() != 0)
      {
        i = 17502;
        str2 = "Transaction id is invalid";
        throw new FFSException(i, str1 + str2);
      }
      FFSDebug.log(str1, "wireInfo: " + localWireInfo, 6);
      int j = 0;
      int k = 0;
      if (localWireInfo.getWireType().equalsIgnoreCase("TEMPLATE")) {
        j = 1;
      } else if (localWireInfo.getWireType().equalsIgnoreCase("RECTEMPLATE")) {
        k = 1;
      }
      if (localWireInfo.getPrcStatus().indexOf("APPROVAL_PENDING") == -1)
      {
        i = 17503;
        str2 = "Transaction not waiting for Approval";
        throw new FFSException(i, str1 + str2);
      }
      if (paramString2.compareTo("Approved") == 0)
      {
        if ((j == 0) && (k == 0))
        {
          if ((localWireInfo.getDateToPost() == null) || (localWireInfo.getDateToPost().length() == 0))
          {
            i = 17527;
            str2 = BPWLocaleUtil.getMessage(17527, null, "WIRE_MESSAGE");
            throw new FFSException(i, str2);
          }
          if (jdMethod_int(localFFSConnectionHolder, localWireInfo) == true)
          {
            i = 17526;
            str2 = BPWLocaleUtil.getMessage(17526, null, "WIRE_MESSAGE");
            throw new FFSException(i, str2);
          }
        }
        if (j != 0) {
          localWireInfo.setPrcStatus("TEMPLATE");
        } else if (k != 0) {
          localWireInfo.setPrcStatus("RECTEMPLATE");
        } else {
          localWireInfo.setPrcStatus("CREATED");
        }
        Wire.updateStatus(localFFSConnectionHolder, localWireInfo);
      }
      else if (paramString2.compareTo("Rejected") == 0)
      {
        int m = LimitCheckApprovalProcessor.processWireReject(localFFSConnectionHolder, localWireInfo, paramHashMap);
        if (LimitCheckApprovalProcessor.mapToStatus(m).equals("LIMIT_REVERT_SUCCEEDED"))
        {
          FFSDebug.log(str1, "LIMIT REVERT SUCCEEDED", 6);
          localWireInfo.setPrcStatus("APPROVAL_REJECTED");
          Wire.updateStatus(localFFSConnectionHolder, localWireInfo);
        }
        else
        {
          FFSDebug.log(str1, "LIMIT REVERT FAILED", 6);
          try
          {
            if (this.B >= 1)
            {
              a(localWireInfo, "ProcessApprvlRslt", "Failed to revert limit.", false);
              localFFSConnectionHolder.conn.commit();
            }
          }
          catch (Exception localException)
          {
            String str5 = "*** " + str1 + " failed to write audit log.";
            String str6 = null;
            localFFSConnectionHolder.conn.rollback();
            str6 = FFSDebug.stackTrace(localException);
            FFSDebug.log(str5, str6, 0);
          }
          i = 17505;
          str2 = "Limit Revert Failed";
          throw new FFSException(i, str1 + str2);
        }
      }
      else
      {
        i = 17506;
        str2 = "Decision from Approval System is invalid";
        throw new FFSException(i, str1 + str2);
      }
      if (this.B >= 4) {
        a(localFFSConnectionHolder, localWireInfo, "ProcessApprvlRslt", false);
      }
      if ((paramString2.compareTo("Approved") == 0) && (!this.F) && (localWireInfo.getPrcStatus().equals("CREATED")))
      {
        FFSDebug.log(str1 + " releasing wire in auto release mode", 6);
        localWireInfo = processSingleWireInAutoReleaseMode(localFFSConnectionHolder, localWireInfo);
      }
      if (localWireInfo.getStatusCode() != 0) {
        localFFSConnectionHolder.conn.rollback();
      } else {
        localFFSConnectionHolder.conn.commit();
      }
      FFSDebug.log(str1, "Done", 6);
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str4 = null;
      localFFSConnectionHolder.conn.rollback();
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str4, 0);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = null;
      localFFSConnectionHolder.conn.rollback();
      str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      if (localFFSConnectionHolder != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  public BPWHist getCombinedWireHistory(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("WireProcessor.getCombinedWireHistory : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramBPWHist = Wire.getCombinedWireHistory(localFFSConnectionHolder, paramBPWHist);
      localFFSConnectionHolder.conn.commit();
      BPWHist localBPWHist = paramBPWHist;
      return localBPWHist;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "WireProcessor.getCombinedWireHistory failed " + localThrowable.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public HashMap getWiresConfiguration()
    throws FFSException
  {
    if (this.E != null) {
      return this.E;
    }
    this.E = new HashMap();
    try
    {
      String str = A.otherProperties.getProperty("bpw.wires.config.supportrelease", "true");
      if (str != null) {
        this.E.put("bpw.wires.config.supportrelease", str);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireProcessor.getWiresConfiguration: " + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, "WireProcessor.getWiresConfiguration failed");
    }
    return this.E;
  }
  
  public WireInfo processSingleWireInAutoReleaseMode(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    String str = "WireProcessor.processSingleWireInAutoReleaseMode: ";
    FFSDebug.log(str, "start", 6);
    WireInfo[] arrayOfWireInfo = new WireInfo[1];
    arrayOfWireInfo[0] = paramWireInfo;
    arrayOfWireInfo = jdMethod_for(paramFFSConnectionHolder, arrayOfWireInfo);
    paramWireInfo = arrayOfWireInfo[0];
    return paramWireInfo;
  }
  
  private WireInfo[] jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, WireInfo[] paramArrayOfWireInfo)
    throws FFSException
  {
    String str = "WireProcessor.processSingleWireListInAutoReleaseMode: ";
    FFSDebug.log(str, "start", 6);
    Hashtable[] arrayOfHashtable = null;
    if ((paramArrayOfWireInfo == null) || (paramArrayOfWireInfo.length == 0))
    {
      FFSDebug.log(str, "Wire list is empty.", 0);
      return paramArrayOfWireInfo;
    }
    try
    {
      FFSDebug.log(str, "auto-releasing wire", 6);
      arrayOfHashtable = new Hashtable[paramArrayOfWireInfo.length];
      for (int i = 0; i < paramArrayOfWireInfo.length; i++)
      {
        paramArrayOfWireInfo[i].setPrcStatus("RELEASED");
        arrayOfHashtable[i] = paramArrayOfWireInfo[i].getExtInfo();
        paramArrayOfWireInfo[i].setExtInfo(null);
      }
      paramArrayOfWireInfo = jdMethod_if(paramFFSConnectionHolder, paramArrayOfWireInfo);
      for (i = 0; i < paramArrayOfWireInfo.length; i++) {
        paramArrayOfWireInfo[i].setExtInfo(arrayOfHashtable[i]);
      }
      FFSDebug.log(str, "done", 6);
      return paramArrayOfWireInfo;
    }
    catch (Exception localException)
    {
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str + "failed");
    }
  }
  
  public static void limitCheckWireAdd(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "WireProcessor.limitCheckWireAdd: ";
    FFSDebug.log(str1 + "start.", 6);
    int i = LimitCheckApprovalProcessor.processWireAdd(paramFFSConnectionHolder, paramWireInfo, paramHashMap);
    String str2 = LimitCheckApprovalProcessor.mapToStatus(i);
    FFSDebug.log(str1 + ": retStatus LimitCheck:[", str2, "]", 6);
    if (("LIMIT_CHECK_FAILED".equalsIgnoreCase(str2)) || ("LIMIT_REVERT_FAILED".equalsIgnoreCase(str2)) || ("APPROVAL_FAILED".equalsIgnoreCase(str2))) {
      paramWireInfo.setPrcStatus(str2);
    } else if ("APPROVAL_PENDING".equalsIgnoreCase(str2)) {
      paramWireInfo.setPrcStatus("APPROVAL_PENDING");
    } else if ("APPROVAL_NOT_ALLOWED".equalsIgnoreCase(str2)) {
      paramWireInfo.setPrcStatus("APPROVAL_NOT_ALLOWED");
    } else {
      paramWireInfo.setPrcStatus("CREATED");
    }
    FFSDebug.log(str1 + ": wireInstance: " + paramWireInfo, 6);
    FFSDebug.log(str1 + "end.", 6);
  }
  
  private WireInfo a(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo1, WireInfo paramWireInfo2, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "WireProcessor.handlePayeeForModWire: ";
    WirePayeeInfo localWirePayeeInfo1 = null;
    WirePayeeInfo localWirePayeeInfo2 = null;
    String str2 = null;
    FFSDebug.log(str1, "Starts", 6);
    FFSDebug.log(str1, "wireInfo = " + paramWireInfo1, 6);
    FFSDebug.log(str1, "wirePayeeInfo = " + paramWireInfo1.getWirePayeeInfo(), 6);
    try
    {
      if (paramBoolean2)
      {
        localWirePayeeInfo1 = paramWireInfo1.getWirePayeeInfo();
        localWirePayeeInfo2 = paramWireInfo2.getWirePayeeInfo();
        str2 = paramWireInfo1.getWirePayeeId();
      }
      else
      {
        localWirePayeeInfo1 = paramWireInfo1.getWireCreditInfo();
        localWirePayeeInfo2 = paramWireInfo2.getWireCreditInfo();
        str2 = paramWireInfo1.getWireCreditId();
      }
      String str3 = paramWireInfo2.getWireType();
      str4 = paramWireInfo2.getTemplateId();
      str5 = paramWireInfo2.getRecSrvrTid();
      FFSDebug.log(str1, "wireType = " + str3, 6);
      FFSDebug.log(str1, "templateId = " + str4, 6);
      FFSDebug.log(str1, "recurringId = " + str5, 6);
      if (localWirePayeeInfo1 != null)
      {
        String str6 = localWirePayeeInfo1.getPayeeGroup();
        FFSDebug.log(str1, "modified payeeGroup = " + str6, 6);
        String str7;
        if ((str2 == null) && (!"UNMANAGED".equals(str6)) && (!"SECURE".equals(str6)))
        {
          if ((localWirePayeeInfo2 != null) && ((localWirePayeeInfo2.getPayeeGroup().equals("UNMANAGED")) || (localWirePayeeInfo2.getPayeeGroup().equals("SECURE"))) && ("SINGLE".equalsIgnoreCase(str3)) && (str4 == null) && (str5 == null))
          {
            WirePayee.delete(paramFFSConnectionHolder, localWirePayeeInfo2);
            if (localWirePayeeInfo2.getStatusCode() != 0)
            {
              str7 = "failed: " + localWirePayeeInfo2.getStatusMsg();
              FFSDebug.log(str1, str7, 0);
              paramWireInfo1.setStatusCode(localWirePayeeInfo2.getStatusCode());
              paramWireInfo1.setStatusMsg(localWirePayeeInfo2.getStatusMsg());
              if (this.B >= 1) {
                a(paramWireInfo1, "Mod", null, paramBoolean1);
              }
              return paramWireInfo1;
            }
            WirePayeeProcessor.logAudit(paramFFSConnectionHolder, localWirePayeeInfo1, "Can");
          }
          if (paramBoolean2) {
            jdMethod_for(paramFFSConnectionHolder, paramWireInfo1);
          } else {
            jdMethod_new(paramFFSConnectionHolder, paramWireInfo1);
          }
          if (paramWireInfo1.getStatusCode() != 0)
          {
            if (this.B >= 1) {
              a(paramWireInfo1, "Mod", null, paramBoolean1);
            }
            return paramWireInfo1;
          }
        }
        else if ((str2 == null) && (("UNMANAGED".equals(str6)) || ("SECURE".equals(str6))))
        {
          if ((localWirePayeeInfo2 != null) && ((localWirePayeeInfo2.getPayeeGroup().equals("UNMANAGED")) || (localWirePayeeInfo2.getPayeeGroup().equals("SECURE"))))
          {
            if (("SINGLE".equalsIgnoreCase(str3)) && (str4 != null))
            {
              if (paramBoolean2) {
                jdMethod_for(paramFFSConnectionHolder, paramWireInfo1);
              } else {
                jdMethod_new(paramFFSConnectionHolder, paramWireInfo1);
              }
              if (paramWireInfo1.getStatusCode() != 0)
              {
                if (this.B >= 1) {
                  a(paramWireInfo1, "Mod", null, paramBoolean1);
                }
                return paramWireInfo1;
              }
            }
            else if (("SINGLE".equalsIgnoreCase(str3)) && (str4 == null))
            {
              localWirePayeeInfo1.setPayeeId(localWirePayeeInfo2.getPayeeId());
              WirePayee.updatePayee(paramFFSConnectionHolder, localWirePayeeInfo1);
              if (localWirePayeeInfo1.getStatusCode() != 0)
              {
                str7 = "failed: " + localWirePayeeInfo1.getStatusMsg();
                FFSDebug.log(str1, str7, 0);
                paramWireInfo1.setStatusCode(localWirePayeeInfo1.getStatusCode());
                paramWireInfo1.setStatusMsg(localWirePayeeInfo1.getStatusMsg());
                if (this.B >= 1) {
                  a(paramWireInfo1, "Mod", null, paramBoolean1);
                }
                return paramWireInfo1;
              }
              WirePayeeProcessor.logAudit(paramFFSConnectionHolder, localWirePayeeInfo1, "Mod");
              if (paramBoolean2) {
                paramWireInfo1.setWirePayeeId(localWirePayeeInfo1.getPayeeId());
              } else {
                paramWireInfo1.setWireCreditId(localWirePayeeInfo1.getPayeeId());
              }
            }
            else
            {
              localWirePayeeInfo1.setPayeeId(localWirePayeeInfo2.getPayeeId());
              WirePayee.modifyPayee(paramFFSConnectionHolder, localWirePayeeInfo1);
              if (localWirePayeeInfo1.getStatusCode() != 0)
              {
                str7 = "failed: " + localWirePayeeInfo1.getStatusMsg();
                FFSDebug.log(str1, str7, 0);
                paramWireInfo1.setStatusCode(localWirePayeeInfo1.getStatusCode());
                paramWireInfo1.setStatusMsg(localWirePayeeInfo1.getStatusMsg());
                if (this.B >= 1) {
                  a(paramWireInfo1, "Mod", null, paramBoolean1);
                }
                return paramWireInfo1;
              }
              WirePayeeProcessor.logAudit(paramFFSConnectionHolder, localWirePayeeInfo1, "Mod");
              if (paramBoolean2) {
                paramWireInfo1.setWirePayeeId(localWirePayeeInfo1.getPayeeId());
              } else {
                paramWireInfo1.setWireCreditId(localWirePayeeInfo1.getPayeeId());
              }
            }
          }
          else
          {
            if (paramBoolean2) {
              jdMethod_for(paramFFSConnectionHolder, paramWireInfo1);
            } else {
              jdMethod_new(paramFFSConnectionHolder, paramWireInfo1);
            }
            if (paramWireInfo1.getStatusCode() != 0)
            {
              if (this.B >= 1) {
                a(paramWireInfo1, "Mod", null, paramBoolean1);
              }
              return paramWireInfo1;
            }
          }
        }
        else if ((str2 != null) && (("UNMANAGED".equals(str6)) || ("SECURE".equals(str6))) && ("mod".equals(localWirePayeeInfo1.getAction())))
        {
          if (!str2.equals(localWirePayeeInfo2.getPayeeId()))
          {
            paramWireInfo1.setStatusCode(19850);
            paramWireInfo1.setStatusMsg(BPWLocaleUtil.getMessage(19850, null, "WIRE_MESSAGE"));
            if (this.B >= 1) {
              a(paramWireInfo1, "Mod", null, paramBoolean1);
            }
            return paramWireInfo1;
          }
          if (("SINGLE".equalsIgnoreCase(str3)) && (str4 != null))
          {
            FFSDebug.log(str1, "handling single wire created from template", 6);
            FFSDebug.log(str1, "Calling hasTemplatedPayee", 6);
            if (Wire.hasTemplatedPayee(paramFFSConnectionHolder, paramWireInfo1))
            {
              if (paramBoolean2) {
                jdMethod_for(paramFFSConnectionHolder, paramWireInfo1);
              } else {
                jdMethod_new(paramFFSConnectionHolder, paramWireInfo1);
              }
              if (paramWireInfo1.getStatusCode() != 0)
              {
                if (this.B >= 1) {
                  a(paramWireInfo1, "Mod", null, paramBoolean1);
                }
                return paramWireInfo1;
              }
            }
            else
            {
              WirePayee.updatePayee(paramFFSConnectionHolder, localWirePayeeInfo1);
              if (localWirePayeeInfo1.getStatusCode() != 0)
              {
                str7 = "failed: " + localWirePayeeInfo1.getStatusMsg();
                FFSDebug.log(str1, str7, 0);
                paramWireInfo1.setStatusCode(localWirePayeeInfo1.getStatusCode());
                paramWireInfo1.setStatusMsg(localWirePayeeInfo1.getStatusMsg());
                if (this.B >= 1) {
                  a(paramWireInfo1, "Mod", null, paramBoolean1);
                }
                return paramWireInfo1;
              }
            }
          }
          else if (("SINGLE".equalsIgnoreCase(str3)) && (str4 == null))
          {
            FFSDebug.log(str1, "handling single wire not created from template", 6);
            WirePayee.updatePayee(paramFFSConnectionHolder, localWirePayeeInfo1);
            if (localWirePayeeInfo1.getStatusCode() != 0)
            {
              str7 = "failed: " + localWirePayeeInfo1.getStatusMsg();
              FFSDebug.log(str1, str7, 0);
              paramWireInfo1.setStatusCode(localWirePayeeInfo1.getStatusCode());
              paramWireInfo1.setStatusMsg(localWirePayeeInfo1.getStatusMsg());
              if (this.B >= 1) {
                a(paramWireInfo1, "Mod", null, paramBoolean1);
              }
              return paramWireInfo1;
            }
            WirePayeeProcessor.logAudit(paramFFSConnectionHolder, localWirePayeeInfo1, "Mod");
          }
          else
          {
            FFSDebug.log(str1, "handling Template or Recurring model", 6);
            if (!localWirePayeeInfo1.equals(localWirePayeeInfo2))
            {
              FFSDebug.log(str1, "payee has been modified", 6);
              WirePayee.modifyPayee(paramFFSConnectionHolder, localWirePayeeInfo1);
              if (localWirePayeeInfo1.getStatusCode() != 0)
              {
                str7 = "failed: " + localWirePayeeInfo1.getStatusMsg();
                FFSDebug.log(str1, str7, 0);
                paramWireInfo1.setStatusCode(localWirePayeeInfo1.getStatusCode());
                paramWireInfo1.setStatusMsg(localWirePayeeInfo1.getStatusMsg());
                if (this.B >= 1) {
                  a(paramWireInfo1, "Mod", null, paramBoolean1);
                }
                return paramWireInfo1;
              }
              WirePayeeProcessor.logAudit(paramFFSConnectionHolder, localWirePayeeInfo1, "Mod");
            }
            else
            {
              FFSDebug.log(str1, "payee info is the same", 6);
            }
          }
        }
      }
      paramWireInfo1.setStatusCode(0);
      paramWireInfo1.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    }
    catch (Throwable localThrowable)
    {
      String str4 = str1 + "failed. ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4 + str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    return paramWireInfo1;
  }
  
  public BPWHist getDuplicateWires(WireInfo paramWireInfo)
    throws FFSException
  {
    String str1 = "Wire.getDuplicateWires: ";
    FFSDebug.log(str1, "start", 6);
    BPWHist localBPWHist1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localBPWHist1 = Wire.getDuplicateWires(localFFSConnectionHolder, paramWireInfo);
      localFFSConnectionHolder.conn.commit();
      BPWHist localBPWHist2 = localBPWHist1;
      return localBPWHist2;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private boolean a(WireBatchInfo paramWireBatchInfo1, WireBatchInfo paramWireBatchInfo2)
  {
    FFSDebug.log("WireProcessor.doAllWireNeedUpdate: ", "start", 6);
    String str1 = paramWireBatchInfo2.getBatchDest();
    String str2;
    String str3;
    if (str1.equals("INTERNATIONAL"))
    {
      str2 = paramWireBatchInfo1.getDateDue();
      str3 = paramWireBatchInfo2.getDateDue();
      String str4 = paramWireBatchInfo1.getSettlementDate();
      String str5 = paramWireBatchInfo2.getSettlementDate();
      String str6 = paramWireBatchInfo1.getOrigCurrency();
      String str7 = paramWireBatchInfo2.getOrigCurrency();
      String str8 = paramWireBatchInfo1.getDestCurrency();
      String str9 = paramWireBatchInfo2.getDestCurrency();
      String str10 = paramWireBatchInfo1.getExchangeRate();
      String str11 = paramWireBatchInfo2.getExchangeRate();
      String str12 = paramWireBatchInfo1.getContractNumber();
      String str13 = paramWireBatchInfo2.getContractNumber();
      String str14 = paramWireBatchInfo1.getMathRule();
      String str15 = paramWireBatchInfo2.getMathRule();
      if ((str2 != null) && (str2.length() > 6)) {
        str2 = str2.substring(0, 6);
      }
      if ((str3 != null) && (str3.length() > 6)) {
        str3 = str3.substring(0, 6);
      }
      if (((str2 != null) && (!str2.equals(str3))) || ((str2 == null) && (str3 != null)))
      {
        FFSDebug.log("WireProcessor.doAllWireNeedUpdate: ", "Old DueDate=", str2, ",  New DueDate=", str3, 6);
        return true;
      }
      if (((str4 != null) && (!str4.equals(str5))) || ((str4 == null) && (str5 != null)))
      {
        FFSDebug.log("WireProcessor.doAllWireNeedUpdate: ", "Old SettlementDate=", str4, ",  New SettlementDate=", str5, 6);
        return true;
      }
      if (((str6 != null) && (!str6.equals(str7))) || ((str6 == null) && (str7 != null)))
      {
        FFSDebug.log("WireProcessor.doAllWireNeedUpdate: ", "Old OrigCurrency=", str6, ",  New OrigCurrency=", str7, 6);
        return true;
      }
      if (((str8 != null) && (!str8.equals(str9))) || ((str8 == null) && (str9 != null)))
      {
        FFSDebug.log("WireProcessor.doAllWireNeedUpdate: ", "Old DestCurrency=", str8, ",  New DestCurrency=", str9, 6);
        return true;
      }
      if (((str10 != null) && (!str10.equals(str11))) || ((str10 == null) && (str11 != null)))
      {
        FFSDebug.log("WireProcessor.doAllWireNeedUpdate: ", "Old ExchangeRate=", str10, ",  New ExchangeRate=", str11, 6);
        return true;
      }
      if (((str12 != null) && (!str12.equals(str13))) || ((str12 == null) && (str13 != null)))
      {
        FFSDebug.log("WireProcessor.doAllWireNeedUpdate: ", "Old ContractNum=", str12, ",  New ContractNum=", str13, 6);
        return true;
      }
      if (((str14 != null) && (!str14.equals(str15))) || ((str14 == null) && (str15 != null)))
      {
        FFSDebug.log("WireProcessor.doAllWireNeedUpdate: ", "Old MathRule=", str14, ",  New MathRule=", str15, 6);
        return true;
      }
    }
    else
    {
      str2 = paramWireBatchInfo1.getDateDue();
      str3 = paramWireBatchInfo2.getDateDue();
      FFSDebug.log("WireProcessor.doAllWireNeedUpdate: ", "Old DueDate=", str2, ",  New DueDate=", str3, 6);
      if ((str2 != null) && (str2.length() > 6)) {
        str2 = str2.substring(0, 6);
      }
      if ((str3 != null) && (str3.length() > 6)) {
        str3 = str3.substring(0, 6);
      }
      if (((str2 != null) && (!str2.equals(str3))) || ((str2 == null) && (str3 != null))) {
        return true;
      }
    }
    FFSDebug.log("WireProcessor.doAllWireNeedUpdate: ", "done", 6);
    return false;
  }
  
  public BankingDays getBankingDaysInRange(BankingDays paramBankingDays, HashMap paramHashMap)
    throws FFSException
  {
    String str = "WireProcessor.getBankingDaysInRange: ";
    FFSDebug.log(str + "start: CompId= " + paramBankingDays.getCompId() + ", SEC= " + paramBankingDays.getSec() + ", startDate= " + DBUtil.calendarToString(paramBankingDays.getStartDate()) + ", endDate = " + DBUtil.calendarToString(paramBankingDays.getEndDate()), 6);
    int i = (int)((paramBankingDays.getEndDate().getTimeInMillis() - paramBankingDays.getStartDate().getTimeInMillis()) / 86400000L + 1L);
    boolean[] arrayOfBoolean = new boolean[i];
    try
    {
      Calendar localCalendar1 = (Calendar)paramBankingDays.getStartDate().clone();
      Calendar localCalendar2 = paramBankingDays.getEndDate();
      Calendar localCalendar3 = a(localCalendar1, paramBankingDays, paramHashMap);
      localCalendar1 = paramBankingDays.getStartDate();
      int j = 0;
      while (localCalendar1.before(localCalendar3))
      {
        arrayOfBoolean[j] = false;
        j++;
        localCalendar1.add(5, 1);
      }
      if (localCalendar1.equals(localCalendar3))
      {
        arrayOfBoolean[j] = true;
        j++;
        localCalendar1.add(5, 1);
      }
      while ((localCalendar1.before(localCalendar2)) || (localCalendar1.equals(localCalendar2)))
      {
        int k = BPWUtil.calendarToDueDateFormatInt(localCalendar1);
        int m = SmartCalendar.getPayday(paramBankingDays.getFiID(), k);
        if (k == m) {
          arrayOfBoolean[j] = true;
        } else {
          arrayOfBoolean[j] = false;
        }
        j++;
        localCalendar1.add(5, 1);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log(str + localException.toString(), 0);
      FFSDebug.stackTrace(localException);
      throw new FFSException(localException, "Unable to determine a valid Wire due date");
    }
    paramBankingDays.setBankingDays(arrayOfBoolean);
    return paramBankingDays;
  }
  
  private static Calendar a(Calendar paramCalendar, BankingDays paramBankingDays, HashMap paramHashMap)
    throws Exception
  {
    Calendar localCalendar = Calendar.getInstance();
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    String[] arrayOfString3 = null;
    String[] arrayOfString4 = null;
    if ((paramCalendar.equals(localCalendar)) || (paramCalendar.before(localCalendar)))
    {
      String str1 = (String)paramHashMap.get("WireDest");
      String str2 = paramBankingDays.getCustomerID();
      ProcessingWindowList localProcessingWindowList = new ProcessingWindowList();
      arrayOfString3 = new String[1];
      arrayOfString3[0] = paramBankingDays.getFiID();
      localProcessingWindowList.setFIId(arrayOfString3);
      arrayOfString1 = new String[1];
      arrayOfString1[0] = "WIRES";
      localProcessingWindowList.setPmtType(arrayOfString1);
      arrayOfString2 = new String[1];
      arrayOfString2[0] = str1;
      localProcessingWindowList.setPmtSubType(arrayOfString2);
      arrayOfString4 = new String[1];
      arrayOfString4[0] = str2;
      localProcessingWindowList.setCustomerId(arrayOfString4);
      boolean bool = a(localProcessingWindowList);
      if (!bool) {
        paramCalendar.add(5, 1);
      }
    }
    return paramCalendar;
  }
  
  private static boolean a(ProcessingWindowList paramProcessingWindowList)
    throws FFSException
  {
    String str1 = "WireProcessor.isProcessingWindowOpen: ";
    FFSDebug.log(str1, "start searchCriteria: " + paramProcessingWindowList, 6);
    String str2 = null;
    ProcessingWindowInfo[] arrayOfProcessingWindowInfo = null;
    int i = 0;
    SimpleDateFormat localSimpleDateFormat = null;
    localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    Date localDate = new Date();
    str2 = localSimpleDateFormat.format(localDate);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramProcessingWindowList = DBPropertyConfig.getProcessingWindows(localFFSConnectionHolder, paramProcessingWindowList);
      if (paramProcessingWindowList.getStatusCode() == 0)
      {
        arrayOfProcessingWindowInfo = paramProcessingWindowList.getProcessingWindows();
        for (j = 0; j < arrayOfProcessingWindowInfo.length; j++)
        {
          if (a(arrayOfProcessingWindowInfo[j].getStartTime(), arrayOfProcessingWindowInfo[j].getCloseTime(), str2))
          {
            i = 1;
            break;
          }
          i = 0;
        }
      }
      if (paramProcessingWindowList.getStatusCode() == 16020)
      {
        paramProcessingWindowList.setCustomerId(null);
        paramProcessingWindowList = DBPropertyConfig.getProcessingWindows(localFFSConnectionHolder, paramProcessingWindowList);
        if (paramProcessingWindowList.getStatusCode() == 0)
        {
          arrayOfProcessingWindowInfo = paramProcessingWindowList.getProcessingWindows();
          for (j = 0; j < arrayOfProcessingWindowInfo.length; j++)
          {
            if (a(arrayOfProcessingWindowInfo[j].getStartTime(), arrayOfProcessingWindowInfo[j].getCloseTime(), str2))
            {
              i = 1;
              break;
            }
            i = 0;
          }
        }
        if (paramProcessingWindowList.getStatusCode() == 16020) {
          i = 1;
        } else {
          i = 0;
        }
      }
      else
      {
        i = 0;
      }
      int j = i;
      return j;
    }
    catch (Exception localException)
    {
      FFSDebug.stackTrace(localException);
      throw new FFSException(localException, "Unable to retrieve wire processing windows");
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.WireProcessor
 * JD-Core Version:    0.7.0.1
 */