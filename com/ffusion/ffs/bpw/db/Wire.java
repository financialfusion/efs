package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.BPWInfoBase;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecWireInfo;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.ffs.bpw.interfaces.WireBatchInfo;
import com.ffusion.ffs.bpw.interfaces.WireHistoryInfo;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.ffs.bpw.interfaces.WireReleaseInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.beans.PagingContext;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class Wire
  implements FFSConst, ACHConsts, DBConsts, BPWResource
{
  private static final String yG = " ORDER BY SrvrTID";
  private static final String yz = " ORDER BY a.SrvrTID";
  private static final String yI = " ORDER BY RecSrvrTID";
  private static final String yH = " ORDER BY a.RecSrvrTID";
  private static final String yJ = " ORDER BY BatchId";
  private static final String yF = "com.ffusion.ffs.scheduling.db.ScheduleInfo";
  private static final String yB = "getPendingDatesByStartAndEndDate";
  private static final String yE = "getScheduleInfo";
  private static final String yD = "UPDATE BPW_WireInfo SET Status=? ";
  private static final String yC = " WHERE SrvrTID=?";
  private static final String[] yA = new String[4];
  private static int yy = 0;
  
  public Wire()
  {
    yA[0] = "CREATED";
    yA[1] = "RELEASEPENDING";
    yA[2] = "WILLPROCESSON";
    yA[3] = "MIXED_PENDING";
  }
  
  public static WireInfo getWireInfo(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    FFSDebug.log("Wire.getWireInfo: start, wireInfo: ", paramWireInfo.toString(), 6);
    if (paramWireInfo == null) {
      return paramWireInfo;
    }
    String str = paramWireInfo.getSrvrTid();
    if ((str == null) || (str.length() == 0))
    {
      localObject = "Wire.getWireInfo: Failed, Invalid transaction ID: " + str;
      FFSDebug.log((String)localObject, 6);
      throw new FFSException((String)localObject);
    }
    Object localObject = getWireInfo(paramFFSConnectionHolder, paramWireInfo, false, true);
    FFSDebug.log("Wire.getWireInfo: done", 6);
    if (((WireInfo)localObject).getStatusCode() == 16020) {
      localObject = null;
    }
    return localObject;
  }
  
  private static int al()
    throws FFSException
  {
    if (yy == 0)
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      String str = localPropertyConfig.otherProperties.getProperty("bpw.paging.maximum.sessionsize", "1000");
      yy = Integer.parseInt(str);
    }
    return yy;
  }
  
  private static void jdMethod_new(PagingInfo paramPagingInfo, StringBuffer paramStringBuffer, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Wire.createPagingSearchCriteria";
    FFSDebug.log(str1 + " : start ", 6);
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    String str2 = null;
    String str3 = null;
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    String[] arrayOfString3 = null;
    String[] arrayOfString4 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    if (paramStringBuffer == null) {
      paramStringBuffer = new StringBuffer();
    }
    if (paramArrayList == null) {
      paramArrayList = new ArrayList();
    }
    Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramPagingInfo.getStartDate()));
    Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramPagingInfo.getEndDate()));
    paramArrayList.add(localInteger1.toString());
    paramArrayList.add(localInteger2.toString());
    str2 = (String)localHashMap2.get("CustomerId");
    if ((str2 != null) && (str2.trim().length() != 0))
    {
      paramStringBuffer.append(" AND a.CustomerID = ? ");
      paramArrayList.add(str2);
    }
    str3 = (String)localHashMap2.get("FIID");
    if ((str3 != null) && (str3.trim().length() != 0))
    {
      paramStringBuffer.append(" AND FIID = ? ");
      paramArrayList.add(str3);
    }
    arrayOfString1 = (String[])localHashMap2.get("SubmittedBys");
    int i;
    if ((arrayOfString1 != null) && (arrayOfString1.length > 0))
    {
      FFSDebug.log(str1 + " submittedBy.length:" + arrayOfString1.length, 6);
      if (arrayOfString1.length == 1)
      {
        paramStringBuffer.append(" AND a.SubmittedBy = ? ");
        paramArrayList.add(arrayOfString1[0]);
      }
      else
      {
        paramStringBuffer.append(" AND a.SubmittedBy IN (?");
        paramArrayList.add(arrayOfString1[0]);
        for (i = 1; i < arrayOfString1.length; i++)
        {
          paramStringBuffer.append(", ?");
          paramArrayList.add(arrayOfString1[i]);
        }
        paramStringBuffer.append(")");
      }
    }
    arrayOfString2 = (String[])localHashMap2.get("UserIds");
    if ((arrayOfString2 != null) && (arrayOfString2.length > 0))
    {
      FFSDebug.log(str1 + " : userIds.length:" + arrayOfString2.length, 6);
      if (arrayOfString2.length == 1)
      {
        paramStringBuffer.append(" AND a.UserId = ? ");
        paramArrayList.add(arrayOfString2[0]);
      }
      else
      {
        paramStringBuffer.append(" AND a.UserId IN (?");
        paramArrayList.add(arrayOfString2[0]);
        for (i = 1; i < arrayOfString2.length; i++)
        {
          paramStringBuffer.append(", ?");
          paramArrayList.add(arrayOfString2[i]);
        }
        paramStringBuffer.append(")");
      }
    }
    arrayOfString4 = (String[])localHashMap2.get("TransType");
    if ((arrayOfString4 != null) && (arrayOfString4.length > 0))
    {
      FFSDebug.log(str1 + " : transType.length:" + arrayOfString4.length, 6);
      if (arrayOfString4.length == 1)
      {
        paramStringBuffer.append(" AND a.WireType = ? ");
        paramArrayList.add(arrayOfString4[0]);
      }
      else
      {
        paramStringBuffer.append(" AND a.WireType IN (?");
        paramArrayList.add(arrayOfString4[0]);
        for (i = 1; i < arrayOfString4.length; i++)
        {
          paramStringBuffer.append(", ?");
          paramArrayList.add(arrayOfString4[i]);
        }
        paramStringBuffer.append(")");
      }
    }
    str4 = (String)localHashMap2.get("TransScope");
    if ((str4 != null) && (str4.trim().length() != 0))
    {
      paramStringBuffer.append(" AND WireScope = ? ");
      paramArrayList.add(str4);
    }
    str5 = (String)localHashMap2.get("TransSource");
    if ((str5 != null) && (str5.trim().length() != 0))
    {
      paramStringBuffer.append(" AND WireSource = ? ");
      paramArrayList.add(str5);
    }
    arrayOfString3 = (String[])localHashMap2.get("Dests");
    if ((arrayOfString3 != null) && (arrayOfString3.length > 0))
    {
      FFSDebug.log(str1 + " : dests.length:" + arrayOfString3.length, 6);
      if (arrayOfString3.length == 1)
      {
        paramStringBuffer.append(" AND a.WireDest = ? ");
        paramArrayList.add(arrayOfString3[0]);
      }
      else
      {
        paramStringBuffer.append(" AND a.WireDest IN (?");
        paramArrayList.add(arrayOfString3[0]);
        for (i = 1; i < arrayOfString3.length; i++)
        {
          paramStringBuffer.append(", ?");
          paramArrayList.add(arrayOfString3[i]);
        }
        paramStringBuffer.append(")");
      }
    }
    str6 = (String)localHashMap2.get("TemplateId");
    if ((str6 != null) && (str6.trim().length() != 0))
    {
      paramStringBuffer.append(" AND TemplateId = ? ");
      paramArrayList.add(str6);
    }
    str7 = (String)localHashMap2.get("MinAmount");
    if ((str7 != null) && (str7.trim().length() != 0))
    {
      paramStringBuffer.append(" AND cast (Amount as decimal) >= ? ");
      paramArrayList.add(new BigDecimal(str7));
    }
    str8 = (String)localHashMap2.get("MaxAmount");
    if ((str8 != null) && (str8.trim().length() != 0))
    {
      paramStringBuffer.append(" AND cast (Amount as decimal) <= ? ");
      paramArrayList.add(new BigDecimal(str8));
    }
    FFSDebug.log(str1 + " : where clause as " + paramStringBuffer.toString(), 6);
    FFSDebug.log(str1 + " : end. ", 6);
  }
  
  public static void createSessionData(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    jdMethod_new(paramPagingInfo, localStringBuffer, localArrayList);
    String str = localStringBuffer.toString();
    String[] arrayOfString = (String[])localHashMap2.get("TransType");
    paramPagingInfo.setTotalTrans(0);
    if ((arrayOfString == null) || (arrayOfString.length == 0))
    {
      jdMethod_char(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      if (paramPagingInfo.getStatusCode() != 0) {
        return;
      }
      jdMethod_goto(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      if (paramPagingInfo.getStatusCode() != 0) {
        return;
      }
      jdMethod_else(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      if (paramPagingInfo.getStatusCode() != 0) {
        return;
      }
      jdMethod_long(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      if (paramPagingInfo.getStatusCode() == 0) {}
    }
    else
    {
      if ((jdMethod_if(arrayOfString, "SINGLE")) || (jdMethod_if(arrayOfString, "TEMPLATE")) || (jdMethod_if(arrayOfString, "BATCHWIRE")))
      {
        jdMethod_char(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
        if (paramPagingInfo.getStatusCode() != 0) {
          return;
        }
      }
      if (jdMethod_if(arrayOfString, "RECURRING"))
      {
        jdMethod_goto(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
        if (paramPagingInfo.getStatusCode() != 0) {
          return;
        }
      }
      if (jdMethod_if(arrayOfString, "RECTEMPLATE"))
      {
        jdMethod_else(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
        if (paramPagingInfo.getStatusCode() != 0) {
          return;
        }
      }
      if (jdMethod_if(arrayOfString, "BATCH"))
      {
        jdMethod_long(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
        if (paramPagingInfo.getStatusCode() != 0) {}
      }
    }
  }
  
  private static void jdMethod_char(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Wire.createSessionDataWire";
    FFSDebug.log(str1 + " : start", 6);
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = localPropertyConfig.getBatchSize();
      int j = paramPagingInfo.getTotalTrans();
      WireHistoryInfo localWireHistoryInfo = null;
      String str3 = paramPagingInfo.getPagingContext().getSessionId();
      if ((str3 == null) || (str3.trim().length() == 0))
      {
        str3 = DBUtil.getNextIndexString("SessionID");
        paramPagingInfo.getPagingContext().setSessionId(str3);
      }
      StringBuffer localStringBuffer = new StringBuffer("SELECT a.SrvrTID,BankFromID,BranchFromID,CustomerID,WirePayeeID,RecSrvrTID,FIID,Amount,AmtCurrency,DestCurrency,XferFee,AcctDebitID, AcctDebitType,AcctDebitKey,DateCreate,DateDue,DateToPost,DatePosted, WireType,WireCategory,WireGroup,WireDest, BatchId, ContractNumber, Status,LogID,ConfirmNum, ConfirmNum2, ConfirmMsg, PayInstruct, Memo,ExchangeRate, SubmittedBy, ExtId, WireSource, WireName, NickName, WireLimit, SettlementDate,WireScope, MathRule, OrigToBeneficiaryMemo, OrigToBeneficiaryInfo, BanktoBankInfo, ProcessedBy,TemplateId, AgentId, AgentName, Method, AmountType, CustomerRef, OriginatorCharges, ReceiverCharges, WireChargesDetails, OrgChargesAccountNum, BenefChargesAccountNum,HostId, UserId,AgentType,OrigAmount,OrigCurrency, WireCreditID, b.Version  FROM BPW_WireInfo a, BPW_WireInfo2 b WHERE a.SrvrTID = b.SrvrTID AND DateToPost >= ? AND DateToPost <= ?");
      if (paramString != null) {
        localStringBuffer.append(paramString);
      }
      String[] arrayOfString1 = (String[])localHashMap2.get("StatusList");
      if ((arrayOfString1 != null) && (arrayOfString1.length > 0))
      {
        localStringBuffer.append(" AND Status IN (?");
        paramArrayList.add(arrayOfString1[0]);
        for (int k = 1; k < arrayOfString1.length; k++)
        {
          localStringBuffer.append(", ?");
          paramArrayList.add(arrayOfString1[k]);
        }
        localStringBuffer.append(")");
      }
      String[] arrayOfString2 = (String[])localHashMap2.get("TransType");
      if ((arrayOfString2 != null) && (arrayOfString2.length > 0)) {
        if (((jdMethod_if(arrayOfString2, "SINGLE")) || (jdMethod_if(arrayOfString2, "TEMPLATE"))) && (!jdMethod_if(arrayOfString2, "BATCHWIRE"))) {
          localStringBuffer.append(" AND BatchId IS NULL ");
        } else if ((!jdMethod_if(arrayOfString2, "SINGLE")) && (!jdMethod_if(arrayOfString2, "TEMPLATE")) && (jdMethod_if(arrayOfString2, "BATCHWIRE"))) {
          localStringBuffer.append(" AND BatchId IS NOT NULL ");
        }
      }
      localStringBuffer.append(" ORDER BY a.SrvrTID");
      FFSDebug.log(str1 + "Sql Statement: " + localStringBuffer.toString(), 6);
      for (int m = 0; m < paramArrayList.size(); m++) {
        FFSDebug.log(str1 + " Sql Param:" + m + " :" + String.valueOf(paramArrayList.get(m)), 6);
      }
      Object[] arrayOfObject = paramArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        j++;
        if (j > al())
        {
          paramPagingInfo.setStatusCode(28020);
          paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
          break;
        }
        localWireHistoryInfo = new WireHistoryInfo();
        localWireHistoryInfo.setCursorId(Integer.toString(j));
        localWireHistoryInfo.setTransType("SINGLE");
        a(localWireHistoryInfo, localFFSResultSet);
        String str4 = localFFSResultSet.getColumnString("WireType");
        if (str4.equalsIgnoreCase("RECURRING")) {
          str4 = "RECURRING_SINGLE";
        }
        localWireHistoryInfo.setTransType(str4);
        WireTempHist.createTempHist(paramFFSConnectionHolder, str3, j, localWireHistoryInfo);
        if (j % i == 0) {
          paramFFSConnectionHolder.conn.commit();
        }
      }
      paramPagingInfo.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1 + " : end", 6);
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + " failed : ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + " failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  private static void jdMethod_goto(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Wire.createSessionDataRecWire";
    FFSDebug.log(str1 + " : start", 6);
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = localPropertyConfig.getBatchSize();
      int j = paramPagingInfo.getTotalTrans();
      WireHistoryInfo localWireHistoryInfo = null;
      String str3 = paramPagingInfo.getPagingContext().getSessionId();
      if ((str3 == null) || (str3.trim().length() == 0))
      {
        str3 = DBUtil.getNextIndexString("SessionID");
        paramPagingInfo.getPagingContext().setSessionId(str3);
      }
      boolean bool = true;
      String[] arrayOfString = (String[])localHashMap2.get("StatusList");
      if ((arrayOfString != null) && (arrayOfString.length > 0)) {
        bool = jdMethod_if(arrayOfString, "CREATED");
      }
      if (bool)
      {
        int k = DBUtil.getCurrentStartDate();
        int m = BPWUtil.getDateDBFormat(paramPagingInfo.getEndDate());
        int n = (m - k) / 10000;
        if (n >= 0)
        {
          StringBuffer localStringBuffer = new StringBuffer("SELECT a.RecSrvrTID, BankFromID, BranchFromID, CustomerID,WirePayeeID, FIID, Amount, AcctDebitID,AcctDebitType,AcctDebitKey, WireType,WireCategory,WireGroup,WireDest,Status,LogID,PayInstruct,Memo,AmtCurrency,DestCurrency,XferFee, StartAmount,EndAmount,DateCreate,Frequency,StartDate,EndDate,InstanceCount,SubmittedBy, ExchangeRate, BatchId, ContractNumber, ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,AgentId,AgentName,Method, ProcessedBy, TemplateId,CustomerRef,OriginatorCharges,ReceiverCharges, WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum, UserId,AgentType,OrigAmount,OrigCurrency,WireCreditID, b.Version  FROM BPW_RecWireInfo a, BPW_RecWireInfo2 b WHERE a.RecSrvrTID = b.RecSrvrTID AND a.EndDate > ? AND a.StartDate < ? ");
          if (paramString != null) {
            localStringBuffer.append(paramString);
          }
          localStringBuffer.append(" AND Status ='WILLPROCESSON' ");
          localStringBuffer.append(" AND BatchId IS NULL ");
          localStringBuffer.append(" ORDER BY a.RecSrvrTID");
          FFSDebug.log(str1 + "Sql Statement: " + localStringBuffer.toString(), 6);
          for (int i1 = 0; i1 < paramArrayList.size(); i1++) {
            FFSDebug.log(str1 + " Sql Param:" + i1 + " :" + String.valueOf(paramArrayList.get(i1)), 6);
          }
          Object[] arrayOfObject1 = paramArrayList.toArray();
          localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject1);
          Object localObject1 = null;
          Method localMethod = null;
          Class localClass = null;
          Object localObject2 = null;
          String str4 = null;
          String str5 = null;
          label931:
          while (localFFSResultSet.getNextRow())
          {
            localWireHistoryInfo = new WireHistoryInfo();
            localWireHistoryInfo.setTransType(localFFSResultSet.getColumnString("WireType"));
            FFSDebug.log(str1 + " wHistInfo:" + localWireHistoryInfo, 6);
            a(localWireHistoryInfo, localFFSResultSet);
            localObject2 = null;
            Object localObject4;
            if (localObject1 == null)
            {
              localClass = Class.forName("com.ffusion.ffs.scheduling.db.ScheduleInfo");
              localObject3 = localClass.getDeclaredMethods();
              for (int i2 = 0; i2 < localObject3.length; i2++) {
                if (localObject3[i2].getName().equalsIgnoreCase("getPendingDatesByStartAndEndDate"))
                {
                  localObject1 = localObject3[i2];
                  break;
                }
              }
              localObject4 = new Class[] { String.class, String.class, String.class, FFSConnectionHolder.class };
              localMethod = localClass.getMethod("getScheduleInfo", (Class[])localObject4);
            }
            str4 = localFFSResultSet.getColumnString("RecSrvrTID");
            FFSDebug.log(str1 + " recSrvrTId:", str4, 6);
            str5 = localFFSResultSet.getColumnString("FIID");
            Object localObject3 = { str5, "RECWIRETRN", str4, paramFFSConnectionHolder };
            localObject2 = localMethod.invoke(null, (Object[])localObject3);
            if (localObject2 != null)
            {
              localObject4 = null;
              Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramPagingInfo.getStartDate()));
              Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramPagingInfo.getEndDate()));
              Object[] arrayOfObject2 = { localObject2, localInteger1, localInteger2 };
              FFSDebug.log(str1 + " startDateInteger:" + localInteger1, 6);
              FFSDebug.log(str1 + " endDateInteger:" + localInteger2, 6);
              localObject4 = (String[])localObject1.invoke(localObject2, arrayOfObject2);
              for (int i3 = 0;; i3++)
              {
                if (i3 >= localObject4.length) {
                  break label931;
                }
                String str6 = localObject4[i3].substring(0, 10);
                j++;
                if (j > al())
                {
                  paramPagingInfo.setStatusCode(28020);
                  paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
                  break;
                }
                localWireHistoryInfo.setCursorId(Integer.toString(j));
                localWireHistoryInfo.setDate(str6);
                WireTempHist.createTempHist(paramFFSConnectionHolder, str3, j, localWireHistoryInfo);
                if (j % i == 0) {
                  paramFFSConnectionHolder.conn.commit();
                }
              }
            }
          }
        }
      }
      FFSDebug.log(str1 + " rows:" + j, 6);
      paramPagingInfo.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1 + " : end", 6);
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + " failed : ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + " failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  private static void jdMethod_else(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Wire.createSessionDataRecTemplateWire";
    FFSDebug.log(str1 + " : start", 6);
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = localPropertyConfig.getBatchSize();
      int j = paramPagingInfo.getTotalTrans();
      WireHistoryInfo localWireHistoryInfo = null;
      String str3 = paramPagingInfo.getPagingContext().getSessionId();
      if ((str3 == null) || (str3.trim().length() == 0))
      {
        str3 = DBUtil.getNextIndexString("SessionID");
        paramPagingInfo.getPagingContext().setSessionId(str3);
      }
      StringBuffer localStringBuffer = new StringBuffer("SELECT a.RecSrvrTID, BankFromID, BranchFromID, CustomerID,WirePayeeID, FIID, Amount, AcctDebitID,AcctDebitType,AcctDebitKey, WireType,WireCategory,WireGroup,WireDest,Status,LogID,PayInstruct,Memo,AmtCurrency,DestCurrency,XferFee, StartAmount,EndAmount,DateCreate,Frequency,StartDate,EndDate,InstanceCount,SubmittedBy, ExchangeRate, BatchId, ContractNumber, ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,AgentId,AgentName,Method, ProcessedBy, TemplateId,CustomerRef,OriginatorCharges,ReceiverCharges, WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum, UserId,AgentType,OrigAmount,OrigCurrency,WireCreditID, b.Version  FROM BPW_RecWireInfo a, BPW_RecWireInfo2 b  WHERE a.RecSrvrTID = b.RecSrvrTID AND StartDate >= ? AND StartDate <= ?");
      if ((paramString != null) && (paramString.length() > 0)) {
        localStringBuffer.append(paramString);
      }
      localStringBuffer.append(" AND a.WireType = ? ");
      paramArrayList.add("RECTEMPLATE");
      String[] arrayOfString = (String[])localHashMap2.get("StatusList");
      if ((arrayOfString != null) && (arrayOfString.length > 0))
      {
        localStringBuffer.append(" AND Status IN (?");
        paramArrayList.add(arrayOfString[0]);
        for (int k = 1; k < arrayOfString.length; k++)
        {
          localStringBuffer.append(", ?");
          paramArrayList.add(arrayOfString[k]);
        }
        localStringBuffer.append(")");
      }
      localStringBuffer.append(" AND BatchId IS NULL ");
      localStringBuffer.append(" ORDER BY a.RecSrvrTID");
      Object[] arrayOfObject = paramArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        j++;
        if (j > al())
        {
          paramPagingInfo.setStatusCode(28020);
          paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
          break;
        }
        localWireHistoryInfo = new WireHistoryInfo();
        localWireHistoryInfo.setCursorId(Integer.toString(j));
        localWireHistoryInfo.setTransType(localFFSResultSet.getColumnString("WireType"));
        a(localWireHistoryInfo, localFFSResultSet);
        WireTempHist.createTempHist(paramFFSConnectionHolder, str3, j, localWireHistoryInfo);
        if (j % i == 0) {
          paramFFSConnectionHolder.conn.commit();
        }
      }
      paramPagingInfo.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1 + " : end", 6);
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + " failed : ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + " failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  private static void jdMethod_long(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Wire.createSessionDataWireBatch";
    FFSDebug.log(str1 + " : start", 6);
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = localPropertyConfig.getBatchSize();
      int j = paramPagingInfo.getTotalTrans();
      WireHistoryInfo localWireHistoryInfo = null;
      String str3 = paramPagingInfo.getPagingContext().getSessionId();
      if ((str3 == null) || (str3.trim().length() == 0))
      {
        str3 = DBUtil.getNextIndexString("SessionID");
        paramPagingInfo.getPagingContext().setSessionId(str3);
      }
      StringBuffer localStringBuffer = new StringBuffer("SELECT DISTINCT BatchId  FROM BPW_WireInfo a  WHERE DateToPost >= ? AND DateToPost <= ?");
      if ((paramString != null) && (paramString.length() > 0)) {
        localStringBuffer.append(paramString);
      }
      String[] arrayOfString = (String[])localHashMap2.get("StatusList");
      if ((arrayOfString != null) && (arrayOfString.length != 0))
      {
        localStringBuffer.append(" AND Status IN (?,");
        paramArrayList.add(arrayOfString[0]);
        for (int k = 1; k < arrayOfString.length - 1; k++)
        {
          localStringBuffer.append(" ?,");
          paramArrayList.add(arrayOfString[k]);
        }
        localStringBuffer.append(" ?)");
        paramArrayList.add(arrayOfString[(arrayOfString.length - 1)]);
      }
      localStringBuffer.append(" ORDER BY BatchId");
      Object[] arrayOfObject = paramArrayList.toArray();
      FFSDebug.log(str1 + ": Sql: ", localStringBuffer.toString(), 6);
      FFSDebug.log(str1 + ": paramList: " + paramArrayList, 6);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      WireBatchInfo localWireBatchInfo = null;
      WireBatchInfo[] arrayOfWireBatchInfo1 = null;
      WireInfo[] arrayOfWireInfo = null;
      ArrayList localArrayList = new ArrayList();
      String str4 = null;
      String str5 = null;
      int m;
      while (localFFSResultSet.getNextRow())
      {
        str5 = localFFSResultSet.getColumnString("BatchId");
        FFSDebug.log(str1 + ": found batchId: ", str5, 6);
        if ((str5 == null) || (str5.trim().length() == 0))
        {
          FFSDebug.log(str1 + ": WARNING: Invalid BatchId: ", str5, 6);
        }
        else
        {
          localWireBatchInfo = new WireBatchInfo();
          localWireBatchInfo.setBatchId(str5);
          boolean bool1 = false;
          m = 1;
          boolean bool2 = false;
          if ((localWireBatchInfo.getBatchId() == null) || (localWireBatchInfo.getBatchId().trim().length() == 0))
          {
            FFSDebug.log(str1 + ": WARNING: Invalid BatchId: ", str5, 6);
          }
          else
          {
            arrayOfWireBatchInfo1 = getWireTransferBatch(paramFFSConnectionHolder, localWireBatchInfo, bool1, m, bool2);
            if ((arrayOfWireBatchInfo1 == null) || (arrayOfWireBatchInfo1[0] == null) || (arrayOfWireBatchInfo1[0].getStatusCode() != 0))
            {
              FFSDebug.log(str1 + ": WARNING: Invalid Wire Batch status returned batch will be ignored: ", str5, 6);
            }
            else
            {
              localWireBatchInfo = arrayOfWireBatchInfo1[0];
              arrayOfWireInfo = localWireBatchInfo.getWires();
              if ((arrayOfWireInfo != null) && (arrayOfWireInfo.length != 0))
              {
                str4 = jdMethod_int(localWireBatchInfo);
                localWireBatchInfo.setPrcStatus(str4);
                localArrayList.add(localWireBatchInfo);
              }
            }
          }
        }
      }
      if (localArrayList.size() != 0)
      {
        paramFFSConnectionHolder.conn.commit();
        WireBatchInfo[] arrayOfWireBatchInfo2 = (WireBatchInfo[])localArrayList.toArray(new WireBatchInfo[0]);
        for (m = 0; m < arrayOfWireBatchInfo2.length; m++)
        {
          localWireBatchInfo = arrayOfWireBatchInfo2[m];
          j++;
          if (j > al())
          {
            paramPagingInfo.setStatusCode(28020);
            paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
            break;
          }
          localWireHistoryInfo = new WireHistoryInfo();
          localWireHistoryInfo.setCursorId(Integer.toString(j));
          localWireHistoryInfo.setTransType("BATCH");
          a(localWireHistoryInfo, localWireBatchInfo);
          WireTempHist.createTempHist(paramFFSConnectionHolder, str3, j, localWireHistoryInfo);
          if (m % i == 0) {
            paramFFSConnectionHolder.conn.commit();
          }
        }
      }
      paramPagingInfo.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1 + " : end", 6);
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + " failed : ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + " failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  public static PagingInfo getSessionPage(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "Wire.getSessionPage";
    FFSDebug.log(str1 + " : start", 6);
    String str2 = paramPagingInfo.getPagingContext().getSessionId();
    if ((str2 == null) || (str2.trim().length() == 0))
    {
      paramPagingInfo.setStatusCode(16000);
      paramPagingInfo.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "SessionId " }, "WIRE_MESSAGE"));
      FFSDebug.log(str1 + " : " + "Null sessionId passed", 0);
      return paramPagingInfo;
    }
    try
    {
      ArrayList localArrayList = WireTempHist.getSessionPage(paramFFSConnectionHolder, paramPagingInfo);
      paramPagingInfo.setPagingResult(localArrayList);
      WireTempHist.getBounds(paramFFSConnectionHolder, paramPagingInfo);
      paramPagingInfo.setStatusCode(0);
      return paramPagingInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed : ";
      FFSDebug.log(str3 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str3);
    }
  }
  
  public static void updateToBackendStatus(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    FFSDebug.log("Wire.updateToBackendStatus: start, wireInfo: ", paramWireInfo.toString(), 6);
    String str1 = null;
    if (paramWireInfo == null)
    {
      str2 = "Wire.updateToBackendStatus: Failed, Invalid wireInfo: " + paramWireInfo;
      FFSDebug.log(str2, 6);
      throw new FFSException(str2);
    }
    String str2 = paramWireInfo.getSrvrTid();
    if ((str2 == null) || (str2.length() == 0))
    {
      str3 = "Wire.updateToBackendStatus: Failed, Invalid transaction ID: " + str2;
      FFSDebug.log(str3, 6);
      throw new FFSException(str3);
    }
    String str3 = FFSUtil.getDateString("yyyyMMddHHmmss");
    paramWireInfo.setDatePosted(str3);
    FFSDebug.log("Wire.updateToBackendStatus. wireInfo.getPrcStatus(): ", paramWireInfo.getPrcStatus(), 6);
    Object[] arrayOfObject = { paramWireInfo.getPrcStatus(), str3, str2 };
    str1 = "UPDATE BPW_WireInfo SET Status = ?, DatePosted = ? WHERE SrvrTID=?";
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Update BPW_WireInfo2 Set Version=Version+1  WHERE SrvrTID = ?");
      localObject = jdMethod_byte(paramWireInfo, false);
      DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject, paramWireInfo, "SrvrTID", paramWireInfo.getSrvrTid());
    }
    catch (Throwable localThrowable)
    {
      Object localObject = "*** Wire.updateToBackendStatus failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject + str4, 0);
      throw new FFSException(localThrowable, (String)localObject);
    }
    paramWireInfo.setStatusCode(0);
    paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    FFSDebug.log("Wire.updateToBackendStatus: done", 6);
  }
  
  public static void updateStatus(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    FFSDebug.log("Wire.updateStatus: start, wireInfo: ", paramWireInfo.toString(), 6);
    if (paramWireInfo == null) {
      return;
    }
    String str1 = paramWireInfo.getSrvrTid();
    if ((str1 == null) || (str1.length() == 0))
    {
      String str2 = "Wire.updateStatus: Failed, Invalid transaction ID: " + str1;
      FFSDebug.log(str2, 6);
      throw new FFSException(str2);
    }
    updateStatus(paramFFSConnectionHolder, paramWireInfo, paramWireInfo.getPrcStatus(), false);
    FFSDebug.log("Wire.updateStatus: done", 6);
  }
  
  public static WireInfo addWire(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Wire.addWire";
    String str2 = null;
    String str3 = null;
    String str4 = null;
    Object[] arrayOfObject = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    FFSDebug.log(str1 + ": Starts. Recurring: " + paramBoolean, 6);
    if (paramFFSConnectionHolder == null)
    {
      paramWireInfo.setStatusCode(16000);
      String str8 = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str8);
      FFSDebug.log(str1, str8, 0);
      return paramWireInfo;
    }
    str6 = paramWireInfo.getWireSource();
    if ((str6 != null) && (str6.trim().equalsIgnoreCase("HOST"))) {
      jdMethod_else(paramFFSConnectionHolder, paramWireInfo);
    } else {
      jdMethod_goto(paramFFSConnectionHolder, paramWireInfo);
    }
    if (paramWireInfo.getStatusCode() != 0) {
      return paramWireInfo;
    }
    str7 = paramWireInfo.getWireType().trim();
    if (((str7.equalsIgnoreCase("RECTEMPLATE")) || (str7.equalsIgnoreCase("TEMPLATE"))) && (isDuplicateTemplate(paramFFSConnectionHolder, paramWireInfo, paramBoolean)))
    {
      FFSDebug.log(str1, "The template Name and Nickname combination of the specified wire is duplicate.", 0);
      paramWireInfo.setStatusCode(19760);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19760, null, "WIRE_MESSAGE"));
      return paramWireInfo;
    }
    try
    {
      if (paramBoolean)
      {
        str3 = "INSERT INTO BPW_RecWireInfo ( RecSrvrTID,BatchId,ContractNumber,BankFromID,BranchFromID,CustomerID,WirePayeeID,FIID,Amount,AcctDebitID,AcctDebitType,AcctDebitKey,WireType,WireCategory,WireGroup,WireDest,Status,LogID,PayInstruct,Memo,AmtCurrency,DestCurrency, StartAmount, EndAmount,DateCreate, Frequency,StartDate,EndDate,InstanceCount,ExchangeRate,SubmittedBy, ExtId,WireSource,WireName,NickName,WireLimit,SettlementDate, WireScope,MathRule,OrigToBeneficiaryMemo,OrigToBeneficiaryInfo, BanktoBankInfo,ProcessedBy,AgentId,AgentName,Method,TemplateId, CustomerRef,OriginatorCharges,ReceiverCharges,WireChargesDetails, OrgChargesAccountNum,BenefChargesAccountNum,UserId,AgentType,OrigAmount, OrigCurrency,WireCreditID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        str4 = "INSERT INTO BPW_RecWireInfo2 ( RecSrvrTID, ByOrderOfName, ByOrderOfAddr1, ByOrderOfAddr2, ByOrderOfAddr3, ByOrderOfAcct) VALUES (?,?,?,?,?,?)";
        if (str7.equalsIgnoreCase("RECTEMPLATE"))
        {
          str5 = "RECTEMPLATE";
          paramWireInfo.setPrcStatus("RECTEMPLATE");
        }
        else
        {
          str5 = "RECWIRE";
          paramWireInfo.setPrcStatus("WILLPROCESSON");
        }
      }
      else
      {
        str3 = "INSERT INTO BPW_WireInfo ( SrvrTID,BatchId,ContractNumber,BankFromID, BranchFromID,CustomerID,WirePayeeID, RecSrvrTID,FIID,Amount,AcctDebitID,AcctDebitType,AcctDebitKey, DateCreate,DateDue,DateToPost,WireType,WireCategory,WireGroup,WireDest,Status,LogID,PayInstruct,Memo,AmtCurrency,DestCurrency, ExchangeRate, SubmittedBy, ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,ProcessedBy,TemplateId,AgentId, AgentName,Method, AmountType,CustomerRef,OriginatorCharges, ReceiverCharges,WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum, HostId,UserId,AgentType, OrigAmount, OrigCurrency, WireCreditID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        str4 = "INSERT INTO BPW_WireInfo2 ( SrvrTID, ByOrderOfName, ByOrderOfAddr1, ByOrderOfAddr2, ByOrderOfAddr3, ByOrderOfAcct) VALUES (?,?,?,?,?,?)";
        if (str7.equalsIgnoreCase("TEMPLATE"))
        {
          str5 = "TEMPLATE";
          paramWireInfo.setPrcStatus("TEMPLATE");
        }
        else
        {
          str5 = "WIRE";
          paramWireInfo.setPrcStatus("CREATED");
        }
      }
      str2 = DBUtil.getNewTransId("SrvrTID", 18);
      paramWireInfo.setSrvrTid(str2);
      paramWireInfo.setDatePosted(null);
      if ((str7.equalsIgnoreCase("TEMPLATE")) || (str7.equalsIgnoreCase("RECTEMPLATE"))) {
        paramWireInfo.setTemplateId(str2);
      }
      arrayOfObject = jdMethod_char(paramWireInfo, paramBoolean);
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      arrayOfObject = jdMethod_case(paramWireInfo, paramBoolean);
      DBUtil.executeStatement(paramFFSConnectionHolder, str4, arrayOfObject);
      if (null != paramWireInfo.getExtInfo())
      {
        int i = BPWExtraInfo.processXtraInfo(paramFFSConnectionHolder, paramWireInfo.getFiID(), paramWireInfo.getSrvrTid(), str5, paramWireInfo.getExtInfo());
        if (i != 0)
        {
          paramWireInfo.setStatusCode(19270);
          paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19270, null, "WIRE_MESSAGE"));
        }
        else
        {
          paramWireInfo.setStatusCode(0);
          paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        }
      }
      else
      {
        paramWireInfo.setStatusCode(0);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
    }
    catch (Throwable localThrowable)
    {
      String str9 = str1 + " failed: ";
      String str10 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str9, str10, 0);
      throw new FFSException(localThrowable, str9);
    }
    return paramWireInfo;
  }
  
  private static Object[] jdMethod_char(WireInfo paramWireInfo, boolean paramBoolean)
    throws FFSException
  {
    if (paramBoolean)
    {
      localObject = (RecWireInfo)paramWireInfo;
      Object[] arrayOfObject = { ((RecWireInfo)localObject).getSrvrTid(), ((RecWireInfo)localObject).getBatchId(), ((RecWireInfo)localObject).getContractNumber(), ((RecWireInfo)localObject).getFromBankId(), ((RecWireInfo)localObject).getFromBranchId(), ((RecWireInfo)localObject).getCustomerID(), ((RecWireInfo)localObject).getWirePayeeId(), ((RecWireInfo)localObject).getFiID(), ((RecWireInfo)localObject).getAmount(), ((RecWireInfo)localObject).getFromAcctId(), ((RecWireInfo)localObject).getFromAcctType(), ((RecWireInfo)localObject).getFromAcctKey(), ((RecWireInfo)localObject).getWireType().toUpperCase(), ((RecWireInfo)localObject).getWireCategory(), ((RecWireInfo)localObject).getWireGroup(), ((RecWireInfo)localObject).getWireDest(), ((RecWireInfo)localObject).getPrcStatus(), ((RecWireInfo)localObject).getLogId(), ((RecWireInfo)localObject).getPayInstruct(), ((RecWireInfo)localObject).getMemo(), ((RecWireInfo)localObject).getAmtCurrency(), ((RecWireInfo)localObject).getDestCurrency(), ((RecWireInfo)localObject).getStartAmount(), ((RecWireInfo)localObject).getEndAmount(), DBUtil.getCurrentLogDate(), new Integer(FFSUtil.getFreqInt(((RecWireInfo)localObject).getFrequency())), Integer.toString(BPWUtil.getDateDBFormat(((RecWireInfo)localObject).getStartDate())), Integer.toString(BPWUtil.getDateDBFormat(((RecWireInfo)localObject).getEndDate())), new Integer(((RecWireInfo)localObject).getPmtsCount()), ((RecWireInfo)localObject).getExchangeRate(), ((RecWireInfo)localObject).getSubmittedBy(), ((RecWireInfo)localObject).getExtId(), ((RecWireInfo)localObject).getWireSource(), ((RecWireInfo)localObject).getWireName(), ((RecWireInfo)localObject).getNickName(), ((RecWireInfo)localObject).getWireLimit(), ((RecWireInfo)localObject).getSettlementDate(), ((RecWireInfo)localObject).getWireScope(), ((RecWireInfo)localObject).getMathRule(), ((RecWireInfo)localObject).getOrigToBeneficiaryMemo(), ((RecWireInfo)localObject).getOrigToBeneficiaryInfo(), ((RecWireInfo)localObject).getBanktoBankInfo(), ((RecWireInfo)localObject).getProcessedBy(), ((RecWireInfo)localObject).getAgentId(), ((RecWireInfo)localObject).getAgentName(), ((RecWireInfo)localObject).getMethod(), ((RecWireInfo)localObject).getTemplateId(), ((RecWireInfo)localObject).getCustomerRef(), ((RecWireInfo)localObject).getOriginatorCharges(), ((RecWireInfo)localObject).getReceiverCharges(), ((RecWireInfo)localObject).getWireChargesDetails(), ((RecWireInfo)localObject).getOrgChargesAccountNum(), ((RecWireInfo)localObject).getBenefChargesAccountNum(), ((RecWireInfo)localObject).getUserId(), ((RecWireInfo)localObject).getAgentType(), ((RecWireInfo)localObject).getOrigAmount(), ((RecWireInfo)localObject).getOrigCurrency(), ((RecWireInfo)localObject).getWireCreditId() };
      return arrayOfObject;
    }
    Object localObject = { paramWireInfo.getSrvrTid(), paramWireInfo.getBatchId(), paramWireInfo.getContractNumber(), paramWireInfo.getFromBankId(), paramWireInfo.getFromBranchId(), paramWireInfo.getCustomerID(), paramWireInfo.getWirePayeeId(), paramWireInfo.getRecSrvrTid(), paramWireInfo.getFiID(), paramWireInfo.getAmount(), paramWireInfo.getFromAcctId(), paramWireInfo.getFromAcctType(), paramWireInfo.getFromAcctKey(), DBUtil.getCurrentLogDate(), Integer.toString(BPWUtil.getDateDBFormat(paramWireInfo.getDateDue())), Integer.toString(BPWUtil.getDateDBFormat(paramWireInfo.getDateToPost())), paramWireInfo.getWireType().toUpperCase(), paramWireInfo.getWireCategory(), paramWireInfo.getWireGroup(), paramWireInfo.getWireDest(), paramWireInfo.getPrcStatus(), paramWireInfo.getLogId(), paramWireInfo.getPayInstruct(), paramWireInfo.getMemo(), paramWireInfo.getAmtCurrency(), paramWireInfo.getDestCurrency(), paramWireInfo.getExchangeRate(), paramWireInfo.getSubmittedBy(), paramWireInfo.getExtId(), paramWireInfo.getWireSource(), paramWireInfo.getWireName(), paramWireInfo.getNickName(), paramWireInfo.getWireLimit(), paramWireInfo.getSettlementDate(), paramWireInfo.getWireScope(), paramWireInfo.getMathRule(), paramWireInfo.getOrigToBeneficiaryMemo(), paramWireInfo.getOrigToBeneficiaryInfo(), paramWireInfo.getBanktoBankInfo(), paramWireInfo.getProcessedBy(), paramWireInfo.getTemplateId(), paramWireInfo.getAgentId(), paramWireInfo.getAgentName(), paramWireInfo.getMethod(), paramWireInfo.getAmountType(), paramWireInfo.getCustomerRef(), paramWireInfo.getOriginatorCharges(), paramWireInfo.getReceiverCharges(), paramWireInfo.getWireChargesDetails(), paramWireInfo.getOrgChargesAccountNum(), paramWireInfo.getBenefChargesAccountNum(), paramWireInfo.getHostId(), paramWireInfo.getUserId(), paramWireInfo.getAgentType(), paramWireInfo.getOrigAmount(), paramWireInfo.getOrigCurrency(), paramWireInfo.getWireCreditId() };
    return localObject;
  }
  
  private static Object[] jdMethod_case(WireInfo paramWireInfo, boolean paramBoolean)
    throws FFSException
  {
    if (paramBoolean)
    {
      localObject = (RecWireInfo)paramWireInfo;
      Object[] arrayOfObject = { ((RecWireInfo)localObject).getSrvrTid(), ((RecWireInfo)localObject).getByOrderOfName(), ((RecWireInfo)localObject).getByOrderOfAddr1(), ((RecWireInfo)localObject).getByOrderOfAddr2(), ((RecWireInfo)localObject).getByOrderOfAddr3(), ((RecWireInfo)localObject).getByOrderOfAcctNum() };
      return arrayOfObject;
    }
    Object localObject = { paramWireInfo.getSrvrTid(), paramWireInfo.getByOrderOfName(), paramWireInfo.getByOrderOfAddr1(), paramWireInfo.getByOrderOfAddr2(), paramWireInfo.getByOrderOfAddr3(), paramWireInfo.getByOrderOfAcctNum() };
    return localObject;
  }
  
  public static WireInfo generateSingleWireFromRecWire(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    Object localObject1 = new RecWireInfo();
    ((WireInfo)localObject1).setSrvrTid(paramString1);
    localObject1 = (RecWireInfo)getWireInfo(paramFFSConnectionHolder, (WireInfo)localObject1, true);
    Hashtable localHashtable = ((WireInfo)localObject1).getExtInfo();
    if (localHashtable != null)
    {
      localObject2 = (ValueInfo[])localHashtable.values().toArray(new ValueInfo[0]);
      for (int i = 0; i < localObject2.length; i++)
      {
        localObject2[i].setAction("add");
        FFSDebug.log("Value=" + localObject2[i].toString(), 6);
        FFSDebug.log("Action=" + localObject2[i].getAction(), 6);
      }
    }
    if (((WireInfo)localObject1).getStatusCode() != 0) {
      return localObject1;
    }
    Object localObject2 = ((WireInfo)localObject1).getAmount();
    if (paramInt == 0) {
      localObject2 = ((RecWireInfo)localObject1).getStartAmount();
    } else if (paramInt == 1) {
      localObject2 = ((RecWireInfo)localObject1).getEndAmount();
    }
    FFSDebug.log("amount= ", (String)localObject2, 6);
    if (!NumberUtils.isNumber((String)localObject2)) {
      localObject2 = ((RecWireInfo)localObject1).getAmount();
    }
    ((WireInfo)localObject1).setRecSrvrTid(paramString1);
    ((WireInfo)localObject1).setDateDue(paramString2);
    ((WireInfo)localObject1).setAmount((String)localObject2);
    FFSDebug.log("Wire.generateSingleWireFromRecWire:DueDate=", ((WireInfo)localObject1).getDateDue(), 6);
    paramString2 = String.valueOf(Integer.parseInt(paramString2));
    ((WireInfo)localObject1).setDateToPost(DBUtil.getPayday(((WireInfo)localObject1).getFiID(), paramString2));
    ((WireInfo)localObject1).setWireType("Recurring");
    FFSDebug.log("DueDate=", ((WireInfo)localObject1).getDateDue(), " DtToPost=", ((WireInfo)localObject1).getDateToPost(), 6);
    if (((WireInfo)localObject1).getProcessedBy() == null) {
      ((WireInfo)localObject1).setProcessedBy(((WireInfo)localObject1).getSubmittedBy());
    }
    ((WireInfo)localObject1).setExtId(BPWTrackingIDGenerator.getNextId());
    localObject1 = addWire(paramFFSConnectionHolder, (WireInfo)localObject1, false);
    return localObject1;
  }
  
  public static WireInfo getWireInfo(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, boolean paramBoolean)
    throws FFSException
  {
    return getWireInfo(paramFFSConnectionHolder, paramWireInfo, paramBoolean, true);
  }
  
  public static WireInfo getWireInfo(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "Wire.getWireInstruction";
    FFSDebug.log(str1, ": start, srvrTID=", paramWireInfo.getSrvrTid(), 6);
    String str2 = null;
    String str3 = null;
    Hashtable localHashtable = null;
    Object[] arrayOfObject = { paramWireInfo.getSrvrTid() };
    Object localObject1 = null;
    FFSResultSet localFFSResultSet = null;
    WirePayeeInfo localWirePayeeInfo1 = null;
    WirePayeeInfo localWirePayeeInfo2 = null;
    if (paramBoolean1)
    {
      str2 = "SELECT a.RecSrvrTID, BankFromID, BranchFromID, CustomerID,WirePayeeID, FIID, Amount, AcctDebitID,AcctDebitType,AcctDebitKey, WireType,WireCategory,WireGroup,WireDest,Status,LogID,PayInstruct,Memo,AmtCurrency,DestCurrency,XferFee, StartAmount,EndAmount,DateCreate,Frequency,StartDate,EndDate,InstanceCount,SubmittedBy, ExchangeRate, BatchId, ContractNumber, ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,AgentId,AgentName,Method, ProcessedBy, TemplateId,CustomerRef,OriginatorCharges,ReceiverCharges, WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum, UserId,AgentType,OrigAmount,OrigCurrency,WireCreditID , ByOrderOfName, ByOrderOfAddr1, ByOrderOfAddr2, ByOrderOfAddr3, ByOrderOfAcct, b.Version  FROM BPW_RecWireInfo a, BPW_RecWireInfo2 b  WHERE a.RecSrvrTID = b.RecSrvrTID AND a.RecSrvrTID = ?";
      str3 = "RECWIRE";
      localObject1 = new RecWireInfo();
    }
    else
    {
      str2 = "SELECT a.SrvrTID,BankFromID,BranchFromID,CustomerID,WirePayeeID,RecSrvrTID,FIID,Amount,AmtCurrency,DestCurrency,XferFee,AcctDebitID, AcctDebitType,AcctDebitKey,DateCreate,DateDue,DateToPost,DatePosted, WireType,WireCategory,WireGroup,WireDest, BatchId, ContractNumber, Status,LogID,ConfirmNum, ConfirmNum2, ConfirmMsg, PayInstruct, Memo,ExchangeRate,SubmittedBy,ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,ProcessedBy,TemplateId,AgentId, AgentName,Method,AmountType,CustomerRef,OriginatorCharges,ReceiverCharges, WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum,HostId, UserId,AgentType,OrigAmount,OrigCurrency, WireCreditID, ByOrderOfName, ByOrderOfAddr1, ByOrderOfAddr2, ByOrderOfAddr3, ByOrderOfAcct, b.Version  FROM BPW_WireInfo a, BPW_WireInfo2 b  WHERE a.SrvrTID = b.SrvrTID AND a.SrvrTID = ?";
      str3 = "WIRE";
      localObject1 = new WireInfo();
    }
    if (paramBoolean2) {
      str2 = str2 + " AND Status != 'CANCELEDON'";
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        a((WireInfo)localObject1, localFFSResultSet, paramBoolean1);
        FFSDebug.log(str1, "XtraInfo:FIID:", ((WireInfo)localObject1).getFiID(), "SrvrTid:", ((WireInfo)localObject1).getSrvrTid(), 6);
        String str4 = ((WireInfo)localObject1).getWireType();
        if ("TEMPLATE".equalsIgnoreCase(str4)) {
          str3 = "TEMPLATE";
        } else if ("RECTEMPLATE".equalsIgnoreCase(str4)) {
          str3 = "RECTEMPLATE";
        }
        localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, ((WireInfo)localObject1).getFiID(), ((WireInfo)localObject1).getSrvrTid(), str3);
        ((WireInfo)localObject1).setExtInfo(localHashtable);
        FFSDebug.log(str1, "XtraInfo:" + localHashtable, 6);
        if (((WireInfo)localObject1).getWirePayeeId() != null)
        {
          localWirePayeeInfo1 = WirePayee.getWirePayeeInfo(paramFFSConnectionHolder, ((WireInfo)localObject1).getWirePayeeId(), false);
          if (localWirePayeeInfo1.getStatusCode() != 0)
          {
            ((WireInfo)localObject1).setStatusCode(localWirePayeeInfo1.getStatusCode());
            ((WireInfo)localObject1).setStatusMsg(localWirePayeeInfo1.getStatusMsg());
            localObject2 = localObject1;
            return localObject2;
          }
          ((WireInfo)localObject1).setWirePayeeInfo(localWirePayeeInfo1);
        }
        if (((WireInfo)localObject1).getWireCreditId() != null)
        {
          localWirePayeeInfo2 = WirePayee.getWirePayeeInfo(paramFFSConnectionHolder, ((WireInfo)localObject1).getWireCreditId(), false);
          if (localWirePayeeInfo2.getStatusCode() != 0)
          {
            ((WireInfo)localObject1).setStatusCode(localWirePayeeInfo2.getStatusCode());
            ((WireInfo)localObject1).setStatusMsg(localWirePayeeInfo2.getStatusMsg());
            localObject2 = localObject1;
            return localObject2;
          }
          ((WireInfo)localObject1).setWireCreditInfo(localWirePayeeInfo2);
        }
        ((WireInfo)localObject1).setStatusCode(0);
        ((WireInfo)localObject1).setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      else
      {
        ((WireInfo)localObject1).setStatusCode(16020);
        ((WireInfo)localObject1).setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WireInfo" }, "WIRE_MESSAGE"));
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject2 = str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2, str5, 0);
      throw new FFSException(localThrowable, (String)localObject2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str6 = str1 + " failed: ";
        String str7 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str6, str7, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return localObject1;
  }
  
  public static WireInfo getWireTransfer(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, boolean paramBoolean)
    throws FFSException
  {
    return getWireTransfer(paramFFSConnectionHolder, paramWireInfo, paramBoolean, true, true);
  }
  
  public static WireInfo getWireTransfer(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws FFSException
  {
    String str1 = "Wire.getWireTransfer";
    FFSDebug.log(str1, ": start, extId=", paramWireInfo.getExtId(), 6);
    String str2 = null;
    String str3 = null;
    Hashtable localHashtable = null;
    Object[] arrayOfObject = { paramWireInfo.getExtId() };
    Object localObject1 = null;
    FFSResultSet localFFSResultSet = null;
    WirePayeeInfo localWirePayeeInfo1 = null;
    WirePayeeInfo localWirePayeeInfo2 = null;
    String str4 = null;
    if (paramBoolean1)
    {
      str2 = "SELECT a.RecSrvrTID, BankFromID, BranchFromID, CustomerID,WirePayeeID, FIID, Amount, AcctDebitID,AcctDebitType,AcctDebitKey, WireType,WireCategory,WireGroup,WireDest,Status,LogID,PayInstruct,Memo,AmtCurrency,DestCurrency,XferFee, StartAmount,EndAmount,DateCreate,Frequency,StartDate,EndDate,InstanceCount,SubmittedBy, ExchangeRate, BatchId, ContractNumber,ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,AgentId,AgentName,Method, ProcessedBy, TemplateId,CustomerRef,OriginatorCharges,ReceiverCharges, WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum, UserId,AgentType,OrigAmount,OrigCurrency,WireCreditID , ByOrderOfName, ByOrderOfAddr1, ByOrderOfAddr2, ByOrderOfAddr3, ByOrderOfAcct, b.Version  FROM BPW_RecWireInfo a, BPW_RecWireInfo2 b  WHERE a.RecSrvrTID = b.RecSrvrTID AND ExtId = ?";
      str3 = "RECWIRE";
      localObject1 = new RecWireInfo();
      str4 = " ORDER BY RecSrvrTID DESC";
    }
    else
    {
      str2 = "SELECT a.SrvrTID,BankFromID,BranchFromID,CustomerID,WirePayeeID,RecSrvrTID,FIID,Amount,AmtCurrency,DestCurrency,XferFee,AcctDebitID, AcctDebitType,AcctDebitKey,DateCreate,DateDue,DateToPost,DatePosted, WireType,WireCategory,WireGroup,WireDest, BatchId, ContractNumber, Status,LogID,ConfirmNum, ConfirmNum2, ConfirmMsg, PayInstruct, Memo,ExchangeRate,SubmittedBy,ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,ProcessedBy,TemplateId,AgentId, AgentName,Method,AmountType,CustomerRef,OriginatorCharges,ReceiverCharges, WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum,HostId, UserId,AgentType,OrigAmount,OrigCurrency, WireCreditID , ByOrderOfName, ByOrderOfAddr1, ByOrderOfAddr2, ByOrderOfAddr3, ByOrderOfAcct, b.Version  FROM BPW_WireInfo a, BPW_WireInfo2 b  WHERE a.SrvrTID = b.SrvrTID AND ExtId = ?";
      str3 = "WIRE";
      localObject1 = new WireInfo();
      str4 = " ORDER BY SrvrTID DESC";
    }
    if (paramBoolean2) {
      str2 = str2 + " AND Status != 'CANCELEDON'";
    }
    str2 = str2 + str4;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        a((WireInfo)localObject1, localFFSResultSet, paramBoolean1);
        FFSDebug.log(str1, "XtraInfo:FIID:", ((WireInfo)localObject1).getFiID(), "SrvrTid:", ((WireInfo)localObject1).getSrvrTid(), 6);
        localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, ((WireInfo)localObject1).getFiID(), ((WireInfo)localObject1).getSrvrTid(), str3);
        ((WireInfo)localObject1).setExtInfo(localHashtable);
        FFSDebug.log(str1, "XtraInfo:" + localHashtable, 6);
        Object localObject2;
        if ((paramBoolean3) && (((WireInfo)localObject1).getWirePayeeId() != null))
        {
          localWirePayeeInfo1 = WirePayee.getWirePayeeInfo(paramFFSConnectionHolder, ((WireInfo)localObject1).getWirePayeeId(), false);
          if (localWirePayeeInfo1.getStatusCode() != 0)
          {
            ((WireInfo)localObject1).setStatusCode(localWirePayeeInfo1.getStatusCode());
            ((WireInfo)localObject1).setStatusMsg(localWirePayeeInfo1.getStatusMsg());
            localObject2 = localObject1;
            return localObject2;
          }
          ((WireInfo)localObject1).setWirePayeeInfo(localWirePayeeInfo1);
        }
        if (((WireInfo)localObject1).getWireCreditId() != null)
        {
          localWirePayeeInfo2 = WirePayee.getWirePayeeInfo(paramFFSConnectionHolder, ((WireInfo)localObject1).getWireCreditId(), false);
          if (localWirePayeeInfo2.getStatusCode() != 0)
          {
            ((WireInfo)localObject1).setStatusCode(localWirePayeeInfo2.getStatusCode());
            ((WireInfo)localObject1).setStatusMsg(localWirePayeeInfo2.getStatusMsg());
            localObject2 = localObject1;
            return localObject2;
          }
          ((WireInfo)localObject1).setWireCreditInfo(localWirePayeeInfo2);
        }
        ((WireInfo)localObject1).setStatusCode(0);
        ((WireInfo)localObject1).setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      else
      {
        ((WireInfo)localObject1).setStatusCode(16020);
        ((WireInfo)localObject1).setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WireInfo" }, "WIRE_MESSAGE"));
      }
    }
    catch (Throwable localThrowable)
    {
      String str5 = str1 + " failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5, str6, 0);
      throw new FFSException(localThrowable, str5);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str7 = str1 + " failed: ";
        String str8 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str7, str8, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return localObject1;
  }
  
  public static String[] getUnprocessedWires(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Wire.getUnProcessedWires";
    FFSDebug.log(str1, ": start, payeeID=", paramString, 6);
    String str2 = null;
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    if (paramBoolean) {
      str2 = "SELECT RecSrvrTID  FROM BPW_RecWireInfo WHERE WirePayeeID = ? AND Status IN ('WILLPROCESSON', 'RECTEMPLATE') ";
    } else {
      str2 = "SELECT SrvrTID  FROM BPW_WireInfo WHERE WirePayeeID = ? AND Status IN ('WILLPROCESSON', 'CREATED', 'TEMPLATE', 'APPROVAL_PENDING', 'FUNDSAPPROVED', 'INFUNDSREVERT', 'INFUNDSAPPROVAL', 'NOFUNDS', 'BACKENDPENDING', 'RELEASEPENDING', 'FUNDSPENDING', 'INPROCESS', 'IMMED_INPROCESS') ";
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        String str3 = localFFSResultSet.getColumnString(1);
        localArrayList.add(str3);
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str6 = str1 + " failed: ";
        String str7 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str6, str7, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  private static void a(WireInfo paramWireInfo, FFSResultSet paramFFSResultSet, boolean paramBoolean)
    throws Exception
  {
    DBUtil.populateBaseInfo(paramFFSResultSet, paramWireInfo);
    if (paramBoolean == true)
    {
      RecWireInfo localRecWireInfo = (RecWireInfo)paramWireInfo;
      localRecWireInfo.setSrvrTid(paramFFSResultSet.getColumnString("RecSrvrTID"));
      localRecWireInfo.setFromBankId(paramFFSResultSet.getColumnString("BankFromID"));
      localRecWireInfo.setFromBranchId(paramFFSResultSet.getColumnString("BranchFromID"));
      localRecWireInfo.setCustomerID(paramFFSResultSet.getColumnString("CustomerID"));
      localRecWireInfo.setAmount(paramFFSResultSet.getColumnString("Amount"));
      localRecWireInfo.setFromAcctId(paramFFSResultSet.getColumnString("AcctDebitID"));
      localRecWireInfo.setFromAcctType(paramFFSResultSet.getColumnString("AcctDebitType"));
      localRecWireInfo.setFromAcctKey(paramFFSResultSet.getColumnString("AcctDebitKey"));
      localRecWireInfo.setWireType(paramFFSResultSet.getColumnString("WireType"));
      localRecWireInfo.setWireCategory(paramFFSResultSet.getColumnString("WireCategory"));
      localRecWireInfo.setWireGroup(paramFFSResultSet.getColumnString("WireGroup"));
      localRecWireInfo.setWireDest(paramFFSResultSet.getColumnString("WireDest"));
      localRecWireInfo.setPrcStatus(paramFFSResultSet.getColumnString("Status"));
      localRecWireInfo.setLogId(paramFFSResultSet.getColumnString("LogID"));
      localRecWireInfo.setPayInstruct(paramFFSResultSet.getColumnString("PayInstruct"));
      localRecWireInfo.setMemo(paramFFSResultSet.getColumnString("Memo"));
      localRecWireInfo.setAmtCurrency(paramFFSResultSet.getColumnString("AmtCurrency"));
      localRecWireInfo.setDestCurrency(paramFFSResultSet.getColumnString("DestCurrency"));
      localRecWireInfo.setFiID(paramFFSResultSet.getColumnString("FIID"));
      localRecWireInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
      localRecWireInfo.setStartDate(BPWUtil.getDateBeanFormat(Integer.parseInt(paramFFSResultSet.getColumnString("StartDate"))));
      localRecWireInfo.setEndDate(BPWUtil.getDateBeanFormat(Integer.parseInt(paramFFSResultSet.getColumnString("EndDate"))));
      localRecWireInfo.setFrequency(FFSUtil.getFreqString(paramFFSResultSet.getColumnInt("Frequency")));
      localRecWireInfo.setPmtsCount(paramFFSResultSet.getColumnInt("InstanceCount"));
      localRecWireInfo.setWirePayeeId(paramFFSResultSet.getColumnString("WirePayeeID"));
      localRecWireInfo.setExchangeRate(paramFFSResultSet.getColumnString("ExchangeRate"));
      localRecWireInfo.setStartAmount(paramFFSResultSet.getColumnString("StartAmount"));
      localRecWireInfo.setEndAmount(paramFFSResultSet.getColumnString("EndAmount"));
      localRecWireInfo.setWireFee(paramFFSResultSet.getColumnString("XferFee"));
      localRecWireInfo.setBatchId(paramFFSResultSet.getColumnString("BatchId"));
      localRecWireInfo.setContractNumber(paramFFSResultSet.getColumnString("ContractNumber"));
      localRecWireInfo.setExtId(paramFFSResultSet.getColumnString("ExtId"));
      localRecWireInfo.setWireSource(paramFFSResultSet.getColumnString("WireSource"));
      localRecWireInfo.setWireName(paramFFSResultSet.getColumnString("WireName"));
      localRecWireInfo.setNickName(paramFFSResultSet.getColumnString("NickName"));
      localRecWireInfo.setWireLimit(paramFFSResultSet.getColumnString("WireLimit"));
      localRecWireInfo.setSettlementDate(paramFFSResultSet.getColumnString("SettlementDate"));
      localRecWireInfo.setWireScope(paramFFSResultSet.getColumnString("WireScope"));
      localRecWireInfo.setMathRule(paramFFSResultSet.getColumnString("MathRule"));
      localRecWireInfo.setOrigToBeneficiaryMemo(paramFFSResultSet.getColumnString("OrigToBeneficiaryMemo"));
      localRecWireInfo.setOrigToBeneficiaryInfo(paramFFSResultSet.getColumnString("OrigToBeneficiaryInfo"));
      localRecWireInfo.setBanktoBankInfo(paramFFSResultSet.getColumnString("BanktoBankInfo"));
      localRecWireInfo.setProcessedBy(paramFFSResultSet.getColumnString("ProcessedBy"));
      localRecWireInfo.setTemplateId(paramFFSResultSet.getColumnString("TemplateId"));
      localRecWireInfo.setAgentId(paramFFSResultSet.getColumnString("AgentId"));
      localRecWireInfo.setAgentName(paramFFSResultSet.getColumnString("AgentName"));
      localRecWireInfo.setMethod(paramFFSResultSet.getColumnString("Method"));
      localRecWireInfo.setCustomerRef(paramFFSResultSet.getColumnString("CustomerRef"));
      localRecWireInfo.setOriginatorCharges(paramFFSResultSet.getColumnString("OriginatorCharges"));
      localRecWireInfo.setReceiverCharges(paramFFSResultSet.getColumnString("ReceiverCharges"));
      localRecWireInfo.setWireChargesDetails(paramFFSResultSet.getColumnString("WireChargesDetails"));
      localRecWireInfo.setOrgChargesAccountNum(paramFFSResultSet.getColumnString("OrgChargesAccountNum"));
      localRecWireInfo.setBenefChargesAccountNum(paramFFSResultSet.getColumnString("BenefChargesAccountNum"));
      localRecWireInfo.setUserId(paramFFSResultSet.getColumnString("UserId"));
      localRecWireInfo.setAgentType(paramFFSResultSet.getColumnString("AgentType"));
      localRecWireInfo.setOrigAmount(paramFFSResultSet.getColumnString("OrigAmount"));
      localRecWireInfo.setOrigCurrency(paramFFSResultSet.getColumnString("OrigCurrency"));
      localRecWireInfo.setWireCreditId(paramFFSResultSet.getColumnString("WireCreditID"));
      localRecWireInfo.setByOrderOfName(paramFFSResultSet.getColumnString("ByOrderOfName"));
      localRecWireInfo.setByOrderOfAddr1(paramFFSResultSet.getColumnString("ByOrderOfAddr1"));
      localRecWireInfo.setByOrderOfAddr2(paramFFSResultSet.getColumnString("ByOrderOfAddr2"));
      localRecWireInfo.setByOrderOfAddr3(paramFFSResultSet.getColumnString("ByOrderOfAddr3"));
      localRecWireInfo.setByOrderOfAcctNum(paramFFSResultSet.getColumnString("ByOrderOfAcct"));
      localRecWireInfo.setCreateDate(paramFFSResultSet.getColumnString("DateCreate"));
    }
    else
    {
      paramWireInfo.setSrvrTid(paramFFSResultSet.getColumnString("SrvrTID"));
      paramWireInfo.setFromBankId(paramFFSResultSet.getColumnString("BankFromID"));
      paramWireInfo.setFromBranchId(paramFFSResultSet.getColumnString("BranchFromID"));
      paramWireInfo.setCustomerID(paramFFSResultSet.getColumnString("CustomerID"));
      paramWireInfo.setRecSrvrTid(paramFFSResultSet.getColumnString("RecSrvrTID"));
      paramWireInfo.setAmount(paramFFSResultSet.getColumnString("Amount"));
      paramWireInfo.setFromAcctId(paramFFSResultSet.getColumnString("AcctDebitID"));
      paramWireInfo.setFromAcctType(paramFFSResultSet.getColumnString("AcctDebitType"));
      paramWireInfo.setFromAcctKey(paramFFSResultSet.getColumnString("AcctDebitKey"));
      paramWireInfo.setWireType(paramFFSResultSet.getColumnString("WireType"));
      paramWireInfo.setWireCategory(paramFFSResultSet.getColumnString("WireCategory"));
      paramWireInfo.setWireGroup(paramFFSResultSet.getColumnString("WireGroup"));
      paramWireInfo.setWireDest(paramFFSResultSet.getColumnString("WireDest"));
      paramWireInfo.setDateDue(paramFFSResultSet.getColumnString("DateDue"));
      paramWireInfo.setDateToPost(paramFFSResultSet.getColumnString("DateToPost"));
      paramWireInfo.setDatePosted(paramFFSResultSet.getColumnString("DatePosted"));
      paramWireInfo.setPrcStatus(paramFFSResultSet.getColumnString("Status"));
      paramWireInfo.setLogId(paramFFSResultSet.getColumnString("LogID"));
      paramWireInfo.setConfirmNum(paramFFSResultSet.getColumnString("ConfirmNum"));
      paramWireInfo.setPayInstruct(paramFFSResultSet.getColumnString("PayInstruct"));
      paramWireInfo.setMemo(paramFFSResultSet.getColumnString("Memo"));
      paramWireInfo.setAmtCurrency(paramFFSResultSet.getColumnString("AmtCurrency"));
      paramWireInfo.setDestCurrency(paramFFSResultSet.getColumnString("DestCurrency"));
      paramWireInfo.setWirePayeeId(paramFFSResultSet.getColumnString("WirePayeeID"));
      paramWireInfo.setExchangeRate(paramFFSResultSet.getColumnString("ExchangeRate"));
      paramWireInfo.setWireFee(paramFFSResultSet.getColumnString("XferFee"));
      paramWireInfo.setConfirmMsg(paramFFSResultSet.getColumnString("ConfirmMsg"));
      paramWireInfo.setFiID(paramFFSResultSet.getColumnString("FIID"));
      paramWireInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
      paramWireInfo.setConfirmNum2(paramFFSResultSet.getColumnString("ConfirmNum2"));
      paramWireInfo.setBatchId(paramFFSResultSet.getColumnString("BatchId"));
      paramWireInfo.setContractNumber(paramFFSResultSet.getColumnString("ContractNumber"));
      paramWireInfo.setExtId(paramFFSResultSet.getColumnString("ExtId"));
      paramWireInfo.setWireSource(paramFFSResultSet.getColumnString("WireSource"));
      paramWireInfo.setWireName(paramFFSResultSet.getColumnString("WireName"));
      paramWireInfo.setNickName(paramFFSResultSet.getColumnString("NickName"));
      paramWireInfo.setWireLimit(paramFFSResultSet.getColumnString("WireLimit"));
      paramWireInfo.setSettlementDate(paramFFSResultSet.getColumnString("SettlementDate"));
      paramWireInfo.setWireScope(paramFFSResultSet.getColumnString("WireScope"));
      paramWireInfo.setMathRule(paramFFSResultSet.getColumnString("MathRule"));
      paramWireInfo.setOrigToBeneficiaryMemo(paramFFSResultSet.getColumnString("OrigToBeneficiaryMemo"));
      paramWireInfo.setOrigToBeneficiaryInfo(paramFFSResultSet.getColumnString("OrigToBeneficiaryInfo"));
      paramWireInfo.setBanktoBankInfo(paramFFSResultSet.getColumnString("BanktoBankInfo"));
      paramWireInfo.setProcessedBy(paramFFSResultSet.getColumnString("ProcessedBy"));
      paramWireInfo.setTemplateId(paramFFSResultSet.getColumnString("TemplateId"));
      paramWireInfo.setAgentId(paramFFSResultSet.getColumnString("AgentId"));
      paramWireInfo.setAgentName(paramFFSResultSet.getColumnString("AgentName"));
      paramWireInfo.setMethod(paramFFSResultSet.getColumnString("Method"));
      paramWireInfo.setAmountType(paramFFSResultSet.getColumnString("AmountType"));
      paramWireInfo.setCustomerRef(paramFFSResultSet.getColumnString("CustomerRef"));
      paramWireInfo.setOriginatorCharges(paramFFSResultSet.getColumnString("OriginatorCharges"));
      paramWireInfo.setReceiverCharges(paramFFSResultSet.getColumnString("ReceiverCharges"));
      paramWireInfo.setWireChargesDetails(paramFFSResultSet.getColumnString("WireChargesDetails"));
      paramWireInfo.setOrgChargesAccountNum(paramFFSResultSet.getColumnString("OrgChargesAccountNum"));
      paramWireInfo.setBenefChargesAccountNum(paramFFSResultSet.getColumnString("BenefChargesAccountNum"));
      paramWireInfo.setHostId(paramFFSResultSet.getColumnString("HostId"));
      paramWireInfo.setUserId(paramFFSResultSet.getColumnString("UserId"));
      paramWireInfo.setAgentType(paramFFSResultSet.getColumnString("AgentType"));
      paramWireInfo.setOrigAmount(paramFFSResultSet.getColumnString("OrigAmount"));
      paramWireInfo.setOrigCurrency(paramFFSResultSet.getColumnString("OrigCurrency"));
      paramWireInfo.setWireCreditId(paramFFSResultSet.getColumnString("WireCreditID"));
      paramWireInfo.setByOrderOfName(paramFFSResultSet.getColumnString("ByOrderOfName"));
      paramWireInfo.setByOrderOfAddr1(paramFFSResultSet.getColumnString("ByOrderOfAddr1"));
      paramWireInfo.setByOrderOfAddr2(paramFFSResultSet.getColumnString("ByOrderOfAddr2"));
      paramWireInfo.setByOrderOfAddr3(paramFFSResultSet.getColumnString("ByOrderOfAddr3"));
      paramWireInfo.setByOrderOfAcctNum(paramFFSResultSet.getColumnString("ByOrderOfAcct"));
      paramWireInfo.setCreateDate(paramFFSResultSet.getColumnString("DateCreate"));
    }
  }
  
  private static void jdMethod_goto(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    String str1 = "Wire.checkWirePayeeCustFI ";
    String str2 = null;
    String str3 = paramWireInfo.getWirePayeeId();
    FFSDebug.log(str1 + ": PayeeID:", str3, " CustID:", paramWireInfo.getCustomerID(), " FIID:", paramWireInfo.getFiID(), 6);
    try
    {
      str2 = paramWireInfo.getWireType();
      if ((str2 == null) || ((!str2.trim().equalsIgnoreCase("TEMPLATE")) && (!str2.trim().equalsIgnoreCase("RECTEMPLATE"))) || ((str3 != null) && (str3.trim().length() != 0)))
      {
        WirePayeeInfo localWirePayeeInfo = WirePayee.getWirePayeeInfo(paramFFSConnectionHolder, str3, false);
        if ((localWirePayeeInfo == null) || (localWirePayeeInfo.getStatusCode() == 16020))
        {
          localObject = BPWLocaleUtil.getMessage(19120, new String[] { str3 }, "WIRE_MESSAGE");
          paramWireInfo.setStatusCode(19120);
          paramWireInfo.setStatusMsg((String)localObject);
          FFSDebug.log(str1 + "failed, ", (String)localObject, 0);
          return;
        }
        localObject = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
        String str4 = ((PropertyConfig)localObject).otherProperties.getProperty("bpw.wire.validate.wire.destination", "Y");
        FFSDebug.log(str1 + "validateWireDest flag, ", str4, 6);
        if (str4.equals("Y"))
        {
          validatePayeeDestWireDestCombination(paramWireInfo, localWirePayeeInfo);
          if (paramWireInfo.getStatusCode() != 0)
          {
            FFSDebug.log(str1 + "failed, ", paramWireInfo.getStatusMsg(), 0);
            return;
          }
        }
        BPWBankInfo localBPWBankInfo = localWirePayeeInfo.getBeneficiaryBankInfo();
        if ((paramWireInfo.getWireDest() != null) && ((paramWireInfo.getWireDest().equals("FEDWIRE")) || (paramWireInfo.getWireDest().equals("BOOKTRANSFER")) || (paramWireInfo.getWireDest().equals("DRAWDOWN"))) && (localBPWBankInfo != null) && (localBPWBankInfo.getSwiftRTN() != null))
        {
          paramWireInfo.setStatusCode(23335);
          paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(23335, null, "WIRE_MESSAGE"));
          return;
        }
        paramWireInfo.setWirePayeeInfo(localWirePayeeInfo);
      }
      jdMethod_else(paramFFSConnectionHolder, paramWireInfo);
      if (paramWireInfo.getStatusCode() != 0) {
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject = "***Wire.checkWirePayeeCustFI failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject, 0);
      throw new FFSException(localThrowable, (String)localObject);
    }
    paramWireInfo.setStatusCode(0);
    paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    FFSDebug.log(str1, ": Done", 6);
  }
  
  private static void jdMethod_else(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    String str1 = "Wire.checkWireCustFI ";
    String str2 = paramWireInfo.getCustomerID();
    String str3 = paramWireInfo.getFiID();
    BPWFIInfo localBPWFIInfo = null;
    CustomerInfo localCustomerInfo = null;
    String str4 = null;
    FFSDebug.log(str1 + ": CustID:", str2, " FIID:", str3, 6);
    try
    {
      str4 = paramWireInfo.getWireType();
      String str5;
      if ((str4 == null) || (!str4.trim().equalsIgnoreCase("TEMPLATE")) || ((str3 != null) && (str3.trim().length() != 0)))
      {
        localBPWFIInfo = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, str3);
        if (localBPWFIInfo.getFIId() == null)
        {
          str5 = BPWLocaleUtil.getMessage(23170, new String[] { str3 }, "WIRE_MESSAGE");
          paramWireInfo.setStatusCode(23170);
          paramWireInfo.setStatusMsg(str5);
          FFSDebug.log(str1 + "failed, ", str5, 0);
          return;
        }
      }
      localCustomerInfo = Customer.getCustomerInfo(str2, paramFFSConnectionHolder, paramWireInfo);
      if (localCustomerInfo == null)
      {
        str5 = BPWLocaleUtil.getMessage(19130, new String[] { str2 }, "WIRE_MESSAGE");
        paramWireInfo.setStatusCode(19130);
        paramWireInfo.setStatusMsg(str5);
        FFSDebug.log(str1 + "failed, ", str5, 0);
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      String str6 = "***Wire.checkWireCustFI failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str6, 0);
      throw new FFSException(localThrowable, str6);
    }
    paramWireInfo.setStatusCode(0);
    paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
  }
  
  public static void validatePayeeDestWireDestCombination(WireInfo paramWireInfo, WirePayeeInfo paramWirePayeeInfo)
    throws BPWException
  {
    String str1 = paramWirePayeeInfo.getPayeeDest();
    String str2 = paramWireInfo.getWireDest();
    if ((str1 != null) && (str2 != null)) {
      if (str1.equals("DRAWDOWN"))
      {
        if (!str2.equals("DRAWDOWN"))
        {
          paramWireInfo.setStatusCode(19550);
          paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19550, null, "WIRE_MESSAGE"));
        }
      }
      else if (str1.equals("BOOK"))
      {
        if (!str2.equals("BOOKTRANSFER"))
        {
          paramWireInfo.setStatusCode(19550);
          paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19550, null, "WIRE_MESSAGE"));
        }
      }
      else if ((!str2.equals("DOMESTIC")) && (!str2.equals("FEDWIRE")) && (!str2.equals("INTERNATIONAL")))
      {
        paramWireInfo.setStatusCode(19550);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19550, null, "WIRE_MESSAGE"));
        return;
      }
    }
    paramWireInfo.setStatusCode(0);
    paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
  }
  
  public static boolean isDuplicateTemplate(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Wire.isDuplicateTemplate";
    FFSDebug.log(str1, ": start, wire=" + paramWireInfo, 6);
    String str2 = null;
    Object[] arrayOfObject = { paramWireInfo.getWireName(), paramWireInfo.getNickName(), paramWireInfo.getCustomerID() };
    FFSResultSet localFFSResultSet = null;
    if (paramBoolean) {
      str2 = "SELECT a.RecSrvrTID, BankFromID, BranchFromID, CustomerID,WirePayeeID, FIID, Amount, AcctDebitID,AcctDebitType,AcctDebitKey, WireType,WireCategory,WireGroup,WireDest,Status,LogID,PayInstruct,Memo,AmtCurrency,DestCurrency,XferFee, StartAmount,EndAmount,DateCreate,Frequency,StartDate,EndDate,InstanceCount,SubmittedBy, ExchangeRate, BatchId, ContractNumber,ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,AgentId,AgentName,Method, ProcessedBy, TemplateId,CustomerRef,OriginatorCharges,ReceiverCharges, WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum, UserId,AgentType,OrigAmount,OrigCurrency,WireCreditID , ByOrderOfName, ByOrderOfAddr1, ByOrderOfAddr2, ByOrderOfAddr3, ByOrderOfAcct, b.Version  FROM BPW_RecWireInfo a, BPW_RecWireInfo2 b  WHERE a.RecSrvrTID = b.RecSrvrTID AND WireType = 'RECTEMPLATE'  AND WireName = ?  AND NickName = ?  AND CustomerID = ? ";
    } else {
      str2 = "SELECT a.SrvrTID,BankFromID,BranchFromID,CustomerID,WirePayeeID,RecSrvrTID,FIID,Amount,AmtCurrency,DestCurrency,XferFee,AcctDebitID, AcctDebitType,AcctDebitKey,DateCreate,DateDue,DateToPost,DatePosted, WireType,WireCategory,WireGroup,WireDest, BatchId, ContractNumber, Status,LogID,ConfirmNum, ConfirmNum2, ConfirmMsg, PayInstruct, Memo,ExchangeRate,SubmittedBy,ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,ProcessedBy,TemplateId,AgentId, AgentName,Method,AmountType,CustomerRef,OriginatorCharges,ReceiverCharges, WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum,HostId, UserId,AgentType,OrigAmount,OrigCurrency, WireCreditID , ByOrderOfName, ByOrderOfAddr1, ByOrderOfAddr2, ByOrderOfAddr3, ByOrderOfAcct, b.Version  FROM BPW_WireInfo a, BPW_WireInfo2 b  WHERE a.SrvrTID = b.SrvrTID AND WireType = 'TEMPLATE'  AND WireName = ?  AND NickName = ?  AND CustomerID = ? ";
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        String str3 = localFFSResultSet.getColumnString(1);
        if ((paramWireInfo.getSrvrTid() != null) && (paramWireInfo.getSrvrTid().equals(str3)))
        {
          bool = false;
          return bool;
        }
        boolean bool = true;
        return bool;
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str6 = str1 + " failed: ";
        String str7 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str6, str7, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return false;
  }
  
  public static boolean hasTemplatedPayee(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    String str1 = "Wire.hasTemplatedPayee";
    FFSDebug.log(str1, ": start, wire=" + paramWireInfo, 6);
    String str2 = paramWireInfo.getTemplateId();
    String str3 = paramWireInfo.getWirePayeeId();
    if ((str2 == null) || (str2.length() == 0) || (str3 == null) || (str3.length() == 0)) {
      return false;
    }
    FFSResultSet localFFSResultSet = null;
    String str4 = "SELECT WirePayeeID  FROM BPW_WireInfo WHERE SrvrTID = ?  AND WirePayeeID = ? ";
    Object[] arrayOfObject = { str2, str3 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str4, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        bool = true;
        return bool;
      }
      boolean bool = false;
      return bool;
    }
    catch (Throwable localThrowable)
    {
      String str5 = str1 + " failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5, str6, 0);
      throw new FFSException(localThrowable, str5);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str7 = str1 + " failed: ";
        String str8 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str7, str8, 0);
      }
    }
  }
  
  public static String[] getSrvrTIDsForRecSrvrTID(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "Wire.getSrvrTIDsForRecSrvrTID";
    FFSDebug.log(str1, ": start, recSrvrTid=", paramString, 6);
    String[] arrayOfString = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    String str2 = "SELECT SrvrTID FROM BPW_WireInfo WHERE RecSrvrTID = ? AND Status IN ('CREATED','RELEASEPENDING','WILLPROCESSON','APPROVAL_REJECTED','APPROVAL_PENDING')";
    Object[] arrayOfObject = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localArrayList.add(localFFSResultSet.getColumnString("SrvrTID"));
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    arrayOfString = (String[])localArrayList.toArray(new String[0]);
    FFSDebug.log(str1, ": done", 6);
    return arrayOfString;
  }
  
  public static WireInfo cancelWire(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Wire.cancelWire";
    FFSDebug.log(str1, ": start, SrvrTid=", paramWireInfo.getSrvrTid(), "New Status=", paramWireInfo.getPrcStatus(), 6);
    String str2 = null;
    StringBuffer localStringBuffer1 = null;
    StringBuffer localStringBuffer2 = null;
    Object[] arrayOfObject = null;
    ArrayList localArrayList = null;
    int i = 0;
    int j = 0;
    String str3 = null;
    if (paramFFSConnectionHolder == null)
    {
      paramWireInfo.setStatusCode(16000);
      String str4 = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str4);
      FFSDebug.log(str1, str4, 0);
      return paramWireInfo;
    }
    paramWireInfo.setPrcStatus("CANCELEDON");
    if ((paramWireInfo.getWireType() != null) && (paramWireInfo.getWireType().trim().equalsIgnoreCase("TEMPLATE")))
    {
      FFSDebug.log(str1, " Deleting a template", 6);
      i = 1;
    }
    else if ((paramWireInfo.getWireType() != null) && (paramWireInfo.getWireType().trim().equalsIgnoreCase("RECTEMPLATE")))
    {
      j = 1;
      FFSDebug.log(str1, " Deleting a rectemplate", 6);
    }
    localStringBuffer1 = new StringBuffer();
    localStringBuffer2 = new StringBuffer();
    localArrayList = new ArrayList();
    if (paramBoolean)
    {
      str3 = "RecSrvrTID";
      if (j != 0)
      {
        localStringBuffer1.append("Delete From BPW_RecWireInfo  WHERE RecSrvrTID = ? AND WireType = 'RECTEMPLATE' ");
        localStringBuffer2.append("Delete From BPW_RecWireInfo2  WHERE RecSrvrTID = ?");
        str3 = "RecSrvrTID";
      }
      else
      {
        localStringBuffer1.append("UPDATE BPW_RecWireInfo SET Status=? WHERE RecSrvrTID=?");
        localStringBuffer1.append(" AND Status = ?");
        localArrayList.add("WILLPROCESSON");
        localStringBuffer2.append("Update BPW_RecWireInfo2 Set Version=Version+1  WHERE RecSrvrTID = ?");
      }
    }
    else
    {
      str3 = "SrvrTID";
      if (i != 0)
      {
        localStringBuffer1.append("Delete From BPW_WireInfo  WHERE SrvrTID = ? AND WireType = 'TEMPLATE'");
        localStringBuffer2.append("Delete From BPW_WireInfo2  WHERE SrvrTID = ?");
      }
      else
      {
        localStringBuffer1.append("UPDATE BPW_WireInfo SET Status=? WHERE SrvrTID=?");
        localStringBuffer1.append(" AND Status IN (?, ?, ?, ?, ?, ?)");
        localArrayList.add("CREATED");
        localArrayList.add("RELEASEPENDING");
        localArrayList.add("APPROVAL_PENDING");
        localArrayList.add("APPROVAL_REJECTED");
        localArrayList.add("WILLPROCESSON");
        localArrayList.add("APPROVAL_NOT_ALLOWED");
        localStringBuffer2.append("Update BPW_WireInfo2 Set Version=Version+1  WHERE SrvrTID = ?");
      }
    }
    localStringBuffer1.append(" AND CustomerID = ? ");
    localArrayList.add(paramWireInfo.getCustomerID());
    localStringBuffer1.append(" AND FIID = ? ");
    localArrayList.add(paramWireInfo.getFiID());
    localArrayList.add(0, paramWireInfo.getSrvrTid());
    if ((i == 0) && (j == 0)) {
      localArrayList.add(0, paramWireInfo.getPrcStatus());
    }
    arrayOfObject = localArrayList.toArray();
    for (int k = 0; k < localArrayList.size(); k++) {
      FFSDebug.log(str1, "Sql Param:" + k + " :" + String.valueOf(localArrayList.get(k)), 6);
    }
    try
    {
      str2 = localStringBuffer1.toString();
      FFSDebug.log(str1, " stmt:", str2, 6);
      k = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (k == 0)
      {
        localObject = new StringBuffer();
        ((StringBuffer)localObject).append("***").append(str1);
        ((StringBuffer)localObject).append(" failed, No record is found in DB with");
        if (paramBoolean) {
          ((StringBuffer)localObject).append(" RecSrvrTId:");
        } else {
          ((StringBuffer)localObject).append(" SrvrTId:");
        }
        ((StringBuffer)localObject).append(paramWireInfo.getSrvrTid());
        ((StringBuffer)localObject).append(" FIID:").append(paramWireInfo.getFiID());
        ((StringBuffer)localObject).append(" and CustomerID:").append(paramWireInfo.getCustomerID());
        FFSDebug.log(((StringBuffer)localObject).toString(), 0);
        paramWireInfo.setStatusCode(19280);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19280, null, "WIRE_MESSAGE"));
      }
      else
      {
        localObject = new Object[] { paramWireInfo.getSrvrTid() };
        str2 = localStringBuffer2.toString();
        FFSDebug.log(str1, " stmt:", str2, 6);
        k = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject, paramWireInfo, str3, paramWireInfo.getSrvrTid());
        if ((k == 0) && (paramWireInfo.getStatusCode() != 0)) {
          return paramWireInfo;
        }
        paramWireInfo.setStatusCode(0);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject + str5, 0);
      throw new FFSException(localThrowable, (String)localObject);
    }
    FFSDebug.log(str1, "done", 6);
    return paramWireInfo;
  }
  
  public static RecWireInfo canSingleForRecurring(FFSConnectionHolder paramFFSConnectionHolder, RecWireInfo paramRecWireInfo)
    throws FFSException
  {
    String str = paramRecWireInfo.getSrvrTid();
    String[] arrayOfString = paramRecWireInfo.getSingleIds();
    if ((arrayOfString == null) || (arrayOfString.length == 0))
    {
      FFSDebug.log("Wire.canSingleForRecurring: ", "No single wire is found for the recurring model, RecSrvrTid:", str, 6);
      return paramRecWireInfo;
    }
    for (int i = 0; i < arrayOfString.length; i++)
    {
      paramRecWireInfo.setSrvrTid(arrayOfString[i]);
      cancelWire(paramFFSConnectionHolder, paramRecWireInfo, false);
      if (paramRecWireInfo.getStatusCode() != 0) {
        break;
      }
    }
    paramRecWireInfo.setSrvrTid(str);
    return paramRecWireInfo;
  }
  
  public static WireInfo updateWire(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Wire.updateWire";
    StringBuffer localStringBuffer1 = null;
    StringBuffer localStringBuffer2 = null;
    Object[] arrayOfObject = null;
    ArrayList localArrayList = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    FFSDebug.log(str1, ": Starts. Recurring: " + paramBoolean, 6);
    String str5;
    if (paramFFSConnectionHolder == null)
    {
      paramWireInfo.setStatusCode(16000);
      str5 = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg(str5);
      FFSDebug.log(str1, str5, 0);
      return paramWireInfo;
    }
    str3 = paramWireInfo.getWireSource();
    if ((str3 != null) && (str3.trim().equalsIgnoreCase("HOST"))) {
      jdMethod_else(paramFFSConnectionHolder, paramWireInfo);
    } else {
      jdMethod_goto(paramFFSConnectionHolder, paramWireInfo);
    }
    if (paramWireInfo.getStatusCode() != 0) {
      return paramWireInfo;
    }
    str4 = paramWireInfo.getWireType().trim();
    if (((str4.equalsIgnoreCase("RECTEMPLATE")) || (str4.equalsIgnoreCase("TEMPLATE"))) && (isDuplicateTemplate(paramFFSConnectionHolder, paramWireInfo, paramBoolean)))
    {
      FFSDebug.log(str1, "The template Name and Nickname combination of the specified wire is duplicate.", 0);
      paramWireInfo.setStatusCode(19760);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19760, null, "WIRE_MESSAGE"));
      return paramWireInfo;
    }
    try
    {
      paramWireInfo.setDatePosted(null);
      arrayOfObject = jdMethod_else(paramWireInfo, paramBoolean);
      localArrayList = new ArrayList(Arrays.asList(arrayOfObject));
      localStringBuffer1 = new StringBuffer();
      localStringBuffer2 = new StringBuffer();
      str5 = null;
      if (paramBoolean)
      {
        str5 = "RecSrvrTID";
        if ((str4 != null) && (str4.equalsIgnoreCase("RECTEMPLATE")))
        {
          localStringBuffer1.append("Update BPW_RecWireInfo Set ContractNumber=?,BankFromID=?, BranchFromID=?,CustomerID=?,WirePayeeID=?,FIID=?,Amount=?,AcctDebitID=?,AcctDebitType=?,AcctDebitKey=?,WireType=?,WireCategory=?,WireGroup=?, WireDest=?,LogID=?,PayInstruct=?,Memo=?,AmtCurrency=?,DestCurrency=?, StartAmount=?, EndAmount=?,Frequency=?,StartDate=?,EndDate=?, InstanceCount=?,ExchangeRate=?,WireSource=?,WireName=?, NickName=?,WireLimit=?,SettlementDate=?,WireScope=?,MathRule=?, OrigToBeneficiaryMemo=?,OrigToBeneficiaryInfo=?,BanktoBankInfo=?, AgentId=?,AgentName=?,Method=?, ProcessedBy=?, CustomerRef=?, OriginatorCharges=?, ReceiverCharges=?, WireChargesDetails=?, OrgChargesAccountNum=?, BenefChargesAccountNum=?, AgentType=?, OrigAmount=?, OrigCurrency=?, WireCreditID=?, LastModified = ?, SubmittedBy = ? WHERE RecSrvrTID = ?");
          localStringBuffer1.append(" AND WireType = ?");
          localArrayList.add("RECTEMPLATE");
          str2 = "RECTEMPLATE";
        }
        else
        {
          localStringBuffer1.append("Update BPW_RecWireInfo Set Amount = ?, AcctDebitID = ?,  AcctDebitType = ?, AcctDebitKey = ?, PayInstruct = ?, Memo = ?,  StartAmount = ?, EndAmount = ?,Frequency = ?, StartDate = ?,  EndDate = ?, InstanceCount = ?, LastModified = ?, WirePayeeID = ?,  ExchangeRate = ?, WireType = ?, WireCategory = ?,WireGroup = ?, WireDest = ?, ContractNumber = ?,  WireSource = ?, WireName = ?, NickName = ?,WireLimit = ?,  SettlementDate = ?,WireScope = ?, MathRule = ?, OrigToBeneficiaryMemo = ?,OrigToBeneficiaryInfo = ?,BanktoBankInfo = ?, AgentId = ?, AgentName = ?, Method = ?, ProcessedBy = ?, CustomerRef=?, OriginatorCharges=?, ReceiverCharges=?, WireChargesDetails=?,  OrgChargesAccountNum=?, BenefChargesAccountNum=?, AgentType=?,  OrigAmount=?, OrigCurrency=?, WireCreditID=?, AmtCurrency=?, DestCurrency=?, BankFromID=?, BranchFromID=?, SubmittedBy = ? WHERE RecSrvrTID = ?");
          localStringBuffer1.append(" AND Status = ?");
          localArrayList.add("WILLPROCESSON");
          str2 = "RECWIRE";
        }
        localStringBuffer2.append("Update BPW_RecWireInfo2 Set ByOrderOfName=?,  ByOrderOfAddr1=?, ByOrderOfAddr2=?, ByOrderOfAddr3=?, ByOrderOfAcct=?, Version=Version+1  WHERE RecSrvrTID = ?");
      }
      else
      {
        str5 = "SrvrTID";
        if ((str4 != null) && (str4.equalsIgnoreCase("TEMPLATE")))
        {
          localStringBuffer1.append("Update BPW_WireInfo Set ContractNumber = ?,BankFromID = ?, BranchFromID = ?,CustomerID = ?,WirePayeeID = ?, FIID = ?,Amount = ?,AcctDebitID = ?,AcctDebitType = ?,AcctDebitKey = ?, DateDue = ?,DateToPost = ?,WireType = ?,WireCategory = ?,WireGroup = ?,WireDest = ?,LogID = ?,PayInstruct = ?,Memo = ?,AmtCurrency = ?,DestCurrency = ?,ExchangeRate = ?, WireSource = ?,WireName = ?,NickName = ?,WireLimit = ?, SettlementDate = ?,WireScope = ?,MathRule = ?,OrigToBeneficiaryMemo = ?, OrigToBeneficiaryInfo = ?,BanktoBankInfo = ?,ProcessedBy = ?,  AgentId = ?, AgentName = ?, Method = ?, AmountType=?, CustomerRef=?,  OriginatorCharges=?, ReceiverCharges=?, WireChargesDetails=?,  OrgChargesAccountNum=?, BenefChargesAccountNum=?, AgentType=?,  OrigAmount=?, OrigCurrency=?, WireCreditID=?, LastModified = ?, SubmittedBy = ? WHERE SrvrTID = ?");
          localStringBuffer1.append(" AND WireType= ? ");
          localArrayList.add("TEMPLATE");
          str2 = "TEMPLATE";
        }
        else
        {
          localStringBuffer1.append("Update BPW_WireInfo Set Amount = ?,  AcctDebitID = ?, AcctDebitType = ?, AcctDebitKey = ?,  DateDue = ?, DateToPost = ?, PayInstruct = ?, Memo = ?,  LastModified = ?, WirePayeeID = ?, ExchangeRate = ?,  WireType = ?, WireCategory = ?,WireGroup = ?,WireDest = ?,  ContractNumber = ?,  WireSource = ?, WireName = ?, NickName = ?,WireLimit = ?,   SettlementDate = ?,WireScope = ?, MathRule = ?,OrigToBeneficiaryMemo = ?,   OrigToBeneficiaryInfo = ?, BanktoBankInfo = ?, ProcessedBy = ?,  AgentId = ?, AgentName = ?, Method = ?, AmountType=?, CustomerRef=?,  OriginatorCharges=?, ReceiverCharges=?, WireChargesDetails=?,  OrgChargesAccountNum=?, BenefChargesAccountNum=?, AgentType=?,  OrigAmount=?, OrigCurrency=?, WireCreditID=?, AmtCurrency=?, DestCurrency=?, BankFromID=?, BranchFromID=?, SubmittedBy = ? WHERE SrvrTID = ?");
          localStringBuffer1.append(" AND Status IN (?, ?, ?, ?, ?)");
          localArrayList.add("CREATED");
          localArrayList.add("RELEASEPENDING");
          localArrayList.add("APPROVAL_PENDING");
          localArrayList.add("APPROVAL_NOT_ALLOWED");
          localArrayList.add("APPROVAL_REJECTED");
          str2 = "WIRE";
        }
        localStringBuffer2.append("Update BPW_WireInfo2 Set ByOrderOfName=?,  ByOrderOfAddr1=?, ByOrderOfAddr2=?, ByOrderOfAddr3=?, ByOrderOfAcct=?, Version=Version+1  WHERE SrvrTID = ?");
      }
      arrayOfObject = localArrayList.toArray();
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer1.toString(), arrayOfObject);
      if (i == 0)
      {
        paramWireInfo.setStatusCode(16020);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WireInfo" }, "WIRE_MESSAGE"));
      }
      else
      {
        arrayOfObject = jdMethod_try(paramWireInfo, paramBoolean);
        i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer2.toString(), arrayOfObject, paramWireInfo, str5, paramWireInfo.getSrvrTid());
        if ((i == 0) && (paramWireInfo.getStatusCode() != 0)) {
          return paramWireInfo;
        }
        if (null != paramWireInfo.getExtInfo())
        {
          int j = BPWExtraInfo.processXtraInfo(paramFFSConnectionHolder, paramWireInfo.getFiID(), paramWireInfo.getSrvrTid(), str2, paramWireInfo.getExtInfo());
          if (j != 0)
          {
            paramWireInfo.setStatusCode(19270);
            paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19270, null, "WIRE_MESSAGE"));
          }
          else
          {
            paramWireInfo.setStatusCode(0);
            paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
          }
        }
        else
        {
          paramWireInfo.setStatusCode(0);
          paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str6 = str1 + " failed: ";
      String str7 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str6, str7, 0);
      throw new FFSException(localThrowable, str6);
    }
    return paramWireInfo;
  }
  
  private static Object[] jdMethod_else(WireInfo paramWireInfo, boolean paramBoolean)
    throws FFSException
  {
    if (paramBoolean)
    {
      localObject = (RecWireInfo)paramWireInfo;
      if ((paramWireInfo.getWireType() != null) && (paramWireInfo.getWireType().trim().equalsIgnoreCase("RECTEMPLATE")))
      {
        arrayOfObject = new Object[] { ((RecWireInfo)localObject).getContractNumber(), ((RecWireInfo)localObject).getFromBankId(), ((RecWireInfo)localObject).getFromBranchId(), ((RecWireInfo)localObject).getCustomerID(), ((RecWireInfo)localObject).getWirePayeeId(), ((RecWireInfo)localObject).getFiID(), ((RecWireInfo)localObject).getAmount(), ((RecWireInfo)localObject).getFromAcctId(), ((RecWireInfo)localObject).getFromAcctType(), ((RecWireInfo)localObject).getFromAcctKey(), ((RecWireInfo)localObject).getWireType().toUpperCase(), ((RecWireInfo)localObject).getWireCategory(), ((RecWireInfo)localObject).getWireGroup(), ((RecWireInfo)localObject).getWireDest(), ((RecWireInfo)localObject).getLogId(), ((RecWireInfo)localObject).getPayInstruct(), ((RecWireInfo)localObject).getMemo(), ((RecWireInfo)localObject).getAmtCurrency(), ((RecWireInfo)localObject).getDestCurrency(), ((RecWireInfo)localObject).getStartAmount(), ((RecWireInfo)localObject).getEndAmount(), new Integer(FFSUtil.getFreqInt(((RecWireInfo)localObject).getFrequency())), Integer.toString(BPWUtil.getDateDBFormat(((RecWireInfo)localObject).getStartDate())), Integer.toString(BPWUtil.getDateDBFormat(((RecWireInfo)localObject).getEndDate())), new Integer(((RecWireInfo)localObject).getPmtsCount()), ((RecWireInfo)localObject).getExchangeRate(), ((RecWireInfo)localObject).getWireSource(), ((RecWireInfo)localObject).getWireName(), ((RecWireInfo)localObject).getNickName(), ((RecWireInfo)localObject).getWireLimit(), ((RecWireInfo)localObject).getSettlementDate(), ((RecWireInfo)localObject).getWireScope(), ((RecWireInfo)localObject).getMathRule(), ((RecWireInfo)localObject).getOrigToBeneficiaryMemo(), ((RecWireInfo)localObject).getOrigToBeneficiaryInfo(), ((RecWireInfo)localObject).getBanktoBankInfo(), ((RecWireInfo)localObject).getAgentId(), ((RecWireInfo)localObject).getAgentName(), ((RecWireInfo)localObject).getMethod(), ((RecWireInfo)localObject).getProcessedBy(), ((RecWireInfo)localObject).getCustomerRef(), ((RecWireInfo)localObject).getOriginatorCharges(), ((RecWireInfo)localObject).getReceiverCharges(), ((RecWireInfo)localObject).getWireChargesDetails(), ((RecWireInfo)localObject).getOrgChargesAccountNum(), ((RecWireInfo)localObject).getBenefChargesAccountNum(), ((RecWireInfo)localObject).getAgentType(), ((RecWireInfo)localObject).getOrigAmount(), ((RecWireInfo)localObject).getOrigCurrency(), ((RecWireInfo)localObject).getWireCreditId(), DBUtil.getCurrentLogDate(), ((RecWireInfo)localObject).getSubmittedBy(), ((RecWireInfo)localObject).getSrvrTid() };
        return arrayOfObject;
      }
      Object[] arrayOfObject = { ((RecWireInfo)localObject).getAmount(), ((RecWireInfo)localObject).getFromAcctId(), ((RecWireInfo)localObject).getFromAcctType(), ((RecWireInfo)localObject).getFromAcctKey(), ((RecWireInfo)localObject).getPayInstruct(), ((RecWireInfo)localObject).getMemo(), ((RecWireInfo)localObject).getStartAmount(), ((RecWireInfo)localObject).getEndAmount(), new Integer(FFSUtil.getFreqInt(((RecWireInfo)localObject).getFrequency())), Integer.toString(BPWUtil.getDateDBFormat(((RecWireInfo)localObject).getStartDate())), Integer.toString(BPWUtil.getDateDBFormat(((RecWireInfo)localObject).getEndDate())), new Integer(((RecWireInfo)localObject).getPmtsCount()), DBUtil.getCurrentLogDate(), ((RecWireInfo)localObject).getWirePayeeId(), ((RecWireInfo)localObject).getExchangeRate(), ((RecWireInfo)localObject).getWireType().toUpperCase(), ((RecWireInfo)localObject).getWireCategory(), ((RecWireInfo)localObject).getWireGroup(), ((RecWireInfo)localObject).getWireDest(), ((RecWireInfo)localObject).getContractNumber(), ((RecWireInfo)localObject).getWireSource(), ((RecWireInfo)localObject).getWireName(), ((RecWireInfo)localObject).getNickName(), ((RecWireInfo)localObject).getWireLimit(), ((RecWireInfo)localObject).getSettlementDate(), ((RecWireInfo)localObject).getWireScope(), ((RecWireInfo)localObject).getMathRule(), ((RecWireInfo)localObject).getOrigToBeneficiaryMemo(), ((RecWireInfo)localObject).getOrigToBeneficiaryInfo(), ((RecWireInfo)localObject).getBanktoBankInfo(), ((RecWireInfo)localObject).getAgentId(), ((RecWireInfo)localObject).getAgentName(), ((RecWireInfo)localObject).getMethod(), ((RecWireInfo)localObject).getProcessedBy(), ((RecWireInfo)localObject).getCustomerRef(), ((RecWireInfo)localObject).getOriginatorCharges(), ((RecWireInfo)localObject).getReceiverCharges(), ((RecWireInfo)localObject).getWireChargesDetails(), ((RecWireInfo)localObject).getOrgChargesAccountNum(), ((RecWireInfo)localObject).getBenefChargesAccountNum(), ((RecWireInfo)localObject).getAgentType(), ((RecWireInfo)localObject).getOrigAmount(), ((RecWireInfo)localObject).getOrigCurrency(), ((RecWireInfo)localObject).getWireCreditId(), ((RecWireInfo)localObject).getAmtCurrency(), ((RecWireInfo)localObject).getDestCurrency(), ((RecWireInfo)localObject).getFromBankId(), ((RecWireInfo)localObject).getFromBranchId(), ((RecWireInfo)localObject).getSubmittedBy(), ((RecWireInfo)localObject).getSrvrTid() };
      return arrayOfObject;
    }
    if ((paramWireInfo.getWireType() != null) && (paramWireInfo.getWireType().trim().equalsIgnoreCase("TEMPLATE")))
    {
      localObject = new Object[] { paramWireInfo.getContractNumber(), paramWireInfo.getFromBankId(), paramWireInfo.getFromBranchId(), paramWireInfo.getCustomerID(), paramWireInfo.getWirePayeeId(), paramWireInfo.getFiID(), paramWireInfo.getAmount(), paramWireInfo.getFromAcctId(), paramWireInfo.getFromAcctType(), paramWireInfo.getFromAcctKey(), Integer.toString(BPWUtil.getDateDBFormat(paramWireInfo.getDateDue())), Integer.toString(BPWUtil.getDateDBFormat(paramWireInfo.getDateToPost())), paramWireInfo.getWireType().toUpperCase(), paramWireInfo.getWireCategory(), paramWireInfo.getWireGroup(), paramWireInfo.getWireDest(), paramWireInfo.getLogId(), paramWireInfo.getPayInstruct(), paramWireInfo.getMemo(), paramWireInfo.getAmtCurrency(), paramWireInfo.getDestCurrency(), paramWireInfo.getExchangeRate(), paramWireInfo.getWireSource(), paramWireInfo.getWireName(), paramWireInfo.getNickName(), paramWireInfo.getWireLimit(), paramWireInfo.getSettlementDate(), paramWireInfo.getWireScope(), paramWireInfo.getMathRule(), paramWireInfo.getOrigToBeneficiaryMemo(), paramWireInfo.getOrigToBeneficiaryInfo(), paramWireInfo.getBanktoBankInfo(), paramWireInfo.getProcessedBy(), paramWireInfo.getAgentId(), paramWireInfo.getAgentName(), paramWireInfo.getMethod(), paramWireInfo.getAmountType(), paramWireInfo.getCustomerRef(), paramWireInfo.getOriginatorCharges(), paramWireInfo.getReceiverCharges(), paramWireInfo.getWireChargesDetails(), paramWireInfo.getOrgChargesAccountNum(), paramWireInfo.getBenefChargesAccountNum(), paramWireInfo.getAgentType(), paramWireInfo.getOrigAmount(), paramWireInfo.getOrigCurrency(), paramWireInfo.getWireCreditId(), DBUtil.getCurrentLogDate(), paramWireInfo.getSubmittedBy(), paramWireInfo.getSrvrTid() };
      return localObject;
    }
    Object localObject = { paramWireInfo.getAmount(), paramWireInfo.getFromAcctId(), paramWireInfo.getFromAcctType(), paramWireInfo.getFromAcctKey(), Integer.toString(BPWUtil.getDateDBFormat(paramWireInfo.getDateDue())), Integer.toString(BPWUtil.getDateDBFormat(paramWireInfo.getDateToPost())), paramWireInfo.getPayInstruct(), paramWireInfo.getMemo(), DBUtil.getCurrentLogDate(), paramWireInfo.getWirePayeeId(), paramWireInfo.getExchangeRate(), paramWireInfo.getWireType().toUpperCase(), paramWireInfo.getWireCategory(), paramWireInfo.getWireGroup(), paramWireInfo.getWireDest(), paramWireInfo.getContractNumber(), paramWireInfo.getWireSource(), paramWireInfo.getWireName(), paramWireInfo.getNickName(), paramWireInfo.getWireLimit(), paramWireInfo.getSettlementDate(), paramWireInfo.getWireScope(), paramWireInfo.getMathRule(), paramWireInfo.getOrigToBeneficiaryMemo(), paramWireInfo.getOrigToBeneficiaryInfo(), paramWireInfo.getBanktoBankInfo(), paramWireInfo.getProcessedBy(), paramWireInfo.getAgentId(), paramWireInfo.getAgentName(), paramWireInfo.getMethod(), paramWireInfo.getAmountType(), paramWireInfo.getCustomerRef(), paramWireInfo.getOriginatorCharges(), paramWireInfo.getReceiverCharges(), paramWireInfo.getWireChargesDetails(), paramWireInfo.getOrgChargesAccountNum(), paramWireInfo.getBenefChargesAccountNum(), paramWireInfo.getAgentType(), paramWireInfo.getOrigAmount(), paramWireInfo.getOrigCurrency(), paramWireInfo.getWireCreditId(), paramWireInfo.getAmtCurrency(), paramWireInfo.getDestCurrency(), paramWireInfo.getFromBankId(), paramWireInfo.getFromBranchId(), paramWireInfo.getSubmittedBy(), paramWireInfo.getSrvrTid() };
    return localObject;
  }
  
  private static Object[] jdMethod_try(WireInfo paramWireInfo, boolean paramBoolean)
  {
    Object[] arrayOfObject = { paramWireInfo.getByOrderOfName(), paramWireInfo.getByOrderOfAddr1(), paramWireInfo.getByOrderOfAddr2(), paramWireInfo.getByOrderOfAddr3(), paramWireInfo.getByOrderOfAcctNum(), paramWireInfo.getSrvrTid() };
    return arrayOfObject;
  }
  
  private static Object[] jdMethod_byte(WireInfo paramWireInfo, boolean paramBoolean)
  {
    Object[] arrayOfObject = { paramWireInfo.getSrvrTid() };
    return arrayOfObject;
  }
  
  public static WireBatchInfo updateWiresWithBatchValues(FFSConnectionHolder paramFFSConnectionHolder, WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    String str1 = "Wire.updateWiresWithBatchValues";
    String str2 = null;
    FFSDebug.log(str1, ": Starts.", 6);
    if (paramFFSConnectionHolder == null)
    {
      paramWireBatchInfo.setStatusCode(16000);
      localObject = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      paramWireBatchInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      return paramWireBatchInfo;
    }
    str2 = "Update BPW_WireInfo Set DateDue = ?, SettlementDate = ?,  AmtCurrency = ?,DestCurrency = ?, ExchangeRate = ?,  ContractNumber = ?, MathRule = ?, AgentId = ?, AgentName = ?,  AgentType=?, OrigCurrency=?  WHERE BatchId = ? AND Status = 'CREATED' ";
    Object localObject = { String.valueOf(BPWUtil.getDateDBFormat(paramWireBatchInfo.getDateDue())), paramWireBatchInfo.getSettlementDate(), paramWireBatchInfo.getAmtCurrency(), paramWireBatchInfo.getDestCurrency(), paramWireBatchInfo.getExchangeRate(), paramWireBatchInfo.getContractNumber(), paramWireBatchInfo.getMathRule(), paramWireBatchInfo.getAgentId(), paramWireBatchInfo.getAgentName(), paramWireBatchInfo.getAgentType(), paramWireBatchInfo.getOrigCurrency(), paramWireBatchInfo.getBatchId() };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      paramWireBatchInfo.setStatusCode(0);
      paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    return paramWireBatchInfo;
  }
  
  public static RecWireInfo updateSingleForRecurring(FFSConnectionHolder paramFFSConnectionHolder, RecWireInfo paramRecWireInfo)
    throws FFSException
  {
    String str1 = "Wire.updateSingleForRecurring: ";
    FFSDebug.log(str1, "Start", 6);
    String str2 = paramRecWireInfo.getSrvrTid();
    String[] arrayOfString = paramRecWireInfo.getSingleIds();
    if ((arrayOfString == null) || (arrayOfString.length == 0))
    {
      FFSDebug.log(str1, "Single IDs null", 6);
      return paramRecWireInfo;
    }
    int i = paramRecWireInfo.getVersion();
    for (int j = 0; j < arrayOfString.length; j++)
    {
      paramRecWireInfo.setSrvrTid(arrayOfString[j]);
      WireInfo localWireInfo = getWireInfo(paramFFSConnectionHolder, paramRecWireInfo);
      paramRecWireInfo.setVersion(localWireInfo.getVersion());
      String str3 = paramRecWireInfo.getStartDate();
      paramRecWireInfo.setDateDue(str3);
      str3 = String.valueOf(Integer.parseInt(str3));
      paramRecWireInfo.setDateToPost(DBUtil.getPayday(paramRecWireInfo.getFiID(), str3));
      paramRecWireInfo.setWireType("Recurring");
      updateWire(paramFFSConnectionHolder, paramRecWireInfo, false);
      if (paramRecWireInfo.getStatusCode() != 0)
      {
        paramRecWireInfo.setSrvrTid(str2);
        break;
      }
    }
    paramRecWireInfo.setVersion(i);
    paramRecWireInfo.setSrvrTid(str2);
    FFSDebug.log(str1, "End", 6);
    return paramRecWireInfo;
  }
  
  public static BPWHist getWireHistory(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("Wire.getWireHistory : start", 6);
    paramBPWHist = a(paramBPWHist, "getWireHistory");
    FFSDebug.log("Wire.getWireHistory : commonFieldsValid done", 6);
    FFSDebug.log("Wire.getWireHistory : wireHist:" + paramBPWHist, 6);
    if (paramBPWHist.getStatusCode() != 0) {
      return paramBPWHist;
    }
    FFSDebug.log("Wire.getWireHistory : calling getWireHistoryCommon", 6);
    return jdMethod_if(paramFFSConnectionHolder, paramBPWHist, null, paramBoolean);
  }
  
  public static BPWHist getWireHistoryByCustomer(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("Wire.getWireHistoryByCustomer : start", 6);
    paramBPWHist = a(paramBPWHist, "getWireHistoryByCustomer");
    if (paramBPWHist.getStatusCode() != 0) {
      return paramBPWHist;
    }
    if (((paramBPWHist.getHistId() == null) || (paramBPWHist.getHistId().trim().length() == 0)) && ((paramBPWHist.getCustId() == null) || (paramBPWHist.getCustId().trim().length() == 0)))
    {
      FFSDebug.log("Wire.getWireHistoryByCustomer failed  Null CustId Passed", 0);
      paramBPWHist.setStatusCode(16010);
      paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "BPWHist", "CustId" }, "WIRE_MESSAGE"));
      return paramBPWHist;
    }
    return jdMethod_if(paramFFSConnectionHolder, paramBPWHist, null, paramBoolean);
  }
  
  private static BPWHist a(BPWHist paramBPWHist, String paramString)
    throws BPWException
  {
    if (paramBPWHist == null)
    {
      paramBPWHist = new BPWHist();
      paramBPWHist.setStatusCode(16000);
      paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(16000, new String[] { "BPWHist " }, "WIRE_MESSAGE"));
      FFSDebug.log("Wire." + paramString + " : " + "Null BPWHist passed", 0);
      return paramBPWHist;
    }
    String[] arrayOfString = paramBPWHist.getStatusList();
    if ((arrayOfString != null) && (arrayOfString.length > 0))
    {
      int i = 0;
      int j = 0;
      for (int k = 0; k < arrayOfString.length; k++) {
        if (("TEMPLATE".equalsIgnoreCase(arrayOfString[k])) || ("RECTEMPLATE".equalsIgnoreCase(arrayOfString[k])))
        {
          i = 1;
          if (j != 0)
          {
            paramBPWHist.setStatusCode(19340);
            paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(19340, null, "WIRE_MESSAGE"));
            FFSDebug.log("Wire.", paramString, " : ", "Status list cannot contain status TEMPLATE or RECTEMPLATE with any other status", 0);
            return paramBPWHist;
          }
        }
        else
        {
          j = 1;
          if (i != 0)
          {
            paramBPWHist.setStatusCode(19340);
            paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(19340, null, "WIRE_MESSAGE"));
            FFSDebug.log("Wire.", paramString, " : ", "Status list cannot contain status TEMPLATE or RECTEMPLATE with any other status", 0);
            return paramBPWHist;
          }
        }
      }
    }
    String str1 = paramBPWHist.getTransType();
    if ((str1 != null) && (str1.trim().length() != 0) && (!str1.equalsIgnoreCase("SINGLE")) && (!str1.equalsIgnoreCase("TEMPLATE")) && (!str1.equalsIgnoreCase("RECURRING")) && (!str1.equalsIgnoreCase("RECTEMPLATE")))
    {
      paramBPWHist.setStatusCode(19390);
      paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(19390, new String[] { str1 }, "WIRE_MESSAGE"));
      FFSDebug.log("Wire.", paramString, " : ", "Invalid trans type in BPWHist:", str1, 0);
      return paramBPWHist;
    }
    jdMethod_if(paramBPWHist);
    String str2;
    if (!BPWUtil.checkFrontEndDateFormat(paramBPWHist.getStartDate()))
    {
      paramBPWHist.setStatusCode(17205);
      str2 = paramString + ":" + BPWLocaleUtil.getMessage(17205, null, "WIRE_MESSAGE") + ": " + paramBPWHist.getStartDate();
      paramBPWHist.setStatusMsg(str2);
      FFSDebug.log(str2, 0);
      return paramBPWHist;
    }
    if (!BPWUtil.checkFrontEndDateFormat(paramBPWHist.getEndDate()))
    {
      paramBPWHist.setStatusCode(17206);
      str2 = paramString + ":" + BPWLocaleUtil.getMessage(17206, null, "WIRE_MESSAGE") + ": " + paramBPWHist.getStartDate();
      paramBPWHist.setStatusMsg(str2);
      FFSDebug.log(str2, 0);
      return paramBPWHist;
    }
    FFSDebug.log("Wire." + paramString + " : StartDate: ", paramBPWHist.getStartDate(), ", EndDate: ", paramBPWHist.getEndDate(), 6);
    if (Integer.parseInt(paramBPWHist.getStartDate()) > Integer.parseInt(paramBPWHist.getEndDate()))
    {
      paramBPWHist.setStatusCode(17080);
      paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(17080, null, "WIRE_MESSAGE"));
      FFSDebug.log("Wire." + paramString + " : " + " Start Date is later than End Date", 0);
      return paramBPWHist;
    }
    paramBPWHist.setStatusCode(0);
    paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    return paramBPWHist;
  }
  
  private static void jdMethod_if(BPWHist paramBPWHist)
  {
    if ((paramBPWHist.getStartDate() == null) || (paramBPWHist.getStartDate().trim().length() == 0)) {
      paramBPWHist.setStartDate(String.valueOf(FFSUtil.getInstanceDate(-30)));
    } else if (paramBPWHist.getStartDate().trim().length() == 8) {
      paramBPWHist.setStartDate(paramBPWHist.getStartDate().trim() + "00");
    }
    if ((paramBPWHist.getEndDate() == null) || (paramBPWHist.getEndDate().trim().length() == 0)) {
      paramBPWHist.setEndDate(String.valueOf(FFSUtil.getInstanceDate(30)));
    } else if (paramBPWHist.getEndDate().trim().length() == 8) {
      paramBPWHist.setEndDate(paramBPWHist.getEndDate().trim() + "00");
    }
    FFSDebug.log("Wire.fixHistDates: StartDate=", paramBPWHist.getStartDate(), " EndDate=", paramBPWHist.getEndDate(), 6);
  }
  
  private static BPWHist jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, String paramString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("Wire.getWireHistoryCommon : start", 6);
    FFSDebug.log("Wire.getWireHistoryCommon : Param Values", 6);
    FFSDebug.log("StartDate : " + paramBPWHist.getStartDate() + "\nEndDate : " + paramBPWHist.getEndDate(), 6);
    StringBuffer localStringBuffer = new StringBuffer();
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    String[] arrayOfString3 = null;
    String str9 = null;
    ArrayList localArrayList = new ArrayList();
    if (paramString != null) {
      localStringBuffer.append(paramString);
    }
    arrayOfString1 = paramBPWHist.getSubmittedBy();
    int i;
    if ((arrayOfString1 != null) && (arrayOfString1.length > 0))
    {
      FFSDebug.log("Wire.getWireHistory : submittedBy.length:" + arrayOfString1.length, 6);
      if (arrayOfString1.length == 1)
      {
        localStringBuffer.append(" AND a.SubmittedBy = ? ");
        localArrayList.add(arrayOfString1[0]);
      }
      else
      {
        localStringBuffer.append(" AND a.SubmittedBy IN (?");
        localArrayList.add(arrayOfString1[0]);
        for (i = 1; i < arrayOfString1.length; i++)
        {
          localStringBuffer.append(", ?");
          localArrayList.add(arrayOfString1[i]);
        }
        localStringBuffer.append(")");
      }
    }
    arrayOfString2 = paramBPWHist.getUserId();
    if ((arrayOfString2 != null) && (arrayOfString2.length > 0))
    {
      FFSDebug.log("Wire.getWireHistory : userIds.length:" + arrayOfString2.length, 6);
      if (arrayOfString2.length == 1)
      {
        localStringBuffer.append(" AND a.UserId = ? ");
        localArrayList.add(arrayOfString2[0]);
      }
      else
      {
        localStringBuffer.append(" AND a.UserId IN (?");
        localArrayList.add(arrayOfString2[0]);
        for (i = 1; i < arrayOfString2.length; i++)
        {
          localStringBuffer.append(", ?");
          localArrayList.add(arrayOfString2[i]);
        }
        localStringBuffer.append(")");
      }
    }
    str1 = paramBPWHist.getFiId();
    if ((str1 != null) && (str1.trim().length() != 0))
    {
      localStringBuffer.append(" AND FIID = ? ");
      localArrayList.add(str1);
    }
    str2 = paramBPWHist.getTransType();
    if ((str2 != null) && (str2.trim().length() != 0)) {
      if (str2.equalsIgnoreCase("TEMPLATE"))
      {
        localStringBuffer.append(" AND ( (WireType = 'TEMPLATE') OR (WireType = 'RECTEMPLATE') ) ");
      }
      else
      {
        localStringBuffer.append(" AND WireType = ? ");
        localArrayList.add(str2);
      }
    }
    str3 = paramBPWHist.getTransScope();
    if ((str3 != null) && (str3.trim().length() != 0))
    {
      localStringBuffer.append(" AND WireScope = ? ");
      localArrayList.add(str3);
    }
    str4 = paramBPWHist.getTransSource();
    if ((str4 != null) && (str4.trim().length() != 0))
    {
      localStringBuffer.append(" AND WireSource = ? ");
      localArrayList.add(str4);
    }
    arrayOfString3 = paramBPWHist.getDest();
    if ((arrayOfString3 != null) && (arrayOfString3.length > 0))
    {
      FFSDebug.log("Wire.getWireHistory : dests.length:" + arrayOfString3.length, 6);
      if (arrayOfString3.length == 1)
      {
        localStringBuffer.append(" AND a.WireDest = ? ");
        localArrayList.add(arrayOfString3[0]);
      }
      else
      {
        localStringBuffer.append(" AND a.WireDest IN (?");
        localArrayList.add(arrayOfString3[0]);
        for (i = 1; i < arrayOfString3.length; i++)
        {
          localStringBuffer.append(", ?");
          localArrayList.add(arrayOfString3[i]);
        }
        localStringBuffer.append(")");
      }
    }
    str5 = paramBPWHist.getTemplateId();
    if ((str5 != null) && (str5.trim().length() != 0))
    {
      localStringBuffer.append(" AND TemplateId = ? ");
      localArrayList.add(str5);
    }
    str6 = paramBPWHist.getMinAmount();
    if ((str6 != null) && (str6.trim().length() != 0))
    {
      localStringBuffer.append(" AND cast (Amount as decimal) >= ? ");
      localArrayList.add(new BigDecimal(str6));
    }
    str7 = paramBPWHist.getMaxAmount();
    if ((str7 != null) && (str7.trim().length() != 0))
    {
      localStringBuffer.append(" AND cast (Amount as decimal) <= ? ");
      localArrayList.add(new BigDecimal(str7));
    }
    str8 = paramBPWHist.getCustId();
    if ((str8 != null) && (str8.trim().length() != 0))
    {
      localStringBuffer.append(" AND a.CustomerID = ? ");
      localArrayList.add(paramBPWHist.getCustId());
    }
    str9 = paramBPWHist.getSelectionCriteria();
    if ((str9 == null) || (!str9.trim().equalsIgnoreCase("all"))) {
      localStringBuffer.append(" AND BatchId IS NULL ");
    }
    if ((paramBPWHist.getHistId() == null) || (paramBPWHist.getHistId().trim().length() == 0))
    {
      if (paramBoolean) {
        jdMethod_new(paramFFSConnectionHolder, paramBPWHist, localStringBuffer.toString(), localArrayList);
      } else {
        jdMethod_for(paramFFSConnectionHolder, paramBPWHist, localStringBuffer.toString(), localArrayList);
      }
      paramBPWHist.setCursorId("0");
    }
    jdMethod_if(paramFFSConnectionHolder, paramBPWHist, paramBoolean);
    return paramBPWHist;
  }
  
  private static boolean jdMethod_if(String[] paramArrayOfString, String paramString)
  {
    FFSDebug.log("Wire.containsInArray: Start", 6);
    FFSDebug.log("Wire.containsInArray: aStringList:" + paramArrayOfString, 6);
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
      return false;
    }
    FFSDebug.log("Wire.containsStatus: reqStatus:", paramString, 6);
    if (paramString == null) {
      return false;
    }
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if (paramString.equalsIgnoreCase(paramArrayOfString[i]))
      {
        FFSDebug.log("Wire.containsInArray: reqString FOUND", 6);
        return true;
      }
    }
    FFSDebug.log("Wire.containsInArray: reqString not FOUND", 6);
    FFSDebug.log("Wire.containsInArray: End", 6);
    return false;
  }
  
  private static void jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    FFSDebug.log("Wire.makeHistory : start", 6);
    FFSResultSet localFFSResultSet1 = null;
    FFSResultSet localFFSResultSet2 = null;
    try
    {
      int i = 0;
      str1 = null;
      int j = 0;
      String str2 = null;
      Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramBPWHist.getStartDate()));
      Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramBPWHist.getEndDate()));
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int k = localPropertyConfig.getBatchSize();
      StringBuffer localStringBuffer = new StringBuffer("SELECT SrvrTID, DateToPost, RecSrvrTID FROM BPW_WireInfo a  WHERE DateToPost >= ? AND DateToPost <= ?");
      String[] arrayOfString = paramBPWHist.getStatusList();
      if ((arrayOfString != null) && (arrayOfString.length > 0))
      {
        localStringBuffer.append(" AND Status IN (?");
        paramArrayList.add(0, arrayOfString[0]);
        for (int m = 1; m < arrayOfString.length; m++)
        {
          localStringBuffer.append(", ?");
          paramArrayList.add(m, arrayOfString[m]);
        }
        localStringBuffer.append(")");
      }
      else
      {
        localStringBuffer.append(" AND Status <> ? ");
        paramArrayList.add(0, "CANCELEDON");
        localStringBuffer.append(" AND WireType <> ? ");
        paramArrayList.add(1, "TEMPLATE");
      }
      if (paramString != null) {
        localStringBuffer.append(paramString);
      }
      localStringBuffer.append(" ORDER BY SrvrTID");
      str2 = DBUtil.getNextIndexString("HistID");
      paramBPWHist.setHistId(str2);
      paramArrayList.add(0, localInteger2.toString());
      paramArrayList.add(0, localInteger1.toString());
      Object[] arrayOfObject1 = paramArrayList.toArray();
      for (int n = 0; n < paramArrayList.size(); n++) {
        FFSDebug.log("Wire.makeHistory : Sql Param:" + n + " :" + String.valueOf(paramArrayList.get(n)), 6);
      }
      localFFSResultSet1 = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject1);
      while (localFFSResultSet1.getNextRow())
      {
        localFFSResultSet1.getColumnString("RecSrvrTID");
        j = Integer.parseInt(localFFSResultSet1.getColumnString("DateToPost"));
        i++;
        str1 = DBUtil.getCursor(j, i);
        TempHist.create(paramFFSConnectionHolder, str2, str1, localFFSResultSet1.getColumnString("SrvrTID"), "WIRE", j, null);
        if (i % k == 0) {
          paramFFSConnectionHolder.conn.commit();
        }
      }
      paramFFSConnectionHolder.conn.commit();
      paramArrayList.remove(0);
      paramArrayList.remove(0);
      if ((arrayOfString != null) && (arrayOfString.length > 0)) {
        n = 0;
      }
      while (n < arrayOfString.length)
      {
        paramArrayList.remove(0);
        n++;
        continue;
        paramArrayList.remove(0);
        paramArrayList.remove(0);
      }
      n = 1;
      boolean bool1;
      if ((arrayOfString != null) && (arrayOfString.length > 0)) {
        bool1 = jdMethod_if(arrayOfString, "WILLPROCESSON");
      }
      if (bool1)
      {
        i1 = DBUtil.getCurrentStartDate();
        int i2 = BPWUtil.getDateDBFormat(paramBPWHist.getEndDate());
        int i3 = (i2 - i1) / 10000;
        if (i3 >= 0)
        {
          localStringBuffer = new StringBuffer("SELECT a.RecSrvrTID, a.Frequency, a.StartDate, a.EndDate,  a.InstanceCount, a.FIID  FROM BPW_RecWireInfo a  WHERE a.StartDate <= ? AND a.EndDate >= ?");
          localStringBuffer.append(" AND Status ='WILLPROCESSON' ");
          if (paramString != null) {
            localStringBuffer.append(paramString);
          }
          localStringBuffer.append(" ORDER BY RecSrvrTID");
          paramArrayList.add(0, localInteger1.toString());
          paramArrayList.add(0, localInteger2.toString());
          Object[] arrayOfObject2 = paramArrayList.toArray();
          localFFSResultSet2 = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject2);
          Object localObject1 = null;
          Method localMethod = null;
          Class localClass = null;
          Object localObject2 = null;
          String str3 = null;
          String str4 = null;
          while (localFFSResultSet2.getNextRow())
          {
            localObject2 = null;
            Object localObject4;
            if (localObject1 == null)
            {
              localClass = Class.forName("com.ffusion.ffs.scheduling.db.ScheduleInfo");
              localObject3 = localClass.getDeclaredMethods();
              for (int i4 = 0; i4 < localObject3.length; i4++) {
                if (localObject3[i4].getName().equalsIgnoreCase("getPendingDatesByStartAndEndDate"))
                {
                  localObject1 = localObject3[i4];
                  break;
                }
              }
              localObject4 = new Class[] { String.class, String.class, String.class, FFSConnectionHolder.class };
              localMethod = localClass.getMethod("getScheduleInfo", (Class[])localObject4);
            }
            str3 = localFFSResultSet2.getColumnString("RecSrvrTID");
            str4 = localFFSResultSet2.getColumnString("FIID");
            Object localObject3 = { str4, "RECWIRETRN", str3, paramFFSConnectionHolder };
            localObject2 = localMethod.invoke(null, (Object[])localObject3);
            if (localObject2 != null)
            {
              localObject4 = null;
              Object[] arrayOfObject3 = { localObject2, localInteger1, localInteger2 };
              localObject4 = (String[])localObject1.invoke(localObject2, arrayOfObject3);
              for (int i5 = 0; i5 < localObject4.length; i5++)
              {
                j = Integer.parseInt(localObject4[i5].substring(0, 10));
                i++;
                str1 = DBUtil.getCursor(j, i);
                TempHist.create(paramFFSConnectionHolder, str2, str1, str3, "RECWIRE", j, null);
                if (i % k == 0) {
                  paramFFSConnectionHolder.conn.commit();
                }
              }
            }
          }
        }
        paramArrayList.remove(0);
        paramArrayList.remove(0);
      }
      paramBPWHist.setTotalTrans(i);
      paramFFSConnectionHolder.conn.commit();
      int i1 = 0;
      boolean bool2;
      if ((arrayOfString != null) && (arrayOfString.length > 0)) {
        bool2 = jdMethod_if(arrayOfString, "RECTEMPLATE");
      }
      if (bool2) {
        jdMethod_try(paramFFSConnectionHolder, paramBPWHist, paramString, paramArrayList);
      }
      FFSDebug.log("Wire.makeHistory : end", 6);
    }
    catch (Throwable localThrowable)
    {
      String str1 = "Wire.makeHistory failed : ";
      FFSDebug.log(str1 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str1);
    }
    finally
    {
      if (localFFSResultSet1 != null) {
        try
        {
          localFFSResultSet1.close();
          localFFSResultSet1 = null;
        }
        catch (Exception localException1)
        {
          FFSDebug.log("Wire.makeHistory failed to close ResultSet " + FFSDebug.stackTrace(localException1), 0);
        }
      }
      if (localFFSResultSet2 != null) {
        try
        {
          localFFSResultSet2.close();
          localFFSResultSet2 = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log("Wire.makeHistory failed to close ResultSet " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
  }
  
  private static void jdMethod_new(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    FFSDebug.log("Wire.makeRecWireHistory : start", 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = 0;
      int j = 0;
      String str2 = null;
      int k = 0;
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int m = localPropertyConfig.getBatchSize();
      StringBuffer localStringBuffer = new StringBuffer("SELECT RecSrvrTID, StartDate FROM BPW_RecWireInfo a  WHERE StartDate >= ? AND StartDate <= ?");
      String[] arrayOfString = paramBPWHist.getStatusList();
      if ((arrayOfString != null) && (arrayOfString.length > 0))
      {
        localStringBuffer.append(" AND Status IN (?");
        paramArrayList.add(0, arrayOfString[0]);
        for (int n = 1; n < arrayOfString.length; n++)
        {
          localStringBuffer.append(", ?");
          paramArrayList.add(n, arrayOfString[n]);
        }
        localStringBuffer.append(")");
      }
      else
      {
        localStringBuffer.append(" AND Status <> ? ");
        paramArrayList.add(0, "CANCELEDON");
        localStringBuffer.append(" AND WireType <> ? ");
        paramArrayList.add(1, "RECTEMPLATE");
      }
      if ((paramString != null) && (paramString.length() > 0)) {
        localStringBuffer.append(paramString);
      }
      localStringBuffer.append(" ORDER BY RecSrvrTID");
      paramBPWHist.setHistId(DBUtil.getNextIndexString("HistID"));
      Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramBPWHist.getStartDate()));
      Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramBPWHist.getEndDate()));
      paramArrayList.add(0, localInteger2.toString());
      paramArrayList.add(0, localInteger1.toString());
      Object[] arrayOfObject = paramArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        k = Integer.parseInt(localFFSResultSet.getColumnString("StartDate"));
        i++;
        j++;
        str2 = DBUtil.getCursor(k, j);
        TempHist.create(paramFFSConnectionHolder, paramBPWHist.getHistId(), str2, localFFSResultSet.getColumnString("RecSrvrTID"), "RECWIRE", k, null);
        if (i % m == 0) {
          paramFFSConnectionHolder.conn.commit();
        }
      }
      paramBPWHist.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      FFSDebug.log("Wire.makeRecWireHistory : end", 6);
    }
    catch (Throwable localThrowable)
    {
      String str1 = "Wire.makeRecWireHistory failed : ";
      FFSDebug.log(str1 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("Wire.makeRecWireHistory failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  private static void jdMethod_try(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    FFSDebug.log("Wire.makeRecTemplateHistory : start", 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = 0;
      int j = (int)paramBPWHist.getTotalTrans();
      long l = paramBPWHist.getPageSize();
      String str2 = null;
      int k = 0;
      StringBuffer localStringBuffer = new StringBuffer("SELECT RecSrvrTID, StartDate FROM BPW_RecWireInfo a  WHERE StartDate >= ? AND StartDate <= ?");
      if ((paramString != null) && (paramString.length() > 0)) {
        localStringBuffer.append(paramString);
      }
      localStringBuffer.append(" AND WireType = ? ");
      paramArrayList.add("RECTEMPLATE");
      localStringBuffer.append(" ORDER BY RecSrvrTID");
      Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramBPWHist.getStartDate()));
      Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramBPWHist.getEndDate()));
      paramArrayList.add(0, localInteger2.toString());
      paramArrayList.add(0, localInteger1.toString());
      Object[] arrayOfObject = paramArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        k = Integer.parseInt(localFFSResultSet.getColumnString("StartDate"));
        i++;
        j++;
        str2 = DBUtil.getCursor(k, j);
        TempHist.create(paramFFSConnectionHolder, paramBPWHist.getHistId(), str2, localFFSResultSet.getColumnString("RecSrvrTID"), "RECWIRE", k, null);
        if ((l != 0L) && (i == l))
        {
          i = 0;
          paramFFSConnectionHolder.conn.commit();
        }
      }
      paramBPWHist.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      FFSDebug.log("Wire.makeRecTemplateHistory : end", 6);
    }
    catch (Throwable localThrowable)
    {
      String str1 = "Wire.makeRecTemplateHistory failed : ";
      FFSDebug.log(str1 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log("Wire.makeRecTemplateHistory failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  private static void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("Wire.getHistory : start", 6);
    FFSDebug.log("Wire.getHistory : Parameter values \nHistId : " + paramBPWHist.getHistId() + "\nCursorId : " + paramBPWHist.getCursorId() + "\nPage Size : " + paramBPWHist.getPageSize(), 6);
    ArrayList localArrayList = new ArrayList();
    Object localObject = null;
    try
    {
      TempHist[] arrayOfTempHist = TempHist.get(paramFFSConnectionHolder, paramBPWHist.getHistId(), paramBPWHist.getCursorId(), paramBPWHist.getPageSize());
      int i = arrayOfTempHist.length;
      for (int j = 0; j < i; j++)
      {
        FFSDebug.log("Wire.getHistory : RecordType=", arrayOfTempHist[j].RecordType, 6);
        if (arrayOfTempHist[j].RecordType.equals("WIRE"))
        {
          localObject = jdMethod_case(paramFFSConnectionHolder, arrayOfTempHist[j].RecordExtId, false);
          if (((WireInfo)localObject).getWireType().equals("Recurring")) {
            ((WireInfo)localObject).setDateToPost(BPWUtil.getDateBeanFormat(arrayOfTempHist[j].DueDate));
          }
          ((WireInfo)localObject).setRecordCursor(Long.parseLong(arrayOfTempHist[j].CursorId));
        }
        else if (arrayOfTempHist[j].RecordType.equals("RECWIRE"))
        {
          localObject = new RecWireInfo();
          ((WireInfo)localObject).setSrvrTid(arrayOfTempHist[j].RecordExtId);
          localObject = getWireInfo(paramFFSConnectionHolder, (WireInfo)localObject, true, false);
          ((WireInfo)localObject).setRecSrvrTid(arrayOfTempHist[j].RecordExtId);
          ((WireInfo)localObject).setDateDue(BPWUtil.getDateBeanFormat(arrayOfTempHist[j].DueDate));
          try
          {
            ((WireInfo)localObject).setDateToPost(DBUtil.getPayday(((WireInfo)localObject).getFiID(), ((WireInfo)localObject).getDateDue()));
          }
          catch (Throwable localThrowable2)
          {
            String str2 = "Exception in Wire.getHistory:";
            String str3 = FFSDebug.stackTrace(localThrowable2);
            FFSDebug.log(str2 + str3, 0);
            paramBPWHist.setStatusCode(19200);
            paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(19200, null, "WIRE_MESSAGE"));
            return;
          }
          ((WireInfo)localObject).setRecordCursor(Long.parseLong(arrayOfTempHist[j].CursorId));
          if ((!paramBoolean) && (((WireInfo)localObject).getPrcStatus().equals("WILLPROCESSON"))) {
            ((WireInfo)localObject).setPrcStatus("CREATED");
          }
        }
        else
        {
          localObject = new WireInfo();
          ((WireInfo)localObject).setStatusCode(23190);
          ((WireInfo)localObject).setStatusMsg(BPWLocaleUtil.getMessage(23190, null, "WIRE_MESSAGE"));
          FFSDebug.log("Wire.getHistory : Invalid History Record Type : RecordExtId = " + arrayOfTempHist[j].RecordExtId + "RecordType = " + arrayOfTempHist[j].RecordType, 0);
        }
        if (localObject != null) {
          localArrayList.add(localObject);
        }
      }
      if (paramBoolean) {
        paramBPWHist.setTrans((RecWireInfo[])localArrayList.toArray(new RecWireInfo[0]));
      } else {
        paramBPWHist.setTrans((WireInfo[])localArrayList.toArray(new WireInfo[0]));
      }
      if (paramBPWHist.getTotalTrans() == 0L) {
        paramBPWHist.setTotalTrans(TempHist.getCount(paramFFSConnectionHolder, paramBPWHist.getHistId()));
      }
      if (localArrayList.size() == 0)
      {
        FFSDebug.log("Wire.getHistory : No history record found", 6);
        paramBPWHist.setStatusCode(16020);
        paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WireInstruction History " }, "WIRE_MESSAGE"));
      }
      else
      {
        paramBPWHist.setCursorId(arrayOfTempHist[(arrayOfTempHist.length - 1)].CursorId);
        paramBPWHist.setStatusCode(0);
        paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable1)
    {
      String str1 = "Wire.getHistory : failed:  " + localThrowable1.getMessage();
      FFSDebug.log(str1 + FFSDebug.stackTrace(localThrowable1), 0);
      throw new FFSException(localThrowable1, str1);
    }
  }
  
  private static WireInfo jdMethod_case(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Wire.getWireInstructionForHistory ";
    FFSDebug.log(str1 + " start", 6);
    Object localObject = null;
    localObject = new RecWireInfo();
    ((WireInfo)localObject).setSrvrTid(paramString);
    localObject = getWireInfo(paramFFSConnectionHolder, (WireInfo)localObject, paramBoolean, false);
    if (((WireInfo)localObject).getStatusCode() != 0) {
      return localObject;
    }
    if (((WireInfo)localObject).getWireType().equals("Recurring"))
    {
      String str2 = ((WireInfo)localObject).getSrvrTid();
      String str3 = ((WireInfo)localObject).getRecSrvrTid();
      ((WireInfo)localObject).setSrvrTid(str3);
      String str4 = ((WireInfo)localObject).getPrcStatus();
      localObject = getWireInfo(paramFFSConnectionHolder, (WireInfo)localObject, true, false);
      ((WireInfo)localObject).setRecSrvrTid(str3);
      ((WireInfo)localObject).setSrvrTid(str2);
      ((WireInfo)localObject).setPrcStatus(str4);
      FFSDebug.log(str1 + " srvrTId=" + ((WireInfo)localObject).getSrvrTid(), 6);
      ((WireInfo)localObject).setWireType("Recurring");
    }
    return localObject;
  }
  
  public static String getStatus(String paramString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("Wire.getStatus: start, srvrTID=" + paramString, 6);
    String str1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str2;
    String str3;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Exception localException)
    {
      str2 = "*** Wire.getStatus failed: ";
      str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localException, str2);
    }
    try
    {
      str1 = getStatus(localFFSConnectionHolder, paramString, paramBoolean);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "*** Wire.getStatus failed: ";
      str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("Wire.getStatus: done, status=" + str1, 6);
    return str1;
  }
  
  public static String getStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("Wire.getStatus: start, srvrTID=" + paramString, 6);
    String str1 = null;
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString };
    if (paramBoolean) {
      str1 = "SELECT Status FROM BPW_RecWireInfo WHERE RecSrvrTID = ?";
    } else {
      str1 = "SELECT Status FROM BPW_WireInfo WHERE SrvrTID = ?";
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        str2 = localFFSResultSet.getColumnString(1);
        FFSDebug.log("Wire.getStatus: retrieved wire status.", 6);
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = "*** Wire.getStatus failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3 + str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str5 = "*** Wire.getStatus  failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5 + str6, 0);
      }
    }
    FFSDebug.log("Wire.getStatus: done, status=" + str2, 6);
    return str2;
  }
  
  public static WireInfo updateStatus(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Wire.updateStatus: ";
    FFSDebug.log(str1, "start", 6);
    FFSDebug.log(str1, "update status to " + paramString, 6);
    String str3 = null;
    String str4 = paramWireInfo.getSrvrTid();
    String str5 = null;
    if (paramFFSConnectionHolder == null)
    {
      paramWireInfo.setStatusCode(16000);
      localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1, (String)localObject1, 0);
      return paramWireInfo;
    }
    Object localObject1 = { paramString, str4 };
    String str2;
    if (paramBoolean)
    {
      str2 = "UPDATE BPW_RecWireInfo SET Status=? WHERE RecSrvrTID=?";
      str3 = "Update BPW_RecWireInfo2 Set Version=Version+1  WHERE RecSrvrTID = ?";
      str5 = "RecSrvrTID";
    }
    else
    {
      str2 = "UPDATE BPW_WireInfo SET Status=? WHERE SrvrTID=?";
      str3 = "Update BPW_WireInfo2 Set Version=Version+1  WHERE SrvrTID = ?";
      str5 = "SrvrTID";
    }
    try
    {
      int i = 0;
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject1);
      localObject2 = jdMethod_byte(paramWireInfo, false);
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, (Object[])localObject2, paramWireInfo, str5, paramWireInfo.getSrvrTid());
      if ((i == 0) && (paramWireInfo.getStatusCode() != 0)) {
        return paramWireInfo;
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject2 = "*** Wire.updateStatus failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2 + str6, 0);
      throw new FFSException(localThrowable, (String)localObject2);
    }
    paramWireInfo.setPrcStatus(paramString);
    paramWireInfo.setStatusCode(0);
    paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    FFSDebug.log(str1, "done", 6);
    return paramWireInfo;
  }
  
  public static WireInfo updateStatusProcessedBy(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    String str1 = "Wire.updateStatusProcessedBy: ";
    FFSDebug.log(str1, "start", 6);
    String str2 = null;
    String str3 = paramWireInfo.getSrvrTid();
    String str4 = paramWireInfo.getPrcStatus();
    String str5 = paramWireInfo.getProcessedBy();
    if (paramFFSConnectionHolder == null)
    {
      paramWireInfo.setStatusCode(16000);
      localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1, (String)localObject1, 0);
      return paramWireInfo;
    }
    Object localObject1 = { str4, str5, str3 };
    str2 = "UPDATE BPW_WireInfo SET Status = ?, ProcessedBy = ? WHERE SrvrTID=?";
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Update BPW_WireInfo2 Set Version=Version+1  WHERE SrvrTID = ?");
      localObject2 = jdMethod_byte(paramWireInfo, false);
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject2, paramWireInfo, "SrvrTID", paramWireInfo.getSrvrTid());
      if ((i == 0) && (paramWireInfo.getStatusCode() != 0)) {
        return paramWireInfo;
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject2 = "*** Wire.updateStatusProcessedBy failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2 + str6, 0);
      throw new FFSException(localThrowable, (String)localObject2);
    }
    paramWireInfo.setPrcStatus(str4);
    paramWireInfo.setStatusCode(0);
    paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    FFSDebug.log(str1, "done", 6);
    return paramWireInfo;
  }
  
  public static WireInfo updateReleaseWire(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    String str1 = "Wire.updateReleaseWire: ";
    FFSDebug.log(str1, "start", 6);
    String str2 = null;
    String str3 = paramWireInfo.getSrvrTid();
    String str4 = paramWireInfo.getPrcStatus();
    String str5 = paramWireInfo.getProcessedBy();
    String str6 = paramWireInfo.getMemo();
    if (paramFFSConnectionHolder == null)
    {
      paramWireInfo.setStatusCode(16000);
      localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      paramWireInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1, (String)localObject1, 0);
      return paramWireInfo;
    }
    Object localObject1 = { str4, str5, str6, str3 };
    str2 = "UPDATE BPW_WireInfo SET Status = ?, ProcessedBy = ?, Memo = ?  WHERE SrvrTID=?";
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Update BPW_WireInfo2 Set Version=Version+1  WHERE SrvrTID = ?");
      localObject2 = jdMethod_byte(paramWireInfo, false);
      int j = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject2, paramWireInfo, "SrvrTID", paramWireInfo.getSrvrTid());
      if ((j == 0) && (paramWireInfo.getStatusCode() != 0)) {
        return paramWireInfo;
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject2 = "*** Wire.updateStatusProcessedBy failed: ";
      String str7 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2 + str7, 0);
      throw new FFSException(localThrowable, (String)localObject2);
    }
    if (paramWireInfo.getExtInfo() != null)
    {
      int i = BPWExtraInfo.processXtraInfo(paramFFSConnectionHolder, paramWireInfo.getFiID(), str3, "WIRE", paramWireInfo.getExtInfo());
      if (i != 0)
      {
        paramWireInfo.setStatusCode(19270);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19270, null, "WIRE_MESSAGE"));
      }
      else
      {
        paramWireInfo.setStatusCode(0);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
    }
    else
    {
      paramWireInfo.setStatusCode(0);
      paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    }
    FFSDebug.log(str1, "status code: " + paramWireInfo.getStatusCode(), 6);
    FFSDebug.log(str1, "done", 6);
    return paramWireInfo;
  }
  
  public static WireInfo[] getAuditWireTransfer(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "Wire.getAuditWireTransfer";
    FFSDebug.log(str1, ": start, srvrTID=", paramString, 6);
    WireInfo localWireInfo1 = null;
    WireInfo localWireInfo2 = null;
    ArrayList localArrayList = null;
    FFSResultSet localFFSResultSet = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    String str9 = null;
    String str10 = null;
    String str11 = null;
    String str12 = null;
    String str13 = null;
    String str14 = null;
    Object localObject1;
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = "FFSConnectionHolder object  is null";
      FFSDebug.log(str1, (String)localObject1, 0);
      return null;
    }
    str2 = "SELECT TRAN_ID,USER_ID,AGENT_ID,AGENT_TYPE,DESCRIPTION,LOG_DATE, TRAN_TYPE,BUSINESS_ID,AMOUNT,CURRENCY_CODE,SRVR_TID,STATE,TO_ACCT_ID, TO_ACCT_RTGNUM,FROM_ACCT_ID,FROM_ACCT_RTGNUM,MODULE  FROM AUDIT_LOG WHERE TRAN_ID = ?  ORDER BY LOG_DATE ASC";
    try
    {
      localWireInfo2 = new WireInfo();
      localWireInfo2.setSrvrTid(paramString);
      localWireInfo2 = getWireInfo(paramFFSConnectionHolder, localWireInfo2, false, false);
      localWireInfo2.setWirePayeeInfo(null);
      localObject1 = new Object[] { localWireInfo2.getExtId() };
      FFSDebug.log(str1, "ExtId:", localWireInfo2.getExtId(), 6);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      localArrayList = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        localWireInfo1 = (WireInfo)localWireInfo2.clone();
        str3 = localFFSResultSet.getColumnString("USER_ID");
        str4 = localFFSResultSet.getColumnString("AGENT_ID");
        str5 = localFFSResultSet.getColumnString("AGENT_TYPE");
        str6 = localFFSResultSet.getColumnString("TRAN_ID");
        str7 = localFFSResultSet.getColumnString("BUSINESS_ID");
        str8 = localFFSResultSet.getColumnString("AMOUNT");
        str9 = localFFSResultSet.getColumnString("CURRENCY_CODE");
        str10 = localFFSResultSet.getColumnString("STATE");
        str11 = localFFSResultSet.getColumnString("FROM_ACCT_ID");
        str12 = localFFSResultSet.getColumnString("FROM_ACCT_RTGNUM");
        str13 = localFFSResultSet.getColumnString("LOG_DATE");
        str14 = localFFSResultSet.getColumnString("SRVR_TID");
        localWireInfo1.setSubmittedBy(str3);
        localWireInfo1.setAgentId(str4);
        localWireInfo1.setAgentType(str5);
        localWireInfo1.setExtId(str6);
        localWireInfo1.setCustomerID(str7);
        localWireInfo1.setAmount(str8);
        localWireInfo1.setOrigCurrency(str9);
        localWireInfo1.setPrcStatus(str10);
        localWireInfo1.setFromAcctId(str11);
        localWireInfo1.setFromBankId(str12);
        localWireInfo1.setLogDate(str13);
        localWireInfo1.setSrvrTid(str14);
        localWireInfo1.setStatusCode(0);
        localWireInfo1.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        localArrayList.add(localWireInfo1);
      }
    }
    catch (Throwable localThrowable)
    {
      String str15 = str1 + " failed: ";
      String str16 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str15, str16, 0);
      throw new FFSException(localThrowable, str15);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str17 = str1 + " failed: ";
        String str18 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str17, str18, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    if (localArrayList.size() == 0) {
      return null;
    }
    return (WireInfo[])localArrayList.toArray(new WireInfo[0]);
  }
  
  public static WireInfo[] getAuditWireTransferByExtId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "Wire.getAuditWireTransferByExtId";
    FFSDebug.log(str1, ": start, ExtId=", paramString, 6);
    WireInfo localWireInfo1 = null;
    WireInfo localWireInfo2 = null;
    ArrayList localArrayList = null;
    FFSResultSet localFFSResultSet = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    String str9 = null;
    String str10 = null;
    String str11 = null;
    String str12 = null;
    String str13 = null;
    String str14 = null;
    Object[] arrayOfObject = { paramString };
    if (paramFFSConnectionHolder == null)
    {
      String str15 = "FFSConnectionHolder object  is null";
      FFSDebug.log(str1, str15, 0);
      return null;
    }
    str2 = "SELECT TRAN_ID,USER_ID,AGENT_ID,AGENT_TYPE,DESCRIPTION,LOG_DATE, TRAN_TYPE,BUSINESS_ID,AMOUNT,CURRENCY_CODE,SRVR_TID,STATE,TO_ACCT_ID, TO_ACCT_RTGNUM,FROM_ACCT_ID,FROM_ACCT_RTGNUM,MODULE  FROM AUDIT_LOG WHERE TRAN_ID = ?  ORDER BY LOG_DATE ASC";
    try
    {
      localWireInfo2 = new WireInfo();
      localWireInfo2.setExtId(paramString);
      localWireInfo2 = getWireTransfer(paramFFSConnectionHolder, localWireInfo2, false, false, false);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      localArrayList = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        localWireInfo1 = (WireInfo)localWireInfo2.clone();
        str3 = localFFSResultSet.getColumnString("USER_ID");
        str4 = localFFSResultSet.getColumnString("AGENT_ID");
        str5 = localFFSResultSet.getColumnString("AGENT_TYPE");
        str6 = localFFSResultSet.getColumnString("TRAN_ID");
        str7 = localFFSResultSet.getColumnString("BUSINESS_ID");
        str8 = localFFSResultSet.getColumnString("AMOUNT");
        str9 = localFFSResultSet.getColumnString("CURRENCY_CODE");
        str10 = localFFSResultSet.getColumnString("STATE");
        str11 = localFFSResultSet.getColumnString("FROM_ACCT_ID");
        str12 = localFFSResultSet.getColumnString("FROM_ACCT_RTGNUM");
        str13 = localFFSResultSet.getColumnString("LOG_DATE");
        str14 = localFFSResultSet.getColumnString("SRVR_TID");
        localWireInfo1.setSubmittedBy(str3);
        localWireInfo1.setAgentId(str4);
        localWireInfo1.setAgentType(str5);
        localWireInfo1.setExtId(str6);
        localWireInfo1.setCustomerID(str7);
        localWireInfo1.setAmount(str8);
        localWireInfo1.setOrigCurrency(str9);
        localWireInfo1.setPrcStatus(str10);
        localWireInfo1.setFromAcctId(str11);
        localWireInfo1.setFromBankId(str12);
        localWireInfo1.setLogDate(str13);
        localWireInfo1.setSrvrTid(str14);
        localWireInfo1.setStatusCode(0);
        localWireInfo1.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        localArrayList.add(localWireInfo1);
      }
    }
    catch (Throwable localThrowable)
    {
      String str16 = str1 + " failed: ";
      String str17 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str16, str17, 0);
      throw new FFSException(localThrowable, str16);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str18 = str1 + " failed: ";
        String str19 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str18, str19, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    if (localArrayList.size() == 0) {
      return null;
    }
    return (WireInfo[])localArrayList.toArray(new WireInfo[0]);
  }
  
  public static WireReleaseInfo getWireReleaseCount(FFSConnectionHolder paramFFSConnectionHolder, WireReleaseInfo paramWireReleaseInfo)
    throws FFSException
  {
    String str1 = "Wire.getWireReleaseCount";
    FFSDebug.log(str1, ": start", 6);
    FFSDebug.log(str1 + paramWireReleaseInfo, 6);
    ArrayList localArrayList = null;
    FFSResultSet localFFSResultSet = null;
    StringBuffer localStringBuffer = null;
    Object[] arrayOfObject = null;
    int i = 0;
    String[] arrayOfString = null;
    a(paramWireReleaseInfo);
    if (paramWireReleaseInfo.getStatusCode() != 0) {
      return paramWireReleaseInfo;
    }
    localArrayList = new ArrayList();
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT COUNT(*) from BPW_WireInfo  WHERE Status = 'CREATED'  AND CustomerID = ?  AND FIID = ?  AND DateToPost >= ?  AND DateToPost <= ? ");
    localArrayList.add(paramWireReleaseInfo.getCustomerId());
    localArrayList.add(paramWireReleaseInfo.getFIId());
    localArrayList.add(paramWireReleaseInfo.getStartDate());
    localArrayList.add(paramWireReleaseInfo.getEndDate());
    if ((paramWireReleaseInfo.getMinAmount() != null) && (paramWireReleaseInfo.getMinAmount().trim().length() > 0))
    {
      localStringBuffer.append(" AND cast (Amount as decimal) >= ? ");
      localArrayList.add(new BigDecimal(paramWireReleaseInfo.getMinAmount()));
    }
    if ((paramWireReleaseInfo.getMaxAmount() != null) && (paramWireReleaseInfo.getMaxAmount().trim().length() > 0))
    {
      localStringBuffer.append(" AND cast (Amount as decimal) <= ? ");
      localArrayList.add(new BigDecimal(paramWireReleaseInfo.getMaxAmount()));
    }
    arrayOfString = paramWireReleaseInfo.getSubmittedBy();
    if ((arrayOfString != null) && (arrayOfString.length > 0))
    {
      localStringBuffer.append(" AND SubmittedBy IN (");
      for (int j = 0; j < arrayOfString.length; j++)
      {
        if (j > 0) {
          localStringBuffer.append(", ");
        }
        localStringBuffer.append("?");
        localArrayList.add(arrayOfString[j]);
      }
      localStringBuffer.append(") ");
    }
    arrayOfObject = (Object[])localArrayList.toArray(new Object[0]);
    FFSDebug.log(str1, "B4 query:" + paramWireReleaseInfo, 6);
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
      paramWireReleaseInfo.setWireCount(i);
      paramWireReleaseInfo.setStatusCode(0);
      paramWireReleaseInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str4 = str1 + " failed: ";
        String str5 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str4, str5, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return paramWireReleaseInfo;
  }
  
  private static WireReleaseInfo a(WireReleaseInfo paramWireReleaseInfo)
    throws BPWException
  {
    String str1 = "Wire.validateDates:";
    FFSDebug.log(str1, " start ", 6);
    if (paramWireReleaseInfo.getStartDate() == null) {
      paramWireReleaseInfo.setStartDate("" + DBUtil.getCurrentStartDate());
    } else if (paramWireReleaseInfo.getStartDate().trim().length() == 8) {
      paramWireReleaseInfo.setStartDate(paramWireReleaseInfo.getStartDate().trim() + "00");
    }
    if (paramWireReleaseInfo.getEndDate() == null) {
      paramWireReleaseInfo.setEndDate("" + DBUtil.getCurrentStartDate());
    } else if (paramWireReleaseInfo.getEndDate().trim().length() == 8) {
      paramWireReleaseInfo.setEndDate(paramWireReleaseInfo.getEndDate().trim() + "00");
    }
    String str2;
    if (!BPWUtil.checkFrontEndDateFormat(paramWireReleaseInfo.getStartDate()))
    {
      paramWireReleaseInfo.setStatusCode(19410);
      str2 = BPWLocaleUtil.getMessage(19410, new String[] { str1, "" + paramWireReleaseInfo.getStartDate() }, "WIRE_MESSAGE");
      paramWireReleaseInfo.setStatusMsg(str2);
      FFSDebug.log(str2, 0);
      return paramWireReleaseInfo;
    }
    if (!BPWUtil.checkFrontEndDateFormat(paramWireReleaseInfo.getEndDate()))
    {
      paramWireReleaseInfo.setStatusCode(19420);
      str2 = BPWLocaleUtil.getMessage(19420, new String[] { str1, "" + paramWireReleaseInfo.getEndDate() }, "WIRE_MESSAGE");
      paramWireReleaseInfo.setStatusMsg(str2);
      FFSDebug.log(str2, 0);
      return paramWireReleaseInfo;
    }
    FFSDebug.log("Wire." + str1 + " : StartDate: ", paramWireReleaseInfo.getStartDate(), ", EndDate: ", paramWireReleaseInfo.getEndDate(), 6);
    if (Integer.parseInt(paramWireReleaseInfo.getStartDate()) > Integer.parseInt(paramWireReleaseInfo.getEndDate()))
    {
      paramWireReleaseInfo.setStatusCode(17080);
      paramWireReleaseInfo.setStatusMsg(BPWLocaleUtil.getMessage(17080, null, "WIRE_MESSAGE"));
      FFSDebug.log("Wire." + str1 + " : " + " Start Date is later than End Date", 0);
      return paramWireReleaseInfo;
    }
    paramWireReleaseInfo.setStatusCode(0);
    paramWireReleaseInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    return paramWireReleaseInfo;
  }
  
  public static void updateFromApprovalStatus(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    FFSDebug.log("Wire.updateFromApprovalStatus: start, wireInfo: ", paramWireInfo.toString(), 6);
    if (paramWireInfo == null)
    {
      str1 = "Wire.updateFromApprovalStatus: Failed, Invalid wireInfo: " + paramWireInfo;
      FFSDebug.log(str1, 6);
      throw new FFSException(str1);
    }
    String str1 = paramWireInfo.getSrvrTid();
    if ((str1 == null) || (str1.length() == 0))
    {
      str2 = "Wire.updateFromApprovalStatus: Failed, Invalid transaction ID: " + str1;
      FFSDebug.log(str2, 6);
      throw new FFSException(str2);
    }
    String str2 = paramWireInfo.getPrcStatus();
    if ((str2 == null) || (str2.length() == 0))
    {
      String str3 = "Wire.updateFromApprovalStatus: Failed, Status is missing.";
      FFSDebug.log(str3, 6);
      throw new FFSException(str3);
    }
    try
    {
      a(paramFFSConnectionHolder, paramWireInfo, false, false);
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** Wire.updateFromApprovalStatus failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4 + str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    FFSDebug.log("Wire.updateFromApprovalStatus: done", 6);
  }
  
  public static void updateFromBackendStatus(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    FFSDebug.log("Wire.updateFromBackendStatus: start, wireInfo: ", paramWireInfo.toString(), 6);
    if (paramWireInfo == null)
    {
      str1 = "Wire.updateFromBackendStatus: Failed, Invalid wireInfo: " + paramWireInfo;
      FFSDebug.log(str1, 6);
      throw new FFSException(str1);
    }
    String str1 = paramWireInfo.getSrvrTid();
    if ((str1 == null) || (str1.length() == 0))
    {
      str2 = "Wire.updateFromBackendStatus: Failed, Invalid transaction ID: " + str1;
      FFSDebug.log(str2, 6);
      throw new FFSException(str2);
    }
    String str2 = paramWireInfo.getPrcStatus();
    if ((str2 == null) || (str2.length() == 0))
    {
      String str3 = "Wire.updateFromBackendStatus: Failed, Status is missing.";
      FFSDebug.log(str3, 6);
      throw new FFSException(str3);
    }
    try
    {
      a(paramFFSConnectionHolder, paramWireInfo, true, true);
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** Wire.updateFromBackendStatus failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4 + str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    FFSDebug.log("Wire.updateFromBackendStatus: done", 6);
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    FFSDebug.log("Wire.updateStatusAndOther: start, wireInfo: ", paramWireInfo.toString(), 6);
    StringBuffer localStringBuffer = null;
    ArrayList localArrayList = null;
    int i = 0;
    Object[] arrayOfObject = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    String str9 = null;
    String str10 = null;
    String str11 = null;
    String str12 = null;
    String str13 = null;
    String str14 = null;
    String str15 = null;
    String str16 = null;
    String str17 = null;
    String str18;
    if (paramWireInfo == null)
    {
      str18 = "Wire.updateStatusAndOther: Failed, Invalid wireInfo: " + paramWireInfo;
      FFSDebug.log(str18, 6);
      throw new FFSException(str18);
    }
    str1 = paramWireInfo.getSrvrTid();
    if ((str1 == null) || (str1.length() == 0))
    {
      str18 = "Wire.updateStatusAndOther: Failed, Invalid transaction ID: " + str1;
      FFSDebug.log(str18, 6);
      throw new FFSException(str18);
    }
    str2 = paramWireInfo.getPrcStatus();
    if ((str2 == null) || (str2.length() == 0))
    {
      str18 = "Wire.updateStatusAndOther: Failed, Status is missing.";
      FFSDebug.log(str18, 6);
      throw new FFSException(str18);
    }
    localArrayList = new ArrayList();
    localArrayList.add(str2);
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("UPDATE BPW_WireInfo SET Status=? ");
    if (paramBoolean1)
    {
      str18 = paramWireInfo.getDatePosted();
      if ((str18 == null) || (str18.trim().length() == 0)) {
        str18 = FFSUtil.getDateString("yyyyMMddHHmmss");
      }
      paramWireInfo.setDatePosted(str18);
      localStringBuffer.append(", ").append("DatePosted").append(" = ? ");
      localArrayList.add(str18);
    }
    str3 = paramWireInfo.getAmount();
    if ((str3 != null) && (str3.trim().length() > 0))
    {
      localStringBuffer.append(", ").append("Amount").append(" = ? ");
      localArrayList.add(str3);
    }
    str4 = paramWireInfo.getAmtCurrency();
    if ((str4 != null) && (str4.trim().length() > 0))
    {
      localStringBuffer.append(", ").append("AmtCurrency").append(" = ? ");
      localArrayList.add(str4);
    }
    str5 = paramWireInfo.getDestCurrency();
    if ((str5 != null) && (str5.trim().length() > 0))
    {
      localStringBuffer.append(", ").append("DestCurrency").append(" = ? ");
      localArrayList.add(str5);
    }
    str6 = paramWireInfo.getWireFee();
    if ((str6 != null) && (str6.trim().length() > 0))
    {
      localStringBuffer.append(", ").append("XferFee").append(" = ? ");
      localArrayList.add(str6);
    }
    str7 = paramWireInfo.getPayInstruct();
    if ((str7 != null) && (str7.trim().length() > 0))
    {
      localStringBuffer.append(", ").append("PayInstruct").append(" = ? ");
      localArrayList.add(str7);
    }
    str8 = paramWireInfo.getExchangeRate();
    if (StringUtils.trimToNull(str8) != null)
    {
      FFSDebug.log("exchangeRate: ", str8, 6);
      if (FFSUtil.isNegative(str8))
      {
        str18 = "Wire.updateStatusAndOther: Failed, Invalid Exchange Rate: " + str8;
        FFSDebug.log(str18, 6);
        throw new FFSException(str18);
      }
      if (FFSUtil.isZero(str8))
      {
        str18 = "Wire.updateStatusAndOther: Invalid Exchange Rate: " + str8 + ". Exchange rate will be ignored";
        FFSDebug.log(str18, 6);
      }
      else
      {
        localStringBuffer.append(", ").append("ExchangeRate").append(" = ? ");
        localArrayList.add(str8);
      }
    }
    if (paramBoolean2)
    {
      str9 = paramWireInfo.getConfirmNum();
      if ((str9 != null) && (str9.trim().length() > 0))
      {
        localStringBuffer.append(", ").append("ConfirmNum").append(" = ? ");
        localArrayList.add(str9);
      }
      str10 = paramWireInfo.getConfirmNum2();
      if ((str10 != null) && (str10.trim().length() > 0))
      {
        localStringBuffer.append(", ").append("ConfirmNum2").append(" = ? ");
        localArrayList.add(str10);
      }
      str11 = paramWireInfo.getConfirmMsg();
      if ((str11 != null) && (str11.trim().length() > 0))
      {
        localStringBuffer.append(", ").append("ConfirmMsg").append(" = ? ");
        localArrayList.add(str11);
      }
    }
    str12 = paramWireInfo.getContractNumber();
    if ((str12 != null) && (str12.trim().length() > 0))
    {
      localStringBuffer.append(", ").append("ContractNumber").append(" = ? ");
      localArrayList.add(str12);
    }
    str13 = paramWireInfo.getBanktoBankInfo();
    if ((str13 != null) && (str13.trim().length() > 0))
    {
      localStringBuffer.append(", ").append("BanktoBankInfo").append(" = ? ");
      localArrayList.add(str13);
    }
    str14 = paramWireInfo.getAgentId();
    if ((str14 != null) && (str14.trim().length() > 0))
    {
      localStringBuffer.append(", ").append("AgentId").append(" = ? ");
      localArrayList.add(str14);
    }
    str15 = paramWireInfo.getAgentType();
    if ((str15 != null) && (str15.trim().length() > 0))
    {
      if (Integer.parseInt(str15) <= 0)
      {
        str18 = "Wire.updateStatusAndOther: Failed, Invalid Agent Type: " + str15;
        FFSDebug.log(str18, 6);
        throw new FFSException(str18);
      }
      localStringBuffer.append(", ").append("AgentType").append(" = ? ");
      localArrayList.add(str15);
    }
    str16 = paramWireInfo.getAgentName();
    if ((str16 != null) && (str16.trim().length() > 0))
    {
      localStringBuffer.append(", ").append("AgentName").append(" = ? ");
      localArrayList.add(str16);
    }
    str17 = paramWireInfo.getProcessedBy();
    if ((str17 != null) && (str17.trim().length() > 0))
    {
      localStringBuffer.append(", ").append("ProcessedBy").append(" = ? ");
      localArrayList.add(str17);
    }
    localArrayList.add(str1);
    localStringBuffer.append(" WHERE SrvrTID=?");
    i = localArrayList.size();
    arrayOfObject = new Object[i];
    arrayOfObject = (Object[])localArrayList.toArray(new Object[0]);
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
    }
    catch (Throwable localThrowable)
    {
      String str19 = "*** Wire.updateStatusAndOther failed: ";
      String str20 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str19 + str20, 0);
      throw new FFSException(localThrowable, str19);
    }
    paramWireInfo.setStatusCode(0);
    paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    FFSDebug.log("Wire.updateStatusAndOther: done", 6);
  }
  
  public static WireBatchInfo addWireTransferBatch(FFSConnectionHolder paramFFSConnectionHolder, WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    String str1 = "Wire.addWireTransferBatch";
    String str2 = null;
    String str3 = null;
    Object[] arrayOfObject = null;
    FFSDebug.log(str1 + ": Starts: ", 6);
    Object localObject;
    if (paramFFSConnectionHolder == null)
    {
      paramWireBatchInfo.setStatusCode(16000);
      localObject = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      paramWireBatchInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      return paramWireBatchInfo;
    }
    try
    {
      localObject = new WireInfo();
      ((WireInfo)localObject).setFiID(paramWireBatchInfo.getFIId());
      ((WireInfo)localObject).setCustomerID(paramWireBatchInfo.getCustomerId());
      jdMethod_else(paramFFSConnectionHolder, (WireInfo)localObject);
      if (((WireInfo)localObject).getStatusCode() != 0)
      {
        String str4 = "failed " + ((WireInfo)localObject).getStatusMsg();
        FFSDebug.log(str1, str4, 0);
        paramWireBatchInfo.setStatusCode(((WireInfo)localObject).getStatusCode());
        paramWireBatchInfo.setStatusMsg(((WireInfo)localObject).getStatusMsg());
        return paramWireBatchInfo;
      }
      localObject = null;
      str2 = DBUtil.getNewTransId("SrvrTID", 18);
      str3 = "INSERT INTO BPW_WireBatch ( BatchId, FIId, CustomerId,  UserId, BatchName, BatchType, BatchCategory, TotalAmount, WireCount,  SubmittedBy, SubmitDate, BatchStatus, Memo, LogId, BatchDest,  ExtId,DateDue, DateToPost,PayInstruct,RecWireBatchId, MathRule,  ContractNumber, ExchangeRate, AmtCurrency, DestCurrency,  SettlementDate, TotalCreditAmount, TotalDebitAmount, AgentId,  AgentName, AgentType,OrigAmount,OrigCurrency) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      paramWireBatchInfo.setBatchId(str2);
      paramWireBatchInfo.setPrcStatus("CREATED");
      arrayOfObject = jdMethod_new(paramWireBatchInfo);
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      if (null != paramWireBatchInfo.getExtInfo())
      {
        int i = BPWExtraInfo.processXtraInfo(paramFFSConnectionHolder, paramWireBatchInfo.getFIId(), paramWireBatchInfo.getBatchId(), "WIREBATCHTRN", paramWireBatchInfo.getExtInfo());
        if (i != 0)
        {
          paramWireBatchInfo.setStatusCode(19270);
          paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19270, null, "WIRE_MESSAGE"));
        }
        else
        {
          paramWireBatchInfo.setStatusCode(0);
          paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        }
      }
      else
      {
        paramWireBatchInfo.setStatusCode(0);
        paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
    }
    catch (Throwable localThrowable)
    {
      String str5 = str1 + " failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5, str6, 0);
      throw new FFSException(localThrowable, str5);
    }
    return paramWireBatchInfo;
  }
  
  private static Object[] jdMethod_new(WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    Object[] arrayOfObject = { paramWireBatchInfo.getBatchId(), paramWireBatchInfo.getFIId(), paramWireBatchInfo.getCustomerId(), paramWireBatchInfo.getUserId(), paramWireBatchInfo.getBatchName(), paramWireBatchInfo.getBatchType(), paramWireBatchInfo.getBatchCategory(), paramWireBatchInfo.getTotalAmount(), paramWireBatchInfo.getWireCount(), paramWireBatchInfo.getSubmittedBy(), DBUtil.getCurrentLogDate(), paramWireBatchInfo.getPrcStatus(), paramWireBatchInfo.getMemo(), paramWireBatchInfo.getLogId(), paramWireBatchInfo.getBatchDest(), paramWireBatchInfo.getExtId(), Integer.toString(BPWUtil.getDateDBFormat(paramWireBatchInfo.getDateDue())), Integer.toString(BPWUtil.getDateDBFormat(paramWireBatchInfo.getDateToPost())), paramWireBatchInfo.getPayInstruct(), paramWireBatchInfo.getRecWireBatchId(), paramWireBatchInfo.getMathRule(), paramWireBatchInfo.getContractNumber(), paramWireBatchInfo.getExchangeRate(), paramWireBatchInfo.getAmtCurrency(), paramWireBatchInfo.getDestCurrency(), paramWireBatchInfo.getSettlementDate(), paramWireBatchInfo.getTotalCreditAmount(), paramWireBatchInfo.getTotalDebitAmount(), paramWireBatchInfo.getAgentId(), paramWireBatchInfo.getAgentName(), paramWireBatchInfo.getAgentType(), paramWireBatchInfo.getOrigAmount(), paramWireBatchInfo.getOrigCurrency() };
    return arrayOfObject;
  }
  
  public static WireBatchInfo updateWireTransferBatch(FFSConnectionHolder paramFFSConnectionHolder, WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    String str1 = "Wire.updateWireTransferBatch";
    String str2 = null;
    Object[] arrayOfObject = null;
    FFSDebug.log(str1 + ": Starts.", 6);
    Object localObject;
    if (paramFFSConnectionHolder == null)
    {
      paramWireBatchInfo.setStatusCode(16000);
      localObject = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      paramWireBatchInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      return paramWireBatchInfo;
    }
    try
    {
      localObject = new WireInfo();
      ((WireInfo)localObject).setFiID(paramWireBatchInfo.getFIId());
      ((WireInfo)localObject).setCustomerID(paramWireBatchInfo.getCustomerId());
      jdMethod_else(paramFFSConnectionHolder, (WireInfo)localObject);
      if (((WireInfo)localObject).getStatusCode() != 0)
      {
        String str3 = "failed " + ((WireInfo)localObject).getStatusMsg();
        FFSDebug.log(str1, str3, 0);
        paramWireBatchInfo.setStatusCode(((WireInfo)localObject).getStatusCode());
        paramWireBatchInfo.setStatusMsg(((WireInfo)localObject).getStatusMsg());
        return paramWireBatchInfo;
      }
      localObject = null;
      str2 = "UPDATE BPW_WireBatch set BatchName=?,  BatchType=?, BatchCategory=?, TotalAmount=?,  WireCount=?, Memo=?, LogId=?, BatchDest=?, ExtId=?,  DateDue=?, DateToPost=?,PayInstruct=?,RecWireBatchId=?, MathRule=?,  ContractNumber=?, ExchangeRate=?, AmtCurrency=?, DestCurrency=?,  SettlementDate=?, TotalCreditAmount=?, TotalDebitAmount=?, AgentId=?,  AgentName=?, AgentType=?,OrigAmount=?,OrigCurrency=?, SubmittedBy = ?, Version=Version+1  WHERE BatchId=?  AND FIId=?  AND CustomerId=? ";
      arrayOfObject = jdMethod_for(paramWireBatchInfo);
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject, paramWireBatchInfo, "BatchId", paramWireBatchInfo.getBatchId());
      if ((i == 0) && (paramWireBatchInfo.getStatusCode() != 0)) {
        return paramWireBatchInfo;
      }
      if (i == 0)
      {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(" record not found");
        localStringBuffer.append(" for batchId:").append(paramWireBatchInfo.getBatchId());
        localStringBuffer.append(", FIID:").append(paramWireBatchInfo.getFIId());
        localStringBuffer.append(" and CustomerID:");
        localStringBuffer.append(paramWireBatchInfo.getCustomerId());
        paramWireBatchInfo.setStatusCode(16020);
        paramWireBatchInfo.setStatusMsg(localStringBuffer.toString());
        paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(((WireInfo)localObject).getStatusCode(), new String[] { " BatchId: " + paramWireBatchInfo.getBatchId() + " FIID: " + paramWireBatchInfo.getFIId() + " CustomerID:" + paramWireBatchInfo.getCustomerId() }, "WIRE_MESSAGE"));
        return paramWireBatchInfo;
      }
      if (null != paramWireBatchInfo.getExtInfo())
      {
        int j = BPWExtraInfo.processXtraInfo(paramFFSConnectionHolder, paramWireBatchInfo.getFIId(), paramWireBatchInfo.getBatchId(), "WIREBATCHTRN", paramWireBatchInfo.getExtInfo());
        if (j != 0)
        {
          paramWireBatchInfo.setStatusCode(19270);
          paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19270, null, "WIRE_MESSAGE"));
        }
        else
        {
          paramWireBatchInfo.setStatusCode(0);
          paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        }
      }
      else
      {
        paramWireBatchInfo.setStatusCode(0);
        paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    return paramWireBatchInfo;
  }
  
  private static Object[] jdMethod_for(WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    Object[] arrayOfObject = { paramWireBatchInfo.getBatchName(), paramWireBatchInfo.getBatchType(), paramWireBatchInfo.getBatchCategory(), paramWireBatchInfo.getTotalAmount(), paramWireBatchInfo.getWireCount(), paramWireBatchInfo.getMemo(), paramWireBatchInfo.getLogId(), paramWireBatchInfo.getBatchDest(), paramWireBatchInfo.getExtId(), Integer.toString(BPWUtil.getDateDBFormat(paramWireBatchInfo.getDateDue())), Integer.toString(BPWUtil.getDateDBFormat(paramWireBatchInfo.getDateToPost())), paramWireBatchInfo.getPayInstruct(), paramWireBatchInfo.getRecWireBatchId(), paramWireBatchInfo.getMathRule(), paramWireBatchInfo.getContractNumber(), paramWireBatchInfo.getExchangeRate(), paramWireBatchInfo.getAmtCurrency(), paramWireBatchInfo.getDestCurrency(), paramWireBatchInfo.getSettlementDate(), paramWireBatchInfo.getTotalCreditAmount(), paramWireBatchInfo.getTotalDebitAmount(), paramWireBatchInfo.getAgentId(), paramWireBatchInfo.getAgentName(), paramWireBatchInfo.getAgentType(), paramWireBatchInfo.getOrigAmount(), paramWireBatchInfo.getOrigCurrency(), paramWireBatchInfo.getSubmittedBy(), paramWireBatchInfo.getBatchId(), paramWireBatchInfo.getFIId(), paramWireBatchInfo.getCustomerId() };
    return arrayOfObject;
  }
  
  public static WireBatchInfo updateWireBatchCtrlAmts(FFSConnectionHolder paramFFSConnectionHolder, WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    String str1 = "Wire.updateWireBatchTotalAmt";
    String str2 = null;
    FFSDebug.log(str1 + ": Starts: ", 6);
    if (paramFFSConnectionHolder == null)
    {
      paramWireBatchInfo.setStatusCode(16000);
      localObject = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      paramWireBatchInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      return paramWireBatchInfo;
    }
    Object localObject = { paramWireBatchInfo.getTotalAmount(), paramWireBatchInfo.getTotalDebitAmount(), paramWireBatchInfo.getTotalCreditAmount(), paramWireBatchInfo.getWireCount(), paramWireBatchInfo.getBatchId() };
    try
    {
      str2 = "UPDATE BPW_WireBatch set TotalAmount = ?,  TotalDebitAmount = ?, TotalCreditAmount = ?, WireCount = ?, Version=Version+1  WHERE BatchId = ? ";
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject, paramWireBatchInfo, "BatchId", paramWireBatchInfo.getBatchId());
      if ((i == 0) && (paramWireBatchInfo.getStatusCode() != 0)) {
        return paramWireBatchInfo;
      }
      paramWireBatchInfo.setStatusCode(0);
      paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    return paramWireBatchInfo;
  }
  
  public static WireBatchInfo cancelWireTransferBatch(FFSConnectionHolder paramFFSConnectionHolder, WireBatchInfo paramWireBatchInfo)
    throws FFSException
  {
    String str1 = "Wire.cancelWireTransferBatch";
    String str2 = null;
    String str3 = null;
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    FFSDebug.log(str1 + ": Starts.", 6);
    Object localObject1;
    if (paramFFSConnectionHolder == null)
    {
      paramWireBatchInfo.setStatusCode(16000);
      localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      paramWireBatchInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1, (String)localObject1, 0);
      return paramWireBatchInfo;
    }
    try
    {
      str2 = paramWireBatchInfo.getBatchId();
      localObject1 = new Object[] { str2, "CREATED", "RELEASEPENDING", "CANCELEDON", "APPROVAL_PENDING", "APPROVAL_REJECTED", "APPROVAL_NOT_ALLOWED" };
      FFSDebug.log(str1, "BatchId:", str2, "Status:", "CREATED", ",", "CANCELEDON", 6);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT SrvrTID FROM BPW_WireInfo WHERE BatchId = ? AND Status NOT IN (?, ?, ?, ?, ?, ?, ?) ", (Object[])localObject1);
      if (localFFSResultSet.getNextRow())
      {
        localObject2 = "Failed some wires are already processed, " + BPWLocaleUtil.getMessage(19430, null, "WIRE_MESSAGE") + " batchId: " + str2;
        FFSDebug.log(str1, (String)localObject2, 0);
        paramWireBatchInfo.setStatusCode(19430);
        paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19430, null, "WIRE_MESSAGE"));
        return paramWireBatchInfo;
      }
      localObject2 = new Object[] { "CANCELEDON", str2 };
      FFSDebug.log(str1 + ": cancelling batch:", str2, 6);
      FFSDebug.log(str1 + ": sql:", "UPDATE BPW_WireBatch set BatchStatus = ?, Version=Version+1  WHERE BatchId = ? ", 6);
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_WireBatch set BatchStatus = ?, Version=Version+1  WHERE BatchId = ? ", (Object[])localObject2, paramWireBatchInfo, "BatchId", paramWireBatchInfo.getBatchId());
      if ((i == 0) && (paramWireBatchInfo.getStatusCode() != 0)) {
        return paramWireBatchInfo;
      }
      if (i == 0)
      {
        localObject3 = new StringBuffer();
        ((StringBuffer)localObject3).append("No Wire batch record is found in DB with BatchId: ");
        ((StringBuffer)localObject3).append(str2);
        FFSDebug.log("***", str1, " failed ", ((StringBuffer)localObject3).toString(), 0);
        paramWireBatchInfo.setStatusCode(16020);
        paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { str2 }, "WIRE_MESSAGE"));
        return paramWireBatchInfo;
      }
      paramWireBatchInfo.setPrcStatus("CANCELEDON");
      str3 = "UPDATE BPW_WireInfo SET Status=? WHERE BatchId = ?  AND Status IN ('CREATED', 'RELEASEPENDING', 'WILLPROCESSON') ";
      localObject3 = new Object[] { "CANCELEDON", str2 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, (Object[])localObject3);
      FFSDebug.log(str1, " Number of Wires cancelled:" + i, 6);
      WireInfo[] arrayOfWireInfo = paramWireBatchInfo.getWires();
      if (arrayOfWireInfo != null) {
        for (int j = 0; j < arrayOfWireInfo.length; j++) {
          arrayOfWireInfo[j].setPrcStatus("CANCELEDON");
        }
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject2 = str1 + " failed: ";
      Object localObject3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2, (String)localObject3, 0);
      throw new FFSException(localThrowable, (String)localObject2);
    }
    paramWireBatchInfo.setStatusCode(0);
    paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    FFSDebug.log(str1, "done", 6);
    return paramWireBatchInfo;
  }
  
  public static boolean isWireBatchDeleteable(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "Wire.isWireBatchDeleteable";
    FFSDebug.log(str1, ": start.", 6);
    FFSResultSet localFFSResultSet = null;
    boolean bool = false;
    Object[] arrayOfObject = { paramString, "CREATED", "RELEASEPENDING", "CANCELEDON", "APPROVAL_PENDING", "APPROVAL_REJECTED", "APPROVAL_NOT_ALLOWED", "WILLPROCESSON" };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT SrvrTID FROM BPW_WireInfo WHERE BatchId = ? AND Status NOT IN (?, ?, ?, ?, ?, ?, ?) ", arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        bool = false;
      } else {
        bool = true;
      }
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str4 = str1 + " failed: ";
        String str5 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str4, str5, 0);
      }
    }
    FFSDebug.log(str1, ": isWireBatchDeletable=", String.valueOf(bool), 6);
    FFSDebug.log(str1, ": done", 6);
    return bool;
  }
  
  public static boolean isWirebatchModifyable(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "Wire.isWirebatchModifyable";
    FFSDebug.log(str1, ": start.", 6);
    FFSResultSet localFFSResultSet = null;
    boolean bool = false;
    Object[] arrayOfObject = { paramString, "CREATED", "RELEASEPENDING", "APPROVAL_PENDING", "APPROVAL_REJECTED", "APPROVAL_NOT_ALLOWED", "WILLPROCESSON" };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT SrvrTID FROM BPW_WireInfo WHERE BatchId = ? AND Status IN (?, ?, ?, ?, ?, ?) ", arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        bool = true;
      } else {
        bool = false;
      }
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str4 = str1 + " failed: ";
        String str5 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str4, str5, 0);
      }
    }
    FFSDebug.log(str1, ": isWireBatchModifyable= ", String.valueOf(bool), 6);
    FFSDebug.log(str1, ": done", 6);
    return bool;
  }
  
  public static WireBatchInfo updateBatchStatus(FFSConnectionHolder paramFFSConnectionHolder, WireBatchInfo paramWireBatchInfo, String paramString)
    throws FFSException
  {
    String str1 = "Wire.updateBatchStatus: ";
    FFSDebug.log(str1, "start", 6);
    String str2 = null;
    String str3 = paramWireBatchInfo.getBatchId();
    if (paramFFSConnectionHolder == null)
    {
      paramWireBatchInfo.setStatusCode(16000);
      localObject = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      paramWireBatchInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      return paramWireBatchInfo;
    }
    Object localObject = { paramString, str3 };
    str2 = "UPDATE BPW_WireBatch set BatchStatus = ?, Version=Version+1  WHERE BatchId = ? ";
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject, paramWireBatchInfo, "BatchId", paramWireBatchInfo.getBatchId());
      if ((i == 0) && (paramWireBatchInfo.getStatusCode() != 0)) {
        return paramWireBatchInfo;
      }
      if (i == 0)
      {
        paramWireBatchInfo.setStatusCode(16020);
        str4 = "failed, 16020 BatchId:" + str3;
        paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { str3 }, "WIRE_MESSAGE"));
        FFSDebug.log(str1, str4, 0);
        return paramWireBatchInfo;
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** Wire.updateBatchStatus failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4 + str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    paramWireBatchInfo.setPrcStatus(paramString);
    paramWireBatchInfo.setStatusCode(0);
    paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    FFSDebug.log(str1, "done", 6);
    return paramWireBatchInfo;
  }
  
  public static String getWireBatchStatus(String paramString)
    throws FFSException
  {
    FFSDebug.log("Wire.getWireBatchStatus: start, batchId=" + paramString, 6);
    String str1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str2;
    String str3;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Exception localException)
    {
      str2 = "*** Wire.getWireBatchStatus failed: ";
      str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localException, str2);
    }
    try
    {
      str1 = r(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "*** Wire.getWireBatchStatus failed: ";
      str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("Wire.getWireBatchStatus: done, status=" + str1, 6);
    return str1;
  }
  
  private static String r(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("Wire.getWireBatchStatus: start, batchId=" + paramString, 6);
    String str1 = null;
    String str2 = null;
    String str3 = null;
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString };
    str1 = "SELECT DISTINCT PrcStatus  FROM BPW_WireInfo WHERE BatchId = ? ";
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str3 = localFFSResultSet.getColumnString(1);
      }
      while (localFFSResultSet.getNextRow())
      {
        str2 = localFFSResultSet.getColumnString(1);
        str3 = calculateBatchStatus(str2, str3);
        if (str3.equalsIgnoreCase("MIXED")) {
          break;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** Wire.getWireBatchStatus failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4 + str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str6 = "*** Wire.getWireBatchStatus  failed: ";
        String str7 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str6 + str7, 0);
      }
    }
    FFSDebug.log("Wire.getWireBatchStatus: done, batchStatus=" + str3, 6);
    return str3;
  }
  
  private static String jdMethod_int(WireBatchInfo paramWireBatchInfo)
  {
    String str1 = null;
    String str2 = null;
    WireInfo[] arrayOfWireInfo = paramWireBatchInfo.getWires();
    if ((arrayOfWireInfo != null) && (arrayOfWireInfo.length >= 1))
    {
      str1 = arrayOfWireInfo[0].getPrcStatus();
      for (int i = 1; i < arrayOfWireInfo.length; i++)
      {
        str2 = arrayOfWireInfo[i].getPrcStatus();
        str1 = calculateBatchStatus(str2, str1);
        if (str1.equalsIgnoreCase("MIXED")) {
          break;
        }
      }
    }
    if (str1 != null) {
      paramWireBatchInfo.setPrcStatus(str1);
    }
    return str1;
  }
  
  public static String calculateBatchStatus(String paramString1, String paramString2)
  {
    if (!paramString1.equalsIgnoreCase(paramString2)) {
      paramString2 = "MIXED";
    }
    return paramString2;
  }
  
  public static WireBatchInfo[] getWireTransferBatch(FFSConnectionHolder paramFFSConnectionHolder, WireBatchInfo paramWireBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    String str = "Wire.getWireTransferBatch";
    FFSDebug.log(str, ": start", 6);
    WireBatchInfo[] arrayOfWireBatchInfo = getWireTransferBatch(paramFFSConnectionHolder, paramWireBatchInfo, paramBoolean, true);
    FFSDebug.log(str, ": done", 6);
    return arrayOfWireBatchInfo;
  }
  
  public static WireBatchInfo[] getWireTransferBatch(FFSConnectionHolder paramFFSConnectionHolder, WireBatchInfo paramWireBatchInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str = "Wire.getWireTransferBatch";
    FFSDebug.log(str, ": start", 6);
    WireBatchInfo[] arrayOfWireBatchInfo = getWireTransferBatch(paramFFSConnectionHolder, paramWireBatchInfo, paramBoolean1, paramBoolean2, true);
    FFSDebug.log(str, ": done", 6);
    return arrayOfWireBatchInfo;
  }
  
  public static WireBatchInfo[] getWireTransferBatch(FFSConnectionHolder paramFFSConnectionHolder, WireBatchInfo paramWireBatchInfo, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws FFSException
  {
    String str1 = "Wire.getWireTransferBatch";
    FFSDebug.log(str1, ": start", 6);
    StringBuffer localStringBuffer = null;
    ArrayList localArrayList1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    int i = 0;
    WireBatchInfo localWireBatchInfo = null;
    ArrayList localArrayList2 = null;
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = null;
    if (paramWireBatchInfo == null) {
      return null;
    }
    str2 = paramWireBatchInfo.getCustomerId();
    str3 = paramWireBatchInfo.getUserId();
    str4 = paramWireBatchInfo.getBatchId();
    Object localObject1;
    if (((str2 == null) || (str2.trim().length() == 0)) && ((str3 == null) || (str3.trim().length() == 0)) && ((str4 == null) || (str4.trim().length() == 0)))
    {
      String str5 = " CustomerId, UserId and BatchId all are null or not specified.";
      FFSDebug.log("Wire.getWireTransferBatch: Failed, ", str5, 0);
      paramWireBatchInfo.setStatusCode(16010);
      paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "Wire", "CustomerId UserId BatchId" }, "WIRE_MESSAGE"));
      localObject1 = new WireBatchInfo[1];
      localObject1[0] = paramWireBatchInfo;
      return localObject1;
    }
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT BatchId, FIId, CustomerId,  UserId, BatchName, BatchType, BatchCategory, TotalAmount, WireCount,  SubmittedBy, SubmitDate, BatchStatus, ExtInfo, Memo, LogId, BatchDest,  ExtId,DateDue, DateToPost,PayInstruct,RecWireBatchId,ConfirmNum,  ConfirmMsg,ConfirmNum2,DatePosted, MathRule,  ContractNumber, ExchangeRate, AmtCurrency, DestCurrency,  SettlementDate, TotalCreditAmount, TotalDebitAmount, AgentId,  AgentName, AgentType,OrigAmount,OrigCurrency, Version  FROM BPW_WireBatch ");
    localArrayList1 = new ArrayList();
    if ((str2 != null) && (str2.trim().length() > 0))
    {
      localStringBuffer.append(" WHERE CustomerId = ? ");
      localArrayList1.add(str2);
    }
    if ((str3 != null) && (str3.trim().length() > 0))
    {
      if (localArrayList1.size() > 0) {
        localStringBuffer.append(" AND UserId = ? ");
      } else {
        localStringBuffer.append(" WHERE UserId = ? ");
      }
      localArrayList1.add(str3);
    }
    if ((str4 != null) && (str4.trim().length() > 0))
    {
      if (localArrayList1.size() > 0) {
        localStringBuffer.append(" AND BatchId = ? ");
      } else {
        localStringBuffer.append(" WHERE BatchId = ? ");
      }
      localArrayList1.add(str4);
    }
    if (paramBoolean3) {
      localStringBuffer.append(" AND BatchStatus != '").append("CANCELEDON").append("' ");
    }
    localStringBuffer.append(" ORDER BY BatchId ");
    i = localArrayList1.size();
    arrayOfObject = new Object[i];
    for (int j = 0; j < i; j++) {
      arrayOfObject[j] = localArrayList1.get(j);
    }
    try
    {
      localArrayList2 = new ArrayList();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localWireBatchInfo = jdMethod_int(localFFSResultSet);
        if (paramBoolean2)
        {
          localWireBatchInfo = getWiresByBatchId(paramFFSConnectionHolder, localWireBatchInfo, paramBoolean1);
          if (localWireBatchInfo.getStatusCode() != 0)
          {
            String str6 = str1 + " failed: " + localWireBatchInfo.getStatusMsg();
            FFSDebug.log(str6, 0);
            localArrayList2.clear();
            localArrayList2.add(localWireBatchInfo);
            break;
          }
        }
        localWireBatchInfo.setStatusCode(0);
        localWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        localArrayList2.add(localWireBatchInfo);
      }
      if (localArrayList2.size() == 0)
      {
        paramWireBatchInfo.setStatusCode(16020);
        paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WireBatchInfo" }, "WIRE_MESSAGE"));
        localArrayList2.add(paramWireBatchInfo);
      }
    }
    catch (Throwable localThrowable)
    {
      localObject1 = str1 + " failed: ";
      String str7 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1, str7, 0);
      throw new FFSException(localThrowable, (String)localObject1);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str8 = str1 + " failed: ";
        String str9 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str8, str9, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return (WireBatchInfo[])localArrayList2.toArray(new WireBatchInfo[0]);
  }
  
  private static WireBatchInfo jdMethod_int(FFSResultSet paramFFSResultSet)
    throws Exception
  {
    WireBatchInfo localWireBatchInfo = new WireBatchInfo();
    DBUtil.populateBaseInfo(paramFFSResultSet, localWireBatchInfo);
    localWireBatchInfo.setBatchId(paramFFSResultSet.getColumnString("BatchId"));
    localWireBatchInfo.setFIId(paramFFSResultSet.getColumnString("FIId"));
    localWireBatchInfo.setCustomerId(paramFFSResultSet.getColumnString("CustomerId"));
    localWireBatchInfo.setUserId(paramFFSResultSet.getColumnString("UserId"));
    localWireBatchInfo.setBatchName(paramFFSResultSet.getColumnString("BatchName"));
    localWireBatchInfo.setBatchType(paramFFSResultSet.getColumnString("BatchType"));
    localWireBatchInfo.setBatchCategory(paramFFSResultSet.getColumnString("BatchCategory"));
    localWireBatchInfo.setTotalAmount(paramFFSResultSet.getColumnString("TotalAmount"));
    localWireBatchInfo.setWireCount(paramFFSResultSet.getColumnString("WireCount"));
    localWireBatchInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    localWireBatchInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
    localWireBatchInfo.setPrcStatus(paramFFSResultSet.getColumnString("BatchStatus"));
    localWireBatchInfo.setExtInfo(FFSUtil.stringToHashtable(paramFFSResultSet.getColumnString("ExtInfo")));
    localWireBatchInfo.setMemo(paramFFSResultSet.getColumnString("Memo"));
    localWireBatchInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    localWireBatchInfo.setBatchDest(paramFFSResultSet.getColumnString("BatchDest"));
    localWireBatchInfo.setExtId(paramFFSResultSet.getColumnString("ExtId"));
    localWireBatchInfo.setDateDue(paramFFSResultSet.getColumnString("DateDue"));
    localWireBatchInfo.setDateToPost(paramFFSResultSet.getColumnString("DateToPost"));
    localWireBatchInfo.setPayInstruct(paramFFSResultSet.getColumnString("PayInstruct"));
    localWireBatchInfo.setRecWireBatchId(paramFFSResultSet.getColumnString("RecWireBatchId"));
    localWireBatchInfo.setConfirmNum(paramFFSResultSet.getColumnString("ConfirmNum"));
    localWireBatchInfo.setConfirmMsg(paramFFSResultSet.getColumnString("ConfirmMsg"));
    localWireBatchInfo.setConfirmNum2(paramFFSResultSet.getColumnString("ConfirmNum2"));
    localWireBatchInfo.setDatePosted(paramFFSResultSet.getColumnString("DatePosted"));
    localWireBatchInfo.setMathRule(paramFFSResultSet.getColumnString("MathRule"));
    localWireBatchInfo.setContractNumber(paramFFSResultSet.getColumnString("ContractNumber"));
    localWireBatchInfo.setExchangeRate(paramFFSResultSet.getColumnString("ExchangeRate"));
    localWireBatchInfo.setAmtCurrency(paramFFSResultSet.getColumnString("AmtCurrency"));
    localWireBatchInfo.setDestCurrency(paramFFSResultSet.getColumnString("DestCurrency"));
    localWireBatchInfo.setSettlementDate(paramFFSResultSet.getColumnString("SettlementDate"));
    localWireBatchInfo.setTotalCreditAmount(paramFFSResultSet.getColumnString("TotalCreditAmount"));
    localWireBatchInfo.setTotalDebitAmount(paramFFSResultSet.getColumnString("TotalDebitAmount"));
    localWireBatchInfo.setAgentId(paramFFSResultSet.getColumnString("AgentId"));
    localWireBatchInfo.setAgentName(paramFFSResultSet.getColumnString("AgentName"));
    localWireBatchInfo.setAgentType(paramFFSResultSet.getColumnString("AgentType"));
    localWireBatchInfo.setOrigAmount(paramFFSResultSet.getColumnString("OrigAmount"));
    localWireBatchInfo.setOrigCurrency(paramFFSResultSet.getColumnString("OrigCurrency"));
    return localWireBatchInfo;
  }
  
  public static WireBatchInfo getWiresByBatchId(FFSConnectionHolder paramFFSConnectionHolder, WireBatchInfo paramWireBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Wire.getWiresByBatchId";
    FFSDebug.log(str1, ": start, BatchId=", paramWireBatchInfo.getBatchId(), 6);
    String str2 = null;
    Object[] arrayOfObject = { paramWireBatchInfo.getBatchId(), "CANCELEDON" };
    String str3 = null;
    Hashtable localHashtable = null;
    Object localObject1 = null;
    Object localObject2 = null;
    ArrayList localArrayList = null;
    FFSResultSet localFFSResultSet = null;
    WirePayeeInfo localWirePayeeInfo1 = null;
    WirePayeeInfo localWirePayeeInfo2 = null;
    String str4 = null;
    if (paramBoolean)
    {
      str2 = "SELECT a.RecSrvrTID, BankFromID, BranchFromID, CustomerID,WirePayeeID, FIID, Amount, AcctDebitID,AcctDebitType,AcctDebitKey, WireType,WireCategory,WireGroup,WireDest,Status,LogID,PayInstruct,Memo,AmtCurrency,DestCurrency,XferFee, StartAmount,EndAmount,DateCreate,Frequency,StartDate,EndDate,InstanceCount,SubmittedBy, ExchangeRate, BatchId, ContractNumber,ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,AgentId,AgentName,Method, ProcessedBy, TemplateId,CustomerRef,OriginatorCharges,ReceiverCharges, WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum, UserId,AgentType,OrigAmount,OrigCurrency,WireCreditID , ByOrderOfName, ByOrderOfAddr1, ByOrderOfAddr2, ByOrderOfAddr3, ByOrderOfAcct, b.Version  FROM BPW_RecWireInfo a, BPW_RecWireInfo2 b  WHERE a.RecSrvrTID = b.RecSrvrTID AND BatchId = ?";
      str3 = "RECWIRE";
    }
    else
    {
      str2 = "SELECT a.SrvrTID,BankFromID,BranchFromID,CustomerID,WirePayeeID,RecSrvrTID,FIID,Amount,AmtCurrency,DestCurrency,XferFee,AcctDebitID, AcctDebitType,AcctDebitKey,DateCreate,DateDue,DateToPost,DatePosted, WireType,WireCategory,WireGroup,WireDest, BatchId, ContractNumber, Status,LogID,ConfirmNum, ConfirmNum2, ConfirmMsg, PayInstruct, Memo,ExchangeRate,SubmittedBy,ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,ProcessedBy,TemplateId,AgentId, AgentName,Method,AmountType,CustomerRef,OriginatorCharges,ReceiverCharges, WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum,HostId, UserId,AgentType,OrigAmount,OrigCurrency, WireCreditID , ByOrderOfName, ByOrderOfAddr1, ByOrderOfAddr2, ByOrderOfAddr3, ByOrderOfAcct, b.Version  FROM BPW_WireInfo a, BPW_WireInfo2 b  WHERE a.SrvrTID = b.SrvrTID AND BatchId = ?";
      str3 = "WIRE";
    }
    str2 = str2 + " AND Status != ?";
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      localArrayList = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        if (paramBoolean) {
          localObject1 = new RecWireInfo();
        } else {
          localObject1 = new WireInfo();
        }
        a((WireInfo)localObject1, localFFSResultSet, paramBoolean);
        localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, ((WireInfo)localObject1).getFiID(), ((WireInfo)localObject1).getSrvrTid(), str3);
        ((WireInfo)localObject1).setExtInfo(localHashtable);
        Object localObject3;
        if (((WireInfo)localObject1).getWirePayeeId() != null)
        {
          localWirePayeeInfo1 = WirePayee.getWirePayeeInfo(paramFFSConnectionHolder, ((WireInfo)localObject1).getWirePayeeId(), false);
          if (localWirePayeeInfo1.getStatusCode() != 0)
          {
            localObject3 = BPWLocaleUtil.getMessage(localWirePayeeInfo1.getStatusCode(), new String[] { localWirePayeeInfo1.getStatusMsg(), "SrvrTid:", ((WireInfo)localObject1).getSrvrTid(), "BatchId:", paramWireBatchInfo.getBatchId() }, "WIRE_MESSAGE");
            paramWireBatchInfo.setStatusCode(localWirePayeeInfo1.getStatusCode());
            paramWireBatchInfo.setStatusMsg((String)localObject3);
            localObject4 = paramWireBatchInfo;
            return localObject4;
          }
          ((WireInfo)localObject1).setWirePayeeInfo(localWirePayeeInfo1);
        }
        if (((WireInfo)localObject1).getWireCreditId() != null)
        {
          localWirePayeeInfo2 = WirePayee.getWirePayeeInfo(paramFFSConnectionHolder, ((WireInfo)localObject1).getWireCreditId(), false);
          if (localWirePayeeInfo2.getStatusCode() != 0)
          {
            paramWireBatchInfo.setStatusCode(localWirePayeeInfo2.getStatusCode());
            paramWireBatchInfo.setStatusMsg(localWirePayeeInfo2.getStatusMsg());
            localObject3 = paramWireBatchInfo;
            return localObject3;
          }
          ((WireInfo)localObject1).setWireCreditInfo(localWirePayeeInfo2);
        }
        ((WireInfo)localObject1).setStatusCode(0);
        ((WireInfo)localObject1).setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
        localArrayList.add(localObject1);
      }
      if (localArrayList.size() > 0)
      {
        if (paramBoolean) {
          localObject2 = (RecWireInfo[])localArrayList.toArray(new RecWireInfo[0]);
        } else {
          localObject2 = (WireInfo[])localArrayList.toArray(new WireInfo[0]);
        }
        paramWireBatchInfo.setWires((WireInfo[])localObject2);
        str4 = jdMethod_int(paramWireBatchInfo);
        paramWireBatchInfo.setPrcStatus(str4);
      }
      else
      {
        paramWireBatchInfo.setWires(null);
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject4 = str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject4, str5, 0);
      throw new FFSException(localThrowable, (String)localObject4);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str6 = str1 + " failed: ";
        String str7 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str6, str7, 0);
      }
    }
    paramWireBatchInfo.setStatusCode(0);
    paramWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    FFSDebug.log(str1, ": done", 6);
    return paramWireBatchInfo;
  }
  
  public static BPWHist getWireBatchHistory(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("Wire.getWireBatchHistory : start", 6);
    StringBuffer localStringBuffer = null;
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    ArrayList localArrayList = new ArrayList();
    paramBPWHist = a(paramBPWHist, "getWireBatchHistory");
    if (paramBPWHist.getStatusCode() != 0) {
      return paramBPWHist;
    }
    localStringBuffer = new StringBuffer();
    if ((paramBPWHist.getCustId() != null) && (paramBPWHist.getCustId().trim().length() > 0))
    {
      localStringBuffer.append(" AND a.CustomerID = ? ");
      localArrayList.add(paramBPWHist.getCustId());
    }
    arrayOfString1 = paramBPWHist.getSubmittedBy();
    int i;
    if ((arrayOfString1 != null) && (arrayOfString1.length > 0)) {
      if (arrayOfString1.length == 1)
      {
        localStringBuffer.append(" AND a.SubmittedBy = ? ");
        localArrayList.add(arrayOfString1[0]);
      }
      else
      {
        localStringBuffer.append(" AND a.SubmittedBy IN (?");
        localArrayList.add(arrayOfString1[0]);
        for (i = 1; i < arrayOfString1.length; i++)
        {
          localStringBuffer.append(", ?");
          localArrayList.add(arrayOfString1[i]);
        }
        localStringBuffer.append(")");
      }
    }
    str1 = paramBPWHist.getFiId();
    if ((str1 != null) && (str1.trim().length() != 0))
    {
      localStringBuffer.append(" AND FIID = ? ");
      localArrayList.add(str1);
    }
    str2 = paramBPWHist.getTransType();
    if ((str2 != null) && (str2.trim().length() != 0))
    {
      localStringBuffer.append(" AND WireType = ? ");
      localArrayList.add(str2);
    }
    str3 = paramBPWHist.getTransScope();
    if ((str3 != null) && (str3.trim().length() != 0))
    {
      localStringBuffer.append(" AND WireScope = ? ");
      localArrayList.add(str3);
    }
    str4 = paramBPWHist.getTemplateId();
    if ((str4 != null) && (str4.trim().length() != 0))
    {
      localStringBuffer.append(" AND TemplateId = ? ");
      localArrayList.add(str4);
    }
    arrayOfString2 = paramBPWHist.getDest();
    if ((arrayOfString2 != null) && (arrayOfString2.length > 0))
    {
      FFSDebug.log("Wire.getWireBatchHistory : dests.length:" + arrayOfString2.length, 6);
      if (arrayOfString2.length == 1)
      {
        localStringBuffer.append(" AND a.WireDest = ? ");
        localArrayList.add(arrayOfString2[0]);
      }
      else
      {
        localStringBuffer.append(" AND a.WireDest IN (?");
        localArrayList.add(arrayOfString2[0]);
        for (i = 1; i < arrayOfString2.length; i++)
        {
          localStringBuffer.append(", ?");
          localArrayList.add(arrayOfString2[i]);
        }
        localStringBuffer.append(")");
      }
    }
    localStringBuffer.append(" AND BatchId IS NOT NULL ");
    if ((paramBPWHist.getHistId() == null) || (paramBPWHist.getHistId().trim().length() == 0))
    {
      paramBPWHist.setTotalTrans(0L);
      jdMethod_int(paramFFSConnectionHolder, paramBPWHist, localStringBuffer.toString(), localArrayList);
      paramBPWHist.setCursorId("0");
    }
    jdMethod_if(paramFFSConnectionHolder, paramBPWHist);
    return paramBPWHist;
  }
  
  private static void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("Wire.getBatchHistory : start", 6);
    FFSDebug.log("Wire.getBatchHistory : Parameter values \nHistId : " + paramBPWHist.getHistId() + "\nCursorId : " + paramBPWHist.getCursorId() + "\nPage Size : " + paramBPWHist.getPageSize(), 6);
    ArrayList localArrayList = new ArrayList();
    WireBatchInfo[] arrayOfWireBatchInfo = null;
    WireBatchInfo localWireBatchInfo = null;
    try
    {
      TempHist[] arrayOfTempHist = TempHist.get(paramFFSConnectionHolder, paramBPWHist.getHistId(), paramBPWHist.getCursorId(), paramBPWHist.getPageSize());
      int i = arrayOfTempHist.length;
      for (int j = 0; j < i; j++)
      {
        FFSDebug.log("Wire.getBatchHistory : RecordType=", arrayOfTempHist[j].RecordType, 6);
        if (arrayOfTempHist[j].RecordType.equals("WIREBATCH"))
        {
          localWireBatchInfo = new WireBatchInfo();
          localWireBatchInfo.setBatchId(arrayOfTempHist[j].RecordExtId);
          arrayOfWireBatchInfo = getWireTransferBatch(paramFFSConnectionHolder, localWireBatchInfo, false, false, false);
          localWireBatchInfo = arrayOfWireBatchInfo[0];
          localWireBatchInfo.setPrcStatus(arrayOfTempHist[j].Amount);
          localWireBatchInfo.setRecordCursor(Long.parseLong(arrayOfTempHist[j].CursorId));
        }
        else
        {
          localWireBatchInfo = new WireBatchInfo();
          localWireBatchInfo.setStatusCode(23190);
          localWireBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(23190, null, "WIRE_MESSAGE"));
          FFSDebug.log("Wire.getBatchHistory : Invalid History Record Type : RecordExtId = " + arrayOfTempHist[j].RecordExtId + "RecordType = " + arrayOfTempHist[j].RecordType, 0);
        }
        if (localWireBatchInfo != null) {
          localArrayList.add(localWireBatchInfo);
        }
      }
      paramBPWHist.setTrans((WireBatchInfo[])localArrayList.toArray(new WireBatchInfo[0]));
      if (paramBPWHist.getTotalTrans() == 0L) {
        paramBPWHist.setTotalTrans(TempHist.getCount(paramFFSConnectionHolder, paramBPWHist.getHistId()));
      }
      if (localArrayList.size() == 0)
      {
        FFSDebug.log("Wire.getBatchHistory : No history record found", 6);
        paramBPWHist.setStatusCode(16020);
        paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WireInstruction" }, "WIRE_MESSAGE"));
      }
      else
      {
        paramBPWHist.setCursorId(arrayOfTempHist[(arrayOfTempHist.length - 1)].CursorId);
        paramBPWHist.setStatusCode(0);
        paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      String str = "Wire.getBatchHistory : failed:  " + localThrowable.getMessage();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public static BPWHist getCombinedWireHistory(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist)
    throws FFSException
  {
    String str1 = "Wire.getCombinedWireHistory: ";
    FFSDebug.log(str1, "start", 6);
    paramBPWHist = a(paramBPWHist, "getWireHistory");
    FFSDebug.log(str1, "commonFieldsValid done", 6);
    FFSDebug.log(str1, "wireHist:" + paramBPWHist, 6);
    if (paramBPWHist.getStatusCode() != 0) {
      return paramBPWHist;
    }
    try
    {
      a(paramFFSConnectionHolder, paramBPWHist, null);
      a(paramFFSConnectionHolder, paramBPWHist);
      return paramBPWHist;
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed : ";
      FFSDebug.log(str2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, String paramString)
    throws FFSException
  {
    FFSDebug.log("Wire.createWireHistory : start", 6);
    FFSDebug.log("Wire.createWireHistory : Param Values", 6);
    FFSDebug.log("StartDate : " + paramBPWHist.getStartDate() + "\nEndDate : " + paramBPWHist.getEndDate(), 6);
    StringBuffer localStringBuffer = new StringBuffer();
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    String[] arrayOfString3 = null;
    String str9 = null;
    String str10 = null;
    ArrayList localArrayList1 = new ArrayList();
    if (paramString != null) {
      localStringBuffer.append(paramString);
    }
    arrayOfString1 = paramBPWHist.getSubmittedBy();
    int i;
    if ((arrayOfString1 != null) && (arrayOfString1.length > 0))
    {
      FFSDebug.log("Wire.getWireHistory : submittedBy.length:" + arrayOfString1.length, 6);
      if (arrayOfString1.length == 1)
      {
        localStringBuffer.append(" AND a.SubmittedBy = ? ");
        localArrayList1.add(arrayOfString1[0]);
      }
      else
      {
        localStringBuffer.append(" AND a.SubmittedBy IN (?");
        localArrayList1.add(arrayOfString1[0]);
        for (i = 1; i < arrayOfString1.length; i++)
        {
          localStringBuffer.append(", ?");
          localArrayList1.add(arrayOfString1[i]);
        }
        localStringBuffer.append(")");
      }
    }
    arrayOfString2 = paramBPWHist.getUserId();
    if ((arrayOfString2 != null) && (arrayOfString2.length > 0))
    {
      FFSDebug.log("Wire.getWireHistory : userIds.length:" + arrayOfString2.length, 6);
      if (arrayOfString2.length == 1)
      {
        localStringBuffer.append(" AND a.UserId = ? ");
        localArrayList1.add(arrayOfString2[0]);
      }
      else
      {
        localStringBuffer.append(" AND a.UserId IN (?");
        localArrayList1.add(arrayOfString2[0]);
        for (i = 1; i < arrayOfString2.length; i++)
        {
          localStringBuffer.append(", ?");
          localArrayList1.add(arrayOfString2[i]);
        }
        localStringBuffer.append(")");
      }
    }
    str1 = paramBPWHist.getFiId();
    if ((str1 != null) && (str1.trim().length() != 0))
    {
      localStringBuffer.append(" AND FIID = ? ");
      localArrayList1.add(str1);
    }
    str2 = paramBPWHist.getTransType();
    if ((str2 != null) && (str2.trim().length() != 0)) {
      if (str2.equalsIgnoreCase("TEMPLATE"))
      {
        localStringBuffer.append(" AND ( (WireType = 'TEMPLATE') OR (WireType = 'RECTEMPLATE') ) ");
      }
      else
      {
        localStringBuffer.append(" AND WireType = ? ");
        localArrayList1.add(str2);
      }
    }
    str3 = paramBPWHist.getTransScope();
    if ((str3 != null) && (str3.trim().length() != 0))
    {
      localStringBuffer.append(" AND WireScope = ? ");
      localArrayList1.add(str3);
    }
    str4 = paramBPWHist.getTransSource();
    if ((str4 != null) && (str4.trim().length() != 0))
    {
      localStringBuffer.append(" AND WireSource = ? ");
      localArrayList1.add(str4);
    }
    arrayOfString3 = paramBPWHist.getDest();
    if ((arrayOfString3 != null) && (arrayOfString3.length > 0))
    {
      FFSDebug.log("Wire.createWireHistory : dests.length:" + arrayOfString3.length, 6);
      if (arrayOfString3.length == 1)
      {
        localStringBuffer.append(" AND a.WireDest = ? ");
        localArrayList1.add(arrayOfString3[0]);
      }
      else
      {
        localStringBuffer.append(" AND a.WireDest IN (?");
        localArrayList1.add(arrayOfString3[0]);
        for (i = 1; i < arrayOfString3.length; i++)
        {
          localStringBuffer.append(", ?");
          localArrayList1.add(arrayOfString3[i]);
        }
        localStringBuffer.append(")");
      }
    }
    str5 = paramBPWHist.getTemplateId();
    if ((str5 != null) && (str5.trim().length() != 0))
    {
      localStringBuffer.append(" AND TemplateId = ? ");
      localArrayList1.add(str5);
    }
    str6 = paramBPWHist.getMinAmount();
    if ((str6 != null) && (str6.trim().length() != 0))
    {
      localStringBuffer.append(" AND cast (Amount as decimal) >= ? ");
      localArrayList1.add(new BigDecimal(str6));
    }
    str7 = paramBPWHist.getMaxAmount();
    if ((str7 != null) && (str7.trim().length() != 0))
    {
      localStringBuffer.append(" AND cast (Amount as decimal) <= ? ");
      localArrayList1.add(new BigDecimal(str7));
    }
    str8 = paramBPWHist.getCustId();
    if ((str8 != null) && (str8.trim().length() != 0))
    {
      localStringBuffer.append(" AND a.CustomerID = ? ");
      localArrayList1.add(paramBPWHist.getCustId());
    }
    str10 = localStringBuffer.toString();
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.addAll(localArrayList1);
    FFSDebug.log("Wire.createWireHistory : sqlToAppendComm:", str10, 6);
    str9 = paramBPWHist.getSelectionCriteria();
    if ((str9 == null) || (!str9.trim().equalsIgnoreCase("all"))) {
      localStringBuffer.append(" AND BatchId IS NULL ");
    }
    if ((paramBPWHist.getHistId() == null) || (paramBPWHist.getHistId().trim().length() == 0))
    {
      jdMethod_for(paramFFSConnectionHolder, paramBPWHist, localStringBuffer.toString(), localArrayList1);
      FFSDebug.log("Wire.createWireHistory : TotalTrans1:" + paramBPWHist.getTotalTrans(), 6);
      localStringBuffer = new StringBuffer();
      localStringBuffer.append(str10);
      localStringBuffer.append(" AND BatchId IS NOT NULL ");
      jdMethod_int(paramFFSConnectionHolder, paramBPWHist, localStringBuffer.toString(), localArrayList2);
      FFSDebug.log("Wire.createWireHistory : TotalTrans2:" + paramBPWHist.getTotalTrans(), 6);
      paramBPWHist.setCursorId("0");
    }
  }
  
  private static void jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "Wire.createWireBatchHist: ";
    FFSDebug.log(str1, "start", 6);
    FFSResultSet localFFSResultSet = null;
    String str2 = null;
    String str3 = null;
    try
    {
      int i = 0;
      str4 = null;
      String str5 = null;
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int j = localPropertyConfig.getBatchSize();
      StringBuffer localStringBuffer = new StringBuffer("SELECT DISTINCT BatchId  FROM BPW_WireInfo a  WHERE DateToPost >= ? AND DateToPost <= ?");
      if ((paramString != null) && (paramString.length() > 0)) {
        localStringBuffer.append(paramString);
      }
      String[] arrayOfString = paramBPWHist.getStatusList();
      if ((arrayOfString == null) || (arrayOfString.length == 0))
      {
        localStringBuffer.append(" AND Status NOT IN (?, ?, ?)");
        paramArrayList.add("CANCELEDON");
        paramArrayList.add("TEMPLATE");
        paramArrayList.add("RECTEMPLATE");
      }
      else
      {
        localStringBuffer.append(" AND Status IN (?,");
        paramArrayList.add(arrayOfString[0]);
        for (int k = 1; k < arrayOfString.length - 1; k++)
        {
          localStringBuffer.append(" ?,");
          paramArrayList.add(arrayOfString[k]);
        }
        localStringBuffer.append(" ?)");
        paramArrayList.add(arrayOfString[(arrayOfString.length - 1)]);
      }
      localStringBuffer.append(" ORDER BY BatchId");
      if ((paramBPWHist.getHistId() == null) || (paramBPWHist.getHistId().length() == 0)) {
        paramBPWHist.setHistId(DBUtil.getNextIndexString("HistID"));
      }
      Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramBPWHist.getStartDate()));
      Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramBPWHist.getEndDate()));
      paramArrayList.add(0, localInteger2.toString());
      paramArrayList.add(0, localInteger1.toString());
      Object[] arrayOfObject = paramArrayList.toArray();
      FFSDebug.log(str1 + ": Sql: ", localStringBuffer.toString(), 6);
      FFSDebug.log(str1 + ": paramList: " + paramArrayList, 6);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      WireBatchInfo localWireBatchInfo = null;
      WireBatchInfo[] arrayOfWireBatchInfo1 = null;
      WireInfo[] arrayOfWireInfo = null;
      ArrayList localArrayList = new ArrayList();
      i = (int)paramBPWHist.getTotalTrans();
      int m;
      while (localFFSResultSet.getNextRow())
      {
        str3 = localFFSResultSet.getColumnString("BatchId");
        localWireBatchInfo = new WireBatchInfo();
        localWireBatchInfo.setBatchId(str3);
        boolean bool1 = false;
        m = 1;
        boolean bool2 = false;
        arrayOfWireBatchInfo1 = getWireTransferBatch(paramFFSConnectionHolder, localWireBatchInfo, bool1, m, bool2);
        localWireBatchInfo = arrayOfWireBatchInfo1[0];
        arrayOfWireInfo = localWireBatchInfo.getWires();
        if ((arrayOfWireInfo != null) && (arrayOfWireInfo.length != 0))
        {
          str5 = localWireBatchInfo.getDateToPost();
          i++;
          str4 = DBUtil.getCursor(Integer.parseInt(str5), i);
          FFSDebug.log(str1 + ": cursorId: ", str4, 6);
          localWireBatchInfo.setRecordCursor(Long.parseLong(str4));
          localArrayList.add(localWireBatchInfo);
        }
      }
      paramBPWHist.setTotalTrans(i);
      if (localArrayList.size() != 0)
      {
        paramFFSConnectionHolder.conn.commit();
        WireBatchInfo[] arrayOfWireBatchInfo2 = (WireBatchInfo[])localArrayList.toArray(new WireBatchInfo[0]);
        try
        {
          for (m = 0; m < arrayOfWireBatchInfo2.length; m++)
          {
            localWireBatchInfo = arrayOfWireBatchInfo2[m];
            str3 = localWireBatchInfo.getBatchId();
            str5 = localWireBatchInfo.getDateToPost();
            str2 = localWireBatchInfo.getPrcStatus();
            str4 = Long.toString(localWireBatchInfo.getRecordCursor());
            FFSDebug.log(str1, "Create TemHist for batchId=" + str3, ", dateToPost=" + str5, ", cursorId=" + str4, ", status=" + str2, 6);
            TempHist.create(paramFFSConnectionHolder, paramBPWHist.getHistId(), str4, str3, "WIREBATCH", Integer.parseInt(str5), str2);
            if (m % j == 0) {
              paramFFSConnectionHolder.conn.commit();
            }
          }
        }
        catch (Throwable localThrowable2) {}
      }
      paramFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1, "end", 6);
    }
    catch (Throwable localThrowable1)
    {
      String str4 = str1 + "failed : ";
      FFSDebug.log(str4, FFSDebug.stackTrace(localThrowable1), 0);
      throw new FFSException(localThrowable1, str4);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1, "failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("Wire.getHistoryRecords : start", 6);
    FFSDebug.log("Wire.getHistoryRecords : Parameter values \nHistId : " + paramBPWHist.getHistId() + "\nCursorId : " + paramBPWHist.getCursorId() + "\nPage Size : " + paramBPWHist.getPageSize(), 6);
    ArrayList localArrayList = new ArrayList();
    WireHistoryInfo localWireHistoryInfo = null;
    try
    {
      TempHist[] arrayOfTempHist = TempHist.get(paramFFSConnectionHolder, paramBPWHist.getHistId(), paramBPWHist.getCursorId(), paramBPWHist.getPageSize());
      int i = arrayOfTempHist.length;
      for (int j = 0; j < i; j++)
      {
        FFSDebug.log("Wire.getHistoryRecords : RecordType=", arrayOfTempHist[j].RecordType, 6);
        localWireHistoryInfo = new WireHistoryInfo();
        localWireHistoryInfo.setId(arrayOfTempHist[j].RecordExtId);
        if (arrayOfTempHist[j].RecordType.equals("WIRE"))
        {
          localWireHistoryInfo.setTransType("SINGLE");
        }
        else if (arrayOfTempHist[j].RecordType.equals("RECWIRE"))
        {
          localWireHistoryInfo.setTransType("RECURRING");
        }
        else if (arrayOfTempHist[j].RecordType.equals("WIREBATCH"))
        {
          localWireHistoryInfo.setTransType("BATCH");
        }
        else
        {
          localWireHistoryInfo = new WireHistoryInfo();
          localWireHistoryInfo.setStatusCode(23190);
          localWireHistoryInfo.setStatusMsg(BPWLocaleUtil.getMessage(23190, null, "WIRE_MESSAGE"));
          FFSDebug.log("Wire.getHistoryRecords : Invalid History Record Type : RecordExtId = " + arrayOfTempHist[j].RecordExtId + "RecordType = " + arrayOfTempHist[j].RecordType, 0);
        }
        localWireHistoryInfo = getWireHistoryInfo(paramFFSConnectionHolder, localWireHistoryInfo);
        localWireHistoryInfo.setDate(BPWUtil.getDateBeanFormat(arrayOfTempHist[j].DueDate));
        if (arrayOfTempHist[j].RecordType.equals("RECWIRE"))
        {
          if (localWireHistoryInfo.getStatus().equals("WILLPROCESSON")) {
            localWireHistoryInfo.setStatus("CREATED");
          }
        }
        else if (arrayOfTempHist[j].RecordType.equals("WIREBATCH")) {
          localWireHistoryInfo.setStatus(arrayOfTempHist[j].Amount);
        }
        if ((localWireHistoryInfo.getRecId() != null) && (localWireHistoryInfo.getRecId().trim().length() > 0)) {
          localWireHistoryInfo.setTransType("RECURRING");
        }
        if (localWireHistoryInfo != null) {
          localArrayList.add(localWireHistoryInfo);
        }
      }
      paramBPWHist.setTrans((WireHistoryInfo[])localArrayList.toArray(new WireHistoryInfo[0]));
      if (paramBPWHist.getTotalTrans() == 0L) {
        paramBPWHist.setTotalTrans(TempHist.getCount(paramFFSConnectionHolder, paramBPWHist.getHistId()));
      }
      if (localArrayList.size() == 0)
      {
        FFSDebug.log("Wire.getHistoryRecords : No history record found", 6);
        paramBPWHist.setStatusCode(16020);
        paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WireInstruction" }, "WIRE_MESSAGE"));
      }
      else
      {
        paramBPWHist.setCursorId(arrayOfTempHist[(arrayOfTempHist.length - 1)].CursorId);
        paramBPWHist.setStatusCode(0);
        paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      String str = "Wire.getHistoryRecords : failed:  " + localThrowable.getMessage();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public static WireHistoryInfo getWireHistoryInfoWithWireObject(FFSConnectionHolder paramFFSConnectionHolder, WireHistoryInfo paramWireHistoryInfo)
    throws FFSException
  {
    String str1 = "Wire.getWireHistoryInfoWithWireObject";
    FFSDebug.log(str1, ": start, srvrTID=", paramWireHistoryInfo.getId(), 6);
    try
    {
      String str2 = paramWireHistoryInfo.getTransType();
      localObject1 = null;
      boolean bool1;
      boolean bool2;
      Object localObject3;
      String str4;
      int i;
      if (str2.equals("RECURRING"))
      {
        bool1 = true;
        bool2 = true;
        localObject1 = new RecWireInfo();
        FFSDebug.log(str1 + ": wireHist.getRecId()= " + paramWireHistoryInfo.getRecId(), 6);
        ((WireInfo)localObject1).setSrvrTid(paramWireHistoryInfo.getId());
        localObject1 = getWireInfo(paramFFSConnectionHolder, (WireInfo)localObject1, bool1, bool2);
        FFSDebug.log(str1 + ": wireInfo= " + localObject1, 6);
        if (((WireInfo)localObject1).getStatusCode() != 0)
        {
          FFSDebug.log(str1 + ": srvrTID= " + paramWireHistoryInfo.getId(), 6);
          paramWireHistoryInfo.setStatusCode(((WireInfo)localObject1).getStatusCode());
          paramWireHistoryInfo.setStatusMsg(((WireInfo)localObject1).getStatusMsg());
          return paramWireHistoryInfo;
        }
        ((WireInfo)localObject1).setDateDue(paramWireHistoryInfo.getDate());
        String str3 = null;
        try
        {
          str3 = DBUtil.getPayday(((WireInfo)localObject1).getFiID(), ((WireInfo)localObject1).getDateDue());
          ((WireInfo)localObject1).setDateToPost(str3);
        }
        catch (Throwable localThrowable2)
        {
          localObject3 = "Exception in " + str1 + " :";
          str4 = FFSDebug.stackTrace(localThrowable2);
          FFSDebug.log((String)localObject3 + str4, 0);
          paramWireHistoryInfo.setStatusCode(19200);
          paramWireHistoryInfo.setStatusMsg(BPWLocaleUtil.getMessage(19200, null, "WIRE_MESSAGE"));
          return paramWireHistoryInfo;
        }
        if (((WireInfo)localObject1).getPrcStatus().equals("WILLPROCESSON")) {
          ((WireInfo)localObject1).setPrcStatus("CREATED");
        }
        a(paramWireHistoryInfo, (RecWireInfo)localObject1);
        FFSDebug.log(str1 + ": dateToPost= " + str3, 6);
        i = BPWUtil.getDateDBFormat(str3);
        paramWireHistoryInfo.setDate("" + i);
        paramWireHistoryInfo.setWireObject(localObject1);
        paramWireHistoryInfo.setRecId(paramWireHistoryInfo.getId());
        FFSDebug.log(str1 + ": wireInfo2= " + localObject1, 6);
      }
      else if (str2.equals("RECTEMPLATE"))
      {
        bool1 = true;
        bool2 = true;
        localObject1 = new RecWireInfo();
        ((WireInfo)localObject1).setSrvrTid(paramWireHistoryInfo.getId());
        localObject1 = getWireInfo(paramFFSConnectionHolder, (WireInfo)localObject1, bool1, bool2);
        FFSDebug.log(str1 + ": wireInfo33= " + localObject1, 6);
        if (((WireInfo)localObject1).getStatusCode() != 0)
        {
          paramWireHistoryInfo.setStatusCode(((WireInfo)localObject1).getStatusCode());
          FFSDebug.log(str1, ": srvrTID=", paramWireHistoryInfo.getId(), 6);
          paramWireHistoryInfo.setStatusMsg(((WireInfo)localObject1).getStatusMsg());
          return paramWireHistoryInfo;
        }
        a(paramWireHistoryInfo, (RecWireInfo)localObject1);
        FFSDebug.log(str1 + ": wireInfo22= " + localObject1, 6);
      }
      else if ((str2.equals("SINGLE")) || (str2.equals("TEMPLATE")) || (str2.equals("BATCHWIRE")) || (str2.equals("RECURRING_SINGLE")))
      {
        bool1 = false;
        bool2 = true;
        localObject1 = new WireInfo();
        ((WireInfo)localObject1).setSrvrTid(paramWireHistoryInfo.getId());
        localObject1 = getWireInfo(paramFFSConnectionHolder, (WireInfo)localObject1, bool1, bool2);
        if (((WireInfo)localObject1).getStatusCode() != 0)
        {
          paramWireHistoryInfo.setStatusCode(((WireInfo)localObject1).getStatusCode());
          paramWireHistoryInfo.setStatusMsg(((WireInfo)localObject1).getStatusMsg());
          return paramWireHistoryInfo;
        }
        a(paramWireHistoryInfo, (WireInfo)localObject1);
        if (str2.equals("RECURRING_SINGLE")) {
          paramWireHistoryInfo.setTransType("RECURRING");
        }
      }
      else if (str2.equals("BATCH"))
      {
        localObject2 = new WireBatchInfo();
        ((WireBatchInfo)localObject2).setBatchId(paramWireHistoryInfo.getId());
        bool2 = true;
        boolean bool3 = false;
        i = 0;
        localObject3 = getWireTransferBatch(paramFFSConnectionHolder, (WireBatchInfo)localObject2, bool3, bool2, i);
        localObject2 = localObject3[0];
        if (((WireBatchInfo)localObject2).getStatusCode() != 0)
        {
          paramWireHistoryInfo.setStatusCode(((WireBatchInfo)localObject2).getStatusCode());
          paramWireHistoryInfo.setStatusMsg(((WireBatchInfo)localObject2).getStatusMsg());
          return paramWireHistoryInfo;
        }
        str4 = jdMethod_int((WireBatchInfo)localObject2);
        FFSDebug.log(str1, ": TODO: batchStatus", str4, 6);
        ((WireBatchInfo)localObject2).setPrcStatus(str4);
        FFSDebug.log(str1, ": TODO: batchInfo.getPrcStatus()", ((WireBatchInfo)localObject2).getPrcStatus(), 6);
        a(paramWireHistoryInfo, (WireBatchInfo)localObject2);
      }
      else
      {
        paramWireHistoryInfo.setStatusCode(19390);
        paramWireHistoryInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, new String[] { str2 }, "WIRE_MESSAGE"));
        FFSDebug.log(str1, " : ", "Invalid trans type in BPWHist:", str2, 0);
        return paramWireHistoryInfo;
      }
      paramWireHistoryInfo.setStatusCode(0);
      paramWireHistoryInfo.setStatusMsg("Success");
      paramWireHistoryInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = str1 + " failed: ";
      Object localObject2 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, (String)localObject2, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    FFSDebug.log(str1, ": done", 6);
    return paramWireHistoryInfo;
  }
  
  public static WireHistoryInfo getWireHistoryInfo(FFSConnectionHolder paramFFSConnectionHolder, WireHistoryInfo paramWireHistoryInfo)
    throws FFSException
  {
    String str1 = "Wire.getWireHistoryInfo";
    FFSDebug.log(str1, ": start, srvrTID=", paramWireHistoryInfo.getId(), 6);
    String str2 = null;
    Object[] arrayOfObject = { paramWireHistoryInfo.getId() };
    FFSResultSet localFFSResultSet = null;
    WirePayeeInfo localWirePayeeInfo = null;
    if ((paramWireHistoryInfo.getTransType().equals("RECURRING")) || (paramWireHistoryInfo.getTransType().equals("RECTEMPLATE")))
    {
      str2 = "SELECT a.RecSrvrTID, BankFromID, BranchFromID, CustomerID,WirePayeeID, FIID, Amount, AcctDebitID,AcctDebitType,AcctDebitKey, WireType,WireCategory,WireGroup,WireDest,Status,LogID,PayInstruct,Memo,AmtCurrency,DestCurrency,XferFee, StartAmount,EndAmount,DateCreate,Frequency,StartDate,EndDate,InstanceCount,SubmittedBy, ExchangeRate, BatchId, ContractNumber, ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,AgentId,AgentName,Method, ProcessedBy, TemplateId,CustomerRef,OriginatorCharges,ReceiverCharges, WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum, UserId,AgentType,OrigAmount,OrigCurrency,WireCreditID , ByOrderOfName, ByOrderOfAddr1, ByOrderOfAddr2, ByOrderOfAddr3, ByOrderOfAcct, b.Version  FROM BPW_RecWireInfo a, BPW_RecWireInfo2 b  WHERE a.RecSrvrTID = b.RecSrvrTID AND a.RecSrvrTID = ?";
    }
    else if ((paramWireHistoryInfo.getTransType().equals("SINGLE")) || (paramWireHistoryInfo.getTransType().equals("TEMPLATE")) || (paramWireHistoryInfo.getTransType().equals("BATCHWIRE")))
    {
      str2 = "SELECT a.SrvrTID,BankFromID,BranchFromID,CustomerID,WirePayeeID,RecSrvrTID,FIID,Amount,AmtCurrency,DestCurrency,XferFee,AcctDebitID, AcctDebitType,AcctDebitKey,DateCreate,DateDue,DateToPost,DatePosted, WireType,WireCategory,WireGroup,WireDest, BatchId, ContractNumber, Status,LogID,ConfirmNum, ConfirmNum2, ConfirmMsg, PayInstruct, Memo,ExchangeRate,SubmittedBy,ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,ProcessedBy,TemplateId,AgentId, AgentName,Method,AmountType,CustomerRef,OriginatorCharges,ReceiverCharges, WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum,HostId, UserId,AgentType,OrigAmount,OrigCurrency, WireCreditID, ByOrderOfName, ByOrderOfAddr1, ByOrderOfAddr2, ByOrderOfAddr3, ByOrderOfAcct, b.Version  FROM BPW_WireInfo a, BPW_WireInfo2 b  WHERE a.SrvrTID = b.SrvrTID AND a.SrvrTID = ?";
    }
    else
    {
      str2 = "SELECT BatchId, FIId, CustomerId,  UserId, BatchName, BatchType, BatchCategory, TotalAmount, WireCount,  SubmittedBy, SubmitDate, BatchStatus, ExtInfo, Memo, LogId, BatchDest,  ExtId,DateDue, DateToPost,PayInstruct,RecWireBatchId,ConfirmNum,  ConfirmMsg,ConfirmNum2,DatePosted, MathRule,  ContractNumber, ExchangeRate, AmtCurrency, DestCurrency,  SettlementDate, TotalCreditAmount, TotalDebitAmount, AgentId,  AgentName, AgentType,OrigAmount,OrigCurrency, Version  FROM BPW_WireBatch ";
      str2 = str2 + " WHERE BatchId = ?";
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        a(paramWireHistoryInfo, localFFSResultSet);
        if (paramWireHistoryInfo.getPayeeId() != null)
        {
          localWirePayeeInfo = WirePayee.getWirePayeeInfo(paramFFSConnectionHolder, paramWireHistoryInfo.getPayeeId(), false);
          if (localWirePayeeInfo.getStatusCode() != 0)
          {
            paramWireHistoryInfo.setStatusCode(localWirePayeeInfo.getStatusCode());
            paramWireHistoryInfo.setStatusMsg(localWirePayeeInfo.getStatusMsg());
            WireHistoryInfo localWireHistoryInfo = paramWireHistoryInfo;
            return localWireHistoryInfo;
          }
          paramWireHistoryInfo.setPayeeInfo(localWirePayeeInfo);
        }
        paramWireHistoryInfo.setStatusCode(0);
        paramWireHistoryInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      else
      {
        paramWireHistoryInfo.setStatusCode(16020);
        paramWireHistoryInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WireHistoryInfo" }, "WIRE_MESSAGE"));
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return paramWireHistoryInfo;
  }
  
  private static void a(WireHistoryInfo paramWireHistoryInfo, FFSResultSet paramFFSResultSet)
    throws Exception
  {
    String str1 = null;
    String str2 = null;
    DBUtil.populateBaseInfo(paramFFSResultSet, paramWireHistoryInfo);
    if ((paramWireHistoryInfo.getTransType().equals("RECURRING")) || (paramWireHistoryInfo.getTransType().equals("RECTEMPLATE")))
    {
      paramWireHistoryInfo.setId(paramFFSResultSet.getColumnString("RecSrvrTID"));
      paramWireHistoryInfo.setRecId(paramFFSResultSet.getColumnString("RecSrvrTID"));
      paramWireHistoryInfo.setBusinessId(paramFFSResultSet.getColumnString("RecSrvrTID"));
      paramWireHistoryInfo.setAmount(paramFFSResultSet.getColumnString("Amount"));
      paramWireHistoryInfo.setDestination(paramFFSResultSet.getColumnString("WireDest"));
      paramWireHistoryInfo.setDate(paramFFSResultSet.getColumnString("StartDate"));
      paramWireHistoryInfo.setStatus(paramFFSResultSet.getColumnString("Status"));
      paramWireHistoryInfo.setPayeeId(paramFFSResultSet.getColumnString("WirePayeeID"));
      paramWireHistoryInfo.setExtId(paramFFSResultSet.getColumnString("ExtId"));
      paramWireHistoryInfo.setSource(paramFFSResultSet.getColumnString("WireSource"));
      paramWireHistoryInfo.setUserId(paramFFSResultSet.getColumnString("UserId"));
      paramWireHistoryInfo.setTemplateId(paramFFSResultSet.getColumnString("TemplateId"));
      paramWireHistoryInfo.setWireName(paramFFSResultSet.getColumnString("WireName"));
      str1 = paramFFSResultSet.getColumnString("AcctDebitID");
      str2 = paramFFSResultSet.getColumnString("AcctDebitType");
      paramWireHistoryInfo.setFromAcctNum(str1);
      paramWireHistoryInfo.setFromAcctType(str2);
      paramWireHistoryInfo.buildFromAcctId();
    }
    else if ((paramWireHistoryInfo.getTransType().equals("SINGLE")) || (paramWireHistoryInfo.getTransType().equals("TEMPLATE")) || (paramWireHistoryInfo.getTransType().equals("BATCHWIRE")))
    {
      paramWireHistoryInfo.setId(paramFFSResultSet.getColumnString("SrvrTID"));
      paramWireHistoryInfo.setRecId(paramFFSResultSet.getColumnString("RecSrvrTID"));
      paramWireHistoryInfo.setBusinessId(paramFFSResultSet.getColumnString("CustomerID"));
      paramWireHistoryInfo.setAmount(paramFFSResultSet.getColumnString("Amount"));
      paramWireHistoryInfo.setDestination(paramFFSResultSet.getColumnString("WireDest"));
      paramWireHistoryInfo.setDate(paramFFSResultSet.getColumnString("DateToPost"));
      paramWireHistoryInfo.setStatus(paramFFSResultSet.getColumnString("Status"));
      paramWireHistoryInfo.setPayeeId(paramFFSResultSet.getColumnString("WirePayeeID"));
      paramWireHistoryInfo.setExtId(paramFFSResultSet.getColumnString("ExtId"));
      paramWireHistoryInfo.setSource(paramFFSResultSet.getColumnString("WireSource"));
      paramWireHistoryInfo.setUserId(paramFFSResultSet.getColumnString("UserId"));
      paramWireHistoryInfo.setTemplateId(paramFFSResultSet.getColumnString("TemplateId"));
      paramWireHistoryInfo.setWireName(paramFFSResultSet.getColumnString("WireName"));
      str1 = paramFFSResultSet.getColumnString("AcctDebitID");
      str2 = paramFFSResultSet.getColumnString("AcctDebitType");
      paramWireHistoryInfo.setFromAcctNum(str1);
      paramWireHistoryInfo.setFromAcctType(str2);
      paramWireHistoryInfo.buildFromAcctId();
    }
    else
    {
      paramWireHistoryInfo.setId(paramFFSResultSet.getColumnString("BatchId"));
      paramWireHistoryInfo.setBusinessId(paramFFSResultSet.getColumnString("CustomerId"));
      paramWireHistoryInfo.setAmount(paramFFSResultSet.getColumnString("TotalAmount"));
      paramWireHistoryInfo.setDestination(paramFFSResultSet.getColumnString("BatchDest"));
      paramWireHistoryInfo.setDate(paramFFSResultSet.getColumnString("DateToPost"));
      paramWireHistoryInfo.setStatus(paramFFSResultSet.getColumnString("BatchStatus"));
      paramWireHistoryInfo.setExtId(paramFFSResultSet.getColumnString("ExtId"));
      paramWireHistoryInfo.setUserId(paramFFSResultSet.getColumnString("UserId"));
    }
  }
  
  private static void a(WireHistoryInfo paramWireHistoryInfo, WireBatchInfo paramWireBatchInfo)
    throws Exception
  {
    paramWireHistoryInfo.setId(paramWireBatchInfo.getBatchId());
    paramWireHistoryInfo.setBusinessId(paramWireBatchInfo.getCustomerId());
    paramWireHistoryInfo.setAmount(paramWireBatchInfo.getTotalAmount());
    paramWireHistoryInfo.setDestination(paramWireBatchInfo.getBatchDest());
    paramWireHistoryInfo.setDate(paramWireBatchInfo.getDateToPost());
    paramWireHistoryInfo.setStatus(paramWireBatchInfo.getPrcStatus());
    paramWireHistoryInfo.setExtId(paramWireBatchInfo.getExtId());
    paramWireHistoryInfo.setUserId(paramWireBatchInfo.getUserId());
    paramWireHistoryInfo.setWireObject(paramWireBatchInfo);
    paramWireHistoryInfo.setVersion(paramWireBatchInfo.getVersion());
  }
  
  private static void a(WireHistoryInfo paramWireHistoryInfo, RecWireInfo paramRecWireInfo)
    throws Exception
  {
    paramWireHistoryInfo.setId(paramRecWireInfo.getSrvrTid());
    paramWireHistoryInfo.setRecId(paramRecWireInfo.getRecSrvrTid());
    paramWireHistoryInfo.setBusinessId(paramRecWireInfo.getCustomerID());
    paramWireHistoryInfo.setAmount(paramRecWireInfo.getAmount());
    paramWireHistoryInfo.setDestination(paramRecWireInfo.getWireDest());
    paramWireHistoryInfo.setDate(paramRecWireInfo.getStartDate());
    paramWireHistoryInfo.setStatus(paramRecWireInfo.getPrcStatus());
    paramWireHistoryInfo.setPayeeId(paramRecWireInfo.getWirePayeeId());
    paramWireHistoryInfo.setExtId(paramRecWireInfo.getExtId());
    paramWireHistoryInfo.setSource(paramRecWireInfo.getWireSource());
    paramWireHistoryInfo.setUserId(paramRecWireInfo.getUserId());
    paramWireHistoryInfo.setTemplateId(paramRecWireInfo.getTemplateId());
    paramWireHistoryInfo.setWireName(paramRecWireInfo.getWireName());
    String str1 = paramRecWireInfo.getFromAcctId();
    String str2 = paramRecWireInfo.getFromAcctType();
    paramWireHistoryInfo.setFromAcctNum(str1);
    paramWireHistoryInfo.setFromAcctType(str2);
    paramWireHistoryInfo.buildFromAcctId();
    paramWireHistoryInfo.setPayeeInfo(paramRecWireInfo.getWirePayeeInfo());
    paramWireHistoryInfo.setVersion(paramRecWireInfo.getVersion());
    paramWireHistoryInfo.setWireObject(paramRecWireInfo);
  }
  
  private static void a(WireHistoryInfo paramWireHistoryInfo, WireInfo paramWireInfo)
    throws Exception
  {
    paramWireHistoryInfo.setId(paramWireInfo.getSrvrTid());
    paramWireHistoryInfo.setRecId(paramWireInfo.getRecSrvrTid());
    paramWireHistoryInfo.setBusinessId(paramWireInfo.getCustomerID());
    paramWireHistoryInfo.setAmount(paramWireInfo.getAmount());
    paramWireHistoryInfo.setDestination(paramWireInfo.getWireDest());
    paramWireHistoryInfo.setDate(paramWireInfo.getDateToPost());
    paramWireHistoryInfo.setStatus(paramWireInfo.getPrcStatus());
    paramWireHistoryInfo.setPayeeId(paramWireInfo.getWirePayeeId());
    paramWireHistoryInfo.setExtId(paramWireInfo.getExtId());
    paramWireHistoryInfo.setSource(paramWireInfo.getWireSource());
    paramWireHistoryInfo.setUserId(paramWireInfo.getUserId());
    paramWireHistoryInfo.setTemplateId(paramWireInfo.getTemplateId());
    paramWireHistoryInfo.setWireName(paramWireInfo.getWireName());
    String str1 = paramWireInfo.getFromAcctId();
    String str2 = paramWireInfo.getFromAcctType();
    paramWireHistoryInfo.setFromAcctNum(str1);
    paramWireHistoryInfo.setFromAcctType(str2);
    paramWireHistoryInfo.buildFromAcctId();
    paramWireHistoryInfo.setPayeeInfo(paramWireInfo.getWirePayeeInfo());
    paramWireHistoryInfo.setWireObject(paramWireInfo);
    paramWireHistoryInfo.setVersion(paramWireInfo.getVersion());
  }
  
  public static int cleanup(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Wire.cleanup ";
    FFSDebug.log(str1 + " start...", 6);
    int i = 1;
    if ((paramString != null) && (paramString.trim().length() != 0)) {
      i = 0;
    } else {
      i = 1;
    }
    String str2 = null;
    String str3 = null;
    int j = 0;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd00");
    String str4 = localSimpleDateFormat.format(localCalendar.getTime());
    int k = Integer.parseInt(str4);
    Object[] arrayOfObject1 = null;
    if (i != 0) {
      arrayOfObject1 = new Object[] { String.valueOf(k) };
    } else {
      arrayOfObject1 = new Object[] { String.valueOf(k), paramString };
    }
    Object[] arrayOfObject2 = null;
    FFSDebug.log(str1, " customerId=", paramString, " iAgeDate=" + k, 6);
    try
    {
      if (paramBoolean)
      {
        if (i != 0)
        {
          str2 = "DELETE FROM BPW_RecWireInfo WHERE EndDate <= ?  AND Status = 'POSTEDON' ";
          str3 = "DELETE FROM SCH_EventInfoLog  WHERE LogDate <= ?  AND InstructionType = ?  AND InstructionID IN (SELECT RecSrvrTID FROM BPW_RecWireInfo     WHERE EndDate <= ? ) ";
          arrayOfObject2 = new Object[] { new Integer(k), "RECWIRETRN", String.valueOf(k) };
        }
        else
        {
          str2 = "DELETE FROM BPW_RecWireInfo WHERE EndDate <= ? AND CustomerID = ?  AND Status = 'POSTEDON' ";
          str3 = "DELETE FROM SCH_EventInfoLog  WHERE LogDate <= ?  AND InstructionType = ?  AND InstructionID IN (SELECT RecSrvrTID FROM BPW_RecWireInfo     WHERE EndDate <= ? AND CustomerID = ? ) ";
          arrayOfObject2 = new Object[] { new Integer(k), "RECWIRETRN", String.valueOf(k), paramString };
        }
      }
      else if (i != 0)
      {
        str2 = "DELETE FROM BPW_WireInfo WHERE DateToPost <= ?  AND Status IN ('COMPLETED', 'POSTEDON') ";
        str3 = "DELETE FROM SCH_EventInfoLog  WHERE LogDate <= ?  AND InstructionID IN (SELECT SrvrTID FROM BPW_WireInfo     WHERE DateToPost <= ? )  AND InstructionType IN ('WIREAPPROVALTRN',  'WIREAPPROVALRSLTTRN', 'WIRETRN', 'WIRERSLTTRN') ";
        arrayOfObject2 = new Object[] { new Integer(k), String.valueOf(k) };
      }
      else
      {
        str2 = "DELETE FROM BPW_WireInfo WHERE DateToPost <= ? AND CustomerID = ?  AND Status IN ('COMPLETED', 'POSTEDON') ";
        str3 = "DELETE FROM SCH_EventInfoLog  WHERE LogDate <= ?  AND InstructionID IN (SELECT SrvrTID FROM BPW_WireInfo     WHERE DateToPost <= ? AND CustomerID = ? )  AND InstructionType IN ('WIREAPPROVALTRN',  'WIREAPPROVALRSLTTRN', 'WIRETRN', 'WIRERSLTTRN') ";
        arrayOfObject2 = new Object[] { new Integer(k), String.valueOf(k), paramString };
      }
      j = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject2);
      j += DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject1);
    }
    catch (FFSException localFFSException)
    {
      str5 = "*** " + str1 + " failed: ";
      str6 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str5, str6, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str5 = "***  " + str1 + ": failed:";
      String str6 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str6, 0);
      throw new FFSException(localException, str5);
    }
    FFSDebug.log(str1 + "end, No of rows deleted = " + j, 6);
    return j;
  }
  
  public static WireInfo populateCustomerAndFIInfo(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    String str = "Wire.populateCustomerAndFIInfo: ";
    CustomerInfo localCustomerInfo = Customer.getCustomerInfo(paramWireInfo.getCustomerID(), paramFFSConnectionHolder, paramWireInfo);
    paramWireInfo.setCustomerInfo(localCustomerInfo);
    FFSDebug.log(str, "customerInfo:" + paramWireInfo.getCustomerInfo(), 6);
    BPWFIInfo localBPWFIInfo = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, paramWireInfo.getFiID());
    paramWireInfo.setFiInfo(localBPWFIInfo);
    FFSDebug.log(str, "bpwFiInfo:" + paramWireInfo.getFiInfo(), 6);
    return paramWireInfo;
  }
  
  public static int updatePayeeOfCompletedWires(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "Wire.updatePayeeOfCompletedWires: ";
    FFSDebug.log(str1, "start", 6);
    StringBuffer localStringBuffer = null;
    if (paramFFSConnectionHolder == null)
    {
      localObject = "FFSConnectionHolder object  is null";
      FFSDebug.log(str1, (String)localObject, 0);
      return 16000;
    }
    Object localObject = { paramString2, paramString1 };
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("UPDATE BPW_WireInfo SET WirePayeeID=? WHERE WirePayeeID = ? ");
    localStringBuffer.append(" AND Status not in ('");
    localStringBuffer.append("CREATED").append("', '");
    localStringBuffer.append("WILLPROCESSON").append("', '");
    localStringBuffer.append("RELEASEPENDING").append("', '");
    localStringBuffer.append("TEMPLATE").append("', '");
    localStringBuffer.append("APPROVAL_PENDING").append("', '");
    localStringBuffer.append("APPROVAL_REJECTED").append("')");
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject);
      FFSDebug.log(str1, "done", 6);
      return i;
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** Wire.updatePayeeOfCompletedWires failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  public static BPWHist getWireInfoByStatusPayee(FFSConnectionHolder paramFFSConnectionHolder, BPWHist paramBPWHist, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "Wire.getWireInfoByStatusPayee: ";
    FFSDebug.log(str1, "start", 6);
    StringBuffer localStringBuffer = null;
    String str2 = null;
    Hashtable localHashtable = null;
    ArrayList localArrayList = null;
    Object[] arrayOfObject = null;
    String str3 = null;
    String[] arrayOfString = null;
    Object localObject1 = null;
    FFSResultSet localFFSResultSet = null;
    WirePayeeInfo localWirePayeeInfo1 = null;
    WirePayeeInfo localWirePayeeInfo2 = null;
    str3 = paramBPWHist.getPayeeId();
    arrayOfString = paramBPWHist.getStatusList();
    localStringBuffer = new StringBuffer();
    if (paramBoolean)
    {
      localStringBuffer.append("SELECT a.RecSrvrTID, BankFromID, BranchFromID, CustomerID,WirePayeeID, FIID, Amount, AcctDebitID,AcctDebitType,AcctDebitKey, WireType,WireCategory,WireGroup,WireDest,Status,LogID,PayInstruct,Memo,AmtCurrency,DestCurrency,XferFee, StartAmount,EndAmount,DateCreate,Frequency,StartDate,EndDate,InstanceCount,SubmittedBy, ExchangeRate, BatchId, ContractNumber, ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,AgentId,AgentName,Method, ProcessedBy, TemplateId,CustomerRef,OriginatorCharges,ReceiverCharges, WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum, UserId,AgentType,OrigAmount,OrigCurrency,WireCreditID, ByOrderOfName, ByOrderOfAddr1, ByOrderOfAddr2, ByOrderOfAddr3, ByOrderOfAcct, b.Version  FROM BPW_RecWireInfo a, BPW_RecWireInfo2 b  WHERE a.RecSrvrTID = b.RecSrvrTID AND WirePayeeID = ?");
      str2 = "RECWIRE";
      localObject1 = new RecWireInfo();
    }
    else
    {
      localStringBuffer.append("SELECT a.SrvrTID,BankFromID,BranchFromID,CustomerID,WirePayeeID,RecSrvrTID,FIID,Amount,AmtCurrency,DestCurrency,XferFee,AcctDebitID, AcctDebitType,AcctDebitKey,DateCreate,DateDue,DateToPost,DatePosted, WireType,WireCategory,WireGroup,WireDest, BatchId, ContractNumber, Status,LogID,ConfirmNum, ConfirmNum2, ConfirmMsg, PayInstruct, Memo,ExchangeRate,SubmittedBy,ExtId,WireSource,WireName,NickName,WireLimit, SettlementDate,WireScope,MathRule,OrigToBeneficiaryMemo, OrigToBeneficiaryInfo,BanktoBankInfo,ProcessedBy,TemplateId,AgentId, AgentName,Method,AmountType,CustomerRef,OriginatorCharges,ReceiverCharges, WireChargesDetails,OrgChargesAccountNum,BenefChargesAccountNum,HostId, UserId,AgentType,OrigAmount,OrigCurrency, WireCreditID, ByOrderOfName, ByOrderOfAddr1, ByOrderOfAddr2, ByOrderOfAddr3, ByOrderOfAcct, b.Version  FROM BPW_WireInfo a, BPW_WireInfo2 b  WHERE a.SrvrTID = b.SrvrTID AND WirePayeeID = ?");
      str2 = "WIRE";
      localObject1 = new WireInfo();
    }
    FFSDebug.log(str1, " wirePayeeId=", str3, 6);
    arrayOfObject = new Object[1 + arrayOfString.length];
    arrayOfObject[0] = str3;
    if ((arrayOfString != null) && (arrayOfString.length > 0))
    {
      localStringBuffer.append(" AND Status IN (");
      localStringBuffer.append("?");
      arrayOfObject[1] = arrayOfString[0];
      for (int i = 1; i < arrayOfString.length; i++)
      {
        localStringBuffer.append(", ?");
        arrayOfObject[(i + 1)] = arrayOfString[i];
      }
      localStringBuffer.append(")");
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      localArrayList = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        localObject1 = new WireInfo();
        a((WireInfo)localObject1, localFFSResultSet, paramBoolean);
        localArrayList.add(localObject1);
        FFSDebug.log(str1, "XtraInfo:FIID:", ((WireInfo)localObject1).getFiID(), "SrvrTid:", ((WireInfo)localObject1).getSrvrTid(), 6);
        localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, ((WireInfo)localObject1).getFiID(), ((WireInfo)localObject1).getSrvrTid(), str2);
        ((WireInfo)localObject1).setExtInfo(localHashtable);
        FFSDebug.log(str1, "XtraInfo:" + localHashtable, 6);
        BPWHist localBPWHist;
        if (((WireInfo)localObject1).getWirePayeeId() != null)
        {
          localWirePayeeInfo1 = WirePayee.getWirePayeeInfo(paramFFSConnectionHolder, ((WireInfo)localObject1).getWirePayeeId(), false);
          if (localWirePayeeInfo1.getStatusCode() != 0)
          {
            paramBPWHist.setStatusCode(localWirePayeeInfo1.getStatusCode());
            paramBPWHist.setStatusMsg(localWirePayeeInfo1.getStatusMsg());
            localBPWHist = paramBPWHist;
            return localBPWHist;
          }
          ((WireInfo)localObject1).setWirePayeeInfo(localWirePayeeInfo1);
        }
        if (((WireInfo)localObject1).getWireCreditId() != null)
        {
          localWirePayeeInfo2 = WirePayee.getWirePayeeInfo(paramFFSConnectionHolder, ((WireInfo)localObject1).getWireCreditId(), false);
          if (localWirePayeeInfo2.getStatusCode() != 0)
          {
            paramBPWHist.setStatusCode(localWirePayeeInfo2.getStatusCode());
            paramBPWHist.setStatusMsg(localWirePayeeInfo2.getStatusMsg());
            localBPWHist = paramBPWHist;
            return localBPWHist;
          }
          ((WireInfo)localObject1).setWireCreditInfo(localWirePayeeInfo2);
        }
        ((WireInfo)localObject1).setStatusCode(0);
        ((WireInfo)localObject1).setStatusMsg("Success");
      }
      if (localArrayList.size() == 0)
      {
        paramBPWHist.setStatusCode(16020);
        paramBPWHist.setStatusMsg(" record not found");
      }
      else
      {
        paramBPWHist.setTrans((WireInfo[])localArrayList.toArray(new WireInfo[0]));
        paramBPWHist.setStatusCode(0);
        paramBPWHist.setStatusMsg("Success");
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str6 = str1 + " failed: ";
        String str7 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str6, str7, 0);
      }
    }
    FFSDebug.log(str1, "Done", 6);
    return paramBPWHist;
  }
  
  public static BPWHist getDuplicateWires(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    String str1 = "Wire.getDuplicateWires: ";
    FFSDebug.log(str1, "start", 6);
    StringBuffer localStringBuffer = null;
    Hashtable localHashtable = null;
    Object[] arrayOfObject = null;
    WireInfo localWireInfo = null;
    FFSResultSet localFFSResultSet = null;
    WirePayeeInfo localWirePayeeInfo1 = null;
    WirePayeeInfo localWirePayeeInfo2 = null;
    ArrayList localArrayList1 = null;
    ArrayList localArrayList2 = null;
    FFSDebug.log(str1, "wireInfo: ", paramWireInfo.toString(), 6);
    BPWHist localBPWHist1 = new BPWHist();
    if (paramWireInfo == null)
    {
      localObject1 = "WireInfo parameter  is null";
      FFSDebug.log(str1, (String)localObject1, 0);
      localBPWHist1.setStatusCode(16000);
      localBPWHist1.setStatusMsg((String)localObject1);
      return localBPWHist1;
    }
    localArrayList1 = new ArrayList();
    localArrayList2 = new ArrayList();
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT a.SrvrTID, a.BankFromID,a.BranchFromID,a.CustomerID,a.WirePayeeID,a.RecSrvrTID,a.FIID,a.Amount,a.AmtCurrency,a.DestCurrency,a.XferFee,a.AcctDebitID, a.AcctDebitType,a.AcctDebitKey,a.DateCreate,a.DateDue,a.DateToPost,a.DatePosted, a.WireType,a.WireCategory,a.WireGroup,a.WireDest, a.BatchId, a.ContractNumber, a.Status,a.LogID,a.ConfirmNum, a.ConfirmNum2, a.ConfirmMsg, a.PayInstruct, a.Memo,a.ExchangeRate,a.SubmittedBy,a.ExtId,a.WireSource,a.WireName,a.NickName, a.WireLimit, a.SettlementDate,a.WireScope,a.MathRule,a.OrigToBeneficiaryMemo, a.OrigToBeneficiaryInfo,a.BanktoBankInfo,a.ProcessedBy,a.TemplateId,a.AgentId, a.AgentName,a.Method,a.AmountType,a.CustomerRef,a.OriginatorCharges,a.ReceiverCharges, a.WireChargesDetails,a.OrgChargesAccountNum,a.BenefChargesAccountNum,a.HostId, a.UserId,a.AgentType,a.OrigAmount,a.OrigCurrency, a.WireCreditID, b.ByOrderOfName, b.ByOrderOfAddr1, b.ByOrderOfAddr2, b.ByOrderOfAddr3, b.ByOrderOfAcct, b.Version  FROM BPW_WireInfo a, BPW_WireInfo2 b ");
    if (!aC(paramWireInfo.getWirePayeeId()))
    {
      localStringBuffer.append(" WHERE a.SrvrTID = b.SrvrTID ");
      localStringBuffer.append(" AND a.WirePayeeID = ? ");
      localArrayList2.add(paramWireInfo.getWirePayeeId());
    }
    else if (paramWireInfo.getWirePayeeInfo() != null)
    {
      localObject1 = paramWireInfo.getWirePayeeInfo();
      if (!aC(((WirePayeeInfo)localObject1).getBeneficiaryBankId()))
      {
        localStringBuffer.append(", ").append("BPW_WirePayee").append(" c ");
        localStringBuffer.append(" WHERE a.SrvrTID = b.SrvrTID ");
        localStringBuffer.append("  AND a.WirePayeeID = c.PayeeId ");
        localStringBuffer.append(" AND c.BeneficiaryBankId = ? ");
        localArrayList2.add(((WirePayeeInfo)localObject1).getBeneficiaryBankId());
        localStringBuffer.append(" AND c.BankAcctId = ? ");
        localArrayList2.add(((WirePayeeInfo)localObject1).getBankAcctId());
      }
      else if (((WirePayeeInfo)localObject1).getBeneficiaryBankInfo() != null)
      {
        localStringBuffer.append(", ").append("BPW_WirePayee").append(" c ");
        localStringBuffer.append(", ").append("BPW_WireBank").append(" d ");
        localStringBuffer.append(" WHERE a.SrvrTID = b.SrvrTID ");
        localStringBuffer.append("  AND a.WirePayeeID = c.PayeeId ");
        localStringBuffer.append("  AND c.BeneficiaryBankId = d.BankId ");
        localObject2 = ((WirePayeeInfo)localObject1).getBeneficiaryBankInfo();
        if (((BPWBankInfo)localObject2).getFedRTN() != null)
        {
          localStringBuffer.append(" AND d.FedRTN = ? ");
          localArrayList2.add(((BPWBankInfo)localObject2).getFedRTN());
        }
        if (((BPWBankInfo)localObject2).getChipsRTN() != null)
        {
          localStringBuffer.append(" AND d.ChipsRTN = ? ");
          localArrayList2.add(((BPWBankInfo)localObject2).getChipsRTN());
        }
        if (((BPWBankInfo)localObject2).getSwiftRTN() != null)
        {
          localStringBuffer.append(" AND d.SwiftRTN = ? ");
          localArrayList2.add(((BPWBankInfo)localObject2).getSwiftRTN());
        }
        if (((BPWBankInfo)localObject2).getOtherRTN() != null)
        {
          localStringBuffer.append(" AND d.OtherRTN = ? ");
          localArrayList2.add(((BPWBankInfo)localObject2).getOtherRTN());
        }
        localStringBuffer.append(" AND c.BankAcctId = ? ");
        localArrayList2.add(((WirePayeeInfo)localObject1).getBankAcctId());
      }
    }
    else
    {
      localStringBuffer.append(" WHERE a.SrvrTID = b.SrvrTID ");
    }
    if (!aC(paramWireInfo.getCustomerID()))
    {
      localStringBuffer.append(" AND a.CustomerID = ? ");
      localArrayList2.add(paramWireInfo.getCustomerID());
    }
    Object localObject1 = paramWireInfo.getDateToPost();
    if (!aC(paramWireInfo.getDateToPost()))
    {
      if (((String)localObject1).length() == 8) {
        localObject1 = (String)localObject1 + "00";
      }
      FFSDebug.log(str1, "dateToPost = " + (String)localObject1, 6);
      localStringBuffer.append(" AND a.DateToPost = ? ");
      localArrayList2.add(localObject1);
    }
    if (!aC(paramWireInfo.getWireDest()))
    {
      localStringBuffer.append(" AND a.WireDest = ? ");
      localArrayList2.add(paramWireInfo.getWireDest());
    }
    if (!aC(paramWireInfo.getOrigAmount()))
    {
      localStringBuffer.append(" AND a.OrigAmount = ? ");
      localArrayList2.add(paramWireInfo.getOrigAmount());
    }
    if (!aC(paramWireInfo.getOrigCurrency()))
    {
      localStringBuffer.append(" AND a.OrigCurrency = ? ");
      localArrayList2.add(paramWireInfo.getOrigCurrency());
    }
    Object localObject2 = paramWireInfo.getWireType();
    if (!aC((String)localObject2))
    {
      localStringBuffer.append(" AND a.WireType = ? ");
      localArrayList2.add(localObject2);
    }
    else
    {
      localStringBuffer.append(" AND a.WireType <> ? ");
      localArrayList2.add("TEMPLATE");
    }
    if (!aC(paramWireInfo.getExtId()))
    {
      localStringBuffer.append(" AND a.ExtId <> ? ");
      localArrayList2.add(paramWireInfo.getExtId());
    }
    localStringBuffer.append(" AND a.RecSrvrTID is NULL ");
    localStringBuffer.append(" AND a.Status <> ?");
    localArrayList2.add("CANCELEDON");
    try
    {
      arrayOfObject = localArrayList2.toArray(new Object[0]);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localWireInfo = new WireInfo();
        a(localWireInfo, localFFSResultSet, false);
        localArrayList1.add(localWireInfo);
        FFSDebug.log(str1, "XtraInfo:FIID:", localWireInfo.getFiID(), "SrvrTid:", localWireInfo.getSrvrTid(), 6);
        localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, localWireInfo.getFiID(), localWireInfo.getSrvrTid(), "WIRE");
        localWireInfo.setExtInfo(localHashtable);
        FFSDebug.log(str1, "XtraInfo:" + localHashtable, 6);
        BPWHist localBPWHist2;
        if (localWireInfo.getWirePayeeId() != null)
        {
          localWirePayeeInfo1 = WirePayee.getWirePayeeInfo(paramFFSConnectionHolder, localWireInfo.getWirePayeeId(), false);
          if (localWirePayeeInfo1.getStatusCode() != 0)
          {
            localBPWHist1.setStatusCode(localWirePayeeInfo1.getStatusCode());
            localBPWHist1.setStatusMsg(localWirePayeeInfo1.getStatusMsg());
            localBPWHist2 = localBPWHist1;
            return localBPWHist2;
          }
          localWireInfo.setWirePayeeInfo(localWirePayeeInfo1);
        }
        if (localWireInfo.getWireCreditId() != null)
        {
          localWirePayeeInfo2 = WirePayee.getWirePayeeInfo(paramFFSConnectionHolder, localWireInfo.getWireCreditId(), false);
          if (localWirePayeeInfo2.getStatusCode() != 0)
          {
            localBPWHist1.setStatusCode(localWirePayeeInfo2.getStatusCode());
            localBPWHist1.setStatusMsg(localWirePayeeInfo2.getStatusMsg());
            localBPWHist2 = localBPWHist1;
            return localBPWHist2;
          }
          localWireInfo.setWireCreditInfo(localWirePayeeInfo2);
        }
        localWireInfo.setStatusCode(0);
        localWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      if (localArrayList1.size() > 0)
      {
        localBPWHist1.setTrans(localArrayList1.toArray(new WireInfo[0]));
        localBPWHist1.setStatusCode(0);
        localBPWHist1.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "WIRE_MESSAGE"));
      }
      else
      {
        localBPWHist1.setStatusCode(16020);
        localBPWHist1.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "WireInfo" }, "WIRE_MESSAGE"));
      }
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str4 = str1 + " failed: ";
        String str5 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str4, str5, 0);
      }
    }
    FFSDebug.log(str1, ": dupWireList.size = " + localArrayList1.size(), 6);
    FFSDebug.log(str1, ": done", 6);
    return localBPWHist1;
  }
  
  public static void updateBatchStatusFromSingleWire(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    String str1 = "Wire.updateBatchStatusFromSingleWire: ";
    FFSDebug.log(str1, "start", 6);
    String str2 = paramWireInfo.getBatchId();
    if ((str2 == null) || (str2.trim().length() == 0))
    {
      FFSDebug.log(str1, "Wire doesn't belong to a Batch.", 6);
      FFSDebug.log(str1, "done", 6);
      return;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "FFSConnectionHolder" }, "WIRE_MESSAGE");
      FFSDebug.log(str1, (String)localObject1, 0);
      return;
    }
    Object localObject1 = null;
    String str3 = null;
    FFSResultSet localFFSResultSet = null;
    String str4 = "SELECT BatchStatus, WireCount FROM BPW_WireBatch WHERE BatchId = ?";
    Object[] arrayOfObject = { str2 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str4, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localObject1 = localFFSResultSet.getColumnString(1);
        str3 = localFFSResultSet.getColumnString(2);
      }
    }
    catch (Throwable localThrowable1)
    {
      localObject2 = str1 + "failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject2 + str6, 0);
      throw new FFSException(localThrowable1, (String)localObject2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str9 = str1 + "failed: ";
        String str10 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str9 + str10, 0);
      }
    }
    if ((localObject1 == null) || (str3 == null))
    {
      FFSDebug.log(str1, "Unable to retrieve batchStatus and wireCount of batch with Id: " + str2, 6);
      FFSDebug.log(str1, "done", 6);
      return;
    }
    String str5 = paramWireInfo.getPrcStatus();
    Object localObject2 = null;
    if (str3.equals("1"))
    {
      localObject1 = str5;
    }
    else if (((String)localObject1).equalsIgnoreCase("MIXED"))
    {
      localObject2 = new WireBatchInfo();
      ((WireBatchInfo)localObject2).setBatchId(str2);
      localObject2 = getWiresByBatchId(paramFFSConnectionHolder, (WireBatchInfo)localObject2, false);
      localObject1 = jdMethod_int((WireBatchInfo)localObject2);
    }
    else if (!((String)localObject1).equalsIgnoreCase(str5))
    {
      localObject1 = "MIXED";
    }
    arrayOfObject = new Object[] { localObject1, str2 };
    str4 = "UPDATE BPW_WireBatch set BatchStatus = ?, Version=Version+1  WHERE BatchId = ? ";
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str4, arrayOfObject, (BPWInfoBase)localObject2, "BatchId", ((WireBatchInfo)localObject2).getBatchId());
      if (i == 0)
      {
        str7 = "failed, 16020 BatchId:" + str2;
        FFSDebug.log(str1, str7, 0);
        return;
      }
    }
    catch (Throwable localThrowable2)
    {
      String str7 = str1 + "failed: ";
      String str8 = FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str7 + str8, 0);
      throw new FFSException(localThrowable2, str7);
    }
    FFSDebug.log(str1, "done", 6);
  }
  
  private static boolean aC(String paramString)
  {
    return (paramString == null) || (paramString.trim().length() == 0);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.Wire
 * JD-Core Version:    0.7.0.1
 */