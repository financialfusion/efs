package com.ffusion.services;

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
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public abstract interface ACH
{
  public static final int ERROR_INVALID_SIGNON = 16000;
  public static final int ERROR_NO_DATABASE_CONNECTION = 16001;
  public static final int ERROR_UNABLE_TO_COMPLETE_REQUEST = 16002;
  public static final int ERROR_INVALID_REQUEST = 16003;
  public static final int ERROR_REMOTE_EXCEPTION = 16004;
  public static final int ERROR_EJB_NOT_FOUND = 16005;
  public static final int ERROR_FEDTAXENTRYNOTFOUND = 16100;
  public static final int ERROR_INVALID_ROUTING_NUMBER = 16110;
  public static final int ERROR_NO_ACH_ENTRIES_TO_ADD = 16111;
  public static final int ERROR_INVALID_ACH_GROUP_NAME = 16112;
  public static final int ERROR_INVALID_ACH_BATCH_NAME = 16113;
  public static final int ERROR_INVALID_ACH = 16114;
  public static final int ERROR_INIT_FILE_NOT_FOUND = 16115;
  public static final int ERROR_INVALID_INIT_FILE = 16116;
  public static final int ERROR_COMPANY_ALREADY_EXISTS = 16117;
  public static final int ERROR_COMPANY_NOT_SET_UP = 16118;
  
  public abstract int initialize(String paramString);
  
  public abstract ACHBatch addACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatch modifyACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch1, ACHBatch paramACHBatch2, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void deleteACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatch addRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatch modifyRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch1, ACHBatch paramACHBatch2, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void deleteRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatches getACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatches getRecACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatch getACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatch getRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatches getPagedPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatches getNextPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatches getPreviousPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatches getPagedCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatches getNextCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatches getPreviousCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHPayees getACHPayees(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHPayee addACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void deleteACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHPayee modifyACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatches getAllBatchesWithPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void deletePayeeFromBatch(SecureUser paramSecureUser, ACHPayee paramACHPayee, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void uploadACHFile(SecureUser paramSecureUser, StringBuffer paramStringBuffer, HashMap paramHashMap)
    throws CSILException;
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, String paramString, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException;
  
  public abstract String[] getStatesWithTaxForms(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException;
  
  public abstract TaxForms getTaxForms(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract TaxForm getTaxForm(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHCompany addACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHCompanies getACHCompanies(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void modifyACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void deleteACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHCompanySummaries getACHCompanySummaries(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void addOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHOffsetAccounts getOffsetAccounts(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void modifyOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void deleteOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException;
  
  public abstract AffiliateBank addAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException;
  
  public abstract AffiliateBank modifyAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException;
  
  public abstract AffiliateBank deleteAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException;
  
  public abstract AffiliateBank getAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException;
  
  public abstract AffiliateBanks getAffiliateBanks(SecureUser paramSecureUser, AffiliateBanks paramAffiliateBanks, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void addCustomer(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ACH
 * JD-Core Version:    0.7.0.1
 */