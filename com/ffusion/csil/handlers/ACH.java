package com.ffusion.csil.handlers;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHCompanySummaries;
import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.beans.ach.TaxForm;
import com.ffusion.beans.ach.TaxForms;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.ACH5;
import com.ffusion.services.TaxFormService;
import com.ffusion.services.banksim2.ACHService;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class ACH
  extends Initialize
{
  private static final String a7k = "ACH";
  private static ACH5 a7j = null;
  private static final String a7h = "TaxForm";
  private static TaxFormService a7g = null;
  private static String a7f;
  private static String a7i;
  private static String a7e;
  private static String a7d;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.initialize";
    debug(str);
    a7d = HandlerUtil.getGlobalPageSize(paramHashMap);
    HashMap localHashMap1 = HandlerUtil.getServiceSettings(paramHashMap, "ACH", str, 20107);
    a7j = (ACH5)HandlerUtil.instantiateService(localHashMap1, str, 20107);
    HashMap localHashMap2 = HandlerUtil.getServiceSettings(paramHashMap, "TaxForm", str, 20107);
    a7g = (TaxFormService)HandlerUtil.instantiateService(localHashMap2, str, 20107);
    try
    {
      a7j.initialize("");
      a7g.initialize();
      a7f = HandlerUtil.getGlobalACHCompanyDisplaySize(paramHashMap);
      a7i = HandlerUtil.getGlobalACHPayeeDisplaySize(paramHashMap);
      a7e = HandlerUtil.getGlobalACHCompanyMaxResultSize(paramHashMap);
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
    if (paramString.equals("TaxForm")) {
      return a7g;
    }
    return a7j;
  }
  
  public static String getDisplayCount()
  {
    return a7f;
  }
  
  public static String getDisplayPayeeCount()
  {
    return a7i;
  }
  
  public static String getMaxResultCount()
  {
    return a7e;
  }
  
  public static ACHBatch addACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.addACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
  }
  
  public static ACHBatch modifyACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch1, ACHBatch paramACHBatch2, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.modifyACHBatch(paramSecureUser, paramACHBatch1, paramACHBatch2, paramHashMap);
  }
  
  public static void deleteACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    a7j.deleteACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
  }
  
  public static ACHBatch addRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.addRecACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
  }
  
  public static ACHBatch modifyRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch1, ACHBatch paramACHBatch2, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.modifyRecACHBatch(paramSecureUser, paramACHBatch1, paramACHBatch2, paramHashMap);
  }
  
  public static void deleteRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    a7j.deleteRecACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
  }
  
  public static ACHBatches getACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getACHBatches(paramSecureUser, paramString1, paramString2, paramHashMap);
  }
  
  public static ACHBatches getRecACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getRecACHBatches(paramSecureUser, paramString1, paramString2, paramHashMap);
  }
  
  public static ACHBatch getACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
  }
  
  public static ACHBatch getRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getRecACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
  }
  
  public static ACHBatches getPagedPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    return a7j.getPagedPendingACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
  }
  
  public static ACHBatches getNextPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    return a7j.getNextPendingACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
  }
  
  public static ACHBatches getPreviousPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    return a7j.getPreviousPendingACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
  }
  
  public static ACHBatches getPagedCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    return a7j.getPagedCompletedACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
  }
  
  public static ACHBatches getNextCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    return a7j.getNextCompletedACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
  }
  
  public static ACHBatches getPreviousCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    return a7j.getPreviousCompletedACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
  }
  
  public static ACHBatches getPagedApprovalACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    return a7j.getPagedApprovalACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
  }
  
  public static ACHBatches getNextApprovalACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    return a7j.getNextApprovalACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
  }
  
  public static ACHBatches getPreviousApprovalACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    checkExtraParams(paramHashMap);
    return a7j.getPreviousApprovalACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
  }
  
  public static ACHPayees getACHPayees(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getACHPayees(paramSecureUser, paramString, paramHashMap);
  }
  
  public static ACHPayees getACHPayees(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getACHPayees(paramSecureUser, paramString1, paramString2, paramHashMap);
  }
  
  public static ACHPayee addACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.addACHPayee(paramSecureUser, paramACHPayee, paramHashMap);
  }
  
  public static void deleteACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {
    a7j.deleteACHPayee(paramSecureUser, paramACHPayee, paramHashMap);
  }
  
  public static ACHPayee modifyACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.modifyACHPayee(paramSecureUser, paramACHPayee, paramHashMap);
  }
  
  public static ACHBatches getAllBatchesWithPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getAllBatchesWithPayee(paramSecureUser, paramACHPayee, paramHashMap);
  }
  
  public static void deletePayeeFromBatch(SecureUser paramSecureUser, ACHPayee paramACHPayee, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    a7j.deletePayeeFromBatch(paramSecureUser, paramACHPayee, paramACHBatch, paramHashMap);
  }
  
  public static void uploadACHFile(SecureUser paramSecureUser, StringBuffer paramStringBuffer, HashMap paramHashMap)
    throws CSILException
  {
    a7j.uploadACHFile(paramSecureUser, paramStringBuffer, paramHashMap);
  }
  
  public static TaxForms getUserTaxForms(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a7g.getTaxForms(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, mapError(localProfileException.code), localProfileException);
      DebugLog.throwing("ACH.getTaxForms", localCSILException);
      throw localCSILException;
    }
  }
  
  public static TaxForm addUserTaxForm(SecureUser paramSecureUser, TaxForm paramTaxForm, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a7g.addTaxForm(paramSecureUser, paramTaxForm, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, mapError(localProfileException.code), localProfileException);
      DebugLog.throwing("ACH.addTaxForm", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteUserTaxForm(SecureUser paramSecureUser, TaxForm paramTaxForm, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a7g.deleteTaxForm(paramSecureUser, paramTaxForm, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(-1009, mapError(localProfileException.code), localProfileException);
      DebugLog.throwing("ACH.deleteTaxForm", localCSILException);
      throw localCSILException;
    }
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, String paramString, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getReportData(paramSecureUser, paramString, paramReportCriteria, paramHashMap);
  }
  
  public static String[] getStatesWithTaxForms(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getStatesWithTaxForms(paramSecureUser, paramHashMap);
  }
  
  public static HashMap getStateNamesWithTaxForms(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getStateNamesWithTaxForms(paramSecureUser, paramHashMap);
  }
  
  public static TaxForms getTaxForms(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getTaxForms(paramSecureUser, paramInt, paramString, paramHashMap);
  }
  
  public static TaxForm getTaxForm(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getTaxForm(paramSecureUser, paramString, paramHashMap);
  }
  
  public static ACHCompany addACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.addACHCompany(paramSecureUser, paramACHCompany, paramString, paramHashMap);
  }
  
  public static ACHCompanies getACHCompanies(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getACHCompanies(paramSecureUser, paramString1, paramString2, paramHashMap);
  }
  
  public static void modifyACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    a7j.modifyACHCompany(paramSecureUser, paramACHCompany, paramString, paramHashMap);
  }
  
  public static void deleteACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    a7j.deleteACHCompany(paramSecureUser, paramACHCompany, paramString, paramHashMap);
  }
  
  public static ACHCompanySummaries getACHCompanySummaries(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getACHCompanySummaries(paramSecureUser, paramArrayOfString, paramHashMap);
  }
  
  public static void addOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    a7j.addOffsetAccount(paramSecureUser, paramACHOffsetAccount, paramACHCompany, paramHashMap);
  }
  
  public static ACHOffsetAccounts getOffsetAccounts(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getOffsetAccounts(paramSecureUser, paramACHOffsetAccount, paramACHCompany, paramHashMap);
  }
  
  public static void modifyOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    a7j.modifyOffsetAccount(paramSecureUser, paramACHOffsetAccount, paramACHCompany, paramHashMap);
  }
  
  public static void deleteOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    a7j.deleteOffsetAccount(paramSecureUser, paramACHOffsetAccount, paramACHCompany, paramHashMap);
  }
  
  public static AffiliateBank addAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.addAffiliateBank(paramSecureUser, paramAffiliateBank, paramHashMap);
  }
  
  public static AffiliateBank modifyAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.modifyAffiliateBank(paramSecureUser, paramAffiliateBank, paramHashMap);
  }
  
  public static AffiliateBank deleteAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.deleteAffiliateBank(paramSecureUser, paramAffiliateBank, paramHashMap);
  }
  
  public static AffiliateBank getAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getAffiliateBank(paramSecureUser, paramAffiliateBank, paramHashMap);
  }
  
  public static AffiliateBanks getAffiliateBanks(SecureUser paramSecureUser, AffiliateBanks paramAffiliateBanks, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getAffiliateBanks(paramSecureUser, paramAffiliateBanks, paramHashMap);
  }
  
  public static boolean isBusinessRegisteredWithBPW(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.isBusinessRegisteredWithBPW(paramSecureUser, paramString, paramHashMap);
  }
  
  public static boolean getBusinessRegistrationBPWInfo(SecureUser paramSecureUser, String paramString, Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getBusinessRegistrationBPWInfo(paramSecureUser, paramString, paramBusiness, paramHashMap);
  }
  
  public static void addCustomer(SecureUser paramSecureUser, String paramString, Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    a7j.addCustomer(paramSecureUser, paramString, paramBusiness, paramHashMap);
  }
  
  public static void modifyCustomer(SecureUser paramSecureUser, String paramString, Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    a7j.modifyCustomer(paramSecureUser, paramString, paramBusiness, paramHashMap);
  }
  
  public static void deactivateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    a7j.deactivateCustomers(paramSecureUser, paramArrayOfString, paramHashMap);
  }
  
  public static void activateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    a7j.activateCustomers(paramSecureUser, paramArrayOfString, paramHashMap);
  }
  
  public static Date getSmartDate(SecureUser paramSecureUser, DateTime paramDateTime)
    throws CSILException
  {
    String str = "ACHHandler.getSmartDate";
    try
    {
      return a7j.getSmartDate(paramSecureUser, paramDateTime);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static int getReleaseBatchesCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACHHandler.getReleaseBatchesCount";
    try
    {
      return a7j.getReleaseACHBatchesCount(paramSecureUser, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ACHBatches getReleaseBatches(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACHHandler.getReleaseBatches";
    try
    {
      return a7j.getReleaseACHBatches(paramSecureUser, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void releaseBatches(SecureUser paramSecureUser, ACHBatches paramACHBatches, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACHHandler.releaseBatches";
    try
    {
      a7j.releaseACHBatches(paramSecureUser, paramACHBatches, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void processApprovalResult(SecureUser paramSecureUser, Object paramObject, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    a7j.processApprovalResult(paramSecureUser, paramObject, paramString, paramHashMap);
  }
  
  public static Object exportACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    if ((a7j instanceof ACHService)) {
      return ((ACHService)a7j).exportACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
    }
    return "ACH Batch Export code not written yet";
  }
  
  protected static void checkExtraParams(HashMap paramHashMap)
  {
    if (!paramHashMap.containsKey("PAGESIZE")) {
      paramHashMap.put("PAGESIZE", a7d);
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
    return a7j;
  }
  
  public static HashSet getEnabledSECCodes(SecureUser paramSecureUser, String paramString, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    return a7j.getEnabledSECCodes(paramSecureUser, paramString, paramInt, paramHashMap);
  }
  
  public static void setEnabledSECCodes(SecureUser paramSecureUser, String paramString, int paramInt, HashSet paramHashSet, HashMap paramHashMap)
    throws CSILException
  {
    a7j.setEnabledSECCodes(paramSecureUser, paramString, paramInt, paramHashSet, paramHashMap);
  }
  
  public static String getDefaultEffectiveDate(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACHHandler. getDefaultEffectiveDate(";
    try
    {
      return a7j.getDefaultEffectiveDate(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getDefaultACHFrequencies(SecureUser paramSecureUser)
    throws CSILException
  {
    String str = "ACHHandler.getDefaultACHFrequencies ";
    try
    {
      return a7j.getDefaultACHFrequencies(paramSecureUser);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ACHBatches getPagedACHBatchHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.getPagedACHBatchHistories";
    try
    {
      return a7j.getPagedACHBatchHistories(paramSecureUser, paramPagingContext, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.ACH
 * JD-Core Version:    0.7.0.1
 */