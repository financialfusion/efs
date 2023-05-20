package com.ffusion.csil.core;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.util.CommBankIdentifier;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.MapUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLTag;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.filemonitor.FMLog;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import com.ffusion.util.logging.TrackingIDGenerator;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

public class Initialize
{
  protected static boolean initialized = false;
  public static final String CSIL_PROPERTIES_FILENAME = "csil.xml";
  public static final String SETTINGS_KEY = "CSIL_CORE";
  private static final String ao9 = "SERVERID";
  private static final String apv = "DB_PROPERTIES";
  private static final String apG = "SERVICE";
  protected static String serverID = "00";
  protected static HashMap settings;
  private static final Object ao8 = new Object();
  private static final String apl = "Accounts";
  private static final String apc = "AccountGroups";
  private static final String apF = "AutoEntitle";
  private static final String apT = "ACH";
  private static final String ape = "Admin";
  private static final String apx = "AffiliateBank";
  private static final String api = "Applications";
  private static final String app = "Alerts";
  private static final String apQ = "BankEmployeeAdmin";
  private static final String ao7 = "Banking";
  private static final String apw = "BankLookup";
  private static final String apg = "BankReports";
  private static final String apo = "BCReport";
  private static final String apr = "Billpay";
  private static final String apm = "BillPresentment";
  private static final String apn = "Blocked Accounts";
  private static final String apN = "Business";
  private static final String apL = "Cash Concentration";
  private static final String apk = "Check Imaging";
  private static final String apI = "Data Consolidator";
  private static final String apM = "Disbursements";
  private static final String apq = "ExternalTransferAdmin";
  private static final String apj = "File Importer";
  private static final String apf = "File Monitor";
  private static final String apE = "Lockbox";
  private static final String aps = "Messaging";
  private static final String apJ = "Messaging";
  private static final String apP = "Mailing";
  private static final String apS = "Online Statement";
  private static final String apK = "Password Recovery";
  private static final String apz = "PaymentsAdmin";
  private static final String apC = "Portal";
  private static final String apd = "Positive Pay";
  private static final String apO = "ProfileBillpay";
  private static final String apH = "Register";
  private static final String apu = "Reporting";
  private static final String apb = "SmartCalendar";
  private static final String apD = "SystemAdmin";
  private static final String aph = "TaxForm";
  private static final String apt = "Terms";
  private static final String apA = "TreasuryDirect";
  private static final String apB = "UserAdmin";
  private static final String apR = "WireTransfer";
  private static final String apa = "Account Aggregation";
  
  public static HashMap getSettings()
  {
    return settings;
  }
  
  static HashMap getDBSettingsHashMap()
  {
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = (HashMap)settings.get("SERVICE_SETTINGS_LIST");
    ArrayList localArrayList = (ArrayList)localHashMap2.get("SERVICE");
    Iterator localIterator1 = localArrayList.iterator();
    if (localIterator1 != null) {
      while (localIterator1.hasNext())
      {
        HashMap localHashMap3 = (HashMap)localIterator1.next();
        if (localHashMap3.containsKey("DB_PROPERTIES"))
        {
          Properties localProperties = (Properties)localHashMap3.get("DB_PROPERTIES");
          HashMap localHashMap4 = new HashMap();
          Set localSet = localProperties.keySet();
          Iterator localIterator2 = localSet.iterator();
          while (localIterator2.hasNext())
          {
            String str1 = (String)localIterator2.next();
            String str2 = (String)localProperties.get(str1);
            localHashMap4.put(str1, str2);
          }
          localHashMap1.put("DB_PROPERTIES", localHashMap4);
          break;
        }
      }
    }
    return localHashMap1;
  }
  
  static Properties getDBSettingsProperties()
  {
    Properties localProperties = new Properties();
    HashMap localHashMap1 = (HashMap)settings.get("SERVICE_SETTINGS_LIST");
    ArrayList localArrayList = (ArrayList)localHashMap1.get("SERVICE");
    Iterator localIterator = localArrayList.iterator();
    if (localIterator != null) {
      while (localIterator.hasNext())
      {
        HashMap localHashMap2 = (HashMap)localIterator.next();
        if (localHashMap2.containsKey("DB_PROPERTIES")) {
          localProperties = (Properties)localHashMap2.get("DB_PROPERTIES");
        }
      }
    }
    return localProperties;
  }
  
  public static void initialize() {}
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      try
      {
        DebugLog.initialize(paramHashMap);
      }
      catch (Throwable localThrowable1)
      {
        DebugLog.log(Level.SEVERE, "DebugLog.initialize:" + localThrowable1.toString());
      }
      try
      {
        com.ffusion.util.logging.AuditLog.initialize(paramHashMap);
      }
      catch (Throwable localThrowable2)
      {
        DebugLog.log(Level.SEVERE, "AuditLog.initialize:" + localThrowable2.toString());
      }
      try
      {
        FMLog.initialize(paramHashMap);
      }
      catch (Throwable localThrowable3)
      {
        DebugLog.log(Level.SEVERE, "FMLog.initialize:" + localThrowable3.toString());
      }
      try
      {
        PerfLog.initialize(paramHashMap);
      }
      catch (Throwable localThrowable4)
      {
        DebugLog.log(Level.SEVERE, "PerfLog.initialize:" + localThrowable4.toString());
      }
      try
      {
        DBUtil.initialize(paramHashMap);
      }
      catch (Throwable localThrowable5)
      {
        DebugLog.log(Level.SEVERE, "DBUtil.initialize:" + localThrowable5.toString());
      }
      Object localObject = MapUtil.getStringValue(paramHashMap, "SERVERID", "00");
      String str = System.getProperties().getProperty("auditServerID");
      if ((str != null) && (str.length() > 0)) {
        localObject = str;
      }
      try
      {
        TrackingIDGenerator.initialize((String)localObject);
      }
      catch (Throwable localThrowable6)
      {
        DebugLog.log(Level.SEVERE, "TrackingIDGenerator.initialize:" + localThrowable6.toString());
      }
      try
      {
        Approvals.initialize(paramHashMap);
      }
      catch (Throwable localThrowable7)
      {
        DebugLog.log(Level.SEVERE, "Approvals.initialize:" + localThrowable7.toString());
      }
      try
      {
        AuditLog.initialize(paramHashMap);
      }
      catch (Throwable localThrowable8)
      {
        DebugLog.log(Level.SEVERE, "com.ffusion.csil.core.AuditLog.initialize:" + localThrowable8.toString());
      }
      try
      {
        Entitlements.initialize(paramHashMap);
      }
      catch (Throwable localThrowable9)
      {
        DebugLog.log(Level.SEVERE, "Entitlements.initialize:" + localThrowable9.toString());
      }
      try
      {
        FX.initialize(paramHashMap);
      }
      catch (Throwable localThrowable10)
      {
        DebugLog.log(Level.SEVERE, "FX.initialize:" + localThrowable10.toString());
        localThrowable10.printStackTrace();
      }
      try
      {
        Profile.initialize(paramHashMap);
      }
      catch (Throwable localThrowable11)
      {
        DebugLog.log(Level.SEVERE, "Profile.initialize:" + localThrowable11.toString());
      }
      try
      {
        Util.initialize(paramHashMap);
      }
      catch (Throwable localThrowable12)
      {
        DebugLog.log(Level.SEVERE, "Util.initialize:" + localThrowable12.toString());
        localThrowable12.printStackTrace();
      }
      try
      {
        Authentication.initialize(paramHashMap);
      }
      catch (Throwable localThrowable13)
      {
        DebugLog.log(Level.SEVERE, "Authentication.initialize:" + localThrowable13.toString());
        localThrowable13.printStackTrace();
      }
      try
      {
        Password.initialize(paramHashMap);
      }
      catch (Throwable localThrowable14)
      {
        DebugLog.log(Level.SEVERE, "Password.initialize:" + localThrowable14.toString());
        localThrowable14.printStackTrace();
      }
      try
      {
        com.ffusion.csil.handlers.Initialize.initialize(paramHashMap);
      }
      catch (Throwable localThrowable15)
      {
        DebugLog.log(Level.SEVERE, "Could not initialize handlers" + localThrowable15.toString());
      }
      if (serviceExists(paramHashMap, "Accounts")) {
        try
        {
          Accounts.initialize(paramHashMap);
        }
        catch (Throwable localThrowable16)
        {
          DebugLog.log(Level.SEVERE, "Accounts.initialize:" + localThrowable16.toString());
        }
      }
      if (serviceExists(paramHashMap, "AccountGroups")) {
        try
        {
          AccountGroup.initialize(paramHashMap);
        }
        catch (Throwable localThrowable17)
        {
          DebugLog.log(Level.SEVERE, "AccountGroup.initialize:" + localThrowable17.toString());
        }
      }
      if ((serviceExists(paramHashMap, "ACH")) || (serviceExists(paramHashMap, "TaxForm"))) {
        try
        {
          ACH.initialize(paramHashMap);
        }
        catch (Throwable localThrowable18)
        {
          DebugLog.log(Level.SEVERE, "ACH.initialize:" + localThrowable18.toString());
        }
      }
      if (serviceExists(paramHashMap, "Admin")) {
        try
        {
          Admin.initialize(paramHashMap);
        }
        catch (Throwable localThrowable19)
        {
          DebugLog.log(Level.SEVERE, "Admin.initialize:" + localThrowable19.toString());
        }
      }
      if (serviceExists(paramHashMap, "Applications")) {
        try
        {
          Applications.initialize(paramHashMap);
        }
        catch (Throwable localThrowable20)
        {
          DebugLog.log(Level.SEVERE, "Applications.initialize:" + localThrowable20.toString());
        }
      }
      if (serviceExists(paramHashMap, "AffiliateBank")) {
        try
        {
          AffiliateBankAdmin.initialize(paramHashMap);
        }
        catch (Throwable localThrowable21)
        {
          DebugLog.log(Level.SEVERE, "AffiliateBankAdmin.initialize:" + localThrowable21.toString());
        }
      }
      if (serviceExists(paramHashMap, "AutoEntitle")) {
        try
        {
          AutoEntitleAdmin.initialize(paramHashMap);
        }
        catch (Throwable localThrowable22)
        {
          DebugLog.log(Level.SEVERE, "AutoEntitle.initialize:" + localThrowable22.toString());
        }
      }
      if (serviceExists(paramHashMap, "BankEmployeeAdmin")) {
        try
        {
          BankEmployeeAdmin.initialize(paramHashMap);
        }
        catch (Throwable localThrowable23)
        {
          DebugLog.log(Level.SEVERE, "BankEmployeeAdmin.initialize:" + localThrowable23.toString());
        }
      }
      if (serviceExists(paramHashMap, "Banking")) {
        try
        {
          Banking.initialize(paramHashMap);
        }
        catch (Throwable localThrowable24)
        {
          DebugLog.log(Level.SEVERE, "Banking.initialize:" + localThrowable24.toString());
        }
      }
      if (serviceExists(paramHashMap, "BCReport")) {
        try
        {
          BCReport.initialize(paramHashMap);
        }
        catch (Throwable localThrowable25)
        {
          DebugLog.log(Level.SEVERE, "BCReport.initialize:" + localThrowable25.toString());
        }
      }
      if ((serviceExists(paramHashMap, "Billpay")) || (serviceExists(paramHashMap, "ProfileBillpay"))) {
        try
        {
          Billpay.initialize(paramHashMap);
        }
        catch (Throwable localThrowable26)
        {
          DebugLog.log(Level.SEVERE, "Billpay.initialize:" + localThrowable26.toString());
        }
      }
      if (serviceExists(paramHashMap, "BillPresentment")) {
        try
        {
          BillPresentment.initialize(paramHashMap);
        }
        catch (Throwable localThrowable27)
        {
          DebugLog.log(Level.SEVERE, "BillPresentment.initialize:" + localThrowable27.toString());
        }
      }
      if (serviceExists(paramHashMap, "Business")) {
        try
        {
          Business.initialize(paramHashMap);
        }
        catch (Throwable localThrowable28)
        {
          DebugLog.log(Level.SEVERE, "Business.initialize:" + localThrowable28.toString());
        }
      }
      if (serviceExists(paramHashMap, "Cash Concentration")) {
        try
        {
          CashCon.initialize(paramHashMap);
        }
        catch (Throwable localThrowable29)
        {
          DebugLog.log(Level.SEVERE, "CashCon.initialize:" + localThrowable29.toString());
        }
      }
      if (serviceExists(paramHashMap, "Check Imaging")) {
        try
        {
          CheckImaging.initialize(paramHashMap);
        }
        catch (Throwable localThrowable30)
        {
          DebugLog.log(Level.SEVERE, "CheckImaging.initialize:" + localThrowable30.toString());
        }
      }
      if (serviceExists(paramHashMap, "Online Statement")) {
        try
        {
          StatementData.initialize(paramHashMap);
        }
        catch (Throwable localThrowable31)
        {
          DebugLog.log(Level.SEVERE, "StatementData.initialize:" + localThrowable31.toString());
        }
      }
      if (serviceExists(paramHashMap, "Messaging")) {
        try
        {
          MessageAdmin.initialize(paramHashMap);
        }
        catch (Throwable localThrowable32)
        {
          DebugLog.log(Level.SEVERE, "MessageAdmin.initialize:" + localThrowable32.toString());
        }
      }
      if ((serviceExists(paramHashMap, "Messaging")) || (serviceExists(paramHashMap, "Mailing"))) {
        try
        {
          Messages.initialize(paramHashMap);
        }
        catch (Throwable localThrowable33)
        {
          DebugLog.log(Level.SEVERE, "Messages.initialize:" + localThrowable33.toString());
        }
      }
      if (serviceExists(paramHashMap, "Password Recovery")) {
        try
        {
          PasswordRecovery.initialize(paramHashMap);
        }
        catch (Throwable localThrowable34)
        {
          DebugLog.log(Level.SEVERE, "PasswordRecovery.initialize:" + localThrowable34.toString());
        }
      }
      if (serviceExists(paramHashMap, "Portal")) {
        try
        {
          Portal.initialize(paramHashMap);
        }
        catch (Throwable localThrowable35)
        {
          DebugLog.log(Level.SEVERE, "Portal.initialize:" + localThrowable35.toString());
        }
      }
      if (serviceExists(paramHashMap, "Register")) {
        try
        {
          Register.initialize(paramHashMap);
        }
        catch (Throwable localThrowable36)
        {
          DebugLog.log(Level.SEVERE, "Register.initialize:" + localThrowable36.toString());
        }
      }
      if (serviceExists(paramHashMap, "UserAdmin")) {
        try
        {
          UserAdmin.initialize(paramHashMap);
        }
        catch (Throwable localThrowable37)
        {
          DebugLog.log(Level.SEVERE, "UserAdmin.initialize:" + localThrowable37.toString());
        }
      }
      if (serviceExists(paramHashMap, "WireTransfer")) {
        try
        {
          Wire.initialize(paramHashMap);
        }
        catch (Throwable localThrowable38)
        {
          DebugLog.log(Level.SEVERE, "Wire.initialize:" + localThrowable38.toString());
        }
      }
      if (serviceExists(paramHashMap, "PaymentsAdmin")) {
        try
        {
          PaymentsAdmin.initialize(paramHashMap);
        }
        catch (Throwable localThrowable39)
        {
          DebugLog.log(Level.SEVERE, "PaymentsAdmin.initialize:" + localThrowable39.toString());
          localThrowable39.printStackTrace();
        }
      }
      if (serviceExists(paramHashMap, "BankLookup")) {
        try
        {
          BankLookup.initialize(paramHashMap);
        }
        catch (Throwable localThrowable40)
        {
          DebugLog.log(Level.SEVERE, "BankLookup.initialize:" + localThrowable40.toString());
          localThrowable40.printStackTrace();
        }
      }
      if (serviceExists(paramHashMap, "File Importer")) {
        try
        {
          FileImporter.initialize(paramHashMap);
        }
        catch (Throwable localThrowable41)
        {
          DebugLog.log(Level.SEVERE, "FileImporter.initialize:" + localThrowable41.toString());
        }
      }
      if (serviceExists(paramHashMap, "Reporting")) {
        try
        {
          Reporting.initialize(paramHashMap);
        }
        catch (Throwable localThrowable42)
        {
          DebugLog.log(Level.SEVERE, "Reporting.initialize:" + localThrowable42.toString());
        }
      }
      if (serviceExists(paramHashMap, "Data Consolidator")) {
        try
        {
          DataConsolidator.initialize(paramHashMap);
        }
        catch (Throwable localThrowable43)
        {
          DebugLog.log(Level.SEVERE, "DataConsolidator.initialize:" + localThrowable43.toString());
        }
      }
      if (serviceExists(paramHashMap, "Positive Pay")) {
        try
        {
          PositivePay.initialize(paramHashMap);
        }
        catch (Throwable localThrowable44)
        {
          DebugLog.log(Level.SEVERE, "PositivePay.initialize:" + localThrowable44.toString());
          localThrowable44.printStackTrace();
        }
      }
      if (serviceExists(paramHashMap, "Disbursements")) {
        try
        {
          Disbursements.initialize(paramHashMap);
        }
        catch (Throwable localThrowable45)
        {
          DebugLog.log(Level.SEVERE, "Disbursements.initialize:" + localThrowable45.toString());
        }
      }
      if (serviceExists(paramHashMap, "Lockbox")) {
        try
        {
          Lockbox.initialize(paramHashMap);
        }
        catch (Throwable localThrowable46)
        {
          DebugLog.log(Level.SEVERE, "Lockbox.initialize:" + localThrowable46.toString());
          localThrowable46.printStackTrace();
        }
      }
      if (serviceExists(paramHashMap, "File Monitor")) {
        try
        {
          FileMonitor.initialize(paramHashMap);
        }
        catch (Throwable localThrowable47)
        {
          DebugLog.log(Level.SEVERE, "FileMonitor.initialize:" + localThrowable47.toString());
          localThrowable47.printStackTrace();
        }
      }
      if (serviceExists(paramHashMap, "SmartCalendar")) {
        try
        {
          SmartCalendar.initialize(paramHashMap);
        }
        catch (Throwable localThrowable48)
        {
          DebugLog.log(Level.SEVERE, "SmartCalendar.initialize:" + localThrowable48.toString());
          localThrowable48.printStackTrace();
        }
      }
      if (serviceExists(paramHashMap, "BankReports")) {
        try
        {
          BankReport.initialize(paramHashMap);
        }
        catch (Throwable localThrowable49)
        {
          DebugLog.log(Level.SEVERE, "BankReport.initialize:" + localThrowable49.toString());
          localThrowable49.printStackTrace();
        }
      }
      if (serviceExists(paramHashMap, "ExternalTransferAdmin")) {
        try
        {
          ExternalTransferAdmin.initialize(paramHashMap);
        }
        catch (Throwable localThrowable50)
        {
          DebugLog.log(Level.SEVERE, "ExternalTransferAdmin.initialize:" + localThrowable50.toString());
          localThrowable50.printStackTrace();
        }
      }
      if (serviceExists(paramHashMap, "SystemAdmin")) {
        try
        {
          SystemAdmin.initialize(paramHashMap);
        }
        catch (Throwable localThrowable51)
        {
          DebugLog.log(Level.SEVERE, "SystemAdmin.initialize:" + localThrowable51.toString());
          localThrowable51.printStackTrace();
        }
      }
      if (serviceExists(paramHashMap, "TreasuryDirect")) {
        try
        {
          TreasuryDirect.initialize(paramHashMap);
        }
        catch (Throwable localThrowable52)
        {
          DebugLog.log(Level.SEVERE, "TreasuryDirect.initialize:" + localThrowable52.toString());
          localThrowable52.printStackTrace();
        }
      }
      if (serviceExists(paramHashMap, "Blocked Accounts")) {
        try
        {
          BlockedAccts.initialize(paramHashMap);
        }
        catch (Throwable localThrowable53)
        {
          DebugLog.log(Level.SEVERE, "BlockedAccts.initialize:" + localThrowable53.toString());
          localThrowable53.printStackTrace();
        }
      }
      if (serviceExists(paramHashMap, "Terms")) {
        try
        {
          Terms.initialize(paramHashMap);
        }
        catch (Throwable localThrowable54)
        {
          DebugLog.log(Level.SEVERE, "Terms.initialize:" + localThrowable54.toString());
          localThrowable54.printStackTrace();
        }
      }
      if (serviceExists(paramHashMap, "Account Aggregation")) {
        try
        {
          AccountAggregation.initialize(paramHashMap);
        }
        catch (Throwable localThrowable55)
        {
          DebugLog.log(Level.SEVERE, "AccountAggregation.initialize:" + localThrowable55.toString());
        }
      }
      if (serviceExists(paramHashMap, "Alerts")) {
        try
        {
          Alerts.initialize(paramHashMap);
        }
        catch (Throwable localThrowable56)
        {
          DebugLog.log(Level.SEVERE, "Alerts.initialize:" + localThrowable56.toString());
        }
      }
      try
      {
        BusinessAdmin.initialize(paramHashMap);
      }
      catch (Throwable localThrowable57)
      {
        DebugLog.log(Level.SEVERE, "BusinessAdmin.initialize:" + localThrowable57.toString());
      }
      try
      {
        BPTW.initialize(paramHashMap);
      }
      catch (Throwable localThrowable58)
      {
        DebugLog.log(Level.SEVERE, "BPTW.initialize:" + localThrowable58.toString());
      }
      try
      {
        Enroll.initialize(paramHashMap);
      }
      catch (Throwable localThrowable59)
      {
        DebugLog.log(Level.SEVERE, "Enroll.initialize:" + localThrowable59.toString());
      }
      try
      {
        Stops.initialize(paramHashMap);
      }
      catch (Throwable localThrowable60)
      {
        DebugLog.log(Level.SEVERE, "Stops.initialize:" + localThrowable60.toString());
      }
      try
      {
        CommBankIdentifier.initialize(paramHashMap);
      }
      catch (Throwable localThrowable61)
      {
        DebugLog.log(Level.SEVERE, "BankIdentifier.initialize:" + localThrowable61.toString());
      }
    }
    catch (Exception localException)
    {
      throw new CSILException(-1007, localException);
    }
  }
  
  public static boolean serviceExists(HashMap paramHashMap, String paramString)
  {
    HashMap localHashMap = null;
    boolean bool = false;
    try
    {
      localHashMap = (HashMap)paramHashMap.get("SERVICE_SETTINGS_LIST");
      if (localHashMap != null)
      {
        Object localObject = XMLUtil.getMultiSetEntry(localHashMap, "SERVICE", "SERVICE_NAME", paramString);
        if ((localObject != null) && ((localObject instanceof HashMap))) {
          bool = true;
        }
      }
    }
    catch (Exception localException) {}
    return bool;
  }
  
  public static void audit(SecureUser paramSecureUser, String paramString1, String paramString2, int paramInt, BigDecimal paramBigDecimal, String paramString3, String paramString4)
  {
    audit(paramSecureUser, paramString1, paramSecureUser.getBusinessID(), paramString2, paramInt, paramBigDecimal, paramString3, paramString4);
  }
  
  public static void audit(SecureUser paramSecureUser, ILocalizable paramILocalizable, String paramString1, int paramInt, BigDecimal paramBigDecimal, String paramString2, String paramString3)
  {
    audit(paramSecureUser, paramILocalizable, paramSecureUser.getBusinessID(), paramString1, paramInt, paramBigDecimal, paramString2, paramString3);
  }
  
  public static void audit(SecureUser paramSecureUser, String paramString1, int paramInt1, String paramString2, int paramInt2, BigDecimal paramBigDecimal, String paramString3, String paramString4)
  {
    audit(paramSecureUser, new LocalizableString("dummy", paramString1, null), paramInt1, paramString2, paramInt2, paramBigDecimal, paramString3, paramString4);
  }
  
  public static void audit(SecureUser paramSecureUser, ILocalizable paramILocalizable, int paramInt1, String paramString1, int paramInt2, BigDecimal paramBigDecimal, String paramString2, String paramString3)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    int i = paramSecureUser.getPrimaryUserID();
    if (paramSecureUser.getUserType() == 2)
    {
      str1 = "";
      str2 = String.valueOf(paramSecureUser.getProfileID());
      str3 = String.valueOf(paramSecureUser.getUserType());
    }
    else
    {
      str1 = String.valueOf(paramSecureUser.getProfileID());
      if (paramSecureUser.getAgent() != null) {
        if (paramSecureUser.getAgent().getProfileID() > 0)
        {
          str2 = String.valueOf(paramSecureUser.getAgent().getProfileID());
          str3 = String.valueOf(paramSecureUser.getAgent().getUserType());
        }
        else
        {
          str2 = paramSecureUser.getAgent().getUserName();
        }
      }
    }
    com.ffusion.util.logging.AuditLog.log(str1, i, str2, str3, paramILocalizable, paramString1, paramInt2, paramInt1, paramBigDecimal, paramString2, paramString3);
  }
  
  public static void audit(SecureUser paramSecureUser, String paramString1, String paramString2, int paramInt)
  {
    audit(paramSecureUser, paramString1, paramString2, paramInt, null, null, null);
  }
  
  public static void audit(SecureUser paramSecureUser, ILocalizable paramILocalizable, String paramString, int paramInt)
  {
    audit(paramSecureUser, paramILocalizable, paramString, paramInt, null, null, null);
  }
  
  public static void audit(SecureUser paramSecureUser, String paramString1, int paramInt1, String paramString2, int paramInt2)
  {
    audit(paramSecureUser, paramString1, paramInt1, paramString2, paramInt2, null, null, null);
  }
  
  public static void audit(SecureUser paramSecureUser, ILocalizable paramILocalizable, int paramInt1, String paramString, int paramInt2)
  {
    audit(paramSecureUser, paramILocalizable, paramInt1, paramString, paramInt2, null, null, null);
  }
  
  public static void audit(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, int paramInt)
  {
    audit(paramSecureUser, new LocalizableString("dummy", paramString1, null), paramString2, paramString3, paramInt);
  }
  
  public static void audit(SecureUser paramSecureUser, ILocalizable paramILocalizable, String paramString1, String paramString2, int paramInt)
  {
    if (paramString1 == null) {
      audit(paramSecureUser, paramILocalizable, paramString2, paramInt, null, null, null);
    } else {
      audit(paramSecureUser, paramILocalizable, Integer.parseInt(paramString1), paramString2, paramInt, null, null, null);
    }
  }
  
  public static void audit(SecureUser paramSecureUser, String paramString1, String paramString2, int paramInt, String paramString3)
  {
    audit(paramSecureUser, paramString1, paramString2, paramInt, null, null, paramString3);
  }
  
  public static void audit(SecureUser paramSecureUser, ILocalizable paramILocalizable, String paramString1, int paramInt, String paramString2)
  {
    audit(paramSecureUser, paramILocalizable, paramString1, paramInt, null, null, paramString2);
  }
  
  public static void audit(SecureUser paramSecureUser, String paramString1, String paramString2, int paramInt, BigDecimal paramBigDecimal, String paramString3)
  {
    audit(paramSecureUser, paramString1, paramString2, paramInt, paramBigDecimal, paramString3, null);
  }
  
  public static void audit(SecureUser paramSecureUser, ILocalizable paramILocalizable, String paramString1, int paramInt, BigDecimal paramBigDecimal, String paramString2)
  {
    audit(paramSecureUser, paramILocalizable, paramString1, paramInt, paramBigDecimal, paramString2, null);
  }
  
  public static void audit(SecureUser paramSecureUser, String paramString1, String paramString2, int paramInt, Currency paramCurrency)
  {
    audit(paramSecureUser, paramString1, paramString2, paramInt, paramCurrency, null);
  }
  
  public static void audit(SecureUser paramSecureUser, ILocalizable paramILocalizable, String paramString, int paramInt, Currency paramCurrency)
  {
    audit(paramSecureUser, paramILocalizable, paramString, paramInt, paramCurrency, null);
  }
  
  public static void audit(SecureUser paramSecureUser, String paramString1, String paramString2, int paramInt, Currency paramCurrency, String paramString3)
  {
    audit(paramSecureUser, new LocalizableString("dummy", paramString1, null), paramString2, paramInt, paramCurrency, paramString3);
  }
  
  public static void audit(SecureUser paramSecureUser, ILocalizable paramILocalizable, String paramString1, int paramInt, Currency paramCurrency, String paramString2)
  {
    BigDecimal localBigDecimal = null;
    String str = null;
    if (paramCurrency != null)
    {
      localBigDecimal = paramCurrency.getAmountValue();
      str = paramCurrency.getCurrencyCode();
    }
    audit(paramSecureUser, paramILocalizable, paramString1, paramInt, localBigDecimal, str, paramString2);
  }
  
  public static void audit(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2, int paramInt)
  {
    com.ffusion.util.logging.AuditLog.log(paramEntitlementGroupMember.getId(), paramEntitlementGroupMember.getAgentID(), paramEntitlementGroupMember.getAgentType(), paramString1, paramString2, paramInt, paramEntitlementGroupMember.getBusinessID());
  }
  
  public static void audit(EntitlementGroupMember paramEntitlementGroupMember, ILocalizable paramILocalizable, String paramString, int paramInt)
  {
    com.ffusion.util.logging.AuditLog.log(paramEntitlementGroupMember.getId(), paramEntitlementGroupMember.getAgentID(), paramEntitlementGroupMember.getAgentType(), paramILocalizable, paramString, paramInt, paramEntitlementGroupMember.getBusinessID());
  }
  
  public static void audit(SecureUser paramSecureUser, AuditLogRecord paramAuditLogRecord)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    if (paramSecureUser.getUserType() == 2)
    {
      str1 = "";
      str2 = String.valueOf(paramSecureUser.getProfileID());
      str3 = String.valueOf(paramSecureUser.getUserType());
    }
    else
    {
      str1 = String.valueOf(paramSecureUser.getProfileID());
      if (paramSecureUser.getAgent() != null) {
        if (paramSecureUser.getAgent().getProfileID() > 0)
        {
          str2 = String.valueOf(paramSecureUser.getAgent().getProfileID());
          str3 = String.valueOf(paramSecureUser.getAgent().getUserType());
        }
        else
        {
          str2 = paramSecureUser.getAgent().getUserName();
        }
      }
    }
    paramAuditLogRecord.setUserID(str1);
    paramAuditLogRecord.setAgentID(str2);
    paramAuditLogRecord.setAgentType(str3);
    paramAuditLogRecord.setBusinessID(paramSecureUser.getBusinessID());
    com.ffusion.util.logging.AuditLog.log(paramAuditLogRecord);
  }
  
  public static void audit(AuditLogRecord paramAuditLogRecord)
  {
    com.ffusion.util.logging.AuditLog.log(paramAuditLogRecord);
  }
  
  public static void logEntitlementFault(SecureUser paramSecureUser, String paramString1, String paramString2)
  {
    logEntitlementFault(paramSecureUser, new LocalizableString("dummy", paramString1, null), paramString2);
  }
  
  public static void logEntitlementFault(SecureUser paramSecureUser, ILocalizable paramILocalizable, String paramString)
  {
    audit(paramSecureUser, paramILocalizable, paramString, 2401);
  }
  
  public static void debug(String paramString)
  {
    DebugLog.log(Level.FINE, paramString);
  }
  
  public static void debug(SecureUser paramSecureUser, String paramString)
  {
    if (paramSecureUser != null) {
      DebugLog.log(Level.FINE, paramSecureUser.getId() + ": " + paramString);
    } else {
      DebugLog.log(Level.FINE, paramString);
    }
  }
  
  public static void debug(EntitlementGroupMember paramEntitlementGroupMember, String paramString)
  {
    if (paramEntitlementGroupMember != null) {
      DebugLog.log(Level.FINE, paramEntitlementGroupMember.getId() + ": " + paramString);
    } else {
      DebugLog.log(Level.FINE, paramString);
    }
  }
  
  public static synchronized void fini()
  {
    try
    {
      com.ffusion.util.logging.AuditLog.flush();
      FMLog.flush();
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("Initialize.fini", localThrowable);
    }
  }
  
  static
  {
    try
    {
      localObject1 = null;
      localObject1 = ResourceUtil.getResourceAsStream(new Initialize(), "csil.xml");
      int i = 0;
      int j = 0;
      ArrayList localArrayList = new ArrayList(3);
      while (j != -1)
      {
        arrayOfByte = new byte[1024];
        j = ((InputStream)localObject1).read(arrayOfByte, 0, 1024);
        if (j == -1) {
          break;
        }
        localArrayList.add(arrayOfByte);
        i += j;
      }
      ((InputStream)localObject1).close();
      byte[] arrayOfByte = new byte[i];
      int k = 0;
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        localObject2 = (byte[])localIterator.next();
        if (i - k > 1024) {
          j = 1024;
        } else {
          j = i - k;
        }
        System.arraycopy(localObject2, 0, arrayOfByte, k, j);
        k += j;
      }
      Object localObject2 = new XMLTag(true);
      ((XMLTag)localObject2).build(new String(arrayOfByte, 0, i));
      settings = XMLUtil.tagToHashMap((XMLTag)localObject2);
      initialize(settings);
      initialized = true;
    }
    catch (Exception localException)
    {
      Object localObject1 = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter((Writer)localObject1);
      localPrintWriter.print("****** Error initializing CSIL Core.  The product will not be functional:");
      localException.printStackTrace(localPrintWriter);
      DebugLog.log(Level.SEVERE, ((StringWriter)localObject1).toString());
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Initialize
 * JD-Core Version:    0.7.0.1
 */