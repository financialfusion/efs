package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.positivepay.PPayAccount;
import com.ffusion.beans.positivepay.PPayCheckRecord;
import com.ffusion.beans.positivepay.PPayCheckRecords;
import com.ffusion.beans.positivepay.PPayDecision;
import com.ffusion.beans.positivepay.PPayDecisions;
import com.ffusion.beans.positivepay.PPayIssues;
import com.ffusion.beans.positivepay.PPaySummaries;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.handlers.PositivePayHandler;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class PositivePay
  extends Initialize
{
  private static Entitlement aAo = new Entitlement("Positive Pay", null, null);
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePay.initialize";
    PositivePayHandler.initialize(paramHashMap);
    debug(str);
  }
  
  public static Object getService()
  {
    return PositivePayHandler.getService();
  }
  
  public static PPaySummaries getSummaries(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePay.getSummaries";
    long l = System.currentTimeMillis();
    jdMethod_int(paramSecureUser, str);
    PPaySummaries localPPaySummaries = PositivePayHandler.getSummaries(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localPPaySummaries;
  }
  
  public static int getNumIssues(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePay.getNumIssues";
    long l = System.currentTimeMillis();
    jdMethod_int(paramSecureUser, str);
    int i = PositivePayHandler.getNumIssues(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return i;
  }
  
  public static PPayIssues getIssues(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePay.getIssues";
    long l = System.currentTimeMillis();
    jdMethod_int(paramSecureUser, str);
    PPayIssues localPPayIssues = PositivePayHandler.getIssues(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localPPayIssues;
  }
  
  public static PPayIssues getIssuesForAccount(SecureUser paramSecureUser, PPayAccount paramPPayAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePay.getIssuesForAccount";
    long l = System.currentTimeMillis();
    jdMethod_int(paramSecureUser, str);
    PPayIssues localPPayIssues = PositivePayHandler.getIssuesForAccount(paramSecureUser, paramPPayAccount, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localPPayIssues;
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "PositivePay.getReportData";
    long l = System.currentTimeMillis();
    jdMethod_int(paramSecureUser, str1);
    Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
    IReportResult localIReportResult = PositivePayHandler.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    String str2 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, str2, true);
    String str3 = paramReportCriteria.getReportOptions().getProperty("TITLE");
    if ((str3 == null) || (str3.length() == 0)) {
      str3 = ReportConsts.getReportName(str2, Locale.getDefault());
    }
    audit(paramSecureUser, "Generate " + str3 + ".", TrackingIDGenerator.GetNextID(), 2902);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return localIReportResult;
  }
  
  public static void submitDecisions(SecureUser paramSecureUser, PPayDecisions paramPPayDecisions, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "PositivePay.submitDecisions";
    long l = System.currentTimeMillis();
    jdMethod_int(paramSecureUser, str1);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    PositivePayHandler.submitDecisions(paramSecureUser, paramPPayDecisions, paramHashMap);
    PerfLog.log(str1, l, true);
    for (int i = 0; i < paramPPayDecisions.size(); i++)
    {
      PPayDecision localPPayDecision = (PPayDecision)paramPPayDecisions.get(i);
      PPayCheckRecord localPPayCheckRecord = localPPayDecision.getCheckRecord();
      String str2 = TrackingIDGenerator.GetNextID();
      StringBuffer localStringBuffer = new StringBuffer();
      String str3 = null;
      localStringBuffer.append("Submit Positive Pay decision for ");
      localStringBuffer.append("account ");
      try
      {
        str3 = AccountUtil.buildAccountDisplayText(localPPayCheckRecord.getAccountID(), localPPayCheckRecord.getLocale());
      }
      catch (UtilException localUtilException)
      {
        DebugLog.throwing("Error while generating account display text.", localUtilException);
        str3 = localPPayCheckRecord.getAccountID();
      }
      localStringBuffer.append(str3);
      localStringBuffer.append(", check number ");
      localStringBuffer.append(localPPayCheckRecord.getCheckNumber());
      localStringBuffer.append(". The decision is '");
      localStringBuffer.append(localPPayDecision.getDecision());
      localStringBuffer.append("'.");
      audit(paramSecureUser, localStringBuffer.toString(), str2, 2900);
    }
    debug(paramSecureUser, str1);
  }
  
  public static void submitCheckRecords(SecureUser paramSecureUser, PPayCheckRecords paramPPayCheckRecords, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "PositivePay.submitCheckRecords";
    String str2 = TrackingIDGenerator.GetNextID();
    long l = System.currentTimeMillis();
    jdMethod_int(paramSecureUser, str1);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    PositivePayHandler.submitCheckRecords(paramSecureUser, paramPPayCheckRecords, paramHashMap);
    PerfLog.log(str1, l, true);
    audit(paramSecureUser, "Submit list of manually entered Positive Pay check records.", str2, 2901);
    debug(paramSecureUser, str1);
  }
  
  public static void cleanup(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePay.cleanup";
    long l = System.currentTimeMillis();
    PositivePayHandler.cleanup(paramInt, paramHashMap);
    PerfLog.log(str, l, true);
    debug(str);
  }
  
  public static void submitDefaultDecisions(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    PositivePayHandler.submitDefaultDecisions(paramInt, paramHashMap);
  }
  
  private static void jdMethod_int(SecureUser paramSecureUser, String paramString)
    throws CSILException
  {
    if ((paramSecureUser.getUserType() != 3) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAo)))
    {
      DebugLog.log("The user is not entitled to use Positive Pay. The call to " + paramString + " has failed for this reason.");
      logEntitlementFault(paramSecureUser, "The user is not entitled to use Positive Pay services.", TrackingIDGenerator.GetNextID());
      throw new CSILException(22002);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.PositivePay
 * JD-Core Version:    0.7.0.1
 */