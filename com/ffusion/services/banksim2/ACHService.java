package com.ffusion.services.banksim2;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHAddenda;
import com.ffusion.beans.ach.ACHAddendas;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHCompanySummaries;
import com.ffusion.beans.ach.ACHCompanySummary;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.beans.ach.TaxForm;
import com.ffusion.beans.ach.TaxFormCache;
import com.ffusion.beans.ach.TaxForms;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.approvals.ApprovalsChainItem;
import com.ffusion.beans.approvals.ApprovalsGroup;
import com.ffusion.beans.approvals.ApprovalsGroups;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsStatus;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.cashcon.CashCon;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
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
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.util.BeansConverter;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Approvals;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.Reporting;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.ffs.bpw.ACHServices.ACHBPWServices;
import com.ffusion.ffs.bpw.ACHServices.ACHBPWServicesHome;
import com.ffusion.ffs.bpw.BPWServices.BPWServices;
import com.ffusion.ffs.bpw.BPWServices.BPWServicesHome;
import com.ffusion.ffs.bpw.enums.UserType;
import com.ffusion.ffs.bpw.interfaces.ACHAddendaInfo;
import com.ffusion.ffs.bpw.interfaces.ACHBatchHist;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyList;
import com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryList;
import com.ffusion.ffs.bpw.interfaces.ACHException;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.ACHFileInfo;
import com.ffusion.ffs.bpw.interfaces.ACHHistInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeList;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWInfoBase;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.SortCriterion;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.fileimporter.fileadapters.ACHAdapter;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeARCEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCCDAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCCDEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCIEAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCIEEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCTXAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCTXEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePOPEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePPDAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePPDEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeRCKEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeTELEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeWEBAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeWEBEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeXCKEntryDetailRecord;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.services.ACH5;
import com.ffusion.util.ContextPool;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.RoutingNumberUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.TrackingIDGenerator;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

public class ACHService
  extends Base
  implements ACH5
{
  protected int maxTransactionDays = 90;
  protected static final String ACH_CALLBACK_JNDI_NAME = "ACHCallBackJNDIName";
  protected static final String ACH_TRANSACTION_DAYS = "ACHTransactions";
  protected static final String BPW_CALLBACK_JNDI_NAME = "BPWCallBackJNDIName";
  protected static final String RECURRING_PREFIX = "rec_";
  protected static final int TRN_PENDING = 1;
  protected static final int TRN_COMPLETED = 2;
  protected static final int TRN_APPROVAL = 3;
  protected static final int TRN_ALL_STATUSES = 99;
  protected String ach_callback_JNDI_name = "ACHBPWServices";
  protected String bpwJNDIName = "BPWServices";
  protected int default_pagesize = 10;
  protected static TaxFormCache taxFormCache = null;
  protected int ACH_RECORD_LENGTH = 94;
  
  public int initialize(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = "banksim.xml";
    }
    int i = initialize(paramString, new a());
    taxFormCache = new TaxFormCache();
    taxFormCache.loadFromXMLFile("taxforms.xml");
    return i;
  }
  
  public void processOFXRequest() {}
  
  protected ACHBPWServices getACHHandler()
    throws CSILException
  {
    ACHBPWServices localACHBPWServices = null;
    ContextPool localContextPool = null;
    Context localContext = null;
    if ((this.provider_url_list.size() == 0) || (taxFormCache == null)) {
      throw new CSILException(29101);
    }
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
          DebugLog.log(Level.INFO, "##################### Getting ACH Handler ################");
          DebugLog.log(Level.INFO, "provider_url = " + str);
        }
        localContextPool = getContextPool(str);
        localContext = localContextPool.getContext();
        Object localObject1 = localContext.lookup(this.ach_callback_JNDI_name);
        localObject2 = (ACHBPWServicesHome)PortableRemoteObject.narrow(localObject1, ACHBPWServicesHome.class);
        localACHBPWServices = ((ACHBPWServicesHome)localObject2).create();
        localContextPool.returnContext(localContext);
        return localACHBPWServices;
      }
      catch (Throwable localThrowable)
      {
        Object localObject2;
        DebugLog.throwing("Couldn't get handler in BankSim service for " + str, localThrowable);
        if ((localContext != null) && (localContextPool != null)) {
          try
          {
            localContext = localContextPool.refreshContext(localContext);
            localObject2 = localContext.lookup(this.ach_callback_JNDI_name);
            ACHBPWServicesHome localACHBPWServicesHome = (ACHBPWServicesHome)PortableRemoteObject.narrow(localObject2, ACHBPWServicesHome.class);
            localACHBPWServices = localACHBPWServicesHome.create();
            localContextPool.returnContext(localContext);
            return localACHBPWServices;
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
    throw new CSILException(25018);
  }
  
  protected BPWServices getBPWHandler()
    throws CSILException
  {
    BPWServices localBPWServices = null;
    ContextPool localContextPool = null;
    Context localContext = null;
    if ((this.provider_url_list.size() == 0) || (taxFormCache == null)) {
      throw new CSILException(29101);
    }
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
        Object localObject1 = localContext.lookup(this.bpwJNDIName);
        localObject2 = (BPWServicesHome)PortableRemoteObject.narrow(localObject1, BPWServicesHome.class);
        localBPWServices = ((BPWServicesHome)localObject2).create();
        localContextPool.returnContext(localContext);
        return localBPWServices;
      }
      catch (Throwable localThrowable)
      {
        Object localObject2;
        DebugLog.throwing("Couldn't get handler in BankSim service for " + str, localThrowable);
        if ((localContext != null) && (localContextPool != null)) {
          try
          {
            localContext = localContextPool.refreshContext(localContext);
            localObject2 = localContext.lookup(this.bpwJNDIName);
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
    throw new CSILException(25018);
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
  
  protected static void removeACHHandler(ACHBPWServices paramACHBPWServices)
  {
    if (paramACHBPWServices != null) {
      try
      {
        if (debugService) {
          DebugLog.log(Level.INFO, "##################### Removing ACH Handler ################");
        }
        paramACHBPWServices.remove();
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("Ignoring error removing ACH handler", localThrowable);
      }
    }
  }
  
  public ACHBatch addACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    Object localObject1 = new ACHBatchInfo();
    RecACHBatchInfo localRecACHBatchInfo = null;
    int i = 0;
    int j = 0;
    String str1 = "";
    int k = 0;
    int m = 0;
    int n = 0;
    if ("REVERSAL".equals(paramACHBatch.getCoEntryDesc())) {
      n = 1;
    }
    String str2 = formatDate.format(paramACHBatch.getDateValue().getTime());
    if ((paramACHBatch.getFrequencyValue() != 0) && (paramACHBatch.getNumberPaymentsValue() > 1))
    {
      localRecACHBatchInfo = new RecACHBatchInfo();
      localObject1 = localRecACHBatchInfo;
      localRecACHBatchInfo.setPmtsCount(paramACHBatch.getNumberPaymentsValue());
      localRecACHBatchInfo.setFrequency(BeansConverter.getBPWFrequency(paramACHBatch.getFrequencyValue()));
      localRecACHBatchInfo.setStartDate(str2);
    }
    if ((paramHashMap != null) && (paramHashMap.get("Modify") != null))
    {
      j = 1;
      if (localRecACHBatchInfo != null) {
        ((ACHBatchInfo)localObject1).setBatchId(paramACHBatch.getRecID());
      } else {
        ((ACHBatchInfo)localObject1).setBatchId(paramACHBatch.getID());
      }
      ((ACHBatchInfo)localObject1).setFiId(paramSecureUser.getBPWFIID());
    }
    if (paramACHBatch.getCoDiscretionaryData() == null) {
      paramACHBatch.setCoDiscretionaryData("");
    }
    ((ACHBatchInfo)localObject1).setCompId(paramACHBatch.getCoID());
    ((ACHBatchInfo)localObject1).setCompName(paramACHBatch.getCoName());
    ((ACHBatchInfo)localObject1).setDueDate(str2);
    ((ACHBatchInfo)localObject1).setClassCode(paramACHBatch.getStandardEntryClassCode());
    paramACHBatch.setSubmittedBy(Integer.toString(paramSecureUser.getProfileID()));
    ((ACHBatchInfo)localObject1).setSubmittedBy(paramACHBatch.getSubmittedBy());
    if (paramACHBatch.getTrackingID() == null) {
      paramACHBatch.setTrackingID(TrackingIDGenerator.GetNextID());
    }
    ((ACHBatchInfo)localObject1).setLogId(paramACHBatch.getTrackingID());
    String str3 = formatDate.format(new DateTime().getTime());
    ((ACHBatchInfo)localObject1).setSubmitDate(str3);
    ((ACHBatchInfo)localObject1).setBatchCategory("ACH_BATCH_PAYMENT");
    ((ACHBatchInfo)localObject1).setLastModifier(Integer.toString(paramSecureUser.getProfileID()));
    ACHEntries localACHEntries = paramACHBatch.getACHEntries();
    TaxForm localTaxForm = paramACHBatch.getTaxForm();
    String str4 = null;
    String str5 = null;
    if (localTaxForm != null)
    {
      str4 = localTaxForm.getID();
      str5 = localTaxForm.getState();
      ((ACHBatchInfo)localObject1).setBatchCategory("ACH_BATCH_TAX");
      if (localTaxForm.getAddendaFormat().equals("DED")) {
        ((ACHBatchInfo)localObject1).setBatchCategory("ACH_BATCH_CHILD_SUPPORT");
      }
    }
    if (n != 0) {
      ((ACHBatchInfo)localObject1).setBatchCategory("ACH_BATCH_REVERSAL");
    }
    localACHEntries.setFilter("ACTIVEENTRY==TRUE");
    int i1 = localACHEntries.size();
    if (paramACHBatch.getBatchTypeValue() == 2) {
      i1++;
    } else if (paramACHBatch.getBatchTypeValue() == 3) {
      i1 *= 2;
    }
    if (i1 == 0) {
      return paramACHBatch;
    }
    Object localObject2 = new ACHRecordInfo[i1];
    Hashtable localHashtable = new Hashtable();
    localHashtable.put("NAME", paramACHBatch.getName());
    if (str4 != null) {
      localHashtable.put("TAXID", str4);
    }
    if (str5 != null) {
      localHashtable.put("TAXSTATE", str5);
    }
    if ((paramACHBatch.getDebitBatch() != null) && (paramACHBatch.getDebitBatch().length() > 0)) {
      localHashtable.put("CREDIT", paramACHBatch.getDebitBatch());
    }
    if ((paramACHBatch.getTemplateID() != null) && (paramACHBatch.getTemplateID().length() > 0)) {
      localHashtable.put("TEMPLATEID", paramACHBatch.getTemplateID());
    }
    if ((n != 0) && (paramACHBatch.getOriginalID() != null) && (paramACHBatch.getOriginalID().length() > 0)) {
      localHashtable.put("ORIGINALID", paramACHBatch.getOriginalID());
    }
    if ((paramACHBatch.getBatchScope() != null) && (paramACHBatch.getBatchScope().length() > 0)) {
      localHashtable.put("SCOPE", paramACHBatch.getBatchScope());
    }
    if (paramACHBatch.getCompanyID() != null) {
      localHashtable.put("COMPANYID", paramACHBatch.getCompanyID());
    }
    if (paramACHBatch.getTrackingID() != null)
    {
      localHashtable.put("TRACKINGID", paramACHBatch.getTrackingID());
      ((ACHBatchInfo)localObject1).setLogId(paramACHBatch.getTrackingID());
    }
    if (paramACHBatch.getBatchTypeValue() == 2) {
      ((ACHBatchInfo)localObject1).setBatchBalanceType("BatchBalancedBatch");
    } else if (paramACHBatch.getBatchTypeValue() == 3) {
      ((ACHBatchInfo)localObject1).setBatchBalanceType("EntryBalancedBatch");
    } else {
      ((ACHBatchInfo)localObject1).setBatchBalanceType("UnbalancedBatch");
    }
    if ((paramACHBatch.getHeaderCompName() != null) && (paramACHBatch.getHeaderCompName().length() > 0)) {
      ((ACHBatchInfo)localObject1).setHeaderCompName(paramACHBatch.getHeaderCompName());
    }
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    Object localObject4;
    try
    {
      Iterator localIterator = localACHEntries.iterator();
      BigDecimal localBigDecimal1 = new BigDecimal(0.0D);
      BigDecimal localBigDecimal2 = new BigDecimal(0.0D);
      BigDecimal localBigDecimal3 = new BigDecimal(0.0D);
      int i6 = 0;
      int i7 = paramACHBatch.getStandardEntryClassCodeValue();
      ACHEntry localACHEntry;
      ACHRecordInfo localACHRecordInfo;
      Object localObject5;
      while (localIterator.hasNext())
      {
        i2++;
        i3++;
        localACHEntry = (ACHEntry)localIterator.next();
        localACHRecordInfo = new ACHRecordInfo();
        if (j != 0)
        {
          if ("ADD".equalsIgnoreCase(localACHEntry.getAction()))
          {
            localACHRecordInfo.setAction("add");
          }
          else if ("CAN".equalsIgnoreCase(localACHEntry.getAction()))
          {
            localACHRecordInfo = (ACHRecordInfo)localACHEntry.getBpwEntryObject();
            localACHRecordInfo.setAction("del");
            i3--;
          }
          else if ("MOD".equalsIgnoreCase(localACHEntry.getAction()))
          {
            localACHRecordInfo = (ACHRecordInfo)localACHEntry.getBpwEntryObject();
            localACHRecordInfo.setAction("mod");
          }
        }
        else {
          localACHEntry.setAction(null);
        }
        localObject2[(i2 - 1)] = localACHRecordInfo;
        if (i7 == 10) {
          if (localRecACHBatchInfo != null) {
            localACHEntry.setPaymentTypeCode("R");
          } else {
            localACHEntry.setPaymentTypeCode("S");
          }
        }
        localObject4 = localACHEntry.getAchPayee();
        if ((localObject4 != null) && ("ACHBatch".equalsIgnoreCase(((ACHPayee)localObject4).getScope())))
        {
          localObject5 = new ACHPayeeInfo();
          ((ACHPayee)localObject4).setCompanyID(paramACHBatch.getCoID());
          if (((ACHPayee)localObject4).getSubmittedBy() == null) {
            ((ACHPayee)localObject4).setSubmittedBy(paramACHBatch.getSubmittedBy());
          }
          if (i7 == 18) {
            ((ACHPayee)localObject4).setName("XCK Freeform Payee");
          }
          if (((ACHPayee)localObject4).getAddToListValue() == true)
          {
            ((ACHPayee)localObject4).setScope("ACHCompany");
            ((ACHPayeeInfo)localObject5).setPrenoteSecCode(paramACHBatch.getStandardEntryClassCode());
          }
          setPayeeInfoFromPayee((ACHPayeeInfo)localObject5, (ACHPayee)localObject4);
          if (j == 0) {
            ((ACHPayeeInfo)localObject5).setPayeeID(null);
          }
          localACHRecordInfo.setPayeeAggregateInfo((ACHPayeeInfo)localObject5);
        }
        localACHRecordInfo.setClassCode(paramACHBatch.getStandardEntryClassCode());
        localACHRecordInfo.setPairedID(String.valueOf(i2));
        if (localACHEntry.getTaxFormID() != null) {
          localACHRecordInfo.setTaxFormId(localACHEntry.getTaxFormID());
        }
        if (localACHEntry.isAmountIsDebit())
        {
          m = 1;
          i5++;
        }
        else
        {
          k = 1;
          i4++;
        }
        localBigDecimal3 = setACHRecordInfo(localACHRecordInfo, i7, localACHEntry);
        if (localACHEntry.getAmountIsDebitValue() == true) {
          localBigDecimal2 = localBigDecimal2.add(localBigDecimal3);
        } else {
          localBigDecimal1 = localBigDecimal1.add(localBigDecimal3);
        }
        i6 += localACHRecordInfo.getAddendaCount().intValue();
        if (paramACHBatch.getBatchTypeValue() == 3)
        {
          i2++;
          localACHRecordInfo = new ACHRecordInfo();
          if (j != 0) {
            if ("ADD".equalsIgnoreCase(localACHEntry.getAction()))
            {
              localACHRecordInfo.setAction("add");
            }
            else if ("CAN".equalsIgnoreCase(localACHEntry.getAction()))
            {
              localACHRecordInfo = (ACHRecordInfo)localACHEntry.getBpwBalancedEntryObject();
              localACHRecordInfo.setAction("del");
            }
            else if ("MOD".equalsIgnoreCase(localACHEntry.getAction()))
            {
              localACHRecordInfo = (ACHRecordInfo)localACHEntry.getBpwBalancedEntryObject();
              localACHRecordInfo.setAction("mod");
            }
          }
          localObject2[(i2 - 1)] = localACHRecordInfo;
          localACHRecordInfo.setClassCode(paramACHBatch.getStandardEntryClassCode());
          localObject5 = localACHEntry.getAchPayee();
          String str6 = localACHEntry.getPrenote();
          localObject4 = new ACHPayee();
          localACHEntry.setAchPayee((ACHPayee)localObject4);
          localACHRecordInfo.setOffsetAccountID(localACHEntry.getOffsetAccountID());
          ((ACHPayee)localObject4).setAccountNumber(localACHEntry.getOffsetAccountNumber());
          ((ACHPayee)localObject4).setRoutingNumber(localACHEntry.getOffsetAccountBankID());
          ((ACHPayee)localObject4).setAccountType(localACHEntry.getOffsetAccountType());
          ((ACHPayee)localObject4).setName("Balanced");
          ((ACHPayee)localObject4).setUserAccountNumber(" ");
          localACHEntry.setPrenote("false");
          localACHRecordInfo.setPairedID(String.valueOf(i2 - 1));
          localACHEntry.setAmountIsDebit(!localACHEntry.getAmountIsDebitValue());
          localBigDecimal3 = setACHRecordInfo(localACHRecordInfo, i7, localACHEntry);
          if (localACHEntry.isAmountIsDebit()) {
            m = 1;
          } else {
            k = 1;
          }
          localACHEntry.setAmountIsDebit(!localACHEntry.getAmountIsDebitValue());
          i6 += localACHRecordInfo.getAddendaCount().intValue();
          localACHEntry.setAchPayee((ACHPayee)localObject5);
          localACHEntry.setPrenote(str6);
          localACHRecordInfo.setRecordCategory("BE");
        }
      }
      localBigDecimal2 = localBigDecimal2.negate();
      localBigDecimal3 = localBigDecimal1.add(localBigDecimal2);
      if ((paramACHBatch.getBatchTypeValue() == 3) || (paramACHBatch.getBatchTypeValue() == 2))
      {
        localBigDecimal2 = localBigDecimal3.abs();
        localBigDecimal1 = localBigDecimal3.abs();
      }
      if (paramACHBatch.getBatchTypeValue() == 2)
      {
        ((ACHBatchInfo)localObject1).setOffsetAccountID(paramACHBatch.getOffsetAccountID());
        if (localBigDecimal3.doubleValue() == 0.0D)
        {
          if (j != 0)
          {
            if (paramACHBatch.getBpwBalancedBatchObject() != null)
            {
              localACHRecordInfo = (ACHRecordInfo)paramACHBatch.getBpwBalancedBatchObject();
              localACHRecordInfo.setAction("del");
              localObject2[i2] = localACHRecordInfo;
            }
          }
          else
          {
            localObject5 = new ACHRecordInfo[i1 - 1];
            System.arraycopy(localObject2, 0, localObject5, 0, i1 - 1);
            localObject2 = localObject5;
          }
          localHashtable.put("BB_ACCOUNTID", paramACHBatch.getOffsetAccountID());
          localHashtable.put("BB_ACCOUNTNUMBER", paramACHBatch.getOffsetAccountNumber());
          localHashtable.put("BB_ACCOUNTBANKID", paramACHBatch.getOffsetAccountBankID());
          localHashtable.put("BB_ACCOUNTTYPE", paramACHBatch.getOffsetAccountType());
          localHashtable.put("BB_ACCOUNTNAME", paramACHBatch.getOffsetAccountName());
        }
        else
        {
          i2++;
          localACHRecordInfo = new ACHRecordInfo();
          if (j != 0) {
            if (paramACHBatch.getBpwBalancedBatchObject() != null)
            {
              localACHRecordInfo = (ACHRecordInfo)paramACHBatch.getBpwBalancedBatchObject();
              localACHRecordInfo.setAction("mod");
            }
            else
            {
              localACHRecordInfo.setAction("add");
            }
          }
          localACHRecordInfo.setOffsetAccountID(paramACHBatch.getOffsetAccountID());
          localObject2[(i2 - 1)] = localACHRecordInfo;
          localACHRecordInfo.setClassCode(paramACHBatch.getStandardEntryClassCode());
          localBigDecimal3 = localBigDecimal3.negate();
          localACHEntry = (ACHEntry)localACHEntries.createNoAdd();
          localObject4 = new ACHPayee();
          localACHEntry.setAchPayee((ACHPayee)localObject4);
          ((ACHPayee)localObject4).setAccountNumber(paramACHBatch.getOffsetAccountNumber());
          ((ACHPayee)localObject4).setRoutingNumber(paramACHBatch.getOffsetAccountBankID());
          ((ACHPayee)localObject4).setAccountType(paramACHBatch.getOffsetAccountType());
          ((ACHPayee)localObject4).setName("Balanced");
          ((ACHPayee)localObject4).setUserAccountNumber(" ");
          if (i7 == 10) {
            if (localRecACHBatchInfo != null) {
              localACHEntry.setPaymentTypeCode("R");
            } else {
              localACHEntry.setPaymentTypeCode("S");
            }
          }
          if ((i7 == 1) || (i7 == 5) || (i7 == 17) || (i7 == 18)) {
            localACHEntry.setCheckSerialNumber("Balanced");
          }
          if (i7 == 5)
          {
            localACHEntry.setTerminalState("BB");
            localACHEntry.setTerminalCity("BB");
          }
          if (i7 == 18)
          {
            localACHEntry.setProcessControlField("BB");
            localACHEntry.setItemResearchNumber("BB");
          }
          if (localBigDecimal3.signum() < 0)
          {
            localBigDecimal3 = localBigDecimal3.negate();
            localACHEntry.setAmountIsDebit(true);
            localACHEntry.setAmount(localBigDecimal3);
          }
          else
          {
            localACHEntry.setAmountIsDebit(false);
            localACHEntry.setAmount(localBigDecimal3);
          }
          if (localACHEntry.isAmountIsDebit()) {
            m = 1;
          } else {
            k = 1;
          }
          localACHRecordInfo.setPairedID(String.valueOf(0));
          localBigDecimal3 = setACHRecordInfo(localACHRecordInfo, i7, localACHEntry);
          i6 += localACHRecordInfo.getAddendaCount().intValue();
          localACHRecordInfo.setRecordCategory("BB");
        }
      }
      ((ACHBatchInfo)localObject1).setRecords((ACHRecordInfo[])localObject2);
      short s = 220;
      if ((k != 0) && (m != 0)) {
        s = 200;
      } else if (k != 0) {
        s = 220;
      } else if (m != 0) {
        s = 225;
      }
      paramACHBatch.setServiceClassCode("" + s);
      paramACHBatch.setTotalNumberCredits(i4);
      paramACHBatch.setTotalNumberDebits(i5);
      ((ACHBatchInfo)localObject1).createBatchControl();
      ((ACHBatchInfo)localObject1).createBatchHeader();
      setACHBatchHeaderObject(((ACHBatchInfo)localObject1).getBatchHeader(), s, paramACHBatch);
      setACHBatchControlObject(((ACHBatchInfo)localObject1).getBatchControl(), s);
      ((ACHBatchInfo)localObject1).setTotalBatchSize(i6 + i2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    ACHBPWServices localACHBPWServices = null;
    try
    {
      localACHBPWServices = getACHHandler();
      localHashtable.put("TOTALENTRIES", "" + i3);
      ((ACHBatchInfo)localObject1).setMemo(localHashtable);
      populateOBOAgentInfo(paramSecureUser, (BPWInfoBase)localObject1);
      if (paramHashMap.get("ExportOnly") == null) {
        try
        {
          if (localRecACHBatchInfo != null)
          {
            if (j != 0) {
              localRecACHBatchInfo = localACHBPWServices.modRecACHBatch(localRecACHBatchInfo);
            } else {
              localRecACHBatchInfo = localACHBPWServices.addRecACHBatch(localRecACHBatchInfo);
            }
            localObject1 = localRecACHBatchInfo;
          }
          else if (j != 0)
          {
            localObject1 = localACHBPWServices.modACHBatch((ACHBatchInfo)localObject1);
          }
          else
          {
            localObject1 = localACHBPWServices.addACHBatch((ACHBatchInfo)localObject1);
          }
        }
        catch (RemoteException localRemoteException)
        {
          localObject4 = localRemoteException.toString();
          if ((localObject4 != null) && (((String)localObject4).indexOf("Server is not running!") > 0)) {
            throw new CSILException(25018);
          }
        }
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    if (paramHashMap.get("ExportOnly") != null)
    {
      paramHashMap.put("ACHBatchInfo", localObject1);
      return paramACHBatch;
    }
    if (localObject1 != null)
    {
      i = ((ACHBatchInfo)localObject1).getStatusCode();
      str1 = ((ACHBatchInfo)localObject1).getStatusMsg();
      Object localObject3;
      if (i == 0)
      {
        paramACHBatch.setID(((ACHBatchInfo)localObject1).getBatchId());
        paramACHBatch.setStatus(ACHBatch.mapBPWStatusToInt(((ACHBatchInfo)localObject1).getBatchStatus()));
        localObject3 = paramACHBatch.getDateFormat();
        paramACHBatch.setDateFormat("yyyyMMdd");
        paramACHBatch.setSubmitDate(((ACHBatchInfo)localObject1).getSubmitDate());
        paramACHBatch.setDateFormat((String)localObject3);
      }
      else
      {
        DebugLog.log(Level.INFO, "*** BPW Error: " + str1);
        if (i == 23610)
        {
          i = 16126;
        }
        else if (i == 17170)
        {
          i = 16127;
        }
        else if (i == 17521)
        {
          i = 20003;
          localObject3 = ((ACHBatchInfo)localObject1).getExtInfo();
          if (localObject3 != null)
          {
            localObject4 = (Limits)((Hashtable)localObject3).get("NOT_ALLOWED_APPROVAL_LIMITS");
            if (localObject4 != null)
            {
              if (paramHashMap == null) {
                paramHashMap = new HashMap();
              }
              paramHashMap.put("ExceededLimits", localObject4);
            }
          }
        }
        else
        {
          i = 16002;
        }
        throw new CSILException(-1009, i, "", str1);
      }
    }
    if (i != 0) {
      paramACHBatch.getACHEntries().clear();
    }
    return paramACHBatch;
  }
  
  protected static BigDecimal setACHRecordInfo(ACHRecordInfo paramACHRecordInfo, int paramInt, ACHEntry paramACHEntry)
  {
    int i = 0;
    if ("CAN".equalsIgnoreCase(paramACHEntry.getAction()))
    {
      localBigDecimal = new BigDecimal("0.0");
      return localBigDecimal;
    }
    BigDecimal localBigDecimal = paramACHEntry.getAmountValue().getAmountValue();
    if (localBigDecimal.signum() < 0) {
      localBigDecimal = localBigDecimal.negate();
    }
    ACHPayee localACHPayee = paramACHEntry.getAchPayee();
    paramACHRecordInfo.setPayeeId(localACHPayee.getID());
    if ((localACHPayee != null) && ("ACHBatch".equalsIgnoreCase(localACHPayee.getScope())) && (paramACHEntry.getAction() == null)) {
      paramACHRecordInfo.setPayeeId(null);
    }
    i = paramACHEntry.getAddendas().size();
    if ((i > 1) && (paramInt != 15) && (paramInt != 13) && (paramInt != 16)) {
      i = 1;
    }
    if ((paramInt == 18) || (paramInt == 17) || (paramInt == 9) || (paramInt == 5)) {
      i = 0;
    }
    switch (paramInt)
    {
    case 1: 
      paramACHRecordInfo.setRecord(new TypeARCEntryDetailRecord());
      break;
    case 12: 
      paramACHRecordInfo.setRecord(new TypeCCDEntryDetailRecord());
      str1 = localACHPayee.getName();
      if (str1 == null) {
        str1 = "none";
      }
      if ((str1 != null) && (str1.length() > 22)) {
        str1 = str1.substring(0, 22);
      }
      paramACHRecordInfo.setFieldValueObject("Receiving_Company_Name", str1);
      break;
    case 2: 
      paramACHRecordInfo.setRecord(new TypeCIEEntryDetailRecord());
      break;
    case 13: 
      paramACHRecordInfo.setRecord(new TypeCTXEntryDetailRecord());
      str1 = localACHPayee.getName();
      if (str1 == null) {
        str1 = "none";
      }
      if ((str1 != null) && (str1.length() > 16)) {
        str1 = str1.substring(0, 16);
      }
      paramACHRecordInfo.setFieldValueObject("Receiving_Company_Identification_Number", str1);
      paramACHRecordInfo.setFieldValueObject("Reserved2", "");
      paramACHRecordInfo.setFieldValueObject("Number_Of_Addenda_Records", new Integer(i));
      break;
    case 6: 
      paramACHRecordInfo.setRecord(new TypePPDEntryDetailRecord());
      break;
    case 17: 
      paramACHRecordInfo.setRecord(new TypeRCKEntryDetailRecord());
      break;
    case 5: 
      paramACHRecordInfo.setRecord(new TypePOPEntryDetailRecord());
      paramACHRecordInfo.setFieldValueObject("Terminal_City4", paramACHEntry.getTerminalCity());
      paramACHRecordInfo.setFieldValueObject("Terminal_State", paramACHEntry.getTerminalState());
      break;
    case 9: 
      paramACHRecordInfo.setRecord(new TypeTELEntryDetailRecord());
      break;
    case 10: 
      paramACHRecordInfo.setRecord(new TypeWEBEntryDetailRecord());
      paramACHRecordInfo.setFieldValueObject("Payment_Type_Code", paramACHEntry.getPaymentTypeCode());
      break;
    case 18: 
      paramACHRecordInfo.setRecord(new TypeXCKEntryDetailRecord());
      paramACHRecordInfo.setFieldValueObject("Process_Control_Field", paramACHEntry.getProcessControlField());
      paramACHRecordInfo.setFieldValueObject("Item_Research_Number", paramACHEntry.getItemResearchNumber());
      break;
    }
    paramACHRecordInfo.setRecordType(6);
    paramACHRecordInfo.setFieldValueObject("Addenda_Record_Indicator", new Short((short)(i == 0 ? 0 : 1)));
    paramACHRecordInfo.setFieldValueObject("Amount", new Long(localBigDecimal.abs().multiply(new BigDecimal(100.0D)).longValue()));
    paramACHRecordInfo.setFieldValueObject("Record_Type_Code", new Short((short)6));
    paramACHRecordInfo.setFieldValueObject("Transaction_Code", new Short(paramACHEntry.getTransactionCodeValue(paramInt)));
    paramACHRecordInfo.setFieldValueObject("Check_Digit", new Short(RoutingNumberUtil.getRoutingNumber_CheckDigit(localACHPayee.getRoutingNumber())));
    paramACHRecordInfo.setFieldValueObject("DFI_Account_Number", localACHPayee.getAccountNumber());
    paramACHRecordInfo.setFieldValueObject("Receiving_DFI_Identification", RoutingNumberUtil.getRoutingNumber_EightDigits(localACHPayee.getRoutingNumber()));
    String str1 = paramACHEntry.getDiscretionaryData();
    if ((str1 != null) && (str1.length() > 0))
    {
      if (str1.length() > 2) {
        str1 = str1.substring(0, 2);
      }
      paramACHRecordInfo.setFieldValueObject("Discretionary_Data", str1);
      paramACHRecordInfo.setFieldValueObject("Discretionary_DataExists", Boolean.valueOf(true));
    }
    else
    {
      paramACHRecordInfo.setFieldValueObject("Discretionary_Data", null);
      paramACHRecordInfo.setFieldValueObject("Discretionary_DataExists", Boolean.valueOf(false));
    }
    if ((paramInt == 1) || (paramInt == 5) || (paramInt == 17) || (paramInt == 18))
    {
      str2 = paramACHEntry.getCheckSerialNumber();
      if ((paramInt == 5) && (str2 != null) && (str2.length() > 9)) {
        str2 = str2.substring(0, 9);
      }
      if ((paramInt != 5) && (str2 != null) && (str2.length() > 15)) {
        str2 = str2.substring(0, 15);
      }
      paramACHRecordInfo.setFieldValueObject("Check_Serial_Number", str2);
    }
    if ((paramInt == 10) || (paramInt == 9) || (paramInt == 2) || (paramInt == 12) || (paramInt == 13) || (paramInt == 4) || (paramInt == 6))
    {
      str2 = "Individual_Identification_Number";
      String str3 = localACHPayee.getUserAccountNumber();
      if ((paramInt == 13) || (paramInt == 12))
      {
        str2 = "Identification_Number";
        if (paramACHEntry.getIdentificationNumber() != null) {
          str3 = paramACHEntry.getIdentificationNumber();
        }
      }
      if (str3 != null)
      {
        if ((paramInt == 2) && (str3.length() > 22)) {
          str3 = str3.substring(0, 22);
        }
        if ((paramInt != 2) && (str3.length() > 15)) {
          str3 = str3.substring(0, 15);
        }
        paramACHRecordInfo.setFieldValueObject(str2, str3);
        paramACHRecordInfo.setFieldValueObject(str2 + "Exists", Boolean.valueOf(true));
      }
      else
      {
        paramACHRecordInfo.setFieldValueObject(str2 + "Exists", Boolean.valueOf(false));
      }
    }
    String str2 = localACHPayee.getName();
    int j = 22;
    String str4 = "Individual_Name";
    if (paramInt == 2) {
      j = 15;
    }
    if ((str2 != null) && (str2.length() > j)) {
      str2 = str2.substring(0, j);
    }
    if ((paramInt != 12) && (paramInt != 13) && (paramInt != 11) && (paramInt != 16) && (paramInt != 18)) {
      paramACHRecordInfo.setFieldValueObject(str4, str2);
    }
    if (paramInt == 5) {
      paramACHRecordInfo.setFieldValueObject("Individual_NameExists", Boolean.valueOf(true));
    }
    if (i > 0) {
      paramACHRecordInfo.setAddenda(getAddenda(paramInt, paramACHEntry.getAddendas()));
    }
    paramACHRecordInfo.setRecordCategory(null);
    return localBigDecimal;
  }
  
  protected static ACHAddendaInfo[] getAddenda(int paramInt, ArrayList paramArrayList)
  {
    if ((paramInt == 1) || (paramInt == 5) || (paramInt == 17) || (paramInt == 9) || (paramInt == 18)) {
      return null;
    }
    if ((paramArrayList == null) || (paramArrayList.size() == 0)) {
      return null;
    }
    int i = paramArrayList.size();
    ACHAddendaInfo[] arrayOfACHAddendaInfo = new ACHAddendaInfo[i];
    Iterator localIterator = paramArrayList.iterator();
    int j = 0;
    while (localIterator.hasNext())
    {
      ACHAddenda localACHAddenda = (ACHAddenda)localIterator.next();
      ACHAddendaInfo localACHAddendaInfo = new ACHAddendaInfo();
      if (localACHAddenda.getAction() != null) {
        if (localACHAddenda.getAction().equalsIgnoreCase("CAN"))
        {
          localACHAddendaInfo = (ACHAddendaInfo)localACHAddenda.getBpwAddendaObject();
          localACHAddendaInfo.setAction("del");
        }
        else if (localACHAddenda.getAction().equalsIgnoreCase("MOD"))
        {
          localACHAddendaInfo = (ACHAddendaInfo)localACHAddenda.getBpwAddendaObject();
          localACHAddendaInfo.setAction("mod");
        }
        else if (localACHAddenda.getAction().equalsIgnoreCase("ADD"))
        {
          localACHAddendaInfo.setAction("add");
        }
      }
      arrayOfACHAddendaInfo[j] = localACHAddendaInfo;
      j++;
      switch (paramInt)
      {
      case 12: 
        localACHAddendaInfo.setAddenda(new TypeCCDAddendaRecord());
        break;
      case 2: 
        localACHAddendaInfo.setAddenda(new TypeCIEAddendaRecord());
        break;
      case 13: 
        localACHAddendaInfo.setAddenda(new TypeCTXAddendaRecord());
        break;
      case 6: 
        localACHAddendaInfo.setAddenda(new TypePPDAddendaRecord());
        break;
      case 10: 
        localACHAddendaInfo.setAddenda(new TypeWEBAddendaRecord());
        break;
      }
      localACHAddendaInfo.setFieldValueObject("Addenda_Sequence_Number", new Short((short)j));
      localACHAddendaInfo.setFieldValueObject("Addenda_Type_Code", new Short((short)5));
      localACHAddendaInfo.setFieldValueObject("Payment_Related_InformationExists", Boolean.valueOf(true));
      localACHAddendaInfo.setFieldValueObject("Payment_Related_Information", localACHAddenda.getPmtRelatedInfo());
      localACHAddendaInfo.setFieldValueObject("Record_Type_Code", new Short((short)7));
    }
    return arrayOfACHAddendaInfo;
  }
  
  protected static void setACHBatchControlObject(ACHRecordInfo paramACHRecordInfo, short paramShort)
  {
    paramACHRecordInfo.setFieldValueObject("Message_Authentication_Code", null);
    paramACHRecordInfo.setFieldValueObject("Message_Authentication_CodeExists", Boolean.valueOf(false));
    paramACHRecordInfo.setFieldValueObject("Record_Type_Code", new Short((short)8));
    paramACHRecordInfo.setFieldValueObject("Service_Class_Code", new Short(paramShort));
    paramACHRecordInfo.setRecordType(8);
  }
  
  protected static void setACHBatchHeaderObject(ACHRecordInfo paramACHRecordInfo, short paramShort, ACHBatch paramACHBatch)
  {
    if ((paramACHBatch.getStandardEntryClassCodeValue() == 11) || (paramACHBatch.getStandardEntryClassCodeValue() == 4))
    {
      paramACHRecordInfo.setFieldValueObject("Foreign_Exchange_Indicator", "");
      paramACHRecordInfo.setFieldValueObject("Foreign_Exchange_Reference", "");
      paramACHRecordInfo.setFieldValueObject("Foreign_Exchange_Reference_Indicator", new Short((short)0));
      paramACHRecordInfo.setFieldValueObject("ISO_Dest_Currency_Code", "");
      paramACHRecordInfo.setFieldValueObject("ISO_Destination_Country_Code", "");
      paramACHRecordInfo.setFieldValueObject("ISO_Original_Currency_Code", "");
    }
    paramACHRecordInfo.setFieldValueObject("Record_Type_Code", new Short((short)5));
    paramACHRecordInfo.setFieldValueObject("Service_Class_Code", new Short(paramShort));
    paramACHRecordInfo.setFieldValueObject("Standard_Entry_Class_Code", paramACHBatch.getStandardEntryClassCode());
    String str1 = paramACHBatch.getCoDiscretionaryData();
    String str2 = paramACHBatch.getCoEntryDesc();
    if (!"REVERSAL".equals(str2)) {
      if (paramACHBatch.getStandardEntryClassCodeValue() == 18) {
        str2 = "NO CHECK";
      } else if (paramACHBatch.getStandardEntryClassCodeValue() == 17) {
        str2 = "REDEPCHECK";
      }
    }
    if (paramACHBatch.getTaxForm() != null)
    {
      localObject = paramACHBatch.getTaxForm();
      if (((str1 == null) || (str1.length() == 0)) && (((TaxForm)localObject).getCompDiscData() != null) && (((TaxForm)localObject).getCompDiscData().length() > 0)) {
        str1 = ((TaxForm)localObject).getCompDiscData();
      }
      if (((str2 == null) || (str2.length() == 0)) && (((TaxForm)localObject).getCompEntryDisc() != null) && (((TaxForm)localObject).getCompEntryDisc().length() > 0)) {
        str2 = ((TaxForm)localObject).getCompEntryDisc();
      }
    }
    if ((str2 == null) || (str2.length() == 0)) {
      str2 = paramACHBatch.getStandardEntryClassCode() + " Batch";
    }
    paramACHRecordInfo.setFieldValueObject("Company_Discretionary_Data", str1);
    paramACHRecordInfo.setFieldValueObject("Company_Entry_Description", str2);
    paramACHRecordInfo.setFieldValueObject("Company_Name", paramACHBatch.getCoName());
    Object localObject = formatDate.format(paramACHBatch.getDateValue().getTime());
    paramACHRecordInfo.setFieldValueObject("Effective_Entry_Date", ((String)localObject).substring(2));
    paramACHRecordInfo.setFieldValueObject("Originator_Status_Code", "1");
    paramACHRecordInfo.setRecordType(5);
  }
  
  public ACHBatch modifyACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch1, ACHBatch paramACHBatch2, HashMap paramHashMap)
    throws CSILException
  {
    paramHashMap.put("Modify", "true");
    ACHEntries localACHEntries1 = paramACHBatch1.getACHEntries();
    ACHEntries localACHEntries2 = (ACHEntries)paramACHBatch2.getACHEntries().clone();
    ACHEntries localACHEntries3 = new ACHEntries();
    Iterator localIterator = localACHEntries1.iterator();
    ACHEntry localACHEntry2;
    while (localIterator.hasNext())
    {
      ACHEntry localACHEntry1 = (ACHEntry)localIterator.next();
      localACHEntry2 = localACHEntries2.getByID(localACHEntry1.getID());
      if (localACHEntry2 != null)
      {
        localACHEntries2.remove(localACHEntry2);
        localACHEntry1.setAction("MOD");
        if (!localACHEntry1.getActiveValue())
        {
          localACHEntry1.setActive("true");
          localACHEntry1.setAction("CAN");
        }
      }
      if (localACHEntry1.getBpwEntryObject() == null) {
        localACHEntry1.setAction("ADD");
      }
      if ((localACHEntry2 != null) && ("MOD".equals(localACHEntry1.getAction())) && ((localACHEntry1.getAddendaCount() > 0) || (localACHEntry2.getAddendaCount() > 0)))
      {
        ACHAddendas localACHAddendas1 = localACHEntry1.getAddendas();
        ACHAddendas localACHAddendas2 = (ACHAddendas)localACHEntry2.getAddendas().clone();
        int i = 0;
        if (localACHAddendas2.size() >= localACHAddendas1.size()) {
          i = localACHAddendas1.size();
        } else {
          i = localACHAddendas2.size();
        }
        int j = 0;
        ACHAddenda localACHAddenda1;
        ACHAddenda localACHAddenda2;
        while (j < i)
        {
          localACHAddenda1 = (ACHAddenda)localACHAddendas1.get(j);
          localACHAddenda2 = (ACHAddenda)localACHAddendas2.get(j);
          j++;
          localACHAddenda1.setAction("MOD");
          localACHAddenda1.setBpwAddendaObject(localACHAddenda2.getBpwAddendaObject());
        }
        if (localACHAddendas2.size() > localACHAddendas1.size())
        {
          i = localACHAddendas2.size();
          while (j < i)
          {
            localACHAddenda2 = (ACHAddenda)localACHAddendas2.get(j);
            localACHAddenda2.setAction("CAN");
            localACHAddendas1.add(localACHAddenda2);
            j++;
          }
        }
        if (localACHAddendas1.size() > localACHAddendas2.size())
        {
          i = localACHAddendas1.size();
          while (j < i)
          {
            localACHAddenda1 = (ACHAddenda)localACHAddendas1.get(j);
            localACHAddenda1.setAction("ADD");
            j++;
          }
        }
      }
      localACHEntries3.add(localACHEntry1);
    }
    if (localACHEntries2.size() > 0)
    {
      localIterator = localACHEntries2.iterator();
      while (localIterator.hasNext())
      {
        localACHEntry2 = (ACHEntry)localIterator.next();
        localACHEntry2.setAction("CAN");
        localACHEntries3.add(localACHEntry2);
      }
    }
    paramACHBatch1.setACHEntries(localACHEntries3);
    return addACHBatch(paramSecureUser, paramACHBatch1, paramHashMap);
  }
  
  public void deleteACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    ACHBPWServices localACHBPWServices = null;
    try
    {
      localACHBPWServices = getACHHandler();
      ACHBatchInfo localACHBatchInfo = new ACHBatchInfo();
      localACHBatchInfo.setBatchId(paramACHBatch.getID());
      localACHBatchInfo.setCompId(paramACHBatch.getCoID());
      paramACHBatch.setSubmittedBy(Integer.toString(paramSecureUser.getProfileID()));
      localACHBatchInfo.setSubmittedBy(paramACHBatch.getSubmittedBy());
      localACHBatchInfo.setLogId(paramACHBatch.getTrackingID());
      localACHBatchInfo.setLastModifier(Integer.toString(paramSecureUser.getProfileID()));
      populateOBOAgentInfo(paramSecureUser, localACHBatchInfo);
      String str;
      try
      {
        localACHBatchInfo = localACHBPWServices.canACHBatch(localACHBatchInfo);
      }
      catch (RemoteException localRemoteException)
      {
        str = localRemoteException.toString();
        if ((str != null) && (str.indexOf("Server is not running!") > 0)) {
          throw new CSILException(25018);
        }
      }
      if ((localACHBatchInfo != null) && (localACHBatchInfo.getStatusCode() != 0))
      {
        DebugLog.log(Level.INFO, "*** BPW Error: " + localACHBatchInfo.getStatusMsg());
        int i = localACHBatchInfo.getStatusCode();
        str = localACHBatchInfo.getStatusMsg();
        if (i == 23610) {
          i = 16126;
        } else if (i == 17170) {
          i = 16127;
        } else {
          i = 16002;
        }
        throw new CSILException(-1009, i, "", str);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
  }
  
  public ACHBatch addRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    return addACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
  }
  
  public ACHBatch modifyRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch1, ACHBatch paramACHBatch2, HashMap paramHashMap)
    throws CSILException
  {
    return modifyACHBatch(paramSecureUser, paramACHBatch1, paramACHBatch2, paramHashMap);
  }
  
  public void deleteRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    ACHBPWServices localACHBPWServices = null;
    try
    {
      localACHBPWServices = getACHHandler();
      RecACHBatchInfo localRecACHBatchInfo = new RecACHBatchInfo();
      localRecACHBatchInfo.setBatchId(paramACHBatch.getRecID());
      localRecACHBatchInfo.setCompId(paramACHBatch.getCoID());
      if (paramACHBatch.getSubmittedBy() == null) {
        paramACHBatch.setSubmittedBy(Integer.toString(paramSecureUser.getProfileID()));
      }
      localRecACHBatchInfo.setSubmittedBy(paramACHBatch.getSubmittedBy());
      localRecACHBatchInfo.setLogId(paramACHBatch.getTrackingID());
      localRecACHBatchInfo.setLastModifier(Integer.toString(paramSecureUser.getProfileID()));
      populateOBOAgentInfo(paramSecureUser, localRecACHBatchInfo);
      String str;
      try
      {
        localRecACHBatchInfo = localACHBPWServices.canRecACHBatch(localRecACHBatchInfo);
      }
      catch (RemoteException localRemoteException)
      {
        str = localRemoteException.toString();
        if ((str != null) && (str.indexOf("Server is not running!") > 0)) {
          throw new CSILException(25018);
        }
      }
      if ((localRecACHBatchInfo != null) && (localRecACHBatchInfo.getStatusCode() != 0))
      {
        DebugLog.log(Level.INFO, "*** BPW Error: " + localRecACHBatchInfo.getStatusMsg());
        int i = localRecACHBatchInfo.getStatusCode();
        str = localRecACHBatchInfo.getStatusMsg();
        if (i == 23610) {
          i = 16126;
        } else if (i == 17170) {
          i = 16127;
        } else {
          i = 16002;
        }
        throw new CSILException(-1009, i, "", str);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
  }
  
  public ACHBatches getACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    paramHashMap.put("PAGESIZE", "1000");
    return getBatchesFromBatchHist(paramSecureUser, paramString1, paramString2, localPagingContext, false, paramHashMap, 99);
  }
  
  protected ACHBatches getBatchesFromApprovals(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    ACHBatches localACHBatches1 = new ACHBatches();
    boolean bool = true;
    if ((paramHashMap != null) && (paramHashMap.get("ViewAll") != null))
    {
      bool = Boolean.valueOf((String)paramHashMap.get("ViewAll")).booleanValue();
      if (bool == true) {
        paramHashMap.put("ACH_VIEW", "ALL");
      }
    }
    ACHBatches localACHBatches2 = getBatchesFromBatchHist(paramSecureUser, paramString1, paramString2, paramPagingContext, false, paramHashMap, 3);
    if (localACHBatches2 != null) {
      localACHBatches1.setPagingContext(localACHBatches2.getPagingContext());
    }
    if ((localACHBatches2 != null) && (localACHBatches2.size() > 0))
    {
      Iterator localIterator1 = localACHBatches2.iterator();
      while (localIterator1.hasNext())
      {
        ACHBatch localACHBatch = (ACHBatch)localIterator1.next();
        try
        {
          HashMap localHashMap = new HashMap();
          int i = 7;
          if (paramString2.equals("TaxPayment")) {
            i = 9;
          } else if (paramString2.equals("ChildSupportPayment")) {
            i = 13;
          }
          localHashMap.put("TrackingID", localACHBatch.getTrackingID());
          localHashMap.put("ItemSubType", new Integer(i));
          String[] arrayOfString = { "Pending", "Rejected" };
          ApprovalsStatuses localApprovalsStatuses = Approvals.getSubmittedItems(1, arrayOfString, localHashMap, paramHashMap);
          if ((localApprovalsStatuses != null) && (localApprovalsStatuses.size() > 0))
          {
            Iterator localIterator2 = localApprovalsStatuses.iterator();
            while (localIterator2.hasNext())
            {
              ApprovalsStatus localApprovalsStatus = (ApprovalsStatus)localIterator2.next();
              ApprovalsItem localApprovalsItem = localApprovalsStatus.getApprovalItem();
              localACHBatch.setCategory("ACH_BATCH_PAYMENT");
              localACHBatch.setSubmitDate(localApprovalsItem.getSubmissionDate());
              Object localObject1;
              if (localApprovalsStatus.getCurrentChainItem() != null) {
                if (localApprovalsStatus.getCurrentChainItem().isUsingUser())
                {
                  localObject1 = new ArrayList();
                  ((ArrayList)localObject1).add("" + localApprovalsStatus.getCurrentChainItem().getGroupOrUserID());
                  Object localObject2;
                  if (localApprovalsStatus.getCurrentChainItem().getUserType() == 2)
                  {
                    localObject2 = new LocalizableString("com.ffusion.approvals.resources", "ApprovalsBankEmployeeDesc", null);
                    localACHBatch.setApproverName((String)((ILocalizable)localObject2).localize(paramSecureUser.getLocale()));
                    localACHBatch.setApproverIsGroup(false);
                  }
                  else
                  {
                    try
                    {
                      localObject2 = CustomerAdapter.getBusinessEmployeesByIds((ArrayList)localObject1);
                      if (((BusinessEmployees)localObject2).size() > 0)
                      {
                        BusinessEmployee localBusinessEmployee = (BusinessEmployee)((BusinessEmployees)localObject2).get(0);
                        localACHBatch.setApproverName(localBusinessEmployee.getName());
                        localACHBatch.setApproverIsGroup(false);
                      }
                    }
                    catch (ProfileException localProfileException) {}
                  }
                }
                else if (localApprovalsStatus.getCurrentChainItem().getIsApprovalsGroup())
                {
                  localObject1 = Approvals.getApprovalGroups(paramSecureUser, paramHashMap);
                  ApprovalsGroup localApprovalsGroup = ((ApprovalsGroups)localObject1).getByID(localApprovalsStatus.getCurrentChainItem().getGroupOrUserID());
                  localACHBatch.setApproverName(localApprovalsGroup.getGroupName());
                  localACHBatch.setApproverIsGroup(true);
                }
                else
                {
                  localObject1 = Entitlements.getEntitlementGroup(localApprovalsStatus.getCurrentChainItem().getGroupOrUserID());
                  localACHBatch.setApproverName(((EntitlementGroup)localObject1).getGroupName());
                  localACHBatch.setApproverIsGroup(true);
                }
              }
              if ((localApprovalsStatus.getDecision() != null) && (localApprovalsStatus.getDecision().equals("Rejected")))
              {
                localObject1 = localApprovalsItem.getApprovalItemProperty("RejectReason");
                if ((localObject1 != null) && (((String)localObject1).trim().length() > 0)) {
                  localACHBatch.setRejectReason((String)localObject1);
                }
                localACHBatch.setRecID(null);
                localACHBatch.setFrequency(0);
                localACHBatch.setNumberPayments(0);
                localACHBatch.setStatus(10);
              }
              else if ((localApprovalsStatus.getDecision() != null) && (localApprovalsStatus.getDecision().equals("Hold")))
              {
                localACHBatch.setStatus(9);
              }
              else
              {
                localACHBatch.setStatus(8);
              }
            }
          }
        }
        catch (CSILException localCSILException) {}
      }
    }
    return localACHBatches2;
  }
  
  protected ACHBatches getBatchesFromBatchHist(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, boolean paramBoolean, HashMap paramHashMap, int paramInt)
    throws CSILException
  {
    return getBatchesFromBatchHist(paramSecureUser, paramString1, paramString2, paramPagingContext, paramBoolean, paramHashMap, paramInt, false);
  }
  
  protected ACHBatches getBatchesFromBatchHist(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, boolean paramBoolean1, HashMap paramHashMap, int paramInt, boolean paramBoolean2)
    throws CSILException
  {
    ACHBatches localACHBatches = new ACHBatches();
    localACHBatches.setPagingContext(paramPagingContext);
    boolean bool1 = false;
    boolean bool2 = false;
    HashMap localHashMap1 = paramPagingContext.getMap();
    HashMap localHashMap2 = new HashMap();
    if (localHashMap1 == null) {
      localHashMap1 = new HashMap();
    }
    paramPagingContext.setMap(localHashMap1);
    ACHBatchHist localACHBatchHist = new ACHBatchHist();
    if ((paramString1 != null) && (paramString1.length() > 0)) {
      localACHBatchHist.setCompId(paramString1);
    }
    localACHBatchHist.setCustomerId("" + paramSecureUser.getBusinessID());
    if ((paramHashMap != null) && (paramHashMap.get("ACH_VIEW") == null))
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = new String("" + paramSecureUser.getProfileID());
      localACHBatchHist.setSubmittedByList(arrayOfString);
    }
    else
    {
      localACHBatchHist.setViewEntitlementCheck(true);
      localACHBatchHist.setViewerId("" + paramSecureUser.getProfileID());
    }
    long l1 = 10L;
    l1 = getPageSize(paramHashMap);
    localACHBatchHist.setBatchPageSize(l1);
    if (paramInt == 2) {
      localACHBatchHist.setAscending(false);
    }
    if (paramString2.equals("REVERSAL"))
    {
      localACHBatchHist.setCursorId(null);
      localACHBatchHist.setHistId(null);
      if (paramPagingContext.getStartDate() != null)
      {
        localObject1 = formatDate.format(paramPagingContext.getStartDate().getTime());
        localACHBatchHist.setStartDate((String)localObject1);
      }
      if (paramPagingContext.getEndDate() != null)
      {
        localObject1 = formatDate.format(paramPagingContext.getEndDate().getTime());
        localACHBatchHist.setEndDate((String)localObject1);
      }
      localObject1 = new String[] { "WILLPROCESSON", "PROCESSEDON", "POSTEDON", "CREATED" };
      localACHBatchHist.setBatchStatusList((String[])localObject1);
      localACHBatchHist.setLogId((String)paramHashMap.get("REVERSEID"));
    }
    else
    {
      Object localObject3;
      if (paramPagingContext.getDirection().equalsIgnoreCase("FIRST"))
      {
        localACHBatchHist.setCursorId(null);
        localACHBatchHist.setHistId(null);
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
            localACHBatchHist.setStartDate((String)localObject2);
          }
          else
          {
            localObject2 = GregorianCalendar.getInstance();
            localObject3 = formatDate.format(((Calendar)localObject2).getTime());
            localACHBatchHist.setStartDate((String)localObject3);
          }
          if (paramPagingContext.getEndDate() != null)
          {
            localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
            localACHBatchHist.setEndDate((String)localObject2);
          }
          localObject2 = new String[] { "CREATED", "WILLPROCESSON" };
          localACHBatchHist.setBatchStatusList((String[])localObject2);
        }
        else if (paramInt == 2)
        {
          if (paramPagingContext.getStartDate() != null)
          {
            localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
            localACHBatchHist.setStartDate((String)localObject2);
          }
          else
          {
            localObject2 = GregorianCalendar.getInstance();
            ((Calendar)localObject2).add(5, -this.maxTransactionDays);
            localObject3 = formatDate.format(((Calendar)localObject2).getTime());
            localACHBatchHist.setStartDate((String)localObject3);
          }
          if (paramPagingContext.getEndDate() != null)
          {
            localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
            localACHBatchHist.setEndDate((String)localObject2);
          }
          localObject2 = new String[] { "PROCESSEDON", "POSTEDON", "LIMIT_CHECK_FAILED", "LIMIT_REVERT_FAILED", "APPROVAL_FAILED", "APPROVAL_NOT_ALLOWED", "FAILEDON" };
          localACHBatchHist.setBatchStatusList((String[])localObject2);
        }
        else if (paramInt == 3)
        {
          if (paramPagingContext.getStartDate() != null)
          {
            localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
            localACHBatchHist.setStartDate((String)localObject2);
          }
          else
          {
            localObject2 = GregorianCalendar.getInstance();
            ((Calendar)localObject2).add(5, -this.maxTransactionDays);
            localObject3 = formatDate.format(((Calendar)localObject2).getTime());
            localACHBatchHist.setStartDate((String)localObject3);
          }
          if (paramPagingContext.getEndDate() != null)
          {
            localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
            localACHBatchHist.setEndDate((String)localObject2);
          }
          localObject2 = new String[] { "APPROVAL_PENDING", "APPROVAL_REJECTED" };
          localACHBatchHist.setBatchStatusList((String[])localObject2);
        }
        else
        {
          if (paramPagingContext.getStartDate() != null)
          {
            localObject2 = formatDate.format(paramPagingContext.getStartDate().getTime());
            localACHBatchHist.setStartDate((String)localObject2);
          }
          if (paramPagingContext.getEndDate() != null)
          {
            localObject2 = formatDate.format(paramPagingContext.getEndDate().getTime());
            localACHBatchHist.setEndDate((String)localObject2);
          }
          localObject2 = new String[] { "WILLPROCESSON", "PROCESSEDON", "POSTEDON", "LIMIT_CHECK_FAILED", "LIMIT_REVERT_FAILED", "APPROVAL_PENDING", "APPROVAL_REJECTED", "APPROVAL_FAILED", "APPROVAL_NOT_ALLOWED", "FAILEDON", "CREATED" };
          localACHBatchHist.setBatchStatusList((String[])localObject2);
        }
      }
      else
      {
        int i;
        if (paramPagingContext.getDirection().equalsIgnoreCase("NEXT"))
        {
          localObject1 = (String)localHashMap1.get("HISTID");
          localObject2 = (String)localHashMap1.get("CURSORID");
          localObject3 = (ArrayList)localHashMap1.get("PREV");
          paramPagingContext.setLastIndex(paramPagingContext.getLastIndex() + l1);
          i = new Long(paramPagingContext.getFirstIndex()).intValue();
          i++;
          paramPagingContext.setFirstIndex(i);
          if (i >= ((ArrayList)localObject3).size()) {
            ((ArrayList)localObject3).add(localObject2);
          } else {
            ((ArrayList)localObject3).set(i, localObject2);
          }
          localHashMap1.put("PREV", localObject3);
          localACHBatchHist.setCursorId((String)localObject2);
          localACHBatchHist.setHistId((String)localObject1);
          localACHBatchHist.setStartDate("00000000");
          bool1 = false;
        }
        else if (paramPagingContext.getDirection().equalsIgnoreCase("PREVIOUS"))
        {
          localObject1 = (String)localHashMap1.get("HISTID");
          localObject2 = (String)localHashMap1.get("CURSORID");
          localObject3 = (ArrayList)localHashMap1.get("PREV");
          paramPagingContext.setLastIndex(paramPagingContext.getLastIndex() - l1);
          i = new Long(paramPagingContext.getFirstIndex()).intValue();
          if (i != 0) {
            i--;
          }
          paramPagingContext.setFirstIndex(i);
          if ((localObject3 != null) && (((ArrayList)localObject3).size() > 0))
          {
            localObject2 = (String)((ArrayList)localObject3).get(i);
            if (i == 0) {
              bool1 = true;
            }
            localHashMap1.put("PREV", localObject3);
          }
          localACHBatchHist.setCursorId((String)localObject2);
          localACHBatchHist.setHistId((String)localObject1);
          localACHBatchHist.setStartDate("00000000");
        }
      }
    }
    if (paramString2.toUpperCase().startsWith("REVERSAL"))
    {
      localObject1 = new String[] { "ACH_BATCH_REVERSAL" };
      localACHBatchHist.setBatchCategoryList((String[])localObject1);
    }
    else if (paramString2.toUpperCase().startsWith("CHILD"))
    {
      localObject1 = new String[] { "ACH_BATCH_CHILD_SUPPORT" };
      localACHBatchHist.setBatchCategoryList((String[])localObject1);
    }
    else if (paramString2.toUpperCase().startsWith("TAX"))
    {
      localObject1 = new String[] { "ACH_BATCH_TAX" };
      localACHBatchHist.setBatchCategoryList((String[])localObject1);
    }
    else if (!paramBoolean2)
    {
      localObject1 = new String[] { "ACH_BATCH_PAYMENT", "ACH_BATCH_REVERSAL" };
      localACHBatchHist.setBatchCategoryList((String[])localObject1);
    }
    else
    {
      localObject1 = new String[] { "ACH_BATCH_PAYMENT", "ACH_BATCH_REVERSAL", "ACH_BATCH_PRENOTE" };
      localACHBatchHist.setBatchCategoryList((String[])localObject1);
    }
    if ((paramHashMap != null) && (paramHashMap.get("REPORT") != null))
    {
      localObject1 = localACHBatchHist.getStartDate();
      if ((localObject1 != null) && (((String)localObject1).length() > 2)) {
        localObject1 = ((String)localObject1).substring(2);
      }
      localACHBatchHist.setStartEffectiveDate((String)localObject1);
      localObject1 = localACHBatchHist.getEndDate();
      if ((localObject1 != null) && (((String)localObject1).length() > 2)) {
        localObject1 = ((String)localObject1).substring(2);
      }
      localACHBatchHist.setEndEffectiveDate((String)localObject1);
    }
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject4;
    try
    {
      localObject2 = getACHHandler();
      if (paramBoolean1) {
        localObject1 = ((ACHBPWServices)localObject2).getRecACHBatchHistory(localACHBatchHist);
      } else {
        localObject1 = ((ACHBPWServices)localObject2).getACHBatchHistory(localACHBatchHist);
      }
    }
    catch (RemoteException localRemoteException)
    {
      localObject4 = localRemoteException.toString();
      if ((localObject4 != null) && (((String)localObject4).indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
      bool2 = true;
      bool1 = true;
    }
    finally
    {
      if (localObject2 != null)
      {
        removeACHHandler((ACHBPWServices)localObject2);
        localObject2 = null;
      }
    }
    if (localObject1 != null)
    {
      ACHBatchInfo[] arrayOfACHBatchInfo = (ACHBatchInfo[])((ACHBatchHist)localObject1).getBatches();
      localObject4 = null;
      ArrayList localArrayList1 = new ArrayList();
      HashMap localHashMap3 = new HashMap();
      String str = ((ACHBatchHist)localObject1).getCursorId();
      long l2 = ((ACHBatchHist)localObject1).getTotalBatches();
      localHashMap1.put("HISTID", ((ACHBatchHist)localObject1).getHistId());
      localHashMap1.put("CURSORID", str);
      ACHBatch localACHBatch;
      Object localObject7;
      if (arrayOfACHBatchInfo != null)
      {
        for (int j = 0; (j < arrayOfACHBatchInfo.length) && (localACHBatches.size() < l1); j++)
        {
          localObject4 = arrayOfACHBatchInfo[j];
          localACHBatch = (ACHBatch)localACHBatches.create();
          BeansConverter.setBatchHeaderFromBatchInfo(localACHBatch, (ACHBatchInfo)localObject4);
          if (localACHBatch.getTaxFormID() != null) {
            try
            {
              TaxForm localTaxForm = getTaxForm(paramSecureUser, localACHBatch.getTaxFormID(), paramHashMap);
              localACHBatch.setTaxForm(localTaxForm);
            }
            catch (CSILException localCSILException) {}
          }
          if (localACHBatch.getCompanyID() == null)
          {
            if (localHashMap2.get(localACHBatch.getCoID()) == null)
            {
              localObject7 = getACHCompanyInfo(paramSecureUser, null, localACHBatch.getCoID());
              localHashMap2.put(localACHBatch.getCoID(), localObject7);
            }
            localObject7 = (ACHCompanyInfo)localHashMap2.get(localACHBatch.getCoID());
            localACHBatch.setCompanyID(((ACHCompanyInfo)localObject7).getCompACHId());
          }
          if ((((ACHBatchInfo)localObject4).getBatchType() != null) && (((ACHBatchInfo)localObject4).getBatchType().equalsIgnoreCase("Recurring")))
          {
            if (localACHBatch.getRecID() == null) {
              localACHBatch.setRecID(((ACHBatchInfo)localObject4).getBatchId());
            }
            if (localHashMap3.get(localACHBatch.getRecID()) == null)
            {
              localArrayList1.add(localACHBatch.getRecID());
              localHashMap3.put(localACHBatch.getRecID(), new ArrayList());
            }
            if (paramInt == 1)
            {
              localObject7 = "rec_" + localACHBatch.getRecID() + "_" + j;
              localACHBatch.setID((String)localObject7);
            }
            localObject7 = (ArrayList)localHashMap3.get(localACHBatch.getRecID());
            ((ArrayList)localObject7).add(localACHBatch);
          }
        }
        if ((arrayOfACHBatchInfo == null) || (paramPagingContext.getLastIndex() + localACHBatches.size() >= l2)) {
          bool2 = true;
        }
      }
      localHashMap1.put("CURSORID", str);
      if (localArrayList1.size() > 0)
      {
        RecACHBatchInfo[] arrayOfRecACHBatchInfo = null;
        localObject2 = null;
        try
        {
          localObject2 = getACHHandler();
          localObject7 = (String[])localArrayList1.toArray(new String[0]);
          localObject8 = new ACHBatchHist();
          ((ACHBatchHist)localObject8).setBatchIdList((String[])localObject7);
          localObject8 = ((ACHBPWServices)localObject2).getRecACHBatchHistory((ACHBatchHist)localObject8);
          arrayOfRecACHBatchInfo = (RecACHBatchInfo[])((ACHBatchHist)localObject8).getBatches();
        }
        catch (Exception localException)
        {
          Object localObject8 = localException.toString();
          if ((localObject8 != null) && (((String)localObject8).indexOf("Server is not running!") > 0)) {
            throw new CSILException(25018);
          }
        }
        finally
        {
          if (localObject2 != null) {
            removeACHHandler((ACHBPWServices)localObject2);
          }
        }
        if (arrayOfRecACHBatchInfo != null)
        {
          RecACHBatchInfo localRecACHBatchInfo = null;
          for (int k = 0; k < arrayOfRecACHBatchInfo.length; k++)
          {
            localRecACHBatchInfo = arrayOfRecACHBatchInfo[k];
            ArrayList localArrayList2 = (ArrayList)localHashMap3.get(localRecACHBatchInfo.getBatchId());
            Iterator localIterator = localArrayList2.iterator();
            while (localIterator.hasNext())
            {
              localACHBatch = (ACHBatch)localIterator.next();
              if ((localRecACHBatchInfo != null) && (localACHBatch != null))
              {
                localACHBatch.setNumberPayments(localRecACHBatchInfo.getPmtsCount());
                localACHBatch.setFrequency(BeansConverter.getBPWFrequency(localRecACHBatchInfo.getFrequency()));
                localACHBatch.set("StartDate", localRecACHBatchInfo.getStartDate());
              }
            }
          }
        }
      }
    }
    else
    {
      bool2 = true;
    }
    localACHBatches.getPagingContext().setLastPage(bool2);
    localACHBatches.getPagingContext().setFirstPage(bool1);
    return localACHBatches;
  }
  
  public ACHBatches getPagedPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("FIRST");
    if ((paramHashMap != null) && (paramHashMap.get("Approvals") != null)) {
      return getBatchesFromApprovals(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
    }
    return getBatchesFromBatchHist(paramSecureUser, paramString1, paramString2, paramPagingContext, false, paramHashMap, 1);
  }
  
  public ACHBatches getPagedACHBatchHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    HashMap localHashMap1 = paramPagingContext.getMap();
    HashMap localHashMap2 = new HashMap();
    if (localHashMap1 == null)
    {
      localHashMap1 = new HashMap();
      paramPagingContext.setMap(localHashMap1);
    }
    long l1 = 10L;
    try
    {
      l1 = getPageSize(paramHashMap);
    }
    catch (Exception localException1) {}
    localHashMap1.put("PAGE_SIZE", String.valueOf(l1));
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap1.get("ReportCriteria");
    Object localObject7;
    Object localObject8;
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
          if ((((ReportSortCriterion)localObject4).getName() != null) && (((ReportSortCriterion)localObject4).getName().length() > 0)) {
            ((ArrayList)localObject2).add(localObject5);
          }
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
      localObject7 = null;
      HashMap localHashMap3 = null;
      localObject8 = null;
      String str = null;
      if (paramHashMap != null)
      {
        localObject4 = (String)paramHashMap.get("ACH_VIEW");
        localObject5 = (String)paramHashMap.get("ACH_STATUS");
        localObject6 = (String)paramHashMap.get("ACH_TYPE");
        localObject7 = (String)paramHashMap.get("CompanyID");
        localHashMap3 = (HashMap)paramHashMap.get("ACH_CompanyIDs");
        localObject8 = (String)paramHashMap.get("IncludePrenote");
        str = (String)paramHashMap.get("TrackingID");
      }
      int k = 0;
      if ("ALL".equals(localObject4)) {
        k = 1;
      }
      if (k == 0)
      {
        arrayOfString1 = new String[] { String.valueOf(paramSecureUser.getProfileID()) };
        ((HashMap)localObject3).put("SubmittedBys", arrayOfString1);
      }
      if ((localHashMap3 != null) && (localHashMap3.size() > 0)) {
        ((HashMap)localObject3).put("CompanyIdSECs", localHashMap3);
      }
      if ((str != null) && (str.length() > 0)) {
        ((HashMap)localObject3).put("LogId", str);
      }
      if ((localObject7 != null) && (((String)localObject7).length() > 0)) {
        ((HashMap)localObject3).put("CompId", localObject7);
      }
      String[] arrayOfString1 = null;
      if ("ACH_STATUS_PENDING".equalsIgnoreCase((String)localObject5)) {
        arrayOfString1 = new String[] { "CREATED", "WILLPROCESSON" };
      } else if ("ACH_STATUS_APPROVAL".equalsIgnoreCase((String)localObject5)) {
        arrayOfString1 = new String[] { "APPROVAL_PENDING", "APPROVAL_REJECTED" };
      } else if ("ACH_STATUS_APPROVAL_PENDING".equalsIgnoreCase((String)localObject5)) {
        arrayOfString1 = new String[] { "APPROVAL_PENDING" };
      } else if ("ACH_STATUS_APPROVAL_REJECTED".equalsIgnoreCase((String)localObject5)) {
        arrayOfString1 = new String[] { "APPROVAL_REJECTED" };
      } else if ("ACH_STATUS_REVERSAL".equalsIgnoreCase((String)localObject5)) {
        arrayOfString1 = new String[] { "WILLPROCESSON", "PROCESSEDON", "POSTEDON", "CREATED" };
      } else if ("ACH_STATUS_COMPLETED".equalsIgnoreCase((String)localObject5)) {
        arrayOfString1 = new String[] { "PROCESSEDON", "POSTEDON", "LIMIT_CHECK_FAILED", "LIMIT_REVERT_FAILED", "APPROVAL_FAILED", "APPROVAL_NOT_ALLOWED", "FAILEDON" };
      }
      ((HashMap)localObject3).put("StatusList", arrayOfString1);
      String[] arrayOfString2 = null;
      if (localObject6 == null) {
        localObject6 = "ACHBatch";
      }
      if ("REVERSAL".equalsIgnoreCase((String)localObject6)) {
        arrayOfString2 = new String[] { "ACH_BATCH_REVERSAL" };
      } else if ("ChildSupportPayment".equalsIgnoreCase((String)localObject6)) {
        arrayOfString2 = new String[] { "ACH_BATCH_CHILD_SUPPORT" };
      } else if ("TaxPayment".equalsIgnoreCase((String)localObject6)) {
        arrayOfString2 = new String[] { "ACH_BATCH_TAX" };
      } else if (localObject8 == null) {
        arrayOfString2 = new String[] { "ACH_BATCH_PAYMENT", "ACH_BATCH_REVERSAL" };
      } else {
        arrayOfString2 = new String[] { "ACH_BATCH_PAYMENT", "ACH_BATCH_REVERSAL", "ACH_BATCH_PRENOTE" };
      }
      ((HashMap)localObject3).put("CategoryList", arrayOfString2);
      ((HashMap)localObject3).put("TransType", new String[] { "ACH" });
      localHashMap1.put("SEARCH_CRITERIA", localObject3);
    }
    Object localObject1 = localHashMap1.get("SORT_VALUE_MIN_TransactionIndex");
    Object localObject2 = localHashMap1.get("SORT_VALUE_MAX_TransactionIndex");
    if (localObject1 != null) {
      localHashMap1.put("SORT_VALUE_MIN_TransactionIndex", String.valueOf(localObject1));
    }
    if (localObject2 != null) {
      localHashMap1.put("SORT_VALUE_MAX_TransactionIndex", String.valueOf(localObject2));
    }
    Object localObject3 = new PagingInfo();
    ((PagingInfo)localObject3).setPagingContext(paramPagingContext);
    Object localObject4 = getACHHandler();
    try
    {
      localHashMap1.remove("ReportCriteria");
      paramPagingContext.setMap(localHashMap1);
      ((PagingInfo)localObject3).setPagingContext(paramPagingContext);
      localObject3 = ((ACHBPWServices)localObject4).getPagedACH((PagingInfo)localObject3);
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("getACHBatchHistoriesPage ", localException2);
      localObject6 = localException2.toString();
      if ((localObject6 != null) && (((String)localObject6).indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
      throw new CSILException(-1009, 16133);
    }
    finally
    {
      if (localObject4 != null) {
        removeACHHandler((ACHBPWServices)localObject4);
      }
    }
    ArrayList localArrayList = ((PagingInfo)localObject3).getPagingResult();
    paramPagingContext.setMap(((PagingInfo)localObject3).getPagingContext().getMap());
    paramPagingContext.setSessionId(((PagingInfo)localObject3).getPagingContext().getSessionId());
    localHashMap1 = paramPagingContext.getMap();
    if (localHashMap1 != null)
    {
      localHashMap1.put("ReportCriteria", localReportCriteria);
      localObject6 = localHashMap1.get("LOWER_BOUND_TransactionIndex");
      localObject7 = localHashMap1.get("UPPER_BOUND_TransactionIndex");
      try
      {
        long l2;
        if (localObject6 != null)
        {
          l2 = Long.parseLong(localObject6.toString());
          localHashMap1.put("LOWER_BOUND_TransactionIndex", new Long(l2));
        }
        if (localObject7 != null)
        {
          l2 = Long.parseLong(localObject7.toString());
          localHashMap1.put("UPPER_BOUND_TransactionIndex", new Long(l2));
        }
      }
      catch (Exception localException3)
      {
        DebugLog.log("ACHService.getPagedACHBatchHistories: Exception thrown!");
        localException3.printStackTrace();
      }
    }
    Object localObject6 = new ACHBatches();
    ((ACHBatches)localObject6).setPagingContext(((PagingInfo)localObject3).getPagingContext());
    if ((localArrayList != null) && (localArrayList.size() != 0))
    {
      localObject7 = null;
      ACHHistInfo localACHHistInfo = null;
      if (localArrayList != null) {
        for (int j = 0; j < localArrayList.size(); j++)
        {
          localACHHistInfo = (ACHHistInfo)localArrayList.get(j);
          localObject7 = (ACHBatchInfo)localACHHistInfo.getObjInfo();
          localObject8 = (ACHBatch)((ACHBatches)localObject6).create();
          BeansConverter.setBatchHeaderFromBatchInfo((ACHBatch)localObject8, (ACHBatchInfo)localObject7);
          ((ACHBatch)localObject8).setTransactionIndex(localACHHistInfo.getCursorId());
          if (((ACHBatch)localObject8).getTaxFormID() != null) {
            try
            {
              TaxForm localTaxForm = getTaxForm(paramSecureUser, ((ACHBatch)localObject8).getTaxFormID(), paramHashMap);
              ((ACHBatch)localObject8).setTaxForm(localTaxForm);
            }
            catch (CSILException localCSILException) {}
          }
          if (((ACHBatch)localObject8).getCompanyID() == null)
          {
            if (localHashMap2.get(((ACHBatch)localObject8).getCoID()) == null)
            {
              localACHCompanyInfo = getACHCompanyInfo(paramSecureUser, null, ((ACHBatch)localObject8).getCoID());
              localHashMap2.put(((ACHBatch)localObject8).getCoID(), localACHCompanyInfo);
            }
            ACHCompanyInfo localACHCompanyInfo = (ACHCompanyInfo)localHashMap2.get(((ACHBatch)localObject8).getCoID());
            ((ACHBatch)localObject8).setCompanyID(localACHCompanyInfo.getCompACHId());
          }
        }
      }
    }
    return localObject6;
  }
  
  public ACHBatches getNextPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("NEXT");
    return getBatchesFromBatchHist(paramSecureUser, paramString1, paramString2, paramPagingContext, false, paramHashMap, 1);
  }
  
  public ACHBatches getPreviousPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("PREVIOUS");
    return getBatchesFromBatchHist(paramSecureUser, paramString1, paramString2, paramPagingContext, false, paramHashMap, 1);
  }
  
  public ACHBatches getPagedApprovalACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("FIRST");
    return getBatchesFromApprovals(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
  }
  
  public ACHBatches getNextApprovalACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("NEXT");
    return getBatchesFromApprovals(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
  }
  
  public ACHBatches getPreviousApprovalACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("PREVIOUS");
    return getBatchesFromApprovals(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
  }
  
  public ACHBatches getPagedCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("FIRST");
    return getBatchesFromBatchHist(paramSecureUser, paramString1, paramString2, paramPagingContext, false, paramHashMap, 2);
  }
  
  public ACHBatches getNextCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("NEXT");
    return getBatchesFromBatchHist(paramSecureUser, paramString1, paramString2, paramPagingContext, false, paramHashMap, 2);
  }
  
  public ACHBatches getPreviousCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("PREVIOUS");
    return getBatchesFromBatchHist(paramSecureUser, paramString1, paramString2, paramPagingContext, false, paramHashMap, 2);
  }
  
  public ACHBatches getRecACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    paramHashMap.put("PAGESIZE", "1000");
    return getBatchesFromBatchHist(paramSecureUser, paramString1, paramString2, localPagingContext, true, paramHashMap, 99);
  }
  
  public ACHBatch getACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    ACHBPWServices localACHBPWServices = null;
    try
    {
      localACHBPWServices = getACHHandler();
      Object localObject1 = new ACHBatchInfo();
      RecACHBatchInfo localRecACHBatchInfo = null;
      int i = -1;
      if ((paramACHBatch.getRecID() != null) && (paramACHBatch.getRecID().length() > 0)) {
        i = paramACHBatch.getStatusValue();
      }
      int j = 1;
      int k = 0;
      if ((paramACHBatch.getRecID() != null) && (paramACHBatch.getRecID().length() > 0)) {
        k = 1;
      }
      if ((paramACHBatch.getStatusValue() == 2) || (paramACHBatch.getStatusValue() == 11) || (paramACHBatch.getStatusValue() == 8) || (paramACHBatch.getStatusValue() == 9) || (paramACHBatch.getStatusValue() == 10)) {
        j = 0;
      }
      if ((j == 0) && (k != 0))
      {
        String str1 = (String)paramACHBatch.get("StartDate");
        if ((str1 != null) && (str1.length() > 0))
        {
          localObject2 = new DateTime();
          ((DateTime)localObject2).setFormat("yyyyMMdd");
          ((DateTime)localObject2).setDate(str1);
          ((DateTime)localObject2).setFormat(paramACHBatch.getDateFormat());
          paramACHBatch.setDate((DateTime)localObject2);
        }
        localObject2 = formatDate.format(paramACHBatch.getDateValue().getTime());
        localRecACHBatchInfo = new RecACHBatchInfo();
        localObject1 = localRecACHBatchInfo;
        localRecACHBatchInfo.setPmtsCount(paramACHBatch.getNumberPaymentsValue());
        localRecACHBatchInfo.setFrequency(BeansConverter.getBPWFrequency(paramACHBatch.getFrequencyValue()));
        localRecACHBatchInfo.setStartDate((String)localObject2);
        localRecACHBatchInfo.setBatchId(paramACHBatch.getRecID());
      }
      else
      {
        ((ACHBatchInfo)localObject1).setBatchId(paramACHBatch.getID());
      }
      ((ACHBatchInfo)localObject1).setBatchPageSize(99999L);
      try
      {
        if (localRecACHBatchInfo != null)
        {
          localRecACHBatchInfo = localACHBPWServices.getRecACHBatch(localRecACHBatchInfo);
          localObject1 = localRecACHBatchInfo;
        }
        else
        {
          localObject1 = localACHBPWServices.getACHBatch((ACHBatchInfo)localObject1);
        }
      }
      catch (RemoteException localRemoteException1)
      {
        localObject2 = localRemoteException1.toString();
        if ((localObject2 != null) && (((String)localObject2).indexOf("Server is not running!") > 0)) {
          throw new CSILException(25018);
        }
      }
      if ((localObject1 != null) && (((ACHBatchInfo)localObject1).getStatusCode() != 0))
      {
        DebugLog.log(Level.INFO, "*** BPW Error: " + ((ACHBatchInfo)localObject1).getStatusMsg());
        throw new CSILException(-1009, 16002, "", ((ACHBatchInfo)localObject1).getStatusMsg());
      }
      ACHPayees localACHPayees = new ACHPayees();
      if ((paramHashMap != null) && (paramHashMap.get("ACHPayees") != null)) {
        localACHPayees.addAll((ACHPayees)paramHashMap.get("ACHPayees"));
      }
      Object localObject2 = new ArrayList();
      HashMap localHashMap = new HashMap();
      Object localObject3;
      if (localObject1 != null)
      {
        localObject3 = ((ACHBatchInfo)localObject1).getRecords();
        for (int m = 0; m < localObject3.length; m++)
        {
          Object localObject4 = localObject3[m];
          if (j != 0) {
            localObject4.setPayeeId(null);
          }
          String str2 = localObject4.getPayeeId();
          ACHPayee localACHPayee = localACHPayees.getByID(str2);
          if ((str2 != null) && (localACHPayee == null) && (localHashMap.get(str2) == null))
          {
            ACHPayeeInfo localACHPayeeInfo = new ACHPayeeInfo();
            try
            {
              localACHPayeeInfo = localACHBPWServices.getACHPayee(str2);
            }
            catch (RemoteException localRemoteException2)
            {
              String str3 = localRemoteException2.toString();
              if ((str3 != null) && (str3.indexOf("Server is not running!") > 0)) {
                throw new CSILException(25018);
              }
            }
            if ((localACHPayeeInfo != null) && (localACHPayeeInfo.getStatusCode() == 0))
            {
              localHashMap.put(str2, "x");
              ((ArrayList)localObject2).add(new String(str2));
              localACHPayee = (ACHPayee)localACHPayees.create();
              BeansConverter.setPayeeFromPayeeInfo(localACHPayee, localACHPayeeInfo);
            }
          }
        }
        if (((ArrayList)localObject2).size() <= 0) {}
      }
      BeansConverter.setBatchFromBatchInfo(paramACHBatch, localACHPayees, (ACHBatchInfo)localObject1);
      if (paramACHBatch.getCompanyID() == null)
      {
        localObject3 = getACHCompanyInfo(paramSecureUser, null, paramACHBatch.getCoID());
        paramACHBatch.setCompanyID(((ACHCompanyInfo)localObject3).getCompACHId());
      }
      if ((j == 0) && (k != 0) && (i != -1)) {
        paramACHBatch.setStatus(i);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    return paramACHBatch;
  }
  
  public ACHBatch getRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    paramHashMap.put("Recurring", "true");
    return getACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
  }
  
  public ACHPayees getACHPayees(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return getACHPayees(paramSecureUser, "" + paramSecureUser.getBusinessID(), paramString, paramHashMap);
  }
  
  protected static void addPayeesToList(SecureUser paramSecureUser, ACHPayees paramACHPayees, Object[] paramArrayOfObject)
  {
    if ((paramArrayOfObject != null) && (paramArrayOfObject.length > 0)) {
      for (int i = 0; i < paramArrayOfObject.length; i++)
      {
        ACHPayeeInfo localACHPayeeInfo = (ACHPayeeInfo)paramArrayOfObject[i];
        if ((localACHPayeeInfo.getStatusCode() == 0) && ("Y".equalsIgnoreCase(localACHPayeeInfo.getManagedPayee())))
        {
          ACHPayee localACHPayee = (ACHPayee)paramACHPayees.createNoAdd();
          BeansConverter.setPayeeFromPayeeInfo(localACHPayee, localACHPayeeInfo);
          if ((localACHPayee.getSecurePayeeValue()) && (!("" + paramSecureUser.getProfileID()).equals(localACHPayee.getSubmittedBy()))) {
            localACHPayee.setDisplayAsSecurePayee(true);
          }
          if ((paramACHPayees.getByID(localACHPayee.getID()) == null) && ((localACHPayee.getPayeeGroupValue() != 1) || (("" + paramSecureUser.getProfileID()).equals(localACHPayee.getSubmittedBy())))) {
            paramACHPayees.add(localACHPayee);
          }
        }
      }
    }
  }
  
  public ACHPayees getACHPayees(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    int i = 16002;
    ACHBPWServices localACHBPWServices = null;
    ACHPayees localACHPayees = null;
    Object localObject1;
    Object localObject2;
    Object localObject4;
    Object localObject3;
    if ((paramHashMap != null) && (paramHashMap.get("PayeeIDs") != null))
    {
      try
      {
        localACHBPWServices = getACHHandler();
        localObject1 = (ArrayList)paramHashMap.get("PayeeIDs");
        localObject2 = new ACHPayeeInfo();
        localACHPayees = new ACHPayees();
        localObject4 = ((ArrayList)localObject1).iterator();
        while (((Iterator)localObject4).hasNext())
        {
          localObject3 = (String)((Iterator)localObject4).next();
          try
          {
            localObject2 = localACHBPWServices.getACHPayee((String)localObject3);
          }
          catch (RemoteException localRemoteException)
          {
            String str = localRemoteException.toString();
            if ((str != null) && (str.indexOf("Server is not running!") > 0)) {
              throw new CSILException(25018);
            }
          }
          if ((localObject2 != null) && (((ACHPayeeInfo)localObject2).getStatusCode() == 0))
          {
            ACHPayee localACHPayee = (ACHPayee)localACHPayees.create();
            BeansConverter.setPayeeFromPayeeInfo(localACHPayee, (ACHPayeeInfo)localObject2);
          }
        }
        i = 0;
      }
      catch (Throwable localThrowable1)
      {
        DebugLog.log("ACH.getACHPayee exception: " + localThrowable1.getMessage());
        localObject1 = localThrowable1.toString();
        if ((localObject1 != null) && (((String)localObject1).indexOf("Server is not running!") > 0)) {
          throw new CSILException(25018);
        }
      }
      finally
      {
        if (localACHBPWServices != null) {
          removeACHHandler(localACHBPWServices);
        }
      }
      if (i == 0) {
        return localACHPayees;
      }
      throw new CSILException(-1009, i);
    }
    try
    {
      localACHBPWServices = getACHHandler();
      ACHPayeeList localACHPayeeList = new ACHPayeeList();
      localACHPayeeList.setManagedPayee("Y");
      localACHPayeeList.setCustId(paramString1);
      if (paramString2 == null)
      {
        localACHPayeeList.setCompIdList(null);
      }
      else
      {
        localObject1 = new String[1];
        localObject1[0] = new String(paramString2);
        localACHPayeeList.setCompIdList((String[])localObject1);
      }
      localObject1 = new String[] { "Company" };
      localACHPayeeList.setPayeeGroupList((String[])localObject1);
      localACHPayeeList.setPayeeCategory("ACH_Payee");
      localACHPayeeList.setPageSize(99999L);
      localACHPayeeList = localACHBPWServices.getACHPayeeList(localACHPayeeList);
      localACHPayees = new ACHPayees();
      addPayeesToList(paramSecureUser, localACHPayees, localACHPayeeList.getPayees());
      localACHPayeeList = new ACHPayeeList();
      localACHPayeeList.setManagedPayee("Y");
      localACHPayeeList.setCustId(paramString1);
      localACHPayeeList.setPayeeCategory("ACH_Payee");
      localObject2 = new String[] { "User" };
      localACHPayeeList.setPayeeGroupList((String[])localObject2);
      localObject3 = new String[1];
      localObject3[0] = new String("" + paramSecureUser.getProfileID());
      localACHPayeeList.setSubmittedBy((String[])localObject3);
      localACHPayeeList = localACHBPWServices.getACHPayeeList(localACHPayeeList);
      addPayeesToList(paramSecureUser, localACHPayees, localACHPayeeList.getPayees());
      localACHPayeeList = new ACHPayeeList();
      localACHPayeeList.setManagedPayee("Y");
      localACHPayeeList.setCustId(paramString1);
      localACHPayeeList.setPayeeCategory("ACH_Payee");
      localObject4 = new String[] { "Business" };
      localACHPayeeList.setPayeeGroupList((String[])localObject4);
      localACHPayeeList.setPageSize(99999L);
      localACHPayeeList = localACHBPWServices.getACHPayeeList(localACHPayeeList);
      addPayeesToList(paramSecureUser, localACHPayees, localACHPayeeList.getPayees());
      i = 0;
    }
    catch (Throwable localThrowable2)
    {
      DebugLog.log("ACH.getACHPayee exception: " + localThrowable2.getMessage());
      localObject1 = localThrowable2.toString();
      if ((localObject1 != null) && (((String)localObject1).indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    if (i == 0) {
      return localACHPayees;
    }
    throw new CSILException(-1009, i);
  }
  
  public static void setPayeeInfoFromPayee(ACHPayeeInfo paramACHPayeeInfo, ACHPayee paramACHPayee)
  {
    String str1 = paramACHPayee.getName();
    if ((str1 != null) && (str1.length() > 22)) {
      str1 = str1.substring(0, 22);
    }
    paramACHPayeeInfo.setPayeeID(paramACHPayee.getID());
    paramACHPayeeInfo.setLogId(paramACHPayee.getTrackingID());
    paramACHPayeeInfo.setPayeeName(str1);
    paramACHPayeeInfo.setContactName(paramACHPayee.getBankName());
    paramACHPayeeInfo.setCompId(paramACHPayee.getCompanyID());
    if (!paramACHPayee.getSecurePayeeValue()) {
      paramACHPayeeInfo.setSecurePayee(0);
    } else if (paramACHPayee.getSecurePayeeValue() == true) {
      paramACHPayeeInfo.setSecurePayee(1);
    }
    Hashtable localHashtable = new Hashtable();
    paramACHPayeeInfo.setMemo(localHashtable);
    String str2 = "Company";
    if (paramACHPayee.getPayeeGroupValue() == 3) {
      str2 = "Business";
    } else if (paramACHPayee.getPayeeGroupValue() == 1) {
      str2 = "User";
    }
    paramACHPayeeInfo.setPayeeGroup(str2);
    paramACHPayeeInfo.setBankRTN(paramACHPayee.getRoutingNumber());
    paramACHPayeeInfo.setBankAcctId(paramACHPayee.getAccountNumber());
    String str3 = paramACHPayee.getUserAccountNumber();
    if ((str3 == null) || (str3.length() == 0)) {
      str3 = " ";
    }
    paramACHPayeeInfo.setPayAcct(str3);
    String str4;
    switch (paramACHPayee.getAccountTypeValue())
    {
    case 1: 
    default: 
      str4 = "Checking";
      break;
    case 2: 
      str4 = "Savings";
      break;
    case 3: 
      str4 = "Loan";
      break;
    case 4: 
      str4 = "Ledger";
    }
    paramACHPayeeInfo.setBankAcctType(str4);
    if (paramACHPayee.getPrenoteStatus().equalsIgnoreCase("PRENOTE_REQUESTED"))
    {
      paramACHPayeeInfo.setDoPrenote(true);
      paramACHPayee.setPrenoteStatus("PRENOTE_PENDING");
    }
    else if (paramACHPayee.getPrenoteStatus().equalsIgnoreCase("PRENOTE_NOT_REQUIRED"))
    {
      paramACHPayeeInfo.setDoPrenote(false);
    }
    if ((paramACHPayee.getNickName() == null) || (paramACHPayee.getNickName().length() == 0)) {
      paramACHPayee.setNickName(paramACHPayee.getName());
    }
    paramACHPayeeInfo.setNickName(paramACHPayee.getNickName());
    if ("ACHCompany".equalsIgnoreCase(paramACHPayee.getScope())) {
      paramACHPayeeInfo.setManagedPayee("Y");
    } else if ("ACHTemplate".equalsIgnoreCase(paramACHPayee.getScope())) {
      paramACHPayeeInfo.setManagedPayee("Template");
    } else if ("ACHBatch".equalsIgnoreCase(paramACHPayee.getScope())) {
      paramACHPayeeInfo.setManagedPayee("N");
    }
    if (paramACHPayee.getTrackingID() == null) {
      paramACHPayee.setTrackingID(TrackingIDGenerator.GetNextID());
    }
    if (paramACHPayee.getPrenoteDemand() != null) {
      if ("Credit".equals(paramACHPayee.getPrenoteDemand())) {
        paramACHPayeeInfo.setPrenoteDemand("ACH_PRENOTE_DEMAND_CREDIT");
      } else if ("Debit".equals(paramACHPayee.getPrenoteDemand())) {
        paramACHPayeeInfo.setPrenoteDemand("ACH_PRENOTE_DEMAND_DEBIT");
      } else {
        paramACHPayeeInfo.setPrenoteDemand("ACH_PRENOTE_DEMAND_BOTH");
      }
    }
    paramACHPayeeInfo.setLogId(paramACHPayee.getTrackingID());
    paramACHPayeeInfo.setSubmittedBy(paramACHPayee.getSubmittedBy());
  }
  
  public ACHPayee addACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {
    int i = 16002;
    ACHPayeeInfo localACHPayeeInfo = null;
    ACHBPWServices localACHBPWServices = null;
    String str1 = "";
    Object localObject1;
    if ((paramHashMap != null) && (paramHashMap.get("ACHPayees") != null) && ((paramHashMap.get("ACHPayees") instanceof ACHPayees)) && (paramACHPayee == null)) {
      try
      {
        localACHBPWServices = getACHHandler();
        ACHPayees localACHPayees = (ACHPayees)paramHashMap.get("ACHPayees");
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = localACHPayees.iterator();
        String str2 = (String)paramHashMap.get("ACHSECCode");
        while (localIterator.hasNext())
        {
          localObject1 = (ACHPayee)localIterator.next();
          localACHPayeeInfo = new ACHPayeeInfo();
          ((ACHPayee)localObject1).setSubmittedBy(Integer.toString(paramSecureUser.getProfileID()));
          setPayeeInfoFromPayee(localACHPayeeInfo, (ACHPayee)localObject1);
          if (str2 != null) {
            localACHPayeeInfo.setPrenoteSecCode(str2);
          }
          populateOBOAgentInfo(paramSecureUser, localACHPayeeInfo);
          localACHPayeeInfo.setPayeeID(null);
          localArrayList.add(localACHPayeeInfo);
        }
        ACHPayeeInfo[] arrayOfACHPayeeInfo = (ACHPayeeInfo[])localArrayList.toArray(new ACHPayeeInfo[0]);
        arrayOfACHPayeeInfo = localACHBPWServices.addACHPayee(arrayOfACHPayeeInfo);
        if (arrayOfACHPayeeInfo != null) {
          for (int j = 0; j < arrayOfACHPayeeInfo.length; j++)
          {
            localACHPayeeInfo = arrayOfACHPayeeInfo[j];
            localObject1 = (ACHPayee)localACHPayees.get(j);
            if (localACHPayeeInfo != null)
            {
              i = localACHPayeeInfo.getStatusCode();
              str1 = localACHPayeeInfo.getStatusMsg();
            }
            if (i == 0) {
              BeansConverter.setPayeeFromPayeeInfo((ACHPayee)localObject1, localACHPayeeInfo);
            }
          }
        }
      }
      catch (Throwable localThrowable1)
      {
        DebugLog.log("ACH.addACHPayee exception: " + localThrowable1.getMessage());
        localObject1 = localThrowable1.toString();
        if ((localObject1 != null) && (((String)localObject1).indexOf("Server is not running!") > 0)) {
          throw new CSILException(25018);
        }
      }
      finally
      {
        if (localACHBPWServices != null) {
          removeACHHandler(localACHBPWServices);
        }
      }
    } else {
      try
      {
        localACHBPWServices = getACHHandler();
        localACHPayeeInfo = new ACHPayeeInfo();
        paramACHPayee.setSubmittedBy(Integer.toString(paramSecureUser.getProfileID()));
        setPayeeInfoFromPayee(localACHPayeeInfo, paramACHPayee);
        populateOBOAgentInfo(paramSecureUser, localACHPayeeInfo);
        localACHPayeeInfo.setPayeeID(null);
        localACHPayeeInfo = localACHBPWServices.addACHPayee(localACHPayeeInfo);
        if (localACHPayeeInfo != null)
        {
          i = localACHPayeeInfo.getStatusCode();
          str1 = localACHPayeeInfo.getStatusMsg();
        }
        if (i == 0) {
          BeansConverter.setPayeeFromPayeeInfo(paramACHPayee, localACHPayeeInfo);
        }
      }
      catch (Throwable localThrowable2)
      {
        DebugLog.log("ACH.addACHPayee exception: " + localThrowable2.getMessage());
        localObject1 = localThrowable2.toString();
        if ((localObject1 != null) && (((String)localObject1).indexOf("Server is not running!") > 0)) {
          throw new CSILException(25018);
        }
      }
      finally
      {
        if (localACHBPWServices != null) {
          removeACHHandler(localACHBPWServices);
        }
      }
    }
    if (i == 0) {
      return paramACHPayee;
    }
    throw new CSILException(-1009, 16002, "", str1);
  }
  
  public void deleteACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {
    int i = 16002;
    ACHPayeeInfo localACHPayeeInfo = null;
    ACHBPWServices localACHBPWServices = null;
    String str1 = "";
    try
    {
      localACHBPWServices = getACHHandler();
      localACHPayeeInfo = localACHBPWServices.getACHPayee(paramACHPayee.getID());
      paramACHPayee.setSubmittedBy(Integer.toString(paramSecureUser.getProfileID()));
      localACHPayeeInfo.setSubmittedBy(paramACHPayee.getSubmittedBy());
      if ((localACHPayeeInfo != null) && (localACHPayeeInfo.getStatusCode() == 0))
      {
        populateOBOAgentInfo(paramSecureUser, localACHPayeeInfo);
        localACHPayeeInfo = localACHBPWServices.canACHPayee(localACHPayeeInfo);
        i = localACHPayeeInfo.getStatusCode();
        str1 = localACHPayeeInfo.getStatusMsg();
      }
      else
      {
        i = localACHPayeeInfo.getStatusCode();
        str1 = localACHPayeeInfo.getStatusMsg();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACH.deleteACHPayee exception: " + localThrowable.getMessage());
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    if (i != 0) {
      throw new CSILException(-1009, 16002, "", str1);
    }
  }
  
  public ACHPayee modifyACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {
    int i = 16002;
    ACHPayeeInfo localACHPayeeInfo = null;
    ACHBPWServices localACHBPWServices = null;
    String str1 = "";
    try
    {
      localACHBPWServices = getACHHandler();
      localACHPayeeInfo = localACHBPWServices.getACHPayee(paramACHPayee.getID());
      if ((localACHPayeeInfo != null) && (localACHPayeeInfo.getStatusCode() == 0))
      {
        String str2 = paramACHPayee.getSubmittedBy();
        paramACHPayee.setSubmittedBy(Integer.toString(paramSecureUser.getProfileID()));
        localACHPayeeInfo.setSubmittedBy(paramACHPayee.getSubmittedBy());
        setPayeeInfoFromPayee(localACHPayeeInfo, paramACHPayee);
        paramACHPayee.setSubmittedBy(str2);
        populateOBOAgentInfo(paramSecureUser, localACHPayeeInfo);
        localACHPayeeInfo = localACHBPWServices.modACHPayee(localACHPayeeInfo);
        i = localACHPayeeInfo.getStatusCode();
        str1 = localACHPayeeInfo.getStatusMsg();
        if (i == 0) {
          localACHPayeeInfo = localACHBPWServices.getACHPayee(paramACHPayee.getID());
        }
      }
      else if (localACHPayeeInfo != null)
      {
        i = localACHPayeeInfo.getStatusCode();
        str1 = localACHPayeeInfo.getStatusMsg();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACH.modifyACHPayee exception: " + localThrowable.getMessage());
      String str3 = localThrowable.toString();
      if ((str3 != null) && (str3.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    if (i == 0) {
      return paramACHPayee;
    }
    throw new CSILException(-1009, 16002, "", str1);
  }
  
  public ACHBatches getAllBatchesWithPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.getACHBatches", 19003);
  }
  
  public void deletePayeeFromBatch(SecureUser paramSecureUser, ACHPayee paramACHPayee, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.getACHBatches", 19003);
  }
  
  public Object exportACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    ACHBPWServices localACHBPWServices = getACHHandler();
    String str1 = "unable to export ACH file";
    int i = 16002;
    String str2 = "";
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    try
    {
      paramHashMap.put("ExportOnly", "TRUE");
      addACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
      ACHBatchInfo localACHBatchInfo = (ACHBatchInfo)paramHashMap.get("ACHBatchInfo");
      if (localACHBatchInfo != null)
      {
        populateOBOAgentInfo(paramSecureUser, localACHBatchInfo);
        localACHBatchInfo = localACHBPWServices.exportACHBatch(localACHBatchInfo);
        if (localACHBatchInfo != null)
        {
          i = localACHBatchInfo.getStatusCode();
          str2 = localACHBatchInfo.getStatusMsg();
          if (i == 0)
          {
            str1 = localACHBatchInfo.getBatchContent();
          }
          else
          {
            DebugLog.log(Level.INFO, "*** BPW Error: " + str2);
            i = 16002;
            throw new CSILException(-1009, i, "", str2);
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACHService.addACHCompany exception: " + localThrowable.getMessage());
      String str3 = localThrowable.toString();
      if ((str3 != null) && (str3.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
      str1 = str1 + "\n" + localThrowable.toString();
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    return str1;
  }
  
  public void uploadACHFile(SecureUser paramSecureUser, StringBuffer paramStringBuffer, HashMap paramHashMap)
    throws CSILException
  {
    ACHBPWServices localACHBPWServices = null;
    ACHFileInfo localACHFileInfo = new ACHFileInfo();
    String str1 = "ACHService.uploadACHFile";
    try
    {
      localACHBPWServices = getACHHandler();
      localACHFileInfo.setFilePageSize(-1L);
      if ((paramHashMap != null) && (paramHashMap.get("TRACKING_ID") != null)) {
        localACHFileInfo.setLogId((String)paramHashMap.get("TRACKING_ID"));
      }
      localACHFileInfo.setFileContent(paramStringBuffer.toString());
      localACHFileInfo.setFileSize(paramStringBuffer.length() / this.ACH_RECORD_LENGTH);
      localACHFileInfo.setFileCursor(0L);
      localACHFileInfo.setSubmitDate(formatDate.format(Calendar.getInstance().getTime()));
      localACHFileInfo.setDueDate(formatDate.format(Calendar.getInstance().getTime()));
      localACHFileInfo.setChunkId("1");
      String str2 = Integer.toString(paramSecureUser.getProfileID());
      localACHFileInfo.setSubmittedBy(str2);
      localACHFileInfo.setCustomerId("" + paramSecureUser.getBusinessID());
      localACHFileInfo.setUploadFileName((String)paramHashMap.get("FILE_NAME"));
      populateOBOAgentInfo(paramSecureUser, localACHFileInfo);
      localACHFileInfo = localACHBPWServices.addACHFile(localACHFileInfo);
      if (paramHashMap != null) {
        paramHashMap.put("ACHFileId", localACHFileInfo.getFileId());
      }
    }
    catch (Throwable localThrowable1)
    {
      DebugLog.log("ACHService.uploadACHFile exception: " + localThrowable1.getMessage());
      String str3 = localThrowable1.toString();
      if ((str3 != null) && (str3.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
      FFSException localFFSException = null;
      if ((localThrowable1 instanceof FFSException)) {
        localFFSException = (FFSException)localThrowable1;
      }
      if ((localThrowable1 instanceof RemoteException))
      {
        Throwable localThrowable2 = ((RemoteException)localThrowable1).detail;
        if ((localThrowable2 instanceof ACHException))
        {
          ACHException localACHException = (ACHException)localThrowable2;
          if ((localACHException.getChildException() instanceof FFSException)) {
            localFFSException = (FFSException)localACHException.getChildException();
          }
        }
      }
      if ((localFFSException != null) && (localFFSException.getMessage() != null))
      {
        str3 = localFFSException.getMessage();
      }
      else
      {
        if (str3.indexOf("at com.ffusion.") > 0) {
          str3 = str3.substring(0, str3.indexOf("at com.ffusion."));
        }
        if (str3.indexOf("com.ffusion.msgbroker.interfaces.MBException:") >= 0) {}
        for (str3 = str3.substring(str3.indexOf("com.ffusion.msgbroker.interfaces.MBException:") + "com.ffusion.msgbroker.interfaces.MBException:".length()); str3.indexOf("com.ffusion.ffs.interfaces.FFSException:") >= 0; str3 = str3.substring(str3.indexOf("com.ffusion.ffs.interfaces.FFSException:") + "com.ffusion.ffs.interfaces.FFSException:".length())) {}
        while (str3.indexOf("failed:") >= 0) {
          str3 = str3.substring(str3.indexOf("failed:") + "failed:".length());
        }
        while ((str3.indexOf(":") == 0) || (str3.indexOf(" ") == 0)) {
          str3 = str3.substring(1);
        }
      }
      paramHashMap.put("FileUploadErrorMessage", str3);
      ACHAdapter.createFMLogRecordForACH(paramHashMap, "Failed");
      return;
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    if ((localACHFileInfo != null) && (localACHFileInfo.getStatusCode() != 0))
    {
      DebugLog.log("ACHService.uploadACHFile failed: " + localACHFileInfo.getStatusMsg());
      paramHashMap.put("FileUploadErrorMessage", localACHFileInfo.getStatusMsg());
      ACHAdapter.createFMLogRecordForACH(paramHashMap, "Failed");
      return;
    }
    ACHAdapter.createFMLogRecordForACH(paramHashMap, "Complete");
  }
  
  protected ACHBatches filterACHBatchesInReport(SecureUser paramSecureUser, ACHBatches paramACHBatches, HashMap paramHashMap)
  {
    return paramACHBatches;
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, String paramString, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACHService.getReportData";
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
      str3 = "Missing report options";
      DebugLog.log(str3);
      throw new CSILException(str1, 21007, str3);
    }
    String str3 = ((Properties)localObject2).getProperty("REPORTTYPE");
    if (str3 == null)
    {
      localObject3 = "Report type not specified";
      DebugLog.log((String)localObject3);
      throw new CSILException(str1, 21007, (String)localObject3);
    }
    String str2;
    if ((str3.equals("Completed ACH Payments")) || (str3.equals("ACH Participant Prenote Report")))
    {
      str2 = "ACHBatch";
    }
    else if (str3.equals("Total Tax Payments"))
    {
      str2 = "TaxPayment";
    }
    else if (str3.equals("Total Child Support Payments"))
    {
      str2 = "ChildSupportPayment";
    }
    else
    {
      if (str3.equals("CB ACH File Upload")) {
        return a(paramSecureUser, paramReportCriteria, paramHashMap);
      }
      localObject3 = "Report type is not valid";
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
      str4 = "Unable to calculate the date ranges. A report cannot be run.";
      DebugLog.log(str4);
      if (localCSILException.getCode() == -1009) {
        throw new CSILException(str1, localCSILException.getServiceError(), str4);
      }
      throw new CSILException(str1, localCSILException.getCode(), str4);
    }
    DateTime localDateTime1 = null;
    String str4 = localProperties.getProperty("StartDate");
    if ((str4 != null) && (str4.length() > 0)) {
      try
      {
        localDateTime1 = new DateTime(DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").parse(str4), Locale.getDefault(), "MM/dd/yyyy HH:mm:ss");
      }
      catch (Exception localException1) {}
    } else {
      paramReportCriteria.setHiddenSearchCriterion("StartDate", true);
    }
    DateTime localDateTime2 = null;
    String str5 = localProperties.getProperty("EndDate");
    if ((str5 != null) && (str5.length() > 0)) {
      try
      {
        localDateTime2 = new DateTime(DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").parse(str5), Locale.getDefault(), "MM/dd/yyyy HH:mm:ss");
      }
      catch (Exception localException2) {}
    } else {
      paramReportCriteria.setHiddenSearchCriterion("EndDate", true);
    }
    PagingContext localPagingContext = new PagingContext(localDateTime1, localDateTime2);
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    paramHashMap.put("PAGESIZE", "250");
    paramHashMap.put("ACH_VIEW", "ALL");
    paramHashMap.put("IncludePrenote", "ALL");
    paramHashMap.put("ACH_TYPE", str2);
    paramString = getSearchCriteria(paramReportCriteria.getSearchCriteria(), "CompanyID", null);
    ACHCompanyInfo localACHCompanyInfo = getACHCompanyInfo(paramSecureUser, paramString, null);
    double d = 0.0D;
    Currency localCurrency = new Currency(new BigDecimal(d), Locale.getDefault());
    int i = paramSecureUser.getBusinessID();
    if (str3.equals("ACH Participant Prenote Report"))
    {
      if (null != localACHCompanyInfo) {
        paramString = localACHCompanyInfo.getCompId();
      }
      str6 = localProperties.getProperty("PrenoteStatus");
      ACHPayeeList localACHPayeeList = getACHPayList(paramSecureUser, "" + i, paramString, str6, str4, str5, paramHashMap);
      localReportResult = getACHPayeePrenoteReport(paramSecureUser, paramReportCriteria, localACHPayeeList, localCurrency, Locale.getDefault(), paramHashMap);
      return localReportResult;
    }
    String str6 = localProperties.getProperty("PendingCompleted");
    if ((str6 != null) && (str6.length() > 0)) {
      if (str6.equalsIgnoreCase("Pending")) {
        paramHashMap.put("ACH_STATUS", "ACH_STATUS_PENDING");
      } else if (str6.equalsIgnoreCase("Completed")) {
        paramHashMap.put("ACH_STATUS", "ACH_STATUS_COMPLETED");
      }
    }
    if (null != localACHCompanyInfo) {
      paramHashMap.put("CompanyID", localACHCompanyInfo.getCompId());
    }
    ACHBatches localACHBatches1 = getPagedACHBatchHistories(paramSecureUser, localPagingContext, paramHashMap);
    localACHBatches1.setSortedBy("DATE");
    StringBuffer localStringBuffer = new StringBuffer();
    boolean bool = false;
    bool = addFilter(localProperties, localStringBuffer, "BatchType", "STANDARDENTRYCLASSCODE", "=", bool);
    if (bool) {
      localACHBatches1.setFilter(localStringBuffer.toString());
    }
    localACHBatches1 = filterACHBatchesInReport(paramSecureUser, localACHBatches1, paramHashMap);
    ACHBatches localACHBatches2 = new ACHBatches();
    Iterator localIterator1 = localACHBatches1.iterator();
    String str7 = localProperties.getProperty("AmountFrom");
    String str8 = localProperties.getProperty("AmountTo");
    ACHCompanies localACHCompanies = (ACHCompanies)paramHashMap.get("ACHCOMPANIES");
    while (localIterator1.hasNext())
    {
      localObject4 = (ACHBatch)localIterator1.next();
      Object localObject6;
      if ((str7 != null) || (str8 != null))
      {
        localObject5 = ((ACHBatch)localObject4).getAmountValue().getAmountValue();
        localObject6 = new Currency();
        if ((str7 != null) && (str7.trim().length() > 0))
        {
          ((Currency)localObject6).fromString(str7);
          if (((BigDecimal)localObject5).compareTo(((Currency)localObject6).getAmountValue()) < 0) {
            continue;
          }
        }
        if ((str8 != null) && (str8.trim().length() > 0))
        {
          ((Currency)localObject6).fromString(str8);
          if (((BigDecimal)localObject5).compareTo(((Currency)localObject6).getAmountValue()) > 0) {
            continue;
          }
        }
      }
      if (localACHCompanies != null)
      {
        localObject5 = localACHCompanies.getByID(((ACHBatch)localObject4).getCoID());
        if ((localObject5 != null) && ((!((ACHCompany)localObject5).getActiveValue()) || (!((ACHCompany)localObject5).getACHPaymentEntitledValue()))) {}
      }
      else
      {
        localObject5 = localProperties.getProperty("BatchStatus");
        if ((localObject5 == null) || (((String)localObject5).equals("2") ? ((ACHBatch)localObject4).getStatusValue() == 2 : ((String)localObject5).equals("8") ? (((ACHBatch)localObject4).getStatusValue() == 8) && (((ACHBatch)localObject4).getStatusValue() == 9) : ((String)localObject5).equals("10") ? ((ACHBatch)localObject4).getStatusValue() == 10 : ((String)localObject5).equals("5") ? ((ACHBatch)localObject4).getStatusValue() == 5 : (!((String)localObject5).equals("6")) || (((ACHBatch)localObject4).getStatusValue() == 6) || (((ACHBatch)localObject4).getStatusValue() == 19) || (((ACHBatch)localObject4).getStatusValue() == 20) || (((ACHBatch)localObject4).getStatusValue() == 17) || (((ACHBatch)localObject4).getStatusValue() == 16) || (((ACHBatch)localObject4).getStatusValue() == 7) || (((ACHBatch)localObject4).getStatusValue() == 14) || (((ACHBatch)localObject4).getStatusValue() == 18)))
        {
          try
          {
            localObject6 = null;
            if ((((ACHBatch)localObject4).getRecID() != null) && (((ACHBatch)localObject4).getRecID().length() > 0) && (((ACHBatch)localObject4).getRecID().equals(((ACHBatch)localObject4).getID()))) {
              localObject6 = ((ACHBatch)localObject4).getProcessedOnDateValue();
            }
            localObject4 = getACHBatch(paramSecureUser, (ACHBatch)localObject4, paramHashMap);
            int j = 1;
            if ((((ACHBatch)localObject4).getStatusValue() == 2) || (((ACHBatch)localObject4).getStatusValue() == 11) || (((ACHBatch)localObject4).getStatusValue() == 8) || (((ACHBatch)localObject4).getStatusValue() == 9) || (((ACHBatch)localObject4).getStatusValue() == 10)) {
              j = 0;
            }
            if ((localObject6 != null) && (j == 0)) {
              ((ACHBatch)localObject4).setDate((DateTime)localObject6);
            }
          }
          catch (Exception localException3) {}
          localACHBatches2.add(localObject4);
          d += ((ACHBatch)localObject4).getAmountValue().doubleValue();
        }
      }
    }
    Object localObject4 = paramReportCriteria.getSortCriteria();
    Object localObject5 = new StringBuffer();
    if ((localObject4 != null) && (((ReportSortCriteria)localObject4).size() > 0))
    {
      ((ReportSortCriteria)localObject4).setSortedBy("ORDINAL");
      Iterator localIterator2 = ((ReportSortCriteria)localObject4).iterator();
      while (localIterator2.hasNext())
      {
        ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localIterator2.next();
        String str9 = localReportSortCriterion.getName();
        if ((str9 != null) && (str9.trim().length() != 0))
        {
          String str10 = localReportSortCriterion.getAsc() ? "ASC" : "DESC";
          if (((StringBuffer)localObject5).length() > 0) {
            ((StringBuffer)localObject5).append(", ");
          }
          ((StringBuffer)localObject5).append(str9.trim());
          ((StringBuffer)localObject5).append(" ");
          ((StringBuffer)localObject5).append(str10);
        }
      }
      localACHBatches2.setSortedBy(((StringBuffer)localObject5).toString());
    }
    ReportResult localReportResult = getACHReport(paramSecureUser, paramReportCriteria, localACHBatches2, localCurrency, Locale.getDefault());
    return localReportResult;
  }
  
  protected ReportResult getACHReport(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, ACHBatches paramACHBatches, Currency paramCurrency, Locale paramLocale)
    throws CSILException
  {
    String str1 = "ACHService.GetACHReport";
    ReportResult localReportResult = new ReportResult(paramLocale);
    int i = 0;
    int j = Integer.parseInt(getSearchCriteria(paramReportCriteria.getReportOptions(), "MAXDISPLAYSIZE", String.valueOf(2147483647)));
    int k = 0;
    String str2 = getSearchCriteria(paramReportCriteria.getSearchCriteria(), "AmountFrom", "");
    paramReportCriteria.setHiddenSearchCriterion("AmountFrom", str2.equals(""));
    String str3 = getSearchCriteria(paramReportCriteria.getSearchCriteria(), "AmountTo", "");
    paramReportCriteria.setHiddenSearchCriterion("AmountTo", str3.equals(""));
    String str4 = getSearchCriteria(paramReportCriteria.getSearchCriteria(), "CompanyID", "");
    if (str4.equals("")) {
      paramReportCriteria.setDisplayValue("CompanyID", ReportConsts.getText(10028, paramLocale));
    } else {
      paramReportCriteria.setDisplayValue("CompanyID", getACHCompanyInfo(paramSecureUser, str4, null).getCompName());
    }
    String str5 = getSearchCriteria(paramReportCriteria.getSearchCriteria(), "BatchStatus", "");
    if (str5.equals("")) {
      paramReportCriteria.setDisplayValue("BatchStatus", ReportConsts.getText(10018, paramLocale));
    } else {
      paramReportCriteria.setDisplayValue("BatchStatus", ResourceUtil.getString("ACHStatus" + str5, "com.ffusion.beans.ach.resources", paramLocale));
    }
    String str6 = getSearchCriteria(paramReportCriteria.getSearchCriteria(), "BatchType", "");
    if (str6.equals("")) {
      paramReportCriteria.setDisplayValue("BatchType", ReportConsts.getText(10033, paramLocale));
    } else {
      paramReportCriteria.setDisplayValue("BatchType", ResourceUtil.getString("ACHClassCodeDesc" + str6, "com.ffusion.beans.ach.resources", paramLocale));
    }
    try
    {
      localReportResult.init(paramReportCriteria);
      Properties localProperties = paramReportCriteria.getSearchCriteria();
      String str7 = (String)localProperties.get("ACHFileDetailLevel");
      if ((str7 == null) || (str7.trim().length() == 0)) {
        str7 = "Detail Entry";
      }
      HashMap localHashMap1 = new HashMap();
      Iterator localIterator = paramACHBatches.iterator();
      if (localIterator.hasNext())
      {
        while (localIterator.hasNext())
        {
          localObject1 = (ACHBatch)localIterator.next();
          if (localHashMap1.get(((ACHBatch)localObject1).getCoID()) == null) {
            localHashMap1.put(((ACHBatch)localObject1).getCoID(), new ArrayList());
          }
          localObject2 = (ArrayList)localHashMap1.get(((ACHBatch)localObject1).getCoID());
          ((ArrayList)localObject2).add(localObject1);
        }
        localIterator = localHashMap1.keySet().iterator();
        while (localIterator.hasNext())
        {
          localObject1 = (String)localIterator.next();
          localObject2 = (ArrayList)localHashMap1.get(localObject1);
          int m = 1;
          localObject3 = null;
          int n = 0;
          for (int i1 = 0; i1 < ((ArrayList)localObject2).size(); i1++)
          {
            ACHBatch localACHBatch = (ACHBatch)((ArrayList)localObject2).get(i1);
            if (m != 0)
            {
              m = 0;
              localObject3 = new ReportResult(localReportResult.getLocale());
              localReportResult.addSubReport((ReportResult)localObject3);
              ReportHeading localReportHeading = new ReportHeading(localReportResult.getLocale());
              localReportHeading.setLabel(ReportConsts.getText(2059, localReportResult.getLocale()) + " " + localACHBatch.getCoName() + " / " + localACHBatch.getCompanyID());
              ((ReportResult)localObject3).setHeading(localReportHeading);
              localObject3 = new ReportResult(localReportResult.getLocale());
              localReportResult.addSubReport((ReportResult)localObject3);
              localACHBatch.set("IsFirst", "true");
              n = 0;
            }
            else if (str7.equals("Detail Entry"))
            {
              localObject3 = new ReportResult(localReportResult.getLocale());
              localReportResult.addSubReport((ReportResult)localObject3);
              n = 0;
            }
            else
            {
              n++;
            }
            addACHSubReport(paramSecureUser, str7, paramReportCriteria, (ReportResult)localObject3, localReportResult, localACHBatch, n, i, j);
          }
        }
      }
      Object localObject1 = new ReportResult(paramLocale);
      localReportResult.addSubReport((ReportResult)localObject1);
      Object localObject2 = null;
      ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
      localReportDataDimensions.setNumColumns(1);
      localReportDataDimensions.setNumRows(1);
      ((ReportResult)localObject1).setDataDimensions(localReportDataDimensions);
      localObject2 = new ReportColumn(paramLocale);
      ((ReportColumn)localObject2).setDataType("java.lang.String");
      ((ReportColumn)localObject2).setWidthAsPercent(100);
      ((ReportResult)localObject1).addColumn((ReportColumn)localObject2);
      localObject2 = null;
      Object localObject3 = new ReportRow(paramLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
      ReportDataItem localReportDataItem = null;
      ((ReportRow)localObject3).setDataItems(localReportDataItems);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(2503, paramLocale));
      localReportDataItem = null;
      localReportDataItems = null;
      ((ReportResult)localObject1).addRow((ReportRow)localObject3);
      localObject3 = null;
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
        HashMap localHashMap2 = new HashMap();
        localHashMap2.put("TRUNCATED", new Integer(i));
        localReportResult.setProperties(localHashMap2);
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
  
  protected static void addACHSubReport(SecureUser paramSecureUser, String paramString, ReportCriteria paramReportCriteria, ReportResult paramReportResult1, ReportResult paramReportResult2, ACHBatch paramACHBatch, int paramInt1, int paramInt2, int paramInt3)
    throws CSILException, RptException
  {
    if (paramInt2 == paramInt3) {
      return;
    }
    String str1 = "ACHService.addACHSubReport";
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str2 = localProperties.getProperty("DATEFORMAT");
    String str3 = localProperties.getProperty("TIMEFORMAT");
    Locale localLocale = paramReportResult2.getLocale();
    if ((paramString.equals("Detail Entry")) || (paramACHBatch.get("IsFirst") != null))
    {
      if (paramString.equals("Detail Entry"))
      {
        localObject1 = new ReportHeading(localLocale);
        ((ReportHeading)localObject1).setLabel(ReportConsts.getColumnName(2472, localLocale));
        paramReportResult1.setSectionHeading((ReportHeading)localObject1);
      }
      localObject1 = new ReportDataDimensions(localLocale);
      ((ReportDataDimensions)localObject1).setNumColumns(10);
      ((ReportDataDimensions)localObject1).setNumRows(-1);
      paramReportResult1.setDataDimensions((ReportDataDimensions)localObject1);
      a(paramReportResult1, 1850, "java.lang.String", 10, null);
      a(paramReportResult1, 1868, "java.lang.String", 10, null);
      a(paramReportResult1, 1869, "com.ffusion.util.beans.DateTime", 8, null);
      a(paramReportResult1, 2040, "java.lang.String", 5, null);
      a(paramReportResult1, 1853, "java.lang.String", 8, null);
      a(paramReportResult1, 1854, "com.ffusion.beans.Currency", 10, "RIGHT");
      a(paramReportResult1, 1855, "com.ffusion.beans.Currency", 10, "RIGHT");
      a(paramReportResult1, 1867, "java.lang.String", 10, null);
      a(paramReportResult1, 1878, "java.lang.String", 10, null);
      a(paramReportResult1, 1856, "java.lang.String", 10, null);
    }
    Object localObject1 = new ReportRow(localLocale);
    if (paramInt1 % 2 == 1) {
      ((ReportRow)localObject1).set("CELLBACKGROUND", "reportDataShaded");
    }
    ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
    ((ReportRow)localObject1).setDataItems(localReportDataItems);
    ReportDataItem localReportDataItem = localReportDataItems.add();
    setDataItem(localReportDataItem, paramACHBatch.getName());
    localReportDataItem = localReportDataItems.add();
    if (paramACHBatch.getSubmitDateValue() != null)
    {
      localDateTime = new DateTime();
      localDateTime.setTime(paramACHBatch.getSubmitDateValue().getTime());
      localObject2 = null;
      if (str2 == null) {
        localObject2 = DateFormatUtil.getFormatter(null);
      } else {
        localObject2 = DateFormatUtil.getFormatter(str2 + (str3 != null ? " " + str3 : ""), localLocale);
      }
      setDataItem(localReportDataItem, ((DateFormat)localObject2).format(localDateTime.getTime()));
    }
    else
    {
      localDateTime = new DateTime();
      localDateTime.set(5, 0);
      localDateTime.set(2, 0);
      localDateTime.set(1, 0);
      setDataItem(localReportDataItem, localDateTime);
    }
    localReportDataItem = localReportDataItems.add();
    DateTime localDateTime = paramACHBatch.getDateValue();
    if (localDateTime != null)
    {
      setDataItem(localReportDataItem, localDateTime);
    }
    else
    {
      localObject2 = new DateTime();
      ((DateTime)localObject2).set(5, 0);
      ((DateTime)localObject2).set(2, 0);
      ((DateTime)localObject2).set(1, 0);
      setDataItem(localReportDataItem, localObject2);
    }
    localReportDataItem = localReportDataItems.add();
    setDataItem(localReportDataItem, paramACHBatch.getStandardEntryClassCode());
    localReportDataItem = localReportDataItems.add();
    setDataItem(localReportDataItem, paramACHBatch.getNumberEntries());
    localReportDataItem = localReportDataItems.add();
    setDataItem(localReportDataItem, paramACHBatch.getTotalCreditAmountValue());
    localReportDataItem = localReportDataItems.add();
    setDataItem(localReportDataItem, paramACHBatch.getTotalDebitAmountValue());
    localReportDataItem = localReportDataItems.add();
    Object localObject2 = paramACHBatch.getCategory();
    if (localObject2 == null) {
      localObject2 = "";
    }
    if (((String)localObject2).equals("ACH_BATCH_PAYMENT")) {
      localObject2 = ReportConsts.getText(2020, localLocale);
    } else if (((String)localObject2).equals("ACH_BATCH_TAX")) {
      localObject2 = ReportConsts.getText(2021, localLocale);
    } else if (((String)localObject2).equals("ACH_BATCH_CHILD_SUPPORT")) {
      localObject2 = ReportConsts.getText(2022, localLocale);
    } else if (((String)localObject2).equals("ACH_BATCH_PRENOTE")) {
      localObject2 = ReportConsts.getText(2023, localLocale);
    } else if (((String)localObject2).equals("ACH_BATCH_REVERSAL")) {
      localObject2 = ReportConsts.getText(2024, localLocale);
    }
    setDataItem(localReportDataItem, localObject2);
    localReportDataItem = localReportDataItems.add();
    setDataItem(localReportDataItem, paramACHBatch.getTrackingID());
    localReportDataItem = localReportDataItems.add();
    setDataItem(localReportDataItem, paramACHBatch.getStatus());
    paramReportResult1.addRow((ReportRow)localObject1);
    paramInt2++;
    if (paramInt2 == paramInt3) {
      return;
    }
    if (paramACHBatch.getACHEntries().size() == 0) {
      return;
    }
    if (!paramString.equals("Detail Entry")) {
      return;
    }
    int i = 0;
    int j = 0;
    if ((paramACHBatch.getStandardEntryClassCodeValue() == 1) || (paramACHBatch.getStandardEntryClassCodeValue() == 5) || (paramACHBatch.getStandardEntryClassCodeValue() == 17) || (paramACHBatch.getStandardEntryClassCodeValue() == 18)) {
      j = 1;
    }
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult2.addSubReport(localReportResult);
    ReportHeading localReportHeading = new ReportHeading(localLocale);
    localReportHeading.setLabel(ReportConsts.getColumnName(2487, localLocale));
    localReportResult.setSectionHeading(localReportHeading);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(j != 0 ? 8 : 7);
    localReportDataDimensions.setNumRows(-1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    a(localReportResult, 1857, "java.lang.String", 20, null);
    a(localReportResult, 1877, "java.lang.String", 12, null);
    a(localReportResult, 1863, "java.lang.String", 10, null);
    a(localReportResult, 1862, "java.lang.String", 23, null);
    a(localReportResult, 1860, "com.ffusion.beans.Currency", 10, "RIGHT");
    a(localReportResult, 1861, "java.lang.String", 8, "RIGHT");
    a(localReportResult, 1859, "java.lang.String", 7, null);
    if (j != 0) {
      a(localReportResult, 1858, "java.lang.String", 12, null);
    }
    Iterator localIterator = paramACHBatch.getACHEntries().iterator();
    ACHEntry localACHEntry = null;
    while ((localIterator.hasNext()) && (paramInt2 < paramInt3))
    {
      localACHEntry = (ACHEntry)localIterator.next();
      localObject1 = new ReportRow(localLocale);
      if (i % 2 == 1) {
        ((ReportRow)localObject1).set("CELLBACKGROUND", "reportDataShaded");
      }
      localReportDataItems = new ReportDataItems(localLocale);
      ((ReportRow)localObject1).setDataItems(localReportDataItems);
      String str4 = "";
      String str5 = "";
      String str6 = "";
      String str7 = "";
      if (localACHEntry.getAchPayee() != null)
      {
        localObject3 = localACHEntry.getAchPayee();
        str4 = ((ACHPayee)localObject3).getNickName();
        if ((str4 == null) || (str4.length() == 0)) {
          str4 = ((ACHPayee)localObject3).getName();
        }
        if (str4 == null) {
          str4 = "";
        }
        if (((ACHPayee)localObject3).getUserAccountNumber() != null) {
          str5 = ((ACHPayee)localObject3).getUserAccountNumber();
        }
        if ((((ACHPayee)localObject3).getSecurePayeeValue() == true) && (((ACHPayee)localObject3).getSubmittedBy() != null) && (!((ACHPayee)localObject3).getSubmittedBy().equals("" + paramSecureUser.getProfileID())))
        {
          str6 = ((ACHPayee)localObject3).getSecurePayeeMask();
          str7 = ((ACHPayee)localObject3).getSecurePayeeMask();
          str4 = ((ACHPayee)localObject3).getSecurePayeeMask();
        }
        else
        {
          str6 = ((ACHPayee)localObject3).getAccountNumber() + " (" + ((ACHPayee)localObject3).getAccountType() + ")";
          str7 = ((ACHPayee)localObject3).getRoutingNumber();
        }
      }
      localReportDataItem = localReportDataItems.add();
      setDataItem(localReportDataItem, str4);
      localReportDataItem = localReportDataItems.add();
      setDataItem(localReportDataItem, str5);
      localReportDataItem = localReportDataItems.add();
      setDataItem(localReportDataItem, str7);
      localReportDataItem = localReportDataItems.add();
      setDataItem(localReportDataItem, str6);
      localReportDataItem = localReportDataItems.add();
      setDataItem(localReportDataItem, localACHEntry.getAmountValue());
      localReportDataItem = localReportDataItems.add();
      if (localACHEntry.isAmountIsDebit()) {
        setDataItem(localReportDataItem, ReportConsts.getText(10032, localLocale));
      } else {
        setDataItem(localReportDataItem, ReportConsts.getText(10031, localLocale));
      }
      Object localObject3 = localACHEntry.getDiscretionaryData();
      if (localObject3 == null) {
        localObject3 = "";
      }
      localReportDataItem = localReportDataItems.add();
      setDataItem(localReportDataItem, localObject3);
      if (j != 0)
      {
        localReportDataItem = localReportDataItems.add();
        String str8 = localACHEntry.getCheckSerialNumber();
        if (str8 == null) {
          str8 = "";
        }
        setDataItem(localReportDataItem, str8);
      }
      localReportResult.addRow((ReportRow)localObject1);
      paramInt2++;
      i++;
    }
    localReportDataDimensions.setNumRows(i);
  }
  
  private static void a(ReportResult paramReportResult, int paramInt1, String paramString1, int paramInt2, String paramString2)
    throws RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportColumn localReportColumn = new ReportColumn(localLocale);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(paramInt1, localLocale));
    localReportColumn.setLabels(localArrayList);
    if (paramString1 != null) {
      localReportColumn.setDataType(paramString1);
    }
    if (paramInt2 != 0) {
      localReportColumn.setWidthAsPercent(paramInt2);
    }
    if (paramString2 != null) {
      localReportColumn.setJustification(paramString2);
    }
    paramReportResult.addColumn(localReportColumn);
  }
  
  protected ACHCompanyInfo getACHCompanyInfo(SecureUser paramSecureUser, String paramString1, String paramString2)
  {
    ACHBPWServices localACHBPWServices;
    try
    {
      localACHBPWServices = getACHHandler();
    }
    catch (CSILException localCSILException)
    {
      return null;
    }
    ACHCompanyInfo[] arrayOfACHCompanyInfo = null;
    try
    {
      ACHCompanyList localACHCompanyList = new ACHCompanyList();
      localACHCompanyList.setCustomerId(String.valueOf(paramSecureUser.getBusinessID()));
      localACHCompanyList = localACHBPWServices.getACHCompanyList(localACHCompanyList);
      arrayOfACHCompanyInfo = localACHCompanyList.getACHCompanyInfoList();
    }
    catch (Throwable localThrowable)
    {
      ACHCompanyInfo localACHCompanyInfo = null;
      return localACHCompanyInfo;
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    for (int i = 0; i < arrayOfACHCompanyInfo.length; i++)
    {
      if ((paramString1 != null) && (arrayOfACHCompanyInfo[i].getCompACHId().equals(paramString1))) {
        return arrayOfACHCompanyInfo[i];
      }
      if ((paramString2 != null) && (arrayOfACHCompanyInfo[i].getCompId().equals(paramString2))) {
        return arrayOfACHCompanyInfo[i];
      }
    }
    return null;
  }
  
  protected static void setDataItem(ReportDataItem paramReportDataItem, Object paramObject)
  {
    if (paramObject == null) {
      paramReportDataItem.setData("");
    } else {
      paramReportDataItem.setData(paramObject);
    }
  }
  
  protected static String getSearchCriteria(Properties paramProperties, String paramString1, String paramString2)
  {
    String str = paramProperties.getProperty(paramString1);
    if ((str == null) || (str.equals(""))) {
      str = paramString2;
    } else {
      str = str.trim();
    }
    return str;
  }
  
  public String[] getStatesWithTaxForms(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    if (taxFormCache != null)
    {
      if ((paramHashMap != null) && (paramHashMap.get("TaxFormType") != null))
      {
        String str = (String)paramHashMap.get("TaxFormType");
        return TaxFormCache.getStatesWithTaxForms(Integer.parseInt(str));
      }
      return TaxFormCache.getStatesWithTaxForms();
    }
    throw new CSILException("ACH.getStatesWithTaxForms", 19003);
  }
  
  public HashMap getStateNamesWithTaxForms(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    if (taxFormCache != null)
    {
      if ((paramHashMap != null) && (paramHashMap.get("TaxFormType") != null))
      {
        String str = (String)paramHashMap.get("TaxFormType");
        return TaxFormCache.getStateNamesWithTaxForms(Integer.parseInt(str));
      }
      return TaxFormCache.getStateNamesWithTaxForms();
    }
    throw new CSILException("ACH.getStatesWithTaxForms", 19003);
  }
  
  public TaxForms getTaxForms(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    if (taxFormCache != null)
    {
      if ((paramInt == 2) || (paramInt == 4)) {
        return TaxFormCache.getTaxForms(paramInt, paramString);
      }
      return TaxFormCache.getTaxForms(paramInt, null);
    }
    throw new CSILException("ACH.getTaxForms", 19003);
  }
  
  public TaxForm getTaxForm(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    if (taxFormCache != null) {
      return TaxFormCache.getTaxForm(paramString);
    }
    throw new CSILException("ACH.getTaxForm", 19003);
  }
  
  public ACHCompany addACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    int i = 16002;
    ACHBPWServices localACHBPWServices = getACHHandler();
    ACHCompanyInfo localACHCompanyInfo = null;
    BPWServices localBPWServices = getBPWHandler();
    String str1 = "";
    try
    {
      BPWFIInfo localBPWFIInfo = localBPWServices.getBPWFIInfo(paramString);
      str2 = localBPWFIInfo.getFIRTN();
      ACHFIInfo[] arrayOfACHFIInfo = localACHBPWServices.getFIInfoByRTN(str2);
      if (arrayOfACHFIInfo == null) {
        throw new CSILException(-1009, i);
      }
      ACHFIInfo localACHFIInfo = arrayOfACHFIInfo[0];
      localACHCompanyInfo = new ACHCompanyInfo();
      localACHCompanyInfo.setLogId(paramACHCompany.getTrackingID());
      localACHCompanyInfo.setCompName(paramACHCompany.getCompanyName());
      localACHCompanyInfo.setCompDesc("");
      localACHCompanyInfo.setCompACHId(paramACHCompany.getCompanyID());
      localACHCompanyInfo.setCustomerId(paramACHCompany.getCustID());
      localACHCompanyInfo.setODFIACHId(localACHFIInfo.getODFIACHId());
      paramACHCompany.setSubmittedBy(Integer.toString(paramSecureUser.getProfileID()));
      localACHCompanyInfo.setSubmittedBy(paramACHCompany.getSubmittedBy());
      if (paramACHCompany.getTrackingID() == null) {
        paramACHCompany.setTrackingID(TrackingIDGenerator.GetNextID());
      }
      localACHCompanyInfo.setLogId(paramACHCompany.getTrackingID());
      String str3 = null;
      if (paramACHCompany.getACHBatchTypeValue() == 1) {
        str3 = "UnbalancedBatch";
      } else if (paramACHCompany.getACHBatchTypeValue() == 2) {
        str3 = "BatchBalancedBatch";
      } else if (paramACHCompany.getACHBatchTypeValue() == 3) {
        str3 = "EntryBalancedBatch";
      }
      localACHCompanyInfo.setBatchType(str3);
      populateOBOAgentInfo(paramSecureUser, localACHCompanyInfo);
      localACHCompanyInfo = localACHBPWServices.addACHCompany(localACHCompanyInfo);
      if (localACHCompanyInfo != null)
      {
        i = localACHCompanyInfo.getStatusCode();
        str1 = localACHCompanyInfo.getStatusMsg();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACHService.addACHCompany exception: " + localThrowable.getMessage());
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i == 0)
    {
      paramACHCompany.setID(localACHCompanyInfo.getCompId());
      return paramACHCompany;
    }
    throw new CSILException(16120, 16002, "", str1);
  }
  
  public ACHCompanies getACHCompanies(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    int i = 16002;
    ACHBPWServices localACHBPWServices = getACHHandler();
    ACHCompanyInfo[] arrayOfACHCompanyInfo = null;
    Object localObject1;
    try
    {
      ACHCompanyList localACHCompanyList = new ACHCompanyList();
      localACHCompanyList.setCustomerId(paramString1);
      localACHCompanyList = localACHBPWServices.getACHCompanyList(localACHCompanyList);
      arrayOfACHCompanyInfo = localACHCompanyList.getACHCompanyInfoList();
      if (arrayOfACHCompanyInfo != null) {
        i = 0;
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACHService.getACHCompanies exception: " + localThrowable.getMessage());
      localObject1 = localThrowable.toString();
      if ((localObject1 != null) && (((String)localObject1).indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    if (i == 0)
    {
      ACHCompanies localACHCompanies = new ACHCompanies(Locale.getDefault());
      for (int j = 0; j < arrayOfACHCompanyInfo.length; j++)
      {
        localObject1 = localACHCompanies.create(arrayOfACHCompanyInfo[j].getCompId(), arrayOfACHCompanyInfo[j].getCustomerId(), arrayOfACHCompanyInfo[j].getCompACHId(), arrayOfACHCompanyInfo[j].getCompName());
        ((ACHCompany)localObject1).setTrackingID(arrayOfACHCompanyInfo[j].getLogId());
        ((ACHCompany)localObject1).setSubmittedBy(arrayOfACHCompanyInfo[j].getSubmittedBy());
        ((ACHCompany)localObject1).setACHBatchType(0);
        if (arrayOfACHCompanyInfo[j].getBatchType() != null) {
          if (arrayOfACHCompanyInfo[j].getBatchType().equalsIgnoreCase("BatchBalancedBatch")) {
            ((ACHCompany)localObject1).setACHBatchType(2);
          } else if (arrayOfACHCompanyInfo[j].getBatchType().equalsIgnoreCase("UnbalancedBatch")) {
            ((ACHCompany)localObject1).setACHBatchType(1);
          } else if (arrayOfACHCompanyInfo[j].getBatchType().equalsIgnoreCase("EntryBalancedBatch")) {
            ((ACHCompany)localObject1).setACHBatchType(3);
          }
        }
      }
      return localACHCompanies;
    }
    throw new CSILException(-1009, i);
  }
  
  public void modifyACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    int i = 16002;
    ACHBPWServices localACHBPWServices = getACHHandler();
    BPWServices localBPWServices = getBPWHandler();
    ACHCompanyInfo localACHCompanyInfo = null;
    String str1 = "";
    try
    {
      BPWFIInfo localBPWFIInfo = localBPWServices.getBPWFIInfo(paramString);
      str2 = localBPWFIInfo.getFIRTN();
      ACHFIInfo[] arrayOfACHFIInfo = localACHBPWServices.getFIInfoByRTN(str2);
      if (arrayOfACHFIInfo == null) {
        throw new CSILException(-1009, i);
      }
      ACHFIInfo localACHFIInfo = arrayOfACHFIInfo[0];
      localACHCompanyInfo = localACHBPWServices.getACHCompany(paramACHCompany.getID());
      localACHCompanyInfo.setCompName(paramACHCompany.getCompanyName());
      localACHCompanyInfo.setCompACHId(paramACHCompany.getCompanyID());
      localACHCompanyInfo.setODFIACHId(localACHFIInfo.getODFIACHId());
      String str3 = paramACHCompany.getSubmittedBy();
      paramACHCompany.setSubmittedBy(Integer.toString(paramSecureUser.getProfileID()));
      localACHCompanyInfo.setSubmittedBy(paramACHCompany.getSubmittedBy());
      String str4 = null;
      if (paramACHCompany.getACHBatchTypeValue() == 1) {
        str4 = "UnbalancedBatch";
      } else if (paramACHCompany.getACHBatchTypeValue() == 2) {
        str4 = "BatchBalancedBatch";
      } else if (paramACHCompany.getACHBatchTypeValue() == 3) {
        str4 = "EntryBalancedBatch";
      }
      localACHCompanyInfo.setBatchType(str4);
      populateOBOAgentInfo(paramSecureUser, localACHCompanyInfo);
      localACHCompanyInfo = localACHBPWServices.modACHCompany(localACHCompanyInfo);
      if (localACHCompanyInfo != null)
      {
        i = localACHCompanyInfo.getStatusCode();
        str1 = localACHCompanyInfo.getStatusMsg();
      }
      if (str3 != null) {
        paramACHCompany.setSubmittedBy(str3);
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACHService.modACHCompany exception: " + localThrowable.getMessage());
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0) {
      throw new CSILException(16119, i, "", str1);
    }
  }
  
  public void deleteACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    int i = 16002;
    String str1 = "";
    ACHBPWServices localACHBPWServices = getACHHandler();
    BPWServices localBPWServices = getBPWHandler();
    ACHCompanyInfo localACHCompanyInfo = null;
    try
    {
      BPWFIInfo localBPWFIInfo = localBPWServices.getBPWFIInfo(paramString);
      str2 = localBPWFIInfo.getFIRTN();
      ACHFIInfo[] arrayOfACHFIInfo = localACHBPWServices.getFIInfoByRTN(str2);
      if (arrayOfACHFIInfo == null) {
        throw new CSILException(-1009, i);
      }
      ACHFIInfo localACHFIInfo = arrayOfACHFIInfo[0];
      localACHCompanyInfo = localACHBPWServices.getACHCompany(paramACHCompany.getID());
      localACHCompanyInfo.setCompName(paramACHCompany.getCompanyName());
      localACHCompanyInfo.setCompACHId(paramACHCompany.getCompanyID());
      localACHCompanyInfo.setODFIACHId(localACHFIInfo.getODFIACHId());
      paramACHCompany.setSubmittedBy(Integer.toString(paramSecureUser.getProfileID()));
      localACHCompanyInfo.setSubmittedBy(paramACHCompany.getSubmittedBy());
      populateOBOAgentInfo(paramSecureUser, localACHCompanyInfo);
      localACHCompanyInfo = localACHBPWServices.canACHCompany(localACHCompanyInfo);
      if (localACHCompanyInfo != null)
      {
        i = localACHCompanyInfo.getStatusCode();
        str1 = localACHCompanyInfo.getStatusMsg();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACHService.deleteACHCompany exception: " + localThrowable.getMessage());
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    if (i != 0) {
      throw new CSILException(16119, i, "", str1);
    }
  }
  
  public ACHCompanySummaries getACHCompanySummaries(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    int i = 16002;
    ACHBPWServices localACHBPWServices = null;
    ACHCompanySummaryInfo[] arrayOfACHCompanySummaryInfo = null;
    ACHCompanySummaries localACHCompanySummaries = null;
    ACHCompanySummaryList localACHCompanySummaryList = new ACHCompanySummaryList();
    localACHCompanySummaryList.setCompanyIdList(paramArrayOfString);
    localACHCompanySummaryList.setCustomerId("" + paramSecureUser.getBusinessID());
    Object localObject1;
    if ((paramHashMap != null) && (paramHashMap.get("BatchType") != null) && (((String)paramHashMap.get("BatchType")).toUpperCase().startsWith("TAX")))
    {
      localObject1 = new String[] { "ACH_BATCH_TAX" };
      localACHCompanySummaryList.setBatchCategoryList((String[])localObject1);
    }
    else if ((paramHashMap != null) && (paramHashMap.get("BatchType") != null) && (((String)paramHashMap.get("BatchType")).toUpperCase().startsWith("CHILD")))
    {
      localObject1 = new String[] { "ACH_BATCH_CHILD_SUPPORT" };
      localACHCompanySummaryList.setBatchCategoryList((String[])localObject1);
    }
    else
    {
      localObject1 = new String[] { "ACH_BATCH_PAYMENT", "ACH_BATCH_REVERSAL" };
      localACHCompanySummaryList.setBatchCategoryList((String[])localObject1);
    }
    try
    {
      localACHBPWServices = getACHHandler();
      localACHCompanySummaryList = localACHBPWServices.getACHCompanySummaries(localACHCompanySummaryList);
      arrayOfACHCompanySummaryInfo = localACHCompanySummaryList.getACHCompanySummaryInfoList();
      if (arrayOfACHCompanySummaryInfo != null) {
        i = 0;
      }
      if (i == 0)
      {
        localACHCompanySummaries = new ACHCompanySummaries(Locale.getDefault());
        localObject1 = null;
        for (int j = 0; j < arrayOfACHCompanySummaryInfo.length; j++)
        {
          localObject1 = localACHCompanySummaries.getByCompanyID(arrayOfACHCompanySummaryInfo[j].getCompanyId());
          if (localObject1 == null) {
            localObject1 = localACHCompanySummaries.create(arrayOfACHCompanySummaryInfo[j].getCompanyId());
          }
          ((ACHCompanySummary)localObject1).setNumBatches(((ACHCompanySummary)localObject1).getNumBatches() + arrayOfACHCompanySummaryInfo[j].getNumberOfBatches());
          ((ACHCompanySummary)localObject1).setNumBatchEntries(((ACHCompanySummary)localObject1).getNumBatchEntries() + arrayOfACHCompanySummaryInfo[j].getNumberOfBatchEntries());
          String str = BeansConverter.getAmountAddDecimal("" + arrayOfACHCompanySummaryInfo[j].getTotalCredit());
          Currency localCurrency = new Currency(str, ((ACHCompanySummary)localObject1).getLocale());
          if (((ACHCompanySummary)localObject1).getTotalCredit() != null) {
            localCurrency.addAmount(((ACHCompanySummary)localObject1).getTotalCredit());
          }
          ((ACHCompanySummary)localObject1).setTotalCredit(localCurrency);
          str = BeansConverter.getAmountAddDecimal("" + arrayOfACHCompanySummaryInfo[j].getTotalDebit());
          localCurrency = new Currency(str, ((ACHCompanySummary)localObject1).getLocale());
          if (((ACHCompanySummary)localObject1).getTotalDebit() != null) {
            localCurrency.addAmount(((ACHCompanySummary)localObject1).getTotalDebit());
          }
          ((ACHCompanySummary)localObject1).setTotalDebit(localCurrency);
          str = BeansConverter.getAmountAddDecimal("" + arrayOfACHCompanySummaryInfo[j].getGrandTotal());
          localCurrency = new Currency(str, ((ACHCompanySummary)localObject1).getLocale());
          if (((ACHCompanySummary)localObject1).getGrandTotal() != null) {
            localCurrency.addAmount(((ACHCompanySummary)localObject1).getGrandTotal());
          }
          ((ACHCompanySummary)localObject1).setGrandTotal(localCurrency);
        }
        localObject2 = localACHCompanySummaries;
        return localObject2;
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACHService.getACHCompanySummaries exception: " + localThrowable.getMessage());
      Object localObject2 = localThrowable.toString();
      if ((localObject2 != null) && (((String)localObject2).indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    localACHCompanySummaries = new ACHCompanySummaries(Locale.getDefault());
    return localACHCompanySummaries;
  }
  
  public void addOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    int i = 16002;
    ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo = null;
    ACHBPWServices localACHBPWServices = null;
    String str1 = "";
    try
    {
      localACHBPWServices = getACHHandler();
      localACHCompOffsetAcctInfo = new ACHCompOffsetAcctInfo();
      localACHCompOffsetAcctInfo.setCompId(paramACHCompany.getID());
      localACHCompOffsetAcctInfo.setAcctNickName(paramACHOffsetAccount.getNickName());
      localACHCompOffsetAcctInfo.setBankRTN(paramACHOffsetAccount.getRoutingNum());
      localACHCompOffsetAcctInfo.setAcctNumber(Strings.removeChars(paramACHOffsetAccount.getNumber(), ' '));
      localACHCompOffsetAcctInfo.setAcctType(paramACHOffsetAccount.getType());
      localACHCompOffsetAcctInfo = localACHBPWServices.addACHCompanyOffsetAccount(localACHCompOffsetAcctInfo);
      if (localACHCompOffsetAcctInfo != null)
      {
        i = localACHCompOffsetAcctInfo.getStatusCode();
        str1 = localACHCompOffsetAcctInfo.getStatusMsg();
      }
      paramACHOffsetAccount.setID(localACHCompOffsetAcctInfo.getAcctId());
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACHService.addACHOffsetAccount exception: " + localThrowable.getMessage());
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    if (i != 0) {
      throw new CSILException(16121, i, "", str1);
    }
  }
  
  public ACHOffsetAccounts getOffsetAccounts(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    int i = 16002;
    ACHCompOffsetAcctInfo[] arrayOfACHCompOffsetAcctInfo = null;
    ACHBPWServices localACHBPWServices = null;
    ACHOffsetAccounts localACHOffsetAccounts = null;
    try
    {
      localACHBPWServices = getACHHandler();
      arrayOfACHCompOffsetAcctInfo = localACHBPWServices.getACHCompanyOffsetAccountByCompId(paramACHCompany.getID());
      if (arrayOfACHCompOffsetAcctInfo != null)
      {
        i = 0;
        localACHOffsetAccounts = new ACHOffsetAccounts(paramACHCompany.getLocale());
        for (int j = 0; j < arrayOfACHCompOffsetAcctInfo.length; j++)
        {
          ACHOffsetAccount localACHOffsetAccount = localACHOffsetAccounts.create();
          localACHOffsetAccount.setNumber(arrayOfACHCompOffsetAcctInfo[j].getAcctNumber());
          localACHOffsetAccount.setNickName(arrayOfACHCompOffsetAcctInfo[j].getAcctNickName());
          localACHOffsetAccount.setType(arrayOfACHCompOffsetAcctInfo[j].getAcctType());
          localACHOffsetAccount.setRoutingNum(arrayOfACHCompOffsetAcctInfo[j].getBankRTN());
          localACHOffsetAccount.setID(arrayOfACHCompOffsetAcctInfo[j].getAcctId());
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACHService.getOffsetAccounts exception: " + localThrowable.getMessage());
      String str = localThrowable.toString();
      if ((str != null) && (str.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    if (i != 0) {
      throw new CSILException(16122, i);
    }
    return localACHOffsetAccounts;
  }
  
  public void modifyOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    int i = 16002;
    ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo = null;
    ACHBPWServices localACHBPWServices = null;
    String str1 = "";
    try
    {
      localACHBPWServices = getACHHandler();
      localACHCompOffsetAcctInfo = new ACHCompOffsetAcctInfo();
      localACHCompOffsetAcctInfo.setCompId(paramACHCompany.getID());
      localACHCompOffsetAcctInfo.setAcctNickName(paramACHOffsetAccount.getNickName());
      localACHCompOffsetAcctInfo.setBankRTN(paramACHOffsetAccount.getRoutingNum());
      localACHCompOffsetAcctInfo.setAcctNumber(Strings.removeChars(paramACHOffsetAccount.getNumber(), ' '));
      localACHCompOffsetAcctInfo.setAcctType(paramACHOffsetAccount.getType());
      localACHCompOffsetAcctInfo.setAcctId(paramACHOffsetAccount.getID());
      localACHCompOffsetAcctInfo.setAcctStatus("ACTIVE");
      localACHCompOffsetAcctInfo = localACHBPWServices.modACHCompanyOffsetAccount(localACHCompOffsetAcctInfo);
      if (localACHCompOffsetAcctInfo != null)
      {
        i = localACHCompOffsetAcctInfo.getStatusCode();
        str1 = localACHCompOffsetAcctInfo.getStatusMsg();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACHService.modifyOffsetAccount exception: " + localThrowable.getMessage());
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    if (i != 0) {
      throw new CSILException(16123, i, "", str1);
    }
  }
  
  public void deleteOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    int i = 16002;
    ACHCompOffsetAcctInfo localACHCompOffsetAcctInfo = null;
    ACHBPWServices localACHBPWServices = null;
    String str1 = "";
    try
    {
      localACHBPWServices = getACHHandler();
      localACHCompOffsetAcctInfo = new ACHCompOffsetAcctInfo();
      localACHCompOffsetAcctInfo.setAcctId(paramACHOffsetAccount.getID());
      localACHCompOffsetAcctInfo = localACHBPWServices.canACHCompanyOffsetAccount(localACHCompOffsetAcctInfo);
      if (localACHCompOffsetAcctInfo != null)
      {
        i = localACHCompOffsetAcctInfo.getStatusCode();
        str1 = localACHCompOffsetAcctInfo.getStatusMsg();
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACHService.deleteOffsetAccount exception: " + localThrowable.getMessage());
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    if (i != 0) {
      throw new CSILException(16124, i, "", str1);
    }
  }
  
  public AffiliateBank addAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    int i = 0;
    ACHBPWServices localACHBPWServices = getACHHandler();
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      String str1 = paramAffiliateBank.getAffiliateBankName().trim();
      String str2 = paramAffiliateBank.getDescription();
      if (str2 != null) {
        str2 = str2.trim();
      }
      String str3 = paramAffiliateBank.getAffiliateRoutingNum().trim();
      String str4 = paramAffiliateBank.getSwiftBIC();
      if (str4 != null) {
        str4 = str4.trim();
      }
      if ((str1 != null) && (str1.length() > 0) && (((str3 != null) && (str3.length() > 0)) || ((str4 != null) && (str4.length() > 0))))
      {
        BPWFIInfo localBPWFIInfo = new BPWFIInfo();
        localBPWFIInfo.setFIName(str1);
        localBPWFIInfo.setFIDesc(str2);
        localBPWFIInfo.setFIRTN(str3);
        localBPWFIInfo.setSwiftRTN(str4);
        localBPWFIInfo.setLogId(TrackingIDGenerator.GetNextID());
        localBPWFIInfo.setCurrencyCode(paramAffiliateBank.getCurrencyCode());
        localBPWFIInfo.setAddr1(paramAffiliateBank.getStreet());
        localBPWFIInfo.setAddr2(paramAffiliateBank.getStreet2());
        localBPWFIInfo.setAddr3(paramAffiliateBank.getStreet3());
        localBPWFIInfo.setCity(paramAffiliateBank.getCity());
        localBPWFIInfo.setState(paramAffiliateBank.getState());
        localBPWFIInfo.setCountry(paramAffiliateBank.getCountry());
        localBPWFIInfo.setPostalCode(paramAffiliateBank.getZipCode());
        if (paramAffiliateBank.getWarehousingType() == 2) {
          localBPWFIInfo.setACHTransWareHouse(1);
        }
        localBPWFIInfo.setACHMaxNoFutureDays(paramAffiliateBank.getAchMaxFutureDays());
        localBPWFIInfo.setACHSameDayEffDate(paramAffiliateBank.getAchSameDayEffDating() ? "Y" : "N");
        localBPWFIInfo.setETFDepositAcct(paramAffiliateBank.getEtfAccountNum());
        localBPWFIInfo.setETFDepositAcctType(BeansConverter.getACHAcctTypeFromEFS(paramAffiliateBank.getEtfAccountTypeValue()));
        localBPWFIInfo.setETFMaxDepositAmt(paramAffiliateBank.getEtfMaximumDepositAmountValue());
        localBPWFIInfo.setETFMinDepositAmt(paramAffiliateBank.getEtfMinimumDepositAmountValue());
        localBPWFIInfo = localBPWServices.addBPWFIInfo(localBPWFIInfo);
        i = localBPWFIInfo.getStatusCode();
        if (i == 0)
        {
          paramAffiliateBank.setFIBPWID(localBPWFIInfo.getFIId());
          String str5 = paramAffiliateBank.getACHImmediateOrigin().trim();
          String str6 = paramAffiliateBank.getACHImmediateOriginName().trim();
          String str7 = paramAffiliateBank.getACHDestination().trim();
          String str8 = paramAffiliateBank.getACHDestinationName().trim();
          ACHFIInfo localACHFIInfo = null;
          if ((str5 != null) && (str5.length() > 0) && (str6 != null) && (str6.length() > 0) && (str7 != null) && (str7.length() > 0) && (str8 != null) && (str8.length() > 0))
          {
            localACHFIInfo = new ACHFIInfo();
            localACHFIInfo.setFIId(localBPWFIInfo.getFIId());
            localACHFIInfo.setODFIACHId(str5);
            localACHFIInfo.setODFIName(str6);
            localACHFIInfo.setRDFIACHId(str7);
            localACHFIInfo.setRDFIName(str8);
            localACHFIInfo.setLogId(localBPWFIInfo.getLogId());
            localACHFIInfo = localACHBPWServices.addACHFIInfo(localACHFIInfo);
            i = localACHFIInfo.getStatusCode();
            if (i != 0)
            {
              localBPWServices.canBPWFIInfo(localBPWFIInfo);
              if (i == 23340) {
                throw new CSILException(-1009, 25017);
              }
              throw new CSILException(-1009, 25008);
            }
          }
        }
        else
        {
          if (i == 23300) {
            throw new CSILException(-1009, 25012);
          }
          throw new CSILException(-1009, 25004);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.addAffiliateBank exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      DebugLog.log("ACHService.addAffiliateBank exception: " + localException.getMessage());
      throw new CSILException(-1009, 25013, localException);
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return paramAffiliateBank;
  }
  
  public AffiliateBank modifyAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    int i = 0;
    ACHBPWServices localACHBPWServices = getACHHandler();
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      String str1 = paramAffiliateBank.getFIBPWID().trim();
      if ((str1 != null) && (str1.length() > 0) && (!str1.equals("0")))
      {
        int j = 0;
        BPWFIInfo localBPWFIInfo = localBPWServices.getBPWFIInfo(paramAffiliateBank.getFIBPWID());
        i = localBPWFIInfo.getStatusCode();
        paramAffiliateBank.setEtfVirtualUserID(localBPWFIInfo.getETFVirtualUserID());
        paramAffiliateBank.setEtfCompany(a(localBPWFIInfo.getETFCompanyID()));
        String str2 = paramAffiliateBank.getAffiliateBankName().trim();
        String str3 = paramAffiliateBank.getDescription();
        if (str3 != null) {
          str3 = str3.trim();
        }
        String str4 = paramAffiliateBank.getAffiliateRoutingNum().trim();
        String str5 = paramAffiliateBank.getSwiftBIC();
        if (str5 != null) {
          str5 = str5.trim();
        }
        String str6 = paramAffiliateBank.getCurrencyCode().trim();
        String str7 = paramAffiliateBank.getStreet();
        String str8 = paramAffiliateBank.getStreet2();
        String str9 = paramAffiliateBank.getStreet3();
        if (str7 != null) {
          str7 = str7.trim();
        }
        if (str8 != null) {
          str8 = str8.trim();
        }
        if (str9 != null) {
          str9 = str9.trim();
        }
        String str10 = paramAffiliateBank.getCity();
        if (str10 != null) {
          str10 = str10.trim();
        }
        String str11 = paramAffiliateBank.getState();
        if (str11 != null) {
          str11 = str11.trim();
        }
        String str12 = paramAffiliateBank.getCountry();
        if (str12 != null) {
          str12 = str12.trim();
        }
        String str13 = paramAffiliateBank.getZipCode();
        if (str13 != null) {
          str13 = str13.trim();
        }
        if ((str7 != null) && (!str7.equals(localBPWFIInfo.getAddr1())))
        {
          localBPWFIInfo.setAddr1(str7);
          j = 1;
        }
        if ((str8 != null) && (!str8.equals(localBPWFIInfo.getAddr2())))
        {
          localBPWFIInfo.setAddr2(str8);
          j = 1;
        }
        if ((str9 != null) && (!str9.equals(localBPWFIInfo.getAddr3())))
        {
          localBPWFIInfo.setAddr3(str9);
          j = 1;
        }
        if ((str10 != null) && (!str10.equals(localBPWFIInfo.getCity())))
        {
          localBPWFIInfo.setCity(str10);
          j = 1;
        }
        if ((str11 != null) && (!str11.equals(localBPWFIInfo.getState())))
        {
          localBPWFIInfo.setState(str11);
          j = 1;
        }
        if ((str12 != null) && (!str12.equals(localBPWFIInfo.getCountry())))
        {
          localBPWFIInfo.setCountry(str12);
          j = 1;
        }
        if ((str13 != null) && (!str13.equals(localBPWFIInfo.getPostalCode())))
        {
          localBPWFIInfo.setPostalCode(str13);
          j = 1;
        }
        if ((str2 != null) && (!str2.equals(localBPWFIInfo.getFIName())))
        {
          localBPWFIInfo.setFIName(str2);
          j = 1;
        }
        if ((str6 != null) && (!str6.equals(localBPWFIInfo.getCurrencyCode())))
        {
          localBPWFIInfo.setCurrencyCode(str6);
          j = 1;
        }
        if ((str3 != null) && (!str3.equals(localBPWFIInfo.getFIDesc())))
        {
          localBPWFIInfo.setFIDesc(str3);
          j = 1;
        }
        if ((str4 != null) && (!str4.equals(localBPWFIInfo.getFIRTN())))
        {
          localBPWFIInfo.setFIRTN(str4);
          j = 1;
        }
        if ((str5 != null) && (!str5.equals(localBPWFIInfo.getSwiftRTN())))
        {
          localBPWFIInfo.setSwiftRTN(str5);
          j = 1;
        }
        int k = paramAffiliateBank.getWarehousingType() == 2 ? 1 : 0;
        if (k != localBPWFIInfo.getACHTransWareHouse())
        {
          localBPWFIInfo.setACHTransWareHouse(k);
          j = 1;
        }
        if (paramAffiliateBank.getAchMaxFutureDays() != localBPWFIInfo.getACHMaxNoFutureDays())
        {
          localBPWFIInfo.setACHMaxNoFutureDays(paramAffiliateBank.getAchMaxFutureDays());
          j = 1;
        }
        String str14 = paramAffiliateBank.getAchSameDayEffDating() ? "Y" : "N";
        if (!str14.equals(localBPWFIInfo.getACHSameDayEffDate()))
        {
          localBPWFIInfo.setACHSameDayEffDate(str14);
          j = 1;
        }
        String str15 = paramAffiliateBank.getEtfAccountNum();
        if ((str15 != null) && (str15.trim().length() > 0))
        {
          localBPWFIInfo.setETFDepositAcct(paramAffiliateBank.getEtfAccountNum());
          j = 1;
        }
        String str16 = "";
        str16 = BeansConverter.getACHAcctTypeFromEFS(paramAffiliateBank.getEtfAccountTypeValue());
        if (!str16.equals(localBPWFIInfo.getETFDepositAcctType()))
        {
          localBPWFIInfo.setETFDepositAcctType(str16);
          j = 1;
        }
        if ((paramAffiliateBank.getEtfMaximumDepositAmountValue() != localBPWFIInfo.getETFMaxDepositAmt()) || (paramAffiliateBank.getEtfMinimumDepositAmountValue() != localBPWFIInfo.getETFMinDepositAmt()))
        {
          localBPWFIInfo.setETFMaxDepositAmt(paramAffiliateBank.getEtfMaximumDepositAmountValue());
          localBPWFIInfo.setETFMinDepositAmt(paramAffiliateBank.getEtfMinimumDepositAmountValue());
          j = 1;
        }
        if (j != 0)
        {
          localBPWFIInfo = localBPWServices.modBPWFIInfo(localBPWFIInfo);
          i = localBPWFIInfo.getStatusCode();
          if (i != 0)
          {
            if (i == 16130) {
              throw new CSILException(-1009, 25012);
            }
            throw new CSILException(-1009, 25006);
          }
        }
        ACHFIInfo[] arrayOfACHFIInfo = localACHBPWServices.getFIInfoByRTN(localBPWFIInfo.getFIRTN());
        ACHFIInfo localACHFIInfo = arrayOfACHFIInfo[0];
        String str17 = paramAffiliateBank.getACHImmediateOrigin();
        if (str17 != null) {
          str17 = str17.trim();
        }
        String str18 = paramAffiliateBank.getACHImmediateOriginName();
        if (str18 != null) {
          str18 = str18.trim();
        }
        String str19 = paramAffiliateBank.getACHDestination();
        if (str19 != null) {
          str19 = str19.trim();
        }
        String str20 = paramAffiliateBank.getACHDestinationName();
        if (str20 != null) {
          str20 = str20.trim();
        }
        if ((str17 != null) && (str17.length() > 0) && (str18 != null) && (str18.length() > 0) && (str19 != null) && (str19.length() > 0) && (str20 != null) && (str20.length() > 0))
        {
          j = 0;
          if ((arrayOfACHFIInfo != null) && (arrayOfACHFIInfo.length > 0) && (localACHFIInfo.getStatusCode() == 0))
          {
            j = 0;
            if (!str17.equals(localACHFIInfo.getODFIACHId()))
            {
              localACHFIInfo.setODFIACHId(str17);
              j = 1;
            }
            if (!str18.equals(localACHFIInfo.getODFIName()))
            {
              localACHFIInfo.setODFIName(str18);
              j = 1;
            }
            if (!str19.equals(localACHFIInfo.getRDFIACHId()))
            {
              localACHFIInfo.setRDFIACHId(str19);
              j = 1;
            }
            if (!str20.equals(localACHFIInfo.getRDFIName()))
            {
              localACHFIInfo.setRDFIName(str20);
              j = 1;
            }
            if (j != 0)
            {
              localACHFIInfo = localACHBPWServices.modACHFIInfo(localACHFIInfo);
              i = localACHFIInfo.getStatusCode();
              if (i != 0)
              {
                if ((i == 23340) || (i == 16140)) {
                  throw new CSILException(-1009, 25017);
                }
                throw new CSILException(-1009, 25010);
              }
            }
          }
          else
          {
            localACHFIInfo = new ACHFIInfo();
            localACHFIInfo.setFIId(localBPWFIInfo.getFIId());
            localACHFIInfo.setODFIACHId(str17);
            localACHFIInfo.setODFIName(str18);
            localACHFIInfo.setRDFIACHId(str19);
            localACHFIInfo.setRDFIName(str20);
            localACHFIInfo.setLogId(TrackingIDGenerator.GetNextID());
            localACHFIInfo = localACHBPWServices.addACHFIInfo(localACHFIInfo);
            i = localACHFIInfo.getStatusCode();
            if (i != 0)
            {
              if (i == 23340) {
                throw new CSILException(-1009, 25017);
              }
              throw new CSILException(-1009, 25008);
            }
          }
        }
        else if (localACHFIInfo.getStatusCode() == 0)
        {
          localACHFIInfo = localACHBPWServices.canACHFIInfo(localACHFIInfo);
          i = localACHFIInfo.getStatusCode();
          if (i != 0) {
            throw new CSILException(-1009, 25009);
          }
          paramAffiliateBank.setACHImmediateOrigin(null);
          paramAffiliateBank.setACHImmediateOriginName(null);
          paramAffiliateBank.setACHDestination(null);
          paramAffiliateBank.setACHDestinationName(null);
        }
      }
      else
      {
        paramAffiliateBank = addAffiliateBank(paramSecureUser, paramAffiliateBank, paramHashMap);
      }
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.modifyAffiliateBank exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      DebugLog.log("ACHService.modifyAffiliateBank exception: " + localException.getMessage());
      throw new CSILException(-1009, 25015, localException);
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return paramAffiliateBank;
  }
  
  public AffiliateBank deleteAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    int i = 0;
    BPWServices localBPWServices = getBPWHandler();
    ACHBPWServices localACHBPWServices = getACHHandler();
    try
    {
      String str = paramAffiliateBank.getFIBPWID().trim();
      if ((str != null) && (str.length() > 0) && (!str.equals("0")))
      {
        BPWFIInfo localBPWFIInfo = localBPWServices.getBPWFIInfo(paramAffiliateBank.getFIBPWID());
        i = localBPWFIInfo.getStatusCode();
        if (localBPWFIInfo != null)
        {
          ACHFIInfo[] arrayOfACHFIInfo = localACHBPWServices.getFIInfoByRTN(localBPWFIInfo.getFIRTN());
          if ((arrayOfACHFIInfo != null) && (arrayOfACHFIInfo.length > 0) && (arrayOfACHFIInfo[0].getStatusCode() == 0))
          {
            ACHFIInfo localACHFIInfo = arrayOfACHFIInfo[0];
            localACHFIInfo = localACHBPWServices.canACHFIInfo(localACHFIInfo);
            i = localACHFIInfo.getStatusCode();
            if (i != 0) {
              throw new CSILException(-1009, 25009);
            }
            paramAffiliateBank.setACHImmediateOrigin(null);
            paramAffiliateBank.setACHImmediateOriginName(null);
            paramAffiliateBank.setACHDestination(null);
            paramAffiliateBank.setACHDestinationName(null);
          }
          localBPWFIInfo = localBPWServices.canBPWFIInfo(localBPWFIInfo);
          i = localBPWFIInfo.getStatusCode();
          if (i != 0) {
            throw new CSILException(-1009, 25005);
          }
          paramAffiliateBank.setDescription(null);
          paramAffiliateBank.setAffiliateRoutingNum(null);
          paramAffiliateBank.setFIBPWID(null);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.deleteAffiliateBank exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      DebugLog.log("ACHService.deleteAffiliateBank exception: " + localException.getMessage());
      throw new CSILException(-1009, 25014, localException);
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return paramAffiliateBank;
  }
  
  public AffiliateBank getAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    int i = 0;
    ACHBPWServices localACHBPWServices = getACHHandler();
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      String str1 = paramAffiliateBank.getFIBPWID();
      if (str1 != null) {
        str1 = str1.trim();
      }
      String str2 = paramAffiliateBank.getAffiliateRoutingNum();
      BPWFIInfo localBPWFIInfo = null;
      if ((str1 != null) && (str1.length() > 0) && (!str1.equals("0"))) {
        localBPWFIInfo = localBPWServices.getBPWFIInfo(paramAffiliateBank.getFIBPWID());
      } else if ((str2 != null) && (str2.length() > 0)) {
        localBPWFIInfo = localBPWServices.getBPWFIInfoByRTN(paramAffiliateBank.getAffiliateRoutingNum());
      }
      if (localBPWFIInfo != null)
      {
        i = localBPWFIInfo.getStatusCode();
        if (i != 0) {
          throw new CSILException(-1009, 25007);
        }
        paramAffiliateBank.setDescription(localBPWFIInfo.getFIDesc());
        paramAffiliateBank.setAffiliateRoutingNum(localBPWFIInfo.getFIRTN());
        paramAffiliateBank.setSwiftBIC(localBPWFIInfo.getSwiftRTN());
        paramAffiliateBank.setFIBPWID(localBPWFIInfo.getFIId());
        paramAffiliateBank.setCurrencyCode(localBPWFIInfo.getCurrencyCode());
        paramAffiliateBank.setStreet(localBPWFIInfo.getAddr1());
        paramAffiliateBank.setStreet2(localBPWFIInfo.getAddr2());
        paramAffiliateBank.setStreet3(localBPWFIInfo.getAddr3());
        paramAffiliateBank.setCity(localBPWFIInfo.getCity());
        paramAffiliateBank.setState(localBPWFIInfo.getState());
        paramAffiliateBank.setCountry(localBPWFIInfo.getCountry());
        paramAffiliateBank.setZipCode(localBPWFIInfo.getPostalCode());
        int j = localBPWFIInfo.getACHTransWareHouse();
        if (j == 1) {
          paramAffiliateBank.setWarehousingType(2);
        } else if (j == 0) {
          paramAffiliateBank.setWarehousingType(1);
        }
        paramAffiliateBank.setAchMaxFutureDays(localBPWFIInfo.getACHMaxNoFutureDays());
        String str3 = localBPWFIInfo.getACHSameDayEffDate();
        paramAffiliateBank.setAchSameDayEffDating((str3 != null) && (!str3.equals("N")));
        paramAffiliateBank.setEtfAccountNum(localBPWFIInfo.getETFDepositAcct());
        paramAffiliateBank.setEtfAccountType(localBPWFIInfo.getETFDepositAcctType());
        paramAffiliateBank.setEtfMaximumDepositAmountValue(localBPWFIInfo.getETFMaxDepositAmt());
        paramAffiliateBank.setEtfMinimumDepositAmountValue(localBPWFIInfo.getETFMinDepositAmt());
        paramAffiliateBank.setEtfVirtualUserID(localBPWFIInfo.getETFVirtualUserID());
        paramAffiliateBank.setEtfCompany(a(localBPWFIInfo.getETFCompanyID()));
        if (paramAffiliateBank.getEtfCompany() != null)
        {
          localObject1 = paramAffiliateBank.getEtfCompany();
          paramAffiliateBank.setEtfCompanyBatchDescription(((ExtTransferCompany)localObject1).getBatchDescription());
          paramAffiliateBank.setEtfCompanyName(((ExtTransferCompany)localObject1).getCompanyName());
          paramAffiliateBank.setEtfCompanyID(((ExtTransferCompany)localObject1).getCompanyID());
        }
        Object localObject1 = localACHBPWServices.getFIInfoByRTN(localBPWFIInfo.getFIRTN());
        if ((localObject1 != null) && (localObject1.length > 0) && (localObject1[0].getStatusCode() == 0))
        {
          Object localObject2 = localObject1[0];
          paramAffiliateBank.setACHImmediateOrigin(localObject2.getODFIACHId());
          paramAffiliateBank.setACHImmediateOriginName(localObject2.getODFIName());
          paramAffiliateBank.setACHDestination(localObject2.getRDFIACHId());
          paramAffiliateBank.setACHDestinationName(localObject2.getRDFIName());
        }
      }
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.getAffiliateBank exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      DebugLog.log("ACHService.getAffiliateBank exception: " + localException.getMessage());
      throw new CSILException(-1009, 25016, localException);
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return paramAffiliateBank;
  }
  
  public AffiliateBanks getAffiliateBanks(SecureUser paramSecureUser, AffiliateBanks paramAffiliateBanks, HashMap paramHashMap)
    throws CSILException
  {
    int i = 0;
    ACHBPWServices localACHBPWServices = getACHHandler();
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      HashMap localHashMap = new HashMap();
      String[] arrayOfString = new String[paramAffiliateBanks.size()];
      String str1 = null;
      int j = 0;
      AffiliateBank localAffiliateBank = null;
      Iterator localIterator = paramAffiliateBanks.iterator();
      while (localIterator.hasNext())
      {
        localAffiliateBank = (AffiliateBank)localIterator.next();
        str1 = localAffiliateBank.getFIBPWID().trim();
        if ((str1 != null) && (str1.length() > 0) && (!str1.equals("0")))
        {
          arrayOfString[(j++)] = str1;
          localHashMap.put(str1, localAffiliateBank);
        }
      }
      if (localHashMap.size() > 0)
      {
        BPWFIInfo[] arrayOfBPWFIInfo = localBPWServices.getBPWFIInfo(arrayOfString);
        i = arrayOfBPWFIInfo[0].getStatusCode();
        if (i != 0) {
          throw new CSILException(-1009, 25007);
        }
        if (arrayOfBPWFIInfo.length > 0)
        {
          BPWFIInfo localBPWFIInfo = null;
          for (j = 0; j < arrayOfBPWFIInfo.length; j++)
          {
            localBPWFIInfo = arrayOfBPWFIInfo[j];
            if ((localBPWFIInfo != null) && (localBPWFIInfo.getFIRTN() != null))
            {
              localAffiliateBank = (AffiliateBank)localHashMap.get(localBPWFIInfo.getFIId());
              localAffiliateBank.setDescription(localBPWFIInfo.getFIDesc());
              localAffiliateBank.setAffiliateRoutingNum(localBPWFIInfo.getFIRTN());
              localAffiliateBank.setSwiftBIC(localBPWFIInfo.getSwiftRTN());
              localAffiliateBank.setCurrencyCode(localBPWFIInfo.getCurrencyCode());
              localAffiliateBank.setStreet(localBPWFIInfo.getAddr1());
              localAffiliateBank.setStreet2(localBPWFIInfo.getAddr2());
              localAffiliateBank.setStreet3(localBPWFIInfo.getAddr3());
              localAffiliateBank.setCity(localBPWFIInfo.getCity());
              localAffiliateBank.setState(localBPWFIInfo.getState());
              localAffiliateBank.setCountry(localBPWFIInfo.getCountry());
              localAffiliateBank.setZipCode(localBPWFIInfo.getPostalCode());
              int k = localBPWFIInfo.getACHTransWareHouse();
              if (k == 1) {
                localAffiliateBank.setWarehousingType(2);
              } else if (k == 0) {
                localAffiliateBank.setWarehousingType(1);
              }
              localAffiliateBank.setAchMaxFutureDays(localBPWFIInfo.getACHMaxNoFutureDays());
              String str2 = localBPWFIInfo.getACHSameDayEffDate();
              localAffiliateBank.setAchSameDayEffDating((str2 != null) && (!str2.equals("N")));
              localAffiliateBank.setEtfAccountNum(localBPWFIInfo.getETFDepositAcct());
              localAffiliateBank.setEtfAccountType(localBPWFIInfo.getETFDepositAcctType());
              localAffiliateBank.setEtfMaximumDepositAmountValue(localBPWFIInfo.getETFMaxDepositAmt());
              localAffiliateBank.setEtfMinimumDepositAmountValue(localBPWFIInfo.getETFMinDepositAmt());
              localAffiliateBank.setEtfVirtualUserID(localBPWFIInfo.getETFVirtualUserID());
              localAffiliateBank.setEtfCompany(a(localBPWFIInfo.getETFCompanyID()));
              if (localAffiliateBank.getEtfCompany() != null)
              {
                localObject1 = localAffiliateBank.getEtfCompany();
                localAffiliateBank.setEtfCompanyBatchDescription(((ExtTransferCompany)localObject1).getBatchDescription());
                localAffiliateBank.setEtfCompanyName(((ExtTransferCompany)localObject1).getCompanyName());
                localAffiliateBank.setEtfCompanyID(((ExtTransferCompany)localObject1).getCompanyID());
              }
              Object localObject1 = localACHBPWServices.getFIInfoByRTN(localBPWFIInfo.getFIRTN());
              if ((localObject1 != null) && (localObject1.length > 0) && (localObject1[0].getStatusCode() == 0))
              {
                Object localObject2 = localObject1[0];
                localAffiliateBank.setACHImmediateOrigin(localObject2.getODFIACHId());
                localAffiliateBank.setACHImmediateOriginName(localObject2.getODFIName());
                localAffiliateBank.setACHDestination(localObject2.getRDFIACHId());
                localAffiliateBank.setACHDestinationName(localObject2.getRDFIName());
              }
            }
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.getAffiliateBank exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      DebugLog.log("ACHService.getAffiliateBank exception: " + localException.getMessage());
      throw new CSILException(-1009, 25016, localException);
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return paramAffiliateBanks;
  }
  
  private ExtTransferCompany a(String paramString)
    throws CSILException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return null;
    }
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(25018);
    }
    int i = 0;
    String str1 = "";
    ExtTransferCompany localExtTransferCompany = null;
    ExtTransferCompanyInfo localExtTransferCompanyInfo = new ExtTransferCompanyInfo();
    try
    {
      localExtTransferCompanyInfo.setCompId(paramString);
      localExtTransferCompanyInfo = localBPWServices.getExtTransferCompany(localExtTransferCompanyInfo);
      i = localExtTransferCompanyInfo.getStatusCode();
      if (i != 0)
      {
        DebugLog.log("Error occurred when getting an External Transfer Company:");
        DebugLog.log("*** BPW ErrorCode: " + i);
        str1 = localExtTransferCompanyInfo.getStatusMsg();
        DebugLog.log("*** BPW ErrorMsg: " + str1);
        throw new CSILException(39712, i, "BPW getExtTransferCompanyForBank", str1);
      }
      localExtTransferCompany = BeansConverter.getExtTransferCompany(localExtTransferCompanyInfo);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACHService.getExtTransferCompanyForBank exception: " + localThrowable.getMessage());
      if ((localThrowable instanceof CSILException)) {
        throw ((CSILException)localThrowable);
      }
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
      throw new CSILException(39712);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    return localExtTransferCompany;
  }
  
  public boolean isBusinessRegisteredWithBPW(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    boolean bool = false;
    try
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramString;
      CustomerInfo[] arrayOfCustomerInfo = localBPWServices.getCustomersInfo(arrayOfString);
      if ((arrayOfCustomerInfo != null) && (arrayOfCustomerInfo.length > 0) && (arrayOfCustomerInfo[0].customerID.equals(paramString))) {
        bool = true;
      }
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.isBusinessRegisteredWithBPW exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (Exception localException)
    {
      DebugLog.log("ACHService.isBusinessRegisteredWithBPW exception: " + localException.getMessage());
      throw new CSILException(-1009, 25020, localException);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return bool;
  }
  
  public boolean getBusinessRegistrationBPWInfo(SecureUser paramSecureUser, String paramString, Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    boolean bool = false;
    try
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramBusiness.getId();
      CustomerInfo[] arrayOfCustomerInfo = localBPWServices.getCustomersInfo(arrayOfString);
      bool = (arrayOfCustomerInfo != null) && (arrayOfCustomerInfo.length > 0) && (arrayOfCustomerInfo[0].customerID.equals(paramBusiness.getId()));
      if (bool)
      {
        paramBusiness.setAchCreditLeadDays(arrayOfCustomerInfo[0].ACHCreditLeadDays);
        paramBusiness.setAchDebitLeadDays(arrayOfCustomerInfo[0].ACHDebitLeadDays);
      }
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.getBusinessRegistrationBPWInfo exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (Exception localException)
    {
      DebugLog.log("ACHService.getBusinessRegistrationBPWInfo exception: " + localException.getMessage());
      throw new CSILException(-1009, 25020, localException);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return bool;
  }
  
  public void addCustomer(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      CustomerInfo localCustomerInfo = new CustomerInfo();
      localCustomerInfo.customerID = paramString1;
      localCustomerInfo.fIID = paramString2;
      localCustomerInfo.setUserType(UserType.BUSINESS);
      CustomerInfo[] arrayOfCustomerInfo = new CustomerInfo[1];
      arrayOfCustomerInfo[0] = localCustomerInfo;
      int i = localBPWServices.addCustomers(arrayOfCustomerInfo);
      if (i != 1) {
        throw new CSILException(-1009, 25019);
      }
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.addCustomer exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (Exception localException)
    {
      DebugLog.log("ACHService.addCustomer exception: " + localException.getMessage());
      throw new CSILException(-1009, 25019, localException);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  public void deactivateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      if (localBPWServices != null) {
        localBPWServices.deactivateCustomers(paramArrayOfString);
      }
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.deactivateCustomers exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (Exception localException)
    {
      DebugLog.log("ACHService.deactivateCustomers exception: " + localException.getMessage());
      throw new CSILException(-1009, 25026, localException);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  public void activateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      if (localBPWServices != null) {
        localBPWServices.activateCustomers(paramArrayOfString);
      }
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.activateCustomers exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (Exception localException)
    {
      DebugLog.log("ACHService.activateCustomers exception: " + localException.getMessage());
      throw new CSILException(-1009, 25025, localException);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  protected long getPageSize(HashMap paramHashMap)
  {
    long l = this.default_pagesize;
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
    default: 
      i = 1;
    }
    return i;
  }
  
  public int getReleaseACHBatchesCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.getACHBatches", 19003);
  }
  
  public ACHBatches getReleaseACHBatches(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.getACHBatches", 19003);
  }
  
  public void releaseACHBatches(SecureUser paramSecureUser, ACHBatches paramACHBatches, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.getACHBatches", 19003);
  }
  
  protected static ACHBatchInfo[] getReleaseBatchInfos(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.getACHBatches", 19003);
  }
  
  public Date getSmartDate(SecureUser paramSecureUser, DateTime paramDateTime)
    throws CSILException
  {
    int i = 0;
    if (paramDateTime == null) {
      return null;
    }
    DateTime localDateTime = (DateTime)paramDateTime.clone();
    BPWServices localBPWServices = getBPWHandler();
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
      DebugLog.log("ACHService.getSmartDate: Failed to get smartDate  for specified date " + localDateTime.toString());
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
  
  public void processApprovalResult(SecureUser paramSecureUser, Object paramObject, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      Object localObject1;
      if ((paramObject instanceof ACHBatch))
      {
        localObject1 = (ACHBatch)paramObject;
        localBPWServices.processApprovalResult("ACHBATCHTRN", ((ACHBatch)localObject1).getID(), paramString, paramHashMap);
      }
      else if ((paramObject instanceof Transfer))
      {
        localObject1 = (Transfer)paramObject;
        if (((Transfer)localObject1).getTransferDestination().equals("INTERNAL")) {
          localBPWServices.processApprovalResult("INTRATRN", ((Transfer)localObject1).getID(), paramString, paramHashMap);
        } else {
          localBPWServices.processApprovalResult("ETFTRN", ((Transfer)localObject1).getID(), paramString, paramHashMap);
        }
      }
      else if ((paramObject instanceof Payment))
      {
        localObject1 = (Payment)paramObject;
        localBPWServices.processApprovalResult("PMTTRN", ((Payment)localObject1).getID(), paramString, paramHashMap);
      }
      else if ((paramObject instanceof CashCon))
      {
        localObject1 = (CashCon)paramObject;
        localBPWServices.processApprovalResult("CASHCONTRN", ((CashCon)localObject1).getID(), paramString, paramHashMap);
      }
    }
    catch (FFSException localFFSException)
    {
      localFFSException.printStackTrace(System.err);
      int i = a(localFFSException.getErrCode(), 30213);
      throw new CSILException(-1009, i);
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.processApprovalResult exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (Exception localException)
    {
      DebugLog.log("ACHService.processApprovalResult exception: " + localException.getMessage());
      throw new CSILException(-1009, 30213, localException);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  private static int a(int paramInt1, int paramInt2)
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
    case 17522: 
    default: 
      i = paramInt2;
    }
    return i;
  }
  
  public void addCustomer(SecureUser paramSecureUser, String paramString, Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      CustomerInfo localCustomerInfo = new CustomerInfo();
      localCustomerInfo.customerID = paramBusiness.getId();
      localCustomerInfo.firstName = paramBusiness.getBusinessName();
      localCustomerInfo.fIID = paramString;
      localCustomerInfo.ACHCreditLeadDays = paramBusiness.getAchCreditLeadDaysValue();
      localCustomerInfo.ACHDebitLeadDays = paramBusiness.getAchDebitLeadDaysValue();
      localCustomerInfo.setUserType(UserType.BUSINESS);
      if ("Virtual User".equals(paramBusiness.getDescription())) {
        localCustomerInfo.virtualCustomer = "Y";
      }
      CustomerInfo[] arrayOfCustomerInfo = new CustomerInfo[1];
      arrayOfCustomerInfo[0] = localCustomerInfo;
      int i = localBPWServices.addCustomers(arrayOfCustomerInfo);
      if (i != 1) {
        throw new CSILException(-1009, 25019);
      }
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.addCustomer exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (Exception localException)
    {
      DebugLog.log("ACHService.addCustomer exception: " + localException.getMessage());
      throw new CSILException(-1009, 25019, localException);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  public void modifyCustomer(SecureUser paramSecureUser, String paramString, Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = new String("" + paramBusiness.getId());
      CustomerInfo[] arrayOfCustomerInfo = localBPWServices.getCustomersInfo(arrayOfString);
      if (arrayOfCustomerInfo != null)
      {
        CustomerInfo localCustomerInfo = arrayOfCustomerInfo[0];
        localCustomerInfo.customerID = paramBusiness.getId();
        localCustomerInfo.firstName = paramBusiness.getBusinessName();
        localCustomerInfo.fIID = paramString;
        localCustomerInfo.ACHCreditLeadDays = paramBusiness.getAchCreditLeadDaysValue();
        localCustomerInfo.ACHDebitLeadDays = paramBusiness.getAchDebitLeadDaysValue();
        int i = localBPWServices.modifyCustomers(arrayOfCustomerInfo);
        if (i != 1) {
          throw new CSILException(-1009, 25023);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.addCustomer exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (Exception localException)
    {
      DebugLog.log("ACHService.addCustomer exception: " + localException.getMessage());
      throw new CSILException(-1009, 25023, localException);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  public HashSet getEnabledSECCodes(SecureUser paramSecureUser, String paramString, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACHService.getEnabledSECCodes";
    ACHBPWServices localACHBPWServices = getACHHandler();
    ACHSameDayEffDateInfo localACHSameDayEffDateInfo = new ACHSameDayEffDateInfo();
    localACHSameDayEffDateInfo.setObjectType(paramInt);
    localACHSameDayEffDateInfo.setObjectId(paramString);
    try
    {
      localACHSameDayEffDateInfo = localACHBPWServices.getACHSameDayEffDateInfo(localACHSameDayEffDateInfo);
      if (localACHSameDayEffDateInfo.getStatusCode() == 16020)
      {
        localHashSet1 = new HashSet();
        return localHashSet1;
      }
      if (localACHSameDayEffDateInfo.getStatusCode() != 0)
      {
        DebugLog.log(Level.INFO, "*** BPW Error: " + localACHSameDayEffDateInfo.getStatusCode());
        throw new CSILException(16601, localACHSameDayEffDateInfo.getStatusCode(), str, localACHSameDayEffDateInfo.getStatusMsg());
      }
      HashSet localHashSet1 = localACHSameDayEffDateInfo.getSECs();
      if (localHashSet1 == null) {
        localHashSet1 = new HashSet();
      }
      HashSet localHashSet2 = localHashSet1;
      return localHashSet2;
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.addCustomer exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.log("ACHService.addCustomer exception: " + localException.getMessage());
      throw new CSILException(-1009, 16601, localException);
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
  }
  
  public void setEnabledSECCodes(SecureUser paramSecureUser, String paramString, int paramInt, HashSet paramHashSet, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACHService.setEnabledSECCodes";
    ACHBPWServices localACHBPWServices = getACHHandler();
    ACHSameDayEffDateInfo localACHSameDayEffDateInfo = new ACHSameDayEffDateInfo();
    localACHSameDayEffDateInfo.setObjectType(paramInt);
    localACHSameDayEffDateInfo.setObjectId(paramString);
    localACHSameDayEffDateInfo.setSECs(paramHashSet);
    try
    {
      localACHSameDayEffDateInfo = localACHBPWServices.setACHSameDayEffDateInfo(localACHSameDayEffDateInfo);
      if (localACHSameDayEffDateInfo.getStatusCode() != 0)
      {
        DebugLog.log(Level.INFO, "*** BPW Error: " + localACHSameDayEffDateInfo.getStatusCode());
        throw new CSILException(16600, localACHSameDayEffDateInfo.getStatusCode(), str, localACHSameDayEffDateInfo.getStatusMsg());
      }
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService.setEnabledSECCodes exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.log("ACHService.setEnabledSECCodes exception: " + localException.getMessage());
      throw new CSILException(-1009, 16600, localException);
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
  }
  
  public String getDefaultEffectiveDate(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACHService. getDefaultEffectiveDate ";
    ACHBPWServices localACHBPWServices = getACHHandler();
    String str2 = null;
    try
    {
      str2 = localACHBPWServices.getDefaultACHBatchEffectiveDate(paramString1, paramString2);
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("ACHService. getDefaultEffectiveDate exception: " + localRemoteException.getMessage());
      throw new CSILException(-1009, 25018, localRemoteException);
    }
    catch (Exception localException)
    {
      DebugLog.log("ACHService. getDefaultEffectiveDate exception: " + localException.getMessage());
      throw new CSILException(-1009, 16602, localException);
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    return str2;
  }
  
  public ArrayList getDefaultACHFrequencies(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = paramSecureUser.getBPWFIID();
    BPWServices localBPWServices = getBPWHandler();
    ArrayList localArrayList = null;
    try
    {
      BPWFIInfo localBPWFIInfo = localBPWServices.getBPWFIInfo(str1);
      if ((localBPWFIInfo == null) || (localBPWFIInfo.getStatusCode() != 0)) {
        throw new CSILException(-1009, 25007);
      }
      int i = localBPWFIInfo.getACHMaxNoFutureDays();
      localArrayList = new ArrayList();
      localArrayList.add(new Integer(0));
      for (int j = 0; j < BeansConverter.FreqMap.length; j++)
      {
        Integer localInteger = new Integer(BeansConverter.FreqMap[j]);
        localArrayList.add(localInteger);
      }
      if (i <= 90)
      {
        localArrayList.remove(9);
        localArrayList.remove(8);
      }
      if (i < 90) {
        localArrayList.remove(7);
      }
      if (i < 60) {
        localArrayList.remove(6);
      }
      if (i < 30) {
        localArrayList.remove(4);
      }
      if (i < 28) {
        localArrayList.remove(5);
      }
      if (i < 15) {
        localArrayList.remove(3);
      }
      if (i < 14) {
        localArrayList.remove(2);
      }
      if (i < 7) {
        localArrayList.remove(1);
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACHService.getDefaultACHFrequencies exception: " + localThrowable.getMessage());
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return localArrayList;
  }
  
  private static IReportResult a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACHService.getACHFileUploadReportData";
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    String str2;
    try
    {
      Reporting.calculateDateRange(paramSecureUser, null, paramReportCriteria, localHashMap1, localHashMap2, paramHashMap);
      paramHashMap.put("StartDates", localHashMap1);
      paramHashMap.put("EndDates", localHashMap2);
    }
    catch (CSILException localCSILException)
    {
      str2 = "Unable to calculate the date ranges. A report cannot be run.";
      DebugLog.log(str2);
      if (localCSILException.getCode() == -1009) {
        throw new CSILException(str1, localCSILException.getServiceError(), str2);
      }
      throw new CSILException(str1, localCSILException.getCode(), str2);
    }
    try
    {
      return ACHAdapter.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (Exception localException)
    {
      str2 = "Unable to generate report for ACH File Upload: " + localException.toString();
      DebugLog.log(str2);
      throw new CSILException(str1, 16002, str2);
    }
  }
  
  public ACHPayeeList getACHPayList(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, HashMap paramHashMap)
    throws CSILException
  {
    int i = 16002;
    ACHBPWServices localACHBPWServices = null;
    ACHPayeeList localACHPayeeList = new ACHPayeeList();
    String str1 = new String("MM/dd/yyyy HH:mm:ss");
    try
    {
      if (null != paramString4) {
        paramString4 = FFSUtil.convertDateFormat(str1, "yyyy/MM/dd", paramString4);
      }
      if (null != paramString5) {
        paramString5 = FFSUtil.convertDateFormat(str1, "yyyy/MM/dd", paramString5);
      }
      localACHBPWServices = getACHHandler();
      localACHPayeeList.setCustId(paramString1);
      if (paramString2 == null)
      {
        localACHPayeeList.setCompIdList(null);
      }
      else
      {
        String[] arrayOfString = new String[1];
        arrayOfString[0] = new String(paramString2);
        localACHPayeeList.setCompIdList(arrayOfString);
      }
      if (paramString4 != null) {
        localACHPayeeList.setPrenoteDateStart(paramString4);
      }
      if (paramString5 != null) {
        localACHPayeeList.setPrenoteDateEnd(paramString5);
      }
      if (paramString3 != null) {
        localACHPayeeList.setPrenoteCreditStatus(paramString3);
      }
      localACHPayeeList.setPageSize(99999L);
      localACHPayeeList = localACHBPWServices.getACHPayeeList(localACHPayeeList);
      i = 0;
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("ACH.getACHPayList exception: " + localThrowable.getMessage());
      String str2 = localThrowable.toString();
      if ((str2 != null) && (str2.indexOf("Server is not running!") > 0)) {
        throw new CSILException(25018);
      }
    }
    finally
    {
      if (localACHBPWServices != null) {
        removeACHHandler(localACHBPWServices);
      }
    }
    if (i == 0) {
      return localACHPayeeList;
    }
    throw new CSILException(-1009, i);
  }
  
  protected ReportResult getACHPayeePrenoteReport(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, ACHPayeeList paramACHPayeeList, Currency paramCurrency, Locale paramLocale, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACHService.getACHPayeePrenoteReport";
    ReportResult localReportResult = new ReportResult(paramLocale);
    int i = 0;
    int j = Integer.parseInt(getSearchCriteria(paramReportCriteria.getReportOptions(), "MAXDISPLAYSIZE", String.valueOf(2147483647)));
    int k = 0;
    try
    {
      localReportResult.init(paramReportCriteria);
      int m = paramSecureUser.getBusinessID();
      ACHCompanies localACHCompanies1 = getACHCompanies(paramSecureUser, "" + m, "", paramHashMap);
      localACHCompanies1.setSortedBy("CompanyName ASC");
      ACHPayees localACHPayees1 = new ACHPayees();
      Object[] arrayOfObject = null;
      arrayOfObject = paramACHPayeeList.getPayees();
      if ((arrayOfObject != null) && (arrayOfObject.length > 0)) {
        addPayeesToList(paramSecureUser, localACHPayees1, arrayOfObject);
      }
      String str2 = null;
      String str3 = "ID";
      ACHPayees localACHPayees2 = new ACHPayees();
      HashMap localHashMap1 = new HashMap();
      Iterator localIterator = localACHCompanies1.iterator();
      ACHCompanies localACHCompanies2 = (ACHCompanies)paramHashMap.get("ACHCOMPANIES");
      Object localObject1;
      Object localObject2;
      Object localObject4;
      Object localObject5;
      while (localIterator.hasNext())
      {
        ACHCompany localACHCompany = (ACHCompany)localIterator.next();
        localObject1 = localACHCompany.getCompanyName();
        localObject2 = localACHCompany.getID();
        int n = 1;
        if (localACHCompanies2 != null)
        {
          localObject4 = localACHCompanies2.getByID((String)localObject2);
          if (localObject4 != null)
          {
            if (!((ACHCompany)localObject4).getActiveValue()) {
              n = 0;
            }
            if (!((ACHCompany)localObject4).getACHPaymentEntitledValue()) {
              n = 0;
            }
          }
        }
        str2 = "COMPANYID=" + (String)localObject2;
        localACHPayees1.setFilter(str2);
        localACHPayees1.setFilterSortedBy(str3);
        localHashMap1.put(localObject2, localObject1);
        localObject4 = localACHPayees1.iterator();
        while (((Iterator)localObject4).hasNext())
        {
          localObject5 = (ACHPayee)((Iterator)localObject4).next();
          if ((n != 0) || (((ACHPayee)localObject5).getPayeeGroupValue() != 2)) {
            localACHPayees2.add(localObject5);
          }
        }
      }
      Object localObject3;
      if (localACHPayees2.size() > 0)
      {
        localObject1 = paramReportCriteria.getReportOptions();
        localObject2 = ((Properties)localObject1).getProperty("DATEFORMAT");
        localObject3 = null;
        if (localObject2 == null) {
          localObject3 = DateFormatUtil.getFormatter(null);
        } else {
          localObject3 = DateFormatUtil.getFormatter((String)localObject2, paramLocale);
        }
        addACHSubPrenoteReport(paramSecureUser, localReportResult, localACHPayees2, i, j, localHashMap1, (DateFormat)localObject3);
      }
      else
      {
        localObject1 = new ReportResult(paramLocale);
        localReportResult.addSubReport((ReportResult)localObject1);
        localObject2 = null;
        localObject3 = new ReportDataDimensions(paramLocale);
        ((ReportDataDimensions)localObject3).setNumColumns(1);
        ((ReportDataDimensions)localObject3).setNumRows(1);
        ((ReportResult)localObject1).setDataDimensions((ReportDataDimensions)localObject3);
        localObject2 = new ReportColumn(paramLocale);
        ((ReportColumn)localObject2).setDataType("java.lang.String");
        ((ReportColumn)localObject2).setWidthAsPercent(100);
        ((ReportResult)localObject1).addColumn((ReportColumn)localObject2);
        localObject2 = null;
        localObject4 = new ReportRow(paramLocale);
        localObject5 = new ReportDataItems(paramLocale);
        ReportDataItem localReportDataItem = null;
        ((ReportRow)localObject4).setDataItems((ReportDataItems)localObject5);
        localReportDataItem = ((ReportDataItems)localObject5).add();
        localReportDataItem.setData(ReportConsts.getText(2505, paramLocale));
        localReportDataItem = null;
        localObject5 = null;
        ((ReportResult)localObject1).addRow((ReportRow)localObject4);
        localObject4 = null;
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
        HashMap localHashMap2 = new HashMap();
        localHashMap2.put("TRUNCATED", new Integer(i));
        localReportResult.setProperties(localHashMap2);
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
  
  protected void addACHSubPrenoteReport(SecureUser paramSecureUser, ReportResult paramReportResult, ACHPayees paramACHPayees, int paramInt1, int paramInt2, HashMap paramHashMap, DateFormat paramDateFormat)
    throws CSILException, RptException
  {
    if (paramInt1 == paramInt2) {
      return;
    }
    String str1 = "ACHService.addACHSubPrenoteReport";
    ReportResult localReportResult = new ReportResult(paramReportResult.getLocale());
    paramReportResult.addSubReport(localReportResult);
    Locale localLocale = paramReportResult.getLocale();
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(8);
    localReportDataDimensions.setNumRows(-1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = new ReportColumn(localLocale);
    ArrayList localArrayList = new ArrayList();
    String str2 = "";
    str2 = ReportConsts.getColumnName(2638, localLocale);
    localArrayList.add(str2);
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    a(localReportResult, 2631, "java.lang.String", 13, null);
    a(localReportResult, 2630, "java.lang.String", 15, null);
    a(localReportResult, 2633, "java.lang.String", 17, null);
    a(localReportResult, 2632, "java.lang.String", 15, null);
    a(localReportResult, 2635, "java.lang.String", 10, null);
    a(localReportResult, 2636, "java.lang.String", 10, null);
    a(localReportResult, 2637, "java.lang.String", 10, null);
    Iterator localIterator = paramACHPayees.iterator();
    while (localIterator.hasNext())
    {
      ACHPayee localACHPayee = (ACHPayee)localIterator.next();
      String str3 = "";
      String str4 = "";
      String str5 = "";
      String str6 = "";
      str3 = localACHPayee.getNickName();
      if ((str3 == null) || (str3.length() == 0)) {
        str3 = localACHPayee.getName();
      }
      if (str3 == null) {
        str3 = "";
      }
      if (localACHPayee.getUserAccountNumber() != null) {
        str4 = localACHPayee.getUserAccountNumber();
      }
      if (localACHPayee.getDisplayAsSecurePayeeValue())
      {
        str5 = localACHPayee.getSecurePayeeMask();
        str6 = localACHPayee.getSecurePayeeMask();
        str3 = localACHPayee.getSecurePayeeMask();
      }
      else
      {
        str5 = localACHPayee.getAccountNumber() + " (" + localACHPayee.getAccountType() + ")";
        str6 = localACHPayee.getRoutingNumber();
      }
      ReportRow localReportRow = new ReportRow(localReportResult.getLocale());
      ReportDataItems localReportDataItems = new ReportDataItems(localReportResult.getLocale());
      localReportRow.setDataItems(localReportDataItems);
      String str7 = (String)paramHashMap.get(localACHPayee.getCompanyID());
      ReportDataItem localReportDataItem = localReportDataItems.add();
      setDataItem(localReportDataItem, localACHPayee.getPayeeGroupValue() != 2 ? localACHPayee.getPayeeGroup() : str7);
      localReportDataItem = localReportDataItems.add();
      setDataItem(localReportDataItem, str4);
      localReportDataItem = localReportDataItems.add();
      setDataItem(localReportDataItem, str3);
      localReportDataItem = localReportDataItems.add();
      setDataItem(localReportDataItem, str5);
      localReportDataItem = localReportDataItems.add();
      setDataItem(localReportDataItem, str6);
      localReportDataItem = localReportDataItems.add();
      setDataItem(localReportDataItem, localACHPayee.getPrenoteStatusDisplay());
      localReportDataItem = localReportDataItems.add();
      String str8 = "";
      if (localACHPayee.getPrenoteSubmitDateValue() != null) {
        str8 = paramDateFormat.format(localACHPayee.getPrenoteSubmitDateValue().getTime());
      }
      setDataItem(localReportDataItem, str8);
      localReportDataItem = localReportDataItems.add();
      String str9 = "";
      if (localACHPayee.getPrenoteMatureDateValue() != null) {
        str9 = paramDateFormat.format(localACHPayee.getPrenoteMatureDateValue().getTime());
      }
      setDataItem(localReportDataItem, str9);
      if (paramInt1 % 2 == 1) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      localReportResult.addRow(localReportRow);
      paramInt1++;
    }
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
      if (paramString1.equals("ACHCallBackJNDIName")) {
        ACHService.this.ach_callback_JNDI_name = paramString2;
      } else if (paramString1.equals("ACHTransactions")) {
        ACHService.this.maxTransactionDays = new Integer(paramString2).intValue();
      } else if (paramString1.equals("BPWCallBackJNDIName")) {
        ACHService.this.bpwJNDIName = paramString2;
      } else {
        super.attribute(paramString1, paramString2, paramBoolean);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.banksim2.ACHService
 * JD-Core Version:    0.7.0.1
 */