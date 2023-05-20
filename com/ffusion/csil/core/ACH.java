package com.ffusion.csil.core;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHAddendas;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHCompanySummaries;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.beans.ach.TaxForm;
import com.ffusion.beans.ach.TaxForms;
import com.ffusion.beans.bcreport.ReportLogRecords;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.ReportAuditAdapter;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.entitlements.ParentEntitlementsCache;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

public class ACH
  extends Initialize
{
  private static Entitlement aFN = new Entitlement("ACHBatch", null, null);
  private static Entitlement aFE = new Entitlement("BC ACH Management", null, null);
  private static Entitlement aFI = new Entitlement("BusinessProfileCreate", null, null);
  private static Entitlement aFU = new Entitlement("BusinessProfileEdit", null, null);
  private static Entitlement aFK = new Entitlement("BusinessProfileView", null, null);
  private static Entitlement aFZ = new Entitlement("CCD + TXP", null, null);
  private static Entitlement aEu = new Entitlement("CCD + DED", null, null);
  private static Entitlement aEU = new Entitlement("ACH File Upload", null, null);
  private static Entitlement aF0 = new Entitlement("Manage ACH Participants", null, null);
  private static final String aFO = "com.ffusion.util.logging.audit.ach";
  private static final String aEF = "AuditMessage_0";
  private static final String aFS = "AuditMessage_1.1";
  private static final String aFM = "AuditMessage_1.2";
  private static final String aEK = "AuditMessage_1.3";
  private static final String aFA = "AuditMessage_1.4";
  private static final String aFq = "AuditMessage_2.1";
  private static final String aET = "AuditMessage_2.2";
  private static final String aEj = "AuditMessage_2.3";
  private static final String aGf = "AuditMessage_2.4";
  private static final String aF1 = "AuditMessage_3";
  private static final String aE6 = "AuditMessage_4";
  private static final String aEd = "AuditMessage_5.1";
  private static final String aFW = "AuditMessage_5.2";
  private static final String aEo = "AuditMessage_5.3";
  private static final String aEz = "AuditMessage_5.4";
  private static final String aFY = "AuditMessage_6.1";
  private static final String aEr = "AuditMessage_6.2";
  private static final String aEk = "AuditMessage_7";
  private static final String aGb = "AuditMessage_8";
  private static final String aFC = "AuditMessage_9";
  private static final String aFt = "AuditMessage_10";
  private static final String aEM = "AuditMessage_11.1";
  private static final String aEg = "AuditMessage_11.2";
  private static final String aFp = "AuditMessage_12";
  private static final String aEA = "AuditMessage_13";
  private static final String aFd = "AuditMessage_14";
  private static final String aE8 = "AuditMessage_15";
  private static final String aFJ = "AuditMessage_16";
  private static final String aEv = "AuditMessage_17";
  private static final String aEN = "AuditMessage_18";
  private static final String aFo = "AuditMessage_19";
  private static final String aF4 = "AuditMessage_20.1";
  private static final String aEQ = "AuditMessage_20.2";
  private static final String aFF = "AuditMessage_21.1";
  private static final String aEV = "AuditMessage_21.2";
  private static final String aEx = "AuditMessage_22.1";
  private static final String aFT = "AuditMessage_22.2";
  private static final String aFs = "AuditMessage_23.1";
  private static final String aFR = "AuditMessage_23.2";
  private static final String aF2 = "AuditMessage_24.1";
  private static final String aFG = "AuditMessage_24.2";
  private static final String aE0 = "AuditMessage_25";
  private static final String aES = "AuditMessage_26";
  private static final String aE9 = "AuditMessage_27";
  private static final String aEq = "AuditMessage_28";
  private static final String aEm = "AuditMessage_29";
  private static final String aFD = "AuditMessage_30";
  private static final String aGa = "AuditMessage_31";
  private static final String aEt = "AuditMessage_32";
  private static final String aEJ = "AuditMessage_33";
  private static final String aFH = "AuditMessage_34";
  private static final String aEW = "AuditMessage_35";
  private static final String aGe = "AuditMessage_36";
  private static final String aFb = "AuditMessage_37";
  private static final String aF8 = "AuditEntFault_1";
  private static final String aF7 = "AuditEntFault_2";
  private static final String aEh = "AuditEntFault_3";
  private static final String aEf = "AuditEntFault_4";
  private static final String aF6 = "AuditEntFault_5";
  private static final String aF3 = "AuditEntFault_6";
  private static final String aE7 = "AuditEntFault_7";
  private static final String aEw = "AuditEntFault_8";
  private static final String aEi = "AuditEntFault_9";
  private static final String aEn = "AuditEntFault_10";
  private static final String aEI = "AuditEntFault_11";
  private static final String aE4 = "AuditEntFault_12";
  private static final String aEe = "AuditEntFault_13";
  private static final String aFx = "AuditEntFault_14";
  private static final String aE3 = "AuditEntFault_15";
  private static final String aEE = "AuditEntFault_16";
  private static final String aEc = "AuditEntFault_17";
  private static final String aFX = "AuditEntFault_18";
  private static final String aEG = "AuditEntFault_19";
  private static final String aEH = "AuditEntFault_20";
  private static final String aEZ = "AuditEntFault_21";
  private static final String aFL = "AuditEntFault_22";
  private static final String aED = "AuditEntFault_23";
  private static final String aFz = "AuditEntFault_24";
  private static final String aFw = "AuditEntFault_25";
  private static final String aGc = "AuditEntFault_26";
  private static final String aFn = "AuditEntFault_27";
  private static final String aEs = "AuditEntFault_28";
  private static final String aEy = "AuditEntFault_29";
  private static final String aF9 = "AuditEntFault_30";
  private static final String aF5 = "AuditEntFault_31";
  private static final String aFg = "AuditEntFault_32";
  private static final String aFP = "AuditEntFault_33";
  private static final String aEL = "AuditEntFault_34";
  private static final String aFi = "AuditEntFault_35";
  private static final String aEC = "AuditEntFault_36";
  private static final String aGd = "AuditEntFault_37";
  private static final String aFr = "AuditEntFault_38";
  private static final String aEX = "AuditEntFault_39";
  private static final String aFl = "AuditEntFault_40";
  private static final String aER = "AuditEntFault_41";
  private static final String aEB = "AuditEntFault_42";
  private static final String aFf = "AuditEntFault_43";
  private static final String aEP = "AuditEntFault_44";
  private static final String aFQ = "AuditEntFault_45";
  private static final String aEl = "AuditEntFault_46";
  private static final String aFc = "AuditEntFault_47";
  private static final String aEO = "AuditEntFault_48";
  private static final String aFh = "AuditEntFault_49";
  private static final String aFB = "AuditEntFault_50";
  private static final String aE1 = "AuditEntFault_51";
  private static final String aE5 = "AuditEntFault_52";
  private static final String aFa = "AuditEntFault_53";
  private static final String aFm = "AuditEntFault_54";
  private static final String aEY = "AuditEntFault_55";
  private static final String aFe = "AuditEntFault_56";
  private static final String aE2 = "AuditEntFault_57";
  private static final String aFV = "AuditEntFault_58";
  private static final String aFu = "AuditEntFault_59";
  private static final String aEp = "AuditEntFault_60";
  private static final String aFy = "AuditEntFault_61";
  private static final String aFv = "AuditEntFault_62";
  private static final String aFk = "AuditEntFault_63";
  private static final String aFj = "AuditEntFault_64";
  public static final String ACH_AUDIT_STATE_ADDED = "ADDED";
  public static final String ACH_AUDIT_STATE_DELETED = "DELETED";
  public static final String ACH_AUDIT_STATE_MODIFIED = "MODIFIED";
  public static final String ACH_AUDIT_STATE_FAILED = "FAILED";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.ACH.initialize");
    com.ffusion.csil.handlers.ACH.initialize(paramHashMap);
  }
  
  public static Object getService(String paramString)
  {
    return com.ffusion.csil.handlers.ACH.getService(paramString);
  }
  
  public static ACHBatch addACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.AddACHBatch";
    int i = 0;
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString = null;
    if (checkACHSECEntitlement(paramSecureUser, paramACHBatch, "ACH Payment Entry", paramHashMap))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str3 = paramACHBatch.getTrackingID();
      if (str3 == null) {
        str3 = TrackingIDGenerator.GetNextID();
      }
      paramACHBatch.setTrackingID(str3);
      paramACHBatch.setSubmittedBy(paramSecureUser.getProfileID());
      paramACHBatch.fixupEntriesAmount();
      if ("REVERSAL".equals(paramACHBatch.getCoEntryDesc())) {
        i = 1;
      }
      String str4 = "";
      String str5 = "";
      int j = 0;
      String str6 = paramACHBatch.getTypeString();
      if ((paramACHBatch.getTemplateID() != null) && (paramACHBatch.getTemplateID().length() > 0) && (paramACHBatch.getTemplateName() != null) && (paramACHBatch.getTemplateName().toUpperCase().indexOf("SINGLE USE") == -1))
      {
        str4 = paramACHBatch.getBatchScope();
        str5 = paramACHBatch.getTemplateName();
        j = 1;
      }
      int k = 1300;
      int m = 14;
      if (paramACHBatch.getTaxForm() != null)
      {
        Entitlement localEntitlement = null;
        if (paramACHBatch.getTaxForm().getAddendaFormat().equals("DED"))
        {
          localEntitlement = new Entitlement("CCD + DED", "State", paramACHBatch.getTaxForm().getState());
          k = 1350;
          m = 16;
        }
        else
        {
          localEntitlement = new Entitlement("CCD + TXP", "State", paramACHBatch.getTaxForm().getState());
          k = 1310;
          m = 15;
        }
        if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement))
        {
          arrayOfObject = new Object[1];
          arrayOfObject[0] = paramACHBatch.getTaxForm().getState();
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_1", arrayOfObject);
          logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
          throw new CSILException(str1, 20001);
        }
      }
      try
      {
        String str2;
        try
        {
          paramACHBatch = com.ffusion.csil.handlers.ACH.addACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
        }
        catch (CSILException localCSILException1)
        {
          arrayOfObject = new Object[4];
          arrayOfObject[0] = str6;
          arrayOfObject[1] = paramACHBatch.getName();
          if (j != 0)
          {
            arrayOfObject[2] = str4;
            arrayOfObject[3] = str5;
            if (i != 0) {
              str2 = "AuditMessage_1.4";
            } else {
              str2 = "AuditMessage_1.2";
            }
          }
          else if (i != 0)
          {
            str2 = "AuditMessage_1.3";
          }
          else
          {
            str2 = "AuditMessage_1.1";
          }
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", str2, arrayOfObject);
          if ((localCSILException1.why != null) && (localCSILException1.why.length() > 0))
          {
            arrayOfObject = new Object[2];
            arrayOfObject[0] = localLocalizableString;
            arrayOfObject[1] = localCSILException1.why;
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
          }
          audit(paramACHBatch.getAuditRecord(paramSecureUser, localLocalizableString, k, "FAILED"));
          throw localCSILException1;
        }
        arrayOfObject = new Object[4];
        arrayOfObject[0] = str6;
        arrayOfObject[1] = paramACHBatch.getName();
        if (j != 0)
        {
          arrayOfObject[2] = str4;
          arrayOfObject[3] = str5;
          if (i != 0) {
            str2 = "AuditMessage_2.4";
          } else {
            str2 = "AuditMessage_2.2";
          }
        }
        else if (i != 0)
        {
          str2 = "AuditMessage_2.3";
        }
        else
        {
          str2 = "AuditMessage_2.1";
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", str2, arrayOfObject);
        audit(paramACHBatch.getAuditRecord(paramSecureUser, localLocalizableString, k, "ADDED"));
      }
      catch (CSILException localCSILException2)
      {
        throw localCSILException2;
      }
      PerfLog.log(str1, l, true);
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, m, paramACHBatch.getID(), str3);
      paramACHBatch.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      debug(paramSecureUser, str1);
      return paramACHBatch;
    }
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_2", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static ACHBatch modifyACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch1, ACHBatch paramACHBatch2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.ModifyACHBatch";
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString = null;
    if (checkACHSECEntitlement(paramSecureUser, paramACHBatch1, "ACH Payment Entry", paramHashMap))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = paramACHBatch2.getTrackingID();
      if (str2 == null) {
        str2 = TrackingIDGenerator.GetNextID();
      }
      paramACHBatch1.setTrackingID(str2);
      paramACHBatch1.fixupEntriesAmount();
      int i = 1301;
      int j = 14;
      String str3 = paramACHBatch1.getTypeString();
      if (paramACHBatch1.getTaxForm() != null)
      {
        Entitlement localEntitlement = null;
        if (paramACHBatch1.getTaxForm().getAddendaFormat().equals("DED"))
        {
          localEntitlement = new Entitlement("CCD + DED", "State", paramACHBatch1.getTaxForm().getState());
          i = 1351;
          j = 16;
        }
        else
        {
          localEntitlement = new Entitlement("CCD + TXP", "State", paramACHBatch1.getTaxForm().getState());
          i = 1311;
          j = 15;
        }
        if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement))
        {
          arrayOfObject = new Object[1];
          arrayOfObject[0] = paramACHBatch1.getTaxForm().getState();
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_3", arrayOfObject);
          logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
          throw new CSILException(str1, 20001);
        }
      }
      try
      {
        paramACHBatch1 = com.ffusion.csil.handlers.ACH.modifyACHBatch(paramSecureUser, paramACHBatch1, paramACHBatch2, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        arrayOfObject = new Object[2];
        arrayOfObject[0] = str3;
        arrayOfObject[1] = paramACHBatch1.getName();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_3", arrayOfObject);
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0))
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableString;
          arrayOfObject[1] = localCSILException.why;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
        }
        audit(paramACHBatch1.getAuditRecord(paramSecureUser, localLocalizableString, i, "FAILED"));
        throw localCSILException;
      }
      arrayOfObject = new Object[2];
      arrayOfObject[0] = str3;
      arrayOfObject[1] = paramACHBatch1.getName();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_4", arrayOfObject);
      audit(paramACHBatch1.getAuditRecord(paramSecureUser, localLocalizableString, i, "MODIFIED"));
      ACHBatch localACHBatch = paramACHBatch1;
      PerfLog.log(str1, l, true);
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, j, paramACHBatch1.getID(), str2);
      paramACHBatch1.logChanges(localHistoryTracker, paramACHBatch2, localHistoryTracker.buildLocalizableComment(17));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      debug(paramSecureUser, str1);
      return localACHBatch;
    }
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_4", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void deleteACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.DeleteACHBatch";
    int i = 0;
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString = null;
    if (checkACHSECEntitlement(paramSecureUser, paramACHBatch, "ACH Payment Entry", paramHashMap))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str3 = paramACHBatch.getTrackingID();
      int j = 1302;
      int k = 14;
      String str4 = paramACHBatch.getTypeString();
      if ("REVERSAL".equals(paramACHBatch.getCoEntryDesc())) {
        i = 1;
      }
      if (paramACHBatch.getTaxForm() != null)
      {
        Entitlement localEntitlement = null;
        if (paramACHBatch.getTaxForm().getAddendaFormat().equals("DED"))
        {
          localEntitlement = new Entitlement("CCD + DED", "State", paramACHBatch.getTaxForm().getState());
          j = 1352;
          k = 16;
        }
        else
        {
          localEntitlement = new Entitlement("CCD + TXP", "State", paramACHBatch.getTaxForm().getState());
          j = 1312;
          k = 15;
        }
        if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement))
        {
          arrayOfObject = new Object[1];
          arrayOfObject[0] = paramACHBatch.getTaxForm().getState();
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_5", arrayOfObject);
          logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
          throw new CSILException(str1, 20001);
        }
      }
      String str2;
      try
      {
        jdMethod_for(paramSecureUser, paramACHBatch, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        arrayOfObject = new Object[2];
        arrayOfObject[0] = str4;
        arrayOfObject[1] = paramACHBatch.getName();
        if ((paramACHBatch.getStatusValue() == 8) || (paramACHBatch.getStatusValue() == 10) || (paramACHBatch.getStatusValue() == 9))
        {
          if (i != 0) {
            str2 = "AuditMessage_5.4";
          } else {
            str2 = "AuditMessage_5.2";
          }
        }
        else if (i != 0) {
          str2 = "AuditMessage_5.3";
        } else {
          str2 = "AuditMessage_5.1";
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", str2, arrayOfObject);
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0))
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableString;
          arrayOfObject[1] = localCSILException.why;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
        }
        audit(paramACHBatch.getAuditRecord(paramSecureUser, localLocalizableString, j, "FAILED"));
        throw localCSILException;
      }
      PerfLog.log(str1, l, true);
      arrayOfObject = new Object[2];
      arrayOfObject[0] = str4;
      arrayOfObject[1] = paramACHBatch.getName();
      if (i != 0) {
        str2 = "AuditMessage_6.2";
      } else {
        str2 = "AuditMessage_6.1";
      }
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", str2, arrayOfObject);
      audit(paramACHBatch.getAuditRecord(paramSecureUser, localLocalizableString, j, "DELETED"));
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, k, paramACHBatch.getID(), str3);
      paramACHBatch.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      debug(paramSecureUser, str1);
    }
    else
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_6", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static ACHBatch addRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.AddRecACHBatch";
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString = null;
    if (checkACHSECEntitlement(paramSecureUser, paramACHBatch, "ACH Payment Entry", paramHashMap))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = paramACHBatch.getTrackingID();
      if (str2 == null) {
        str2 = TrackingIDGenerator.GetNextID();
      }
      paramACHBatch.setTrackingID(str2);
      paramACHBatch.fixupEntriesAmount();
      int i = 1303;
      int j = 14;
      if (paramACHBatch.getTaxForm() != null)
      {
        Entitlement localEntitlement = null;
        if (paramACHBatch.getTaxForm().getAddendaFormat().equals("DED"))
        {
          localEntitlement = new Entitlement("CCD + DED", "State", paramACHBatch.getTaxForm().getState());
          i = 1353;
          j = 16;
        }
        else
        {
          localEntitlement = new Entitlement("CCD + TXP", "State", paramACHBatch.getTaxForm().getState());
          i = 1313;
          j = 15;
        }
        if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement)) {
          throw new CSILException(str1, 20001);
        }
      }
      try
      {
        try
        {
          paramACHBatch = com.ffusion.csil.handlers.ACH.addRecACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
        }
        catch (CSILException localCSILException1)
        {
          arrayOfObject = new Object[1];
          arrayOfObject[0] = paramACHBatch.getName();
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_7", arrayOfObject);
          if ((localCSILException1.why != null) && (localCSILException1.why.length() > 0))
          {
            arrayOfObject = new Object[2];
            arrayOfObject[0] = localLocalizableString;
            arrayOfObject[1] = localCSILException1.why;
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
          }
          audit(paramACHBatch.getAuditRecord(paramSecureUser, localLocalizableString, i, "FAILED"));
          throw localCSILException1;
        }
        arrayOfObject = new Object[1];
        arrayOfObject[0] = paramACHBatch.getName();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_8", arrayOfObject);
        audit(paramACHBatch.getAuditRecord(paramSecureUser, localLocalizableString, i, "ADDED"));
      }
      catch (CSILException localCSILException2)
      {
        throw localCSILException2;
      }
      PerfLog.log(str1, l, true);
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, j, paramACHBatch.getID(), str2);
      paramACHBatch.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      debug(paramSecureUser, str1);
      return paramACHBatch;
    }
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_7", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static ACHBatch modifyRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch1, ACHBatch paramACHBatch2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.ModifyRecACHBatch";
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString = null;
    if (checkACHSECEntitlement(paramSecureUser, paramACHBatch1, "ACH Payment Entry", paramHashMap))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = paramACHBatch2.getTrackingID();
      if (str2 == null) {
        str2 = TrackingIDGenerator.GetNextID();
      }
      paramACHBatch1.setTrackingID(str2);
      paramACHBatch1.fixupEntriesAmount();
      int i = 1304;
      int j = 14;
      if (paramACHBatch1.getTaxForm() != null)
      {
        Entitlement localEntitlement = null;
        if (paramACHBatch1.getTaxForm().getAddendaFormat().equals("DED"))
        {
          localEntitlement = new Entitlement("CCD + DED", "State", paramACHBatch1.getTaxForm().getState());
          i = 1354;
          j = 16;
        }
        else
        {
          localEntitlement = new Entitlement("CCD + TXP", "State", paramACHBatch1.getTaxForm().getState());
          i = 1314;
          j = 15;
        }
        if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement)) {
          throw new CSILException(str1, 20001);
        }
      }
      try
      {
        paramACHBatch1 = com.ffusion.csil.handlers.ACH.modifyRecACHBatch(paramSecureUser, paramACHBatch1, paramACHBatch2, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = paramACHBatch1.getName();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_9", arrayOfObject);
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0))
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableString;
          arrayOfObject[1] = localCSILException.why;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
        }
        audit(paramACHBatch1.getAuditRecord(paramSecureUser, localLocalizableString, i, "FAILED"));
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramACHBatch1.getName();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_10", arrayOfObject);
      audit(paramACHBatch1.getAuditRecord(paramSecureUser, localLocalizableString, i, "MODIFIED"));
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, j, paramACHBatch1.getID(), str2);
      paramACHBatch1.logChanges(localHistoryTracker, paramACHBatch2, localHistoryTracker.buildLocalizableComment(17));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return paramACHBatch1;
    }
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_8", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void deleteRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.DeleteRecACHBatch";
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString = null;
    if (checkACHSECEntitlement(paramSecureUser, paramACHBatch, "ACH Payment Entry", paramHashMap))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = paramACHBatch.getTrackingID();
      int i = 1305;
      int j = 14;
      if (paramACHBatch.getTaxForm() != null)
      {
        Entitlement localEntitlement = null;
        if (paramACHBatch.getTaxForm().getAddendaFormat().equals("DED"))
        {
          localEntitlement = new Entitlement("CCD + DED", "State", paramACHBatch.getTaxForm().getState());
          i = 1355;
          j = 16;
        }
        else
        {
          localEntitlement = new Entitlement("CCD + TXP", "State", paramACHBatch.getTaxForm().getState());
          i = 1315;
          j = 15;
        }
        if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement)) {
          throw new CSILException(str1, 20001);
        }
      }
      try
      {
        jdMethod_for(paramSecureUser, paramACHBatch, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = paramACHBatch.getName();
        if ((paramACHBatch.getStatusValue() == 8) || (paramACHBatch.getStatusValue() == 10) || (paramACHBatch.getStatusValue() == 9)) {
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_11.2", arrayOfObject);
        } else {
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_11.1", arrayOfObject);
        }
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0))
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableString;
          arrayOfObject[1] = localCSILException.why;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
        }
        audit(paramACHBatch.getAuditRecord(paramSecureUser, localLocalizableString, i, "FAILED"));
        throw localCSILException;
      }
      PerfLog.log(str1, l, true);
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramACHBatch.getName();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_12", arrayOfObject);
      audit(paramACHBatch.getAuditRecord(paramSecureUser, localLocalizableString, i, "DELETED"));
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, j, paramACHBatch.getID(), str2);
      paramACHBatch.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      debug(paramSecureUser, str1);
    }
    else
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_9", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static ACHBatches getACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetACHBatches";
    if (jdMethod_byte(paramSecureUser, paramString2))
    {
      long l = System.currentTimeMillis();
      ACHBatches localACHBatches = com.ffusion.csil.handlers.ACH.getACHBatches(paramSecureUser, paramString1, paramString2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHBatches;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_10", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHBatches getRecACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetRecACHBatches";
    if (jdMethod_byte(paramSecureUser, paramString2))
    {
      long l = System.currentTimeMillis();
      ACHBatches localACHBatches = com.ffusion.csil.handlers.ACH.getRecACHBatches(paramSecureUser, paramString1, paramString2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHBatches;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_11", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHBatch getACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetACHBatch";
    if (checkACHSECEntitlement(paramSecureUser, paramACHBatch, null, paramHashMap))
    {
      long l = System.currentTimeMillis();
      ACHBatch localACHBatch = com.ffusion.csil.handlers.ACH.getACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHBatch;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_12", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHBatch getRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetRecACHBatch";
    if (checkACHSECEntitlement(paramSecureUser, paramACHBatch, null, paramHashMap))
    {
      long l = System.currentTimeMillis();
      ACHBatch localACHBatch = com.ffusion.csil.handlers.ACH.getRecACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHBatch;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_13", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHBatches getPagedPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetPagedPendingACHBatches";
    if (jdMethod_byte(paramSecureUser, paramString2))
    {
      long l = System.currentTimeMillis();
      ACHBatches localACHBatches = com.ffusion.csil.handlers.ACH.getPagedPendingACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHBatches;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString1;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_14", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHBatches getPagedACHBatchHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.getPagedACHBatchHistories";
    String str2 = "ACHBatch";
    if ((paramHashMap != null) && (paramHashMap.get("ACH_TYPE") != null)) {
      str2 = (String)paramHashMap.get("ACH_TYPE");
    }
    if (jdMethod_byte(paramSecureUser, str2))
    {
      long l = System.currentTimeMillis();
      ACHBatches localACHBatches = com.ffusion.csil.handlers.ACH.getPagedACHBatchHistories(paramSecureUser, paramPagingContext, paramHashMap);
      String str3 = paramHashMap == null ? null : (String)paramHashMap.get("ACH_STATUS");
      if ((str3 != null) && (str3.equals("ACH_STATUS_APPROVAL")))
      {
        Iterator localIterator = localACHBatches.iterator();
        while (localIterator.hasNext())
        {
          ACHBatch localACHBatch = (ACHBatch)localIterator.next();
          Util.insertApprovalInfo(localACHBatch, paramSecureUser, null, paramHashMap);
        }
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localACHBatches;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_15", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static ACHBatches getNextPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetNextPendingACHBatches";
    if (jdMethod_byte(paramSecureUser, paramString2))
    {
      long l = System.currentTimeMillis();
      ACHBatches localACHBatches = com.ffusion.csil.handlers.ACH.getNextPendingACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHBatches;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString1;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_16", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHBatches getPreviousPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetPreviousPendingACHBatches";
    if (jdMethod_byte(paramSecureUser, paramString2))
    {
      long l = System.currentTimeMillis();
      ACHBatches localACHBatches = com.ffusion.csil.handlers.ACH.getPreviousPendingACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHBatches;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString1;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_17", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHBatches getPagedCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetPagedCompletedACHBatches";
    if (jdMethod_byte(paramSecureUser, paramString2))
    {
      long l = System.currentTimeMillis();
      ACHBatches localACHBatches = com.ffusion.csil.handlers.ACH.getPagedCompletedACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHBatches;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString1;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_18", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHBatches getNextCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetNextCompletedACHBatches";
    if (jdMethod_byte(paramSecureUser, paramString2))
    {
      long l = System.currentTimeMillis();
      ACHBatches localACHBatches = com.ffusion.csil.handlers.ACH.getNextCompletedACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHBatches;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString1;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_19", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHBatches getPreviousCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetPreviousCompletedACHBatches";
    if (jdMethod_byte(paramSecureUser, paramString2))
    {
      long l = System.currentTimeMillis();
      ACHBatches localACHBatches = com.ffusion.csil.handlers.ACH.getPreviousCompletedACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHBatches;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString1;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_20", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHBatches getPagedApprovalACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.getPagedApprovalACHBatches";
    if (jdMethod_byte(paramSecureUser, paramString2))
    {
      long l = System.currentTimeMillis();
      ACHBatches localACHBatches = com.ffusion.csil.handlers.ACH.getPagedApprovalACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHBatches;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString1;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_21", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHBatches getNextApprovalACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.getNextApprovalACHBatches";
    if (jdMethod_byte(paramSecureUser, paramString2))
    {
      long l = System.currentTimeMillis();
      ACHBatches localACHBatches = com.ffusion.csil.handlers.ACH.getNextApprovalACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHBatches;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString1;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_22", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHBatches getPreviousApprovalACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.getPreviousApprovalACHBatches";
    if (jdMethod_byte(paramSecureUser, paramString2))
    {
      long l = System.currentTimeMillis();
      ACHBatches localACHBatches = com.ffusion.csil.handlers.ACH.getPreviousApprovalACHBatches(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHBatches;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString1;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_23", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHPayees getACHPayees(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetACHPayees";
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aF0)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aFN)))
    {
      long l = System.currentTimeMillis();
      ACHPayees localACHPayees = com.ffusion.csil.handlers.ACH.getACHPayees(paramSecureUser, paramString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHPayees;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_24", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHPayees getACHPayees(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetACHPayees";
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aF0)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aFN)))
    {
      long l = System.currentTimeMillis();
      ACHPayees localACHPayees = com.ffusion.csil.handlers.ACH.getACHPayees(paramSecureUser, paramString1, paramString2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHPayees;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString2;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_25", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHPayee addACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.AddACHPayee";
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aF0))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      paramACHPayee.setTrackingID(str2);
      int i = 1320;
      ACHPayee localACHPayee = null;
      try
      {
        localACHPayee = com.ffusion.csil.handlers.ACH.addACHPayee(paramSecureUser, paramACHPayee, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_13", null);
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0))
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableString;
          arrayOfObject[1] = localCSILException.why;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
        }
        audit(paramSecureUser, localLocalizableString, str2, i, null, null, paramACHPayee.getID());
        throw localCSILException;
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_14", null);
      audit(paramSecureUser, localLocalizableString, str2, i, null, null, localACHPayee.getID());
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 8, localACHPayee.getID(), str2);
      localACHPayee.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      return localACHPayee;
    }
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_26", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void deleteACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.DeleteACHPayee";
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aF0))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = paramACHPayee.getTrackingID();
      if (str2 == null) {
        str2 = TrackingIDGenerator.GetNextID();
      }
      int i = 1322;
      try
      {
        com.ffusion.csil.handlers.ACH.deleteACHPayee(paramSecureUser, paramACHPayee, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_15", null);
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0))
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableString;
          arrayOfObject[1] = localCSILException.why;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
        }
        audit(paramSecureUser, localLocalizableString, str2, i, null, null, paramACHPayee.getID());
        throw localCSILException;
      }
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_16", null);
      audit(paramSecureUser, localLocalizableString, str2, i, null, null, paramACHPayee.getID());
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 8, paramACHPayee.getID(), str2);
      paramACHPayee.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_27", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static ACHPayee modifyACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee1, ACHPayee paramACHPayee2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.ModifyACHPayee";
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aF0))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = paramACHPayee2.getTrackingID();
      if (str2 == null) {
        str2 = TrackingIDGenerator.GetNextID();
      }
      paramACHPayee1.setTrackingID(str2);
      int i = 1321;
      ACHPayee localACHPayee = null;
      try
      {
        localACHPayee = com.ffusion.csil.handlers.ACH.modifyACHPayee(paramSecureUser, paramACHPayee1, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_17", null);
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0))
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableString;
          arrayOfObject[1] = localCSILException.why;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
        }
        audit(paramSecureUser, localLocalizableString, str2, i, null, null, paramACHPayee1.getID());
        throw localCSILException;
      }
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_18", null);
      audit(paramSecureUser, localLocalizableString, str2, i, null, null, paramACHPayee1.getID());
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 8, paramACHPayee1.getID(), str2);
      paramACHPayee1.logChanges(localHistoryTracker, paramACHPayee2, localHistoryTracker.buildLocalizableComment(17));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localACHPayee;
    }
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_28", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static ACHBatches getAllBatchesWithPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetAllBatchesWithPayee";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aFN))
    {
      long l = System.currentTimeMillis();
      ACHBatches localACHBatches = com.ffusion.csil.handlers.ACH.getAllBatchesWithPayee(paramSecureUser, paramACHPayee, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHBatches;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramACHPayee.getName();
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_29", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void deletePayeeFromBatch(SecureUser paramSecureUser, ACHPayee paramACHPayee, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.DeletePayeeFromBatch";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aFN))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      com.ffusion.csil.handlers.ACH.deletePayeeFromBatch(paramSecureUser, paramACHPayee, paramACHBatch, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_30", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
  }
  
  public static void uploadACHFile(SecureUser paramSecureUser, StringBuffer paramStringBuffer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.uploadACHFile";
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString = null;
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    String str2 = (String)paramHashMap.get("TRACKING_ID");
    if (str2 == null) {
      str2 = TrackingIDGenerator.GetNextID();
    }
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aEU))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      paramHashMap.put("TRACKING_ID", str2);
      paramHashMap.remove("FileUploadErrorMessage");
      int i = 1363;
      try
      {
        com.ffusion.csil.handlers.ACH.uploadACHFile(paramSecureUser, paramStringBuffer, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_19", null);
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0))
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableString;
          arrayOfObject[1] = localCSILException.why;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
        }
        audit(paramSecureUser, localLocalizableString, str2, i);
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramHashMap.get("FILE_NAME");
      if (paramHashMap.get("FileUploadErrorMessage") == null) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_20.1", arrayOfObject);
      } else {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_20.2", arrayOfObject);
      }
      audit(paramSecureUser, localLocalizableString, str2, i);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_31", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, str2);
      throw new CSILException(str1, 20001);
    }
  }
  
  public static TaxForms getUserTaxForms(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    TaxForms localTaxForms = null;
    String str = "ACH.GetUserTaxForms";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((Entitlements.checkEntitlement(localEntitlementGroupMember, aFZ)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aEu)))
    {
      long l = System.currentTimeMillis();
      localTaxForms = com.ffusion.csil.handlers.ACH.getUserTaxForms(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      throw new CSILException(str, 20001);
    }
    return localTaxForms;
  }
  
  public static TaxForm addUserTaxForm(SecureUser paramSecureUser, TaxForm paramTaxForm, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.AddUserTaxForm";
    LocalizableString localLocalizableString = null;
    int i = 1330;
    Entitlement localEntitlement = aFZ;
    if ((paramTaxForm != null) && (paramTaxForm.getAddendaFormat().equals("DED")))
    {
      i = 1360;
      localEntitlement = aEu;
    }
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      try
      {
        paramTaxForm = com.ffusion.csil.handlers.ACH.addUserTaxForm(paramSecureUser, paramTaxForm, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        if (i == 1330) {
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_21.1", null);
        } else {
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_21.2", null);
        }
        audit(paramSecureUser, localLocalizableString, str2, i, null, null, paramTaxForm.getID());
        throw localCSILException;
      }
      if (i == 1330) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_22.1", null);
      } else {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_22.2", null);
      }
      audit(paramSecureUser, localLocalizableString, str2, i, null, null, paramTaxForm.getID());
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      if (i == 1330) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_32", null);
      } else {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_33", null);
      }
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    return paramTaxForm;
  }
  
  public static void deleteUserTaxForm(SecureUser paramSecureUser, TaxForm paramTaxForm, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.DeleteUserTaxForm";
    LocalizableString localLocalizableString = null;
    int i = 1331;
    Entitlement localEntitlement = aFZ;
    if ((paramTaxForm != null) && (paramTaxForm.getAddendaFormat().equals("DED")))
    {
      i = 1361;
      localEntitlement = aEu;
    }
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      try
      {
        com.ffusion.csil.handlers.ACH.deleteUserTaxForm(paramSecureUser, paramTaxForm, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        if (i == 1331) {
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_23.1", null);
        } else {
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_23.2", null);
        }
        audit(paramSecureUser, localLocalizableString, str2, i, null, null, paramTaxForm.getID());
        throw localCSILException;
      }
      if (i == 1331) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_24.1", null);
      } else {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_24.2", null);
      }
      audit(paramSecureUser, localLocalizableString, str2, i, null, null, paramTaxForm.getID());
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      if (i == 1331) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_34", null);
      } else {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_35", null);
      }
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, String paramString, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.GetReportData";
    String str2 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, str2, true);
    Entitlement localEntitlement = aFN;
    if ("Total Child Support Payments".equals(str2)) {
      localEntitlement = aEu;
    } else if ("Total Tax Payments".equals(str2)) {
      localEntitlement = aFZ;
    } else if ("ACH File Upload".equals(str2)) {
      localEntitlement = aEU;
    }
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement))
    {
      long l = System.currentTimeMillis();
      Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
      IReportResult localIReportResult = com.ffusion.csil.handlers.ACH.getReportData(paramSecureUser, paramString, paramReportCriteria, paramHashMap);
      String str3 = ReportConsts.getReportName(str2, Locale.getDefault());
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = str3;
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_25", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, TrackingIDGenerator.GetNextID(), 1356);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localIReportResult;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_36", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static String[] getStatesWithTaxForms(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String[] arrayOfString = null;
    String str = "ACH.GetStatesWithTaxForms";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((Entitlements.checkEntitlement(localEntitlementGroupMember, aFZ)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aEu)))
    {
      long l = System.currentTimeMillis();
      arrayOfString = com.ffusion.csil.handlers.ACH.getStatesWithTaxForms(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return arrayOfString;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_37", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static HashMap getStateNamesWithTaxForms(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    HashMap localHashMap = null;
    String str = "ACH.GetStateNamessWithTaxForms";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((Entitlements.checkEntitlement(localEntitlementGroupMember, aFZ)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aEu)))
    {
      long l = System.currentTimeMillis();
      localHashMap = com.ffusion.csil.handlers.ACH.getStateNamesWithTaxForms(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localHashMap;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_38", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static TaxForms getTaxForms(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetTaxForms";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((Entitlements.checkEntitlement(localEntitlementGroupMember, aFZ)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aEu)))
    {
      long l = System.currentTimeMillis();
      TaxForms localTaxForms = com.ffusion.csil.handlers.ACH.getTaxForms(paramSecureUser, paramInt, paramString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localTaxForms;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_39", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static TaxForm getTaxForm(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.GetTaxForm";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((Entitlements.checkEntitlement(localEntitlementGroupMember, aFZ)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aEu)))
    {
      long l = System.currentTimeMillis();
      TaxForm localTaxForm = com.ffusion.csil.handlers.ACH.getTaxForm(paramSecureUser, paramString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localTaxForm;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_40", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ACHCompany addACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return addACHCompany(paramSecureUser, paramACHCompany, paramString, true, paramHashMap);
  }
  
  public static ACHCompany addACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.AddACHCompany";
    LocalizableString localLocalizableString = null;
    Object[] arrayOfObject = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aFN))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      paramACHCompany.setTrackingID(str2);
      int i = 1335;
      ACHCompany localACHCompany = null;
      int j = Integer.parseInt(paramACHCompany.getCustID());
      try
      {
        localACHCompany = com.ffusion.csil.handlers.ACH.addACHCompany(paramSecureUser, paramACHCompany, paramString, paramHashMap);
        int k = Integer.parseInt(localACHCompany.getCustID());
        com.ffusion.beans.business.Business localBusiness = Business.jdMethod_int(paramSecureUser, k, paramHashMap);
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
        HashMap localHashMap = new HashMap();
        localHashMap.put("category", "per ACH company");
        EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(localHashMap);
        String str3 = null;
        Iterator localIterator = localEntitlementTypePropertyLists.iterator();
        while (localIterator.hasNext())
        {
          EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
          str3 = localEntitlementTypePropertyList.getPropertyValue("hide", 0);
          if ((str3 == null) || (!str3.equals("yes"))) {
            localEntitlements.add(new Entitlement(localEntitlementTypePropertyList.getOperationName(), "ACHCompany", localACHCompany.getCompanyID()));
          }
        }
        AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, localBusiness.getEntitlementGroup(), localEntitlements, 2, paramBoolean, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_26", null);
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0))
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableString;
          arrayOfObject[1] = localCSILException.why;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
        }
        audit(paramSecureUser, localLocalizableString, j, str2, i);
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramACHCompany.getCompanyName();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_27", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, Integer.parseInt(localACHCompany.getCustID()), str2, i, null, null, localACHCompany.getID());
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 11, localACHCompany.getID(), str2);
      paramACHCompany.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localACHCompany;
    }
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_41", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static ACHCompanies getACHCompanies(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    return getACHCompanies(paramSecureUser, paramString1, paramString2, true, paramHashMap);
  }
  
  public static ACHCompanies getACHCompanies(SecureUser paramSecureUser, String paramString1, String paramString2, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.getACHCompanies";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((Entitlements.checkEntitlement(localEntitlementGroupMember, aFN)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aFZ)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aEu)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aF0)))
    {
      long l = System.currentTimeMillis();
      ACHCompanies localACHCompanies1 = com.ffusion.csil.handlers.ACH.getACHCompanies(paramSecureUser, paramString1, paramString2, paramHashMap);
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = Entitlements.getCumulativeEntitlements(localEntitlementGroupMember);
      ACHCompanies localACHCompanies2 = new ACHCompanies(paramSecureUser.getLocale());
      localACHCompanies1.setFilter("All");
      Iterator localIterator1 = localACHCompanies1.iterator();
      while (localIterator1.hasNext())
      {
        ACHCompany localACHCompany = (ACHCompany)localIterator1.next();
        localACHCompanies2.add(localACHCompany);
        ArrayList localArrayList = new ArrayList();
        localACHCompany.setEntryClasses(localArrayList);
        boolean bool = localEntitlements.contains(new Entitlement("ACHBatch", "ACHCompany", EntitlementsUtil.getEntitlementObjectId(localACHCompany)));
        if (bool)
        {
          localACHCompany.setActive("false");
        }
        else
        {
          EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties("category", "per ACH company");
          Iterator localIterator2 = localEntitlementTypePropertyLists.iterator();
          while (localIterator2.hasNext())
          {
            EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator2.next();
            String str2 = localEntitlementTypePropertyList.getOperationName();
            if ((str2.indexOf("ACH Payment Entry") != -1) && (str2.indexOf("Overall") == -1) && (str2.indexOf("ACHBatch") == -1))
            {
              String str3 = str2.substring(0, str2.indexOf("ACH Payment Entry") - 1);
              MultiEntitlement localMultiEntitlement = new MultiEntitlement();
              localMultiEntitlement.setOperations(new String[] { str2 });
              localMultiEntitlement.setObjects(new String[] { "ACHCompany" }, new String[] { EntitlementsUtil.getEntitlementObjectId(localACHCompany) });
              localMultiEntitlement = ParentEntitlementsCache.appendParentEntitlements(localMultiEntitlement);
              if ((Entitlements.checkEntitlement(localEntitlementGroupMember, localMultiEntitlement) == null) && (!localArrayList.contains(str3)))
              {
                localArrayList.add(str3);
                if (str3.toUpperCase().indexOf("ADDENDA") > 0)
                {
                  str3 = str2.substring(0, 3);
                  if (!localArrayList.contains(str3)) {
                    localArrayList.add(str3);
                  }
                }
              }
            }
          }
        }
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localACHCompanies2;
    }
    if (paramBoolean)
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_42", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    }
    throw new CSILException(str1, 20001);
  }
  
  public static void modifyACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany1, ACHCompany paramACHCompany2, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.ModACHCompany";
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aFN))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = paramACHCompany1.getTrackingID();
      if (str2 == null)
      {
        str2 = TrackingIDGenerator.GetNextID();
        paramACHCompany1.setTrackingID(str2);
      }
      int i = 1336;
      int j = Integer.parseInt(paramACHCompany1.getCustID());
      try
      {
        com.ffusion.csil.handlers.ACH.modifyACHCompany(paramSecureUser, paramACHCompany1, paramString, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_28", null);
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0))
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableString;
          arrayOfObject[1] = localCSILException.why;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
        }
        audit(paramSecureUser, localLocalizableString, j, str2, i, null, null, paramACHCompany1.getID());
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramACHCompany1.getCompanyName();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_29", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, j, str2, i, null, null, paramACHCompany1.getID());
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 11, paramACHCompany1.getID(), str2);
      paramACHCompany1.logChanges(localHistoryTracker, paramACHCompany2, localHistoryTracker.buildLocalizableComment(17));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_43", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void deleteACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.deleteACHCompany";
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aFN))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = paramACHCompany.getTrackingID();
      if (str2 == null) {
        str2 = TrackingIDGenerator.GetNextID();
      }
      int i = 1337;
      try
      {
        com.ffusion.csil.handlers.ACH.deleteACHCompany(paramSecureUser, paramACHCompany, paramString, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_30", null);
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0))
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableString;
          arrayOfObject[1] = localCSILException.why;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
        }
        try
        {
          int k = Integer.parseInt(paramACHCompany.getCustID());
          audit(paramSecureUser, localLocalizableString, k, str2, i, null, null, paramACHCompany.getID());
        }
        catch (Exception localException2)
        {
          audit(paramSecureUser, localLocalizableString, str2, i, null, null, paramACHCompany.getID());
        }
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramACHCompany.getCompanyName();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_31", arrayOfObject);
      try
      {
        int j = Integer.parseInt(paramACHCompany.getCustID());
        audit(paramSecureUser, localLocalizableString, j, str2, i, null, null, paramACHCompany.getID());
      }
      catch (Exception localException1)
      {
        audit(paramSecureUser, localLocalizableString, str2, i, null, null, paramACHCompany.getID());
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 11, paramACHCompany.getID(), str2);
      paramACHCompany.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_44", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static ACHCompanySummaries getACHCompanySummaries(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.getACHCompanySummaries";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((Entitlements.checkEntitlement(localEntitlementGroupMember, aFN)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aFZ)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aEu)))
    {
      long l = System.currentTimeMillis();
      ACHCompanySummaries localACHCompanySummaries = com.ffusion.csil.handlers.ACH.getACHCompanySummaries(paramSecureUser, paramArrayOfString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHCompanySummaries;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_45", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void addOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.addOffsetAccount";
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString1 = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aFN))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 1340;
      try
      {
        com.ffusion.csil.handlers.ACH.addOffsetAccount(paramSecureUser, paramACHOffsetAccount, paramACHCompany, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_32", null);
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0))
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableString1;
          arrayOfObject[1] = localCSILException.why;
          localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
        }
        try
        {
          int k = Integer.parseInt(paramACHCompany.getCustID());
          audit(paramSecureUser, localLocalizableString1, k, str2, i, null, null, paramACHOffsetAccount.getID());
        }
        catch (Exception localException2)
        {
          audit(paramSecureUser, localLocalizableString1, str2, i, null, null, paramACHOffsetAccount.getID());
        }
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramACHCompany.getCompanyName();
      localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_33", arrayOfObject);
      try
      {
        int j = Integer.parseInt(paramACHCompany.getCustID());
        audit(paramSecureUser, localLocalizableString1, j, str2, i, null, null, paramACHOffsetAccount.getID());
      }
      catch (Exception localException1)
      {
        audit(paramSecureUser, localLocalizableString1, str2, i, null, null, paramACHOffsetAccount.getID());
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 11, paramACHCompany.getID(), str2);
      arrayOfObject = new Object[2];
      arrayOfObject[0] = paramACHOffsetAccount.getNumber();
      arrayOfObject[1] = paramACHOffsetAccount.getNickName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.beans.history.resources", "OFFSET_ACCOUNT_ADDED", arrayOfObject);
      localHistoryTracker.logChange(ACHCompany.class.getName(), "OFFSETACCOUNT", (String)null, (String)null, localLocalizableString2);
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_46", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static ACHOffsetAccounts getOffsetAccounts(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.getOffsetAccounts";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((Entitlements.checkEntitlement(localEntitlementGroupMember, aFN)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aFZ)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aEu)))
    {
      long l = System.currentTimeMillis();
      ACHOffsetAccounts localACHOffsetAccounts = com.ffusion.csil.handlers.ACH.getOffsetAccounts(paramSecureUser, paramACHOffsetAccount, paramACHCompany, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localACHOffsetAccounts;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_47", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void modifyOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.modifyOffsetAccount";
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aFN))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 1341;
      try
      {
        com.ffusion.csil.handlers.ACH.modifyOffsetAccount(paramSecureUser, paramACHOffsetAccount, paramACHCompany, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_34", null);
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0))
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableString;
          arrayOfObject[1] = localCSILException.why;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
        }
        try
        {
          int k = Integer.parseInt(paramACHCompany.getCustID());
          audit(paramSecureUser, localLocalizableString, k, str2, i, null, null, paramACHOffsetAccount.getID());
        }
        catch (Exception localException2)
        {
          audit(paramSecureUser, localLocalizableString, str2, i, null, null, paramACHOffsetAccount.getID());
        }
        throw localCSILException;
      }
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_35", null);
      try
      {
        int j = Integer.parseInt(paramACHCompany.getCustID());
        audit(paramSecureUser, localLocalizableString, j, str2, i, null, null, paramACHOffsetAccount.getID());
      }
      catch (Exception localException1)
      {
        audit(paramSecureUser, localLocalizableString, str2, i, null, null, paramACHOffsetAccount.getID());
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_48", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void deleteOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.deleteOffsetAccount";
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString1 = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aFN))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 1342;
      try
      {
        com.ffusion.csil.handlers.ACH.deleteOffsetAccount(paramSecureUser, paramACHOffsetAccount, paramACHCompany, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_36", null);
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0))
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableString1;
          arrayOfObject[1] = localCSILException.why;
          localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_0", arrayOfObject);
        }
        try
        {
          int k = Integer.parseInt(paramACHCompany.getCustID());
          audit(paramSecureUser, localLocalizableString1, k, str2, i, null, null, paramACHOffsetAccount.getID());
        }
        catch (Exception localException2)
        {
          audit(paramSecureUser, localLocalizableString1, str2, i, null, null, paramACHOffsetAccount.getID());
        }
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramACHCompany.getCompanyName();
      localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditMessage_37", arrayOfObject);
      try
      {
        int j = Integer.parseInt(paramACHCompany.getCustID());
        audit(paramSecureUser, localLocalizableString1, j, str2, i, null, null, paramACHOffsetAccount.getID());
      }
      catch (Exception localException1)
      {
        audit(paramSecureUser, localLocalizableString1, str2, i, null, null, paramACHOffsetAccount.getID());
      }
      PerfLog.log(str1, l, true);
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 11, paramACHCompany.getID(), str2);
      arrayOfObject = new Object[2];
      arrayOfObject[0] = paramACHOffsetAccount.getNumber();
      arrayOfObject[1] = paramACHOffsetAccount.getNickName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.beans.history.resources", "OFFSET_ACCOUNT_DELETED", arrayOfObject);
      localHistoryTracker.logChange(ACHCompany.class.getName(), "OFFSETACCOUNT", (String)null, (String)null, localLocalizableString2);
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      debug(paramSecureUser, str1);
    }
    else
    {
      localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_49", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    if ((paramACHBatch.getFrequencyValue() != 0) && (paramACHBatch.getNumberPaymentsValue() > 1)) {
      com.ffusion.csil.handlers.ACH.deleteRecACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
    } else {
      com.ffusion.csil.handlers.ACH.deleteACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
    }
  }
  
  private static boolean jdMethod_byte(SecureUser paramSecureUser, String paramString)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = aFN;
    if (paramString != null)
    {
      if (paramString.equals("TaxPayment")) {
        localEntitlement = aFZ;
      }
      if (paramString.equals("ChildSupportPayment")) {
        localEntitlement = aEu;
      }
    }
    return Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement);
  }
  
  public static void checkLimitsDelete(SecureUser paramSecureUser, ACHBatch paramACHBatch)
    throws CSILException
  {
    Entitlement localEntitlement = null;
    String str = paramACHBatch.getCompanyID();
    if (paramACHBatch.getACHType().equals("TaxPayment")) {
      localEntitlement = new Entitlement("Tax Payments", "ACHCompany", str);
    } else if (paramACHBatch.getACHType().equals("ChildSupportPayment")) {
      localEntitlement = new Entitlement("Child Support", "ACHCompany", str);
    } else {
      localEntitlement = new Entitlement("ACHBatch", "ACHCompany", str);
    }
    Entitlements.checkLimitsDelete(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement, paramACHBatch.getAmountValue().getAmountValue(), paramACHBatch.getDateValue().getTime());
  }
  
  public static String getDisplayCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "ACH.getDisplayCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aFN))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.ACH.getDisplayCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_50", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static String getDisplayPayeeCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "ACH.getDisplayPayeeCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aFN))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.ACH.getDisplayPayeeCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_51", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static String getMaxResultCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "ACH.getMaxResultCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aFN))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.ACH.getMaxResultCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_52", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static FinancialInstitutions getFinancialInstitutions(SecureUser paramSecureUser, FinancialInstitution paramFinancialInstitution, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.getFinancialInstitutions";
    long l = System.currentTimeMillis();
    FinancialInstitutions localFinancialInstitutions = new FinancialInstitutions();
    try
    {
      localFinancialInstitutions = BankLookup.getFinancialInstitutions(paramSecureUser, paramFinancialInstitution, paramInt, paramHashMap);
      if (localFinancialInstitutions == null) {
        DebugLog.log("No FinancialInstitutions are found matching the search criteria.");
      }
    }
    catch (CSILException localCSILException)
    {
      localCSILException.printStackTrace(System.err);
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localFinancialInstitutions;
  }
  
  public static boolean isBusinessRegisteredWithBPW(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool = false;
    String str = "ACH.addCustomer";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((Entitlements.checkEntitlement(localEntitlementGroupMember, aFI)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aFU)))
    {
      long l = System.currentTimeMillis();
      bool = com.ffusion.csil.handlers.ACH.isBusinessRegisteredWithBPW(paramSecureUser, paramString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_53", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    return bool;
  }
  
  public static boolean getBusinessRegistrationBPWInfo(SecureUser paramSecureUser, String paramString, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool = false;
    String str = "ACH.getBusinessRegistrationBPWInfo";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((paramSecureUser.getUserType() == 1) || ((paramSecureUser.getUserType() == 2) && ((Entitlements.checkEntitlement(localEntitlementGroupMember, aFI)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aFU)))))
    {
      long l = System.currentTimeMillis();
      bool = com.ffusion.csil.handlers.ACH.getBusinessRegistrationBPWInfo(paramSecureUser, paramString, paramBusiness, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_54", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    return bool;
  }
  
  public static void addCustomer(SecureUser paramSecureUser, String paramString, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.addCustomer";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((Entitlements.checkEntitlement(localEntitlementGroupMember, aFI)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aFU)))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      com.ffusion.csil.handlers.ACH.addCustomer(paramSecureUser, paramString, paramBusiness, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_55", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
  }
  
  public static void modifyCustomer(SecureUser paramSecureUser, String paramString, com.ffusion.beans.business.Business paramBusiness, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.modifyCustomer";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((Entitlements.checkEntitlement(localEntitlementGroupMember, aFI)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aFU)))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      com.ffusion.csil.handlers.ACH.modifyCustomer(paramSecureUser, paramString, paramBusiness, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_56", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
  }
  
  public static void deactivateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.deactivateCustomers";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((Entitlements.checkEntitlement(localEntitlementGroupMember, aFI)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aFU)))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      com.ffusion.csil.handlers.ACH.deactivateCustomers(paramSecureUser, paramArrayOfString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_57", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, null);
      throw new CSILException(str, 20001);
    }
  }
  
  public static void activateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.deactivateCustomers";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if ((Entitlements.checkEntitlement(localEntitlementGroupMember, aFI)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, aFU)))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      com.ffusion.csil.handlers.ACH.activateCustomers(paramSecureUser, paramArrayOfString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_58", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, null);
      throw new CSILException(str, 20001);
    }
  }
  
  public static Date getSmartDate(SecureUser paramSecureUser, DateTime paramDateTime)
    throws CSILException
  {
    String str = "ACH.getSmartDate";
    debug(paramSecureUser, str);
    return com.ffusion.csil.handlers.ACH.getSmartDate(paramSecureUser, paramDateTime);
  }
  
  public static ReportLogRecords getAuditHistory(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.getAuditHistory";
    if (checkACHSECEntitlement(paramSecureUser, paramACHBatch, null, paramHashMap))
    {
      long l = System.currentTimeMillis();
      ReportLogRecords localReportLogRecords = ReportAuditAdapter.getAuditHistoryByTrackingId(paramSecureUser, paramACHBatch.getTrackingID());
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localReportLogRecords;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_59", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Object exportACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.exportBatch";
    if (checkACHSECEntitlement(paramSecureUser, paramACHBatch, null, paramHashMap))
    {
      long l = System.currentTimeMillis();
      Object localObject = com.ffusion.csil.handlers.ACH.exportACHBatch(paramSecureUser, paramACHBatch, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localObject;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_60", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static HashSet getEnabledSECCodes(SecureUser paramSecureUser, String paramString, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.getEnabledSECCodes";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    LocalizableString localLocalizableString;
    if (paramInt == 0)
    {
      if ((!Entitlements.checkEntitlement(localEntitlementGroupMember, aFI)) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, aFU)) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, aFK)))
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_61", null);
        logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        throw new CSILException(str, 20001);
      }
    }
    else if (paramInt == 1)
    {
      if (!Entitlements.checkEntitlement(localEntitlementGroupMember, aFE))
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_62", null);
        logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        throw new CSILException(str, 20001);
      }
    }
    else {
      throw new CSILException(str, 16132);
    }
    return com.ffusion.csil.handlers.ACH.getEnabledSECCodes(paramSecureUser, paramString, paramInt, paramHashMap);
  }
  
  public static void setEnabledSECCodes(SecureUser paramSecureUser, String paramString, int paramInt, HashSet paramHashSet, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ACH.getEnabledSECCodes";
    long l = System.currentTimeMillis();
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    if (paramInt == 0)
    {
      if ((!Entitlements.checkEntitlement(localEntitlementGroupMember, aFI)) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, aFU)) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, aFK)))
      {
        localObject1 = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_63", null);
        logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
      }
    }
    else if (paramInt == 1)
    {
      if (!Entitlements.checkEntitlement(localEntitlementGroupMember, aFE))
      {
        localObject1 = new LocalizableString("com.ffusion.util.logging.audit.ach", "AuditEntFault_64", null);
        logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
      }
    }
    else {
      throw new CSILException(str1, 16132);
    }
    Object localObject1 = com.ffusion.csil.handlers.ACH.getEnabledSECCodes(paramSecureUser, paramString, paramInt, paramHashMap);
    com.ffusion.csil.handlers.ACH.setEnabledSECCodes(paramSecureUser, paramString, paramInt, paramHashSet, paramHashMap);
    String str2 = TrackingIDGenerator.GetNextID();
    int i = 11;
    PerfLog.log(str1, l, true);
    String str3;
    if (paramInt == 0)
    {
      localObject2 = (com.ffusion.beans.business.Business)paramHashMap.get("Object");
      str3 = localObject2 != null ? ((com.ffusion.beans.business.Business)localObject2).getId() : "";
      i = 2;
    }
    else
    {
      localObject2 = (ACHCompany)paramHashMap.get("Object");
      str3 = localObject2 != null ? ((ACHCompany)localObject2).getID() : "";
    }
    Object localObject2 = new HistoryTracker(paramSecureUser, i, str3, str2);
    Iterator localIterator = null;
    ArrayList localArrayList = new ArrayList();
    String str4;
    Object[] arrayOfObject;
    LocalizableString localLocalizableString;
    if (localObject1 != null)
    {
      localIterator = ((HashSet)localObject1).iterator();
      while (localIterator.hasNext())
      {
        str4 = (String)localIterator.next();
        if (paramHashSet.contains(str4))
        {
          localArrayList.add(str4);
        }
        else
        {
          arrayOfObject = new Object[1];
          arrayOfObject[0] = str4;
          localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "REMOVE_SEC_CODES", arrayOfObject);
          ((HistoryTracker)localObject2).logChange(ACHCompany.class.getName(), "SAMEDAYEFFDATE", (String)null, (String)null, localLocalizableString);
        }
      }
    }
    localIterator = paramHashSet.iterator();
    while (localIterator.hasNext())
    {
      str4 = (String)localIterator.next();
      if (!localArrayList.contains(str4))
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = str4;
        localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "ADD_SEC_CODES", arrayOfObject);
        ((HistoryTracker)localObject2).logChange(ACHCompany.class.getName(), "SAMEDAYEFFDATE", (String)null, (String)null, localLocalizableString);
      }
    }
    try
    {
      HistoryAdapter.addHistory(((HistoryTracker)localObject2).getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
    }
    debug(paramSecureUser, str1);
  }
  
  public static String getDefaultEffectiveDate(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ACH.getDefaultEffectiveDate ";
    debug(paramSecureUser, str);
    return com.ffusion.csil.handlers.ACH.getDefaultEffectiveDate(paramSecureUser, paramString1, paramString2, paramHashMap);
  }
  
  public static ArrayList getDefaultACHFrequencies(SecureUser paramSecureUser)
    throws CSILException
  {
    String str = "ACH.getDefaultACHFrequencies ";
    debug(paramSecureUser, str);
    return com.ffusion.csil.handlers.ACH.getDefaultACHFrequencies(paramSecureUser);
  }
  
  public static boolean checkACHSECEntitlement(SecureUser paramSecureUser, ACHBatch paramACHBatch, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool = false;
    ACHEntries localACHEntries = paramACHBatch.getACHEntries();
    Iterator localIterator = localACHEntries.iterator();
    while (localIterator.hasNext())
    {
      ACHEntry localACHEntry = (ACHEntry)localIterator.next();
      if ((localACHEntry.getAddendas() != null) && (localACHEntry.getAddendas().size() != 0))
      {
        bool = true;
        break;
      }
    }
    return checkACHSECEntitlement(paramSecureUser, paramACHBatch.getSECEntitlementName(), paramACHBatch.getCompanyID(), paramString, bool, paramHashMap);
  }
  
  public static boolean checkACHSECEntitlement(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = new Entitlement(paramString1, "ACHCompany", paramString2);
    String[] arrayOfString = com.ffusion.util.entitlements.EntitlementsUtil.getACHSECEntitlementWithAddenda(paramString1);
    String str = null;
    if (!paramBoolean)
    {
      str = arrayOfString[0];
      if ((paramString3 != null) && (paramString3.length() > 0)) {
        str = com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName(str, paramString3);
      }
      localEntitlement.setOperationName(str);
      if ((Entitlements.entitlementTypeExists(str)) && (EntitlementsUtil.checkEntitlementAndParents(localEntitlementGroupMember, localEntitlement) == null)) {
        return true;
      }
      if (arrayOfString[0].equals(arrayOfString[1])) {
        return false;
      }
    }
    str = arrayOfString[1];
    if ((paramString3 != null) && (paramString3.length() > 0)) {
      str = com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName(str, paramString3);
    }
    localEntitlement.setOperationName(str);
    return EntitlementsUtil.checkEntitlementAndParents(localEntitlementGroupMember, localEntitlement) == null;
  }
  
  public static String getACHSECEntitlement(SecureUser paramSecureUser, ACHBatch paramACHBatch, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool = false;
    ACHEntries localACHEntries = paramACHBatch.getACHEntries();
    Iterator localIterator = localACHEntries.iterator();
    while (localIterator.hasNext())
    {
      ACHEntry localACHEntry = (ACHEntry)localIterator.next();
      if ((localACHEntry.getAddendas() != null) && (localACHEntry.getAddendas().size() != 0))
      {
        bool = true;
        break;
      }
    }
    return getACHSECEntitlement(paramSecureUser, paramACHBatch.getSECEntitlementName(), paramACHBatch.getCompanyID(), paramString, bool, paramHashMap);
  }
  
  public static String getACHSECEntitlement(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = new Entitlement(paramString1, "ACHCompany", paramString2);
    String[] arrayOfString = com.ffusion.util.entitlements.EntitlementsUtil.getACHSECEntitlementWithAddenda(paramString1);
    String str = null;
    if (!paramBoolean)
    {
      str = arrayOfString[0];
      if ((paramString3 != null) && (paramString3.length() > 0)) {
        str = com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName(str, paramString3);
      }
      localEntitlement.setOperationName(str);
      if ((Entitlements.entitlementTypeExists(str)) && (EntitlementsUtil.checkEntitlementAndParents(localEntitlementGroupMember, localEntitlement) == null)) {
        return str;
      }
      if (arrayOfString[0].equals(arrayOfString[1])) {
        return "";
      }
    }
    str = arrayOfString[1];
    if ((paramString3 != null) && (paramString3.length() > 0)) {
      str = com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName(str, paramString3);
    }
    localEntitlement.setOperationName(str);
    if ((Entitlements.entitlementTypeExists(str)) && (EntitlementsUtil.checkEntitlementAndParents(localEntitlementGroupMember, localEntitlement) == null)) {
      return str;
    }
    return "";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.ACH
 * JD-Core Version:    0.7.0.1
 */