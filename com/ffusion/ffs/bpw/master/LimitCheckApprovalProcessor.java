package com.ffusion.ffs.bpw.master;

import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.ffs.bpw.custimpl.limits.CustomLimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.custimpl.limits.ExtTrnLimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.CCEntryInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.bpw.util.BPWConfigWrapper;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.services.fx.FXService;
import com.ffusion.util.ContextPool;
import com.ffusion.util.XMLTag;
import com.ffusion.util.entitlements.ParentEntitlementsCache;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public final class LimitCheckApprovalProcessor
  implements DBConsts, FFSConst
{
  private static ContextPool cw = null;
  private static final String co = "CONTEXTFACTORY";
  private static final String cj = "USER";
  private static final String cq = "PASSWORD";
  private static final String cy = "JNDINAME";
  private static final String cx = "URL";
  private static final String cE = "DB_PROPERTIES";
  private static final String cF = "ENTITLEMENT_CACHED_ADAPTER";
  private static final String cB = "INITIALCONTEXTS";
  private static final String ct = "INCREMENTALCONTEXTS";
  private static final String cr = "MAXCONTEXTS";
  private static String cp = "com.ibm.websphere.naming.WsnInitialContextFactory";
  private static String cn = "";
  private static String cl = "";
  private static String ck = "ApprovalJNDIName";
  private static String cD = "iiop://localhost:2810";
  private static String cG = null;
  private static String cC = null;
  private static String cv = null;
  private static boolean cs = false;
  private static boolean cu = false;
  private static EntitlementCachedAdapter cA;
  private static boolean cm = true;
  public static final int NO_NEED_APPROVAL = 0;
  public static final int APPROVAL_PENDING = 1;
  public static final int APPROVAL_FAILED = 3;
  public static final int LIMIT_CHECK_FAILED = 4;
  public static final int LIMIT_REVERT_FAILED = 5;
  public static final int LIMIT_REVERT_SUCCEEDED = 6;
  public static final int LIMIT_CHECK_SUCCEEDED = 7;
  public static final int NO_NEED_REVERT = 8;
  public static final int APPROVAL_NOT_ALLOWED = 9;
  private static final HashMap cz = new HashMap();
  
  public static boolean start()
  {
    String str1 = "LimitCheckApprovalProcessor.start: ";
    if (!cs)
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      String str2 = localPropertyConfig.otherProperties.getProperty("bpw.limit.check.approval", "N");
      String str3 = localPropertyConfig.otherProperties.getProperty("bpw.server.standalone", "N");
      Object localObject2;
      try
      {
        Properties localProperties = new Properties();
        a(localProperties, localPropertyConfig);
        localObject2 = localPropertyConfig.otherProperties.getProperty("bpw.limit.check.approval.property.useCache", "true");
        boolean bool = Boolean.valueOf((String)localObject2).booleanValue();
        cA = new EntitlementCachedAdapter(localProperties, bool);
        HashMap localHashMap = new HashMap();
        localHashMap.put("ENTITLEMENT_CACHED_ADAPTER", cA);
        localHashMap.put("DB_PROPERTIES", localProperties);
        FXService localFXService = new FXService();
        localFXService.initialize(localHashMap);
        cA.setFXService(localFXService);
        FFSRegistry.bind("FXSERVICE", localFXService);
        ParentEntitlementsCache.initialize(cA.getEntitlementTypesWithProperties(new HashMap()));
      }
      catch (Throwable localThrowable1)
      {
        FFSDebug.log(str1 + ": Error: " + FFSDebug.stackTrace(localThrowable1), 0);
      }
      if (!str3.equalsIgnoreCase("N"))
      {
        FFSDebug.log("BPW is running stand alone.");
        FFSDebug.log(" ==== LimitCheckApprovalProcessor not started....");
        FFSDebug.console("BPW is running stand alone.");
        FFSDebug.console(" ==== LimitCheckApprovalProcessor not started....");
        cs = true;
      }
      else if (str2.equalsIgnoreCase("Y"))
      {
        cu = true;
        localObject1 = (BPWConfigWrapper)FFSRegistry.lookup("BPWCONFIGWRAPPER");
        try
        {
          localObject2 = ((BPWConfigWrapper)localObject1).getLimitCheckConfiguration();
          a((Properties)localObject2);
          loadFIs();
          CustomLimitCheckApprovalProcessor.start(cw, cu, cA, (Properties)localObject2);
          FFSDebug.console(" ==== CustomLimitCheckApprovalProcessor started....");
          FFSDebug.log(" ==== CustomLimitCheckApprovalProcessor started....");
          cs = true;
          FFSDebug.console(" ==== LimitCheckApprovalProcessor started....");
          FFSDebug.log(" ==== LimitCheckApprovalProcessor started....");
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log(str1 + ": Error: " + FFSDebug.stackTrace(localThrowable2), 0);
        }
      }
      else
      {
        FFSDebug.log("BPW Limit Check & Approval is not enabled .");
        FFSDebug.log(" ==== LimitCheckApprovalProcessor not started....");
        FFSDebug.console(str2 + " is not enabled .");
        FFSDebug.console(" ==== LimitCheckApprovalProcessor not started....");
        cs = true;
      }
      Object localObject1 = localPropertyConfig.otherProperties.getProperty("bpw.transfer.manageCustomers", "BPW");
      if (((String)localObject1).equalsIgnoreCase("EXTERNAL")) {
        cm = false;
      }
    }
    return cs;
  }
  
  public static void stop()
  {
    String str = "LimitCheckApprovalProcessor.stop";
    try
    {
      if (cs == true)
      {
        CustomLimitCheckApprovalProcessor.stop();
        FFSDebug.log(" ==== CustomLimitCheckApprovalProcessor stopped....", 6);
        FFSDebug.console(" ==== CustomLimitCheckApprovalProcessor stopped....");
        if (cw != null)
        {
          cw.closeContexts();
          cw = null;
        }
        if (cA != null)
        {
          cA.shutDown();
          cA = null;
        }
        cz.clear();
        FFSDebug.log(" ==== LimitCheckApprovalProcessor stopped....", 6);
        FFSDebug.console(" ==== LimitCheckApprovalProcessor stopped....");
      }
      else
      {
        FFSDebug.log("LimitCheckApprovalProcessor already stopped", 6);
        FFSDebug.console("LimitCheckApprovalProcessor already stopped ");
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log(str + " Error:" + FFSDebug.stackTrace(localException), 0);
    }
    cu = false;
    cs = false;
  }
  
  public static String mapToStatus(int paramInt)
  {
    String str1 = "WILLPROCESSON";
    String str2 = null;
    switch (paramInt)
    {
    case 0: 
      str2 = "WILLPROCESSON";
      break;
    case 1: 
      str2 = "APPROVAL_PENDING";
      break;
    case 3: 
      str2 = "APPROVAL_FAILED";
      break;
    case 4: 
      str2 = "LIMIT_CHECK_FAILED";
      break;
    case 5: 
      str2 = "LIMIT_REVERT_FAILED";
      break;
    case 6: 
      str2 = "LIMIT_REVERT_SUCCEEDED";
      break;
    case 7: 
      str2 = "LIMIT_CHECK_SUCCEEDED";
      break;
    case 8: 
      str2 = "LIMIT_REVERT_SUCCEEDED";
      break;
    case 9: 
      str2 = "APPROVAL_NOT_ALLOWED";
      break;
    case 2: 
    default: 
      str2 = str1;
    }
    return str2;
  }
  
  public static int processACHBatchAdd(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, HashMap paramHashMap)
    throws FFSException
  {
    if ("ACH_BATCH_REVERSAL".equals(paramACHBatchInfo.getBatchCategory())) {
      return 0;
    }
    return CustomLimitCheckApprovalProcessor.processACHBatchAdd(paramFFSConnectionHolder, paramACHBatchInfo, paramHashMap);
  }
  
  public static int processACHBatchReject(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, HashMap paramHashMap)
    throws FFSException
  {
    if ("ACH_BATCH_REVERSAL".equals(paramACHBatchInfo.getBatchCategory())) {
      return 8;
    }
    return CustomLimitCheckApprovalProcessor.processACHBatchReject(paramFFSConnectionHolder, paramACHBatchInfo, paramHashMap);
  }
  
  public static int processACHBatchDelete(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, HashMap paramHashMap)
    throws FFSException
  {
    if ("ACH_BATCH_REVERSAL".equals(paramACHBatchInfo.getBatchCategory())) {
      return 8;
    }
    return CustomLimitCheckApprovalProcessor.processACHBatchDelete(paramFFSConnectionHolder, paramACHBatchInfo, paramHashMap);
  }
  
  public static int processCCEntryAdd(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.processCCEntryAdd(paramFFSConnectionHolder, paramCCEntryInfo, paramHashMap);
  }
  
  public static int processCCEntryReject(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.processCCEntryReject(paramFFSConnectionHolder, paramCCEntryInfo, paramHashMap);
  }
  
  public static int processCCEntryDelete(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.processCCEntryDelete(paramFFSConnectionHolder, paramCCEntryInfo, paramHashMap);
  }
  
  public static int processWireAdd(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.processWireAdd(paramFFSConnectionHolder, paramWireInfo, paramHashMap);
  }
  
  public static int processWireReject(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.processWireReject(paramFFSConnectionHolder, paramWireInfo, paramHashMap);
  }
  
  public static int processWireDelete(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.processWireDelete(paramFFSConnectionHolder, paramWireInfo, paramHashMap);
  }
  
  public static int processPmtAdd(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.processPmtAdd(paramFFSConnectionHolder, paramPmtInfo, paramHashMap);
  }
  
  public static int processPmtReject(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.processPmtReject(paramFFSConnectionHolder, paramPmtInfo, paramHashMap);
  }
  
  public static int processPmtDelete(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.processPmtDelete(paramFFSConnectionHolder, paramPmtInfo, paramHashMap);
  }
  
  public static boolean checkEntitlementACHBatch(ACHBatchInfo paramACHBatchInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.checkEntitlementACHBatch(paramACHBatchInfo, paramHashMap);
  }
  
  public static boolean checkEntitlementPmt(PmtInfo paramPmtInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.checkEntitlementPmt(paramPmtInfo, paramHashMap);
  }
  
  public static boolean checkEntitlementIntra(IntraTrnInfo paramIntraTrnInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.checkEntitlementIntra(paramIntraTrnInfo, paramHashMap);
  }
  
  public static boolean checkEntitlementWire(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.checkEntitlementWire(paramFFSConnectionHolder, paramWireInfo, paramHashMap);
  }
  
  public static boolean checkEntitlementExtTrn(TransferInfo paramTransferInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.checkEntitlementExtTrn(paramTransferInfo, paramHashMap);
  }
  
  public static int processIntraTrnAdd(FFSConnectionHolder paramFFSConnectionHolder, IntraTrnInfo paramIntraTrnInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.processIntraTrnAdd(paramFFSConnectionHolder, paramIntraTrnInfo, paramHashMap);
  }
  
  public static int processIntraTrnReject(FFSConnectionHolder paramFFSConnectionHolder, IntraTrnInfo paramIntraTrnInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.processIntraTrnReject(paramFFSConnectionHolder, paramIntraTrnInfo, paramHashMap);
  }
  
  public static int processIntraTrnDelete(FFSConnectionHolder paramFFSConnectionHolder, IntraTrnInfo paramIntraTrnInfo, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.processIntraTrnDelete(paramFFSConnectionHolder, paramIntraTrnInfo, paramHashMap);
  }
  
  public static int processExternalTransferAdd(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str = "LimitCheckApprovalProcessor.processExternalTransferAdd: ";
    if (!ExtTrnLimitCheckApprovalProcessor.shouldCheckExtTransferLimits(paramFFSConnectionHolder, paramTransferInfo, paramHashMap, str, cm)) {
      return 0;
    }
    return CustomLimitCheckApprovalProcessor.processExternalTransferAdd(paramFFSConnectionHolder, paramTransferInfo, paramHashMap);
  }
  
  public static int processExternalTransferReject(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str = "LimitCheckApprovalProcessor.processExternalTransferReject: ";
    if (!ExtTrnLimitCheckApprovalProcessor.shouldCheckExtTransferLimits(paramFFSConnectionHolder, paramTransferInfo, paramHashMap, str, cm)) {
      return 8;
    }
    return CustomLimitCheckApprovalProcessor.processExternalTransferReject(paramFFSConnectionHolder, paramTransferInfo, paramHashMap);
  }
  
  public static int processExternalTransferDelete(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str = "LimitCheckApprovalProcessor.processExternalTransferDelete: ";
    if (!ExtTrnLimitCheckApprovalProcessor.shouldCheckExtTransferLimits(paramFFSConnectionHolder, paramTransferInfo, paramHashMap, str, cm)) {
      return 8;
    }
    return CustomLimitCheckApprovalProcessor.processExternalTransferDelete(paramFFSConnectionHolder, paramTransferInfo, paramHashMap);
  }
  
  private static void a(Properties paramProperties)
    throws Exception
  {
    String str = "LimitCheckApprovalProcessor.initContextPool";
    FFSDebug.log(str, "Starts  ....", 6);
    cn = paramProperties.getProperty("USER", cn);
    cl = paramProperties.getProperty("PASSWORD", cl);
    cD = paramProperties.getProperty("URL", cD);
    cp = paramProperties.getProperty("CONTEXTFACTORY", cp);
    ck = paramProperties.getProperty("JNDINAME", ck);
    cG = paramProperties.getProperty("INITIALCONTEXTS");
    cw = new ContextPool(cD, cn, cl, cp);
    if (cG != null) {
      cw.setInitialContexts(Integer.parseInt(cG));
    }
    cC = paramProperties.getProperty("INCREMENTALCONTEXTS");
    if (cC != null) {
      cw.setIncrementalContexts(Integer.parseInt(cC));
    }
    cv = paramProperties.getProperty("MAXCONTEXTS");
    if (cv != null) {
      cw.setMaxContexts(Integer.parseInt(cv));
    }
    cw.createPool();
  }
  
  private static void a(Properties paramProperties, PropertyConfig paramPropertyConfig)
    throws Exception
  {
    String str1 = "LimitCheckApprProcessor.populateEntitlementDBParams: ";
    FFSDebug.log(str1 + "starts ...", 6);
    paramProperties.setProperty("ConnectionType", paramPropertyConfig.BPWServ_dbType);
    paramProperties.setProperty("PoolType", "FFI");
    paramProperties.setProperty("DBName", paramPropertyConfig.BPWServ_dbName);
    paramProperties.setProperty("Server", paramPropertyConfig.BPWServ_dbHost);
    paramProperties.setProperty("Port", paramPropertyConfig.BPWServ_dbPort);
    paramProperties.setProperty("User", paramPropertyConfig.BPWServ_dbUser);
    paramProperties.setProperty("Password", paramPropertyConfig.BPWServ_dbPassword);
    paramProperties.setProperty("DefaultConnections", "5");
    paramProperties.setProperty("MaxConnections", "10");
    String str2 = paramPropertyConfig.otherProperties.getProperty("bpw.limit.check.approval.property.cacheMaxSize", "10000");
    String str3 = paramPropertyConfig.otherProperties.getProperty("bpw.limit.check.approval.property.reapInterval", "1000");
    String str4 = paramPropertyConfig.otherProperties.getProperty("bpw.limit.check.approval.property.outDateInterval", "900000");
    String str5 = paramPropertyConfig.otherProperties.getProperty("BPW.DB.URL");
    FFSDebug.log(str1 + "cacheMaxSize = " + str2, 6);
    FFSDebug.log(str1 + "reapInterval = " + str3, 6);
    FFSDebug.log(str1 + "outDateInterval = " + str4, 6);
    FFSDebug.log(str1 + "JDBCUrl = " + str5, 6);
    paramProperties.setProperty("CacheMaxSize", str2);
    paramProperties.setProperty("ReapInterval", str3);
    if ((str5 != null) && (str5.length() > 0)) {
      paramProperties.setProperty("JDBCUrl", str5);
    }
    FFSDebug.log(str1 + "done.", 6);
  }
  
  private static void a(String paramString, Properties paramProperties)
    throws Exception
  {
    XMLTag localXMLTag = new XMLTag(true);
    localXMLTag.build(paramString);
    ArrayList localArrayList = localXMLTag.getContainedTagList();
    String str = null;
    str = a("URL", localArrayList);
    paramProperties.put("URL", str);
    str = a("CONTEXTFACTORY", localArrayList);
    paramProperties.put("CONTEXTFACTORY", str);
    str = a("JNDINAME", localArrayList);
    paramProperties.put("JNDINAME", str);
    str = a("USER", localArrayList);
    paramProperties.put("USER", str);
    str = a("PASSWORD", localArrayList);
    paramProperties.put("PASSWORD", str);
    str = a("INITIALCONTEXTS", localArrayList);
    if (str != null) {
      paramProperties.put("INITIALCONTEXTS", str);
    }
    str = a("INCREMENTALCONTEXTS", localArrayList);
    if (str != null) {
      paramProperties.put("INCREMENTALCONTEXTS", str);
    }
    str = a("MAXCONTEXTS", localArrayList);
    if (str != null) {
      paramProperties.put("MAXCONTEXTS", str);
    }
  }
  
  private static String a(String paramString, ArrayList paramArrayList)
    throws Exception
  {
    String str1 = "LimitCheckApprovalProcessor.getAttributeName";
    int i = paramArrayList.size();
    String str2 = null;
    for (int j = 0; j < i; j++)
    {
      XMLTag localXMLTag = (XMLTag)paramArrayList.get(j);
      str2 = (String)localXMLTag.getAttribute(paramString);
      if (str2 != null)
      {
        paramArrayList.remove(paramString);
        break;
      }
    }
    if (str2 == null) {
      throw new Exception(str1 + "ERROR: No Attribute value found");
    }
    return str2;
  }
  
  public static String getRTN(String paramString)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.getRTN(paramString);
  }
  
  public static void loadFIs()
    throws FFSException
  {}
  
  public static boolean checkEntitlementCCEntry(CCEntryInfo paramCCEntryInfo, String paramString, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.checkEntitlementCCEntry(paramCCEntryInfo, paramString, paramHashMap);
  }
  
  public static boolean checkEntitlementACHBatchViewing(ACHBatchInfo paramACHBatchInfo, EntitlementGroupMember paramEntitlementGroupMember, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.checkEntitlementACHBatchViewing(paramACHBatchInfo, paramEntitlementGroupMember, paramHashMap);
  }
  
  public static boolean checkEntitlementCCEntryViewing(CCEntryInfo paramCCEntryInfo, EntitlementGroupMember paramEntitlementGroupMember, HashMap paramHashMap)
    throws FFSException
  {
    return CustomLimitCheckApprovalProcessor.checkEntitlementCCEntryViewing(paramCCEntryInfo, paramEntitlementGroupMember, paramHashMap);
  }
  
  public static boolean doLimitCheck()
  {
    return cu;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.LimitCheckApprovalProcessor
 * JD-Core Version:    0.7.0.1
 */