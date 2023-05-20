package com.ffusion.csil.handlers;

import com.ffusion.approvals.ApprovalsException;
import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsChainItems;
import com.ffusion.beans.approvals.ApprovalsDecisions;
import com.ffusion.beans.approvals.ApprovalsGroup;
import com.ffusion.beans.approvals.ApprovalsGroupMembers;
import com.ffusion.beans.approvals.ApprovalsGroups;
import com.ffusion.beans.approvals.ApprovalsHistory;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsItemCounts;
import com.ffusion.beans.approvals.ApprovalsItems;
import com.ffusion.beans.approvals.ApprovalsLevel;
import com.ffusion.beans.approvals.ApprovalsLevels;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.services.IApprovalsService2;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.logging.Level;

public class ApprovalsHandler
  extends Initialize
{
  private static final String a65 = "Approvals";
  private static IApprovalsService2 a66 = null;
  private static final String a64 = "INIT_URL";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.initialize";
    DebugLog.log(Level.FINE, str);
    try
    {
      HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Approvals", str, 20107);
      a66 = (IApprovalsService2)HandlerUtil.instantiateService(localHashMap, str, 20107);
      a66.initialize(paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str + ": " + localCSILException.why, localCSILException);
      throw new CSILException(-1007, localCSILException);
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str + ": " + localException.getMessage(), localException);
      throw new CSILException(-1007, localException);
    }
  }
  
  public static Object getService()
  {
    return a66;
  }
  
  public static ApprovalsLevels getWorkflowLevels(SecureUser paramSecureUser, int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    return getWorkflowLevels(paramSecureUser, paramInt, 1, paramString1, paramString2, paramHashMap);
  }
  
  public static ApprovalsLevels getWorkflowLevels(SecureUser paramSecureUser, int paramInt1, int paramInt2, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getWorkflowLevels";
    DebugLog.log(Level.FINE, str);
    ApprovalsLevels localApprovalsLevels = null;
    try
    {
      localApprovalsLevels = a66.getWorkflowLevels(paramSecureUser, paramInt1, paramInt2, paramString1, paramString2, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsLevels;
  }
  
  public static void addWorkflowLevel(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.addWorkflowLevel";
    debug(paramSecureUser, str);
    try
    {
      a66.addWorkflowLevel(paramSecureUser, paramApprovalsLevel, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
  }
  
  public static void updateWorkflowLevels(SecureUser paramSecureUser, ApprovalsLevels paramApprovalsLevels, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.updateWorkflowLevels";
    debug(paramSecureUser, str);
    try
    {
      a66.updateWorkflowLevels(paramSecureUser, paramApprovalsLevels, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
  }
  
  public static void removeWorkflowLevels(SecureUser paramSecureUser, ApprovalsLevels paramApprovalsLevels, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.removeWorkflowLevels";
    debug(paramSecureUser, str);
    try
    {
      a66.removeWorkflowLevels(paramSecureUser, paramApprovalsLevels, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
  }
  
  public static ApprovalsChainItems getWorkflowChainItems(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws CSILException
  {
    ApprovalsChainItems localApprovalsChainItems = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getWorkflowChainItems";
    debug(paramSecureUser, str);
    try
    {
      localApprovalsChainItems = a66.getWorkflowChainItems(paramSecureUser, paramApprovalsLevel, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsChainItems;
  }
  
  public static void setWorkflowChainItems(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, ApprovalsChainItems paramApprovalsChainItems, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.setWorkflowChainItems";
    debug(paramSecureUser, str);
    try
    {
      a66.setWorkflowChainItems(paramSecureUser, paramApprovalsLevel, paramApprovalsChainItems, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
  }
  
  public static void clearWorkflowChainItems(SecureUser paramSecureUser, ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.clearWorkflowChainItems";
    debug(paramSecureUser, str);
    try
    {
      a66.clearWorkflowChainItems(paramSecureUser, paramApprovalsLevel, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
  }
  
  public static ApprovalsStatuses getPendingItems(int paramInt, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    ApprovalsStatuses localApprovalsStatuses = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getPendingItems";
    debug(str);
    try
    {
      localApprovalsStatuses = a66.getPendingItems(paramInt, paramHashMap1, paramHashMap2);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsStatuses;
  }
  
  public static ApprovalsStatuses getSubmittedItems(int paramInt, String[] paramArrayOfString, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    ApprovalsStatuses localApprovalsStatuses = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getSubmittedItems";
    debug(str);
    try
    {
      localApprovalsStatuses = a66.getSubmittedItems(paramInt, paramArrayOfString, paramHashMap1, paramHashMap2);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsStatuses;
  }
  
  public static int getNumberOfPendingApprovals(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    int i = 0;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getNumberOfPendingApprovals";
    debug(paramSecureUser, str);
    try
    {
      i = a66.getNumberOfPendingApprovals(paramSecureUser, paramHashMap1, paramHashMap2);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return i;
  }
  
  public static ApprovalsItemCounts getNumberOfPendingApprovalsDetail(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    ApprovalsItemCounts localApprovalsItemCounts = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getNumberOfPendingApprovalsDetail";
    debug(paramSecureUser, str);
    try
    {
      localApprovalsItemCounts = a66.getNumberOfPendingApprovalsDetail(paramSecureUser, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsItemCounts;
  }
  
  public static ApprovalsItems getPendingApprovals(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    ApprovalsItems localApprovalsItems = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getPendingApprovals";
    debug(paramSecureUser, str);
    try
    {
      localApprovalsItems = a66.getPendingApprovals(paramSecureUser, paramHashMap1, paramHashMap2);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsItems;
  }
  
  public static ApprovalsItems getPendingApprovalsForBank(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    ApprovalsItems localApprovalsItems = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getPendingApprovalsForBank";
    debug(paramSecureUser, str);
    try
    {
      localApprovalsItems = a66.getPendingApprovalsForBank(paramSecureUser, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsItems;
  }
  
  public static ApprovalsItems getConsumerPendingApprovalsForBank(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    ApprovalsItems localApprovalsItems = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getConsumerPendingApprovalsForBank";
    debug(paramSecureUser, str);
    try
    {
      localApprovalsItems = a66.getConsumerPendingApprovalsForBank(paramSecureUser, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsItems;
  }
  
  public static ApprovalsItem createApprovalItem(SecureUser paramSecureUser, int paramInt, IApprovable paramIApprovable, String[] paramArrayOfString1, String[] paramArrayOfString2, HashMap paramHashMap)
    throws CSILException
  {
    ApprovalsItem localApprovalsItem = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.createApprovalItem";
    debug(paramSecureUser, str);
    try
    {
      localApprovalsItem = a66.createApprovalItem(paramSecureUser, paramInt, paramIApprovable, paramArrayOfString1, paramArrayOfString2, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsItem;
  }
  
  public static ApprovalsItem modifyApprovalItem(ApprovalsItem paramApprovalsItem, HashMap paramHashMap)
    throws CSILException
  {
    ApprovalsItem localApprovalsItem = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.modifyApprovalItem";
    debug(str);
    try
    {
      localApprovalsItem = a66.modifyApprovalItem(paramApprovalsItem, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsItem;
  }
  
  public static ApprovalsItems submitDecisions(SecureUser paramSecureUser, ApprovalsDecisions paramApprovalsDecisions, HashMap paramHashMap)
    throws CSILException
  {
    ApprovalsItems localApprovalsItems = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.submitDecisions";
    debug(paramSecureUser, str);
    try
    {
      localApprovalsItems = a66.submitDecisions(paramSecureUser, paramApprovalsDecisions, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsItems;
  }
  
  public static void removeApprovalItem(ApprovalsItem paramApprovalsItem, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.removeApprovalItem";
    debug(str);
    try
    {
      a66.removeApprovalItem(paramApprovalsItem, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
  }
  
  public static void removeBusiness(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.removeBusiness";
    debug(str);
    try
    {
      a66.removeBusiness(paramInt, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
  }
  
  public static void removeObject(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.removeBusiness";
    debug(str);
    try
    {
      a66.removeObject(paramInt1, paramInt2, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
  }
  
  public static boolean isUserInUse(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool = false;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.isUserInUse";
    debug(paramSecureUser, str);
    try
    {
      bool = a66.isUserInUse(paramSecureUser, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return bool;
  }
  
  public static ApprovalsItems removeUser(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    ApprovalsItems localApprovalsItems = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.removeUser";
    debug(paramSecureUser, str);
    try
    {
      localApprovalsItems = a66.removeUser(paramSecureUser, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsItems;
  }
  
  public static boolean isGroupInUse(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool = false;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.isGroupInUse";
    debug(str);
    try
    {
      bool = a66.isGroupInUse(paramInt, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return bool;
  }
  
  public static boolean isBusinessInUse(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool = false;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.isBusinessInUse";
    debug(str);
    try
    {
      bool = a66.isBusinessInUse(paramInt, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return bool;
  }
  
  public static boolean isObjectInUse(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool = false;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.isObjectInUse";
    debug(str);
    try
    {
      bool = a66.isObjectInUse(paramInt1, paramInt2, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return bool;
  }
  
  public static ApprovalsItems removeGroup(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    ApprovalsItems localApprovalsItems = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.removeGroup";
    debug(str);
    try
    {
      localApprovalsItems = a66.removeGroup(paramInt, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsItems;
  }
  
  public static ApprovalsHistory getItemHistory(int paramInt, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    ApprovalsHistory localApprovalsHistory = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getItemHistory";
    debug(str);
    try
    {
      localApprovalsHistory = a66.getItemHistory(paramInt, paramHashMap1, paramHashMap2);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsHistory;
  }
  
  public static boolean isUserApprover(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool = false;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.isUserApprover";
    debug(paramSecureUser, str);
    try
    {
      bool = a66.isUserApprover(paramSecureUser, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return bool;
  }
  
  public static void addApprovalGroup(SecureUser paramSecureUser, ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.addApprovalGroup";
    debug(paramSecureUser, str);
    try
    {
      a66.addApprovalGroup(paramSecureUser, paramApprovalsGroup, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
  }
  
  public static ApprovalsItems removeApprovalGroup(SecureUser paramSecureUser, ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws CSILException
  {
    ApprovalsItems localApprovalsItems = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.removeApprovalGroup";
    debug(paramSecureUser, str);
    try
    {
      localApprovalsItems = a66.removeApprovalGroup(paramSecureUser, paramApprovalsGroup, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsItems;
  }
  
  public static void updateApprovalGroup(SecureUser paramSecureUser, ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.updateApprovalGroup";
    debug(paramSecureUser, str);
    try
    {
      a66.updateApprovalGroup(paramSecureUser, paramApprovalsGroup, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
  }
  
  public static boolean isApprovalGroupInUse(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool = false;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.isApprovalGroupInUse";
    debug(str);
    try
    {
      bool = a66.isApprovalGroupInUse(paramInt, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return bool;
  }
  
  public static ApprovalsGroupMembers getApprovalGroupMembers(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    ApprovalsGroupMembers localApprovalsGroupMembers = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getApprovalGroupMembers";
    debug(paramSecureUser, str);
    try
    {
      localApprovalsGroupMembers = a66.getApprovalGroupMembers(paramSecureUser, paramInt, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsGroupMembers;
  }
  
  public static void assignApprovalGroups(SecureUser paramSecureUser, int paramInt, ApprovalsGroups paramApprovalsGroups, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.assignApprovalGroups";
    debug(paramSecureUser, str);
    try
    {
      a66.assignApprovalGroups(paramSecureUser, paramInt, paramApprovalsGroups, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
  }
  
  public static ApprovalsGroups getApprovalGroups(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    ApprovalsGroups localApprovalsGroups = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getApprovalGroups";
    debug(paramSecureUser, str);
    try
    {
      localApprovalsGroups = a66.getApprovalGroups(paramSecureUser, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsGroups;
  }
  
  public static ApprovalsGroups getApprovalGroupsForUser(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    ApprovalsGroups localApprovalsGroups = null;
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getApprovalGroupsForUser";
    debug(paramSecureUser, str);
    try
    {
      localApprovalsGroups = a66.getApprovalGroupsForUser(paramSecureUser, paramInt, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localApprovalsGroups;
  }
  
  public static BusinessEmployees getEntitledBusinessEmployees(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getEntitledBusinessEmployees";
    debug(paramSecureUser, str);
    BusinessEmployees localBusinessEmployees = null;
    try
    {
      localBusinessEmployees = a66.getEntitledBusinessEmployees(paramSecureUser, paramString, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localBusinessEmployees;
  }
  
  public static EntitlementGroups getEntitledGroups(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.getEntitledGroups";
    debug(paramSecureUser, str);
    EntitlementGroups localEntitlementGroups = null;
    try
    {
      localEntitlementGroups = a66.getEntitledGroups(paramSecureUser, paramString, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
    return localEntitlementGroups;
  }
  
  public static void cleanup(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.ApprovalsHandler.cleanup";
    debug(str);
    try
    {
      a66.cleanup(paramInt, paramHashMap);
    }
    catch (ApprovalsException localApprovalsException)
    {
      DebugLog.throwing(str, localApprovalsException);
      throw new CSILException(jdMethod_int(localApprovalsException), localApprovalsException);
    }
  }
  
  private static int jdMethod_int(Throwable paramThrowable)
  {
    int i = 30208;
    if ((paramThrowable instanceof ApprovalsException))
    {
      int j = ((ApprovalsException)paramThrowable).getErrorCode();
      switch (j)
      {
      case 30002: 
        i = 30217;
        break;
      case 30113: 
        i = 30216;
        break;
      case 30076: 
        i = 20011;
        break;
      default: 
        i = j;
      }
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.ApprovalsHandler
 * JD-Core Version:    0.7.0.1
 */