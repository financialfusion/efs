package com.ffusion.services.cashcon;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.beans.cashcon.CashConAccount;
import com.ffusion.beans.cashcon.CashConAccounts;
import com.ffusion.beans.cashcon.CashConCompanies;
import com.ffusion.beans.cashcon.CashConCompany;
import com.ffusion.beans.cashcon.CashCons;
import com.ffusion.beans.cashcon.Location;
import com.ffusion.beans.cashcon.LocationSearchCriteria;
import com.ffusion.beans.cashcon.LocationSearchResult;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.beans.cashcon.Locations;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.util.BeansConverter;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Reporting;
import com.ffusion.efs.adapters.profile.LocationAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.ReportTransactionAdapter;
import com.ffusion.ffs.bpw.BPWServices.BPWServices;
import com.ffusion.ffs.bpw.BPWServices.BPWServicesHome;
import com.ffusion.ffs.bpw.adminEJB.BPWAdminHome;
import com.ffusion.ffs.bpw.adminEJB.IBPWAdmin;
import com.ffusion.ffs.bpw.interfaces.ACHHistInfo;
import com.ffusion.ffs.bpw.interfaces.BPWInfoBase;
import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfoList;
import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfoList;
import com.ffusion.ffs.bpw.interfaces.CCCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyInfoList;
import com.ffusion.ffs.bpw.interfaces.CCEntryHistInfo;
import com.ffusion.ffs.bpw.interfaces.CCEntryInfo;
import com.ffusion.ffs.bpw.interfaces.CCLocationInfo;
import com.ffusion.ffs.bpw.interfaces.CCLocationInfoList;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.SortCriterion;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.CashCon1;
import com.ffusion.services.admin.AdminException;
import com.ffusion.services.banksim2.Base;
import com.ffusion.services.banksim2.Base.a;
import com.ffusion.util.ContextPool;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.TrackingIDGenerator;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

public class CashConService
  extends Base
  implements CashCon1
{
  private static final String c3 = "BPWCallBackJNDIName";
  private static final String cY = "BPWAdminCallBackJNDIName";
  private static final int c5 = 1;
  private static final int cW = 2;
  private static final int cZ = 3;
  private static final int c1 = 4;
  protected static DateFormat formatDate = DateFormatUtil.getFormatter("yyyyMMdd");
  private String c0 = "BPWServices";
  private String c4 = "bpw.BPWAdminHome";
  private int c2 = 10;
  private static final String cX = "DEFAULTACCOUNT";
  
  public int initialize(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = "banksim.xml";
    }
    int i = initialize(paramString, new a());
    return i;
  }
  
  public void processOFXRequest() {}
  
  protected BPWServices getBPWHandler()
    throws CashConException
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
        localContextPool = getContextPool(str);
        localContext = localContextPool.getContext();
        Object localObject1 = localContext.lookup(this.c0);
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
            localObject2 = localContext.lookup(this.c0);
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
    throw new CashConException(25018);
  }
  
  protected void removeBPWHandler(BPWServices paramBPWServices)
  {
    if (paramBPWServices != null) {
      try
      {
        paramBPWServices.remove();
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("Error removing bpw handler", localThrowable);
      }
    }
  }
  
  protected IBPWAdmin getBPWAdminHandler()
  {
    IBPWAdmin localIBPWAdmin = null;
    ContextPool localContextPool = null;
    Context localContext = null;
    for (int i = 0; i < this.provider_url_list.size(); i++)
    {
      String str = (String)this.provider_url_list.get(i);
      try
      {
        if (debugService)
        {
          DebugLog.log(Level.INFO, "##################### Getting BPW Handler ################");
          DebugLog.log(Level.INFO, "provider_url = " + str);
        }
        localContextPool = getContextPool(str);
        localContext = localContextPool.getContext();
        Object localObject1 = localContext.lookup(this.c4);
        localObject2 = (BPWAdminHome)PortableRemoteObject.narrow(localObject1, BPWAdminHome.class);
        localIBPWAdmin = ((BPWAdminHome)localObject2).create();
        localContextPool.returnContext(localContext);
        if (localIBPWAdmin != null)
        {
          try
          {
            if (localIBPWAdmin.ping()) {
              return localIBPWAdmin;
            }
          }
          catch (Exception localException2)
          {
            DebugLog.throwing("Couldn't ping the BPW server for " + str, localException2);
          }
          removeBPWAdminHandler(localIBPWAdmin);
        }
      }
      catch (Throwable localThrowable)
      {
        Object localObject2;
        DebugLog.throwing("Couldn't get handler in BankSim service", localThrowable);
        if ((localContext != null) && (localContextPool != null)) {
          try
          {
            localContext = localContextPool.refreshContext(localContext);
            localObject2 = localContext.lookup(this.c4);
            BPWAdminHome localBPWAdminHome = (BPWAdminHome)PortableRemoteObject.narrow(localObject2, BPWAdminHome.class);
            localIBPWAdmin = localBPWAdminHome.create();
            localContextPool.returnContext(localContext);
            if (localIBPWAdmin != null)
            {
              try
              {
                if (localIBPWAdmin.ping()) {
                  return localIBPWAdmin;
                }
              }
              catch (Exception localException3)
              {
                DebugLog.throwing("Couldn't ping the BPW server for " + str, localException3);
              }
              removeBPWAdminHandler(localIBPWAdmin);
            }
          }
          catch (Exception localException1)
          {
            DebugLog.throwing("Couldn't refresh the contexts for " + str, localException1);
            if ((localContextPool != null) && (localContext != null)) {
              localContextPool.returnContext(localContext);
            }
          }
        }
      }
    }
    return null;
  }
  
  public com.ffusion.beans.cashcon.CashCon addCashCon(SecureUser paramSecureUser, com.ffusion.beans.cashcon.CashCon paramCashCon, HashMap paramHashMap)
    throws CashConException
  {
    String str1 = "CashConService.addCashCon";
    CCEntryInfo localCCEntryInfo = new CCEntryInfo();
    localCCEntryInfo.setEntryId(null);
    BigDecimal localBigDecimal = new BigDecimal(0.0D);
    localBigDecimal = paramCashCon.getAmountValue().getAmountValue();
    long l = localBigDecimal.abs().multiply(new BigDecimal(100.0D)).longValue();
    localCCEntryInfo.setAmount("" + l);
    localCCEntryInfo.setLocationId(paramCashCon.getLocationID());
    if (paramCashCon.getType() == 15) {
      localCCEntryInfo.setTransactionType("CONCENTRATION");
    }
    if (paramCashCon.getType() == 16) {
      localCCEntryInfo.setTransactionType("DISBURSEMENT");
    }
    localCCEntryInfo.setSubmittedBy("" + paramSecureUser.getProfileID());
    String str2 = formatDate.format(GregorianCalendar.getInstance().getTime());
    localCCEntryInfo.setDueDate(str2);
    localCCEntryInfo.setEntryCategory("USER_ENTRY");
    localCCEntryInfo.setLogId(paramCashCon.getTrackingID());
    localCCEntryInfo.setLastModifier(Integer.toString(paramSecureUser.getProfileID()));
    int i = 16002;
    BPWServices localBPWServices = getBPWHandler();
    String str3 = "";
    populateOBOAgentInfo(paramSecureUser, localCCEntryInfo);
    Object localObject1;
    try
    {
      localCCEntryInfo = localBPWServices.addCCEntry(localCCEntryInfo);
      if (localCCEntryInfo != null)
      {
        i = localCCEntryInfo.getStatusCode();
        str3 = localCCEntryInfo.getStatusMsg();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.addCashCon exception: " + localThrowable.getMessage());
      localObject1 = localThrowable.toString();
      if ((localObject1 != null) && (((String)localObject1).indexOf("Server is not running!") > 0)) {
        throw new CashConException(25018);
      }
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i == 0)
    {
      BeansConverter.setCashConFromCCEntryInfo(paramCashCon, localCCEntryInfo);
      paramCashCon.setID(localCCEntryInfo.getEntryId());
      paramCashCon.bpwObject = localCCEntryInfo;
    }
    else
    {
      if (i == 17521)
      {
        i = 20003;
        Hashtable localHashtable = localCCEntryInfo.getExtInfo();
        if (localHashtable != null)
        {
          localObject1 = (Limits)localHashtable.get("NOT_ALLOWED_APPROVAL_LIMITS");
          if (localObject1 != null)
          {
            if (paramHashMap == null) {
              paramHashMap = new HashMap();
            }
            paramHashMap.put("ExceededLimits", localObject1);
          }
        }
        throw new CashConException(str1, 20003, str3);
      }
      throw new CashConException(str1, 39001, str3);
    }
    return paramCashCon;
  }
  
  public com.ffusion.beans.cashcon.CashCon modifyCashCon(SecureUser paramSecureUser, com.ffusion.beans.cashcon.CashCon paramCashCon1, com.ffusion.beans.cashcon.CashCon paramCashCon2, HashMap paramHashMap)
    throws CashConException
  {
    String str1 = "CashConService.modifyCashCon";
    CCEntryInfo localCCEntryInfo = null;
    if (paramCashCon1.bpwObject != null) {
      localCCEntryInfo = (CCEntryInfo)paramCashCon1.bpwObject;
    } else {
      localCCEntryInfo = new CCEntryInfo();
    }
    localCCEntryInfo.setEntryId(paramCashCon1.getID());
    String str2 = formatDate.format(GregorianCalendar.getInstance().getTime());
    localCCEntryInfo.setDueDate(str2);
    BigDecimal localBigDecimal = new BigDecimal(0.0D);
    localBigDecimal = paramCashCon1.getAmountValue().getAmountValue();
    long l = localBigDecimal.abs().multiply(new BigDecimal(100.0D)).longValue();
    localCCEntryInfo.setAmount("" + l);
    localCCEntryInfo.setLocationId(paramCashCon1.getLocationID());
    localCCEntryInfo.setEntryCategory("USER_ENTRY");
    if (paramCashCon1.getType() == 15) {
      localCCEntryInfo.setTransactionType("CONCENTRATION");
    }
    if (paramCashCon1.getType() == 16) {
      localCCEntryInfo.setTransactionType("DISBURSEMENT");
    }
    localCCEntryInfo.setSubmittedBy("" + paramSecureUser.getProfileID());
    localCCEntryInfo.setLogId(paramCashCon1.getTrackingID());
    localCCEntryInfo.setLastModifier(Integer.toString(paramSecureUser.getProfileID()));
    int i = 16002;
    BPWServices localBPWServices = getBPWHandler();
    String str3 = "";
    populateOBOAgentInfo(paramSecureUser, localCCEntryInfo);
    Object localObject1;
    try
    {
      localCCEntryInfo = localBPWServices.modCCEntry(localCCEntryInfo);
      if (localCCEntryInfo != null)
      {
        i = localCCEntryInfo.getStatusCode();
        str3 = localCCEntryInfo.getStatusMsg();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.addCashCon exception: " + localThrowable.getMessage());
      localObject1 = localThrowable.toString();
      if ((localObject1 != null) && (((String)localObject1).indexOf("Server is not running!") > 0)) {
        throw new CashConException(25018);
      }
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i == 0)
    {
      BeansConverter.setCashConFromCCEntryInfo(paramCashCon1, localCCEntryInfo);
      paramCashCon1.setID(localCCEntryInfo.getEntryId());
      paramCashCon1.bpwObject = localCCEntryInfo;
    }
    else
    {
      if (i == 17521)
      {
        i = 20003;
        Hashtable localHashtable = localCCEntryInfo.getExtInfo();
        if (localHashtable != null)
        {
          localObject1 = (Limits)localHashtable.get("NOT_ALLOWED_APPROVAL_LIMITS");
          if (localObject1 != null)
          {
            if (paramHashMap == null) {
              paramHashMap = new HashMap();
            }
            paramHashMap.put("ExceededLimits", localObject1);
          }
        }
        throw new CashConException(str1, 20003, str3);
      }
      throw new CashConException(str1, 39002, str3);
    }
    return paramCashCon1;
  }
  
  public void deleteCashCon(SecureUser paramSecureUser, com.ffusion.beans.cashcon.CashCon paramCashCon, HashMap paramHashMap)
    throws CashConException
  {
    String str1 = "CashConService.deleteCashCon";
    int i = 16002;
    BPWServices localBPWServices = getBPWHandler();
    String str2 = "";
    CCEntryInfo localCCEntryInfo = new CCEntryInfo();
    localCCEntryInfo.setEntryId(paramCashCon.getID());
    localCCEntryInfo.setLastModifier(Integer.toString(paramSecureUser.getProfileID()));
    populateOBOAgentInfo(paramSecureUser, localCCEntryInfo);
    try
    {
      localCCEntryInfo = localBPWServices.cancelCCEntry(localCCEntryInfo);
      if (localCCEntryInfo != null)
      {
        i = localCCEntryInfo.getStatusCode();
        str2 = localCCEntryInfo.getStatusMsg();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.addCashCon exception: " + localThrowable.getMessage());
      String str3 = localThrowable.toString();
      if ((str3 != null) && (str3.indexOf("Server is not running!") > 0)) {
        throw new CashConException(25018);
      }
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i == 0) {
      return;
    }
    throw new CashConException(str1, 39003, str2);
  }
  
  public CashCons getPagedPendingCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    paramPagingContext.setDirection("FIRST");
    return getCashConsFromCCEntryHist(paramSecureUser, paramPagingContext, paramHashMap, 1);
  }
  
  public CashCons getNextPendingCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    paramPagingContext.setDirection("NEXT");
    return getCashConsFromCCEntryHist(paramSecureUser, paramPagingContext, paramHashMap, 1);
  }
  
  public CashCons getPreviousPendingCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    paramPagingContext.setDirection("PREVIOUS");
    return getCashConsFromCCEntryHist(paramSecureUser, paramPagingContext, paramHashMap, 1);
  }
  
  public CashCons getPagedCompletedCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    paramPagingContext.setDirection("FIRST");
    return getCashConsFromCCEntryHist(paramSecureUser, paramPagingContext, paramHashMap, 2);
  }
  
  public CashCons getNextCompletedCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    paramPagingContext.setDirection("NEXT");
    return getCashConsFromCCEntryHist(paramSecureUser, paramPagingContext, paramHashMap, 2);
  }
  
  public CashCons getPreviousCompletedCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    paramPagingContext.setDirection("PREVIOUS");
    return getCashConsFromCCEntryHist(paramSecureUser, paramPagingContext, paramHashMap, 2);
  }
  
  public CashCons getPagedApprovalCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    paramPagingContext.setDirection("FIRST");
    return getCashConsFromCCEntryHist(paramSecureUser, paramPagingContext, paramHashMap, 3);
  }
  
  public CashCons getNextApprovalCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    paramPagingContext.setDirection("NEXT");
    return getCashConsFromCCEntryHist(paramSecureUser, paramPagingContext, paramHashMap, 3);
  }
  
  public CashCons getPreviousApprovalCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    paramPagingContext.setDirection("PREVIOUS");
    return getCashConsFromCCEntryHist(paramSecureUser, paramPagingContext, paramHashMap, 3);
  }
  
  protected CashCons getCashConsFromCCEntryHist(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap, int paramInt)
    throws CashConException
  {
    CashCons localCashCons = new CashCons();
    localCashCons.setPagingContext(paramPagingContext);
    boolean bool1 = false;
    boolean bool2 = false;
    HashMap localHashMap1 = paramPagingContext.getMap();
    if (localHashMap1 == null) {
      localHashMap1 = new HashMap();
    }
    paramPagingContext.setMap(localHashMap1);
    CCEntryHistInfo localCCEntryHistInfo = new CCEntryHistInfo();
    localCCEntryHistInfo.setCustomerId("" + paramSecureUser.getBusinessID());
    if ((paramHashMap != null) && (paramHashMap.get("VIEW") == null))
    {
      localCCEntryHistInfo.setSubmittedBy("" + paramSecureUser.getProfileID());
    }
    else
    {
      localCCEntryHistInfo.setViewEntitlementCheck(true);
      localCCEntryHistInfo.setViewerId("" + paramSecureUser.getProfileID());
    }
    long l1 = 10L;
    l1 = jdMethod_byte(paramHashMap);
    localCCEntryHistInfo.setPageSize(l1);
    if (paramInt == 2) {
      localCCEntryHistInfo.setAscending(false);
    }
    LocationSearchResults localLocationSearchResults = getEntitledLocations(paramSecureUser);
    if ((localLocationSearchResults == null) || (localLocationSearchResults.size() == 0))
    {
      paramPagingContext.setFirstPage(true);
      paramPagingContext.setLastPage(true);
      return localCashCons;
    }
    Iterator localIterator = localLocationSearchResults.iterator();
    String[] arrayOfString = new String[localLocationSearchResults.size()];
    for (int i = 0; localIterator.hasNext(); i++)
    {
      localObject1 = (LocationSearchResult)localIterator.next();
      arrayOfString[i] = new String(((LocationSearchResult)localObject1).getLocationBPWID());
    }
    localCCEntryHistInfo.setLocationIdList(arrayOfString);
    Object localObject3;
    if (paramPagingContext.getDirection().equalsIgnoreCase("FIRST"))
    {
      localCCEntryHistInfo.setCursorId(null);
      localCCEntryHistInfo.setHistId(null);
      localObject1 = new ArrayList();
      if (paramInt == 2) {
        ((ArrayList)localObject1).add("999999999");
      } else {
        ((ArrayList)localObject1).add("0");
      }
      localHashMap1.put("PREV", localObject1);
      bool1 = true;
      paramPagingContext.setFirstIndex(0L);
      paramPagingContext.setLastIndex(0L);
      paramPagingContext.setFirstPage(true);
      if (paramInt == 1)
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localCCEntryHistInfo.setStartDate((String)localObject2);
        }
        else
        {
          localObject2 = GregorianCalendar.getInstance();
          localObject3 = formatDate.format(((Calendar)localObject2).getTime());
          localCCEntryHistInfo.setStartDate((String)localObject3);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localCCEntryHistInfo.setEndDate((String)localObject2);
        }
        localObject2 = new String[] { "CREATED", "WILLPROCESSON" };
        localCCEntryHistInfo.setStatusList((String[])localObject2);
      }
      else if (paramInt == 2)
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localCCEntryHistInfo.setStartDate((String)localObject2);
        }
        else
        {
          localObject2 = GregorianCalendar.getInstance();
          ((Calendar)localObject2).add(5, -90);
          localObject3 = formatDate.format(((Calendar)localObject2).getTime());
          localCCEntryHistInfo.setStartDate((String)localObject3);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localCCEntryHistInfo.setEndDate((String)localObject2);
        }
        localObject2 = new String[] { "PROCESSEDON", "POSTEDON", "CANCELEDON", "LIMIT_CHECK_FAILED", "LIMIT_REVERT_FAILED", "APPROVAL_FAILED", "FAILEDON" };
        localCCEntryHistInfo.setStatusList((String[])localObject2);
      }
      else if (paramInt == 3)
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localCCEntryHistInfo.setStartDate((String)localObject2);
        }
        else
        {
          localObject2 = GregorianCalendar.getInstance();
          ((Calendar)localObject2).add(5, -90);
          localObject3 = formatDate.format(((Calendar)localObject2).getTime());
          localCCEntryHistInfo.setStartDate((String)localObject3);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localCCEntryHistInfo.setEndDate((String)localObject2);
        }
        localObject2 = new String[] { "APPROVAL_PENDING", "APPROVAL_REJECTED" };
        localCCEntryHistInfo.setStatusList((String[])localObject2);
      }
      else
      {
        if (paramPagingContext.getStartDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
          localCCEntryHistInfo.setStartDate((String)localObject2);
        }
        if (paramPagingContext.getEndDate() != null)
        {
          localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
          localCCEntryHistInfo.setEndDate((String)localObject2);
        }
        localObject2 = new String[] { "WILLPROCESSON", "PROCESSEDON", "APPROVAL_PENDING", "APPROVAL_REJECTED", "POSTEDON", "LIMIT_CHECK_FAILED", "LIMIT_REVERT_FAILED", "APPROVAL_FAILED", "FAILEDON", "CREATED" };
        localCCEntryHistInfo.setStatusList((String[])localObject2);
      }
    }
    else
    {
      int j;
      if (paramPagingContext.getDirection().equalsIgnoreCase("NEXT"))
      {
        localObject1 = (String)localHashMap1.get("HISTID");
        localObject2 = (String)localHashMap1.get("CURSORID");
        localObject3 = (ArrayList)localHashMap1.get("PREV");
        paramPagingContext.setLastIndex(paramPagingContext.getLastIndex() + l1);
        j = new Long(paramPagingContext.getFirstIndex()).intValue();
        j++;
        paramPagingContext.setFirstIndex(j);
        if (j >= ((ArrayList)localObject3).size()) {
          ((ArrayList)localObject3).add(localObject2);
        } else {
          ((ArrayList)localObject3).set(j, localObject2);
        }
        localHashMap1.put("PREV", localObject3);
        localCCEntryHistInfo.setCursorId((String)localObject2);
        localCCEntryHistInfo.setHistId((String)localObject1);
        localCCEntryHistInfo.setStartDate("00000000");
        bool1 = false;
      }
      else if (paramPagingContext.getDirection().equalsIgnoreCase("PREVIOUS"))
      {
        localObject1 = (String)localHashMap1.get("HISTID");
        localObject2 = (String)localHashMap1.get("CURSORID");
        localObject3 = (ArrayList)localHashMap1.get("PREV");
        paramPagingContext.setLastIndex(paramPagingContext.getLastIndex() - l1);
        j = new Long(paramPagingContext.getFirstIndex()).intValue();
        if (j != 0) {
          j--;
        }
        paramPagingContext.setFirstIndex(j);
        if ((localObject3 != null) && (((ArrayList)localObject3).size() > 0))
        {
          localObject2 = (String)((ArrayList)localObject3).get(j);
          if (j == 0) {
            bool1 = true;
          }
          localHashMap1.put("PREV", localObject3);
        }
        localCCEntryHistInfo.setCursorId((String)localObject2);
        localCCEntryHistInfo.setHistId((String)localObject1);
        localCCEntryHistInfo.setStartDate("00000000");
      }
    }
    if ((paramHashMap != null) && (paramHashMap.get("transType") != null))
    {
      localObject1 = (String)paramHashMap.get("transType");
      if (((String)localObject1).equalsIgnoreCase("concentration"))
      {
        localObject2 = new String[] { "CONCENTRATION" };
        localCCEntryHistInfo.setCategoryTypeList((String[])localObject2);
      }
      else if (((String)localObject1).equalsIgnoreCase("disbursement"))
      {
        localObject2 = new String[] { "DISBURSEMENT" };
        localCCEntryHistInfo.setCategoryTypeList((String[])localObject2);
      }
    }
    Object localObject1 = null;
    Object localObject2 = getBPWHandler();
    Object localObject4;
    try
    {
      localObject1 = ((BPWServices)localObject2).getCCEntryHist(localCCEntryHistInfo);
    }
    catch (FFSException localFFSException)
    {
      localFFSException.printStackTrace();
    }
    catch (RemoteException localRemoteException)
    {
      localObject4 = localRemoteException.toString();
      if ((localObject4 != null) && (((String)localObject4).indexOf("Server is not running!") > 0)) {
        throw new CashConException(25018);
      }
      bool2 = true;
      bool1 = true;
    }
    finally
    {
      if (localObject2 != null) {
        removeBPWHandler((BPWServices)localObject2);
      }
    }
    if (localObject1 != null)
    {
      CCEntryInfo[] arrayOfCCEntryInfo = (CCEntryInfo[])((CCEntryHistInfo)localObject1).getCCEntryInfoList();
      localObject4 = null;
      String str1 = ((CCEntryHistInfo)localObject1).getCursorId();
      long l2 = ((CCEntryHistInfo)localObject1).getTotalTrans();
      localHashMap1.put("HISTID", ((CCEntryHistInfo)localObject1).getHistId());
      localHashMap1.put("CURSORID", str1);
      if (arrayOfCCEntryInfo != null)
      {
        HashMap localHashMap2 = new HashMap();
        localHashMap2.put("LocationResults", localLocationSearchResults);
        for (int k = 0; (k < arrayOfCCEntryInfo.length) && (localCashCons.size() < l1); k++)
        {
          localObject4 = arrayOfCCEntryInfo[k];
          com.ffusion.beans.cashcon.CashCon localCashCon;
          if (((CCEntryInfo)localObject4).getTransactionType().equals("CONCENTRATION")) {
            localCashCon = (com.ffusion.beans.cashcon.CashCon)localCashCons.createConcentration();
          } else {
            localCashCon = (com.ffusion.beans.cashcon.CashCon)localCashCons.createDisbursement();
          }
          BeansConverter.setCashConFromCCEntryInfo(localCashCon, (CCEntryInfo)localObject4, localHashMap2);
          String str2 = localCashCon.getLocationID();
          String str3 = "";
          if (localCashCon.getType() == 16) {
            str3 = "Cash Con - Disbursement Request";
          }
          if (localCashCon.getType() == 15) {
            str3 = "Cash Con - Deposit Entry";
          }
          Entitlement localEntitlement1 = new Entitlement(str3, null, null);
          try
          {
            if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement1))
            {
              localCashCon.setCanEdit("false");
              localCashCon.setCanView("false");
              localCashCon.setCanDelete("false");
            }
          }
          catch (CSILException localCSILException1) {}
          if (!localCashCon.getSubmittedBy().equals("" + paramSecureUser.getProfileID()))
          {
            String str4 = "";
            String str5 = "";
            String str6 = "";
            if (localCashCon.getType() == 16)
            {
              str4 = "Cash Con - Disbursement Request Delete Other";
              str5 = "Cash Con - Disbursement Request Edit Other";
              str6 = "Cash Con - Disbursement Request View Other";
            }
            if (localCashCon.getType() == 15)
            {
              str4 = "Cash Con - Deposit Entry Delete Other";
              str5 = "Cash Con - Deposit Entry Edit Other";
              str6 = "Cash Con - Deposit Entry View Other";
            }
            Entitlement localEntitlement2 = new Entitlement(str5, "Location", str2);
            Entitlement localEntitlement3 = new Entitlement(str6, "Location", str2);
            Entitlement localEntitlement4 = new Entitlement(str4, "Location", str2);
            try
            {
              if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement2)) {
                localCashCon.setCanEdit("false");
              }
              if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement3))
              {
                localCashCon.setCanView("false");
                localCashCon.setCanDelete("false");
                localCashCon.setCanEdit("false");
              }
              if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement4)) {
                localCashCon.setCanDelete("false");
              }
            }
            catch (CSILException localCSILException2) {}
          }
        }
        if (paramPagingContext.getLastIndex() + localCashCons.size() >= l2) {
          bool2 = true;
        }
      }
      localHashMap1.put("CURSORID", str1);
    }
    else
    {
      bool2 = true;
    }
    localCashCons.getPagingContext().setLastPage(bool2);
    localCashCons.getPagingContext().setFirstPage(bool1);
    return localCashCons;
  }
  
  public CashCons getPagedCashConHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null)
    {
      localHashMap = new HashMap();
      paramPagingContext.setMap(localHashMap);
    }
    long l1 = 10L;
    try
    {
      l1 = jdMethod_byte(paramHashMap);
    }
    catch (Exception localException1) {}
    localHashMap.put("PAGE_SIZE", String.valueOf(l1));
    LocationSearchResults localLocationSearchResults = getEntitledLocations(paramSecureUser);
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
    Object localObject7;
    Object localObject8;
    String str2;
    if (paramPagingContext.getDirection().equals("FIRST"))
    {
      localObject1 = null;
      if (localReportCriteria != null) {
        localObject1 = localReportCriteria.getSortCriteria();
      }
      localObject2 = new ArrayList();
      if (localObject1 != null) {
        for (int i = 0; i < ((ReportSortCriteria)localObject1).size(); i++)
        {
          localObject4 = (ReportSortCriterion)((ReportSortCriteria)localObject1).get(i);
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
      localObject4 = localLocationSearchResults.iterator();
      Object localObject5 = new String[localLocationSearchResults.size()];
      for (int j = 0; ((Iterator)localObject4).hasNext(); j++)
      {
        localObject7 = (LocationSearchResult)((Iterator)localObject4).next();
        localObject5[j] = new String(((LocationSearchResult)localObject7).getLocationBPWID());
      }
      ((HashMap)localObject3).put("LocationIds", localObject5);
      ((HashMap)localObject3).put("CustomerId", String.valueOf(paramSecureUser.getBusinessID()));
      localObject7 = null;
      String str1 = null;
      if (paramHashMap != null)
      {
        localObject7 = (String)paramHashMap.get("ACH_VIEW");
        str1 = (String)paramHashMap.get("CASHCON_STATUS");
      }
      int k = 0;
      if ("ALL".equals(localObject7) == true) {
        k = 1;
      }
      if (k == 0)
      {
        localObject8 = new String[] { String.valueOf(paramSecureUser.getProfileID()) };
        ((HashMap)localObject3).put("SubmittedBys", localObject8);
      }
      localObject8 = null;
      if ("CASHCON_STATUS_PENDING".equalsIgnoreCase(str1)) {
        localObject8 = new String[] { "CREATED", "WILLPROCESSON" };
      } else if ("CASHCON_STATUS_APPROVAL".equalsIgnoreCase(str1)) {
        localObject8 = new String[] { "APPROVAL_PENDING", "APPROVAL_REJECTED" };
      } else if ("CASHCON_STATUS_COMPLETED".equalsIgnoreCase(str1)) {
        localObject8 = new String[] { "PROCESSEDON", "POSTEDON", "CANCELEDON", "LIMIT_CHECK_FAILED", "LIMIT_REVERT_FAILED", "APPROVAL_FAILED", "FAILEDON" };
      }
      ((HashMap)localObject3).put("StatusList", localObject8);
      String[] arrayOfString = null;
      if ((paramHashMap != null) && (paramHashMap.get("CASHCON_TRAN_TYPE") != null))
      {
        str2 = (String)paramHashMap.get("CASHCON_TRAN_TYPE");
        if (str2.equalsIgnoreCase("Deposit")) {
          arrayOfString = new String[] { "CONCENTRATION" };
        } else if (str2.equalsIgnoreCase("Disbursement")) {
          arrayOfString = new String[] { "DISBURSEMENT" };
        }
      }
      ((HashMap)localObject3).put("TransType", arrayOfString);
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
    ((PagingInfo)localObject3).setPagingContext(paramPagingContext);
    Object localObject4 = null;
    try
    {
      localObject4 = getBPWHandler();
      localHashMap.remove("ReportCriteria");
      paramPagingContext.setMap(localHashMap);
      ((PagingInfo)localObject3).setPagingContext(paramPagingContext);
      localObject3 = ((BPWServices)localObject4).getPagedCashCon((PagingInfo)localObject3);
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("getCashConHistoriesPage ", localException2);
      localObject6 = localException2.toString();
      if ((localObject6 != null) && (((String)localObject6).indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
      throw new CSILException(-1009, 38529);
    }
    finally
    {
      if (localObject4 != null) {
        removeBPWHandler((BPWServices)localObject4);
      }
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
        long l2;
        if (localObject6 != null)
        {
          l2 = Long.parseLong(localObject6.toString());
          localHashMap.put("LOWER_BOUND_TransactionIndex", new Long(l2));
        }
        if (localObject7 != null)
        {
          l2 = Long.parseLong(localObject7.toString());
          localHashMap.put("UPPER_BOUND_TransactionIndex", new Long(l2));
        }
      }
      catch (Exception localException3)
      {
        DebugLog.log("CashConService.getPagedCashConHistories: Exception thrown!");
        localException3.printStackTrace();
      }
    }
    Object localObject6 = new CashCons();
    ((CashCons)localObject6).setPagingContext(((PagingInfo)localObject3).getPagingContext());
    if ((localArrayList != null) && (localArrayList.size() != 0))
    {
      CCEntryInfo localCCEntryInfo = null;
      localObject8 = new HashMap();
      ((HashMap)localObject8).put("LocationResults", localLocationSearchResults);
      for (int m = 0; m < localArrayList.size(); m++)
      {
        localObject7 = (ACHHistInfo)localArrayList.get(m);
        localCCEntryInfo = (CCEntryInfo)((ACHHistInfo)localObject7).getObjInfo();
        com.ffusion.beans.cashcon.CashCon localCashCon;
        if (localCCEntryInfo.getTransactionType().equals("CONCENTRATION")) {
          localCashCon = (com.ffusion.beans.cashcon.CashCon)((CashCons)localObject6).createConcentration();
        } else {
          localCashCon = (com.ffusion.beans.cashcon.CashCon)((CashCons)localObject6).createDisbursement();
        }
        BeansConverter.setCashConFromCCEntryInfo(localCashCon, localCCEntryInfo, (HashMap)localObject8);
        str2 = localCashCon.getLocationID();
        localCashCon.setTransactionIndex(((ACHHistInfo)localObject7).getCursorId());
        String str3 = "";
        if (localCashCon.getType() == 16) {
          str3 = "Cash Con - Disbursement Request";
        }
        if (localCashCon.getType() == 15) {
          str3 = "Cash Con - Deposit Entry";
        }
        Entitlement localEntitlement1 = new Entitlement(str3, null, null);
        try
        {
          if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement1))
          {
            localCashCon.setCanEdit("false");
            localCashCon.setCanView("false");
            localCashCon.setCanDelete("false");
          }
        }
        catch (CSILException localCSILException1) {}
        if (!localCashCon.getSubmittedBy().equals("" + paramSecureUser.getProfileID()))
        {
          String str4 = "";
          String str5 = "";
          String str6 = "";
          if (localCashCon.getType() == 16)
          {
            str4 = "Cash Con - Disbursement Request Delete Other";
            str5 = "Cash Con - Disbursement Request Edit Other";
            str6 = "Cash Con - Disbursement Request View Other";
          }
          if (localCashCon.getType() == 15)
          {
            str4 = "Cash Con - Deposit Entry Delete Other";
            str5 = "Cash Con - Deposit Entry Edit Other";
            str6 = "Cash Con - Deposit Entry View Other";
          }
          Entitlement localEntitlement2 = new Entitlement(str5, "Location", str2);
          Entitlement localEntitlement3 = new Entitlement(str6, "Location", str2);
          Entitlement localEntitlement4 = new Entitlement(str4, "Location", str2);
          try
          {
            if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement2)) {
              localCashCon.setCanEdit("false");
            }
            if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement3))
            {
              localCashCon.setCanView("false");
              localCashCon.setCanDelete("false");
              localCashCon.setCanEdit("false");
            }
            if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement4)) {
              localCashCon.setCanDelete("false");
            }
          }
          catch (CSILException localCSILException2) {}
        }
      }
    }
    return localObject6;
  }
  
  public static LocationSearchResults getEntitledLocations(SecureUser paramSecureUser)
  {
    LocationSearchResults localLocationSearchResults = new LocationSearchResults();
    try
    {
      localLocationSearchResults = com.ffusion.csil.core.CashCon.getEntitledLocations(paramSecureUser, new HashMap());
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    return localLocationSearchResults;
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CashConException
  {
    String str1 = "CashConService.getReportData";
    try
    {
      Properties localProperties1 = paramReportCriteria.getReportOptions();
      String str2 = localProperties1.getProperty("REPORTTYPE");
      if (str2 == null)
      {
        localObject = "The report options contained within the Report Criteria used in a call to getBCReportData does not contain a report type.";
        throw new CashConException(16002);
      }
      Object localObject = new HashMap();
      HashMap localHashMap = new HashMap();
      try
      {
        Reporting.calculateDateRange(paramSecureUser, null, paramReportCriteria, (HashMap)localObject, localHashMap, paramHashMap);
        paramHashMap.put("StartDates", localObject);
        paramHashMap.put("EndDates", localHashMap);
      }
      catch (CSILException localCSILException1)
      {
        str3 = "Unable to calculate the date ranges. A report cannot be run.";
        DebugLog.log(str3);
        if (localCSILException1.getCode() == -1009) {
          throw new CashConException(localCSILException1.getServiceError());
        }
        throw new CashConException(localCSILException1.getCode());
      }
      Properties localProperties2 = paramReportCriteria.getSearchCriteria();
      String str3 = "" + paramSecureUser.getBusinessID();
      localProperties2.setProperty("Business", str3);
      if (str2.equals("Cash Concentration Activity Report"))
      {
        String str4 = localProperties2.getProperty("Division");
        String str5 = localProperties2.getProperty("Location");
        if ((str4 != null) && (!str4.equals("AllDivisions")))
        {
          int i = -1;
          try
          {
            i = Integer.parseInt(str4);
          }
          catch (NumberFormatException localNumberFormatException)
          {
            throw new CashConException(21007);
          }
          EntitlementGroup localEntitlementGroup = null;
          try
          {
            localEntitlementGroup = Entitlements.getEntitlementGroup(i);
            if (localEntitlementGroup == null) {
              throw new CashConException(21007);
            }
            paramHashMap.put("ExtraDivisionName", localEntitlementGroup.getGroupName());
          }
          catch (CSILException localCSILException2)
          {
            throw new CashConException(21007);
          }
        }
        if ((str5 != null) && (!str5.equals("AllLocations")))
        {
          Location localLocation = null;
          try
          {
            localLocation = getLocation(paramSecureUser, str5, new HashMap());
          }
          catch (CashConException localCashConException2)
          {
            DebugLog.log("Exception while fetching location");
          }
          if (localLocation != null) {
            paramHashMap.put("ExtraLocationName", localLocation.getLocationName());
          }
        }
        return ReportTransactionAdapter.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      if (str2.equals("Inactive or Non-Reporting Divisions and Locations Report")) {
        return ReportTransactionAdapter.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      throw new CashConException(21007);
    }
    catch (AdminException localAdminException)
    {
      DebugLog.throwing("AdminException", localAdminException);
      throw new CashConException(21007);
    }
    catch (CashConException localCashConException1)
    {
      throw localCashConException1;
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception", localException);
      throw new CashConException(21007);
    }
  }
  
  private long jdMethod_byte(HashMap paramHashMap)
  {
    long l = this.c2;
    if (paramHashMap != null)
    {
      String str = (String)paramHashMap.get("PAGESIZE");
      if (str != null) {
        try
        {
          l = Integer.parseInt(str);
        }
        catch (Exception localException) {}
      }
    }
    return l;
  }
  
  protected int mapError(int paramInt)
  {
    int i = 1;
    switch (paramInt)
    {
    case 2006: 
    case 2007: 
    case 2008: 
      i = 1000;
      break;
    case 2003: 
    case 2009: 
    case 2010: 
      i = 1001;
      break;
    case 2005: 
      i = 1002;
      break;
    case 2011: 
      i = 1003;
      break;
    case 2002: 
      i = 1004;
      break;
    case 10504: 
      i = 1006;
      break;
    case 2012: 
      i = 1007;
      break;
    case 1008: 
      i = 1008;
      break;
    case 2014: 
      i = 1009;
      break;
    case 2015: 
      i = 1010;
      break;
    case 2001: 
      i = 1011;
      break;
    case 2016: 
      i = 1012;
      break;
    case 10508: 
      i = 1013;
      break;
    case 24240: 
      i = 38504;
      break;
    default: 
      i = 1;
    }
    return i;
  }
  
  public CashConCompanies getCashConCompanies(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    int i = 16002;
    String str1 = "";
    CCCompanyInfo[] arrayOfCCCompanyInfo = null;
    Object localObject1;
    try
    {
      CCCompanyInfoList localCCCompanyInfoList = new CCCompanyInfoList();
      localCCCompanyInfoList.setCustomerId(paramString1);
      localCCCompanyInfoList.setFIId(paramString2);
      localObject1 = (String)paramHashMap.get("IncludeDeletedCompanies");
      if ((localObject1 != null) && (((String)localObject1).equalsIgnoreCase("true"))) {
        localCCCompanyInfoList.setIncludeDeletedEntries(true);
      }
      localCCCompanyInfoList = localBPWServices.getCCCompanyList(localCCCompanyInfoList);
      if (localCCCompanyInfoList != null)
      {
        i = localCCCompanyInfoList.getStatusCode();
        str1 = localCCCompanyInfoList.getStatusMsg();
        arrayOfCCCompanyInfo = localCCCompanyInfoList.getCCCompanyInfoList();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.getCashConCompanies exception: " + localThrowable.getMessage());
      throw new CashConException(38507, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i == 16020) {
      return new CashConCompanies();
    }
    if (i == 0)
    {
      CashConCompanies localCashConCompanies = new CashConCompanies();
      for (int j = 0; j < arrayOfCCCompanyInfo.length; j++)
      {
        localObject1 = new CashConCompany();
        ((CashConCompany)localObject1).setBPWID(arrayOfCCCompanyInfo[j].getCompId());
        ((CashConCompany)localObject1).setCustID(arrayOfCCCompanyInfo[j].getCustomerId());
        ((CashConCompany)localObject1).setCompanyID(arrayOfCCCompanyInfo[j].getCCCompId());
        ((CashConCompany)localObject1).setCompanyName(arrayOfCCCompanyInfo[j].getCompName());
        if (arrayOfCCCompanyInfo[j].getStatus().equals("ACTIVE")) {
          ((CashConCompany)localObject1).setActive(true);
        } else {
          ((CashConCompany)localObject1).setActive(false);
        }
        if ("CANCELEDON".equals(arrayOfCCCompanyInfo[j].getStatus())) {
          ((CashConCompany)localObject1).setIsDeleted(true);
        } else {
          ((CashConCompany)localObject1).setIsDeleted(false);
        }
        if (arrayOfCCCompanyInfo[j].getBatchType().equals("BatchBalancedBatch")) {
          ((CashConCompany)localObject1).setBatchType(2);
        } else if (arrayOfCCCompanyInfo[j].getBatchType().equals("EntryBalancedBatch")) {
          ((CashConCompany)localObject1).setBatchType(3);
        }
        String str2 = arrayOfCCCompanyInfo[j].getConcentrateAcctId();
        String str3 = arrayOfCCCompanyInfo[j].getDisburseAcctId();
        if (str2 != null) {
          paramHashMap.put("DEFAULTACCOUNT", str2);
        }
        getConcAccounts(paramSecureUser, null, (CashConCompany)localObject1, paramHashMap);
        if (str2 != null) {
          paramHashMap.put("DEFAULTACCOUNT", str3);
        }
        getDisbAccounts(paramSecureUser, null, (CashConCompany)localObject1, paramHashMap);
        getConcCutOffs(paramSecureUser, null, (CashConCompany)localObject1, paramHashMap);
        getDisbCutOffs(paramSecureUser, null, (CashConCompany)localObject1, paramHashMap);
        localCashConCompanies.add(localObject1);
      }
      return localCashConCompanies;
    }
    System.out.println("BPW error:" + i);
    System.out.println("msg:" + str1);
    throw new CashConException(38507, str1);
  }
  
  public CashConCompany addCashConCompany(SecureUser paramSecureUser, CashConCompany paramCashConCompany, String paramString, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    int i = 16002;
    CCCompanyInfo localCCCompanyInfo = new CCCompanyInfo();
    localCCCompanyInfo.setCCCompId(paramCashConCompany.getCompanyID());
    localCCCompanyInfo.setCompName(paramCashConCompany.getCompanyName());
    localCCCompanyInfo.setCustomerId(paramCashConCompany.getCustID());
    localCCCompanyInfo.setStatus(paramCashConCompany.getActive() ? "ACTIVE" : "INACTIVE");
    if (paramCashConCompany.getBatchType() == 2) {
      localCCCompanyInfo.setBatchType("BatchBalancedBatch");
    } else if (paramCashConCompany.getBatchType() == 3) {
      localCCCompanyInfo.setBatchType("EntryBalancedBatch");
    }
    localCCCompanyInfo.setSubmittedBy(Integer.toString(paramSecureUser.getProfileID()));
    localCCCompanyInfo.setLogId(paramCashConCompany.getTrackingID());
    String str = "";
    populateOBOAgentInfo(paramSecureUser, localCCCompanyInfo);
    try
    {
      localCCCompanyInfo = localBPWServices.addCCCompany(localCCCompanyInfo);
      if (localCCCompanyInfo != null)
      {
        i = localCCCompanyInfo.getStatusCode();
        str = localCCCompanyInfo.getStatusMsg();
      }
      if (i == 0)
      {
        paramCashConCompany.setBPWID(localCCCompanyInfo.getCompId());
        CashConAccounts localCashConAccounts = paramCashConCompany.getConcAccounts();
        if (localCashConAccounts != null)
        {
          localObject1 = null;
          for (int j = 0; j < localCashConAccounts.size(); j++)
          {
            localObject1 = (CashConAccount)localCashConAccounts.get(j);
            addConcAccount(paramSecureUser, (CashConAccount)localObject1, paramCashConCompany, paramHashMap);
            if (((CashConAccount)localObject1).getDefaultAccount() == true)
            {
              localCCCompanyInfo.setConcentrateAcctId(((CashConAccount)localObject1).getBPWID());
              localCCCompanyInfo = localBPWServices.modCCCompany(localCCCompanyInfo);
              if ((localCCCompanyInfo != null) && (localCCCompanyInfo.getStatusCode() != 0))
              {
                CSILException localCSILException2 = new CSILException("", 0, localCCCompanyInfo.getStatusMsg());
                throw new CashConException(38508, "Unable to set default status.\n" + localCCCompanyInfo.getStatusMsg(), localCSILException2);
              }
            }
          }
        }
        Object localObject1 = paramCashConCompany.getDisbAccounts();
        if (localObject1 != null)
        {
          localObject2 = null;
          for (int k = 0; k < ((CashConAccounts)localObject1).size(); k++)
          {
            localObject2 = (CashConAccount)((CashConAccounts)localObject1).get(k);
            addDisbAccount(paramSecureUser, (CashConAccount)localObject2, paramCashConCompany, paramHashMap);
            if (((CashConAccount)localObject2).getDefaultAccount() == true)
            {
              localCCCompanyInfo.setDisburseAcctId(((CashConAccount)localObject2).getBPWID());
              localCCCompanyInfo = localBPWServices.modCCCompany(localCCCompanyInfo);
              if ((localCCCompanyInfo != null) && (localCCCompanyInfo.getStatusCode() != 0))
              {
                CSILException localCSILException3 = new CSILException("", 0, localCCCompanyInfo.getStatusMsg());
                throw new CashConException(38508, "Unable to set default status.\n" + localCCCompanyInfo.getStatusMsg(), localCSILException3);
              }
            }
          }
        }
        Object localObject2 = paramCashConCompany.getConcCutOffs();
        if (localObject2 != null)
        {
          localObject3 = null;
          for (int m = 0; m < ((CutOffTimes)localObject2).size(); m++)
          {
            localObject3 = (CutOffTime)((CutOffTimes)localObject2).get(m);
            addConcCutOff(paramSecureUser, (CutOffTime)localObject3, paramCashConCompany, paramHashMap);
          }
        }
        Object localObject3 = paramCashConCompany.getDisbCutOffs();
        if (localObject3 != null)
        {
          CutOffTime localCutOffTime = null;
          for (int n = 0; n < ((CutOffTimes)localObject3).size(); n++)
          {
            localCutOffTime = (CutOffTime)((CutOffTimes)localObject3).get(n);
            addDisbCutOff(paramSecureUser, localCutOffTime, paramCashConCompany, paramHashMap);
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.addCashConCompany exception: " + localThrowable.getMessage());
      throw new CashConException(38508, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i == 0) {
      return paramCashConCompany;
    }
    System.out.println("BPW error:" + i);
    System.out.println("msg:" + str);
    CSILException localCSILException1 = new CSILException("", 0, str);
    throw new CashConException(38508, str, localCSILException1);
  }
  
  public void modifyCashConCompany(SecureUser paramSecureUser, CashConCompany paramCashConCompany1, CashConCompany paramCashConCompany2, String paramString, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    int i = 16002;
    CCCompanyInfo localCCCompanyInfo = new CCCompanyInfo();
    localCCCompanyInfo.setCompId(paramCashConCompany1.getBPWID());
    localCCCompanyInfo.setCCCompId(paramCashConCompany1.getCompanyID());
    localCCCompanyInfo.setCompName(paramCashConCompany1.getCompanyName());
    localCCCompanyInfo.setCustomerId(paramCashConCompany1.getCustID());
    localCCCompanyInfo.setStatus(paramCashConCompany1.getActive() ? "ACTIVE" : "INACTIVE");
    if (paramCashConCompany1.getBatchType() == 2) {
      localCCCompanyInfo.setBatchType("BatchBalancedBatch");
    } else if (paramCashConCompany1.getBatchType() == 3) {
      localCCCompanyInfo.setBatchType("EntryBalancedBatch");
    }
    localCCCompanyInfo.setSubmittedBy(Integer.toString(paramSecureUser.getProfileID()));
    localCCCompanyInfo.setLogId(TrackingIDGenerator.GetNextID());
    String str = "";
    populateOBOAgentInfo(paramSecureUser, localCCCompanyInfo);
    try
    {
      localCCCompanyInfo = localBPWServices.modCCCompany(localCCCompanyInfo);
      if (localCCCompanyInfo != null)
      {
        i = localCCCompanyInfo.getStatusCode();
        str = localCCCompanyInfo.getStatusMsg();
      }
      if (i == 0)
      {
        paramCashConCompany1.setBPWID(localCCCompanyInfo.getCompId());
        CashConAccounts localCashConAccounts1 = new CashConAccounts();
        CashConAccounts localCashConAccounts2 = new CashConAccounts();
        a(paramCashConCompany2.getConcAccounts(), paramCashConCompany1.getConcAccounts(), localCashConAccounts1, localCashConAccounts2);
        CashConAccount localCashConAccount = null;
        for (int j = 0; j < localCashConAccounts1.size(); j++)
        {
          localCashConAccount = (CashConAccount)localCashConAccounts1.get(j);
          addConcAccount(paramSecureUser, localCashConAccount, paramCashConCompany1, paramHashMap);
          if (localCashConAccount.getDefaultAccount() == true)
          {
            localCCCompanyInfo.setConcentrateAcctId(localCashConAccount.getBPWID());
            localCCCompanyInfo = localBPWServices.modCCCompany(localCCCompanyInfo);
            if ((localCCCompanyInfo != null) && (localCCCompanyInfo.getStatusCode() != 0))
            {
              CSILException localCSILException2 = new CSILException("", 0, localCCCompanyInfo.getStatusMsg());
              throw new CashConException(38510, "Unable to set default status.\n" + localCCCompanyInfo.getStatusMsg(), localCSILException2);
            }
          }
        }
        for (j = 0; j < localCashConAccounts2.size(); j++)
        {
          localCashConAccount = (CashConAccount)localCashConAccounts2.get(j);
          deleteConcAccount(paramSecureUser, localCashConAccount, paramCashConCompany1, paramHashMap);
        }
        CashConAccounts localCashConAccounts3 = paramCashConCompany1.getConcAccounts();
        for (int k = 0; k < localCashConAccounts3.size(); k++)
        {
          localCashConAccount = (CashConAccount)localCashConAccounts3.get(k);
          if ((localCashConAccount.getDefaultAccount() == true) && (localCashConAccount.getBPWID() != null) && (localCashConAccount.getBPWID().length() > 0))
          {
            localCCCompanyInfo.setConcentrateAcctId(localCashConAccount.getBPWID());
            localCCCompanyInfo = localBPWServices.modCCCompany(localCCCompanyInfo);
            if ((localCCCompanyInfo == null) || (localCCCompanyInfo.getStatusCode() == 0)) {
              break;
            }
            localObject1 = new CSILException("", 0, localCCCompanyInfo.getStatusMsg());
            throw new CashConException(38510, "Unable to set default status.\n" + localCCCompanyInfo.getStatusMsg(), (Throwable)localObject1);
          }
        }
        localCashConAccounts1 = new CashConAccounts();
        localCashConAccounts2 = new CashConAccounts();
        a(paramCashConCompany2.getDisbAccounts(), paramCashConCompany1.getDisbAccounts(), localCashConAccounts1, localCashConAccounts2);
        localCashConAccount = null;
        for (k = 0; k < localCashConAccounts1.size(); k++)
        {
          localCashConAccount = (CashConAccount)localCashConAccounts1.get(k);
          addDisbAccount(paramSecureUser, localCashConAccount, paramCashConCompany1, paramHashMap);
          if (localCashConAccount.getDefaultAccount() == true)
          {
            localCCCompanyInfo.setDisburseAcctId(localCashConAccount.getBPWID());
            localCCCompanyInfo = localBPWServices.modCCCompany(localCCCompanyInfo);
            if ((localCCCompanyInfo != null) && (localCCCompanyInfo.getStatusCode() != 0))
            {
              localObject1 = new CSILException("", 0, localCCCompanyInfo.getStatusMsg());
              throw new CashConException(38510, "Unable to set default status.\n" + localCCCompanyInfo.getStatusMsg(), (Throwable)localObject1);
            }
          }
        }
        for (k = 0; k < localCashConAccounts2.size(); k++)
        {
          localCashConAccount = (CashConAccount)localCashConAccounts2.get(k);
          deleteDisbAccount(paramSecureUser, localCashConAccount, paramCashConCompany1, paramHashMap);
        }
        localCashConAccounts3 = paramCashConCompany1.getDisbAccounts();
        for (k = 0; k < localCashConAccounts3.size(); k++)
        {
          localCashConAccount = (CashConAccount)localCashConAccounts3.get(k);
          if ((localCashConAccount.getDefaultAccount() == true) && (localCashConAccount.getBPWID() != null) && (localCashConAccount.getBPWID().length() > 0))
          {
            localCCCompanyInfo.setDisburseAcctId(localCashConAccount.getBPWID());
            localCCCompanyInfo = localBPWServices.modCCCompany(localCCCompanyInfo);
            if ((localCCCompanyInfo == null) || (localCCCompanyInfo.getStatusCode() == 0)) {
              break;
            }
            localObject1 = new CSILException("", 0, localCCCompanyInfo.getStatusMsg());
            throw new CashConException(38510, "Unable to set default status.\n" + localCCCompanyInfo.getStatusMsg(), (Throwable)localObject1);
          }
        }
        CutOffTimes localCutOffTimes = new CutOffTimes();
        Object localObject1 = new CutOffTimes();
        a(paramCashConCompany2.getConcCutOffs(), paramCashConCompany1.getConcCutOffs(), localCutOffTimes, (CutOffTimes)localObject1);
        CutOffTime localCutOffTime = null;
        for (int m = 0; m < localCutOffTimes.size(); m++)
        {
          localCutOffTime = (CutOffTime)localCutOffTimes.get(m);
          addConcCutOff(paramSecureUser, localCutOffTime, paramCashConCompany1, paramHashMap);
        }
        for (m = 0; m < ((CutOffTimes)localObject1).size(); m++)
        {
          localCutOffTime = (CutOffTime)((CutOffTimes)localObject1).get(m);
          deleteConcCutOff(paramSecureUser, localCutOffTime, paramCashConCompany1, paramHashMap);
        }
        localCutOffTimes = new CutOffTimes();
        localObject1 = new CutOffTimes();
        a(paramCashConCompany2.getDisbCutOffs(), paramCashConCompany1.getDisbCutOffs(), localCutOffTimes, (CutOffTimes)localObject1);
        localCutOffTime = null;
        for (m = 0; m < localCutOffTimes.size(); m++)
        {
          localCutOffTime = (CutOffTime)localCutOffTimes.get(m);
          addDisbCutOff(paramSecureUser, localCutOffTime, paramCashConCompany1, paramHashMap);
        }
        for (m = 0; m < ((CutOffTimes)localObject1).size(); m++)
        {
          localCutOffTime = (CutOffTime)((CutOffTimes)localObject1).get(m);
          deleteDisbCutOff(paramSecureUser, localCutOffTime, paramCashConCompany1, paramHashMap);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.modifyCashConCompany exception: " + localThrowable.getMessage());
      throw new CashConException(38510, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i == 0)
    {
      paramCashConCompany1.setBPWID(localCCCompanyInfo.getCompId());
    }
    else
    {
      System.out.println("BPW error:" + i);
      System.out.println("msg:" + str);
      CSILException localCSILException1 = new CSILException("", 0, str);
      throw new CashConException(38510, str, localCSILException1);
    }
  }
  
  private static void a(CashConAccounts paramCashConAccounts1, CashConAccounts paramCashConAccounts2, CashConAccounts paramCashConAccounts3, CashConAccounts paramCashConAccounts4)
  {
    if (paramCashConAccounts2 != null)
    {
      CashConAccount localCashConAccount;
      for (int i = 0; i < paramCashConAccounts2.size(); i++)
      {
        localCashConAccount = (CashConAccount)paramCashConAccounts2.get(i);
        if ((paramCashConAccounts1 == null) || (!a(paramCashConAccounts1, localCashConAccount))) {
          paramCashConAccounts3.add(localCashConAccount);
        }
      }
      if (paramCashConAccounts1 != null) {
        for (i = 0; i < paramCashConAccounts1.size(); i++)
        {
          localCashConAccount = (CashConAccount)paramCashConAccounts1.get(i);
          if (!a(paramCashConAccounts2, localCashConAccount)) {
            paramCashConAccounts4.add(localCashConAccount);
          }
        }
      }
    }
  }
  
  private static void a(CutOffTimes paramCutOffTimes1, CutOffTimes paramCutOffTimes2, CutOffTimes paramCutOffTimes3, CutOffTimes paramCutOffTimes4)
  {
    if (paramCutOffTimes2 != null)
    {
      CutOffTime localCutOffTime;
      for (int i = 0; i < paramCutOffTimes2.size(); i++)
      {
        localCutOffTime = (CutOffTime)paramCutOffTimes2.get(i);
        if ((paramCutOffTimes1 == null) || (!a(paramCutOffTimes1, localCutOffTime))) {
          paramCutOffTimes3.add(localCutOffTime);
        }
      }
      if (paramCutOffTimes1 != null) {
        for (i = 0; i < paramCutOffTimes1.size(); i++)
        {
          localCutOffTime = (CutOffTime)paramCutOffTimes1.get(i);
          if (!a(paramCutOffTimes2, localCutOffTime)) {
            paramCutOffTimes4.add(localCutOffTime);
          }
        }
      }
    }
  }
  
  private static boolean a(CashConAccounts paramCashConAccounts, CashConAccount paramCashConAccount)
  {
    if (paramCashConAccounts != null)
    {
      for (int i = 0; i < paramCashConAccounts.size(); i++)
      {
        CashConAccount localCashConAccount = (CashConAccount)paramCashConAccounts.get(i);
        if ((localCashConAccount.getNumber().equals(paramCashConAccount.getNumber())) && (localCashConAccount.getRoutingNumber().equals(paramCashConAccount.getRoutingNumber()))) {
          return true;
        }
      }
      return false;
    }
    return false;
  }
  
  private static boolean a(CutOffTimes paramCutOffTimes, CutOffTime paramCutOffTime)
  {
    if (paramCutOffTimes != null)
    {
      for (int i = 0; i < paramCutOffTimes.size(); i++)
      {
        CutOffTime localCutOffTime = (CutOffTime)paramCutOffTimes.get(i);
        if (localCutOffTime.getCutOffId().equals(paramCutOffTime.getCutOffId())) {
          return true;
        }
      }
      return false;
    }
    return false;
  }
  
  public void deleteCashConCompany(SecureUser paramSecureUser, CashConCompany paramCashConCompany, String paramString, HashMap paramHashMap)
    throws CashConException
  {
    int i = 16002;
    String str = "";
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    try
    {
      CCCompanyInfo localCCCompanyInfo = new CCCompanyInfo();
      localCCCompanyInfo.setCompId(paramCashConCompany.getBPWID());
      populateOBOAgentInfo(paramSecureUser, localCCCompanyInfo);
      localCCCompanyInfo = localBPWServices.cancelCCCompany(localCCCompanyInfo);
      if (localCCCompanyInfo != null)
      {
        i = localCCCompanyInfo.getStatusCode();
        str = localCCCompanyInfo.getStatusMsg();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.deleteCashConCompany exception: " + localThrowable.getMessage());
      throw new CashConException(38509, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i != 0)
    {
      System.out.println("BPW error:" + i);
      System.out.println("BPW msg:" + str);
      CSILException localCSILException = new CSILException("", 0, str);
      throw new CashConException(38509, str, localCSILException);
    }
  }
  
  public void addConcAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    int i = 16002;
    String str = "";
    try
    {
      CCCompanyAcctInfo localCCCompanyAcctInfo = new CCCompanyAcctInfo();
      localCCCompanyAcctInfo.setCompId(paramCashConCompany.getBPWID());
      localCCCompanyAcctInfo.setTransactionType("CONCENTRATION");
      localCCCompanyAcctInfo.setBankName(paramCashConAccount.getBankName());
      localCCCompanyAcctInfo.setAcctNumber(paramCashConAccount.getNumber());
      localCCCompanyAcctInfo.setAcctNickName(paramCashConAccount.getNickname());
      localCCCompanyAcctInfo.setCurrency(paramCashConAccount.getCurrency());
      if (paramCashConAccount.getType() == 1) {
        localCCCompanyAcctInfo.setAcctType("Checking");
      } else if (paramCashConAccount.getType() == 2) {
        localCCCompanyAcctInfo.setAcctType("Savings");
      }
      localCCCompanyAcctInfo.setBankRTN(paramCashConAccount.getRoutingNumber());
      localCCCompanyAcctInfo = localBPWServices.addCCCompanyAcct(localCCCompanyAcctInfo);
      if (localCCCompanyAcctInfo != null)
      {
        i = localCCCompanyAcctInfo.getStatusCode();
        if (i == 0)
        {
          paramCashConAccount.setBPWID(localCCCompanyAcctInfo.getAcctId());
        }
        else
        {
          DebugLog.log(Level.INFO, "*** BPW addCCCompanyAcct() failed!");
          DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
          DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localCCCompanyAcctInfo.getStatusMsg());
          CSILException localCSILException = new CSILException("", 0, localCCCompanyAcctInfo.getStatusMsg());
          throw new CashConException(38512, localCCCompanyAcctInfo.getStatusMsg(), localCSILException);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.addConcAccount exception: " + localThrowable.getMessage());
      throw new CashConException(38512, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
  }
  
  public CashConAccounts getConcAccounts(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    int i = 16002;
    String str1 = "";
    CCCompanyAcctInfo[] arrayOfCCCompanyAcctInfo = null;
    try
    {
      CCCompanyAcctInfoList localCCCompanyAcctInfoList = new CCCompanyAcctInfoList();
      localCCCompanyAcctInfoList.setCustomerId(paramCashConCompany.getCustID());
      localCCCompanyAcctInfoList.setCompId(paramCashConCompany.getBPWID());
      localCCCompanyAcctInfoList.setTransactionType("CONCENTRATION");
      localCCCompanyAcctInfoList = localBPWServices.getCCCompanyAcctList(localCCCompanyAcctInfoList);
      if (localCCCompanyAcctInfoList != null)
      {
        i = localCCCompanyAcctInfoList.getStatusCode();
        str1 = localCCCompanyAcctInfoList.getStatusMsg();
        arrayOfCCCompanyAcctInfo = localCCCompanyAcctInfoList.getCCCompanyAcctInfoList();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.getConcAccounts exception: " + localThrowable.getMessage());
      throw new CashConException(38511, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i == 16020) {
      return new CashConAccounts();
    }
    if (i == 0)
    {
      localObject1 = new CashConAccounts();
      String str2 = (String)paramHashMap.get("DEFAULTACCOUNT");
      for (int j = 0; j < arrayOfCCCompanyAcctInfo.length; j++)
      {
        CashConAccount localCashConAccount = new CashConAccount();
        localCashConAccount.setBPWID(arrayOfCCCompanyAcctInfo[j].getAcctId());
        localCashConAccount.setBankName(arrayOfCCCompanyAcctInfo[j].getBankName());
        localCashConAccount.setNumber(arrayOfCCCompanyAcctInfo[j].getAcctNumber());
        localCashConAccount.setNickname(arrayOfCCCompanyAcctInfo[j].getAcctNickName());
        localCashConAccount.setCurrency(arrayOfCCCompanyAcctInfo[j].getCurrency());
        if (arrayOfCCCompanyAcctInfo[j].getAcctType().equals("Checking")) {
          localCashConAccount.setType(1);
        } else if (arrayOfCCCompanyAcctInfo[j].getAcctType().equals("Savings")) {
          localCashConAccount.setType(2);
        } else {
          localCashConAccount.setType(0);
        }
        localCashConAccount.setRoutingNumber(arrayOfCCCompanyAcctInfo[j].getBankRTN());
        if ((str2 != null) && (str2.equals(arrayOfCCCompanyAcctInfo[j].getAcctId()))) {
          localCashConAccount.setDefaultAccount(true);
        } else {
          localCashConAccount.setDefaultAccount(false);
        }
        ((CashConAccounts)localObject1).add(localCashConAccount);
      }
      paramCashConCompany.setConcAccounts((CashConAccounts)localObject1);
      return localObject1;
    }
    System.out.println("BPW error:" + i);
    System.out.println("msg:" + str1);
    Object localObject1 = new CSILException("", 0, str1);
    throw new CashConException(38511, str1, (Throwable)localObject1);
  }
  
  public void deleteConcAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException
  {
    int i = 16002;
    String str = "";
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    try
    {
      CCCompanyAcctInfo localCCCompanyAcctInfo = new CCCompanyAcctInfo();
      localCCCompanyAcctInfo.setAcctId(paramCashConAccount.getBPWID());
      localCCCompanyAcctInfo.setCompId(paramCashConCompany.getBPWID());
      localCCCompanyAcctInfo.setTransactionType("CONCENTRATION");
      localCCCompanyAcctInfo = localBPWServices.cancelCCCompanyAcct(localCCCompanyAcctInfo);
      if (localCCCompanyAcctInfo != null)
      {
        i = localCCCompanyAcctInfo.getStatusCode();
        str = localCCCompanyAcctInfo.getStatusMsg();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.deleteConcAccount exception: " + localThrowable.getMessage());
      throw new CashConException(38513, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i != 0)
    {
      System.out.println("BPW error:" + i);
      System.out.println("msg:" + str);
      CSILException localCSILException = new CSILException("", 0, str);
      throw new CashConException(38513, str, localCSILException);
    }
  }
  
  public void addDisbAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    int i = 16002;
    String str = "";
    try
    {
      CCCompanyAcctInfo localCCCompanyAcctInfo = new CCCompanyAcctInfo();
      localCCCompanyAcctInfo.setCompId(paramCashConCompany.getBPWID());
      localCCCompanyAcctInfo.setTransactionType("DISBURSEMENT");
      localCCCompanyAcctInfo.setBankName(paramCashConAccount.getBankName());
      localCCCompanyAcctInfo.setAcctNumber(paramCashConAccount.getNumber());
      localCCCompanyAcctInfo.setAcctNickName(paramCashConAccount.getNickname());
      localCCCompanyAcctInfo.setCurrency(paramCashConAccount.getCurrency());
      if (paramCashConAccount.getType() == 1) {
        localCCCompanyAcctInfo.setAcctType("Checking");
      } else if (paramCashConAccount.getType() == 2) {
        localCCCompanyAcctInfo.setAcctType("Savings");
      }
      localCCCompanyAcctInfo.setBankRTN(paramCashConAccount.getRoutingNumber());
      localCCCompanyAcctInfo = localBPWServices.addCCCompanyAcct(localCCCompanyAcctInfo);
      if (localCCCompanyAcctInfo != null)
      {
        i = localCCCompanyAcctInfo.getStatusCode();
        str = localCCCompanyAcctInfo.getStatusMsg();
        if (i == 0) {
          paramCashConAccount.setBPWID(localCCCompanyAcctInfo.getAcctId());
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.addDisbAccount exception: " + localThrowable.getMessage());
      throw new CashConException(38520, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i != 0)
    {
      System.out.println("BPW error:" + i);
      System.out.println("msg:" + str);
      CSILException localCSILException = new CSILException("", 0, str);
      throw new CashConException(38520, str, localCSILException);
    }
  }
  
  public CashConAccounts getDisbAccounts(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    int i = 16002;
    String str1 = "";
    CCCompanyAcctInfo[] arrayOfCCCompanyAcctInfo = null;
    try
    {
      CCCompanyAcctInfoList localCCCompanyAcctInfoList = new CCCompanyAcctInfoList();
      localCCCompanyAcctInfoList.setCustomerId(paramCashConCompany.getCustID());
      localCCCompanyAcctInfoList.setCompId(paramCashConCompany.getBPWID());
      localCCCompanyAcctInfoList.setTransactionType("DISBURSEMENT");
      localCCCompanyAcctInfoList = localBPWServices.getCCCompanyAcctList(localCCCompanyAcctInfoList);
      if (localCCCompanyAcctInfoList != null)
      {
        i = localCCCompanyAcctInfoList.getStatusCode();
        str1 = localCCCompanyAcctInfoList.getStatusMsg();
        arrayOfCCCompanyAcctInfo = localCCCompanyAcctInfoList.getCCCompanyAcctInfoList();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.getDisbAccounts exception: " + localThrowable.getMessage());
      throw new CashConException(38519, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i == 16020) {
      return new CashConAccounts();
    }
    if (i == 0)
    {
      CashConAccounts localCashConAccounts = new CashConAccounts();
      String str2 = (String)paramHashMap.get("DEFAULTACCOUNT");
      for (int j = 0; j < arrayOfCCCompanyAcctInfo.length; j++)
      {
        CashConAccount localCashConAccount = new CashConAccount();
        localCashConAccount.setBPWID(arrayOfCCCompanyAcctInfo[j].getAcctId());
        localCashConAccount.setBankName(arrayOfCCCompanyAcctInfo[j].getBankName());
        localCashConAccount.setNumber(arrayOfCCCompanyAcctInfo[j].getAcctNumber());
        localCashConAccount.setNickname(arrayOfCCCompanyAcctInfo[j].getAcctNickName());
        localCashConAccount.setCurrency(arrayOfCCCompanyAcctInfo[j].getCurrency());
        if (arrayOfCCCompanyAcctInfo[j].getAcctType().equals("Checking")) {
          localCashConAccount.setType(1);
        } else if (arrayOfCCCompanyAcctInfo[j].getAcctType().equals("Savings")) {
          localCashConAccount.setType(2);
        } else {
          localCashConAccount.setType(0);
        }
        localCashConAccount.setRoutingNumber(arrayOfCCCompanyAcctInfo[j].getBankRTN());
        if ((str2 != null) && (str2.equals(arrayOfCCCompanyAcctInfo[j].getAcctId()))) {
          localCashConAccount.setDefaultAccount(true);
        } else {
          localCashConAccount.setDefaultAccount(false);
        }
        localCashConAccounts.add(localCashConAccount);
      }
      paramCashConCompany.setDisbAccounts(localCashConAccounts);
      return localCashConAccounts;
    }
    System.out.println("BPW error:" + i);
    System.out.println("msg:" + str1);
    throw new CashConException(38519, str1);
  }
  
  public void deleteDisbAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException
  {
    int i = 16002;
    String str = "";
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    try
    {
      CCCompanyAcctInfo localCCCompanyAcctInfo = new CCCompanyAcctInfo();
      localCCCompanyAcctInfo.setAcctId(paramCashConAccount.getBPWID());
      localCCCompanyAcctInfo.setTransactionType("DISBURSEMENT");
      localCCCompanyAcctInfo.setCompId(paramCashConCompany.getBPWID());
      localCCCompanyAcctInfo = localBPWServices.cancelCCCompanyAcct(localCCCompanyAcctInfo);
      if (localCCCompanyAcctInfo != null)
      {
        i = localCCCompanyAcctInfo.getStatusCode();
        str = localCCCompanyAcctInfo.getStatusMsg();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.deleteDisbAccount exception: " + localThrowable.getMessage());
      throw new CashConException(38521, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i != 0)
    {
      System.out.println("BPW error:" + i);
      System.out.println("msg:" + str);
      CSILException localCSILException = new CSILException("", 0, str);
      throw new CashConException(38521, str, localCSILException);
    }
  }
  
  public void addConcCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    int i = 16002;
    String str = "";
    try
    {
      CCCompanyCutOffInfo localCCCompanyCutOffInfo = new CCCompanyCutOffInfo();
      localCCCompanyCutOffInfo.setCompId(paramCashConCompany.getBPWID());
      localCCCompanyCutOffInfo.setTransactionType("CONCENTRATION");
      localCCCompanyCutOffInfo.setCutOffId(paramCutOffTime.getCutOffId());
      populateOBOAgentInfo(paramSecureUser, localCCCompanyCutOffInfo);
      localCCCompanyCutOffInfo = localBPWServices.addCCCompanyCutOff(localCCCompanyCutOffInfo);
      if (localCCCompanyCutOffInfo != null)
      {
        i = localCCCompanyCutOffInfo.getStatusCode();
        str = localCCCompanyCutOffInfo.getStatusMsg();
      }
      if (i == 0) {
        paramCutOffTime.setCompCutOffId(localCCCompanyCutOffInfo.getCompCutOffId());
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.addConcCutOff exception: " + localThrowable.getMessage());
      throw new CashConException(38516, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i != 0)
    {
      System.out.println("BPW error:" + i);
      System.out.println("msg:" + str);
      CSILException localCSILException = new CSILException("", 0, str);
      throw new CashConException(38516, str, localCSILException);
    }
  }
  
  public CutOffTimes getConcCutOffs(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    int i = 16002;
    String str = "";
    CCCompanyCutOffInfo[] arrayOfCCCompanyCutOffInfo = null;
    try
    {
      CCCompanyCutOffInfoList localCCCompanyCutOffInfoList = new CCCompanyCutOffInfoList();
      localCCCompanyCutOffInfoList.setCustomerId(paramCashConCompany.getCustID());
      localCCCompanyCutOffInfoList.setCompId(paramCashConCompany.getBPWID());
      localCCCompanyCutOffInfoList.setTransactionType("CONCENTRATION");
      localCCCompanyCutOffInfoList = localBPWServices.getCCCompanyCutOffList(localCCCompanyCutOffInfoList);
      if (localCCCompanyCutOffInfoList != null)
      {
        i = localCCCompanyCutOffInfoList.getStatusCode();
        str = localCCCompanyCutOffInfoList.getStatusMsg();
        arrayOfCCCompanyCutOffInfo = localCCCompanyCutOffInfoList.getCCCompanyCutOffInfoList();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.getConcCutOffs exception: " + localThrowable.getMessage());
      throw new CashConException(38515, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i == 16020) {
      return new CutOffTimes();
    }
    if (i == 0)
    {
      CutOffTimes localCutOffTimes = new CutOffTimes();
      for (int j = 0; j < arrayOfCCCompanyCutOffInfo.length; j++)
      {
        CutOffTime localCutOffTime = new CutOffTime();
        localCutOffTime.setCompCutOffId(arrayOfCCCompanyCutOffInfo[j].getCompCutOffId());
        localCutOffTime.setCutOffId(arrayOfCCCompanyCutOffInfo[j].getCutOffId());
        localCutOffTime.setCompId(arrayOfCCCompanyCutOffInfo[j].getCompId());
        a(localCutOffTime);
        localCutOffTimes.add(localCutOffTime);
      }
      paramCashConCompany.setConcCutOffs(localCutOffTimes);
      return localCutOffTimes;
    }
    System.out.println("BPW error:" + i);
    System.out.println("msg:" + str);
    throw new CashConException(38515, str);
  }
  
  public void deleteConcCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException
  {
    int i = 16002;
    String str = "";
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    try
    {
      CCCompanyCutOffInfo localCCCompanyCutOffInfo = new CCCompanyCutOffInfo();
      localCCCompanyCutOffInfo.setCompCutOffId(paramCutOffTime.getCompCutOffId());
      localCCCompanyCutOffInfo.setCutOffId(paramCutOffTime.getCutOffId());
      localCCCompanyCutOffInfo.setTransactionType("CONCENTRATION");
      localCCCompanyCutOffInfo.setCompId(paramCashConCompany.getBPWID());
      populateOBOAgentInfo(paramSecureUser, localCCCompanyCutOffInfo);
      localCCCompanyCutOffInfo = localBPWServices.cancelCCCompanyCutOff(localCCCompanyCutOffInfo);
      if (localCCCompanyCutOffInfo != null)
      {
        i = localCCCompanyCutOffInfo.getStatusCode();
        str = localCCCompanyCutOffInfo.getStatusMsg();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.deleteConcCutOff exception: " + localThrowable.getMessage());
      throw new CashConException(38517, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i != 0)
    {
      System.out.println("BPW error:" + i);
      System.out.println("msg:" + str);
      CSILException localCSILException = new CSILException("", 0, str);
      throw new CashConException(38517, str, localCSILException);
    }
  }
  
  public void addDisbCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    int i = 16002;
    String str = "";
    try
    {
      CCCompanyCutOffInfo localCCCompanyCutOffInfo = new CCCompanyCutOffInfo();
      localCCCompanyCutOffInfo.setCompId(paramCashConCompany.getBPWID());
      localCCCompanyCutOffInfo.setTransactionType("DISBURSEMENT");
      localCCCompanyCutOffInfo.setCutOffId(paramCutOffTime.getCutOffId());
      populateOBOAgentInfo(paramSecureUser, localCCCompanyCutOffInfo);
      localCCCompanyCutOffInfo = localBPWServices.addCCCompanyCutOff(localCCCompanyCutOffInfo);
      if (localCCCompanyCutOffInfo != null)
      {
        i = localCCCompanyCutOffInfo.getStatusCode();
        str = localCCCompanyCutOffInfo.getStatusMsg();
      }
      if (i == 0) {
        paramCutOffTime.setCompCutOffId(localCCCompanyCutOffInfo.getCompCutOffId());
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.addDisbCutOff exception: " + localThrowable.getMessage());
      throw new CashConException(38524, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i != 0)
    {
      System.out.println("BPW error:" + i);
      System.out.println("msg:" + str);
      CSILException localCSILException = new CSILException("", 0, str);
      throw new CashConException(38524, str, localCSILException);
    }
  }
  
  public CutOffTimes getDisbCutOffs(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    int i = 16002;
    String str = "";
    CCCompanyCutOffInfo[] arrayOfCCCompanyCutOffInfo = null;
    try
    {
      CCCompanyCutOffInfoList localCCCompanyCutOffInfoList = new CCCompanyCutOffInfoList();
      localCCCompanyCutOffInfoList.setCustomerId(paramCashConCompany.getCustID());
      localCCCompanyCutOffInfoList.setCompId(paramCashConCompany.getBPWID());
      localCCCompanyCutOffInfoList.setTransactionType("DISBURSEMENT");
      localCCCompanyCutOffInfoList = localBPWServices.getCCCompanyCutOffList(localCCCompanyCutOffInfoList);
      if (localCCCompanyCutOffInfoList != null)
      {
        i = localCCCompanyCutOffInfoList.getStatusCode();
        str = localCCCompanyCutOffInfoList.getStatusMsg();
        arrayOfCCCompanyCutOffInfo = localCCCompanyCutOffInfoList.getCCCompanyCutOffInfoList();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.getDisbCutOffs exception: " + localThrowable.getMessage());
      throw new CashConException(38523, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (i == 16020) {
      return new CutOffTimes();
    }
    if (i == 0)
    {
      CutOffTimes localCutOffTimes = new CutOffTimes();
      for (int j = 0; j < arrayOfCCCompanyCutOffInfo.length; j++)
      {
        CutOffTime localCutOffTime = new CutOffTime();
        localCutOffTime.setCompCutOffId(arrayOfCCCompanyCutOffInfo[j].getCompCutOffId());
        localCutOffTime.setCutOffId(arrayOfCCCompanyCutOffInfo[j].getCutOffId());
        localCutOffTime.setCompId(arrayOfCCCompanyCutOffInfo[j].getCompId());
        a(localCutOffTime);
        localCutOffTimes.add(localCutOffTime);
      }
      paramCashConCompany.setDisbCutOffs(localCutOffTimes);
      return localCutOffTimes;
    }
    System.out.println("BPW error:" + i);
    System.out.println("msg:" + str);
    throw new CashConException(38523, str);
  }
  
  public void deleteDisbCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    try
    {
      CCCompanyCutOffInfo localCCCompanyCutOffInfo = new CCCompanyCutOffInfo();
      localCCCompanyCutOffInfo.setCompCutOffId(paramCutOffTime.getCompCutOffId());
      localCCCompanyCutOffInfo.setCutOffId(paramCutOffTime.getCutOffId());
      localCCCompanyCutOffInfo.setTransactionType("DISBURSEMENT");
      localCCCompanyCutOffInfo.setCompId(paramCashConCompany.getBPWID());
      populateOBOAgentInfo(paramSecureUser, localCCCompanyCutOffInfo);
      localCCCompanyCutOffInfo = localBPWServices.cancelCCCompanyCutOff(localCCCompanyCutOffInfo);
      if (localCCCompanyCutOffInfo != null)
      {
        int i = localCCCompanyCutOffInfo.getStatusCode();
        if (i != 0)
        {
          CSILException localCSILException = new CSILException("", 0, localCCCompanyCutOffInfo.getStatusMsg());
          throw new CashConException(38525, localCCCompanyCutOffInfo.getStatusMsg(), localCSILException);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.deleteDisbCutOff exception: " + localThrowable.getMessage());
      throw new CashConException(38525, localThrowable);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
  }
  
  private CutOffTime a(CutOffTime paramCutOffTime)
    throws CashConException
  {
    int i = -1;
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler();
    if (localIBPWAdmin == null) {
      throw new CashConException(25018);
    }
    CutOffInfo localCutOffInfo = new CutOffInfo();
    localCutOffInfo.setCutOffId(paramCutOffTime.getCutOffId());
    try
    {
      localCutOffInfo = localIBPWAdmin.getCutOff(localCutOffInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new CashConException(38527);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
    i = localCutOffInfo.getStatusCode();
    if (i == 16020) {
      return paramCutOffTime;
    }
    if (i != 0)
    {
      DebugLog.log(Level.INFO, "*** BPW Get CutOffTimes failed!");
      DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + i);
      DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localCutOffInfo.getStatusMsg());
      throw new CashConException(38527);
    }
    paramCutOffTime.setCutOffInfo(localCutOffInfo);
    return paramCutOffTime;
  }
  
  public Locations getLocationsByAccount(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    Locations localLocations = new Locations(paramSecureUser.getLocale());
    try
    {
      CCLocationInfoList localCCLocationInfoList = new CCLocationInfoList();
      localCCLocationInfoList.setFIId(paramString1);
      localCCLocationInfoList.setCustomerId(paramString2);
      localCCLocationInfoList.setCompId(paramString3);
      if (paramBoolean) {
        localCCLocationInfoList.setDisburseAcctId(paramString4);
      } else {
        localCCLocationInfoList.setConcentrateAcctId(paramString4);
      }
      localCCLocationInfoList = localBPWServices.getCCLocationList(localCCLocationInfoList);
      if (localCCLocationInfoList != null)
      {
        int i = localCCLocationInfoList.getStatusCode();
        if (i != 16020)
        {
          if (i != 0)
          {
            localObject1 = new CSILException("", 0, localCCLocationInfoList.getStatusMsg());
            throw new CashConException(mapError(i), localCCLocationInfoList.getStatusMsg(), (Throwable)localObject1);
          }
          Object localObject1 = localCCLocationInfoList.getCCLocationInfoList();
          for (int j = 0; j < localObject1.length; j++) {
            localLocations.add(BeansConverter.bpwToEfsBean(localObject1[j], paramHashMap));
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.getLocationsByAccount: " + localThrowable.getMessage());
      String str = localThrowable.toString();
      if ((str != null) && (str.indexOf("Server is not running!") > 0)) {
        throw new CashConException(25018);
      }
      if ((localThrowable instanceof CashConException)) {
        throw ((CashConException)localThrowable);
      }
      throw new CashConException(38501, localThrowable);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return localLocations;
  }
  
  public int getNumberOfLocations(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    int i = 0;
    try
    {
      CCLocationInfoList localCCLocationInfoList = new CCLocationInfoList();
      localCCLocationInfoList.setFIId(paramString1);
      localCCLocationInfoList.setCustomerId(paramString2);
      localCCLocationInfoList.setCompId(paramString3);
      localCCLocationInfoList = localBPWServices.getCCLocationList(localCCLocationInfoList);
      if (localCCLocationInfoList != null)
      {
        int j = localCCLocationInfoList.getStatusCode();
        if (j == 16020)
        {
          i = 0;
        }
        else
        {
          if (j != 0)
          {
            localObject1 = new CSILException("", 0, localCCLocationInfoList.getStatusMsg());
            throw new CashConException(mapError(j), localCCLocationInfoList.getStatusMsg(), (Throwable)localObject1);
          }
          Object localObject1 = localCCLocationInfoList.getCCLocationInfoList();
          i = localObject1.length;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.getNumberOfLocations exception: " + localThrowable.getMessage());
      String str = localThrowable.toString();
      if ((str != null) && (str.indexOf("Server is not running!") > 0)) {
        throw new CashConException(25018);
      }
      if ((localThrowable instanceof CashConException)) {
        throw ((CashConException)localThrowable);
      }
      throw new CashConException(38501, localThrowable);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return i;
  }
  
  public LocationSearchResults getLocations(SecureUser paramSecureUser, LocationSearchCriteria paramLocationSearchCriteria, HashMap paramHashMap)
    throws CashConException
  {
    try
    {
      return LocationAdapter.getLocations(paramLocationSearchCriteria, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throw new CashConException(38500, localProfileException.getMessage(), localProfileException);
    }
  }
  
  public Location getLocation(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CashConException
  {
    Location localLocation = null;
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      CCLocationInfo localCCLocationInfo = new CCLocationInfo();
      localCCLocationInfo.setLocationId(paramString);
      localCCLocationInfo = localBPWServices.getCCLocation(localCCLocationInfo);
      if (localCCLocationInfo != null)
      {
        int i = localCCLocationInfo.getStatusCode();
        if (i != 0)
        {
          localObject2 = new CSILException("", 0, localCCLocationInfo.getStatusMsg());
          throw new CashConException(mapError(i), localCCLocationInfo.getStatusMsg(), (Throwable)localObject2);
        }
        localLocation = (Location)BeansConverter.bpwToEfsBean(localCCLocationInfo, paramHashMap);
      }
      localObject1 = new LocationSearchCriteria();
      ((LocationSearchCriteria)localObject1).setLocationName(localLocation.getLocationName());
      ((LocationSearchCriteria)localObject1).setLocationID(localLocation.getLocationID());
      Object localObject2 = LocationAdapter.getLocations((LocationSearchCriteria)localObject1, paramHashMap);
      Iterator localIterator = ((LocationSearchResults)localObject2).iterator();
      while (localIterator.hasNext())
      {
        LocationSearchResult localLocationSearchResult = (LocationSearchResult)localIterator.next();
        if (localLocationSearchResult.getLocationBPWID().equals(localLocation.getLocationBPWID()))
        {
          localLocation.setDivisionID(localLocationSearchResult.getDivisionID());
          break;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.getLocation exception: " + localThrowable.getMessage());
      Object localObject1 = localThrowable.toString();
      if ((localObject1 != null) && (((String)localObject1).indexOf("Server is not running!") > 0)) {
        throw new CashConException(25018);
      }
      if ((localThrowable instanceof CashConException)) {
        throw ((CashConException)localThrowable);
      }
      throw new CashConException(38501, localThrowable);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return localLocation;
  }
  
  public void addLocation(SecureUser paramSecureUser, Location paramLocation, HashMap paramHashMap)
    throws CashConException
  {
    String str = "CashConService.addLocation";
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      LocationSearchCriteria localLocationSearchCriteria = new LocationSearchCriteria();
      localLocationSearchCriteria.setDivisionID(paramLocation.getDivisionID());
      localObject1 = LocationAdapter.getLocations(localLocationSearchCriteria, paramHashMap);
      Iterator localIterator = ((LocationSearchResults)localObject1).iterator();
      while (localIterator.hasNext())
      {
        localObject2 = (LocationSearchResult)localIterator.next();
        if (((LocationSearchResult)localObject2).getLocationName().equalsIgnoreCase(paramLocation.getLocationName())) {
          throw new CashConException(38502, "CashConService.addLocation: Duplicate location " + paramLocation.getLocationName());
        }
        if (((LocationSearchResult)localObject2).getLocationID().equalsIgnoreCase(paramLocation.getLocationID())) {
          throw new CashConException(38530, "CashConService.addLocation: Duplicate location ID " + paramLocation.getLocationID());
        }
      }
      Object localObject2 = (CCLocationInfo)BeansConverter.efsToBpwBean(paramLocation, paramHashMap);
      populateOBOAgentInfo(paramSecureUser, (BPWInfoBase)localObject2);
      localObject2 = localBPWServices.addCCLocation((CCLocationInfo)localObject2);
      if (localObject2 != null)
      {
        int i = ((CCLocationInfo)localObject2).getStatusCode();
        if (i != 0)
        {
          CSILException localCSILException = new CSILException("", 0, ((CCLocationInfo)localObject2).getStatusMsg());
          throw new CashConException(mapError(i), ((CCLocationInfo)localObject2).getStatusMsg(), localCSILException);
        }
      }
      try
      {
        paramLocation.setLocationBPWID(((CCLocationInfo)localObject2).getLocationId());
        LocationAdapter.addLocation(paramLocation, paramHashMap);
      }
      catch (Throwable localThrowable2)
      {
        try
        {
          populateOBOAgentInfo(paramSecureUser, (BPWInfoBase)localObject2);
          localObject2 = localBPWServices.cancelCCLocation((CCLocationInfo)localObject2);
        }
        catch (Throwable localThrowable3) {}
        throw localThrowable2;
      }
    }
    catch (Throwable localThrowable1)
    {
      DebugLog.log("CashConService.addLocation exception: " + localThrowable1.getMessage());
      Object localObject1 = localThrowable1.toString();
      if ((localObject1 != null) && (((String)localObject1).indexOf("Server is not running!") > 0)) {
        throw new CashConException(25018);
      }
      if ((localThrowable1 instanceof CashConException)) {
        throw ((CashConException)localThrowable1);
      }
      throw new CashConException(38503, localThrowable1);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  public void deleteLocation(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CashConException
  {
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      CCLocationInfo localCCLocationInfo = new CCLocationInfo();
      localCCLocationInfo.setLocationId(paramString);
      populateOBOAgentInfo(paramSecureUser, localCCLocationInfo);
      localCCLocationInfo = localBPWServices.cancelCCLocation(localCCLocationInfo);
      if (localCCLocationInfo != null)
      {
        int i = localCCLocationInfo.getStatusCode();
        if (i != 0)
        {
          CSILException localCSILException = new CSILException("", 0, localCCLocationInfo.getStatusMsg());
          throw new CashConException(mapError(i), localCCLocationInfo.getStatusMsg(), localCSILException);
        }
      }
      LocationAdapter.deleteLocation(paramString, paramHashMap);
      boolean bool = EntitlementsUtil.removeEntitlementsAndLimitsForObjectUnsafe(paramSecureUser.getBusinessID(), "Location", paramString);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.deleteLocation exception: " + localThrowable.getMessage());
      String str = localThrowable.toString();
      if ((str != null) && (str.indexOf("Server is not running!") > 0)) {
        throw new CashConException(25018);
      }
      if ((localThrowable instanceof CashConException)) {
        throw ((CashConException)localThrowable);
      }
      throw new CashConException(38504, localThrowable);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  public void modifyLocation(SecureUser paramSecureUser, Location paramLocation, HashMap paramHashMap)
    throws CashConException
  {
    String str = "CashConService.modifyLocation";
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      LocationSearchCriteria localLocationSearchCriteria = new LocationSearchCriteria();
      localLocationSearchCriteria.setDivisionID(paramLocation.getDivisionID());
      localObject1 = LocationAdapter.getLocations(localLocationSearchCriteria, paramHashMap);
      Iterator localIterator = ((LocationSearchResults)localObject1).iterator();
      while (localIterator.hasNext())
      {
        localObject2 = (LocationSearchResult)localIterator.next();
        if (!((LocationSearchResult)localObject2).getLocationBPWID().equalsIgnoreCase(paramLocation.getLocationBPWID()))
        {
          if (((LocationSearchResult)localObject2).getLocationName().equalsIgnoreCase(paramLocation.getLocationName())) {
            throw new CashConException(38502, "CashConService.modifyLocation: Duplicate location " + paramLocation.getLocationName());
          }
          if (((LocationSearchResult)localObject2).getLocationID().equalsIgnoreCase(paramLocation.getLocationID())) {
            throw new CashConException(38530, "CashConService.modifyLocation: Duplicate location ID " + paramLocation.getLocationID());
          }
        }
      }
      Object localObject2 = (CCLocationInfo)BeansConverter.efsToBpwBean(paramLocation, paramHashMap);
      populateOBOAgentInfo(paramSecureUser, (BPWInfoBase)localObject2);
      localObject2 = localBPWServices.modCCLocation((CCLocationInfo)localObject2);
      if (localObject2 != null)
      {
        int i = ((CCLocationInfo)localObject2).getStatusCode();
        if (i != 0)
        {
          CSILException localCSILException = new CSILException("", 0, ((CCLocationInfo)localObject2).getStatusMsg());
          throw new CashConException(mapError(i), ((CCLocationInfo)localObject2).getStatusMsg(), localCSILException);
        }
      }
      LocationAdapter.modifyLocation(paramLocation, paramHashMap);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("CashConService.modifyLocation exception: " + localThrowable.getMessage());
      Object localObject1 = localThrowable.toString();
      if ((localObject1 != null) && (((String)localObject1).indexOf("Server is not running!") > 0)) {
        throw new CashConException(25018);
      }
      if ((localThrowable instanceof CashConException)) {
        throw ((CashConException)localThrowable);
      }
      throw new CashConException(38505, localThrowable);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  public int getPendingCashConCount(SecureUser paramSecureUser, String paramString1, String paramString2, String[] paramArrayOfString, HashMap paramHashMap)
    throws CashConException
  {
    String str1 = "CashConService.getPendingCashConCount";
    int i = 0;
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CashConException(25018);
    }
    try
    {
      CCEntryHistInfo localCCEntryHistInfo = new CCEntryHistInfo();
      localCCEntryHistInfo.setFIId(paramString1);
      localCCEntryHistInfo.setCompId(paramString2);
      localCCEntryHistInfo.setTransactionTypeList(paramArrayOfString);
      localCCEntryHistInfo.setStatusList(new String[] { "WILLPROCESSON", "APPROVAL_PENDING" });
      localCCEntryHistInfo = localBPWServices.getCCEntryHist(localCCEntryHistInfo);
      if (localCCEntryHistInfo != null)
      {
        int j = localCCEntryHistInfo.getStatusCode();
        if (j == 0) {
          i = localCCEntryHistInfo.getCCEntryInfoList().length;
        } else if (j == 17020) {
          i = 0;
        } else {
          throw new CashConException(mapError(j), localCCEntryHistInfo.getStatusMsg());
        }
      }
    }
    catch (CashConException localCashConException)
    {
      DebugLog.log("CashConService.getPendingCashConCount exception: " + localCashConException.getMessage());
      throw localCashConException;
    }
    catch (FFSException localFFSException)
    {
      DebugLog.log("CashConService.getPendingCashConCount exception: " + localFFSException.getMessage());
      throw new CashConException(38528, localFFSException);
    }
    catch (RemoteException localRemoteException)
    {
      String str2 = localRemoteException.toString();
      DebugLog.log("CashConService.getPendingCashConCount exception: " + localRemoteException.getMessage());
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CashConException(25018);
      }
      throw new CashConException(38528, localRemoteException);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return i;
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
        CashConService.this.c0 = paramString2;
      } else if (paramString1.equals("BPWAdminCallBackJNDIName")) {
        CashConService.this.c4 = paramString2;
      } else {
        super.attribute(paramString1, paramString2, paramBoolean);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.cashcon.CashConService
 * JD-Core Version:    0.7.0.1
 */