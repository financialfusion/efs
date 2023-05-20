package com.ffusion.services.banksim2;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.bcreport.ReportLogRecord;
import com.ffusion.beans.bcreport.ReportLogRecords;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.user.User;
import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireBatches;
import com.ffusion.beans.wiretransfers.WireHistories;
import com.ffusion.beans.wiretransfers.WireHistory;
import com.ffusion.beans.wiretransfers.WireStatusMap;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransferPayees;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Reporting;
import com.ffusion.csil.core.WireUtil;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.WireAdapter;
import com.ffusion.ffs.bpw.BPWServices.BPWServices;
import com.ffusion.ffs.bpw.BPWServices.BPWServicesHome;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.BPWInfoBase;
import com.ffusion.ffs.bpw.interfaces.BPWPayeeList;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.RecWireInfo;
import com.ffusion.ffs.bpw.interfaces.SortCriterion;
import com.ffusion.ffs.bpw.interfaces.WireBatchInfo;
import com.ffusion.ffs.bpw.interfaces.WireHistoryInfo;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.services.WireTransferService4;
import com.ffusion.util.ArrayUtil;
import com.ffusion.util.ContextPool;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.TrackingIDGenerator;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

public class WireTransferService
  extends Base
  implements WireTransferService4
{
  private static final String cC = "BPWCallBackJNDIName";
  private String cG = "BPWServices";
  private final int cH = 10;
  private static final int cQ = 0;
  private static final int cF = 1;
  private static final int cM = 2;
  private static final int cD = 3;
  private static final int cR = 4;
  private static final int cE = 5;
  private static final int cI = 6;
  private static final int cP = 7;
  private static final String cJ = "Statuses";
  private static final String cO = "TransSource";
  private static final String cL = "TemplateID";
  private static final String cK = "TransScope";
  private static final String cN = "AllWires";
  
  public int initialize(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = "banksim.xml";
    }
    return initialize(paramString, new a());
  }
  
  public WireTransfer addWireTransfer(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    int i = -1;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      paramWireTransfer.setUserID(paramSecureUser.getProfileID());
      paramWireTransfer.setSubmittedBy(paramSecureUser.getProfileID());
      String str1 = "";
      if (paramWireTransfer.getDueDateValue() != null) {
        str1 = formatDate.format(paramWireTransfer.getDueDateValue().getTime());
      }
      String str2 = "";
      if (paramWireTransfer.getSettlementDateValue() != null) {
        str2 = formatDate.format(paramWireTransfer.getSettlementDateValue().getTime());
      }
      Object localObject1;
      if (paramWireTransfer.getType() == 6)
      {
        localObject1 = paramWireTransfer.getRecWireInfo();
        ((RecWireInfo)localObject1).setTrnId(TrackingIDGenerator.GetNextID());
        ((RecWireInfo)localObject1).setStartDate(str1);
        ((RecWireInfo)localObject1).setProcessedBy(paramSecureUser.getUserName());
        populateOBOAgentInfo(paramSecureUser, (BPWInfoBase)localObject1);
        DebugLog.log(Level.INFO, "*** BPW.AddWireInfo: Adding WireInfo = [" + ((RecWireInfo)localObject1).toString() + "]");
        localObject1 = localBPWServices.addRecWireTransfer((RecWireInfo)localObject1);
        i = a(paramWireTransfer, (WireInfo)localObject1, paramHashMap);
      }
      else
      {
        localObject1 = paramWireTransfer.getWireInfo();
        ((WireInfo)localObject1).setTrnId(TrackingIDGenerator.GetNextID());
        ((WireInfo)localObject1).setDateDue(str1);
        ((WireInfo)localObject1).setSettlementDate(str2);
        ((WireInfo)localObject1).setProcessedBy(paramSecureUser.getUserName());
        populateOBOAgentInfo(paramSecureUser, (BPWInfoBase)localObject1);
        DebugLog.log(Level.INFO, "*** BPW.AddWireInfo: Adding WireInfo = [" + ((WireInfo)localObject1).toString() + "]");
        localObject1 = localBPWServices.addWireTransfer((WireInfo)localObject1);
        i = a(paramWireTransfer, (WireInfo)localObject1, paramHashMap);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      int j = 31003;
      if ((paramWireTransfer.getWireType() != null) && ((paramWireTransfer.getWireType().equals("TEMPLATE")) || (paramWireTransfer.getWireType().equals("RECTEMPLATE")))) {
        j = 31011;
      }
      throw new CSILException(-1009, jdMethod_do(i, j));
    }
    return paramWireTransfer;
  }
  
  public void modifyWireTransfer(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    int i = -1;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      paramWireTransfer.setUserID(paramSecureUser.getProfileID());
      paramWireTransfer.setSubmittedBy(paramSecureUser.getProfileID());
      String str1 = "";
      if (paramWireTransfer.getDueDateValue() != null) {
        str1 = formatDate.format(paramWireTransfer.getDueDateValue().getTime());
      }
      String str2 = "";
      if (paramWireTransfer.getSettlementDateValue() != null) {
        str2 = formatDate.format(paramWireTransfer.getSettlementDateValue().getTime());
      }
      Object localObject1;
      if (paramWireTransfer.getType() == 6)
      {
        localObject1 = paramWireTransfer.getRecWireInfo();
        ((RecWireInfo)localObject1).setTrnId(TrackingIDGenerator.GetNextID());
        ((RecWireInfo)localObject1).setStartDate(str1);
        ((RecWireInfo)localObject1).setProcessedBy(paramSecureUser.getUserName());
        populateOBOAgentInfo(paramSecureUser, (BPWInfoBase)localObject1);
        DebugLog.log(Level.INFO, "*** BPW.ModifyWireInfo: Modifying WireInfo = [" + ((RecWireInfo)localObject1).toString() + "]");
        localObject1 = localBPWServices.modRecWireTransfer((RecWireInfo)localObject1);
        i = a(paramWireTransfer, (WireInfo)localObject1, paramHashMap);
      }
      else
      {
        localObject1 = paramWireTransfer.getWireInfo();
        ((WireInfo)localObject1).setTrnId(TrackingIDGenerator.GetNextID());
        ((WireInfo)localObject1).setDateDue(str1);
        ((WireInfo)localObject1).setSettlementDate(str2);
        ((WireInfo)localObject1).setProcessedBy(paramSecureUser.getUserName());
        populateOBOAgentInfo(paramSecureUser, (BPWInfoBase)localObject1);
        DebugLog.log(Level.INFO, "*** BPW.ModifyWireInfo: Modifying WireInfo = [" + ((WireInfo)localObject1).toString() + "]");
        localObject1 = localBPWServices.modWireTransfer((WireInfo)localObject1);
        i = a(paramWireTransfer, (WireInfo)localObject1, paramHashMap);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      int j = 31005;
      if ((paramWireTransfer.getWireType() != null) && ((paramWireTransfer.getWireType().equals("TEMPLATE")) || (paramWireTransfer.getWireType().equals("RECTEMPLATE")))) {
        j = 31012;
      }
      throw new CSILException(-1009, jdMethod_do(i, j));
    }
  }
  
  public void deleteWireTransfer(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    int i = -1;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      paramWireTransfer.setUserID(paramSecureUser.getProfileID());
      paramWireTransfer.setSubmittedBy(paramSecureUser.getProfileID());
      Object localObject1;
      if (paramWireTransfer.getType() == 6)
      {
        localObject1 = paramWireTransfer.getRecWireInfo();
        ((RecWireInfo)localObject1).setTrnId(TrackingIDGenerator.GetNextID());
        ((RecWireInfo)localObject1).setProcessedBy(paramSecureUser.getUserName());
        populateOBOAgentInfo(paramSecureUser, (BPWInfoBase)localObject1);
        localObject1 = localBPWServices.cancRecWireTransfer((RecWireInfo)localObject1);
        i = a(paramWireTransfer, (WireInfo)localObject1, paramHashMap);
      }
      else
      {
        localObject1 = paramWireTransfer.getWireInfo();
        ((WireInfo)localObject1).setTrnId(TrackingIDGenerator.GetNextID());
        ((WireInfo)localObject1).setProcessedBy(paramSecureUser.getUserName());
        populateOBOAgentInfo(paramSecureUser, (BPWInfoBase)localObject1);
        localObject1 = localBPWServices.cancWireTransfer((WireInfo)localObject1);
        i = a(paramWireTransfer, (WireInfo)localObject1, paramHashMap);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      if ((paramWireTransfer.getWireType() != null) && ((paramWireTransfer.getWireType().equals("TEMPLATE")) || (paramWireTransfer.getWireType().equals("RECTEMPLATE"))))
      {
        i = jdMethod_do(i, 31013);
        throw new CSILException(-1009, i);
      }
      i = jdMethod_do(i, 31007);
      throw new CSILException(-1009, i);
    }
  }
  
  public void deleteWireBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, HashMap paramHashMap)
    throws CSILException
  {
    int i = -1;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      paramWireBatch.setUserID(paramSecureUser.getProfileID());
      paramWireBatch.setSubmittedBy(paramSecureUser.getProfileID());
      WireBatchInfo localWireBatchInfo = paramWireBatch.getWireBatchInfo();
      localWireBatchInfo.setTrnId(TrackingIDGenerator.GetNextID());
      populateOBOAgentInfo(paramSecureUser, localWireBatchInfo);
      localWireBatchInfo = localBPWServices.canWireTransferBatch(localWireBatchInfo);
      i = a(paramWireBatch, localWireBatchInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      i = jdMethod_do(i, 31016);
      throw new CSILException(-1009, i);
    }
  }
  
  public WireTransfers getWireTransfers(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    WireTransfers localWireTransfers = new WireTransfers();
    return localWireTransfers;
  }
  
  public WireTransfers getWireTransfersByBatchId(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    WireTransfers localWireTransfers = new WireTransfers();
    WireInfo[] arrayOfWireInfo = jdMethod_byte(paramString);
    if ((arrayOfWireInfo != null) && (arrayOfWireInfo.length != 0)) {
      for (int i = 0; i < arrayOfWireInfo.length; i++)
      {
        WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.createNoAdd();
        a(localWireTransfer, arrayOfWireInfo[i], null);
        if (localWireTransfer.getStatus() != 3) {
          localWireTransfers.add(localWireTransfer);
        }
      }
    }
    return localWireTransfers;
  }
  
  private static BPWHist a(String paramString, int paramInt)
  {
    BPWHist localBPWHist = new BPWHist();
    localBPWHist.setVersion("200");
    localBPWHist.setCustId(new Integer(paramInt).toString());
    localBPWHist.setCursorId(null);
    localBPWHist.setHistId(null);
    String[] arrayOfString = { "TEMPLATE" };
    localBPWHist.setStatusList(arrayOfString);
    localBPWHist.setTransScope(paramString);
    return localBPWHist;
  }
  
  public WireTransfers getAllWireTemplates(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    WireTransfers localWireTransfers = new WireTransfers(paramSecureUser.getLocale());
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    Object[] arrayOfObject1 = null;
    Object[] arrayOfObject2 = null;
    try
    {
      BPWHist localBPWHist = localBPWServices.getWireHistory(a("BUSINESS", paramInt));
      if (localBPWHist != null) {
        arrayOfObject2 = localBPWHist.getTrans();
      }
      localBPWHist = localBPWServices.getWireHistory(a("BANK", paramInt));
      if (localBPWHist != null) {
        arrayOfObject1 = localBPWHist.getTrans();
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("getAllWireTemplates ", localException);
      localException.printStackTrace(System.err);
      throw new CSILException(-1009, 31010);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    Object[] arrayOfObject3 = arrayOfObject2;
    for (int i = 0; i < 2; i++)
    {
      if (arrayOfObject3 != null) {
        for (int j = 0; j < arrayOfObject3.length; j++)
        {
          WireInfo localWireInfo = null;
          RecWireInfo localRecWireInfo = null;
          WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.create();
          if ((arrayOfObject3[j] instanceof RecWireInfo))
          {
            localRecWireInfo = (RecWireInfo)arrayOfObject3[j];
            localWireTransfer.setRecWireInfo(localRecWireInfo);
            localWireTransfer.setType(6);
          }
          else if ((arrayOfObject3[j] instanceof WireInfo))
          {
            localWireInfo = (WireInfo)arrayOfObject3[j];
            localWireTransfer.setWireInfo(localWireInfo);
          }
          else
          {
            DebugLog.log("getAllWireTemplates: No WireInfo record found.");
          }
        }
      }
      arrayOfObject3 = arrayOfObject1;
    }
    localWireTransfers.setSortedBy("WIRE_NAME");
    return localWireTransfers;
  }
  
  public WireTransfers getWireTemplates(SecureUser paramSecureUser, PagingContext paramPagingContext, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    return a(paramSecureUser, paramPagingContext, paramWireTransfer, paramHashMap);
  }
  
  public WireTransfer getWireTransferById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    WireTransfer localWireTransfer = new WireTransfer();
    WireInfo localWireInfo = jdMethod_case(paramString);
    if (localWireInfo.getStatusCode() != 0) {
      return localWireTransfer;
    }
    Accounts localAccounts = (Accounts)paramHashMap.get("ACCOUNTS");
    a(localWireTransfer, localWireInfo, localAccounts);
    return localWireTransfer;
  }
  
  public WireTransfer getRecWireTransferById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    WireTransfer localWireTransfer = new WireTransfer();
    boolean bool = true;
    if (paramHashMap != null)
    {
      localObject = (String)paramHashMap.get("EXCLUDE_CANCELLED");
      if (localObject != null) {
        bool = Boolean.getBoolean((String)localObject);
      }
    }
    Object localObject = a(paramString, bool);
    if (((RecWireInfo)localObject).getStatusCode() != 0) {
      return localWireTransfer;
    }
    Accounts localAccounts = (Accounts)paramHashMap.get("ACCOUNTS");
    a(localWireTransfer, (RecWireInfo)localObject, localAccounts);
    return localWireTransfer;
  }
  
  public WireTransfer getCompletedWireTransferById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    WireTransfer localWireTransfer = new WireTransfer();
    WireInfo localWireInfo = jdMethod_case(paramString);
    localWireTransfer.setWireInfo(localWireInfo);
    return localWireTransfer;
  }
  
  public WireTransfers getPagedCompletedWires(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    return jdMethod_if(paramSecureUser, paramPagingContext, 6, paramHashMap);
  }
  
  public WireBatches getPagedCompletedWireBatches(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    return a(paramSecureUser, paramPagingContext, 6, paramHashMap);
  }
  
  public WireTransfers getPagedPendingWires(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    return jdMethod_if(paramSecureUser, paramPagingContext, 5, paramHashMap);
  }
  
  public WireBatches getPagedPendingWireBatches(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    return a(paramSecureUser, paramPagingContext, 5, paramHashMap);
  }
  
  public WireTransferPayee addWireTransferPayee(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException
  {
    WirePayeeInfo localWirePayeeInfo = paramWireTransferPayee.getWirePayeeInfo();
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      populateOBOAgentInfo(paramSecureUser, localWirePayeeInfo);
      DebugLog.log("*** BPW.AddPayeeInfo: Adding PayeeInfo = [" + localWirePayeeInfo.toString() + "]");
      localWirePayeeInfo = localBPWServices.addWirePayee(localWirePayeeInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
      throw new CSILException(-1009, 31004);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    int i = localWirePayeeInfo.getStatusCode();
    if (i != 0)
    {
      DebugLog.log("*** BPW ErrorCode: " + i);
      DebugLog.log("*** BPW ErrorMsg: " + localWirePayeeInfo.getStatusMsg());
      i = jdMethod_do(i, 31004);
      throw new CSILException(-1009, i);
    }
    paramWireTransferPayee.setID(localWirePayeeInfo.getPayeeId());
    paramWireTransferPayee.setActivationDate(localWirePayeeInfo.getActivationDate());
    paramWireTransferPayee.setLastModDate(localWirePayeeInfo.getLastModDate());
    paramWireTransferPayee.setStatus(localWirePayeeInfo.getStatus());
    return paramWireTransferPayee;
  }
  
  public WireTransferPayee modifyWireTransferPayee(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException
  {
    if (paramWireTransferPayee.getPayeeScope().equals("USER")) {
      paramWireTransferPayee.setSubmittedBy(paramSecureUser.getProfileID());
    }
    WirePayeeInfo localWirePayeeInfo = paramWireTransferPayee.getWirePayeeInfo();
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      populateOBOAgentInfo(paramSecureUser, localWirePayeeInfo);
      DebugLog.log("*** BPW.ModifyPayeeInfo: Modifying PayeeInfo = [" + localWirePayeeInfo.toString() + "]");
      localWirePayeeInfo = localBPWServices.modWirePayee(localWirePayeeInfo);
      WireHistories localWireHistories = new WireHistories();
      Hashtable localHashtable = localWirePayeeInfo.getExtInfo();
      if (localHashtable != null)
      {
        ArrayList localArrayList = null;
        Object localObject1 = localHashtable.get("MOD_BENE_WIRES_LIST");
        if ((localObject1 != null) && ((localObject1 instanceof ArrayList))) {
          localArrayList = (ArrayList)localObject1;
        }
        if ((localArrayList != null) && (localArrayList.size() > 0))
        {
          Iterator localIterator = localArrayList.iterator();
          while (localIterator.hasNext())
          {
            WireInfo localWireInfo = (WireInfo)localIterator.next();
            WireHistory localWireHistory = (WireHistory)localWireHistories.create();
            localWireHistory.setWireHistoryFromWireInfo(localWireInfo);
            User localUser = CustomerAdapter.getUserById(Integer.parseInt(localWireHistory.getSubmittedBy()));
            if (localUser != null) {
              localWireHistory.setSubmittedBy(localUser.getUserName());
            }
          }
          paramHashMap.put("PAYEE_MODIFICATION_ERROR", localWireHistories);
          paramWireTransferPayee.setErrorCode(31056);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
      throw new CSILException(-1009, 31006);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    int i = localWirePayeeInfo.getStatusCode();
    if (i != 0)
    {
      DebugLog.log("*** BPW ErrorCode: " + i);
      DebugLog.log("*** BPW ErrorMsg: " + localWirePayeeInfo.getStatusMsg());
      i = jdMethod_do(i, 31006);
      throw new CSILException(-1009, i);
    }
    paramWireTransferPayee.setLastModDate(localWirePayeeInfo.getLastModDate());
    return paramWireTransferPayee;
  }
  
  public void deleteWireTransferPayee(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException
  {
    WirePayeeInfo localWirePayeeInfo = paramWireTransferPayee.getWirePayeeInfo();
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      populateOBOAgentInfo(paramSecureUser, localWirePayeeInfo);
      localWirePayeeInfo = localBPWServices.canWirePayee(localWirePayeeInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    int i = localWirePayeeInfo.getStatusCode();
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localWirePayeeInfo.getStatusMsg());
      i = jdMethod_do(i, 31008);
      throw new CSILException(-1009, i);
    }
  }
  
  public WireTransferPayees getWireTransferPayees(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
    throws CSILException
  {
    BPWPayeeList localBPWPayeeList = new BPWPayeeList();
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      localBPWPayeeList.setCustId(paramWireTransferPayee.getCustomerID());
      String str = paramWireTransferPayee.getSubmittedBy();
      if ((str != null) && (str.length() > 0))
      {
        localObject1 = new String[] { str };
        localBPWPayeeList.setSubmittedBy((String[])localObject1);
      }
      localObject1 = paramWireTransferPayee.getPayeeDestination();
      if ((localObject1 != null) && (((String)localObject1).length() > 0))
      {
        localObject2 = new String[] { localObject1 };
        localBPWPayeeList.setPayeeDestList((String[])localObject2);
      }
      localBPWPayeeList = localBPWServices.getWirePayeeByCustomer(localBPWPayeeList);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    int i = localBPWPayeeList.getStatusCode();
    if ((i != 0) && (i != 16020))
    {
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localBPWPayeeList.getStatusMsg());
      i = jdMethod_do(i, 31002);
      throw new CSILException(-1009, i);
    }
    Object localObject1 = localBPWPayeeList.getPayees();
    Object localObject2 = new WireTransferPayees();
    if (localObject1 != null) {
      for (int j = 0; j < localObject1.length; j++)
      {
        WirePayeeInfo localWirePayeeInfo = (WirePayeeInfo)localObject1[j];
        if (localWirePayeeInfo.getStatusCode() == 0)
        {
          WireTransferPayee localWireTransferPayee = ((WireTransferPayees)localObject2).create();
          localWireTransferPayee.setWirePayeeInfo(localWirePayeeInfo);
        }
      }
    }
    return localObject2;
  }
  
  public WireTransferPayee getWireTransferPayeeById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    WirePayeeInfo localWirePayeeInfo = null;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      localWirePayeeInfo = localBPWServices.getWirePayee(paramString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    int i = localWirePayeeInfo.getStatusCode();
    if ((i != 0) && (i != 16020))
    {
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localWirePayeeInfo.getStatusMsg());
      i = jdMethod_do(i, 31002);
      throw new CSILException(-1009, i);
    }
    WireTransferPayee localWireTransferPayee = new WireTransferPayee();
    localWireTransferPayee.setWirePayeeInfo(localWirePayeeInfo);
    return localWireTransferPayee;
  }
  
  protected WireTransfers filterWireTransfersInReport(SecureUser paramSecureUser, WireTransfers paramWireTransfers, HashMap paramHashMap)
  {
    return paramWireTransfers;
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "WireTransferService.GetReportData";
    Object localObject1 = null;
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if (localProperties == null)
    {
      localObject2 = "No search criteria found.  Report cannot be run.";
      DebugLog.log((String)localObject2);
      throw new CSILException(str1, 21007, (String)localObject2);
    }
    Object localObject2 = paramReportCriteria.getReportOptions();
    if (localObject2 == null)
    {
      str2 = "Missing report options";
      DebugLog.log(str2);
      throw new CSILException(str1, 21007, str2);
    }
    String str2 = ((Properties)localObject2).getProperty("REPORTTYPE");
    if (str2 == null)
    {
      localObject3 = "Report type not specified";
      DebugLog.log((String)localObject3);
      throw new CSILException(str1, 21007, (String)localObject3);
    }
    Object localObject3 = new HashMap();
    HashMap localHashMap = new HashMap();
    try
    {
      Reporting.calculateDateRange(paramSecureUser, null, paramReportCriteria, (HashMap)localObject3, localHashMap, paramHashMap);
      paramHashMap.put("StartDates", localObject3);
      paramHashMap.put("EndDates", localHashMap);
    }
    catch (CSILException localCSILException)
    {
      str3 = "Unable to calculate the date ranges. A report cannot be run.";
      DebugLog.log(str3);
      if (localCSILException.getCode() == -1009) {
        throw new CSILException(str1, localCSILException.getServiceError(), str3);
      }
      throw new CSILException(str1, localCSILException.getCode(), str3);
    }
    DateTime localDateTime1 = null;
    String str3 = localProperties.getProperty("StartDate");
    if ((str3 != null) && (str3.length() > 0)) {
      try
      {
        localDateTime1 = new DateTime(DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").parse(str3), Locale.getDefault(), "MM/dd/yyyy HH:mm:ss");
      }
      catch (Exception localException1) {}
    } else {
      paramReportCriteria.setHiddenSearchCriterion("StartDate", true);
    }
    DateTime localDateTime2 = null;
    String str4 = localProperties.getProperty("EndDate");
    if ((str4 != null) && (str4.length() > 0)) {
      try
      {
        localDateTime2 = new DateTime(DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").parse(str4), Locale.getDefault(), "MM/dd/yyyy HH:mm:ss");
      }
      catch (Exception localException2) {}
    } else {
      paramReportCriteria.setHiddenSearchCriterion("EndDate", true);
    }
    PagingContext localPagingContext = new PagingContext(localDateTime1, localDateTime2);
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    paramHashMap.put("PAGESIZE", "99999");
    paramHashMap.put("AllWires", "all");
    WireTransfers localWireTransfers = null;
    if (str2.equals("InProcess Wires"))
    {
      localWireTransfers = jdMethod_if(paramSecureUser, localPagingContext, 5, paramHashMap);
    }
    else if (str2.equals("Completed Wires"))
    {
      localWireTransfers = jdMethod_if(paramSecureUser, localPagingContext, 2, paramHashMap);
    }
    else if (str2.equals("Failed Wires"))
    {
      localWireTransfers = jdMethod_if(paramSecureUser, localPagingContext, 4, paramHashMap);
    }
    else
    {
      String str5;
      if (str2.equals("Wire By Status"))
      {
        str5 = localProperties.getProperty("Status");
        if (str5 == null) {
          str5 = "";
        }
        DebugLog.log("Status List to search for: " + str5);
        localObject4 = new StringTokenizer(str5, ",");
        localObject5 = null;
        localObject6 = new ArrayList();
        if (((StringTokenizer)localObject4).countTokens() == 0)
        {
          ((ArrayList)localObject6).add("CANCELEDON");
          ((ArrayList)localObject6).add("WILLPROCESSON");
          ((ArrayList)localObject6).add("IMMED_INPROCESS");
          ((ArrayList)localObject6).add("INPROCESS");
          ((ArrayList)localObject6).add("CREATED");
          ((ArrayList)localObject6).add("COMPLETED");
          ((ArrayList)localObject6).add("INFUNDSAPPROVAL");
          ((ArrayList)localObject6).add("RELEASEREJECTED");
          ((ArrayList)localObject6).add("BACKENDREJECTED");
          ((ArrayList)localObject6).add("REJECTED");
          ((ArrayList)localObject6).add("POSTEDON");
          ((ArrayList)localObject6).add("ACCEPTED");
          ((ArrayList)localObject6).add("CONFIRMED");
          ((ArrayList)localObject6).add("BACKENDPENDING");
          ((ArrayList)localObject6).add("RELEASEPENDING");
          ((ArrayList)localObject6).add("FUNDSPENDING");
          ((ArrayList)localObject6).add("NOFUNDS");
          ((ArrayList)localObject6).add("NOFUNDS_NOTIF");
          ((ArrayList)localObject6).add("APPROVAL_PENDING");
          ((ArrayList)localObject6).add("APPROVAL_REJECTED");
          ((ArrayList)localObject6).add("APPROVAL_FAILED");
          ((ArrayList)localObject6).add("ACKNOWLEDGED");
          ((ArrayList)localObject6).add("FAILEDON");
          ((ArrayList)localObject6).add("FAILEDON_NOTIF");
          ((ArrayList)localObject6).add("FUNDSFAILEDON");
          ((ArrayList)localObject6).add("FUNDSFAILEDON_NOTIF");
          ((ArrayList)localObject6).add("FUNDSREVERTFAILED");
          ((ArrayList)localObject6).add("FUNDSREVERTFAILED_NOTIF");
          ((ArrayList)localObject6).add("FUNDSREVERTED");
          ((ArrayList)localObject6).add("FUNDSREVERTED_NOTIF");
          ((ArrayList)localObject6).add("BACKENDREJECTED");
          ((ArrayList)localObject6).add("BACKENDREJECTED_NOTIF");
          ((ArrayList)localObject6).add("BACKENDFAILED");
          ((ArrayList)localObject6).add("BACKENDFAILED_NOTIF");
        }
        while (((StringTokenizer)localObject4).hasMoreTokens())
        {
          localObject5 = ((StringTokenizer)localObject4).nextToken();
          if (localObject5 != null) {
            if (((String)localObject5).equals("Cancelled"))
            {
              ((ArrayList)localObject6).add("CANCELEDON");
            }
            else if (((String)localObject5).equals("Scheduled"))
            {
              ((ArrayList)localObject6).add("WILLPROCESSON");
            }
            else if (((String)localObject5).equals("Created"))
            {
              ((ArrayList)localObject6).add("CREATED");
            }
            else if (((String)localObject5).equals("Completed"))
            {
              ((ArrayList)localObject6).add("COMPLETED");
            }
            else if (((String)localObject5).equals("In Funds Approval"))
            {
              ((ArrayList)localObject6).add("INFUNDSAPPROVAL");
            }
            else if (((String)localObject5).equals("Rejected by Backend"))
            {
              ((ArrayList)localObject6).add("BACKENDREJECTED");
              ((ArrayList)localObject6).add("BACKENDREJECTED_NOTIF");
              ((ArrayList)localObject6).add("REJECTED");
            }
            else if (((String)localObject5).equals("Release Rejected"))
            {
              ((ArrayList)localObject6).add("RELEASEREJECTED");
            }
            else if (((String)localObject5).equals("Received by Backend"))
            {
              ((ArrayList)localObject6).add("POSTEDON");
            }
            else if (((String)localObject5).equals("Confirmed by Backend"))
            {
              ((ArrayList)localObject6).add("ACCEPTED");
              ((ArrayList)localObject6).add("CONFIRMED");
            }
            else if (((String)localObject5).equals("Backend Pending"))
            {
              ((ArrayList)localObject6).add("BACKENDPENDING");
              ((ArrayList)localObject6).add("RELEASEPENDING");
            }
            else if (((String)localObject5).equals("Funds Pending"))
            {
              ((ArrayList)localObject6).add("FUNDSPENDING");
            }
            else if (((String)localObject5).equals("No Funds"))
            {
              ((ArrayList)localObject6).add("NOFUNDS");
              ((ArrayList)localObject6).add("NOFUNDS_NOTIF");
            }
            else if (((String)localObject5).equals("Release Pending"))
            {
              ((ArrayList)localObject6).add("RELEASEPENDING");
            }
            else if (((String)localObject5).equals("Approval Pending"))
            {
              ((ArrayList)localObject6).add("APPROVAL_PENDING");
            }
            else if (((String)localObject5).equals("Approval Rejected"))
            {
              ((ArrayList)localObject6).add("APPROVAL_REJECTED");
            }
            else if (((String)localObject5).equals("Approval Failed"))
            {
              ((ArrayList)localObject6).add("APPROVAL_FAILED");
            }
            else if (((String)localObject5).equals("Acknowledged"))
            {
              ((ArrayList)localObject6).add("ACKNOWLEDGED");
            }
            else if (((String)localObject5).equals("In Process by Backend"))
            {
              ((ArrayList)localObject6).add("INPROCESS");
            }
            else if (((String)localObject5).equals("Failed at Backend"))
            {
              ((ArrayList)localObject6).add("BACKENDFAILED_NOTIF");
              ((ArrayList)localObject6).add("BACKENDFAILED");
            }
            else if (((String)localObject5).equals("Immediate In Process"))
            {
              ((ArrayList)localObject6).add("IMMED_INPROCESS");
            }
            else if (((String)localObject5).equals("Funds Reverted"))
            {
              ((ArrayList)localObject6).add("FUNDSREVERTED");
              ((ArrayList)localObject6).add("FUNDSREVERTED_NOTIF");
            }
            else if (((String)localObject5).equals("Funds Revert Failed"))
            {
              ((ArrayList)localObject6).add("FUNDSREVERTFAILED");
              ((ArrayList)localObject6).add("FUNDSREVERTFAILED_NOTIF");
            }
            else if (((String)localObject5).equals("Funds Approval Failed"))
            {
              ((ArrayList)localObject6).add("FUNDSFAILEDON");
              ((ArrayList)localObject6).add("FUNDSFAILEDON_NOTIF");
            }
          }
        }
        String[] arrayOfString = (String[])((ArrayList)localObject6).toArray(new String[0]);
        paramHashMap.put("Statuses", arrayOfString);
        localWireTransfers = jdMethod_if(paramSecureUser, localPagingContext, 0, paramHashMap);
      }
      else if (str2.equals("Wire By Source"))
      {
        str5 = localProperties.getProperty("WireSource");
        localObject4 = null;
        if ((str5 != null) && (str5.trim().length() > 0)) {
          if (str5.equalsIgnoreCase("TEMPLATE")) {
            localObject4 = "TEMPLATE";
          } else if (str5.equalsIgnoreCase("HOST")) {
            localObject4 = "HOST";
          } else if (str5.equalsIgnoreCase("FREEFORM")) {
            localObject4 = "FREEFORM";
          }
        }
        localObject5 = new String[0];
        paramHashMap.put("Statuses", localObject5);
        if (localObject4 != null) {
          paramHashMap.put("TransSource", localObject4);
        }
        localWireTransfers = jdMethod_if(paramSecureUser, localPagingContext, 0, paramHashMap);
      }
      else if (str2.equals("Wire By Template"))
      {
        str5 = localProperties.getProperty("Template");
        localObject4 = null;
        if ((str5 != null) && (str5.trim().length() > 0)) {
          localObject4 = str5.trim();
        }
        localObject5 = new String[0];
        paramHashMap.put("Statuses", localObject5);
        if (localObject4 != null) {
          paramHashMap.put("TemplateID", localObject4);
        } else {
          paramHashMap.put("TransSource", "TEMPLATE");
        }
        localWireTransfers = jdMethod_if(paramSecureUser, localPagingContext, 0, paramHashMap);
      }
      else if (str2.equals("Wire Templates"))
      {
        str5 = localProperties.getProperty("WireScope");
        localObject4 = null;
        if ((str5 != null) && (str5.trim().length() > 0))
        {
          str5 = str5.trim();
          if ((str5.equals("BANK")) || (str5.equals("BUSINESS")) || (str5.equals("USER"))) {
            localObject4 = str5;
          }
        }
        localObject5 = new String[] { "TEMPLATE", "RECTEMPLATE" };
        paramHashMap.put("Statuses", localObject5);
        if (localObject4 != null) {
          paramHashMap.put("TransScope", localObject4);
        }
        localWireTransfers = jdMethod_if(paramSecureUser, localPagingContext, 0, paramHashMap);
      }
      else
      {
        str5 = "Report type is not valid";
        DebugLog.log(str5);
        throw new CSILException(str1, 21007, str5);
      }
    }
    int i = 0;
    if (!jdMethod_try(localProperties.getProperty("Account")))
    {
      localObject4 = new StringTokenizer(localProperties.getProperty("Account"), ",", false);
      localObject5 = new StringBuffer();
      while (((StringTokenizer)localObject4).hasMoreTokens())
      {
        localObject6 = ((StringTokenizer)localObject4).nextToken();
        ((StringBuffer)localObject5).append("FROM_ACCOUNT_ID").append("=").append((String)localObject6).append(",");
      }
      localWireTransfers.setFilter(((StringBuffer)localObject5).toString());
      i = 1;
    }
    Object localObject4 = new StringBuffer();
    if (!jdMethod_try(localProperties.getProperty("Payee"))) {
      ((StringBuffer)localObject4).append("WIRE_PAYEE_ID").append("=").append(localProperties.getProperty("Payee"));
    }
    if (!jdMethod_try(localProperties.getProperty("AmountFrom")))
    {
      if (((StringBuffer)localObject4).length() > 0) {
        ((StringBuffer)localObject4).append(",");
      }
      ((StringBuffer)localObject4).append("AMOUNT").append(">=").append(localProperties.getProperty("AmountFrom"));
    }
    if (!jdMethod_try(localProperties.getProperty("AmountTo")))
    {
      if (((StringBuffer)localObject4).length() > 0) {
        ((StringBuffer)localObject4).append(",");
      }
      ((StringBuffer)localObject4).append("AMOUNT").append("<=").append(localProperties.getProperty("AmountTo"));
    }
    if (((StringBuffer)localObject4).length() > 0) {
      ((StringBuffer)localObject4).append(",AND");
    }
    if (i != 0) {
      localWireTransfers.setFilterOnFilter(((StringBuffer)localObject4).toString());
    } else {
      localWireTransfers.setFilter(((StringBuffer)localObject4).toString());
    }
    localWireTransfers = filterWireTransfersInReport(paramSecureUser, localWireTransfers, paramHashMap);
    Object localObject5 = new WireTransfers(paramSecureUser.getLocale());
    Object localObject6 = localWireTransfers.iterator();
    double d1 = 0.0D;
    double d2 = 0.0D;
    int j = 0;
    int k = 0;
    while (((Iterator)localObject6).hasNext())
    {
      localObject7 = (WireTransfer)((Iterator)localObject6).next();
      ((WireTransfers)localObject5).add(localObject7);
      localObject8 = ((WireTransfer)localObject7).getWireDestination();
      if ("DRAWDOWN".equals(localObject8))
      {
        d2 += ((WireTransfer)localObject7).getAmountValue().doubleValue();
        k++;
      }
      else
      {
        d1 += ((WireTransfer)localObject7).getAmountValue().doubleValue();
        j++;
      }
    }
    Object localObject7 = jdMethod_if(paramReportCriteria.getSearchCriteria(), "LEVEL", "Summary");
    Object localObject8 = paramReportCriteria.getSortCriteria();
    StringBuffer localStringBuffer = new StringBuffer();
    if ((localObject8 != null) && (((ReportSortCriteria)localObject8).size() > 0))
    {
      ((ReportSortCriteria)localObject8).setSortedBy("ORDINAL");
      Iterator localIterator = ((ReportSortCriteria)localObject8).iterator();
      while (localIterator.hasNext())
      {
        ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localIterator.next();
        String str6 = localReportSortCriterion.getName();
        String str7 = localReportSortCriterion.getAsc() ? "ASC" : "DESC";
        localStringBuffer.append(str6);
        localStringBuffer.append(" ");
        localStringBuffer.append(str7);
        if (localIterator.hasNext()) {
          localStringBuffer.append(", ");
        }
      }
      ((WireTransfers)localObject5).setSortedBy(localStringBuffer.toString());
    }
    return a(paramSecureUser, paramReportCriteria, (WireTransfers)localObject5, d1, d2, j, k, paramSecureUser.getLocale(), paramHashMap);
  }
  
  private ReportResult a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, WireTransfers paramWireTransfers, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, Locale paramLocale, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "WireTransferService.GetWireReport";
    ReportResult localReportResult = new ReportResult(paramLocale);
    int i = 0;
    int j = Integer.parseInt(jdMethod_if(paramReportCriteria.getReportOptions(), "MAXDISPLAYSIZE", String.valueOf(2147483647)));
    int k = 0;
    Accounts localAccounts = (Accounts)paramHashMap.get("AccountsForReport");
    String str2 = jdMethod_if(paramReportCriteria.getSearchCriteria(), "Account", "");
    if (str2.equals("")) {
      paramReportCriteria.setDisplayValue("Account", ReportConsts.getText(10024, paramLocale));
    } else {
      paramReportCriteria.setDisplayValue("Account", jdMethod_do(localAccounts, str2));
    }
    String str3 = jdMethod_if(paramReportCriteria.getSearchCriteria(), "AmountFrom", "");
    paramReportCriteria.setHiddenSearchCriterion("AmountFrom", str3.equals(""));
    String str4 = jdMethod_if(paramReportCriteria.getSearchCriteria(), "AmountTo", "");
    paramReportCriteria.setHiddenSearchCriterion("AmountTo", str4.equals(""));
    String str5 = jdMethod_if(paramReportCriteria.getSearchCriteria(), "Payee", "");
    if (str5.equals(""))
    {
      paramReportCriteria.setDisplayValue("Payee", ReportConsts.getText(10029, paramLocale));
    }
    else
    {
      localObject1 = getWireTransferPayeeById(paramSecureUser, str5, new HashMap());
      if ((((WireTransferPayee)localObject1).getNickName() != null) && (((WireTransferPayee)localObject1).getNickName().length() > 0)) {
        paramReportCriteria.setDisplayValue("Payee", ((WireTransferPayee)localObject1).getPayeeName() + " (" + ((WireTransferPayee)localObject1).getNickName() + ")");
      } else {
        paramReportCriteria.setDisplayValue("Payee", ((WireTransferPayee)localObject1).getPayeeName());
      }
    }
    Object localObject1 = jdMethod_if(paramReportCriteria.getSearchCriteria(), "Template", "");
    if (((String)localObject1).equals(""))
    {
      paramReportCriteria.setDisplayValue("Template", ReportConsts.getText(10034, paramLocale));
    }
    else
    {
      localObject2 = getWireTransferById(paramSecureUser, (String)localObject1, paramHashMap);
      if ((((WireTransfer)localObject2).getWireName() == null) || (((WireTransfer)localObject2).getWireName().length() == 0)) {
        localObject2 = getRecWireTransferById(paramSecureUser, (String)localObject1, paramHashMap);
      }
      if (((WireTransfer)localObject2).getWireName() != null) {
        paramReportCriteria.setDisplayValue("Template", ((WireTransfer)localObject2).getWireName() + "(" + ((WireTransfer)localObject2).getNickName() + ")");
      }
    }
    Object localObject2 = jdMethod_if(paramReportCriteria.getSearchCriteria(), "WireScope", "");
    if (((String)localObject2).equals("")) {
      paramReportCriteria.setDisplayValue("WireScope", ReportConsts.getText(10038, paramLocale));
    } else if (((String)localObject2).equals("USER")) {
      paramReportCriteria.setDisplayValue("WireScope", ReportConsts.getText(10035, paramLocale));
    } else if (((String)localObject2).equals("BUSINESS")) {
      paramReportCriteria.setDisplayValue("WireScope", ReportConsts.getText(10036, paramLocale));
    } else if (((String)localObject2).equals("BANK")) {
      paramReportCriteria.setDisplayValue("WireScope", ReportConsts.getText(10037, paramLocale));
    }
    String str6 = jdMethod_if(paramReportCriteria.getSearchCriteria(), "WireSource", "");
    if (str6.equals("")) {
      paramReportCriteria.setDisplayValue("WireSource", ReportConsts.getText(10042, paramLocale));
    } else if (str6.equals("HOST")) {
      paramReportCriteria.setDisplayValue("WireSource", ReportConsts.getText(10039, paramLocale));
    } else if (str6.equals("TEMPLATE")) {
      paramReportCriteria.setDisplayValue("WireSource", ReportConsts.getText(10040, paramLocale));
    } else if (str6.equals("FREEFORM")) {
      paramReportCriteria.setDisplayValue("WireSource", ReportConsts.getText(10041, paramLocale));
    }
    String str7 = jdMethod_if(paramReportCriteria.getSearchCriteria(), "Status", "");
    Object localObject3;
    Object localObject4;
    if (str7.equals(""))
    {
      paramReportCriteria.setDisplayValue("Status", ReportConsts.getText(10018, paramLocale));
    }
    else
    {
      str8 = str7.substring(0, str7.length() - 1);
      localObject3 = new StringBuffer();
      localObject4 = new StringTokenizer(str7, ",");
      while (((StringTokenizer)localObject4).hasMoreTokens())
      {
        ((StringBuffer)localObject3).append(((StringTokenizer)localObject4).nextToken());
        if (((StringTokenizer)localObject4).hasMoreTokens()) {
          ((StringBuffer)localObject3).append(", ");
        }
      }
      paramReportCriteria.setDisplayValue("Status", ((StringBuffer)localObject3).toString());
    }
    String str8 = jdMethod_if(paramReportCriteria.getSearchCriteria(), "LEVEL", "Summary");
    if (str8.equals("Detailed")) {
      return a(paramSecureUser, paramReportCriteria, paramWireTransfers, paramLocale, j, paramHashMap);
    }
    try
    {
      localReportResult.init(paramReportCriteria);
      localObject3 = new ReportResult(paramLocale);
      localReportResult.addSubReport((ReportResult)localObject3);
      localObject4 = paramWireTransfers.iterator();
      Object localObject6;
      Object localObject7;
      Object localObject5;
      if (((Iterator)localObject4).hasNext())
      {
        int m = 10;
        int n = 0;
        int i1 = 0;
        localObject6 = null;
        localObject7 = paramReportCriteria.getReportOptions();
        if (localObject7 != null) {
          localObject6 = ((Properties)localObject7).getProperty("REPORTTYPE");
        }
        if ("Wire Templates".equals(localObject6))
        {
          n = 1;
          m = 8;
        }
        else if ("Wire By Source".equals(localObject6))
        {
          i1 = 1;
          m = 11;
        }
        ReportDataDimensions localReportDataDimensions2 = new ReportDataDimensions(paramLocale);
        localReportDataDimensions2.setNumColumns(m);
        localReportDataDimensions2.setNumRows(-1);
        ((ReportResult)localObject3).setDataDimensions(localReportDataDimensions2);
        ReportColumn localReportColumn1 = new ReportColumn(paramLocale);
        ArrayList localArrayList1 = new ArrayList();
        localArrayList1.add(ReportConsts.getColumnName(2891, paramLocale));
        localReportColumn1.setLabels(localArrayList1);
        localReportColumn1.setDataType("com.ffusion.util.beans.DateTime");
        localReportColumn1.setWidthAsPercent(10);
        ((ReportResult)localObject3).addColumn(localReportColumn1);
        if (n == 0)
        {
          localReportColumn2 = new ReportColumn(paramLocale);
          localArrayList2 = new ArrayList();
          localArrayList2.add(ReportConsts.getColumnName(1888, paramLocale));
          localReportColumn2.setLabels(localArrayList2);
          localReportColumn2.setDataType("com.ffusion.util.beans.DateTime");
          localReportColumn2.setWidthAsPercent(10);
          ((ReportResult)localObject3).addColumn(localReportColumn2);
          localReportColumn3 = new ReportColumn(paramLocale);
          localArrayList3 = new ArrayList();
          localArrayList3.add(ReportConsts.getColumnName(1886, paramLocale));
          localReportColumn3.setLabels(localArrayList3);
          localReportColumn3.setDataType("com.ffusion.util.beans.DateTime");
          localReportColumn3.setWidthAsPercent(13);
          ((ReportResult)localObject3).addColumn(localReportColumn3);
        }
        ReportColumn localReportColumn2 = new ReportColumn(paramLocale);
        ArrayList localArrayList2 = new ArrayList();
        localArrayList2.add(ReportConsts.getColumnName(1882, paramLocale));
        localReportColumn2.setLabels(localArrayList2);
        localReportColumn2.setDataType("java.lang.String");
        localReportColumn2.setWidthAsPercent(15);
        ((ReportResult)localObject3).addColumn(localReportColumn2);
        ReportColumn localReportColumn3 = new ReportColumn(paramLocale);
        ArrayList localArrayList3 = new ArrayList();
        localArrayList3.add(ReportConsts.getColumnName(2890, paramLocale));
        localReportColumn3.setLabels(localArrayList3);
        localReportColumn3.setDataType("java.lang.String");
        localReportColumn3.setWidthAsPercent(15);
        ((ReportResult)localObject3).addColumn(localReportColumn3);
        ReportColumn localReportColumn4 = new ReportColumn(paramLocale);
        ArrayList localArrayList4 = new ArrayList();
        localArrayList4.add(ReportConsts.getColumnName(1883, paramLocale));
        localReportColumn4.setLabels(localArrayList4);
        localReportColumn4.setDataType("java.lang.String");
        localReportColumn4.setWidthAsPercent(20);
        ((ReportResult)localObject3).addColumn(localReportColumn4);
        if (i1 != 0)
        {
          localReportColumn5 = new ReportColumn(paramLocale);
          localArrayList5 = new ArrayList();
          localArrayList5.add(ReportConsts.getColumnName(1889, paramLocale));
          localReportColumn5.setLabels(localArrayList5);
          localReportColumn5.setDataType("java.lang.String");
          localReportColumn5.setWidthAsPercent(10);
          ((ReportResult)localObject3).addColumn(localReportColumn5);
        }
        ReportColumn localReportColumn5 = new ReportColumn(paramLocale);
        ArrayList localArrayList5 = new ArrayList();
        localArrayList5.add(ReportConsts.getColumnName(603, paramLocale));
        localReportColumn5.setLabels(localArrayList5);
        localReportColumn5.setDataType("java.lang.String");
        localReportColumn5.setWidthAsPercent(15);
        ((ReportResult)localObject3).addColumn(localReportColumn5);
        ReportColumn localReportColumn6 = new ReportColumn(paramLocale);
        ArrayList localArrayList6 = new ArrayList();
        localArrayList6.add(ReportConsts.getColumnName(2889, paramLocale));
        localReportColumn6.setLabels(localArrayList6);
        localReportColumn6.setDataType("java.lang.String");
        localReportColumn6.setWidthAsPercent(10);
        ((ReportResult)localObject3).addColumn(localReportColumn6);
        ReportColumn localReportColumn7 = new ReportColumn(paramLocale);
        ArrayList localArrayList7 = new ArrayList();
        localArrayList7.add(ReportConsts.getColumnName(1884, paramLocale));
        localReportColumn7.setLabels(localArrayList7);
        localReportColumn7.setDataType("java.lang.String");
        localReportColumn7.setWidthAsPercent(20);
        ((ReportResult)localObject3).addColumn(localReportColumn7);
        ReportColumn localReportColumn8 = new ReportColumn(paramLocale);
        ArrayList localArrayList8 = new ArrayList();
        localArrayList8.add(ReportConsts.getColumnName(1885, paramLocale));
        localReportColumn8.setLabels(localArrayList8);
        localReportColumn8.setDataType("com.ffusion.beans.Currency");
        localReportColumn8.setJustification("RIGHT");
        localReportColumn8.setWidthAsPercent(10);
        ((ReportResult)localObject3).addColumn(localReportColumn8);
        WireTransfer localWireTransfer = null;
        while ((((Iterator)localObject4).hasNext()) && (i < j))
        {
          localWireTransfer = (WireTransfer)((Iterator)localObject4).next();
          String str9 = localWireTransfer.getWireDestination();
          ReportRow localReportRow2 = new ReportRow(((ReportResult)localObject3).getLocale());
          if (i % 2 == 1) {
            localReportRow2.set("CELLBACKGROUND", "reportDataShaded");
          }
          ReportDataItems localReportDataItems = new ReportDataItems(((ReportResult)localObject3).getLocale());
          localReportRow2.setDataItems(localReportDataItems);
          ReportDataItem localReportDataItem = localReportDataItems.add();
          jdMethod_if(localReportDataItem, localWireTransfer.getCreateDateValue());
          if (n == 0)
          {
            localReportDataItem = localReportDataItems.add();
            jdMethod_if(localReportDataItem, localWireTransfer.getDateToPostValue());
            localReportDataItem = localReportDataItems.add();
            jdMethod_if(localReportDataItem, localWireTransfer.getDatePostedValue());
          }
          localReportDataItem = localReportDataItems.add();
          Object localObject8;
          if (str9.equals("HOST"))
          {
            jdMethod_if(localReportDataItem, ReportConsts.getText(637, paramLocale));
          }
          else if (str9.equals("DRAWDOWN"))
          {
            localObject8 = localWireTransfer.getWireCreditInfo();
            if (localObject8 != null) {
              jdMethod_if(localReportDataItem, ((WireTransferPayee)localObject8).getPayeeName());
            } else {
              jdMethod_if(localReportDataItem, "");
            }
          }
          else
          {
            localObject8 = getWireTransferPayeeById(paramSecureUser, localWireTransfer.getWirePayeeID(), null);
            if ((((WireTransferPayee)localObject8).getNickName() != null) && (((WireTransferPayee)localObject8).getNickName().length() > 0)) {
              jdMethod_if(localReportDataItem, ((WireTransferPayee)localObject8).getPayeeName() + " (" + ((WireTransferPayee)localObject8).getNickName() + ")");
            } else {
              jdMethod_if(localReportDataItem, ((WireTransferPayee)localObject8).getPayeeName());
            }
          }
          localReportDataItem = localReportDataItems.add();
          if (str9.equals("DRAWDOWN"))
          {
            localObject8 = localWireTransfer.getWireCreditInfo();
            if (localObject8 != null) {
              jdMethod_if(localReportDataItem, ((WireTransferPayee)localObject8).getAccountDisplayText());
            } else {
              jdMethod_if(localReportDataItem, "");
            }
          }
          else if (str9.equals("HOST"))
          {
            jdMethod_if(localReportDataItem, "HOST");
          }
          else
          {
            localObject8 = localWireTransfer.getWirePayee();
            if (localObject8 != null) {
              jdMethod_if(localReportDataItem, ((WireTransferPayee)localObject8).getAccountDisplayText());
            } else {
              jdMethod_if(localReportDataItem, "");
            }
          }
          localReportDataItem = localReportDataItems.add();
          if (str9.equals("HOST"))
          {
            jdMethod_if(localReportDataItem, ReportConsts.getText(637, paramLocale));
          }
          else if (str9.equals("DRAWDOWN"))
          {
            localObject8 = localWireTransfer.getWirePayee();
            if (localObject8 != null) {
              jdMethod_if(localReportDataItem, ((WireTransferPayee)localObject8).getAccountDisplayText());
            } else {
              jdMethod_if(localReportDataItem, "");
            }
          }
          else
          {
            localObject8 = localAccounts.getByID(localWireTransfer.getFromAccountID());
            jdMethod_if(localReportDataItem, ((Account)localObject8).getDisplayTextRoutingNumNickNameCurrency());
          }
          if (i1 != 0)
          {
            localReportDataItem = localReportDataItems.add();
            localObject8 = localWireTransfer.getWireSource();
            if ((null != localObject8) && (((String)localObject8).equals("TEMPLATE"))) {
              localObject8 = localWireTransfer.getWireName();
            }
            jdMethod_if(localReportDataItem, localObject8);
          }
          localReportDataItem = localReportDataItems.add();
          jdMethod_if(localReportDataItem, localWireTransfer.getStatusName());
          localReportDataItem = localReportDataItems.add();
          jdMethod_if(localReportDataItem, localWireTransfer.getWireDestination());
          localReportDataItem = localReportDataItems.add();
          jdMethod_if(localReportDataItem, localWireTransfer.getReferenceNumber());
          localReportDataItem = localReportDataItems.add();
          jdMethod_if(localReportDataItem, localWireTransfer.getAmountValue());
          ((ReportResult)localObject3).addRow(localReportRow2);
          i++;
        }
        localReportDataDimensions2.setNumRows(i);
      }
      else
      {
        localObject5 = null;
        ReportDataDimensions localReportDataDimensions1 = new ReportDataDimensions(paramLocale);
        localReportDataDimensions1.setNumColumns(1);
        localReportDataDimensions1.setNumRows(1);
        ((ReportResult)localObject3).setDataDimensions(localReportDataDimensions1);
        localObject5 = new ReportColumn(paramLocale);
        ((ReportColumn)localObject5).setDataType("java.lang.String");
        ((ReportColumn)localObject5).setWidthAsPercent(100);
        ((ReportResult)localObject3).addColumn((ReportColumn)localObject5);
        localObject5 = null;
        ReportRow localReportRow1 = new ReportRow(paramLocale);
        localObject6 = new ReportDataItems(paramLocale);
        localObject7 = null;
        localReportRow1.setDataItems((ReportDataItems)localObject6);
        localObject7 = ((ReportDataItems)localObject6).add();
        ((ReportDataItem)localObject7).setData(ReportConsts.getText(2501, paramLocale));
        localObject7 = null;
        localObject6 = null;
        ((ReportResult)localObject3).addRow(localReportRow1);
        localReportRow1 = null;
      }
      if ((i < j) && (i > 0))
      {
        localObject5 = ReportConsts.getText(3004, paramLocale);
        a(localReportResult, (String)localObject5, paramLocale, paramInt2, paramInt1, paramDouble2, paramDouble1);
      }
    }
    catch (Exception localException)
    {
      k = 1;
      localReportResult.setError(localException);
      throw new CSILException(str1, 21007, localException);
    }
    finally
    {
      if (i == j)
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("TRUNCATED", new Integer(i));
        localReportResult.setProperties(localHashMap);
      }
      try
      {
        localReportResult.fini();
      }
      catch (RptException localRptException)
      {
        if (k == 0) {
          throw new CSILException(str1, 21007, localRptException);
        }
      }
    }
    return localReportResult;
  }
  
  private static void jdMethod_if(ReportDataItem paramReportDataItem, Object paramObject)
  {
    if (paramObject == null) {
      paramReportDataItem.setData("");
    } else {
      paramReportDataItem.setData(paramObject);
    }
  }
  
  private static String jdMethod_if(Properties paramProperties, String paramString1, String paramString2)
  {
    String str = paramProperties.getProperty(paramString1);
    if ((str == null) || (str.equals(""))) {
      str = paramString2;
    } else {
      str = str.trim();
    }
    return str;
  }
  
  public int getReleaseWiresCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    WireInfo[] arrayOfWireInfo = a(paramSecureUser, paramHashMap);
    int i = 0;
    if (arrayOfWireInfo != null) {
      i = arrayOfWireInfo.length;
    }
    return i;
  }
  
  public WireTransfers getReleaseWires(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    WireInfo[] arrayOfWireInfo = a(paramSecureUser, paramHashMap);
    WireTransfers localWireTransfers = new WireTransfers();
    if (arrayOfWireInfo != null)
    {
      WireInfo localWireInfo = null;
      for (int i = 0; i < arrayOfWireInfo.length; i++)
      {
        WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.create();
        localWireInfo = arrayOfWireInfo[i];
        a(localWireTransfer, localWireInfo, null);
      }
    }
    return localWireTransfers;
  }
  
  public void releaseWires(SecureUser paramSecureUser, WireTransfers paramWireTransfers, HashMap paramHashMap)
    throws CSILException
  {
    WireInfo[] arrayOfWireInfo = new WireInfo[paramWireTransfers.size()];
    for (int i = 0; i < paramWireTransfers.size(); i++)
    {
      WireTransfer localWireTransfer = (WireTransfer)paramWireTransfers.get(i);
      WireInfo localWireInfo = localWireTransfer.getWireInfo();
      localWireInfo.setTrnId(TrackingIDGenerator.GetNextID());
      localWireInfo.setProcessedBy(paramSecureUser.getUserName());
      localWireInfo.setSubmitedBy(String.valueOf(paramSecureUser.getProfileID()));
      arrayOfWireInfo[i] = localWireInfo;
    }
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      localBPWServices.releaseWireTransfer(arrayOfWireInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  public WireBatch addWireBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, HashMap paramHashMap)
    throws CSILException
  {
    int i = -1;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      paramWireBatch.setUserID(paramSecureUser.getProfileID());
      paramWireBatch.setSubmittedBy(paramSecureUser.getProfileID());
      String str1 = "";
      if (paramWireBatch.getDueDateValue() != null) {
        str1 = formatDate.format(paramWireBatch.getDueDateValue().getTime());
      }
      String str2 = "";
      if (paramWireBatch.getSettlementDateValue() != null) {
        str2 = formatDate.format(paramWireBatch.getSettlementDateValue().getTime());
      }
      WireBatchInfo localWireBatchInfo = paramWireBatch.getWireBatchInfo();
      localWireBatchInfo.setTrnId(TrackingIDGenerator.GetNextID());
      localWireBatchInfo.setDateDue(str1);
      localWireBatchInfo.setSettlementDate(str2);
      populateOBOAgentInfo(paramSecureUser, localWireBatchInfo);
      DebugLog.log("*** BPW.AddBatchInfo: Adding BatchInfo [ " + localWireBatchInfo.toString() + " ]");
      localWireBatchInfo = localBPWServices.addWireTransferBatch(localWireBatchInfo);
      i = a(paramWireBatch, localWireBatchInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      i = jdMethod_do(i, 31014);
      throw new CSILException(-1009, i);
    }
    return paramWireBatch;
  }
  
  public void modifyWireBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, HashMap paramHashMap)
    throws CSILException
  {
    int i = -1;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      paramWireBatch.setUserID(paramSecureUser.getProfileID());
      paramWireBatch.setSubmittedBy(paramSecureUser.getProfileID());
      String str1 = "";
      if (paramWireBatch.getDueDateValue() != null) {
        str1 = formatDate.format(paramWireBatch.getDueDateValue().getTime());
      }
      String str2 = "";
      if (paramWireBatch.getSettlementDateValue() != null) {
        str2 = formatDate.format(paramWireBatch.getSettlementDateValue().getTime());
      }
      WireBatchInfo localWireBatchInfo = paramWireBatch.getWireBatchInfo();
      localWireBatchInfo.setTrnId(TrackingIDGenerator.GetNextID());
      localWireBatchInfo.setDateDue(str1);
      localWireBatchInfo.setSettlementDate(str2);
      populateOBOAgentInfo(paramSecureUser, localWireBatchInfo);
      DebugLog.log("*** BPW.ModifyBatchInfo: Modifying BatchInfo [ " + localWireBatchInfo.toString() + " ]");
      localWireBatchInfo = localBPWServices.modWireTransferBatch(localWireBatchInfo);
      i = a(paramWireBatch, localWireBatchInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      i = jdMethod_do(i, 31014);
      throw new CSILException(-1009, i);
    }
  }
  
  public WireTransfer addHostWire(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    int i = -1;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      paramWireTransfer.setUserID(paramSecureUser.getProfileID());
      paramWireTransfer.setSubmittedBy(paramSecureUser.getProfileID());
      String str1 = "";
      if (paramWireTransfer.getDueDateValue() != null) {
        str1 = formatDate.format(paramWireTransfer.getDueDateValue().getTime());
      }
      String str2 = "";
      if (paramWireTransfer.getSettlementDateValue() != null) {
        str2 = formatDate.format(paramWireTransfer.getSettlementDateValue().getTime());
      }
      WireInfo localWireInfo = paramWireTransfer.getWireInfo();
      localWireInfo.setTrnId(TrackingIDGenerator.GetNextID());
      localWireInfo.setDateDue(str1);
      localWireInfo.setSettlementDate(str2);
      localWireInfo.setProcessedBy(paramSecureUser.getUserName());
      localWireInfo.setWirePayeeId(null);
      localWireInfo.setWirePayeeInfo(null);
      populateOBOAgentInfo(paramSecureUser, localWireInfo);
      DebugLog.log(Level.INFO, "*** BPW.AddWireInfo: Adding WireInfo = [" + localWireInfo.toString() + "]");
      localWireInfo = localBPWServices.addWireTransfer(localWireInfo);
      i = a(paramWireTransfer, localWireInfo, paramHashMap);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      i = jdMethod_do(i, 31003);
      throw new CSILException(-1009, i);
    }
    return paramWireTransfer;
  }
  
  public void modifyHostWire(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    int i = -1;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      paramWireTransfer.setUserID(paramSecureUser.getProfileID());
      paramWireTransfer.setSubmittedBy(paramSecureUser.getProfileID());
      String str1 = formatDate.format(paramWireTransfer.getDueDateValue().getTime());
      String str2 = "";
      if (paramWireTransfer.getSettlementDateValue() != null) {
        str2 = formatDate.format(paramWireTransfer.getSettlementDateValue().getTime());
      }
      WireInfo localWireInfo = paramWireTransfer.getWireInfo();
      localWireInfo.setTrnId(TrackingIDGenerator.GetNextID());
      localWireInfo.setDateDue(str1);
      localWireInfo.setSettlementDate(str2);
      localWireInfo.setProcessedBy(paramSecureUser.getUserName());
      localWireInfo.setWirePayeeId(null);
      localWireInfo.setWirePayeeInfo(null);
      populateOBOAgentInfo(paramSecureUser, localWireInfo);
      DebugLog.log(Level.INFO, "*** BPW.ModifyWireInfo: Modifying WireInfo = [" + localWireInfo.toString() + "]");
      localWireInfo = localBPWServices.modWireTransfer(localWireInfo);
      i = a(paramWireTransfer, localWireInfo, paramHashMap);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0)
    {
      i = jdMethod_do(i, 31005);
      throw new CSILException(-1009, i);
    }
  }
  
  public Date getSmartDate(SecureUser paramSecureUser, DateTime paramDateTime)
    throws CSILException
  {
    int i = 0;
    if (paramDateTime == null) {
      return new Date();
    }
    DateTime localDateTime = (DateTime)paramDateTime.clone();
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      String str = formatDate.format(localDateTime.getTime());
      i = localBPWServices.getSmartDate(paramSecureUser.getBPWFIID(), Integer.parseInt(str));
      localDateTime.setFormat("yyyyMMdd");
      localDateTime.fromString(String.valueOf(i));
      localDateTime.setFormat("SHORT");
    }
    catch (Exception localException)
    {
      DebugLog.log("WireTransferService.getSmartDate: Failed to get smartDate  for specified date " + localDateTime.toString());
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return localDateTime.getTime();
  }
  
  public WireTransferBank getBPWFIById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    WireTransferBank localWireTransferBank = null;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      BPWFIInfo localBPWFIInfo = localBPWServices.getBPWFIInfo(paramString);
      if (localBPWFIInfo != null)
      {
        localWireTransferBank = new WireTransferBank();
        localWireTransferBank.setBPWFIInfo(localBPWFIInfo);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new CSILException(-1009, 31022);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return localWireTransferBank;
  }
  
  public WireHistories getCompletedWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    return jdMethod_do(paramSecureUser, paramPagingContext, 6, paramHashMap);
  }
  
  public WireHistories getPendingWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    return jdMethod_do(paramSecureUser, paramPagingContext, 5, paramHashMap);
  }
  
  public WireHistories getApprovalWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    return jdMethod_do(paramSecureUser, paramPagingContext, 7, paramHashMap);
  }
  
  public WireBatch getWireBatchById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    WireBatch localWireBatch = new WireBatch();
    WireBatchInfo[] arrayOfWireBatchInfo = null;
    WireBatchInfo localWireBatchInfo = new WireBatchInfo();
    localWireBatchInfo.setBatchId(paramString);
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      arrayOfWireBatchInfo = localBPWServices.getWireTransferBatch(localWireBatchInfo);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("getWireBatchByID ", localException);
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if ((arrayOfWireBatchInfo != null) && (arrayOfWireBatchInfo.length > 0))
    {
      localWireBatchInfo = arrayOfWireBatchInfo[0];
      localWireBatch.setBatchInfo(localWireBatchInfo);
    }
    return localWireBatch;
  }
  
  private WireTransfers jdMethod_if(SecureUser paramSecureUser, PagingContext paramPagingContext, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool1 = false;
    boolean bool2 = false;
    WireTransfers localWireTransfers = new WireTransfers(paramSecureUser.getLocale());
    localWireTransfers.setPagingContext(paramPagingContext);
    getClass();
    int i = 10;
    try
    {
      i = jdMethod_try(paramHashMap);
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("GetWiresPage ", localException1);
    }
    BPWHist localBPWHist = new BPWHist();
    localBPWHist.setPageSize(i);
    localBPWHist.setVersion("200");
    HashMap localHashMap1 = paramPagingContext.getMap();
    if (localHashMap1 == null) {
      localHashMap1 = new HashMap();
    }
    paramPagingContext.setMap(localHashMap1);
    Object localObject2;
    Object localObject3;
    if (paramPagingContext.getDirection().equalsIgnoreCase("FIRST"))
    {
      localBPWHist.setCursorId(null);
      localBPWHist.setHistId(null);
      localBPWHist.setCustId(Integer.toString(paramSecureUser.getBusinessID()));
      localBPWHist.setFiId(paramSecureUser.getBPWFIID());
      int j = 0;
      if (paramHashMap != null)
      {
        localObject2 = (String)paramHashMap.get("VIEW");
        if ((localObject2 != null) && (((String)localObject2).equals("ALL") == true)) {
          j = 1;
        }
      }
      if (j == 0)
      {
        localObject2 = new String[] { String.valueOf(paramSecureUser.getProfileID()) };
        localBPWHist.setUserId((String[])localObject2);
      }
      if (paramHashMap != null)
      {
        localObject2 = (String)paramHashMap.get("DESTINATION");
        if ((localObject2 != null) && (((String)localObject2).length() > 0))
        {
          localObject3 = new String[] { localObject2 };
          localBPWHist.setDest((String[])localObject3);
        }
      }
      localObject2 = new ArrayList();
      ((ArrayList)localObject2).add("0");
      localHashMap1.put("PAGELIST", localObject2);
      bool1 = true;
      paramPagingContext.setFirstIndex(0L);
      paramPagingContext.setLastIndex(0L);
      String str1;
      if (paramInt == 1)
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject3 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localBPWHist.setStartDate((String)localObject3);
        }
        else
        {
          localObject3 = GregorianCalendar.getInstance();
          str1 = formatDate.format(((Calendar)localObject3).getTime());
          localBPWHist.setStartDate(str1);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject3 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localBPWHist.setEndDate((String)localObject3);
        }
        else
        {
          localObject3 = GregorianCalendar.getInstance();
          ((Calendar)localObject3).add(5, 30);
          str1 = formatDate.format(((Calendar)localObject3).getTime());
          localBPWHist.setEndDate(str1);
        }
        localBPWHist.setStatusList(getBPWStatusList(paramInt));
      }
      else if (paramInt == 5)
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject3 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localBPWHist.setStartDate((String)localObject3);
        }
        else
        {
          localObject3 = GregorianCalendar.getInstance();
          ((Calendar)localObject3).add(5, -30);
          str1 = formatDate.format(((Calendar)localObject3).getTime());
          localBPWHist.setStartDate(str1);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject3 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localBPWHist.setEndDate((String)localObject3);
        }
        else
        {
          localObject3 = GregorianCalendar.getInstance();
          ((Calendar)localObject3).add(5, 30);
          str1 = formatDate.format(((Calendar)localObject3).getTime());
          localBPWHist.setEndDate(str1);
        }
        localBPWHist.setStatusList(getBPWStatusList(paramInt));
      }
      else
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject3 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localBPWHist.setStartDate((String)localObject3);
        }
        else
        {
          localObject3 = GregorianCalendar.getInstance();
          ((Calendar)localObject3).add(5, -30);
          str1 = formatDate.format(((Calendar)localObject3).getTime());
          localBPWHist.setStartDate(str1);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject3 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localBPWHist.setEndDate((String)localObject3);
        }
        else
        {
          localObject3 = GregorianCalendar.getInstance();
          str1 = formatDate.format(((Calendar)localObject3).getTime());
          localBPWHist.setEndDate(str1);
        }
        localObject3 = null;
        if (paramHashMap != null)
        {
          localObject3 = (String[])paramHashMap.get("Statuses");
          if (paramHashMap.get("TemplateID") != null) {
            localBPWHist.setTemplateId((String)paramHashMap.get("TemplateID"));
          }
          if (paramHashMap.get("TransScope") != null) {
            localBPWHist.setTransScope((String)paramHashMap.get("TransScope"));
          }
          if (paramHashMap.get("TransSource") != null) {
            localBPWHist.setTransSource((String)paramHashMap.get("TransSource"));
          }
        }
        if (localObject3 == null) {
          localObject3 = getBPWStatusList(paramInt);
        }
        localBPWHist.setStatusList((String[])localObject3);
      }
    }
    else
    {
      int k;
      if (paramPagingContext.getDirection().equalsIgnoreCase("NEXT"))
      {
        localObject1 = (String)localHashMap1.get("HISTID");
        localObject2 = (String)localHashMap1.get("CURSORID");
        localObject3 = (ArrayList)localHashMap1.get("PAGELIST");
        paramPagingContext.setLastIndex(paramPagingContext.getLastIndex() + i);
        k = new Long(paramPagingContext.getFirstIndex()).intValue();
        k++;
        paramPagingContext.setFirstIndex(k);
        if (k >= ((ArrayList)localObject3).size()) {
          ((ArrayList)localObject3).add(localObject2);
        } else {
          ((ArrayList)localObject3).set(k, localObject2);
        }
        localHashMap1.put("PAGELIST", localObject3);
        localBPWHist.setCursorId((String)localObject2);
        localBPWHist.setHistId((String)localObject1);
        bool1 = false;
      }
      else if (paramPagingContext.getDirection().equalsIgnoreCase("PREVIOUS"))
      {
        localObject1 = (String)localHashMap1.get("HISTID");
        localObject2 = (String)localHashMap1.get("CURSORID");
        localObject3 = (ArrayList)localHashMap1.get("PAGELIST");
        paramPagingContext.setLastIndex(paramPagingContext.getLastIndex() - i);
        k = new Long(paramPagingContext.getFirstIndex()).intValue();
        if (k != 0) {
          k--;
        }
        paramPagingContext.setFirstIndex(k);
        if ((localObject3 != null) && (((ArrayList)localObject3).size() > 0))
        {
          localObject2 = (String)((ArrayList)localObject3).get(k);
          if (((String)localObject2).equals("0")) {
            bool1 = true;
          }
          localHashMap1.put("PAGELIST", localObject3);
        }
        localBPWHist.setCursorId((String)localObject2);
        localBPWHist.setHistId((String)localObject1);
      }
    }
    if (paramHashMap.get("AllWires") != null) {
      localBPWHist.setSelectionCriteria((String)paramHashMap.get("AllWires"));
    }
    Object localObject1 = getBPWHandler();
    if (localObject1 == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      localBPWHist = ((BPWServices)localObject1).getWireHistory(localBPWHist);
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("GetWireTransfersPage ", localException2);
      localException2.printStackTrace(System.err);
      throw new CSILException(-1009, 31000);
    }
    finally
    {
      if (localObject1 != null) {
        removeBPWHandler((BPWServices)localObject1);
      }
    }
    HashMap localHashMap2 = new HashMap();
    if (localBPWHist != null)
    {
      localObject3 = null;
      if ((localBPWHist.getTrans() instanceof WireInfo[])) {
        localObject3 = (WireInfo[])localBPWHist.getTrans();
      } else {
        DebugLog.log("GetWiresPage: No WireInfo record found.");
      }
      localHashMap1.put("HISTID", localBPWHist.getHistId());
      localHashMap1.put("CURSORID", localBPWHist.getCursorId());
      if ((localObject3 != null) && (localObject3.length > 0))
      {
        WireInfo localWireInfo = null;
        Accounts localAccounts = (Accounts)paramHashMap.get("ACCOUNTS");
        for (int m = 0; m < localObject3.length; m++)
        {
          WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.create();
          localWireInfo = localObject3[m];
          a(localWireTransfer, localWireInfo, localAccounts);
          String str2 = localWireInfo.getRecSrvrTid();
          if ((str2 != null) && (str2.length() > 0))
          {
            localWireTransfer.setType(6);
            RecWireInfo localRecWireInfo = (RecWireInfo)localHashMap2.get(str2);
            if (localRecWireInfo == null)
            {
              localRecWireInfo = a(str2, true);
              if ((localRecWireInfo != null) && (localRecWireInfo.getStatusCode() == 0)) {
                localHashMap2.put(str2, localRecWireInfo);
              }
            }
            if (localRecWireInfo.getStatusCode() == 0)
            {
              localWireTransfer.setFrequency(getFrequency(localRecWireInfo.getFrequency()));
              localWireTransfer.setNumberTransfers(localRecWireInfo.getPmtsCount());
            }
          }
        }
      }
      else
      {
        bool2 = true;
      }
      if ((localObject3 == null) || (paramPagingContext.getLastIndex() + localWireTransfers.size() >= localBPWHist.getTotalTrans())) {
        bool2 = true;
      }
    }
    localWireTransfers.getPagingContext().setLastPage(bool2);
    localWireTransfers.getPagingContext().setFirstPage(bool1);
    return localWireTransfers;
  }
  
  private WireBatches a(SecureUser paramSecureUser, PagingContext paramPagingContext, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool1 = false;
    boolean bool2 = false;
    WireBatches localWireBatches = new WireBatches(paramSecureUser.getLocale());
    localWireBatches.setPagingContext(paramPagingContext);
    getClass();
    int i = 10;
    try
    {
      i = jdMethod_try(paramHashMap);
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("GetWireBatchesPage ", localException1);
    }
    BPWHist localBPWHist = new BPWHist();
    localBPWHist.setPageSize(i);
    localBPWHist.setVersion("200");
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null) {
      localHashMap = new HashMap();
    }
    paramPagingContext.setMap(localHashMap);
    Object localObject2;
    Object localObject3;
    int k;
    if (paramPagingContext.getDirection().equalsIgnoreCase("FIRST"))
    {
      localBPWHist.setCursorId(null);
      localBPWHist.setHistId(null);
      localBPWHist.setCustId(Integer.toString(paramSecureUser.getBusinessID()));
      localBPWHist.setFiId(paramSecureUser.getBPWFIID());
      int j = 0;
      if (paramHashMap != null)
      {
        localObject2 = (String)paramHashMap.get("VIEW");
        if ((localObject2 != null) && (((String)localObject2).equals("ALL") == true)) {
          j = 1;
        }
      }
      if (j == 0)
      {
        localObject2 = new String[] { String.valueOf(paramSecureUser.getProfileID()) };
        localBPWHist.setUserId((String[])localObject2);
      }
      if (paramHashMap != null)
      {
        localObject2 = (String)paramHashMap.get("DESTINATION");
        if ((localObject2 != null) && (((String)localObject2).length() > 0))
        {
          localObject3 = new String[] { localObject2 };
          localBPWHist.setDest((String[])localObject3);
        }
      }
      localObject2 = new ArrayList();
      ((ArrayList)localObject2).add("0");
      localHashMap.put("PAGELIST", localObject2);
      bool1 = true;
      paramPagingContext.setFirstIndex(0L);
      paramPagingContext.setLastIndex(0L);
      String str;
      if (paramInt == 1)
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject3 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localBPWHist.setStartDate((String)localObject3);
        }
        else
        {
          localObject3 = GregorianCalendar.getInstance();
          str = formatDate.format(((Calendar)localObject3).getTime());
          localBPWHist.setStartDate(str);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject3 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localBPWHist.setEndDate((String)localObject3);
        }
        else
        {
          localObject3 = GregorianCalendar.getInstance();
          ((Calendar)localObject3).add(5, 30);
          str = formatDate.format(((Calendar)localObject3).getTime());
          localBPWHist.setEndDate(str);
        }
        localObject3 = new String[] { "CREATED", "WILLPROCESSON", "RELEASEPENDING" };
        localBPWHist.setStatusList((String[])localObject3);
      }
      else if (paramInt == 5)
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject3 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localBPWHist.setStartDate((String)localObject3);
        }
        else
        {
          localObject3 = GregorianCalendar.getInstance();
          ((Calendar)localObject3).add(5, -30);
          str = formatDate.format(((Calendar)localObject3).getTime());
          localBPWHist.setStartDate(str);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject3 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localBPWHist.setEndDate((String)localObject3);
        }
        else
        {
          localObject3 = GregorianCalendar.getInstance();
          ((Calendar)localObject3).add(5, 30);
          str = formatDate.format(((Calendar)localObject3).getTime());
          localBPWHist.setEndDate(str);
        }
        localObject3 = new String[] { "CREATED", "WILLPROCESSON", "RELEASEPENDING", "RELEASED", "INFUNDSAPPROVAL", "FUNDSAPPROVED", "FUNDSAPPROVALACTIVE", "IMMED_INPROCESS", "INPROCESS", "PENDING", "BACKENDPENDING", "HELD" };
        localBPWHist.setStatusList((String[])localObject3);
      }
      else
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject3 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localBPWHist.setStartDate((String)localObject3);
        }
        else
        {
          localObject3 = GregorianCalendar.getInstance();
          ((Calendar)localObject3).add(5, -30);
          str = formatDate.format(((Calendar)localObject3).getTime());
          localBPWHist.setStartDate(str);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject3 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localBPWHist.setEndDate((String)localObject3);
        }
        else
        {
          localObject3 = GregorianCalendar.getInstance();
          str = formatDate.format(((Calendar)localObject3).getTime());
          localBPWHist.setEndDate(str);
        }
        localBPWHist.setStatusList(getBPWStatusList(paramInt));
      }
    }
    else if (paramPagingContext.getDirection().equalsIgnoreCase("NEXT"))
    {
      localObject1 = (String)localHashMap.get("HISTID");
      localObject2 = (String)localHashMap.get("CURSORID");
      localObject3 = (ArrayList)localHashMap.get("PAGELIST");
      paramPagingContext.setLastIndex(paramPagingContext.getLastIndex() + i);
      k = new Long(paramPagingContext.getFirstIndex()).intValue();
      k++;
      paramPagingContext.setFirstIndex(k);
      if (k >= ((ArrayList)localObject3).size()) {
        ((ArrayList)localObject3).add(localObject2);
      } else {
        ((ArrayList)localObject3).set(k, localObject2);
      }
      localHashMap.put("PAGELIST", localObject3);
      localBPWHist.setCursorId((String)localObject2);
      localBPWHist.setHistId((String)localObject1);
      bool1 = false;
    }
    else if (paramPagingContext.getDirection().equalsIgnoreCase("PREVIOUS"))
    {
      localObject1 = (String)localHashMap.get("HISTID");
      localObject2 = (String)localHashMap.get("CURSORID");
      localObject3 = (ArrayList)localHashMap.get("PAGELIST");
      paramPagingContext.setLastIndex(paramPagingContext.getLastIndex() - i);
      k = new Long(paramPagingContext.getFirstIndex()).intValue();
      if (k != 0) {
        k--;
      }
      paramPagingContext.setFirstIndex(k);
      if ((localObject3 != null) && (((ArrayList)localObject3).size() > 0))
      {
        localObject2 = (String)((ArrayList)localObject3).get(k);
        if (((String)localObject2).equals("0")) {
          bool1 = true;
        }
        localHashMap.put("PAGELIST", localObject3);
      }
      localBPWHist.setCursorId((String)localObject2);
      localBPWHist.setHistId((String)localObject1);
    }
    Object localObject1 = getBPWHandler();
    if (localObject1 == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      localBPWHist = ((BPWServices)localObject1).getWireBatchHistory(localBPWHist);
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("GetWireBatchesPage ", localException2);
      localException2.printStackTrace(System.err);
      throw new CSILException(-1009, 31017);
    }
    finally
    {
      if (localObject1 != null) {
        removeBPWHandler((BPWServices)localObject1);
      }
    }
    if (localBPWHist != null)
    {
      WireBatchInfo[] arrayOfWireBatchInfo = null;
      if ((localBPWHist.getTrans() instanceof WireBatchInfo[])) {
        arrayOfWireBatchInfo = (WireBatchInfo[])localBPWHist.getTrans();
      } else {
        DebugLog.log("GetWireBatchesPage: No WireBatchInfo record found.");
      }
      localHashMap.put("HISTID", localBPWHist.getHistId());
      localHashMap.put("CURSORID", localBPWHist.getCursorId());
      if (arrayOfWireBatchInfo != null)
      {
        localObject3 = null;
        for (k = 0; k < arrayOfWireBatchInfo.length; k++)
        {
          WireBatch localWireBatch = (WireBatch)localWireBatches.create();
          localWireBatch.setBatchInfo(arrayOfWireBatchInfo[k]);
        }
      }
      else
      {
        bool2 = true;
      }
      if ((arrayOfWireBatchInfo == null) || (paramPagingContext.getLastIndex() + localWireBatches.size() >= localBPWHist.getTotalTrans())) {
        bool2 = true;
      }
    }
    localWireBatches.getPagingContext().setLastPage(bool2);
    localWireBatches.getPagingContext().setFirstPage(bool1);
    return localWireBatches;
  }
  
  private WireTransfers a(SecureUser paramSecureUser, PagingContext paramPagingContext, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool1 = false;
    boolean bool2 = false;
    WireTransfers localWireTransfers = new WireTransfers(paramSecureUser.getLocale());
    localWireTransfers.setPagingContext(paramPagingContext);
    getClass();
    int i = 10;
    try
    {
      i = jdMethod_try(paramHashMap);
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("GetTemplatesPage ", localException1);
    }
    BPWHist localBPWHist = new BPWHist();
    localBPWHist.setPageSize(i);
    localBPWHist.setVersion("200");
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null) {
      localHashMap = new HashMap();
    }
    paramPagingContext.setMap(localHashMap);
    Object localObject2;
    Object localObject3;
    if (paramPagingContext.getDirection().equalsIgnoreCase("FIRST"))
    {
      localBPWHist.setCursorId(null);
      localBPWHist.setHistId(null);
      localBPWHist.setCustId(paramWireTransfer.getCustomerID());
      localBPWHist.setFiId(paramSecureUser.getBPWFIID());
      if (paramWireTransfer.getWireScope().equals("USER"))
      {
        localObject1 = new String[] { String.valueOf(paramSecureUser.getProfileID()) };
        localBPWHist.setUserId((String[])localObject1);
      }
      localObject1 = new ArrayList();
      ((ArrayList)localObject1).add("0");
      localHashMap.put("PAGELIST", localObject1);
      bool1 = true;
      paramPagingContext.setFirstIndex(0L);
      paramPagingContext.setLastIndex(0L);
      if (paramPagingContext.getStartDate() != null)
      {
        localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
        localBPWHist.setStartDate((String)localObject2);
      }
      else
      {
        localObject2 = GregorianCalendar.getInstance();
        ((Calendar)localObject2).add(1, -10);
        localObject3 = formatDate.format(((Calendar)localObject2).getTime());
        localBPWHist.setStartDate((String)localObject3);
      }
      if (paramPagingContext.getEndDate() != null)
      {
        localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
        localBPWHist.setEndDate((String)localObject2);
      }
      else
      {
        localObject2 = GregorianCalendar.getInstance();
        ((Calendar)localObject2).add(1, 10);
        localObject3 = formatDate.format(((Calendar)localObject2).getTime());
        localBPWHist.setEndDate((String)localObject3);
      }
      if (paramWireTransfer.getStatus() == 22)
      {
        localObject2 = new String[] { "TEMPLATE" };
        localBPWHist.setStatusList((String[])localObject2);
      }
      else
      {
        localObject2 = new String[] { "TEMPLATE", "RECTEMPLATE" };
        localBPWHist.setStatusList((String[])localObject2);
      }
      localBPWHist.setTransScope(paramWireTransfer.getWireScope());
      localObject2 = paramWireTransfer.getWireDestination();
      if ((localObject2 != null) && (((String)localObject2).length() > 0))
      {
        localObject3 = new String[] { localObject2 };
        localBPWHist.setDest((String[])localObject3);
      }
    }
    else
    {
      int k;
      if (paramPagingContext.getDirection().equalsIgnoreCase("NEXT"))
      {
        localObject1 = (String)localHashMap.get("HISTID");
        localObject2 = (String)localHashMap.get("CURSORID");
        localObject3 = (ArrayList)localHashMap.get("PAGELIST");
        paramPagingContext.setLastIndex(paramPagingContext.getLastIndex() + i);
        k = new Long(paramPagingContext.getFirstIndex()).intValue();
        k++;
        paramPagingContext.setFirstIndex(k);
        if (k >= ((ArrayList)localObject3).size()) {
          ((ArrayList)localObject3).add(localObject2);
        } else {
          ((ArrayList)localObject3).set(k, localObject2);
        }
        localHashMap.put("PAGELIST", localObject3);
        localBPWHist.setCursorId((String)localObject2);
        localBPWHist.setHistId((String)localObject1);
        bool1 = false;
      }
      else if (paramPagingContext.getDirection().equalsIgnoreCase("PREVIOUS"))
      {
        localObject1 = (String)localHashMap.get("HISTID");
        localObject2 = (String)localHashMap.get("CURSORID");
        localObject3 = (ArrayList)localHashMap.get("PAGELIST");
        paramPagingContext.setLastIndex(paramPagingContext.getLastIndex() - i);
        k = new Long(paramPagingContext.getFirstIndex()).intValue();
        if (k != 0) {
          k--;
        }
        paramPagingContext.setFirstIndex(k);
        if ((localObject3 != null) && (((ArrayList)localObject3).size() > 0))
        {
          localObject2 = (String)((ArrayList)localObject3).get(k);
          if (((String)localObject2).equals("0")) {
            bool1 = true;
          }
          localHashMap.put("PAGELIST", localObject3);
        }
        localBPWHist.setCursorId((String)localObject2);
        localBPWHist.setHistId((String)localObject1);
      }
    }
    Object localObject1 = getBPWHandler();
    if (localObject1 == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      localBPWHist = ((BPWServices)localObject1).getWireHistory(localBPWHist);
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("GetTemplatesPage ", localException2);
      localException2.printStackTrace(System.err);
      throw new CSILException(-1009, 31010);
    }
    finally
    {
      if (localObject1 != null) {
        removeBPWHandler((BPWServices)localObject1);
      }
    }
    if (localBPWHist != null)
    {
      Object[] arrayOfObject = localBPWHist.getTrans();
      localHashMap.put("HISTID", localBPWHist.getHistId());
      localHashMap.put("CURSORID", localBPWHist.getCursorId());
      if (arrayOfObject != null) {
        for (int j = 0; j < arrayOfObject.length; j++)
        {
          WireInfo localWireInfo = null;
          RecWireInfo localRecWireInfo = null;
          WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.create();
          if ((arrayOfObject[j] instanceof RecWireInfo))
          {
            localRecWireInfo = (RecWireInfo)arrayOfObject[j];
            localWireTransfer.setRecWireInfo(localRecWireInfo);
            localWireTransfer.setType(6);
          }
          else if ((arrayOfObject[j] instanceof WireInfo))
          {
            localWireInfo = (WireInfo)arrayOfObject[j];
            localWireTransfer.setWireInfo(localWireInfo);
          }
          else
          {
            DebugLog.log("GetTemplatesPage: No WireInfo record found.");
          }
        }
      } else {
        bool2 = true;
      }
      if ((arrayOfObject == null) || (paramPagingContext.getLastIndex() + localWireTransfers.size() >= localBPWHist.getTotalTrans())) {
        bool2 = true;
      }
    }
    localWireTransfers.getPagingContext().setLastPage(bool2);
    localWireTransfers.getPagingContext().setFirstPage(bool1);
    return localWireTransfers;
  }
  
  private WireInfo jdMethod_case(String paramString)
    throws CSILException
  {
    WireInfo localWireInfo = null;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      localWireInfo = localBPWServices.getWireTransferById(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("GetWireByID ", localException);
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    int i = localWireInfo.getStatusCode();
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "Unable to retrieve Wire with id " + paramString);
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localWireInfo.getStatusMsg());
      i = jdMethod_do(i, 31000);
      throw new CSILException(-1009, i);
    }
    return localWireInfo;
  }
  
  private RecWireInfo a(String paramString, boolean paramBoolean)
    throws CSILException
  {
    RecWireInfo localRecWireInfo = null;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      localRecWireInfo = localBPWServices.getRecWireTransferById(paramString, paramBoolean);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    int i = localRecWireInfo.getStatusCode();
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "Unable to retrieve Recurring Wire with id " + paramString);
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localRecWireInfo.getStatusMsg());
      i = jdMethod_do(i, 31000);
      throw new CSILException(-1009, i);
    }
    return localRecWireInfo;
  }
  
  private WireInfo[] a(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    BPWHist localBPWHist = new BPWHist();
    boolean bool = true;
    if ((paramHashMap != null) && (paramHashMap.get("ReleaseOwnWires") != null)) {
      bool = Boolean.valueOf((String)paramHashMap.get("ReleaseOwnWires")).booleanValue();
    }
    String[] arrayOfString = { "CREATED", "RELEASEPENDING" };
    localBPWHist.setStatusList(arrayOfString);
    Calendar localCalendar1 = null;
    Calendar localCalendar2 = null;
    String str1 = null;
    if ((paramHashMap != null) && (paramHashMap.get("START_DATE") != null))
    {
      localCalendar1 = (Calendar)paramHashMap.get("START_DATE");
    }
    else
    {
      localCalendar1 = GregorianCalendar.getInstance();
      localCalendar1.add(1, -10);
    }
    str1 = formatDate.format(localCalendar1.getTime());
    localBPWHist.setStartDate(str1);
    if ((paramHashMap != null) && (paramHashMap.get("END_DATE") != null)) {
      localCalendar2 = (Calendar)paramHashMap.get("END_DATE");
    } else {
      localCalendar2 = GregorianCalendar.getInstance();
    }
    str1 = formatDate.format(localCalendar2.getTime());
    localBPWHist.setEndDate(str1);
    localBPWHist.setCustId(Integer.toString(paramSecureUser.getBusinessID()));
    localBPWHist.setFiId(paramSecureUser.getBPWFIID());
    localBPWHist.setSelectionCriteria("all");
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      localBPWHist = localBPWServices.getWireHistory(localBPWHist);
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("GetReleaseWires ", localException1);
      localException1.printStackTrace(System.err);
      throw new CSILException(-1009, 31000);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    WireInfo[] arrayOfWireInfo = null;
    if (localBPWHist != null)
    {
      try
      {
        localBPWHist.setTrans(ArrayUtil.convertReferences(localBPWHist.getTrans(), new WireInfo[0].getClass()));
      }
      catch (Exception localException2)
      {
        DebugLog.throwing("GetReleaseWires ", localException2);
        localException2.printStackTrace(System.err);
        throw new CSILException(-1009, 31000);
      }
      if ((localBPWHist.getTrans() instanceof WireInfo[])) {
        arrayOfWireInfo = (WireInfo[])localBPWHist.getTrans();
      } else {
        DebugLog.log("*** WARNING: In GetReleaseWires, NO WireInfo object found!");
      }
    }
    if ((!bool) && (arrayOfWireInfo != null))
    {
      ArrayList localArrayList = new ArrayList();
      for (int i = 0; i < arrayOfWireInfo.length; i++)
      {
        WireInfo localWireInfo = arrayOfWireInfo[i];
        String str2 = String.valueOf(paramSecureUser.getProfileID());
        if ((localWireInfo.getSubmitedBy() != null) && (!localWireInfo.getSubmitedBy().equals(str2))) {
          localArrayList.add(localWireInfo);
        }
      }
      arrayOfWireInfo = (WireInfo[])localArrayList.toArray(new WireInfo[0]);
    }
    return arrayOfWireInfo;
  }
  
  private WireInfo[] jdMethod_byte(String paramString)
    throws CSILException
  {
    WireInfo[] arrayOfWireInfo = null;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      arrayOfWireInfo = localBPWServices.getBatchWires(paramString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return arrayOfWireInfo;
  }
  
  private static void a(WireTransfer paramWireTransfer, WireInfo paramWireInfo, Accounts paramAccounts)
  {
    paramWireTransfer.setWireInfo(paramWireInfo);
    if (((paramWireTransfer.getStatus() == 1) || (paramWireTransfer.getStatus() == 21)) && (paramAccounts != null) && (paramAccounts.size() > 0))
    {
      Account localAccount = paramAccounts.getByAccountNumberAndType(paramWireTransfer.getFromAccountNum(), paramWireTransfer.getFromAccountType());
      if ((localAccount != null) || (paramWireTransfer.getWireSource().equals("HOST")))
      {
        paramWireTransfer.setCanEdit(true);
        paramWireTransfer.setCanDelete(true);
      }
    }
  }
  
  private static void a(WireTransfer paramWireTransfer, RecWireInfo paramRecWireInfo, Accounts paramAccounts)
  {
    paramWireTransfer.setRecWireInfo(paramRecWireInfo);
    if (((paramWireTransfer.getStatus() == 1) || (paramWireTransfer.getStatus() == 21)) && (paramAccounts != null) && (paramAccounts.size() > 0))
    {
      Account localAccount = paramAccounts.getByAccountNumberAndType(paramWireTransfer.getFromAccountNum(), paramWireTransfer.getFromAccountType());
      if (localAccount != null)
      {
        paramWireTransfer.setCanEdit(true);
        paramWireTransfer.setCanDelete(true);
      }
    }
  }
  
  private static int a(WireTransfer paramWireTransfer, WireInfo paramWireInfo, HashMap paramHashMap)
  {
    int i = paramWireInfo.getStatusCode();
    Object localObject1;
    Object localObject2;
    if (i == 0)
    {
      paramWireTransfer.setID(paramWireInfo.getSrvrTid());
      paramWireTransfer.setReferenceNumber(paramWireInfo.getSrvrTid());
      paramWireTransfer.setStatus(WireStatusMap.mapStatusToInt(paramWireInfo.getPrcStatus()));
      if ((paramWireTransfer.getStatus() == 1) || (paramWireTransfer.getStatus() == 21))
      {
        paramWireTransfer.setCanEdit(true);
        paramWireTransfer.setCanDelete(true);
      }
      paramWireTransfer.setDateFormat("yyyyMMdd");
      localObject1 = paramWireInfo.getDateToPost();
      if ((localObject1 != null) && (((String)localObject1).length() > 8)) {
        localObject1 = ((String)localObject1).substring(0, 8);
      }
      paramWireTransfer.setDateToPost((String)localObject1);
      if (paramWireInfo.getLogDate() != null) {
        paramWireTransfer.setLogDate(paramWireInfo.getLogDate().toString().substring(0, 19));
      } else {
        paramWireTransfer.setLogDate("0000/00/00 00:00:00");
      }
      localObject1 = paramWireInfo.getSettlementDate();
      if ((localObject1 != null) && (((String)localObject1).length() > 8)) {
        localObject1 = ((String)localObject1).substring(0, 8);
      }
      paramWireTransfer.setSettlementDate((String)localObject1);
      paramWireTransfer.setDateFormat("SHORT");
      paramWireTransfer.setConfirmationMsg(paramWireInfo.getConfirmMsg());
      paramWireTransfer.setConfirmationNum(paramWireInfo.getConfirmNum());
      paramWireTransfer.setFedConfirmationNum(paramWireInfo.getConfirmNum2());
      if ((paramWireTransfer.getWireType() != null) && ((paramWireTransfer.getWireType().equals("TEMPLATE")) || (paramWireTransfer.getWireType().equals("RECTEMPLATE"))))
      {
        paramWireTransfer.setTemplateID(paramWireInfo.getTemplateId());
        localObject2 = paramWireTransfer.getWireScope();
        if ((localObject2 != null) && ((((String)localObject2).equals("USER")) || (((String)localObject2).equals("BUSINESS"))))
        {
          paramWireTransfer.setCanEdit(true);
          paramWireTransfer.setCanDelete(true);
        }
      }
      localObject2 = paramWireTransfer.getWirePayee();
      if (localObject2 != null) {
        ((WireTransferPayee)localObject2).setID(paramWireInfo.getWirePayeeId());
      }
    }
    else
    {
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + paramWireInfo.getStatusMsg());
      if (i == 17521)
      {
        localObject1 = paramWireInfo.getExtInfo();
        if (localObject1 != null)
        {
          localObject2 = (Limits)((Hashtable)localObject1).get("NOT_ALLOWED_APPROVAL_LIMITS");
          if (localObject2 != null)
          {
            if (paramHashMap == null) {
              paramHashMap = new HashMap();
            }
            paramHashMap.put("ExceededLimits", localObject2);
          }
        }
      }
    }
    return i;
  }
  
  private static int a(WireBatch paramWireBatch, WireBatchInfo paramWireBatchInfo)
  {
    if (paramWireBatchInfo.getStatusCode() == 0)
    {
      paramWireBatch.setBatchID(paramWireBatchInfo.getBatchId());
      paramWireBatch.setReferenceNumber(paramWireBatchInfo.getBatchId());
      paramWireBatch.setStatus(WireStatusMap.mapStatusToInt(paramWireBatchInfo.getPrcStatus()));
      paramWireBatch.setConfirmationMsg(paramWireBatchInfo.getConfirmMsg());
      paramWireBatch.setConfirmationNum(paramWireBatchInfo.getConfirmNum());
      paramWireBatch.setFedConfirmationNum(paramWireBatchInfo.getConfirmNum2());
      if ((paramWireBatch.getStatus() == 1) || (paramWireBatch.getStatus() == 21))
      {
        paramWireBatch.setCanEdit(true);
        paramWireBatch.setCanDelete(true);
      }
      paramWireBatch.setDateFormat("yyyyMMdd");
      String str = paramWireBatchInfo.getDateToPost();
      if ((str != null) && (str.length() > 8)) {
        str = str.substring(0, 8);
      }
      paramWireBatch.setDateToPost(str);
      str = paramWireBatchInfo.getDateToPost();
      if (str != null) {
        str = str.toString();
      }
      paramWireBatch.setProcessTime("0000/00/00 00:00:00");
      str = paramWireBatchInfo.getSettlementDate();
      if ((str != null) && (str.length() > 8)) {
        str = str.substring(0, 8);
      }
      paramWireBatch.setSettlementDate(str);
      paramWireBatch.setDateFormat("SHORT");
      WireInfo[] arrayOfWireInfo = paramWireBatchInfo.getWires();
      WireTransfers localWireTransfers = paramWireBatch.getWires();
      if ((localWireTransfers != null) && (arrayOfWireInfo != null)) {
        for (int i = 0; i < localWireTransfers.size(); i++)
        {
          WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.get(i);
          WireInfo localWireInfo = arrayOfWireInfo[i];
          localWireTransfer.setID(localWireInfo.getSrvrTid());
          localWireTransfer.setReferenceNumber(localWireInfo.getSrvrTid());
          localWireTransfer.setConfirmationNum(localWireInfo.getConfirmNum());
          localWireTransfer.setFedConfirmationNum(localWireInfo.getConfirmNum2());
          localWireTransfer.setStatus(WireStatusMap.mapStatusToInt(localWireInfo.getPrcStatus()));
          WireTransferPayee localWireTransferPayee = localWireTransfer.getWirePayee();
          if (localWireTransferPayee != null) {
            localWireTransferPayee.setID(localWireInfo.getWirePayeeId());
          }
        }
      }
    }
    else
    {
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + paramWireBatchInfo.getStatusCode());
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + paramWireBatchInfo.getStatusMsg());
    }
    return paramWireBatchInfo.getStatusCode();
  }
  
  private int jdMethod_try(HashMap paramHashMap)
  {
    int i = 10;
    if (paramHashMap != null)
    {
      String str = (String)paramHashMap.get("PAGESIZE");
      if (str != null) {
        try
        {
          i = Integer.parseInt(str);
        }
        catch (Exception localException) {}
      }
    }
    return i;
  }
  
  public void processOFXRequest() {}
  
  protected BPWServices getBPWHandler()
  {
    BPWServices localBPWServices = null;
    ContextPool localContextPool = null;
    Context localContext = null;
    for (int i = 0; i < this.provider_url_list.size(); i++)
    {
      String str = (String)this.provider_url_list.get(i);
      try
      {
        if (debugService)
        {
          DebugLog.log(Level.INFO, "##################### Ping BPW Server ################");
          DebugLog.log(Level.INFO, "provider_url = " + str);
        }
        if (!ping(str)) {
          continue;
        }
      }
      catch (CSILException localCSILException)
      {
        continue;
      }
      try
      {
        if (debugService)
        {
          DebugLog.log(Level.INFO, "##################### Getting BPW Handler ################");
          DebugLog.log(Level.INFO, "provider_url = " + str);
        }
        localContextPool = getContextPool(str);
        localContext = localContextPool.getContext();
        Object localObject1 = localContext.lookup(this.cG);
        localObject2 = (BPWServicesHome)PortableRemoteObject.narrow(localObject1, BPWServicesHome.class);
        localBPWServices = ((BPWServicesHome)localObject2).create();
        localContextPool.returnContext(localContext);
        return localBPWServices;
      }
      catch (Throwable localThrowable)
      {
        Object localObject2;
        DebugLog.throwing("Couldn't get handler in BankSim service", localThrowable);
        if ((localContext != null) && (localContextPool != null)) {
          try
          {
            localContext = localContextPool.refreshContext(localContext);
            localObject2 = localContext.lookup(this.cG);
            BPWServicesHome localBPWServicesHome = (BPWServicesHome)PortableRemoteObject.narrow(localObject2, BPWServicesHome.class);
            localBPWServices = localBPWServicesHome.create();
            localContextPool.returnContext(localContext);
            return localBPWServices;
          }
          catch (Exception localException)
          {
            DebugLog.throwing("Couldn't refresh the contexts for " + str, localException);
            if ((localContextPool != null) && (localContext != null)) {
              localContextPool.returnContext(localContext);
            }
          }
        }
      }
    }
    return null;
  }
  
  protected static void removeBPWHandler(BPWServices paramBPWServices)
  {
    if (paramBPWServices != null) {
      try
      {
        if (debugService) {
          DebugLog.log(Level.INFO, "##################### Removing BPW Handler ################");
        }
        paramBPWServices.remove();
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("Error removing bpw handler", localThrowable);
      }
    }
  }
  
  public static String[] getBPWStatusList(int paramInt)
  {
    if (paramInt == 1)
    {
      arrayOfString = new String[] { "CREATED", "WILLPROCESSON", "RELEASEPENDING" };
      return arrayOfString;
    }
    if (paramInt == 5)
    {
      arrayOfString = new String[] { "CREATED", "WILLPROCESSON", "RELEASEPENDING", "RELEASED", "INFUNDSAPPROVAL", "FUNDSAPPROVED", "FUNDSPENDING", "FUNDSAPPROVALACTIVE", "IMMED_INPROCESS", "INPROCESS", "PENDING", "BACKENDPENDING", "HELD" };
      return arrayOfString;
    }
    if (paramInt == 2)
    {
      arrayOfString = new String[] { "ACCEPTED", "CONFIRMED", "POSTEDON", "COMPLETED" };
      return arrayOfString;
    }
    if (paramInt == 3)
    {
      arrayOfString = new String[] { "INFUNDSAPPROVAL", "FUNDSAPPROVED", "FUNDSAPPROVALACTIVE", "FUNDSPENDING", "IMMED_INPROCESS", "INPROCESS", "BACKENDPENDING", "PENDING", "RELEASED", "HELD" };
      return arrayOfString;
    }
    if (paramInt == 4)
    {
      arrayOfString = new String[] { "FAILED", "FAILEDON", "REJECTED", "BACKENDFAILED", "BACKENDFAILED_NOTIF", "BACKENDREJECTED", "BACKENDREJECTED_NOTIF", "NOFUNDS", "NOFUNDS_NOTIF", "FUNDSFAILEDON", "FUNDSFAILEDON_NOTIF", "INFUNDSREVERT", "FUNDSREVERTACTIVE", "FUNDSREVERTFAILED", "FUNDSREVERTFAILED_NOTIF", "FUNDSREVERTED", "FUNDSREVERTED_NOTIF", "RELEASEFAILED", "RELEASEREJECTED", "LIMIT_CHECK_FAILED", "LIMIT_REVERT_FAILED", "APPROVAL_FAILED", "FAILEDON", "FAILEDON_NOTIF" };
      return arrayOfString;
    }
    if (paramInt == 6)
    {
      arrayOfString = new String[] { "ACCEPTED", "CONFIRMED", "ACKNOWLEDGED", "POSTEDON", "COMPLETED", "BACKENDREJECTED", "BACKENDREJECTED_NOTIF", "BACKENDFAILED", "BACKENDFAILED_NOTIF", "FAILED", "FAILEDON", "REJECTED", "NOFUNDS", "NOFUNDS_NOTIF", "FUNDSFAILEDON", "FUNDSFAILEDON_NOTIF", "INFUNDSREVERT", "FUNDSREVERTACTIVE", "FUNDSREVERTFAILED", "FUNDSREVERTFAILED_NOTIF", "FUNDSREVERTED", "FUNDSREVERTED_NOTIF", "RELEASEFAILED", "RELEASEREJECTED", "LIMIT_CHECK_FAILED", "LIMIT_REVERT_FAILED", "APPROVAL_FAILED", "FAILEDON", "FAILEDON_NOTIF" };
      return arrayOfString;
    }
    String[] arrayOfString = new String[0];
    return arrayOfString;
  }
  
  public void addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      HistoryAdapter.addHistory(paramHistories);
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Exception in WireTransferService.AddHistory!");
      throw new CSILException(-1009, 31020);
    }
  }
  
  public WireTransfers getAuditHistoryById(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    WireTransfers localWireTransfers = null;
    try
    {
      localWireTransfers = WireAdapter.getAuditHistoryById(paramString1, paramString2);
    }
    catch (ProfileException localProfileException)
    {
      localProfileException.printStackTrace();
    }
    return localWireTransfers;
  }
  
  public WireTransfer processApprovalResult(SecureUser paramSecureUser, WireTransfer paramWireTransfer, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      localBPWServices.processApprovalResult("WIRETRN", paramWireTransfer.getID(), paramString, paramHashMap);
    }
    catch (FFSException localFFSException)
    {
      localFFSException.printStackTrace(System.err);
      int i = jdMethod_do(localFFSException.getErrCode(), 30213);
      throw new CSILException(-1009, i);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
      throw new CSILException(-1009, 31003);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return paramWireTransfer;
  }
  
  public WireTransferBanks getBPWFIs()
    throws CSILException
  {
    WireTransferBanks localWireTransferBanks = new WireTransferBanks();
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      BPWFIInfo[] arrayOfBPWFIInfo = localBPWServices.getBPWFIInfoByStatus("ACTIVE");
      if ((arrayOfBPWFIInfo != null) && (arrayOfBPWFIInfo.length > 0)) {
        for (int i = 0; i < arrayOfBPWFIInfo.length; i++)
        {
          WireTransferBank localWireTransferBank = localWireTransferBanks.create();
          localWireTransferBank.setBPWFIInfo(arrayOfBPWFIInfo[i]);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new CSILException(-1009, 31022);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return localWireTransferBanks;
  }
  
  public HashMap getWiresConfiguration(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    HashMap localHashMap = null;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      localHashMap = localBPWServices.getWiresConfiguration();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new CSILException(-1009, 31022);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    return localHashMap;
  }
  
  public boolean isBatchDeletable(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    if (paramString == null) {
      throw new CSILException(-1009, 3003);
    }
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      boolean bool = localBPWServices.isWireBatchDeleteable(paramString);
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new CSILException(-1009, 31022);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
  }
  
  private WireHistories jdMethod_do(SecureUser paramSecureUser, PagingContext paramPagingContext, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool1 = false;
    boolean bool2 = false;
    WireHistories localWireHistories = new WireHistories(paramSecureUser.getLocale());
    localWireHistories.setPagingContext(paramPagingContext);
    getClass();
    int i = 10;
    try
    {
      i = jdMethod_try(paramHashMap);
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("getWireHistoriesPage ", localException1);
    }
    int j = 0;
    BPWHist localBPWHist = new BPWHist();
    localBPWHist.setPageSize(i);
    localBPWHist.setVersion("200");
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null) {
      localHashMap = new HashMap();
    }
    paramPagingContext.setMap(localHashMap);
    Object localObject2;
    Object localObject3;
    int k;
    if (paramPagingContext.getDirection().equalsIgnoreCase("FIRST"))
    {
      localBPWHist.setCursorId(null);
      localBPWHist.setHistId(null);
      localBPWHist.setCustId(Integer.toString(paramSecureUser.getBusinessID()));
      localBPWHist.setFiId(paramSecureUser.getBPWFIID());
      if (paramHashMap != null)
      {
        localObject1 = (String)paramHashMap.get("VIEW");
        if ((localObject1 != null) && (((String)localObject1).equals("ALL") == true)) {
          j = 1;
        }
      }
      if (j == 0)
      {
        localObject1 = new String[] { String.valueOf(paramSecureUser.getProfileID()) };
        localBPWHist.setUserId((String[])localObject1);
      }
      if (paramHashMap != null)
      {
        localObject1 = (String)paramHashMap.get("DESTINATION");
        if ((localObject1 != null) && (((String)localObject1).length() > 0))
        {
          localObject2 = new String[] { localObject1 };
          localBPWHist.setDest((String[])localObject2);
        }
      }
      localObject1 = new ArrayList();
      ((ArrayList)localObject1).add("0");
      localHashMap.put("PAGELIST", localObject1);
      bool1 = true;
      paramPagingContext.setFirstIndex(0L);
      paramPagingContext.setLastIndex(0L);
      if (paramInt == 1)
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localBPWHist.setStartDate((String)localObject2);
        }
        else
        {
          localObject2 = GregorianCalendar.getInstance();
          localObject3 = formatDate.format(((Calendar)localObject2).getTime());
          localBPWHist.setStartDate((String)localObject3);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localBPWHist.setEndDate((String)localObject2);
        }
        else
        {
          localObject2 = GregorianCalendar.getInstance();
          ((Calendar)localObject2).add(5, 30);
          localObject3 = formatDate.format(((Calendar)localObject2).getTime());
          localBPWHist.setEndDate((String)localObject3);
        }
        localObject2 = new String[] { "CREATED", "WILLPROCESSON", "RELEASEPENDING" };
        localBPWHist.setStatusList((String[])localObject2);
      }
      else if (paramInt == 7)
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localBPWHist.setStartDate((String)localObject2);
        }
        else
        {
          localObject2 = GregorianCalendar.getInstance();
          ((Calendar)localObject2).add(5, -30);
          localObject3 = formatDate.format(((Calendar)localObject2).getTime());
          localBPWHist.setStartDate((String)localObject3);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localBPWHist.setEndDate((String)localObject2);
        }
        else
        {
          localObject2 = GregorianCalendar.getInstance();
          ((Calendar)localObject2).add(5, 30);
          localObject3 = formatDate.format(((Calendar)localObject2).getTime());
          localBPWHist.setEndDate((String)localObject3);
        }
        localObject2 = new String[] { "APPROVAL_PENDING", "APPROVAL_REJECTED" };
        localBPWHist.setStatusList((String[])localObject2);
      }
      else if (paramInt == 5)
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localBPWHist.setStartDate((String)localObject2);
        }
        else
        {
          localObject2 = GregorianCalendar.getInstance();
          ((Calendar)localObject2).add(5, -30);
          localObject3 = formatDate.format(((Calendar)localObject2).getTime());
          localBPWHist.setStartDate((String)localObject3);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localBPWHist.setEndDate((String)localObject2);
        }
        else
        {
          localObject2 = GregorianCalendar.getInstance();
          ((Calendar)localObject2).add(5, 30);
          localObject3 = formatDate.format(((Calendar)localObject2).getTime());
          localBPWHist.setEndDate((String)localObject3);
        }
        localObject2 = new String[] { "CREATED", "WILLPROCESSON", "RELEASEPENDING", "RELEASED", "INFUNDSAPPROVAL", "FUNDSAPPROVED", "FUNDSPENDING", "FUNDSAPPROVALACTIVE", "IMMED_INPROCESS", "INPROCESS", "PENDING", "BACKENDPENDING", "HELD" };
        localBPWHist.setStatusList((String[])localObject2);
      }
      else
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localBPWHist.setStartDate((String)localObject2);
        }
        else
        {
          localObject2 = GregorianCalendar.getInstance();
          ((Calendar)localObject2).add(5, -30);
          localObject3 = formatDate.format(((Calendar)localObject2).getTime());
          localBPWHist.setStartDate((String)localObject3);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localBPWHist.setEndDate((String)localObject2);
        }
        else
        {
          localObject2 = GregorianCalendar.getInstance();
          localObject3 = formatDate.format(((Calendar)localObject2).getTime());
          localBPWHist.setEndDate((String)localObject3);
        }
        localObject2 = null;
        if (paramHashMap != null)
        {
          localObject2 = (String[])paramHashMap.get("Statuses");
          if (paramHashMap.get("TemplateID") != null) {
            localBPWHist.setTemplateId((String)paramHashMap.get("TemplateID"));
          }
          if (paramHashMap.get("TransScope") != null) {
            localBPWHist.setTransScope((String)paramHashMap.get("TransScope"));
          }
          if (paramHashMap.get("TransSource") != null) {
            localBPWHist.setTransSource((String)paramHashMap.get("TransSource"));
          }
        }
        if (localObject2 == null) {
          localObject2 = getBPWStatusList(paramInt);
        }
        localBPWHist.setStatusList((String[])localObject2);
      }
    }
    else if (paramPagingContext.getDirection().equalsIgnoreCase("NEXT"))
    {
      localObject1 = (String)localHashMap.get("HISTID");
      localObject2 = (String)localHashMap.get("CURSORID");
      localObject3 = (ArrayList)localHashMap.get("PAGELIST");
      paramPagingContext.setLastIndex(paramPagingContext.getLastIndex() + i);
      k = new Long(paramPagingContext.getFirstIndex()).intValue();
      k++;
      paramPagingContext.setFirstIndex(k);
      if (k >= ((ArrayList)localObject3).size()) {
        ((ArrayList)localObject3).add(localObject2);
      } else {
        ((ArrayList)localObject3).set(k, localObject2);
      }
      localHashMap.put("PAGELIST", localObject3);
      localBPWHist.setCursorId((String)localObject2);
      localBPWHist.setHistId((String)localObject1);
      bool1 = false;
    }
    else if (paramPagingContext.getDirection().equalsIgnoreCase("PREVIOUS"))
    {
      localObject1 = (String)localHashMap.get("HISTID");
      localObject2 = (String)localHashMap.get("CURSORID");
      localObject3 = (ArrayList)localHashMap.get("PAGELIST");
      paramPagingContext.setLastIndex(paramPagingContext.getLastIndex() - i);
      k = new Long(paramPagingContext.getFirstIndex()).intValue();
      if (k != 0) {
        k--;
      }
      paramPagingContext.setFirstIndex(k);
      if ((localObject3 != null) && (((ArrayList)localObject3).size() > 0))
      {
        localObject2 = (String)((ArrayList)localObject3).get(k);
        if (((String)localObject2).equals("0")) {
          bool1 = true;
        }
        localHashMap.put("PAGELIST", localObject3);
      }
      localBPWHist.setCursorId((String)localObject2);
      localBPWHist.setHistId((String)localObject1);
    }
    Object localObject1 = getBPWHandler();
    if (localObject1 == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      localBPWHist = ((BPWServices)localObject1).getCombinedWireHistory(localBPWHist);
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("getWireHistoriesPage ", localException2);
      localException2.printStackTrace(System.err);
      throw new CSILException(-1009, 31000);
    }
    finally
    {
      if (localObject1 != null) {
        removeBPWHandler((BPWServices)localObject1);
      }
    }
    if (localBPWHist != null)
    {
      WireHistoryInfo[] arrayOfWireHistoryInfo = null;
      if ((localBPWHist.getTrans() instanceof WireHistoryInfo[])) {
        arrayOfWireHistoryInfo = (WireHistoryInfo[])localBPWHist.getTrans();
      } else {
        DebugLog.log("getWireHistoriesPage: No WireHistoryInfo record found.");
      }
      localHashMap.put("HISTID", localBPWHist.getHistId());
      localHashMap.put("CURSORID", localBPWHist.getCursorId());
      if ((arrayOfWireHistoryInfo != null) && (arrayOfWireHistoryInfo.length > 0))
      {
        localObject3 = null;
        for (k = 0; k < arrayOfWireHistoryInfo.length; k++)
        {
          WireHistory localWireHistory = (WireHistory)localWireHistories.create();
          localObject3 = arrayOfWireHistoryInfo[k];
          localWireHistory.setWireHistoryInfo((WireHistoryInfo)localObject3);
          if (localWireHistory.getTransType().equals("BATCH"))
          {
            localWireHistory.setFromAccountID("BATCH");
            localWireHistory.setPayeeID("BATCH");
          }
          else if (localWireHistory.getDestination().equals("HOST"))
          {
            localWireHistory.setFromAccountID("HOST");
            localWireHistory.setPayeeID("HOST");
          }
          else if ((localWireHistory.getTransType().equals("RECURRING")) && (localWireHistory.getID().equals(localWireHistory.getRecurringID()) == true))
          {
            DateTime localDateTime = localWireHistory.getDateValue();
            String str = localDateTime.getFormat();
            localWireHistory.put("DUE_DATE", localDateTime);
            Date localDate = getSmartDate(paramSecureUser, localDateTime);
            localWireHistory.setDate(new DateTime(localDate, paramSecureUser.getLocale(), str));
          }
          if ((localWireHistory.getStatus() == 1) || (localWireHistory.getStatus() == 21) || (localWireHistory.getStatus() == 2) || (localWireHistory.getStatus() == 26) || (localWireHistory.getStatus() >= 100)) {
            if (localWireHistory.getDestination().equals("HOST"))
            {
              localWireHistory.setCanEdit(true);
              localWireHistory.setCanDelete(true);
            }
            else if (localWireHistory.getTransType().equals("BATCH"))
            {
              localWireHistory.setCanEdit(true);
              if (isBatchDeletable(paramSecureUser, localWireHistory.getID(), paramHashMap) == true) {
                localWireHistory.setCanDelete(true);
              }
            }
            else
            {
              WireUtil.setCanEditDelete(paramSecureUser, localWireHistory, paramHashMap);
            }
          }
        }
      }
      else
      {
        bool2 = true;
      }
      if ((arrayOfWireHistoryInfo == null) || (paramPagingContext.getLastIndex() + localWireHistories.size() >= localBPWHist.getTotalTrans())) {
        bool2 = true;
      }
    }
    localWireHistories.getPagingContext().setLastPage(bool2);
    localWireHistories.getPagingContext().setFirstPage(bool1);
    return localWireHistories;
  }
  
  public WireHistories getPagedWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    WireHistories localWireHistories = new WireHistories();
    try
    {
      HashMap localHashMap = paramPagingContext.getMap();
      if (localHashMap == null)
      {
        localHashMap = new HashMap();
        paramPagingContext.setMap(localHashMap);
      }
      getClass();
      int i = 10;
      try
      {
        i = jdMethod_try(paramHashMap);
      }
      catch (Exception localException2) {}
      localHashMap.put("PAGE_SIZE", String.valueOf(i));
      ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
      Object localObject6;
      Object localObject8;
      if (paramPagingContext.getDirection().equals("FIRST"))
      {
        localObject1 = null;
        if (localReportCriteria != null) {
          localObject1 = localReportCriteria.getSortCriteria();
        }
        localObject2 = new ArrayList();
        if (localObject1 != null) {
          for (int j = 0; j < ((ReportSortCriteria)localObject1).size(); j++)
          {
            localObject4 = (ReportSortCriterion)((ReportSortCriteria)localObject1).get(j);
            localObject5 = new SortCriterion();
            ((SortCriterion)localObject5).setName(((ReportSortCriterion)localObject4).getName());
            if (((ReportSortCriterion)localObject4).getAsc() == true) {
              ((SortCriterion)localObject5).setAscending();
            } else {
              ((SortCriterion)localObject5).setDescending();
            }
            ((ArrayList)localObject2).add(localObject5);
          }
        }
        paramPagingContext.setSortCriteriaList((ArrayList)localObject2);
        if (paramPagingContext.getStartDate() == null)
        {
          localObject3 = GregorianCalendar.getInstance();
          ((Calendar)localObject3).add(5, -30);
          paramPagingContext.setStartDate((Calendar)localObject3);
        }
        if (paramPagingContext.getEndDate() == null)
        {
          localObject3 = GregorianCalendar.getInstance();
          ((Calendar)localObject3).add(5, 30);
          paramPagingContext.setEndDate((Calendar)localObject3);
        }
        localObject3 = new HashMap();
        ((HashMap)localObject3).put("CustomerId", String.valueOf(paramSecureUser.getBusinessID()));
        ((HashMap)localObject3).put("FIID", paramSecureUser.getBPWFIID());
        localObject4 = null;
        Object localObject5 = null;
        localObject6 = null;
        if (paramHashMap != null)
        {
          localObject4 = (String)paramHashMap.get("VIEW");
          localObject5 = (String)paramHashMap.get("DESTINATION");
          localObject6 = (String)paramHashMap.get("STATUS");
        }
        int m = 0;
        if ("ALL".equals(localObject4) == true) {
          m = 1;
        }
        if (m == 0)
        {
          arrayOfString = new String[] { String.valueOf(paramSecureUser.getProfileID()) };
          ((HashMap)localObject3).put("UserIds", arrayOfString);
        }
        if ((localObject5 != null) && (((String)localObject5).length() > 0))
        {
          arrayOfString = new String[] { localObject5 };
          ((HashMap)localObject3).put("Dests", arrayOfString);
        }
        String[] arrayOfString = null;
        if ("PENDING".equals(localObject6)) {
          arrayOfString = new String[] { "CREATED", "WILLPROCESSON", "RELEASEPENDING", "RELEASED", "INFUNDSAPPROVAL", "FUNDSAPPROVED", "FUNDSPENDING", "FUNDSAPPROVALACTIVE", "IMMED_INPROCESS", "INPROCESS", "PENDING", "BACKENDPENDING", "HELD", "APPROVAL_NOT_ALLOWED" };
        } else if ("APPROVAL".equals(localObject6)) {
          arrayOfString = new String[] { "APPROVAL_PENDING", "APPROVAL_REJECTED" };
        } else if ("COMPLETED".equals(localObject6)) {
          arrayOfString = new String[] { "ACCEPTED", "CONFIRMED", "ACKNOWLEDGED", "POSTEDON", "COMPLETED", "BACKENDREJECTED", "BACKENDFAILED", "FAILED", "FAILEDON", "REJECTED", "NOFUNDS", "NOFUNDS_NOTIF", "FUNDSFAILEDON", "FUNDSFAILEDON_NOTIF", "INFUNDSREVERT", "FUNDSREVERTACTIVE", "FUNDSREVERTFAILED", "FUNDSREVERTFAILED_NOTIF", "FUNDSREVERTED", "FUNDSREVERTED_NOTIF", "RELEASEFAILED", "RELEASEREJECTED", "LIMIT_CHECK_FAILED", "LIMIT_REVERT_FAILED", "APPROVAL_FAILED" };
        }
        ((HashMap)localObject3).put("StatusList", arrayOfString);
        localObject8 = new String[] { "BATCH", "SINGLE", "RECURRING" };
        ((HashMap)localObject3).put("TransType", localObject8);
        localHashMap.put("SEARCH_CRITERIA", localObject3);
      }
      Object localObject1 = localHashMap.get("SORT_VALUE_MIN_TransactionIndex");
      Object localObject2 = localHashMap.get("SORT_VALUE_MAX_TransactionIndex");
      if (localObject1 != null) {
        localHashMap.put("SORT_VALUE_MIN_TransactionIndex", String.valueOf(localObject1));
      }
      if (localObject2 != null) {
        localHashMap.put("SORT_VALUE_MAX_TransactionIndex", String.valueOf(localObject2));
      }
      Object localObject3 = new PagingInfo();
      Object localObject4 = getBPWHandler();
      if (localObject4 == null) {
        throw new CSILException(-1009, 31023);
      }
      try
      {
        localHashMap.remove("ReportCriteria");
        paramPagingContext.setMap(localHashMap);
        ((PagingInfo)localObject3).setPagingContext(paramPagingContext);
        localObject3 = ((BPWServices)localObject4).getPagedWire((PagingInfo)localObject3);
      }
      catch (Exception localException3)
      {
        DebugLog.throwing("getWireHistoriesPage ", localException3);
        localException3.printStackTrace(System.err);
        throw new CSILException(-1009, 31000);
      }
      finally
      {
        removeBPWHandler((BPWServices)localObject4);
      }
      ArrayList localArrayList = ((PagingInfo)localObject3).getPagingResult();
      paramPagingContext.setMap(((PagingInfo)localObject3).getPagingContext().getMap());
      paramPagingContext.setSessionId(((PagingInfo)localObject3).getPagingContext().getSessionId());
      localHashMap = paramPagingContext.getMap();
      Object localObject7;
      if (localHashMap != null)
      {
        localHashMap.put("ReportCriteria", localReportCriteria);
        localObject6 = localHashMap.get("LOWER_BOUND_TransactionIndex");
        localObject7 = localHashMap.get("UPPER_BOUND_TransactionIndex");
        try
        {
          long l;
          if (localObject6 != null)
          {
            l = Long.parseLong(localObject6.toString());
            localHashMap.put("LOWER_BOUND_TransactionIndex", new Long(l));
          }
          if (localObject7 != null)
          {
            l = Long.parseLong(localObject7.toString());
            localHashMap.put("UPPER_BOUND_TransactionIndex", new Long(l));
          }
        }
        catch (Exception localException4)
        {
          DebugLog.log("WireTransferService.getPagedWireHistories: Exception thrown!");
          localException4.printStackTrace();
        }
      }
      localWireHistories.setPagingContext(((PagingInfo)localObject3).getPagingContext());
      if ((localArrayList != null) && (localArrayList.size() != 0)) {
        for (int k = 0; k < localArrayList.size(); k++)
        {
          localObject7 = (WireHistoryInfo)localArrayList.get(k);
          WireHistory localWireHistory = (WireHistory)localWireHistories.create();
          localWireHistory.setWireHistoryInfo((WireHistoryInfo)localObject7);
          if (localWireHistory.getTransType().equals("BATCH"))
          {
            localWireHistory.setFromAccountID("BATCH");
            localWireHistory.setPayeeID("BATCH");
          }
          else if (localWireHistory.getDestination().equals("HOST"))
          {
            localWireHistory.setFromAccountID("HOST");
            localWireHistory.setPayeeID("HOST");
          }
          else if ((localWireHistory.getTransType().equals("RECURRING")) && (localWireHistory.getID().equals(localWireHistory.getRecurringID()) == true))
          {
            localObject8 = localWireHistory.getDateValue();
            localWireHistory.put("DUE_DATE", localObject8);
            Date localDate = getSmartDate(paramSecureUser, (DateTime)localObject8);
            localWireHistory.setDate(new DateTime(localDate, paramSecureUser.getLocale()));
          }
          if ((localWireHistory.getStatus() == 1) || (localWireHistory.getStatus() == 21) || (localWireHistory.getStatus() == 2) || (localWireHistory.getStatus() == 26) || (localWireHistory.getStatus() >= 100)) {
            if (localWireHistory.getDestination().equals("HOST"))
            {
              localWireHistory.setCanEdit(true);
              localWireHistory.setCanDelete(true);
            }
            else if (localWireHistory.getTransType().equals("BATCH"))
            {
              localWireHistory.setCanEdit(true);
              if (isBatchDeletable(paramSecureUser, localWireHistory.getID(), paramHashMap) == true) {
                localWireHistory.setCanDelete(true);
              }
            }
            else
            {
              WireUtil.setCanEditDelete(paramSecureUser, localWireHistory, paramHashMap);
            }
          }
        }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    return localWireHistories;
  }
  
  public WireTransfers getPagedWireTemplates(SecureUser paramSecureUser, PagingContext paramPagingContext, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null)
    {
      localHashMap = new HashMap();
      paramPagingContext.setMap(localHashMap);
    }
    getClass();
    int i = 10;
    try
    {
      i = jdMethod_try(paramHashMap);
    }
    catch (Exception localException1) {}
    localHashMap.put("PAGE_SIZE", String.valueOf(i));
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
    Object localObject7;
    if (paramPagingContext.getDirection().equals("FIRST"))
    {
      localObject1 = null;
      if (localReportCriteria != null) {
        localObject1 = localReportCriteria.getSortCriteria();
      }
      localObject2 = new ArrayList();
      if (localObject1 != null) {
        for (int j = 0; j < ((ReportSortCriteria)localObject1).size(); j++)
        {
          localObject4 = (ReportSortCriterion)((ReportSortCriteria)localObject1).get(j);
          localObject5 = new SortCriterion();
          ((SortCriterion)localObject5).setName(((ReportSortCriterion)localObject4).getName());
          if (((ReportSortCriterion)localObject4).getAsc() == true) {
            ((SortCriterion)localObject5).setAscending();
          } else {
            ((SortCriterion)localObject5).setDescending();
          }
          ((ArrayList)localObject2).add(localObject5);
        }
      }
      paramPagingContext.setSortCriteriaList((ArrayList)localObject2);
      if (paramPagingContext.getStartDate() == null)
      {
        localObject3 = GregorianCalendar.getInstance();
        ((Calendar)localObject3).add(1, -5);
        paramPagingContext.setStartDate((Calendar)localObject3);
      }
      if (paramPagingContext.getEndDate() == null)
      {
        localObject3 = GregorianCalendar.getInstance();
        paramPagingContext.setEndDate((Calendar)localObject3);
      }
      localObject3 = new HashMap();
      ((HashMap)localObject3).put("CustomerId", paramWireTransfer.getCustomerID());
      ((HashMap)localObject3).put("FIID", paramSecureUser.getBPWFIID());
      if (paramWireTransfer.getWireScope().equals("USER"))
      {
        localObject4 = new String[] { String.valueOf(paramSecureUser.getProfileID()) };
        ((HashMap)localObject3).put("UserIds", localObject4);
      }
      localObject4 = null;
      Object localObject5 = null;
      localObject6 = (String)paramHashMap.get("PENDING_APPROVAL");
      if ((localObject6 != null) && (((String)localObject6).equalsIgnoreCase("true")))
      {
        localObject4 = new String[] { "APPROVAL_PENDING", "APPROVAL_REJECTED", "TEMPLATE", "RECTEMPLATE" };
        localObject5 = new String[] { "TEMPLATE", "RECTEMPLATE" };
      }
      else if (paramWireTransfer.getStatus() == 22)
      {
        localObject4 = new String[] { "TEMPLATE" };
        localObject5 = new String[] { "TEMPLATE" };
      }
      else
      {
        localObject4 = new String[] { "TEMPLATE", "RECTEMPLATE" };
        localObject5 = new String[] { "TEMPLATE", "RECTEMPLATE" };
      }
      ((HashMap)localObject3).put("StatusList", localObject4);
      ((HashMap)localObject3).put("TransType", localObject5);
      ((HashMap)localObject3).put("TransScope", paramWireTransfer.getWireScope());
      localObject7 = paramWireTransfer.getWireDestination();
      if ((localObject7 != null) && (((String)localObject7).length() > 0))
      {
        String[] arrayOfString = { localObject7 };
        ((HashMap)localObject3).put("Dests", arrayOfString);
      }
      localHashMap.put("SEARCH_CRITERIA", localObject3);
    }
    Object localObject1 = localHashMap.get("SORT_VALUE_MIN_TransactionIndex");
    Object localObject2 = localHashMap.get("SORT_VALUE_MAX_TransactionIndex");
    if (localObject1 != null) {
      localHashMap.put("SORT_VALUE_MIN_TransactionIndex", String.valueOf(localObject1));
    }
    if (localObject2 != null) {
      localHashMap.put("SORT_VALUE_MAX_TransactionIndex", String.valueOf(localObject2));
    }
    Object localObject3 = new PagingInfo();
    Object localObject4 = getBPWHandler();
    if (localObject4 == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      localHashMap.remove("ReportCriteria");
      paramPagingContext.setMap(localHashMap);
      ((PagingInfo)localObject3).setPagingContext(paramPagingContext);
      localObject3 = ((BPWServices)localObject4).getPagedWire((PagingInfo)localObject3);
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("getWireHistoriesPage ", localException2);
      localException2.printStackTrace(System.err);
      throw new CSILException(-1009, 31000);
    }
    finally
    {
      removeBPWHandler((BPWServices)localObject4);
    }
    ArrayList localArrayList = ((PagingInfo)localObject3).getPagingResult();
    paramPagingContext.setMap(((PagingInfo)localObject3).getPagingContext().getMap());
    paramPagingContext.setSessionId(((PagingInfo)localObject3).getPagingContext().getSessionId());
    localHashMap = paramPagingContext.getMap();
    if (localHashMap != null)
    {
      localHashMap.put("ReportCriteria", localReportCriteria);
      localObject6 = localHashMap.get("LOWER_BOUND_TransactionIndex");
      localObject7 = localHashMap.get("UPPER_BOUND_TransactionIndex");
      try
      {
        long l;
        if (localObject6 != null)
        {
          l = Long.parseLong(localObject6.toString());
          localHashMap.put("LOWER_BOUND_TransactionIndex", new Long(l));
        }
        if (localObject7 != null)
        {
          l = Long.parseLong(localObject7.toString());
          localHashMap.put("UPPER_BOUND_TransactionIndex", new Long(l));
        }
      }
      catch (Exception localException3)
      {
        DebugLog.log("WireTransferService.getPagedWireHistories: Exception thrown!");
        localException3.printStackTrace();
      }
    }
    Object localObject6 = new WireTransfers();
    ((WireTransfers)localObject6).setPagingContext(((PagingInfo)localObject3).getPagingContext());
    if ((localArrayList != null) && (localArrayList.size() != 0)) {
      for (int k = 0; k < localArrayList.size(); k++)
      {
        WireHistoryInfo localWireHistoryInfo = (WireHistoryInfo)localArrayList.get(k);
        WireInfo localWireInfo = null;
        RecWireInfo localRecWireInfo = null;
        Object localObject9 = localWireHistoryInfo.getWireObject();
        WireTransfer localWireTransfer = (WireTransfer)((WireTransfers)localObject6).create();
        localWireTransfer.setTransactionIndex(localWireHistoryInfo.getCursorId());
        if ((localObject9 instanceof RecWireInfo))
        {
          localRecWireInfo = (RecWireInfo)localObject9;
          localWireTransfer.setRecWireInfo(localRecWireInfo);
          localWireTransfer.setType(6);
        }
        else if ((localObject9 instanceof WireInfo))
        {
          localWireInfo = (WireInfo)localObject9;
          localWireTransfer.setWireInfo(localWireInfo);
        }
        else
        {
          DebugLog.log("GetPagedWireTemplates: No WireInfo record found.");
        }
      }
    }
    return localObject6;
  }
  
  public WireTransfers getFilteredWires(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    WireInfo[] arrayOfWireInfo = a(paramSecureUser, paramWireTransfer, paramHashMap);
    WireTransfers localWireTransfers = new WireTransfers();
    if (arrayOfWireInfo != null) {
      for (int i = 0; i < arrayOfWireInfo.length; i++)
      {
        WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.create();
        a(localWireTransfer, arrayOfWireInfo[i], null);
      }
    }
    return localWireTransfers;
  }
  
  private WireInfo[] a(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    BPWHist localBPWHist = new BPWHist();
    String str = formatDate.format(paramWireTransfer.getDateToPostValue().getTime());
    localBPWHist.setStartDate(str);
    localBPWHist.setEndDate(str);
    localBPWHist.setCustId(paramWireTransfer.getCustomerID());
    String[] arrayOfString = { paramWireTransfer.getWireDestination() };
    localBPWHist.setDest(arrayOfString);
    localBPWHist.setSelectionCriteria("all");
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    try
    {
      localBPWHist = localBPWServices.getWireHistory(localBPWHist);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("GetFilteredWires ", localException);
      localException.printStackTrace(System.err);
      throw new CSILException(-1009, 31000);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    WireInfo[] arrayOfWireInfo = null;
    if (localBPWHist != null) {
      if ((localBPWHist.getTrans() instanceof WireInfo[])) {
        arrayOfWireInfo = (WireInfo[])localBPWHist.getTrans();
      } else {
        DebugLog.log("*** WARNING: In GetFilteredWires, NO WireInfo object found!");
      }
    }
    return arrayOfWireInfo;
  }
  
  public WireTransfers getDuplicateWires(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(-1009, 31023);
    }
    int i = 0;
    WireTransfers localWireTransfers = new WireTransfers();
    try
    {
      WireInfo localWireInfo = paramWireTransfer.getWireInfo();
      String str = "";
      if (paramWireTransfer.getDateToPostValue() != null)
      {
        str = formatDate.format(paramWireTransfer.getDateToPostValue().getTime());
        localWireInfo.setDateToPost(str);
      }
      BPWHist localBPWHist = localBPWServices.getDuplicateWires(localWireInfo);
      i = localBPWHist.getStatusCode();
      if (i == 0)
      {
        WireInfo[] arrayOfWireInfo = null;
        if ((localBPWHist.getTrans() instanceof WireInfo[]))
        {
          arrayOfWireInfo = (WireInfo[])localBPWHist.getTrans();
          for (int j = 0; j < arrayOfWireInfo.length; j++)
          {
            WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.create();
            localWireTransfer.setWireInfo(arrayOfWireInfo[j]);
          }
        }
      }
      else if (i != 16020)
      {
        DebugLog.log("ERROR failed checking for duplicate wires");
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("getDuplicateWires: ", localException);
      localException.printStackTrace(System.err);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    return localWireTransfers;
  }
  
  private static int jdMethod_do(int paramInt1, int paramInt2)
  {
    int i;
    switch (paramInt1)
    {
    case 17521: 
      i = 20003;
      break;
    case 17523: 
      i = 20011;
      break;
    case 17524: 
      i = 30216;
      break;
    case 17525: 
      i = 30207;
      break;
    case 17526: 
      i = 30218;
      break;
    case 17527: 
      i = 30219;
      break;
    case 19851: 
      i = 31062;
      break;
    case 19852: 
      i = 31063;
      break;
    case 19000: 
      i = 31005;
      break;
    case 19100: 
      i = 1038;
      break;
    case 19010: 
      i = 31007;
      break;
    case 19180: 
      i = 1035;
      break;
    case 19170: 
      i = 1052;
      break;
    case 17200: 
      i = 1039;
      break;
    case 19110: 
      i = 1041;
      break;
    case 17201: 
      i = 1042;
      break;
    case 19200: 
      i = 1053;
      break;
    case 19210: 
      i = 1045;
      break;
    case 19280: 
      i = 1056;
      break;
    case 19340: 
      i = 1057;
      break;
    case 19390: 
      i = 1058;
      break;
    case 17205: 
      i = 1059;
      break;
    case 17206: 
      i = 1060;
      break;
    case 17080: 
      i = 1061;
      break;
    case 19410: 
      i = 1042;
      break;
    case 19560: 
      i = 31025;
      break;
    case 19500: 
      i = 31026;
      break;
    case 19020: 
      i = 31027;
      break;
    case 19030: 
      i = 31028;
      break;
    case 19470: 
      i = 31029;
      break;
    case 16020: 
      i = 31030;
      break;
    case 19350: 
      i = 31031;
      break;
    case 19490: 
      i = 31032;
      break;
    case 19480: 
      i = 31033;
      break;
    case 19380: 
      i = 31034;
      break;
    case 19360: 
      i = 31035;
      break;
    case 19370: 
      i = 31036;
      break;
    case 19140: 
      i = 31037;
      break;
    case 19310: 
      i = 31038;
      break;
    case 19400: 
      i = 31039;
      break;
    case 17180: 
      i = 31040;
      break;
    case 19760: 
      i = 31041;
      break;
    case 19270: 
      i = 31042;
      break;
    case 19120: 
      i = 31043;
      break;
    case 23170: 
      i = 31044;
      break;
    case 19130: 
      i = 1043;
      break;
    case 19550: 
      i = 31046;
      break;
    case 19430: 
      i = 31047;
      break;
    case 19190: 
      i = 31048;
      break;
    case 19570: 
      i = 31049;
      break;
    case 19580: 
      i = 31050;
      break;
    case 19590: 
      i = 31051;
      break;
    case 19520: 
      i = 31052;
      break;
    case 19250: 
      i = 31053;
      break;
    case 19260: 
      i = 31054;
      break;
    case 19610: 
      i = 31055;
      break;
    case 19160: 
      i = 31024;
      break;
    case 23335: 
      i = 31057;
      break;
    case 23330: 
      i = 31058;
      break;
    case 19810: 
      i = 31058;
      break;
    default: 
      i = paramInt2;
    }
    return i;
  }
  
  private ReportResult a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, WireTransfers paramWireTransfers, Locale paramLocale, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "WireTransferService.createDetailedWireReport";
    ReportResult localReportResult = new ReportResult(paramLocale);
    int i = 0;
    int j = 0;
    try
    {
      localReportResult.init(paramReportCriteria);
      int k = 0;
      int m = 0;
      double d1 = 0.0D;
      double d2 = 0.0D;
      Iterator localIterator = paramWireTransfers.iterator();
      WireTransfers localWireTransfers = new WireTransfers();
      Object localObject1;
      Object localObject2;
      Object localObject3;
      Object localObject4;
      if (localIterator.hasNext())
      {
        localObject1 = null;
        while ((localIterator.hasNext()) && (i < paramInt))
        {
          localObject1 = (WireTransfer)localIterator.next();
          localObject2 = ((WireTransfer)localObject1).getWireDestination();
          a(paramSecureUser, paramReportCriteria, localReportResult, paramLocale, (WireTransfer)localObject1, paramHashMap);
          if (!((String)localObject2).equals("HOST"))
          {
            localObject3 = null;
            localObject4 = paramReportCriteria.getReportOptions();
            if (localObject4 != null) {
              localObject3 = ((Properties)localObject4).getProperty("REPORTTYPE");
            }
            a(paramSecureUser, localReportResult, (String)localObject3, paramLocale, (WireTransfer)localObject1, paramHashMap);
          }
          a(localReportResult, paramLocale, (WireTransfer)localObject1, paramInt);
          if (((String)localObject2).equals("DRAWDOWN"))
          {
            m++;
            d2 += ((WireTransfer)localObject1).getAmountValue().doubleValue();
          }
          else
          {
            k++;
            d1 += ((WireTransfer)localObject1).getAmountValue().doubleValue();
          }
        }
        localObject2 = ReportConsts.getText(3004, paramLocale);
        a(localReportResult, (String)localObject2, paramLocale, m, k, d2, d1);
      }
      else
      {
        localObject1 = null;
        localObject2 = new ReportDataDimensions(paramLocale);
        ((ReportDataDimensions)localObject2).setNumColumns(1);
        ((ReportDataDimensions)localObject2).setNumRows(1);
        localReportResult.setDataDimensions((ReportDataDimensions)localObject2);
        localObject1 = new ReportColumn(paramLocale);
        ((ReportColumn)localObject1).setDataType("java.lang.String");
        ((ReportColumn)localObject1).setWidthAsPercent(100);
        localReportResult.addColumn((ReportColumn)localObject1);
        localObject1 = null;
        localObject3 = new ReportRow(paramLocale);
        localObject4 = new ReportDataItems(paramLocale);
        ReportDataItem localReportDataItem = null;
        ((ReportRow)localObject3).setDataItems((ReportDataItems)localObject4);
        localReportDataItem = ((ReportDataItems)localObject4).add();
        localReportDataItem.setData(ReportConsts.getText(2501, paramLocale));
        localReportDataItem = null;
        localObject4 = null;
        localReportResult.addRow((ReportRow)localObject3);
        localObject3 = null;
      }
    }
    catch (Exception localException)
    {
      j = 1;
      localReportResult.setError(localException);
      throw new CSILException(str, 21007, localException);
    }
    finally
    {
      if (i == paramInt)
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("TRUNCATED", new Integer(i));
        localReportResult.setProperties(localHashMap);
      }
      try
      {
        localReportResult.fini();
      }
      catch (RptException localRptException)
      {
        if (j == 0) {
          throw new CSILException(str, 21007, localRptException);
        }
      }
    }
    return localReportResult;
  }
  
  private static void a(ReportResult paramReportResult, String paramString, Locale paramLocale, int paramInt1, int paramInt2, double paramDouble1, double paramDouble2)
    throws Exception
  {
    ReportColumn localReportColumn = null;
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportHeading localReportHeading = new ReportHeading(Locale.getDefault());
    localReportHeading.setLabel(paramString);
    localReportResult.setHeading(localReportHeading);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(Locale.getDefault());
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(4);
    localReportResult.setDataDimensions(localReportDataDimensions);
    localReportColumn = new ReportColumn(Locale.getDefault());
    localReportColumn.setWidthAsPercent(25);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(Locale.getDefault());
    localReportColumn.setWidthAsPercent(25);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(Locale.getDefault());
    localReportColumn.setWidthAsPercent(25);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(Locale.getDefault());
    localReportColumn.setWidthAsPercent(25);
    localReportResult.addColumn(localReportColumn);
    Currency localCurrency1 = new Currency(new BigDecimal(paramDouble1), paramLocale);
    Currency localCurrency2 = new Currency(new BigDecimal(paramDouble2), paramLocale);
    StringBuffer localStringBuffer1 = new StringBuffer();
    localStringBuffer1.append("(");
    localStringBuffer1.append(localCurrency1.getCurrencyCode());
    localStringBuffer1.append(") ");
    localStringBuffer1.append(localCurrency1.toString());
    StringBuffer localStringBuffer2 = new StringBuffer();
    localStringBuffer2.append("(");
    localStringBuffer2.append(localCurrency2.getCurrencyCode());
    localStringBuffer2.append(") ");
    localStringBuffer2.append(localCurrency2.toString());
    ReportDataItems localReportDataItems = new ReportDataItems(Locale.getDefault());
    localReportDataItems.add().setData(ReportConsts.getText(3006, paramLocale));
    localReportDataItems.add().setData(String.valueOf(paramInt2));
    localReportDataItems.add().setData(ReportConsts.getText(3010, paramLocale));
    localReportDataItems.add().setData(localCurrency2.toString());
    ReportRow localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(Locale.getDefault());
    localReportDataItems.add().setData(ReportConsts.getText(3005, paramLocale));
    localReportDataItems.add().setData(String.valueOf(paramInt1));
    localReportDataItems.add().setData(ReportConsts.getText(3011, paramLocale));
    localReportDataItems.add().setData(localCurrency1.toString());
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
  }
  
  private void a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, ReportResult paramReportResult, Locale paramLocale, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws Exception
  {
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    String str1 = paramWireTransfer.getWireDestination();
    ReportHeading localReportHeading = new ReportHeading(paramLocale);
    localReportHeading.setLabel(ReportConsts.getText(3007, paramLocale) + ": " + paramWireTransfer.getTrackingID());
    localReportResult.setHeading(localReportHeading);
    Accounts localAccounts = (Accounts)paramHashMap.get("AccountsForReport");
    int i = 10;
    int j = 0;
    int k = 0;
    String str2 = null;
    Properties localProperties = paramReportCriteria.getReportOptions();
    if (localProperties != null) {
      str2 = localProperties.getProperty("REPORTTYPE");
    }
    if ("Wire Templates".equals(str2))
    {
      j = 1;
      i = 8;
    }
    else if ("Wire By Source".equals(str2))
    {
      k = 1;
      i = 11;
    }
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(i);
    localReportDataDimensions.setNumRows(-1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn1 = new ReportColumn(paramLocale);
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(ReportConsts.getColumnName(1880, paramLocale));
    localReportColumn1.setLabels(localArrayList1);
    localReportColumn1.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn1.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn1);
    if (j == 0)
    {
      localReportColumn2 = new ReportColumn(paramLocale);
      localArrayList2 = new ArrayList();
      localArrayList2.add(ReportConsts.getColumnName(1888, paramLocale));
      localReportColumn2.setLabels(localArrayList2);
      localReportColumn2.setDataType("com.ffusion.util.beans.DateTime");
      localReportColumn2.setWidthAsPercent(10);
      localReportResult.addColumn(localReportColumn2);
      localReportColumn3 = new ReportColumn(paramLocale);
      localArrayList3 = new ArrayList();
      localArrayList3.add(ReportConsts.getColumnName(1886, paramLocale));
      localReportColumn3.setLabels(localArrayList3);
      localReportColumn3.setDataType("java.lang.String");
      localReportColumn3.setWidthAsPercent(13);
      localReportResult.addColumn(localReportColumn3);
    }
    ReportColumn localReportColumn2 = new ReportColumn(paramLocale);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(1882, paramLocale));
    localReportColumn2.setLabels(localArrayList2);
    localReportColumn2.setDataType("java.lang.String");
    localReportColumn2.setWidthAsPercent(15);
    localReportResult.addColumn(localReportColumn2);
    ReportColumn localReportColumn3 = new ReportColumn(paramLocale);
    ArrayList localArrayList3 = new ArrayList();
    localArrayList3.add(ReportConsts.getColumnName(2890, paramLocale));
    localReportColumn3.setLabels(localArrayList3);
    localReportColumn3.setDataType("java.lang.String");
    localReportColumn3.setWidthAsPercent(15);
    localReportResult.addColumn(localReportColumn3);
    ReportColumn localReportColumn4 = new ReportColumn(paramLocale);
    ArrayList localArrayList4 = new ArrayList();
    localArrayList4.add(ReportConsts.getColumnName(1883, paramLocale));
    localReportColumn4.setLabels(localArrayList4);
    localReportColumn4.setDataType("java.lang.String");
    localReportColumn4.setWidthAsPercent(20);
    localReportResult.addColumn(localReportColumn4);
    if (k != 0)
    {
      localReportColumn5 = new ReportColumn(paramLocale);
      localArrayList5 = new ArrayList();
      localArrayList5.add(ReportConsts.getColumnName(1889, paramLocale));
      localReportColumn5.setLabels(localArrayList5);
      localReportColumn5.setDataType("java.lang.String");
      localReportColumn5.setWidthAsPercent(10);
      localReportResult.addColumn(localReportColumn5);
    }
    ReportColumn localReportColumn5 = new ReportColumn(paramLocale);
    ArrayList localArrayList5 = new ArrayList();
    localArrayList5.add(ReportConsts.getColumnName(603, paramLocale));
    localReportColumn5.setLabels(localArrayList5);
    localReportColumn5.setDataType("java.lang.String");
    localReportColumn5.setWidthAsPercent(15);
    localReportResult.addColumn(localReportColumn5);
    ReportColumn localReportColumn6 = new ReportColumn(paramLocale);
    ArrayList localArrayList6 = new ArrayList();
    localArrayList6.add(ReportConsts.getColumnName(2889, paramLocale));
    localReportColumn6.setLabels(localArrayList6);
    localReportColumn6.setDataType("java.lang.String");
    localReportColumn6.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn6);
    ReportColumn localReportColumn7 = new ReportColumn(paramLocale);
    ArrayList localArrayList7 = new ArrayList();
    localArrayList7.add(ReportConsts.getColumnName(1884, paramLocale));
    localReportColumn7.setLabels(localArrayList7);
    localReportColumn7.setDataType("java.lang.String");
    localReportColumn7.setWidthAsPercent(20);
    localReportResult.addColumn(localReportColumn7);
    ReportColumn localReportColumn8 = new ReportColumn(paramLocale);
    ArrayList localArrayList8 = new ArrayList();
    localArrayList8.add(ReportConsts.getColumnName(1885, paramLocale));
    localReportColumn8.setLabels(localArrayList8);
    localReportColumn8.setDataType("com.ffusion.beans.Currency");
    localReportColumn8.setJustification("RIGHT");
    localReportColumn8.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn8);
    ReportRow localReportRow = new ReportRow(localReportResult.getLocale());
    ReportDataItems localReportDataItems = new ReportDataItems(localReportResult.getLocale());
    localReportRow.setDataItems(localReportDataItems);
    ReportDataItem localReportDataItem = localReportDataItems.add();
    jdMethod_if(localReportDataItem, paramWireTransfer.getDueDateValue());
    Object localObject;
    String str3;
    if (j == 0)
    {
      localReportDataItem = localReportDataItems.add();
      jdMethod_if(localReportDataItem, paramWireTransfer.getDateToPostValue());
      localObject = paramWireTransfer.getDatePostedValue();
      str3 = "";
      if (localObject != null) {
        str3 = ((DateTime)localObject).getString();
      }
      localReportDataItem = localReportDataItems.add();
      jdMethod_if(localReportDataItem, str3);
    }
    localReportDataItem = localReportDataItems.add();
    if (str1.equals("HOST"))
    {
      jdMethod_if(localReportDataItem, ReportConsts.getText(637, paramLocale));
    }
    else if (str1.equals("DRAWDOWN"))
    {
      localObject = paramWireTransfer.getWireCreditInfo();
      if (localObject != null) {
        jdMethod_if(localReportDataItem, ((WireTransferPayee)localObject).getPayeeName());
      } else {
        jdMethod_if(localReportDataItem, "");
      }
    }
    else
    {
      localObject = getWireTransferPayeeById(paramSecureUser, paramWireTransfer.getWirePayeeID(), null);
      if ((((WireTransferPayee)localObject).getNickName() != null) && (((WireTransferPayee)localObject).getNickName().length() > 0)) {
        jdMethod_if(localReportDataItem, ((WireTransferPayee)localObject).getPayeeName() + " (" + ((WireTransferPayee)localObject).getNickName() + ")");
      } else {
        jdMethod_if(localReportDataItem, ((WireTransferPayee)localObject).getPayeeName());
      }
    }
    localReportDataItem = localReportDataItems.add();
    if (str1.equals("DRAWDOWN"))
    {
      localObject = paramWireTransfer.getWireCreditInfo();
      if (localObject != null)
      {
        str3 = ((WireTransferPayee)localObject).getAccountNum();
        String str4 = ((WireTransferPayee)localObject).getAccountType();
        if ((str3 != null) && (str4 != null)) {
          str3 = str3 + " - " + str4;
        }
        jdMethod_if(localReportDataItem, str3);
        DebugLog.log("Printing To Account in detail " + ((WireTransferPayee)localObject).getAccountNum());
      }
      else
      {
        jdMethod_if(localReportDataItem, "");
      }
    }
    else if (str1.equals("HOST"))
    {
      jdMethod_if(localReportDataItem, "HOST");
    }
    else
    {
      localObject = paramWireTransfer.getWirePayee();
      if (localObject != null) {
        jdMethod_if(localReportDataItem, ((WireTransferPayee)localObject).getAccountNum() + " - " + ((WireTransferPayee)localObject).getAccountType());
      } else {
        jdMethod_if(localReportDataItem, "");
      }
    }
    localReportDataItem = localReportDataItems.add();
    if (str1.equals("HOST"))
    {
      jdMethod_if(localReportDataItem, ReportConsts.getText(637, paramLocale));
    }
    else if (str1.equals("DRAWDOWN"))
    {
      localObject = paramWireTransfer.getWirePayee();
      if (localObject != null)
      {
        DebugLog.log("Printing From Account in detail " + ((WireTransferPayee)localObject).getAccountNum());
        jdMethod_if(localReportDataItem, ((WireTransferPayee)localObject).getAccountNum() + " - " + ((WireTransferPayee)localObject).getAccountType());
      }
      else
      {
        jdMethod_if(localReportDataItem, "");
      }
    }
    else
    {
      localObject = localAccounts.getByID(paramWireTransfer.getFromAccountID());
      jdMethod_if(localReportDataItem, ((Account)localObject).getDisplayTextRoutingNumNickNameCurrency());
    }
    if (k != 0)
    {
      localReportDataItem = localReportDataItems.add();
      jdMethod_if(localReportDataItem, paramWireTransfer.getWireSource());
    }
    localReportDataItem = localReportDataItems.add();
    jdMethod_if(localReportDataItem, paramWireTransfer.getStatusName());
    localReportDataItem = localReportDataItems.add();
    jdMethod_if(localReportDataItem, paramWireTransfer.getWireDestination());
    localReportDataItem = localReportDataItems.add();
    jdMethod_if(localReportDataItem, paramWireTransfer.getReferenceNumber());
    localReportDataItem = localReportDataItems.add();
    jdMethod_if(localReportDataItem, paramWireTransfer.getAmountValue());
    localReportResult.addRow(localReportRow);
  }
  
  private static void a(SecureUser paramSecureUser, ReportResult paramReportResult, String paramString, Locale paramLocale, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws Exception
  {
    String str = paramWireTransfer.getWireDestination();
    if (str.equals("DRAWDOWN")) {
      jdMethod_if(paramSecureUser, paramReportResult, paramString, paramLocale, paramWireTransfer, paramHashMap);
    } else {
      jdMethod_do(paramSecureUser, paramReportResult, paramString, paramLocale, paramWireTransfer, paramHashMap);
    }
  }
  
  private static void jdMethod_do(SecureUser paramSecureUser, ReportResult paramReportResult, String paramString, Locale paramLocale, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws Exception
  {
    WireTransferPayee localWireTransferPayee = null;
    WireTransferBank localWireTransferBank1 = null;
    localWireTransferPayee = paramWireTransfer.getWirePayee();
    String str1 = paramWireTransfer.getWireDestination();
    String str2 = "";
    String str3 = "";
    String str4 = "";
    String str5 = "";
    String str6 = "";
    String str7 = "";
    String str8 = "";
    String str9 = "";
    String str10 = "";
    String str11 = "";
    String str12 = "";
    String str13 = "";
    String str14 = "";
    String str15 = "";
    String str16 = "";
    String str17 = "";
    String str18 = "";
    String str19 = "";
    String str20 = "";
    String str21 = "";
    String str22 = "";
    String str23 = "";
    String str24 = "";
    String str25 = "";
    String str26 = "";
    String str27 = "";
    String str28 = "";
    String str29 = "";
    String str30 = "";
    String str31 = "";
    WireTransferBanks localWireTransferBanks = null;
    if (localWireTransferPayee != null)
    {
      localWireTransferBank1 = localWireTransferPayee.getDestinationBank();
      str2 = localWireTransferPayee.getNickName();
      localObject1 = new StringBuffer();
      if (localWireTransferPayee.getStreet() != null)
      {
        ((StringBuffer)localObject1).append(localWireTransferPayee.getStreet());
        if (localWireTransferPayee.getStreet2() != null)
        {
          ((StringBuffer)localObject1).append(", ");
          ((StringBuffer)localObject1).append(localWireTransferPayee.getStreet2());
          if (localWireTransferPayee.getStreet3() != null)
          {
            ((StringBuffer)localObject1).append(", ");
            ((StringBuffer)localObject1).append(localWireTransferPayee.getStreet3());
          }
        }
      }
      str3 = ((StringBuffer)localObject1).toString();
      str4 = localWireTransferPayee.getCity();
      str6 = localWireTransferPayee.getZipCode();
      str7 = localWireTransferPayee.getContactName();
      str8 = localWireTransferPayee.getPayeeScope();
      str9 = localWireTransferPayee.getAccountNum();
      str10 = localWireTransferPayee.getAccountType();
      localWireTransferBanks = localWireTransferPayee.getIntermediaryBanks();
      if (localWireTransferBank1 != null)
      {
        str11 = localWireTransferBank1.getID();
        str12 = localWireTransferBank1.getBankName();
        localObject2 = new StringBuffer();
        if (localWireTransferBank1.getStreet() != null)
        {
          ((StringBuffer)localObject2).append(localWireTransferBank1.getStreet());
          if (localWireTransferBank1.getStreet2() != null)
          {
            ((StringBuffer)localObject2).append(", ");
            ((StringBuffer)localObject2).append(localWireTransferBank1.getStreet2());
            if (localWireTransferPayee.getStreet3() != null)
            {
              ((StringBuffer)localObject2).append(", ");
              ((StringBuffer)localObject2).append(localWireTransferBank1.getStreet3());
            }
          }
        }
        str13 = ((StringBuffer)localObject2).toString();
        str14 = localWireTransferBank1.getCity();
        str15 = localWireTransferBank1.getState();
        str16 = localWireTransferBank1.getZipCode();
        str17 = localWireTransferBank1.getRoutingFedWire();
        str18 = localWireTransferBank1.getRoutingSwift();
        str19 = localWireTransferBank1.getRoutingChips();
        str20 = localWireTransferBank1.getRoutingOther();
      }
      str26 = paramWireTransfer.getComment();
      str27 = paramWireTransfer.getOrigToBeneficiaryInfo1();
      str28 = paramWireTransfer.getBankToBankInfo1();
      str29 = paramWireTransfer.getByOrderOfName();
      str30 = paramWireTransfer.getByOrderOfAddress1();
      str31 = paramWireTransfer.getByOrderOfAccount();
    }
    str21 = paramWireTransfer.getDueDate();
    str22 = paramWireTransfer.getOrigCurrency();
    str23 = paramWireTransfer.getExchangeRate();
    str24 = paramWireTransfer.getMathRule();
    str25 = paramWireTransfer.getContractNumber();
    Object localObject1 = null;
    Object localObject2 = new ReportResult(paramLocale);
    paramReportResult.addSubReport((ReportResult)localObject2);
    ((ReportResult)localObject2).setHeading(null);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(2);
    ((ReportResult)localObject2).setDataDimensions(localReportDataDimensions);
    localObject1 = new ReportColumn(paramLocale);
    ((ReportColumn)localObject1).setWidthAsPercent(5);
    ((ReportResult)localObject2).addColumn((ReportColumn)localObject1);
    localObject1 = new ReportColumn(paramLocale);
    ((ReportColumn)localObject1).setWidthAsPercent(10);
    ((ReportResult)localObject2).addColumn((ReportColumn)localObject1);
    int i = 0;
    if ("Completed Wires".equals(paramString))
    {
      ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3107, paramLocale), paramWireTransfer.getConfirmationNum(), i, paramLocale));
      i++;
      ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3108, paramLocale), paramWireTransfer.getFedConfirmationNum(), i, paramLocale));
      i++;
    }
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3051, paramLocale), str2, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3052, paramLocale), str3, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3053, paramLocale), str4, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3054, paramLocale), str5, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3055, paramLocale), str6, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3056, paramLocale), str7, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3057, paramLocale), str8, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3058, paramLocale), str9, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3059, paramLocale), str10, i, paramLocale));
    i++;
    if (str1.equals("DOMESTIC"))
    {
      ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3101, paramLocale), str11, i, paramLocale));
      i++;
    }
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3060, paramLocale), str12, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3061, paramLocale), str13, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3062, paramLocale), str14, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3063, paramLocale), str15, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3078, paramLocale), str16, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3064, paramLocale), str17, i, paramLocale));
    i++;
    if ((str1.equals("DOMESTIC")) || (str1.equals("HOST")))
    {
      ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3065, paramLocale), str18, i, paramLocale));
      i++;
      ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3066, paramLocale), str19, i, paramLocale));
      i++;
      ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3067, paramLocale), str20, i, paramLocale));
      i++;
    }
    if (str1.equals("INTERNATIONAL"))
    {
      ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3068, paramLocale), str21, i, paramLocale));
      i++;
      ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3069, paramLocale), str22, i, paramLocale));
      i++;
      ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3070, paramLocale), str23, i, paramLocale));
      i++;
      ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3071, paramLocale), str24, i, paramLocale));
      i++;
      ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3072, paramLocale), str25, i, paramLocale));
      i++;
    }
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3073, paramLocale), str26, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3109, paramLocale), paramWireTransfer.getOrigToBeneficiaryInfo1(), i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3110, paramLocale), paramWireTransfer.getOrigToBeneficiaryInfo2(), i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3111, paramLocale), paramWireTransfer.getOrigToBeneficiaryInfo3(), i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3112, paramLocale), paramWireTransfer.getOrigToBeneficiaryInfo4(), i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3113, paramLocale), paramWireTransfer.getBankToBankInfo1(), i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3114, paramLocale), paramWireTransfer.getBankToBankInfo2(), i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3115, paramLocale), paramWireTransfer.getBankToBankInfo3(), i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3116, paramLocale), paramWireTransfer.getBankToBankInfo4(), i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3117, paramLocale), paramWireTransfer.getBankToBankInfo5(), i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3118, paramLocale), paramWireTransfer.getBankToBankInfo6(), i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3075, paramLocale), str29, i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3119, paramLocale), paramWireTransfer.getByOrderOfAddress1(), i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3120, paramLocale), paramWireTransfer.getByOrderOfAddress2(), i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3121, paramLocale), paramWireTransfer.getByOrderOfAddress3(), i, paramLocale));
    i++;
    ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3077, paramLocale), str31, i, paramLocale));
    i++;
    if ((str1.equals("DOMESTIC")) && (localWireTransferBanks != null))
    {
      Iterator localIterator = localWireTransferBanks.iterator();
      while (localIterator.hasNext())
      {
        WireTransferBank localWireTransferBank2 = (WireTransferBank)localIterator.next();
        String str32 = localWireTransferBank2.getID();
        String str33 = localWireTransferBank2.getBankName();
        StringBuffer localStringBuffer = new StringBuffer();
        if (localWireTransferBank2.getStreet() != null)
        {
          localStringBuffer.append(localWireTransferBank2.getStreet());
          if (localWireTransferBank2.getStreet2() != null)
          {
            localStringBuffer.append(",");
            localStringBuffer.append(localWireTransferBank2.getStreet2());
            if (localWireTransferBank2.getStreet3() != null)
            {
              localStringBuffer.append(",");
              localStringBuffer.append(localWireTransferBank2.getStreet3());
            }
          }
        }
        String str34 = localStringBuffer.toString();
        ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3104, paramLocale), str32, i, paramLocale));
        i++;
        ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3102, paramLocale), str33, i, paramLocale));
        i++;
        ((ReportResult)localObject2).addRow(a(ReportConsts.getColumnName(3103, paramLocale), str34, i, paramLocale));
        i++;
      }
    }
  }
  
  private static void jdMethod_if(SecureUser paramSecureUser, ReportResult paramReportResult, String paramString, Locale paramLocale, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws Exception
  {
    WireTransferPayee localWireTransferPayee = null;
    WireTransferBank localWireTransferBank1 = null;
    Object localObject1 = null;
    WireTransferBank localWireTransferBank2 = null;
    localWireTransferPayee = paramWireTransfer.getWireCreditInfo();
    String str1 = "";
    String str2 = "";
    String str3 = "";
    String str4 = "";
    String str5 = "";
    String str6 = "";
    String str7 = "";
    String str8 = "";
    String str9 = "";
    String str10 = "";
    String str11 = "";
    String str12 = "";
    String str13 = "";
    String str14 = "";
    String str15 = "";
    String str16 = "";
    String str17 = "";
    String str18 = "";
    String str19 = "";
    String str20 = "";
    String str21 = "";
    String str22 = "";
    String str23 = "";
    String str24 = "";
    String str25 = "";
    String str26 = "";
    String str27 = "";
    String str28 = "";
    String str29 = "";
    String str30 = "";
    String str31 = "";
    String str32 = "";
    String str33 = "";
    String str34 = "";
    String str35 = "";
    if (localWireTransferPayee != null)
    {
      localWireTransferBank1 = localWireTransferPayee.getDestinationBank();
      str1 = localWireTransferPayee.getName();
      localObject2 = new StringBuffer();
      if (localWireTransferPayee.getStreet() != null)
      {
        ((StringBuffer)localObject2).append(localWireTransferPayee.getStreet());
        if (localWireTransferPayee.getStreet2() != null)
        {
          ((StringBuffer)localObject2).append(", ");
          ((StringBuffer)localObject2).append(localWireTransferPayee.getStreet2());
          if (localWireTransferPayee.getStreet3() != null)
          {
            ((StringBuffer)localObject2).append(", ");
            ((StringBuffer)localObject2).append(localWireTransferPayee.getStreet3());
          }
        }
      }
      str2 = ((StringBuffer)localObject2).toString();
      str3 = localWireTransferPayee.getCity();
      str4 = localWireTransferPayee.getState();
      str5 = localWireTransferPayee.getZipCode();
      str6 = localWireTransferPayee.getContactName();
      str7 = localWireTransferPayee.getAccountNum();
      if (localWireTransferBank1 != null)
      {
        str8 = localWireTransferBank1.getBankName();
        localObject3 = new StringBuffer();
        if (localWireTransferBank1.getStreet() != null)
        {
          ((StringBuffer)localObject3).append(localWireTransferBank1.getStreet());
          if (localWireTransferBank1.getStreet2() != null)
          {
            ((StringBuffer)localObject3).append(", ");
            ((StringBuffer)localObject3).append(localWireTransferBank1.getStreet2());
            if (localWireTransferBank1.getStreet3() != null)
            {
              ((StringBuffer)localObject3).append(", ");
              ((StringBuffer)localObject3).append(localWireTransferBank1.getStreet3());
            }
          }
        }
        str9 = ((StringBuffer)localObject3).toString();
        str10 = localWireTransferBank1.getCity();
        str11 = localWireTransferBank1.getState();
        str12 = localWireTransferBank1.getZipCode();
        str12 = localWireTransferBank1.getZipCode();
      }
    }
    if (localObject1 != null)
    {
      localWireTransferBank2 = localObject1.getDestinationBank();
      str14 = localObject1.getName();
      str15 = localObject1.getNickName();
      localObject2 = new StringBuffer();
      if (localObject1.getStreet() != null)
      {
        ((StringBuffer)localObject2).append(localObject1.getStreet());
        if (localObject1.getStreet2() != null)
        {
          ((StringBuffer)localObject2).append(", ");
          ((StringBuffer)localObject2).append(localObject1.getStreet2());
          if (localObject1.getStreet3() != null)
          {
            ((StringBuffer)localObject2).append(", ");
            ((StringBuffer)localObject2).append(localObject1.getStreet3());
          }
        }
      }
      str16 = ((StringBuffer)localObject2).toString();
      str17 = localObject1.getCity();
      str18 = localObject1.getState();
      str19 = str5 = localObject1.getZipCode();
      str20 = str6 = localObject1.getContactName();
      str21 = localObject1.getPayeeScope();
      str22 = localObject1.getAccountNum();
      str23 = localObject1.getAccountType();
      if (localWireTransferBank2 != null)
      {
        str24 = localWireTransferBank2.getBankName();
        localObject3 = new StringBuffer();
        if (localWireTransferBank2.getStreet() != null)
        {
          ((StringBuffer)localObject3).append(localWireTransferBank2.getStreet());
          if (localWireTransferBank2.getStreet2() != null)
          {
            ((StringBuffer)localObject3).append(", ");
            ((StringBuffer)localObject3).append(localWireTransferBank2.getStreet2());
            if (localObject1.getStreet3() != null)
            {
              ((StringBuffer)localObject3).append(", ");
              ((StringBuffer)localObject3).append(localWireTransferBank2.getStreet3());
            }
          }
        }
        str25 = ((StringBuffer)localObject3).toString();
        str26 = localWireTransferBank2.getCity();
        str27 = localWireTransferBank2.getState();
        str28 = localWireTransferBank2.getZipCode();
        str29 = localWireTransferBank2.getRoutingFedWire();
      }
    }
    str30 = paramWireTransfer.getComment();
    str31 = paramWireTransfer.getOrigToBeneficiaryInfo1();
    str32 = paramWireTransfer.getBankToBankInfo1();
    str33 = paramWireTransfer.getByOrderOfName();
    str34 = paramWireTransfer.getByOrderOfAddress1();
    str35 = paramWireTransfer.getByOrderOfAccount();
    Object localObject2 = null;
    Object localObject3 = new ReportResult(paramLocale);
    paramReportResult.addSubReport((ReportResult)localObject3);
    ((ReportResult)localObject3).setHeading(null);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(2);
    ((ReportResult)localObject3).setDataDimensions(localReportDataDimensions);
    localObject2 = new ReportColumn(paramLocale);
    ((ReportColumn)localObject2).setWidthAsPercent(5);
    ((ReportResult)localObject3).addColumn((ReportColumn)localObject2);
    localObject2 = new ReportColumn(paramLocale);
    ((ReportColumn)localObject2).setWidthAsPercent(10);
    ((ReportResult)localObject3).addColumn((ReportColumn)localObject2);
    int i = 0;
    if ("Completed Wires".equals(paramString))
    {
      ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3107, paramLocale), paramWireTransfer.getConfirmationNum(), i, paramLocale));
      i++;
      ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3108, paramLocale), paramWireTransfer.getFedConfirmationNum(), i, paramLocale));
      i++;
    }
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3050, paramLocale), str1, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3052, paramLocale), str2, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3053, paramLocale), str3, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3054, paramLocale), str4, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3055, paramLocale), str5, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3056, paramLocale), str6, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3080, paramLocale), str7, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3060, paramLocale), str8, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3061, paramLocale), str9, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3062, paramLocale), str10, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3063, paramLocale), str11, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3078, paramLocale), str12, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3064, paramLocale), str13, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3081, paramLocale), str14, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3082, paramLocale), str15, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3083, paramLocale), str16, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3084, paramLocale), str17, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3085, paramLocale), str18, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3086, paramLocale), str19, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3087, paramLocale), str20, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3088, paramLocale), str21, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3089, paramLocale), str22, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3090, paramLocale), str23, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3091, paramLocale), str24, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3092, paramLocale), str25, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3093, paramLocale), str26, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3094, paramLocale), str27, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3095, paramLocale), str28, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3096, paramLocale), str29, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3073, paramLocale), str30, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3109, paramLocale), paramWireTransfer.getOrigToBeneficiaryInfo1(), i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3110, paramLocale), paramWireTransfer.getOrigToBeneficiaryInfo2(), i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3111, paramLocale), paramWireTransfer.getOrigToBeneficiaryInfo3(), i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3112, paramLocale), paramWireTransfer.getOrigToBeneficiaryInfo4(), i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3113, paramLocale), paramWireTransfer.getBankToBankInfo1(), i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3114, paramLocale), paramWireTransfer.getBankToBankInfo2(), i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3115, paramLocale), paramWireTransfer.getBankToBankInfo3(), i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3116, paramLocale), paramWireTransfer.getBankToBankInfo4(), i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3117, paramLocale), paramWireTransfer.getBankToBankInfo5(), i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3118, paramLocale), paramWireTransfer.getBankToBankInfo6(), i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3075, paramLocale), str33, i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3119, paramLocale), paramWireTransfer.getByOrderOfAddress1(), i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3120, paramLocale), paramWireTransfer.getByOrderOfAddress2(), i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3121, paramLocale), paramWireTransfer.getByOrderOfAddress3(), i, paramLocale));
    i++;
    ((ReportResult)localObject3).addRow(a(ReportConsts.getColumnName(3077, paramLocale), str35, i, paramLocale));
    i++;
  }
  
  private static void a(ReportResult paramReportResult, Locale paramLocale, WireTransfer paramWireTransfer, int paramInt)
    throws Exception
  {
    String str1 = "WireTransferService.createWireLifeCycleReport";
    int i = 0;
    int j = 0;
    if ((paramReportResult == null) || (paramWireTransfer == null))
    {
      DebugLog.log(Level.WARNING, "Error while getting Wire Life Cycle");
      throw new Exception("Error while getting Wire Life Cycle");
    }
    if (paramLocale == null) {
      paramLocale = paramReportResult.getLocale();
    }
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    String str2 = paramWireTransfer.getTrackingID();
    String str3 = paramWireTransfer.getID();
    if ((str2 == null) || (str3 == null))
    {
      i = 1;
    }
    else
    {
      ReportLogRecords localReportLogRecords = WireAdapter.getAuditHistoryRecordsById(str2, str3, paramLocale);
      Object localObject1;
      Object localObject2;
      Object localObject3;
      Object localObject4;
      if (localReportLogRecords == null)
      {
        i = 1;
      }
      else
      {
        localObject1 = localReportLogRecords.iterator();
        if (((Iterator)localObject1).hasNext()) {
          try
          {
            ReportHeading localReportHeading = new ReportHeading(paramLocale);
            localReportHeading.setLabel(ReportConsts.getText(3008, paramLocale));
            localReportResult.setHeading(localReportHeading);
            localObject2 = new ReportDataDimensions(paramLocale);
            ((ReportDataDimensions)localObject2).setNumRows(-1);
            ((ReportDataDimensions)localObject2).setNumColumns(4);
            localReportResult.setDataDimensions((ReportDataDimensions)localObject2);
            localObject3 = new ReportColumn(paramLocale);
            localObject4 = new ArrayList();
            ((ArrayList)localObject4).add(ReportConsts.getColumnName(3097, paramLocale));
            ((ReportColumn)localObject3).setLabels((ArrayList)localObject4);
            ((ReportColumn)localObject3).setDataType("java.lang.String");
            ((ReportColumn)localObject3).setWidthAsPercent(20);
            localReportResult.addColumn((ReportColumn)localObject3);
            localObject3 = new ReportColumn(paramLocale);
            localObject4 = new ArrayList();
            ((ArrayList)localObject4).add(ReportConsts.getColumnName(3098, paramLocale));
            ((ReportColumn)localObject3).setLabels((ArrayList)localObject4);
            ((ReportColumn)localObject3).setDataType("java.lang.String");
            ((ReportColumn)localObject3).setWidthAsPercent(20);
            localReportResult.addColumn((ReportColumn)localObject3);
            localObject3 = new ReportColumn(paramLocale);
            localObject4 = new ArrayList();
            ((ArrayList)localObject4).add(ReportConsts.getColumnName(3099, paramLocale));
            ((ReportColumn)localObject3).setLabels((ArrayList)localObject4);
            ((ReportColumn)localObject3).setDataType("java.lang.String");
            ((ReportColumn)localObject3).setWidthAsPercent(20);
            localReportResult.addColumn((ReportColumn)localObject3);
            localObject3 = new ReportColumn(paramLocale);
            localObject4 = new ArrayList();
            ((ArrayList)localObject4).add(ReportConsts.getColumnName(3100, paramLocale));
            ((ReportColumn)localObject3).setLabels((ArrayList)localObject4);
            ((ReportColumn)localObject3).setDataType("java.lang.String");
            ((ReportColumn)localObject3).setWidthAsPercent(40);
            localReportResult.addColumn((ReportColumn)localObject3);
            int k = 1;
            while ((((Iterator)localObject1).hasNext()) && (j < paramInt))
            {
              j++;
              ReportLogRecord localReportLogRecord = (ReportLogRecord)((Iterator)localObject1).next();
              String str4 = localReportLogRecord.getTimestamp();
              String str5 = localReportLogRecord.getState();
              String str6 = localReportLogRecord.getProcessedBy();
              String str7 = localReportLogRecord.getMessage();
              ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
              localReportDataItems.add().setData(str4);
              localReportDataItems.add().setData(str5);
              localReportDataItems.add().setData(str6);
              localReportDataItems.add().setData(str7);
              ReportRow localReportRow = new ReportRow(paramLocale);
              if (k % 2 == 0) {
                localReportRow.set("CELLBACKGROUND", "reportDataShaded");
              }
              k++;
              localReportRow.setDataItems(localReportDataItems);
              localReportResult.addRow(localReportRow);
            }
          }
          catch (Exception localException)
          {
            DebugLog.log(Level.WARNING, "Error while generating Wire Life Cycle Report");
            localException.printStackTrace();
          }
          finally
          {
            if (j == paramInt)
            {
              HashMap localHashMap = new HashMap();
              localHashMap.put("TRUNCATED", new Integer(j));
              localReportResult.setProperties(localHashMap);
            }
          }
        } else {
          i = 1;
        }
      }
      if (i != 0)
      {
        localObject1 = null;
        ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
        localReportDataDimensions.setNumColumns(1);
        localReportDataDimensions.setNumRows(1);
        localReportResult.setDataDimensions(localReportDataDimensions);
        localObject1 = new ReportColumn(paramLocale);
        ((ReportColumn)localObject1).setDataType("java.lang.String");
        ((ReportColumn)localObject1).setWidthAsPercent(100);
        localReportResult.addColumn((ReportColumn)localObject1);
        localObject1 = null;
        localObject2 = new ReportRow(paramLocale);
        localObject3 = new ReportDataItems(paramLocale);
        localObject4 = null;
        ((ReportRow)localObject2).setDataItems((ReportDataItems)localObject3);
        localObject4 = ((ReportDataItems)localObject3).add();
        ((ReportDataItem)localObject4).setData(ReportConsts.getText(2501, paramLocale));
        localObject4 = null;
        localObject3 = null;
        localReportResult.addRow((ReportRow)localObject2);
        localObject2 = null;
      }
    }
  }
  
  private static ReportRow a(String paramString1, String paramString2, int paramInt, Locale paramLocale)
  {
    ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
    ReportRow localReportRow = new ReportRow(paramLocale);
    if ((paramInt != -1) && (paramInt % 2 == 0)) {
      localReportRow.set("CELLBACKGROUND", "reportDataShaded");
    }
    localReportRow.setDataItems(localReportDataItems);
    localReportDataItems.add().setData(paramString1);
    localReportDataItems.add().setData(paramString2);
    return localReportRow;
  }
  
  private static String jdMethod_do(Accounts paramAccounts, String paramString)
  {
    if ((paramAccounts == null) || (paramAccounts.isEmpty())) {
      return paramString;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    StringBuffer localStringBuffer1 = new StringBuffer();
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      StringBuffer localStringBuffer2 = new StringBuffer();
      for (int i = 0; i < paramAccounts.size(); i++)
      {
        Account localAccount = (Account)paramAccounts.get(i);
        if (localAccount.getID().equals(str))
        {
          localStringBuffer2.append(localAccount.getDisplayTextRoutingNumNickNameCurrency());
          break;
        }
      }
      if (localStringBuffer2.length() > 0)
      {
        if (localStringBuffer1.length() > 0) {
          localStringBuffer1.append("&&");
        }
        localStringBuffer1.append(localStringBuffer2);
      }
      else
      {
        return paramString;
      }
    }
    return localStringBuffer1.length() > 0 ? localStringBuffer1.toString() : paramString;
  }
  
  private static boolean jdMethod_try(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }
  
  public class a
    extends Base.a
  {
    public a()
    {
      super();
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("BPWCallBackJNDIName")) {
        WireTransferService.this.cG = paramString2;
      } else {
        super.attribute(paramString1, paramString2, paramBoolean);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.banksim2.WireTransferService
 * JD-Core Version:    0.7.0.1
 */