package com.ffusion.csil.handlers;

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
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.beans.cashcon.Locations;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.CashCon1;
import com.ffusion.services.cashcon.CashConException;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;

public class CashCon
  extends Initialize
{
  private static final String a6O = "Cash Concentration";
  private static com.ffusion.services.CashCon a6K = null;
  private static String a6N;
  private static String a6M;
  private static String a6L;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.initialize";
    debug(str);
    a6L = HandlerUtil.getGlobalPageSize(paramHashMap);
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Cash Concentration", str, 20107);
    a6K = (com.ffusion.services.CashCon)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      a6K.initialize("");
      a6N = HandlerUtil.getGlobalLocationDisplaySize(paramHashMap);
      a6M = HandlerUtil.getGlobalACHCompanyMaxResultSize(paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(20107, localException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService(String paramString)
  {
    return a6K;
  }
  
  public static String getDisplayCount()
  {
    return a6N;
  }
  
  public static String getMaxResultCount()
  {
    return a6M;
  }
  
  public static com.ffusion.beans.cashcon.CashCon addCashCon(SecureUser paramSecureUser, com.ffusion.beans.cashcon.CashCon paramCashCon, HashMap paramHashMap)
    throws CashConException
  {
    return a6K.addCashCon(paramSecureUser, paramCashCon, paramHashMap);
  }
  
  public static com.ffusion.beans.cashcon.CashCon modifyCashCon(SecureUser paramSecureUser, com.ffusion.beans.cashcon.CashCon paramCashCon1, com.ffusion.beans.cashcon.CashCon paramCashCon2, HashMap paramHashMap)
    throws CashConException
  {
    return a6K.modifyCashCon(paramSecureUser, paramCashCon1, paramCashCon2, paramHashMap);
  }
  
  public static void deleteCashCon(SecureUser paramSecureUser, com.ffusion.beans.cashcon.CashCon paramCashCon, HashMap paramHashMap)
    throws CashConException
  {
    a6K.deleteCashCon(paramSecureUser, paramCashCon, paramHashMap);
  }
  
  public static CashCons getPagedPendingCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    checkExtraParams(paramHashMap);
    return a6K.getPagedPendingCashCons(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public static CashCons getNextPendingCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    checkExtraParams(paramHashMap);
    return a6K.getNextPendingCashCons(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public static CashCons getPreviousPendingCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    checkExtraParams(paramHashMap);
    return a6K.getPreviousPendingCashCons(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public static CashCons getPagedCompletedCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    checkExtraParams(paramHashMap);
    return a6K.getPagedCompletedCashCons(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public static CashCons getNextCompletedCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    checkExtraParams(paramHashMap);
    return a6K.getNextCompletedCashCons(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public static CashCons getPreviousCompletedCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    checkExtraParams(paramHashMap);
    return a6K.getPreviousCompletedCashCons(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public static CashCons getPagedApprovalCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    checkExtraParams(paramHashMap);
    return a6K.getPagedApprovalCashCons(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public static CashCons getNextApprovalCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    checkExtraParams(paramHashMap);
    return a6K.getNextApprovalCashCons(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public static CashCons getPreviousApprovalCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException
  {
    checkExtraParams(paramHashMap);
    return a6K.getPreviousApprovalCashCons(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CashConException
  {
    return a6K.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
  }
  
  protected static void checkExtraParams(HashMap paramHashMap)
  {
    if (!paramHashMap.containsKey("PAGESIZE")) {
      paramHashMap.put("PAGESIZE", a6L);
    }
  }
  
  protected static int mapError(int paramInt)
  {
    switch (paramInt)
    {
    case 13: 
      return 19003;
    }
    return 16002;
  }
  
  public static Object getService()
  {
    return a6K;
  }
  
  public static CashConCompanies getCashConCompanies(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a6K.getCashConCompanies(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static CashConCompany addCashConCompany(SecureUser paramSecureUser, CashConCompany paramCashConCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a6K.addCashConCompany(paramSecureUser, paramCashConCompany, paramString, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static void modifyCashConCompany(SecureUser paramSecureUser, CashConCompany paramCashConCompany1, CashConCompany paramCashConCompany2, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6K.modifyCashConCompany(paramSecureUser, paramCashConCompany1, paramCashConCompany2, paramString, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static void deleteCashConCompany(SecureUser paramSecureUser, CashConCompany paramCashConCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6K.deleteCashConCompany(paramSecureUser, paramCashConCompany, paramString, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static void addConcAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6K.addConcAccount(paramSecureUser, paramCashConAccount, paramCashConCompany, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static CashConAccounts getConcAccounts(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a6K.getConcAccounts(paramSecureUser, paramCashConAccount, paramCashConCompany, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static void deleteConcAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6K.deleteConcAccount(paramSecureUser, paramCashConAccount, paramCashConCompany, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static void addDisbAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6K.addDisbAccount(paramSecureUser, paramCashConAccount, paramCashConCompany, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static CashConAccounts getDisbAccounts(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a6K.getDisbAccounts(paramSecureUser, paramCashConAccount, paramCashConCompany, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static void deleteDisbAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6K.deleteDisbAccount(paramSecureUser, paramCashConAccount, paramCashConCompany, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static void addConcCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6K.addConcCutOff(paramSecureUser, paramCutOffTime, paramCashConCompany, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static CutOffTimes getConcCutOffs(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a6K.getConcCutOffs(paramSecureUser, paramCutOffTime, paramCashConCompany, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static void deleteConcCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6K.deleteConcCutOff(paramSecureUser, paramCutOffTime, paramCashConCompany, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static void addDisbCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6K.addDisbCutOff(paramSecureUser, paramCutOffTime, paramCashConCompany, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static CutOffTimes getDisbCutOffs(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a6K.getDisbCutOffs(paramSecureUser, paramCutOffTime, paramCashConCompany, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static void deleteDisbCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6K.deleteDisbCutOff(paramSecureUser, paramCutOffTime, paramCashConCompany, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static int getNumberOfLocations(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a6K.getNumberOfLocations(paramSecureUser, paramString1, paramString2, paramString3, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(localCashConException.getErrorCode(), localCashConException);
    }
  }
  
  public static Locations getLocationsByAccount(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a6K.getLocationsByAccount(paramSecureUser, paramString1, paramString2, paramString3, paramString4, paramBoolean, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      throw new CSILException(mapError(localCashConException.getErrorCode()), localCashConException);
    }
  }
  
  public static LocationSearchResults getLocations(SecureUser paramSecureUser, LocationSearchCriteria paramLocationSearchCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashConHandler.getLocations";
    try
    {
      return a6K.getLocations(paramSecureUser, paramLocationSearchCriteria, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      CSILException localCSILException = new CSILException(localCashConException.getErrorCode(), localCashConException);
      DebugLog.throwing("CashConHandler.getLocations exception: ", localCSILException);
      throw localCSILException;
    }
  }
  
  public static Location getLocation(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashConHandler.getLocation";
    try
    {
      return a6K.getLocation(paramSecureUser, paramString, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      CSILException localCSILException = new CSILException(localCashConException.getErrorCode(), localCashConException);
      DebugLog.throwing("CashConHandler.getLocation exception: ", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void addLocation(SecureUser paramSecureUser, Location paramLocation, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashConHandler.addLocation";
    try
    {
      a6K.addLocation(paramSecureUser, paramLocation, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      CSILException localCSILException = new CSILException(localCashConException.getErrorCode(), localCashConException);
      DebugLog.throwing("CashConHandler.addLocation exception: ", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteLocation(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashConHandler.deleteLocation";
    try
    {
      a6K.deleteLocation(paramSecureUser, paramString, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      CSILException localCSILException = new CSILException(localCashConException.getErrorCode(), localCashConException);
      DebugLog.throwing("CashConHandler.deleteLocation exception: ", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void modifyLocation(SecureUser paramSecureUser, Location paramLocation, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashConHandler.modifyLocation";
    try
    {
      a6K.modifyLocation(paramSecureUser, paramLocation, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      CSILException localCSILException = new CSILException(localCashConException.getErrorCode(), localCashConException);
      DebugLog.throwing("CashConHandler.modifyLocation exception: ", localCSILException);
      throw localCSILException;
    }
  }
  
  public static int getPendingCashConCount(SecureUser paramSecureUser, String paramString1, String paramString2, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getPendingCashConCount";
    int i = 0;
    try
    {
      i = a6K.getPendingCashConCount(paramSecureUser, paramString1, paramString2, paramArrayOfString, paramHashMap);
    }
    catch (CashConException localCashConException)
    {
      CSILException localCSILException = new CSILException(localCashConException.getErrorCode(), localCashConException);
      DebugLog.throwing("CashCon.getPendingCashConCount exception: ", localCSILException);
      throw localCSILException;
    }
    return i;
  }
  
  public static CashCons getPagedCashConHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "CashCon.getPagedCashConHistories";
    try
    {
      if ((a6K instanceof CashCon1)) {
        return ((CashCon1)a6K).getPagedCashConHistories(paramSecureUser, paramPagingContext, paramHashMap);
      }
      return new CashCons();
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.CashCon
 * JD-Core Version:    0.7.0.1
 */